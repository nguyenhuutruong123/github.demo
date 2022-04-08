package geso.dms.center.beans.nhomsanpham;

import java.io.Serializable;
import java.sql.ResultSet;

public interface INhomsanpham extends Serializable {
	
	public String getView() ;
	public void setView(String view) ;
	public String getId();

	public void setId(String id);
	
	public String getMa();

	public void setMa(String Ma);
	
	public String getParent();

	public void setParent(String parent);
	
	public String getTen();
	
	public void setTen(String ten);
	
	public String getDiengiai();
	
	public void setDiengiai(String diengiai);

	public String getThanhvien();
	
	public void setThanhvien(String thanhvien);
	
	public String getLoainhom();
	
	public void setLoainhom(String lnhom);

	public String getTrangthai();
	
	public void setTrangthai(String trangthai);
	
	public String getNgaytao();
	
	public void setNgaytao(String ngaytao);
	
	public String getNgaysua();
	
	public void setNgaysua(String ngaysua);
	
	public String getNguoitao();
	
	public void setNguoitao(String nguoitao);
	
	public String getNguoisua();
	
	public void setNguoisua(String nguoisua);

	public String getMessage();
	
	public void setMessage(String msg);
	
	public ResultSet getNspList();

	public void setNspList(ResultSet nspList);
	
	public ResultSet getSpList();

	public void setSpList(ResultSet spList);

	public ResultSet getSpSelected();

	public void setSpSelected(ResultSet spSelected);

	public ResultSet getDvkdList();

	public void setDvkdList(ResultSet dvkdList);
	
	public ResultSet getNhList();

	public void setNhList(ResultSet nhList);
	
	public ResultSet getCLList();

	public void setClList(ResultSet clList);
	
	public String getDvkdId();

	public void setDvkdId(String dvkdId);

	public String getNhId();

	public void setNhId(String nhId);

	public String getClId();

	public void setClId(String clId);
	
	public void UpdateRS();

	public String[] getNhomsp();

	public void setNhomsp(String[] nhom);

	public String[] getSanpham();

	public void setSanpham(String[] sanpham);
		
	public boolean saveNewNsp();
	
	public boolean updateNsp();
   
}