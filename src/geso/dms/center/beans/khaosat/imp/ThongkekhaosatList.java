package geso.dms.center.beans.khaosat.imp;

import geso.dms.center.beans.khaosat.IThongkekhaosatList;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ThongkekhaosatList implements IThongkekhaosatList
{
	String userId;
	String tennguoitraloi;
	String dienthoai;
	String msg;
	
	ResultSet KhaosatRs;
	String khaosatId;
	
	
	String tungay ="";
	String denngay ="";
	String doituong ="TDV";
	ResultSet kqKhaosatRs;
	
	dbutils db;
	
	public ThongkekhaosatList()
	{
		this.userId = "";

		this.tennguoitraloi = "";
		this.dienthoai = "";
		this.khaosatId = "";
		this.tungay = Utility.getNgayHienTai();
		this.denngay = Utility.getNgayHienTai();
		this.msg = "";
		this.doituong ="TDV";
		this.db = new dbutils();
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

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}

	public void init(String query) 
	{
		String sql = "";
		
		if(query.length() > 0)
			sql = query;
		else
		{	
			sql = "select a.pk_seq, a.tieude, a.diengiai, a.bophan, a.socauhoi, a.trangthai, b.ten as nguoitao, a.ngaytao, c.ten as nguoisua, a.ngaysua    " +
				  "from KHAOSAT a inner join NhanVien b on a.nguoitao = b.pk_seq    " +
				  		"inner join nhanvien c on a.nguoisua = c.pk_seq  " +
				  "order by a.pk_seq desc";
		}
		
		System.out.println("__Khao sat : " + sql);
		this.KhaosatRs = db.get(sql);
	}

	public void DbClose() 
	{
		try 
		{
			if(this.KhaosatRs != null)
				this.KhaosatRs.close();
			this.db.shutDown();
		} 
		catch (SQLException e) {}
	}

	public ResultSet getKhaosatRs() 
	{
		return this.KhaosatRs;
	}

	public void setKhaosatRs(ResultSet KhaosatRs) 
	{
		this.KhaosatRs = KhaosatRs;
	}

	
	public String getKhaosatId() {
		
		return this.khaosatId;
	}

	
	public void setKhaosatId(String khaosatId) {
		
		this.khaosatId = khaosatId;
	}

	
	public String getTennguoitraloi() {
		
		return this.tennguoitraloi;
	}

	
	public void setTennguoitraloi(String tennguoitraloi) {
		
		this.tennguoitraloi = tennguoitraloi;
	}

	
	public String getSodienthoai() {
		
		return this.dienthoai;
	}

	
	public void setSodienthoai(String sodienthoai) {
		
		this.dienthoai = sodienthoai;
	}

	
	public ResultSet getKetquaKsRs() {
		
		return this.kqKhaosatRs;
	}

	
	public void setKetquaKsRs(ResultSet ketquaKsRs) {
		
		this.kqKhaosatRs = ketquaKsRs;
	}


	public void createRs() 
	{
		this.KhaosatRs = db.get("select pk_seq, tieude from KHAOSAT where trangthai = '1'  and  doituong ='" + this.doituong+"'");
		
		if(this.khaosatId.trim().length() > 0)
		{
			String query = "select distinct isnull(a.tennguoithuchien, b.TEN) as nguoithuchien, a.ten, a.sodienthoai, case when a.gioitinh = 0 then N'Nữ' else N'Nam' end as gioitinh,  " +
								"a.diachi, a.tuoi, a.doituong, a.muctieu, a.thunhap  " +
						   "from KHAOSAT_CAUHOI_THUCHIEN a left join NHANVIEN b on a.NHANVIEN_FK = b.PK_SEQ  " +
						   "where khaosat_cauhoi_fk in ( select pk_seq from KHAOSAT_CAUHOI where KHAOSAT_FK = '" + this.khaosatId + "' )";
			
			this.kqKhaosatRs = db.get(query);
			
		}
		
	}
	
	public String getTungay() {
		return tungay;
	}
	public void setTungay(String tungay) {
		this.tungay = tungay;
	}
	public String getDenngay() {
		return denngay;
	}
	public void setDenngay(String denngay) {
		this.denngay = denngay;
	}
	public String getDoituong() {
		return doituong;
	}
	
	
	public void setDoituong(String doituong) {
		this.doituong = doituong;
	}
	

	

}
