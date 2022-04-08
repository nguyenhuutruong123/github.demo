package geso.dms.center.beans.khuvuc.imp;

import geso.dms.center.beans.khuvuc.IKhuvuc;
import geso.dms.center.beans.khuvuc.IKhuvucList;
import geso.dms.center.db.sql.dbutils;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class KhuvucList implements IKhuvucList 
{
	private static final long serialVersionUID = -9217977546733610214L;
	
	String userId;
	ResultSet vungmien;
	String vmId;
	String trangthai;
	String Msg;
	List<IKhuvuc> kvlist;
	
	dbutils db;
	
	List<Object> dataSearch = new ArrayList<Object>(); 
	
	public List<Object> getDataSearch() {
		return dataSearch;
	}
	public void setDataSearch(List<Object> dataSearch) {
		this.dataSearch = dataSearch;
	}
	public KhuvucList(String[] param)
	{
		this.trangthai = param[0];
		this.vmId = param[1];
		db = new dbutils();
	}
	
	public KhuvucList()
	{
		this.trangthai = "";
		this.vmId = "";
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
	
	public ResultSet getVungMien() 
	{
		return this.vungmien;
	}

	public void setVungmien(ResultSet vungmien) 
	{
		this.vungmien = vungmien;
	}

	public String getVmId() 
	{
		return this.vmId;
	}

	public void setVmId(String vmId) 
	{
		this.vmId = vmId;
	}

	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}

	public List<IKhuvuc> getKvList() 
	{
		return this.kvlist;
	}

	public void setKvList(List<IKhuvuc> kvlist)
	{
		this.kvlist = kvlist;
	}

	private void createVmRS()
	{  				
		
		this.vungmien =  this.db.get("select Ten as vmTen, pk_seq as vmId from vung");;
	}

	public void createKvBeanList(String query)
	{  	  
		ResultSet rs =  this.db.getByPreparedStatement(query,dataSearch);
		List<IKhuvuc> kvlist = new ArrayList<IKhuvuc>();
		if (rs != null){		
			IKhuvuc kvBean;
			String[] param = new String[11];
			try{
				while(rs.next()){
					param[0]= rs.getString("id");
					param[1]= rs.getString("kvTen");
					param[2]= rs.getString("vmTen");
					param[3]= rs.getString("trangthai");
					param[4]= rs.getString("ngaytao");
					param[5]= rs.getString("nguoitao");
					param[6]= rs.getString("ngaysua");
					param[7]= rs.getString("nguoisua");
					param[8]= rs.getString("vmId");
					param[9] = rs.getString("diengiai");
					param[10] = rs.getString("ma");
					kvBean = new Khuvuc(param);
					kvlist.add(kvBean);															
				}
			}catch(Exception e){
		
			}
		}
		
		this.kvlist = kvlist;
	}
	
	public void init(String search) 
	{
		String query;	
		if (search.length() == 0)
		{
			query = "select a.pk_seq as id,isnull(a.ma,'') as ma, a.ten as kvTen, a.trangthai as trangthai, b.pk_seq as vmId, b.ten as vmTen, c.ten as nguoitao, a.ngaytao as ngaytao, d.ten as nguoisua, a.ngaysua as ngaysua, a.diengiai";
			query = query + " from khuvuc a, vung b, nhanvien c, nhanvien d";
			query = query + " where a.vung_fk=b.pk_seq and a.nguoitao = c.pk_seq and a.nguoisua = d.pk_seq";
		}
		else
		{
			query = search;
		}
		
		createKvBeanList(query);  
		createVmRS();
	}

	public void setMsg(String Msg) {
	     this.Msg = Msg;
		
	}

	
	public String getMsg() {
		return this.Msg;
	}
}
