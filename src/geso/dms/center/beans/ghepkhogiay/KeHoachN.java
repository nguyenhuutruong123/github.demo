package geso.dms.center.beans.ghepkhogiay;

import geso.dms.distributor.db.sql.dbutils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.aspose.cells.Cell;
import com.aspose.cells.CellArea;
import com.aspose.cells.Cells;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Style;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class KeHoachN {
	private Hashtable<Float, KichBanN> toHopMay;//Key: kho giay nguyen lieu cua may
	//Bua sau chuyen thanh key la id may san xuat

	public KeHoachN()
	{
		toHopMay = new Hashtable<Float, KichBanN>();
	}
	
	public void addToHop(ToHopN toHop)
	{
		float khoGiayNguyenLieu = toHop.getKhoGiayNguyenLieu();
		Set<Float> keys = this.toHopMay.keySet();
		boolean isExit = false;
		for (float key : keys)
		{
			if (key == khoGiayNguyenLieu)
				isExit = true;
		}
		if (isExit == true)
		{
			this.toHopMay.get(toHop.getKhoGiayNguyenLieu()).addToHop(toHop);
		}
		else
		{
			KichBanN kb = new KichBanN();
			kb.addToHop(toHop);
			this.toHopMay.put(toHop.getKhoGiayNguyenLieu(), kb);
		}
	}
	
	public void init(KichBanN kichBan, List<SanPham> sanPhamDonHangList)
	{
		toHopMay = new Hashtable<Float, KichBanN>();
		for (ToHopN toHop : kichBan.getToHopList())
		{
			Set<Float> toHopMayKeys = toHopMay.keySet();
			if (toHop.getSoLuong() == 7)
				System.out.println();
			//Phan bo don hang cho tung san pham trong to hop
//			ToHopN toHopNew = addDonHangToToHop(toHop, sanPhamDonHangList);
//			ToHop toHopNew = toHop;
			boolean isExit = false;
			for (float khoGiayNguyenLieu : toHopMayKeys)
			{
				if (khoGiayNguyenLieu == toHop.getKhoGiayNguyenLieu())
				{
					toHopMay.get(khoGiayNguyenLieu).addToHop(toHop);
					isExit = true;
				}
			}
			
			if (isExit == false)
			{
				KichBanN kichBanTmp = new KichBanN();
				kichBanTmp.addToHop(toHop);
				toHopMay.put(toHop.getKhoGiayNguyenLieu(), kichBanTmp);
			}
		}
	}
	
//	public boolean phanBoMaySanXuat(List<MaySanXuat> maySanXuatList) {
//		List<ToHop> tohopList = new ArrayList<ToHop>();
//		//Xet voi tung may
//		for (MaySanXuat maySanXuat : maySanXuatList)
//		{
//			//Lay kich ban co cung kho giay nguyen lieu voi may san xuat
//			KichBanN kichBan = this.toHopMay.get(maySanXuat.getKhoGiayNguyenLieu());
//			if (kichBan != null)
//			{
//				List<ToHopN> toHopList = kichBan.getToHopList();
////				Set<Float> toHopHashKeys = toHopHash.keySet();
//				//Xet tung to hop co kho giay tieu chuan trung voi may san xuat
//				for (ToHopN toHop : toHopList)
//				{
//					//Neu to hop co dinh luong ma may san xuat co
//					//Hien tai khong tach to hop => sau xet toi cong suat may va thoi gian => can tach to hop
////					if (maySanXuat.isExitDinhLuong(toHop.getDinhLuong()) == true)
////					Set<Float> keyTmps = toHop.getSanPhamHash().keySet();
//					String tenMaySanXuatSp = toHop.getTenMaySanXuatSp();
//					if (toHop.getMaySanXuatId().trim().length() == 0 //toHop chua duoc phan vao may san xuat nao 
//					&& maySanXuat.getTen().trim().equals(tenMaySanXuatSp) == true)
//					{
////						toHop.setMaySanXuatId(maySanXuat.getId());
//						float trongLuongTrong = maySanXuat.getCongSuatMay()* 1000 - maySanXuat.getTrongLuongHienTai();
//						
//						float trongLuongToHop = toHop.getTrongLuong();
//						int nToHop = (int) (trongLuongTrong / trongLuongToHop);
//						if (nToHop >= toHop.getSoLuong())
//							toHop.setMaySanXuatId(maySanXuat.getId());
//						else
//						{
//							if (nToHop > 0)
//							{
////								ToHop toHop1 = new ToHop(toHop);
////								toHop1.truSoLuong(nToHop);
////								toHop1.setSoLuong(toHop.getSoLuong() - nToHop);
//								ToHop toHop1 = toHop.truSoLuong(toHop.getSoLuong() - nToHop);
//								tohopList.add(toHop1);
////								toHop.setSoLuong(nToHop);
//								toHop.setMaySanXuatId(maySanXuat.getId());
//								maySanXuat.setTrongLuongHienTai(maySanXuat.getTrongLuongHienTai() + nToHop * toHop.getTrongLuong());
//							}
//						}
//			/////////////
//					}
//				}
//			}
//		}
//		
//		System.out.println("Start");
//		//Add nhung to hop bi tach, sau do phan bo may san xuat cho nhung to hop nay
//		if (tohopList != null)
//		{
//			for (ToHop toHop : tohopList)
//				addToHop(toHop);
////			if (tohopList.size() > 0)
////				phanBoMaySanXuat(maySanXuatList);
//		}
//		
//		//Kiem tra xem con toHop nao chua duoc phan vao may san xuat
//		boolean isExit = false;
//		for (MaySanXuat maySanXuat : maySanXuatList)
//		{
//			//Lay kich ban co cung kho giay nguyen lieu voi may san xuat
//			KichBan kichBan = this.toHopMay.get(maySanXuat.getKhoGiayNguyenLieu());
//			if (kichBan != null)
//			{
//				Hashtable<Float, ToHop> toHopHash = kichBan.getToHopHash();
//				Set<Float> toHopHashKeys = toHopHash.keySet();
//				//Xet tung to hop co kho giay tieu chuan trung voi may san xuat
//				for (float key : toHopHashKeys)
//				{
//					ToHop toHop = toHopHash.get(key);
//					if (toHop.getMaySanXuatId().trim().length() == 0)
//						isExit = true;
//				}
//			}
//		}
////		
//		//Neu co thi tang cong suat moi may len them gap doi roi phan bo may san xuat lai cho nhung to hop chua duoc phan bo
////		while (isExit == true)
//		if (isExit == true)
//		{
//			System.out.println("To hop chua phan vao may san xuat: " + isExit + " trong luong hien tai: ");
//			//Tang cong suat may
//			for (MaySanXuat maySanXuat : maySanXuatList)
//			{
////				maySanXuat.setCongSuatMay(maySanXuat.getCongSuatMay() * 2);
//				maySanXuat.setTrongLuongHienTai(0);
//			}
//			//Phan bo lai
////			isExit = phanBoMaySanXuat(maySanXuatList);
//			phanBoMaySanXuat(maySanXuatList);
//		}
//		return isExit;
//	}

	private ToHopN addDonHangToToHop(ToHopN toHop, List<SanPham> sanPhamDonHangList)
	{
		ToHopN toHopNew = new ToHopN(toHop.getHaoHut(), toHop.getSoLuong(), toHop.getKhoGiayNguyenLieu(), toHop.getDinhLuong());
		List<SanPham> sanPhamList= toHop.getSanPhamList();
		for (SanPham sanPham : sanPhamList)
		{
			String idSanPham = sanPham.getId();
			int tongSoLuong = sanPham.getSoLuong() * toHop.getSoLuong();
			for (SanPham spDonHang : sanPhamDonHangList)
			{
				String idSpDonHang = spDonHang.getId();
				int soLuongSpDonHang = spDonHang.getSoLuong();
				if (tongSoLuong > 0 && idSpDonHang.trim().equals(idSanPham) == true && soLuongSpDonHang > 0)
				{
					SanPham spNew = new SanPham(sanPham);
					spNew.setTiLe(spNew.getSoLuong());
					spNew.setIdDonHang(spDonHang.getIdDonHang());
					if (tongSoLuong > spDonHang.getSoLuong())
					{
						tongSoLuong = tongSoLuong - spDonHang.getSoLuong();
						spNew.setSoLuong(spDonHang.getSoLuong());
						spDonHang.setSoLuong(0);
					}
					else{
						spDonHang.setSoLuong(spDonHang.getSoLuong() - tongSoLuong);
						spNew.setSoLuong(tongSoLuong);
						tongSoLuong = 0;
					}
					toHopNew.getSanPhamList().add(spNew);
				}
			}
		}
		return toHopNew;
	}
	
	public void init(KichBanN kichBan)
	{
		toHopMay = new Hashtable<Float, KichBanN>();
		for (ToHopN toHop : kichBan.getToHopList())
		{
			Set<Float> toHopMayKeys = toHopMay.keySet();
			boolean isExit = false;
			for (float khoGiayNguyenLieu : toHopMayKeys)
			{
				if (khoGiayNguyenLieu == toHop.getKhoGiayNguyenLieu())
				{
					toHopMay.get(khoGiayNguyenLieu).addToHop(toHop);
					isExit = true;
				}
			}
			if (isExit == false)
			{
				KichBanN kichBanTmp = new KichBanN();
				kichBanTmp.addToHop(toHop);
				toHopMay.put(toHop.getKhoGiayNguyenLieu(), kichBanTmp);
			}
		}
	}


	
	public void xuatExcell(OutputStream outstream, String templateFileName, List<MaySanXuat> maySanXuatList)
	{
		try {
			FileInputStream fstream = null;
			fstream = new FileInputStream(templateFileName);
		Workbook workbook = new Workbook();
		workbook.setFileFormatType(FileFormatType.EXCEL2007);
		try {
			workbook.open(fstream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
		
		Worksheets worksheets = workbook.getWorksheets();
		
		Cell cell = null;
	    Set<Float> toHopMayKeys = this.toHopMay.keySet();
	    //Get style 
	    Worksheet worksheet = worksheets.getSheet("Sheet1");
	    
	    ///
	    ArrayList<CellArea> al = worksheet.getCells().getMergedCells();
	  //Define cellarea
	    CellArea ca;

	    //Define some variables
	    int frow, fcol, erow, ecol;

	    // Print Message
	    System.out.println("Merged Areas: \n"+ al.toString());

	    //Loop through the arraylist and get each cellarea to unmerge it
	    for(int i = al.size()-1 ; i > -1; i--)
	    {
	    	ca = new CellArea();
	    	ca = (CellArea)al.get(i);
	    	frow = ca.getStartRow();
	    	fcol = ca.getStartColumn();
	    	erow = ca.getEndRow();
	    	ecol = ca.getEndColumn();
	    	System.out.println((i+1) + ". [" + fcol +"," + frow +"] " + "[" + ecol +"," + erow +"]");
//	    	worksheet.getCells().unMerge(frow, fcol, erow, ecol);
	    }
	    ///
	    
	    
	    Cells styleCells = worksheet.getCells();
	    //Style STT
	    Cell styleCell = styleCells.getCell("B11");
	    Style STTStyle = styleCell.getStyle();
	    //Style kho giay to hop
	    styleCell = styleCells.getCell("H11");
	    Style khoGiayStyle = styleCell.getStyle();
	    //Style kho giay to hop
	    styleCell = styleCells.getCell("I11");
	    Style soLuongStyle = styleCell.getStyle();
	    
	    //Style ten san pham normal
	    styleCell = styleCells.getCell("C9");
	    Style tenSpStyle = styleCell.getStyle();
	    //Style ten san pham border
	    styleCell = styleCells.getCell("C11");
	    Style tenSpBStyle = styleCell.getStyle();
	    //Style dinh luong san pham normal
	    styleCell = styleCells.getCell("G9");
	    Style dinhLuongSpStyle = styleCell.getStyle();
	    //Style dinh luong san pham border
	    styleCell = styleCells.getCell("G11");
	    Style dinhLuongSpBStyle = styleCell.getStyle();
	    //Style kho giay san pham normal
	    styleCell = styleCells.getCell("J9");
	    Style khoGiaySpStyle = styleCell.getStyle();
	    //Style kho giay san pham border
	    styleCell = styleCells.getCell("J11");
	    Style khoGiaySpBStyle = styleCell.getStyle();
	    //Style so luong san pham normal
	    styleCell = styleCells.getCell("K9");
	    Style soLuongSpStyle = styleCell.getStyle();
	    //Style so luong san pham border
	    styleCell = styleCells.getCell("K11");
	    Style soLuongSpBStyle = styleCell.getStyle();
	    //Style trong luong san pham normal
	    styleCell = styleCells.getCell("L9");
	    Style trongLuongSpStyle = styleCell.getStyle();
	    //Style trong luong san pham border
	    styleCell = styleCells.getCell("L11");
	    Style trongLuongSpBStyle = styleCell.getStyle();
	    //Style khach hang san pham normal*
	    styleCell = styleCells.getCell("N9");
	    Style khachHangSpStyle = styleCell.getStyle();
	    //Style khach hang san pham border
	    styleCell = styleCells.getCell("N11");
	    Style khachHangSpBStyle = styleCell.getStyle();
	    styleCell = styleCells.getCell("G1");
	    System.out.println("styleCell.getValue(): " + styleCell.getValue());
	    
	    int sheetIndex = 0;
	    for (MaySanXuat maySanXuat : maySanXuatList)
	    {
	    	float khoGiayNLMay = maySanXuat.getKhoGiayNguyenLieu();
	    	String idMaySanXuat = maySanXuat.getId();
	    	int i = 1;
	    	int start = 9;
	    	List<ToHopN> toHopList = null;
	    	if (this.toHopMay.get(khoGiayNLMay) != null)
	    		toHopList = this.toHopMay.get(khoGiayNLMay).getToHopList();
	    	//Sheet 1
	    	System.out.println("Sheet index: " + sheetIndex);
		    Worksheet worksheet1 = worksheets.getSheet(sheetIndex);
		    Cells cells1 = worksheet1.getCells();
		    Cell nameCell = cells1.getCell("G1");
		    nameCell.setValue("KẾ HOẠCH SANE XUẤT - MÁY " + maySanXuat.getTen() + " " + maySanXuat.getKhoGiayNguyenLieu());
		    nameCell = cells1.getCell("G2");
		    nameCell.setValue("The Production schedule for " + maySanXuat.getTen());
		    if (toHopList != null)
		    {
		    for (ToHopN toHop : toHopList)
		    {
		    	if (idMaySanXuat.trim().equals(toHop.getMaySanXuatId()) == true)
		    	{
			    	List<SanPham> sanPhamList = toHop.getSanPhamList();
			    	int rowsNumber = 0;
			    	Style stylet = null;
			    	for (SanPham sanPham : sanPhamList)
			    	{
			    		int index = start + rowsNumber;
						
						//Nhan hang
						cell = cells1.getCell("C" + index);
			    		cell.setValue(sanPham.getTenNhanHang()); 
			    		cell.setStyle(tenSpStyle);
	
						//Nhan hang con
						cell = cells1.getCell("D" + index);
			    		cell.setValue(""); 
			    		cell.setStyle(tenSpStyle);
	
			    		
			    		//Id san pham
			    		cell = cells1.getCell("F" + index); 
			    		cell.setValue(sanPham.getMa()); 
			    		cell.setStyle(tenSpStyle);
			    		
			    		//Dinh luong san pham
			    		cell = cells1.getCell("G" + index); 
			    		cell.setValue(sanPham.getDinhLuong()); 
			    		cell.setStyle(dinhLuongSpStyle);
			    		
			    		//Kho giay san pham
			    		cell = cells1.getCell("J" + index); 
			    		cell.setValue(sanPham.getKhoGiay()); 
			    		cell.setStyle(khoGiaySpStyle);
			    		
			    		//So luong san pham
			    		cell = cells1.getCell("K" + index); 
			    		cell.setValue(sanPham.getSoLuong() * toHop.getSoLuong()); 
			    		cell.setStyle(soLuongSpStyle);
			    		
			    		//Id don hang
			    		cell = cells1.getCell("M" + index); 
			    		cell.setValue(sanPham.getIdDonHang()); 
			    		cell.setStyle(khachHangSpStyle);
			    		
			    		//Trong luong san pham
			    		float trongLuong = (float) (sanPham.getSoLuong() * (sanPham.getKhoGiay() * 7.5 + sanPham.getDinhLuong() - 115));
			    		cell = cells1.getCell("L" + index); 
			    		cell.setValue(trongLuong); 
			    		cell.setStyle(trongLuongSpStyle);
			    		
			    		//Ten khach hang
			    		cell = cells1.getCell("N" + index); 
			    		cell.setValue(sanPham.getTenKhachHang()); 
			    		cell.setStyle(khachHangSpStyle);
			    		
			    		//Ngay nhap
			    		cell = cells1.getCell("O" + index); 
			    		cell.setValue(""); 
			    		cell.setStyle(khachHangSpStyle);
	
			    		//Chu y
			    		cell = cells1.getCell("P" + index); 
			    		cell.setValue(""); 
			    		cell.setStyle(khachHangSpStyle);
	
			    		rowsNumber++;
			    	}
			    	int end = start + rowsNumber - 1;
			    	int start1 = start - 1;
			    	int end1 = (start + rowsNumber - 1 - 1);
			    	//STT
			    	cells1.merge(start1, 1, end1, 1);
			    	cell = cells1.getCell("B" + (start)); //cell.setValue(i);
			    	cell.setValue(i);
			    	setStyleForMergeCell(cells1, "B", STTStyle, start, end);
			    	
					//Kho giay to hop
					cell = cells1.getCell("H" + (start + rowsNumber - 1)); cell.setValue(toHop.getKhoGiayNguyenLieu() - toHop.getHaoHut());cell.setStyle(stylet);
					cells1.merge(start1, 7, end1, 7);
			    	setStyleForMergeCell(cells1, "H", khoGiayStyle, start, end);
			    	
			    	//So luong to hop
					cell = cells1.getCell("I" + (start + rowsNumber - 1)); 
					cell.setValue(toHop.getSoLuong());
					cells1.merge(start - 1, 8, end1, 8);
			    	setStyleForMergeCell(cells1, "I", soLuongStyle, start, end);
	
			    	////////
			    	//Nhan hang
					cell = cells1.getCell("C" + end);
		    		cell.setStyle(tenSpBStyle);
	
					//Nhan hang con
					cell = cells1.getCell("D" + end);
		    		cell.setStyle(tenSpBStyle);
	
		    		//Id san pham
		    		cell = cells1.getCell("F" + end); 
		    		cell.setStyle(tenSpBStyle);
		    		
		    		//Dinh luong san pham
		    		cell = cells1.getCell("G" + end); 
		    		cell.setStyle(dinhLuongSpBStyle);
		    		
		    		//Kho giay san pham
		    		cell = cells1.getCell("J" + end); 
		    		cell.setStyle(khoGiaySpBStyle);
		    		
		    		//So luong san pham
		    		cell = cells1.getCell("K" + end); 
		    		cell.setStyle(soLuongSpBStyle);
		    		
		    		//Id don hang
		    		cell = cells1.getCell("M" + end); 
		    		cell.setStyle(khachHangSpBStyle);
		    		
		    		//Trong luong san pham
		    		cell = cells1.getCell("L" + end); 
		    		cell.setStyle(trongLuongSpBStyle);
		    		
		    		//Ten khach hang
		    		cell = cells1.getCell("N" + end); 
		    		cell.setStyle(khachHangSpBStyle);
		    		
		    		//Ngay nhap
		    		cell = cells1.getCell("O" + end); 
		    		cell.setStyle(khachHangSpBStyle);
	
		    		//Chu y
		    		cell = cells1.getCell("P" + end); 
		    		cell.setStyle(khachHangSpBStyle);
	
			    	///////
			    	start += rowsNumber;
			    	i++;
			    }
		    }
	    }
		    worksheet1.setName(maySanXuat.getTen());
		    sheetIndex++;
	    }
	    try {
			workbook.save(outstream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			fstream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void setStyleForMergeCell(Cells cells1, String column,
			Style style, int start, int end) {
		// TODO Auto-generated method stub
		for (int i = start; i <= end; i ++)
		{
			Cell cell = cells1.getCell(column + i);
			cell.setStyle(style);
		}
	}

	public void setToHopMay(Hashtable<Float, KichBanN> toHopMay) {
		this.toHopMay = toHopMay;
	}

	public Hashtable<Float, KichBanN> getToHopMay() {
		return toHopMay;
	}

	public void importFromExcell(String fileName, dbutils db, List<MaySanXuat> maySanXuatList) {
//		if (fileName.contains(".xlsx") || fileName.contains(".XLSX"))
		{
			readXLSXFile(fileName, db, maySanXuatList);
			return;
		}
//		if (fileName.contains(".xls") || fileName.contains(".XLS"))
//			readXLSFile(fileName, db, maySanXuatList);
	}
	
//	private void readXLSXFile(String fileName, dbutils db)
//	{
//		
//	}
	private void readXLSXFile(String fileName, dbutils db, List<MaySanXuat> maySanXuatList)
	{
		String msg = "";
		try 
		{
			InputStream ExcelFileToRead = new FileInputStream(fileName);
			XSSFWorkbook wb = new XSSFWorkbook(ExcelFileToRead);
			int sosheet=wb.getNumberOfSheets();
			ToHopN toHop = null;
			for(int ii=0;ii<sosheet;ii++)
			{
				XSSFSheet sheet = wb.getSheetAt(ii);
				String maMaySanXuat = wb.getSheetName(ii);
				String idMaySanXuat = null;
				for (MaySanXuat  maySanXuat : maySanXuatList)
				{
					if (maMaySanXuat.trim().equals(maySanXuat.getTen()) == true)
						idMaySanXuat = maySanXuat.getId();
				}
				System.out.println("===============Shett " + wb.getSheetName(ii) + "===============");
//				XSSFSheet sheet = wb.getSheet("PM4");
				Row row;
				Iterator rows = sheet.rowIterator();
				int rowIndex = 0;
				//	System.out.println("[rows][soNpp]"+rowFixed.getLastCellNum());
				String khoGiayNguyenLieu = null;
				while (rows.hasNext()) 
				{
					row = (Row)rows.next();
					if (rowIndex == 0)
						khoGiayNguyenLieu = (getContent(row, 6)).split(" ")[(getContent(row, 6)).split(" ").length - 1];
					if(rowIndex >= 8)
					{
						String soThuTu = (getContent(row, 1));
						System.out.println("STT " + rowIndex + " : " + soThuTu);
						System.out.println("tensp : " + getContent(row, 2));
						System.out.println("idsanPham : " + getContent(row, 5));
						System.out.println("dinhLuong : " + getContent(row, 6));
						System.out.println("kho ngl : " + getContent(row, 7));
						System.out.println("so luong nguyen lieu: " + getContent(row, 8));
						System.out.println("kho thanh pham : " + getContent(row, 9));
						System.out.println("so luong thanh pham: " + getContent(row, 10));
						System.out.println("trongluong: " + getContent(row, 11));
						System.out.println("PO number: " + getContent(row, 12));
						System.out.println("khach hang: " + getContent(row, 13));
						if (soThuTu.trim().length() != 0)
						{
							System.out.println("getContent(row, 1): " + getContent(row, 1));
							if (toHop != null)
								this.addToHop(toHop);
							if (!(rowIndex == 8 && soThuTu.trim().length() == 0 && getContent(row, 5).trim().length() > 0))
							{
								toHop = new ToHopN();
								toHop.setMaySanXuatId(idMaySanXuat);
//								toHop.setKhoGiayNguyenLieu(Float.parseFloat(getContent(row, 7)));
								toHop.setKhoGiayNguyenLieu(Float.parseFloat(khoGiayNguyenLieu));
								toHop.setDinhLuong(Float.parseFloat(getContent(row, 6)));
								String s = getContent(row, 8);
								String [] sl = s.trim().split(".", 2);
								float f = Float.parseFloat(getContent(row, 8));
								int soLuong = (int)f;
								toHop.setSoLuong(soLuong);
							}
						}
						if (toHop != null && getContent(row, 5).trim().length() > 0)
						{
							SanPham sanPham = new SanPham();
							toHop.getSanPhamList().add(sanPham);
							sanPham.setTenNhanHang(getContent(row, 2));
							sanPham.setMa(getContent(row, 5));
							sanPham.setDinhLuong(toHop.getDinhLuong());
							sanPham.setKhoGiay(Float.parseFloat(getContent(row, 9)));
							float f = Float.parseFloat(getContent(row, 10));
							int soLuong = (int)f / toHop.getSoLuong();
							System.out.println("So luon excel: " + getContent(row, 10));
							System.out.println("So luon float: " + f);
							System.out.println("So luon int: " + soLuong);
							sanPham.setSoLuong(soLuong);
							sanPham.setTiLe(soLuong);
							sanPham.setIdDonHang(getContent(row, 12));
							sanPham.setTenKhachHang(getContent(row, 13));
						}
					}
					rowIndex++;
				}
			}
			if (toHop != null)
				this.addToHop(toHop);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
//	
//	private void readXLSFile(String fileName, dbutils db)
//	{
//		
//	}
	private void readXLSFile(String fileName, dbutils db, List<MaySanXuat> maySanXuatList)
	{
		String msg = "";
		try 
		{
			InputStream ExcelFileToRead = new FileInputStream(fileName);
			HSSFWorkbook wb = new HSSFWorkbook(ExcelFileToRead);
			int sosheet = wb.getNumberOfSheets();
			for(int ii=0;ii<sosheet;ii++)
			{
				HSSFSheet sheet = wb.getSheetAt(ii);
				HSSFRow row;
				HSSFCell cell;
				Iterator rows = sheet.rowIterator();
				int rowIndex = 0;
				//	System.out.println("[rows][soNpp]"+rowFixed.getLastCellNum());
				int stt = 0;
				ToHopN toHop = null;
				while (rows.hasNext()) 
				{
					row = (HSSFRow)rows.next();
					if(rowIndex > 7)
					{
						Iterator cells = row.cellIterator();
						String idSp;
						String maSp = "";
						String tenSp = "";
						String donGia = "";
						if (cells.hasNext()) 
						{
							cell =(HSSFCell) cells.next();
							String soThuTu = cell.getStringCellValue();
							if (soThuTu.trim().length() == 0)
							{
								if (toHop != null)
									this.addToHop(toHop);
								toHop = new ToHopN();
							}
							SanPham sanPham = new SanPham();
							if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING)
							{
								sanPham.setMa(cell.getStringCellValue());
							}
//							else if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
//							{
//								maSp = String.valueOf(cell.getNumericCellValue());
//							}
//								
//							if (cells.hasNext())
//							{
//								cell =(HSSFCell) cells.next();
//								if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING)
//								{
//									donGia = cell.getStringCellValue();
//								}
//								else if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
//								{
//									donGia = String.valueOf(cell.getNumericCellValue());
//								}
//							}
						}
					
//						if(maSp.trim().length() <= 0)
//							maSp="0";
//						if(donGia.length() <= 0)
//							donGia="0";
//						
//						String query = "SELECT PK_SEQ, TEN FROM SANPHAM WHERE MA = '" + maSp + "'";
//						ResultSet rs = db.get(query);
//						if (rs != null)
//						{
//							if (rs.next())
//							{
//								try {
//									idSp = rs.getString("PK_SEQ");
//									tenSp = rs.getString("TEN");
//									idSpArr.add(idSp);
//									maSpArr.add(maSp);
//									tenSpArr.add(tenSp);
//									donGiaArr.add(donGia);
//									rs.close();
//								} catch (SQLException e) {
//									msg += " " + maSp;
//									System.out.println(e);
//								}
//							}
//							else
//							{
//								msg += " " + maSp;
//							}
//						}
//						else
//						{
//							msg += " " + maSp;
//						}
					}
					rowIndex++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
//	
	private static String getContent(org.apache.poi.ss.usermodel.Row row, int column)
	{
		org.apache.poi.ss.usermodel.Cell cell = row.getCell(column,Row.CREATE_NULL_AS_BLANK);
		return cell.toString();
	}
}
