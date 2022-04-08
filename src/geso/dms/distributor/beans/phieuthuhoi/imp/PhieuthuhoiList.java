package geso.dms.distributor.beans.phieuthuhoi.imp;

import geso.dms.center.util.Phan_Trang;
import geso.dms.distributor.beans.phieuthuhoi.IPhieuthuhoi;
import geso.dms.distributor.beans.phieuthuhoi.IPhieuthuhoiList;
import geso.dms.distributor.db.sql.dbutils;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PhieuthuhoiList extends Phan_Trang implements IPhieuthuhoiList, Serializable
{
	private static final long serialVersionUID = -9217977546733610214L;

	String userId; 
	
	String trangthai;
	String tungay;
	
	ResultSet nhanviengn;
	String nvgnId;
		
	List<IPhieuthuhoi> pthlist;
	
	String nppId;
	String nppTen;
	String sitecode;
	
	String msg;
	
	dbutils db;
	
	public PhieuthuhoiList(String[] param)
	{
		this.nvgnId = param[0];
		this.trangthai = param[1];
		this.tungay = param[2];
		
		this.msg = "";
		db = new dbutils();
	}
	
	public PhieuthuhoiList()
	{
		this.nvgnId = "";
		this.trangthai = "";
		this.tungay = "";
		
		this.msg = "";
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
	
	public List<IPhieuthuhoi> getPthList() 
	{		
		return this.pthlist;
	}
	
	public void setPthList(List<IPhieuthuhoi> pthlist) 
	{		
		this.pthlist = pthlist;
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
		//this.dangnhap = util.getDangNhap();
		this.sitecode=util.getSitecode();
	}

	public void init(String search) 
	{
		db = new dbutils();
		this.getNppInfo();
		
		String query = "";	
		if (search.length() == 0)
		{
			query = "select pth.pk_seq as pthId, pth.phieuxuatkho_fk as pxkId, pth.trangthai, pth.ngaytao, pth.ngaysua, nv.ten as nguoitao, nv2.ten as nguoisua ";
			query = query + "from phieuthuhoi pth inner join nhanvien nv on pth.nguoitao = nv.pk_seq inner join nhanvien nv2 on pth.nguoisua = nv2.pk_seq inner join phieuxuatkho pxk on pth.phieuxuatkho_fk = pxk.pk_seq ";
			query = query + " where pxk.npp_fk = '" + this.nppId + "' ";
		}
		else
		{
			query = "select pth.pk_seq as pthId, pth.phieuxuatkho_fk as pxkId, pth.trangthai, pth.ngaytao, pth.ngaysua, nv.ten as nguoitao, nv2.ten as nguoisua ";
			query = query + "from phieuthuhoi pth inner join nhanvien nv on pth.nguoitao = nv.pk_seq inner join nhanvien nv2 on pth.nguoisua = nv2.pk_seq inner join phieuxuatkho pxk on pth.phieuxuatkho_fk = pxk.pk_seq ";
			query = query + " where pxk.npp_fk = '" + this.nppId + "' "+search;
		}	
		
		System.out.println("phieu xuat kho: " + query);
		
		this.createRS();
		this.createPthBeanList(query);		
	}

	private void createPthBeanList(String query) 
	{
		ResultSet rs = createSplittingData(super.getItems(), super.getSplittings(), "pthId desc ", query); //db.get(query);
		List<IPhieuthuhoi> pthlist = new ArrayList<IPhieuthuhoi>();
		
		if(rs != null)
		{
			String[] param = new String[7];
			IPhieuthuhoi pthBean = null;			
			try {
				while(rs.next())
				{	
					param[0]= rs.getString("pthId");
					param[1]= rs.getString("pxkId");
					param[2]= rs.getString("trangthai");
					param[3]= rs.getDate("ngaytao").toString();
					param[4]= rs.getString("nguoitao");
					param[5]= rs.getDate("ngaysua").toString();
					param[6]= rs.getString("nguoisua");
					
					pthBean = new Phieuthuhoi(param);
					pthlist.add(pthBean);
				}
				rs.close();
			}
			catch(Exception e) {}			
		}
		this.pthlist = pthlist;	
	}
	
	public void createRS() 
	{
		String sql = "select pk_seq as nvgnId, ten as nvgnTen from nhanviengiaonhan where npp_fk = '" + this.nppId + "' and trangthai='1'";
		this.nhanviengn = db.get(sql);
	}

	public void DBclose() 
	{
		try 
		{
			if(!(nhanviengn == null))
				nhanviengn.close();
			this.db.shutDown();
			if(pthlist!=null){
				pthlist.clear();
			}
		} 
		catch(Exception e) {}
	}

	public String getMsg() 
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}
}
