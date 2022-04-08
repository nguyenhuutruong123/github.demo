package geso.dms.distributor.beans.banggiasieuthi.imp;

import geso.dms.distributor.beans.banggiasieuthi.IKhachHang_Bgst;

public class KhachHang_Bgst implements IKhachHang_Bgst {

	String KhId;
	String TenKH;
	String Check;
	String Bgstid;
	@Override
	public void setIdKh(String khid) {
		// TODO Auto-generated method stub
		this.KhId=khid;
	}

	@Override
	public String getIdKh() {
		// TODO Auto-generated method stub
		return this.KhId;
	}

	@Override
	public void setTenKh(String tenkh) {
		// TODO Auto-generated method stub
		this.TenKH=tenkh;
	}

	@Override
	public String getTdKh() {
		// TODO Auto-generated method stub
		return this.TenKH;
	}

	@Override
	public void setBGST(String BGST) {
		// TODO Auto-generated method stub
		this.Bgstid=BGST;
	}

	@Override
	public String getBGST() {
		// TODO Auto-generated method stub
		return this.Bgstid;
	}

	@Override
	public void setCheck(String check) {
		// TODO Auto-generated method stub
		this.Check=check;
	}

	@Override
	public String getCheck() {
		// TODO Auto-generated method stub
		return this.Check;
	}

}
