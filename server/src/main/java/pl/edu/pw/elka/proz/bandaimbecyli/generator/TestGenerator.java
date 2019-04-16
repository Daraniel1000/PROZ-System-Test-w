package pl.edu.pw.elka.proz.bandaimbecyli.generator;

import pl.edu.pw.elka.proz.bandaimbecyli.dao.TestsDAO;
import pl.edu.pw.elka.proz.bandaimbecyli.models.AvailableTest;
import pl.edu.pw.elka.proz.bandaimbecyli.models.GeneratedTest;
import pl.edu.pw.elka.proz.bandaimbecyli.models.TestResults;

import java.util.List;

// TODO: implement
public class TestGenerator {
    private final TestsDAO dao;

    public TestGenerator(TestsDAO dao) {
        this.dao = dao;
    }

    public GeneratedTest generateTest(AvailableTest test)
    {
        GeneratedTest genTest = new GeneratedTest(test.getId(), "bla bla bla something TODO");
        dao.saveGeneratedTest(genTest);
        return genTest;
    }

    public TestResults submitAndGradeTest(AvailableTest test, GeneratedTest genTest, List<String> solutions) {
        return new TestResults(genTest.getId(), "Dobra robota, 2/10");
    }
}
