package geso.dms.distributor.beans.kehoachnhanvien.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import geso.dms.distributor.beans.kehoachnhanvien.IKhaibaongaynghi;
import geso.dms.distributor.db.sql.dbutils;

public class Khaibaongaynghi implements IKhaibaongaynghi 
{
	String userId;
	String msg;

	ResultSet ddkdRs;
	String ddkdId; 
	String nppId;
	String[] Ngay;

	String[] DanhSachNpp;


	dbutils db;
	private ResultSet ngaynghiRs;
	private String thang;
	private String nam;
	private String id;
	private String[] ghichu;
	private String view;

	public Khaibaongaynghi()
	{
		this.id = "";
		msg = "";
		Ngay = new String[0];
		this.ghichu = new String[0];
		this.thang = "";
		this.nam = "";
		this.nppId = "";
		this.ddkdId = "";
		this.view = "";
		db = new dbutils();
	}

	public String getUserId() 
	{	
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;	
	}

	public String getMsg() 
	{
		return this.msg;
	}

	public String setMsg(String msg) 
	{
		return this.msg = msg;
	}
	
	geso.dms.distributor.util.Utility util=new geso.dms.distributor.util.Utility();
	private void getNppInfo()
	{		
		util.getUserInfo(userId, db);
		if(!util.getLoaiNv().equals("3"))
		{
			this.nppId=util.getIdNhapp(this.userId);
		}else 
		{
			
		}
	}
	

	@Override
	public void init() 
	{
		getNppInfo();
		if(this.id.length() > 0)
		{
			String query = "select * from KEHOACHNV where PK_SEQ = " + this.id;
			ResultSet rs = this.db.get(query);
			try 
			{
				if(rs != null && rs.next())
				{
					this.ddkdId = rs.getString("NHANVIEN_FK");
					this.thang = rs.getString("THANG");
					this.nam = rs.getString("NAM");
					rs.close();
				}
				query = "select * from KEHOACHNV_NGAYNGHI where KEHOACHNV_FK = '"+this.id+"'";
				String ngay = "", ghichus = "";
				rs = this.db.get(query);
				if(rs != null)
					while(rs.next()){
						ngay += rs.getString("NGAY") + ",";
						ghichus += (rs.getString("GHICHU") != null && rs.getString("GHICHU").length() > 0 ? rs.getString("GHICHU"): " ") + "__";
					}
				if(ngay.length() > 0)
					ngay = ngay.substring(0, ngay.length() - 1);
				if(ghichus.length() > 0)
					ghichus = ghichus.substring(0, ghichus.length() - 2);
				this.Ngay = ngay.split(",");
				this.ghichu = ghichus.split("__");
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		createRs();
	}

	@Override
	public void createRs() 
	{
		String query = "select * from DAIDIENKINHDOANH a WHERE 1=1 ";
		if(!this.view.equals("TT"))
		{
			if(nppId.length()>0)
				query+=" and pk_seq in ( select ddkd_fk from DAIDIENKINHDOANH_NPP where npp_fk = '" + this.nppId + "' ) ";
			
			String quyen = util.getDDKD_ASM(this.userId);
			
			if(quyen.length() > 0)
				query += " and a.PK_SEQ IN " + quyen;
			
		}else  query = "select * from DAIDIENKINHDOANH a WHERE 1=1 ";
		query +=
				" order by ten "; 
		
		System.out.println(query);
		this.ddkdRs = this.db.get(query);
	}

	@Override
	public void DBclose() {
		this.db.shutDown();
	}

	@Override
	public void SetNgayNghi(String[] Ngay) {
		this.Ngay = Ngay;
	}

	@Override
	public String[] GetNgayNghi() {
		return this.Ngay;
	}

	@Override
	public ResultSet getDDKDRs() {
		return this.ddkdRs;
	}

	@Override
	public ResultSet getNgaynghiRs() {
		return this.ngaynghiRs;
	}

	@Override
	public String getDdkdId() {
		return this.ddkdId;
	}

	@Override
	public void setDdkdId(String value) {
		this.ddkdId = value;
	}

	@Override
	public String getNppId() {
		return this.nppId;
	}

	@Override
	public void setNppId(String value) {
		this.nppId = value;
	}

	@Override
	public String getThang() {
		return this.thang;
	}

	@Override
	public void setThang(String thang) {
		this.thang = thang;
	}

	@Override
	public String getNam() {
		return this.nam;
	}

	@Override
	public void setNam(String nam) {
		this.nam = nam;
	}

	@Override
	public boolean CreateNew() {
		try {
			this.db.getConnection().setAutoCommit(false);

			//Insert kehoachnv
			String query = " INSERT KEHOACHNV(NHANVIEN_FK, THANG, NAM, NGUOITAO, NGAYTAO, NGAYSUA , NGUOISUA, LOAI) " +
					" SELECT '" + this.ddkdId + "', '" + thang + "', '" + this.nam + "', '" + this.userId + "', '" + getDateTime() + "', '" + getDateTime() + "', " + this.userId + ", 1";
			if (!db.update(query)) {
				this.msg = "Không thể thêm mới kế hoạch tháng " + thang + " năm " + nam+ ". (" + query + ")";
				db.getConnection().rollback();
				return false;
			}

			//Lấy id vừa tạo
			query = " SELECT IDENT_CURRENT('KEHOACHNV') AS ID";
			ResultSet rs = this.db.get(query);
			if(rs != null) {
				try {
					rs.next();
					this.id = rs.getString("ID");
					rs.close();
				} catch(Exception e) {
					this.msg = "Xảy ra lỗi khi lấy thông tin mã kế hoạch vừa tạo (" + e.getMessage() + ")";
					db.getConnection().rollback();
					return false;
				}
			} else {
				this.msg = "Không thể lấy mã kế hoạch vừa tạo !";
				db.getConnection().rollback();
				return false;
			}
			query = "";
			for(int i=0;i<this.Ngay.length; i++)
				if(Ngay[i].trim().length() != 0){
					query += " select '" + Ngay[i].trim() + "' as ngay, N'" + this.ghichu[i].trim() + "' as ghichu union all \n";
				}
			if(query.length() > 0){
				query = query.substring(0, query.length() - 11);
				String sql = "insert into KEHOACHNV_NGAYNGHI(KEHOACHNV_FK, DDKD_FK, NGAY, GHICHU)"+
						"select '"+this.id+"', '"+this.ddkdId+"', d.ngay, d.ghichu from ("+query+") d";
				if (!db.update(sql)) {
					this.msg = "Không thể thêm mới ngày nghỉ. (" + query + ")";
					db.getConnection().rollback();
					return false;
				}
			}
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);

		} catch (SQLException e) {
			e.printStackTrace();
			this.msg = "Xảy ra lỗi khi cập nhật kế hoạch nhân viên (" + e.getMessage() + ")";
			try {
				db.getConnection().rollback();
			} catch (Exception ex) { }
			return false;
		}
		return true;
	}

	@Override
	public boolean Update() {
		try {

			this.db.getConnection().setAutoCommit(false);

			//Insert kehoachnv
			String query = " update KEHOACHNV " +
					" SET THANG = '" + thang + "', NAM = '" + this.nam + "', NGUOISUA = '" + this.userId + "', NGAYSUA = '" + getDateTime() + "' WHERE PK_SEQ = " + this.id;
			if (!db.update(query)) {
				this.msg = "Không thể cập nhật kế hoạch tháng " + thang + " năm " + nam+ ". (" + query + ")";
				db.getConnection().rollback();
				return false;
			}

			query = " DELETE FROM KEHOACHNV_NGAYNGHI WHERE KEHOACHNV_FK = " + this.id;
			if(!this.db.update(query)) {
				this.msg = "Xảy ra lỗi khi cập nhật (" + query + ")";
				db.getConnection().rollback();
				return false;
			}

			query = "";
			for(int i=0;i<this.Ngay.length; i++)
				if(Ngay[i].trim().length() != 0){
					query += " select '" + Ngay[i].trim() + "' as ngay, N'" + this.ghichu[i].trim() + "' as ghichu union all \n";
				}
			if(query.length() > 0){
				query = query.substring(0, query.length() - 11);
				String sql = "insert into KEHOACHNV_NGAYNGHI(KEHOACHNV_FK, DDKD_FK, NGAY, GHICHU)"+
						"select '"+this.id+"', '"+this.ddkdId+"', d.ngay, d.ghichu from ("+query+") d";
				if (!db.update(sql)) {
					this.msg = "Không thể thêm mới ngày nghỉ. (" + query + ")";
					db.getConnection().rollback();
					return false;
				}
			}
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);

		} catch (SQLException e) {
			e.printStackTrace();
			this.msg = "Xảy ra lỗi khi cập nhật kế hoạch nhân viên (" + e.getMessage() + ")";
			try {
				db.getConnection().rollback();
			} catch (Exception ex) { }
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

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public void SetGhichu(String[] ghichu) {
		this.ghichu = ghichu;
	}

	@Override
	public String[] GetGhichu() {
		return this.ghichu;
	}

	@Override
	public void setView(String view) {
		this.view = view;
	}

	@Override
	public String getView() {
		return this.view;
	}	
}
