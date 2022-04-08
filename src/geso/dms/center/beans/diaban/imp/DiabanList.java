package geso.dms.center.beans.diaban.imp;

import geso.dms.center.beans.diaban.IDiaban;
import geso.dms.center.beans.diaban.IDiabanList;
import geso.dms.center.db.sql.dbutils;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DiabanList implements IDiabanList 
{
	private static final long serialVersionUID = -9217977546733610214L;
	
	String userId;
	ResultSet khuvucRs;
	String khuvucId;
	String trangthai;
	String Msg;
	List<IDiaban> dblist;
	
	dbutils db;
	
	public DiabanList(String[] param)
	{
		this.trangthai = param[0];
		this.khuvucId = param[1];
		db = new dbutils();
	}
	
	public DiabanList()
	{
		this.trangthai = "";
		this.khuvucId = "";
		this.Msg ="";
		db = new dbutils();
		init("");
	}
	
	public String getUserId() 
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;
	}
	
	public ResultSet getKhuvucRs() 
	{
		return this.khuvucRs;
	}



	public String getKhuvucId() 
	{
		return this.khuvucId;
	}

	public void setKhuvucId(String vmId) 
	{
		this.khuvucId = vmId;
	}

	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}

	public List<IDiaban> getDiabanList() 
	{
		return this.dblist;
	}

	public void setDiabanList(List<IDiaban> kvlist)
	{
		this.dblist = kvlist;
	}

	private void createKvRs()
	{  				
		
		this.khuvucRs =  this.db.get("select Ten , pk_seq  from khuvuc");;
	}

	public void createKvBeanList(String query)
	{  	  
		ResultSet rs =  db.get(query);
		List<IDiaban> dblist = new ArrayList<IDiaban>();
		if (rs != null){		
			IDiaban dbBean;
			String[] param = new String[10];
			try{
				while(rs.next()){
					param[0]= rs.getString("id");
					param[1]= rs.getString("dbTen");
					param[2]= rs.getString("trangthai");
					param[3]= rs.getString("ngaytao");
					param[4]= rs.getString("nguoitao");
					param[5]= rs.getString("ngaysua");
					param[6]= rs.getString("nguoisua");
					param[7]= rs.getString("diengiai");
					param[8] = rs.getString("khuvuc_fk");
					param[9] = rs.getString("kvTen");
					dbBean = new Diaban(param);
					dblist.add(dbBean);															
				}
			}catch(Exception e){
		
			}
		}
		
		this.dblist = dblist;
	}
	
	public void init(String search) 
	{
		String query;	
		if (search.length() == 0)
		{
			query = "select a.pk_seq as id, a.ten as dbTen, a.trangthai as trangthai, a.khuvuc_fk, b.ten as kvTen, c.ten as nguoitao, a.ngaytao as ngaytao, d.ten as nguoisua, a.ngaysua as ngaysua, a.diengiai";
			query = query + " from diaban a, khuvuc b, nhanvien c, nhanvien d";
			query = query + " where a.khuvuc_fk=b.pk_seq and a.nguoitao = c.pk_seq and a.nguoisua = d.pk_seq";
		}
		else
		{
			query = search;
		}
		
		createKvBeanList(query);  
		createKvRs();
	}

	public void setMsg(String Msg) {
	     this.Msg = Msg;
		
	}

	
	public String getMsg() {
		return this.Msg;
	}

	public void closeDB(){
		if (this.db != null) {
			this.db.shutDown();
		}
	}
}
