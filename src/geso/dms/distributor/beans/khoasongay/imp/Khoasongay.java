package geso.dms.distributor.beans.khoasongay.imp;

import geso.dms.center.db.sql.Idbutils;
import geso.dms.center.util.Utility;
import geso.dms.distributor.beans.khoasongay.IKhoasongay;
import geso.dms.distributor.db.sql.dbutils;
import geso.dms.distributor.servlets.chuyenkhonew.ChuyenKhoSvl;
import geso.dms.distributor.servlets.dontrahang.DontrahangSvl;
import geso.dms.distributor.util.FixData;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Khoasongay implements IKhoasongay , Serializable
{
	private static final long serialVersionUID = -9217977546733610214L;
	String userId;	
	String ngaykhoaso;
	String msg;

	String nppId;
	String nppTen;
	String sitecode;
	String tuthang = "";
	String tunam = "";
	//don hang chua chot
	ResultSet dhcclist;

	//don hang da xuat kho
	ResultSet dhdxklist;

	//don hang da chot
	ResultSet dhdclist;

	String ngayksgn;
	
	boolean isPxkChuaChot;
	boolean isPthChuaChot;
	dbutils db;
	
	String ngaycuoithang;
	String ngayhientai;
	String thanksgn;
	
	String namksgn;

	public Khoasongay()
	{
		this.ngaykhoaso = this.getDateTime();
		this.ngayksgn = "";
		this.msg = "";
		this.ngaycuoithang="";
		this.ngayhientai="";
		this.thanksgn="";
		this.namksgn="";
		this.isPthChuaChot = false;
		this.isPxkChuaChot = false;
		db = new dbutils();
	}

	public String getNgaycuoithang() {
		return ngaycuoithang;
	}

	public void setNgaycuoithang(String ngaycuoithang) {
		this.ngaycuoithang = ngaycuoithang;
	}

	public String getNgayhientai() {
		return ngayhientai;
	}

	public void setNgayhientai(String ngayhientai) {
		this.ngayhientai = ngayhientai;
	}

	public String getUserId() 
	{		
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;		
	}

	public String getNgaygiao() 
	{		
		return this.ngaykhoaso;
	}

	public void setNgaygiao(String ngaykhoaso) 
	{
		this.ngaykhoaso = ngaykhoaso;
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
		geso.dms.distributor.util.Utility util=new geso.dms.distributor.util.Utility();
		this.nppId=util.getIdNhapp(this.userId);
		this.nppTen=util.getTenNhaPP();
		this.sitecode=util.getSitecode();
	}

	public String getNgaykhoaso()
	{	
		return this.ngaykhoaso;
	}

	public void setNgaykhoaso(String ngaykhoaso)
	{
		this.ngaykhoaso = ngaykhoaso;	
	}

	public String getMessege() 
	{
		return this.msg;
	}

	public void setMessege(String msg)
	{		
		this.msg = msg;
	}

	public ResultSet getDhChuaChotList() 
	{	
		return this.dhcclist;
	}

	public void setDhChuaChotList(ResultSet dhcclist) 
	{
		this.dhcclist = dhcclist;		
	}

	public ResultSet getDhDaXuatKhoList() 
	{	
		return this.dhdxklist;
	}

	public void setDhDaXuatKhoList(ResultSet dhdxklist) 
	{
		this.dhdxklist = dhdxklist;	
	}

	public ResultSet getDhDaChotList() 
	{	
		return this.dhdclist;
	}

	public void setDhDaChotList(ResultSet dhdclist) 
	{
		this.dhdclist = dhdclist;	
	}
	
	

	
	public boolean KhoaSoNgay(String ngayks) 
	{	
		try
		{
			String query = "";
			init();
			db.getConnection().setAutoCommit(false);
			String thangks = "";
			
			if(this.tuthang.length() <= 0 || this.tunam.length() <= 0)
			{
				db.getConnection().rollback();
				this.msg = "Vui lòng chọn lại tháng khóa sổ !";
				return false;
			}
			if(this.ngaykhoaso.trim().length() <=0)
			{
				db.getConnection().rollback();
				this.msg = "Chưa lấy được ngày khóa sổ!";
				return false;
			}
			String sql ="select count(*) sl from nhaphang where trangthai=0";
			ResultSet checkRs=db.get(sql);
			checkRs.next();
			db.getConnection().setAutoCommit(false);
			int check =checkRs.getInt("sl");
			if(check>0){
				String sql1 = "update nhaphang set trangthai = 0 where trangthai=0 ";
				if(db.updateReturnInt(sql1)!=check) {
					this.msg = "Không thể khóa sổ khi đang chốt phiếu xuất kho";
					db.getConnection().rollback();
					return false;
				}
			}
			query="select DATEDIFF (dd,'"+this.ngaykhoaso+"','"+this.ngayhientai+"') as giatri,DATEDIFF (mm,'"+this.ngaykhoaso+"','"+this.ngayhientai+"') as giatri1 ";
			ResultSet rs = db.get(query);
			rs.next();
			int giatri=rs.getInt("giatri");
			int giatri1=rs.getInt("giatri1");
			System.out.println(query);
			if(giatri < 0 || giatri1 <0 )
				{
					db.getConnection().rollback();
					this.msg = "Bạn không được khóa số vượt ngày hiện tại!!! ";
					return false;
				}
			
			
			query = " select 1 where  "+this.tuthang+" + 12 * "+this.tunam+" >=  month(getdate()) + year(getdate()) * 12   ";
			 rs = db.get(query);
			if(rs.next())
			{
				db.getConnection().rollback();
				this.msg = "Vui lòng chọn lại tháng khóa sổ, tháng khóa sổ phải nhỏ hơn tháng hiện tại !";
				return false;
			}
	
			query =" select * from khoasongay where npp_fk ="+this.nppId + "  and ngayks ='"+this.ngaykhoaso+"' ";
			rs = db.get(query);
			if(rs.next())
			{
				db.getConnection().rollback();
				this.msg =  this.tuthang + "/" + this.tunam + " đã khóa sổ rồi !";
				return false;
			}
			query = " select * from dieuchinhtonkho where NGAYDC <= '"+this.ngaykhoaso+"' and trangthai = 0  and npp_fk ="+this.nppId + " ";
			rs = db.get(query);
			if(rs.next())
			{
				System.out.println("query check = "+ query);
				db.getConnection().rollback();
				this.msg =  this.tuthang + "/" + this.tunam + " - Vui lòng xử lý các nghiệp vụ Điều chỉnh tồn kho trước khi khóa sổ !";
				return false;
			}
			query = " select * from nhaphang where ngaynhan <= '"+this.ngaykhoaso+"' and trangthai = 0  and npp_fk ="+this.nppId + " ";
			rs = db.get(query);
			if(rs.next())
			{
				System.out.println("query check = "+ query);
				db.getConnection().rollback();
				this.msg =  this.tuthang + "/" + this.tunam + " - Vui lòng xử lý các nghiệp vụ nhận hàng trước khi khóa sổ !";
				return false;
			}
			
			query = " select * from dontrahang where NGAYTRA <= '"+this.ngaykhoaso+"' and trangthai in (0,1)  and npp_fk ="+this.nppId + " ";
			rs = db.get(query);
			if(rs.next())
			{
				System.out.println("query check = "+ query);
				db.getConnection().rollback();
				this.msg =  this.tuthang + "/" + this.tunam + " - Vui lòng xử lý các nghiệp vụ trả hàng về NCC trước khi khóa sổ !";
				return false;
			}
			query = " select * from donhang where NGAYNHAP <= '"+this.ngaykhoaso+"' and trangthai = 0  and npp_fk ="+this.nppId + " ";
			rs = db.get(query);
			if(rs.next())
			{
				System.out.println("query check = "+ query);
				db.getConnection().rollback();
				this.msg =  this.tuthang + "/" + this.tunam + " - Vui lòng xử lý các đơn hàng trước khi khóa sổ !";
				return false;
			}
			
			
			query = " select * from DONHANGTRAVE where donhang_fk is null and NGAYNHAP <= '"+this.ngaykhoaso+"' and trangthai in (0,1)  and npp_fk ="+this.nppId + " ";
			rs = db.get(query);
			if(rs.next())
			{
				System.out.println("query check = "+ query);
				db.getConnection().rollback();
				this.msg =  this.tuthang + "/" + this.tunam + " - Vui lòng xử lý các đơn trả hàng lẻ trước khi khóa sổ !";
				return false;
			}
			
			
			query = " select * from  DONHANGTRAVE a inner join DONHANG b on a.DONHANG_FK=b.PK_SEQ  where b.NGAYNHAP <= '"+this.ngaykhoaso+"' and a.trangthai in (0,1)  and a.npp_fk ="+this.nppId + " ";
			rs = db.get(query);
			if(rs.next())
			{
				System.out.println("query check = "+ query);
				db.getConnection().rollback();
				this.msg =  this.tuthang + "/" + this.tunam + " - Vui lòng xử lý các đơn trả hàng nguyên đơn  trước khi khóa sổ !";
				return false;
			}
			
			query = " select * from   ufn_XNT_TuNgay_DenNgay_FULL_Chitiet_New("+this.nppId+", '"+this.tungay+"','"+this.ngaykhoaso+"' ) a "+
					" where ( cuoiky <0 or dauky < 0 ) "  ;
			rs = db.get(query);
			if(rs.next())
			{
				db.getConnection().rollback();
				this.msg =  this.tuthang + "/" + this.tunam + " -bị âm xnt chi tiết vui lòng liên hệ admin để khắc phục !";
				return false;
			}
			query = " select * from   ufn_XNT_TuNgay_DenNgay_FULL_New("+this.nppId+", '"+this.tungay+"','"+this.ngaykhoaso+"' ) a "+
			" where ( cuoiky <0 or dauky < 0 ) "  ;
			rs = db.get(query);
			if(rs.next())
			{
				db.getConnection().rollback();
				this.msg =  this.tuthang + "/" + this.tunam + " -bị âm xnt tổng vui lòng liên hệ admin để khắc phục !";
				return false;
			}
			query = " insert TONKHONGAY(KHO_FK,NPP_FK,SANPHAM_FK,NGAY,SOLUONG,KBH_FK) " +
					"  select kho_fk,npp_fk,sanpham_fk,'"+this.ngaykhoaso+"',cuoiky,kbh_fk" +
					"  from ufn_XNT_TuNgay_DenNgay_FULL_New("+this.nppId+", '"+this.tungay+"','"+this.ngaykhoaso+"' ) a ";
			if(db.updateReturnInt(query) < 0)
			{
				db.getConnection().rollback();
				this.msg =  this.tuthang + "/" + this.tunam + " - không thể lưu tồn kho ngày !";
				return false;
			}
			
			query = " insert TONKHONGAY_CHITIET(KHO_FK,NPP_FK,SANPHAM_FK,NGAY,SOLUONG,KBH_FK,ngaynhapkho) " +
			"  select kho_fk,npp_fk,sanpham_fk,'"+this.ngaykhoaso+"',cuoiky,kbh_fk,ngaynhapkho " +
			"  from ufn_XNT_TuNgay_DenNgay_FULL_Chitiet_New("+this.nppId+", '"+this.tungay+"','"+this.ngaykhoaso+"' ) a ";
			if(db.updateReturnInt(query) < 0)
			{
				db.getConnection().rollback();
				this.msg =  this.tuthang + "/" + this.tunam + " - không thể lưu tồn kho ngày chi tiết !";
				return false;
			}
			query = " insert KHOASONGAY(NGAYKSGANNHAT,NGAYKS,NGAYTAO,NGUOITAO,NPP_FK,chon,doanhso,Created_Date)  " +
					"  select NULL,'"+this.ngaykhoaso+"','"+Utility.getNgayHienTai()+"',"+this.userId+","+this.nppId+",0,0,getdate() ";
			if(db.updateReturnInt(query) !=1)
			{
				db.getConnection().rollback();
				this.msg =  this.tuthang + "/" + this.tunam + " - Không thể tạo mới ngày khóa sổ !";
				return false;
			}
			this.msg= this.tuthang + "/" + this.tunam + " - khóa sổ thành công !";
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return true;
		}catch (Exception e) {
			 e.printStackTrace();
				this.msg =  this.tuthang + "/" + this.tunam + " - Exception:"+e.getMessage()+"!";
			 Utility.rollback_throw_exception(db);
			 return false;
		}
		
		
		
	

	}
	

	
	

	
	String tungay = "";
	public void init() 
	{
		this.getNppInfo();
		try 
		{
			String query =  " 	select top 1 convert(varchar(10), DATEADD(dd,1,ngayks),120) tungay ,convert(varchar(10), DATEADD (dd, -1, DATEADD(mm, DATEDIFF(mm, 0, DATEADD(dd,1,ngayks)) + 1, 0)) ,120) ngayks " +
							" 		,month (ngayks) thangksgn,  year (ngayks) namksgn  ,month (DATEADD(dd,1,ngayks)) thangks,  year (DATEADD(dd,1,ngayks)) namks  " +
							",convert (char(10),DATEADD(DAY, -(DAY(GETDATE())), GETDATE()),126) as ngaycuoicuathang ,convert (char(10),GETDATE(),126) as ngayhientai"+
							" 	from KHOASONGAY where NPP_FK = "+this.nppId+" order by NGAYKS desc "			 ;
			System.out.println("query "+query);
			ResultSet rs =db.get(query);
			
			if(rs != null)
			{
				rs.next();
				this.tungay= rs.getString ("tungay");
				this.tuthang = rs.getString ("thangks");
				this.tunam = rs.getString ("namks");
				this.ngayksgn = rs.getString ("thangksgn") +"/" + rs.getString ("namksgn");
				this.ngaykhoaso = rs.getString("ngayks");
				this.ngaycuoithang=rs.getString("ngaycuoicuathang");
				this.ngayhientai=rs.getString("ngayhientai");
				this.thanksgn=rs.getString("thangksgn");
				this.namksgn=rs.getString("namksgn");
				rs.close();
			}

		
		} 
		catch(Exception e) { }
	}

	private void createDhdcList()
	{
		String query = "select a.pk_seq as dhId, a.ngaynhap, a.trangthai, a.tonggiatri,b.smartid, b.pk_seq as khId, b.ten as khTen from donhang a inner join khachhang b on a.khachhang_fk = b.pk_seq where a.ngaynhap = '" + this.ngaykhoaso + "' and a.npp_fk = '" + this.nppId + "' and a.trangthai = '1' and a.tinhtrang = '0'";
		this.dhdclist = db.get(query);
	}

	private void createDhdxkList()
	{
		String query = "select a.pk_seq as dhId, a.ngaynhap, a.trangthai, a.tonggiatri,b.smartid, b.pk_seq as khId, b.ten as khTen from donhang a inner join khachhang b on a.khachhang_fk = b.pk_seq where a.ngaynhap = '" + this.ngaykhoaso + "' and a.npp_fk = '" + this.nppId + "' and a.trangthai = '3' and a.tinhtrang = '0'";
		this.dhdxklist = db.get(query);
	}

	private void createDhccList() 
	{
		String query = "select a.pk_seq as dhId, a.ngaynhap, a.trangthai, a.tonggiatri,b.smartid, b.pk_seq as khId, b.ten as khTen from donhang a inner join khachhang b on a.khachhang_fk = b.pk_seq where a.ngaynhap = '" + this.ngaykhoaso + "' and a.npp_fk = '" + this.nppId + "' and a.trangthai = '0' and a.tinhtrang = '0'";
		this.dhcclist = db.get(query);

		//check phieuxuatkho, phieuthuhoi
		query = "select count(*) as sodong from phieuxuatkho where npp_fk = '" + this.nppId + "' and ngaylapphieu = '" + this.ngaykhoaso + "' and trangthai = '0'";
		System.out.println("Query pxk: " + query + "\n");
		ResultSet rs = db.get(query);
		try 
		{
			if(rs.next())
			{
				if(rs.getInt("sodong") > 0)
					this.isPxkChuaChot = true;
				rs.close();
			}

			query = "select count(pk_seq) as sodong from phieuthuhoi where npp_fk = '" + this.nppId + "' and ngaytao = '" + this.ngaykhoaso + "' and trangthai = '0'";
			System.out.println("Query pth: " + query + "\n");
			ResultSet rsTh = db.get(query);
			if(rsTh.next())
			{
				if(rs.getInt("sodong") > 0)
					this.isPthChuaChot = true;
				rsTh.close();
			}
		} 
		catch(Exception e) {}
	}

	public String getNgayKhoaSoGanNhat() 
	{
		return this.ngayksgn;
	}

	public void setNgayKhoaSoGanNhat(String nksgn) 
	{
		this.ngayksgn = nksgn;
	}

	public void createRs() 
	{
		this.getNppInfo();

		this.createDhccList();
		this.createDhdxkList();
		this.createDhdcList();

		if(this.isPxkChuaChot)
			this.msg += "\n+ Co phieu xuat kho chua chot trong ngay khoa so.";
		if(this.isPthChuaChot)
			this.msg += "\n+ Co phieu thu hoi chua chot trong ngay khoa so.";
	}

	public String checkNgayChot(String ngayksgn, String ngayks)
	{
		ResultSet rs = db.get("select top(1) ngayks as ngay from khoasongay where npp_fk = '" + this.nppId + "' and ngayks > '" + ngayks + "' order by ngayks DESC");
		String ngayGanNhat = "";
		if(rs != null)
		{
			try 
			{
				rs.next();
				ngayGanNhat = rs.getString("ngay");
			} 
			catch(Exception e) { return ""; }
			finally{try {
				if(rs!= null)
					rs.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}}
		}
		return ngayGanNhat;
	}

	private String getDateTime() 
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);	
	}


	public void DBclose() {


		try {

			if(this.dhcclist != null)
				this.dhcclist.close();
			if(this.dhdclist != null)
				this.dhdclist.close();
			if(this.dhdxklist != null)
				this.dhdxklist.close();
			if(this.db != null)
				this.db.shutDown();


		} catch (Exception e) {
			// TODO: handle exception
		}

	}


	public boolean isPxkChuaChot() 
	{
		return this.isPxkChuaChot;
	}

	public void setIsPxkChuaChot(boolean pxkChuaChot) 
	{
		this.isPxkChuaChot = pxkChuaChot;
	}

	public boolean isPthChuaChot() 
	{
		return this.isPthChuaChot;
	}

	public void setIsPthChuaChot(boolean pthChuaChot) 
	{
		this.isPthChuaChot = pthChuaChot;
	}
	private String TangNgayKs()
	{
		String ngayks = this.ngayksgn;

		if (ngayks.equals(""))
			ngayks = this.getDateTime();

		String[] ngay = ngayks.split("-");

		Calendar c2 = Calendar.getInstance();

		// trong java thang bat dau bang 0 (thang rieng)
		c2.set(Integer.parseInt(ngay[0]), Integer.parseInt(ngay[1]) - 1, Integer.parseInt(ngay[2]));
		c2.add(Calendar.DATE, 1);

		String month = Integer.toString(c2.get(Calendar.MONTH) + 1);
		if ((c2.get(Calendar.MONTH) + 1) < 10)
		{
			month = "0" + Integer.toString(c2.get(Calendar.MONTH) + 1);
		}
		String date = Integer.toString(c2.get(Calendar.DATE));
		if ((c2.get(Calendar.DATE)) < 10)
		{
			date = "0" + Integer.toString(c2.get(Calendar.DATE));
		}

		System.out.println("ngay chon:" + Integer.toString(c2.get(Calendar.YEAR)) + "-" + month + "-" + date);
		return Integer.toString(c2.get(Calendar.YEAR)) + "-" + month + "-" + date;
	}


	public void setFromMonth(String month) {

		this.tuthang = month;
	}


	public String getFromMonth() {

		return this.tuthang;
	}


	public void setFromYear(String fromyear) {

		this.tunam = fromyear;
	}


	public String getFromYear() {

		return this.tunam;
	}
	private int CompareDATE(String _date1, String _date2)
	{
		try
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			//Date date1 = sdf.parse("2014-10-01");
			//Date date2 = sdf.parse("2014-10-01");

			Date date1 = sdf.parse(_date1);
			Date date2 = sdf.parse(_date2);

			//System.out.println(sdf.format(date1));
			//System.out.println(sdf.format(date2));

			return date1.compareTo(date2);
		}
		catch (Exception e) {
			return 0;
		}

	}

	@Override
	public String getThanksgn() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setThanksgn(String thanksgn) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getNamksgn() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setNamksgn(String namksgn) {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args) {
		
			/*if(1==1)
				return;*/
			
			String user_khoa_so_tu_dong = "100003";
			dbutils db = new dbutils();
			try
			{
				String query =  "\n select npp.pk_seq nppId , '2020-01-14' NGAYKS " +
								"\n		, convert( VARCHAR(10), DATEADD(dd,1, ks.ngayks) ,120)  tungay " +
								"\n from nhaphanphoi npp  " +
								"\n outer apply " +
								"\n ( " +
								"\n 	select max(ngayks)ngayks " +
								"\n 	from khoasongay ks " +
								"\n 	where ks.NPP_FK = npp.PK_SEQ " +
								"\n ) ks " +
								"\n where npp.khoasotudong = 1 and npp.trangthai = 1 and npp.pk_seq = 1000653 ";//and npp.pk_seq in (1000561,1000560)
				System.out.println("query kstd = "+ query);
				ResultSet rs = db.get(query);
				while(rs.next())
				{
					String error = "";
					String nppId = rs.getString("nppId");
					String tungay = rs.getString("tungay");
					String NGAYKS = rs.getString("NGAYKS");
					
					if(tungay != null)
					{
						
						error = KhoaSoTuDong_NPP( db, nppId,NGAYKS, tungay,user_khoa_so_tu_dong,1);
					}
					else
					{
						error = "Chưa có tồn đầu";
					}
					
					if(error.trim().length() > 0)
					{
						query = "\n insert KhoaSoNgay_ThatBai ( NPP_FK,ngay,msg,nguoitao )  " +
								"\n	select  "+nppId+", '"+NGAYKS+"', N'"+error+"',  " + user_khoa_so_tu_dong;
						System.out.println("query err = "+ query);
						db.update(query);
					}
					
				}
				
			}
			catch (Exception e) {
				
			}
			db.shutDown();
		
	}
	
	public static void KhoaSoTuDong()
	{
		/*if(1==1)
			return;*/
		
		String user_khoa_so_tu_dong = "100003";
		dbutils db = new dbutils();
		try
		{
			String query =  "\n select npp.pk_seq nppId , convert( VARCHAR(10), DBO.GETLOCALDATE(DEFAULT),120) NGAYKS " +
							"\n		, convert( VARCHAR(10), DATEADD(dd,1, ks.ngayks) ,120)  tungay " +
							"\n from nhaphanphoi npp  " +
							"\n outer apply " +
							"\n ( " +
							"\n 	select max(ngayks)ngayks " +
							"\n 	from khoasongay ks " +
							"\n 	where ks.NPP_FK = npp.PK_SEQ " +
							"\n ) ks " +
							"\n where npp.khoasotudong = 1 and npp.trangthai = 1  ";//and npp.pk_seq in (1000561,1000560)
			System.out.println("query kstd = "+ query);
			ResultSet rs = db.get(query);
			while(rs.next())
			{
				String error = "";
				String nppId = rs.getString("nppId");
				String tungay = rs.getString("tungay");
				String NGAYKS = rs.getString("NGAYKS");
				
				if(tungay != null)
				{
					
					error = KhoaSoTuDong_NPP( db, nppId,NGAYKS, tungay,user_khoa_so_tu_dong,1);
				}
				else
				{
					error = "Chưa có tồn đầu";
				}
				
				if(error.trim().length() > 0)
				{
					query = "\n insert KhoaSoNgay_ThatBai ( NPP_FK,ngay,msg,nguoitao )  " +
							"\n	select  "+nppId+", '"+NGAYKS+"', N'"+error+"',  " + user_khoa_so_tu_dong;
					System.out.println("query err = "+ query);
					db.update(query);
				}
				
			}
			
		}
		catch (Exception e) {
			
		}
		db.shutDown();
	}
	
	
	public static String Doi_ngay_DONTRAHANG(Idbutils db,String nppId,String ngayks, String tungay,String userId)
	{
		
		try
		{
			
			// nhả book toàn bộ đơn đã chuyển lên HO
			String query =  "\n select th.PK_SEQ from DONTRAHANG th where th.TRANGTHAI = 1 and th.NPP_FK = "+nppId+" and th.NGAYTRA >='"+tungay+"' and th.NGAYTRA <='"+ngayks+"'  ";
			ResultSet rs= db.get(query);
			while(rs.next())
			{
				String id = rs.getString("PK_SEQ");
				String msg = DontrahangSvl.Nha_Book_NPP(id, db);
				if(msg.trim().length() > 0)
				{
					return "Lỗi trả hàng NCC:" + msg;
				}
			}
			rs.close();
		
			// dời ngày và chuyển toàn bộ về chưa chốt
			query = "\n update DONTRAHANG set trangthai = 0,NGAYTRA = convert( VARCHAR(10), DATEADD(dd,1, '"+ngayks+"') ,120), nguoisua = '" + userId + "', Modified_Date =  dbo.getlocaldate(default) " +
					"\n where trangthai in (0,1)  and Npp_fk = "+nppId+" and NGAYTRA <='"+ngayks+"' and NGAYTRA >='"+tungay+"' ";
			if (db.updateReturnInt(query)  < 0)
			{
				return " Không dời được ngày trả hàng NCC !";				
			}
			
			return "";
		}
		catch (Exception e) {
			return "Lỗi trả hàng NCC: " + e.getMessage();
		}

	}
	
	public static String KhoaSo_NPP(Idbutils db,String nppId,String ngayks, String tungay,String userId, int auto)
	{
		try
		{
			String query = "";
			ResultSet rs;
			if(tungay.compareTo( Utility.getNgayHienTai() ) > 0)
			{
				return " Không thể khóa sổ ngày tương lai !";
			}
			
			if(tungay.compareTo( Utility.getNgayHienTai() ) == 0)
			{/*
				
				query = " select 1 where datepart(hour, dbo.GetLocalDate(default)) < 22 ";
				rs = db.get(query);
				if(rs.next())
				{
					rs.close();
					return "Chỉ có thể khóa sổ ngày hiện tại sau 22h !";
					
				}
				rs.close();
			*/}
			
			
			
			
			query = " select pk_seq from KHOASONGAY where  npp_fk =  "+nppId+" and  NGAYKS = '"+ngayks+"' ";
			rs = db.get(query);			
			if(rs.next())
			{
				rs.close();
				System.out.println("qu = "+ query);
				return "  Ngày ("+ngayks+") đã khóa sổ !";
			}
			
			// nhập hàng
			
			query = 	"\n update NHAPHANG set NGAYNHAN = convert( VARCHAR(10), DATEADD(dd,1, '"+ngayks+"') ,120), nguoisua = '" + userId + "', ngaysua = dbo.getlocaldate(default)  " +
						"\n where trangthai in (0)  and Npp_fk = "+nppId+" and  NGAYNHAN <='"+ngayks+"' and NGAYNHAN >='"+tungay+"' ";
			if (db.updateReturnInt(query)  < 0)
			{
				System.out.println("qu = "+ query);
				return "  Lỗi dời ngày nhận hàng KH !";
			}
			System.out.println("NHAPHANG pass " + nppId + " " + ngayks + " tungay : " + tungay);
			// đơn hàng
			
			query = "\n delete dhkm  " +
					"\n from DONHANG_CTKM_TRAKM dhkm " +
					"\n inner join DONHANG dh on dhkm.DONHANGID = dh.PK_SEQ " +
					"\n where isnull(dhkm.ap_dung,0) = 0 and dh.trangthai = 0 and dh.Npp_fk = "+nppId+" and dh.ngaynhap <='"+ngayks+"' and dh.ngaynhap >='"+tungay+"' " +
					"\n 		and not exists ( select 1 from CTKHUYENMAI ct where ct.pk_seq = dhkm.ctkmId and ct.TUNGAY <= convert( VARCHAR(10), DATEADD(dd,1, '"+ngayks+"') ,120) " +
					"\n					and ct.DENNGAY >=convert( VARCHAR(10), DATEADD(dd,1, '"+ngayks+"') ,120) ) ";
			if(db.updateReturnInt(query) < 0)
			{
				return "  Lỗi dời ngày đơn hàng 1 !";
			}
			
			query = "\n delete dhkm  " +
					"\n from donhang_ctkm_dkkm dhkm " +
					"\n inner join DONHANG dh on dhkm.donhang_fk = dh.PK_SEQ " +
					"\n where dh.trangthai = 0 and dh.Npp_fk = "+nppId+" and dh.ngaynhap <='"+ngayks+"' and dh.ngaynhap >='"+tungay+"' " +
					"\n 		and not exists ( select 1 from CTKHUYENMAI ct where ct.pk_seq = dhkm.ctkm_fk and ct.TUNGAY <= convert( VARCHAR(10), DATEADD(dd,1, '"+ngayks+"') ,120) " +
					"\n					and ct.DENNGAY >=convert( VARCHAR(10), DATEADD(dd,1, '"+ngayks+"') ,120) ) ";
			if(db.updateReturnInt(query) < 0)
			{
				return "  Lỗi dời ngày đơn hàng 2 !";
			}
			
			query = "\n update donhang set ngaynhap = convert( VARCHAR(10), DATEADD(dd,1, '"+ngayks+"') ,120), ngaysua ='"+Utility.getNgayHienTai()+"' , nguoisua= " + userId +
					"\n where trangthai = 0 and Npp_fk = "+nppId+" and ngaynhap <='"+ngayks+"' and ngaynhap >='"+tungay+"'   ";
			if(db.updateReturnInt(query) < 0)
			{
				return "  Lỗi dời ngày đơn hàng  3!";
			}
			
			System.out.println("donhang pass " + nppId + " " + ngayks + " tungay : " + tungay);			
			// trả hàng kh nguyên đơn
			
			 query = "\n update donhangtrave set trangthai = 2, nguoiduyet = '" + userId + "', ngayduyet = '" +Utility.getNgayHienTai() + "' " +
					 "\n where donhang_fk is not null and trangthai = 0  and Npp_fk = "+nppId+"   ";
				if (db.updateReturnInt(query)  < 0)
				{
					return "  Lỗi xóa đơn trả  hàng Kh nguyên đơn !";
				}
			
				System.out.println("tra nguyen don pass " + nppId + " " + ngayks + " tungay : " + tungay);
			// trả hàng kh lẻ
				
				 query = 	"\n update donhangtrave set ngaynhap = convert( VARCHAR(10), DATEADD(dd,1, '"+ngayks+"') ,120), nguoisua = '" + userId + "', ngaysua = '" +Utility.getNgayHienTai() + "' " +
				 			"\n where donhang_fk is  null and trangthai = 0  and Npp_fk = "+nppId+"  and ngaynhap <='"+ngayks+"' and ngaynhap >='"+tungay+"' ";
					if (db.updateReturnInt(query)  < 0)
					{
						return "  Lỗi dời ngày đơn trả hàng lẻ KH !";
					}
					System.out.println("tra lẻ pass " + nppId + " " + ngayks + " tungay : " + tungay);		
					
			/*
			 * 
			 * - Phiếu chưa chốt đến ngày N+1
				- Phiếu đã được NPP chốt nhưng Admin công ty chưa duyệt à Mở chốt sau đó chuyển ngày nghiệp vụ đến N+1
			 */
					// điều chỉnh tồn kho
					 query = 	"\n update DIEUCHINHTONKHO set NppDaChot = 0,NGAYDC = convert( VARCHAR(10), DATEADD(dd,1, '"+ngayks+"') ,120), nguoisua = '" + userId + "', modified_date = dbo.getlocaldate(default) " +
			 					"\n where trangthai = 0  and Npp_fk = "+nppId+" and NGAYDC <='"+ngayks+"' and NGAYDC >='"+tungay+"' ";
				if (db.updateReturnInt(query)  < 0)
				{
					return "  Lỗi dời ngày điều chỉnh tồn kho lẻ KH !";
				}
					
				System.out.println("dieu chinh ton kho pass " + nppId + " " + ngayks + " tungay : " + tungay);
			
			
			// chuyển kho
				
				query = 	"\n update chuyenkho set trangthai = 0,NgayDieuChinh = convert( VARCHAR(10), DATEADD(dd,1, '"+ngayks+"') ,120), nguoisua = '" + userId + "', ngaysua = '"+Utility.getNgayHienTai()+"'  " +
					"\n where trangthai in (0,1)  and Npp_fk = "+nppId+" and NgayDieuChinh <='"+ngayks+"' and NgayDieuChinh >='"+tungay+"' ";
				if (db.updateReturnInt(query)  < 0)
				{
					return "  Lỗi dời ngày chuyển kho KH !";
				}
				
				System.out.println("chuyen kho pass " + nppId + " " + ngayks + " tungay : " + tungay);
			// trả hàng NCC
				
				
			String msgDonTraHang = Doi_ngay_DONTRAHANG( db, nppId, ngayks,  tungay, userId);
			if(msgDonTraHang.trim().length() > 0)
			{
				return msgDonTraHang;
			}
			System.out.println("tra hang ncc pass " + nppId + " " + ngayks + " tungay : " + tungay);
			// khóa sổ
			
			
			query = " select * from   ufn_XNT_TuNgay_DenNgay_FULL_Chitiet_New("+nppId+", '"+tungay+"','"+ngayks+"' ) a "+
			" where ( cuoiky <0 or dauky < 0 ) "  ;
			rs = db.get(query);
			if(rs.next())
			{
				return "  bị âm xnt chi tiết vui lòng liên hệ admin để khắc phục !";

			}
			query = " select * from   ufn_XNT_TuNgay_DenNgay_FULL_New("+nppId+", '"+tungay+"','"+ngayks+"' ) a "+
			" where ( cuoiky <0 or dauky < 0 ) "  ;
			rs = db.get(query);
			if(rs.next())
			{
				return "  bị âm xnt tổng vui lòng liên hệ admin để khắc phục !";
			}
			query = " insert TONKHONGAY(KHO_FK,NPP_FK,SANPHAM_FK,NGAY,SOLUONG,KBH_FK) " +
					"  select kho_fk,npp_fk,sanpham_fk,'"+ngayks+"',cuoiky,kbh_fk" +
					"  from ufn_XNT_TuNgay_DenNgay_FULL_New("+nppId+", '"+tungay+"','"+ngayks+"' ) a ";
			if(db.updateReturnInt(query) < 0)
			{
				return "  không thể lưu tồn kho ngày !";
			}
			
			query = " insert TONKHONGAY_CHITIET(KHO_FK,NPP_FK,SANPHAM_FK,NGAY,SOLUONG,KBH_FK,ngaynhapkho) " +
			"  select kho_fk,npp_fk,sanpham_fk,'"+ngayks+"',cuoiky,kbh_fk,ngaynhapkho " +
			"  from ufn_XNT_TuNgay_DenNgay_FULL_Chitiet_New("+nppId+", '"+tungay+"','"+ngayks+"' )  a ";
			if(db.updateReturnInt(query) < 0)
			{
				return "  không thể lưu tồn kho ngày chi tiết !";
			}
			query = " insert KHOASONGAY(NGAYKSGANNHAT,NGAYKS,NGAYTAO,NGUOITAO,NPP_FK,chon,doanhso,Created_Date,isAuto)  " +
					"  select NULL,'"+ngayks+"','"+Utility.getNgayHienTai()+"',"+userId+","+nppId+",0,0,getdate(), " + auto;
			if(db.updateReturnInt(query) !=1)
			{
				return " Không thể tạo mới ngày khóa sổ !";
			}
			
			return "";
			
		}
		catch (Exception e) {
			return "Exception:"+ e.getMessage();
		}
	}
	
	public static String KhoaSoTuDong_NPP(Idbutils db,String nppId,String ngayks, String tungay,String userId, int auto)
	{
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String query = " select Ngay from dbo.uf_CacNgayTrongKhoangThoiGian('"+tungay+"','"+ngayks+"') ";
			ResultSet rs = db.get(query);
			while(rs.next())
			{
				String ngay = rs.getString("Ngay");
				String msg = Khoasongay.KhoaSo_NPP(db, nppId, ngay, ngay, userId, 0);
				if(msg.trim().length() > 0)
				{
					db.getConnection().rollback();
					db.getConnection().setAutoCommit(true);
					return ngay + " - " + msg;
				}
			}
			
			
			
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return "";
			
		}
		catch (Exception e) {
			try {
				db.getConnection().setAutoCommit(true);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return "Exception:"+ e.getMessage();
		}
	}
	
	
}
