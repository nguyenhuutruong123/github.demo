package geso.dms.center.beans.tinnhan;

import geso.dms.center.db.sql.dbutils;

public interface ITinNhan
{

	public String getSdt();
	public void setSdt(String sdt);

	public String getNgayGio();
	public void setNgayGio(String ngayGio);

	public String getNoiDung();
	public void setNoiDung(String noiDung);

	public String getStt();
	public void setStt(String stt);

	public String getCOM();
	public void setCOM(String cOM);
	
	public String getMsg();
	public void setMsg(String msg);
	
	public String Save(dbutils db);
	
	public String Convert(dbutils db);
	
	public String getUserId();
	public void setUserId(String userId);
	
	public String getId();
	public void setId(String id);
	
	public String getDienthoai();
	public void setDienthoai(String dienthoai);
	
	public String getNppId();
	public void setNppId(String nppId);
	
	public String getDdkdId();
	public void setDdkdId(String ddkdId);
	
	public String getGsbhId();
	public void setGsbhId(String gsbhId);
	
	public String getKhId();
	public void setKhId(String khId);
	
	public String getSmartId();
	public void setSmartId(String smartId);

	
	public void init();
	
}
