package geso.dms.distributor.beans.reports.imp;

import java.io.Serializable;
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
import geso.dms.distributor.beans.reports.IBCCongNoTheoHD;
import geso.dms.distributor.beans.reports.ITomTatCongNoTrongKy;
import geso.dms.distributor.db.sql.dbutils;

public class TomTatCongNoTrongKy implements ITomTatCongNoTrongKy, Serializable {

	private String userId;
	private String userName;
	private String tuNgay;
	private String denNgay;
	private String nppId;
	private String ngayKS;
	private ResultSet rs;
	
	public TomTatCongNoTrongKy(){
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
		if(userId != null)
			this.userId = userId;
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
		if(tuNgay != null)
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
		if(denNgay != null)
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
	public String getNgayKS() {
		// TODO Auto-generated method stub
		Utility ut = new Utility();
		this.nppId = ut.getIdNhapp(userId);
		
		dbutils db = new dbutils();
		ResultSet rs = db.get("select max(ngayks) from khoasongay where npp_fk='"+this.nppId+"'");
		if(rs != null)
		{
			try {
				rs.next();
				this.ngayKS = rs.getString("ngayks");
			} catch(Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally{try {
				rs.close();
			} catch(Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}}
			
			
		}
		if(db != null)
			db.shutDown();
		return this.ngayKS;

	}

	@Override
	public ResultSet getRS() {
		// TODO Auto-generated method stub
		return this.rs;
	}
	
	@Override
	public void createStaticHeader(Workbook workbook) {
		// TODO Auto-generated method stub
		Utility ut = new Utility();
		this.nppId = ut.getIdNhapp(userId);
		
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	   	   
	    Cells cells = worksheet.getCells();
	   
	   	Style style;
	    //cells.setColumnWidth(0, 200.0f);
	    cells.setRowHeight(0, 20.0f);
	    Cell cell = cells.getCell("A1");  
	    cell.setValue("NPP " + this.userName);   		      
	    style = cell.getStyle();
	  
	    Font f = new Font();
	    //f.setColor(Color.RED);//mau chu
	    //f.setSize(16);// size chu
	    f.setBold(true);
	    
	    style.setFont(f); 
	    style.setHAlignment(TextAlignmentType.LEFT);// canh le cho chu       
	    cell.setStyle(style);
    
	    f = new Font();
	    cell = cells.getCell("E2");
	    cell.setValue("Tóm Tắt Công Nợ Trong Kỳ");
	     		      
	    style = cell.getStyle();
	  
	    
	    //font3.setColor(Color.RED);//mau chu
	    f.setSize(14);// size chu
	    f.setBold(true);
	    style.setFont(f); 
	    style.setHAlignment(TextAlignmentType.CENTER);// canh le cho chu       
	    cell.setStyle(style);
	    
	    f = new Font();
	    cell = cells.getCell("E4");
	    cell.setValue("Từ " + tuNgay + " đến " + denNgay + "");
	    
	    f.setBold(true);
	    style = cell.getStyle();
	    style.setFont(f);
	    style.setHAlignment(TextAlignmentType.CENTER);// canh le cho chu
	    cell.setStyle(style);
	    
	    f = new Font();
	    f.setItalic(true);
	    style.setFont(f);
	    style.setHAlignment(TextAlignmentType.LEFT);
	    cell = cells.getCell("A6");
	    cell.setValue("Ghi chú:");
	    cell.setStyle(style);
	    cell = cells.getCell("A7");
	    cell.setValue("KH: Khách hàng; ĐK: Đầu kỳ; CK: Cuối kỳ; CN: Công nợ");
	    cell.setStyle(style);
	    

	    worksheet.setName("bcCongNoTheoHD");
		
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		Utility util = new Utility();
		this.nppId = util.getIdNhapp(userId);
		
		System.out.println("nha ppid: "+this.nppId);
		String dk = "";
		if(!this.tuNgay.equals(""))
			dk += 	" and dh.ngaynhap >= '"+this.tuNgay+"' ";
		if(!this.denNgay.equals(""))
			dk += " and dh.ngaynhap <= '"+this.denNgay+"' ";

		String sql=	"	SELECT 	KH.PK_SEQ AS KHID, KH.TEN + '-' + KH.DIACHI AS KH, (ISNULL(NODAU.NODAU,0) - ISNULL(THUDAU.THANHTOAN,0)) AS NODAU, " +
					" 			ISNULL(HD.HOADON,0) AS HOADON, ISNULL(THANHTOAN.THANHTOAN,0) AS THANHTOAN,  " +
					" 			(ISNULL(NODAU.NODAU,0) - ISNULL(THUDAU.THANHTOAN,0) + ISNULL(HD.HOADON,0) - ISNULL(THANHTOAN.THANHTOAN,0)) AS PHAITHU, " + 
					" 			ISNULL(GHCN.SOTIENNO,0) AS GHCN " +
					"	FROM KHACHHANG KH " + 
					" 	LEFT JOIN " + 
					" 	(  " +
					" 		SELECT 	KH_CN.KHACHHANG_FK AS KHID,  " +
					" 				ISNULL(SUM(KH_CN.SOTIENNO),0) AS NODAU	 " + 
					" 		FROM KHACHHANG_CONGNO KH_CN  " +
					" 		INNER JOIN DONHANG DH ON DH.PK_SEQ = KH_CN.DONHANG_FK  " +
					" 		WHERE DH.NPP_FK='" + this.nppId + "' AND KH_CN.NGAYNO < '"+ this.tuNgay + "' " +
					" 		GROUP BY KH_CN.KHACHHANG_FK " + 
					" ) " +
					" NODAU ON NODAU.KHID = KH.PK_SEQ " +
					" 	LEFT JOIN " + 
					" 	( " +
					" 		SELECT DH.KHACHHANG_FK AS KHID, SUM(PTT.SOTIEN) AS THANHTOAN FROM PHIEUTHANHTOAN PTT " + 
					" 		INNER JOIN DONHANG DH ON DH.PK_SEQ = PTT.DONHANG_FK WHERE PTT.NGAY <'"+ this.tuNgay + "' " + 
					" 		AND DH.NPP_FK='" + this.nppId + "' GROUP BY DH.KHACHHANG_FK " +
					" ) " +
					" THUDAU ON THUDAU.KHID=KH.PK_SEQ " +
					" LEFT JOIN " + 
					" (	 " +
					" 	SELECT 	KH_CN.KHACHHANG_FK AS KHID, SUM(KH_CN.SOTIENNO) " + 
					" 			AS HOADON	FROM KHACHHANG_CONGNO KH_CN  " +
					" 	INNER JOIN DONHANG DH ON DH.PK_SEQ = KH_CN.DONHANG_FK AND KH_CN.NGAYNO >='"+ this.tuNgay + "' AND KH_CN.NGAYNO <='"+ this.denNgay + "' " + 
					" 	WHERE DH.NPP_FK='" + this.nppId + "' GROUP BY KH_CN.KHACHHANG_FK " + 
					" ) " +
					" HD ON HD.KHID = KH.PK_SEQ " + 
					" LEFT JOIN " +
					" (	 " +
						" SELECT DH.KHACHHANG_FK AS KHID, SUM(PTT.SOTIEN) AS THANHTOAN FROM PHIEUTHANHTOAN PTT " + 
						" INNER JOIN DONHANG DH ON DH.PK_SEQ = PTT.DONHANG_FK WHERE PTT.NGAY >='"+ this.tuNgay + "' " + 
						" AND PTT.NGAY <='"+ this.denNgay + "' AND DH.NPP_FK='" + this.nppId + "' GROUP BY DH.KHACHHANG_FK " +
					"  ) " +
					" THANHTOAN ON THANHTOAN.KHID = KH.PK_SEQ  " +
					" LEFT JOIN GIOIHANCONGNO GHCN ON KH.GHCN_FK = GHCN.PK_SEQ " +
					" WHERE KH.NPP_FK='" + this.nppId + "' " + 
							" AND ((ISNULL(NODAU.NODAU,0) - ISNULL(THUDAU.THANHTOAN,0)) > 0 OR ISNULL(HD.HOADON,0) > 0 OR ISNULL(THANHTOAN.THANHTOAN,0)> 0) "+
					" ORDER BY KH.TEN ";
		dbutils db = new dbutils();
		System.out.println("sql jkf: " + sql);
		this.rs = db.get(sql);
	}

	@Override
	public void tieuDe(Workbook workbook, int rowIndex) {
		// TODO Auto-generated method stub
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	   
	    
   	   
	    Cells cells = worksheet.getCells();
	    Cell cell = cells.getCell("A" + rowIndex);  cell.setValue("Nhân viên"); 		
//	    createBorderSetting(workbook,"A" + rowIndex); 
	    getCellStyle(workbook,"A" + rowIndex,Color.BLACK,true,9);
	    cell = cells.getCell("B"  + rowIndex); cell.setValue("Mã KH");		
//	    createBorderSetting(workbook,"B" + rowIndex);	
	    getCellStyle(workbook,"B" + rowIndex,Color.BLACK,true,9);
	    cell = cells.getCell("C"  + rowIndex); cell.setValue("Khách hàng");		
//	    createBorderSetting(workbook,"C" + rowIndex);	
	    getCellStyle(workbook,"C" + rowIndex,Color.BLACK,true,9);
	    cell = cells.getCell("D"  + rowIndex); cell.setValue("Phải thu ĐK");			
//	    createBorderSetting(workbook,"D" + rowIndex);	
	    getCellStyle(workbook,"D" + rowIndex,Color.BLACK,true,9);
	    cell = cells.getCell("E"  + rowIndex); cell.setValue("Hóa đơn");	
//	    createBorderSetting(workbook,"E" + rowIndex);	
	    getCellStyle(workbook,"E" + rowIndex,Color.BLACK,true,9);
	    cell = cells.getCell("F"  + rowIndex); cell.setValue("HĐ trả hàng");		
//	    createBorderSetting(workbook,"F" + rowIndex);	
	    getCellStyle(workbook,"F" + rowIndex,Color.BLACK,true,9);
	    cell = cells.getCell("G"  + rowIndex); cell.setValue("Thanh toán");		
//	    createBorderSetting(workbook,"G" + rowIndex);	
	    getCellStyle(workbook,"G" + rowIndex,Color.BLACK,true,9);
	    cell = cells.getCell("H"  + rowIndex); cell.setValue("Phải thu CK");		
//	    createBorderSetting(workbook,"H" + rowIndex);	
	    getCellStyle(workbook,"H" + rowIndex,Color.BLACK,true,9);
	    cell = cells.getCell("I"  + rowIndex); cell.setValue("Giới hạn CN");		
//	    createBorderSetting(workbook,"I" + rowIndex);	
	    getCellStyle(workbook,"I" + rowIndex,Color.BLACK,true,9);
	    //cells.merge(4, 2, 4, 3);
	    cells.setColumnWidth(0, 10.0f);//NV
		cells.setColumnWidth(1, 10.0f);//MAKH
		cells.setColumnWidth(2, 45.0f);//TENKH
		cells.setColumnWidth(3, 12.0f);//PHAITHUDK
		cells.setColumnWidth(4, 12.0f);	//HD
		cells.setColumnWidth(5, 12.0f);//HD TRA HANG
		cells.setColumnWidth(6, 12.0f);//THANHTOAN
		cells.setColumnWidth(7, 12.0f);//PHAI THU CK
		cells.setColumnWidth(8, 12.0f);//GIOI HAN CN

	}

	@Override
	public void createStaticData(Workbook workbook) {
		// TODO Auto-generated method stub
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();
		if(rs != null){
			try {
				tieuDe(workbook, 8);
						
				
				int i = 9;
				String khId = "";
				Cell cell = null;
				Font f = null;
				Style style = null;
				double tongPTDK = 0;
				double tongHD = 0;
				double tongTHHD = 0;
				double tongTT = 0;
				double tongPTCK = 0;
				double tongGHCN = 0;
				while (rs.next()) {
					
					

					
					String NVID = " " ; //rs.getString("NVID");
					String NV = " " ; // rs.getString("NV");
    				String KHID = rs.getString("KHID");
    				String KH = rs.getString("KH");
    				
    				String NODAU = rs. getString("NODAU");
    				tongPTDK += Double.parseDouble(NODAU);
    				
    				String HOADON = rs.getString("HOADON");
    				tongHD += Double.parseDouble(HOADON);
    				
    				String TRAHANGHD = " ";
    				tongTHHD =  0;
    				
    				String THANHTOAN = rs.getString("THANHTOAN");
    				tongTT += Double.parseDouble(THANHTOAN);
    				
    				String PHAITHUCK = rs.getString("PHAITHU");
    				tongPTCK += Double.parseDouble(PHAITHUCK);
    				
    				String GHCN = rs.getString("GHCN");
    				tongGHCN += Double.parseDouble(GHCN);
    				
    				
					cell = cells.getCell("A" + Integer.toString(i)); cell.setValue(" ");			//createBorderSetting(workbook,"A" + Integer.toString(i));
					cell = cells.getCell("B" + Integer.toString(i));
					style = cell.getStyle();
					style.setHAlignment(TextAlignmentType.CENTER);
					cell.setStyle(style);
					cell.setValue(KHID);		// createBorderSetting(workbook,"B" + Integer.toString(i));
					cell = cells.getCell("C" + Integer.toString(i)); cell.setValue(KH);			//createBorderSetting(workbook,"C" + Integer.toString(i));
					cell = cells.getCell("D" + Integer.toString(i)); cell.setValue(Double.parseDouble(NODAU));	//createBorderSetting(workbook,"D" + Integer.toString(i));
					cell = cells.getCell("E" + Integer.toString(i)); cell.setValue(Double.parseDouble(HOADON));		//createBorderSetting(workbook,"E" + Integer.toString(i));
					cell = cells.getCell("F" + Integer.toString(i)); cell.setValue(" ");		//createBorderSetting(workbook,"F" + Integer.toString(i));
					cell = cells.getCell("G" + Integer.toString(i)); cell.setValue(Double.parseDouble(THANHTOAN));	//		createBorderSetting(workbook,"G" + Integer.toString(i));
					cell = cells.getCell("H" + Integer.toString(i)); cell.setValue(Double.parseDouble(PHAITHUCK));	//createBorderSetting(workbook,"H" + Integer.toString(i));
					cell = cells.getCell("I" + Integer.toString(i)); cell.setValue(Double.parseDouble(GHCN));		//createBorderSetting(workbook,"I" + Integer.toString(i));
			
					i++;

				}
				
				//System.out.println("tong: "+ tongTienDH);
				
				f = new Font();
				f.setBold(true);
				//f.setItalic(false);
				cell = cells.getCell("A" + i);
				cell.setValue("Tổng Cộng");
				style = cell.getStyle();
				style.setFont(f);
				cell.setStyle(style);
//				createBorderSetting(workbook,"A" + i);
				
				
				//f = new Font();
				//f.setBold(true);
				//f.setItalic(false);
				cell = cells.getCell("D" + i);
				cell.setValue(tongPTDK);
				style = cell.getStyle();
				style.setFont(f);
				cell.setStyle(style);
//				createBorderSetting(workbook,"D" + i);
				
				
				//f = new Font();
				//f.setBold(true);
				//f.setItalic(false);
				cell = cells.getCell("E" + Integer.toString(i));
				cell.setValue(tongHD);
				style = cell.getStyle();
				style.setFont(f);
				cell.setStyle(style);
//				createBorderSetting(workbook,"E" + Integer.toString(i));
				
				cell = cells.getCell("F" + Integer.toString(i));
				cell.setValue(" ");
				style = cell.getStyle();
				style.setFont(f);
				cell.setStyle(style);
//				createBorderSetting(workbook,"F" + Integer.toString(i));
				
				cell = cells.getCell("G" + Integer.toString(i));
				cell.setValue(tongTT);
				style = cell.getStyle();
				style.setFont(f);
				cell.setStyle(style);
//				createBorderSetting(workbook,"G" + Integer.toString(i));

				cell = cells.getCell("H" + Integer.toString(i));
				cell.setValue(tongPTCK);
				style = cell.getStyle();
				style.setFont(f);
				cell.setStyle(style);
//				createBorderSetting(workbook,"H" + Integer.toString(i));
				
				cell = cells.getCell("I" + Integer.toString(i));
				cell.setValue(tongGHCN);
				style = cell.getStyle();
				style.setFont(f);
				cell.setStyle(style);
//				createBorderSetting(workbook,"I" + Integer.toString(i));


				
				
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

        //Set border color
        style.setBorderColor(BorderType.TOP, Color.BLACK);
        style.setBorderColor(BorderType.BOTTOM, Color.BLACK);
        style.setBorderColor(BorderType.LEFT, Color.BLACK);
        style.setBorderColor(BorderType.RIGHT, Color.BLACK);
        //style.setBorderColor(BorderType.DIAGONAL_DOWN, Color.BLACK);
        //style.setBorderColor(BorderType.DIAGONAL_UP, Color.BLACK);

        //Set the cell border type
        style.setBorderLine(BorderType.TOP, BorderLineType.THIN);
        style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
        style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
        style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
        //style.setBorderLine(BorderType.DIAGONAL_DOWN, BorderLineType.DASHED);
        //style.setBorderLine(BorderType.DIAGONAL_UP, BorderLineType.DASHED);

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
			if(rs !=null)
				rs.close();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
	}



}
