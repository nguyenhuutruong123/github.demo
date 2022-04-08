package geso.dms.distributor.beans.banggiasieuthi.imp;

import geso.dms.distributor.db.sql.*;
import geso.dms.distributor.util.Utility;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import geso.dms.distributor.beans.banggiasieuthi.*;

public class BanggiasieuthiList implements IBanggiasieuthiList
{
	private static final long serialVersionUID = -9217977546733610214L;
	
	String userId; //nppId
	
	String tenbanggia;
	
	ResultSet nhacungcap;
	String nccId;
		
	ResultSet donvikinhdoanh;
	String dvkdId;
	
	List<IBanggiasieuthi> bgstList;
	
	String nppId;
	String nppTen;
	String sitecode;
	dbutils db;
	
	public BanggiasieuthiList(String[] param)
	{
		this.tenbanggia = param[0];
		this.nccId = param[1];
		this.dvkdId = param[2];
		this.db = new dbutils();
	}
	
	public BanggiasieuthiList()
	{
		this.tenbanggia = "";
		this.nccId = "";
		this.dvkdId = "";
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
	
	public String getTenbanggia()
	{
		return this.tenbanggia;
	}
	
	public void setTenbanggia(String tenbanggia)
	{
		this.tenbanggia = tenbanggia;
	}
	
	public ResultSet getNcc() 
	{	
		return this.nhacungcap;
	}

	
	public void setNcc(ResultSet ncc) 
	{
		this.nhacungcap = ncc;		
	}
	
	public String getNccId() 
	{		
		return this.nccId;
	}
	
	public void setNccId(String nccId) 
	{
		this.nccId = nccId;		
	}
	
	public ResultSet getDvkd() 
	{		
		return this.donvikinhdoanh;
	}
	
	public void setDvkd(ResultSet dvkd) 
	{
		this.donvikinhdoanh = dvkd;
	}
	
	public String getDvkdId() 
	{
		return this.dvkdId;
	}
	
	public void setDvkdId(String dvkdId) 
	{
		this.dvkdId = dvkdId;		
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
	
	public List<IBanggiasieuthi> getBgstList()
	{
		return this.bgstList;
	}
	
	public void setBgstList(List<IBanggiasieuthi> bgstList)
	{
		this.bgstList = bgstList;
	}
	
	private void getNppInfo()
	{
		/*
		ResultSet rs = this.db.get("select a.pk_seq, a.ten, a.sitecode from nhaphanphoi a, nhanvien b where b.dangnhap = a.ma and b.pk_seq ='" + this.userId + "'");
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
	
	public void init(String search) 
	{
		this.getNppInfo();
		db = new dbutils();
		String query;	
		if (search.length() == 0)
		{
			query = "select * from vwbanggiasieuthi where nppId='" + this.nppId + "'";
		}
		else
		{
			query = search;
		}
		
		this.createBggstBeanList(query); 
		this.createResualset();
	}

	private void createBggstBeanList(String query) 
	{
		ResultSet rs =  db.get(query);
		List<IBanggiasieuthi> bgstlist = new ArrayList<IBanggiasieuthi>();			
		if(rs != null)
		{
			String[] param = new String[10];
			IBanggiasieuthi bgstBean = null;			
			try {
				while(rs.next())
				{		
					param[0]= rs.getString("bgstId");
					param[1]= rs.getString("bgstTen");//ten bang gia
					param[2]= rs.getString("dvkdId");
					//param[2]= rs.getString("dvkdTenVietTat");
				    //param[2]= rs.getString("dvkdTen");
					param[3]= rs.getString("dvkdTen");//ten don vi kinh doanh
					param[4]= rs.getString("ngaytao");
					param[5]= rs.getString("nguoitao");
					param[6]= rs.getString("ngaysua");
					param[7]= rs.getString("nguoisua");								
					//param[7]= rs.getString("dvkdId");
					bgstBean = new Banggiasieuthi(param);
					bgstlist.add(bgstBean);
				}
				rs.close();
			}
			catch(Exception e) {}		
		}
		this.bgstList = bgstlist;
	}
	
	private void createNhacungcap()
	{
		this.nhacungcap = this.db.get("select pk_seq as nccId, ten as nccTen from nhacungcap");
	}
	
	private void createDonvikd()
	{
		String sql = "select a.pk_seq as dvkdId, a.donvikinhdoanh as dvkdTenviettat, a.diengiai as dvkdTen from donvikinhdoanh a,Nhacungcap_dvkd b where a.pk_seq = b.dvkd_fk and a.trangthai='1' and b.checked ='1'";
		this.donvikinhdoanh = this.db.get(sql);
		//this.donvikinhdoanh = this.db.get("select pk_seq as dvkdId, donvikinhdoanh as dvkdTenviettat, diengiai as dvkdTen from Donvikinhdoanh");
	}
	
	private void createResualset()
	{
		this.createNhacungcap();
		this.createDonvikd();
	}
	
	@Override
	public void DBclose() 
	{
		try 
		{
			if(!(nhacungcap == null))
				nhacungcap.close();
			if(!(donvikinhdoanh == null))
				donvikinhdoanh.close();
			if(bgstList!=null){
				bgstList.clear();
			}
			if(db != null)
				db.shutDown();
		} 
		catch(Exception e) {}	
	}

	
	
}