package geso.dms.center.beans.chitieu.imp;

import java.io.Serializable;
import java.sql.ResultSet;

import geso.dms.center.beans.chitieu.IChuLucList;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Phan_Trang;

public class ChuLucList  extends Phan_Trang implements IChuLucList, Serializable
{
	private static final long serialVersionUID = -2527170100288575419L;
	dbutils db;
	String userId,thang,nam;
	String trangthai;
	public ChuLucList()
	{
		this.thang = "";
		this.nam = "";
		this.trangthai = "";
		this.msg = "";
		this.diengiai = "";
		this.tungay = "";
		this.denngay = "";
		this.view="";
		this.db = new dbutils();
	}	
	@Override
	public String getUserId()
	{
		return this.userId;
	}

	@Override
	public void setUserId(String userId)
	{
		this.userId= userId;
	}

	@Override
	public String getThang()
	{
		return this.thang;
	}

	@Override
	public void setThang(String thang)
	{
		this.thang =thang;
	}

	@Override
	public String getNam()
	{
		return this.nam;
	}

	@Override
	public void setNam(String nam)
	{
		this.nam=nam;
	}


	@Override
	public String getTrangthai()
	{
		return this.trangthai;
	}

	@Override
	public void setTrangthai(String trangthai)
	{
		this.trangthai=trangthai;
	}

	String msg;
	@Override
	public String getMsg()
	{
		return this.msg;
	}

	@Override
	public void setMsg(String msg)
	{
		this.msg=msg;
	}

	String diengiai;
	@Override
	public String getDiengiai()
	{
		return this.diengiai;
	}

	@Override
	public void setDiengiai(String diengiai)
	{
		this.diengiai=diengiai;
	}

	@Override
	public void init(String query)
	{
		String sql = "";
		if(query.length() > 0)
			sql = query;
		else
		{
			sql = 
			"	select a.ma, a.pk_seq, a.Ky,a.Quy,a.nam, a.ten , a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua  "+ 
			"		from ChiTieuChuLuc a  "+
			"		inner join NHANVIEN b on a.NGUOITAO = b.pk_seq "+ 
			"		inner join NHANVIEN c on a.NGUOISUA = c.pk_seq  "+
			"  where 1=1 " ; 
		}
		this.dataRs = this.createSplittingData(super.getItems(), super.getSplittings(), "nam,ky,quy desc", sql);
	}
	
	
	ResultSet dataRs;

	@Override
	public ResultSet getDataRs()
	{
		return this.dataRs;
	}

	@Override
	public void setDataRs(ResultSet dataRs)
	{
		this.dataRs=dataRs;
	}

	
	String tungay,denngay;
	@Override
	public String getTungay()
	{
		return this.tungay;
	}

	@Override
	public void setTungay(String tungay)
	{
		this.tungay =tungay;
	}

	@Override
	public String getDenngay()
	{
		return this.denngay;
	}

	@Override
	public void setDenngay(String denngay)
	{
		this.denngay =denngay;
	}

	String view;
	@Override
	public String getView()
	{
		return this.view;
	}

	@Override
	public void setView(String view)
	{
		this.view =view;

	}

}
