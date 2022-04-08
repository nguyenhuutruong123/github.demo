package geso.dms.distributor.beans.chuyenkhonv.imp;

import geso.dms.distributor.beans.chuyenkhonv.IChuyenkho;
import geso.dms.distributor.db.sql.dbutils;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.connector.Request;

public class Chuyenkho implements IChuyenkho, Serializable
{
	private static final long serialVersionUID = -9217977546733610214L;

	String userId;
	String id;
	String ngaychuyen;
	String trangthai;
	
	String msg;
	
	ResultSet nvbhRs;
	String nvbhId;
	ResultSet khoRs;
	String khoId;
	
	ResultSet spRs;
	String spIds;
	Hashtable<String, Integer> sp_sl;

	String nppId;
	String nppTen;
	String sitecode;
		
	dbutils db;
	
	public Chuyenkho()
	{
		this.id = "";
		this.ngaychuyen = "";
		this.nvbhId = "";
		this.khoId = "";
		this.spIds = "";
		this.trangthai = "";
		this.msg = "";
		
		sp_sl = new Hashtable<String, Integer>();
		db = new dbutils();
	}
	
	public Chuyenkho(String id)
	{
		this.id = id;
		this.ngaychuyen = "";
		this.nvbhId = "";
		this.khoId = "";
		this.spIds = "";
		this.trangthai = "";
		this.msg = "";
		
		sp_sl = new Hashtable<String, Integer>();
		db = new dbutils();
	}
	
	public String getUserId()
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;
	}
	
	public String getId() 
	{
		return this.id;
	}
	
	public void setId(String id) 
	{
		this.id = id;
	}
	
	public String getMessage() 
	{
		return this.msg;
	}
	
	public void setMessage(String msg) 
	{
		this.msg = msg;
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
	
	public String getSitecode() 
	{
		return this.sitecode;
	}

	public void setSitecode(String sitecode) 
	{
		this.sitecode = sitecode;
	}

	public String getNgaychuyen() 
	{
		return this.ngaychuyen;
	}

	public void setNgaychuyen(String ngaychuyen) 
	{
		this.ngaychuyen = ngaychuyen;
	}

	public ResultSet getNvBanhang() 
	{
		return this.nvbhRs;
	}

	public void setNvBanhang(ResultSet nvbanhang)
	{
		this.nvbhRs = nvbanhang;
	}

	public String getNvbhId() 
	{
		return this.nvbhId;
	}

	public void setNvbhId(String nvbhId)
	{
		this.nvbhId = nvbhId;
	}

	public ResultSet getKhoRs() 
	{
		return this.khoRs;
	}

	public void setKhoRs(ResultSet khoRs) 
	{
		this.khoRs = khoRs;
	}

	public String getKhoId() 
	{
		return this.khoId;
	}

	public void setKhoId(String khoId) 
	{
		this.khoId = khoId;
	}

	public ResultSet getSpRs() 
	{
		return this.spRs;
	}

	public void setSpRs(ResultSet spRs) 
	{
		this.spRs = spRs;
	}

	public String getSpIds()
	{
		return this.spIds;
	}

	public void setSpIds(String spIds)
	{
		this.spIds = spIds;
	}

	public boolean CreateCk( HttpServletRequest request )
	{
		if(this.ngaychuyen.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn ngày chuyển";
			return false;
		}
		if(!ngaychuyen.equals(getDateTime()))
		{
			this.msg = "Ngày chuyển phải bằng ngày hiện tại";
			return false;
		}
		if(this.nvbhId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn nhân viên bán hàng";
			return false;
		}
		
		if(this.khoId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn kho chuyển";
			return false;
		}
		
		try 
		{
			//Check co sp nao duoc nhap so luong khong
			boolean flag = false;
			Enumeration<String> keys = this.sp_sl.keys();
			while(keys.hasMoreElements())
			{
				String key = keys.nextElement();
				if(this.sp_sl.get(key) > 0)
					flag = true;
			}
			
			if(!flag)
			{
				this.msg = "Không có sản phẩm nào để chuyển kho";
				return false;
			}
			
			db.getConnection().setAutoCommit(false);
			
			String query = "insert chuyenkhoNV(nvbh_fk, kho_fk, ngaychuyen, trangthai, npp_fk, ngaytao, nguoitao, ngaysua, nguoisua) " +
							"values('" + this.nvbhId + "', '" + this.khoId + "', '" + this.ngaychuyen + "', '0', '" + this.nppId + "', '" + getDateTime() + "', '" + this.userId + "', '" + getDateTime() + "', '" + this.userId + "')";
			
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới chuyển kho: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			
			String ckId = "";
			query = "select IDENT_CURRENT('chuyenkhoNV') as ckId";
			ResultSet ckCurrent = db.get(query);
			if(ckCurrent != null)
			{
				if(ckCurrent.next())
				{
					ckId = ckCurrent.getString("ckId");
					ckCurrent.close();
				}
			}
			
			String[] spIds = request.getParameterValues("spIds");
			String[] spSOLUONG = request.getParameterValues("soluong");
			String[] spTEN = request.getParameterValues("spTen");
			
			for(int i = 0; i < spIds.length; i++ )
			{
				if(spSOLUONG[i].trim().length() > 0)
				{
					query = "insert chuyenkhoNV_sanpham(chuyenkhoNV_fk, sanpham_fk, soluong) " +
							"values('" + ckId + "', '" + spIds[i] + "', '" + spSOLUONG[i].trim().replaceAll(",", "") + "')";
					if(!db.update(query))
					{
						this.msg = "Không thể tạo mới chuyển kho: " + query;
						db.getConnection().rollback();
						return false;
					}
					//CHECK TON KHO
					double avai = 0;
					query = "select available from NHAPP_KHO  " +
							"where npp_fk = '" + this.nppId + "' and kho_fk = '" + this.khoId + "' and kbh_fk = '100025' and sanpham_fk = '" + spIds[i] + "'  ";
					ResultSet rsCHECK = db.get(query);
					if(rsCHECK.next())
					{
						avai = rsCHECK.getDouble("available");
					}
					rsCHECK.close();
					
					if(avai < Double.parseDouble(spSOLUONG[i].trim().replaceAll(",", "")) )
					{
						this.msg = "Tồn kho của sản phẩm ( " + spTEN[i] + " ) =, số lượng ( " + spSOLUONG[i] + " ) còn tối đa ( " + avai + " ) ";
						db.getConnection().rollback();
						return false;
					}
					
					
			
			
					
					query = "update nhapp_kho set available = available - '" + spSOLUONG[i].trim().replaceAll(",", "") + "', booked = booked + '" + spSOLUONG[i].trim().replaceAll(",", "") + "' " +
							"where npp_fk = '" + this.nppId + "' and kho_fk = '" + this.khoId + "' and kbh_fk = '100025' and sanpham_fk = '" + spIds[i] + "' ";
							
					if(!db.update(query))
					{
						this.msg = "Không thể tạo mới chuyển kho: " + query;
						db.getConnection().rollback();
						return false;
					}
					
					
				}
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e)
		{
			this.msg = "Vui long thu lai sau";
			System.out.println("Exception; " + e.getMessage());
			
			return false;
		}
		
		return true;
	}

	public boolean UpdateCk( HttpServletRequest request ) 
	{
		if(this.ngaychuyen.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn ngày chuyển";
			return false;
		}
		
		if(this.nvbhId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn nhân viên bán hàng";
			return false;
		}
		if(!ngaychuyen.equals(getDateTime()))
		{
			this.msg = "Ngày chuyển phải bằng ngày hiện tại";
			return false;
		}
		if(this.khoId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn kho chuyển";
			return false;
		}
		
		try 
		{
			boolean flag = false;
			Enumeration<String> keys = this.sp_sl.keys();
			while(keys.hasMoreElements())
			{
				String key = keys.nextElement();
				if(this.sp_sl.get(key) > 0)
					flag = true;
			}
			
			if(!flag)
			{
				this.msg = "Không có sản phẩm nào để chuyển kho";
				return false;
			}
			
			db.getConnection().setAutoCommit(false);
			
			//Cap nhat lai kho NPP truoc
			String query = "select a.npp_fk, a.kho_fk, b.sanpham_fk, b.soluong " +
						"from chuyenkhoNV a inner join chuyenkhoNV_sanpham b on a.pk_seq = b.chuyenkhoNV_fk where a.pk_seq = '" + this.id + "'";
			System.out.println("QR SP: "+query );
			ResultSet rsUpdate = db.get(query);
			if(rsUpdate != null)
			{
				while(rsUpdate.next())
				{
					
					query = "update nhapp_kho set available = available + '" + rsUpdate.getInt("soluong") + "', booked = booked - '" + rsUpdate.getInt("soluong") + "' " +
							"where npp_fk = '" + rsUpdate.getString("npp_fk") + "' and kho_fk = '" + rsUpdate.getString("kho_fk") + "' and kbh_fk = '100025' and sanpham_fk = '" + rsUpdate.getString("sanpham_fk") + "' ";
					System.out.println("Nha KHo: "+query );
					if(!db.update(query))
					{
						this.msg = "Không thể tạo mới chuyển kho: " + query;
						db.getConnection().rollback();
						return false;
					}
				}
				rsUpdate.close();
			}
			

			query = "update chuyenkhoNV set nvbh_fk = '" + this.nvbhId + "', kho_fk = '" + this.khoId + "', ngaychuyen = '" + this.ngaychuyen + "', " +
					"ngaysua = '" + getDateTime() + "', nguoisua = '" + this.userId + "' where pk_seq = '" + this.id + "' ";
					
			if(!db.update(query))
			{
				this.msg = "Không thể tạo cập nhật chuyển kho: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete chuyenkhoNV_sanpham where chuyenkhoNV_fk = '" + this.id + "'";	
			if(!db.update(query))
			{
				this.msg = "Không thể tạo cập nhật chuyển kho: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			
			
			String[] spIds = request.getParameterValues("spIds");
			String[] spSOLUONG = request.getParameterValues("soluong");
			String[] spTEN = request.getParameterValues("spTen");
			
			for(int i = 0; i < spIds.length; i++ )
			{
				if(spSOLUONG[i].trim().length() > 0)
				{
					query = "insert chuyenkhoNV_sanpham(chuyenkhoNV_fk, sanpham_fk, soluong) " +
							"values('" + this.id + "', '" + spIds[i] + "', '" + spSOLUONG[i].trim().replaceAll(",", "") + "')";
					if(!db.update(query))
					{
						this.msg = "Không thể tạo mới chuyển kho: " + query;
						db.getConnection().rollback();
						return false;
					}
					
				
					
							//CHECK TON KHO
							double avai = 0;
							query = "select available from NHAPP_KHO  " +
									"where npp_fk = '" + this.nppId + "' and kho_fk = '" + this.khoId + "' and kbh_fk = '100025' and sanpham_fk = '" + spIds[i] + "'  ";
							ResultSet rsCHECK = db.get(query);
							if(rsCHECK.next())
							{
								avai = rsCHECK.getDouble("available");
							}
							rsCHECK.close();
							
							if(avai < Double.parseDouble(spSOLUONG[i].trim().replaceAll(",", "")) )
							{
								this.msg = "Tồn kho của sản phẩm ( " + spTEN[i] + " ) =, số lượng ( " + spSOLUONG[i] + " ) còn tối đa ( " + avai + " ) ";
								db.getConnection().rollback();
								return false;
							}
							
							
					
					
							
							query = "update nhapp_kho set available = available - '" + spSOLUONG[i].trim().replaceAll(",", "") + "', booked = booked + '" + spSOLUONG[i].trim().replaceAll(",", "") + "' " +
									"where npp_fk = '" + this.nppId + "' and kho_fk = '" + this.khoId + "' and kbh_fk = '100025' and sanpham_fk = '" + spIds[i] + "' ";
									
							if(!db.update(query))
							{
								this.msg = "Không thể tạo mới chuyển kho: " + query;
								db.getConnection().rollback();
								return false;
							}
						
				
					
					
				}
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e)
		{
			this.msg = "Vui long thu lai sau";
			System.out.println("Exception; " + e.getMessage());
			return false;
		}
		
		return true;
	}

	public boolean ChotCk() 
	{
		try 
		{
			String trangthai = "",ngaychuyen = "";
			String	query = "select ngaychuyen,trangthai,npp_fk from chuyenkhonv where pk_seq = "+this.id;
			System.out.println("QR: "+query);
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				if(rs.next())
				{
					ngaychuyen = rs.getString("ngaychuyen");
					trangthai = rs.getString("trangthai");
					this.nppId = rs.getString("npp_fk");
				}
			}
			if(!trangthai.equals("0"))
			{
				this.msg = "Phiếu chuyển kho đã chốt rồi !";
				return false;
			}
		/*	query = "select max(ngayks)  as ngayks from khoasongay where npp_fk  = (select npp_fk from chuyenkhonv where pk_seq = "+this.id+")"
					+ " having max(ngayks) >= '"+ngaychuyen+"'  " ;
			System.out.println("QR: "+query);
			 rs = db.get(query);
			 if(rs != null)
			 {
				 if(rs.next())
				 {
					 	this.msg = "Không chốt được phiếu chuyển kho NVBH trước ngày khóa sổ !";
						return false;
				 }
			 }*/
			 
			 
			
			if(ngaychuyen.length() <= 0)
			{
				this.msg = "Không có ngày chuyển kho !";
				return false;
			}
			 query = "select MAX(NGAYKS) as ngayks from KHOASONGAY where NPP_FK = '"+this.nppId+"'";
				rs = db.get(query);

				if(rs.next())
				{
					String ngayks = rs.getString("ngayks")==null?"":rs.getString("ngayks");
					if(ngayks.length() <= 0)
					{
						this.msg = "Nhà phân phối chưa khóa sổ hoặc ngày điều chỉnh phải lớn hơn ngày khóa sổ";
					
						return false;
					}
					if(ngayks.compareTo(ngaychuyen) >= 0)
					{
						this.msg = "Không thể nhận hàng trước ngày khóa sổ hoặc không có ngày khóa sổ!";
						return false;
					}
				}

			
			query = "select * from [dbo].[ufn_Check_XNT_KhiChotCK]("+nppId+",'"+ngaychuyen+"',"+this.id+")";
			System.out.println("CHECK XNT: "+query);
			ResultSet rsXNT  = db.get(query);
			if(rsXNT != null)
			{
				String loi = "";
				while(rsXNT.next())
				{
					loi+= "\n Phiếu chuyển kho "+this.id+". Sản phẩm  "+rsXNT.getString("tensp")+". Không đủ XNT trong tháng ("+ngaychuyen.substring(0,7)+"). Vui lòng điều chỉnh số lượng !";
				}
				if(loi.length() > 0)
				{
					msg = loi;
				
					rsXNT.close();
					System.out.println("Sai XNT : "+query);
					return false; 
				}
				
			}
			db.getConnection().setAutoCommit(false);
			
			//Cap nhat lai kho NPP truoc
			 query = "select a.npp_fk, a.nvbh_fk, a.kho_fk, b.sanpham_fk, b.soluong  " +
						  "from chuyenkhoNV a inner join chuyenkhoNV_sanpham b on a.pk_seq = b.chuyenkhoNV_fk where a.pk_seq = '" + this.id + "' and trangthai = 0 ";
			
			ResultSet rsUpdate = db.get(query);
			if(rsUpdate != null)
			{
				while(rsUpdate.next())
				{
					int soluong = rsUpdate.getInt("soluong");
					String spId = rsUpdate.getString("sanpham_fk");
					this.nppId = rsUpdate.getString("npp_fk");
					this.nvbhId = rsUpdate.getString("nvbh_fk");
					this.khoId = rsUpdate.getString("kho_fk");
				
					
					query = "update nhapp_kho set soluong = soluong - '" + rsUpdate.getInt("soluong") + "', booked = booked - '" + rsUpdate.getInt("soluong") + "' " +
							"where npp_fk = '" + rsUpdate.getString("npp_fk") + "' and kho_fk = '" + rsUpdate.getString("kho_fk") + "' and kbh_fk = '100025' and sanpham_fk = '" + rsUpdate.getString("sanpham_fk") + "' ";
					
					if(!db.update(query))
					{
						this.msg = "Không thể chốt chuyển kho: " + query;
						db.getConnection().rollback();
						return false;
					}
					
					
					
					
					//Chuyen hang sang kho NVBH
					query = "select count(sanpham_fk) as sodong from nvbh_kho " +
								"where nvbh_fk = '" + rsUpdate.getString("nvbh_fk")  + "' and kho_fk = '" + rsUpdate.getString("kho_fk") + "' and kbh_fk = '100025' and sanpham_fk = '" + rsUpdate.getString("sanpham_fk") + "' ";
			
					ResultSet rsSp = db.get(query);
					int sodong = 0;
					
					if(rsSp.next())
					{
						sodong = rsSp.getInt("sodong");
						rsSp.close();
					}
					
					if(sodong > 0) //Da co. Update
					{
						query = "update nvbh_kho set soluong = soluong + '" + rsUpdate.getInt("soluong") + "', available = available + '" + rsUpdate.getInt("soluong") + "' " +
								"where nvbh_fk = '" + this.nvbhId + "' and kho_fk = '" + this.khoId + "' and kbh_fk = '100025' and sanpham_fk = '" + rsUpdate.getString("sanpham_fk") + "' ";
								
						if(!db.update(query))
						{
							this.msg = "Không thể tạo mới chuyển kho: " + query;
							db.getConnection().rollback();
							return false;
						}
						
					}
					else //Tao moi
					{
						query = "insert nvbh_kho(npp_fk, nvbh_fk, kho_fk, kbh_fk, sanpham_fk, soluong, booked, available) " +
								"values('" + this.nppId + "', '" + this.nvbhId + "', '" + this.khoId + "', '100025', '" + rsUpdate.getString("sanpham_fk") + "', '" + rsUpdate.getInt("soluong") + "', '0', '" + rsUpdate.getInt("soluong") + "')";
						
						if(!db.update(query))
						{
							this.msg = "Không thể tạo mới chuyển kho: " + query;
							db.getConnection().rollback();
							return false;
						}
						
					}
					
					
				
				
					
				
					
				}
				rsUpdate.close();
			}
			
			String ngaychot="";
			ngaychot=getNgayKs_CongMot(nppId, db);
			System.out.println("[NGAYCHOT]"+ngaychot);
			if(ngaychot.length()<=0)
			{
				this.msg = "Không thể xác định ngày KS của npp : " + query;
				db.getConnection().rollback();
				return false;
			}
			
			
			query = "update chuyenkhoNV set trangthai = '1', ngaysua = '" + getDateTime() + "', nguoisua = '" + this.userId + "' where pk_seq = '" + this.id + "' and trangthai = 0 ";
					
			if(!db.update(query))
			{
				this.msg = "Không thể tạo cập nhật chuyển kho: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e)
		{
			this.msg = "Vui long thu lai sau";
			e.printStackTrace();
			System.out.println("Exception; " + e.getMessage());
			return false;
		}
		
		return true;
	}
	
	public String getNgayKs_CongMot(String nppId,dbutils db )
	{
		String query=
			"	SELECT "+
			"		( select convert(varchar(10), DATEADD(dd, 1, ( select MAX(ngayks) from KHOASONGAY where NPP_FK = a.PK_SEQ )), 120) ) as ngayKS,A.pk_Seq "+
			"	FROM NHAPHANPHOI A "+
			"	where A.PK_sEQ='"+nppId+"' ";
		String ngayKsCongMot="";
		
		System.out.println("[Query]"+query);
		
		ResultSet rs =db.get(query);
		try
    {
	    while(rs.next())
	    {
	    	ngayKsCongMot=rs.getString("ngayKS");
	    }
	    if(rs!=null)rs.close();
    } catch (Exception e)
    {
	    e.printStackTrace();
    }
		return ngayKsCongMot;
	}
	
	
	public boolean DeleteCk() 
	{
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			//Cap nhat lai kho NPP truoc
			String query = "select a.npp_fk, a.kho_fk, b.sanpham_fk, b.soluong " +
						"from chuyenkhoNV a inner join chuyenkhoNV_sanpham b on a.pk_seq = b.chuyenkhoNV_fk where a.pk_seq = '" + this.id + "' and a.trangthai = '0' ";
			
			ResultSet rsUpdate = db.get(query);
			if(rsUpdate != null)
			{
				while(rsUpdate.next())
				{
					
					query = "update nhapp_kho set available = available + '" + rsUpdate.getInt("soluong") + "', booked = booked - '" + rsUpdate.getInt("soluong") + "' " +
							"where npp_fk = '" + rsUpdate.getString("npp_fk") + "' and kho_fk = '" + rsUpdate.getString("kho_fk") + "' and kbh_fk = '100025' and sanpham_fk = '" + rsUpdate.getString("sanpham_fk") + "' ";
					
					if(!db.update(query))
					{
						this.msg = "Không thể xóa  chuyển kho: " + query;
						db.getConnection().rollback();
						return false;
					}
				}
				rsUpdate.close();
			}else
			{
				this.msg = "Lỗi không  xóa chuyển kho: " + query;
				db.getConnection().rollback();
			}
			
			query = "update chuyenkhoNV set trangthai = '2', ngaysua = '" + getDateTime() + "', nguoisua = '" + this.userId + "' where pk_seq = '" + this.id + "' ";
					
			if(!db.update(query))
			{
				this.msg = "Không thể xóa chuyển kho: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e)
		{
			this.msg = "Vui long thu lai sau";
			System.out.println("Exception; " + e.getMessage());
			return false;
		}
		
		return true;
	}
	
	public void createRS() 
	{
		this.getNppInfo();
		
		String query = "select pk_seq, ten + ', ' + diengiai as khoTen from kho where trangthai = '1' " +
							"and  pk_seq in ( select distinct kho_fk from nhapp_kho where npp_fk = '" + this.nppId + "' )  order by pk_seq asc";
		this.khoRs = db.get(query);
		
		query = "select pk_seq as nvbhId, ten as nvbhTen from daidienkinhdoanh where trangthai = '1' and npp_fk = '" + this.nppId + "' ";
		System.out.println("1.Lay NVBH: " + query);
		
		this.nvbhRs = db.get(query);
		
		if(this.nvbhId.trim().length() > 0 && this.khoId.trim().length() > 0)
		{
			if(this.id.trim().length() <= 0)
			{
				query = "select b.pk_seq as spId, b.ma as spMa, b.ten as spTen, isnull(c.donvi, 'NA') as donvi, a.available, " +
						" isnull(	(select available from nvbh_kho where nvbh_fk = '" + this.nvbhId + "' and sanpham_fk = b.pk_seq and kbh_fk = '100025'  and kho_fk = '"+this.khoId+"' ), 0) as avaiNVBH   " +
						"from nhapp_kho a inner join sanpham b on a.sanpham_fk = b.pk_seq inner join donvidoluong c on b.dvdl_fk = c.pk_seq  " +
						"where npp_fk = '" + this.nppId + "' and b.trangthai = '1' and kho_fk = '" + this.khoId + "' and kbh_fk = '100025' and  a.available > 0 ";													

			}
			else
			{
				/*query = "select b.pk_seq as spId, b.ma as spMa, b.ten as spTen, isnull(c.donvi, 'NA') as donvi, a.available + isnull(ck.soluong, 0) as available, " +
						" isnull(	(select available from nvbh_kho where nvbh_fk = '" + this.nvbhId + "' and sanpham_fk = b.pk_seq and kbh_fk = '100025'), 0) as avaiNVBH   " +
						"from nhapp_kho a inner join sanpham b on a.sanpham_fk = b.pk_seq inner join donvidoluong c on b.dvdl_fk = c.pk_seq " +
						"left join chuyenkhoNV_sanpham ck on b.pk_seq = ck.sanpham_fk and ck.chuyenkhoNV_fk = '" + this.id + "' and ck.chuyenkhoNV_fk in ( select pk_seq from chuyenkhoNV where pk_seq = '" + this.id + "' and kho_fk = '" + this.khoId + "' ) " +
						"where npp_fk = '" + this.nppId + "' and b.trangthai = '1' and kho_fk = '" + this.khoId + "' and kbh_fk = '100025' and  a.available > 0 ";*/
				
				query = "select b.pk_seq as spId, b.ma as spMa, b.ten as spTen, isnull(c.donvi, 'NA') as donvi, a.available + isnull(ck.soluong, 0) as available, " +
				" isnull(	(select available from nvbh_kho where nvbh_fk = '" + this.nvbhId + "' and sanpham_fk = b.pk_seq and kbh_fk = '100025' and kho_fk = '"+this.khoId+"' ), 0) as avaiNVBH   " +
				"from nhapp_kho a inner join sanpham b on a.sanpham_fk = b.pk_seq inner join donvidoluong c on b.dvdl_fk = c.pk_seq " +
				"inner join chuyenkhoNV_sanpham ck on b.pk_seq = ck.sanpham_fk and ck.chuyenkhoNV_fk = '" + this.id + "' and ck.chuyenkhoNV_fk in ( select pk_seq from chuyenkhoNV where pk_seq = '" + this.id + "' and kho_fk = '" + this.khoId + "' ) " +
				"where npp_fk = '" + this.nppId + "' and b.trangthai = '1' and kho_fk = '" + this.khoId + "' and kbh_fk = '100025' ";
								
			}
			
			System.out.println("2.Khoi tao SP: " + query);
			this.spRs = db.get(query);
		}
	}
	
	public void createRS2() 
	{
		this.getNppInfo();
		
		String query = "select pk_seq, ten + ', ' + diengiai as khoTen from kho where trangthai = '1' " +
							" and pk_seq in ( select distinct kho_fk from nhapp_kho where npp_fk = '" + this.nppId + "' )  order by pk_seq asc";
		this.khoRs = db.get(query);
		
		query = "select pk_seq as nvbhId, ten as nvbhTen from daidienkinhdoanh where trangthai = '1' and npp_fk = '" + this.nppId + "' ";
		
		this.nvbhRs = db.get(query);
		
		if(this.nvbhId.trim().length() > 0 && this.khoId.trim().length() > 0)
		{
			query = "select b.pk_seq as spId, b.ma as spMa, b.ten as spTen, isnull(c.donvi, 'NA') as donvi, a.available, " +
					" isnull(	(select available from nvbh_kho where nvbh_fk = '" + this.nvbhId + "' and sanpham_fk = b.pk_seq and kbh_fk = '100025' and kho_fk = '" + this.khoId + "'), 0) as avaiNVBH ,ck.soluong  " +
					"from nhapp_kho a inner join sanpham b on a.sanpham_fk = b.pk_seq inner join donvidoluong c on b.dvdl_fk = c.pk_seq " +
					"left join chuyenkhoNV_sanpham ck on b.pk_seq = ck.sanpham_fk and ck.chuyenkhoNV_fk = '" + this.id + "' and ck.chuyenkhoNV_fk in ( select pk_seq from chuyenkhoNV where pk_seq = '" + this.id + "' and kho_fk = '" + this.khoId + "' ) " +
					"where npp_fk = '" + this.nppId + "' and b.trangthai = '1' and kho_fk = '" + this.khoId + "' and kbh_fk = '100025' and  ck.soluong > 0 ";
			
			System.out.println("2.Khoi tao SP2: " + query);
			this.spRs = db.get(query);
		}
	}

	
	public void init()
	{
		String query = "select nvbh_fk, ngaychuyen, kho_fk, trangthai  from chuyenkhoNV where pk_seq = '" + this.id + "'";
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try
			{
				if(rs.next())
				{
					this.ngaychuyen = rs.getString("ngaychuyen");
					this.nvbhId = rs.getString("nvbh_fk");
					this.khoId = rs.getString("kho_fk");
					this.trangthai = rs.getString("trangthai");
					
					rs.close();
				}
				query = "select sanpham_fk, soluong from chuyenkhonv_sanpham where chuyenkhonv_fk = '" + this.id + "'";
				rs = db.get(query);
				if(rs != null)
				{
					this.sp_sl = new Hashtable<String, Integer>();
					while(rs.next())
					{
						this.sp_sl.put(rs.getString("sanpham_fk"), rs.getInt("soluong"));
					}
					rs.close();
				}
				
			} 
			catch (Exception e) {}
		}
		
		this.createRS();
		
	}
	
	public void initDisplay() 
	{
		String query = "select nvbh_fk, ngaychuyen, kho_fk  from chuyenkhoNV where pk_seq = '" + this.id + "'";
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try
			{
				if(rs.next())
				{
					this.ngaychuyen = rs.getString("ngaychuyen");
					this.nvbhId = rs.getString("nvbh_fk");
					this.khoId = rs.getString("kho_fk");
					
					rs.close();
				}
				
				query = "select sanpham_fk, soluong from chuyenkhoNV_sanpham where chuyenkhoNV_fk = '" + this.id + "'";
				rs = db.get(query);
				if(rs != null)
				{
					this.sp_sl = new Hashtable<String, Integer>();
					while(rs.next())
					{
						this.sp_sl.put(rs.getString("sanpham_fk"), rs.getInt("soluong"));
					}
					rs.close();
				}
			} 
			catch (Exception e) {}
		}
		
		this.createRS2();
	}

	private void getNppInfo()
	{		
		geso.dms.distributor.util.Utility util=new geso.dms.distributor.util.Utility();
		this.nppId=util.getIdNhapp(this.userId);
		this.nppTen=util.getTenNhaPP();
		this.sitecode=util.getSitecode();
	}
	
	public void DBclose()
	{
		try 
		{
			if(this.nvbhRs != null)
				this.nvbhRs.close();
			
			if(this.khoRs != null)
				this.khoRs.close();
			if(this.spRs != null)
				this.spRs.close();
			
			db.shutDown();
		} 
		catch (Exception e) {}
		
		
	}

	public Hashtable<String, Integer> getSp_Soluong() 
	{
		return this.sp_sl;
	}

	public void setSSp_Soluong(Hashtable<String, Integer> sp_sl) 
	{
		this.sp_sl = sp_sl;
	}
	
	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	
	public ResultSet getSoloTheoSp(String spIds, String tongluong)
	{
		String query = "select AVAILABLE, SOLO, NGAYHETHAN from NHAPP_KHO_CHITIET " +
					   "where NPP_FK = '" + this.nppId + "' and KHO_FK = '" + this.khoId + "' and SANPHAM_FK = '" + spIds + "' and KBH_FK = '100025' order by NGAYHETHAN asc";
		
		return db.get(query);
	}
	

	
}
