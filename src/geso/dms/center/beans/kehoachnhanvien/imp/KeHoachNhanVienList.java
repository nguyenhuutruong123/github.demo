package geso.dms.center.beans.kehoachnhanvien.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import geso.dms.center.beans.kehoachnhanvien.IKeHoachNhanVienList;
import geso.dms.center.db.sql.Idbutils;
import geso.dms.center.db.sql.dbutils;
import geso.dms.distributor.util.Utility;

public class KeHoachNhanVienList implements IKeHoachNhanVienList
{
	private static final long serialVersionUID = -9217977546733610214L;
	
	// Tieu chi tim kiem
	String userId;
	String nhanvienId = "";
	String nhanvienTen = "";
	String loai = "";
	String thang;
	String nam;
	String Msg;
	ResultSet khnvRs; 
	String kbhId;
	ResultSet kbhRs; 
	
	ResultSet vungRs;
	ResultSet khuvucRs;
	String vungId;
	String khuvucId;
	String main_query = "";
	dbutils db;

	private String Loai;
	
	public KeHoachNhanVienList(String[] param)
	{
		this.db = new dbutils();
		this.nhanvienTen = param[0];
		this.thang = param[1];
		this.nam = param[2];
		this.userId = "";
		this.vungId = "";
		this.khuvucId = "";
		this.kbhId = "";
	}
	
	public KeHoachNhanVienList()
	{
		this.db = new dbutils();
		this.userId = "";
		this.nhanvienTen = "";
		this.thang = "";
		this.nam = "";
		this.Msg ="";
		this.vungId = "";
		this.khuvucId = "";
		this.kbhId = "";
	}
	
	public KeHoachNhanVienList(boolean abc)
	{
		this.db = new dbutils();
		this.userId = "";
		this.nhanvienTen = "";
		this.thang = "";
		this.nam = "";
		this.Msg ="";
		this.vungId = "";
		this.kbhId = "";
		this.khuvucId = "";
	}
	public String getKbhId() {
		return kbhId;
	}

	public void setKbhId(String kbhId) {
		this.kbhId = kbhId;
	}

	public ResultSet getKbhRs() {
		return kbhRs;
	}

	public void setKbhRs(ResultSet kbhRs) {
		this.kbhRs = kbhRs;
	}

	public String getMain_query() {
		String query = "\n select case when a.tutao = 1 or a.loai not in (1,2,3) then 1 else 0 end allowChot, * " +
		"\n from " +
		"\n ( " +
		"\n     select 0 tutao, isnull(nv.loai,0) loai, a.nhanvien_fk, a.pk_seq, nv.ten as nhanvien, " +
		"\n         a.thang, a.nam, a.ngaytao, b.ten as nguoitao, a.ngaysua, c.ten as nguoisua, " +
		"\n         isnull(a.trangthai, 0) as trangthai, '1' as loaikehoach ,ql.khuvuc_fk, ql.vung_fk, ql.KBH_FK " + 
		"\n     from kehoachnv a " + 
		"\n     inner join nhanvien nv on nv.pk_seq = a.nhanvien_fk " + 
		"\n     inner join nhanvien b on a.nguoitao = b.pk_seq " + 
		"\n     inner join nhanvien c on a.nguoisua = c.pk_seq " +
		"\n     outer apply (select khuvuc_fk , vung_fk,KBH_FK from [dbo].[ufn_VungKhuVuc_NhanVien](nv.pk_seq ) x ) ql " +
		"\n     where a.nhanvien_fk = " + this.userId + 
		"\n     union " +
		"\n     select 1 tutao, isnull(nv.loai,0) loai, a.nhanvien_fk, a.pk_seq, nv.ten as nhanvien, " +
		"\n         a.thang, a.nam, a.ngaytao, b.ten as nguoitao, a.ngaysua, c.ten as nguoisua, " +
		"\n         isnull(a.trangthai, 0) as trangthai, '0' as loaikehoach,ql.khuvuc_fk, ql.vung_fk, ql.KBH_FK  " +
		"\n     from kehoachnv a " + 
		"\n     inner join nhanvien nv on nv.pk_seq = a.nhanvien_fk " + 
		"\n     inner join nhanvien b on a.nguoitao = b.pk_seq " + 
		"\n     inner join nhanvien c on a.nguoisua = c.pk_seq " +
		"\n     outer apply (select khuvuc_fk , vung_fk,KBH_FK from [dbo].[ufn_VungKhuVuc_NhanVien](nv.pk_seq ) x ) ql " +
		"\n     where 1 = 1 "+(getIdNhanVienCapDuoiQuery().equals("null") ? "" : " and a.nhanvien_fk in ( "+ getIdNhanVienCapDuoiQuery()+" )")+
		"\n ) a " +
		"\n where 1 = 1 ";
		
		return query;
	}
	
	public void setMain_query(String main_query) {
		this.main_query = main_query;
	}
	
	public ResultSet getKhnvRs() 
	{
		return this.khnvRs;
	}

	public void setKhnvRs(ResultSet khnvRs)
	{
		this.khnvRs = khnvRs;
	}
	
	public String getUserId() 
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;
	}
	
	public String getTenNhanVien() 
	{
		return this.nhanvienTen;
	}

	public void setTenNhanVien(String nhanvienTen) 
	{
		this.nhanvienTen = nhanvienTen;
	}

	public String getLoai() 
	{
		return this.loai;
	}

	public void setLoai(String loai) 
	{
		this.loai = loai;
	}

	public String getThang() 
	{
		return this.thang;
	}

	public void setThang(String thang) 
	{
		this.thang = thang;
	}

	public String getNam() 
	{
		return this.nam;
	}

	public void setNam(String nam) 
	{
		this.nam = nam;
	}
	
	public void init(String search) 
	{
		String query = "";
		query = "select * from VUNG where TRANGTHAI = 1";

		this.vungRs = this.db.get(query);

		query = "select * from KHUVUC where TRANGTHAI = 1";
		if (this.vungId != null && this.vungId.length() > 0)
			query += " and VUNG_FK = '"+this.vungId+"'";

		this.khuvucRs = this.db.get(query);
		this.kbhRs = this.db.get("select ten,pk_seq from kenhbanhang");
		if (search == null || search.trim().length() == 0)
		{			
			query = getMain_query();			
			query += "\n order by loaikehoach, trangthai, nam desc, thang desc ";
		}
		else
		{
			query = search;
		}
		
		System.out.println("[KeHoachNhanVienList.init] query = " + query);
		khnvRs = this.db.get(query);  
	}
	
	/**
	 * Lấy câu sql trả về các pk_seq nhân viên cấp dưới của nhân viên này
	 * @return String result
	 */
	
	public static String getIdNhanVienCapDuoiQuery(Idbutils db, String userId) {
		
		String query = "\n SELECT LOAI, isnull(isadmin,'') isadmin, phanloai " +
		"\n FROM NHANVIEN WHERE PK_SEQ = " + userId;
		System.out.println("[KeHoachNhanVienList.init] query = " + query);
		ResultSet rs = db.get(query);
		String loai = "";
		String isadmin  = "";
		String phanloai = "";
		try 
		{
			rs.next();
			loai = rs.getString("LOAI") == null ? "" : rs.getString("LOAI");
			isadmin = rs.getString("isadmin") == null ? rs.getString("isadmin") : rs.getString("isadmin");
			phanloai = rs.getString("phanloai") == null ? rs.getString("phanloai") : rs.getString("phanloai");
			rs.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		if ( phanloai.equals("2") && !loai.equals("1")&& !loai.equals("2")&& !loai.equals("3") ) //Admin HO
			isadmin = "1";
		
		query = "null";		
		if (isadmin.equals("1") || userId.equals("100002"))
		{
			query = " SELECT PK_SEQ FROM NHANVIEN WHERE LOAI in (select loai from loainhanvien) ";
		}
		else if (loai.equals("1")) // BM chỉ lấy của ASM
		{
			query = "\n SELECT PK_SEQ FROM NHANVIEN WHERE LOAI = 2 AND ASM_FK IN ( " +
			"\n 	SELECT DISTINCT(ASM_FK) FROM ASM_KHUVUC Y INNER JOIN ASM Z ON Z.PK_SEQ = Y.ASM_FK WHERE Y.KHUVUC_FK IN ( " +
			"\n 		SELECT PK_SEQ FROM KHUVUC WHERE VUNG_FK IN ( " +
			"\n 			SELECT VUNG_FK FROM BM_CHINHANH A INNER JOIN VUNG B ON B.PK_SEQ = A.VUNG_FK WHERE A.BM_FK IN ( " +
			"\n 				SELECT BM_FK FROM NHANVIEN WHERE PK_SEQ = "+userId+" " +
			"\n 			) AND B.TRANGTHAI = 1 " +
			"\n 		) AND TRANGTHAI = 1 " +
			"\n 	) AND Z.TRANGTHAI = 1 " +
			"\n ) AND TRANGTHAI = 1 " ;
			query += "\n union  SELECT PK_SEQ FROM NHANVIEN WHERE LOAI = 3 AND GSBH_FK IN ( " +
					"\n 	SELECT DISTINCT(GSBH_FK) FROM GSBH_KHUVUC Q INNER JOIN GIAMSATBANHANG P ON Q.GSBH_FK = P.PK_SEQ WHERE Q.KHUVUC_FK IN ( " +
					"\n 		SELECT PK_SEQ FROM KHUVUC WHERE VUNG_FK IN ( " +
					"\n 			SELECT VUNG_FK FROM BM_CHINHANH A INNER JOIN VUNG B ON B.PK_SEQ = A.VUNG_FK WHERE A.BM_FK IN ( " +
					"\n 				SELECT BM_FK FROM NHANVIEN WHERE PK_SEQ = "+userId+" " +
					"\n 			) AND B.TRANGTHAI = 1 " +
					"\n 		) AND TRANGTHAI = 1 " +
					"\n 	) AND P.TRANGTHAI = 1 " +
					"\n ) AND TRANGTHAI = 1 ";

		} 
		else if (loai.equals("3")) // loai GSBH lấy bản thân
		{
			
			query = "\n 0 "; // gs ko laasy cap duoi
		} 		
		else if (loai.equals("2")) 	//ASM: lấy tất cả giám sát bán hàng
		{
		
			query = "\n SELECT PK_SEQ FROM NHANVIEN WHERE LOAI = 3 AND GSBH_FK IN ( " +
			"\n 	SELECT DISTINCT(GSBH_FK) FROM GSBH_KHUVUC Q INNER JOIN GIAMSATBANHANG P ON Q.GSBH_FK = P.PK_SEQ WHERE Q.KHUVUC_FK IN ( " +
			"\n 		SELECT KHUVUC_FK FROM ASM_KHUVUC WHERE ASM_FK IN ( " +
			"\n 			SELECT ASM_FK FROM NHANVIEN WHERE PK_SEQ = "+userId+" " +
			"\n 		)  " +
			"\n 	) AND P.TRANGTHAI = 1 " +
			"\n ) AND TRANGTHAI = 1 ";
		} 
		
		
		return query;
	}
	
	/**
	 * Lấy câu sql trả về các pk_seq nhân viên cấp dưới của nhân viên này
	 * @return String result
	 */
	private String getIdNhanVienCapDuoiQuery() {
		return KeHoachNhanVienList.getIdNhanVienCapDuoiQuery(this.db, this.userId);
	}
	
	public String getSearchQuery() 
	{
		String query = "";
		String condition = "";
		
		if (loai.length() > 0) {
			condition = condition + " and nhanvien_fk in (select pk_seq from nhanvien where loai = " + loai + ") ";
		}
		
    	if (thang.length() > 0) {
    		condition = condition + " and convert(int,thang) = " + thang;
    	}
    	if (nhanvienTen.length() > 0) {
    		condition = condition + " and nhanvien like N'%" + nhanvienTen+"%'";
    	}
    	
    	if (nam.length() > 0) {
    		condition = condition + " and nam = " + nam;
    	}
    	if (kbhId.length() > 0) {
    		condition = condition + " and kbh_fk = " + kbhId;
    	}
		if(this.vungId.length() > 0)
			condition += " and vung_fk = " + this.vungId;
			//condition += " and nv.BM_FK in (select BM_FK from vung where PK_SEQ = "+this.vungId+")";
		
		if(this.khuvucId.length() > 0)
			condition += " and khuvuc_fk = " + this.khuvucId;    
		//condition += " and nv.asm_fk in (select asm_fk from khuvuc where PK_SEQ = "+this.khuvucId+")";
   
    	query = getMain_query() + condition;
		query += "\n order by loaikehoach, trangthai, nam desc, thang desc ";
    	
    	System.out.println("Search: "+query);
    	return query;
	}
	
	public void closeDB(){
		try {
			if(khnvRs != null) {
				khnvRs.close();
			}
			if(vungRs != null) {
				vungRs.close();
			}
			if(khuvucRs != null) {
				khuvucRs.close();
			}
		} catch(Exception e) { }
		if(this.db != null)
			this.db.shutDown();
	}


	public void setMsg(String Msg) {
	   this.Msg = Msg;
	}

	
	public String getMsg() {
		return this.Msg;
	}

	@Override
	public String getNhanVienId() {
		return nhanvienId;
	}

	@Override
	public void setNhanVienId(String nhanvienId) {
		this.nhanvienId = nhanvienId;
	}

	@Override
	public boolean delete(String id) 
	{
		String query = " SELECT count(*) as num from KEHOACHNV where pk_seq = " + id + " and nhanvien_fk = " + this.userId + " and trangthai = 1 ";
		System.out.println(query);
		ResultSet rs1 = db.get(query);
		try 
		{
			rs1.next();
			int count = rs1.getInt("num");
			if( count > 0) 
			{
				rs1.close();
				this.Msg = "Không thể xóa kế hoạch: Kế hoạch đã chốt hoặc không phải do bạn tạo!";
				return false;
			} 
			
			query = "\n select SUM(num.num) as num " +
			"\n from " +
			"\n ( " +
			"\n     select COUNT(*) as num from KEHOACHNV_NPP where KEHOACHNV_FK = " + id + " and THOIDIEMDEN is not null " +
			"\n     union all " +
			"\n     select COUNT(*) as num from KEHOACHNV_THITRUONG where KEHOACHNV_FK = " + id + " and THOIDIEM is not null " +
			"\n     union all " +
			"\n     select COUNT(*) as num from KEHOACHNV_TBH k inner join KEHOACHNV_TBH_KHACHHANG vt on vt.KEHOACHNV_TBH_FK = k.PK_SEQ where KEHOACHNV_FK = " + id + " " +
			"\n ) num";
			rs1 = db.get(query);
			rs1.next();
			count = rs1.getInt("num");
			if(count > 0) 
			{
				rs1.close();
				this.Msg = "Không thể xóa kế hoạch: Kế hoạch đã phát sinh dữ liệu viếng thăm";
				return false;
			} 
			else 
			{
				db.getConnection().setAutoCommit(false);
				
				query = "DELETE KEHOACHNV_NPP WHERE KEHOACHNV_FK = '" + id + "'";
				if(!db.update(query)) {
					db.getConnection().rollback();
					this.Msg = "Không thể xóa kế hoạch nhân viên 1.";
					return false;
				}
				
				query = "DELETE KEHOACHNV_THITRUONG WHERE KEHOACHNV_FK = '" + id + "'";
				if(!db.update(query)) {
					db.getConnection().rollback();
					this.Msg = "Không thể xóa kế hoạch nhân viên 2.";
					return false;
				}
				
				query = "delete from KEHOACHNV_TBH where KEHOACHNV_FK  = '" + id + "'";
				if(!db.update(query)) {
					db.getConnection().rollback();
					this.Msg = "Không thể xóa kế hoạch nhân viên 3.";
					return false;
				}
				
				query = "delete from nhanvien_kehoach_log where KEHOACHNV_FK  = '" + id + "'";
				if(!db.update(query)) {
					db.getConnection().rollback();
					this.Msg = "Không thể xóa kế hoạch nhân viên 4.";
					return false;
				}
				
				query = "delete kehoachnv where pk_seq = '" + id + "'";
				if(!db.update(query)) {
					this.Msg = "Không thể xóa kế hoạch nhân viên 5.";
					return false;
				}
				
				db.getConnection().commit();
				db.getConnection().setAutoCommit(true);
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			try 
			{
				db.getConnection().rollback();
			} 
			catch (SQLException e1) {	}
			this.Msg = "Xảy ra lỗi khi xóa kế hoạch " + id + " ("+e.getMessage()+")";
			return false;
		}
		
		return true;
	}

	@Override
	public boolean duyet(String id) {
		try{
			String ngaysua = getDateTime();
			String nguoisua = this.userId;
			String query= " select count(*) as sl from KEHOACHNV where NHANVIEN_FK = "+this.userId+" and PK_SEQ = " + id + " AND TRANGTHAI = 0  ";
			ResultSet rs=db.get(query);
			rs.next();
			int sl=rs.getInt("sl");
			if (sl>0)
			{
				query = " UPDATE KEHOACHNV SET TRANGTHAI = 1, NGUOISUA = '"+nguoisua+"', NGAYSUA = '"+ngaysua+"' WHERE PK_SEQ = " + id + " AND TRANGTHAI = 0 ";
			}
			else
			{
				query = "\n UPDATE KEHOACHNV SET TRANGTHAI = 1, NGUOISUA = '"+nguoisua+"', NGAYSUA = '"+ngaysua+"' " +
				"\n WHERE PK_SEQ = " + id + " AND TRANGTHAI = 0 AND NHANVIEN_FK IN (" + this.getIdNhanVienCapDuoiQuery() + ")";
			}
			
			System.out.println("Query duyet: "+query);
			if (db.updateReturnInt(query)<=0) {
				this.Msg = "Không thể duyệt kế hoạch " + id;
				return false;
			}
		} 
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

	@Override
	public boolean moduyet(String id) {
		String query = "";
		String ngaysua = getDateTime();
		String nguoisua = this.userId;
		
		int thang = Integer.parseInt(getDateTime("MM"));
		int nam = Integer.parseInt(getDateTime("yyyy"));
		
		if(!Utility.KiemTra_PK_SEQ_HopLe(id, "KEHOACHNV", db))
		{
			this.Msg = "Định danh không hợp lệ!";
			return false;
		}
		
		query = " SELECT THANG, NAM FROM KEHOACHNV WHERE PK_SEQ = " + id + " ";
		ResultSet rs = db.get(query);
		try {
			rs.next();
			int thangKh = rs.getInt("thang");
			int namKh = rs.getInt("nam");
			
			if (namKh < nam || (namKh == nam && thangKh < thang)) {
				this.Msg = "Chỉ được bỏ duyệt những kế hoạch có tháng lớn hơn hoặc bằng tháng hiện tại!";
				return false;
			}
			
		} catch(Exception e) {
			this.Msg = "Xảy ra lỗi khi duyệt kế hoạch " + id + "(" + e.getMessage() + ")";
			return false;
		}
		
		Object[] data = dbutils.setObject(nguoisua,ngaysua,id);
		//Cập nhật trạng thái kế hoạch nhân viên có trạng thái chưa duyệt của cấp dưới nhân viên này 
		query = "\n UPDATE KEHOACHNV SET TRANGTHAI = 0, NGUOISUA = ?, NGAYSUA = ? " +
		"\n WHERE PK_SEQ = ? AND TRANGTHAI = 1 AND NHANVIEN_FK IN (" + this.getIdNhanVienCapDuoiQuery() + ") ";
		if (db.updateQueryByPreparedStatement(query, data) != 1) {
			this.Msg = "Không thể mở duyệt kế hoạch!";
			return false;
		}
		
		return true;
	}
	
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
      
        return dateFormat.format(date);
	}
	

	
	private String getDateTime(String pattern) 
	{
		Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(date);
	}

	@Override
	public ResultSet getVungRs() {
		return this.vungRs;
	}

	@Override
	public ResultSet getKhuvucRs() {
		return this.khuvucRs;
	}

	@Override
	public String getVungId() {
		return this.vungId;
	}

	@Override
	public void setVungId(String value) {
		this.vungId = value;
	}

	@Override
	public String getKhuvucId() {
		return this.khuvucId;
	}

	@Override
	public void setKhuvucId(String value) {
		this.khuvucId = value;
	}

	public boolean Upload(String data)
	{
		try {
			Idbutils db = new dbutils();
			Utility util = new Utility();
			String error="";
			String query ="select data.ten, data.thang, data.nam from ("+data+") as data inner join nhanvien nv on data.dangnhap = nv.dangnhap "
					+ "\n where exists (select 1 from KEHOACHNV where nhanvien_fk=nv.pk_seq and thang=data.thang and nam =data.nam ) ";
			System.out.println("______query: "+query);
			ResultSet rsCheck = db.get(query);
			while(rsCheck.next())
			{
				error += "Nhân viên "+rsCheck.getString("ten")+" đã khai kế hoạch của tháng "+rsCheck.getString("thang")+"-"+rsCheck.getString("nam")+" \n ";
			}
			if(error.trim().length()>0)
			{
				this.Msg=error+" \n không có trên hệ thống. Vui lòng chỉnh sửa lại file upload!! ";
				return false;
			}
			query ="select distinct data.thang, data.nam from ("+data+") as data  ";
			System.out.println("______query: "+query);
			rsCheck = db.get(query);
			int count=0;
			while(rsCheck.next())
			{
				count ++;
			}
			if(count != 1)
			{
				this.Msg="Vui lòng chỉ upload kế hoạch cho một tháng!! ";
				return false;
			}
			
			query ="select distinct data.ten, data.dangnhap from ("+data+") as data where not exists (select 1 from nhanvien where dangnhap = data.dangnhap) ";
			System.out.println("______query: "+query);
			rsCheck = db.get(query); 
			while(rsCheck.next())
			{
				error += "Đăng nhập "+rsCheck.getString("dangnhap")+" của nhân viên "+rsCheck.getString("ten")+"  \n ";
			}
			if(count != 1)
			{
				this.Msg=error+"không có trên hệ thống. Vui lòng check lại thông tin upload";
				return false;
			}
			
			if(error.trim().length()>0)
			{
				this.Msg=error+" \n Vui lòng chỉnh sửa lại file upload!! ";
				return false;
			}
			query ="select data.ten,data.manpp, data.thang, data.nam from ("+data+") as data inner join nhanvien nv on data.dangnhap =nv.dangnhap "+ 
					"\n where not exists (select 1 from nhaphanphoi npp where npp.ma = data.manpp and npp.pK_Seq in (select npp_fk from phamvihoatdong where nhanvien_fk=nv.pk_seq ))  and data.tacvu like N'Đi NPP' ";
			System.out.println("______query: "+query);
			rsCheck = db.get(query);
			
			while(rsCheck.next())
			{
				error += "Mã nhà phân phối "+rsCheck.getString("manpp")+" của nhân viên "+rsCheck.getString("ten") +" ở kế hoạch tháng "+rsCheck.getString("thang")+"-"+rsCheck.getString("nam")+" \n ";
			}
			if(error.trim().length()>0)
			{
				this.Msg=error+" \n không tồn tại trên hệ thống hoặc không thuộc phạm vi quản lí của nhân viên. Vui lòng check lại thông tin upload  !! ";
				return false;
			}
			query ="select data.ten,data.manpp,data.maddkd,data.ngayid, data.thang, data.nam from ("+data+") as data "+ 
					"\n inner join nhanvien nv on nv.dangnhap=data.dangnhap "+ 
					"\n where not exists (select 1 from tuyenbanhang tbh inner join daidienkinhdoanh ddkd on tbh.ddkd_fk=ddkd.pk_Seq"+ 
					"\n inner join nhaphanphoi npp on npp.pk_Seq=tbh.npp_fk "+ 
					"\n where  ddkd.smartid = data.maddkd and npp.ma=data.manpp and tbh.ngayid=data.ngayid and npp.pK_Seq in (select npp_fk from phamvihoatdong where nhanvien_fk=nv.pk_seq )) and data.tacvu like N'Đi tuyến bán hàng' ";
			System.out.println("______query: "+query);
			rsCheck = db.get(query);
			while(rsCheck.next())
			{
				error += "Tuyến bán hàng ngày "+rsCheck.getString("ngayid")+" của nhân viên bán hàng "+rsCheck.getString("maddkd") +" ở kế hoạch của nhân viên "+rsCheck.getString("ten")+" tháng "+rsCheck.getString("thang")+"-"+rsCheck.getString("nam")+" \n ";
			}
			if(error.trim().length()>0)
			{
				this.Msg=error+" \n không tồn tại trên hệ thống hoặc không thuộc phạm vi quản lí của nhân viên. Vui lòng check lại thông tin upload  !! ";
				return false;
			}
			query ="select data.ten,data.manpp,data.quanhuyen_fk,data.tinhthanh_fk, data.thang, data.nam from ("+data+") as data "+ 
					"\n where not exists (select 1 from quanhuyen qh inner join tinhthanh tt on qh.tinhthanh_fk=tt.pk_seq "	+ 
					"\n where qh.pk_seq=data.quanhuyen_fk and tt.pk_Seq=data.tinhthanh_fk) and data.tacvu like N'Đi thị trường' ";
			System.out.println("______query: "+query);
			rsCheck = db.get(query);
			while(rsCheck.next())
			{
				error += "Quận huyện "+rsCheck.getString("quanhuyen_fk")+", tỉnh thành "+rsCheck.getString("tinhthanh_fk")+" ở kế hoạch của nhân viên "+rsCheck.getString("ten")+" tháng "+rsCheck.getString("thang")+"-"+rsCheck.getString("nam")+" \n ";
			}
			if(error.trim().length()>0)
			{
				this.Msg=error+" \n không tồn tại trên hệ thống. Vui lòng check lại thông tin upload  !! ";
				return false;
			}
			db.getConnection().setAutoCommit(false);
			
			query = "INSERT KEHOACHNV(NHANVIEN_FK, THANG, NAM, NGAYTAO, NGAYSUA, NGUOITAO, NGUOISUA) "+ 
					"\n select distinct nv.pk_Seq, data.thang,data.nam, '"+this.getDateTime()+"','"+this.getDateTime()+"',"+this.userId+","+this.userId+" "+ 
					"\n from ("+data+") as data inner join nhanvien nv on data.dangnhap = nv.dangnhap  ";
			System.out.println("______query: "+query);
			if(!db.update(query))
			{
				db.getConnection().rollback();
				this.Msg="KHNV01. Lỗi tạo mới kế hoạch nhân viên. Vui lòng liên hệ Admin ";
				return false;
			}
			//ke hoach npp
			query = "INSERT KEHOACHNV_NPP(KEHOACHNV_FK, NGAY, NPP_FK, GHICHU, THANG, NAM) "+ 
					"\n select kh.pk_Seq,data.ngay,npp.pk_Seq,data.ghichu, data.thang,data.nam "+ 
					"\n from ("+data+") as data inner join nhanvien nv on data.dangnhap = nv.dangnhap "+ 
					"\n inner join kehoachnv kh on kh.nhanvien_fk=nv.pk_seq and data.thang=kh.thang and data.nam = kh.nam "+ 
					"\n inner join nhaphanphoi npp on npp.ma = data.manpp where data.tacvu like N'Đi NPP' ";
			System.out.println("______query: "+query);
			if(!db.update(query))
			{
				db.getConnection().rollback();
				this.Msg="KHNVNPP. Lỗi tạo mới kế hoạch nhân viên. Vui lòng liên hệ Admin ";
				return false;
			}
			//ke hoach thi truong
			query = "INSERT KEHOACHNV_THITRUONG(KEHOACHNV_FK, NGAY, TINH_FK, QUANHUYEN_FK, GHICHU, THANG, NAM) "+ 
					"\n select kh.pk_Seq,data.ngay,tt.pk_Seq,qh.pk_Seq,data.ghichu, data.thang,data.nam "+ 
					"\n from ("+data+") as data inner join nhanvien nv on data.dangnhap = nv.dangnhap "+ 
					"\n inner join kehoachnv kh on kh.nhanvien_fk=nv.pk_seq and data.thang=kh.thang and data.nam = kh.nam "+ 
					"\n inner join tinhthanh tt on tt.pk_seq = data.tinhthanh_fk "+
					"\n inner join quanhuyen qh on qh.pk_seq = data.quanhuyen_fk where data.tacvu like N'Đi thị trường'";
			System.out.println("______query: "+query);
			if(!db.update(query))
			{
				db.getConnection().rollback();
				this.Msg="KHNVTT. Lỗi tạo mới kế hoạch nhân viên. Vui lòng liên hệ Admin ";
				return false;
			}
			//ke hoach tbh
			query = "insert into KEHOACHNV_TBH(KEHOACHNV_FK, NGAY, NPP_FK, DDKD_FK, TUYEN_FK,GHICHU, THANG, NAM) "+ 
					"\n select kh.pk_Seq,data.ngay,npp.pk_Seq,ddkd.pk_Seq,tbh.pk_seq,data.ghichu, data.thang,data.nam "+ 
					"\n from ("+data+") as data inner join nhanvien nv on data.dangnhap = nv.dangnhap "+ 
					"\n inner join kehoachnv kh on kh.nhanvien_fk=nv.pk_seq and data.thang=kh.thang and data.nam = kh.nam "+ 
					"\n inner join nhaphanphoi npp on npp.ma = data.manpp "+ 
					"\n inner join daidienkinhdoanh ddkd on ddkd.smartid = data.maddkd "+ 
					"\n inner join tuyenbanhang tbh on data.ngayid = tbh.ngayid and npp.pk_Seq=tbh.npp_fk and ddkd.pk_seq=tbh.ddkd_fk"+ 
					"\n where data.tacvu like N'Đi tuyến bán hàng' ";
			System.out.println("______query: "+query);
			if(!db.update(query))
			{
				db.getConnection().rollback();
				this.Msg="KHNVTBH. Lỗi tạo mới kế hoạch nhân viên. Vui lòng liên hệ Admin ";
				return false;
			}
			
			this.Msg="Upload thành công!!";
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return true;
		}catch (Exception e) {
			try {
				db.getConnection().rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			this.Msg="KHNVTBH. Lỗi tạo mới kế hoạch nhân viên. Vui lòng liên hệ Admin ";
			return false;
		}
	}
}


