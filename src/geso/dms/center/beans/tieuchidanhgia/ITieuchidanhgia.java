package geso.dms.center.beans.tieuchidanhgia;

import java.sql.ResultSet;
import java.util.List;

public interface ITieuchidanhgia 
{
	public String getUserId();
	public void setUserId(String userId);
	public String getId();
	public void setId(String Id);
	
	public String getTungay();
	public void setTungay(String tungay);
	public String getDenngay();
	public void setDenngay(String denngay);
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public String getTenkhoa();
	public void setTenkhoa(String tenkhoa);
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	public String getMsg();
	public void setMsg(String msg);
	
	public String getThang();
	public void setThang(String thang);
	public String getNam();
	public void setNam(String nam);
	
	public ResultSet getGsbhRs();
	public void setGsbhRs(ResultSet gsbhRs);
	public String getGsbhIds();
	public void setGsbhIds(String gsbhIds);
	
	public boolean createKhl();
	public boolean updateKhl();
	
	public List<ITieuchiDetail> getTieuchiDetail();
	public void setTieuchiDetail(List<ITieuchiDetail> tcDetail);
	
	public ResultSet getVungRs();
	public void setVungRs(ResultSet vungRs);
	public String getVungId();
	public void setVungId(String vungId);
	
	public ResultSet getKhuvucRs();
	public void setKhuvucRs(ResultSet kvRs);
	public String getKvId();
	public void setKvId(String kvId);
	
	public void createRs();
	public void init();
	public void DbClose();
}
