package geso.dms.center.beans.phieuxuatkhodms.imp;

import geso.dms.center.beans.phieuxuatkhodms.IPhieuxuatkhoDms;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.OracleConnUtils;
import geso.dms.center.util.Utility;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

public class PhieuxuatkhoDms implements IPhieuxuatkhoDms
{
	String userId;
	String id;

	String sochungtu;
	String ngaychungtu;
	String ghichu;
	String sopo;
	String soso;
	String trangthai;
	
	String msg;
	
	String nppId;
	String dvkdId;
	String kbhId;
	
	ResultSet kbhRs;
	ResultSet dvtRs;
	ResultSet schemeRs;
	ResultSet dvkdRs;	
	ResultSet nppRs;
	
	String[] spId;
	String[] spMa;
	String[] spTen;
	String[] spDonvi;
	String[] spSoluong;
	String[] spGianhap;
	String[] spSCheme;
	String[] spVAT;
	
	dbutils db;
	NumberFormat formater = new DecimalFormat("##,###,###");
	NumberFormat formater2 = new DecimalFormat("##,###,###.####");

	public PhieuxuatkhoDms()
	{
		this.id = "";
		this.ngaychungtu = getDateTime();
		this.ghichu = "";
		this.nppId = "";
		this.msg = "";
		this.trangthai = "0";
		this.dvkdId = "";
		this.kbhId = "";
		this.sochungtu = "";
		this.sopo = "";
		this.soso = "";
		this.db = new dbutils();
	}

	public PhieuxuatkhoDms(String id)
	{
		this.id = id;
		this.ngaychungtu = getDateTime();
		this.ghichu = "";
		this.nppId = "";
		this.msg = "";
		this.trangthai = "0";
		this.dvkdId = "";
		this.kbhId = "";
		this.sochungtu = "";
		this.sopo = "";
		this.soso = "";

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

	public String getMsg() 
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}

	public boolean createNK() 
	{
		if( this.sochungtu.trim().length() <= 0 )
		{
			this.msg = "Vui lòng nhập số chứng từ";
			return false;
		}
		
		if( this.sopo.trim().length() <= 0 )
		{
			this.msg = "Vui lòng nhập số PO";
			return false;
		}
		
		if( this.soso.trim().length() <= 0 )
		{
			this.msg = "Vui lòng nhập số SO";
			return false;
		}
		
		if(this.ngaychungtu.trim().length() < 10)
		{
			this.msg = "Vui lòng nhập ngày nhập kho";
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
			this.msg = "Vui lòng chọn nhà phân phối đặt hàng";
			return false;
		}

		if(spMa == null)
		{
			this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm đặt hàng";
			return false;
		}
		else
		{
			boolean coSP = false;
			for(int i = 0; i < spMa.length; i++)
			{
				if( spMa[i].trim().length() > 0 )
				{
					if(spSoluong[i].trim().length() <= 0)
					{
						this.msg = "Bạn phải nhập số lượng của sản phẩm ( " + spTen[i] + " )";
						return false;
					}

					if(spGianhap[i].trim().length() <= 0 && spSCheme[i].trim().length() <= 0 )
					{
						this.msg = "Bạn phải nhập đơn giá của sản phẩm ( " + spTen[i] + " )";
						return false;
					}

					coSP = true;
				}
			}

			if(!coSP)
			{
				this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm";
				return false;
			}

			//CHECK TRUNG MA 
			for(int i = 0; i < spMa.length - 1; i++)
			{
				for(int j = i + 1; j < spMa.length; j++)
				{
					if(spMa[i].trim().length() > 0)
					{
						if( spMa[i].trim().equals(spMa[j].trim()) && spSCheme[i].trim().equals(spSCheme[j].trim()))
						{
							this.msg = "Sản phẩm ( " + spTen[i] + " )  đã bị trùng.";
							return false;
						}
					}
				}
			}	
		}

		try
		{
			db.getConnection().setAutoCommit(false);

			String query = 
				" insert phieuxuatkhodms(sochungtu, ngaychungtu, sopo, soso, trangthai, dvkd_fk, kbh_fk, npp_fk, "+
				" ghichu, ngaytao, nguoitao, ngaysua, nguoisua) " +
				" values('" + this.sochungtu + "', '" + this.ngaychungtu + "', '" + this.sopo + "', '"+this.soso+"', '0', '" + dvkdId + "', "+
				" '" + kbhId + "', '" + nppId + "', N'" + this.ghichu + "', '" + getDateTime() + "', '" + this.userId + "', "+
				" '" + getDateTime() + "', '" + this.userId + "')";

			System.out.println("1.Insert DDH: " + query);
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới phieuxuatkhodms " + query;
				db.getConnection().rollback();
				return false;
			}

			//LAY ID
			ResultSet rsDDH = db.get("select Scope_identity() as ID ");
			if(rsDDH.next())
			{
				this.id = rsDDH.getString("ID");
			}
			rsDDH.close();

			System.out.println("DDH ID: " + this.id);

			for(int i = 0; i < spMa.length; i++)
			{
				if(spMa[i].trim().length() > 0)
				{
					query = "insert phieuxuatkhodms_sanpham(phieuxuatkho_fk, SANPHAM_FK, soluong, dongia, dvdl_fk, vat, scheme) " +
							"select '" + this.id + "', pk_seq, '" + spSoluong[i].replaceAll(",", "") + "', '" + spGianhap[i].replaceAll(",", "") + "', "+
							"ISNULL( ( select pk_Seq from DONVIDOLUONG where donvi = N'" + spDonvi[i].trim() + "' ), DVDL_FK), "+
							"'" + spVAT[i].replaceAll(",", "") + "', N'"+spSCheme[i]+"'  " +
							"from SANPHAM where MA = '" + spMa[i].trim() + "' ";

					System.out.println("1.Insert NK - SP: " + query);
					if(!db.update(query))
					{
						this.msg = "Khong the tao moi phieuxuatkhodms_sanpham: " + query;
						db.getConnection().rollback();
						return false;
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

	public boolean updateNK() 
	{
		if( this.sochungtu.trim().length() <= 0 )
		{
			this.msg = "Vui lòng nhập số chứng từ";
			return false;
		}
		
		if( this.sopo.trim().length() <= 0 )
		{
			this.msg = "Vui lòng nhập số PO";
			return false;
		}
		
		if( this.soso.trim().length() <= 0 )
		{
			this.msg = "Vui lòng nhập số SO";
			return false;
		}
		
		if(this.ngaychungtu.trim().length() < 10)
		{
			this.msg = "Vui lòng nhập ngày nhập kho";
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
			this.msg = "Vui lòng chọn nhà phân phối đặt hàng";
			return false;
		}

		if(spMa == null)
		{
			this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm đặt hàng";
			return false;
		}
		else
		{
			boolean coSP = false;
			for(int i = 0; i < spMa.length; i++)
			{
				if( spMa[i].trim().length() > 0 )
				{
					if(spSoluong[i].trim().length() <= 0 && spSCheme[i].trim().length() <= 0 )
					{
						this.msg = "Bạn phải nhập số lượng của sản phẩm ( " + spTen[i] + " )";
						return false;
					}

					if(spGianhap[i].trim().length() <= 0 && spSCheme[i].trim().length() <= 0 )
					{
						this.msg = "Bạn phải nhập đơn giá của sản phẩm ( " + spTen[i] + " )";
						return false;
					}

					coSP = true;
				}
			}

			if(!coSP)
			{
				this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm";
				return false;
			}

			//CHECK TRUNG MA 
			for(int i = 0; i < spMa.length - 1; i++)
			{
				for(int j = i + 1; j < spMa.length; j++)
				{
					if(spMa[i].trim().length() > 0)
					{
						if( spMa[i].trim().equals(spMa[j].trim()) && spSCheme[i].trim().equals(spSCheme[j].trim()))
						{
							this.msg = "Sản phẩm ( " + spTen[i] + " )  đã bị trùng.";
							return false;
						}
					}
				}
			}

			try
			{
				db.getConnection().setAutoCommit(false);
		
				String query =	
					" Update PHIEUXUATKHODMS set ngaychungtu = '" + this.ngaychungtu + "', sochungtu = '"+this.sochungtu+"', "+
					" ghichu = N'" + this.ghichu + "', dvkd_fk = '" + this.dvkdId + "', sopo = '"+this.sopo+"', soso = '"+this.soso+"',"+
					" kbh_fk = '" + this.kbhId + "', npp_fk = '" + this.nppId + "', ngaysua = '" + getDateTime() + "', "+
					" nguoisua = '" + this.userId + "' " + 
					" where pk_seq = '" + this.id + "' and trangthai = 0 ";

				if(this.db.updateReturnInt(query)!=1)
				{
					this.msg = "Không thể cập nhật PHIEUXUATKHODMS do trạng thái không hợp lệ ";
					db.getConnection().rollback();
					return false;
				}

				query = "delete phieuxuatkhodms_sanpham where phieuxuatkho_fk = '" + this.id + "'";
				if(!db.update(query))
				{
					this.msg = "Không thể cập nhật phieuxuatkhodms_sanpham " + query;
					db.getConnection().rollback();
					return false;
				}

				for(int i = 0; i < spMa.length; i++)
				{
					if(spMa[i].trim().length() > 0)
					{
						query = "insert phieuxuatkhodms_sanpham(phieuxuatkho_fk, SANPHAM_FK, soluong, dongia, dvdl_fk, vat, scheme) " +
								"select '" + this.id + "', pk_seq, '" + spSoluong[i].replaceAll(",", "") + "', '" + spGianhap[i].replaceAll(",", "") + "', "+
								"ISNULL( ( select pk_Seq from DONVIDOLUONG where donvi = N'" + spDonvi[i].trim() + "' ), DVDL_FK), "+
								"'" + spVAT[i].replaceAll(",", "") + "', N'"+spSCheme[i]+"' " +
								"from SANPHAM where MA = '" + spMa[i].trim() + "' ";

						System.out.println("1.Insert NK - SP: " + query);
						if(!db.update(query))
						{
							this.msg = "Khong the tao moi ERP_Dondathang_SANPHAM: " + query;
							db.getConnection().rollback();
							return false;
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
	}
	
	public void createRs() 
	{			
		Utility util =new Utility(); 
		String query = "";
		ResultSet rs;
		
		this.dvtRs = db.getScrol("select PK_SEQ, DONVI from DONVIDOLUONG where trangthai = '1' ");		
		this.dvkdRs = db.get("select PK_SEQ, DONVIKINHDOANH as TEN from DONVIKINHDOANH where TRANGTHAI = '1'");
		this.kbhRs = db.get("select PK_SEQ, DIENGIAI as TEN from KENHBANHANG where TRANGTHAI = '1' and pk_Seq in "+util.quyen_kenh(userId));

		query = "select PK_SEQ, MA + ' - ' + TEN as TEN from NHAPHANPHOI where TRANGTHAI = '1'  AND PK_sEQ IN "+util.quyen_npp(userId)+" ";
		if(this.kbhId.equals("100021"))
			query = "select PK_SEQ, MA + ' - ' + TEN as TEN from NHAPHANPHOI where TRANGTHAI = '1' ";
		if(this.dvkdId.trim().length() > 0)
			query += " and pk_seq in ( select NPP_FK from NHAPP_NHACC_DONVIKD where NCC_DVKD_FK in ( select PK_SEQ from NHACUNGCAP_DVKD where DVKD_FK = '" + this.dvkdId + "' ) ) ";
		if(this.kbhId.trim().length() > 0)
			query += " and pk_seq in ( select NPP_FK from NHAPP_KBH where KBH_FK = '" + this.kbhId + "' ) ";
		this.nppRs = db.get(query);
		if(this.nppId.trim().length() > 0)
		{
			query = "select a.PK_SEQ as nppId, d.DVKD_FK, b.KBH_FK  " +
					"From NhaPhanPhoi a left join nhapp_kbh b on b.NPP_FK = a.PK_SEQ left join NHAPP_NHACC_DONVIKD c on c.NPP_FK = b.NPP_FK  " +
					"	left join NHACUNGCAP_DVKD d on d.PK_SEQ = c.NCC_DVKD_FK   " +
					"where a.pk_Seq = '" + this.nppId + "' ";
			ResultSet rsInfo = db.get(query);
			/*if(rsInfo != null)*/
			{
				try 
				{
					if(rsInfo.next())
					{
						if(this.dvkdId.trim().length() <= 0)
							this.dvkdId = rsInfo.getString("DVKD_FK");
						if(this.kbhId.trim().length() <= 0 )
							this.kbhId = rsInfo.getString("KBH_FK");
					}
					rsInfo.close();
				} 
				catch (Exception e) {e.printStackTrace();}
			}
		}
		
		query = "select pk_Seq, SCHEMEERP, SCHEME, LOAICT from CTKHUYENMAI"; 
		this.schemeRs = db.get(query);
	}

	private void initSANPHAM() 
	{
		if(this.id.length()>0)
		{
			String query =  "select b.MA, b.TEN, DV.donvi, a.soluong, a.dongia, a.vat, a.scheme " +
					" from phieuxuatkhodms_sanpham a inner Join SanPham b on a.SANPHAM_FK = b.PK_SEQ    " +
					" 		INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ = a.DVDL_FK       " +
					" where a.phieuxuatkho_FK = '" + this.id + "' ";

			System.out.println("--INIT SP: " + query);
			ResultSet spRs = db.get(query);

			if(spRs != null)
			{
				try 
				{
					String spMA = "";
					String spTEN = "";
					String spDONVI = "";
					String spSOLUONG = "";
					String spGIANHAP = "";
					String spVAT = "";
					String spSCHEME = "";

					while(spRs.next())
					{
						spMA += spRs.getString("MA") + "__";
						spTEN += spRs.getString("TEN") + "__";
						spDONVI += spRs.getString("DONVI") + "__";
						spSOLUONG += formater.format(spRs.getDouble("SOLUONG")) + "__";
						spGIANHAP += formater2.format(spRs.getDouble("DONGIA")) + "__";
						spVAT += formater2.format(spRs.getDouble("VAT")) + "__";
						spSCHEME += spRs.getString("scheme").equals("")? " __":spRs.getString("scheme")+"__";
					}
					spRs.close();
					System.out.println("spscheme "+spSCHEME);
					if(spMA.trim().length() > 0)
					{
						spMA = spMA.substring(0, spMA.length() - 2);
						this.spMa = spMA.split("__");

						spTEN = spTEN.substring(0, spTEN.length() - 2);
						this.spTen = spTEN.split("__");

						spDONVI = spDONVI.substring(0, spDONVI.length() - 2);
						this.spDonvi = spDONVI.split("__");
						
						spSOLUONG = spSOLUONG.substring(0, spSOLUONG.length() - 2);
						this.spSoluong = spSOLUONG.split("__");

						spGIANHAP = spGIANHAP.substring(0, spGIANHAP.length() - 2);
						this.spGianhap = spGIANHAP.split("__");

						spVAT = spVAT.substring(0, spVAT.length() - 2);
						this.spVAT = spVAT.split("__");

						spSCHEME = spSCHEME.substring(0, spSCHEME.length() - 2);
						this.spSCheme = spSCHEME.split("__");
					}
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
					System.out.println("115.Exception: " + e.getMessage());
				}
			}
		}
	}

	public void init() 
	{
		String query = 
				"select sochungtu, ngaychungtu, ISNULL(ghichu, '') as ghichu, dvkd_fk, kbh_fk, npp_fk, sopo, soso "+
				"from phieuxuatkhodms where pk_seq = '" + this.id + "'";
		System.out.println("____INIT NHAP KHO: " + query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				if(rs.next())
				{
					this.sochungtu = rs.getString("sochungtu");
					this.sopo = rs.getString("sopo");
					this.soso = rs.getString("soso");
					this.ngaychungtu = rs.getString("ngaychungtu");
					this.ghichu = rs.getString("ghichu");
					this.dvkdId = rs.getString("dvkd_fk");
					this.kbhId = rs.getString("kbh_fk");
					this.nppId = rs.getString("npp_fk");
				}
				rs.close();
			} 
			catch (Exception e) 
			{
				System.out.println("---LOI INIT: " + e.getMessage());
			}
		}
	
		this.initSANPHAM();
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
	
	public ResultSet getDvtRs() {

		return this.dvtRs;
	}

	public void setDvtRs(ResultSet dvtRs) {

		this.dvtRs = dvtRs;
	}

	public ResultSet getSchemeRs() {

		return this.schemeRs;
	}

	public void setSchemeRs(ResultSet schemeRs) {

		this.schemeRs = schemeRs;
	}

	public String[] getSpScheme() {

		return this.spSCheme;
	}

	public void setSpScheme(String[] spScheme) {

		this.spSCheme = spScheme;
	}
	
	public String[] getSpVat() 
	{
		return this.spVAT;
	}
	public void setSpVat(String[] spVat) 
	{
		this.spVAT = spVat;
	}

	@Override
	public String getNgaychungtu() {
		return this.ngaychungtu;
	}

	@Override
	public void setNgaychungtu(String ngaychungtu) {
		this.ngaychungtu = ngaychungtu;
	}

	@Override
	public String getSochungtu() {
		return sochungtu;
	}

	@Override
	public void setSochungtu(String sochungtu) {
		this.sochungtu = sochungtu;
	}

	@Override
	public String getSopo() {
		return this.sopo;
	}

	@Override
	public void setSopo(String sopo) {
		this.sopo = sopo;
	}

	@Override
	public String getSoso() {
		return this.soso;
	}

	@Override
	public void setSoso(String soso) {
		this.soso = soso;
	}
}
