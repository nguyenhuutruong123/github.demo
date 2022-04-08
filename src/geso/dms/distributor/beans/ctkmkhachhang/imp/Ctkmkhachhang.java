package geso.dms.distributor.beans.ctkmkhachhang.imp;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import geso.dms.distributor.beans.ctkmkhachhang.ICtkmkhachhang;
import geso.dms.distributor.db.sql.dbutils;

public class Ctkmkhachhang implements ICtkmkhachhang, Serializable 
{
	private static final long serialVersionUID = 1L;
	
	String userId;
    String Trangthai;
    String Diengiai;
    ResultSet Dskh;
	String Id;
	String nppId;
	String nppTen;
	String Scheme;
	String ctkmId;
	
	String tungay;
	String denngay;
	String ten;
	
	ResultSet ddkdlist;
	String[] ddkdIds;
	
	ResultSet Schemes;
	String[] khachhang;
	dbutils db;
	
	public Ctkmkhachhang()
	{
		this.Trangthai = "";
		this.Diengiai ="";
		this.nppId ="";
		this.nppTen="";
		this.Id ="";
		this.Scheme ="";
		this.ctkmId ="";
		
		this.ten = "";
		this.tungay = "";
		this.denngay = "";
		
		this.db =  new dbutils();
	}
	
	public void setuserId(String userId) 
	{
		this.userId = userId;
		getNppInfo();
	}
	
	public String getuserId()
	{
		return this.userId;
	}

	public void setTrangthai(String Trangthai) {
		
		this.Trangthai = Trangthai;
	}

	public String getTrangthai() {
		
		return this.Trangthai;
	}

	public void setDiengiai(String Diengiai) {
		
		this.Diengiai = Diengiai;
	}

	public String getDiengiai() {
		
		return this.Diengiai;
	}

	public void setId(String Id) {
		this.Id = Id;
		
	}

	public String getId() {
		
		return this.Id;
	}
  
	public void init() 
	{
		getNppInfo();
		
		String sql ="select * from nhomkhachhangnpp where pk_seq = '" + this.Id + "' ";
		ResultSet rs = db.get(sql);
		try 
		{
			rs.next();
			this.Diengiai = rs.getString("diengiai");
			rs.close();
		} 
		catch(Exception e) {}
		
		this.ddkdlist = db.get("select pk_seq as ddkdId, ten as ddkdTen, isnull(dienthoai, '') as dienthoai, isnull(diachi, '') as diachi from daidienkinhdoanh where npp_fk='" + this.nppId + "' and trangthai = '1'");
		System.out.println("ddkdlist"+"select pk_seq as ddkdId, ten as ddkdTen, isnull(dienthoai, '') as dienthoai, isnull(diachi, '') as diachi from daidienkinhdoanh where npp_fk='" + this.nppId + "' and trangthai = '1'");
		sql = "select scheme + ' - ' + diengiai as ten, tungay, denngay from CTKHUYENMAI where PK_SEQ = '" + this.ctkmId + "' ";
		System.out.println("______Khoi tao CTKM: " + sql);
		
		rs = db.get(sql);
		if(rs != null)
		{
			try
			{
				while(rs.next())
				{
					this.ten = rs.getString("ten");
					this.tungay = rs.getString("tungay");
					this.denngay = rs.getString("denngay");
				}
				rs.close();
			} catch(Exception e) {}
		}
		
		this.createKhachHang();
	}

	
	private void createKhachHang()
	{
		if(this.ddkdIds == null)
		{
			String query = "select pk_seq, smartId, ten, isnull(diachi, '') as diachi, isnull(dienthoai, '') as dienthoai, '1' as chon from khachhang where trangthai = 1 and npp_fk ='" + this.nppId + "' and npp_fk in (select npp_fk from ctkm_npp where ctkm_fk = "+this.ctkmId+") and pk_seq in (select khachhang_fk from CTKM_KHACHHANG where nhomkhnpp_fk ='"+ this.Id +"')";
			query = query + " union select pk_seq, smartId, ten, isnull(diachi, '') as diachi, isnull(dienthoai, '') as dienthoai, '0' as chon from khachhang where trangthai = 1 and npp_fk ='" + this.nppId + "' and npp_fk in (select npp_fk from ctkm_npp where ctkm_fk = "+this.ctkmId+") and pk_seq not in (select khachhang_fk from CTKM_KHACHHANG where nhomkhnpp_fk ='"+ this.Id +"')";
			System.out.println("__ể__+_"+query);
			//System.out.println(query);
			this.Dskh = db.get(query);
			
			//khoi tao ddkdIds
			//query = "select distinct ddkd_fk from tuyenbanhang where pk_seq in (select distinct tbh_fk from khachhang_tuyenbh where khachhang_fk in (  select pk_seq from khachhang where npp_fk ='" + this.nppId + "' and pk_seq in (select khachhang_fk from CTKM_KHACHHANG where nhomkhnpp_fk ='"+ this.Id +"')  ))";
			query = "select distinct ddkd_fk from CTKM_NHOMKHNPP_DDKD where nhomkhnpp_fk ='"+ this.Id +"'";
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				try 
				{
					String ddkd = "";
					while(rs.next())
					{
						if(rs.getString("ddkd_fk") != null)
							ddkd += rs.getString("ddkd_fk") + ",";
					}
					if(ddkd.length() > 0)
					{
						ddkd = ddkd.substring(0, ddkd.length() - 1);
						this.ddkdIds = ddkd.split(",");
					}
				} 
				catch(Exception e) {}
				finally{try {
					if(rs != null)
						rs.close();
				} catch (Exception e2) {
					
				}}
			}
		}
		else
		{
			String ddkd = "";
			for(int i = 0; i < this.ddkdIds.length; i++)
				ddkd += this.ddkdIds[i] + ",";
			
			if(ddkd.length() > 0)
			{
				ddkd = ddkd.substring(0, ddkd.length() - 1);
				
				String query = "select distinct pk_seq, smartId, ten, isnull(diachi, '') as diachi, isnull(dienthoai, '') as dienthoai, '1' as chon from khachhang where npp_fk ='" + this.nppId + "' and pk_seq in (select khachhang_fk from CTKM_KHACHHANG where nhomkhnpp_fk ='" + this.Id + "') and pk_seq in (select khachhang_fk from khachhang_tuyenbh where tbh_fk in (select pk_seq from tuyenbanhang where ddkd_fk in (" + ddkd + ")))";
				query += " union "; 
				query += "select distinct pk_seq, smartId, ten, isnull(diachi, '') as diachi, isnull(dienthoai, '') as dienthoai, '0' as chon from khachhang where npp_fk ='" + this.nppId + "' and pk_seq not in (select khachhang_fk from CTKM_KHACHHANG where nhomkhnpp_fk ='" + this.Id + "') and pk_seq in (select khachhang_fk from khachhang_tuyenbh where tbh_fk in (select pk_seq from tuyenbanhang where ddkd_fk in (" + ddkd + ")))";
				System.out.println("__ể__+_"+query);
				this.Dskh = db.get(query);
			}
		}
	}

	public void setnppId(String nppId) {
		
		this.nppId = nppId;
	}

	
	public String getnppTen() {
		
		return this.nppTen;
	}

	
	public void setnppTen(String nppId) {
		
		
	}
	public String getnppId() {
		
		return this.nppId;
	}
	public void setScheme(String Scheme) 
	{
		this.Scheme = Scheme ;
	}

	
	public String getScheme() {
		
		return this.Scheme;
	}
	private void getNppInfo()
	{	
		//Phien ban moi
		geso.dms.distributor.util.Utility util=new geso.dms.distributor.util.Utility();
		this.nppId=util.getIdNhapp(this.userId);
		this.nppTen=util.getTenNhaPP();				
	}

	public ResultSet getDsnpp() {
	
		return null;
	}

	public void setDskh(ResultSet Dskh) {
		this.Dskh = Dskh;
		
	}
	public ResultSet getDskh() {
		
		return this.Dskh;
	}

	public void setSchemes(ResultSet Schemes) {
		this.Schemes = Schemes;
		
	}

	public ResultSet getSchemes() {
		return this.Schemes;
	}

	public boolean save() 
	{
		try
		{
			System.out.println("vào đây");
			db.getConnection().setAutoCommit(false);
			db.update("delete from ctkm_khachhang where nhomkhnpp_fk ='"+ this.Id +"' and khachhang_fk in (select pk_seq from khachhang where npp_fk = '" + this.nppId + "')");
			for(int i = 0; i < this.khachhang.length; i++)
			{  
				String sql ="insert ctkm_khachhang values('"+ this.Id +"','"+ this.khachhang[i] +"')";
				System.out.println("_____+___________"+sql);
				if(!db.update(sql))
				{
					db.getConnection().rollback();
					System.out.println("Cau lenh Insert ctkm_khachhang: " + sql);
					return false;
				}
				
			}
			if(this.ddkdIds != null)
			{
				db.update("delete from CTKM_NHOMKHNPP_DDKD where nhomkhnpp_fk ='"+ this.Id +"' and ddkd_fk in (select pk_seq from daidienkinhdoanh where npp_fk = '" + this.nppId + "')");
				System.out.println("____+____"+"delete from CTKM_NHOMKHNPP_DDKD where nhomkhnpp_fk ='"+ this.Id +"' and ddkd_fk in (select pk_seq from daidienkinhdoanh where npp_fk = '" + this.nppId + "')");
				for(int i = 0; i < this.ddkdIds.length ; i++)
				{  
					String query = "insert CTKM_NHOMKHNPP_DDKD values('"+ this.Id +"','"+ this.ddkdIds[i] +"')";
					
					if(!db.update(query))
					{
						db.getConnection().rollback();
						System.out.println("Cau lenh Insert CTKM_NHOMKHNPP_DDKD: " + query);
						return false;
					}
				}
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return true;
		} 
		catch(Exception e) 
		{
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return false;
		}
		
	}

	public void setKhachhang(String[] khachhang) 
	{
		this.khachhang = khachhang;
	}

	public String[] getKhachhang() 
	{
		return this.khachhang;
	}

	public void setCtkmId(String ctkmId) 
	{
		this.ctkmId = ctkmId;
	}

	public String getCtkmId() 
	{
		return this.ctkmId;
	}

	
	public void setDdkdIds(String[] ddkdIds)
	{
		this.ddkdIds = ddkdIds;
	}

	public Hashtable<Integer, String> getDdkdIds() 
	{
		Hashtable<Integer, String> selected = new Hashtable<Integer, String>();
		if(this.ddkdIds != null){
			int size = (this.ddkdIds).length;
			int m = 0;
			while(m < size){
				selected.put(new Integer(m), this.ddkdIds[m]) ;
				//System.out.println("Danh Sach DDKD : "+ this.ddkdIds[m] );
				m++;
			}
		}else{
			selected.put(new Integer(0), "null");
		}
		return selected;
	}
	
	public void setDdkdList(ResultSet ddkdlist) 
	{
		this.ddkdlist = ddkdlist;
	}

	public ResultSet getDdkdList() 
	{
		return this.ddkdlist;
	}

	
	public void DBclose() {
		
		
		try {

			if(this.ddkdlist != null)
				this.ddkdlist.close();
			if(this.Dskh != null)
				this.Dskh.close();
			if(this.db != null)
				this.db.shutDown();
			
		} catch (Exception e) {
			
		}
		
	}
	
	public void setTen(String ten) 
	{
		this.ten = ten;
	}

	public String getTen()
	{
		return this.ten;
	}

	public void setTungay(String tungay) 
	{
		this.tungay = tungay;
	}

	public String getTungay() 
	{
		return this.tungay;
	}

	public void setDenngay(String denngay) 
	{
		this.denngay = denngay;	
	}

	public String getDenngay() 
	{
		return this.denngay;
	}

	
}
