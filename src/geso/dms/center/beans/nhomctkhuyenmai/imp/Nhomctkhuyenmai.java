package geso.dms.center.beans.nhomctkhuyenmai.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import geso.dms.center.beans.nhomctkhuyenmai.INhomctkhuyenmai;
import geso.dms.center.db.sql.dbutils;

public class Nhomctkhuyenmai implements INhomctkhuyenmai
{
	private static final long serialVersionUID = -9217977546733690415L;
	String userId;
	String id;
	String ten;
	String diengiai;
	String trangthai;	
	String ngaytao;
	String ngaysua;
	String nguoitao;
	String nguoisua;
	String tungay;
	String denngay;
	String msg;
	ResultSet ctkmList;
	String ctkmIds;
	dbutils db ;
	
	public Nhomctkhuyenmai()
	{
		this.id = "";
		this.ten = "";
		this.diengiai = "";
		this.trangthai = "0";
		this.ngaytao = "";
		this.ngaysua = "";
		this.nguoitao = "";
		this.nguoisua = "";
		this.msg = "";
		this.ctkmIds = "";
		this.tungay = "";
		this.denngay = "";
		this.db = new dbutils();
	}
	
	public Nhomctkhuyenmai(String id)
	{
		this.id = id;
		this.ten = "";
		this.diengiai = "";
		this.trangthai = "0";
		this.ngaytao = "";
		this.ngaysua = "";
		this.nguoitao = "";
		this.nguoisua = "";
		this.msg = "";
		this.ctkmIds = "";
		this.tungay = "";
		this.denngay = "";
		this.db = new dbutils();
	}
	
	public String getUserId() 
	{
		return this.userId;
	}

	public void setUserId(String userid)
	{
		this.userId = userid;
	}
	
	public String getId() 
	{
		return this.id;
	}
	
	public void setId(String id) 
	{
		this.id = id;
	}
	
	public String getTen() 
	{
		return this.ten;
	}
	
	public void setTen(String ten) 
	{
		this.ten = ten;
	}
	
	public String getDiengiai()
	{
		return this.diengiai;
	}
	
	public void setDiengiai(String diengiai) 
	{
		this.diengiai = diengiai;
	}
	
	public String getTrangthai()
	{
		return this.trangthai;
	}
	
	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}
	
	public String getNgaytao() 
	{
		return this.ngaytao;
	}
	
	public void setNgaytao(String ngaytao) 
	{
		this.ngaytao = ngaytao;
	}
	
	public String getNgaysua() 
	{
		return this.ngaysua;
	}
	
	public void setNgaysua(String ngaysua) 
	{
		this.ngaysua = ngaysua;
	}
	
	public String getNguoitao() 
	{
		return this.nguoitao;
	}
	
	public void setNguoitao(String nguoitao)
	{
		this.nguoitao = nguoitao;
	}
	
	public String getNguoisua()
	{
		return this.nguoisua;
	}
	
	public void setNguoisua(String nguoisua)
	{
		this.nguoisua = nguoisua;
	}
	
	public String getMessage()
	{
		return this.msg;
	}
	
	public void setMessage(String msg) 
	{
		this.msg = msg;
	}
	
	public ResultSet getCtkmList() 
	{
		return this.ctkmList;
	}
	
	public void setCtkmList(ResultSet ctkmList)
	{
		this.ctkmList = ctkmList;
	}
	
	public String getCtkmIds() 
	{
		return this.ctkmIds;
	}
	
	public void setCtkmIds(String ctkmIds) 
	{
		this.ctkmIds = ctkmIds;
	}
	
	public boolean save() 
	{
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			if(this.ctkmIds.length() <= 0)
			{
				this.msg = "Ban phai chon chuong trinh khuyen mai cho nhom ctkm nay";
				return false;
			}
			
			String query = "insert NHOMCTKHUYENMAI(tennhom, diengiai, ngaytao, ngaysua, nguoitao, nguoisua, trangthai) " +
					"values(N'" + this.ten + "', N'" + this.diengiai + "', '" + this.getDateTime() + "', '" + this.getDateTime() + "', '" + this.userId + "', '" + this.userId + "', '1')";
			if(!db.update(query))
			{
				this.msg = "Khong the tao moi NHOMCTKHUYENMAI, " + query;
				db.getConnection().rollback();
				return false;
			}
			
			String nctkmCurrent = "";
			query = "select IDENT_CURRENT('NHOMCTKHUYENMAI') as nctkmId";
			
			ResultSet rsNhomctkm = this.db.get(query);						
			rsNhomctkm.next();
			nctkmCurrent = rsNhomctkm.getString("nctkmId");
			rsNhomctkm.close();
			
			String[] ctkmid = this.ctkmIds.split(",");
			for(int i = 0; i < ctkmid.length; i++)
			{
				query = "insert nhomctkhuyenmai_ctkm(nhomctkmId, ctkmId) values('" + nctkmCurrent + "', '" + ctkmid[i] + "')";
				if(!db.update(query))
				{
					this.msg = "Khong the tao moi nhomctkhuyenmai_ctkm, " + query;
					db.getConnection().rollback();
					return false;
				}
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}
		
		return true;
	}
	
	public boolean update() 
	{
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			if(this.ctkmIds.length() <= 0)
			{
				this.msg = "Ban phai chon chuong trinh khuyen mai cho nhom ctkm nay";
				return false;
			}
			
			String query = "update NHOMCTKHUYENMAI set tennhom = N'" + this.ten + "', diengiai = N'" + this.diengiai + "', ngaysua = '" + this.getDateTime() +"', nguoisua = '" + this.userId + "' where pk_seq = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat NHOMCTKHUYENMAI, " + query;
				db.getConnection().rollback();
				return false;
			}
			
			db.update("delete nhomctkhuyenmai_ctkm where nhomctkmId = '" + this.id + "'");
			
			String[] ctkmid = this.ctkmIds.split(",");
			for(int i = 0; i < ctkmid.length; i++)
			{
				query = "insert nhomctkhuyenmai_ctkm(nhomctkmId, ctkmId) values('" + this.id + "', '" + ctkmid[i] + "')";
				if(!db.update(query))
				{
					this.msg = "Khong the cap nhat nhomctkhuyenmai_ctkm, " + query;
					db.getConnection().rollback();
					return false;
				}
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}
		
		return true;
	}

	public void init() 
	{
		String query = "select a.pk_seq as nkmId, a.tennhom, a.diengiai, a.ngaytao, a.ngaysua, a.trangthai, b.ctkmId " +
						"from nhomctkhuyenmai a inner join nhomctkhuyenmai_ctkm b on a.pk_seq = b.nhomctkmId where a.pk_seq = '" + this.id + "'";
		
		ResultSet rs = db.get(query);
		String ctkmIds = "";
		if(rs != null)
		{
			try 
			{
				while(rs.next())
				{
					this.ten = rs.getString("tennhom");
					this.diengiai = rs.getString("diengiai");
					this.trangthai = rs.getString("trangthai");
					
					ctkmIds += rs.getString("ctkmId") + ",";
				}
				rs.close();
				
				if(ctkmIds.length() > 0)
				{
					ctkmIds = ctkmIds.substring(0, ctkmIds.length() - 1);
					this.ctkmIds = ctkmIds;
				}
			} 
			catch(Exception e) {}
		}
		
		this.ctkmList = db.get("select pk_seq as ctkmId, scheme, diengiai from ctkhuyenmai where pk_seq in (select ctkmId from nhomctkhuyenmai_ctkm where nhomctkmId = '" + this.id + "') order by pk_seq desc");
		
	}

	public void createRs() 
	{
		String query = "";
		if(this.tungay.length() > 0 || this.denngay.length() > 0)
		{
			query = "select pk_seq as ctkmId, scheme, diengiai from ctkhuyenmai where pk_seq > 0 ";
			if(tungay.length() > 0)
				query += " and denngay >= '" + this.tungay + "'";
			if(this.denngay.length() > 0)
				query += " and denngay <= '" + this.denngay + "'";
		}
		else
		{
			String[] str = this.getDateTime().split("-");
			
			//mac dinh lay nhung ctkm trong thang neu khong cho tu ngay den ngay
			query = "select pk_seq as ctkmId, scheme, diengiai from ctkhuyenmai where substring(denngay, 0, 5) = '" + str[0] + "' and substring(denngay, 6, 2) = '" + str[1] + "'";
		}
		
		query += " and pk_seq not in (select distinct ctkmId from nhomctkhuyenmai_ctkm) order by pk_seq desc";
		System.out.println("Sql truy xuat du lieu: " + query);
		this.ctkmList = db.get(query);
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
	
	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
}
