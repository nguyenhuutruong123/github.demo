package geso.dms.distributor.beans.dondathang.imp;

import java.sql.ResultSet;

import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.IPhanTrang;
import geso.dms.center.util.PhanTrang;
import geso.dms.center.util.Phan_Trang;
import geso.dms.center.util.Utility;
import geso.dms.distributor.beans.dondathang.IErpDondathangDoitacList;

public class ErpDondathangDoitacList extends Phan_Trang implements IErpDondathangDoitacList
{
	private static final long serialVersionUID = 1L;
	String userId;
	String tungay;
	String denngay;
	String trangthai;

	String msg;
	
	ResultSet DondathangRs;
	
	String loaidonhang;
	String sodonhang;
	
	String nppId;
	String nppTen;
	String sitecode;
	
	String khTen;
	ResultSet khRs;
	String maddh;
	public String getMaddh() {
		return maddh;
	}

	public void setMaddh(String maddh) {
		this.maddh = maddh;
	}
	private int num;
	private int[] listPages;
	private int currentPages;
	
	dbutils db;
	
	public ErpDondathangDoitacList()
	{
		this.tungay = "";
		this.denngay = "";
		this.nppTen = "";
		this.trangthai = "";
		this.msg = "";
		this.loaidonhang = "0";
		this.sodonhang = "";
		this.khTen = "";
		currentPages = 1;
		num = 1;
		maddh="";
		this.iskm="";
		
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
		this.getNppInfo();
		
		if(search.length() > 0)
			query = search;
		else
		{
			query = "\n select  isnull(a.isdhkhac,0)isdhkhac ,isnull(a.ngaygiochot,a.ngaydonhang) as ngaygiochot,a.PK_SEQ, a.trangthai, a.ngaydonhang, c.ten as nppTEN, b.ten as khoTEN, NV.TEN as nguoitao, ISNULL(cast(a.from_dondathang as nvarchar),'')as maddh,b.ten as khonhan, a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua, a.NPP_DACHOT," +
					"\n		case a.KBH_FK when 100052 then N'ETC' when 100025 then N'OTC' end as KenhBanHang,isnull(a.iskm,0) as iskm  " +
					"\n from ERP_DondathangNPP a inner join KHO b on a.kho_fk = b.pk_seq inner join NHAPHANPHOI c on a.NPP_DAT_FK = c.pk_seq  " +
					"\n inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ   " +
					"\n inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ" +
					"\n where a.trangthai !=3 and  a.pk_seq > 0  and a.NPP_FK = '" + this.nppId + "'  and a.kho_fk in "+ util.quyen_kho(this.userId);
		} 
		
		query += " and a.loaidonhang = '" + this.loaidonhang + "' ";
		
		System.out.println("___CHUYEN KHO: " + query);
		this.DondathangRs = createSplittingData(50, 10, "ngaydonhang desc, trangthai asc ", query);
		
		query = 
				"	select PK_SEQ,  isnull(ma, '') + ', ' + TEN as TEN  " +
				"	from NHAPHANPHOI where TRANGTHAI = '1' and tructhuoc_fk = '" + this.nppId + "' ";
		this.khRs = db.get(query);

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


	public String getLoaidonhang() {

		return this.loaidonhang;
	}


	public void setLoaidonhang(String loaidonhang) {
		
		this.loaidonhang = loaidonhang;
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
	
	private void getNppInfo()
	{		
		//Phien ban moi
		geso.dms.distributor.util.Utility util=new geso.dms.distributor.util.Utility();
		this.nppId=util.getIdNhapp(this.userId);
		this.nppTen=util.getTenNhaPP();
		//this.dangnhap = util.getDangNhap();
		this.sitecode=util.getSitecode();
	}


	public String getSodonhang() {

		return this.sodonhang;
	}

	public void setSodonhang(String sodonhang) {
		
		this.sodonhang = sodonhang;
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
	

	String iskm;
	public String getIsKm()
  {
  	return iskm;
  }

	public void setIsKm(String iskm)
  {
  	this.iskm = iskm;
  }
	

}
