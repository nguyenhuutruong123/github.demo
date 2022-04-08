package geso.dms.center.beans.theodoithieuhang;

import java.sql.ResultSet;

public interface ITheodoithieuhang {
	String getSKU();

	void setSKU(String sku);

	String getTungay();

	void setTungay(String tungay);
	
	String getDenngay();
	
	void setDenngay(String denngay);

	void setThlist(ResultSet thlist);

	ResultSet getThlist();
		
	void DBClose();
	
	void setMsg(String Msg);
	
	String getMsg();
    void setuserId(String userId);
   String getuserId();
	void InitEdit(String masp);
	void InitSearch();
}
