package namng.test.testDB;
import java.sql.*;

public class TestPosgreSQL {
    public static void main(String[] args) {
        String host="localhost";
        String port="5432";
        String dbname="ITSS_project";
        String dbuser="postgres";
        String dbpass="namhhbg0001";
        String dburl="jdbc:postgresql://"+host+":"+port+"/"+dbname+"?loggerLevel=OFF";
        Connection con = null;
        Statement stmt = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(dburl,dbuser,dbpass);
            stmt = con.createStatement();
            rs = stmt.executeQuery("select * from users");
            while (rs.next()){
                System.out.println(rs.getInt(1) + rs.getString(2));
            }
            rs.close();
            stmt.close();
            con.close();
        }catch(SQLException e){
            e.printStackTrace();
        }catch(Exception c){
            c.printStackTrace();
        }
        System.out.println("Hello world!");
    }
}