package geso.dms.center.beans.ghepkhogiay;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import com.aspose.cells.BorderType;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Font;
import com.aspose.cells.Style;
import com.aspose.cells.StyleFlag;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class KeHoach {
	private Hashtable<Float, KichBan> toHopMay;//Key: kho giay nguyen lieu cua may

	public KeHoach()
	{
		toHopMay = new Hashtable<Float, KichBan>();
	}
	
	public void addToHop(ToHop toHop)
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
			KichBan kb = new KichBan();
			kb.addToHop(toHop);
			this.toHopMay.put(toHop.getKhoGiayNguyenLieu(), kb);
		}
	}
	
//	public KeHoach(KichBan kichBan, List<SanPham> sanPhamDonHangList)
//	{
//		toHopMay = new Hashtable<Float, KichBan>();
//		Set<Float> keys = kichBan.getToHopHash().keySet();
//		for (float key : keys)
//		{
//			Set<Float> toHopMayKeys = toHopMay.keySet();
//			ToHop toHop = kichBan.getToHopHash().get(key);
//			boolean isExit = false;
//			for (float khoGiayNguyenLieu : toHopMayKeys)
//			{
//				if (khoGiayNguyenLieu == toHop.getKhoGiayNguyenLieu())
//				{
//					toHopMay.get(toHopMayKeys).addToHop(toHop);
//					isExit = true;
//				}
//			}
//			if (isExit == false)
//			{
//				KichBan kichBanTmp = new KichBan();
//				kichBanTmp.addToHop(toHop);
//				toHopMay.put(toHop.getKhoGiayNguyenLieu(), kichBanTmp);
//			}
//		}
//	}

	
	public void init(KichBan kichBan, List<SanPham> sanPhamDonHangList)
	{
		toHopMay = new Hashtable<Float, KichBan>();
		Set<Float> keys = kichBan.getToHopHash().keySet();
		for (float key : keys)
		{
			Set<Float> toHopMayKeys = toHopMay.keySet();
			ToHop toHop = kichBan.getToHopHash().get(key);
			if (toHop.getSoLuong() == 7)
				System.out.println();
			//Phan bo don hang cho tung san pham trong to hop
			ToHop toHopNew = addDonHangToToHop(toHop, sanPhamDonHangList);
//			ToHop toHopNew = toHop;
			boolean isExit = false;
			for (float khoGiayNguyenLieu : toHopMayKeys)
			{
				if (khoGiayNguyenLieu == toHopNew.getKhoGiayNguyenLieu())
				{
					toHopMay.get(khoGiayNguyenLieu).addToHop(toHopNew);
					isExit = true;
				}
			}
			
			if (isExit == false)
			{
				KichBan kichBanTmp = new KichBan();
				kichBanTmp.addToHop(toHopNew);
				toHopMay.put(toHopNew.getKhoGiayNguyenLieu(), kichBanTmp);
			}
		}
	}
	
	public boolean phanBoMaySanXuat(List<MaySanXuat> maySanXuatList) {
		List<ToHop> tohopList = new ArrayList<ToHop>();
		//Xet voi tung may
		for (MaySanXuat maySanXuat : maySanXuatList)
		{
			//Lay kich ban co cung kho giay nguyen lieu voi may san xuat
			KichBan kichBan = this.toHopMay.get(maySanXuat.getKhoGiayNguyenLieu());
			if (kichBan != null)
			{
				Hashtable<Float, ToHop> toHopHash = kichBan.getToHopHash();
				Set<Float> toHopHashKeys = toHopHash.keySet();
				//Xet tung to hop co kho giay tieu chuan trung voi may san xuat
				for (float key : toHopHashKeys)
				{
					ToHop toHop = toHopHash.get(key);
					//Neu to hop co dinh luong ma may san xuat co
					//Hien tai khong tach to hop => sau xet toi cong suat may va thoi gian => can tach to hop
//					if (maySanXuat.isExitDinhLuong(toHop.getDinhLuong()) == true)
					Set<Float> keyTmps = toHop.getSanPhamHash().keySet();
					String tenMaySanXuatSp = "";
					for (float keyTmp : keyTmps)
					{
						tenMaySanXuatSp = toHop.getSanPhamHash().get(keyTmp).getTenMaySanXuat();
					}
					if (toHop.getMaySanXuatId().trim().length() == 0 //toHop chua duoc phan vao may san xuat nao 
					&& maySanXuat.getTen().trim().equals(tenMaySanXuatSp) == true)
					{
//						toHop.setMaySanXuatId(maySanXuat.getId());
						float trongLuongTrong = maySanXuat.getCongSuatMay()* 1000 - maySanXuat.getTrongLuongHienTai();
						
						float trongLuongToHop = toHop.getTrongLuong();
						int nToHop = (int) (trongLuongTrong / trongLuongToHop);
						if (nToHop >= toHop.getSoLuong())
							toHop.setMaySanXuatId(maySanXuat.getId());
						else
						{
							if (nToHop > 0)
							{
//								ToHop toHop1 = new ToHop(toHop);
//								toHop1.truSoLuong(nToHop);
//								toHop1.setSoLuong(toHop.getSoLuong() - nToHop);
								ToHop toHop1 = toHop.truSoLuong(toHop.getSoLuong() - nToHop);
								tohopList.add(toHop1);
//								toHop.setSoLuong(nToHop);
								toHop.setMaySanXuatId(maySanXuat.getId());
								maySanXuat.setTrongLuongHienTai(maySanXuat.getTrongLuongHienTai() + nToHop * toHop.getTrongLuong());
							}
						}
			/////////////
					}
				}
			}
		}
		
		System.out.println("Start");
		//Add nhung to hop bi tach, sau do phan bo may san xuat cho nhung to hop nay
		if (tohopList != null)
		{
			for (ToHop toHop : tohopList)
				addToHop(toHop);
//			if (tohopList.size() > 0)
//				phanBoMaySanXuat(maySanXuatList);
		}
		
		//Kiem tra xem con toHop nao chua duoc phan vao may san xuat
		boolean isExit = false;
		for (MaySanXuat maySanXuat : maySanXuatList)
		{
			//Lay kich ban co cung kho giay nguyen lieu voi may san xuat
			KichBan kichBan = this.toHopMay.get(maySanXuat.getKhoGiayNguyenLieu());
			if (kichBan != null)
			{
				Hashtable<Float, ToHop> toHopHash = kichBan.getToHopHash();
				Set<Float> toHopHashKeys = toHopHash.keySet();
				//Xet tung to hop co kho giay tieu chuan trung voi may san xuat
				for (float key : toHopHashKeys)
				{
					ToHop toHop = toHopHash.get(key);
					if (toHop.getMaySanXuatId().trim().length() == 0)
						isExit = true;
				}
			}
		}
//		
		//Neu co thi tang cong suat moi may len them gap doi roi phan bo may san xuat lai cho nhung to hop chua duoc phan bo
//		while (isExit == true)
		if (isExit == true)
		{
			System.out.println("To hop chua phan vao may san xuat: " + isExit + " trong luong hien tai: ");
			//Tang cong suat may
			for (MaySanXuat maySanXuat : maySanXuatList)
			{
//				maySanXuat.setCongSuatMay(maySanXuat.getCongSuatMay() * 2);
				maySanXuat.setTrongLuongHienTai(0);
			}
			//Phan bo lai
//			isExit = phanBoMaySanXuat(maySanXuatList);
			phanBoMaySanXuat(maySanXuatList);
		}
		return isExit;
	}

	private ToHop addDonHangToToHop(ToHop toHop, List<SanPham> sanPhamDonHangList)
	{
		ToHop toHopNew = new ToHop(toHop.getHaoHut(), toHop.getSoLuong(), toHop.getKhoGiayNguyenLieu(), toHop.getDinhLuong());
		Hashtable<Float, SanPham> sanPhamHash = toHop.getSanPhamHash();
		Set<Float> spToHopKeys = sanPhamHash.keySet();
		for (float khoGiay : spToHopKeys)
		{
			String idSanPham = sanPhamHash.get(khoGiay).getId();
			int tongSoLuong = sanPhamHash.get(khoGiay).getSoLuong() * toHop.getSoLuong();
			for (SanPham spDonHang : sanPhamDonHangList)
			{
				String idSpDonHang = spDonHang.getId();
				int soLuongSpDonHang = spDonHang.getSoLuong();
				if (tongSoLuong > 0 && idSpDonHang.trim().equals(idSanPham) == true && soLuongSpDonHang > 0)
				{
					SanPham spNew = new SanPham(sanPhamHash.get(khoGiay));
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
					toHopNew.getSanPhamHash().put(spNew.getKhoGiay() + Integer.parseInt(spDonHang.getIdDonHang()), spNew);
				}
			}
		}
		return toHopNew;
	}
	
	public void init(KichBan kichBan)
	{
		toHopMay = new Hashtable<Float, KichBan>();
		Set<Float> keys = kichBan.getToHopHash().keySet();
		for (float key : keys)
		{
			Set<Float> toHopMayKeys = toHopMay.keySet();
			ToHop toHop = kichBan.getToHopHash().get(key);
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
				KichBan kichBanTmp = new KichBan();
				kichBanTmp.addToHop(toHop);
				toHopMay.put(toHop.getKhoGiayNguyenLieu(), kichBanTmp);
			}
		}
	}


	
	public void xuatExcell(OutputStream outstream, String templateFileName)
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
//	    toHopMay = new Hashtable<Float, KichBan>();
	    Set<Float> toHopMayKeys = this.toHopMay.keySet();
	    
	    int sheetIndex = 1;
	    boolean isMerge = true;
	    for (float khoGiayNLMay : toHopMayKeys)
	    {
	    	int i = 1;
	    	int start = 9;
	    	Hashtable<Float, ToHop> toHopHash = toHopMay.get(khoGiayNLMay).getToHopHash();
	    	//Sheet 1
		    Worksheet worksheet1 = worksheets.getSheet("Sheet" + sheetIndex);
//	    	Worksheet worksheet1 = worksheets.getSheet("Sheet3");
		    Cells cells1 = worksheet1.getCells();
//		    cell = cells1.getCell("G1"); cell.setValue(pk_seq);
		    Set<Float> toHopHashKeys = toHopHash.keySet();
		    for (float key : toHopHashKeys)
		    {
		    	ToHop toHop = toHopHash.get(key);
		    	Hashtable<Float, SanPham> sanPhamHash = toHop.getSanPhamHash();
		    	Set<Float> sanPhamKeys = sanPhamHash.keySet();
		    	int rowsNumber = 0;
		    	boolean isFirst = true;
		    	for (float sanPhamKey : sanPhamKeys)
		    	{
		    		SanPham sanPham = sanPhamHash.get(sanPhamKey);
		    		int index = start + rowsNumber;
		    		cell = cells1.getCell("C" + index); 
		    		//////
		    		Style style = cell.getStyle();
					style.setColor(Color.WHITE);
					style.setBorderLine(BorderType.BOTTOM, 1);
					style.setBorderLine(BorderType.LEFT, 1);
					style.setBorderLine(BorderType.TOP, 1);
					style.setBorderLine(BorderType.RIGHT, 1);
					Font font = style.getFont();
					font.setColor(Color.BLACK);
					font.setBold(true);
					style.setFont(font);
		    		//////
		    		cell.setValue(sanPham.getTenNhanHang()); cell.setStyle(style);	
		    		cell = cells1.getCell("G" + index); cell.setValue(sanPham.getDinhLuong()); cell.setStyle(style);
//		    		cell = cells1.getCell("F" + index); cell.setValue(sanPham.getMa()); cell.setStyle(style);
		    		cell = cells1.getCell("F" + index); cell.setValue(sanPham.getId()); cell.setStyle(style);
		    		cell = cells1.getCell("J" + index); cell.setValue(sanPham.getKhoGiay()); cell.setStyle(style);
		    		System.out.println("Kho giay: " + sanPham.getKhoGiay()); cell.setStyle(style);
		    		cell = cells1.getCell("K" + index); cell.setValue(sanPham.getSoLuong()); cell.setStyle(style);
		    		cell = cells1.getCell("M" + index); cell.setValue(sanPham.getIdDonHang()); cell.setStyle(style);
		    		float trongLuong = (float) (sanPham.getSoLuong() * (sanPham.getKhoGiay() * 7.5 + sanPham.getDinhLuong() - 115));
		    		cell = cells1.getCell("L" + index); cell.setValue(trongLuong); cell.setStyle(style);
		    		//Ten khach hang
		    		cell = cells1.getCell("N" + index); cell.setValue(sanPham.getTenKhachHang()); cell.setStyle(style);
//		    		start++;
//		    		cell = cells1.getCell("B" + index);
//		    		style = cell.getStyle();
//					style.setColor(Color.WHITE);
////					style.setBorderLine(BorderType.BOTTOM, 0);
//					style.setBorderLine(BorderType.LEFT, 1);
//					if (isFirst == true)
//					{
//						style.setBorderLine(BorderType.TOP, 0);
//						isFirst = false;
//					}
//					style.setBorderLine(BorderType.RIGHT, 1);
//					font = style.getFont();
//					font.setColor(Color.BLACK);
//					font.setBold(true);
//					style.setFont(font);
//					cell.setStyle(style);	
		    		rowsNumber++;
		    	}
		    	
//		    	cell = cells1.getCell("B" + (start)); cell.setValue(i);
		    	System.out.println(start + "->" + (start + rowsNumber - 1));
		    	cells1.merge(start - 1, 1, (start + rowsNumber - 1 - 1), 1);
		    	cell = cells1.getCell("B" + (start)); //cell.setValue(i);
		    	worksheet1.getCell(start - 1, 1).setValue(i);  
		    	
//		    	cell = cells1.getCell("B" + (start + rowsNumber - 1)); cell.setValue(i);
		    	
		    	Style style = cell.getStyle();
				style.setColor(Color.WHITE);
				style.setBorderLine(BorderType.BOTTOM, 1);
				style.setBorderLine(BorderType.LEFT, 1);
				style.setBorderLine(BorderType.TOP, 1);
				style.setBorderLine(BorderType.RIGHT, 1);
				Font font = style.getFont();
				font.setColor(Color.BLACK);
				font.setBold(true);
				style.setFont(font);
//				cell.setStyle(style);	
//				Cells cellArry = cell.getCells();
				Cells cellArry = cell.getCells();
				StyleFlag flag = new StyleFlag();
				cells1.applyStyle(style, flag, start - 1, 1, (start + rowsNumber - 1 - 1), 1);
				cell = cells1.getCell("H" + (start + rowsNumber - 1)); cell.setValue(toHop.getKhoGiayNguyenLieu() - toHop.getHaoHut());
				cells1.merge(start - 1, 7, (start + rowsNumber - 1 - 1), 7);
				cell = cells1.getCell("I" + (start + rowsNumber - 1)); cell.setValue(toHop.getSoLuong());
				cells1.merge(start - 1, 8, (start + rowsNumber - 1 - 1), 8);
		    	//cells1.getCells(start, 1, start + rowsNumber - 1, 1);

//		    	System.out.println("B" + (start + rowsNumber - 1) + ": " + i);
				if (isMerge == true)
				{
					isMerge = false;
					cells1.merge(100, 1, 100 + rowsNumber, 1);
				}
		    	start += rowsNumber;
		    	i++;
		    }
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
	
	public void setToHopMay(Hashtable<Float, KichBan> toHopMay) {
		this.toHopMay = toHopMay;
	}

	public Hashtable<Float, KichBan> getToHopMay() {
		return toHopMay;
	}
}
