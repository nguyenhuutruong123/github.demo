package geso.dms.center.beans.chungloai.imp;
import java.sql.ResultSet;
import geso.dms.center.beans.chungloai.IChungloaiList;

import geso.dms.center.db.sql.*;


public class ChungloaiList implements IChungloaiList
{
	private static final long serialVersionUID = -9217977546733610215L;
	String cloai;
	String nhId;
	String tungay;
	String denngay;
	String trangthai;
    String Msg;
	ResultSet nhList;
	ResultSet clList;
	dbutils db;
	
	public ChungloaiList(String[] param)
	{
		this.cloai = param[0];
		this.nhId = param[1];
		this.tungay = param[2];
		this.denngay = param[3];
		this.trangthai = param[4];
		this.nhList = getNhanhang();
		this.db = new dbutils();
	}
	
	public ChungloaiList()
	{   this.Msg ="";
		this.cloai = "";
		this.nhId = "";
		this.tungay = "";
		this.denngay = "";
		this.trangthai = "2";
		this.nhList = getNhanhang();
		this.db = new dbutils();
	}

	public String getCloai()
	{
		return this.cloai;
	}

	public void setCloai(String cloai)
	{
		this.cloai = cloai;
	}

	public String  getNhId(){
		return this.nhId;
	}
	
	public void setNhId(String nhId){
		this.nhId = nhId;
	}
	
	public String getTungay()
	{
		return this.tungay;
		
	}

	public void setTungay(String tungay)
	{
		this.tungay = tungay;
	}
	
	public String getDenngay()
	{
		return this.denngay;
		
	}

	public void setDenngay(String denngay)
	{
		this.denngay = denngay;
	}
		
	public String getTrangthai()
	{
		return this.trangthai;
	}

	public void getTrangthai(String trangthai)
	{
		this.trangthai = trangthai;
	}

	public ResultSet getNhList(){
		return this.nhList;
	}
	
	public void setNhList(ResultSet nhList){
		this.nhList = nhList;
	}

	public void setTrangthai(String trangthai)
	{
		this.trangthai = trangthai;
	}
	
	public ResultSet getClList(){
		return this.clList;
	}
	
	public void setClList(ResultSet clList){
		this.clList = clList;
	}

	private ResultSet getNhanhang(){
		dbutils db = new dbutils();
		//String query = "select pk_seq, ten from nhanhang where trangthai = '1' order by ten" ;
		String query = "select distinct a.pk_seq, a.ten from nhanhang a,donvikinhdoanh b where a.dvkd_fk = b.pk_seq and a.trangthai = '1' order by a.ten" ;
		this.nhList =db.get(query);
		return db.get(query);
		
	}
		
	public void DBClose(){
		try{
			if(this.nhList != null) this.nhList.close();
			if(this.clList != null) this.clList.close();
			
			this.db.shutDown();
		}catch(Exception e){}
	}
	public void setMsg(String Msg) {
	   this.Msg =Msg;
		
	}

    public String getMsg() {
		
		return this.Msg;
	}
		
}