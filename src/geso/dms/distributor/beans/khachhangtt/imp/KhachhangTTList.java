package geso.dms.distributor.beans.khachhangtt.imp;

import java.sql.ResultSet;

import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Phan_Trang;
import geso.dms.distributor.beans.khachhangtt.IKhachhangTTList;

public class KhachhangTTList  extends Phan_Trang  implements IKhachhangTTList
{
	private static final long serialVersionUID = -9217977546733610214L;

	String userId; //nppId
	String ten;
	String trangthai;
	String msg;
	
	ResultSet nppRs;
	String nppId;
	String nppTen;
	String sitecode;
	
	String tungay;
	String denngay;
	String loaiKH;
	String hopdong;
	String query;
	
	String vungid;
	String dayOfWeekId="";
	ResultSet dayOfWeekRs;
	public ResultSet getDayOfWeekRs() {
		return db.get(" select 2 as NgayId , N'Thứ 2' as Ten union all " +
				  " select 3 as NgayId , N'Thứ 3' as Ten union all " +
				  " select 4 as NgayId , N'Thứ 4' as Ten union all " +
				  " select 5 as NgayId , N'Thứ 5' as Ten union all " +
				  " select 6 as NgayId , N'Thứ 2' as Ten union all " +
				  " select 7 as NgayId , N'Thứ 7' as Ten ");
	}
	public String getDayOfWeekId() {
		return dayOfWeekId;
	}
	public void setDayOfWeekId(String dayOfWeekId) {
		this.dayOfWeekId = dayOfWeekId;
	}
	public String getVungid() {
		return vungid;
	}

	public void setVungid(String vungid) {
		this.vungid = vungid;
	}

	String mienid;
	public String getMienid() {
		return mienid;
	}


	public void setMienid(String mienid) {
		this.mienid = mienid;
	}

	//List<IKhachhang> khlist;
	ResultSet khlist;
	
	ResultSet hangcuahang;
	String hchId;
	
	ResultSet loaikhachhang;

	
	public ResultSet getLoaikhachhang() {
		return loaikhachhang;
	}


	public void setLoaikhachhang(ResultSet loaikhachhang) {
		this.loaikhachhang = loaikhachhang;
	}

	ResultSet kenhbanhang;
	String kbhId;
	
	ResultSet vitricuahang;
	String vtchId;
	
	ResultSet loaicuahang;
	String lchId;
	
	ResultSet nhomcuahang;
	String nchId;
	ResultSet vung;
	ResultSet mien;
	public ResultSet getVung() {
		return vung;
	}


	public void setVung(ResultSet vung) {
		this.vung = vung;
	}

	
	
	public ResultSet getMien() {
		return mien;
	}


	public void setMien(ResultSet mien) {
		this.mien = mien;
	}

	dbutils db;
	
	int currentPages;
	int[] listPages;
	
	String diachi, maFAST;
	
	public KhachhangTTList()
	{
		this.ten = "";
		this.trangthai = "";
		this.hchId = "";
		this.kbhId = "";
		this.vtchId = "";
		this.lchId = "";
		this.nchId = "";
		this.msg = "";
		this.diadiemId="";
		this.xuatkhau ="";
		
		this.diachi="";
		this.maFAST = "";
		
		this.vungid="";
		this.mienid="";
		this.tungay="";
		this.denngay="";
		this.loaiKH="";
		this.hopdong="";
		this.nppId = "";
		
		currentPages = 1;
		
		this.ddkdId="";
		this.tbhId="";
		
		listPages = new int[]{1, 2 , 3 , 4, 5, 6, 7, 8, 9, 10};
		
		this.db = new dbutils();
		
	}
	
		
	public String getDiachi() {
		return diachi;
	}

	public void setDiachi(String diachi) {
		this.diachi = diachi;
	}


	public String getMaFAST() {
		return maFAST;
	}


	public void setMaFAST(String maFAST) {
		this.maFAST = maFAST;
	}


	public String getUserId()
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;
	}
	
	public String getTen() 
	{
		return this.ten;
	}

	public void setTen(String ten) 
	{
		this.ten = ten;
	}

	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}

	public ResultSet getHangcuahang() 
	{
		return this.hangcuahang;
	}

	public void setHangcuahang(ResultSet hangcuahang)
	{
		this.hangcuahang = hangcuahang;
	}

	public ResultSet getKenhbanhang() 
	{
		return this.kenhbanhang;
	}

	public void setKenhbanhang(ResultSet kenhbanhang)
	{
		this.kenhbanhang = kenhbanhang;
	}

	public ResultSet getVitricuahang() 
	{
		return this.vitricuahang;
	}

	public void setVitricuahang(ResultSet vitricuahang) 
	{
		this.vitricuahang = vitricuahang;
	}

	public ResultSet getLoaicuahang() 
	{
		return this.loaicuahang;
	}

	public void setLoaicuahang(ResultSet loaicuahang) 
	{
		this.loaicuahang =  loaicuahang;
	}

	public ResultSet getNhomcuahang() 
	{
		return this.nhomcuahang;
	}

	public void setNhomcuahang(ResultSet nhomcuahang) 
	{
		this.nhomcuahang = nhomcuahang;
	}

	public String getHchId() 
	{
		return this.hchId;
	}

	public void setHchId(String hchId)
	{
		this.hchId = hchId;
	}

	public String getKbhId() 
	{
		return this.kbhId;
	}

	public void setKbhId(String kbhId) 
	{
		this.kbhId = kbhId;
	}

	public String getVtchId() 
	{
		return this.vtchId;
	}

	public void setVtId(String vtchId) 
	{
		this.vtchId = vtchId;
	}

	public String getLchId()
	{
		return this.lchId;
	}

	public void setLchId(String lchId) 
	{
		this.lchId = lchId;
	}

	public String getNchId() 
	{
		return this.nchId;
	}

	public void setNchId(String nchId) 
	{
		this.nchId = nchId;
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
	public void createLKHRS()
	{
		this.loaikhachhang =  this.db.get("select pk_seq,ten from LOAIKHACHHANG order by pk_seq");
	}
	public void createHchRS()
	{
		this.hangcuahang =  this.db.get("select hang as hchTen, pk_seq as hchId from hangcuahang where trangthai='1' order by hang");
	}
	public void createmien()
	{
		this.vung =  this.db.get("select pk_seq,ten from Vung");
	}
	public void createtinhthanh()
	{
		this.mien =  this.db.get("select pk_seq,Ten from tinhthanh");
	}
	public void createKbhRS()
	{
		this.kenhbanhang =  this.db.get("select diengiai as kbhTen, pk_seq as kbhId from kenhbanhang where trangthai='1' order by diengiai");
	}
	
	public void createVtchRS()
	{
		this.vitricuahang =  this.db.get("select vitri as vtchTen, pk_seq as vtchId from vitricuahang where trangthai='1' order by vitri");
	}
	
	public void createLchRS()
	{
		this.loaicuahang =  this.db.get("select loai as lchTen, pk_seq as lchId from loaicuahang where trangthai='1' order by loai");
	}
	
	public void createNchRS()
	{
		this.nhomcuahang =  this.db.get("select diengiai as nchTen, pk_seq as nchId from nhomcuahang order by diengiai");
	}
	
	geso.dms.center.util.Utility ut = new geso.dms.center.util.Utility();	
	public void createRS()
	{
		this.createHchRS();
		this.createKbhRS();
		this.createLchRS();
		this.createVtchRS();
		this.createtinhthanh();
		this.createmien();
		this.createLKHRS();
		this.nppRs = db.get("select pk_seq, TEN from NHAPHANPHOI f where isKHACHHANG = '0' and trangthai = '1' and f.pk_seq in "+ ut.quyen_npp(userId) +   "");
		
		String query="select * from DiaDiem ";
		this.diadiemRs = this.db.get(query);
		
		query="select ten as ddkdTen, pk_Seq as ddkdId from DaiDienKinhDoanh where trangthai=1  ";
		query+= " and pk_Seq in " + ut.Quyen_Ddkd(userId)+"";
		
		
		if(this.nppId.trim().length() > 0)
			query += " and pk_seq in ( select ddkd_fk from DAIDIENKINHDOANH_NPP where NPP_FK = '" + this.nppId + "' ) ";

		this.ddkdRs = this.db.get(query);

		
		
		
		
		if(ddkdId.length()>0)
		{
			query= "\n select  STUFF       " +
	                "\n      (     " +
	                "\n 		  (    " +
	                "\n 		   select DISTINCT TOP 100 PERCENT ' , ' + cast(PK_SEQ as varchar) " +
	                "\n 		   from tuyenbanhang  " +
	                "\n 		   where DDKD_FK =  " + this.ddkdId + " and NGAYID =a.NGAYID and NPP_FK in (select npp_fk from daidienkinhdoanh_npp where ddkd_fk =" + this.ddkdId + ") " +
	                "\n 		   ORDER BY ' , ' + cast(PK_SEQ as varchar) " +
	                "\n 		   FOR XML PATH('')       " +
	                "\n 		   ), 1, 2, ''    " +
	                "\n      ) + ' '  AS tbhId " +
	                "\n , diengiai as tbhTen from tuyenbanhang a where ddkd_fk =  " + this.ddkdId +
	                "\n and NPP_FK in (select top 1 npp_fk from daidienkinhdoanh_npp where ddkd_fk =" + this.ddkdId + ") " ;
			if(this.nppId.trim().length() > 0)
				query += " and NPP_FK = '" + this.nppId + "'  ";        
			query+="\n   order by ngayId asc ";
		}

		
		
	
		this.tbhRs = this.db
				.get(query);
		
	}
	
	public void init(String search) 
	{
		
		String query;	
		if (search.length() == 0)
		{		
			query = 
				"	select  ROW_NUMBER() OVER(ORDER BY a.pk_seq DESC) AS 'stt', isnull(a.mafast,'') as mafast , a.pk_seq as khId, a.smartid, a.ten as khTen, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua, d.chietkhau, d.pk_seq as mckId,  "+ 
				"		e.diengiai as kbhTen, e.pk_seq as kbhId, f.hang as hchTen, f.pk_seq as hchId, g.loai as lchTen, g.pk_seq as lchId, h.ten as nppTen, h.pk_seq as nppId,   "+
				"		k.sotienno as ghcnTen, k.pk_seq as ghcnId, l.ten as bgstTen, l.pk_seq as bgstId, m.vitri as vtchTen,  "+
				"		m.pk_seq as vtchId, a.dienthoai, a.diachi,  "+
				"	STUFF      "+
				"	(    "+
				"		(   "+
				"			select DISTINCT TOP 100 PERCENT ' , ' + tbh.DIENGIAI   "+
				"			from KHACHHANG_TUYENBH khtbh inner join TUYENBANHANG tbh on tbh.PK_SEQ=khtbh.TBH_FK   "+ 
				"			where khtbh.KHACHHANG_FK=a.PK_SEQ and tbh.NPP_FK=a.NPP_FK  "+ 
				"			ORDER BY ' , ' + tbh.DIENGIAI      "+
				"			FOR XML PATH('')      "+
				"		 ), 1, 2, ''   "+
				"	) + ' '  AS tbhTen,a.CHUCUAHIEU,a.MaHD,n.ten as LoaiCH    "+ 
				"  from    "+
				"  khachhang a inner join nhanvien b on a.nguoitao = b.pk_seq   "+ 
				"  inner join nhanvien c on a.nguoisua = c.pk_seq    "+
				"  left join mucchietkhau d on a.chietkhau_fk = d.pk_seq   "+
				"  left join kenhbanhang e on a.kbh_fk = e.pk_seq    "+
				"  left join hangcuahang f on a.hch_fk = f.pk_seq    "+
				"  left join loaicuahang g on a.lch_fk = g.pk_seq    "+
				"  inner join nhaphanphoi h on a.npp_fk = h.pk_seq    "+
				"  left join gioihancongno k on a.ghcn_fk = k.pk_seq    "+
				"  left join banggiasieuthi l on a.bgst_fk = l.pk_seq    "+
				"  left join vitricuahang m on a.vtch_fk = m.pk_seq    "+
				"   left join LOAIKHACHHANG n on n.pk_seq=a.XuatKhau "+
				" where a.pk_seq > 0  and h.pk_seq in "+ ut.quyen_npp(userId)+ "" ;
		}
		else
		{
			query = search;
		}
		
		System.out.println("chuoi:"+query );
	
		this.khlist =  super.createSplittingData(super.getItems(), super.getSplittings(), "khId desc", query);
		this.createRS();
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
		ResultSet rs = db.get("select count(*) as skh from khachhang");
		try 
		{
			rs.next();
			int count = Integer.parseInt(rs.getString("skh"));
			rs.close();
			return count;
		}
		catch(Exception e) {}
		finally{try {
			if(rs != null)
				rs.close();
		} catch (Exception e2) {
			
		}}
		
		return 0;
	}

	
	public void DBclose() {
		
		try {
			if(this.hangcuahang != null)
				this.hangcuahang.close();
			if(this.loaikhachhang != null)
				this.loaikhachhang.close();
			if(this.kenhbanhang != null)
				this.kenhbanhang.close();
			if(this.loaicuahang != null)
				this.loaicuahang.close();
			if(this.nhomcuahang != null)
				this.nhomcuahang.close();
			if(this.vitricuahang != null)
				this.vitricuahang.close();
			if(ddkdRs!=null)ddkdRs.close();
			if(tbhRs!=null)tbhRs.close();
			if(nppRs!=null)nppRs.close();
			if(this.db != null)
				db.shutDown();
			if(this.nhomcuahang!=null){
				nhomcuahang.close();
			}
		
			
		} catch (Exception e) {
			
		}
		
	}

	
	public void setKhList(ResultSet khlist) {
		this.khlist = khlist;
		
	}

	
	public ResultSet getKhList() {		
		return khlist;
	}

	
	public void setMsg(String msg) 
	{
		this.msg = msg;
	}

	
	public String getMsg() 
	{	
		return msg;
	}
	String diadiemId,xuatkhau;
	public String getXuatkhau()
  {
  	return xuatkhau;
  }

	public void setXuatkhau(String xuatkhau)
  {
  	this.xuatkhau = xuatkhau;
  }

	public String getDiadiemId()
  {
  	return diadiemId;
  }

	public void setDiadiemId(String diadiemId)
  {
  	this.diadiemId = diadiemId;
  }

	public ResultSet getDiadiemRs()
  {
  	return diadiemRs;
  }

	public void setDiadiemRs(ResultSet diadiemRs)
  {
  	this.diadiemRs = diadiemRs;
  }
	ResultSet diadiemRs;
	
	String ddkdId,tbhId;
	
	
	
	
	public String getDdkdId()
  {
  	return ddkdId;
  }


	public void setDdkdId(String ddkdId)
  {
  	this.ddkdId = ddkdId;
  }


	public String getTbhId()
  {
  	return tbhId;
  }


	public void setTbhId(String tbhId)
  {
  	this.tbhId = tbhId;
  }

		ResultSet ddkdRs,tbhRs;
		public ResultSet getDdkdRs()
		{
			return ddkdRs;
		}
		
		public void setDdkdRs(ResultSet ddkdRs)
		{
			this.ddkdRs = ddkdRs;
		}
		
		public ResultSet getTbhRs()
		{
			return tbhRs;
		}
		
		public void setTbhRs(ResultSet tbhRs)
		{
			this.tbhRs = tbhRs;
		}



		public String getTungay() {
		
			return this.tungay;
		}


	
		public void setTungay(String tungay) {
			this.tungay=tungay;
			
		}



		public String getDenngay() {
		
			return this.denngay;
		}



		public void setDenngay(String denngay) {
			this.denngay= denngay;
			
		}



		public String getloaiKH() {
	
			return this.loaiKH;
		}


	
		public void setloaiKH(String loaikh) {
			this.loaiKH= loaikh;
			
		}



		public String getHopdong() {
		
			return this.hopdong;
		}


	
		public void setHopdong(String hopdong) {
			this.hopdong= hopdong;
			
		}


		
		public void setQuery(String query) {
			this.query= query;
			
		}


		
		public ResultSet getNppRs() {
			
			return this.nppRs;
		}


		
		public void setNppRs(ResultSet nppRs) {
			
			this.nppRs = nppRs;
		}

	
}
