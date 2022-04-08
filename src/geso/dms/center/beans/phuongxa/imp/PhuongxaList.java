package geso.dms.center.beans.phuongxa.imp;

import geso.dms.center.beans.phuongxa.IPhuongxaList;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Phan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PhuongxaList extends Phan_Trang implements IPhuongxaList, Serializable 
{
	private static final long serialVersionUID = -9217977546733610214L;
	
	String userId;
	String trangthai;
	String Msg;
	String pxId, ttId, qhId;
	ResultSet pxlist, ttlist, qhlist;
	
	int currentPages;
	int[] listPages;

	dbutils db;
	List<Object> dataSearch = new ArrayList<Object>(); 
	
	public List<Object> getDataSearch() {
		return dataSearch;
	}
	public void setDataSearch(List<Object> dataSearch) {
		this.dataSearch = dataSearch;
	}
	
	public PhuongxaList(String[] param)
	{
		this.trangthai = param[0];
		//this.vmId = param[1];
		db = new dbutils();
	}
	
	public PhuongxaList()
	{
		this.trangthai = "";
		this.pxId = "";
		this.ttId = "";
		this.qhId = "";
		
		this.Msg ="";
		db = new dbutils();
		init("");
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

	public void createKvBeanList(String query)
	{
	//	this.pxlist = super.createSplittingData(super.getItems(), super.getSplittings(), "ngaytao desc", query); //db.get(query);
		this.pxlist = super.createSplittingData_v2(db, super.getItems(), super.getSplittings(),  "ngaytao desc", query, dataSearch, "");
				
	}
	
	public void init(String search) 
	{
		String query;	
		if (search.length() == 0)
		{
			query = "\n SELECT PX.pk_Seq, PX.Ten AS TENPX, TT.TEN AS TENTT, QH.TEN AS TENQH, NT.TEN AS NGUOITAO, " +
			"\n NS.TEN AS NGUOISUA, PX.NGAYTAO, PX.NGAYSUA, PX.TRANGTHAI " + 
			"\n FROM PHUONGXA PX " + 
			"\n INNER JOIN TINHTHANH TT ON TT.PK_SEQ = PX.TinhThanh_FK " + 
			"\n INNER JOIN QUANHUYEN QH ON QH.PK_SEQ = PX.QuanHuyen_FK " + 
			"\n INNER JOIN NHANVIEN NT ON NT.PK_SEQ = PX.NGUOITAO " + 
			"\n INNER JOIN NHANVIEN NS ON NS.PK_SEQ = PX.NGUOISUA ";
		}
		else
		{
			query = search;
		}

		System.out.println("Init PhuongxaList: " + query);
		createKvBeanList(query);  
		createRS();
	}

	public void setMsg(String Msg) {
	     this.Msg = Msg;
		
	}

	
	public String getMsg() {
		return this.Msg;
	}


	@Override
	public ResultSet getPhuongxaRs() {
		
		return this.pxlist;
	}

	@Override
	public void setPhuongxaRs(ResultSet pxRs) {
		
		this.pxlist = pxRs;
	}

	@Override
	public void createRS() {
		
		if(this.db == null)
			db = new dbutils();
		this.ttlist = db.get("select * from tinhthanh");
		
		String sql = "select * from quanhuyen";
		if(!this.ttId.equals(""))
		{
			sql += " where TINHTHANH_FK = '"+ this.ttId +"' ";
		}
		this.qhlist = db.get(sql);
	}

	@Override
	public ResultSet getTinhthanhRs() {
		
		return this.ttlist;
	}

	@Override
	public void setTinhthanhRs(ResultSet ttRs) {
		
		this.ttlist = ttRs;
	}

	@Override
	public String getTinhthanhId() {
		
		return this.ttId;
	}

	@Override
	public void setTinhthanhId(String ttId) {
		
		this.ttId = ttId;
	}

	@Override
	public ResultSet getQuanhuyenRs() {
		
		return this.qhlist;
	}

	@Override
	public void setQuanhuyenRs(ResultSet qhRs) {
		
		this.qhlist = qhRs;
	}

	@Override
	public String getQuanhuyenId() {
		
		return this.qhId;
	}

	@Override
	public void setQuanhuyenId(String qhId) {
		
		this.qhId = qhId;
	}

	@Override
	public int getCurrentPage() {
		
		return this.currentPages;
	}

	@Override
	public void setCurrentPage(int current) {
		
		this.currentPages = current;
	}

	@Override
	public int[] getListPages() {
		
		return this.listPages;
	}

	@Override
	public void setListPages(int[] listPages) {
		
		this.listPages = listPages;
	}

	@Override
	public int getLastPage() {
		
		ResultSet rs = db.get("select count(*) as px from phuongxa");
		try 
		{
			rs.next();
			int count = Integer.parseInt(rs.getString("px"));
			rs.close();
			return count;
		}
		catch(Exception e) {}
		
		return 0;
	}
}
