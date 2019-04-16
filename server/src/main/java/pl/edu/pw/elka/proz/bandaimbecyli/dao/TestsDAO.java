package pl.edu.pw.elka.proz.bandaimbecyli.dao;

import pl.edu.pw.elka.proz.bandaimbecyli.models.GeneratedTest;
import pl.edu.pw.elka.proz.bandaimbecyli.models.Test;

import java.util.Collection;

public interface TestsDAO {
    Collection<Test> listAvailableTests();

    Test getTestById(int id, boolean includingQuestionsAnswers);

    void saveGeneratedTest(GeneratedTest genTest);

    GeneratedTest getGeneratedTestById(int genTestId);
}
