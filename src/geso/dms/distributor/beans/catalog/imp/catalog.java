package geso.dms.distributor.beans.catalog.imp;

import geso.dms.distributor.beans.catalog.*;
import geso.dms.center.db.sql.dbutils;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class catalog implements Icatalog, Serializable
{
	private static final long serialVersionUID = -9217977546733690415L;


	String id;
	String ten;
	String chungloai;
	String duongdan;
	String ghichu;
	String userId;
	String msg = "";
	String nhanhang;
	String ma;
	
	dbutils db;

	public catalog(String[] param)
	{
		this.id = param[0];
		this.ten = param[1];
		this.chungloai = param[2];
		this.duongdan = param[3];
		this.ghichu = param[4];
		this.ten = param[5];
		this.nhanhang = param[6];
		this.ma = param[7];
		
		this.db = new dbutils();
	}

	public catalog()
	{
		this.id = "";
		this.ten ="";
		this.chungloai = "";
		this.duongdan = "";
		this.ghichu = "";
		this.ten ="";
		this.nhanhang="";
		this.ma="";
		this.db = new dbutils();
	}
	public String getNhanhang() {
		return nhanhang;
	}

	public void setNhanhang(String nhanhang) {
		this.nhanhang = nhanhang;
	}

	public String getMa() {
		return ma;
	}

	public void setMa(String ma) {
		this.ma = ma;
	}

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	public String getChungloai() {
		return chungloai;
	}

	public void setChungloai(String chungloai) {
		this.chungloai = chungloai;
	}

	public String getDuongdan() {
		return duongdan;
	}

	public void setDuongdan(String duongdan) {
		this.duongdan = duongdan;
	}

	public String getGhichu() {
		return ghichu;
	}

	public void setGhichu(String ghichu) {
		this.ghichu = ghichu;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}


	
	public boolean saveNsp()
	{
		String query;

		try
		{
			if(this.ten.trim().length() <= 0)
			{
				this.msg = "Vui lòng nhập tên";
				return false;
			}
			
			/*if(this.chungloai.trim().length() <= 0)
			{
				this.msg = "Vui lòng nhập chủng loại";
				return false;
			}*/
			
			if(this.ma.trim().length() <= 0)
			{
				this.msg = "Vui lòng Nhập Mã";
				return false;
			}
			if(this.nhanhang.trim().length() <= 0)
			{
				this.msg = "Vui lòng Nhập Nhãn hàng";
				return false;
			}
			
			System.out.println("query là 1 ");
			this.db.getConnection().setAutoCommit(false);

			query="select count(*) as sl from Catalog where MA='"+this.ma.trim()+"'";
			
			ResultSet ckma=db.get(query);
			
			ckma.next();
			
			int sl=ckma.getInt("sl");
			System.out.println("query là 2 " +sl);
			ckma.close();
			
			if(sl>0)
			{
				this.msg = " Mã Catalog này đã tồn tại vui lòng đổi mã khác. ";
				db.getConnection().rollback();
				return false;
			}
			
			/*query = "insert into Catalog( ten,CHUNGLOai_fk,ghichu,duongdan,nguoitao,nguoisua,ngaytao,ngaysua,nhanhang,Ma) "
					+ " values(N'" + this.ten + "', N'" + this.chungloai + "', N'" + this.ghichu + "',N'"+this.duongdan+"', '" + this.userId + "', '" + this.userId + "','" + getDateTime() + "', '" + getDateTime() + "',N'"+this.nhanhang+"',N'"+this.ma+"')";

			System.out.println("1.Insert Catalog: " + query);*/
			query = "insert into Catalog( ten,CHUNGLOai_fk,ghichu,duongdan,nguoitao,nguoisua,ngaytao,ngaysua,nhanhang,Ma) "
					+ " values(?, ?, ?,?, ?, ?,?, ?,?,?)";
			System.out.println("query là  ");
			
			Object[] data;
			data= new Object[]   { this.ten , this.chungloai , this.ghichu ,this.duongdan, this.userId , this.userId , getDateTime() , getDateTime() ,this.nhanhang,this.ma };
			db.viewQuery(query, data);
		 	
			if( this.db.updateQueryByPreparedStatement(query, data)!=1 ) 
			{
				//this.msg = "Khong the tao moi Catalog: " + query;
				this.msg = "1. Lỗi trong quá trình lưu dữ liệu, vui lòng liên hệ admin để xử lý";
				db.getConnection().rollback();
				return false;
			}

		
			
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		} catch (Exception e1)
		{
			System.out.println("Exception: " + e1.getMessage());
		}

		return true;
	}

	public boolean updateNsp()
	{
		String query;

		try
		{

			if(this.ten.trim().length() <= 0)
			{
				this.msg = "Vui lòng nhập tên";
				return false;
			}
			
			/*if(this.chungloai.trim().length() <= 0)
			{
				this.msg = "Vui lòng nhập chủng loại";
				return false;
			}*/
			
			if(this.ma.trim().length() <= 0)
			{
				this.msg = "Vui lòng Nhập Mã";
				return false;
			}
			if(this.nhanhang.trim().length() <= 0)
			{
				this.msg = "Vui lòng Nhập Nhãn hàng";
				return false;
			}
				
			this.db.getConnection().setAutoCommit(false);

			query="select count(*) as sl from Catalog where MA='"+this.ma.trim()+"' and pk_seq <> "+this.id;
			ResultSet ckma=db.get(query);
			ckma.next();
			int sl=ckma.getInt("sl");
			ckma.close();
			if(sl>0)
			{
				this.msg = " Mã Catalog này đã tồn tại vui lòng đổi mã khác. ";
				db.getConnection().rollback();
				return false;
			}
			
			/*query = "update Catalog set nhanhang=N'"+this.nhanhang+"',Ma=N'"+this.ma+"',duongdan=N'"+this.duongdan+"',ten = N'" + this.ten + "', chungloai_fk = N'" + this.chungloai + "',ghichu = N'" + this.ghichu + "', ngaysua = '" + getDateTime() + "', "+
					"nguoisua = '" + this.userId + "' where pk_seq = "+this.id;*/
			
			query = "update Catalog set nhanhang=?,Ma=?,duongdan=?,ten = ?, chungloai_fk = ?,ghichu = ?, ngaysua = ?, "+
					"nguoisua = ? where pk_seq = ?";

			Object[] data;
			data= new Object[]   { this.nhanhang,this.ma,this.duongdan, this.ten , this.chungloai , this.ghichu , getDateTime() , this.userId ,this.id };
			db.viewQuery(query, data);
		 	
			if( this.db.updateQueryByPreparedStatement(query, data)!=1 ) 
			{
				//this.msg = "Khong the tao moi Catalog: " + query;
				this.msg = "1. Lỗi trong quá trình lưu dữ liệu, vui lòng liên hệ admin để xử lý";
				db.getConnection().rollback();
				return false;
			}

			
			
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		} catch (Exception e1)
		{
			System.out.println("Exception: " + e1.getMessage());
		}

		return true;
	}

	
	

	public void init(){
		String query = "select a.nhanhang,a.ma,a.pk_seq,a.ten,chungloai_fk,ghichu,duongdan,a.ngaytao,a.ngaysua,b.ten as nguoitao,c.ten as nguoisua from Catalog a inner join nhanvien b on a.nguoitao=b.pk_seq inner join nhanvien c on c.pk_seq=a.nguoisua where a.pk_seq= "+this.id;
		System.out.println("init "+query);
		ResultSet rs=db.get(query);
		try {
			rs.next();
			this.ten=rs.getString("ten");
			this.chungloai=rs.getString("chungloai_fk");
			this.ghichu=rs.getString("ghichu");
			this.duongdan=rs.getString("duongdan");
			this.nhanhang=rs.getString("nhanhang");
			this.ma=rs.getString("ma");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	

	public void createRS()
	{
		
		//createSpRS();
	}

	
	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	
}

