package geso.dms.distributor.beans.kehoachnhanvien.imp;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

import geso.dms.center.util.Phan_Trang;
import geso.dms.distributor.beans.kehoachnhanvien.IKhaibaongaynghiList;
import geso.dms.distributor.db.sql.dbutils;
import geso.dms.distributor.util.Utility;

public class KhaibaongaynghiList extends Phan_Trang implements IKhaibaongaynghiList, Serializable
{
	private static final long serialVersionUID = -9217977546733610214L;
	String userId;
	String msg;
	String nppId;

	ResultSet ngaynghiRs;


	dbutils db;
	private String loaiNV;
	private String phanloai;
	private String view;
	String thang = "";
	String trangthai = "";
	ResultSet AsmRs;
	ResultSet DDkdRs;
	String asmid = "";
	String DDkdid = "";
	Utility util;
	public KhaibaongaynghiList()
	{
		this.phanloai = "";
		this.loaiNV = "";
		msg = "";
		this.view = "NPP";
		this.nppId="";
		util = new Utility();
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

	
	public void init() 
	{
		getNppInfo();
		String loai = "";
		String query = "SELECT PHANLOAI, LOAI FROM NHANVIEN WHERE PK_SEQ = " + this.userId;
		ResultSet rs = this.db.get(query);
		try {
			rs.next();
			this.phanloai = rs.getString("PHANLOAI");
			loai = rs.getString("LOAI");
			if(loai == null)
				loai = "-1";
			this.loaiNV = loai;
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
		System.out.println("[init]"+query);
		query = "";
		if(phanloai.equals("1")) // npp
		{
			query = "\n SELECT kh.PK_SEQ, kh.THANG, kh.NAM, d.TEN, kh.TRANGTHAI, nv1.TEN AS NGUOITAO, nv2.TEN AS NGUOISUA, kh.NGAYTAO, kh.NGAYSUA " +
					"\n FROM KEHOACHNV " +
					"\n kh inner join DAIDIENKINHDOANH d on kh.NHANVIEN_FK = d.PK_SEQ " +
					"\n inner join NHANVIEN nv1 on nv1.PK_SEQ = kh.NGUOITAO " +
					"\n inner join NHANVIEN nv2 on nv2.PK_SEQ = kh.NGUOISUA " +
					"\n where kh.LOAI = 1";
			if(nppId != null && nppId.length()>0)
				query+=" and d.PK_SEQ in (select ddkd_fk from DAIDIENKINHDOANH_NPP where NPP_FK = '"+this.nppId+"')   ";

			String quyen = util.getDDKD_ASM(this.userId);

			if(quyen.length() > 0)
				query += " and d.PK_SEQ IN " + quyen;
		}
		else if(util.getLoaiNv().equals("1")){ // bm
			query = "\n SELECT kh.PK_SEQ, kh.THANG, kh.NAM, d.TEN, kh.TRANGTHAI, nv1.TEN AS NGUOITAO, nv2.TEN AS NGUOISUA, kh.NGAYTAO, kh.NGAYSUA " +
					"\n FROM KEHOACHNV " +
					"\n kh inner join DAIDIENKINHDOANH d on kh.NHANVIEN_FK = d.PK_SEQ " +
					"\n inner join NHANVIEN nv1 on nv1.PK_SEQ = kh.NGUOITAO " +
					"\n inner join NHANVIEN nv2 on nv2.PK_SEQ = kh.NGUOISUA " +
					"\n where kh.LOAI = 1 AND d.DIABAN_FK in ( " +
					"	select PK_SEQ from DIABAN where KHUVUC_FK in ( " +
					"		select PK_SEQ from KHUVUC where VUNG_FK IN ( " +
					"			SELECT VUNG_FK FROM BM_CHINHANH WHERE BM_FK = ( " +
					"				SELECT BM_FK FROM NHANVIEN WHERE PK_SEQ = '"+this.userId+"')))) ";
			
		}
		else if(util.getLoaiNv().equals("3")){
			query = "\n SELECT kh.PK_SEQ, kh.THANG, kh.NAM, d.TEN, kh.TRANGTHAI, nv1.TEN AS NGUOITAO, nv2.TEN AS NGUOISUA, kh.NGAYTAO, kh.NGAYSUA " +
			"\n FROM KEHOACHNV " +
			"\n kh inner join DAIDIENKINHDOANH d on kh.NHANVIEN_FK = d.PK_SEQ " +
			"\n inner join NHANVIEN nv1 on nv1.PK_SEQ = kh.NGUOITAO " +
			"\n inner join NHANVIEN nv2 on nv2.PK_SEQ = kh.NGUOISUA " +
			"\n where kh.LOAI = 1 AND d.PK_SEQ in " + util.getDDKD_ASM(this.userId);
		}
		else{
			query = "\n SELECT kh.PK_SEQ, kh.THANG, kh.NAM, d.TEN, kh.TRANGTHAI, nv1.TEN AS NGUOITAO, nv2.TEN AS NGUOISUA, kh.NGAYTAO, kh.NGAYSUA " +
			"\n FROM KEHOACHNV " +
			"\n kh inner join DAIDIENKINHDOANH d on kh.NHANVIEN_FK = d.PK_SEQ " +
			"\n inner join NHANVIEN nv1 on nv1.PK_SEQ = kh.NGUOITAO " +
			"\n inner join NHANVIEN nv2 on nv2.PK_SEQ = kh.NGUOISUA " +
			"\n where kh.LOAI = 1";
			this.view = "TT";
		}
		if(this.thang.length() > 0)
		{
			String thang = this.thang;
			if(this.thang.length() < 2)
				 thang = "0"+this.thang;
			query+= " and kh.thang = '"+thang+"'";
		}
		if(this.asmid.length() > 0)
		{
			query+= " and kh.nguoitao in (select pk_seq from nhanvien where gsbh_fk ='"+this.asmid+"' )";
		}
		if(this.DDkdid.length() > 0)
		{
			query+= " and d.Pk_seq ='"+this.DDkdid+"' ";
		}
	///	query += " order by  kh.TRANGTHAI,kh.ngaytao desc";
		System.out.println("[init] "+query);
		if(query.length() > 0)
			this.ngaynghiRs = super.createSplittingData(super.getItems(), super.getSplittings(), " TRANGTHAI,ngaytao desc", query);
			
		createRs();
	}

	
	public void createRs() {

		String query = " select PK_SEQ,TEN,maFAST from GIAMSATBANHANG where TRANGTHAI = '1' ";
		this.AsmRs = db.get(query);
		query = " select PK_SEQ,TEN,maFAST from daidienkinhdoanh where TRANGTHAI = '1' ";
		this.DDkdRs = db.get(query);
	}

	
	public void DBclose() {
		this.db.shutDown();
	}

	
	public ResultSet getNgaynghiRs() {
		return this.ngaynghiRs;
	}

	
	public String getLoaiNv() {
		return this.loaiNV;
	}

	
	public String getPhanloaiNv() {
		return this.phanloai;
	}

	
	public String getView() {
		return this.view;
	}

	
	public void setView(String view) {
		this.view = view;
	}

	
	public String getThang() {
		
		return this.thang;
	}

	
	public void setThang(String Thang) {
		
		this.thang = Thang;
	}

	
	public String getTrangThai() {
		
		return this.thang;
	}

	
	public void setTrangThai(String TrangThai) {
		
		this.trangthai = TrangThai;
	}

	
	public String getAsmId() {
		
		return this.asmid;
	}

	
	public void setAsmId(String AsmId) {
		
		this.asmid = AsmId;
	}

	
	public ResultSet getAsmRs() {
		
		return this.AsmRs;
	}

	
	public String getDdkdId() {
		
		return this.DDkdid;
	}

	
	public void setDdkdId(String DdkdId) {
		
		this.DDkdid = DdkdId;
	}

	
	public ResultSet getDDkdRs() {
		
		return this.DDkdRs;
	}

}
