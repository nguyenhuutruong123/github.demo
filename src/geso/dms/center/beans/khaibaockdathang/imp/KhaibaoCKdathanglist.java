package geso.dms.center.beans.khaibaockdathang.imp;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import geso.dms.center.beans.khaibaockdathang.IKhaibaoCKdathang;
import geso.dms.center.beans.khaibaockdathang.IKhaibaoCKdathanglist;
import geso.dms.center.db.sql.dbutils;

public class KhaibaoCKdathanglist implements IKhaibaoCKdathanglist {

private static final long serialVersionUID = -9217977546733610214L;
	
	// Tieu chi tim kiem
	String userId;
	String scheme;
	String tungay;
	String denngay;
	ResultSet npplist;
	String nppId;
	String Msg;
	String loaick;
	String trangthai;
	List<IKhaibaoCKdathang> ckdhlist; 
	
	dbutils db;
	
	public KhaibaoCKdathanglist(String[] param)
	{
		this.db = new dbutils();
		this.scheme = param[0];
		//this.trangthai = param[4];
		this.loaick = param[1];
		this.tungay = param[2];
		this.denngay = param[3];
		//this.npp = param[4];
		createNPPList();
	}
	
	public KhaibaoCKdathanglist()
	{
		this.db = new dbutils();
		this.scheme = "";
		this.trangthai = "";
		this.loaick = "";
		this.tungay = "";
		this.denngay = "";
		this.nppId = "";
		this.Msg = "";
		createNPPList();
	}
	
	public List<IKhaibaoCKdathang> getKhaibaoCKdathangList() 
	{
		return this.ckdhlist;
	}

	public void setKhaibaoCKdathangList(List<IKhaibaoCKdathang> ckdhlist)
	{
		this.ckdhlist = ckdhlist;
	}
	
	public String getUserId() 
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;
	}
	
	public String getScheme() 
	{
		return this.scheme;
	}

	public void setScheme(String scheme) 
	{
		this.scheme = scheme;
	}

	public String getLoaiCK() 
	{
		return this.loaick;
	}

	public void setLoaiCK(String loaick) 
	{
		this.loaick = loaick;
	}
	
	public String getTungay() 
	{
		return this.tungay;
	}

	public void setTungay(String tungay) 
	{
		this.tungay = tungay;
	}
	
	public String getDenngay() 
	{
		return this.denngay;
	}

	public void setDenngay(String denngay) 
	{
		this.denngay = denngay;
	}
	
	public String getNppId() 
	{
		return this.nppId;
	}

	public void setNppId(String nppId) 
	{
		this.nppId = nppId;
	}
	
	public ResultSet getNPPList() 
	{
		return this.npplist;
	}

	public void setNPPList(ResultSet npplist) 
	{
		this.npplist = npplist;
	}

	private void createckBeanList(String query)
	{  	  
		ResultSet rs =  db.get(query);
		List<IKhaibaoCKdathang> ckdhList = new ArrayList<IKhaibaoCKdathang>();
		if (rs != null){		
			IKhaibaoCKdathang ckdhBean;
			String[] param = new String[12];
			try{
				while(rs.next())
				{
					param[0]= rs.getString("id");
					param[1] = rs.getString("scheme");
					param[9]= rs.getString("loaick");
					param[2]= rs.getString("ngaytao");
					param[3]= rs.getString("nguoitao");
					param[4]= rs.getString("ngaysua");
					param[5]= rs.getString("nguoisua");
					param[6] = rs.getString("tungay");
					param[7] = rs.getString("denngay");
					param[8] = rs.getString("diengiai");
					param[9] = rs.getString("trangthai");
					ckdhBean = new KhaibaoCKdathang(param);
					ckdhBean.setDiengiai( rs.getString("diengiai"));
					ckdhList.add(ckdhBean);															
				}
			}catch(java.sql.SQLException e){
		
			}
		}
		
		this.ckdhlist = ckdhList;
	}
	
	public void init(String search) 
	{
		String query;	
		if (search.length() == 0)
		{
			query = "select a.pk_seq as id, a.scheme, a.loaick, a.ngaytao, b.ten as nguoitao, a.ngaysua, c.ten as nguoisua, a.tungay, a.denngay, isnull(a.diengiai, 'na') as diengiai, a.trangthai from chietkhaudathang a, nhanvien b, nhanvien c ";
			query = query + " where a.nguoitao = b.pk_seq and a.nguoisua = c.pk_seq"; 			
			System.out.println("Query la: " + query + "\n");
		}
		else
		{
			query = search;
		}
		
		createckBeanList(query);  
	}

	private void createNPPList()
	{
		this.npplist =  this.db.get("select PK_SEQ, MA, TEN  from NhaPhanPhoi where trangthai = '1' and PRIANDSECOND = '0' ");
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

	@Override
	public String getTrangthai() {
		// TODO Auto-generated method stub
		return this.trangthai;
	}

	@Override
	public void setTrangthai(String trangthai) {
		// TODO Auto-generated method stub
		this.trangthai = trangthai;
	}
	
}
