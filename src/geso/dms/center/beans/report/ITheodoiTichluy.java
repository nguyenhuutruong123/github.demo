package geso.dms.center.beans.report;

import java.sql.ResultSet;

public interface ITheodoiTichluy 
{
	
	public void setuserId(String userId);
	public String getuserId();

	public void setuserTen(String userTen);
	public String getuserTen();

	public void setnppId(String nppId);
	public String getnppId();

	public void setkenhId(String kenhId);
	public String getkenhId();

	public void settungay(String tungay);
	public String gettungay();

	public void setdenngay(String denngay);
	public String getdenngay();

	public void setMsg(String msg);
	public String getMsg();

	public ResultSet getkenhRs();

	public void setpromotion(String promotion);
	public String getpromotion();
	
	public ResultSet getPromotionRs();

	public void setvungId(String vungId);
	public String getvungId();

	public ResultSet getvungRs();

	public void setkhuvucId(String khuvucId);
	public String getkhuvucId();

	public ResultSet getkhuvucRs();	

	public ResultSet getNppRs();

	public String getDateTime();

	public void init();

	public void DBclose();

	// Danh muc dai dien kinh doanh
	public void setDdkdId(String ddkd);
	public String getDdkdId();

	public ResultSet getDdkdRs();	
	
}

