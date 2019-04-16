package pl.edu.pw.elka.proz.bandaimbecyli.dao;

import pl.edu.pw.elka.proz.bandaimbecyli.models.AvailableTest;
import pl.edu.pw.elka.proz.bandaimbecyli.models.GeneratedTest;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class InMemoryTestsDAO implements TestsDAO {
    private static final Map<Integer, AvailableTest> tests = new HashMap<>();
    private static final Map<Integer, GeneratedTest> generatedTests = new HashMap<>();
    private static int generatedTestsLastId = 0;

    public InMemoryTestsDAO() {
    }

    public InMemoryTestsDAO(Collection<AvailableTest> tsts) {
        setAvailableTests(tsts);
    }

    private void setAvailableTests(Collection<AvailableTest> tsts)
    {
        tests.clear();
        for (AvailableTest t : tsts) {
            tests.put(t.getId(), t);
        }
    }

    @Override
    public Collection<AvailableTest> listAvailableTests()
    {
        return tests.values();
    }

    @Override
    public AvailableTest getTestById(int id, boolean includingQuestionsAnswers)
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
