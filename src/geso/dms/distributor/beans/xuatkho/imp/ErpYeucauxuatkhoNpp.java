package geso.dms.distributor.beans.xuatkho.imp;

import geso.dms.distributor.beans.xuatkho.IErpYeucauxuatkhoNpp;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

public class ErpYeucauxuatkhoNpp implements IErpYeucauxuatkhoNpp
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
	
	String khId;
	ResultSet khRs;
	
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
	
	String nppId;
	String nppTen;
	String sitecode;
	String xuatcho;
	
	dbutils db;
	Utility util;
	
	public ErpYeucauxuatkhoNpp()
	{
		this.id = "";
		this.ngayyeucau = getDateTime();
		this.ghichu = "";
		this.khoNhanId = "";
		this.khId = "";
		this.msg = "";
		this.trangthai = "0";
		this.ddhId = "";
		this.xuatcho = "0";
		
		this.sanpham_soluong = new Hashtable<String, String>();
		
		this.db = new dbutils();
		this.util = new Utility();
	}
	
	public ErpYeucauxuatkhoNpp(String id)
	{
		this.id = id;
		this.ngayyeucau = getDateTime();
		this.ghichu = "";
		this.khoNhanId = "";
		this.khId = "";
		this.msg = "";
		this.trangthai = "0";
		this.ddhId = "";
		this.xuatcho = "0";

		this.sanpham_soluong = new Hashtable<String, String>();
		
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

	
	public void createRs() 
	{
		this.getNppInfo();
		this.khoNhanRs = db.get("select PK_SEQ, TEN from KHO where trangthai = '1' and pk_seq in " + this.util.quyen_kho(this.userId)     );
		
		String query = "select PK_SEQ, MA + ' - ' + TEN as TEN from NHAPHANPHOI where TRANGTHAI = '1'  AND PK_SEQ != '" + this.nppId + "' and loaiNPP = '4' ";
		if(this.xuatcho.equals("1"))
			query = " select pk_seq, TEN from KHACHHANG where trangthai = '1' and NPP_FK = '" + this.nppId + "' and KBH_FK = '100052' ";
		this.khRs = db.get(query);
		
		
		if(this.khId.trim().length() > 0 && this.khoNhanId.trim().length() > 0 )
		{
			query = "select PK_SEQ, CAST(pk_seq as varchar(10)) + ' / ' + NgayDonHang as ten " +
					"from ERP_DONDATHANGNPP where TRANGTHAI = '2' and NPP_FK = '" + this.nppId + "' and KHO_FK = '" + this.khoNhanId + "'  ";
			
			if(this.xuatcho.equals("0")) //XUAT CHO DOI TAC
				query += " and NPP_DAT_FK = '" + this.khId + "' ";
			else
				query += " and KHACHHANG_FK = '" + this.khId + "' ";
			
			if(this.id.trim().length() > 0)
			{
				query += " union  " +
						 "select PK_SEQ, CAST(pk_seq as varchar(10)) + ' / ' + NgayDonHang as ten " +
						 "from ERP_DONDATHANGNPP where pk_seq in ( select ddh_fk from ERP_YCXUATKHONPP_DDH where ycxk_fk = '" + this.id + "'  )   ";
			}
			
			System.out.println("----LAY DON DAT HANG: " + query );
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
			query = " select ddh.*, ISNULL(xuat.soluongXUAT, 0) as xuat,   " +
					" 	ISNULL( daxuat.soluongDAXUAT, 0) as daxuat,   " +
					" 	ISNULL( ( select AVAILABLE from NHAPP_KHO where kho_fk = '" + this.khoNhanId + "' and sanpham_fk = ddh.PK_SEQ and NPP_FK = '" + this.nppId + "' and KBH_FK = ( select kbh_fk from ERP_DONDATHANGNPP where pk_seq = '" + this.ddhId + "' ) ), 0) + ISNULL(xuat.soluongXUAT, 0) as tonkho    " +
					" from   " +
					" (   " +
					" 	select sp.PK_SEQ, sp.MA, sp.TEN, dv.DONVI, scheme, loai, SUM(dathang.soluong) as soluongDAT    " +
					" 	from   " +
					" 	(   " +
					" 			select a.sanpham_fk, b.DVDL_FK as dvCHUAN,   " +
					" 					case when a.dvdl_fk IS null then a.soluong    " +
					" 						 when a.dvdl_fk = b.DVDL_FK then a.soluong   " +
					" 						 else  a.soluong * ( select SOLUONG1 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk and DVDL1_FK=b.DVDL_FK )    " +
					" 										 / ( select SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk  and DVDL1_FK=b.DVDL_FK )	 end as soluong, 0 as loai, ' ' as scheme  " +
					" 			from ERP_DONDATHANGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ   " +
					" 			where a.dondathang_fk in (   " + this.ddhId + "   )  " +
					" 	)   " +
					" 	dathang inner join SANPHAM sp on dathang.sanpham_fk = sp.PK_SEQ   " +
					" 			inner join DONVIDOLUONG dv on sp.DVDL_FK = dv.PK_SEQ   " +
					" 	group by sp.PK_SEQ, sp.MA, sp.TEN, dv.DONVI, scheme, loai  " +
					" )   " +
					" ddh left join    " +
					" (   " +
					" 	select b.sanpham_fk, b.LOAI, isnull(b.SCHEME, '') as SCHEME, b.soluongXUAT   " +
					" 	from ERP_YCXUATKHONPP a inner join ERP_YCXUATKHONPP_SANPHAM b on a.PK_SEQ = b.ycxk_fk  " +
					" 	where a.PK_SEQ = '" + ycxkID + "'  " +
					" )  " +
					" xuat on ddh.PK_SEQ = xuat.sanpham_fk and ddh.loai = xuat.LOAI and ddh.scheme = xuat.scheme left join    " +
					" (   " +
					" 	select b.sanpham_fk, b.LOAI, isnull(b.SCHEME, '') as SCHEME, SUM( b.soluongXUAT ) as soluongDAXUAT   " +
					" 	from ERP_YCXUATKHONPP a inner join ERP_YCXUATKHONPP_SANPHAM b on a.PK_SEQ = b.ycxk_fk  " +
					" 	where a.PK_SEQ != '" + ycxkID + "' and a.TRANGTHAI != 3   " +
					" 		and a.PK_SEQ in ( select ycxk_fk from  ERP_YCXUATKHONPP_DDH where ddh_fk in (  " + this.ddhId + "  ) )  " +
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
						else
							conLAI = spRs.getDouble("xuat");
						 
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
								spSOLUONGXUAT += formater.format( conLAI ) + "__";
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
				catch (Exception e) {e.printStackTrace(); System.out.println("EXCEPTION SP: " + e.getMessage() ); }
				
			}
			
		}
		else
		{
			this.spId = null;
			this.spMa = null;
			this.spTen = null;
			this.spDonvi = null;
			this.spSoluongDat = null;
			this.spTonkho = null;
			this.spDaxuat = null;
			this.spSoluong = null;
			this.spLoai = null;
			this.spSCheme = null;
		}
		
	}

	public void init() 
	{
		String query = "select ngayyeucau, xuatcho, ISNULL(ghichu, '') as ghichu, isnull(npp_dat_fk, khachhang_fk) as khId, kho_fk, trangthai " +
						"from ERP_YCXUATKHONPP where pk_seq = '" + this.id + "'";
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
					this.khId = rs.getString("khId");
					this.khoNhanId = rs.getString("kho_fk");
					this.xuatcho = rs.getString("xuatcho");
					this.trangthai = rs.getString("trangthai");
				}
				rs.close();
				
				//INIT DDH
				query = "select ddh_fk from ERP_YCXUATKHONPP_DDH where ycxk_fk = '" + this.id + "' ";
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
				query = "select sanpham_fk, solo, isnull(LOAI, 0) as LOAI, soluong, ngayhethan, isnull(scheme, '') as scheme " +
						"from ERP_YCXUATKHONPP_SANPHAM_CHITIET where ycxk_fk = '" + this.id + "'";
				System.out.println("---INIT SP: " + query);
				rs = db.get(query);
				if(rs != null)
				{
					Hashtable<String, String> sp_soluong = new Hashtable<String, String>();
					while(rs.next())
					{
						if(this.trangthai.equals("0"))
						{
							//System.out.println("---KEY BEAN: " + rs.getString("sanpham_fk") + "__" + rs.getString("LOAI") + "__" + rs.getString("SCHEME") + "__" + rs.getString("solo"));
							sp_soluong.put(rs.getString("sanpham_fk") + "__" + rs.getString("LOAI") + "__" + rs.getString("SCHEME").trim() + "__" + rs.getString("solo"), rs.getString("soluong") );
						}
						else
						{
							//System.out.println("---KEY BEAN: " + rs.getString("sanpham_fk") + "__" + rs.getString("LOAI") + "__" + rs.getString("SCHEME") + "__" + rs.getString("solo"));
							String key = rs.getString("sanpham_fk") + "__" + rs.getString("LOAI") + "__" + rs.getString("SCHEME").trim();
							
							String value = sp_soluong.get(key);
							if(value != null)
								value += ";" + rs.getString("solo") + "__" + rs.getString("soluong") + "__" + rs.getString("ngayhethan");
							else
								value = rs.getString("solo") + "__" + rs.getString("soluong") + "__" + rs.getString("ngayhethan");
							
							sp_soluong.put( key, value );
						}
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

	
	public void setNppId(String khId) {
		
		this.nppId = khId;
	}

	
	public ResultSet getKhRs() {
		
		return this.khRs;
	}

	
	public void setKhRs(ResultSet khRs) {
		
		this.khRs = khRs;
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
					   "from NHAPP_KHO_CHITIET ct " +
					   "where KHO_FK = '" + this.khoNhanId + "' and SANPHAM_FK = '" + spIds + "' and KBH_FK = ( select kbh_fk from ERP_DONDATHANGNPP where pk_seq = '" + this.ddhId + "' ) and NPP_FK = '" + this.nppId + "' " +
					   		" and ( AVAILABLE + ISNULL( ( select sum(soluong) from ERP_YCXUATKHO_SANPHAM_CHITIET where ycxk_fk = '" + ( this.id.trim().length() > 0 ? this.id : "-1" ) + "' and sanpham_fk = ct.sanpham_fk and solo = ct.solo  ), 0 ) ) > 0 " +
					    " order by NGAYHETHAN asc";
		
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

		if( this.khId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn khách hàng ETC / đối tác";
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
			
			String khachhang_fk = "NULL";
			String npp_dat_fk = "NULL";
			if(this.xuatcho.equals("0"))
				npp_dat_fk = this.khId;
			else
				khachhang_fk = this.khId;
			
			String query = " insert ERP_YCXUATKHONPP(NgayYeuCau, ghichu, trangthai, npp_fk, kho_fk, xuatcho, NPP_DAT_FK, KHACHHANG_FK, KBH_FK, ngaytao, nguoitao, ngaysua, nguoisua) " +
						   " select '" + this.ngayyeucau + "', N'" + this.ghichu + "', '0', '" + this.nppId + "', " + this.khoNhanId + ", '" + this.xuatcho + "', " + npp_dat_fk + ", " + khachhang_fk + ", kbh_fk, " +
						   		" '" + getDateTime() + "', '" + this.userId + "', '" + getDateTime() + "', '" + this.userId + "' " +
						   " from ERP_DONDATHANGNPP where pk_seq = '" + this.ddhId + "' ";
			
			System.out.println("1.Insert ERP_YCXUATKHONPP: " + query);
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ERP_YCXUATKHONPP " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "Insert ERP_YCXUATKHONPP_DDH(ycxk_fk, ddh_fk) " +
					"select IDENT_CURRENT('ERP_YCXUATKHONPP'), pk_seq from ERP_DONDATHANGNPP where pk_seq in ( " + this.ddhId + " )  ";
			System.out.println("2.chen ERP_YCXUATKHONPP: " + query);
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ERP_YCXUATKHONPP_DDH " + query;
				db.getConnection().rollback();
				return false;
			}
			
			for(int i = 0; i < spId.length; i++)
			{
				if(spId[i].trim().length() > 0 && Double.parseDouble(spSoluong[i].trim().replaceAll(",", "")) > 0 )
				{
					query = "insert ERP_YCXUATKHONPP_SANPHAM( ycxk_fk, sanpham_fk, soluongDAT, tonkho, daxuat, soluongXUAT, LOAI, SCHEME ) " +
							"select IDENT_CURRENT('ERP_YCXUATKHONPP'), '" + spId[i] + "', '" + spSoluongDat[i].replaceAll(",", "") + "', '" + spTonkho[i].replaceAll(",", "") + "', '" + spDaxuat[i].replaceAll(",", "") + "', '" + spSoluong[i].replaceAll(",", "") + "', '" + spLoai[i] + "', N'" + spSCheme[i] + "' ";
					
					System.out.println("1.1.Insert YCXK - SP: " + query);
					if(!db.update(query))
					{
						this.msg = "Khong the tao moi ERP_YCXUATKHONPP_SANPHAM: " + query;
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
								query = "select AVAILABLE from NHAPP_KHO_CHITIET " +
										"where KHO_FK = '" + this.khoNhanId + "' and SOLO = '" + _sp[3] + "' and SANPHAM_FK = '" + spId[i] + "' and NPP_FK = '" + this.nppId + "' and KBH_FK = ( select kbh_fk from ERP_DONDATHANGNPP where pk_seq = '" + this.ddhId + "' ) ";
								System.out.println("---LAY AVAI: " + query);
								ResultSet rsTK = db.get(query);
								double avai = 0;
								if(rsTK.next())
								{
									avai = rsTK.getDouble("AVAILABLE");
								}
								rsTK.close();
								
								if( Double.parseDouble(_soluongCT) > avai )
								{
									this.msg = "Sản phẩm (" + spTen[i] + ") với số lô (" + _sp[3] + "), số lượng xuất (" + _soluongCT + ") còn tối đa (" + avai + "). ";
									db.getConnection().rollback();
									return false;
								}
								
								
								query = "insert ERP_YCXUATKHONPP_SANPHAM_CHITIET( ycxk_fk, SANPHAM_FK, solo, soluong, loai, scheme ) " +
										"select IDENT_CURRENT('ERP_YCXUATKHONPP'), pk_seq, N'" + _sp[3] + "', '" + _soluongCT.replaceAll(",", "") + "', '" + spLoai[i] + "', '" + spSCheme[i] + "' " +
										"from SANPHAM where PK_SEQ = '" + spId[i] + "' ";
								
								System.out.println("1.2.Insert YCXK - SP - CT: " + query);
								if(!db.update(query))
								{
									this.msg = "Khong the tao moi ERP_YCXUATKHO_SANPHAM_CHITIET: " + query;
									db.getConnection().rollback();
									return false;
								}
								
								
								query = "Update NHAPP_KHO_CHITIET set booked = booked + '" + _soluongCT.replaceAll(",", "") + "', AVAILABLE = AVAILABLE - '" + _soluongCT.replaceAll(",", "") + "' " +
										"where KHO_FK = '" + this.khoNhanId + "' and SOLO = '" + _sp[3] + "' and SANPHAM_FK = '" + spId[i] + "' and NPP_FK = '" + this.nppId + "' and KBH_FK = ( select kbh_fk from ERP_DONDATHANGNPP where pk_seq = '" + this.ddhId + "' ) ";
								if(!db.update(query))
								{
									this.msg = "Khong the cap nhat NHAPP_KHO_CHITIET: " + query;
									db.getConnection().rollback();
									return false;
								}
								
								
								query = "Update NHAPP_KHO set booked = booked + '" + _soluongCT.replaceAll(",", "") + "', AVAILABLE = AVAILABLE - '" + _soluongCT.replaceAll(",", "") + "' " +
										"where KHO_FK = '" + this.khoNhanId + "' and SANPHAM_FK = '" + spId[i] + "' and NPP_FK = '" + this.nppId + "' and KBH_FK = ( select kbh_fk from ERP_DONDATHANGNPP where pk_seq = '" + this.ddhId + "' )  ";
								if(!db.update(query))
								{
									this.msg = "Khong the cap nhat NHAPP_KHO: " + query;
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
			e.printStackTrace();
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

		if( this.khId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn khách hàng ETC / đối tác";
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
		
			String khachhang_fk = "NULL";
			String npp_dat_fk = "NULL";
			if(this.xuatcho.equals("0"))
				npp_dat_fk = this.khId;
			else
				khachhang_fk = this.khId;
			
			String query = " Update ERP_YCXUATKHONPP set NgayYeuCau = '" + this.ngayyeucau + "', ghichu = N'" + this.ghichu + "', npp_fk = '" + this.nppId + "', kho_fk = '" + this.khoNhanId + "', " +
						   "	xuatcho = '" + this.xuatcho + "', khachhang_fk = " + khachhang_fk + ", kbh_fk = ( select KBH_FK from ERP_DONDATHANGNPP where pk_seq = '" + this.ddhId + "' ), npp_dat_fk = " + npp_dat_fk + ", " +
							"	ngaysua = '" + this.getDateTime() + "', nguoisua = '" + this.userId + "' where pk_seq = '" + this.id + "' ";
			
			System.out.println("1.Update ERP_YCXUATKHONPP: " + query);
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_YCXUATKHONPP " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = " update kho set kho.AVAILABLE = kho.AVAILABLE + CT.tongxuat, " +
					" 			   kho.BOOKED = kho.BOOKED - CT.tongxuat " +
					" from " +
					" ( " +
					" 	select a.kho_fk, a.kbh_fk, b.sanpham_fk, b.solo, SUM(b.soluong) as tongxuat  " +
					" 	from ERP_YCXUATKHONPP a inner join ERP_YCXUATKHONPP_SANPHAM_CHITIET b on a.pk_seq = b.ycxk_fk " +
					" 	where ycxk_fk = '" + this.id + "' " +
					" 	group by a.kho_fk, a.kbh_fk, b.solo, b.sanpham_fk " +
					" ) " +
					" CT inner join NHAPP_KHO_CHITIET kho on CT.kho_fk = kho.KHO_FK  " +
					" 	and CT.sanpham_fk = kho.SANPHAM_FK and CT.solo = kho.SOLO and CT.KBH_FK = kho.KBH_FK and kho.NPP_FK = '" + this.nppId + "' ";
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
					" 	select a.kho_fk, a.kbh_fk, b.sanpham_fk, SUM(b.soluong) as tongxuat  " +
					" 	from ERP_YCXUATKHONPP a inner join ERP_YCXUATKHONPP_SANPHAM_CHITIET b on a.pk_seq = b.ycxk_fk " +
					" 	where ycxk_fk = '" + this.id + "' " +
					" 	group by a.kho_fk, a.kbh_fk, b.sanpham_fk " +
					" ) " +
					" CT inner join NHAPP_KHO kho on CT.kho_fk = kho.KHO_FK  " +
					" 	and CT.sanpham_fk = kho.SANPHAM_FK and CT.KBH_FK = kho.KBH_FK and kho.NPP_FK = '" + this.nppId + "'  ";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_KHOTT_SANPHAM " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete ERP_YCXUATKHONPP_DDH where ycxk_fk = '" + this.id + "' ";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_YCXUATKHO_DDH " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete ERP_YCXUATKHONPP_SANPHAM where ycxk_fk = '" + this.id + "' ";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_YCXUATKHO_SANPHAM " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete ERP_YCXUATKHONPP_SANPHAM_CHITIET where ycxk_fk = '" + this.id + "' ";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_YCXUATKHO_SANPHAM " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "Insert ERP_YCXUATKHONPP_DDH(ycxk_fk, ddh_fk) " +
					"select '" + this.id + "', pk_seq from ERP_DONDATHANGNPP where pk_seq in ( " + this.ddhId + " )  ";
			System.out.println("2.chen ERP_YCXUATKHONPP: " + query);
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ERP_YCXUATKHONPP_DDH " + query;
				db.getConnection().rollback();
				return false;
			}
			
			for(int i = 0; i < spId.length; i++)
			{
				if(spId[i].trim().length() > 0 && Double.parseDouble(spSoluong[i].trim().replaceAll(",", "")) > 0 )
				{
					query = "insert ERP_YCXUATKHONPP_SANPHAM( ycxk_fk, sanpham_fk, soluongDAT, tonkho, daxuat, soluongXUAT, LOAI, SCHEME ) " +
							"select '" + this.id + "', '" + spId[i] + "', '" + spSoluongDat[i].replaceAll(",", "") + "', '" + spTonkho[i].replaceAll(",", "") + "', '" + spDaxuat[i].replaceAll(",", "") + "', '" + spSoluong[i].replaceAll(",", "") + "', '" + spLoai[i] + "', N'" + spSCheme[i] + "' ";
					
					System.out.println("1.1.Insert YCXK - SP: " + query);
					if(!db.update(query))
					{
						this.msg = "Khong the tao moi ERP_YCXUATKHONPP_SANPHAM: " + query;
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
								query = "select AVAILABLE from NHAPP_KHO_CHITIET " +
										"where KHO_FK = '" + this.khoNhanId + "' and SOLO = '" + _sp[3] + "' and SANPHAM_FK = '" + spId[i] + "' and NPP_FK = '" + this.nppId + "' and KBH_FK = ( select kbh_fk from ERP_DONDATHANGNPP where pk_seq = '" + this.ddhId + "' ) ";
								System.out.println("---LAY AVAI: " + query);
								ResultSet rsTK = db.get(query);
								double avai = 0;
								if(rsTK.next())
								{
									avai = rsTK.getDouble("AVAILABLE");
								}
								rsTK.close();
								
								if( Double.parseDouble(_soluongCT) > avai )
								{
									this.msg = "Sản phẩm (" + spTen[i] + ") với số lô (" + _sp[3] + "), số lượng xuất (" + _soluongCT + ") còn tối đa (" + avai + "). ";
									db.getConnection().rollback();
									return false;
								}
								
								
								query = "insert ERP_YCXUATKHONPP_SANPHAM_CHITIET( ycxk_fk, SANPHAM_FK, solo, soluong, loai, scheme ) " +
										"select '" + this.id + "', pk_seq, N'" + _sp[3] + "', '" + _soluongCT.replaceAll(",", "") + "', '" + spLoai[i] + "', '" + spSCheme[i] + "' " +
										"from SANPHAM where PK_SEQ = '" + spId[i] + "' ";
								
								System.out.println("1.2.Insert YCXK - SP - CT: " + query);
								if(!db.update(query))
								{
									this.msg = "Khong the tao moi ERP_YCXUATKHO_SANPHAM_CHITIET: " + query;
									db.getConnection().rollback();
									return false;
								}
								
								
								query = "Update NHAPP_KHO_CHITIET set booked = booked + '" + _soluongCT.replaceAll(",", "") + "', AVAILABLE = AVAILABLE - '" + _soluongCT.replaceAll(",", "") + "' " +
										"where KHO_FK = '" + this.khoNhanId + "' and SOLO = '" + _sp[3] + "' and SANPHAM_FK = '" + spId[i] + "' and NPP_FK = '" + this.nppId + "' and KBH_FK = ( select kbh_fk from ERP_DONDATHANGNPP where pk_seq = '" + this.ddhId + "' ) ";
								if(!db.update(query))
								{
									this.msg = "Khong the cap nhat NHAPP_KHO_CHITIET: " + query;
									db.getConnection().rollback();
									return false;
								}
								
								
								query = "Update NHAPP_KHO set booked = booked + '" + _soluongCT.replaceAll(",", "") + "', AVAILABLE = AVAILABLE - '" + _soluongCT.replaceAll(",", "") + "' " +
										"where KHO_FK = '" + this.khoNhanId + "' and SANPHAM_FK = '" + spId[i] + "' and NPP_FK = '" + this.nppId + "' and KBH_FK = ( select kbh_fk from ERP_DONDATHANGNPP where pk_seq = '" + this.ddhId + "' )  ";
								if(!db.update(query))
								{
									this.msg = "Khong the cap nhat NHAPP_KHO: " + query;
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
	
	public String getKhId() 
	{
		return this.khId;
	}

	public void setKhId(String khId) 
	{
		this.khId = khId;
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
		geso.dms.distributor.util.Utility util=new geso.dms.distributor.util.Utility();
		this.nppId=util.getIdNhapp(this.userId);
		this.nppTen=util.getTenNhaPP();
		//this.dangnhap = util.getDangNhap();
		this.sitecode=util.getSitecode();
	}

	public String getXuatcho() {

		return this.xuatcho;
	}

	public void setXuatcho(String xuatcho) {
		
		this.xuatcho = xuatcho;
	}


		
}
