package geso.dms.distributor.beans.banggiabandoitac.imp;

import java.sql.ResultSet;
import java.sql.Statement;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Phan_Trang;
import geso.dms.distributor.beans.banggiabandoitac.IBanggiabandoitacList;

public class BanggiabandoitacList  extends Phan_Trang implements IBanggiabandoitacList
{
	private static final long serialVersionUID = -927977546783610214L;
	
	String userId;
	String userTen;
	String ten;
	ResultSet dvkdIds;
	String dvkdId;
	ResultSet kenhIds;
	String kenhId;	
	String trangthai;
	String tungay,denngay,msg;
	
	String nppId;
	String nppTen;
	String sitecode;
	String type;

	ResultSet bglist;
	dbutils db;
	public String getNhomsp() {
		return nhomsp;
	}

	public void setNhomsp(String nhomsp) {
		this.nhomsp = nhomsp;
	}

	public ResultSet getRsnhomsp() {
		return rsnhomsp;
	}

	public void setRsnhomsp(ResultSet rsnhomsp) {
		this.rsnhomsp = rsnhomsp;
	}

	String nhomsp;
	ResultSet rsnhomsp;
	//Constructor
	public BanggiabandoitacList(String[] param)
	{
		
		this.ten = param[1];		
		this.dvkdId = param[2];
		this.kenhId = param[3];
		this.trangthai = param[4];
	
		this.tungay="";
		this.denngay="";
		this.msg="";
		this.type = "";
		this.db = new dbutils();
	}
	
	public BanggiabandoitacList()
	{
		this.ten = "";		
		this.dvkdId = "";
		this.kenhId = "";
		this.trangthai = "";
		this.tungay="";
		this.denngay="";
		this.msg="";
		this.type = "";
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

	public String getUserTen() 
	{
		return this.userTen;
	}
	
	public void setUserTen(String userTen) 
	{
		this.userTen = userTen;
		
	}
	
	public String getTen() 
	{
		return this.ten;
	}

	public void setTen(String ten) 
	{
		this.ten = ten;
	}

	
	public String getDvkdId() 
	{
		return this.dvkdId;
	}

	public void setDvkdId(String dvkdId) 
	{
		this.dvkdId = dvkdId;
	}
	
	public String getKenhId() 
	{
		return this.kenhId;
	}

	public void setKenhId(String kenhId) 
	{
		this.kenhId = kenhId;
	}

	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}


	public ResultSet getDvkd() 
	{
		return this.dvkdIds;
	}

	public void setDvkd(ResultSet dvkdIds) 
	{
		this.dvkdIds = dvkdIds;
	}

	public ResultSet getKenh() 
	{
		return this.kenhIds;
	}

	public void setKenh(ResultSet kenhIds) 
	{
		this.kenhIds = kenhIds;
	}

	public ResultSet getBglist() 
	{
		return this.bglist;
	}

	public void setBglist(ResultSet bglist) 
	{
		this.bglist = bglist;
	}

	public boolean saveNewBgblc() 
	{
		return false;
	}

	public boolean UpdateBgblc() 
	{
		return false;
	}

	
	private void createDvkdRS(){  				
		
		//this.dvkdIds  =  this.db.get("select donvikinhdoanh as dvkd, pk_seq as dvkdId from donvikinhdoanh where trangthai='1' ");;
		this.dvkdIds  =  this.db.get("select distinct a.donvikinhdoanh as dvkd, a.pk_seq as dvkdId from donvikinhdoanh a,nhacungcap_dvkd b where a.pk_seq = b.DVKD_fk and b.checked ='1' and a.trangthai ='1' order by a.donvikinhdoanh");
	}

	private void createKenhRS(){  				
		
		this.kenhIds  =  this.db.get("select diengiai as kenh, pk_seq as kenhId from kenhbanhang where trangthai='1'");
	}

	
	public void init(String search)
	{
		this.getNppInfo();
		
		String query;
		
		if (search.length() > 0)
		{
			query = search;
		}
		else
		{
			query = "select a.pk_seq as id, a.ten as ten,b.donvikinhdoanh as dvkd, c.ten as kenh,a.tungay, a.trangthai as trangthai, d.ten as nguoitao, a.ngaytao as ngaytao, a.ngaysua as ngaysua,e.ten as nguoisua,   c.pk_seq as kenhId " +
					"from BANGGIABANDOITAC a inner join donvikinhdoanh b on a.dvkd_fk = b.pk_seq " +
					"	inner join kenhbanhang c on a.kenh_fk = c.pk_seq" +
					"	inner join nhanvien d on a.nguoitao = d.pk_seq" +
					"	inner join nhanvien e on a.nguoisua = e.pk_seq " +
					"where a.npp_fk = '" + this.nppId + "' ";
		}		
		
		System.out.println("Init BG: " + query );
		this.bglist = super.createSplittingData(super.getItems(), super.getSplittings(), " id desc ", query);
		
		createDvkdRS();
		createKenhRS();
		this.rsnhomsp=db.get("select pk_seq , ten from nhomsanpham");
		System.out.println("vao day ne  select pk_seq , ten from nhomsanpham");
	}
	
	public void closeDB()
	{
		try
		{
			Statement st;
			if(dvkdIds != null) {
				st = dvkdIds.getStatement();
				st.close();
				dvkdIds.close();
			}
			
			if (kenhIds != null){
				st = kenhIds.getStatement();
				st.close();				
				kenhIds.close();
			}
			
			if (bglist != null){
				st = bglist.getStatement();
				st.close();				
				bglist.close();
			}
			
			this.db.shutDown();
		}
		catch(Exception e){}

	}
	
	public void setDenngay(String denngay) 
	{
		this.denngay = denngay;
	}
	
	public String getTungay() 
	{
		return tungay;
	}

	public void setTungay(String tungay) 
	{
		this.tungay = tungay;
	}

	public String getDenngay() 
	{
		return denngay;
	}
	
	public String getMsg() 
	{
		return msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}

	
	public String getType() {
		
		return this.type;
	}

	
	public void setType(String type) {
		
		this.type = type;
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

	public ResultSet getBgdoitacList() {
		
		return this.bglist;
	}

	public void setBgdoitacList(ResultSet bgDoitac) {
		
		this.bglist = bgDoitac;
	}

	private void getNppInfo()
	{		
		//Phien ban moi
		geso.dms.distributor.util.Utility util=new geso.dms.distributor.util.Utility();
		this.nppId=util.getIdNhapp(this.userId);
		this.nppTen=util.getTenNhaPP();
		//this.dangnhap = util.getDangNhap();
		this.sitecode=util.getSitecode();
	}
	

}

