package geso.dms.distributor.beans.tradonhang.imp;

import java.sql.ResultSet;

import geso.dms.center.util.Phan_Trang;
import geso.dms.distributor.beans.tradonhang.ITraDonHangList;
import geso.dms.distributor.db.sql.dbutils;

public class TraDonHangList  extends Phan_Trang implements ITraDonHangList
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
	public TraDonHangList(String[] param)
	{
		this.db = new dbutils();
		this.tungay = param[1];
		this.denngay = param[2];
		this.trangthai = param[3];
		this.userId = "";
		this.nppTen = "";
		this.nppId = "";
		createDdhlist("");
	}
	
	public TraDonHangList()
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
			"	SELECT DH.DONHANG_FK ,DH.PK_SEQ AS NHAPHANGID,DH.NGAYNHAP,NT.TEN AS NGUOITAO,DH.NGAYTAO, "+ 
			"		NS.TEN AS NGUOISUA,DH.NGAYSUA,DH.TRANGTHAI,ISNULL(DH.NGAYNHAP,'') AS NGAYCHOT,KH.DIENTHOAI,KH.TEN   AS KHTEN "+
			"	FROM DONHANGTRAVE DH INNER JOIN KHACHHANG KH ON KH.PK_SEQ=DH.KHACHHANG_FK    "+
			"		INNER JOIN NHANVIEN NT ON  DH.NGUOITAO=NT.PK_SEQ   "+
			"		INNER JOIN NHANVIEN NS ON NS.PK_SEQ=DH.NGUOISUA  "+
			" where dh.npp_fk='"+this.nppId+"' AND DH.DONHANG_FK IS NOT NULL ";
		}
		this.dhList =  createSplittingData(50, 15, "NGAYNHAP desc,NHAPHANGID ", query);
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
