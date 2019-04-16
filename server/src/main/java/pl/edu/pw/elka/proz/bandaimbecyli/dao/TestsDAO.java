package pl.edu.pw.elka.proz.bandaimbecyli.dao;

import pl.edu.pw.elka.proz.bandaimbecyli.models.AvailableTest;
import pl.edu.pw.elka.proz.bandaimbecyli.models.GeneratedTest;

import java.util.Collection;

public interface TestsDAO {
    Collection<AvailableTest> listAvailableTests();

    AvailableTest getTestById(int id, boolean includingQuestionsAnswers);

    void saveGeneratedTest(GeneratedTest genTest);

    GeneratedTest getGeneratedTestById(int genTestId);
}
