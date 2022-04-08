<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import = "java.io.*" %>
    <%@ page import = "java.sql.*" %>  
    <%@ page import = "java.lang.String" %>
    <%@ page import = "java.util.ArrayList" %>
    <%@ page import = "java.util.List" %>
    <%@ page import = "java.io.Serializable" %>
    <%@ page import = "java.lang.*" %>
    <%@ page import = "oracle.jdbc.pool.*" %>
    

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%
DriverManager.registerDriver((Driver)Class.forName("oracle.jdbc.driver.OracleDriver").newInstance());

int i = 0;
while(i<500){
	System.out.println("Connection " +(i++));	
	Connection c=null;
	while(c==null)
	{
		try
		{
		  c = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "BEST", "b3stv13tn@m");
			/*OracleDataSource ods = new OracleDataSource();
			ods.setUser("BEST");
			ods.setPassword("b3stv13tn@m");
			ods.setURL("jdbc:oracle:thin:@localhost:1521:XE");
			ods.setConnectionCachingEnabled(true);
			c= ods.getConnection();*/
		}
		catch(SQLException x)
		{
		  if(x.getMessage().indexOf("ORA-12519")!=-1)
		  {
		    //sleep
		    System.out.println("Sleeping...");
		    Thread.sleep(500);
		  }
		  else
		  {
		    throw x;
		  }
		}
	}
	Statement stm=c.createStatement();
	stm.execute("select * from nhanvien");
	stm.close();
	c.close();
	Thread.sleep(250);
}
/*while (true)
{
	System.out.println("Connection "+(i++));
	Connection c=null;
	/*while(c==null)
	{
		try
		{
		  c = DriverManager.getConnection(
		      "jdbc:oracle:thin:@localhost:1521:XE", "admercucio", "q");
		}
		catch(SQLException x)
		{
		  if(x.getMessage().indexOf("ORA-12519")!=-1)
		  {
		    //sleep for a while
		    System.out.println("Sleeping...");
		    Thread.sleep(500);
		  }
		  else
		  {
		    throw x;
		  }
		}
	}

	Statement stm=c.createStatement();
	stm.execute("select * from dual");
	stm.close();
	
	//Thread.sleep(250);
	
}*/

%>
</body>
</html>