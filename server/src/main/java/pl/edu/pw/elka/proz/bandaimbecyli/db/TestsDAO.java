package pl.edu.pw.elka.proz.bandaimbecyli.db;

import pl.edu.pw.elka.proz.bandaimbecyli.models.prozQuestion;
import pl.edu.pw.elka.proz.bandaimbecyli.models.prozResults;
import pl.edu.pw.elka.proz.bandaimbecyli.models.prozTest;
import pl.edu.pw.elka.proz.bandaimbecyli.models.prozUser;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface TestsDAO {
    prozUser CheckUserLogin(String username, String password) throws SQLException; // zwraca null jeśli źle

    List<prozTest> GetTestsAvailableForUser(int uID) throws SQLException;

    prozTest GetTest(int tID) throws SQLException;

    void FillTestQuestions(prozTest test) throws SQLException;

    void FillQuestionAnswers(prozQuestion question) throws SQLException;

    void SendResults(prozResults Results) throws SQLException;

    prozResults GetResults(int tID, int uID) throws SQLException;

    ArrayList<prozTest> getAllTests() throws SQLException;

    ArrayList<prozQuestion> getAllQuestions() throws SQLException;

    void addTest(prozTest test) throws SQLException;

    void addQuestion(prozQuestion question) throws SQLException;

    prozQuestion GetQuestion(int questionId) throws SQLException;

    ArrayList<prozUser> getAllUsers() throws SQLException;

    void addUserToTest(int uID, int tID) throws SQLException;

    ArrayList<prozResults> GetResultsForTest(int tID) throws SQLException;
}
