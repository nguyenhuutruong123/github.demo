package geso.dms.center.beans.bm.imp;

import geso.dms.center.beans.bm.IBmList;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import geso.dms.center.db.sql.*;
import geso.dms.center.util.Utility;
public class BmList implements IBmList {
	
	List<Object> dataSearch = new ArrayList<Object>(); 
	
	public List<Object> getDataSearch() {
		return dataSearch;
	}
	public void setDataSearch(List<Object> dataSearch) {
		this.dataSearch = dataSearch;
	}
	
	public String bmTen;
	public String dienthoai;
	public String email;
	public String diachi;
	public String kbhId;
	public String dvkdId;
	public String vungId;
	public String trangthai;
	public String msg;
	public ResultSet kbh;
	public ResultSet vung;
	public ResultSet dvkd;
	public ResultSet bmlist;
	public dbutils db;
	
	public BmList(){
		this.bmTen = "";
		this.dienthoai = "";
		this.email = "";
		this.diachi = "";
		this.kbhId = "";
		this.dvkdId = "";
		this.vungId = "";
		this.trangthai = "";
		this.msg = "";
		this.db = new dbutils();
	}
	
	public String getTen(){
		return this.bmTen;
	}
	
	public void setTen(String bmTen){
		this.bmTen = bmTen;
	}
	
	public String getDienthoai(){
		return this.dienthoai;
	}
	
	public void setDienthoai(String dienthoai){
		this.dienthoai = dienthoai;
	}
	
	public String getEmail(){
		return this.email;
	}
	
	public void setEmail(String email){
		this.email = email;
	}

	public String getDiachi(){
		return this.diachi;
	}
	
	public void setDiachi(String diachi){
		this.diachi = diachi;
	}

	public String getKbhId(){
		return this.kbhId;
	}
	
	public void setKbhId(String kbhId){
		this.kbhId = kbhId;
	}

	public String getDvkdId(){
		return this.dvkdId;
	}
	
	public void setDvkdId(String dvkdId){
		this.dvkdId = dvkdId;
	}

	public String getVungId(){
		return this.vungId;
	}
	
	public void setVungId(String vungId){
		this.vungId = vungId;
	}

	public String getTrangthai(){
		return this.trangthai;
	}
	
	public void setTrangthai(String trangthai){
		this.trangthai = trangthai;
	}

	public String getMsg(){
		return this.msg;
	}
	
	public void setMsg(String msg){
		this.msg = msg;
	}

	public ResultSet getKbh(){
		return this.kbh;
	}
	
	public void setKbh(ResultSet kbh){
		this.kbh = kbh;
	}


	public ResultSet getDvkd(){
		return this.dvkd;
	}
	
	public void setDvkd(ResultSet dvkd){
		this.dvkd = dvkd;
	}

	public ResultSet getVung(){
		return this.vung;
	}
	
	public void setVung(ResultSet vung){
		this.vung = vung;
	}

	public ResultSet getBmlist(){
		return this.bmlist;
	}
	
	public void setBmlist(ResultSet bmlist){
		this.bmlist = bmlist;
	}
	
	public void init(){
		String sql = 	"SELECT PK_SEQ AS KBHID, TEN AS KBH FROM KENHBANHANG WHERE TRANGTHAI='1' "; //AND PK_SEQ='100000' OR PK_SEQ = '100002'";
		this.kbh = this.db.get(sql);
		
		sql  	=		"SELECT PK_SEQ AS VUNGID, TEN AS VUNG FROM VUNG WHERE TRANGTHAI='1'";
		this.vung = this.db.get(sql);
		
		sql 	= 		"SELECT PK_SEQ AS DVKDID, DONVIKINHDOANH AS DVKD FROM DONVIKINHDOANH WHERE TRANGTHAI='1'";
		this.dvkd = this.db.get(sql);
		
		sql 	=		"SELECT BM.PK_SEQ AS BMID,isnull(BM.smartid,'') as smartid, BM.TEN AS BMTEN, BM.DIENTHOAI, BM.TRANGTHAI, KBH.TEN AS KBH, " + 
						"\n BM.NGAYTAO, NV1.TEN AS NGUOITAO, BM.NGAYSUA, NV2.TEN AS NGUOISUA, "+ // VUNG.PK_SEQ  AS VUNGID," +
						"\n [dbo].[GetVungRSM](bm.pk_seq) as VUNG, DVKD.DONVIKINHDOANH AS DVKD " +
						"\n FROM BM BM " +
						"\n left JOIN KENHBANHANG KBH ON KBH.PK_SEQ = BM.KBH_FK " +
						//"left JOIN BM_CHINHANH BM_CN ON BM_CN.BM_FK = BM.PK_SEQ " +
						//"left JOIN VUNG VUNG ON VUNG.PK_SEQ = BM_CN.VUNG_FK " +
						"\n left JOIN DONVIKINHDOANH DVKD ON DVKD.PK_SEQ = BM.DVKD_FK " +
						"\n INNER JOIN NHANVIEN NV1 ON NV1.PK_SEQ = BM.NGUOITAO " +
						"\n INNER JOIN NHANVIEN NV2 ON NV2.PK_SEQ = BM.NGUOISUA " +
						"\n WHERE BM.TRANGTHAI >= '0' ";
		
		if (this.kbhId.length() > 0)
		{			
			sql = sql + "AND KBH.PK_SEQ = ? ";
			this.getDataSearch().add( "" + this.kbhId  + "" );
		}
		if (this.dvkdId.length() > 0) 
		{
			sql = sql + "AND DVKD.PK_SEQ = ? ";
			this.getDataSearch().add( "" + this.dvkdId  + "" );	
		}
		if (this.trangthai.length() > 0 & !this.trangthai.equals("2")) 
		{
			sql = sql + "AND BM.TRANGTHAI = ? ";
			this.getDataSearch().add(this.trangthai  );
		}
		
		if (this.bmTen.length() > 0) 
		{
			sql = sql + "AND BM.TEN LIKE ? ";
			this.getDataSearch().add( "%" + this.bmTen  + "%" );	
		}
		if (this.dienthoai.length() > 0) 
		{
			sql = sql + "AND BM.DIENTHOAI LIKE ? ";
			this.getDataSearch().add( "%" + this.dienthoai  + "%" );	
		}
		if (this.vungId.length() > 0) 
		{
			sql 	=		"SELECT BM.PK_SEQ AS BMID,isnull(BM.smartid,'') as smartid, BM.TEN AS BMTEN, BM.DIENTHOAI, BM.TRANGTHAI, KBH.TEN AS KBH, " + 
					"\n BM.NGAYTAO, NV1.TEN AS NGUOITAO, BM.NGAYSUA, NV2.TEN AS NGUOISUA, "+ //VUNG.PK_SEQ AS VUNGID," +
					"\n [dbo].[GetVungRSM](bm.pk_seq) as VUNG, "+
					//"\n VUNG.TEN AS VUNG, " +
					"DVKD.DONVIKINHDOANH AS DVKD " +
					"\n FROM BM BM " +
					"\n left JOIN KENHBANHANG KBH ON KBH.PK_SEQ = BM.KBH_FK " +
					"\n left JOIN BM_CHINHANH BM_CN ON BM_CN.BM_FK = BM.PK_SEQ " +
					"\n left JOIN VUNG VUNG ON VUNG.PK_SEQ = BM_CN.VUNG_FK " +
					"\n left JOIN DONVIKINHDOANH DVKD ON DVKD.PK_SEQ = BM.DVKD_FK " +
					"\n INNER JOIN NHANVIEN NV1 ON NV1.PK_SEQ = BM.NGUOITAO " +
					"\n INNER JOIN NHANVIEN NV2 ON NV2.PK_SEQ = BM.NGUOISUA " +
					"\n WHERE BM.TRANGTHAI >= '0' "+
					"\n AND VUNG.PK_SEQ = '" + this.vungId + "' ";
			if (this.kbhId.length() > 0) 
			{
				sql = sql + "AND KBH.PK_SEQ = ? ";
				this.getDataSearch().add( "" + this.kbhId  + "" );	
			}
			if (this.dvkdId.length() > 0) 
			{
				sql = sql + "AND DVKD.PK_SEQ = ? ";
				this.getDataSearch().add( "" + this.dvkdId  + "" );	
			}
			if (this.trangthai.length() > 0 & !this.trangthai.equals("2")) 
			{
				sql = sql + "AND BM.TRANGTHAI = ?";
				this.getDataSearch().add( "" + this.trangthai  + "" );	

			}
			if (this.bmTen.length() > 0) 
			{
				sql = sql + "AND BM.TEN LIKE ? ";
				this.getDataSearch().add( "%" + this.bmTen  + "%" );	
			}
			if (this.dienthoai.length() > 0) 
			{
				sql = sql + "AND BM.DIENTHOAI LIKE ? ";
				this.getDataSearch().add( "%" + this.dienthoai  + "%" );	

			}
			
		}
		/*Utility util = new Utility();
		sql+=" AND BM.PK_SEQ IN "+util.quyen_bm(this.userId)+"";*/
		
		sql = sql + " ORDER BY KBH ASC, DVKD ASC, VUNG ASC, BMTEN ASC, TRANGTHAI DESC";
		
		
		
		System.out.println(sql);
		
		this.bmlist = db.getByPreparedStatement(sql, dataSearch);
		
		
	}
	
	public void Delete(String bmid, String vungId){
		try{
			
			 if(!Utility.KiemTra_PK_SEQ_HopLe(bmid, "BM",  db))
			   {
			   
			    this.msg = "Lỗi ID.";
			    return ; 
			   }
			String sql = "DELETE FROM BM_CHINHANH WHERE BM_FK = '" + bmid + "' ";
			this.db.update(sql);
		
			sql = "SELECT COUNT(*) AS NUM FROM BM_CHINHANH WHERE BM_FK = '" + bmid + "'";
			ResultSet rs = this.db.get(sql);
			rs.next();
		
			if(rs.getString("NUM").equals("0")){
				sql = "DELETE FROM BM WHERE PK_SEQ ='" + bmid + "'";
				this.db.update(sql);				
			}
			rs.close();
		}catch(Exception e){}
	}
	
	public void DBClose(){
		try{
			if(this.dvkd != null) this.dvkd.close();
			if(this.kbh != null) this.kbh.close();
			if(this.bmlist != null) this.bmlist.close();
			if(this.vung != null) this.vung.close();
			if(this.db != null) this.db.shutDown();
		}catch(Exception e){}
	}
	
	String userId;
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
