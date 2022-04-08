package geso.dms.center.beans.mucchietkhau.imp;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import geso.dms.center.beans.mucchietkhau.IChietkhau;
import geso.dms.center.beans.mucchietkhau.IChietkhauList;
import geso.dms.center.db.sql.dbutils;

public class ChietkhauList implements IChietkhauList
{
private static final long serialVersionUID = -9217977546733610214L;
	
	// Tieu chi tim kiem
	String userId;
	String sotien;
	String mucchietkhau;
	String Msg;
	List<IChietkhau> cklist; 
	
	dbutils db;
	
	public ChietkhauList(String[] param)
	{
		this.db = new dbutils();
		this.sotien = param[0];
		this.mucchietkhau = param[1];
	}
	
	public ChietkhauList()
	{
		this.db = new dbutils();
		this.sotien = "";
		this.mucchietkhau = "";
		this.Msg = "";
	}
	
	public List<IChietkhau> getChietkhauList() 
	{
		return this.cklist;
	}

	public void setChietkhauList(List<IChietkhau> cklist)
	{
		this.cklist = cklist;
	}
	
	public String getUserId() 
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;
	}
	
	public String getSotien() 
	{
		return this.sotien;
	}

	public void setSotien(String sotien) 
	{
		this.sotien = sotien;
	}

	public String getChietkhau() 
	{
		return this.mucchietkhau;
	}

	public void setChietkhau(String chietkhau) 
	{
		this.mucchietkhau = chietkhau;
	}

	private void createckBeanList(String query)
	{  	  
		ResultSet rs =  db.get(query);
		List<IChietkhau> ckList = new ArrayList<IChietkhau>();
		if (rs != null){		
			IChietkhau ckBean;
			String[] param = new String[10];
			try{
				while(rs.next())
				{
					param[0]= rs.getString("id");
					param[1]= rs.getString("sotien");
					param[2]= rs.getString("mucchietkhau");
					param[3]= rs.getString("ngaytao");
					param[4]= rs.getString("nguoitao");
					param[5]= rs.getString("ngaysua");
					param[6]= rs.getString("nguoisua");
					
					ckBean = new Chietkhau(param);
					ckBean.setDiengiai( rs.getString("diengiai"));
					ckList.add(ckBean);															
				}
			}catch(java.sql.SQLException e){
		
			}
		}
		
		this.cklist = ckList;
	}
	
	public void init(String search) 
	{
		String query;	
		if (search.length() == 0)
		{
			query = "select a.pk_seq as id, isnull(a.diengiai, 'na') as diengiai, a.sotien, a.mucchietkhau, a.ngaytao, b.ten as nguoitao, a.ngaysua, c.ten as nguoisua from Chietkhau a, nhanvien b, nhanvien c ";
			query = query + " where a.nguoitao = b.pk_seq and a.nguoisua = c.pk_seq"; 			
			System.out.println("Query la: " + query + "\n");
		}
		else
		{
			query = search;
		}
		
		createckBeanList(query);  
	}

	public void DBClose(){
		if (this.db != null) 
			this.db.shutDown();
	}


	public void setMsg(String Msg) {
	     this.Msg = Msg;
		
	}
	public String getMsg() {
          return this.Msg;
	}

	
}
