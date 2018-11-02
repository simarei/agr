package dev.rei.agr.util;
import java.sql.*;
public class DBManager {
public static void main(String[] args) throws SQLException {
Connection con = getConnection();
System.out.println("con = "+con);
con.close();
}
private static String driverNm = "com.mysql.jdbc.Driver";
private static String url = "jdbc:mysql://localhost/agri";
private static String user = "agri";
private static String pass = "agri";

public static Connection getConnection() {
Connection con = null;
try {
Class.forName(driverNm);
con = DriverManager.getConnection(url,user,pass);
} catch (ClassNotFoundException e) {
e.printStackTrace();
} catch (SQLException e) {
e.printStackTrace();
}

return con;
}

}