package pl.edu.pw.elka.proz.bandaimbecyli.db;

import pl.edu.pw.elka.proz.bandaimbecyli.models.prozAnswer;
import pl.edu.pw.elka.proz.bandaimbecyli.models.prozQuestion;
import pl.edu.pw.elka.proz.bandaimbecyli.models.prozResults;
import pl.edu.pw.elka.proz.bandaimbecyli.models.prozTest;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class prozDatabaseConnection implements TestsDAO {
    private Connection databaseConn;

    private boolean testConnection() throws SQLException {
        if (databaseConn == null)
            return false;

        try {
            Statement stmt = databaseConn.createStatement();
            stmt.executeQuery("select 1 from dual");
            return true;
        }
        catch(SQLRecoverableException ex) {
            return false;
        }
    }

    private void checkConnection() throws SQLException {
        if (!testConnection())
            databaseConn = DriverManager.getConnection("jdbc:oracle:thin:@ora3.elka.pw.edu.pl:1521:ora3inf","mkapusci","mkapusci");
    }

    @Override
    public int CheckUserLogin(String username, String password) throws SQLException
    {
        checkConnection();
        PreparedStatement stmt = databaseConn.prepareStatement(prozQueryGenerator.CheckUserQuery());
        stmt.setString(1, username);
        stmt.setString(2, password);
        ResultSet rs = stmt.executeQuery();
        while(rs.next())
        {
            return rs.getInt("USER_ID");
        }
        return -1;
    }

    @Override
    public List<prozTest> GetTestsAvailableForUser(int uID) throws SQLException
    {
        checkConnection();
        ArrayList<prozTest> testList = new ArrayList<>();
        Statement stmt = databaseConn.createStatement();
        ResultSet rs = stmt.executeQuery(prozQueryGenerator.TestsForUserQuery(uID));
        while (rs.next())
        {
            testList.add(new prozTest(
                rs.getInt("TEST_ID"),
                rs.getString("TITLE"),
                rs.getTimestamp("START_DATE"),
                rs.getTimestamp("FINISH_DATE"),
                rs.getInt("TYPE"),
                isTestDoneByUser(rs.getInt("TEST_ID"), uID))); // TODO: dlaczego to jest w oddzielnym zapytaniu
        }
        return testList;
    }

    @Override
    public void FillTestQuestions(prozTest test) throws SQLException {
        test.setQuestions(GetTestQuestions(test.getTestID()));
    }

    private ArrayList<prozQuestion> GetTestQuestions(int tID) throws SQLException
    {
        checkConnection();
        ArrayList<prozQuestion> qList = new ArrayList<>();
        Statement stmt = databaseConn.createStatement();
        ResultSet rs = stmt.executeQuery(prozQueryGenerator.QuestionsForTestQuery(tID));
        while (rs.next())
        {
            qList.add(new prozQuestion(
                    rs.getInt("QUESTION_ID"),
                    rs.getInt("TYPE"),
                    rs.getString("TEXT")));
        }
        return qList;
    }

    @Override
    public void FillQuestionAnswers(prozQuestion question) throws SQLException {
        question.setAnswers(GetQuestionAnswers(question.getQuestionID()));
    }

    private ArrayList<prozAnswer> GetQuestionAnswers(int qID) throws SQLException
    {
        checkConnection();
        ArrayList<prozAnswer> aList = new ArrayList<>();
        Statement stmt = databaseConn.createStatement();
        ResultSet rs = stmt.executeQuery(prozQueryGenerator.AnswersForQuestionQuery(qID));
        while (rs.next())
        {
            aList.add(new prozAnswer(
                    rs.getInt("ANSWER_ID"),
                    rs.getBoolean("CORRECT"),
                    rs.getString("TEXT")));
        }
        return aList;
    }

    @Override
    public prozTest GetTest(int tID) throws SQLException {
        checkConnection();
        Statement stmt = databaseConn.createStatement();
        ResultSet rs = stmt.executeQuery(prozQueryGenerator.GetTestQuery(tID));
        while (rs.next()) {
            prozTest test = new prozTest(
                    rs.getInt("TEST_ID"),
                    rs.getString("TITLE"),
                    rs.getTimestamp("START_DATE"),
                    rs.getTimestamp("FINISH_DATE"),
                    rs.getInt("TYPE"));
            return test;
        }
        return null;
    }

    @Override
    public void SendResults(prozResults Results) throws SQLException
    {
        checkConnection();
        PreparedStatement preparedStmt = databaseConn.prepareStatement(prozQueryGenerator.InsertResultsQuery());
        preparedStmt.setInt(1, Results.getTestID());
        preparedStmt.setInt(2, Results.getUserID());
        preparedStmt.setTimestamp(3, Results.getSentDate());
        preparedStmt.setInt(4, Results.getPoints());
        preparedStmt.execute();
        //Statement stmt = databaseConn.createStatement();
        //ResultSet rs = stmt.executeQuery(prozQueryGenerator.ResultsForUserTestQuery(Results.getTestID(),Results.getUserID())); // TODO: brzydkie xd
        ResultSet rs = preparedStmt.getGeneratedKeys();
        rs.next();
        Results.setResultsID(rs.getInt(1));
        for(int i=0; i<Results.getAnswerIDSize(); ++i)
        {
            preparedStmt = databaseConn.prepareStatement(pl.edu.pw.elka.proz.bandaimbecyli.db.prozQueryGenerator.InsertResultsAnswersQuery());
            preparedStmt.setInt(1, Results.getAnswerID(i));
            preparedStmt.setInt(2, Results.getResultsID());
            preparedStmt.execute();
        }
    }

    public Boolean isTestDoneByUser(int tID, int uID) throws SQLException
    {
        checkConnection();
        Statement stmt = databaseConn.createStatement();
        ResultSet rs = stmt.executeQuery(prozQueryGenerator.ResultsForUserTestQuery(tID, uID));
        return rs.next();
    }

    @Override
    public prozResults GetResults(int tID, int uID) throws SQLException
    {
        checkConnection();
        Statement stmt = databaseConn.createStatement();
        ResultSet rs = stmt.executeQuery(pl.edu.pw.elka.proz.bandaimbecyli.db.prozQueryGenerator.ResultsForUserTestQuery(tID, uID));
        while (rs.next())
        {
            prozResults Results = new prozResults(
                    rs.getInt("RESULTS_ID"),
                    tID,
                    uID,
                    rs.getTimestamp("SENT_DATE"),
                    rs.getInt("POINTS")
            );
            stmt.close();
            stmt = databaseConn.createStatement();
            rs.close();
            rs = stmt.executeQuery(pl.edu.pw.elka.proz.bandaimbecyli.db.prozQueryGenerator.AnswerIDsForResultsQuery(Results.getResultsID()));
            Results.initAnswers(rs.getFetchSize());
            while(rs.next())
            {
                Results.addAnswerID(rs.getInt("ANSWER_ID"));
            }
            rs.close();
            return Results;
        }
        return null;
    }

    private void addQuestion(prozQuestion question) throws SQLException
    {
        //TODO
    }

    private void addTest(prozTest test) throws SQLException
    {
        //TODO
    }

    private ArrayList<prozQuestion> getAllQuestions() throws SQLException
    {
        ArrayList<prozQuestion> qList;
        Statement stmt = databaseConn.createStatement();
        ResultSet rs = stmt.executeQuery("select * from Question");
        qList = new ArrayList<>(rs.getFetchSize());
        while(rs.next())
        {
            qList.add(new prozQuestion(
                    rs.getInt(1),
                    rs.getInt(3),
                    rs.getString(2)
            ));
        }
        rs.close();
        return qList;
    }

    private ArrayList<prozTest> getAllTests() throws SQLException
    {
        ArrayList<prozTest> tList;
        Statement stmt = databaseConn.createStatement();
        ResultSet rs = stmt.executeQuery("select * from Test");
        tList = new ArrayList<>(rs.getFetchSize());
        while(rs.next())
        {
            tList.add(new prozTest(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getTimestamp(3),
                    rs.getTimestamp(4),
                    rs.getInt(5)
            ));
        }
        rs.close();
        return tList;
    }
}
