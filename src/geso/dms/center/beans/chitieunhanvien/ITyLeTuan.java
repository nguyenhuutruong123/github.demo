package geso.htp.center.beans.chitieunhanvien;

import geso.htp.center.beans.chitieunhanvien.imp.TyLeTuan;

import java.sql.ResultSet;
import java.util.List;

public interface ITyLeTuan {

	public void setID(String id);
	public String getID();
	
	public void setNguoiTao(String nguoitao);
	public String getNguoiTao();
	public void setNguoiSua(String nguoisua);
	public String getNguoiSua();
	public void setThang(int thang);
	public int getThang();
	public void setNam(int nam);
	public int getNam();
	public void setMessage(String strmessage);
	public String getMessage();


	public void setNgayTao(String ngaytao);
	public String getNgayTao();

	public void setNgaySua(String nguoisua);
	public String getNgaySua();

	public String getUserId();
	public void setUserId(String userId);
	
	public String getNppId();
	public void setNppId(String nppId);

	public String getNppTen();
	public void setNppTen(String nppTen);
	
	public String getTructhuocId();
	public void setTructhuocId(String tructhuocId);
	
	public void setDienGiai(String diengiai);
	public String getDienGiai();


	public boolean  Create();

	public boolean Update();

	public boolean Delete();

	public List<TyLeTuan> getChiTieu();
	public void setListChiTieu(String sql);


	public void setTrangThai(String trangthai);
	public String getTrangThai();


	public boolean Chot();
	public void closeDB();

	public boolean UnChot();
	
	public String getLoai();
	public void setLoai(String loai) ;
	
	public List<ITieuchi> getListTieuChi() ;
	public void setListTieuChi(List<ITieuchi> listTieuchi);

	public void initBCNhanVien();
	
	public String getIsDisplay();
	public void setIsDisplay(String isDisplay) ;
	public ResultSet getChitieuRs();
}
