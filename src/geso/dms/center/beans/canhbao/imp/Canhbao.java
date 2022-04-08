package geso.dms.center.beans.canhbao.imp;

import geso.dms.center.beans.canhbao.ICanhbao;
import geso.dms.center.db.sql.dbutils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Canhbao implements ICanhbao
{
	String userId;
	String congtyId;
	String id;
	
	String ma;
	String diengiai;
	String diachi;
	
	String trangthai;
	String msg;
	
	dbutils db;
	
	public Canhbao()
	{
		this.userId = "";
		this.id = "";
		this.ma = "";
		this.diengiai = "";
		this.diachi = "";

		this.trangthai = "1";
		this.msg = "";
		this.db = new dbutils();
	}
	
	public Canhbao(String id)
	{
		this.id = id;
		this.ma = "";
		this.diengiai = "";
		this.diachi = "";

		this.trangthai = "1";
		this.msg = "";
		
		this.db = new dbutils();
	}
	
	public String getId() 
	{
		return this.id;
	}

	public void setId(String Id) 
	{
		this.id = Id;
	}
	
	public String getUserId() 
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;	
	}

	public String getMsg() 
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}

	public void init() 
	{
		String query = "select ma, diengiai, trangthai from NganhHang where PK_SEQ = '" + this.id + "'";
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				while(rs.next())
				{
					this.ma =  rs.getString("ma");
					this.diengiai = rs.getString("diengiai");
					this.trangthai = rs.getString("trangthai");
				}
				rs.close();
			} 
			catch (Exception e) 
			{
				System.out.println("__Exception: " + e.getMessage());
			}
		}
		
	}
	
	public boolean createCanhbao()
	{	
		try 
		{
			if(this.ma.trim().length() <= 0)
			{
				this.msg = "Vui lòng nhập mã";
				return false;
			}
			
			db.getConnection().setAutoCommit(false);
			
			String query = "insert NganhHang(MA, diengiai, trangthai, ngaytao, nguoitao, ngaysua, nguoisua) " +
							"values(N'" + this.ma + "', N'" + this.diengiai + "', '" + this.trangthai + "', '" + this.getDateTime() + "', '" + this.userId + "', '" + this.getDateTime() + "', '" + this.userId + "')";
			
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới NganhHang " + query;
				db.getConnection().rollback();
				return false;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			this.msg = "Loi: " + e.getMessage();
			try 
			{
				db.getConnection().rollback();
			} 
			catch (SQLException e1) {}
			return false;
		}
		
		return true;
	}
	
	public boolean updateCanhbao() 
	{
		try 
		{
			if(this.ma.trim().length() <= 0)
			{
				this.msg = "Vui lòng nhập mã";
				return false;
			}
			
			
			db.getConnection().setAutoCommit(false);
			
			String query = "update NganhHang set MA = N'" + this.ma + "', diengiai = N'" + this.diengiai + "', trangthai = '" + this.trangthai + "', " +
							"ngaysua = '" + this.getDateTime() + "', nguoisua = '" + this.userId + "' where pk_seq = '" + this.id + "' ";

			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật NganhHang " + query;
				db.getConnection().rollback();
				return false;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			this.msg = "Loi: " + e.getMessage();
			try 
			{
				db.getConnection().rollback();
			} 
			catch (SQLException e1) {}
			return false;
		}
		
		return true;
	}
	
	public String getDiengiai()
	{
		return this.diengiai;
	}

	public void setDiengiai(String diengiai)
	{
		this.diengiai = diengiai;
	}

	
	public void DbClose() 
	{
		try 
		{
			this.db.shutDown();
		} 
		catch (Exception e) {}
		
	}
	
	public String getMa() 
	{
		return this.ma;
	}

	public void setMa(String ma) 
	{
		this.ma = ma;
	}

	
	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}


	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}
	
	public String getCongtyId() 
	{
		return this.congtyId;
	}

	public void setCongtyId(String congtyId) 
	{
		this.congtyId = congtyId;
	}

	public String getDiachi() 
	{
		return this.diachi;
	}

	public void setDiachi(String diachi) 
	{
		this.diachi = diachi;
	}
	
	
	

}
