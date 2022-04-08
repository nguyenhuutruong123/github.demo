package geso.dms.distributor.beans.donhangchoxuly.imp;

import java.io.Serializable;

import geso.dms.center.util.Phan_Trang;
import geso.dms.distributor.beans.donhangchoxuly.IDonHangChoXuLyList;
import geso.dms.distributor.db.sql.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DonHangChoXuLyList extends Phan_Trang implements IDonHangChoXuLyList, Serializable
{
	private static final long serialVersionUID = -9217977546733610214L;

	String userId; //nppId
	String tungay;
	String denngay;
	String trangthai;
	String sohoadon;
	String khachhang;
	String tumuc;
	String denmuc;
	String msg;

	String nppId;
	String nppTen;
	String sitecode;

	List<IDonHangChoXuLyList> dhlist;

	ResultSet daidienkd;
	String ddkdId;

	ResultSet dhRs;

	dbutils db;

	int currentPages;
	int[] listPages;

	//Constructor
	public DonHangChoXuLyList(String[] param)
	{
		this.tungay = param[0];
		this.denngay = param[1];
		this.trangthai = param[2];
		this.ddkdId = param[3];
		this.msg = "";

		currentPages = 1;
		listPages = new int[]{1, 2 , 3 , 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
		this.id="";
		this.db = new dbutils();
	}

	public DonHangChoXuLyList()
	{
		this.tungay = "";
		this.denngay = "";
		this.trangthai = "";
		this.ddkdId = "";
		this.sohoadon = "";
		this.khachhang = "";
		this.tumuc = "";
		this.denmuc = "";
		this.msg = "";

		currentPages = 1;
		listPages = new int[]{1, 2 , 3 , 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
		this.id="";
		this.db = new dbutils();
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

	public void createDdkd()
	{
		if(this.db == null)
			db = new dbutils();
		this.getNppInfo();
		this.daidienkd = db.get("select ten as ddkdTen, pk_seq as ddkdId from daidienkinhdoanh where npp_fk = '" + this.nppId +"'");
	}

	private void getNppInfo()
	{
		geso.dms.distributor.util.Utility util = new geso.dms.distributor.util.Utility();
		this.nppId=util.getIdNhapp(this.userId,this.db);
		this.nppTen=util.getTenNhaPP();
		this.sitecode=util.getSitecode();
	}

	public void createDhBeanList(String query)
	{
		this.dhRs = super.createSplittingData(super.getItems(), super.getSplittings(), "ngaynhap desc", query); //db.get(query);
	}

	public void init(String search) 
	{
		//db = new dbutils();
		this.getNppInfo();
		String query;	
		if (search.length() == 0)
		{
			query = " select a.DonHang_FK,a.pk_seq as dhId, a.ngaynhap, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua, d.ten as khTen, d.pk_seq as khId, \n"+
					"        e.pk_seq as ddkdId, e.ten as ddkdTen, f.ten as nppTen, a.tonggiatri, a.IsPDA,a.ddkdtao_fk, a.ddkdTao, \n"+
					"        (select count(*) from donhangchoxuly_sanpham where donhangchoxuly_fk = a.pk_seq AND conLai > 0) isChuyen \n"+
			        " from DonHangChoXuLy a left join nhanvien b on a.nguoitao = b.pk_seq \n"+
			        "      left join nhanvien c on a.nguoisua = c.pk_seq \n"+
			        "      inner join khachhang d on a.khachhang_fk = d.pk_seq \n"+
			        "      inner join daidienkinhdoanh e on a.ddkd_fk = e.pk_seq \n"+
			        "      inner join nhaphanphoi f on a.npp_fk = f.pk_seq \n"+
			        " where a.npp_fk = '" + this.nppId + "'  ";
		}
		else
		{
			query = search;
		}
		this.createDhBeanList(query); 
		this.createDdkd();
	}

	public void DBclose() 
	{		
		try 
		{
			if(!(this.daidienkd == null))
				this.daidienkd.close();
			if(dhRs!=null){
				dhRs.close();
			}
			if(db != null)
				this.db.shutDown();

		} 
		catch(Exception e) {}
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
		ResultSet rs = db.get("select count(*) as skh from khachhang");
		try 
		{
			rs.next();
			int count = Integer.parseInt(rs.getString("skh"));
			rs.close();
			return count;
		}
		catch(Exception e) {}

		return 0;
	}

	public String getSohoadon() 
	{
		return this.sohoadon;
	}

	public void setSohoadon(String sohoadon) 
	{
		this.sohoadon = sohoadon;
	}

	public String getKhachhang() 
	{
		return this.khachhang;
	}

	public void setKhachhang(String khachhang)
	{
		this.khachhang = khachhang;
	}

	public ResultSet getDonhangRs() 
	{
		return this.dhRs;
	}

	public void setDonhangRs(ResultSet dhRs) 
	{
		this.dhRs = dhRs;
	}

	public String getMsg() 
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}

	@Override
	public String getTumuc() {

		return this.tumuc;
	}

	@Override
	public void setTumuc(String tumuc) {

		this.tumuc = tumuc;
	}

	@Override
	public String getDenmuc() {

		return this.denmuc;
	}

	@Override
	public void setDenmuc(String denmuc) {
		this.denmuc = denmuc;
	}

	String id;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}


}

