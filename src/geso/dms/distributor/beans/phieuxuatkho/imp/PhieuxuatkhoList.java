package geso.dms.distributor.beans.phieuxuatkho.imp;

import geso.dms.center.util.IPhanTrang;
import geso.dms.center.util.PhanTrang;
import geso.dms.center.util.Phan_Trang;
import geso.dms.distributor.beans.phieuxuatkho.IPhieuxuatkho;
import geso.dms.distributor.beans.phieuxuatkho.IPhieuxuatkhoList;
import geso.dms.distributor.db.sql.dbutils;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class PhieuxuatkhoList extends Phan_Trang implements IPhieuxuatkhoList, Serializable
{
	private static final long serialVersionUID = -9217977546733610214L;

	String userId; 

	ResultSet nhanviengn;
	String nvgnId;
	
	String trangthai;
	String tungay;
		
	List<IPhieuxuatkho> pxklist;
	
	String nppId;
	String nppTen;
	String sitecode;
	String quytrinhbanhang = "";
	String msg;
	String donhangid = "";
	


	dbutils db;

	private int num;

	private int[] listPages;

	private int currentPages;

	private HttpServletRequest request;
	
	private ResultSet rspxk;
	
	public PhieuxuatkhoList(String[] param)
	{
		this.nvgnId = param[0];
		this.trangthai = param[1];
		this.tungay = param[2];
		db = new dbutils();
	}
	
	public PhieuxuatkhoList()
	{
		this.nvgnId = "";
		this.trangthai = "";
		this.tungay = "";
		this.msg = "";


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
	
	public ResultSet getNhanvienGN() 
	{		
		return this.nhanviengn;
	}
	
	public void setNhanvienGN(ResultSet nhanviengn) 
	{
		this.nhanviengn = nhanviengn;		
	}
	
	public String getNvgnId() 
	{		
		return this.nvgnId;
	}
	
	public void setNvgnId(String nvgnId) 
	{
		this.nvgnId = nvgnId;		
	}
	
	public List<IPhieuxuatkho> getPxkList() 
	{		
		return this.pxklist;
	}
	
	public void setPxkList(List<IPhieuxuatkho> pxklist) 
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
		/*ResultSet rs = this.db.get("select a.pk_seq, a.ten, a.sitecode from nhaphanphoi a, nhanvien b where b.dangnhap = a.ma and b.pk_seq ='" + this.userId + "'");
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
			
		}catch(Exception e){}	
		*/
		//Phien ban moi
		geso.dms.distributor.util.Utility util=new geso.dms.distributor.util.Utility();
		this.nppId=util.getIdNhapp(this.userId);
		this.nppTen=util.getTenNhaPP();
		//this.dangnhap = util.getDangNhap();
		this.sitecode=util.getSitecode();
	}

	public void init(String search) 
	{
		//db = new dbutils();
		this.getNppInfo();
		
		String query = "";	
		if (search.length() == 0)
		{
			query = "select pxk.pk_seq as pxkId, nvgn.pk_seq as nvgnId, nvgn.ten as nvgnTen, pxk.trangthai, pxk.ngaylapphieu, pxk.ngaytao, pxk.ngaysua, nv1.ten as nguoitao, nv2.ten as nguoisua ";
			query =	query + "from phieuxuatkho pxk inner join nhanviengiaonhan nvgn on pxk.nvgn_fk = nvgn.pk_seq inner join nhanvien nv1 on pxk.nguoitao = nv1.pk_seq inner join nhanvien nv2 on pxk.nguoisua = nv2.pk_seq ";
			query = query + "where pxk.npp_fk = '" + this.nppId + "'";
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

	private void createPxkBeanList(String query) 
	{
		rspxk = createSplittingData_New(db,50, 10, "ngaylapphieu desc, pxkId desc, trangthai asc ", query);// createSplittingData(request, "pxkId desc", query); //db.get(query);
		
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

	
	public ResultSet getRsPXK() {
		
		return this.rspxk;
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

		
}
