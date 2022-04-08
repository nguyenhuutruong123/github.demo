package geso.dms.center.beans.kho.imp;

import geso.dms.center.beans.kho.IKho;
import geso.dms.center.db.sql.*;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.nhat.replacement.TaskReplacement;


public class Kho implements IKho
{
	private static final long serialVersionUID = -9217977546733610415L;
	String id;
	String ten;
	String diengiai;
	String ngaytao;
	String ngaysua;
	String nguoitao;
	String nguoisua;
	String trangthai;
	String msg;
	dbutils db;
	
	public Kho(String[] param)
	{
		this.id = param[0];
		this.ten = param[1];	
		this.diengiai  = param[2];
		this.ngaytao = param[3];
		this.ngaysua = param[4];
		this.nguoitao = param[5];
		this.nguoisua = param[6];
		this.trangthai = param[7];	
		this.msg = "";
		this.db = new dbutils();
	}
	
	public Kho()
	{
		this.id = "";
		this.ten = "";
		this.diengiai  = "";
		this.ngaytao = "";
		this.ngaysua = "";
		this.nguoitao = "";
		this.nguoisua = "";
		this.trangthai = "";	
		this.msg = "";
		this.db = new dbutils();
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
	
	public String getTrangthai()
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai)
	{
		this.trangthai = trangthai;
	}


	public String getMessage()
	{
		return this.msg;
	}

	public void setMessage(String msg)
	{
		this.msg = msg;
	}
	
	public boolean saveNewKho(){
		// Insert data set into table "kho"
		try{
			this.db.getConnection().setAutoCommit(false);
			
					/*String query ="insert into kho values(N'" + this.ten + "','"+ this.ngaytao + "','" + this.ngaysua + "','" + this.nguoitao + "','" + this.nguoisua + "','" + this.trangthai + "',N'" + this.diengiai + "',1)";
					System.out.println("KHOOOOOOOOO " + query);
					if (!this.db.update(query))
					{
						this.db.getConnection().setAutoCommit(false);
						geso.dms.center.util.Utility.rollback_throw_exception(this.db);
						this.msg = "Khong the luu vao table 'Kho'" ;
						return false;			
					}*/
				
				String cauQuery = "insert into kho values(?,?,?,?,?,?,?,1)" ;
				Object[] dataQuery = {this.ten,this.ngaytao,this.ngaysua,this.nguoitao,this.nguoisua,this.trangthai,this.diengiai}; 
				if (this.db.updateQueryByPreparedStatement(cauQuery,dataQuery)!= 1) {
					dbutils.viewQuery(cauQuery,dataQuery);
					this.msg = "Error At : b676a680-572e-4134-ae4d-1fbcd3460757";
					this.db.getConnection().rollback();
					return false;
				}

				
				String query = "select IDENT_CURRENT('kho')as khoId";
				ResultSet rs = this.db.get(query);
			
				rs.next();
				String khoId = rs.getString("khoId");
				if(khoId==null){
					this.db.getConnection().setAutoCommit(false);
					geso.dms.center.util.Utility.rollback_throw_exception(this.db);
					this.msg="Ban Khong The Cap Nhat Kho ,Loi Dong Lenh Sau :" + query;
					return false;
				}
				
				query = "  insert into nhapp_kho  " +
						" select distinct '"+khoId+"' as kho_fk,a.pk_seq as npp_fk, b.pk_seq as sanpham_fk, c.kbh_fk as kbh_fk,0,0,0,0 from nhaphanphoi a," +
						"  sanpham b, nhapp_kbh c, nhapp_nhacc_donvikd d, nhacungcap_dvkd e where a.pk_seq = c.npp_fk " +
						"  and a.pk_seq= d.npp_fk and d.ncc_dvkd_fk = e.pk_seq and e.dvkd_fk=b.dvkd_fk " +
						"  and  a.trangthai=1 and a.priandsecond=0 and b.trangthai=1 ";
				System.out.println("Truy Van "+query);
				if(!this.db.update(query)){
					this.db.getConnection().setAutoCommit(false);
					geso.dms.center.util.Utility.rollback_throw_exception(this.db);
					this.msg="Ban Khong The Cap Nhat Kho ,Loi Dong Lenh Sau :" + query;
					return false; 
				}
				
				/*cauQuery = "insert into nhapp_kho select distinct ? as kho_fk,a.pk_seq as npp_fk, b.pk_seq as sanpham_fk, c.kbh_fk as kbh_fk,0,0,0,0 from nhaphanphoi a, sanpham b, nhapp_kbh c, nhapp_nhacc_donvikd d, nhacungcap_dvkd e where a.pk_seq = c.npp_fk and a.pk_seq= d.npp_fk and d.ncc_dvkd_fk = e.pk_seq and e.dvkd_fk=b.dvkd_fk and a.trangthai=1 and a.priandsecond=0 and b.trangthai=1" ;
				Object[] dataQuery1 = {khoId}; 
				if (this.db.updateQueryByPreparedStatement(cauQuery,dataQuery1)!= 1) {
					dbutils.viewQuery(cauQuery,dataQuery1);
					this.msg = "Error At : dd95c744-540a-42bd-8751-0f310c046a44";
					this.db.getConnection().rollback();
					return false;
				}*/
 
				/*rs = this.db.get(query);
			
				while(rs.next()){
					query = "insert into nhapp_kho values('"+ khoId +"', '" + rs.getString("npp_fk") + "','" + rs.getString("sanpham_fk") + "','0','0','0','0','" + rs.getString("kbh_fk") + "')";
					
					if(!this.db.update(query)){
						geso.dms.center.util.Utility.rollback_throw_exception(this.db);
						this.msg="Ban Khong The Cap Nhat Kho ,Loi Dong Lenh Sau :" + query;
						return false;
						
					}
					
				}
				if(rs!=null){
					rs.close();
				}*/
				if(rs!=null) rs.close();				
					this.db.getConnection().commit();
					this.db.getConnection().setAutoCommit(true);
					if(db!=null) db.shutDown();
				return true;
				
	        	
		}catch(Exception e){
			geso.dms.center.util.Utility.rollback_throw_exception(this.db);
			this.msg="Ban Khong The Cap Nhat Kho ,Loi Dong Lenh Sau "+e.toString();
			return false;
		}
				
		
	}
	
	public boolean UpdateKho(){
		try{
 
			this.db.getConnection().setAutoCommit(false);
			// Update table "Kho"
			/*String query = "update kho set ten =N'" + this.ten + "',  ngaysua = '" + this.ngaysua + "',  nguoisua = '" + this.nguoisua + "', trangthai = '" + this.trangthai + "', diengiai = N'" + this.diengiai + "' where pk_seq = '" + this.id + "'" ;
	
			if(!this.db.update(query)){
				geso.dms.center.util.Utility.rollback_throw_exception(this.db);
				this.msg = "Khong the cap nhat table 'Kho'";
				return false; 
			}*/
			
			String cauQuery = "update kho set ten =?, ngaysua = ?, nguoisua = ?, trangthai = ?, diengiai = ? where pk_seq = ?" ;
			Object[] dataQuery = {this.ten,this.ngaysua,this.nguoisua,this.trangthai,this.diengiai,this.id}; 
			if (this.db.updateQueryByPreparedStatement(cauQuery,dataQuery)!= 1) {
				dbutils.viewQuery(cauQuery,dataQuery);
				this.msg = "Error At : 22379457-cb98-4cbb-9de0-975520b101d1";
				this.db.getConnection().rollback();
				return false;
			}
			
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
			
		}catch(Exception e){ 
			geso.dms.center.util.Utility.rollback_throw_exception(this.db);
			this.msg="Ban Khong The Cap Nhat Kho";
			return false;
		}
		return true;
	}

	@Override
	public void DBClose() 
	{		
		if(db!=null){
			db.shutDown();
		}
	}
	
	public static void main(String[] args) {
		TaskReplacement t = new TaskReplacement();
		String inl = "update kho set ten =N'\" + this.ten + \"',  ngaysua = '\" + this.ngaysua + \"',  nguoisua = '\" + this.nguoisua + \"', trangthai = '\" + this.trangthai + \"', diengiai = N'\" + this.diengiai + \"' where pk_seq = '\" + this.id + \"'";
		System.out.println(t.taoQuery(inl));
 
	}
		
		
}


