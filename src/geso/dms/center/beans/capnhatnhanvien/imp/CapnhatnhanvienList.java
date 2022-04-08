package geso.dms.center.beans.capnhatnhanvien.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import geso.dms.center.beans.capnhatnhanvien.ICapnhatnhanvienList;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Phan_Trang;
import geso.dms.center.util.Utility;

public class CapnhatnhanvienList extends Phan_Trang implements ICapnhatnhanvienList {
	List<Object> dataSearch = new  ArrayList<Object>();
	String Ten;
	String pk_seq;
	String Ngaysinh;
	String username_email;
	String Tungay;
	String Denngay;
	ResultSet DSNV;
	ResultSet loainv;
	String userId;
	String Trangthai;
	String Phanloai;
	String msg;
	String loainhanvien_fk = "";
	ResultSet loainhanvienRs;
	dbutils db;
	
	public CapnhatnhanvienList()
	{   
		this.Ten = "";
		this.pk_seq = "";
		this.Ngaysinh ="";
		this.Tungay = "";
		this.Denngay ="";
		this.Trangthai="";
		this.Phanloai ="";
		this.username_email = "";
		this.msg ="";
		db = new dbutils();
	}
	
	public String getLoainhanvien_fk() {
		return loainhanvien_fk;
	}
	public void setLoainhanvien_fk(String loainhanvien_fk) {
		this.loainhanvien_fk = loainhanvien_fk;
	}
	public ResultSet getLoainhanvienRs() {
		String query = "select loai, ten from loainhanvien";
		return db.get(query);
		//return loainhanvienRs;
	}
	public void setLoainhanvienRs(ResultSet loainhanvienRs) {
		this.loainhanvienRs = loainhanvienRs;
	}
	public String getPk_seq() {
		return pk_seq;
	}
	public void setPk_seq(String pk_seq) {
		this.pk_seq = pk_seq;
	}
	public String getUsername_email() {
		return username_email;
	}
	public void setUsername_email(String username_email) {
		this.username_email = username_email;
	}

	public void setTen(String Ten) {
		this.Ten = Ten;
	}

	public String getTen() {
		return this.Ten;
	}

	public void setNgaysinh(String Ngaysinh) {
		this.Ngaysinh = Ngaysinh;

	}

	public String getNgaysinh() {

		return this.Ngaysinh;
	}

	public void setTungay(String Tungay) {
		this.Tungay = Tungay;

	}

	public String getTungay() {

		return this.Tungay;
	}


	public void setDenngay(String Denngay) {

		this.Denngay = Denngay;
	}


	public String getDenngay() {

		return this.Denngay;
	}

	public ResultSet getDSNV() {

		return this.DSNV;
	}
	public ResultSet getLoainv() {

		return this.loainv;
	}
	public void CreateLoaiNV(){
		String query = "select PK_SEQ, loai from NHANVIEN where trangthai = 1 ";
		this.loainv = this.db.get(query);
	}

	public void init(String st) {

		String sql="";
		System.out.println("sssssssssss");
		if(st.length()>0)
		{
			sql = "\n select a.loai, a.pk_seq,a.Ten,a.dangnhap, isnull(a.email,'') email, isnull(a.dienthoai,'') dienthoai," +
			"\n a.trangthai,a.phanloai,b.ten as nguoitao1,c.ten as nguoisua1,a.ngaytao,a.ngaysua " +
			"\n from nhanvien a inner join nhanvien b on b.pk_seq = a.nguoitao " +
			"\n inner join nhanvien c on c.pk_seq = a.nguoisua where 1=1 "+ st;
		}
		else
			sql ="select a.loai, a.pk_seq,a.Ten,a.dangnhap,isnull(a.email,'') email, isnull(a.dienthoai,'') dienthoai," +
			"\n a.trangthai,a.phanloai,b.ten as nguoitao1,c.ten as nguoisua1,a.ngaytao,a.ngaysua " +
			"\n from nhanvien a inner join nhanvien b on b.pk_seq = a.nguoitao " +
			"\n inner join nhanvien c on c.pk_seq = a.nguoisua ";
		// sql = "select a.pk_seq,a.Ten,a.dangnhap,a.email,a.dienthoai,a.trangthai,a.phanloai,b.ten as nguoitao1,c.ten as nguoisua1,a.ngaytao,a.ngaysua from nhanvien a,nhanvien b,nhanvien c where a.pk_seq = b.pk_seq and a.pk_seq = c.pk_seq and a.trangthai ='1' or a.trangthai ='0'";


		System.out.print("chuoi: "+ sql);
		this.DSNV = db.getByPreparedStatement(sql, dataSearch);
	}

	public void setuserId(String userId) {
		this.userId = userId;
	}

	public String getuserId() {
		return this.userId;
	}

	public void setTrangthai(String Trangthai) {
		this.Trangthai = Trangthai;

	}
	public String getTrangthai() {

		return this.Trangthai;
	}
	public void setPhanloai(String Phanloai) {
		this.Phanloai = Phanloai;
	}

	public String getPhanloai() {

		return this.Phanloai;
	}

	public boolean xoa(String Id) {
		String sql ="select count(*) as num from donhang where nguoitao ='"+ Id +"' or  nguoisua ='"+ Id +"'";
		ResultSet rs = db.get(sql);
		try {
			rs.next();
			if(!rs.getString("num").equals("0"))
			{
				this.msg ="khong the xoa duoc ";
				return false;
			}
			rs.close();
			sql ="select count(*) as num from ctkhuyenmai where nguoitao ='"+ Id +"' or  nguoisua ='"+ Id +"'";
			ResultSet tg = db.get(sql);
			tg.next();
			if(!tg.getString("num").equals("0"))
			{
				this.msg ="khong the xoa duoc ";
				return false;

			}
			
			if(!Utility.KiemTra_PK_SEQ_HopLe(Id, "nhanvien",  db))
			{
			   

				this.msg ="Id không hợp lệ ";
				return false;
			}
			if(xoa1(Id))
			{
				sql ="delete from nhanvien where pk_seq ='"+ Id +"'";
				if(!db.update(sql))
				{
					this.msg ="khong the xoa duoc ";
					return false;
				}
			}
			if(tg!=null){
				tg.close();
			}
		} catch(Exception e) {

			e.printStackTrace();
		}

		return false;
	}
	boolean xoa1(String Id)
	{
		String sql ="delete from phanquyen where nhanvien_fk ='"+Id +"'";

		if(!db.update(sql))
		{
			this.msg = sql;
			return false;
		}
		System.out.println(sql);
		sql ="delete from phamvihoatdong where nhanvien_fk ='"+ Id+"'";

		if(!db.update(sql))
		{
			this.msg = sql;
			return false;
		}
		System.out.println(sql);
		sql ="delete from nhanvien_kenh where nhanvien_fk ='"+ Id +"'";

		if(!db.update(sql))
		{
			this.msg = sql;
			return false;
		}
		System.out.println(sql);
		sql ="delete from nhanvien_sanpham where nhanvien_fk ='"+ Id +"'";

		if(!db.update(sql))
		{
			this.msg = sql;
			return false;
		}
		System.out.println(sql);

		sql ="delete from nhanvien_nhomskus where nhanvien_fk ='"+ Id +"'";

		if(!db.update(sql))
		{
			this.msg = sql;
			return false;
		}
		System.out.println(sql);
		return true;
	}

	public void setmsg(String msg) {

	}

	public String getmsg() {
		return null;
	}
	@Override
	public void DbClose() {
		// TODO Auto-generated method stub
		try{
			if(DSNV!=null)
				DSNV.close();
			if(loainv!=null)
				loainv.close();
			db.shutDown();
		}catch(Exception er){

		}
	}

	public List<Object> getDataSearch() {
		return dataSearch;
	}

	public void setDataSearch(List<Object> dataSearch) {
		this.dataSearch = dataSearch;
	}

}
