package geso.dms.center.beans.bcchart.imp;

import geso.dms.center.beans.bcchart.IBcchart;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Phan_Trang;
import geso.dms.center.util.Utility;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Random;

public class Bcchart extends Phan_Trang implements IBcchart{

	private static final long serialVersionUID = 1L;
	String view;
	String userId;
	String id;
	String nam;
	String thang;
	String theomuc;
	String donvitinh;
	String[] mangtenDiaban;	
	String[] mangidDiaban;
	String[] mangtenMien;	
	String[] mangidMien;
	String[] mangidNSP;
	String[] mangtenNSP;
	String[] mangtenSPmoi;
	String[] mangidSPmoi;
	String[] mangtenSPChuLuc;
	String[] mangidSPChuLuc;
	
	ResultSet rsTemp;
	ResultSet rsTonKhoDiaBan;
	ResultSet rsTonKhoMien;
	ResultSet rsDoanhSoDiaBan;
	ResultSet rsDoanhSoMien;
	ResultSet rsDoanhThuDiaBan;
	ResultSet rsDoanhThuMien;
	ResultSet rsDoanhSoNSP;
	ResultSet rsDoanhSoSPMoi;
	ResultSet rsDoanhSoSPChuLuc;
	
	String msg;
	String date;
	String ck;
	dbutils db;
	
	String data_doanhthu;

	ResultSet rs;
	ResultSet SPrs;
	ResultSet rsVung;
	ResultSet rsKenh;
	String spId;
	String spVungId ;
	String spKenhId ;
	
	
	public ResultSet getRsVung() {
		
		String query = "\n select pk_seq,ten,diengiai from vung " +
		"\n where 1 = 1 ";
		if(this.phanloai.equals("2") )
		{
			if(this.loainv.equals("2")) //asm
				query += "\n	  and pk_seq in ( "+ geso.dms.center.util.Utility.get_VUNG_from_ASM(" select top(1) asm_fk from NHANVIEN where PK_SEQ ='"+userId+"' ")  +" ) ";
			else if(this.loainv.equals("3"))// gsbh
				query += "\n	  and pk_seq in ( "+ geso.dms.center.util.Utility.get_VUNG_from_GSBH(" select top(1) gsbh_fk from NHANVIEN where PK_SEQ ='"+userId+"' ")  +" ) ";
			else if(this.loainv.equals("1"))// rsm
				query += "\n	  and pk_seq in ( "+ geso.dms.center.util.Utility.get_VUNG_from_BM(" select top(1) bm_fk from NHANVIEN where PK_SEQ ='"+userId+"' ")  +" ) ";
		}
		return db.get(query);
	}
	public void setRsVung(ResultSet rsVung) {
		this.rsVung = rsVung;
	}
	public ResultSet getRsKenh() {
		String query = " select pk_seq,ten,diengiai from kenhbanhang where trangthai = 1  and pk_seq in ("+geso.dms.center.util.Utility.PhanQuyenKBH(this.userId)+") ";
		
		return db.get(query);
	}
	public void setRsKenh(ResultSet rsKenh) {
		this.rsKenh = rsKenh;
	}
	public String getSpVungId() {
		return spVungId;
	}
	public void setSpVungId(String spVungId) {
		this.spVungId = spVungId;
	}
	public String getSpKenhId() {
		return spKenhId;
	}
	public void setSpKenhId(String spKenhId) {
		this.spKenhId = spKenhId;
	}
	public String getSpId() {
		return spId;
	}
	public void setSpId(String spId) {
		this.spId = spId;
	}
	
	public ResultSet getSpRs() {
		String st = " select  a.pk_seq,a.ma,a.ten from sanpham a   " +
					" where  exists ( select 1 from donhang_sanpham x where a.pk_seq = x.sanpham_fk ) or a.trangthai = 1   ";
		return db.get(st);
	}
	
	
	public Bcchart()
	{
		this.userId = "";
		this.id = "";
		this.theomuc = "0";
		this.msg = "";
		Calendar cal = Calendar.getInstance();
		int year_ = cal.get(Calendar.YEAR);
		this.nam = year_ + "";
		int thang_= cal.get(Calendar.MONTH)+1;		
		this.thang = thang_ + "";
		
		if(this.thang.trim().length() == 1)
		{
			this.thang = "0"+ this.thang;
		}
		
		this.db = new dbutils();
		/*this.setDiaBan_VungMien();
		this.setNhomSanPham();
		this.setSanPhamMoi_ChuLuc();*/
		this.spId = "";
		this.spVungId = "";
		this.spKenhId = "";
		this.donvitinh = "";
		this.data_doanhthu = "";
		this.view = "";
	}	

	public String getId()
	{
		return this.id;
	}

	public void setId(String Id)
	{
		this.id = Id;
	}

	public String getUserId()
	{
		return this.userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public String getMsg()
	{
		return this.msg;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}
	public String setChietKhau(String date)
	{
		
        //if (!date.contains("2014-07"))
        {
        	//System.out.println("Khác tháng 07");
            this.ck =" select hoadon_fk,SUM((ck.chietkhau*( 1+ ck.thueVAT/100)))  as AVAT_CK " +
            " from HOADON_CHIETKHAU ck" +
            " group by hoadon_fk   ";
        }
        /*else
        {
        	System.out.println("Tháng 07");
        	this.ck=  "          	select hoadon_fk,SUM((ck.chietkhau*( 1+ ck.thueVAT/100)))  as AVAT_CK        " +
                    "          	from         " +
                    "          	(          " +
                    "          			select b.hoadon_fk, N''CN5'' as diengiai, sum(chietkhau) as chietkhau, thueVAT          " +
                    "          			from DONHANG_SANPHAM  a inner join HOADON_DDH b on a.donhang_fk = b.ddh_fk          " +
                    "          			where a.thueVAT = ''5''         " +
                    "          			group by b.hoadon_fk, thueVAT          " +
                    "          			union    all       " +
                    "          			select b.hoadon_fk, N''CN10'' as diengiai, sum(chietkhau) as chietkhau, thueVAT          " +
                    "          			from DONHANG_SANPHAM  a inner join HOADON_DDH b on a.donhang_fk = b.ddh_fk          " +
                    "          			where a.thueVAT = ''10''         " +
                    "          			group by b.hoadon_fk, thueVAT          " +
                    "          			union  all       " +
                    "          			select b.hoadon_fk, a.diengiai, sum( a.thanhtoan / ( 1  + ptTHUE / 100 )  ) as chietkhau, ptTHUE as thueVAT         " +
                    "          			from DUYETTRAKHUYENMAI_DONHANG a inner join HOADON_DDH b on a.donhang_fk = b.ddh_fk             " +
                    "          			group by b.hoadon_fk, a.diengiai, ptTHUE           " +
                    "          		union           " +
                    "          			select b.hoadon_fk, a.maloai as diengiai, sum(a.chietkhau) as chietkhau, ptVAT as thueVAT         " +
                    "          			from DONHANG_CHIETKHAUBOSUNG a inner join HOADON_DDH b on a.donhang_fk = b.ddh_fk          " +
                    "          			where chietkhau != 0         " +
                    "          			group by b.hoadon_fk, a.maloai, ptVAT          " +
                    "          	)          " +
                    "          	ck left join LOAICHIETKHAU loai on ck.diengiai = loai.maloai          " +
                    "          	inner join HOADON hd on hd.PK_SEQ=ck.HOADON_FK       " +
                    "          	where hd.LOAIHOADON =0 and hd.TRANGTHAI in (2,4)  and hd.NgayXuatHD like ''%" + date + "%'' " +
                    "          	group by HOADON_FK       ";
        }*/
        return this.ck;
	}

	public Boolean CheckDate()
	{
		if(this.nam.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn năm";
			return false;
		}
		
		if(this.thang.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn tháng";
			return false;
		}
		if(Integer.parseInt(thang)<10)
			this.date = nam+"-0"+thang;
		else
			this.date=nam+"-"+thang;
		
		return true;
	}
	//LẤY ĐỊA BÀN & VÙNG MIỀN
	public void setDiaBan_VungMien()
	{
		String query="";
		// LẤY ARR ĐỊA BÀN CÓ DOANH SỐ
		query="select PK_SEQ,TEN "+
				"	from( "+
				"		 SELECT C.PK_SEQ,C.TEN   "+
				"		 FROM HOADON A INNER JOIN NHAPHANPHOI B ON B.PK_SEQ=A.NPP_FK INNER JOIN TINHTHANH C on C.PK_SEQ=B.TINHTHANH_FK "+
				"		 WHERE B.TRANGTHAI=1 "+
				"		 GROUP BY C.PK_SEQ,C.TEN "+
				"	)as SoucerTable "+
				"	group by PK_SEQ,TEN"; 
		rsTemp=this.db.get(query);		
		
		try{
			String tmpIdDiaBan="";
			String tmpTenDiaBan="";
			while (rsTemp.next())
			{
				tmpIdDiaBan+=rsTemp.getString("pk_seq")+"__";
				tmpTenDiaBan+=rsTemp.getString("ten")+"__";
			}

			this.mangidDiaban=tmpIdDiaBan.split("__");
			this.mangtenDiaban=tmpTenDiaBan.split("__");
			
			rsTemp.close();
		}catch (Exception e) {
			this.msg="Không tìm thấy khu vực nào";
		}

		// LẤY ARR VÙNG CÓ NPP HOẠT ĐỘNG
		query="SELECT distinct v.PK_SEQ,v.ten "
				+ "FROM NHAPHANPHOI npp inner join KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK "
				+ "inner join VUNG v on kv.VUNG_FK = v.PK_SEQ WHERE npp.TRANGTHAI = 1";		
		rsTemp=this.db.get(query);		
		
		try{
			String tmpIdMien="";
			String tmpTenMien="";
			while (rsTemp.next())
			{
				tmpIdMien+=rsTemp.getString("pk_seq")+"__";
				tmpTenMien+=rsTemp.getString("ten")+"__";
			}
			
			this.mangidMien=tmpIdMien.split("__");
			this.mangtenMien=tmpTenMien.split("__");
			
			rsTemp.close();
		}catch (Exception e) {
			this.msg="Không tìm thấy Miền nào";
		}
	}
	
	//LẤY NHÓM SẢN PHẨM
	public void setNhomSanPham()
	{
		String query="";
		// LẤY ARR NHÓM SẢN PHẨM HIỆN CÓ
		query="SELECT distinct PK_SEQ,TEN  FROM NHOMSANPHAM";
		rsTemp=this.db.get(query);		
		
		try{
			String tmpidNSP="";
			String tmptenNSP="";
			while (rsTemp.next())
			{
				tmpidNSP+=rsTemp.getString("pk_seq")+"__";
				tmptenNSP+=rsTemp.getString("ten")+"__";
			}

			this.mangidNSP=tmpidNSP.split("__");
			this.mangtenNSP=tmptenNSP.split("__");
			
			rsTemp.close();
		}catch (Exception e) {
			this.msg="Không tìm thấy nhóm sản phẩm nào";
		}
	}
	//LẤY SẢN PHẨM MỚI VÀ CHỦ LỰC
	public void setSanPhamMoi_ChuLuc()
	{
		String query="";
		// LẤY ARR SẢN PHẨM MỚI
		query="SELECT distinct SP.PK_SEQ as PK_SEQ,SP.TEN as TEN  "+
				"	FROM SANPHAM SP inner join HOADON_SP HDSP on SP.PK_SEQ = HDSP.SANPHAM_FK "+
				"	WHERE SP.SPMOI=1";
		rsTemp=this.db.get(query);		
		
		try{
			String tmpIdSPmoi="";
			String tmpTenSPmoi="";
			while (rsTemp.next())
			{
				tmpIdSPmoi+=rsTemp.getString("pk_seq")+"__";
				tmpTenSPmoi+=rsTemp.getString("ten")+"__";
			}

			this.mangidSPmoi=tmpIdSPmoi.split("__");
			this.mangtenSPmoi=tmpTenSPmoi.split("__");
			
			rsTemp.close();
		}catch (Exception e) {
			this.msg="Không tìm thấy sản phẩm mới nào";
		}

		// LẤY ARR SẢN PHẨM CHỦ LỰC 
		query="SELECT distinct SP.PK_SEQ as PK_SEQ,SP.TEN as TEN  "+
				"	FROM SANPHAM SP inner join HOADON_SP HDSP on SP.PK_SEQ = HDSP.SANPHAM_FK "+
				"	WHERE SP.SPCHULUC=1";
		rsTemp=this.db.get(query);		
		
		try{
			String tmpIdSPChuLuc="";
			String tmpTenSPChuLuc="";
			while (rsTemp.next())
			{
				tmpIdSPChuLuc+=rsTemp.getString("pk_seq")+"__";
				tmpTenSPChuLuc+=rsTemp.getString("ten")+"__";
			}
			
			this.mangidSPChuLuc=tmpIdSPChuLuc.split("__");
			this.mangtenSPChuLuc=tmpTenSPChuLuc.split("__");
			
			rsTemp.close();
		}catch (Exception e) {
			this.msg="Không tìm thấy sản phẩm chủ lực nào";
		}
	}
	
	
	//LẤY BÁO CÁO BIỂU ĐỒ
	public void initTonKho(String query) {
		if(query.isEmpty())
		{
			if(CheckDate()) //CHECK NGÀY THÁNG HỢP LỆ
			{
				// LẤY BÁO CÁO TỒN KHO MIỀN
				query="declare @cols varchar(max), @sql nvarchar(max) "+
						"	set @cols = '[' + REPLACE( "+
						"			(SELECT distinct v.PK_SEQ as [data()] "+
						"							FROM NHAPP_KHO nppkho inner join NHAPHANPHOI npp on nppkho.NPP_FK= npp.PK_SEQ inner join "+
						"							 KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK join VUNG v on kv.VUNG_FK = v.PK_SEQ "+
						"							 WHERE npp.TRANGTHAI = 1 and nppkho.AVAILABLE>0 "+
						"			FOR XML PATH('') "+
						"				),' ','],[') + ']' ; "+
						"	set @sql = ' "+
						"		SELECT N''Ton kho'' as type, ' + @cols + ' "+
						"		FROM( "+
						"			SELECT cast(sum(nppkho.AVAILABLE) as numeric(18,0)) as TonKho,v.pk_seq "+
						"			FROM NHAPP_KHO nppkho inner join NHAPHANPHOI npp on nppkho.NPP_FK= npp.PK_SEQ inner join "+
						"			KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK join VUNG v on kv.VUNG_FK = v.PK_SEQ "+
						"			WHERE npp.TRANGTHAI =1 and nppkho.AVAILABLE >0 "+
						"						and npp.pk_seq in ( select npp_fk from phamvihoatdong where nhanvien_fk =''"+this.getUserId()+"'') "+
						"			Group by v.pk_seq "+
						"			) a "+
						"		 PIVOT "+
						"		( "+
						"			SUM(a.TonKho) FOR a.PK_SEQ  IN ('+@cols +') "+
						"		) AS PIVOTTable ' "+
						"	exec (@sql)"; 
				System.out.println("Lấy báo cáo tồn kho miền: "+query);
				this.rsTonKhoMien=this.db.get(query);
				
				// LẤY BÁO CÁO TỒN KHO ĐỊA BÀN
				query="declare @cols varchar(max), @sql nvarchar(max) "+
						"	set @cols = '[' + REPLACE( "+
						"			(SELECT distinct PK_SEQ AS [data()] FROM TINHTHANH "+
						"			WHERE PK_SEQ in (SELECT distinct TINHTHANH_FK  "+
						"							FROM NHAPP_KHO nppkho inner join NHAPHANPHOI npp on nppkho.NPP_FK= npp.PK_SEQ inner join TINHTHANH tt on npp.TINHTHANH_FK= tt.PK_SEQ "+
						"							WHERE nppkho.AVAILABLE>0 and npp.TRANGTHAI=1) "+
						"			FOR XML PATH('') "+
						"				),' ','],[') + ']' ; "+
						"	set @sql = ' "+
						"		SELECT N''Ton kho'' as type, ' + @cols + ' "+
						"		FROM( "+
						"			SELECT cast(sum(nppkho.AVAILABLE) as numeric(18,0)) as TonKho,tt.pk_seq "+
						"			FROM NHAPP_KHO nppkho inner join NHAPHANPHOI npp on nppkho.NPP_FK= npp.PK_SEQ inner join TINHTHANH tt on npp.TINHTHANH_FK= tt.PK_SEQ "+
						"			WHERE npp.TRANGTHAI=1 and nppkho.AVAILABLE>0 "+
						"						and npp.pk_seq in ( select npp_fk from phamvihoatdong where nhanvien_fk =''"+this.getUserId()+"'') "+
						"			Group by tt.pk_seq "+
						"			) a "+
						"		 PIVOT "+
						"		( "+
						"			SUM(a.TonKho) FOR a.PK_SEQ  IN ('+@cols +') "+
						"		) AS PIVOTTable ' "+
						" exec (@sql)";
				System.out.println("Lấy tồn kho địa bàn: "+query);
				this.rsTonKhoDiaBan=this.db.get(query);
			}
			else
			{
				return;
			}
		}
		else
		{
			if(CheckDate()) //CHECK NGÀY THÁNG HỢP LỆ
			{
				
			}
			else
			{
				return;
			}
		}	
	}

	public void initDoanhSo(String query) 
	{
		if(query.isEmpty())
		{
			if(CheckDate()) //CHECK NGÀY THÁNG HỢP LỆ
			{
				setChietKhau(this.date);
				// LẤY BÁO CÁO DOANH SỐ ĐỊA BÀN
				query=	" declare @cols varchar(max), @sql nvarchar(max) " + 
						"	set @cols = '[' + REPLACE(  " + 
						"			(SELECT PK_SEQ AS [data()] " + 
						"				from( " + 
						"					 SELECT C.PK_SEQ,C.TEN    " + 
						"					 FROM HOADON A INNER JOIN NHAPHANPHOI B ON B.PK_SEQ=A.NPP_FK INNER JOIN TINHTHANH C on C.PK_SEQ=B.TINHTHANH_FK " + 
						"					 WHERE B.TRANGTHAI=1 " + 
						"					 GROUP BY C.PK_SEQ,C.TEN				 " + 
						"				)as SoucerTable " + 
						"				group by PK_SEQ,TEN  " + 
						"			FOR XML PATH('') " + 
						"				),' ','],[') + ']' ; " + 
						" set @sql = '	SELECT ''Doanh so'' as type, '+@cols+'   " + 
						"				FROM(    " + 
						"							select TT_PK as PK_SEQ, sum(db.doanhso)/1000000 as doanhso " + 
						"					from (   " + 
						"						select npp.TINHTHANH_FK as TT_PK,db.doanhso, db.chietkhau, db.doanhthu from (  " + 
						"						select 	hdOTC.NPP_FK,  " + 
						"						sum(hdOTC.AVAT) as doanhso, sum(isnull(ck.AVAT_CK,0)) as chietkhau,   " + 
						"						sum(hdOTC.AVAT - isnull(ck.AVAT_CK,0)) as doanhthu  " + 
						"								from   " + 
						"								(  select   " + 
						"									a.NPP_FK,  " + 
						"									b.HOADON_FK ,  " + 
						"									round(sum(b.SoLuong*b.DONGIA*(1+b.vat/100) ),0) as AVAT  " + 
						"									from HOADON a inner join HOADON_SP b on b.HOADON_FK = a.PK_SEQ     " + 
						"									where a.LOAIHOADON =0 and a.TRANGTHAI in (2,4)  and a.NgayXuatHD like ''%"+date+"%''  " + 
						"									and a.NPP_FK in ( select npp_fk from phamvihoatdong where nhanvien_fk = ''"+this.getUserId()+"'')  " + 
						"									group by b.HOADON_FK, a.NPP_FK  " + 
						"									)as hdOTC  " + 
						"					left join ( "+ ck + //CHIẾT KHẤU Ở ĐÂY NÀY
						"								)as ck on ck.hoadon_fk = hdOTC.HOADON_FK  " + 
						"											group by hdOTC.NPP_FK  " + 
						"							) as db   " + 
						"							inner join NHAPHANPHOI npp on npp.PK_SEQ = db.NPP_FK    " + 
						"							where npp.PK_SEQ in ( select npp_fk from phamvihoatdong where nhanvien_fk = ''"+this.getUserId()+"'')  " + 
						"							group by npp.TINHTHANH_FK,db.doanhso, db.chietkhau, db.doanhthu " + 
						"				union all  " + 
						"				select npp.TINHTHANH_FK as TT_PK,hdETC.AVAT as doanhso, hdETC.AVAT_CK as chietkhau, (hdETC.AVAT - hdETC.AVAT_CK) as doanhthu 					 " + 
						"					from             " + 
						"					(          " + 
						"							select  HOADON_FK,npp_fk,KHACHHANG_fk,ddkd_fk,  " + 
						"								sum(soluong) as soluong, ( sum( soluong * dongia ) / sum(soluong) ) as dongia,       " + 
						"								sum( soluong * dongia )  as BVAT,( sum( soluong * dongia*thuexuat/100 ) ) as VAT,     " + 
						"								sum( soluong * dongia*(1+thuexuat/100 ) ) as AVAT,   " + 
						"										sum(isnull(chietkhau,0)*(1+thuexuat/100)) as AVAT_CK,              " + 
						"										sum(isnull(thuexuat,0)) as BVAT_CK           " + 
						"							from (      " + 
						"								select  c.HOADON_FK,a.npp_fk,a.KHACHHANG_fk,c.chietkhau,c.vat,   " + 
						"									(      " + 
						"										select top(1) bb.DDKD_FK       " + 
						"										from ERP_HOADONNPP_DDH aa inner join ERP_DONDATHANGNPP bb on bb.PK_SEQ=aa.DDH_FK       " + 
						"										where aa.HOADONNPP_FK=c.HOADON_FK  " + 
						"									) as ddkd_fk ,   " + 
						"										case when c.donvitinh = e.donvi then c.soluong else c.soluong * ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as soluong,     " + 
						"										case when c.donvitinh = e.donvi then c.dongia else c.dongia * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as dongia, c.vat as thuexuat      " + 
						"								from ERP_HOADONNPP a     " + 
						"									inner join ERP_HOADONNPP_SP c on a.pk_seq = c.hoadon_fk      " + 
						"									inner join SANPHAM d on c.sanpham_fk = d.pk_seq     " + 
						"									inner join DONVIDOLUONG e on d.DVDL_FK = e.pk_seq     " + 
						"									where 1=1 and c.SOLUONG > 0 and a.trangthai not in ( 1 , 3, 5 )   " + 
						"									 and a.NgayXuatHD like ''%"+date+"%'' " + 
						"							)ETC   " + 
						"							group by NPP_FK,KHACHHANG_FK,ddkd_fk,HOADON_FK   " + 
						"					)as hdETC         " + 
						"					inner join ERP_HOADONNPP hd on hd.PK_SEQ = hdETC.HOADON_FK      " + 
						"					inner join NHAPHANPHOI npp on npp.PK_SEQ=hdETC.NPP_FK					  " + 
						"					where npp.PK_SEQ in ( select npp_fk from phamvihoatdong where nhanvien_fk =''"+this.getUserId()+"'' ) " + 
						"					group by npp.TINHTHANH_FK,hdETC.AVAT, hdETC.AVAT_CK " + 
						"				) as db group by db.TT_PK " + 
						"	) a   " + 
						"		PIVOT   " + 
						"	( SUM(a.doanhso) FOR a.PK_SEQ  IN ('+@cols+') ) AS PIVOTTable'    " + 
						"exec(@sql)  " ;
				
				System.out.println("Lấy doanh số theo địa bàn: "+query);
				this.rsDoanhSoDiaBan=this.db.get(query);
				
				// LẤY BÁO CÁO DOANH SỐ MIỀN
				query="declare @cols varchar(max), @sql varchar(max) " + 
						"set @cols = '[' + REPLACE(  " + 
						"			(SELECT distinct v.PK_SEQ as [data()]  " + 
						"								FROM NHAPHANPHOI npp inner join KHUVUC kv  " + 
						"								on kv.PK_SEQ = npp.KHUVUC_FK inner join VUNG v on kv.VUNG_FK = v.PK_SEQ " + 
						"								 WHERE npp.TRANGTHAI = 1 " + 
						"			FOR XML PATH('') " + 
						"				),' ','],[') + ']' ; " + 
						"set @sql = ' " + 
						"			SELECT N''Doanh số'' as type, '+@cols+' " + 
						"			FROM( " + 
						"				select VUNG_PK as PK_SEQ, sum(db.doanhso)/1000000 as doanhso " + 
						"			from (   " + 
						"				select v.PK_SEQ as VUNG_PK,db.doanhso, db.chietkhau, db.doanhthu from (  " + 
						"				select 	hdOTC.NPP_FK,  " + 
						"				sum(hdOTC.AVAT) as doanhso, sum(isnull(ck.AVAT_CK,0)) as chietkhau,   " + 
						"				sum(hdOTC.AVAT - isnull(ck.AVAT_CK,0)) as doanhthu  " + 
						"						from   " + 
						"						(  select   " + 
						"							a.NPP_FK,  " + 
						"							b.HOADON_FK ,  " + 
						"							round(sum(b.SoLuong*b.DONGIA*(1+b.vat/100) ),0) as AVAT  " + 
						"							from HOADON a inner join HOADON_SP b on b.HOADON_FK = a.PK_SEQ     " + 						
						"							where a.LOAIHOADON =0 and a.TRANGTHAI in (2,4)  and a.NgayXuatHD like ''%"+date+"%''  " + 
						"							and a.NPP_FK in ( select npp_fk from phamvihoatdong where nhanvien_fk = ''"+this.getUserId()+"'')  " + 
						"							group by b.HOADON_FK, a.NPP_FK  " + 
						"							)as hdOTC  " + 
						"						left join ( "+ ck + //########################//
						"					)as ck on ck.hoadon_fk = hdOTC.HOADON_FK  " + 
						"					group by hdOTC.NPP_FK  " + 
						"				) as db   " + 
						"				inner join NHAPHANPHOI npp on npp.PK_SEQ = db.NPP_FK    " + 
						"			    inner join KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK  " + 
						"				inner join VUNG v on kv.VUNG_FK = v.PK_SEQ " + 
						"			    where npp.PK_SEQ in ( select npp_fk from phamvihoatdong where nhanvien_fk = ''100002'') " + 
						"				union all  " + 
						"				select v.PK_SEQ as PK_SEQ,hdETC.AVAT as doanhso, hdETC.AVAT_CK as chietkhau, (hdETC.AVAT - hdETC.AVAT_CK) as doanhthu				 " + 
						"				from             " + 
						"				(          " + 
						"						select  HOADON_FK,npp_fk,KHACHHANG_fk,ddkd_fk,   " + 
						"							sum(soluong) as soluong, ( sum( soluong * dongia ) / sum(soluong) ) as dongia,       " + 
						"							sum( soluong * dongia )  as BVAT,( sum( soluong * dongia*thuexuat/100 ) ) as VAT,     " + 
						"							sum( soluong * dongia*(1+thuexuat/100 ) ) as AVAT,   " + 
						"									sum(isnull(chietkhau,0)*(1+thuexuat/100)) as AVAT_CK,              " + 
						"									sum(isnull(thuexuat,0)) as BVAT_CK           " + 
						"						from (      " + 
						"							select  c.HOADON_FK,a.npp_fk,a.KHACHHANG_fk,c.chietkhau,c.vat,   " + 
						"								(      " + 
						"									select top(1) bb.DDKD_FK       " + 
						"									from ERP_HOADONNPP_DDH aa inner join ERP_DONDATHANGNPP bb on bb.PK_SEQ=aa.DDH_FK       " + 
						"									where aa.HOADONNPP_FK=c.HOADON_FK  " + 
						"								) as ddkd_fk ,   " + 
						"									case when c.donvitinh = e.donvi then c.soluong else c.soluong * ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as soluong,     " + 
						"									case when c.donvitinh = e.donvi then c.dongia else c.dongia * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as dongia, c.vat as thuexuat      " + 
						"							from ERP_HOADONNPP a     " + 
						"								inner join ERP_HOADONNPP_SP c on a.pk_seq = c.hoadon_fk      " + 
						"								inner join SANPHAM d on c.sanpham_fk = d.pk_seq     " + 
						"								inner join DONVIDOLUONG e on d.DVDL_FK = e.pk_seq     " + 
						"								where 1=1 and c.SOLUONG > 0 and a.trangthai not in ( 1 , 3, 5 )   " + 
						"								 and a.NgayXuatHD like ''%"+date+"%'' " + 
						"						)ETC   " + 
						"						group by NPP_FK,KHACHHANG_FK,ddkd_fk,HOADON_FK   " + 
						"				)as hdETC " + 
						"				inner join ERP_HOADONNPP hd on hd.PK_SEQ = hdETC.HOADON_FK      " + 
						"				inner join NHAPHANPHOI npp on npp.PK_SEQ=hdETC.NPP_FK " + 
						"				inner join KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK  " + 
						"				inner join VUNG v on kv.VUNG_FK = v.PK_SEQ     " + 
						"				where npp.PK_SEQ in ( select npp_fk from phamvihoatdong where nhanvien_fk =''"+this.getUserId()+"'')  " + 
						"				) as db group by db.VUNG_PK		 " + 
						"		) a " + 
						"			PIVOT " + 
						"		( SUM(a.doanhso) FOR a.PK_SEQ  IN ('+@cols+') ) AS PIVOTTable' " + 
						"exec(@sql) ";
				
				System.out.println("Lấy doanh số theo miền: "+ query);
				this.rsDoanhSoMien=this.db.get(query);
				
				// LẤY DOANH SỐ NHÓM SẢN PHẨM
				query="declare @cols varchar(max), @sql nvarchar(max) " + 
						"	set @cols = '[' + REPLACE(  " + 
						"			(SELECT distinct PK_SEQ AS [data()] FROM NHOMSANPHAM " + 
						"			FOR XML PATH('') " + 
						"				),' ','],[') + ']' ; " + 
						"set @sql = N' " + 
						"			SELECT N''Doanh số'' as type,  '+@cols+' " + 
						"			FROM ( " + 
						"			 select NSP_FK as PK_SEQ, sum(db.doanhso)/1000000 as doanhso " + 
						"				from (   " + 
						"					select db.NSP_FK ,db.doanhso from (  " + 
						"					select 	hdOTC.NSP_FK,  " + 
						"						hdOTC.AVAT as doanhso " + 
						"							from   " + 
						"							(	select   " + 
						"								nsp.PK_SEQ as NSP_FK,  " + 
						"								round(sum(b.SoLuong*b.DONGIA*(1+b.vat/100) ),0) as AVAT  " + 
						"								from HOADON a inner join HOADON_SP b on b.HOADON_FK = a.PK_SEQ     " + 
						"								inner JOIN NHOMSANPHAM_SANPHAM nsp_sp on nsp_sp.SP_FK = b.SANPHAM_FK inner join NHOMSANPHAM nsp on nsp.PK_SEQ = nsp_sp.NSP_FK " + 
						"								where a.LOAIHOADON =0 and a.TRANGTHAI in (2,4)  and a.NgayXuatHD like ''%"+date+"%''  " + 
						"								and a.NPP_FK in ( select npp_fk from phamvihoatdong where nhanvien_fk = ''"+this.getUserId()+"'')  " + 
						"								group by nsp.PK_SEQ " + 
						"						     " + 
						"								)as hdOTC  " + 
						"						) as db     " + 
						"					union all  " + 
						"					  " + 
						"					select nsp.PK_SEQ as NSP_FK,sum(hdETC.AVAT) as doanhso		 " + 
						"					from             " + 
						"					(          " + 
						"						select  sanpham_fk, " + 
						"							sum( soluong * dongia*(1+thuexuat/100 ) ) as AVAT " + 
						"						from (      " + 
						"							select  c.sanpham_fk,			  " + 
						"							case when c.donvitinh = e.donvi then c.soluong else c.soluong * ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as soluong,     " + 
						"							case when c.donvitinh = e.donvi then c.dongia else c.dongia * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as dongia, c.vat as thuexuat      " + 
						"							from ERP_HOADONNPP a     " + 
						"								inner join ERP_HOADONNPP_SP c on a.pk_seq = c.hoadon_fk      " + 
						"								inner join SANPHAM d on c.sanpham_fk = d.pk_seq     " + 
						"								inner join DONVIDOLUONG e on d.DVDL_FK = e.pk_seq     " + 
						"								where 1=1 and c.SOLUONG > 0 and a.trangthai not in ( 1 , 3, 5 )   " + 
						"								 and a.NgayXuatHD like ''%"+date+"%'' " + 
						"								and a.NPP_FK in ( select npp_fk from phamvihoatdong where nhanvien_fk =''"+this.getUserId()+"'' ) " + 
						"						)ETC   " + 
						"						group by sanpham_fk " + 
						"					)as hdETC         " + 
						"					inner JOIN NHOMSANPHAM_SANPHAM nsp_sp on nsp_sp.SP_FK = hdETC.SANPHAM_FK inner join NHOMSANPHAM nsp on nsp.PK_SEQ = nsp_sp.NSP_FK " + 
						"					group by nsp.PK_SEQ " + 
						"				) as db group by db.NSP_FK " + 
						"			) a " + 
						"			PIVOT " + 
						"			( SUM(a.doanhso) FOR a.PK_SEQ IN ('+@cols+') ) as PIVOTTable' " + 
						"exec (@sql)";
				System.out.println("Lấy báo cáo doanh số theo nhóm sản phẩm: "+query);
				this.rsDoanhSoNSP=this.db.get(query);
			}	
			else
			{
				return;
			}
		}
		else
		{
			if(CheckDate()) //CHECK NGÀY THÁNG HỢP LỆ
			{
				
			}
			else
			{
				return;
			}
		}	
	}

	public void initDoanhThu(String query) {
		if(query.isEmpty())
		{
			if(CheckDate()) //CHECK NGÀY THÁNG HỢP LỆ
			{
				setChietKhau(this.date);
				// LẤY BÁO CÁO DOANH THU THEO ĐỊA BÀN
				query=	" declare @cols varchar(max), @sql nvarchar(max) " + 
						"	set @cols = '[' + REPLACE(  " + 
						"			(SELECT PK_SEQ AS [data()] " + 
						"				from( " + 
						"					 SELECT C.PK_SEQ,C.TEN    " + 
						"					 FROM HOADON A INNER JOIN NHAPHANPHOI B ON B.PK_SEQ=A.NPP_FK INNER JOIN TINHTHANH C on C.PK_SEQ=B.TINHTHANH_FK " + 
						"					 WHERE B.TRANGTHAI=1 " + 
						"					 GROUP BY C.PK_SEQ,C.TEN				 " + 
						"				)as SoucerTable " + 
						"				group by PK_SEQ,TEN  " + 
						"			FOR XML PATH('') " + 
						"				),' ','],[') + ']' ; " + 
						" set @sql = '	SELECT ''Doanh thu'' as type, '+@cols+'   " + 
						"				FROM(    " + 
						"							select TT_PK as PK_SEQ, sum(db.doanhthu)/1000000 as doanhthu " + 
						"					from (   " + 
						"						select npp.TINHTHANH_FK as TT_PK,db.doanhso, db.chietkhau, db.doanhthu from (  " + 
						"						select 	hdOTC.NPP_FK,  " + 
						"						sum(hdOTC.AVAT) as doanhso, sum(isnull(ck.AVAT_CK,0)) as chietkhau,   " + 
						"						sum(hdOTC.AVAT - isnull(ck.AVAT_CK,0)) as doanhthu  " + 
						"								from   " + 
						"								(  select   " + 
						"									a.NPP_FK,  " + 
						"									b.HOADON_FK ,  " + 
						"									round(sum(b.SoLuong*b.DONGIA*(1+b.vat/100) ),0) as AVAT  " + 
						"									from HOADON a inner join HOADON_SP b on b.HOADON_FK = a.PK_SEQ     " + 
						"									where a.LOAIHOADON =0 and a.TRANGTHAI in (2,4)  and a.NgayXuatHD like ''%"+date+"%''  " + 
						"									and a.NPP_FK in ( select npp_fk from phamvihoatdong where nhanvien_fk = ''"+this.getUserId()+"'')  " + 
						"									group by b.HOADON_FK, a.NPP_FK  " + 
						"									)as hdOTC  " + 
						"					left join ( "+ ck + //CHIẾT KHẤU Ở ĐÂY NÀY
						"								)as ck on ck.hoadon_fk = hdOTC.HOADON_FK  " + 
						"											group by hdOTC.NPP_FK  " + 
						"							) as db   " + 
						"							inner join NHAPHANPHOI npp on npp.PK_SEQ = db.NPP_FK    " + 
						"							where npp.PK_SEQ in ( select npp_fk from phamvihoatdong where nhanvien_fk = ''"+this.getUserId()+"'')  " + 
						"							group by npp.TINHTHANH_FK,db.doanhso, db.chietkhau, db.doanhthu " + 
						"				union all  " + 
						"				select npp.TINHTHANH_FK as TT_PK,hdETC.AVAT as doanhso, hdETC.AVAT_CK as chietkhau, (hdETC.AVAT - hdETC.AVAT_CK) as doanhthu 					 " + 
						"					from             " + 
						"					(          " + 
						"							select  HOADON_FK,npp_fk,KHACHHANG_fk,ddkd_fk,  " + 
						"								sum(soluong) as soluong, ( sum( soluong * dongia ) / sum(soluong) ) as dongia,       " + 
						"								sum( soluong * dongia )  as BVAT,( sum( soluong * dongia*thuexuat/100 ) ) as VAT,     " + 
						"								sum( soluong * dongia*(1+thuexuat/100 ) ) as AVAT,   " + 
						"										sum(isnull(chietkhau,0)*(1+thuexuat/100)) as AVAT_CK,              " + 
						"										sum(isnull(thuexuat,0)) as BVAT_CK           " + 
						"							from (      " + 
						"								select  c.HOADON_FK,a.npp_fk,a.KHACHHANG_fk,c.chietkhau,c.vat,   " + 
						"									(      " + 
						"										select top(1) bb.DDKD_FK       " + 
						"										from ERP_HOADONNPP_DDH aa inner join ERP_DONDATHANGNPP bb on bb.PK_SEQ=aa.DDH_FK       " + 
						"										where aa.HOADONNPP_FK=c.HOADON_FK  " + 
						"									) as ddkd_fk ,   " + 
						"										case when c.donvitinh = e.donvi then c.soluong else c.soluong * ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as soluong,     " + 
						"										case when c.donvitinh = e.donvi then c.dongia else c.dongia * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as dongia, c.vat as thuexuat      " + 
						"								from ERP_HOADONNPP a     " + 
						"									inner join ERP_HOADONNPP_SP c on a.pk_seq = c.hoadon_fk      " + 
						"									inner join SANPHAM d on c.sanpham_fk = d.pk_seq     " + 
						"									inner join DONVIDOLUONG e on d.DVDL_FK = e.pk_seq     " + 
						"									where 1=1 and c.SOLUONG > 0 and a.trangthai not in ( 1 , 3, 5 )   " + 
						"									 and a.NgayXuatHD like ''%"+date+"%'' " + 
						"							)ETC   " + 
						"							group by NPP_FK,KHACHHANG_FK,ddkd_fk,HOADON_FK   " + 
						"					)as hdETC         " + 
						"					inner join ERP_HOADONNPP hd on hd.PK_SEQ = hdETC.HOADON_FK      " + 
						"					inner join NHAPHANPHOI npp on npp.PK_SEQ=hdETC.NPP_FK					  " + 
						"					where npp.PK_SEQ in ( select npp_fk from phamvihoatdong where nhanvien_fk =''"+this.getUserId()+"'' ) " + 
						"					group by npp.TINHTHANH_FK,hdETC.AVAT, hdETC.AVAT_CK " + 
						"				) as db group by db.TT_PK " + 
						"	) a   " + 
						"		PIVOT   " + 
						"	( SUM(a.doanhthu) FOR a.PK_SEQ  IN ('+@cols+') ) AS PIVOTTable'    " + 
						"exec(@sql)  " ;
				
				System.out.println("Lấy báo cáo doanh thu địa bàn: "+query);
				this.rsDoanhThuDiaBan=this.db.get(query);
				
				// LẤY BÁO CÁO DOANH THU THEO MIỀN
				query="declare @cols varchar(max), @sql varchar(max) " + 
						"set @cols = '[' + REPLACE(  " + 
						"			(SELECT distinct v.PK_SEQ as [data()]  " + 
						"								FROM NHAPHANPHOI npp inner join KHUVUC kv  " + 
						"								on kv.PK_SEQ = npp.KHUVUC_FK inner join VUNG v on kv.VUNG_FK = v.PK_SEQ " + 
						"								 WHERE npp.TRANGTHAI = 1 " + 
						"			FOR XML PATH('') " + 
						"				),' ','],[') + ']' ; " + 
						"set @sql = ' " + 
						"			SELECT N''Doanh thu'' as type, '+@cols+' " + 
						"			FROM( " + 
						"				select VUNG_PK as PK_SEQ, sum(db.doanhthu)/1000000 as doanhthu " + 
						"			from (   " + 
						"				select v.PK_SEQ as VUNG_PK,db.doanhso, db.chietkhau, db.doanhthu from (  " + 
						"				select 	hdOTC.NPP_FK,  " + 
						"				sum(hdOTC.AVAT) as doanhso, sum(isnull(ck.AVAT_CK,0)) as chietkhau,   " + 
						"				sum(hdOTC.AVAT - isnull(ck.AVAT_CK,0)) as doanhthu  " + 
						"						from   " + 
						"						(  select   " + 
						"							a.NPP_FK,  " + 
						"							b.HOADON_FK ,  " + 
						"							round(sum(b.SoLuong*b.DONGIA*(1+b.vat/100) ),0) as AVAT  " + 
						"							from HOADON a inner join HOADON_SP b on b.HOADON_FK = a.PK_SEQ     " + 				
						"							where a.LOAIHOADON =0 and a.TRANGTHAI in (2,4)  and a.NgayXuatHD like ''%"+date+"%''  " + 
						"							and a.NPP_FK in ( select npp_fk from phamvihoatdong where nhanvien_fk = ''"+this.getUserId()+"'')  " + 
						"							group by b.HOADON_FK, a.NPP_FK  " + 
						"							)as hdOTC  " + 
						"						left join ( "+ ck + 
						"					)as ck on ck.hoadon_fk = hdOTC.HOADON_FK  " + 
						"					group by hdOTC.NPP_FK  " + 
						"				) as db   " + 
						"				inner join NHAPHANPHOI npp on npp.PK_SEQ = db.NPP_FK    " + 
						"			    inner join KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK  " + 
						"				inner join VUNG v on kv.VUNG_FK = v.PK_SEQ " + 
						"			    where npp.PK_SEQ in ( select npp_fk from phamvihoatdong where nhanvien_fk = ''100002'') " + 
						"				union all  " + 
						"				select v.PK_SEQ as PK_SEQ,hdETC.AVAT as doanhso, hdETC.AVAT_CK as chietkhau, (hdETC.AVAT - hdETC.AVAT_CK) as doanhthu				 " + 
						"				from             " + 
						"				(          " + 
						"						select  HOADON_FK,npp_fk,KHACHHANG_fk,ddkd_fk,   " + 
						"							sum(soluong) as soluong, ( sum( soluong * dongia ) / sum(soluong) ) as dongia,       " + 
						"							sum( soluong * dongia )  as BVAT,( sum( soluong * dongia*thuexuat/100 ) ) as VAT,     " + 
						"							sum( soluong * dongia*(1+thuexuat/100 ) ) as AVAT,   " + 
						"									sum(isnull(chietkhau,0)*(1+thuexuat/100)) as AVAT_CK,              " + 
						"									sum(isnull(thuexuat,0)) as BVAT_CK           " + 
						"						from (      " + 
						"							select  c.HOADON_FK,a.npp_fk,a.KHACHHANG_fk,c.chietkhau,c.vat,   " + 
						"								(      " + 
						"									select top(1) bb.DDKD_FK       " + 
						"									from ERP_HOADONNPP_DDH aa inner join ERP_DONDATHANGNPP bb on bb.PK_SEQ=aa.DDH_FK       " + 
						"									where aa.HOADONNPP_FK=c.HOADON_FK  " + 
						"								) as ddkd_fk ,   " + 
						"									case when c.donvitinh = e.donvi then c.soluong else c.soluong * ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as soluong,     " + 
						"									case when c.donvitinh = e.donvi then c.dongia else c.dongia * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as dongia, c.vat as thuexuat      " + 
						"							from ERP_HOADONNPP a     " + 
						"								inner join ERP_HOADONNPP_SP c on a.pk_seq = c.hoadon_fk      " + 
						"								inner join SANPHAM d on c.sanpham_fk = d.pk_seq     " + 
						"								inner join DONVIDOLUONG e on d.DVDL_FK = e.pk_seq     " + 
						"								where 1=1 and c.SOLUONG > 0 and a.trangthai not in ( 1 , 3, 5 )   " + 
						"								 and a.NgayXuatHD like ''%"+date+"%'' " + 
						"			    				   " + 
						"						)ETC   " + 
						"						group by NPP_FK,KHACHHANG_FK,ddkd_fk,HOADON_FK   " + 
						"				)as hdETC " + 
						"				         " + 
						"				inner join ERP_HOADONNPP hd on hd.PK_SEQ = hdETC.HOADON_FK      " + 
						"				inner join NHAPHANPHOI npp on npp.PK_SEQ=hdETC.NPP_FK " + 
						"				inner join KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK  " + 
						"				inner join VUNG v on kv.VUNG_FK = v.PK_SEQ     " + 
						"				where npp.PK_SEQ in ( select npp_fk from phamvihoatdong where nhanvien_fk =''"+this.getUserId()+"'')  " + 
						"				 " + 
						"				) as db group by db.VUNG_PK		 " + 
						"		) a " + 
						"			PIVOT " + 
						"		( SUM(a.doanhthu) FOR a.PK_SEQ  IN ('+@cols+') ) AS PIVOTTable' " + 
						"exec(@sql) ";
				
				System.out.println("Lấy báo cáo doanh thu theo miền: "+query);
				this.rsDoanhThuMien=this.db.get(query);
			}
			else
			{
				return;
			}
		}
		else
		{
			if(CheckDate()) //CHECK NGÀY THÁNG HỢP LỆ
			{
				
			}
			else
			{
				return;
			}
		}	
	}
	
	public void initSanPham(String query) 
	{	
		if(query.isEmpty())
		{
			if(CheckDate()) //CHECK NGÀY THÁNG HỢP LỆ
			{
				// LẤY BÁO CÁO DOANH SỐ SẢN PHẨM MỚI
				query=	"declare @cols varchar(max), @sql nvarchar(max) " + 
						"	set @cols = '[' + REPLACE(  " + 
						"			(SELECT distinct SP.PK_SEQ AS [data()]  FROM SANPHAM SP inner join HOADON_SP HDSP on SP.PK_SEQ = HDSP.SANPHAM_FK  " + 
						"			WHERE SP.SPMOI = 1  " + 
						"			FOR XML PATH('') " + 
						"				),' ','],[') + ']' ; " + 
						"set @sql = ' " + 
						"			SELECT N''Doanh so'' as type,  '+@cols+' " + 
						"			FROM (  " + 
						"					select SP_FK as PK_SEQ, sum(db.doanhso)/1000000 as doanhso " + 
						"						from (   " + 
						"							select db.SP_FK ,db.doanhso from (  " + 
						"							select 	hdOTC.SP_FK,  " + 
						"								hdOTC.AVAT as doanhso " + 
						"									from   " + 
						"									(	select   " + 
						"										sp.PK_SEQ as SP_FK,  " + 
						"										round(sum(b.SoLuong*b.DONGIA*(1+b.vat/100) ),0) as AVAT  " + 
						"										from HOADON a inner join HOADON_SP b on b.HOADON_FK = a.PK_SEQ     " + 
						"										inner join SANPHAM sp on sp.PK_SEQ=b.SANPHAM_FK " + 
						"										where a.LOAIHOADON =0 and a.TRANGTHAI in (2,4)  and a.NgayXuatHD like ''%"+date+"%''  " + 
						"										and a.NPP_FK in ( select npp_fk from phamvihoatdong where nhanvien_fk = ''"+this.getUserId()+"'')  " + 
						"										and sp.SPMOI = 1 " + 
						"										group by sp.PK_SEQ						     " + 
						"										)as hdOTC  " + 
						"								) as db     " + 
						"							union all  " + 
						"							  " + 
						"							select hdETC.SANPHAM_FK as SP_FK ,sum(hdETC.AVAT) as doanhso		 " + 
						"							from             " + 
						"							(          " + 
						"								select  sanpham_fk, " + 
						"									sum( soluong * dongia*(1+thuexuat/100 ) ) as AVAT " + 
						"								from (      " + 
						"									select  c.sanpham_fk,			  " + 
						"									case when c.donvitinh = e.donvi then c.soluong else c.soluong * ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as soluong,     " + 
						"									case when c.donvitinh = e.donvi then c.dongia else c.dongia * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as dongia, c.vat as thuexuat      " + 
						"									from ERP_HOADONNPP a     " + 
						"										inner join ERP_HOADONNPP_SP c on a.pk_seq = c.hoadon_fk      " + 
						"										inner join SANPHAM d on c.sanpham_fk = d.pk_seq     " + 
						"										inner join DONVIDOLUONG e on d.DVDL_FK = e.pk_seq     " + 
						"										where 1=1 and c.SOLUONG > 0 and a.trangthai not in ( 1 , 3, 5 )   " + 
						"										 and a.NgayXuatHD like ''%"+date+"%'' " + 
						"										and a.NPP_FK in ( select npp_fk from phamvihoatdong where nhanvien_fk =''"+this.getUserId()+"'' ) " + 
						"										 and d.SPMOI = 1 " + 
						"								)ETC   " + 
						"								group by sanpham_fk " + 
						"							)as hdETC         " + 
						"							group by hdETC.SANPHAM_FK " + 
						"						) as db group by db.SP_FK " + 
						"			) a " + 
						"			PIVOT " + 
						"			( SUM(a.doanhso) FOR a.PK_SEQ IN ('+@cols+') ) as PIVOTTable' " + 
						"exec (@sql)";
				System.out.println("Lấy doanh số theo sản phẩm mới: "+query);
				this.rsDoanhSoSPMoi=this.db.get(query);
				
				// LẤY BÁO CÁO DOANH SỐ SẢN PHẨM CHỦ LỰC
				
				query=	"declare @cols varchar(max), @sql nvarchar(max) " + 
						"	set @cols = '[' + REPLACE(  " + 
						"			(SELECT distinct SP.PK_SEQ AS [data()]  FROM SANPHAM SP inner join HOADON_SP HDSP on SP.PK_SEQ = HDSP.SANPHAM_FK  " + 
						"			WHERE SP.SPCHULUC = 1  " + 
						"			FOR XML PATH('') " + 
						"				),' ','],[') + ']' ; " + 
						"set @sql = ' " + 
						"			SELECT N''Doanh so'' as type,  '+@cols+' " + 
						"			FROM (  " + 
						"					select SP_FK as PK_SEQ, sum(db.doanhso)/1000000 as doanhso " + 
						"						from (   " + 
						"							select db.SP_FK ,db.doanhso from (  " + 
						"							select 	hdOTC.SP_FK,  " + 
						"								hdOTC.AVAT as doanhso " + 
						"									from   " + 
						"									(	select   " + 
						"										sp.PK_SEQ as SP_FK,  " + 
						"										round(sum(b.SoLuong*b.DONGIA*(1+b.vat/100) ),0) as AVAT  " + 
						"										from HOADON a inner join HOADON_SP b on b.HOADON_FK = a.PK_SEQ     " + 
						"										inner join SANPHAM sp on sp.PK_SEQ=b.SANPHAM_FK " + 
						"										where a.LOAIHOADON =0 and a.TRANGTHAI in (2,4)  and a.NgayXuatHD like ''%"+date+"%''  " + 
						"										and a.NPP_FK in ( select npp_fk from phamvihoatdong where nhanvien_fk = ''"+this.getUserId()+"'')  " + 
						"										 and sp.SPCHULUC = 1 " + 
						"										group by sp.PK_SEQ						     " + 
						"										)as hdOTC  " + 
						"								) as db     " + 
						"							union all  " + 
						"							  " + 
						"							select hdETC.SANPHAM_FK as SP_FK ,sum(hdETC.AVAT) as doanhso		 " + 
						"							from             " + 
						"							(          " + 
						"								select  sanpham_fk, " + 
						"									sum( soluong * dongia*(1+thuexuat/100 ) ) as AVAT " + 
						"								from (      " + 
						"									select  c.sanpham_fk,			  " + 
						"									case when c.donvitinh = e.donvi then c.soluong else c.soluong * ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as soluong,     " + 
						"									case when c.donvitinh = e.donvi then c.dongia else c.dongia * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as dongia, c.vat as thuexuat      " + 
						"									from ERP_HOADONNPP a     " + 
						"										inner join ERP_HOADONNPP_SP c on a.pk_seq = c.hoadon_fk      " + 
						"										inner join SANPHAM d on c.sanpham_fk = d.pk_seq     " + 
						"										inner join DONVIDOLUONG e on d.DVDL_FK = e.pk_seq     " + 
						"										where 1=1 and c.SOLUONG > 0 and a.trangthai not in ( 1 , 3, 5 )   " + 
						"										 and a.NgayXuatHD like ''%"+date+"%'' " + 
						"										and a.NPP_FK in ( select npp_fk from phamvihoatdong where nhanvien_fk =''"+this.getUserId()+"'' ) " + 
						"										and d.SPCHULUC = 1  " + 
						"								)ETC   " + 
						"								group by sanpham_fk " + 
						"							)as hdETC         " + 
						"							group by hdETC.SANPHAM_FK " + 
						"						) as db group by db.SP_FK " + 
						"			) a " + 
						"			PIVOT " + 
						"			( SUM(a.doanhso) FOR a.PK_SEQ IN ('+@cols+') ) as PIVOTTable' " + 
						"exec (@sql)";
				System.out.println("Lấy doanh số theo sản phẩm chủ lực: "+ query);
				this.rsDoanhSoSPChuLuc=this.db.get(query);
			}
			else
			{
				return;
			}
		}
		else
		{
			if(CheckDate()) //CHECK NGÀY THÁNG HỢP LỆ
			{
				
			}
			else
			{
				return;
			}
		}
	}

	public void DbClose()
	{
		try
		{
			this.db.shutDown();
		}
		catch (Exception e) {}
	}
	
	public String getThang() {		
		return this.thang;
	}
	
	public void setThang(String thang) {		
		this.thang=thang;
	}
	
	public String getNam() {
		return this.nam;
	}
	
	public void setnam(String nam) {
		this.nam=nam;		
	}

	public String[] getArrTenDiaBan() {
		return this.mangtenDiaban;
	}

	public String[] getArrIDDiaBan() {
		return this.mangidDiaban;
	}

	public String[] getArrTenMien() {
		return this.mangtenMien;
	}

	public String[] getArrIDMien() {
		return this.mangidMien;
	}
	
	public String[] getArrTenNSP() {
		return this.mangtenNSP;
	}

	public String[] getArrIDNSP() {
		return this.mangidNSP;
	}

	public ResultSet getRsTonKhoDiaBan() {
		return this.rsTonKhoDiaBan;
	}

	public ResultSet getRsTonKhoMien() {
		return this.rsTonKhoMien;
	}

	public ResultSet getRsDoanhSoDiaBan() {
		return this.rsDoanhSoDiaBan;
	}

	public ResultSet getRsDoanhSoMien() {
		return this.rsDoanhSoMien;
	}

	public ResultSet getRsDoanhThuDiaBan() {
		return this.rsDoanhThuDiaBan;
	}

	public ResultSet getRsDoanhThuMien() {
		return this.rsDoanhThuMien;
	}

	public ResultSet getRsDoanhSoNSP() {
		return this.rsDoanhSoNSP; 
	}
	
	public ResultSet getRsDoanhSoSPChuLuc() {		
		return this.rsDoanhSoSPChuLuc;
	}
	
	public ResultSet getRsDoanhSoSPMoi() {		
		return this.rsDoanhSoSPMoi;
	}

	public String[] getArrTenSanPhamMoi() {
		return this.mangtenSPmoi;
	}

	public String[] getArrIDSanPhamMoi() {
		return this.mangidSPmoi;
	}

	public String[] getArrTenSanPhamChuLuc() {
		return this.mangtenSPChuLuc;
	}

	public String[] getArrIDSanPhamChuLuc() {
		return this.mangidSPChuLuc;
	}

	
	public String getTheomuc() {

		return this.theomuc;
	}


	public void setTheomuc(String theomuc) {

		this.theomuc = theomuc;
	}

	
	public String getData_ChartDoanhthu()
	{

		return this.data_doanhthu;
	}


	public void setData_ChartDoanhthu(String msg) 
	{
		this.data_doanhthu = msg;
	}

	public void initData(String chartType) 
	{
		if( chartType.equals("thucdatchitieu") )
		{
			this.InitDoanhThu_Chart();
		}
		else if( chartType.equals("tangtruongdoanhso") )
		{
			this.InitBieuDoKD_Chart();
		}else if( chartType.equals("muc_do_tang_truong_san_pham") )
		{
			init_tien_do_san_pham();
		}else if( chartType.equals("tang_truong_doanh_so_theo_ngay") )
		{
			init_tien_do_doanh_so_ngay();
		}
		
		
		
	}
	ArrayList<Long> dataSanpham ;
	Long[][] dataDoanhSoNgay;
	
	public Long[][] getDataDoanhSoNgay() {
		return dataDoanhSoNgay;
	}
	//cylasion sua query na
	public void init_tien_do_san_pham()
	{
		dataSanpham = new ArrayList<Long>();
		if(this.spId.trim().length() <=0) return ;
		
		
		
		
		String denngay = this.thang;
		
		
		init_user();
		String  condition = "";
		if(this.phanloai.equals("2"))
		{
		
			condition += "\n and dh.kbh_fk in ( "+geso.dms.center.util.Utility.PhanQuyenKBH(this.userId)+"  )  " ;
			if(this.loainv.equals("1"))
			{	
				condition += "\n and dh.ddkd_fk in ( "+ geso.dms.center.util.Utility.get_DDKD_from_BM( " select top 1 bm_fk from nhanvien where pk_seq ="+this.userId+" " ) +" ) ";
								
			}
			else if(this.loainv.equals("2"))
			{
				condition += "\n and dh.ddkd_fk in ( "+ geso.dms.center.util.Utility.get_DDKD_from_ASM( " select top 1 asm_fk from nhanvien where pk_seq ="+this.userId+" " ) +" ) ";
				
			}
			else if(this.loainv.equals("3"))
			{
				condition += "\n and dh.ddkd_fk in ( "+ geso.dms.center.util.Utility.get_DDKD_from_GSBH( " select top 1 gsbh_Fk from nhanvien where pk_seq ="+this.userId+" " ) +" ) ";
				
			}
			else
			{			
				condition += " and dh.npp_fk in ("+geso.dms.center.util.Utility.Quyen_npp(this.userId)+") ";
			}
		}
		else
		{
			condition += " and dh.npp_fk in ("+this.nppId+") ";
		}
		
		if(this.spKenhId.trim().length()>0)
		{
			condition += "\n AND dh.KBH_FK = "+this.spKenhId;
		}
		if(this.spVungId.trim().length()>0)
		{
			condition += "\n AND EXISTS (SELECT KHUVUC_FK FROM dbo.NHAPHANPHOI NPP INNER JOIN KHUVUC KV ON KV.PK_SEQ = NPP.KHUVUC_FK WHERE NPP.PK_SEQ = dh.NPP_FK AND KV.VUNG_FK = "+this.spVungId+" ) ";
		}
		
		String query =  "\n  declare @ngaydaunam varchar(10) =convert( varchar(10),DATEADD(yy, DATEDIFF(yy, 0, '"+denngay+"'), 0),120) ; " +
						"\n 							 with doanhso as " +
						"\n 							 (  " +
						"\n 							  	select year(ngaynhap) nam , month(ngaynhap) thang, sum(sellout)sellout  " +
						"\n 							  	from  " +
						"\n 							  	(	  " +
						"\n 							 		select  dh.NGAYNHAP , sum(soluong*giamua) sellout	   " +
						"\n 							 		from DONHANG dh   " +
						"\n 							 		inner join DONHANG_SANPHAM dhsp on dhsp.DONHANG_FK = dh.PK_SEQ   " +
						"\n 							 		where dhsp.sanpham_fk = "+this.spId+" and dh.TRANGTHAI = 1 and NGAYNHAP >=@ngaydaunam and  NGAYNHAP <='"+denngay+"' " +
						"\n 											and not exists ( select 1 from DONHANGTRAVE x where x.trangthai = 3 and x.DONHANG_FK = dh.PK_SEQ)   "  + condition +
															
						"\n 							 		group by  dh.NGAYNHAP  " +
						"\n 						 			union all  " +
						"\n 				 					select  dh.NGAYNHAP , sum((-1)*soluong*giamua) sellout	   " +
						"\n 				 					from DONHANGTRAVE dh   " +
						"\n 				 					inner join DONHANGTRAVE_SANPHAM dhsp on dhsp.DONHANGTRAVE_FK = dh.PK_SEQ   " +			
						"\n 				 					where dhsp.sanpham_fk = "+this.spId+" and  dh.donhang_fk is null and  dh.TRANGTHAI = 3 and NGAYNHAP >=@ngaydaunam and  NGAYNHAP <='"+denngay+"' " +
																																						condition +
						"\n 				 					group by  dh.NGAYNHAP  " +
						"\n 				 				)ds  " +
						"\n 				 				group by year(ngaynhap)  , month(ngaynhap)					 " +
						"\n 				 ) " +
						"\n 		SELECT round(isnull(doanhso.sellout,0),0)sellout " +
						"\n 		FROM master..spt_values a " +
						"\n			left join doanhso on  a.number = doanhso.thang " +
						"\n 		WHERE Type = 'P' and number >= 1 and number <= month('"+denngay+"') " +
						"\n 		ORDER BY Number " ;
		System.out.println("query = "+ query);
		ResultSet rs = db.get(query);
		try {
			while(rs.next())
			{
				dataSanpham.add(rs.getLong("sellout"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	int isCuoiThang = 0;
	public int getIsCuoiThang() {
		return isCuoiThang;
	}
	public void setIsCuoiThang(int isCuoiThang) {
		this.isCuoiThang = isCuoiThang;
	}
	
	public void init_tien_do_doanh_so_ngay()
	{
		String condition  = "";
		if(this.spId != null && this.spId.trim().length() > 0)
		{
			condition += "  and dhsp.sanpham_fk in ("+this.spId+") ";
		}
		init_user();
		if(this.phanloai.equals("2"))
		{
		
			condition += "\n and dh.kbh_fk in ( "+geso.dms.center.util.Utility.PhanQuyenKBH(this.userId)+"  )  " ;
			if(this.loainv.equals("1"))
			{	
				condition += "\n and dh.ddkd_fk in ( "+ geso.dms.center.util.Utility.get_DDKD_from_BM( " select top 1 bm_fk from nhanvien where pk_seq ="+this.userId+" " ) +" ) ";
								
			}
			else if(this.loainv.equals("2"))
			{
				condition += "\n and dh.ddkd_fk in ( "+ geso.dms.center.util.Utility.get_DDKD_from_ASM( " select top 1 asm_fk from nhanvien where pk_seq ="+this.userId+" " ) +" ) ";
				
			}
			else if(this.loainv.equals("3"))
			{
				condition += "\n and dh.ddkd_fk in ( "+ geso.dms.center.util.Utility.get_DDKD_from_GSBH( " select top 1 gsbh_Fk from nhanvien where pk_seq ="+this.userId+" " ) +" ) ";
				
			}
			else
			{			
				condition += " and dh.npp_fk in ("+geso.dms.center.util.Utility.Quyen_npp(this.userId)+") ";
			}
		}
		else
		{
			condition += " and dh.npp_fk in ("+this.nppId+") ";
		}
		
		if(this.spKenhId.trim().length()>0)
		{
			condition += "\n AND dh.KBH_FK = "+this.spKenhId;
		}
		if(this.spVungId.trim().length()>0)
		{
			condition += "\n AND EXISTS (SELECT KHUVUC_FK FROM dbo.NHAPHANPHOI NPP INNER JOIN KHUVUC KV ON KV.PK_SEQ = NPP.KHUVUC_FK WHERE NPP.PK_SEQ = dh.NPP_FK AND KV.VUNG_FK = "+this.spVungId+" ) ";
		}
		
		int so_thang_lui = 4 ; // fixcung
		String denngay = this.thang;
		if(denngay.trim().length() <=0)
			return;		
		int songay =  31; //Utility.parseInt(denngay.split("-")[2]);
		dataDoanhSoNgay = new Long[so_thang_lui][songay];
		
		
		
		String query =  "\n declare @denngay varchar(10) = '"+denngay+"' " +
				"\n declare @iscuoithang tinyint = 0" +
				"\n  if month (  dateadd(dd,1,@denngay)) != month(@denngay)" +
				"\n  	set @iscuoithang = 1 " +
			"\n declare @tungay varchar(10)=   convert(varchar(10),DATEADD(month, DATEDIFF(month, 0, DATEADD(mm,-("+so_thang_lui+" - 1),@denngay)), 0),120) " +
			"\n ;WITH months(ngay) AS " +
			"\n ( " +
			"\n 	SELECT @denngay as ngay " +
			"\n 	UNION ALL " +
			"\n 	SELECT  convert(varchar(10),DATEADD(dd,-1,ngay),120) " +
			"\n 	FROM months " +
			"\n 	WHERE    ngay >=@tungay " +
			"\n ) " +
			"\n select  @iscuoithang iscuoithang,year(months.ngay)_nam ,month(months.ngay)_thang , day(months.ngay)_day , months.ngay , isnull(ds.sellout,0) sellout " +
			"\n 	,DENSE_RANK()  OVER (  ORDER BY year(months.ngay) desc,month(months.ngay) desc) rankThang    " +
			"\n from months " +
			"\n outer apply " +
			"\n (						 " +
			"\n 	select sum(sellout)sellout   " +
			"\n 	from   " +
			"\n 	(	   " +
			"\n 		select  sum(soluong*giamua) sellout	   " + 
			"\n 		from DONHANG dh    " +
			"\n 		inner join DONHANG_SANPHAM dhsp on dhsp.DONHANG_FK = dh.PK_SEQ    " +
			"\n 		where dh.TRANGTHAI = 1 and NGAYNHAP = months.ngay " + condition +
			"\n 		and not exists ( select 1 from DONHANGTRAVE x where x.trangthai = 3 and x.DONHANG_FK = dh.PK_SEQ)   " +
			
			"\n 		union all   " +
			"\n 		select   sum((-1)*soluong*giamua) sellout	   " + 
			"\n 		from DONHANGTRAVE dh    " +
			"\n 		inner join DONHANGTRAVE_SANPHAM dhsp on dhsp.DONHANGTRAVE_FK = dh.PK_SEQ   	 " +		
			"\n 		where dh.donhang_fk is null and  dh.TRANGTHAI = 3  and  NGAYNHAP = months.ngay " + condition +
			
			"\n 	)ds  				  " +
			"\n )ds " +
			"\n where  months.ngay >= @tungay  and ( day(months.ngay) <=day(@denngay) or @iscuoithang = 1 )  " +
			"\n order by year(months.ngay) desc,month(months.ngay) desc, day(months.ngay) asc " +
			"\n option (maxrecursion 0) " ; 
			System.out.println("query = "+ query);
		ResultSet rs = db.get(query);
		try {
			while(rs.next())
			{
				this.isCuoiThang = rs.getInt("iscuoithang");
				int rankThang =rs.getInt("rankThang");
				int _day =rs.getInt("_day");				
				long data = rs.getString("sellout") == null ? 0 :  rs.getLong("sellout");				
				dataDoanhSoNgay[rankThang-1][_day-1] = data;
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public ArrayList<Long> getDataSanpham() {
		return dataSanpham;
	}
	public ResultSet getTieuChiList()
	{
		try
		{
			String query =  " select ct.pk_seq , ct.diengiai " +
							" from tieuchithuong_chitiet ct " +
							" inner join tieuchitinhthuong t on ct.tieuchitinhthuong_fk = t.pk_seq" +
							" where t.trangthai = 1 and t.thang = "+this.thang+" and t.nam = "+this.nam+" and ct.tieuchi !=6 ";
			return db.get(query);
		}
		catch (Exception e) {
			return null;
		}
	}
	String phanloai = "";
	String loainv = "";
	String nppId = "";
	public void init_user()
	{
		try{
			
			Utility Ult = new Utility();
			String query="select phanloai,loai from nhanvien where pk_seq="+this.userId;
			System.out.println(" user :" + query);
			ResultSet rs=this.db.get(query);
			if(rs!=null){
				if(rs.next()){

					this.phanloai = rs.getString("phanloai");
					System.out.println("Phan loai : "+this.phanloai);					 				
					this.loainv =  rs.getString("loai");
					if( rs.getString("phanloai").equals("1")){
						this.nppId = Ult.getIdNhapp(this.userId);
						
					}
					rs.close();
				}
			}
		}catch(Exception er){

		}
	}
	
	private void InitBieuDoKD_Chart() 
	{
		init_user();
		String  condition = "";
		if(this.phanloai.equals("2"))
		{
		
			condition += "\n and dh.kbh_fk in ( "+geso.dms.center.util.Utility.PhanQuyenKBH(this.userId)+"  )  " ;
			if(this.loainv.equals("1"))
			{	
				condition += "\n and dh.ddkd_fk in ( "+ geso.dms.center.util.Utility.get_DDKD_from_BM( " select top 1 bm_fk from nhanvien where pk_seq ="+this.userId+" " ) +" ) ";
								
			}
			else if(this.loainv.equals("2"))
			{
				condition += "\n and dh.ddkd_fk in ( "+ geso.dms.center.util.Utility.get_DDKD_from_ASM( " select top 1 asm_fk from nhanvien where pk_seq ="+this.userId+" " ) +" ) ";
				
			}
			else if(this.loainv.equals("3"))
			{
				condition += "\n and dh.ddkd_fk in ( "+ geso.dms.center.util.Utility.get_DDKD_from_GSBH( " select top 1 gsbh_Fk from nhanvien where pk_seq ="+this.userId+" " ) +" ) ";
				
			}
			else
			{			
				condition += " and dh.npp_fk in ("+geso.dms.center.util.Utility.Quyen_npp(this.userId)+") ";
			}
		}
		else
		{
			condition += " and dh.npp_fk in ("+this.nppId+") ";
		}
		
		if(this.spKenhId.trim().length()>0)
		{
			condition += "\n AND dh.KBH_FK = "+this.spKenhId;
		}
		if(this.spVungId.trim().length()>0)
		{
			condition += "\n AND EXISTS (SELECT KHUVUC_FK FROM dbo.NHAPHANPHOI NPP INNER JOIN KHUVUC KV ON KV.PK_SEQ = NPP.KHUVUC_FK WHERE NPP.PK_SEQ = dh.NPP_FK AND KV.VUNG_FK = "+this.spVungId+" ) ";
		}
		
		String ngaytrongquy = "";
		// check ngay trong quy
		if(this.thang.equals("1")) //
			ngaytrongquy =  nam +"-01-01";
		else if(this.thang.equals("2")) //
			ngaytrongquy =  nam +"-04-01";
		else if(this.thang.equals("3")) //
			ngaytrongquy =  nam +"-07-01";
		else if(this.thang.equals("4")) //
			ngaytrongquy =  nam +"-09-01";
		String query =  "\n  declare @datetmp varchar(10) ='"+ngaytrongquy+"' " +
						"\n 							 declare @dau_quy_truoc varchar(10) =convert( char(10), DATEADD(qq, DATEDIFF(qq, 0, @datetmp) - 1, 0),120) " +
						"\n 							 declare @cuoi_quy_nay varchar(10) = convert( char(10),DATEADD (dd, -1, DATEADD(qq, DATEDIFF(qq, 0, @datetmp) +1, 0)),120); " +
						"\n 							 with doanhso as " +
						"\n 							 (  " +
						"\n 							  	select VUNG_FK ,year(ngaynhap) nam , DATEPART(quarter,ngaynhap) quy, sum(sellout)sellout  " +
						"\n 							  	from  " +
						"\n 							  	(	  " +
						"\n 							 		select kv.VUNG_FK , dh.NGAYNHAP , sum(soluong*giamua) sellout	   " +
						"\n 							 		from DONHANG dh   " +
						"\n 							 		inner join DONHANG_SANPHAM dhsp on dhsp.DONHANG_FK = dh.PK_SEQ   " +
						"\n 							 		inner join NHAPHANPHOI npp on npp.PK_SEQ = dh.NPP_FK   " +
						"\n 							 		inner join KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK   " +
						"\n 							 		where dh.TRANGTHAI = 1 and NGAYNHAP >=@dau_quy_truoc and  NGAYNHAP <=@cuoi_quy_nay " +  condition +
						"\n 											and not exists ( select 1 from DONHANGTRAVE x where x.trangthai = 3 and x.DONHANG_FK = dh.PK_SEQ)   "  +
						"\n 							 		group by kv.VUNG_FK   , dh.NGAYNHAP  " +
						"\n 						 		union all  " +
						"\n 				 		select kv.VUNG_FK  , dh.NGAYNHAP , sum((-1)*soluong*giamua) sellout	   " +
						"\n 				 		from DONHANGTRAVE dh   " +
						"\n 				 		inner join DONHANGTRAVE_SANPHAM dhsp on dhsp.DONHANGTRAVE_FK = dh.PK_SEQ   " +
						"\n 				 		inner join NHAPHANPHOI npp on npp.PK_SEQ = dh.NPP_FK   " +
						"\n 				 		inner join KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK   " +
						"\n 				 		where dh.donhang_fk is null and  dh.TRANGTHAI = 3 and NGAYNHAP >=@dau_quy_truoc and  NGAYNHAP <=@cuoi_quy_nay " + condition  +
						"\n 				 		group by kv.VUNG_FK    , dh.NGAYNHAP  " +
						"\n 				 	)ds  " +
						"\n 				 	group by VUNG_FK,year(ngaynhap)  , DATEPART(quarter,ngaynhap) 						 " +
						"\n 				 ) " +
			
						"\n 				 select v.TEN, isnull(quynay.sellout,0)dsquynay,isnull(quytruoc.sellout,0) dsquytruoc  " +
						"\n 				 from vung v " +
						"\n 				 outer apply  " +
						"\n 				 ( " +
						"\n 					select sellout  from doanhso where nam = year(@cuoi_quy_nay) and quy = DATEPART(qq,@cuoi_quy_nay) and doanhso.VUNG_FK = v.PK_SEQ " +
						"\n 				 )quynay " +
						"\n 				 outer apply  " +
						"\n 				 (	" +
						"\n						select sellout  from doanhso where nam = year(@dau_quy_truoc) and quy =  DATEPART(qq,@dau_quy_truoc) and doanhso.VUNG_FK = v.PK_SEQ " +
						"\n 				 )quytruoc ";
		System.out.println("query = "+ query);



		ResultSet rs = db.get(query);
		String data = "";
		try 
		{
			while( rs.next() )
			{
				String doituongTen = rs.getString("TEN");
				double dsquynay= rs.getDouble("dsquynay");
				double dsquytruoc= rs.getDouble("dsquytruoc");
				
				data +=  " {  " +
						 " 	'date': '" + doituongTen + "', " +
						 " 	'market1': " + dsquynay  + ", " +
						 " 	'market2': " + dsquytruoc + ", " +
						 //" 	'sales1': " + _chitieu + ", " +
						 //" 	'sales2': " + _thucdat + " " +
						 " }, ";
			}
			
			if( data.trim().length() > 0 )
			{
				data = data.substring(0, data.length() - 2);
				this.data_doanhthu = data;
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	private void InitDoanhThu_Chart() 
	{
	
		init_user();
		String  condition = "";
		if(this.phanloai.equals("2"))
		{
		
			condition += "\n and ddkd.kbh_fk in ( "+geso.dms.center.util.Utility.PhanQuyenKBH(this.userId)+"  )  " ;
			if(this.loainv.equals("1"))
			{	
				condition += "\n and ddkd.pk_seq in ( "+ geso.dms.center.util.Utility.get_DDKD_from_BM( " select top 1 bm_fk from nhanvien where pk_seq ="+this.userId+" " ) +" ) ";
								
			}
			else if(this.loainv.equals("2"))
			{
				condition += "\n and ddkd.pk_seq in ( "+ geso.dms.center.util.Utility.get_DDKD_from_ASM( " select top 1 asm_fk from nhanvien where pk_seq ="+this.userId+" " ) +" ) ";
				
			}
			else if(this.loainv.equals("3"))
			{
				condition += "\n and ddkd.pk_seq in ( "+ geso.dms.center.util.Utility.get_DDKD_from_GSBH( " select top 1 gsbh_Fk from nhanvien where pk_seq ="+this.userId+" " ) +" ) ";
				
			}
			else
			{			
				condition += " and ddkd.npp_fk in ("+geso.dms.center.util.Utility.Quyen_npp(this.userId)+") ";
			}
		}
		else
		{
			condition += " and ddkd.npp_fk in ("+this.nppId+") ";
		}
		
		String tungay =  this.nam +  "-" + (this.thang.trim().length() > 1 ?  this.thang :"0" + this.thang ) + "-01";
		
		String query = "\n  select tct.TIEUCHI,v.TEN,sum(ct.ChiTieu) as [Target], sum(isnull(td.thucdat,0)) [Actual]  " +
			"\n from ChiTieuNhanVien_DDKD ct  " +
			"\n 	inner join  ChiTieuNhanVien t on ct.ctnv_fk = t.pk_seq and t.trangthai = 1 and t.THANG = "+this.thang+" and t.NAM =  " + this.nam +
			"\n inner join DaiDienKinhDoanh ddkd on ct.NhanVien_FK = ddkd.pk_seq   " +
			"\n inner join nhaphanphoi npp on npp.pk_seq = ddkd.npp_fk   " +
			"\n inner join KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK  " +
			"\n inner join VUNG v on v.PK_SEQ = kv.VUNG_FK " +
			"\n inner join tieuchithuong_chitiet tct on ct.tctct_fk = tct.pk_seq    " +
			"\n outer apply  [dbo].[ufn_KPI_DDKD]( '"+tungay+"',ct.tctct_fk,ct.NhanVien_FK) td     " +
			"\n where  tct.PK_SEQ = "+this.theomuc+" and  ct.chitieu > 0 and ct.TieuChi !=6   " + condition +
			"\n group by tct.TIEUCHI, v.TEN";
		
		System.out.println("query chart = "+ query);
		
		ResultSet rs = db.get(query);
		String data = "";
		try 
		{
			while( rs.next() )
			{
				double quydoi =  1;
				int TIEUCHI = rs.getInt("TIEUCHI");
				if(TIEUCHI == 1 || TIEUCHI == 2)
				{ 	
					this.donvitinh = "triệu";
					quydoi = 1000000;
				}
				String doituongTen = rs.getString("TEN");
				double Target = rs.getDouble("Target")/quydoi;
				double Actual = rs.getDouble("Actual")/quydoi;
				data +=  " {  " +
						 " 	'date': '" + doituongTen + "', " +
						 //" 	'market1': 71, " +
						 //" 	'market2': 75, " +
						 " 	'sales1': " + Target + ", " +
						 " 	'sales2': " + Actual + " " +
						 " }, ";
			}
			
			if( data.trim().length() > 0 )
			{
				data = data.substring(0, data.length() - 2);
				this.data_doanhthu = data;
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
	}

	public String getView() {
		return view;
	}
	public void setView(String view) {
		this.view = view;
	}
	public String getDonvitinh() {
		return donvitinh;
	}
	public void setDonvitinh(String donvitinh) {
		this.donvitinh = donvitinh;
	}
}
