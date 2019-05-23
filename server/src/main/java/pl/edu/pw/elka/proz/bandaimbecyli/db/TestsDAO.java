package pl.edu.pw.elka.proz.bandaimbecyli.db;

import pl.edu.pw.elka.proz.bandaimbecyli.models.prozQuestion;
import pl.edu.pw.elka.proz.bandaimbecyli.models.prozResults;
import pl.edu.pw.elka.proz.bandaimbecyli.models.prozTest;

import java.sql.SQLException;
import java.util.List;

public interface TestsDAO {
    int CheckUserLogin(String username, String password) throws SQLException; // zwraca id jeśli dobrze i -1 jeśli źle

    List<prozTest> GetTestsAvailableForUser(int uID) throws SQLException;

    prozTest GetTest(int tID) throws SQLException;

    void FillTestQuestions(prozTest test) throws SQLException;

    void FillQuestionAnswers(prozQuestion question) throws SQLException;

    void SendResults(prozResults Results) throws SQLException;
}
