package geso.dms.center.beans.chitieuttchovung.imp;

import geso.dms.center.beans.chitieuttchovung.IChiTieuTTKhuVuc;

public class ChiTieuTTKhuVuc implements IChiTieuTTKhuVuc {

	String Id;
	String KhuVucId;
	String TenKhuVucID;
	double ChiTieu;
	double TrungBinhThang;
	String SoDonHang="";
	String SoSKU="";
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


	@Override
	public void setChiTieu(double chitieu) {
		// TODO Auto-generated method stub
		this.ChiTieu=chitieu;
	}

	@Override
	public double getChiTieu() {
		// TODO Auto-generated method stub
		return this.ChiTieu;
	}

	@Override
	public void setKhuVucId(String khuvucid) {
		// TODO Auto-generated method stub
		this.KhuVucId=khuvucid;
	}

	@Override
	public String getKhuVucId() {
		// TODO Auto-generated method stub
		return this.KhuVucId;
	}

	@Override
	public void setTenKhuVucId(String tenkhuvucid) {
		// TODO Auto-generated method stub
		this.TenKhuVucID=tenkhuvucid;
	}

	@Override
	public String getTenKhuVucId() {
		// TODO Auto-generated method stub
		return this.TenKhuVucID;
	}

	@Override
	public void setSoDonHang(String sodonhang) {
		// TODO Auto-generated method stub
		this.SoDonHang=sodonhang;
	}

	@Override
	public String getSoDonHang() {
		// TODO Auto-generated method stub
		return this.SoDonHang;
	}

	@Override
	public void setSoSKU(String soSku) {
		// TODO Auto-generated method stub
		this.SoSKU=soSku;
	}

	@Override
	public String getSoSKU() {
		// TODO Auto-generated method stub
		return this.SoSKU;
	}

	@Override
	public void setTrungBinhThang(double trungbinhthang) {
		// TODO Auto-generated method stub
		this.TrungBinhThang=trungbinhthang;
	}

	@Override
	public double getTrungBinhThang() {
		// TODO Auto-generated method stub
		return this.TrungBinhThang;
	}

}
