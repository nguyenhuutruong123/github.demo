package geso.dms.center.beans.nhomnhanvien.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import geso.dms.center.beans.nhomnhanvien.INhomNhanVien;
import geso.dms.center.db.sql.dbutils;

public class NhomNhanVien implements INhomNhanVien
{
	String dvdlId,id, userId, ma, ten, loainhom, msg,nganhHangId,nhanHangId;
	ResultSet nganhHangRs,nhanhangRs,dvdlRs;
	String loainv = "";
	String loainvId = "";
	ResultSet LoaiNV;
	String nvselected;
	ResultSet kenhbanhang;
	String kbhId;
	
	public NhomNhanVien(String id)
	{
		super();
		this.id = id;
		this.userId = "";
		this.ma = "";
		this.ten = "";
		this.loainhom = "";
		this.msg = "";
		this.spId="";
		this.loainvId = "";
		this.nvselected = "";
		this.kbhId = "";
		this.db = new dbutils();
	}

	public NhomNhanVien()
	{
		this.id = "";
		this.ma = "";
		this.ten = "";
		this.userId = "";
		this.loainhom = "2";
		this.msg = "";
		this.spId="";
		this.loainvId = "";
		this.nvselected = "";
		this.kbhId = "";
		this.db = new dbutils();
	}
	public ResultSet getKenhbanhang() {
		return kenhbanhang;
	}
	public void setKenhbanhang(ResultSet kenhbanhang) {
		this.kenhbanhang = kenhbanhang;
	}
	public String getKbhId() {
		return kbhId;
	}
	public void setKbhId(String kbhId) {
		this.kbhId = kbhId;
	}
	public String getNvselected() {
		return nvselected;
	}
	public void setNvselected(String nvselected) {
		this.nvselected = nvselected;
	}

	public String getLoainvId() {
		return loainvId;
	}

	public void setLoainvId(String loainvId) {
		this.loainvId = loainvId;
	}

	public ResultSet getLoaiNV() {
		return LoaiNV;
	}

	public void setLoaiNV(ResultSet loaiNV) {
		LoaiNV = loaiNV;
	}

	public String getDvdlId() {
  	return dvdlId;
  }

	public void setDvdlId(String dvdlId) {
		this.dvdlId = dvdlId;
	}
	public ResultSet getDvdlRs() {
  	return dvdlRs;
  }

	public void setDvdlRs(ResultSet dvdlRs) {
		this.dvdlRs = dvdlRs;
	}

	public String getNganhHangId() {
		return nganhHangId;
	}

	public void setNganhHangId(String nganhHangId) {
		this.nganhHangId = nganhHangId;
	}

	dbutils db;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMa() {
		return ma;
	}

	public void setMa(String ma) {
		this.ma = ma;
	}
	public String getTen() {
		return ten;
	}
	public void setTen(String ten) {
		this.ten = ten;
	}
	public String getMsg() {

		return this.msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public boolean save() {
		String query=
				"insert into NhomNhanVien(ten,nguoitao,ngaytao,nguoisua,ngaysua,manhanvien,loai)" +
				" select N'"+this.ten+"','"+this.userId+"','"+this.getDateTime()+"','"+this.userId+"','"+this.getDateTime()+"','"+this.spId+"','"+this.loainvId+"'";
		try {
			this.db.getConnection().setAutoCommit(false);
			if(!this.db.update(query)) {
				this.msg="1.1 Lỗi hệ thống "+query;
				this.db.getConnection().rollback();
				return false;
			}
			query = "select SCOPE_IDENTITY() as nhomnvId";
		 	ResultSet rs = this.db.get(query);
			rs.next();
			this.id = rs.getString("nhomnvId");
			query=
					"insert into NhomNhanVien_ddkd(ten,nguoitao,ngaytao,nguoisua,ngaysua,manhanvien,loai,nhomnv_fk)" +
					" select N'"+this.ten+"','"+this.userId+"','"+this.getDateTime()+"','"+this.userId+"','"+this.getDateTime()+"','"+this.nvselected+"','4', '"+this.id+"'";
			if(!this.db.update(query)) {
				this.msg="1.2 Lỗi hệ thống "+query;
				this.db.getConnection().rollback();
				return false;
			}
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			if(db!=null)db.shutDown();
		}
		return true;
	}
	public boolean edit() {
		String query="update NhomNhanVien  set Ten=N'"+this.ten+"',NguoiSua='"+this.userId+"',NgaySua='"+getDateTime()+"', manhanvien ='"+this.spId+"',loai = '"+this.loainvId+"' where pk_Seq='"+this.id+"'" ;
		try {
			this.db.getConnection().setAutoCommit(false);
			if(!this.db.update(query))
			{
				this.msg="Lỗi hệ thống "+query;
				this.db.getConnection().rollback();
				return false;
			}
			query = "delete from NhomNhanVien_ddkd where nhomnv_fk = '"+this.id+"' ";
			if(!this.db.update(query))
			{
				this.msg="1.3 Lỗi hệ thống "+query;
				this.db.getConnection().rollback();
				return false;
			}
			
			query= " insert into NhomNhanVien_ddkd(ten,nguoitao,ngaytao,nguoisua,ngaysua,manhanvien,loai,nhomnv_fk)" +
					" select N'"+this.ten+"','"+this.userId+"','"+this.getDateTime()+"','"+this.userId+"','"+this.getDateTime()+"','"+this.nvselected+"','4', '"+this.id+"'";
			if(!this.db.update(query))
			{
				this.msg="1.2 Lỗi hệ thống "+query;
				this.db.getConnection().rollback();
				return false;
			}
			
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
 
	public void init()
	{
		String query="select pk_seq,ten,loai,manhanvien from NhomNhanVien where pk_seq='"+this.id+"'";
		ResultSet rs=this.db.get(query);
		try {
			while(rs.next()) {
				this.loainv=rs.getString("loai");
				this.spId = rs.getString("manhanvien");
				this.ten=rs.getString("ten");
				this.loainvId =  rs.getString("loai");
			}rs.close();
			
		query = "select pk_seq,ten,isnull(loai,'') as loai,manhanvien,nhomnv_fk from NhomNhanVien_ddkd where nhomnv_fk = '"+this.id+"' "	;
		rs = this.db.get(query);
		if(rs.next()){
			this.nvselected = rs.getString("manhanvien");
			this.loainvId = rs.getString("loai");
		}
			createRs();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}

	public void createRs()
	{
		String query = "";
//		query = "select distinct nhanvien.pk_seq, nhanvien.dangnhap, nhanvien.ten, nhanvien.dienthoai " +
//				"from nhanvien  " +
//		  		" where nhanvien.trangthai = '1' ";
//		if(this.loainv.length() > 0 &&  !this.loainv.equals("0") ) {
//			if(this.loainv.equals("6")){ // nhân viên npp
//				query += "and phanloai = '1' and pk_seq not in (100004,100005)";
//			}
//			else if(this.loainv.equals("7")){ // nhân viên trung tâm
//					query += "and phanloai = '2'  ";
//					query += " union select distinct nhanvien.pk_seq, nhanvien.dangnhap, nhanvien.ten, nhanvien.dienthoai " +
//							"from nhanvien  " +
//					  		" where nhanvien.trangthai = '1' and pk_seq in (100004,100005)";
//				}
//				else
//					query += "and loai = '"+this.loainv+"'";
			//loi loai nhan vien =4, khong co table daidienkinhdoanh_npp
			//sua lai ngay 25/11/2019 lay ddkd trangthai=1
//			if(this.loainv.equals("4")) {
//				query = "select  distinct  a.PK_SEQ, a.MANHANVIEN as dangnhap, a.TEN, isnull(a.DIENTHOAI, '') as dienthoai  " +
//						"from DAIDIENKINHDOANH a inner join daidienkinhdoanh_npp ddkd on a.pk_seq = ddkd.ddkd_fk  " +
//						"inner join NHAPHANPHOI b on ddkd.NPP_FK = b.PK_SEQ  "+ 
//						"where a.TRANGTHAI = '1'";
//				query = " union select select  distinct  a.PK_SEQ, '' as dangnhap, a.TEN, isnull(a.DIENTHOAI, '') as dienthoai "+
//						"\n from DAIDIENKINHDOANH where trangthai='1' ";
//			}
			
//		}
		int coUnion = 0;
		query = "\n select pk_seq, dangnhap, ten,DIENTHOAI, loai from ( ";
				if(!this.loainvId.equals("4") && this.loainvId.trim().length() >0){
					coUnion =1;
					query+=	"\n select distinct nv.pk_seq, nv.dangnhap, nv.ten, nv.dienthoai, nv.LOAI  from nhanvien nv   "+
						"\n where nv.trangthai = '1' and nv.loai = '"+this.loainvId+"' ";
				}  
				if(this.loainvId.trim().length()<=0){
					coUnion =1;
					query+=	"\n select distinct nv.pk_seq, nv.dangnhap, nv.ten, nv.dienthoai, LOAI  from nhanvien nv   "+
							"\n where nv.trangthai = '1'  ";
				}
				if(coUnion >= 1){
					if(this.kbhId.trim().length()>0){
						if(this.loainvId.equals("1")|| this.loainvId.equals("2") || this.loainvId.equals("3")){
							query += "\n and (isnull(nv.loai,0) not in (1,2,3) "+
							"\n or(	 isnull(nv.loai,0) = 1 and  ( select top 1 kbh_fk from bm where pk_seq = nv.bm_fk )= '"+this.kbhId+"') "+
							"\n or(	 isnull(nv.loai,0) = 2 and  ( select top 1 kbh_fk from asm where pk_seq = nv.asm_fk )= '"+this.kbhId+"')  "+
							"\n or(	 isnull(nv.loai,0) = 3 and  ( select top 1 kbh_fk from giamsatbanhang where pk_seq = nv.gsbh_fk )  = '"+this.kbhId+"')) ";
						}
					}
				}
				if (this.spId.trim().length()>0 ) {
					 if(coUnion ==1) query += " union ";
					query+=	"\n select distinct nv.pk_seq, nv.dangnhap, nv.ten, nv.dienthoai, LOAI  from nhanvien nv  "+
							"\n where nv.trangthai = '1' and pk_seq in ("+this.spId+") ";
					
					coUnion =1;
				}
				int coddkd = 0;
				if(this.loainvId.equals("4") && this.loainvId.trim().length() >0){
					if(coUnion==1) {
					query +="\n union ";
				}
					query +="\n  select distinct pk_seq, '' dangnhap, ten, DIENTHOAI, 4 as loai from DAIDIENKINHDOANH ddkd where TRANGTHAI = 1  ";
					coddkd=1;
				}  
				
				if(this.loainvId.trim().length()<=0){
					query +="\n union select distinct pk_seq, '' dangnhap, ten, DIENTHOAI, 4 as loai from DAIDIENKINHDOANH ddkd where TRANGTHAI = 1  ";
					coddkd=1;
				}
				if(this.kbhId.trim().length()>0 && coddkd>=1){
					if(this.loainvId.equals("4")||this.nvselected.trim().length()>0){
						query += "\n and  exists  ( select 1 from ddkd_gsbh x where x.DDKD_FK = ddkd.pk_seq and GSBH_FK in (select pk_seq from GIAMSATBANHANG where KBH_FK = '"+this.kbhId+"'  ))";
					}
				}
				if(this.nvselected.trim().length()>0) {
					query +="\n union select distinct pk_seq, '' dangnhap, ten, DIENTHOAI, 4 as loai from DAIDIENKINHDOANH ddkd where TRANGTHAI = 1 and pk_seq in ("+this.nvselected+")  ";
				}
				query += "\n ) a " ;
		
		System.out.println("List Nhân viên đăng nhập: "+query);
		this.spRs=this.db.get(query);
		query = "select loai, ten from loainhanvien";
		this.LoaiNV = this.db.get(query);
		query = "select pk_seq, diengiai from kenhbanhang where trangthai= '1'";
		this.kenhbanhang = this.db.get(query);

	}
	private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	public String getLoainhom() {
		return loainhom;
	}
	public void setLoainhom(String loainhom) {
		this.loainhom = loainhom;
	}
	public String getNhanHangId(){
		return nhanHangId;
	}
	public void setNhanHangId(String nhanHangId) {
		this.nhanHangId = nhanHangId;
	}
	public ResultSet getNganhHangRs() {
		return nganhHangRs;
	}
	public void setNganhHangRs(ResultSet nganhHangRs) {
		this.nganhHangRs = nganhHangRs;
	}
	public ResultSet getNhanhangRs() {
		return nhanhangRs;
	}
	public void setNhanhangRs(ResultSet nhanhangRs) {
		this.nhanhangRs = nhanhangRs;
	}
	String spId;
	public String getSpId() {
		return spId;
	}

	public void setSpId(String spId) {
		this.spId = spId;
	}
	ResultSet spRs;
	public ResultSet getSpRs() {
		return spRs;
	}

	public void setSpRs(ResultSet spRs) {
		this.spRs = spRs;
	}
	public String getLoaiNv() {
		return this.loainv;
	}
	public void setLoaiNv(String LoaiNv) {
		this.loainv = LoaiNv;
	}
	@Override
	public void DBclose(){
		try {
			if(this.spRs != null) this.spRs.close();
			if(this.LoaiNV != null) this.LoaiNV.close();
			if(this.kenhbanhang != null) this.kenhbanhang.close();
			if(!(this.db == null)){
				this.db.shutDown();
			}
		} catch (Exception e)  {
			e.printStackTrace();
		}
	}
	
}
