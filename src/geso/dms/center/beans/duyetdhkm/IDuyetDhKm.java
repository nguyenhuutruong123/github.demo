package geso.dms.center.beans.duyetdhkm;

import geso.dms.center.beans.donmuahang.ISanPhamTraKM;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

public interface IDuyetDhKm extends Serializable 
{
	public String getUserId();
	public void setUserId(String userId);
	
	public String getId();
	public void setId(String id);
	
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	
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
	
	public String getNam();
	public void setNam(String nam);	
 
	public String getThang();
	public void setThang(String thang);	
	
	public ResultSet getRsCTKM();
	public void setRsCTKM(ResultSet rs);

	public ResultSet getlistsp();
	public void setlistsp(ResultSet rs);
	
	public void setCTKMChon(String str);
	public String getCTKMChon();
	
	public void closeDB();
	public void init();
	public boolean createDhKm();
	public void init_view();
	
	public String getSelected();
	public void setSelected(String spId);
	
	public String getTable();
	public void setTable(String table);
	
}
