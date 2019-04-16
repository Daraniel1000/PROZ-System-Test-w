package pl.edu.pw.elka.proz.bandaimbecyli.tests;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import pl.edu.pw.elka.proz.bandaimbecyli.dao.InMemoryTestsDAO;
import pl.edu.pw.elka.proz.bandaimbecyli.models.AvailableTest;
import pl.edu.pw.elka.proz.bandaimbecyli.models.GeneratedTest;
import pl.edu.pw.elka.proz.bandaimbecyli.models.TestResults;
import pl.edu.pw.elka.proz.bandaimbecyli.web.TestsService;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TestsTests extends JerseyTest {
    @Override
    protected Application configure() {
        return new ResourceConfig(TestsService.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();

        // Reset the database before every test
        List<AvailableTest> tests = new ArrayList<>();
        tests.add(new AvailableTest(1, "Test pierwszy"));
        tests.add(new AvailableTest(2, "Test drugi"));
        TestsService.dao = new InMemoryTestsDAO(tests);
    }

    @Test
    public void givenGetAvailableTests_thenResponseHasTests() {
        Response response = target("/tests").request().get();

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaderString(HttpHeaders.CONTENT_TYPE));

        List<AvailableTest> content = response.readEntity(new GenericType<List<AvailableTest>>() {});
        assertEquals(2, content.size());
        assertEquals(1, content.get(0).getId());
        assertEquals("Test pierwszy", content.get(0).getName());
        assertEquals(2, content.get(1).getId());
        assertEquals("Test drugi", content.get(1).getName());
    }

    @Test
    public void givenGenerateTest_whenTestIsValid_thenTestIsGenerated() {
        Response response = target("/tests/2/solve").request().post(null);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaderString(HttpHeaders.CONTENT_TYPE));

        GeneratedTest content = response.readEntity(GeneratedTest.class);
        assertNotEquals(0, content.getId());
        assertEquals(2, content.getTestId());
    }

    @Test
    public void givenGenerateTest_whenTestIsValid_thenErrorIsThrown() {
        Response response = target("/tests/2137/solve").request().post(null);
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void givenSubmitTest_whenGeneratedTestIsValid_thenResponseIsOK() {
        GeneratedTest generatedTest = target("/tests/2/solve").request().post(null).readEntity(GeneratedTest.class);

        List<String> responses = new ArrayList<>();
        responses.add("B");
        responses.add("D");
        responses.add("zielony");
        Response response = target("/tests/2/solve/" + generatedTest.getId() + "/submit").request().post(Entity.entity(responses, MediaType.APPLICATION_JSON_TYPE));

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaderString(HttpHeaders.CONTENT_TYPE));

        TestResults content = response.readEntity(TestResults.class);
        assertEquals(generatedTest.getId(), content.getGeneratedTestId());
    }

    @Test
    public void givenSubmitTest_whenNoResponseIsSubmitted_thenResponseIsError() {
        GeneratedTest generatedTest = target("/tests/2/solve").request().post(null).readEntity(GeneratedTest.class);

        Response response = target("/tests/2/solve/" + generatedTest.getId() + "/submit").request().post(null);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    public void givenSubmitTest_whenUnmatchedTestId_thenResponseIsError() {
        GeneratedTest generatedTest = target("/tests/2/solve").request().post(null).readEntity(GeneratedTest.class);

        List<String> responses = new ArrayList<>();
        responses.add("B");
        responses.add("D");
        responses.add("zielony");
        Response response = target("/tests/1/solve/" + generatedTest.getId() + "/submit").request().post(Entity.entity(responses, MediaType.APPLICATION_JSON_TYPE));
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }
}
