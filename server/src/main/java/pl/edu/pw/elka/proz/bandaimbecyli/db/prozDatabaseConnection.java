package pl.edu.pw.elka.proz.bandaimbecyli.db;

import pl.edu.pw.elka.proz.bandaimbecyli.models.*;
import pl.edu.pw.elka.proz.bandaimbecyli.models.prozUser;

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
    public prozUser CheckUserLogin(String username, String password) throws SQLException
    {
        checkConnection();
        PreparedStatement stmt = databaseConn.prepareStatement(prozQueryGenerator.CheckUserQuery());
        stmt.setString(1, username);
        stmt.setString(2, password);
        ResultSet rs = stmt.executeQuery();
        while(rs.next())
        {
            prozUser user = new prozUser(
                    rs.getInt("USER_ID"),
                    rs.getString("LOGIN"),
                    rs.getString("FIRST_NAME"),
                    rs.getString("LAST_NAME"),
                    rs.getBoolean("ADMIN")
            );
            rs.close();
            return user;
        }
        rs.close();
        return null;
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
                rs.getBoolean("isComplete")));
        }
        rs.close();
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
        rs.close();
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
        rs.close();
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
            rs.close();
            return test;
        }
        rs.close();
        return null;
    }

    @Override
    public void SendResults(prozResults Results) throws SQLException
    {
        checkConnection();
        PreparedStatement preparedStmt = databaseConn.prepareStatement(prozQueryGenerator.InsertResultsQuery(), new String[] { "RESULTS_ID" });
        preparedStmt.setInt(1, Results.getTestID());
        preparedStmt.setInt(2, Results.getUserID());
        preparedStmt.setTimestamp(3, Results.getSentDate());
        preparedStmt.setInt(4, Results.getPoints());
        preparedStmt.execute();
        ResultSet rs = preparedStmt.getGeneratedKeys();
        rs.next();
        Results.setResultsID(rs.getInt(1));
        preparedStmt.close();
        for(int i=0; i<Results.getAnswerIDSize(); ++i)
        {
            preparedStmt = databaseConn.prepareStatement(prozQueryGenerator.InsertResultsAnswersQuery());
            preparedStmt.setInt(1, Results.getAnswerID(i));
            preparedStmt.setInt(2, Results.getResultsID());
            preparedStmt.execute();
            preparedStmt.close();
        }
    }

    public Boolean isTestDoneByUser(int tID, int uID) throws SQLException
    {
        checkConnection();
        Statement stmt = databaseConn.createStatement();
        ResultSet rs = stmt.executeQuery(prozQueryGenerator.ResultsForUserTestQuery(tID, uID));
        Boolean b = rs.next();
        rs.close();
        return b;
    }

    @Override
    public prozResults GetResults(int tID, int uID) throws SQLException
    {
        checkConnection();
        Statement stmt = databaseConn.createStatement();
        ResultSet rs = stmt.executeQuery(prozQueryGenerator.ResultsForUserTestQuery(tID, uID));
        while (rs.next())
        {
            prozResults Results = new prozResults(
                    rs.getInt("RESULTS_ID"),
                    tID,
                    uID,
                    rs.getTimestamp("SENT_DATE"),
                    rs.getInt("POINTS")
            );
            Results.initAnswers(rs.getFetchSize());
            stmt.close();
            stmt = databaseConn.createStatement();
            rs.close();
            rs = stmt.executeQuery(prozQueryGenerator.AnswerIDsForResultsQuery(Results.getResultsID()));
            while(rs.next())
            {
                Results.addAnswerID(rs.getInt("ANSWER_ID"));
            }
            rs.close();
            return Results;
        }
        rs.close();
        return null;
    }

    @Override
    public void addQuestion(prozQuestion question) throws SQLException
    {
        checkConnection();
        //char c;
        PreparedStatement preparedStmt = databaseConn.prepareStatement(prozQueryGenerator.InsertQuestionQuery(), new String[] { "QUESTION_ID" });
        preparedStmt.setString(1, question.getText());
        preparedStmt.setInt(2, question.getType());
        preparedStmt.execute();
        ResultSet rs = preparedStmt.getGeneratedKeys();
        rs.next();
        question.setQuestionID(rs.getInt(1));
        rs.close();
        preparedStmt.close();
        for(int i=0; i<question.getAnswersSize(); ++i)
        {
            //c = question.getAnswer(i).isCorrect()? 'y':'n';
            preparedStmt = databaseConn.prepareStatement(prozQueryGenerator.insertAnswerQuery());
            preparedStmt.setString(1, question.getAnswer(i).getText());
            preparedStmt.setInt(2, question.getQuestionID());
            preparedStmt.setBoolean(3,question.getAnswer(i).isCorrect());
            preparedStmt.execute();
            preparedStmt.close();
        }
    }

    @Override
    public void addTest(prozTest test) throws SQLException
    {
        checkConnection();
        PreparedStatement preparedStmt = databaseConn.prepareStatement(prozQueryGenerator.InsertTestQuery(), new String[] { "TEST_ID" });
        preparedStmt.setString(1, test.getTitle());
        preparedStmt.setTimestamp(2, test.getStartDate());
        preparedStmt.setTimestamp(3, test.getEndDate());
        preparedStmt.setInt(4,test.getType());
        preparedStmt.execute();
        ResultSet rs = preparedStmt.getGeneratedKeys();
        rs.next();
        test.setTestID(rs.getInt(1));
        rs.close();
        preparedStmt.close();
        for(int i=0; i<test.getQuestionsSize(); ++i)
        {
            preparedStmt = databaseConn.prepareStatement(prozQueryGenerator.insertTestQuestionsQuery());
            preparedStmt.setInt(1, test.getQuestion(i).getQuestionID());
            preparedStmt.setInt(2, test.getTestID());
            preparedStmt.execute();
            preparedStmt.close();
        }
    }

    @Override
    public ArrayList<prozQuestion> getAllQuestions() throws SQLException
    {
        checkConnection();
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

    @Override
    public ArrayList<prozTest> getAllTests() throws SQLException
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

    @Override
    public prozQuestion GetQuestion(int questionId) throws SQLException {
        Statement stmt = databaseConn.createStatement();
        ResultSet rs = stmt.executeQuery(prozQueryGenerator.GetQuestionQuery(questionId));
        while (rs.next())
        {
            prozQuestion q = new prozQuestion(
                    rs.getInt("QUESTION_ID"),
                    rs.getInt("TYPE"),
                    rs.getString("TEXT"));
            rs.close();
            return q;
        }
        rs.close();
        return null;
    }

    @Override
    public ArrayList<prozUser> getAllUsers() throws SQLException
    {
        checkConnection();
        ArrayList<prozUser> uList;
        Statement stmt = databaseConn.createStatement();
        ResultSet rs = stmt.executeQuery("select * from Users");
        uList = new ArrayList<>(rs.getFetchSize());
        while(rs.next())
        {
            uList.add(new prozUser(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getBoolean(6)
            ));
        }
        rs.close();
        return uList;
    }

    @Override
    public void addUserToTest(int uID, int tID) throws SQLException
    {
        PreparedStatement preparedStmt;
        preparedStmt = databaseConn.prepareStatement(prozQueryGenerator.InsertUsersTestsQuery());
        preparedStmt.setInt(1, uID);
        preparedStmt.setInt(2, tID);
        preparedStmt.execute();
        preparedStmt.close();
    }

}
