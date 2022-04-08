package geso.dms.center.beans.banggia;

import java.sql.ResultSet;
import java.util.List;

public interface IBangGia
{
	public String getUserId();
	public void setUserId(String userId);
	
	public String getId();
	public void setId(String Id);
	
	public String getTen();
	public void setTen(String ten);
	
	public String getDvkdId();
	public void setDvkdId(String dvkdId);
	public ResultSet getDvkdRs();
	public void setDvkdRs(ResultSet dvkdRs);
	
	public String getKbhId();
	public void setKbhId(String kbhId);
	public ResultSet getKbhRs();
	public void setKbhRs(ResultSet kbhRs);

	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public String getMsg();
	public void setMsg(String msg);
	
	public List<IBangGia_Sp> getSpList();
	public void setSpList(List<IBangGia_Sp> spList);
	
	
	public String getNppId();
	public void setNppId(String nppId);
	public ResultSet getNppRs();
	public void setNppRs(ResultSet NppRs);
	
	public boolean createBanggia();
	public boolean updateBanggia();
	
	public void init();
	public void createRs();
	
	public void DbClose();

	public String getTuNgay();
	public void setTuNgay(String tungay);
	
	
	public String getChietKhau();
	public void setChietKhau(String chietkhau);
	
	public String[] getNppIdCks();
	public void setNppIdCks(String[] ids);
	public String[] getNppChietKhaus();
	public void setNppChietKhaus(String[] cks);
	public String getLoai() ;

	public void setLoai(String loai);
	public boolean createBanggia_B2B();
	public boolean updateBanggia_B2B() ;
	
	public String getView();
	public void setView(String view) ;
	
}
