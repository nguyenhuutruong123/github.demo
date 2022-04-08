package geso.dms.distributor.beans.xuatkho.imp;

import geso.dms.center.util.Phan_Trang;
import geso.dms.distributor.beans.xuatkho.IXuatKhoList;
import geso.dms.distributor.db.sql.dbutils;

import java.io.Serializable;
import java.sql.ResultSet;

public class XuatKhoList extends Phan_Trang implements IXuatKhoList, Serializable
{
	private static final long serialVersionUID = 6858020173468336341L;
	private String userId;
	private String nppTen;
	private String nppId;
	private String tungay;
	private String denngay;
	private String trangthai,msg;
	private ResultSet dhList;
	private dbutils db;
	public XuatKhoList(String[] param)
	{
		this.db = new dbutils();
		this.tungay = param[1];
		this.denngay = param[2];
		this.trangthai = param[3];
		this.userId = "";
		this.nppTen = "";
		this.nppId = "";
		this.msg="";
		createDdhlist("");
	}
	
	public XuatKhoList()
	{
		this.db = new dbutils();
		this.userId = "";
		this.nppTen = "";
		this.nppId = "";
		this.tungay = "";
		this.denngay = "";
		this.trangthai = "";
		this.msg="";
		
	}
	
	public ResultSet getDhList()
	{
		return this.dhList;
	}
	
	public void setDhList(ResultSet dhList)
	{
		this.dhList = dhList;
	}

	public String getUserId()
	{
		return this.userId;
	}
	
	public void setUserId(String userId)
	{
		this.userId = userId;
		getNPPInfo();
	}

	public String getNppId()
	{
		return this.nppId;
	}
	
	public void setNppId(String nppId)
	{
		this.nppId = nppId;
	}

	public String getNppTen()
	{
		return this.nppTen;
	}
	
	public void setNppTen(String nppTen)
	{
		this.nppTen = nppTen;
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

	public void createDdhlist(String querystr)
	{
		String query;
		if (querystr.length()>0)
		{
			query = querystr;
		}else
		{				
			query = 
			"	select dh.pk_seq as nhaphangId,dh.NgayXuat ,nt.TEN as NguoiTao,dh.ngaytao,ns.TEN as NguoiSua,dh.ngaysua,dh.TrangThai "+
			"	from XuatKho dh inner join NHANVIEN nt on  dh.NguoiTao=nt.PK_SEQ "+
			"		inner join NHANVIEN ns on ns.PK_SEQ=dh.NguoiSua "+
			" where dh.npp_fk='"+this.nppId+"' ";

			System.out.println("Khoi tao : "+query);
		}
		this.dhList =  super.createSplittingData(super.getItems(), super.getSplittings(), "nhaphangId desc", query);
	}

	private void getNPPInfo()
	{
		geso.dms.distributor.util.Utility util=new geso.dms.distributor.util.Utility();
		this.nppId = util.getIdNhapp(this.userId);
		this.nppTen = util.getTenNhaPP();
	}

	@Override
	public void DBclose(){
		
		try {
			if(this.dhList != null)
				this.dhList.close();
			if(!(this.db == null)){
				this.db.shutDown();
			}
			
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

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
}
