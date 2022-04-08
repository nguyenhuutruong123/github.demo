package geso.dms.center.servlets.reports;

import geso.dms.center.util.Utility;

import java.io.*;
import java.io.IOException;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//import jxl.Workbook;
//import jxl.WorkbookSettings;
//import jxl.write.WritableWorkbook;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.Font;
import com.aspose.cells.PivotFieldDataDisplayFormat;
import com.aspose.cells.PivotFieldType;
import com.aspose.cells.PivotTable;
import com.aspose.cells.PivotTables;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;
public class XuatfileSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
       public XuatfileSvl() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		   request.setCharacterEncoding("UTF-8");
		   response.setCharacterEncoding("UTF-8");
		   response.setContentType("text/html; charset=UTF-8");
//		    PrintWriter out = response.getWriter();
		    	    
		   HttpSession session = request.getSession();	    
 	       Utility util = new Utility();
			
	       String querystring = request.getQueryString();
		   String rp = util.getUserId(querystring).split(";")[1];
		   String userId = util.getUserId(querystring).split(";")[1];
		    
	    		
	    	File file = new File("D:\\DMS\\Best\\WebContent\\pages\\Reports\\" + rp);
	    	long fileLength = file.length();
	    	System.out.println("file size:" + fileLength);
	    		
	    	int offset = 0;
			response.setHeader("Content-length", "" + fileLength);
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition","attachment; filename=" + rp);
			FileInputStream inputStream = new FileInputStream("D:\\DMS\\Best\\WebContent\\pages\\Reports\\" + rp);
			try
			    {
			    	
			        byte[] buffer = new byte[1024];
			        int bytesRead = 0;

			        do
			        {
			        		if ((buffer.length + offset) <= fileLength){
			        			System.out.println("Offset = " + (buffer.length + offset));	
			        			bytesRead = inputStream.read(buffer, 0, buffer.length);
			        		}else{
			        			int tmp = (int)(fileLength-offset);
			        			bytesRead = inputStream.read(buffer, 0, tmp );
			        		}
			                response.getOutputStream().write(buffer, 0, bytesRead);
			                
			                offset = offset + 1024;
			                
			        }
			        while (bytesRead == buffer.length);

			        response.getOutputStream().flush();
			    }
			    finally
			    {
			        if(inputStream != null)
			                inputStream.close();
			    }
			    
	}
	
	String file(String st)
	{ String str1 = st;
		String[] strSplit = str1.split("");
         String b = strSplit[0];
		return b;
	}
 	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
