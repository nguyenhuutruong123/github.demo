package geso.dms.center.beans.bcchart;

import geso.dms.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.ArrayList;

public interface IBcchart extends IPhan_Trang, Serializable
{
	public int getIsCuoiThang();
	public void setIsCuoiThang(int isCuoiThang);
	public ArrayList<Long> getDataSanpham() ;
	public Long[][] getDataDoanhSoNgay() ;
	public String getSpId() ;
	public void setSpId(String spId) ;
	
	public ResultSet getSpRs();
	public String getUserId();
	public void setUserId(String userId);

	public String getSpVungId();
	public void setSpVungId(String spVungId);
	public String getSpKenhId() ;
	public void setSpKenhId(String spKenhId) ;
	public String getId();
	public void setId(String Id);
	public String getThang();
	public void setThang(String thang);
	public String getNam();
	public void setnam(String nam);
	public String getTheomuc();
	public void setTheomuc(String theomuc);
	public String getMsg();
	public void setMsg(String msg);
	
	public String[] getArrTenDiaBan();
	public String[] getArrIDDiaBan();
	public String[] getArrTenMien();
	public String[] getArrIDMien();
	public String[] getArrTenNSP();
	public String[] getArrIDNSP();
	public String[] getArrTenSanPhamMoi();
	public String[] getArrIDSanPhamMoi();
	public String[] getArrTenSanPhamChuLuc();
	public String[] getArrIDSanPhamChuLuc();
	
	public void initTonKho(String query);
	public void initDoanhSo(String query);
	public void initDoanhThu(String query);
	public void initSanPham(String query);
	public ResultSet getRsTonKhoDiaBan();
	public ResultSet getRsTonKhoMien();
	public ResultSet getRsDoanhSoDiaBan();
	public ResultSet getRsDoanhSoMien();
	public ResultSet getRsDoanhThuDiaBan();
	public ResultSet getRsDoanhThuMien();
	public ResultSet getRsDoanhSoNSP();
	public ResultSet getRsDoanhSoSPChuLuc();
	public ResultSet getRsDoanhSoSPMoi();
	
	public void initData(String chartType);
	
	public String getData_ChartDoanhthu();
	public void setData_ChartDoanhthu(String msg);

	public void DbClose();

	public String getView();
	public void setView(String view);
	public ResultSet getTieuChiList();
	public String getDonvitinh() ;
	public void setDonvitinh(String donvitinh) ;
	public ResultSet getRsVung();
	public void setRsVung(ResultSet rsVung);
	public ResultSet getRsKenh();
	public void setRsKenh(ResultSet rsKenh);
}
