package geso.dms.center.beans.hoadon.imp;

import geso.dms.center.beans.hoadon.IHoaDon_CTKM;

public class HoaDon_CTKM implements IHoaDon_CTKM{

	String CTKMID;
	String TenChuongTRinh;
	String DienGiai;
	double TongTien;
	String NppID;
	String Id;
	@Override
	public void setCTKM(String ctkm) {
		// TODO Auto-generated method stub
		this.CTKMID=ctkm;
		
	}
	

	@Override
	public String getNhaPP() {
		// TODO Auto-generated method stub
		return this.getNhaPP();
	}

	@Override
	public void setTenChuongTrinh(String tenchuongtrinh) {
		// TODO Auto-generated method stub
		this.TenChuongTRinh=tenchuongtrinh;
	}

	@Override
	public String getTenChuongTrinh() {
		// TODO Auto-generated method stub
		return this.TenChuongTRinh;
	}

	@Override
	public void setDienGiai(String diengiai) {
		// TODO Auto-generated method stub
		this.DienGiai=diengiai;
	}

	@Override
	public String getDienGiai() {
		// TODO Auto-generated method stub
		return this.DienGiai;
	}

	@Override
	public void setDaSuDung(double dasudung) {
		// TODO Auto-generated method stub
		this.TongTien=dasudung;
	}

	@Override
	public double getDaSuDung() {
		// TODO Auto-generated method stub
		return this.TongTien;
	}


	@Override
	public String getCTKM() {
		// TODO Auto-generated method stub
		return this.CTKMID;
	}


	@Override
	public void setNhaPP(String Npp) {
		// TODO Auto-generated method stub
		this.NppID=Npp;
	}


	@Override
	public void setID(String id) {
		// TODO Auto-generated method stub
		this.Id=id;
	}


	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return this.Id;
	}
	

}
