package geso.dms.center.beans.duyetdontrahangnpp.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import geso.dms.distributor.beans.donhangtrave.IDonhangtrave;
import geso.dms.distributor.beans.donhangtrave.IDonhangtraveList;
import geso.dms.distributor.beans.donhangtrave.imp.Donhangtrave;
import geso.dms.distributor.db.sql.dbutils;

public class Duyetdontrahangnpp implements IDonhangtraveList {

	String userId; //nppId
	String tungay;
	String denngay;
	String trangthai;
	
	String nppId;
	String nppTen;
	String sitecode;
	
	List<IDonhangtrave> dhtvlist;
	
	ResultSet daidienkd;
	String ddkdId;
	
	dbutils db;
		
	//Constructor
	public Duyetdontrahangnpp(String[] param)
	{
		this.tungay = param[0];
		this.denngay = param[1];
		this.trangthai = param[2];
		this.ddkdId = param[3];
		db = new dbutils();
	}
	
	public Duyetdontrahangnpp()
	{
		this.tungay = "";
		this.denngay = "";
		this.trangthai = "";
		this.ddkdId = "";
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
	
	public ResultSet getDaidienkd() 
	{	
		return this.daidienkd;
	}
	
	public void setDaidienkd(ResultSet daidienkd) 
	{
		this.daidienkd = daidienkd;		
	}
	
	public String getDdkdId()
	{	
		return this.ddkdId;
	}
	
	public void setDdkdId(String ddkdId) 
	{
		this.ddkdId = ddkdId;		
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
	
	public List<IDonhangtrave> getDhtvList() 
	{	
		return this.dhtvlist;
	}

	public void setDhtvList(List<IDonhangtrave> dhtvlist) 
	{
		this.dhtvlist = dhtvlist;		
	}
	
	public void createDdkd()
	{
		this.daidienkd = db.get("select ten as ddkdTen, pk_seq as ddkdId from daidienkinhdoanh ");//where npp_fk = '" + this.nppId +"'");
	}
	
	private void getNppInfo(){
		
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
	}
	
	public void createDhtvBeanList(String query)
	{
		ResultSet rs =  db.get(query);
		List<IDonhangtrave> dhtvlist = new ArrayList<IDonhangtrave>();
			
		if(rs != null)
		{
			String[] param = new String[14];
			IDonhangtrave dhtvBean = null;			
			try {
				while(rs.next())
				{	
					param[0]= rs.getString("dhtvId");
					param[1]= rs.getString("khId");
					param[2]= rs.getString("khTen");
					param[3]= rs.getString("ngaynhap");
					param[4]= rs.getString("nppTen");
					param[5]= rs.getString("ddkdTen");
					param[6]= rs.getString("trangthai");
					param[7]= rs.getString("ngaytao");
					param[8]= rs.getString("nguoitao");
					param[9]= rs.getString("ngaysua");
					param[10]= rs.getString("nguoisua");
								
					//param[11]= rs.getString("chietkhau");
					param[11]= rs.getString("VAT");
					param[12]= rs.getString("ddkdId");
																											
					dhtvBean = new Donhangtrave(param);
					dhtvlist.add(dhtvBean);
				}
				rs.close();
			}
			catch(Exception e) {}			
		}
		this.dhtvlist = dhtvlist;
	}
	
	public void init(String search) 
	{
		db = new dbutils();
	//	this.getNppInfo();
		String query;	
		if (search.length() == 0)
		{
			query = "select a.pk_seq as dhtvId, a.ngaynhap, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua, d.ten as khTen, d.pk_seq as khId, e.pk_seq as ddkdId, e.ten as ddkdTen, f.ten as nppTen, a.VAT";
			query = query + " from donhangtrave a inner join nhanvien b on a.nguoitao = b.pk_seq inner join nhanvien c on a.nguoisua = c.pk_seq inner join khachhang d on a.khachhang_fk = d.pk_seq inner join daidienkinhdoanh e on a.ddkd_fk = e.pk_seq inner join nhaphanphoi f on a.npp_fk = f.pk_seq";
			query = query + " where a.trangthai = '1' or a.trangthai = '2' order by a.pk_seq";
		}
		else
		{
			query = search;
		}
		System.out.println(query);
		this.createDhtvBeanList(query);  
		this.createDdkd();
	
	}

	public void DBclose()
	{
		try 
		{
			if(!(this.daidienkd == null))
				this.daidienkd.close();		
		} 
		catch(Exception e) {}
	}

			
}
