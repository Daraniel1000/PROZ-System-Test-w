package pl.edu.pw.elka.proz.bandaimbecyli.web;

import pl.edu.pw.elka.proz.bandaimbecyli.db.InMemoryTestsDAO;
import pl.edu.pw.elka.proz.bandaimbecyli.db.TestsDAO;
import pl.edu.pw.elka.proz.bandaimbecyli.db.prozDatabaseConnection;
import pl.edu.pw.elka.proz.bandaimbecyli.models.prozAnswer;
import pl.edu.pw.elka.proz.bandaimbecyli.models.prozQuestion;
import pl.edu.pw.elka.proz.bandaimbecyli.models.prozTest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Path("/tests")
@Produces(MediaType.APPLICATION_JSON)
public class TestsService
{
    public static TestsDAO dao;

    static
    {

        prozQuestion q1 = new prozQuestion(1, 1, "Pamiętaj, zawsze B");
        q1.initAnswers(4);
        q1.addAnswer(new prozAnswer(1, false, "A"));
        q1.addAnswer(new prozAnswer(2, true, "B"));
        q1.addAnswer(new prozAnswer(3, false, "C"));
        q1.addAnswer(new prozAnswer(4, false, "D"));

        prozTest t1 = new prozTest(1, "Test pierwszy", "20.03.2007", "30.05.2020", 1);
        t1.initQuestions(1);
        t1.addQuestion(q1);

        prozTest t2 = new prozTest(2, "Test drugi", "20.03.2007", "30.05.2020", 1);
        t2.initQuestions(1);
        t2.addQuestion(q1);

        List<prozTest> tests = new ArrayList<>();
        tests.add(t1);
        tests.add(t2);
        dao = new InMemoryTestsDAO(tests);
        /*try {
            dao = new prozDatabaseConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
    }

    @Path("/")
    @GET
    public List<prozTest> listAvailableTests() throws SQLException
    {
        return dao.GetTestsAvailableForUser(1);
    }

    @Path("/{testId}")
    @GET
    public prozTest generateTest(@PathParam("testId") int testId) throws SQLException
    {
        prozTest test = dao.GetTest(testId);
        if (test == null)
            throw new NotFoundException("No such test found");
        dao.FillTestQuestions(test);
        for(prozQuestion q : test.getQuestions())
            dao.FillQuestionAnswers(q);
        return test;
    }

    @Path("/{testId}/submit")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String submitAnswers(@PathParam("testId") int testId,List<String> solutions) throws SQLException
    {
        if (solutions == null)
            throw new BadRequestException("No solutions sent");
        prozTest test = dao.GetTest(testId);
        if (test == null)
            throw new NotFoundException("No such test found");
        dao.FillTestQuestions(test);
        for(prozQuestion q : test.getQuestions())
            dao.FillQuestionAnswers(q);
        return "good job"; // TODO
    }
}
