package geso.dms.center.beans.asm.imp;

import geso.dms.center.beans.asm.IAsm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import geso.dms.center.db.sql.*;
import geso.dms.distributor.db.sql.dbutils;

public class Asm implements IAsm {
	public String Id;
	public String asmMa;
	public String asmTen;
	public String dienthoai;
	public String email;
	public String diachi;
		
	public String kbhId;
	public ResultSet kbh;
		
	public String dvkdId;
	String thuviec;
	public ResultSet dvkd;
		
	public String[] kvId;
	public ResultSet kv;
	
	public ResultSet ttpp;
	public String ttppId;
		
	public String trangthai;
	public String msg;
	
	String cmnd;
	String ngaysinh;
	String quequan;
	String ngaybatdau;
	String ngayketthuc;
	
	public String userId;
	
	public dbutils db ;
		
	public Asm(){
		this.Id = "";
		this.asmMa = "";
		this.asmTen = "";
		this.dienthoai = "";
		this.email = "";
		this.diachi = "";
		this.kbhId = "";
		this.dvkdId = "";
		this.cmnd="";
		this.quequan="";
		this.ngaybatdau="";
		this.ngayketthuc="";
		this.ngaysinh="";
		this.trangthai = "";
		this.msg = "";
		this.userId = "";
		this.thuviec="";
		this.db = new dbutils();
	}
		
	public String getId(){
		return this.Id;
	}
		
	public void setId(String Id){
		this.Id = Id;
	}

	public String getTen(){
		return this.asmTen;
	}
		
	public void setTen(String asmTen){
		this.asmTen = asmTen;
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

	public String[] getKvId(){
		return this.kvId;
	}
		
	public void setKvId(String[] kvId){
		this.kvId = kvId;
	}

	public ResultSet getKv(){
		return this.kv;
	}
		
	public void setKv(ResultSet kv){
		this.kv = kv;
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
		String sql 	= "SELECT PK_SEQ AS KBHID, TEN AS KBH FROM KENHBANHANG";// WHERE PK_SEQ = '100000' OR PK_SEQ = '100002'";
		this.kbh  	= this.db.get(sql);
			
		sql 		= "SELECT PK_SEQ AS DVKDID, DONVIKINHDOANH AS DVKD FROM DONVIKINHDOANH";// WHERE PK_SEQ = '100000' OR PK_SEQ = '100001'";
		this.dvkd 	= this.db.get(sql);			
		
		sql 		= "select a.PK_SEQ,a.MA,a.TEN from TRUNGTAMPHANPHOI a where TRANGTHAI=1";
		this.ttpp 	= this.db.get(sql);	
		
		if(this.Id.length()>0){
		sql			= "SELECT PK_SEQ AS KVID, TEN AS KV,b.ngaybatdau,b.ngayketthuc  FROM KHUVUC left join ASM_KHUVUC b  "+
			"  on KHUVUC.pk_seq=b.KHUVUC_FK WHERE B.ASM_FK="+this.Id +
			"  UNION  SELECT PK_SEQ AS KVID, TEN AS KV,'' as ngaybatdau,'' as ngayketthuc "+  
			"  FROM KHUVUC  WHERE KHUVUC.PK_SEQ NOT IN " +
			"(SELECT KHUVUC_FK FROM ASM_KHUVUC WHERE ASM_FK ="+this.Id+")  ORDER BY TEN";  	
		}else{
			sql= "SELECT PK_SEQ AS KVID, TEN AS KV,'' as ngaybatdau,'' as ngayketthuc "+  
			"  FROM KHUVUC order by ten ";
		}
		System.out.println("init_New: ' "+ sql + " ' ");
		this.kv = this.db.get(sql);
			
	}
		
		public void init_Update()
		{
			try{
				String sql 	=	"SELECT ASM.BM_FK,isnull(ASM.SMARTID,'') as ASMMA, isnull(asm.tinhtrang,'') as tinhtrang,isnull(asm.cmnd,'') as cmnd,isnull(asm.quequan,'') as quequan,isnull(asm.ngaysinh,'') as ngaysinh , ASM.PK_SEQ AS ASMID, ASM.TEN AS ASMTEN, ASM.DIACHI, ASM.DIENTHOAI, ASM.EMAIL, ASM.TRANGTHAI, " +
								"KBH.PK_SEQ AS KBHID, ASM.NGAYTAO, ASM.NGUOITAO, ASM.NGAYSUA, ASM.NGUOISUA, KHUVUC.PK_SEQ AS KVID," +
								"KHUVUC.TEN AS KV, DVKD.PK_SEQ AS DVKDID, ASM.TTPP_FK " +
								"FROM ASM ASM " +
								"left JOIN KENHBANHANG KBH ON KBH.PK_SEQ = ASM.KBH_FK " +
								"left JOIN ASM_KHUVUC ASM_KV ON ASM_KV.ASM_FK = ASM.PK_SEQ " +
								"left JOIN KHUVUC KHUVUC ON KHUVUC.PK_SEQ = ASM_KV.KHUVUC_FK " +
								"left JOIN DONVIKINHDOANH DVKD ON DVKD.PK_SEQ = ASM.DVKD_FK " +
								"WHERE ASM.PK_SEQ = '" + this.Id + "'";
				
				System.out.println("init_Update: ' " + sql + " ' ");
				
				ResultSet rs = this.db.get(sql);
				rs.next();
				this.bmId = rs.getString("BM_FK") == null ? "" :   rs.getString("BM_FK");
				this.asmTen = rs.getString("asmten");
				this.asmMa = rs.getString("asmma");
				this.diachi = rs.getString("diachi");
				this.dienthoai = rs.getString("dienthoai");
				this.email = rs.getString("email");
				this.trangthai = rs.getString("trangthai");
				this.kbhId = rs.getString("kbhid");
				this.dvkdId = rs.getString("dvkdid");
				this.cmnd=rs.getString("cmnd");
	        	this.ngaysinh=rs.getString("ngaysinh");
	        	this.quequan=rs.getString("quequan");
	        	this.thuviec=rs.getString("tinhtrang");
	        	this.ttppId=rs.getString("TTPP_FK");
				
				rs.close();
				
				sql = "SELECT COUNT(*) AS NUM FROM ASM_KHUVUC WHERE ASM_FK = '" + this.Id + "'";
				rs = this.db.get(sql);
				rs.next();
				int num = Integer.parseInt(rs.getString("NUM"));
				System.out.println("" + num);
				
				sql = "SELECT ASM_FK AS ASMID, KHUVUC_FK AS KVID FROM ASM_KHUVUC WHERE ASM_FK = '" + this.Id + "'";
				System.out.println(sql);
				
				rs = this.db.get(sql);
				
				this.kvId = new String[num];
				
				int i = 0;
				while(rs.next()){
					System.out.println(this.kvId[i]);
					this.kvId[i] = rs.getString("KVID");
					i++;
				}
				if(rs!=null)
					rs.close();
				this.init_New();
				
			}catch(Exception e){}
		}
		
		public boolean Save(HttpServletRequest request){
			String sql = "";
			try{
				 this.db.getConnection().setAutoCommit(false);
				 if(this.ttppId.equals(""))
				 {
					 this.ttppId = "NULL";
				 }
				 
				 String msg1 = CheckMa(this.asmMa);
				 if(msg1.trim().length() > 0)
				 {
					 geso.dms.center.util.Utility.rollback_throw_exception(this.db);
					 this.msg = msg1;
					 return false;
				 }
				 
				 
				 if(this.kbhId.length() <=0)
				 {
					 geso.dms.center.util.Utility.rollback_throw_exception(this.db);
					 this.msg = "Vui lòng chọn kênh bán hàng!";
					 return false;
				 }
				 if(this.bmId.length() <=0)
				 {
					 geso.dms.center.util.Utility.rollback_throw_exception(this.db);
					 this.msg = "Vui lòng chọn RSM trục thuộc!";
					 return false;
				 }
				 
					List<Object> data = new ArrayList<Object>();	

				 if(this.Id.length() == 0){
					 sql  = "INSERT INTO ASM (BM_FK,SMARTID, TEN,tinhtrang,cmnd,ngaysinh,quequan, DIACHI, EMAIL, DIENTHOAI, TRANGTHAI, KBH_FK, DVKD_FK, NGAYTAO, NGAYSUA, NGUOITAO, NGUOISUA, TTPP_FK) " +
					 "VALUES("+this.bmId+",N'"+ this.asmMa +"', N'"+ this.asmTen +"','"+this.thuviec+"','"+this.cmnd+"','"+this.ngaysinh+"',N'"+this.quequan+"',N'" + this.diachi + "','" + this.email + "','" + this.dienthoai + "','"+this.trangthai+"'," +(this.kbhId.length() <= 0 ?null:this.kbhId)+ ",'" + this.dvkdId + "','" + this.getDateTime() + "','" + this.getDateTime()+"','" + this.userId + "','" + this.userId + "', " + this.ttppId + " ) ";
					 	if(!this.db.update(sql))
						{
							this.msg="Không thể lưu ASM này";
							this.db.getConnection().rollback();
							return false;
						}
					
				 
					 sql= "SELECT MAX(PK_SEQ) FROM dbo.ASM";
					 ResultSet rs = this.db.get(sql);
					 rs.next();
					 
					 this.Id = rs.getInt(1)+"";
					 sql="INSERT INTO NHANVIEN (loai,asm_fk,TEN,NGAYSINH,DANGNHAP,MATKHAU,EMAIL,DIENTHOAI,TRANGTHAI,NGAYTAO,NGAYSUA,NGUOITAO,NGUOISUA,PHANLOAI,ISLOGIN,SESSIONID) "+
					 "\n	select 2,?,?,?,?,pwdencrypt(?),?,?,1,?,?,?,?,2,0,2012-01-01";

					 data.clear();
						data.add(this.Id);
						data.add(this.getTen());
						data.add(this.getDateTime());
						data.add("ASM"+this.Id);
						data.add("ASM"+this.Id);
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
				
				 }else
				 {
					 	
					 sql = " select d.ten, k.TEN kenhDDKD " +
					 	" from  giamsatbanhang d  " +
						" inner join KENHBANHANG k on k.PK_SEQ = d.KBH_FK " +
						" where d.trangthai = 1 and d.asm_fk ="+this.Id+"	and d.KBH_FK != "+this.kbhId+"";
					ResultSet rs = db.get(sql);
					if(rs.next()) 
					{
						this.msg =  "GSBH   " + rs.getString("ten") + " kênh (" + rs.getString("kenhDDKD") + ")  đang trực thuộc, bạn không thể đổi kênh!  ";
						this.db.getConnection().rollback();
						return false;
						
					} 
					 
					sql = 	"UPDATE ASM SET  BM_FK = "+this.bmId+",kbh_fk = '"+this.kbhId+"',ten = ?, tinhtrang=?, CMND=?,quequan=?,ngaysinh=?, NGAYSUA = ?, NGUOISUA = ? ,TRANGTHAI = ?, DIENTHOAI = ?,DIACHI=?,email=? WHERE PK_SEQ = '" + this.Id + "'";
					data.clear();
					data.add(this.asmTen);
					data.add(this.thuviec);
					data.add(this.cmnd);
					data.add(this.quequan);
					data.add(this.ngaysinh);
					data.add(this.getDateTime());
					data.add(this.userId);
					data.add( this.trangthai);
					data.add( this.dienthoai);
					data.add( this.diachi);
					data.add( this.email);
					if( this.db.updateQueryByPreparedStatement(sql, data)!=1 )
					{
						this.msg="Khong The Thuc Hien Luu ASM Nay,Vui Long Lien He Voi Admin De Sua Loi Nay";
						this.db.getConnection().rollback();
						return false;
					}
					 
					
						
				 }
				 sql = "delete asm_khuvuc where asm_fk = '"+this.Id+"' ";
				 if(!this.db.update(sql))
				 {
					 geso.dms.center.util.Utility.rollback_throw_exception(this.db);
						this.msg="Khong The Thuc Hien Luu ASM Nay,Vui Long Lien He Voi Admin De Sua Loi Nay. Loi :";
						return false; 
				 }
				 
				 if(kvId != null)
				 for(int i = 0; i < this.kvId.length ; i++){
					 
					 String ngaybatdau=request.getParameter("ngaybatdau"+this.kvId[i]);
					 String ngayketthuc=request.getParameter("ngayketthuc"+this.kvId[i]);
					 
					 sql="select * from asm_khuvuc where asm_fk='"+this.Id+"' and khuvuc_fk='"+ this.kvId[i]+"'";
					 System.out.println(sql);
					 ResultSet rs=this.db.get(sql);
					 
					 if(rs.next()){
						 sql="update asm_khuvuc set ngaybatdau=?,ngayketthuc=? where asm_fk='"+this.Id+"' and khuvuc_fk='"+ this.kvId[i]+"'";
					 
						 data.clear();
							data.add(ngaybatdau);
							data.add(ngayketthuc);

							
							
							
					 }else{
						 sql = "INSERT INTO ASM_KHUVUC (ASM_FK, KHUVUC_FK,ngaybatdau,ngayketthuc) " +
						 "\n	select ?,?,?,?";
//						 		"VALUES('" + this.Id+"','" + this.kvId[i] + "','"+ ngaybatdau+"','"+ ngayketthuc +"')";

						 data.clear();
							data.add(this.Id);
							data.add(this.kvId[i]);
							
							data.add(ngaybatdau);
							data.add(ngayketthuc);
						 
					 }
					
					 
					 if( this.db.updateQueryByPreparedStatement(sql, data)!=1 )
						{
							this.msg="Khong The Thuc Hien Luu ASM Nay,Vui Long Lien He Voi Admin De Sua Loi Nay";
							this.db.getConnection().rollback();
							return false;
						}
					
				 }
			
				 this.db.getConnection().commit();
				 this.db.getConnection().setAutoCommit(true);
				 
			}catch(Exception e){
				e.printStackTrace();
				geso.dms.center.util.Utility.rollback_throw_exception(this.db);
				this.msg="Khong The Thuc Hien Luu ASM Nay,Vui Long Lien He Voi Admin De Sua Loi Nay.Loi :" + e.toString();

				return false;
				
			}
			return true;

		}
		
		private String CheckMa(String asmMa) 
		{
			String msg = "";
			
			try
			{
				String query = "SELECT COUNT(*) sodong FROM ASM WHERE SMARTID = N'" + this.asmMa + "' AND TRANGTHAI = '1' ";
				if(this.Id.trim().length() > 0)
				{
					query += " AND PK_SEQ != "+ this.Id +" ";
				}
				System.out.println("querry check = " + query);
				ResultSet rs = db.get(query);
				int sodong = 0;
				if(rs!= null)
				{
					while(rs.next())
					{
						sodong = rs.getInt("sodong");
					}rs.close();
				}
				
				if(sodong > 0)
				{
					msg = " Mã " + this.asmMa + " đã tồn tại trong hệ thống. Vui lòng nhập mã khác. ";
				}
				
			}catch(Exception e)
			{
				e.printStackTrace();
				msg= "Xảy ra lỗi trong quá trình kiểm tra Mã ASM";
			}
			
			return msg;
		}

		public Hashtable<String, String> getHTKvId(){
			Hashtable<String, String> ht = new Hashtable<String, String>();
			ht.put("0", "0");
			if(this.kvId != null){
				for(int i = 0; i < this.kvId.length; i++){
					ht.put(this.kvId[i], this.kvId[i]);
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
				if(kv != null) kv.close(); 
				if(this.db != null) this.db.shutDown();
			}catch(Exception e){}
		}
		public String getCmnd() {
			
			return this.cmnd;
		}

		
		public void setCmnd(String cmnd) {
			
			this.cmnd=cmnd;
		}

		
		public String getNgaysinh() {
			
			return this.ngaysinh;
		}

		
		public void setNgaysinh(String ngaysinh) {
			
			this.ngaysinh=ngaysinh;
		}

		
		public String getQuequan() {
			
			return this.quequan;
		}

		
		public void setQuequan(String quequan) {
			this.quequan=quequan;
			
		}

		
		public String getNgaybatdau() {
			
			return this.ngaybatdau;
		}

		
		public void setNgaybatdau(String ngaybatdau) {
			
			this.ngaybatdau=ngaybatdau;
		}

		
		public String getNgayketthuc() {
			
			return this.ngayketthuc;
		}

		
		public void setNgayketthuc(String ngayketthuc) {
			
			this.ngayketthuc=ngayketthuc;
		}


		public String getThuviec() {
			return this.thuviec;
		}


		public void setThuviec(String thiec) {

			this.thuviec=thiec;
		}

		public ResultSet getTrungtamphanphoiList() {
			return this.ttpp;
		}

		
		public String getTtppId() {
			return this.ttppId;
		}

	
		public void setTtppId(String ttppId) 
		{
			this.ttppId = ttppId;
		}
	
		public String getMa() 
		{
			return this.asmMa ;
		}

		public void setMa(String asmMa) 
		{
			this.asmMa = asmMa;
		}
		
		String bmId = "";
		public String getBmId() {
			return bmId;
		}
		public void setBmId(String bmId) {
			this.bmId = bmId;
		}
		public ResultSet getBmRs()
		{
			if(this.kbhId != null && this.kbhId.length() > 0)
			{
				String query = " select pk_seq, ten from BM where trangthai = 1 and kbh_fk = " + this.kbhId;
				return db.get(query);
			}
			return null;
		}
		
	}
