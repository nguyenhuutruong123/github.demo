package geso.dms.center.beans.Danhmucdoitra.imp;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import geso.dms.center.beans.Danhmucdoitra.IDanhmucdoitra;
import geso.dms.center.beans.Danhmucdoitra.IDanhmucdoitraList;
import geso.dms.center.db.sql.dbutils;

public  class DanhmucdoitraList implements IDanhmucdoitraList
{
	private static final long serialVersionUID = -9217977546733610214L;
	
	// Tieu chi tim kiem
	String userId;
	String loaicuahang;
	String diengiai;
	String trangthai;
	String Msg;
	List<IDanhmucdoitra> lchlist; 
	
	dbutils db;
	
	public DanhmucdoitraList(String[] param)
	{
		this.db = new dbutils();
		this.loaicuahang = param[0];
		this.diengiai = param[1];
		this.trangthai = param[2];
	}
	
	public DanhmucdoitraList()
	{
		this.db = new dbutils();
		this.loaicuahang = "";
		this.diengiai = "";
		this.trangthai = "2";
		this.Msg ="";
		init("");
	}
	
	public List<IDanhmucdoitra> getLchList() 
	{
		return this.lchlist;
	}

	public void setLchList(List<IDanhmucdoitra> lchlist)
	{
		this.lchlist = lchlist;
	}
	
	public String getUserId() 
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;
	}
	
	public String getLoaicuahang() 
	{
		return this.loaicuahang;
	}

	public void setLoaicuahang(String loaicuahang) 
	{
		this.loaicuahang = loaicuahang;
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
	
	public void createLchBeanList(String query)
	{  	  
		ResultSet rs =  this.db.get(query);
		List<IDanhmucdoitra> lchlist = new ArrayList<IDanhmucdoitra>();
		if (rs != null){		
			IDanhmucdoitra lchBean;
			String[] param = new String[9];
			try{
				while(rs.next()){
					param[0]= rs.getString("id");
					param[1]= rs.getString("loai");
					param[2]= rs.getString("diengiai");
					param[3]= rs.getString("ngaytao");
					param[4]= rs.getString("nguoitao");
					param[5]= rs.getString("ngaysua");
					param[6]= rs.getString("nguoisua");
					
					lchBean = new Danhmucdoitra(param);
					lchlist.add(lchBean);															
				}
			}catch(Exception e){
		
			}
		}
		
		this.lchlist = lchlist;
	}
	
	public void init(String search) 
	{
		String query;
		
		if (search.length() == 0)
		{
			query = "select a.pk_seq as id, a.loai, a.lydo as diengiai,CONVERT(VARCHAR(10),a.NGAYTAO,110) as ngaytao, b.ten as nguoitao,CONVERT(VARCHAR(10),a.NGaysua,110) as ngaysua, c.ten as nguoisua"; 
			query = query + " from doitrahang a, nhanvien b, nhanvien c where a.nguoitao = b.pk_seq and a.nguoisua = c.pk_seq";
		}
		else
		{
			query = search;
		}
		
		createLchBeanList(query);  
	}
	
	public void closeDB(){
		if(this.db != null)
			this.db.shutDown();
	}


	public void setMsg(String Msg) {
	   this.Msg = Msg;
	}

	
	public String getMsg() {
		return this.Msg;
	}

	

}

