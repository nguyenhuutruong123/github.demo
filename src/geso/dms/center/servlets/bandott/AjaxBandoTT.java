package geso.dms.center.servlets.bandott;

import geso.dms.center.util.Utility;
import geso.dms.center.beans.bandott.IBandott;
import geso.dms.center.beans.bandott.imp.Bandott;
import geso.dms.center.db.sql.dbutils;
import geso.dms.distributor.beans.khachhang.IKhachhangList;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import org.json.*;

public class AjaxBandoTT extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out; 
       
    public AjaxBandoTT() {
        super();
    }

    private String COLUMN_NAME;
	private String DIRECTION;
	private int INITIAL;
	private int RECORD_SIZE;
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		System.out.println("vo AjaxDistributionTT !!!!!!");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		JSONObject jsonResult = new JSONObject();
		int listDisplayAmount = 10;
		int start = 0;
		int column = 0;
		String dir = "asc";
		String pageNo = request.getParameter("iDisplayStart");
		String pageSize = request.getParameter("iDisplayLength");
		Utility util = new Utility();
		
		if (pageNo != null) {
		   start = Integer.parseInt(pageNo);
		   if (start < 0) { start = 0; }
		}
		
		if (pageSize != null) 
		{
		   listDisplayAmount = Integer.parseInt(pageSize);
		   if (listDisplayAmount < 10 || listDisplayAmount > 50) 
		   { listDisplayAmount = 10; }
		 }
		
		int totalRecords= -1;
		try { totalRecords = getTotalRecordCount(request, util); } catch (SQLException e1) { e1.printStackTrace(); }
		
		RECORD_SIZE = listDisplayAmount;
		DIRECTION = dir;
		INITIAL = start;
		
		try {
		   jsonResult = getDataDetails(totalRecords, request, util);
		  } catch (ClassNotFoundException e) {
		   e.printStackTrace();
		  } catch (SQLException e) {
		   e.printStackTrace();
		  }
		 
		  response.setContentType("application/json");
		  response.setHeader("Cache-Control", "no-store");
		  PrintWriter out = response.getWriter();
		  out.print(jsonResult);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{ }
	
	public int getTotalRecordCount(HttpServletRequest request, Utility util) throws SQLException 
	{
	  int totalRecords = -1;
	  dbutils db = new dbutils();
	  String sql = "";
	  if(request.getParameter("action").equals("ToaDoKH"))
	  {
		  sql = ToaDoKhachHang_Num(request, util);
	  }
	  ResultSet rs = db.get(sql);
	  rs.next();
	  totalRecords = rs.getInt("num");
	  rs.close();
	  db.shutDown();
	  return totalRecords;
	}
	
	public JSONObject getDataDetails(int totalRecords, HttpServletRequest request, Utility util) throws SQLException, ClassNotFoundException 
	{
		  int totalAfterSearch = totalRecords;
		  JSONObject result = new JSONObject();
		  JSONArray array = new JSONArray();
		  if(request.getParameter("action").equals("ToaDoKH"))
		  {
			  array = ToaDoKhachHang(request, util);
		  }
		  try {
			   result.put("iTotalRecords", totalRecords);
			   result.put("iTotalDisplayRecords", totalAfterSearch);
			   result.put("aaData", array);
			  } catch (Exception e) { }
		  return result;
	 }
	
	public JSONArray ToaDoKhachHang(HttpServletRequest request, Utility util) throws SQLException, ClassNotFoundException 
	{
		dbutils db = new dbutils();
		JSONArray array = new JSONArray();
		String makhId = util.antiSQLInspection(request.getParameter("makhId"));
		String dvkd = util.antiSQLInspection(request.getParameter("dvkd"));
		String vung = util.antiSQLInspection(request.getParameter("vung"));
		String kv = util.antiSQLInspection(request.getParameter("kv"));
		String ddkd =  util.antiSQLInspection(request.getParameter("ddkd"));
		String tbh = util.antiSQLInspection(request.getParameter("tbh"));
		String npp = util.antiSQLInspection(request.getParameter("npp"));
		if(makhId==null) {
			makhId = "";
		}
		if(dvkd == null) {
			dvkd = "";
		}
		if(vung == null) {
			vung = "";
		}
		if(kv==null) {
			kv = "";
		}
		if(ddkd==null) {
			dvkd = "";
		}
		if(tbh == null) {
			tbh = "";
		}
		if(npp == null) {
			npp = "";
		}
		String sql1= "";
//			sql1 = 
//				"\n select top("+ RECORD_SIZE +") * from( select row_number() over(order by addNo.khTen) as _no, addNo.* from "+
//				"\n (  "+
//				"\n 	 select  isnull(kh.CHUCUAHIEU,'NA') as CHUCUAHIEU, kh.pk_seq as pk_seq, " +
//				"\n 	 kh.SMARTID as mafast, kh.smartid as khId, kh.ten as khTen, isnull(kh.dienthoai, 'NA') as dienthoai, " +
//				"\n 	 kh.lat as lat, kh.long as lon,isnull(kh.diachi, 'NA') as diachi " +
//				"\n	 	 from KhachHang kh " +
//				"\n 	 where 1 = 1 and (lat is not null or long is not null) and ismt = '0' ";						 			
//		
//				/*if(mafast != null && mafast.length()>0){
//					sql1 +=" and kh.SMARTID like '%"+mafast+"%'"; 
//				}*/
//			
//				if(ddkdId != null && ddkdId.trim().length() > 0)
//				{ sql1 += " and kh.ddkd_fkip = '" + ddkdId + "'  "; }
		
		sql1 = 
				"\n select top("+ RECORD_SIZE +") * from( select row_number() over(order by addNo.khTen) as _no, addNo.* from "+
				"\n (  "+
				"\n 	 select  isnull(kh.CHUCUAHIEU,'NA') as CHUCUAHIEU, kh.pk_seq as pk_seq, " +
				"\n 	 kh.SMARTID as mafast, kh.smartid as khId, kh.ten as khTen, isnull(kh.dienthoai, 'NA') as dienthoai, " +
				"\n 	 kh.lat as lat, kh.long as lon,isnull(kh.diachi, 'NA') as diachi " +
				"\n	 	 from KhachHang kh  " +
				"\n      WHERE 1=1  ";	

		if(dvkd.trim().length()> 0 || kv.trim().length()> 0 || vung.trim().length() > 0 || ddkd.trim().length() > 0) {
			sql1 += " AND EXISTS\r\n" + 
					"("+
					"SELECT * \r\n" + 
					"	FROM dbo.NHAPP_NHACC_DONVIKD nppnccdvkd\r\n" + 
					"	INNER JOIN dbo.NHACUNGCAP_DVKD nccdvkd ON nccdvkd.PK_SEQ = nppnccdvkd.NCC_DVKD_FK\r\n" + 
					"	WHERE 1=1 AND kh.NPP_FK = nppnccdvkd.NPP_FK ";
			if(dvkd.trim().length() > 0) {
				sql1 += "		 AND  nccdvkd.DVKD_FK  = '"+dvkd+"' \r\n" ;
			}
			if(kv.trim().length() + vung.trim().length() > 0) {
				sql1 += " AND EXISTS\r\n" + 
						"	( \n"+
						" SELECT * FROM dbo.KHUVUC kv\r\n" + 
						" INNER JOIN dbo.NHAPHANPHOI npp ON npp.KHUVUC_FK = kv.PK_SEQ AND npp.PK_SEQ = nppnccdvkd.NPP_FK \n"+
						" WHERE 1=1 ";
				if(kv.trim().length() > 0 ) {
					sql1 += " and kv.PK_SEQ = '"+kv+"' ";
				}
				if(vung.trim().length() > 0) {
					sql1 += " and kv.VUNG_FK = '"+vung+"' ";
				}
				sql1 += "\n  ) ";
			}
			if(ddkd.trim().length() > 0) {
				sql1 += " AND EXISTS \r\n" + 
						"	(\r\n" + 
						"		SELECT * \r\n" + 
						"		FROM dbo.ddkd_khachhang ddkdkh\r\n" + 
						"		WHERE ddkdkh. khachhang_fk = kh.PK_SEQ AND ddkdkh.ddkd_fk = '"+ddkd+"' "+
						"	) \n";
			}
			sql1 += " ) \n";
		}
		if(tbh.trim().length() > 0) {
			sql1+= " AND EXISTS \r\n" + 
					"(\r\n" + 
					"	SELECT * \r\n" + 
					"	FROM KHACHHANG_TUYENBH khtbh\r\n" + 
					"	WHERE kh.PK_SEQ = khtbh.KHACHHANG_FK   AND khtbh.TBH_FK = '"+tbh+"'  \r\n" + 
					")";
		}
		if(npp.trim().length() > 0) {
			sql1 += " AND kh.NPP_FK = '"+npp+"'\r\n";
		}
		if(makhId.trim().length() > 0) {
			sql1 += " AND ( kh.TEN LIKE '%"+makhId+"%' OR kh.SMARTID LIKE '%"+makhId+"%' ) ";
		}
		sql1+= " and (kh.lat is not null or kh.long is not null)";
		sql1 +=    "\n ) addNo) list where _no > '"+ INITIAL +"' ";
		System.out.println("sql ne : "+ sql1);
		  ResultSet rs = db.get(sql1);
		  while (rs.next()) 
		  {
			   JSONArray ja = new JSONArray();
			   
			   ja.put(rs.getString("khId"));
			   ja.put(rs.getString("khTen"));
			   ja.put(rs.getString("diachi"));
			   ja.put(rs.getString("dienthoai"));
			   ja.put(rs.getString("lat"));
			   ja.put(rs.getString("lon"));
			   ja.put(rs.getString("pk_seq"));
			   ja.put("0");
			   ja.put("0");
			   /*ja.put((formatter.format(rs.getDouble("soluong"))));
			   ja.put((formatter.format(rs.getDouble("giamua"))));
			   ja.put((formatter.format(rs.getDouble("doanhthu"))));
			   ja.put((formatter.format(rs.getDouble("thue"))));
			   ja.put((formatter.format(rs.getDouble("tongtien"))));*/
			   array.put(ja); 
		  }
		  rs.close();
		  db.shutDown();
		return array;
	}
	
	private String ToaDoKhachHang_Num(HttpServletRequest request, Utility util) 
	{
		String ddkdId = util.antiSQLInspection(request.getParameter("ddkdId"));
		String ma_tenkh = util.antiSQLInspection(request.getParameter("makhId"));
		
		String sql1 = 
				"\n select count(kh.pk_seq) num " +
				"\n from KhachHang kh " +
				"\n where 1 = 1 and (lat is not null or long is not null) and ismt = '0' ";						 			

				/*if(mafast != null && mafast.length()>0){
					sql1 +=" and kh.SMARTID like '%"+mafast+"%'"; 
				}*/

				if(ddkdId != null && ddkdId.trim().length() > 0)
				{
					sql1 += " and kh.ddkd_fkip = '" + ddkdId + "'  ";
				}
				if(ma_tenkh != null && ma_tenkh.length()>0){
					sql1 +=" and (kh.smartid like '%"+ma_tenkh+"%' or kh.ten like '%"+ma_tenkh+"%')"; 
				}
		
		return sql1;
	}
	
}
