package pl.edu.pw.elka.proz.bandaimbecyli.web;

import pl.edu.pw.elka.proz.bandaimbecyli.dao.InMemoryTestsDAO;
import pl.edu.pw.elka.proz.bandaimbecyli.dao.TestsDAO;
import pl.edu.pw.elka.proz.bandaimbecyli.generator.TestGenerator;
import pl.edu.pw.elka.proz.bandaimbecyli.models.GeneratedTest;
import pl.edu.pw.elka.proz.bandaimbecyli.models.Test;
import pl.edu.pw.elka.proz.bandaimbecyli.models.TestResults;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;
import java.util.List;

@Path("/tests")
@Produces(MediaType.APPLICATION_JSON)
public class TestsService
{
    private TestsDAO dao = new InMemoryTestsDAO();

    @Path("/")
    @GET
    public Collection<Test> listAvailableTests()
    {
        return dao.listAvailableTests();
    }

    @Path("/{testId}/solve")
    @POST
    public GeneratedTest generateTest(@PathParam("testId") int testId)
    {
        Test test = dao.getTestById(testId, true);
        if (test == null)
            throw new NotFoundException("No such test found");
        return new TestGenerator(dao).generateTest(test);
    }

    @Path("/{testId}/solve/{solveId}/submit")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public TestResults submitAnswers(@PathParam("testId") int testId, @PathParam("solveId") int solveId, List<String> solutions)
    {
        GeneratedTest genTest = dao.getGeneratedTestById(solveId);
        if (genTest == null)
            throw new NotFoundException("No such generated test found");
        if (genTest.getTestId() != testId)
            throw new BadRequestException("Invalid - solution does not match testId");
        Test test = dao.getTestById(testId, true);
        return new TestGenerator(dao).submitAndGradeTest(test, genTest, solutions);
    }
}
