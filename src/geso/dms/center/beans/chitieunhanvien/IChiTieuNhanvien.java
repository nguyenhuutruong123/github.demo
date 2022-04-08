package geso.dms.center.beans.chitieunhanvien;

import geso.dms.center.beans.chitieunhanvien.imp.ChiTieuNhanvien;

import java.sql.ResultSet;
import java.util.List;

public interface IChiTieuNhanvien {

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

	public void setVung(String Vung);
	public String getVung();

	
	public void setKhuVuc(String KhuVuc);
	public String getKhuVuc();

	public void setNgayTao(String ngaytao);
	public String getNgayTao();

	public void setNgaySua(String nguoisua);
	public String getNgaySua();

	public String getUserId();
	public void setUserId(String userId);
	
	
	public void setDienGiai(String diengiai);
	public String getDienGiai();
	
	public ResultSet getVungRS();
	public ResultSet getKhuVucRS();


	public boolean  CreateChiTieuLuongThuong(String values,String valuesNhom) ;

	public boolean UpdateChiTieuLuongThuong(String values,String valuesNhom);

	public boolean DeleteChiTieuLuongThuong();

	public List<ChiTieuNhanvien> getChiTieu();
	public void setListChiTieu(String sql);


	public void setTrangThai(String trangthai);
	public String getTrangThai();


	public boolean ChotChiTieuLuongThuong();
	public void closeDB();

	public boolean UnChotChiTieuLuongThuong();
	
	public String getLoai();
	public void setLoai(String loai) ;
	
	public List<ICTNhanvien> getListCTNhanVien() ;
	public void setListCTNhanVien(List<ICTNhanvien> listCTNhanVien);

	public void initBCNhanVien();
	
	public String getIsDisplay();
	public void setIsDisplay(String isDisplay) ;
	public ResultSet getChitieuRs();
	
	public String getLoaiUpload() ;
	public void setLoaiUpload(String loaiUpload) ;
	
	public List<Object> getDataSearch();
	public void setDataSearch(List<Object> dataSearch);
}
