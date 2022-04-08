package geso.dms.center.beans.tieuchithuong.imp;

import java.sql.ResultSet;
import geso.dms.center.beans.tieuchithuong.ITieuchithuongDPList;
import geso.dms.center.db.sql.dbutils;

public class TieuchithuongDPList implements ITieuchithuongDPList 
{
	String userId;
	
	String thang;
	String nam;
	String trangthai;
	String msg;
	String diengiai;
	
	ResultSet rsTieuchi;
	
	ResultSet vungRs;
	String vungId;
	ResultSet khuvucRs;
	String khuvucId;
	ResultSet nppRs;
	String nppId;
	ResultSet schemeRs;
	String schemeId;
	
	String type;
	String tungay;
	String denngay;
	
	dbutils db;
	
	public TieuchithuongDPList()
	{
		this.thang = "";
		this.nam = "";
		this.trangthai = "";
		this.msg = "";
		this.diengiai = "";
		this.vungId = "";
		this.khuvucId = "";
		this.nppId = "";
		this.schemeId = "";
		
		this.type = "1"; //xem theo thang
		this.tungay = "";
		this.denngay = "";
		
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

	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}


	public void init(String query)
	{
		String sql = "";
		if(query.length() > 0)
			sql = query;
		else
		{
			sql = "select a.scheme, a.pk_seq, a.thang, a.nam, a.diengiai, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua  " +
					"from TIEUCHITHUONGDP a inner join NHANVIEN b on a.NGUOITAO = b.pk_seq inner join NHANVIEN c on a.NGUOISUA = c.pk_seq " +
					"order by nam desc, thang desc";
		}
		
		System.out.println("1.Khoi tao chi tieu: " + sql);
		this.rsTieuchi = db.get(sql);
	}

	public String getMsg()
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}

	public String getDiengiai() 
	{
		return this.diengiai;
	}

	public void setDiengiai(String diengiai) 
	{
		this.diengiai = diengiai;
	}

	public ResultSet getTieuchiSKUInRs()
	{
		return this.rsTieuchi;
	}

	public void setTieuchiSKUInRs(ResultSet tieuchiSKU) 
	{
		this.rsTieuchi = tieuchiSKU;
	}

	public void initReport(String query) 
	{
		this.vungRs = db.get("select pk_seq,ten,diengiai from vung where trangthai = '1'");
		
		if (this.vungId.length() > 0)
		{
			this.khuvucRs = db.get("select pk_seq, ten from khuvuc where trangthai = '1' and vung_fk = '" + this.vungId + "'");
		} 
		else
			this.khuvucRs = db.get("select pk_seq, ten from khuvuc where trangthai = '1' ");
		
		String sql = "select pk_seq,ten from nhaphanphoi where trangthai ='1' ";
		if (this.khuvucId.length() > 0)
		{
			sql += " and khuvuc_fk ='" + this.khuvucId + "'";
		}
		
		if (this.vungId.length() > 0) 
		{
			sql += " and khuvuc_fk in (select pk_seq from khuvuc where vung_fk ='" + this.vungId + "')";
		}
		
		this.nppRs = db.get(sql);
		
		//System.out.println("Get NPP :"+sql);
		/*
		if (this.kenhId.length() > 0)
			sql = sql
					+ " and pk_seq in (select npp_fk from NHAPP_KBH where kbh_fk ='"
					+ this.kenhId + "')";*/
		
		if(this.thang.trim().length() > 0 && this.nam.trim().length() > 0)
		{
			this.schemeRs= db.get("select pk_seq as schemeId, scheme as schemeTen from TieuChiThuongDP where thang = '" + this.thang + "' and nam = '" + this.nam + "' and trangthai = '1'");
		}
		
		
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

	public void setVungId(String vung) 
	{
		this.vungId = vung;
	}

	public ResultSet getKhuvucRs()
	{
		return this.khuvucRs;
	}

	public void setKhuvucRs(ResultSet khuvucRs) 
	{
		this.khuvucRs = khuvucRs;
	}

	public String getKvId()
	{
		return this.khuvucId;
	}

	public void setKvId(String kv)
	{
		this.khuvucId = kv;
	}

	public ResultSet getNppRs()
	{
		return this.nppRs;
	}

	public void setNppRs(ResultSet nppRs) 
	{
		this.nppRs = nppRs;
	}

	public String getNppIds()
	{
		return this.nppId;
	}

	public void setNppIds(String nppId) 
	{
		this.nppId = nppId;
	}

	public String getType() 
	{
		return this.type;
	}

	public void setType(String type) 
	{
		this.type = type;
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

	
	public ResultSet getSchemeRs() {
		
		return this.schemeRs;
	}

	
	public void setSchemeRs(ResultSet schemeRs) {
		
		this.schemeRs = schemeRs;
	}

	
	public String getSchemeIds() {
		
		return this.schemeId;
	}

	
	public void setSchemeIds(String schemeId) {
		
		this.schemeId = schemeId;
	}
	
}
