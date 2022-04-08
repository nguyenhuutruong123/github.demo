package geso.htp.center.beans.chitieunhanvien.imp;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import geso.htp.center.beans.chitieunhanvien.INhanvien;
import geso.htp.center.beans.chitieunhanvien.ITieuchi;
import geso.htp.center.beans.chitieunhanvien.ITyLeTuan;
import geso.htp.distributor.db.sql.dbutils;

public class TyLeTuan implements ITyLeTuan {

	public String  isDisplay = "0";

	public String loai = "1";

	double Chitiet=0;
	String UserID;
	String nppTen;
	String nppId;
	String tructhuocId;
	String Id = "";
	int Thang;
	int Nam;
	String NgayTao;
	String NguoiTao;
	String NguoiSua;
	String NgaySua;
	String NgayKetThuc;
	String KhuVuc="";
	String DienGiai;
	String TenKhuVuc;
	String Message;
	String Dvkd_id;
	String TenDVkd;
	String SoNgayLamViec;
	double ChiTieu;
	String KenhId;
	String TrangThai;
	ResultSet rsChiTieuNV;
	String ChuoiNhomSP;
	String[] NhomSP;
	List<TyLeTuan> listchitieu;
	List<ITieuchi> listTieuchi;
	dbutils db;
	
	ResultSet chitieuRs ;
	
	public TyLeTuan(){
		this.NgayKetThuc="";
		this.SoNgayLamViec="";
		this.Message="";
		this.DienGiai="";
		this.KhuVuc="";
	//	this.loai = "1";
		this.isDisplay = "0";
		this.Id = "";
		listchitieu = new ArrayList<TyLeTuan>();
		db=new dbutils();
	}
	public TyLeTuan(String id){
		db=new dbutils();
		String  sql_getdata="";
		System.out.println("Loai trong bean "+loai);
		sql_getdata="SELECT   c.trangthai,C.PK_SEQ, C.THANG, C.NAM, C.DIENGIAI,  C.NGAYTAO, C.NGAYSUA,NT.TEN AS NGUOITAO, c.loai, "+ 
		"NS.TEN AS NGUOISUA FROM chitieunhanvien AS C " +
		"INNER JOIN "+
		"dbo.NHANVIEN AS NT ON C.NGUOITAO = NT.PK_SEQ INNER JOIN dbo.NHANVIEN AS NS ON C.NGUOISUA = NS.PK_SEQ where c.trangthai!='2' and c.pk_seq="+ id;

		System.out.println("sql_getdata "+sql_getdata);

		try{
			ResultSet rsChiTieu=	db.get(sql_getdata);
			if(rsChiTieu!=null)
			{
				while(rsChiTieu.next()){
					this.setID(rsChiTieu.getString("PK_SEQ"));
					this.setThang(rsChiTieu.getInt("THANG"));
					this.setNam(rsChiTieu.getInt("NAM"));
					this.setNgayTao(rsChiTieu.getString("NGAYTAO"));
					this.setNgaySua(rsChiTieu.getString("NGAYSUA"));
					this.setDienGiai(rsChiTieu.getString("DIENGIAI")==null?"":rsChiTieu.getString("DIENGIAI"));
					this.setNguoiTao(rsChiTieu.getString("NGUOITAO"));
					this.setNguoiSua(rsChiTieu.getString("NGUOISUA"));
					this.setTrangThai(rsChiTieu.getString("trangthai"));
					this.setLoai(rsChiTieu.getString("loai"));
					if(rsChiTieu.getString("trangthai").trim().equals("1"))
						this.isDisplay="1";
				}
			}
			rsChiTieu.close();	
			
		}catch(Exception er){
			this.Message="Error ChiTieu.java - line : 88- detail error :"+ er.toString();
			System.out.println("Error Class name : CHITIEU.JAVA- LINE 216 :STRING SQL" + er.toString());
		}
		this.Message="";
	}


	public void initBCNhanVien()
	{
		try
		{
			String query =" ";
			ResultSet rs;
			
			if(this.listTieuchi == null || this.listTieuchi.size() <= 0)
			{
				
				System.out.println("loai bean "+loai);
				listTieuchi = new ArrayList<ITieuchi>();
				query = "select a.pk_seq,a.diengiai from TIEUCHITHUONG_CHITIET a   inner join TIEUCHITINHTHUONG b on a.TIEUCHITINHTHUONG_FK = b.PK_SEQ   "
						+"where b.TRANGTHAI = 1 and b.THANG = "+this.Thang+" and b.NAM = "+this.Nam+" and b.LOAI = "+this.loai+" and b.tructhuoc_fk = '"+this.tructhuocId+"' ";
				System.out.println("tctct " + query);
				rs = db.get(query);
				if(rs != null)
				{
					while(rs.next())
					{
						ITieuchi tc = new Tieuchi();
						tc.setID(rs.getString("pk_seq"));
						tc.setDiengiai(rs.getString("diengiai"));
						if(this.loai.equals("1"))
						{
							query = "select * from daidienkinhdoanh where trangthai = 1 and tructhuoc_fk = "+this.tructhuocId;
						}
						else if(this.loai.equals("2"))
						{
							query = "select * from bm where trangthai = 1 and tructhuoc_fk = "+this.tructhuocId;
						}
						else
						{
							query = "select * from giamsatbanhang where trangthai = 1 and tructhuoc_fk = "+this.tructhuocId;
						}
						ResultSet rsnv = db.get(query);
						List<INhanvien> lstnv = new ArrayList<INhanvien>();
						if(rsnv != null)
						{
							while(rsnv.next())
							{
								INhanvien nv = new Nhanvien();
								nv.setID(rsnv.getString("pk_Seq"));
								nv.setTen(rsnv.getString("ten"));
								if(this.Id.length() > 0)
								{
									if(this.loai.equals("1"))
									{
										query = "select * from ChiTieuNhanVien_TyLeTuanDDKD where ctnv_fk = "+this.Id+" and nhanvien_fk = "+rsnv.getString("pk_Seq")+" and tctct_fk ="+rs.getString("pk_seq");
									}
									else if(this.loai.equals("2"))
									{
										query = "select * from ChiTieuNhanVien_TyLeTuanRSM where ctnv_fk = "+this.Id+" and nhanvien_fk = "+rsnv.getString("pk_Seq")+" and tctct_fk ="+rs.getString("pk_seq");
									}
									else
									{
										query = "select * from ChiTieuNhanVien_TyLeTuanSS where ctnv_fk = "+this.Id+" and nhanvien_fk = "+rsnv.getString("pk_Seq")+" and tctct_fk ="+rs.getString("pk_seq");
									}
									System.out.println("ctnv_tlt " + query);
									ResultSet rstl = db.get(query);
									if(rstl != null)
									{
										while(rstl.next())
										{
											nv.setTuan1(rstl.getString("tuan1"));
											nv.setTuan2(rstl.getString("tuan2"));
											nv.setTuan3(rstl.getString("tuan3"));
											nv.setTuan4(rstl.getString("tuan4"));
										}
									}
								}
								lstnv.add(nv);
							}
						}
						tc.setNhanvien(lstnv);
						listTieuchi.add(tc);
					}
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			
		}
	}

	public void setID(String id) {

		this.Id=id;	
	}

	public String getID() {

		return Id;
	}

	public void setThang(int thang) {

		this.Thang=thang;
	}

	public int getThang() {

		return this.Thang;
	}

	public void setNam(int nam) {

		this.Nam=nam;
	}

	public int getNam() {

		return this.Nam;
	}

	public void setNgayTao(String ngaytao) {

		this.NgayTao =ngaytao;
	}

	public String getNgayTao() {

		return this.NgayTao;
	}

	public void setNgaySua(String ngaysua) {

		this.NgaySua=ngaysua;
	}

	public String getNgaySua() {

		return this.NgaySua;
	}

	public void setDienGiai(String diengiai) {

		this.DienGiai=diengiai;
	}

	public String getDienGiai() {

		return this.DienGiai;
	}
	public boolean KiemTraHopLe(){

		if(this.NguoiTao.toString().equals("null") || this.NguoiTao.equals("")){
			this.Message="Ten Nguoi Dung Khong Hop Le,Vui Long Dang Xuat De Thu Lai!";
			return false;
		}

		if(this.Thang==0){
			this.Message="Vui Long Chon Thang";
			return false;
		}
		if(this.Nam==0){
			this.Message="Vui Long Chon Nam";
			return false;
		}

		return true;
	}

	public String getUserId() {

		return this.UserID;
	}

	public void setUserId(String userId)
	{
		this.UserID = userId;
		geso.htp.distributor.util.Utility util=new geso.htp.distributor.util.Utility();
		this.nppId=util.getIdNhapp(this.UserID);
		this.nppTen=util.getTenNhaPP();
		this.tructhuocId=util.getTructhuocId();
		System.out.println("userid " + userId + ", nppid "+ nppId);
	}

	public String getNppId()
	{
		return this.nppId;
	}
	
	public void setNppId(String nppId)
	{
		this.nppId = nppId;
	}

	public String getNppTen()
	{
		return this.nppTen;
	}
	
	public void setNppTen(String nppTen)
	{
		this.nppTen = nppTen;
	}
	
	public String getTructhuocId()
	{
		return this.tructhuocId;
	}
	
	public void setTructhuocId(String TructhuocId)
	{
		this.tructhuocId = TructhuocId;
	}
	
	public List<TyLeTuan> getChiTieu() {

		return listchitieu;
	}

	public void setListChiTieu(String sql) {
		String  sql_getdata="";

		sql_getdata="SELECT   C.PK_SEQ,c.trangthai, C.THANG, C.NAM, C.DIENGIAI, C.NGAYTAO, C.NGAYSUA,NT.TEN AS NGUOITAO, "+ 
		"NS.TEN AS NGUOISUA FROM chitieunhanvien AS C INNER JOIN "+
		"dbo.NHANVIEN AS NT ON C.NGUOITAO = NT.PK_SEQ INNER JOIN dbo.NHANVIEN AS NS ON C.NGUOISUA = NS.PK_SEQ" +
		" where 1=1 and c.trangthai != 2 and c.tructhuoc_fk = '"+this.tructhuocId+"'";

		if(sql!=""){
			sql_getdata=sql;
		} 

		try{
			System.out.println("Cau lenh Sql Chi Tieu 213:"+sql_getdata);
			ResultSet rsChiTieu=	db.get(sql_getdata);
			if(rsChiTieu!=null){
				while(rsChiTieu.next()){
					TyLeTuan CT=new TyLeTuan();
					CT.setID(rsChiTieu.getString("PK_SEQ"));
					CT.setThang(rsChiTieu.getInt("THANG"));
					CT.setNam(rsChiTieu.getInt("NAM"));


					CT.setNgayTao(rsChiTieu.getString("NGAYTAO"));
					CT.setNgaySua(rsChiTieu.getString("NGAYSUA"));
					CT.setDienGiai(rsChiTieu.getString("DIENGIAI"));

					CT.setNguoiTao(rsChiTieu.getString("NGUOITAO"));
					CT.setNguoiSua(rsChiTieu.getString("NGUOISUA"));

					CT.setTrangThai(rsChiTieu.getString("trangthai"));

					listchitieu.add(CT);
				}
			}
		}catch(Exception er){
			System.out.println("Error Class name : CHITIEU.JAVA- LINE 216 :STRING SQL" + er.toString());
		}
	}

	public void setNguoiTao(String nguoitao) {

		this.NguoiTao=nguoitao;		
	}

	public String getNguoiTao() {

		return this.NguoiTao;
	}

	public void setNguoiSua(String nguoisua) {

		this.NguoiSua=nguoisua;
	}

	public String getNguoiSua() {

		return this.NguoiSua;
	}
	
	public void setMessage(String strmessage) {

		this.Message=strmessage;
	}

	public String getMessage() {

		return this.Message;
	}

	public boolean Create() 
	{
		try
		{
			if(!KiemTraHopLe()){
				return false;
			}

			db.getConnection().setAutoCommit(false);

			//kiem tra xenm chi tieu trong thoi gian nay da co chua
			String sql="select pk_seq from tyletuan where thang= " +this.Thang+" and nam="+ this.Nam +" and tructhuoc_fk = '"+this.tructhuocId+"' and trangthai!=2";
			System.out.println("sql : "+sql);
			ResultSet rs_check=db.get(sql);
			if(rs_check!=null){
				if(rs_check.next()){//co du lieu
					this.setMessage("Chi Tieu Trong Thang Da Thiet Lap");
					rs_check.close();
					return false;
				}
			}	
			
			// thuc hien insert
			sql="insert into tyletuan(diengiai,THANG,NAM,NGAYTAO,NGUOITAO,NGAYSUA,NGUOISUA,TRANGTHAI, npp_fk, tructhuoc_fk,loai) " +
			"values (N'"+this.DienGiai+"',"+this.Thang+","+this.Nam+",'"+this.NgayTao+"','"+this.NguoiTao+"','"+this.NgaySua+"','"+this.NguoiSua+"','0', '"+this.nppId+"', '"+this.tructhuocId+"', "+this.loai+")";
			System.out.println("save chi tieu luong thuong " +sql);
			if(!db.update(sql)){

				this.Message="Loi :"+sql;		
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				return false;
			}		
			//thuc hien insert chi tiet
			String query = "select SCOPE_IDENTITY() as dhId";
			ResultSet rsDh = db.get(query);	
			rsDh.next();
			this.setID(rsDh.getString("dhId"));

			String table ="ChiTieuNhanVien_TyLeTuanDDKD";
			if(this.loai.equals("2"))
				table ="ChiTieuNhanVien_TyLeTuanBM";
			else if(this.loai.equals("3"))
				table ="ChiTieuNhanVien_TyLeTuanSS";
			
			if(this.listTieuchi != null)
			{
				for (int i = 0; i < this.listTieuchi.size(); i++) {
					ITieuchi tc = this.listTieuchi.get(i);
					List<INhanvien> lstnv = tc.getNhanvien(); 
					for (int j = 0; j < lstnv.size(); j++) {
						INhanvien nv = lstnv.get(j);
						
						query = "insert into "+table+"(ctnv_fk, nhanvien_fk, tuan1, tuan2, tuan3, tuan4, tctct_fk) " +
								"values('"+ this.Id + "', '" + nv.getID() + "', '" + nv.getTuan1() + "', '" + nv.getTuan2() + "', '" + nv.getTuan3() + "', '" + nv.getTuan4() + "', '" + tc.getID() + "' )";
						
						System.out.println("1.Insert: " + query);
						if(!db.update(query))
						{
							this.Message = "Khong the tao moi chi tieu: " + query;
							db.getConnection().rollback();
							return false;
						}
						
					}
				}
			}

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}catch(Exception er){	
			er.printStackTrace();
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			this.setMessage("Ban khong the them chi tieu moi nay, loi : " + er.toString());
			return false;			
		}
		return true;
	}

	public boolean Update() 
	{
		try
		{
			if(!KiemTraHopLe()){
				return false;
			}
			db.getConnection().setAutoCommit(false);

			String table ="ChiTieuNhanVien_TyLeTuanDDKD";
			if(this.loai.equals("2"))
				table ="ChiTieuNhanVien_TyLeTuanBM";
			else if(this.loai.equals("3"))
				table ="ChiTieuNhanVien_TyLeTuanSS";
			
			String query = "";
			
			// xoa chi tieu cu
			String sql="delete from "+table+" where CTNV_FK = '"+this.Id+"'" ;
			System.out.println("delete chi tieu cu "+sql);
			if(!db.update(sql)){
				db.getConnection().rollback();
				this.Message="Loi :"+sql;		
				return false;
			}	
			
			if(this.listTieuchi != null)
			{
				for (int i = 0; i < this.listTieuchi.size(); i++) {
					ITieuchi tc = this.listTieuchi.get(i);
					List<INhanvien> lstnv = tc.getNhanvien(); 
					for (int j = 0; j < lstnv.size(); j++) {
						INhanvien nv = lstnv.get(j);
						query = "insert into "+table+"(ctnv_fk, nhanvien_fk, tuan1, tuan2, tuan3, tuan4, tctct_fk) " +
								"values('"+ this.Id + "', '" + nv.getID() + "', '" + nv.getTuan1() + "', '" + nv.getTuan2() + "', '" + nv.getTuan3() + "', '" + nv.getTuan4() + "', '" + tc.getID() + "' )";
						
						System.out.println("1.Insert: " + query);
						if(!db.update(query))
						{
							this.Message = "Khong the tao moi chi tieu: " + query;
							db.getConnection().rollback();
							return false;
						}
					}
				}
			}

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}catch(Exception er){	
			er.printStackTrace();
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			this.setMessage("Ban khong the them chi tieu moi nay, loi : " + er.toString());
			return false;			
		}
		return true;

	}
	
	public boolean Delete() {

		try{
			db.getConnection().setAutoCommit(false);


			String sql="update tyletuan  set trangthai = 2  where pk_seq= "+this.Id;
			if(!db.update(sql)){
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				this.setMessage("Vui Long Kiem Tra Lai.Loi :"+sql);
				return false;	
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}catch(Exception er){
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			this.setMessage("Ban khong the xoa chi tieu moi nay, loi : " + er.toString());
			return false;
		}
		return false;
	}

	public boolean InsertSR(dbutils db, String id,String values,String valuesNhom)
	{
		String sql = "";

		String table ="ChiTieuNhanVien_DDKD";
		if(this.loai.equals("2"))
			table ="ChiTieuNhanVien_BM";
		else if(this.loai.equals("3"))
			table ="ChiTieuNhanVien_SS";
		/*else if(this.loai.equals("4"))
			table ="ChiTieuNhanVien_ASM";*/
		
		// xoa chi tieu cu
		sql="delete from "+table+" where CTNV_FK = '"+id+"'" ;
		System.out.println("delete chi tieu cu "+sql);
		if(!db.update(sql)){

			this.Message="Loi :"+sql;		
			return false;
		}	
		sql="  insert "+table+"(CTNV_FK,TCTCT_FK,NSP_FK,TIEUCHI,NHANVIEN_FK,CHITIEU) " +
		"select "+id+" ,data.TCTCT_FK,t.nhomsp_fk,t.tieuchi,data.manv,data.chitieu from  ("+values+")data inner join tieuchithuong_chitiet t on data.TCTCT_FK = t.pk_Seq  ";
		System.out.println("ChiTieuNhanVien_SR:" + sql );
		if(db.updateReturnInt(sql) <=0)
		{
			System.out.println("save ChiTieuNhanVien_SR: " +sql);
			this.Message="Loi :"+sql;			
			return false;
		}	

		return true;
	}

	public void setTrangThai(String trangthai) {

		this.TrangThai=trangthai;
	}

	public String getTrangThai() {

		return this.TrangThai;
	}

	public boolean Chot() {

		String sqlchot="update tyletuan set trangthai='1' where pk_seq="+ this.Id;
		System.out.println("slq chot  : "+sqlchot);

		try{
			db.getConnection().setAutoCommit(false);
			if(!db.update(sqlchot)){//khong xoa duoc
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				return false;
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}catch(Exception er){
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return false;
		}
		return true;
	}

	public void closeDB() {

		try{
			this.rsChiTieuNV.close();
			if(this.db!=null){
				db.shutDown();
			}
		}catch(Exception er){

		}
	}

	public boolean UnChot() {

		String sqlchot="update tyletuan set trangthai='0' where pk_seq="+ this.Id;
		try{
			db.getConnection().setAutoCommit(false);
			if(!db.update(sqlchot)){//khong xoa duoc
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				return false;
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}catch(Exception er){
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return false;
		}
		return true;
	}

	public String getLoai() {
		return loai;
	}
	
	public void setLoai(String loai) {
		this.loai = loai;
	}

	public List<ITieuchi> getListTieuChi() {
		return listTieuchi;
	}
	
	public void setListTieuChi(List<ITieuchi> listTieuchi) {
		this.listTieuchi = listTieuchi;
	}
	
	public String getIsDisplay() {
		return isDisplay;
	}
	
	public void setIsDisplay(String isDisplay) {
		this.isDisplay = isDisplay;
	}
	
	public ResultSet getChitieuRs() {
		return chitieuRs;
	}
}
