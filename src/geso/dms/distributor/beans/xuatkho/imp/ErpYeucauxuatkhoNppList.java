package geso.dms.distributor.beans.xuatkho.imp;

import java.sql.ResultSet;
import geso.dms.distributor.beans.xuatkho.IErpYeucauxuatkhoNppList;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.IPhanTrang;
import geso.dms.center.util.PhanTrang;
import geso.dms.center.util.Phan_Trang;
import geso.dms.center.util.Utility;

public class ErpYeucauxuatkhoNppList extends Phan_Trang implements IErpYeucauxuatkhoNppList
{
	private static final long serialVersionUID = 1L;
	String userId;
	String tungay;
	String denngay;
	String trangthai;

	String khTen;
	String msg;
	
	ResultSet khRs;
	ResultSet DondathangRs;
	
	String loaidonhang;
	
	private int num;
	private int[] listPages;
	private int currentPages;
	
	String nppId;
	String nppTen;
	String sitecode;
	
	dbutils db;
	
	public ErpYeucauxuatkhoNppList()
	{
		this.tungay = "";
		this.denngay = "";
		this.khTen = "";
		this.trangthai = "";
		this.msg = "";
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
		this.getNppInfo();
		
		String query = "";
		Utility util = new Utility();
		if(search.length() > 0)
			query = search;
		else
		{
			query = "select a.PK_SEQ, a.trangthai, a.ngayyeucau, isnull(c.ten, d.ten) as nppTEN, b.ten as khoTEN, NV.TEN as nguoitao, b.ten as khonhan, a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua, " +
					"	 (	Select cast(YCXK1.DDH_FK as varchar(10)) + ',' AS [text()]  " +
					"		From ERP_YCXUATKHONPP_DDH YCXK1   " +
					"		Where YCXK1.ycxk_fk = a.pk_seq  " +
					"		For XML PATH ('') )  as ddhIds    " +
					"from ERP_YCXUATKHONPP a inner join KHO b on a.kho_fk = b.pk_seq " +
					"	left join NHAPHANPHOI c on a.NPP_DAT_FK = c.pk_seq " +
					"	left join KHACHHANG d on a.khachhang_fk = d.pk_seq  " +
					"	inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ   " +
					"	inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ where a.npp_fk = '" + this.nppId + "' and a.kho_fk in "+ util.quyen_kho(this.userId); 
		} 
		
		System.out.println("___CHUYEN KHO: " + query);
		this.DondathangRs = createSplittingData(50, 10, "ngayyeucau desc, trangthai asc ", query);
		
		this.khRs = db.get(" select PK_SEQ, maFAST + ' - ' + TEN as TEN from NHAPHANPHOI where TRANGTHAI = '1'   and loaiNPP = '4' and TRUCTHUOC_FK='"+this.nppId+"' union all select PK_SEQ, maFAST + ' - ' + TEN as TEN from KHACHHANG where TRANGTHAI = '1' and NPP_FK='"+this.nppId+"'");
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

	
	public String getKhTen() {
		
		return this.khTen;
	}

	
	public void setKhTen(String khTen) {
		
		this.khTen = khTen;
	}

	
	public ResultSet getKhRs() {
		
		return this.khRs;
	}

	
	public void setKhRs(ResultSet khRs) {
		
		this.khRs = khRs;
	}

	
	public String getLoaidonhang() {

		return this.loaidonhang;
	}


	public void setLoaidonhang(String loaidonhang) {
		
		this.loaidonhang = loaidonhang;
	}
	
	public String getNppId() {
		
		return this.nppId;
	}

	
	public void setNppId(String khId) {
		
		this.nppId = khId;
	}
	

	public String getNppTen() {

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
