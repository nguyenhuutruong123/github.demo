package geso.dms.center.servlets.reports;

import geso.dms.center.beans.stockintransit.IStockintransit;
import geso.dms.center.beans.stockintransit.imp.Stockintransit;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.servlets.report.ReportAPI;
import geso.dms.center.util.Utility;
import geso.dms.distributor.beans.report.Ireport;
import geso.dms.distributor.beans.report.imp.Report;

import java.io.File;
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
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class BCTongquanMCPSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;


   
	private void setQueryBC(Workbook workbook, String userid) throws Exception
	{
		try{
			FileInputStream fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\BCTongQuanMCP.xlsm");
			
			//File f= new File(getServletContext().getRealPath(getServletContext().getInitParameter("path"))+"\\BCDonHangBanTrongKyNPP.xlsm");
			//FileInputStream fstream = new FileInputStream(f) ;
			
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			worksheet.setName("TongQuanMCP");
		
			dbutils db = new dbutils();
			Cells cells = worksheet.getCells();
			cells.setRowHeight(0, 50.0f);
			Cell cell = cells.getCell("A1");
			ReportAPI.getCellStyle(cell, Color.RED, true, 16,"Báo Cáo Tổng Quan MCP");

			cell = cells.getCell("A3");
			ReportAPI.getCellStyle(cell, Color.BLUE, true, 10, "Ngày tạo : "+ getDateTime());
			
			cell = cells.getCell("A2");
			cells.setColumnWidth(0, 20.0f);
			ReportAPI.getCellStyle(cell, Color.BLUE, true, 10, "Tháng: ");
			cell = cells.getCell("B2");
			ReportAPI.getCellStyle(cell, Color.BLUE, true, 10, "Năm: ");
			cells.setColumnWidth(4, 20.0f);
			cells.setColumnWidth(9, 15.0f);
			cells.setColumnWidth(8, 15.0f);
			cells.setColumnWidth(7, 15.0f);
			cells.setColumnWidth(6, 15.0f);
			cell = cells.getCell("A4");
			ReportAPI.getCellStyle(cell, Color.BLUE, true, 10,"Người tạo : " );
			
		String sql = "select distinct tanso from khachhang_tuyenbh ";
		System.out.println(sql);
		ResultSet r = db.getScrol(sql);
		String subSelect = "";
		String subPivot = "";
		String subIn = "";
		String select_mcp = "";
		String select_cods = "";
		String selectall = "";
		String tongch = "";
		String tongcods = "";
		int col = 0;
		while(r.next())
		col++;
		String[] col_row= new String[col];
		r.beforeFirst();
		int i = 0;
		while(r.next()){
			subSelect += " ISNULL(MCP.["+r.getString("tanso")+"],0) AS ["+r.getString("tanso")+"] \n" +
					",ISNULL(CODS.[DS_["+r.getString("tanso")+"],0) AS [CODS_"+r.getString("tanso")+"], \n";
			selectall += " ds.["+r.getString("tanso")+"] , ds.[CODS_"+r.getString("tanso")+"], \n";
			tongch += " ds.["+r.getString("tanso")+"] + \n";
			tongcods +=" ds.[CODS_"+r.getString("tanso")+"] + \n";
		subPivot += "PIVOTTABLE.["+r.getString("tanso")+"], \n";
		subIn += "["+r.getString("tanso")+"], ";
		select_mcp += "PIVOTTABLE.[" + r.getString("tanso") +"], \n";
		select_cods += "PIVOTTABLE.[" + r.getString("tanso")+"] as [DS_[" + r.getString("tanso")+"], \n";
		col_row[i] = r.getString("tanso");
		i++;
		}
		subSelect = subSelect.substring(0, subSelect.length() - 3);
		selectall = selectall.substring(0, selectall.length() - 3);
		subPivot = subPivot.substring(0, subPivot.length() - 3);
		select_mcp = select_mcp.substring(0, select_mcp.length() - 3);
		select_cods = select_cods.substring(0, select_cods.length() - 3);
		subIn = subIn.substring(0, subIn.length() - 2);
		tongch = tongch.substring(0, tongch.length() - 3);
		tongcods = tongcods.substring(0, tongcods.length() - 3);
		
		
		String query = "\n SELECT v.TEN as VUNG,kv.TEN as KHUVUC,case when ddkd.TRANGTHAI= 1 then N'Hoạt động' else N'Ngưng hoạt động' end as TRANGTHAI, KBH.TEN AS KENHBANHANG, NPP.TEN AS NHAPHANPHOI, DDKD.TEN AS NHANVIENBANHANG," +
				" KHN.KHNGUNGHD, KHM.KHTAOMOI ,"+tongch +" as SOCUAHIEU, COTOADO.SOCUAHIEU AS SOCHCAPNHATTOADO, "+tongcods+" as SOCHCODOANHSO "+
			"\n , "+  selectall +
			"\n from  "+
			"\n ( "+
			"\n 	select  ISNULL(MCP.DDKDID, CODS.DDKDID) AS DDKD_FK, ISNULL(MCP.nppid, CODS.NPPID) AS NPP_FK "+
			"\n 	, "+ subSelect +
			"\n 	from "+
			"\n 	(	 "+
			"\n 		select PIVOTTABLE.DDKDID,PIVOTTABLE.NPPID , " + select_mcp +
			"\n 		 from "+
			"\n 		( "+
			"\n 		select TBH.DDKD_FK AS DDKDID, ddkd.NPP_FK as nppid, "+
			"\n 		ISNULL(COUNT(distinct kht.KHACHHANG_FK),0) AS SOCUAHIEU,KHT.TANSO "+
			"\n 		from DAIDIENKINHDOANH ddkd  "+
			"\n 		inner join TUYENBANHANG tbh on tbh.DDKD_FK = ddkd.PK_SEQ "+
			"\n 		inner join KHACHHANG_TUYENBH kht on kht.TBH_FK = tbh.PK_SEQ "+
			"\n 		inner join khachhang kh on kh.PK_SEQ = kht.KHACHHANG_FK "+
			"\n 		where 1=1 and kh.TRANGTHAI =1 "+
			"\n 		group by tbh.DDKD_FK, kht.TANSO, ddkd.NPP_FK "+
			"\n 		) st PIVOT (sum(st.SOCUAHIEU) for st.TANSO IN ("+subIn+") ) AS PIVOTTABLE "+
			"\n 	) MCP "+
			"\n 	full outer join  "+
			"\n 	( "+
			"\n 		select PIVOTTABLE.DDKDID,  PIVOTTABLE.NPPID, "+ select_cods +
			"\n 		from  "+
			"\n 		(  "+
			"\n  			SELECT A.DVKDID,A.NPPID,a.TANSO,A.DDKDID,SUM(SO) AS SOCHCODS  "+
			"\n  			FROM   "+
			"\n  			(  "+
			"\n  				SELECT  A.DVKDID,A.NPPID,A.TANSO,A.DDKDID,A.KHID, SO=CASE WHEN SUM(KH_IN_DAY)  = 1   THEN 1  ELSE 0 END    "+
			"\n 				FROM   "+
			"\n 				(  "+
			"\n 					SELECT SP.DVKD_FK AS DVKDID,KHT.TANSO ,KH.NPP_FK AS NPPID ,TBH.DDKD_FK AS DDKDID,DH.KHACHHANG_FK AS KHID,--DH.NGAYNHAP,  "+
			"\n 					COUNT(DISTINCT(DH.KHACHHANG_FK)) AS KH_IN_DAY  "+
			"\n 					FROM DONHANG DH  "+
			"\n 					INNER JOIN DONHANG_SANPHAM DH_SP ON DH_SP.DONHANG_FK = DH.PK_SEQ  "+
			"\n  					INNER JOIN SANPHAM SP ON SP.PK_SEQ = DH_SP.SANPHAM_FK  "+
			"\n  					INNER JOIN KHACHHANG_TUYENBH KHT ON KHT.KHACHHANG_FK = DH.KHACHHANG_FK "+
			"\n  					INNER JOIN tuyenbanhang tbh ON tbh.pk_seq = kht.tbh_fk "+
			"\n  					INNER JOIN KHACHHANG KH ON KH.pk_seq = DH.KHACHHANG_FK "+
			"\n  					WHERE DH.TONGGIATRI >0 AND DH.TRANGTHAI = '1' and kh.trangthai = 1 and dh.ngaynhap >= CONVERT(date,DATEADD(month, -2, dbo.GetLocalDate(DEFAULT)))  AND DH.KHACHHANG_FK IN (SELECT KHACHHANG_FK FROM KHACHHANG_TUYENBH )  "+
			"\n 					GROUP BY SP.DVKD_FK ,KHT.TANSO ,KH.NPP_FK,tbh.DDKD_FK ,DH.KHACHHANG_FK--,DH.NGAYNHAP  "+
			"\n 				) A  "+
		/*	"\n  				left JOIN   "+
			"\n 				(  "+
			"\n  					SELECT DISTINCT SP.DVKD_FK AS DVKDID ,DH.NPP_FK AS NPPID ,DH.DDKD_FK AS DDKDID,KHACHHANG_FK AS KHID  "+
			"\n  					FROM DONHANG DH  "+
			"\n  					INNER JOIN DONHANG_SANPHAM DH_SP ON DH_SP.DONHANG_FK = DH.PK_SEQ  "+
			"\n 					INNER JOIN SANPHAM SP ON SP.PK_SEQ = DH_SP.SANPHAM_FK  "+
			"\n  					WHERE DH.TONGGIATRI >0 AND DH.TRANGTHAI = '1'      "+
			"\n  					UNION  "+
			"\n  					SELECT DISTINCT SP.DVKD_FK AS DVKDID ,DH.NPP_FK AS NPPID ,DH.DDKD_FK AS DDKDID ,KHACHHANG_FK AS KHID  "+
			"\n 					FROM DONHANGTRAVE DH   "+
			"\n 					INNER JOIN DONHANG_SANPHAM  DH_SP1 ON DH.DONHANG_FK=DH_SP1.DONHANG_FK  "+
			"\n  					INNER JOIN SANPHAM SP ON SP.PK_SEQ=DH_SP1.SANPHAM_FK  "+
			"\n 					WHERE DH.TRANGTHAI=3 AND DH.DONHANG_FK IS NOT NULL   "+
			"\n 				) B  "+
			"\n 				ON A.DVKDID=B.DVKDID AND A.NPPID=B.NPPID AND A.DDKDID=B.DDKDID AND A.KHID=B.KHID  "+
			"\n 				left JOIN  "+
			"\n 				(  "+
			"\n 					SELECT   "+
			"\n 					DH.DDKD_FK AS DDKDID,DH.NPP_FK AS NPPID ,SP.DVKD_FK AS DVKDID ,DH.NGAYNHAP,KHACHHANG_FK AS KHID,COUNT(DISTINCT DH.KHACHHANG_FK) AS KHTRA_IN_DAY  "+
			"\n 					FROM   "+
			"\n 					DONHANGTRAVE DH   "+
			"\n 					INNER JOIN   "+
			"\n 					DONHANG_SANPHAM DH_SP1 ON DH.DONHANG_FK=DH_SP1.DONHANG_FK  "+
			"\n 					INNER JOIN SANPHAM SP ON SP.PK_SEQ=DH_SP1.SANPHAM_FK  "+
			"\n 					WHERE DH.TRANGTHAI=3 AND DH.DONHANG_FK IS NOT NULL  "+
			"\n 					GROUP BY DH.DDKD_FK ,DH.NPP_FK,SP.DVKD_FK,DH.NGAYNHAP,DH.KHACHHANG_FK  "+
			"\n 				) TRA  "+
			"\n 				ON A.DVKDID=TRA.DVKDID AND A.NPPID=TRA.NPPID AND A.DDKDID=TRA.DDKDID AND A.KHID=TRA.KHID  "+*/
			"\n 				GROUP BY A.DVKDID,A.NPPID,A.DDKDID,A.KHID, a.TANSO "+
			"\n 			) A GROUP BY A.DVKDID,A.NPPID,A.DDKDID, A.TANSO "+
			"\n 		 ) st PIVOT (sum(st.SOCHCODS) for st.TANSO IN ("+subIn+" )) AS PIVOTTABLE "+
			"\n 	 )CODS ON MCP.DDKDID = CODS.DDKDID and CODS.NPPID = mcp.nppid "+
			"\n  ) ds  "+
			"\n  left join "+
			"\n  (  "+
			"\n  		SELECT   "+
			"\n  		TBH.NPP_FK AS NPPID,TBH.DDKD_FK AS DDKDID,  "+
			"\n  		ISNULL(COUNT(DISTINCT KH_TBH.KHACHHANG_FK),0) AS SOCUAHIEU   "+
			"\n  		FROM KHACHHANG_TUYENBH KH_TBH   "+
			"\n  		INNER JOIN TUYENBANHANG TBH ON TBH.PK_SEQ = KH_TBH.TBH_FK   "+
			"\n 		INNER JOIN KHACHHANG KH ON KH.PK_SEQ=KH_TBH.KHACHHANG_FK  and KH.TRANGTHAI = '1'  "+
			"\n  		where KH.LAT is not null and KH.LONG is not null  "+
			"\n  		GROUP BY TBH.NPP_FK ,TBH.DDKD_FK "+
			"\n ) COTOADO ON  COTOADO.NPPID = ds.NPP_FK AND COTOADO.DDKDID = ds.DDKD_FK   "+
			///////////////////////////////////////////////////
			"\n  left join "+
			"\n  (  "+
			"\n  		SELECT dh.NPP_FK, dh.ddkd_fk, count(distinct dh.pk_seq) KHNGUNGHD  "+
			"\n  		FROM donhang dh "+
			"\n			inner join khachhang kh on dh.khachhang_fk = kh.pk_seq				"+
			"\n  		where dh.ngaynhap >= CONVERT(date,DATEADD(month, -2, dbo.GetLocalDate(DEFAULT))) and dh.trangthai = 1 "+
			"\n 		and (dh.khachhang_fk in (select distinct khachhang_fk from khachhang_hdlog where trangthai = 0 and ngaysua >= CONVERT(date,DATEADD(month, -1, dbo.GetLocalDate(DEFAULT))) ) or kh.ngaysua >= CONVERT(date,DATEADD(month, -1, dbo.GetLocalDate(DEFAULT))) ) and kh.trangthai = 0 "		+	
			"\n  		GROUP BY dh.NPP_FK,dh.DDKD_FK "+
			"\n ) KHN ON  KHN.NPP_FK = ds.NPP_FK AND KHN.DDKD_FK = ds.DDKD_FK   "+
			"\n  left join "+
			"\n  (  "+
			"\n  		SELECT dh.NPP_FK, dh.ddkd_fk, count(distinct kh.pk_seq) KHTAOMOI  "+
			"\n  		FROM donhang dh "+
			"\n			inner join khachhang kh on dh.khachhang_fk = kh.pk_seq				"+
			"\n  		where dh.ngaynhap >= CONVERT(date,DATEADD(month, -1, dbo.GetLocalDate(DEFAULT))) and dh.trangthai = 1 "+
			"\n 		and kh.ngaytao >= CONVERT(date,DATEADD(month, -1, dbo.GetLocalDate(DEFAULT))) "		+	
			"\n  		GROUP BY dh.NPP_FK,dh.DDKD_FK "+
			"\n ) KHM ON  KHM.NPP_FK = ds.NPP_FK AND KHM.DDKD_FK = ds.DDKD_FK   "+
			///////////////////////////////////////////////////
			"\n  INNER JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ = ds.NPP_FK  "+
			"\n  INNER JOIN DAIDIENKINHDOANH DDKD ON DDKD.PK_SEQ = ds.DDKD_FK and NPP.PK_SEQ = DDKD.NPP_FK " +
			"\n inner join phamvihoatdong pv on pv.npp_fk = npp.pk_seq and pv.nhanvien_fk = "+userid+" "+
			"\n  left join KHUVUC kv on kv.PK_SEQ = NPP.KHUVUC_FK  "+
			"\n  left join VUNG v on v.PK_SEQ = kv.VUNG_FK " +
			"\n  left join nhapp_kbh nk on nk.npp_fk = npp.pk_seq " +
			"\n  left join kenhbanhang kbh on kbh.pk_seq = nk.kbh_FK  ";
		
		System.out.println("BC tong quan MCP : " + query);
		ResultSet rs = db.get(query);
		ResultSetMetaData rsmd = rs.getMetaData();
		int socottrongSql = rsmd.getColumnCount();
		
		int countRow = 10;

		for(  i =1 ; i <=socottrongSql ; i ++  )
		{
			cell = cells.getCell(countRow,i-1 );cell.setValue(rsmd.getColumnName(i));
			cells.setColumnWidth(i, 20.0f);
			ReportAPI.setCellBackground(cell, new Color(70,130,180), BorderLineType.THIN, true, 0);	
		 
		}
		countRow ++;
		
		int stt = 0;
		while(rs.next())
		{
			
			stt++;
		
			for( i =1;i <=socottrongSql ; i ++)
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
					if(!rsmd.getColumnName(i).equals("SKU"))
						ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
					else
						ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					
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
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		/*try {*/
			IStockintransit obj = new Stockintransit();
			request.setCharacterEncoding("utf-8");
			HttpSession session = request.getSession();
			String userId = (String)session.getAttribute("userId");
			String userTen = (String) session.getAttribute("userTen");
			System.out.println("UserId : "+userId);
			obj.setuserId(userId);
			obj.setuserTen(userTen);
			OutputStream out = response.getOutputStream();
			
			response.setContentType("application/xlsm");			
			response.setHeader("Content-Disposition", "attachment; filename=BCTongQuanMCP.xlsm");
			
			//String query = setQuery();

	        try 
	        {
	        	Workbook workbook = new Workbook();
	        	setQueryBC(workbook, userId);
				
				workbook.save(out);	
				/*if(!CreatePivotTable(out, response, request, obj, query))
				{
					response.setContentType("text/html");
				    PrintWriter writer = new PrintWriter(out);
				    writer.print("Xin loi khong co bao cao trong thoi gian nay");
				    writer.close();
				}*/
			} 
	        catch (Exception e) 
	        {
				obj.setMsg("Khong the tao bao cao " + e.getMessage());
			}					
	}
	
	private boolean CreatePivotTable(OutputStream out,HttpServletResponse response,HttpServletRequest request,IStockintransit obj, String query) throws Exception 
	{
		boolean isFillData = false;
		FileInputStream fstream = null;
		Workbook workbook = new Workbook();

		String strfstream = getServletContext().getInitParameter("path") + "\\BCTongQuanMCP.xlsm";  
		fstream = new FileInputStream(strfstream);		
		//File f= new File(getServletContext().getRealPath(getServletContext().getInitParameter("path"))+"\\BCTongQuanMCP.xlsm");
		//FileInputStream fstream = new FileInputStream(f) ;
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

		CreateHeader(workbook,obj);
		isFillData = FillData(workbook, query, obj);
   
		if (!isFillData)
		{
			if(fstream != null)
				fstream.close();
			return false;
		}
		
		workbook.save(out);
		fstream.close();
		return true;	
	}
	
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException 
	{	}


	private void CreateHeader(Workbook workbook,IStockintransit obj) 
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);	    
	    worksheet.setName("Sheet1");
	    Cells cells = worksheet.getCells();	 
	    
	    cells.setRowHeight(0, 20.0f);	    
	    Cell cell = cells.getCell("A1");	
	    ReportAPI.getCellStyle(cell,Color.RED, true, 16, "BÁO CÁO TỔNG QUAN MCP");	    
	    cell = cells.getCell("A2");
	    ReportAPI.getCellStyle(cell,Color.NAVY,true,10,"Ngày tạo : " + this.getDateTime()); 
	    cell = cells.getCell("A3");
	    ReportAPI.getCellStyle(cell,Color.NAVY,true,10,"Người tạo : " + obj.getuserTen());
	    
	    
	    cell = cells.getCell("EA1");		cell.setValue("NHAPHANPHOI");				
		cell = cells.getCell("EB1");		cell.setValue("NHANVIENBANHANG");	
		cell = cells.getCell("EC1");		cell.setValue("SOCHMCP");		
		cell = cells.getCell("ED1");		cell.setValue("SOCHCAPNHATTOADO");			
		cell = cells.getCell("EE1");		cell.setValue("SOCHCODOANHSO");			
		
		cell = cells.getCell("EF1");		cell.setValue("SOCHF1-2");			
		cell = cells.getCell("EG1");		cell.setValue("F1-2CODOANHSO");

		cell = cells.getCell("EH1");		cell.setValue("SOCHF2");			
		cell = cells.getCell("EI1");		cell.setValue("F2CODOANHSO");
		
		cell = cells.getCell("EJ1");		cell.setValue("SOCHF2-1");			
		cell = cells.getCell("EK1");		cell.setValue("F2-1CODOANHSO");
		
		cell = cells.getCell("EL1");		cell.setValue("SOCHF2-2");			
		cell = cells.getCell("EM1");		cell.setValue("F2-2CODOANHSO");
		
		cell = cells.getCell("EN1");		cell.setValue("SOCHF4");			
		cell = cells.getCell("EO1");		cell.setValue("F4CODOANHSO");
		
		cell = cells.getCell("EP1");		cell.setValue("SOCHF6");			
		cell = cells.getCell("EQ1");		cell.setValue("F6CODOANHSO");
		
		cell = cells.getCell("ER1");		cell.setValue("SOCHF8");			
		cell = cells.getCell("ES1");		cell.setValue("F8CODOANHSO");	
		
		cell = cells.getCell("ET1");		cell.setValue("Vung");			
		cell = cells.getCell("EU1");		cell.setValue("KhuVuc");	
		cell = cells.getCell("EV1");		cell.setValue("TrangThai");			

	}
	
	private boolean FillData(Workbook workbook, String query, IStockintransit obj) throws Exception
	{
		ResultSet rs = null;
		dbutils db = new dbutils();
		
		try
		{	
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			Cells cells = worksheet.getCells();
			
			for(int i=0;i<15;++i)
			{
		    	cells.setColumnWidth(i, 15.0f);	    	
		    }	
			 rs = db.get(query);
			int index = 2;
		    Cell cell = null;
		    if (rs != null) 
			{ 
			while (rs.next())
			{
				
				cell = cells.getCell("EA" + String.valueOf(index));		cell.setValue(rs.getString("NPPTEN"));			
				cell = cells.getCell("EB" + String.valueOf(index));		cell.setValue(rs.getString("DDKDTEN"));
				cell = cells.getCell("EC" + String.valueOf(index));		cell.setValue(rs.getString("SOMCP"));
				cell = cells.getCell("ED" + String.valueOf(index));		cell.setValue(rs.getInt("CHCOTOADO"));
				cell = cells.getCell("EE" + String.valueOf(index));		cell.setValue(rs.getInt("CHCODOANHSO"));
				
				cell = cells.getCell("EF" + String.valueOf(index));		cell.setValue(rs.getInt("F12"));
				cell = cells.getCell("EG" + String.valueOf(index));		cell.setValue(rs.getInt("F12CODS"));
				
				cell = cells.getCell("EH" + String.valueOf(index));		cell.setValue(rs.getInt("F2"));
				cell = cells.getCell("EI" + String.valueOf(index));		cell.setValue(rs.getInt("F2CODS"));
				
				cell = cells.getCell("EJ" + String.valueOf(index));		cell.setValue(rs.getInt("F21"));
				cell = cells.getCell("EK" + String.valueOf(index));		cell.setValue(rs.getInt("F21CODS"));
				
				cell = cells.getCell("EL" + String.valueOf(index));		cell.setValue(rs.getInt("F22"));
				cell = cells.getCell("EM" + String.valueOf(index));		cell.setValue(rs.getInt("F22CODS"));
				
				cell = cells.getCell("EN" + String.valueOf(index));		cell.setValue(rs.getInt("F4"));
				cell = cells.getCell("EO" + String.valueOf(index));		cell.setValue(rs.getInt("F4CODS"));
				
				cell = cells.getCell("EP" + String.valueOf(index));		cell.setValue(rs.getInt("F6"));
				cell = cells.getCell("EQ" + String.valueOf(index));		cell.setValue(rs.getInt("F6CODS"));
				
				cell = cells.getCell("ER" + String.valueOf(index));		cell.setValue(rs.getInt("F8"));
				cell = cells.getCell("ES" + String.valueOf(index));		cell.setValue(rs.getInt("F8CODS"));
				
				cell = cells.getCell("ET" + String.valueOf(index));		cell.setValue(rs.getString("Vung"));
				cell = cells.getCell("EU" + String.valueOf(index));		cell.setValue(rs.getString("KhuVuc"));
				cell = cells.getCell("EV" + String.valueOf(index));		cell.setValue(rs.getString("TrangThai").equals("0")?"Ngưng hoạt động":"Hoạt động");
				

				index++;
			}
			if(rs!=null){
				rs.close();
			}
			ReportAPI.setHidden(workbook,14);
			}else
			{return false;}		
							 				    	
		}		
			catch(Exception ex)
			{
				throw new Exception(ex.getMessage());
			}				
		
		return true;
	}

	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy : hh-mm-ss");
		Date date = new Date();
		return dateFormat.format(date);
	}
	


}
