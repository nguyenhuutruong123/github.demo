package geso.dms.distributor.beans.donhangtrave.imp;

import geso.dms.center.db.sql.Idbutils;
import geso.dms.center.util.Utility;
import geso.dms.distributor.beans.donhangtrave.IDonhangtrave;
import geso.dms.distributor.beans.donhangtrave.ISanpham;
import geso.dms.distributor.beans.donhangtrave.imp.Sanpham;
import geso.dms.distributor.db.sql.*;
import geso.dms.distributor.util.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Hashtable;

import com.google.common.collect.Sets.SetView;

public class Donhangtrave implements IDonhangtrave 
{	
	private static final long serialVersionUID = -9217977546733610214L;

	String userId;
	String id; //ma don hang
	String ngaygiaodich;
	String daidienkd;
	String trangthai;
	String ghichu;



	String ngaytao;
	String nguoitao;
	String ngaysua;
	String nguoisua;
	String chietkhau;
	String tongchietkhau;
	String VAT;
	String msg;
	String lydo;
	ResultSet lydoRs;
	String nppId;
	String nppTen;
	String sitecode;

	ResultSet ddkdlist;
	String ddkdId;

	ResultSet nppList;
	ResultSet donhangList;
	ResultSet gsbhList;
	String gsbhId;
	String smartId;
	String khTen;
	ResultSet khlist;
	String khId;

	ResultSet kholist;
	String khoId;

	List<ISanpham> splist;

	String tongtientruocVAT;
	String tongtiensauVAT;

	Hashtable<String, Integer> spThieuList;

	//trakhuyen mai
	Hashtable<String, Float> scheme_tongtien = new Hashtable<String, Float>();
	Hashtable<String, Float> scheme_chietkhau = new Hashtable<String, Float>();
	List<ISanpham> scheme_sanpham = new ArrayList<ISanpham>();
	String isTraNguyenDon = "0"; //Tách Menu Trả nguyên đơn nên Set = 0
	final int TachTraNguyenDon = 1;
	String dhId = null, tungay = "", denngay = "", view = "";
	ResultSet ddkdRs, khachhangRs, donhangRs;	
	
	dbutils db = null;
	
	public Donhangtrave(String[] param)
	{
		this.id = param[0];
		this.khId = param[1];
		this.ngaygiaodich = param[2];
		this.nppTen = param[3];
		this.daidienkd = param[4];
		this.trangthai = param[5];
		this.ngaytao = param[6];
		this.nguoitao = param[7];
		this.ngaysua = param[8];
		this.nguoisua = param[9];	
		this.VAT = param[10];
		this.ddkdId = param[11];
		this.nppId = param[12];
		this.dhId = param[13];
		this.lydo="";
		this.gsbhId = "";
		this.chietkhau = "";
		this.tongchietkhau = "";
		this.khoId = "";
		this.ghichu = "";

		this.msg = "";
		this.spThieuList = new Hashtable<String, Integer>();

		db = new dbutils();

	}

	public Donhangtrave(String id)
	{
		this.id = id;
		this.khId = "";
		this.ngaygiaodich = "";
		this.nppTen = "";
		this.ghichu = "";
		this.daidienkd = "";
		this.trangthai = "";
		this.ngaytao = "";
		this.nguoitao = "";
		this.ngaysua = "";
		this.nguoisua = "";	
		this.VAT = "";
		this.ddkdId = "";
		this.gsbhId = "";
		this.nppId = "";
		this.chietkhau = "";
		this.tongchietkhau = "";
		this.tongtiensauVAT = "0";
		this.tongtientruocVAT ="0";
		this.khoId = "";
		this.msg = "";
		this.khTen = "";
		this.smartId = "";
		this.lydo="";
		this.spThieuList = new Hashtable<String, Integer>();

		this.dhId = "";
		db = new dbutils();	
	}
	public String getGhichu() {
		return ghichu;
	}

	public void setGhichu(String ghichu) {
		this.ghichu = ghichu;
	}
	public int getTachTraNguyenDon() {
		return TachTraNguyenDon;
	}
	
	public String getView() {
		return view;
	}
	public void setView(String view) {
		this.view = view;
	}
	public dbutils getDb() {
		return db;
	}
	public ResultSet getDdkdRs() {
		return ddkdRs;
	}
	public void setDdkdRs(ResultSet ddkdRs) {
		this.ddkdRs = ddkdRs;
	}
	public ResultSet getKhachhangRs() {
		return khachhangRs;
	}
	public void setKhachhangRs(ResultSet khachhangRs) {
		this.khachhangRs = khachhangRs;
	}
	public ResultSet getDonhangRs() {
		return donhangRs;
	}
	public void setDonhangRs(ResultSet donhangRs) {
		this.donhangRs = donhangRs;
	}
	public String getTungay() {
		return tungay;
	}
	public void setTungay(String tungay) {
		this.tungay = tungay;
	}
	public String getDenngay() {
		return denngay;
	}
	public void setDenngay(String denngay) {
		this.denngay = denngay;
	}
	public String getIsTraNguyenDon() {
		String isTraNguyenDon = "0";
		return isTraNguyenDon;
	}

	public void setIsTraNguyenDon(String isTraNguyenDon) {
		this.isTraNguyenDon = isTraNguyenDon;
	}

	public String getUserId() 
	{		
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;		
	}

	public String getSmartId() 
	{		
		return this.smartId;
	}

	public void setSmartId(String smartId) 
	{
		this.smartId = smartId;		
	}

	public String getKhTen() 
	{		
		return this.khTen;
	}

	public void setKhTen(String khTen) 
	{
		this.khTen = khTen;		
	}

	public String getId() 
	{
		return this.id;
	}

	public void setId(String id) 
	{
		this.id = id;		
	}

	public String getKhId() 
	{	
		return this.khId;
	}

	public void setKhId(String khId) 
	{
		this.khId = khId;
	}

	public String getNgaygiaodich() 
	{	
		return this.ngaygiaodich;
	}

	public void setNgaygiaodich(String ngaygiaodich) 
	{
		this.ngaygiaodich = ngaygiaodich;		
	}

	public String getDaidienkd() 
	{	
		return this.daidienkd;
	}

	public void setDaidienkd(String daidienkd) 
	{
		this.daidienkd = daidienkd;		
	}

	public String getTrangthai()
	{	
		return this.trangthai;
	}

	public void setTrangthai(String trangthai)
	{
		this.trangthai = trangthai;		
	}

	public String getNgaytao()
	{	
		return this.ngaytao;
	}

	public void setNgaytao(String ngaytao) 
	{
		this.ngaytao = ngaytao;		
	}

	public String getNguoitao() 
	{		
		return this.nguoitao;
	}

	public void setNguoitao(String nguoitao) 
	{
		this.nguoitao = nguoitao;		
	}

	public String getNgaysua() 
	{		
		return this.ngaysua;
	}

	public void setNgaysua(String ngaysua) 
	{
		this.ngaysua = ngaysua;	
	}

	public String getNguoisua() 
	{		
		return this.nguoisua;
	}

	public void setNguoisua(String nguoisua) 
	{
		this.nguoisua = nguoisua;	
	}

	public String getChietkhau() 
	{
		if (this.chietkhau.length() <= 0)
			this.chietkhau = "0";
		return this.chietkhau;
	}

	public void setChietkhau(String chietkhau) 
	{
		this.chietkhau = chietkhau;		
	}

	public String getVAT() 
	{
		if (this.VAT == "")
			this.VAT = "10";
		return this.VAT;
	}

	public void setVAT(String vat) 
	{
		this.VAT = vat;	
	}

	public String getMessage() 
	{	
		return this.msg;
	}

	public void setMessage(String msg) 
	{
		this.msg = msg;		
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

	public ResultSet getDdkdList() 
	{	
		return this.ddkdlist;
	}

	public void setDdkdList(ResultSet ddkdList)
	{
		this.ddkdlist = ddkdList;		
	}

	public String getDdkdId() 
	{		
		return this.ddkdId;
	}

	public void setDdkdId(String ddkdId) 
	{
		this.ddkdId = ddkdId;	
	}

	public List<ISanpham> getSpList()
	{	
		return this.splist;
	}

	public void setSpList(List<ISanpham> splist) 
	{
		this.splist = splist;
	}

	public String getTongtientruocVAT() 
	{		
		return this.tongtientruocVAT;
	}

	public void setTongtientruocVAT(String tttvat) 
	{
		this.tongtientruocVAT = tttvat;		
	}

	public String getTongtiensauVAT()
	{		
		return this.tongtiensauVAT;
	}

	public void setTongtiensauVAT(String ttsvat) 
	{
		this.tongtiensauVAT = ttsvat;		
	}

	public String getGsbhId() 
	{
		return this.gsbhId;
	}

	public void setGsbhId(String gsbhId) 
	{
		this.gsbhId = gsbhId;
	}

	public ResultSet getKhList() 
	{
		return this.khlist;
	}

	public void setKhList(ResultSet khlist) 
	{
		this.khlist = khlist;
	}

	public Hashtable<String, Integer> getSpThieuList() 
	{
		return this.spThieuList;
	}

	public void setSpThieuList(Hashtable<String, Integer> spThieuList) 
	{
		this.spThieuList = spThieuList;
	}

	//tra km
	public Hashtable<String, Float> getScheme_Tongtien() 
	{
		return this.scheme_tongtien;
	}

	public void setScheme_Tongtien(Hashtable<String, Float> scheme_tongtien) 
	{
		this.scheme_tongtien = scheme_tongtien;
	}

	public Hashtable<String, Float> getScheme_Chietkhau() 
	{
		return this.scheme_chietkhau;
	}

	public void setScheme_Chietkhau(Hashtable<String, Float> scheme_chietkhau) 
	{
		this.scheme_chietkhau = scheme_chietkhau;
	}

	public List<ISanpham> getScheme_SpList() 
	{
		return this.scheme_sanpham;
	}

	public void setScheme_SpList(List<ISanpham> splist) 
	{
		this.scheme_sanpham = splist;
	}

	private void getNppInfo()
	{		
		geso.dms.distributor.util.Utility util = new geso.dms.distributor.util.Utility();
		this.nppId = util.getIdNhapp(this.userId);
		this.nppTen = util.getTenNhaPP();
		this.sitecode = util.getSitecode();		
	}

	String kenh()
	{ 
		String sql ="select kbh_fk from giamsatbanhang where pk_seq ='"+ this.gsbhId +"'";
		ResultSet rs = db.get(sql);
		if (rs != null)
		{
			try 
			{
				rs.next();
				return rs.getString("kbh_fk");
			} 
			catch(Exception e) {}
		}
		return "null";
	}

	public void createRS() 
	{
		this.getNppInfo();
		this.createDH();
		this.createNpp();
		this.createDdkd();	
		this.createKho();
		this.createLydo();
		this.CreateSpList();
	}

	private void createLydo()
	{
		String sql ="select a.pk_seq,a.lydo from doitrahang a where loai=1";
		System.out.println("lấy lý do " + sql);
		this.lydoRs = db.get(sql);
	}

	private void createDH()
	{
		String qqs = "select pk_seq as dhId  from donhang where npp_fk = '"+nppId+"' and ngaynhap > (select max(ngayks) from khoasongay where npp_fk ='"+nppId+"' ) and khachhang_fk = '"+khId+"' and trangthai = 1";
		System.out.println("vao day k " + qqs);
		this.donhangList = db.get(qqs);
	}

	private void createNpp()
	{
		String qqs = "select ten as nppTen, pk_seq as nppId from nhaphanphoi where 1=1 ";
		if (this.nppId != null && this.nppId.length() > 0)
		{
			qqs += " and pk_seq = "+this.nppId;
		}
		System.out.println("vao day k " + qqs);
		this.nppList = db.get(qqs);
	}

	private void createDdkd()
	{
		//tao gsbh
		if (this.nppId.length() > 0)
		{
			String sql ="select distinct a.ten  ,a.pk_seq from giamsatbanhang a inner join NHAPP_GIAMSATBH b on a.pk_seq = b.gsbh_fk where  npp_fk ='"+ this.nppId +"'";
			this.gsbhList = db.get(sql);

			if (this.gsbhId.length() > 0)
			{
				String query = "select ten as ddkdTen, pk_seq as ddkdId from daidienkinhdoanh where npp_fk ='"+ this.nppId +" ' and pk_seq in (select ddkd_fk from ddkd_gsbh where gsbh_fk in (select gsbh_fk from nhapp_giamsatbh where  gsbh_fk ='"+ this.gsbhId +"' and npp_fk = '" + this.nppId + "') )";
				this.ddkdlist = db.get(query);
			}
			else 
			{
				String query = "select ten as ddkdTen, pk_seq as ddkdId from daidienkinhdoanh where npp_fk ='"+ this.nppId +" ' ";
				this.ddkdlist = db.get(query);
			}
		}
		else
		{
			String sql ="select distinct a.ten, a.pk_seq from giamsatbanhang a inner join NHAPP_GIAMSATBH b on a.pk_seq = b.gsbh_fk ";
			this.gsbhList = db.get(sql);

			if (this.gsbhId.length() > 0)
			{
				String query = "select distinct ten as ddkdTen, pk_seq as ddkdId from daidienkinhdoanh where  pk_seq in (select ddkd_fk from ddkd_gsbh where gsbh_fk in (select gsbh_fk from nhapp_giamsatbh where  gsbh_fk ='"+ this.gsbhId +"' ) )";
				this.ddkdlist = db.get(query);
			}
			else 
			{
				String query = "select ten as ddkdTen, pk_seq as ddkdId from daidienkinhdoanh  ";
				this.ddkdlist = db.get(query);
			}
		}

	}

	private void createKhRs()
	{
		String query;
		if (this.nppId.length() > 0)
		{
			query = "select a.pk_seq as khId, a.ten as khTen, a.diachi, ISNULL(b.CHIETKHAU,0) as chietkhau ";
			query = query + "from KHACHHANG a left join MUCCHIETKHAU b on a.CHIETKHAU_FK = b.PK_SEQ where a.npp_fk = '" + this.nppId + "' ";
		}
		else 
		{
			query = "select a.pk_seq as khId, a.ten as khTen, a.diachi, ISNULL(b.CHIETKHAU,0) as chietkhau ";
			query = query + "from KHACHHANG a left join MUCCHIETKHAU b on a.CHIETKHAU_FK = b.PK_SEQ  ";
		}
		//		if (this.tbhId.length() > 0)
		//			query = query + "and a.PK_SEQ in (select distinct khachhang_fk from KHACHHANG_TUYENBH where TBH_FK = '" + this.tbhId + "')";

		//System.out.println("Tao Khach Hang list:" + query);
		this.khlist = db.get(query);

	}

	private void createKho()
	{
		this.kholist = db.get("select distinct PK_SEQ as khoId, Ten, Diengiai from KHO where PK_SEQ in (select kho_fk from NHAPP_KHO)");
	}


	public void init() 
	{
		String query = "\n select isnull(a.ghichutt,'') as ghichutt,a.pk_seq as dhtvId, a.ngaynhap, a.trangthai, a.ngaytao, a.ngaysua, " +
		"\n a.khachhang_fk as khId, a.gsbh_fk as gsbhId, a.donhang_fk, g.ten as khTen, g.smartid, " +
		"\n a.kho_fk as khoId, b.ten as nguoitao, c.ten as nguoisua, e.pk_seq as ddkdId, e.ten as ddkdTen, " +
		"\n f.pk_seq as nppId, f.ten as nppTen, a.lydo_fk, dh.tonggiatri " +
		"\n from donhangtrave a left join nhanvien b on a.nguoitao = b.pk_seq " +
		"\n left join nhanvien c on a.nguoisua = c.pk_seq " +
		"\n inner join daidienkinhdoanh e on a.ddkd_fk = e.pk_seq " +
		"\n inner join nhaphanphoi f on a.npp_fk = f.pk_seq " +
		"\n inner join khachhang g on a.khachhang_fk = g.pk_seq " +
		"\n left join donhang dh on dh.pk_seq = a.donhang_fk " +
		"\n where  a.pk_seq = '" + this.id + "'";
		ResultSet rs = db.get(query);
		System.out.println("Init: " + query);
		
		try
		{
			rs.next();
			this.id = rs.getString("dhtvId");
			this.khId = rs.getString("khId");
			this.ghichu = rs.getString("ghichutt");
			this.khTen = rs.getString("khTen");
			this.smartId = rs.getString("smartId").substring(rs.getString("smartId").indexOf("_")+1, rs.getString("smartId").length());
			this.ngaygiaodich = rs.getString("ngaynhap");
			this.nppId = rs.getString("nppId");
			this.nppTen = rs.getString("nppTen");
			this.daidienkd = rs.getString("ddkdTen");
			this.trangthai = rs.getString("trangthai");
			this.ngaytao = rs.getString("ngaytao");
			this.nguoitao = rs.getString("nguoitao");
			this.ngaysua = rs.getString("ngaysua");
			this.nguoisua = rs.getString("nguoisua");
			this.tongtientruocVAT = rs.getString("tonggiatri");
			this.VAT = "10";
			this.ddkdId = rs.getString("ddkdId");
			this.khoId = rs.getString("khoId");
			this.lydo = rs.getString("lydo_fk");
			this.gsbhId = "";
			
			if (rs.getString("gsbhId") != null)
				this.gsbhId = rs.getString("gsbhId");
			
			this.dhId = "";
			if (rs.getString("donhang_fk") != null)
				this.dhId = rs.getString("donhang_fk");
			
			rs.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		this.createDH();
		this.createNpp();
		this.createLydo();
		this.createDdkd();
		this.createKhRs();
		this.createKho();
		this.CreateSpList();
	}

	public String getKhoId() 
	{
		return this.khoId;
	}

	public void setKhoId(String khoId) 
	{
		this.khoId = khoId;
	}

	public ResultSet getKhoList() 
	{
		return this.kholist;
	}

	public void setKhoList(ResultSet kholist) 
	{
		this.kholist = kholist;
	}

	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);	
	}

	public void DBclose()
	{
		try 
		{
			if (!(this.ddkdlist == null))
				this.ddkdlist.close();
			if (this.db != null)
				this.db.shutDown();
		} 
		catch(Exception e) {}
	}

	public ResultSet getGsbhList()
	{	
		return this.gsbhList;
	}

	public void setNppList(ResultSet nppList) 
	{
		this.nppList = nppList;
	}

	public ResultSet getNppList()
	{	
		return this.nppList;
	}

	public void setGsbhList(ResultSet gsbhList) 
	{
		this.gsbhList = gsbhList;
	}

	public void setDonhangList(ResultSet donhangList) 
	{
		this.donhangList = donhangList;
	}

	public ResultSet getDonhangList()
	{	
		return this.donhangList;
	}


	public String getTongChietKhau() 
	{
		return this.tongchietkhau;
	}

	public void setTongChietKhau(String tck) 
	{
		this.tongchietkhau = tck;
	}

	public boolean CreateDhtv(List<ISanpham> splist) 
	{		
		this.ngaytao = getDateTime();
		this.nguoisua = this.userId;
		//this.ngaygiaodich = getDateTime();

		String msg = checkNgayTraHang();
		if (msg.length() > 0)
		{
			this.msg = msg;
			return false;
		}

		try 
		{
			db.getConnection().setAutoCommit(false);	
			
			
			String msgKS  = geso.dms.center.util.Utility.Check_Huy_NghiepVu_KhoaSo_Tao_Moi_NV(this.nppId, this.ngaygiaodich, db);
			if( msgKS.length() > 0)
			{
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
				this.msg = msgKS;
				return false;
				
			}
			
			
			if (TachTraNguyenDon == 1) {
				dhId = null;
			}
			
			String _lydo_fk = "";
			if (this.lydo == null || this.lydo.trim().length() <= 0) {
				_lydo_fk = null;
			}
            if(this.ghichu!=null && this.ghichu.trim().length()<=0){
            	db.getConnection().rollback();
				this.msg = "Vui lòng nhập ghi chú";
				return false; 
            }
			String kbh = kenh();
			String query = "\n insert into donhangtrave(ghichutt,ngaynhap, trangthai, ngaytao, ngaysua, nguoitao, nguoisua, ddkd_fk, " +
			"\n khachhang_fk, npp_fk, vat, gsbh_fk, kho_fk, kbh_fk, donhang_fk, NgayGio) " +
			"\n select N'" + this.ghichu + "','" + this.ngaygiaodich + "', '0', '" + this.ngaytao + "', '" + this.ngaytao + "', '" + this.nguoisua + "', " +
			"\n '" + this.nguoisua + "', '" + this.ddkdId + "', '" + this.khId + "', '" + this.nppId + "', '" + this.VAT + "', " +
			"\n '" + this.gsbhId + "', '" + this.khoId + "', '" + kbh + "', " + this.dhId + ", CONVERT(char(10),GETDATE(),121) + ' ' + CONVERT(CHAR( 5),GETDATE(),114) ";
			if (!db.update(query)){
				db.getConnection().rollback();
				this.msg = "Lỗi tạo mới Đơn hàng trả về 1.";
				return false; 
			}
			query = "select scope_identity() as dhId";
			ResultSet rsDh = this.db.get(query);
			rsDh.next();
			this.id = rsDh.getString("dhId");
			rsDh.close();
			
			query = " update dh  set LCH_FK = lch.PK_SEQ , tyleVat = case when lch.khongVat = 1 then 1 else 1.1 end "+
			"\n from donhangtrave dh "+
			"\n inner join KHACHHANG kh on kh.PK_SEQ = dh.KHACHHANG_FK "+
			"\n inner join LOAICUAHANG lch on lch.PK_SEQ = kh.LCH_FK "+
			"\n where dh.PK_SEQ =" + this.id;
			if (db.updateReturnInt(query)!= 1) {
				System.out.println("query = "+ query);
				this.msg = "Lỗi khi lưu đơn hàng 2";
				this.db.getConnection().rollback();
				return false;
			}
			
			if (splist.size() > 0)
			{
				

				for (int m = 0; m < splist.size(); m++)
				{
					ISanpham sp = splist.get(m);
					String pk_seq = "";
					ResultSet rs = db.get("select pk_seq from sanpham where ma = '" + sp.getMasanpham() + "'");
					try 
					{
						rs.next();
						pk_seq = rs.getString("pk_seq");
						rs.close();
					} 
					catch(Exception e) {
						db.getConnection().rollback();
						this.msg = "Lỗi không tìm thấy sản phẩm: " + sp.getMasanpham();
						return false; 
					}
					
					if (pk_seq != "")
					{
						query = "insert into donhangtrave_sanpham(sanpham_fk, donhangtrave_fk, soluong, kho_fk, giamua) " +
						"\n select '" + pk_seq + "', '" + this.id + "', '" + sp.getSoluong().replace(",", "") + "', " + khoId + ", '" + sp.getDongia().replace(",", "") + "'";
						if (!db.update(query))
						{
							db.getConnection().rollback();
							this.msg = "Lỗi tạo mới Đơn hàng trả về 2.";
							return false; 
						}
					}
				}
				
				msg = UpdateGiaTri(this.db, id);
				if (msg != null && msg.length() > 0) {
					this.db.getConnection().rollback();
					this.msg = msg;
					return false;
				}
			}
			else
			{
				this.db.getConnection().rollback();
				this.msg = "Vui lòng nhập sản phẩm trả.";
				return false; 
			}
			this.db.getConnection().commit();
		}
		catch(Exception e1) {
			e1.printStackTrace();
			geso.dms.center.util.Utility.rollback_throw_exception(this.db);
			this.msg = "Lỗi ngoại lệ tạo mới.";
			return false; 
		} finally{ try { this.db.getConnection().setAutoCommit(true); } catch (SQLException e) { e.printStackTrace(); } }
		return true;
	}

	public boolean CreateDhtv_NguyenDon() 
	{		
		

		try 
		{
			this.db.getConnection().setAutoCommit(false);	

//			Object[] data = dbutils.setObject(this.lydo, this.ngaygiaodich, Utility.getNgayHienTai(),  Utility.getNgayHienTai(), 
//					this.userId, this.userId, this.dhId, this.dhId);

			String query = "\n insert into donhangtrave(ghichutt,LCH_FK,tyleVat,ghichu, ngaynhap, trangthai, ngaytao, ngaysua, nguoitao, nguoisua, ddkd_fk, " +
			"\n khachhang_fk, npp_fk, vat, gsbh_fk, kho_fk, kbh_fk, donhang_fk, isTraNguyenDon, ngaygio, ngaygiosua) " +
			"\n select N'"+this.ghichu+"',LCH_FK,tyleVat,N'"+this.lydo+"', '"+this.ngaygiaodich+"', '0', '"+Utility.getNgayHienTai()+"', '"+Utility.getNgayHienTai()+"',  "+		
			"\n '"+this.userId+"', '"+this.userId+"', ddkd_fk, khachhang_fk,npp_fk, vat,gsbh_fk, kho_fk, kbh_fk, pk_seq as donhang_fk,1, CONVERT(char(10),GETDATE(),121) + ' ' + CONVERT(CHAR( 5),GETDATE(),114), CONVERT(char(10),GETDATE(),121) + ' ' + CONVERT(CHAR( 5),GETDATE(),114)  "+
			"\n from  donhang where trangthai = 1 and pk_seq = "+this.dhId+" ";
			if (!db.update(query)){
				db.getConnection().rollback();
				this.db.getConnection().setAutoCommit(true);
				this.msg = "Lỗi tạo mới trả đơn hàng: " + query;
				return false; 
			}
		
			//			"\n select ?, ?, '0', ?, ?, ?, " +
//			"\n ?, ddkd_fk, khachhang_fk, npp_fk, vat, " +
//			"\n gsbh_fk, kho_fk, kbh_fk, ?, 1, CONVERT(char(10),GETDATE(),121) + ' ' + CONVERT(CHAR( 5),GETDATE(),114) " +
//			"\n from donhang where trangthai = 1 and pk_seq = ? ";
//			if (this.db.update_v2(query, data) != 1){
//				this.db.getConnection().rollback();
//				this.db.getConnection().setAutoCommit(true);
//				this.msg = "Lỗi tạo mới trả nguyên đơn!";
//				return false; 
//			}		
			
//			String idTmp = this.db.getPk_seq();
			String idTmp = "select SCOPE_IDENTITY() as nkhId";
			ResultSet rs = this.db.get(idTmp);
				rs.next();
				idTmp = rs.getString("nkhId");
			System.out.println("id dontrahang moi tao: " + idTmp);
			this.msg = geso.dms.center.util.Utility.Check_Huy_NghiepVu_KhoaSo("donhangtrave", idTmp, "ngaynhap", this.db);
			if( this.msg.length() > 0) {
				this.db.getConnection().rollback();
				this.db.getConnection().setAutoCommit(true);
				return false;
			}
			String checksql="select count(*) as sl from donhangtrave a inner join donhang b on a.donhang_fk = b.pk_seq where "
					+ " a.ngaynhap>=b.ngaynhap and  a.pk_seq="+idTmp;
			ResultSet rscheck=db.get(checksql);
			rscheck.next();
			if(rscheck.getInt("sl")==0){
			
				this.db.getConnection().setAutoCommit(true);
				this.msg =  "Ngày trả phải lớn hơn ngày đơn hàng!";
				return false; 
			}
			
			
			this.msg = geso.dms.center.util.Utility.Check_Huy_NghiepVu_KhoaSo("donhang",  this.dhId, "ngaynhap", this.db);
			if( this.msg.length() > 0)
			{
				this.db.getConnection().rollback();
				this.db.getConnection().setAutoCommit(true);
				return false;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return true;

		}

		catch(Exception e1) {
			e1.printStackTrace();
			geso.dms.center.util.Utility.rollback_throw_exception(this.db);
			this.msg = "Lỗi ngoại lệ tạo mới trả nguyên đơn!";
			return false; 
		}

		
	}

	public boolean UpdateDhtv_NguyenDon() 
	{		
		

		try 
		{
			db.getConnection().setAutoCommit(false);				

			Object[] data = dbutils.setObject(this.lydo, this.ngaygiaodich, Utility.getNgayHienTai(), this.userId, this.dhId, this.dhId, this.id);

//			String query = "\n update a set a.ghichu = ?, a.ngaynhap = ?, a.ngaysua = ?, a.nguoisua = ?, a.ddkd_fk = dh.ddkd_fk, " +
//			"\n a.khachhang_fk = dh.khachhang_fk, a.npp_fk = dh.npp_fk, a.vat = dh.vat, a.gsbh_fk = dh.gsbh_fk, " +
//			"\n kho_fk = dh.kho_fk, kbh_fk = dh.kbh_fk, donhang_fk = ?, ngaygiosua= CONVERT(char(10),GETDATE(),121) + ' ' + CONVERT(CHAR( 5),GETDATE(),114) " +
//			"\n from donhangtrave a inner join donhang dh on 1=1 " +
//			"\n where a.trangthai = 0 and dh.pk_seq = ? and a.pk_seq = ? ";
//
//			if (db.update_v2(query, data) != 1){
//				dbutils.viewQuery(query, data);
//				db.getConnection().rollback();
//				db.getConnection().setAutoCommit(true);
//				this.msg = "Lỗi cập nhật trả nguyên đơn!";
//				return false; 
//			}		
//			
			String query = " update a set a.ghichutt = N'"+this.ghichu+"',a.LCH_FK = dh.LCH_FK ,a.tyleVat = dh.tyleVat,a.ghichu = N'"+this.lydo+"', a.ngaynhap = '"+this.ngaygiaodich+"', a.ngaysua = '"+Utility.getNgayHienTai()+"'"
					+ "\n ,nguoisua = '"+this.userId+"', a.ddkd_fk = dh.ddkd_fk, a.khachhang_fk = dh.khachhang_fk, a.npp_fk = dh.npp_fk, a.vat = dh.vat, a.gsbh_fk = dh.gsbh_fk,"
					+ "\n  kho_fk = dh.kho_fk, kbh_fk = dh.kbh_fk, donhang_fk = '"+this.dhId+"', ngaygiosua= CONVERT(char(10),GETDATE(),121) + ' ' + CONVERT(CHAR( 5),GETDATE(),114) "
					+ "\n from donhangtrave a inner join donhang dh on 1=1 where a.trangthai = 0 and dh.pk_seq = '"+this.dhId+"' and a.pk_seq = '"+this.id+"' ";
			
			if (!db.update(query)){
				db.getConnection().rollback();
				this.db.getConnection().setAutoCommit(true);
				this.msg = "Lỗi tạo mới trả đơn hàng: " + query;
				return false; 
			}
			this.msg = geso.dms.center.util.Utility.Check_Huy_NghiepVu_KhoaSo("donhangtrave", this.id, "ngaynhap", db);
			if( this.msg.length() > 0) {
				db.getConnection().rollback();
				return false;
			}
			String checksql="select count(*) as sl from donhangtrave a inner join donhang b on a.donhang_fk = b.pk_seq where "
					+ " a.ngaynhap>=b.ngaynhap and  a.pk_seq="+this.id;
			ResultSet rscheck=db.get(checksql);
			rscheck.next();
			if(rscheck.getInt("sl")==0){
			
				this.db.getConnection().setAutoCommit(true);
				this.msg =  "Ngày trả phải lớn hơn ngày đơn hàng!";
				return false; 
			}
			this.msg = geso.dms.center.util.Utility.Check_Huy_NghiepVu_KhoaSo("donhang",  this.dhId, "ngaynhap", db);
			if( this.msg.length() > 0)
			{
				db.getConnection().rollback();
				return false;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return true;
		}

		catch(Exception e1) {
			e1.printStackTrace();
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			this.msg = "Lỗi ngoại lệ cập nhật trả nguyên đơn!";
			return false; 
		}

		
	}

	private String checkNgayTraHang() 
	{
		String msg = "";
		msg = this.checkNgaykhoaso();
		if (msg.length() > 0)
			return msg;
		msg = this.checkNgaytradh();
		if (msg.length() > 0)
			return msg;

		return "";
	}

	public boolean UpdateDhtv(List<ISanpham> splist)
	{
		this.ngaysua = getDateTime();
		this.nguoisua = this.userId;
		
		if (TachTraNguyenDon == 1) {
			dhId = null;
		}

		String msg = checkNgayTraHang();
		if (msg.length() > 0)
		{
			this.msg = msg;
			return false;
		}

/*		if (this.spThieuList.size() > 0)
		{
			this.msg = "Kiểm tra lại số lượng các mặt hàng trả về.";
			return false;
		}*/

		try 
		{
			db.getConnection().setAutoCommit(false);
			
			String _lydo_fk = "";
			if (this.lydo == null || this.lydo.trim().length() <= 0) {
				_lydo_fk = null;
			}
			
			
			String msgKS  = geso.dms.center.util.Utility.Check_Huy_NghiepVu_KhoaSo_Tao_Moi_NV(this.nppId, this.ngaygiaodich, db);
			if( msgKS.length() > 0)
			{
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
				this.msg = msgKS;
				return false;
			}
			
			if(this.ghichu!=null && this.ghichu.trim().length()<=0){
            	db.getConnection().rollback();
				this.msg = "Vui lòng nhập ghi chú";
				return false; 
            }
			String kbh = kenh();
			String query = "\n update donhangtrave set ghichutt = N'" + this.ghichu + "', ngaynhap = '" + this.ngaygiaodich + "', ddkd_fk = '"+ this.ddkdId + "', " +
			"\n khachhang_fk = '" + this.khId + "', gsbh_fk = '" + this.gsbhId + "', donhang_fk = " + this.dhId + ", " +
			"\n kho_fk = '" + this.khoId + "', kbh_fk = '" + kbh + "', ngaysua = '" + this.ngaysua + "', nguoisua = '" + this.nguoisua + "', " +
			"\n lydo_fk = " + _lydo_fk + ", NgayGioSua=CONVERT(char(10),GETDATE(),121) + ' ' + CONVERT(CHAR( 5),GETDATE(),114) where pk_seq = '" + this.id + "'" ;
			if (!db.update(query))
			{
				db.getConnection().rollback();
				this.msg = "Lỗi cập nhật Đơn trả về 1.";
				return false; 
			}
			
			query = " update dh  set LCH_FK = lch.PK_SEQ , tyleVat = case when lch.khongVat = 1 then 1 else 1.1 end "+
			"\n from DONHANGTRAVE dh "+
			"\n inner join KHACHHANG kh on kh.PK_SEQ = dh.KHACHHANG_FK "+
			"\n inner join LOAICUAHANG lch on lch.PK_SEQ = kh.LCH_FK "+
			"\n where dh.PK_SEQ =" + this.id;
			if (db.updateReturnInt(query)!= 1) {
				System.out.println("query = "+ query);
				this.msg = "Lỗi khi lưu đơn hàng 2";
				this.db.getConnection().rollback();
				return false;
			}

			if (splist.size() > 0)
			{
				//delete san pham cu trong bang donhangtrave_sanpham
				query = "delete from donhangtrave_sanpham where donhangtrave_fk = '" + this.id + "'";
				if (!db.update(query)){
					db.getConnection().rollback();
					this.msg = "Lỗi cập nhật Đơn trả về 2.";
					return false; 
				}

				query = "delete from donhangtrave_sanpham_chitiet where donhangtrave_fk = '" + this.id + "'";
				if (!db.update(query)){
					db.getConnection().rollback();
					this.msg = "Lỗi cập nhật Đơn trả về 3.";
					return false; 
				}

				for (int m = 0; m < splist.size(); m++)
				{
					ISanpham sp = splist.get(m);
					String pk_seq = "";
					ResultSet rs = db.get("select pk_seq from sanpham where ma = '" + sp.getMasanpham() + "'");
					try 
					{
						rs.next();
						pk_seq = rs.getString("pk_seq");
						rs.close();
					} 
					catch(Exception e) {
						db.getConnection().rollback();
						this.msg = "Lỗi cập nhật Đơn trả về 4.";
						return false; 
					}
					
					if (pk_seq != "")
					{
						query = "insert into donhangtrave_sanpham(sanpham_fk, donhangtrave_fk, soluong, kho_fk, giamua) values('" + pk_seq + "','" + this.id + "','" + sp.getSoluong().replace(",", "") + "','100004','" + sp.getDongia().replace(",", "") + "')";
						if (!db.update(query))
						{
							db.getConnection().rollback();
							this.msg = "Lỗi cập nhật Đơn trả về 5.";
							return false; 
						}
					}
				}
				
				msg = UpdateGiaTri(db, id);
				if (msg != null && msg.length() > 0) {
					db.getConnection().rollback();
					this.msg = msg;
					return false;
				}
			}
			else
			{
				this.db.getConnection().rollback();
				this.msg = "Vui lòng nhập sản phẩm trả.";
				return false;
			}
			this.db.getConnection().commit();
		} 
		catch(Exception e1) {
			e1.printStackTrace();
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			this.msg = "Loi khi cap nhat bang "+e1.toString();
			return false;
		} finally{ try { this.db.getConnection().setAutoCommit(true); } catch (SQLException e) { e.printStackTrace(); } }
		return true;
	}

	public void CreateSpList()
	{		
		String command = "";
		if (this.id.length() > 0)
		{
			command = "select DISTINCT b.pk_seq as spId, b.ma as spMa, b.ten as spTen, a.soluong, isnull(c.donvi, 'Chua xac dinh') as donvi, a.giamua as dongia ";
			command = command + "from donhangtrave_sanpham a inner join sanpham b on a.sanpham_fk = b.pk_seq left join donvidoluong c on b.dvdl_fk = c.pk_seq ";
			command = command + " where a.donhangtrave_fk = '" + this.id + "'";
		}
		else
		{
			command = "select DISTINCT b.pk_seq as spId, b.ma as spMa, b.ten as spTen, a.soluong, isnull(c.donvi, 'Chua xac dinh') as donvi, a.giamua as dongia ";
			command = command + "from donhang_sanpham a inner join sanpham b on a.sanpham_fk = b.pk_seq left join donvidoluong c on b.dvdl_fk = c.pk_seq ";
			command = command + " where a.donhang_fk = '" + this.dhId + "'";
		}
		System.out.println("___sp______"+command);
		ResultSet splistRs = db.get(command);
		float tonggiatri = 0f; 
		List<ISanpham> splist = new ArrayList<ISanpham>();
		if (splistRs != null)
		{
			String[] param = new String[8];
			ISanpham sp = null;	
			try 
			{
				while(splistRs.next())
				{
					param[0] = splistRs.getString("spId");
					param[1] = splistRs.getString("spma");
					param[2] = splistRs.getString("spten");
					param[3] = splistRs.getString("soluong");
					param[4] = splistRs.getString("donvi");
					param[5] = splistRs.getString("dongia");
					param[6] = "";
					param[7] = "";

					tonggiatri += Float.parseFloat(param[5]) * Float.parseFloat(param[3]);

					sp = new Sanpham(param);
					splist.add(sp);
				}
			} 
			catch(Exception e) {
				e.printStackTrace();
			}
		}

		if (this.dhId != null && this.dhId.length() > 0) //hien thi san pham khuyen mai
		{
			//spkm
			String query = "\n select d.pk_seq as spId, a.spMa, d.ten as spten, dv.donvi, a.soluong, a.tonggiatri, b.scheme, " +
			"\n (a.tonggiatri / a.soluong) as dongia " +
			"\n from donhang_ctkm_trakm a " + 
			"\n inner join ctkhuyenmai b on a.ctkmid = b.pk_seq " +
			"\n left join sanpham d on a.spMa = d.ma " +
			"\n left join donvidoluong dv on dv.pk_seq = d.dvdl_fk " + 
			"\n where a.donhangId = '" + this.dhId + "' and a.spMa is not null ";

			ResultSet spkmlistRs = db.get(query);
			if (spkmlistRs != null)
			{
				String[] param = new String[8];
				ISanpham sp = null;	
				try 
				{
					while(spkmlistRs.next())
					{
						param[0] = spkmlistRs.getString("spId");
						param[1] = spkmlistRs.getString("spma");
						param[2] = spkmlistRs.getString("spten");
						param[3] = spkmlistRs.getString("soluong");
						param[4] = spkmlistRs.getString("donvi");
						param[5] = spkmlistRs.getString("dongia");
						param[6] = spkmlistRs.getString("scheme");
						param[7] = "";

						sp = new Sanpham(param);
						splist.add(sp);
					}
					spkmlistRs.close();
				} 
				catch(Exception e) { 
					e.printStackTrace();
				}
			}
		}

		this.splist = splist;
	}

	public String getDonhangId() 
	{
		return this.dhId;
	}

	public void setDonhangId(String dhId) 
	{
		this.dhId = dhId;
	}


	String ngaykhoaso = "";
	private String checkNgaykhoaso() 
	{
		//don hang phai sau ngay khoa so gan nhat

		String query = "select max(ngayks) as nks from khoasongay where npp_fk = '" + this.nppId + "'";
		ResultSet rsKs = db.get(query);

		if (rsKs != null)
		{
			try 
			{
				if (rsKs.next())
				{
					ngaykhoaso = rsKs.getString("nks");
					rsKs.close();
				}
			} 
			catch(Exception e) {}
		}

		//String[] ngaythang = ngaykhoaso.split("-");
		//int ngay = Integer.parseInt(ngaythang[2]) + 1;

		//String ngayTang1 = ngaythang[0] + "-" + ngaythang[1] + "-" + Integer.toString(ngay);

		query = "SELECT DATEDIFF(day, '" + ngaykhoaso + "', '" + this.ngaygiaodich + "') AS songay";
		System.out.println("Query la: " + query + "\n");

		ResultSet rs = db.get(query);

		int songay = 0;
		String msg = "";
		if (rs != null)
		{
			try 
			{
				if (rs.next())
				{
					songay = rs.getInt("songay");
				}
				rs.close();
			} 
			catch(Exception e) {}
		}

		if (songay < 1)
			msg = "Bạn chỉ được phép trả những đơn hàng sau ngày khóa sổ gần nhất 1 ngay";
		return msg;
	}

	private String checkNgaytradh() 
	{
		String msg = "";
		String ngaynhap = this.ngaygiaodich;

		String[] nn = ngaynhap.trim().split("-");
		String[] date = this.getDateTime().trim().split("-");
		if (ngaykhoaso.length() > 0)
			date = this.ngaykhoaso.trim().split("-");
		return msg;
	}

	@Override
	public String getLydo() {
		// TODO Auto-generated method stub
		return this.lydo;
	}

	@Override
	public void setLydo(String lydo) {
		// TODO Auto-generated method stub
		this.lydo= lydo;
	}

	@Override
	public ResultSet getLydoList() {
		// TODO Auto-generated method stub
		return this.lydoRs;
	}

	@Override
	public void setLydoList(ResultSet lydolist) {
		// TODO Auto-generated method stub
		this.lydoRs=lydolist;
	}

	public void init_TraNguyenDon() {
		String query = "";

		try {
			query = "select ghichu, trangthai, ngaynhap, npp_fk, ddkd_fk, khachhang_fk, donhang_fk, lydo_fk " +
			"\n from donhangtrave where pk_seq = "+this.id;
			System.out.println("init donhangtra: " + query);
			ResultSet rs = db.get(query);
			while (rs.next()) {
				this.ngaygiaodich = rs.getString("ngaynhap");
				this.nppId = rs.getString("npp_fk");
				this.ddkdId = rs.getString("ddkd_fk");
				this.khId = rs.getString("khachhang_fk");
				this.dhId = rs.getString("donhang_fk");
				this.lydo = rs.getString("ghichu");
				this.trangthai = rs.getString("trangthai");
			}
			rs.close();

			createRs_TraNguyenDon();
		}		
		catch (Exception e){
			e.printStackTrace();
		}
	}

	public void createRs_TraNguyenDon() {

		String query = "";

		try {

			if (!checkNull(this.view)) {
				this.view = "";
				this.getNppInfo();
			}

			if (this.view.equals("TT")) {
				query = "select npp_fk from donhangtrave where pk_seq = "+this.id;
				ResultSet rs = db.get(query);
				while (rs.next()) {
					this.nppId = rs.getString("npp_fk");
				}
				rs.close();
			}			

			if (checkNull(this.nppId)) {
				query = "\n select pk_seq, ten, smartid mafast from daidienkinhdoanh where npp_fk = "+this.nppId+
				"\n union " +
				"\n select pk_seq, ten, smartid mafast from daidienkinhdoanh " +
				"\n where pk_seq in (select ddkd_fk from donhang where npp_fk = "+this.nppId+")";
				this.ddkdRs = db.get(query);

				if (checkNull(ddkdId)) {
					query = "\n select pk_seq, ten, smartid mafast from khachhang " +
					"\n where pk_seq in " +
					"\n (" +
					"\n     select khachhang_fk from khachhang_tuyenbh a inner join tuyenbanhang b on a.tbh_fk = b.pk_seq " +
					"\n     where b.ddkd_fk = "+this.ddkdId+
					"\n ) ";
					this.khachhangRs = db.get(query);

					query = "\n select pk_seq from donhang where trangthai = 1 and ddkd_fk = "+this.ddkdId +" ";
					if(this.id.trim().length()<=0){
							query += "\n and pk_seq not in ( select donhang_fk from donhangtrave where donhang_fk is not null and trangthai <> 2) " ;
					}
					if (checkNull(khId)) {
						query += "\n and khachhang_fk = "+this.khId;
					}

					if (checkNull(this.tungay) && checkNull(this.denngay)) {
						query += "\n and ngaynhap >= '"+this.tungay+"'";
						query += "\n and ngaynhap <= '"+this.denngay+"'";
					}
					System.out.println("query donhangRs: " + query);
					this.donhangRs = db.get(query);

					if (checkNull(this.dhId)) {

						query = "select tonggiatri from donhang where pk_seq = "+this.dhId;
						ResultSet rs = db.get(query);
						while (rs.next()) {
							setTongtiensauVAT(rs.getString("tonggiatri"));
						}
						rs.close();

						query = "\n select ''scheme, sp.ma masp, sp.ten tensp, a.soluong, dvdl.donvi donvi, a.giamua, a.soluong*a.giamua tt " +
						"\n from donhang_sanpham a inner join sanpham sp on a.sanpham_fk = sp.pk_seq " +
						"\n inner join donvidoluong dvdl on dvdl.pk_seq = sp.dvdl_fk " +
						"\n where a.donhang_fk = "+this.dhId+
						"\n union all " +
						"\n select km.scheme, sp.ma masp, sp.ten tensp, a.soluong, dvdl.donvi donvi, 0 giamua, 0 tt " +
						"\n from donhang_ctkm_trakm a inner join sanpham sp on a.spma = sp.ma " +
						"\n inner join ctkhuyenmai km on km.pk_seq = a.ctkmid " +
						"\n inner join donvidoluong dvdl on dvdl.pk_seq = sp.dvdl_fk " +
						"\n where spma is not null and a.donhangid = "+this.dhId+
						"\n union all " +
						"\n select km.scheme, '' masp, km.diengiai tensp, 0 soluong, '' donvi, 0 giamua, -1*TONGGIATRI tt  " +
						"\n from donhang_ctkm_trakm a " +
						"\n inner join ctkhuyenmai km on km.pk_seq = a.CTKMID " +
						"\n where spma is null and a.donhangid = "+this.dhId;
						rs = db.get(query);
						System.out.println("DonhangRs: "+query);

						List<ISanpham> splist = new ArrayList<ISanpham>();
						String[] param = new String[8];
						ISanpham sp = null;	
						while (rs.next()) {
							param[0] = rs.getString("masp");
							param[1] = rs.getString("tensp");
							param[2] = rs.getString("soluong");
							param[3] = rs.getString("donvi");
							param[4] = rs.getString("giamua");
							param[5] = rs.getString("tt");
							param[6] = rs.getString("scheme");

							sp = new Sanpham(param, "1");
							splist.add(sp);
						}
						rs.close();

						if (splist != null)
							this.splist = splist;
					}
					else {
						this.splist = null;
					}
				}	
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean checkNull(String input) {
		if (input != null && input.trim().length() > 0) 
			return true;
		else
			return false;
	}

	public String toCurrencyFormat(String input) {
		String output = "";
		try {
			NumberFormat formatter = new DecimalFormat("###,###,###,###");
			output = formatter.format(Double.parseDouble(input));
		}
		catch (Exception e) {output = "0";}

		return output;
	}
	
	private String UpdateGiaTri(Idbutils db, String id) {
		String msg = "", query = "";
		
		try {
			query = "\n update a set a.TongTraHang = cte.tong " +
			"\n  from donhangtrave a inner join " +
			"\n  ( " +
			"\n select pk_seq, sum(b.soluong*b.giamua)*(1+convert(float,vat)/100) as tong " +
			"\n from donhangtrave a inner join donhangtrave_sanpham b on a.pk_seq = b.donhangtrave_fk " +
			"\n where a.pk_seq = " + id +
			"\n group by pk_seq, vat " +
			"\n  ) cte on cte.pk_seq = a.pk_seq ";
			if (!db.update(query)) {
				msg = "Lỗi cập nhật giá trị đơn.";
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			msg = "Lỗi ngoại lệ cập nhật giá trị đơn: " + e.getMessage();
		}
		
		return msg;
	}
}
