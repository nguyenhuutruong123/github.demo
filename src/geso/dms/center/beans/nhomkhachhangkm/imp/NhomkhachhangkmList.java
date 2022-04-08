package geso.dms.center.beans.nhomkhachhangkm.imp;

import java.sql.ResultSet;

import geso.dms.center.beans.nhomkhachhangkm.INhomkhachhangkmList;
import geso.dms.center.util.Utility;
import geso.dms.distributor.db.sql.dbutils;

public class NhomkhachhangkmList implements INhomkhachhangkmList{
     
	private String Diengiai;
	private String Trangthai;
	private String Tungay;
	private String Denngay;
	private String userId;
	private ResultSet Dskh;
	String msg;
	dbutils db = new dbutils();
	public NhomkhachhangkmList(){ 
		
		 this.Diengiai = "";;
		 this.Trangthai = "";
		 this.Tungay = "";
		 this.Denngay = "";
		 this.msg ="";
	}

	public void setDiengiai(String Diengiai) {

         this.Diengiai = Diengiai;
         
	}

	public String getDiengiai() {
	
		return this.Diengiai;
	}

	public void setTrangthai(String Trangthai) {
	
		this.Trangthai = Trangthai;
	}
	
	public String getTrangthai() {

		return this.Trangthai;
	}

	public void setTungay(String Tungay) {

		this.Tungay = Tungay;
	}

	public String getTungay() {

		return this.Tungay;
	}

	public void setDenngay(String Denngay) {

		this.Denngay = Denngay;
	} 

	public String getDenngay() {

		return this.Denngay;
	}

	public void init(String st) {
		String query = "";
		if (st.length() > 0)
		{
			String tk = "";
			geso.dms.distributor.util.Utility util = new geso.dms.distributor.util.Utility();
			if (this.Diengiai.length()>0)
				tk = tk + " and upper(dbo.ftBoDau(a.diengiai)) like upper(N'%"+ util.replaceAEIOU(this.Diengiai) +"%')";
			if (this.Trangthai.length()>0)
			{
				tk = tk + "  and a.trangthai = '"+ this.Trangthai +"'";
			}

			if (this.Tungay.length()>0)
				tk = tk + " and a.ngaytao >= '"+ this.Tungay +"'";
			if (this.Denngay.length()>0)
				tk = tk + " and a.ngaytao <= '"+ this.Denngay +"'";
			
			query = "select a.pk_seq,a.diengiai,a.trangthai,a.ngaytao,a.ngaysua,b.ten as nguoitao,c.ten as nguoisua, a.trangthai " +
			"\n from NHOMKHACHHANGNPP a, nhanvien b ,nhanvien c where a.nguoitao = b.pk_seq and a.nguoisua = c.pk_seq " + tk;

		}
		else
			query = "\n select a.pk_seq,a.diengiai,a.trangthai,a.ngaytao,a.ngaysua,b.ten as nguoitao,c.ten as nguoisua, a.trangthai " +
			"\n from NHOMKHACHHANGNPP a, nhanvien b ,nhanvien c where a.nguoitao = b.pk_seq and a.nguoisua = c.pk_seq ";

		System.out.println("Init List: " + query);
		this.Dskh = db.get(query);
	}

	public void setDskh(ResultSet Dskh) {
		
		this.Dskh = Dskh;		
	}

	public ResultSet getDskh() {
		
		return this.Dskh;
	}

	public void setuserId(String userId) {
	
		this.userId = userId;
		
	}

	public String getuserId() {

		return this.userId;
	}

	public void setmsg(String msg) {
		
		this.msg = msg;
	}

	public String getmsg() {
	
		return this.msg;
	}

}
