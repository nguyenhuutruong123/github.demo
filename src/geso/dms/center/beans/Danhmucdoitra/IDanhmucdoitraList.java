package geso.dms.center.beans.Danhmucdoitra;

import geso.dms.center.beans.Danhmucdoitra.IDanhmucdoitra;
import geso.dms.center.beans.Danhmucdoitra.imp.Danhmucdoitra;
import geso.dms.center.db.sql.dbutils;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public interface IDanhmucdoitraList extends Serializable 
{

	
	public List<IDanhmucdoitra> getLchList() ;

	public void setLchList(List<IDanhmucdoitra> lchlist);
	
	public String getUserId();
	public void setUserId(String userId);
	
	public String getLoaicuahang() ;

	public void setLoaicuahang(String loaicuahang) ;

	public String getDiengiai();

	public void setDiengiai(String diengiai) ;

	public String getTrangthai();

	public void setTrangthai(String trangthai);
	
	public void createLchBeanList(String query);
	
	public void init(String search) ;
	
	public void closeDB();

	public void setMsg(String Msg);
	
	public String getMsg();
}