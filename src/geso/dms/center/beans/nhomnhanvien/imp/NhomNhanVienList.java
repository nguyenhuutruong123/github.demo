package geso.dms.center.beans.nhomnhanvien.imp;
import geso.dms.center.beans.nhomnhanvien.INhomNhanVienList;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Phan_Trang;

import java.sql.ResultSet;

public class NhomNhanVienList  extends Phan_Trang implements INhomNhanVienList
{
	private static final long serialVersionUID = 1L;
	String id, ma, ten, msg, userId;
	dbutils db;
	ResultSet nhomRs;

	public NhomNhanVienList()
	{
		this.id = "";
		this.ma = "";
		this.ten = "";
		this.msg = "";
		this.userId = "";
		this.db = new dbutils();
	}

	public NhomNhanVienList(String id, String ma, String ten, String msg, String userId, dbutils db)
	{
		this.id = id;
		this.ma = ma;
		this.ten = ten;
		this.msg = msg;
		this.userId = userId;
		this.db = db;
	}

	@Override
	public String getId()
	{

		return this.id;
	}

	@Override
	public void setId(String id)
	{
		this.id = id;
	}

	@Override
	public String getMa()
	{

		return this.ma;
	}

	@Override
	public void setMa(String ma)
	{
		this.ma = ma;
	}

	@Override
	public String getTen()
	{

		return this.ten;
	}

	@Override
	public void setTen(String ten)
	{
		this.ten = ten;
	}

	@Override
	public String getMsg()
	{

		return this.msg;
	}

	@Override
	public void setMsg(String msg)
	{
		this.msg = msg;

	}

	@Override
	public String getUserId()
	{

		return this.userId;
	}

	@Override
	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	@Override
	public void closeDB()
	{

	}

	@Override
	public void init(String query)
	{
		if(query.length() <= 0)
		 query=
		"SELECT a.pk_seq as id,a.ten,nt.TEN as NguoiTao,a.ngaytao,ns.TEN as NguoiSua,a.ngaysua "+
		"FROM NhomNhanVien a inner join NHANVIEN nt on nt.PK_SEQ=a.nguoitao "+
		"	inner join NHANVIEN ns on ns.PK_SEQ=a.nguoisua ";
		this.nhomRs=this.db.get(query);
	}

	@Override
	public void createRs()
	{

	}

	@Override
	public ResultSet getNhomRs()
	{
		return this.nhomRs;
	}

	@Override
	public void setNhomRs(ResultSet nhomRs)
	{
		this.nhomRs = nhomRs;
	}
	
}
