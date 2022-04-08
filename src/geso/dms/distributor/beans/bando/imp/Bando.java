package geso.dms.distributor.beans.bando.imp;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import geso.dms.distributor.beans.bando.IBando;
import geso.dms.distributor.db.sql.dbutils;
import geso.dms.distributor.util.Utility;

public class Bando implements IBando 
{
	String KHACHHANGSI_ID = "100890";
	String userId;
	String nppId;
	String nppTen;
	String sitecode;
	
	String ddkdId;
	String tbhId;
	String ngay;
	String address;
	String trungtam;
	
	ResultSet ddkdList;
	ResultSet tbhList;
	
	ResultSet khSelected; //da vieng tham
	ResultSet khList; //chua vieng tham
	
	ResultSet dstbKhachhang;
	ResultSet khKocodoanhso;
	String khSi;
	String clId;
	ResultSet chungloaiRs;
	ResultSet dstbNganhhang;
	
	ResultSet vungRs;
	String vungId;
	ResultSet kvRs;
	String kvId;
	ResultSet nppRs;
	String nppLocId;

	ResultSet spRs;
	String spId;
	
	String view;
	String[] latcondition;
	String[] longcondition;
	String denngay;
	dbutils db;
	
	public Bando()
	{
		this.ddkdId = "";
		this.tbhId = "";
		
		//String[] dauthang = this.getDateTime().split("-");
		this.ngay = this.getDateTime();
		
		//this.ngay = this.getDateTime();
		this.address = "";
		this.trungtam = "";
		this.clId = "";
		
		this.vungId = "";
		this.kvId = "";
		this.nppLocId = "";
		this.spId = "";
		this.view = "";
		this.khSi = "0";
		this.denngay = this.getDateTime();
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
	
	private void getNppInfo()
	{		
		//Phien ban moi
		Utility util=new Utility();
		this.nppId=util.getIdNhapp(this.userId);
		this.nppTen=util.getTenNhaPP();
		this.sitecode=util.getSitecode();
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
	
	public void init() 
	{
		//db = new dbutils();
		this.getNppInfo();
		
		this.ddkdList = db.get("select pk_seq as ddkdId, ten as ddkdTen from daidienkinhdoanh where npp_fk = '" + this.nppId + "'");
		
		if(this.ddkdId.trim().length() > 0)
		{
			this.tbhList = db.get("select pk_seq as tbhId, diengiai as tbhTen from tuyenbanhang where ddkd_fk = '" + this.ddkdId + "'");
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
		
		/*String before3 = Integer.toString(Integer.parseInt(thang) - 3);
		if(before3.length() < 2)
			before3 = "0" + before3;*/
		
		String before1 = Integer.toString(Integer.parseInt(thang) - 1);
		if(before1.length() < 2)
			before1 = "0" + before1;
		
		String ngaycuoi = LastDayOfMonth(Integer.parseInt(thang) - 1, Integer.parseInt(now[0]));
		
		//String bd_truoc_3_thang = now[0] + "-" + before3 + "-01";
		String bd_truoc_1_thang = now[0] + "-" + before1 + "-" + "-01";
		String kt_truoc_1_thang = now[0] + "-" + before1 + "-" + ngaycuoi;
		
		String dauthang = now[0] + "-" + now[1] + "-01";
		
		/*int tongsongay = Songaytrongthang(Integer.parseInt(thang) - 1, Integer.parseInt(now[0])) 
						+ Songaytrongthang(Integer.parseInt(thang) - 2, Integer.parseInt(now[0])) 
						+ Songaytrongthang(Integer.parseInt(thang) - 3, Integer.parseInt(now[0]));*/
		int tongsongay = Songaytrongthang(Integer.parseInt(thang) - 1, Integer.parseInt(now[0]));
		tongsongay = (tongsongay - 4); //tru 4 chu nhat
		
		
		/*String sql = "select kh.pk_seq as khId, kh.ten as khTen, isnull(kh.dienthoai, 'NA') as dienthoai, kh.lat, kh.long,   " +
						"cast(isnull(doanhsoThangtruoc, 0) as numeric(18, 0)) as doanhsoThangtruoc ,   " +
						"cast( isnull(doanhsoTudauthang, 0) as numeric(18, 0)) as doanhsoTrongthang  " +
				"from KhachHang kh  " +
				"left join  " +
				"(   " +
					"select a.khachhang_fk as khId, sum(b.soluong * b.giamua) /  " + tongsongay + "  as doanhsoThangtruoc    " +
					"from donhang a inner join donhang_sanpham b on a.pk_seq = b.donhang_fk  " +
					"where a.khachhang_fk in ( select pk_seq from khachhang where npp_fk = '" + this.nppId + "' and lat is not null ) and a.trangthai = '1'   " +
						"and '" + bd_truoc_1_thang + "' <= a.ngaynhap and a.ngaynhap <= '" + kt_truoc_1_thang + "'   " +
					"group by a.khachhang_fk  " +
				")   " +
				"thangtruoc on kh.pk_seq = thangtruoc.khId left join   " +
				"(   " +
					"select a.khachhang_fk as khId, sum(b.soluong * b.giamua) / 1  as doanhsoTudauthang   " +
					"from donhang a inner join donhang_sanpham b on a.pk_seq = b.donhang_fk  " +
					"where a.khachhang_fk in (select pk_seq from khachhang where npp_fk = '" + this.nppId + "' and lat is not null ) and a.trangthai = '1'   " +
						"and '" + dauthang + "' <= a.ngaynhap and a.ngaynhap <= '" + this.ngay + "'   " +
					"group by a.khachhang_fk  " +
				")   " +
				"trongthang  on kh.pk_seq = trongthang.khId   " +
				"where kh.npp_fk = '" + this.nppId + "' and lat is not null and long is not null";*/
		String condition = "";
		if(this.nppId.trim().length() > 0)
			condition += " AND npp_fk = '" + this.nppId + "' ";
		
		String sql = "select kh.pk_seq as khId, kh.ten as khTen, isnull(kh.dienthoai, 'NA') as dienthoai, isnull(kh.diachi, '') as diachi, isnull(kh.lat,'') as lat, isnull(kh.long,'') as long, " +
				"\n case when anhcuahang is null then 'hinh1.jpg' else anhcuahang end as anhcuahang,  " +
				"\n cast(isnull(doanhsoThangtruoc, 0) as numeric(18, 0)) / 3 as doanhsoThangtruoc ,   " +
				"\n cast( isnull(doanhsoTudauthang, 0) as numeric(18, 0)) as doanhsoTrongthang, " +
				"\n isnull(( select min(ngaynhap) from DONHANG where trangthai != '2' and khachhang_fk = kh.pk_seq ),'') AS ngayMHDauTien," +
				"\n isnull(( select max(ngaynhap) from DONHANG where trangthai != '2' and khachhang_fk = kh.pk_seq ),'') AS ngayMHCuoicung,	" +
				"\n isnull(( select tonggiatri from DONHANG where pk_seq = ( select max(pk_seq) from DONHANG where trangthai != '2' and khachhang_fk = kh.pk_seq ) ),'') AS dhGANNHAT, " +
				"\n isnull(( select max(thoidiem) from DDKD_KHACHHANG where khachhang_fk = kh.pk_seq ),'') AS vtGANNHAT," +
				"\n isnull(( select sum(tonggiatri) / count(pk_seq) from DONHANG where trangthai != '2' and khachhang_fk = kh.pk_seq and ngaynhap >= '" + bd_truoc_1_thang + "' and ngaynhap <= '" + kt_truoc_1_thang + "' ),'') as tbDONHANG, " +
				"\n isnull(( select count(distinct dh_sp.sanpham_fk) from DONHANG dh inner join DONHANG_SANPHAM dh_sp on dh.pk_seq = dh_sp.donhang_fk where dh.khachhang_fk = kh.pk_seq and dh.trangthai != '2' ),'') as dophuSP, " +
				"\n isnull(( select isnull(sum(soluong),0) as tonkho from KHACHHANG_KHO where KH_FK = kh.pk_seq and ngay = '" + this.ngay + "' ),0) as tonkhoSP ,	" +
				"\n CASE WHEN isnull(kh.LCH_FK, '0') = '" + KHACHHANGSI_ID + "' THEN '1' ELSE '0' END AS khachhangsi " +
			"\n from KhachHang kh  " +
			"\n left join  " +
			"\n (   " +
				"\n select a.khachhang_fk as khId, sum(b.soluong * b.giamua) as doanhsoThangtruoc    " +
				"\nfrom donhang a inner join donhang_sanpham b on a.pk_seq = b.donhang_fk  " +
				"\n where a.khachhang_fk in ( select pk_seq from khachhang where lat is not null " + condition + " ) and a.trangthai = '1'   " +
					"\n and '" + bd_truoc_1_thang + "' <= a.ngaynhap and a.ngaynhap <= '" + kt_truoc_1_thang + "'   " +
				"\n group by a.khachhang_fk  " +
			" \n )   " +
			"\n thangtruoc on kh.pk_seq = thangtruoc.khId left join   " +
			"\n (   " +
				"\n select a.khachhang_fk as khId, sum(b.soluong * b.giamua) / 1  as doanhsoTudauthang   " +
				"\n from donhang a inner join donhang_sanpham b on a.pk_seq = b.donhang_fk  " +
				"\n where a.khachhang_fk in (select pk_seq from khachhang where lat is not null " + condition + " ) and a.trangthai = '1'   " +
					"\n and '" + dauthang + "' <= a.ngaynhap and a.ngaynhap <= '" + this.ngay + "'   " +
				"\n group by a.khachhang_fk  " +
			"\n )   " +
			"\n trongthang  on kh.pk_seq = trongthang.khId   " +
			"\n where lat is not null and long is not null ";
		
		if(this.tbhId.trim().length() > 0)
		{
			sql += "\n and kh.pk_seq in (select khachhang_fk from khachhang_tuyenbh where tbh_fk = '" + this.tbhId + "') ";
		}
		else if(this.ddkdId.trim().length() > 0)
		{
			sql += "\n and kh.pk_seq in ( select khachhang_fk from khachhang_tuyenbh where tbh_fk in (select pk_seq from tuyenbanhang where ddkd_fk = '" + this.ddkdId + "') ) ";
		}
		
			if(nppId != null)
			if(this.nppId.trim().length() > 0)
				sql += " AND kh.npp_fk = '" + this.nppId + "' ";
		System.out.println("1.Khoi tao khach hang: " + sql);
		this.khList = db.get(sql);
		
		 condition = "";
		if(this.nppId.trim().length() > 0)
			condition += " AND npp_fk = '" + this.nppId + "' ";
		else
			condition += " AND npp_fk in ( select pk_seq from NHAPHANPHOI where khuvuc_fk = '" + this.kvId + "' ) ";
		if(this.ngay.trim().length() > 0)
		{/*
			String query = "select distinct a.ten as khTen, a.diachi, a.dienthoai as dienthoai, b.thoidiem, c.ten as ddkdTen, b.lat, b.long " +
					"from khachhang a inner join ddkd_khachhang b on a.pk_seq = b.khachhang_fk inner join daidienkinhdoanh c on b.ddkd_fk = c.pk_seq " +
					"where a.npp_fk = '" + this.nppId + "' and replace(convert(nvarchar(10), b.thoidiem, 102), '.', '-') = '" + this.ngay + "' ";
			
			if(this.ddkdId.trim().length() > 0)
			{
				query += " and b.ddkd_fk = '" + this.ddkdId + "' ";
			}
			
			if(this.tbhId.trim().length() > 0)
			{
				query += " and a.khachhang_fk in (select khachhang_fk from khachhang_tuyenbh where tbh_fk = '" + this.tbhId + "') ";
			}
			
			System.out.println("2.Khach hang Selected: " + query);
			this.khSelected = db.get(query);
			
			String query = " select distinct a.ten as khTen, a.diachi, a.dienthoai as dienthoai, b.thoidiem, c.ten as ddkdTen, b.lat, b.long, " +
					" isnull((select sum(tonggiatri) from Donhang where trangthai != 2 and ngaynhap = '" + this.ngay + "' and khachhang_fk = a.pk_seq), 0) as doanhso " +
					" from khachhang a inner join ddkd_khachhang b on a.pk_seq = b.khachhang_fk inner join daidienkinhdoanh c on b.ddkd_fk = c.pk_seq " +
					" where a.npp_fk = '" + this.nppId + "' and replace(convert(nvarchar(10), b.thoidiem, 102), '.', '-') = '" + this.ngay + "' ";
			
			if(this.ddkdId.trim().length() > 0)
			{
				query += " and b.ddkd_fk = '" + this.ddkdId + "' ";
			}
			
			if(this.tbhId.trim().length() > 0)
			{
				query += " and a.khachhang_fk in (select khachhang_fk from khachhang_tuyenbh where tbh_fk = '" + this.tbhId + "') ";
			}
			
			System.out.println("2.Khach hang Selected: " + query);
			this.khSelected = db.get(query);
		*/

			String query = " select distinct a.pk_seq as khId, a.ten as khTen, isnull(a.diachi, '') as diachi, isnull(a.dienthoai, 'NA') as dienthoai, b.thoidiem, c.ten as ddkdTen, b.lat, b.long, " +
					"	case when anhcuahang is null then 'hinh1.jpg' else anhcuahang end as anhcuahang, " +
					"   cast(isnull(doanhsoThangtruoc, 0) as numeric(18, 0)) / 3 as doanhsoThangtruoc ,   " +
					"   cast( isnull(doanhsoTudauthang, 0) as numeric(18, 0)) as doanhsoTrongthang, " +
					" ( select min(ngaynhap) from DONHANG where trangthai != '2' and khachhang_fk = a.pk_seq ) AS ngayMHDauTien, " +
					" ( select max(ngaynhap) from DONHANG where trangthai != '2' and khachhang_fk = a.pk_seq ) AS ngayMHCuoicung,	" +
					" ( select tonggiatri from DONHANG where pk_seq = ( select max(pk_seq) from DONHANG where trangthai != '2' and khachhang_fk = a.pk_seq ) ) AS dhGANNHAT, " +
					" ( select max(thoidiem) from DDKD_KHACHHANG where khachhang_fk = a.pk_seq ) AS vtGANNHAT, " +
					" ( select sum(tonggiatri) / count(pk_seq) from DONHANG where trangthai != '2' and khachhang_fk = a.pk_seq and ngaynhap >= '" + bd_truoc_1_thang + "' and ngaynhap <= '" + kt_truoc_1_thang + "' ) as tbDONHANG,	" +
					" ( select count(distinct dh_sp.sanpham_fk) from DONHANG dh inner join DONHANG_SANPHAM dh_sp on dh.pk_seq = dh_sp.donhang_fk where dh.khachhang_fk = a.pk_seq and dh.trangthai != '2' ) as dophuSP, " +
					" ( select sum(soluong) from KHACHHANG_KHO where KH_FK = a.pk_seq and ngay = '" + this.ngay + "' ) as tonkhoSP,	" +
					" ( select sum(tonggiatri)  from DONHANG where trangthai != '2' and khachhang_fk = a.pk_seq and ngaynhap >= '" + this.ngay + "' and ngaynhap <= '" + this.ngay + "' ) as ds	" +
					" from khachhang a inner join ddkd_khachhang b on a.pk_seq = b.khachhang_fk " +
					"	inner join daidienkinhdoanh c on b.ddkd_fk = c.pk_seq " +
					"left join  " +
					"(   " +
						"select a.khachhang_fk as khId, sum(b.soluong * b.giamua)  as doanhsoThangtruoc    " +
						"from donhang a inner join donhang_sanpham b on a.pk_seq = b.donhang_fk  " +
						"where a.khachhang_fk in ( select pk_seq from khachhang where lat is not null " + condition + " ) and a.trangthai = '1'   " +
							"and '" + bd_truoc_1_thang + "' <= a.ngaynhap and a.ngaynhap <= '" + kt_truoc_1_thang + "'   " +
						"group by a.khachhang_fk  " +
					")   " +
					"thangtruoc on a.pk_seq = thangtruoc.khId left join   " +
					"(   " +
						"select a.khachhang_fk as khId, sum(b.soluong * b.giamua) / 1  as doanhsoTudauthang   " +
						"from donhang a inner join donhang_sanpham b on a.pk_seq = b.donhang_fk  " +
						"where a.khachhang_fk in (select pk_seq from khachhang where lat is not null " + condition + " ) and a.trangthai = '1'   " +
							"and '" + dauthang + "' <= a.ngaynhap and a.ngaynhap <= '" + this.ngay + "'   " +
						"group by a.khachhang_fk  " +
					")   " +
					"trongthang  on a.pk_seq = trongthang.khId   " +
					" where replace(convert(nvarchar(10), b.thoidiem, 102), '.', '-') = '" + this.ngay + "' ";
			
			if(this.nppId.trim().length() > 0)
				query += " and a.npp_fk = '" + this.nppId + "' ";
			if(this.tbhId.trim().length() > 0)
			{
				query += " and a.pk_seq in (select khachhang_fk from khachhang_tuyenbh where tbh_fk = '" + this.tbhId + "') ";
			}
			else if(this.ddkdId.trim().length() > 0)
			{
				query += " and b.ddkd_fk = '" + this.ddkdId + "' ";
			}
			
			query += " order by b.thoidiem ";
			
			System.out.println("[Bandott.init] 2.Khach hang Selected: " + query);
			
			
		/*	if(action.equals("viewBd"))*/
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
			if(this.ddkdList != null)
				this.ddkdList.close();
			if(this.khList != null)
				this.khList.close();
			if(this.khSelected != null)
				this.khSelected.close();
			if(this.tbhList != null)
				this.tbhList.close();
			if(this.nppRs != null) {
				this.nppRs.close();
			}
			if(this.spRs != null) {
				this.spRs.close();
			}
			
		} catch (Exception e) {}
		if(this.db != null)
			this.db.shutDown();
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

	
	public ResultSet getChungloaiRs() 
	{
		return this.chungloaiRs;
	}

	
	public void setChungloaiRs(ResultSet chungloaiRs) 
	{
		this.chungloaiRs = 	chungloaiRs;
	}

	
	public String getChungloaiId()
	{
		return this.clId;
	}

	public void setChungloaiId(String clId) 
	{
		this.clId = clId;
	}

	public ResultSet getDSTbNganhhang() 
	{
		return this.dstbNganhhang;
	}

	public void getDSTbNganhhang(ResultSet dstb3Thang)
	{
		this.dstbNganhhang = dstb3Thang;
	}

	
	public void initBCDoPhu() 
	{
		this.getNppInfo();
		
		this.chungloaiRs = db.get("select pk_seq, ten from chungloai where trangthai = '1'");
		
		//Lay cach 3 thang
		if(this.ngay.length() <= 0)
		{
			String[] dauthang = this.getDateTime().split("-");
			this.ngay = dauthang[0] + "-" + dauthang[1] + "-01";
		}
		
		if(this.denngay.length() <=0 )
			this.denngay = this.getDateTime();
		
		String[] now = this.ngay.split("-");
		String thang = now[1];
		
		if(thang.startsWith("0"))
			thang = thang.replaceAll("0", "");
		
		String iDay = now[2];
		if(iDay.startsWith("0"))
			iDay = iDay.replaceAll("0", "");
		
		String before3 = Integer.toString(Integer.parseInt(thang) - 3);
		if(before3.length() < 2)
			before3 = "0" + before3;
		
		String before1 = Integer.toString(Integer.parseInt(thang) - 1);
		if(before1.length() < 2)
			before1 = "0" + before1;
		
		String ngaycuoi = LastDayOfMonth(Integer.parseInt(thang) - 1, Integer.parseInt(now[0]));
		
		String bd_truoc_3_thang = now[0] + "-" + before3 + "-01";
		String kt_truoc_1_thang = now[0] + "-" + before1 + "-" + ngaycuoi;
		
		String dauthang = now[0] + "-" + now[1] + "-01";
		
		int tongsongay = Songaytrongthang(Integer.parseInt(thang) - 1, Integer.parseInt(now[0])) 
						+ Songaytrongthang(Integer.parseInt(thang) - 2, Integer.parseInt(now[0])) 
						+ Songaytrongthang(Integer.parseInt(thang) - 3, Integer.parseInt(now[0]));
		tongsongay = (tongsongay - 12);
		
		if(this.clId.length() <= 0)
		{
			/*String sql = "select khachhangAll.khId, khachhangAll.khTen, khachhangAll.dienthoai, khachhangAll.lat, khachhangAll.long, " +
							"isnull(dstrungbinh.trungbinh3Thang, '0') as doanhsoThangtruoc, " +
							"isnull(trongthang.tudauthang, '0') as doanhsoTrongthang " +
					"from ( " +
						"select pk_seq as khId, ten as khTen, dienthoai, lat, long " +
						"from khachhang where npp_fk = '" + this.nppId + "' and trangthai = '1' and lat is not null ) khachhangAll " +
					"left join ( " +
							"select d.pk_seq as khId, sum(b.soluong * b.giamua) / " + tongsongay + "  as trungbinh3Thang  " +
							"from donhang a inner join donhang_sanpham b on a.pk_seq = b.donhang_fk inner join sanpham c on b.sanpham_fk = c.pk_seq " +
								"inner join khachhang d on a.khachhang_fk = d.pk_seq  " +
							"where a.khachhang_fk in (select pk_seq from khachhang where npp_fk = '" + this.nppId + "' and lat is not null) and a.trangthai = '1' " +
								"and '" + bd_truoc_3_thang + "' <= a.ngaynhap and a.ngaynhap <= '" + kt_truoc_1_thang + "' " +
							"group by d.pk_seq) " +
						"dstrungbinh on khachhangAll.khId = dstrungbinh.khId " +
					"left join ( " +
							"select d.pk_seq as khId, sum(b.soluong * b.giamua) / " + iDay + "  as tudauthang " +
							"from donhang a inner join donhang_sanpham b on a.pk_seq = b.donhang_fk inner join sanpham c on b.sanpham_fk = c.pk_seq  " +
								"inner join khachhang d on a.khachhang_fk = d.pk_seq " +
							"where a.khachhang_fk in (select pk_seq from khachhang where npp_fk = '" + this.nppId + "' and lat is not null ) and a.trangthai = '1' " +
								"and '" + dauthang + "' <= a.ngaynhap and a.ngaynhap <= '" + this.ngay + "' " +
							"group by d.pk_seq) " +
					"trongthang on khachhangAll.khId = trongthang.khId";*/
			
			/*String sql = "select d.ten as khten, d.dienthoai, d.lat, d.long, cast(isnull(doanhso.tbdoanhso, '0') as numeric(18, 0)) as tbdoanhso, '0' as doanhsotheonganh " +
					"from khachhang d " +
					"left join " +
						"( " +
						"select a.khachhang_fk, sum(isnull(b.soluong, '0') * isnull(b.giamua, '0')) / DATEDIFF (day, '" + this.ngay + "', '" + this.denngay + "') as tbdoanhso " +
						"from donhang a inner join donhang_sanpham b on a.pk_seq = b.donhang_fk " +
						"group by a.khachhang_fk) doanhso " +
					"on d.pk_seq = doanhso.khachhang_fk " +
					"where npp_fk = '" + this.nppId + "' and lat is not null";
			
			sql += " union  " +
				   "select b.ten, b.dienthoai, b.lat, b.long, 1, 1   " +
				   "from lichsu_dophu a inner join khachhang b on a.khachhang_fk = b.pk_seq   " +
				   "where lat is not null and long is not null and khachhang_fk in (select pk_seq from khachhang where npp_fk = '" + this.nppId + "') ";*/
			
			String sql = "select khId, khTen, dienthoai, lat, long, sum(tbdoanhso) as tbdoanhso, sum(doanhsotheonganh) as doanhsotheonganh  " +
					"from  " +
					"(  " +
						"select d.pk_seq as khId, d.ten as khten, d.dienthoai, d.lat, d.long, cast(isnull(doanhso.tbdoanhso, '0') as numeric(18, 0)) as tbdoanhso, '0' as doanhsotheonganh   " +
						"from khachhang d left join   " +
						"(   " +
							"select a.khachhang_fk, sum(isnull(b.soluong, '0') * isnull(b.giamua, '0')) / DATEDIFF (day, '" + this.ngay + "', '" + this.denngay + "') as tbdoanhso   " +
							"from donhang a inner join donhang_sanpham b on a.pk_seq = b.donhang_fk   " +
							"group by a.khachhang_fk  " +
						")   " +
						"doanhso on d.pk_seq = doanhso.khachhang_fk    " +
						"where npp_fk = '" + this.nppId +  "' and lat is not null   " +
					"union   " +
						"select b.pk_seq as khId, b.ten, b.dienthoai, b.lat, b.long, 1, 1     " +
						"from lichsu_dophu a inner join khachhang b on a.khachhang_fk = b.pk_seq     " +
						"where lat is not null and long is not null and khachhang_fk in (select pk_seq from khachhang where npp_fk = '" + this.nppId + "')  " +
					")  " +
					"dophu group by khId, khTen, dienthoai, lat, long";
			
			System.out.println("1.Do phu nganh hang: " + sql);
			this.khList = db.get(sql);
		}
		else
		{
			String query = "select khTen, dienthoai, lat, long, sum(tbdoanhso) as tbdoanhso, sum(doanhsotheonganh) as doanhsotheonganh " +
					"from " +
					"(	" +
						"select dophu.khTen, dophu.dienthoai, dophu.lat, dophu.long, cast(dophu.tbdoanhso as numeric(18, 0)) as tbdoanhso, " +
								"cast(isnull(theonganh.dstheonganh, '0') as numeric(18, 0)) as doanhsotheonganh " +
					"from ( " +
							" select d.pk_seq as khId, d.ten as khten, d.dienthoai, d.lat, d.long, isnull(doanhso.tbdoanhso, '0') as tbdoanhso, '0' as doanhsotheonganh " +
							" from khachhang d " +
							" left join " +
							" ( " +
							" select a.khachhang_fk, sum(isnull(b.soluong, '0') * isnull(b.giamua, '0')) / DATEDIFF (day, '" + this.ngay + "', '" + this.denngay + "') as tbdoanhso " +
							"	from donhang a inner join donhang_sanpham b on a.pk_seq = b.donhang_fk " + 
							"	group by a.khachhang_fk	" +
							" ) doanhso " +
							" on d.pk_seq = doanhso.khachhang_fk " +
							" where npp_fk = '" + this.nppId + "' and lat is not null " +
						" ) dophu " +
					"left join ( " +
							"select d.pk_seq as khId, sum(b.soluong * b.giamua) / DATEDIFF (day, '" + this.ngay + "', '" + this.denngay + "') as dstheonganh " +
							"from donhang a inner join donhang_sanpham b on a.pk_seq = b.donhang_fk inner join sanpham c on b.sanpham_fk = c.pk_seq " +
								"inner join khachhang d on a.khachhang_fk = d.pk_seq " +
							"where a.khachhang_fk in (select pk_seq from khachhang where npp_fk = '" + this.nppId + "' and lat is not null) and a.trangthai = '1' " +
								"and '" + this.ngay + "' <= a.ngaynhap and a.ngaynhap <= '" + this.denngay + "' and c.chungloai_fk = '" + this.clId + "' " +
							"group by d.pk_seq) theonganh on dophu.khId = theonganh.khId";
			
				query += " union  " +
						 "select b.ten, b.dienthoai, b.lat, b.long, 1, 1   " +
						 "from lichsu_dophu a inner join khachhang b on a.khachhang_fk = b.pk_seq   " +
						 "where lat is not null and long is not null and sanpham_fk in ( select pk_seq from sanpham where chungloai_fk = '" + this.clId + "' )  " +
						 	"and khachhang_fk in (select pk_seq from khachhang where npp_fk = '" + this.nppId + "') " +
					 ") dophu group by khTen, dienthoai, lat, long ";
			
			System.out.println("2.Độ phủ ngành hàng: " + query);
			
			this.khList = db.get(query);
			
		}
		
		//Khoi tao toa do Center dau
		String query = "select top(1) pk_seq, lat + ',' + long  as trungtam " +
					"from KhachHang where trangthai = '1' and lat is not null and long is not null and npp_fk = '" + this.nppId + "'";
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

	public String getDenngay() 
	{
		return this.denngay;
	}

	public void setDenngay(String denngay)
	{
		this.denngay = denngay;
	}

	public void initBCDoPhuTT()
	{
		String query = "select PK_SEQ, TEN from VUNG";
		this.vungRs = db.get(query);
		
		query = "select PK_SEQ, TEN from KHUVUC where TRANGTHAI = '1' ";
		if(this.vungId.trim().length() > 0)
			query += " and vung_fk = '" + this.vungId + "' ";
		this.kvRs = db.get(query);
		
		
		query = " select PK_SEQ, MA, TEN from NHAPHANPHOI where PK_SEQ > 0 ";
		if(this.kvId.trim().length() > 0)
		{
			query += " and KHUVUC_FK = '" + this.kvId + "' ";
		}
		else
		{
			if(this.vungId.trim().length() > 0)
			{
				query += " and KHUVUC_FK in ( select pk_seq from KHUVUC where vung_fk = '" + this.vungId + "' ) ";
			}
		}
		this.nppRs = db.get(query);
		
		query = " select PK_SEQ, MA, TEN from SANPHAM where PK_SEQ > 0 ";
		if(this.clId.trim().length() > 0)
			query += " and CHUNGLOAI_FK = '" + this.clId + "' ";
		
		this.spRs = db.get(query);
		
		
		this.chungloaiRs = db.get("select pk_seq, ten from chungloai where trangthai = '1'");
		
		if(this.ngay.length() <= 0)
		{
			String[] dauthang = this.getDateTime().split("-");
			this.ngay = dauthang[0] + "-" + dauthang[1] + "-01";
		}
		
		if(this.denngay.length() <= 0 )
			this.denngay = this.getDateTime();
		
		String condition = "";
		if(this.vungId.trim().length() > 0)
		{
			condition += " and a.npp_fk in ( select pk_seq from NHAPHANPHOI where khuvuc_fk in ( select pk_seq from KHUVUC where vung_fk = '" + this.vungId + "' ) ) ";
		}
		
		if(this.kvId.trim().length() > 0)
		{
			condition += " and a.npp_fk in ( select pk_seq from NHAPHANPHOI where khuvuc_fk = '" + this.kvId + "' ) ";
		}
		
		if(this.nppLocId.trim().length() > 0)
		{
			condition += " and a.npp_fk in ( " + this.nppLocId + " ) ";
		}
		
		if(this.spId.trim().length() > 0)
		{
			condition += " and  b.sanpham_fk in ( " + this.spId + " ) ";
		}
		
		if(this.nppLocId.trim().length() > 0  )
		{
			if(this.clId.length() <= 0)
			{
				String sql = "select khId, khTen, isnull(dienthoai, '') as dienthoai, lat, long, sum(tbdoanhso) as tbdoanhso, sum(doanhsotheonganh) as doanhsotheonganh  " +
					"from  " +
					"(  " +
						"select d.pk_seq as khId, d.ten as khten, d.dienthoai, d.lat, d.long, cast(isnull(doanhso.tbdoanhso, '0') as numeric(18, 0)) as tbdoanhso, '0' as doanhsotheonganh   " +
						"from khachhang d inner join   " +
						"(   " +
							"select a.khachhang_fk, sum(isnull(b.soluong, '0') * isnull(b.giamua, '0')) / DATEDIFF (day, '" + this.ngay + "', '" + this.denngay + "') as tbdoanhso   " +
							"from donhang a inner join donhang_sanpham b on a.pk_seq = b.donhang_fk  " +
							"where a.trangthai in (1, 3) and a.ngaynhap >= '" + this.ngay + "' and a.ngaynhap <= '" + this.denngay + "'  " + condition +
							"group by a.khachhang_fk  " +
						")   " +
						"doanhso on d.pk_seq = doanhso.khachhang_fk    " +
						"where  lat is not null and d.npp_fk in ( " + this.nppLocId + " ) " +
					"union   " +
						"select b.pk_seq as khId, b.ten, b.dienthoai, b.lat, b.long, 1, 1     " +
						"from lichsu_dophu a inner join khachhang b on a.khachhang_fk = b.pk_seq     " +
						"where lat is not null and long is not null and khachhang_fk in (select pk_seq from khachhang where npp_fk in ( " + this.nppLocId + " ) ) " +
						"  	and a.sanpham_fk in ( " + this.spId + " ) " +
					")  " +
					"dophu group by khId, khTen, dienthoai, lat, long";
				
				System.out.println("11.Do phu nganh hang TT: " + sql);
				this.khList = db.get(sql);
			}
			else
			{
				query = "select khTen, isnull(dienthoai, '') as dienthoai, lat, long, sum(tbdoanhso) as tbdoanhso, sum(doanhsotheonganh) as doanhsotheonganh " +
					"from " +
					"(	" +
						"select dophu.khTen, dophu.dienthoai, dophu.lat, dophu.long, cast(dophu.tbdoanhso as numeric(18, 0)) as tbdoanhso, " +
								"cast(isnull(theonganh.dstheonganh, '0') as numeric(18, 0)) as doanhsotheonganh " +
					"from ( " +
							" select d.pk_seq as khId, d.ten as khten, d.dienthoai, d.lat, d.long, isnull(doanhso.tbdoanhso, '0') as tbdoanhso, '0' as doanhsotheonganh " +
							" from khachhang d " +
							" inner join " +
							" ( " +
							" 	select a.khachhang_fk, sum(isnull(b.soluong, '0') * isnull(b.giamua, '0')) / DATEDIFF (day, '" + this.ngay + "', '" + this.denngay + "') as tbdoanhso " +
							"	from donhang a inner join donhang_sanpham b on a.pk_seq = b.donhang_fk " +
							"	where a.ngaynhap >= '" + this.ngay + "' and a.ngaynhap <= '" + this.denngay + "' and a.trangthai in ( 1, 3 ) " + condition + 
							"	group by a.khachhang_fk	" +
							" ) doanhso " +
							" on d.pk_seq = doanhso.khachhang_fk " +
							" where  lat is not null and d.npp_fk in ( " + this.nppLocId + " ) " +
						" ) dophu " +
					"inner join ( " +
							"select d.pk_seq as khId, sum(b.soluong * b.giamua) / DATEDIFF (day, '" + this.ngay + "', '" + this.denngay + "') as dstheonganh " +
							"from donhang a inner join donhang_sanpham b on a.pk_seq = b.donhang_fk inner join sanpham c on b.sanpham_fk = c.pk_seq " +
								"inner join khachhang d on a.khachhang_fk = d.pk_seq " +
							"where a.khachhang_fk in (select pk_seq from khachhang where lat is not null and npp_fk in ( " + this.nppLocId + " )) and a.trangthai = '1' " +
								"and '" + this.ngay + "' <= a.ngaynhap and a.ngaynhap <= '" + this.denngay + "' and c.chungloai_fk = '" + this.clId + "' " + condition + 
							"group by d.pk_seq) theonganh on dophu.khId = theonganh.khId";
			
				query += " union  " +
						 "select b.ten, isnull(b.dienthoai, '') as dienthoai, b.lat, b.long, 1, 1   " +
						 "from lichsu_dophu a inner join khachhang b on a.khachhang_fk = b.pk_seq   " +
						 "where lat is not null and long is not null and sanpham_fk in ( select pk_seq from sanpham where chungloai_fk = '" + this.clId + "' )  " +
						 	"	and khachhang_fk in (select pk_seq from khachhang where npp_fk in ( " + this.nppLocId + " ) ) " +
						 	"  	and a.sanpham_fk in ( " + this.spId + " ) " +
					 ") dophu group by khTen, dienthoai, lat, long ";
				
				System.out.println("12.Độ phủ ngành hàng TT: " + query);
				
				this.khList = db.get(query);
				
			}
		}
		
		
	}

	
	public ResultSet getNppRs() {
		
		return this.nppRs;
	}

	
	public void setNppRs(ResultSet nppRs) {
		
		this.nppRs = nppRs;
	}

	
	public ResultSet getVungRs() {
		
		return this.vungRs;
	}

	
	public void setVungRs(ResultSet vungRs) {
		
		this.vungRs = vungRs;
	}

	
	public String getVungId() {
		
		return this.vungId;
	}

	
	public void setVungId(String vungId) {
		
		this.vungId = vungId;
	}

	
	public ResultSet getKhuvucRs() {
		
		return this.kvRs;
	}

	
	public void setKhuvucRs(ResultSet kvRs) {
		
		this.kvRs = kvRs;
	}

	
	public String getKhuvucId() {
		
		return this.kvId;
	}

	
	public void setKhuvucId(String kvId) {
		
		this.kvId = kvId;
	}

	
	public ResultSet getSanphamRs() {
		
		return this.spRs;
	}

	
	public void setSanphamRs(ResultSet spRs) {
		
		this.spRs = spRs;
	}

	
	public String getSanphamId() {
		
		return this.spId;
	}

	
	public void setSanphamId(String spId) {
		
		this.spId = spId;
	}

	
	public String getNppLocId() {
		
		return this.nppLocId;
	}

	
	public void setNppLocId(String nppLocId) {
		
		this.nppLocId = nppLocId;
	} 
	
	public String getView() {
		
		return this.view;
	}

	
	public void setView(String view) {
		
		this.view = view;
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

	
	public String getKhachHangSi() {
		
		return this.khSi;
	}

	
	public void setKhachHangSi(String kHSi) {
		
		this.khSi = kHSi;
	}
	
}