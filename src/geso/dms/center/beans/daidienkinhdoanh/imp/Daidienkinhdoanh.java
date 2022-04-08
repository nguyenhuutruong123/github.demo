package geso.dms.center.beans.daidienkinhdoanh.imp;
import geso.dms.center.beans.daidienkinhdoanh.IDaidienkinhdoanh;
import geso.dms.center.db.sql.*;
import geso.dms.distributor.util.Utility;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.hssf.record.formula.Ptg;

public class Daidienkinhdoanh implements IDaidienkinhdoanh
{
	private static final long serialVersionUID = -9217977546733610214L;
	boolean isdelete;
	String userId;
	String id;
	String ten;
	String sodienthoai;
	String diachi;
	String trangthai;
	String ngaytao;
	String nguoitao;
	String ngaysua;
	String nguoisua;
	String msg;
	String cmnd;
	String ngaysinh;
	String quequan;
	String thuviec;
	String ngaybatdau;
	String ngayketthuc;
	String chonmt;
	String kenhbanhang;
	String gsbanhang;
	String nhaphanphoi;
	String filecap;
	String filethe;
	String filemat;
	String sotaikhoan;
	String chutaikhoan;
	String nganhang;
	String Smartid;
	String nppId;
	String ttppId;
	String ddkdTn;
	String PhanTramChuyen;
	String hinhanh;
	String matkhau;
	String maycap;
	String maythe;
	String maymat;
	String tiencap;
	String tienthe;
	String tienmat;
	String maerp;
	String nppasm;
	String imei = "";
	String[] gsbhIds;
	ResultSet nppList;
	ResultSet ttppList;
	ResultSet gsbhList;
	ResultSet RsDDKD;
	
	
	
	
	String view = "";
	ResultSet routeRs;
	String route_fk = "";
	String folderPath = "";
	ArrayList<String> imgList = new ArrayList<String>();
	dbutils db;
	
	public Daidienkinhdoanh(dbutils db) {
		this.db = db;
	}
	
	public Daidienkinhdoanh(String[] param)
	{
		this.db = new dbutils();
		this.id = param[0];
		this.ten = param[1];
		this.sodienthoai = param[2];
		this.diachi = param[3];
		this.trangthai = param[4];
		this.ngaytao = param[5];
		this.nguoitao = param[6];
		this.ngaysua = param[7];
		this.nguoisua = param[8];
		this.kenhbanhang = param[9];
		this.gsbanhang = param[10];
		this.nhaphanphoi = param[11];
		this.cmnd=param[12];
		this.ngaysinh=param[13];
		this.quequan=param[14];
		this.ngaybatdau=param[15];
		this.ngayketthuc=param[16];
		this.thuviec=param[17];
		this.maycap = param[18];
		this.maymat = param[19];
		this.maythe = param[20];
		this.filecap = param[18];
		this.filemat = param[19];
		this.filethe = param[20];
		this.chonmt =  param[21];
		this.tiencap = param[22];
		this.tienmat = param[23];
		this.tienthe = param[24];
		this.nppasm = param[24];
		this.hinhanh = "";
		System.out.println(this.thuviec);
		this.isdelete = true;
		this.msg = "";
		this.matkhau = "";

	}

	public Daidienkinhdoanh(String id)
	{
		this.db = new dbutils();
		this.id = id;
		this.ten = "";
		this.sodienthoai = "";
		this.diachi = "";
		this.trangthai = "2";
		this.ngaytao = "";
		this.nguoitao = "";
		this.ngaysua = "";
		this.nguoisua ="";
		this.kenhbanhang = "";
		this.gsbanhang = "";
		this.nhaphanphoi = "";
		this.nppId ="";
		this.ttppId = "";
		this.msg = "";
		this.ddkdTn="";
		this.PhanTramChuyen="0";
		this.cmnd="";
		this.ngaysinh="";
		this.quequan="";
		this.ngaybatdau="";
		this.ngayketthuc="";
		this.thuviec="0";
		this.sotaikhoan="";
		this.chutaikhoan="";
		this.nganhang="";
		this.isdelete = true;
		this.hinhanh = "";
		this.matkhau = "";
		this.Smartid = "";
		this.maymat ="";
		this.maycap ="";
		this.maythe ="";
		this.filecap ="";
		this.filethe ="";
		this.filemat ="";
		this.chonmt ="";
		this.tiencap ="";
		this.tienthe ="";
		this.tienmat ="";
		this.nppasm ="";
		this.maerp = "";
		if (id.length() > 0)
			this.init();
		else
			this.createRS();
	}

	public Daidienkinhdoanh(String id, String nppId)
	{

		this.db = new dbutils();
		this.id = id;
		this.ten = "";
		this.sodienthoai = "";
		this.diachi = "";
		this.trangthai = "2";
		this.ngaytao = "";
		this.nguoitao = "";
		this.ngaysua = "";
		this.nguoisua ="";
		this.kenhbanhang = "";
		this.gsbanhang = "";
		this.nhaphanphoi = "";
		this.nppId = nppId;
		this.ttppId = "";
		this.msg = "";
		this.ddkdTn="";
		this.PhanTramChuyen="0";
		this.cmnd="";
		this.ngaysinh="";
		this.quequan="";
		this.ngaybatdau="";
		this.ngayketthuc="";
		this.thuviec="0";
		this.sotaikhoan="";
		this.chutaikhoan="";
		this.nganhang="";
		this.isdelete = true;
		this.hinhanh = "";
		this.matkhau = "";
		this.Smartid = "";
		this.maymat ="";
		this.maycap ="";
		this.maythe ="";
		this.filecap ="";
		this.filethe ="";
		this.filemat ="";
		this.chonmt ="";
		this.tienmat ="";
		this.tiencap ="";
		this.tienthe ="";
		this.nppasm ="";
		this.maerp = "";
		if (id.length() > 0)
			this.init();
		else
			this.createRS();
	}
	
	public dbutils getDb() {
		return db;
	}
	public void setImgList(ArrayList<String> imgList) {
		this.imgList = imgList;
	}
	public ArrayList<String> getImgList() {
		return imgList;
	}
	public String getFolderPath() {
		return folderPath;
	}
	public void setFolderPath(String folderPath) {
		this.folderPath = folderPath;
	}
	public String getRoute_fk() {
		return route_fk;
	}
	public void setRoute_fk(String route_fk) {
		this.route_fk = route_fk;
	}
	public ResultSet getRouteRs() {
		return routeRs;
	}
	public void setRouteRs(ResultSet routeRs) {
		this.routeRs = routeRs;
	}
	public String getView() {
		return view;
	}
	public void setView(String view) {
		this.view = view;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public void setChuTaiKhoan(String chutaikhoan)
	{
		this.chutaikhoan=chutaikhoan;
	}
	public String getChuTaiKhoan()
	{
		return this.chutaikhoan;
	}
	public void setSoTaiKhoan(String sotaikhoan)
	{
		this.sotaikhoan=sotaikhoan;
	}
	public String getSoTaiKhoan()
	{
		return this.sotaikhoan;
	}
	public void setNganHang(String nganhang)
	{
		this.nganhang=nganhang;
	}
	public String getNganHang()
	{
		return this.nganhang;
	}

	public String getUserId()
	{
		return this.userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
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

	public String getSodt()
	{
		return this.sodienthoai;
	}

	public void setSodt(String sodienthoai)
	{
		this.sodienthoai = sodienthoai;
	}

	public String getDiachi()
	{
		return this.diachi;
	}

	public void setDiachi(String diachi)
	{
		this.diachi = diachi;
	}

	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}

	public String getNppId() 
	{		
		return this.nppId;
	}

	public void setNppId(String nppId) 
	{
		this.nppId = nppId;
	}

	public String getNgaytao()
	{
		return this.ngaytao;
	}

	public void setNgaytao(String ngaytao)
	{
		this.ngaytao = ngaytao;
	}

	public String getNguoitao()
	{
		return this.nguoitao;
	}

	public void setNguoitao(String nguoitao)
	{
		this.nguoitao = nguoitao;
	}

	public String getNgaysua()
	{
		return this.ngaysua;
	}

	public void setNgaysua(String ngaysua)
	{
		this.ngaysua = ngaysua;
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

	public String getKenhbanhang() 
	{
		return this.kenhbanhang;
	}

	public void setKenhbanhang(String kenhbanhang)
	{
		this.kenhbanhang = kenhbanhang;
	}

	public String getGsbanhang() 
	{
		return this.gsbanhang;
	}

	public void setGsbanhang(String gsbanhang) 
	{
		this.gsbanhang = gsbanhang;
	}

	public String getNhaphanphoi() 
	{
		return this.nhaphanphoi;
	}

	public void setNhaphanphoi(String nhaphanphoi) 
	{
		this.nhaphanphoi = nhaphanphoi;
	}

	public ResultSet getGsbhList() 
	{
		return this.gsbhList;
	}

	public void setGsbhList(ResultSet gsbhList) 
	{
		this.gsbhList = gsbhList;
	}

	public Hashtable getGsbhIds() 
	{
		Hashtable ht = new Hashtable();
		System.out.println("tren "+this.gsbhIds );
		if (this.gsbhIds != null){
			for (int i=0; i < this.gsbhIds.length; i++){
				System.out.println("fffff"+this.gsbhIds[i]);
				ht.put(i, this.gsbhIds[i]);			
			}
		}
		return ht;
	}

	public void setGsbhIds(String[] gsbhIds) 
	{
		this.gsbhIds = gsbhIds;
	}

	public void setNppList(ResultSet npplist)
	{
		this.nppList = npplist;
	}

	public ResultSet getNppList() 
	{
		return this.nppList;
	}

	public boolean getIsDelete() 
	{
		return this.isdelete;
	}

	public void setIsDelete(boolean isDelete) 
	{
		this.isdelete = isDelete;
	}

	public void createNppList()
	{
		String query = "select a.pk_seq as nppId, a.ten as nppTen, 'Khu vuc ' + b.ten as kvTen ";
		query += "from nhaphanphoi a inner join khuvuc b on a.khuvuc_fk = b.pk_seq where a.trangthai = '1' and a.sitecode = a.convsitecode order by b.pk_seq asc";
		this.nppList = this.db.get(query);
		// and priandsecond='0'
		System.out.println("Danh sach NPP lay duoc: " + query + "\n");
	}

	public void createTtppList()
	{
		String query = "select a.PK_SEQ,a.MA,a.TEN from TRUNGTAMPHANPHOI a where TRANGTHAI=1 ";
		this.ttppList = this.db.getScrol(query);
	}

	public void createGsbhList()
	{
		if ( this.kenhbanhang!=null && this.kenhbanhang.length()>0 &&this.nppId!=null && this.nppId.length()>0)
		{
			String query = "select a.pk_seq as id, a.ten as gsbhTen, kbh.ten as kbhTen from giamsatbanhang a " +
					"\n inner join nhapp_giamsatbh c on a.pk_seq = c.gsbh_fk" +
					"\n inner join kenhbanhang kbh on kbh.pk_seq = a.kbh_fk " +
					"where c.npp_fk='" + this.nppId + "' and a.trangthai = 1 and kbh.pk_seq =  "+ this.kenhbanhang;
			this.gsbhList = this.db.get(query);
			System.out.println("Danh sach GSBH lay duoc: "+query);
		}
		if (this.id.length() > 0 && (this.nppId == null || this.nppId.length() <=0 ) )
		{
			String query = "\n if ((select isnull(ismt,'') from DAIDIENKINHDOANH where PK_SEQ = '"+this.id+"') = 1) " +
						"\n select a.pk_seq as id, a.ten as gsbhTen, b.ten as kbhTen  " +
						"\n from giamsatbanhang a inner join DDKD_GSBH ddkd on a.PK_SEQ = ddkd.GSBH_FK " +
						"\n left join nhapp_giamsatbh e on e.gsbh_fk = a.pk_seq" +
						"\n left join KENHBANHANG b on kbh.KBH_FK = a.PK_SEQ " +
						 "\n where a.trangthai = 1  and ddkd.DDKD_FK = '"+this.id+"' "; 
			System.out.println("SQL: "+query);
			this.gsbhList = this.db.get(query);
		}
	}

	ResultSet logRs ;
	
	public void createRS() 
	{
		String idx =  this.id != null && this.id.trim().length() > 0 ? this.id :  "0";
		String query = "";
		if (this.nppId != null) {
			query = "select pk_seq, ten from dms_route a where npp_fk = "+this.nppId + " and not exists (select 1 from daidienkinhdoanh x where x.trangthai = 1 and x.route_fk = a.pk_seq and x.pk_seq != "+idx+" ) ";
			routeRs = db.get(query);
		}
		else {
			routeRs = null;
		}
		System.out.println("dms_route: " + query);
		
		createNppList();
		createTtppList();
		createGsbhList();
		CreateDDKDList();
		
		if (this.id != null && this.id.trim().length() > 0 )
		{
			query = "\n select l.trangthai,npp.MANPP, npp.TEN NPP, r.CodeRoute, r.ten routeName, gs.SMARTID magsbh, gs.TEN gsbh, " +
			"\n convert (varchar(19),l.thoidiem,121) ThoiDiem " + 
			"\n from DAIDIENKINHDOANH_LOG l " + 
			"\n left join NHAPHANPHOI npp on npp.PK_SEQ = l.npp_fk " + 
			"\n left join DMS_Route r on r.PK_SEQ = l.route_fk " + 
			"\n left join GIAMSATBANHANG gs on gs.PK_SEQ = l.gsbh_fk " + 
			"\n where l.PK_SEQ = " + this.id + 
			"\n order by l.thoidiem desc ";
			this.logRs = db.get(query);
		}
		
	}
	
	public ResultSet getLogRs() {
		return logRs;
	}


	private void CreateDDKDList() {
		//nppId phai khac null
		String query = "";
		System.out.println("nppId: '" + this.nppId + "' ");
		if (this.nppId.length()>0)
		{
			query = "select * from daidienkinhdoanh where npp_fk="+ this.nppId;
			if (this.id.length() > 0 )
				query+= " and pk_seq <> "+this.id;
			this.RsDDKD=this.db.get(query);
		}
		System.out.println("Danh sach DDKD lay duoc: '" + query + "' ");
	}

	public boolean CreateDdkd() 
	{
		try{
			this.db.getConnection().setAutoCommit(false);
			this.ngaytao = getDateTime();
			this.nguoitao = this.userId;

			if (this.ttppId.equals(""))
			{
				ttppId = null;
			}
			if(this.kenhbanhang == null || this.kenhbanhang.trim().length() <=0)
			{
				this.msg = "Vui lòng chọn kênh";
				this.db.getConnection().rollback();
				return false;
			}
			
			String sql = "select smartid from daidienkinhdoanh where smartid = '"+this.Smartid+"'";
			ResultSet ktrs = db.get(sql);
			if (ktrs.next())
			{
				this.msg = "Trùng mã đại diện kinh doanh. Vui lòng sửa lại mã đại diện kinh doanh.";
				return false;
			}

			Object[] _data = dbutils.setObject(this.route_fk);
			this.msg = checkRoute(_data, db);
			if (this.msg != null && this.msg.length() > 0) {
				this.db.getConnection().rollback();
				return false;
			}

			System.out.println("nppIdfwf "+this.nppId);
			List<Object> data = new ArrayList<Object>();
			String query = "";
			query = "\n insert into daidienkinhdoanh " +
			"\n ( kbh_fk,imei, ten, tinhtrang, cmnd, ngaysinh, quequan, ngaybatdau, ngayketthuc, dienthoai, diachi, " +
			"\n npp_fk, trangthai, nguoitao, nguoisua, ngaytao, ngaysua, phantramchuyen, taikhoan, " +
			"\n nganhang, chutaikhoan, HinhAnh, matkhau, smartid, Email, maerp) " +
			"\n	select  ?,?, ?, ?, ?, ?, ?, ?, ?, ?, ? , ?, 1, ?, ?, ?, ?, ?, ?, ?, ?, ?, " +
			"\n     PWDENCRYPT(?),  ?, ?, ? ";

			data.clear();
			
			data.add(this.kenhbanhang);data.add(this.imei);data.add(this.ten);data.add(this.thuviec);data.add(this.cmnd);data.add(this.ngaysinh);data.add(this.quequan);
			data.add(this.ngaybatdau);data.add(this.ngaybatdau);data.add(this.sodienthoai);data.add(this.diachi);data.add(this.nppId);
			data.add(this.nguoitao);data.add(this.nguoitao);data.add(this.ngaytao);data.add(this.ngaytao);
			data.add(this.PhanTramChuyen.trim().length()==0?null:this.PhanTramChuyen);data.add(this.sotaikhoan);data.add(this.nganhang);data.add(this.chutaikhoan);
			data.add(this.hinhanh);data.add(this.matkhau);data.add(this.Smartid);
			data.add(this.email);
			data.add(this.maerp);
			
			if ( this.db.updateQueryByPreparedStatement(query, data) != 1)
			{
				db.viewQuery(query, data);
				this.msg = "Lỗi tạo mới NVBH 1.";
				this.db.getConnection().rollback();
				return false;
			}	

			this.msg = query;	
			this.id = db.getPk_seq();

			if (Utility.isValid(gsbhIds)) {
				for (int i = 0; i < this.gsbhIds.length; i++)
				{
					_data = dbutils.setObject(this.gsbhIds[i]);
					query = "insert into ddkd_gsbh select '"+ this.id + "', ? ";
					if (db.update_v2(query, _data) < 0)
					{
						this.msg = "Lỗi tạo mới NVBH 2.";
						this.db.getConnection().rollback();
						return false;
					}
				}
			}

			if (Utility.isValid(this.ddkdTn))
			{
				_data = dbutils.setObject(ddkdTn);
				query = "\n update TuyenBanHang set DDKD_FK = ? where DDKD_FK='"+this.id+"' ";
				if (db.update_v2(query, _data) < 0)
				{
					this.msg = "Lỗi tạo mới NVBH 3.";
					this.db.getConnection().rollback();
					return false;
				}

				_data = dbutils.setObject(ddkdTn);
				query = "\n update TuyenBanHang set DDKD_FK = '"+this.id+"' where DDKD_FK = ? ";
				if (db.update_v2(query, _data) < 0)
				{
					this.msg = "Lỗi tạo mới NVBH 4.";
					this.db.getConnection().rollback();
					return false;
				}

				_data = dbutils.setObject(ddkdTn);
				query = "\n insert into Log_ChonTienNhiem(DDKD_FK,DDKDTN_FK,UserId) " +
				"\n select '"+this.id+"', ?, '"+this.userId+"' " ;
				if (!(this.db.update(query)))
				{
					this.msg = "Lỗi tạo mới NVBH 4.";
					this.db.getConnection().rollback();
					return false;
				}
			}

			query = "\n INSERT INTO TUYENBANHANG(DIENGIAI, NGAYLAMVIEC, NGAYTAO, NGAYSUA, NGUOITAO, NGUOISUA, DDKD_FK, NPP_FK, NGAYID) " + 
			"\n select N'Thứ ' + cast(ngay.ngayId  as varchar(10)), ngay.ngaylv AS NGAYLAMVIEC, '" + this.ngaytao + "', " +
			"\n '" + this.ngaytao + "', "+this.userId+", "+this.userId+", A.PK_SEQ AS DDKD, a.NPP_FK, ngay.ngayId   " + 
			"\n from DAIDIENKINHDOANH a " + 
			"\n inner join NHAPHANPHOI npp on npp.PK_SEQ = a.npp_fk, " + 
			"\n ( " + 
			"\n     select 2 as ngayId,'Thu hai' as ngaylv union " + 
			"\n     select 3 as ngayId, 'Thu ba' union " + 
			"\n     select 4 as ngayId, 'Thu tu' union " + 
			"\n     select 5 as ngayId, 'Thu nam' union " + 
			"\n     select 6 as ngayId, 'Thu sau' union " + 
			"\n     select 7 as ngayId, 'Thu bay' " + 
			"\n ) AS ngay " + 
			"\n where a.pk_seq = "+this.id+" " +
			"\n and not exists (select 1 from TUYENBANHANG tbh where tbh.ddkd_fk = a.pk_Seq and tbh.npp_fk = a.npp_fk and tbh.ngayId = ngay.ngayId )  " ;
			if (!(this.db.update(query)))
			{
				this.msg = "Lỗi tạo mới NVBH 5.";
				this.db.getConnection().rollback();
				return false;
			}
			
			String kq = updateDaidienkinhdoanh_log(this.db, this.id, "Tạo mới");
			if (Utility.isValid(kq))
			{
				this.msg = "Lỗi tạo mới NVBH 6.";
				this.db.getConnection().rollback();
				return false;
			}

			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		}
		catch(Exception e){
			geso.dms.center.util.Utility.rollback_throw_exception(this.db);
			this.msg = "Lỗi ngoại lệ: "+e.getMessage();
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public static String updateDaidienkinhdoanh_log(Idbutils dbX, String id, String menu)
	{
		String query = "\ninsert DAIDIENKINHDOANH_LOG(menu, PK_SEQ, route_fk, npp_fk, gsbh_fk, trangthai) " +
		"\n	select N'"+menu+"', PK_SEQ, route_fk, npp_fk, (select top 1 gsbh_fk from DDKD_GSBH x where x.DDKD_FK = a.PK_SEQ), " +
		"\n trangthai from DAIDIENKINHDOANH a " +
		"\n where pk_Seq = "+ id;
		if (!(dbX.update(query)))
		{
			return "Không thể ghi nhận Log.";
		}
		
		return "";
	}
	

	public boolean UpdateDdkd() 
	{
		this.ngaysua = getDateTime();
		this.nguoisua = this.userId;
		
		if (!Utility.isValid(ttppId))
		{
			ttppId = null;
		}
		if(this.kenhbanhang == null || this.kenhbanhang.trim().length() <=0)
		{
			this.msg = "Vui lòng chọn kênh";
			return false;
		}

		try
		{
			this.db.getConnection().setAutoCommit(false);
			
			if (!Utility.KiemTra_PK_SEQ_HopLe(this.id, "daidienkinhdoanh", this.db))
			{
				this.msg = "Định danh NVBH không hợp lệ!";
				return false;
			}
			
			Object[] _data = dbutils.setObject(this.route_fk, this.id);
			this.msg = checkRoute(_data, db);
			if (Utility.isValid(this.msg)) {
				this.db.getConnection().rollback();
				return false;
			}
			
			_data = dbutils.setObject(Smartid);
			String query = "\n select smartid  from daidienkinhdoanh where smartid = ? and pk_seq <> "+this.id+"";
			ResultSet ktrs = db.get_v2(query, _data);
			if (ktrs.next())
			{
				this.msg = "Trùng mã đại diện kinh doanh. Vui lòng sửa lại mã đại diện kinh doanh.";
				return false;
			}	
						
			route_fk = null;
			System.out.println("imei = "+ this.imei);
			_data = dbutils.setObject(this.kenhbanhang,Smartid, route_fk, 
				this.imei, email, ten, 
				thuviec, cmnd, quequan, ngaysinh, 
				ngaybatdau, ngayketthuc, sodienthoai, 
				diachi, (!Utility.isValid(this.nppId) ? null : this.nppId), 
				trangthai, userId, ngaysua, 
				sotaikhoan, nganhang, chutaikhoan, 
				hinhanh, filecap, filethe, 
				filemat, chonmt, 
				nppasm, ttppId, 
				(!Utility.isValid(this.maycap) ? null : this.maycap), 
				(!Utility.isValid(this.maythe) ? null : this.maythe), 
				(!Utility.isValid(this.maymat) ? null : this.maymat), 
				(!Utility.isValid(this.tiencap) ? null : this.tiencap), 
				(!Utility.isValid(this.tienmat) ? null : this.tienmat), 
				(!Utility.isValid(this.tienthe) ? null : this.tienthe),
				(!Utility.isValid(this.maerp) ? null : this.maerp)
			);
			
			query = "\n update daidienkinhdoanh set kbh_fk = ?,smartid = ?, route_fk = ?, " +
			"\n imei = ?, email = ?, ten = ?, " +
			"\n tinhtrang = ?, CMND = ?, quequan = ?, ngaysinh = ?, " +
			"\n ngaybatdau = ?, ngayketthuc = ?, dienthoai = ?, " +
			"\n diachi = ?, npp_fk = ?, " +
			"\n trangthai = ?, nguoisua = ?, ngaysua = ?, " +
			"\n taikhoan = ?, nganhang = ?, chutaikhoan = ?, " +
			"\n HinhAnh = ?, filemaycap = ?, filemaythe = ?, " +
			"\n filemaymat = ?, chonmaythe = ?, " +					
			"\n nppasmgiumay = ?, TTPP_FK = ?, " +
			"\n maydacap = ?, " +
			"\n maythechan = ?, " +
			"\n maymat = ?, " +
			"\n tienmaycap = ?, " +
			"\n tienmaymat = ?, " +
			"\n tienmaythe = ?, maerp = ? ";
			
			if (Utility.isValid(this.matkhau)) 
			{
				_data = dbutils.AddToObject(_data, matkhau);
				query += "\n , matkhau = PWDENCRYPT(?) ";
			}
			
			query += "\n where pk_seq = '" + this.id + "'" ;
			
			if (db.update_v2(query, _data) != 1)
			{
				this.msg = "Lỗi cập nhật 1.";
				this.db.getConnection().rollback();
				return false;
			}

			query = "delete ddkd_gsbh where ddkd_fk = '" + this.id + "'";
			db.update(query);
			
			if (Utility.isValid(gsbhIds)) {
				for (int i = 0; i < this.gsbhIds.length; i++)
				{
					_data = dbutils.setObject(this.gsbhIds[i]);
					query = "insert into ddkd_gsbh select '"+ this.id + "', ? ";
					if (db.update_v2(query, _data) < 0)
					{
						this.msg = "Lỗi cập nhật 2.";
						this.db.getConnection().rollback();
						return false;
					}
				}
			}
			
			query = "\n select DDKDTIENNHIEM as ddkdTnOLD,isnull(phantramchuyen,'') as phantramchuyen " +
			"\n from daidienkinhdoanh where pk_Seq = '"+id+"' and DDKDTIENNHIEM is not null ";
			System.out.println("QRTN "+query);
			ResultSet rs = db.get(query);
			String ddkdTnOLD = "", phantramchuyen = "";
			while(rs.next())
			{
				ddkdTnOLD = rs.getString("ddkdTnOLD");
				phantramchuyen = rs.getString("phantramchuyen");
			}
			rs.close();					
			
			if (Utility.isValid(ddkdTn) && Utility.isValid(ddkdTnOLD) && !ddkdTnOLD.equals(this.ddkdTn))
			{				
				_data = dbutils.setObject((Utility.isValid(ddkdTn) ? ddkdTn : null), PhanTramChuyen);
				query = "\n update daidienkinhdoanh set DDKDTIENNHIEM = ?, PHANTRAMCHUYEN = ? " +
				"\n where pk_seq = '" + this.id + "' ";
				if (db.update_v2(query, _data) < 0)
				{
					this.msg = "Lỗi cập nhật 3.";
					this.db.getConnection().rollback();
					return false;
				}
				
				/*
				query = "select count(*) as kt from ChiTieuNhanVien a inner join ChiTieuNhanVien_DDKD b on a.pk_seq=b.CTNV_FK "+
						" where a.thang="+this.getDateTime().substring(5,7)+" and a.nam="+this.getDateTime().substring(0,4)+" and b.NhanVien_FK="+ this.id;
				int kt = 0;
				rs=db.get(query);
				while(rs.next())
				{
					kt=rs.getInt("kt");
				}
				rs.close(); 
				
				if (kt < 1)
				query = " insert into ChiTieuNhanVien_DDKD(CTNV_FK,TCTCT_FK,NSP_FK,TieuChi,NhanVien_FK,chitieu)   "
						+ " select a.pk_seq,b.TCTCT_FK,b.NSP_FK,b.tieuchi, "+this.id+", cast( cast(b.chitieu as float)*"+PhanTramChuyen+"/100 as numeric(18,0)) " +
						" from ChiTieuNhanVien a inner join ChiTieuNhanVien_DDKD b on a.pk_seq=b.CTNV_FK " + 
						" where a.thang="+this.getDateTime().substring(5,7)+" and a.nam="+this.getDateTime().substring(0,4)+" and b.NhanVien_FK = "+ this.ddkdTn;
				else
					query = " update ChiTieuNhanVien_DDKD set chitieu = cast( cast(b.chitieu as float)*"+PhanTramChuyen+"/100 as numeric(18,0)) " +
							" from ( select b.CTNV_FK, b.chitieu  from " + 
							" ChiTieuNhanVien a inner join ChiTieuNhanVien_DDKD b on a.pk_seq=b.CTNV_FK " + 
							" where a.thang="+this.getDateTime().substring(5,7)+" and a.nam="+this.getDateTime().substring(0,4)+" and b.NhanVien_FK="+ this.ddkdTn +") b "+
							" where ChiTieuNhanVien_DDKD.CTNV_FK = b.CTNV_FK and ChiTieuNhanVien_DDKD.NhanVien_FK = "+this.id;
				System.out.println("ChiTieuNhanVien_DDKD: "+query);
				if (!(this.db.update(query))){
					this.msg = "Không thể chuyển chỉ tiêu: "+ query;
					geso.dms.center.util.Utility.rollback_throw_exception(this.db);
					return false;
				}
				query= " update b set b.chitieu = b.chitieu - cast( b.chitieu as float) /100*"+PhanTramChuyen+" "+
					   " from ChiTieuNhanVien a inner join ChiTieuNhanVien_DDKD b on a.pk_seq=b.CTNV_FK " + 
					   " where thang="+this.getDateTime().substring(5,7)+" and nam="+this.getDateTime().substring(0,4)+" and NhanVien_FK ="+ this.ddkdTn;
				System.out.println("Chuyen chi tieu : "+query);
				if (!(this.db.update(query))){
					this.msg = "Không thể chuyển chỉ tiêu: "+ query;
					geso.dms.center.util.Utility.rollback_throw_exception(this.db);
					return false;
				}
				*/
				
				_data = dbutils.setObject(ddkdTn);
				query = "update TuyenBanHang set DDKD_FK = '"+this.id+"' where DDKD_FK = ? ";
				if (db.update_v2(query, _data) < 0)
				{
					this.msg = "Lỗi cập nhật 4.";
					this.db.getConnection().rollback();
					return false;
				}

				_data = dbutils.setObject(ddkdTn);
				query = "\n insert into Log_ChonTienNhiem(DDKD_FK, DDKDTN_FK, UserId) " +
				"\n select '"+this.id+"', ?, '"+this.userId+"' " ;
				if (db.update_v2(query, _data) < 0)
				{
					this.msg = "Lỗi cập nhật 5.";
					this.db.getConnection().rollback();
					return false;
				}
			}
			
			query = "\n INSERT INTO TUYENBANHANG(DIENGIAI, NGAYLAMVIEC, NGAYTAO, NGAYSUA, NGUOITAO, NGUOISUA, DDKD_FK, NPP_FK, NGAYID) " + 
			"\n select N'Thứ ' + cast(ngay.ngayId  as varchar(10)), ngay.ngaylv AS NGAYLAMVIEC, '" + this.ngaytao + "', " +
			"\n '" + this.ngaytao + "', "+this.userId+", "+this.userId+", A.PK_SEQ AS DDKD, a.NPP_FK, ngay.ngayId   " + 
			"\n from DAIDIENKINHDOANH a " + 
			"\n inner join NHAPHANPHOI npp on npp.PK_SEQ = a.npp_fk, " + 
			"\n ( " + 
			"\n     select 2 as ngayId,'Thu hai' as ngaylv union " + 
			"\n     select 3 as ngayId, 'Thu ba' union " + 
			"\n     select 4 as ngayId, 'Thu tu' union " + 
			"\n     select 5 as ngayId, 'Thu nam' union " + 
			"\n     select 6 as ngayId, 'Thu sau' union " + 
			"\n     select 7 as ngayId, 'Thu bay' " + 
			"\n ) AS ngay " + 
			"\n where a.pk_seq = "+this.id+" " +
			"\n and not exists (select 1 from TUYENBANHANG tbh where tbh.ddkd_fk = a.pk_Seq and tbh.npp_fk = a.npp_fk and tbh.ngayId = ngay.ngayId )  " ;
			if (!(this.db.update(query)))
			{
				this.msg = "Lỗi cập nhật 6.";
				this.db.getConnection().rollback();
				return false;
			}
					
			String kq = updateDaidienkinhdoanh_log(this.db ,this.id,"Cập nhật");
			if (kq.trim().length() >0)
			{
				this.msg = "Lỗi cập nhật 7.";
				this.db.getConnection().rollback();
				return false;			
			}	
			
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
			
			/*if (this.PhanTramChuyen.length() > 0 && this.ddkdTn.length() > 0 
					&& ddkdTnOLD.equals(this.ddkdTn) && !phantramchuyen.equals(this.PhanTramChuyen) )
			{
				this.msg = "Cập nhật thông tin NVBH thành công. Không được phép chuyển chỉ tiêu NVBH tiền nhiệm vì đã chuyển chỉ tiêu trước đó !";
				return false;
			}*/

		}
		catch(Exception e)
		{
			this.msg = "Exception  "+ e.getMessage();
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return false;
		}
	
		return true;
	}	

	public void init() 
	{
		String query = "\n select isnull(a.imei,'')imei ,ISNULL(A.MAERP, '') MAERP, isnull(a.route_fk,-1) route_fk, a.smartid, isnull(a.tinhtrang,'0') as tinhtrang, isnull(a.cmnd,'') as cmnd, " +
		"\n isnull(a.quequan,'') as quequan, isnull(a.ngaysinh,'') as ngaysinh, isnull(a.ngaybatdau,'') as ngaybatdau, " +
		"\n isnull(a.ngayketthuc,'') as ngayketthuc, a.pk_seq as id, a.ten, a.dienthoai, a.diachi, a.trangthai, a.ngaytao, " +
		"\n b.ten as nguoitao, a.ngaysua, c.ten as nguoisua, d.pk_seq as nppId, d.ten as nppTen, isnull(a.maydacap, 0) as maydacap, " +
		"\n isnull(a.maythechan,0) as maythechan, isnull(a.maymat,0) as maymat, a.filemaycap, a.filemaythe, a.filemaymat, " +
		"\n isnull(a.chonmaythe,0) as chonmaythe, isnull(a.tienmaythe,0) as tienmaythe, isnull(a.tienmaycap,0) as tienmaycap, " +
		"\n isnull(a.tienmaymat,0) as tienmaymat, nppasmgiumay, " +
		"\n f.ten as gsbhTen, a.kbh_fk ,isnull(a.nganhang,'') as nganhang, isnull(a.taikhoan,'') as taikhoan, " +
		"\n isnull(a.chutaikhoan,'') as chutaikhoan, ISNULL(a.HinhAnh,'') as HinhAnh, a.TTPP_FK,a.email, a.ddkdtiennhiem, a.phantramchuyen " +
		"\n from daidienkinhdoanh a inner join nhanvien b on a.nguoitao = b.pk_seq " + 
		"\n inner join nhanvien c on a.nguoisua = c.pk_seq " +
		"\n left join nhaphanphoi d on a.npp_fk = d.pk_seq " +
		"\n left join ddkd_gsbh e on  e.ddkd_fk = a.pk_seq " +
		"\n left join  giamsatbanhang f on e.gsbh_fk = f.pk_seq " +
		"\n where a.pk_seq = "+this.id;
		System.out.println("Init: "+query);
		ResultSet rs =  this.db.get(query);
		try{
			rs.next();      
			
			this.id = rs.getString("id");
			this.maerp = rs.getString("maerp");
			this.route_fk = rs.getString("route_fk");
			this.ten = rs.getString("ten");
			this.sodienthoai = rs.getString("dienthoai");
			this.diachi = rs.getString("diachi");
			this.trangthai = rs.getString("trangthai");
			this.ngaytao = rs.getDate("ngaytao").toString();
			this.nguoitao = rs.getString("nguoitao");
			this.ngaysua = rs.getDate("ngaysua").toString();
			this.nguoisua = rs.getString("nguoisua");
			this.kenhbanhang = rs.getString("kbh_fk") == null ? "": rs.getString("kbh_fk");
			this.gsbanhang = rs.getString("gsbhTen");
			this.nhaphanphoi = rs.getString("nppTen");
			this.nppId = rs.getString("nppId")==null?"": rs.getString("nppId");
			this.email = rs.getString("email")==null?"": rs.getString("email");
			this.cmnd = rs.getString("cmnd");
			this.ngaysinh = rs.getString("ngaysinh");
			this.quequan = rs.getString("quequan");
			this.ngaybatdau = rs.getString("ngaybatdau");
			this.ngayketthuc = rs.getString("ngayketthuc");
			this.thuviec = rs.getString("tinhtrang");
			this.nganhang = rs.getString("nganhang");
			this.sotaikhoan = rs.getString("taikhoan");
			this.chutaikhoan = rs.getString("chutaikhoan");
			this.imei  = rs.getString("imei");
			this.hinhanh = rs.getString("HinhAnh");
			if (this.hinhanh != null && this.hinhanh.trim().length() > 0) {
				imgList.clear();
				imgList.add(this.hinhanh);
			}
			
			this.ttppId = rs.getString("TTPP_FK");
			this.Smartid = rs.getString("Smartid");
			this.ddkdTn = rs.getString("ddkdtiennhiem");
			this.maycap = rs.getString("maydacap");
			this.maymat = rs.getString("maymat");
			this.maythe = rs.getString("maythechan");
			this.filecap = rs.getString("filemaycap");
			this.filethe = rs.getString("filemaythe");
			this.filemat = rs.getString("filemaymat");
			this.tiencap = rs.getString("tienmaycap");
			this.tienthe = rs.getString("tienmaythe");
			this.tienmat = rs.getString("tienmaymat");
			this.chonmt = rs.getString("chonmaythe");
			this.nppasm = rs.getString("nppasmgiumay");
			this.PhanTramChuyen = rs.getString("phantramchuyen")==null?"0":rs.getString("phantramchuyen");
			
			rs = this.db.get("select count(gsbh_fk) as num from ddkd_gsbh where ddkd_fk = '" + this.id + "'");
			rs.next();
			this.gsbhIds = new String[Integer.valueOf(rs.getString("num")).intValue()];
			rs.close();

			rs = this.db.get("select gsbh_fk as gsbhId from ddkd_gsbh where ddkd_fk = '" + this.id + "'");
			if (rs != null){
				int i = 0;
				while(rs.next()){
					this.gsbhIds[i] = rs.getString("gsbhId");
					i++;
				}
			}
		}
		catch(Exception e){e.printStackTrace();}
		createRS(); 
	}

	private String getDateTime() 
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);	
	}

	public void DBClose() {
		try{

			if (nppList!=null){
				nppList.close();
			}

			if (gsbhList!=null){
				gsbhList.close();
			}
			
			if (RsDDKD!=null){
				RsDDKD.close();
			}
			
			if (db!=null){
				db.shutDown();
			}
		}catch(Exception er){
			er.printStackTrace();
		}
	}


	public ResultSet GetRsDDKDTienNhiem() {

		return this.RsDDKD;
	}


	public void setDDKDTn(String id) {

		this.ddkdTn=id;
	}


	public String getDDKDTn() {

		return this.ddkdTn;
	}


	public void setPhantramChuyen(String phantram) {

		this.PhanTramChuyen=phantram;
	}


	public String getPhanTramChuyen() {

		return this.PhanTramChuyen;
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
	
	
	public String getMaycap() {

		return this.maycap;
	}


	public void setMaycap(String maycap) {
		this.maycap=maycap;

	}
	
	public String getMaymat() {

		return this.maymat;
	}


	public void setMaymat(String maymat) {
		this.maymat=maymat;

	}
	
	public String getTienmat() {

		return this.tienmat;
	}


	public void setTienmat(String tienmat) {
		this.tienmat=tienmat;

	}
	
	public String getTiencap() {

		return this.tiencap;
	}


	public void setTiencap(String tiencap) {
		this.tiencap=tiencap;

	}
	
	public String getTienthe() {

		return this.tienthe;
	}


	public void setTienthe(String tienthe) {
		this.tienthe=tienthe;

	}
	
	public String getNppasm() {

		return this.nppasm;
	}


	public void setNppasm(String nppasm) {
		this.nppasm=nppasm;

	}
	
	public String getMaythe() {

		return this.maythe;
	}


	public void setMaythe(String maythe) {
		this.maythe=maythe;

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
	public void setHinhanh(String ha) {
		this.hinhanh = ha;
	}

	public String getHinhanh() {
		return this.hinhanh;
	}

	@Override
	public void setMatkhau(String matkhau) {
		this.matkhau = matkhau;
	}

	@Override
	public String getMatkhau() {
		return this.matkhau;
	}


	public ResultSet getTrungtamphanphoiList() {
		return this.ttppList;
	}

	@Override
	public String getTtppId() {
		return this.ttppId;
	}

	@Override
	public void setTtppId(String ttppId) {
		this.ttppId = ttppId;		
	}

	@Override
	public String getSmartId() {
		return this.Smartid;
	}

	@Override
	public void setSmartId(String smartid) {
		this.Smartid = smartid;
	}

	String email="";

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}
	
	public String getFilecap() {

		return this.filecap;
	}


	public void setFilecap(String filecap) {

		this.filecap=filecap;
	}
	
	
	public String getFilethe() {

		return this.filethe;
	}
	public void setFilethe(String filethe) {

		this.filethe=filethe;
	}
	
	public String getChonmt() {

		return this.chonmt;
	}
	public void setChonmt(String chonmt) {

		this.chonmt=chonmt;
	}
	
	public String getFilemat() {

		return this.filemat;
	}


	public void setFilemat(String filemat) {

		this.filemat=filemat;
	}

	public String checkRoute(Object[] _data, Idbutils db) {
		String query = "\n select count(*) c from daidienkinhdoanh where route_fk = ? and trangthai =1  ";
		if (_data != null && _data.length > 1) {
			query += "\n and pk_seq != ?";
		}
		int check = -1;
		ResultSet rs = db.get_v2(query, _data);
		try {
			while(rs.next()) {
				check = rs.getInt("c");
			}
			rs.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		if (check > 0) {
			return "Route này đã tồn tại, vui lòng đổi Route!";
		}else{
			return "";
		}
	}
	
	public boolean saveImage(dbutils db, ArrayList<String> imgList) {
		if (id != null && id.length() > 0) {
			//Cho chạy vòng for biết đâu mai mốt nó yêu cầu Up nhiều ảnh.
			for (int i = 0; i < imgList.size(); i++) { 
				String query = "update daidienkinhdoanh set hinhanh = '"+imgList.get(i)+"' where pk_seq = "+id;
				if (!db.update(query)) {
					this.msg = "Lỗi cập nhật hình ảnh cho NVBH!";
					return false;
				}
			}
		}
		else {
			this.msg = "Không tìm thấy định danh của NVBH!";
			return false;
		}
		
		return true;
	}

	@Override
	public String getMaERP() {
		// TODO Auto-generated method stub
		return this.maerp;
	}

	@Override
	public void setMaERP(String maerp) {
		// TODO Auto-generated method stub
		this.maerp = maerp;
	}
	public ResultSet getKbhRs()
	{
		String query = " select pk_seq,ten from kenhbanhang where trangthai = 1 ";
		return db.get(query);
	}
}
	