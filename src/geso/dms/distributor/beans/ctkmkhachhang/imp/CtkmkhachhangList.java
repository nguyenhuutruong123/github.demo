package geso.dms.distributor.beans.ctkmkhachhang.imp;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.rmi.CORBA.Util;
import javax.servlet.http.HttpServletRequest;

import geso.dms.center.util.IPhanTrang;
import geso.dms.center.util.PhanTrang;
import geso.dms.center.util.Phan_Trang;
import geso.dms.distributor.util.Utility;
import geso.dms.distributor.beans.ctkmkhachhang.ICtkmkhachhangList;
import geso.dms.distributor.db.sql.dbutils;

public class CtkmkhachhangList extends Phan_Trang implements ICtkmkhachhangList, Serializable{
    String userId;
    String Diengiai;
    ResultSet Dskm;
    String Tungay;
    String Denngay;
    String Trangthai;
    dbutils db ;
    String TenNpp;
	private ResultSet ddkd;
	private String ddkdId;
	private String maKH;
	private String tenKH;
	private int num;
	private int[] listPages;
	private int currentPages;
	private HttpServletRequest request;
    public CtkmkhachhangList()
    {
    	this.Diengiai ="";
    	this.Tungay ="";
    	this.Denngay ="";
    	this.Trangthai ="";
    	this.ddkdId ="";
    	this.maKH ="";
    	this.tenKH ="";
    	currentPages = 1;
		num = 1;
    	db = new dbutils();
    	}
    
	public HttpServletRequest getRequestObj() 
	{
		return this.request;
	}

	public void setRequestObj(HttpServletRequest request) 
	{
		this.request = request;
	}
    
	
	public void setuserId(String userId) {
		this.userId = userId;
		
	}

	
	public String getuserId() {
		
		return this.userId;
	}

	
	public void setDiengiai(String Diengiai) {
		
		this.Diengiai = Diengiai;
	}

	
	public String getDiengiai() {
		
		return this.Diengiai;
	}

	
	public void setDskm(ResultSet Dskm) {
		
		this.Dskm = Dskm;
	}

	
	public ResultSet getDskm() {
		
		return this.Dskm;
	}
	
	private String getMainSql(){
		
		getNppInfo();
		
		String st = "";
		String ddkd ="";
		String khachhang="";
			if(this.ddkdId.length() > 0)
			  st += " and f.nhomkhnpp_fk in (select nhomkhnpp_fk from KHACHHANG_TUYENBH kht inner join ctkm_khachhang khkm on khkm.khachhang_fk = kht.khachhang_fk "+
				  	" inner join tuyenbanhang tbh on kht.tbh_fk = tbh.pk_seq where tbh.ddkd_fk like '%"+this.ddkdId+"%')";
				  
		   if(this.Diengiai.length()>0)
		   {
			   st = st +" and f.scheme like '%"+ this.Diengiai +"%'";
		   }
		   if(this.Tungay.length()>0)
		   {
			   st = st + " and f.tungay >='"+ this.Tungay +"'";
			   }
		   if(this.Denngay.length()>0)
		   {
			   st = st + " and f.tungay <='"+ this.Denngay +"'";
		   }
		   
			  if(this.tenKH.length() > 0){
				  Utility util = new Utility();
				  st += " and f.nhomkhnpp_fk in (select nhomkhnpp_fk from ctkm_khachhang khkm inner join khachhang kh on khkm.khachhang_fk = kh.pk_seq where upper(dbo.ftBoDau(kh.ten)) like upper(N'%"+util.replaceAEIOU(this.tenKH)+"%'))";
				//  st += " and upper(kh.ten) like upper(N'%"+util.replaceAEIOU(this.tenKH)+"%')";//upper(a.ten) like upper(N'%" + util.replaceAEIOU(Tengsbh) + "%')";
			  }
			  if(this.maKH.length() > 0)
				  st += " and kh.pk_seq like '%"+this.maKH+"%'";
		   
		//	String sql ="select * from ctkhuyenmai where pk_seq in (select ctkm_fk from ctkm_npp where npp_fk ='"+ getNppInfo() +"')";
		String sql ="select distinct  ROW_NUMBER() OVER(ORDER BY f.pk_seq DESC) AS stt,e.diengiai,f.scheme as ctkm,f.tungay,f.denngay,f.pk_seq,e.pk_seq as ma " +
				"from nhomkhachhangnpp e inner join ctkhuyenmai f on e.PK_SEQ = f.NHOMKHNPP_FK    " +
				"where f.nhomkhnpp_fk is not null and e.pk_seq in (select b.nhomkhnpp_fk from ctkm_npp a, ctkhuyenmai b where b.nhomkhnpp_fk is not null and a.ctkm_fk = b.pk_seq and a.npp_fk = '" + getNppInfo() + "'" + st + ")";
		   return sql;
	
	}
	
	
	public void init() {
	   
		 //sql ="select distinct e.pk_seq,f.diengiai as ctkm,f.pk_seq as ma,e.diengiai,f.tungay,f.denngay from nhomkhachhangnpp e,ctkhuyenmai f  where e.pk_seq = f.nhomkhnpp_fk and e.pk_seq in (select b.nhomkhnpp_fk from ctkm_npp a,ctkhuyenmai b where a.ctkm_fk = b.pk_seq and a.npp_fk ='"+ getNppInfo() +"')";
	   
	   String query = getMainSql();
	   
   	
	   
	System.out.println("load:"+ query);
		this.Dskm = createSplittingData(50, 10, "ctkm desc", query);// createSplittingData(request, "ctkm desc", query);//db.get(query);

		createDdkdRS();
		
	}
	private String getNppInfo()
	{	
		//Phien ban moi
		geso.dms.distributor.util.Utility util=new geso.dms.distributor.util.Utility();
		String NppId= util.getIdNhapp(this.userId);
		this.TenNpp=util.getTenNhaPP();
		return NppId;
		//this.nppTen=util.getTenNhaPP();
	}
	
	
	public void setTrangthai(String Trangthai) {
		this.Trangthai = Trangthai;
		
	}
	
	public String getTrangthai() {
		
		return this.Trangthai;
	}
	
	public void setTungay(String Tungay) {
		
		this.Tungay = Tungay;
	}
	
	public String getTungay() {
		
		return this.Tungay;
	}
	
	public void setDenngay(String Denngay) {
		
		this.Denngay = Denngay;
	}
	
	public String getDenngay() {
		
		return this.Denngay;
	}
	
	public void createDdkdRS()
	{
		//this.getNppInfo();
		this.ddkd = db.get("select pk_seq as ddkdId, ten as ddkdTen from daidienkinhdoanh where npp_fk ='"+ getNppInfo() +"' order by ten ASC");
	}
	
	public ResultSet getDDKD(){
		
		return this.ddkd;
	}
	
	public String getDDKDId(){
		
		return this.ddkdId;
	}
	
	public void setDDKdId(String ddkdId){
		
		this.ddkdId = ddkdId;
	}
	
	public void setMaKH(String maKH) {
		
		this.maKH = maKH;
		
	}
	
	public String getMaKH() {
		
		return this.maKH;
	}
	
	public void setTenKH(String tenKH) {
		
		this.tenKH = tenKH;
		
	}
	
	public String getTenKH() {
		
		return this.tenKH;
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
		
		//db = new dbutils();
		String q = "select count(stt) as c from ("+getMainSql()+") sc";
		ResultSet rs = db.get(q);
		try {
			rs.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return PhanTrang.getLastPage(rs);
	}
	
	public int[] getNewPagesList(String action, int num, int currentPage, int theLastPage, String[] listPage) {
		
		IPhanTrang pt = new PhanTrang();
		return pt.getNewPagesList(action, num, currentPage, theLastPage, listPage);
	}		
	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	
	public String getTenNpp() {
		
		return this.TenNpp;
	}

	
	public void setTenNpp(String tennpp) {
		
		this.TenNpp=tennpp;
	}

	
	public void DBclose() {
		
		try {
			if(this.db != null)
				this.db.shutDown();
			if(Dskm!=null){
				Dskm.close();
			}
			if(ddkd!=null){
				ddkd.close();
			}
		} catch (Exception e) {
			
		}
	}
	
	


}
