package geso.dms.distributor.beans.catalog;

import java.io.Serializable;
import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;

public interface Icatalog extends Serializable {
	
	public String getId();

	public void setId(String id) ;

	public String getTen() ;

	public void setTen(String ten);

	public String getChungloai() ;

	public void setChungloai(String chungloai);

	public String getDuongdan() ;

	public void setDuongdan(String duongdan);

	public String getGhichu();

	public void setGhichu(String ghichu);

	public String getUserId() ;

	public void setUserId(String userId) ;

	public String getMsg();

	public void setMsg(String msg) ;

		
	public boolean saveNsp();
	
	public boolean updateNsp();
	
	public void init();
	public String getNhanhang() ;

	public void setNhanhang(String nhanhang) ;

	public String getMa() ;

	public void setMa(String ma);
}