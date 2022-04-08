package geso.dms.distributor.beans.chuyenkhonew;

import geso.dms.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IChuyenKhoList extends  Serializable, IPhan_Trang
{
	
	public String getView() ;
	public void setView(String view) ;
	public String getUserId();
	public void setUserId(String userId);

	public ResultSet getNvRs();
	public void setNvRs(ResultSet nvRs);


	public String getNppId();
	public void setNppId(String nppId);
	public String getSochungtu();
	public void setSochungtu(String sochungtu) ;
	public String getNguoitao() ;
	public void setNguoitao(String nguoitao);
	public String getNppTen();
	public void setNppTen(String nppTen);

	public ResultSet getDhList();
	public void setDhList(ResultSet dhList);
	
	public String getTungay();
	public void setTungay(String tungay);
	
	public String getDenngay();
	public void setDenngay(String denngay);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public String getMsg();
	public void setMsg(String msg);

	public void createDdhlist(String query);
	public void DBclose();
	
	public int getCurrentPage();

	public void setCurrentPage(int current);
}



