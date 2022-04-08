package geso.dms.distributor.beans.chuyenkho.imp;

import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;
import geso.dms.distributor.beans.chuyenkho.IErpChuyenkhoNpp;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

public class ErpChuyenkhoNpp implements IErpChuyenkhoNpp
{
	String userId;
	String id;
	
	String ngayyeucau;
	String ghichu;

	String msg;
	String trangthai;
	
	String khoXuatId;
	ResultSet khoXuatRs;
	
	String khoNhanId;
	ResultSet khoNhanRs;
	
	String khId;
	ResultSet khRs;
	ResultSet dvtRs;
	
	String kbhId;
	ResultSet kbhRs;

	String lenhdieudong, lddcua, lddveviec, ngaylenhdieudong, sohopdong,ngayhopdong, nguoivanchuyen, ptvanchuyen;
	
	ResultSet spRs;
	
	
	String[] spId;
	String[] spMa;
	String[] spTen;
	String[] spDonvi;
	String[] spSoluong;
	String[] spGianhap;
	String[] spSolo;
	String[] spTonkho;
	String[] spBooked;
	String[] spNgaysanxuat;
	String[] spNgayhethan;
	
	String nppId;
	String nppTen;
	String sitecode;
	String sochungtu;
	String dungchung;
	Hashtable<String, String> sanpham_soluong;
	
	dbutils db;
	Utility util;
	
	public ErpChuyenkhoNpp()
	{
		this.id = "";
		this.ngayyeucau = getDateTime();
		this.ghichu = "";
		this.khoNhanId = "";
		this.khoXuatId = "";
		this.khId = "";
		this.kbhId = "";
		this.msg = "";
		this.trangthai = "0";
		
		this.lenhdieudong="";
		this.lddcua="";
		this.lddveviec="";
		this.ngaylenhdieudong="";
		this.sohopdong="";
		this.ngayhopdong="";
		this.nguoivanchuyen="";
		this.ptvanchuyen="";
		
		this.sanpham_soluong = new Hashtable<String, String>();
		
		this.sochungtu ="";
		this.dungchung = "";
		this.db = new dbutils();
		this.util = new Utility();
	}
	
	public ErpChuyenkhoNpp(String id)
	{
		this.id = id;
		this.ngayyeucau = getDateTime();
		this.ghichu = "";
		this.khoNhanId = "";
		this.khoXuatId = "";
		this.khId = "";
		this.kbhId = "";
		this.msg = "";
		this.trangthai = "0";
		
		this.lenhdieudong="";
		this.lddcua="";
		this.lddveviec="";
		this.ngaylenhdieudong="";
		this.sohopdong="";
		this.ngayhopdong="";
		this.nguoivanchuyen="";
		this.ptvanchuyen="";
		
		this.sanpham_soluong = new Hashtable<String, String>();
		
		this.sochungtu ="";
		this.dungchung = "";
		this.db = new dbutils();
		this.util = new Utility();
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

	public void setId(String Id) 
	{
		this.id = Id;
	}

	public String getNgayyeucau() 
	{
		return this.ngayyeucau;
	}

	public void setNgayyeucau(String ngayyeucau) 
	{
		this.ngayyeucau = ngayyeucau;
	}

	public String getKhoNhapId()
	{
		return this.khoNhanId;
	}

	public void setKhoNhapId(String khonhaptt) 
	{
		this.khoNhanId = khonhaptt;
	}

	public ResultSet getKhoNhapRs() 
	{
		return this.khoNhanRs;
	}

	public void setKhoNHapRs(ResultSet khonhapRs) 
	{
		this.khoNhanRs = khonhapRs;
	}
	
	public String getMsg() 
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}

	
	public boolean createNK() 
	{
		if(this.ngayyeucau.trim().length() <= 0)
		{
			this.msg = "Vui lòng nhập ngày chuyển";
			return false;
		}
		
		if( this.khoXuatId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn kho xuất";
			return false;
		}
		
		if( this.khId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn đối tượng nhận";
			return false;
		}
		
		if( this.kbhId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn kênh bán hàng";
			return false;
		}
		
		if( this.sochungtu.trim().length() <= 0 )
		{
			this.msg = "Vui lòng nhập số chứng từ";
			return false;
		}
		
		boolean coSP = false;
		for(int i = 0; i < spMa.length; i++)
		{
			if(spMa[i].trim().length() > 0 && spSoluong[i].trim().length() > 0  )
			{
				if( spSoluong[i].trim().length() > 0 )
				{
					coSP = true;
				}
			}
		}
		
		if(!coSP)
		{
			this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm xuất";
			return false;
		}
		
		//CHECK TRUNG MA 
		for(int i = 0; i < spMa.length - 1; i++)
		{
			for(int j = i + 1; j < spMa.length; j++)
			{
				if(spMa[i].trim().length() > 0 && spMa[j].trim().length() > 0 )
				{
					if( spMa[i].trim().equals(spMa[j].trim()) )
					{
						this.msg = "Sản phẩm ( " + spTen[i] + " )  đã bị trùng.";
						return false;
					}
				}
			}
		}	
		
		if(this.sanpham_soluong == null)
		{
			this.msg = "Vui lòng kiểm tra lại số lô xuất";
			return false;
		}
		
		int countsp = 0 ;
		try
		{
			
			db.getConnection().setAutoCommit(false);
			String query = " insert ERP_CHUYENKHONPP(SoChungTu,ngaychuyen, ghichu, trangthai, khoxuat_fk, kbh_fk, npp_fk, npp_dat_fk, ngaytao, nguoitao, ngaysua, nguoisua," +
			               "        lenhdieudong, lddcua, lddveviec, ngaydieudong, sohopdong,ngayhopdong, nguoivanchuyen, ptvanchuyen) " +
						   " values('"+this.sochungtu+"','" + this.ngayyeucau + "', N'" + this.ghichu + "', '0', '" +  this.khoXuatId + "', '" + this.kbhId + "', " + this.nppId + ", '" + this.khId + "', '" + getDateTime() + "', '" + this.userId + "', '" + getDateTime() + "', '" + this.userId + "'," +
						   "        N'"+ this.lenhdieudong +"', N'"+ this.lddcua +"',N'"+ this.lddveviec +"','"+ this.ngaylenhdieudong +"',N'"+ this.sohopdong +"','"+ this.ngayhopdong +"',N'"+ this.nguoivanchuyen +"', N'"+ this.ptvanchuyen +"' )";
			
			System.out.println("1.Insert CK: " + query);
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ERP_CHUYENKHONPP " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query =  "select isnull(dungchungkenh, 0) as dungchungkenh from NHAPHANPHOI where pk_seq = '" + this.nppId + "' ";
			ResultSet rsKENH = db.get(query);
			 this.dungchungkenh = "0";
			if(rsKENH != null)
			{
				try 
				{
					if(rsKENH.next())
					{
						dungchungkenh = rsKENH.getString("dungchungkenh");
					}
					rsKENH.close();
				} 
				catch (Exception e) {e.printStackTrace(); }
			}
			if(this.dungchungkenh.equals("1"))
			{
				 this.kbhId ="100025";
			}
			
			for(int i = 0; i < spMa.length; i++)
			{
				if(spMa[i].trim().length() > 0 && spSoluong[i].trim().length() > 0 )
				{
					
					query =    "select sp.ten, dv.donvi, case when sp.dvdl_fk != dv.pk_seq   " +
						   "	then ISNULL( ( select soluong2 / soluong1 from QUYCACH where SANPHAM_FK = sp.PK_SEQ and DVDL1_FK = sp.DVDL_FK and DVDL2_FK = dv.pk_seq ), -1 ) else 1 end as quycach   "  +
						   "from SANPHAM sp, DONVIDOLUONG dv " +
						   "where sp.MA = '" + spMa[i].trim() + "' and dv.donvi = N'" + spDonvi[i].trim() + "'   ";
					
					System.out.println("-----CHECK QUY CACH: " + query );
					ResultSet rs = db.get(query);
					if(rs.next())
					{
						if(rs.getDouble("quycach") <= 0)
					{
							this.msg = "Sản phẩm ( " + rs.getString("ten") + " ) với đơn vị đặt ( " + rs.getString("donvi") + " ) chưa thiết lập quy cách trong DLN ";
							rs.close();
							db.getConnection().rollback();
							return false;
						}
					}
					rs.close();
					
					
					query = "insert ERP_CHUYENKHONPP_SANPHAM( chuyenkho_fk, SANPHAM_FK, soluongchuyen, dongia, dvdl_fk ) " +
							"select ident_current('ERP_CHUYENKHONPP'), pk_seq, '" + spSoluong[i].replaceAll(",", "") + "', '" + spGianhap[i].replaceAll(",", "") + "', ISNULL( ( select pk_Seq from DONVIDOLUONG where donvi = N'" + spDonvi[i].trim() + "' ), DVDL_FK ) " +
							"from SANPHAM where MA = '" + spMa[i].trim() + "' ";
					
					System.out.println("3.Insert CK - SP: " + query);
					if(!db.update(query))
					{
						this.msg = "Khong the tao moi ERP_CHUYENKHONPP_SANPHAM: " + query;
						db.getConnection().rollback();
						return false;
					}
					
					if(this.sanpham_soluong != null)
					{
						Enumeration<String> keys = this.sanpham_soluong.keys();
						double totalCT = 0;
						
						while(keys.hasMoreElements())
						{
							String key = keys.nextElement();
							
							if(key.startsWith( spMa[i]+"__") )
							{
								String[] _sp = key.split("__");
								
								String _soluongCT = "0"; 
								if(this.sanpham_soluong.get(key) != null)
								{
									_soluongCT = this.sanpham_soluong.get(key).replaceAll(",", "");
								}
								
								totalCT += Double.parseDouble(_soluongCT);
								
								if(!_soluongCT.equals("0"))
								{
									//CHECK TON KHO
									query = "select AVAILABLE, NGAYHETHAN from NHAPP_KHO_CHITIET " +
											"where KHO_FK = '" + this.khoXuatId + "' and NPP_FK = '" + this.nppId + "' and KBH_FK = '" + this.kbhId + "' and SOLO = '" + _sp[1] + "' and NGAYHETHAN = '" + _sp[2] + "' " +
											"	and SANPHAM_FK = ( select pk_seq from SANPHAM where ma = '" + spMa[i] + "' ) ";
									
									System.out.println("_____"+query);
									
									ResultSet rsTK = db.get(query);
									double avai = 0;
									String ngayhethan = "";
									if(rsTK.next())
									{
										avai = rsTK.getDouble("AVAILABLE");
										ngayhethan = rsTK.getString("NGAYHETHAN");
									}
									rsTK.close();
									
									if( Double.parseDouble(_soluongCT) > avai )
									{
										this.msg = "Sản phẩm (" + spTen[i] + ") với số lô (" + _sp[1] + "), số lượng xuất (" + _soluongCT + ") còn tối đa (" + avai + "). ";
										db.getConnection().rollback();
										return false;
									}
									
									query = "insert ERP_CHUYENKHONPP_SANPHAM_CHITIET( chuyenkho_fk, SANPHAM_FK, solo, ngayhethan, soluong ) " +
											"select IDENT_CURRENT('ERP_CHUYENKHONPP'), pk_seq, N'" + _sp[1] + "', '" + ngayhethan + "', '" + _soluongCT.replaceAll(",", "") + "' " +
											"from SANPHAM where MA = '" + spMa[i] + "' ";
									
									System.out.println("1.2.Insert YCCK - SP - CT: " + query);
									if(db.updateReturnInt(query)!=1)
									{
										this.msg = "Khong the tao moi ERP_CHUYENKHONPP_SANPHAM_CHITIET: " + query;
										db.getConnection().rollback();
										return false;
									}
									
									countsp ++;
									query = "Update NHAPP_KHO_CHITIET set booked = booked + '" + _soluongCT.replaceAll(",", "") + "', AVAILABLE = AVAILABLE - '" + _soluongCT.replaceAll(",", "") + "' " +
											"where KHO_FK = '" + this.khoXuatId + "' and NPP_FK = '" + this.nppId + "' and KBH_FK = '" + this.kbhId + "' and SOLO = '" + _sp[1] + "' and SANPHAM_FK = ( select pk_seq from SANPHAM where MA = '" + spMa[i] + "' )  and NgayHetHan='"+ngayhethan+"' ";
									System.out.println("---CAP NHAT BOOKED: " + query );
									if(db.updateReturnInt(query)!=1)
									{
										this.msg = "Khong the cap nhat NHAPP_KHO_CHITIET: " + query;
										db.getConnection().rollback();
										return false;
									}
									
									
									query = "Update NHAPP_KHO set booked = booked + '" + _soluongCT.replaceAll(",", "") + "', AVAILABLE = AVAILABLE - '" + _soluongCT.replaceAll(",", "") + "' " +
											"where KHO_FK = '" + this.khoXuatId + "' and NPP_FK = '" + this.nppId + "' and KBH_FK = '" + this.kbhId + "' and SANPHAM_FK = ( select pk_seq from SANPHAM where MA = '" + spMa[i] + "' )  ";
									System.out.println("---CAP NHAT BOOKED KHO TONG: " + query );
									if(db.updateReturnInt(query)!=1)
									{
										this.msg = "số lượng tồn sản phẩm "+spMa[i]+" không đủ vui lòng kiểm tra lại ";
										db.getConnection().rollback();
										return false;
									}
								}
								
							}
						}
						
						//NEU TONG SO LUONG CT MA KHONG BANG TONG LUONG XUAT THI KO CHO LUU
						if(totalCT != Double.parseDouble(spSoluong[i].replaceAll(",", "").trim()) )
						{
							this.msg = "Tổng xuất theo lô của sản phẩm ( " + spTen[i] + " ) ( " + totalCT + " ) phải bằng tổng số lượng xuất ( " + spSoluong[i] + " ) ";
							db.getConnection().rollback();
							return false;
						}
						
					}
					
				}
			}
			
			if (countsp - 1 > 10)// Nếu số dòng vượt quá 10 thì k cho lưu
			{
				this.msg = "TỔNG SỐ DÒNG CỦA PHIẾU XUẤT CHUYỂN NỘI BỘ KHÔNG ĐƯỢC PHÉP QUÁ 10 DÒNG.";
				db.getConnection().rollback();
				return false;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			e.printStackTrace();
			this.msg = "Exception: " + e.getMessage();
			return false;
		}
		
		return true;
	}

	public boolean updateNK() 
	{
		if(this.ngayyeucau.trim().length() <= 0)
		{
			this.msg = "Vui lòng nhập ngày chuyển";
			return false;
		}
		
		if( this.khoXuatId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn kho xuất";
			return false;
		}
		
		if( this.khId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn đối tượng nhận";
			return false;
		}
		
		if( this.kbhId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn kênh bán hàng";
			return false;
		}
		
		if( this.sochungtu.trim().length() <= 0 )
		{
			this.msg = "Vui lòng nhập số chứng từ";
			return false;
		}
		
		boolean coSP = false;
		for(int i = 0; i < spMa.length; i++)
		{
			if(spMa[i].trim().length() > 0 && spSoluong[i].trim().length() > 0  )
			{
				if( spSoluong[i].trim().length() > 0 )
				{
					coSP = true;
				}
			}
		}
		
		if(!coSP)
		{
			this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm xuất";
			return false;
		}
		
		//CHECK TRUNG MA 
		for(int i = 0; i < spMa.length - 1; i++)
		{
			for(int j = i + 1; j < spMa.length; j++)
			{
				if(spMa[i].trim().length() > 0 && spMa[j].trim().length() > 0 )
				{
					if( spMa[i].trim().equals(spMa[j].trim()) )
					{
						this.msg = "Sản phẩm ( " + spTen[i] + " )  đã bị trùng.";
						return false;
					}
				}
			}
		}	
		
		if(this.sanpham_soluong == null)
		{
			this.msg = "Vui lòng kiểm tra lại số lô xuất";
			return false;
		}
		
		int countsp=0;
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String query =	" Update ERP_CHUYENKHONPP set SoChungTu='"+this.sochungtu+"',ngaychuyen = '" + this.ngayyeucau + "', ghichu = N'" + this.ghichu + "', " +
							" khoxuat_fk = " + this.khoXuatId + ", kbh_fk = '" + this.kbhId + "', npp_fk = '" + this.nppId + "', npp_dat_fk = '" + this.khId + "', ngaysua = '" + getDateTime() + "', nguoisua = '" + this.userId + "', " + 
							" lenhdieudong = N'"+ this.lenhdieudong +"', lddcua= N'"+ this.lddcua +"', lddveviec=N'"+ this.lddveviec +"', ngaydieudong ='"+ this.ngaylenhdieudong +"' ," +
						    " sohopdong = N'"+ this.sohopdong +"' ,ngayhopdong ='"+ this.ngayhopdong +"', nguoivanchuyen=N'"+ this.nguoivanchuyen +"' , ptvanchuyen=N'"+ this.ptvanchuyen +"'" + 							
							" where pk_seq = '" + this.id + "' ";
		
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_CHUYENKHONPP " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query =  "select isnull(dungchungkenh, 0) as dungchungkenh from NHAPHANPHOI where pk_seq = '" + this.nppId + "' ";
			ResultSet rsKENH = db.get(query);
			 this.dungchungkenh = "0";
			if(rsKENH != null)
			{
				try 
				{
					if(rsKENH.next())
					{
						dungchungkenh = rsKENH.getString("dungchungkenh");
					}
					rsKENH.close();
				} 
				catch (Exception e) {e.printStackTrace(); }
			}
			if(this.dungchungkenh.equals("1"))
			{
				 this.kbhId ="100025";
			}
			
				
			//TANG KHO NGUOC LAI
			query = " update kho set kho.AVAILABLE = kho.AVAILABLE + CT.tongxuat, " +
					" 			   kho.BOOKED = kho.BOOKED - CT.tongxuat " +
					" from " +
					" ( " +
					" 	select a.khoxuat_fk as kho_fk, a.npp_fk, "+this.kbhId+" as kbh_fk, b.sanpham_fk, b.solo, SUM(b.soluong) as tongxuat,b.NgayHetHan  " +
					" 	from ERP_CHUYENKHONPP a inner join ERP_CHUYENKHONPP_SANPHAM_CHITIET b on a.pk_seq = b.chuyenkho_fk " +
					" 	where chuyenkho_fk = '" + this.id + "' " +
					" 	group by a.khoxuat_fk, a.npp_fk, a.kbh_fk, b.solo, b.sanpham_fk,b.NgayHetHan " +
					" ) " +
					" CT inner join NHAPP_KHO_CHITIET kho on CT.kho_fk = kho.KHO_FK  " +
					" 	and CT.sanpham_fk = kho.SANPHAM_FK and CT.solo = kho.SOLO and CT.NPP_FK = kho.npp_fk and CT.KBH_FK = kho.kbh_fk  and ct.NgayHetHan=kho.NgayHetHan ";
			System.out.println("---TANG KHO NGUOC LAI: " + query );
			if(db.updateReturnInt(query)<=0)
			{
				this.msg = "Không thể cập nhật NHAPP_KHO_CHITIET " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = " update kho set kho.AVAILABLE = kho.AVAILABLE + CT.tongxuat, " +
					" 			   kho.BOOKED = kho.BOOKED - CT.tongxuat " +
					" from " +
					" ( " +
					" 	select a.khoxuat_fk as kho_fk, a.npp_fk, "+this.kbhId+" as kbh_fk , b.sanpham_fk, SUM(b.soluong) as tongxuat  " +
					" 	from ERP_CHUYENKHONPP a inner join ERP_CHUYENKHONPP_SANPHAM_CHITIET b on a.pk_seq = b.chuyenkho_fk " +
					" 	where chuyenkho_fk = '" + this.id + "' " +
					" 	group by a.khoxuat_fk, a.npp_fk, a.kbh_fk, b.sanpham_fk " +
					" ) " +
					" CT inner join NHAPP_KHO kho on CT.kho_fk = kho.KHO_FK  " +
					" 	and CT.sanpham_fk = kho.SANPHAM_FK and CT.NPP_FK = kho.npp_fk and CT.KBH_FK = kho.kbh_fk ";
			System.out.println("---TANG KHO NGUOC LAI 2: " + query );
			if(db.updateReturnInt(query)<=0)
			{
				this.msg = "Không thể cập nhật NHAPP_KHO " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete ERP_CHUYENKHONPP_SANPHAM where chuyenkho_fk = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_CHUYENKHONPP_SANPHAM " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete ERP_CHUYENKHONPP_SANPHAM_CHITIET where chuyenkho_fk = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_CHUYENKHONPP_SANPHAM_CHITIET " + query;
				db.getConnection().rollback();
				return false;
			}
			
			for(int i = 0; i < spMa.length; i++)
			{
				if(spMa[i].trim().length() > 0 && spSoluong[i].trim().length() > 0 )
				{
					
					query =    "select sp.ten, dv.donvi, case when sp.dvdl_fk != dv.pk_seq   " +
						   "	then ISNULL( ( select soluong2 / soluong1 from QUYCACH where SANPHAM_FK = sp.PK_SEQ and DVDL1_FK = sp.DVDL_FK and DVDL2_FK = dv.pk_seq ), -1 ) else 1 end as quycach   "  +
						   "from SANPHAM sp, DONVIDOLUONG dv " +
						   "where sp.MA = '" + spMa[i].trim() + "' and dv.donvi = N'" + spDonvi[i].trim() + "'   ";
				
				System.out.println("-----CHECK QUY CACH: " + query );
				ResultSet rs = db.get(query);
				if(rs.next())
				{
					if(rs.getDouble("quycach") <= 0)
					{
							this.msg = "Sản phẩm ( " + rs.getString("ten") + " ) với đơn vị đặt ( " + rs.getString("donvi") + " ) chưa thiết lập quy cách trong DLN ";
						rs.close();
						db.getConnection().rollback();
						return false;
					}
				}
				rs.close();
					
					
					query = "insert ERP_CHUYENKHONPP_SANPHAM( chuyenkho_fk, SANPHAM_FK, soluongchuyen, dongia, dvdl_fk ) " +
							"select '" + this.id + "', pk_seq, '" + spSoluong[i].replaceAll(",", "") + "', '" + spGianhap[i].replaceAll(",", "") + "', ISNULL( ( select pk_Seq from DONVIDOLUONG where donvi = N'" + spDonvi[i].trim() + "' ), DVDL_FK ) " +
							"from SANPHAM where MA = '" + spMa[i].trim() + "' ";
					
					System.out.println("3.Insert CK - SP: " + query);
					if(!db.update(query))
					{
						this.msg = "Khong the tao moi ERP_CHUYENKHONPP_SANPHAM: " + query;
						db.getConnection().rollback();
						return false;
					}
					
					if(this.sanpham_soluong != null)
					{
						Enumeration<String> keys = this.sanpham_soluong.keys();
						double totalCT = 0;
						
						while(keys.hasMoreElements())
						{
							String key = keys.nextElement();
							
							if(key.startsWith( spMa[i]+"__") )
							{
								String[] _sp = key.split("__");
								
								String _soluongCT = "0"; 
								if(this.sanpham_soluong.get(key) != null)
								{
									_soluongCT = this.sanpham_soluong.get(key).replaceAll(",", "");
								}
								
								totalCT += Double.parseDouble(_soluongCT);
								
								if(!_soluongCT.equals("0"))
								{
									//CHECK TON KHO
									query = "select AVAILABLE, NGAYHETHAN from NHAPP_KHO_CHITIET " +
											"where KHO_FK = '" + this.khoXuatId + "' and NPP_FK = '" + this.nppId + "' and KBH_FK = '" + this.kbhId + "' and SOLO = '" + _sp[1] + "' and NGAYHETHAN = '" + _sp[2] + "' " +
											"	and SANPHAM_FK = ( select pk_seq from SANPHAM where ma = '" + spMa[i] + "' ) ";
									System.out.println("---CHECK TON KHO: " + query);
									ResultSet rsTK = db.get(query);
									double avai = 0;
									String ngayhethan = "";
									if(rsTK.next())
									{
										avai = rsTK.getDouble("AVAILABLE");
										ngayhethan = rsTK.getString("NGAYHETHAN");
									}
									rsTK.close();
									
									if( Double.parseDouble(_soluongCT) > avai )
									{
										this.msg = "Sản phẩm (" + spTen[i] + ") với số lô (" + _sp[1] + "), số lượng xuất (" + _soluongCT + ") còn tối đa (" + avai + "). ";
										db.getConnection().rollback();
										return false;
									}
									
									query = "insert ERP_CHUYENKHONPP_SANPHAM_CHITIET( chuyenkho_fk, SANPHAM_FK, solo, ngayhethan, soluong ) " +
											"select '" + this.id + "', pk_seq, N'" + _sp[1] + "', '" + ngayhethan + "', '" + _soluongCT.replaceAll(",", "") + "' " +
											"from SANPHAM where MA = '" + spMa[i] + "' ";
									
									System.out.println("1.2.Insert YCCK - SP - CT: " + query);
									
																		
									if(!db.update(query))
									{
										this.msg = "Khong the tao moi ERP_CHUYENKHONPP_SANPHAM_CHITIET: " + query;
										db.getConnection().rollback();
										return false;
									}
									countsp++; // tính tổng số dòng lưu theo số
												// lô
									
									query = "Update NHAPP_KHO_CHITIET set booked = booked + '" + _soluongCT.replaceAll(",", "") + "', AVAILABLE = AVAILABLE - '" + _soluongCT.replaceAll(",", "") + "' " +
											"where KHO_FK = '" + this.khoXuatId + "' and NPP_FK = '" + this.nppId + "' and KBH_FK = '" + this.kbhId + "' and SOLO = '" + _sp[1] + "' and SANPHAM_FK = ( select pk_seq from SANPHAM where MA = '" + spMa[i] + "' ) and NgayHetHan='"+ngayhethan+"' ";
									System.out.println("---CAP NHAT BOOKED: " + query );
									if(db.updateReturnInt(query)!=1)
									{
										this.msg = "Khong the cap nhat NHAPP_KHO_CHITIET: " + query;
										db.getConnection().rollback();
										return false;
									}
									
									
									query = "Update NHAPP_KHO set booked = booked + '" + _soluongCT.replaceAll(",", "") + "', AVAILABLE = AVAILABLE - '" + _soluongCT.replaceAll(",", "") + "' " +
											"where KHO_FK = '" + this.khoXuatId + "' and NPP_FK = '" + this.nppId + "' and KBH_FK = '" + this.kbhId + "' and SANPHAM_FK = ( select pk_seq from SANPHAM where MA = '" + spMa[i] + "' )  ";
									System.out.println("---CAP NHAT BOOKED KHO TONG: " + query );
									if(db.updateReturnInt(query)!=1)
									{
										this.msg = "Khong the cap nhat NHAPP_KHO: " + query;
										db.getConnection().rollback();
										return false;
									}
									
								}
								
							}
						}
						
						//NEU TONG SO LUONG CT MA KHONG BANG TONG LUONG XUAT THI KO CHO LUU
						if(totalCT != Double.parseDouble(spSoluong[i].replaceAll(",", "").trim()) )
						{
							this.msg = "Tổng xuất theo lô của sản phẩm ( " + spTen[i] + " ) ( " + totalCT + " ) phải bằng tổng số lượng xuất ( " + spSoluong[i] + " ) ";
							db.getConnection().rollback();
							return false;
						}
						
					}
					
				}				
			}
			
			System.out.println("count:"+countsp);
			if (countsp - 1 > 10)// Nếu số dòng vượt quá 10 thì k cho lưu
			{
				this.msg = "TỔNG SỐ DÒNG CỦA PHIẾU XUẤT CHUYỂN NỘI BỘ KHÔNG ĐƯỢC PHÉP QUÁ 10 DÒNG.";
				db.getConnection().rollback();
				return false;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			this.msg = "Exception: " + e.getMessage();
			e.printStackTrace();
			return false;
		}
		
		return true;
	}


	public void createRs() 
	{
		this.getNppInfo();
		System.out.println("---NPP ID: " + this.nppId+" USEDID: "+this.userId);
		
		String query = "select PK_SEQ, TEN from KHO where trangthai = '1' and pk_seq in "+util.quyen_kho(this.userId);
		System.out.println("__Int"+query);
		System.out.println("KHO: "+query);
		this.khoXuatRs = db.get(query);
		
		/*
		 * HCM chuyển hàng cho CN/DT trực thuộc nó
		 * HCM chuyển hàng cho HO
		 * Đồng Nai xuất chuyển hàng ngược lại cho HCM
		 * 
		 */
		query = 
						"select PK_SEQ, TEN from NHAPHANPHOI where pk_Seq=1  union all " +
						"	select PK_SEQ, TEN from NHAPHANPHOI where trangthai = '1' and loaiNPP not in ( 4, 5, 6 ) and pk_seq != '" + this.nppId + "' and tructhuoc_fk = '" + this.nppId + "' " +
						" union all " +
						" select PK_SEQ,TEN from NHAPHANPHOI where TRANGTHAI=1 and PK_SEQ in (select TRUCTHUOC_FK from NHAPHANPHOI where PK_SEQ='"+this.nppId+"') ";
		
		System.out.println("___"+query);
		
		this.khRs = db.get(query);
		
		this.dvtRs = db.getScrol("select PK_SEQ, DONVI from DONVIDOLUONG where trangthai = '1' ");
		
		//CHECK DUNG CHUNG KENH HAY KHONG
		query =  "select isnull(dungchungkenh, 0) as dungchungkenh from NHAPHANPHOI where pk_seq = '" + this.nppId + "' ";
		ResultSet rsKENH = db.get(query);
		 this.dungchungkenh = "0";
		if(rsKENH != null)
		{
			try 
			{
				if(rsKENH.next())
				{
					dungchungkenh = rsKENH.getString("dungchungkenh");
				}
				rsKENH.close();
			} 
			catch (Exception e) {e.printStackTrace(); }
		}
		
	/*	if(dungchungkenh.equals("1"))
			this.kbhRs = db.getScrol("select PK_SEQ, TEN from KENHBANHANG where trangthai = '1' and pk_seq = '100025' ");
		else*/
		
			this.kbhRs = db.getScrol("select PK_SEQ, TEN from KENHBANHANG where trangthai = '1' ");
		
	}

	private void initSANPHAM() 
	{
		String kenhId="100025";
		if(!this.dungchungkenh.equals("1"))
		{
			 kenhId="(select KBH_FK from ERP_CHUYENKHONPP where PK_SEQ= '" + this.id + "')";
		}
		
		String query =  
					"select b.MA, b.TEN, DV.donvi, a.soluongchuyen as soluong, a.dongia, 0 as chietkhau, ISNULL(b.trongluong, 0) as trongluong, ISNULL(b.thetich, 0) as thetich ," +
					"       a.soluongchuyen + ISNULL( ( select sum(available) " +
					"                                   from NHAPP_KHO" +
					"                                   where KHO_FK = (select KHOXUAT_FK from ERP_CHUYENKHONPP where PK_SEQ= '" + this.id + "') and sanpham_fk = b.pk_seq " +
					"                                          and NPP_FK = (select NPP_FK from ERP_CHUYENKHONPP where PK_SEQ= '" + this.id + "') " +
					"                                          and KBH_FK ="+kenhId+"   ), 0 ) as avai " +
					" from ERP_CHUYENKHONPP_SANPHAM a inner Join SanPham b on a.SANPHAM_FK = b.PK_SEQ    " +
					" 		INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ = a.DVDL_FK       " +
					"where a.chuyenkho_FK = '" + this.id + "' ";
		
		System.out.println("---INIT SP: " + query);
		ResultSet spRs = db.get(query);
		
		NumberFormat formater = new DecimalFormat("##,###,###");
		if(spRs != null)
		{
			try 
			{
				String spMA = "";
				String spTEN = "";
				String spDONVI = "";
				String spSOLUONG = "";
				String spGIANHAP = "";
				String spTONKHO = "";
				
				
				while(spRs.next())
				{
					spMA += spRs.getString("MA") + "__";
					spTEN += spRs.getString("TEN") + "__";
					spDONVI += spRs.getString("DONVI") + "__";
					spSOLUONG += formater.format(spRs.getDouble("SOLUONG")) + "__";
					spGIANHAP += formater.format(spRs.getDouble("DONGIA")) + "__";
					spTONKHO += formater.format(spRs.getDouble("avai")) + "__";
				}
				spRs.close();
				
				if(spMA.trim().length() > 0)
				{
					spMA = spMA.substring(0, spMA.length() - 2);
					this.spMa = spMA.split("__");
					
					spTEN = spTEN.substring(0, spTEN.length() - 2);
					this.spTen = spTEN.split("__");
					
					spDONVI = spDONVI.substring(0, spDONVI.length() - 2);
					this.spDonvi = spDONVI.split("__");
					
					spSOLUONG = spSOLUONG.substring(0, spSOLUONG.length() - 2);
					this.spSoluong = spSOLUONG.split("__");
					
					spGIANHAP = spGIANHAP.substring(0, spGIANHAP.length() - 2);
					this.spGianhap = spGIANHAP.split("__");
					
					spTONKHO = spTONKHO.substring(0, spTONKHO.length() - 2);
					this.spTonkho = spTONKHO.split("__");
					
				}
			} 
			catch (Exception e) 
			{
				System.out.println("115.Exception: " + e.getMessage());
				e.printStackTrace();
			}
		}
		
	}

	public void init() 
	{
		String query =  "select SoChungTu,ngaychuyen, ISNULL(ghichu, '') as ghichu, khoxuat_fk, kbh_fk, npp_dat_fk, trangthai, " +
						" lenhdieudong, lddcua, lddveviec, ngaydieudong, sohopdong,ngayhopdong, nguoivanchuyen, ptvanchuyen " +
						"from ERP_CHUYENKHONPP where pk_seq = '" + this.id + "'";
		System.out.println("____INIT NHAP KHO: " + query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				if(rs.next())
				{
					this.ngayyeucau = rs.getString("ngaychuyen");
					this.ghichu = rs.getString("ghichu");
					this.khoXuatId = rs.getString("khoxuat_fk");
					this.khId = rs.getString("npp_dat_fk");
					this.kbhId = rs.getString("kbh_fk");
					this.trangthai = rs.getString("trangthai");
					this.lenhdieudong =rs.getString("lenhdieudong")== null ? "":rs.getString("lenhdieudong") ;
					this.lddcua =rs.getString("lddcua")== null ? "":rs.getString("lddcua");
					this.lddveviec =rs.getString("lddveviec")== null ? "":rs.getString("lddveviec");
					this.ngaylenhdieudong =rs.getString("ngaydieudong")== null ? "":rs.getString("ngaydieudong");
					this.sohopdong =rs.getString("sohopdong")== null ? "":rs.getString("sohopdong");
					this.ngayhopdong =rs.getString("ngayhopdong")== null ? "":rs.getString("ngayhopdong");
					this.nguoivanchuyen =rs.getString("nguoivanchuyen")== null ? "":rs.getString("nguoivanchuyen");
					this.ptvanchuyen =rs.getString("ptvanchuyen")== null ? "":rs.getString("ptvanchuyen");
					
					this.sochungtu =rs.getString("sochungtu")== null ? "":rs.getString("sochungtu");
				
				}
				rs.close();
				
				//INIT SO LUONG
				query = "select sanpham_fk, (select MA from SANPHAM where pk_seq = a.sanpham_fk ) as spMA,  solo, soluong,a.NgayHetHan " +
						"from ERP_CHUYENKHONPP_SANPHAM_CHITIET a where chuyenkho_fk = '" + this.id + "'";
				System.out.println("---INIT SP: " + query);
				rs = db.get(query);
				if(rs != null)
				{
					Hashtable<String, String> sp_soluong = new Hashtable<String, String>();
					while(rs.next())
					{
						//System.out.println("---KEY BEAN: " + rs.getString("sanpham_fk") + "__" + rs.getString("LOAI") + "__" + rs.getString("SCHEME") + "__" + rs.getString("solo"));
						sp_soluong.put(rs.getString("spMA") + "__" + rs.getString("solo") + "__" + rs.getString("NgayHetHan")  , rs.getString("soluong") );
					}
					rs.close();
					
					this.sanpham_soluong = sp_soluong;
				}
				
			} 
			catch (Exception e) 
			{
				System.out.println("---LOI INIT: " + e.getMessage());
				e.printStackTrace();
			}
		}
		
		System.out.println("---KHO XUAT: " + this.khoXuatId);
		this.createRs();
		
		this.initSANPHAM();
		
	}

	public void DBclose() {
		
		try{
			
			if(khoNhanRs!=null){
				khoNhanRs.close();
			}
			
			this.db.shutDown();
			
		}catch(Exception er)
		{
			er.printStackTrace();
		}
	}
	
	public String getDateTime() 
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

	public String getGhichu() {
		
		return this.ghichu;
	}

	public void setGhichu(String ghichu) {
		
		this.ghichu = ghichu;
	}

	
	public String[] getSpId() {
		
		return this.spId;
	}

	
	public void setSpId(String[] spId) {
		
		this.spId = spId;
	}

	
	public String[] getSpMa() {
		
		return this.spMa;
	}

	
	public void setSpMa(String[] spMa) {
		
		this.spMa = spMa;
	}

	
	public String[] getSpTen() {
		
		return this.spTen;
	}

	
	public void setSpTen(String[] spTen) {
		
		this.spTen = spTen;
	}

	
	public String[] getSpDonvi() {
		
		return this.spDonvi;
	}

	
	public void setSpDonvi(String[] spDonvi) {
		
		this.spDonvi = spDonvi;
	}

	
	public String[] getSpSoluong() {
		
		return this.spSoluong;
	}

	
	public void setSpSoluong(String[] spSoluong) {
		
		this.spSoluong = spSoluong;
	}

	
	public String[] getSpGianhap() {
		
		return this.spGianhap;
	}

	
	public void setSpGianhap(String[] spGianhap) {
		
		this.spGianhap = spGianhap;
	}

	
	public String[] getSpSolo() {
		
		return this.spSolo;
	}

	
	public void setSpSolo(String[] spSolo) {
		
		this.spSolo = spSolo;
	}

	
	public String[] getSpNgaysanxuat() {
		
		return this.spNgaysanxuat;
	}

	
	public void setSpNgaysanxuat(String[] spNgaysanxuat) {
		
		this.spNgaysanxuat = spNgaysanxuat;
	}

	
	public String[] getSpNgayhethan() {
		
		return this.spNgayhethan;
	}

	
	public void setSpNgayhethan(String[] spNgayhethan) {
		
		this.spNgayhethan = spNgayhethan;
	}

	
	public String[] getSpTonkho() {

		return this.spTonkho;
	}


	public void setSpTonkho(String[] spTonkho) {
		
		this.spTonkho = spTonkho;
	}

	
	public String[] getSpBooked() {

		return this.spBooked;
	}


	public void setSpBooked(String[] spBooked) {
		
		this.spBooked = spBooked;
	}

	
	public ResultSet getSanphamRs() {

		return this.spRs;
	}


	public void setSanphamRs(ResultSet spRs) {
		
		this.spRs = spRs;
	}

	
	public String getKhoXuatId() {
		
		return this.khoXuatId;
	}

	
	public void setKhoXuatId(String khoxuattt) {
		
		this.khoXuatId = khoxuattt;
	}

	
	public ResultSet getKhoXuatRs() {
		
		return this.khoXuatRs;
	}

	
	public void setKhoXuatRs(ResultSet khoxuatRs) {
		
		this.khoXuatRs = khoxuatRs;
	}

	
	public String getKhId() {
		
		return this.khId;
	}

	
	public void setKhId(String khId) {
		
		this.khId = khId;
	}

	
	public ResultSet getKhRs() {
		
		return this.khRs;
	}

	
	public void setKhRs(ResultSet khRs) {
		
		this.khRs = khRs;
	}

	
	public ResultSet getDvtRs() {

		return this.dvtRs;
	}


	public void setDvtRs(ResultSet dvtRs) {

		this.dvtRs = dvtRs;
	}

	
	public String getKbhId() {
		
		return this.kbhId;
	}

	
	public void setKbhId(String kbhId) {
		
		this.kbhId = kbhId;
	}

	
	public ResultSet getKbhRs() {
		
		return this.kbhRs;
	}

	
	public void setKbhRs(ResultSet kbhRs) {
		
		this.kbhRs = kbhRs;
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
	
	public String getLenhdieudong() 
	{
		return this.lenhdieudong;
	}

	public void setLenhdieudong(String lenhdieudong) 
	{
		this.lenhdieudong =lenhdieudong;
		
	}

	public String getLDDcua() 
	{
		return this.lddcua;
	}

	public void setLDDcua(String LDDcua) 
	{
		this.lddcua= LDDcua;
		
	}

	public String getLDDveviec() 
	{
		return this.lddveviec;
	}

	public void setLDDveviec(String LDDveviec) 
	{
		this.lddveviec= LDDveviec;
		
	}

	public String getNgaydieudong() 
	{
		return this.ngaylenhdieudong;
	}

	public void setNgaydieudong(String ngaydieudong) 
	{
		this.ngaylenhdieudong =ngaydieudong;
		
	}

	public String getNguoivanchuyen() 
	{
		return this.nguoivanchuyen;
	}

	public void setNguoivanchuyen(String nguoivanchuyen) 
	{
		this.nguoivanchuyen = nguoivanchuyen;
		
	}

	public String getPtvanchuyen() 
	{
		return this.ptvanchuyen;
	}

	public void setPtvanchuyen(String ptvanchuyen) 
	{
		this.ptvanchuyen = ptvanchuyen;
		
	}

	public String getSohopdong() 
	{
		return this.sohopdong;
	}

	public void setSohopdong(String sohopdong) 
	{
		this.sohopdong = sohopdong;
		
	}

	public String getNgayhopdong() 
	{
		return this.ngayhopdong;
	}

	public void setNgayhopdong(String ngayhopdong) 
	{
		this.ngayhopdong = ngayhopdong;
		
	}
	
	private void getNppInfo()
	{		
		//Phien ban moi
		geso.dms.distributor.util.Utility util=new geso.dms.distributor.util.Utility();
		this.nppId=util.getIdNhapp(this.userId);
		this.nppTen=util.getTenNhaPP();
		//this.dangnhap = util.getDangNhap();
		this.sitecode=util.getSitecode();
	}

	
	public String getSoChungTu()
	{
		return sochungtu;
	}
	public void setSoChungTu(String sochungtu)
	{
		this.sochungtu=sochungtu;
	}

	String[] spNgayHetHan;

	public String[] getSpNgayHetHan()
	{
		return spNgayHetHan;
	}

	public void setSpNgayHetHan(String[] ngayHetHan)
    {
		this.spNgayHetHan = ngayHetHan;
    }
	
	
	public Hashtable<String, String> getSanpham_Soluong() {
		
		return this.sanpham_soluong;
	}

	
	public void setSanpham_Soluong(Hashtable<String, String> sp_soluong) {
		
		this.sanpham_soluong = sp_soluong;
	}
	
	public ResultSet getSoloTheoSp(String spMa, String tongluong)
	{
		if(this.nppId.trim().length() > 0 && this.kbhId.trim().length() > 0 )
		{
			tongluong = tongluong.replaceAll(",", "");
			
			String kenhId="100025";
			if(!this.dungchungkenh.equals("1"))
			{
				 kenhId=this.kbhId ;
			}
			
			//System.out.println("---TONG LUONG: " + tongluong );
			
			String query = "select AVAILABLE + ISNULL( ( select sum(soluong) from ERP_CHUYENKHONPP_SANPHAM_CHITIET where chuyenkho_fk = '" + ( this.id.trim().length() > 0 ? this.id : "-1" ) + "' and sanpham_fk = ct.sanpham_fk and solo = ct.solo  and NgayHetHan=ct.NgayHetHan ), 0 ) as AVAILABLE, NGAYHETHAN, SOLO " +
						   "from NHAPP_KHO_CHITIET ct " +
						   "where KHO_FK = '" + this.khoXuatId + "' and KBH_FK ="+kenhId+"  and NPP_FK = '" + this.nppId + "' and SANPHAM_FK = ( select pk_seq from SANPHAM where ma = '" + spMa + "' ) " +
   		" and ( AVAILABLE + ISNULL( ( select sum(soluong) from ERP_CHUYENKHONPP_SANPHAM_CHITIET where chuyenkho_fk = '" + ( this.id.trim().length() > 0 ? this.id : "-1" ) + "' and sanpham_fk = ( select pk_seq from SANPHAM where ma = '" + spMa + "' ) and solo = ct.solo  and NgayHetHan=ct.NgayHetHan   ), 0 ) ) > 0 " +
						   " order by NGAYHETHAN asc ";
			
			System.out.println("----LAY SO LO: " + query );
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
						query2 += "select '" + avai + "' as AVAILABLE, '" + rs.getString("NGAYHETHAN") + "' as NGAYHETHAN, '" + rs.getString("SOLO") + "' as SOLO, '" + slg + "' as tuDEXUAT union ALL ";
					}
					else
					{
						query2 += "select '" + avai + "' as AVAILABLE, '" + rs.getString("NGAYHETHAN") + "' as NGAYHETHAN, '" + rs.getString("SOLO") + "' as SOLO, '' as tuDEXUAT union ALL ";
					}
					
				}
				rs.close();
			} 
			catch (Exception e) 
			{
				System.out.println("EXCEPTION INIT SOLO: " + e.getMessage());
				e.printStackTrace();
			}
			
			if(query2.trim().length() > 0)
			{
				query2 = query2.substring(0, query2.length() - 10);
				//System.out.println("---TU DONG DE XUAT BIN - LO: " + query2 );
				return db.get(query2);
			}
		}
		
		return null;
	}
	
	String dungchungkenh;

	public String getDungchungkenh()
  {
  	return dungchungkenh;
  }

	public void setDungchungkenh(String dungchungkenh)
  {
  	this.dungchungkenh = dungchungkenh;
  }
	
}
