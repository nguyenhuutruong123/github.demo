package geso.trafaco.center.beans.themsanphambanggia;

import java.io.Serializable;
import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;

public interface IThemSp extends Serializable
{
	public ResultSet getRsbanggiamua();
	public void setRsbanggiamua(ResultSet rsbanggiamua);
	public ResultSet getRsbanggiaban();
	public void setRsbanggiaban(ResultSet rsbanggiaban);
	public String getGia();
	public void setGia(String gia);
	public String getLoai() ;
	public void setLoai(String loai);
	public String getChietkhau();
	public void setChietkhau(String chietkhau);
	public String getUserid();
	public void setUserid(String userid);
	public String getMsg();
	public void setMsg(String msg) ;
	public void init();
	public String getIdbanggiaban() ;
	public void setIdbanggiaban(String idbanggiaban);
	public String getIdbanggiamua();
	public void setIdbanggiamua(String idbanggiamua);
	public ResultSet getRssanpham();
	public void setRssanpham(ResultSet rssanpham);
	public String getSpid();
	public void setSpid(String spid);
	public boolean save();
	public void Dbclose();
}
