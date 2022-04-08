package geso.dms.center.beans.khoahuanluyen.imp;

import java.sql.ResultSet;
import java.sql.SQLException;

import geso.dms.center.beans.khoahuanluyen.IKhoahuanluyenList;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;

public class KhoahuanluyenList implements IKhoahuanluyenList 
{
	String userId;
	String userTen;
	String tungay;
	String denngay;
	String trangthai; 
	String tenkhoa;
	String diengiai;
	String msg;
	
	ResultSet khlRs;
	
	ResultSet vungRs;
	String vungId;
	ResultSet kvRs;
	String kvId;
	ResultSet nppRs;
	String nppId;
	ResultSet kbhRs;
	String kbhId;

	String khlIds;
	
	String view;
	
	dbutils db;
	
	public KhoahuanluyenList()
	{
		this.userId = "";
		this.userTen = "";
		this.tungay = "";
		this.denngay = "";
		this.trangthai = "";
		this.tenkhoa = "";
		this.diengiai = "";
		this.msg = "";
		
		//Bao cao
		this.vungId = "";
		this.kvId = "";
		this.nppId = "";
		this.kbhId = "";
		this.khlIds = "";
		
		this.view = "";
		
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

	public String getTungay()
	{
		return this.tungay;
	}

	public void setTungay(String tungay) 
	{
		this.tungay = tungay;
	}

	public String getDenngay()
	{
		return this.denngay;
	}

	public void setDenngay(String denngay)
	{
		this.denngay = denngay;
	}

	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}

	public String getTenkhoa() 
	{
		return this.tenkhoa;
	}

	public void setTenkhoa(String tenkhoa) 
	{
		this.tenkhoa = tenkhoa;
	}

	public String getDiengiai() 
	{
		return this.diengiai;
	}

	public void setDiengiai(String diengiai) 
	{
		this.diengiai = diengiai;
	}

	public String getMsg() 
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}

	public ResultSet getKhoahuanluyenRs() 
	{
		return this.khlRs;
	}

	public void setKhoahuanluyenRs(ResultSet khlRs) 
	{
		this.khlRs = khlRs;
	}

	public void init(String query) 
	{
		String sql = "";
		
		if(query.length() > 0)
			sql = query;
		else
		{	
			sql = "select a.pk_seq, a.tungay, a.denngay, a.tieude, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua  " +
				"from KhoaHuanLuyen a inner join NHANVIEN b on a.NGUOITAO = b.pk_seq " +
				"inner join NHANVIEN c on a.NGUOISUA = c.pk_seq order by a.denngay desc, a.trangthai asc ";
		}
		
		this.khlRs = db.get(sql);
	}

	public void DbClose() 
	{
		
		try 
		{
			if(this.khlRs != null)
				this.khlRs.close();
			this.db.shutDown();
		} 
		catch (SQLException e) {}
		
	}

	public ResultSet getVungRs() 
	{
		return this.vungRs;
	}

	public void setVungRs(ResultSet vungRs)
	{
		this.vungRs = vungRs;
	}

	public String getVungId() 
	{
		return this.vungId;
	}

	public void setVungId(String vungId) 
	{
		this.vungId = vungId;
	}

	public ResultSet getKhuvucRs() 
	{
		return this.kvRs;
	}

	public void setKhuvucRs(ResultSet kvRs) 
	{
		this.kvRs = kvRs;
	}

	public String getKvId()
	{
		return this.kvId;
	}

	public void setKvId(String kvId) 
	{
		this.kvId = kvId;
	}

	public ResultSet getKbhRs()
	{
		return this.kbhRs;
	}

	public void setKbhRs(ResultSet kbhRs)
	{
		this.kbhRs = kbhRs;
	}

	public String getKbhId() 
	{
		return this.kbhId;
	}

	public void setKbhId(String kbhId) 
	{
		this.kbhId = kbhId;
	}

	public ResultSet getNppRs()
	{
		return this.nppRs;
	}

	public void setNppRs(ResultSet nppRs) 
	{
		this.nppRs = nppRs;
	}

	public String getNppId() 
	{
		return this.nppId;
	}

	public void setNppId(String nppId)
	{
		this.nppId = nppId;	
	}

	public ResultSet getKhlRs()
	{
		return this.khlRs;
	}
	
	public void setKhlRs(ResultSet khlRs) 
	{
		this.khlRs = khlRs;
	}

	public String getKhlId() 
	{
		return this.khlIds;
	}

	public void setKhlIds(String khlIds) 
	{
		this.khlIds = khlIds;
	}

	
	public void initBC(String query) 
	{
		this.kbhRs = db.get(" select pk_seq,ten,diengiai from kenhbanhang where trangthai = '1' ");

		this.vungRs = db.get("select pk_seq,ten,diengiai from vung where trangthai = '1' ");

		if (this.vungId.length() > 0) 
		{
			this.kvRs = db.get("select pk_seq,ten from khuvuc where vung_fk = '"+ this.vungId + "' and trangthai = '1'");
		} 
		else
			this.kvRs = db.get("select pk_seq,ten from khuvuc where trangthai = '1' ");

		String sql = "select pk_seq, ma, ten, isnull(diachi, 'NA') as diachi from nhaphanphoi where trangthai = '1' ";
		if (this.kvId.trim().length() > 0) 
		{
			sql += " and khuvuc_fk ='" + this.kvId + "'";
		}
		
		if (this.vungId.trim().length() > 0)
		{
			sql += " and khuvuc_fk in (select pk_seq from khuvuc where vung_fk = '" + this.vungId + "')";
		}
		
		if (this.kbhId.trim().length() > 0)
			sql +=  " and pk_seq in (select npp_fk from NHAPP_KBH where kbh_fk = '" + this.kbhId + "')";
		Utility util=new Utility();
		sql=sql+" and pk_seq in"+util.quyen_npp(this.userId);
		
		System.out.println("1.Khoi tao Npp: " + sql);
		this.nppRs = db.get(sql);
		
		this.khlRs = db.get("select pk_seq, tieude, tungay, denngay from khoahuanluyen where trangthai != 2 order by denngay desc");
		
	}

	public String getUserTen() 
	{
		return this.userTen;
	}

	public void setUserten(String userTen) 
	{
		this.userTen = userTen;
	}
	
	String gia="";

	public String getGia()
	{
		return gia;
	}

	public void setGia(String gia)
	{
		this.gia = gia;
	}

	public String getView()
	{
		return this.view;
	}

	public void setView(String view) 
	{
		this.view = view;
		
	}
	

}
