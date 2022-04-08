

package geso.dms.center.beans.chitieunhanvien.imp;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


import geso.dms.center.beans.chitieunhanvien.ICTNhanvien_NSP;
import geso.dms.center.beans.chitieunhanvien.IChiTieuNhanvien;
import geso.dms.center.util.Utility;
import geso.dms.distributor.db.sql.dbutils;
import geso.dms.center.beans.chitieunhanvien.imp.CTNhanvien;
import geso.dms.center.beans.chitieunhanvien.ICTNhanvien;

public class ChiTieuNhanvien implements IChiTieuNhanvien {

	public String loaiUpload = "1";  // 1 upload chi tieu bt, 2 upload  chi tieu do phu mat hang
	
	
	public String  isDisplay = "0";

	public String loai = "1";

	double Chitiet=0;
	String UserID;

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
	String KhuvucId = "";
	String VungId = "";
	List<ChiTieuNhanvien> listchitieu = new ArrayList<ChiTieuNhanvien>();
	dbutils db;
	
	ResultSet chitieuRs ;
	ResultSet VungRs ;
	ResultSet KhuvucRs ;
	
	List<Object> dataSearch = new ArrayList<Object>(); 
	
	public ChiTieuNhanvien(){
		this.NgayKetThuc="";
		this.SoNgayLamViec="";
		this.Message="";
		this.DienGiai="";
		this.KhuVuc="";
	//	this.loai = "1";
		this.isDisplay = "0";
		this.Id = "";
		this.loaiUpload = "1";
		db=new dbutils();
	}
	public ChiTieuNhanvien(String id,String loainv,String userid){
		db=new dbutils();
		this.UserID=userid;
		String  sql_getdata="";
		if(loainv.equals(""))
			loainv = "1";
		this.loai = loainv;
		
		sql_getdata="\n SELECT   c.trangthai,C.PK_SEQ, C.THANG, C.NAM, C.DIENGIAI,  C.NGAYTAO, C.NGAYSUA,NT.TEN AS NGUOITAO, "+ 
					"\n NS.TEN AS NGUOISUA FROM ChiTieuNhanVien AS C " +
					"\n INNER JOIN "+
					"\n dbo.NHANVIEN AS NT ON C.NGUOITAO = NT.PK_SEQ INNER JOIN dbo.NHANVIEN AS NS ON C.NGUOISUA = NS.PK_SEQ where c.trangthai!='2' and c.pk_seq="+ id;

		System.out.println("sql_getdata"+sql_getdata);

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
					this.setDienGiai(rsChiTieu.getString("DIENGIAI"));
					this.setNguoiTao(rsChiTieu.getString("NGUOITAO"));
					this.setNguoiSua(rsChiTieu.getString("NGUOISUA"));
					this.setTrangThai(rsChiTieu.getString("trangthai"));

					if(rsChiTieu.getString("trangthai").trim().equals("1"))
						this.isDisplay="1";


				}
			}
			rsChiTieu.close();	

			initBCNhanVien();
			

		}catch(Exception er){
			this.Message="Error ChiTieu.java - line : 88- detail error :"+ er.toString();
			System.out.println("Error Class name : CHITIEU.JAVA- LINE 216 :STRING SQL" + er.toString());
		}
		this.Message="";
		

	}


	public void initBCNhanVien()
	{
		this.VungRs = db.get("select * from vung where trangthai = 1 ");
		this.KhuvucRs = db.get("select * from khuvuc where trangthai = 1");
		if(VungId.length() > 0)
		{
			this.KhuvucRs = db.get("select * from khuvuc where trangthai = 1 and vung_fk = '"+this.VungId+"' ");
		}
		try
		{
			
			String query ="  ";
		System.out.println("loai bean "+loai);
		
		String ketSanPham ="";
		String hienthiSanPham ="";
		if(this.loaiUpload.equals("2"))
		{
			ketSanPham ="\n left join SanPham sp on nsp.SP_FK  = sp.pk_Seq  " ;
			hienthiSanPham = " + ' - SP: ' + isnull(sp.Ten,'') ";
		}
		
			if(this.loai.equals("1"))
			{	
				
				query ="\n select nv.TEN as N'Nhân viên',t.DIENGIAI as N'Diễn giải',isnull(nsp.TEN,'') "+hienthiSanPham+" as N'Nhóm sản phẩm',a.chitieu as N'Chỉ tiêu' from chitieunhanvien_DDKD a   " + 
				 "\n inner join DAIDIENKINHDOANH nv on a.nhanvien_fk = nv.PK_SEQ  " + 
				 "\n inner join TIEUCHITHUONG_CHITIET t on t.PK_SEQ = a.tctct_fk  " + 
				 "\n left join NhomSanPhamChiTieu nsp on nsp.PK_SEQ =a.nsp_fk" +
				 ketSanPham +
				 "\n where a.ctnv_fk = "+this.Id;
				
				if(VungId.length() > 0)
				{
					query+= " and exists (select 1 from vung where pk_seq in (select vung_fk from khuvuc where pk_seq in (select khuvuc_fk from nhaphanphoi where pk_seq = nv.npp_fk) )and pk_seq = '"+this.VungId+"')  ";
				}
				if(KhuvucId.length() > 0)
				{
					query+= " and exists (select 1  from khuvuc where pk_seq in (select khuvuc_fk from nhaphanphoi where pk_seq = nv.npp_fk) and pk_seq = '"+this.KhuvucId+"')";
				}
			
			}else if(this.loai.equals("4"))
			{
				query ="\n select nv.TEN as N'Nhân viên',t.DIENGIAI as N'Diễn giải',isnull(nsp.TEN,'') "+hienthiSanPham+" as N'Nhóm sản phẩm',a.chitieu as N'Chỉ tiêu' from chitieunhanvien_BM a   " + 
				 "\n inner join BM nv on a.nhanvien_fk = nv.PK_SEQ  " + 
				 "\n inner join TIEUCHITHUONG_CHITIET t on t.PK_SEQ = a.tctct_fk  " + 
				 "\n left join NhomSanPhamChiTieu nsp on nsp.PK_SEQ =a.nsp_fk" +
				 ketSanPham +
				 "\n where a.ctnv_fk = "+this.Id;
			}else if(this.loai.equals("2"))
			{
				query ="\n select nv.TEN as N'Nhân viên',t.DIENGIAI as N'Diễn giải',isnull(nsp.TEN,'') "+hienthiSanPham+" as N'Nhóm sản phẩm',a.chitieu as N'Chỉ tiêu' from chitieunhanvien_SS a   " + 
				 "\n inner join Giamsatbanhang nv on a.nhanvien_fk = nv.PK_SEQ  " + 
				 "\n inner join TIEUCHITHUONG_CHITIET t on t.PK_SEQ = a.tctct_fk  " + 
				 "\n left join NhomSanPhamChiTieu nsp on nsp.PK_SEQ =a.nsp_fk" +
				 ketSanPham +
				 "\n where a.ctnv_fk = "+this.Id;
			}else if(this.loai.equals("3"))
			{
				query ="\n select nv.TEN as N'Nhân viên',t.DIENGIAI as N'Diễn giải',isnull(nsp.TEN,'') as N'Nhóm sản phẩm',a.chitieu as N'Chỉ tiêu' from chitieunhanvien_ASM a   " + 
				 "\n inner join ASM nv on a.nhanvien_fk = nv.PK_SEQ  " + 
				 "\n inner join TIEUCHITHUONG_CHITIET t on t.PK_SEQ = a.tctct_fk  " + 
				 "\n left join NhomSanPhamChiTieu nsp on nsp.PK_SEQ =a.nsp_fk" +
				 "\n where a.ctnv_fk = "+this.Id;
			}
			else if(this.loai.equals("5"))
			{
				query ="\n select nv.TEN as N'Nhà phân phối',t.DIENGIAI as N'Diễn giải',isnull(nsp.TEN,'') as N'Nhóm sản phẩm',a.chitieu as N'Chỉ tiêu'"
				+ " from ChiTieuNhanVien_NPP a   " + 
				 "\n inner join nhaphanphoi nv on a.nhanvien_fk = nv.PK_SEQ  " + 
				 "\n inner join TIEUCHITHUONG_CHITIET t on t.PK_SEQ = a.tctct_fk  " + 
				 "\n left join NhomSanPhamChiTieu nsp on nsp.PK_SEQ =a.nsp_fk" +
				 "\n where a.ctnv_fk = "+this.Id;
			}
			System.out.println("initBCNhanVien "+query);
			this.chitieuRs= db.get(query);
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
	
	}


	public List<ChiTieuNhanvien> getChiTieu() {

		return listchitieu;
	}

	public void setListChiTieu(String sql) {

		String  sql_getdata = "";

		sql_getdata = "\n SELECT C.PK_SEQ,c.trangthai, C.THANG, C.NAM, C.DIENGIAI, C.NGAYTAO, C.NGAYSUA,NT.TEN AS NGUOITAO, " + 
		"\n NS.TEN AS NGUOISUA FROM ChiTieuNhanVien AS C INNER JOIN " +
		"\n dbo.NHANVIEN AS NT ON C.NGUOITAO = NT.PK_SEQ INNER JOIN dbo.NHANVIEN AS NS ON C.NGUOISUA = NS.PK_SEQ " +
		"\n where 1=1 ";
		if (sql!="") {
			sql_getdata = sql;
		} 
		sql_getdata+=" ORDER BY C.NAM DESC,C.THANG DESC ";
		System.out.println("Init List: "+sql_getdata);

		try{

			ResultSet rsChiTieu = this.db.getByPreparedStatement(sql_getdata, this.dataSearch);
			if (rsChiTieu != null){
				while (rsChiTieu.next()){
					ChiTieuNhanvien CT = new ChiTieuNhanvien();
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
				rsChiTieu.close();
			}
		}catch(Exception er){
			er.printStackTrace();
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

	public boolean CreateChiTieuLuongThuong(String values,String valuesNhom) 
	{
		try
		{
			if(!KiemTraHopLe()){
				return false;
			}

			db.getConnection().setAutoCommit(false);

			//kiem tra xenm chi tieu trong thoi gian nay da co chua
			String sql="select pk_seq from ChiTieuNhanVien where thang= " +this.Thang+" and nam="+ this.Nam +" and trangthai!=2";
			System.out.println("sql : "+sql);
			ResultSet rs_check=db.get(sql);
			if(rs_check!=null){
				if(rs_check.next()){//co du lieu
					this.setMessage("Chi Tieu Trong Thang Da Thiet Lap");
					rs_check.close();
					return false;
				}

			}	

			boolean pass = checkSQL(values,valuesNhom);
			if(!pass)
			{
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
				return false;
			}
			
			// thuc hien insert
			sql="insert into ChiTieuNhanVien(diengiai,THANG,NAM,NGAYTAO,NGUOITAO,NGAYSUA,NGUOISUA,TRANGTHAI) " +
			"values (N'"+this.DienGiai+"',"+this.Thang+","+this.Nam+",'"+this.NgayTao+"','"+this.NguoiTao+"','"+this.NgaySua+"','"+this.NguoiSua+"','0')";
			System.out.println("save chi tieu luong thuong " +sql);
			if(!db.update(sql)){

				this.Message="Loi :"+sql;		
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				return false;
			}		
			//thuc hien insert chi tiet
			String query = "select SCOPE_IDENTITY() as dhId";
			ResultSet rsDh = db.get(query);	
			try
			{
				rsDh.next();
				this.setID(rsDh.getString("dhId"));
			}catch(Exception ex){
				ex.printStackTrace();
				return false;
			}

			pass = false;
			
			
			
				pass = InsertSR(db,this.Id,values,valuesNhom);
				
				
				
		
			if(!pass)
			{	
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
				return false;
			}

			System.out.println("pass = " + pass);

	

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

	public boolean UpdateChiTieuLuongThuong(String values,String valuesNhom) 
	{
		try
		{
			if(!KiemTraHopLe()){
				return false;
			}
			db.getConnection().setAutoCommit(false);

			//kiem tra xenm chi tieu trong thoi gian nay da co chua
			String sql="select pk_seq from ChiTieuNhanVien where thang= " +this.Thang+" and nam="+ this.Nam +"  and trangthai!=2 and pk_seq !="+this.Id+" ";
			System.out.println("sql : "+sql);
			ResultSet rs_check=db.get(sql);
			if(rs_check!=null){
				if(rs_check.next()){//co du lieu
					this.setMessage("Chi Tieu Trong Thang Da Thiet Lap");
					rs_check.close();
					return false;
				}
			}				
			
			boolean pass = checkSQL(values,valuesNhom);
			if(!pass)
			{
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
				return false;
			}

			// thuc hien insert
			sql="update ChiTieuNhanVien set  " +
			" diengiai=N'"+this.DienGiai+"',THANG="+this.Thang+",NAM="+this.Nam+",NGAYSUA='"+this.NgaySua+"',NGUOISUA='"+this.NguoiSua+"' where  pk_seq= "+this.Id;
			System.out.println("save chi tieu luong thuong " +sql);
			if(db.updateReturnInt(sql)<=0){

				this.Message="Loi trang thai chitieu :"+sql;		
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				return false;
			}	

			pass = false;
			pass = InsertSR(db,this.Id,values,valuesNhom);
			if(!pass)
			{	
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
				return false;
			}

			System.out.println("pass = " + pass);

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
	
	public boolean checkSQL(String values,String valuesNhom)
	{
		try 
		{
			String sql = "",dk="";
			ResultSet rs_check ;
			
			String tableNhanVien = "Daidienkinhdoanh";
			if(loai.equals("2"))
				tableNhanVien = "GiamSatBanHang";
			if(loai.equals("3"))
				tableNhanVien = "ASM";
			if(loai.equals("4"))
				tableNhanVien = "BM";
			
			if(loai.equals("5"))
			{
				tableNhanVien = "NhanPhanPhoi ";
				dk = " and loainpp = '4' ";
			}
			if(loai.equals("6"))
			{
				tableNhanVien = "NhanPhanPhoi ";
				dk = " and loainpp = '1' ";
			}
			
			if(this.loaiUpload.equals("2"))
				sql=" select manv from ("+values+")data   group by manv,tctct_fk,sanpham_fk having  COUNT(manv) >1  ";
			else
				sql=" select manv from ("+values+")data   group by manv,tctct_fk having,sanpham_fk  COUNT(manv) >1  ";
			System.out.println("du lieu trung " +sql);
			rs_check=db.get(sql);
			String error="";
			if(rs_check!=null){
				while (rs_check.next()){//co du lieu

					error =error+","+rs_check.getString("manv");
				}
				rs_check.close();
			}

			if(error.length()>0){
				this.setMessage("Vui Lòng kiểm tra file Upload,Có một số mã bị trùng :" + error);
				return false;
			}

			sql="select manv from ("+values+")data where  manv not in (select pk_seq from "+tableNhanVien+" where 1= 1"+dk+")";
			System.out.println("kiem tra nhan vien " +sql);
			rs_check=db.get(sql);
			error="";
			if(rs_check!=null){
				while (rs_check.next()){//co du lieu

					error =error+","+rs_check.getString("manv");
				}
				rs_check.close();
			}
			if(error.length()>0){
				this.setMessage("Vui Lòng kiểm tra file Upload,Có một số mã "+tableNhanVien+" không tồn tại :" + error);
				return false;
			}

			sql="select tctct_fk from ("+values+")data " +
					"\n 	tctct_fk not in " +
					"\n		(	select pk_seq from tieuchithuong_chitiet " +
					"\n			where tieuchitinhthuong_fk in (select pk_seq from tieuchitinhthuong where loai ="+this.loai+" " +
					"\n				and trangthai = 1 and thang ="+this.Thang+" and nam ="+this.Nam+")" +
					"\n				and tieuchi in (select tieuchi from tieuchi )  )";
			System.out.println("kiem tra kpi " +sql);
			rs_check=db.get(sql);
			error="";
			if(rs_check!=null){
				while (rs_check.next()){//co du lieu

					error =error+","+rs_check.getString("tctct_fk");
				}
				rs_check.close();
			}
			if(error.length()>0){
				this.setMessage("Vui Lòng kiểm tra file Upload,Có một số mã  KPI khong ton tai  khai bao thuong KPI :" + error);
				return false;
			} 			

			return true;
		} 
		catch (Exception e) 
		{
			this.setMessage("Ban khong the them chi tieu moi nay, loi : " + e.toString());
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean DeleteChiTieuLuongThuong() {

		try{
			db.getConnection().setAutoCommit(false);


			String sql="update ChiTieuNhanVien  set trangthai = 2  where pk_seq= "+this.Id;
			if(!db.update(sql)){
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				this.setMessage("Vui Long Kiem Tra Lai.Loi :"+sql);
				return false;	
			}
			 sql="update ChiTieuNhanVien_ddkd  set trangthai = 2  where ctnv_fk= "+this.Id;
			if(!db.update(sql)){
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				this.setMessage("Vui Long Kiem Tra Lai.Loi :"+sql);
				return false;	
			}
			 sql="update ChiTieuNhanVien_vung  set trangthai = 2  where ctnv_fk= "+this.Id;
				if(!db.update(sql)){
					geso.dms.center.util.Utility.rollback_throw_exception(db);
					this.setMessage("Vui Long Kiem Tra Lai.Loi :"+sql);
					return false;	
				}
			 sql="update ChiTieuNhanVien_npp  set trangthai = 2  where ctnv_fk= "+this.Id;
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
			table ="ChiTieuNhanVien_GSBH";
		else if(this.loai.equals("3"))
			table ="ChiTieuNhanVien_ASM";
		else if(this.loai.equals("4"))
			table ="ChiTieuNhanVien_BM";
		else if(this.loai.equals("5"))
			table ="ChiTieuNhanVien_NPP";
		
		// xoa chi tieu cu
		sql="delete from "+table+" where CTNV_FK = '"+id+"'" ;
		System.out.println("delete chi tieu cu "+sql);
		if(!db.update(sql)){

			this.Message="Lỗi không thể xóa chỉ tiêu :"+sql;		
			return false;
		}	

		
		sql="  insert "+table+"(CTNV_FK,TCTCT_FK,NSP_FK,TIEUCHI,NHANVIEN_FK,CHITIEU) " +
		"select "+id+" ,data.TCTCT_FK,t.nhomsp_fk,t.tieuchi,data.manv,data.chitieu from  ("+values+")data inner join tieuchithuong_chitiet t on data.TCTCT_FK = t.pk_Seq  ";
		System.out.println("ChiTieuNhanVien_SR:" + sql );
		if(db.updateReturnInt(sql) <=0)
		{
			System.out.println("save ChiTieuNhanVien_SR: " +sql);
			this.Message="Lỗi không thể lưu chỉ tiêu :"+sql;			
			return false;
		}	
	
		
			String check = 	" select TCTCT_FK "+ 
				  " from "+table+" ctct "+
				  "  where exists "+ 
				  " (  select  * from chitieunhanvien ct inner join tieuchitinhthuong t on t.THANG != ct.THANG or ct.NAM != t.NAM "+ 
				  " inner join TIEUCHITHUONG_CHITIET tct on tct.TIEUCHITINHTHUONG_FK = t.PK_SEQ "+
				  " where ctct.CTNV_FK = ct.PK_SEQ     and tct.PK_SEQ = ctct.TCTCT_FK   ) and  ctct.CTNV_FK = '"+this.Id+"' ";
			ResultSet rs = db.get(check);
			if(rs != null)
			{
				try {
					if(rs.next())
					{
						String TCTCT_FK = rs.getString("TCTCT_FK");
						this.Message = "Lỗi Tồn tại mã công thức thưởng "+TCTCT_FK+" không thuộc tháng "+this.Thang+"- "+this.Nam;
						return false;
					}
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
					
			}
				return true;
	}

	public boolean InsertDoPhuMatHang(dbutils db, String id,String values,String valuesNhom)
	{
		String sql = "";

		String table ="ChiTieuNhanVien_DDKD_DoPhuMatHang";
		if(this.loai.equals("2"))
			return true;
		else if(this.loai.equals("3"))
			return true;
		
		// xoa chi tieu cu
		sql="delete from "+table+" where CTNV_FK = '"+id+"'" ;
		System.out.println("delete chi tieu cu "+sql);
		if(!db.update(sql)){

			this.Message="Loi :"+sql;		
			return false;
		}	
		sql="  insert "+table+"(CTNV_FK,TCTCT_FK,NSP_FK,SanPham_FK,tieuchi,NHANVIEN_FK,CHITIEU) " +
		"select "+id+" ,data.TCTCT_FK,t.NhomSP_FK,data.SanPHam_FK,t.tieuchi,data.manv,data.chitieu from  ("+values+")data inner join tieuchithuong_chitiet t on data.TCTCT_FK = t.pk_Seq  ";
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

	public boolean ChotChiTieuLuongThuong() {

		String sqlchot="";
	
		 sqlchot="update ChiTieuNhanVien set trangthai='1' where pk_seq="+ this.Id;
		
		/*if(this.loai.equals("2"))
		{

			sqlchot="	update b set b.trangthai=1 from chitieunhanvien a inner join chitieunhanvien_npp b "+
					"  on a.PK_SEQ=b.ctnv_fk  where a.PK_SEQ="+this.Id +
			        "	and b.nhanvien_fk in (select npp_fk from  PHAMVIHOATDONG where Nhanvien_fk= "+this.UserID+") ";
		}
		if(this.loai.equals("1"))
		{
			sqlchot="update b set b.trangthai=1 from chitieunhanvien a inner join ChiTieuNhanVien_DDKD b "+
					"  on a.PK_SEQ=b.ctnv_fk  where a.PK_SEQ="+this.Id+
					"  and b.NPP_FK =(select npp.PK_SEQ from NHAPHANPHOI npp inner join NHANVIEN nv on npp.SITECODE=nv.CONVSITECODE where nv.PK_SEQ= "+this.UserID+")";
		}*/
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

	public boolean UnChotChiTieuLuongThuong() {

		String sqlchot="update ChiTieuNhanVien set trangthai='0' where pk_seq="+ this.Id;
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

	private class SanPham
	{
		String nsp_fk ="";
		double chitieu = 0;
		public SanPham (String nsp_fk,double  chitieu)
		{
			this.nsp_fk = nsp_fk;
			this.chitieu = chitieu;
		}
	}

	public List<SanPham> getNSP(String format)
	{

		List<SanPham>  spList =  new ArrayList<SanPham>();

		format = format.replace("[","");
		String[] arrSp = format.split("][");
		for(int i = 0 ; i < arrSp.length ; i ++)
		{
			String[] arrTT = arrSp[i].split(";");
			SanPham sp = new SanPham(arrTT[0],Double.parseDouble(arrTT[1]));
			spList.add(sp);
		}
		return spList;
	}

	public String getLoai() {
		return loai;
	}
	
	public void setLoai(String loai) {
		this.loai = loai;
	}

	List<ICTNhanvien> listCTNhanVien = new ArrayList<ICTNhanvien>();
	public List<ICTNhanvien> getListCTNhanVien() {
		return listCTNhanVien;
	}
	
	public void setListCTNhanVien(List<ICTNhanvien> listCTNhanVien) {
		this.listCTNhanVien = listCTNhanVien;
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
	
	public String getLoaiUpload() {
		return loaiUpload;
	}
	public void setLoaiUpload(String loaiUpload) {
		this.loaiUpload = loaiUpload;
	}
	
	public void setVung(String Vung) {
		
		this.VungId = Vung;
	}
	
	public String getVung() {
		
		return this.VungId;
	}
	
	public void setKhuVuc(String KhuVuc) {
		
		this.KhuvucId  = KhuVuc;
	}
	
	public String getKhuVuc() {
		
		return KhuvucId;
	}
	
	public ResultSet getVungRS() {
		
		return this.VungRs;
	}
	
	public ResultSet getKhuVucRS() {
		
		return this.KhuvucRs;
	}
	public List<Object> getDataSearch() {
		return dataSearch;
	}
	public void setDataSearch(List<Object> dataSearch) {
		this.dataSearch = dataSearch;
	}
	
	
}
