package geso.dms.center.beans.hangcuahang.imp;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import geso.dms.center.beans.hangcuahang.IHangcuahang;
import geso.dms.center.beans.hangcuahang.IHangcuahangList;
import geso.dms.center.db.sql.dbutils;

public class HangcuahangList implements IHangcuahangList
{
	private static final long serialVersionUID = -9217977546733610214L;
	
	List<Object> dataSearch = new ArrayList<Object>(); 
	public List<Object> getDataSearch() {
		return dataSearch;
	}
	public void setDataSearch(List<Object> dataSearch) {
		this.dataSearch = dataSearch;
	}
	
	// Tieu chi tim kiem
	String view;
	String userId;
	String hangcuahang;
	String diengiai;
	String trangthai;
	String Msg;
	//List<IHangcuahang> hchlist; 
	ResultSet hchRs;
	dbutils db;
	
	public HangcuahangList(String[] param)
	{
		this.db = new dbutils();
		this.hangcuahang = param[0];
		this.diengiai = param[1];
		this.trangthai = param[2];
	}
	
	public HangcuahangList()
	{
		this.db = new dbutils();
		this.hangcuahang = "";
		this.diengiai = "";
		this.trangthai = "2";
		this.Msg ="";
		init("");
	}
	
//	public List<IHangcuahang> getHchList() 
//	{
//		return this.hchlist;
//	}
//
//	public void setHchList(List<IHangcuahang> hchlist)
//	{
//		this.hchlist = hchlist;
//	}
	
	public ResultSet getHchList() 
	{
		return this.hchRs;
	}

	public void setHchList(ResultSet hchlist)
	{
		this.hchRs = hchlist;
	}
	
	public String getUserId() 
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;
	}
	
	public String getHangcuahang() 
	{
		return this.hangcuahang;
	}

	public void setHangcuahang(String hangcuahang) 
	{
		this.hangcuahang = hangcuahang;
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
	
//	public void createHchBeanList(String query)
//	{  	  
//		ResultSet rs =  db.get(query);
//		List<IHangcuahang> hchlist = new ArrayList<IHangcuahang>();
//		if (rs != null){		
//			IHangcuahang hchBean;
//			String[] param = new String[11];
//			try{
//				while(rs.next()){
//					param[0]= rs.getString("id");
//					param[1]= rs.getString("hang");
//					param[2]= rs.getString("diengiai");
//					param[3]= rs.getString("trangthai");
//					param[4] = rs.getString("tumuc");
//					param[5] = rs.getString("denmuc");
//					param[6]= rs.getString("ngaytao");
//					param[7]= rs.getString("nguoitao");
//					param[8]= rs.getString("ngaysua");
//					param[9]= rs.getString("nguoisua");
//					param[10]= rs.getString("thangtb")==null?"":rs.getString("thangtb");
//					
//					hchBean = new Hangcuahang(param);
//					hchlist.add(hchBean);															
//				}
//			}catch(Exception e){
//		
//			}
//		}
//		
//		this.hchlist = hchlist;
//	}
	
	public void init(String search) 
	{
		String query;
		
		if (search.length() == 0)
		{
	    	query = "select a.pk_seq as id, a.hang, a.diengiai, a.trangthai,a.tumuc,a.denmuc, a.ngaytao, b.ten as nguoitao, a.ngaysua, c.ten as nguoisua,a.thangtb"; 
			query = query + " from hangcuahang a, nhanvien b, nhanvien c where a.nguoitao = b.pk_seq and a.nguoisua = c.pk_seq order by hang";
			
			System.out.println("query :"+query);
			//createHchBeanList(query);  
			this.hchRs=this.db.get(query);
		}
		else
		{
			query = search;
			
			db.viewQuery(query, this.dataSearch);
			this.hchRs = db.getByPreparedStatement(query, this.dataSearch);
		}
		
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

	@Override
	public String getDateTime() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getView() {
		return view;
	}

	public void setView(String view) {
		this.view = view;
	}

}

