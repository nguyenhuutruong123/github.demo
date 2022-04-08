package geso.dms.distributor.beans.tuyenbanhang.imp;

import geso.dms.center.util.IPhanTrang;
import geso.dms.center.util.PhanTrang;
import geso.dms.center.util.Phan_Trang;
import geso.dms.distributor.beans.tuyenbanhang.ITuyenbanhangList;
import geso.dms.distributor.beans.tuyenbanhang.ITuyenbanhang;
import geso.dms.distributor.db.sql.*;
import geso.dms.distributor.util.Utility;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class TuyenbanhangList extends Phan_Trang implements ITuyenbanhangList, Serializable
{
	private static final long serialVersionUID = -9217977546733610214L;

	String userId;
	String tuyenbh;
	ResultSet ddkd;
	String ddkdId;
	
	String nppId;
	String nppTen;
	String sitecode;
		
	String Message;
	List<ITuyenbanhang> tbhlist;
	dbutils db;
	String view = "";
	String npp_fk_ho = ""; //HO phải chọn 1 NPP để Load List
	ResultSet nppRs_ho;
	List<Object> datasearch = new ArrayList<Object>();
	private int num;

	private int[] listPages;

	private int currentPages;

	private HttpServletRequest request;
	
	public TuyenbanhangList(String[] param)
	{
		this.tuyenbh = param[0];
		this.ddkdId = param[1];
	}
	
	public TuyenbanhangList()
	{
		this.tuyenbh = "";
		this.ddkdId = "";
		currentPages = 1;
		num = 1;
		this.Message="";
		db = new dbutils();
	}
	public ResultSet getNppRs_ho() {
		return nppRs_ho;
	}
	public void setNppRs_ho(ResultSet nppRs_ho) {
		this.nppRs_ho = nppRs_ho;
	}
	public String getNpp_fk_ho() {
		return npp_fk_ho;
	}
	public void setNpp_fk_ho(String npp_fk_ho) {
		this.npp_fk_ho = npp_fk_ho;
	}
	public String getView() {
		return view;
	}
	public void setView(String view) {
		this.view = view;
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
	
	public String getTuyenbh() 
	{
		return this.tuyenbh;
	}

	public void setTuyenbh(String tuyenbh) 
	{
		this.tuyenbh = tuyenbh;
	}

	public ResultSet getDdkd() 
	{
		return this.ddkd;
	}

	public void setDdkd(ResultSet ddkd) 
	{
		this.ddkd = ddkd;
	}

	public String getDdkdId() 
	{
		return this.ddkdId;
	}

	public void setDdkdId(String ddkdId)
	{
		this.ddkdId = ddkdId;
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
	
	
	
	public List<Object> getDatasearch() {
		return datasearch;
	}

	public void setDatasearch(List<Object> datasearch) {
		this.datasearch = datasearch;
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
		if (view != null && !view.equals("TT")) 
		{
			this.nppId=util.getIdNhapp(this.userId);
		}
		this.nppTen=util.getTenNhaPP();
		//this.dangnhap = util.getDangNhap();
		this.sitecode=util.getSitecode();
	}

	public List<ITuyenbanhang> getTbhList() 
	{
		return this.tbhlist;
	}

	public void setTbhList(List<ITuyenbanhang> tbhlist) 
	{
		this.tbhlist = tbhlist;
	}
	
	public void createDdkdRS()
	{
		this.getNppInfo();
		String sql ="select pk_seq as ddkdId, ten as ddkdTen from daidienkinhdoanh where npp_fk="+ this.nppId +" order by ten ASC";
	//	//System.out.println("chuoi thu:" +  sql);
		this.ddkd = db.get(sql);
		
	}
	
	public void createTbhBeanList(String query)
	{
		//System.out.println("createTbhBeanList");
		ResultSet rs = createSplittingData(50, 10, "ngayId ,ddkdTen,tbhId desc", query);// createSplittingData(request, "tbhId desc", query);//this.db.get(query);
		List<ITuyenbanhang> tbhlist = new ArrayList<ITuyenbanhang>();
			
		if(rs != null)
		{
			String[] param = new String[10];
			ITuyenbanhang tbhBean = null;			
			try {
				while(rs.next())
				{	
					param[0]= rs.getString("tbhId");
					param[1]= rs.getString("tbhTen");
					param[2]= rs.getString("ddkdTen");
					param[3]= rs.getString("ngaylamviec");
					param[4]= rs.getDate("ngaytao").toString();
					param[5]= rs.getString("nguoitao");
					param[6]= rs.getDate("ngaysua").toString();
					param[7]= rs.getString("nguoisua");
					param[8]= rs.getString("ddkdId");
																															
					tbhBean = new Tuyenbanhang(param);
					tbhlist.add(tbhBean);
				}
			}
			catch(Exception e) {}
			finally{try {
				if(rs != null)
					rs.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}}
			
		}
		this.tbhlist = tbhlist;
	}
	public void createTbhBeanList_v2(String query)
	{
		//System.out.println("createTbhBeanList_v2");
		ResultSet rs = createSplittingData_v2(db,super.getItems(), super.getSplittings(), "ngayId ,ddkdTen,tbhId desc", query,this.getDatasearch(),"");// createSplittingData(request, "tbhId desc", query);//this.db.get(query);
		List<ITuyenbanhang> tbhlist = new ArrayList<ITuyenbanhang>();
			
		if(rs != null)
		{
			String[] param = new String[10];
			ITuyenbanhang tbhBean = null;			
			try {
				while(rs.next())
				{	
					param[0]= rs.getString("tbhId");
					param[1]= rs.getString("tbhTen");
					param[2]= rs.getString("ddkdTen");
					param[3]= rs.getString("ngaylamviec");
					param[4]= rs.getDate("ngaytao").toString();
					param[5]= rs.getString("nguoitao");
					param[6]= rs.getDate("ngaysua").toString();
					param[7]= rs.getString("nguoisua");
					param[8]= rs.getString("ddkdId");
																															
					tbhBean = new Tuyenbanhang(param);
					tbhlist.add(tbhBean);
				}
			}
			catch(Exception e) {}
			finally{try {
				if(rs != null)
					rs.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}}
			
		}
		this.tbhlist = tbhlist;
		//System.out.println("thanh cong !");
	}
		
	public void init(String search) 
	{
		
		if (view != null && view.equals("TT")) {
			Utility util = new Utility();
			nppRs_ho = db.get("select pk_seq,ten from nhaphanphoi where trangthai = 1 and pk_seq in "+util.quyen_npp(this.userId)+" ");
		}
		else {
			nppRs_ho = null;
		}
		
		if (view != null && view.equals("TT")) {
			this.nppId = npp_fk_ho;
		}
		
		//db = new dbutils();
		this.getNppInfo();
		String query="";	
		if (search.length() == 0)
		{
			query = "\n select a.pk_seq as tbhId,a.ngayId, a.diengiai as tbhTen, a.ngaytao, a.ngaysua, a.ngaylamviec, " +
					"\n b.ten as nguoitao, c.ten as nguoisua, +" +
					"\n d.pk_seq as ddkdId, d.ten as ddkdTen, e.ten as nppTen, e.pk_seq as nppId " + 
					"\n from tuyenbanhang a inner join nhanvien b on a.nguoitao = b.pk_seq " +
					"\n inner join nhanvien c on a.nguoisua = c.pk_seq " +
					"\n inner join daidienkinhdoanh d on a.ddkd_fk = d.pk_seq " + 
					"\n inner join nhaphanphoi e on a.npp_fk = e.pk_seq " +
		    		"\n where a.npp_fk = '" + this.nppId  + "'";
			this.createTbhBeanList(query);  
		}
		else
		{
			query = search;
			this.createTbhBeanList_v2(query);  
		}
		
		this.createDdkdRS();
	}
	
	public int getNum(){
		return this.num;
	}
	public void setNum(int num){
		this.num = num;
		listPages = PhanTrang.getListPages(num);

	}

	
	public int getCurrentPage() {
		// TODO Auto-generated method stub
		return this.currentPages;
	}

	
	public void setCurrentPage(int current) {
		// TODO Auto-generated method stub
		this.currentPages = current;
	}

	
	public int[] getListPages() {
		// TODO Auto-generated method stub
		return this.listPages;
	}

	
	public void setListPages(int[] listPages) {
		// TODO Auto-generated method stub
		this.listPages = listPages;
	}

	
	public int getLastPage() {
		// TODO Auto-generated method stub
		ResultSet rs = db.get("select count(*) as c from tuyenbanhang");
		return PhanTrang.getLastPage(rs);

	}

	
	public int[] getNewPagesList(String action, int num, int currentPage, int theLastPage, String[] listPage) {
		// TODO Auto-generated method stub
		IPhanTrang pt = new PhanTrang();
		return pt.getNewPagesList(action, num, currentPage, theLastPage, listPage);
	}

	@Override
	public void DBclose() {
		// TODO Auto-generated method stub
		
		try {
		
			if(this.ddkd != null)
				this.ddkd.close();
			if(this.db != null)
				this.db.shutDown();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

	@Override
	public void setMsg(String msg) {
		// TODO Auto-generated method stub
		this.Message=msg;
	}

	@Override
	public String getMsg() {
		// TODO Auto-generated method stub
		return this.Message;
	}		

	
	
}
