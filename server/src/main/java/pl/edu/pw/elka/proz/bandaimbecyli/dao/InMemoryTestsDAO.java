package pl.edu.pw.elka.proz.bandaimbecyli.dao;

import pl.edu.pw.elka.proz.bandaimbecyli.models.GeneratedTest;
import pl.edu.pw.elka.proz.bandaimbecyli.models.Test;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class InMemoryTestsDAO implements TestsDAO {
    private static final Map<Integer, Test> tests = new HashMap<>();
    private static final Map<Integer, GeneratedTest> generatedTests = new HashMap<>();
    private static int generatedTestsLastId = 0;

    static {
        tests.put(1, new Test(1, "Test pierwszy"));
        tests.put(2, new Test(2, "Test drugi"));
    }

    @Override
    public Collection<Test> listAvailableTests()
    {
        return tests.values();
    }

    @Override
    public Test getTestById(int id, boolean includingQuestionsAnswers)
    {
        return tests.get(id);
    }

    @Override
    public void saveGeneratedTest(GeneratedTest genTest) {
        if (genTest.getId() != 0)
            throw new IllegalStateException("Already saved, cannot modify");
        genTest.setId(++generatedTestsLastId);
        generatedTests.put(genTest.getId(), genTest);
    }

    @Override
    public GeneratedTest getGeneratedTestById(int genTestId) {
        return generatedTests.get(genTestId);
    }
}
