package geso.dms.distributor.beans.report.imp;

import geso.dms.distributor.beans.report.Ireport;
import geso.dms.distributor.db.sql.dbutils;
import geso.dms.distributor.util.Utility;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Report implements Ireport
{
	String userId;
    String nppId = "";
    String loainppId;
    String nppTen;
    String kenhId;
    String nhomspct;
    String dvkdId;
    String nhanhangId;
    String chungloaiId;
    String tungay;
    String denngay;
    String userTen;
    String khoId;
    String book;
    
    //bcperformance
    String st;
	String st_loc;
	String st_v;
    
    
    
    ResultSet kho;
    String msg;
    ResultSet kenh;
    ResultSet dvkd;
    ResultSet nhanhang;
    ResultSet chungloai;
    String vungId=  "";
    String khuvucId = "";
    ResultSet khuvuc;
    ResultSet vung;
    ResultSet npp;
    ResultSet loainpp;
    String vat;
    String gsbhId;
    ResultSet gsbh;
    String sanphamId;
    ResultSet sanpham;
    String dvdlId;
    String[] FieldShow;
    String[] FieldHidden;
    ResultSet dvdl;
    String khachhangId;
    ResultSet khachhang;
    String ddkdId;
    String ngayton;
    ResultSet ddkd; 
    
    String nhomskusId;
    ResultSet nhomskus;
    
    String  view = "";
    dbutils db;
    String url;
    String key;

	
	public Report()
    {
    	 this.userId="";
    	 this.nppId="";
    	 this.loainppId="";
    	 this.nppTen="";
    	 this.kenhId="";
    	 this.dvkdId="";
    	 this.nhomspct="";
    	 this.nhanhangId="";
    	 this.chungloaiId="";
    	 this.tungay="";
    	 this.denngay="";
    	 this.userTen="";
    	 this.khoId="";
    	 this.book="";
    	 this.msg="";
    	 this.vungId="";
    	 this.khuvucId="";
    	 this.vat="";
    	 this.gsbhId="";
    	 this.sanphamId="";
    	 this.dvdlId="";
    	 this.khachhangId ="";
    	 this.ddkdId ="";
    	 this.ngayton ="0";
    	 this.url="";
    	 this.view = "";
    	 this.key="_CHECK";
    	 
    	 //bcperformance
    	 this.st = "";
    	 this.st_loc ="";
    	 this.st_v  ="";
    	    
    	 
    	 
    	 
    	 this.nhomskusId = "";
    	 
	   	 this.db = new dbutils();
    }
	
	
	
	public String getNhomskusId() {
		return nhomskusId;
	}

	
	//bcperformance
	
	public String getSt() {
		return st;
	}



	public void setSt(String st) {
		this.st = st;
	}



	public String getSt_loc() {
		return st_loc;
	}



	public void setSt_loc(String st_loc) {
		this.st_loc = st_loc;
	}



	public String getSt_v() {
		return st_v;
	}



	public void setSt_v(String st_v) {
		this.st_v = st_v;
	}



	public void setNhomskusId(String nhomskusId) {
		this.nhomskusId = nhomskusId;
	}

	public ResultSet getNhomskus() {
		return nhomskus;
	}

	public void setNhomskus(ResultSet nhomskus) {
		this.nhomskus = nhomskus;
	}

	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	 public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}
	
	public void setuserId(String userId) {
		
	    this.userId = userId;
	}

	
	public String getuserId() {
		
		return this.userId;
	}

	
	public void setnppId(String nppId) {
		
		this.nppId = nppId;
	}

	
	public String getnppId() {
		
		return this.nppId;
	}

	
	public void setnppTen(String nppTen) {
		
		this.nppTen = nppTen;
	}

	
	public String getnppTen() {
		
		return this.nppTen;
	}

	
	public void setkenhId(String kenhId) {
		
		this.kenhId = kenhId;
	}

	
	public String getkenhId() {
		
		return this.kenhId;
	}
	
public void setnhomspct(String nhomspct) {
		
		this.nhomspct = nhomspct;
	}

	
	public String getnhomspct() {
		
		return this.nhomspct;
	}

	
	public void setdvkdId(String dvkdId) {
		
		this.dvkdId = dvkdId;
	}

	
	public String getdvkdId() {
		
		return this.dvkdId;
	}

	
	public void setnhanhangId(String nhanhangId) {
		
		this.nhanhangId = nhanhangId;
	}

	
	public String getnhanhangId() {
		
		return this.nhanhangId;
	}

	
	public void setchungloaiId(String chungloaiId) {
		
		this.chungloaiId = chungloaiId;
	}

	
	public String getchungloaiId() {
		
		return this.chungloaiId;
	}

	
	public void setkenh(ResultSet kenh) {
		
		this.kenh = kenh;
	}

	
	public ResultSet getkenh() {
		
		return this.kenh;
	}

	
	public void setdvkd(ResultSet dvkd) {
		
		this.dvkd = dvkd;
	}

	
	public ResultSet getdvkd() {
		
		return this.dvkd;
	}

	
	public void setnhanhang(ResultSet nhanhang) {
		
		this.nhanhang = nhanhang;
	}

	
	public ResultSet getnhanhang() {
		
		return this.nhanhang;
	}

	
	public void setchungloai(ResultSet chungloai) {
		
		this.chungloai = chungloai;
	}

	
	public void setuserTen(String userTen) {
		
		this.userTen = userTen;
	}

	
	public String getuserTen() {
		
		return this.userTen;
	}

	
	public void settungay(String tungay) {
		
		this.tungay = tungay;
	}

	
	public String gettungay() {
		
		return this.tungay;
	}

	
	public void setdenngay(String denngay) {
		
		this.denngay = denngay;
	}

	
	public String getdenngay() {
		
		return this.denngay;
	}

	

	
	public String getMsg() {
		
		return null;
	}
	ResultSet logBaoCaoRs ;
	public void init_don_hang_ban_trong_ky_queue(String baocaoId)
	{
		init();
		String query = " select [trangthai],[userId],[_link],_fileName,[_path],cot_bat_dau,[thoidiem], isnull(thongbao,'')thongbao  from [BaoCao_log] where userId = "+this.userId+" baocao_fk =   " + baocaoId + " order by thoidiem desc ";
		logBaoCaoRs = db.get(query); 
	}
	public ResultSet getLogBaoCaoRs() {
		return logBaoCaoRs;
	}
	
	
	public void init() 
	{

		if(this.nppId.length()==0){
			Utility Ult = new Utility();
			this.nppId = Ult.getIdNhapp(this.userId, db);
		}
		
		this.vung = db.get("select pk_seq,ten,diengiai from vung ");
				
		if(this.vungId.trim().length() > 0){
			this.khuvuc = db.get("select pk_seq,ten from khuvuc where vung_fk = "+ this.vungId +" ");
		}else{
			this.khuvuc = db.get("select pk_seq,ten from khuvuc");
		}
		
		String sql = "select * from daidienkinhdoanh where 1 = 1";
		this.ddkd = db.get(sql);
		
		sql = " select top(100)* from khachhang where trangthai=1  ";
		this.khachhang = db.get(sql);
		if(this.nppId.length() > 0)
		{
			sql = "select * from daidienkinhdoanh where npp_fk ='"+ this.nppId +"'";
			this.ddkd = db.get(sql);
			
			if(this.ddkdId.length()>0)
			sql = " select * from khachhang where  trangthai=1  and pk_seq in (select khachhang_fk from KHACHHANG_TUYENBH where tbh_fk in (select pk_seq from tuyenbanhang where npp_fk ='"+ this.nppId +"' and ddkd_fk ='"+ this.ddkdId +"')) ";
			else
				sql = " select * from khachhang where trangthai=1 and  pk_seq in (select khachhang_fk from KHACHHANG_TUYENBH where tbh_fk in (select pk_seq from tuyenbanhang where npp_fk ='"+ this.nppId +"')) ";
			this.khachhang = db.get(sql);
		}	
		
		String query = "\n select a.pk_seq as nppId, a.ten as nppTen, 'Khu vuc ' + b.ten as kvTen " +
						"\n from nhaphanphoi a inner join khuvuc b on a.khuvuc_fk = b.pk_seq " +
						"\n where a.trangthai = '1' and a.sitecode = a.convsitecode ";
		if(this.khuvucId.trim().length() >0)
			query += " and a.KHUVUC_FK = "+ this.khuvucId +" ";
		if(this.vungId.trim().length() >0)
			query += " and a.KHUVUC_FK in ( select PK_SEQ from KHUVUC where VUNG_FK =  "+ this.vungId +") ";
				
		String command = "select phanloai from nhanvien where pk_seq = '"+this.userId+"' ";
		
		ResultSet k = db.get(command);
		
		String phanloai = "1";
		try {
			k.next();
			
			phanloai =  k.getString("phanloai");
			k.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(!phanloai.equals("1"))
		{
			query = query + " and a.pk_seq in (select npp_fk from phamvihoatdong where nhanvien_fk =  '"+this.userId+"' ) ";
		}
		if (view != null && !view.equals("TT") && nppId != null && nppId.length() > 0) {
			query += "\n and a.pk_seq = "+nppId;
		}
		System.out.println("nppRs: " + query);
		this.npp = db.get(query);
	
		query = " select pk_Seq,ten  from LoaiNPP ";
		this.loainpp = db.get(query);
		
		query = "select pk_seq, ten from kenhbanhang";
		this.kenh = db.get(query);
		
		geso.dms.center.util.Utility util = new geso.dms.center.util.Utility();
		String querySkus = "\n select a.pk_seq, a.ten, a.ma, a.trangthai,"
				+ "\n a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua from nhomskus a, nhanvien b,"
				+ "\n nhanvien c where a.nguoitao = b.PK_SEQ and " + " a.nguoisua = c.PK_SEQ ";
		querySkus += "\n and a.pk_seq in " + util.quyen_nhomskus(this.userId);
		querySkus += "\n order by pk_seq ";
		this.nhomskus = db.get(querySkus);
	}

	
	
	public void initPerformance()
	{
		
		
		this.vung = db.get("select pk_seq,ten,diengiai from vung ");
				
		if(this.vungId.trim().length() > 0){
			this.khuvuc = db.get("select pk_seq,ten from khuvuc where vung_fk = "+ this.vungId +" ");
		}else{
			this.khuvuc = db.get("select pk_seq,ten from khuvuc");
		}
		
		String sql = "select * from daidienkinhdoanh where 1 = 1";
		this.ddkd = db.get(sql);
		
		sql = " select top(100)* from khachhang where trangthai=1  ";
		this.khachhang = db.get(sql);
		if(this.nppId.length() > 0)
		{
			sql = "select * from daidienkinhdoanh where npp_fk ='"+ this.nppId +"'";
			this.ddkd = db.get(sql);
			
			if(this.ddkdId.length()>0)
			sql = " select * from khachhang where  trangthai=1  and pk_seq in (select khachhang_fk from KHACHHANG_TUYENBH where tbh_fk in (select pk_seq from tuyenbanhang where npp_fk ='"+ this.nppId +"' and ddkd_fk ='"+ this.ddkdId +"')) ";
			else
				sql = " select * from khachhang where trangthai=1 and  pk_seq in (select khachhang_fk from KHACHHANG_TUYENBH where tbh_fk in (select pk_seq from tuyenbanhang where npp_fk ='"+ this.nppId +"')) ";
			this.khachhang = db.get(sql);
		}	
		
		String query = "\n select a.pk_seq as nppId, a.ten as nppTen, 'Khu vuc ' + b.ten as kvTen " +
						"\n from nhaphanphoi a inner join khuvuc b on a.khuvuc_fk = b.pk_seq " +
						"\n where a.trangthai = '1' and a.sitecode = a.convsitecode ";
		if(this.khuvucId.trim().length() >0)
			query += " and a.KHUVUC_FK = "+ this.khuvucId +" ";
		if(this.vungId.trim().length() >0)
			query += " and a.KHUVUC_FK in ( select PK_SEQ from KHUVUC where VUNG_FK =  "+ this.vungId +") ";
				
		String command = "select phanloai from nhanvien where pk_seq = '"+this.userId+"' ";
		
		ResultSet k = db.get(command);
		
		String phanloai = "1";
		try {
			k.next();
			
			phanloai =  k.getString("phanloai");
			k.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(!phanloai.equals("1"))
		{
			query = query + " and a.pk_seq in (select npp_fk from phamvihoatdong where nhanvien_fk =  '"+this.userId+"' ) ";
		}
		if (view != null && !view.equals("TT") && nppId != null && nppId.length() > 0) {
			query += "\n and a.pk_seq = "+nppId;
		}
		System.out.println("nppRs: " + query);
		this.npp = db.get(query);
	
		query = " select pk_Seq,ten  from LoaiNPP ";
		this.loainpp = db.get(query);
		
		query = "select pk_seq, ten from kenhbanhang";
		this.kenh = db.get(query);
		
		geso.dms.center.util.Utility util = new geso.dms.center.util.Utility();
		String querySkus = "\n select a.pk_seq, a.ten, a.ma, a.trangthai,"
				+ "\n a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua from nhomskus a, nhanvien b,"
				+ "\n nhanvien c where a.nguoitao = b.PK_SEQ and " + " a.nguoisua = c.PK_SEQ ";
		querySkus += "\n and a.pk_seq in " + util.quyen_nhomskus(this.userId);
		querySkus += "\n order by pk_seq ";
		this.nhomskus = db.get(querySkus);
		
	}
	
	public void setkhachhangId(String khachhangId) {
		
		this.khachhangId = khachhangId;
	}

	
	public String getkhachhangId() {
		
		return this.khachhangId;
	}

	
	public void setkhachhang(ResultSet khachhang) {
		
		this.khachhang = khachhang;
	}

	
	public ResultSet getkhachhang() {
		
		return this.khachhang;
	}

	
	public void setMsg(String msg) {
		
		
	}

	
	public void setddkdId(String ddkdId) {
		
		this.ddkdId = ddkdId;
	}

	
	public String getddkdId() {
		
		return this.ddkdId;
	}

	
	public void setddkd(ResultSet ddkd) {
		
		this.ddkd = ddkd;
	}

	
	public ResultSet getddkd() {
		
		return this.ddkd;
	}
	public void setFieldShow(String[] fieldShow) {
		
		this.FieldShow  =fieldShow;
	}

	
	public String[] getFieldShow() {
		
		return this.FieldShow;
	}

	
	public void setFieldHidden(String[] fieldHidden) {
		
		this.FieldHidden = fieldHidden;
	}

	
	public String[] getFieldHidden() {
		
		return this.FieldHidden;
	}

	
	public String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
         Date date = new Date();
         return dateFormat.format(date);
	}

	
	public void setngayton(String ngayton) 
	{
		this.ngayton = ngayton;
	}

	public String getngayton()
	{
		return this.ngayton;
	}

	public void setNppRs(ResultSet nppRs) 
	{
		this.npp = nppRs;
	}

	public ResultSet getNppRs() 
	{
		return this.npp;
	}

	public void setLoaiNppRs(ResultSet nppRs) {
		this.loainpp = nppRs;
	}

	public ResultSet getLoaiNppRs() {
		return this.loainpp;
	}

	public void setLoaiNppId(String loainppId) {
		this.loainppId=loainppId;
	}

	public String getLoaiNppId() {
		return this.loainppId;
	}
	
	public void setvungId(String vungId) {

		this.vungId = vungId;
	}

	public String getvungId() {

		return this.vungId;
	}

	public void setvung(ResultSet vung) {

		this.vung = vung;
	}

	public ResultSet getvung() {

		return this.vung;
	}
	

	public void setkhuvucId(String khuvucId) {

		this.khuvucId = khuvucId;
	}

	public String getkhuvucId() {

		return this.khuvucId;
	}

	public void setkhuvuc(ResultSet khuvuc) {

		this.khuvuc = khuvuc;
	}

	public ResultSet getkhuvuc() {

		return this.khuvuc;
	}
	

	public void setView(String view) {

		this.view = view;
	}

	public String getView() {

		return this.view;
	}
	public dbutils getDb() {
		return db;
	}
	
	
	
}
