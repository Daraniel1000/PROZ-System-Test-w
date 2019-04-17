package pl.edu.pw.elka.proz.bandaimbecyli.dao;

import pl.edu.pw.elka.proz.bandaimbecyli.models.GeneratedTest;
import pl.edu.pw.elka.proz.bandaimbecyli.models.AvailableTest;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DatabaseTestsDAO implements TestsDAO {
    private final Connection databaseConn;

    public DatabaseTestsDAO() throws SQLException {
         databaseConn = DriverManager.getConnection("jdbc:oracle:thin:@ora3.elka.pw.edu.pl:1521:ora3inf","mkapusci","mkapusci");
    }

    @Override
    public Collection<AvailableTest> listAvailableTests() {
        try {
            Statement stmt = databaseConn.createStatement();

            ResultSet rs = stmt.executeQuery("select * from test");

            List<AvailableTest> list = new ArrayList<>();
            while (rs.next()) {
                list.add(new AvailableTest(
                        rs.getInt("TEST_ID"),
                        rs.getString("TITLE"),
                        rs.getDate("START_DATE"),
                        rs.getDate("FINISH_DATE")));
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return null; // TODO
        }
    }

    @Override
    public AvailableTest getTestById(int id, boolean includingQuestionsAnswers) {
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
