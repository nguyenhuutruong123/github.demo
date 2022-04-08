package geso.dms.distributor.beans.phieuthutien;

import geso.dms.distributor.beans.phieuthutien.IPhieuthutien;
import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

public interface IPhieuthutienList extends Serializable
{
	public String getUserId();

	public void setUserId(String userId); 
	
	public String getNppId(); 

	public void setNppId(String nppId); 
	
	public String getNppTen(); 

	public void setNppTen(String nppTen);
	
	public String getSitecode();
	
	public void setSitecode(String sitecode);
	
	public String getNvgnId(); 
	
	public void setNvgnId(String nvgnId); 
	
	public ResultSet getNvgn(); 
	
	public void setNvgn(ResultSet nvgn); 

	public String getTungay(); 
	
	public void setTungay(String tungay);
	
	public String getDenngay(); 
	
	public void setDenngay(String denngay); 
	
	public String getSotien(); 
	
	public void setSotien(String sotien); 
	
	public ResultSet getPttList(); 
	
	public void setPttList(ResultSet pttlist); 
	
	public void init(String search);
	
	public void DBclose();
	public String getMsg();
	public void setMsg(String mgs);
	
}