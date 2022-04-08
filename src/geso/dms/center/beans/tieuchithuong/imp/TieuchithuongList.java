package geso.dms.center.beans.tieuchithuong.imp;

import java.io.Serializable;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import geso.dms.center.beans.tieuchithuong.*;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Phan_Trang;

public class TieuchithuongList   extends Phan_Trang implements ITieuchithuongList ,Serializable
{
	public String dvkdId;
	public ResultSet dvkd;

	String tungay;
	String denngay;
	
	public String kbhId;
	public ResultSet kbh;

	public String msg;

	public String thang;
	public String nam;

	public String userId;


	public String loai;

	ResultSet tct;

	public dbutils db;
	
	List<Object> dataSearch = new ArrayList<Object>(); 

	public TieuchithuongList() {
		this.dvkdId = "";
		this.kbhId = "";
		this.msg = "";
		this.thang = "";
		this.tungay = "";
		this.denngay ="";
		this.nam = "";
		this.loai = "1";
		this.db = new dbutils();
	}

	public void setDvkdId(String dvkdId) {
		this.dvkdId = dvkdId;
	}

	public String getdvkdId() {
		return this.dvkdId;
	}

	public void setDvkd(ResultSet dvkd) {
		this.dvkd = dvkd;
	}

	public ResultSet getdvkd() {
		return this.dvkd;
	}

	public void setKbhId(String kbhId) {
		this.kbhId = kbhId;
	}

	public String getkbhId() {
		return this.kbhId;
	}

	public void setKbh(ResultSet kbh) {
		this.kbh = kbh;
	}

	public ResultSet getKbh() {
		return this.kbh;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return this.msg;
	}

	public void setMonth(String month) {
		this.thang = month;
	}

	public String getMonth() {
		return this.thang;
	}

	public void setYear(String year) {
		this.nam = year;
	}

	public String getYear() {
		return this.nam;
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
	
	
	
	
	public String getUserId() {
		return this.userId;
	}
	
	public void setUserId(String userId)
	{
		this.userId = userId;
		
	}

	

	public void setLoai(String loai) {
		this.loai = loai;
	}

	public String getLoai() {
		return this.loai;
	}

	public void setTct(ResultSet tct) {
		this.tct = tct;
	}

	public ResultSet getTct() {
		return this.tct;
	}

	private String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd: hh-mm-ss");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public void init() 
	{
		String query = "SELECT PK_SEQ AS DVKDID, DONVIKINHDOANH AS DVKD FROM DONVIKINHDOANH " ;
		this.dvkd = this.db.get(query);

		query = "SELECT PK_SEQ AS KBHID, TEN AS KBH FROM KENHBANHANG where  trangthai  = 1";
		
		this.kbh = this.db.get(query);

		query = "SELECT TCT.PK_SEQ, TCT.DIENGIAI, KBH.TEN AS KBH, "
				+ "DVKD.DONVIKINHDOANH AS DVKD, TCT.THANG, TCT.NAM, "
				+ "NV1.TEN AS NGUOITAO, "
				+ "NV2.TEN AS NGUOISUA,"
				+ "TCT.NGAYTAO,"
				+ "TCT.NGAYSUA,"
				+ "TCT.TRANGTHAI "
				+ "FROM TIEUCHITINHTHUONG TCT "
				+ "INNER JOIN KENHBANHANG KBH ON KBH.PK_SEQ = TCT.KBH_FK "
				+ "INNER JOIN DONVIKINHDOANH DVKD ON DVKD.PK_SEQ = TCT.DVKD_FK "
				+ "INNER JOIN NHANVIEN NV1 ON NV1.PK_SEQ = TCT.NGUOITAO "
				+ "INNER JOIN NHANVIEN NV2 ON NV2.PK_SEQ = TCT.NGUOISUA ";

//		String condition = " WHERE TCT.LOAI ='" + this.loai + "' ";
		
		String condition = "\n WHERE TCT.LOAI = ? ";	
		this.getDataSearch().add( "" + this.loai + "" );

	/*	if (this.kbhId.length() > 0) {
			condition = condition + " AND TCT.KBH_FK='" + this.kbhId + "' ";
		}
*/
		if (this.dvkdId.length() > 0) {
//			condition = condition + " AND TCT.DVKD_FK='" + this.dvkdId + "' ";
			
			condition = condition + "\n AND TCT.DVKD_FK=? ";	
			this.getDataSearch().add( "" + this.dvkdId + "" );
		}

		if (this.thang.length() > 0) {
//			condition = condition + " AND TCT.THANG ='" + this.thang + "' ";
			
			condition = condition + "\n AND TCT.THANG = ? ";	
			this.getDataSearch().add( "" + this.thang + "" );
		}

		if (this.nam.length() > 0) {
//			condition = condition + " AND TCT.NAM ='" + this.nam + "' ";
			
			condition = condition + "\n AND TCT.NAM =? ";	
			this.getDataSearch().add( "" + this.nam + "" );
		}

		query = query + condition;
		query+=" order by cast(TCT.NAM as int) desc,cast(TCT.THANG as int)   desc";
		System.out.println("TctList123:" +query);
//		this.tct = this.db.get(query); 
		this.tct = this.db.getByPreparedStatement(query, this.dataSearch);
	}

	public void closeDB() {
		if (this.db != null)
			this.db.shutDown();
	}

	public List<Object> getDataSearch() {
		return dataSearch;
	}

	public void setDataSearch(List<Object> dataSearch) {
		this.dataSearch = dataSearch;
	}
}
