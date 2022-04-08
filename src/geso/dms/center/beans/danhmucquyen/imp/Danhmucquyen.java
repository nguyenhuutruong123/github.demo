package geso.dms.center.beans.danhmucquyen.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;

import geso.dms.center.beans.danhmucquyen.IDanhmucquyen;
import geso.dms.center.db.sql.dbutils;

public class Danhmucquyen implements IDanhmucquyen
{
	
	String Ten;
	String DienGiai;
	String UserId;
	String Msg;
	String TrangThai;
	String Id;
	
	dbutils db ;
	
	public Danhmucquyen(String id)
	{
		this.Id=id;
		Ten = "";
		DienGiai = "";
		TrangThai = "0";
		Msg = "";
		this.loaiMenu="0";
		this.cbChot="";
		this.cbHienThi="";
		this.cbHuyChot="";
		this.cbSua="";
		this.cbThem="";
		this.cbXoa="";
		this.cbXem="";
		this.ungdungIds="";
		db= new dbutils();
		
/*		System.out.println("[INIT___]"+id);
		
		if(this.Id.length()>0)
		{
			init();
		}
		createRs();*/
		
	}
	
	
	
	public void setTen(String Ten)
	{
		this.Ten = Ten;
		
	}
	
	public String getTen()
	{
		
		return this.Ten;
	}
	
	public void setDiengiai(String DienGiai)
	{
		this.DienGiai = DienGiai;
	}
	
	public String getDiengiai()
	{
		return this.DienGiai;
	}
	
	
	public void init()
	{
		String sql = "select * from DanhMucQuyen where pk_seq ='" + this.Id + "'";
		ResultSet tb = db.get(sql);
		try
		{
			while (tb.next())
			{
				this.Ten = tb.getString("ten");
				this.DienGiai = tb.getString("diengiai");
				this.loaiMenu= 	tb.getString("LoaiMenu");
				this.TrangThai =	tb.getString("hoatdong");
			}
			if(tb!=null)tb.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		sql = "select isnull(hienThi,0) as hienThi, dmq_fk,ungdung_fk,isnull(them,'0') as them ,isnull(xoa,'0') as xoa,isnull(sua,'0') as sua, isnull(xem,'0') as xem,isnull(chot,'0') as chot,isnull(HuyChot,'0') as HuyChot  from nhomquyen where DMQ_fk ='" + this.Id + "'";
		
		System.out.println("[SQL__INIT]"+sql);
		
		 tb =db.get(sql);
		int i=0;
		try
    {
	    while(tb.next())
	    {
	    	if (i == 0)
	    	{
					ungdungIds += tb.getString("ungdung_fk");
					
					if(tb.getInt("Them")==1)
						cbThem += tb.getString("ungdung_fk");
					
					if(tb.getInt("Xoa")==1)
						cbXoa += tb.getString("ungdung_fk");
					
					if(tb.getInt("Sua")==1)
						cbSua += tb.getString("ungdung_fk");
					
					if(tb.getInt("Xem")==1)
						cbXem += tb.getString("ungdung_fk");
					
					if(tb.getInt("Chot")==1)
						cbChot += tb.getString("ungdung_fk");
					
					if(tb.getInt("HuyChot")==1)
						 cbHuyChot += tb.getString("ungdung_fk");
					
					if(tb.getInt("HienThi")==1)
						 cbHienThi += tb.getString("ungdung_fk");
					
	    	}
				else
				{
					ungdungIds += "," + tb.getString("ungdung_fk");
					
					if(tb.getInt("Them")==1)
						cbThem += "," + tb.getString("ungdung_fk");
					
					if(tb.getInt("Xoa")==1)
						cbXoa +=  "," +  tb.getString("ungdung_fk");
					
					if(tb.getInt("Sua")==1)
						cbSua += "," +   tb.getString("ungdung_fk");
					
					if(tb.getInt("Xem")==1)
						cbXem +=  "," +   tb.getString("ungdung_fk");
					
					if(tb.getInt("Chot")==1)
						cbChot += "," +   tb.getString("ungdung_fk");
					
					if(tb.getInt("HuyChot")==1)
						 cbHuyChot += "," +  tb.getString("ungdung_fk");
					
					if(tb.getInt("HienThi")==1)
						 cbHienThi +="," +   tb.getString("ungdung_fk");
				}
	    	i++;
	    }
	    if(tb!=null)tb.close();
    } catch (SQLException e)
    {
	    e.printStackTrace();
    }
		System.out.println("[cbHuyChot]"+this.cbHuyChot);
		
		createRs();
	}
	
	public ResultSet getUngdung()
	{
		return ungdungRs;
	}
	
	public void setUserId(String UserId)
	{
		this.UserId = UserId;
		
	}
	
	public String getUserId()
	{
		return this.UserId;
	}
	
	public boolean update(HttpServletRequest request)
	{
		try
		{
			db.getConnection().setAutoCommit(false);
			String sql = "update DanhMucQuyen set LoaiMENU=?,ten = ?,diengiai = ?,nguoisua =?,ngaysua =?,hoatdong =? where pk_seq = ? ";
//			 LoaiMENU='"+this.loaiMenu+"',ten = N'" + this.Ten + "',diengiai = N'" + this.DienGiai + "',nguoisua ='" + this.UserId + "',ngaysua ='" + getDateTime() + "',hoatdong ='" + this.TrangThai + "' where pk_seq = '" + this.Id + "'
			Object[] data;
			data= new Object[]   {  this.loaiMenu,this.Ten, this.DienGiai,this.UserId,
					getDateTime(),this.TrangThai,this.Id};
				 
//			db.update(sql);
			if (db.updateQueryByPreparedStatement(sql, data)!=1)
			{
				this.Msg = "Lỗi khi update dữ liệu" ;
				db.getConnection().rollback();
				return false;
			}
			sql = "delete  from nhomquyen where DMQ_fk = "+this.Id+" ";
//			data= new Object[]   { this.Id };
			if (!db.update(sql))
			{
				this.Msg = "Lỗi khi update dữ liệu";
				db.getConnection().rollback();
				return false;
				
			}
			
			String[] ungdungIds = request.getParameterValues("ungdungId");
			if (ungdungIds != null)
			{				
				for (int i = 0; i < ungdungIds.length; i++)
				{
						String ungdung = ungdungIds[i];
						String hienthi = this.cbHienThi.indexOf(ungdung)  <0 ? "0" : "1";
						String them = this.cbThem.indexOf(ungdung)  <0  ? "0" : "1";
						String xoa = this.cbXoa.indexOf(ungdung) <0  ? "0" : "1";
						String sua = this.cbSua.indexOf(ungdung)<0 ? "0" : "1";
						String xem =this.cbXem.indexOf(ungdung)<0 ? "0" : "1";
						String chot = this.cbChot.indexOf(ungdung)<0 ? "0" : "1";
						String huychot = this.cbHuyChot.indexOf(ungdung)<0? "0" : "1";
						sql = "insert into Nhomquyen(dmq_fk,ungdung_fk,them,xoa,sua,xem,chot,huychot,HienThi)  " +
							 " values(?,?,?,?,?,?,?,?,?)";
						data= new Object[]   {this.Id , ungdung , them , xoa , sua , xem , chot , huychot ,hienthi };
					System.out.println("[Nhomquyen]" + sql);
					
					if (db.updateQueryByPreparedStatement(sql, data)!=1)
					{
						this.Msg = "Lỗi khi update dữ liệu";
						db.getConnection().rollback();
						return false;
						
					}
				}
			}
			
			sql="delete from NhanVien_UngDung  "+ 
					"	where nhanvien_Fk   "+
					"	in   "+ 
					"	(  "+
					"		select nhanvien_fk from PHANQUYEN where dmq_Fk= '"+this.Id+"' "+
					"	) " ;
//						data= new Object[]   {this.Id };
						if (!db.update(sql))
						{
							this.Msg = "Lỗi khi update dữ liệu";
							db.getConnection().rollback();
							return false;
							
						}
					
				sql=
				"		insert into NhanVien_UngDung(UngDung_fk,NhanVien_fk)  "+
				"  select distinct nq.UngDung_fk,pq.Nhanvien_fk from NHOMQUYEN nq inner join phanquyen pq on pq.DMQ_fk=nq.DMQ_fk  "+
				"  inner join UNGDUNG ud on ud.PK_SEQ=nq.UngDung_fk  "+
			  " 	where nq.HienThi=1 and ud.TRANGTHAI=1 and pq.dmq_Fk ='"+this.Id+"' ";
				System.out.println(sql);
				if (!db.update(sql))
				{
					this.Msg = "Lỗi khi update dữ liệu";
					db.getConnection().rollback();
					return false;
				}
			
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return true;
		} catch (Exception er)
		{
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			this.Msg = er.toString();
			return false;
			
		}
	}
	
	public boolean save(HttpServletRequest request)
	{
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String sql = 
					
			"	insert into DANHMUCQUYEN(Ten,DienGiai,NguoiTao,NguoiSua,NgayTao,NgaySua,HoatDong,LoaiMenu)  "+
			"	values(?,?,?,?,?,?,?,?)  ";
			Object[] data;
			data= new Object[]   {  this.Ten,this.DienGiai,this.UserId,this.UserId,
					this.getDateTime(),this.getDateTime(),this.TrangThai,this.loaiMenu};
			
			if (db.updateQueryByPreparedStatement(sql, data)!=1)
			{
				this.Msg = "Xảy ra lỗi khi lưu dữ liệu";
				db.getConnection().rollback();
				return false;
			} 
			
		
			this.Id = db.getPk_seq();
//			if(rs!=null)rs.close();
			
			String[] ungdungIds = request.getParameterValues("ungdungId");
			if (ungdungIds != null)
			{				
				for (int i = 0; i < ungdungIds.length; i++)
				{
						String ungdung = ungdungIds[i];
						String hienthi = this.cbHienThi.indexOf(ungdung)  <0 ? "0" : "1";
						String them = this.cbThem.indexOf(ungdung)  <0  ? "0" : "1";
						String xoa = this.cbXoa.indexOf(ungdung) <0  ? "0" : "1";
						String sua = this.cbSua.indexOf(ungdung)<0 ? "0" : "1";
						String xem =this.cbXem.indexOf(ungdung)<0 ? "0" : "1";
						String chot = this.cbChot.indexOf(ungdung)<0 ? "0" : "1";
						String huychot = this.cbHuyChot.indexOf(ungdung)<0 ? "0" : "1";
						sql = "insert into Nhomquyen(dmq_fk,ungdung_fk,them,xoa,sua,xem,chot,huychot,HienThi)  " +
						" values(?,?,?,?,?,?,?,?,?)";
						
						data= new Object[]   {  this.Id , ungdung , them , xoa , sua , xem , chot , huychot ,hienthi};
						
						
					System.out.println("[Nhomquyen]" + sql);
					
					if (db.updateQueryByPreparedStatement(sql, data)!=1)
					{
						this.Msg = "Xảy ra lỗi khi lưu dữ liệu";

						db.getConnection().rollback();
//						geso.dms.center.util.Utility.rollback_throw_exception(db);
						return false;
					}
				}
			}
			
			sql="delete from NhanVien_UngDung  "+ 
					"	where nhanvien_Fk   "+
					"	in   "+ 
					"	(  "+
					"		select nhanvien_fk from PHANQUYEN where dmq_Fk='"+this.Id+"' "+
					"	) " ;
					if (!db.update(sql))
					{
						this.Msg = "Xảy ra lỗi khi lưu dữ liệu";

						db.getConnection().rollback();
						return false;
					}
					
				sql=
				"		insert into NhanVien_UngDung(UngDung_fk,NhanVien_fk)  "+
				"  select distinct nq.UngDung_fk,pq.Nhanvien_fk from NHOMQUYEN nq inner join phanquyen pq on pq.DMQ_fk=nq.DMQ_fk  "+
				"  inner join UNGDUNG ud on ud.PK_SEQ=nq.UngDung_fk  "+
			  " 	where nq.HienThi=1 and ud.TRANGTHAI=1 and pq.dmq_Fk ='"+this.Id+"' ";
				
				if (!db.update(sql))
				{
					this.Msg = "Xảy ra lỗi khi lưu dữ liệu";
					db.getConnection().rollback();
					return false;
				}
			
			
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return true;
		} catch (Exception er)
		{
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			er.printStackTrace();
			return false;
			
		}
	}
	
	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	public void setTrangthai(String TrangThai)
	{
		this.TrangThai = TrangThai;
	}
	
	public String getTrangThai()
	{
		return this.TrangThai;
	}
	
	public void setMsg(String Msg)
	{
		this.Msg = Msg;
		
	}
	
	public String getMsg()
	{
		return this.Msg;
	}
	
	public void setId(String Id)
	{
		this.Id = Id;
	}
	
	public String getId()
	{
		return this.Id;
	}
	
	public String getCbChot()
  {
  	return cbChot;
  }

	public void setCbChot(String cbChot)
  {
  	this.cbChot = cbChot;
  }

	public String getCbHienThi()
  {
  	return cbHienThi;
  }

	public void setCbHienThi(String cbHienThi)
  {
  	this.cbHienThi = cbHienThi;
  }

	public ResultSet getNhaphanphoi()
	{
		
		ResultSet rs = db.get("select a.pk_seq,a.ten,b.ten as khuvuc from nhaphanphoi a, khuvuc b where a.khuvuc_fk = b.pk_seq order by a.KHUVUC_FK");
		return rs;
	}
	
	@Override
	public void DBClose()
	{
		
		if (this.db != null)
		{
			db.shutDown();
		}
	}
	
	String cbThem,cbXoa,cbSua,cbChot,cbHienThi,cbXem,cbHuyChot;
	public String getCbThem()
  {
  	return cbThem;
  }

	public void setCbThem(String cbThem)
  {
  	this.cbThem = cbThem;
  }

	public String getCbXoa()
  {
  	return cbXoa;
  }

	public void setCbXoa(String cbXoa)
  {
  	this.cbXoa = cbXoa;
  }

	public String getCbSua()
  {
  	return cbSua;
  }

	public void setCbSua(String cbSua)
  {
  	this.cbSua = cbSua;
  }

	public String getCbXem()
  {
  	return cbXem;
  }

	public void setCbXem(String cbXem)
  {
  	this.cbXem = cbXem;
  }

	public String getCbHuyChot()
  {
  	return cbHuyChot;
  }

	public void setCbHuyChot(String cbHuyChot)
  {
  	this.cbHuyChot = cbHuyChot;
  }

	String loaiMenu;

	public String getLoaiMenu() {
		return loaiMenu;
	}

	public void setLoaiMenu(String loaiMenu) {
		this.loaiMenu = loaiMenu;
	}
	
	ResultSet ungdungRs;


	public ResultSet getUngdungRs()
  {
  	return ungdungRs;
  }

	public void setUngdungRs(ResultSet ungdungRs)
  {
  	this.ungdungRs = ungdungRs;
  }
	
	
String ungdungIds;
	
	@Override
	public String getUngdungIds()
	{
		return ungdungIds;
	}
	
	@Override
	public void setUngdungIds(String ungdungIds)
	{
		this.ungdungIds = ungdungIds;
	}



	@Override
  public void createRs()
  {
		String	query = 
				"	select   B.PK_SEQ as DanhMuc_FK,a.PK_SEQ as pk_Seq  , c.ten + ' ___  ' + b.ten as TENDANHMUC, a.ten , a.servlet,  "+
				"			a.parameters, c.sott as stt1, b.sott as stt2, a.sott         "+
				"from ungdung a inner join ungdung b on a.ungdung_fk = b.pk_seq "+             
				"	inner join ungdung c on b.ungdung_fk = c.pk_seq  "+            
				"where a.level = '3' and b.level = '2'  and a.loaiMenu='"+this.loaiMenu+"' and a.TrangThai=1 "+          
				"union all           "+
				"select c.PK_SEQ as DanhMuc_FK ,a.PK_SEQ ,  c.ten   as TENDANHMUC, a.ten, a.servlet, a.parameters, c.sott as stt1, a.sott as stt2, a.sott "+
				"	from ungdung a  inner join ungdung c on a.ungdung_fk = c.pk_seq  "+            
				"where a.level = '3' and c.level = '1'  and a.loaiMenu='"+this.loaiMenu+"'  and a.TrangThai=1  "+           
				"order by stt1 asc, stt2 asc, sott asc " ;	
		
		System.out.println("[UNGDUNG]"+query);
		
		this.ungdungRs=this.db.get(query);
	  
  }
	
}
