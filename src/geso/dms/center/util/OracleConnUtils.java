package geso.dms.center.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class OracleConnUtils 
{
	private Connection connection;
	private Statement statement;
	
	public OracleConnUtils()
    {  
    	connect();
    }  
    
	public boolean connect() 
	{
		try 
		{
			String hostName = "...";
		       String sid = "x";
	       String userName = "x";
	       String password = "x";
    			       
	       //connection = DriverManager.getConnection(
	    	//		"jdbc:oracle:thin:" + hostName + ":1529:core", userName, password );
	       
	        connection = getOracleConnection(hostName, sid, userName, password);
			
	        if( connection != null )
	        	System.out.println("OK");
	        else
	        	System.out.println("LOI KET NOI");
	        
	        return true;
			
		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
			System.out.print(ex.toString());
		}
		return false;
	}
	
	
	 public static Connection getOracleConnection() throws ClassNotFoundException, SQLException 
	 {
			String hostName = "210.211.121.130.1";
		       String sid = "CORE";
	       String userName = "dms";
	       String password = "dmsgroup";
	 
	       return getOracleConnection(hostName, sid, userName, password);
	   }
	 
	   public static Connection getOracleConnection(String hostName, String sid,
	           String userName, String password) throws ClassNotFoundException, SQLException 
	   {
	 
	       // Khai báo class Driver cho DB Oracle
	       // Việc này cần thiết với Java 5
	       // Java6 trở lên tự động tìm kiếm Driver thích hợp.
	       // Nếu bạn dùng Java > 6, thì ko cần dòng này cũng được.
	       Class.forName("oracle.jdbc.driver.OracleDriver");
	 
	       // Cấu trúc URL Connection dành cho Oracle
	       // Ví dụ: jdbc:oracle:thin:@localhost:1521:db11g
	       //String connectionURL = "jdbc:oracle:thin:@" + hostName + ":1524:" + sid;
	       //String connectionURL = "jdbc:oracle:thin:@" + hostName + ":1529:" + sid;
	       String connectionURL = "jdbc:oracle:thin:@" + hostName + ":1529:" + sid;
	       Connection conn = DriverManager.getConnection(connectionURL, userName, password);
	       return conn;
	   }
	   
	   public ResultSet get(String query) 
	   {
			try {

				statement = connection.createStatement();
				ResultSet rs = statement.executeQuery(query);
				
				if( rs != null )
					System.out.println("RS KHAC NULL");
				else
					System.out.println("RS LA NULL");
				
				return rs;
			} 
			catch (Exception sqle) {
				System.out.println("Loi "+query);
				sqle.printStackTrace();
				return null;
			}
		}
	   public int updateReturnInt(String query)  
		{  
			try {  
				statement = connection.createStatement();  
				return	statement.executeUpdate( query );  
			}  
			catch ( SQLException sqle ) {  
				System.out.println(sqle);
				return -1;  
			}  
		}  


	   public Connection getConnection(){
	    	return this.connection;
	    }

		public boolean update(String query)  
		{  
			try {  
				statement = connection.createStatement();  
				statement.executeUpdate( query );  


				return true;  
			}  
			catch ( SQLException sqle ) { 
				System.out.println("Lỗi "+sqle);
				return false;  
			}  
		}  
	    public boolean shutDown()  
	    {  
	    	try {  
	    		if(statement != null)
	    			statement.close();
	    		
	    		if(connection != null)
	    			connection.close();  
	    		
	    		return true;  
	    	}  
	    	catch ( SQLException sqlex ) {  
	    		return false;  
	    	}  
	    } 

}
