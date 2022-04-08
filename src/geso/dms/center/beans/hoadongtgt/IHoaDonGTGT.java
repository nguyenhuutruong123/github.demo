package geso.dms.center.beans.hoadongtgt;

import geso.dms.center.beans.hoadon.IHoaDon_SanPham;
import geso.dms.center.beans.nhaphanphoi.INhaphanphoi;
import geso.dms.center.beans.nhaphanphoi.INhaphanphoiList;

import java.util.List;

public interface IHoaDonGTGT   {
	public String getId();
	public void setId(String id);
	
	public String getSoHoaDon();
	public void setSoHoaDon(String sohoadon);
	
	public String getNgaygiaodich();
	public void setNgaygiaodich(String ngaygiaodich);
	
	public String getNppTen();
	public void setNppTen(String nppTen);		
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	public String getNgaytao();
	public void setNgaytao(String ngaytao);
	public String getNguoitao();
	public void setNguoitao(String nguoitao);
	public String getNgaysua();
	public void setNgaysua(String ngaysua);	
	public String getNguoisua();
	public void setNguoisua(String nguoisua);
	public String getIdDonDatHang();
	public void setIdDonHangDat(String iddonhangdat);
	public List<IHoaDonGTGT> getListHoaDonGTGT();
	public List<IHoaDonGTGT_SP> getListSanPham();
	public void setListSanPham(List<IHoaDonGTGT_SP> list);
	public void setListHoaDonGTGT(String sql);
	public boolean SaveHoaDonGTGT();
	public boolean EditHoaDonGTGT();
	public void setInfoNhaPhoiPhoi(INhaphanphoiList npp);
	public INhaphanphoiList getInfoNhaPhoiPhoi();
	public String getMessage();
	public void setMessage(String msg);
	public String getIdNhaCungCap();
	public void  setIdNhaCungCap(String idnhacc);
	public String getTenNhaCungCap();
	public void  setTenNhaCungCap(String tennhacc);

	public String getIDKenhBanHang();
	public void setIDKenhBanHang(String kenhbanhangid);

	/**
	 * ID of distributor selling products
	 * @return
	 */
	public String getNppId();
	public void setNppId(String nppId);
	/**
	 * khott is storage store product of supply,choose  storage to make hoadon
	 */
	public String getKhottId();
	public void setKhottId(String khottid);
	
	public String getKhottTen();
	public void setKhottTen(String KhottTen);
	
	public double getTongtienchuaCK();
	public void setTongtienchuaCK(double ttcck);
	public double getTienCK();
	public void setTienCK(double tienck);
	public double getTongtientruocVAT();
	public void setTongtientruocVAT(double tttvat);
	public double getTongtiensauVAT();
	public void setTongtiensauVAT(double ttsvat);
	public void setSoTienHuongKM(double sotienhuongkm);
	public double getSoTienHuongKM();
	public void setVat(String vat);
	public String getVat();
	public void setListSanPhamDDH(String ddhid);
	public boolean ChotHoaDonGTGT();
	public void setGhiChuHuongKM(String ghichuhuongkm);
	public String getGhiChuCHuongKm();
	public void setGhiChuHuongTB(String ghichuhuongtb);
	public String getGhiChuCHuongTB();
}
