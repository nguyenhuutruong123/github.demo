package geso.dms.center.beans.nhomctkhuyenmai;

import java.sql.ResultSet;

public interface INhomctkhuyenmai 
{
	public String getUserId();
	public void setUserId(String userid);
	
	public String getId();
	public void setId(String id);
	
	public String getTen();
	public void setTen(String ten);
	
	public String getDiengiai();
	public void setDiengiai(String diengiai);

	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public String getTungay();
	public void setTungay(String tungay);
	public String getDenngay();
	public void setDenngay(String denngay);
	
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
	
	public ResultSet getCtkmList();
	public void setCtkmList(ResultSet ctkmList);

	public String getCtkmIds();
	public void setCtkmIds(String ctkmIds);
		
	public boolean save();
	public boolean update();
	
	public void init();
	public void createRs();
}
