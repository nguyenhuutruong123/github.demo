package geso.dms.center.beans.chitieunhaphanphoi;

import geso.dms.center.beans.chitieunhaphanphoi.imp.CTNhaphanphoi;

import java.sql.ResultSet;
import java.util.List;

public interface ICTNhaphanphoi {

	public String getId();
	public void setId(String id) ;
	public void setTen(String ten) ;
	public String getTen();
	
	public String getNppId() ;
	public void setNppId(String manv);
	
	//this.DoanhSoBanRa = Double.parseDouble(param[3]);
	public double getDoanhSoBanRa();
	//this.SoLuongBanRa = Double.parseDouble(param[4]);
	public double getSoLuongBanRa();
	//this.DoanhSoMuaVao = Double.parseDouble(param[5]);
	public double getDoanhSoMuaVao();
	//this.SoLuongMuaVao = Double.parseDouble(param[6]);
	public double getSoLuongMuaVao();
	//this.TonKhoNgay = Double.parseDouble(param[7]);
	public double getTonKhoNgay() ;
	//this.SoDonHang = Double.parseDouble(param[8]);
	public double getSoDonHang() ;
	//this.TyLeGiaoHang = Double.parseDouble(param[9]);
	public double getTyLeGiaoHang();
	public double getThoiGianGiaoHang() ;
	
	public List<ICTNhaphanphoi_NSP> getCtNspList() ;
	public void setCtNspList(List<ICTNhaphanphoi_NSP> ctNspList);

}
