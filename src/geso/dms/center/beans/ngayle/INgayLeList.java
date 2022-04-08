package geso.dms.center.beans.ngayle;

import java.io.Serializable;

import geso.dms.center.beans.nhacungcap.INhacungcap;

import java.util.List;
import java.sql.ResultSet;

public interface INgayLeList extends Serializable {
	public void init();	
	

	public String getNgaytao();
	public void setNgaytao(String ngaytao);

	public String getNgaysua() ;

	public void setNgaysua(String ngaysua);

	public String getNguoitao();

	public void setNguoitao(String nguoitao);

	public String getNguoisua() ;

	public void setNguoisua(String nguoisua) ;

	public String getNgayle();

	public void setNgayle(String ngayle);

	public String getDiengiai();

	public void setDiengiai(String diengiai);
	public String getQuery();
	
	public void setQuery(String query);
	
	public ResultSet getNccList();
	
	public void setNccList(ResultSet ncclist);
	
	public String getNhacungcap();
	
	public void setNhacungcap(String nhacungcap);
	
	public String getTenviettat();
	
	public void setTenviettat(String tenviettat);
	
	public String getTungay();
	
	public void setTungay(String tungay);

	public String getDenngay();
	
	public void setDenngay(String denngay);
	
	public String getTrangthai();
	
	public void setTrangthai(String trangthai);
	public void setMsg(String Msg);
	public String getMsg();
   
	public void DBClose();
	
	public List<Object> getDataSearch();
	public void setDataSearch(List<Object> dataSearch);
}