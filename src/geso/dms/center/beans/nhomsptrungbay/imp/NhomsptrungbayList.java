package geso.dms.center.beans.nhomsptrungbay.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import geso.dms.center.beans.nhomsptrungbay.INhomsptrungbayList;
import geso.dms.center.beans.nhomsptrungbay.INhomsptrungbay;
import geso.dms.center.beans.nhomsptrungbay.imp.Nhomsptrungbay;
import geso.dms.center.util.IPhanTrang;
import geso.dms.center.util.PhanTrang;
import geso.dms.center.util.Phan_Trang;
import geso.dms.center.util.Utility;
import geso.dms.distributor.db.sql.dbutils;

public class NhomsptrungbayList extends Phan_Trang implements INhomsptrungbayList
{
	List<Object> dataSearch = new ArrayList<Object>(); 
	public List<Object> getDataSearch() {
		return dataSearch;
	}
	private static final long serialVersionUID = -9217977546733610214L;

	String userId; 
	
	String diengiai;
	String tungay;
	String denngay;

		
	List<INhomsptrungbay> nsptbList;
	
	dbutils db;

	private String mgs;

	private int num;

	private int[] listPages;

	private int currentPages;

	private HttpServletRequest request;
	
	public NhomsptrungbayList(String[] param)
	{
		this.diengiai = param[0];
		this.tungay = param[1];
		this.denngay = param[2];
		db = new dbutils();
	}
	
	public NhomsptrungbayList()
	{
		this.mgs = "";
		this.diengiai = "";
		this.denngay = "";
		this.tungay = "";

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
	
	public List<INhomsptrungbay> getNsptbList() 
	{		
		return this.nsptbList;
	}
	
	public void setNsptbList(List<INhomsptrungbay> nsptbList) 
	{
		this.nsptbList = nsptbList;	
	}
	
	public void init(String search) 
	{
		db = new dbutils();
		
		String query = "";	
		Utility Ult = new Utility();
		if (search.length() == 0)
		{
			query = "select a.PK_SEQ as nsptbId, a.DIENGIAI, isnull(a.TONGLUONG, 0) as tongluong, isnull(a.TONGTIEN, 0) as tongtien, a.LOAI, a.NGAYTAO, a.NGAYSUA, b.TEN as nguoitao, c.TEN as nguoisua ";
			query = query + " from Nhomsptrungbay a inner join NHANVIEN b on a.NGUOITAO = b.PK_SEQ inner join NHANVIEN c on a.NGUOISUA = c.PK_SEQ ";
			query = query +" where a.pk_seq in (select nhomsptrungbay_fk from Nhomsptrungbay_sanpham where sanpham_fk in"+ Ult.quyen_sanpham(this.userId) +")";
			//query = query + " order by a.NGAYTAO DESC, a.pk_seq DESC";
		}
		else
		{
			query = search;
			
		}				
	
		
		System.out.println("ct trung bay:"+ query);
		this.createDkkmBeanList(query);	
	}
	
	private void createDkkmBeanList(String query) 
	{
		ResultSet rs = super.createSplittingData_v2(db,super.getItems(), super.getSplittings(), "nsptbId desc", query, dataSearch, "");// createSplittingData(request, "nsptbId desc", query);//db.get(query);
		List<INhomsptrungbay> nsptbList = new ArrayList<INhomsptrungbay>();
		
		if(rs != null)
		{
			String[] param = new String[9];
			INhomsptrungbay dkkmBean = null;
			try {
				while(rs.next())
				{	
					param[0]= rs.getString("nsptbId");
					param[1]= rs.getString("diengiai");
					param[2]= rs.getString("tongtien");
					param[3] = rs.getString("tongluong");
					param[4]= rs.getString("loai");
					param[5]= rs.getString("ngaytao");
					param[6]= rs.getString("nguoitao");
					param[7]= rs.getString("ngaysua");
					param[8]= rs.getString("nguoisua");
					
					dkkmBean = new Nhomsptrungbay(param);
					nsptbList.add(dkkmBean);
				}
				rs.close();
			}
			catch(Exception e) {}		
		}
		this.nsptbList = nsptbList;	
	}
	
	public void DBclose() 
	{
				
	}

	@Override
	public void setMgs(String mgs) {
		// TODO Auto-generated method stub
		this.mgs = mgs;
		
	}

	@Override
	public String getMgs() {
		// TODO Auto-generated method stub
		return this.mgs;
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
		ResultSet rs = db.get("select count(*) as c from Nhomsptrungbay");
		return PhanTrang.getLastPage(rs);

	}

	
	public int[] getNewPagesList(String action, int num, int currentPage, int theLastPage, String[] listPage) {
		// TODO Auto-generated method stub
		IPhanTrang pt = new PhanTrang();
		return pt.getNewPagesList(action, num, currentPage, theLastPage, listPage);
	}		


}
