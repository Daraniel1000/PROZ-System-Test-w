package pl.edu.pw.elka.proz.bandaimbecyli.web;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import pl.edu.pw.elka.proz.bandaimbecyli.db.InMemoryTestsDAO;
import pl.edu.pw.elka.proz.bandaimbecyli.db.TestsDAO;
import pl.edu.pw.elka.proz.bandaimbecyli.db.prozDatabaseConnection;
import pl.edu.pw.elka.proz.bandaimbecyli.logic.PointsCalculator;
import pl.edu.pw.elka.proz.bandaimbecyli.models.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;
import java.util.logging.Logger;

@Path("/tests")
@Produces(MediaType.APPLICATION_JSON)
public class TestsService
{
    public static TestsDAO dao;

    static
    {
        /*prozQuestion q1 = new prozQuestion(1, 1, "Pamiętaj, zawsze B");
        q1.initAnswers(4);
        q1.addAnswer(new prozAnswer(1, false, "A"));
        q1.addAnswer(new prozAnswer(2, true, "B"));
        q1.addAnswer(new prozAnswer(3, false, "C"));
        q1.addAnswer(new prozAnswer(4, false, "D"));

        prozQuestion q2 = new prozQuestion(2, 1, "Czy zdążymy z projektem na czas?");
        q2.initAnswers(2);
        q2.addAnswer(new prozAnswer(5, false, "Tak"));
        q2.addAnswer(new prozAnswer(6, true, "Oczywiście że tak"));

        prozQuestion q3 = new prozQuestion(3, 1, "Czy chodzisz na wykłady z PODA?");
        q3.initAnswers(3);
        q3.addAnswer(new prozAnswer(7, false, "Na wszystkie"));
        q3.addAnswer(new prozAnswer(8, true, "No ze dwa razy może się zdarzyło..."));
        q3.addAnswer(new prozAnswer(9, false, "Nope"));


        prozTest t1 = new prozTest(1, "Test pierwszy", "20.03.2007", "30.05.2020", 1);
        t1.initQuestions(3);
        t1.addQuestion(q1);
        t1.addQuestion(q2);
        t1.addQuestion(q3);

        prozTest t2 = new prozTest(2, "Test drugi", "20.03.2007", "30.05.2020", 1);
        t2.initQuestions(1);
        t2.addQuestion(q1);

        List<prozTest> tests = new ArrayList<>();
        tests.add(t1);
        tests.add(t2);
        dao = new InMemoryTestsDAO(tests);*/

        dao = new prozDatabaseConnection();
    }

    @Path("/")
    @GET
    public List<prozTest> listAvailableTests(@HeaderParam("Authorization") String auth) throws SQLException
    {
        int user = checkUser(auth);
        if (user == -1)
            throw new ForbiddenException("Invalid user");
        return dao.GetTestsAvailableForUser(user);
    }

    @Path("/{testId}")
    @GET
    public prozTest startTest(@PathParam("testId") int testId, @HeaderParam("Authorization") String auth) throws SQLException
    {
        int user = checkUser(auth);
        if (user == -1)
            throw new ForbiddenException("Invalid user");

        prozTest test = dao.GetTest(testId);
        if (test == null)
            throw new NotFoundException("No such test found");
        if (Calendar.getInstance().getTime().getTime() < test.getStartDate().getTime())
            throw new ForbiddenException("Test not started");
        if (Calendar.getInstance().getTime().getTime() > test.getEndDate().getTime())
            throw new ForbiddenException("Test already finished");
        dao.FillTestQuestions(test);
        for(prozQuestion q : test.getQuestions())
            dao.FillQuestionAnswers(q);
        return test;
    }

    @Path("/{testId}/submit")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public ResultsResponse submitAnswers(@PathParam("testId") int testId, List<Integer> solutions, @HeaderParam("Authorization") String auth) throws SQLException
    {
        int user = checkUser(auth);
        if (user == -1)
            throw new ForbiddenException("Invalid user");

        if (solutions == null)
            throw new BadRequestException("No solutions sent");
        prozTest test = dao.GetTest(testId);
        if (test == null)
            throw new NotFoundException("No such test found");
        if (Calendar.getInstance().getTime().getTime() < test.getStartDate().getTime())
            throw new ForbiddenException("Test not started");
        if (Calendar.getInstance().getTime().getTime() > test.getEndDate().getTime() + 60000) // a small window in case of network problems
            throw new ForbiddenException("Test already finished");
        if (dao.GetResults(test.getTestID(), user) != null)
            throw new ForbiddenException("Test already solved");
        dao.FillTestQuestions(test);
        for(prozQuestion q : test.getQuestions())
            dao.FillQuestionAnswers(q);

        prozResults results = new prozResults(test.getTestID(), user, new Timestamp(new Date().getTime()), -1);
        results.initAnswers(solutions.size());
        PointsCalculator pointsCalculator = new PointsCalculator(test);

        for(prozQuestion question : test.getQuestions()) {
            List<prozAnswer> answers = new ArrayList<>();
            for(int aID : solutions) {
                prozAnswer answer = null;
                for(prozAnswer a : question.getAnswers()) {
                    if (a.getAnswerID() == aID) {
                        answer = a;
                        break;
                    }
                }
                if (answer == null)
                    continue;

                results.addAnswerID(answer.getAnswerID());
                answers.add(answer);
            }

            pointsCalculator.addPointsForQuestion(question, answers);
        }

        if (!results.getAnswerID().containsAll(solutions))
            throw new BadRequestException("Invalid solution IDs found");

        results.setPoints(pointsCalculator.getPoints());
        dao.SendResults(results);

        // TODO: show answers only after test finished to avoid cheating?
        List<Integer> correctAnswers = ResultsResponse.correctAnswersForTest(test);

        return new ResultsResponse(results, correctAnswers);
    }

    @Path("/{testId}/results")
    @GET
    public ResultsResponse getResults(@PathParam("testId") int testId, @HeaderParam("Authorization") String auth) throws SQLException
    {
        int user = checkUser(auth);
        if (user == -1)
            throw new ForbiddenException("Invalid user");

        prozTest test = dao.GetTest(testId);
        if (test == null)
            throw new NotFoundException("No such test found");
        dao.FillTestQuestions(test);
        for(prozQuestion q : test.getQuestions())
            dao.FillQuestionAnswers(q);

        prozResults results = dao.GetResults(test.getTestID(), user);
        if (results == null)
            throw new NotFoundException("Test not solved yet");

        List<Integer> correctAnswers = ResultsResponse.correctAnswersForTest(test);
        return new ResultsResponse(results, correctAnswers);
    }

    private int checkUser(String authString) throws SQLException
    {
        if (authString == null)
            return -1;
        String[] authParts = authString.split(" ");
        if (authParts.length != 2)
            return -1;
        if (!authParts[0].equals("Basic"))
            return -1;

        String decodedAuth = new String(Base64.decode(authParts[1]));
        String[] authData = decodedAuth.split(":");
        if (authData.length != 2)
            return -1;
        String username = authData[0];
        String password = authData[1];

        return dao.CheckUserLogin(username, password);
    }
}
