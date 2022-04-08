package geso.dms.center.beans.vitricuahang.imp;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import geso.dms.center.beans.vitricuahang.IVitricuahang;
import geso.dms.center.beans.vitricuahang.IVitricuahangList;
import geso.dms.center.db.sql.dbutils;

public class VitricuahangList implements IVitricuahangList
{
	private static final long serialVersionUID = -9217977546733610214L;
	
	List<Object> dataSearch = new ArrayList<Object>(); 
	public List<Object> getDataSearch() {
		return dataSearch;
	}
	public void setDataSearch(List<Object> dataSearch) {
		this.dataSearch = dataSearch;
	}
	
	String view;
	
	// Tieu chi tim kiem
	String userId;
	String vitricuahang;
	String diengiai;
	String trangthai;
	String Msg;
	List<IVitricuahang> hchlist; 
	
	dbutils db;
	
	public VitricuahangList(String[] param)
	{
		this.db = new dbutils();
		this.vitricuahang = param[0];
		this.diengiai = param[1];
		this.trangthai = param[2];
		//init("");
	}
	
	public VitricuahangList()
	{
		this.db = new dbutils();
		this.vitricuahang = "";
		this.diengiai = "";
		this.trangthai = "2";
		this.Msg ="";
		init("");
	}
	
	public List<IVitricuahang> getHchList() 
	{
		return this.hchlist;
	}

	public void setHchList(List<IVitricuahang> hchlist)
	{
		this.hchlist = hchlist;
	}
	
	public String getUserId() 
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;
	}
	
	public String getVitricuahang() 
	{
		return this.vitricuahang;
	}

	public void setVitricuahang(String vitricuahang) 
	{
		this.vitricuahang = vitricuahang;
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
	
	public void createHchBeanList(String query, String search, List<Object> dataSearch)
	{  	  
		ResultSet rs;
		if(search.trim().length() > 0){
			rs = db.getByPreparedStatement(search, dataSearch);
		} else {
			rs =  db.get(query);
		}
		
		List<IVitricuahang> hchlist = new ArrayList<IVitricuahang>();
		if (rs != null){		
			IVitricuahang hchBean;
			String[] param = new String[10];
			try{
				while(rs.next()){
					param[0]= rs.getString("id");
					param[1]= rs.getString("vitri");
					param[2]= rs.getString("diengiai");
					param[3]= rs.getString("trangthai");
					param[4]= rs.getString("ngaytao");
					param[5]= rs.getString("nguoitao");
					param[6]= rs.getString("ngaysua");
					param[7]= rs.getString("nguoisua");
					
					hchBean = new Vitricuahang(param);
					hchlist.add(hchBean);															
				}
			}catch(Exception e){
		
			}
		}
		
		this.hchlist = hchlist;
	}
	
	public void init(String search) 
	{
		String query = "select a.pk_seq as id, a.vitri, a.diengiai, a.trangthai, a.ngaytao, b.ten as nguoitao, a.ngaysua, c.ten as nguoisua"; 
			query = query + " from vitricuahang a, nhanvien b, nhanvien c where a.nguoitao = b.pk_seq and a.nguoisua = c.pk_seq";
		
		createHchBeanList(query, search, this.dataSearch);  
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

	public String getView() {
		return view;
	}

	public void setView(String view) {
		this.view = view;
	}

}

