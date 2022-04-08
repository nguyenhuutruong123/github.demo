package geso.dms.center.beans.tieuchithuong.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import geso.dms.center.beans.dieukienkhuyenmai.ISanpham;
import geso.dms.center.beans.dieukienkhuyenmai.imp.Sanpham;
import geso.dms.center.beans.tieuchithuong.INhomsp;
import geso.dms.center.beans.tieuchithuong.INhomspDetail;
import geso.dms.center.beans.tieuchithuong.ITieuchithuongSKU;
import geso.dms.center.db.sql.dbutils;

public class TieuChiThuongSKU implements ITieuchithuongSKU 
{
	String userId;
	String id;
	String scheme;
	String thang;
	String nam;
	String diengiai;
	String phaidat;
	String thuong;
	String thuongtoida;
	String thuongGS;
	String thuongtoidaGS;
	String[] daidienkinhdoanhimport;
	String[] phaidatimport;
	ResultSet nhomspRs;
	
	List<INhomsp> nhomSpList;
	
	String isThung;
	String msg;

	dbutils db;
	
	public TieuChiThuongSKU()
	{
		this.id = "";
		this.thang = "";
		this.nam = "";
		this.diengiai = "";
		this.phaidat = "";
		this.thuong = "0";
		this.thuongtoida = "0";
		this.thuongGS = "0";
		this.thuongtoidaGS = "0";
		this.msg = "";
		this.scheme = "";
		this.daidienkinhdoanhimport = null;
		this.phaidatimport=null;
		this.nhomSpList = new ArrayList<INhomsp>();
		
		this.isThung = "0";
		this.db = new dbutils();
	}
	
	public TieuChiThuongSKU(String id)
	{
		this.id = id;
		this.thang = "";
		this.nam = "";
		this.diengiai = "";
		this.phaidat = "";
		this.thuong = "0";
		this.thuongtoida = "0";
		this.thuongGS = "0";
		this.thuongtoidaGS = "0";
		this.msg = "";
		this.scheme = "";
		this.daidienkinhdoanhimport = null;
		this.phaidatimport=null;
		this.nhomSpList = new ArrayList<INhomsp>();
		
		this.isThung = "0";
		this.db = new dbutils();
	}
	
	public String getUserId()
	{
		return this.userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public String getThang() 
	{
		return this.thang;
	}
	
	public void setThang(String thang) 
	{
		this.thang = thang;
	}
	
	public String getNam() 
	{
		return this.nam;
	}
	
	public void setNam(String nam)
	{
		this.nam = nam;
	}

	public String getId() 
	{
		return this.id;
	}

	public void setId(String id) 
	{
		this.id = id;
	}

	public String getDiengiai()
	{
		return this.diengiai;
	}

	public void setDiengiai(String diengiai) 
	{
		this.diengiai = diengiai;
	}

	public String getPhaidat() 
	{
		return this.phaidat;
	}

	public void setPhaidat(String phaidat) 
	{
		this.phaidat = phaidat;
	}

	public String getThuong() 
	{
		return this.thuong;
	}

	public void setThuong(String thuong) 
	{
		this.thuong = thuong;
	}

	public String getThuongtoida()
	{
		return this.thuongtoida;
	}

	public void setThuongtoida(String thuongtoida)
	{
		this.thuongtoida = thuongtoida;
	}

	public List<INhomsp> getNhomspList() 
	{
		return this.nhomSpList;
	}

	public void setNhomspList(List<INhomsp> nspList) 
	{
		this.nhomSpList = nspList;
	}

	public String getMsg()
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}

	
	public boolean createTctSKU(String[] nspId, String[] nspTongluong, String[] nspTrongso, String[] nspTumuc, String[] nspDenmuc,  
			String[] nspTSR, String[] nspTTDSR, String[] nspTSS, String[] nspTTDSS, String[] nspTASM, String[] nspTTDASM ) 
	{
		try
		{
			if(this.scheme.trim().length() <= 0)
			{
				this.msg = "Vui lòng scheme";
				return false;
			}
			
			if(this.phaidat.length() <= 0)
			{
				this.msg = "Chưa nhập số lượng phải đạt của chỉ tiêu";
				return false;
			}
			
			if(this.thuong.length() <= 0)
			{
				this.msg = "Chưa nhập số tiền thưởng của chỉ tiêu";
				return false;
			}
			
			if(this.thuongGS.length() <= 0)
			{
				this.msg = "Chưa nhập số tiền thưởng cho giám sát chỉ tiêu";
				return false;
			}
			
			//check chi tieu
			/*String query = "select count(*) as sodong from [TIEUCHITHUONGSKU] where thang = '" + this.thang + "' and nam = '" + this.nam + "'";
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				int count = 0;
				if(rs.next())
				{
					count = rs.getInt("sodong");
					if(count > 0)
					{
						this.msg = "Thang " + this.thang + " Cua nam " + this.nam + " da chia chi tieu roi.";
						rs.close();
						return false;
					}
				}
				rs.close();
			}*/
			
			//Check Scheme
			String query = "select count(*) as sodong from [TIEUCHITHUONGSKU] where scheme = N'" + this.scheme + "'";
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				int count = 0;
				if(rs.next())
				{
					count = rs.getInt("sodong");
					if(count > 0)
					{
						this.msg = "Scheme: " + this.scheme + " đã tồn tại, vui lòng nhập scheme khác";
						rs.close();
						return false;
					}
				}
				rs.close();
			}
			
			//Check moi nhom san pham chon chi co 1 sanpham (Neu co thiet lap tumuc -- denmuc thi ko check duoc....)
			if(nspId.length > 0)
			{
				String nspIds = "";
				for(int i = 0; i < nspId.length; i++)
				{
					if(nspId[i].trim().length() > 0)
					{
						nspIds += nspId[i] + ",";
					}
				}
				
				if(nspIds.length() <= 0)
				{
					this.msg = "Vui lòng chọn nhóm sản phẩm thưởng";
					return false;
				}
				
				nspIds = nspIds.substring(0, nspIds.length() - 1);
				
				//String query = "select count(*) as sosanpham from NHOMSANPHAM_SANPHAM where nsp_fk in (" + nspIds + ") group by sp_fk";
				/*query = "select count(sp_fk) as sosanpham " +
								"from NHOMSANPHAM_SANPHAM " +
								"where (nsp_fk in (" + nspIds + "))  " +
										"or (nsp_fk in ( select nhomsp_fk " +
														"from tieuchithuongsku_nhomsp " +
														"where tieuchithuongsku_fk in (select pk_seq from tieuchithuongsku where thang = '" + this.thang + "' and nam = '" + this.nam + "') )) " +
														"group by sp_fk " +
														"having count(sp_fk) > 1";
				rs = db.get(query);
				if(rs != null)
				{
					int count = 0;
					if(rs.next())
					{
						count = rs.getInt("sosanpham");
						if(count > 1)
						{
							this.msg = "Có sản phẩm trùng nhau trong khai báo chỉ tiêu tháng, vui lòng kiểm tra lại";
							rs.close();
							return false;
						}
					}
					rs.close();
				}*/
			}
			else
			{
				this.msg = "Vui lòng chọn nhóm sản phẩm thưởng";
				return false;
			}
			
			
			db.getConnection().setAutoCommit(false);
			
			query = "insert [TIEUCHITHUONGSKU](scheme, thang, nam, diengiai, trangthai, ngaytao, nguoitao, ngaysua, nguoisua, IsThung) " +
					"values(N'" + this.scheme + "', '" + this.thang + "', '" + this.nam + "', N'" + this.diengiai + "', '0', " +
							"'" + this.getDateTime() + "', '" + this.userId + "', '" + this.getDateTime() + "', '" + this.userId + "', '" + this.isThung + "')";
			
			System.out.println("1.Insert: " + query);
			if(!db.update(query))
			{
				this.msg = "Khong the tao moi TIEUCHITHUONGSKU: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			//lay dkkm current
			String tctCurrent = "";
			query = "select IDENT_CURRENT('TIEUCHITHUONGSKU') as tctId";
			
			ResultSet rsTct = db.get(query);						
			rsTct.next();
			tctCurrent = rsTct.getString("tctId");
			rsTct.close();
			/////////////////Ngan da them/////////////////////////////
			System.out.println("sdsdsds...."+this.daidienkinhdoanhimport );
			if(this.daidienkinhdoanhimport == null)
			{
				this.msg = "Bạn phải Upload tiêu chí của nhân viên bán hàng: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			if(this.daidienkinhdoanhimport.length != 0)
			{
				
				String queryidsp = "select count(*) as soluong from tieuchithuongSKU_DDKD where tieuchithuongSKU_fk ='"+ tctCurrent +"'";
				int sl=0;
				ResultSet rsSp = db.get(queryidsp);
				if(rsSp!=null)
				{
					try 
					{
						if(rsSp.next())
						{
							sl = rsSp.getInt("soluong");
							rsSp.close();
						}
					} 
					catch (SQLException e) 
					{
						geso.dms.center.util.Utility.rollback_throw_exception(db);
						this.msg="Lổi trong lúc lấy tiêu chí thưởng SKU : " + queryidsp;	
						return false;
					}
				}
				else
				{
					geso.dms.center.util.Utility.rollback_throw_exception(db);
					this.msg="Lổi trong lúc lấy tiêu chí thưởng SKU : " + queryidsp;
					return false;
				}
				if(sl>0)
				{
					query = "delete TIEUCHITHUONGSKU_ddkd where tieuchithuongsku_fk='"+this.id+"'";
					
					System.out.println("___Chen TIEUCHITHUONGSKU_ddkdP: " + query);
					
					if(!db.update(query))
					{
						this.msg = "Khong the xóa TIEUCHITHUONGSKU_ddkd đã có: " + query;
						db.getConnection().rollback();
						return false;
					}
				}
				
				for(int i = 0; i < this.daidienkinhdoanhimport.length; i++)
				{
					
					/*String queryidsp = "select PK_SEQ from daidienkinhdoanh where ma='"+ this.daidienkinhdoanhimport[i] +"'";
					String pk_seq="";
					ResultSet rsSp = db.get(queryidsp);
					if(rsSp!=null)
					{
						try 
						{
							if(rsSp.next())
							{
								pk_seq = rsSp.getString("PK_SEQ");
								rsSp.close();
							}
						} 
						catch (SQLException e) 
						{
							geso.dms.center.util.Utility.rollback_throw_exception(db);
							this.msg="Lổi trong lúc lấy PK sản phẩm : " + queryidsp;	
							return false;
						}
					}
					else
					{
						geso.dms.center.util.Utility.rollback_throw_exception(db);
						this.msg="Lổi trong lúc lấy PK sản phẩm : " + queryidsp;
						return false;
					}*/
					if(this.daidienkinhdoanhimport.length > 0)
					{

						query = "insert TIEUCHITHUONGSKU_ddkd(TIEUCHITHUONGSKU_FK, ddkd_fk,phaidat) " +
								"values('" + tctCurrent + "', '" + this.daidienkinhdoanhimport[i] + "', '" + this.phaidatimport[i] +"')";	
						System.out.println("___Chen TIEUCHITHUONGSKU_ddkdP: " + query);
						if(this.daidienkinhdoanhimport[i] != null && this.phaidatimport[i]!= null)
						{
							if(!db.update(query))
							{
								this.msg = "Khong the tao moi TIEUCHITHUONGSKU_ddkd: " + query;
								db.getConnection().rollback();
								return false;
							}
						}
					}
					else
					{
						this.msg = "Bạn chưa chọn file upload";
						db.getConnection().rollback();
						return false;
					}
				}
			}
			
			///////////////////////////////////////////////////////////
			System.out.println("NHom san pham Id la: " + nspId.length);
			
			if(nspId != null)
			{
				for(int i = 0; i < nspId.length; i++)
				{
					if(nspId[i].length() > 0)
					{
						if(nspTrongso[i].length() <= 0)
							nspTrongso[i] = "0";
						if(nspTumuc[i].length() <= 0)
							nspTumuc[i] = "0";
						if(nspDenmuc[i].length() <= 0)
							nspDenmuc[i] = "9999999";
						
						
						query = "insert TIEUCHITHUONGSKU_NHOMSP(TIEUCHITHUONGSKU_FK, NHOMSP_FK, TONGLUONG, TRONGSO, TUMUC, DENMUC) " +
								"values('" + tctCurrent + "', '" + nspId[i] + "', '" + nspTongluong[i] + "', '" + nspTrongso[i] + "', '" + nspTumuc[i] + "', '" + nspDenmuc[i] + "')";
						
						System.out.println("___Chen TIEUCHITHUONGSKU_NHOMSP: " + query);
						if(!db.update(query))
						{
							this.msg = "Khong the tao moi TIEUCHITHUONGSKU_NHOMSP: " + query;
							db.getConnection().rollback();
							return false;
						}
						System.out.println("asdadasdsad.asd.as."+nspTSR[i].length() );
						if(nspTSR[i].length()>  0)
						{
							if(nspTTDSR[i].length() <= 0 )
								nspTTDSR[i] = "0";
							if(nspTSS[i].length() <= 0 )
								nspTSS[i] = "0";
							if(nspTTDSS[i].length() <= 0 )
								nspTTDSS[i] = "0";
							if(nspTASM[i].length() <= 0 )
								nspTASM[i] = "0";
							if(nspTTDASM[i].length() <= 0 )
								nspTTDASM[i] = "0";
							
							query = "update NHOMSANPHAM_SANPHAM set Thuong_Sr = '" + nspTSR[i] + "', Thuong_Td_Sr = '" + nspTTDSR[i] + "', Thuong_Ss = '" + nspTSS[i] + "', Thuong_Td_Ss = '" + nspTTDSS[i] + "'" +
									", thuong_ASM = '" + nspTASM[i] + "', thuong_Td_ASM = '" + nspTTDASM[i] + "' where NSP_FK = '" + nspId[i] + "'";
							System.out.println("___Chen TIEUCHITHUONGSKU_NHOMSP: " + query);
							if(!db.update(query))
							{
								this.msg = "Khong the tao moi TIEUCHITHUONGSKU_NHOMSP: " + query;
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
		catch (SQLException e)
		{
			try 
			{
				db.getConnection().rollback();
			} catch (SQLException e1) {}
			
			System.out.println("115.Error: " + e.getMessage());
		}
		
		return true;
	}

	public boolean updateTctSKU(String[] nspId, String[] nspTongluong, String[] nspTrongso, String[] nspTumuc, String[] nspDenmuc,  
			String[] nspTSR, String[] nspTTDSR, String[] nspTSS, String[] nspTTDSS, String[] nspTASM, String[] nspTTDASM)
	{
		try
		{
			if(this.scheme.trim().length() <= 0)
			{
				this.msg = "Vui long nhap scheme";
				return false;
			}
			
			if(this.phaidat.length() <= 0)
			{
				this.msg = "Chua nhap so luong phai dat cua chi tieu";
				return false;
			}
			
			if(this.thuong.length() <= 0)
			{
				this.msg = "Chua nhap so tien thuong cua chi tieu";
				return false;
			}
			
			if(this.thuongGS.length() <= 0)
			{
				this.msg = "Chua nhap so tien thuong cho giam sat cua chi tieu";
				return false;
			}
			
			
			//Check Scheme
			String query = "select count(*) as sodong from [TIEUCHITHUONGSKU] where scheme = N'" + this.scheme + "' and pk_seq != '" + this.id + "'";
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				int count = 0;
				if(rs.next())
				{
					count = rs.getInt("sodong");
					if(count > 0)
					{
						this.msg = "Scheme: " + this.scheme + " đã tồn tại, vui lòng nhập scheme khác";
						rs.close();
						return false;
					}
				}
				rs.close();
			}
			
			//Check moi nhom san pham chon chi co 1 sanpham
			if(nspId.length > 0)
			{
				String nspIds = "";
				for(int i = 0; i < nspId.length; i++)
				{
					if(nspId[i].trim().length() > 0)
					{
						nspIds += nspId[i] + ",";
					}
				}
				
				if(nspIds.length() <= 0)
				{
					this.msg = "Vui long chon nhom sa pham";
					return false;
				}
				
				nspIds = nspIds.substring(0, nspIds.length() - 1);
				
				/*query = "select count(sp_fk) as sosanpham " +
				"from NHOMSANPHAM_SANPHAM " +
				"where (nsp_fk in (" + nspIds + "))  " +
						"or (nsp_fk in ( select nhomsp_fk " +
										"from tieuchithuongsku_nhomsp " +
										"where tieuchithuongsku_fk in (select pk_seq from tieuchithuongsku where thang = '" + this.thang + "' and nam = '" + this.nam + "') )) " +
										"group by sp_fk " +
										"having count(sp_fk) > 1";
				rs = db.get(query);
				if(rs != null)
				{
					int count = 0;
					if(rs.next())
					{
						count = rs.getInt("sosanpham");
						if(count > 1)
						{
							this.msg = "Có sản phẩm trùng nhau trong khai báo chỉ tiêu tháng, vui lòng kiểm tra lại";
							rs.close();
							return false;
						}
					}
					rs.close();
				}*/
			}
			else
			{
				this.msg = "Vui lòng chọn nhóm sản phẩm thưởng";
				return false;
			}
			
			db.getConnection().setAutoCommit(false);
			
			query = "update [TIEUCHITHUONGSKU] set scheme = N'" + this.scheme + "', thang = '" + this.thang + "', nam = '" + this.nam + "', diengiai = N'" + this.diengiai + "', " +
					"ngaysua = '" + this.getDateTime() + "', nguoisua = '" + this.userId + "', IsThung = '" + this.isThung + "' where pk_seq = '" + this.id + "'";
					
			System.out.println("1.Update: " + query);
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat TIEUCHITHUONGSKU: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete TIEUCHITHUONGSKU_NHOMSP where TIEUCHITHUONGSKU_FK = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat TIEUCHITHUONGSKU_NHOMSP: " + query;
				db.getConnection().rollback();
				return false;
			}
/////////////////Ngan da them/////////////////////////////
			
			if(this.daidienkinhdoanhimport != null)
			{
				if(this.daidienkinhdoanhimport.length != 0)
				{
					query = "delete TIEUCHITHUONGSKU_ddkd where tieuchithuongsku_fk='"+this.id+"'";
					
					System.out.println("___Chen TIEUCHITHUONGSKU_ddkdP: " + query);
					
					if(!db.update(query))
					{
						this.msg = "Khong the cap nhat TIEUCHITHUONGSKU_ddkd: " + query;
						db.getConnection().rollback();
						return false;
					}
					
					
					for(int i = 0; i < this.daidienkinhdoanhimport.length; i++)
					{
						if(this.daidienkinhdoanhimport.length > 0)
						{
	
							query = "insert TIEUCHITHUONGSKU_ddkd(TIEUCHITHUONGSKU_FK, ddkd_fk,phaidat) " +
									"values('" + this.id + "', '" + this.daidienkinhdoanhimport[i] + "', '" + this.phaidatimport[i] +"')";
							
							System.out.println("___Chen TIEUCHITHUONGSKU_ddkdP: " + query);
							if(this.daidienkinhdoanhimport[i] != null && this.phaidatimport[i]!= null)
							{
								if(!db.update(query))
								{
									this.msg = "Khong the tao moi TIEUCHITHUONGSKU_ddkd: " + query;
									db.getConnection().rollback();
									return false;
								}
							}
							else
								System.out.println("bang null");
						}
					}
				}
			}
			
			///////////////////////////////////////////////////////////
			if(nspId != null)
			{
				for(int i = 0; i < nspId.length; i++)
				{
					if(nspId[i].length() > 0)
					{
						if(nspTrongso[i].length() <= 0)
							nspTrongso[i] = "0";
						if(nspTumuc[i].length() <= 0)
							nspTumuc[i] = "0";
						if(nspDenmuc[i].length() <= 0)
							nspDenmuc[i] = "9999999";
						
						query = "insert TIEUCHITHUONGSKU_NHOMSP(TIEUCHITHUONGSKU_FK, NHOMSP_FK, TONGLUONG, TRONGSO, TUMUC, DENMUC) " +
								"values('" + this.id + "', '" + nspId[i] + "', '" + nspTongluong[i] + "', '" + nspTrongso[i] + "', '" + nspTumuc[i] + "', '" + nspDenmuc[i] + "')";
				
						System.out.println("___Chen TIEUCHITHUONGSKU_NHOMSP: " + query);
						if(!db.update(query))
						{
							this.msg = "Khong the tao moi TIEUCHITHUONGSKU_NHOMSP: " + query;
							db.getConnection().rollback();
							return false;
						}
					
						if(nspTSR[i].length() > 0)
						{
							if(nspTTDSR[i].length() <= 0 )
								nspTTDSR[i] = "0";
							if(nspTSS[i].length() <= 0 )
								nspTSS[i] = "0";
							if(nspTTDSS[i].length() <= 0 )
								nspTTDSS[i] = "0";
							if(nspTASM[i].length() <= 0 )
								nspTASM[i] = "0";
							if(nspTTDASM[i].length() <= 0 )
								nspTTDASM[i] = "0";
							
							query = "update NHOMSANPHAM_SANPHAM set Thuong_Sr = '" + nspTSR[i] + "', Thuong_Td_Sr = '" + nspTTDSR[i] + "', Thuong_Ss = '" + nspTSS[i] + "', Thuong_Td_Ss = '" + nspTTDSS[i] + "'" +
									", thuong_ASM = '" + nspTASM[i] + "', thuong_Td_ASM = '" + nspTTDASM[i] + "' where NSP_FK = '" + nspId[i] + "'";
							System.out.println("___Chen TIEUCHITHUONGSKU_NHOMSP: " + query);
							if(!db.update(query))
							{
								this.msg = "Khong the tao moi TIEUCHITHUONGSKU_NHOMSP: " + query;
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
		catch (SQLException e)
		{
			try 
			{
				db.getConnection().rollback();
			} catch (SQLException e1) {}
			
			System.out.println("115.Error: " + e.getMessage());
		}
		
		return true;
	}

	public void init() 
	{
		String query = "select scheme, thang, nam,diengiai, phaidat, thuong, isnull(thuongtoida, '') as thuongtoida, thuongGS, isnull(thuongtoidaGS, '') as thuongtoidaGS, IsThung " +
				"from tieuchithuongSKU where pk_seq = '" + this.id + "'";
		
		System.out.println("__Khoi tao tieu chi thuong: " + query);
		
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				while(rs.next())
				{
					this.scheme = rs.getString("scheme");
					this.thang = rs.getString("thang");
					this.nam = rs.getString("nam");
					this.phaidat = rs.getString("phaidat");
					this.thuong = rs.getString("thuong");
					this.thuongtoida = rs.getString("thuongtoida");
					this.thuongGS = rs.getString("thuongGS");
					this.diengiai= rs.getString("diengiai");
					this.thuongtoidaGS = rs.getString("thuongtoidaGS");
					this.isThung = rs.getString("IsThung");
				}
				rs.close();
			} 
			catch (SQLException e)
			{
				System.out.println("115.Error Meg: " + e.getMessage());
			}
		}
		
		this.createNsp();
	}
	
	public void initUpdate() 
	{
		this.createNsp();
	}
	
	private void createNsp() 
	{
		String query = "select pk_seq as nspId, diengiai as nspTen from nhomsanpham where nguoitao is not null and type = '4' and trangthai='1'";
		this.nhomspRs = db.getScrol(query);	
		
		query = "select a.trongso, isnull(a.tumuc, '0') as tumuc, isnull(a.denmuc, '0') as denmuc, b.pk_seq nspId, b.ten " +
						"from tieuchithuongSKU_nhomsp a inner join nhomsanpham b on a.nhomsp_fk = b.pk_seq " +
						"where tieuchithuongsku_fk = '" + this.id + "'";
		
		List<INhomsp> nhomSpList = new ArrayList<INhomsp>();
		
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				INhomsp nsp = null;
				while(rs.next())
				{
					nsp = new Nhomsp();
					nsp.setId(rs.getString("nspId"));
					nsp.setDiengiai(rs.getString("ten"));
					nsp.setTrongso(rs.getString("trongso"));
					nsp.setTumuc(rs.getString("tumuc"));
					nsp.setdenmuc(rs.getString("denmuc"));
					
					INhomspDetail nspDetail = new NhomspDetail();
					nspDetail.setId(rs.getString("nspId"));
					nspDetail.setDiengiai(nsp.getDiengiai());
					
					query = "select b.ma, b.ten, a.thuong_ss, a.thuong_td_ss, a.thuong_sr, a.thuong_td_sr, a.thuong_asm, a.thuong_td_asm " +
							"from nhomsanpham_sanpham a inner join sanpham b on a.sp_fk = b.pk_seq where a.nsp_fk = '" + nsp.getId() + "'";
					System.out.println("query:...."+query);
					ResultSet rsDetail = db.get(query);
					if(rsDetail != null)
					{
						List<ISanpham> spList = new ArrayList<ISanpham>();
						ISanpham sp = null;
						while(rsDetail.next())
						{
							sp = new Sanpham();
							sp.setMasanpham(rsDetail.getString("ma"));
							sp.setTensanpham(rsDetail.getString("ten"));
							sp.setThuongSS(rsDetail.getString("thuong_ss"));
							sp.setThuongTDSS(rsDetail.getString("thuong_td_ss"));
							sp.setThuongSR(rsDetail.getString("thuong_sr"));
							sp.setThuongTDSR(rsDetail.getString("thuong_td_sr"));
							sp.setThuongASM(rsDetail.getString("thuong_asm"));
							sp.setThuongTDASM(rsDetail.getString("thuong_td_asm"));

							spList.add(sp);
						}
						rsDetail.close();
						
						nspDetail.setSpList(spList);
						nsp.setSpDetail(nspDetail);
					}
					
					nhomSpList.add(nsp);
				}
				rs.close();
			} 
			catch (SQLException e) {
				
				System.out.println("loi.............."+e.toString());
			}
		}
		
		this.nhomSpList = nhomSpList;
	}

	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	
	public String getThuongGS()
	{
		return this.thuongGS;
	}

	
	public void setThuongGS(String thuong)
	{
		this.thuongGS = thuong;
	}

	public String getThuongtoidaGS()
	{
		return this.thuongtoidaGS;
	}

	public void setThuongtoidaGS(String thuongtoida) 
	{
		this.thuongtoidaGS = thuongtoida;
	}

	public String getScheme()
	{
		return this.scheme;
	}

	public void setScheme(String scheme) 
	{
		this.scheme = scheme;
	}

	
	public String[] getdaidienkinhdoanhimport() {
		
		return this.daidienkinhdoanhimport;
	}

	
	public void setdaidienkinhdoanhimport(String[] daidienkinhdoanhimport) 
	{
		
		this.daidienkinhdoanhimport=daidienkinhdoanhimport;
	}

	
	public String[] getPhaidatimport() {
		
		return this.phaidatimport;
	}

	
	public void setPhaidatimport(String[] Phaidatimport) {
		this.phaidatimport=Phaidatimport;
		
	}

	public String getIsthung()
	{
		return this.isThung;
	}

	public void setIsthung(String isthung) 
	{
		this.isThung = isthung;
	}
	
	public void setNhomspRs(ResultSet nspRs) 
	{
		this.nhomspRs = nspRs;
	}

	public ResultSet getNhomspRs()
	{
		return this.nhomspRs;
	}

}
