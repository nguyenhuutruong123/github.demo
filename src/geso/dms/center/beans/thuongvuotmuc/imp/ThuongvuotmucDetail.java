package geso.dms.center.beans.thuongvuotmuc.imp;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import geso.dms.center.beans.thuongvuotmuc.IThuongvuotmucDetail;
import geso.dms.center.beans.tinhthunhap.IKhuvuc;
import geso.dms.center.beans.tinhthunhap.INhanvien;
import geso.dms.center.beans.tinhthunhap.imp.Nhanvien;
import geso.dms.center.db.sql.dbutils;

public class ThuongvuotmucDetail implements IThuongvuotmucDetail 
{
	String id;
	String ma;
	String diengiai;
	String dvkdId;
	String kbhId;
	String kvId;
	
	String chucvu;
	
	List<IKhuvuc> kvList;
	List<INhanvien> nvList;
	
	String[] nhomthuong;
	String[] tumuc;
	String[] denmuc;
	String[] thuong;
	
	String gsbhSelected;
	String ddkdSelected;
	String nvIds;
	
	String stt;
	
	public ThuongvuotmucDetail()
	{
		this.id = "";
		this.ma = "";
		this.diengiai = "";
		this.dvkdId = "";
		this.kbhId = "";
		this.kvId = "";
		this.chucvu = "";
		
		this.nhomthuong = new String[]{"", "", "", "", "", "", "", "", "", ""};
		this.tumuc = new String[]{"", "", "", "", "", "", "", "", "", ""};
		this.denmuc = new String[]{"", "", "", "", "", "", "", "", "", ""};
		this.thuong = new String[]{"", "", "", "", "", "", "", "", "", ""};
		
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

	
	public String getChucvu() {
		
		return this.chucvu;
	}

	
	public void setChucvu(String chucvu) {
		
		this.chucvu = chucvu;
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
				query = "select '' as nppTen, PK_SEQ, TEN from GIAMSATBANHANG where TRANGTHAI = '1' and KHUVUC_FK in ( " + this.kvId + " )";
				if(this.gsbhSelected.trim().length() > 0&&this.gsbhSelected.length()!=1)
				{
					query += " and pk_seq not in (" + this.gsbhSelected + ") ";
				}
			}
			else
			{
				query = "select b.ten as nppTen, a.PK_SEQ, a.TEN from DAIDIENKINHDOANH a inner join NhaPhanPhoi b on a.npp_fk = b.pk_seq " +
						"where a.TRANGTHAI = '1' and  b.KHUVUC_FK in ( " + this.kvId + " ) ";
				if(this.ddkdSelected.trim().length() > 0)
				{
					query += " and a.pk_seq not in (" + this.ddkdSelected + ") ";
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
						
						nv.setNppTen(rs.getString("nppTen"));
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
				query = "select '' as nppTen, PK_SEQ, TEN from GIAMSATBANHANG where TRANGTHAI = '1' and KHUVUC_FK in ( " + this.kvId + " )";
				/*if(this.gsbhSelected.trim().length() > 0)
				{
					query += " and pk_seq not in (" + this.gsbhSelected + ") ";
				}*/
			}
			else
			{
				query = "select b.ten as nppTen, a.PK_SEQ, a.TEN from DAIDIENKINHDOANH a inner join NhaPhanPhoi b on a.npp_fk = b.pk_seq " +
						"where a.TRANGTHAI = '1' and  b.KHUVUC_FK in ( " + this.kvId + " ) ";
				/*if(this.ddkdSelected.trim().length() > 0)
				{
					query += " and a.pk_seq not in (" + this.ddkdSelected + ") ";
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
						
						nv.setNppTen(rs.getString("nppTen"));
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

	
	public String[] getNhomthuong()
	{
		return this.nhomthuong;
	}

	public void setNhomthuong(String[] nhomthuong) 
	{
		this.nhomthuong = nhomthuong;
	}

	public String[] getTumuc() 
	{
		return this.tumuc;
	}

	public void setTumuc(String[] tumuc) 
	{
		this.tumuc = tumuc;
	}

	public String[] getDenmuc() 
	{
		return this.denmuc;
	}

	public void setDenmuc(String[] denmuc) 
	{
		this.denmuc = denmuc;
	}

	public String[] getThuong() 
	{
		return this.thuong;
	}

	public void setThuong(String[] thuong) 
	{
		this.thuong = thuong;
	}

	
	
	
}
