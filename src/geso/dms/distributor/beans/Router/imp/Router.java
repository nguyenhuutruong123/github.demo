package geso.dms.distributor.beans.Router.imp;

import java.io.Serializable;
import java.sql.ResultSet;

import geso.dms.distributor.beans.Router.IDRouter;
import geso.dms.distributor.db.sql.dbutils;

public class Router implements IDRouter, Serializable{

	String nppId;
	String ddkdId;
	String tuyenId;
	ResultSet tuyen;
	ResultSet npp;
	ResultSet ddkd;
	String userId;
	dbutils db;
	ResultSet danhsach;
	public Router()
	{
		this.nppId ="";
		this.ddkdId ="";
		this.tuyenId ="";
		this.userId ="";
		db = new dbutils();
	}
	
	
	public void setnppId(String nppId) {
		
		this.nppId = nppId;
	}

	
	public String getnppId() {
		
		return this.nppId;
	}

	
	public void setddkdId(String ddkdId) {
		this.ddkdId = ddkdId;
		
	}

	
	public String getddkdId() {
		
		return this.ddkdId;
	}

	
	public ResultSet getnpp() {
		
		return this.npp;
	}

	
	public ResultSet getddkd() {
		
		return this.ddkd;
	}

	private void getNppInfo()
	{		
		/*ResultSet rs = this.db.get("select a.pk_seq, a.ten, a.sitecode from nhaphanphoi a, nhanvien b where b.dangnhap = a.ma and b.pk_seq ='" + this.userId + "'");
		try{
			if (!(rs == null)){
				rs.next();
				this.nppId = rs.getString("pk_seq");
		//	this.nppTen = rs.getString("ten");
			//	this.sitecode = rs.getString("sitecode");
				
			}else
			{
				this.nppId = "";
				//this.nppTen = "";
				//this.sitecode = "";				
			}
			
		}catch(Exception e){}
		*/
		//Phien ban moi
		geso.dms.distributor.util.Utility util=new geso.dms.distributor.util.Utility();
		this.nppId=util.getIdNhapp(this.userId);
		//this.nppTen=util.getTenNhaPP();
		//this.dangnhap = util.getDangNhap();
		//this.sitecode=util.getSitecode();
	}
	
	public void init() {
		getNppInfo();
		String sql;
	
        sql ="select * from daidienkinhdoanh where npp_fk ='"+ this.nppId+"'";
      
        this.ddkd = db.get(sql);
        
        if(this.ddkdId.length()>0)
        	sql ="select distinct ngaylamviec from tuyenbanhang where ddkd_fk ='"+ this.ddkdId +"' order by ngaylamviec desc";
        else
        	sql ="select distinct ngaylamviec from tuyenbanhang order by ngaylamviec desc ";
        this.tuyen = db.get(sql);
       String st="";
        if(this.nppId.length()>0)
	    {
	    st = st + "tbh.npp_fk ='"+ this.nppId +"'";
	    }
	    if(this.tuyenId.length()>0)
	    {
	    	if(st.length()>0)
	    		st = st + " and tbh.ngaylamviec ='"+ this.tuyenId +"' ";
	    	else
	    		st ="tbh.ngaylamviec ='"+ this.tuyenId +"' ";
	    }
	    if(this.ddkdId.length()>0)
	    {
	    	if(st.length()>0)
	    		st = st + " and tbh.ddkd_fk ='"+ this.ddkdId +"' ";
	    	else
	    		st = st + " tbh.ddkd_fk ='"+ this.ddkdId +"' ";
	    }
	    if(st.length()>0)
	    	st = " where " + st;
	    //khoi tao ket noi csdl
		
		 sql  = "select tbh.ngaylamviec,kh.pk_seq as Customer_Key,kh.ten as Customer_Name,kh.diachi as Address,qh.ten as province,case when ds.tonggiatri is null then 0 else ds.tonggiatri end as Average_Volume,lch.diengiai as Outlet_Type,"+
					" vt.vitri as Outlet_Location,hch.hang as Outlet_Class,kh_tuyen.tanso as Frequency"+
					" from khachhang kh"+
					" left join quanhuyen qh on kh.quanhuyen_fk = qh.pk_seq"+
					" left join loaicuahang lch on lch.pk_seq = kh.lch_fk"+
					" left join vitricuahang vt on vt.pk_seq = kh.vtch_fk"+
					" left join hangcuahang hch on hch.pk_seq = kh.hch_fk"+
					" left join KHACHHANG_TUYENBH kh_tuyen on kh_tuyen.khachhang_fk = kh.pk_seq"+
					" left join (select khachhang_fk,cast(sum(tonggiatri)/3 as int) as tonggiatri from donhang where ngaynhap >'2011-08-01' and ngaynhap < '2011-12-15' group by khachhang_fk) as ds"+
					" on ds.khachhang_fk = kh.pk_seq"+
					" left join tuyenbanhang tbh on tbh.pk_seq = kh_tuyen.tbh_fk "+ st +
					" order by tbh.ngaylamviec desc";


		System.out.println("Lay Du Lieu :"+sql);
		//this.danhsach = db.get(sql);
		
	}


	
	public void settuyenId(String tuyenId) {
		
		this.tuyenId = tuyenId;
	}


	
	public String gettuyenId() {
		
		return this.tuyenId;
	}


	
	public ResultSet getTuyen() {
		
		return this.tuyen;
	}


	
	public ResultSet getdanhsach() {
		
		return this.danhsach;
	}
	public void setuserId(String userId) {
	
		this.userId = userId;
	}

	public String getuserId() {
		return this.userId;
	}


	@Override
	public void DBclose() {
		// TODO Auto-generated method stub
		try {

			if(this.danhsach != null)
				this.danhsach.close();
			if(this.ddkd != null)
				this.ddkd.close();
			if(this.npp != null)
				this.npp.close();
			if(this.tuyen != null)
				this.tuyen.close();
			if(this.db != null)
				this.db.shutDown();
			
				
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

}
