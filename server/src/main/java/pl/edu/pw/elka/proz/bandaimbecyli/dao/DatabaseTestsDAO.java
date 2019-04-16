package pl.edu.pw.elka.proz.bandaimbecyli.dao;

import pl.edu.pw.elka.proz.bandaimbecyli.models.GeneratedTest;
import pl.edu.pw.elka.proz.bandaimbecyli.models.Test;

import java.util.Collection;

public class DatabaseTestsDAO implements TestsDAO {
    // TODO: Implement database

    @Override
    public Collection<Test> listAvailableTests() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Test getTestById(int id, boolean includingQuestionsAnswers) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void saveGeneratedTest(GeneratedTest genTest) {
        throw new UnsupportedOperationException();
    }

    @Override
    public GeneratedTest getGeneratedTestById(int genTestId) {
        throw new UnsupportedOperationException();
    }
}
