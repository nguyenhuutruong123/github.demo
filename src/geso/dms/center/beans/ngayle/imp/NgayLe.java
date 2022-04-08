package geso.dms.center.beans.ngayle.imp;
import geso.dms.center.beans.ngayle.INgayLe;
import geso.dms.center.beans.nhacungcap.INhacungcap;
import geso.dms.center.db.sql.*;
import geso.dms.center.util.Utility;

import java.sql.ResultSet;

import com.nhat.replacement.TaskReplacement;

public class NgayLe implements INgayLe
{
	private static final long serialVersionUID = -9217977546733610214L;
	String id;
	String userId = "";
	String ten;
	String diachi;
	String masothue;
	String tenviettat;
	String trangthai;
	String ngaytao;
	String ngaysua;
	String diengiai = "";
	String ngayle = "";
	String nguoitao;
	String nguoisua;
	String sotaikhoan;
	String dienthoai;
	String fax;
	String nguoidaidien;
	String msg;
	dbutils db;
	
	public NgayLe(String[] param)
	{
		this.id = param[0];
		this.ten = param[1];
		this.diachi = param[2];
		this.masothue = param[3];
		this.tenviettat = param[4];
		this.trangthai = param[5];
		this.ngaytao = param[6];
		this.nguoitao = param[7];
		this.ngaysua = param[8];
		this.nguoisua = param[9];
		this.sotaikhoan=param[10];
		this.dienthoai=param[11];
		this.fax=param[12];
		this.nguoidaidien=param[13];
		this.msg = "";
		this.db = new dbutils();
	}
	
	public NgayLe()
	{
		this.id = "";
		this.ten = "";
		this.tenviettat = "";
		this.diachi = "";
		this.masothue = "";
		this.tenviettat = "";
		this.trangthai = "";
		this.ngaytao = "";
		this.nguoitao = "";
		this.ngaysua = "";
		this.nguoisua = "";
		this.msg = "";
		this.sotaikhoan="";
		this.dienthoai="";
		this.fax="";
		this.nguoidaidien="";
		this.db = new dbutils();
	}

	
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDiengiai() {
		return diengiai;
	}

	public void setDiengiai(String diengiai) {
		this.diengiai = diengiai;
	}

	public String getNgayle() {
		return ngayle;
	}

	public void setNgayle(String ngayle) {
		this.ngayle = ngayle;
	}

	public String getId()
	{
		return this.id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getTen()
	{
		return this.ten;
	}

	public void setTen(String ten)
	{
		this.ten = ten;
	}
	
	public String getDiachi()
	{
		return this.diachi;
	}
	
	public void setDiachi(String diachi)
	{
		this.diachi = diachi;
	}
	
	public String getMasothue()
	{
		return this.masothue;
	}
	
	public void setMasothue(String masothue)
	{
		this.masothue = masothue;
	}
	
	public String getTenviettat()
	{
		return this.tenviettat;
	}

	public void setTenviettat(String tenviettat)
	{
		this.tenviettat = tenviettat;
	}

	public String getTrangthai()
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai)
	{
		this.trangthai = trangthai;
	}

	public String getNgaytao()
	{
		return this.ngaytao;
		
	}

	public void setNgaytao(String ngaytao)
	{
		this.ngaytao = ngaytao;
	}
	
	public String getNgaysua()
	{
		return this.ngaysua;
		
	}

	public void setNgaysua(String ngaysua)
	{
		this.ngaysua = ngaysua;
	}
	
	public String getNguoitao()
	{
		return this.nguoitao;
	}
	
	public void setNguoitao(String nguoitao)
	{
		this.nguoitao = nguoitao;
	}

	public String getNguoisua()
	{
		return this.nguoisua;
	}

	public void setNguoisua(String nguoisua)
	{
		this.nguoisua = nguoisua;
	}
	
	public String getMessage()
	{
		return this.msg;
	}

	public void setMessage(String msg)
	{
		this.msg = msg;
	}
	
	boolean kiemtraten(String ngayle)
	{

		String sql ="select count(*)c from ngayle where ngayle = '"+ ngayle +"'" ;

		ResultSet rs = db.get(sql);
		try {
			while(rs.next()){
				int temp = rs.getInt("c");
				if( temp > 1 )
					return false;
			}

			if(rs!=null) rs.close(); 
			return true;
		} catch(Exception e) {		
			e.printStackTrace();
			return false;
		}
	}
	public boolean saveNewNgayLe(){
	//	
		if(!kiemtraten(ngayle)){
			this.msg = "Ngày này đã tồn tại!";
			return false;
		}
		String query;
		
		try 
		{
			db.getConnection().setAutoCommit(false);// new 1 transaction
			
			/*query = "\n insert ngayle (ngaytao, ngaysua, nguoitao, nguoisua, ngayle, diengiai)  " +
					"\n select  '" + Utility.getNgayHienTai() + "','" + Utility.getNgayHienTai() + "'"
							+ ",'" + this.userId + "'," +
					"\n '" + this.userId + "','" + this.ngayle + "', N'" + this.diengiai + "'";
			System.out.print("Query Insert: "+query);
			if (!db.update(query)){
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
				this.msg = "Xảy ra lỗi khi tạo ngày mới (1) ";
				return false;			
			}*/
			
			String cauQuery = "insert ngayle (ngaytao, ngaysua, nguoitao, nguoisua, ngayle, diengiai)  select ?,? ,?,  ?,?, ?" ;
			Object[] dataQuery = {Utility.getNgayHienTai(),Utility.getNgayHienTai(),this.userId,this.userId,this.ngayle,this.diengiai}; 
			if (this.db.updateQueryByPreparedStatement(cauQuery,dataQuery)!= 1) {
				dbutils.viewQuery(cauQuery,dataQuery);
				this.msg = "Error At : 16402e1a-c805-41a2-80b9-7980242bdcdf";
				this.db.getConnection().rollback();
				return false;
			}
 	
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return true;

		} catch(Exception e) {
			e.printStackTrace();
			this.msg = "Exception Create!";
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return false;
		}
	}
	
	public boolean UpdateNcc(){
		
		String query;		
		if(!kiemtraten(ngayle)){
			this.msg = "Ngày này đã tồn tại!";
			return false;
		}
		
		try{
			db.getConnection().setAutoCommit(false);
			
			/*query = "\n update ngayle set diengiai = N'"+this.diengiai + "', ngaysua = '" + this.ngaysua + "', nguoisua = '" + userId + "'"  +
					"\n where pk_seq =" + this.id;
			System.out.print("Update: " + query);
			if(!db.update(query)){
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
				this.msg = "Lỗi update ngày lễ: "+ query;
				return false;
			}*/
			
			String cauQuery = "update ngayle set ngayle=?, diengiai = ?, ngaysua = ?, nguoisua = ?  where pk_seq =?" ;
			Object[] dataQuery = {this.ngayle,this.diengiai,this.ngaysua,userId,this.id}; 
			if (this.db.updateQueryByPreparedStatement(cauQuery,dataQuery)!= 1) {
				dbutils.viewQuery(cauQuery,dataQuery);
				this.msg = "Error At : 66598ef0-ac2a-42d7-91b5-11f3a9989258";
				this.db.getConnection().rollback();
				return false;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return true;
			
		}catch(Exception e){
			e.printStackTrace();
			db.update("rollbak");
			this.msg = "Exception Update!";
			return false;
		}
	}
	
	public void init(){
		String query = "select pk_seq, ngayle, diengiai from ngayle where pk_seq = "+this.id;
		System.out.println(query);
   		ResultSet ncc = this.db.get(query);
   		
   		try{
   			ncc.next();
   			this.id = ncc.getString("pk_seq");
   			this.ngayle = ncc.getString("ngayle");
   			this.diengiai = ncc.getString("diengiai");
   			if(ncc!=null) ncc.close();   			        	       
   		}
   		catch(Exception e){
   			e.printStackTrace();
   		}
	}
	
	public void DBClose(){				
		this.db.shutDown();
	}

	
	public String getSotaikhoan() {
		
		return this.sotaikhoan;
	}

	
	public void setSotaikhoan(String sotaikhoan) {
		
		this.sotaikhoan=sotaikhoan;
	}

	
	public String getDienthoai() {
		
		return this.dienthoai;
	}

	
	public void setDienthoai(String dienthoai) {
		
		this.dienthoai=dienthoai;
	}

	
	public String getFax() {
		
		return this.fax;
	}

	
	public void setFax(String fax) {
		
		this.fax=fax;
	}

	
	public String getNguoidaidien() {
		
		return this.nguoidaidien;
	}

	
	public void setNguoidaidien(String nguoidaidien) {
		
		this.nguoidaidien=nguoidaidien;
	}
	
	public static void main(String[] args) {
		TaskReplacement t = new TaskReplacement();
		String in1 = "\"  update ngayle set diengiai = N'\"+this.diengiai + \"', ngaysua = '\" + this.ngaysua + \"', nguoisua = '\" + userId + \"'\"  +\r\n" + 
				"					\"\\n where pk_seq ='\" + this.id +\"'";
		
		System.out.println(t.taoQuery(in1));
	
	}
}
