package geso.dms.distributor.beans.Nhaphangnpp.imp;

import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;
import geso.dms.distributor.beans.Nhaphangnpp.INhaphangnpp;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Nhaphangnpp implements INhaphangnpp
{
	String userId;
	String id;

	String nppId;

	String ngayyeucau;
	String ngaynhan;
	String sochungtu;
	String ghichu;

	String msg;
	String trangthai;

	String ddhId;
	ResultSet ddhRs;


	String khonhanId;
	ResultSet khonhanRs;

	String[] spId;
	String[] spMa;
	String[] spTen;
	String[] spDonvi;
	String[] spSolo;
	String[] spTonkho;
	String[] spXuat;
	String[] spSoluong;
	String[] spDongia;
	String[] spLoai;
	String[] spSCheme;
	String[] spvat;
	String[] spchietkhau;
	public String[] getSpchietkhau()
	{
		return spchietkhau;
	}

	public void setSpchietkhau(String[] spchietkhau)
	{
		this.spchietkhau = spchietkhau;
	}

	public String[] getSpvat()
	{
		return spvat;
	}

	public void setSpvat(String[] spvat)
	{
		this.spvat = spvat;
	}
	dbutils db;

	public Nhaphangnpp()
	{
		this.id = "";
		this.ngayyeucau = getDateTime();
		this.ghichu = "";
		this.msg = "";
		this.trangthai = "0";
		this.ddhId = "";
		this.ngaynhan = "";
		this.sochungtu = "";
		this.khonhanId = "";
		this.loaiDh="";
		this.db = new dbutils();
	}

	public Nhaphangnpp(String id)
	{
		this.id = id;
		this.ngayyeucau = getDateTime();
		this.ghichu = "";
		this.msg = "";
		this.trangthai = "0";
		this.ddhId = "";
		this.ngaynhan = "";
		this.sochungtu = "";
		this.khonhanId = "";
		this.loaiDh="";
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
		String	query = "select PK_SEQ, CAST(pk_seq as varchar(10)) + ' / ' + NgayDonHang as ten " +
				"from ERP_DONDATHANG where TRANGTHAI in (2, 4) and PK_SEQ in ( select ddh_fk from NHAPKHO_DDH where NHAPKHO_fk = '" + this.id + "' )  ";
		this.ddhRs = db.get(query);
		Utility util = new Utility();
		if(this.id.trim().length() > 0 )
		{
			query = "declare @tb table  ";
			query+="( ";
			query+="sanpham_fk numeric(18,0), ";
			query += "dongia float,thuevat float,chietkhau float ";
			query+=") ";

			query+="declare @chuyenkho_fk int;";
			query += "set @chuyenkho_fk=(select CHUYENKHO_FK from NHAPKHO where PK_SEQ=" + this.id + ") ";

			query+="declare @ycxkfk int;";
			query += "set @ycxkfk=(select YCXK_FK  from NHAPKHO where PK_SEQ=" + this.id + ") ";

			query+="declare @ycxknppfk int;";
			query += "set @ycxknppfk=(select YCXKNPP_FK  from NHAPKHO where PK_SEQ=" + this.id + ") ";

			query+="declare @cknppfk int;";
			query += "set @cknppfk=(select CHUYENKHONPP_FK  from NHAPKHO where PK_SEQ=" + this.id + ") ";

			query += "if(@chuyenkho_fk is not null) ";
			query += "begin ";
			query += "insert into @tb(sanpham_fk, dongia,thuevat,chietkhau)";
			query += "select sanpham_fk,dongia,0,0 from ERP_CHUYENKHO_SANPHAM where chuyenkho_fk=@chuyenkho_fk  ";
			query += "end ";
			query += " if(@ycxkfk is not null) ";
			query += "begin ";
			query += "insert into @tb(sanpham_fk, dongia,thuevat,chietkhau) ";
			query += "    select sanpham_fk,dongia,thueVAT,chietkhau from ERP_DONDATHANG_SANPHAM where dondathang_fk in  ( select ddh_fk from ERP_YCXUATKHO_DDH where ycxk_fk=@ycxkfk) ";
			query += "	end ";
			query += "if(@ycxknppfk is not null) ";
			query += "begin ";
			query += "insert into @tb(sanpham_fk, dongia,thuevat,chietkhau) ";
			query += " select sanpham_fk,dongia,thueVAT,chietkhau from ERP_DONDATHANGNPP_SANPHAM where dondathang_fk in  ( select ddh_fk from ERP_YCXUATKHONPP_DDH where ycxk_fk=@ycxknppfk)";
			query += "end ";
			query += "if(@cknppfk is not null) ";
			query += "begin ";

			query += "insert into @tb(sanpham_fk, dongia,thuevat,chietkhau) ";
			query += "select sanpham_fk,dongia,0,0 from ERP_CHUYENKHONPP_SANPHAM where chuyenkho_fk =@cknppfk ";
			query += "end ";

			query += "select * from @tb ";

			query += "inner join ";
			query += "(select b.PK_SEQ, b.MA, b.TEN, c.DONVI, dongia, isnull(a.SOLO, 'NA') as SOLO, a.SOLUONG,";
			query += "ISNULL(soluongNHAN, a.SOLUONG) as soluongNHAN, isnull(a.loai, 0) as loai,";
			query += " isnull(a.SCHEME, '') as SCHEME,isnull(a.NgayHetHan,'') as spNGAYHETHAN  ";
			query += "from NHAPKHO_SP a inner join SANPHAM b on a.SANPHAM_FK = b.ma 		";
			query += "inner join DONVIDOLUONG c on b.DVDL_FK = c.PK_SEQ  where a.NHAPKHO_FK = '" + this.id + "') b on b.PK_SEQ = sanpham_fk  ";

			/*
			 * query =
			 * "  select b.PK_SEQ, b.MA, b.TEN, c.DONVI, isnull(a.DONGIA, 0) as DONGIA, isnull(a.SOLO, 'NA') as SOLO, a.SOLUONG, ISNULL(soluongNHAN, a.SOLUONG) as soluongNHAN, isnull(a.loai, 0) as loai, isnull(a.SCHEME, '') as SCHEME,isnull(a.NgayHetHan,'') as spNGAYHETHAN   "
			 * +
			 * "  froNHAPKHOngnpp_SP a inner join SANPHAM b on a.SANPHAM_FK = b.PK_SEQ "
			 * + "		inner join DONVIDOLUONG c on b.DVDL_FK = c.PK_SEQ " +
			 * "  where a.Nhaphangnpp_FK = '" + this.id + "' ";
			 */

			System.out.println("---INIT NK: " + query);
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
					String spDONGIA = "";
					String spVAT = "";
					String spchietkhau = "";
					String spSOLO = "";
					String spXUAT = "";
					String spSOLUONG = "";
					String spLOAI = "";
					String spSCHEME = "";
					String spNGAYHETHAN = "";

					while(spRs.next())
					{
						spID += spRs.getString("PK_SEQ") + "__";
						spMA += spRs.getString("MA") + "__";
						spTEN += spRs.getString("TEN") + "__";
						spDONVI += spRs.getString("DONVI") + "__";
						spDONGIA += spRs.getDouble("DONGIA") + "__";
						spXUAT += formater.format(spRs.getDouble("SOLUONG")) + "__";
						spSOLUONG += formater.format(spRs.getDouble("soluongNHAN")) + "__";
						spchietkhau += formater.format(spRs.getDouble("chietkhau")) + "__";
						spVAT += formater.format(spRs.getDouble("thuevat")) + "__";
						spLOAI += spRs.getString("LOAI") + "__";

						if(spRs.getString("SOLO").trim().length() > 0)
							spSOLO += spRs.getString("SOLO") + "__";
						else 
							spSOLO += " __";

						if(spRs.getString("spNGAYHETHAN").trim().length() > 0)
							spNGAYHETHAN += spRs.getString("spNGAYHETHAN") + "__";
						else 
							spNGAYHETHAN += " __";

						if(spRs.getString("SCHEME").trim().length() > 0)
							spSCHEME += spRs.getString("SCHEME") + "__";
						else
							spSCHEME += " __";

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

						spDONGIA = spDONGIA.substring(0, spDONGIA.length() - 2);
						this.spDongia = spDONGIA.split("__");

						spSOLO = spSOLO.substring(0, spSOLO.length() - 2);
						this.spSolo = spSOLO.split("__");

						spXUAT = spXUAT.substring(0, spXUAT.length() - 2);
						this.spXuat = spXUAT.split("__");

						spSOLUONG = spSOLUONG.substring(0, spSOLUONG.length() - 2);
						this.spSoluong = spSOLUONG.split("__");

						spLOAI = spLOAI.substring(0, spLOAI.length() - 2);
						this.spLoai = spLOAI.split("__");

						spSCHEME = spSCHEME.substring(0, spSCHEME.length() - 2);
						this.spSCheme = spSCHEME.split("__");

						spNGAYHETHAN = spNGAYHETHAN.substring(0, spNGAYHETHAN.length() - 2);
						this.spNgayHetHan = spNGAYHETHAN.split("__");

						spVAT = spVAT.substring(0, spVAT.length() - 2);
						this.spvat = spVAT.split("__");
						spchietkhau = spchietkhau.substring(0, spchietkhau.length() - 2);
						this.spchietkhau = spchietkhau.split("__");
					}

				} 
				catch (Exception e) {e.printStackTrace(); System.out.println("EXCEPTION SP: " + e.getMessage() ); }

			}

		}

		this.khonhanRs = db.get("select pk_seq, ten from KHO where pk_seq in " + util.quyen_kho(this.userId)+" order by pk_seq asc");


		this.getNppInfo();
	}

	private void getNppInfo()
	{		
		//Phien ban moi
		geso.dms.distributor.util.Utility util=new geso.dms.distributor.util.Utility();
		this.nppId=util.getIdNhapp(this.userId);
	}

	public void init() 
	{
		String query = 
				"select SoChungTu,ngaychungtu as ngayyeucau, isnull(ghichu,'') as ghichu, npp_fk, isnull(ngaynhan, '') as ngaynhan, trangthai, isnull(ycxk_fk, chuyenkho_fk) as ycxk_fk, isnull(kho_fk, 100000) as kho_fk " +
						"	,	(	select top(1) isnull(a.loaidonhang,b.LoaiDonHang) as loaidh  from ERP_DONDATHANG a inner join ERP_CHUYENKHO b on b.ddh_fk=a.PK_SEQ  "+
						"		where b.PK_SEQ=NHAPKHO.CHUYENKHO_FK and b.trangthai=1 "+
						" )as LoaiDH  "+
						"from NHAPKHO where pk_seq = '" + this.id + "'";
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
					this.ngaynhan = rs.getString("ngaynhan");
					String loaidh=rs.getString("loaidh")==null?"0":rs.getString("loaidh");
					if(loaidh.equals("4"))
						this.khonhanId="100001";
					else
						this.khonhanId = rs.getString("kho_fk");
					System.out.println("da vao day"+this.khonhanId);
					if(rs.getString("trangthai").equals("1"))
						this.sochungtu = rs.getString("SoChungTu");

					this.loaiDh=rs.getString("loaiDh")==null?"":rs.getString("loaiDh");

				}
				rs.close();

				//INIT DDH
				query = "select ddh_fk from NHAPKHO_DDH where NHAPKHO_fk = '" + this.id + "' ";
				rs = db.get(query);
				System.out.println("quer __"+query);
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

			} 
			catch (Exception e) 
			{
				System.out.println("---LOI INIT: " + e.getMessage());
			}
		}

		this.createRs();

	}

	public void DBclose() {

		try{

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


	public String[] getSpTonKho() {

		return this.spTonkho;
	}


	public void setSpTonKho(String[] spTonkho) {

		this.spTonkho = spTonkho; 
	}


	public boolean update() 
	{
		if(this.ngaynhan.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn ngày nhận hàng";
			return false;
		}

		if(spId == null)
		{
			this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm nhập kho";
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

					if( Double.parseDouble(spSoluong[i].trim().replaceAll(",", "")) >  Double.parseDouble(spXuat[i].trim().replaceAll(",", "")) )
					{
						this.msg = "Số lương nhận của sản phẩm (" + spTen[i] + ") không được vượt quá số lượng xuất ";
						return false;
					}
				}
			}

			if(!coSP)
			{
				this.msg = "Không có sản phẩm nào được nhập số lượng nhận. Vui lòng kiểm tra lại.";
				return false;
			}	
		}

		try
		{
			db.getConnection().setAutoCommit(false);

			String query = " Update NHAPKHO set ngaynhan = '" + this.ngaynhan + "', ngaysua = '" + this.getDateTime() + "', nguoisua = '" + this.userId + "', kho_fk = '" + this.khonhanId + "' where pk_seq = '" + this.id + "' ";

			System.out.println("1.Update NHAPKHOnpp: " + query);
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật NHAPKHOnpp " + query;
				db.getConnection().rollback();
				return false;
			}

			for(int i = 0; i < spId.length; i++)
			{
				String soluongNHAN = "0";
				if(spSoluong[i].trim().replaceAll(",", "").length() > 0)
					soluongNHAN = spSoluong[i].trim().replaceAll(",", "");

				query = "UPDATE NHAPKHO_SP set soluongNHAN = '" + soluongNHAN + "' " +
						"where NHAPKHO_fk = '" + this.id + "' and sanpham_fk = (select ma from sanpham where pk_seq= " + spId[i] + ") and loai = '" + spLoai[i] + "' and solo = '" + spSolo[i] + "' and scheme = N'" + spSCheme[i] + "' and NgayHetHan='"+spNgayHetHan[i]+"' ";

				System.out.println("2.Update NHAPKHOnpp_SP: " + query);
				if(db.updateReturnInt(query)!=1)
				{
					this.msg = "Khong the cap nhat NHAPKHOnpp_SP: " + query;
					db.getConnection().rollback();
					return false;
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
		getNppInfo();


		Utility util = new Utility();
		/*msg= util.Check_Huy_NghiepVu_KhoaSo("NHAPKHOnpp", this.id, "NgayNhan");
		if(msg.length()>0)
			return false;*/

		if(this.ngaynhan.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn ngày nhận hàng";
			return false;
		}

		if(this.sochungtu.trim().length() <= 0)
		{
			this.msg = "Vui lòng nhập số chứng từ";
			return false;
		}

		if(spId == null)
		{
			this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm nhập kho";
			return false;
		}
		else
		{
			boolean coSP = false;
			for(int i = 0; i < spId.length; i++)
			{
				if( spSoluong[i].trim().length() > 0 )
				{
					if(spSoluong[i].trim().replaceAll(",", "").length() > 0)
						coSP = true;

					if( Double.parseDouble(spSoluong[i].trim().replaceAll(",", "")) >  Double.parseDouble(spXuat[i].trim().replaceAll(",", "")) )
					{
						this.msg = "Số lương nhận của sản phẩm (" + spTen[i] + ") không được vượt quá số lượng xuất ";
						return false;
					}
				}
			}

			if(!coSP)
			{
				this.msg = "Không có sản phẩm nào được nhập số lượng nhận. Vui lòng kiểm tra lại.";
				return false;
			}	
		}

		try
		{
			//CHECK SO CHUNG TU

			String ycxk_fk = "";
			String query =
					"		select case   "+ 
							"			when YCXK_FK IS not null then CAST( YCXK_FK   AS VARCHAR(50) )  "+
							"			when CHUYENKHO_FK IS not null then (SELECT SoChungTu from ERP_CHUYENKHO where PK_SEQ=NHAPKHO.CHUYENKHO_FK)  "+
							"			when YCXKNPP_FK IS not null then  "+
							"	 "+
							"		(     "+
							"				SELECT top(1) SOHOADON FROM ERP_HOADONNPP_DDH A    "+
							"					INNER JOIN  ERP_YCXUATKHONPP_DDH B ON A.DDH_FK=B.DDH_FK   "+
							"					INNER JOIN ERP_HOADONNPP C ON C.PK_SEQ=A.HOADONNPP_FK     "+
							"				WHERE C.TRANGTHAI IN (2,4)	AND B.YCXK_FK=NHAPKHO.YCXKNPP_FK  "+   
							"			)  "+  
							"		when CHUYENKHONPP_FK IS NOT NULL THEN (SELECT SoChungTu from ERP_CHUYENKHONPP where pk_seq=NHAPKHO.CHUYENKHONPP_FK) end  as SoChungTu  "+
							"		,YCXK_FK,CHUYENKHO_FK,YCXKNPP_FK,CHUYENKHONPP_FK  "+
							"	from NHAPKHO  " +
							" where pk_Seq='"+this.id+"'   ";

			System.out.println("[SoCT]"+query);

			ResultSet rs = db.get(query);

			if(rs.next())
			{
				ycxk_fk = rs.getString("SoChungTu")==null ? "": rs.getString("SoChungTu");
			}

			if(rs != null)
			{				
				rs.close();	
			}

			if(ycxk_fk.length()<=0)
			{
				this.msg = "Số chứng từ chưa phát sinh!Vui lòng liên hệ TT!";
				return false;
			}

			if(!this.sochungtu.trim().equals(ycxk_fk.trim()) )
			{
				this.msg = "Số chứng từ không hợp lệ";
				return false;
			}



			db.getConnection().setAutoCommit(false);

			if(this.loaiDh.equals("4"))
			{
				this.khonhanId="100001";
			}


			// HÀNG KHUYẾN MẠI THÌ VẪN VÔ KHO LÚC TẠO NHẬN HÀNG
			query = " UPDATE NHAPKHO_SP SET KHONHAN_FK = '" + this.khonhanId + "' "+
					" WHERE NHAPKHO_fK = '" + this.id + "' and isnull(SCHEME,'') = ''   " ;

			System.out.println(":::>"+query);
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat NHAPKHOnpp_SP: " + query;
				db.getConnection().rollback();
				return false;
			}

			// NHập vô kho nhận được chọn
			query = " UPDATE NHAPKHO_SP SET KHONHAN_FK = '" + this.khonhanId + "' "+
					" WHERE NHAPKHO_fK = '" + this.id + "' and KHONHAN_FK IS NULL " ;

			System.out.println(":::"+query);
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat NHAPKHO_SP: " + query;
				db.getConnection().rollback();
				return false;
			}


			for(int i = 0; i < spId.length; i++)
			{
				String soluongNHAN = "0";
				if(spSoluong[i].trim().replaceAll(",", "").length() > 0)
					soluongNHAN = spSoluong[i].trim().replaceAll(",", "");

				query = "UPDATE NHAPKHO_SP set soluongNHAN = '" + soluongNHAN + "' " +
						"where NHAPKHO_fk = '" + this.id + "' and sanpham_fk = (select ma from sanpham where pk_seq=" + spId[i] + ") and isnull(loai,0) = '" + spLoai[i] + "' and solo = '" + spSolo[i] + "' and isnull(scheme,'') = N'" + spSCheme[i] + "' and NgayHetHan='"+spNgayHetHan[i]+"' ";

				System.out.println("2.Update NHAPKHOnpp_SP: " + query);
				if(db.updateReturnInt(query)!=1)
				{
					this.msg = "Khong the cap nhat NHAPKHOnpp_SP: " + query;
					db.getConnection().rollback();
					return false;
				}

				//TANG KHO
				String kho_fk = "";
				if(spLoai[i].equals("1"))  //SPKM
					kho_fk = " select khonhan_fk from NHAPKHO_SP where NHAPKHO_fk = '" + this.id + "' and sanpham_fk =  (select ma from sanpham where pk_seq=" + spId[i] + ") and solo = '" + spSolo[i] + "' and isnull(scheme,'') = '" + spSCheme[i] + "' and NgayHetHan='"+spNgayHetHan[i]+"' ";
				else
					kho_fk = " select khonhan_fk from NHAPKHO_SP where NHAPKHO_fk = '" + this.id + "' and sanpham_fk =  (select ma from sanpham where pk_seq=" + spId[i] + ") and solo = '" + spSolo[i] + "' and isnull(loai,0) = '0'  and NgayHetHan='"+spNgayHetHan[i]+"'  ";

				int exits = 1;

				query = 
						"select COUNT(*) as soDONG " +
								"from NHAPP_KHO where KHO_FK in ( " + kho_fk + " ) and NPP_FK = ( select NPP_FK from NHAPKHO where PK_SEQ = '" + this.id + "' )" +
								" and SANPHAM_FK = '" + spId[i] + "' and KBH_FK = ( select KBH_FK from NHAPKHO where PK_SEQ = '" + this.id + "' )  ";
				System.out.println("CHECK EXITS: " + query);

				ResultSet rsCHECK = db.get(query);
				if(rsCHECK.next())
				{
					exits = rsCHECK.getInt("soDONG");
				}
				rsCHECK.close();
				if(exits <= 0)
				{
					query = 
							"insert NHAPP_KHO(KHO_FK, NPP_FK, SANPHAM_FK, SOLUONG, BOOKED, AVAILABLE, KBH_FK) " +
									"select '"+this.khonhanId+"','"+this.nppId+"','"+spId[i]+"',0,0,0, ( select KBH_FK from NHAPKHO where PK_SEQ = '" + this.id + "' )  ";

					if(db.updateReturnInt(query)!=1)
					{
						this.msg = "Khong the cap nhat NHAPP_KHO: " + query;
						db.getConnection().rollback();
						return false;
					}
				}
				query = "update NHAPP_KHO set SOLUONG = SOLUONG + '" + soluongNHAN + "', AVAILABLE = AVAILABLE + '" + soluongNHAN + "' " +
						"where KHO_FK in ( " + kho_fk + " ) and NPP_FK = ( select NPP_FK from NHAPKHO where PK_SEQ = '" + this.id + "' ) and SANPHAM_FK = '" + spId[i] + "' " +
						" and KBH_FK = ( select KBH_FK from NHAPKHO where PK_SEQ = '" + this.id + "' ) ";

				System.out.println("3.cap nhat NHAPP_KHO: " + query);
				if(db.updateReturnInt(query)!=1)
				{
					this.msg = "Khong the cap nhat NHAPP_KHO: " + query;
					db.getConnection().rollback();
					return false;
				}
				//CHECK XEM KHO CHI TIET DA CO CHUA, NEU CHUA CO THI INSERT

				/* exits = 1;
					query = 
							"select COUNT(*) as soDONG " +
							"from NHAPP_KHO_CHITIET where KHO_FK in ( " + kho_fk + " ) and NPP_FK = ( select NPP_FK from Nhaphang where PK_SEQ = '" + this.id + "' )" +
							" and SANPHAM_FK = '" + spId[i] + "' and KBH_FK = ( select KBH_FK from Nhaphang where PK_SEQ = '" + this.id + "' ) and SOLO = '" + spSolo[i] + "' and NgayHetHan='"+spNgayHetHan[i]+"'  ";

					System.out.println("CHECK EXITS: " + query);

					rsCHECK = db.get(query);

					{
						if(rsCHECK.next())
						{
							exits = rsCHECK.getInt("soDONG");
						}
						rsCHECK.close();
					}

				if(exits <= 0)
				{
					query = 
							"insert NHAPP_KHO_CHITIET(KHO_FK, NPP_FK, SANPHAM_FK, SOLUONG, BOOKED, AVAILABLE, KBH_FK, SOLO, NGAYHETHAN) " +
							"select ( " + kho_fk + " ), b.NPP_FK, a.SANPHAM_FK, a.soluongNHAN, 0, a.soluongNHAN, b.KBH_FK, a.SOLO, a.NGAYHETHAN  " +
							"from Nhaphang_SP a inner join Nhaphang b on a.Nhaphang_FK = b.PK_SEQ  " +
							"where b.PK_SEQ = '" + this.id + "' and SANPHAM_FK = '" + spId[i] + "' and SOLO = '" + spSolo[i] + "' and isnull(LOAI,0) = '" + spLoai[i] + "' and isnull(SCHEME,'') = '" + spSCheme[i] + "' and NgayHetHan='"+spNgayHetHan[i]+"' ";
				}
				else
				{
					query = " update CT  " +
									" set CT.SOLUONG = CT.SOLUONG + NH.soluongNHAN, " +
									" 			  CT.AVAILABLE = CT.AVAILABLE + NH.soluongNHAN, " +
									" 			  CT.NGAYHETHAN = NH.NGAYHETHAN " +
									" from " +
									" ( " +
										" select ( " + kho_fk + " ) as kho_fk, b.NPP_FK, a.SANPHAM_FK, a.soluongNHAN, b.KBH_FK, a.SOLO,a.NGAYHETHAN " +
										" from Nhaphang_SP a inner join Nhaphang b on a.Nhaphang_FK = b.PK_SEQ " +
										" where b.PK_SEQ = '" + this.id + "' and SANPHAM_FK = '" + spId[i] + "' and SOLO = '" + spSolo[i] + "' and isnull(LOAI,0) = '" + spLoai[i] + "' and isnull(SCHEME,'') = '" + spSCheme[i] + "' and NgayHetHan='"+spNgayHetHan[i]+"'  " +
									" ) " +
									" NH inner join NHAPP_KHO_CHITIET CT on NH.kho_fk = CT.KHO_FK and NH.NPP_FK = CT.NPP_FK and NH.KBH_FK = CT.KBH_FK and NH.SOLO = CT.SOLO and NH.SANPHAM_FK = CT.sanpham_fk and  nh.NgayHetHan=ct.NgayHetHan ";
				}
				 */
				System.out.println("4.cap nhat NHAPP_KHO_CHITIET: " + query);
				if(db.updateReturnInt(query)!=1)
				{
					this.msg = "Khong the cap nhat NHAPP_KHO_CHITIET: " + query;
					db.getConnection().rollback();
					return false;
				}
			}

			query = 
					" Update NHAPKHO set SoChungTu='" + this.sochungtu + "',ngaynhan = '" + this.ngaynhan + "', trangthai = '1', kho_fk = '" + this.khonhanId + "', ngaysua = '" + this.getDateTime() + "', nguoisua = '" + this.userId + "' " + 
							" where pk_seq = '" + this.id+ "' and trangthai=0 ";

			System.out.println("1.Update NHAPKHOnpp: " + query);
			if (db.updateReturnInt(query) != 1)
			{
				this.msg = "Không thể cập nhật NHAPKHOnpp " + query;
				db.getConnection().rollback();
				return false;
			}
			/*msg= util.Check_Kho_Tong_VS_KhoCT(util.getIdNhapp(userId));
			if(msg.length()>0)
			{
				db.getConnection().rollback();
				return false;
			}*/
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


	public String[] getSpSolo() {

		return this.spSolo;
	}


	public void setSpSolo(String[] spSolo) {

		this.spSolo = spSolo;
	}


	public String[] getSpXuat() {

		return this.spXuat;
	}


	public void setSpXuat(String[] spXuat) {

		this.spXuat = spXuat;
	}


	public String[] getSpDongia() {

		return this.spDongia;
	}


	public void setSpDongia(String[] spDongia) {

		this.spDongia = spDongia;
	}


	public boolean create() {

		return false;
	}


	public String getNgaynhap() {

		return this.ngaynhan;
	}


	public void setNgaynhap(String ngaynhap) {

		this.ngaynhan = ngaynhap;
	}


	public String getSochungtu() {

		return this.sochungtu;
	}


	public void setSOchungtu(String sochungtu) {

		this.sochungtu = sochungtu;
	}


	public String getKhonhanId() {

		return this.khonhanId;
	}


	public void setKhonhanId(String khonhanId) {

		this.khonhanId = khonhanId;
	}


	public ResultSet getKhonhanRs() {

		return this.khonhanRs;
	}


	public void setKhonhanRs(ResultSet khonhanRs) {

		this.khonhanRs = khonhanRs;
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

	public String getNppId() 
	{
		return this.nppId;
	}

	public void setNppId(String nppId) 
	{
		this.nppId = nppId;
	}
	String loaiDh;
	public String getLoaiDh()
	{
		return loaiDh;
	}

	public void setLoaiDh(String loaiDh)
	{
		this.loaiDh = loaiDh;
	}

}
