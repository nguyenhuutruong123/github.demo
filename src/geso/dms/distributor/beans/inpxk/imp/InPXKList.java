package geso.dms.distributor.beans.inpxk.imp;

import geso.dms.center.util.IPhanTrang;
import geso.dms.center.util.PhanTrang;
import geso.dms.center.util.Phan_Trang;
import geso.dms.distributor.beans.inpxk.IInPXK;
import geso.dms.distributor.beans.inpxk.IInPXKList;
import geso.dms.distributor.db.sql.dbutils;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class InPXKList extends Phan_Trang implements IInPXKList, Serializable
{
	private static final long serialVersionUID = -9217977546733610214L;

	String userId; 

	ResultSet nhanviengn;
	ResultSet nhanvienbh;
	String nvgnId;
	String nvbhId;
	
	String trangthai;
	String tungay;
	String denngay;
		
	List<IInPXK> pxklist;
	
	String nppId;
	String nppTen;
	String sitecode;
	String quytrinhbanhang = "";
	String msg;
	String tab;
	String donhangid = "";
	String activeTab;


	dbutils db;

	private int num;

	private int[] listPages;

	private int currentPages;

	private HttpServletRequest request;
	
	private ResultSet rspxk;
	private ResultSet rspxkin;
	
	public InPXKList(String[] param)
	{
		this.nvgnId = param[0];
		this.trangthai = param[1];
		this.tungay = param[2];
		this.denngay = param[3];
		this.activeTab = param[4];
		db = new dbutils();
	}
	
	public InPXKList()
	{
		this.nvgnId = "";
		this.trangthai = "";
		this.tungay = "";
		this.denngay ="";
		this.activeTab = "0";
		this.msg = "";
		this.tab ="0";


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
	
	public ResultSet getNhanvienGN() 
	{		
		return this.nhanviengn;
	}
	
	public void setNhanvienGN(ResultSet nhanviengn) 
	{
		this.nhanviengn = nhanviengn;		
	}
	
	public ResultSet getNhanvienBH() 
	{		
		return this.nhanvienbh;
	}
	
	public void setNhanvienBH(ResultSet nhanvienbh) 
	{
		this.nhanvienbh = nhanvienbh;		
	}
	
	
	public String getNvgnId() 
	{		
		return this.nvgnId;
	}
	
	public void setNvgnId(String nvgnId) 
	{
		this.nvgnId = nvgnId;		
	}
	
	
	public String getNvbhId() 
	{		
		return this.nvbhId;
	}
	
	public void setNvbhId(String nvbhId) 
	{
		this.nvbhId = nvbhId;		
	}
	

	public List<IInPXK> getInPxkList() 
	{		
		return this.pxklist;
	}
	
	public void setInPxkList(List<IInPXK> pxklist) 
	{		
		this.pxklist = pxklist;
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
		/*String query = "select a.pk_seq, a.ten, a.sitecode from nhaphanphoi a, nhanvien b where b.dangnhap = a.ma and b.pk_seq ='" + this.userId + "'";
		ResultSet rs = this.db.get(query);
		System.out.println("_______V_____" + query);
		try{
			if (!(rs == null)){
				rs.next();
				this.nppId = rs.getString("pk_seq");
				this.nppTen = rs.getString("ten");
				this.sitecode = rs.getString("sitecode");
				
			}else
			{
				this.nppId = "";
				this.nppTen = "";
				this.sitecode = "";				
			}
			
		}catch(Exception e){}	*/
		//Phien ban moi
		geso.dms.distributor.util.Utility util=new geso.dms.distributor.util.Utility();
		System.out.println("odsfhs" + this.userId);
		this.nppId=util.getIdNhapp(this.userId);
		System.out.println("nppi +++++___+" + this.nppId);
		this.nppTen=util.getTenNhaPP();
		//this.dangnhap = util.getDangNhap();
		this.sitecode=util.getSitecode();
	}

	public void init(String search) 
	{
		//db = new dbutils();
		this.getNppInfo();
		System.out.println("nppID_____________" + this.nppId);
		String query = "";	
		if (search.length() == 0)
		{
			query = "select b.pk_seq as dhId, b.ngaytao as ngaylapphieu, b.ngaytao as ngaydonhang, d.pk_seq as maKH, d.ten as tenKH, b.trangthaiin ";
			query =	query + "from donhang b " +
					" inner join khachhang d on b.khachhang_fk = d.pk_seq " +
					" inner join daidienkinhdoanh c on c.pk_seq = b.ddkd_fk ";
			query = query + "where b.trangthai != 2   and b.npp_fk = '" + this.nppId + "'" +
					" and not exists (select 1 from PXKIN_DH where donhang_fk = b.pk_seq)";
			//query = "select * from phieuxuatkho";
		}
		else
		{
			query = search;
		}			
		System.out.println("Phieu Xuat Kho : "+query);
		String sql = "select isnull(quytrinhbanhang,0) as quytrinhbanhang  from NHAPHANPHOI where pk_seq = "+this.nppId+"";
		ResultSet rs = db.get(sql);
		try {
			rs.next();
			this.quytrinhbanhang = rs.getString("quytrinhbanhang");
			rs.close();
		} catch (SQLException e)
		{
			
			e.printStackTrace();
		}
		this.createRS();
		this.createPxkBeanList(query);		
	}

	public void init1(String search) 
	{
		//db = new dbutils();
		this.getNppInfo();
		System.out.println("nppID_____________" + this.nppId);
		String query = "";	
		if (search.length() == 0)
		{
			query = "select b.pk_seq as dhId, b.ngaytao as ngaylapphieu, b.ngaytao as ngaydonhang, d.pk_seq as maKH, d.ten as tenKH,b.trangthaiin ";
			query =	query + "from donhang b on a.donhang_fk = b.pk_seq " +
					" inner join khachhang d on b.khachhang_fk = d.pk_seq " +
					" inner join daidienkinhdoanh c on c.pk_seq = b.ddkd_fk ";
			query = query + "where b.trangthai != 2  and b.npp_fk = '" + this.nppId + "'";
			//query = "select * from phieuxuatkho";
		}
		else
		{
			query = search;
		}			
		System.out.println("Phieu Xuat Kho : "+query);
		String sql = "select isnull(quytrinhbanhang,0) as quytrinhbanhang  from NHAPHANPHOI where pk_seq = "+this.nppId+"";
		ResultSet rs = db.get(sql);
		try {
			rs.next();
			this.quytrinhbanhang = rs.getString("quytrinhbanhang");
			rs.close();
		} catch (SQLException e)
		{
			
			e.printStackTrace();
		}
		this.createRS();
		this.createPxkBeanList1(query);		
	}

	
	private void createPxkBeanList(String query) 
	{
		System.out.println("query______________+++++++++ "+ query);
		rspxk = createSplittingData(50, 10, "ngaylapphieu desc ", query);// createSplittingData(request, "pxkId desc", query); //db.get(query);
		
		/*query = "select a.ngaytao, a.ngaytao as ngaytaoi, a.pk_seq, b.ten as nguoitao from PXKIN a inner join nhanvien b on a.nguoitao = b.pk_seq " +
				" inner join NHAPHANPHOI c on c.SITECODE = b.CONVSITECODE where c.pk_seq = "+this.nppId+" ";
		rspxkin = createSplittingData(50, 10, "ngaytaoi desc, pk_seq desc ", query);*/
		// createSplittingData(request, "pxkId desc", query); //db.get(query);
		
		/*List<IPhieuxuatkho> pxklist = new ArrayList<IPhieuxuatkho>();
		
		if(rs != null)
		{
			String[] param = new String[8];
			IPhieuxuatkho pxkBean = null;			
			try {
				while(rs.next())
				{	
					param[0]= rs.getString("pxkId");
					param[1]= rs.getString("trangthai");
					param[2]= rs.getString("ngaytao");
					param[3]= rs.getString("nguoitao");
					param[4]= rs.getString("ngaysua");
					param[5]= rs.getString("nguoisua");
					param[6]= rs.getString("nvgnTen");
					param[7]= rs.getString("ngaylapphieu");
					//param[8]= rs.getString("nvgnTen");
					
					pxkBean = new Phieuxuatkho(param);
					pxklist.add(pxkBean);
				}
				rs.close();
			}
			catch(Exception e) {}
			finally{try {
				if(rs != null)
					rs.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}}
		}
		this.pxklist = pxklist;	*/
	}
	
	
	private void createPxkBeanList1(String query) 
	{
		
		//query = "select a.ngaytao, a.pk_seq, b.ten as nguoitao from PXKIN a inner join nhanvien b on a.nguoitao = b.pk_seq  ";
		rspxkin = createSplittingData(50, 10, "ngaytao desc, pk_seq desc ", query);// createSplittingData(request, "pxkId desc", query); //db.get(query);
		
		/*List<IPhieuxuatkho> pxklist = new ArrayList<IPhieuxuatkho>();
		
		if(rs != null)
		{
			String[] param = new String[8];
			IPhieuxuatkho pxkBean = null;			
			try {
				while(rs.next())
				{	
					param[0]= rs.getString("pxkId");
					param[1]= rs.getString("trangthai");
					param[2]= rs.getString("ngaytao");
					param[3]= rs.getString("nguoitao");
					param[4]= rs.getString("ngaysua");
					param[5]= rs.getString("nguoisua");
					param[6]= rs.getString("nvgnTen");
					param[7]= rs.getString("ngaylapphieu");
					//param[8]= rs.getString("nvgnTen");
					
					pxkBean = new Phieuxuatkho(param);
					pxklist.add(pxkBean);
				}
				rs.close();
			}
			catch(Exception e) {}
			finally{try {
				if(rs != null)
					rs.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}}
		}
		this.pxklist = pxklist;	*/
	}
	
	
	public void createRS() 
	{
		String sql = "select pk_seq as nvgnId, ten as nvgnTen from nhanviengiaonhan where npp_fk = '" + this.nppId + "' and trangthai='1'";
		this.nhanviengn = db.get(sql);
		sql = "select pk_seq as nvbhId, ten as nvbhTen from daidienkinhdoanh where npp_fk = '"+this.nppId+"' and trangthai ='1' ";
		this.nhanvienbh = db.get(sql);
		
	}

	public void DBclose() 
	{
		try 
		{
			if(!(nhanviengn == null))
				nhanviengn.close();
			if(rspxk!=null){
				rspxk.close();
			}
			if(rspxkin!=null){
				rspxkin.close();
			}			
			if(pxklist!=null){
				pxklist.clear();
			}
			if(this.db != null)
				this.db.shutDown();
		} 
		catch(Exception e) {}
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
		ResultSet rs = db.get("select count(*) as c from phieuxuatkho");
		return PhanTrang.getLastPage(rs);
	}
	public int[] getNewPagesList(String action, int num, int currentPage, int theLastPage, String[] listPage) {
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
	
	public String getTab() 
	{
		return this.tab;
	}

	public void setTab(String tab) 
	{
		this.tab = tab;
	}

	
	public ResultSet getRsPXK() {
		
		return this.rspxk;
	}

public ResultSet getRsPXKIN() {
		
		return this.rspxkin;
	}

	
	public String getQuyTrinhBanHang() {
		
		return this.quytrinhbanhang;
	}

	
	public void setQuyTrinhBanHang(String qtbh) {
		
		this.quytrinhbanhang = qtbh;
	}		
	
	public String getDonhangId() {
		
		return donhangid;
	}

	
	public void setDonhangId(String donhangid) {
		this.donhangid = donhangid;
		
	}

	
	public String getActiveTab()
	{
		return activeTab;
	}
	public void setActiveTab(String activeTab)
	{
		this.activeTab = activeTab;
	}
	
	

		
}
