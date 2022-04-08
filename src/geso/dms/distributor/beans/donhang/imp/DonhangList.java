package geso.dms.distributor.beans.donhang.imp;

import java.io.Serializable;

import geso.dms.center.util.Phan_Trang;
import geso.dms.distributor.beans.donhang.IDonhangList;
import geso.dms.distributor.beans.donhang.IDonhang;
import geso.dms.distributor.db.sql.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DonhangList extends Phan_Trang implements IDonhangList, Serializable
{
	private static final long serialVersionUID = -9217977546733610214L;

	String userId; //nppId
	String tungay;
	String denngay;
	String trangthai;
	String sohoadon;
	String khachhang;
	String dongbodonhang;
	String sodonhangchuadongbo;
	
	String ttdh;
	String tumuc;
	String denmuc;
	String msg;
	String qtbh  ="";
	String nppId;
	String nppTen;
	String sitecode;
	
	List<IDonhang> dhlist;
	
	ResultSet daidienkd;
	String ddkdId;
	
	ResultSet dhRs;
	String ttin;
	
	dbutils db;
	
	int currentPages;
	int[] listPages;
		
	//Constructor
	public DonhangList(String[] param)
	{
		this.tungay = param[0];
		this.denngay = param[1];
		this.trangthai = param[2];
		this.ttdh = param[3];
		this.ddkdId = param[4];
		this.msg = "";
		this.ttin="";
		
		currentPages = 1;
		listPages = new int[]{1, 2 , 3 , 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
		
		this.db = new dbutils();
	}
	
	public DonhangList()
	{
		this.tungay = "";
		this.denngay = "";
		this.trangthai = "";
		this.sodonhangchuadongbo = "";
		this.dongbodonhang = "";
		this.ddkdId = "";
		this.sohoadon = "";
		this.khachhang = "";
		this.ttdh="";
		this.tumuc = "";
		this.denmuc = "";
		this.msg = "";
		this.ttin="";
		
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
		if (this.db == null)
			db = new dbutils();
		this.getNppInfo();
		this.daidienkd = db.get("select ten as ddkdTen, pk_seq as ddkdId from daidienkinhdoanh where npp_fk = '" + this.nppId +"'");
	}
	
	private void getNppInfo()
	{
		geso.dms.distributor.util.Utility util = new geso.dms.distributor.util.Utility();
		this.nppId = util.getIdNhapp(this.userId, this.db);
		this.nppTen = util.getTenNhaPP();
		this.sitecode = util.getSitecode();
	}
	
	public void createDhBeanList(String query)
	{
		this.dhRs = super.createSplittingData_New(db,super.getItems(), super.getSplittings(), "ngaynhap desc", query); //db.get(query);
	}
	
	
	public void init(String search) 
	{
		//db = new dbutils();
		this.getNppInfo();
		String query;	
		if (search.length() == 0)
		{
			query = "\n select  isnull(a.ngaychot,'')ngaychot ,isnull(a.donhangkhac,'')donhangkhac ,isnull(a.ngaygiaohang,'')ngaygiaohang ,isnull(d.coderoute,'')coderoute,d.smartid,isnull(a.ispda,0)ispda,a.pk_seq as dhId, a.ngaynhap,a.trangthaiin, a.trangthai, a.ngaytao, a.ngaysua, " +
					"\n b.ten as nguoitao, c.ten as nguoisua, d.ten as khTen, d.pk_seq as khId, e.pk_seq as ddkdId, " +
					"\n e.ten as ddkdTen,a.FlagModified as ttdh,a.FlagModified, f.ten as nppTen, " +
					"\n isnull(a.tonggiatri,0) as tonggiatri,a.ddkdtao_fk, a.ddkdTao " +
					"\n 	,( select isnull(quytrinhbanhang,0) from NHAPHANPHOI where pk_seq = "+this.nppId+") as qtbh " +
					"\n		, isnull(trahang.datrahang,0)datrahang,a.madonhang  " + 
					"\n from donhang a  with (updlock,readpast)  " +
					"\n left join nhanvien b on a.nguoitao = b.pk_seq " +
					"\n left join nhanvien c on a.nguoisua = c.pk_seq " +
					"\n inner join khachhang d on a.khachhang_fk = d.pk_seq " +
					"\n inner join daidienkinhdoanh e on a.ddkd_fk = e.pk_seq " +
					"\n inner join nhaphanphoi f on a.npp_fk = f.pk_seq " +
					"\n outer apply ( select count(pk_seq) datrahang from donhangtrave x where x.donhang_fk = a.pk_seq and x.trangthai = 3  ) trahang  " + 
					"\n where a.npp_fk = '" + this.nppId + "' and a.TrangThai !=7 and isnull(a.issam, 0) <> 1 ";
		}
		else
		{
			query = search;
		}
		System.out.println("Đơn hàng List: "+query);
		
		ResultSet rs  = db.get(" select isnull(quytrinhbanhang,0) quytrinhbanhang from NHAPHANPHOI where pk_seq = "+this.nppId+" ");
		if (rs != null)
		{
			try {
				if (rs.next())
				{
					this.qtbh = 	rs.getString("quytrinhbanhang");
					rs.close();
				}
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		 
		}
	    rs  = db.get("select count(*) as sl from donhang where npp_fk = "+this.nppId+" and isnull(isdongbo,0)=0 and ngaynhap>='2021-01-22' and trangthai=1");
 		if (rs != null)
		{
			try {
				if (rs.next())
				{
					this.sodonhangchuadongbo = 	rs.getString("sl");
					rs.close();
				}
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		 
		}
		try {
			this.createDhBeanList(query); 
			this.createDdkd();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getDongbodonhang() {
		return dongbodonhang;
	}

	public void setDongbodonhang(String dongbodonhang) {
		this.dongbodonhang = dongbodonhang;
	}

	public String getSodonhangchuadongbo() {
		return sodonhangchuadongbo;
	}

	public void setSodonhangchuadongbo(String sodonhangchuadongbo) {
		this.sodonhangchuadongbo = sodonhangchuadongbo;
	}

	public void DBclose() 
	{		
		try 
		{
			if (!(this.daidienkd == null))
				this.daidienkd.close();
			if (dhRs!=null){
				dhRs.close();
			}
			if (db != null)
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

	public String getTtdh() 
	{
		return this.ttdh;
	}

	public void setTtdh(String ttdh)
	{
		this.ttdh = ttdh;
	}

	
	public ResultSet getDonhangRs() 
	{
		return this.dhRs;
	}

	public void setDonhangRs(ResultSet dhRs) 
	{
		this.dhRs = dhRs;
	}
	
	public String getTrangthaiin() 
	{
		return this.ttin;
	}

	public void setTrangthaiin(String ttin) 
	{
		this.ttin = ttin;
	}

	public String getMsg() 
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}

	
	public String getTumuc() {
		
		return this.tumuc;
	}

	
	public void setTumuc(String tumuc) {
		
		this.tumuc = tumuc;
	}

	
	public String getDenmuc() {
		
		return this.denmuc;
	}

	
	public void setDenmuc(String denmuc) {
		
		this.denmuc = denmuc;
	}

	
	public String getqtbh() {
		
		return this.qtbh;
	}

	
	public void setqtbh(String qtbh) {
		
		this.qtbh = qtbh;
	}

			
}

