package geso.dms.center.beans.banggiabanlechuan.imp;

import geso.dms.center.util.Phan_Trang;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;
import geso.dms.center.beans.banggiabanlechuan.IBangGiaBanLeChuanList;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BangGiaBanLeChuanList   extends Phan_Trang implements IBangGiaBanLeChuanList, Serializable
{
	
	/**
   * 
   */
  private static final long serialVersionUID = 1L;
	String userId;
	String congtyId;
	String ma;
	String diengiai;
	String trangthai; 
	String msg;
	
	ResultSet dvkdIds;
	String dvkdId;
	ResultSet vungrs;
	String vungid;
	ResultSet diabanrs;
	String diabanid;
	ResultSet kenhIds;
	String kenhId;	
	ResultSet bangiaRs;
	
	dbutils db;
	Utility util;
	
	public BangGiaBanLeChuanList()
	{
		this.userId = "";

		this.ma = "";
		this.trangthai = "";
		this.diengiai = "";
		this.dvkdId = "";
		this.kenhId = "";
		this.msg = "";
		this.vungid="";
		this.diabanid="";
		this.db = new dbutils();
		this.util = new Utility();
		this.dvkdIds  =  this.db.get("select donvikinhdoanh as dvkd, pk_seq as dvkdId from DonViKinhDoanh where trangthai = '1' ");
		this.kenhIds  =  this.db.get("select diengiai as kenh, pk_seq as kenhId from kenhbanhang where trangthai='1' ");
	}
	
	public String getUserId() 
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;	
	}

	public String getMa() 
	{
		return this.ma;
	}

	public void setMa(String ma) 
	{
		this.ma = ma;
	}
	
	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}

	public String getDiengiai() 
	{
		return this.diengiai;
	}

	public void setDiengiai(String diengiai) 
	{
		this.diengiai = diengiai;
	}

	public String getMsg() 
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}

	public void init(String query) 
	{
		this.getNppInfo();
		String sql = "";
		if(query.length() > 0)
			sql = query;
		else
		{	
			sql = " select a.pk_seq, a.ten, a.trangthai , d.ten as nguoitao, a.ngaytao as ngaytao,  " +
				  " 	e.ten as nguoisua, a.ngaysua as ngaysua, b.donvikinhdoanh as dvkd, c.ten as kenh, c.pk_seq as kenhId," +
				  " 	a.TrangThai,a.TuNgay " +
				  " from BANGGIABANLECHUAN a, donvikinhdoanh b, kenhbanhang c, nhanvien d, nhanvien e   " +
				  " where a.dvkd_fk = b.pk_seq and a.KENH_FK = c.pk_seq and a.nguoitao = d.pk_seq and a.nguoisua = e.pk_seq AND a.kenh_fk in "+util.quyen_kenh(userId)+" " +
				  "		 " ;
		}
		System.out.println("BGBLC: "+sql);
		this.bangiaRs = db.get(sql);
		
		query = "SELECT PK_SEQ,TEN FROM VUNG WHERE TRANGTHAI='1'";
		this.vungrs = db.get(query);		
		
		query = " SELECT PK_SEQ, TEN FROM TINHTHANH WHERE 1=1";
		
		this.diabanrs = db.get(query);
	}

	public void DbClose() 
	{
		try 
		{
			if(this.bangiaRs != null)
				this.bangiaRs.close();
			this.vungrs.close();
			this.diabanrs.close();
			this.db.shutDown();
		} 
		catch (SQLException e) {e.printStackTrace();}
	}

	public ResultSet getBanggiaRs() 
	{
		return this.bangiaRs;
	}

	public void setBanggiaRs(ResultSet banggiaRs) 
	{
		this.bangiaRs = banggiaRs;
	}

	public String getCongtyId() 
	{
		return this.congtyId;
	}

	public void setCongtyId(String congtyId) 
	{
		this.congtyId = congtyId;
	}
	
	public String getDvkdId() 
	{
		return this.dvkdId;
	}

	public void setDvkdId(String dvkdId) 
	{
		this.dvkdId = dvkdId;
	}
	
	public String getKenhId() 
	{
		return this.kenhId;
	}

	public void setKenhId(String kenhId) 
	{
		this.kenhId = kenhId;
	}
	
	public ResultSet getDvkd() 
	{
		return this.dvkdIds;
	}

	public void setDvkd(ResultSet dvkdIds) 
	{
		this.dvkdIds = dvkdIds;
	}

	public ResultSet getKenh() 
	{
		return this.kenhIds;
	}

	public void setKenh(ResultSet kenhIds) 
	{
		this.kenhIds = kenhIds;
	}
	
	String nppId;
	String nppTen;
	String sitecode;

	public String getNppId()
  {
  	return nppId;
  }

	public void setNppId(String nppId)
  {
  	this.nppId = nppId;
  }

	public String getNppTen()
  {
  	return nppTen;
  }

	public void setNppTen(String nppTen)
  {
  	this.nppTen = nppTen;
  }

	public String getSitecode()
  {
  	return sitecode;
  }

	public void setSitecode(String sitecode)
  {
  	this.sitecode = sitecode;
  }
	
	private void getNppInfo()
	{
		Utility util=new Utility();
		this.nppId=util.getIdNhapp(this.userId);
		this.nppTen=util.getTenNhaPP();
		this.sitecode=util.getSitecode();
	}


	public String getVungId() {

		return this.vungid;
	}


	public void setVungId(String vungid) {

		this.vungid=vungid;
	}


	public String getDiabanId() {

		return this.diabanid;
	}


	public void setDiabanId(String diabanid) {

		this.diabanid=diabanid;
	}


	public ResultSet getVungRs() {

		return this.vungrs;
	}


	public void setVungRs(ResultSet vungrs) {

		this.vungrs=vungrs;
	}


	public ResultSet getDiabanRs() {

		return this.diabanrs;
	}


	public void setDiabanRs(ResultSet diabanrs) {

		this.diabanrs=diabanrs;
	}

}
