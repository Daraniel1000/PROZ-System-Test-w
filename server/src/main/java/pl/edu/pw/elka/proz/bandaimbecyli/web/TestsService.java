package pl.edu.pw.elka.proz.bandaimbecyli.web;

import pl.edu.pw.elka.proz.bandaimbecyli.dao.DatabaseTestsDAO;
import pl.edu.pw.elka.proz.bandaimbecyli.dao.InMemoryTestsDAO;
import pl.edu.pw.elka.proz.bandaimbecyli.dao.TestsDAO;
import pl.edu.pw.elka.proz.bandaimbecyli.generator.TestGenerator;
import pl.edu.pw.elka.proz.bandaimbecyli.models.GeneratedTest;
import pl.edu.pw.elka.proz.bandaimbecyli.models.AvailableTest;
import pl.edu.pw.elka.proz.bandaimbecyli.models.TestResults;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Path("/tests")
@Produces(MediaType.APPLICATION_JSON)
public class TestsService
{
    public static TestsDAO dao;

    static
    {
        /*List<AvailableTest> tests = new ArrayList<>();
        tests.add(new AvailableTest(1, "Test pierwszy"));
        tests.add(new AvailableTest(2, "Test drugi"));
        dao = new InMemoryTestsDAO(tests);*/
        try {
            dao = new DatabaseTestsDAO();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Path("/")
    @GET
    public Collection<AvailableTest> listAvailableTests()
    {
        return dao.listAvailableTests();
    }

    @Path("/{testId}/solve")
    @POST
    public GeneratedTest generateTest(@PathParam("testId") int testId)
    {
        AvailableTest test = dao.getTestById(testId, true);
        if (test == null)
            throw new NotFoundException("No such test found");
        return new TestGenerator(dao).generateTest(test);
    }

    @Path("/{testId}/solve/{solveId}/submit")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public TestResults submitAnswers(@PathParam("testId") int testId, @PathParam("solveId") int solveId, List<String> solutions)
    {
        if (solutions == null)
            throw new BadRequestException("No solutions sent");
        GeneratedTest genTest = dao.getGeneratedTestById(solveId);
        if (genTest == null)
            throw new NotFoundException("No such generated test found");
        if (genTest.getTestId() != testId)
            throw new BadRequestException("Invalid - solution does not match testId");
        AvailableTest test = dao.getTestById(testId, true);
        return new TestGenerator(dao).submitAndGradeTest(test, genTest, solutions);
    }
}
