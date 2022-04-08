package geso.dms.distributor.beans.nhanviengiaonhan.imp;

import geso.dms.distributor.beans.nhanviengiaonhan.*;
import geso.dms.distributor.db.sql.dbutils;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import B.FI;

public class NhanviengiaonhanList implements INhanviengiaonhanList, Serializable
{
	private static final long serialVersionUID = -9217977546733610214L;

	String userId; 
	
	String tennv;
	String trangthai;	
	ResultSet ddkd;
	String ddkdId;
	List<Object> datasearch = new ArrayList<Object>();	
	List<INhanviengiaonhan> nvgnlist;
	String msg;
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	String nppId;
	String nppTen;
	String sitecode;
	
	dbutils db;
	
	public NhanviengiaonhanList(String[] param)
	{
		this.tennv = param[0];
		this.trangthai = param[1];
		this.ddkdId = param[2];
		db = new dbutils();
	}
	
	public NhanviengiaonhanList()
	{
		this.tennv = "";
		this.trangthai = "";
		this.ddkdId = "";
		this.msg="";

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
	
	public String getTennv() 
	{	
		return this.tennv;
	}
	
	public void setTennv(String tennv) 
	{
		this.tennv = tennv;		
	}

	public String getTrangthai()
	{	
		return this.trangthai;
	}
	
	public void setTrangthai(String trangthai) 
	{		
		this.trangthai = trangthai;
	}
	
	public ResultSet getDdkd() 
	{		
		return this.ddkd;
	}
	
	public void setDdkd(ResultSet ddkd) 
	{		
		this.ddkd = ddkd;
	}
	
	public String getDdkdId() 
	{	
		return this.ddkdId;
	}
	
	public void setDdkdId(String ddkdId) 
	{
		this.ddkdId = ddkdId;		
	}
	
	public List<INhanviengiaonhan> getNvgnList() 
	{	
		return this.nvgnlist;
	}
	
	public void setNvgnList(List<INhanviengiaonhan> nvgnList)
	{
		this.nvgnlist = nvgnList;		
	}
	
	
	public List<Object> getDatasearch() {
		return datasearch;
	}

	public void setDatasearch(List<Object> datasearch) {
		this.datasearch = datasearch;
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
			query = "select a.pk_seq as nvgnId, a.ten as nvgnTen, a.trangthai, a.diachi, a.npp_fk as nppId, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua, a.dienthoai ";
			query = query + "from nhanviengiaonhan a inner join nhanvien b on a.nguoitao = b.pk_seq inner join nhanvien c on a.nguoisua = c.pk_seq where a.npp_fk='" + this.nppId + "' order by a.pk_seq";
		}
		else
		{
			query = "select a.pk_seq as nvgnId, a.ten as nvgnTen, a.trangthai, a.diachi, a.npp_fk as nppId, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua, a.dienthoai ";
			query = query + "from nhanviengiaonhan a inner join nhanvien b on a.nguoitao = b.pk_seq inner join nhanvien c on a.nguoisua = c.pk_seq where a.npp_fk='" + this.nppId + "' "+search+" order by a.pk_seq";

		}		
		
		System.out.print(query);
		
		this.createNvgnBeanList(query);
		this.createddkdResualset();
	}

	private void createddkdResualset() 
	{
		this.ddkd = db.get("select pk_seq as ddkdId, ten as ddkdTen from daidienkinhdoanh where npp_fk='" + this.nppId + "'");
	}

	private void createNvgnBeanList(String query) 
	{
		ResultSet rs =  db.getByPreparedStatement(query, this.getDatasearch());
		List<INhanviengiaonhan> nvgnlist = new ArrayList<INhanviengiaonhan>();
			
		if(rs != null)
		{
			String[] param = new String[9];
			INhanviengiaonhan nvgnBean = null;			
			try 
			{
				while(rs.next())
				{
					param[0]= rs.getString("nvgnId");
					param[1]= rs.getString("nvgnTen");
					param[2]= rs.getString("trangthai");
					param[3]= rs.getString("diachi");
					param[4]= rs.getString("ngaytao");
					param[5]= rs.getString("nguoitao");
					param[6]= rs.getString("ngaysua");
					param[7]= rs.getString("nguoisua");
					param[8] = rs.getString("dienthoai");
					
					nvgnBean = new Nhanviengiaonhan(param);
					nvgnlist.add(nvgnBean);
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
		this.nvgnlist = nvgnlist;
	}

	public void DBclose() 
	{
			try 
			{
				if(this.db != null)
					this.db.shutDown();
				
				if(!(ddkd == null))
					ddkd.close();
			} 
			catch(Exception e) {}
	}
	
}
