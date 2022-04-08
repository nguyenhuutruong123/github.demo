package geso.dms.center.servlets.kehoachbanhang;

import geso.dms.center.beans.kehoachbanhang.IKehoachbanhang;
import geso.dms.center.beans.kehoachbanhang.IKehoachbanhangList;
import geso.dms.center.beans.kehoachbanhang.imp.Kehoachbanhang;
import geso.dms.center.beans.kehoachbanhang.imp.KehoachbanhangList;
import geso.dms.center.beans.stockintransit.IStockintransit;
import geso.dms.center.servlets.report.ReportAPI;
import geso.dms.center.util.OracleConnUtils;
import geso.dms.distributor.beans.tuyenbanhang.*;
import geso.dms.distributor.beans.tuyenbanhang.imp.*;
import geso.dms.distributor.db.sql.dbutils;
import geso.dms.distributor.util.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

public class KehoachbanhangSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;


	public KehoachbanhangSvl()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/AHF/index.jsp");
		}else{
			IKehoachbanhangList obj;
			PrintWriter out; 



			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			out  = response.getWriter();


			Utility util = new Utility();
			out = response.getWriter();

			String querystring = request.getQueryString();
			userId = util.getUserId(querystring);
			out.println(userId);

			if (userId.length()==0)
				userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));

			String ddkdId= util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ddkdId")));
			String action = util.getAction(querystring);
			out.println(action);

			String tbhId = util.getId(querystring);
			String msg="";
			if (action.equals("delete")){	
				System.out.println("Vao day");
				msg= Delete(tbhId, userId);
				out.print(tbhId);
			}else
				if(action.equals("chot"))
				{
					msg= Chot(tbhId, userId,ddkdId);
				}else
					if(action.equals("unchot"))
					{
						msg= UnChot(tbhId, userId);
					}

			obj = new KehoachbanhangList();
			obj.setMsg(msg);

			obj.setRequestObj(request);
			obj.setUserId(userId);

			obj.init("");

			session.setAttribute("obj", obj);

			String nextJSP = "/AHF/pages/Center/KeHoachBanHang.jsp";
			response.sendRedirect(nextJSP);
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{ 

		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/AHF/index.jsp");
		}else{
			IKehoachbanhangList obj  = new KehoachbanhangList();


			IKehoachbanhang tbhBean;

			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");

			Utility util = new Utility();

			userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));

			obj.setUserId(userId);

			String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
			if (action == null){
				action = "";
			}

			String nextJSP = "";

			obj.setRequestObj(request);
			if (action.equals("new")){
				// Empty Bean for distributor
				tbhBean = (IKehoachbanhang) new Kehoachbanhang("");
				tbhBean.setUserId(userId);
				tbhBean.createRS();

				session.setAttribute("tbhBean", tbhBean);

				nextJSP = "/AHF/pages/Center/KeHoachBanHangNew.jsp";


			}

			else  if (action.equals("excel")){
				// Empty Bean for distributor
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=KEHOACHBANHANGIP.xlsm");
				OutputStream out = response.getOutputStream(); 
				boolean isTrue;
				try {
					isTrue = CreatePivotTable2(out, "","");
					if(!isTrue)
					{
						PrintWriter writer = new PrintWriter(out);
						writer.write("Khong tao duoc bao cao trong thoi gian nay...!!!");
					}	
				} catch (Exception e) {

					e.printStackTrace();
				}



			}else
				if(action.equals("view") || action.equals("next") || action.equals("prev")){

					String search = getSearchQuery(request, obj);

					int i = Integer.parseInt(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nxtApprSplitting")));

					obj.setNxtApprSplitting(i);
					obj.init(search);
					obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
					nextJSP = "/AHF/pages/Center/KeHoachBanHang.jsp";
				}


				else{

					String search = getSearchQuery(request, obj);


					obj.setUserId(userId);
					obj.init(search);
					nextJSP = "/AHF/pages/Center/KeHoachBanHang.jsp";


				}
			if (action.equals("search")){
				String search = getSearchQuery(request, obj);
				System.out.print("sql: "+search+"\n");
				//obj = new KhachhangList(search);
				obj.setUserId(userId);

				obj.init(search);
				nextJSP = "/AHF/pages/Center/KeHoachBanHang.jsp";

			}
			session.setAttribute("obj", obj);  	
			session.setAttribute("userId", userId);

			response.sendRedirect(nextJSP);
		}
	}
	private boolean CreatePivotTable2(OutputStream out, String condition, String giatinh) throws Exception
	{   
		FileInputStream fstream = null;
		Workbook workbook = new Workbook();		


		//fstream = new FileInputStream("D:\\Project\\ICP\\Best\\WebContent\\pages\\Templates\\TonHienTaiTT.xlsm");
		//fstream = new FileInputStream("C:\\Program Files\\Apache Software Foundation\\Tomcat 7.0\\webapps\\Best\\pages\\Templates\\TonHienTaiTT.xlsm");		


		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

		CreateStaticHeader2(workbook, giatinh);	     
		boolean isTrue = CreateStaticData2(workbook, condition, giatinh);
		if(!isTrue){
			return false;
		}
		workbook.save(out);


		return true;
	}
	private boolean CreateStaticData2(Workbook workbook, String condition, String giatinh) throws Exception
	{
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		Cells cells = worksheet.getCells();

		geso.dms.center.util.Utility Uti_center = new geso.dms.center.util.Utility();

		String sql = "";

		sql = sql + condition;

		String query = "select '' as STT, A.PK_SEQ as MAKEHOACH,NGAY,A.GHICHU AS DIENGIAI,DDKD.TEN, KH.TEN AS KHACHHANG " 
				+"\n from KEHOACHBANHANG a inner join KEHOACHBANHANG_CT b on a.PK_SEQ  = b.KEHOACHBANHANG_FK "
				+"\n INNER JOIN KHACHHANG KH ON KH.PK_SEQ = B.KHACHHANG_FK "
				+"\n INNER JOIN DAIDIENKINHDOANH DDKD ON DDKD.PK_SEQ = A.DDKD_FK "
				+"\n where a.TRANGTHAI  = 1 ";

		System.out.println("Excel: " + query);
		ResultSet rs = db.get(query);
		ResultSetMetaData rsmd = rs.getMetaData();
		int socottrongSql = rsmd.getColumnCount();
		int countRow = 10;
		Cell cell = null;
		for( int i =1 ; i <=socottrongSql ; i ++  )
		{
			cell = cells.getCell(countRow,i-1 );cell.setValue(rsmd.getColumnName(i));
			cells.setColumnWidth(i, 20.0f);
			ReportAPI.setCellBackground(cell, new Color(70,130,180), BorderLineType.THIN, true, 0);	

		}
		countRow ++;
		int sonvkdpda = 0;
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




		if(db != null) db.shutDown();
		return true;

	}
	private void CreateStaticHeader2(Workbook workbook, String giatinh) 
	{
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);

		worksheet.setName("Sheet1");

		Cells cells = worksheet.getCells();

		Style style;
		Font font = new Font();
		font.setColor(Color.RED);//mau chu
		font.setSize(16);// size chu
		font.setBold(true);

		cells.setRowHeight(0, 20.0f);
		Cell cell = cells.getCell("A1");
		style = cell.getStyle();
		style.setFont(font); 
		style.setHAlignment(TextAlignmentType.LEFT);// canh le cho chu 	  

		String tieude = "DANH SÁCH KẾ HOẠCH BÁN HÀNG (IP)";
		ReportAPI.getCellStyle(cell,Color.RED, true, 14, tieude);

		cells.setRowHeight(4, 18.0f);
		cell = cells.getCell("A3");
		ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày báo cáo: " + ReportAPI.NOW("yyyy-MM-dd"));




	}


	private String getSearchQuery(HttpServletRequest request, IKehoachbanhangList obj)
	{		
		Utility util = new Utility();
		String tbhTen = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tbhTen")));
		if ( tbhTen == null)
			tbhTen = "";
		obj.setTuyenbh(tbhTen);


		String ddkdId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ddkdTen")));
		if (ddkdId == null)
			ddkdId = "";    	
		obj.setDdkdId(ddkdId);

		String trangthai= util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai")));

		if(trangthai==null)
		{
			trangthai="";

		}
		obj.setTrangthai(trangthai);
		

		String	query = "select a.pk_seq as tbhId,a.ngay as ngayId, a.ghichu as tbhTen, a.ngaytao, a.ngaysua, '' as ngaylamviec, isnull(b.ten,d1.TEN) as nguoitao,a.trangthai, isnull(c.ten,d1.TEN) as nguoisua,";
		query = query +	" d.pk_seq as ddkdId, d.ten as ddkdTen, '' as nppTen, '' as nppId";
		query = query + " from kehoachbanhang a left join nhanvien b on a.nguoitao = b.pk_seq left join nhanvien c on a.nguoisua = c.pk_seq inner join daidienkinhdoanh d on a.ddkd_fk = d.pk_seq"
				+ "  left join DAIDIENKINHDOANH d1 on d1.PK_SEQ = a.NGUOITAO "
				+ "	  left join DAIDIENKINHDOANH d2 on d2.PK_SEQ = a.NGUOISUA where 1 = 1 "; 

		if (tbhTen.length()>0)
		{
			query = query + " and (upper(dbo.ftBoDau(a.ghichu)) like upper(N'%" + util.replaceAEIOU(tbhTen) + "%') or a.pk_seq = '"+tbhTen+"' ) ";			
		}

		if (ddkdId.length()>0)
		{
			query = query + " and a.ddkd_fk ='" + ddkdId + "'";			
		}

		if (trangthai.length()>0)
		{
			query = query + " and a.trangthai ='" + trangthai + "'";			
		}

		return query;
	}	

	private String Chot(String id, String userId, String ddkdId)
	{
		dbutils db = new dbutils();
		try{
			String sql = "update kehoachbanhang set trangthai = 1 where pk_seq = '" + id + "'  ";
			if(!db.update(sql))
			{
				return "Không thể chốt kế hoạch này!";
			}

	
			
			sql = "select GHICHU,NGAY from KEHOACHBANHANG where   pk_seq = '" + id + "' ";
			ResultSet rs = db.get(sql);			
			rs.next();
			String tenkhbh=rs.getString("GHICHU") +" ngày "+rs.getString("NGAY")+" đã được duyệt";
			
			String ngay=this.getDateTime();
			
			if(ddkdId==null || ddkdId.trim().length()<=0)
				ddkdId="NULL";
			// sql ="insert into thongbao(tieude,noidung,filename,nguoitao,ngaytao,nguoisua,ngaysua,ngaybatdau,ngayketthuc,trangthai, loaithongbao, hethieuluc)" +
			//		" values(N'Duyệt Kế Hoạch Bán Hàng', N'Da duyet'+, N'tenfile', '"+ userId +"', '"+ngay+"', '"+userId+"','" + ngay + "','"+ngay+"','"+ngay+"','0', '0', '0')";
			 
			 sql ="insert into thongbao(tieude,noidung,filename,nguoitao,ngaytao,nguoisua,ngaysua,ngaybatdau,ngayketthuc,trangthai)" +
						" values(N'Duyệt Kế Hoạch Bán Hàng', N'"+tenkhbh+"', ' ','"+userId+"','"+ngay+"','"+userId+"','"+ngay+"','"+ngay+"','"+ngay+"','1')";
			db.update(sql);

			sql = "select SCOPE_IDENTITY() as tbId";
			System.out.println("cau select: "+sql);
			 rs = db.get(sql);			
			rs.next();
			String tbId = rs.getString("tbId");
			
			sql = "Insert THONGBAO_DDKD(thongbao_fk, ddkd_fk, trangthai) select "+tbId+", pk_seq, 0 " +
					"from DaiDienKinhDoanh where pk_seq in ( " + ddkdId+ " ) ";
			db.update(sql);
			
			db.shutDown();
		}catch(Exception err){
			return err.toString();
		}
		return "";
	}
	private String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	private String UnChot(String id, String userId)
	{
		dbutils db = new dbutils();
		try{
			String sql = "update kehoachbanhang set trangthai = 0 where pk_seq = '" + id + "' and ngay >=  '"+getDateTime()+"' and trangthai = '1' ";
			if(db.updateReturnInt(sql) <= 0)
			{
				return "Không thể mở  chốt kế hoạch này !";
			}

			db.shutDown();
		}catch(Exception err){
			return err.toString();
		}
		return "";
	}
	private String Delete(String id, String userId)
	{
		dbutils db = new dbutils();
		try{
			String query = "select count(*) as num from KEHOACHBANHANG_CT where KEHOACHBANHANG_FK='" + id + "'";	
			ResultSet rs = db.get(query);
			int a = 0;
			try
			{
				if(rs.next())
				{
					a = rs.getInt("num");

				}rs.close();
			} 
			catch(Exception e) {System.out.println("Loi : "+e.toString());}

			if(a == 0)
			{

				String sql = "delete from KEHOACHBANHANG where pk_seq = '" + id + "' and pk_seq not in (select KEHOACHBANHANG_FK from KEHOACHBANHANG_CT ) ";
				System.out.println("Xoa bang cha : "+sql);
				db.update(sql);

			}else{
				return "Không thể xóa KHBH này, vì  đang có khách hàng trong KHBH này!";
			}
			db.shutDown();
		}catch(Exception err){
			return err.toString();
		}
		return "";
	}

}
