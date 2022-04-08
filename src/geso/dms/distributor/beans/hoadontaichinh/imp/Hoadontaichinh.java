package geso.dms.distributor.beans.hoadontaichinh.imp;

import geso.dms.distributor.beans.hoadontaichinh.IHoadontaichinh;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

public class Hoadontaichinh implements IHoadontaichinh
{
	
	String userId;
	String id;
	
	String[] tichluy_scheme;
	String[] tichluy_tongtien;
	String[] tichluy_vat;
	
	String ngayxuatHD;
	String ngayghinhanCN;
	String ghichu;
	
	String kyhieuhoadon;
	String sohoadon;
	String hinhthuctt;
	String nguoimua;
	String innguoimua;
	
	String chietkhau;

	String loaidonhang;  //0 đơn đặt hàng, 1 đơn hàng khuyến mại ứng hàng, 3 đơn hàng khuyến mại tích lũy, 4 đơn hàng trưng bày, 5 đơn hàng khác
	
	String msg;
	String trangthai;
	String khoNhanId;
	ResultSet khoNhanRs;
	
	String nppId;
	ResultSet nppRs;
	
	String khId;
	ResultSet khRs;
	
	String ddhId;
	ResultSet ddhRs;

	String[] spId;
	String[] spMa;
	String[] spTen;
	String[] spDonvi;
	String[] spDongia;
	String[] spChietkhau;
	String[] spSoluong;
	String[] spLoai;
	String[] spSCheme;
	String[] spVat;
	String[] spThue;
	
	String[] dhCk_diengiai;
	String[] dhCk_giatri;
	String[] dhCk_loai;
	
	String bvat;
	String totalCHIETKHAU;
	String thueVAT;
	String avat;
	String mavuviec;
	public String getMavuviec() {
		return mavuviec;
	}

	public void setMavuviec(String mavuviec) {
		this.mavuviec = mavuviec;
	}


	Hashtable<String, String> sanpham_soluong;
	
	dbutils db;
	
	public Hoadontaichinh()
	{
		this.id = "";
		this.ngayxuatHD = getDateTime();
		this.ngayghinhanCN = getDateTime();
		this.ghichu = "";
		this.kyhieuhoadon = "";
		this.hinhthuctt = "";
		this.sohoadon= "";
		this.khoNhanId = "";
		this.nppId = "";
		this.chietkhau="";
		this.khId ="";
		this.msg = "";
		this.trangthai = "0";
		this.ddhId = "";
		
		this.innguoimua = "";

		this.loaidonhang = "0";
		this.nguoimua = "";
		
		this.bvat = "0";
		this.totalCHIETKHAU = "0";
		this.thueVAT = "0";
		this.avat = "0";
		this.mavuviec="";
		this.sanpham_soluong = new Hashtable<String, String>();
		this.db = new dbutils();
	}
	
	public Hoadontaichinh(String id)
	{
		this.id = id;
		this.ngayxuatHD = getDateTime();
		this.ngayghinhanCN = getDateTime();
		this.ghichu = "";
		this.kyhieuhoadon = "";
		this.sohoadon = "";
		this.khoNhanId = "";
		this.hinhthuctt = "";
		this.nppId = "";
		this.chietkhau="";
		this.msg = "";
		this.trangthai = "0";
		this.khId ="";
		this.ddhId = "";

		this.loaidonhang = "0";
		this.nguoimua= "";
		
		this.innguoimua = "";

		this.bvat = "0";
		this.totalCHIETKHAU = "0";
		this.thueVAT = "0";
		this.avat = "0";
		this.mavuviec="";
		this.sanpham_soluong = new Hashtable<String, String>();
		this.db = new dbutils();
	}

	public String getInNguoimua() 
	{
		return this.innguoimua;
	}

	public void setInNguoimua(String innguoimua) 
	{
		this.innguoimua = innguoimua;
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

	public String getNgayxuatHD() 
	{
		return this.ngayxuatHD;
	}

	public void setNgayxuatHD(String ngayxuatHD) 
	{
		this.ngayxuatHD = ngayxuatHD;
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
	private void getNppInfo()
	{		
		//Phien ban moi
		geso.dms.distributor.util.Utility util=new geso.dms.distributor.util.Utility();
		this.nppId = util.getIdNhapp(this.userId);
	}
	
	public void createRs() 
	{
		this.getNppInfo();
		String query = "  select PK_SEQ, MAFAST + '-' + TEN as TEN" +
				       "  from KHACHHANG" +
				       "  where TRANGTHAI = '1' and NPP_FK= "+ this.nppId +" and KBH_FK=100025" +
				       "         and pk_seq in (select KHACHHANG_FK from DONHANG where trangthai in  (1,4) ) ";
		System.out.println("Khách hàng "+query);
		this.khRs = db.get(query);
		
		this.createChietkhau();
		if(this.khId.trim().length() > 0 )
		{			
			if(trangthai.equals("1") || trangthai.trim().length() <= 0 )
			{
				query = " select PK_SEQ , NgayNhap as NgayDonHang  " +
					  " from DONHANG " +
					  " where  NPP_FK= "+ this.nppId +" AND  KHACHHANG_FK = '" + this.khId + "' " +
					  "       and pk_seq in (select donhang_fk " +
					  "                      from PHIEUXUATKHO_DONHANG a inner join PHIEUXUATKHO b on a.pxk_fk= b.pk_seq " +
					  "                      where b.trangthai=1 and b.NPP_FK= "+ this.nppId +" )  " +
					  " and pk_seq not in (Select a.ddh_fk from HoaDon_ddh a inner join hoadon b on a.hoadon_fk=b.pk_seq where b.trangthai = 2 and b.loaihoadon = 0) " ;
				
				System.out.println("Lay don hang: " + query);		
				this.ddhRs = db.get(query);
			}
		}
		
		if(this.id.length() <= 0 )
		{
		  // TỰ TẠO SỐ HÓA ĐƠN CỦA USER
			
			int kbDLN =0;
			String chuoiHD= "";
			long sohoadontu= 0;
			String sohoadonden= "";
			
			try
			{
				// Kiểm tra npp nếu là Đối tác thì không dùng Số hóa đơn của hệ thống
				query = " select loaiNPP " +
						" from NHAPHANPHOI " +
						" where pk_seq = '" + nppId + "' ";	
				ResultSet rsNpp = db.get(query);
				String loainpp= "";
				if(rsNpp!= null)
				{
					while(rsNpp.next())
					{
						loainpp = rsNpp.getString("loaiNPP");
					}
					rsNpp.close();
				}
					
				if(loainpp.equals("4") || loainpp.equals("5"))	// DOI TAC VA CHI NHANH CUA DOI TAC
				{
					this.kyhieuhoadon = "NA";
					this.sohoadon = "NA";
				}
				else
				{
					if(this.nppId.equals("100002")) // CN HÀ NỘI DÙNG MẪU 2(HO)
					{

						//KIEM TRA USER ĐÃ KHAI BAO SO HOA DON TRONG DLN CHUA
						query= " select count(pk_seq) as dem" +
							   " from NHANVIEN" +
							   " where pk_seq= '"+ this.userId+"' and  sohoadontu2!= '' and sohoadonden2 != ''" +
							   "       and isnull(kyhieu2,'') != ''  ";
						ResultSet KTDLN = db.get(query);
						if(KTDLN!= null)
						{
							while(KTDLN.next())
							{
								kbDLN = KTDLN.getInt("dem");
							}
							KTDLN.close();
						}
						
						if(kbDLN <= 0 )
						{
							this.msg = "Vui lòng khai báo Số hóa đơn trong (Thiết lập dữ liệu nền > Số hóa đơn) cho user này ";
						}
						else
						{
							// LAY KY HIEU HOA DON ,SOHDTU TRONG DLN
							query= " select kyhieu2 as kyhieuhoadon, sohoadontu2 as sohoadontu, sohoadonden2 as sohoadonden," +
								   "        isnull(ngayhoadon2,'') as ngayhoadon " +
								   " from NHANVIEN " +
								   " where pk_seq= '"+ this.userId +"'";
						
							ResultSet rsLayDL =  db.get(query);
							if(rsLayDL != null)
							{
								while(rsLayDL.next())
								{
									this.kyhieuhoadon= rsLayDL.getString("kyhieuhoadon");
									sohoadontu = rsLayDL.getLong("sohoadontu");
									sohoadonden = rsLayDL.getString("sohoadonden");
									this.ngayxuatHD = rsLayDL.getString("ngayhoadon");
								}
								rsLayDL.close();
							}
				
							
							// KIEM TRA SOHOADON DA DUOC DUNG CHUA : 
							        //OTC
								query =" select count(pk_seq) as dem" +
									   " from HOADON" +
									   " where RTRIM(LTRIM(kyhieu)) = '"+ this.kyhieuhoadon +"' and cast((case when  sohoadon='NA' then sohoadon else 0 end)  as numeric(18,0)) >= "+ sohoadontu +" and cast((case when  sohoadon='NA' then sohoadon else 0 end)  as numeric(18,0) as numeric(18,0))<= " + Integer.parseInt(sohoadonden) + " " +
									   "      and trangthai != '3' and nguoisua= "+ this.userId +" and mauhoadon = 2  ";
								System.out.println("Câu kiểm tra SHD "+query);
								ResultSet KiemTra = db.get(query);
								int check = 0;
								if(KiemTra != null)
								{
									while(KiemTra.next())
									{
										check = KiemTra.getInt("dem");
									}
									KiemTra.close();
								}
								
								//ETC 
								
								query = " select count(pk_seq) as dem" +
										" from ERP_HOADONNPP" +
										" where RTRIM(LTRIM(kyhieu)) = '"+ this.kyhieuhoadon +"' and cast((case when  sohoadon='NA' then sohoadon else 0 end)  as numeric(18,0) as numeric(18,0)) >= "+ sohoadontu +" and cast((case when  sohoadon='NA' then sohoadon else 0 end)  as numeric(18,0) as numeric(18,0))<= " + Integer.parseInt(sohoadonden) + " " +
										"       and trangthai != '3' and nguoisua= "+ this.userId +" and mauhoadon = 2  ";
								
								System.out.println("Câu kiểm tra SHD "+query);
								ResultSet KiemTraETC = db.get(query);
								int check_ETC = 0;
								if(KiemTraETC != null)
								{
									while(KiemTraETC.next())
									{
										check_ETC = KiemTraETC.getInt("dem");
									}
									KiemTraETC.close();
								}
						
							// LAY SOIN MAX	
							if(check <= 0 && check_ETC <= 0)
							{
								chuoiHD = ("000000"+ sohoadontu);
								chuoiHD = chuoiHD.substring(chuoiHD.length() - 7, chuoiHD.length());
							}
							else
							{
								// LAY SOIN MAX TRONG HOADON : 
								  //OTC
								query = " select  MAX(cast(SOHOADON as numeric)) as SOIN_MAX" +
										" from HOADON" +
										" where RTRIM(LTRIM(KYHIEU)) ='"+ this.kyhieuhoadon +"' and cast((case when  sohoadon='NA' then sohoadon else 0 end)  as numeric(18,0) as numeric(18,0)) >= "+ sohoadontu +" and cast((case when  sohoadon='NA' then sohoadon else 0 end)  as numeric(18,0) as numeric(18,0))<= " + Integer.parseInt(sohoadonden) + " " +
										"       and trangthai != 3 and nguoisua= "+ this.userId +" and mauhoadon = 2 ";
								System.out.println("Câu lấy shd max "+query);
								ResultSet laySOIN = db.get(query);
							    long soinMAX_OTC= 0;
								if(laySOIN!= null)
								{
									while(laySOIN.next())
									{
										soinMAX_OTC = laySOIN.getLong("SOIN_MAX");
										
									}laySOIN.close();
								}
								
								
								 //ETC
								query = " select  MAX(cast(SOHOADON as numeric)) as SOIN_MAX" +
										" from ERP_HOADONNPP" +
										" where RTRIM(LTRIM(KYHIEU)) ='"+ this.kyhieuhoadon +"' and cast((case when  sohoadon='NA' then sohoadon else 0 end)  as numeric(18,0) as numeric(18,0)) >= "+ sohoadontu +" and cast((case when  sohoadon='NA' then sohoadon else 0 end)  as numeric(18,0) as numeric(18,0))<= " + Integer.parseInt(sohoadonden) + " " +
										"       and trangthai != '3'  and nguoisua= "+ this.userId +"  and mauhoadon = 2 ";
								System.out.println("Câu lấy shd max "+query);
								ResultSet laySOIN_ETC = db.get(query);
							    long soinMAX_ETC= 0;
								if(laySOIN_ETC!= null)
								{
									while(laySOIN_ETC.next())
									{
										soinMAX_ETC = laySOIN_ETC.getLong("SOIN_MAX");
										
									}laySOIN_ETC.close();
								}
								
								if(soinMAX_OTC > soinMAX_ETC) 
								{
									chuoiHD = ("000000"+ (soinMAX_OTC >0 ? (soinMAX_OTC +1) :"1"));
								}else
								{
									chuoiHD = ("000000"+ (soinMAX_ETC >0 ? (soinMAX_ETC +1) :"1"));
								}
								
								chuoiHD = chuoiHD.substring(chuoiHD.length() - 7, chuoiHD.length());
								
								
							}
							this.sohoadon =  chuoiHD;
							
							if(Integer.parseInt(this.sohoadon) > Integer.parseInt(sohoadonden))
							{ 
								this.msg = "Số hóa đơn đã vượt quá Số hóa đơn đến trong dữ liệu nền. Vui lòng khai báo ký hiệu hóa đơn mới ! ";
							}
							
						 }	
					   
					}
					else
					{
					//KIEM TRA USER ĐÃ KHAI BAO SO HOA DON TRONG DLN CHUA
					query= " select count(pk_seq) as dem" +
						   " from NHANVIEN" +
						   " where pk_seq= '"+ this.userId+"' and  sohoadontu!= '' and sohoadonden != ''" +
						   "       and isnull(kyhieu,'') != ''  ";
					ResultSet KTDLN = db.get(query);
					if(KTDLN!= null)
					{
						while(KTDLN.next())
						{
							kbDLN = KTDLN.getInt("dem");
						}
						KTDLN.close();
					}
					
					if(kbDLN <= 0 )
					{
						this.msg = "Vui lòng khai báo Số hóa đơn trong (Thiết lập dữ liệu nền > Số hóa đơn) cho user này ";
					}
					else
					{
						// LAY KY HIEU HOA DON ,SOHDTU TRONG DLN
						query= " select kyhieu as kyhieuhoadon, sohoadontu, sohoadonden, isnull(ngayhoadon,'') as ngayhoadon " +
							   " from NHANVIEN " +
							   " where pk_seq= '"+ this.userId +"'";
						System.out.println("Cau .... "+query);
						ResultSet rsLayDL =  db.get(query);
						if(rsLayDL != null)
						{
							while(rsLayDL.next())
							{
								this.kyhieuhoadon= rsLayDL.getString("kyhieuhoadon");
								sohoadontu = rsLayDL.getLong("sohoadontu");
								sohoadonden = rsLayDL.getString("sohoadonden");
								this.ngayxuatHD = rsLayDL.getString("ngayhoadon");
							}
							rsLayDL.close();
						}
			
						
						// KIEM TRA SOHOADON DA DUOC DUNG CHUA : 
						        //OTC
							query =" select count(pk_seq) as dem" +
								   " from HOADON" +
								   " where RTRIM(LTRIM(kyhieu)) = '"+ this.kyhieuhoadon +"' and cast((case when  sohoadon='NA' then sohoadon else 0 end) as numeric(18,0)) >= "+ sohoadontu +" and cast((case when  sohoadon='NA' then sohoadon else 0 end) as numeric(18,0))<= " + Integer.parseInt(sohoadonden) + " " +
								   "      and trangthai != '3' and nguoisua= "+ this.userId +" and mauhoadon = 1 ";
							System.out.println("Câu kiểm tra SHD "+query);
							ResultSet KiemTra = db.get(query);
							int check = 0;
							if(KiemTra != null)
							{
								while(KiemTra.next())
								{
									check = KiemTra.getInt("dem");
								}
								KiemTra.close();
							}
							
							//ETC 
							
							query = " select count(pk_seq) as dem" +
									" from ERP_HOADONNPP" +
									" where RTRIM(LTRIM(kyhieu)) = '"+ this.kyhieuhoadon +"' and cast((case when  sohoadon='NA' then sohoadon else 0 end) as numeric(18,0)) >= "+ sohoadontu +" and cast((case when  sohoadon='NA' then sohoadon else 0 end) as numeric(18,0))<= " + Integer.parseInt(sohoadonden) + " " +
									"       and trangthai != '3' and nguoisua= "+ this.userId +" and mauhoadon = 1  ";
							
							System.out.println("Câu kiểm tra SHD "+query);
							ResultSet KiemTraETC = db.get(query);
							int check_ETC = 0;
							if(KiemTraETC != null)
							{
								while(KiemTraETC.next())
								{
									check_ETC = KiemTraETC.getInt("dem");
								}
								KiemTraETC.close();
							}
					
						// LAY SOIN MAX	
						if(check <= 0 && check_ETC <= 0)
						{
							chuoiHD = ("000000"+ sohoadontu);
							chuoiHD = chuoiHD.substring(chuoiHD.length() - 7, chuoiHD.length());
						}
						else
						{
							// LAY SOIN MAX TRONG HOADON : 
							  //OTC
							query = " select  MAX(cast(SOHOADON as numeric)) as SOIN_MAX" +
									" from HOADON" +
									" where RTRIM(LTRIM(KYHIEU)) ='"+ this.kyhieuhoadon +"' and cast((case when  sohoadon='NA' then sohoadon else 0 end) as numeric(18,0)) >= "+ sohoadontu +" and cast((case when  sohoadon='NA' then sohoadon else 0 end) as numeric(18,0))<= " + Integer.parseInt(sohoadonden) + " " +
									"       and trangthai != 3 and nguoisua= "+ this.userId +" and mauhoadon = 1 ";
							System.out.println("Câu lấy shd max "+query);
							ResultSet laySOIN = db.get(query);
						    long soinMAX_OTC= 0;
							if(laySOIN!= null)
							{
								while(laySOIN.next())
								{
									soinMAX_OTC = laySOIN.getLong("SOIN_MAX");
									
								}laySOIN.close();
							}
							
							
							 //ETC
							query = " select  MAX(cast(SOHOADON as numeric)) as SOIN_MAX" +
									" from ERP_HOADONNPP" +
									" where RTRIM(LTRIM(KYHIEU)) ='"+ this.kyhieuhoadon +"' and cast((case when  sohoadon='NA' then sohoadon else 0 end) as numeric(18,0)) >= "+ sohoadontu +" and cast((case when  sohoadon='NA' then sohoadon else 0 end) as numeric(18,0))<= " + Integer.parseInt(sohoadonden) + " " +
									"       and trangthai != '3'  and nguoisua= "+ this.userId +"  and mauhoadon = 1 ";
							System.out.println("Câu lấy shd max "+query);
							ResultSet laySOIN_ETC = db.get(query);
						    long soinMAX_ETC= 0;
							if(laySOIN_ETC!= null)
							{
								while(laySOIN_ETC.next())
								{
									soinMAX_ETC = laySOIN_ETC.getLong("SOIN_MAX");
									
								}laySOIN_ETC.close();
							}
							
							if(soinMAX_OTC > soinMAX_ETC) 
							{
								chuoiHD = ("000000"+ (soinMAX_OTC >0 ? (soinMAX_OTC +1) :"1"));
							}else
							{
								chuoiHD = ("000000"+ (soinMAX_ETC >0 ? (soinMAX_ETC +1) :"1"));
							}
							
							chuoiHD = chuoiHD.substring(chuoiHD.length() - 7, chuoiHD.length());
							
							
						}
						this.sohoadon =  chuoiHD;
						
						if(Integer.parseInt(this.sohoadon) > Integer.parseInt(sohoadonden))
						{ 
							this.msg = "Số hóa đơn đã vượt quá Số hóa đơn đến trong dữ liệu nền. Vui lòng khai báo ký hiệu hóa đơn mới ! ";
						}
						
					 }
				    }
				   }
				}catch(Exception e)
				{
					this.msg = "Lỗi xảy ra trong quá trình lấy Số hóa đơn";
					e.printStackTrace();
				}
		 
			
			this.createChietkhau();
			
			if(this.khId.trim().length() > 0 )
			{			
				// LAY TEN NGUOI MUA TRONG DLN
				  query =" select isnull(chucuahieu,'') as nguoimua from KHACHHANG where pk_seq= '"+ this.khId +"' ";
					ResultSet rsLayTT = db.get(query);
					if(rsLayTT!= null)
					{
						try
						{
							while(rsLayTT.next())
							{
								this.nguoimua = rsLayTT.getString("nguoimua");
							}
							rsLayTT.close();
						}catch (Exception e) { e.printStackTrace(); }
					}	
				
				  query = 
					" select PK_SEQ , NgayNhap as NgayDonHang  " +
					" from DONHANG " +
					" where  trangthai not in (0,2) and   isnull(donhangkhac,0) = 0 and  NPP_FK= "+ this.nppId +" AND  KHACHHANG_FK = '" + this.khId + "' " +
					"         and ( import ='1' or pk_seq in (select donhang_fk " +
					"                      from PHIEUXUATKHO_DONHANG a inner join PHIEUXUATKHO b on a.pxk_fk= b.pk_seq " +
					"                      where b.trangthai=1 and b.NPP_FK= "+ this.nppId +" )  ) " +
					"         and pk_seq not in (Select a.ddh_fk from HoaDon_ddh a inner join hoadon b on a.hoadon_fk=b.pk_seq where b.trangthai in (1,2,4) and b.loaihoadon = 0 ) " ;
					System.out.println("Câu query "+query);		
					this.ddhRs = db.get(query);
			}
			
			String chuoi = this.ddhId;
			if(chuoi.length() > 0)
			{			
				// INIT SP	
			    query = " select b.PK_SEQ as SPID, b.MA, b.TEN, DV.donvi, a.giamua as dongia ,' ' as scheme ,a.thuevat," +
			    		"        sum( a.soluong) as soluong, '0'  as chietkhau " +
						" from Donhang_SANPHAM a inner Join SanPham b on a.SANPHAM_FK = b.PK_SEQ    " +
						" 	   INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ = b.DVDL_FK       " +
						" where a.Donhang_FK in ( " + chuoi + " ) " +
						" group by  b.PK_SEQ , b.MA, b.TEN, DV.donvi, a.giamua  ,a.thuevat ";
		
				    
			    System.out.println("Lấy sản phẩm: "+query);
			    ResultSet rsLaySP = db.get(query);
				NumberFormat formater = new DecimalFormat("##,###,###");
			    try 
			    {
			    	String spID = "";
					String spMA = "";
					String spTEN = "";
					String spDONVI = "";
					String spSOLUONG = "";
					String spGIANHAP = "";
					String spCHIETKHAU = "";
					String spSCHEME = "";
					String spVAT = "";
					String spTienThue = "";
					double tienthue= 0;
			    	
				    if(rsLaySP!= null)
				    {				    	
						while(rsLaySP.next())
						{
							spID += rsLaySP.getString("SPID") + "__";
							spMA += rsLaySP.getString("MA") + "__";
							spTEN += rsLaySP.getString("TEN") + "__";
							spDONVI += rsLaySP.getString("DONVI") + "__";
							spSOLUONG += (rsLaySP.getDouble("SOLUONG")) + "__";
							spGIANHAP += (rsLaySP.getDouble("DONGIA")) + "__";
							spCHIETKHAU += (rsLaySP.getDouble("chietkhau")) + "__";
							spSCHEME += rsLaySP.getString("scheme") + "__";
							spVAT += (rsLaySP.getDouble("thuevat")) + "__";
							tienthue = (Math.round(rsLaySP.getDouble("SOLUONG") * rsLaySP.getDouble("DONGIA")) - rsLaySP.getDouble("chietkhau"))*rsLaySP.getDouble("thuevat")/100;
							spTienThue += tienthue + "__";
						}
						rsLaySP.close();
						
						if(spMA.trim().length() > 0)
						{
							spID = spID.substring(0, spID.length() - 2);
							this.spId = spID.split("__");
							
							spMA = spMA.substring(0, spMA.length() - 2);
							this.spMa = spMA.split("__");
							
							spTEN = spTEN.substring(0, spTEN.length() - 2);
							this.spTen = spTEN.split("__");
							
							spDONVI = spDONVI.substring(0, spDONVI.length() - 2);
							this.spDonvi = spDONVI.split("__");
							
							spSOLUONG = spSOLUONG.substring(0, spSOLUONG.length() - 2);
							this.spSoluong = spSOLUONG.split("__");
							
							spGIANHAP = spGIANHAP.substring(0, spGIANHAP.length() - 2);
							this.spDongia = spGIANHAP.split("__");
							
							spCHIETKHAU = spCHIETKHAU.substring(0, spCHIETKHAU.length() - 2);
							this.spChietkhau = spCHIETKHAU.split("__");
							
							spSCHEME = spSCHEME.substring(0, spSCHEME.length() - 2);
							this.spSCheme = spSCHEME.split("__");
							
							spVAT = spVAT.substring(0, spVAT.length() - 2);
							this.spVat = spVAT.split("__");
							
							spTienThue = spTienThue.substring(0, spTienThue.length() - 2);
							this.spThue = spTienThue.split("__");


							System.out.println("Da vao day");
						}
				    }
				} 
			    catch (Exception e) 
			    {
					e.printStackTrace();
				}
				
				 this.initTichLuy(chuoi);
				 this.getTongTien(chuoi);

			 }
		  }
		  else // ID > 0
		  {
			  String chuoi = this.ddhId;

			  this.initTichLuy(chuoi);
			  this.getTongTien(chuoi);
					
			  // LAY SP TRONG HOADON
			  query = " select b.PK_SEQ as SPID, b.MA, b.TEN, a.donvitinh, a.soluong, a.dongia, '0' as chietkhau, ' ' as scheme, a.vat  " +
					" from HOADON_SP a inner Join SanPham b on a.SANPHAM_FK = b.PK_SEQ    " +
					" where a.hoadon_fk = "+ this.id +"  " ;		
				    
			  System.out.println("Lấy sản phẩm: "+query);
			  ResultSet rsLaySP = db.get(query);
			  try 
			  {
				  String spID = "";
				  String spMA = "";
				  String spTEN = "";
				  String spDONVI = "";
				  String spSOLUONG = "";
				  String spGIANHAP = "";
				  String spCHIETKHAU = "";
				  String spSCHEME = "";
				  String spVAT = "";
				  String spTienThue = "";
				  double tienthue= 0;
		    	
				  if(rsLaySP!= null)
				  {				    	
					  while(rsLaySP.next())
					  {
							spID += rsLaySP.getString("SPID") + "__";
							spMA += rsLaySP.getString("MA") + "__";
							spTEN += rsLaySP.getString("TEN") + "__";
							spDONVI += rsLaySP.getString("donvitinh") + "__";
							spSOLUONG += (rsLaySP.getDouble("SOLUONG")) + "__";
							spGIANHAP += (rsLaySP.getDouble("DONGIA")) + "__";
							spCHIETKHAU += (rsLaySP.getDouble("chietkhau")) + "__";
							spSCHEME += rsLaySP.getString("scheme") + "__";
							spVAT += rsLaySP.getString("vat") + "__";
							tienthue = Math.round(( Math.round(rsLaySP.getDouble("SOLUONG") * rsLaySP.getDouble("DONGIA")) - rsLaySP.getDouble("chietkhau"))*rsLaySP.getDouble("vat")/100);
							spTienThue += tienthue + "__";
					  }
					  rsLaySP.close();
					
					  if(spMA.trim().length() > 0)
					  {
							spID = spID.substring(0, spID.length() - 2);
							this.spId = spID.split("__");
							
							spMA = spMA.substring(0, spMA.length() - 2);
							this.spMa = spMA.split("__");
							
							spTEN = spTEN.substring(0, spTEN.length() - 2);
							this.spTen = spTEN.split("__");
							
							spDONVI = spDONVI.substring(0, spDONVI.length() - 2);
							this.spDonvi = spDONVI.split("__");
							
							spSOLUONG = spSOLUONG.substring(0, spSOLUONG.length() - 2);
							this.spSoluong = spSOLUONG.split("__");
							
							spGIANHAP = spGIANHAP.substring(0, spGIANHAP.length() - 2);
							this.spDongia = spGIANHAP.split("__");
							
							spCHIETKHAU = spCHIETKHAU.substring(0, spCHIETKHAU.length() - 2);
							this.spChietkhau = spCHIETKHAU.split("__");
							
							spSCHEME = spSCHEME.substring(0, spSCHEME.length() - 2);
							this.spSCheme = spSCHEME.split("__");
							
							spVAT = spVAT.substring(0, spVAT.length() - 2);
							this.spVat = spVAT.split("__");
							
							spTienThue = spTienThue.substring(0, spTienThue.length() - 2);
							this.spThue = spTienThue.split("__");
					  }
				  }
			  } 
			  catch (Exception e) 
			  {
				  e.printStackTrace();
			  }	
		}
		
		try
		{
			//INIT SOLO
			query = "select sanpham_fk, solo, ngayhethan, sum(soluong) as soluong  " +
					"from PHIEUXUATKHO_SANPHAM_CHITIET where PXK_FK in ( select PXK_FK from PHIEUXUATKHO_DONHANG a inner join PHIEUXUATKHO b on a.pxk_fk = b.pk_seq where b.trangthai = '1' and donhang_fk in ( " + this.ddhId + " ) ) " +
					"group by sanpham_fk, solo, ngayhethan ";
			System.out.println("---LO DA XUAT: " + query);
			ResultSet rsSOLO = db.get(query);
			this.sanpham_soluong = new Hashtable<String, String>();
			if(rsSOLO != null)
			{
				while(rsSOLO.next())
				{
					String key = rsSOLO.getString("sanpham_fk") + "__" + rsSOLO.getString("solo") + "__" + rsSOLO.getString("ngayhethan");
					this.sanpham_soluong.put(key, rsSOLO.getString("soluong"));
				}
				rsSOLO.close();
			}
		}
		catch (Exception e) 
		{	
			e.printStackTrace();
		}
		
	}

	public void init() 
	{
		this.getNppInfo();
		String query =  " select dondathang_fk, ngayxuatHD, ISNULL(ghichu, '') as ghichu, kyhieu, sohoadon, hinhthuctt ,khachhang_fk, npp_fk, trangthai, isnull(nguoimua,'') as nguoimua, isnull(innguoimua,1) as innguoimua,  " +
				        "        isnull(chietkhau,0) as chietkhau, isnull(tongtienbvat,0) as tongtienbvat, isnull(ngayghinhanCN, '"+ getDateTime() +"') as ngayghinhanCN , " +
				        "        isnull(tongtienavat, 0) as tongtienavat, isnull(vat, 0) as vat, isnull(tongtienkm, 0) as tongtienkm,isnull(mavv,'') as mavv  " +
						" from HOADON a " +
						" where pk_seq = '" + this.id + "'";
		System.out.println("____INIT HOADON: " + query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				NumberFormat formatter = new DecimalFormat("#,###,###");
				if(rs.next())
				{
					this.ngayxuatHD = rs.getString("ngayxuatHD");
					this.ghichu = rs.getString("ghichu");
					this.hinhthuctt = rs.getString("hinhthuctt");
					this.kyhieuhoadon = rs.getString("kyhieu");
					this.ngayghinhanCN = rs.getString("ngayghinhanCN");
					this.sohoadon = rs.getString("sohoadon");
					this.khId = rs.getString("khachhang_fk");
					this.nppId = rs.getString("npp_fk");
					this.trangthai = rs.getString("trangthai");
					this.innguoimua = rs.getString("innguoimua");
					this.nguoimua = rs.getString("nguoimua");
					this.mavuviec=rs.getString("mavv");
					
					this.bvat = formatter.format(rs.getDouble("tongtienbvat"));
					this.totalCHIETKHAU = formatter.format(rs.getDouble("chietkhau"));
					this.thueVAT = formatter.format(rs.getDouble("vat"));
					this.avat = formatter.format(rs.getDouble("tongtienavat"));
					//this.mavuviec=rs.getString("mavv");
						
				}
				rs.close();
				
				//INIT DDH
				query = "SELECT DDH_FK FROM HOADON_DDH WHERE HOADON_FK = " + this.id;
				rs = this.db.get(query);
				
				String ddh = "";
				if(rs!=null)
				{
					while(rs.next())
					{
						ddh += rs.getString("DDH_FK") + ",";
					}
					rs.close();
					
					if(ddh.trim().length() > 0)
						this.ddhId = ddh.substring(0, ddh.length() - 1);
				}
				
				if(this.trangthai.equals("3") || this.trangthai.equals("5") )
				{
					query = " select B.PK_SEQ, B.NGAYNHAP AS NgayDonHang  " +
							" from HOADON_DDH A INNER JOIN DONHANG B ON A.DDH_FK = B.PK_SEQ " +
							" where A.HOADON_FK = '"+ this.id +"' AND A.HOADON_FK IN (SELECT PK_SEQ FROM HOADON WHERE LOAIHOADON= 0) ";
				
				}
				else
				{				
					query = "select PK_SEQ, NGAYNHAP AS NgayDonHang  " +
							"from DONHANG " +
							"where  trangthai not in (0,2 ) and   isnull(donhangkhac,0) = 0 and NPP_FK = " + this.nppId + " and  KHACHHANG_FK = '" + this.khId + "' " +
							"    and ( import= '1' or pk_seq in (select donhang_fk " +
							"                      from PHIEUXUATKHO_DONHANG a inner join PHIEUXUATKHO b on a.pxk_fk= b.pk_seq " +
							"					   where b.trangthai=1 and b.NPP_FK= "+ this.nppId +"))  " +
							" and pk_seq not in ( Select a.ddh_fk from HoaDon_ddh a inner join hoadon b on a.hoadon_fk = b.pk_seq where b.trangthai in ( 1, 2, 4 ) and b.loaihoadon = 0 and b.pk_seq != '" + this.id + "')  " ;
				}
				
				System.out.println("Lấy đơn hàng " + query);		
				this.ddhRs = db.get(query);	
			} 
			catch (Exception e) 
			{
				System.out.println("---LOI INIT: " + e.getMessage());
			}
		}

		this.createRs();

	}
	
	private void createChietkhau()
	{
		if( ( this.chietkhau.equals("0") || this.chietkhau == "" ) && this.ddhId.trim().length() > 0 )
		{
			//String sql ="select ISNULL(b.chietkhau,0 ) as chietkhau from KHACHHANG a left join MUCCHIETKHAU b on a.CHIETKHAU_FK = b.pk_seq where a.PK_SEQ = '" + khId + "'";
			
			/*String sql = "select a.xuatkhau, ( select loaiNPP from NHAPHANPHOI where pk_seq = a.npp_fk ) as loaiNPP " +
						 "from khachhang a " +
						 "where a.trangthai = '1' and a.npp_fk = '" + nppId + "' and a.pk_seq = '" + this.khId + "' "; */
			
			String sql = "select isnull(chietkhau, 0) as chietkhau " +
						 "from DONHANG a " +
						 "where a.pk_seq in (  " + this.ddhId +" ) "; 

			ResultSet rs = db.get(sql);
			if(rs != null)
			{
				try 
				{
					if(rs.next())
					{
						/*String banbuon = rs.getString("xuatkhau");
				    	String loaiNPP = rs.getString("loaiNPP");
				    	
				    	if( loaiNPP.equals("4") || loaiNPP.equals("5") ) //Đối tác thì bán buôn, bán lẻ đều chiết khấu 5%
				    		this.chietkhau = "5";
				    	else
				    	{
				    		if( banbuon.equals("1") || banbuon.equals("2") )
				    			this.chietkhau = "5";
				    		else
				    			this.chietkhau = "3";
				    	}*/
						
						this.chietkhau = rs.getString("chietkhau");
					}
					rs.close();
				} 
				catch(Exception e) {}			
			}
		}
		
	}
	public void DBclose() {
		
		try{
			
			if(khoNhanRs!=null){
				khoNhanRs.close();
			}
			
			this.db.shutDown();
			
		}catch(Exception er){
			
		}
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

	public String getGhichu() {
		
		return this.ghichu;
	}

	public void setGhichu(String ghichu) {
		
		this.ghichu = ghichu;
	}

	public String getHinhthucTT() {
		
		return this.hinhthuctt;
	}

	public void setHinhthucTT(String hinhthucTT) {
		
		this.hinhthuctt = hinhthucTT;
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


	public String getNppId() {
		
		return this.nppId;
	}

	
	public void setNppId(String nppId) {
		
		this.nppId = nppId;
	}

	
	public ResultSet getNppRs() {
		
		return this.nppRs;
	}

	
	public void setNppRs(ResultSet nppRs) {
		
		this.nppRs = nppRs;
	}

	
	public String getDondathangId() {
		
		return this.ddhId;
	}

	
	public void setDondathangId(String kbhId) {
		
		this.ddhId = kbhId;
	}

	
	public ResultSet getDondathangRs() {
		
		return this.ddhRs;
	}

	
	public void setDondathangRs(ResultSet ddhRs) {
		
		this.ddhRs = ddhRs;
	}

	public boolean create() 
	{
		this.getNppInfo();
		if(this.ngayxuatHD.trim().length() < 10)
		{
			this.msg = "Vui lòng nhập ngày xuất hóa đơn";
			return false;
		}

		if(this.ngayghinhanCN.trim().length() < 10)
		{
			this.msg = "Vui lòng nhập ngày ghi nợ";
			return false;
		}
		
		if( this.ddhId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn đơn đặt hàng";
			return false;
		}
				
		if(spMa == null)
		{
			this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm  xuất hoá đơn";
			return false;
		}
		else
		{
			boolean coSP = false;
			for(int i = 0; i < spId.length; i++)
			{
				if( spSoluong[i].trim().length() > 0 )
				{
					if(Double.parseDouble(spSoluong[i].trim().replaceAll(",", "")) > 0)
						coSP = true;
				}
			}
			
			if(!coSP)
			{
				this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm  xuất hoá đơn";
				return false;
			}	
		}
		
		try
		{
			db.update("SET TRANSACTION ISOLATION LEVEL SNAPSHOT;");
			db.update("SET LOCK_TIMEOUT 60000;");
			db.getConnection().setAutoCommit(false);
			
			String query = "";
			String chuoi="";
			long sohoadontu=0;
			String sohoadonden="";
			int kbDLN=0;
			
			// Kiểm tra npp nếu là Đối tác thì không dùng Số hóa đơn của hệ thống
			query = " select loaiNPP, (  select PXK_FK from PHIEUXUATKHO_DONHANG a inner join PHIEUXUATKHO b on a.pxk_fk = b.pk_seq where b.trangthai = '1' and donhang_fk in ( " + this.ddhId + " ) ) as pxkID " +
					" from NHAPHANPHOI " +
					" where pk_seq = '" + nppId + "' ";	
			ResultSet rsNpp = db.get(query);
			String loainpp= "";
			String pxkID = "";
			if(rsNpp!= null)
			{
				while(rsNpp.next())
				{
					loainpp = rsNpp.getString("loaiNPP");
					pxkID = rsNpp.getString("pxkID");
				}
				rsNpp.close();
			}
				
			if(!loainpp.equals("4") && !loainpp.equals("5"))	// KHONG PHAI LA DOI TAC VA CHI NHANH CUA DOI TAC
			{
				if(this.nppId.equals("100002")) // CN HÀ NỘI DÙNG MẪU 2
				{

					// LAY KY HIEU HOA DON ,SOHDTU TRONG DLN
					query= " select  sohoadontu2, sohoadonden2 " +
						   " from NHANVIEN" +
						   " where pk_seq= '"+ this.userId +"' and isnull(kyhieu2, '')= '"+ this.kyhieuhoadon +"' ";
					ResultSet rsLayDL =  db.get(query);
					if(rsLayDL != null)
					{
						while(rsLayDL.next())
						{
							sohoadontu = rsLayDL.getLong("sohoadontu2");
							sohoadonden = rsLayDL.getString("sohoadonden2");
						}
						rsLayDL.close();
					}
					if(sohoadontu == 0 || sohoadonden.trim().length() <= 0)
					{
						this.msg = " Ký hiệu "+ this.kyhieuhoadon +" không giống với ký hiệu khai báo trong dữ liệu nền/ Chưa khai báo số hóa đơn trong dữ liệu nền (Số hóa đơn) ";
						db.getConnection().rollback();
						return false;
					}
					
					// Check So hoa don sua da co dung chua
					   // OTC
					query= " select  count(pk_seq) as kiemtra " +
					       " from HOADON " +
					       " where nguoisua= '"+ this.userId +"' and RTRIM(LTRIM(sohoadon)) = '"+ this.sohoadon.trim() +"' and pk_seq != "+ this.id +" " +
					       "       and trangthai != 3  and mauhoadon = 2 and RTRIM(LTRIM(isnull(kyhieu, ''))) = RTRIM(LTRIM('"+ this.kyhieuhoadon +"')) ";
					System.out.println("Cau kiem tra so hoa don "+query);
					ResultSet KtraSHD =  db.get(query);
					int ktra= 0;
					
					if(KtraSHD != null)
					{
						while(KtraSHD.next())
						{
							ktra = KtraSHD.getInt("kiemtra");
						}
						KtraSHD.close();
					}
					
					  // ETC
					query= " select  count(pk_seq) as kiemtra " +
					       " from ERP_HOADONNPP " +
					       " where nguoisua= '"+ this.userId +"' and RTRIM(LTRIM(sohoadon)) = '"+ this.sohoadon.trim() +"' and pk_seq != "+ this.id +" " +
					       "        and trangthai != 3 and mauhoadon = 2 and RTRIM(LTRIM(isnull(kyhieu, ''))) = RTRIM(LTRIM('"+ this.kyhieuhoadon +"')) ";
					System.out.println("Cau kiem tra so hoa don "+query);
					ResultSet KtraSHD_ETC =  db.get(query);
					int ktra_ETC= 0;
					
					if(KtraSHD_ETC != null)
					{
						while(KtraSHD_ETC.next())
						{
							ktra_ETC = KtraSHD_ETC.getInt("kiemtra");
						}
						KtraSHD_ETC.close();
					}
					
					
					if(Integer.parseInt(sohoadonden.trim()) <  Integer.parseInt(this.sohoadon.trim()))
					{
						this.msg = "Số hóa đơn này đã vượt quá số hóa đơn đến trong dữ liệu nền. Vui lòng thiết lập lại số hóa đơn ";
						db.getConnection().rollback();
						return false;
					}
					else if (ktra > 0 || ktra_ETC > 0 )
					{
						this.msg = "Số hóa đơn này đã được sử dụng. Vui lòng đánh số hóa đơn khác. ";
						db.getConnection().rollback();
						return false;
					}
					else if (this.sohoadon.trim().length() != 7 )
					{
						this.msg = "Số hóa đơn phải đủ 7 ký tự. Vui lòng kiểm tra lại.";
						db.getConnection().rollback();
						return false;
					}
				  
				}
				else
				{
				// LAY KY HIEU HOA DON ,SOHDTU TRONG DLN
				query= " select  sohoadontu, sohoadonden " +
					   " from NHANVIEN where pk_seq= '"+ this.userId +"' and isnull(kyhieu, '')= '"+ this.kyhieuhoadon +"' ";
				ResultSet rsLayDL =  db.get(query);
				if(rsLayDL != null)
				{
					while(rsLayDL.next())
					{
						sohoadontu = rsLayDL.getLong("sohoadontu");
						sohoadonden = rsLayDL.getString("sohoadonden");
					}
					rsLayDL.close();
				}
				if(sohoadontu == 0 || sohoadonden.trim().length() <= 0)
				{
					this.msg = " Ký hiệu "+ this.kyhieuhoadon +" không giống với ký hiệu khai báo trong dữ liệu nền/ Chưa khai báo số hóa đơn trong dữ liệu nền (Số hóa đơn) ";
					db.getConnection().rollback();
					return false;
				}
				
				// Check So hoa don sua da co dung chua
				   // OTC
				query= " select  count(pk_seq) as kiemtra " +
				       " from HOADON " +
				       " where nguoisua= '"+ this.userId +"' and RTRIM(LTRIM(sohoadon)) = '"+ this.sohoadon.trim() +"' and pk_seq != "+ this.id +" " +
				       "       and trangthai != 3 and mauhoadon = 1 and RTRIM(LTRIM(isnull(kyhieu, ''))) = RTRIM(LTRIM('"+ this.kyhieuhoadon +"')) ";
				System.out.println("Cau kiem tra so hoa don "+query);
				ResultSet KtraSHD =  db.get(query);
				int ktra= 0;
				
				if(KtraSHD != null)
				{
					while(KtraSHD.next())
					{
						ktra = KtraSHD.getInt("kiemtra");
					}
					KtraSHD.close();
				}
				
				  // ETC
				query= " select  count(pk_seq) as kiemtra " +
				       " from ERP_HOADONNPP " +
				       " where nguoisua= '"+ this.userId +"' and RTRIM(LTRIM(sohoadon)) = '"+ this.sohoadon.trim() +"' and pk_seq != "+ this.id +" " +
				       "        and trangthai != 3 and mauhoadon = 1 and RTRIM(LTRIM(isnull(kyhieu, ''))) = RTRIM(LTRIM('"+ this.kyhieuhoadon +"')) ";
				System.out.println("Cau kiem tra so hoa don "+query);
				ResultSet KtraSHD_ETC =  db.get(query);
				int ktra_ETC= 0;
				
				if(KtraSHD_ETC != null)
				{
					while(KtraSHD_ETC.next())
					{
						ktra_ETC = KtraSHD_ETC.getInt("kiemtra");
					}
					KtraSHD_ETC.close();
				}
				
				
				if(Integer.parseInt(sohoadonden.trim()) <  Integer.parseInt(this.sohoadon.trim()))
				{
					this.msg = "Số hóa đơn này đã vượt quá số hóa đơn đến trong dữ liệu nền. Vui lòng thiết lập lại số hóa đơn ";
					db.getConnection().rollback();
					return false;
				}
				else if (ktra > 0 || ktra_ETC > 0 )
				{
					this.msg = "Số hóa đơn này đã được sử dụng. Vui lòng đánh số hóa đơn khác. ";
					db.getConnection().rollback();
					return false;
				}
				else if (this.sohoadon.trim().length() != 7 )
				{
					this.msg = "Số hóa đơn phải đủ 7 ký tự. Vui lòng kiểm tra lại.";
					db.getConnection().rollback();
					return false;
				}
			  }
			}
			
			String mau = "1";
			if(this.nppId.equals("100002"))	mau = "2";
	
			query=
			 " insert HOADON(KBH_FK, DDKD_FK, GSBH_fK, KHO_FK, khachhang_fk, ngayxuatHD, trangthai, ngaytao, nguoitao, ngaysua, nguoisua, "+
				"               kyhieu, sohoadon, hinhthuctt , chietkhau, tongtienbvat, tongtienavat, vat, tongtienkm, ghichu, npp_fk, " +
	      " 		        loaihoadon, nguoimua, innguoimua, mauhoadon,TENKHACHHANG,DIACHI,MASOTHUE,mavv) " +
		    " SELECT DH.KBH_FK, DH.DDKD_FK, DH.GSBH_fK, DH.KHO_FK, DH.KHACHHANG_FK , '" + ngayxuatHD + "', '1','" + getDateTime() + "', '" + userId + "', '" + getDateTime() + "', '" + userId + "', " +
		    "        '" + kyhieuhoadon + "', '" + sohoadon + "', N'"+this.hinhthuctt+"' , '0', '0', '0', '0', '0', N'"+this.ghichu+"', " + nppId + " , " +
		    "         '0', ISNULL(KH.CHUCUAHIEU,'')  ,( CASE WHEN DH.NPP_FK = 106210  THEN 0 ELSE 1 END) AS INNGUOIMUA , ( CASE WHEN DH.NPP_FK = 100002  THEN 2 ELSE 1 END) AS MAUHD " +
		    "  ,KH.TEN as tenkh , ISNULL(KH.DIACHI,'') as diachikh ,ISNULL(KH.MASOTHUE,'') as mst,'"+this.mavuviec+"'  "+
		    " FROM  DONHANG DH  INNER JOIN  KHACHHANG KH ON DH.KHACHHANG_FK = KH.PK_SEQ     \n"+
		    " WHERE DH.PK_SEQ ="+this.ddhId+"  \n";
			 
			 
			System.out.println("1.Insert HOADON: " + query);
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới HOADON " + query;
				db.getConnection().rollback();
				return false;
			}
			
			String hdId = "";
			query = "select SCOPE_IDENTITY() as hdId";
			ResultSet rs1 = db.get(query);
			rs1.next();
			hdId = rs1.getString("hdId");
			this.id=hdId;
			rs1.close();
			
			query = "Update PHIEUXUATKHO set NGAYLAPPHIEU = '" + this.ngayxuatHD + "' " +
					"where PK_SEQ in ( select pxk_fk from PHIEUXUATKHO_DONHANG where donhang_fk in ( "+this.ddhId+" ) )  ";
			System.out.println("1372---3.HUY HOA DON: " + query );
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới HOADON " + query;
				db.getConnection().rollback();
				return false;
			}
					
			query = "Insert HOADON_DDH(hoadon_fk, ddh_fk) select " + hdId + " , pk_seq from DONHANG where pk_seq in ( " + this.ddhId + " ) ";
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới HOADON_DDH " + query;
				db.getConnection().rollback();
				return false;
			}
					
			for(int i = 0; i < spId.length; i++)
			{
				if(spId[i].trim().length() > 0 && Double.parseDouble(spSoluong[i].trim().replaceAll(",", "")) > 0 )
				{
					query = "insert HOADON_SP( hoadon_fk, sanpham_fk, donvitinh, soluong, dongia, thanhtien, chietkhau, scheme, vat, dongiaGOC ) " +
							" select "+ hdId +" , '" + spId[i] + "', N'"+ spDonvi[i] +"', '" + spSoluong[i].replaceAll(",", "") + "', '" + spDongia[i].replaceAll(",", "") + "'," +
									" (" + spSoluong[i].replaceAll(",", "")+ " * "+ spDongia[i].replaceAll(",", "") +"), '"+ spChietkhau[i].replaceAll(",", "") +"', N'"+ spSCheme[i].replaceAll(",", "") +"', '" + spVat[i].replaceAll(",", "") + "', " +
									"		ISNULL ( ( select giabanleNPP from BGBANLENPP_SANPHAM where sanpham_fk = '" + spId[i] + "' and BGBANLENPP_FK in ( select pk_seq from BANGGIABANLENPP where npp_fk = '" + this.nppId + "'  ) ), 0) as dongiaGOC ";
					
					System.out.println("1.1.Insert ERP_HOADON_SP: " + query);
					if(!db.update(query))
					{
						this.msg = "Khong the tao moi ERP_HOADON_SP: " + query;
						db.getConnection().rollback();
						return false;
					}
				}
			}			
			//XÓA PXK CŨ
			query =
			"update kho set kho.SOLUONG = kho.SOLUONG + xuat.soluong, " +
			"			   kho.AVAILABLE = kho.AVAILABLE + xuat.soluong	 " +
			"from NHAPP_KHO_CHITIET kho inner join " +
			"( " +
			"	select kho_fk, kbh_fk, NPP_FK, SANPHAM_FK, SOLO, SUM(soluong) as soluong ,NgayHetHan" +
			"   from (	" +
			"		select b.kho_fk, b.kbh_fk, a.NPP_FK, b.SANPHAM_FK, b.SOLO, SUM(b.soluong) as soluong,b.NgayHetHan " +
			"		from PHIEUXUATKHO a inner join PHIEUXUATKHO_SANPHAM_CHITIET b on a.PK_SEQ = b.PXK_FK " +
			"		where a.PK_SEQ = '" + pxkID + "' " +
			"		group by b.kho_fk, b.kbh_fk, a.NPP_FK, b.SANPHAM_FK, b.SOLO,b.NgayHetHan " +
			"	)" +
			"	TX group by kho_fk, kbh_fk, NPP_FK, SANPHAM_FK, SOLO,NgayHetHan " +
			") " +
			"xuat on kho.SANPHAM_FK = xuat.SANPHAM_FK and kho.NPP_FK = xuat.NPP_FK and kho.KBH_FK = xuat.kbh_fk and kho.KHO_FK = xuat.kho_fk and kho.SOLO = xuat.SOLO and kho.NgayHetHan=xuat.NgayHetHan";
			System.out.println("2.TANG KHO: " + query);
			if(!db.update(query))
			{
				this.msg = "Không thể hủy " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete PHIEUXUATKHO_SANPHAM_CHITIET where PXK_FK = '" + pxkID + "'  ";
			if(!db.update(query))
			{
				this.msg = "Không thể hủy " + query;
				db.getConnection().rollback();
				return false;
			}
			
			//CHEN LAI PXK TRONG TRUONG HOP DOI SOLO
			for(int i = 0; i < spId.length; i++)
			{
				if(spId[i].trim().length() > 0 && Double.parseDouble(spSoluong[i].trim().replaceAll(",", "")) > 0 )
				{
					if(this.sanpham_soluong.size() > 0)
					{
						Enumeration<String> keys = this.sanpham_soluong.keys();
						double totalCT = 0;
						
						while(keys.hasMoreElements())
						{
							String key = keys.nextElement();
							System.out.println("----KEY: " + key + "  -- ID: " + spId[i]);
							
							if(key.startsWith( spId[i]+"__") )
							{
								String[] _sp = key.split("__");
								
								String _soluongCT = "0"; 
								if(this.sanpham_soluong.get(key) != null)
								{
									_soluongCT = this.sanpham_soluong.get(key).replaceAll(",", "");
								}
								
								totalCT += Double.parseDouble(_soluongCT);
								
								//CHECK TỒN KHO CỦA LÔ MUỐN ĐỔI CÓ ĐỦ KHÔNG
								query = "select AVAILABLE from NHAPP_KHO_CHITIET " +
										"where NPP_FK = '" + this.nppId + "' and kho_fk = ( select kho_fk from DONHANG where pk_seq in (" + this.ddhId + ") ) and kbh_fk = '100025' and sanpham_fk = '" + _sp[0] + "' and SOLO = '" + _sp[1] + "' and ngayhethan = '" + _sp[2] + "' ";
								System.out.println("1.1.Check TONKHO: " + query);
								ResultSet rsCHECK_TK = db.get(query);
								double avai = 0;
								if(rsCHECK_TK.next())
								{
									avai = rsCHECK_TK.getDouble("AVAILABLE");
								}
								rsCHECK_TK.close();
								
								if(avai < Double.parseDouble(_soluongCT) )
								{
									db.getConnection().rollback();
									this.msg = "Sản phẩm ( " + spTen[i] + " ) với số lô ( " + _sp[1] + " ), ngày hết hạn ( " + _sp[2] + " ) còn tối đa ( " + avai + " ) ";
									return false;
								}
								
								
								query = "insert PHIEUXUATKHO_SANPHAM_CHITIET(PXK_FK, SANPHAM_FK, SOLUONG, SOLO, NGAYHETHAN, KBH_FK, KHO_FK)" +
										"select '" + pxkID + "', '" + _sp[0] + "', '" + _soluongCT + "', '" + _sp[1] + "', '" + _sp[2] + "', '100025', ( select kho_fk from DONHANG where pk_seq in (" + this.ddhId + ") ) as kho_fk  ";
								
								System.out.println("1.2.Insert PHIEUXUATKHO_SANPHAM_CHITIET: " + query);
								if(db.updateReturnInt(query)!=1)
								{
									this.msg = "Khong the tao moi PHIEUXUATKHO_SANPHAM_CHITIET: " + query;
									db.getConnection().rollback();
									return false;
								}
								
								query = "Update NHAPP_KHO_CHITIET set soluong = soluong - '" + _soluongCT + "', AVAILABLE = AVAILABLE - '" + _soluongCT + "' " +
										"where NPP_FK = '" + this.nppId + "' and kho_fk = ( select kho_fk from DONHANG where pk_seq in (" + this.ddhId + ") ) and kbh_fk = '100025' and sanpham_fk = '" + _sp[0] + "' and SOLO = '" + _sp[1] + "' and ngayhethan = '" + _sp[2] + "' ";
								System.out.println("1.2.Insert NHAPP_KHO_CHITIET: " + query);
								if(db.updateReturnInt(query)!=1)
								{
									this.msg = "Khong the tao moi NHAPP_KHO_CHITIET: " + query;
									db.getConnection().rollback();
									return false;
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
			
			query = "insert HOADON_SP_CHITIET( HOADON_FK, MA, TEN, DONVI, DONGIA, DONGIAGOC, SOLO, NGAYHETHAN, THUEVAT, SOLUONG, CHIETKHAU,KHO_FK )  " +
					" SELECT '" + hdId + "' as HOADON_FK, c.MA, c.TEN, d.DONVI, dhsp.GIAMUA AS DONGIA, dhsp.DONGIAGOC,   " +
					"		case f.SOLO when 'NA' then ' ' else f.SOLO end as SOLO,   " +
					"		case f.SOLO when 'NA' then ' ' else isnull(f.NGAYHETHAN,'') end as NGAYHETHAN, dhsp.thuevat,     " +
					"	    SUM( f.soluong) as soluong, '0' as chietkhau,dhsp.Kho_Fk   " +
					" FROM  DONHANG dh INNER JOIN DONHANG_SANPHAM dhsp on dhsp.DONHANG_FK = dh.PK_SEQ    " +
					"				   INNER JOIN SANPHAM c on dhsp.SANPHAM_FK = c.PK_SEQ                 " +
					"				   LEFT JOIN DONVIDOLUONG d on d.PK_SEQ = c.DVDL_FK   " +
					"				   LEFT JOIN PHIEUXUATKHO_DONHANG e on dh.PK_SEQ = e.DONHANG_FK  " +
					"				   LEFT JOIN PHIEUXUATKHO_SANPHAM_CHITIET f on e.PXK_FK = f.PXK_FK and c.PK_SEQ = f.SANPHAM_FK  " +
					" WHERE dh.PK_SEQ in (select ddh_fk from HOADON_DDH where hoadon_fk =  '" + hdId + "' )  and  dhsp.SOLUONG > 0 " +
							"  and e.PXK_FK in ( select pk_seq from PHIEUXUATKHO where NPP_FK = '" + nppId + "' and trangthai != '2' )	  " +
					" GROUP BY  c.MA, c.TEN, d.DONVI, dhsp.GIAMUA, dhsp.DONGIAGOC, dhsp.THUEVAT, f.SOLO, f.NGAYHETHAN, dhsp.KHO_FK  ";
			
			System.out.println("---CHAY HOA DON CHI TIET: " + hdId );
			if(db.updateReturnInt(query)<=0  )
			{
				msg = "Khong the cap nhat HOADON_SP_CHITIET: " + query;
				db.getConnection().rollback();
				return false;
			}
			query = "delete HOADON_CHIETKHAU where hoadon_fk = '" + this.id + "' ";
			if(!db.update(query)  )
			{
				msg = "Khong the cap nhat HOADON_CHIETKHAU: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			//LUU LAI THONG TIN CHIET KHAU
			int donhang_sau_ngay_01 = this.CompareDATE(this.ngayxuatHD, "2014-10-01");
			String hienthi = "0";
			if(donhang_sau_ngay_01 < 0)
				hienthi = "1";
			
			query = "insert HOADON_CHIETKHAU(hoadon_fk, donhang_fk, diengiai, chietkhau, thueVAT, STT, tichluyQUY, HIENTHI) " +
					"	select '" + this.id + "', donhang_fk, N'CN5' as diengiai, sum(chietkhau) as chietkhau, thueVAT, 1 as STT, 0 as tichluyQUY, '" + hienthi + "' as HIENTHI  " +
					"	from DONHANG_SANPHAM  " +
					"	where thueVAT = '5' and donhang_fk in ( select DDH_FK from HOADON_DDH where HOADON_FK = '" + this.id + "' ) and chietkhau > 0 " +
					"	group by donhang_fk, thueVAT " +
					"	 " +
					"union   " +
					"	select '" + this.id + "', donhang_fk, N'CN10' as diengiai, sum(chietkhau) as chietkhau, thueVAT, 2 as STT, 0 as tichluyQUY, '" + hienthi + "' as HIENTHI   " +
					"	from DONHANG_SANPHAM   " +
					"	where thueVAT = '10' and donhang_fk in ( select DDH_FK from HOADON_DDH where HOADON_FK = '" + this.id + "' ) and chietkhau > 0 " +
					"	group by donhang_fk, thueVAT " +
					"union  " +
					"	select '" + this.id + "', donhang_fk, a.diengiai, a.thanhtoan / ( 1 + ptTHUE / 100 ) as chietkhau, a.ptTHUE as thueVAT, 3 as STT, tichluyQUY, HIENTHI   " +
					"	from DUYETTRAKHUYENMAI_DONHANG a inner join TIEUCHITHUONGTL b on a.duyetkm_fk = b.PK_SEQ   " +
					"	where a.thanhtoan > 0 and donhang_fk in ( select DDH_FK from HOADON_DDH where HOADON_FK = '" + this.id + "' )  " +
					"union  " +
					"	select '" + this.id + "', donhang_fk, a.maloai as diengiai, a.chietkhau, a.ptVAT as thueVAT, 4 as STT, 0 as tichluyQUY, 1 as HIENTHI   " +
					"	from DONHANG_CHIETKHAUBOSUNG a   " +
					"	where a.chietkhau > 0 and donhang_fk in ( select DDH_FK from HOADON_DDH where HOADON_FK = '" + this.id + "' )  ";
			if( !db.update(query)  )
			{
				msg = "Khong the cap nhat HOADON_CHIETKHAU: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			
			/*query = "update hd set hd.tongtienbVAT = TGT.tongtienBVAT, hd.chietkhau = TGT.tongCK,  " +
					"			  hd.vat = TGT.thueVAT, hd.tongtienAVAT = TGT.tongtienAVAT " +
					"from HOADON hd " +
					"inner join " +
					"( " +
					"	select hangban.hoadon_fk, hangban.thanhtien as tongtienBVAT, isnull(chietkhau.thanhtien, 0) as tongCK, " +
					"				hangban.thueVAT - isnull(chietkhau.thueVAT, 0) as thueVAT, " +
					"				hangban.thanhtien + hangban.thueVAT - ( isnull(chietkhau.thanhtien, 0) + isnull(chietkhau.thueVAT, 0) ) as tongtienAVAT " +
					"	from HOADON hd inner join " +
					"	( " +
					"		select hoadon_fk,   " +
					"				sum( cast( ( SOLUONG * DONGIA ) as numeric(18, 0) ) ) as thanhtien,   " +
					"				sum( cast( (SOLUONG * DONGIA * thuevat / 100) as numeric(18, 0) ) )  as thueVAT " +
					"		from HOADON_SP_CHITIET  " +
					"		where hoadon_fk = '" + this.id + "'  " +
					"		group by  hoadon_fk " +
					"	) " +
					"	hangban on hd.pk_seq = hangban.hoadon_fk left join " +
					"	( " +
					"		select hoadon_fk,  " +
					"				SUM(  cast( chietkhau  as numeric(18, 0) )  ) as thanhtien,   " +
					"				SUM(  cast ( ( chietkhau * thueVAT / 100 ) as numeric(18, 0) )  )  as thueVAT " +
					"		from HOADON_CHIETKHAU   " +
					"		where hoadon_fk = '" + this.id + "'  " +
					"		group by hoadon_fk  " +
					"	) " +
					"	chietkhau on hd.pk_seq = chietkhau.hoadon_fk " +
					") " +
					"TGT on hd.pk_seq = TGT.hoadon_fk ";*/
			
			//HÓA ĐƠN MỚI -> theo cách làm tròn của FAST: VAT = ROUND( soluong * dongia ) * VAT / 100
			query = "update hd set hd.tongtienbVAT = TGT.tongtienBVAT, hd.chietkhau = TGT.tongCK,  " +
					"			  hd.vat = TGT.thueVAT, hd.tongtienAVAT = TGT.tongtienAVAT " +
					"from HOADON hd " +
					"inner join " +
					"( " +
					"	select hangban.hoadon_fk, hangban.thanhtien as tongtienBVAT, isnull(chietkhau.thanhtien, 0) as tongCK, " +
					"				hangban.thueVAT - isnull(chietkhau.thueVAT, 0) as thueVAT, " +
					"				hangban.thanhtien + hangban.thueVAT - ( isnull(chietkhau.thanhtien, 0) + isnull(chietkhau.thueVAT, 0) ) as tongtienAVAT " +
					"	from HOADON hd inner join " +
					"	( " +
					"		select hoadon_fk,   " +
					"			sum( round( (SOLUONG * DONGIA ), 0 ) ) as thanhtien,    " +
					"			sum( round( ( round( (SOLUONG * DONGIA ), 0 ) * VAT / 100 ), 0 ) ) as thueVAT	 " +
					"		from HOADON_SP  " +
					"		where hoadon_fk = '" + this.id + "'  " +
					"		group by  hoadon_fk " +
					"	) " +
					"	hangban on hd.pk_seq = hangban.hoadon_fk left join " +
					"	( " +
					"		select hoadon_fk,  " +
					"				round ( SUM( chietkhau ), 0 ) as thanhtien, " +
					"				round ( SUM( round(chietkhau, 0) * thueVAT / 100 ), 0)  as thueVAT  " +
					"		from HOADON_CHIETKHAU   " +
					"		where hoadon_fk = '" + this.id + "' and HIENTHI = '1' " +
					"		group by hoadon_fk  " +
					"	) " +
					"	chietkhau on hd.pk_seq = chietkhau.hoadon_fk " +
					") " +
					"TGT on hd.pk_seq = TGT.hoadon_fk ";
			
			if(!db.update(query))
			{
				msg = "Khong the cap nhat HOADON_CHIETKHAU: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			Utility util = new Utility();
			msg= util.Check_Huy_NghiepVu_KhoaSo("HoaDon", id, "NgayXuatHD");
			if(msg.length()>0)
			{
				db.getConnection().rollback();
				return false;
			}
			msg= util.Check_Kho_Tong_VS_KhoCT(util.getIdNhapp(userId));
			if(msg.length()>0)
			{
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

	
	public boolean update() 
	{
		this.getNppInfo();
		
		//NEU HOA DON DA HUY THI CHI DUOC SUA KY HIEU VA SO HOA DON
		String sqlCHECK = "select trangthai from HOADON where pk_seq = '" + this.id + "'";
		ResultSet rsCHECK = db.get(sqlCHECK);
		String trangthai = "";
		try 
		{
			if(rsCHECK.next())
			{
				trangthai = rsCHECK.getString("trangthai");
			}
			rsCHECK.close();
		} 
		catch (Exception e2) 
		{
			e2.printStackTrace();
		}
	
		if(this.ngayxuatHD.trim().length() < 10)
		{
			this.msg = "Vui lòng nhập ngày xuất hóa đơn";
			return false;
		}

		if(this.ngayghinhanCN.trim().length() < 10)
		{
			this.msg = "Vui lòng nhập ngày ghi nhận";
			return false;
		}
		
		if(trangthai.equals("1") || trangthai.equals("2") || trangthai.equals("4") )
		{
			if(this.ddhId.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn đơn đặt hàng";
				return false;
			}		
			
			if(spMa == null)
			{
				this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm  xuất hoá đơn tài chính";
				return false;
			}
			else
			{
				boolean coSP = false;
				for(int i = 0; i < spId.length; i++)
				{
					if( spSoluong[i].trim().length() > 0 )
					{
						if(Double.parseDouble(spSoluong[i].trim().replaceAll(",", "")) > 0)
							coSP = true;
					}
				}
				
				if(!coSP)
				{
					this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm  xuất hoá đơn tài chính";
					return false;
				}	
			}
		}
		
		try
		{
			db.update("SET TRANSACTION ISOLATION LEVEL SNAPSHOT;");
			db.update("SET LOCK_TIMEOUT 60000;");
			db.getConnection().setAutoCommit(false);
			String query ="";
			
			// Kiểm tra npp nếu là Đối tác thì không dùng Số hóa đơn của hệ thống
			query = " select loaiNPP, (  select PXK_FK from PHIEUXUATKHO_DONHANG a inner join PHIEUXUATKHO b on a.pxk_fk = b.pk_seq where b.trangthai = '1' and donhang_fk in ( " + this.ddhId + " ) ) as pxkID " +
					" from NHAPHANPHOI " +
					" where pk_seq = '" + nppId + "' ";	
			ResultSet rsNpp = db.get(query);
			String loainpp= "";
			String pxkID = "";
			if(rsNpp!= null)
			{
				while(rsNpp.next())
				{
					loainpp = rsNpp.getString("loaiNPP");
					pxkID = rsNpp.getString("pxkID");
				}
				rsNpp.close();
			}
				
			if(!loainpp.equals("4") && !loainpp.equals("5"))	// KHONG PHAI LA DOI TAC VA CHI NHANH CUA DOI TAC
			{
				
				// LAY KY HIEU HOA DON ,SOHDTU TRONG DLN
				String chuoi="";
				long sohoadontu=0;
				String sohoadonden="";
				int kbDLN=0;
				
				if(this.nppId.equals("100002")) // CN HÀ NỘI DÙNG MẪU 2
				{

					// LAY KY HIEU HOA DON ,SOHDTU TRONG DLN
					query= " select  sohoadontu2, sohoadonden2 " +
						   " from NHANVIEN" +
						   " where pk_seq= '"+ this.userId +"' and isnull(kyhieu2, '')= '"+ this.kyhieuhoadon +"' ";
					ResultSet rsLayDL =  db.get(query);
					if(rsLayDL != null)
					{
						while(rsLayDL.next())
						{
							sohoadontu = rsLayDL.getLong("sohoadontu2");
							sohoadonden = rsLayDL.getString("sohoadonden2");
						}
						rsLayDL.close();
					}
					if(sohoadontu == 0 || sohoadonden.trim().length() <= 0)
					{
						this.msg = " Ký hiệu "+ this.kyhieuhoadon +" không giống với ký hiệu khai báo trong dữ liệu nền/ Chưa khai báo số hóa đơn trong dữ liệu nền (Số hóa đơn) ";
						db.getConnection().rollback();
						return false;
					}
					
					// Check So hoa don sua da co dung chua
					   // OTC
					query= " select  count(pk_seq) as kiemtra " +
					       " from HOADON " +
					       " where nguoisua= '"+ this.userId +"' and RTRIM(LTRIM(sohoadon)) = '"+ this.sohoadon.trim() +"' and pk_seq != "+ this.id +" " +
					       "       and trangthai != 3  and mauhoadon = 2 and RTRIM(LTRIM(isnull(kyhieu, ''))) = RTRIM(LTRIM('"+ this.kyhieuhoadon +"'))  ";
					System.out.println("Cau kiem tra so hoa don "+query);
					ResultSet KtraSHD =  db.get(query);
					int ktra= 0;
					
					if(KtraSHD != null)
					{
						while(KtraSHD.next())
						{
							ktra = KtraSHD.getInt("kiemtra");
						}
						KtraSHD.close();
					}
					
					  // ETC
					query= " select  count(pk_seq) as kiemtra " +
					       " from ERP_HOADONNPP " +
					       " where nguoisua= '"+ this.userId +"' and RTRIM(LTRIM(sohoadon)) = '"+ this.sohoadon.trim() +"' and pk_seq != "+ this.id +" " +
					       "        and trangthai != 3 and mauhoadon = 2 and RTRIM(LTRIM(isnull(kyhieu, ''))) = RTRIM(LTRIM('"+ this.kyhieuhoadon +"')) ";
					System.out.println("Cau kiem tra so hoa don "+query);
					ResultSet KtraSHD_ETC =  db.get(query);
					int ktra_ETC= 0;
					
					if(KtraSHD_ETC != null)
					{
						while(KtraSHD_ETC.next())
						{
							ktra_ETC = KtraSHD_ETC.getInt("kiemtra");
						}
						KtraSHD_ETC.close();
					}
					
					
					if(Integer.parseInt(sohoadonden.trim()) <  Integer.parseInt(this.sohoadon.trim()))
					{
						this.msg = "Số hóa đơn này đã vượt quá số hóa đơn đến trong dữ liệu nền. Vui lòng thiết lập lại số hóa đơn ";
						db.getConnection().rollback();
						return false;
					}
					else if (ktra > 0 || ktra_ETC > 0 )
					{
						this.msg = "Số hóa đơn này đã được sử dụng. Vui lòng đánh số hóa đơn khác. ";
						db.getConnection().rollback();
						return false;
					}
					else if (this.sohoadon.trim().length() != 7 )
					{
						this.msg = "Số hóa đơn phải đủ 7 ký tự. Vui lòng kiểm tra lại.";
						db.getConnection().rollback();
						return false;
					}
				  
				}
				else
				{
				 query= " select  sohoadontu, sohoadonden " +
						" from NHANVIEN " +
						" where pk_seq= '"+ this.userId +"' and " +
						"       isnull(kyhieu,'') = '"+ this.kyhieuhoadon +"' ";
				
				ResultSet rsLayDL =  db.get(query);
				{
					while(rsLayDL.next())
					{
						sohoadontu = rsLayDL.getLong("sohoadontu");
						sohoadonden = rsLayDL.getString("sohoadonden");
					}
					rsLayDL.close();
				}
				if(sohoadontu == 0 || sohoadonden.trim().length() <= 0)
				{
					this.msg = " Ký hiệu "+ this.kyhieuhoadon +" không giống với ký hiệu khai báo trong dữ liệu nền/ Chưa khai báo số hóa đơn trong dữ liệu nền (Số hóa đơn) ";
					db.getConnection().rollback();
					return false;
				}
				
				// Check So hoa don sua da co dung chua
				   // OTC
				query= " select  count(pk_seq) as kiemtra " +
				       " from HOADON " +
				       " where nguoisua= '"+ this.userId +"' and RTRIM(LTRIM(sohoadon)) = '"+ this.sohoadon.trim() +"' and pk_seq != "+ this.id +" " +
				       "        and mauhoadon = 1 and trangthai != 3 and RTRIM(LTRIM(isnull(kyhieu, ''))) = RTRIM(LTRIM('"+ this.kyhieuhoadon +"')) ";
				System.out.println("Cau kiem tra so hoa don "+query);
				ResultSet KtraSHD =  db.get(query);
				int ktra= 0;
				
				if(KtraSHD != null)
				{
					while(KtraSHD.next())
					{
						ktra = KtraSHD.getInt("kiemtra");
					}
					KtraSHD.close();
				}
				
				  // ETC
				query= " select  count(pk_seq) as kiemtra " +
				       " from ERP_HOADONNPP " +
				       " where nguoisua= '"+ this.userId +"' and trangthai != 3 and RTRIM(LTRIM(sohoadon)) = '"+ this.sohoadon.trim() +"' and pk_seq != "+ this.id +" " +
				       "       and mauhoadon = 1  and RTRIM(LTRIM(isnull(kyhieu, ''))) = RTRIM(LTRIM('"+ this.kyhieuhoadon +"')) ";
				System.out.println("Cau kiem tra so hoa don "+query);
				ResultSet KtraSHD_ETC =  db.get(query);
				int ktra_ETC= 0;
				
				if(KtraSHD_ETC != null)
				{
					while(KtraSHD_ETC.next())
					{
						ktra_ETC = KtraSHD_ETC.getInt("kiemtra");
					}
					KtraSHD_ETC.close();
				}
				
				
				if(Integer.parseInt(sohoadonden.trim()) <  Integer.parseInt(this.sohoadon.trim()))
				{
					this.msg = "Số hóa đơn này đã vượt quá số hóa đơn đến trong dữ liệu nền. Vui lòng thiết lập lại số hóa đơn ";
					db.getConnection().rollback();
					return false;
				}
				else if (ktra > 0 || ktra_ETC > 0 )
				{
					this.msg = "Số hóa đơn này đã được sử dụng. Vui lòng đánh số hóa đơn khác. ";
					db.getConnection().rollback();
					return false;
				}
				else if (this.sohoadon.trim().length() != 7 )
				{
					this.msg = "Số hóa đơn phải đủ 7 ký tự. Vui lòng kiểm tra lại.";
					db.getConnection().rollback();
					return false;
				}
			  }
			}
			
			if( !trangthai.equals("1") )
			{
				query = "Update HOADON set sohoadon = RTRIM(LTRIM('" + this.sohoadon + "')), kyhieu = RTRIM(LTRIM('" + this.kyhieuhoadon + "')) where pk_seq = '" + this.id + "' ";
				System.out.println("1.Update HOADON: " + query);
				if(!db.update(query))
				{
					this.msg = "Lỗi khi cập nhật hóa đơn: " + query;
					db.getConnection().rollback();
					return false;
				}
				Utility util = new Utility();
				msg= util.Check_Huy_NghiepVu_KhoaSo("HOADON", id, "ngayxuatHD");
				if(msg.length()>0)
				{
					db.getConnection().rollback();
					return false;
				}

				//Đã chốt, đã xóa, đã hủy, đã in thì khi thay đổi số hóa đơn phải lưu lại thay đổi đó trong LOG
				
				db.getConnection().commit();
				return true;				
				
			}
			else
			{
				query =    " update HOADON set ngayghinhanCN =  '"+ this.ngayghinhanCN +"', khachhang_fk= "+ this.khId +" , ngayxuatHD = '" + this.ngayxuatHD + "' , ngaysua = '" + getDateTime() + "' , nguoisua ='" + this.userId + "'," +
					       " kyhieu = RTRIM(LTRIM('" + this.kyhieuhoadon + "')) , sohoadon= RTRIM(LTRIM('" + this.sohoadon + "')), hinhthuctt= N'"+ this.hinhthuctt +"' ," +
					       " ghichu= N'"+ this.ghichu +"', NPP_FK= "+ this.nppId +" , loaihoadon = '0', nguoimua = N'"+ this.nguoimua +"', innguoimua = "+ this.innguoimua +", mavv=' "+this.mavuviec+"'  " +
					       " where pk_seq = '"+ this.id +"'" ;
				if(!db.update(query))
				{
					this.msg = "Không thể cập nhật HOADON " + query;
					db.getConnection().rollback();
					return false;
				}
				Utility util = new Utility();
				msg= util.Check_Huy_NghiepVu_KhoaSo("HOADON", id, "ngayxuatHD");
				if(msg.length()>0)
				{
					db.getConnection().rollback();
					return false;
				}

				
				query = " delete from HOADON_SP where hoadon_fk = '"+ this.id +"' " ;
				if(!db.update(query))
				{
					this.msg = "Không thể xóa HOADON_SP " + query;
					db.getConnection().rollback();
					return false;
	            }
				
				query = " delete from HOADON_DDH where hoadon_fk = '"+ this.id +"' " ;
				if(!db.update(query))
				{
					this.msg = "Không thể xóa HOADON_DDH " + query;
					db.getConnection().rollback();
					return false;
	            }
				
				
				query = "Insert HOADON_DDH(hoadon_fk, ddh_fk) select " + this.id +" ,  pk_seq from DONHANG where pk_seq in ( " + this.ddhId + " ) ";
				if(!db.update(query))
				{
					this.msg = "Không thể tạo mới HOADON_DDH " + query;
					db.getConnection().rollback();
					return false;
				}
						
				
				for(int i = 0; i < spId.length; i++)
				{
					if(spId[i].trim().length() > 0 && Double.parseDouble(spSoluong[i].trim().replaceAll(",", "")) > 0 )
					{
						query = "insert HOADON_SP( hoadon_fk, sanpham_fk, donvitinh, soluong, dongia, thanhtien, chietkhau, scheme, vat, dongiaGOC ) " +
								" select " + this.id + " , '" + spId[i] + "', N'"+ spDonvi[i] +"', '" + spSoluong[i].replaceAll(",", "") + "', '" + spDongia[i].replaceAll(",", "") + "'," +
								" (" + spSoluong[i].replaceAll(",", "")+ " * "+ spDongia[i].replaceAll(",", "") +"), '"+ spChietkhau[i].replaceAll(",", "") +"', N'"+ spSCheme[i].replaceAll(",", "") +"', '" + spVat[i].replaceAll(",", "") + "', " +
								"		ISNULL ( ( select giabanleNPP from BGBANLENPP_SANPHAM where sanpham_fk = '" + spId[i] + "' and BGBANLENPP_FK in ( select pk_seq from BANGGIABANLENPP where npp_fk = '" + this.nppId + "'  ) ), 0) as dongiaGOC ";
				
						
						System.out.println("1.1.Insert HOADON_SP: " + query);
						if(!db.update(query))
						{
							this.msg = "Khong the tao moi HOADON_SP: " + query;
							db.getConnection().rollback();
							return false;
						}
					}
				}
			}
			 		
			query = "Update PHIEUXUATKHO set NGAYLAPPHIEU = '" + this.ngayxuatHD + "' " +
					"where PK_SEQ in ( select pxk_fk from PHIEUXUATKHO_DONHANG where donhang_fk in  ("+this.ddhId+") )  ";
			//System.out.println("---3.HUY HOA DON: " + query );
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới HOADON " + query;
				db.getConnection().rollback();
				return false;
			}
			
			//XÓA PXK CŨ
			query =
			"update kho set kho.SOLUONG = kho.SOLUONG + xuat.soluong, " +
			"			   kho.AVAILABLE = kho.AVAILABLE + xuat.soluong	 " +
			"from NHAPP_KHO_CHITIET kho inner join " +
			"( " +
			"	select kho_fk, kbh_fk, NPP_FK, SANPHAM_FK, SOLO, SUM(soluong) as soluong ,NgayHetHan" +
			"   from (	" +
			"		select b.kho_fk, b.kbh_fk, a.NPP_FK, b.SANPHAM_FK, b.SOLO, SUM(b.soluong) as soluong,b.NgayHetHan " +
			"		from PHIEUXUATKHO a inner join PHIEUXUATKHO_SANPHAM_CHITIET b on a.PK_SEQ = b.PXK_FK " +
			"		where a.PK_SEQ = '" + pxkID + "' " +
			"		group by b.kho_fk, b.kbh_fk, a.NPP_FK, b.SANPHAM_FK, b.SOLO,b.NgayHetHan " +
			"	)" +
			"	TX group by kho_fk, kbh_fk, NPP_FK, SANPHAM_FK, SOLO,NgayHetHan " +
			") " +
			"xuat on kho.SANPHAM_FK = xuat.SANPHAM_FK and kho.NPP_FK = xuat.NPP_FK and kho.KBH_FK = xuat.kbh_fk and kho.KHO_FK = xuat.kho_fk and kho.SOLO = xuat.SOLO and kho.NgayHetHan=xuat.NgayHetHan";
			System.out.println("2.TANG KHO: " + query);
			if(!db.update(query))
			{
				this.msg = "Không thể hủy " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete PHIEUXUATKHO_SANPHAM_CHITIET where PXK_FK = '" + pxkID + "'  ";
			if(!db.update(query))
			{
				this.msg = "Không thể hủy " + query;
				db.getConnection().rollback();
				return false;
			}
			
			//CHEN LAI PXK TRONG TRUONG HOP DOI SOLO
			for(int i = 0; i < spId.length; i++)
			{
				if(spId[i].trim().length() > 0 && Double.parseDouble(spSoluong[i].trim().replaceAll(",", "")) > 0 )
				{
					if(this.sanpham_soluong.size() > 0)
					{
						Enumeration<String> keys = this.sanpham_soluong.keys();
						double totalCT = 0;
						
						while(keys.hasMoreElements())
						{
							String key = keys.nextElement();
							System.out.println("----KEY: " + key + "  -- ID: " + spId[i]);
							
							if(key.startsWith( spId[i]+"__") )
							{
								String[] _sp = key.split("__");
								
								String _soluongCT = "0"; 
								if(this.sanpham_soluong.get(key) != null)
								{
									_soluongCT = this.sanpham_soluong.get(key).replaceAll(",", "");
								}
								
								totalCT += Double.parseDouble(_soluongCT);
								
								//CHECK TỒN KHO CỦA LÔ MUỐN ĐỔI CÓ ĐỦ KHÔNG
								query = "select AVAILABLE from NHAPP_KHO_CHITIET " +
										"where NPP_FK = '" + this.nppId + "' and kho_fk = ( select kho_fk from DONHANG where pk_seq in (" + this.ddhId + ") ) and kbh_fk = '100025' and sanpham_fk = '" + _sp[0] + "' and SOLO = '" + _sp[1] + "' and ngayhethan = '" + _sp[2] + "' ";
								System.out.println("1.1.Check TONKHO: " + query);
								ResultSet rsCHECK_TK = db.get(query);
								double avai = 0;
								if(rsCHECK_TK.next())
								{
									avai = rsCHECK_TK.getDouble("AVAILABLE");
								}
								rsCHECK_TK.close();
								
								if(avai < Double.parseDouble(_soluongCT) )
								{
									db.getConnection().rollback();
									this.msg = "Sản phẩm ( " + spTen[i] + " ) với số lô ( " + _sp[1] + " ), ngày hết hạn ( " + _sp[2] + " ) còn tối đa ( " + avai + " ) ";
									return false;
								}
								
								
								query = "insert PHIEUXUATKHO_SANPHAM_CHITIET(PXK_FK, SANPHAM_FK, SOLUONG, SOLO, NGAYHETHAN, KBH_FK, KHO_FK)" +
										"select '" + pxkID + "', '" + _sp[0] + "', '" + _soluongCT + "', '" + _sp[1] + "', '" + _sp[2] + "', '100025', ( select kho_fk from DONHANG where pk_seq in (" + this.ddhId + ") ) as kho_fk  ";
								
								System.out.println("1.2.Insert PHIEUXUATKHO_SANPHAM_CHITIET: " + query);
								if(db.updateReturnInt(query)!=1)
								{
									this.msg = "Khong the tao moi PHIEUXUATKHO_SANPHAM_CHITIET: " + query;
									db.getConnection().rollback();
									return false;
								}
								
								query = "Update NHAPP_KHO_CHITIET set soluong = soluong - '" + _soluongCT + "', AVAILABLE = AVAILABLE - '" + _soluongCT + "' " +
										"where NPP_FK = '" + this.nppId + "' and kho_fk = ( select kho_fk from DONHANG where pk_seq in (" + this.ddhId + ") ) and kbh_fk = '100025' and sanpham_fk = '" + _sp[0] + "' and SOLO = '" + _sp[1] + "' and ngayhethan = '" + _sp[2] + "' ";
								System.out.println("1.2.Insert NHAPP_KHO_CHITIET: " + query);
								if(db.updateReturnInt(query)!=1)
								{
									this.msg = "Khong the tao moi NHAPP_KHO_CHITIET: " + query;
									db.getConnection().rollback();
									return false;
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
			
			
			
			
			query = "delete HOADON_CHIETKHAU where hoadon_fk = '" + this.id + "' ";
			if(!db.update(query)  )
			{
				msg = "Khong the cap nhat HOADON_CHIETKHAU: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			//LUU LAI THONG TIN CHIET KHAU
			int donhang_sau_ngay_01 = this.CompareDATE(this.ngayxuatHD, "2014-10-01");
			String hienthi = "0";
			if(donhang_sau_ngay_01 < 0)
				hienthi = "1";
			
			query = "insert HOADON_CHIETKHAU(hoadon_fk, donhang_fk, diengiai, chietkhau, thueVAT, STT, tichluyQUY, HIENTHI) " +
					"	select '" + this.id + "', donhang_fk, N'CN5' as diengiai, sum(chietkhau) as chietkhau, thueVAT, 1 as STT, 0 as tichluyQUY, '" + hienthi + "' as HIENTHI  " +
					"	from DONHANG_SANPHAM  " +
					"	where thueVAT = '5' and donhang_fk in ( select DDH_FK from HOADON_DDH where HOADON_FK = '" + this.id + "' ) and chietkhau > 0 " +
					"	group by donhang_fk, thueVAT " +
					"	 " +
					"union   " +
					"	select '" + this.id + "', donhang_fk, N'CN10' as diengiai, sum(chietkhau) as chietkhau, thueVAT, 2 as STT, 0 as tichluyQUY, '" + hienthi + "' as HIENTHI   " +
					"	from DONHANG_SANPHAM   " +
					"	where thueVAT = '10' and donhang_fk in ( select DDH_FK from HOADON_DDH where HOADON_FK = '" + this.id + "' ) and chietkhau > 0 " +
					"	group by donhang_fk, thueVAT " +
					"union  " +
					"	select '" + this.id + "', donhang_fk, a.diengiai, a.thanhtoan / ( 1 + ptTHUE / 100 ) as chietkhau, a.ptTHUE as thueVAT, 3 as STT, tichluyQUY, HIENTHI   " +
					"	from DUYETTRAKHUYENMAI_DONHANG a inner join TIEUCHITHUONGTL b on a.duyetkm_fk = b.PK_SEQ   " +
					"	where a.thanhtoan > 0 and donhang_fk in ( select DDH_FK from HOADON_DDH where HOADON_FK = '" + this.id + "' )  " +
					"union  " +
					"	select '" + this.id + "', donhang_fk, a.maloai as diengiai, a.chietkhau, a.ptVAT as thueVAT, 4 as STT, 0 as tichluyQUY, 1 as HIENTHI   " +
					"	from DONHANG_CHIETKHAUBOSUNG a   " +
					"	where a.chietkhau > 0 and donhang_fk in ( select DDH_FK from HOADON_DDH where HOADON_FK = '" + this.id + "' )  ";
			if( !db.update(query)  )
			{
				msg = "Khong the cap nhat HOADON_CHIETKHAU: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			
			/*query = "update hd set hd.tongtienbVAT = TGT.tongtienBVAT, hd.chietkhau = TGT.tongCK,  " +
					"			  hd.vat = TGT.thueVAT, hd.tongtienAVAT = TGT.tongtienAVAT " +
					"from HOADON hd " +
					"inner join " +
					"( " +
					"	select hangban.hoadon_fk, hangban.thanhtien as tongtienBVAT, isnull(chietkhau.thanhtien, 0) as tongCK, " +
					"				hangban.thueVAT - isnull(chietkhau.thueVAT, 0) as thueVAT, " +
					"				hangban.thanhtien + hangban.thueVAT - ( isnull(chietkhau.thanhtien, 0) + isnull(chietkhau.thueVAT, 0) ) as tongtienAVAT " +
					"	from HOADON hd inner join " +
					"	( " +
					"		select hoadon_fk,   " +
					"				sum( cast( ( SOLUONG * DONGIA ) as numeric(18, 0) ) ) as thanhtien,   " +
					"				sum( cast( (SOLUONG * DONGIA * thuevat / 100) as numeric(18, 0) ) )  as thueVAT " +
					"		from HOADON_SP_CHITIET  " +
					"		where hoadon_fk = '" + this.id + "'  " +
					"		group by  hoadon_fk " +
					"	) " +
					"	hangban on hd.pk_seq = hangban.hoadon_fk left join " +
					"	( " +
					"		select hoadon_fk,  " +
					"				SUM(  cast( chietkhau  as numeric(18, 0) )  ) as thanhtien,   " +
					"				SUM(  cast ( ( chietkhau * thueVAT / 100 ) as numeric(18, 0) )  )  as thueVAT " +
					"		from HOADON_CHIETKHAU   " +
					"		where hoadon_fk = '" + this.id + "'  " +
					"		group by hoadon_fk  " +
					"	) " +
					"	chietkhau on hd.pk_seq = chietkhau.hoadon_fk " +
					") " +
					"TGT on hd.pk_seq = TGT.hoadon_fk ";*/
			
			//HÓA ĐƠN MỚI -> theo cách làm tròn của FAST: VAT = ROUND( soluong * dongia ) * VAT / 100
			query = "update hd set hd.tongtienbVAT = TGT.tongtienBVAT, hd.chietkhau = TGT.tongCK,  " +
					"			  hd.vat = TGT.thueVAT, hd.tongtienAVAT = TGT.tongtienAVAT " +
					"from HOADON hd " +
					"inner join " +
					"( " +
					"	select hangban.hoadon_fk, hangban.thanhtien as tongtienBVAT, isnull(chietkhau.thanhtien, 0) as tongCK, " +
					"				hangban.thueVAT - isnull(chietkhau.thueVAT, 0) as thueVAT, " +
					"				hangban.thanhtien + hangban.thueVAT - ( isnull(chietkhau.thanhtien, 0) + isnull(chietkhau.thueVAT, 0) ) as tongtienAVAT " +
					"	from HOADON hd inner join " +
					"	( " +
					"		select hoadon_fk,   " +
					"			sum( round( (SOLUONG * DONGIA ), 0 ) ) as thanhtien,    " +
					"			sum( round( ( round( (SOLUONG * DONGIA ), 0 ) * VAT / 100 ), 0 ) ) as thueVAT	 " +
					"		from HOADON_SP  " +
					"		where hoadon_fk = '" + this.id + "'  " +
					"		group by  hoadon_fk " +
					"	) " +
					"	hangban on hd.pk_seq = hangban.hoadon_fk left join " +
					"	( " +
					"		select hoadon_fk,  " +
					"				round ( SUM( chietkhau ), 0 ) as thanhtien, " +
					"				round ( SUM( round(chietkhau, 0) * thueVAT / 100 ), 0)  as thueVAT  " +
					"		from HOADON_CHIETKHAU   " +
					"		where hoadon_fk = '" + this.id + "' and HIENTHI = '1' " +
					"		group by hoadon_fk  " +
					"	) " +
					"	chietkhau on hd.pk_seq = chietkhau.hoadon_fk " +
					") " +
					"TGT on hd.pk_seq = TGT.hoadon_fk ";
			
			if(!db.update(query))
			{
				msg = "Khong the cap nhat HOADON_CHIETKHAU: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			Utility util = new Utility();
			msg= util.Check_Huy_NghiepVu_KhoaSo("HoaDon", id, "NgayXuatHD");
			if(msg.length()>0)
			{
				db.getConnection().rollback();
				return false;
			}
			msg= util.Check_Kho_Tong_VS_KhoCT(util.getIdNhapp(userId));
			if(msg.length()>0)
			{
				db.getConnection().rollback();
				return false;
			}
				
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			geso.dms.center.util.Utility.rollback_throw_exception(db);			
			return false;
		}
		
		return true;
	}
	
	
	public String[] getSpLoai() {

		return this.spLoai;
	}


	public void setSpLoai(String[] spLoai) {

		this.spLoai = spLoai;
	}

	
	public String[] getSpScheme() {
		
		return this.spSCheme;
	}

	
	public void setSpScheme(String[] spScheme) {
		
		this.spSCheme = spScheme;
	}

	public boolean chot(String hdId, boolean updateBEFORE) 
	{
		//Chốt trong chỗ cập nhật hóa đơn
		if(updateBEFORE)
		{
			boolean kqUPDATE = this.update();
			if(kqUPDATE == false)
			{
				return false;
			}
		}
		
		try
		{
			this.id = hdId;
			
			db.update("SET TRANSACTION ISOLATION LEVEL SNAPSHOT;");
			db.update("SET LOCK_TIMEOUT 60000;");
			db.getConnection().setAutoCommit(false);
			
			//KHONG DUOC CHOT HOA DON TRONG THANG DA KHOA SO
			String query = "select top(1) NAM as namMax, THANGKS as thangMax, " +
			"	( select ngayxuatHD from HOADON where pk_seq = '" + hdId + "' ) as ngaylapphieu, " +
			"	( select trangthai from HOADON where pk_seq = '" + hdId + "' ) as trangthai, " +
			"	( select count(*) as soDONG from HOADON where sohoadon = ( select sohoadon from HOADON where pk_seq = '" + hdId + "' ) and sohoadon != 'NA' and npp_fk = ( select npp_fk from HOADON where pk_seq = '" + hdId + "' ) and trangthai != '3' and KYHIEU = ( select KYHIEU from HOADON where pk_seq = '" + hdId + "' ) ) as soDONG_SOHOADON " +
			"from KHOASOTHANG where NPP_FK = ( select NPP_FK from HOADON where pk_seq = '" + hdId + "' ) " +
			"order by NAM desc, THANGKS desc ";
			System.out.println("1.Khoi tao thang: " + query);
			ResultSet rsKS = db.get(query);
			
			String trangthai = "";
			int soDONG_SOHOADON = 0;
			try
			{
				{
					while(rsKS.next())
					{
						trangthai = rsKS.getString("trangthai");
						int thangKs = rsKS.getInt("thangMax");
						int namKs = rsKS.getInt("namMax"); 
						this.ngayxuatHD = rsKS.getString("ngaylapphieu");
						soDONG_SOHOADON = rsKS.getInt("soDONG_SOHOADON");
				
						if(soDONG_SOHOADON > 1)
						{
							msg = "Số hóa đơn đã bị trùng. Vui lòng kiểm tra lại.";
							db.getConnection().rollback();
							rsKS.close();
							return false;
						}
						
						int nam = Integer.parseInt( rsKS.getString("ngaylapphieu").substring(0, 4) );
						int thang = Integer.parseInt( rsKS.getString("ngaylapphieu").substring(5, 7) );
						
						if(	thangKs == thang && nam == namKs )
						{
							msg = "Bạn không được chốt hóa đơn trong tháng đã khóa sổ";
							db.getConnection().rollback();
							rsKS.close();
							return false;
						}
						
					}
					rsKS.close();
				}
			}
			catch (Exception e) 
			{
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				e.printStackTrace();
				msg = "Lỗi khi chốt hóa đơn " + e.getMessage();
				return false;
			}
			
			if(!trangthai.equals("1"))
			{
				msg = "Trạng thái hóa đơn không hợp lệ. Vui lòng kiểm tra lại.";
				db.getConnection().rollback();
				return false;
			}
			
			query = "update HOADON set trangthai = '2'  where pk_seq = '" + this.id + "' ";
			if(!db.update(query))
			{
				msg = "Không thể chốt ERP_HOADON " + query;
				db.getConnection().rollback();
				return false;
			}
			
			//INSET CHIET KHAU --> KHONG LAY TRUC TIEP TU DON HANG, BUOC PHAI COPY SANG
			//TONG SO LUONG TRONG DON HANG PHAI BANG TRONG XUAT KHO
			query = "select count(*) as sodong  " +
					"from " +
					"( " +
					"	select sanpham_fk, soluong  " +
					"	from DONHANG_SANPHAM where donhang_fk in ( select DDH_FK from HOADON_DDH where HOADON_FK = '" + this.id + "' ) " +
					") " +
					"dh left join " +
					"( " +
					"	select sanpham_fk, sum(soluong) as soluong  " +
					"	from PHIEUXUATKHO_SANPHAM " +
					"	where PXK_FK in ( select pxk_fk from PHIEUXUATKHO_DONHANG where donhang_fk in ( select DDH_FK from HOADON_DDH where HOADON_FK = '" + this.id + "' ) ) " +
					"			and PXK_FK in ( select pk_seq from PHIEUXUATKHO where NPP_FK = ( select npp_fk from HOADON where pk_seq = '" + this.id + "' ) and trangthai != '2'  )	 " +
					"	group by sanpham_fk " +
					") " +
					"xk on dh.sanpham_fk = xk.sanpham_fk " +
					"where dh.soluong != isnull(xk.soluong, 0) ";
			int soDONG = 0;
			ResultSet rsCHECK = db.get(query);
			{
				if(rsCHECK.next())
				{
					soDONG = rsCHECK.getInt("sodong");
				}
				rsCHECK.close();
			}
			
			if(soDONG > 0)
			{
				msg = "1.Số lượng trong đơn hàng không khớp với hóa đơn. Vui lòng liên hệ Admin để xử lý ";
				db.getConnection().rollback();
				return false;
			}
			
			query = "select count(*) as sodong  " +
					"from " +
					"( " +
					"	select sanpham_fk, soluong  " +
					"	from DONHANG_SANPHAM where donhang_fk in ( select DDH_FK from HOADON_DDH where HOADON_FK = '" + this.id + "' ) " +
					") " +
					"dh left join " +
					"( " +
					"	select sanpham_fk, sum(soluong) as soluong  " +
					"	from PHIEUXUATKHO_SANPHAM_CHITIET " +
					"	where PXK_FK in ( select pxk_fk from PHIEUXUATKHO_DONHANG where donhang_fk in ( select DDH_FK from HOADON_DDH where HOADON_FK = '" + this.id + "' ) ) " +
					"			and PXK_FK in ( select pk_seq from PHIEUXUATKHO where NPP_FK = ( select npp_fk from HOADON where pk_seq = '" + this.id + "' ) and trangthai != '2'  )	 " +
					"	group by sanpham_fk " +
					") " +
					"xk on dh.sanpham_fk = xk.sanpham_fk " +
					"where dh.soluong != isnull(xk.soluong, 0) ";
			soDONG = 0;
			rsCHECK = db.get(query);
			{
				if(rsCHECK.next())
				{
					soDONG = rsCHECK.getInt("sodong");
				}
				rsCHECK.close();
			}
			
			if(soDONG > 0)
			{
				msg = "2.Số lượng trong đơn hàng không khớp với hóa đơn. Vui lòng liên hệ Admin để xử lý ";
				db.getConnection().rollback();
				return false;
			}
			
			
			query = "delete HOADON_CHIETKHAU where hoadon_fk = '" + this.id + "' ";
			if(!db.update(query)  )
			{
				msg = "Khong the cap nhat HOADON_CHIETKHAU: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			//LUU LAI THONG TIN CHIET KHAU
			int donhang_sau_ngay_01 = this.CompareDATE(this.ngayxuatHD, "2014-10-01");
			String hienthi = "0";
			if(donhang_sau_ngay_01 < 0)
				hienthi = "1";
			
			query = "insert HOADON_CHIETKHAU(hoadon_fk, donhang_fk, diengiai, chietkhau, thueVAT, STT, tichluyQUY, HIENTHI) " +
					"	select '" + this.id + "', donhang_fk, N'CN5' as diengiai, sum(chietkhau) as chietkhau, thueVAT, 1 as STT, 0 as tichluyQUY, '" + hienthi + "' as HIENTHI  " +
					"	from DONHANG_SANPHAM  " +
					"	where thueVAT = '5' and donhang_fk in ( select DDH_FK from HOADON_DDH where HOADON_FK = '" + this.id + "' ) and CHIETKHAU > 0 " +
					"	group by donhang_fk, thueVAT " +
					"union   " +
					"	select '" + this.id + "', donhang_fk, N'CN10' as diengiai, sum(chietkhau) as chietkhau, thueVAT, 2 as STT, 0 as tichluyQUY, '" + hienthi + "' as HIENTHI   " +
					"	from DONHANG_SANPHAM   " +
					"	where thueVAT = '10' and donhang_fk in ( select DDH_FK from HOADON_DDH where HOADON_FK = '" + this.id + "' ) and CHIETKHAU > 0 " +
					"	group by donhang_fk, thueVAT " +
					"union  " +
					"	select '" + this.id + "', donhang_fk, a.diengiai, round(a.thanhtoan, 0) / ( 1 + ptTHUE / 100 ) as chietkhau, a.ptTHUE as thueVAT, 3 as STT, tichluyQUY, HIENTHI   " +
					"	from DUYETTRAKHUYENMAI_DONHANG a inner join TIEUCHITHUONGTL b on a.duyetkm_fk = b.PK_SEQ   " +
					"	where a.thanhtoan > 0 and donhang_fk in ( select DDH_FK from HOADON_DDH where HOADON_FK = '" + this.id + "' )  " +
					"union  " +
					"	select '" + this.id + "', donhang_fk, a.maloai as diengiai, a.chietkhau, a.ptVAT as thueVAT, 4 as STT, 0 as tichluyQUY, 1 as HIENTHI   " +
					"	from DONHANG_CHIETKHAUBOSUNG a   " +
					"	where a.chietkhau > 0 and donhang_fk in ( select DDH_FK from HOADON_DDH where HOADON_FK = '" + this.id + "' )  ";
			System.out.println("---CAP NHAT HOA DON - CHIET KHAU: " + query );
			if(!db.update(query)  )
			{
				msg = "Khong the cap nhat HOADON_CHIETKHAU: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			//LUU LAI HOA DON - SAN PHAM
			query = "delete HOADON_SP where hoadon_fk = '" + this.id + "'  ";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				System.out.println("---1.LOI ROI..." + query);
				return false;
			}
			
			query = "insert HOADON_SP( hoadon_fk, sanpham_fk, donvitinh, soluong, dongia, dongiaGOC, thanhtien, chietkhau, scheme, vat )  " +
					"select '" + this.id + "', c.pk_seq, d.DONVI, SUM( f.soluong) as soluong, dhsp.GIAMUA AS dongia, dhsp.dongiaGOC, " +
							"		SUM( f.soluong) * dhsp.GIAMUA, round ( sum(dhsp.chietkhau), 0 ) as chietkhau, '' as scheme, dhsp.thuevat   " +
					"from  DONHANG dh inner join DONHANG_SANPHAM dhsp on dhsp.donhang_fk=dh.PK_SEQ    " +
					"				 inner join SANPHAM c on dhsp.sanpham_fk = c.PK_SEQ                 " +
					"				 left join DONVIDOLUONG d on d.PK_SEQ = c.dvdl_fk   " +
					"				 left join PHIEUXUATKHO_DONHANG e on dh.pk_seq = e.donhang_fk  " +
					"				 left join PHIEUXUATKHO_SANPHAM_CHITIET f on e.pxk_fk = f.pxk_fk and c.pk_seq = f.sanpham_fk  " +
					"where dh.PK_SEQ in ( select ddh_fk from HOADON_DDH where hoadon_fk =  '" + this.id + "' ) and dhsp.soluong > 0  " +
							"	and e.pxk_fk in ( select pk_seq from PHIEUXUATKHO where NPP_FK = ( select npp_fk from HOADON where pk_seq = '" + this.id + "' ) and trangthai != '2' ) 	  " +
					"group by dh.PK_SEQ, c.pk_seq, d.DONVI, dhsp.GIAMUA, dhsp.dongiaGOC, dhsp.thuevat ";
			
			System.out.println("---CHAY HOA DON: " + query );
			if(!db.update(query) )
			{
				db.getConnection().rollback();
				System.out.println("---2.LOI ROI..." + query);
				return false;
			}
			
			//LUU LAI HOA DON - SAN PHAM
			query = "delete HOADON_SP_CHITIET where hoadon_fk = '" + this.id + "'  ";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				System.out.println("---3.LOI ROI..." + query);
				return false;
			}
			
			query = "insert HOADON_SP_CHITIET( hoadon_fk, MA, TEN, DONVI, DONGIA, dongiaGOC, SOLO, NGAYHETHAN, THUEVAT, SOLUONG, CHIETKHAU )  " +
					"select '" + this.id + "' as hoadon_fk, c.MA, c.TEN, d.DONVI, dhsp.GIAMUA AS dongia, dongiaGOC,   " +
					"		case f.solo when 'NA' then ' ' else f.solo end as solo,   " +
					"		case f.solo when 'NA' then ' ' else isnull(f.ngayhethan,'') end as ngayhethan, dhsp.thuevat,     " +
					"	SUM( f.soluong) as soluong, '0' as chietkhau   " +
					"from  DONHANG dh inner join DONHANG_SANPHAM dhsp on dhsp.donhang_fk=dh.PK_SEQ    " +
					"				 inner join SANPHAM c on dhsp.sanpham_fk = c.PK_SEQ                 " +
					"				 left join DONVIDOLUONG d on d.PK_SEQ = c.dvdl_fk   " +
					"				 left join PHIEUXUATKHO_DONHANG e on dh.pk_seq = e.donhang_fk  " +
					"				 left join PHIEUXUATKHO_SANPHAM_CHITIET f on e.pxk_fk = f.pxk_fk and c.pk_seq = f.sanpham_fk  " +
					"where dh.PK_SEQ in (select ddh_fk from HOADON_DDH where hoadon_fk =  '" + this.id + "' )  and  dhsp.soluong > 0   	  " +
					"			and e.pxk_fk in ( select pk_seq from PHIEUXUATKHO where NPP_FK = ( select npp_fk from HOADON where pk_seq = '" + this.id + "' ) and trangthai != '2' ) 	  " +
					"group by  c.MA, c.TEN, d.DONVI, dhsp.GIAMUA, dongiaGOC, dhsp.thuevat, f.solo, f.ngayhethan  ";
			System.out.println("---CHAY HOA DON CHI TIET: " + this.id );
			if(!db.update(query) )
			{
				db.getConnection().rollback();
				System.out.println("---4.LOI ROI..." + query);
				return false;
			}
			
			//CHECK THEM - NEU HOA DON KHAC VOI DON HANG THI KHONG CHO CHOT
			query = "select count(*) as sodong  " +
					"from " +
					"( " +
					"	select sanpham_fk, soluong  " +
					"	from DONHANG_SANPHAM where donhang_fk in ( select DDH_FK from HOADON_DDH where HOADON_FK = '" + this.id + "' ) " +
					") " +
					"dh left join " +
					"( " +
					"	select sanpham_fk, sum(soluong) as soluong  " +
					"	from HOADON_SP " +
					"	where HOADON_FK = '" + this.id + "'  " +
					"	group by sanpham_fk " +
					") " +
					"xk on dh.sanpham_fk = xk.sanpham_fk " +
					"where dh.soluong != isnull(xk.soluong, 0) ";
			System.out.println("---CHECK HOA DON: " + query);
			soDONG = 0;
			rsCHECK = db.get(query);
			{
				if(rsCHECK.next())
				{
					soDONG = rsCHECK.getInt("sodong");
				}
				rsCHECK.close();
			}
			
			if(soDONG > 0)
			{
				msg = "3.Số lượng trong đơn hàng không khớp với hóa đơn. Vui lòng liên hệ Admin để xử lý ";
				db.getConnection().rollback();
				return false;
			}
			
			//CAP NHAT LAI --> CAU NAY DÙNG CHO NHỮNG HÓA ĐƠN CŨ BỊ SAI CHẤP NHẬN LỆCH 1 ĐỒNG, VỚI NHỮNG HÓA ĐƠN MỚI THÌ PHẢI DÙNG CÂU SAU  --> MỚI CHÍNH XÁC ĐƯỢC
			//HÓA ĐƠN CŨ KHI CHƯA ĐƯA CT LÊN
			/*query = "update hd set tongtienavat = a.AVAT, tongtienbvat = a.BVAT, vat = a.VAT  " +
					"from " +
					"( " +
					"	select hd.PK_SEQ AS HDID, " +
					"		  ( HOADON_CT.tongtien - HOADON_CT.Chietkhau) as BVAT , ( HOADON_CT.VAT - HOADON_CT. VAT_KM) as VAT, " +
					"		  round((HOADON_CT.tongtien + HOADON_CT.VAT ) - ( HOADON_CT.Chietkhau  + HOADON_CT.VAT_KM), 0) AS AVAT " +
					"	from HOADON hd inner join " +
					"	(     " +
					"		select AA.HOADON_FK, AA.tongtien, AA .VAT, BB.Chietkhau, BB.VAT as VAT_KM " +
					"		from " +
					"		( " +
					"		   select HOADON_fk, SUM( SOLUONG * DONGIA ) as tongtien , SUM (SOLUONG * DONGIA * VAT /100) as VAT  " +
					"		   from HOADON_SP where HOADON_fk = '" + this.id + "' " +
					"		   group by HOADON_FK " +
					"		 )  " +
					"		 AA inner join " +
					"		 ( " +
					"			select hoadon_fk, sum(chietkhau) as chietkhau, sum(chietkhau * thueVAT / 100) as VAT  " +
					"			from HOADON_CHIETKHAU where HOADON_fk = '" + this.id + "'  " +
					"			group by hoadon_fk " +
					"		)  " +
					"		BB  ON AA. HOADON_FK=BB .HOADON_FK  " +
					"	)  " +
					"	HOADON_CT on hd.PK_SEQ = HOADON_CT. HOADON_FK " +
					"       left join KHACHHANG kh on kh .PK_SEQ= hd.KHACHHANG_FK " +
					"	where  hd.PK_SEQ = '" + this.id + "'  " +
					") " +
					"as a inner join HOADON hd on hd .PK_SEQ= a.HDID " +
					"where  hd.PK_SEQ = '" + this.id + "' ";*/
			
			//HÓA ĐƠN MỚI
			/*query = "update hd set hd.tongtienbVAT = TGT.tongtienBVAT, hd.chietkhau = TGT.tongCK,  " +
					"			  hd.vat = TGT.thueVAT, hd.tongtienAVAT = TGT.tongtienAVAT " +
					"from HOADON hd " +
					"inner join " +
					"( " +
					"	select hangban.hoadon_fk, hangban.thanhtien as tongtienBVAT, isnull(chietkhau.thanhtien, 0) as tongCK, " +
					"				hangban.thueVAT - isnull(chietkhau.thueVAT, 0) as thueVAT, " +
					"				hangban.thanhtien + hangban.thueVAT - ( isnull(chietkhau.thanhtien, 0) + isnull(chietkhau.thueVAT, 0) ) as tongtienAVAT " +
					"	from HOADON hd inner join " +
					"	( " +
					"		select hoadon_fk,   " +
					"				sum( cast( ( SOLUONG * DONGIA ) as numeric(18, 0) ) ) as thanhtien,   " +
					"				sum( cast( (SOLUONG * DONGIA * thuevat / 100) as numeric(18, 0) ) )  as thueVAT " +
					"		from HOADON_SP_CHITIET  " +
					"		where hoadon_fk = '" + this.id + "'  " +
					"		group by  hoadon_fk " +
					"	) " +
					"	hangban on hd.pk_seq = hangban.hoadon_fk left join " +
					"	( " +
					"		select hoadon_fk,  " +
					"				SUM(  cast( chietkhau  as numeric(18, 0) )  ) as thanhtien,   " +
					"				SUM(  cast ( ( chietkhau * thueVAT / 100 ) as numeric(18, 0) )  )  as thueVAT " +
					"		from HOADON_CHIETKHAU   " +
					"		where hoadon_fk = '" + this.id + "'  " +
					"		group by hoadon_fk  " +
					"	) " +
					"	chietkhau on hd.pk_seq = chietkhau.hoadon_fk " +
					") " +
					"TGT on hd.pk_seq = TGT.hoadon_fk ";*/
			
			//HÓA ĐƠN MỚI -> theo cách làm tròn của FAST: VAT = ROUND( soluong * dongia ) * VAT / 100
			query = "update hd set hd.tongtienbVAT = TGT.tongtienBVAT, hd.chietkhau = TGT.tongCK,  " +
					"			  hd.vat = TGT.thueVAT, hd.tongtienAVAT = TGT.tongtienAVAT " +
					"from HOADON hd " +
					"inner join " +
					"( " +
					"	select hangban.hoadon_fk, hangban.thanhtien as tongtienBVAT, isnull(chietkhau.thanhtien, 0) as tongCK, " +
					"				hangban.thueVAT - isnull(chietkhau.thueVAT, 0) as thueVAT, " +
					"				hangban.thanhtien + hangban.thueVAT - ( isnull(chietkhau.thanhtien, 0) + isnull(chietkhau.thueVAT, 0) ) as tongtienAVAT " +
					"	from HOADON hd inner join " +
					"	( " +
					"		select hoadon_fk,   " +
					"			sum( round( (SOLUONG * DONGIA ), 0 ) ) as thanhtien,    " +
					"			sum( round( ( round( (SOLUONG * DONGIA ), 0 ) * thueVAT / 100 ), 0 ) ) as thueVAT	 " +
					"		from HOADON_SP_CHITIET  " +
					"		where hoadon_fk = '" + this.id + "'  " +
					"		group by  hoadon_fk " +
					"	) " +
					"	hangban on hd.pk_seq = hangban.hoadon_fk left join " +
					"	( " +
					/*"		select hoadon_fk,  " +
					"				round ( SUM( chietkhau ), 0 ) as thanhtien, " +
					"				round ( SUM( round(chietkhau, 0) * thueVAT / 100 ), 0)  as thueVAT  " +
					"		from HOADON_CHIETKHAU   " +
					"		where hoadon_fk = '" + this.id + "'  " +
					"		group by hoadon_fk  " +*/
					"		select hoadon_fk,   " +
					"			SUM (  round( chietkhau, 0 ) ) as thanhtien,  " +
					"			sum ( round( chietkhau * thueVAT / 100 , 0 ) )  as thueVAT   " +
					"  		from HOADON_CHIETKHAU    " +
					"		where hoadon_fk = '" + this.id + "' and HIENTHI = '1'  " +
					"		group by hoadon_fk  	" +
					"	) " +
					"	chietkhau on hd.pk_seq = chietkhau.hoadon_fk " +
					") " +
					"TGT on hd.pk_seq = TGT.hoadon_fk ";
			
			if(!db.update(query))
			{
				msg = "Khong the cap nhat HOADON_CHIETKHAU: " + query;
				db.getConnection().rollback();
				return false;
			}

			//CAP NHAT LAI DA XUAT HOA DON
			query = "update DONHANG set DAXUATHOADON = '1' where pk_seq in ( select DDH_FK from HOADON_DDH where HOADON_FK = '" + this.id + "' ) ";
			if(!db.update(query))
			{
				msg = "Khong the cap nhat DONHANG: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			//XUAT PHIEU KHAU TRU CHIET KHAU THANG TU DONG
			/* KIỂM TRA CHIẾT KHẤU HÓA ĐƠN */
			
			query =	
					" SELECT hd.npp_fk, round( isnull(hd_ck.TienCK,0), 0) TienCK, " +
					"	ISNULL( ( select denghitraCK from DONHANG where pk_seq in ( select ddh_fk from HOADON_DDH where hoadon_fk = '" + id + "' ) ), 0) as denghitraCK \n"+
					" FROM HOADON hd inner join \n"+
					" ( " +
					"		  SELECT hd_ck.hoadon_fk, sum( round( round(hd_ck.chietkhau, 0) * (1 + hd_ck.thueVAT / 100.0 ), 0) ) as TienCK \n"+
					"	  	  FROM	 HOADON_CHIETKHAU hd_ck \n"+
					"	  	  WHERE  hd_ck.diengiai = 'CT5' and hd_ck.chietkhau > 0 \n"+
					"	  	  GROUP BY hd_ck.hoadon_fk \n"+
					" ) " +
					" hd_ck on hd_ck.HOADON_FK = hd.PK_SEQ \n"+					
					" WHERE hd.LOAIHOADON=0 and hd.PK_SEQ = '" + id + "'";
			
			 
			 ResultSet rss = db.get(query);
			 String TienCK = "0";
			 String npp = ""; 
			 String denghitraCK = "";
			 {
				 while(rss.next())
				 {
					 TienCK = rss.getString("TienCK");
					 npp = rss.getString("npp_fk");
					 denghitraCK = rss.getString("denghitraCK");
				 }
				 rss.close();
			 }
			
			 /*Nếu tiền chiết khấu >0 --> CHECK KY LAI SAO BI TAO 2 LAN ???? */ 
			 if(Double.parseDouble(TienCK) > 0 && denghitraCK.equals("1")  )
			 {
				 query = " select count(*) as sodong from CANTRUCONGNO_HOADON " +
				 		 " where HOADON_FK = '" + id + "' and CANTRUCONGNO_FK in ( select pk_seq from CANTRUCONGNO where trangthai != 2 and npp_fk = '" + npp + "' ) ";
				 rsCHECK = db.get(query);
				 int exits = 0;
				 if(rsCHECK.next())
				 {
					 exits = rsCHECK.getInt("sodong");
					 rsCHECK.close();
				 }
				 if(exits > 0)
				 {
					 db.getConnection().rollback();
					 this.msg = "Hóa đơn đã tồn tại trong phiếu cấn trừ";
					 return false; 
				 }
				 else
				 {
					
					 query=
								"	 select b.KHACHHANG_FK,SUBSTRING(NGAYCHUNGTU,1,7) as NgayThang,COUNT(*) as SoDong  "+
								"	 from CANTRUCONGNO a inner join CANTRUCONGNO_HOADON b on b.CANTRUCONGNO_FK=a.PK_SEQ    "+
								"	 where  b.hoadon_fk!='"+id+"'  and a.TRANGTHAI=1  "+
								"	 	and b.KHACHHANG_FK=(select KHACHHANG_FK from HOADON where PK_SEQ='"+id+"')  "+
								"	 	and SUBSTRING(a.NGAYCHUNGTU,1,7) =( select SUBSTRING(NGAYXUATHD,1,7) as NgayThang  from HOADON where PK_SEQ='"+id+"')  "+
								"   group by b.KHACHHANG_FK,SUBSTRING(NGAYCHUNGTU,1,7) ";
					 
					 		System.out.println("___KiemTraXemCoPhieuCanTru__"+query);
					 		
							 rsCHECK = db.get(query);
							  exits = 0;
							 if(rsCHECK.next())
							 {
								 exits = rsCHECK.getInt("sodong");
								 rsCHECK.close();
							 }
							 
						 if(exits<=0)
						 {
							 query = "INSERT CANTRUCONGNO(NGAYCHUNGTU, TRANGTHAI, NGAYTAO, NGAYSUA, NGUOITAO, NGUOISUA, NPP_FK, GHICHU) " +
									 "values('"+ngayxuatHD+"', 1, '" + getDateTime() + "', '" + getDateTime() + "', '" + this.userId + "', '" + this.userId + "','" + npp + "',N'Phiếu khấu trừ chiết khấu tháng tự động HD " + id + "')";
							 System.out.print("INSERT CANTRUCONGNO________"+ query);
							 
						 	 if(!db.update(query))
							 {
								db.getConnection().rollback();
								this.msg = "Khong the tao moi 'CANTRUCONGNO' , " + query;
								return false; 
							 }
						 	
							 query = "insert CANTRUCONGNO_HOADON(CANTRUCONGNO_FK, HOADON_FK, KHACHHANG_FK, SOTIENCANTRU)" +
					         		 " select scope_identity(), '" + id + "', khachhang_fk, '" + TienCK + "' from HOADON where pk_seq = '" + id + "'";
							 System.out.print("INSERT CANTRUCONGNO_HOADON _____ :"+ query);
							 if(!db.update(query))
							 {
								geso.dms.center.util.Utility.rollback_throw_exception(db);
								this.msg = "Khong the insert bang CANTRUCONGNO_HOADON: " + query;
								return false; 
							 }
						 }
				}
			}
				Utility util = new Utility();
				msg= util.Check_Huy_NghiepVu_KhoaSo("HoaDon", id, "NgayXuatHD");
				if(msg.length()>0)
				{
					db.getConnection().rollback();
					return false; 
				}
			 
				msg= util.Check_Kho_Tong_VS_KhoCT(util.getIdNhapp(userId));
				if(msg.length()>0)
				{
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
		finally
		{
			db.shutDown();
		}
		return true;
	}

	public String getNgayghinhanCN() 
	{
		return this.ngayghinhanCN;
	}

	public void setNgayghinhanCN(String ngayghinhanCN) 
	{
		this.ngayghinhanCN = ngayghinhanCN;
	}

	public String getKyhieuhoadon() 
	{
		return this.kyhieuhoadon;
	}

	public void setKyhieuhoadon(String kyhieuhoadon) 
	{
		this.kyhieuhoadon = kyhieuhoadon;
		
	}

	public String getSohoadon() 
	{		
		return this.sohoadon;
	}

	public void setSohoadon(String sohoadon) 
	{
		this.sohoadon = sohoadon;
	}

	public String[] getSpDongia() 
	{
		return this.spDongia;
	}

	public void setSpDongia(String[] spDongia) 
	{
		this.spDongia = spDongia;
		
	}

	public String[] getSpChietkhau()
	{
		return this.spChietkhau;
	}

	public void setSpChietkhau(String[] spChietkhau) 
	{
		this.spChietkhau = spChietkhau;
		
	}

   public String[] getDhck_diengiai() {
		
		return this.dhCk_diengiai;
	}

	
	public void setDhck_Diengiai(String[] obj) {
		
		this.dhCk_diengiai = obj;
	}

	
	public String[] getDhck_giatri() {
		
		return this.dhCk_giatri;
	}

	
	public void setDhck_giatri(String[] obj) {
		
		this.dhCk_giatri = obj;
	}

	
	public String[] getDhck_loai() {
		
		return this.dhCk_loai;
	}

	
	public void setDhck_loai(String[] obj) {
		
		this.dhCk_loai = obj;
	}

	public String getLoaidonhang() {
		
		return this.loaidonhang;
	}

	
	public void setLoaidonhang(String loaidonhang) {
		
		this.loaidonhang = loaidonhang;
	}

	
	public String getNguoimua() 
	{
		return this.nguoimua;
	}

	
	public void setNguoimua(String nguoimua) {
		this.nguoimua = nguoimua;
		
	}

	
	public String getKhId() {
		return this.khId;
	}

	
	public void setKhId(String khId) {
		this.khId =khId;
		
	}

	
	public ResultSet getKhRs() {
		return this.khRs;
	}

	
	public void setKhRs(ResultSet khRs) {
		this.khRs = khRs;
		
	}

	
	public String getChietkhau() {
		
		return this.chietkhau;
	}

	
	public void setChietkhau(String chietkhau) {
		this.chietkhau= chietkhau;
		
	}
   public String[] getTichLuy_Scheme() {
		
		return this.tichluy_scheme;
	}

	
	public void setTichLuy_Scheme(String[] tichluy_scheme) {
		
		this.tichluy_scheme = tichluy_scheme;
	}

	
	public String[] getTichLuy_Tongtien() {
		
		return this.tichluy_tongtien;
	}

	
	public void setTichLuy_Tongtien(String[] tichluy_tongtien) {
		
		this.tichluy_tongtien = tichluy_tongtien;
	}


	public String[] getTichLuy_VAT() {
		return this.tichluy_vat;
	}


	public void setTichLuy_TVAT(String[] tichluy_vat) {
		this.tichluy_vat = tichluy_vat;
		
	}	
	
	public void initTichLuy(String ddh) 
	{
		String query = "";
		
		//CHUA CHOT, TAO MOI THI LOAD TU DON HANG
		if(this.trangthai.equals("0") || this.trangthai.equals("1") || this.trangthai.trim().length() <= 0 )
		{
			query =	"select N'CN5' as diengiai, sum(chietkhau) as chietkhau, thueVAT, 1 as STT, 0 as tichluyQUY from DONHANG_SANPHAM  " +
					"	where donhang_fk in (" + ddh+ ") and thueVAT = '5' and chietkhau > 0 group by thueVAT " +
					"union  " +
					"	select N'CN10' as diengiai, sum(chietkhau) as chietkhau, thueVAT, 2 as STT, 0 as tichluyQUY  " +
					"	from DONHANG_SANPHAM  " +
					"	where donhang_fk in (" + ddh+ ") and thueVAT = '10' and chietkhau > 0 group by thueVAT " +
					"union " +
					"	select a.diengiai, a.thanhtoan / ( 1 + ptTHUE / 100 ) as chietkhau, a.ptTHUE as thueVAT, 3 as STT, tichluyQUY  " +
					"	from DUYETTRAKHUYENMAI_DONHANG a inner join TIEUCHITHUONGTL b on a.duyetkm_fk = b.PK_SEQ   " +
					"	where a.donhang_fk in (" + ddh+ ") and hienthi = '1'  "+
					"union " +
					"	select a.maloai as diengiai, a.chietkhau, a.ptVAT as thueVAT, 4 as STT, 0 as tichluyQUY  " +
					"	from DONHANG_CHIETKHAUBOSUNG a    " +
					"	where a.donhang_fk in (" + ddh+ ")  order by STT, tichluyQUY  ";
		}
		else
		{
			query = " select diengiai, chietkhau, thueVAT, STT, tichluyQUY " +
					" from HOADON_CHIETKHAU where hoadon_fk = '" + this.id + "' and HIENTHI = '1' order by STT, tichluyQUY ";
		}
				
		System.out.println("---INIT TICH LUY: " + query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			String schemeMa = "";
			String schemeVAT = "";
			String schemeTt = "";
			
			try 
			{
				NumberFormat format = new DecimalFormat("#,###,###.###");
				while(rs.next())
				{
					schemeMa += rs.getString("diengiai") + "__";
					schemeVAT += rs.getString("thueVAT") + "__";
					schemeTt += format.format( rs.getDouble("chietkhau")) + "__";
				}
				rs.close();
				
				if(schemeMa.trim().length() > 0)
				{
					schemeMa = schemeMa.substring(0, schemeMa.length() - 2);
					this.tichluy_scheme = schemeMa.split("__");
					
					schemeVAT = schemeVAT.substring(0, schemeVAT.length() - 2);
					this.tichluy_vat = schemeVAT.split("__");
					
					schemeTt = schemeTt.substring(0, schemeTt.length() - 2);
					this.tichluy_tongtien = schemeTt.split("__");
				}
			} 
			catch (Exception e) 
			{
				System.out.println("__EXCEPTION: " + e.getMessage());
				e.printStackTrace();
			}
		}	
	}
	
	
	public void getTongTien(String ddhId) 
	{
		String query = "";
		
		//CHUA CHOT, TAO MOI THI LOAD TU DON HANG
		if(this.trangthai.equals("0") || this.trangthai.equals("1") || this.trangthai.trim().length() <= 0 )
		{
			query = "select hangban.donhang_fk, hangban.thanhtien as tongtienBVAT, isnull(chietkhau.thanhtien, 0) as chietkhau,  " +
					"			hangban.thueVAT - isnull(chietkhau.thueVAT, 0) as vat, " +
					"			hangban.thanhtien + hangban.thueVAT - ( isnull(chietkhau.thanhtien, 0) + isnull(chietkhau.thueVAT, 0) ) as tongtienavat " +
					"from DONHANG hd inner join " +
					"( " +
					"	 select '" + ddhId + "' as donhang_fk,    " +
					"				sum( cast( ( SOLUONG * DONGIA ) as numeric(18, 0) ) ) as thanhtien,   " +
					"				sum( cast( (SOLUONG * DONGIA * thuevat / 100) as numeric(18, 0) ) )  as thueVAT " +
					"	 from " +
					"	 (	 " +
					"		 select b.PK_SEQ as SPID, b.MA, b.TEN, DV.donvi, a.giamua as dongia, '' as scheme, a.thuevat,  " +
					"	 			sum( a.soluong) as soluong, sum(isnull(a.chietkhau, 0))  as chietkhau  " +
					"		 from Donhang_SANPHAM a inner Join SanPham b on a.SANPHAM_FK = b.PK_SEQ     " +
					"	 		  INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ = b.DVDL_FK        " +
					"		 where a.Donhang_FK = '" + ddhId + "' " +
					"		 group by  b.PK_SEQ, b.MA, b.TEN, DV.donvi, a.giamua, a.thuevat " +
					"	 ) " +
					"	 DH " +
					") " +
					"hangban on hd.pk_seq = hangban.donhang_fk left join " +
					"( " +
					"	select '" + ddhId + "' as donhang_fk,  " +
					"				SUM(  cast( chietkhau  as numeric(18, 0) )  ) as thanhtien,   " +
					"				SUM(  cast ( ( chietkhau * thueVAT / 100 ) as numeric(18, 0) )  )  as thueVAT " +
					"	from  " +
					"	( " +
					"			select N'CN5' as diengiai, sum(chietkhau) as chietkhau, thueVAT, 1 as STT, 0 as tichluyQUY  " +
					"			from DONHANG_SANPHAM   " +
					"			where donhang_fk = '" + ddhId + "' and thueVAT = '5' group by thueVAT  " +
					"		union   " +
					"			select N'CN10' as diengiai, sum(chietkhau) as chietkhau, thueVAT, 2 as STT, 0 as tichluyQUY   " +
					"			from DONHANG_SANPHAM   " +
					"			where donhang_fk = '" + ddhId + "' and thueVAT = '10' group by thueVAT  " +
					"		union  " +
					"			select a.diengiai, a.thanhtoan / ( 1 + ptTHUE / 100 ) as chietkhau, a.ptTHUE as thueVAT, 3 as STT, tichluyQUY   " +
					"			from DUYETTRAKHUYENMAI_DONHANG a inner join TIEUCHITHUONGTL b on a.duyetkm_fk = b.PK_SEQ    " +
					"			where a.donhang_fk = '" + ddhId + "' and HIENTHI = '1'  " +
					"		union  " +
					"			select a.maloai as diengiai, a.chietkhau, a.ptVAT as thueVAT, 4 as STT, 0 as tichluyQUY   " +
					"			from DONHANG_CHIETKHAUBOSUNG a     " +
					"			where a.donhang_fk = '" + ddhId + "'    " +
					"	) " +
					"	CK " +
					") " +
					"chietkhau on hd.pk_seq = chietkhau.donhang_fk ";
		}
		else
		{
			query = " select tongtienbvat, chietkhau, vat, tongtienavat from HOADON where pk_seq = '" + this.id + "'  ";
		}
				
		System.out.println("---INIT TONG TIEN: " + query);
		//INIT 4 COT TONG TIEN
		
		ResultSet rsTien = db.get(query);
		
		if(rsTien != null)
		{
			try 
			{
				NumberFormat formatter = new DecimalFormat("#,###,###");
				if(rsTien.next())
				{
					this.bvat = formatter.format(rsTien.getDouble("tongtienbvat"));
					this.totalCHIETKHAU = formatter.format(rsTien.getDouble("chietkhau"));
					this.thueVAT = formatter.format(rsTien.getDouble("vat"));
					this.avat = formatter.format(rsTien.getDouble("tongtienavat"));
				}
				rsTien.close();
			} 
			catch (Exception e) { e.printStackTrace(); }	
		}
	}

	
	public String[] getSpVat() {
		
		return this.spVat;
	}

	
	public void setSpVat(String[] spVat) {
		this.spVat= spVat;
		
	}

	
	public String[] getSpThue() {
		
		return this.spThue;
	}

	
	public void setSpThue(String[] spThue) {
		this.spThue = spThue;
		
	}
	
	public void loadContents() 
	{
		System.out.println("da vao loadcontents");
		this.getNppInfo();
		Utility util = new Utility();
		String query = "  select PK_SEQ, MAFAST + '-' + TEN as TEN" +
				       "  from KHACHHANG" +
				       "  where TRANGTHAI = '1' and NPP_FK= "+ this.nppId +" and KBH_FK=100025" +
				       "         and pk_seq in (select KHACHHANG_FK from DONHANG where trangthai in  (1,4) ) ";
		this.khRs = db.get(query);
		
		this.createChietkhau();
		if(this.khId.trim().length() > 0 )
		{			
			  query = " select PK_SEQ , NgayNhap as NgayDonHang  " +
					  " from DONHANG " +
					  " where  NPP_FK= "+ this.nppId +" AND  KHACHHANG_FK = '" + this.khId + "' and kho_fk in "+util.quyen_kho(this.userId) +
					  "       and pk_seq in (select donhang_fk " +
					  "                      from PHIEUXUATKHO_DONHANG a inner join PHIEUXUATKHO b on a.pxk_fk= b.pk_seq " +
					  "                      where b.trangthai=1 and b.NPP_FK= "+ this.nppId +" )  " +
					  " and pk_seq not in ( select a.ddh_fk from HoaDon_ddh a inner join hoadon b on a.hoadon_fk=b.pk_seq where b.trangthai not in ( 3, 5 ) and b.loaihoadon = 0 ) " ;
				
			System.out.println("Lay don hang: " + query);		
			this.ddhRs = db.get(query);
			
			if(this.khId.trim().length() > 0 )
			{			
				// LAY TEN NGUOI MUA TRONG DLN
				query =" select isnull(chucuahieu,'') as nguoimua from KHACHHANG where pk_seq= '"+ this.khId +"' ";
				ResultSet rsLayTT = db.get(query);
				if(rsLayTT != null)
				{
					try
					{
						while(rsLayTT.next())
						{
							this.nguoimua = rsLayTT.getString("nguoimua");
						}
						rsLayTT.close();
					}
					catch (Exception e) { e.printStackTrace(); }
				}	
			}
		}
		
		String chuoi = this.ddhId;
		System.out.println("don dat hang la "+chuoi);
		System.out.println("--Don hang IDS: "+chuoi);
		if(chuoi.trim().length() > 0)
		{			
			// INIT SP	
		    query = " select b.PK_SEQ as SPID, b.MA, b.TEN, DV.donvi, a.giamua as dongia , ' ' as scheme, a.thuevat, " +
		    		" 		sum( a.soluong) as soluong, sum(isnull(a.chietkhau, 0))  as chietkhau " +
					" from Donhang_SANPHAM a inner Join SanPham b on a.SANPHAM_FK = b.PK_SEQ    " +
					" 	  INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ = b.DVDL_FK       " +
					" where a.Donhang_FK in ( " + chuoi + " ) " +
					" group by  b.PK_SEQ, b.MA, b.TEN, DV.donvi, a.giamua, a.thuevat ";

		    System.out.println("Lấy sản phẩm: "+query);
		    ResultSet rsLaySP = db.get(query);
		    try 
		    {
		    	String spID = "";
				String spMA = "";
				String spTEN = "";
				String spDONVI = "";
				String spSOLUONG = "";
				String spGIANHAP = "";
				String spCHIETKHAU = "";
				String spSCHEME = "";
				String spVAT = "";
				String spTienThue = "";
				double tienthue= 0;
		    	
			    if(rsLaySP!= null)
			    {				    	
					while(rsLaySP.next())
					{
						spID += rsLaySP.getString("SPID") + "__";
						spMA += rsLaySP.getString("MA") + "__";
						spTEN += rsLaySP.getString("TEN") + "__";
						spDONVI += rsLaySP.getString("DONVI") + "__";
						spSOLUONG += (rsLaySP.getDouble("SOLUONG")) + "__";
						spGIANHAP += (rsLaySP.getDouble("DONGIA")) + "__";
						spCHIETKHAU += (rsLaySP.getDouble("chietkhau")) + "__";
						spSCHEME += rsLaySP.getString("scheme") + "__";
						spVAT += rsLaySP.getString("thuevat") + "__";
						tienthue = (rsLaySP.getDouble("SOLUONG") * rsLaySP.getDouble("DONGIA") - rsLaySP.getDouble("chietkhau")) * rsLaySP.getDouble("thuevat")/100;
						spTienThue += tienthue + "__";
					}
					rsLaySP.close();
					
					if(spMA.trim().length() > 0)
					{
						spID = spID.substring(0, spID.length() - 2);
						this.spId = spID.split("__");
						
						spMA = spMA.substring(0, spMA.length() - 2);
						this.spMa = spMA.split("__");
						
						spTEN = spTEN.substring(0, spTEN.length() - 2);
						this.spTen = spTEN.split("__");
						
						spDONVI = spDONVI.substring(0, spDONVI.length() - 2);
						this.spDonvi = spDONVI.split("__");
						
						spSOLUONG = spSOLUONG.substring(0, spSOLUONG.length() - 2);
						this.spSoluong = spSOLUONG.split("__");
						
						spGIANHAP = spGIANHAP.substring(0, spGIANHAP.length() - 2);
						this.spDongia = spGIANHAP.split("__");
						
						spCHIETKHAU = spCHIETKHAU.substring(0, spCHIETKHAU.length() - 2);
						this.spChietkhau = spCHIETKHAU.split("__");
						
						spSCHEME = spSCHEME.substring(0, spSCHEME.length() - 2);
						this.spSCheme = spSCHEME.split("__");
						
						spVAT = spVAT.substring(0, spVAT.length() - 2);
						this.spVat = spVAT.split("__");
						
						spTienThue = spTienThue.substring(0, spTienThue.length() - 2);
						this.spThue = spTienThue.split("__");
	
					}
					
					//KHOI TAO SOLO
					query = "select sanpham_fk, solo, ngayhethan, sum(soluong) as soluong  " +
							"from PHIEUXUATKHO_SANPHAM_CHITIET where PXK_FK in ( select PXK_FK from PHIEUXUATKHO_DONHANG a inner join PHIEUXUATKHO b on a.pxk_fk = b.pk_seq where b.trangthai = '1' and donhang_fk in ( " + this.ddhId + " ) ) " +
							"group by sanpham_fk, solo, ngayhethan ";
					System.out.println("---LO DA XUAT: " + query);
					ResultSet rsSOLO = db.get(query);
					this.sanpham_soluong = new Hashtable<String, String>();
					if(rsSOLO != null)
					{
						while(rsSOLO.next())
						{
							String key = rsSOLO.getString("sanpham_fk") + "__" + rsSOLO.getString("solo") + "__" + rsSOLO.getString("ngayhethan");
							this.sanpham_soluong.put(key, rsSOLO.getString("soluong"));
						}
						rsSOLO.close();
					}
					
			    }
			}
		    catch (Exception e) 
			{
				e.printStackTrace();
			}
			
			this.initTichLuy(chuoi);
			this.getTongTien(chuoi);
			
		}	
	}
	
	public boolean duyetDIEUCHINH() 
	{
		if(spMa == null)
		{
			this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm";
			return false;
		}
		
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "update HOADON set LANDIEUCHINH = LANDIEUCHINH + 1 where pk_seq = '" + this.id + "' ";
			if(!db.update(query))
			{
				msg = "Không thể điều chỉnh HOADON " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "insert HOADON_LOG_DIEUCHINH(hoadon_fk, landieuchinh, lydo) " +
					"select pk_seq, max(landieuchinh), N'Điều chỉnh hóa đơn' from HOADON where pk_seq = '" + this.id + "' group by pk_seq";
			if(!db.update(query))
			{
				msg = "Không thể điều chỉnh HOADON " + query;
				db.getConnection().rollback();
				return false;
			}
			
			//COPY HOA DON GOC TRUOC KHI DIEU CHINH
			query = "insert HOADON_LOG_SP(log_fk, hoadon_fk, SANPHAM_FK, SOLUONG_GOC, SOLUONG_MOI, DONGIA, TIENBVAT, VAT, TIENAVAT, DONVITINH, SCHEME, CHIETKHAU, THANHTIEN) " +
					"select ident_current('HOADON_LOG_DIEUCHINH'), hoadon_fk, SANPHAM_FK, SOLUONG, SOLUONG, DONGIA, TIENBVAT, VAT, TIENAVAT, DONVITINH, SCHEME, CHIETKHAU, THANHTIEN   " +
					"from HOADON_SP where hoadon_fk = '" + this.id + "' ";
			if(!db.update(query))
			{
				msg = "Không thể điều chỉnh HOADON " + query;
				db.getConnection().rollback();
				return false;
			}
			
			for(int i = 0; i < spId.length; i++ )
			{
				if(spSoluong[i].trim().length() > 0 && spDongia[i].trim().length() > 0)
				{
					query = "update HOADON_SP set SOLUONG = '', DONGIA = '' ";
				}
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch (Exception e) 
		{
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			this.msg = "Exception: " + e.getMessage();
			return false;
		}
		
		return true;
	}

	
	public String getTongtienBVAT() {
		
		return this.bvat;
	}

	
	public void setTongtienBVAT(String bvat) {
		
		this.bvat = bvat;
	}

	
	public String getTongCK() {
		
		return this.totalCHIETKHAU;
	}

	
	public void setTongCK(String tongCK) {
		
		this.totalCHIETKHAU = tongCK;
	}

	
	public String getTongVAT() {
		
		return this.thueVAT;
	}

	
	public void setTongVAT(String vat) {
		
		this.thueVAT = vat;
	}

	
	public String getTongtienAVAT() {
		
		return this.avat;
	}

	
	public void setTongtienAVAT(String avat) {
		
		this.avat = avat;
	}

	
	public void init_Display() 
	{
		this.getNppInfo();
		String query =  " select dondathang_fk, ngayxuatHD, ISNULL(ghichu, '') as ghichu, kyhieu, sohoadon, hinhthuctt ,khachhang_fk, npp_fk, trangthai, isnull(nguoimua,'') as nguoimua, isnull(innguoimua,1) as innguoimua,  " +
				        "        isnull(tongtienbvat, 0) as tongtienbvat, isnull(chietkhau, 0) as chietkhau, isnull(ngayghinhanCN, '"+ getDateTime() +"') as ngayghinhanCN , " +
				        "        isnull(vat, 0) as vat, isnull(tongtienavat, 0) as tongtienavat, isnull(tongtienkm, 0) as tongtienkm,isnull(mavv,'') as mavv   " +
						" from HOADON" +
						" where pk_seq = '" + this.id + "'";
		System.out.println("____INIT HOADON: " + query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				NumberFormat formatter = new DecimalFormat("#,###,###");
				if(rs.next())
				{
					this.ngayxuatHD = rs.getString("ngayxuatHD");
					this.ghichu = rs.getString("ghichu");
					this.hinhthuctt = rs.getString("hinhthuctt");
					this.kyhieuhoadon = rs.getString("kyhieu");
					this.ngayghinhanCN = rs.getString("ngayghinhanCN");
					this.sohoadon = rs.getString("sohoadon");
					this.khId = rs.getString("khachhang_fk");
					this.nppId = rs.getString("npp_fk");
					this.trangthai = rs.getString("trangthai");
					this.innguoimua = rs.getString("innguoimua");
					this.nguoimua = rs.getString("nguoimua");
					this.mavuviec=rs.getString("mavv");
					
					this.bvat = formatter.format(rs.getDouble("tongtienbvat"));
					this.totalCHIETKHAU = formatter.format(rs.getDouble("chietkhau"));
					this.thueVAT = formatter.format(rs.getDouble("vat"));
					this.avat = formatter.format(rs.getDouble("tongtienavat"));
						
				}
				rs.close();
				
				//INIT DDH
				query = "SELECT DDH_FK FROM HOADON_DDH WHERE HOADON_FK = " + this.id;
				rs = this.db.get(query);
				
				String ddh = "";
				if(rs!=null)
				{
					while(rs.next())
					{
						ddh += rs.getString("DDH_FK") + ",";
					}
					rs.close();
					
					if(ddh.trim().length() > 0)
						this.ddhId = ddh.substring(0, ddh.length() - 1);
				}
				
				if( !this.trangthai.equals("1")  )
				{
					query = " select B.PK_SEQ ,B.NGAYNHAP AS NgayDonHang  " +
							" from HOADON_DDH A INNER JOIN DONHANG B ON A.DDH_FK = B.PK_SEQ " +
							" where A.HOADON_FK = '"+ this.id +"' AND A.HOADON_FK IN (SELECT PK_SEQ FROM HOADON WHERE LOAIHOADON= 0) ";
				
				}
				else
				{				
					query = "select PK_SEQ ,NGAYNHAP AS NgayDonHang  " +
							"from DONHANG " +
							"where  trangthai not in (0,2 ) and   isnull(donhangkhac,0) = 0 and NPP_FK= "+ this.nppId +" and  KHACHHANG_FK = '" + this.khId + "' " +
							"    and ( import= '1' or pk_seq in (select donhang_fk " +
							"                      from PHIEUXUATKHO_DONHANG a inner join PHIEUXUATKHO b on a.pxk_fk= b.pk_seq " +
							"					   where b.trangthai=1 and b.NPP_FK= "+ this.nppId +"))  " +
							" and pk_seq not in (Select a.ddh_fk from HoaDon_ddh a inner join hoadon b on a.hoadon_fk=b.pk_seq where b.trangthai in (2,4) and b.loaihoadon = 0 and b.pk_seq!= '"+ this.id +"')  " ;
				}
				
				System.out.println("Lấy đơn hàng "+query);		
				this.ddhRs = db.get(query);
					
			} 
			catch (Exception e) 
			{
				System.out.println("---LOI INIT: " + e.getMessage());
			}
		}

		this.createRs();
		
	}


	public void getTongTienPDF() 
	{
		String query = "";
		query = " select tongtienbvat, chietkhau, vat, tongtienavat from HOADON where pk_seq = '" + this.id + "'  ";
				
		System.out.println("---INIT TONG TIEN: " + query);
		//INIT 4 COT TONG TIEN
		
		ResultSet rsTien = db.get(query);
		
		if(rsTien != null)
		{
			try 
			{
				NumberFormat formatter = new DecimalFormat("#,###,###");
				if(rsTien.next())
				{
					this.bvat = formatter.format(rsTien.getDouble("tongtienbvat"));
					this.totalCHIETKHAU = formatter.format(rsTien.getDouble("chietkhau"));
					this.thueVAT = formatter.format(rsTien.getDouble("vat"));
					this.avat = formatter.format(rsTien.getDouble("tongtienavat"));
				}
				rsTien.close();
			} 
			catch (Exception e) { e.printStackTrace(); }	
		}
	}
	
	private int CompareDATE(String _date1, String _date2)
	{
		try
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    	//Date date1 = sdf.parse("2014-10-01");
	    	//Date date2 = sdf.parse("2014-10-01");
	    	
	    	Date date1 = sdf.parse(_date1);
	    	Date date2 = sdf.parse(_date2);
	
	    	//System.out.println(sdf.format(date1));
	    	//System.out.println(sdf.format(date2));
	
	    	return date1.compareTo(date2);
		}
		catch (Exception e) {
			return 0;
		}

	}

	public Hashtable<String, String> getSanpham_Soluong() {
		
		return this.sanpham_soluong;
	}

	
	public void setSanpham_Soluong(Hashtable<String, String> sp_soluong) {
		
		this.sanpham_soluong = sp_soluong;
	}
	
	public ResultSet getSoloTheoSp(String spMa, String donvi, String tongluong)
	{
		// Kiểm tra npp nếu là Đối tác thì không dùng Số hóa đơn của hệ thống
		String		query = 
				" select loaiNPP, (  select PXK_FK from PHIEUXUATKHO_DONHANG a inner join PHIEUXUATKHO b on a.pxk_fk = b.pk_seq where b.trangthai = '1' and donhang_fk in ( " + this.ddhId + " ) ) as pxkID " +
				",  ISNULL( ( select DonHangKhac from DONHANG aa  where pk_seq="+this.ddhId+" ), 0) as DonHangKhac " +
				" from NHAPHANPHOI " +
				" where pk_seq = '" + nppId + "' ";
		System.out.println("____Init___"+query);
		ResultSet rs = db.get(query);
		String loainpp= "";
		String pxkID = "";
		String donhangkhac = "";
		{
			try
      {
	      while(rs.next())
	      {
	      	loainpp = rs.getString("loaiNPP");
	      	pxkID = rs.getString("pxkID");
	      	donhangkhac=rs.getString("DonHangKhac");
	      }
	      rs.close();
      } catch (SQLException e)
      {
	      // TODO Auto-generated catch block
	      e.printStackTrace();
      }
			
		}
		
		
		tongluong = tongluong.replaceAll(",", "");
		//System.out.println("---TONG LUONG: " + tongluong );
		String kbh_fk = "100025";
		/* query = "select  "+
						" AVAILABLE+isnull(( select hdsp.SOLUONG  from HOADON_SP_CHITIET hdsp where hdsp.hoadon_fk='"+this.id+"' and hdsp.SOLO=ct.SOLO and sp.MA=hdsp.MA and hdsp.NGAYHETHAN=ct.NGAYHETHAN),0)as AVAILABLE"+
						" , NGAYHETHAN, SOLO " +
					   "from NHAPP_KHO_CHITIET ct inner join SANPHAM sp on ct.sanpham_fk = sp.pk_seq " +
					   "where KHO_FK = ( select kho_fk from DONHANG where pk_seq in (" + this.ddhId + ") ) and SANPHAM_FK = '" + spMa + "'   " +
					   "	and AVAILABLE +isnull(( select hdsp.SOLUONG  from HOADON_SP_CHITIET hdsp where hdsp.hoadon_fk='"+this.id+"' and hdsp.SOLO=ct.SOLO and sp.MA=hdsp.MA and hdsp.NGAYHETHAN=ct.NGAYHETHAN),0) >0 and NPP_FK = '" + this.nppId + "' and KBH_FK = '" + kbh_fk + "'  order by NGAYHETHAN asc ";*/
query=		
	"	 select available+ isnull(( select soluong from phieuxuatkho a inner join phieuxuatkho_sanpham_chitiet b on b.pxk_fk=a.pk_seq  "+
	"				where b.sanpham_fk=ct.sanpham_fk and a.npp_fk=ct.npp_fk and b.kbh_fk=ct.kbh_fk  "+
	"					and b.kho_fk=ct.kho_fk and b.solo=ct.solo and b.ngayhethan=ct.ngayhethan and a.pk_seq='"+pxkID+"' ),0)as available ,ngayhethan, solo  "+
	"			from nhapp_kho_chitiet ct inner join sanpham sp on ct.sanpham_fk = sp.pk_seq  "+
	"			where kho_fk = ( select kho_fk from donhang where pk_seq in ("+this.ddhId+") ) and sanpham_fk = '"+spMa+"'  "+
	"				and available +isnull(( select soluong from phieuxuatkho a inner join phieuxuatkho_sanpham_chitiet b on b.pxk_fk=a.pk_seq "+
	"			where b.sanpham_fk=ct.sanpham_fk and a.npp_fk=ct.npp_fk and b.kbh_fk=ct.kbh_fk  "+
	"				and b.kho_fk=ct.kho_fk and b.solo=ct.solo and b.ngayhethan=ct.ngayhethan and a.pk_seq='"+pxkID+"'),0) >0 and npp_fk = '"+nppId+"' and kbh_fk = '"+kbh_fk+"'  "+
	"			order by ngayhethan asc  ";
		 
		System.out.println("----LAY SO LO: " + query+"--"+this.id );
		String query2 = "";
		 rs = db.get(query);
		try 
		{
			double total = 0;
			
			while(rs.next())
			{
				double slg = 0;
				double avai = Math.round(rs.getDouble("AVAILABLE") * 100.0 ) / 100.0;
				
				System.out.println("===================== AVAI: " + avai);
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
		
		return null;
	}
	
	
	public static void main(String[] arg)
	{
		geso.dms.distributor.db.sql.dbutils db = new geso.dms.distributor.db.sql.dbutils();
		
		
	}
	
}
