package geso.dms.center.beans.hdnhaphang.imp;

import geso.dms.center.beans.hdnhaphang.IHDnhaphangList;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.IPhanTrang;
import geso.dms.center.util.PhanTrang;
import geso.dms.center.util.Phan_Trang;
import geso.dms.distributor.util.Utility;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class HDnhaphangList  extends Phan_Trang implements IHDnhaphangList, Serializable
{
	private static final long serialVersionUID = -9217977546733610214L;
	private String userId;
	private String nppTen;
	private String nppId;
	private String dangnhap;
	private String sku;
	private String tungay;
	private String query;
	private String denngay;
	private String trangthai;
	private ResultSet nhaphangList;
	private ResultSet nppList;
	private String maList;
	private dbutils db;
	private String IdDonHang;
	String Msg ;
	
	int currentPages;
	int[] listPages;
	
	public HDnhaphangList(String[] param)
	{
		this.db = new dbutils();
		this.sku = param[0];
		this.tungay = param[1];
		this.denngay = param[2];
		this.trangthai = param[3];
		this.userId = "";
		this.nppTen = "";
		this.nppId = "";
		this.query = "";
		init();
		
		currentPages = 1;
		listPages = new int[]{1, 2 , 3 , 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
	}
	
	public HDnhaphangList()
	{
		this.db = new dbutils();
		this.userId = "";
		this.nppTen = "";
		this.nppId = "";
		this.sku = "";
		this.tungay = "";
		this.denngay = "";
		this.trangthai = "";
		this.Msg ="";
		this.IdDonHang="";
		this.query = "";
		currentPages = 1;
		listPages = new int[]{1, 2 , 3 , 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
	}
	
	public ResultSet getNhaphangList()
	{
		return this.nhaphangList;
	}
	
	public void setNhaphangList(ResultSet nhaphangList)
	{
		this.nhaphangList = nhaphangList;
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
	
	public String getSKU()
	{
		return this.sku;
	}
	
	public void setSKU(String sku)
	{
		this.sku = sku;
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
	
	public String getTrangthai()
	{
		return this.trangthai;
	}
	
	public void setTrangthai(String trangthai)
	{
		this.trangthai = trangthai;
	}

	public String getMalist()
	{
		return this.maList;
	}
	
	public void setMalist(String malist)
	{
		this.maList = malist;
	}

	public String getDangnhap()
	{
		return this.dangnhap;
	}
	
	public void setDangnhap(String dangnhap)
	{
		this.dangnhap = dangnhap;
	}

	public void init(){		
		/*Utility util=new Utility();
		this.nppId = util.getIdNhapp(userId);
		query = "select a.pk_seq, a.ten, a.ma as dangnhap from nhaphanphoi a, nhanvien b where a.ma = b.dangnhap and b.pk_seq = '" + this.nppId + "'";
		System.out.println("Init : "+query);
		ResultSet rs = this.db.get(query);
		try{
			if (rs != null){
				rs.next();
				this.nppId = rs.getString("pk_seq");
				this.nppTen = rs.getString("ten");
				this.dangnhap = rs.getString("dangnhap");
			}else{
				this.nppId = "";
				this.nppTen = "";
				this.dangnhap = "";
			}
		}catch(Exception ex){}
			
			
			//this.nppId = util.getIdNhapp(userId);
			
			this.nppTen = util.getTenNhaPP();
	
			this.dangnhap = util.getDangNhap();*/
			
		if (this.query.length() == 0)
		{				
			//query = "select distinct a.ngaychungtu,a.dathang_fk, a.ngaynhan, a.pk_seq as chungtu,e.donvikinhdoanh,npp.ten as tennpp,  f.ten as kbh ,a.sotienavat, b.ten as nguoitao, c.ten as nguoisua, a.trangthai from hdnhaphang a, nhanvien b, nhanvien c, hdnhaphang_sp d, nhaphanphoi npp, donvikinhdoanh e, kenhbanhang f where a.nguoitao = b.pk_seq and a.nguoisua = c.pk_seq and a.pk_seq = d.nhaphang_fk and a.dvkd_fk = e.pk_seq and a.kbh_fk = f.pk_seq ";
			query = "select distinct hd.pk_seq as chungtu, hd.ngaychungtu, hd.DATHANG_FK, hd.NGAYNHAN, dvkd.DONVIKINHDOANH, "+
				    "npp.TEN, kbh.TEN as kbh, hd.SOTIENAVAT, nt.TEN as nguoitao, ns.TEN as nguoisua, hd.TRANGTHAI "+
				    "from HDNHAPHANG hd "+
				    "inner join NHANVIEN nt on nt.PK_SEQ = hd.NGUOITAO "+
				    "inner join NHANVIEN ns on ns.PK_SEQ = hd.NGUOISUA "+
				    "inner join HDNHAPHANG_SP hdsp on hdsp.NHAPHANG_FK = hd.PK_SEQ "+
				    "inner join NHAPHANPHOI npp on npp.PK_SEQ = hd.NPP_FK "+
				    "inner join DONVIKINHDOANH dvkd on dvkd.PK_SEQ = hd.DVKD_FK "+
				    "inner join KENHBANHANG kbh on kbh.PK_SEQ = hd.KBH_FK"; 
			System.out.println("HD nhap hang list : "+query);
		}
			
			this.nhaphangList = super.createSplittingData(super.getItems(), super.getSplittings(), " ten, trangthai, ngaychungtu, chungtu ", query);	
			createNppRs();
	}	

	public void DBclose(){
		try {
			if(this.nhaphangList != null)
				this.nhaphangList.close();
			
		} catch (Exception e) {			
		}
		if(!(this.db == null)){
			this.db.shutDown();
		}
	}

	public void setMsg(String Msg) {
	  this.Msg = Msg;
		
	}

   public String getMsg() {
		
		return this.Msg;
	}


	public void setDonHangId(String iddonhang) {
		
		this.IdDonHang=iddonhang;
	}
	
	
	public String getDonHangId() {
		
		return this.IdDonHang;
	}
	
	
/*	public String getNpp() {
		
		return this.nppId;
	}
	
	
	public void setNpp(String npp) {
		
		this.nppId = npp;
	}*/
	
	
	public ResultSet getNppRs() {
		
		return this.nppList;
	}
	
	
	public void setNppRs(ResultSet npplist) {
		
		this.nppList = npplist;
	}
	
	public void createNppRs()
	{
		db = new dbutils();
		String query = "select pk_seq, ten from nhaphanphoi";
		this.nppList = db.get(query);
	}	

	
	public int getCurrentPage() 
	{
		return this.currentPages;
	}

	public void setCurrentPage(int current) 
	{
		this.currentPages = current;
	}

	public int[] getListPages()
	{
		return this.listPages;
	}

	public void setListPages(int[] listPages) 
	{
		this.listPages = listPages;
	}

	public int getLastPage() 
	{
		ResultSet rs = db.get("select count(*) as hdnh from hdnhaphang");
		try 
		{
			rs.next();
			int count = Integer.parseInt(rs.getString("hdnh"));
			rs.close();
			return count;
		}
		catch (SQLException e) {}
		
		return 0;
	}

	
	public String getQuery() {
		
		return this.query;
	}

	@Override
	public void setQuery(String query) {
		
		this.query = query;
	}
	
	
}
