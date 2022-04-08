package geso.dms.distributor.beans.chuyenkho.imp;

import java.sql.ResultSet;

import geso.dms.distributor.beans.chuyenkho.IErpChuyenkhoNppList;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.IPhanTrang;
import geso.dms.center.util.PhanTrang;
import geso.dms.center.util.Phan_Trang;
import geso.dms.center.util.Utility;

public class ErpChuyenkhoNppList extends Phan_Trang implements IErpChuyenkhoNppList
{
	private static final long serialVersionUID = 1L;
	String userId;
	String tungay;
	String denngay;
	String trangthai;

	String sophieu;
	String lydo;
	String msg;
	
	ResultSet nhapkhoRs;
	ResultSet khRs;
	String khId;
	
	String nppId;
	String nppTen;
	String sitecode;
	String sochungtu;
	
	private int num;
	private int[] listPages;
	private int currentPages;
	
	dbutils db;
	
	public ErpChuyenkhoNppList()
	{
		this.tungay = "";
		this.denngay = "";
		this.trangthai = "";
		this.sophieu="";
		this.sochungtu="";
		this.lydo = "";
		this.msg = "";
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
			query = "select a.PK_SEQ, a.trangthai,isnull( a.sochungtu,'')as SOCHUNGTU ,a.ngaychuyen, a.ghichu as lydo, NV.TEN as nguoitao, b.ten as khoxuat, isnull(c.ten,'') as nppTEN,isnull(c.PK_SEQ, 0) as nppId, a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua, isnull(a.DAIN,0) DAIN \n" +
					"from ERP_CHUYENKHONPP a inner join KHO b on a.khoxuat_fk = b.pk_seq left join NHAPHANPHOI c on a.npp_dat_fk = c.pk_seq  \n" +
					"inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ   \n" +
					"inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ where a.NPP_FK = '" + this.nppId + "' \n";
			} 
		query+= " and a.khoxuat_fk in "+ util.quyen_kho(this.userId);
		System.out.println("___CHUYEN KHO: " + query);
		
		this.nhapkhoRs = createSplittingData(50, 10, "ngaychuyen desc, SOCHUNGTU desc, trangthai asc ", query);
		this.khRs = db.get("select PK_SEQ, TEN from NHAPHANPHOI where trangthai = '1' and loaiNPP not in ( 4, 5, 6 ) and pk_seq != '" + this.nppId + "' and tructhuoc_fk = '" + this.nppId + "' union select PK_SEQ,TEN from NHAPHANPHOI where TRANGTHAI=1 and PK_SEQ in (select TRUCTHUOC_FK from NHAPHANPHOI where PK_SEQ='"+this.nppId+"') ");
		
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
		System.out.println("NPPID: "+this.nppId +" USERID: "+this.userId);
		this.nppId=util.getIdNhapp(this.userId);
		this.nppTen=util.getTenNhaPP();
		//this.dangnhap = util.getDangNhap();
		this.sitecode=util.getSitecode();
	}

	public String getSochungtu() {
		return this.sochungtu;
	}

	public void setSochungtu(String sochungtu) {
		this.sochungtu=sochungtu;
	}

	public ResultSet getKhRs() {
		return this.khRs;
	}

	public void setKhRs(ResultSet khrs) {
		this.khRs=khrs;
		
	}

	public String getKhId() {
		return this.khId;
	}

	public void setKhId(String KhId) {
		this.khId=KhId;
		
	}

	String type;
	@Override
  public String getType()
  {
	  return type;
  }

	@Override
  public void setType(String type)
  {
	  this.type=type;
  }

}
