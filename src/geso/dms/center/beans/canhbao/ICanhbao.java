package geso.dms.center.beans.canhbao;

public interface ICanhbao
{
	public String getUserId();
	public void setUserId(String userId);
	
	public String getCongtyId();
	public void setCongtyId(String congtyId);
	
	public String getId();
	public void setId(String Id);
	
	public String getMa();
	public void setMa(String ma);
	
	public String getDiengiai();
	public void setDiengiai(String diengiai);

	public String getDiachi();
	public void setDiachi(String diachi);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public String getMsg();
	public void setMsg(String msg);
	
	public boolean createCanhbao();
	public boolean updateCanhbao();
	
	public void init();
	
	public void DbClose();
	
}
