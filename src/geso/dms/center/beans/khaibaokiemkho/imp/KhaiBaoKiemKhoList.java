package geso.dms.center.beans.khaibaokiemkho.imp;

import geso.dms.center.util.Phan_Trang;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;
import geso.dms.center.beans.khaibaokiemkho.IKhaiBaoKiemKhoList;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

public class KhaiBaoKiemKhoList extends Phan_Trang implements IKhaiBaoKiemKhoList, Serializable
{
	
	/**
   * 
   */
  private static final long serialVersionUID = 1L;
	String userId;

	String diengiai;
	String trangthai; 
	String msg;

	String tungay;
	String dengay;
	ResultSet kiemkhoRs;
	
	dbutils db;
	
	public KhaiBaoKiemKhoList()
	{
		this.userId = "";

		this.trangthai = "";
		this.diengiai = "";
		this.tungay = "";
		this.dengay = "";
		this.msg = "";
		this.db = new dbutils();
		
	}
	
	public String getUserId() 
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;	
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
		String sql = "";
		
		if(query.length() > 0)
			sql = query;
		else
		{	
			sql = " select a.pk_seq, a.DIENGIAI, d.ten as nguoitao, a.ngaytao as ngaytao,  " +
				  " 	e.ten as nguoisua, a.ngaysua as ngaysua, " +
				  " 	isnull(a.TrangThai,0) as TRANGTHAI, a.TuNgay, a.DENNGAY " +
				  " from KHAIBAOKIEMKHO a, nhanvien d, nhanvien e   " +
				  " where a.nguoitao = d.pk_seq and a.nguoisua = e.pk_seq";
		}
		
		System.out.println("__Bang gia : " + sql);
		this.kiemkhoRs = db.get(sql);
		
		
	}

	public void DbClose() 
	{
		try 
		{
			if(this.kiemkhoRs != null)
				this.kiemkhoRs.close();
			this.db.shutDown();
		} 
		catch (SQLException e) {e.printStackTrace();}
	}

	@Override
	public String getTungay() {
		return this.tungay;
	}

	@Override
	public void setTungay(String value) {
		this.tungay = value;
	}

	@Override
	public String getDenngay() {
		return this.dengay;
	}

	@Override
	public void setDenngay(String value) {
		this.dengay = value;
	}

	@Override
	public ResultSet getKiemkhoRs() {
		return this.kiemkhoRs;
	}


}
