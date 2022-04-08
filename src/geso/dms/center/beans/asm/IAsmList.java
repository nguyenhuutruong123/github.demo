package geso.dms.center.beans.asm;


import geso.dms.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

public interface IAsmList extends Serializable, IPhan_Trang{
	
	public String getUserId();
	public void setUserId(String userId);
	
	public String getMa();
	
	public void setMa(String asmMa);
	
	public String getTen();
	
	public void setTen(String asmTen);
	
	public String getDienthoai();
	
	public void setDienthoai(String dienthoai);
	
	public String getEmail();
	
	public void setEmail(String email);

	public String getDiachi();
	
	public void setDiachi(String diachi);

	public String getKbhId();
	
	public void setKbhId(String kbhId);

	public String getDvkdId();
	
	public void setDvkdId(String dvkdId);

	public String getKvId();
	
	public void setKvId(String kvId);

	public String getTrangthai();
	
	public void setTrangthai(String trangthai);

	public String getMsg();
	
	public String getThuviec();
	public void setThuviec(String thiec);
	
	public void setMsg(String msg);

	public ResultSet getKbh();
	
	public void setKbh(ResultSet kbh);

	public ResultSet getDvkd();
	
	public void setDvkd(ResultSet dvkd);

	public ResultSet getKv();
	
	public void setKv(ResultSet kv);

	public ResultSet getAsmlist();
	
	public void setAsmlist(ResultSet asmlist);
	
	public int getCurrentPage();
	public void setCurrentPage(int current);
	
	public int[] getListPages();
	public void setListPages(int[] listPages);
	
	public void init();
	
	public void Delete(String asmid, String kvId);
	
	public void DBClose();
	
	public List<Object> getDataSearch();
	public void setDataSearch(List<Object> dataSearch);
}
