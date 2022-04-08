package geso.dms.distributor.beans.hdnhaphang.imp;

import java.io.Serializable;
import java.sql.ResultSet;

import geso.dms.distributor.beans.hdnhaphang.IHDnhaphangList;
import geso.dms.distributor.db.sql.dbutils;
import geso.dms.distributor.util.Utility;

public class HDnhaphangList implements IHDnhaphangList, Serializable
{
	private static final long serialVersionUID = -9217977546733610214L;
	private String userId;
	private String nppTen;
	private String nppId;
	private String dangnhap;
	private String sku;
	private String tungay;
	private String denngay;
	private String trangthai;
	private ResultSet nhaphangList;
	private String maList;
	private dbutils db;
	private String IdDonHang;
	String Msg ;
	public HDnhaphangList(String[] param)
	{
		this.db = new dbutils();
		this.sku = param[0];
		this.tungay = param[1];
		this.denngay = param[2];
		this.trangthai = param[3];
		this.userId = "";
		this.nppTen = "";
		this.nppId = "";
		
		createNhaphanglist("");
	}
	
	public HDnhaphangList()
	{
		this.db = new dbutils();
		this.userId = "";
		this.nppTen = "";
		this.nppId = "";
		this.sku = "";
		this.tungay = "";
		this.denngay = "";
		this.trangthai = "";
		this.Msg ="";
		this.IdDonHang="";
	}
	
	public ResultSet getNhaphangList()
	{
		return this.nhaphangList;
	}
	
	public void setNhaphangList(ResultSet nhaphangList)
	{
		this.nhaphangList = nhaphangList;
	}

	public String getUserId()
	{
		return this.userId;
	}
	
	public void setUserId(String userId)
	{
		this.userId = userId;
	}
	
	public String getNppId()
	{
		return this.nppId;
	}
	
	public void setNppId(String nppId)
	{
		this.nppId = nppId;
	}

	public String getNppTen()
	{
		return this.nppTen;
	}
	
	public void setNppTen(String nppTen)
	{
		this.nppTen = nppTen;
	}
	
	public String getSKU()
	{
		return this.sku;
	}
	
	public void setSKU(String sku)
	{
		this.sku = sku;
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
	
	public void setTrangthai(String trangthai)
	{
		this.trangthai = trangthai;
	}

	public String getMalist()
	{
		return this.maList;
	}
	
	public void setMalist(String malist)
	{
		this.maList = malist;
	}

	public String getDangnhap()
	{
		return this.dangnhap;
	}
	
	public void setDangnhap(String dangnhap)
	{
		this.dangnhap = dangnhap;
	}

	public void createNhaphanglist(String querystr){
		String query;
		/*
		query = "select a.pk_seq, a.ten, a.ma as dangnhap from nhaphanphoi a, nhanvien b where a.ma = b.dangnhap and b.pk_seq = '" + this.userId + "'";
		
		ResultSet rs = this.db.get(query);
		try{
			if (rs != null){
				rs.next();
				this.nppId = rs.getString("pk_seq");
				this.nppTen = rs.getString("ten");
				this.dangnhap = rs.getString("dangnhap");
			}else{
				this.nppId = "";
				this.nppTen = "";
				this.dangnhap = "";
			}
			*/
			Utility util=new Utility();
			this.nppId = util.getIdNhapp(userId);
			
			this.nppTen = util.getTenNhaPP();
	
			this.dangnhap = util.getDangNhap();
			
			if (querystr.length()>0){
				query = querystr;
			}else{
				query = "select distinct a.ngaychungtu,a.dathang_fk, a.ngaynhan, a.pk_seq as chungtu,e.donvikinhdoanh, f.ten as kbh ,a.sotienavat, b.ten as nguoitao, c.ten as nguoisua, a.trangthai from hdnhaphang a, nhanvien b, nhanvien c, hdnhaphang_sp d, donvikinhdoanh e, kenhbanhang f where a.nguoitao = b.pk_seq and a.nguoisua = c.pk_seq and a.pk_seq = d.nhaphang_fk and a.dvkd_fk = e.pk_seq and a.npp_fk='" + this.nppId + "' and a.kbh_fk = f.pk_seq order by trangthai, ngaychungtu, chungtu";
				System.out.println("HD nhap hang list : "+query);
			}
			this.nhaphangList =  this.db.get(query);
			
		


	}
	public void DBclose(){
		try {
			if(this.nhaphangList != null)
				this.nhaphangList.close();
			
		} catch (Exception e) {			
		}
		if(!(this.db == null)){
			this.db.shutDown();
		}
	}

	public void setMsg(String Msg) {
	  this.Msg = Msg;
		
	}

   public String getMsg() {
		
		return this.Msg;
	}


public void setDonHangId(String iddonhang) {
	
	this.IdDonHang=iddonhang;
}


public String getDonHangId() {
	
	return this.IdDonHang;
}
	
}
