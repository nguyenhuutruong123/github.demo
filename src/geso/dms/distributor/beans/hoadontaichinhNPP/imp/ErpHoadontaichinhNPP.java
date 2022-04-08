package geso.dms.distributor.beans.hoadontaichinhNPP.imp;

import geso.dms.distributor.beans.hoadontaichinhNPP.IErpHoadontaichinhNPP;
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

public class ErpHoadontaichinhNPP implements IErpHoadontaichinhNPP
{
	String userId;
	String id;
	
	String ngayxuatHD;
	String ngayghinhanCN;
	String ghichu;
	
	String kyhieuhoadon;
	String sohoadon;
	String hinhthuctt;
	String nguoimua;
	String innguoimua;

	String loaidonhang;  //0 đơn đặt hàng, 1 đơn hàng khuyến mại ứng hàng, 3 đơn hàng khuyến mại tích lũy, 4 đơn hàng trưng bày, 5 đơn hàng khác
	
	String loaiXHD;
	
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
	
	String nppDangnhap;
	
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
	String[] spTienthue;
	
	String[] dhCk_diengiai;
	String[] dhCk_giatri;
	String[] dhCk_loai;
	
	String bvat;
	String totalCHIETKHAU;
	String thueVAT;
	String avat;
	String mavuviec="";
	public String getMavuviec() {
		return mavuviec;
	}

	public void setMavuviec(String mavuviec) {
		this.mavuviec = mavuviec;
	}

	dbutils db;
	
	public ErpHoadontaichinhNPP()
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
		this.khId ="";
		this.msg = "";
		this.trangthai = "0";
		this.ddhId = "";
		
		this.bvat = "0";
		this.totalCHIETKHAU = "0";
		this.thueVAT = "0";
		this.avat = "0";
		
		this.nppDangnhap="";
		
		this.loaidonhang = "0";
		this.nguoimua = ""; 
		this.innguoimua = "";
		this.loaiXHD ="";
		
		this.db = new dbutils();
	}
	
	public ErpHoadontaichinhNPP(String id)
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
		this.msg = "";
		this.trangthai = "0";
		this.khId ="";
		this.ddhId = "";
		
		this.bvat = "0";
		this.totalCHIETKHAU = "0";
		this.thueVAT = "0";
		this.avat = "0";
		
		this.nppDangnhap="";

		this.loaidonhang = "0";
		this.nguoimua= "";
		this.innguoimua = "";
		
		this.loaiXHD ="";
		
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
	
	public String getnppDangnhap() 
	{
		return this.nppDangnhap;
	}

	public void setnppDangnhap(String nppDangnhap) 
	{
		this.nppDangnhap = userId;
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
		this.nppDangnhap=util.getIdNhapp(this.userId);
		String	query = "select dungchungkenh from NHAPHANPHOI where PK_SEQ = '" + this.nppDangnhap + "' ";
		ResultSet rs = db.get(query);
		try {
			if(rs.next())
			{
				this.dungchungKENH=rs.getString("dungchungkenh");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public void createRs() 
	{
		this.getNppInfo();
		String query = "select PK_SEQ, MA + '-' + TEN as TEN from KHACHHANG where TRANGTHAI = '1' and npp_fk ='"+ this.nppDangnhap +"' " +
				       " and pk_seq in (select KHACHHANG_FK from ERP_DONDATHANGNPP where trangthai in (2,4) )";
		this.khRs = db.get(query);
		
		 query = "select PK_SEQ, NHAPHANPHOI.MA + '-' + TEN as TEN from NHAPHANPHOI where TRANGTHAI = '1' and loaiNPP = '4' and tructhuoc_fk ='"+ this.nppDangnhap +"' " +
		 		 " and pk_seq in (select NPP_DAT_FK from ERP_DONDATHANGNPP where trangthai in (2,4)) ";
		System.out.println("Nha pp : "+query);
		 this.nppRs = db.get(query);
	 		
		 if(this.ddhId.length()>0)
		 {
			 query="select pk_seq,ten from kenhbanhang where pk_Seq in (select kbh_fk from Erp_DonDatHangNPP where pk_Seq in ("+this.ddhId+"))";
			 this.kbhRs=this.db.get(query);
			 System.out.println("Nha pp : "+query);
		 }
		 
	if(this.id.length() <=0 )
	{
	// TỰ TẠO SỐ HÓA ĐƠN CỦA USER
			
			int kbDLN =0;
			String chuoiHD= "";
			long sohoadontu= 0;
			String sohoadonden= "";
			
			try
			{
				// Lấy mẫu hóa đơn của Khách hàng dùng
				query =" select mauhoadon from KHACHHANG where PK_SEQ ='"+this.khId+"'";
				ResultSet mauHDrs = db.get(query);
				String mau = "";
				if(mauHDrs!=null)
				{
					while(mauHDrs.next())
					{
						mau = mauHDrs.getString("mauhoadon");
					}
					mauHDrs.close();
				}
				
				if(this.nppDangnhap.equals("100002") || (this.nppDangnhap.equals("106210")&& mau.equals("2"))) // CN HÀ NỘI / CN HỒ CHÍ MINH VÀ KHÁCH HÀNG DÙNG MẪU HD TRÊN HO
				{

					//KIEM TRA USER ĐÃ KHAI BAO SO HOA DON TRONG DLN CHUA
					query=  " select count(pk_seq) as dem" +
							" from NHANVIEN" +
							" where pk_seq= '"+ this.userId+"' and  isnull(sohoadontu2,'') != '' and isnull(sohoadonden2,'')  != ''" +
							"       and isnull(kyhieu2, '') != ''  ";
					
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
						this.msg = "Vui lòng khai báo Số hóa đơn trong menu Cập nhật nhân viên cho user này ";
						
					}
					else
					{
						// LAY KY HIEU HOA DON ,SOHDTU TRONG DLN
						query= "  select kyhieu2 as kyhieuhoadon, sohoadontu2 as sohoadontu, sohoadonden2 as sohoadonden " +
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
							}
							rsLayDL.close();
						}
			
						
						// KIEM TRA SOHOADON DA DUOC DUNG CHUA
						   // ETC
							query = " select count(pk_seq) as dem" +
									" from ERP_HOADONNPP " +
									" where RTRIM(LTRIM(kyhieu)) = '"+ this.kyhieuhoadon +"' and cast((case when  sohoadon!='NA' then sohoadon else 0 end) as int) >=  "+ sohoadontu +" " +
									"       and trangthai != '3' and nguoisua= "+ this.userId +" and sohoadon != 'NA' and mauhoadon = 2  ";
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
												        			
							//OTC
							query = " select count(pk_seq) as dem" +
									" from HOADON" +
									" where RTRIM(LTRIM(kyhieu)) = '"+ this.kyhieuhoadon +"' and cast((case when  sohoadon!='NA' then sohoadon else 0 end) as numeric(18,0)) >= "+ sohoadontu +" and cast(sohoadon as numeric(18,0))<= " + Integer.parseInt(sohoadonden) + " " +
									"        and trangthai != '3'  and nguoisua= "+ this.userId +" and sohoadon != 'NA' and mauhoadon = 2 ";
							System.out.println("Câu kiểm tra SHD "+query);
							ResultSet KiemTra_OTC = db.get(query);
							int check_OTC = 0;
							if(KiemTra_OTC != null)
							{
								while(KiemTra_OTC.next())
								{
									check_OTC = KiemTra_OTC.getInt("dem");
								}
								KiemTra_OTC.close();
							}
			
						// LAY SOIN MAX	
						if(check <= 0 && check_OTC <= 0)
						{
							chuoiHD = ("000000"+ sohoadontu);
							chuoiHD = chuoiHD.substring(chuoiHD.length() - 7, chuoiHD.length());
						}
						else
						{
							// LAY SOIN MAX TRONG HOADON : 
							  //OTC
							query = " select  MAX(cast((case when  sohoadon!='NA' then sohoadon else 0 end) as numeric)) as SOIN_MAX" +
									" from HOADON where RTRIM(LTRIM(KYHIEU)) ='"+ this.kyhieuhoadon +"' and cast((case when  sohoadon!='NA' then sohoadon else 0 end) as numeric(18,0)) >= "+ sohoadontu +" and cast(sohoadon as numeric(18,0))<= " + Integer.parseInt(sohoadonden) + " " +
									"       and trangthai != '3'  and nguoisua= "+ this.userId +" and sohoadon != 'NA' and mauhoadon = 2 ";
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
							query = " select  MAX(cast((case when  sohoadon!='NA' then sohoadon else 0 end) as numeric)) as SOIN_MAX" +
									" from ERP_HOADONNPP " +
									" where RTRIM(LTRIM(KYHIEU)) ='"+ this.kyhieuhoadon +"' and cast((case when  sohoadon!='NA' then sohoadon else 0 end) as numeric(18,0)) >= "+ sohoadontu +" and cast(sohoadon as numeric(18,0))<= " + Integer.parseInt(sohoadonden) + " " +
									"       and trangthai != '3'  and nguoisua= "+ this.userId +" and sohoadon != 'NA' and mauhoadon = 2 ";
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
					query=  " select count(pk_seq) as dem" +
							" from NHANVIEN" +
							" where pk_seq= '"+ this.userId+"' and  isnull(sohoadontu,'') != '' and isnull(sohoadonden,'')  != ''" +
							"       and isnull(kyhieu, '') != ''  ";
					
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
						this.msg = "Vui lòng khai báo Số hóa đơn trong menu Cập nhật nhân viên cho user này ";
						
					}
					else
					{
						// LAY KY HIEU HOA DON ,SOHDTU TRONG DLN
						query= "  select kyhieu as kyhieuhoadon, sohoadontu, sohoadonden " +
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
							}
							rsLayDL.close();
						}
			
						
						// KIEM TRA SOHOADON DA DUOC DUNG CHUA
						   // ETC
							query = " select count(pk_seq) as dem" +
									" from ERP_HOADONNPP" +
									" where RTRIM(LTRIM(kyhieu)) = '"+ this.kyhieuhoadon +"' and cast((case when  sohoadon!='NA' then sohoadon else 0 end) as int) >=  "+ sohoadontu +" " +
									"       and trangthai != '3' and nguoisua= "+ this.userId +" and sohoadon != 'NA' and mauhoadon = 1  ";
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
							
					        //OTC
							query = " select count(pk_seq) as dem" +
									" from HOADON" +
									" where RTRIM(LTRIM(kyhieu)) = '"+ this.kyhieuhoadon +"' and cast((case when  sohoadon!='NA' then sohoadon else 0 end) as numeric(18,0)) >= "+ sohoadontu +" and cast(sohoadon as numeric(18,0))<= " + Integer.parseInt(sohoadonden) + " " +
									"        and trangthai != '3'  and nguoisua= "+ this.userId +" and sohoadon != 'NA' and mauhoadon = 1 ";
							System.out.println("Câu kiểm tra SHD "+query);
							ResultSet KiemTra_OTC = db.get(query);
							int check_OTC = 0;
							if(KiemTra_OTC != null)
							{
								while(KiemTra_OTC.next())
								{
									check_OTC = KiemTra_OTC.getInt("dem");
								}
								KiemTra_OTC.close();
							}
			
						// LAY SOIN MAX	
						if(check <= 0 && check_OTC <= 0)
						{
							chuoiHD = ("000000"+ sohoadontu);
							chuoiHD = chuoiHD.substring(chuoiHD.length() - 7, chuoiHD.length());
						}
						else
						{
							// LAY SOIN MAX TRONG HOADON : 
							  //OTC
							query = " select  MAX(cast((case when  sohoadon!='NA' then sohoadon else 0 end) as numeric)) as SOIN_MAX" +
									" from HOADON where RTRIM(LTRIM(KYHIEU)) ='"+ this.kyhieuhoadon +"' and cast((case when  sohoadon!='NA' then sohoadon else 0 end) as numeric(18,0)) >= "+ sohoadontu +" and cast(sohoadon as numeric(18,0))<= " + Integer.parseInt(sohoadonden) + " " +
									"       and trangthai != '3'  and nguoisua= "+ this.userId +" and sohoadon != 'NA' and mauhoadon = 1 ";
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
							query = " select  MAX(cast((case when  sohoadon!='NA' then sohoadon else 0 end) as numeric)) as SOIN_MAX" +
									" from ERP_HOADONNPP " +
									" where RTRIM(LTRIM(KYHIEU)) ='"+ this.kyhieuhoadon +"' and cast((case when  sohoadon!='NA' then sohoadon else 0 end) as numeric(18,0)) >= "+ sohoadontu +" and cast(sohoadon as numeric(18,0))<= " + Integer.parseInt(sohoadonden) + " " +
									"       and trangthai != '3'  and nguoisua= "+ this.userId +" and sohoadon != 'NA' and mauhoadon = 1 ";
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
			}catch(Exception e)
			{
				this.msg = "Lỗi xảy ra trong quá trình lấy Số hóa đơn";
				e.printStackTrace();
			}
		
		if(this.khId.trim().length() > 0 )
		{	
		  // LAY TEN NGUOI MUA TRONG DLN
		  query =" select isnull(chucuahieu,'') as nguoimua from khachhang where pk_seq= '"+ this.khId +"' ";
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
			
		// LAY DONHANG	
		  query = 
			" select PK_SEQ , NgayDonHang  " +
			" from ERP_DONDATHANGNPP " +
			" where  NPP_FK = '"+ this.nppDangnhap +"'  AND  KHACHHANG_FK = '" + this.khId + "' " +
			"       and ( pk_seq in (select ddh_fk " +
			"                      from ERP_YCXUATKHONPP_DDH a inner join ERP_YCXUATKHONPP b on a.ycxk_fk= b.pk_seq " +
			"                      where b.trangthai=2 )  " +
			"       or pk_seq in (select ddh_fk " +
			"                      from ERP_YCXUATKHO_DDH a inner join ERP_YCXUATKHO b on a.ycxk_fk= b.pk_seq " +
			"                      where b.trangthai=2 and b.NPP_FK= "+ this.nppDangnhap +" ) ) " +
			" and pk_seq not in (select a.DDH_FK " +
			"                    from  ERP_HOADONNPP_DDH a inner join ERP_HOADONNPP b on a.HOADONNPP_FK=b.PK_SEQ" +
			"                    where b.TRANGTHAI in (1,2,4) )" ;
			System.out.println("Câu query "+query);		
			this.ddhRs = db.get(query);
		}
		if(this.nppId.trim().length() > 0 )
		{			
		  query = 
			" select PK_SEQ , NgayDonHang  " +
			" from ERP_DONDATHANGNPP " +
			" where NPP_FK = '"+ this.nppDangnhap +"' AND  NPP_DAT_FK = '" + this.nppId + "' " +
			"       and pk_seq in (select ddh_fk " +
			"                      from ERP_YCXUATKHONPP_DDH a inner join ERP_YCXUATKHONPP b on a.ycxk_fk= b.pk_seq " +
			"                      where b.trangthai=2 ) " +
			" and pk_seq not in (select a.DDH_FK " +
			"                    from  ERP_HOADONNPP_DDH a inner join ERP_HOADONNPP b on a.HOADONNPP_FK=b.PK_SEQ" +
			"                    where b.TRANGTHAI in (1,2,4) )" ;
			System.out.println("Câu query "+query);		
			this.ddhRs = db.get(query);
		}
		
		String chuoi = this.ddhId;
		if(chuoi.length() > 0)
		{	
			// INIT TONG TIEN VAT
		  try 
		  {
				query = "select b.PK_SEQ as SPID, b.MA, b.TEN, DV.donvi, a.dongia , "+
					    "  isnull(scheme,' ') as scheme , isnull(a.thuevat,0) as vat ,   " +
					    "  SUM( a.soluong)  as soluong, SUM( isnull(a.chietkhau, 0)) as chietkhau "+
					    "from ERP_DONDATHANGNPP_SANPHAM a inner Join SanPham b on a.SANPHAM_FK = b.PK_SEQ   "+  	 
					    " INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ = a.DVDL_FK  " +
					    " inner join  ERP_DONDATHANGNPP c on a.dondathang_fk=c.pk_seq    "+
					    "where a.dondathang_fk in ( "+ chuoi +" ) and a.dondathang_fk in (select pk_seq from ERP_DONDATHANGNPP where NPP_FK="+ this.nppDangnhap +")  " +
					    "group by b.PK_SEQ , b.MA, b.TEN, DV.donvi, a.dongia , isnull(scheme,' ') , isnull(a.thuevat,0) ";
			 
			    System.out.println("Lấy sản phẩm: "+query);
			    ResultSet rsLaySP = db.get(query);
		
		    	String spID = "";
				String spMA = "";
				String spTEN = "";
				String spDONVI = "";
				String spSOLUONG = "";
				String spGIANHAP = "";
				String spCHIETKHAU = "";
				String spSCHEME = "";
				String spVAT = "";
				String spTIENTHUE = "";
			
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
						spVAT +=  (rsLaySP.getDouble("vat")) + "__";
						spTIENTHUE +=  Math.round( ( Math.round( rsLaySP.getDouble("SOLUONG") * rsLaySP.getDouble("DONGIA") ) - rsLaySP.getDouble("chietkhau") ) * rsLaySP.getDouble("vat") / 100 ) + "__";
						
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
						
						spTIENTHUE = spTIENTHUE.substring(0, spTIENTHUE.length() - 2);
						this.spTienthue = spTIENTHUE.split("__");
						
					}
			    }
			} 
		  catch (SQLException e) 
		  {
				e.printStackTrace();
		  }
		}
		
	  }
	  else // ID > 0
	  {
		// LAY SP TRONG HOADON
			query = "select b.PK_SEQ as SPID, b.MA, b.TEN, a.donvitinh, a.soluong, a.dongia, isnull(a.chietkhau, 0) as chietkhau, a.scheme, a.vat," +
					"	isnull( TIENVAT, ( ( round( soluong * dongia, 0 ) - chietkhau ) * isnull(vat, 0) / 100 ) ) as tienVAT  " +
					"from ERP_HOADONNPP_SP a inner Join SanPham b on a.SANPHAM_FK = b.PK_SEQ    " +
					"where a.hoadon_fk = "+ this.id +"  " ;				   
		  
		  System.out.println("INIT sản phẩm: "+query);
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
				String spTIENTHUE = "";
				
		  	
			    if(rsLaySP!= null)
			    {				    	
					while(rsLaySP.next())
					{
						spID += rsLaySP.getString("SPID") + "__";
						spMA += rsLaySP.getString("MA") + "__";
						spTEN += rsLaySP.getString("TEN") + "__";
						spDONVI += rsLaySP.getString("DONVITINH") + "__";
						spSOLUONG += (rsLaySP.getDouble("SOLUONG")) + "__";
						spGIANHAP += (rsLaySP.getDouble("DONGIA")) + "__";
						spCHIETKHAU += (rsLaySP.getDouble("chietkhau")) + "__";
						spSCHEME += rsLaySP.getString("scheme") + "__";
						spVAT +=  (rsLaySP.getDouble("vat")) + "__";
						spTIENTHUE +=  (rsLaySP.getDouble("tienVAT")) + "__";
					
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
		
						spTIENTHUE = spTIENTHUE.substring(0, spTIENTHUE.length() - 2);
						this.spTienthue = spTIENTHUE.split("__");
						
					}
			    }
	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		  
	  }
	  
	  
	}
		

	public void init() 
	{
		NumberFormat formatter = new DecimalFormat("##,###,###");
		NumberFormat formatter2 = new DecimalFormat("##,###,###.####");
		Utility util = new Utility();
		this.getNppInfo();
		String query =  "select kho_fk,dondathangnpp_fk,"+
						 " kbh_fk as kbhId,"+						
						 " npp_fk, ngayxuatHD, ISNULL(ghichu, '') as ghichu, khachhang_fk, npp_dat_fk, trangthai, "+
						 "kyhieu, sohoadon, hinhthuctt ,  isnull(chietkhau,0) as chietkhau, " +
						 "(case when nguoimua is null and khachhang_fk is not null then (select isnull(chucuahieu,'') from KHACHHANG where pk_seq= khachhang_fk ) " +
						 "     else  isnull(nguoimua,'') end) as nguoimua, isnull(innguoimua,1) as innguoimua, "+
						 " isnull(tongtienbvat,0) as tongtienbvat,  isnull(tongtienavat,0) as tongtienavat, "+
						 " isnull(vat, 0) as vat, isnull(chietkhau, 0) as chietkhau, loaixuatHD,isnull(mavv,'') as mavv "+
						"from ERP_HOADONNPP "+
						"where pk_seq = '"+ this.id +"'";
		System.out.println("____INIT HOADON: " + query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				if(rs.next())
				{
					this.ngayxuatHD = rs.getString("ngayxuatHD");
					this.ghichu = rs.getString("ghichu");
					this.hinhthuctt = rs.getString("hinhthuctt");
					this.kyhieuhoadon = rs.getString("kyhieu");
					this.sohoadon = rs.getString("sohoadon");
					this.khId = rs.getString("khachhang_fk");
					this.nppId = rs.getString("npp_dat_fk");
					this.loaiXHD = rs.getString("loaixuatHD");		
					this.nguoimua = rs.getString("nguoimua");	
					this.innguoimua = rs.getString("innguoimua");
					this.trangthai = rs.getString("trangthai");
					this.kbhId=rs.getString("kbhId");
					this.khoNhanId=rs.getString("kho_fk");
					this.mavuviec=rs.getString("mavv");
					if(rs.getString("npp_fk").equals("106179")) //Hải phòng không làm tròn tiền
					{
						this.bvat = formatter2.format(rs.getDouble("tongtienbvat"));
						this.totalCHIETKHAU = formatter2.format(rs.getDouble("chietkhau"));
						this.thueVAT = formatter2.format(rs.getDouble("vat"));
						this.avat = formatter2.format(rs.getDouble("tongtienavat"));
					}
					else
					{
						this.bvat = formatter.format(rs.getDouble("tongtienbvat"));
						this.totalCHIETKHAU = formatter.format(rs.getDouble("chietkhau"));
						this.thueVAT = formatter.format(rs.getDouble("vat"));
						this.avat = formatter.format(rs.getDouble("tongtienavat"));
					}
					this.ddhId="";
					//INIT DDH
					query = "SELECT HOADONNPP_FK, DDH_FK FROM ERP_HOADONNPP_DDH WHERE HOADONNPP_FK = " + this.id;
					System.out.println("---LAY DDH: " + query );
					rs = this.db.get(query);
					if(rs!=null)
					{
						
						String _ddhId = "";
						while(rs.next())
						{
							_ddhId = _ddhId + rs.getString("DDH_FK") + ",";
						}
						
						if(_ddhId.trim().length() > 0)
						{
							_ddhId = _ddhId.substring(0, _ddhId.length() - 1);
							this.ddhId = _ddhId;
						}
					}
					
					if(this.trangthai.equals("3") || this.trangthai.equals("5") )
					{
						query = " select B.PK_SEQ ,B.NgayDonHang   " +
								" from ERP_HOADONNPP_DDH A INNER JOIN ERP_DONDATHANGNPP B ON A.DDH_FK = B.PK_SEQ " +
								" where A.HOADONNPP_FK = '"+ this.id +"'  and B.kho_fk in " + util.quyen_kho(this.userId);
					}
					else
					{
						if(this.loaiXHD.equals("1") ) // KHACHHANG
						{			
						  query = 
							" select PK_SEQ , NgayDonHang  " +
							" from ERP_DONDATHANGNPP " +
							" where  NPP_FK="+ this.nppDangnhap +" AND  KHACHHANG_FK = '" + this.khId + "' and kho_fk in " + util.quyen_kho(this.userId) +
							"       and (pk_seq in (select ddh_fk " +
							"                      from ERP_YCXUATKHONPP_DDH a inner join ERP_YCXUATKHONPP b on a.ycxk_fk= b.pk_seq " +
							"                      where b.trangthai=2 )" +
							"       or pk_seq in (select ddh_fk " +
							"                      from ERP_YCXUATKHO_DDH a inner join ERP_YCXUATKHO b on a.ycxk_fk= b.pk_seq " +
							"                      where b.trangthai=2 and b.NPP_FK= "+ this.nppDangnhap +" ) ) " +
							" and pk_seq not in  (select a.DDH_FK from  ERP_HOADONNPP_DDH a inner join ERP_HOADONNPP b on a.HOADONNPP_FK=b.PK_SEQ where b.TRANGTHAI in (2,4) and b.pk_seq != " + this.id +")   " ;
	
						}
						else // DOITAC
						{			
						  query = 
							" select PK_SEQ , NgayDonHang  " +
							" from ERP_DONDATHANGNPP " +
							" where   NPP_FK="+ this.nppDangnhap +" AND  NPP_DAT_FK = '" + this.nppId + "' and kho_fk in" +util.quyen_kho(this.userId) +
							"       and pk_seq in (select ddh_fk " +
							"                      from ERP_YCXUATKHONPP_DDH a inner join ERP_YCXUATKHONPP b on a.ycxk_fk= b.pk_seq " +
							"                      where b.trangthai=2 )" +
							" and pk_seq not in(select a.DDH_FK from  ERP_HOADONNPP_DDH a inner join ERP_HOADONNPP b on a.HOADONNPP_FK=b.PK_SEQ where b.TRANGTHAI =2 and b.pk_seq != " + this.id +")  " ;
	
						}
					}
					System.out.println("LAY DANH SACH DDH: " + query);		
					this.ddhRs = db.get(query);
				}
				rs.close();
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				System.out.println("---LOI INIT: " + e.getMessage());
			}
		}
		try
		{
			//INIT SOLO
			query = "select sanpham_fk as sanpham_fk, solo, ngayhethan, sum(soluong) as soluong  " +
					"from ERP_DONDATHANGNPP_SANPHAM_CHITIET  ct "
					+ " where dondathang_fk='"+this.ddhId+"' " +
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

		this.createRs();
	}

	public void DBclose() {
		
		try{
			
			if(khoNhanRs!=null){
				khoNhanRs.close();
			}
			
			this.db.shutDown();
			
		}catch(Exception er){
			er.printStackTrace();
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
		
		if(this.ddhId.trim().length() <= 0)
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
					if(Double.parseDouble(spSoluong[i].trim().replace(",", "")) > 0)
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
			db.getConnection().setAutoCommit(false);
			
			String query = " select loaiNPP from NHAPHANPHOI where pk_seq = '" + this.nppDangnhap + "' ";
			ResultSet rs = db.get(query);
			String loaiNPP = "";
			if(rs != null)
			{
				if(rs.next())
				{
					loaiNPP = rs.getString("loaiNPP");
				}
				rs.close();
			}
			
			// LAY KY HIEU HOA DON ,SOHDTU TRONG DLN
			String chuoi="";
			long sohoadontu=0;
			String sohoadonden="";
			int kbDLN=0;
			
			// Lấy mẫu hóa đơn của Khách hàng dùng
			query =" select mauhoadon from KHACHHANG where PK_SEQ ='"+this.khId+"'";
			ResultSet mauHDrs = db.get(query);
			String mau = "";
			if(mauHDrs!=null)
			{
				while(mauHDrs.next())
				{
					mau = mauHDrs.getString("mauhoadon");
				}
				mauHDrs.close();
			}
			
			if(!loaiNPP.equals("4"))
			{
				if(this.nppDangnhap.equals("100002") || (this.nppDangnhap.equals("106210")&& mau.equals("2"))) // NẾU LÀ CN HÀ NỘI/ HỒ CHÍ MINH && KHÁCH HÀNG DÙNG MẪU HD TRREN TT
				{

					 query= " select  sohoadontu2, sohoadonden2 " +
							" from NHANVIEN" +
							" where pk_seq= '"+ this.userId +"' and  isnull(kyhieu2,'')= '"+ this.kyhieuhoadon +"' ";
					
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
					if (sohoadontu == 0 || sohoadonden.trim().length() <= 0 )
					{
						this.msg = "Ký hiệu "+ this.kyhieuhoadon +" khác với ký hiệu khai báo trong dữ liệu nền/ Chưa khai báo số hóa đơn trong dữ liệu nền (Số hóa đơn)";
						db.getConnection().rollback();
						return false;
					}
					
					// Check So hoa don sua da co dung chua

					// OTC
					query= " select  count(pk_seq) as kiemtra " +
					       " from HOADON " +
					       " where nguoisua= '"+ this.userId +"' and RTRIM(LTRIM(sohoadon)) = '"+ this.sohoadon.trim() +"' and RTRIM(LTRIM(kyhieu)) ='"+ this.kyhieuhoadon +"' " +
					       "       and trangthai != '3' and pk_seq != "+ this.id +"  and npp_fk = "+this.nppDangnhap+" and mauhoadon = 2 ";
				
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
					       " where nguoisua= '"+ this.userId +"' and RTRIM(LTRIM(sohoadon)) = '"+ this.sohoadon.trim() +"' and RTRIM(LTRIM(kyhieu))='"+ this.kyhieuhoadon +"' " +
					       "       and trangthai != '3' and pk_seq != "+ this.id +"  and npp_fk = "+this.nppDangnhap+" and mauhoadon = 2  ";
					
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
				
				
					if (ktra > 0 || ktra_ETC > 0 )
					{
						this.msg = "Số hóa đơn này đã được sử dụng. Vui lòng đánh số hóa đơn khác. ";
						db.getConnection().rollback();
						return false;
					}
					else if(this.sohoadon.trim().length() != 7)
					{
						this.msg = "Số hóa đơn phải đủ 7 ký tự .Vui lòng kiểm tra lại! ";
						db.getConnection().rollback();
						return false;
					}
				}
				else
				{
					 query= " select  sohoadontu, sohoadonden " +
							" from NHANVIEN" +
							" where pk_seq= '"+ this.userId +"' and  isnull(kyhieu,'')= '"+ this.kyhieuhoadon +"' ";
					
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
					if (sohoadontu == 0 || sohoadonden.trim().length() <= 0 )
					{
						this.msg = "Ký hiệu "+ this.kyhieuhoadon +" khác với ký hiệu khai báo trong dữ liệu nền/ Chưa khai báo số hóa đơn trong dữ liệu nền (Số hóa đơn)";
						db.getConnection().rollback();
						return false;
					}
					
					// Check So hoa don sua da co dung chua
					   // OTC
						query= " select  count(pk_seq) as kiemtra " +
						       " from HOADON " +
						       " where nguoisua= '"+ this.userId +"' and RTRIM(LTRIM(sohoadon)) = '"+ this.sohoadon.trim() +"' and RTRIM(LTRIM(kyhieu)) ='"+ this.kyhieuhoadon +"' " +
						       "       and trangthai != '3' and pk_seq != "+ this.id +"  and npp_fk = "+this.nppDangnhap+"  and mauhoadon = 1 ";
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
						       " where nguoisua= '"+ this.userId +"' and RTRIM(LTRIM(sohoadon)) = '"+ this.sohoadon.trim() +"' and RTRIM(LTRIM(kyhieu))='"+ this.kyhieuhoadon +"' " +
						       "       and trangthai != '3' and pk_seq != "+ this.id +"  and npp_fk = "+this.nppDangnhap+" and mauhoadon = 1  ";
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
					
					
					if (ktra > 0 || ktra_ETC > 0 )
					{
						this.msg = "Số hóa đơn này đã được sử dụng. Vui lòng đánh số hóa đơn khác. ";
						db.getConnection().rollback();
						return false;
					}
					else if(this.sohoadon.trim().length() != 7)
					{
						this.msg = "Số hóa đơn phải đủ 7 ký tự .Vui lòng kiểm tra lại! ";
						db.getConnection().rollback();
						return false;
					}
					
			  }
			}
			
			String tbThongTin = " NHAPHANPHOI ";
			String thongtinId = this.nppId;
			if(this.nppId.length() <= 0)
			{ 
				this.nppId = "NULL"; 
				tbThongTin = " KHACHHANG ";
				thongtinId = this.khId;
			}
			if(this.khId.length() <= 0) this.khId = "NULL";
			
			
			
			if(this.nppDangnhap.equals("100002")) mau = "2";
			if(!this.nppDangnhap.equals("100002") && !this.nppDangnhap.equals("106210")) mau = "1";
			
			
			String ddkd_fk ="(select ddkd_fk From Erp_DonDatHangNpp where pk_Seq='"+this.ddhId+"') ";
			String khoId ="(select KHO_FK From Erp_DonDatHangNpp where pk_Seq='"+ddhId+"') ";
			String kbhId ="(select kbh_fk From Erp_DonDatHangNpp where pk_Seq='"+ddhId+"') ";
		 
			
			 query = 	" insert ERP_HOADONNPP(KHO_FK,DDKD_FK,nguoimua, innguoimua,ngayxuatHD, trangthai, ngaytao, nguoitao, ngaysua, nguoisua, kyhieu, sohoadon, hinhthuctt ," +
				       " 	chietkhau, tongtienbvat, tongtienavat, vat, ghichu, loaixuathd, npp_fk, khachhang_fk, npp_dat_fk, mauhoadon,TENKHACHHANG,DIACHI,MASOTHUE,mavv ) " +
					   " select "+khoId+","+ddkd_fk+",  N'"+ this.nguoimua +"' , "+ this.innguoimua +", '" + this.ngayxuatHD + "', '1','" + getDateTime() + "', '" + this.userId + "', '" + getDateTime() + "', '" + this.userId + "', RTRIM(LTRIM('" + this.kyhieuhoadon + "')), " +
					   "       RTRIM(LTRIM('" + this.sohoadon + "')), N'"+ this.hinhthuctt +"' , '"+ this.totalCHIETKHAU.replaceAll(",", "")  +"', '"+ this.bvat.replaceAll(",", "") +"', '" + this.avat.replaceAll(",", "")  +"'," +
					   "       '"+ this.thueVAT.replaceAll(",", "") +"', N'"+ this.ghichu +"', '"+ this.loaiXHD +"', '"+ this.nppDangnhap +"', "+ this.khId +", "+ this.nppId +", '"+ mau +"'" +
					   "		 , (select ten from "+tbThongTin+" where pk_Seq =" + thongtinId + " ) as nppMua " +
					   " 		, (select ISNULL(DIACHI,'') from "+tbThongTin+" where pk_Seq =" + thongtinId + " ) as diachinpp " +
					   " 		, (select ISNULL(MASOTHUE,'') from "+tbThongTin+" where pk_Seq =" + thongtinId + " ) as mst, '"+this.mavuviec+"'" ;
			 			
			System.out.println("1.Insert ERP_HOADONNPP: " + query);
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ERP_HOADONNPP " + query;
				db.getConnection().rollback();
				return false;
			}
			
			String hdId = "";
			query = "select SCOPE_IDENTITY() as hdId";
			ResultSet rs1 = db.get(query);
			rs1.next();
			hdId = rs1.getString("hdId");
			rs1.close();
			
			for(int i = 0; i < spId.length; i++)
			{
				if(spId[i].trim().length() > 0 && Double.parseDouble(spSoluong[i].trim().replaceAll(",", "")) > 0 )
				{
					String thanhtien = spSoluong[i].replaceAll(",", "")+ " * "+ spDongia[i].replaceAll(",", "");
					
					query = "insert ERP_HOADONNPP_SP( hoadon_fk, sanpham_fk, donvitinh, soluong, dongia, thanhtien, chietkhau, scheme , vat) " +
							" values( "+ hdId +", '" + spId[i] + "', N'"+ spDonvi[i] +"', '" + spSoluong[i].replaceAll(",", "") + "', '" + spDongia[i].replaceAll(",", "") + "'," +
									" "+ thanhtien +", '"+ spChietkhau[i].replaceAll(",", "") +"', N'"+ spSCheme[i].replaceAll(",", "") +"', '"+ spVat[i].replaceAll(",", "") +"' ) ";
					
					System.out.println("1.1.Insert ERP_HOADONNPP_SP: " + query);
					if(!db.update(query))
					{
						this.msg = "Khong the tao moi ERP_HOADONNPP_SP: " + query;
						db.getConnection().rollback();
						return false;
					}
			
				}
			}
			
				query=
				"		update C set SoLuong_Chuan=  "+
				"				case     when c.donvitinh = e.donvi then c.soluong  "+     
				"								  else c.soluong * ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK )   end  ,  "+ 
				"		DonGia_Chuan=	case     when c.donvitinh = e.donvi then c.dongia      "+
				"							  else c.dongia * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from		DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK )   end  , "+
				"			DonVi_Chuan=e.DONVI  "+
				"		from ERP_HOADONNPP a         "+
				"			left join KHACHHANG KH on a.KHACHHANG_FK = KH.PK_SEQ      "+
				"			inner join ERP_HOADONNPP_SP c on a.pk_seq = c.hoadon_fk  "+ 
				"			inner join SANPHAM d on c.sanpham_fk = d.pk_seq    "+
				"			inner join DONVIDOLUONG e on d.DVDL_FK = e.pk_seq  "+      
				"		where a.pk_Seq='"+hdId+"' ";
				System.out.println("1.1.UPDATE ERP_HOADONNPP: " + query);
				if(!db.update(query))
				{
					this.msg = "Không thể tạo mới ERP_HOADONNPP_SP " + query;
					db.getConnection().rollback();
					return false;
				}
			
			
			query = "Insert ERP_HOADONNPP_DDH(hoadonnpp_fk, ddh_fk) select " + hdId + ", pk_seq from ERP_DONDATHANGNPP where pk_seq in ( " + this.ddhId + " )    ";
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ERP_HOADONNPP_DDH " + query;
				db.getConnection().rollback();
				return false;
			}
					
			query = "Update ERP_YCXUATKHONPP set NGAYYEUCAU = '" + this.ngayxuatHD + "' " +
					"where PK_SEQ in ( select ycxk_fk from ERP_YCXUATKHONPP_DDH where ddh_fk in ( " + this.ddhId + " ) )";
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ERP_YCXUATKHONPP " + query;
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
		String sqlCHECK = "select a.trangthai, b.loaiNPP from ERP_HOADONNPP a inner join NHAPHANPHOI b on a.npp_fk = b.pk_seq " +
						  "where a.pk_seq = '" + this.id + "'";
		System.out.println("Lấy trạng thái "+sqlCHECK);
		ResultSet rsCHECK = db.get(sqlCHECK);
		String trangthai = "";
		String loaiNPP = "";
		try 
		{
			if(rsCHECK.next())
			{
				trangthai = rsCHECK.getString("trangthai");
				loaiNPP = rsCHECK.getString("loaiNPP");
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
			if(this.ddhId.length() <= 0)
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
			db.getConnection().setAutoCommit(false);
			
			// LAY KY HIEU HOA DON ,SOHDTU TRONG DLN
			String chuoi="";
			long sohoadontu=0;
			String sohoadonden="";
			int kbDLN=0;
			
			// Lấy mẫu hóa đơn của Khách hàng dùng
			String sql =" select mauhoadon from KHACHHANG where PK_SEQ ='"+this.khId+"'";
			ResultSet mauHDrs = db.get(sql);
			String mau = "";
			if(mauHDrs!=null)
			{
				while(mauHDrs.next())
				{
					mau = mauHDrs.getString("mauhoadon");
				}
				mauHDrs.close();
			}
			
			if(!loaiNPP.equals("4"))
			{
				if(this.nppDangnhap.equals("100002") || (this.nppDangnhap.equals("106210")&& mau.equals("2"))) // NẾU LÀ CN HỒ CHÍ MINH && KHÁCH HÀNG DÙNG MẪU HD TRÊN TT
				{

					String query=   " select  sohoadontu2, sohoadonden2 " +
									" from NHANVIEN" +
									" where pk_seq= '"+ this.userId +"' and isnull(kyhieu2, '') = '"+ this.kyhieuhoadon +"' ";
				
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
						this.msg = " Ký hiệu hóa đơn vừa sửa không giống với ký hiệu khai báo trong dữ liệu nền/ Chưa khai báo số hóa đơn trong dữ liệu nền (Số hóa đơn) ";
						db.getConnection().rollback();
						return false;
					}
					
					 // OTC
					query= " select  count(pk_seq) as kiemtra " +
					       " from HOADON " +
					       " where nguoisua= '"+ this.userId +"' and RTRIM(LTRIM(sohoadon)) = '"+ this.sohoadon.trim() +"' and RTRIM(LTRIM(kyhieu)) ='"+ this.kyhieuhoadon +"' " +
					       "       and trangthai != 3   and npp_fk = "+this.nppDangnhap+" and mauhoadon = 2 ";
					
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
					       " where nguoisua= '"+ this.userId +"' and RTRIM(LTRIM(sohoadon)) = '"+ this.sohoadon.trim() +"' and RTRIM(LTRIM(kyhieu)) ='"+ this.kyhieuhoadon +"' " +
					       "       and trangthai != 3  and pk_seq != "+ this.id +"  and npp_fk = "+this.nppDangnhap+" and mauhoadon = 2 ";
				
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
				
				
					if (ktra > 0 || ktra_ETC > 0 )
					{
						this.msg = "Số hóa đơn này đã được sử dụng. Vui lòng đánh số hóa đơn khác. ";
						db.getConnection().rollback();
						return false;
					}
					else if (this.sohoadon.trim().length() != 7 )
					{
							this.msg = "Số hóa đơn phải đủ 7 ký tự . Vui lòng kiểm tra lại. ";
							db.getConnection().rollback();
							return false;
					}
					
			   
				}
				else
				{
					String query=   " select  sohoadontu, sohoadonden " +
									" from NHANVIEN" +
									" where pk_seq= '"+ this.userId +"' and isnull(kyhieu, '') = '"+ this.kyhieuhoadon +"' ";
					System.out.println("Câu query: "+query);
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
						this.msg = " Ký hiệu hóa đơn vừa sửa không giống với ký hiệu khai báo trong dữ liệu nền/ Chưa khai báo số hóa đơn trong dữ liệu nền (Số hóa đơn) ";
						db.getConnection().rollback();
						return false;
					}
					
					// Check So hoa don sua da co dung chua
					   // OTC
						query= " select  count(pk_seq) as kiemtra " +
						       " from HOADON " +
						       " where nguoisua= '"+ this.userId +"' and RTRIM(LTRIM(sohoadon)) = '"+ this.sohoadon.trim() +"' and RTRIM(LTRIM(kyhieu)) ='"+ this.kyhieuhoadon +"' " +
						       "       and trangthai != 3   and npp_fk = "+this.nppDangnhap+" and mauhoadon = 1 ";
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
						       " where nguoisua= '"+ this.userId +"' and RTRIM(LTRIM(sohoadon)) = '"+ this.sohoadon.trim() +"' and RTRIM(LTRIM(kyhieu)) ='"+ this.kyhieuhoadon +"' " +
						       "       and trangthai != 3  and pk_seq != "+ this.id +"  and npp_fk = "+this.nppDangnhap+" and mauhoadon = 1 ";
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
					
					
					if (ktra > 0 || ktra_ETC > 0 )
					{
						this.msg = "Số hóa đơn này đã được sử dụng. Vui lòng đánh số hóa đơn khác. ";
						db.getConnection().rollback();
						return false;
					}
					else if (this.sohoadon.trim().length() != 7 )
					{
							this.msg = "Số hóa đơn phải đủ 7 ký tự . Vui lòng kiểm tra lại. ";
							db.getConnection().rollback();
							return false;
					}
					
			   }
			}
			
			if(this.nppId.length() <= 0) this.nppId = "NULL";
			if(this.khId.length() <= 0) this.khId = "NULL";
			
			String query = "";
			if(!trangthai.equals("1") )  //NEU DA DUYET THI CHI DUOC SUA CAC THONG NAY
			{
				query = " update ERP_HOADONNPP set  ngaysua = '" + getDateTime() + "' , nguoisua ='" + this.userId + "'," +
					    " kyhieu = RTRIM(LTRIM('" + this.kyhieuhoadon + "')) , sohoadon= RTRIM(LTRIM('" + this.sohoadon + "')), hinhthuctt= N'"+ this.hinhthuctt +"', ghichu= N'"+ this.ghichu +"' " +
					    " where pk_seq = '"+ this.id +"' and TrangThai!=1 " ;
				System.out.println("1.Update ERP_HOADONNPP1: " + query);
				if(db.updateReturnInt(query)!=1)
				{
					this.msg = "Hóa đơn đã chốt,vui lòng kiểm tra lại "+query;
					db.getConnection().rollback();
					return false;
				}
			}
			else
			{
				 query = " update ERP_HOADONNPP set innguoimua= "+ this.innguoimua +" , nguoimua = N'"+ this.nguoimua +"' , dondathangnpp_fk = "+ this.ddhId + ", ngayxuatHD = '" + this.ngayxuatHD + "' , ngaysua = '" + getDateTime() + "' , nguoisua ='" + this.userId + "'," +
						       " kyhieu = RTRIM(LTRIM('" + this.kyhieuhoadon + "')) , sohoadon= RTRIM(LTRIM('" + this.sohoadon + "')), hinhthuctt= N'"+ this.hinhthuctt +"' ," +
						       " chietkhau =  '"+ this.totalCHIETKHAU.replaceAll(",", "")  +"' , tongtienbvat= '"+ this.bvat.replaceAll(",", "") +"', tongtienavat='" + this.avat.replaceAll(",", "") + "', vat = '"+ this.thueVAT.replaceAll(",", "") + "', ghichu= N'"+ this.ghichu +"'," +
						       " loaixuathd= '"+ this.loaiXHD +"' , npp_fk = '"+ this.nppDangnhap +"', khachhang_fk= "+ this.khId +", npp_dat_fk = "+ this.nppId +" ,mavv='" +this.mavuviec+"'"+
						       " where pk_seq = '"+ this.id +"'" ;
				 
				System.out.println("1.Update ERP_HOADONNPP2: " + query);
				if(!db.update(query))
				{
					this.msg = "Không thể cập nhật ERP_HOADONNPP " + query;
					db.getConnection().rollback();
					return false;
				}
				
				/*Utility util = new Utility();
				msg= util.Check_Huy_NghiepVu_KhoaSo("ERP_HOADONNPP", id, "ngayxuatHD");
				if(msg.length()>0)
				{
					db.getConnection().rollback();
					return false;
				}*/
				
				query = " delete from ERP_HOADONNPP_SP where hoadon_fk = '"+ this.id +"' " ;
				if(!db.update(query))
				{
					this.msg = "Không thể xóa HOADON_SP " + query;
					db.getConnection().rollback();
					return false;
	       }
				
				query="delete from ERP_HOADONNPP_SP_ChiTiet where hoadon_fk="+this.id+"";
				if(db.updateReturnInt(query)<=0)
				{
					msg = "Khong the cap nhat ERP_HOADONNPP_SP_ChiTiet: " + query;
					db.getConnection().rollback();
					return false;
				}						
				
				query = " delete from ERP_HOADONNPP_DDH where hoadonnpp_fk = '"+ this.id +"' " ;
				if(!db.update(query))
				{
					this.msg = "Không thể xóa ERP_HOADONNPP_DDH " + query;
					db.getConnection().rollback();
					return false;
	      }
				
				for(int i = 0; i < spId.length; i++)
				{
					if(spId[i].trim().length() > 0 && Double.parseDouble(spSoluong[i].trim().replaceAll(",", "")) > 0 )
					{
						String thanhtien = spSoluong[i].replaceAll(",", "") + " * "+ spDongia[i].replaceAll(",", "");
						
						query = "insert ERP_HOADONNPP_SP( hoadon_fk, sanpham_fk, donvitinh, soluong, dongia, thanhtien, chietkhau, scheme, vat, TIENVAT ) values " +
								" ('"+ this.id +"', '" + spId[i] + "', N'"+ spDonvi[i] +"', '" + spSoluong[i].replaceAll(",", "") + "', '" + spDongia[i].replaceAll(",", "") + "'," +
										" ("+ thanhtien +"), '"+ spChietkhau[i].replaceAll(",", "") +"', N'"+ spSCheme[i].replaceAll(",", "") +"','"+ spVat[i].replaceAll(",", "") +"', '" + spTienthue[i].replaceAll(",", "") + "' )  ";
						
						System.out.println("1.1.Insert ERP_HOADONNPP_SP: " + query);
						if(!db.update(query))
						{
							this.msg = "Khong the tao moi ERP_HOADONNPP_SP: " + query;
							db.getConnection().rollback();
							return false;
						}
					}
				}
				
				query=
				"		update C set SoLuong_Chuan=  "+
				"				case     when c.donvitinh = e.donvi then c.soluong  "+     
				"								  else c.soluong * ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK )   end  ,  "+ 
				"		DonGia_Chuan=	case     when c.donvitinh = e.donvi then c.dongia      "+
				"							  else c.dongia * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from		DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK )   end  , "+
				"			DonVi_Chuan=e.DONVI  "+
				"		from ERP_HOADONNPP a         "+
				"			left join KHACHHANG KH on a.KHACHHANG_FK = KH.PK_SEQ      "+
				"			inner join ERP_HOADONNPP_SP c on a.pk_seq = c.hoadon_fk  "+ 
				"			inner join SANPHAM d on c.sanpham_fk = d.pk_seq    "+
				"			inner join DONVIDOLUONG e on d.DVDL_FK = e.pk_seq  "+      
				"		where a.pk_Seq='"+this.id+"' ";
				
				System.out.println("1.1.UPDATE ERP_HOADONNPP_SP: " + query);
				
				if(!db.update(query))
				{
					this.msg = "Không thể tạo mới ERP_HOADONNPP_SP " + query;
					db.getConnection().rollback();
					return false;
				}				
				query = "Insert ERP_HOADONNPP_DDH(hoadonnpp_fk, ddh_fk) select '"+ this.id +"', pk_seq from ERP_DONDATHANGNPP where pk_seq in ( " + this.ddhId + " )  ";
				if(!db.update(query))
				{
					this.msg = "Không thể tạo mới ERP_HOADONNPP_DDH " + query;
					db.getConnection().rollback();
					return false;
				}
				
				query = "Update ERP_YCXUATKHONPP set NGAYYEUCAU = '" + this.ngayxuatHD + "' " +
						"where PK_SEQ in ( select ycxk_fk from ERP_YCXUATKHONPP_DDH where ddh_fk in ( " + this.ddhId + " ) )";
				if(!db.update(query))
				{
					this.msg = "Không thể tạo mới ERP_YCXUATKHONPP " + query;
					db.getConnection().rollback();
					return false;
				}
				
				query = "delete ERP_DONDATHANGNPP_SANPHAM_CHITIET where dondathang_fk in  (" + this.ddhId + ") ";
				if(!db.update(query))
				{
					this.msg = "Lỗi khi duyệt: " + query;
					db.getConnection().rollback();
					return false;
				}
				//LUU VAO BANG CHI TIET
				for(int i = 0; i < spMa.length; i++)
				{
					if(spMa[i].trim().length() > 0 && spSoluong[i].trim().length() > 0 )
					{
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
									
									query = "insert ERP_DONDATHANGNPP_SANPHAM_CHITIET( dondathang_fk, SANPHAM_FK, dvdl_fk, solo, soluong, ngayhethan )  " +
											"select '" + ddhId+ "', pk_seq, ( select dvdl_fk from ERP_DONDATHANGNPP_SANPHAM where dondathang_fk = '" + ddhId + "' and sanpham_fk = a.pk_seq ),  N'" + _sp[1] + "' as solo, '" + _soluongCT.replaceAll(",", "") + "' as soluong, '"+_sp[2]+"' as NgayHetHan   " +
											"from SANPHAM a where MA = '" + spMa[i] + "'  ";
									
									System.out.println("1.2.Insert DDH - SP - CT: " + query);
									if(!db.update(query))
									{
										this.msg = "Khong the tao moi ERP_DONDATHANGNPP_SANPHAM_CHITIET: " + query;
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
				
				//CHECK TONG KHO CHI TIET PHAI BANG TONG TRONG KHO TONG
				query = "select count(*) as soDONG   " +
						"from ERP_DONDATHANGNPP_SANPHAM tong left join   " +
						"	(  " +
						"		select sanpham_fk, sum(soluong) as soluong   " +
						"		from ERP_DONDATHANGNPP_SANPHAM_CHITIET  " +
						"		where  dondathang_fk = '" + this.ddhId + "'  " +
						"		group by sanpham_fk " +
						"	)  " +
						"	CT on tong.sanpham_fk = CT.sanpham_fk " +
						"where dondathang_fk = '" + this.ddhId + "' and tong.soluong != isnull(CT.soluong, 0)  " ;
				
				System.out.println("[CheckTong_CT]"+query);
				
				rsCHECK = db.get(query);
				int soDONG = 0;
				{
					if( rsCHECK.next() )
					{
						soDONG = rsCHECK.getInt("soDONG");
					}
					rsCHECK.close();
				}
				
				if(soDONG > 0)
				{
					db.getConnection().rollback();
					this.msg = "11.Lỗi hệ thống ( tổng xuất theo lô đề xuất đang bị sai ). Vui lòng liên hệ trung tâm để được hỗ trợ xử lý."+query;
					return false;
				}
				
				String ycxkId="(select ycxk_fk from ERP_YCXUATKHONPP_DDH a inner join ERP_YCXUATKHONPP b on b.pk_seq=a.ycxk_fk where a.ddh_fk="+this.ddhId+" and b.trangthai!=3 )";
				String kbh_fk="(select b.kbh_fk from  erp_DonDatHangNpp b  where pk_seq="+this.ddhId+"  )";
			
				if(dungchungKENH.equals("1"))
				{
					kbh_fk="100025";
				}
				
			
				query=
				"   select b.NPP_FK as nppId ,"+kbh_fk+"  as  KBH_FK,b.Kho_FK,a.sanpham_fk,a.solo,a.ngayhethan , a.soluong "+
				" from ERP_YCXUATKHONPP_SANPHAM_CHITIET a inner join ERP_YCXUATKHONPP b on b.PK_SEQ=a.ycxk_fk  "+ 
				" where a.ycxk_fk="+ycxkId+"  ";
				System.out.println("---hehe---heheh" + query);
				ResultSet rs=db.get(query);
				while(rs.next())
				{
					double SoLuong=rs.getDouble("SoLuong");
					String nppId= rs.getString("nppId");
					String khoId =rs.getString("Kho_FK");
					String sanpham_fk =rs.getString("sanpham_fk");
					String SoLo =rs.getString("SoLo");
					String NgayHetHan =rs.getString("NgayHetHan");
					
						query="update nhapp_kho_chitiet set SoLuong=SoLuong+'"+SoLuong+"',Available=Available+"+SoLuong+" where npp_fk='"+nppId+"' and kbh_fk='"+kbh_fk+"' and kho_fk='"+khoId+"' and SanPham_fk='"+sanpham_fk+"' and SoLo='"+SoLo+"' and NgayHetHan='"+NgayHetHan+"' ";
						if(db.updateReturnInt(query)!=1)
						{
							msg = "Khong the cap nhat nhapp_kho_chitiet: " + query;
							db.getConnection().rollback();
							return false;
						}
				}
				
				query="delete from ERP_YCXUATKHONPP_SANPHAM_CHITIET where ycxk_fk="+ycxkId+"";
				if(db.updateReturnInt(query)<=0)
				{
					msg = "Khong the cap nhat ERP_YCXUATKHONPP_SANPHAM_CHITIET: " + query;
					db.getConnection().rollback();
					return false;
				}						
				
				query = "select c.npp_fk, case when isnull(d.dungchungkenh, 0) = 0 then c.kbh_fk else 100025 end as kbh_fk, " +
						"		c.kho_fk, a.sanpham_fk, b.ten as TEN, a.soluong as soluongDAT, a.solo, a.ngayhethan,  " +
						"		case when a.dvdl_fk IS null then a.soluong      " +
						"			 when a.dvdl_fk = b.DVDL_FK then a.soluong     " +
						"			 else  a.soluong * ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk and DVDL1_FK = b.DVDL_FK )   end as soluong,  " +
						"	0 as loai, ' ' as scheme    " +
						"from ERP_DONDATHANGNPP_SANPHAM_CHITIET a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ   " +
						"		inner join ERP_DONDATHANGNPP c on  a.dondathang_fk = c.pk_seq inner join NHAPHANPHOI d on c.npp_fk = d.pk_seq " +
						"where a.dondathang_fk in ( " + this.ddhId + " )   ";
				
				System.out.println("--CHECK TON KHO CHI TIET: " + query);
				
				rs = db.get(query);				
				{
					while(rs.next())
					{
						String khoID = rs.getString("kho_fk");
						String kbhID = rs.getString("kbh_fk");
						String nppID = rs.getString("npp_fk");
						String spID = rs.getString("sanpham_fk");
						
						double soluong = rs.getDouble("soluong");
						String solo = rs.getString("solo");
						String ngayhethan = rs.getString("ngayhethan");
						
						double tonkho = 0;
						query = "select AVAILABLE from NHAPP_KHO_CHITIET " +
								"where kho_fk = '" + khoID + "' and sanpham_fk = '" + spID + "' and kbh_fk = '" + kbhID + "' and npp_fk = '" + nppID + "' and solo = '" + solo + "' and NgayHetHan='"+ngayhethan+"' ";
						System.out.println("CHECK TON KHO: " + query);
						ResultSet rsTONKHO = db.get(query);
						{
							if(rsTONKHO.next())
								tonkho = rsTONKHO.getDouble("AVAILABLE");
							rsTONKHO.close();
						}
						
						if(soluong > tonkho)
						{
							msg = "Sản phẩm ( " + rs.getString("TEN") + " ), số lô ( " + rs.getString("solo") + " ) với số lượng yêu cầu ( " + rs.getString("soluong") + " ) không đủ tồn kho ( " + tonkho + " ). Vui lòng kiểm tra lại.";
							rs.close();
							db.getConnection().rollback();
							return false;
						}
						query = "Update NHAPP_KHO_CHITIET set soluong = soluong - '" + soluong + "', AVAILABLE = AVAILABLE - '" + soluong + "' " +
									  "where KHO_FK = '" + khoID + "' and KBH_FK = '" + kbhID + "' and NPP_FK = '" + nppID + "' and SANPHAM_FK = '" + spID + "' and SOLO = '" + solo + "' and NgayHetHan='"+ngayhethan+"' ";
						if(db.updateReturnInt(query)!=1)
						{
							msg = "Khong the cap nhat NHAPP_KHO_CHITIET: " + query;
							rs.close();
							db.getConnection().rollback();
							return false;
						}
						query = "insert ERP_YCXUATKHONPP_SANPHAM_CHITIET( ycxk_fk, SANPHAM_FK, solo, soluong, ngayhethan, kho_fk ) " +
								"select " + ycxkId + ", '" + spID + "', N'" + solo + "', '" + soluong  + "', '" + ngayhethan + "', '" + khoID + "' ";
						
						System.out.println("1.2.Insert YCXK - SP - CT: " + query);
						if(db.updateReturnInt(query)!=1)
						{
							msg = "Khong the cap nhat ERP_YCXUATKHONPP_SANPHAM_CHITIET: " + query;
							rs.close();
							db.getConnection().rollback();
							return false;
						}						
					}
					rs.close();
				}
				
				query =
				" insert ERP_HOADONNPP_SP_CHITIET(hoadon_fk, donhang_fk, KBH_FK, Kho_FK, MA, TEN, DONVI, DVCHUAN, DVDATHANG, SOLUONG, SOLO, NGAYHETHAN, CHIETKHAU, THUEVAT, DONGIA,SoLuong_Chuan,DonGia_Chuan,SoLuong_DatHang)  " +
				"		select '" + this.id + "' as hoadon_fk, dh.pk_seq as donhang_fk, a.KBH_FK, a.KHO_FK, c.MA, isnull(dhsp.sanphamTEN ,c.TEN ) as TEN, (select donvi from DONVIDOLUONG where pk_seq = dhsp.dvdl_fk ) as donvi, d.pk_seq as dvCHUAN, dhsp.dvdl_fk  as dvDATHANG, "+    
				"	case when d.pk_seq = dhsp.dvdl_fk then b.soluong  "+    
				"			else b.soluong * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.pk_seq and DVDL2_FK = dhsp.dvdl_fk and DVDL1_FK = d.pk_seq ) end as soluong, "+      
				"	case solo when 'NA' then ' ' else b.solo end as solo,     "+
				"	case solo when 'NA' then ' ' else isnull(b.NGAYHETHAN,'') end as NGAYHETHAN, " +
				"		CASE WHEN ROW_NUMBER() OVER(PARTITION BY c.ma ORDER BY c.ma DESC) = 1 then dhsp.chietkhau else 0 end as chietKhau "+
				", dhsp.thuevat,    "+
				"	( select dongia from ERP_HOADONNPP_SP where hoadon_fk ='" + this.id + "' and sanpham_fk = b.sanpham_fk ) as dongia  	, "+   
				"		b.soluong  as SoLuong_Chuan,   "+
				"			case when d.pk_seq = dhsp.dvdl_fk then dhsp.DONGIA "+     
				"	else dhsp.DONGIA *    "+
				"	( select SOLUONG2 / SOLUONG1 "+
				"	from QUYCACH where sanpham_fk = c.pk_seq and DVDL2_FK = dhsp.dvdl_fk and DVDL1_FK = d.pk_seq ) end as DonGia_Chuan ,   "+
				"		CASE WHEN ROW_NUMBER() OVER(PARTITION BY c.ma ORDER BY c.ma DESC) = 1 THEN dhsp.soluong else 0 end as SoLuong_DatHang "+  
				" from ERP_YCXUATKHONPP a inner join ERP_YCXUATKHONPP_SANPHAM_CHITIET b on a.pk_seq = b.ycxk_fk "+	   
				"     inner join 	ERP_YCXUATKHONPP_DDH e on e.ycxk_fk = a.PK_SEQ									       "+ 									   
				"     inner join ERP_DONDATHANGNPP dh on dh.PK_SEQ = e.ddh_fk    									    "+
				"     inner join ERP_DONDATHANGNPP_SANPHAM dhsp on dhsp.dondathang_fk = dh.PK_SEQ and dhsp.sanpham_fk = b.sanpham_fk "+	   
				"     inner join SANPHAM c on dhsp.sanpham_fk = c.PK_SEQ   "+						   
				"     inner join DONVIDOLUONG d on d.PK_SEQ = c.dvdl_fk "+ 	   
				" where dh.trangthai != '3' and a.PK_SEQ in    "+
				"  ( select ycxk_fk from ERP_YCXUATKHONPP_DDH   "+
				"  where ddh_fk = ( select DDH_FK from ERP_HOADONNPP_DDH where hoadonnpp_fk = '" + this.id + "' ) ) and b.soluong > 0 and a.TRANGTHAI != '3' " ;
				
				if(db.updateReturnInt(query)<=0)
				{
					msg = "Khong the cap nhat ERP_HOADONNPP_SP_CHITIET: " + query;
					rs.close();
					db.getConnection().rollback();
					return false;
				}						
			}				
			
			Utility util = new Utility();
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

	public boolean chot() 
	{
		try
		{
			
			Utility util = new Utility();
			msg= util.Check_Huy_NghiepVu_KhoaSo("ERP_HOADONNPP", id, "NgayXuatHD");
			if(msg.length()>0)
				return false;
			
			db.getConnection().setAutoCommit(false);
			String query = "update ERP_HOADONNPP set trangthai = '2',NguoiSua='"+this.userId+"',NgaySua='"+getDateTime()+"'  where pk_seq = '" + this.id + "' and TrangThai!=2 ";
			if(db.updateReturnInt(query)!=1)
			{
				msg = "Hóa đơn đã chốt rồi hoặc có lỗi phát sinh trong quá trình cập nhật "+query;
				db.getConnection().rollback();
				return false;
			}
			query="delete from ERP_HOADONNPP_SP_CHITIET where hoadon_fk='"+this.id+"'";
			if(!db.update(query))
			{
				msg = "Hóa đơn đã chốt rồi hoặc có lỗi phát sinh trong quá trình cập nhật "+query;
				db.getConnection().rollback();
				return false;
			}
			
			query = 
					" insert ERP_HOADONNPP_SP_CHITIET(hoadon_fk, donhang_fk, KBH_FK, Kho_FK, MA, TEN, DONVI, DVCHUAN, DVDATHANG, SOLUONG, SOLO, NGAYHETHAN, CHIETKHAU, THUEVAT, DONGIA,SoLuong_Chuan,DonGia_Chuan,SoLuong_DatHang)  " +
					"		select '" + this.id + "' as hoadon_fk, dh.pk_seq as donhang_fk, a.KBH_FK, a.KHO_FK, c.MA, isnull(dhsp.sanphamTEN ,c.TEN ) as TEN, (select donvi from DONVIDOLUONG where pk_seq = dhsp.dvdl_fk ) as donvi, d.pk_seq as dvCHUAN, dhsp.dvdl_fk  as dvDATHANG, "+    
					"	case when d.pk_seq = dhsp.dvdl_fk then b.soluong  "+    
					"			else b.soluong * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.pk_seq and DVDL2_FK = dhsp.dvdl_fk and DVDL1_FK = d.pk_seq ) end as soluong, "+      
					"	case solo when 'NA' then ' ' else b.solo end as solo,     "+
					"	case solo when 'NA' then ' ' else isnull(b.NGAYHETHAN,'') end as NGAYHETHAN, " +
					"		CASE WHEN ROW_NUMBER() OVER(PARTITION BY c.ma ORDER BY c.ma DESC) = 1 then dhsp.chietkhau else 0 end as chietKhau "+
					", dhsp.thuevat,    "+
					"	( select dongia from ERP_HOADONNPP_SP where hoadon_fk ='" + this.id + "' and sanpham_fk = b.sanpham_fk ) as dongia  	, "+   
					"		b.soluong  as SoLuong_Chuan,   "+
					"			case when d.pk_seq = dhsp.dvdl_fk then dhsp.DONGIA "+     
					"	else dhsp.DONGIA *    "+
					"	( select SOLUONG2 / SOLUONG1 "+
					"	from QUYCACH where sanpham_fk = c.pk_seq and DVDL2_FK = dhsp.dvdl_fk and DVDL1_FK = d.pk_seq ) end as DonGia_Chuan ,   "+
					"		CASE WHEN ROW_NUMBER() OVER(PARTITION BY c.ma ORDER BY c.ma DESC) = 1 THEN dhsp.soluong else 0 end as SoLuong_DatHang "+  
					" from ERP_YCXUATKHONPP a inner join ERP_YCXUATKHONPP_SANPHAM_CHITIET b on a.pk_seq = b.ycxk_fk "+	   
					"     inner join 	ERP_YCXUATKHONPP_DDH e on e.ycxk_fk = a.PK_SEQ									       "+ 									   
					"     inner join ERP_DONDATHANGNPP dh on dh.PK_SEQ = e.ddh_fk    									    "+
					"     inner join ERP_DONDATHANGNPP_SANPHAM dhsp on dhsp.dondathang_fk = dh.PK_SEQ and dhsp.sanpham_fk = b.sanpham_fk "+	   
					"     inner join SANPHAM c on dhsp.sanpham_fk = c.PK_SEQ   "+						   
					"     inner join DONVIDOLUONG d on d.PK_SEQ = c.dvdl_fk "+ 	   
					" where dh.trangthai != '3' and a.PK_SEQ in    "+
					"  ( select ycxk_fk from ERP_YCXUATKHONPP_DDH   "+
					"  where ddh_fk = ( select DDH_FK from ERP_HOADONNPP_DDH where hoadonnpp_fk = '" + this.id + "' ) ) and b.soluong > 0 and a.TRANGTHAI != '3' " ;
			
			System.out.println("---CHAY HOA DON CHI TIET: " + query );
			if(!db.update(query))
			{
				msg = "Hóa đơn đã chốt rồi hoặc có lỗi phát sinh trong quá trình cập nhật "+query;
				db.getConnection().rollback();
				return false;
			}
			
			//CHECK BANG TONG PHAI BANG BANG CHI TIET
			query = "select count(*) as sodong  " +
					"from " +
					"( " +
					"	select b.pk_seq as sanpham_fk, sum(soluong) as soluong  " +
					"	from ERP_HOADONNPP_SP a inner join SANPHAM b on a.sanpham_fk = b.pk_seq " +
					"	where a.hoadon_fk = '" + this.id + "' " +
					"	group by b.pk_seq " +
					") " +
					"dh left join " +
					"( " +
					"	select b.pk_seq as sanpham_fk, sum(soluong) as soluong  " +
					"	from ERP_HOADONNPP_SP_CHITIET a inner join SANPHAM b on a.MA = b.MA " +
					"	where a.hoadon_fk = '" + this.id + "' " +
					"	group by b.pk_seq " +
					") " +
					"xk on dh.sanpham_fk = xk.sanpham_fk " +
					"where dh.soluong != isnull(xk.soluong, 0) ";
			System.out.println("---CHECK HOA DON: " + query);
			int soDONG = 0;
			ResultSet rsCHECK = db.get(query);
			if(rsCHECK != null)
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
			
			msg= util.Check_Kho_Tong_VS_KhoCT(util.getIdNhapp(userId));
			if(msg.length()>0)
			{
				db.getConnection().rollback();
				return false;
			}

			//LUU LAI THONG TIN
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

	public String getLoaiXHD() {
		
		return this.loaiXHD;
	}

	public void setLoaiXHD(String loaiXHD) {
		this.loaiXHD= loaiXHD;
		
	}

	
	public String[] getSpVat() {
		
		return this.spVat;
	}

	
	public void setSpVat(String[] spVat) {
		 this.spVat = spVat;
		
	}

	
	public void loadContents()
	{
		this.getNppInfo();
		String query ="";
		Utility util = new Utility();
		query = "select PK_SEQ, TEN from KHACHHANG where TRANGTHAI = '1' and npp_fk ='"+ this.nppDangnhap +"' ";
		this.khRs = db.get(query);
		
		 query = "select PK_SEQ, MA + ' - ' + TEN as TEN from NHAPHANPHOI where TRANGTHAI = '1' and loaiNPP = '4' and tructhuoc_fk ='"+ this.nppDangnhap +"' ";
		 this.nppRs = db.get(query);
		
		if(this.khId.trim().length() > 0 )
		{			
		  query = 
			" select PK_SEQ , NgayDonHang  " +
			" from ERP_DONDATHANGNPP " +
			" where NPP_FK = '"+ this.nppDangnhap +"'  AND  KHACHHANG_FK = '" + this.khId + "' and kho_fk in " + util.quyen_kho(this.userId)+
			"       and pk_seq in (select ddh_fk " +
			"                      from ERP_YCXUATKHONPP_DDH a inner join ERP_YCXUATKHONPP b on a.ycxk_fk= b.pk_seq " +
			"                      where b.trangthai=2 )  " +
			" and pk_seq not in (select a.DDH_FK " +
			"                    from  ERP_HOADONNPP_DDH a inner join ERP_HOADONNPP b on a.HOADONNPP_FK=b.PK_SEQ" +
			"                    where b.TRANGTHAI =2 )" +
			" and pk_seq not in(select a.DDH_FK from  ERP_HOADONNPP_DDH a inner join ERP_HOADONNPP b on a.HOADONNPP_FK=b.PK_SEQ where b.TRANGTHAI in ( 1, 2, 4 ) and b.pk_seq != " + ( this.id.trim().length() > 0 ? this.id : "-1" ) + " )  " +
		  	" order by NgayDonHang desc ";
		  System.out.println("Câu query "+query);		
			this.ddhRs = db.get(query);
		}
		if(this.nppId.trim().length() > 0 )
		{			
		  query = 
			" select PK_SEQ , NgayDonHang  " +
			" from ERP_DONDATHANGNPP " +
			" where NPP_FK = '"+ this.nppDangnhap +"' AND  NPP_DAT_FK = '" + this.nppId + "' and kho_fk in " + util.quyen_kho(this.userId) +
			"       and pk_seq in (select ddh_fk " +
			"                      from ERP_YCXUATKHONPP_DDH a inner join ERP_YCXUATKHONPP b on a.ycxk_fk= b.pk_seq " +
			"                      where b.trangthai=2 ) " +
			" and pk_seq not in (select a.DDH_FK " +
			"                    from  ERP_HOADONNPP_DDH a inner join ERP_HOADONNPP b on a.HOADONNPP_FK=b.PK_SEQ" +
			"                    where b.TRANGTHAI =2 )" +
			" and pk_seq not in(select a.DDH_FK from  ERP_HOADONNPP_DDH a inner join ERP_HOADONNPP b on a.HOADONNPP_FK=b.PK_SEQ where b.TRANGTHAI in ( 1, 2, 4 ) and b.pk_seq != " + ( this.id.trim().length() > 0 ? this.id : "-1" ) + " )  " +
			" order by NgayDonHang desc ";
		  System.out.println("Câu query "+query);		
			this.ddhRs = db.get(query);
		}
		
		String chuoi = this.ddhId;
		if(chuoi.length() > 0)
		{	
			// INIT TONG TIEN VAT
		  try 
		   {
				NumberFormat formater = new DecimalFormat("##,###,###");
			    
			 query = "select b.PK_SEQ as SPID, b.MA, b.TEN, DV.donvi, a.dongia , "+
				    "  isnull(scheme,' ') as scheme , isnull(a.thuevat,0) as vat ,   " +
				    "  SUM( a.soluong)  as soluong, SUM( isnull(a.chietkhau, 0)) as chietkhau "+
				    "from ERP_DONDATHANGNPP_SANPHAM a inner Join SanPham b on a.SANPHAM_FK = b.PK_SEQ   "+  	 
				    " INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ = b.DVDL_FK  " +
				    " inner join  ERP_DONDATHANGNPP c on a.dondathang_fk=c.pk_seq    "+
				    "where a.dondathang_fk in ( "+ chuoi +" ) and a.dondathang_fk in (select pk_seq from ERP_DONDATHANGNPP where NPP_FK="+ this.nppDangnhap +")  " +
				    "group by b.PK_SEQ , b.MA, b.TEN, DV.donvi, a.dongia , isnull(scheme,' ') , isnull(a.thuevat,0) ";
			 
		    System.out.println("Lấy sản phẩm: "+query);
		    ResultSet rsLaySP = db.get(query);
		
		    
		    	String spID = "";
				String spMA = "";
				String spTEN = "";
				String spDONVI = "";
				String spSOLUONG = "";
				String spGIANHAP = "";
				String spCHIETKHAU = "";
				String spSCHEME = "";
				String spVAT = "";
				String spTIENTHUE = "";
			
		    	
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
						spVAT +=  (rsLaySP.getDouble("vat")) + "__";
						spTIENTHUE +=  Math.round( ( Math.round( rsLaySP.getDouble("SOLUONG") * rsLaySP.getDouble("DONGIA") ) - rsLaySP.getDouble("chietkhau") ) * rsLaySP.getDouble("vat") / 100 ) + "__";
						
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
						
						spTIENTHUE = spTIENTHUE.substring(0, spTIENTHUE.length() - 2);
						this.spTienthue = spTIENTHUE.split("__");
						
					}
			    }

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
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

	
	public String[] getSpTienthue() {

		return this.spTienthue;
	}


	public void setSpTienthua(String[] spTienthue) {
		
		this.spTienthue = spTienthue;
	}

	Hashtable<String, String> sanpham_soluong= new Hashtable<String, String>();
	
	public Hashtable<String, String> getSanpham_Soluong() {
		
		return this.sanpham_soluong;
	}

	
	public void setSanpham_Soluong(Hashtable<String, String> sp_soluong) {
		
		this.sanpham_soluong = sp_soluong;
	}
	
	public ResultSet getSoloTheoSp(String spMa, String donvi, String tongluong)
	{
		tongluong = tongluong.replaceAll(",", "");
		//System.out.println("---TONG LUONG: " + tongluong );
		
		String kbh_fk = this.kbhId;
		if(dungchungKENH.equals("1"))
		{
			kbh_fk="100025";
		}
	
		String		
		query="select case when sp.dvdl_fk !=(select PK_SEQ from DONVIDOLUONG where DIENGIAI like N'"+donvi+"')"+  
		"then ( select soluong2 / soluong1 from QUYCACH where SANPHAM_FK = sp.PK_SEQ and DVDL1_FK = sp.DVDL_FK and DVDL2_FK = (select PK_SEQ from DONVIDOLUONG where DIENGIAI like N'"+donvi+"') ) * AVAILABLE else AVAILABLE end as AVAILABLE,"+ 
		" '2030-31-12' as NGAYHETHAN, 'NA' as SOLO "+
		"from NHAPP_KHO ct inner join SANPHAM sp on ct.sanpham_fk = sp.pk_seq "+
		"where KHO_FK = '"+this.khoNhanId+"' and SANPHAM_FK = '"+spMa+"'"+
		"and  AVAILABLE>0   "+
		"and NPP_FK = '"+this.nppDangnhap+"' and KBH_FK ='"+kbh_fk+"' "+
		"order by NGAYHETHAN asc ";
		System.out.println("----LAY SO LO: " + query );
		String query2 = "";
		ResultSet rs = db.get(query);
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
	
	String kbhId;
	ResultSet kbhRs;

	public String getKbhId() {
		return kbhId;
	}

	public void setKbhId(String kbhId) {
		this.kbhId = kbhId;
	}

	public ResultSet getKbhRs() {
		return kbhRs;
	}

	public void setKbhRs(ResultSet kbhRs) {
		this.kbhRs = kbhRs;
	}
	
	String dungchungKENH;

	public String getDungchungKENH() {
		return dungchungKENH;
	}

	public void setDungchungKENH(String dungchungKENH) {
		this.dungchungKENH = dungchungKENH;
	}
		
}
