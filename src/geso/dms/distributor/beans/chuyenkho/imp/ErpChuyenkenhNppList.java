package geso.dms.distributor.beans.chuyenkho.imp;

import java.sql.ResultSet;

import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.IPhanTrang;
import geso.dms.center.util.PhanTrang;
import geso.dms.center.util.Phan_Trang;
import geso.dms.distributor.beans.chuyenkho.IErpChuyenkenhNppList;

public class ErpChuyenkenhNppList extends Phan_Trang implements IErpChuyenkenhNppList
{
	private static final long serialVersionUID = 1L;
	String userId;
	String tungay;
	String denngay;
	String trangthai;
	String ctid;
	String sophieu;
	String lydo;
	String msg;
	
	ResultSet nhapkhoRs;
	ResultSet chungtuRs;

	String nppId;
	String nppTen;
	String sitecode;
	
	String type;
	
	private int num;
	private int[] listPages;
	private int currentPages;
	
	dbutils db;
	
	public ErpChuyenkenhNppList()
	{
		this.tungay = "";
		this.denngay = "";
		this.trangthai = "";
		this.sophieu="";
		this.lydo = "";
		this.msg = "";
		this.ctid="";
		this.type = "chuyenkenh";
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
        
		if(search.length() > 0)
			query = search;
		else
		{
			query = "select a.PK_SEQ, a.trangthai, a.ngaychuyen, a.ghichu as lydo, NV.TEN as nguoitao, b.ten as khoxuat, c.diengiai as kbhTEN, d.diengiai as kbhNHANTEN, a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua, isnull(a.loaichuyen, 'chuyenkenh') as loaichuyen   " +
					"from ERP_CHUYENKENH a inner join KHO b on a.khoxuat_fk = b.pk_seq " +
					"	inner join KENHBANHANG c on a.kbh_fk = c.pk_seq inner join KENHBANHANG d on a.kbh_nhan_fk = d.pk_seq  " +
					"inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ   " +
					"inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ where a.npp_fk = '" + this.nppId + "' and loaichuyen = N'" + this.type + "' ";
		} 
			
		System.out.println("___CHUYEN KHO: " + query);
		this.nhapkhoRs = createSplittingData(50, 10, "ngaychuyen desc, trangthai asc ", query);
		
		//this.chungtuRs = db.get("select a.PK_SEQ from ERP_CHUYENKHO a ");
	
	}
	
	public void DBclose() 
	{
		this.db.shutDown();
	}

	public String getSophieu()
	{
		return sophieu;
	}

	public void setSophieu(String sophieu) 
	{
		this.sophieu = sophieu;
	}

	public String getLydo() 
	{
		return lydo;
	}

	public void setLydo(String lydo) 
	{
		this.lydo = lydo;
	}

	public String getTungayTao() 
	{
		return this.tungay;
	}

	public void setTungayTao(String tungay) 
	{
		this.tungay =tungay;	
	}

	public String getDenngayTao() 
	{
		return this.denngay;
	}

	public void setDenngayTao(String denngay) 
	{
		this.denngay = denngay;
	}

	public ResultSet getNhapkhoRs() 
	{
		return this.nhapkhoRs;
	}

	public void setNhapkhoRs(ResultSet nkRs) 
	{
		this.nhapkhoRs = nkRs;
	}



	public void setChungtuRs(ResultSet chtuRs) {
		this.chungtuRs = chtuRs;
		
	}

	
	public ResultSet getChungtuRs() {
		
		return this.chungtuRs;
	}



	public void setctId(String ctId) {
		this.ctid = ctId;
		
	}


	public String getctId() {
		
		return this.ctid;
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

	
	public String getType() 
	{
		return this.type;
	}

	public void setType(String type) 
	{
		this.type = type;
	}

}
