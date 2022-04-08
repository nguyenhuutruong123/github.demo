package geso.dms.distributor.beans.donhangthuhoi.imp;

import geso.dms.distributor.beans.donhangthuhoi.IDonhangthuhoi;
import geso.dms.distributor.beans.donhangthuhoi.IDonhangthuhoiList;
import geso.dms.distributor.db.sql.dbutils;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DonhangthuhoiList implements IDonhangthuhoiList, Serializable
{
	private static final long serialVersionUID = -9217977546733610214L;

	String userId; 
	
	String trangthai;
	String tungay;
	
	ResultSet nhanviengn;
	String nvgnId;
		
	List<IDonhangthuhoi> dhthlist;
	
	String nppId;
	String nppTen;
	String sitecode;
	
	dbutils db;
	
	public DonhangthuhoiList(String[] param)
	{
		this.nvgnId = param[0];
		this.trangthai = param[1];
		this.tungay = param[2];
		db = new dbutils();
	}
	
	public DonhangthuhoiList()
	{
		this.nvgnId = "";
		this.trangthai = "";
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
	
	public ResultSet getNhanvienGN() 
	{		
		return this.nhanviengn;
	}
	
	public void setNhanvienGN(ResultSet nhanviengn) 
	{
		this.nhanviengn = nhanviengn;		
	}
	
	public String getNvgnId() 
	{		
		return this.nvgnId;
	}
	
	public void setNvgnId(String nvgnId) 
	{
		this.nvgnId = nvgnId;		
	}
	
	public List<IDonhangthuhoi> getDhthList() 
	{		
		return this.dhthlist;
	}
	
	public void setDhthList(List<IDonhangthuhoi> dhthlist) 
	{		
		this.dhthlist = dhthlist;
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

	public void init(String search) 
	{
		//db = new dbutils();
		this.getNppInfo();
		
		String query = "";	
		if (search.length() == 0)
		{
			query = "select dhth.pk_seq as dhthId, dhth.phieuxuatkho_fk as pxkId, dhth.trangthai, dhth.ngaytao, dhth.ngaysua, dhth.donhang_fk, nv.ten as nguoitao, nv2.ten as nguoisua ";
			query = query + "from donhangthuhoi dhth inner join nhanvien nv on dhth.nguoitao = nv.pk_seq inner join nhanvien nv2 on dhth.nguoisua = nv2.pk_seq ";
			query = query + "where dhth.npp_fk = '" + this.nppId + "' order by dhth.pk_seq DESC";
		}
		else
		{
			query = search;
		}				
		this.createRS();
		this.createDhthBeanList(query);		
	}

	private void createDhthBeanList(String query) 
	{
		ResultSet rs =  db.get(query);
		List<IDonhangthuhoi> dhthlist = new ArrayList<IDonhangthuhoi>();
		
		if(rs != null)
		{
			String[] param = new String[8];
			IDonhangthuhoi dhthBean = null;			
			try {
				while(rs.next())
				{	
					param[0]= rs.getString("dhthId");
					param[1]= rs.getString("pxkId");
					param[2]= rs.getString("trangthai");
					param[3]= rs.getDate("ngaytao").toString();
					param[4]= rs.getString("nguoitao");
					param[5]= rs.getDate("ngaysua").toString();
					param[6]= rs.getString("nguoisua");
					param[7]= rs.getString("donhang_fk");
					
					dhthBean = new Donhangthuhoi(param);
					dhthlist.add(dhthBean);
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
		this.dhthlist = dhthlist;	
		
	}
	
	public void createRS() 
	{
		String sql = "select pk_seq as nvgnId, ten as nvgnTen from nhanviengiaonhan where npp_fk = '" + this.nppId + "' and trangthai='1'";
		this.nhanviengn = db.get(sql);
	}
	@Override
	public void DBclose() 
	{
		try 
		{
			if(!(nhanviengn == null))
				nhanviengn.close();
			if(this.db != null)
				this.db.shutDown();
			
		} 
		catch(Exception e) {}
	}
}

