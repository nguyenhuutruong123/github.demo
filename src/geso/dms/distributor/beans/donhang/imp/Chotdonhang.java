package geso.dms.distributor.beans.donhang.imp;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;

import geso.dms.center.util.IPhanTrang;
import geso.dms.center.util.PhanTrang;
import geso.dms.center.util.Phan_Trang;
import geso.dms.distributor.util.Utility;
import geso.dms.distributor.beans.donhang.IChotdonhang;
import geso.dms.distributor.db.sql.dbutils;

public class Chotdonhang extends Phan_Trang implements IChotdonhang, Serializable
{
	private static final long serialVersionUID = -9217977546733610214L;
	String userId;
	String ngaygiao;
	String msg;

	String nppId;
	String nppTen;
	String sitecode;

	ResultSet nvbhlist;
	String nvbhId;
	ResultSet nvgnlist;
	String nvgnId;

	ResultSet pxklist;
	String[] pxkIds;
	ResultSet dhlist;
	String[] dhIds;

	dbutils db;
	private int num;
	private int[] listPages;
	private int currentPages;
	private int theLastPage;

	private String ngayksgn;
	private HttpServletRequest request;

	public Chotdonhang()
	{
		this.nvbhId = "";
		this.nvgnId = "";
		this.ngaygiao = "";
		this.msg = "";
		currentPages = 1;
		num = 1;
		this.ngayksgn = "";
		this.db = new dbutils();
		// this.init();
	}

	public HttpServletRequest getRequestObj()
	{
		return this.request;
	}

	public void setRequestObj(HttpServletRequest request)
	{
		this.request = request;
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
		return this.ngaygiao;
	}

	public void setNgaygiao(String ngaygiao)
	{
		this.ngaygiao = ngaygiao;
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
		/*
		 * try { ResultSet rs = db.get(
		 * "select a.pk_seq, a.ten, a.sitecode from nhaphanphoi a, nhanvien b where b.dangnhap = a.ma and b.pk_seq ='"
		 * + this.userId + "'"); if( rs != null) { rs.next(); this.nppId =
		 * rs.getString("pk_seq"); this.nppTen = rs.getString("ten");
		 * this.sitecode = rs.getString("sitecode"); } else { this.nppId = "";
		 * this.nppTen = ""; this.sitecode = ""; } } catch(Exception e) {}
		 */
		// Phien ban moi
		geso.dms.distributor.util.Utility util = new geso.dms.distributor.util.Utility();
		this.nppId = util.getIdNhapp(this.userId);
		this.nppTen = util.getTenNhaPP();
		this.sitecode = util.getSitecode();
	}

	public ResultSet getNvbhList()
	{
		return this.nvbhlist;
	}

	public void setNvbhList(ResultSet nvbhlist)
	{
		this.nvbhlist = nvbhlist;
	}

	public String getNvbhId()
	{
		return this.nvbhId;
	}

	public void setNvbhId(String nvbhId)
	{
		this.nvbhId = nvbhId;
	}

	public ResultSet getNvgnList()
	{
		return this.nvgnlist;
	}

	public void setNvgnList(ResultSet nvgnlist)
	{
		this.nvgnlist = nvgnlist;
	}

	public String getNvgnId()
	{
		return this.nvgnId;
	}

	public void setNvgnId(String nvgnId)
	{
		this.nvgnId = nvgnId;
	}

	public ResultSet getPxkList()
	{
		return this.pxklist;
	}

	public void setPxkList(ResultSet pxklist)
	{
		this.pxklist = pxklist;
	}

	public Hashtable<Integer, String> getPxkIds()
	{
		Hashtable<Integer, String> selected = new Hashtable<Integer, String>();
		if (this.pxkIds != null)
		{
			int size = (this.pxkIds).length;
			int m = 0;
			while (m < size)
			{
				selected.put(new Integer(m), this.pxkIds[m]);
				m++;
			}
		} else
		{
			selected.put(new Integer(0), "null");
		}
		return selected;
	}

	public void setPxkIds(String[] pxkIds)
	{
		this.pxkIds = pxkIds;
	}

	public ResultSet getDhList()
	{
		return this.dhlist;
	}

	public void setDhList(ResultSet dhlist)
	{
		this.dhlist = dhlist;
	}

	public Hashtable<Integer, String> getDhIds()
	{
		Hashtable<Integer, String> selected = new Hashtable<Integer, String>();
		if (this.dhIds != null)
		{
			int size = (this.dhIds).length;
			int m = 0;
			while (m < size)
			{
				String dhId = this.dhIds[m].substring(0, this.dhIds[m].indexOf(","));
				selected.put(new Integer(m), dhId);
				m++;
			}
		} else
		{
			selected.put(new Integer(0), "null");
		}
		return selected;
	}

	public void setDhIds(String[] dhIds)
	{
		this.dhIds = dhIds;
	}

	public void init()
	{
		db = new dbutils();
		this.getNppInfo();
		this.createNvbhRs();
		this.createNvgnRs();
		this.createDhRs();
	}

	private void createNvbhRs()
	{
		String sql = "select pk_seq as nvbhId, ten as nvbhTen from daidienkinhdoanh where npp_fk = '" + this.nppId +
			"' and pk_seq in ";
		sql = sql +
			"(select distinct b.ddkd_fk from khachhang_tuyenbh a inner join tuyenbanhang b on b.pk_seq = a.tbh_fk inner join nvgn_kh c on a.khachhang_fk = c.khachhang_fk where b.npp_fk = '" +
			this.nppId + "' ";
		if (this.nvgnId.length() > 0)
			sql = sql + "and c.nvgn_fk = '" + this.nvgnId + "'";
		sql = sql + " )";
		System.out.println("chuoi in:" + sql);
		this.nvbhlist = db.get(sql);
	}

	private void createNvgnRs()
	{
		String sql = "select pk_seq as nvgnId, ten as nvgnTen from nhanviengiaonhan ";
		sql = sql + "where npp_fk = '" + this.nppId + "' and trangthai='1' ";
		this.nvgnlist = db.get(sql);
	}

	private String getQuery()
	{
		Utility util = new Utility();
		this.ngayksgn = util.ngaykhoaso(this.nppId);			

		String query = 
			"select a.pk_seq as dhId,a.npp_fk as NppId, a.ngaynhap, a.tonggiatri,b.smartid, b.pk_seq as khId, b.ten as khTen, d.pk_seq as pxkId, d.nvgn_fk as nvgnId, " +
			"STUFF ( ( select DISTINCT TOP 100 PERCENT ' , ' + cast(dk.DENGHITRATB_FK as varchar(10)) AS [text()] " +
			"from DENGHITRATB_DONHANG dk " +
			"where a.pk_seq = dk.DONHANG_FK " +
			"ORDER BY ' , ' + cast(dk.DENGHITRATB_FK as varchar(10)) " +
			"FOR XML PATH('') ), 1, 2, '' ) as DENGHITRATRUNGBAYId " +			
			" from donhang a inner join khachhang b on a.khachhang_fk = b.pk_seq " +
			" inner join phieuxuatkho_donhang c on a.pk_seq = c.donhang_fk inner join phieuxuatkho d on c.pxk_fk = d.pk_seq " +
			" where a.npp_fk = '" +this.nppId +"' and a.trangthai = '3' and a.ngaynhap > '" + this.ngayksgn + "'  ";
		if (this.nvbhId.length() > 0)
			query = query + " and a.ddkd_fk = '" + this.nvbhId + "'";
		if (this.nvgnId.length() > 0)
			query = query + " and d.nvgn_fk = '" + this.nvgnId + "'";
		if (this.ngaygiao.length() > 0)
			query = query + " and d.ngaylapphieu = '" + convertDate(this.ngaygiao) + "'";

		System.out.println("Query la: " + query + "\n");

		return query;
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

	private void createDhRs()
	{
		String query = getQuery();

		this.dhlist = db.get(query);
		//this.dhlist = createSplittingData(50, 10, "dhId desc", query);
	}

	public boolean chotDonhang()
	{
		Utility util = new Utility();
		this.ngayksgn = util.ngaykhoaso(this.nppId);		
		
		if (this.dhIds != null)
		{
			try
			{
				
				int sodong = 0;
				
				String query = "select COUNT(*) as numb  " +
							   "from donhang a inner join DENGHITRATB_DONHANG dk on dk.donhang_fk = a.pk_seq    " +
							   "where a.npp_fk = '" + this.nppId + "' and a.trangthai = '3' and a.ngaynhap > '" + this.ngayksgn + "' and a.ngaynhap <= '" + TangNgayKs() + "' " +
							   "	and a.PK_SEQ not in ( select DONHANG_FK from DONHANG_CTTB_TRATB )";
				
				ResultSet rs = this.db.get(query);
				System.out.println("Check don hang chua cap nhat san pham tra trung bay " + query);
				if (rs != null)
				{
					while (rs.next())
					{
						sodong = rs.getInt(1);
					}
					rs.close();
				}
				if (sodong > 0)
				{
					this.msg = "Con " + sodong +
						" Don hang chua cap nhat tra trung bay! Ban phai cap nhat tra trung bay truoc khi chot don hang!";
					return false;
				}
				db.getConnection().setAutoCommit(false);

				
				
				
				
				for (int i = 0; i < this.dhIds.length; i++)
				{
					// thu tu qui uoc ben trang chotdonhang.jsp dhId > khId >
					// tonggiatri > nvgnId > pxkId > ngaynhap
					// this.msg = this.msg + this.dhIds[i] + " \\\\\\ ";

					String[] arr = this.dhIds[i].split(",");
					query = "select count(*) as dhth from  PHIEUTHUHOI a inner join PHIEUTHUHOI_DONHANG "+
							" b on a.PK_SEQ = b.pth_fk where b.donhang_fk = '"+ arr[0] +"' and a.trangthai = '0' ";
				
					rs = db.get(query);
					if(rs != null)
					{
						if(rs.next())
						{
							if(rs.getInt("dhth") > 0)
							{
								msg += "Đơn hàng số "+ arr[0] +" không được phép chốt,Vui lòng chốt phiếu thu hồi trước khi chốt đơn hàng !\n";
								return false;
							}
						}
						
					}
					query = "select (select count(distinct ctkm_fk) as ctkmid  from donhang_ctkm_dkkm a"
							+ " where donhang_fk  = "
							+ arr[0]
							+ "  ) as sl, (select count(distinct ctkmid) as ctkmid  from donhang_ctkm_trakm a"
							+ " where donhangid  = " + arr[0] + "  ) as sl2 ";
					System.out.println("___+__"+query);
					rs = db.get(query);
					while (rs.next()) {
						String msg1 = "";
						if (rs.getDouble("sl") < rs.getDouble("sl2")) {

							msg1 = "vui lòng check lại khuyến mãi ";

						}
						
						if (msg1.length() > 0) {
							return false ;
						}

					}

				
					query = " select d.SANPHAM_FK from donhang_ctkm_dkkm a "+
							 "\n inner join DONHANG_CTKM_TRAKM b on a.donhang_fk = b.DONHANGID and a.ctkm_fk = b.CTKMID "+
							 "\n inner join donhang c on c.PK_SEQ = a.donhang_fk "+
							 "\n inner join sanpham e on e.ma = a.sanpham_fk "+
							 "\n inner join DIEUKIENKM_SANPHAM d on a.dkkm_fk = d.DIEUKIENKHUYENMAI_FK and e.PK_SEQ = d.SANPHAM_FK "+
							 "\n where d.sanpham_fk not in (select sanpham_fk from DONHANG_SANPHAM where donhang_fk = a.donhang_fk and donhang_fk = "+ arr[0]+") and a.donhang_fk = "+ arr[0]+" ";

					rs = db.get(query);
					System.out
							.println("Check don hang sản phẩm mua  "
									+ query);
					if (rs != null) {
						while (rs.next()) {
							sodong = rs.getInt(1);
						}
						rs.close();
					}
					if (sodong > 0) {
						msg = "đơn hàng lỗi do k có sản phẩm mua trong điều kiện khuyến mãi mà có trả km !";
						return false;
					}
					query = "update donhang set trangthai='1',FlagModified =1,chuyen ='1',NGAYCHOT = dbo.GetLocalDate(DEFAULT),NGUOICHOT = "+(this.userId.length() <= 0?null:this.userId)+" where pk_seq = '" + arr[0] + "' and trangthai <> 1";
					if (!db.update(query))
					{
						db.getConnection().rollback();
						this.msg = "Loi khi chốt đơn hàng, lỗi trạng thái" ;
						return false;
					}

					
				}
				db.getConnection().commit();
				db.getConnection().setAutoCommit(true);
			} catch (Exception e)
			{
				
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				//this.msg = "Loi khi cap ,loi : " + e.toString() +this.msg;
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	public String getMessege()
	{
		return this.msg;
	}

	public void setMessege(String msg)
	{
		this.msg = msg;
	}

	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	private String convertDate(String date)
	{
		// chuyen dinh dang dd-MM-yyyy sang dinh dang yyyy-MM-dd
		if (!date.contains("-"))
			return getDateTime();
		String[] arr = date.split("-");
		if (arr[0].length() < arr[2].length())
			return arr[2] + "-" + arr[1] + "-" + arr[0];
		return date;
	}

	public int getNum()
	{
		return this.num;
	}

	public void setNum(int num)
	{
		this.num = num;
		listPages = PhanTrang.getListPages(num);
	}

	public int getCurrentPage()
	{
		return this.currentPages;
	}

	public void setCurrentPage(int current)
	{
		this.currentPages = current;
	}

	public int[] getListPages()
	{
		return this.listPages;
	}

	public void setListPages(int[] listPages)
	{
		this.listPages = listPages;
	}

	public int getLastPage()
	{
		String q = "select count(stt) as c from (" + getQuery() + ") dh";
		db = new dbutils();
		ResultSet rs = db.get(q);
		return PhanTrang.getLastPage(rs);
	}

	public int[] getNewPagesList(String action, int num, int currentPage, int theLastPage, String[] listPage)
	{
		IPhanTrang pt = new PhanTrang();
		return pt.getNewPagesList(action, num, currentPage, theLastPage, listPage);
	}

	@Override
	public void DBclose()
	{
		// TODO Auto-generated method stub
		try
		{
			if (this.dhlist != null)
				this.dhlist.close();
			if (this.nvbhlist != null)
				this.nvbhlist.close();
			if (this.nvgnlist != null)
				this.nvgnlist.close();
			if (pxklist != null)
			{
				pxklist.close();
			}
			pxkIds = null;
			if (this.db != null)
				this.db.getConnection().close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public boolean TraTrungBay(HttpServletRequest request)
	{
		String dhTrungbayId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dhTBId"));
		if(dhTrungbayId != null)
		{
			String[] ttbIdChon = request.getParameterValues(dhTrungbayId + "_ttbIdChon");
			if(ttbIdChon == null)
			{
				this.msg = "Bạn phải chọn trả trưng bày cho đơn hàng số ( " + dhTrungbayId + " ) ";
				return false;
			}
			
			//THOA DIEU KIEN  --> LUU LAI
			try 
			{
				db.getConnection().setAutoCommit(false);
			
				String query = "DELETE FROM DONHANG_CTTB_TRATB WHERE DONHANG_FK = '" + dhTrungbayId + "'";
				if (!this.db.update(query))
				{
					this.db.getConnection().rollback();
					this.msg = "Loi xoa thong tin trong don hang  " + query;
					return false;
				}
				
				for(int i = 0; i < ttbIdChon.length; i++)
				{
					String ttb_tongluong = request.getParameter(dhTrungbayId + "_" + ttbIdChon[i] + "_TongLuong");
					String ttb_cttbID = request.getParameter(dhTrungbayId + "_" + ttbIdChon[i] + "_cttbID");
					String ttb_cttbSOXUAT = request.getParameter(dhTrungbayId + "_" + ttbIdChon[i] + "_cttbSOXUAT");
					String ttb_cttbLOAI = request.getParameter(dhTrungbayId + "_" + ttbIdChon[i] + "_cttbLOAI");
					
					if(ttb_cttbLOAI.equals("2"))
					{
						//SAN PHAM CHON KHONG DUOC PHEP VUOT QUA TONG LUONG
						String[] ttb_spID = request.getParameterValues(dhTrungbayId + "_" + ttbIdChon[i] + "_spID");
						String[] ttb_spSOLUONG = request.getParameterValues(dhTrungbayId + "_" + ttbIdChon[i] + "_spSOLUONG");
						
						double totalSOLUONG = 0;
						for(int j = 0; j < ttb_spID.length; j++ )
						{
							if(ttb_spSOLUONG[j].trim().length() > 0)
							{
								totalSOLUONG += Double.parseDouble(ttb_spSOLUONG[j].trim().replaceAll(",", ""));
								
								//CHECK TON KHO
								query =  "select AVAILABLE, ( select ten from SANPHAM where pk_seq = '" + ttb_spID[j] + "' ) as spTEN " +
												"from NHAPP_KHO a where NPP_FK = '" + this.nppId + "' and KHO_FK = '100000' and KBH_FK = (select KBH_FK from DONHANG where PK_SEQ = '" + dhTrungbayId + "') and SANPHAM_FK = '" + ttb_spID[j] + "' ";
								ResultSet rsTK = db.get(query);
								double avai = 0;
								String spTEN = "";
								
								if(rsTK.next())
								{
									avai = rsTK.getDouble("AVAILABLE");
									spTEN = rsTK.getString("spTEN");
								}
								rsTK.close();
								
								if(avai < Double.parseDouble(ttb_spSOLUONG[j].trim().replaceAll(",", "")) )
								{
									this.db.getConnection().rollback();
									this.msg = "Sản phẩm ( " + spTEN + " ) của trả trưng bày đơn hàng ( " + dhTrungbayId + " ) còn tối đa ( " + avai +  " ), không đủ số lượng trả ( " + ttb_spSOLUONG[j].trim() + " ) ";
									return false;
								}
								
							}
						}
						
						if(totalSOLUONG != Double.parseDouble(ttb_tongluong) )
						{
							this.db.getConnection().rollback();
							this.msg = "Tổng lượng trả khuyến mại ( " + totalSOLUONG + " ) của đơn hàng ( " + dhTrungbayId + " ) phải bằng tổng số lương theo điều kiện trả ( " + ttb_tongluong + " ). ";
							return false;
						}
						
						for(int j = 0; j < ttb_spID.length; j++ )
						{
							if(ttb_spSOLUONG[j].trim().length() > 0)
							{
								//CAP NHAT TON KHO
								query = "UPDATE NHAPP_KHO set soluong = soluong - '" + ttb_spSOLUONG[j].trim().replaceAll(",", "") + "', AVAILABLE = AVAILABLE - '" + ttb_spSOLUONG[j].trim().replaceAll(",", "") + "' " +
										"where NPP_FK = '" + this.nppId + "' and KHO_FK = '100000' and KBH_FK = (select KBH_FK from DONHANG where PK_SEQ = '" + dhTrungbayId + "') and SANPHAM_FK = '" + ttb_spID[j] + "' ";
								if (!this.db.update(query))
								{
									this.db.getConnection().rollback();
									this.msg = "Loi khi cap nhat ton kho: " + query;
									return false;
								}
								
								query = "INSERT INTO DONHANG_CTTB_TRATB(CTTB_FK, TTB_FK, DONHANG_FK, SANPHAM_FK, SOLUONG, SOXUAT, TONGGIATRI)" +
										"select '" + ttb_cttbID + "', '" + ttbIdChon[i] + "', '" + dhTrungbayId + "','" + ttb_spID[j] + "','" + ttb_spSOLUONG[j].trim().replaceAll(",", "") + "','" + ttb_cttbSOXUAT + "', isnull(c.GIAMUANPP, 0) * '" + ttb_spSOLUONG[j].trim().replaceAll(",", "") + "' " +
										"from NHAPHANPHOI a inner join BANGGIAMUANPP_NPP b on a.PK_SEQ = b.NPP_FK  " +
										"	inner join BGMUANPP_SANPHAM c on b.BANGGIAMUANPP_FK = c.BGMUANPP_FK  " +
										"	inner join  BANGGIAMUANPP bgmnpp on bgmnpp.pk_seq=c.BGMUANPP_FK   " +
										"where a.PK_SEQ = '" + this.nppId + "' and  c.trangthai = '1'  and  bgmnpp.kenh_fk = ( select kbh_fk from donhang where pk_seq = '" + dhTrungbayId + "')  and c.SANPHAM_FK = '" + ttb_spID[j] + "' ";
								if (!this.db.update(query))
								{
									this.db.getConnection().rollback();
									this.msg = "Loi chot don hang " + query;
									return false;
								}
								System.out.println("___1_Chen vao bang DONHANG_CTTB_TRATB: " + query);
							}
						}
					}
					else
					{
						//TIEN HOAC CHIET KHAU
						/*double tongtien = Double.parseDouble(ttb_tongluong);
						if(tongtien < 200)  //TRA CHIET KHAU
						{
							query =  "select SUM(soluong * giamua - chietkhau) as tongtien  " +
									 "from DONHANG_SANPHAM  " +
									 "where DONHANG_FK = '" + dhTrungbayId + "' and SANPHAM_FK in ( select SANPHAM_FK from NHOMSPTRUNGBAY_SANPHAM where NHOMSPTRUNGBAY_FK in ( select NHOMSPTRUNGBAY_FK from CTTB_NHOMSPTRUNGBAY where CTTRUNGBAY_FK = '" + ttb_cttbID + "' ) ) ";
							ResultSet rsTGT = db.get(query);
							if(rsTGT.next())
							{
								tongtien = tongtien * rsTGT.getDouble("tongtien") / 100;
							}
							rsTGT.close();
						}*/
						
						query = "INSERT INTO DONHANG_CTTB_TRATB(CTTB_FK, TTB_FK, DONHANG_FK, SANPHAM_FK, SOLUONG, SOXUAT, TONGGIATRI)" +
								"select '" + ttb_cttbID + "', '" + ttbIdChon[i] + "', '" + dhTrungbayId + "', NULL, 0,'" + ttb_cttbSOXUAT + "', " + ttb_tongluong;	
						if (!this.db.update(query))
						{
							this.db.getConnection().rollback();
							this.msg = "Loi chot don hang " + query;
							return false;
						}
						System.out.println("___2_Chen vao bang DONHANG_CTTB_TRATB: " + query);
						
					}
					
				}
				
				query = "UPDATE DONHANG set trangthai = '1',FlagModified =1 where pk_seq = '" + dhTrungbayId + "' and trangthai = '3' ";
				if (this.db.updateReturnInt(query)!=1)
				{
					this.db.getConnection().rollback();
					this.msg = "Đơn hàng này đã được chốt !";
					return false;
				}
				
				db.getConnection().commit();
			} 
			catch (Exception e) 
			{
				try {
					db.getConnection().rollback();
				} catch (SQLException e1) {}
				
				this.msg = "Lỗi khi chốt đơn hàng: " + e.getMessage();
				return false;
			}
		}
		
		return true;
	}


	public ResultSet getTrungbayRs(String dhId) 
	{
		String query =  " select dk_dh.DAT, CT.PK_SEQ as cttbID, CT.SCHEME, isnull(SP.PK_SEQ, '-1') as spID, isnull(SP.TEN, '') as spTEN, CTTB_TRATB.pheptoan, CTTB_TRATB.TRATRUNGBAY_FK,  " +
						"  	 	case when TRA.LOAI = 2 then dk_dh.DAT * TRA.TONGLUONG  " +
						"  	 		 when TRA.LOAI = 1 and TRA.TONGLUONG > 100 then dk_dh.DAT * TRA.TONGLUONG  " +
						"  	 		 else ( ( dk_dh.DAT * TRA.TONGLUONG ) / 100 ) * (  select SUM(soluong * giamua - chietkhau) as tongtien  " +
						" 										from DONHANG_SANPHAM  " +
						" 										where DONHANG_FK = '" + dhId + "' and SANPHAM_FK in ( select SANPHAM_FK from NHOMSPTRUNGBAY_SANPHAM where NHOMSPTRUNGBAY_FK in ( select NHOMSPTRUNGBAY_FK from CTTB_NHOMSPTRUNGBAY where CTTRUNGBAY_FK = ct.pk_seq ) ) ) end as TONGLUONG,  " +
						"  	 	isnull(TRA.SANPHAM_FK, '-1') as SANPHAM_FK, TRA.TRATRUNGBAY_FK, TRA.LOAI, TRA.HINHTHUC, TRA.DIENGIAI,  " +
						"  	 	case TRA.HINHTHUC when 2 then 0 else dk_dh.DAT * TRA.soluong end as soLUONG,  " +
						" 		ISNULL( ( select AVAILABLE from NHAPP_KHO where NPP_FK = '" + this.nppId + "' and KHO_FK = '100000' and KBH_FK = (select KBH_FK from DONHANG where PK_SEQ = '" + dhId + "') and SANPHAM_FK = SP.PK_SEQ ), 0 ) as AVAI  " +
						"  from DENGHITRATB_DONHANG dk_dh inner join DENGHITRATRUNGBAY dk on dk_dh.DENGHITRATB_FK = dk.pk_seq  " +
						"  	 inner join CTTRUNGBAY ct on dk.cttrungbay_fk = ct.pk_seq  " +
						"  	 inner join CTTB_TRATB ON CTTB_TRATB.CTTRUNGBAY_FK = CT.PK_SEQ   " +
						"  	 INNER JOIN   " +
						"  	 (  " +
						"  		SELECT TRA.LOAI, case TRA.LOAI when 2 then ISNULL(TRA.TONGLUONG,   " +
						"  							(   " +
						"  								SELECT SUM(SOLUONG) FROM TRATRUNGBAY_SANPHAM   " +
						"  								WHERE TRATRUNGBAY_FK = TRA.PK_SEQ GROUP BY TRATRUNGBAY_FK   " +
						"  							)) else TRA.TONGTIEN END AS TONGLUONG,  " +
						"  			TRA.PK_SEQ as TRATRUNGBAY_FK, TRA.HINHTHUC, TRASP.SANPHAM_FK, isnull(TRASP.SOLUONG, 0) as soluong, tra.diengiai   " +
						"  		FROM TRATRUNGBAY TRA LEFT JOIN TRATRUNGBAY_SANPHAM TRASP ON TRASP.TRATRUNGBAY_FK=TRA.PK_SEQ   " +
						"  	 )  " +
						"  	 TRA ON TRA.TRATRUNGBAY_FK = CTTB_TRATB.TRATRUNGBAY_FK   " +
						"  	 LEFT JOIN SANPHAM SP ON SP.PK_SEQ=TRA.SANPHAM_FK    " +
						"  where dk_dh.donhang_fk = '" + dhId + "'  " +
						"  order by CTTB_TRATB.thutudieukien ASC  ";
		
		System.out.println("--LAT TTB: " + query);
		return db.get(query);
	}

	
}
