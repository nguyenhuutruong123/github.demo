package geso.dms.center.beans.daidienkinhdoanh.imp;

import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import geso.dms.center.beans.daidienkinhdoanh.*;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.IPhanTrang;
import geso.dms.center.util.PhanTrang;
import geso.dms.center.util.Phan_Trang;
import geso.dms.center.util.Utility;
import geso.dms.center.*;


public class DaidienkinhdoanhList extends Phan_Trang implements IDaidienkinhdoanhList
{
	private static final long serialVersionUID = -9217977546733610214L;

	// Tieu chi tim kiem
	String userId;
	String ten;
	String sodienthoai;
	String trangthai;
	String smartid;
	ResultSet kenhbanhang;
	String kbhId;
	ResultSet gsbanhang;
	String gsbhId;
	String thuviec;
	ResultSet nhaphanphoi;
	String nppId;
    String Msg;
	String view = "";
	dbutils db;
	
	private int num;
	private int[] listPages;
	private int currentPages;
	private ResultSet ddkdlist;
	private HttpServletRequest request;

List<Object> dataSearch = new ArrayList<Object>(); 
	
	public List<Object> getDataSearch() {
		return dataSearch;
	}
	public void setDataSearch(List<Object> dataSearch) {
		this.dataSearch = dataSearch;
	}
	public DaidienkinhdoanhList(String[] param)
	{
		this.ten = param[1];
		this.sodienthoai = param[2];
		this.trangthai = param[3];
		this.kbhId = param[4];
		this.gsbhId = param[5];
		this.nppId = param[6];
		this.db = new dbutils();
		this.Msg = "";
		this.thuviec="";
		num = 1;
		//	listPages = new int[]{1, 2 , 3 , 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
			currentPages = 1;
		//init("");
	}
	
	public DaidienkinhdoanhList()
	{
		this.ten = "";
		this.sodienthoai = "";
		this.trangthai = "2";
		this.kbhId = "";
		this.gsbhId = "";
		this.nppId = "";
		this.Msg="";
		this.thuviec="";
		this.smartid = "";
		this.db = new dbutils();
		
	}
	
	public String getView() {
		return view;
	}
	public void setView(String view) {
		this.view = view;
	}
	public HttpServletRequest getRequestObj() 
	{
		return this.request;
	}

	public void setRequestObj(HttpServletRequest request) 
	{
		this.request = request;
	}
	
	public String getUserId() 
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;
	}
	
	public String getTen() 
	{
		return this.ten;
	}

	public void setTen(String ten) 
	{
		this.ten = ten;
	}

	public String getSodienthoai()
	{
		return this.sodienthoai;
	}

	public void setSodienthoai(String sodienthoai) 
	{
		this.sodienthoai = sodienthoai;
	}
	
	public String getTrangthai()
	{
		return this.trangthai;
	}
	
	public void setTrangthai(String trangthai)
	{
		this.trangthai = trangthai;
	}

	public ResultSet getDdkdList()
	{
		return this.ddkdlist;
	}
	
	public void setDdkdList(ResultSet ddkdlist)
	{
		this.ddkdlist = ddkdlist;
	}
	
	public ResultSet getKenhbanhang() 
	{
		return this.kenhbanhang;
	}

	public void setKenhbanhang(ResultSet kenhbanhang)
	{
		this.kenhbanhang = kenhbanhang;
	}

	public String getKbhId() 
	{
		return this.kbhId;
	}

	public void setKbhId(String kbhId) 
	{
		this.kbhId = kbhId;
	}
	
	public ResultSet getGsbanhang() 
	{
		return this.gsbanhang;
	}

	public void setGsbanhang(ResultSet gsbanhang)
	{
		this.gsbanhang = gsbanhang;
	}

	public String getGsbhId() 
	{
		return this.gsbhId;
	}

	public void setGsbhId(String gsbhId) 
	{
		this.gsbhId = gsbhId;
	}
	public ResultSet getNhaphanphoi() 
	{
		return this.nhaphanphoi;
	}

	public void setNhaphanphoi(ResultSet nhaphanphoi)
	{
		this.nhaphanphoi = nhaphanphoi;
	}

	public String getNppId() 
	{
		return this.nppId;
	}

	public void setNppId(String nppId) 
	{
		this.nppId = nppId;
	}

	private void createKbhRS()
	{  				
		this.kenhbanhang =  this.db.get("select diengiai as kbhTen, pk_seq as kbhId from kenhbanhang where trangthai='1' ");
	}
	private void createGsbhRS()
	{
		String query = "select ten+ ' ('+cast(pk_seq as varchar)+')' as gsbhTen, pk_seq as gsbhId from giamsatbanhang where trangthai = '1' order by gsbhTen";
		this.gsbanhang =  this.db.get(query); 
	}

	private void createNppRS()
	{  	
		//String query = "select ten as nppTen, pk_seq as nppId from nhaphanphoi where trangthai='1' order by nppTen";
		//this.nhaphanphoi =  this.db.get(query); 
		String query = "select a.pk_seq as nppId, a.ten as nppTen, 'Khu vuc ' + b.ten as kvTen ";
		query += "from nhaphanphoi a inner join khuvuc b on a.khuvuc_fk = b.pk_seq where a.trangthai = '1' and a.sitecode = a.convsitecode order by b.pk_seq asc";
		System.out.println("Query NPP: " + query);
		this.nhaphanphoi = this.db.get(query);
	}

	public void createDdkdBeanList(String query)
	{  	  
	//	this.ddkdlist =  createSplittingData(super.getItems(), super.getSplittings(), "id desc", query);
		this.ddkdlist = super.createSplittingData_v2(db,super.getItems(), super.getSplittings(), "id desc", query, this.dataSearch, "");
	}
	
	public void init(String search) 
	{
		String query;	
		if (search.length() == 0)
		{
			query = "\n select isnull(route.ten,'') route, isnull(a.tinhtrang,'0') as tinhtrang,a.cmnd,a.quequan,a.ngaysinh,a.ngaybatdau,a.ngayketthuc, a.pk_seq as id,a.smartid, a.ten , a.dienthoai, a.diachi, a.trangthai, a.ngaytao, "+
			"\n b.ten as nguoitao, a.ngaysua, c.ten as nguoisua, isnull(d.ten,'') as nppTen,  isnull(f.ten,'') as gsbhTen, "+
			"\n g.ten as kbhTen " +
			"\n from daidienkinhdoanh a inner join nhanvien b on a.nguoitao = b.pk_seq "+ 
			"\n inner join nhanvien c on a.nguoisua = c.pk_seq "+
			"\n left join nhaphanphoi d on a.npp_fk = d.pk_seq "+  
			"\n left join ddkd_gsbh e on a.pk_seq = e.ddkd_fk "+
			"\n left join giamsatbanhang f on e.gsbh_fk = f.pk_seq "+ 
			"\n left join kenhbanhang g on f.kbh_fk = g.pk_seq " +
			"\n left join dms_route route on route.pk_seq = a.route_fk " +
			"\n where 1=1 ";
			
			/*Utility ut = new Utility();
			query += " and d.pk_seq in "+ ut.quyen_npp(userId) + " and g.pk_seq in " + ut.quyen_kenh(userId);*/
		
		}
		else
		{
			query = search;
		}
		
		String _nppid = "";
		if (view != null && !view.equals("TT")) {
			geso.dms.distributor.util.Utility util = new geso.dms.distributor.util.Utility();
			_nppid = util.getIdNhapp(userId);
			if (_nppid != null && _nppid.length() > 0) {
				query += "\n and a.npp_fk = "+_nppid;
			}
		}
		
		System.out.println("Init List: "+query);
		createDdkdBeanList(query);
		createKbhRS();
		createGsbhRS();
		createNppRS();
	}

		public void setMsg(String Msg) {
		this.Msg = Msg;
		
	}

	
	public String getMsg() {
		return this.Msg;
	}
	public int getNum(){
		return this.num;
	}
	public void setNum(int num){
		this.num = num;
		listPages = PhanTrang.getListPages(num);

	}

	
	public int getCurrentPage() {
		return this.currentPages;
	}

	
	public void setCurrentPage(int current) {
		this.currentPages = current;
	}

	
	public int[] getListPages() {
		return this.listPages;
	}

	
	public void setListPages(int[] listPages) {
		this.listPages = listPages;
	}

	
	public int getLastPage() {
		ResultSet rs = db.get("select count(*) as c from ERP_CHUYENKHO ");
		return PhanTrang.getLastPage(rs);
	}

	
	public int[] getNewPagesList(String action, int num, int currentPage, int theLastPage, String[] listPage) {
		IPhanTrang pt = new PhanTrang();
		return pt.getNewPagesList(action, num, currentPage, theLastPage, listPage);
	}
	@Override
	public void DbClose() {
		// TODO Auto-generated method stub
	
		try{
			if(kenhbanhang!=null){
				kenhbanhang.close();
			}
			if(gsbanhang!=null){
				gsbanhang.close();
			}
			if(nhaphanphoi!=null){
				nhaphanphoi.close();
			}
			if(db!=null){
				db.shutDown();
			}
			
		}catch(Exception er){
			
		}
		
	}


	public String getThuviec() {

		return this.thuviec;
	}

	public void setThuviec(String thiec) {

		this.thuviec=thiec;
	}

	@Override
	public String getSmartId() {
		// TODO Auto-generated method stub
		return this.smartid;
	}

	@Override
	public void setSmartId(String smartid) {
		this.smartid = smartid;
		
	}



}
