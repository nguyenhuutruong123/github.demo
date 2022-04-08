/*
 * khona 
 * date create 14-11-2011
 * Description :Save infomation about selling product to distributor 
 */
package geso.dms.center.beans.hoadon;
import geso.dms.center.beans.nhaphanphoi.INhaphanphoiList;

import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.List;

public interface IHoaDon {
	public String getId();
	public void setId(String id);	
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
	public double getChietkhau();
	public void setChietkhau(double chietkhau);
	public double getVAT();
	public void setVAT(double vat);	
	public String getMessage();
	public void setMessage(String msg);
	public String getIdDonDatHang();
	public void setIdDonHangDat(String iddonhangdat);
	public String getIdNhaCungCap();
	public void  setIdNhaCungCap(String idnhacc);
	public String getTenNhaCungCap();
	public void  setTenNhaCungCap(String tennhacc);
	public void setListSanPham(List<IHoaDon_SanPham> list);
	public String getIDKenhBanHang();
	public void setIDKenhBanHang(String kenhbanhangid);
	public List<IHoaDon_SanPham> getListSanPham();
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
	
	public Hashtable<String, Integer> getSpThieuList();
	public void setSpThieuList(Hashtable<String, Integer> spThieuList);
	public boolean SaveHoaDon();
	public boolean EditHoaDon();
	public boolean DeleteHoaDon();
	public void setHashtableNhaPhanPhoi();
	public Hashtable<String, INhaphanphoiList> getHashtableNhaPP();
	public void setInfoNhaPhoiPhoi(INhaphanphoiList npp);
	public INhaphanphoiList getInfoNhaPhoiPhoi();
	public void setIdDVKD(String dvkdid);
	public String getIdDVKD();
	public void setSoSO(String soso);
	public String getSoSO();
	/*
	 * Thiet lap de nghi dat hang 
	 * 
	 */
	public void setDeNghiDatHang(String denghidathang);
	public String getDeNghiDatHang();
	/**
	 * get list IHoaDon object
	 * @return
	 */
	public List<IHoaDon> getListHoaDon();
	public void setListHoaDon(String sql);
	/**
	 * tientrakm is money of central spending(chi tra) to distributor
	 * @param tientrakm
	 */
	public void setSoTienTraKM(double tientrakm);
	public void setSoTienTraTB(double tientratb);
	
	public double getSoTienTraKM();
	public double getSoTienTraTB();
	
	public double getTienNo();
	public double getGioiHanCongNo();
	
	public double getTienHuongKhuyenMai();
	public double getTienHuongTrungBay();
	
	public void setListCTKM(List<IHoaDon_CTKM> listctkm);
	//Lay danh sach cac chuong trinh khuyen mai,da duoc duyet va chua duoc tra
	public void setListCTKMInit();
	public List<IHoaDon_CTKM> getListCTKM();
	public boolean saveDonHangKhuyenMai();
	public boolean EditDonHangKhuyenMai();
	
	public void setLoaiDonHang(String loaidonhang);
	public String getLoaiDonHang();
	public ResultSet getReS_GSBH();
	public void setGiamSatBanHang(String gsbhid);
	public String getGiamSatBanHang();
}
