package geso.dms.center.beans.xuatkho.imp;

import geso.dms.center.beans.xuatkho.IErpXuatkho;
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

import org.apache.catalina.connector.Request;

public class ErpXuatkho implements IErpXuatkho
{
	private static final long serialVersionUID = 1L;
	String userId;
	String id;
	
	String ngayyeucau;
	String ghichu;

	String msg;
	String trangthai;
	String khoNhanId;
	ResultSet khoNhanRs;
	
	String nppId;
	ResultSet nppRs;
	
	String ddhId;
	ResultSet ddhRs;
	
	Hashtable<String, String> sanpham_soluong;

	String[] spId;
	String[] spMa;
	String[] spTen;
	String[] spDonvi;
	String[] spSoluongDat;
	String[] spTonkho;
	String[] spDaxuat;
	String[] spSoluong;
	String[] spGianhap;
	String[] spLoai;
	String[] spSCheme;
	
	dbutils db;
	
	public ErpXuatkho()
	{
		this.id = "";
		this.ngayyeucau = getDateTime();
		this.ghichu = "";
		this.khoNhanId = "";
		this.nppId = "";
		this.msg = "";
		this.trangthai = "0";
		this.ddhId = "";
		
		this.sanpham_soluong = new Hashtable<String, String>();
		
		this.db = new dbutils();
	}
	
	public ErpXuatkho(String id)
	{
		this.id = id;
		this.ngayyeucau = getDateTime();
		this.ghichu = "";
		this.khoNhanId = "";
		this.nppId = "";
		this.msg = "";
		this.trangthai = "0";
		this.ddhId = "";

		this.sanpham_soluong = new Hashtable<String, String>();
		
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

	
	public void createRs() 
	{
		Utility util =new Utility(); 
		
		this.khoNhanRs = db.get("select PK_SEQ, TEN from ERP_KHOTT where trangthai = '1' AND PK_sEQ IN "+util.quyen_khoTT(userId)+"   ");
		
		String query = "select PK_SEQ, MA + ' - ' + TEN as TEN from NHAPHANPHOI where TRANGTHAI = '1' ";
		this.nppRs = db.get(query);
		
		if(this.nppId.trim().length() > 0 && this.khoNhanId.trim().length() > 0 )
		{
			query = "select PK_SEQ, CAST(pk_seq as varchar(10)) + ' / ' + NgayDonHang as ten " +
					"from ERP_DONDATHANG where TRANGTHAI = '2' and NPP_FK = '" + this.nppId + "' and KHO_FK = '" + this.khoNhanId + "' ";
			
			if(this.id.trim().length() > 0)
			{
				query += " union  " +
						 "select PK_SEQ, CAST(pk_seq as varchar(10)) + ' / ' + NgayDonHang as ten " +
						 "from ERP_DONDATHANG where pk_seq in ( select ddh_fk from ERP_YCXUATKHO_DDH where ycxk_fk = '" + this.id + "' ) ";
			}
			
			this.ddhRs = db.get(query);
		}
		else
		{
			this.ddhId = "";
			this.ddhRs = null;
		}
		
		if(this.ddhId.trim().length() > 0 && this.khoNhanId.trim().length() > 0 )
		{
			//INIT SP
			
			String ycxkID = ( this.id.trim().length() <= 0 ) ? "-1" : this.id;
			/*query = " select ddh.*, ISNULL(xuat.soluongXUAT, 0) as xuat,  " +
					" 		ISNULL( daxuat.soluongDAXUAT, 0) as daxuat,  " +
					" 		ISNULL( ( select AVAILABLE from ERP_KHOTT_SANPHAM where khott_fk = '" + this.khoNhanId + "' and sanpham_fk = ddh.PK_SEQ ), 0) + ISNULL(xuat.soluongXUAT, 0) as tonkho   " +
					"  from  " +
					"  (  " +
					"  	select sp.PK_SEQ, sp.MA, sp.TEN, dv.DONVI, SUM(dathang.soluong) as soluongDAT, 0 as loai   " +
					"  	from  " +
					"  	(  " +
					"  		select a.sanpham_fk, b.DVDL_FK as dvCHUAN,  " +
					"  				case when a.dvdl_fk IS null then a.soluong   " +
					"  					 when a.dvdl_fk = b.DVDL_FK then a.soluong  " +
					"  					 else  a.soluong * ( select SOLUONG1 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )   " +
					"  									 / ( select SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )	 end as soluong  " +
					"  		from ERP_DONDATHANG_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ  " +
					"  		where a.dondathang_fk in (  " + this.ddhId + "  )  " +
					"  	)  " +
					"  	dathang inner join SANPHAM sp on dathang.sanpham_fk = sp.PK_SEQ  " +
					"  			inner join DONVIDOLUONG dv on sp.DVDL_FK = dv.PK_SEQ  " +
					"  	group by sp.PK_SEQ, sp.MA, sp.TEN, dv.DONVI  " +
					"  )  " +
					"  ddh left join   " +
					"  (  " +
					" 	select b.sanpham_fk, b.soluongXUAT  " +
					" 	from ERP_YCXUATKHO a inner join ERP_YCXUATKHO_SANPHAM b on a.PK_SEQ = b.ycxk_fk " +
					" 	where a.PK_SEQ = '" + ycxkID + "' " +
					"  ) " +
					"  xuat on ddh.PK_SEQ = xuat.sanpham_fk left join   " +
					"  (  " +
					" 	select b.sanpham_fk, SUM( b.soluongXUAT ) as soluongDAXUAT  " +
					" 	from ERP_YCXUATKHO a inner join ERP_YCXUATKHO_SANPHAM b on a.PK_SEQ = b.ycxk_fk " +
					" 	where a.PK_SEQ != '" + ycxkID + "' and a.TRANGTHAI in (0, 1)  " +
					" 		and a.PK_SEQ in ( select ycxk_fk from  ERP_YCXUATKHO_DDH where ddh_fk in ( " + this.ddhId + " ) ) " +
					" 	group by b.sanpham_fk " +
					"  ) " +
					"  daxuat on ddh.PK_SEQ = daxuat.sanpham_fk " ;*/
			
			query = " select ddh.*, ISNULL(xuat.soluongXUAT, 0) as xuat,   " +
					" 	ISNULL( daxuat.soluongDAXUAT, 0) as daxuat,   " +
					" 	ISNULL( ( select AVAILABLE from ERP_KHOTT_SANPHAM where khott_fk = '" + this.khoNhanId + "' and sanpham_fk = ddh.PK_SEQ ), 0) + ISNULL(xuat.soluongXUAT, 0) as tonkho    " +
					" from   " +
					" (   " +
					" 	select sp.PK_SEQ, sp.MA, sp.TEN, dv.DONVI, scheme, loai, SUM(dathang.soluong) as soluongDAT    " +
					" 	from   " +
					" 	(   " +
					" 			select a.sanpham_fk, b.DVDL_FK as dvCHUAN,   " +
					" 					case when a.dvdl_fk IS null then a.soluong    " +
					" 						 when a.dvdl_fk = b.DVDL_FK then a.soluong   " +
					" 						 else  a.soluong * ( select SOLUONG1 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )    " +
					" 										 / ( select SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )	 end as soluong, 0 as loai, ' ' as scheme  " +
					" 			from ERP_DONDATHANG_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ   " +
					" 			where a.dondathang_fk in (   " + this.ddhId + "   )  " +
					" 		union ALL " +
					" 			select b.PK_SEQ, b.DVDL_FK as dvCHUAN, a.soluong, 1 as loai, c.SCHEME  " +
					" 			from ERP_DONDATHANG_CTKM_TRAKM a inner join SANPHAM b on a.SPMA = b.MA  " +
					" 					inner join CTKHUYENMAI c on a.CTKMID = c.PK_SEQ  " +
					" 			where a.DONDATHANGID in (   " + this.ddhId + "   )   " +
					" 	)   " +
					" 	dathang inner join SANPHAM sp on dathang.sanpham_fk = sp.PK_SEQ   " +
					" 			inner join DONVIDOLUONG dv on sp.DVDL_FK = dv.PK_SEQ   " +
					" 	group by sp.PK_SEQ, sp.MA, sp.TEN, dv.DONVI, scheme, loai  " +
					" )   " +
					" ddh left join    " +
					" (   " +
					" 	select b.sanpham_fk, b.LOAI, isnull(b.SCHEME, '') as SCHEME, b.soluongXUAT   " +
					" 	from ERP_YCXUATKHO a inner join ERP_YCXUATKHO_SANPHAM b on a.PK_SEQ = b.ycxk_fk  " +
					" 	where a.PK_SEQ = '" + ycxkID + "'  " +
					" )  " +
					" xuat on ddh.PK_SEQ = xuat.sanpham_fk and ddh.loai = xuat.LOAI and ddh.scheme = xuat.scheme left join    " +
					" (   " +
					" 	select b.sanpham_fk, b.LOAI, isnull(b.SCHEME, '') as SCHEME, SUM( b.soluongXUAT ) as soluongDAXUAT   " +
					" 	from ERP_YCXUATKHO a inner join ERP_YCXUATKHO_SANPHAM b on a.PK_SEQ = b.ycxk_fk  " +
					" 	where a.PK_SEQ != '" + ycxkID + "' and a.TRANGTHAI != 3   " +
					" 		and a.PK_SEQ in ( select ycxk_fk from  ERP_YCXUATKHO_DDH where ddh_fk in (  " + this.ddhId + "  ) )  " +
					" 	group by b.sanpham_fk, b.LOAI, b.SCHEME  " +
					" )  " +
					" daxuat on ddh.PK_SEQ = daxuat.sanpham_fk and ddh.loai = daxuat.LOAI and ddh.scheme = daxuat.scheme order by SCHEME asc  ";
			
			System.out.println("---INIT YCXK: " + query);
			ResultSet spRs = db.get(query);
			NumberFormat formater = new DecimalFormat("##,###,###");
			
			if(spRs != null)
			{
				try 
				{
					String spID = "";
					String spMA = "";
					String spTEN = "";
					String spDONVI = "";
					String spSOLUONGDAT = "";
					String spTONKHO = "";
					String spDAXUAT = "";
					String spSOLUONGXUAT = "";
					String spLOAI = "";
					String spSCHEME = "";
					
					while(spRs.next())
					{
						double conLAI = 1000000;
						if(this.id.trim().length() <= 0)
							conLAI = spRs.getDouble("soluongDAT") - spRs.getDouble("daxuat");
						 
						if(conLAI > 0)
						{
							spID += spRs.getString("PK_SEQ") + "__";
							spMA += spRs.getString("MA") + "__";
							spTEN += spRs.getString("TEN") + "__";
							spDONVI += spRs.getString("DONVI") + "__";
							spSOLUONGDAT += formater.format(spRs.getDouble("soluongDAT")) + "__";
							spTONKHO += formater.format(spRs.getDouble("tonkho")) + "__";
							spDAXUAT += formater.format(spRs.getDouble("daxuat")) + "__";
							if(this.id.trim().length() <= 0)
								spSOLUONGXUAT += formater.format( spRs.getDouble("soluongDAT") - spRs.getDouble("daxuat") ) + "__";
							else
								spSOLUONGXUAT += formater.format( spRs.getDouble("xuat") ) + "__";
							spLOAI += spRs.getString("LOAI") + "__";
							spSCHEME += spRs.getString("SCHEME") + "__";
						}
					}
					spRs.close();
					
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
						
						spSOLUONGDAT = spSOLUONGDAT.substring(0, spSOLUONGDAT.length() - 2);
						this.spSoluongDat = spSOLUONGDAT.split("__");
						
						spTONKHO = spTONKHO.substring(0, spTONKHO.length() - 2);
						this.spTonkho = spTONKHO.split("__");
						
						spDAXUAT = spDAXUAT.substring(0, spDAXUAT.length() - 2);
						this.spDaxuat = spDAXUAT.split("__");
						
						spSOLUONGXUAT = spSOLUONGXUAT.substring(0, spSOLUONGXUAT.length() - 2);
						this.spSoluong = spSOLUONGXUAT.split("__");
						
						spLOAI = spLOAI.substring(0, spLOAI.length() - 2);
						this.spLoai = spLOAI.split("__");
						
						spSCHEME = spSCHEME.substring(0, spSCHEME.length() - 2);
						this.spSCheme = spSCHEME.split("__");
						
					}
					
				} 
				catch (Exception e) { System.out.println("EXCEPTION SP: " + e.getMessage() ); }
				
			}
			
		}
		
	}

	public void init() 
	{
		String query = "select ngayyeucau, ISNULL(ghichu, '') as ghichu, npp_fk, kho_fk " +
						"from ERP_YCXUATKHO where pk_seq = '" + this.id + "'";
		System.out.println("____INIT NHAP KHO: " + query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				if(rs.next())
				{
					this.ngayyeucau = rs.getString("ngayyeucau");
					this.ghichu = rs.getString("ghichu");
					this.nppId = rs.getString("npp_fk");
					this.khoNhanId = rs.getString("kho_fk");
				}
				rs.close();
				
				//INIT DDH
				query = "select ddh_fk from ERP_YCXUATKHO_DDH where ycxk_fk = '" + this.id + "' ";
				rs = db.get(query);
				String ddhID = "";
				while(rs.next())
				{
					ddhID += rs.getString("ddh_fk") + ",";
				}
				rs.close();
				
				if(ddhID.trim().length() > 0)
				{
					this.ddhId = ddhID.substring(0, ddhID.length() - 1);
				}
				
				//INIT SO LUONG
				query = "select sanpham_fk, solo, LOAI, soluong, isnull(scheme, '') as scheme " +
						"from ERP_YCXUATKHO_SANPHAM_CHITIET where ycxk_fk = '" + this.id + "'";
				rs = db.get(query);
				if(rs != null)
				{
					Hashtable<String, String> sp_soluong = new Hashtable<String, String>();
					while(rs.next())
					{
						sp_soluong.put(rs.getString("sanpham_fk") + "__" + rs.getString("LOAI") + "__" + rs.getString("SCHEME").trim() + "__" + rs.getString("solo"), rs.getString("soluong") );
					}
					rs.close();
					
					this.sanpham_soluong = sp_soluong;
				}
				
			} 
			catch (Exception e) 
			{
				System.out.println("---LOI INIT: " + e.getMessage());
			}
		}

		this.createRs();
	
		//this.initSANPHAM();
		
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

	
	public Hashtable<String, String> getSanpham_Soluong() {
		
		return this.sanpham_soluong;
	}

	
	public void setSanpham_Soluong(Hashtable<String, String> sp_soluong) {
		
		this.sanpham_soluong = sp_soluong;
	}

	
	public ResultSet getSoloTheoSp(String spIds, String tongluong)
	{
		tongluong = tongluong.replaceAll(",", "");
		//System.out.println("---TONG LUONG: " + tongluong );
		
		String query = "select AVAILABLE + ISNULL( ( select sum(soluong) from ERP_YCXUATKHO_SANPHAM_CHITIET where ycxk_fk = '" + ( this.id.trim().length() > 0 ? this.id : "-1" ) + "' and sanpham_fk = ct.sanpham_fk and solo = ct.solo  ), 0 ) as AVAILABLE, NGAYHETHAN, SOLO " +
					   "from ERP_KHOTT_SP_CHITIET ct " +
					   "where KHOTT_FK = '" + this.khoNhanId + "' and SANPHAM_FK = '" + spIds + "' " +
					   		" and ( AVAILABLE + ISNULL( ( select sum(soluong) from ERP_YCXUATKHO_SANPHAM_CHITIET where ycxk_fk = '" + ( this.id.trim().length() > 0 ? this.id : "-1" ) + "' and sanpham_fk = ct.sanpham_fk and solo = ct.solo  ), 0 ) ) > 0 " +
					    " order by NGAYHETHAN asc";
		
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
		}
		
		if(query2.trim().length() > 0)
		{
			query2 = query2.substring(0, query2.length() - 10);
			//System.out.println("---TU DONG DE XUAT BIN - LO: " + query2 );
			return db.get(query2);
		}
		
		return null;
	}

	
	public String[] getSpSoluongDat() {
		
		return this.spSoluongDat;
	}

	
	public void setSpSoluongDat(String[] spSoluong) {
		
		this.spSoluongDat = spSoluong;
	}

	
	public String[] getSpTonKho() {
		
		return this.spTonkho;
	}

	
	public void setSpTonKho(String[] spTonkho) {
		
		this.spTonkho = spTonkho; 
	}

	
	public String[] getSpDaXuat() {
		
		return this.spDaxuat;
	}

	
	public void setSpDaXuat(String[] spDaXuat) {
		
		this.spDaxuat = spDaXuat;
	}

	
	public boolean create() 
	{
		if(this.ngayyeucau.trim().length() < 10)
		{
			this.msg = "Vui lòng nhập ngày yêu cầu";
			return false;
		}

		if( this.nppId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn nhà phân phối";
			return false;
		}
		
		if(this.ddhId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn đơn đặt hàng";
			return false;
		}
		
		if( this.khoNhanId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn kho xuất hàng";
			return false;
		}
		
		if(spMa == null)
		{
			this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm yêu cầu xuất kho";
			return false;
		}
		else
		{
			boolean coSP = false;
			for(int i = 0; i < spId.length; i++)
			{
				if( spSoluong[i].trim().length() > 0 )
				{
					if(Double.parseDouble(spSoluong[i].trim()) > 0)
						coSP = true;
				}
			}
			
			if(!coSP)
			{
				this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm yêu cầu xuất kho";
				return false;
			}	
		}
		
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String query = " insert ERP_YCXUATKHO(NgayYeuCau, ghichu, trangthai, npp_fk, kho_fk, ngaytao, nguoitao, ngaysua, nguoisua) " +
						   " values('" + this.ngayyeucau + "', N'" + this.ghichu + "', '0', '" + nppId + "', " + this.khoNhanId + ", '" + getDateTime() + "', '" + this.userId + "', '" + getDateTime() + "', '" + this.userId + "' )";
			
			System.out.println("1.Insert YCXUATKHO: " + query);
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ERP_YCXUATKHO " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "Insert ERP_YCXUATKHO_DDH(ycxk_fk, ddh_fk) " +
					"select IDENT_CURRENT('ERP_YCXUATKHO'), pk_seq from ERP_DONDATHANG where pk_seq in ( " + this.ddhId + " )  ";
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ERP_YCXUATKHO_DDH " + query;
				db.getConnection().rollback();
				return false;
			}
			
			for(int i = 0; i < spId.length; i++)
			{
				if(spId[i].trim().length() > 0 && Double.parseDouble(spSoluong[i].trim()) > 0 )
				{
					query = "insert ERP_YCXUATKHO_SANPHAM( ycxk_fk, sanpham_fk, soluongDAT, tonkho, daxuat, soluongXUAT, LOAI, SCHEME ) " +
							"select IDENT_CURRENT('ERP_YCXUATKHO'), '" + spId[i] + "', '" + spSoluongDat[i].replaceAll(",", "") + "', '" + spTonkho[i].replaceAll(",", "") + "', '" + spDaxuat[i].replaceAll(",", "") + "', '" + spSoluong[i].replaceAll(",", "") + "', '" + spLoai[i] + "', N'" + spSCheme[i] + "' ";
					
					System.out.println("1.1.Insert YCXK - SP: " + query);
					if(!db.update(query))
					{
						this.msg = "Khong the tao moi ERP_YCXUATKHO_SANPHAM: " + query;
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
							if(key.startsWith(spId[i]+"__"))
							{
								String[] _sp = key.split("__");
								
								String _soluongCT = "0"; 
								if(this.sanpham_soluong.get(key) != null)
									_soluongCT = this.sanpham_soluong.get(key).replaceAll(",", "");
								
								totalCT += Double.parseDouble(_soluongCT);
								
								//CHECK TON KHO
								query = "select NGAYHETHAN, AVAILABLE from ERP_KHOTT_SP_CHITIET where KHOTT_FK = '" + this.khoNhanId + "' and SOLO = '" + _sp[1] + "' and SANPHAM_FK = '" + spId[i] + "'";
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
								
								
								query = "insert ERP_YCXUATKHO_SANPHAM_CHITIET( ycxk_fk, SANPHAM_FK, solo, ngayhethan, soluong, loai, scheme ) " +
										"select IDENT_CURRENT('ERP_YCXUATKHO'), pk_seq, N'" + _sp[1] + "',  '" + ngayhethan + "', '" + _soluongCT.replaceAll(",", "") + "', '" + spLoai[i] + "', '" + spSCheme[i] + "' " +
										"from SANPHAM where PK_SEQ = '" + spId[i] + "' ";
								
								System.out.println("1.2.Insert YCXK - SP - CT: " + query);
								if(!db.update(query))
								{
									this.msg = "Khong the tao moi ERP_YCXUATKHO_SANPHAM_CHITIET: " + query;
									db.getConnection().rollback();
									return false;
								}
								
								
								query = "Update ERP_KHOTT_SP_CHITIET set booked = booked + '" + _soluongCT.replaceAll(",", "") + "', AVAILABLE = AVAILABLE - '" + _soluongCT.replaceAll(",", "") + "' " +
										"where KHOTT_FK = '" + this.khoNhanId + "' and SOLO = '" + _sp[1] + "' and SANPHAM_FK = '" + spId[i] + "'  ";
								if(!db.update(query))
								{
									this.msg = "Khong the cap nhat ERP_KHOTT_SP_CHITIET: " + query;
									db.getConnection().rollback();
									return false;
								}
								
								
								query = "Update ERP_KHOTT_SANPHAM set booked = booked + '" + _soluongCT.replaceAll(",", "") + "', AVAILABLE = AVAILABLE - '" + _soluongCT.replaceAll(",", "") + "' " +
										"where KHOTT_FK = '" + this.khoNhanId + "' and SANPHAM_FK = '" + spId[i] + "'  ";
								if(!db.update(query))
								{
									this.msg = "Khong the cap nhat ERP_KHOTT_SANPHAM: " + query;
									db.getConnection().rollback();
									return false;
								}
								
							}
						}
						
						//NEU TONG SO LUONG CT MA KHONG BANG TONG LUONG XUAT THI KO CHO LUU
						if(totalCT != Double.parseDouble(spSoluong[i].replaceAll(",", "").trim()) )
						{
							this.msg = "Tổng xuất theo lô của sản phẩm ( " + spTen[i] + " ) phải bằng tổng số lượng xuất ( " + spSoluong[i] + " ) ";
							db.getConnection().rollback();
							return false;
						}
						
					}
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

	
	public boolean update() 
	{
		if(this.ngayyeucau.trim().length() < 10)
		{
			this.msg = "Vui lòng nhập ngày yêu cầu";
			return false;
		}

		if( this.nppId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn nhà phân phối";
			return false;
		}
		
		if(this.ddhId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn đơn đặt hàng";
			return false;
		}
		
		if( this.khoNhanId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn kho xuất hàng";
			return false;
		}
		
		if(spMa == null)
		{
			this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm yêu cầu xuất kho";
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
				this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm yêu cầu xuất kho";
				return false;
			}	
		}
		
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String query = " Update ERP_YCXUATKHO set NgayYeuCau = '" + this.ngayyeucau + "', ghichu = N'" + this.ghichu + "', npp_fk = '" + this.nppId + "', kho_fk = '" + this.khoNhanId + "', " +
								"ngaysua = '" + this.getDateTime() + "', nguoisua = '" + this.userId + "' where pk_seq = '" + this.id + "' ";
			
			System.out.println("1.Update YCXUATKHO: " + query);
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_YCXUATKHO " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = " update kho set kho.AVAILABLE = kho.AVAILABLE + CT.tongxuat, " +
					" 			   kho.BOOKED = kho.BOOKED - CT.tongxuat " +
					" from " +
					" ( " +
					" 	select a.kho_fk, b.sanpham_fk, b.solo, SUM(b.soluong) as tongxuat  " +
					" 	from ERP_YCXUATKHO a inner join ERP_YCXUATKHO_SANPHAM_CHITIET b on a.pk_seq = b.ycxk_fk " +
					" 	where ycxk_fk = '" + this.id + "' " +
					" 	group by a.kho_fk, b.solo, b.sanpham_fk " +
					" ) " +
					" CT inner join ERP_KHOTT_SP_CHITIET kho on CT.kho_fk = kho.KHOTT_FK  " +
					" 	and CT.sanpham_fk = kho.SANPHAM_FK and CT.solo = kho.SOLO ";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_KHOTT_SP_CHITIET " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = " update kho set kho.AVAILABLE = kho.AVAILABLE + CT.tongxuat, " +
					" 			   kho.BOOKED = kho.BOOKED - CT.tongxuat " +
					" from " +
					" ( " +
					" 	select a.kho_fk, b.sanpham_fk, SUM(b.soluong) as tongxuat  " +
					" 	from ERP_YCXUATKHO a inner join ERP_YCXUATKHO_SANPHAM_CHITIET b on a.pk_seq = b.ycxk_fk " +
					" 	where ycxk_fk = '" + this.id + "' " +
					" 	group by a.kho_fk, b.sanpham_fk " +
					" ) " +
					" CT inner join ERP_KHOTT_SANPHAM kho on CT.kho_fk = kho.KHOTT_FK  " +
					" 	and CT.sanpham_fk = kho.SANPHAM_FK  ";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_KHOTT_SANPHAM " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete ERP_YCXUATKHO_DDH where ycxk_fk = '" + this.id + "' ";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_YCXUATKHO_DDH " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete ERP_YCXUATKHO_SANPHAM where ycxk_fk = '" + this.id + "' ";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_YCXUATKHO_SANPHAM " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete ERP_YCXUATKHO_SANPHAM_CHITIET where ycxk_fk = '" + this.id + "' ";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_YCXUATKHO_SANPHAM " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "Insert ERP_YCXUATKHO_DDH(ycxk_fk, ddh_fk) " +
					"select '" + this.id + "', pk_seq from ERP_DONDATHANG where pk_seq in ( " + this.ddhId + " )  ";
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ERP_YCXUATKHO_DDH " + query;
				db.getConnection().rollback();
				return false;
			}
	
			for(int i = 0; i < spId.length; i++)
			{
				if(spId[i].trim().length() > 0 && Double.parseDouble(spSoluong[i].trim().replaceAll(",", "")) > 0 )
				{
					query = "insert ERP_YCXUATKHO_SANPHAM( ycxk_fk, sanpham_fk, soluongDAT, tonkho, daxuat, soluongXUAT, LOAI, SCHEME ) " +
							"select '" + this.id + "', '" + spId[i] + "', '" + spSoluongDat[i].replaceAll(",", "") + "', '" + spTonkho[i].replaceAll(",", "") + "', '" + spDaxuat[i].replaceAll(",", "") + "', '" + spSoluong[i].replaceAll(",", "") + "', '" + spLoai[i] + "', N'" + spSCheme[i].trim() + "' ";
					
					System.out.println("1.1.Update YCXK - SP: " + query);
					if(!db.update(query))
					{
						this.msg = "Khong the tao moi ERP_YCXUATKHO_SANPHAM: " + query;
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
							
							if(key.startsWith( spId[i] + "__" + spLoai[i] + "__" + spSCheme[i].trim() ))
							{
								String[] _sp = key.split("__");
								
								String _soluongCT = "0"; 
								if(this.sanpham_soluong.get(key) != null)
								{
									_soluongCT = this.sanpham_soluong.get(key).replaceAll(",", "");
								}
								
								totalCT += Double.parseDouble(_soluongCT);
								
								//CHECK TON KHO
								query = "select NGAYHETHAN, AVAILABLE from ERP_KHOTT_SP_CHITIET where KHOTT_FK = '" + this.khoNhanId + "' and SOLO = '" + _sp[3] + "' and SANPHAM_FK = '" + spId[i] + "'";
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
									this.msg = "Sản phẩm (" + spTen[i] + ") với số lô (" + _sp[3] + "), số lượng xuất (" + _soluongCT + ") còn tối đa (" + avai + "). ";
									db.getConnection().rollback();
									return false;
								}
								
								
								query = "insert ERP_YCXUATKHO_SANPHAM_CHITIET( ycxk_fk, SANPHAM_FK, solo, ngayhethan, soluong, loai, scheme ) " +
										"select '" + this.id + "', pk_seq, N'" + _sp[3] + "', '" + ngayhethan + "', '" + _soluongCT.replaceAll(",", "") + "', '" + spLoai[i] + "', '" + spSCheme[i] + "' " +
										"from SANPHAM where PK_SEQ = '" + spId[i] + "' ";
								
								System.out.println("1.2.Insert YCXK - SP - CT: " + query);
								if(!db.update(query))
								{
									this.msg = "Khong the tao moi ERP_YCXUATKHO_SANPHAM_CHITIET: " + query;
									db.getConnection().rollback();
									return false;
								}
								
								
								query = "Update ERP_KHOTT_SP_CHITIET set booked = booked + '" + _soluongCT.replaceAll(",", "") + "', AVAILABLE = AVAILABLE - '" + _soluongCT.replaceAll(",", "") + "' " +
										"where KHOTT_FK = '" + this.khoNhanId + "' and SOLO = '" + _sp[3] + "' and SANPHAM_FK = '" + spId[i] + "'  ";
								if(!db.update(query))
								{
									this.msg = "Khong the cap nhat ERP_KHOTT_SP_CHITIET: " + query;
									db.getConnection().rollback();
									return false;
								}
								
								
								query = "Update ERP_KHOTT_SANPHAM set booked = booked + '" + _soluongCT.replaceAll(",", "") + "', AVAILABLE = AVAILABLE - '" + _soluongCT.replaceAll(",", "") + "' " +
										"where KHOTT_FK = '" + this.khoNhanId + "' and SANPHAM_FK = '" + spId[i] + "'  ";
								if(!db.update(query))
								{
									this.msg = "Khong the cap nhat ERP_KHOTT_SANPHAM: " + query;
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
	
	
	public boolean chot() 
	{
		if(this.ngayyeucau.trim().length() < 10)
		{
			this.msg = "Vui lòng nhập ngày yêu cầu";
			return false;
		}

		if( this.nppId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn nhà phân phối";
			return false;
		}
		
		if(this.ddhId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn đơn đặt hàng";
			return false;
		}
		
		if( this.khoNhanId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn kho xuất hàng";
			return false;
		}
		
		if(spMa == null)
		{
			this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm yêu cầu xuất kho";
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
				this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm yêu cầu xuất kho";
				return false;
			}	
		}
		
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String query = " Update ERP_YCXUATKHO set NgayYeuCau = '" + this.ngayyeucau + "', ghichu = N'" + this.ghichu + "', npp_fk = '" + this.nppId + "', kho_fk = '" + this.khoNhanId + "', " +
								"ngaysua = '" + this.getDateTime() + "', nguoisua = '" + this.userId + "' where pk_seq = '" + this.id + "' ";
			
			System.out.println("1.Update YCXUATKHO: " + query);
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_YCXUATKHO " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = " update kho set kho.AVAILABLE = kho.AVAILABLE + CT.tongxuat, " +
					" 			   kho.BOOKED = kho.BOOKED - CT.tongxuat " +
					" from " +
					" ( " +
					" 	select a.kho_fk, b.sanpham_fk, b.solo, SUM(b.soluong) as tongxuat  " +
					" 	from ERP_YCXUATKHO a inner join ERP_YCXUATKHO_SANPHAM_CHITIET b on a.pk_seq = b.ycxk_fk " +
					" 	where ycxk_fk = '" + this.id + "' " +
					" 	group by a.kho_fk, b.solo, b.sanpham_fk " +
					" ) " +
					" CT inner join ERP_KHOTT_SP_CHITIET kho on CT.kho_fk = kho.KHOTT_FK  " +
					" 	and CT.sanpham_fk = kho.SANPHAM_FK and CT.solo = kho.SOLO ";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_KHOTT_SP_CHITIET " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = " update kho set kho.AVAILABLE = kho.AVAILABLE + CT.tongxuat, " +
					" 			   kho.BOOKED = kho.BOOKED - CT.tongxuat " +
					" from " +
					" ( " +
					" 	select a.kho_fk, b.sanpham_fk, SUM(b.soluong) as tongxuat  " +
					" 	from ERP_YCXUATKHO a inner join ERP_YCXUATKHO_SANPHAM_CHITIET b on a.pk_seq = b.ycxk_fk " +
					" 	where ycxk_fk = '" + this.id + "' " +
					" 	group by a.kho_fk, b.sanpham_fk " +
					" ) " +
					" CT inner join ERP_KHOTT_SANPHAM kho on CT.kho_fk = kho.KHOTT_FK  " +
					" 	and CT.sanpham_fk = kho.SANPHAM_FK  ";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_KHOTT_SANPHAM " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete ERP_YCXUATKHO_DDH where ycxk_fk = '" + this.id + "' ";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_YCXUATKHO_DDH " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete ERP_YCXUATKHO_SANPHAM where ycxk_fk = '" + this.id + "' ";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_YCXUATKHO_SANPHAM " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete ERP_YCXUATKHO_SANPHAM_CHITIET where ycxk_fk = '" + this.id + "' ";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_YCXUATKHO_SANPHAM " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "Insert ERP_YCXUATKHO_DDH(ycxk_fk, ddh_fk) " +
					"select '" + this.id + "', pk_seq from ERP_DONDATHANG where pk_seq in ( " + this.ddhId + " )  ";
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ERP_YCXUATKHO_DDH " + query;
				db.getConnection().rollback();
				return false;
			}
	
			for(int i = 0; i < spId.length; i++)
			{
				if(spId[i].trim().length() > 0 && Double.parseDouble(spSoluong[i].trim().replaceAll(",", "")) > 0 )
				{
					query = "insert ERP_YCXUATKHO_SANPHAM( ycxk_fk, sanpham_fk, soluongDAT, tonkho, daxuat, soluongXUAT, LOAI, SCHEME ) " +
							"select '" + this.id + "', '" + spId[i] + "', '" + spSoluongDat[i].replaceAll(",", "") + "', '" + spTonkho[i].replaceAll(",", "") + "', '" + spDaxuat[i].replaceAll(",", "") + "', '" + spSoluong[i].replaceAll(",", "") + "', '" + spLoai[i] + "', N'" + spSCheme[i].trim() + "' ";
					
					System.out.println("1.1.Update YCXK - SP: " + query);
					if(!db.update(query))
					{
						this.msg = "Khong the tao moi ERP_YCXUATKHO_SANPHAM: " + query;
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
							
							if(key.startsWith( spId[i] + "__" + spLoai[i] + "__" + spSCheme[i].trim() ))
							{
								String[] _sp = key.split("__");
								
								String _soluongCT = "0"; 
								if(this.sanpham_soluong.get(key) != null)
								{
									_soluongCT = this.sanpham_soluong.get(key).replaceAll(",", "");
								}
								
								totalCT += Double.parseDouble(_soluongCT);
								
								//CHECK TON KHO
								query = "select NGAYHETHAN, AVAILABLE from ERP_KHOTT_SP_CHITIET where KHOTT_FK = '" + this.khoNhanId + "' and SOLO = '" + _sp[3] + "' and SANPHAM_FK = '" + spId[i] + "'";
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
									this.msg = "Sản phẩm (" + spTen[i] + ") với số lô (" + _sp[3] + "), số lượng xuất (" + _soluongCT + ") còn tối đa (" + avai + "). ";
									db.getConnection().rollback();
									return false;
								}
								
								
								query = "insert ERP_YCXUATKHO_SANPHAM_CHITIET( ycxk_fk, SANPHAM_FK, solo, ngayhethan, soluong, loai, scheme ) " +
										"select '" + this.id + "', pk_seq, N'" + _sp[3] + "', '" + ngayhethan + "', '" + _soluongCT.replaceAll(",", "") + "', '" + spLoai[i] + "', '" + spSCheme[i] + "' " +
										"from SANPHAM where PK_SEQ = '" + spId[i] + "' ";
								
								System.out.println("1.2.Insert YCXK - SP - CT: " + query);
								if(!db.update(query))
								{
									this.msg = "Khong the tao moi ERP_YCXUATKHO_SANPHAM_CHITIET: " + query;
									db.getConnection().rollback();
									return false;
								}
								
								
								query = "Update ERP_KHOTT_SP_CHITIET set booked = booked + '" + _soluongCT.replaceAll(",", "") + "', AVAILABLE = AVAILABLE - '" + _soluongCT.replaceAll(",", "") + "' " +
										"where KHOTT_FK = '" + this.khoNhanId + "' and SOLO = '" + _sp[3] + "' and SANPHAM_FK = '" + spId[i] + "'  ";
								if(!db.update(query))
								{
									this.msg = "Khong the cap nhat ERP_KHOTT_SP_CHITIET: " + query;
									db.getConnection().rollback();
									return false;
								}
								
								
								query = "Update ERP_KHOTT_SANPHAM set booked = booked + '" + _soluongCT.replaceAll(",", "") + "', AVAILABLE = AVAILABLE - '" + _soluongCT.replaceAll(",", "") + "' " +
										"where KHOTT_FK = '" + this.khoNhanId + "' and SANPHAM_FK = '" + spId[i] + "'  ";
								if(!db.update(query))
								{
									this.msg = "Khong the cap nhat ERP_KHOTT_SANPHAM: " + query;
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
			
			
			//CHOT
			query = " update kho set kho.SoLuong = kho.SoLuong - CT.tongxuat, " +
					" 			   kho.Booked = kho.Booked - CT.tongxuat " +
					" from " +
					" ( " +
					" 	select a.kho_fk, b.sanpham_fk, b.solo, SUM(b.soluong) as tongxuat  " +
					" 	from ERP_YCXUATKHO a inner join ERP_YCXUATKHO_SANPHAM_CHITIET b on a.pk_seq = b.ycxk_fk " +
					" 	where ycxk_fk = '" + this.id + "' " +
					" 	group by a.kho_fk, b.solo, b.sanpham_fk " +
					" ) " +
					" CT inner join ERP_KHOTT_SP_CHITIET kho on CT.kho_fk = kho.KHOTT_FK  " +
					" 	and CT.sanpham_fk = kho.SANPHAM_FK and CT.solo = kho.SOLO ";
			if(!db.update(query))
			{
				msg = "Không thể chốt ERP_KHOTT_SP_CHITIET " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = " update kho set kho.SoLuong = kho.SoLuong - CT.tongxuat, " +
					" 			   kho.Booked = kho.Booked - CT.tongxuat " +
					" from " +
					" ( " +
					" 	select a.kho_fk, b.sanpham_fk, SUM(b.soluong) as tongxuat  " +
					" 	from ERP_YCXUATKHO a inner join ERP_YCXUATKHO_SANPHAM_CHITIET b on a.pk_seq = b.ycxk_fk " +
					" 	where ycxk_fk = '" + this.id + "' " +
					" 	group by a.kho_fk, b.sanpham_fk " +
					" ) " +
					" CT inner join ERP_KHOTT_SANPHAM kho on CT.kho_fk = kho.KHOTT_FK  " +
					" 	and CT.sanpham_fk = kho.SANPHAM_FK  ";
			if(!db.update(query))
			{
				msg = "Không thể chốt ERP_KHOTT_SANPHAM " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "update ERP_YCXUATKHO set trangthai = '2'  where pk_seq = '" + this.id + "' ";
			if(!db.update(query))
			{
				msg = "Không thể chốt ERP_YCXUATKHO " + query;
				db.getConnection().rollback();
				return false;
			}
			
			
			//TU DONG HOAN TAT CAC DON DAT HANG TU CU TOI MOI
			query = "select ddh_fk from ERP_YCXUATKHO_DDH where ycxk_fk = '" + this.id + "' order by ddh_fk asc";
			ResultSet rsDDH = db.get(query);
			String ddhID = "";
			if(rsDDH != null)
			{
				while(rsDDH.next())
				{
					ddhID += rsDDH.getString("ddh_fk") + ",";
					
					query = "  select COUNT(*) as soDONG,   " +
							" 		(   select count(distinct sanpham_fk) as soSP      " +
							"   			from     " +
							"   			(     " +
							"   					select a.sanpham_fk " +
							"   					from ERP_DONDATHANG_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ     " +
							"   					where a.dondathang_fk in (    " + ( ddhID.substring(0, ddhID.length() - 1) ) + "   )    " +
							"   				union  " +
							"   					select b.PK_SEQ " +
							"   					from ERP_DONDATHANG_CTKM_TRAKM a inner join SANPHAM b on a.SPMA = b.MA    " +
							"   							inner join CTKHUYENMAI c on a.CTKMID = c.PK_SEQ    " +
							"   					where a.DONDATHANGID in (  " + ( ddhID.substring(0, ddhID.length() - 1) ) + "     )     " +
							"   			)     " +
							"   			dathang  )	 as soSP  " +
							"  from  " +
							"  (  " +
							"  	select sanpham_fk, sum(soluongXUAT) as soluongXUAT  " +
							"  	from ERP_YCXUATKHO_SANPHAM  " +
							" 	where ycxk_fk in ( select ycxk_fk from ERP_YCXUATKHO_DDH where ddh_fk in ( " + ( ddhID.substring(0, ddhID.length() - 1) ) + " ) ) " +
							"  	group by sanpham_fk  " +
							"  )   " +
							"  XUAT inner join   " +
							"  (  " +
							"   	select dathang.sanpham_fk, SUM(dathang.soluong) as soluongDAT      " +
							"   	from     " +
							"   	(     " +
							"   			select a.sanpham_fk, b.DVDL_FK as dvCHUAN,     " +
							"   					case when a.dvdl_fk IS null then a.soluong      " +
							"   						 when a.dvdl_fk = b.DVDL_FK then a.soluong     " +
							"   						 else  a.soluong * ( select SOLUONG1 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )      " +
							"   										 / ( select SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )	 end as soluong, 0 as loai, ' ' as scheme    " +
							"   			from ERP_DONDATHANG_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ     " +
							"   			where a.dondathang_fk in (    " + ( ddhID.substring(0, ddhID.length() - 1) ) + "   )    " +
							"   		union ALL   " +
							"   			select b.PK_SEQ, b.DVDL_FK as dvCHUAN, a.soluong, 1 as loai, c.SCHEME    " +
							"   			from ERP_DONDATHANG_CTKM_TRAKM a inner join SANPHAM b on a.SPMA = b.MA    " +
							"   					inner join CTKHUYENMAI c on a.CTKMID = c.PK_SEQ    " +
							"   			where a.DONDATHANGID in (  " + ( ddhID.substring(0, ddhID.length() - 1) ) + "     )     " +
							"   	)     " +
							"   	dathang   " +
							"   	group by dathang.sanpham_fk  " +
							"  )  " +
							"  DDH on XUAT.sanpham_fk = DDH.sanpham_fk  " +
							"  where XUAT.soluongXUAT >= DDH.soluongDAT ";
					
					System.out.println("CHECK HOAN TAT: " + query);
					ResultSet rsCHECK = db.get(query);
					if(rsCHECK.next())
					{
						String trangthai = "";
						if(rsCHECK.getInt("soDONG") == rsCHECK.getInt("soSP"))
							trangthai = "4";  //HOAN TAT
						else
							trangthai = "2";  //KE TOAN DUYET
						
						query = " UPDATE ERP_DONDATHANG set trangthai = '" + trangthai + "' where pk_seq in ( " + ( rsDDH.getString("ddh_fk") ) + " ) ";
						if(!db.update(query))
						{
							msg = "Không thể chốt ERP_YCXUATKHO " + query;
							db.getConnection().rollback();
							return false;
						}
					}
				}
				rsDDH.close();
			}
			
			
			//TU DONG DAY RA NHAN HANG CHO NPP (KO CHI NHAN HANG THEO DVKD -- > DVKD LAY TU SP)
			/*query = " insert NHAPHANG(NGAYNHAN, NGAYCHUNGTU, NPP_FK, NCC_FK, GSBH_FK, ASM_FK, BM_FK, DVKD_FK, KBH_FK, YCXK_FK, TRANGTHAI, NGUOITAO, NGAYTAO, NGUOISUA, NGAYSUA) " +
					" select distinct NgayYeuCau, NgayYeuCau, NPP_FK,  " +
					" 			( select top(1) NCC_FK from NHACUNGCAP_DVKD where PK_SEQ in ( select NCC_DVKD_FK from NHAPP_NHACC_DONVIKD where NPP_FK = a.NPP_FK ) ), " +
					"			( select top(1) GSBH_FK from NHAPP_GIAMSATBH where NPP_FK = a.NPP_FK and NGAYBATDAU <= a.NgayYeuCau and NGAYKETTHUC >= a.NgayYeuCau ), " +
					"			( select top(1) ASM_FK from ASM_KHUVUC where KHUVUC_FK in ( select KHUVUC_FK from NHAPHANPHOI where PK_SEQ = a.NPP_FK )), " +
					"			( select top(1) BM_FK from BM_CHINHANH where VUNG_FK in ( select VUNG_FK from KHUVUC where PK_SEQ in (  select KHUVUC_FK from NHAPHANPHOI where PK_SEQ = a.NPP_FK ) ) ), " +
					" 	   c.DVKD_FK, ( select top(1) KBH_FK from NHAPP_KBH where NPP_FK = a.NPP_FK ) as KBH_FK, '" + this.id + "', '0', '" + this.userId + "', '" + getDateTime() + "', '" + this.userId + "', '" + getDateTime() + "' " +
					" from ERP_YCXUATKHO a inner join ERP_YCXUATKHO_SANPHAM b on a.PK_SEQ = b.ycxk_fk " +
					" 		inner join SANPHAM c on b.sanpham_fk = c.PK_SEQ " +
					" where a.PK_SEQ = '" + this.id + "' ";*/
			
			query = " insert NHAPHANG(NGAYNHAN, NGAYCHUNGTU, NPP_FK, NCC_FK, GSBH_FK, ASM_FK, BM_FK, DVKD_FK, KBH_FK, YCXK_FK, TRANGTHAI, NGUOITAO, NGAYTAO, NGUOISUA, NGAYSUA) " +
					" select distinct NgayYeuCau, NgayYeuCau, NPP_FK,  " +
					" 			( select top(1) NCC_FK from NHACUNGCAP_DVKD where PK_SEQ in ( select NCC_DVKD_FK from NHAPP_NHACC_DONVIKD where NPP_FK = a.NPP_FK ) ), " +
					"			( select top(1) GSBH_FK from NHAPP_GIAMSATBH where NPP_FK = a.NPP_FK and NGAYBATDAU <= a.NgayYeuCau and NGAYKETTHUC >= a.NgayYeuCau ), " +
					"			( select top(1) ASM_FK from ASM_KHUVUC where KHUVUC_FK in ( select KHUVUC_FK from NHAPHANPHOI where PK_SEQ = a.NPP_FK )), " +
					"			( select top(1) BM_FK from BM_CHINHANH where VUNG_FK in ( select VUNG_FK from KHUVUC where PK_SEQ in (  select KHUVUC_FK from NHAPHANPHOI where PK_SEQ = a.NPP_FK ) ) ), " +
					" 	   NULL as DVKD_FK, ( select top(1) KBH_FK from NHAPP_KBH where NPP_FK = a.NPP_FK ) as KBH_FK, '" + this.id + "', '0', '" + this.userId + "', '" + getDateTime() + "', '" + this.userId + "', '" + getDateTime() + "' " +
					" from ERP_YCXUATKHO a inner join ERP_YCXUATKHO_SANPHAM b on a.PK_SEQ = b.ycxk_fk " +
					" where a.PK_SEQ = '" + this.id + "' ";
			
			System.out.println("---INSERT DON DAT HANG: " + query );
			if(!db.update(query))
			{
				msg = "Không tạo mới NHAPHANG " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = " insert NHAPHANG_SP(NHAPHANG_FK, SANPHAM_FK, SOLUONG, soluongNHAN, DONGIA, CHIETKHAU, DVDL_FK, LOAI, SCHEME, SOLO, NGAYHETHAN) " +
					" select ( select pk_seq from NHAPHANG where YCXK_FK = a.PK_SEQ  ),  " +
					" 		b.sanpham_fk, b.soluong, NULL, b.dongia, 0 as chietkhau, c.DVDL_FK, b.LOAI, b.SCHEME, b.solo, b.ngayhethan " +
					" from ERP_YCXUATKHO a inner join ERP_YCXUATKHO_SANPHAM_CHITIET b on a.PK_SEQ = b.ycxk_fk " +
					" 		inner join SANPHAM c on b.sanpham_fk = c.PK_SEQ   " +
					" where a.PK_SEQ = '" + this.id + "' and b.soluong > 0 ";
			if(!db.update(query))
			{
				msg = "Không tạo mới NHAPHANG_SP " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "insert NHAPHANG_DDH(nhaphang_fk, ddh_fk)  " +
					"select ( select PK_SEQ from NHAPHANG where YCXK_FK = '" + this.id + "' ) as nhID, ddh_fk  " +
					"from ERP_YCXUATKHO_DDH where ycxk_fk = '" + this.id + "'";
			if(!db.update(query))
			{
				msg = "Không tạo mới NHAPHANG_DDH " + query;
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

		
}
