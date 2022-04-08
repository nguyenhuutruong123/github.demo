package geso.dms.center.beans.donhangip.imp;


import geso.dms.center.beans.donhangip.IDonhangIP;
import geso.dms.center.beans.donhangip.IDonhangListIP;
import geso.dms.center.util.Phan_Trang;
import geso.dms.distributor.db.sql.*;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DonhangListIP extends Phan_Trang implements IDonhangListIP, Serializable
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
	
	List<IDonhangIP> dhlist;
	
	ResultSet daidienkd;
	String ddkdId;
	
	ResultSet dhRs;
	
	dbutils db;
	
	int currentPages;
	int[] listPages;
		
	//Constructor
	public DonhangListIP(String[] param)
	{
		this.tungay = param[0];
		this.denngay = param[1];
		this.trangthai = param[2];
		this.ddkdId = param[3];
		this.msg = "";
		
		currentPages = 1;
		listPages = new int[]{1, 2 , 3 , 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
		
		this.db = new dbutils();
	}
	
	public DonhangListIP()
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
		this.daidienkd = db.get("select pk_seq as ddkdId, ten as ddkdTen from daidienkinhdoanh  where trangthai = '1' and pk_seq in (select ddkd_fk from DDKD_GSBH where GSBH_FK in (select PK_SEQ from GIAMSATBANHANG where DVKD_FK=100069 ))   order by ten ASC");
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
			query = "select a.pk_seq as dhId, a.ngaynhap, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua, d.ten as khTen, d.pk_seq as khId, e.pk_seq as ddkdId, e.ten as ddkdTen, a.tonggiatri, a.IsPDA,a.ddkdtao_fk, a.ddkdTao,'' as qtbh ";
			query = query + " from donhangip a left join nhanvien b on a.nguoitao = b.pk_seq left join nhanvien c on a.nguoisua = c.pk_seq inner join khachhang d on a.khachhang_fk = d.pk_seq inner join daidienkinhdoanh e on a.ddkd_fk = e.pk_seq";
			query = query + " where  a.TrangThai !=7 ";
		}
		else
		{
			query = search;
		}
		System.out.println("don hang AAAAAAAAAAA "+query);
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
		// TODO Auto-generated method stub
		return this.tumuc;
	}

	@Override
	public void setTumuc(String tumuc) {
		// TODO Auto-generated method stub
		this.tumuc = tumuc;
	}

	@Override
	public String getDenmuc() {
		// TODO Auto-generated method stub
		return this.denmuc;
	}

	@Override
	public void setDenmuc(String denmuc) {
		// TODO Auto-generated method stub
		this.denmuc = denmuc;
	}

			
}

