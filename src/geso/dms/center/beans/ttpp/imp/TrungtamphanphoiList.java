package geso.dms.center.beans.ttpp.imp;

import geso.dms.center.beans.ttpp.ITrungtamphanphoiList;
import geso.dms.center.db.sql.dbutils;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TrungtamphanphoiList implements ITrungtamphanphoiList
{
	
	List<Object> dataSearch = new ArrayList<Object>(); 
	
	public List<Object> getDataSearch() {
		return dataSearch;
	}
	public void setDataSearch(List<Object> dataSearch) {
		this.dataSearch = dataSearch;
	}
	
	private static final long serialVersionUID = -9217977546733610214L;	
	private String dvkd;
	private String nccId;
	private String tungay;
	private String denngay;
	private String trangthai;
	private String query = "";
	String Msg;
	String view = "";
	String userId = "";
	private dbutils db;
	private ResultSet dvkdlist; 
	
	public TrungtamphanphoiList(String[] param)
	{
		this.dvkd = param[0];
		this.nccId = param[1];
		this.tungay = param[2];
		this.denngay = param[3];
		this.trangthai = param[4];
		this.db = new dbutils();
	}
	
	public TrungtamphanphoiList()
	{
		this.dvkd = "";
		this.nccId = "";
		this.tungay = "";
		this.denngay = "";
		this.trangthai = "";
		this.query = "";
		this.Msg = "";
		this.db = new dbutils();
	}
	
	public String getQuery()
	{
		return this.query;
	}
	
	public void setQuery(String query)
	{
		this.query = query;
	}
 
	public String getDvkd()
	{
		return this.dvkd;
	}
	
	public void setDvkd(String dvkd)
	{
		this.dvkd = dvkd;
	}
	
	public String getNccId()
	{
		return this.nccId;
	}
	
	public void setNccId(String nccId)
	{
		this.nccId = nccId;
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
 
	public ResultSet getDvkdList()
	{
		String query = "\n select a.pk_seq, a.TEN as ttpp, d.ten as nhacungcap, " +
		"\n a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, " + 
		"\n c.ten as nguoisua, d.pk_seq as nccId, a.MA " +
		"\n from TRUNGTAMPHANPHOI a, nhanvien b, nhanvien c, nhacungcap d, TTPP_NCC e " +
		"\n where a.PK_SEQ = e.TTPP_FK and d.PK_SEQ = e.NCC_FK  and a.nguoitao = b.PK_SEQ and a.nguoisua = c.PK_SEQ " +
		"\n group by a.pk_seq, a.TEN, a.trangthai, a.ngaytao, a.ngaysua, b.ten, " +
		"\n c.ten, a.MA, d.ten, d.pk_seq ";
		
		if (dataSearch != null && dataSearch.size() > 0) {
			return this.db.getByPreparedStatement(query, dataSearch);
		}
		else {
			return this.db.get(query);
		}

	}
	
	public ResultSet getNccList(boolean all)
	{
		String query;
		if (all)
			query = "select pk_seq, ten from nhacungcap";
		else
			query = "select pk_seq, ten from nhacungcap where trangthai='1'";
		
		return this.db.get(query);
		
	}
	
	public void DBClose()
	{
		this.db.shutDown();
		
	}
	
	public void setMsg(String Msg)
	{
		this.Msg = Msg;
		
	}
	
	public String getMsg()
	{
		return this.Msg;
	}

	public String getView() {
		return view;
	}

	public void setView(String view) {
		this.view = view;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
}
