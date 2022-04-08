package geso.dms.center.servlets.util;

import geso.dms.center.beans.thongbao.IThongbao;
import geso.dms.center.beans.thongbao.IThongbaoList;
import geso.dms.center.beans.thongbao.imp.Thongbao;
import geso.dms.center.beans.thongbao.imp.ThongbaoList;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;

import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Error500Svl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Error500Svl() {
        super();
    }
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String newJSP = "/AHF/Error500.jsp";
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");	    	    
		response.sendRedirect(newJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
	}

}
