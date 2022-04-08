package geso.dms.center.servlets.SAP_SO_REJECT;

import geso.dms.center.beans.report.IBcHoaDon;
import geso.dms.center.beans.report.imp.BcHoaDon;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.FileFormatType;
import com.aspose.cells.Workbook;


@WebServlet("/SAP_SO_REJECT")
public class SAP_SO_REJECT extends HttpServlet {
	private static final long serialVersionUID = 1L;


	public SAP_SO_REJECT() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
   	{
   		IBcHoaDon obj;
   		request.setCharacterEncoding("UTF-8");
   		response.setCharacterEncoding("UTF-8");
   		response.setContentType("text/html; charset=UTF-8");

   		HttpSession session = request.getSession();

   		Utility util = new Utility();

   		String querystring = request.getQueryString();
   		String userId = util.getUserId(querystring);

   		if (userId.length() == 0)
   			userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));

   		String loaihoadon = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("loaihoadon"));
   		if (loaihoadon == null)
   			loaihoadon = "0";

   		obj = new BcHoaDon();
   		obj.setUserId(userId);
   		obj.setView("TT");
   		String nextJSP = "";
   		
   	String query=
   			" SELECT [DMS_PO_Number]   "+
   					"      ,[So_Number] "+
   					"      ,[SoStatus] "+
   					"     ,[Status] "+
   					"     ,[Created_Date] "+
   					"      ,[NgayReject],[DMS_DonDatHang_FK] "+
   					"  FROM [DMSSAP].[dbo].[SAP_SO_REJECT] "+
   					"  where 1=1 ";
   		
   		obj.init(query,"DMS_PO_Number desc,Created_Date");

   		nextJSP = "/AHF/pages/Center/SAP_SO_REJECT.jsp";
   		session.setAttribute("obj", obj);
   		response.sendRedirect(nextJSP);

   	}

   	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
   	{

   		request.setCharacterEncoding("UTF-8");
   		response.setCharacterEncoding("UTF-8");
   		response.setContentType("text/html; charset=UTF-8");

   		Utility util = new Utility();
   		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
   		HttpSession session = request.getSession();

   		OutputStream out = response.getOutputStream();

   		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
   		if (action == null)
   			action = "";

   		IBcHoaDon obj = new BcHoaDon();
   		obj.setUserId(userId);

   		
   		String query=
		" SELECT [DMS_PO_Number]   "+
		"      ,[So_Number] "+
		"      ,[SoStatus] "+
		"     ,[Status] "+
		"     ,[Created_Date] "+
		"      ,[NgayReject],[DMS_DonDatHang_FK] "+
		"  FROM [DMSSAP].[dbo].[SAP_SO_REJECT] "+
		"  where 1=1 ";
   					
   		
   		
   		obj.setView("TT");

   		String tungay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Sdays") == null ? "" : request.getParameter("Sdays"));
   		obj.setTuNgay(tungay);

   		String denngay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Edays") == null ? "" : request.getParameter("Edays"));
   		obj.setDenNgay(denngay);

   		String vungId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId") == null ? "" : request.getParameter("vungId"));
   		obj.setVungId(vungId);

   		String kbhId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kbhId") == null ? "" : request.getParameter("kbhId"));
   		obj.setKbhId(kbhId);

   		String ttId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ttId") == null ? "" : request.getParameter("ttId"));
   		obj.setTtId(ttId);

   		String nhomId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nhomId") == null ? "" : request.getParameter("nhomId"));
   		obj.setNhomId(nhomId);

   		String khId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khId") == null ? "" : request.getParameter("khId"));
   		obj.setKhId(khId);

   		String ddkdId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ddkdId") == null ? "" : request.getParameter("ddkdId"));
   		obj.setDdkdId(ddkdId);

   		String spId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("spId") == null ? "" : request.getParameter("spId"));
   		obj.setSpId(spId);

   		String nppId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId") == null ? "" : request.getParameter("nppId"));
   		obj.setNppId(nppId);
   		
   		
   		String soId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("soId") == null ? "" : request.getParameter("soId"));
   		obj.setSoId(soId);

   		
   		String hoadon = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("hoadon") == null ? "" : request.getParameter("hoadon"));
   		obj.setSohoadon(hoadon);
   		
   		
   		String InvoiceId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("InvoiceId") == null ? "" : request.getParameter("InvoiceId"));
   		obj.setInvoiceId(InvoiceId);
   		
   		
   		String Invoice_RevertFor = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Invoice_RevertFor") == null ? "" : request.getParameter("Invoice_RevertFor"));
   		obj.setInvoice_RevertFor(Invoice_RevertFor);
   		
   		
   		String LegalNumber = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("LegalNumber") == null ? "" : request.getParameter("LegalNumber"));
   		obj.setLegalNumber(LegalNumber);
   		
   		
   		String CustomerId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("CustomerId") == null ? "" : request.getParameter("CustomerId"));
   		obj.setCustomerId(CustomerId);
   		
   		
   		String CustomerName = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("CustomerName") == null ? "" : request.getParameter("CustomerName"));
   		obj.setCustomerName(CustomerName);
   		
   		
   		
   		String InVoiceDate = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("InVoiceDate") == null ? "" : request.getParameter("InVoiceDate"));
   		obj.setInVoiceDate(InVoiceDate);
   		
   		
   		
   		String InvoiceType = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("InvoiceType") == null ? "" : request.getParameter("InvoiceType"));
   		obj.setInvoiceType(InvoiceType);
   		
   		
   		String SO_Number = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("SO_Number") == null ? "" : request.getParameter("SO_Number"));
   		obj.setSO_Number(SO_Number);
   		
   		String PO_Number = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("PO_Number") == null ? "" : request.getParameter("PO_Number"));
   		obj.setPO_Number(PO_Number);
   		
 

   		if(InvoiceId.length()>0)
   		{
   			query+=" and InvoiceId  like '%"+InvoiceId+"%' "		;
   		}
   		
   		if(Invoice_RevertFor.length()>0)
   		{
   			query+=" and Invoice_RevertFor  like '%"+Invoice_RevertFor+"%' "		;
   		}
   		
   		
   		if(LegalNumber.length()>0)
   		{
   			query+=" and LegalNumber  like '%"+LegalNumber+"%' "		;
   		}
   	
   		if(CustomerId.length()>0)
   		{
   			query+=" and CustomerId  like '%"+CustomerId+"%' "		;
   		}
   		
   		
   		
   		
   		if(CustomerName.length()>0)
   		{
   			query+=" and CustomerName  like '%"+CustomerName+"%' "		;
   		}
   		
   		
   		
   		if(Invoice_RevertFor.length()>0)
   		{
   			query+=" and Invoice_RevertFor  like '%"+Invoice_RevertFor+"%' "		;
   		}
   		
   		
   		
   		if(SO_Number.length()>0)
   		{
   			query+=" and SO_Number  like '%"+SO_Number+"%' "		;
   		}
   		
   		
   		
   		if(PO_Number.length()>0)
   		{
   			query+=" and PO_Number  like '%"+PO_Number+"%' "		;
   		}
   	
   		
   		if(tungay.length()>0)
   		{
   			query+=" and 	CONVERT(char(10),cast(InVoiceDate as datetime),20) >='"+tungay+"'   "		;
   		}
   		
   		if(denngay.length()>0)
   		{
   			query+=" and 	CONVERT(char(10),cast(InvoiceDate as datetime),20) <='"+tungay+"'   "		;
   		}
   
   		obj.set_Action(action);

   		System.out.println("___ATION " + action);

   		if (action.equals("excel"))
   		{
   			try
   			{
   				response.setContentType("application/xlsm");
   				response.setHeader("Content-Disposition", "attachment; filename=DMS_S_ARInvoiceCollection.xlsm");
   				//FileInputStream fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\DMS_S_ARInvoiceCollection.xlsm");
   				File f= new File(getServletContext().getRealPath(getServletContext().getInitParameter("path"))+"\\DMS_S_ARInvoiceCollection.xlsm");
   				FileInputStream fstream = new FileInputStream(f) ;
   				Workbook workbook = new Workbook();
   				workbook.open(fstream);
   				workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
   				CreateStaticHeader(workbook, obj);
   				obj.setUserId(userId);
   				obj.init(query,"DMS_PO_Number desc,Created_Date");
   				FillData(workbook, obj, query);
   				workbook.save(out);
   				fstream.close();
   			} catch (Exception ex)
   			{
   				ex.printStackTrace();
   				obj.setMsg("Khong the tao pivot.");
   			}
   			session.setAttribute("obj", obj);
   			session.setAttribute("userId", userId);
   			String nextJSP = "";
   			nextJSP = "/AHF/pages/Center/SAP_SO_REJECT.jsp";
   			response.sendRedirect(nextJSP);
   		} if(action.equals("save"))
   		{
   			obj.init(query,"DMS_PO_Number desc,Created_Date " );
   			obj.setMsg(obj.TaoDonHang());
   			session.setAttribute("obj", obj);
   			String nextJSP = "";
   			nextJSP = "/AHF/pages/Center/SAP_SO_REJECT.jsp";
   			response.sendRedirect(nextJSP);
   		}
   		else if (action.equals("view") || action.equals("next") || action.equals("prev"))
   		{
   			obj.setNxtApprSplitting(Integer.parseInt(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nxtApprSplitting"))));
   			obj.setUserId(userId);
   			obj.init(query,"DMS_PO_Number desc,Created_Date");
   			obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
   			session.setAttribute("obj", obj);

   			String nextJSP = "/AHF/pages/Center/SAP_SO_REJECT.jsp";
   			response.sendRedirect(nextJSP);
   		} 
   		
   		else
   		{
   			obj.init(query,"DMS_PO_Number desc,Created_Date " );
   			session.setAttribute("obj", obj);
   			String nextJSP = "";
   			nextJSP = "/AHF/pages/Center/SAP_SO_REJECT.jsp";
   			response.sendRedirect(nextJSP);
   		}
   	}

	
	
	private boolean FillData(Workbook workbook, IBcHoaDon obj, String query) throws Exception
   	{
   		
   		return true;

   	}

   	private void CreateStaticHeader(Workbook workbook, IBcHoaDon obj)
   	{
   		
   	}


}
