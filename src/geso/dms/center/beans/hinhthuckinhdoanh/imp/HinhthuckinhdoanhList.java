package geso.dms.center.beans.hinhthuckinhdoanh.imp;

import geso.dms.center.beans.hinhthuckinhdoanh.IHinhthuckinhdoanhList;

import java.sql.ResultSet;

import geso.dms.center.db.sql.*;

public class HinhthuckinhdoanhList implements IHinhthuckinhdoanhList{

	private static final long serialVersionUID = -9217977546733610214L;

	// Tieu chi tim kiem
	private String ma;
	private String diengiai;
	private String tungay;
	private String denngay;
	private String trangthai;	
	private String query;
	String Msg;
	private dbutils db;
	
	public HinhthuckinhdoanhList(String[] param)
	{
		this.diengiai = param[0];
		this.tungay = param[1];
		this.denngay = param[2];
		this.trangthai = param[3];
		this.ma = param[4];
		this.db = new dbutils();
	}
	
	public HinhthuckinhdoanhList()
	{
		this.ma = "";
		this.diengiai = "";
		this.tungay = "";
		this.denngay = "";
		this.trangthai = "2";
		this.query = "";
		this.Msg ="";
		this.db = new dbutils();
	}

	public String getMa(){
		return this.ma;
	}
	
	public void setMa(String ma){
		this.ma = ma;
	}

	public String getDiengiai(){
		return this.diengiai;
	}
	
	public void setDiengiai(String diengiai){
		this.diengiai = diengiai;
	}
	
	public String getTungay(){
		return this.tungay;
	}
	
	public void setTungay(String tungay){
		this.tungay = tungay;
	}

	public String getDenngay(){
		return this.denngay;
	}
	
	public void setDenngay(String denngay){
		this.denngay = denngay;
	}
	
	public String getTrangthai(){
		return this.trangthai;
	}
	
	public void setTrangthai(String trangthai){
		this.trangthai = trangthai;
	}
	
	public String getQuery(){
		return this.query;
	}
	
	public void setQuery(String query){
		this.query = query;
	}

	public ResultSet getHtkdlist(){
		if (this.query.length() == 0)
			this.query = "select a.pk_seq, a.ma, a.diengiai, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua from hinhthuckinhdoanh a, nhanvien b, nhanvien c where a.nguoitao = b.PK_SEQ and a.nguoisua = c.PK_SEQ "; 

		return this.db.get(this.query);
	}
	
	public void DBClose(){
		this.db.shutDown();

	}

	public void setMsg(String Msg) {
	
		this.Msg = Msg;
	}

	public String getMsg() {
	
		return this.Msg;
	}
}
