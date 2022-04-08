package geso.dms.center.beans.duyettradonhang.imp;

import java.sql.ResultSet;
import java.sql.SQLException;

import geso.dms.center.beans.duyettradonhang.IDuyettradonhangList;
import geso.dms.center.util.Phan_Trang;
import geso.dms.distributor.db.sql.dbutils;

public class DuyettradonhangList  extends Phan_Trang implements IDuyettradonhangList
{
	private static final long serialVersionUID = -9217977546733610214L;

	String userId; //nppId
	String tungay;
	String denngay;
	String trangthai,Msg;
	
	ResultSet nppRs;
	String nppId;
	
	ResultSet dhtvList;
	
	dbutils db;
		
	//Constructor
	public DuyettradonhangList(String[] param)
	{
		this.tungay = param[0];
		this.denngay = param[1];
		this.trangthai = param[2];
		this.nppId = param[3];
		
		db = new dbutils();
	}
	
	public DuyettradonhangList()
	{
		this.tungay = "";
		this.denngay = "";
		this.trangthai = "";
		this.nppId = "";
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
	
	public void init(String search) 
	{
		db = new dbutils();

		String query;	
		if (search.length() == 0)
		{
			query = "select a.pk_seq as dhtvId, a.ngaynhap, a.trangthai, a.ngaytao, a.ngaysua, isnull(b.ten, '') as nguoiduyet, d.ten as khTen, d.pk_seq as khId, e.pk_seq as ddkdId, e.ten as ddkdTen, f.ten as nppTen, f.pk_seq as nppId, a.VAT, isnull(a.ngayduyet, '') as ngayduyet";
			query = query + " from donhangtrave a left join nhanvien b on a.nguoiduyet = b.pk_seq inner join khachhang d on a.khachhang_fk = d.pk_seq inner join daidienkinhdoanh e on a.ddkd_fk = e.pk_seq inner join nhaphanphoi f on a.npp_fk = f.pk_seq";
			query = query + " where a.trangthai in (1, 2,3) and a.donhang_fk is not null ";
			System.out.println("Query la: " + query + "\n");
		}
		else
		{
			query = search;
		}
		this.dhtvList=createSplittingData(50, 10,"NgayNhap DESC, dhtvId desc", query);  
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

	public ResultSet getdhtvList()
	{
		return this.dhtvList;
	}

	public void setdhtvList(ResultSet dhtvList)
	{
		this.dhtvList = dhtvList;
	}
	
	public void DBclose()
	{
			try
			{
				if(dhtvList!=null)
				this.dhtvList.close();
				if(nppRs!=null)
					this.nppRs.close();
				if(db!=null)
					this.db.shutDown();
			}	 catch (SQLException e)
			{
				e.printStackTrace();
			}
	}
	

	
	public String getMsg()
	{
		return this.Msg;
	}

	
	public void setMsg(String Msg)
	{
		this.Msg=Msg;
		
	}
}
