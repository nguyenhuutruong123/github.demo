package geso.dms.distributor.beans.dontratrungbaynpp.imp;

import java.sql.ResultSet;

import geso.dms.distributor.beans.dontratrungbaynpp.IDonTraTrungBayNppList;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.IPhanTrang;
import geso.dms.center.util.PhanTrang;
import geso.dms.center.util.Phan_Trang;

public class DonTraTrungBayNppList extends Phan_Trang implements IDonTraTrungBayNppList
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

	String nppId;
	String nppTen;
	String sitecode;
	
	private int num;
	private int[] listPages;
	private int currentPages;
	
	dbutils db;
	
	//Tim kiem theo makh, tenkh
	String makh, tenkh;
	ResultSet khRs;
	//.
	
	public DonTraTrungBayNppList()
	{
		this.tungay = "";
		this.denngay = "";
		this.trangthai = "";
		this.sophieu="";
		this.lydo = "";
		this.msg = "";
		this.makh = "";
		this.tenkh = "";
		currentPages = 1;
		num = 1;
		
		this.db = new dbutils();
	}
	
	//Them makh, tenkh
	public String getMaKh()
	{
		return this.makh;
	}

	public void setMaKh(String makh) 
	{
		this.makh = makh;
	}
	
	public String getTenKh()
	{
		return this.tenkh;
	}

	public void setTenKh(String tenkh) 
	{
		this.tenkh = tenkh;
	}
	
	public ResultSet getKhRs()
	{
		return this.khRs;
	}

	public void setKhRs(ResultSet khRs) 
	{
		this.khRs = khRs;
	}
	//.
	
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
			query =
		"	select 0 donhangId , 0 sonetId,a.PK_SEQ, a.trangthai, a.ngaytra, a.ghichu as lydo, NV.TEN as nguoitao, b.ten as khoxuat,   "+
		"			c.ten as nppTEN, a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua,kbh.TEN  as kbhTEN " +
		"		, ct.diengiai + ' - ' + dn.diengiai tenCt  "+				
		"		from DonTraTrungBayNPP a inner join KHO b on a.kho_fk = b.pk_seq left join NHAPHANPHOI c on a.npp_fk = c.pk_seq  " +
		"    	inner join denghitratrungbay dn on dn.pk_seq = a.denghitratrungbay_fk" +
		"		inner join cttrungbay ct on ct.pk_seq = dn.cttrungbay_fk  "+
		"			inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ      "+
		"			inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ  "+
		"			inner join KENHBANHANG kbh on kbh.PK_SEQ=a.kbh_fk   "+
		"		where a.npp_fk = '"+this.nppId+"'" ;

					
		} 
		CreateKhRs();	
		System.out.println("___CHUYEN KHO: " + query);
		this.nhapkhoRs = createSplittingData(50, 10, "ngaytra desc, trangthai asc ", query);
	}
	
	public void CreateKhRs(){
		this.db = new dbutils();
		String query = "";
		query = "select distinct PK_SEQ, MAFAST + '-' + TEN as TEN, mafast \n" +
		  "from KHACHHANG \n" +
		  "where TRANGTHAI = '1' and PK_SEQ in ( select Khachhang_fk from KHACHHANG_NPP where NPP_FK ='"+this.nppId+"') \n" +
		  "union \n" +
		  "select PK_SEQ, MAFAST + '-' + TEN as TEN, mafast \n" +
		  		  "from nhaphanphoi \n" +
		  		  "where TRANGTHAI = '1' and TRUCTHUOC_FK ='" +this.nppId+"'";
		this.khRs = null;
		this.khRs = this.db.get(query);
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
		this.nppId=util.getIdNhapp(this.userId);
		this.nppTen=util.getTenNhaPP();
		//this.dangnhap = util.getDangNhap();
		this.sitecode=util.getSitecode();
	}

}
