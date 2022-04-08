package geso.dms.distributor.beans.mucchietkhau.imp;

import geso.dms.distributor.beans.mucchietkhau.IMucchietkhauList;
import geso.dms.distributor.beans.mucchietkhau.IMucchietkhau;
import geso.dms.distributor.db.sql.dbutils;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MucchietkhauList implements IMucchietkhauList , Serializable
{
	private static final long serialVersionUID = -9217977546733610214L;

	String userId; 
	String mucchietkhau;
	String khachhang;		
	List<IMucchietkhau> mcklist;
	
	String nppId;
	String nppTen;
	String sitecode;
	String Msg;
	dbutils db;
	
	public MucchietkhauList(String[] param)
	{
		this.mucchietkhau = param[0];
		this.khachhang = param[1];
		this.db = new dbutils();
	}
	
	public MucchietkhauList()
	{
		this.mucchietkhau = "";
		this.khachhang = "";
		this.Msg ="";
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
	
	public String getMucchietkhau() 
	{
		return this.mucchietkhau;
	}

	public void setMucchietkhau(String mucchietkhau) 
	{
		this.mucchietkhau = mucchietkhau;
	}

	public String getKhachhang() 
	{
		return this.khachhang;
	}

	public void setKhachhang(String khachhang) 
	{
		this.khachhang = khachhang;
	}
	
	public String getNppId() 
	{
		return this.nppId;
	}

	public void setNppId(String nppId) 
	{
		this.nppId = nppId;
	}
	
	public String getNppTen() 
	{
		return this.nppTen;
	}

	public void setNppTen(String nppTen) 
	{
		this.nppTen = nppTen;
	}
	
	public String getSitecode() 
	{
		return this.sitecode;
	}

	public void setSitecode(String sitecode) 
	{
		this.sitecode = sitecode;
	}
	
	public List<IMucchietkhau> getMckList()
	{
		return this.mcklist;
	}
	
	private void getNppInfo()
	{		
		/*ResultSet rs = this.db.get("select a.pk_seq, a.ten, a.sitecode from nhaphanphoi a, nhanvien b where b.dangnhap = a.ma and b.pk_seq ='" + this.userId + "'");
		try{
			if (!(rs == null)){
				rs.next();
				this.nppId = rs.getString("pk_seq");
				this.nppTen = rs.getString("ten");
				this.sitecode = rs.getString("sitecode");
				
			}else
			{
				this.nppId = "";
				this.nppTen = "";
				this.sitecode = "";				
			}
			
		}catch(Exception e){}	
		*/
		//Phien ban moi
		geso.dms.distributor.util.Utility util=new geso.dms.distributor.util.Utility();
		this.nppId=util.getIdNhapp(this.userId);
		this.nppTen=util.getTenNhaPP();
		this.sitecode=util.getSitecode();
	}

	public void setMucchietkhauList(List<IMucchietkhau> mcklist) 
	{
		this.mcklist = mcklist;
	}

	public void init(String search) 
	{
		this.getNppInfo();
		
		String query = "";	
		if (search.length() == 0)
		{
			query = "select * from vwMucchietkhau where nppId = '" + this.nppId + "' order by chietkhau";
		}
		else
		{
			query = search;
		}		
		this.createMckBeanList(query);
	}

	private void createMckBeanList(String query) 
	{
		ResultSet rs =  this.db.get(query);
		List<IMucchietkhau> mcklist = new ArrayList<IMucchietkhau>();
			
		if(rs != null)
		{
			String[] param = new String[7];
			IMucchietkhau mckBean = null;			
			try {
				while(rs.next())
				{	
					param[0]= rs.getString("mckId");
					param[1]= rs.getString("chietkhau");
					param[2]= rs.getString("diengiai");
					param[3]= rs.getString("ngaytao");
					param[4]= rs.getString("nguoitao");
					param[5]= rs.getString("ngaysua");
					param[6]= rs.getString("nguoisua");
																
					mckBean = new Mucchietkhau(param);
					mcklist.add(mckBean);
				}
				rs.close();
			}
			catch(Exception e) {}
			finally{try {
				if(rs != null)
				rs.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}}
			
		}
		this.mcklist = mcklist;		
	}


	
	public void DBclose() 
	{
		if(!(this.db == null))
		this.db.shutDown();
	}

	public void setMsg(String Msg) {
		this.Msg = Msg;
		
	}

	public String getMsg() {
		return this.Msg;
	}
	

}
