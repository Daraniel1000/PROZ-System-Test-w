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
}
