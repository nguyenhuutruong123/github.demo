package geso.dms.center.beans.duyettrasanpham.imp;

import java.sql.ResultSet;
import java.sql.SQLException;

import geso.dms.center.beans.duyettrasanpham.IDuyettrasanphamList;
import geso.dms.distributor.db.sql.dbutils;

public class DuyettrasanphamList implements IDuyettrasanphamList 
{
	private static final long serialVersionUID = -9217977546733610214L;

	String userId; //nppId
	String tungay;
	String denngay;
	String trangthai;
	
	ResultSet nppRs;
	String nppId;
	
	ResultSet dsplist;
	
	dbutils db;
		
	//Constructor
	public DuyettrasanphamList(String[] param)
	{
		this.tungay = param[0];
		this.denngay = param[1];
		this.trangthai = param[2];
		this.nppId = param[3];
		db = new dbutils();
	}
	
	public DuyettrasanphamList()
	{
		this.tungay = "";
		this.denngay = "";
		this.trangthai = "";
		this.nppId = "";
		//init("");
	}
	
	public String getUserId()
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;
	}
	
	public String getTrangthai() 
	{	
		return this.trangthai;
	}
	
	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;	
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
	
	public void createDhtvBeanList(String query)
	{
		this.dsplist =  db.get(query);
	}
	
	public void init(String search) 
	{
		db = new dbutils();

		String query;	
		if (search.length() == 0)
		{
			query = "select a.pk_seq as dhtvId, a.ngaynhap, a.trangthai, a.ngaytao, a.ngaysua, isnull(b.ten, '') as nguoiduyet, d.ten as khTen, d.pk_seq as khId, e.pk_seq as ddkdId, e.ten as ddkdTen, f.ten as nppTen, f.pk_seq as nppId, a.VAT, isnull(a.ngayduyet, '') as ngayduyet";
			query = query + " from donhangtrave a left join nhanvien b on a.nguoiduyet = b.pk_seq inner join khachhang d on a.khachhang_fk = d.pk_seq inner join daidienkinhdoanh e on a.ddkd_fk = e.pk_seq inner join nhaphanphoi f on a.npp_fk = f.pk_seq";
			query = query + " where a.trangthai in (1,2, 3) and a.donhang_fk is null order by a.pk_seq desc";
		}
		else
		{
			query = search;
		}
		
		this.createDhtvBeanList(query);  
		this.createNpp();
	}

	private void createNpp()
	{
		this.nppRs = db.get("select pk_seq as nppId, ten as nppTen from nhaphanphoi where trangthai = '1' and sitecode = convsitecode");
	}

	public ResultSet getNppRs()
	{
		return this.nppRs;
	}
	
	public void setNppRs(ResultSet nppRs)
	{
		this.nppRs = nppRs;
	}

	public ResultSet getDspList()
	{
		return this.dsplist;
	}

	public void setDspList(ResultSet dsplist)
	{
		this.dsplist = dsplist;
	}
	
	public void DBclose()
	{
		
	}

}
