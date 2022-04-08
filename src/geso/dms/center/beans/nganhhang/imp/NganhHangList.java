package geso.dms.center.beans.nganhhang.imp;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import geso.dms.center.beans.nganhhang.INganhHangList;
import geso.dms.center.db.sql.dbutils;

public class NganhHangList implements INganhHangList
{
	private static final long serialVersionUID = -9217977546733610214L;
	
	// Tieu chi tim kiem
	String userId;
	String ten;
	String diengiai;
	String trangthai;
	String msg;
	
	ResultSet list;
	String view = "";
	dbutils db;
	
	
	List<Object> dataSearch = new ArrayList<Object>(); 
	
	public List<Object> getDataSearch() {
		return dataSearch;
	}
	public void setDataSearch(List<Object> dataSearch) {
		this.dataSearch = dataSearch;
	}
	
	public NganhHangList(String[] param)
	{
		this.db = new dbutils();
		this.ten = param[0];
		this.diengiai = param[1];
		this.trangthai = param[2];
		
	}
	
	public NganhHangList()
	{
		this.db = new dbutils();
		this.ten = "";
		this.diengiai = "";
		this.trangthai = "";
		this.msg ="";
	}
	
	public String getUserId() 
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;
	}
	
	public String getTen() 
	{
		return this.ten;
	}

	public void setTen(String ten) 
	{
		this.ten = ten;
	}

	public String getDiengiai() 
	{
		return this.diengiai;
	}

	public void setDiengiai(String diengiai) 
	{
		this.diengiai = diengiai;
	}

	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}

	@Override
	public ResultSet getList() {
		return this.list;
	}

	@Override
	public void setList(ResultSet list) {
		this.list = list;
	}

	@Override
	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String getMsg() {
		return this.msg;
	}
	
	
	public void init(String search) 
	{
		String query;	
		if (search.length() == 0)
		{
			query = " select a.pk_seq, a.ten, a.diengiai, a.trangthai, d.donvikinhdoanh, a.ngaytao, b.ten as nguoitao, a.ngaysua, c.ten as nguoisua " +
					" from nganhhang a " +
					" inner join donvikinhdoanh d on d.pk_seq = a.dvkd_fk " +
					" inner join nhanvien b on b.pk_seq=a.nguoitao " +
					" inner join nhanvien c on a.nguoisua = c.pk_seq ";
		}
		else
		{
			query = search;
		}
		
		//this.list = db.get(query);
		this.list = db.getByPreparedStatement(query, dataSearch);
		
		
		
	}

	public void DBClose(){
		if (this.db != null) 
			this.db.shutDown();
	}
	
	public String getView() {
		return view;
	}

	public void setView(String view) {
		this.view = view;
	}


	
	
}
