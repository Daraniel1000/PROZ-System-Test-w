package database_classes;

import java.sql.*;
import java.util.ArrayList;

public class prozDatabaseConnection {
    public static ArrayList<prozTest> GetUserTests() throws SQLException {
        return GetUserTests(1);
    }

    public static ArrayList<prozTest> GetUserTests(int uID) throws SQLException
    {
        ArrayList<prozTest> testList = new ArrayList<>();
        Connection databaseConn = DriverManager.getConnection("jdbc:oracle:thin:@ora3.elka.pw.edu.pl:1521:ora3inf","mkapusci","mkapusci");
        Statement stmt = databaseConn.createStatement();
        ResultSet rs = stmt.executeQuery(prozQueryGenerator.TestsForUserQuery(uID));
        while (rs.next())
        {
            testList.add(new prozTest(
                rs.getInt("TEST_ID"),
                rs.getString("TITLE"),
                rs.getString("START_DATE"),
                rs.getString("FINISH_DATE"),
                rs.getInt("TYPE")));
        }
        databaseConn.close();
        return testList;
    }

    public static void GetTestQuestions(prozTest test) throws SQLException {
        test.setQuestions(GetTestQuestions(test.getTestID()));
        return;
    }

    public static ArrayList<prozQuestion> GetTestQuestions(int tID) throws SQLException
    {
        ArrayList<prozQuestion> qList = new ArrayList<>();
        Connection databaseConn = DriverManager.getConnection("jdbc:oracle:thin:@ora3.elka.pw.edu.pl:1521:ora3inf","mkapusci","mkapusci");
        Statement stmt = databaseConn.createStatement();
        ResultSet rs = stmt.executeQuery(prozQueryGenerator.QuestionsForTestQuery(tID));
        while (rs.next())
        {
            qList.add(new prozQuestion(
                    rs.getInt("QUESTION_ID"),
                    rs.getInt("TYPE"),
                    rs.getString("TEXT")));
        }
        databaseConn.close();
        return qList;
    }

    public static void GetQuestionAnswers(prozQuestion question) throws SQLException {
        question.setAnswers(GetQuestionAnswers(question.getQuestionID()));
        return;
    }

    public static ArrayList<prozAnswer> GetQuestionAnswers(int qID) throws SQLException
    {
        ArrayList<prozAnswer> aList = new ArrayList<>();
        Connection databaseConn = DriverManager.getConnection("jdbc:oracle:thin:@ora3.elka.pw.edu.pl:1521:ora3inf","mkapusci","mkapusci");
        Statement stmt = databaseConn.createStatement();
        ResultSet rs = stmt.executeQuery(prozQueryGenerator.AnswersForQuestionQuery(qID));
        while (rs.next())
        {
            aList.add(new prozAnswer(
                    rs.getInt("ANSWER_ID"),
                    rs.getBoolean("CORRECT"),
                    rs.getString("TEXT")));
        }
        databaseConn.close();
        return aList;
    }

    public static prozTest FillTest(int tID) throws SQLException {
        Connection databaseConn = DriverManager.getConnection("jdbc:oracle:thin:@ora3.elka.pw.edu.pl:1521:ora3inf","mkapusci","mkapusci");
        Statement stmt = databaseConn.createStatement();
        ResultSet rs = stmt.executeQuery(prozQueryGenerator.GetTestQuery(tID));
        prozTest test = new prozTest(
                rs.getInt("TEST_ID"),
                rs.getString("TITLE"),
                rs.getString("START_DATE"),
                rs.getString("FINISH_DATE"),
                rs.getInt("TYPE"));
        databaseConn.close();
        FillTest(test);
        return test;
    }

    public static void FillTest(prozTest test) throws SQLException
    {
        GetTestQuestions(test);
        for (int i=0; i<test.getQuestionsSize(); ++i)
        {
            GetQuestionAnswers(test.getQuestion(i));
        }
    }
}
