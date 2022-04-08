package geso.dms.distributor.beans.denghitratb.imp;

import java.io.Serializable;
import java.sql.ResultSet;

import geso.dms.center.util.Phan_Trang;
import geso.dms.distributor.beans.denghitratb.*;
import geso.dms.distributor.db.sql.dbutils;

public class DenghitratbList extends Phan_Trang implements IDenghitratbList, Serializable
{
	private static final long serialVersionUID = -9217977546733610214L;

	String userId; 
	
	String scheme;
	String tungay;
	String denngay;
	ResultSet rsDenghi;
	String nppId;
	String nppTen;
	String sitecode;
	
	dbutils db;
	
	public DenghitratbList(String[] param)
	{
		this.scheme = param[0];
		this.tungay = param[1];
		this.denngay = param[2];
		db = new dbutils();
	}
	
	public DenghitratbList()
	{
		this.scheme = "";
		this.denngay = "";
		this.tungay = "";
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
	
	
	public void init(String search) 
	{
		this.getNppInfo();
		String query = "";	
		if (search.length() == 0)
		{
			query = "select a.pk_seq as dnttbId, b.pk_seq as cttbId, b.diengiai as cttbTen, a.ngaydenghi, a.ngaysua, a.trangthai, c.ten as nguoitao, d.ten as nguoisua ";
			query = query + "from denghitratrungbay a inner join cttrungbay b on a.cttrungbay_fk = b.pk_seq inner join nhanvien c on a.nguoitao = c.pk_seq inner join nhanvien d on a.nguoisua = d.pk_seq ";
			query = query + "where a.npp_fk = '" + this.nppId + "'";
		}
		else
		{
			query = search;
		}				
		this.rsDenghi=   createSplittingData(50, 10, "ngaydenghi desc, dnttbId desc", query);	
	}
	
	
	@Override
	public void DBclose() 
	{
		try {
			if(this.db != null)
				this.db.shutDown();
			if(this.rsDenghi!=null){
				this.rsDenghi.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
				
	}

	@Override
	public void setRsDenghi(ResultSet rsDenghi)
	{
		this.rsDenghi=rsDenghi;
	}

	@Override
	public ResultSet getRsDenghi()
	{

		return this.rsDenghi;
	}

	
}



