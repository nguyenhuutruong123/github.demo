package geso.dms.center.beans.bm.imp;

import geso.dms.center.beans.bm.IBm;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import geso.dms.center.db.sql.*;

public class Bm implements IBm {
	public String Id;
	public String bmTen;
	public String dienthoai;
	public String email;
	public String diachi;
	
	public String kbhId;
	public ResultSet kbh;
	
	public String dvkdId;
	public ResultSet dvkd;
	
	public ResultSet ttpp;
	public String ttppId;
	
	public String[] vungId;
	public ResultSet vung;
	String Smartid  ="";
	

	public String trangthai;
	public String msg;
	
	public String userId;
	
	public dbutils db ;
	
	public Bm(){
		this.Id = "";
		this.bmTen = "";
		this.dienthoai = "";
		this.email = "";
		this.diachi = "";
		this.kbhId = "";
		this.dvkdId = "";
		this.ttppId = "";
		this.trangthai = "";
		this.msg = "";
		this.userId = "";
		this.db = new dbutils();
	}
	
	public String getId(){
		return this.Id;
	}
	
	public void setId(String Id){
		this.Id = Id;
	}
	public String getSmartid() {
		return Smartid;
	}

	public void setSmartid(String smartid) {
		Smartid = smartid;
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

	public ResultSet getKbh(){
		return this.kbh;
	}
	
	public void setKbh(ResultSet kbh){
		this.kbh = kbh;
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

	public String[] getVungId(){
		return this.vungId;
	}
	
	public void setVungId(String[] vungId){
		this.vungId = vungId;
	}

	public ResultSet getVung(){
		return this.vung;
	}
	
	public void setVung(ResultSet vung){
		this.vung = vung;
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
	
	public String getUserId(){
		return this.userId;
	}
	
	public void setUserId(String userId){
		this.userId = userId;
	}

	public void init_New(){
		String sql 	= "SELECT PK_SEQ AS KBHID, TEN AS KBH FROM KENHBANHANG WHERE trangthai = '1' "; //PK_SEQ = '100000' OR PK_SEQ = '100002'";
		this.kbh  	= this.db.get(sql);
		
			sql 		= "SELECT PK_SEQ AS DVKDID, DONVIKINHDOANH AS DVKD FROM DONVIKINHDOANH ";//WHERE PK_SEQ = '100000' OR PK_SEQ = '100001'";
		this.dvkd 	= this.db.get(sql);
		
		sql 		= "select a.PK_SEQ,a.MA,a.TEN from TRUNGTAMPHANPHOI a where TRANGTHAI=1 ";
		this.ttpp 	= this.db.get(sql);
		
		if(this.Id.length() <=0){
			sql= "SELECT PK_SEQ AS VUNGID, TEN AS VUNG,'' as ngaybatdau,'' as ngayketthuc  FROM VUNG ORDER BY TEN"; 	
		
		}else{
			sql="SELECT PK_SEQ AS VUNGID, TEN AS VUNG,b.ngaybatdau,b.ngayketthuc "+
			  " FROM VUNG left join BM_CHINHANH b on vung.pk_seq=b.vung_fk WHERE B.BM_FK= "+ this.Id +
			 " UNION  ALL "+
			" SELECT PK_SEQ AS VUNGID, TEN AS VUNG,'' as ngaybatdau,'' as ngayketthuc  FROM VUNG  WHERE PK_SEQ NOT IN (SELECT VUNG_FK FROM BM_CHINHANH WHERE BM_FK ="+this.Id+") "+
			" ORDER BY TEN  ";
		}
		System.out.println(sql);
		this.vung 	= this.db.get(sql);
		
	}
	
	public void init_Update(){
		try{
			String sql 	=	"SELECT BM.PK_SEQ AS BMID, BM.TEN AS BMTEN, BM.DIACHI, BM.DIENTHOAI, BM.EMAIL, BM.TRANGTHAI, KBH.PK_SEQ AS KBHID, " + 
							"BM.NGAYTAO, BM.NGUOITAO, BM.NGAYSUA, BM.NGUOISUA, VUNG.PK_SEQ AS VUNGID,isnull(bm.smartid,'') as smartid," +
							"VUNG.TEN AS VUNG, DVKD.PK_SEQ AS DVKDID, BM.TTPP_FK " +
							"FROM BM BM " +
							"left JOIN KENHBANHANG KBH ON KBH.PK_SEQ = BM.KBH_FK " +
							"left JOIN BM_CHINHANH BM_CN ON BM_CN.BM_FK = BM.PK_SEQ " +
							"left JOIN VUNG VUNG ON VUNG.PK_SEQ = BM_CN.VUNG_FK " +
							"LEFT JOIN DONVIKINHDOANH DVKD ON DVKD.PK_SEQ = BM.DVKD_FK " +
							"WHERE BM.PK_SEQ = '" + this.Id + "'";
			ResultSet rs = this.db.get(sql);
			rs.next();
			
			System.out.println("init_Update: ' " + sql + " ' ");
			
			this.bmTen = rs.getString("bmten");
			this.diachi = rs.getString("diachi");
			this.dienthoai = rs.getString("dienthoai");
			this.email = rs.getString("email");
			this.trangthai = rs.getString("trangthai");
			this.kbhId = rs.getString("kbhid");
			this.dvkdId = rs.getString("dvkdid");
			this.ttppId = rs.getString("TTPP_FK");
			this.Smartid = rs.getString("smartid");
			rs.close();
			
			sql = "SELECT COUNT(*) AS NUM FROM BM_CHINHANH WHERE BM_FK = '" + this.Id + "'";
			rs = this.db.get(sql);
			rs.next();
			int num = Integer.parseInt(rs.getString("NUM"));
			System.out.println("" + num);
			
			sql = "SELECT BM_FK AS BMID, VUNG_FK AS VUNGID FROM BM_CHINHANH WHERE BM_FK = '" + this.Id + "'";
			System.out.println(sql);
			
			rs = this.db.get(sql);
			
			this.vungId = new String[num];
			
			int i = 0;
			while(rs.next()){
				System.out.println(this.vungId[i]);
				this.vungId[i] = rs.getString("VUNGID");
				i++;
			}
			
			this.init_New();
			
		}catch(Exception e){}
	}
	
	public boolean Save(HttpServletRequest request){
		String sql = "";
		try{
			 this.db.getConnection().setAutoCommit(false);

			 if(this.ttppId.equals(""))
			 {
				 this.ttppId = dbutils.NULLVAL;
			 }
				List<Object> data = new ArrayList<Object>();	

			 if(this.Id.length() == 0){
				 sql  = "INSERT INTO BM (SMARTID, TEN, DIACHI, EMAIL, DIENTHOAI, TRANGTHAI, NCC_FK, KBH_FK, DVKD_FK, NGAYTAO, NGAYSUA, NGUOITAO, NGUOISUA, TTPP_FK) " +
				 	
				 "\n	select ?,?,?,?,?,1,100022,?,?,?,?,?,?,?";
//				 "VALUES(N'"+ this.bmTen + "',N'" + this.diachi + "','" + this.email + "','" + this.dienthoai + "','1','100022'," + (this.kbhId.length() <= 0 ?null:this.kbhId) + ",'" + this.dvkdId + "','" + this.getDateTime() + "','" + this.getDateTime()+"','" + this.userId + "','" + this.userId + "', " + this.ttppId
//				 				+ ")";
				 
				 data.clear();
				 	data.add(this.Smartid);
					data.add(this.bmTen);
					data.add(this.diachi);
					data.add(this.email);
					data.add(this.dienthoai);
					
				
					data.add(this.kbhId.length() <= 0 ?null:this.kbhId);
					data.add(this.dvkdId);
					data.add(this.getDateTime());
					data.add(this.getDateTime());
					data.add(this.userId);
					data.add(this.userId);
					data.add(this.ttppId);
				
			 
		
					
					if( this.db.updateQueryByPreparedStatement(sql, data)!=1 )
					{
						this.msg="Khong The Thuc Hien Luu BM Nay,Vui Long Lien He Voi Admin De Sua Loi Nay";
						this.db.getConnection().rollback();
						return false;
					}	
			 
	
			 
			/*	 sql  = "SELECT SCOPE_IDENTITY() AS BMID";
			 
				 ResultSet rs = this.db.get(sql);
				 rs.next();
				 this.Id = rs.getString("bmId");*/
				 this.Id = db.getPk_seq();
				 sql="INSERT INTO NHANVIEN (loai,bm_fk,TEN,NGAYSINH,DANGNHAP,MATKHAU,EMAIL,DIENTHOAI,TRANGTHAI,NGAYTAO,NGAYSUA,NGUOITAO,NGUOISUA,PHANLOAI,ISLOGIN,SESSIONID) "+
				 "\n	select 1,?,?,?,?,pwdencrypt(?),?,?,1,?,?,?,?,2,0,'2012-01-01'";
//				 " VALUES(1,"+this.Id+",N'"+this.getTen()+"','"+this.getDateTime()+"','BM"+this.Id+"',pwdencrypt('BM"+this.Id+"'),'"+this.getEmail()+"','"+this.getDienthoai()+"','1','"+this.getDateTime()+"','"+this.getDateTime()+"','"+this.userId+"','"+this.userId+"','2','0','2012-01-01')";
				 data.clear();
					data.add(this.Id);
					data.add(this.getTen());
					data.add(this.getDateTime());
					data.add("BM"+this.Id);
					data.add("BM"+this.Id);
					data.add(this.getEmail());
					data.add(this.getDienthoai());
					data.add(this.getDateTime());
					data.add(this.getDateTime());
					data.add(this.userId);
					data.add(this.userId);
					   db.viewQuery(sql, data);
					if( this.db.updateQueryByPreparedStatement(sql, data)!=1 )
					{
						this.msg="Khong The Thuc Hien Luu ASM Nay,Vui Long Lien He Voi Admin De Sua Loi Nay";
						this.db.getConnection().rollback();
						return false;
					}
				 
			
				 
			 }else{
			
				 
				 sql = " select d.ten, k.TEN kenhDDKD " +
				 	" from  asm d  " +
					" inner join KENHBANHANG k on k.PK_SEQ = d.KBH_FK " +
					" where d.trangthai = 1 and d.bm_fk ="+this.Id+"	and d.KBH_FK != "+this.kbhId+"";
				ResultSet rs = db.get(sql);
				if(rs.next()) 
				{
					this.msg =  "ASM   " + rs.getString("ten") + " kênh (" + rs.getString("kenhDDKD") + ")  đang trực thuộc, bạn không thể đổi kênh!  ";
					this.db.getConnection().rollback();
					return false;
					
				} 
				 
				 
				 sql = 	"UPDATE BM SET DVKD_FK = '"+this.dvkdId+"' , kbh_fk = '"+this.kbhId+"' ,TEN =?, TRANGTHAI =?, " +
					 	 	"NGAYSUA = ?, NGUOISUA = ?,smartid = ? , TTPP_FK= ?"
					 	 			+ " WHERE PK_SEQ = '" + this.Id + "'";
				 
				 data.clear();
				 	data.add(this.bmTen);
					data.add(this.trangthai);
					data.add(this.getDateTime());
					data.add(this.userId);
					data.add(this.Smartid);
					data.add(this.ttppId);
			
	
				 
				 
					if( this.db.updateQueryByPreparedStatement(sql, data)!=1 )
					{
						this.msg="Khong The Thuc Hien Luu BM Nay,Vui Long Lien He Voi Admin De Sua Loi Nay";
						this.db.getConnection().rollback();
						return false;
					}
				 

				 

					
					
					
				 
				 
			 }
			 
			 sql = "delete bm_chinhanh where BM_FK = '"+this.Id+"' ";
			 if(!this.db.update(sql))
			 {
				 geso.dms.center.util.Utility.rollback_throw_exception(this.db);
					this.msg="Khong The Thuc Hien Luu ASM Nay,Vui Long Lien He Voi Admin De Sua Loi Nay. Loi :";
					return false; 
			 }
			 if(this.vungId != null)
			 for(int i = 0; i < this.vungId.length ; i++){
				 String ngaybatdau=request.getParameter("ngaybatdau"+this.vungId[i]);
				 String ngayketthuc=request.getParameter("ngayketthuc"+this.vungId[i]);
				 System.out.println("RRR: " + ngayketthuc);
				 sql="select * from bm_chinhanh where bm_fk="+this.Id +" and vung_fk="+ this.vungId[i];
				 ResultSet rs_check=db.get(sql);
				 if(rs_check.next()){
				/*	 sql="update bm_chinhanh set ngaybatdau='"+ngaybatdau+"',ngayketthuc='"+ngayketthuc+"' where bm_fk="+this.Id +" and vung_fk="+ this.vungId[i];
					 if(!this.db.update(sql))
					 {
						 geso.dms.center.util.Utility.rollback_throw_exception(this.db);
							this.msg="Khong The Thuc Hien Luu BM Nay .Loi :"+sql;

							return false;  
					 }*/
					 
					 	sql="update bm_chinhanh set ngaybatdau=?,ngayketthuc=? where bm_fk=? and vung_fk=? ";
					 	data.clear();
						data.add(ngaybatdau);
						data.add(ngayketthuc);
						data.add(this.Id);
						data.add(this.vungId[i]);
				
		
					 
						dbutils.viewQuery(sql, data);
						if( this.db.updateQueryByPreparedStatement(sql, data)!=1 )
						{
							this.msg="Khong The Thuc Hien Luu BM Nay";
							geso.dms.center.util.Utility.rollback_throw_exception(this.db);
							return false;
						}
					 
				 }else{
				 
					 sql = "INSERT INTO BM_CHINHANH (BM_FK, VUNG_FK,NGAYBATDAU,NGAYKETTHUC) " +
					 "\n	select ?,?,?,?";

//					 		"VALUES('" + this.Id+"','" + this.vungId[i] + "','"+ngaybatdau+"','2100-01-01')";
					
					 
					 data.clear();
						data.add(this.Id);
						data.add(this.vungId[i]);
						data.add(ngaybatdau);
						data.add(ngayketthuc);
				
				
		
					 
					 
						if( this.db.updateQueryByPreparedStatement(sql, data)!=1 )
						{
							this.msg="Khong The Thuc Hien Luu BM Nay";
							this.db.getConnection().rollback();
							return false;
						}
				
				 }
			 }
			
			 this.db.getConnection().commit();
			 this.db.getConnection().setAutoCommit(true);
			 
		}catch(Exception e){
			System.out.println(e.toString());
			
			geso.dms.center.util.Utility.rollback_throw_exception(this.db);
			this.msg="Khong The Thuc Hien Luu BM Nay,Vui Long Lien He Voi Admin De Sua Loi Nay"+ e.toString();
			e.printStackTrace();
			return false;
			
		}
		return true;

	}
	
	public Hashtable<String, String> getHTVungId(){
		Hashtable<String, String> ht = new Hashtable<String, String>();
		ht.put("0", "0");
		if(this.vungId != null){
			for(int i = 0; i < this.vungId.length; i++){
				ht.put(this.vungId[i], this.vungId[i]);
			}
		}
		return ht;
	}

	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	public void DBClose(){
		try{
			if(kbh != null) kbh.close(); 
			if(dvkd != null) dvkd.close(); 
			if(vung != null) vung.close(); 
			if(this.db != null) this.db.getConnection().close();
		}catch(Exception e){}
	}

	@Override
	public ResultSet getTrungtamphanphoiList() {
		return this.ttpp;
	}

	@Override
	public String getTtppId() {
		return this.ttppId;
	}

	@Override
	public void setTtppId(String ttppId) {
		this.ttppId = ttppId;
	}
}
