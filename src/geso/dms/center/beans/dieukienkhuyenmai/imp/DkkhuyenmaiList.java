package geso.dms.center.beans.dieukienkhuyenmai.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import geso.dms.center.beans.dieukienkhuyenmai.IDkkhuyenmaiList;
import geso.dms.center.beans.dieukienkhuyenmai.IDieukienkhuyenmai;
import geso.dms.center.beans.dieukienkhuyenmai.imp.Dieukienkhuyenmai;
import geso.dms.center.util.IPhanTrang;
import geso.dms.center.util.PhanTrang;
import geso.dms.center.util.Phan_Trang;
import geso.dms.distributor.db.sql.dbutils;

public class DkkhuyenmaiList extends Phan_Trang implements IDkkhuyenmaiList
{
	private static final long serialVersionUID = -9217977546733610214L;

	String userId; 
	
	String diengiai;
	String tungay;
	String denngay;
	String msg;
	ResultSet dkkmList;
	
	dbutils db;
	
	List<Object> dataSearch = new ArrayList<Object>(); 

	private int num;

	private int[] listPages;

	private int currentPages;

	private HttpServletRequest request;
	
	public DkkhuyenmaiList(String[] param)
	{
		this.diengiai = param[0];
		this.tungay = param[1];
		this.denngay = param[2];
		db = new dbutils();
		this.msg ="";
	}
	
	public DkkhuyenmaiList()
	{
		this.diengiai = "";
		this.denngay = "";
		this.tungay = "";
		this.msg ="";

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
	
	public String getDiengiai() 
	{		
		return this.diengiai;
	}
	
	public void setDiengiai(String diengiai) 
	{
		this.diengiai = diengiai;
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
	
	public void init(String search) 
	{
		db = new dbutils();
		
		String query = "";	
		if (search.length() == 0)
		{
			query = "select a.PK_SEQ as dkkmId, a.DIENGIAI, isnull(a.TONGLUONG, 0) as tongluong, isnull(a.TONGTIEN, 0) as tongtien, a.LOAI, a.NGAYTAO, a.NGAYSUA, b.TEN as nguoitao, c.TEN as nguoisua ";
			query = query + " from DIEUKIENKHUYENMAI a inner join NHANVIEN b on a.NGUOITAO = b.PK_SEQ inner join NHANVIEN c on a.NGUOISUA = c.PK_SEQ ";
			//query = query + " order by a.NGAYTAO DESC, a.pk_seq DESC";
		}
		else
		{
			query = search;
		}				
		this.createDkkmBeanList(query);	
	}
	
	private void createDkkmBeanList(String query) 
	{
//		this.dkkmList = createSplittingData(50, 10, "dkkmId desc", query);// createSplittingData(request, "dkkmId desc", query);//db.get(query);
		
		this.dkkmList = super.createSplittingData_v2(db,super.getItems(), super.getSplittings(), "dkkmId desc", query, dataSearch, "");
		
		/*
		List<IDieukienkhuyenmai> dkkmList = new ArrayList<IDieukienkhuyenmai>();
		
		if(rs != null)
		{
			String[] param = new String[9];
			IDieukienkhuyenmai dkkmBean = null;
			try {
				while(rs.next())
				{	
					param[0]= rs.getString("dkkmId");
					param[1]= rs.getString("diengiai");
					param[2]= rs.getString("tongtien");
					param[3] = rs.getString("tongluong");
					param[4]= rs.getString("loai");
					param[5]= rs.getString("ngaytao");
					param[6]= rs.getString("nguoitao");
					param[7]= rs.getString("ngaysua");
					param[8]= rs.getString("nguoisua");
					
					dkkmBean = new Dieukienkhuyenmai(param);
					dkkmList.add(dkkmBean);
				}
				rs.close();
			}
			catch(Exception e) {}		
		}
		this.dkkmList = dkkmList;	
		*/
	}
	
	public void DBclose() 
	{
				
	}
	public void setmsg(String msg) {
		this.msg = msg;
	}

	public String getmsg() {
		return msg;
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
		
		ResultSet rs = db.get("select count(*) as c from DIEUKIENKHUYENMAI");
		return PhanTrang.getLastPage(rs);
	}

	public int[] getNewPagesList(String action, int num, int currentPage, int theLastPage, String[] listPage) {
		
		IPhanTrang pt = new PhanTrang();
		return pt.getNewPagesList(action, num, currentPage, theLastPage, listPage);
	}

	public ResultSet getDkkmList() 
	{
		return this.dkkmList;
	}

	public void setDkkmList(ResultSet dkkmlist) 
	{
		this.dkkmList = dkkmlist;
	}

	public List<Object> getDataSearch() {
		return dataSearch;
	}

	public void setDataSearch(List<Object> dataSearch) {
		this.dataSearch = dataSearch;
	}	
	
}
