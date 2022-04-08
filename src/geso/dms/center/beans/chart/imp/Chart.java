package geso.dms.center.beans.chart.imp;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import geso.dms.center.beans.chart.IChart;
import geso.dms.center.db.sql.dbutils;

public class Chart implements IChart 
{
	String userId;
	String userTen;
	String thang;
	String nam;
	String muclay;
	String msg;
	
	String tungay;
	String dengay;
	String trangthai;
	String mangthang[];
	String mangsku[];
	String mangTensku[];
	String mangtenkhuvuc[];
	
	String mangidkhuvuc[];
	
	ResultSet rsChiTieu;
	ResultSet rsDSoPriYear;
	ResultSet rsDSoSecYear;
	
	ResultSet rsTonKhoKhuvuc;
	ResultSet RsDSSKU;
	Hashtable<String, Long> dtChart;
	Hashtable<String, Long> dtNvhdChart;
	Hashtable<String, Long> dtNvNhdChart;
	ResultSet data;
	
	dbutils db;
	
	public Chart()
	{
		this.userId = "";
		this.userTen = "";
		this.thang = "";
		this.nam = "";
		this.mangthang=new String[12];
		
		this.tungay = "";
		this.dengay  = "";
		this.msg = "";
		this.trangthai = "" ; //Hoat dong
		this.muclay = "0"; //Khu vuc, 1 Vung
		
		Calendar cal = Calendar.getInstance();
		int year_ = cal.get(Calendar.YEAR);
		this.nam=year_+"";
		int thang_=cal.get(Calendar.MONTH)+1;
		
		this.thang=thang_+"";
		
		dtChart = new Hashtable<String, Long>();
		dtNvhdChart = new Hashtable<String, Long>();
		dtNvNhdChart = new Hashtable<String, Long>();
		
		db = new dbutils();
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

	public String getMuclay()
	{
		return this.muclay;
	}

	public void setMuclay(String muclay) 
	{
		this.muclay = muclay;	
	}

	public ResultSet getDataChart()
	{
		return this.data;
	}

	public void setDataChart(ResultSet data) 
	{
		this.data = data;
	}

	public void init(String query) 
	{
		if(this.nam.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn năm";
			return;
		}
		
		this.dtChart.clear();
		
		String sql = "";
		if(query.length() > 0)
			sql = query;
		else
		{
			String ngaydonhang = this.nam;
			if(this.thang.length() > 0)
				ngaydonhang = this.nam + "-" + this.thang;
			
			if(this.muclay.equals("0"))
			{
				sql = "select a.PK_SEQ, a.TEN, SUM(isnull(c.tonggiatri, 0)) as DoanhSo  " +
					"from KHUVUC a left join NHAPHANPHOI b on a.PK_SEQ = b.KHUVUC_FK left join DONHANG c on b.PK_SEQ = c.NPP_FK  " +
					"where c.TRANGTHAI = '1' and c.NGAYNHAP like '" + ngaydonhang + "-%'  " +
					"group by a.PK_SEQ, a.TEN  " +
					"order by a.PK_SEQ asc";
			}
			else
			{
				sql = "select a.PK_SEQ, a.TEN, SUM(isnull(d.tonggiatri, 0)) as DoanhSo   " +
					"from VUNG a left join KHUVUC b on a.PK_SEQ = b.VUNG_FK left join NHAPHANPHOI c on b.PK_SEQ = c.KHUVUC_FK left join DONHANG d on c.PK_SEQ = d.NPP_FK   " +
					"where d.TRANGTHAI = '1' and d.NGAYNHAP like '" + ngaydonhang + "-%'  " +
					"group by a.PK_SEQ, a.TEN  " +
					"order by a.PK_SEQ asc";
			}
		}
		
		System.out.println("115.PT Doanh so: " + sql);
		ResultSet rs = db.get(sql);
		if(rs != null)
		{
			try 
			{
				while(rs.next())
				{
					String key = rs.getString("TEN");
					long doanhso =  Math.round(rs.getDouble("DoanhSo"));
					
					this.dtChart.put(key, doanhso);
				}
				rs.close();
			} 
			catch (Exception e) 
			{
				System.out.println("115.Exception: " + e.getMessage());
			}
		}
	}
	
	public void initChartTK() 
	{
		if(this.tungay.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn thời gian bắt đầu lấy báo cáo";
			return;
		}
		
		if(this.tungay.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn thời gian kết thúc lấy báo cáo";
			return;
		}
		
		this.dtChart.clear();
		
		String sql = "";
		if(this.muclay.equals("0"))
		{
			sql = "select a.PK_SEQ, a.TEN, SUM(ISNULL(c.soluong, 0)) as TonKho " +
				"from KHUVUC a left join NHAPHANPHOI b on a.PK_SEQ = b.KHUVUC_FK left join TONKHONGAY c on b.PK_SEQ = c.NPP_FK  " +
				"where '" + this.tungay + "' <= c.NGAY and c.NGAY <= '" + this.dengay + "'  " +
				"group by a.PK_SEQ, a.TEN order by a.PK_SEQ asc";
		}
		else
		{
			sql = "select a.PK_SEQ, a.TEN, SUM(ISNULL(d.soluong, 0)) as TonKho " +
				"from VUNG a left join KHUVUC b on a.PK_SEQ = b.VUNG_FK left join NHAPHANPHOI c on b.PK_SEQ = c.KHUVUC_FK " +
					"left join TONKHONGAY d on c.PK_SEQ = d.NPP_FK   " +
				"where '" + this.tungay + "' <= d.NGAY and d.NGAY <= '" + this.dengay + "'  " +
				"group by a.PK_SEQ, a.TEN order by a.PK_SEQ asc";
		}
		
		System.out.println("115.PT Ton kho: " + sql);
		ResultSet rs = db.get(sql);
		if(rs != null)
		{
			try 
			{
				while(rs.next())
				{
					String key = rs.getString("TEN");
					long tonkho =  Math.round(rs.getDouble("TonKho"));
					
					this.dtChart.put(key, tonkho);
				}
				rs.close();
			} 
			catch (Exception e) 
			{
				System.out.println("115.Exception: " + e.getMessage());
			}
		}
	}
	
	public void initChartNV() 
	{
		this.dtChart.clear();
		this.dtNvhdChart.clear();
		this.dtNvNhdChart.clear();
		
		String sql = "";
		if(this.muclay.equals("0"))
		{
			/*sql = "select a.PK_SEQ, a.TEN, count(ISNULL(c.pk_seq, 0)) as SoNhanVien  " +
				"from KHUVUC a left join NHAPHANPHOI b on a.PK_SEQ = b.KHUVUC_FK left join DAIDIENKINHDOANH c on b.PK_SEQ = c.NPP_FK  ";
			
			if(this.trangthai.equals("1"))
			{
				sql += " where c.TRANGTHAI = '" + this.trangthai + "'  ";
			}
			
			sql += "group by a.PK_SEQ, a.TEN order by a.PK_SEQ asc";*/
			
			sql = "select tong.TEN, tong.SoNhanVien as TongSo, Isnull(nhanvienhoatdong.SoNhanVien, 0) as DangHoatDong, ISNULL(ngunghoatdong.sonhanvien, 0) as NgungHoatDong  " +
				"from " +
				"( " +
					"select a.PK_SEQ, a.TEN, count(ISNULL(c.pk_seq, 0)) as SoNhanVien " +
					"from KHUVUC a left join NHAPHANPHOI b on a.PK_SEQ = b.KHUVUC_FK left join DAIDIENKINHDOANH c on b.PK_SEQ = c.NPP_FK " +
					"group by a.PK_SEQ, a.TEN " +
				")  " +
				"tong left join " +
				"( " +
					"select a.PK_SEQ, a.TEN, count(ISNULL(c.pk_seq, 0)) as SoNhanVien " +
					"from KHUVUC a left join NHAPHANPHOI b on a.PK_SEQ = b.KHUVUC_FK left join DAIDIENKINHDOANH c on b.PK_SEQ = c.NPP_FK  " +
					"where c.TRANGTHAI = '1'  " +
					"group by a.PK_SEQ, a.TEN " +
				")  " +
				"nhanvienhoatdong on tong.PK_SEQ = nhanvienhoatdong.PK_SEQ left join " +
				"( " +
					"select a.PK_SEQ, count(ISNULL(c.pk_seq, 0)) as SoNhanVien  " +
					"from KHUVUC a left join NHAPHANPHOI b on a.PK_SEQ = b.KHUVUC_FK left join DAIDIENKINHDOANH c on b.PK_SEQ = c.NPP_FK  " +
					"where c.TRANGTHAI = '0' " +
					"group by a.PK_SEQ, a.TEN " +
				")  " +
				"ngunghoatdong on tong.PK_SEQ = ngunghoatdong.PK_SEQ  " +
				"order by tong.PK_SEQ asc";
		}
		else
		{
			/*sql = "select a.PK_SEQ, a.TEN, count(ISNULL(d.pk_seq, 0)) as SoNhanVien  " +
				"from VUNG a left join KHUVUC b on a.PK_SEQ = b.VUNG_FK left join NHAPHANPHOI c on b.PK_SEQ = c.KHUVUC_FK " +
				"left join DAIDIENKINHDOANH d on c.PK_SEQ = d.NPP_FK  ";
			
			if(this.trangthai.equals("1"))
			{
				sql += " where d.TRANGTHAI = '" + this.trangthai + "'  ";
			}

			sql += "group by a.PK_SEQ, a.TEN order by a.PK_SEQ asc";*/
			
			sql = " select tong.TEN, tong.SoNhanVien as TongSo, isnull(danghoatdong.SoNhanVien, 0) as DangHoatDong, isnull(ngunghoatdong.SoNhanVien, 0) as NgungHoatDong  " +
				"from  " +
				"( " +
					"select a.PK_SEQ, a.TEN, count(ISNULL(d.pk_seq, 0)) as SoNhanVien  " +
					"from VUNG a left join KHUVUC b on a.PK_SEQ = b.VUNG_FK left join NHAPHANPHOI c on b.PK_SEQ = c.KHUVUC_FK left join DAIDIENKINHDOANH d on c.PK_SEQ = d.NPP_FK  " +
					"group by a.PK_SEQ, a.TEN  " +
				")  " +
				"tong left join " +
				"( " +
					"select a.PK_SEQ, count(ISNULL(d.pk_seq, 0)) as SoNhanVien  " +
					"from VUNG a left join KHUVUC b on a.PK_SEQ = b.VUNG_FK left join NHAPHANPHOI c on b.PK_SEQ = c.KHUVUC_FK left join DAIDIENKINHDOANH d on c.PK_SEQ = d.NPP_FK  " +
					"where d.TRANGTHAI = '1'  " +
					"group by a.PK_SEQ  " +
				")  " +
				"danghoatdong on tong.PK_SEQ = danghoatdong.PK_SEQ left join   " +
				"( " +
					"select a.PK_SEQ, count(ISNULL(d.pk_seq, 0)) as SoNhanVien  " +
					"from VUNG a left join KHUVUC b on a.PK_SEQ = b.VUNG_FK left join NHAPHANPHOI c on b.PK_SEQ = c.KHUVUC_FK left join DAIDIENKINHDOANH d on c.PK_SEQ = d.NPP_FK  " +
					"where d.TRANGTHAI = '0'  " +
					"group by a.PK_SEQ  " +
				")  " +
				"ngunghoatdong on tong.PK_SEQ = ngunghoatdong.PK_SEQ ";
		}
		
		System.out.println("115.PT Nhan vien: " + sql);
		ResultSet rs = db.get(sql);
		if(rs != null)
		{
			try 
			{
				while(rs.next())
				{
					String key = rs.getString("TEN");
					long tong =  Math.round(rs.getDouble("TongSo"));
					long hoatdong =  Math.round(rs.getDouble("DangHoatDong"));
					long ngunghoatdong =  Math.round(rs.getDouble("NgungHoatDong"));
					
					this.dtChart.put(key, tong);
					this.dtNvhdChart.put(key, hoatdong);
					this.dtNvNhdChart.put(key, ngunghoatdong);
				}
				rs.close();
			} 
			catch (Exception e) 
			{
				System.out.println("115.Exception: " + e.getMessage());
			}
		}
	}
	
	public void initChartCT() 
	{
		
		
		if(this.nam.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn năm";
			return;
		}
		
		if(this.thang.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn tháng";
			return;
		}
		
		this.dtChart.clear();
		this.dtNvhdChart.clear();
		
		String sql = "";
		
		String ngaydonhang = this.nam;
		if(this.thang.length() > 0)
			ngaydonhang = this.nam + "-" + this.thang;
		
		
		String chuoithang="";
			String chuoingayin="";
		Calendar calendar = Calendar.getInstance() ;

		
		 DateFormat formatdate=new SimpleDateFormat("yyyy-MM");
	      
		for(int k=11;k >=0;k--){
			calendar.set(Calendar.YEAR,Integer.parseInt(this.nam));
			calendar.set(Calendar.MONTH, Integer.parseInt(this.thang)-1);
			calendar.set(Calendar.DATE, 1);	
			calendar.add(calendar.MONTH,(-1)*k);
			String chuoiingay=formatdate.format(calendar.getTime());
			mangthang[k]=chuoiingay;
			if(k==11){
				chuoithang="["+chuoiingay+"]";
				chuoingayin="'"+chuoiingay+"'";
			}else{
				chuoithang=chuoithang+",["+chuoiingay+"]";
				chuoingayin=chuoingayin+",'"+chuoiingay+"'";
			}
			
		}
		
		// set chuoi lay doanh so nam ngoai
		String chuoithangPre="";
		String chuoingayinPre="";
		for(int k=11;k >=0;k--){
			calendar.set(Calendar.YEAR,Integer.parseInt(this.nam)-1);
			calendar.set(Calendar.MONTH, Integer.parseInt(this.thang)-1);
			calendar.set(Calendar.DATE, 1);	
			calendar.add(calendar.MONTH,(-1)*k);
			String chuoiingay=formatdate.format(calendar.getTime());
			
			if(k==11){
				chuoithangPre="["+chuoiingay+"]";
				chuoingayinPre="'"+chuoiingay+"'";
			}else{
				chuoithangPre=chuoithangPre+",["+chuoiingay+"]";
				chuoingayinPre=chuoingayinPre+",'"+chuoiingay+"'";
			}
			
		}
		
		System.out.println("chuoi thang :"+chuoithang);
		
	
		 sql=" select N'Chỉ tiêu Sec' as type , "+chuoithang+" from "+ 
			 "  ( "+
			 " select sum(ctnpp.chitieu) as chitieu , cast(ct.nam as varchar(4)) + '-'+   substring('00'+cast(ct.thang as varchar(4)),len( '00'+cast(ct.thang as varchar(4)))-1 ,3) as namthang "+  
			 " from chitieu_sec ct inner join chitieu_nhapp_sec ctnpp on ct.pk_seq=ctnpp.chitieu_sec_fk "+
			 " where ct.trangthai<>'2' and cast(ct.nam as varchar(4)) + '-'+   substring('00'+cast(ct.thang as varchar(4)),len( '00'+cast(ct.thang as varchar(4)))-1 ,3)   in "+ 
			" ("+chuoingayin+") "+
			" group by  cast(ct.nam as varchar(4)) + '-'+   substring('00'+cast(ct.thang as varchar(4)),len( '00'+cast(ct.thang as varchar(4)))-1 ,3) "+  
			" ) a "+
			" PIVOT "+
			" ( "+
			" sum (chitieu) "+
			" for namthang in ("+chuoithang+") "+
			" ) as ctsec "+
			" union all "+
			" select N'Thực đạt Sec' as type , "+chuoithang+" from ( "+
			" select sum(soluong*sp.trongluong) as trongluong ,substring(dh.ngaynhap,1,7) as thangnam "+
			" from donhang  dh  inner join donhang_sanpham dh_sp on dh.pk_seq=dh_sp.donhang_fk "+
			" inner join sanpham sp on sp.pk_seq=dh_sp.sanpham_fk "+
			" where dh.trangthai=1 and  substring(dh.ngaynhap,1,7) in ("+chuoingayin+") "+
			" group by  substring(dh.ngaynhap,1,7)  "+
			" union all "+
			" select (-1)* sum( isnull(dh_sp.soluong,dh_sp1.soluong) *sp2.trongluong) as trongluong ,substring(dh.ngaynhap,1,7) as thangnam "+
			" FROM  DONHANGTRAVE DH     "+
			" LEFT OUTER JOIN  DONHANGTRAVE_SANPHAM DH_SP ON DH_SP.DONHANGTRAVE_FK = DH.PK_SEQ "+    	
			" LEFT OUTER JOIN  DONHANG_SANPHAM DH_SP1 ON DH.DONHANG_FK = DH_SP1.DONHANG_FK  "+
			" INNER JOIN SANPHAM SP2 ON SP2.PK_SEQ = ISNULL(DH_SP.SANPHAM_FK, DH_SP1.SANPHAM_FK)  "+
			" where  dh.trangthai=3 and substring(dh.ngaynhap,1,7) in ("+chuoingayin+")  "+
			" group by substring(dh.ngaynhap,1,7) "+
			" )a "+
			" pivot  "+
			" ( "+
			" sum(trongluong) "+
			" for thangnam in ("+chuoithang+") "+
			" ) dssec "+
			" union all "+
			" select N'Chỉ tiêu Pri' as type , "+chuoithang+" from  "+
			" ( "+
			" select sum(ctnpp.chitieu) as chitieu , cast(ct.nam as varchar(4)) + '-'+   substring('00'+cast(ct.thang as varchar(4)),len( '00'+cast(ct.thang as varchar(4)))-1 ,3) as namthang "+  
			" from chitieu ct inner join chitieu_nhapp ctnpp on ct.pk_seq=ctnpp.chitieu_fk "+
			" where  ct.trangthai<>'2' and cast(ct.nam as varchar(4)) + '-'+   substring('00'+cast(ct.thang as varchar(4)),len( '00'+cast(ct.thang as varchar(4)))-1 ,3)   in "+ 
			" ("+chuoingayin+") "+
			" group by  cast(ct.nam as varchar(4)) + '-'+   substring('00'+cast(ct.thang as varchar(4)),len( '00'+cast(ct.thang as varchar(4)))-1 ,3)   "+
			" ) a "+
			" PIVOT "+
			" ( "+
			" sum (chitieu) "+
			" for namthang in ("+chuoithang+") "+
			" ) as ctsec "+
			" union all "+
			" select N'Thực đạt Pri' as type , "+chuoithang+" from( "+
			" select sum(soluong* sp.trongluong) as trongluong,substring(hd.ngaychungtu,1,7) as thangnam "+
			" from hdnhaphang hd inner join hdnhaphang_sp hd_sp "+
			" on hd.pk_seq=hd_sp.nhaphang_fk "+
			" inner join sanpham sp on sp.ma=hd_sp.sanpham_fk "+
			" where hd.trangthai <> '2' and substring(hd.ngaychungtu,1,7) in ("+chuoingayin+") "+ 
			" group by substring(hd.ngaychungtu,1,7) "+
			" )a "+
			" pivot "+ 
			" ( "+
			" sum(trongluong) "+
			" for thangnam in ("+chuoithang+") "+
			" ) dspri ";
		System.out.println("Lay du Lieu Chi Tieu : "+ sql);
		this.rsChiTieu=db.get(sql);
		
		sql=" select N'Thực đạt Sec"+this.nam+"' as type , "+chuoithang+" from ( "+
		" select sum(soluong*sp.trongluong) as trongluong ,substring(dh.ngaynhap,1,7) as thangnam "+
		" from donhang  dh  inner join donhang_sanpham dh_sp on dh.pk_seq=dh_sp.donhang_fk "+
		" inner join sanpham sp on sp.pk_seq=dh_sp.sanpham_fk "+
		" where dh.trangthai=1 and  substring(dh.ngaynhap,1,7) in ("+chuoingayin+") "+
		" group by  substring(dh.ngaynhap,1,7)  "+
		" union all "+
		" select (-1)* sum( isnull(dh_sp.soluong,dh_sp1.soluong) *sp2.trongluong) as trongluong ,substring(dh.ngaynhap,1,7) as thangnam "+
		" FROM  DONHANGTRAVE DH     "+
		" LEFT OUTER JOIN  DONHANGTRAVE_SANPHAM DH_SP ON DH_SP.DONHANGTRAVE_FK = DH.PK_SEQ "+    	
		" LEFT OUTER JOIN  DONHANG_SANPHAM DH_SP1 ON DH.DONHANG_FK = DH_SP1.DONHANG_FK  "+
		" INNER JOIN SANPHAM SP2 ON SP2.PK_SEQ = ISNULL(DH_SP.SANPHAM_FK, DH_SP1.SANPHAM_FK)  "+
		" where  dh.trangthai=3 and substring(dh.ngaynhap,1,7) in ("+chuoingayin+")  "+
		" group by substring(dh.ngaynhap,1,7) "+
		" )a "+
		" pivot  "+
		" ( "+
		" sum(trongluong) "+
		" for thangnam in ("+chuoithang+") "+
		" ) dssec "+
		" union all " +
		" select N'Thực đạt Sec"+(Integer.parseInt(this.nam)-1)+"' as type , "+chuoithangPre+" from ( "+
		" select sum(soluong*sp.trongluong) as trongluong ,substring(dh.ngaynhap,1,7) as thangnam "+
		" from donhang  dh  inner join donhang_sanpham dh_sp on dh.pk_seq=dh_sp.donhang_fk "+
		" inner join sanpham sp on sp.pk_seq=dh_sp.sanpham_fk "+
		" where dh.trangthai=1 and  substring(dh.ngaynhap,1,7) in ("+chuoingayinPre+") "+
		" group by  substring(dh.ngaynhap,1,7)  "+
		" union all "+
		" select (-1)* sum( isnull(dh_sp.soluong,dh_sp1.soluong) *sp2.trongluong) as trongluong ,substring(dh.ngaynhap,1,7) as thangnam "+
		" FROM  DONHANGTRAVE DH     "+
		" LEFT OUTER JOIN  DONHANGTRAVE_SANPHAM DH_SP ON DH_SP.DONHANGTRAVE_FK = DH.PK_SEQ "+    	
		" LEFT OUTER JOIN  DONHANG_SANPHAM DH_SP1 ON DH.DONHANG_FK = DH_SP1.DONHANG_FK  "+
		" INNER JOIN SANPHAM SP2 ON SP2.PK_SEQ = ISNULL(DH_SP.SANPHAM_FK, DH_SP1.SANPHAM_FK)  "+
		" where  dh.trangthai=3 and substring(dh.ngaynhap,1,7) in ("+chuoingayinPre+")  "+
		" group by substring(dh.ngaynhap,1,7) "+
		" )a "+
		" pivot  "+
		" ( "+
		" sum(trongluong) "+
		" for thangnam in ("+chuoithangPre+") "+
		" ) dssecpre ";
		
		System.out.println("Lay du Lieu Chi Tieu : "+ sql);
		this.rsDSoSecYear=db.get(sql);
		sql=" select N'Thực đạt Pri Nam "+(Integer.parseInt(this.nam))+"' as type , "+chuoithang+" from( "+
		" select sum(soluong* sp.trongluong) as trongluong,substring(hd.ngaychungtu,1,7) as thangnam "+
		" from hdnhaphang hd inner join hdnhaphang_sp hd_sp "+
		" on hd.pk_seq=hd_sp.nhaphang_fk "+
		" inner join sanpham sp on sp.ma=hd_sp.sanpham_fk "+
		" where hd.trangthai <> '2' and substring(hd.ngaychungtu,1,7) in ("+chuoingayin+") "+ 
		" group by substring(hd.ngaychungtu,1,7) "+
		" )a "+
		" pivot "+ 
		" ( "+
		" sum(trongluong) "+
		" for thangnam in ("+chuoithang+") "+
		" ) dspri "+
		" union all select N'Thực đạt Pri Nam "+(Integer.parseInt(this.nam)-1)+" ' as type , "+chuoithangPre+" from( "+
		" select sum(soluong* sp.trongluong) as trongluong,substring(hd.ngaychungtu,1,7) as thangnam "+
		" from hdnhaphang hd inner join hdnhaphang_sp hd_sp "+
		" on hd.pk_seq=hd_sp.nhaphang_fk "+
		" inner join sanpham sp on sp.ma=hd_sp.sanpham_fk "+
		" where hd.trangthai <> '2' and substring(hd.ngaychungtu,1,7) in ("+chuoingayinPre+") "+ 
		" group by substring(hd.ngaychungtu,1,7) "+
		" )a "+
		" pivot "+ 
		" ( "+
		" sum(trongluong) "+
		" for thangnam in ("+chuoithangPre+") "+
		" ) dspriPre ";
		
		this.rsDSoPriYear=db.get(sql);
		
		sql="select ma,ten from sanpham";
		ResultSet rs=this.db.get(sql);
		
		String chuoispngoac="";
		String chuoispin="";
		mangsku=new String[15];
		mangTensku=new String[15];
		
		int l=0;
		try{
			while (rs.next()){
				if(l==0){
					chuoispngoac="["+rs.getString("ma")+"]";
					chuoispin="'"+rs.getString("ma")+"'";
				}else{
					chuoispngoac=chuoispngoac+",["+rs.getString("ma")+"]";
					chuoispin=",'"+rs.getString("ma")+"'";
				}
				mangsku[l]=rs.getString("ma");
				mangTensku[l]=rs.getString("ma")+"_"+rs.getString("ten");
				l++;
			}
			rs.close();
		}catch (Exception e) {
			// TODO: handle exception
		}
		
		sql="select 'Sản Lượng SKU 'as type ,"+chuoispngoac+"  from ( "+
		" select sum(soluong*sp.trongluong) as trongluong ,sp.ma  "+
		" from donhang  dh  inner join donhang_sanpham dh_sp on dh.pk_seq=dh_sp.donhang_fk "+
		" inner join sanpham sp on sp.pk_seq=dh_sp.sanpham_fk "+
		" where dh.trangthai=1 and substring(dh.ngaynhap,1,4) = "+this.nam +
		" group by  sp.ma "+
		" union all "+
		" select (-1)* sum( isnull(dh_sp.soluong,dh_sp1.soluong) *sp2.trongluong) as trongluong ,sp2.ma  "+
		"  FROM  DONHANGTRAVE DH     "+
		" LEFT OUTER JOIN  DONHANGTRAVE_SANPHAM DH_SP ON DH_SP.DONHANGTRAVE_FK = DH.PK_SEQ   "+  	
		" LEFT OUTER JOIN  DONHANG_SANPHAM DH_SP1 ON DH.DONHANG_FK = DH_SP1.DONHANG_FK  "+
		" INNER JOIN SANPHAM SP2 ON SP2.PK_SEQ = ISNULL(DH_SP.SANPHAM_FK, DH_SP1.SANPHAM_FK)  "+
		" where substring(dh.ngaynhap,1,4) ="+this.nam+" and dh.trangthai=3 "+
		" group by sp2.ma "+
		" )a "+
		" pivot "+ 
		" ( "+
		" sum(trongluong) "+
		" for ma in ("+chuoispngoac+") "+
		" ) dssec";
		System.out.println("Chi Tieu SKu : "+sql);
		this.RsDSSKU=db.get(sql);
		
		//lay bao cao ton kho theo khuvuc 
		
		sql="select pk_seq,ten from khuvuc";
		
		rs=this.db.get(sql);
		
		String chuoikhuvucngoac="";
		
		mangtenkhuvuc=new String[15];
		mangidkhuvuc=new String[15];
		 l=0;
		try{
			while (rs.next()){
				if(l==0){
					chuoikhuvucngoac="["+rs.getString("pk_seq")+"]";
				
				}else{
					chuoikhuvucngoac=chuoikhuvucngoac+",["+rs.getString("pk_seq")+"]";
				
				}
				mangtenkhuvuc[l]=rs.getString("ten");
				mangidkhuvuc[l]=rs.getString("pk_seq");
				l++;
			}
			rs.close();
		}catch (Exception e) {
			// TODO: handle exception
		}
		sql=" select N'Tồn Kho' as type,"+chuoikhuvucngoac+" from( "+
			" select sum(soluong*sp.trongluong) as trongluong,kv.pk_seq  "+
			" from nhapp_kho  kho inner join nhaphanphoi npp on npp.pk_seq=kho.npp_fk "+
			" inner join sanpham sp on sp.pk_seq=kho.sanpham_fk "+
			" inner join khuvuc kv on npp.khuvuc_fk=kv.pk_seq "+
			" where soluong >0 "+
			" group by kv.pk_seq "+
			" )a "+
			" pivot "+  
			" ( "+
			" sum(trongluong) "+
			" for pk_seq in ("+chuoikhuvucngoac+" ) "+
			" ) tonkhokv";
		this.rsTonKhoKhuvuc=this.db.get(sql);
		
		
	}
	
	public void initChartCT_Thang() 
	{
		if(this.nam.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn năm";
			return;
		}
		
		this.dtChart.clear();
		this.dtNvhdChart.clear();
		
		String sql = "select thangNam.thang, thangNam.diengiai as Ten, isnull(chitieu.ChiTieu, 0) as ChiTieu, isnull(thucdat.ThucDat, 0) as ThucDat  " +
				"from " +
				"( " +
					"select 1 as thang, N'Tháng 1' as diengiai, '" + this.nam + "' as nam  " +
					"union all " +
					"select 2 as thang, N'Tháng 2' as diengiai, '" + this.nam + "' as nam  " +
					"union all  " +
					"select 3 as thang, N'Tháng 3' as diengiai, '" + this.nam + "' as nam  " +
					"union all  " +
					"select 4 as thang, N'Tháng 4' as diengiai, '" + this.nam + "' as nam  " +
					"union all  " +
					"select 5 as thang, N'Tháng 5' as diengiai, '" + this.nam + "' as nam  " +
					"union all  " +
					"select 6 as thang, N'Tháng 6' as diengiai, '" + this.nam + "' as nam " +
					"union all  " +
					"select 7 as thang, N'Tháng 7' as diengiai, '" + this.nam + "' as nam  " +
					"union all  " +
					"select 8 as thang, N'Tháng 8' as diengiai, '" + this.nam + "' as nam  " +
					"union all  " +
					"select 9 as thang, N'Tháng 9' as diengiai, '" + this.nam + "' as nam  " +
					"union all  " +
					"select 10 as thang, N'Tháng 10' as diengiai, '" + this.nam + "' as nam  " +
					"union all   " +
					"select 11 as thang, N'Tháng 11' as diengiai, '" + this.nam + "' as nam   " +
					"union all   " +
					"select 12 as thang, N'Tháng 12' as diengiai, '" + this.nam + "' as nam  " +
				") " +
				"thangNam left join  " +
				"( " +
					"select c.NAM, c.THANG, SUM(c.chitieu) as ChiTieu  " +
					"from KHUVUC a inner join NHAPHANPHOI b on a.PK_SEQ = b.KHUVUC_FK  inner join CHITIEUNPP c on b.PK_SEQ = c.NHAPP_FK  " +
					"where c.TRANGTHAI = '1' and c.NAM = '" + this.nam + "' " +
					"group by c.NAM, c.THANG " +
				") " +
				"chitieu on thangNam.thang = chitieu.THANG and thangNam.nam = chitieu.NAM left join " +
				"( " +
					"select SUBSTRING(ngaynhap, 0, 5) as nam, cast(SUBSTRING(ngaynhap, 6, 2) as int) as thang, SUM(tonggiatri) as ThucDat   " +
					"from DONHANG where TRANGTHAI = '1' and SUBSTRING(ngaynhap, 0, 5) = '" + this.nam + "'  " +
					"group by SUBSTRING(ngaynhap, 0, 5), SUBSTRING(ngaynhap, 6, 2)  " +
				") " +
				"thucdat on thangNam.thang = thucdat.thang and thangNam.nam = thucdat.nam  " +
				"order by thangNam.thang asc";
		
		
		/*String sql = "select thangNam.thang, thangNam.diengiai as Ten, isnull(chitieu.ChiTieu, 0) as ChiTieu, isnull(thucdat.ThucDat, 0) as ThucDat  " +
		"from " +
		"( " +
			"select 1 as thang, N'Jan-12' as diengiai, '" + this.nam + "' as nam  " +
			"union all " +
			"select 2 as thang, N'Fed-12' as diengiai, '" + this.nam + "' as nam  " +
			"union all  " +
			"select 3 as thang, N'Mar-12' as diengiai, '" + this.nam + "' as nam  " +
			"union all  " +
			"select 4 as thang, N'Apr-12' as diengiai, '" + this.nam + "' as nam  " +
			"union all  " +
			"select 5 as thang, N'May-12' as diengiai, '" + this.nam + "' as nam  " +
			"union all  " +
			"select 6 as thang, N'Jun-12' as diengiai, '" + this.nam + "' as nam " +
			"union all  " +
			"select 7 as thang, N'Jul-12' as diengiai, '" + this.nam + "' as nam  " +
			"union all  " +
			"select 8 as thang, N'Aug-12' as diengiai, '" + this.nam + "' as nam  " +
			"union all  " +
			"select 9 as thang, N'Sep-12' as diengiai, '" + this.nam + "' as nam  " +
			"union all  " +
			"select 10 as thang, N'Oct-12' as diengiai, '" + this.nam + "' as nam  " +
			"union all   " +
			"select 11 as thang, N'Nov-12' as diengiai, '" + this.nam + "' as nam   " +
			"union all   " +
			"select 12 as thang, N'Dec-12' as diengiai, '" + this.nam + "' as nam  " +
		") " +
		"thangNam left join  " +
		"( " +
			"select c.NAM, c.THANG, SUM(c.chitieu) as ChiTieu  " +
			"from KHUVUC a inner join NHAPHANPHOI b on a.PK_SEQ = b.KHUVUC_FK  inner join CHITIEUNPP c on b.PK_SEQ = c.NHAPP_FK  " +
			"where c.TRANGTHAI = '1' and c.NAM = '" + this.nam + "' " +
			"group by c.NAM, c.THANG " +
		") " +
		"chitieu on thangNam.thang = chitieu.THANG and thangNam.nam = chitieu.NAM left join " +
		"( " +
			"select SUBSTRING(ngaynhap, 0, 5) as nam, cast(SUBSTRING(ngaynhap, 6, 2) as int) as thang, SUM(tonggiatri) as ThucDat   " +
			"from DONHANG where TRANGTHAI = '1' and SUBSTRING(ngaynhap, 0, 5) = '" + this.nam + "'  " +
			"group by SUBSTRING(ngaynhap, 0, 5), SUBSTRING(ngaynhap, 6, 2)  " +
		") " +
		"thucdat on thangNam.thang = thucdat.thang and thangNam.nam = thucdat.nam  " +
		"order by thangNam.thang asc";*/
		
		
		
		System.out.println("115.Thuc dat va chi tieu: " + sql);
		ResultSet rs = db.get(sql);
		if(rs != null)
		{
			try 
			{
				while(rs.next())
				{
					String key = rs.getString("Ten");
					
					
					long chitieu =  Math.round(rs.getDouble("ChiTieu"));
					long thucdat =  Math.round(rs.getDouble("ThucDat"));
					
					this.dtChart.put(key, chitieu);
					
					this.dtNvhdChart.put(key, thucdat);
				}
				rs.close();
			} 
			catch (Exception e) 
			{
				System.out.println("115.Exception: " + e.getMessage());
			}
		}
		
		//In thu
		Enumeration<String> keys = this.dtChart.keys();
		while(keys.hasMoreElements())
		{
			System.out.println("Key: " + keys.nextElement());
		}
		
	}

	public void DbClose()
	{
		try{
			if(rsChiTieu!=null){
				rsChiTieu.close();
			}
			if( this.data != null )
				this.data = null;
			
			this.db.shutDown();
		}catch(Exception er){
			
		}
		}

	public String getMsg() 
	{
		return this.msg;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}

	
	public String getUserId() {
		
		return this.userId;
	}

	
	public void setUserId(String userId) {
		
		this.userId = userId;
	}

	
	public String getUserTen() {
		
		return this.userTen;
	}

	
	public void setUserTen(String userTen) {
		
		this.userTen = userTen;
	}

	public Hashtable<String, Long> getData() 
	{
		return this.dtChart;
	}

	public void setData(Hashtable<String, Long> data) 
	{
		this.dtChart = data;
	}

	
	public String getTungay() {
		
		return this.tungay;
	}

	
	public void setTungay(String tungay) {
		
		this.tungay = tungay;
	}

	
	public String getDenngay() {
		
		return this.dengay;
	}

	
	public void setDenngay(String denngay) {
		
		this.dengay = denngay;
	}

	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai)
	{
		this.trangthai = trangthai;
	}

	
	public Hashtable<String, Long> getNVHoatDong() {
		
		return this.dtNvhdChart;
	}

	
	public void setNVHoatDong(Hashtable<String, Long> data) {
		
		this.dtNvhdChart = data;
	}

	
	public Hashtable<String, Long> getNVNgungHoatDong() {
		
		return this.dtNvNhdChart;
	}

	
	public void setNVNgungHoatDong(Hashtable<String, Long> data) {
		
		this.dtNvNhdChart = data;
	}

	@Override
	public ResultSet GetRsChiTieu() {
		// TODO Auto-generated method stub
		return this.rsChiTieu;
	}

	@Override
	public String[] getmangthang() {
		// TODO Auto-generated method stub
		return this.mangthang;
	}

	@Override
	public ResultSet GetRsDSSecYeer() {
		// TODO Auto-generated method stub
		return this.rsDSoSecYear;
	}

	@Override
	public ResultSet GetRsDSPriYeer() {
		// TODO Auto-generated method stub
		return this.rsDSoPriYear;
	}

	@Override
	public ResultSet GetRsDSSKU() {
		// TODO Auto-generated method stub
		return this.RsDSSKU;
	}

	@Override
	public String[] getmangsku() {
		// TODO Auto-generated method stub
		return this.mangsku;
	}

	@Override
	public String[] getmangTenKhuvuc() {
		// TODO Auto-generated method stub
		return this.mangtenkhuvuc;
	}

	@Override
	public String[] getmangIDKhuvuc() {
		// TODO Auto-generated method stub
		return this.mangidkhuvuc;
	}

	@Override
	public ResultSet getRsTonKhoKhuVuc() {
		// TODO Auto-generated method stub
		return this.rsTonKhoKhuvuc;
	}

	@Override
	public String[] getmangTensku() {
		// TODO Auto-generated method stub
		return this.mangTensku;
	}



	
	

}
