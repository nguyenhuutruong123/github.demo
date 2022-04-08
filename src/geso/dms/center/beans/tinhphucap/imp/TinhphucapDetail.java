package geso.dms.center.beans.tinhphucap.imp;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import geso.dms.center.beans.tinhphucap.ITinhphucapDetail;
import geso.dms.center.beans.tinhthunhap.IKhuvuc;
import geso.dms.center.beans.tinhthunhap.INhanvien;
import geso.dms.center.beans.tinhthunhap.imp.Nhanvien;
import geso.dms.center.db.sql.dbutils;

public class TinhphucapDetail implements ITinhphucapDetail 
{
	String id;
	String ma;
	String diengiai;
	String dvkdId;
	String kbhId;
	String kvId;
	
	String luongCB;
	String ptluongtg;
	String ptluonghq;
	String bhTu;
	String bhDen;
	String chucvu;
	
	List<IKhuvuc> kvList;
	List<INhanvien> nvList;
	
	String[] maDetail;
	String[] noidung;
	String[] trongso;
	String[] mucbaohiem;
	String[] thuongSRvuotmuc;
	String[] tinhtheongaycong;
	
	String gsbhSelected;
	String ddkdSelected;
	String nvIds;
	
	String stt;
	
	public TinhphucapDetail()
	{
		this.id = "";
		this.ma = "";
		this.diengiai = "";
		
		this.dvkdId = "";
		this.kbhId = "";
		this.kvId = "";
		
		this.luongCB = "";
		this.ptluongtg = "";
		this.ptluonghq = "";
		this.bhDen = "";
		this.bhTu = "";
		this.chucvu = "";
		
		this.maDetail = new String[]{"TC01", "TC02", "TC03", "TC04", "TC05", "TC06", "TC07", "TC08", "TC09", "TC10"};
		this.noidung = new String[]{"", "", "", "", "", "", "", "", "", ""};
		this.trongso = new String[]{"", "", "", "", "", "", "", "", "", ""};
		this.mucbaohiem = new String[]{"", "", "", "", "", "", "", "", "", ""};
		this.thuongSRvuotmuc = new String[]{"", "", "", "", "", "", "", "", "", ""};
		this.tinhtheongaycong = new String[]{"0", "0", "0", "0", "0", "0", "0", "0", "0", "0"};
		
		this.gsbhSelected = "";
		this.ddkdSelected = "";
		this.nvIds = "";
		this.stt = "";
		
		this.kvList = new ArrayList<IKhuvuc>();
		this.nvList = new ArrayList<INhanvien>();
		
	}

	public String getMa() 
	{
		return this.ma;
	}

	public void setMa(String ma) 
	{
		this.ma = ma;
	}

	public String getDiengiai() 
	{
		return this.diengiai;
	}

	public void setDiengiai(String diengiai) 
	{
		this.diengiai = diengiai;
	}

	public String getId() 
	{
		return this.id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	
	public String getDvkdId() {
		
		return this.dvkdId;
	}

	
	public void setDvkdId(String dvkdId) {
		
		this.dvkdId = dvkdId;
	}

	
	public String getKbhId() {
		
		return this.kbhId;
	}

	
	public void setKbhId(String kbhId) {
		
		this.kbhId = kbhId;
	}

	
	public String getKvId() {
		
		return this.kvId;
	}

	
	public void setKvId(String vungId) {
		
		this.kvId = vungId;
	}

	
	public String getLuongCB() {
		
		return this.luongCB;
	}

	
	public void setLuongCB(String luongCB) {
		
		this.luongCB = luongCB;
	}

	
	public String getPhantramluongTG() {
		
		return this.ptluongtg;
	}

	
	public void setPhantramluongTG(String ptltg) {
		
		this.ptluongtg = ptltg;
	}

	
	public String getPhantramluongHQ() {
		
		return this.ptluonghq;
	}

	
	public void setPhantramluongHQ(String ptlhq) {
		
		this.ptluonghq = ptlhq;
	}

	
	public String getBaohiemtu() {
		
		return this.bhTu;
	}

	
	public void setBaohiemtu(String baohiemTu) {
		
		this.bhTu = baohiemTu;
	}

	
	public String getBaohiemDen() {
		
		return this.bhDen;
	}

	
	public void setBaohiemDen(String baohiemDen) {
		
		this.bhDen = baohiemDen;
	}

	
	public String getChucvu() {
		
		return this.chucvu;
	}

	
	public void setChucvu(String chucvu) {
		
		this.chucvu = chucvu;
	}

	
	public String[] getMaDetail() {
		
		return this.maDetail;
	}

	
	public void setMaDetail(String[] maDetail) {
		
		this.maDetail = maDetail;
	}

	
	public String[] getNoidung() {
		
		return this.noidung;
	}

	
	public void setNoidung(String[] noidung) {
		
		this.noidung = noidung;
	}

	
	public String[] getTrongso() {
		
		return this.trongso;
	}

	
	public void setTrongso(String[] trongso) {
		
		this.trongso = trongso;
	}

	public String[] getThuongSRvuotmuc() 
	{
		return this.thuongSRvuotmuc;
	}

	public void setThuongSRvuotmuc(String[] thuongSRvm)
	{
		this.thuongSRvuotmuc = thuongSRvm;
	}

	public String[] getMucbaohiem() 
	{
		return this.mucbaohiem;
	}

	public void setMucbaohiem(String[] mucbaohiem) 
	{
		this.mucbaohiem = mucbaohiem;
	}
	
	public List<IKhuvuc> getKhuvucList() 
	{
		return this.kvList;
	}

	public void setKhuvucList(List<IKhuvuc> kvList) 
	{
		this.kvList = kvList;
	}

	public List<INhanvien> getNhanvienList() 
	{
		return this.nvList;
	}

	public void setNhanvienList(List<INhanvien> nvList)
	{
		this.nvList = nvList;
	}

	public void InitNhanVien() 
	{
		if(this.chucvu.trim().length() > 0 && this.kvId.trim().length() > 0)
		{
			dbutils db = new dbutils();
			
			String query = "";
			if(this.chucvu.equals("SS"))
			{
				query = "select PK_SEQ, TEN from GIAMSATBANHANG where TRANGTHAI = '1' and KHUVUC_FK in ( " + this.kvId + " )";
				if(this.gsbhSelected.trim().length() > 0)
				{
					query += " and pk_seq not in (" + this.gsbhSelected + ") ";
				}
			}
			else
			{
				query = "select PK_SEQ, TEN from DAIDIENKINHDOANH where TRANGTHAI = '1' and NPP_FK in (select PK_SEQ from NHAPHANPHOI where KHUVUC_FK in ( " + this.kvId + " ) )";
				if(this.ddkdSelected.trim().length() > 0)
				{
					query += " and pk_seq not in (" + this.ddkdSelected + ") ";
				}
				
			}
				
			System.out.println("1.Khoi tao nhan vien: " + query);
			
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				try 
				{
					List<INhanvien> nvList = new ArrayList<INhanvien>();
					INhanvien nv = null;
					
					while(rs.next())
					{
						nv = new Nhanvien();
						
						nv.setId(rs.getString("PK_SEQ"));
						nv.setTen(rs.getString("TEN"));
						nv.setLuongCB("");
						
						nvList.add(nv);
					}
					rs.close();
					
					this.nvList = nvList;
				} 
				catch (Exception e) {}
			}
			
			db.shutDown();
			
				
		}
	}

	public String getNhanvienIds()
	{
		return this.nvIds;
	}

	public void setNhanvienIds(String nvIds) 
	{
		this.nvIds = nvIds;
	}

	public String getGsbhSelected() 
	{
		return this.gsbhSelected;
	}

	public void setGsbhSelected(String gsbhIds)
	{
		this.gsbhSelected = gsbhIds;
	}

	public String getDdkdSelected() 
	{
		return this.ddkdSelected;
	}

	public void setDdkdSelected(String ddkdIds) 
	{
		this.ddkdSelected = ddkdIds;
	}

	public String getSTT() 
	{
		return this.stt;
	}

	public void setSTT(String stt) 
	{
		this.stt = stt;
	}

	public void InitNhanVienSelected() 
	{
		if(this.chucvu.trim().length() > 0 && this.kvId.trim().length() > 0)
		{
			dbutils db = new dbutils();
			
			String query = "";
			if(this.chucvu.equals("SS"))
			{
				query = "select PK_SEQ, TEN from GIAMSATBANHANG where TRANGTHAI = '1' and KHUVUC_FK in ( " + this.kvId + " )";
				/*if(this.gsbhSelected.trim().length() > 0)
				{
					query += " and pk_seq in (" + this.gsbhSelected + ") ";
				}*/
			}
			else
			{
				query = "select PK_SEQ, TEN from DAIDIENKINHDOANH where TRANGTHAI = '1' and NPP_FK in (select PK_SEQ from NHAPHANPHOI where KHUVUC_FK in ( " + this.kvId + " ) )";
				/*if(this.ddkdSelected.trim().length() > 0)
				{
					query += " and pk_seq in (" + this.ddkdSelected + ") ";
				}*/
				
			}
				
			System.out.println("111.Khoi tao nhan vien: " + query);
			
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				try 
				{
					List<INhanvien> nvList = new ArrayList<INhanvien>();
					INhanvien nv = null;
					
					while(rs.next())
					{
						nv = new Nhanvien();
						
						nv.setId(rs.getString("PK_SEQ"));
						nv.setTen(rs.getString("TEN"));
						nv.setLuongCB("");
						
						nvList.add(nv);
					}
					rs.close();
					
					this.nvList = nvList;
				} 
				catch (Exception e) {}
			}
			
			db.shutDown();
			
				
		}
	}

	public String[] getTinhtheongaycong() 
	{
		return this.tinhtheongaycong;
	}

	public void setTinhtheongaycong(String[] tinhtheonc) 
	{
		this.tinhtheongaycong = tinhtheonc;
	}
	
	
	
}
