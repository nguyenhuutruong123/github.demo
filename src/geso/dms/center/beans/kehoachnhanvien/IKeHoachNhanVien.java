package geso.dms.center.beans.kehoachnhanvien;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IKeHoachNhanVien extends Serializable 
{
	public String getUserId();
	public void setUserId(String userId);
	public String getId();
	public void setId(String id);
	public String getTenNhanVien();
	public void setTenNhanVien(String tennhanvien);
	public String getThang();
	public void setThang(String thang);
	public String getNam();
	public void setNam(String nam);
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
	public String getMessage();
	public void setMessage(String msg);
	
	public IKeHoachNhanVienNgay[] getNgayList();
	public void setNgayList(IKeHoachNhanVienNgay[] ngayList);
	
	public ResultSet getNppRs();
	public void setNppRs(ResultSet nppRs);
	public ResultSet getDDKDRs();
	public ResultSet getTBHRs();
	public ResultSet getTinhRs();
	public void setTinhRs(ResultSet tinhRs);
	public ResultSet getQuanRs();
	public void setQuanRs(ResultSet quanRs);

	public void init();
	public void init(boolean display);
	public void createRs();
	
	public boolean create();
	public boolean update();
	public void closeDB();
	
	
}
