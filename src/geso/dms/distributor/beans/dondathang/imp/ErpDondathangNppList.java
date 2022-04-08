package geso.dms.distributor.beans.dondathang.imp;

import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.IPhanTrang;
import geso.dms.center.util.PhanTrang;
import geso.dms.center.util.Phan_Trang;
import geso.dms.distributor.beans.dondathang.IErpDondathangNppList;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ErpDondathangNppList extends Phan_Trang implements IErpDondathangNppList
{
	private static final long serialVersionUID = 1L;
	String userId;
	String tungay;
	String denngay;
	String trangthai;

	String msg;
	
	ResultSet DondathangRs;
	
	String loaidonhang;
	
	String nppId;
	String nppTen;
	String sitecode;
	
	private int num;
	private int[] listPages;
	private int currentPages;
	
	private List<Object> DataSearch = new ArrayList<Object>();
	
	dbutils db;
	
	public ErpDondathangNppList()
	{
		this.tungay = "";
		this.denngay = "";
		this.nppTen = "";
		this.trangthai = "";
		this.msg = "";
		this.loaidonhang = "0";
		currentPages = 1;
		num = 1;
		
		this.db = new dbutils();
	}
	
	public List<Object> getDataSearch() {
		return DataSearch;
	}

	public dbutils getDb() {
		return db;
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
		this.getNppInfo();
		
		if(search.length() > 0)
			query = search;
		else
		{
			query = 
					"\n select a.PK_SEQ, a.trangthai, a.ngaydonhang, c.ten as nppTEN, isnull(b.ten, d.ten) + '-' + isnull(b.diengiai, d.diengiai) as khoTEN, NV.TEN as nguoitao, isnull(b.ten, d.ten) + '-' + isnull(b.diengiai, d.diengiai) as khonhan, a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua, a.NPP_DACHOT ,  " +
					"\n    	(	SELECT COUNT(*) "+   
					"\n    		FROM ERP_YCXUATKHO_DDH "+   
					"\n    			INNER JOIN ERP_YCXUATKHO ON ERP_YCXUATKHO_DDH.ycxk_fk=ERP_YCXUATKHO.PK_SEQ "+   
					"\n    		WHERE ERP_YCXUATKHO_DDH.ddh_fk=a.PK_SEQ AND ERP_YCXUATKHO.TRANGTHAI=2 "+   
					"\n    	) as DaXuatKho,a.TT_DuyetLai "+
					"\n from ERP_Dondathang a left join KHOTT b on a.kho_fk = b.pk_seq inner join NHAPHANPHOI c on a.NPP_FK = c.pk_seq  " +
					"\n left join kho d on a.kho_fk = d.pk_seq "+
					"\n inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ   " +
					"\n inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ where a.pk_seq > 0  and a.NPP_FK = '" + this.nppId + "' ";
		} 
		
		query += "\n  and a.loaidonhang = ? ";
		
		this.DataSearch.add(this.loaidonhang);
		
		System.out.println("___CHUYEN KHO: " + query);
		this.DondathangRs = super.createSplittingData_v2(db, 50, 10, "ngaydonhang desc, trangthai asc ", query, DataSearch, "");
		//this.DondathangRs = createSplittingData(50, 10, "ngaydonhang desc, trangthai asc ", query);

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
	
}
