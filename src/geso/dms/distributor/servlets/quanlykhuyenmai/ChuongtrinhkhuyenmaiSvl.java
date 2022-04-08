package geso.dms.distributor.servlets.quanlykhuyenmai;

import geso.dms.distributor.beans.quanlykhuyenmai.IChuongtrinhkhuyenmai;
import geso.dms.distributor.beans.quanlykhuyenmai.imp.Chuongtrinhkhuyenmai;
import geso.dms.center.beans.ctkhuyenmai.ICtkhuyenmaiList;
import geso.dms.center.beans.ctkhuyenmai.imp.CtkhuyenmaiList;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.servlets.report.ReportAPI;
import geso.dms.center.util.Utility;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.BorderLineType;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ChuongtrinhkhuyenmaiSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public ChuongtrinhkhuyenmaiSvl() {
        super();
    }

	IChuongtrinhkhuyenmai ctkmBean = new Chuongtrinhkhuyenmai();
    private Utility util = new Utility();
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/AHF/index.jsp");
		}else{
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");

	    IChuongtrinhkhuyenmai ctkmBean = new Chuongtrinhkhuyenmai();
	    ctkmBean.setRequestObj(request);
	    String querystring = request.getQueryString();
	    userId = util.getUserId(querystring);
//	    System.out.println(userId);
	    
	    ctkmBean.setUserId(userId);
	   
	    
	    ctkmBean.init();	    
		session.setAttribute("ctkm", ctkmBean);
		session.setAttribute("userId", userId);
		String nextJSP = "/AHF/pages/Distributor/ChuongTrinhKhuyenMai.jsp";
		response.sendRedirect(nextJSP);
		}
	    
	
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		ICtkhuyenmaiList obj = new CtkhuyenmaiList();	
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/AHF/index.jsp");
		}else{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
//		HttpSession session = request.getSession();
		//PrintWriter out = response.getWriter();

		//String contentType = request.getContentType();
		//here we are checking the content type is not equal to Null and as well as the passed data from mulitpart/form-data is greater than or equal to 0

		userId = (String)session.getAttribute("userId");
		//IChuongtrinhkhuyenmai ctkmBean = new Chuongtrinhkhuyenmai();
		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		System.out.println("aciotn " + action);
		String diengiai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diengiai")));
		String tungay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay")));
		String denngay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay")));
		String trangthai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai")));
		
		
		ctkmBean.setDiengiai(diengiai);
		ctkmBean.setTungay(tungay);
		ctkmBean.setDenngay(denngay);
		ctkmBean.setTrangthai(trangthai);
	    ctkmBean.setUserId(userId);
	    
		//a
	    	
    	ctkmBean.setRequestObj(request);  	
	    	
	    	//------------------------
	    	
	    	    	
    	//ctkmBean = new DaidienkinhdoanhList(search);
	    	
    	if(action.equals("view") || action.equals("next") || action.equals("prev")){
		    	
    		ctkmBean.setNxtApprSplitting(Integer.parseInt(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nxtApprSplitting"))));
	    	ctkmBean.init();
	    	ctkmBean.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
    	}
    	
    	else  if (action.equals("excel"))
	    {
    		
	    	try
		    {
	    		ctkmBean.init();	
	    		response.setContentType("application/vnd.ms-excel");
				response.setHeader("Content-Disposition", "attachment; filename=THONGTINCTKM.xls");
			
				com.aspose.cells.Workbook workbook = new com.aspose.cells.Workbook();
				TaoBaoCao(workbook, userTen,obj);
				OutputStream out = response.getOutputStream();
				workbook.save(out);
			 
		    }
		    catch (Exception ex)
		    {
		       ex.printStackTrace();
		    }
	    	
			//obj.setUserId(userId);
		  		
	    }
    	else{
	    	ctkmBean.init();		
			
	    	session.setAttribute("ctkm", ctkmBean);  	
    		session.setAttribute("userId", userId);
    		
    		response.sendRedirect("/AHF/pages/Distributor/ChuongTrinhKhuyenMai.jsp");
    	}
		}
		
	}

	private void TaoBaoCao(com.aspose.cells.Workbook workbook,String nguoitao ,ICtkhuyenmaiList obj)throws Exception
	{
		try{
			workbook.setFileFormatType(FileFormatType.EXCEL2003);
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			worksheet.setName("ThongTinCTKM");
			com.aspose.cells.Cells cells = worksheet.getCells();
			cells.setRowHeight(0, 50.0f);
			com.aspose.cells.Cell cell = cells.getCell("A1");
			ReportAPI.getCellStyle(cell, Color.RED, true, 16,"Chương Trình Khuyến Mãi");

			cell = cells.getCell("A3");
			ReportAPI.getCellStyle(cell, Color.BLUE, true, 10, "Ngày tạo : "+ getDateTime());
			
			cell = cells.getCell("A2");
			cells.setColumnWidth(0, 20.0f);

			cells.setColumnWidth(4, 20.0f);
			cells.setColumnWidth(9, 15.0f);
			cells.setColumnWidth(8, 15.0f);
			cells.setColumnWidth(7, 15.0f);
			cells.setColumnWidth(6, 15.0f);
			cell = cells.getCell("A4");
			ReportAPI.getCellStyle(cell, Color.BLUE, true, 10,"Người tạo : " + nguoitao);
			ResultSet rs = null;
			dbutils db = new dbutils();
			String query =    " select '' as STT,a.NGAYTAO as [Ngày tạo] ,isnull(a.SCHEMEERP,'') SCHEMEERP,a.SCHEME,a.DIENGIAI as [Diễn giải],a.TUNGAY [Từ ngày],a.DENNGAY [Đến ngày],"
					 + "\n a.KBH_FK [Kênh],kho.DIENGIAI [Kho],sp.MA as [Mã SP DKKM],"
					 + "\n sp.ten as [Tên Sản Phẩm DKKM],c.SOLUONG as [Số Lượng DKKM],dv.DIENGIAI as [Đơn vị DKKM],"
					 + "\n sp1.MA as [Mã SP Trả],sp1.ten as [Tên SP Trả],f.SOLUONG as [Số Lượng Trả] ,dv1.DIENGIAI as [Đơn vị trả KM] , case when a.apdungcho = 0 then N'Sell in & Sell out' when a.apdungcho = 1 then N'Sell in' else N'Sell out' end as [Áp dụng cho], "
					 + "\n [dbo].GetCTKHUYENMAI_LOAIKH(a.PK_SEQ) as [Loại KH],[dbo].[GetCTKMNPP_NPP](a.PK_SEQ,"+ctkmBean.getUserId()+") as NhaPhanPhoi"
					 + "\n from CTKHUYENMAI a left join CTKM_DKKM b"
					 + "\n on a.PK_SEQ=b.CTKHUYENMAI_FK left join DIEUKIENKM_SANPHAM c "
					 + "\n on c.DIEUKIENKHUYENMAI_FK=b.DKKHUYENMAI_FK "
					 + "\n left join CTKM_TRAKM d on d.CTKHUYENMAI_FK=a.PK_SEQ"
					 + "\n left join TRAKHUYENMAI_SANPHAM f on f.TRAKHUYENMAI_FK=d.TRAKHUYENMAI_FK"
					 + "\n left join sanpham sp on sp.PK_SEQ=c.SANPHAM_FK"
					 + "\n left join sanpham sp1 on sp1.PK_SEQ=f.SANPHAM_FK"
					 + "\n left join DONVIDOLUONG dv on dv.PK_SEQ=sp.DVDL_FK"
					 + "\n left join DONVIDOLUONG dv1 on dv1.PK_SEQ=sp1.DVDL_FK"
					 + "\n left join KHO kho on kho.PK_SEQ = a.KHO_FK "
					 + " where exists (select 1 from ctkm_npp z where z.npp_fk = '"+ctkmBean.getNppId()+"' and z.ctkm_fk = a.pk_seq ) ";
			if(ctkmBean.getDiengiai().length() > 0){
				query = query + " and scheme like '%" + ctkmBean.getDiengiai() + "%'";
			}
			
			if(ctkmBean.getTungay().length() > 0){
				query = query + " and tungay >='" + ctkmBean.getTungay() +"'";
				
			}
			
			if(ctkmBean.getDenngay().length() > 0){
				query = query + " and denngay <='" + ctkmBean.getDenngay() +"'";
			}
			
			if(ctkmBean.getTrangthai().equals("1")){
				query = query + " and tungay <= (SELECT CONVERT(VARCHAR(10),DATEADD(day,0,dbo.GetLocalDate(DEFAULT)),120))" +
								" and denngay >=( SELECT CONVERT(VARCHAR(10),DATEADD(day,0,dbo.GetLocalDate(DEFAULT)),120))";

			}
			
			if(ctkmBean.getTrangthai().equals("2")){
				query = query + " and tungay >= (SELECT CONVERT(VARCHAR(10),DATEADD(day,0,dbo.GetLocalDate(DEFAULT)),120))" +
								" or denngay <=( SELECT CONVERT(VARCHAR(10),DATEADD(day,0,dbo.GetLocalDate(DEFAULT)),120))";
				
			}
			System.out.println("Get excel CTKM: "+query);
			 rs = db.get(query);
			
			ResultSetMetaData rsmd = rs.getMetaData();
			int socottrongSql = rsmd.getColumnCount();
			
			int countRow = 10;

			for( int i =1 ; i <=socottrongSql ; i ++  )
			{
				cell = cells.getCell(countRow,i-1 );cell.setValue(rsmd.getColumnName(i));
				cells.setColumnWidth(i, 20.0f);
				ReportAPI.setCellBackground(cell, new Color(70,130,180), BorderLineType.THIN, true, 0);	
			 
			}
			countRow ++;
			
			int stt = 0;
			while(rs.next())
			{
				boolean kt = false;
				boolean ck = true;
				stt++;
				String value = "";
				for(int i =1;i <=socottrongSql ; i ++)
				{
					cell = cells.getCell(countRow,i-1 );
					if(rsmd.getColumnName(i).equals("STT"))
					{					
						cell.setValue(stt);
						ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
						//System.out.println("STT: "+stt);

					}else
					if(rsmd.getColumnType(i) == Types.DOUBLE || rsmd.getColumnType(i) == Types.INTEGER || rsmd.getColumnType(i) == Types.DECIMAL)
					{
						cell.setValue(rs.getDouble(i));
						
						ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
					}
					else
					{
						cell.setValue(rs.getString(i));
						ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					}
				}
				++countRow;
			}

			if(rs!=null)rs.close();
			if(db!=null){
				db.shutDown();
			}

	
		}catch(Exception ex){
			
			System.out.println("Errrorr : "+ex.toString());
			ex.printStackTrace();
			throw new Exception("Lỗi không tạo được báo cáo !");
		}
	}
	
	
	public String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

}
