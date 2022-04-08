package geso.dms.center.beans.phieuxuatkhott;

import java.util.List;

public interface IPhieuXuatKhoTT {
	public String getID();
	public void setId(String id);
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	public String getNgaylap();
	public void setNgaylap(String ngaylap);
	
	public String getNgaytao();
	public void setNgaytao(String ngaytao);
	public String getNguoitao();
	public void setNguoitao(String nguoitao);
	public String getNgaysua();
	public void setNgaysua(String ngaysua);	
	public String getNguoisua();
	public void setNguoisua(String nguoisua);
	public void setGhiChu(String ghichu);
	public String getGhiChu();
	public void setLyDoXuat(String lydoxuat);
	public String getLyDoXuat();
	public String getMessage();
	public void setMessage(String msg);
	public void setDonDatHangId(String ddhid);
	public String getDonDatHangID();
	public void init(String sql);
	public List<IPhieuXuatKhoTT> getListPhieuXuatKho();
	public boolean SavePhieuXuatKho();
	public boolean EditPhieuXuatKho();
	public boolean ChotPhieuXuatKho();
	 
	public String getKhoTTId();
	public void setKhoTTId(String khottid);
	
	public void setSoId(String soid);
	public String getSoId();
	public List<IPhieuXuatKhoTT_SP> getListSanPham();
	public void setListSanPham();
	public void setHoTenNguoiNhan(String hotennguoinhan);
	public String getHoTenNguoiNhan();
	public void setDiaChiNguoinhan(String diachi);
	public String getDiaChiNguoiNhan();
	public String getTenKho();
	public void setTenKho(String tenkho);

}
