package geso.dms.distributor.beans.reports.imp;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.aspose.cells.BorderLineType;
import com.aspose.cells.BorderType;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.Font;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

import geso.dms.center.util.Utility;
import geso.dms.distributor.beans.reports.IBCCongNoChiTiet;
import geso.dms.distributor.beans.reports.IBCCongNoTheoHD;
import geso.dms.distributor.beans.reports.ITomTatCongNoTrongKy;
import geso.dms.distributor.db.sql.dbutils;

public class BCCongNoChiTiet implements IBCCongNoChiTiet, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userId;
	private String userName;
	private String tuNgay;
	private String denNgay;
	private String nppId;
	private String ngayKS;
	private ResultSet rs;

	public BCCongNoChiTiet() {
		this.userId = "";
		this.userName = "";
		this.tuNgay = "";
		this.denNgay = "";
		this.nppId = "";
		this.ngayKS = "";
		this.rs = null;
	}

	@Override
	public void setUserId(String userId) {
		// TODO Auto-generated method stub
		if (userId != null)
			this.userId = userId;
		this.getNgayKS();
	}

	@Override
	public String getUserId() {
		// TODO Auto-generated method stub
		return this.userId;
	}

	@Override
	public void setUserName(String userName) {
		// TODO Auto-generated method stub
		this.userName = userName;
	}

	@Override
	public String getUserName() {
		// TODO Auto-generated method stub
		return this.userName;
	}

	@Override
	public void setTuNgay(String tuNgay) {
		// TODO Auto-generated method stub
		if (tuNgay != null)
			this.tuNgay = tuNgay;
	}

	@Override
	public String getTuNgay() {
		// TODO Auto-generated method stub
		return this.tuNgay;
	}

	@Override
	public void setDenNgay(String denNgay) {
		// TODO Auto-generated method stub
		if (denNgay != null)
			this.denNgay = denNgay;
	}

	@Override
	public String getDenNgay() {
		// TODO Auto-generated method stub
		return this.denNgay;
	}

	@Override
	public String getNPPID() {
		// TODO Auto-generated method stub
		return this.nppId;
	}

	@Override
	public void getNgayKS() {
		// TODO Auto-generated method stub
		Utility ut = new Utility();
		this.nppId = ut.getIdNhapp(userId);

		dbutils db = new dbutils();
		String sql = "SELECT TOP(1) NGAYKS FROM KHOASONGAY WHERE NPP_FK = '"+ this.nppId +"' ORDER BY NGAYKS DESC";

		ResultSet ks = db.get(sql);
		try{
			ks.next();
			this.ngayKS = ks.getString("ngayks");
		
		}catch(Exception e){
			this.ngayKS = "";
		}
		if (db != null)
			db.shutDown();


	}

	@Override
	public ResultSet getRS() {
		// TODO Auto-generated method stub
		return this.rs;
	}

	@Override
	public void createStaticHeader(Workbook workbook) {
		// TODO Auto-generated method stub

		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);

		Cells cells = worksheet.getCells();

		Style style;
		// cells.setColumnWidth(0, 200.0f);
		cells.setRowHeight(0, 20.0f);
		Cell cell = cells.getCell("A1");
		cell.setValue(this.userName);
		style = cell.getStyle();

		Font f = new Font();
		// f.setColor(Color.RED);//mau chu
		// f.setSize(16);// size chu
		f.setBold(true);

		style.setFont(f);
		style.setHAlignment(TextAlignmentType.LEFT);// canh le cho chu
		cell.setStyle(style);

		f = new Font();
		cell = cells.getCell("E2");
		cell.setValue("Báo Cáo Công Nợ Chi Tiết");

		style = cell.getStyle();

		// font3.setColor(Color.RED);//mau chu
		f.setSize(14);// size chu
		f.setBold(true);
		style.setFont(f);
		style.setHAlignment(TextAlignmentType.CENTER);// canh le cho chu
		cell.setStyle(style);
		
	
		f = new Font();
		cell = cells.getCell("E4");
		cell.setValue("Từ " + tuNgay + " đến " + denNgay + " (ngày khóa sổ: "
				+ this.ngayKS + ")");

		f.setBold(true);
		style = cell.getStyle();
		style.setFont(f);
		style.setHAlignment(TextAlignmentType.CENTER);// canh le cho chu
		cell.setStyle(style);

/*		f = new Font();
		f.setItalic(true);
		style.setFont(f);
		style.setHAlignment(TextAlignmentType.LEFT);
		cell = cells.getCell("A6");
		cell.setValue("Ghi chú:");
		cell.setStyle(style);
		cell = cells.getCell("A7");
		cell.setValue("HDTC: Hóa đơn tài chính; HT: Hệ thống; TT: Thanh toán");
		cell.setStyle(style);*/

		worksheet.setName("bcCongNoChiTiet");

	}

	@Override
	public void init() {
		
		// TODO Auto-generated method stub
		System.out.println("nha ppid: " + this.nppId);
		String dk = "";
		if (!this.tuNgay.equals(""))
			dk += " and dh.ngaynhap >= '" + this.tuNgay + "' ";
		if (!this.tuNgay.equals(""))
			dk += " and dh.ngaynhap <= '" + this.denNgay + "' ";

		
		String sql; 
		/*
		sql=	"SELECT " + 
					"KH.PK_SEQ AS KHID, " +
					"KH.TEN AS KH, " +
					"'0' AS CHUNGTUHT, " +
					"'0'	AS THANHTOANCHO, " +
					"'"+ this.tuNgay +"' AS NGAY, " +
					"'0' AS HOADON, " +
					"(NODAU.NODAU - NODAU.TRADAU) AS TIENMAT, " +
					"'0' AS CHUYENKHOAN, " +
					"'0' AS TRAHANG, " +
					"'1' AS THUTU " +
				"FROM KHACHHANG KH " +
				"INNER JOIN " +
				"( " +
					"SELECT " + 
						"KH_CN.KHACHHANG_FK AS KHID, " + 
						"ISNULL(SUM(KH_CN.SOTIENNO),0) AS NODAU, " +
						"ISNULL(SUM(PTT.SOTIEN),0) AS TRADAU " +
					"FROM KHACHHANG_CONGNO KH_CN " +
					"INNER JOIN DONHANG DH ON DH.PK_SEQ = KH_CN.DONHANG_FK AND DH.KHACHHANG_FK = KH_CN.KHACHHANG_FK AND DH.NGAYNHAP < '"+ this.tuNgay +"' " +
					"LEFT JOIN PHIEUTHANHTOAN PTT ON PTT.DONHANG_FK = DH.PK_SEQ  AND PTT.NGAY < '"+ this.tuNgay +"' " +
					"WHERE DH.NPP_FK= '" + this.nppId + "' " +
					"GROUP BY KH_CN.KHACHHANG_FK "+
				")NODAU ON NODAU.KHID = KH.PK_SEQ " +
				"WHERE ((NODAU.NODAU - NODAU.TRADAU) > 0)" + 

				"UNION " +
				"SELECT " +
					"KH.PK_SEQ AS KHID, " + 
					"KH.TEN AS KH, " +
					"HD.DHID AS CHUNGTUHT, " +
					"'0' AS THANHTOANCHO, " +
					"HD.NGAY AS NGAY, " +
					"'0' AS HOADON, " +
					"HD.NOTRONGKY AS TIENMAT, " +
					"'0' AS CHUYENKHOAN, " +
					"'0' AS TRAHANG, " +
					"'2' AS THUTU " +
				"FROM KHACHHANG KH " +
				"INNER JOIN " +
				"( " +
					"SELECT " + 
						"KH_CN.KHACHHANG_FK AS KHID, " +
						"KH_CN.DONHANG_FK AS DHID, " +
						"KH_CN.SOTIENNO AS NOTRONGKY, " +
						"KH_CN.NGAYNO AS NGAY " +
					"FROM KHACHHANG_CONGNO KH_CN " +
					"INNER JOIN DONHANG DH ON DH.PK_SEQ = KH_CN.DONHANG_FK AND KH_CN.NGAYNO >='" + this.tuNgay +"' AND KH_CN.NGAYNO <='" + this.denNgay + "' " +
					"WHERE DH.NPP_FK='" + this.nppId + "' "+
			
				")HD ON HD.KHID = KH.PK_SEQ " +
				"UNION " +
				"SELECT " +
					"KH.PK_SEQ AS KHID, " +
					"KH.TEN AS KH, " +
					"THANHTOAN.PTTID AS CHUNGTUHT, " +
					"THANHTOAN.DHID AS THANHTOANCHO, " +
					"THANHTOAN.NGAY AS NGAY, " +
					"'0' AS HOADON, " +
					"'0' AS TIENMAT, " +
					"(-1)*THANHTOAN.THANHTOAN AS CHUYENKHOAN, " +
					"'0' AS TRAHANG, " +
					"'3' AS THUTU " +
				"FROM KHACHHANG KH " +
				"INNER JOIN " +
				"( " +
					"SELECT " + 
						"DH.KHACHHANG_FK AS KHID, " +
						"PTT.PK_SEQ AS PTTID, " +
						"PTT.DONHANG_FK AS DHID, " +
						"PTT.SOTIEN AS THANHTOAN, " +
						"PTT.NGAY AS NGAY " +
					"FROM PHIEUTHANHTOAN PTT " +
					"INNER JOIN DONHANG DH ON DH.PK_SEQ = PTT.DONHANG_FK " +
					"WHERE PTT.NGAY >='"+ this.tuNgay +"' AND PTT.NGAY <='"+ this.denNgay +"' AND DH.NPP_FK='"+ this.nppId +"' " +
			
				")THANHTOAN ON THANHTOAN.KHID = KH.PK_SEQ " +

				"WHERE KH.NPP_FK = '" + this.nppId + "' " + 
				"ORDER BY KH.TEN, THUTU, CHUNGTUHT, THANHTOANCHO, NGAY " ;
			*/
		sql="SELECT KH.PK_SEQ AS KHID, KH.TEN AS KH, '0' AS CHUNGTUHT, '0'	AS THANHTOANCHO, '"+ this.tuNgay +"' AS NGAY, '0' AS HOADON, "+
			" (NODAU.NODAU - NODAU.TRADAU) AS TIENMAT, '0' AS CHUYENKHOAN, '0' AS TRAHANG, '1' AS THUTU "+
			" FROM KHACHHANG KH  "+
			" INNER JOIN  "+
			" (  "+
			" SELECT KH_CN.KHACHHANG_FK AS KHID, ISNULL(SUM(KH_CN.SOTIENNO),0) AS NODAU, "+
			"  ISNULL(TRADAUKY.TIENTHANHTOAN,0) AS TRADAU  "+
			" FROM KHACHHANG_CONGNO KH_CN  "+
			" INNER JOIN DONHANG DH ON DH.PK_SEQ = KH_CN.DONHANG_FK "+ 
			" 	   AND DH.KHACHHANG_FK = KH_CN.KHACHHANG_FK AND KH_CN.NGAYNO < '"+ this.tuNgay +"' "+ 
			"  LEFT JOIN "+
			"     ( "+
			"   SELECT DH.KHACHHANG_FK AS KHID,SUM(SOTIEN) AS TIENTHANHTOAN "+  
			"  FROM   PHIEUTHANHTOAN PTT "+
			"  INNER JOIN DONHANG DH ON DH.PK_SEQ=PTT.DONHANG_FK "+	 
			" 	 WHERE  PTT.NGAY < '"+ this.tuNgay +"' AND DH.NPP_FK= '"+ this.nppId +"' "+
			" 	GROUP BY DH.KHACHHANG_FK "+
			" )  TRADAUKY ON TRADAUKY.KHID = KH_CN.KHACHHANG_FK "+ 
			" WHERE KH_CN.NGAYNO<'"+ this.tuNgay +"' AND DH.NPP_FK=	"+ this.nppId +" "+
			" GROUP BY KH_CN.KHACHHANG_FK,ISNULL(TRADAUKY.TIENTHANHTOAN,0) "+
			" )NODAU  ON NODAU.KHID = KH.PK_SEQ WHERE ((NODAU.NODAU - NODAU.TRADAU) > 0) "+
			" UNION  "+
			" SELECT KH.PK_SEQ AS KHID, KH.TEN AS KH, HD.DHID AS CHUNGTUHT, '0' AS THANHTOANCHO, "+
			" HD.NGAY AS NGAY, '0' AS HOADON, HD.NOTRONGKY AS TIENMAT, '0' AS CHUYENKHOAN, '0' AS TRAHANG, '2' AS THUTU "+ 
			" FROM KHACHHANG KH  "+
			" INNER JOIN  "+
			" (  "+
			" SELECT KH_CN.KHACHHANG_FK AS KHID, KH_CN.DONHANG_FK AS DHID, KH_CN.SOTIENNO AS NOTRONGKY, "+
			" KH_CN.NGAYNO AS NGAY  "+
			" FROM KHACHHANG_CONGNO KH_CN "+ 
			" INNER JOIN DONHANG DH ON DH.PK_SEQ = KH_CN.DONHANG_FK "+ 
			" WHERE DH.NPP_FK='"+ this.nppId +"' AND KH_CN.NGAYNO >='"+ this.tuNgay +"' AND KH_CN.NGAYNO <='"+ this.denNgay +"' "+
			" )HD ON HD.KHID = KH.PK_SEQ "+
			" UNION  "+
			" SELECT KH.PK_SEQ AS KHID, KH.TEN AS KH, THANHTOAN.PTTID AS CHUNGTUHT, THANHTOAN.DHID AS THANHTOANCHO, "+
			" THANHTOAN.NGAY AS NGAY, '0' AS HOADON, '0' AS TIENMAT, (-1)*THANHTOAN.THANHTOAN AS CHUYENKHOAN, '0' AS TRAHANG, "+
			" '3' AS THUTU  "+
			" FROM KHACHHANG KH "+ 
			" INNER JOIN (  "+
			" SELECT DH.KHACHHANG_FK AS KHID, PTT.PK_SEQ AS PTTID, PTT.DONHANG_FK AS DHID, PTT.SOTIEN AS THANHTOAN, PTT.NGAY AS NGAY "+
			" FROM PHIEUTHANHTOAN PTT  "+
			" INNER JOIN DONHANG DH ON DH.PK_SEQ = PTT.DONHANG_FK "+
			" WHERE PTT.NGAY >='"+ this.tuNgay +"' AND PTT.NGAY <='"+ this.denNgay +"' AND DH.NPP_FK='"+ this.nppId +"' "+
			" )THANHTOAN  "+
			" ON THANHTOAN.KHID = KH.PK_SEQ WHERE KH.NPP_FK = '"+ this.nppId +"' "+ 
			" ORDER BY KH.TEN, THUTU, CHUNGTUHT, THANHTOANCHO, NGAY ";
		System.out.println("sql BC CONG NO CHI TIET: " + sql);
		dbutils db = new dbutils();

		this.rs = db.get(sql);
		
	}

	@Override
	public void tieuDe(Workbook workbook, int rowIndex) {
//		this.getNgayKS();
		// TODO Auto-generated method stub
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);

		
		Cells cells = worksheet.getCells();
		getCellStyle(workbook, "D" + rowIndex, Color.BLACK, true, 8);
		Cell cell = cells.getCell("D" + rowIndex);
		cell.setValue("Số HĐTC");
//		createBorderSetting(workbook, "C" + rowIndex);
	
		getCellStyle(workbook, "E" + rowIndex, Color.BLACK, true, 8);
		cell = cells.getCell("E" + rowIndex);
		cell.setValue("Chứng từ");
//		createBorderSetting(workbook, "D" + rowIndex);
	
		getCellStyle(workbook, "F" + rowIndex, Color.BLACK, true, 8);
		cell = cells.getCell("F" + rowIndex);
		cell.setValue("Thanh toán cho");
//		createBorderSetting(workbook, "E" + rowIndex);
	
		getCellStyle(workbook, "G" + rowIndex, Color.BLACK, true, 8);
		cell = cells.getCell("G" + rowIndex);
		cell.setValue("Ngày");
//		reateBorderSetting(workbook, "F" + rowIndex);
	
		getCellStyle(workbook, "H" + rowIndex, Color.BLACK, true, 8);
		cell = cells.getCell("H" + rowIndex);
		cell.setValue("Hóa đơn");
//		createBorderSetting(workbook, "G" + rowIndex);
	
		getCellStyle(workbook, "I" + rowIndex, Color.BLACK, true, 8);
		cell = cells.getCell("I" + rowIndex);
		cell.setValue("Tiền mặt");
//		createBorderSetting(workbook, "H" + rowIndex);
	
		getCellStyle(workbook, "J" + rowIndex, Color.BLACK, true, 8);
		cell = cells.getCell("J" + rowIndex);
		cell.setValue("Chuyển khoản");
//		createBorderSetting(workbook, "I" + rowIndex);
	
		getCellStyle(workbook, "K" + rowIndex, Color.BLACK, true, 8);
		cell = cells.getCell("K" + rowIndex);
		cell.setValue("Trả hàng");
//		createBorderSetting(workbook, "I" + rowIndex);

//		getCellStyle(workbook, "K" + rowIndex, Color.BLACK, true, 8);
		// cell = cells.getCell("H" + rowIndex); cell.setValue("Nợ đầu kỳ");
		// createBorderSetting(workbook,"H" + rowIndex);
		// getCellStyle(workbook,"H" + rowIndex,Color.BLACK,true,10);
		// cell = cells.getCell("I" + rowIndex);
		// cell.setValue("Phải thu cuối kỳ"); createBorderSetting(workbook,"I" +
		// rowIndex); getCellStyle(workbook,"I" + rowIndex,Color.BLACK,true,10);
		// cells.merge(4, 2, 4, 3);
		cells.setColumnWidth(0, 15.0f);// Số hdtc
		cells.setColumnWidth(1, 15.0f);// phai thu cuoi ky
		cells.setColumnWidth(2, 9.0f);// ct ht
		cells.setColumnWidth(3, 12.0f);// thanh toan cho
		cells.setColumnWidth(4, 12.0f); // ngay
		cells.setColumnWidth(5, 15.0f);// hoa don
		cells.setColumnWidth(6, 15.0f);// tien mat
		cells.setColumnWidth(7, 15.0f);
		cells.setColumnWidth(9, 18.0f);
		//worksheet.freezePanes(9, rowIndex+1, 9, rowIndex +2);

	}

	@Override
	public void createStaticData(Workbook workbook) {
		// TODO Auto-generated method stub
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		Cells cells = worksheet.getCells();
		NumberFormat formatter = new DecimalFormat("#,###,###");
		if (rs != null) {
			try {
				
				int i = 8;
				String khId = "";
				Cell cell = null;
				Font f = null;
				Style style = null;
				double nodauky = 0;
				double tongTT = 0;
				double ttongTT = 0;

				double tongPTCK = 0;
				double ttongPTCK = 0;

				double tongTM = 0;
				double ttongTM = 0;

				// double tongHD = 0;
				tieuDe(workbook, i);
				while (rs.next()) {
					i++;
					String KHID = rs.getString("KHID");
					String KH = rs.getString("KH");
					String CHUNGTUHT = rs.getString("CHUNGTUHT");
					String THANHTOANCHO = rs.getString("THANHTOANCHO");
					String NGAY = rs.getString("NGAY");
					String HOADON = " "; //rs.getString("HOADON");
					String TIENMAT = rs.getString("TIENMAT");
					String CHUYENKHOAN = rs.getString("CHUYENKHOAN");
					String TRAHANG = " "; // rs.getString("TRAHANG");
//					String SOHDTC = rs.getString("SOHDTC");
					

					if (!(rs.getString("khid").equals(khId))) {

						// tongPTCK = 0;
						if(i >9){
							f = new Font();
							f.setBold(true);
							f.setItalic(true);							
							f.setSize(8);

							System.out.println("I am here. i = " + i);
							
							cell = cells.getCell("A" + Integer.toString(i));
							style = cell.getStyle();
							style.setFont(f);
							cell.setStyle(style);

							cell.setValue("Phải trả cuối kỳ");
//							createBorderSetting(workbook, "A" + Integer.toString(i));
							cell = cells.getCell("B" + Integer.toString(i));
							
							style = cell.getStyle();
							style.setFont(f);
							style.setHAlignment(TextAlignmentType.RIGHT);
							cell.setStyle(style);
							
							tongPTCK += tongTM + tongTT;
							
							if(tongPTCK != 0){
								cell.setValue(formatter.format(tongPTCK));
							}else{
								cell.setValue(" ");
							}
							
//							createBorderSetting(workbook,"C" + Integer.toString(i));
							ttongPTCK += tongPTCK;
							
							cell = cells.getCell("I" + Integer.toString(i));
							style = cell.getStyle();
							style.setFont(f);
							cell.setStyle(style);

							cell.setValue(formatter.format(tongTM));
							
//							createBorderSetting(workbook, "I" + Integer.toString(i));

							cell = cells.getCell("J" + Integer.toString(i));
							style = cell.getStyle();
							style.setFont(f);
							cell.setStyle(style);

							cell.setValue(tongTT);

							i++;
							tieuDe(workbook, i);
							i++;
						}
							System.out.println("I am here 1. i = " + i);
							tongPTCK = 0;
							tongTM = 0;
							tongTT = 0;
							
							f = new Font();						
							f.setSize(8);
							f.setBold(true);
							f.setItalic(true);
																					
							cell = cells.getCell("A" + Integer.toString(i));
							style = cell.getStyle();
							style.setFont(f);
							cell.setStyle(style);
							cell.setValue(rs.getString("khid") + " - " 	+ rs.getString("kh"));
							
							
//							createBorderSetting(workbook, "A" + Integer.toString(i));
							khId = rs.getString("KHID");
							i++;
													
							cell = cells.getCell("E" + Integer.toString(i));
							style = cell.getStyle();
							style.setHAlignment(TextAlignmentType.CENTER);
							style.setFont(f);
							cell.setStyle(style);
							cell.setValue("Nợ đầu kỳ");
							
							
							if (Double.parseDouble(CHUNGTUHT) != 0) {
								
								cell = cells.getCell("G" + Integer.toString(i));
								style = cell.getStyle();
								style.setFont(f);
								style.setHAlignment(TextAlignmentType.CENTER);
								cell.setStyle(style);
								cell.setValue(this.tuNgay);

								cell = cells.getCell("I" + Integer.toString(i));
								style = cell.getStyle();
								style.setFont(f);
								cell.setStyle(style);
								cell.setValue(Double.parseDouble("0"));

								i++; i++;
								
								f = new Font();						
								f.setSize(8);
								
								cell = cells.getCell("E" + Integer.toString(i));
								style = cell.getStyle();
								style.setFont(f);
								style.setHAlignment(TextAlignmentType.CENTER);
								cell.setStyle(style);
								cell.setValue(Double.parseDouble(CHUNGTUHT));
									
								cell = cells.getCell("G" + Integer.toString(i));
								style = cell.getStyle();
								style.setFont(f);
								style.setHAlignment(TextAlignmentType.CENTER);
								cell.setStyle(style);
								cell.setValue(NGAY);
									
								cell = cells.getCell("I" + Integer.toString(i));
								style = cell.getStyle();
								style.setFont(f);
								cell.setStyle(style);
								if(Double.parseDouble(TIENMAT) != 0){
									cell.setValue(formatter.format(Double.parseDouble(TIENMAT)));							
								}else{
									cell.setValue(" ");
								}
								tongTM += Double.parseDouble(TIENMAT);
								ttongTM=ttongTM+ tongTM;
//									createBorderSetting(workbook, "I" + Integer.toString(i));
								
								
								cell = cells.getCell("J" + Integer.toString(i));
								style = cell.getStyle();
								style.setFont(f);
								cell.setStyle(style);
								if(Double.parseDouble(CHUYENKHOAN) != 0){
									cell.setValue(formatter.format(Double.parseDouble(CHUYENKHOAN)));
								}else{
									cell.setValue(" ");
								}
//								createBorderSetting(workbook, "J" + Integer.toString(i));
								tongTT += Double.parseDouble(CHUYENKHOAN);
								ttongTT=ttongTT+ tongTT;
							
								i++;
																
							}else{
								cell = cells.getCell("G" + Integer.toString(i));
								style = cell.getStyle();
								style.setHAlignment(TextAlignmentType.CENTER);
								style.setFont(f);
								cell.setStyle(style);
								cell.setValue(NGAY);

								cell = cells.getCell("I" + Integer.toString(i));
								style = cell.getStyle();
								style.setFont(f);
								style.setHAlignment(TextAlignmentType.RIGHT);
								cell.setStyle(style);
								if(Double.parseDouble(TIENMAT) != 0){
									cell.setValue(formatter.format(Double.parseDouble(TIENMAT)));							
								}else{
									cell.setValue(" ");
								}
								tongTM += Double.parseDouble(TIENMAT); // Luu gia tri no dau ky vao tongTM
								ttongTM=ttongTM+tongTM;
								i++;
							}
							
						
					}else{
						f = new Font();						
						f.setSize(8);

						tongTM += Double.parseDouble(TIENMAT);
						tongTT += Double.parseDouble(CHUYENKHOAN);
						
						ttongTM=ttongTM+tongTM;
						ttongTT=ttongTT+tongTT;
						
						
						cell = cells.getCell("D" + Integer.toString(i));
						cell.setValue(" ");
//					createBorderSetting(workbook, "D" + Integer.toString(i));
					

						cell = cells.getCell("E" + Integer.toString(i));
						style = cell.getStyle();
						style.setHAlignment(TextAlignmentType.CENTER);
						style.setFont(f);
						cell.setStyle(style);
						cell.setValue(Double.parseDouble(CHUNGTUHT));
//					createBorderSetting(workbook, "E" + Integer.toString(i));
					
						cell = cells.getCell("F" + Integer.toString(i));
						style = cell.getStyle();
						style.setHAlignment(TextAlignmentType.CENTER);
						style.setFont(f);
						cell.setStyle(style);
						cell.setValue(Double.parseDouble(THANHTOANCHO));
//					createBorderSetting(workbook, "F" + Integer.toString(i));
						
						cell = cells.getCell("G" + Integer.toString(i));
						style = cell.getStyle();
						style.setHAlignment(TextAlignmentType.CENTER);
						style.setFont(f);
						cell.setStyle(style);
						cell.setValue(NGAY);
//					createBorderSetting(workbook, "G" + Integer.toString(i));
					
						cell = cells.getCell("H" + Integer.toString(i));
						style = cell.getStyle();
						style.setFont(f);
						cell.setStyle(style);
						cell.setValue(" ");
//					createBorderSetting(workbook, "H" + Integer.toString(i));
					
						cell = cells.getCell("I" + Integer.toString(i));
						style = cell.getStyle();
						style.setFont(f);
						style.setHAlignment(TextAlignmentType.RIGHT);
						cell.setStyle(style);
						if(Double.parseDouble(TIENMAT) != 0){
							cell.setValue(formatter.format(Double.parseDouble(TIENMAT)));
//					createBorderSetting(workbook, "I" + Integer.toString(i));
						}else{
							cell.setValue(" ");
						}

						
						cell = cells.getCell("J" + Integer.toString(i));
						style = cell.getStyle();
						style.setFont(f);
						style.setHAlignment(TextAlignmentType.RIGHT);
						cell.setStyle(style);

						if(Double.parseDouble(CHUYENKHOAN) != 0){
							cell.setValue(formatter.format(Double.parseDouble(CHUYENKHOAN)));
						}else{
							cell.setValue(" ");
						}
//					createBorderSetting(workbook, "J" + Integer.toString(i));

						i++;
					}
				}

				f = new Font();
				f.setBold(true);
				f.setItalic(true);
				f.setSize(8);		
				style = cell.getStyle();
				style.setFont(f);						

				cell = cells.getCell("A" + Integer.toString(i));
				
				cell.setStyle(style);
				cell.setValue("Phải trả cuối kỳ");
//				createBorderSetting(workbook, "A" + Integer.toString(i));
				tongPTCK += tongTM + tongTT;
				style.setHAlignment(TextAlignmentType.RIGHT);
				cell = cells.getCell("B" + Integer.toString(i));
				
				cell.setStyle(style);
				
				cell.setValue(formatter.format(tongPTCK));
//				createBorderSetting(workbook, "B" + Integer.toString(i));
				
				ttongPTCK += tongPTCK;

				cell = cells.getCell("I" + Integer.toString(i));
				cell.setStyle(style);
				style.setHAlignment(TextAlignmentType.RIGHT);
				cell.setValue(formatter.format(tongTM));
//				createBorderSetting(workbook,  "C" + Integer.toString(i));
				
				ttongTM += tongTM;
			
				cell = cells.getCell("J" + Integer.toString(i));
				cell.setStyle(style);
				style.setHAlignment(TextAlignmentType.RIGHT);
				cell.setValue(formatter.format(tongTT));
				
//				createBorderSetting(workbook, "I" + Integer.toString(i));
				
				
//				createBorderSetting(workbook, "J" + Integer.toString(i));
				
				// System.out.println("ta o day");
				i++;
				// System.out.println("tong: "+ tongTienDH);

				f.setSize(10);
				style.setFont(f);
				// f.setItalic(false);
				cell = cells.getCell("A" + i);
				cell.setStyle(style);
				cell.setValue("Tổng Cộng");
				
				

//				createBorderSetting(workbook, "A" + i);

				// f = new Font();
				f.setBold(true);
				 f.setItalic(false);
				cell = cells.getCell("B" + i);
				cell.setStyle(style);
				style.setHAlignment(TextAlignmentType.RIGHT);
				cell.setValue(formatter.format(ttongPTCK));
				
//				createBorderSetting(workbook, "C" + i);

				// f = new Font();
				// f.setBold(true);
				// f.setItalic(false);
				//cell = cells.getCell("I" + Integer.toString(i));
				//style = cell.getStyle();
				//style.setHAlignment(TextAlignmentType.RIGHT);
				//cell.setValue(formatter.format(ttongTM));
//				createBorderSetting(workbook, "H" + Integer.toString(i));

				//cell = cells.getCell("J" + Integer.toString(i));
				//cell.setStyle(style);
				
				//cell.setValue(formatter.format(ttongTT));
				
//				createBorderSetting(workbook, "J" + Integer.toString(i));

				
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(e.toString());
			}
		}

	}

	@Override
	public void getCellStyle(Workbook workbook, String cellName, Color color,
			Boolean bold, int size) {
		// TODO Auto-generated method stub
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);

		Cells cells = worksheet.getCells();
		Style style;
		Cell cell = cells.getCell(cellName);
		style = cell.getStyle();
		style.setHAlignment(TextAlignmentType.CENTER);
		Font font1 = new Font();
		font1.setColor(color);
		font1.setBold(bold);
		font1.setSize(size);
		style.setFont(font1);
		cell.setStyle(style);

	}

	@Override
	public void createBorderSetting(Workbook workbook, String fileName) {
		// TODO Auto-generated method stub

		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		Cells cells = worksheet.getCells();
		Cell cell;
		Style style;

		cell = cells.getCell(fileName);
		style = cell.getStyle();

		// Set border color
		style.setBorderColor(BorderType.TOP, Color.BLACK);
		style.setBorderColor(BorderType.BOTTOM, Color.BLACK);
		style.setBorderColor(BorderType.LEFT, Color.BLACK);
		style.setBorderColor(BorderType.RIGHT, Color.BLACK);
		// style.setBorderColor(BorderType.DIAGONAL_DOWN, Color.BLACK);
		// style.setBorderColor(BorderType.DIAGONAL_UP, Color.BLACK);

		// Set the cell border type
		style.setBorderLine(BorderType.TOP, BorderLineType.THIN);
		style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
		style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
		style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
		// style.setBorderLine(BorderType.DIAGONAL_DOWN, BorderLineType.DASHED);
		// style.setBorderLine(BorderType.DIAGONAL_UP, BorderLineType.DASHED);

		cell.setStyle(style);

	}

	@Override
	public String getDateTime() {
		// TODO Auto-generated method stub
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy : hh-mm-ss");
		Date date = new Date();
		return dateFormat.format(date);
	}

	@Override
	public void dbClose() {
		// TODO Auto-generated method stub
		try {
			if (rs != null)
				rs.close();

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

}
