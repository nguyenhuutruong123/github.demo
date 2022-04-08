package geso.dms.center.beans.doihang.imp;

import geso.dms.center.beans.doihang.IErpDenghidoihang;
import geso.dms.center.db.sql.db_Sync;
import geso.dms.center.db.sql.dbutils;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;

public class ErpDenghidoihang implements IErpDenghidoihang
{
	private static final long serialVersionUID = 1L;
	String userId;
	String id;

	String ngayyeucau;
	String ngaydenghi;
	String ghichu;

	String msg;
	String trangthai;

	String loaidonhang;  //0 đơn đặt hàng, 1 đơn hàng khuyến mại ứng hàng, 3 đơn hàng khuyến mại tích lũy, 4 đơn hàng trưng bày, 5 đơn hàng khác
	String chietkhau;
	String vat;

	String khoNhanId;
	ResultSet khoNhanRs;

	String khottDoiId;
	ResultSet khottDoiRs;

	String nppId;
	ResultSet nppRs;

	String dvkdId;
	ResultSet dvkdRs;

	String kbhId;
	ResultSet kbhRs;

	ResultSet sanphamRs;
	ResultSet sanphamDOIRs;

	String tsdh;

	dbutils db;

	public ErpDenghidoihang()
	{
		this.id = "";
		this.ngayyeucau = getDateTime();
		this.ngaydenghi = "";
		this.ghichu = "";
		this.khoNhanId = "";
		this.nppId = "";
		this.msg = "";
		this.loaidonhang = "0";
		this.trangthai = "0";
		this.chietkhau = "0";
		this.vat = "10";
		this.dvkdId = "";
		this.kbhId = "";
		this.tsdh = "6";

		this.db = new dbutils();
	}

	public ErpDenghidoihang(String id)
	{
		this.id = id;
		this.ngayyeucau = getDateTime();
		this.ngaydenghi = "";
		this.ghichu = "";
		this.khoNhanId = "";
		this.nppId = "";
		this.msg = "";
		this.loaidonhang = "0";
		this.trangthai = "0";
		this.chietkhau = "0";
		this.vat = "10";
		this.dvkdId = "";
		this.kbhId = "";
		this.tsdh = "1";

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


	public boolean createNK(HttpServletRequest request) 
	{
		/*if(this.ngaydenghi.trim().length() < 10)
		{
			this.msg = "Vui lòng nhập ngày đề nghị";
			return false;
		}

		if( this.dvkdId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn đơn vị kinh doanh";
			return false;
		}

		if( this.kbhId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn kênh bán hàng";
			return false;
		}

		if( this.nppId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn nhà phân phối đổi hàng";
			return false;
		}

		try
		{
			db.getConnection().setAutoCommit(false);

			String khonhan_fk = this.khoNhanId.trim().length() <= 0 ? "null" : this.khoNhanId.trim();
			String vat = this.vat.trim().length() <= 0 ? "0" : this.vat.trim();

			String query = " insert ERP_DeNghiDoiHang(ngaydenghi, ghichu, trangthai, dvkd_fk, kbh_fk, npp_fk, kho_fk, chietkhau, vat, ngaytao, nguoitao, ngaysua, nguoisua) " +
						   " values('" + this.ngaydenghi + "', N'" + this.ghichu + "', '0', '" + dvkdId + "', '" + kbhId + "', '" + nppId + "', " + khonhan_fk + ", '0', '" + vat + "', '" + getDateTime() + "', '" + this.userId + "', '" + getDateTime() + "', '" + this.userId + "' )";

			System.out.println("1.Insert DDH: " + query);
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ERP_DeNghiDoiHang " + query;
				db.getConnection().rollback();
				return false;
			}

			//LAY ID
			ResultSet rsDDH = db.get("select IDENT_CURRENT('ERP_DeNghiDoiHang') as ID ");
			if(rsDDH.next())
			{
				this.id = rsDDH.getString("ID");
			}
			rsDDH.close();

			String[] spId = request.getParameterValues("spID");
			String[] spTEN = request.getParameterValues("spTEN");
			String[] spSOLO = request.getParameterValues("spSOLO");
			String[] spSOLUONG = request.getParameterValues("spSOLUONG");
			String[] spTONKHO = request.getParameterValues("spTONKHO");
			String[] spDONGIA = request.getParameterValues("spDONGIA");

			if( spId != null )
			{
				for(int i = 0; i < spId.length; i++ )
				{
					if(spSOLO[i].trim().length() > 0 && spSOLUONG[i].trim().length() > 0)
					{
						String[] soLO = spSOLO[i].split(" --- ");

						//CHECK TON KHO
						query = "select AVAILABLE " +
								"from NHAPP_KHO_CHITIET where SOLO = '" + soLO[0] + "' and KBH_FK = '" + this.kbhId + "' and SANPHAM_FK = '" + spId[i] + "' and KHO_FK = '" + this.khoNhanId + "' and NPP_FK = '" + this.nppId + "'  ";
						double avai = 0;
						ResultSet rsCHECK = db.get(query);
						if(rsCHECK.next())
						{
							avai = rsCHECK.getDouble("AVAILABLE");
						}
						rsCHECK.close();

						if(avai < Double.parseDouble(spSOLUONG[i].trim().replaceAll(",", "")))
						{
							this.msg = "Sản phẩm ( " + spTEN[i] + " ) với số lô ( " + soLO[0] + " ) với số lượng đề nghị ( " + spSOLUONG[i] + " ) không đủ tồn kho ( " + avai + " ) ";
							db.getConnection().rollback();
							return false;
						}

						query = "insert ERP_DENGHIDOIHANG_SANPHAM(denghidoihang_fk, sanpham_fk, dvdl_fk, solo, tonkho, denghi, dongia) " +
								"select '" + this.id + "', '" + spId[i] + "', DVDL_FK, '" + soLO[0] + "', '" + spTONKHO[i].replaceAll(",", "") + "', '" + spSOLUONG[i].replaceAll(",", "") + "', '" + spDONGIA[i].replaceAll(",", "") + "' from SANPHAM " +
								"where pk_seq = '" + spId[i] + "' ";

						System.out.println("--INSERT SP: " + query);
						if(!db.update(query))
						{
							this.msg = "Không thể tạo mới ERP_DENGHIDOIHANG_SANPHAM " + query;
							db.getConnection().rollback();
							return false;
						}

						//UPDATE KHO
						query = "update NHAPP_KHO_CHITIET set AVAILABLE = AVAILABLE - '" + spSOLUONG[i].replaceAll(",", "") + "', BOOKED = BOOKED + '" + spSOLUONG[i].replaceAll(",", "") + "' " +
								"where SOLO = '" + soLO[0] + "' and KBH_FK = '" + this.kbhId + "' and SANPHAM_FK = '" + spId[i] + "' and KHO_FK = '" + this.khoNhanId + "' and NPP_FK = '" + this.nppId + "'  ";
						if(!db.update(query))
						{
							this.msg = "Không thể cập nhật NHAPP_KHO_CHITIET " + query;
							db.getConnection().rollback();
							return false;
						}


						query = "update NHAPP_KHO set AVAILABLE = AVAILABLE - '" + spSOLUONG[i].replaceAll(",", "") + "', BOOKED = BOOKED + '" + spSOLUONG[i].replaceAll(",", "") + "' " +
								"where KBH_FK = '" + this.kbhId + "' and SANPHAM_FK = '" + spId[i] + "' and KHO_FK = '" + this.khoNhanId + "' and NPP_FK = '" + this.nppId + "'  ";
						if(!db.update(query))
						{
							this.msg = "Không thể cập nhật NHAPP_KHO " + query;
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
		 */

		return true;
	}

	public boolean updateNK(HttpServletRequest request) 
	{
		if(this.ngaydenghi.trim().length() < 10)
		{
			this.msg = "Vui lòng nhập ngày duyệt";
			return false;
		}

		String[] spId = request.getParameterValues("spID");
		String[] spSOLO = request.getParameterValues("spSOLO");
		String[] spDUYET = request.getParameterValues("spDUYET");
		String[] spIDDUYET = request.getParameterValues("spIDDUYET");

		if(spId == null)
		{
			this.msg = "Vui lòng kiểm tra lại thông tin sản phẩm duyệt";
			return false;
		}
		else
		{
			boolean coSP = false;
			for(int i = 0; i < spId.length; i++ )
			{
				if(spDUYET[i].trim().length() > 0)
				{
					if(Double.parseDouble(spDUYET[i].trim().replaceAll(",", "")) > 0)
						coSP = true;
				}

			}

			if(!coSP)
			{
				this.msg = "Không có sản phẩm nào được nhập số lượng / số tiền duyệt";
				return false;
			}
		}

		try
		{
			db.getConnection().setAutoCommit(false);

			String query = " update ERP_DeNghiDoiHang set ngayduyet = N'" + this.ngaydenghi + "', ghichu = N'" + this.ghichu + "', " +
					"	ngaysua = '" + this.getDateTime() + "', nguoisua = '" + this.userId + "' " +
					" where pk_seq = '" + this.id + "' ";
			System.out.println("1.update DDH: " + query);
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_DeNghiDoiHang " + query;
				db.getConnection().rollback();
				return false;
			}

			if( spId != null )
			{
				for(int i = 0; i < spId.length; i++ )
				{
					if(spDUYET[i].trim().replaceAll(",", "").length() > 0)
					{
						String sp_IDDUYET = "NULL";
						if(spIDDUYET[i].trim().replaceAll(",", "").length() > 0)
							sp_IDDUYET = spIDDUYET[i].trim().replaceAll(",", "");

						query = "Update ERP_DENGHIDOIHANG_SANPHAM set DUYET = '" + spDUYET[i].trim().replaceAll(",", "") + "', " +
								"				SANPHAM_DUYET_FK = '" + sp_IDDUYET + "' " +
								"where denghidoihang_fk = '" + this.id + "' and sanpham_fk = '" + spId[i] + "' ";

						System.out.println("--UPDATE SP: " + query);
						if(!db.update(query))
						{
							this.msg = "Không thể cập nhật ERP_DENGHIDOIHANG_SANPHAM " + query;
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


	public void createRs() 
	{
		this.khoNhanRs = db.get("select PK_SEQ, TEN from KHO where trangthai = '1' and loaikho = '1' ");
		this.khottDoiRs = db.get("select PK_SEQ, TEN from ERP_KHOTT where trangthai = '1' and loaikho = '1' ");

		this.dvkdRs = db.get("select PK_SEQ, DONVIKINHDOANH as TEN from DONVIKINHDOANH where TRANGTHAI = '1' ");
		this.kbhRs = db.get("select PK_SEQ, DIENGIAI as TEN from KENHBANHANG where TRANGTHAI = '1' and PK_SEQ in ( select KBH_FK from NHAPP_KBH where NPP_FK = '" + this.nppId + "' ) ");

		String query = "select PK_SEQ, MA + ' - ' + TEN as TEN from NHAPHANPHOI where TRANGTHAI = '1'  ";
		this.nppRs = db.get(query);

		this.sanphamDOIRs = db.getScrol("select pk_seq, ten from SANPHAM where trangthai = '1' ");

		/*if(this.nppId.trim().length() > 0)
		{
			//INIT SAN PHAM
			query = " select a.PK_SEQ, a.MA, a.TEN, b.DONVI, BGIA.giamua as donGIA   " +
					" from SANPHAM a inner join DONVIDOLUONG b on a.DVDL_FK = b.PK_SEQ   " +
					" 	 left join   " +
					" 	 (  	 " +
					" 		 select a.PK_SEQ, a.DVDL_FK as dvCHUAN,  isnull( (  select GIAMUA_SAUCK  " +
					" 															from BGMUANPP_SANPHAM    					  " +
					" 															where SANPHAM_FK = a.pk_seq    and BGMUANPP_FK in ( select top(1) PK_SEQ from BANGGIAMUANPP bg inner join BANGGIAMUANPP_NPP bg_npp on bg.PK_SEQ = bg_npp.BANGGIAMUANPP_FK  where bg.TRANGTHAI = '1' and bg_npp.NPP_FK = '" + this.nppId + "' and bg.DVKD_FK = '" + this.dvkdId + "' and bg.KENH_FK = '" + this.kbhId + "'  order by bg.TUNGAY desc ) ), 0) as giamua    	 " +
					" 		 from SANPHAM a   " +
					" 	 )   " +
					" 	 BGIA on a.PK_SEQ = BGIA.PK_SEQ  " +
					" where a.pk_seq in ( select sanpham_fk from ERP_DENGHIDOIHANG_SANPHAM where denghidoihang_fk = '" + this.id + "' )  ";

			System.out.println("--INIT SAN PHAM: " + query);
			this.sanphamRs = db.get(query);

		}*/

	}

	public void init() 
	{
		String query = "select ngaydenghi, ISNULL(ghichu, '') as ghichu, dvkd_fk, kbh_fk, npp_fk, kho_fk, chietkhau, vat " +
				"from ERP_Denghidoihang where pk_seq = '" + this.id + "'";
		System.out.println("____INIT DNDH: " + query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				if(rs.next())
				{
					this.ngaydenghi = rs.getString("ngaydenghi");
					this.ghichu = rs.getString("ghichu");
					this.dvkdId = rs.getString("dvkd_fk");
					this.kbhId = rs.getString("kbh_fk");
					this.nppId = rs.getString("npp_fk");
					this.khoNhanId = rs.getString("kho_fk");
					this.chietkhau = rs.getString("chietkhau");
					this.vat = rs.getString("vat");
				}
				rs.close();
			} 
			catch (Exception e) 
			{
				System.out.println("---LOI INIT: " + e.getMessage());
			}

			//INIT SO LUONG DAT
			query = "select b.pk_seq, b.MA, b.TEN, ( select DONVI from DONVIDOLUONG where pk_seq = b.DVDL_FK ) as donvi, a.denghi, a.solo, " +
					"	isnull(a.duyet, 0) as duyet, isnull(SANPHAM_DUYET_FK, a.sanpham_fk) as SANPHAM_DUYET_FK  " +
					"from ERP_DeNghiDoiHang_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ   " +
					"	inner join ERP_DeNghiDoiHang c on a.denghidoihang_fk= c.pk_seq  " +
					"where a.denghidoihang_fk = '" + this.id + "' and denghi != 0 ";

			this.sanphamRs = db.get(query);

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


	public String getLoaidonhang() {

		return this.loaidonhang;
	}


	public void setLoaidonhang(String loaidonhang) {

		this.loaidonhang = loaidonhang;
	}


	public String getChietkhau() {

		return this.chietkhau;
	}


	public void setChietkhau(String chietkhau) {

		this.chietkhau = chietkhau;
	}


	public String getVat() {

		return this.vat;
	}


	public void setVat(String vat) {

		this.vat = vat;
	}


	public String getDvkdId() {

		return this.dvkdId;
	}


	public void setDvkdId(String dvkdId) {

		this.dvkdId = dvkdId;
	}


	public ResultSet getDvkdRs() {

		return this.dvkdRs;
	}


	public void setDvkdRs(ResultSet dvkdRs) {

		this.dvkdRs = dvkdRs;
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

	public String getNgaydenghi() {

		return this.ngaydenghi;
	}

	public void setNgaydenghi(String ngaydenghi) {

		this.ngaydenghi = ngaydenghi;
	}


	public ResultSet getSanphamRs() {

		return this.sanphamRs;
	}


	public void setSanphamRs(ResultSet spRs) {

		this.sanphamRs = spRs;
	}


	public ResultSet getSanphamDoiRs() {

		return this.sanphamDOIRs;
	}


	public void setSanphamDoiRs(ResultSet spDoiRs) {

		this.sanphamDOIRs = spDoiRs;
	}


	public boolean duyetDH(HttpServletRequest request)
	{
		if(this.ngaydenghi.trim().length() < 10)
		{
			this.msg = "Vui lòng nhập ngày duyệt";
			return false;
		}

		String[] spId = request.getParameterValues("spID");
		String[] spSOLO = request.getParameterValues("spSOLO");
		String[] spSOLUONG = request.getParameterValues("spSOLUONG");
		String[] spDUYET = request.getParameterValues("spDUYET");
		String[] spIDDUYET = request.getParameterValues("spIDDUYET");

		if(spId == null)
		{
			this.msg = "Vui lòng kiểm tra lại thông tin sản phẩm duyệt";
			return false;
		}
		else
		{
			boolean coSP = false;
			for(int i = 0; i < spId.length; i++ )
			{
				if(spDUYET[i].trim().length() > 0)
				{
					if(Double.parseDouble(spDUYET[i].trim().replaceAll(",", "")) > 0 && spIDDUYET[i].trim().length()>0)
						coSP = true;
				}
			}

			if(!coSP)
			{
				this.msg = "Xin nhập đầy đủ sản phẩm đổi và số lượng được duyệt";
				return false;
			}
		}

		db_Sync SYNC = new db_Sync(); 
		try
		{

			db.getConnection().setAutoCommit(false);
			SYNC.getConnection().setAutoCommit(false);
			String query = " update ERP_DeNghiDoiHang set trangthai = '2', ngayduyet = N'" + this.ngaydenghi + "', ghichu = N'" + this.ghichu + "', " +
					"	ngaysua = '" + this.getDateTime() + "', nguoisua = '" + this.userId + "' " +
					" where pk_seq = '" + this.id + "' ";
			System.out.println("1.update DDH: " + query);
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_DeNghiDoiHang " + query;
				db.getConnection().rollback();
				return false;
			}

			if( spId != null )
			{
				for(int i = 0; i < spId.length; i++ )
				{
					if(spDUYET[i].trim().replaceAll(",", "").length() > 0)
					{
						String sp_IDDUYET = "NULL";
						if(spIDDUYET[i].trim().replaceAll(",", "").length() > 0)
							sp_IDDUYET = spIDDUYET[i].trim().replaceAll(",", "");

						query = "Update ERP_DENGHIDOIHANG_SANPHAM set DUYET = '" + spDUYET[i].trim().replaceAll(",", "") + "', " +
								"				SANPHAM_DUYET_FK = '" + sp_IDDUYET + "' " +
								"where denghidoihang_fk = '" + this.id + "' and sanpham_fk = '" + spId[i] + "' ";

						System.out.println("--UPDATE SP: " + query);
						if(!db.update(query))
						{
							this.msg = "Không thể cập nhật ERP_DENGHIDOIHANG_SANPHAM " + query;
							db.getConnection().rollback();
							return false;
						}
					}
				}
			}

			//TU DONG TAO RA 1 DON DAT HANG KHAC
			query = "select count(*) as soDONG " +
					"from ERP_DENGHIDOIHANG_SANPHAM where denghidoihang_fk = '" + this.id + "' and ISNULL(DUYET, 0) != 0 and SANPHAM_DUYET_FK is not null ";
			ResultSet rsCHECK = db.get(query);
			int soDONG = 0;
			if(rsCHECK.next())
			{
				soDONG = rsCHECK.getInt("soDONG");
			}
			rsCHECK.close();
			String DonDatHangId=null;
			if(soDONG > 0)
			{
				query = " insert ERP_DonDatHang(ngaydonhang, ngaydenghi, loaidonhang, ghichu, trangthai, dvkd_fk, kbh_fk, npp_fk, kho_fk, chietkhau, vat, ngaytao, nguoitao, ngaysua, nguoisua, NOTE,DonDoiHang_FK) " +
						" select ngaydenghi, ngaydenghi, 0, ghichu, 0 as trangthai, dvkd_fk, kbh_fk, npp_fk, (select KHOSAP from NHAPHANPHOI where PK_SEQ = a.NPP_FK) as KHO_FK, chietkhau, vat, ngaytao, nguoitao, ngaysua, nguoisua, N'Convert từ duyệt đổi hàng số  " + this.id + "','"+this.id+"'  " +
						" from ERP_DENGHIDOIHANG a where pk_seq = '" + this.id + "' ";

				System.out.println("1.Insert DDH: " + query);
				if(!db.update(query))
				{
					msg = "Không thể tạo mới ERP_DeNghiDatHang " + query;
					db.getConnection().rollback();
					return false;
				} 
				ResultSet rsDDH = db.get("select Scope_identity() as ID ");
				if(rsDDH.next())
				{
					DonDatHangId= rsDDH.getString("ID");
				}
				rsDDH.close();


				query = "insert ERP_DONDATHANG_SANPHAM(dondathang_fk, sanpham_fk, soluong,soluongttduyet, dongia, dvdl_fk,DonGiaGoc)  " +
						"select "+DonDatHangId+", sanpham_duyet_fk, duyet,duyet, dongia, dvdl_fk,dongia   " +
						"from ERP_DENGHIDOIHANG_SANPHAM  " +
						"where denghidoihang_fk = '" + this.id + "' and ISNULL(DUYET, 0) != 0 and SANPHAM_DUYET_FK is not null ";
				System.out.println("1.Insert DDH - SP: " + query);
				if(!db.update(query))
				{
					msg = "Không thể tạo mới ERP_DeNghiDatHang " + query;
					db.getConnection().rollback();
					return false;
				}
			}

			query= " insert into  DMS_Header(LoaiDonHang,DeNghiDoiHang_FK,UserId)"+
					" select 	'ZSDR','"+this.id+"','"+userId+"' ";
			System.out.println("[DMS_Header]"+ query);
			if(db.updateReturnInt(query)!=1)
			{
				msg = "Không thể đẩy đơn hàng sang SAP!Vui lòng kiểm tra lại kết nối !";
				db.getConnection().rollback();
				return false;
			}

			String HeaderID=null;
			ResultSet rsDDH = db.get("select Scope_identity() as ID ");
			if(rsDDH.next())
			{
				HeaderID= rsDDH.getString("ID");
			}
			rsDDH.close();

			query=
					"		select 'ZSDR' as LoaiDonHang ,  "+
							"				'1000'  as ToChucBanHang,C.SMARTID as KenhBanHang,00 AS NganhHang, "+
							"				b.MA as NguoiMuaHang,b.MA as NguoiNhanGiaoHang,b.MA as NguoiTraTien,'2'+cast(a.PK_SEQ as varchar(20)) as SoDonHang, "+
							"					A.NgayDeNghi  as NgayTaoDonHang, "+
							"			'VND' as LoaiTienTe,1 as TyGia, A.NGAYDUYET as NgayChungTu, "+
							"			A.NgayDeNghi as NgayXacDinhGia,NULL AS HanThanhToan,a.GhiChu as LyDoTraHang,NULL as NgayHoaDonTraHang, "+
							"				'2'+cast(a.PK_SEQ as varchar(20))  as HeaderID, CAST(a.pk_Seq as varchar(20)) +'__' + CAST(a.NPP_FK as varchar(20))  +'__'+ b.MA  + '__' + "+ 
							"				b.TEN + '__'+ a.NgayDeNghi  as FullDesc "+
							"		from ERP_DENGHIDOIHANG a "+
							"		inner join NHAPHANPHOI b on b.PK_SEQ=a.NPP_FK "+
							"     inner join KenhBanHang c on c.pk_Seq=a.KBH_FK 	"+
							"		where a.TRANGTHAI=2 and a.pk_seq='"+this.id+"' ";
			ResultSet	rs = db.get(query);
			System.out.println("[LoaiDonHang]"+ query );
			int  SoDong =0;
			while(rs.next())
			{

				String LoaiDonHang= rs.getString("LoaiDonHang");
				String ToChucBanHang= rs.getString("ToChucBanHang");
				String KenhBanHang= rs.getString("KenhBanHang");
				String NganhHang= rs.getString("NganhHang");
				String NguoiMuaHang= rs.getString("NguoiMuaHang");
				String NguoiNhanGiaoHang= rs.getString("NguoiNhanGiaoHang");
				String NguoiTraTien= rs.getString("NguoiTraTien");
				String SoDonHang= rs.getString("SoDonHang");
				Date NgayTaoDonHang= rs.getDate("NgayTaoDonHang");
				String LoaiTienTe= rs.getString("LoaiTienTe");
				String TyGia= rs.getString("TyGia");
				Date NgayChungTu= rs.getDate("NgayChungTu");
				Date NgayXacDinhGia= rs.getDate("NgayXacDinhGia");
				Date HanThanhToan= rs.getDate("HanThanhToan");
				String LyDoTraHang= rs.getString("LyDoTraHang");
				Date NgayHoaDonTraHang= rs.getDate("NgayHoaDonTraHang");
				String FullDesc= rs.getString("FullDesc");

				query=
						" select  '"+LoaiDonHang+"', '"+ToChucBanHang+"' ,'"+KenhBanHang+"','"+NganhHang+"' ,'"+NguoiMuaHang+"'," +
								"  '"+NguoiNhanGiaoHang+"',  "+
								"				'"+NguoiTraTien+"','"+SoDonHang+"', cast('"+NgayTaoDonHang+"' as DATE),'"+LoaiTienTe+"','"+TyGia+"',cast('"+NgayChungTu+"'  as DATE) ,  "+
								" 		cast('"+NgayXacDinhGia+"' as DATE)		,'"+HanThanhToan+"',N'"+LyDoTraHang+"',cast('"+NgayHoaDonTraHang+"' as DATE) ,'"+HeaderID+"','"+FullDesc+"'   ";
				System.out.println("[DMS_DonHang_Header]"+ query );
				query=
						"		insert into DMS_DonHang_Header(LoaiDonHang,ToChucBanHang,KenhBanHang,NganhHang,NguoiMuaHang,NguoiNhanGiaoHang,  "+
								"				NguoiTraTien,SoDonHang,NgayTaoDonHang,LoaiTienTe,TyGia,NgayChungTu,  "+
								" 				NgayXacDinhGia,HanThanhToan,LyDoTraHang,NgayHoaDonTraHang,HeaderID,FullDesc)  " +
								" select ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?								" ;
				SoDong=0;	
				SoDong=	SYNC.executeUpdate(query, LoaiDonHang,ToChucBanHang,KenhBanHang,NganhHang,NguoiMuaHang,NguoiNhanGiaoHang,  
						NguoiTraTien,SoDonHang,NgayTaoDonHang,LoaiTienTe,TyGia,NgayChungTu,  
						NgayXacDinhGia,HanThanhToan,LyDoTraHang,NgayHoaDonTraHang,HeaderID,FullDesc);
				if(SoDong!=1)
				{
					msg = "Không thể đẩy đơn hàng sang SAP!Vui lòng kiểm tra lại kết nối !"+query;
					SYNC.getConnection().rollback();
					db.getConnection().rollback();
					return false;
				}
			}
			query=
					"	select  "+HeaderID+"  as HeaderID ,'2'+ CAST(a.denghidoihang_fk AS VARCHAR(20)),'2'+ CAST(a.denghidoihang_fk AS VARCHAR(20)) as SoDonHang,  "+
							"		ROW_NUMBER() OVER(ORDER BY A.denghidoihang_fk DESC) AS 'stt' "+
							"		,b.MA as Material , d.DONVI as DonViBanHang,a.DUYET as SoLuong ,NULL as NgayGiaoHang ,NULL as IO_CTTangHang,NULL as IO_CTCKTien,NULL as ptChietKhau "+
							"	from ERP_DENGHIDOIHANG_SANPHAM a  "+
							"		left join sanpham b on b.pk_Seq=a.sanpham_fk "+	
							"		left join ERP_DENGHIDOIHANG c on c.PK_SEQ=a.denghidoihang_fk " +
							"		left join DONVIDOLUONG d on d.PK_SEQ=b.DVDL_FK "+
							"	where c.TRANGTHAI=2 and c.pk_seq='"+this.id+"' ";

			rs = db.get(query);
			int PoLine=0;
			while(rs.next())
			{
				PoLine++;
				HeaderID= rs.getString("HeaderID");
				String SoDonHang= rs.getString("SoDonHang");
				String Material= rs.getString("Material");
				String DonViBanHang= rs.getString("DonViBanHang");
				double SoLuong =rs.getDouble("SoLuong");
				String NgayGiaoHang =rs.getString("NgayGiaoHang");
				String IO_CTTangHang = rs.getString("IO_CTTangHang");
				String	IO_CTCKTien = rs.getString("IO_CTCKTien");
				double ptChietKhau =rs.getDouble("ptChietKhau");

				query=
						"insert into DMS_DonHang_Item(HeaderID,SoDonHang,PoLine,Material,DonViBanHang,SoLuong,NgayGiaoHang,IO_CTTangHang,IO_CTCKTien,ptChietKhau)" +
								" select  ? ,? , ?, ? ,?    ,?, ? ,? ,? ,? ";

				SoDong=0;	
				SoDong=	SYNC.executeUpdate(query, HeaderID,SoDonHang,PoLine,Material,DonViBanHang,SoLuong,NgayGiaoHang,IO_CTTangHang,IO_CTCKTien,ptChietKhau);
				System.out.println("[DMS_DonHang_Item]"+ query);
				if(SoDong!=1)
				{
					this.msg = "Lỗi chèn thông tin chi tiết đơn hàng !"+query;
					SYNC.getConnection().rollback();
					db.getConnection().rollback();
					return false;
				}
			}
			rs.close();

			msg=
					DonDatHang(DonDatHangId,this.userId,db,SYNC);
			System.out.println("[msg DonDatHang]"+msg);
			if(msg.length()>0)
			{
				SYNC.getConnection().rollback();
				db.getConnection().rollback();
				return false;
			}

			//TANG KHO LOI CUA TRUNG TAM --> LAM BEN TREN

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);

			SYNC.getConnection().commit();
			SYNC.getConnection().setAutoCommit(true);

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


	public String getKhoTTDoiId() {

		return this.khottDoiId;
	}


	public void setKhoTTDoiId(String khottDoiId) {

		this.khottDoiId = khottDoiId;
	}


	public ResultSet getKhoTTDoiRs() {

		return this.khottDoiRs;
	}


	public void setKhoTTDoiRs(ResultSet khottDoiRs) {

		this.khottDoiRs = khottDoiRs;
	}


	private String DonDatHang(String lsxId,String userId ,dbutils db ,db_Sync SYNC) 
	{
		String msg = "";
		try
		{
			String query = "update ERP_Dondathang set trangthai = '1' where pk_seq = '" + lsxId + "' and TrangThai=0 ";
			System.out.println("[ERP_Dondathang]"+ query);
			if(db.updateReturnInt(query)!=1)
			{
				msg = "Đơn hàng đã chốt rồi : " + query;
				return msg;
			}

			//	CK %,thể hiện ptCK đối với sản phẩm thuộc điều kiện KM
			query=
					"	select * from ERP_DONDATHANG_CTKM_TRAKM   KM where DONDATHANGID='" + lsxId + "' AND SPMA IS NULL  "+
							"			AND TRAKMID IN  "+ 
							"			(  "+
							"				SELECT PK_SEQ FROM TRAKHUYENMAI WHERE LOAI=2 AND PK_SEQ=KM.TRAKMID  "+
							"			)  ";

			ResultSet rs=db.get(query);
			while(rs.next())
			{
				String ctkmId=rs.getString("CTKMID");
				query=
						"	update  Erp_DonDatHang_SanPham set "+ 
								"			IO= "+
								"			( "+
								"				select a.IO "+
								"				from CTKHUYENMAI a inner join CTKM_TRAKM b on b.CTKHUYENMAI_FK=a.PK_SEQ "+	
								"					inner join TRAKHUYENMAI c on c.PK_SEQ=b.TRAKHUYENMAI_FK "+
								"				where a.PK_SEQ='"+ctkmId+"'  and c.LOAI=2 "+
								"			) "+
								"			ptCK=( "+
								"					select c.CHIETKHAU "+
								"					from CTKHUYENMAI a inner join CTKM_TRAKM b on b.CTKHUYENMAI_FK=a.PK_SEQ "+	
								"						inner join TRAKHUYENMAI c on c.PK_SEQ=b.TRAKHUYENMAI_FK "+
								"					where a.PK_SEQ='"+ctkmId+"'  and c.LOAI=2 "+
								"				) "+
								"			CTKM_FK= "+
								"						( "+
								"							select a.PK_SEQ "+
								"							from CTKHUYENMAI a inner join CTKM_TRAKM b on b.CTKHUYENMAI_FK=a.PK_SEQ "+	
								"								inner join TRAKHUYENMAI c on c.PK_SEQ=b.TRAKHUYENMAI_FK "+
								"							where a.PK_SEQ='"+ctkmId+"'  and c.LOAI=2 "+
								"						) "+
								"						where sanpham_fk "+  
								"						in  "+
								"						( "+
								"							select d.SANPHAM_FK "+
								"							from CTKHUYENMAI a inner join CTKM_DKKM b on b.CTKHUYENMAI_FK=a.PK_SEQ "+	
								"								inner join DIEUKIENKHUYENMAI c on c.PK_SEQ=b.DKKHUYENMAI_FK "+
								"								inner join DIEUKIENKM_SANPHAM d on d.DIEUKIENKHUYENMAI_FK=c.PK_SEQ "+
								"							where a.PK_SEQ='"+ctkmId+"'  "+
								"						)and dondathang_fk='"+lsxId+"'  ";

				if(!db.update(query))
				{
					msg = "Lỗi khi cập nhật  : " + query;
					return msg;
				}
			}


			query=
					" 	select SUM(TongGiaTri) as tienCK   , CTKMID  from ERP_DONDATHANG_CTKM_TRAKM   KM where DONDATHANGID='" + lsxId + "' AND SPMA IS NULL  "+
							"			AND TRAKMID IN  "+ 
							"			(  "+
							"				SELECT PK_SEQ FROM TRAKHUYENMAI WHERE LOAI=1 AND PK_SEQ=KM.TRAKMID  "+
							"			)  " +
							" GROUP BY CTKMID             ";

			rs=db.get(query);
			while(rs.next())
			{
				String ctkmId=rs.getString("CTKMID");
				query=
						"	update  Erp_DonDatHang_SanPham set "+ 
								"			IO= "+
								"			( "+
								"				select a.IO "+
								"				from CTKHUYENMAI a inner join CTKM_TRAKM b on b.CTKHUYENMAI_FK=a.PK_SEQ "+	
								"					inner join TRAKHUYENMAI c on c.PK_SEQ=b.TRAKHUYENMAI_FK "+
								"				where a.PK_SEQ='"+ctkmId+"'  and c.LOAI=2 "+
								"			) "+
								"			ptCK=( "+
								"					select c.CHIETKHAU "+
								"					from CTKHUYENMAI a inner join CTKM_TRAKM b on b.CTKHUYENMAI_FK=a.PK_SEQ "+	
								"						inner join TRAKHUYENMAI c on c.PK_SEQ=b.TRAKHUYENMAI_FK "+
								"					where a.PK_SEQ='"+ctkmId+"'  and c.LOAI=2 "+
								"				) "+
								"			CTKM_FK= "+
								"						( "+
								"							select a.PK_SEQ "+
								"							from CTKHUYENMAI a inner join CTKM_TRAKM b on b.CTKHUYENMAI_FK=a.PK_SEQ "+	
								"								inner join TRAKHUYENMAI c on c.PK_SEQ=b.TRAKHUYENMAI_FK "+
								"							where a.PK_SEQ='"+ctkmId+"'  and c.LOAI=2 "+
								"						) "+
								"						where sanpham_fk "+  
								"						in  "+
								"						( "+
								"							select d.SANPHAM_FK "+
								"							from CTKHUYENMAI a inner join CTKM_DKKM b on b.CTKHUYENMAI_FK=a.PK_SEQ "+	
								"								inner join DIEUKIENKHUYENMAI c on c.PK_SEQ=b.DKKHUYENMAI_FK "+
								"								inner join DIEUKIENKM_SANPHAM d on d.DIEUKIENKHUYENMAI_FK=c.PK_SEQ "+
								"							where a.PK_SEQ='"+ctkmId+"'  "+
								"						)and dondathang_fk='"+lsxId+"'  ";

				if(!db.update(query))
				{
					msg = "Lỗi khi cập nhật  : " + query;
					return msg;
				}
			}


			query= " insert into  DMS_Header(LoaiDonHang,DonDatHang_FK,UserId)"+
					" select 	'ZSDH','"+lsxId+"','"+userId+"' ";
			System.out.println("[DMS_Header]"+ query);
			if(db.updateReturnInt(query)!=1)
			{
				msg = "Không thể đẩy đơn hàng sang SAP!Vui lòng kiểm tra lại kết nối !";
				return msg;
			}

			String HeaderID=null;
			ResultSet rsDDH = db.get("select Scope_identity() as ID ");
			if(rsDDH.next())
			{
				HeaderID= rsDDH.getString("ID");
			}
			rsDDH.close();


			query=
					"select 'ZSDH'  as LoaiDonHang ,'1000'  as ToChucBanHang,C.SMARTID as KenhBanHang,00 AS NganhHang,  "+
							"	b.MA as NguoiMuaHang,b.MA as NguoiNhanGiaoHang,b.MA as NguoiTraTien,a.PK_SEQ as SoDonHang,A.NGAYTAO as NgayTaoDonHang, "+
							"	'VND' as LoaiTienTe,1 as TyGia,A.NgayDonHang as NgayChungTu,  "+
							"	A.NgayDonHang as NgayXacDinhGia,NULL AS HanThanhToan,NULL as LyDoTraHang,NULL as NgayHoaDonTraHang,  "+
							"		'"+HeaderID+"'  as HeaderID, CAST(a.pk_Seq as varchar(20)) +'__' + CAST(a.NPP_FK as varchar(20))  +'__'+ b.MA  + '__' + b.TEN + '__'+ a.NgayDonHang  as FullDesc "+
							" from  ERP_DONDATHANG a inner join NHAPHANPHOI b on b.PK_SEQ=a.NPP_FK "+
							"     inner join KenhBanHang c on c.pk_Seq=a.KBH_FK 	"+
							" where a.TRANGTHAI=1 and LoaiDonHang=0 and a.pk_seq='"+lsxId+"' ";
			rs = db.get(query);

			System.out.println("[LoaiDonHang]"+ query );
			int  SoDong =0;
			while(rs.next())
			{

				String LoaiDonHang= rs.getString("LoaiDonHang");
				String ToChucBanHang= rs.getString("ToChucBanHang");
				String KenhBanHang= rs.getString("KenhBanHang");
				String NganhHang= rs.getString("NganhHang");
				String NguoiMuaHang= rs.getString("NguoiMuaHang");
				String NguoiNhanGiaoHang= rs.getString("NguoiNhanGiaoHang");
				String NguoiTraTien= rs.getString("NguoiTraTien");
				String SoDonHang= rs.getString("SoDonHang");
				Date NgayTaoDonHang= rs.getDate("NgayTaoDonHang");
				String LoaiTienTe= rs.getString("LoaiTienTe");
				String TyGia= rs.getString("TyGia");
				Date NgayChungTu= rs.getDate("NgayChungTu");
				Date NgayXacDinhGia= rs.getDate("NgayXacDinhGia");
				Date HanThanhToan= rs.getDate("HanThanhToan");
				String LyDoTraHang= rs.getString("LyDoTraHang");
				Date NgayHoaDonTraHang= rs.getDate("NgayHoaDonTraHang");
				String FullDesc= rs.getString("FullDesc");

				query=
						" select  '"+LoaiDonHang+"', '"+ToChucBanHang+"' ,'"+KenhBanHang+"','"+NganhHang+"' ,'"+NguoiMuaHang+"'," +
								"  '"+NguoiNhanGiaoHang+"',  "+
								"				'"+NguoiTraTien+"','"+SoDonHang+"', cast('"+NgayTaoDonHang+"' as DATE),'"+LoaiTienTe+"','"+TyGia+"',cast('"+NgayChungTu+"'  as DATE) ,  "+
								" 		cast('"+NgayXacDinhGia+"' as DATE)		,'"+HanThanhToan+"',N'"+LyDoTraHang+"',cast('"+NgayHoaDonTraHang+"' as DATE) ,'"+HeaderID+"','"+FullDesc+"'   ";
				System.out.println("[DMS_DonHang_Header]"+ query );
				query=
						"		insert into DMS_DonHang_Header(LoaiDonHang,ToChucBanHang,KenhBanHang,NganhHang,NguoiMuaHang,NguoiNhanGiaoHang,  "+
								"				NguoiTraTien,SoDonHang,NgayTaoDonHang,LoaiTienTe,TyGia,NgayChungTu,  "+
								" 				NgayXacDinhGia,HanThanhToan,LyDoTraHang,NgayHoaDonTraHang,HeaderID,FullDesc)  " +
								" select ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?								" ;
				SoDong=0;	
				//SoDong =SYNC.updateReturnInt(query);
				SoDong=	SYNC.executeUpdate(query, LoaiDonHang,ToChucBanHang,KenhBanHang,NganhHang,NguoiMuaHang,NguoiNhanGiaoHang,  
						NguoiTraTien,SoDonHang,NgayTaoDonHang,LoaiTienTe,TyGia,NgayChungTu,  
						NgayXacDinhGia,HanThanhToan,LyDoTraHang,NgayHoaDonTraHang,HeaderID,FullDesc);
				if(SoDong!=1)
				{
					msg = "Không thể đẩy đơn hàng sang SAP!Vui lòng kiểm tra lại kết nối !"+query;
					return msg;
				}
			}
			rs.close();

			query=
					"	select "+HeaderID+" as HeaderID,a.dondathang_fk as SoDonHang ,  "+
							"			a.SoTT+1 as PoLine,b.MA as Material, d.DONVI as DonViBanHang,a.soluongttduyet as SoLuong,NULL as NgayGiaoHang,  "+
							"			NULL as IO_CTTangHang,NULL as IO_CTCKTien,NULL as ptChietKhau  "+
							"	from dbo.Erp_DonDatHang_SanPham a inner join dbo.sanpham b on b.pk_Seq=a.sanpham_fk  "+	
							"		inner join dbo.ERP_DONDATHANG c on c.PK_SEQ=a.dondathang_fk  "+
							"			inner join dbo.DONVIDOLUONG d on d.PK_SEQ=a.dvdl_fk  "+
							"	where c.TRANGTHAI=1 and c.pk_Seq='"+lsxId+"' "+
							"	union all  "+
							"	select  "+
							"			"+HeaderID+" as HeaderID,a.DonDatHangID as SoDonHang , "+
							"			isnull((select count(*) From Erp_DonDatHang_SanPham dhsp where dhsp.dondathang_fk=a.pk_Seq),0) + " +
							"    	ROW_NUMBER() OVER(ORDER BY a.DonDatHangID DESC)																							 " +
							"  as PoLine,b.MA as Material, d.DONVI as DonViBanHang,a.SoLuong as SoLuong,NULL as NgayGiaoHang,  "+
							"			E.IO as IO_CTTangHang,NULL as IO_CTCKTien,NULL as ptChietKhau  "+
							"	from dbo.Erp_DonDatHang_CTKM_TRAKM a inner join dbo.sanpham b on b.ma=a.spMa  "+
							"		inner join dbo.ERP_DONDATHANG c on c.PK_SEQ=a.DonDatHangID  "+
							"			inner join dbo.DONVIDOLUONG d on d.PK_SEQ=b.dvdl_Fk  "+
							"		inner join CTKHUYENMAI e on e.pk_Seq=a.CTKMID  "+
							"	where c.TRANGTHAI=1 and c.pk_Seq='"+lsxId+"' ";
			System.out.println("[DMS_DonHang_Header]"+ query);
			rs = db.get(query);
			int PoLine=0;
			while(rs.next())
			{
				PoLine++;
				HeaderID= rs.getString("HeaderID");
				String SoDonHang= rs.getString("SoDonHang");
				String Material= rs.getString("Material");
				String DonViBanHang= rs.getString("DonViBanHang");
				double SoLuong =rs.getDouble("SoLuong");
				String NgayGiaoHang =rs.getString("NgayGiaoHang");
				String IO_CTTangHang = rs.getString("IO_CTTangHang");
				String	IO_CTCKTien = rs.getString("IO_CTCKTien");
				double ptChietKhau =rs.getDouble("ptChietKhau");

				query=
						"insert into DMS_DonHang_Item(HeaderID,SoDonHang,PoLine,Material,DonViBanHang,SoLuong,NgayGiaoHang,IO_CTTangHang,IO_CTCKTien,ptChietKhau)" +
								" select  ? ,? , ?, ? ,?    ,?, ? ,? ,? ,? ";

				SoDong=0;	
				SoDong=	SYNC.executeUpdate(query, HeaderID,SoDonHang,PoLine,Material,DonViBanHang,SoLuong,NgayGiaoHang,IO_CTTangHang,IO_CTCKTien,ptChietKhau);
				System.out.println("[DMS_DonHang_Item]"+ query);
				if(SoDong!=1)
				{
					msg = "Lỗi chèn thông tin chi tiết đơn hàng !"+query;
					return msg;
				}
			}
			rs.close();
			query = "update ERP_Dondathang set SendingDate=dbo.GetLocalDate(DEFAULT),Header_FK='"+HeaderID+"',TrangThai=2 where pk_seq = '" + lsxId + "' ";
			System.out.println("[ERP_Dondathang]"+ query);
			if(!db.update(query))
			{
				msg = "Lỗi cập nhật thông tin đơn hàng " + query;
				return msg;
			}
		}
		catch (Exception e) 
		{
			try
			{
				SYNC.getConnection().rollback();
			} catch (SQLException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try
			{
				db.getConnection().rollback();
			} catch (SQLException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
			return "Exception: " + e.getMessage();
		}
		return "";
	}



}
