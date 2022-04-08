package geso.dms.center.beans.chuyennpp.imp;

import geso.dms.center.beans.chuyennpp.IChuyenNpp;
import geso.dms.center.beans.stockintransit.imp.Stockintransit;
import geso.dms.center.db.sql.dbutils;

import java.sql.ResultSet;
import java.sql.SQLException;


public class ChuyenNpp extends Stockintransit implements IChuyenNpp {

	String id, nppIdFrom, nppIdTo, msg,ngayks;
	dbutils db;

	public ChuyenNpp()
	{
		this.id = "";
		this.nppIdFrom = "";
		this.nppIdTo = "";
		this.msg = "";
		this.ngayks="";
		this.db = new dbutils();
	}

	public String getNppIdFrom() {

		return this.nppIdFrom;
	}

	public void setNppIdFrom(String nppIdFrom) {
		this.nppIdFrom = nppIdFrom;
	}

	public String getNppIdTo() {

		return this.nppIdTo;
	}

	public void setNppIdTo(String nppIdTo) {
		this.nppIdTo = nppIdTo;
	}

	public String getMsg() {

		return this.msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getId() {

		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean TransferData() 
	{		
		try {		 
			this.db.getConnection().setAutoCommit(false);
			String query="insert into chuyennpp(npp_fk_from ,npp_fk_to,nguoitao,ngaytao)" +
					" values('"+this.nppIdFrom+"','"+this.nppIdTo+"','"+this.getuserId()+"',dbo.GetLocalDate(DEFAULT))";
			
			if(!this.db.update(query))
			{
				this.db.getConnection().rollback();
				this.msg="Chuyen khach hang bi loi "+query;
				return false;
			}
			
			query="select scope_identity()";
			ResultSet rs=this.db.get(query);
			if(rs!=null)
			{
				rs.next();
				this.id=rs.getString(1);
				rs.close();
			}
			query="insert into chuyennpp_khachhang(chuyennpp_fk,npp_fk_from,khachhang_fk,smartId)" +
					" select '"+this.id+"',npp_fk,pk_Seq as khid,smartId " +
					" from khachhang " +
					"where npp_fk='"+this.nppIdFrom+"' ";

			if(!this.db.update(query))
			{
				this.db.getConnection().rollback();
				this.msg="Chuyen khach hang bi loi "+query;
				return false;
			}
			
			query="insert into chuyennpp_daidienkinhdoanh(chuyennpp_fk,npp_fk_from,ddkd_fk)" +
					" select '"+this.id+"',npp_fk,pk_Seq as ddkd_fk " +
					" from daidienkinhdoanh " +
					"where npp_fk='"+this.nppIdFrom+"' ";
			
			if(!this.db.update(query))
			{
				this.db.getConnection().rollback();
				this.msg="Chuyen khach hang bi loi "+query;
				return false;
			}
			
			query="insert into chuyennpp_tuyenbanhang(chuyennpp_fk,npp_fk_from,tbh_fk)" +
					" select '"+this.id+"',npp_fk,pk_Seq as tbh_fk " +
					" from tuyenbanhang " +
					"where npp_fk='"+this.nppIdFrom+"' ";
			
			if(!this.db.update(query))
			{
				this.db.getConnection().rollback();
				this.msg="Chuyen khach hang bi loi "+query;
				return false;
			}
			
			
			
				query="update khachhang set npp_fk='"+this.nppIdTo+"' where npp_fk='"+this.nppIdFrom+"'  ";
				if(!this.db.update(query))
				{
					this.db.getConnection().rollback();
					this.msg="Chuyen khach hang bi loi "+query;
					return false;
				}
					
			
				query="update tuyenbanhang set npp_fk='"+this.nppIdTo+"' where npp_fk='"+this.nppIdFrom+"'";
				if(!this.db.update(query))
				{
					this.db.getConnection().rollback();
					this.msg="Chuyen khach hang bi loi "+query;
					return false;
				}
			
				query="update daidienkinhdoanh set npp_fk='"+this.nppIdTo+"' where npp_fk='"+this.nppIdFrom+"'";
				if(!this.db.update(query))
				{
					this.db.getConnection().rollback();
					this.msg="Chuyen khach hang bi loi "+query;
					return false;
				}
			
			
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return true;
	}

	public boolean Revert()
	{
//		update KHACHHANG set SMARTID=a.smartId,NPP_FK=a.npp_fk_from
//				from 
//				(
//					select * from chuyennpp_khachhang
//					where NPP_FK_from=100177 
//				)a inner join KHACHHANG b on 
//				a.khachhang_fk=b.PK_SEQ
//
//				update DAIDIENKINHDOANH set NPP_FK=a.npp_fk_from
//				from 
//				(
//					select * from chuyennpp_daidienkinhdoanh
//					where NPP_FK_from=100177 
//				)a inner join DAIDIENKINHDOANH b on 
//				a.ddkd_fk=b.PK_SEQ
//
//				update TUYENBANHANG set NPP_FK=a.npp_fk_from
//				from 
//				(
//					select * from chuyennpp_tuyenbanhang
//					where NPP_FK_from=100177 
//				)a inner join TUYENBANHANG b on 
//				a.tbh_fk=b.PK_SEQ
		
		return true;
	}
	
	
	public void closeDB() 
	{
		
		
	}

	
	public String getNgayKs() {
	
		return this.ngayks;
	}

	
	public void setNgayKs(String ngayKs) {
	this.ngayks=ngayKs;
		
	}
	
}
