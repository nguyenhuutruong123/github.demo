package geso.dms.distributor.beans.dangkytrungbay.imp;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import geso.dms.distributor.beans.dangkytrungbay.IDangkytrungbay;
import geso.dms.distributor.beans.dangkytrungbay.IDangkytrungbayList;
import geso.dms.distributor.db.sql.dbutils;

public class DangkytrungbayList implements IDangkytrungbayList, Serializable
{
	private static final long serialVersionUID = -9217977546733610214L;

	String userId; 
	
	String scheme;
	String tungay;
	String denngay;
		
	ResultSet dktblist;
	
	String nppId;
	String nppTen;
	String sitecode;
	 String trangthai;
	
	dbutils db;
	
	public DangkytrungbayList(String[] param)
	{
		this.scheme = param[0];
		this.tungay = param[1];
		this.denngay = param[2];

		db = new dbutils();
	}
	
	public DangkytrungbayList()
	{
		this.scheme = "";
		this.denngay = "";
		this.tungay = "";
		this.trangthai="";
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
		this.nppId=util.getIdNhapp(this.userId);
		this.nppTen=util.getTenNhaPP();
		this.sitecode=util.getSitecode();
	}
	
	public String getScheme() 
	{		
		return this.scheme;
	}
	
	public void setScheme(String scheme) 
	{
		this.scheme = scheme;		
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
	
	public ResultSet getDktbList() 
	{		
		return this.dktblist;
	}
	
	public void init(String search) 
	{
		//db = new dbutils();
		this.getNppInfo();
		
		String query = "";	
		if (search.length() == 0)
		{
			query = "select a.pk_seq as dktbId, b.pk_seq as cttbId, a.ngaydangky , b.diengiai as cttbTen, a.ngaysua, a.trangthai, c.ten as nguoitao, d.ten as nguoisua ";
			query = query + "from DKTRUNGBAY_npp a inner join cttrungbay b on a.cttrungbay_fk = b.pk_seq inner join nhanvien c on a.nguoitao = c.pk_seq inner join nhanvien d on a.nguoisua = d.pk_seq ";
			query = query + "where a.npp_fk = '" + this.nppId + "' ";
		}
		else
		{
			query = search;
		}	
		System.out.println("get du lieu" + query);
		this.dktblist = this.db.get(query);
	}
	
	
	public void DBclose() 
	{
		if(this.db != null) this.db.shutDown();
		if(dktblist!=null){
			try {
				dktblist.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	
}


