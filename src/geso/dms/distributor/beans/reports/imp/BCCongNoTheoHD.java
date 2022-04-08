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
import geso.dms.distributor.db.sql.dbutils;

public class BCCongNoTheoHD implements IBCCongNoTheoHD, Serializable {

	private String userId;
	private String userName;
	private String tuNgay;
	private String denNgay;
	private String nppId;
	private String ngayKS;
	private ResultSet rs;
	
	public BCCongNoTheoHD(){
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
		if(userId != null)
			this.userId = userId;
	}

	@Override
	public String getUserId() {
		return this.userId;
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
		ResultSet rs = db.get("select * from khoasongay where npp_fk='"+this.nppId+"'");
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
		
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	   	   
	    Cells cells = worksheet.getCells();
	   
	   	Style style;
	    //cells.setColumnWidth(0, 200.0f);
	    cells.setRowHeight(0, 20.0f);
	    Cell cell = cells.getCell("A1");  
	    cell.setValue(this.userName);   		      
	    style = cell.getStyle();
	  
	    Font f = new Font();
	    //f.setColor(Color.RED);//mau chu
	    //f.setSize(16);// size chu
	    f.setBold(true);
	    
	    style.setFont(f); 
	    style.setHAlignment(TextAlignmentType.LEFT);// canh le cho chu       
	    cell.setStyle(style);
    
	    f = new Font();
	    cell = cells.getCell("C2");
	    cell.setValue("Công Nợ Theo Từng Hóa Đơn");
	     		      
	    style = cell.getStyle();
	  
	    
	    //font3.setColor(Color.RED);//mau chu
	    f.setSize(14);// size chu
	    f.setBold(true);
	    style.setFont(f); 
	    style.setHAlignment(TextAlignmentType.CENTER);// canh le cho chu       
	    cell.setStyle(style);
	    
	    f = new Font();
	    cell = cells.getCell("C4");
	    cell.setValue("Tính đến ngày khóa sổ: " + this.ngayKS);
	    
	    f.setBold(true);
	    style = cell.getStyle();
	    style.setFont(f);
	    style.setHAlignment(TextAlignmentType.CENTER);// canh le cho chu
	    cell.setStyle(style);
	    
/*	    f = new Font();
	    f.setItalic(true);
	    style.setFont(f);
//	    style.setHAlignment(TextAlignmentType.LEFT);
//	    cell = cells.getCell("A6");
//	    cell.setValue("Ghi chú:");
	    cell.setStyle(style);
	    cell = cells.getCell("A7");
//	    cell.setValue("HD: Hóa đơn; CT TT: Chứng từ thanh toán");
	    cell.setStyle(style);*/
	    

	    worksheet.setName("bcCongNoTheoHD");
		
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		Utility ut = new Utility();
		this.nppId = ut.getIdNhapp(userId);
		String dk = "";
		if(!this.tuNgay.equals(""))
			dk += 	" and dh.ngaynhap >= '"+this.tuNgay+"' ";
		if(!this.tuNgay.equals(""))
			dk += " and dh.ngaynhap <= '"+this.denNgay+"' ";
		
		String sql = "SELECT TOP(1) NGAYKS FROM KHOASONGAY WHERE NPP_FK = '"+ this.nppId +"' ORDER BY NGAYKS DESC";
		dbutils db = new dbutils();
		ResultSet ks = db.get(sql);
		try{
			ks.next();
			this.ngayKS = ks.getString("ngayks");
			
			sql=" SELECT  	substring(kh.smartid ,11,len(kh.smartid)-10) as smartid, KH.PK_SEQ AS KHID, KH.TEN AS TENKH,	"+    	 
				" DH.PK_SEQ AS DHID, DH.NGAYNHAP AS NGAYDH,    	 	"+
				" KH_CN.SOTIENNO AS TIENDH ,0 as SOCHUNGTU, 0 AS TIENTHANHTOAN	"+  	 
				" FROM DONHANG DH    	"+
				" INNER JOIN KHACHHANG_CONGNO KH_CN ON KH_CN.DONHANG_FK = DH.PK_SEQ 	"+
				" INNER JOIN KHACHHANG KH ON KH.PK_SEQ = DH.KHACHHANG_FK 	"+ 	 
				" WHERE DH.NPP_FK = '"+this.nppId+"'  AND  	 	"+
				" KH_CN.DONHANG_FK NOT IN (SELECT DONHANG_FK FROM PHIEUTHANHTOAN WHERE NGAY <= '"+this.ngayKS+"')	"+  	
				" AND KH_CN.NGAYNO <='"+this.ngayKS+"'	"+
				" UNION   		"+
				" SELECT substring(kh.smartid ,11,len(kh.smartid)-10) as smartid,KH.PK_SEQ,KH.TEN AS TENKH,	"+
				" CN.DONHANG_FK AS DHID,DH.NGAYNHAP AS NGAYDH,CN.SOTIENNO AS TIENDH,	"+  	
				" PTT.PK_SEQ AS SOCHUNGTU,	"+
				" TT.TT AS TIENTHANHTOAN  	 	"+
				" from KHACHHANG_CONGNO CN  		"+
				" INNER JOIN KHACHHANG KH ON KH.PK_SEQ=CN.KHACHHANG_FK 	"+ 	
				" INNER JOIN DONHANG DH ON DH.PK_SEQ=CN.DONHANG_FK	"+  		 
				" INNER JOIN	"+
				" (	"+
				" SELECT DH.PK_SEQ AS DHID, SUM(PTT.SOTIEN) AS TT	"+ 
				" from PHIEUTHANHTOAN PTT  	"+
				" INNER JOIN DONHANG DH ON DH.PK_SEQ = PTT.DONHANG_FK	"+
				" WHERE PTT.NGAY <='"+this.ngayKS+"'	"+ 
				" GROUP BY DH.PK_SEQ	"+
				" )TT ON TT.DHID = CN.DONHANG_FK 	"+
				" LEFT JOIN PHIEUTHANHTOAN PTT ON PTT.DONHANG_FK=CN.DONHANG_FK	"+  
				" where CN.SOTIENNO- TT.TT >10 	"+
				" AND DH.NPP_FK= '"+this.nppId+"' AND CN.NGAYNO <='"+this.ngayKS+"'	"+
				" ORDER BY DH.PK_SEQ ,KH.TEN";
				//" 						 ORDER BY KH.TEN, SOCHUNGTU	";
		System.out.println("sql jkf: " + sql);
		this.rs = db.get(sql);
		}catch(Exception e ){}
	}

	@Override
	public void tieuDe(Workbook workbook, int rowIndex) {
		// TODO Auto-generated method stub
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	   
	    
   	   
	    Cells cells = worksheet.getCells();
	    Cell cell = cells.getCell("A" + rowIndex);  	    
	    cell.setValue("Số hóa đơn"); 		//createBorderSetting(workbook,"A" + rowIndex); 
	    getCellStyle(workbook,"A" + rowIndex,Color.BLACK,true,9);
	    
	    cell = cells.getCell("C"  + rowIndex); 	    
	    cell.setValue("Chứng từ thành toán");		//createBorderSetting(workbook,"B" + rowIndex);	
	    getCellStyle(workbook,"C" + rowIndex,Color.BLACK,true,9);
	    
	    cell = cells.getCell("E"  + rowIndex); cell.setValue("Ngày hóa đơn");			//createBorderSetting(workbook,"C" + rowIndex);	
	    getCellStyle(workbook,"E" + rowIndex,Color.BLACK,true,9);
	    
	    cell = cells.getCell("G"  + rowIndex); cell.setValue("Tiền hóa đơn");	//createBorderSetting(workbook,"D" + rowIndex);	
	    getCellStyle(workbook,"G" + rowIndex,Color.BLACK,true,9);
	    
	    cell = cells.getCell("I"  + rowIndex); cell.setValue("Thanh toán");		//createBorderSetting(workbook,"E" + rowIndex);	
	    getCellStyle(workbook,"I" + rowIndex,Color.BLACK,true,9);
	    
	    cells.setColumnWidth(0, 15.0f);
	    cells.setColumnWidth(1, 15.0f);
		cells.setColumnWidth(2, 20.0f);
		cells.setColumnWidth(4, 15.0f);
		cells.setColumnWidth(6, 15.0f);
		cells.setColumnWidth(8, 15.0f);	
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
				double tongTienDH = 0;
				double tongTienTT = 0;
				double tmpttkh=0;
				double tmphdkh=0;
				String hoadonbk="";
				String soHD="";
				String tienHD="";
				String ctTT = "";
				String ngayHD = "";
				String tientt ="";
				double tmp=0;
				
				while (rs.next()) {
					
					if(khId.length()==0 )
					{
						khId=rs.getString("smartid") +"-"+ rs.getString("khid") + " - " + rs.getString("tenkh");
					}
					
					if(!((rs.getString("smartid") +"-"+ rs.getString("khid") + " - " + rs.getString("tenkh")).equals(khId))){
						i=i+1;
						f = new Font();
						f.setSize(8);
						f.setBold(true);
						f.setItalic(true);
						cell = cells.getCell("A" + Integer.toString(i));
						cell.setValue(khId);
						style = cell.getStyle();
						style.setFont(f);
						cell.setStyle(style);
//						createBorderSetting(workbook,"A" + Integer.toString(i));
						
						
						//
						cell = cells.getCell("G" + Integer.toString(i));
						cell.setValue(tmphdkh);
						style = cell.getStyle();
						style.setFont(f);
						cell.setStyle(style);
//						createBorderSetting(workbook,"A" + Integer.toString(i));
						//
						cell = cells.getCell("I" + Integer.toString(i));
						cell.setValue(tmpttkh);
						style = cell.getStyle();
						style.setFont(f);
						cell.setStyle(style);
//						createBorderSetting(workbook,"A" + Integer.toString(i));
						
						tmphdkh=0;
						tmpttkh=0;
						
						khId =rs.getString("smartid") +"-"+ rs.getString("khid") + " - " + rs.getString("tenkh");
						// tong tien thanh theo khach hang
	
									
						i++;
					}	
						 soHD = rs.getString("dhid");
							if(soHD.equals(hoadonbk)){
								tienHD ="0";
								
							}else{
								tienHD = rs. getString("tiendh");
			    				tongTienDH += Double.parseDouble(tienHD);
			    				hoadonbk=soHD;
							}
							
							System.out.println(i +" -- "+tongTienDH);
							
		    				 ctTT = rs.getString("SOCHUNGTU");
//		    				String ngayTT = rs.getString("ngaythanhtoan");;
		    				 ngayHD = rs.getString("ngaydh");
		    				
		    				
		    				 tientt = rs.getString("TIENTHANHTOAN");
		    				tongTienTT += Double.parseDouble(tientt);
							f = new Font();						
							f.setSize(8);

									//createBorderSetting(workbook,"A" + Integer.toString(i));
							cell = cells.getCell("B" + Integer.toString(i));
							style = cell.getStyle();
							style.setFont(f);
							style.setHAlignment(TextAlignmentType.CENTER);
							cell.setStyle(style);
							cell.setValue(soHD);
//							cell.setValue(ctTT);
							
							cell = cells.getCell("C" + Integer.toString(i));
							style = cell.getStyle();
							style.setFont(f);
							style.setHAlignment(TextAlignmentType.CENTER);
							cell.setStyle(style);
							if(ctTT.equals("0"))
								cell.setValue(" ");
							else
								cell.setValue(ctTT);
//							cell.setValue(ctTT);					
							//createBorderSetting(workbook,"B" + Integer.toString(i));
							
							cell = cells.getCell("E" + Integer.toString(i)); 
							style = cell.getStyle();
							style.setFont(f);
							style.setHAlignment(TextAlignmentType.CENTER);
							cell.setStyle(style);
							cell.setValue(ngayHD);		
							
							//createBorderSetting(workbook,"C" + Integer.toString(i));
							//createBorderSetting(workbook,"D" + Integer.toString(i));
							
							cell = cells.getCell("G" + Integer.toString(i)); 
							style = cell.getStyle();
							style.setFont(f);
							cell.setStyle(style);
							System.out.println("Tien Hoa Don :"+ tienHD);
							cell.setValue(Double.parseDouble(tienHD));		//createBorderSetting(workbook,"E" + Integer.toString(i));
							
							//tien thanh toan
							cell = cells.getCell("I" + Integer.toString(i));
							style = cell.getStyle();
							style.setFont(f);
							style.setHAlignment(TextAlignmentType.RIGHT);
							cell.setStyle(style);
							System.out.println("Tien TT :"+ tientt);
							cell.setValue(Double.parseDouble(tientt));
							tmpttkh=tmpttkh+ Double.parseDouble(tientt);
							tmphdkh=tmphdkh+ Double.parseDouble(tienHD);

							i++;
						System.out.println("BIEN I: "+i);
					
					

				}
				
				//System.out.println("tong: "+ tongTienDH);
				
				//f = new Font();
				f.setBold(true);
				f.setItalic(false);
				cell = cells.getCell("A" + i);
				cell.setValue("Tổng Cộng");
				style = cell.getStyle();
				style.setFont(f);
				cell.setStyle(style);
//				createBorderSetting(workbook,"A" + i);
				
				
				//f = new Font();
				//f.setBold(true);
				//tong cong
				f.setBold(true);
				f.setItalic(false);
				cell = cells.getCell("B" + i);
				cell.setValue(tongTienDH-tongTienTT);
				style = cell.getStyle();
				style.setFont(f);
				cell.setStyle(style);
				
				
				f.setItalic(false);
				cell = cells.getCell("G" + i);
				System.out.println(tongTienDH);
				cell.setValue(tongTienDH);
				style = cell.getStyle();
				style.setFont(f);
				cell.setStyle(style);
//				createBorderSetting(workbook,"D" + i);
				//TONG TIEN THANH TOAN
				
				f.setItalic(false);
				cell = cells.getCell("I" + i);
				cell.setValue(tongTienTT);
				style = cell.getStyle();
				style.setFont(f);
				cell.setStyle(style);
				
				
/*				f = new Font();
				f.setBold(true);
				f.setItalic(true);
				cell = cells.getCell("E" + Integer.toString(i));
				cell.setValue(tongTienTT);
				style = cell.getStyle();
				style.setFont(f);
				cell.setStyle(style);
//				createBorderSetting(workbook,"E" + Integer.toString(i));*/
				
				
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
