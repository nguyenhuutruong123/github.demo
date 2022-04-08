package geso.dms.distributor.beans.dondathang.imp;

import geso.dms.distributor.db.sql.dbutils;
import geso.dms.center.util.Utility;
import geso.dms.distributor.beans.dondathang.IErpDondathangDoitac;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

public class ErpDondathangDoitac implements IErpDondathangDoitac
{

	String userId;
	String id;
	String ngayyeucau;
	String ngaydenghi;
	String ghichu;
	String msg;
	String trangthai;

	String loaidonhang; // 0 đơn đặt hàng, 1 đơn hàng khuyến mại ứng hàng, 3 đơn
	// hàng khuyến mại tích lũy, 4 đơn hàng trưng bày, 5 đơn
	// hàng khác
	String chietkhau;
	String vat;
	String khoNhanId;
	ResultSet khoNhanRs;
	String dungchungKENH;
	String khId;
	ResultSet khRs;
	String nppId;
	String nppTen;
	String sitecode;
	String congno;
	String maddh;
	int ngay_Chenhlech;
	String tiengiamtru = "";
	String diengiaigiamtru = "";

	NumberFormat formater_1sole = new DecimalFormat("########.#");

	public String getTiengiamtru() {
		return tiengiamtru;
	}
	public void setTiengiamtru(String tiengiamtru) {
		this.tiengiamtru = tiengiamtru;
	}
	public String getDiengiaigiamtru() {
		return diengiaigiamtru;
	}
	public void setDiengiaigiamtru(String diengiaigiamtru) {
		this.diengiaigiamtru = diengiaigiamtru;
	}
	public String getMaddh() {
		return maddh;
	}

	public void setMaddh(String maddh) {
		this.maddh = maddh;
	}

	String dvkdId;
	ResultSet dvkdRs;

	String kbhId;
	ResultSet kbhRs;
	String isdhk;
	public String getIsdhk() {
		return isdhk;
	}

	public void setIsdhk(String isdhk) {
		this.isdhk = isdhk;
	}

	String isgia;
	public String getIsgia() {
		return isgia;
	}

	public void setIsgia(String isgia) {
		this.isgia = isgia;
	}

	ResultSet dvtRs;

	String[] spId;
	String[] spMa;
	String[] spTen;
	String[] spDonvi;
	String[] spSoluong;
	String[] spGianhap;
	String[] spChietkhau;
	String[] spVAT;
	String[] spSCheme;
	String[] spSoluongton;
	String[] spGiagoc;
	String[] sptonkhocn;



	public String[] getSpSoluongton()
	{
		return spSoluongton;
	}

	public void setSpSoluongton(String[] spSoluongton)
	{
		this.spSoluongton = spSoluongton;
	}

	String[] dhCk_diengiai;
	String[] dhCk_giatri;
	String[] dhCk_loai;

	ResultSet congnoRs;
	Hashtable<String, String> sanpham_soluong;

	dbutils db;
	Utility util;

	public ErpDondathangDoitac()
	{
		this.id = "";
		this.ngayyeucau = getDateTime();
		this.ngaydenghi = "";
		this.ghichu = "";
		this.khoNhanId = "";
		this.khId = "";
		this.msg = "";
		this.loaidonhang = "0";
		this.trangthai = "0";
		this.chietkhau = "0";
		this.vat = "10";
		this.dvkdId = "";
		this.kbhId = "";
		this.dungchungKENH = "0";
		this.congno = "0";
		this.iskm="0";
		this.sanpham_soluong = new Hashtable<String, String>();
		this.util = new Utility();
		this.db = new dbutils();
		this.isdhk="0";
		this.isgia="0";
	}

	public ErpDondathangDoitac(String id)
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
		this.dungchungKENH = "0";
		this.iskm="0";
		this.sanpham_soluong = new Hashtable<String, String>();
		this.util = new Utility();
		this.db = new dbutils();
		this.isdhk="0";
		this.isgia="0";
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

	
	public String RevertBooked (String nghiepvu) throws SQLException
	{
		
		// kho tong
		geso.dms.center.util.Utility util = new geso.dms.center.util.Utility();
		String query=	 "\n SELECT sp.dvdl_fk,c.ten,DH.NgayDonHang,SP.SANPHAM_FK,dh.KHO_FK,KHO.NPP_FK,KHO.KBH_FK,SP.SOLUONG " +
				 "\n from ERP_DondathangNPP dh inner join ERP_DondathangNPP_SANPHAM sp on dh.PK_SEQ = sp.DONDATHANG_FK   " +
				 "\n inner join nhapp_kho kho on sp.SANPHAM_FK = kho.SANPHAM_FK and dh.KHO_FK = kho.KHO_FK and kho.NPP_FK = dh.NPP_FK " +
				 "\n		and kho.KBH_FK = dh.KBH_FK "+
				 "\n inner join sanpham c on c.pk_seq=kho.sanpham_fk "+
				 "\n where dh.PK_SEQ = "+this.id+
				 "\n 		and dh.trangthai=0 ";

		System.out.println("query select = "+ query);
		ResultSet ckRs = db.get(query);
		while(ckRs.next())
		{
			String _dvdl_fk =ckRs.getString("dvdl_fk");
			String _kho_fk=ckRs.getString("kho_fk");
			String _nppId=ckRs.getString("npp_fk");	
			String _kbh_fk=ckRs.getString("kbh_fk");
			String _sanpham_fk=ckRs.getString("sanpham_fk");
			String ngaynhap  =ckRs.getString("NgayDonHang");
			Double soluong = ckRs.getDouble("soluong");
			String tensp=ckRs.getString("ten");
			msg=util.Update_NPP_Kho_Sp(ngaynhap,this.id, nghiepvu +"RevertBooked Tong_"+ this.id, db, _kho_fk, _sanpham_fk, _nppId, _kbh_fk, 0.0, (-1)* soluong, soluong, 0.0);
			if(msg.length()>0)
			{
				return "Lỗi kho khi tạo đơn hàng: " + this.msg;
				
			}	


		}	
		// kho ct
		query=	 " SELECT DH.NgayDonHang,SP.SANPHAM_FK,DH.KHO_FK,DH.NPP_FK,DH.KBH_FK,SP.SOLUONG, a.ten as tensp, sp.ngaynhapkho " 
			+ "\n from ERP_DondathangNPP dh inner join ERP_DondathangNPP_SANPHAM_chitiet sp on dh.PK_SEQ = sp.DONDATHANG_FK    "
			+ "\n inner join sanpham a on a.pk_seq = sp.sanpham_fk  "
			+ "\n where dh.PK_SEQ = "+ this.id+ " and dh.trangthai=0";

		System.out.println("UPDATE NPP KHO chi tiet: " + query  );
		ckRs = db.get(query);
		while(ckRs.next())
		{
			String kho_fk=ckRs.getString("kho_fk");
			String nppId=ckRs.getString("npp_fk");	
			String kenh =ckRs.getString("kbh_fk");
			String sanpham_fk=ckRs.getString("sanpham_fk");
			String ngaynhap  =ckRs.getString("NgayDonHang");
			String tensp  =ckRs.getString("tensp");
			Double soluong = ckRs.getDouble("soluong");
			String ngaynhapkho=ckRs.getString("NGAYNHAPKHO");    	
			String msg1=util.Update_NPP_Kho_Sp_Chitiet(ngaynhap, nghiepvu+"RevertBooked CT: "+this.id ,  db, kho_fk, sanpham_fk, nppId, kenh, ngaynhapkho, 0,(-1)*soluong, soluong, 0, 0);
			if (msg1.length()> 0) 
			{
				return msg1;
			}
		}	
		return "";
	}
	
	public String BookKho (String nghiepvu) throws SQLException
	{
		geso.dms.center.util.Utility util = new geso.dms.center.util.Utility();
		String query=	 "\n SELECT sp.dvdl_fk,c.ten,DH.NgayDonHang,SP.SANPHAM_FK,dh.KHO_FK,KHO.NPP_FK,KHO.KBH_FK,SP.SOLUONG " +
				 "\n from ERP_DondathangNPP dh inner join ERP_DondathangNPP_SANPHAM sp on dh.PK_SEQ = sp.DONDATHANG_FK   " +
				 "\n inner join nhapp_kho kho on sp.SANPHAM_FK = kho.SANPHAM_FK and dh.KHO_FK = kho.KHO_FK and kho.NPP_FK = dh.NPP_FK " +
				 "\n		and kho.KBH_FK = dh.KBH_FK "+
				 "\n inner join sanpham c on c.pk_seq=kho.sanpham_fk "+
				 "\n where dh.PK_SEQ = "+this.id+
				 "\n 		and dh.trangthai=0 ";

		System.out.println("query select = "+ query);
		ResultSet ckRs = db.get(query);
		while(ckRs.next())
		{
			String _dvdl_fk =ckRs.getString("dvdl_fk");
			String _kho_fk=ckRs.getString("kho_fk");
			String _nppId=ckRs.getString("npp_fk");	
			String _kbh_fk=ckRs.getString("kbh_fk");
			String _sanpham_fk=ckRs.getString("sanpham_fk");
			String ngaynhap  =ckRs.getString("NgayDonHang");
			Double soluong = ckRs.getDouble("soluong");
			String tensp=ckRs.getString("ten");
			msg=util.Update_NPP_Kho_Sp(ngaynhap,this.id,nghiepvu + " ĐƠN HÀNG NPP_", db, _kho_fk, _sanpham_fk, _nppId, _kbh_fk, 0.0,  soluong, (-1)*soluong, 0.0);
			if(msg.length()>0)
			{
				return "Lỗi kho khi tạo đơn hàng: " + this.msg;
				
			}	


			query=  " select KHO_FK,SANPHAM_FK,KBH_FK,NGAYNHAPKHO,available  from NHAPP_KHO_CHITIET "+  
			" where NPP_FK ="+_nppId+" and KBH_FK= " +_kbh_fk +
			" and KHO_FK="+_kho_fk+"  and SANPHAM_FK =  "+ _sanpham_fk +
			" AND AVAILABLE >0  and NGAYNHAPKHO<='"+ngaynhap+"'"+
			" order by NGAYNHAPKHO,AVAILABLE ";
			ResultSet rssp=db.get(query);
			double soluongdenghi=soluong ;

			while(rssp.next() && soluongdenghi >0){
				double soluong_avai= rssp.getDouble("AVAILABLE");
				double soluongcapnhat=0;
				if(soluong_avai >soluongdenghi){
					soluongcapnhat= soluongdenghi;
					soluongdenghi=0;
				}else{
					soluongcapnhat =soluong_avai;
					soluongdenghi =soluongdenghi - soluong_avai;
				}

				String _khoid=rssp.getString("kho_fk");
				String _kbhid=rssp.getString("KBH_FK");
				String ngaynhapkho=rssp.getString("NGAYNHAPKHO");

				query ="\n insert ERP_DONDATHANGNPP_SANPHAM_CHITIET( dondathang_fk,SANPHAM_FK,soluong,LOAI,scheme,DVDL_FK,ngaynhapkho ) "+
				"\n select '"+ this.id+ "',"+ _sanpham_fk +",  "+ soluongcapnhat +", 0 loai, '' scheme, "+_dvdl_fk+" ,'"+ ngaynhapkho +"' ";

				if (db.updateReturnInt(query)<=0) 
				{
					return "Không thể cập nhật : "+query;
					
				}

				String msg1=util.Update_NPP_Kho_Sp_Chitiet(ngaynhap, nghiepvu+ " ĐƠN HÀNG NPP chi tiết_"+this.id ,  db, _khoid, _sanpham_fk, _nppId, _kbhid, ngaynhapkho, 0,soluongcapnhat,(-1)* soluongcapnhat, 0, 0);
				if (msg1.length()> 0) 
				{
					return msg1;
				
				}

			}
			rssp.close();

			if(soluongdenghi!=0){

				return  "Số lượng đề xuất trong lô chi tiết của sản phẩm "+tensp+"   tới ngày (ngày cấu hình hóa đơn)"+ngaynhap+" không còn đủ, " +
				" vui lòng kiểm tra báo cáo ( xuất nhập tồn,tồn hiện tại) theo lô để biết thêm chi tiết ";
				

			}

		}	
		return "";
	}

	public boolean createNK() 
	{


		Utility uilt_kho=new Utility();

		String ngayhoadon_ =uilt_kho.getngayhoadon(this.userId,db,this.ngayyeucau,this.khId,1);

		if(this.ngayyeucau.trim().length() < 10)
		{
			this.msg = "Vui lòng nhập ngày nhập kho";
			return false;
		}

		if(this.ngaydenghi.trim().length() < 10)
		{
			this.msg = "Vui lòng nhập ngày đề nghị giao hàng";
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

		if( this.khId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn đối tác đặt hàng";
			return false;
		}

		if( this.khoNhanId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn kho đặt hàng";
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

					if(spGianhap[i].trim().length() <= 0)
					{
						this.msg = "Bạn phải nhập đơn giá của sản phẩm ( " + spTen[i] + " )";
						return false;
					}

					if(spDonvi[i].trim().length() <= 0)
					{
						this.msg = "Bạn phải nhập đơn vị  của sản phẩm ( " + spTen[i] + " )";
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
					if(spMa[i].trim().length() > 0 && spMa[j].trim().length() > 0 )
					{
						if( spMa[i].trim().equals(spMa[j].trim()) )
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

			String khonhan_fk = this.khoNhanId.trim().length() <= 0 ? "null" : this.khoNhanId.trim();
			String chietkhau = this.chietkhau.trim().length() <= 0 ? "0" : this.chietkhau.replaceAll(",", "").trim();
			String vat = this.vat.trim().length() <= 0 ? "0" : this.vat.replaceAll(",", "").trim();

			String query = 
				"\n  insert ERP_DondathangNPP(tiengiamtru,diengiaigiamtru,ngaydonhang, ngaydenghi, loaidonhang, ghichu, trangthai, dvkd_fk, kbh_fk, npp_fk, npp_dat_fk, kho_fk, chietkhau, vat, ngaytao, nguoitao, ngaysua, nguoisua, NPP_DACHOT,isKM,isdhkhac) " +
				"\n  values("+tiengiamtru+",N'"+diengiaigiamtru+"','" + this.ngayyeucau + "', '" + this.ngaydenghi + "', '" + this.loaidonhang + "', N'" + this.ghichu + "', 0, '" + dvkdId + "', '" + kbhId + "', '" + nppId + "', '" + this.khId + "', " + khonhan_fk + ", '" + chietkhau + "', '" + vat + "', '" + getDateTime() + "', '" + this.userId + "', '" + 
				getDateTime() + "', '" + this.userId + "', '0','"+this.iskm+"',"+this.isdhk+")";

			System.out.println("1.Insert DDH: " + query);
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ERP_DondathangNPP " + query;
				db.getConnection().rollback();
				return false;
			}

			//LAY ID
			ResultSet rsDDH = db.get("select SCOPE_IDENTITY() as ID ");
			if(rsDDH.next())
			{
				this.id = rsDDH.getString("ID");
			}
			rsDDH.close();

			String kbh_fk = this.kbhId;
			query = "select isnull(dungchungkenh,0)dungchungkenh from NHAPHANPHOI where PK_SEQ = '" + this.nppId + "' ";
			ResultSet rs = db.get(query);
			if(rs.next())
			{
				if(rs.getString("dungchungkenh").equals("1"))
					kbh_fk = "100025";
			}

			System.out.println("DDH ID: " + this.id);
			for(int i = 0; i < spMa.length; i++)
			{
				if(spMa[i].trim().length() > 0 && spSCheme[i].trim().length() <= 0 )
				{
					//CHECK SP NAY DA CO KHAI QUY CACH CHUA?
					query =    "select sp.ten, dv.donvi, case when sp.dvdl_fk != dv.pk_seq   " +
					"	then ISNULL( ( select soluong2 / soluong1 from QUYCACH where SANPHAM_FK = sp.PK_SEQ and DVDL1_FK = sp.DVDL_FK and DVDL2_FK = dv.pk_seq ), -1 ) else 1 end as quycach   "  +
					"from SANPHAM sp, DONVIDOLUONG dv " +
					"where sp.MA = '" + spMa[i].trim() + "' and dv.donvi = N'" + spDonvi[i].trim() + "'   ";

					System.out.println("-----CHECK QUY CACH: " + query );
					rs = db.get(query);
					if(rs.next())
					{
						if(rs.getDouble("quycach") <= 0)
						{
							this.msg = "Sản phẩm ( " + rs.getString("ten") + " ) với đơn vị đặt ( " + rs.getString("donvi") + " ) chưa thiết lập quy cách trong DLN ";
							rs.close();
							db.getConnection().rollback();
							return false;
						}
					}
					rs.close();

					String ck = "0";
					if(spChietkhau[i].trim().length() > 0)
						ck = spChietkhau[i].trim().replaceAll(",", "");

					String thueVAT = this.spVAT[i].trim().replaceAll(",", "");
					if(thueVAT.trim().length() < 0)
						thueVAT = "0";

					query = "insert ERP_DondathangNPP_SANPHAM( Dondathang_fk, SANPHAM_FK, soluong, dongia, chietkhau, thueVAT, dvdl_fk, dongiagoc ) " +
					"select '" + this.id + "', pk_seq, '" + spSoluong[i].replaceAll(",", "") + "', '" + spGianhap[i].replaceAll(",", "") + "', '" + ck + "', '" + thueVAT + "', ( select pk_Seq from DONVIDOLUONG where donvi = N'" + spDonvi[i].trim() + "' ), "+ spGiagoc[i].replaceAll(",", "")  +" " +
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

				
			String kqBook = BookKho ("create");
			if(kqBook.trim().length() > 0)
			{
				this.msg =kqBook;
				db.getConnection().rollback();
				return false;
			}
			
			
			
			String kqVat = geso.dms.center.util.Utility.CheckVat( db , "ERP_DondathangNPP_SANPHAM","thueVAT", "Dondathang_fk", this.id );
			if(kqVat.trim().length() > 0)
			{
				this.msg = kqVat;
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
				return false;
			}

			String kqC = CheckDonHang();
			if(kqC.trim().length() > 0)
			{
				this.msg = kqC;
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
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

	public String CheckDonHang() throws SQLException
	{
		String msg = "";
		String query = "\n select * from ERP_DondathangNPP_SANPHAM a  "+
		"\n full outer join   "+
		"\n ( select sanpham_fk, sum(soluong) as soluong from ERP_DondathangNPP_SANPHAM_chitiet where dondathang_fk = '" + this.id	+ "'  group by sanpham_fk ) b   "+
		"\n on a.sanpham_fk = b.sanpham_fk   "+
		"\n where a.dondathang_fk ='" + this.id	+ "'  and a.soluong <> isnull(b.soluong,0)  ";  
		ResultSet rschk = db.get(query);
		while (rschk.next()) {
			msg += "Lỗi:Số lượng kho tổng lệch với kho chi tiết trên đơn hàng!";
		}
		rschk.close();
		return msg ;
	}
	

	public boolean updateNK() 
	{

		String query = "";
		Utility  uilt_kho =new Utility();

		String ngayhoadon_=uilt_kho.getngayhoadon(this.userId, db,this.ngayyeucau,this.khId,1);

		if(this.ngayyeucau.trim().length() < 10)
		{
			this.msg = "Vui lòng nhập ngày nhập kho";
			return false;
		}

		if(this.ngaydenghi.trim().length() < 10)
		{
			this.msg = "Vui lòng nhập ngày đề nghị giao hàng";
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

		if( this.khId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn đối tác đặt hàng";
			return false;
		}

		if( this.khoNhanId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn kho đặt hàng";
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

					if(spGianhap[i].trim().length() <= 0)
					{
						this.msg = "Bạn phải nhập đơn giá của sản phẩm ( " + spTen[i] + " )";
						return false;
					}

					if(spDonvi[i].trim().length() <= 0)
					{
						this.msg = "Bạn phải nhập đơn vị  của sản phẩm ( " + spTen[i] + " )";
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
					if(spMa[i].trim().length() > 0 && spMa[j].trim().length() > 0 )
					{
						if( spMa[i].trim().equals(spMa[j].trim()) )
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

			String khonhan_fk = this.khoNhanId.trim().length() <= 0 ? "null" : this.khoNhanId.trim();
			String chietkhau = this.chietkhau.trim().length() <= 0 ? "0" : this.chietkhau.replaceAll(",", "").trim();
			String vat = this.vat.trim().length() <= 0 ? "0" : this.vat.replaceAll(",", "").trim();

			

			query = "select isnull(dungchungkenh,0)dungchungkenh  from NHAPHANPHOI where PK_SEQ =  (SELECT NPP_FK FROM ERP_DONDATHANGNPP WHERE PK_sEQ="+this.id+" )";
			ResultSet rs = db.get(query);
			boolean dungchungkenh=false;
			if(rs.next())
			{
				if(rs.getString("dungchungkenh").equals("1")){
					dungchungkenh=true;
				}
			}
			rs.close();

			String revertBooked = RevertBooked("update");
			if(revertBooked.trim().length() >0)
			{
				this.msg = revertBooked;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete ERP_DondathangNPP_SANPHAM where Dondathang_fk = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_DondathangNPP_SANPHAM " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete ERP_DondathangNPP_SANPHAM_chitiet where Dondathang_fk = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_DondathangNPP_SANPHAM " + query;
				db.getConnection().rollback();
				return false;
			}


			// PHAI CẬP NHẬT KHO SAU TRONG TRƯỜNG HỢP ĐỔI KHO KHÁC
			query =	"\n Update ERP_DondathangNPP set tiengiamtru="+tiengiamtru+",diengiaigiamtru=N'"+diengiaigiamtru+"', trangthai=0," +
					"\n ngaydonhang = '" + this.ngayyeucau + "', ngaydenghi = '" + this.ngaydenghi + "', ghichu = N'" + this.ghichu + "', " +
					"\n  	dvkd_fk = '" + this.dvkdId + "', kbh_fk = '" + this.kbhId + "', npp_fk = '" +this.nppId + "', " +
					"\n kho_fk = " + khonhan_fk + ", ngaysua = '" + getDateTime() +"', " +
					"\n nguoisua = '" + this.userId + "', chietkhau = '" + chietkhau + "', vat = '"+vat + "'," +
					"\n isKM='"+this.iskm+"',isdhkhac="+this.isdhk+" "+
					"\n where trangthai = 0 and pk_seq = '" + this.id + "' ";

			if(db.updateReturnInt(query) <= 0)
			{
				this.msg = "Không thể cập nhật ERP_DondathangNPP " + query;
				db.getConnection().rollback();
				return false;
			}


			String kbh_fk = this.kbhId;
			query = "select isnull(dungchungkenh,0)dungchungkenh from NHAPHANPHOI where PK_SEQ = '" + this.nppId + "' ";
			rs = db.get(query);
			if(rs.next())
			{
				if(rs.getString("dungchungkenh").equals("1"))
					kbh_fk = "100025";
			}

			for(int i = 0; i < spMa.length; i++)
			{
				if(spMa[i].trim().length() > 0 && spSCheme[i].trim().length() <= 0 )
				{
					//CHECK SP NAY DA CO KHAI QUY CACH CHUA?
					query =    "select sp.ten, dv.donvi, case when sp.dvdl_fk != dv.pk_seq   " +
					"	then ISNULL( ( select soluong2 / soluong1 from QUYCACH where SANPHAM_FK = sp.PK_SEQ and DVDL1_FK = sp.DVDL_FK and DVDL2_FK = dv.pk_seq ), -1 ) else 1 end as quycach   "  +
					"from SANPHAM sp, DONVIDOLUONG dv " +
					"where sp.MA = '" + spMa[i].trim() + "' and dv.donvi = N'" + spDonvi[i].trim() + "'   ";

					System.out.println("-----CHECK QUY CACH: " + query );
					rs = db.get(query);
					if(rs.next())
					{
						if(rs.getDouble("quycach") <= 0)
						{
							this.msg = "Sản phẩm ( " + rs.getString("ten") + " ) với đơn vị đặt ( " + rs.getString("donvi") + " ) chưa thiết lập quy cách trong DLN ";
							rs.close();
							db.getConnection().rollback();
							return false;
						}
					}
					rs.close();


					String ck = "0";
					if(spChietkhau[i].trim().length() > 0)
						ck = spChietkhau[i].trim().replaceAll(",", "");

					String thueVAT = this.spVAT[i].trim().replaceAll(",", "");
					if(thueVAT.trim().length() < 0)
						thueVAT = "0";

					query = "insert ERP_DondathangNPP_SANPHAM( Dondathang_fk, SANPHAM_FK, soluong, dongia, chietkhau, thueVAT, dvdl_fk, dongiagoc ) " +
					"select '" + this.id + "', pk_seq, '" + spSoluong[i].replaceAll(",", "") + "', '" + spGianhap[i].replaceAll(",", "") + "', '" + ck + "', '" + thueVAT + "', ( select pk_Seq from DONVIDOLUONG where donvi = N'" + spDonvi[i].trim() + "' ), "+ spGiagoc[i].replaceAll(",", "")  +" " +
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
			
			String kqBook = BookKho ("update");
			if(kqBook.trim().length() > 0)
			{
				this.msg =kqBook;
				db.getConnection().rollback();
				return false;
			}
			
			String kqVat = geso.dms.center.util.Utility.CheckVat( db , "ERP_DondathangNPP_SANPHAM","thueVAT", "Dondathang_fk", this.id );
			if(kqVat.trim().length() > 0)
			{
				this.msg = kqVat;
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
				return false;
			}
			
			String kqC = CheckDonHang();
			if(kqC.trim().length() > 0)
			{
				this.msg = kqC;
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
				return false;
			}
			

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			this.msg = "Exception: " + e.getMessage() + " ---" + query;
			return false;
		}

		return true;
	}


	public void createRs() 
	{
		this.getNppInfo();

		this.khoNhanRs = db.get("select PK_SEQ, TEN from KHO where trangthai = '1' and pk_seq = 100000 "  );
		if(this.khoNhanId.trim().length() <= 0)
		{
			this.khoNhanId = "100000";
				
		}

		this.dvtRs = db.getScrol("select PK_SEQ, DONVI from DONVIDOLUONG where trangthai = '1' ");

		this.dvkdRs = db.get("select PK_SEQ, DONVIKINHDOANH as TEN from DONVIKINHDOANH where TRANGTHAI = '1' ");
		this.kbhRs = db.get("select PK_SEQ, DIENGIAI as TEN from KENHBANHANG where TRANGTHAI = '1' and PK_SEQ in ( select KBH_FK from NHAPP_KBH where NPP_FK = '" + this.nppId + "' ) ");

		String query = "select PK_SEQ, ISNULL(ma, '') + ' - ' + TEN as TEN from NHAPHANPHOI where TRANGTHAI = '1' and pk_seq !=  "+ this.nppId;
		this.khRs = db.get(query);

		if(this.nppId.trim().length() > 0)
		{
			query = "select a.PK_SEQ as nppId, d.DVKD_FK, b.KBH_FK  " +
			"From NhaPhanPhoi a left join nhapp_kbh b on b.NPP_FK = a.PK_SEQ left join NHAPP_NHACC_DONVIKD c on c.NPP_FK = b.NPP_FK  " +
			"	left join NHACUNGCAP_DVKD d on d.PK_SEQ = c.NCC_DVKD_FK   " +
			"where a.pk_Seq = '" + this.nppId + "' ";
			ResultSet rsInfo = db.get(query);
			if(rsInfo != null)
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

	}

	private void initSANPHAM() 
	{

		String query = "select b.MA,(select kho.available from nhapp_kho kho where kho.sanpham_fk=b.pk_seq and kho.KHO_FK= " + this.getKhoNhapId() + " and NPP_FK in(select NPP_FK from ERP_DONDATHANGNPP where  PK_SEQ=a.dondathang_fk) and kho.KBH_FK="
		+ (this.dungchungKENH.equals("1") ? "100025" : this.kbhId) + " )as soluongton," +
		"(select kho.soluong from nhapp_kho kho where kho.sanpham_fk=b.pk_seq and kho.KHO_FK= 100000 and NPP_FK in(select NPP_DAT_FK from ERP_DONDATHANGNPP where  PK_SEQ=a.dondathang_fk) and kho.KBH_FK="
		+ (this.dungchungKENH.equals("1") ? "100025" : this.kbhId) + " )as tonkhocn, "+
		" b.TEN, DV.donvi, a.soluong, a.dongia, a.chietkhau, a.thueVAT, isnull(a.dongiagoc,0) as dongiagoc   " +
		" from ERP_DondathangNPP_SANPHAM a inner Join SanPham b on a.SANPHAM_FK = b.PK_SEQ    " +
		" 		INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ = a.DVDL_FK       " +
		"where a.Dondathang_FK = '" + this.id + "' ";

		System.out.println("--INIT SP: " + query);
		ResultSet spRs = db.get(query);

		NumberFormat formater = new DecimalFormat("##,###,###");
		if(spRs != null)
		{
			try 
			{
				String spMA = "";
				String spTEN = "";
				String spDONVI = "";
				String spSOLUONG = "";
				String spGIANHAP = "";
				String spGIAGOC = "";
				String spCHIETKHAU = "";
				String spVAT = "";
				String spSCHEME = "";
				String spSOLUONGTON = "";
				String spTonkho="";
				while(spRs.next())
				{
					spMA += spRs.getString("MA") + "__";
					spTEN += spRs.getString("TEN") + "__";
					spDONVI += spRs.getString("DONVI") + "__";
					spSOLUONG += formater.format(spRs.getDouble("SOLUONG")) + "__";
					spGIANHAP += spRs.getDouble("DONGIA") + "__";
					spGIAGOC += spRs.getDouble("DONGIAGOC") + "__";
					spCHIETKHAU += formater.format(spRs.getDouble("chietkhau")) + "__";
					spVAT += formater.format(spRs.getDouble("thueVAT")) + "__";
					spSOLUONGTON += formater.format(spRs.getDouble("soluongton")) + "__";
					spTonkho += formater.format(spRs.getDouble("tonkhocn")) + "__";
					spSCHEME += " __";
				}
				spRs.close();

				//INIT SP KHUYEN MAI
				/*query = "select isnull(b.MA, ' ') as MA, isnull(b.TEN, ' ') as TEN, isnull(c.DONVI, ' ') as donvi, d.SCHEME, isnull(a.soluong, 0) as soluong, a.tonggiatri " +
						"from ERP_DONDATHANG_CTKM_TRAKM a left join SANPHAM b on a.SPMA = b.MA  " +
						"	left join DONVIDOLUONG c on b.DVDL_FK = c.PK_SEQ  " +
						"	inner join CTKHUYENMAI d on a.ctkmID = d.PK_SEQ " +
						"where a.dondathangID = '" + this.id + "' ";
				System.out.println("--INIT SPKM: " + query);

				spRs = db.get(query);
				while(spRs.next())
				{
					spMA += spRs.getString("MA") + "__";
					spTEN += spRs.getString("TEN") + "__";
					spDONVI += spRs.getString("DONVI") + "__";
					spSOLUONG += formater.format(spRs.getDouble("SOLUONG")) + "__";
					spGIANHAP += formater.format(spRs.getDouble("tonggiatri")) + "__";
					spSCHEME += spRs.getString("SCHEME") + "__";
				}
				spRs.close();*/

				//System.out.println("---SCHEME: " + spSCHEME );
				//System.out.println("---DON GIA: " + spGIANHAP );

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

					spGIAGOC = spGIAGOC.substring(0, spGIAGOC.length() - 2);
					this.spGiagoc = spGIAGOC.split("__");

					spCHIETKHAU = spCHIETKHAU.substring(0, spCHIETKHAU.length() - 2);
					this.spChietkhau = spCHIETKHAU.split("__");

					System.out.println("---VAT LA::::::" + spVAT);
					spVAT = spVAT.substring(0, spVAT.length() - 2);
					this.spVAT = spVAT.split("__");

					spSCHEME = spSCHEME.substring(0, spSCHEME.length() - 2);
					this.spSCheme = spSCHEME.split("__");

					spSOLUONGTON = spSOLUONGTON.substring(0, spSOLUONGTON.length() - 2);
					this.spSoluongton = spSOLUONGTON.split("__");


					spTonkho = spTonkho.substring(0, spTonkho.length() - 2);
					this.sptonkhocn = spTonkho.split("__");


				}

			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				System.out.println("115.Exception: " + e.getMessage());
			}
		}
	}

	public void init() 
	{

		String query = "\n select isnull(a.tiengiamtru,0)tiengiamtru,isnull(a.diengiaigiamtru,'')diengiaigiamtru,isnull(isdhkhac,0)isdhkhac," +
				"\n ngaydonhang, ngaydenghi, ISNULL(ghichu, '') as ghichu, dvkd_fk, kbh_fk, npp_fk, kho_fk, " +
				"\n chietkhau, vat, loaidonhang, npp_dat_fk, isnull( ( select dungchungkenh from NHAPHANPHOI where pk_seq = a.npp_fk ), 0 ) as dungchungkenh," +
				"\n trangthai, DATEDIFF (day, ngaytao, '2015-01-29') as CL,isnull(a.iskm,0) as iskm " +
				"\n from ERP_DondathangNPP a where pk_seq = '" + this.id + "'";
		System.out.println("____INIT NHAP KHO: " + query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				if(rs.next())
				{
					this.tiengiamtru = rs.getString("tiengiamtru");
					this.diengiaigiamtru = rs.getString("diengiaigiamtru");
					this.ngayyeucau = rs.getString("ngaydonhang");
					this.ngaydenghi = rs.getString("ngaydenghi");
					this.ghichu = rs.getString("ghichu");
					this.dvkdId = rs.getString("dvkd_fk");
					this.kbhId = rs.getString("kbh_fk");
					this.khId = rs.getString("npp_dat_fk");
					this.khoNhanId = rs.getString("kho_fk");
					this.loaidonhang = rs.getString("loaidonhang");
					this.chietkhau = rs.getString("chietkhau");
					this.vat = rs.getString("vat");
					this.dungchungKENH = rs.getString("dungchungkenh");
					this.trangthai = rs.getString("trangthai");
					this.ngay_Chenhlech = rs.getInt("CL");
					this.iskm = rs.getString("iskm")==null?"0":rs.getString("iskm");
					this.isdhk=rs.getString("isdhkhac");

				}
				rs.close();
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				System.out.println("---LOI INIT: " + e.getMessage());
			}
		}
		this.initSANPHAM();
		this.createRs();




	
		
	}

	public void DBclose() {

		try{

			if(khoNhanRs!=null){
				khoNhanRs.close();
			}

			this.db.shutDown();

		}catch(Exception er)
		{
			er.printStackTrace();

		}
	}

	private String getDateTime() 
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);	
	}

	private String getDateTime1() 
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss.SS");
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

	public ResultSet getDvtRs() {

		return this.dvtRs;
	}


	public void setDvtRs(ResultSet dvtRs) {

		this.dvtRs = dvtRs;
	}

	public String[] getSpScheme() {

		return this.spSCheme;
	}


	public void setSpScheme(String[] spScheme) {

		this.spSCheme = spScheme;
	}

	public ResultSet getCongnoRs() {

		return this.congnoRs;
	}


	public void setCongnoRs(ResultSet congnoRs) {

		this.congnoRs = congnoRs;
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


	public String[] getSpChietkhau() {

		return this.spChietkhau;
	}


	public void setSpChietkhau(String[] spChietkhau) {

		this.spChietkhau = spChietkhau;
	}

	public String[] getSpVat() {

		return this.spVAT;
	}


	public void setSpVat(String[] spVat) {

		this.spVAT = spVat;
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


	public String getKhId() {

		return this.khId;
	}


	public void setKhId(String khId) {

		this.khId = khId;
	}


	public ResultSet getKhRs() {

		return this.khRs;
	}


	public void setKhRs(ResultSet khRs) {

		this.khRs = khRs;
	}

	public boolean duyetDH() 
	{	
		Utility  uilt_kho = new Utility();
		String ngayhoadon_ = uilt_kho.getngayhoadon(this.userId, db,this.ngayyeucau,this.khId,1);

		try
		{
			db.update("SET TRANSACTION ISOLATION LEVEL SNAPSHOT;");
			db.update("SET LOCK_TIMEOUT 60000;");
			db.getConnection().setAutoCommit(false);

			//NEU CO DOI NGAY THI GHI NHAN LAI
			String query = "\n Update ERP_DondathangNPP set ngaydonhang = '" + this.ngayyeucau +  "', ngaydenghi = '" + this.ngaydenghi + "', ghichu = N'" + this.ghichu + "' " +
			"\n where pk_seq = '" + this.id + "' and TrangThai!=4 ";

			if(db.updateReturnInt(query)!=1)
			{
				this.msg = "Lỗi khi duyệt: " + query;
				db.getConnection().rollback();
				return false;
			}

			msg= Utility.Check_Huy_NghiepVu_KhoaSo("ERP_DondathangNPP", id, "ngaydonhang", db);
			if(msg.length()>0)
			{
				db.getConnection().rollback();
				return false;
			}

			query = "select isnull(dungchungkenh,0)dungchungkenh from NHAPHANPHOI where PK_SEQ =  (SELECT NPP_FK FROM ERP_DONDATHANGNPP WHERE PK_sEQ="+this.id+" )";
			ResultSet rs = db.get(query);
			boolean dungchungkenh=false;
			if(rs.next())
			{
				if(rs.getString("dungchungkenh").equals("1")){
					dungchungkenh=true;
				}
			}
			rs.close();

			// CAP NHAT LAI LÔ
			query=	"\n  SELECT KHOXUAT_FK, NPP_FK,   "+(dungchungkenh?"100025":" kbh_fk")+ " as  KBH_FK, SANPHAM_FK, SUM(SOLUONG) AS SOLUONG   ,SOLO, NGAYHETHAN, NGAYNHAPKHO "+      
			"\n  FROM "+
			"\n  ( "+
			"\n  SELECT C.KHO_FK AS KHOXUAT_FK, C.NPP_FK,   KBH_FK, A.SANPHAM_FK, A.SOLO,A.NGAYHETHAN,A.NGAYNHAPKHO, "+     
			"\n  CASE WHEN A.DVDL_FK IS NULL THEN A.SOLUONG       "+
			"\n  WHEN A.DVDL_FK = B.DVDL_FK THEN A.SOLUONG      "+
			"\n  ELSE  A.SOLUONG * ( SELECT SOLUONG1 / SOLUONG2 FROM QUYCACH "+ 
			"\n  WHERE SANPHAM_FK = A.SANPHAM_FK AND DVDL2_FK = A.DVDL_FK AND DVDL1_FK = B.DVDL_FK )  END AS SOLUONG "+   
			"\n  FROM ERP_DONDATHANGNPP_SANPHAM_CHITIET A INNER JOIN SANPHAM B ON A.SANPHAM_FK = B.PK_SEQ   "+
			"\n  INNER JOIN ERP_DONDATHANGNPP C ON A.DONDATHANG_FK = C.PK_SEQ     "+
			"\n  WHERE A.DONDATHANG_FK IN (  "+this.id+" ) AND A.SOLUONG > 0 "+
			"\n  ) "+
			"\n  DATHANG "+ 
			"\n  GROUP BY KHOXUAT_FK, NPP_FK, "+(dungchungkenh?"":" kbh_fk,")+ " SANPHAM_FK ,SOLO, NGAYHETHAN, NGAYNHAPKHO    ";

			ResultSet rskho=db.get(query);
			while(rskho.next())
			{
				String   _khoxuat_fk, _npp_fk, _kbh_fk, _sanpham_fk,_solo,_ngayhethan,_ngaynhapkho ;
				_khoxuat_fk=rskho.getString("khoxuat_fk");
				_npp_fk=rskho.getString("npp_fk");
				_kbh_fk=rskho.getString("kbh_fk");
				_sanpham_fk=rskho.getString("sanpham_fk"); 
				_solo= rskho.getString("SOLO");
				_ngayhethan= rskho.getString("ngayhethan");
				_ngaynhapkho= rskho.getString("ngaynhapkho");

				double soluongct_ =rskho.getDouble("SOLUONG");

				/*String msg1=uilt_kho.Update_NPP_Kho_Sp_Chitiet( this.ngayyeucau,"Cập nhật đơn hàng đối tác :erpdondathangDoitacSvl 372" 
						, db, _khoxuat_fk, _sanpham_fk, _npp_fk, _kbh_fk, _solo, _ngayhethan, _ngaynhapkho, 0, (-1)*soluongct_, soluongct_, soluongct_, 0);
				if(msg1.length() >0){
					this.msg=msg1;
					db.getConnection().rollback();
					return false;
				}*/
			}
			rskho.close();

			query = "delete ERP_DONDATHANGNPP_SANPHAM_CHITIET where dondathang_fk = '" + this.id + "' ";
			if(!db.update(query))
			{
				this.msg = "Lỗi khi duyệt: " + query;
				db.getConnection().rollback();
				return false;
			}

			query = "SELECT NPP_FK  ,Kho_FK,kbh_fk  FROM ERP_DONDATHANGNPP where pk_Seq="+this.id;
			ResultSet rs1=db.get(query);
			String _npp_fk="",_khoxuat_fk="",_kbh_fk="";
			if(rs1.next()){
				_npp_fk=rs1.getString("NPP_FK");
				_khoxuat_fk=rs1.getString("Kho_FK");
				_kbh_fk=rs1.getString("kbh_fk");
			}
			if(dungchungkenh){
				_kbh_fk="100025";
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

								query = "insert ERP_DONDATHANGNPP_SANPHAM_CHITIET( dondathang_fk, SANPHAM_FK, dvdl_fk, solo, soluong, ngayhethan,NGAYNHAPKHO )  " +
								"select '" + this.id + "', pk_seq, ( select dvdl_fk from ERP_DONDATHANGNPP_SANPHAM where dondathang_fk = '" + this.id + "' and sanpham_fk = a.pk_seq ),  N'" + _sp[1] + "' as solo, '" + _soluongCT.replaceAll(",", "") + "' as soluong, '"+_sp[2]+"' as NgayHetHan   " +
								" , '"+_sp[3]+"' as ngaynhapkho from SANPHAM a where MA = '" + spMa[i] + "'  ";

								System.out.println("1.2.Insert DDH - SP - CT: " + query);
								if(!db.update(query))
								{
									this.msg = "Khong the tao moi ERP_DONDATHANGNPP_SANPHAM_CHITIET: " + query;
									db.getConnection().rollback();
									return false;
								}

								// cập nhật lại kho chi tiet quy đổi lại số lượng chuẩn để cập nhật kho 
								double soluong_quydoi=Double.parseDouble(_soluongCT);
								query=  " SELECT PK_sEQ, ( select dvdl_fk from ERP_DONDATHANGNPP_SANPHAM where dondathang_fk = '" + this.id + "' " +
								" and sanpham_fk = SP.pk_seq ) AS DONVI_DONHANG,SP.DVDL_FK  FROM SANPHAM SP WHERE MA='"+spMa[i]+"'";

								ResultSet rssp=db.get(query);
								String spid_="";
								if(rssp.next()){
									spid_=rssp.getString("PK_sEQ");
									if(!rssp.getString("DONVI_DONHANG").equals(rssp.getString("DVDL_FK"))){
										// nếu khác 
										query=" SELECT qc.SOLUONG1,qc.SOLUONG2 FROM QUYCACH qc WHERE SANPHAM_FK="+spid_+" AND DVDL1_FK="+rssp.getString("DVDL_FK")+"  and qc.DVDL2_FK="+rssp.getString("DONVI_DONHANG");
										ResultSet rsqc=db.get(query);
										if(rsqc.next()){
											soluong_quydoi = Double.parseDouble(_soluongCT) * rsqc.getDouble("SOLUONG1")/ rsqc.getDouble("SOLUONG2");

										}else{
											this.msg="Không thể xác định quy đổi của sản phẩm : "+rs.getString("ten");;
											db.getConnection().rollback();
											return false;
										}
									}

								}else{
									this.msg="Không thể xác định quy đổi của sản phẩm : "+rs.getString("ten");;
									db.getConnection().rollback();
									return false;
								}
								rssp.close();

							/*	String msg1 = uilt_kho.Update_NPP_Kho_Sp_Chitiet(ngayhoadon_, "Sửa số lô trên đuyệt đơn hàng " 
										, db, _khoxuat_fk, spid_, _npp_fk,_kbh_fk ,  _sp[1] , _sp[2], _sp[3], 0, soluong_quydoi, (-1)* soluong_quydoi, (-1)* soluong_quydoi, 0);

								if(msg1.length() >0){
									this.msg=msg1;
									db.getConnection().rollback();
									return false;
								}*/
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
			query = "\n select count(*) as soDONG   " +
			"\n from ERP_DONDATHANGNPP_SANPHAM tong left join   " +
			"\n 	(  " +
			"\n 		select sanpham_fk, sum(soluong) as soluong   " +
			"\n 		from ERP_DONDATHANGNPP_SANPHAM_CHITIET  " +
			"\n 		where  dondathang_fk = '" + this.id + "'  " +
			"\n 		group by sanpham_fk " +
			"\n 	)  " +
			"\n 	CT on tong.sanpham_fk = CT.sanpham_fk " +
			"\n where dondathang_fk = '" + this.id + "' and tong.soluong != isnull(CT.soluong, 0)  " ;
			ResultSet rsCHECK = db.get(query);
			int soDONG = 0;
			if(rsCHECK != null )
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
				this.msg = "11.Lỗi hệ thống ( tổng xuất theo lô đề xuất đang bị sai ). Vui lòng liên hệ trung tâm để được hỗ trợ xử lý.";
				return false;
			}

			query = "\n select khachhang_fk, a.kbh_fk, a.npp_fk, a.npp_dat_fk, " +
			"\n ( select priandsecond from NHAPHANPHOI where pk_seq = a.npp_fk ) as tuxuatOTC,  " +
			"\n ( select tuxuatETC from NHAPHANPHOI where pk_seq = a.npp_fk ) as tuxuatETC,  " +
			"\n ( select loaiNPP from NHAPHANPHOI where pk_seq = a.npp_fk ) as loaiNPP, " +
			"\n ( select tructhuoc_fk from NHAPHANPHOI where pk_seq = a.npp_fk ) as tructhuoc_fk,  " +
			"\n  ISNULL( ( select dungchungkenh from NHAPHANPHOI where pk_seq = a.npp_fk ), 0 ) as dungchungkenh, a.kho_fk, a.ngaydonhang  " +
			"\n from ERP_DondathangNPP a where a.pk_seq = '" + id + "' order by pk_seq desc";
			String khachhangID = "";
			String loaiNPP = "";
			String tructhuoc = "";
			String nppId = "";
			String npp_dat_fk = "";
			String kbh_fk = "";
			String khonhanID = "";
			String ngaydonhang = "";

			String tuquanlyKHO_OTC = "0";
			String tuquanlyKHO_ETC = "0";

			rs = db.get(query);
			if(rs != null)
			{
				if(rs.next())
				{
					ngaydonhang = rs.getString("ngaydonhang");
					if(rs.getString("khachhang_fk") != null)
						khachhangID = rs.getString("khachhang_fk");

					loaiNPP = rs.getString("loaiNPP");
					tructhuoc = rs.getString("tructhuoc_fk");
					nppId = rs.getString("npp_fk");

					if(rs.getString("npp_dat_fk") != null)
						npp_dat_fk = rs.getString("npp_dat_fk");

					if(rs.getString("dungchungkenh").equals("1"))
						kbh_fk = "100025";
					else
						kbh_fk = rs.getString("kbh_fk");

					if(rs.getString("tuxuatOTC") != null)
						tuquanlyKHO_OTC = rs.getString("tuxuatOTC");

					if(rs.getString("tuxuatETC") != null)
						tuquanlyKHO_ETC = rs.getString("tuxuatETC");

					khonhanID = rs.getString("kho_fk");
				}
				rs.close();
			}


			query = "\n update ERP_DondathangNPP set trangthai = '4', NPP_DACHOT = '1', nguoisua = '" + userId + "', ngaygiochot='"+getDateTime1() +"' where pk_seq = '" + id + "'";
			if(!db.update(query))
			{
				msg = "Khong the chot: " + query;
				db.getConnection().rollback();
				return false;
			}

			// PHAI HUY DON DUOI Đối tác trực thuộc đặt lên (trường hợp không phải tự tao mới)
			query = "\n update ERP_Dondathang set trangthai = '4' where pk_seq = ( select from_dondathang from ERP_DondathangNPP where pk_seq = '" + id + "' ) ";
			if(!db.update(query))
			{
				msg = "1.Khong the chot: " + query;
				db.getConnection().rollback();
				return false;
			}

			// Tu dong tao Hoa don tai chinh cho NPP					
			msg = this.TaoHoaDonTaiChinhNPP(db, id, userId, nppId, npp_dat_fk, ngaydonhang);
			if(msg.trim().length() > 0)
			{
				msg = "Khong the tao hoa don tai chinh: " + msg;
				db.getConnection().rollback();
				return false;
			}

			msg= util.Check_Kho_Tong_VS_KhoCT(nppId, db);
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

	private String TaoHoaDonTaiChinhNPP(dbutils db, String id, String userId, String nppId, String npp_dat_fk, String ngaydonhang) 
	{
		String msg1 = "";
		try
		{
			String query = "";

			query =" update NHANVIEN SET Active = '1' where pk_seq='"+userId+"'";
			if(!db.update(query))
			{
				msg1 = "Không thể cập nhật thông tin NHANVIEN " + query;
				return msg1;
			}

			//Lấy thông tin hóa đơn
			String kyhieuhoadon = "";
			String sohoadon = "";
			String ngayhoadon = "";
		/*	String[] info = util.LayThongTinSHD(db, userId, nppId, ngaydonhang, "");
			if( info.length < 3 )
				return info[0];

			kyhieuhoadon = info[0];
			//sohoadon = info[1];
			ngayhoadon = info[2];
*/
			// LAY TIEN DE LUU
			double tienck= 0;
			double tienbvat= 0;
			double tienavat= 0;
			String nguoimua ="";

			query = "\n select (case when dh.khachhang_fk is not null then " +
					"\n                               (select isnull(chucuahieu,'') from KHACHHANG where pk_seq = dh.khachhang_fk ) " +
					"\n             else '' end ) as nguoimua  ," +
					"\n        dh_sp.chietkhau, dh_sp.bvat , (dh_sp.bvat + dh.Vat) as AVAT "+
					"\n from ERP_DONDATHANGNPP dh inner join  "+
					"\n	(select a.dondathang_fk, SUM(a.chietkhau)as chietkhau , sum(a.soluong * a.dongia - a.chietkhau) as bvat "+
					"\n	from ERP_DONDATHANGNPP_SANPHAM a   "+
					"\n	group by  a.dondathang_fk ) dh_sp on dh.PK_SEQ = dh_sp.dondathang_fk "+
					"\n where dh.PK_SEQ = "+ id +"  ";

			ResultSet rsLayTien = db.get(query);
			if(rsLayTien!= null)
			{
				while(rsLayTien.next())
				{
					tienck = rsLayTien.getDouble("chietkhau");
					tienbvat = rsLayTien.getDouble("bvat");
					tienavat = rsLayTien.getDouble("avat");
					nguoimua =  rsLayTien.getString("nguoimua");

				}rsLayTien.close();
			}

			// CN TẠI HÀ NỘI DÙNG MẪU 2 (HO)
			String mau = "1";
/*			if(nppId.equals("100002")) mau = "2";	*/		

			query = "\n  insert ERP_HOADONNPP(tiengiamtru,diengiaigiamtru,DDKD_fK,KBH_FK,KHO_fK,nguoimua ,ngayxuatHD, trangthai, ngaytao, nguoitao, ngaysua, nguoisua, kyhieu, sohoadon, hinhthuctt ," +
			"\n         chietkhau, tongtienbvat, tongtienavat, vat, ghichu, loaixuathd, npp_fk,  npp_dat_fk, mauhoadon,TENKHACHHANG,DIACHI,MASOTHUE,LoaiHoaDon,loaihd ) " +
			"\n  SELECT isnull(dh.tiengiamtru,0)tiengiamtru,isnull(dh.diengiaigiamtru,'')diengiaigiamtru,DH.ddkd_Fk, DH.KBH_FK, DH.kho_Fk , N'" + nguoimua + "', '" + ngayhoadon + "', '1','" + getDateTime() + "', '" + userId + "', '" + getDateTime() + "', '" + userId + "', '" + kyhieuhoadon + "'," +
			"\n        '" + sohoadon + "', N'TM/CK' , '"+ tienck  +"', '"+ tienbvat +"', '"+ tienavat  +"'," +
			"\n  '" + this.vat.replaceAll(",", "") + "', N'Phiếu xuất hóa đơn từ động từ đơn hàng " + id + " ', '0', '" + nppId + "', " + npp_dat_fk + ", " + mau + "" +
			"\n  , NPP.ten as nppMua " +
			"\n  , ISNULL(NPP.DIACHI,'')  as diachinpp " +
			"\n  , ISNULL(NPP.MASOTHUE,'')  as mst,case when dh.iskm=1 then 1 else 0 end as LoaiHoaDon "+
			"\n , isnull(DH.isdhkhac,0)"+
			"\n  FROM Erp_DonDatHangNPP DH INNER JOIN NHAPHANPHOI NPP ON DH.NPP_DAT_FK = NPP.PK_SEQ "+
			"\n  WHERE DH.PK_SEQ = '"+id+"' " ;

			System.out.println("1.Insert ERP_HOADONNPP: " + query);
			if(db.updateReturnInt(query) <= 0 )
			{
				msg1 = "Không thể tạo mới ERP_HOADONNPP " + query;
				return msg1;
			}		

			String hdId = "";
			query = "select scope_identity() as hdId";
			ResultSet rs1 = db.get(query);
			rs1.next();
			hdId = rs1.getString("hdId");
			rs1.close();

			query = "\n insert ERP_HOADONNPP_SP( hoadon_fk, sanpham_fk, sanphamTEN, donvitinh, soluong, dongia, thanhtien, chietkhau, scheme , vat,soluong_chuan,dongia_chuan) " +
			"\n  select "+ hdId +", "+
			"\n 	b.PK_SEQ, a.sanphamTEN, DV.donvi, SUM( a.soluong), a.dongia, SUM( a.soluong) * a.dongia ,SUM( isnull(a.chietkhau, 0)), "+
			"\n   	isnull(scheme,' ') , isnull(a.thuevat,0) as vat ,	a.soluong * dbo.LayQuyCach( a.SANPHAM_FK, null, a.DVDL_FK ) as SoLuong_Chuan,  \n" + 
			"\n   	a.dongia * dbo.LayQuyCach_DVBan( a.SANPHAM_FK, null, a.DVDL_FK ) as DonGia_Chuan  \n" +
			"\n from ERP_DONDATHANGNPP_SANPHAM a inner Join SanPham b on a.SANPHAM_FK = b.PK_SEQ   "+  	 
			"\n  INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ = a.DVDL_FK  " +
			"\n  inner join  ERP_DONDATHANGNPP c on a.dondathang_fk=c.pk_seq    "+
			"\n where a.dondathang_fk in ( "+ id +" ) and a.dondathang_fk in (select pk_seq from ERP_DONDATHANGNPP where NPP_FK="+ nppId +") and a.soluong > 0  " +
			"\n group by b.PK_SEQ , a.sanphamTEN, DV.donvi, a.dongia , isnull(scheme,' ') , isnull(a.thuevat,0),a.soluong ,a.dvdl_fk,a.sanpham_fk  ";

			System.out.println("1.1.Insert ERP_HOADONNPP_SP: " + query);
			if(db.updateReturnInt(query) <= 0 )
			{
				msg1 = "Khong the tao moi ERP_HOADONNPP_SP: " + query;
				return msg1;
			}

			query = "Insert ERP_HOADONNPP_DDH(hoadonnpp_fk, ddh_fk) " +
			" values( "+ hdId +",  " + id + "  )";
			if(db.updateReturnInt(query) <= 0 )
			{
				msg1 = "Không thể tạo mới ERP_HOADONNPP_DDH " + query;
				return msg1;
			}
			
			query = "\n   insert ERP_HOADONNPP_SP_CHITIET(hoadon_fk, donhang_fk, KBH_FK, Kho_FK, MA, TEN, DONVI, DVCHUAN, DVDATHANG, SOLUONG, SoLuong_Chuan, SoLuong_DatHang, SOLO, NGAYHETHAN, NGAYNHAPKHO, CHIETKHAU, THUEVAT, DONGIA, DonGia_Chuan) " + 
			"\n   select '" + hdId + "', a.DonDatHang_FK, c.KBH_FK, c.Kho_FK, b.MA, b.TEN, d.DONVI, b.DVDL_FK as dvChuan, a.Dvdl_Fk as dvDathang,  " + 
			"\n   			a.SoLuong, a.SoLuong * dbo.LayQuyCach(a.sanpham_fk, null, e.dvdl_fk) as soluongChuan, a.SoLuong as SoLuong_DatHang,  " + 
			"\n   			a.SoLo, a.NgayHetHan, a.ngaynhapkho, e.chietkhau as chietkhau, e.thueVAT as THUEVAT, e.dongia as DONGIA, e.dongia * dbo.LayQuyCach_DVBan( a.SanPham_fk, null, e.Dvdl_Fk ) as DonGia_Chuan " + 
			"\n   from ERP_DONDATHANGNPP_SANPHAM_CHITIET a inner join SANPHAM b on a.SanPham_fk = b.PK_SEQ " + 
			"\n   		inner join ERP_DONDATHANGNPP c on a.DonDatHang_FK = c.PK_SEQ " + 
			"\n   		left join DONVIDOLUONG d on a.Dvdl_Fk = d.PK_SEQ " + 
			"\n   		inner join ERP_DONDATHANGNPP_SANPHAM e on a.dondathang_fk = e.dondathang_fk and a.sanpham_fk = e.sanpham_fk " + 
			"\n   where a.DonDatHang_FK = '" + this.id + "' and a.Scheme = '' ";

			if(db.updateReturnInt(query) <= 0 )
			{
				msg1 = "Không thể tạo mới ERP_HOADONNPP_SP_CHITIET " + query;
				return msg1;
			}

			//LUU VAO BANG CHI TIET

			//CAP NHAT LAI CAC COT TIEN CUA ETC, SAU NAY IN RA THI CHI IN TU DAY
			/*query = "\n update hd set hd.tongtienbvat = round(giatri.bVAT,0), hd.chietkhau = round(giatri.chietkhau,0),   " +
			"\n 			hd.vat = round(giatri.vat,0), hd.tongtienavat = round(giatri.avat,0)  " +
			"\n from ERP_HOADONNPP hd inner join  " +
			"\n (  " +
			"\n 	select hoadonnpp_fk, sum(bVAT) as bVAT, sum(chietkhau) as chietkhau, (sum(bVAT) - sum(chietkhau)) * ((VAT/100.0)) as VAT, " +
			"\n 			(sum(bVAT) - sum(chietkhau)) * (1+(VAT/100.0)) as aVAT  " +
			"\n 	from  " +
			"\n 	(  " +
			"\n 		select a.hoadonnpp_fk,   " +
			"\n 	cast( floor(b.soluong * b.dongia) as numeric(18, 0) )  as bVAT, isnull(b.chietkhau, 0) as chietkhau,    " +
			"\n 	thuevat as VAT " +
			"\n 		from ERP_HOADONNPP_DDH a inner join ERP_DONDATHANGNPP_SANPHAM b on a.ddh_fk = b.dondathang_fk  " +
			"\n 				inner join ERP_DONDATHANGNPP c on a.ddh_fk = c.pk_seq  " +
			"\n 		where a.ddh_fk = '" + id + "'  " +
			"\n 	)  " +
			"\n 	etc group by hoadonnpp_fk ,etc.vat " +
			"\n )  " +
			"\n giatri on hd.pk_seq = giatri.hoadonnpp_fk  ";*/
			
			//Thêm tiền giảm trừ
			query = "\n update hd set hd.tongtienbvat = round(giatri.bVAT,0), hd.chietkhau = round(giatri.chietkhau,0),   " +
			"\n 			hd.vat = round(giatri.vat,0), hd.tongtienavat = round(giatri.avat,0)  " +
			"\n from ERP_HOADONNPP hd inner join  " +
			"\n (  " +
			"\n 	select hoadonnpp_fk, sum(bVAT)-etc.tiengiamtru as bVAT, sum(chietkhau) as chietkhau, (sum(bVAT) - sum(chietkhau) - etc.tiengiamtru) * ((VAT/100.0)) as VAT, " +
			"\n 			(sum(bVAT) - sum(chietkhau) - etc.tiengiamtru) * (1+(VAT/100.0)) as aVAT, etc.tiengiamtru    " +
			"\n 	from  " +
			"\n 	(  " +
			"\n 		select a.hoadonnpp_fk,isnull(c.tiengiamtru,0)tiengiamtru,   " +
			"\n 	cast( (b.soluong * b.dongia) as float )  as bVAT, isnull(b.chietkhau, 0) as chietkhau,    " +
			"\n 	thuevat as VAT " +
			"\n 		from ERP_HOADONNPP_DDH a inner join ERP_DONDATHANGNPP_SANPHAM b on a.ddh_fk = b.dondathang_fk  " +
			"\n 				inner join ERP_DONDATHANGNPP c on a.ddh_fk = c.pk_seq  " +
			"\n 		where a.ddh_fk = '" + id + "'  " +
			"\n 	)  " +
			"\n 	etc group by hoadonnpp_fk ,etc.vat,etc.TienGiamTru " +
			"\n )  " +
			"\n giatri on hd.pk_seq = giatri.hoadonnpp_fk  ";
			if(db.updateReturnInt(query) <= 0 )
			{
				msg1 = "Không thể tạo mới ERP_HOADONNPP " + query;
				return msg1;
			}
			
			//Update lại tiền VAT từ đơn đối tác = hoá đơn;
			query = "update erp_dondathangnpp set tienvat  = (select vat from erp_hoadonnpp where pk_seq = "+hdId+") where pk_seq = "+id;
			db.update(query);

			//CHECK BANG TONG PHAI BANG BANG CHI TIET
			query = "\n select count(*) as sodong  " +
			"\n from " +
			"\n ( " +
			"\n 	select b.pk_seq as sanpham_fk, sum(soluong) as soluong  " +
			"\n 	from ERP_HOADONNPP_SP a inner join SANPHAM b on a.sanpham_fk = b.pk_seq " +
			"\n 	where a.hoadon_fk = '" + hdId + "' " +
			"\n 	group by b.pk_seq " +
			"\n ) " +
			"\n dh left join " +
			"\n ( " +
			"\n 	select b.pk_seq as sanpham_fk, sum(soluong) as soluong  " +
			"\n 	from ERP_HOADONNPP_SP_CHITIET a inner join SANPHAM b on a.MA = b.MA " +
			"\n 	where a.hoadon_fk = '" + hdId + "' " +
			"\n 	group by b.pk_seq " +
			"\n ) " +
			"\n xk on dh.sanpham_fk = xk.sanpham_fk " +
			"\n where dh.soluong != isnull(xk.soluong, 0) ";
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
				return msg;
			}
			
		} 
		catch (Exception e) 
		{
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			msg1 = "Exception: " + e.getMessage();
			e.printStackTrace();
			return msg1;
		}

		return msg1;
	}

	private String TaoPhieuXuatKho_CapTren_NPP(dbutils db, String id, String userId, String khoId, String nppId, String tructhuoc, String kbh_fk) 
	{
		String query = "";
		String msg = "";

		try
		{
			//Tu dong tao YCXK  --> VA CHOT YCXK NAY LUON
			query = "\n  insert ERP_YCXUATKHONPP(NgayYeuCau, ghichu, trangthai, npp_fk, kho_fk, xuatcho, npp_dat_fk, khachhang_fk, kbh_fk, ngaytao, nguoitao, ngaysua, nguoisua) " +
			"\n  select '" + this.getDateTime()
			+ "\n ', N'Phiếu xuất kho xuất dùm cho khách hàng ETC của CN cấp 2 / đối tác (đơn hàng số: " + id + ")', '2', '" + tructhuoc + "', '100000', " +
			"\n '0' as xuatcho, '" + nppId + "' as npp_dat_fk, khachhang_fk, kbh_fk, '" + getDateTime() + "', '" + userId + "', '" + getDateTime() + "', '" + userId + "' " +
			"\n from ERP_DONDATHANGNPP where pk_seq = '" + id + "' ";

			System.out.println("1.Insert YCXUATKHO: " + query);
			if(db.updateReturnInt(query) <= 0 )
			{
				msg = "Không thể tạo mới ERP_YCXUATKHONPP " + query;
				//db.getConnection().rollback();
				return msg;
			}

			String ycxkId = "";
			ResultSet rsYCXK = db.get("select IDENT_CURRENT('ERP_YCXUATKHONPP') as ycxkId");
			if(rsYCXK.next())
			{
				ycxkId = rsYCXK.getString("ycxkId");
			}
			rsYCXK.close();

			query = "\n Insert ERP_YCXUATKHONPP_DDH(ycxk_fk, ddh_fk) " +
			"\n select IDENT_CURRENT('ERP_YCXUATKHONPP'), pk_seq from ERP_DONDATHANGNPP where pk_seq in ( " + id + " )  ";
			if(!db.update(query))
			{
				msg = "Không thể tạo mới ERP_YCXUATKHONPP_DDH " + query;
				//db.getConnection().rollback();
				return msg;
			}

			query = "\n insert ERP_YCXUATKHONPP_SANPHAM( ycxk_fk, sanpham_fk, soluongDAT, tonkho, daxuat, soluongXUAT, LOAI, SCHEME ) " +
			"\n select IDENT_CURRENT('ERP_YCXUATKHONPP'), sp.PK_SEQ, SUM(dathang.soluong) as soluongDAT, ISNULL( ( select AVAILABLE from ERP_KHOTT_SANPHAM where khott_fk = '" + id + "' and sanpham_fk = sp.PK_SEQ ), 0)  as tonkho, 0, SUM(dathang.soluong) as soluongXUAT, loai, scheme  " +
			"\n from    " +
			"\n (    " +
			"\n 		select a.sanpham_fk, b.DVDL_FK as dvCHUAN,    " +
			"\n 				case when a.dvdl_fk IS null then a.soluong     " +
			"\n 					 when a.dvdl_fk = b.DVDL_FK then a.soluong    " +
			"\n 					 else  a.soluong * ( select SOLUONG1 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )     " +
			"\n 									 / ( select SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )	 end as soluong, 0 as loai, ' ' as scheme   " +
			"\n 		from ERP_DONDATHANGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ    " +
			"\n 		where a.dondathang_fk in ( '" + id + "' )   " +
			"\n )    " +
			"\n dathang inner join SANPHAM sp on dathang.sanpham_fk = sp.PK_SEQ     " +
			"\n group by sp.PK_SEQ, scheme, loai  " ;

			System.out.println("1.1.Insert YCXK - SP: " + query);
			if(db.updateReturnInt(query) <= 0 )
			{
				msg = "Khong the tao moi ERP_YCXUATKHO_SANPHAM: " + query;
				//db.getConnection().rollback();
				return msg;
			}

			//CHECK TON KHO
			query =  "\n select khoxuat_fk as kho_fk, npp_fk, kbh_fk, sp.PK_SEQ, sp.TEN, SUM(dathang.soluong) as soluongXUAT,  " +
			"\n 	ISNULL( ( select AVAILABLE from NHAPP_KHO where kho_fk = dathang.khoxuat_fk and sanpham_fk = sp.PK_SEQ and kbh_fk = dathang.kbh_fk and npp_fk = '" + tructhuoc + "' ), 0) as tonkho  " +
			"\n from     " +
			"\n (     " +
			"\n 	select c.kho_fk as khoxuat_fk, '" + tructhuoc + "' as npp_fk, c.kbh_fk, a.sanpham_fk, b.DVDL_FK as dvCHUAN,     " +
			"\n 			case when a.dvdl_fk IS null then a.soluong      " +
			"\n 				 when a.dvdl_fk = b.DVDL_FK then a.soluong     " +
			"\n 				 else  a.soluong * ( select SOLUONG1 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk and dvdl1_fk = b.dvdl_fk )      " +
			"\n 								 / ( select SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk and dvdl1_fk = b.dvdl_fk )	 end as soluong   " +
			"\n 	from ERP_DONDATHANGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ  " +
			"\n 			inner join ERP_DONDATHANGNPP c on a.dondathang_fk = c.pk_seq    " +
			"\n 	where a.dondathang_fk in ( " + id + " )     " +
			"\n )     " +
			"\n dathang inner join SANPHAM sp on dathang.sanpham_fk = sp.PK_SEQ   " +
			"\n group by khoxuat_fk, npp_fk, kbh_fk, sp.PK_SEQ, sp.TEN  ";

			System.out.println("--CHECK TON KHO: " + query);

			ResultSet rs = db.get(query);
			if(rs != null)
			{
				while(rs.next())
				{
					String khoID = rs.getString("kho_fk");
					String kbhID = rs.getString("kbh_fk");
					String nppID = rs.getString("npp_fk");
					String spID = rs.getString("PK_SEQ");

					double soluong = rs.getDouble("soluongXUAT");
					double tonkho = rs.getDouble("tonkho");

					if(soluong > tonkho)
					{
						msg = "Sản phẩm ( " + rs.getString("TEN") + " ) với số lượng yêu cầu ( " + rs.getString("soluongXUAT") + " ) không đủ tồn kho ( " + rs.getString("tonkho") + " ). Vui lòng liên hệ với chi nhánh cấp trên để xử lý.";
						//db.getConnection().rollback();
						rs.close();
						return msg;
					}

					//CAP NHAT KHO XUAT TONG
					query = "\n Update NHAPP_KHO set soluong = soluong - '" + soluong + "', AVAILABLE = AVAILABLE - '" + soluong + "' " +
					"\n where KHO_FK = '"+khoID+"' and KBH_FK = '" + kbhID + "' and NPP_FK = '" + nppID + "' and SANPHAM_FK = '" + spID + "' ";
					if(db.updateReturnInt(query)!=1)
					{
						msg = "Khong the cap nhat ERP_KHOTT_SANPHAM: " + query;
						//db.getConnection().rollback();
						rs.close();
						return msg;
					}


					//CAP NHAT KHO CHI TIET
					query = "\n  select AVAILABLE, SOLO, ngayhethan,NGAYNHAPKHO from NHAPP_KHO_CHITIET " +
					"\n  where AVAILABLE > 0 and KHO_FK = '" + khoID + "'  and SANPHAM_FK = '" + spID + "'  " +
					"\n  and NPP_FK = '" + nppID + "' and KBH_FK = '" + kbhID + "' order by ngayhethan asc,ngaynhapkho asc ";

					ResultSet rsTK = db.get(query);
					double avai = 0;
					double totalXUAT = 0;
					while(rsTK.next())
					{
						double soluongCT = 0;
						String solo = rsTK.getString("SOLO");
						String ngayhethan = rsTK.getString("ngayhethan");
						String ngaynhapkho= rsTK.getString("ngaynhapkho");
						avai = rsTK.getDouble("AVAILABLE");
						totalXUAT += avai;

						if(totalXUAT <= soluong)
						{
							soluongCT = avai;

							query = "\n insert ERP_YCXUATKHONPP_SANPHAM_CHITIET( ycxk_fk, SANPHAM_FK, solo, soluong, ngayhethan ,ngaynhapkho) " +
							"\n select '" + ycxkId + "', '" + spID + "', N'" + solo + "', '" + soluongCT  + "', '" + ngayhethan + "','"+ngaynhapkho+"' ";

							System.out.println("1.2.Insert YCXK - SP - CT: " + query);
							if(!db.update(query))
							{
								msg = "Khong the tao moi ERP_YCXUATKHONPP_SANPHAM_CHITIET: " + query;
								//db.getConnection().rollback();
								rs.close();
								return msg;
							}

							query = "\n Update NHAPP_KHO_CHITIET set soluong = soluong - '" + soluongCT + "', AVAILABLE = AVAILABLE - '" + soluongCT + "' " +
							"\n where KHO_FK = '" + khoID + "' and SOLO = '" + solo + "' and SANPHAM_FK = '" + spID + "' AND KBH_FK = '" + kbhID + "' and NPP_FK = '" + nppID + "' AND NgayHetHan='"+ngayhethan+"' and ngaynhapkho='"+ngaynhapkho+"' ";
							if(db.updateReturnInt(query)!=1)
							{
								msg = "Khong the cap nhat NHAPP_KHO_CHITIET: " + query;
								//db.getConnection().rollback();
								rs.close();
								return msg;
							}

						}
						else
						{
							soluongCT = soluong - ( totalXUAT - avai );

							query = "insert ERP_YCXUATKHONPP_SANPHAM_CHITIET( ycxk_fk, SANPHAM_FK, solo, soluong, ngayhethan,ngaynhapkho ) " +
							"select '" + ycxkId + "', '" + spID + "', N'" + solo + "', '" + soluongCT + "', '" + ngayhethan + "','"+ngaynhapkho+"' ";

							System.out.println("1.2.Insert YCXK - SP - CT: " + query);
							if(!db.update(query))
							{
								msg = "Khong the tao moi ERP_YCXUATKHONPP_SANPHAM_CHITIET: " + query;
								//db.getConnection().rollback();
								rs.close();
								return msg;
							}

							query = "\n Update NHAPP_KHO_CHITIET set soluong = soluong - '" + soluongCT + "', AVAILABLE = AVAILABLE - '" + soluongCT + "' " +
							"\n where KHO_FK = '" + khoID + "' and SOLO = '" + solo + "' and SANPHAM_FK = '" + spID + "' and KBH_FK = '" + kbhID + "' and NPP_FK = '" + nppID + "' and NgayHetHan='"+ngayhethan+"' and ngaynhapkho='"+ngaynhapkho+"' ";
							if(db.updateReturnInt(query)!=1)
							{
								msg = "Khong the cap nhat NHAPP_KHO_CHITIET: " + query;
								//db.getConnection().rollback();
								rs.close();
								return msg;
							}

							break;
						}
					}
					rsTK.close();
				}
				rs.close();
			}
		} 
		catch (Exception e) {

			e.printStackTrace();
			return "Không thể duyệt đơn hàng " + e.getMessage();
		}

		return "";
	}

	private String TaoPhieuXuatKho_CapTren_HO(dbutils db, String id, String userId, String khoNhanId, String nppId, String tructhuoc, String kbh_fk) 
	{
		String msg = "";
		String query = "";

		try 
		{
			db.getConnection().setAutoCommit(false);

			//CHECK TON KHO
			query = "select sp.PK_SEQ, sp.TEN, SUM(dathang.soluong) as soluongXUAT, " +
			"	ISNULL( ( select AVAILABLE from ERP_KHOTT_SANPHAM where khott_fk = '" + khoNhanId + "' and sanpham_fk = sp.PK_SEQ ), 0) as tonkho " +
			"from    " +
			"(    " +
			"		select a.sanpham_fk, b.DVDL_FK as dvCHUAN,    " +
			"				case when a.dvdl_fk IS null then a.soluong     " +
			"					 when a.dvdl_fk = b.DVDL_FK then a.soluong    " +
			"					 else  a.soluong * ( select SOLUONG1 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )     " +
			"									 / ( select SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )	 end as soluong, 0 as loai, ' ' as scheme   " +
			"		from ERP_DONDATHANGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ    " +
			"		where a.dondathang_fk in ( '" + id + "' )   " +
			")    " +
			"dathang inner join SANPHAM sp on dathang.sanpham_fk = sp.PK_SEQ  " +
			"group by sp.PK_SEQ, sp.TEN " +
			"having  SUM(dathang.soluong) > ISNULL( ( select AVAILABLE from ERP_KHOTT_SANPHAM where khott_fk = '" + khoNhanId + "' and sanpham_fk = sp.PK_SEQ ), 0) " ;

			System.out.println("--CHECK TON KHO: " + query);

			ResultSet rsCHECK = db.get(query);
			if(rsCHECK != null)
			{
				while(rsCHECK.next())
				{
					msg = "Sản phẩm ( " + rsCHECK.getString("TEN") + " ) với số lượng yêu cầu ( " + rsCHECK.getString("soluongXUAT") + " ) không đủ tồn kho ( " + rsCHECK.getString("tonkho") + " ) của HO. Vui lòng liên hệ với HO để xử lý.";
					//db.getConnection().rollback();
					rsCHECK.close();
					return msg;
				}
				rsCHECK.close();
			}

			//Tu dong tao YCXK  --> VA CHOT YCXK NAY LUON
			query = " insert ERP_YCXUATKHO(NgayYeuCau, ghichu, trangthai, npp_fk, kho_fk, ngaytao, nguoitao, ngaysua, nguoisua) " +
			" values('" + this.getDateTime() + "', N'Phiếu xuất kho xuất dùm cho khách hàng ETC của CN cấp 2 / đối tác (đơn hàng số: " + id + ")', '2', '"
			+ nppId + "', " + khoNhanId + ", '" + getDateTime() + "', '" + userId + "', '" + getDateTime() + "', '" + userId + "' )";

			System.out.println("1.Insert YCXUATKHO: " + query);
			if(db.updateReturnInt(query) <= 0 )
			{
				msg = "Không thể tạo mới ERP_YCXUATKHO " + query;
				//db.getConnection().rollback();
				return msg;
			}

			query = "Insert ERP_YCXUATKHO_DDH(ycxk_fk, ddh_fk) " +
			"select IDENT_CURRENT('ERP_YCXUATKHO'), pk_seq from ERP_DONDATHANG where pk_seq in ( " + id + " )  ";
			if(!db.update(query))
			{
				msg = "Không thể tạo mới ERP_YCXUATKHO_DDH " + query;
				//db.getConnection().rollback();
				return msg;
			}

			query = "insert ERP_YCXUATKHO_SANPHAM( ycxk_fk, sanpham_fk, soluongDAT, tonkho, daxuat, soluongXUAT, LOAI, SCHEME ) " +
			"select IDENT_CURRENT('ERP_YCXUATKHO'), sp.PK_SEQ, SUM(dathang.soluong) as soluongDAT, ISNULL( ( select AVAILABLE from ERP_KHOTT_SANPHAM where khott_fk = '" + khoNhanId + "' and sanpham_fk = sp.PK_SEQ ), 0)  as tonkho, 0, SUM(dathang.soluong) as soluongXUAT, loai, scheme  " +
			"from    " +
			"(    " +
			"		select a.sanpham_fk, b.DVDL_FK as dvCHUAN,    " +
			"				case when a.dvdl_fk IS null then a.soluong     " +
			"					 when a.dvdl_fk = b.DVDL_FK then a.soluong    " +
			"					 else  a.soluong * ( select SOLUONG1 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )     " +
			"									 / ( select SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )	 end as soluong, 0 as loai, ' ' as scheme   " +
			"		from ERP_DONDATHANGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ    " +
			"		where a.dondathang_fk in ( '" + id + "' )   " +
			")    " +
			"dathang inner join SANPHAM sp on dathang.sanpham_fk = sp.PK_SEQ     " +
			"group by sp.PK_SEQ, scheme, loai  " ;

			System.out.println("1.1.Insert YCXK - SP: " + query);
			if(db.updateReturnInt(query) <= 0 )
			{
				msg = "Khong the tao moi ERP_YCXUATKHO_SANPHAM: " + query;
				//db.getConnection().rollback();
				return msg;
			}

			query = "select sp.PK_SEQ, sp.TEN, LOAI, SCHEME, SUM(dathang.soluong) as soluongXUAT " +
			"from    " +
			"(    " +
			"		select a.sanpham_fk, b.DVDL_FK as dvCHUAN,    " +
			"				case when a.dvdl_fk IS null then a.soluong     " +
			"					 when a.dvdl_fk = b.DVDL_FK then a.soluong    " +
			"					 else  a.soluong * ( select SOLUONG1 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )     " +
			"									 / ( select SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )	 end as soluong, 0 as loai, ' ' as scheme   " +
			"		from ERP_DONDATHANGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ    " +
			"		where a.dondathang_fk in ( '" + id + "' )   " +
			")    " +
			"dathang inner join SANPHAM sp on dathang.sanpham_fk = sp.PK_SEQ  " +
			"group by sp.PK_SEQ, sp.TEN, LOAI, SCHEME ";
			System.out.println("--CHECK KHO CHI TIET: " + query);
			rsCHECK = db.get(query);
			if(rsCHECK != null)
			{
				while(rsCHECK.next())
				{
					String sanpham_fk = rsCHECK.getString("PK_SEQ");
					String LOAI = rsCHECK.getString("LOAI");
					String SCHEME = rsCHECK.getString("SCHEME");
					double soLUONG = rsCHECK.getDouble("soluongXUAT");

					query = "Update ERP_KHOTT_SANPHAM set soluong = soluong - '" + soLUONG + "', AVAILABLE = AVAILABLE - '" + soLUONG + "' " +
					"where KHOTT_FK = '" + khoNhanId + "' and SANPHAM_FK = '" + sanpham_fk + "'  ";
					if(!db.update(query))
					{
						msg = "Khong the cap nhat ERP_KHOTT_SANPHAM: " + query;
						//db.getConnection().rollback();
						return msg;
					}

					//CAP NHAT KHO CHI TIET
					query = "select AVAILABLE, SOLO from ERP_KHOTT_SP_CHITIET " +
					"where KHOTT_FK = '" + khoNhanId + "'  and SANPHAM_FK = '" + sanpham_fk + "' order by ngayhethan asc ";

					ResultSet rsTK = db.get(query);
					double avai = 0;
					double totalXUAT = 0;
					while(rsTK.next())
					{
						double soluongCT = 0;
						String solo = rsTK.getString("SOLO");

						avai = rsTK.getDouble("AVAILABLE");
						totalXUAT += avai;

						if(totalXUAT <= soLUONG)
						{
							soluongCT = avai;

							query = "insert ERP_YCXUATKHO_SANPHAM_CHITIET( ycxk_fk, SANPHAM_FK, solo, soluong, loai, scheme ) " +
							"select IDENT_CURRENT('ERP_YCXUATKHO'), '" + sanpham_fk + "', N'" + solo + "', '" + soluongCT + "', '" + LOAI + "', '" + SCHEME + "' ";

							System.out.println("1.2.Insert YCXK - SP - CT: " + query);
							if(!db.update(query))
							{
								msg = "Khong the tao moi ERP_YCXUATKHO_SANPHAM_CHITIET: " + query;
								//db.getConnection().rollback();
								return msg;
							}

							query = "Update ERP_KHOTT_SP_CHITIET set soluong = soluong - '" + soluongCT + "', AVAILABLE = AVAILABLE - '" + soluongCT + "' " +
							"where KHOTT_FK = '" + khoNhanId + "' and SOLO = '" + solo + "' and SANPHAM_FK = '" + sanpham_fk + "'  ";
							if(!db.update(query))
							{
								msg = "Khong the cap nhat ERP_KHOTT_SP_CHITIET: " + query;
								//db.getConnection().rollback();
								return msg;
							}

						}
						else
						{
							soluongCT = soLUONG - ( totalXUAT - avai );

							query = "insert ERP_YCXUATKHO_SANPHAM_CHITIET( ycxk_fk, SANPHAM_FK, solo, soluong, loai, scheme ) " +
							"select IDENT_CURRENT('ERP_YCXUATKHO'), '" + sanpham_fk + "', N'" + solo + "', '" + soluongCT + "', '" + LOAI + "', '" + SCHEME + "' ";

							System.out.println("1.2.Insert YCXK - SP - CT: " + query);
							if(!db.update(query))
							{
								msg = "Khong the tao moi ERP_YCXUATKHO_SANPHAM_CHITIET: " + query;
								//db.getConnection().rollback();
								return msg;
							}

							query = "Update ERP_KHOTT_SP_CHITIET set soluong = soluong - '" + soluongCT + "', AVAILABLE = AVAILABLE - '" + soluongCT + "' " +
							"where KHOTT_FK = '" + khoNhanId + "' and SOLO = '" + solo + "' and SANPHAM_FK = '" + sanpham_fk + "'  ";
							if(!db.update(query))
							{
								msg = "Khong the cap nhat ERP_KHOTT_SP_CHITIET: " + query;
								//db.getConnection().rollback();
								return msg;
							}

							break;
						}
					}
					rsTK.close();

				}
				rsCHECK.close();
			}
		} 
		catch (Exception e) 
		{
			msg = "--LOI DUYET: " + e.getMessage();
			e.printStackTrace();
			return msg;
		}

		return "";
	}

	private String TaoPhieuXuatKhoNPP(dbutils db, String id, String userId, String khoNhanId, String nppId, String npp_dat_fk, String kbh_fk)
	{
		String query = "";
		String msg = "";
		Utility util_kho=new Utility();

		try
		{
			query =" update NHANVIEN SET Active = '1' where pk_seq='"+userId+"'";
			if(!db.update(query))
			{
				msg = "Không thể tạo mới ERP_YCXUATKHONPP " + query;
				//db.getConnection().rollback();
				return msg;
			}
			// CHECK XEM CO SP NAO CÓ SỐ LƯỢNG TRONG ĐƠN HÀNG MÀ CHƯA THIẾT LẬP
			// QUY CÁCH KHÔNG
			query = "		select a.sanpham_fk, b.DVDL_FK as dvCHUAN,    " +
			"				case when a.dvdl_fk IS null then a.soluong     " +
			"					 when a.dvdl_fk = b.DVDL_FK then a.soluong    " +
			"					 else  a.soluong * ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk and DVDL1_FK = b.DVDL_FK )   end as soluong, " +
			"			0 as loai, ' ' as scheme, b.ten as spTEN   " +
			"		from ERP_DONDATHANGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ    " +
			"		where a.dondathang_fk in ( '" + id + "' ) and a.soluong > 0   ";
			ResultSet rsCHECK = db.get(query);
			String spCHUACOQC = "";
			if(rsCHECK != null)
			{
				while(rsCHECK.next())
				{
					if (rsCHECK.getString("soluong") == null) // Chưa có thiết lập quy cách mà có số lượng
						spCHUACOQC += rsCHECK.getString("spTEN") + ", ";
				}
				rsCHECK.close();
			}

			if(spCHUACOQC.trim().length() > 0)
			{
				msg = "Các sản phẩm sau chưa thiết lập quy cách: " + spCHUACOQC;
				//db.getConnection().rollback();
				return msg;
			}

			//Tu dong tao YCXK  --> VA CHOT YCXK NAY LUON
			query = " insert ERP_YCXUATKHONPP(NgayYeuCau, ghichu, trangthai, npp_fk, kho_fk, xuatcho, npp_dat_fk, khachhang_fk, kbh_fk, ngaytao, nguoitao, ngaysua, nguoisua) " +
			" select '" + this.getDateTime() + "', N'Phiếu xuất kho tạo tự động từ duyệt đơn đặt hàng " + id+ "', '2', '" + nppId + "', " + khoNhanId + ", " +
			" case when npp_dat_fk is not null then '0' else '1' end as xuatcho, npp_dat_fk, khachhang_fk, KBH_FK as kbh_fk, '" + getDateTime() + "', '" + userId + "', '" + getDateTime() + "', '" + userId + "' " +
			" from ERP_DONDATHANGNPP where pk_seq = '" + id + "' ";

			System.out.println("1.Insert YCXUATKHO: " + query);
			if(db.updateReturnInt(query) <= 0 )
			{
				msg = "Không thể tạo mới ERP_YCXUATKHONPP " + query;
				//db.getConnection().rollback();
				return msg;
			}

			String ycxkId = "";
			ResultSet rsYCXK = db.get("select IDENT_CURRENT('ERP_YCXUATKHONPP') as ycxkId");
			if(rsYCXK.next())
			{
				ycxkId = rsYCXK.getString("ycxkId");
			}
			rsYCXK.close();

			query = "Insert ERP_YCXUATKHONPP_DDH(ycxk_fk, ddh_fk) " +
			"select '" + ycxkId + "', pk_seq from ERP_DONDATHANGNPP where pk_seq in ( " + id + " )  ";
			if(!db.update(query))
			{
				msg = "Không thể tạo mới ERP_YCXUATKHONPP_DDH " + query;
				//db.getConnection().rollback();
				return msg;
			}

			query = "insert ERP_YCXUATKHONPP_SANPHAM( ycxk_fk, sanpham_fk, soluongDAT, tonkho, daxuat, soluongXUAT, LOAI, SCHEME ) " +
			" select '" + ycxkId + "', sp.PK_SEQ, SUM(dathang.soluong) as soluongDAT, " +
			"	ISNULL( ( select AVAILABLE from NHAPP_KHO where kho_fk = dathang.khoxuat_fk and sanpham_fk = sp.PK_SEQ and kbh_fk = '" + kbh_fk + "' and npp_fk = dathang.npp_fk ), 0) as tonkho, 0, SUM(dathang.soluong) as soluongXUAT, loai, scheme  " +
			"from    " +
			"(    " +
			"		select c.kho_fk as khoxuat_fk, c.npp_fk, c.kbh_fk, a.sanpham_fk, b.DVDL_FK as dvCHUAN,    " +
			"				case when a.dvdl_fk IS null then a.soluong     " +
			"					 when a.dvdl_fk = b.DVDL_FK then a.soluong    " +
			"					 else  a.soluong * ( select SOLUONG1 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )     " +
			"									 / ( select SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )	 end as soluong, 0 as loai, ' ' as scheme   " +
			"		from ERP_DONDATHANGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ    " +
			"			inner join ERP_DONDATHANGNPP c on a.dondathang_fk = c.pk_seq    " +
			"		where a.dondathang_fk in ( '" + id + "' )   " +
			")    " +
			"dathang inner join SANPHAM sp on dathang.sanpham_fk = sp.PK_SEQ     " +
			"group by dathang.khoxuat_fk, dathang.npp_fk, sp.PK_SEQ, scheme, loai  " ;

			System.out.println("1.1.Insert YCXK - SP: " + query);
			if(db.updateReturnInt(query) <= 0 )
			{
				msg = "Khong the tao moi ERP_YCXUATKHO_SANPHAM: " + query;
				//db.getConnection().rollback();
				return msg;
			}


			/*// CẬP NHẬT KHO TỔNG
			query = "update kho   " +
					"set kho.soluong = kho.soluong - BOOK_KHO.soluong,  " +
					"	kho.booked = kho.booked - BOOK_KHO.soluong  " +
					"from " +
					"( " +
					"	select khoxuat_fk, npp_fk, kbh_fk, sanpham_fk, sum(soluong) as soluong  " +
					"	from " +
					"	( " +
					"		select c.kho_fk as khoxuat_fk, c.npp_fk, case when isnull(d.dungchungkenh, 0) = 0 then c.kbh_fk else 100025 end as kbh_fk, a.sanpham_fk,       " +
					"				case when a.dvdl_fk IS null then a.soluong       " +
					"					 when a.dvdl_fk = b.DVDL_FK then a.soluong      " +
					"					 else  a.soluong * ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk and dvdl1_fk = b.dvdl_fk )  end as soluong    " +
					"		from ERP_DONDATHANGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ   " +
					"				inner join ERP_DONDATHANGNPP c on a.dondathang_fk = c.pk_seq  inner join NHAPHANPHOI d on c.npp_fk = d.pk_seq   " +
					"		where a.dondathang_fk in (  " + this.id + "  ) and a.soluong > 0 " +
					"	) " +
					"	DATHANG  " +
					"	group by khoxuat_fk, npp_fk, kbh_fk, sanpham_fk " +
					") " +
					"BOOK_KHO inner join NHAPP_KHO kho on BOOK_KHO.khoxuat_fk = kho.kho_fk and BOOK_KHO.npp_fk = kho.npp_fk and BOOK_KHO.kbh_fk = kho.kbh_fk and BOOK_KHO.sanpham_fk = kho.sanpham_fk ";
			if(!db.update(query))
			{
				msg = "Không thể cập nhật NHAPP_KHO " + query;
				return msg;
			}*/

			query=	"  select khoxuat_fk, npp_fk, kbh_fk, sanpham_fk, sum(soluong) as soluong  " +
			"	from " +
			"	( " +
			"		select c.kho_fk as khoxuat_fk, c.npp_fk, case when isnull(d.dungchungkenh, 0) = 0 then c.kbh_fk else 100025 end as kbh_fk, a.sanpham_fk,       " +
			"				case when a.dvdl_fk IS null then a.soluong       " +
			"					 when a.dvdl_fk = b.DVDL_FK then a.soluong      " +
			"					 else  a.soluong * ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk and dvdl1_fk = b.dvdl_fk )  end as soluong    " +
			"		from ERP_DONDATHANGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ   " +
			"				inner join ERP_DONDATHANGNPP c on a.dondathang_fk = c.pk_seq  inner join NHAPHANPHOI d on c.npp_fk = d.pk_seq   " +
			"		where a.dondathang_fk in (  " + this.id + "  ) and a.soluong > 0 " +
			"	) " +
			"	DATHANG  " +
			"	group by khoxuat_fk, npp_fk, kbh_fk, sanpham_fk ";

			ResultSet rssp=db.get(query);
			String _khoxuat_fk="", _npp_fk="", _kbh_fk="", _sanpham_fk="";
			while(rssp.next()){
				_khoxuat_fk =rssp.getString("khoxuat_fk");
				_npp_fk =rssp.getString("npp_fk");
				_kbh_fk =rssp.getString("kbh_fk");
				_sanpham_fk =rssp.getString("sanpham_fk");

				double soluongct_=0;

				soluongct_=rssp.getDouble("soluong");

				String msg1= util_kho.Update_NPP_Kho_Sp(this.ngayyeucau, "Duyệt đơn hàng đối tác ErpdondathangDoitac.java 3515 " + this.id, db, _khoxuat_fk, _sanpham_fk, _npp_fk, _kbh_fk,(-1)* soluongct_, (-1)* soluongct_, 0, 0);

				if(msg1.length()>0){
					return msg1;

				}

			}
			rssp.close();


			query = "select c.npp_fk, case when isnull(d.dungchungkenh, 0) = 0 then c.kbh_fk else 100025 end as kbh_fk, " +
			"		c.kho_fk, a.sanpham_fk, b.ten as TEN, a.soluong as soluongDAT, a.solo, a.ngayhethan,a.ngaynhapkho ,  " +
			"		case when a.dvdl_fk IS null then a.soluong      " +
			"			 when a.dvdl_fk = b.DVDL_FK then a.soluong     " +
			"			 else  a.soluong * ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk and DVDL1_FK = b.DVDL_FK )   end as soluong,  " +
			"	0 as loai, ' ' as scheme    " +
			"from ERP_DONDATHANGNPP_SANPHAM_CHITIET a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ   " +
			"		inner join ERP_DONDATHANGNPP c on  a.dondathang_fk = c.pk_seq inner join NHAPHANPHOI d on c.npp_fk = d.pk_seq " +
			"where a.dondathang_fk in ( " + this.id + " )   ";

			System.out.println("--CHECK TON KHO CHI TIET: " + query);

			ResultSet rs = db.get(query);
			if(rs != null)
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
					String ngaynhapkho=rs.getString("ngaynhapkho");

					String msg1=
					util.Update_NPP_Kho_Sp_Chitiet("",  "Duyệt đơn hàng đối tác ErpdondathangDoitac.java 3612 " + this.id
								, db, khoID, spID, nppID, kbhID, ngaynhapkho, (-1)* soluong, (-1)* soluong, 0, 0, 0);

					if(msg1.length()>0){
						return msg1;
					}


					query = "insert ERP_YCXUATKHONPP_SANPHAM_CHITIET( ycxk_fk, SANPHAM_FK, solo, soluong, ngayhethan, kho_fk ,NGAYNHAPKHO) " +
					"select '" + ycxkId + "', '" + spID + "', N'" + solo + "', '" + soluong  + "', '" + ngayhethan + "', '" + khoID + "','"+ngaynhapkho+"' ";

					System.out.println("1.2.Insert YCXK - SP - CT: " + query);
					if(!db.update(query))
					{
						msg = "Khong the tao moi ERP_YCXUATKHONPP_SANPHAM_CHITIET: " + query;
						//db.getConnection().rollback();
						rs.close();
						return msg;
					}							
				}
				rs.close();
			}

			//CHECK TONG PHAI BANG CHI TIET
			query = "select count(*) as soDONG   " +
			"from ERP_YCXUATKHONPP_SANPHAM tong left join   " +
			"	(  " +
			"		select sanpham_fk, sum(soluong) as soluong   " +
			"		from ERP_YCXUATKHONPP_SANPHAM_CHITIET  " +
			"		where  ycxk_fk = '" + ycxkId + "'  " +
			"		group by sanpham_fk " +
			"	)  " +
			"	CT on tong.sanpham_fk = CT.sanpham_fk " +
			"where ycxk_fk = '" + ycxkId + "' and tong.soluongXUAT != isnull(CT.soluong, 0)  ";
			rsCHECK = db.get(query);
			int soDONG = 0;
			if(rsCHECK != null )
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
				return "11.Lỗi hệ thống ( tổng xuất theo lô đề xuất đang bị sai ). Vui lòng liên hệ trung tâm để được hỗ trợ xử lý.";
			}

			if(npp_dat_fk.trim().length() > 0)
			{
				//Tu dong tao nhan hang
				query = " insert NHAPHANG(NGAYNHAN, NGAYCHUNGTU, NPP_FK, NCC_FK, GSBH_FK, ASM_FK, BM_FK, DVKD_FK, KBH_FK, YCXKNPP_FK, TRANGTHAI, NGUOITAO, NGAYTAO, NGUOISUA, NGAYSUA) " +
				" select distinct NgayYeuCau, NgayYeuCau, NPP_DAT_FK,  " +
				" 			( select top(1) NCC_FK from NHACUNGCAP_DVKD where PK_SEQ in ( select NCC_DVKD_FK from NHAPP_NHACC_DONVIKD where NPP_FK = a.NPP_DAT_FK ) ), " +
				"			( select top(1) GSBH_FK from NHAPP_GIAMSATBH where NPP_FK = a.NPP_DAT_FK ), " +
				"			( select top(1) ASM_FK from ASM_KHUVUC where KHUVUC_FK in ( select KHUVUC_FK from NHAPHANPHOI where PK_SEQ = a.NPP_DAT_FK )), " +
				"			( select top(1) BM_FK from BM_CHINHANH where VUNG_FK in ( select VUNG_FK from KHUVUC where PK_SEQ in (  select KHUVUC_FK from NHAPHANPHOI where PK_SEQ = a.NPP_DAT_FK ) ) ), " +
				" 	   '100001' as DVKD_FK, a.KBH_FK, '" + ycxkId + "', '0', '" + userId + "', '" + getDateTime() + "', '" + userId + "', '" + getDateTime() + "' " +
				" from ERP_YCXUATKHONPP a inner join ERP_YCXUATKHONPP_SANPHAM b on a.PK_SEQ = b.ycxk_fk " +
				" where a.PK_SEQ = '" + ycxkId + "' ";

				System.out.println("---INSERT NHAN HANG: " + query );
				if(!db.update(query))
				{
					msg = "Không tạo mới NHAPHANG " + query;
					//db.getConnection().rollback();
					return msg;
				}

				query = " insert NHAPHANG_SP(NHAPHANG_FK, SANPHAM_FK, SOLUONG, soluongNHAN, DONGIA, CHIETKHAU, DVDL_FK, LOAI, SCHEME, SOLO, NGAYHETHAN) " +
				" select ( select pk_seq from NHAPHANG where YCXKNPP_FK = a.PK_SEQ  ),  " +
				" 		b.sanpham_fk, b.soluong, NULL, b.dongia, 0 as chietkhau, c.DVDL_FK, b.LOAI, b.SCHEME, b.solo, b.ngayhethan " +
				" from ERP_YCXUATKHONPP a inner join ERP_YCXUATKHONPP_SANPHAM_CHITIET b on a.PK_SEQ = b.ycxk_fk " +
				" 		inner join SANPHAM c on b.sanpham_fk = c.PK_SEQ   " +
				" where a.PK_SEQ = '" + ycxkId + "' and b.soluong > 0 ";

				System.out.println("---INSERT NHAN HANG - SP: " + query );
				if(!db.update(query))
				{
					msg = "Không tạo mới NHAPHANG_SP " + query;
					//db.getConnection().rollback();
					return msg;
				}

				query = "insert NHAPHANG_DDH(nhaphang_fk, ddh_fk)  " +
				"select ( select PK_SEQ from NHAPHANG where YCXKNPP_FK = '" + id + "' ) as nhID, ddh_fk  " +
				"from ERP_YCXUATKHONPP_DDH where ycxk_fk = '" + ycxkId + "'";
				if(!db.update(query))
				{
					msg = "Không tạo mới NHAPHANG_DDH " + query;
					//db.getConnection().rollback();
					return msg;
				}
			}
		} 
		catch (Exception e) {

			e.printStackTrace();
			return "Không thể duyệt đơn hàng " + e.getMessage();
		}

		return "";
	}

	public Hashtable<String, String> getSanpham_Soluong() {

		return this.sanpham_soluong;
	}


	public void setSanpham_Soluong(Hashtable<String, String> sp_soluong) {

		this.sanpham_soluong = sp_soluong;
	}

	public ResultSet getSoloTheoSp(String spMa, String donvi, String tongluong)
	{

		Utility  util_kho=new Utility();

		String ngayhoadon_=util_kho.getngayhoadon(this.userId, db,this.ngayyeucau,this.khId,1);


		tongluong = tongluong.replaceAll(",", "");
		//System.out.println("---TONG LUONG: " + tongluong );


		String kbh_fk = "";
		String sqlk="	select (select  kbh_fk from ERP_DONDATHANGNPP where pk_seq="+this.id +")kbh_fk" +
					"	, (select pk_seq from donvidoluong where donvi = N'"+donvi+"')donvi		 ";
		ResultSet rsk=db.get(sqlk);
		try {
			rsk.next();
			kbh_fk=rsk.getString("kbh_fk");
			donvi =  rsk.getString("donvi");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		/*String sqlDONHANG = "";
		if( this.id.trim().length() > 0 )
			sqlDONHANG = " select SUM(soluong) from ERP_DONDATHANGNPP_SANPHAM_CHITIET where dondathang_fk = '" + this.id + "' and SANPHAM_FK = CT.sanpham_fk and solo = CT.solo and ngayhethan = CT.ngayhethan and ngaynhapkho = CT.ngaynhapkho  ";
		else
			sqlDONHANG = " select SUM(soluong) from ERP_DONDATHANGNPP_SANPHAM_CHITIET where dondathang_fk = '1' and SANPHAM_FK = CT.sanpham_fk and solo = CT.solo and ngayhethan = CT.ngayhethan and ngaynhapkho = CT.ngaynhapkho  ";

		String query = "select case when sp.dvdl_fk != '" + donvi + "'  " +
					   "	then ( select soluong2 / soluong1 from QUYCACH where SANPHAM_FK = sp.PK_SEQ and DVDL1_FK = sp.DVDL_FK and DVDL2_FK = '" + donvi + "' ) * AVAILABLE else AVAILABLE end as AVAILABLE,  " +
					   "	NGAYHETHAN, SOLO " +
					   "from NHAPP_KHO_CHITIET ct inner join SANPHAM sp on ct.sanpham_fk = sp.pk_seq " +
					   "where KHO_FK = '" + this.khoNhanId + "' and SANPHAM_FK = ( select pk_seq from SANPHAM where ma = '" + spMa + "' )   " +
					   "	and AVAILABLE + ( " + sqlDONHANG + " ) > 0 and NPP_FK = '" + this.nppId + "' and KBH_FK = '" + kbh_fk + "'  order by NGAYHETHAN asc ";*/


		String dondathang=this.id;

		String query= " select * from  ( "+
		"\n	 	 select '"+ngayhoadon_+"' as NgayDonHang ,a.SANPHAM_FK,a.soluong,a.ngayhethan,a.solo,a.ngaynhapkho,  "+
		"\n	 	case when sp.dvdl_fk !=  '" + donvi + "'    "+
		"\n				then (( select soluong2 / soluong1 from QUYCACH where SANPHAM_FK = a.sanpham_fk  "+
		"\n		 		and DVDL1_FK = sp.DVDL_FK and DVDL2_FK = '" + donvi + "'  ) * AVAILABLE + a.soluong) else AVAILABLE  + a.soluong end as AVAILABLE   "+
		"\n		 from ERP_DONDATHANGNPP_SANPHAM_CHITIET a inner join ERP_DONDATHANGNPP b  "+
		"\n		on a.dondathang_fk=b.PK_SEQ inner join SANPHAM sp on sp.PK_SEQ=a.sanpham_fk   "+
		"\n		inner join NHAPP_KHO_CHITIET kho on kho.SANPHAM_FK=a.sanpham_fk  "+ 
		"\n		and kho.KBH_FK=(select case when isnull(dungchungkenh,0)=1 then 100025 else 100052 end from nhaphanphoi where pk_Seq="+this.nppId+")  "+
		"\n		 and kho.KHO_FK=b.kho_fk and kho.NPP_FK="+this.nppId+"  "+
		"\n		and kho.SOLO=a.solo and kho.NGAYHETHAN=a.ngayhethan and kho.NGAYNHAPKHO=a.ngaynhapkho  "+
		"\n		where b.PK_SEQ='"+dondathang+"' and a.sanpham_fk= ( select pk_seq from SANPHAM where ma = '"+spMa+"' ) "+
		"\n		union all "+
		"\n 	select '"+ngayhoadon_+"',ct.SANPHAM_FK,0 as soluong,ct.NGAYHETHAN,ct.SOLO,ct.NGAYNHAPKHO,case when sp.dvdl_fk != '"+donvi+"'   "+
		"\n		then ( select soluong2 / soluong1 from QUYCACH  "+
		"\n		where SANPHAM_FK = sp.PK_SEQ and DVDL1_FK = sp.DVDL_FK and DVDL2_FK = '"+donvi+"' ) * AVAILABLE  "+
		"\n		else AVAILABLE end as AVAILABLE "+
		"\n 	from NHAPP_KHO_CHITIET ct inner join SANPHAM sp on ct.sanpham_fk = sp.pk_seq  "+
		"\n	where KHO_FK = '"+this.khoNhanId+"' and SANPHAM_FK = ( select pk_seq from SANPHAM where ma = '"+spMa+"' )   "+
		"\n		and AVAILABLE > 0  and NPP_FK = '"+this.nppId+"' and KBH_FK = '"+kbh_fk+"'   "+
		"\n	and not exists ( "+

		"\n  select '"+ngayhoadon_+"' as NgayDonHang,a.SANPHAM_FK,a.soluong,a.ngayhethan,a.solo,a.ngaynhapkho,   "+
		"\n	 	case when sp.dvdl_fk !=  '"+donvi+"'     "+
		"\n				then (( select soluong2 / soluong1 from QUYCACH where SANPHAM_FK = a.sanpham_fk   "+
		"\n		 		and DVDL1_FK = sp.DVDL_FK and DVDL2_FK = '"+donvi+"'  ) * AVAILABLE + a.soluong) else AVAILABLE  + a.soluong end as AVAILABLE  "+  
		"\n		 from ERP_DONDATHANGNPP_SANPHAM_CHITIET a inner join ERP_DONDATHANGNPP b   "+
		"\n		on a.dondathang_fk=b.PK_SEQ inner join SANPHAM sp on sp.PK_SEQ=a.sanpham_fk    "+
		"\n		inner join NHAPP_KHO_CHITIET kho on kho.SANPHAM_FK=a.sanpham_fk   "+
		"\n		and kho.KBH_FK=(select case when isnull(dungchungkenh,0)=1 then 100025 else 100052 end from nhaphanphoi where pk_Seq="+this.nppId+")   "+
		"\n		 and kho.KHO_FK=b.kho_fk and kho.NPP_FK="+this.nppId+"   "+
		"\n		and kho.SOLO=a.solo and kho.NGAYHETHAN=a.ngayhethan and kho.NGAYNHAPKHO=a.ngaynhapkho   "+
		"\n		where b.PK_SEQ='"+dondathang+"' and a.sanpham_fk= ( select pk_seq from SANPHAM where ma =  '"+spMa+"' ) "+
		"\n		  and a.SANPHAM_FK=ct.SANPHAM_FK and a.ngayhethan=ct.NGAYHETHAN and a.solo=ct.SOLO "+
		"\n		  and kho.kbh_fk=ct.KBH_FK and b.Kho_FK=ct.KHO_FK and a.ngaynhapkho=ct.NGAYNHAPKHO		"+ 
		"\n	) "+
		"\n	) as data "+
		"\n	where  data.ngaydonhang >= data.ngaynhapkho "+
		"\n	order by data.NGAYHETHAN  asc,data.solo asc ";


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
					query2 += "select '" + avai + "' as AVAILABLE, '" + rs.getString("NGAYHETHAN") + "' as NGAYHETHAN, '"+rs.getString("ngaynhapkho")+"' as NGAYNHAPKHO,'" + rs.getString("SOLO") + "' as SOLO, '" + slg + "' as tuDEXUAT union ALL ";
				}
				else
				{
					query2 += "select '" + avai + "' as AVAILABLE, '" + rs.getString("NGAYHETHAN") + "' as NGAYHETHAN, '"+rs.getString("ngaynhapkho")+"' as NGAYNHAPKHO, '" + rs.getString("SOLO") + "' as SOLO, '' as tuDEXUAT union ALL ";
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

	public String getDungchungKenh() {

		return this.dungchungKENH;
	}


	public void setDungchungKenh(String dungchungKenh) {

		this.dungchungKENH = dungchungKenh;
	}


	public String getCongNo() {

		return this.congno;
	}

	public String[] getSpGiagoc()
	{
		return this.spGiagoc;
	}


	public void setSpGiagoc(String[] spGiagoc)
	{
		this.spGiagoc = spGiagoc;

	}


	public int getNgay_Chenhlech() 
	{
		return this.ngay_Chenhlech;
	}


	public void setNgay_Chenhlech(int ngay_Chenhlech) 
	{
		this.ngay_Chenhlech = ngay_Chenhlech;

	}



	


	String iskm;
	public String getIsKm()
	{
		return iskm;
	}

	public void setIsKm(String iskm)
	{
		this.iskm = iskm;
	}
	public String[] getSptonkhocn() {
		return sptonkhocn;
	}

	public void setSptonkhocn(String[] sptonkhocn) {
		this.sptonkhocn = sptonkhocn;
	}

	
	
	// hàm bê tự svl qua lưu ý 1 số biến của bean ko tồn tại
	public String Chot(String lsxId, String userId) 
	{
		
		String msg = "";
		try
		{
			db.getConnection().setAutoCommit(false);
			geso.dms.distributor.util.Utility util = new geso.dms.distributor.util.Utility();

			String kbh_fk="";
			String kho_fk="";
			String ngaydonhang="";
			String npp_fk="";
			String sqlck=	"\n select a.ngaydonhang,a.NPP_FK,a.KBH_FK,a.KHO_FK " +
							"\n from ERP_DONDATHANGNPP a  where a.PK_SEQ="+lsxId+" ";
			System.out.println(" chot ban doi tac :"+ sqlck);
			ResultSet rsck=db.get(sqlck);
			if(rsck.next())
			{
				kbh_fk=rsck.getString("kbh_fk");
				kho_fk=rsck.getString("KHO_FK");
				ngaydonhang=rsck.getString("ngaydonhang");
				npp_fk=rsck.getString("NPP_FK");

			}
			rsck.close();
			String query = "update ERP_DondathangNPP set trangthai = '1', NPP_DACHOT = '1' , ngaygiochot='"+ getDateTime1() +"'  where trangthai = 0 and pk_seq = '" + lsxId + "'";
			if(db.updateReturnInt(query) <= 0)
			{
				msg = "Khong the chot: " + query;
				db.getConnection().rollback();
				return msg;
			}

			query = "select isnull(dungchungkenh,0)dungchungkenh from NHAPHANPHOI where PK_SEQ = '" + npp_fk + "' ";
			ResultSet rs = db.get(query);
			if(rs.next())
			{
				if(rs.getString("dungchungkenh").equals("1"))
					kbh_fk = "100025";
			}

			//CHECK BOOKED THEO DV CHUAN
			query =  "\n select sp.dvdl_fk,dvCHUAN, khoxuat_fk as kho_fk, npp_fk, kbh_fk, sp.PK_SEQ, sp.TEN, SUM(dathang.soluong) as soluongXUAT,  " +
			"\n 	ISNULL( ( select AVAILABLE from NHAPP_KHO where kho_fk = dathang.khoxuat_fk and sanpham_fk = sp.PK_SEQ and kbh_fk = dathang.kbh_fk and npp_fk = dathang.npp_fk ), 0) as tonkho  " +
			"\n  from     " +
			"\n  (     " +
			"\n 	select c.kho_fk as khoxuat_fk, c.npp_fk, '" + kbh_fk + "' kbh_fk, a.sanpham_fk,a.DVDL_FK as dvCHUAN,     " +
			"\n 			case when a.dvdl_fk IS null then a.soluong      " +
			"\n 				 when a.dvdl_fk = b.DVDL_FK then a.soluong     " +
			"\n 				 else  a.soluong * ( select SOLUONG1 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk and dvdl1_fk = b.dvdl_fk )      " +
			"\n 								 / ( select SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk and dvdl1_fk = b.dvdl_fk )	 end as soluong   " +
			"\n 	from ERP_DONDATHANGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ  " +
			"\n 			inner join ERP_DONDATHANGNPP c on a.dondathang_fk = c.pk_seq    " +
			"\n 	where a.dondathang_fk in ( " + lsxId + " )     " +
			"\n )     " +
			"\n dathang inner join SANPHAM sp on dathang.sanpham_fk = sp.PK_SEQ   " +
			"\n group by khoxuat_fk, npp_fk, kbh_fk, sp.PK_SEQ, sp.TEN ,sp.dvdl_fk,dvCHUAN ";
			rs = db.get(query);

			Utility uilt_kho=new Utility();
			while(rs.next())
			{
				String khoID = rs.getString("kho_fk");
				String kbhID = rs.getString("kbh_fk");
				String nppID = rs.getString("npp_fk");
				String spID = rs.getString("PK_SEQ");
				String dvCHUAN=rs.getString("dvCHUAN");
				String DVDL_FK=rs.getString("DVDL_FK");
				double soluong = rs.getDouble("soluongXUAT");
				double tonkho = rs.getDouble("tonkho");

				String spten=rs.getString("ten");

				if(soluong > tonkho)
				{
					msg = "Sản phẩm ( " + rs.getString("TEN") + " ) với số lượng yêu cầu ( " + rs.getString("soluongXUAT") + " ) không đủ tồn kho ( " + rs.getString("tonkho") + " ). Vui lòng kiểm tra lại.";
					db.getConnection().rollback();
					rs.close();
					return msg;
				}

				String sqldh="select convert(char(10),Created_Date,126) NGAYGIO_TAO from ERP_DONDATHANGNPP where pk_Seq="+lsxId;
				String ngaytaodh="";
				ResultSet rsdh=db.get(sqldh);
				while (rsdh.next())
				{
					ngaytaodh=rsdh.getString("NGAYGIO_TAO");
				}
				rsdh.close();


				String msg1=uilt_kho.Update_NPP_Kho_Sp(ngaydonhang, "ErpDondathangDoitacSvl.java 414 " 
						, db, khoID, spID, nppID, kbhID, 0, soluong, (-1)* soluong, 0);
				if(msg1.length() >0){
					msg =msg1;
					db.getConnection().rollback();
					rs.close();
					return msg;
				}

				// đề xuất lô để booked ngay 

				query=		"\n select KHO_FK,SANPHAM_FK,KBH_FK,SOLO,NGAYHETHAN,NGAYNHAPKHO,available  from NHAPP_KHO_CHITIET "+  
							"\n where NPP_FK ="+nppID+" and KBH_FK= " +kbhID +
							"\n and KHO_FK="+khoID+"  and SANPHAM_FK =  "+ spID +
							"\n AND AVAILABLE >0  and NGAYNHAPKHO<='"+ngaydonhang+"'"+
							"\n order by NGAYHETHAN ,NGAYNHAPKHO,AVAILABLE ";
				ResultSet rssp=db.get(query);
				double soluongdenghi=soluong ;

				while(rssp.next() && soluongdenghi >0)
				{
					double soluong_avai= rssp.getDouble("AVAILABLE");
					double soluongcapnhat=0;
					if(soluong_avai >soluongdenghi){
						soluongcapnhat= soluongdenghi;
						soluongdenghi=0;
					}else{
						soluongcapnhat =soluong_avai;
						soluongdenghi =soluongdenghi - soluong_avai;
					}
					String solo=rssp.getString("SOLO");
					String ngaynhapkho=rssp.getString("ngaynhapkho");
					String ngayhethan=rssp.getString("ngayhethan");
					String _khoid=rssp.getString("kho_fk");
					String _kbhid=rssp.getString("KBH_FK");
					// cập nhật vào bảng đơn hàng sp _chitiet
					double soluongcapnhat_quydoi ;
					if(dvCHUAN.equals(DVDL_FK)){
						// nếu là đơn vị giống nhau
						soluongcapnhat_quydoi= soluongcapnhat;

					}else{
						query=" SELECT qc.SOLUONG1,qc.SOLUONG2 FROM QUYCACH qc WHERE SANPHAM_FK="+spID+" AND DVDL1_FK="+DVDL_FK+"  and qc.DVDL2_FK="+dvCHUAN;
						ResultSet rs1=db.get(query);
						if(rs1.next()){
							soluongcapnhat_quydoi = soluongcapnhat * rs1.getDouble("SOLUONG2")/ rs1.getDouble("SOLUONG1");

						}else{
							msg="Không thể xác định quy đổi của sản phẩm : "+rs.getString("ten");;
							db.getConnection().rollback();
							return msg;
						}
					}

					soluongcapnhat =soluongcapnhat;

					query = " Insert into ERP_DONDATHANGNPP_SANPHAM_CHITIET(donDAThang_fk ,   sanpham_fk,solo,ngaynhapkho,ngayhethan, soluong,dvdl_fk) "
						+ " values('" + lsxId + "', '" +spID + "','" +solo+ "','"+ngaynhapkho+"','"+ngayhethan+"',round("+soluongcapnhat_quydoi+",1) ,"+dvCHUAN+" )";

					if (!db.update(query)) 
					{
						msg="Không thể cập nhật : "+query;
						db.getConnection().rollback();
						rs.close();
						return msg;
					}


					msg1=uilt_kho.Update_NPP_Kho_Sp_Chitiet(ngaydonhang, "ErpDondathangDoitacSvl.java 489  DHID: "+lsxId,  db, _khoid, spID
							, npp_fk, _kbhid, ngaynhapkho, 0,soluongcapnhat,(-1)* soluongcapnhat, (-1)* soluongcapnhat, 0);
					if (msg1.length()> 0)
					{
						msg=msg1;
						db.getConnection().rollback();
						rs.close();
						return msg;
					}
				}
				rssp.close();

				if(soluongdenghi!=0){
					msg=  "Số lượng đề xuất trong lô chi tiết của sản phẩm "+spten+"   tới ngày (ngày cấu hình hóa đơn)"+ngaydonhang+" không còn đủ, " +
					" vui lòng kiểm tra báo cáo ( xuất nhập tồn,tồn hiện tại) theo lô để biết thêm chi tiết ";

					db.getConnection().rollback();
					rs.close();
					return msg;
				}
			}
			rs.close();


			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();
		}
		catch (Exception e) 
		{
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			db.shutDown();
			return "Exception: " + e.getMessage();
		}

		return "";
	}
	
	public String DeleteChuyenKho(String lsxId)
	{

		String msg = "";
		try
		{
			db.getConnection().setAutoCommit(false);

			

			String query = "update ERP_DondathangNPP set trangthai = '3' where trangthai = 0 and pk_seq = '" + lsxId + "'";
			if(db.updateReturnInt(query)!=1)
			{
				msg = "Error 1: " + query;
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
				return msg;
			}


			String msgRever = RevertBooked("Delete");
			if(msgRever.trim().length() >0)
			{
				msg = "Error 2: " + query;
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
				return msg;
			}
			

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return "Success!";

		}
		catch (Exception e) 
		{
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return "Exception: " + e.getMessage();
		}

		

	}
	
	
	public String Duyet() 
	{
		
		String msg = "";
		String  query = "";
		try
		{
			
			String check = geso.dms.distributor.util.Utility.Check_Huy_NghiepVu_KhoaSo_NPP(this.id,"NPP_FK","NgayDonHang","ERP_DondathangNPP", db);
			if(check.trim().length() >0)
				return check;
		
			
			
			
			db.getConnection().setAutoCommit(false);
			
			query = "update ERP_DondathangNPP set  nguoisua = '" + this.userId + "' where trangthai = 0 and pk_seq = '" + this.id + "'";
			if(db.updateReturnInt(query)<= 0)
			{
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
				return "Error 1:" + query;
			}
			
			String taoxuatkho = TaoPhieuXuatKho_NPP_Ban_AND_NhapHang_NPP_Mua() ;
			if(taoxuatkho.trim().length() >0)
			{
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
				return "Error 1:" + taoxuatkho;
			}
			
			
			
			query = "update ERP_DondathangNPP set trangthai = '4', NPP_DACHOT = '1', nguoisua = '" + userId + "' where trangthai = 0 and pk_seq = '" + this.id + "'";
			if(db.updateReturnInt(query)<= 0)
			{
				msg = "Khong the chot: " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			db.getConnection().commit();
			db.shutDown();
		}
		catch (Exception e) 
		{
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			db.shutDown();
			return "Exception: " + e.getMessage();
		}
		
		return "";
	}
	
	public String TaoPhieuXuatKho_NPP_Ban_AND_NhapHang_NPP_Mua() 
	{
		Utility util = new Utility();
		String query = "";
		String msg = "";
		
		try
		{
			//Tu dong tao YCXK  --> VA CHOT YCXK NAY LUON
			query = " insert ERP_YCXUATKHONPP(NgayYeuCau, ghichu, trangthai, npp_fk, kho_fk, xuatcho, npp_dat_fk, khachhang_fk, kbh_fk, ngaytao, nguoitao, ngaysua, nguoisua) " +
					" select  dh.ngaydonhang, N'Phiếu xuất kho(đơn hàng số: " + this.id + ")', '2', NPP_FK, dh.Kho_FK, " +
					" '0' as xuatcho, npp_dat_fk, khachhang_fk, kbh_fk, '" + getDateTime() + "', '" + this.userId + "', '" + getDateTime() + "', '" + this.userId + "' " +
					" from ERP_DONDATHANGNPP dh where pk_seq = '" + this.id + "' and dh.trangthai = 0 ";
			
			System.out.println("1.Insert YCXUATKHO: " + query);
			if(db.updateReturnInt(query)<=0)
			{
				msg = "Không thể tạo mới ERP_YCXUATKHONPP " + query;
				return msg;
			}
			
			String ycxkId = "";
			ResultSet rsYCXK = db.get("select scope_identity() as ycxkId");
			if(rsYCXK.next())
			{
				ycxkId = rsYCXK.getString("ycxkId");
			}
			rsYCXK.close();
			
			query = "Insert ERP_YCXUATKHONPP_DDH(ycxk_fk, ddh_fk) " +
					"select "+ycxkId+", pk_seq from ERP_DONDATHANGNPP where pk_seq in ( " + this.id + " )  ";
			if(db.updateReturnInt(query)<=0)
			{
				msg = "Không thể tạo mới ERP_YCXUATKHONPP_DDH " + query;
				return msg;
			}
			
			
			
			query = "insert ERP_YCXUATKHONPP_SANPHAM( ycxk_fk, sanpham_fk, soluongDAT, tonkho, daxuat, soluongXUAT, LOAI, SCHEME,dongia ) " +
					"select "+ycxkId+" yckxk,sanpham_fk,soluong, 0 tonkho, 0 daxuat, soluong slXuat,0 loai,isnull(scheme,'') scheme,dongia from ERP_DONDATHANGNPP_SANPHAM where dondathang_fk =  " + this.id ;		
			if(db.updateReturnInt(query)<=0)
			{
				msg = "Khong the tao moi ERP_YCXUATKHO_SANPHAM: " + query;
				//db.getConnection().rollback();
				return msg;
			}
			query  = "\n select b.soluongXUAT,a.NgayYeuCau,a.kho_fk,a.npp_fk, a.kbh_fk, b.sanpham_fk "+
					 "\n from ERP_YCXUATKHONPP a " +
					 "\n inner join ERP_YCXUATKHONPP_SANPHAM b on a.pk_seq = b.ycxk_fk " +
					 "\n where  b.ycxk_fk = "+ ycxkId;
			ResultSet khoTongRs = db.get(query);
			while(khoTongRs.next())
			{
				String _kho_fk=khoTongRs.getString("kho_fk");
				String _nppId=khoTongRs.getString("npp_fk");	
				String _kbh_fk=khoTongRs.getString("kbh_fk");
				String _sanpham_fk=khoTongRs.getString("sanpham_fk");
				String ngaynhap  =khoTongRs.getString("NgayYeuCau");
				Double soluong = khoTongRs.getDouble("soluongXUAT");
				
				msg=util.Update_NPP_Kho_Sp(ngaynhap,this.id," Trừ ĐƠN HÀNG NPP_", db, _kho_fk, _sanpham_fk, _nppId, _kbh_fk, (-1)*soluong, (-1)*soluong, 0, 0.0);
				if(msg.length()>0)
				{
					return "Error trừ kho: " + msg;
					
				}	
			}
			//////
			
			query = "insert ERP_YCXUATKHONPP_SANPHAM_CHitiet(  ycxk_fk, sanpham_fk,soluong,tonkho,LOAI,SCHEME,ngaynhapkho,DONGIA ) " +
			"select "+ycxkId+"  ycxk_fk, SANPHAM_FK,soluong,0 tonkho, LOAI, scheme,ngaynhapkho,DONGIA  from ERP_DONDATHANGNPP_SANPHAM_Chitiet where dondathang_fk = " + this.id ;		
			if(db.updateReturnInt(query)<=0)
			{
				msg = "Khong the tao moi ERP_YCXUATKHONPP_SANPHAM_CHitiet: " + query;
				//db.getConnection().rollback();
				return msg;
			}
			query  = "\n select b.ngaynhapkho,b.soluong,a.NgayYeuCau,a.kho_fk,a.npp_fk, a.kbh_fk, b.sanpham_fk "+
					 "\n from ERP_YCXUATKHONPP a " +
					 "\n inner join ERP_YCXUATKHONPP_SANPHAM_CHitiet b on a.pk_seq = b.ycxk_fk " +
					 "\n where  b.ycxk_fk = "+ ycxkId;
			ResultSet khoCTRs = db.get(query);
			while(khoCTRs.next())
			{
				String _kho_fk=khoCTRs.getString("kho_fk");
				String _nppId=khoCTRs.getString("npp_fk");	
				String _kbh_fk=khoCTRs.getString("kbh_fk");
				String _sanpham_fk=khoCTRs.getString("sanpham_fk");
				String ngaynhap  =khoCTRs.getString("NgayYeuCau");
				Double soluong = khoCTRs.getDouble("soluong");
				String ngaynhapkho  =khoCTRs.getString("ngaynhapkho");
				System.out.println("_sanpham_fk = "+ _sanpham_fk + ",ngaynhapkho = "+ ngaynhapkho);
				String msg1=util.Update_NPP_Kho_Sp_Chitiet(ngaynhap," trừ kho ĐƠN HÀNG NPP chi tiết_"+this.id ,  db, _kho_fk, _sanpham_fk, _nppId, _kbh_fk, ngaynhapkho,(-1)* soluong,(-1)* soluong,0, 0, 0);
				if(msg1.length()>0)
				{
					return "Error trừ kho: " + msg1;
					
				}	
			}
			
			//Tu dong tao nhan hang
			query = " insert NHAPHANG(NGAYNHAN, NGAYCHUNGTU, NPP_FK, NCC_FK, GSBH_FK, ASM_FK, BM_FK, DVKD_FK, KBH_FK, YCXKNPP_FK, TRANGTHAI, NGUOITAO, NGAYTAO, NGUOISUA, NGAYSUA) " +
			" select distinct NgayYeuCau, NgayYeuCau, NPP_DAT_FK,  " +
			" 			( select top(1) NCC_FK from NHACUNGCAP_DVKD where PK_SEQ in ( select NCC_DVKD_FK from NHAPP_NHACC_DONVIKD where NPP_FK = a.NPP_DAT_FK ) ), " +
			"			( select top(1) GSBH_FK from NHAPP_GIAMSATBH where NPP_FK = a.NPP_DAT_FK ), " +
			"			( select top(1) ASM_FK from ASM_KHUVUC where KHUVUC_FK in ( select KHUVUC_FK from NHAPHANPHOI where PK_SEQ = a.NPP_DAT_FK )), " +
			"			( select top(1) BM_FK from BM_CHINHANH where VUNG_FK in ( select VUNG_FK from KHUVUC where PK_SEQ in (  select KHUVUC_FK from NHAPHANPHOI where PK_SEQ = a.NPP_DAT_FK ) ) ), " +
			" 	   '100001' as DVKD_FK, a.KBH_FK, '" + ycxkId + "', '0', '" + this.userId + "', '" + getDateTime() + "', '" + this.userId + "', '" + getDateTime() + "' " +
			" from ERP_YCXUATKHONPP a inner join ERP_YCXUATKHONPP_SANPHAM b on a.PK_SEQ = b.ycxk_fk " +
			" where a.PK_SEQ = '" + ycxkId + "' ";

			System.out.println("---INSERT NHAN HANG: " + query );
			if(db.updateReturnInt(query)<=0)
			{
				msg = "Không tạo mới NHAPHANG " + query;
				//db.getConnection().rollback();
				return msg;
			}

			query = " insert NHAPHANG_SP(NgayNhapKho,NHAPHANG_FK, SANPHAM_FK, SOLUONG, soluongNHAN,GiaNet, DONGIA, CHIETKHAU, DVDL_FK, LOAI, SCHEME, SOLO, NGAYHETHAN, KHONHAN_FK ) " +
			" select a.NgayYeuCau,( select pk_seq from NHAPHANG where YCXKNPP_FK = a.PK_SEQ  ),  " +
			" 		b.sanpham_fk, b.soluong, NULL, " +
			"	   b.dongia, b.dongia  " +
			"	   , 0 as chietkhau, c.DVDL_FK, b.LOAI, b.SCHEME, b.solo, b.ngayhethan, " +
			"      case when b.loai = 1 then (select kho_fk from CTKHUYENMAI x where x.SCHEME = b.SCHEME)   else 100000 end as KHONHAN_FK	 " +
			" from ERP_YCXUATKHONPP a inner join ERP_YCXUATKHONPP_SANPHAM_CHITIET b on a.PK_SEQ = b.ycxk_fk " +
			" 		inner join SANPHAM c on b.sanpham_fk = c.PK_SEQ   " +
			" where a.PK_SEQ = '" + ycxkId + "' and b.soluong > 0 ";

			System.out.println("---INSERT NHAN HANG - SP: " + query );
			//String updateMsg = db.updateMsg(query);
			if(db.updateReturnInt(query)<=0)
			{
				msg = "Không tạo mới NHAPHANG_SP " + query;
				//db.getConnection().rollback();
				return msg;
			}

			query = "insert NHAPHANG_DDH(nhaphang_fk, ddh_fk)  " +
			"select ( select PK_SEQ from NHAPHANG where YCXKNPP_FK = '" + ycxkId + "' ) as nhID, "+this.id+" ";
			if(db.updateReturnInt(query)<=0)
			{
				msg = "Không tạo mới NHAPHANG_DDH " + query;
				//db.getConnection().rollback();
				return msg;
			}
			
		} 
		catch (Exception e) {
			
			e.printStackTrace();
			return "Không thể duyệt đơn hàng " + e.getMessage();
		}
		
		return "";
	}
}
