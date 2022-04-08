package geso.dms.center.servlets.reports;
 
import geso.dms.center.beans.stockintransit.IStockintransit;
import geso.dms.center.beans.stockintransit.imp.Stockintransit;
import geso.dms.center.beans.thuongdauthung.IThuongdauthung;
import geso.dms.center.beans.thuongdauthung.imp.Thuongdauthung;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.servlets.report.ReportAPI;
import geso.dms.center.util.Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.BorderLineType;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Font;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class BCThuongDauThungSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
  
    public BCThuongDauThungSvl() {
        super();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		Utility util = new Utility();
		HttpSession session = request.getSession();
		String userTen = (String) session.getAttribute("userTen");
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		
		IStockintransit obj = new Stockintransit();	
		obj.settungay("");
		obj.setdenngay("");
		obj.setMsg("");
		obj.setuserId(userId);
		obj.setnppId("");
		obj.init();
		obj.setuserId(userId);
		session.setAttribute("obj", obj);
		session.setAttribute("userTen", userTen);
		String nextJSP = "/AHF/pages/Center/BCThuongDauThung.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		OutputStream out = response.getOutputStream();
		IStockintransit obj = new Stockintransit();	
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		//String nextJSP = "/AHF/pages/Center/rp_DailyStock_center.jsp";
		Utility util = new Utility();
		boolean bfasle = true;
		try
		{	
			String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
			
			obj.setuserTen((String) session.getAttribute("userTen")!=null?
					(String) session.getAttribute("userTen"):"");
			
			obj.settungay(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Sdays")))!=null?
					util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Sdays")):""));
			
			obj.setdenngay(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Edays")))!=null?
					util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Edays")):""));
			
			obj.setuserId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")))!=null?
				util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")):""));
			
			obj.setnppId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")))!=null?
					util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")):""));
			
			obj.setkenhId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhId")))!=null?
					util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhId")):""));
			
			obj.setkenhId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhId")))!=null?
					util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhId")):""));
			
			obj.setthuongId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("thuongId")))!=null?
					util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("thuongId")):""));
			
			obj.setdvkdId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdId")))!=null?
					util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdId")):""));
			
			obj.setvungId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId")))!=null?
					util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId")):""));
			
			obj.setnhanhangId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nhanhangId")))!=null?
					util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nhanhangId")):""));
			
			obj.setkhuvucId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId")))!=null?
					util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId")):""));
			
			obj.setchungloaiId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("chungloaiId")))!=null?
					util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("chungloaiId")):""));
			
			obj.init();
			session.setAttribute("obj", obj);
			
			String action = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action")));
			if(action.equals("taomoi"))
			{
			/*response.setContentType("application/xlsm");
			response.setHeader("Content-Disposition", "Attachment; filename=TonKhoHangNgay" + this.getPiVotName() + ".xlsm");

	        CreatePivotTable(out, response, request, obj, bfasle);*/
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=BCThuongDauThung.xlsm");
				Workbook workbook = new Workbook();
				TaoBaoCaoNew(workbook, obj.getuserTen(),obj.gettungay(),obj.getdenngay(),obj.getnppId(),obj.getDdkd(),obj.getASMId(),obj.getgsbhId(),obj.getvungId(),obj.getkhuvucId(), obj.getthuongId());
				
				workbook.save(out);	
			
			}
			else
			{
				response.sendRedirect("/AHF/pages/Center/BCThuongDauThung.jsp");
			}
	    }
	    catch (Exception ex)
	    {
	        ex.printStackTrace();
	        // say sorry
	        response.setContentType("text/html");
	        PrintWriter writer = new PrintWriter(out);
	
	        writer.println("<html>");
	        writer.println("<head>");
	        writer.println("<title>sorry</title>");
	        writer.println("</head>");
	        writer.println("<body>");
	        writer.println("<h1>Xin loi, khong the tao pivot table...</h1>");
	        ex.printStackTrace(writer);
	        writer.println("</body>");
	        writer.println("</html>");
	        writer.close();
	    }
	}

	
	
	private void TaoBaoCaoNew(Workbook workbook,String nguoitao,String tungay,String denngay,String nppid, String nvbh, String asm, String gsbh,String vung, String khuvuc, String diengiai )throws Exception
	{
		try{
			FileInputStream fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\Thuongdauthungnew.xlsm");
			//File f= new File(getServletContext().getRealPath(getServletContext().getInitParameter("path"))+"\\BCScore.xlsm");
			//FileInputStream fstream = new FileInputStream(f) ;
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			worksheet.setName("BCThuongDauThung");
		
			dbutils db = new dbutils();
			Cells cells = worksheet.getCells();
			cells.setRowHeight(0, 50.0f);
			Cell cell = cells.getCell("A3");
			ReportAPI.getCellStyle(cell, Color.BLUE, true, 10, "Ngày tạo : "+ getDateTime());
			
			cell = cells.getCell("A2");
			cells.setColumnWidth(0, 20.0f);
			ReportAPI.getCellStyle(cell, Color.BLUE, true, 10, "Tháng: "+ tungay);
			cell = cells.getCell("B2");
			ReportAPI.getCellStyle(cell, Color.BLUE, true, 10, "Năm: "+ denngay);
			cells.setColumnWidth(4, 20.0f);
			cells.setColumnWidth(9, 15.0f);
			cells.setColumnWidth(8, 15.0f);
			cells.setColumnWidth(7, 15.0f);
			cells.setColumnWidth(6, 15.0f);
			cell = cells.getCell("A4");
			ReportAPI.getCellStyle(cell, Color.BLUE, true, 10,"Người tạo : " + nguoitao);
		
			
			
			
			int i = 0;
			
		
			String query = "\n select distinct v.ten as Vung, kv.ten as Mien, npp.MA as nppMa, npp.ten as nppTen,   "+
					"\n gsbh.SMARTID as gsbhMa, gsbh.ten as gsbhTen, ddkd.SMARTID as ddkdMa, ddkd.ten as ddkdTen  "+
					"\n ,case when t.loaict = 0 then N'Thùng' when t.loaict = 1 then N'Lẻ' else N'Số khách hàng' end as loaiCt,   "+
					"\n t.PK_SEQ as ctMa, t.DIENGIAI as ctTen, td.sokh as sokh  "+
					"\n ,case when t.loaict = 0 then (select top 1 mt.thuong from thuongdauthung_mucthuong mt where mt.thuongdt_fk = t.PK_SEQ and sokh <= td.SoLuongThung and mt.loai = 2  order by thuong desc )   "+
					"\n when t.loaict = 1 then (select top 1 mt.thuong from thuongdauthung_mucthuong mt where mt.thuongdt_fk = t.PK_SEQ and sokh <= td.SoLuong and mt.loai = 2  order by thuong desc )   "+
					"\n else (select top 1 mt.thuong from thuongdauthung_mucthuong mt where mt.thuongdt_fk = t.PK_SEQ and sokh <= td.sokh and mt.loai = 2 order by thuong desc) end  as thuong  "+
					"\n from thuongdauthung_Sr td  "+
					"\n inner join DAIDIENKINHDOANH ddkd on ddkd.PK_SEQ = td.DDKD_FK  "+
					"\n inner join GIAMSATBANHANG gsbh on gsbh.PK_SEQ = td.GSBH_FK  "+
					"\n inner join nhaphanphoi npp on npp.pk_seq = ddkd.NPP_FK  "+
					"\n inner join KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK  "+
					"\n inner join vung v on v.PK_SEQ = kv.VUNG_FK  "+
					"\n inner join THUONGDAUTHUNG t on t.pk_seq = td.Thuongdauthung_Fk "+
					"\n where 1=1 and t.trangthai <> 2 ";
			if(diengiai.length() > 0){
				query = query + " and t.pk_seq like '%" + diengiai + "%'";
			}
				if(tungay.length() > 0 )	
				{
					query += " and t.ngaytao >= '"+tungay+"'";
				}
				if(denngay.length() > 0)
				{
					query += " and t.ngaytao <= '"+denngay+"' ";
				}
				if(nppid.length() > 0)
				{
					query += " and npp.pk_seq = '"+nppid+"' ";
				}
				if(vung.length() >0)
				{
					query += " and v.pk_seq ='"+vung+"' ";
				}
				if(khuvuc.length() > 0)
				{
					query += " and kv.pk_seq = '"+khuvuc+"' ";
				}
					
					
				query += "\n order by t.pk_seq		";
				
			
			
			System.out.println("Get NVBH ko PDA: \n"+query);
			ResultSet rs = db.get(query);
			
			ResultSetMetaData rsmd = rs.getMetaData();
			int socottrongSql = rsmd.getColumnCount();
			
			int countRow = 5;

			/*for( int i =1 ; i <=socottrongSql ; i ++  )
			{
				cell = cells.getCell(countRow,i-1 );cell.setValue(rsmd.getColumnName(i));
				cells.setColumnWidth(i, 20.0f);
				ReportAPI.setCellBackground(cell, new Color(70,130,180), BorderLineType.THIN, true, 0);	
			 
			}*/
			
			
			int stt = 0;
			while(rs.next())
			{
				
				stt++;
			
				for(i =1;i <=socottrongSql ; i ++)
				{
					cell = cells.getCell(countRow,i-1 );
					if(rsmd.getColumnName(i).equals("STT"))
					{					
						cell.setValue(stt);
						ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
						//System.out.println("STT: "+stt);

					}else
					if(rsmd.getColumnType(i) == Types.DOUBLE || rsmd.getColumnType(i) == Types.INTEGER || rsmd.getColumnType(i) == Types.DECIMAL || rsmd.getColumnType(i) == Types.FLOAT || rsmd.getColumnType(i) == Types.NUMERIC )
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
			fstream.close();
			
			
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
	
	private void getCellStyle(Workbook workbook, String a, Color mau, Boolean dam, int size)
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	   	   
	    Cells cells = worksheet.getCells();
		Style style;
		Cell cell = cells.getCell(a); 
		style = cell.getStyle();
        Font font1 = new Font();
        font1.setColor(mau);
        font1.setBold(dam);
        font1.setSize(size);
        style.setFont(font1);
        cell.setStyle(style);
	}
	
	
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy :  hh-mm-ss");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	private String getPiVotName()
	{
		String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
		Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
	    String name = sdf.format(cal.getTime());
	    name = name.replaceAll("-", "");
	    name = name.replaceAll(" ", "_");
	    name = name.replaceAll(":", "");
	    return "_" + name;
	    
	}
}
