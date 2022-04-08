package geso.dms.center.beans.tieuchithuong;

import geso.dms.center.db.sql.Idbutils;

import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.List;

public interface ITichLuyItem 
{
	public void setMuc(int muc) ;
	
	public String getDiengiai() ;
	public int getMuc();
	
	public int getLoaitra() ;
	public void setLoaitra(int loaitra) ;
	
	public Double getTumuc() ;
	public void setTumuc(Double tumuc) ;
	public String getTumucStr();
	public Double getDenmuc();
	public void setDenmuc(Double denmuc) ;
	public String getDenmucStr();
	public Double getTratichluy();
	public void setTratichluy(Double tratichluy) ;
	public String getTratichluyStr();
	public int getHinhthuc();
	public void setHinhthuc(int hinhthuc) ;
	public List<Object[]>  getSpList() ;
	public void setSpList(List<Object[]>  hashSp);
	public ResultSet getSanPhamRs();
	public void setSanPhamRs(Idbutils db);
}
