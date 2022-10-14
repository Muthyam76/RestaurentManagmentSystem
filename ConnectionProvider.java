package restaurentManagement;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionProvider {
	public static Connection getCon()
	{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			String db="jdbc:mysql://localhost:3306/project";
			String u="root";
			String p="muthyam";
			Connection con=DriverManager.getConnection(db,u,p);
			return con;
		}
		catch(Exception e)
		{
			//throw new RuntimeException
			return null;
		}
	}

}
