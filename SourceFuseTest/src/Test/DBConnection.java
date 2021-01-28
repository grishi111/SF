package Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DBConnection {
	static Connection con = null;
	static Statement stmt;
	public static String DB_URL = "jdbc:mysql://localhost/Testdata";
	public static String DB_USER = "dbuser";
	public static String DB_PASSWORD = "dbUserPassword";
	
	public static void SetDBConnection() {
	
		try{
		
		String dbClass = "com.mysql.cj.jdbc.Driver";
		Class.forName(dbClass).newInstance();
		Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
		stmt = con.createStatement();
		}
		catch (Exception e)
		{
		e.printStackTrace();
		}
	}
	public static void CloseDBConncetion(){
			if (con != null) {
				try{
				con.close();
				}catch (Exception e)
				{
					e.printStackTrace();
				}
			}
	}
}