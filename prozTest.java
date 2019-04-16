import java.sql.*;

public class prozTest {
    public int test_id;
    public String title, start_date, end_date;
    public int type;
    public prozTest(int id, String ti, String sd, String ed, int ty)
    {
        test_id = id;
        title = ti;
        start_date = sd;
        end_date = ed;
        type = ty;
    }

    public static void main(String args[]){
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");

            Connection con=DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:xe","system","admin111");

            Statement stmt=con.createStatement();

            ResultSet rs=stmt.executeQuery("select * from test where test_id = 0");
           /* title = rs.getString("TITLE");
            start_date = rs.getString("START_DATE");
            end_date = rs.getString("END_DATE");
            type = rs.getInt("TYPE");
            System.out.println(title);*/
           System.out.println(rs.getString("TITLE"));

//step5 close the connection object
            con.close();

        }catch(Exception e){ System.out.println(e);}

    }

}
