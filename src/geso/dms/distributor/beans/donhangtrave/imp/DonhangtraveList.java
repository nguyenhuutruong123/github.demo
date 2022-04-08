package geso.dms.distributor.beans.donhangtrave.imp;

import geso.dms.distributor.beans.donhangtrave.IDonhangtraveList;
import geso.dms.distributor.beans.donhangtrave.IDonhangtrave;
import geso.dms.distributor.db.sql.*;
import geso.dms.distributor.util.Utility;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Sets.SetView;

public class DonhangtraveList implements IDonhangtraveList
{
	private static final long serialVersionUID = -9217977546733610214L;

	String userId; //nppId
	String tungay;
	String denngay;
	String trangthai;
	
	String nppId;
	String nppTen;
	String sitecode;
	String msg;
	
	ResultSet npp;
	ResultSet daidienkd;
	String ddkdId;
	
	ResultSet dhtvlist;
	
	dbutils db;
	String view = "";
	ResultSet trangthaiNguyenDonRs;
	String trangthaiNguyenDon = "";
	
	//Constructor
	public DonhangtraveList(String[] param)
	{
		this.tungay = param[0];
		this.denngay = param[1];
		this.trangthai = param[2];
		this.ddkdId = param[3];
		this.nppId = param[4];
		this.msg = param[5];
		db = new dbutils();
	}
	
	public DonhangtraveList()
	{
		this.tungay = "";
		this.denngay = "";
		this.trangthai = "";
		this.ddkdId = "";
		this.nppId="";
		this.msg="";
		//init("");
		db = new dbutils();
	}
	
	public String getTrangthaiNguyenDon() {
		return trangthaiNguyenDon;
	}
	public void setTrangthaiNguyenDon(String trangthaiNguyenDon) {
		this.trangthaiNguyenDon = trangthaiNguyenDon;
	}
	public ResultSet getTrangthaiNguyenDonRs() {
		String query = "\n select 0 t, N'Chưa chốt' tt " +
		"\n union all select 2 t, N'Đã huỷ' tt " +
		"\n union all select 3 t, N'Đã duyệt' tt ";
		//System.out.println("getTrangthaiNguyenDonRs: "+query);
		return db.get(query);
	}
	public String getView() {
		return view;
	}
	
	public void setView(String view) {
		this.view = view;
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
	
	public ResultSet getDhtvList() 
	{	
		return this.dhtvlist;
	}

	public void setDhtvList(ResultSet dhtvlist) 
	{
		this.dhtvlist = dhtvlist;		
	}
	
	public ResultSet getNpp() 
	{	
		return this.npp;
	}

	public void setNpp(ResultSet npp) 
	{
		this.npp = npp;		
	}
	
	public void createNpp()
	{
		this.npp = db.get("select ten as nppTen, pk_seq as nppId from nhaphanphoi ");
	}
	
	
	public void createDdkd()
	{
		if (this.nppId.length() > 0)
		{
			this.daidienkd = db.get("select ten as ddkdTen, pk_seq as ddkdId from daidienkinhdoanh  where npp_fk = '" + nppId + "'");
		}
		else
			this.daidienkd = db.get("select ten as ddkdTen, pk_seq as ddkdId from daidienkinhdoanh ");
	}
		
	public void createDhtvBeanList(String query)
	{
		this.dhtvlist =  db.get(query);
	}
	
	public void init(String search) 
	{
		db = new dbutils();
		getNppInfo();
		String query = "";	
		
		if (search.length() == 0)
		{
			query = "\n select isnull(a.ghichutt,'') as lydo,a.pk_seq as dhtvId, a.ngaynhap, a.trangthai, CONVERT(char(10),a.ngaygio,121) + ' ' + CONVERT(CHAR( 5),a.ngaygio,114) as ngaytao, CONVERT(char(10),a.ngaygiosua,121) + ' ' + CONVERT(CHAR( 5),a.ngaygiosua,114) as ngaysua, b.ten as nguoitao, " +
			"\n c.ten as nguoisua, d.ten as khTen,d.smartid, d.pk_seq as khId, e.pk_seq as ddkdId, e.ten as ddkdTen, " +
			"\n f.ten as nppTen, a.VAT, " +
			"\n case when DONHANG_FK Is not null then (select SUM(soluong) from DONHANG_SANPHAM where DONHANG_FK = a.DONHANG_FK) " + 
			"\n else (select SUM(soluong) from DONHANGTRAVE_SANPHAM where DONHANGTRAVE_FK = a.pk_seq) end AS TongLuong, " +
			"\n case when DONHANG_FK Is not null then (select SUM(TONGGIATRI) from DONHANG where DONHANG_FK = a.DONHANG_FK) " +
			"\n else (select SUM(SOLUONG*GIAMUA) from DONHANGTRAVE_SANPHAM where DONHANGTRAVE_FK = A.pk_seq) end AS TongTien " +
			"\n from donhangtrave a " +
			"\n inner join nhanvien b on a.nguoitao = b.pk_seq " +
			"\n inner join nhanvien c on a.nguoisua = c.pk_seq " +
			"\n inner join khachhang d on a.khachhang_fk = d.pk_seq " +
			"\n inner join daidienkinhdoanh e on a.ddkd_fk = e.pk_seq " +
			"\n inner join nhaphanphoi f on a.npp_fk = f.pk_seq " +
			"\n where 1 = 1 ";
		}
		else
		{
			query = search;
			System.out.println("Query la: \n" +query);
		}
		if(this.nppId != null && this.nppId.trim().length() > 0)
			query += " and a.npp_fk =  "+ this.nppId;
		
		//Tách Menu trả nguyên đơn và lẻ
		query += " and isnull(donhang_fk,0) = 0 ";
		System.out.println("Init List: " + query);
		this.createNpp();
		this.createDhtvBeanList(query);  
		this.createDdkd();
	}
	
	public void init_TraNguyenDonList(String search) 
	{
		try {
			//this.getNppInfo();
			String query = "";

			if (search.length() == 0)
			{
				query = "\n select isnull(a.ghichu,'') as lydo,a.trangthai, a.pk_seq, a.donhang_fk, a.ngaynhap, kh.smartid makh,  " +
				"\n case when a.trangthai = 0 then N'Chưa chốt' " +
				"\n when a.trangthai = 2 then N'Đã huỷ' " +
				"\n when a.trangthai = 3 then N'Đã duyệt' " +
				"\n else N'Không xác định' end trangthaihienthi, isnull(CONVERT(char(16),a.ngaygio,121),'') as ngaytao, isnull(CONVERT(char(16),a.ngaygiosua,121),'') as ngaysua,nv1.ten nguoitao, nv2.ten nguoisua " +
				"\n from donhangtrave a " +
				"\n inner join khachhang kh on kh.pk_seq = a.khachhang_fk " +
				"\n inner join nhanvien nv1 on nv1.pk_seq = a.nguoitao " +
				"\n inner join nhanvien nv2 on nv2.pk_seq = a.nguoisua " +
				"\n where 1 = 1 and isnull(donhang_fk,0) != 0 ";
			}
			else
			{
				query = search;
			}

			Utility util = new Utility();
			String _nppId = util.getIdNhapp(this.userId);
			if (_nppId != null && _nppId.length() > 0) {
				query += "\n and a.npp_fk = "+_nppId;
				this.nppId = _nppId;
			}	
			query +="order by a.trangthai asc,a.pk_seq desc";
			System.out.println("Query Init trả nguyên đơn List: "+query);
			
			this.createNpp();
			this.createDhtvBeanList(query);  
			this.createDdkd();

			if (checkNull("view")) {
				query = "select ten from nhanvien where pk_seq = " + userId;
				ResultSet rs = db.get(query);
				while (rs.next()) {
					setNppTen(rs.getString("ten"));
				}
				rs.close();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void DBclose()
	{
		try 
		{
			if (!(this.daidienkd == null))
				this.daidienkd.close();
			if (this.dhtvlist==null){
				this.dhtvlist.close();
			}
			if (this.db != null)
				this.db.shutDown();
		} 
		catch(Exception e) {}
	}

	@Override
	public String getMsg() 
	{		
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;		
	}
	
	public boolean checkNull(String input) {
		if (input != null && input.trim().length() > 0) 
			return true;
		else
			return false;
	}			
	
	private void getNppInfo()
	{
		geso.dms.distributor.util.Utility util = new geso.dms.distributor.util.Utility();
		this.nppId = util.getIdNhapp(this.userId, this.db);
		this.nppTen = util.getTenNhaPP();
		this.sitecode = util.getSitecode();
	}
}

