package pl.edu.pw.elka.proz.bandaimbecyli.tests;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import pl.edu.pw.elka.proz.bandaimbecyli.db.InMemoryTestsDAO;
import pl.edu.pw.elka.proz.bandaimbecyli.models.prozAnswer;
import pl.edu.pw.elka.proz.bandaimbecyli.models.prozQuestion;
import pl.edu.pw.elka.proz.bandaimbecyli.models.prozTest;
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
        TestsService.dao = new InMemoryTestsDAO(tests);
    }

    @Test
    public void givenGetTests_thenResponseHasTests() {
        Response response = target("/tests").request().get();

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaderString(HttpHeaders.CONTENT_TYPE));

        List<prozTest> content = response.readEntity(new GenericType<List<prozTest>>() {});
        assertEquals(2, content.size());
        assertEquals(1, content.get(0).getTestID());
        assertEquals("Test pierwszy", content.get(0).getTitle());
        assertEquals(2, content.get(1).getTestID());
        assertEquals("Test drugi", content.get(1).getTitle());
    }

    @Test
    public void givenStartTest_whenTestIsValid_thenTestIsReturned() {
        Response response = target("/tests/2").request().get();

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaderString(HttpHeaders.CONTENT_TYPE));

        prozTest content = response.readEntity(prozTest.class);
        assertEquals(2, content.getTestID());
        // TODO: check questions
    }

    @Test
    public void givenGenerateTest_whenTestIsNotValid_thenErrorIsThrown() {
        Response response = target("/tests/2137").request().get();
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void givenSubmitTest_whenTestIsValid_thenResponseIsOK() {
        prozTest test = target("/tests/2").request().get().readEntity(prozTest.class);

        List<String> responses = new ArrayList<>();
        responses.add("B");
        responses.add("D");
        responses.add("zielony");
        Response response = target("/tests/2/submit").request().post(Entity.entity(responses, MediaType.APPLICATION_JSON_TYPE));

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaderString(HttpHeaders.CONTENT_TYPE));

        /*TestResults content = response.readEntity(TestResults.class);
        assertEquals(prozTest.getId(), content.getprozTestId());*/
    }

    @Test
    public void givenSubmitTest_whenNoResponseIsSubmitted_thenResponseIsError() {
        prozTest test = target("/tests/2").request().get().readEntity(prozTest.class);

        Response response = target("/tests/2/submit").request().post(null);
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    public void givenSubmitTest_whenTestIsNotValid_thenResponseIsError() {
        prozTest prozTest = target("/tests/2").request().get().readEntity(prozTest.class);

        List<String> responses = new ArrayList<>();
        responses.add("B");
        responses.add("D");
        responses.add("zielony");
        Response response = target("/tests/1337/submit").request().post(Entity.entity(responses, MediaType.APPLICATION_JSON_TYPE));
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }
}
