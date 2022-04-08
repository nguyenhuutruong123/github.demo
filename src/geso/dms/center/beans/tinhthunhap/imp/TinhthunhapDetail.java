package geso.dms.center.beans.tinhthunhap.imp;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import geso.dms.center.beans.tinhthunhap.IKhuvuc;
import geso.dms.center.beans.tinhthunhap.INhaPhanPhoi;
import geso.dms.center.beans.tinhthunhap.INhanvien;
import geso.dms.center.beans.tinhthunhap.IThuongvuotmuc;
import geso.dms.center.beans.tinhthunhap.ITinhthunhapDetail;
import geso.dms.center.db.sql.dbutils;

public class TinhthunhapDetail implements ITinhthunhapDetail 
{

	String id;
	String ma;
	String diengiai;
	String dvkdId;
	String kbhId;
	String kvId;
	String kvTenSelected;
	String NppId;
	String NppIdSelected;
	
	String luongCB;
	String ptluongtg;
	String ptluonghq;
	String bhTu;
	String bhDen;
	String chucvu;
	
	String[] maDetail;
	String[] noidung;
	String[] trongso;
	String[] mucbaohiem;
	String[] thuongSRvuotmuc;
	
	List<IKhuvuc> kvList;
	List<INhanvien> nvList;
	List<IThuongvuotmuc> tvmList;
	List<INhaPhanPhoi> nppList;
	
	String baohiem;
	String congdoan;
	String tdnc;
	
	public TinhthunhapDetail()
	{
		this.id = "";
		this.ma = "";
		this.diengiai = "";
		this.NppId="";
		this.NppIdSelected="";
		this.dvkdId = "";
		this.kbhId = "";
		this.kvId = "";
		this.kvTenSelected = "";
		
		this.luongCB = "";
		this.ptluongtg = "";
		this.ptluonghq = "";
		this.bhDen = "";
		this.bhTu = "";
		this.chucvu = "";
		this.baohiem = "";
		this.congdoan = "";
		this.tdnc = "";
		
		this.maDetail = new String[]{"CT01", "CT02", "CT03", "CT04"};
		this.noidung = new String[]{"", "", "Số đơn hàng", "Qui trình làm việc"};
		this.trongso = new String[]{"", "", "", ""};
		this.mucbaohiem = new String[]{"", "", "", ""};
		this.thuongSRvuotmuc = new String[]{"", "", "", ""};
		
		this.kvList = new ArrayList<IKhuvuc>();
		this.nvList = new ArrayList<INhanvien>();
		this.tvmList = new ArrayList<IThuongvuotmuc>();
		this.nppList=new ArrayList<INhaPhanPhoi>(); 
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

	
	public String getBaohiem() {
		
		return this.baohiem;
	}

	
	public void setBaohiem(String baohiem) {
		
		this.baohiem = baohiem;
	}

	
	public String getCongdoan() {
		
		return this.congdoan;
	}

	
	public void setCongdoan(String congdoan) {
		
		this.congdoan = congdoan;
	}

	public String getThucdatngaycong() 
	{
		return this.tdnc;
	}

	public void setThucdatngaycong(String tdnc)
	{
		this.tdnc = tdnc;
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
		if(this.kvId.trim().length() > 0)
		{
			dbutils db = new dbutils();
			String query = "";
			if(this.chucvu.equals("SS"))
			{
				query = "select '' as nppTen, PK_SEQ, TEN from GIAMSATBANHANG where TRANGTHAI = '1' and KHUVUC_FK in ( " + this.kvId + " )";
				
			}
			else if(this.NppId.trim().length()>0)
			{
				query = "select b.ten as nppTen, a.PK_SEQ, a.TEN from DAIDIENKINHDOANH a inner join NhaPhanPhoi b on a.npp_fk = b.pk_seq " +
						"where a.TRANGTHAI = '1' and  b.KHUVUC_FK in ( " + this.kvId + " ) and a.NPP_FK in ("+this.NppId+ "  ) ";
			}
			System.out.println("Khoi tao nhan vien: " + query);
			ResultSet rs=null;
			if(query.length()>0)
				rs = db.get(query);
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
						nv.setLuongCB(this.luongCB);
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

	public List<IThuongvuotmuc> getThuongvmList() 
	{
		return this.tvmList;
	}

	public void setThuongvuotmucList(List<IThuongvuotmuc> tvmList) 
	{
		this.tvmList = tvmList;
	}

	public String getKvTenSelected() 
	{
		return this.kvTenSelected;
	}

	public void setKvTenSelected(String kvTenSelected)
	{
		this.kvTenSelected = kvTenSelected;
	}

	
	public String GetNppId()
	{
		
		return this.NppId;
	}

	
	public String setNppId(String NppId)
	{
		
		return this.NppId=NppId;
	}


	public List<INhaPhanPhoi> getNhaPhanPhoiList()
	{
		
		return this.nppList;
	}


	public void setNhanPhanPhoiList(List<INhaPhanPhoi> nppList)
	{
		this.nppList=nppList;
	}

	
	public void InitNhaPhanPhoi()
	{
		if(this.kvId.trim().length()>0)
		{
			String query = "";
			dbutils db = new dbutils();
			if(this.NppIdSelected.trim().length()<=0)
			{
				query = "SELECT PK_SEQ ,TEN as nppTen FROM NHAPHANPHOI WHERE TRANGTHAI=1 AND " +
					"KHUVUC_FK IN("+this.kvId+")";
			}
			else 
			{
				query = "SELECT PK_SEQ ,TEN as nppTen FROM NHAPHANPHOI WHERE TRANGTHAI=1 AND " +
				"KHUVUC_FK IN("+this.kvId+") AND PK_SEQ NOT IN("+this.NppIdSelected+")";
			}
			System.out.println("___Khoi tao Npp"+query);
				ResultSet rs=db.get(query);
				if(rs != null)
				{
					try 
					{
						List<INhaPhanPhoi> nppList = new ArrayList<INhaPhanPhoi>();
						INhaPhanPhoi npp = null;
						
						while(rs.next())
						{
							npp = new NhaPhanPhoi();
							npp.setTen(rs.getString("nppTen"));
							npp.setId(rs.getString("PK_SEQ"));
							nppList.add(npp);
						}
						rs.close();
						this.nppList = nppList;
					} 
					catch (Exception e) {}
				}
				db.shutDown();
		}
		
	}

	
	public String getNppSelected()
	{
		
		return this.NppIdSelected;
	}

	
	public void setNppSelected(String NppId)
	{
		this.NppIdSelected=NppId;
		
	}
	
	
}
