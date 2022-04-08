<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%
	//thay bang cac gia tri lay tu ResualSet
	String ids[] = {"1010112340145", "1010112340146", "1010112340147", "1010112340148", "1010112340149", "1010112340150" };
	
	String query = (String)geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("q"));
	//System.out.println("1"+request.getParameterNames().nextElement());
	response.setHeader("Content-Type", "text/html");
	int cnt=1;
	for(int i=0;i<ids.length;i++)
	{
		if(ids[i].toUpperCase().startsWith(query.toUpperCase()))
		{
			out.print(ids[i]+ "-Viet Nam\n");
			if(cnt >= 10)
				break;
			cnt++;
		}
	}
%>