package geso.dms.center.beans.denghitratrungbay.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

import geso.dms.center.beans.denghitratrungbay.IDeNghiTraTrungBay;
import geso.dms.center.db.sql.dbutils;

public class DeNghiTraTrungBay  implements IDeNghiTraTrungBay
{

	String id,nppId,cttbId,msg,trangthai,landuyet,ngaydenghi,userId;
	public String getUserId()
  {
  	return userId;
  }

	public void setUserId(String userId)
  {
  	this.userId = userId;
  }

	public String getNgaydenghi()
  {
  	return ngaydenghi;
  }

	public void setNgaydenghi(String ngaydenghi)
  {
  	this.ngaydenghi = ngaydenghi;
  }

	public String getTrangthai()
  {
  	return trangthai;
  }

	public void setTrangthai(String trangthai)
  {
  	this.trangthai = trangthai;
  }

	public String getLanduyet()
  {
  	return landuyet;
  }

	public void setLanduyet(String landuyet)
  {
  	this.landuyet = landuyet;
  }

	String[] khId,khTen,khMa,khDiaChi,khDienThoai,khDangKy,khDat,khDeNghi,khDuyet;
	public String[] getKhDiaChi()
  {
  	return khDiaChi;
  }

	public void setKhDiaChi(String[] khDiaChi)
  {
  	this.khDiaChi = khDiaChi;
  }

	public String[] getKhDienThoai()
  {
  	return khDienThoai;
  }

	public void setKhDienThoai(String[] khDienThoai)
  {
  	this.khDienThoai = khDienThoai;
  }

	ResultSet nppRs,cttbRs;
	dbutils db;
	
	public DeNghiTraTrungBay(String id)
	{
		this.id=id;
		this.nppId="";
		this.cttbId="";
		this.msg="";
		db = new  dbutils();
	}
	
	@Override
  public String getId()
  {
	  return id;
  }

	@Override
  public void setId(String id)
  {
	 this.id=id;
  }

	@Override
  public String getNppId()
  {
	  return this.nppId;
  }

	@Override
  public void setNppId(String nppId)
  {
		this.nppId=nppId;
  }

	@Override
  public String[] getKhId()
  {
	  return this.khId;
  }

	@Override
  public void setKhId(String[] khId)
  {
	 this.khId=khId;
  }

	@Override
  public String[] getKhTen()
  {
	  return khTen;
  }

	@Override
  public void setKhTen(String[] khTen)
  {
	 this.khTen=khTen;
  }

	@Override
  public String[] getKhMa()
  {
	  return khMa;
  }

	@Override
  public void setKhMa(String[] khMa)
  {
	 this.khMa= khMa;
  }

	@Override
  public String[] getKhDangKy()
  {
	  return khDangKy;
  }

	@Override
  public void setKhDangKy(String[] dangky)
  {
		this.khDangKy=dangky;
  }

	@Override
  public String[] getKhDeNghi()
  {
	  return this.khDangKy;
  }

	@Override
  public void setKhDeNghi(String[] denghi)
  {
		this.khDeNghi=denghi;
  }

	@Override
  public String[] getKhDuyet()
  {
	  return this.khDuyet;
  }

	@Override
  public void setKhDuyet(String[] duyet)
  {
		this.khDuyet=duyet;
  }

	@Override
  public String[] getKhDat()
  {
	  return this.khDat;
  }

	@Override
  public void setKhDat(String[] dat)
  {
	 this.khDat=dat;
  }

	@Override
  public String getMsg()
  {
	  return msg;
  }

	@Override
  public void setMsg(String msg)
  {
	 this.msg=msg;
  }

	@Override
  public boolean Chot()
  {
	  
		try
    {
	    db.getConnection().setAutoCommit(false);
	    String query="";
			Enumeration<String> keys = this.khduyet.keys();
			while(keys.hasMoreElements())
			{
				String key = keys.nextElement();
				String value=this.khduyet.get(key);				
				query="update DENGHITRATB_KHACHHANG set XuatDuyet="+value+" where khachhang_fk='"+key+"' and DENGHITRATB_FK='"+this.id+"' and "+value+"<=XuatDeNghi " ;		
				if (db.updateReturnInt(query)!=1)
				{
					this.msg = "Xuất duyệt không được lớn hơn xuất đề nghị ";
					db.getConnection().rollback();
					return false;
				}
			}
			query=" update DENGHITRATRUNGBAY set NgaySua='"+getDateTime()+"' , NguoiSua='"+this.userId+"',TrangThai=1 where pk_seq='"+this.id+"'  ";
			if (db.updateReturnInt(query)!=1)
			{
				this.msg = "Lỗi phát sinh khi cập nhật "+query;
				db.getConnection().rollback();
				return false;
			}
	    db.getConnection().commit();
	    db.getConnection().setAutoCommit(true);
    } catch (SQLException e)
    {

	    e.printStackTrace();
    }
		
	  return true;
  }

	@Override
  public String getCttbId()
  {
	  return this.cttbId;
  }

	@Override
  public void setCttbId(String cttbId)
  {
	 this.cttbId=cttbId;
  }

	@Override
  public ResultSet getNppRs()
  {
	  return nppRs;
  }

	@Override
  public void setNppRs(ResultSet nppRs)
  {
	  this.nppRs=nppRs;
  }

	@Override
  public ResultSet getCttbRs()
  {
	  return cttbRs;
  }

	@Override
  public void setCttbRs(ResultSet cttbRs)
  {
		this.cttbRs=cttbRs;
  }

	@Override
  public void createRs()
  {
	  
  }

	@Override
  public void init()
  {
		String query=
			"	select CTTRUNGBAY_FK,NGAYDENGHI,TRANGTHAI,NPP_FK,LANTHANHTOAN from DENGHITRATRUNGBAY a "+ 
			"	where a.PK_SEQ='"+this.id+"' ";
		ResultSet rs = db.get(query);
		try
    {
	    while(rs.next())
	    {
	    	this.nppId =  rs.getString("NPP_FK");
	    	this.cttbId  = rs.getString("CTTRUNGBAY_FK");
	    	this.trangthai = rs.getString("TRANGTHAI");
	    	this.landuyet = rs.getString("LANTHANHTOAN");
	    }
	    if(rs!=null)rs.close();
	    
	    String khID ="";
	    String khMA ="";
	    String khTEN ="";
	    String khDIACHI ="";
	    String khDANGKY ="";
	    String khDENGHI ="";
	    String khDUYET ="";
			query=
					"	select a.KHACHHANG_FK,b.SMARTID,b.TEN,b.DIACHI,b.DIENTHOAI ,isnull(a.XUATDANGKY,0) as XUATDANGKY,isnull(a.XUATDENGHI,0) as XUATDENGHI  ,isnull(a.XUATDUYET,0) as XUATDUYET "+
					"	from DENGHITRATB_KHACHHANG a inner join KHACHHANG b on b.PK_SEQ=a.KHACHHANG_FK "+
					"	where a.DENGHITRATB_FK='"+this.id+"' ";
			
			this.khRs =this.db.get(query);
			
						rs=db.get(query);
						while(rs.next())
						{
							khduyet.put(rs.getString("KHACHHANG_FK"), rs.getString("XUATDUYET"));
							 
							 khID += rs.getString("KHACHHANG_FK")+"__";
							 khMA += rs.getString("SMARTID")+"__";
							 khTEN += rs.getString("TEN")+"__";
							 khDIACHI += rs.getString("DIACHI")+"__";
							 khDANGKY += rs.getString("XUATDANGKY")+"__";
							 khDENGHI += rs.getString("XUATDENGHI")+"__";
							 khDUYET += rs.getString("XUATDUYET")+"__";
						}
						if(rs!=null)rs.close();
						if(khID.trim().length() > 0)
						{
							khID = khID.substring(0, khID.length() -2 );
							this.khId = khID.split("__");
							
							khMA = khMA.substring(0, khMA.length() -2 );
							this.khMa = khMA.split("__");
							
							khTEN = khTEN.substring(0, khTEN.length() -2 );
							this.khTen = khTEN.split("__");
							
							khDIACHI = khDIACHI.substring(0, khDIACHI.length() -2 );
							this.khDiaChi = khDIACHI.split("__");
							
							khDANGKY = khDANGKY.substring(0, khDANGKY.length() -2 );
							this.khDangKy = khDANGKY.split("__");
							
							khDENGHI = khDENGHI.substring(0, khDENGHI.length() -2 );
							this.khDeNghi = khDENGHI.split("__");
							
							khDUYET = khDUYET.substring(0, khDUYET.length() -2 );
							this.khDuyet = khDUYET.split("__");
						}
    } catch (SQLException e)
    {
	    e.printStackTrace();
    }
		query="select pk_Seq,ten,ma,diachi,dienthoai from nhaphanphoi where pk_Seq='"+this.nppId+"' ";
		this.nppRs=  db.get(query);
		
		query="select PK_SEQ,SCHEME,NGAYKTTDS,NGAYKTTDS,NGAYTRUNGBAY,NGAYKETTHUCTB,DIENGIAI,SOLANTHANHTOAN,ngaydangky,NGAYKETTHUCDK from CTTRUNGBAY where pk_Seq='"+this.cttbId+"' ";
		this.cttbRs=  db.get(query);
		
		
  }
	ResultSet khRs;
	public ResultSet getKhRs()
  {
  	return khRs;
  }

	public void setKhRs(ResultSet khRs)
  {
  	this.khRs = khRs;
  }

	Hashtable<String, String> khduyet= new Hashtable<String, String>();
	@Override
  public Hashtable<String, String> getKh_Duyet()
  {
	  return this.khduyet;
  }

	@Override
  public void setKh_Duyet(Hashtable<String, String> khDuyet)
  {
	 this.khduyet=khDuyet;
  }

	@Override
  public boolean Save()
  {
		try
    {
	    db.getConnection().setAutoCommit(false);
	    String query="";
			Enumeration<String> keys = this.khduyet.keys();
			while(keys.hasMoreElements())
			{
				String key = keys.nextElement();
				String value=this.khduyet.get(key);				
				query="update DENGHITRATB_KHACHHANG set XuatDuyet="+value+" where khachhang_fk='"+key+"' and DENGHITRATB_FK='"+this.id+"' and "+value+"<=XuatDeNghi " ;		
				if (db.updateReturnInt(query)!=1)
				{
					this.msg = "Xuất duyệt không được lớn hơn xuất đề nghị ";
					db.getConnection().rollback();
					return false;
				}
			}
			query=" update DENGHITRATRUNGBAY set NgaySua='"+getDateTime()+"' , NguoiSua='"+this.userId+"' where pk_seq='"+this.id+"'  ";
			if (db.updateReturnInt(query)!=1)
			{
				this.msg = "Lỗi phát sinh khi cập nhật "+query;
				db.getConnection().rollback();
				return false;
			}
	    db.getConnection().commit();
	    db.getConnection().setAutoCommit(true);
    } catch (SQLException e)
    {

	    e.printStackTrace();
    }
		
	  return true;
  }
	
	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
}

	