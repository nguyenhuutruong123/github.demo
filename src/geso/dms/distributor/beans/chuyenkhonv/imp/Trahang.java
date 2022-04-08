package geso.dms.distributor.beans.chuyenkhonv.imp;

import geso.dms.center.util.Utility;
import geso.dms.distributor.beans.chuyenkhonv.ITrahang;
import geso.dms.distributor.db.sql.dbutils;

import java.io.Serializable;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;

public class Trahang implements ITrahang, Serializable
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
	
	public Trahang()
	{
		
		this.id = "";
		
		this.nvbhId = "";
		this.khoId = "";
		this.spIds = "";
		this.trangthai = "";
		this.msg = "";
		
		sp_sl = new Hashtable<String, Integer>();
		db = new dbutils();
		this.ngaychuyen = "";
	}
	
	public Trahang(String id)
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
		/*if(this.ngaychuyen.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn ngày trả";
			return false;
		}*/
		
		if(this.nvbhId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn nhân viên bán hàng";
			return false;
		}
		
		if(this.khoId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn kho trả";
			return false;
		}
		if(!ngaychuyen.equals(getDateTime()))
		{
			this.msg = "Ngày chuyển phải bằng ngày hiện tại";
			return false;
		}
		try 
		{
			
			
			String query = "select MAX(NGAYKS) as ngayks from KHOASONGAY where NPP_FK = '"+this.nppId+"'";
			ResultSet	rs = db.get(query);

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
				this.msg = "Không có sản phẩm nào để trả hàng";
				return false;
			}
			
			db.getConnection().setAutoCommit(false);
			
			
			String ngaychot="";
			ngaychot=getNgayKs_CongMot(nppId, db);
			System.out.println("[NGAYCHOT]"+ngaychot);
			if(ngaychot.length()<=0)
			{
				this.msg = "Không thể xác định ngày KS của npp : " ;
				db.getConnection().rollback();
				return false;
			}
			
			
			 query = "insert trahang(nvbh_fk, kho_fk, ngaychuyen, trangthai, npp_fk, ngaytao, nguoitao, ngaysua, nguoisua) " +
							"values('" + this.nvbhId + "', '" + this.khoId + "', '" + ngaychuyen+ "', '1', '" + this.nppId + "', '" + getDateTime() + "', '" + this.userId + "', '" + getDateTime() + "', '" + this.userId + "')";
			System.out.println("1.Tao tra hang: " + query);
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới trả hàng: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			
			String ckId = "";
			query = "select IDENT_CURRENT('TraHang') as ckId";
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
					query = "insert trahang_sanpham(trahang_fk, sanpham_fk, soluong) " +
							"values('" + ckId + "', '" + spIds[i] + "', '" + spSOLUONG[i].trim().replaceAll(",", "") + "')";
					if(!db.update(query))
					{
						this.msg = "Không thể tạo mới trả hàng: " + query;
						db.getConnection().rollback();
						return false;
					}	
							//CHECK TON KHO
							double avai = 0;
							
							
							//DO CHUA DUA BAN PDA MOI LEN, NEN PHAI CHECK KHO TONG CHO NAY, SAU KHI DUA PDA LEN THI BO DI
							avai = 0;
							double booked = 0;
							query = "select available, booked from NVBH_KHO  " +
									"where npp_fk = '" + this.nppId + "' and kho_fk = '" + this.khoId + "' and kbh_fk = '100025' and sanpham_fk = '" + spIds[i] + "'  and NVBH_FK = '" + this.nvbhId + "' and available > 0 ";
							System.out.println("---CHECK TON KHO: " + query);
							ResultSet	rsCHECK = db.get(query);
							if(rsCHECK.next())
							{
								avai = rsCHECK.getDouble("available");
								booked = rsCHECK.getDouble("booked");
							}
							rsCHECK.close();
							
							if(avai < Double.parseDouble(spSOLUONG[i].trim().replaceAll(",", "")) )
							{
								this.msg = "Tồn kho của sản phẩm ( " + spTEN[i] + " ), số lượng ( " + spSOLUONG[i] + " ) còn tối đa ( " + avai + " ), đang booked trong đơn hàng ( " + booked + " ) ";
								db.getConnection().rollback();
								return false;
							}
							
							
							query = "update nhapp_kho set available = available + '" + spSOLUONG[i].trim().replaceAll(",", "") + "', soluong = soluong + '" + spSOLUONG[i].trim().replaceAll(",", "") + "' " +
									"where npp_fk = '" + this.nppId + "' and kho_fk = '" + this.khoId + "' and kbh_fk = '100025' and sanpham_fk = '" + spIds[i] + "' ";
									
							if(!db.update(query))
							{
								this.msg = "Không thể tạo mới trả hàng: " + query;
								db.getConnection().rollback();
								return false;
							}
							
							
						
							query = "update NVBH_KHO set available = available - '" + spSOLUONG[i].trim().replaceAll(",", "") + "', soluong = soluong - '" + spSOLUONG[i].trim().replaceAll(",", "") + "' " +
									"where npp_fk = '" + this.nppId + "' and kho_fk = '" + this.khoId + "' and kbh_fk = '100025' and nvbh_fk = '" + this.nvbhId + "' and sanpham_fk = '" + spIds[i] + "' ";
									
							if(!db.update(query))
							{
								this.msg = "Không thể tạo mới trả hàng: " + query;
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
			this.msg = "Lỗi khi tạo trả hàng: " + e.getMessage();
			e.printStackTrace();
			
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
			String query = "select a.npp_fk, a.kho_fk, b.sanpham_fk, b.soluong, b.solo  " +
						"from trahang a inner join trahang_sanpham_chitiet b on a.pk_seq = b.trahang_fk where a.pk_seq = '" + this.id + "'";
			
			ResultSet rsUpdate = db.get(query);
			if(rsUpdate != null)
			{
				while(rsUpdate.next())
				{
					query = "update nhapp_kho_chitiet set available = available + '" + rsUpdate.getInt("soluong") + "', booked = booked - '" + rsUpdate.getInt("soluong") + "' " +
							"where npp_fk = '" + rsUpdate.getString("npp_fk") + "' and kho_fk = '" + rsUpdate.getString("kho_fk") + "' and kbh_fk = '100025' and sanpham_fk = '" + rsUpdate.getString("sanpham_fk") + "' and solo = '" + rsUpdate.getString("solo") + "' ";
					
					if(!db.update(query))
					{
						this.msg = "Không thể tạo mới chuyển kho: " + query;
						db.getConnection().rollback();
						return false;
					}
			
					query = "update nhapp_kho set available = available + '" + rsUpdate.getInt("soluong") + "', booked = booked - '" + rsUpdate.getInt("soluong") + "' " +
							"where npp_fk = '" + rsUpdate.getString("npp_fk") + "' and kho_fk = '" + rsUpdate.getString("kho_fk") + "' and kbh_fk = '100025' and sanpham_fk = '" + rsUpdate.getString("sanpham_fk") + "' ";
					
					if(!db.update(query))
					{
						this.msg = "Không thể tạo mới chuyển kho: " + query;
						db.getConnection().rollback();
						return false;
					}
				}
				rsUpdate.close();
			}
			

			query = "update trahang set nvbh_fk = '" + this.nvbhId + "', kho_fk = '" + this.khoId + "', ngaychuyen = '" + this.ngaychuyen + "', " +
					"ngaysua = '" + getDateTime() + "', nguoisua = '" + this.userId + "' where pk_seq = '" + this.id + "' ";
					
			if(!db.update(query))
			{
				this.msg = "Không thể tạo cập nhật chuyển kho: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete trahang_sanpham where trahang_fk = '" + this.id + "'";	
			if(!db.update(query))
			{
				this.msg = "Không thể tạo cập nhật chuyển kho: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete trahang_sanpham_chitiet where trahang_fk = '" + this.id + "'";	
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
					query = "insert trahang_sanpham(trahang_fk, sanpham_fk, soluong) " +
							"values('" + this.id + "', '" + spIds[i] + "', '" + spSOLUONG[i].trim().replaceAll(",", "") + "')";
					if(!db.update(query))
					{
						this.msg = "Không thể tạo mới chuyển kho: " + query;
						db.getConnection().rollback();
						return false;
					}
					
					String[] _solo = request.getParameterValues( spIds[i] + "_spSOLO");
					String[] _ngayhethan = request.getParameterValues( spIds[i] + "_spNGAYHETHAN");
					String[] _soluong = request.getParameterValues( spIds[i] + "_spSOLUONG");
					for(int j = 0; j < _solo.length; j++ )
					{
						if(_soluong[j].trim().length() > 0)
						{
							//CHECK TON KHO
							double avai = 0;
							query = "select available from NHAPP_KHO_CHITIET  " +
									"where npp_fk = '" + this.nppId + "' and kho_fk = '" + this.khoId + "' and kbh_fk = '100025' and sanpham_fk = '" + spIds[i] + "' and solo = '" + _solo[j] + "' ";
							ResultSet rsCHECK = db.get(query);
							if(rsCHECK.next())
							{
								avai = rsCHECK.getDouble("available");
							}
							rsCHECK.close();
							
							if(avai < Double.parseDouble(_soluong[j].trim().replaceAll(",", "")) )
							{
								this.msg = "Tồn kho của sản phẩm ( " + spTEN[i] + " ) với số lô ( " + _solo[j] + " ), số lượng ( " + _soluong[j] + " ) còn tối đa ( " + avai + " ) ";
								db.getConnection().rollback();
								return false;
							}
							
							query = "insert trahang_sanpham_chitiet(trahang_fk, sanpham_fk, solo, ngayhethan, soluong) " +
									"values('" + this.id + "', '" + spIds[i] + "', '" + _solo[j] + "', '" + _ngayhethan[j] + "', '" + _soluong[j].trim().replaceAll(",", "") + "')";
							if(!db.update(query))
							{
								this.msg = "Không thể tạo mới chuyển kho: " + query;
								db.getConnection().rollback();
								return false;
							}
					
							query = "update NHAPP_KHO_CHITIET set available = available - '" + _soluong[j].trim().replaceAll(",", "") + "', booked = booked + '" + _soluong[j].trim().replaceAll(",", "") + "' " +
									"where npp_fk = '" + this.nppId + "' and kho_fk = '" + this.khoId + "' and kbh_fk = '100025' and sanpham_fk = '" + spIds[i] + "' and solo = '" + _solo[j] + "' ";
									
							if(!db.update(query))
							{
								this.msg = "Không thể tạo mới chuyển kho: " + query;
								db.getConnection().rollback();
								return false;
							}
							
							query = "update nhapp_kho set available = available - '" + _soluong[j].trim().replaceAll(",", "") + "', booked = booked + '" + _soluong[j].trim().replaceAll(",", "") + "' " +
									"where npp_fk = '" + this.nppId + "' and kho_fk = '" + this.khoId + "' and kbh_fk = '100025' and sanpham_fk = '" + spIds[i] + "' ";
									
							if(!db.update(query))
							{
								this.msg = "Không thể tạo mới chuyển kho: " + query;
								db.getConnection().rollback();
								return false;
							}
						}
				
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
			db.getConnection().setAutoCommit(false);
			
			//Cap nhat lai kho NPP truoc
			String query = "select a.npp_fk, a.nvbh_fk, a.kho_fk, b.sanpham_fk, b.soluong, b.solo, b.ngayhethan  " +
						  "from trahang a inner join trahang_sanpham_chitiet b on a.pk_seq = b.trahang_fk where a.pk_seq = '" + this.id + "'";
			
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
					String solo = rsUpdate.getString("solo");
					String ngayhethan = rsUpdate.getString("ngayhethan");
					
					query = "update nhapp_kho set soluong = soluong - '" + rsUpdate.getInt("soluong") + "', booked = booked - '" + rsUpdate.getInt("soluong") + "' " +
							"where npp_fk = '" + rsUpdate.getString("npp_fk") + "' and kho_fk = '" + rsUpdate.getString("kho_fk") + "' and kbh_fk = '100025' and sanpham_fk = '" + rsUpdate.getString("sanpham_fk") + "' ";
					
					if(!db.update(query))
					{
						this.msg = "Không thể chốt chuyển kho: " + query;
						db.getConnection().rollback();
						return false;
					}
					
					query = "update nhapp_kho_chitiet set soluong = soluong - '" + rsUpdate.getInt("soluong") + "', booked = booked - '" + rsUpdate.getInt("soluong") + "' " +
							"where npp_fk = '" + rsUpdate.getString("npp_fk") + "' and kho_fk = '" + rsUpdate.getString("kho_fk") + "' and kbh_fk = '100025' and sanpham_fk = '" + rsUpdate.getString("sanpham_fk") + "' and SOLO = '" + rsUpdate.getString("solo") + "' ";
					
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
					
					
					//Chuyen hang sang kho NVBH - CHI TIET
					query = "select count(sanpham_fk) as sodong from NVBH_KHO_CHITIET " +
								"where nvbh_fk = '" + rsUpdate.getString("nvbh_fk")  + "' and kho_fk = '" + rsUpdate.getString("kho_fk") + "' and kbh_fk = '100025' and sanpham_fk = '" + rsUpdate.getString("sanpham_fk") + "' and SOLO = '" + solo + "' ";
			
					rsSp = db.get(query);
					sodong = 0;
					
					if(rsSp.next())
					{
						sodong = rsSp.getInt("sodong");
						rsSp.close();
					}
					
					if(sodong > 0) //Da co. Update
					{
						query = "update NVBH_KHO_CHITIET set soluong = soluong + '" + rsUpdate.getInt("soluong") + "', available = available + '" + rsUpdate.getInt("soluong") + "', ngayhethan = '" + ngayhethan + "' " +
								"where nvbh_fk = '" + this.nvbhId + "' and kho_fk = '" + this.khoId + "' and kbh_fk = '100025' and sanpham_fk = '" + rsUpdate.getString("sanpham_fk") + "' and SOLO = '" + solo + "' ";
								
						if(!db.update(query))
						{
							this.msg = "Không thể chốt chuyển kho: " + query;
							db.getConnection().rollback();
							return false;
						}
						
					}
					else //Tao moi
					{
						query = "insert NVBH_KHO_CHITIET(npp_fk, nvbh_fk, kho_fk, kbh_fk, sanpham_fk, solo, ngayhethan, soluong, booked, available) " +
								"values('" + this.nppId + "', '" + this.nvbhId + "', '" + this.khoId + "', '100025', '" + rsUpdate.getString("sanpham_fk") + "', '" + solo + "', '" + ngayhethan + "', '" + rsUpdate.getInt("soluong") + "', '0', '" + rsUpdate.getInt("soluong") + "')";
						
						if(!db.update(query))
						{
							this.msg = "Không thể chốt chuyển kho: " + query;
							db.getConnection().rollback();
							return false;
						}
						
					}
					
				}
				rsUpdate.close();
			}
			
			Utility uti = new Utility();
			String ngaychuyen= uti.NgayKhoaSo_Add(nppId, 1);
			
			query = "update trahang set trangthai = '1', NgayChuyen='"+ngaychuyen+"',ngaysua = '" + getDateTime() + "', nguoisua = '" + this.userId + "' where pk_seq = '" + this.id + "' ";
					
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
			System.out.println("Exception; " + e.getMessage());
			return false;
		}
		
		return true;
	}

	public boolean DeleteCk() 
	{
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			//Cap nhat lai kho NPP truoc
			String query = "select a.npp_fk, a.kho_fk, b.sanpham_fk, b.soluong, b.solo  " +
						"from trahang a inner join trahang_sanpham_chitiet b on a.pk_seq = b.trahang_fk where a.pk_seq = '" + this.id + "'";
			
			ResultSet rsUpdate = db.get(query);
			if(rsUpdate != null)
			{
				while(rsUpdate.next())
				{
					query = "update nhapp_kho_chitiet set available = available + '" + rsUpdate.getInt("soluong") + "', booked = booked - '" + rsUpdate.getInt("soluong") + "' " +
							"where npp_fk = '" + rsUpdate.getString("npp_fk") + "' and kho_fk = '" + rsUpdate.getString("kho_fk") + "' and kbh_fk = '100025' and sanpham_fk = '" + rsUpdate.getString("sanpham_fk") + "' and solo = '" + rsUpdate.getString("solo") + "' ";
					
					if(!db.update(query))
					{
						this.msg = "Không thể tạo mới chuyển kho: " + query;
						db.getConnection().rollback();
						return false;
					}
			
					query = "update nhapp_kho set available = available + '" + rsUpdate.getInt("soluong") + "', booked = booked - '" + rsUpdate.getInt("soluong") + "' " +
							"where npp_fk = '" + rsUpdate.getString("npp_fk") + "' and kho_fk = '" + rsUpdate.getString("kho_fk") + "' and kbh_fk = '100025' and sanpham_fk = '" + rsUpdate.getString("sanpham_fk") + "' ";
					
					if(!db.update(query))
					{
						this.msg = "Không thể tạo mới chuyển kho: " + query;
						db.getConnection().rollback();
						return false;
					}
				}
				rsUpdate.close();
			}
			
			query = "update trahang set trangthai = '2', ngaysua = '" + getDateTime() + "', nguoisua = '" + this.userId + "' where pk_seq = '" + this.id + "' ";
					
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
			System.out.println("Exception; " + e.getMessage());
			return false;
		}
		
		return true;
	}
	
	public void createRS() 
	{
		this.getNppInfo();
		
		String query = "select pk_seq, ten + ', ' + diengiai as khoTen from kho where trangthai = '1' " +
							" and pk_seq in ( select distinct kho_fk from nhapp_kho where npp_fk = '" + this.nppId + "' )  order by pk_seq asc";
		this.khoRs = db.get(query);
		
		query = "select pk_seq as nvbhId, ten as nvbhTen from daidienkinhdoanh where trangthai = '1' and npp_fk = '" + this.nppId + "' ";
		System.out.println("1.Lay NVBH: " + query);
		
		this.nvbhRs = db.get(query);
		
		if(this.nvbhId.trim().length() > 0 && this.khoId.trim().length() > 0)
		{
			/*if(this.id.trim().length() <= 0)
			{*/
				query = "select b.pk_seq as spId, b.ma as spMa, b.ten as spTen, isnull(c.donvi, 'NA') as donvi, a.available, " +
						" isnull(	(select sum(available) from nvbh_kho where nvbh_fk = '" + this.nvbhId + "' and sanpham_fk = b.pk_seq and kbh_fk = '100025' and kho_fk = '" + this.khoId + "'), 0) as avaiNVBH   " +
						"from nhapp_kho a inner join sanpham b on a.sanpham_fk = b.pk_seq inner join donvidoluong c on b.dvdl_fk = c.pk_seq  " +
						"where npp_fk = '" + this.nppId + "' and b.trangthai = '1' and kho_fk = '" + this.khoId + "' and kbh_fk = '100025' " +
						//"and  a.available > 0 ";
						"and isnull( (select available from nvbh_kho where nvbh_fk = '" + this.nvbhId + "' and sanpham_fk = b.pk_seq and kbh_fk = '100025' and kho_fk = '" + this.khoId + "'), 0) > 0 ";
			/*}
			else
			{
				query = "select b.pk_seq as spId, b.ma as spMa, b.ten as spTen, isnull(c.donvi, 'NA') as donvi, a.available + isnull(ck.soluong, 0) as available, " +
						" isnull(	(select available from nvbh_kho where nvbh_fk = '" + this.nvbhId + "' and sanpham_fk = b.pk_seq and kbh_fk = '100025'), 0) as avaiNVBH   " +
						"from nhapp_kho a inner join sanpham b on a.sanpham_fk = b.pk_seq inner join donvidoluong c on b.dvdl_fk = c.pk_seq " +
						"left join trahang_sanpham ck on b.pk_seq = ck.sanpham_fk and ck.trahang_fk = '" + this.id + "' and ck.trahang_fk in ( select pk_seq from trahang where pk_seq = '" + this.id + "' and kho_fk = '" + this.khoId + "' ) " +
						"where npp_fk = '" + this.nppId + "' and b.trangthai = '1' and kho_fk = '" + this.khoId + "' and kbh_fk = '100025' and  a.available > 0 ";
			}*/
			
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
					" isnull(	(select available from nvbh_kho where nvbh_fk = '" + this.nvbhId + "' and sanpham_fk = b.pk_seq and kbh_fk = '100025' and kho_fk = '" + this.khoId + "'), 0) as avaiNVBH,ck.soluong   " +
					"from nhapp_kho a inner join sanpham b on a.sanpham_fk = b.pk_seq inner join donvidoluong c on b.dvdl_fk = c.pk_seq " +
					"left join trahang_sanpham ck on b.pk_seq = ck.sanpham_fk and ck.trahang_fk = '" + this.id + "' and ck.trahang_fk in ( " + this.id + " ) " +
					"where npp_fk = '" + this.nppId + "' and b.trangthai = '1' and kho_fk = '" + this.khoId + "' and kbh_fk = '100025' and ck.soluong > 0 ";
			
			System.out.println("2.Khoi tao SP: " + query);
			this.spRs = db.get(query);
		}
	}

	
	public void init()
	{
		String query = "select nvbh_fk, ngaychuyen, kho_fk, trangthai  from trahang where pk_seq = '" + this.id + "'";
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
				
				query = "select sanpham_fk,  soluong from trahang_sanpham where trahang_fk = '" + this.id + "'";
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
		String query = "select nvbh_fk, ngaychuyen, kho_fk  from trahang where pk_seq = '" + this.id + "'";
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
				
				query = "select sanpham_fk, soluong from trahang_sanpham where trahang_fk = '" + this.id + "'";
				System.out.println("QR: "+query);
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
		String query = "";
		
		if(this.id.trim().length() <= 0)
		{
			//PHAI TU DE XUAT LO, DO CHUA DUA BAN PDA TRU KHO SAN LEN
			query = "select AVAILABLE, NGAYHETHAN, SOLO " +
				    "from NVBH_KHO_CHITIET  " +
				    "where NVBH_FK = '" + this.nvbhId + "' and SANPHAM_FK = '" + spIds + "' and NPP_FK = '" + this.nppId + "' " +
				   		" and AVAILABLE > 0 " +
				    " order by NGAYHETHAN asc";
			System.out.println("--DE XUAT LO: " + query );
			
			String query2 = "";
			ResultSet rs = db.get(query);
			try 
			{
				double total = 0;
				
				while(rs.next())
				{
					double slg = 0;
					double avai = rs.getDouble("AVAILABLE");
					
					total += avai;
					
					if(total < Double.parseDouble(tongluong))
					{
						slg = avai;
					}
					else
					{
						slg =  Double.parseDouble(tongluong) - ( total - avai );
					}
						
					if(slg >= 0)
					{
						query2 += "select '" + slg + "' as AVAILABLE, '" + rs.getString("NGAYHETHAN") + "' as NGAYHETHAN, '" + rs.getString("SOLO") + "' as SOLO, '" + slg + "' as SOLUONG union ALL ";
					}
					else
					{
						query2 += "select '" + slg + "' as AVAILABLE, '" + rs.getString("NGAYHETHAN") + "' as NGAYHETHAN, '" + rs.getString("SOLO") + "' as SOLO, '0' as SOLUONG union ALL ";
					}
					
				}
				rs.close();
			} 
			catch (Exception e) 
			{
				System.out.println("EXCEPTION INIT SOLO: " + e.getMessage());
			}
			
			if(query2.trim().length() > 0)
			{
				query2 = query2.substring(0, query2.length() - 10);
				System.out.println("---TU DONG DE XUAT BIN - LO: " + query2 );
				return db.get(query2);
			}
			
			/*query = "select AVAILABLE as SOLUONG, AVAILABLE, SOLO, NGAYHETHAN from NVBH_KHO_CHITIET " +
				    "where NPP_FK = '" + this.nppId + "' and KHO_FK = '" + this.khoId + "' and SANPHAM_FK = '" + spIds + "' " +
				   		"and NVBH_FK = '" + this.nvbhId + "' and KBH_FK = '100025' and AVAILABLE > 0  " +
				    "order by NGAYHETHAN asc";*/
		}
		else
		{
			query = "select SOLUONG, 0 as AVAILABLE, SOLO, NGAYHETHAN " +
					"from TRAHANG_SANPHAM_CHITIET where trahang_fk = '" + this.id + "' and SANPHAM_FK = '" + spIds + "' " +
				    "order by NGAYHETHAN asc";
		}
		
		//System.out.println("Lay SOLO: " + query);
		return db.get(query);
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

	
}
