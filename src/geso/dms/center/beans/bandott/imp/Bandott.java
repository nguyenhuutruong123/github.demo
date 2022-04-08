package geso.dms.center.beans.bandott.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import geso.dms.center.beans.bandott.IBandott;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;

public class Bandott implements IBandott
{
	String KHACHHANGSI_ID = "100890";
	
	String userId;
	String mafast;
	String ngay;
	String address;
	String trungtam;
	String msg;
	String Dvkdid = "";
	ResultSet DvkdRs;
	
	ResultSet ma_tenkhRs;
	String ma_tenkh;
	
	ResultSet ddkdList;
	String ddkdId;
	
	ResultSet nvList;
	String nvId;
	String View = "";
	
	ResultSet tbhList;
	String tbhId;
	
	ResultSet khSelected; //da vieng tham
	ResultSet khList; //chua vieng tham
	
	ResultSet dstbKhachhang;
	ResultSet khKocodoanhso;
	
	String denngay;
	
	ResultSet vungList;
	String vungId;
	ResultSet kvList;
	String kvId;
	ResultSet nppList;
	String nppId;
	ResultSet cttbList;
	String cttbId;
	
	String khSi;
	
	String[] latcondition;
	String[] longcondition;
	
	ResultSet infoRs;
	ResultSet dpNganhRs;
	ResultSet infoDetail;
	ResultSet kiemTKRs;
	
	dbutils db;
	String sonvbh = "";
	String sonvbhol = "";
	String isTT = "";
	String isGSBH = "";
	geso.dms.center.util.Utility util = new geso.dms.center.util.Utility();
	public Bandott()
	{
		String[] dauthang = this.getDateTime().split("-");
		System.out.println("ngay la "+dauthang[0] + "-" + dauthang[1] + "-" + dauthang[2]);
		this.ngay = dauthang[0] + "-" + dauthang[1] + "-" + dauthang[2];
		this.address = "";
		this.trungtam = "";
		this.denngay = this.getDateTime();
		this.mafast="";
		this.vungId = "";
		this.kvId = "";
		this.nppId = "";
		this.cttbId = "";
		this.ddkdId = "";
		this.nvId = "";
		this.tbhId = "";
		this.khSi = "0";
		this.msg = "";
		this.ttId="";	
		this.ma_tenkh = "";
		this.action="";
		db = new dbutils();
	}
	
	
	public String getMa_tenkh() {
		return ma_tenkh;
	}


	public void setMa_tenkh(String ma_tenkh) {
		this.ma_tenkh = ma_tenkh;
	}


	public String getIsGSBH() {
		return isGSBH;
	}
	public void setIsGSBH(String isGSBH) {
		this.isGSBH = isGSBH;
	}
	public String getIsTT() {
		return isTT;
	}
	public void setIsTT(String isTT) {
		this.isTT = isTT;
	}
	public String getUserId()
	{
		return this.userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}
	
	public String getdate() 
	{
		return this.ngay;
	}

	public void setDate(String date) 
	{
		this.ngay = date;
	}

	public ResultSet getDdkdRs() 
	{
		return this.ddkdList;
	}

	public void setDdkdRs(ResultSet ddkdRs) 
	{
		this.ddkdList = ddkdRs;
	}

	public String getDdkdId() 
	{
		return this.ddkdId;
	}

	public void setDdkdId(String ddkdId) 
	{
		this.ddkdId = ddkdId;
	}

	public ResultSet getTbhRs() 
	{
		return this.tbhList;
	}

	public void setTbhRs(ResultSet tbhRs) 
	{
		this.tbhList = tbhRs;
	}

	public String getTbhId() 
	{
		return this.tbhId;
	}

	public void setTbhId(String tbhId) 
	{
		this.tbhId = tbhId;
	}

	public ResultSet getKhDaViengThamRs() 
	{
		return this.khSelected;
	}

	public void setKhDaViengThamRs(ResultSet KhRs) 
	{
		this.khSelected = KhRs;
	}

	public ResultSet getKhChuaViengThamRs()
	{
		return this.khList;
	}

	public void setKhChuaViengThamRs(ResultSet KhRs) 
	{
		this.khList = KhRs;
	}
	
	Utility Ult = new Utility();
	public void init() 
	{
		
		try
		{
			String sql = "select phanloai,LOAI from nhanvien where pk_seq=" + this.userId;
			ResultSet rs = this.db.get(sql);
			if (rs != null)
			{
				if (rs.next())
				{
					this.phanloai = rs.getString("phanloai");
					loaiNv= rs.getString("LOAI")==null?"":rs.getString("LOAI");

					
					if (rs.getString("phanloai").equals("1"))
					{
						this.nppId = Ult.getIdNhapp(this.userId);
					}
					rs.close();
				}
			}
			//System.out.println("sql : " + sql);
		} catch (Exception er)
		{

		}
		String quyenvung = "";
		 String quyenkhuvuc = "";
		 if(this.phanloai.equals("2") )
		 {
			 quyenkhuvuc = " and pk_seq in (select khuvuc_fk from nhaphanphoi where pk_seq in "+util.quyen_npp(this.userId)+") ";
			 quyenvung = " and pk_seq in ( select vung_fk from khuvuc where pk_seq in (select khuvuc_fk from nhaphanphoi where pk_seq in "+util.quyen_npp(this.userId)+")  )";
			 
		 }
		String query="select pk_Seq,ten from tinhthanh where 1=1 "+quyenvung;;
		/*if(this.vungId.length()>0)
		{
			query+=" and vung_fk='"+this.vungId+"' ";
		}*/
		/*
		 if(this.phanloai.equals("2")&& !loaiNv.equals("3"))
			{
			 query+= " and pk_Seq in " + Ult.Quyen_TinhThanh(userId)+"";
			}
		*/
		
		this.ttRs=this.db.get(query);
		
		

		query="select pk_seq, ten from vung where trangthai = '1'"+quyenvung;
		 /*if(this.phanloai.equals("2")&& !loaiNv.equals("3"))
			{
			 query+= " and pk_Seq in " + Ult.Quyen_Vung(userId)+"";
			}*/
		this.vungList = db.get(query);
		
		
		
		
		if(this.vungId.trim().length() > 0)
		{
			this.kvList = db.get("select pk_seq, ten from khuvuc where vung_fk = '" + this.vungId + "' and trangthai = '1' "+quyenkhuvuc);
		}
		else
		{
			this.kvList = db.get("select pk_seq, ten from khuvuc where trangthai = '1'"+quyenkhuvuc);
		}
		
		String sqlNpp = "select pk_seq, ten from nhaphanphoi where trangthai = '1' ";
		
		 if(this.phanloai.equals("2") )
			{
			 sqlNpp+= " and pk_Seq in " + Ult.quyen_npp(userId)+"";
			}
		
		if(this.kvId.trim().length() > 0)
			sqlNpp += " and khuvuc_fk = '" + this.kvId.trim() + "' ";
		if(this.vungId.trim().length() > 0)
			sqlNpp += " and khuvuc_fk in (select pk_seq from khuvuc where vung_fk = '" + this.vungId.trim() + "') ";
		
		if(this.ttId.length()>0)
			sqlNpp +=" and tinhthanh_fk in ("+this.ttId+")" ;
		
		this.nppList = db.get(sqlNpp);
		
		//System.out.println("nppId : "+ this.nppId);
		if(this.nppId != null)
			{
			if(this.nppId.trim().length() > 0)
			{
				 query = "select pk_seq as ddkdId, ten as ddkdTen from daidienkinhdoanh where trangthai=1 ";			
				
				if(this.nppId.trim().length() > 0)
					query += "\n and npp_fk = '" + this.nppId + "' ";
				
				if(this.kvId.length()>0)
					query += "\n and npp_fk in (select pk_Seq from nhaphanphoi where khuvuc_fk='"+this.kvId+"' ) ";
				
				if(this.ttId.length()>0)
					query += "\n and npp_fk in (select pk_Seq from nhaphanphoi where tinhthanh_fk='"+this.ttId+"' )";
				
				this.ddkdList = db.get(query);
				System.out.println("ddkdRs =" + query);
				
				if(this.ddkdId.trim().length() > 0)
				{
					this.tbhList = db.get("select distinct a.PK_SEQ as tbhId, 'T'+CAST(a.NGAYID as CHAR(2)) +b.TEN   as tbhTen from tuyenbanhang a inner join DAIDIENKINHDOANH b on b.PK_SEQ=a.DDKD_FK where a.ddkd_fk = '" + this.ddkdId + "' and a.PK_SEQ in (select distinct tbh_fk from KHACHHANG_TUYENBH) order by tbhTen ");
				}
				
				//Lay cach 3 thang
				if(this.ngay.length() <= 0)
					this.ngay = this.getDateTime();
				
				String[] now = this.ngay.split("-");
				String thang = now[1];
				
				if(thang.startsWith("0"))
					thang = thang.replaceAll("0", "");
				
				String iDay = now[2];
				if(iDay.startsWith("0"))
					iDay = iDay.replaceAll("0", "");
				
				String before1 = Integer.toString(Integer.parseInt(thang) - 1);
				if(before1.length() < 2)
					before1 = "0" + before1;
				
				String before3 = Integer.toString(Integer.parseInt(thang) - 3);
				if(before3.length() < 2)
					before3 = "0" + before3;
				
				String ngaycuoi = LastDayOfMonth(Integer.parseInt(thang) - 1, Integer.parseInt(now[0]));
				
				//String bd_truoc_3_thang = now[0] + "-" + before3 + "-01";
				String bd_truoc_1_thang = now[0] + "-" + before3 + "-" + "01";
				String kt_truoc_1_thang = now[0] + "-" + before1 + "-" + ngaycuoi;
				
				String dauthang = now[0] + "-" + now[1] + "-01";
	
				int tongsongay = Songaytrongthang(Integer.parseInt(thang) - 1, Integer.parseInt(now[0]));
				tongsongay = (tongsongay - 4); //tru 4 chu nhat
				
				String condition = "";
				if(this.nppId.trim().length() > 0)
					condition += " AND npp_fk = '" + this.nppId + "' ";
				else
					condition += " AND npp_fk in ( select pk_seq from NHAPHANPHOI where khuvuc_fk = '" + this.kvId + "' ) ";
				
				System.out.println("---BAT DAU TRUOC 1 THANG: " + bd_truoc_1_thang + " -- KET THUC TRUOC 1 THANG: " + kt_truoc_1_thang + " -- DAU THANG: " + dauthang );
				
				String sql = "select kh.pk_seq as khId, kh.ten as khTen, isnull(kh.dienthoai, 'NA') as dienthoai, isnull(kh.diachi, '') as diachi, isnull(kh.lat,'') as lat, isnull(kh.long,'') as long, " +
								" case when anhcuahang is null then 'hinh1.jpg' else anhcuahang end as anhcuahang,  " +
								" cast(isnull(doanhsoThangtruoc, 0) as numeric(18, 0)) / 3 as doanhsoThangtruoc ,   " +
								" cast( isnull(doanhsoTudauthang, 0) as numeric(18, 0)) as doanhsoTrongthang, " +
								" isnull(( select min(ngaynhap) from DONHANG where trangthai != '2' and khachhang_fk = kh.pk_seq ),'') AS ngayMHDauTien," +
								" isnull(( select max(ngaynhap) from DONHANG where trangthai != '2' and khachhang_fk = kh.pk_seq ),'') AS ngayMHCuoicung,	" +
								" isnull(( select tonggiatri from DONHANG where pk_seq = ( select max(pk_seq) from DONHANG where trangthai != '2' and khachhang_fk = kh.pk_seq ) ),'') AS dhGANNHAT, " +
								"isnull( ( select max(thoidiem) from DDKD_KHACHHANG where khachhang_fk = kh.pk_seq ),'') AS vtGANNHAT," +
								" ( select isnull(sum(tonggiatri) / count(pk_seq),0) from DONHANG where trangthai != '2' and khachhang_fk = kh.pk_seq and ngaynhap >= '" + bd_truoc_1_thang + "' and ngaynhap <= '" + kt_truoc_1_thang + "' ) as tbDONHANG, " +
								" isnull(( select count(distinct dh_sp.sanpham_fk) from DONHANG dh inner join DONHANG_SANPHAM dh_sp on dh.pk_seq = dh_sp.donhang_fk where dh.khachhang_fk = kh.pk_seq and dh.trangthai != '2' ),'') as dophuSP, " +
								" ( select isnull(sum(soluong),'0') as tonkho from KHACHHANG_KHO where KH_FK = kh.pk_seq and ngay = '" + this.ngay + "' ) as tonkhoSP ,	" +
								" CASE WHEN isnull(kh.LCH_FK, '0') = '" + KHACHHANGSI_ID + "' THEN '1' ELSE '0' END AS khachhangsi " +
							"from KhachHang kh  " +
							"left join  " +
							"(   " +
								"select a.khachhang_fk as khId, sum(b.soluong * b.giamua) as doanhsoThangtruoc    " +
								"from donhang a inner join donhang_sanpham b on a.pk_seq = b.donhang_fk  " +
								"where a.khachhang_fk in ( select pk_seq from khachhang where lat is not null " + condition + " ) and a.trangthai = '1'   " +
									"and '" + bd_truoc_1_thang + "' <= a.ngaynhap and a.ngaynhap <= '" + kt_truoc_1_thang + "'   " +
								"group by a.khachhang_fk  " +
							")   " +
							"thangtruoc on kh.pk_seq = thangtruoc.khId left join   " +
							"(   " +
								"select a.khachhang_fk as khId, sum(b.soluong * b.giamua) / 1  as doanhsoTudauthang   " +
								"from donhang a inner join donhang_sanpham b on a.pk_seq = b.donhang_fk  " +
								"where a.khachhang_fk in (select pk_seq from khachhang where lat is not null " + condition + " ) and a.trangthai = '1'   " +
									"and '" + dauthang + "' <= a.ngaynhap and a.ngaynhap <= '" + this.ngay + "'   " +
								"group by a.khachhang_fk  " +
							")   " +
							"trongthang  on kh.pk_seq = trongthang.khId   " +
							"where lat is not null and long is not null ";
				if(this.nppId.trim().length() > 0 )
					sql += " and kh.npp_fk = '" + this.nppId + "'  ";
				else
					sql += " and kh.npp_fk in ( select pk_seq from NHAPHANPHOI where khuvuc_fk = '" + this.kvId + "' ) ";
				
				if(this.tbhId.trim().length() > 0)
				{
					sql += " and kh.pk_seq in (select khachhang_fk from khachhang_tuyenbh where tbh_fk = '" + this.tbhId + "') ";
				} 
				else if(this.ddkdId.trim().length() > 0)
				{
					sql += " and kh.pk_seq in ( select khachhang_fk from khachhang_tuyenbh where tbh_fk in (select pk_seq from tuyenbanhang where ddkd_fk = '" + this.ddkdId + "') ) ";
				}
				
				System.out.println("[Bandott.init] 1.Khoi tao khach hang: " + sql);
				
				if(action.equals("viewBd"))
					this.khList = db.get(sql);
				System.out.println("KHList: "+sql);
				
				if(this.ngay.trim().length() > 0)
				{
					query = "\n select distinct a.pk_seq as khId, a.ten as khTen, isnull(a.diachi, '') as diachi, isnull(a.dienthoai, 'NA') as dienthoai, isnull(b.thoidiem,'') as thoidiem, isnull(c.ten,'') as ddkdTen, isnull(b.lat,'') as lat, isnull(b.long,'') as long, " +
							"\n	case when anhcuahang is null then 'hinh1.jpg' else anhcuahang end as anhcuahang, " +
							"\n   cast(isnull(doanhsoThangtruoc, 0) as numeric(18, 0)) / 3 as doanhsoThangtruoc ,   " +
							"\n   cast( isnull(doanhsoTudauthang, 0) as numeric(18, 0)) as doanhsoTrongthang, " +
							"\n isnull(( select min(ngaynhap) from DONHANG where trangthai != '2' and khachhang_fk = a.pk_seq ),'') AS ngayMHDauTien, " +
							"\n isnull( ( select max(ngaynhap) from DONHANG where trangthai != '2' and khachhang_fk = a.pk_seq ),'') AS ngayMHCuoicung,	" +
							"\n isnull(( select tonggiatri from DONHANG where pk_seq = ( select max(pk_seq) from DONHANG where trangthai != '2' and khachhang_fk = a.pk_seq ) ),'') AS dhGANNHAT, " +
							"\n isnull(( select max(thoidiem) from DDKD_KHACHHANG where khachhang_fk = a.pk_seq ),'') AS vtGANNHAT, " +
							"\n ( select isnull(sum(tonggiatri) / count(pk_seq),0) from DONHANG where trangthai != '2' and khachhang_fk = a.pk_seq and ngaynhap >= '" + bd_truoc_1_thang + "' and ngaynhap <= '" + kt_truoc_1_thang + "' ) as tbDONHANG,	" +
							"\n ( select isnull(count(distinct dh_sp.sanpham_fk),0) from DONHANG dh inner join DONHANG_SANPHAM dh_sp on dh.pk_seq = dh_sp.donhang_fk where dh.khachhang_fk = a.pk_seq and dh.trangthai != '2' ) as dophuSP, " +
							"\n ( select isnull(sum(soluong),0) from KHACHHANG_KHO where KH_FK = a.pk_seq and ngay = '" + this.ngay + "' ) as tonkhoSP,	" +
							"\n ( select isnull(sum(tonggiatri),0)  from DONHANG where trangthai != '2' and khachhang_fk = a.pk_seq and ngaynhap >= '" + this.ngay + "' and ngaynhap <= '" + this.ngay + "' ) as ds	" +
							"\n from khachhang a inner join ddkd_khachhang b on a.pk_seq = b.khachhang_fk " +
							"\n	inner join daidienkinhdoanh c on b.ddkd_fk = c.pk_seq " +
							"\n left join  " +
							"\n (   " +
								"\n select a.khachhang_fk as khId, sum(b.soluong * b.giamua)  as doanhsoThangtruoc    " +
								"\n from donhang a inner join donhang_sanpham b on a.pk_seq = b.donhang_fk  " +
								"\n where a.khachhang_fk in ( select pk_seq from khachhang where lat is not null " + condition + " ) and a.trangthai = '1'   " +
									"\n and '" + bd_truoc_1_thang + "' <= a.ngaynhap and a.ngaynhap <= '" + kt_truoc_1_thang + "'   " +
								"\n group by a.khachhang_fk  " +
							"\n )   " +
							"\n thangtruoc on a.pk_seq = thangtruoc.khId left join   " +
							"\n (   " +
								"\n select a.khachhang_fk as khId, sum(b.soluong * b.giamua) / 1  as doanhsoTudauthang   " +
								"\n from donhang a inner join donhang_sanpham b on a.pk_seq = b.donhang_fk  " +
								"\n where a.khachhang_fk in (select pk_seq from khachhang where lat is not null " + condition + " ) and a.trangthai = '1'   " +
									"\n and '" + dauthang + "' <= a.ngaynhap and a.ngaynhap <= '" + this.ngay + "'   " +
								"\n group by a.khachhang_fk  " +
							"\n )   " +
							"\n trongthang  on a.pk_seq = trongthang.khId   " +
							"\n where replace(convert(nvarchar(10), b.thoidiem, 102), '.', '-') = '" + this.ngay + "' ";
					
					if(this.nppId.trim().length() > 0)
						query += " and a.npp_fk = '" + this.nppId + "' ";
					else
						query += " and a.npp_fk in ( select pk_seq from NHAPHANPHOI where khuvuc_fk = '" + this.kvId + "' ) ";
					
					if(this.tbhId.trim().length() > 0)
					{
						query += " and a.pk_seq in (select khachhang_fk from khachhang_tuyenbh where tbh_fk = '" + this.tbhId + "') ";
					}
					else if(this.ddkdId.trim().length() > 0)
					{
						query += " and b.ddkd_fk = '" + this.ddkdId + "' ";
					}
					
					query += " order by thoidiem ";
					
					System.out.println("[Bandott.init] 2.Khach hang Selected: " + query);
					
					
					if(action.equals("viewBd"))
						this.khSelected = db.getScrol(query);
					
					
					//Ve duong noi cac NVBH
					if(this.ddkdId.trim().length() > 0)
					{
						if(this.khSelected != null)
						{
							try 
							{
								String latC = "";
								String longC = "";
								
								this.khSelected.beforeFirst();
								while(khSelected.next())
								{
									latC += this.khSelected.getString("lat") + "__";
									longC += this.khSelected.getString("long") + "__";
								}
								
								if(latC.trim().length() > 0)
								{
									latC = latC.substring(0, latC.length() - 2);
									this.latcondition = latC.split("__");
									
									longC = longC.substring(0, longC.length() - 2);
									this.longcondition = longC.split("__");
								}
							} 
							catch (Exception e) {e.printStackTrace();}
						}
						
					}
					
				}
				
				//Khoi tao toa do Center dau
				query = "select top(1) pk_seq, lat + ',' + long  as trungtam " +
						"from KhachHang where trangthai = '1' and lat is not null and long is not null ";
	
				if(this.nppId.trim().length() > 0)
					query += " and npp_fk = '" + this.nppId + "' ";
				else
					query += " and npp_fk in ( select pk_seq from NHAPHANPHOI where khuvuc_fk = '" + this.kvId + "' ) ";
				
				if(this.tbhId.trim().length() > 0)
				{
					query += " and pk_seq in (select khachhang_fk from khachhang_tuyenbh where tbh_fk = '" + this.tbhId + "') ";
				}
				else if(this.ddkdId.trim().length() > 0)
				{
					query += " and pk_seq in ( select khachhang_fk from khachhang_tuyenbh where tbh_fk in (select pk_seq from tuyenbanhang where ddkd_fk = '" + this.ddkdId + "') ) ";
				}
				
				System.out.println("[Bandott.init] toa do center dau query = " + query);
				
				ResultSet rsCenter = db.get(query);
				if(rsCenter != null)
				{
					try 
					{
						if(rsCenter.next())
						{
							this.trungtam = rsCenter.getString("trungtam");
						}
						rsCenter.close();
					} 
					catch (Exception e) 
					{
						e.printStackTrace();
						System.out.println("115.Exception: " + e.getMessage());
					}
				}
			} 
		} // nppId != null
		
		
		if(this.Dvkdid.equals("100069"))
		{

			 query = "select pk_seq as ddkdId, ten as ddkdTen from daidienkinhdoanh where trangthai=1 and ismt = '1' ";			
					
			this.ddkdList = db.get(query);
			System.out.println("ddkdRs =" + query);
			
			if(this.ddkdId.trim().length() > 0)
			{
				this.tbhList = db.get("select distinct a.PK_SEQ as tbhId, ' '+CAST(a.ghichu as NVARCHAR(500))    as tbhTen from KEHOACHBANHANG a inner join DAIDIENKINHDOANH b on b.PK_SEQ=a.DDKD_FK where a.ddkd_fk = '" + this.ddkdId + "' and a.PK_SEQ in (select distinct KEHOACHBANHANG_FK from KEHOACHBANHANG_CT) order by tbhTen ");
			}
			
			//Lay cach 3 thang
			if(this.ngay.length() <= 0)
				this.ngay = this.getDateTime();
			
			String[] now = this.ngay.split("-");
			String thang = now[1];
			
			if(thang.startsWith("0"))
				thang = thang.replaceAll("0", "");
			
			String iDay = now[2];
			if(iDay.startsWith("0"))
				iDay = iDay.replaceAll("0", "");
			
			String before1 = Integer.toString(Integer.parseInt(thang) - 1);
			if(before1.length() < 2)
				before1 = "0" + before1;
			
			String before3 = Integer.toString(Integer.parseInt(thang) - 3);
			if(before3.length() < 2)
				before3 = "0" + before3;
			
			String ngaycuoi = LastDayOfMonth(Integer.parseInt(thang) - 1, Integer.parseInt(now[0]));
			
			//String bd_truoc_3_thang = now[0] + "-" + before3 + "-01";
			String bd_truoc_1_thang = now[0] + "-" + before3 + "-" + "01";
			String kt_truoc_1_thang = now[0] + "-" + before1 + "-" + ngaycuoi;
			
			String dauthang = now[0] + "-" + now[1] + "-01";

			int tongsongay = Songaytrongthang(Integer.parseInt(thang) - 1, Integer.parseInt(now[0]));
			tongsongay = (tongsongay - 4); //tru 4 chu nhat
			
			String condition = "";
			if(this.nppId.trim().length() > 0)
				condition += " AND npp_fk = '" + this.nppId + "' ";
			else
				condition += " AND npp_fk in ( select pk_seq from NHAPHANPHOI where khuvuc_fk = '" + this.kvId + "' ) ";
			
			System.out.println("---BAT DAU TRUOC 1 THANG: " + bd_truoc_1_thang + " -- KET THUC TRUOC 1 THANG: " + kt_truoc_1_thang + " -- DAU THANG: " + dauthang );
			
			
			
			String sql = "";
			if(this.Dvkdid.equals("100069"))
			{
				sql = "\n select kh.pk_seq as khId, kh.ten as khTen, isnull(kh.dienthoai, 'NA') as dienthoai, isnull(kh.diachi, '') as diachi, isnull(kh.lat,'') as lat, isnull(kh.long,'') as long, " +
						"\n case when anhcuahang is null then 'hinh1.jpg' else anhcuahang end as anhcuahang,  " +
						"\n cast(isnull(doanhsoThangtruoc, 0) as numeric(18, 0)) / 3 as doanhsoThangtruoc ,   " +
						"\n cast( isnull(doanhsoTudauthang, 0) as numeric(18, 0)) as doanhsoTrongthang, " +
						"\n isnull(( select min(ngaynhap) from DONHANGIP where trangthai != '2' and khachhang_fk = kh.pk_seq ),'') AS ngayMHDauTien," +
						"\n isnull(( select max(ngaynhap) from DONHANGIP where trangthai != '2' and khachhang_fk = kh.pk_seq ),'') AS ngayMHCuoicung,	" +
						"\n isnull(( select tonggiatri from DONHANGIP where pk_seq = ( select max(pk_seq) from DONHANGIP where trangthai != '2' and khachhang_fk = kh.pk_seq ) ),'') AS dhGANNHAT, " +
						"\n isnull( ( select max(thoidiem) from DDKD_KHACHHANG where khachhang_fk = kh.pk_seq ),'') AS vtGANNHAT," +
						"\n ( select isnull(sum(tonggiatri) / count(pk_seq),0) from DONHANGIP where trangthai != '2' and khachhang_fk = kh.pk_seq and ngaynhap >= '" + bd_truoc_1_thang + "' and ngaynhap <= '" + kt_truoc_1_thang + "' ) as tbDONHANG, " +
						"\n isnull(( select count(distinct dh_sp.sanpham_fk) from DONHANGIP dh inner join DONHANG_SANPHAMSPIP dh_sp on dh.pk_seq = dh_sp.DONHANG_fk where dh.khachhang_fk = kh.pk_seq and dh.trangthai != '2' ),'') as dophuSP, " +
						"\n ( select isnull(sum(soluong),'0') as tonkho from KHACHHANG_KHO where KH_FK = kh.pk_seq and ngay = '" + this.ngay + "' ) as tonkhoSP ,	" +
						"\n CASE WHEN isnull(kh.LCH_FK, '0') = '" + KHACHHANGSI_ID + "' THEN '1' ELSE '0' END AS khachhangsi " +
						"\n from KhachHang kh  " +
						"\n left join  " +
						"\n (   " +
						"\n select a.khachhang_fk as khId, sum(b.soluong * b.giamua) as doanhsoThangtruoc    " +
						"\n from DONHANGIP a inner join DONHANG_SANPHAMSPIP b on a.pk_seq = b.DONHANG_fk  " +
						"\n where a.khachhang_fk in ( select pk_seq from khachhang where lat is not null  ) and a.trangthai = '1'   " +
						"\n and '" + bd_truoc_1_thang + "' <= a.ngaynhap and a.ngaynhap <= '" + kt_truoc_1_thang + "'   " +
						"\n group by a.khachhang_fk  " +
						"\n )   " +
						"\n thangtruoc on kh.pk_seq = thangtruoc.khId left join   " +
						"\n (   " +
						"\n select a.khachhang_fk as khId, sum(b.soluong * b.giamua) / 1  as doanhsoTudauthang   " +
						"\n from DONHANGIP a inner join DONHANG_SANPHAMSPIP b on a.pk_seq = b.DONHANG_fk  " +
						"\n where a.khachhang_fk in (select pk_seq from khachhang where lat is not null  ) and a.trangthai = '1'   " +
						"\n and '" + dauthang + "' <= a.ngaynhap and a.ngaynhap <= '" + this.ngay + "'   " +
						"\n group by a.khachhang_fk  " +
						")   " +
						"\n trongthang  on kh.pk_seq = trongthang.khId   " +
						"\n where lat is not null and long is not null and kh.isMT = '1' ";
			}
	
			this.khList = db.get(sql);
			System.out.println("[Bandott.init] 1.Khoi tao khach hang IP: " + sql);
			System.out.println("KHList: "+sql);
			
			if(this.ngay.trim().length() > 0)
			{
				query = "\n select distinct a.pk_seq as khId, a.ten as khTen, isnull(a.diachi, '') as diachi, isnull(a.dienthoai, 'NA') as dienthoai, isnull(b.thoidiem,'') as thoidiem, isnull(c.ten,'') as ddkdTen, isnull(b.lat,'') as lat, isnull(b.long,'') as long, " +
						"\n	case when anhcuahang is null then 'hinh1.jpg' else anhcuahang end as anhcuahang, " +
						"\n   cast(isnull(doanhsoThangtruoc, 0) as numeric(18, 0)) / 3 as doanhsoThangtruoc ,   " +
						"\n   cast( isnull(doanhsoTudauthang, 0) as numeric(18, 0)) as doanhsoTrongthang, " +
						"\n isnull(( select min(ngaynhap) from DONHANGIP where trangthai != '2' and khachhang_fk = a.pk_seq ),'') AS ngayMHDauTien, " +
						"\n isnull( ( select max(ngaynhap) from DONHANGIP where trangthai != '2' and khachhang_fk = a.pk_seq ),'') AS ngayMHCuoicung,	" +
						"\n isnull(( select tonggiatri from DONHANGIP where pk_seq = ( select max(pk_seq) from DONHANGIP where trangthai != '2' and khachhang_fk = a.pk_seq ) ),'') AS dhGANNHAT, " +
						"\n isnull(( select max(thoidiem) from DDKD_KHACHHANG where khachhang_fk = a.pk_seq ),'') AS vtGANNHAT, " +
						"\n ( select isnull(sum(tonggiatri) / count(pk_seq),0) from DONHANGIP where trangthai != '2' and khachhang_fk = a.pk_seq and ngaynhap >= '" + bd_truoc_1_thang + "' and ngaynhap <= '" + kt_truoc_1_thang + "' ) as tbDONHANG,	" +
						"\n ( select isnull(count(distinct dh_sp.sanpham_fk),0) from DONHANGIP dh inner join DONHANG_sanphamSPIP dh_sp on dh.pk_seq = dh_sp.DONHANG_fk where dh.khachhang_fk = a.pk_seq and dh.trangthai != '2' ) as dophuSP, " +
						"\n ( select isnull(sum(soluong),0) from KHACHHANG_KHO where KH_FK = a.pk_seq and ngay = '" + this.ngay + "' ) as tonkhoSP,	" +
						"\n ( select isnull(sum(tonggiatri),0)  from DONHANGIP where trangthai != '2' and khachhang_fk = a.pk_seq and ngaynhap >= '" + this.ngay + "' and ngaynhap <= '" + this.ngay + "' ) as ds	" +
						"\n from khachhang a inner join ddkd_khachhang b on a.pk_seq = b.khachhang_fk " +
						"\n	inner join daidienkinhdoanh c on b.ddkd_fk = c.pk_seq " +
						"\n left join  " +
						"\n (   " +
						"\n select a.khachhang_fk as khId, sum(b.soluong * b.giamua)  as doanhsoThangtruoc    " +
						"\n from DONHANGIP a inner join DONHANG_sanphamSPIP b on a.pk_seq = b.DONHANG_fk  " +
						"\n where a.khachhang_fk in ( select pk_seq from khachhang where lat is not null  ) and a.trangthai = '1'   " +
						"\n and '" + bd_truoc_1_thang + "' <= a.ngaynhap and a.ngaynhap <= '" + kt_truoc_1_thang + "'   " +
						"\n group by a.khachhang_fk  " +
						"\n )   " +
						"\n thangtruoc on a.pk_seq = thangtruoc.khId left join   " +
						"\n (   " +
						"\n select a.khachhang_fk as khId, sum(b.soluong * b.giamua) / 1  as doanhsoTudauthang   " +
						"\n from DONHANGIP a inner join DONHANG_sanphamSPIP b on a.pk_seq = b.DONHANG_fk  " +
						"\n where a.khachhang_fk in (select pk_seq from khachhang where lat is not null  ) and a.trangthai = '1'   " +
						"\n and '" + dauthang + "' <= a.ngaynhap and a.ngaynhap <= '" + this.ngay + "'   " +
						"\n group by a.khachhang_fk  " +
						"\n )   " +
						"\n trongthang  on a.pk_seq = trongthang.khId   " +
						"\n where replace(convert(nvarchar(10), b.thoidiem, 102), '.', '-') = '" + this.ngay + "' and a.isMT = '1' ";
				
			
				
				if(this.tbhId.trim().length() > 0)
				{
					query += " and a.pk_seq in (select khachhang_fk from kehoachbanhang_ct where KEHOACHBANHANG_FK = '" + this.tbhId + "') ";
				}
				else if(this.ddkdId.trim().length() > 0)
				{
					query += " and b.ddkd_fk = '" + this.ddkdId + "' ";
				}
				
				query += " order by thoidiem ";
				
				System.out.println("[Bandott.init] 2.Khach hang Selected IP: " + query);
				
				
				if(action.equals("viewBd"))
					this.khSelected = db.getScrol(query);
				
				
				//Ve duong noi cac NVBH
				if(this.ddkdId.trim().length() > 0)
				{
					if(this.khSelected != null)
					{
						try 
						{
							String latC = "";
							String longC = "";
							
							this.khSelected.beforeFirst();
							while(khSelected.next())
							{
								latC += this.khSelected.getString("lat") + "__";
								longC += this.khSelected.getString("long") + "__";
							}
							
							if(latC.trim().length() > 0)
							{
								latC = latC.substring(0, latC.length() - 2);
								this.latcondition = latC.split("__");
								
								longC = longC.substring(0, longC.length() - 2);
								this.longcondition = longC.split("__");
							}
						} 
						catch (Exception e) {e.printStackTrace();}
					}
					
				}
				
			}
			
			//Khoi tao toa do Center dau
			query = "select top(1) pk_seq, lat + ',' + long  as trungtam " +
					"from KhachHang where trangthai = '1' and ismt = '1' and lat is not null and long is not null ";

	
			
			if(this.tbhId.trim().length() > 0)
			{
				query += " and pk_seq in (select khachhang_fk from kehoachbanhang_ct where kehoachbanhang_fk = '" + this.tbhId + "') ";
			}
			else if(this.ddkdId.trim().length() > 0)
			{
				query += " and pk_seq in ( select khachhang_fk from kehoachbanhang_ct where kehoachbanhang_fk in (select pk_seq from kehoachbanhang where ddkd_fk = '" + this.ddkdId + "') ) ";
			}
			
			System.out.println("[Bandott.init] toa do center dau query = " + query);
			
			ResultSet rsCenter = db.get(query);
			if(rsCenter != null)
			{
				try 
				{
					if(rsCenter.next())
					{
						this.trungtam = rsCenter.getString("trungtam");
					}
					rsCenter.close();
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
					System.out.println("115.Exception: " + e.getMessage());
				}
			}
			

			
		
		}
		
		
		
		
		this.DvkdRs =  db.get("select pk_seq,donvikinhdoanh from donvikinhdoanh where trangthai = 1 ");
		this.ma_tenkhRs =  db.get("select pk_seq,ten from khachhang where trangthai = 1 ");
		
	}
	
	public void initLoTrinhOnline()
	{

		
		try
		{
			if(this.ngay.length() <= 0)
				this.ngay = this.getDateTime();
			

			String			sql = "select phanloai,LOAI from nhanvien where pk_seq=" + this.userId;
			ResultSet rs = this.db.get(sql);
			if (rs != null)
			{
				if (rs.next())
				{
					this.phanloai = rs.getString("phanloai");
					loaiNv= rs.getString("LOAI")==null?"":rs.getString("LOAI");

					if (rs.getString("phanloai").equals("1")  )
					{
						this.nppId = Ult.getIdNhapp(this.userId);
					}
					rs.close();
				}
			}
			//System.out.println("sql : " + sql);
		} catch (Exception er)
		{

		}
		
		/*String query="select pk_Seq,ten from tinhthanh where 1=1 ";;
		if(this.vungId.length()>0)
		{
			query+=" and vung_fk='"+this.vungId+"' ";
		}		
		if(this.phanloai.equals("2")&& !loaiNv.equals("3"))
			{
			 query+= " and pk_Seq in " + Ult.Quyen_TinhThanh(userId)+"";
			}	
		this.ttRs=this.db.get(query);*/
		
		

		String quyenvung = "";
		 String quyenkhuvuc = "";
		 if(this.phanloai.equals("2") )
		 {
			 quyenkhuvuc = " and pk_seq in (select khuvuc_fk from nhaphanphoi where pk_seq in "+util.quyen_npp(this.userId)+") ";
			 quyenvung = " and pk_seq in ( select vung_fk from khuvuc where pk_seq in (select khuvuc_fk from nhaphanphoi where pk_seq in "+util.quyen_npp(this.userId)+")  )";
			 
			 
		 }
		
		String query="select pk_seq, ten from vung where trangthai = '1' " + quyenvung;
		
		this.vungList = db.get(query);
		
		query = "select count(*) as sonv from daidienkinhdoanh where trangthai = 1 ";
		ResultSet rs = db.get(query);
		try {
			if(rs.next())
			{
				this.sonvbh = rs.getString("sonv");
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		query = "select COUNT(distinct ddkd_fk) as so from DDKD_VITRI  where CONVERT(varchar(10),THOIDIEM,120) = '"+this.ngay+"'  ";
		 rs = db.get(query);
		try {
			if(rs.next())
			{
				this.sonvbhol = rs.getString("so");
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		
		query =" select a.pk_seq, a.ten as ten from khuvuc a where 1 = 1 " + quyenkhuvuc;
		 if(this.vungId.trim().length() > 0)
			 query += " and  a.vung_fk = '" + this.vungId.trim() + "' ";
		this.kvList = db.get(query	);
		
		String sqlNpp = "select pk_seq, ten from nhaphanphoi where trangthai = '1' ";
		
		 if(this.phanloai.equals("2"))
		 {
			 sqlNpp+= " and pk_Seq in " + Ult.quyen_npp(userId)+"";
		 }
		
			 if(this.vungId.trim().length() > 0)
				 sqlNpp += " and khuvuc_fk in (select pk_seq from khuvuc where vung_fk = '" + this.vungId.trim() + "') ";
			
		this.nppList = db.get(sqlNpp);
		
		
		if(this.nppId.trim().length() > 0)
		{
			// lay' RS trinh duoc vien
			query = "select pk_seq as ddkdId, ten as ddkdTen from daidienkinhdoanh a where trangthai=1 	  -- " + 
					 "\n and   a.npp_fk = '"+this.nppId+"'   -- " + 
					 "\n 	"; 
								
			this.ddkdList = db.get(query);
			System.out.println("ddkdRs =" + query);
		}
		
		// lay Rs nhan vien
		if(this.nppId.trim().length() > 0)
		{
			query =  "\n select distinct nv.PK_SEQ -- " + 
					 "\n 	, case when ASM_FK IS not null then 'ASM' -- " + 
					 "\n 		when BM_FK IS not null then 'BM' -- " + 
					 "\n 		when GSBH_FK IS not null then 'PTT' end -- " + 
					 "\n 	   + ' ' +nv.ten as TEN -- " + 
					 "\n from  NHANVIEN nv   -- " + 
					 "\n WHERE  nv.TRANGTHAI = 1 and ( GSBH_FK is not null ) -- " + 
					// "\n and exists ( select 1 from  NHANVIEN_VITRI where NHANVIEN_FK = nv.PK_SEQ ) -- " +  // and NGAY = '"+this.ngay+"'
					 "\n AND nv.PK_SEQ IN (SELECT NHANVIEN_FK FROM PHAMVIHOATDONG WHERE NPP_FK IN  -- " + 
					 "\n ( SELECT PK_SEQ FROM NHAPHANPHOI WHERE TRANGTHAI = 1  -- " + 
					 "\n   and PK_SEQ = '"+this.nppId+"' )) ";
			System.out.println("[Bandott.init] 1.get nhanvien RS: " + query);
			this.nvList = this.db.get(query);
		}
		
		
		if(this.nppId.length() > 0 &&!action.equals(""))
		{
			String sql = "";
			if(action.equals("viewBd") && this.ddkdId.trim().length() >0 )
			{
				 sql = "declare @ngay char(10) declare @ddkdId numeric(18,0) -- " + 
				 "\n set @ngay = '"+this.ngay+"' set @ddkdId = '"+this.ddkdId+"' -- " + 
				 "\n select td.lat , td.long , td.stt ,td.khoangcach, TYPE as isVT -- " + 
				 "\n 		, case when kh.PK_SEQ IS not null then isnull(kh.TEN,'') + ' - ' + isnull( kh.CHUCUAHIEU,'')+ ' - ' + isnull(kh.DIACHI,'')  else '' end as thongtinkh -- " + 
				 "\n		,td.thoidiem	"+		
				 "\n from DDKD_VITRI td  -- " + 
				 "\n left join KHACHHANG kh on kh.PK_SEQ = td.khachhang_fk -- " + 
				 "\n where td.ddkd_fk = @ddkdId and  NGAY = @ngay and td.lat is not null and td.long is not null and ( isnull(td.khoangcach,0) >10 or STT = (select top 1 STT from  DDKD_VITRI where  NGAY = td.NGAY and DDKD_FK =td.DDKD_FK and LAT is not null and LONG is not null  ) )"+
				 "\n order by td.thoidiem";	
				
			}
			else if(action.equals("viewBdTT") && this.nvId.trim().length() >0 )
			{
				sql = "declare @ngayNv char(10) declare @nvId numeric(18,0)  -- " + 
					 "\n set @ngayNv = '"+this.ngay+"' set @nvId = '"+this.nvId+"'  -- " + 
					 "\n select td.lat , td.long , td.stt ,  td.khoangcach, 0 as isVT  -- " + 
					 "\n 	, td.diachi as thongtinkh  -- " + 
					 "\n		,td.thoidiem	-- " + 	
					 "\n from NHANVIEN_VITRI td   -- " + 
					 "\n where NHANVIEN_FK = @nvId and  NGAY = @ngayNv and td.lat is not null and td.long is not null and ( isnull(td.khoangcach,0) >10 or STT = (select top 1 STT from  DDKD_VITRI where  NGAY = td.NGAY and NHANVIEN_FK =td.NHANVIEN_FK and LAT is not null and LONG is not null  ) ) "+
					 "\n order by td.thoidiem ";
			}
			if(!sql.equals(""))
			{
				System.out.println("[Bandott.init] 1.Khoi tao LoTrinh online daidienkinhdoanh: " + sql);
				this.khList = db.get(sql);
			}
		}
							
	}
	
	public void initLoTrinhOnlineNPP()
	{

		
		try
		{
			if(this.ngay.length() <= 0)
				this.ngay = this.getDateTime();
			

			String			sql = "select phanloai,LOAI from nhanvien where pk_seq=" + this.userId;
			ResultSet rs = this.db.get(sql);
			if (rs != null)
			{
				if (rs.next())
				{
					this.phanloai = rs.getString("phanloai");
					loaiNv= rs.getString("LOAI")==null?"":rs.getString("LOAI");

					if (rs.getString("phanloai").equals("1")||( this.phanloai.equals("2")   && loaiNv.equals("3")   )  )
					{
						;
					}
					rs.close();
				}
			}
			//System.out.println("sql : " + sql);
		} catch (Exception er)
		{

		}
		
		
		
		

		
		String query = "";
		
		
		String sqlNpp = "select pk_seq, ten from nhaphanphoi where trangthai = '1' ";
		
		 if(this.phanloai.equals("2")&& !loaiNv.equals("3"))
			{
			 sqlNpp+= " and pk_Seq in " + Ult.quyen_npp(userId)+"";
			}else
			{
				sqlNpp+= " and pk_Seq =" + util.getIdNhapp(this.userId);
			}
								 		
		this.nppList = db.get(sqlNpp);
		
		
		if(this.nppId.trim().length() > 0)
		{
			// lay' RS trinh duoc vien
			query = "select pk_seq as ddkdId, ten as ddkdTen from daidienkinhdoanh a where trangthai=1 	  -- " + 
					 "\n and a.npp_fk = '"+this.nppId+"'   -- " + 
					 "\n and	 exists (select 1 from DDKD_VITRI where DDKD_FK =a.pk_Seq  )	"; //and NGAY = '"+this.ngay+"'
								
			this.ddkdList = db.get(query);
			System.out.println("ddkdRs =" + query);
		}
		

		
		
		if(this.nppId.length() > 0 &&!action.equals(""))
		{
			String sql = "";
			if(action.equals("viewBd") && this.ddkdId.trim().length() >0 )
			{
				 sql = "declare @ngay char(10) declare @ddkdId numeric(18,0) -- " + 
				 "\n set @ngay = '"+this.ngay+"' set @ddkdId = '"+this.ddkdId+"' -- " + 
				 "\n select td.lat , td.long , td.stt ,td.khoangcach, TYPE as isVT -- " + 
				 "\n 		, case when kh.PK_SEQ IS not null then isnull(kh.TEN,'') + ' - ' + isnull( kh.CHUCUAHIEU,'')+ ' - ' + isnull(kh.DIACHI,'')  else '' end as thongtinkh -- " + 
				 "\n		,td.thoidiem	"+		
				 "\n from DDKD_VITRI td  -- " + 
				 "\n left join KHACHHANG kh on kh.PK_SEQ = td.khachhang_fk -- " + 
				 "\n where ddkd_fk = @ddkdId and  NGAY = @ngay and td.lat is not null and td.long is not null and ( isnull(td.khoangcach,0) >10 or STT = (select top 1 STT from  DDKD_VITRI where  NGAY = td.NGAY and DDKD_FK =td.DDKD_FK and LAT is not null and LONG is not null  ) )";	
				
			}			
			if(!sql.equals(""))
			{
				System.out.println("[Bandott.init] 1.Khoi tao LoTrinh online daidienkinhdoanh: " + sql);
				this.khList = db.get(sql);
			}
		}
							
	}
	
	public void createRS() 
	{
		this.ddkdList = db.get("select pk_seq as ddkdId, ten as ddkdTen from daidienkinhdoanh where npp_fk = '" + this.nppId + "'");
		this.tbhList = db.get("select pk_seq as tbhId, diengiai as tbhTen from tuyenbanhang where ddkd_fk = '" + this.ddkdId + "'");
		
		System.out.print("select pk_seq as tbhId, diengiai as tbhTen from tuyenbanhang where ddkd_fk = '" + this.ddkdId + "'");
		
		if(ddkdId.equals(""))
		{
			String sql = "select ten as khTen, diachi, dienthoai as dienthoai, lat, long from khachhang where a.npp_fk = '" + this.nppId + "'";
			this.khList = db.get(sql);
			
			sql = "select distinct a.ten as khTen, a.diachi, a.dienthoai as dienthoai, b.lat, b.long from khachhang a inner join ddkd_khachhang b on a.pk_seq = b.khachhang_fk where a.npp_fk = '" + this.nppId + "'";
			this.khSelected = db.get(sql + " and replace(convert(nvarchar(10), thoidiem, 102), '.', '-') = '" + this.ngay + "' ");
		}
		else
		{
			String query = "select a.ten as khTen, a.diachi, a.dienthoai as dienthoai, a.lat, a.long from khachhang a inner join khachhang_tuyenbh b on a.pk_seq = b.pk_seq inner join tuyenbanhang c on b.tbh_fk = c.pk_seq ";
			query = query +	" where c.ddkd_fk = '" + this.ddkdId + "'";
			
			if(this.tbhId.length() > 0)
			{
				query = query + " and b.tbh_fk = '" + this.tbhId + "'";
			}
			
			query = query + " and a.pk_seq in (select distinct khachhang_fk from ddkd_khachhang where replace(convert(nvarchar(10), thoidiem, 102), '.', '-') = '" + this.ngay + "')";
			System.out.println("1.Khoi tao khach hang Selected: " + query);
			this.khSelected = db.get(query);
			
			query = query + " and a.pk_seq not in (select distinct khachhang_fk from ddkd_khachhang where replace(convert(nvarchar(10), thoidiem, 102), '.', '-') = '" + this.ngay + "')";
			System.out.println("2.Khoi tao khach hang chua Selected: " + query);
			this.khList = db.get(query);
		}
	}
	
	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	public void DBclose() 
	{
		try 
		{
			if(this.vungList != null) {
				this.vungList.close();
			}
			if(this.kvList != null) {
				this.kvList.close();
			}
			if(this.cttbList != null) {
				this.cttbList.close();
			}
			if(this.ddkdList != null) {
				this.ddkdList.close();
			}
			if(this.khList != null) {
				this.khList.close();
			}
			if(this.khSelected != null) {
				this.khSelected.close();
			}
			if(this.tbhList != null) {
				this.tbhList.close();
			}
			if(this.db != null) {
				this.db.shutDown();
			}
			
		} catch (Exception e) {}
	}

	public String getAddress() 
	{
		return this.address;
	}

	public void setAddress(String address) 
	{
		this.address = address;
	}

	public String getTrungtam() 
	{
		return this.trungtam;
	}

	public void setTrungtam(String trungtam) 
	{
		this.trungtam = trungtam;
	}

	public ResultSet getKhKhongDSTrongThang()
	{
		return this.khKocodoanhso;
	}

	public void setKhKhongDSTrongThang(ResultSet KhKoRs) 
	{
		this.khKocodoanhso = KhKoRs;
	}

	public ResultSet getDSTbThang() 
	{
		return this.dstbKhachhang;
	}

	public void getDSTbThang(ResultSet dstb3Thang) 
	{
		this.dstbKhachhang = dstb3Thang;
	}
	
	private String LastDayOfMonth(int month, int year) 
    {
        String ngay = "";
        switch (month)
        {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                {
                    ngay = "31";
                    break;
                }
            case 4:
            case 6:
            case 9:
            case 11:
                {
                    ngay = "30";
                    break;
                }
            case 2:
                {
                    if (year % 4 == 0)
                        ngay = "29";
                    else
                        ngay = "28";
                    break;
                }
        }

        return ngay;
    } 
	
	private int Songaytrongthang(int month, int year) 
    {
        int ngay = 0;
        switch (month)
        {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                {
                    ngay = 31;
                    break;
                }
            case 4:
            case 6:
            case 9:
            case 11:
                {
                    ngay = 30;
                    break;
                }
            case 2:
                {
                    if (year % 4 == 0)
                        ngay = 29;
                    else
                        ngay = 28;
                    break;
                }
        }

        return ngay;
    }

	public String getDenngay() 
	{
		return this.denngay;
	}

	public void setDenngay(String denngay)
	{
		this.denngay = denngay;
	}

	
	public ResultSet getVungRs() {
		
		return this.vungList;
	}

	
	public void setVungRs(ResultSet vungRs) {
		
		this.vungList = vungRs;
	}

	
	public String getVungId() {
		
		return this.vungId;
	}

	
	public void setVungId(String vungId) {
		
		this.vungId = vungId;
	}

	
	public ResultSet getKvRs() {
		
		return this.kvList;
	}

	
	public void setKvRs(ResultSet kvRs) {
		
		this.kvList = kvRs;
	}

	
	public String getkvId() {
		
		return this.kvId;
	}

	
	public void setKvId(String kvId) {
		
		this.kvId = kvId;
	}

	
	public ResultSet getNppRs() {
		
		return this.nppList;
	}

	
	public void setNppRs(ResultSet nppRs) {
		
		this.nppList = nppRs;
	}

	
	public String getNppId() {
		
		return this.nppId;
	}

	
	public void setNppId(String nppId) {
		
		this.nppId = nppId;
	}

	public void initTB() 
	{
		
		try
		{

			String			sql = "select phanloai,LOAI from nhanvien where pk_seq=" + this.userId;
			ResultSet rs = this.db.get(sql);
			if (rs != null)
			{
				if (rs.next())
				{
					this.phanloai = rs.getString("phanloai");
					loaiNv= rs.getString("LOAI")==null?"":rs.getString("LOAI");

					if (rs.getString("phanloai").equals("1")||( this.phanloai.equals("2")   && loaiNv.equals("3")   )  )
					{
						this.nppId = Ult.getIdNhapp(this.userId);
					}
					rs.close();
				}
			}
			//System.out.println("sql : " + sql);
		} catch (Exception er)
		{

		}
		String quyenvung = "";
		 String quyenkhuvuc = "";
		 if(this.phanloai.equals("2") )
		 {
			 if(this.loaiNv.equals("2"))
			 {
				 quyenvung = " and pk_seq in (select distinct kv.VUNG_FK from ASM_KHUVUC a inner join KHUVUC kv on a.KHUVUC_FK = kv.PK_SEQ where a.ASM_FK = (select top(1) asm_fk from NHANVIEN where PK_SEQ ='"+userId+"'  )  )";
				 
				 quyenkhuvuc = " and pk_seq in (select distinct kv.pk_seq from ASM_KHUVUC a inner join KHUVUC kv on a.KHUVUC_FK = kv.PK_SEQ where a.ASM_FK = (select top(1) asm_fk from NHANVIEN where PK_SEQ ='"+userId+"'  )  )";
			 }
			 
			 if(this.loaiNv.equals("3"))
			 {
				 quyenvung = " and pk_seq in (select distinct kv.VUNG_FK from GSBH_KHUVUC a inner join KHUVUC kv on a.KHUVUC_FK = kv.PK_SEQ where a.GSBH_FK = (select top(1) gsbh_fk from NHANVIEN where PK_SEQ ='"+userId+"'  )  )";
				 
				 quyenkhuvuc = " and pk_seq in (select distinct kv.pk_seq from GSBH_KHUVUC a inner join KHUVUC kv on a.KHUVUC_FK = kv.PK_SEQ where a.GSBH_FK = (select top(1) gsbh_fk from NHANVIEN where PK_SEQ ='"+userId+"'  )  )";
			 }
		 }
		this.vungList = db.get("select pk_seq, ten from vung where trangthai = '1' "+quyenvung);
		
		if(this.vungId.trim().length() > 0)
		{
			this.kvList = db.get("select pk_seq, ten from khuvuc where vung_fk = '" + this.vungId + "' and trangthai = '1'"+quyenkhuvuc);
		}
		else
		{
			this.kvList = db.get("select pk_seq, ten from khuvuc where trangthai = '1'"+quyenkhuvuc);
		}
		
		String sqlNpp = "select pk_seq, ten from nhaphanphoi where trangthai = '1' and pk_seq in "+ util.quyen_npp(this.userId);
		if(this.kvId.trim().length() > 0)
			sqlNpp += " and khuvuc_fk = '" + this.kvId.trim() + "' ";
		if(this.vungId.trim().length() > 0)
			sqlNpp += " and khuvuc_fk in (select pk_seq from khuvuc where vung_fk = '" + this.vungId.trim() + "') ";
		System.out.println("List NPP: "+sqlNpp);
		this.nppList = db.get(sqlNpp);
		
		String tbQuery = "";
		
		if(this.nppId.trim().length() > 0)
		{
			tbQuery = "select b.pk_seq, b.scheme from dangkytrungbay a inner join cttrungbay b on a.cttrungbay_fk = b.pk_seq " +
			 	  	"where npp_fk = '" + this.nppId + "' and a.trangthai != 2 ";
		}
		else
		{
			tbQuery = "select pk_seq, scheme from cttrungbay";
		}
		
		System.out.println("1.Lay CTTB: " + tbQuery);
		this.cttbList = db.get(tbQuery);
		
		if(this.nppId.trim().length() > 0)
		{
			this.ddkdList = db.get("select pk_seq as ddkdId, ten as ddkdTen from daidienkinhdoanh where npp_fk = '" + this.nppId + "'");
			
			if(this.ddkdId.trim().length() > 0)
			{
				this.tbhList = db.get("select pk_seq as tbhId, diengiai as tbhTen from tuyenbanhang where ddkd_fk = '" + this.ddkdId + "'");
			}
			
			
			if(this.cttbId.trim().length() > 0)
			{

			    String thang = getMonth();
			    String nam = getYear();
			    
				String sql = "select kh.pk_seq as khId, kh.ten as khTen, isnull(kh.dienthoai, 'NA') as dienthoai, kh.lat, kh.long, isnull(dk.ghiChu, '') as ghiChu,   " +
							"isnull( ( select ImageFilePath from DKTRUNGBAY_KHACHHANG_CHITIET where loai = '0' and KHACHHANG_FK = kh.PK_SEQ and DKTRUNGBAY_FK = dk.DKTRUNGBAY_FK  and thang = '"+thang+"' and nam = '"+nam+"') , '') as hinhBenTrai, " +
							"isnull( ( select ImageFilePath from DKTRUNGBAY_KHACHHANG_CHITIET where loai = '2' and KHACHHANG_FK = kh.PK_SEQ and DKTRUNGBAY_FK = dk.DKTRUNGBAY_FK  and thang = '"+thang+"' and nam = '"+nam+"') , '') as hinhBenPhai, " +
							"isnull( ( select ImageFilePath from DKTRUNGBAY_KHACHHANG_CHITIET where loai = '1' and KHACHHANG_FK = kh.PK_SEQ and DKTRUNGBAY_FK = dk.DKTRUNGBAY_FK  and thang = '"+thang+"' and nam = '"+nam+"') , '') as hinhGiua " +
				
							 "from KhachHang kh  left join DKTrungBay_KhachHang dk on kh.pk_seq = dk.khachhang_fk  " +
							 "where kh.npp_fk = '" + this.nppId + "' and lat is not null and long is not null   " +
							 			"and dk.dktrungbay_fk in ( select pk_seq from dangkytrungbay where cttrungbay_fk = '" + this.cttbId + "' and trangthai != 2 and npp_fk = '" + this.nppId + "' )   ";
				
				if(this.ddkdId.trim().length() > 0)
				{
					sql += " and kh.pk_seq in ( select khachhang_fk from khachhang_tuyenbh where tbh_fk in (select pk_seq from tuyenbanhang where ddkd_fk = '" + this.ddkdId + "') ) ";
				}
				
				if(this.tbhId.trim().length() > 0)
				{
					sql += " and kh.pk_seq in ( select khachhang_fk from khachhang_tuyenbh where tbh_fk = '" + this.tbhId + "' ) ";
				}
				
				
				//sql = "select trungbay.* from ( " + sql + " ) trungbay where trungbay.hinhBenTrai != '' or trungbay.hinhBenPhai != '' or trungbay.hinhGiua != ''";
				
				
				System.out.println("1.Khoi tao khach hang trung bay: " + sql);
				this.khList = db.get(sql);
				
	
				//Khoi tao toa do Center dau
				String query = "select top(1) pk_seq, lat + ',' + long  as trungtam " +
						"from KhachHang where trangthai = '1' and lat is not null and long is not null and npp_fk = '" + this.nppId + "'";
				
				if(this.ddkdId.trim().length() > 0)
				{
					query += " and pk_seq in ( select khachhang_fk from khachhang_tuyenbh where tbh_fk in (select pk_seq from tuyenbanhang where ddkd_fk = '" + this.ddkdId + "') ) ";
				}
				
				if(this.tbhId.trim().length() > 0)
				{
					query += " and pk_seq in (select khachhang_fk from khachhang_tuyenbh where tbh_fk = '" + this.tbhId + "') ";
				}
				
				if(this.cttbId.trim().length() > 0)
				{
					sql += " and pk_seq in ( select khachhang_fk from dktrungbay_khachhang where dktrungbay_fk in ( select pk_seq from dangkytrungbay where npp_fk = '" + this.nppId + "' and trangthai != '2' and cttrungbay_fk = '" + this.cttbId + "' )  ) ";
				}
				
				ResultSet rsCenter = db.get(query);
				if(rsCenter != null)
				{
					try 
					{
						if(rsCenter.next())
						{
							this.trungtam = rsCenter.getString("trungtam");
						}
						rsCenter.close();
					} 
					catch (Exception e) 
					{
						System.out.println("115.Exception: " + e.getMessage());
					}
				}
			}
		
		}
	}
	
	public void initToaDo()  {

		String query = "", gsbh_fk = "";
		String queryma_ten = "";
		try
		{
			
					String sql1 = "\n select  isnull(kh.CHUCUAHIEU,'NA') as CHUCUAHIEU , kh.pk_seq as pk_seq, " +
					"\n kh.SMARTID as mafast, kh.smartid as khId, kh.ten as khTen, isnull(kh.dienthoai, 'NA') as dienthoai, " +
					"\n kh.lat as lat, kh.long as lon,isnull(kh.diachi, 'NA') as diachi " +
					"\n from KhachHang kh " +
					"\n where 1 = 1 and (lat is not null or long is not null) and ismt = '0'   ";						 			

					if(this.mafast != null && this.mafast.length()>0){
						sql1 +=" and kh.SMARTID like '%"+this.mafast+"%'"; 
					}

					if(this.ddkdId != null && this.ddkdId.trim().length() > 0)
					{
						sql1 += " and kh.ddkd_fkip = '" + this.ddkdId + "'  ";
					}
					if(this.ma_tenkh != null && this.ma_tenkh.length()>0){
						sql1 +=" and (kh.smartid like '%"+this.ma_tenkh+"%' or kh.ten like '%"+this.ma_tenkh+"%')"; 
					}
					
					sql1 += " order by kh.ten ";
					System.out.println("[Bandott.initToado] khachhangIP sql = " + sql1);
					this.khList = db.get(sql1);
			
			query = "select pk_seq,donvikinhdoanh from donvikinhdoanh where trangthai = 1";
			this.DvkdRs =  db.get(query);
			queryma_ten = "select pk_seq, ten from KHACHHANG where trangthai = 1";
			this.ma_tenkhRs =  db.get(queryma_ten);
			String sql = "\n select phanloai,LOAI,gsbh_fk from nhanvien where pk_seq=" + this.userId;
			ResultSet rs = this.db.get(sql);
			if (rs != null)
			{
				if (rs.next())
				{
					gsbh_fk = rs.getString("gsbh_fk");
					this.phanloai = rs.getString("phanloai");
					loaiNv= rs.getString("LOAI")==null?"":rs.getString("LOAI");


					if (rs.getString("phanloai").equals("1") || ( this.phanloai.equals("2") && loaiNv.equals("3")))
					{
						this.nppId = Ult.getIdNhapp(this.userId);
					}
					rs.close();
				}
			}
			if (gsbh_fk != null && gsbh_fk.length() > 0) {
				this.isGSBH = "1";
			}
			System.out.println("Get phân loại: "+sql);
		} catch (Exception er)
		{
			er.printStackTrace();
		}
		
		String quyenvung = "";
		String quyenkhuvuc = "";
		if(this.phanloai.equals("2") )
		{
			if(this.loaiNv.equals("2"))
			{
				quyenvung = "\n and pk_seq in (select distinct kv.VUNG_FK from ASM_KHUVUC a inner join KHUVUC kv on a.KHUVUC_FK = kv.PK_SEQ where a.ASM_FK = (select top(1) asm_fk from NHANVIEN where PK_SEQ ='"+userId+"'  )  )";

				quyenkhuvuc = "\n and pk_seq in (select distinct kv.pk_seq from ASM_KHUVUC a inner join KHUVUC kv on a.KHUVUC_FK = kv.PK_SEQ where a.ASM_FK = (select top(1) asm_fk from NHANVIEN where PK_SEQ ='"+userId+"'  )  )";
			}

			if(this.loaiNv.equals("3"))
			{
				quyenvung = "\n and pk_seq in (select distinct kv.VUNG_FK from GSBH_KHUVUC a inner join KHUVUC kv on a.KHUVUC_FK = kv.PK_SEQ where a.GSBH_FK = (select top(1) gsbh_fk from NHANVIEN where PK_SEQ ='"+userId+"'  )  )";

				quyenkhuvuc = "\n and pk_seq in (select distinct kv.pk_seq from GSBH_KHUVUC a inner join KHUVUC kv on a.KHUVUC_FK = kv.PK_SEQ where a.GSBH_FK = (select top(1) gsbh_fk from NHANVIEN where PK_SEQ ='"+userId+"'  )  )";
			}
		}

		System.out.println("view = khachhangToado");
		this.vungList = db.get("select pk_seq, ten from vung where trangthai = '1'"+quyenvung);

		if(this.vungId.trim().length() > 0)
		{
			query = "select pk_seq, ten from khuvuc where vung_fk = '" + this.vungId + "' and trangthai = '1'"+quyenkhuvuc;
			this.kvList = db.get(query);
		}
		else
		{
			query = "select pk_seq, ten from khuvuc where trangthai = '1'"+quyenkhuvuc;
			this.kvList = db.get(query);
		}

		String sqlNpp = "\n select pk_seq, ten from nhaphanphoi where trangthai = '1' ";
		if(this.kvId != null && this.kvId.trim().length() > 0)
			sqlNpp += "\n and khuvuc_fk = '" + this.kvId.trim() + "' ";
		if(this.vungId != null && this.vungId.trim().length() > 0)
			sqlNpp += "\n and khuvuc_fk in (select pk_seq from khuvuc where vung_fk = '" + this.vungId.trim() + "') ";
		this.nppList = db.get(sqlNpp);

		System.out.println("NppId: "+this.nppId);
		if(this.nppId != null && this.nppId.trim().length() > 0)
		{
			System.out.println("nppId: " + this.nppId);
			query = "\n select pk_seq as ddkdId, ten as ddkdTen from daidienkinhdoanh where npp_fk = "+this.nppId;
			this.ddkdList = db.get(query);

			if(this.ddkdId != null && this.ddkdId.trim().length() > 0)
			{
				System.out.println("ddkdId: "+this.ddkdId);
				query = "select pk_seq as tbhId, diengiai as tbhTen from tuyenbanhang where ddkd_fk = '" + this.ddkdId + "'";
				this.tbhList = db.get(query);

				if(this.tbhId != null && this.tbhId.trim().length() > 0)
					System.out.println("TBH: "+this.tbhId);

				
				String conditionTT = "";
				String sql = "\n select  isnull(kh.CHUCUAHIEU,'NA') as CHUCUAHIEU  , kh.pk_seq as pk_seq, kh.SMARTID as mafast, kh.smartid as khId, kh.ten as khTen, isnull(kh.dienthoai, 'NA') as dienthoai, kh.lat as lat, kh.long as lon,isnull(kh.diachi, 'NA') as diachi " +
				"\n from KhachHang kh " +
				"\n where 1=1 and (lat is not null or long is not null) "+conditionTT;		
				
				if (isTT != null && isTT.equals("1")) {
					conditionTT += "\n and kh.npp_fk = " + this.nppId;
				}

				if(this.mafast != null && this.mafast.length()>0){
					sql += "\n and kh.SMARTID like '%"+this.mafast+"%'"; 
				}
				if(this.ma_tenkh != null && this.ma_tenkh.length()>0){
					sql +=" and (kh.smartid like '%"+this.ma_tenkh+"%' or kh.ten like '%"+this.ma_tenkh+"%')"; 
				}
				if(this.ddkdId != null && this.ddkdId.trim().length() > 0)
				{
					sql += "\n and kh.pk_seq in ( select khachhang_fk from khachhang_tuyenbh where tbh_fk in (select pk_seq from tuyenbanhang where ddkd_fk = '" + this.ddkdId + "') ) ";
				}

				if(this.tbhId != null && this.tbhId.trim().length() > 0)
				{
					sql += "\n and kh.pk_seq in ( select khachhang_fk from khachhang_tuyenbh where tbh_fk = '" + this.tbhId + "' ) ";
				}
				sql += "\n order by kh.ten ";

				System.out.println("KHRS: "+ sql);
				this.khList = db.get(sql);
			}
		}
		else if (this.isTT != null && this.isTT.equals("1")) { //Trung tâm
			
			System.out.println("isTT: " + this.isTT);
			
			if(this.nppId.length() == 0) {
				query = "\n select pk_seq as ddkdId, ten as ddkdTen from daidienkinhdoanh where trangthai=1 ";			
			}
			else
				query = "\n select pk_seq as ddkdId, ten as ddkdTen from daidienkinhdoanh where npp_fk = "+this.nppId;
			this.ddkdList = db.get(query);


			if(this.ddkdId != null && this.ddkdId.trim().length() > 0)
			{
				System.out.println("ddkdId: "+this.ddkdId);
				query = "select pk_seq as tbhId, diengiai as tbhTen from tuyenbanhang where ddkd_fk = '" + this.ddkdId + "'";
				this.tbhList = db.get(query);

				if(this.tbhId != null && this.tbhId.trim().length() > 0)
					System.out.println("TBH: "+this.tbhId);

				
				String conditionTT = "";
				String sql = "\n select  isnull(kh.CHUCUAHIEU,'NA') as CHUCUAHIEU  , kh.pk_seq as pk_seq, kh.SMARTID as mafast, kh.smartid as khId, kh.ten as khTen, isnull(kh.dienthoai, 'NA') as dienthoai, kh.lat as lat, kh.long as lon,isnull(kh.diachi, 'NA') as diachi " +
				"\n from KhachHang kh " +
				"\n where 1=1 and (lat is not null or long is not null) "+conditionTT;		
				
				if (isTT != null && isTT.equals("1")) {
					conditionTT += "\n and kh.npp_fk = " + this.nppId;
				}

				if(this.mafast != null && this.mafast.length()>0){
					sql += "\n and kh.SMARTID like '%"+this.mafast+"%'"; 
				}
				if(this.ma_tenkh != null && this.ma_tenkh.length()>0){
					sql +=" and (kh.smartid like '%"+this.ma_tenkh+"%' or kh.ten like '%"+this.ma_tenkh+"%')"; 
				}
				if(this.ddkdId != null && this.ddkdId.trim().length() > 0)
				{
					sql += "\n and kh.pk_seq in ( select khachhang_fk from khachhang_tuyenbh where tbh_fk in (select pk_seq from tuyenbanhang where ddkd_fk = '" + this.ddkdId + "') ) ";
				}

				if(this.tbhId != null && this.tbhId.trim().length() > 0)
				{
					sql += "\n and kh.pk_seq in ( select khachhang_fk from khachhang_tuyenbh where tbh_fk = '" + this.tbhId + "' ) ";
				}
				sql += "\n order by kh.ten ";

				System.out.println("KHRS: "+ sql);
				this.khList = db.get(sql);
			}
		
		}
		//if(this.Dvkdid != null && this.Dvkdid.equals("100069"))
		if(this.Dvkdid != null && this.Dvkdid!= "")
		{
			System.out.println("Dvkdid: " + this.Dvkdid);
			
			if(this.nppId.length() == 0) {
				query = "\n select pk_seq as ddkdId, ten as ddkdTen from daidienkinhdoanh where trangthai=1 ";			
			}
			else
				query = "\n select pk_seq as ddkdId, ten as ddkdTen from daidienkinhdoanh where npp_fk = "+this.nppId;
			this.ddkdList = db.get(query);

			String sql = "\n select  isnull(kh.CHUCUAHIEU,'NA') as CHUCUAHIEU , kh.pk_seq as pk_seq, " +
			"\n kh.SMARTID as mafast, kh.smartid as khId, kh.ten as khTen, isnull(kh.dienthoai, 'NA') as dienthoai, " +
			"\n kh.lat as lat, kh.long as lon,isnull(kh.diachi, 'NA') as diachi " +
			"\n from KhachHang kh " +
			"\n  INNER JOIN dbo.NHAPP_NHACC_DONVIKD ON NHAPP_NHACC_DONVIKD.NPP_FK = kh.NPP_FK "+
			"\n INNER JOIN dbo.NHACUNGCAP_DVKD ON NHACUNGCAP_DVKD.PK_SEQ = NHAPP_NHACC_DONVIKD.NCC_DVKD_FK "+
			"\n WHERE dbo.NHACUNGCAP_DVKD.DVKD_FK = '"+this.Dvkdid+"' "+ 
			"\n and (lat is not null or long is not null)   ";						 			

			if(this.mafast != null && this.mafast.length()>0){
				sql +=" and kh.SMARTID like '%"+this.mafast+"%'"; 
			}

			if(this.ddkdId != null && this.ddkdId.trim().length() > 0)
			{
				sql += " and kh.ddkd_fkip = '" + this.ddkdId + "'  ";
			}
			if(this.ma_tenkh != null && this.ma_tenkh.length()>0){
				sql +=" and (kh.smartid like '%"+this.ma_tenkh+"%' or kh.ten like '%"+this.ma_tenkh+"%')"; 
			}
			
			sql += " order by kh.ten ";
			System.out.println("[Bandott.initToado] khachhangIP sql = " + sql);
			this.khList = db.get(sql);
		}
	}
	
	
	public boolean xoaToaDoKh(String khId) 
	{
		try
		{
			db.getConnection().setAutoCommit(false);
			String sql = "UPDATE KHACHHANG SET nguoisua = "+this.userId+" , ngaysua ='"+this.getDateTime()+"' , LAT = NULL, LONG = NULL WHERE PK_SEQ = '" + khId + "'";
			System.out.println("[Bandott.xoaToaDoKh] sql = " + sql);
			if(!this.db.update(sql))
			{
				db.getConnection().rollback();
				return false;
			}
			sql = "delete ddkd_khachhang where convert(varchar(10),thoidiem,20) ='"+this.getDateTime()+"' and   khachhang_fk =" + khId;
			if(!this.db.update(sql))
			{
				db.getConnection().rollback();
				return false;
			}
			db.getConnection().commit();
			return true;

		}catch (Exception ex)
		{
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return false;
		}
	}

	public boolean xoaanhkh(String khId) 
	{
		try
		{
			db.getConnection().setAutoCommit(false);
			String sql = "UPDATE KHACHHANG SET nguoisua = "+this.userId+" , ngaysua ='"+this.getDateTime()+"' , anhcuahang = NULL WHERE PK_SEQ = '" + khId + "'";
			if(!this.db.update(sql))
			{
				db.getConnection().rollback();
				return false;
			}
			db.getConnection().commit();
			return true;

		}catch (Exception ex)
		{
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return false;
		}
	}

	
	
	public ResultSet getCttbRs()
	{
		return this.cttbList;
	}

	public void setCttbRs(ResultSet cttbRs)
	{
		this.cttbList = cttbRs;
	}

	public String getCttbId() 
	{
		return this.cttbId;
	}

	public void setCttbId(String cttbId)
	{
		this.cttbId = cttbId;
	}


	public void initTB_Excel() 
	{
		this.vungList = db.get("select pk_seq, ten from vung where trangthai = '1'");
		
		if(this.vungId.trim().length() > 0)
		{
			this.kvList = db.get("select pk_seq, ten from khuvuc where vung_fk = '" + this.vungId + "' and trangthai = '1'");
		}
		else
		{
			this.kvList = db.get("select pk_seq, ten from khuvuc where trangthai = '1'");
		}
		
		String sqlNpp = "select pk_seq, ten from nhaphanphoi where trangthai = '1' ";
		if(this.kvId.trim().length() > 0)
			sqlNpp += " and khuvuc_fk = '" + this.kvId.trim() + "' ";
		if(this.vungId.trim().length() > 0)
			sqlNpp += " and khuvuc_fk in (select pk_seq from khuvuc where vung_fk = '" + this.vungId.trim() + "') ";
		
		this.nppList = db.get(sqlNpp);
		
		String tbQuery = "";
		
		if(this.nppId.trim().length() > 0)
		{
			tbQuery = "select b.pk_seq, b.scheme from dangkytrungbay a inner join cttrungbay b on a.cttrungbay_fk = b.pk_seq " +
			 	  	"where npp_fk = '" + this.nppId + "' and a.trangthai != 2 ";
		}
		else
		{
			tbQuery = "select pk_seq, scheme from cttrungbay";
		}
		
		System.out.println("1.Lay CTTB: " + tbQuery);
		this.cttbList = db.get(tbQuery);
		
		if(this.nppId.trim().length() > 0)
		{
			this.ddkdList = db.get("select pk_seq as ddkdId, ten as ddkdTen from daidienkinhdoanh where npp_fk = '" + this.nppId + "'");
			
			if(this.ddkdId.trim().length() > 0)
			{
				this.tbhList = db.get("select pk_seq as tbhId, diengiai as tbhTen from tuyenbanhang where ddkd_fk = '" + this.ddkdId + "'");
			}
			
			
			if(this.cttbId.trim().length() > 0)
			{
				String sql = "select kh.pk_seq as khId, kh.ten as khTen, isnull(kh.dienthoai, 'NA') as dienthoai, kh.lat, kh.long, isnull(dk.ghiChu, '') as ghiChu, dk.ImageFilePath    " +
							 "from KhachHang kh  left join DKTrungBay_KhachHang dk on kh.pk_seq = dk.khachhang_fk  " +
							 "where kh.npp_fk = '" + this.nppId + "' and lat is not null and long is not null   " +
							 			"and dk.dktrungbay_fk in ( select pk_seq from dangkytrungbay where cttrungbay_fk = '" + this.cttbId + "' and trangthai != 2 and npp_fk = '" + this.nppId + "' )  and  dk.ImageFilePath is not null  ";
				
				if(this.ddkdId.trim().length() > 0)
				{
					sql += " and kh.pk_seq in ( select khachhang_fk from khachhang_tuyenbh where tbh_fk in (select pk_seq from tuyenbanhang where ddkd_fk = '" + this.ddkdId + "') ) ";
				}
				
				if(this.tbhId.trim().length() > 0)
				{
					sql += " and kh.pk_seq in ( select khachhang_fk from khachhang_tuyenbh where tbh_fk = '" + this.tbhId + "' ) ";
				}
				
				
				System.out.println("1.Khoi tao khach hang trung bay: " + sql);
				this.khList = db.get(sql);
				
	
				//Khoi tao toa do Center dau
				String query = "select top(1) pk_seq, lat + ',' + long  as trungtam " +
						"from KhachHang where trangthai = '1' and lat is not null and long is not null and npp_fk = '" + this.nppId + "'";
				
				if(this.ddkdId.trim().length() > 0)
				{
					query += " and pk_seq in ( select khachhang_fk from khachhang_tuyenbh where tbh_fk in (select pk_seq from tuyenbanhang where ddkd_fk = '" + this.ddkdId + "') ) ";
				}
				
				if(this.tbhId.trim().length() > 0)
				{
					query += " and pk_seq in (select khachhang_fk from khachhang_tuyenbh where tbh_fk = '" + this.tbhId + "') ";
				}
				
				if(this.cttbId.trim().length() > 0)
				{
					sql += " and pk_seq in ( select khachhang_fk from dktrungbay_khachhang where dktrungbay_fk in ( select pk_seq from dangkytrungbay where npp_fk = '" + this.nppId + "' and trangthai != '2' and cttrungbay_fk = '" + this.cttbId + "' )  ) ";
				}
				
				ResultSet rsCenter = db.get(query);
				if(rsCenter != null)
				{
					try 
					{
						if(rsCenter.next())
						{
							this.trungtam = rsCenter.getString("trungtam");
						}
						rsCenter.close();
					} 
					catch (Exception e) 
					{
						System.out.println("115.Exception: " + e.getMessage());
					}
				}
			}
		
		}
	}
	

	
	private String getMonth() {
		DateFormat dateFormat = new SimpleDateFormat("MM");
        Date date = new Date();
        return dateFormat.format(date);
	}
	
	private String getYear() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy");
        Date date = new Date();
        return dateFormat.format(date);
	}

	
	public String getMsg() {
		return this.msg;
	}

	
	public void setMsg(String msg) {
		this.msg = msg;
	}

	
	public String getKhachHangSi() {
		return this.khSi;
	}

	
	public void setKhachHangSi(String khSi) {
		this.khSi = khSi;
	}

	public String[] getLatcondition() {
		
		return this.latcondition;
	}

	
	public void setLatcondition(String[] latcondition) {
		
		this.latcondition = latcondition;
	}

	
	public String[] getLongconditon() {
		
		return this.longcondition;
	}

	
	public void setLongcondition(String[] longcondition) {
		
		this.longcondition = longcondition;
	}

	
	public ResultSet getInfoRs() {
		
		return this.infoRs;
	}

	
	public void setInfoRs(ResultSet infoRs) {
		
		this.infoRs = infoRs;
	}

	
	public ResultSet getDpNganhRs() {
		
		return this.dpNganhRs;
	}

	
	public void setDpNganhRs(ResultSet dpRs) {
		
		this.dpNganhRs = dpRs;
	}

	
	public ResultSet getInfoDetailRs() {
		
		return this.infoDetail;
	}

	
	public void setInfoDetailRs(ResultSet infoDetailRs) {
		
		this.infoDetail = infoDetailRs;
	}

	
	public ResultSet getKiemtkRs() {

		return this.kiemTKRs;
	}

	
	public void setKiemtkRs(ResultSet kiemtkRs) {
		
		this.kiemTKRs = kiemtkRs;
	}

	
	public String getMafast() {
		return this.mafast;
	}

	
	public void setMafast(String mafast) {
		this.mafast=mafast;
		
	}
	
	String loaiNv,phanloai;
	public String getLoaiNv()
  {
  	return loaiNv;
  }

	public void setLoaiNv(String loaiNv)
  {
  	this.loaiNv = loaiNv;
  }

	public String getPhanloai()
  {
  	return phanloai;
  }

	public void setPhanloai(String phanloai)
  {
  	this.phanloai = phanloai;
  }
	
	String ttId;
	ResultSet ttRs;
	public String getTtId()
  {
  	return ttId;
  }

	public void setTtId(String ttId)
  {
  	this.ttId = ttId;
  }

	public ResultSet getTtRs()
  {
  	return ttRs;
  }

	public void setTtRs(ResultSet ttRs)
  {
  	this.ttRs = ttRs;
  }
	
	public String action;
	public String getAction()
  {
  	return action;
  }

	public void setAction(String action)
  {
  	this.action = action;
  }
	
	
	public ResultSet getNvRs() 
	{
		return this.nvList;
	}

	public void setNvRs(ResultSet nvList) 
	{
		this.nvList = nvList;
	}

	public String getNvId() 
	{
		return this.nvId;
	}

	public void setNvId(String nvId) 
	{
		this.nvId = nvId;
	}

	
	public String getView() {
		
		return this.View;
	}

	
	public void setView(String View) {
		
		this.View = View;
	}

	
	
	public ResultSet getMa_tenkhRs() {
		return ma_tenkhRs;
	}

	public void setMa_tenkhRs(ResultSet ma_tenkhRs) {
		this.ma_tenkhRs = ma_tenkhRs;
	}

	public ResultSet getDvkdRs() {
		
		return this.DvkdRs;
	}

	
	public void setDvkdRs(ResultSet DvkdRs) {
		
		this.DvkdRs = DvkdRs;
	}

	
	public String getDvkdId() {
		
		return this.Dvkdid;
	}

	
	public void setDvkdId(String DvkdId) {
		
		this.Dvkdid = DvkdId;
	}

	
	public String getSoNVBH() {
		
		return this.sonvbh;
	}

	
	public String getSoNVBHOnline() {
		
		return this.sonvbhol;
	}
	
	
}
