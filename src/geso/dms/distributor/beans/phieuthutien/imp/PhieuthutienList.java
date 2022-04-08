package geso.dms.distributor.beans.phieuthutien.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;
import geso.dms.distributor.beans.phieuthutien.IPhieuthutien;
import geso.dms.distributor.beans.phieuthutien.IPhieuthutienList;
import geso.dms.distributor.db.sql.dbutils;

public class PhieuthutienList implements IPhieuthutienList, Serializable
{
	private static final long serialVersionUID = -9217977546733610214L;

	String userId; 
	
	String nvgnId;
	ResultSet nvgn;
	String tungay;
	String denngay;
	String sotien;
		
//	List<IPhieuthutien> pttlist;
	ResultSet pttlist;
	String nppId;
	String nppTen;
	String sitecode;
	String Msg;
	dbutils db;
	
	
	public PhieuthutienList()
	{
		this.nvgnId = "";
		this.denngay = "";
		this.tungay = "";
		this.sotien = "";
		this.Msg="";
		
		db = new dbutils();
	}
	
	public String getUserId()
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;
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
		this.nppId = util.getIdNhapp(this.userId);
		this.nppTen = util.getTenNhaPP();
		//this.dangnhap = util.getDangNhap();
		this.sitecode = util.getSitecode();
	}
	
	public String getNvgnId() 
	{		
		return this.nvgnId;
	}
	
	public void setNvgnId(String nvgnId) 
	{
		this.nvgnId = nvgnId;		
	}
	
	public ResultSet getNvgn() 
	{		
		return this.nvgn;
	}
	
	public void setNvgn(ResultSet nvgn) 
	{
		this.nvgn = nvgn;		
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
	
	public String getSotien() 
	{	
		return this.sotien;
	}
	
	public void setSotien(String sotien) 
	{
		this.sotien = sotien;		
	}
	
	public ResultSet getPttList() 
	{		
		return this.pttlist;
	}
	
	public void setPttList(ResultSet pttlist) 
	{
		this.pttlist = pttlist;	
	}
	
	public void init(String search) 
	{
		this.getNppInfo();
		
		String query = "";	
		if (search.length() == 0)
		{
			query = "select ptt.pk_seq as pttId, nvgn.ten as nvgnTen, ptt.sotien, ptt.ngaythu, ptt.diengiai, " + 
					" ptt.trangthai, ptt.ngaytao, ptt.ngaysua, nv1.ten as nguoitao, nv2.ten as nguoisua" + 
					" from phieuthutien_nvgn ptt" + 
					" inner join nhanviengiaonhan nvgn on ptt.nvgn_fk = nvgn.pk_seq" + 
					" inner join nhanvien nv1 on ptt.nguoitao = nv1.pk_seq" + 
					" inner join nhanvien nv2 on ptt.nguoisua = nv2.pk_seq" + 
					" where ptt.npp_fk = '" + this.nppId + "'" ; 
		}
		else
		{
			query = search;
		}				
		System.out.println("sql init of Phieuthulienlist :"+query);
		
		this.pttlist = this.db.get(query);
		
		this.nvgn = this.db.get("select pk_seq as nvgnId, ten as nvgnTen from nhanviengiaonhan where npp_fk='" + this.nppId + "'");
		
		
	}
	
	
	
	public void DBclose() 
	{
		try{
			if(this.pttlist != null) this.pttlist.close();
			if(this.pttlist != null) this.nvgn.close();
			if(this.db != null)
			this.db.shutDown();
		}catch(Exception e){}
	}

	@Override
	public String getMsg() {
		// TODO Auto-generated method stub
		return this.Msg;
	}

	@Override
	public void setMsg(String mgs) {
		// TODO Auto-generated method stub
		this.Msg=mgs;
	}
	
}
