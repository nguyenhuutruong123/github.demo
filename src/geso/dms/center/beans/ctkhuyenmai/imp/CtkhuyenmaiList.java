package geso.dms.center.beans.ctkhuyenmai.imp;

import geso.dms.center.beans.ctkhuyenmai.ICtkhuyenmaiList;
import geso.dms.center.util.IPhanTrang;
import geso.dms.center.util.PhanTrang;
import geso.dms.center.util.Phan_Trang;
import geso.dms.center.util.Utility;
import geso.dms.distributor.db.sql.dbutils;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.rmi.CORBA.Util;
import javax.servlet.http.HttpServletRequest;

public class CtkhuyenmaiList extends Phan_Trang implements ICtkhuyenmaiList 
{
	private static final long serialVersionUID = -9217977546733610214L;

	String userId; 

	String diengiai;
	String tungay;
	String denngay;
	String trangthai;
	String msg;
	String vung;
	String khuvuc;
	String npp;

	int num;
	ResultSet ctkmList;
	ResultSet vungrs;
	ResultSet khuvucrs;
	ResultSet npprs;
	//	List<ICtkhuyenmai> ctkmList;

	dbutils db;

	private int[] listPages;

	private int currentPages;

	private HttpServletRequest request;

	public CtkhuyenmaiList(String[] param)
	{
		this.diengiai = param[0];
		this.tungay = param[1];
		this.denngay = param[2];
		this.msg = "";
		this.num = Integer.parseInt(param[3]);
		currentPages = 1;
		listPages = new int[num];
		for(int i = 0; i < this.num; i++)
			listPages[i]=i+1;

		db = new dbutils();
	}

	public CtkhuyenmaiList()
	{
		this.diengiai = "";
		this.denngay = "";
		this.tungay = "";
		this.trangthai = "";
		this.vung = "";
		this.khuvuc ="";
		this.npp = "";
		this.msg = "";
		currentPages = 1;
		num = 1;
		this.db = new dbutils();
	}

	public HttpServletRequest getRequestObj() 
	{
		return this.request;
	}

	public void setRequestObj(HttpServletRequest request) 
	{
		this.request = request;
	}

	public int getNum(){
		return this.num;
	}
	public void setNum(int num){
		this.num = num;
		listPages = new int[num];
		for(int i = 0; i < this.num; i++)
			listPages[i]=i+1;
	}
	public String getUserId()
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;
	}

	public String getDiengiai() 
	{		
		return this.diengiai;
	}

	public void setDiengiai(String diengiai) 
	{
		this.diengiai = diengiai;
	}

	public String getTrangthai() 
	{		
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}
	
	public String getvung()
	{
		return this.vung;
	}
	public void setvung(String vung)
	{
		this.vung = vung;
	}

	public String getnpp()
	{
		return this.npp;
	}
	public void setnpp(String npp)
	{
		this.npp = npp;
	}
	
	public String getkhuvuc()
	{
		return this.khuvuc;
	}
	public void setkhuvuc(String khuvuc)
	{
		this.khuvuc = khuvuc;
	}
	
	public ResultSet getvungRs() {

		return this.vungrs;
	}

	public void setnppRs(ResultSet npprs) {

		this.npprs=npprs;
	}

	public ResultSet getnppRs() {

		return this.npprs;
	}

	public void setvungRs(ResultSet vungrs) {

		this.vungrs=vungrs;
	}

	public ResultSet getkhuvucRs() {

		return this.khuvucrs;
	}


	public void setkhuvucRs(ResultSet khuvucrs) {

		this.khuvucrs=khuvucrs;
	}
	
	public String getTungay() 
	{		
		return this.tungay;
	}

	public void setTungay(String tungay)
	{
		this.tungay = tungay;		
	}

	public String getDenngay() 
	{		
		return this.denngay;
	}

	public void setDenngay(String denngay) 
	{
		this.denngay = denngay;		
	}

	/*	public List<ICtkhuyenmai> getCtkmList() 
	{		
		return this.ctkmList;
	}*/

	public ResultSet getCtkmList() 
	{		
		return this.ctkmList;
	}

	/*	public void setCtkmList(List<ICtkhuyenmai> ctkmList) 
	{
		this.ctkmList = ctkmList;	
	}*/ 

	public void setCtkmList(ResultSet ctkmlist) 
	{
		this.ctkmList = ctkmlist;	
	}
	
	public void initDuyet(String search) 
	{
		this.db = new dbutils();
		Utility util = new Utility();
		String query = "";	
		if (search.length() == 0)
		{
			query = "select count(distinct e.kbh_fk) as sokenh from CTKHUYENMAI a inner join CTKM_NPP d on a.PK_SEQ = d.CTKM_FK "+
					"inner join NHAPP_KBH e on d.NPP_FK = e.NPP_FK and a.PK_SEQ = d.CTKM_FK";
			int sokenh = 0;
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				try
				{
					rs.next();
					sokenh = rs.getInt("sokenh");
				}
				catch(Exception e){ e.printStackTrace(); }
			}
			
			query = "\n select  top(100) a.PK_SEQ as ctkmId, a.scheme, substring(a.diengiai,1,100) as diengiai, a.TUNGAY, a.DENNGAY, case when " +
					"\n a.LOAICT is null then 1 else a.LOAICT end as type, isnull(a.NGANSACH,0) as ngansach, a.DASUDUNG, a.NGAYTAO, a.NGAYSUA, b.TEN as nguoitao, c.TEN as nguoisua "+
					"\n			,[dbo].[getvungctkm](a.PK_SEQ) as Vten "+
					"\n , (  "+
					"\n	   select COUNT(*)  "+
					"\n	   from PHANBOKHUYENMAI pb where pb.CTKM_FK=a.PK_SEQ and NGANSACH!=0 ) as isPB, "+
					"\n    isnull(a.isduyet,0) isDuyet "+
					"\n	, (select COUNT(*) From DONHANG_CTKM_TRAKM  km where km.CTKMID=a.PK_SEQ  )  "+
					"\n	+ "+
					"\n	(select COUNT(*) from ERP_DONDATHANG_CTKM_TRAKM  where CTKMID=a.PK_SEQ )as soDH, "+
					"\n   DATEDIFF(day, a.tungay, dbo.GetLocalDate(DEFAULT)) kcngay,isnull(ISDONGBO,'0') as ISDONGBO "+
					"\n ,a.ismail "+
					"\n from CTKHUYENMAI a inner join NHANVIEN b on a.NGUOITAO = b.PK_SEQ " +
					"\n inner join NHANVIEN c on a.NGUOISUA = c.PK_SEQ " +
					"\n where 1=1 and a.isduyet >= '1' " +
					"\n and a.PK_SEQ in (select ctkm_fk from CTKM_NPP ctn inner join phamvihoatdong pv on pv.npp_fk = ctn.npp_fk "+ 
					"\n inner join nhaphanphoi npp on npp.PK_SEQ = ctn.NPP_FK	  "+
					"\n and pv.Npp_fk = npp.PK_SEQ and pv.Nhanvien_fk = '"+userId+"')";
			if(sokenh < 2)
				query+= 
				
				" and (select distinct e.kbh_fk from CTKM_NPP d inner join NHAPP_KBH e on d.NPP_FK = e.NPP_FK and a.PK_SEQ = d.CTKM_FK) in " +util.quyen_kenh(userId);
			query+=" order by a.TUNGAY desc,a.isduyet  asc";
			System.out.println("Cau select 1: "+query);
		}
		else
		{
			query = search;
		}
		this.vungrs = db.get("SELECT PK_SEQ,TEN FROM VUNG WHERE TRANGTHAI='1'");
		search = " SELECT PK_SEQ,TEN FROM khuvuc WHERE 1=1";
		if(this.vung.length()>0)
		{
			search+= " and VUNG_FK="+this.vung;
		}
		this.khuvucrs=db.get(search);
		search = "select pk_seq, ten from nhaphanphoi where 1=1";
		if(this.khuvuc.length()>0)
		{
			search+=" and khuvuc_fk="+this.khuvuc;
		}
		this.npprs=db.get(search);
		System.out.println("Query : " + query);
		//this.ctkmList =  createSplittingData(50, 10, " kcngay asc", query); //, ctkmId desc, denngay desc
		this.ctkmList =  createSplittingData(50, 10, "ctkmid desc, ngaytao desc", query); //, ctkmId desc, denngay desc
		
	}

	public void init(String search) 
	{
		this.db = new dbutils();
		Utility util = new Utility();
		String query = "";	
		if (search.length() == 0)
		{
			
			
			query = "\n select  distinct  a.PK_SEQ as ctkmId, a.scheme, substring(a.diengiai,1,100) as diengiai, a.TUNGAY, a.DENNGAY, case when " +
					"\n a.LOAICT is null then 1 else a.LOAICT end as type, isnull(a.NGANSACH,0) as ngansach, a.DASUDUNG, a.NGAYTAO, a.NGAYSUA, b.TEN as nguoitao, c.TEN as nguoisua "+
					"\n			,[dbo].[getvungctkm](a.PK_SEQ) as Vten "+
					"\n , ( select COUNT(*) from PHANBOKHUYENMAI pb where pb.CTKM_FK=a.PK_SEQ and NGANSACH!=0 ) as isPB, "+
					"\n   isnull(a.isduyet,0) isDuyet "+
					"\n	, (select COUNT(*) From DONHANG_CTKM_TRAKM  km where km.CTKMID=a.PK_SEQ  )  "+
					"\n	+ "+
					"\n	(select COUNT(*) from ERP_DONDATHANG_CTKM_TRAKM  where CTKMID=a.PK_SEQ )as soDH, "+
					"\n   DATEDIFF(day, a.tungay, dbo.GetLocalDate(DEFAULT)) kcngay,isnull(ISDONGBO,'0') as ISDONGBO " +
					"\n ,a.ismail "+
					"\n from CTKHUYENMAI a inner join NHANVIEN b on a.NGUOITAO = b.PK_SEQ " +
					"\n inner join NHANVIEN c on a.NGUOISUA = c.PK_SEQ " +
					"\n where 1=1 " +
					"\n and a.PK_SEQ in (select ctkm_fk from CTKM_NPP ctn inner join phamvihoatdong pv on pv.npp_fk = ctn.npp_fk 	       "+ 
					"\n inner join nhaphanphoi npp on npp.PK_SEQ = ctn.NPP_FK	  "+
					"\n and pv.Npp_fk = npp.PK_SEQ and pv.Nhanvien_fk = '"+userId+"')";
			
			System.out.println("Cau select 1: "+query);
		}
		else
		{
			query = search;
		}
		//this.npprs = db.get("select pk_seq, ten from nhaphanphoi where trangthai ='1'");
		this.vungrs = db.get("SELECT PK_SEQ,TEN FROM VUNG WHERE TRANGTHAI='1'");
		search = " SELECT PK_SEQ,TEN FROM khuvuc WHERE 1=1";
		if(this.vung.length()>0)
		{
			search+= " and VUNG_FK="+this.vung;
		}
		this.khuvucrs=db.get(search);
		search = "select pk_seq, ten from nhaphanphoi where 1=1";
		if(this.khuvuc.length()>0)
		{
			search+=" and khuvuc_fk="+this.khuvuc;
		}
		this.npprs=db.get(search);
		System.out.println("uehu " + search);
		//this.ctkmList =  createSplittingData(50, 10, " kcngay asc", query); //, ctkmId desc, denngay desc
		this.ctkmList =  createSplittingData(50, 10, "ctkmid desc, ngaytao desc", query); //, ctkmId desc, denngay desc
	}


	public void DBclose() 
	{
		try 
		{
			this.vungrs.close();
			this.khuvucrs.close();
			this.npprs.close();
			this.db.shutDown();
		} 
		catch (SQLException e) {e.printStackTrace();}

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

		ResultSet rs = db.get("select count(*) as c from CTKHUYENMAI");
		try 
		{
			rs.next();
			int count = Integer.parseInt(rs.getString("c"));
			rs.close();
			return count;
		}
		catch (SQLException e) {}

		return 0;
	}


	public int[] getNewPagesList(String action, int num, int currentPage, int theLastPage, String[] listPage) {

		IPhanTrang pt = new PhanTrang();
		return pt.getNewPagesList(action, num, currentPage, theLastPage, listPage);
	}

	public String getMessage() 
	{
		return this.msg;
	}

	public void setMessage(String msg) 
	{
		this.msg = msg;
	}

	


}