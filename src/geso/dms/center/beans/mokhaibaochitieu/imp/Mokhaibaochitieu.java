package geso.dms.center.beans.mokhaibaochitieu.imp;
import geso.dms.center.beans.mokhaibaochitieu.*;
import geso.dms.center.db.sql.*;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Mokhaibaochitieu implements IMokhaibaochitieu{
	public String userId;
	public String dvkdId;
	public ResultSet dvkd;
	public String nppId;
	public ResultSet npp;
	public dbutils db;
	public String nam;
	public String thang;
	public String msg;
	
	public Mokhaibaochitieu(){
		this.userId = "";
		this.dvkdId = "";
		this.nppId = "";
		this.nam = this.getDateTime().substring(0, 4);
		this.thang = "" + Integer.parseInt(this.getDateTime().substring(5,7));
		System.out.println(this.thang);
		this.msg = "";
		this.db = new dbutils();
	}
	
	public String getUserId(){
		return this.userId;
	}
	
	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getDvkdId(){
		return this.dvkdId;
	}
	
	public void setDvkdId(String dvkdId){
		this.dvkdId = dvkdId;
	}

	public ResultSet getDvkd(){
		return this.dvkd;
	}
	
	public void setDvkd(ResultSet dvkd){
		this.dvkd = dvkd;
	}
	
	public String getNppId(){
		return this.nppId;
	}
	
	public void setNppId(String nppId){
		this.nppId = nppId;
	}
	
	public String getYear(){
		return this.nam;
	}
	
	public void setYear(String nam){
		this.nam = nam;
	}

	public String getMonth(){
		return this.thang;
	}
	
	public void setMonth(String thang){
		this.thang = thang;
	}

	public String getMsg(){
		return this.msg;
	}
	
	public void setMsg(String msg){
		this.msg = msg;
	}

	public ResultSet getNpp(){
		return this.npp;
	}
	
	public void setNpp(ResultSet npp){
		this.npp = npp;
	}
	
	public void init(){
		String sql = "SELECT PK_SEQ, DONVIKINHDOANH FROM DONVIKINHDOANH";
		this.dvkd = this.db.get(sql);
		
		if(this.dvkdId.length() > 0){
			sql = 	"SELECT NPP.PK_SEQ, NPP.TEN " +
					"FROM NHAPP_NHACC_DONVIKD NPP_NHACC_DONVIKD " +
					"INNER JOIN NHACUNGCAP_DVKD NCC_DVKD ON NCC_DVKD.PK_SEQ = NPP_NHACC_DONVIKD.NCC_DVKD_FK AND NCC_DVKD.DVKD_FK='"+ this.dvkdId +"' " +
					"INNER JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ = NPP_NHACC_DONVIKD.NPP_FK " +
					"INNER JOIN CHITIEUNPP CTNPP ON CTNPP.NHAPP_FK = NPP.PK_SEQ AND CTNPP.THANG ='" + this.thang + "' AND CTNPP.NAM ='" + this.nam + "' AND CTNPP.TRANGTHAI='1' " +
					"WHERE NPP.TRANGTHAI=1 ";
			System.out.println(sql);
		}
		
		
		this.npp = this.db.get(sql);
		
	}
	
	public void Execute(){
		try{
			String sql = "UPDATE CHITIEUNPP SET TRANGTHAI = '0' " +
						 "WHERE NHAPP_FK='"+ this.nppId +"' AND NAM = '" + this.nam + "' AND THANG = '" + this.thang + "' AND DVKD_FK='" + this.dvkdId + "'";
			System.out.println(sql);
			this.db.update(sql);
		
			sql = "SELECT TEN FROM NHAPHANPHOI WHERE PK_SEQ='" + this.nppId + "'";
			ResultSet rs = this.db.get(sql);
			rs.next();
			
			String nppTen = rs.getString("ten");
			this.msg = "Đã cho phép Nhà phân phối " + nppTen + " khai báo lại chỉ tiêu tháng " + this.thang + " năm " + this.nam + "";
		}catch(Exception e){}
	}
	
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	public void DBClose(){
		try{
			if(this.dvkd != null) this.dvkd.close();
			if(this.npp != null) this.npp.close();
			if(this.db != null)	this.db.shutDown();
		}catch(Exception e){}
	}
}
