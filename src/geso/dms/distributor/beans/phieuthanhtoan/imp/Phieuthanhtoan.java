package geso.dms.distributor.beans.phieuthanhtoan.imp;

import geso.dms.distributor.beans.phieuthanhtoan.IPhieuthanhtoan;
import geso.dms.distributor.db.sql.dbutils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

public class Phieuthanhtoan implements IPhieuthanhtoan
{
	private static final long serialVersionUID = -9217977546733610214L;
    private String msg;
    private String tungay;
    private String denngay;
    private String nppId;
    private String ddkdId;
    private String userId;
    private String nppTen;
    private String tuyen;
    private String sotien;
    public ResultSet tuyens;
    private String hinhthuc;
    private String id;
    private String diengiai;
    private String nvgn;
    ResultSet nvgns;
    String[] dhids;
    String[] thanhtoan;
    public ResultSet dshd;
    private String ngaythanhtoan;
    ResultSet ddkd;
	private dbutils db;
    public Phieuthanhtoan()
    {
    	this.msg = "";
    	this.tungay ="";
    	this.denngay = "";
    	this.nppId = "";
    	this.nppTen ="";
    	this.userId ="";
    	this.sotien ="0";
    	this.ddkdId="";
    	this.ngaythanhtoan="";
    	this.hinhthuc ="0";
    	this.id ="";
    	this.diengiai ="";
    	this.nvgn ="";
    	this.ddkd = null;
    	db = new dbutils();
    	
    }
    public Phieuthanhtoan(String id)
    {
    	this.msg = "";
    	
    	this.id = id;
    	this.nvgn ="";
    	db = new dbutils();
    	String sql ="select * from phieuthanhtoan where pk_seq ='"+ id +"'";
    	ResultSet rs = db.get(sql);
    	try {
			rs.next();
			this.tungay ="";
	    	this.denngay = "";
	    	this.nppId = "";
	    	this.nppTen ="";
	    	this.userId ="";
	    	this.sotien =rs.getString("sotien");
	    	this.ddkdId=rs.getString("nvgn_fk");;
	    	this.ngaythanhtoan=rs.getString("ngaythanhtoan");
	    	this.hinhthuc =rs.getString("hinhthuc");;
	    	this.diengiai =rs.getString("diengiai");
	    	
			
		} catch(Exception e) {
			
			e.printStackTrace();
		}
    	
    }
	public String getUserId() {
		
		return userId;
	}
	
	public void setUserId(String userId) {
		
		this.userId = userId;
	}
	
	public String getMsg() {
		
		return this.msg;
	}
	
	public void setMsg(String msg) {
		
		this.msg = msg;
	}
	
	public String getNppId() {
		
		return this.nppId;
	}
	
	public void setNppId(String nppId) {
		
		this.nppId = nppId;
	}

	public String getNppTen() {
		
		return this.nppTen;
	}
	
	public void setNppTen(String nppTen) {
		
		this.nppTen = nppTen;
	}
	
	public String getTungay() {
		
		return this.tungay;
	}
	
	public void setTungay(String tungay) {
		
		this.tungay = tungay;
	}
	
	public String getDenngay() {
		
		return this.denngay;
	}
	
	public void setDenngay(String denngay) {
		
		this.denngay = denngay;
	}
	
	public void setddkdId(String ddkdId) {
		
		this.ddkdId = ddkdId;
	}
	
	public String getddkdId() {
		
		return this.ddkdId;
	}
	
	public void setSotien(String sotien) {
		
       this.sotien = sotien;
	}
	
	public String getSotien() {
		
		return this.sotien;
	}
	
	public void setDskh(ResultSet dskh) {
		
		
	}
	
	public ResultSet getDskh() {
		
		return null;
	}
	
	public void init() {
		getNppInfo();
    	CreateDdkd();
		String sql="";
		String st ="";
		if(this.id.length()==0)
		{
				//if(this.ddkdId.length()>0)
				//st = st + " and ddkd_fk ='"+ this.ddkdId +"' ";
				if(this.tungay.length()>0)
				{
					st = st + " and a.ngaynhap >= '"+ this.tungay +"' ";
				}
				if(this.denngay.length()>0)
					st = st + " and a.ngaynhap <='"+ this.denngay +"' ";
					
				if(this.ddkdId.length()>0)
				{
					
				 sql = "select b.pk_seq as khId,a.pk_seq as dhId,b.ten,a.ngaynhap,a.tonggiatri,a.dathanhtoan, " +
				 		"a.tonggiatri-a.dathanhtoan as conlai " +
				 		" from donhang a,khachhang b ";
			      sql = sql +" where a.pk_seq in (select distinct donhang_fk from phieuxuatkho a,phieuxuatkho_donhang b " +
			      		"where a.pk_seq = b.pxk_fk and a.nvgn_fk ='"+ this.ddkdId +"') " +
			      		" and a.khachhang_fk = b.pk_seq and a.tonggiatri > a.dathanhtoan and  a.npp_fk ='"+ this.nppId +"'" + st;
					
			           System.out.println(sql);
				}
				else
				{
					sql ="select '' as khId,'' as dhId, '' as ten,'' as ngaynhap,'' as tonggiatri,'' as dathanhtoan,'' as conlai ";
				}
		}
		else
		{   
			sql ="select b.pk_seq as khId,a.pk_seq as dhId,b.ten,a.ngaynhap,a.tonggiatri,a.dathanhtoan - c.dathanhtoan as dathanhtoan," +
				 "c.dathanhtoan as thanhtoan,a.tonggiatri-a.dathanhtoan as conlai from donhang a,khachhang b,phieuthanhtoan_donhang c   " +
				 "where a.pk_seq = c.donhang_fk and a.khachhang_fk = b.pk_seq and " +
				 "a.pk_seq in (select donhang_fk from phieuthanhtoan_donhang where phieuthanhtoan_fk ='"+ this.id +"')";
		}	
		System.out.println("chuoi:"+sql);
         this.dshd = db.get(sql);
			//CreateDdkd();
		
	}
	private void getNppInfo()
	{	
		/*String sql="select a.pk_seq, a.ten, a.sitecode from nhaphanphoi a, nhanvien b where b.dangnhap = a.ma and b.pk_seq ='" + this.userId + "'";
	    System.out.println("sql"+sql);
		ResultSet rs = this.db.get(sql);
		try{
			if (!(rs == null)){
				rs.next();
				this.nppId = rs.getString("pk_seq");
				System.out.println("npp:"+this.nppId);
				this.nppTen = rs.getString("ten");
				
			}else
			{
				this.nppId = "";
				this.nppTen = "";
						
			}
			
		}catch(Exception e){}	
		*/
		//Phien ban moi
		geso.dms.distributor.util.Utility util=new geso.dms.distributor.util.Utility();
		this.nppId=util.getIdNhapp(this.userId);
		this.nppTen=util.getTenNhaPP();
		//this.dangnhap = util.getDangNhap();
		//this.sitecode=util.getSitecode();
	}
	
	public void setTuyen(String tuyen) {
		
		this.tuyen = tuyen;
	}
	
	public String getTuyen() {
		
		return this.tuyen;
	}
	
	public void setDdkd(ResultSet ddkd) {
		
		this.ddkd = ddkd;
	}
	
	public ResultSet getDdkd() {
		
		return this.ddkd;
	}
	
	public void setNgaythanhtoan(String ngaythanhtoan) {
		
		this.ngaythanhtoan = ngaythanhtoan;
	}
	
	public String getNgaythanhtoan() {
		
		return this.ngaythanhtoan;
	}
     public void CreateDdkd()
     {   
    	 String query;
    /*	 query = "select * from nhanviengiaonhan where pk_seq ='"+ this.nppId +"'";
    	 System.out.println(query);
    	 String sql;
    	 this.nvgns = db.get(query);
    	 if(this.nvgn.length() >0){
    		 sql ="select * from nhanviengiaonhan where pk_seq in (select nvgn_fk from phieuxuatkho where pk_seq in (select pxk_fk from phieuxuatkho_donhang where donhang_fk in (select pk_seq from donhang where ddkd_fk ='"+ this.ddkdId +"' and npp_fk ='"+ this.nppId +"')))"; 		 	
         } 
    	 else
    		 sql = "select * from daidienkinhdoanh where npp_fk ='"+ this.nppId +"'";
    	 System.out.println("dai dien kinh doanh:"+sql);
    	 */
         String  sql ="select distinct * from nhanviengiaonhan where pk_seq in (select nvgn_fk from phieuxuatkho where npp_fk='"+ this.nppId +"' )";
    	 this.ddkd = db.get(sql);
         sql ="select * from tuyenbanhang";
    	 this.tuyens = db.get(sql);
    	 
     }
	
	public void setTuyens(ResultSet tuyens) {
		
		this.tuyens = tuyens;
	}
	
	public ResultSet getTuyens() {
		
		return this.tuyens;
	}
	
	public void setDshd(ResultSet dshd) {
		this.dshd = dshd; 
		 		
	}
	
	public ResultSet getDshd() {
		
		return this.dshd;
	}
	
	public void setHinhthuc(String hinhthuc) {
		
		this.hinhthuc = hinhthuc;		
	}
	
	public String getHinhthuc() {
		
		return this.hinhthuc;
	}
	
	public void setId(String id) {
		
		this.id = id;
	}
	
	public String getId() {
		
		return this.id;
	}
	
	public boolean save() {
		//db = new dbutils();
		String sql =" insert into phieuthanhtoan values('"+ this.ngaythanhtoan +"','"+ this.diengiai +"','0','"+ this.sotien +"','"+ this.nppId +"','"+ getDateTime()+"','"+ getDateTime() +"','"+ this.userId +"','"+ this.userId +"','"+ this.hinhthuc +"','"+ this.ddkdId +"')";
		System.out.println(sql);
		if(!db.update(sql))
		{
		   geso.dms.center.util.Utility.rollback_throw_exception(db);
		   this.msg = sql;
		   return false;
		}
		
		String query = "select IDENT_CURRENT('phieuthanhtoan') as ptt";
		String ptt="";
		ResultSet rsDh = db.get(query);
	   	try {
	   		 rsDh.next();
			 ptt = rsDh.getString("ptt");
		} catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println("so hien tai:"+ptt);
		int h = this.dhids.length;
		for(int i =0;i<h;i++)
		{   float k = Float.parseFloat(this.thanhtoan[i]);
			if(k>0)
			{
				
				System.out.println("so duoc luu:"+thanhtoan[i]);
				sql ="insert into phieuthanhtoan_donhang values('"+ ptt +"','"+ this.dhids[i] +"','"+ this.thanhtoan[i] +"',0)";
				System.out.println(sql);
				if(!db.update(sql))
				{
				   geso.dms.center.util.Utility.rollback_throw_exception(db);
				   this.msg = sql;
				   return false;
				   		   
				}
			  
				sql ="update donhang set dathanhtoan = dathanhtoan + '"+ this.thanhtoan[i]+"' where pk_seq ='"+ this.dhids[i] +"'";
				System.out.println(sql);
				if(!db.update(sql))
				{
				   geso.dms.center.util.Utility.rollback_throw_exception(db);
				   this.msg = sql;
				   return false;			   
				}
				
			
			}
			
		}
		return true;
	}
	
	public boolean Update()
	{
	  String sql =" update phieuthanhtoan set ngaythanhtoan = '"+ this.ngaythanhtoan +"',diengiai = '"+ this.diengiai +"',sotien = '"+ this.sotien +"',ngaysua ='"+ getDateTime() +"',nguoisua ='"+ this.userId +"',hinhthuc = '"+ this.hinhthuc +"',nvgn_fk = '"+ this.ddkdId +"' where pk_seq ='"+ id +"'";
	  System.out.println(sql);
	  if(!db.update(sql))
	  {
	   geso.dms.center.util.Utility.rollback_throw_exception(db);
	   this.msg = sql;
	   return false;
	   }
	  sql ="select * from phieuthanhtoan_donhang where phieuthanhtoan_fk ='"+ id +"'";
	  ResultSet rs = db.get(sql);
	  try {
		while(rs.next())
		  {
			  String sotien = rs.getString("dathanhtoan");
			  String donhang =rs.getString("donhang_fk");
			  sql ="update donhang set dathanhtoan = dathanhtoan -'"+ sotien +"' where pk_seq ='"+ donhang +"'";
			  db.update(sql);
		  }
    	} catch(Exception e) {
		e.printStackTrace();
	   }
       sql ="delete from phieuthanhtoan_donhang where phieuthanhtoan_fk ='"+ id +"'";
       db.update(sql);
       int h = this.dhids.length;
		for(int i =0;i<h;i++)
		{   float k = Float.parseFloat(this.thanhtoan[i]);
			if(k>0)
			{
				
				System.out.println("so duoc luu:"+thanhtoan[i]);
				sql ="insert into phieuthanhtoan_donhang values('"+ id +"','"+ this.dhids[i] +"','"+ this.thanhtoan[i] +"',0)";
				System.out.println(sql);
				if(!db.update(sql))
				{
				   geso.dms.center.util.Utility.rollback_throw_exception(db);
				   this.msg = sql;
				   return false;
				   		   
				}
			  
				sql ="update donhang set dathanhtoan = dathanhtoan + '"+ this.thanhtoan[i]+"' where pk_seq ='"+ this.dhids[i] +"'";
				System.out.println(sql);
				if(!db.update(sql))
				{
				   geso.dms.center.util.Utility.rollback_throw_exception(db);
				   this.msg = sql;
				   return false;			   
				}
			}
		}
		return true;
	}
	
	public void setDiengiai(String diengiai) {
		
		this.diengiai = diengiai;
	}
	
	public String getDiengiai() {
		
		return this.diengiai;
	}

	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	public void setThanhtoan(String[] thanhtoan) {
	
		this.thanhtoan = thanhtoan;
	}

	public String[] getthanhtoan() {
	
		return this.thanhtoan;
	}

	public void setdhIds(String[] dhids) {
		
		this.dhids = dhids;
	}
	
	public String[] getdhIds() {
		
		return this.dhids;
	}
	
	public void setNvgn(String nvgn) {
		
	   this.nvgn = nvgn;	
	}
	
	public String getNvgn() {
		
		return this.nvgn;
	}
	
	public void setNvgns(ResultSet nvgns) {
		
		this.nvgns = nvgns;
	}
	
	public ResultSet getNvgns() {
		
		return nvgns;
	}
	
}

