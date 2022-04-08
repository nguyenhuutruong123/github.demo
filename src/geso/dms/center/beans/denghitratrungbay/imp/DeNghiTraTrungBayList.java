package geso.dms.center.beans.denghitratrungbay.imp;

import geso.dms.center.beans.denghitratrungbay.IDeNghiTraTrungBayList;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.IPhanTrang;
import geso.dms.center.util.PhanTrang;
import geso.dms.center.util.Phan_Trang;
import geso.dms.center.util.Utility;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DeNghiTraTrungBayList extends Phan_Trang implements IDeNghiTraTrungBayList
{
	private static final long serialVersionUID = 1L;
	String userId;
	String tungay;
	String denngay;
	String trangthai;
	String ctId;
	String nppTen;
	String msg;
	
	ResultSet nppRs;
	ResultSet DondathangRs;
	ResultSet chungTuRs;
	
	String loaidonhang;
	
	private int num;
	private int[] listPages;
	private int currentPages;
	
	dbutils db;
	
	public DeNghiTraTrungBayList()
	{
		this.tungay = "";
		this.denngay = "";
		this.nppTen = "";
		this.trangthai = "";
		this.msg = "";
		this.ctId ="";
		this.loaidonhang = "0";
		currentPages = 1;
		num = 1;
		
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
	
	public String getTrangthai()
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}
	
	public int getNum()
	{
		return this.num;
	}
	
	public void setNum(int num)
	{
		this.num = num;
		listPages = PhanTrang.getListPages(num);
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
		ResultSet rs = db.get("select count(*) as c from ERP_YEUCAUNGUYENLIEU");
		return PhanTrang.getLastPage(rs);
	}

	public int[] getNewPagesList(String action, int num, int currentPage, int theLastPage, String[] listPage)
	{
		IPhanTrang pt = new PhanTrang();
		return pt.getNewPagesList(action, num, currentPage, theLastPage, listPage);
	}

	public String getMsg() 
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}

	public void init(String search)
	{
		String query = "";
		
		Utility util = new Utility();
  
		if(search.length() > 0)
			query = search;
		else
		{
			query = 
				"	select a.PK_SEQ as dnId,a.NGAYDENGHI,a.TRANGTHAI ,b.SCHEME,b.DIENGIAI,c.MA as nppMa,c.TEN as nppTen,a.LANTHANHTOAN,a.NGAYSUA,d.TEN as nsua  "+
				"	from DENGHITRATRUNGBAY a inner join CTTRUNGBAY b on  b.PK_SEQ=a.CTTRUNGBAY_FK "+
				"		inner join NHAPHANPHOI c on c.PK_SEQ=a.NPP_FK "+
				"		inner join NHANVIEN d on d.PK_SEQ= a.NGUOISUA " +
				" where 1=1 ";
		} 
		query += " and c.pk_seq in "+util.quyen_npp(userId)+"";
		System.out.println("___CHUYEN KHO: " + query);
		this.DondathangRs = createSplittingData(50, 10, " SCHEME , NGAYDENGHI desc, trangthai asc ", query);
		this.nppRs = db.get("select PK_SEQ, MA + ' - ' + TEN as TEN from NHAPHANPHOI where TRANGTHAI = '1' " );
		
		this.chungTuRs =db.get("select pk_seq,scheme,diengiai from cttrungbay ");
	}
	
	public void DBclose() 
	{
		this.db.shutDown();
	}

	public ResultSet getDondathangRs() 
	{
		return this.DondathangRs;
	}

	public void setDondathangRs(ResultSet nkRs) 
	{
		this.DondathangRs = nkRs;
	}

	
	public String getTungay() {
		
		return this.tungay;
	}

	
	public void setTungay(String tungay) {
		
		this.tungay = tungay;
	}

	
	public String getDenngay() {
		
		return this.denngay;
	}

	
	public void setDenngay(String denngay) {
		
		this.denngay = denngay;
	}

	
	public String getNppTen() {
		
		return this.nppTen;
	}

	
	public void setNppTen(String nppTen) {
		
		this.nppTen = nppTen;
	}

	
	public ResultSet getNppRs() {
		
		return this.nppRs;
	}

	
	public void setNppRs(ResultSet nppRs) {
		
		this.nppRs = nppRs;
	}

	
	public String getLoaidonhang() {

		return this.loaidonhang;
	}


	public void setLoaidonhang(String loaidonhang) {
		
		this.loaidonhang = loaidonhang;
	}


	public ResultSet getChungtuRs() {
	
		return this.chungTuRs;
	}


	public void setChungtuRs(ResultSet chungtuRs) {
		this.chungTuRs = chungtuRs;
	}

	
	public String getctId() {
	
		return this.ctId;
	}

	
	public void setctId(String ctId) {
		this.ctId = ctId;
		
	}
	
	String phanloai;

	public String getPhanloai()
  {
  	return phanloai;
  }

	public void setPhanloai(String phanloai)
  {
  	this.phanloai = phanloai;
  }

}
