package geso.dms.center.beans.thaydoikhuyenmai.imp;

import geso.dms.center.beans.thaydoikhuyenmai.IThayDoiKhuyenMaiList;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Phan_Trang;


import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ThayDoiKhuyenMaiList  extends Phan_Trang   implements IThayDoiKhuyenMaiList
{

	private static final long serialVersionUID = -7131330721029719895L;
	private String userId;
	private String tungay;
	private String denngay;
	private String trangthai;
	private ResultSet nhaphangList;
	private dbutils db;
	String Msg ;
	
	List<Object> dataSearch = new ArrayList<Object>();
	
	public ThayDoiKhuyenMaiList()
	{
		this.db = new dbutils();
		this.userId = "";
		this.tungay = "";
		this.denngay = "";
		this.trangthai = "";
		this.Msg ="";
	}
	
	public ResultSet getNhaphangList()
	{
		return this.nhaphangList;
	}
	
	public void setNhaphangList(ResultSet nhaphangList)
	{
		this.nhaphangList = nhaphangList;
	}

	public String getUserId()
	{
		return this.userId;
	}
	
	public void setUserId(String userId)
	{
		this.userId = userId;
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
	
	public String getTrangthai()
	{
		return this.trangthai;
	}
	
	public void setTrangthai(String trangthai)
	{
		this.trangthai = trangthai;
	}


	public void createNhaphanglist(String querystr)
	{
		String query;
		if (querystr.length()>0)
		{
			query = querystr;
		}else
		{
			query =
			"	select a.PK_SEQ as thaydoiId,a.Loai, "+
			"		a.traKm_fk,a.dkkm_fk,ISNULL(cast(b.pk_seq as varchar(20)),'') +' - '+ isnull(b.DienGiai,'') as dkKm,ISNULL(cast(c.pk_seq as varchar(20)),'') +' - ' + isnull(c.DIENGIAI,'')  as TraKm, "+
			"		nt.TEN as NguoiTao,a.NGAYTAO,ns.TEN as NguoiSua,a.NgaySua,isnull(nd.TEN,'') as NguoiDuyet,isnull(a.NGAYDUYET,'') as NgayDuyet,a.TRANGTHAI "+
			"	from ThayDoiKM a "+
			"		left join DIEUKIENKHUYENMAI b on b.PK_SEQ=a.DKKM_FK "+
			"		left join TRAKHUYENMAI c on c.PK_SEQ=a.TRAKM_FK "+
			"		left join NHANVIEN nt on nt.PK_SEQ=a.NGUOITAO "+
			"		left join NHANVIEN ns on ns.PK_SEQ=a.NGUOISUA "+
			"		left join NHANVIEN nd on nd.PK_SEQ=a.NGUOIDUYET " + 
			"  where 1=1 ";

		}
//		this.nhaphangList =  super.createSplittingData(super.getItems(), super.getSplittings(), "thaydoiId desc, trangthai asc, NGAYTAO desc", query); 	
		this.nhaphangList = super.createSplittingData_v2(db,super.getItems(), super.getSplittings(), "thaydoiId desc, trangthai asc, NGAYTAO desc", query, dataSearch, "");
	}
	public void DBclose(){
		try 
		{
			if(this.nhaphangList != null)
			this.nhaphangList.close();
		} catch (Exception e) {
		}
		if(!(this.db == null)){
			this.db.shutDown();
		}
	}

	public void setMsg(String Msg) 
	{
	  this.Msg = Msg;
	}

   public String getMsg() 
   {
		return this.Msg;
	}

	public List<Object> getDataSearch() {
		return dataSearch;
	}
	
	public void setDataSearch(List<Object> dataSearch) {
		this.dataSearch = dataSearch;
	}
		
}
