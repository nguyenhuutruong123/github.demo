package geso.dms.center.beans.nganhang.imp;

import geso.dms.center.beans.nganhang.IErpNganHang;
import geso.dms.center.db.sql.dbutils;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ErpNganHang implements IErpNganHang {
	dbutils db; 
	String ID;
	String MA;
	String TEN;
	String NGAYTAO;
	String NGAYSUA;
	String NGUOITAO;
	String NGUOISUA;
	String userTen;
	String userId;
	String Msg;
	String Trangthai;
	

	public ErpNganHang(String id) {
		db = new dbutils();
		this.ID=id;
		this.MA = "";
		this.TEN = "";
		this.NGAYTAO = "";
		this.NGAYSUA = "";
		this.NGUOITAO = "";
		this.NGUOISUA = "";
		this.Trangthai = "1";
		this.userId = "";
		this.Msg = "";
	}

	public ErpNganHang() {
		db = new dbutils();
		this.ID = "";
		this.MA = "";
		this.TEN = "";
		this.NGAYTAO = "";
		this.NGAYSUA = "";
		this.NGUOITAO = "";
		this.NGUOISUA = "";
		this.Trangthai = "1";
		this.userId = "";
		this.Msg = "";
	}

	
	public String getID() {
		return ID;
	}

	
	public String getMA() {
		return MA;
	}

	
	public String getTEN() {
		return TEN;
	}

	
	public String getNGAYTAO() {
		return NGAYTAO;
	}

	
	public String getNGAYSUA() {
		return NGAYSUA;
	}

	
	public String getNGUOITAO() {
		return NGUOITAO;
	}

	
	public String getNGUOISUA() {
		return NGUOISUA;
	}

	public String getMsg() {
		return Msg;
	}

	
	public String gettrangthai() {
		return Trangthai;
	}

	
	public void setID(String ID) {
		this.ID = ID;
	}

	
	public void setMA(String MA) {
		this.MA = MA;
	}

	
	public void setTEN(String TEN) {
		this.TEN = TEN;
	}

	
	public void setNGAYTAO(String NGAYTAO) {
		this.NGAYTAO = NGAYTAO;
	}

	
	public void setNGAYSUA(String NGAYSUA) {
		this.NGAYSUA = NGAYSUA;
	}

	
	public void setNGUOITAO(String NGUOITAO) {
		this.NGUOITAO = NGUOITAO;
	}

	
	public void setNGUOISUA(String NGUOISUA) {
		this.NGUOISUA = NGUOISUA;
	}

	
	public void setTrangthai(String trangthai) {
		this.Trangthai = trangthai;
	}

	public void setMsg(String Msg) {
		this.Msg = Msg;
	}
	
	public boolean themMoiMa() {
		if (!CheckUnique())
		{
			this.Msg = "Ma nay da duoc su dung,vui long chon ma khac";
			return false;
		}
		dbutils db = new dbutils();
		String query = "insert erp_nganhang(ma, ten,ngaytao, ngaysua, nguoitao, nguoisua, trangthai) values('"
				+ this.MA
				+ "',N'"
				+ this.TEN
				+ "','"+this.NGAYTAO+"','"+this.NGAYTAO+"','"+this.NGUOITAO+"','"+this.NGUOITAO+"', '"
				+ this.Trangthai + "')";
		System.out.println("Query them moi : " + query);
		if (db.update(query)) {
			return true;
		} else {
			
			this.Msg = "Khong the them moi ngan hang "+query;
			return false;
		}
	}

	public boolean UpdateMa() {
		try
		{	
			if (!CheckUnique())
			{
				this.Msg = "Ma nay da duoc su dung,vui long chon ma khac";
				return false;
			}
			
			dbutils db = new dbutils();
			String query = "UPDATE ERP_NGANHANG " + "SET TEN = N'" + this.TEN + "',TRANGTHAI = '"
					+ this.Trangthai + "' ,MA = '"+ this.MA +"', NGUOISUA = '"+this.NGUOISUA+"'," +
					" NGAYSUA = '"+this.NGAYSUA+"' " 
					+ "WHERE PK_SEQ = '" + this.ID + "' ";
	
			System.out.println("Query update is: " + query);
			if (!db.update(query)) 
			{
				this.Msg = "Khong the cap nhat ngan hang "+query;
				return false;
			}
		}
		catch (Exception e) 
		{
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			this.setMsg(e.toString());
			System.out.println("Loi Update "+ e.toString());			
			return false;
		}
		return true;		
	}

	
	public void init() {
		
		String query = "select * from erp_nganhang where pk_seq='" + this.ID + "'";
		ResultSet nh = db.get(query);
		try {
			if (nh.next()) {
				this.ID = nh.getString("pk_seq");
				this.MA = nh.getString("MA");
				this.TEN = nh.getString("TEN");
				this.NGAYTAO = nh.getString("NGAYTAO");
				this.NGAYSUA = nh.getString("NGAYSUA");
				this.NGUOITAO = nh.getString("NGUOITAO");
				this.NGUOISUA = nh.getString("NGUOISUA");
				this.Trangthai = nh.getString("trangthai");
			}
		} catch (Exception er) {
			this.Msg = "Loi" + er.toString();
		}
	}


	public void DBClose() 
	{
					
		if(db != null)
		{
			db.shutDown();
		}
	}


	public void setUserid(String user) {
		
		this.userId = user;
	}


	public String getUserid() {
		
		return userId;
	}


	public void setUserTen(String userten) {
		
		this.userTen = userten;
	}


	public String getUserTen() {
		
		return userTen;
	}

	public boolean CheckUnique()
	{
		String query = "";
		if (this.ID.length() > 0)
			query = "Select count(*) as count From Erp_NganHang Where MA=N'" + this.MA + "' AND PK_SEQ !='"
					+ this.ID + " '";
		else
			query = "Select count(*) as count From Erp_NganHang Where MA=N'" + this.MA + "' ";
		System.out.println("____Kiem tra rang buoc_____ " + query);
		int count = 0;
		ResultSet rs = this.db.get(query);
		if (rs != null)
			try
			{
				while (rs.next())
				{
					count = rs.getInt("count");
				}
				rs.close();
				if (count > 0)
					return false;
			} catch (SQLException e)
			{
				return false;
			}
		return true;
	}
}
