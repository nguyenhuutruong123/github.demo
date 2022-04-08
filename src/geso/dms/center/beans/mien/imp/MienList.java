package geso.dms.center.beans.mien.imp;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import geso.dms.center.beans.mien.IMien;
import geso.dms.center.beans.mien.IMienList;
import geso.dms.center.db.sql.dbutils;

public class MienList implements IMienList
{
	private static final long serialVersionUID = -9217977546733610214L;
	
	// Tieu chi tim kiem
	String userId;
	String vungmien;
	String diengiai;
	String trangthai;
	String Msg;
	List<IMien> vmlist; 
	
	dbutils db;
	
	public MienList(String[] param)
	{
		this.db = new dbutils();
		this.vungmien = param[0];
		this.diengiai = param[1];
		this.trangthai = param[2];
		
		//init("");
	}
	
	public MienList()
	{
		this.db = new dbutils();
		this.vungmien = "";
		this.diengiai = "";
		this.trangthai = "2";
		this.Msg = "";
		init("");
	}
	
	public List<IMien> getVmList() 
	{
		return this.vmlist;
	}

	public void setVmList(List<IMien> vmlist)
	{
		this.vmlist = vmlist;
	}
	
	public String getUserId() 
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;
	}
	
	public String getMien() 
	{
		return this.vungmien;
	}

	public void setMien(String vungmien) 
	{
		this.vungmien = vungmien;
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
	
	public void createVmBeanList(String query)
	{  	  
		ResultSet rs =  db.get(query);
		List<IMien> vmlist = new ArrayList<IMien>();
		if (rs != null){		
			IMien vmBean;
			String[] param = new String[10];
			try{
				while(rs.next()){
					param[0]= rs.getString("id");
					param[1]= rs.getString("ten");
					param[2]= rs.getString("diengiai");
					param[3]= rs.getString("trangthai");
					param[4]= rs.getDate("ngaytao").toString();
					param[5]= rs.getString("nguoitao");
					param[6]= rs.getDate("ngaysua").toString();
					param[7]= rs.getString("nguoisua");
					param[8]= rs.getString("ma");
					vmBean = new Mien(param);
					vmlist.add(vmBean);															
				}
			}catch(Exception e){
		
			}
		}
		
		this.vmlist = vmlist;
	}
	
	public void init(String search) 
	{
		String query;	
		if (search.length() == 0)
		{
			query = "select a.pk_seq as id,isnull(a.ma,'') as ma, a.ten, a.diengiai, a.trangthai, a.ngaytao, b.ten as nguoitao, a.ngaysua, c.ten as nguoisua"; 
			query = query + " from Mien a, nhanvien b, nhanvien c where a.nguoitao = b.pk_seq and a.nguoisua = c.pk_seq order by a.ten";
		}
		else
		{
			query = search;
		}
		
		createVmBeanList(query);  
	}

	public void closeDB(){
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

