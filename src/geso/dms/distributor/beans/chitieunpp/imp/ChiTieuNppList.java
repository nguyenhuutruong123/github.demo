package geso.dms.distributor.beans.chitieunpp.imp;

import geso.dms.center.beans.chitieu.IChiTieuNhanVienList;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Phan_Trang;
import geso.dms.center.util.Utility;
import geso.dms.distributor.beans.chitieunpp.IChiTieuNppList;

import java.io.Serializable;
import java.sql.ResultSet;

public class ChiTieuNppList  extends Phan_Trang implements IChiTieuNppList, Serializable    {

	
	private static final long serialVersionUID = 8092745136245984000L;

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
	
	public ChiTieuNppList()
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
		this.view="";
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

	Utility Ult = new Utility();
	private void getNppInfo()
	{		
		String sql = "";
		try
		{
			sql = "select phanloai,LOAI from nhanvien where pk_seq=" + this.userId;
			ResultSet rs = this.db.get(sql);
			if (rs != null)
			{
				if (rs.next())
				{
					this.nppId = Ult.getIdNhapp(this.userId);
					rs.close();
				}
			}
		} catch (Exception er)
		{

		}
	
	
		
	}
	

	public void init(String query)
	{
		getNppInfo();
		String sql = "";
		if(query.length() > 0)
			sql = query;
		else
		{
			sql = 
			"	select a.ma, a.pk_seq, a.Thang,a.Quy,a.nam, a.ten , a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua  "+ 
			"		from ChiTieuNhanVien a  "+
			"		inner join NHANVIEN b on a.NGUOITAO = b.pk_seq "+ 
			"		inner join NHANVIEN c on a.NGUOISUA = c.pk_seq  "+
			"  where 1=1 and a.LoaiChiTieu='"+this.type+"' and a.pk_Seq in (select ChiTieuNhanVien_fk from ChiTieuNhanVien_DDKD   where npp_Fk='"+this.nppId+"' ) " ; 
		}
		
		this.rsTieuchi = this.createSplittingData(super.getItems(), super.getSplittings(), "nam,Thang ,quy desc", sql);
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
		
		sql = "select pk_seq as schemeId, scheme as schemeTen from TieuChiThuongTL where trangthai = '1' ";
		if(this.tungay.trim().length() > 0)
		{
			sql += " and thang >= '" + this.tungay.trim() + "' ";
		}
		if(this.denngay.trim().length() > 0)
		{
			sql += " and thang <= '" + this.denngay.trim() + "' ";
		}
		this.schemeRs = db.get(sql);
		
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
	
	String view;

	public String getView()
  {
  	return view;
  }

	public void setView(String view)
  {
  	this.view = view;
  }
	
	String tructhuocId;
	@Override
  public String getTructhuocId()
  {
	  return tructhuocId;
  }

	@Override
  public void setTructhuocId(String tructhuocId)
  {
			this.tructhuocId=tructhuocId;
  }
	
}
