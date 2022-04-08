package geso.dms.center.beans.chungloai.imp;
import java.sql.ResultSet;
import java.util.Hashtable;

import geso.dms.center.beans.chungloai.IChungloai;
import geso.dms.center.db.sql.*;


public class Chungloai implements IChungloai
{
	private static final long serialVersionUID = -9217977546733610215L;
	String id;
	String chungloai;
	String nhanhang;
	String ngaytao;
	String ngaysua;
	String nguoitao;
	String nguoisua;
	String trangthai;
	String nhanhangId;
	String[] nhanhangSelected;
	ResultSet nh_cllist;
	ResultSet nhlist;
	String Ma = "";
	String msg;
	dbutils db;
	
	public Chungloai(String[] param)
	{
		this.id = param[0];		
		this.chungloai = param[1];
		this.nhanhang  = param[2];
		this.ngaytao = param[3];
		this.ngaysua = param[4];
		this.nguoitao = param[5];
		this.nguoisua = param[6];
		this.trangthai = param[7];
		this.nhanhangId = param[8];	
		this.Ma = param[9];
		this.msg = "";
		this.db = new dbutils();
	}
	
	public Chungloai()
	{
		this.id = "";		
		this.chungloai = "";
		this.nhanhang  = "";
		this.ngaytao = "";
		this.ngaysua = "";
		this.nguoitao = "";
		this.nguoisua = "";
		this.trangthai = "";
		this.nhanhangId = "";		
		this.msg = "";
		this.db = new dbutils();
	}

	public String getId()
	{
		return this.id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getChungloai()
	{
		return this.chungloai;
	}

	public void setChungloai(String chungloai)
	{
		this.chungloai = chungloai;
	}

	public String  getNhanhang(){
		return this.nhanhang;
	}
	
	public void setNhanhang(String nhanhang){
		this.nhanhang = nhanhang;
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
	
	public String getTrangthai()
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai)
	{
		this.trangthai = trangthai;
	}


	public String  getNhanhangId(){
		return this.nhanhangId;
	}
	
	public void setNhanhangId(String nhanhangId){
		this.nhanhangId = nhanhangId;
	}
	
	
	public void setNh_clList(ResultSet nh_cllist){
		this.nh_cllist = nh_cllist;
	}

	public ResultSet  getNh_clList(){
		return this.nh_cllist;
	}

	public ResultSet  getNhList(){
//		String query = "select distinct a.pk_seq, a.ten from nhanhang a,donvikinhdoanh b where a.dvkd_fk = b.pk_seq and a.trangthai = '1' order by a.ten" ;
//		return db.get(query);
//	
		return this.nhlist;
	}
	
	public void setNhList(ResultSet nhlist){
		this.nhlist = nhlist;
	}

	public Hashtable<Integer, String>  getNhanhangSelected(){
		Hashtable<Integer, String> selected = new Hashtable<Integer, String>();
		if(this.nhanhangSelected != null){
			int size = (this.nhanhangSelected).length;
			int m = 0;
			while(m < size){
				selected.put(new Integer(m), nhanhangSelected[m]) ;
				m++;
			}
		}else{
			selected.put(new Integer(0), "null");
		}
		return selected;
	}
	
	
	public void setNhanhangSelected(String[] nhanhangSelected){
		this.nhanhangSelected = nhanhangSelected;
	}

	public String getMessage()
	{
		return this.msg;
	}

	public void setMessage(String msg)
	{
		this.msg = msg;
	}
	
	public boolean saveNewChungloai(){
			
		try{
		// Insert data set into table "Chungloai"
		String query ="insert into chungloai(ten,ngaytao,ngaysua,nguoitao,nguoisua,trangthai) values(N'" + this.chungloai + "','"+ this.ngaytao + "','" + this.ngaysua + "','" + this.nguoitao + "','" + this.nguoisua + "','" + this.trangthai + "')";
		
		if (!db.update(query)){
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			this.msg = "Khong the luu vao table 'Chungloai' , " + this.chungloai + " ," + this.ngaytao + ", " + this.ngaysua + ", " + this.nguoitao + ", " + this.nguoisua + ", " + this.trangthai +"";
			return false;			
		}
		
		query = "select IDENT_CURRENT('chungloai') as clId";
		ResultSet rs = this.db.get(query);
			rs.next();
			this.id = rs.getString("clId");
			String sql_update_smartid="update chungloai set smartid='"+this.id+"' where pk_seq=" + this.id;
			try{
			if(!db.update(sql_update_smartid)){
				//geso.dms.center.util.Utility.rollback_throw_exception(db);
				System.out.println("Khong The Thuc Hien Cap Nhat SmartId Cho bang Chung Loai,Vui Long Lien He Voi Admin ");
				//return false;
			}
			}catch(Exception er){
				
			}
			// Insert data set into table "nhanhang_chungloai"
			int size = this.nhanhangSelected.length;
			int m = 0;
			while (m < size ){
				String nhId = (this.nhanhangSelected[m]);
				query = "insert into nhanhang_chungloai(cl_fk, nh_fk) values('" + this.id + "','" + nhId + "')";

				if (!db.update(query)){
					geso.dms.center.util.Utility.rollback_throw_exception(db);
					this.msg = this.msg + " Không thể lưu vào table Nhanhang_Chungloai";
					return false;			
				}
				m++;
			}
		}
		catch(Exception e)
		{
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return false;
		}
		return true;
	}
	
	public boolean UpdateChungloai(){
		// Update table "Chungloai"
		/*String query = "update chungloai set ten=N'" + this.chungloai + "',  ngaysua = '" + this.ngaysua + "',  nguoisua = '" + this.nguoisua + "', trangthai = '" + this.trangthai + "',smartid = '" + this.Ma + "' where pk_seq = '" + this.id + "'" ;*/
		String query = "update chungloai set   ngaysua = '" + this.ngaysua + "',  nguoisua = '" + this.nguoisua + "', trangthai = '" + this.trangthai + "' where pk_seq = '" + this.id + "'" ;
		if(!db.update(query)){
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			this.msg = "Không thể cập nhật table 'Chungloai' , " + this.chungloai + " ," + this.ngaytao + ", " + this.ngaysua + ", " + this.nguoitao + ", " + this.nguoisua + ", " + this.trangthai +"";
			return false; 
		}
			
		query = "delete from nhanhang_chungloai where cl_fk= '" + this.id + "'" ;
		db.update(query);
		
		// Update table "nhanhang_chungloai"
		if (this.nhanhangSelected != null){
			int size = (this.nhanhangSelected).length;
			int m = 0;
			while(m < size){
//				if (this.nhanhangSelected[m].length()>0){
					query = "insert into nhanhang_chungloai values('" + this.id + "', '" + this.nhanhangSelected[m] + "')"; 				
					db.update(query);				
//				}

				m++;
			}
		}
		
		return true;
	}
		
	public void createNhList(){
	
		String query = "select a.pk_seq, a.ten from nhanhang a, nhanhang_chungloai b where a.trangthai ='1' and b.cl_fk = '"+ this.id +"' and a.pk_seq = b.nh_fk order by ten";
		System.out.println("NHanhang "+query);
		this.nh_cllist = db.get(query);
		query = "select pk_seq, ten from nhanhang where trangthai = '1' and pk_seq not in (select nh_fk from nhanhang_chungloai where cl_fk='" + this.id + "')";
		System.out.println("NHanhang "+query);
		this.nhlist  = db.get(query);
	}

	public void DBClose(){
		try{
			if(this.nh_cllist != null) this.nh_cllist.close();
			if(this.nhlist != null) this.nhlist.close();
			
			this.db.shutDown();
		}catch(Exception e){}
	}


	public String getMa() {
		
		return this.Ma;
	}


	public void setMa(String Ma) {
		this.Ma = Ma;
		
	}
	
}


