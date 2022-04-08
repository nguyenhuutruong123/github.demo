package geso.dms.distributor.beans.khachhang.imp;

import geso.dms.center.db.sql.Idbutils;
import geso.dms.distributor.beans.khachhang.*;
import geso.dms.distributor.db.sql.dbutils;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import javax.naming.spi.DirStateFactory.Result;

public class Khachhang implements IKhachhang, Serializable
{
	private static final long serialVersionUID = -9217977546733610214L;

	String userId;
	String id;
	String ten;
	String diachi;
	String tpId;
	String isduyet;


	String qhId;
	String smartid;
	
	String sdckTT = "1";
	String sodienthoai;
	String masothue;
	String trangthai;
	String ngaytao;
	String nguoitao;
	String ngaysua;
	String nguoisua;
	String msg;
	String nppId;
	String nppTen;
	String sitecode;
	String phuong;
	String nguoidaidien;
	String hch;
	String kbh;
	String bgst;
	String vtch;
	String lch;
	String nch;
	String mck;
	String ghcn;
	String Dientichch ="";
	ResultSet tp;
	ResultSet qh = null;

	ResultSet htkdRs;
	ResultSet tansoRs;
	

	String htkdId;

	ResultSet hangcuahang;
	String hchId;
	ResultSet kenhbanhang;
	String kbhId;
	ResultSet bgsieuthi;
	String bgstId;
	ResultSet vtcuahang;
	String vtchId;
	ResultSet loaicuahang;
	String lchId;
	ResultSet nhomcuahang;
	String nchId;
	ResultSet mucchietkhau;
	String mckId;
	ResultSet ghcongno;
	String ghcnId;
	ResultSet Rsbanggiasieuthi;	
	ResultSet nkh_khList;
	ResultSet nkh_khSelected;
	String[] nkh_khIds;
	String  songayno = "0", sotienno = "0";

	String ChonChietKhau;
	List<IErpKhachHang_SPCK> listsanphamCK = new ArrayList<IErpKhachHang_SPCK>();
	List<IErpKhachHang_ChungLoaiSP> listCLSanPham = new ArrayList<IErpKhachHang_ChungLoaiSP>();
	Hashtable kbh_cksp = new Hashtable();
	Hashtable kbh_ckcl = new Hashtable();
	ResultSet nvgnRs;
	String nvgnId;
	String mathamchieu = "";
	String view = "";
	String phuongxa = "";
	String coderoute = "";
	String routename = "";
	dbutils db;

	public Khachhang()
	{
		this.id = "";
		this.ten = "";
		this.diachi = "";
		this.tpId = "";
		this.qhId = "";
		this.sodienthoai = "";
		this.masothue = "";
		this.trangthai = "1";
		this.ngaytao = "";
		this.nguoitao = "";
		this.ngaysua = "";
		this.nguoisua = "";
		this.hch = "";
		this.kbh = "";
		this.bgst = "";
		this.vtch = "";
		this.lch = "";
		this.nch = "";
		this.mck = "";
		this.ghcn = "";
		this.hchId = "";
		this.kbhId = "";
		this.bgstId = "";
		this.vtchId = "";
		this.lchId = "";
		this.nchId = "";
		this.mckId = "";
		this.ghcnId = "";
		this.nvgnId = "";
		this.htkdId = "";
		this.phuong="";
		this.nguoidaidien="";
		this.msg = "";
		this.pxId="";
		this.sonha="";
		this.tenduong="";
		this.ChonChietKhau = "1";
		this.isduyet="0";

		this.db = new dbutils();		
	}

	public Khachhang(String id)
	{
		this.id = id;
		this.ten = "";
		this.diachi = "";
		this.tpId = "";
		this.qhId = "";
		this.sodienthoai = "";
		this.masothue = "";
		this.trangthai = "1";
		this.ngaytao = "";
		this.nguoitao = "";
		this.ngaysua = "";
		this.nguoisua = "";
		this.hch = "";
		this.kbh = "";
		this.bgst = "";
		this.vtch = "";
		this.lch = "";
		this.nch = "";
		this.mck = "";
		this.ghcn = "";
		this.hchId = "";
		this.kbhId = "";
		this.bgstId = "";
		this.vtchId = "";
		this.lchId = "";
		this.nchId = "";
		this.mckId = "";
		this.htkdId = "";
		this.ghcnId = "";
		this.nvgnId = "";
		this.phuong="";
		this.nguoidaidien="";
		this.ChonChietKhau = "1";
		this.msg = "";
		this.pxId="";
		this.sonha="";
		this.tenduong="";
		this.isduyet="0";

		this.db = new dbutils();
	}

	public String getIsduyet() {
		return isduyet;
	}

	public void setIsduyet(String isduyet) {
		this.isduyet = isduyet;
	}
	public ResultSet getTansoRs() {
		return tansoRs;
	}

	public void setTansoRs(ResultSet tansoRs) {
		this.tansoRs = tansoRs;
	}
	public String getCoderoute() {
		return coderoute;
	}
	public void setCoderoute(String coderoute) {
		this.coderoute = coderoute;
	}
	public String getRoutename() {
		return routename;
	}
	public void setRoutename(String routename) {
		this.routename = routename;
	}
	public String getPhuongxa() {
		return phuongxa;
	}
	public void setPhuongxa(String phuongxa) {
		this.phuongxa = phuongxa;
	}
	public String getView() {
		return view;
	}
	public void setView(String view) {
		this.view = view;
	}
	public String getMathamchieu() {
		return mathamchieu;
	}
	public void setMathamchieu(String mathamchieu) {
		this.mathamchieu = mathamchieu;
	}
	public String getSongayno() {
		return songayno;
	}

	public void setSongayno(String songayno) {
		this.songayno = songayno;
	}

	public String getSotienno() {
		return sotienno;
	}

	public void setSotienno(String sotienno) {
		this.sotienno = sotienno;
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

	public void setId(String id) 
	{
		this.id = id;
	}

	public String getTen() 
	{
		return this.ten;
	}

	public void setTen(String ten) 
	{
		this.ten = ten;
	}

	public String getDiachi() 
	{
		return this.diachi;
	}

	public void setDiachi(String diachi) 
	{
		this.diachi = diachi;
	}

	public String getTpId() 
	{
		return this.tpId;
	}

	public void setTpId(String tpId) 
	{
		this.tpId = tpId;
	}

	public String getQhId() 
	{
		return this.qhId;
	}

	public void setQhId(String qhId) 
	{
		this.qhId = qhId;
	}

	public String getSodienthoai() 
	{
		return this.sodienthoai;
	}

	public void setSodienthoai(String sodienthoai) 
	{
		this.sodienthoai = sodienthoai;
	}

	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}

	public String getNgaytao()
	{
		return this.ngaytao;	
	}


	public void setNgaytao(String ngaytao)
	{
		this.ngaytao = ngaytao;
	}

	public String getNgaysua()
	{
		return this.ngaysua;	
	}

	public void setNgaysua(String ngaysua)
	{
		this.ngaysua = ngaysua;
	}

	public String getNguoitao()
	{
		return this.nguoitao;
	}

	public void setNguoitao(String nguoitao)
	{
		this.nguoitao = nguoitao;
	}

	public String getNguoisua()
	{
		return this.nguoisua;
	}

	public void setNguoisua(String nguoisua)
	{
		this.nguoisua = nguoisua;
	}

	public String getMessage() 
	{
		return this.msg;
	}

	public void setMessage(String msg) 
	{
		this.msg = msg;
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

	public String getHch() 
	{
		return this.hch;
	}

	public void setHch(String hch)
	{
		this.hch = hch;		
	}

	public String getKbh() 
	{
		return this.kbh;
	}

	public void setKbh(String kbh) 
	{
		this.kbh = kbh;
	}

	public String getBgst() 
	{	
		return this.bgst;
	}

	public void setBgst(String bgst) 
	{
		this.bgst = bgst;
	}

	public String getVtch() 
	{
		return this.vtch;
	}

	public void setVtch(String vtch) 
	{
		this.vtch = vtch;	
	}

	public String getLch() 
	{	
		return this.lch;
	}

	public void setLch(String lch) 
	{
		this.lch = lch;	
	}

	public String getNch() 
	{	
		return this.nch;
	}

	public void setNch(String nch) 
	{		
		this.nch = nch;
	}

	public String getMck() 
	{	
		return this.mck;
	}

	public void setMck(String mck) 
	{
		this.mck = mck;	
	}

	public String getGhcn() 
	{
		return this.ghcn;
	}

	public void setGhcn(String ghcn) 
	{
		this.ghcn = ghcn;
	}

	public ResultSet getTp() 
	{
		return this.tp;
	}

	public void setTp(ResultSet tp) 
	{
		this.tp = tp;
	}

	public ResultSet getQh() 
	{
		return this.qh;
	}

	public void setQh(ResultSet qh) 
	{
		this.qh = qh;
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

	public ResultSet getBgsieuthi() 
	{
		return this.bgsieuthi;
	}

	public void setBgsieuthi(ResultSet bgsieuthi) 
	{
		this.bgsieuthi = bgsieuthi;
	}

	public ResultSet getVtcuahang() 
	{
		return this.vtcuahang;
	}

	public void setVtcuahang(ResultSet vtcuahang) 
	{
		this.vtcuahang = vtcuahang;
	}

	public ResultSet getLoaicuahang() 
	{
		return this.loaicuahang;
	}

	public void setLoaicuahang(ResultSet loaicuahang) 
	{
		this.loaicuahang = loaicuahang;
	}

	public ResultSet getNhomcuahang() 
	{
		return this.nhomcuahang;
	}

	public void setNhomcuahang(ResultSet nhomcuahang) 
	{
		this.nhomcuahang = nhomcuahang;
	}

	public ResultSet getMucchietkhau() 
	{	
		return this.mucchietkhau;
	}

	public void setMucchietkhau(ResultSet mucchietkhau)
	{
		this.mucchietkhau = mucchietkhau;	
	}

	public ResultSet getGhcongno() 
	{
		return this.ghcongno;
	}

	public void setGhcongno(ResultSet ghcongno) 
	{
		this.ghcongno = ghcongno;	
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

	public String getBgstId() 
	{
		return this.bgstId;
	}

	public void setBgstId(String bgstId) 
	{
		this.bgstId = bgstId;		
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

	public String getMckId() 
	{	
		return this.mckId;
	}

	public void setMckId(String mckId_) 
	{
		this.mckId = mckId_;	
	}

	public String getGhcnId() 
	{	
		return this.ghcnId;
	}

	public void setGhcnId(String ghcnId)
	{
		this.ghcnId = ghcnId;	
	}

	public ResultSet getNkh_khList() 
	{
		return this.nkh_khList;
	}

	public void setNkh_khList(ResultSet nkh_khlist) 
	{
		this.nkh_khList = nkh_khlist;		
	}

	public ResultSet getNkh_KhSelected() 
	{
		return this.nkh_khSelected;
	}

	public void setNkh_KhSelected(ResultSet nkh_khselected) 
	{
		this.nkh_khSelected = nkh_khselected;		
	}

	public Hashtable<Integer, String> getNkh_KhIds() 
	{
		Hashtable<Integer, String> selected = new Hashtable<Integer, String>();
		if (this.nkh_khIds != null) {
			int size = (this.nkh_khIds).length;
			int m = 0;
			while (m < size) {
				selected.put(new Integer(m), this.nkh_khIds[m]) ;
				m++;
			}
		}else{
			selected.put(new Integer(0), "null");
		}
		return selected;
	}

	public void setNkh_KhIds(String[] nkh_khIds) 
	{		
		this.nkh_khIds = nkh_khIds;
	}

	public void createTpRS()
	{  	
		this.tp = this.db.get("select ten as tpTen, pk_seq as tpId from tinhthanh order by ten");
	}
	public void createTansoRS()
	{  	
		this.tansoRs = this.db.get("select b.DIENGIAI,a.TANSO from KHACHHANG_TUYENBH a inner join tuyenbanhang b on a.TBH_FK=b.pk_seq where a.khachhang_fk='"+ this.id +"' order by b.diengiai asc");
	}
	public void createQhRS()
	{  	
		String query = "select ten as qhTen, pk_seq as qhId from quanhuyen where tinhthanh_fk='"+ this.tpId +"' order by ten";
		if (this.tpId != null) {

			this.qh = this.db.get(query);
			System.out.print("createQhRS: "+query);
		}
	}

	public void createHchRS()
	{
		this.hangcuahang =  this.db.get("select hang+'-'+Diengiai as hchTen, pk_seq as hchId from hangcuahang where trangthai='1' order by hang");
		//System.out.println("select hang as hchTen, pk_seq as hchId from hangcuahang where trangthai='1' order by hang");
	}

	public void createKbhRS()
	{
		String query = "";
		query = "select diengiai as kbhTen, pk_seq as kbhId from kenhbanhang where trangthai='1' order by diengiai";
		this.kenhbanhang =  this.db.get(query);		

		query = "\n select pk_seq as nvgnId, ten as nvgnTen from nhanviengiaonhan where npp_fk = '" + this.nppId + "' and trangthai = '1'";
		this.nvgnRs = this.db.get(query);
		System.out.println("NVGNRS: "+query);
	}

	public void createBgstRS()
	{
		this.bgsieuthi =  this.db.get("select ten as bgstTen, pk_seq as bgstId from banggiasieuthi where npp_fk='" + this.nppId + "' order by ten");
	}

	public void createVtchRS()
	{
		this.vtcuahang =  this.db.get("select vitri+'-'+Diengiai as vtchTen, pk_seq as vtchId from vitricuahang where trangthai='1' order by vitri");
	}

	public void createLchRS()
	{
		this.loaicuahang =  this.db.get("select loai+'-'+Diengiai as lchTen, pk_seq as lchId from loaicuahang where trangthai='1' order by loai");
	}

	public void createNchRS()
	{
		this.nhomcuahang =  this.db.get("select diengiai as nchTen, pk_seq as nchId from nhomkhachhang where trangthai='1' order by diengiai");
	}

	public void createMckRS()
	{
		this.mucchietkhau =  this.db.get("select chietkhau as mckTen, pk_seq as mckId from mucchietkhau where npp_fk='" + this.nppId + "' order by chietkhau");
	}

	public void createGhcnRS()
	{
		this.ghcongno =  this.db.get("select diengiai as ghcnTen, pk_seq as ghcnId from gioihancongno where npp_fk='" + this.nppId + "' order by sotienno");
	}

	public void createHtkdRS()
	{
		this.htkdRs =  this.db.get("select diengiai as htkdTen, pk_seq as htkdId from hinhthuckinhdoanh where trangthai = '1' order by diengiai");
	}

	public void createNkh_KhList()
	{  
		if (this.id.length()>0)
		{
			String query="select b.diengiai as nkhTen, b.pk_seq as nkhId";
			query = query + " from nhomkhachhang_khachhang a inner join nhomkhachhang b on a.nkh_fk = b.pk_seq where a.kh_fk='" + this.id + "'";
			System.out.println("NhomKH: "+query);
			this.nkh_khSelected = db.get(query);
			if (this.nkh_khSelected != null)
				try {

					String s = "";
					while (this.nkh_khSelected.next())
					{
						s += this.nkh_khSelected.getString("nkhId")+",";
						System.out.println("NhomKH\n");
					}
					if (s.length() >0 )
					{
						s = s.substring(0,s.length()-1);
						this.nkh_khIds = s.split(",");
					}
				} catch (SQLException e) {

					e.printStackTrace();
				}


				String query2 = "select diengiai as nkhTen, pk_seq as nkhId from nhomkhachhang ";
				/*					+ " where pk_seq not in (select b.pk_seq as nkhId ";
			query2 = query2 + "from nhomkhachhang_khachhang a inner join nhomkhachhang b on a.nkh_fk = b.pk_seq where a.kh_fk='" + this.id + "')";
				 */			System.out.println("nhom"+query2);
				 this.nkh_khList = db.get(query2);
		}
		else
		{
			String query="select diengiai as nkhTen, pk_seq as nkhId from nhomkhachhang";
			//query = query + " from khachhang_nkhachhang a inner join nhomkhachhang b on a.nkh_fk = b.pk_seq ";
			this.nkh_khSelected = db.get(query);

			String query2 = "select diengiai as nkhTen, pk_seq as nkhId from nhomkhachhang ";
			System.out.println("nhom"+query2);
			this.nkh_khList = db.get(query2);
		}
	}

	private void getNppInfo()
	{		
		//Phien ban moi
		geso.dms.distributor.util.Utility util=new geso.dms.distributor.util.Utility();
		this.nppId=util.getIdNhapp(this.userId);
		this.nppTen=util.getTenNhaPP();
		this.sitecode=util.getSitecode();
	}

	public void createRS()
	{
		if (view != null && view.length() > 0) {
			//Trung tâm
		}
		else {
			this.getNppInfo();
		}

		this.createTpRS();
		if(this.id.length()>0){
			this.createTansoRS();
		}
		this.createQhRS();
		this.createHchRS();
		this.createKbhRS();
		this.createBgstRS();
		this.createLchRS();
		this.createNchRS();
		this.createVtchRS();		
		this.createMckRS();
		this.createGhcnRS();
		this.createHtkdRS();
		this.createNkh_KhList();	
		createBangGiaSieuthi();

		if (this.tpId.trim().length() > 0 && this.qhId.trim().length() > 0)
		{
			String query="select * from PhuongXa WHERE TINHTHANH_FK='"+this.tpId+"' and QuanHuyen_fk='"+this.qhId+"'";
			System.out.println("phuong xa " + query);
			this.phuongxaRs=this.db.getScrol(query);
		}
		try{	

			if (this.listCLSanPham.size()<= 0) {
				String sql_getdetail = "\n select DISTINCT CL.PK_SEQ, CL.SMARTID, CL.TEN "+
				"\n from CHUNGLOAI CL "+
				"\n WHERE CL.TRANGTHAI = 1 " +
				"\n order by TEN ";

				List<IErpKhachHang_ChungLoaiSP> chungloaiSP = new ArrayList<IErpKhachHang_ChungLoaiSP>();
				//this.listCLSanPham.clear();
				ResultSet rsInit = db.get(sql_getdetail);

				if (rsInit != null)
				{					
					while (rsInit.next())
					{
						IErpKhachHang_ChungLoaiSP ClSanPham = new ErpKhachHang_ChungLoaiSP();
						ClSanPham.setIdChungLoai(rsInit.getString("PK_SEQ"));
						ClSanPham.setTenChungLoai(rsInit.getString("TEN"));
						ClSanPham.setMaChungLoai(rsInit.getString("SMARTID"));
						System.out.println(" # "+ClSanPham.getMaChungLoai()+" # "+ClSanPham.getTenChungLoai()+" # "+ClSanPham.getPTChietKhau());
						chungloaiSP.add(ClSanPham);
					}
				}
				this.listCLSanPham = chungloaiSP;
				rsInit.close();
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void createBangGiaSieuthi() {
		String sql = "\n select banggiasieuthi_fk as ma,bgst.ten,donvikinhdoanh as dvkdten from BGST_KHACHHANG kh "+
		"\n inner join banggiasieuthi bgst on bgst.pk_Seq=kh.banggiasieuthi_fk "+
		"\n inner join donvikinhdoanh dvkd on dvkd.pk_seq=bgst.dvkd_fk "+
		"\n where khachhang_fk='"+this.id+"'";
		this.Rsbanggiasieuthi=db.get(sql);
	}

	public boolean CreateKh() 
	{		
		try
		{
			this.db.getConnection().setAutoCommit(false);
			this.ngaytao = getDateTime();
			this.nguoisua = this.userId;

			String query = "";
			//Khoa sua them cho nay,khi khach hang them moi khong co chiet khau,va  khong co muc tin dung thi isert vao gia tri null,database cho phep null,nhung ko duoc phep mang gia tri khac voi bang chiet khau va muc tin dung
			//System.out.println("Muc Chiet khau :" + mucchietkhau);
			if (this.mckId == "") {
				this.mckId=null;
			}
			if (this.ghcnId == "") {
				this.ghcnId = null;
			}
			if (this.hchId == "") {
				this.hchId = null;
			}
			if (this.lchId == "") {
				this.lchId = null;
			}
			if (this.vtchId == "") {
				this.vtchId=null;
			}
			if (this.tpId==null || this.tpId.trim().length() == 0)
			{
				db.getConnection().rollback();
				this.msg = "Vui lòng chọn Tỉnh thành";
				return false;			
			}

			// Mã tăng tự động kèm mã vùng
			String mavung = "";
			query = "\n select distinct case  " +  
			"\n when b.VUNG_FK  = 100030  then 11 " +  
			"\n when b.VUNG_FK = 100032 then 12 " +  
			"\n when b.VUNG_FK = 100031 then 13 " +  
			"\n when b.VUNG_FK = 100029 then 15 " +  
			"\n else 14 end as mavung " +  
			"\n from KHUVUC_QUANHUYEN a inner join KHUVUC b on a.KHUVUC_FK = b.PK_SEQ " +  
			"\n where a.QUANHUYEN_FK = '"+this.qhId+"'";
			ResultSet rs;
			
				rs = db.get(query);
				if(rs.next())
					mavung = rs.getString("mavung");
				rs.close();
			

			int stt = Khachhang.MaKhachHang(db);
			query = "select dbo.PlusZero("+stt+",7) ma";
			rs = db.get(query);
			String makh ="";
			if(rs.next())
				makh = rs.getString("ma");
			rs.close();
			String ma = makh;
			List<Object> data = new ArrayList<Object>();			
			coderoute = null;
			
			query = "\n insert into khachhang(coderoute,routename,phuongxa,ten, trangthai, ngaytao, ngaysua, nguoitao, nguoisua, chietkhau, kbh_fk, " +
			"\n hch_fk, lch_fk, npp_fk, ghcn_fk, vtch_fk, dienthoai, diachi,TINHTHANH_FK,QUANHUYEN_FK, masothue," +
			"\n chucuahieu,phuongxa_fk,phuong,SUDUNGCKTT,SMARTID,dientichcuahang) " +
			"\n values(?,?,?,?,?,?,?," +
			"\n ?,?,?,?,?," +
			"\n ?,?,?, ?,?," +
			"\n ?,?,?, ?,?," +
			"\n ?,(select ten from PhuongXa where pk_seq=?)," +
			"\n ?,?,?)";
			data.clear();
			data.add(coderoute); data.add(routename); data.add(phuongxa); data.add(this.ten); data.add(this.trangthai);
			data.add(this.ngaytao); data.add(this.ngaytao); data.add(this.nguoisua); data.add(this.nguoisua);
			data.add(this.mckId == null || this.mckId.trim().length() <=0 ?null : this.mckId); data.add(this.kbhId); data.add((this.hchId==null || this.hchId.trim().length() <= 0)?null:this.hchId); data.add(this.lchId); data.add(this.nppId);
			data.add((this.ghcnId ==null || this.ghcnId.trim().length()<=0) ? null: this.ghcnId); data.add((this.vtchId ==null||this.vtchId.trim().length() <= 0) ?null: this.vtchId); data.add(this.sodienthoai);
			data.add(this.diachi); data.add(this.tpId); data.add(this.qhId); data.add(masothue);
			data.add(this.nguoidaidien); data.add(pxId.length() <= 0 ? null : this.pxId);
			data.add(pxId.length() <= 0 ? null : this.pxId);data.add(this.sdckTT);
			data.add(ma); data.add(this.Dientichch);
			dbutils.viewQuery(query, data);
			if (this.db.updateQueryByPreparedStatement(query, data)<=0) {
				db.getConnection().rollback();
				this.msg = "Không thể tạo mới khách hàng. Liên hệ với quản trị viên ";
				return false;			
			}

			/*query = "select IDENT_CURRENT('khachhang') as khId";
			rs = this.db.get(query);

			rs.next();*/
			this.id = this.db.getPk_seq();
			query = "\n update khachhang set mathamchieu = (select manpp from nhaphanphoi where pk_seq = khachhang.npp_fk)+smartid, " +
			"\n timkiem = dbo.ftBoDau(ten) + '-'+ SmartId where pk_seq = ?";
			data.clear();
			data.add(this.id);
			dbutils.viewQuery(query, data);
			if (this.db.updateQueryByPreparedStatement(query, data)<=0)
			{
				this.db.getConnection().rollback();
				this.msg = "Không thể cập nhật mã tham chiếu khách hàng. Vui lòng liên hệ quản trị viên ";
				return false; 
			}
			if (this.nvgnId == null || this.nvgnId.length() <= 0) {
				this.msg = "Vui lòng chọn NVGN";
				db.getConnection().rollback();
				return false;
			}
			query = "select mathamchieu from khachhang where pk_seq = "+ this.id;
			rs = db.get(query);
			String mathamchieucheck = "";
			if(rs.next()){
				mathamchieucheck = rs.getString("mathamchieu");
			}
			rs.close();
			int _check = -1;
			query = "select count(*) c from khachhang where mathamchieu = '"+mathamchieucheck+"' and pk_seq != ?";
			System.out.println("query check trung mathamchieu: " + query);
			data.clear();
//			data.add(mathamchieu);
			data.add(this.id);
			dbutils.viewQuery(query, data);
			ResultSet temprs = this.db.getByPreparedStatement(query, data);
			try {
				while (temprs.next()) {
					_check = temprs.getInt("c");
				}
				temprs.close();
			}
			catch (Exception e) {
				e.printStackTrace();
			}

			if (_check > 0) {
				this.db.getConnection().rollback();
				this.msg = "Mã tham chiếu đã bị trùng!";
				return false;
			}

			query = "insert nvgn_kh(nvgn_fk, khachhang_fk) values(?,?)";
			data.clear();
			data.add(this.nvgnId);
			data.add(this.id);
			db.viewQuery(query, data);
			if (this.db.updateQueryByPreparedStatement(query, data)<=0)
			{
				this.msg = "Không thể cập nhật nhân viên giao nhận. Vui lòng liên hệ quản trị viên ";
				db.getConnection().rollback();
				return false;
			}
			
			// Insert data set into table "Khachhang_nhomkhachhang"
			if (this.nkh_khIds != null)
			{
				int size = this.nkh_khIds.length;
				int m = 0;

				while (m < size )
				{
					query = "insert into NHOMKHACHHANG_KHACHHANG values(?,?)";
					data.clear();
					data.add(this.nkh_khIds[m]); data.add(this.id);
					if (this.db.updateQueryByPreparedStatement(query, data)<=0) {
						this.msg = "Không thể tạo mới nhóm khách hàng. Vui lòng liên hệ quản trị viên ";
						db.getConnection().rollback();
						return false;
					}
					m++;	
				}
			}
			String kq = Log_MaKhachHang( db,this.id, stt);
			if (kq.trim().length() > 0)
			{
				db.getConnection().rollback();
				this.msg = kq;
				return false;		
			}

			String ddkd_fk = "";
			query = "select ddkd_fk from tuyenbanhang where pk_seq in (select tbh_fk from khachhang_tuyenbh where khachhang_fk = ?)";
			data.clear();
			data.add(this.id);
			dbutils.viewQuery(query, data);
			temprs = db.getByPreparedStatement(query, data);
			try {
				while (temprs.next()) {
					ddkd_fk = temprs.getString("ddkd_fk");
				}
				temprs.close();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			if (ddkd_fk.trim().length()>0) {
				query = "\n update kh set kh.route_fk = ddkd.route_fk " +
				"\n from khachhang_tuyenbh a " +
				"\n inner join khachhang kh on kh.pk_seq = a.khachhang_fk " +
				"\n inner join tuyenbanhang b on a.tbh_fk = b.pk_seq " +
				"\n inner join daidienkinhdoanh ddkd on ddkd.pk_seq = b.ddkd_fk " +
				"\n where kh.pk_seq = ? and ddkd.pk_seq = ?";
				data.clear();
				data.add(this.id); data.add(ddkd_fk);
				dbutils.viewQuery(query, data);
				this.db.updateQueryByPreparedStatement(query, data); 
			}
			
			
			query =   " insert KhachHang_NPP_Log (khachhang_fk,NPP_FK,nguoisua) " +
					" select pk_seq,npp_fk,nguoisua from khachhang where pk_seq =   "+ this.id;
			db.update(query);
			
			

			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		}
		catch(Exception e)
		{
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			e.printStackTrace();
			this.msg = "Lỗi ngoại lệ: "+e.getMessage();
			return false;
		}
		return true;
	}

	public boolean UpdateKh() 
	{	
		List<Object> data = new ArrayList<Object>();
		if (view != null && !view.equals("TT")) {
			this.getNppInfo();
		}

		this.ngaysua = getDateTime();
		this.nguoisua = this.userId;

		if (this.mckId == "") {
			this.mckId=null;
		}

		if (this.ghcnId == "") {
			this.ghcnId=null;
		}

		if (this.hchId == "") {
			this.hchId=null;
		}

		if (this.lchId == "") {
			this.lchId=null;
		}

		if (this.vtchId == "") {
			this.vtchId=null;
		}

		String query = "";
		coderoute = null;

		if (this.bgstId.length() > 0)
		{
			query = "\n update khachhang set phuongxa = ?, PhuongXa_fk = ?, " +
			"\n diachi = ?, ghcn_fk = ?, chietkhau = ?, dienthoai = ?, kbh_fk = ?, lch_fk = ?,"+
			"\n hch_fk = ?,vtch_fk = ?, bgst_fk = ?, ngaysua = ?, trangthai = ?, nguoisua = ?, tinhthanh_fk = ?, " +
			"\n quanhuyen_fk = ?, masothue = ?, phuong = ?, chucuahieu = ?, " +
			"\n SUDUNGCKTT = ?, dientichcuahang = ?, isPDASua = '0',MATHAMCHIEU=? " +
			"\n where pk_seq = ?";
			data.clear();
			data.add(phuongxa); data.add(pxId.length() <= 0 ? "NULL" : this.pxId); data.add(this.diachi);
			data.add(this.ghcnId); data.add(this.mckId); data.add(this.sodienthoai); data.add(this.kbhId);
			data.add(this.lchId); data.add(this.hchId); data.add(this.vtchId); data.add(this.bgstId);
			data.add(this.ngaysua); data.add(this.trangthai); data.add(this.nguoisua); data.add(this.tpId);
			data.add(this.qhId); data.add(this.masothue); data.add(replaceAEIOU(this.phuong).toUpperCase()); 
			data.add(this.nguoidaidien); data.add(this.sdckTT); data.add(this.Dientichch);data.add(this.mathamchieu); data.add(this.id);
		}

		else
		{
			query = "\n update khachhang set TEN = ? ,phuongxa = ? ,PhuongXa_fk = ?, " +
			"\n diachi = ? ,songayno = ?,sotienno = ?, ghcn_fk = ?, chietkhau = ?," +
			"\n dienthoai = ?, kbh_fk = ?, lch_fk = ?," +
			"\n hch_fk = ?, vtch_fk = ?, ngaysua = ?, " +
			"\n trangthai = ?, nguoisua = ?, tinhthanh_fk = ?," +
			"\n quanhuyen_fk = ?, masothue = ?," +
			"\n phuong = ?, chucuahieu = ?," +
			"\n SUDUNGCKTT = ?, dientichcuahang = ?, isPDASua =  '0', mathamchieu=? " +
			"\n where pk_seq = ?" ;
			data.clear();data.add(ten);
			data.add(phuongxa); data.add(pxId.length() <= 0 ? dbutils.NULLVAL : this.pxId); data.add(this.diachi);
			data.add(this.songayno);data.add(this.sotienno);
			data.add(this.ghcnId); data.add(this.mckId); data.add(this.sodienthoai); data.add(this.kbhId);
			data.add( this.lchId.length() <= 0 ? dbutils.NULLVAL : this.lchId ); 
			data.add(this.hchId.length() <= 0 ? dbutils.NULLVAL : this.hchId ); 
			data.add(this.vtchId.length() <= 0 ? dbutils.NULLVAL : this.vtchId ); 
			data.add(this.ngaysua);
			data.add(this.trangthai); data.add(this.nguoisua); data.add(this.tpId);
			data.add(this.qhId); data.add(this.masothue); data.add(replaceAEIOU(this.phuong).toUpperCase()); 
			data.add(this.nguoidaidien); data.add(this.sdckTT); data.add(this.Dientichch);data.add(this.mathamchieu); data.add(this.id);
		}

		try
		{
			this.db.getConnection().setAutoCommit(false);
			dbutils.viewQuery(query, data);
			if (this.db.updateQueryByPreparedStatement(query, data)<=0)
			{
				this.db.getConnection().rollback();
				this.msg = "Không thể cập nhật khách hàng. Vui lòng liên hệ quản trị viên ";
				return false; 
			}
			//Trang jsp khong co bang gia sieu thi
			//neu kenh ban hang 100002 thi xoa het bang gia cua khachhang nay di
			if (!this.kbhId.equals("100002")) {
				query="delete bgst_khachhang where khachhang_fk=?";
				data.clear();
				data.add(this.id);
				dbutils.viewQuery(query, data);
				if (this.db.updateQueryByPreparedStatement(query, data)<0) {
					this.db.getConnection().rollback();
					this.msg = "Không thể xoá bảng giá siêu thị ";
					return false;
				}
			}

			int _check = -1;
			query = "select count(*) c from khachhang where mathamchieu = ? and pk_seq != ?";
			data.clear();
			data.add(mathamchieu);
			data.add(this.id);
			dbutils.viewQuery(query, data);
			ResultSet temprs = this.db.getByPreparedStatement(query, data);
			try {
				while (temprs.next()) {
					_check = temprs.getInt("c");
				}
				temprs.close();
			}
			catch (Exception e) {
				e.printStackTrace();
			}

			if (_check > 0) {
				this.db.getConnection().rollback();
				this.msg = "Mã tham chiếu đã bị trùng!";
				return false;
			}

			query = "\n update khachhang set timkiem = dbo.ftBoDau(ten) + '-'+ SmartId where pk_seq = ?";
			data.clear();
			data.add(this.id);
			dbutils.viewQuery(query, data);
			if (this.db.updateQueryByPreparedStatement(query, data)<=0)
			{
				this.db.getConnection().rollback();
				this.msg = "Không thể cập nhật mã tham chiếu khách hàng. Vui lòng liên hệ quản trị viên ";
				return false; 
			}

			if (this.trangthai.equals("0"))
			{
				query = " insert into khachhang_hdlog (khachhang_fk,ngaysua ,nguoisua,trangthai)"+
				" select ?, ngaysua = ?, nguoisua= ?, trangthai = ? ";
				data.clear();
				data.add(this.id); data.add(this.ngaysua); data.add(this.nguoisua); data.add(this.trangthai);
				dbutils.viewQuery(query, data);
				if (this.db.updateQueryByPreparedStatement(query, data)<=0) {
					geso.dms.center.util.Utility.rollback_throw_exception(db);
					this.msg = "Lỗi tạo Log KHACHHANG_HDLOG ";
					return false; 
				}
			}

			query = "delete from NHOMKHACHHANG_KHACHHANG where kh_fk= ?" ;
			data.clear();
			data.add(this.id);
			dbutils.viewQuery(query, data);
			if (this.db.updateQueryByPreparedStatement(query, data)<0) {
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				this.msg = "Lỗi xoá nhóm khách hàng ";
				return false; 
			}

			// Update table "khachhang_nkhachhang"
			if (this.nkh_khIds != null) {
				int size = (this.nkh_khIds).length;
				int m = 0;
				while (m < size)
				{
					query = "insert into NHOMKHACHHANG_KHACHHANG values('"+this.nkh_khIds[m]+"','" + this.id + "')"; 	
					System.out.println("query insert nkh_kh: " + query);
					if(!db.update(query)){
						this.msg = "Lỗi cập nhật nhóm khách hàng. Vui lòng liên hệ quản trị viên ";
						this.db.getConnection().rollback();
						return false;
					}
//					data.clear();
//					data.add(this.nkh_khIds[m]);
//					data.add(this.id);
//					dbutils.viewQuery(query, data);
//					//System.out.println(query);
//					if (this.db.updateQueryByPreparedStatement(query, data)<=0) {
//						this.msg = "Lỗi cập nhật nhóm khách hàng. Vui lòng liên hệ quản trị viên ";
//						this.db.getConnection().rollback();
//						return false;
//					}
					m++;
				}
			}
			// Trang jsp khong co chiet khau
			// XÓA SẢN PHẨM CHIẾT KHẤU CŨ
			query = "delete from KHACHHANG_CHUNGLOAISPCK WHERE KHACHHANG_FK = ? AND KENHBANHANG_FK = ?";
			data.clear();
			data.add(this.id); data.add(this.kbhId);
			dbutils.viewQuery(query, data);
			if (this.db.updateQueryByPreparedStatement(query, data)<0)
			{
				db.getConnection().rollback();
				this.msg = "Lỗi xoá KHACHHANG_CHUNGLOAISPCK. Vui lòng liên hệ quản trị viên ";
				return false;
			}

			// XÓA SẢN PHẨM CHIẾT KHẤU CŨ
			query = "delete from KHACHHANG_SANPHAMCK where khachhang_fk = ? AND KENHBANHANG_FK = ?";
			data.clear();
			data.add(this.id); data.add(this.kbhId);
			dbutils.viewQuery(query, data); 
			if (this.db.updateQueryByPreparedStatement(query, data)<0)
			{
				db.getConnection().rollback();
				this.msg = "Lỗi xoá KHACHHANG_SANPHAMCK. Vui lòng liên hệ quản trị viên "; 
				return false;
			}

			if (this.ChonChietKhau.equals("1"))
			{
				int count = 0;

				if (this.listsanphamCK != null) {
					while (count < this.listsanphamCK.size())
					{
						IErpKhachHang_SPCK sanphamCK = new ErpKhachHang_SPCK();
						sanphamCK = listsanphamCK.get(count);

						if (!sanphamCK.getPTChietKhau().equals("0") & sanphamCK.getPTChietKhau().length() > 0) {
							query = "\n insert into KHACHHANG_SANPHAMCK (khachhang_fk, sanpham_fk, ptchietkhau, KENHBANHANG_FK ) "
								+ "\n values(?,?,?,?)";
							data.clear();
							data.add(this.id); data.add(sanphamCK.getIdSanPham()); data.add(sanphamCK.getPTChietKhau()); data.add(kbhId);
							dbutils.viewQuery(query, data);
							if (this.db.updateQueryByPreparedStatement(query, data)<0)
							{
								this.msg = "Không thể lưu mã sản phẩm: " + sanphamCK.getMaSanPham() + "Vui lòng liên hệ quản trị viên";
								db.getConnection().rollback();
								return false;
							}
						}
						count++;
					}
				}
			}
			else
			{
				System.out.println("VÀO CHỦNG LOẠI");
				//GET LISTSP THUOC CHUNGLOAI

				System.out.println("SIZELIST: "+listCLSanPham.size());

				if (this.listCLSanPham != null) {
					for(int m = 0; m < this.listCLSanPham.size() ; m++)
					{						
						IErpKhachHang_ChungLoaiSP clSP = new ErpKhachHang_ChungLoaiSP();
						clSP = this.listCLSanPham.get(m);

						if (!clSP.getPTChietKhau().equals("0") & clSP.getPTChietKhau().length() > 0 ) {
							query = "\n insert into KHACHHANG_CHUNGLOAISPCK (khachhang_fk, chungloai_fk, ptchietkhau, KENHBANHANG_FK ) "
								+ "\n values(?,?,?,?)";
							data.clear();
							data.add(this.id); data.add(clSP.getIdChungLoai()); data.add(clSP.getPTChietKhau()); data.add(this.kbhId);
							dbutils.viewQuery(query, data);

							if (this.db.updateQueryByPreparedStatement(query, data)<0)
							{
								this.msg = "Không thể lưu mã sản phẩm chủng loại: " + clSP.getIdChungLoai() + " Vui lòng liên hệ quản trị viên ";
								db.getConnection().rollback();
								return false;
							}


							query = "\n select SP.PK_SEQ as sanpham_fk "+
							"\n from sanpham SP " +
							"\n inner join CHUNGLOAI CL on SP.chungloai_fk = CL.pk_seq "+
							"\n where CL.PK_SEQ = ?";
							data.clear();
							data.add(clSP.getIdChungLoai());
							dbutils.viewQuery(query, data);
							ResultSet rs = db.getByPreparedStatement(query, data);
							while (rs.next())
							{
								String sanpham_fk = rs.getString("sanpham_fk");

								query = "\n insert into KHACHHANG_SANPHAMCK (khachhang_fk, sanpham_fk, ptchietkhau, KENHBANHANG_FK ) "
									+ "\n values(?,?,?,?)";
								data.clear();
								data.add(this.id); data.add(sanpham_fk); data.add(clSP.getPTChietKhau()); data.add(this.kbhId); 
								dbutils.viewQuery(query, data);
								if (this.db.updateQueryByPreparedStatement(query, data)<0)
								{
									this.msg = "Không thể thể lưu mã sản phẩm: " + sanpham_fk + " Vui lòng liên hệ quản trị viên ";
									db.getConnection().rollback();
									return false;
								}	
							}
						}
					}
				}
			}

			query = "delete nvgn_kh where khachhang_fk = ?";
			data.clear();
			data.add(this.id);
			dbutils.viewQuery(query, data);
			this.db.updateQueryByPreparedStatement(query, data);
			if (this.nvgnId == null || this.nvgnId.length() <= 0) {
				this.msg = "Vui lòng chọn NVGN";
				db.getConnection().rollback();
				return false;
			}

			query = "insert nvgn_kh(nvgn_fk, khachhang_fk) values(?,?)";
			data.clear();
			data.add(this.nvgnId); data.add(this.id);

			if (this.db.updateQueryByPreparedStatement(query, data)<=0)
			{
				this.msg = "Không thể lưu nhân viên giao nhận";
				db.getConnection().rollback();
				return false;
			}

			/*	//Update bảng "Khachhang_htkd"
			query = "delete khachhang_htkd where khachhang_fk = '" + this.id + "'"; 	
			if (!db.update(query)) {
				this.msg="Loi khi cap nhat bang "+query;
				db.getConnection().rollback();
				return false;
			}*/

			/*	if (this.htkdId != null)
			{
				query = "insert into khachhang_htkd(khachhang_fk, htkd_fk) select '" + this.id + "', pk_seq from hinhthuckinhdoanh where pk_seq in (" + this.htkdId + ")"; 	
				if (!db.update(query)) {
					this.msg="Khong the cap nhat khachang_htkd .vui long thuc hien lai,hoac lien he voi admin de sua loi nay . Loi insert: "+query;
					db.getConnection().rollback();
					return false;
				}
			}*/

			String ddkd_fk = "";
			query = "select ddkd_fk from tuyenbanhang where pk_seq in (select tbh_fk from khachhang_tuyenbh where khachhang_fk = ?)";
			data.clear();
			data.add(this.id);
			dbutils.viewQuery(query, data);
			temprs = this.db.getByPreparedStatement(query, data);
			try {
				while (temprs.next()) {
					ddkd_fk = temprs.getString("ddkd_fk");
				}
				temprs.close();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			
			
			
			
			if (ddkd_fk.trim().length()>0) {
				
				query =" select a.kbh_fk,a.ten, k.ten kbh from daidienkinhdoanh a inner join kenhbanhang k on a.kbh_fk = k.pk_seq  where a.pk_seq=  " + ddkd_fk;
				ResultSet rs = db.get(query);
				rs.next();
				String kenhDDKD = rs.getString("kbh_fk");
				if(!kenhDDKD.equals(this.kbhId))
				{
					this.msg = "Khách hàng  đang thuộc tuyến NVBH ("+rs.getString("ten")+") , kênh ( "+rs.getString("kbh")+" ) , bạn không thể đổi sáng kênh khác! ";
					db.getConnection().rollback();
					return false;
				}
				
				query = "\n update kh set kh.route_fk = ddkd.route_fk " +
				"\n from khachhang_tuyenbh a " +
				"\n inner join khachhang kh on kh.pk_seq = a.khachhang_fk " +
				"\n inner join tuyenbanhang b on a.tbh_fk = b.pk_seq " +
				"\n inner join daidienkinhdoanh ddkd on ddkd.pk_seq = b.ddkd_fk " +
				"\n where kh.pk_seq = ? and ddkd.pk_seq = ?";
				data.clear();
				data.add(this.id);
				data.add(ddkd_fk);
				dbutils.viewQuery(query, data);
				this.db.updateQueryByPreparedStatement(query, data); 
			}


			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		}
		catch(Exception er) {
			this.msg = "Lỗi ngoại lệ: "+er.getMessage();
			geso.dms.center.util.Utility.rollback_throw_exception(this.db);
			return false;
		}
		return true;
	}

	public void init() 
	{
		String query = "\n select isnull(a.daduyet,'0') isduyet,isnull(route.coderoute,'')coderoute, isnull(route.ten,'')routename,isnull(a.phuongxa,'')phuongxa,isnull(a.daduyet,0)daduyet,isnull(a.mathamchieu,'')mathamchieu,isnull(a.phuong,'') as phuong,isnull(a.chucuahieu,'') as nguoidaidien," +
		"\n a.pk_seq as khId, a.smartid,a.ten as khTen, a.trangthai, a.ngaytao, a.ngaysua, isnull(a.masothue, '') as masothue," +
		" b.ten as nguoitao, c.ten as nguoisua, a.chietkhau,a.chonck,isnull(a.SUDUNGCKTT,'') as SUDUNGCKTT, " +
		"\n e.diengiai as kbhTen, e.pk_seq as kbhId, f.hang as hchTen, f.pk_seq as hchId, g.loai as lchTen," +
		"\n g.pk_seq as lchId, h.ten as nppTen, h.manpp as manpp, h.pk_seq nppId, " +
		"\n k.sotienno as ghcnTen, k.pk_seq as ghcnId, l.ten as bgstTen, l.pk_seq as bgstId, m.vitri as vtchTen," +
		"\n m.pk_seq as vtchId, a.dienthoai, a.diachi, a.tinhthanh_fk as tpId, a.quanhuyen_fk as qhId," +
		"\n nvgn_kh.nvgn_fk as nvgnId,a.phuongxa_fk as pxId, " +
		"\n (select REPLACE((SELECT distinct xx.htkd_FK as [data()] " +
		"\n		FROM khachhang_htkd xx  where xx.khachhang_FK=a.PK_SEQ FOR XML PATH('') ),' ',',')) as htkdId," +
		"\n a.songayno,a.sotienno,isnull(a.dientichcuahang,'') as dientichcuahang  " +
		"\n from khachhang a inner join nhanvien b on a.nguoitao = b.pk_seq " +
		"\n inner join nhanvien c on a.nguoisua = c.pk_seq  " +
		"\n left join kenhbanhang e on a.kbh_fk = e.pk_seq " +
		"\n left join hangcuahang f on a.hch_fk = f.pk_seq " +
		"\n left join loaicuahang g on a.lch_fk = g.pk_seq " +
		"\n inner join nhaphanphoi h on a.npp_fk = h.pk_seq " +
		"\n left join gioihancongno k on a.ghcn_fk = k.pk_seq " +
		"\n left join banggiasieuthi l on a.bgst_fk = l.pk_seq " +
		"\n left join vitricuahang m on a.vtch_fk = m.pk_seq " +
		"\n left join nvgn_kh on a.pk_seq = nvgn_kh.khachhang_fk " +
		"\n left join DMS_Route route on route.pk_seq = a.route_fk " +
		"\n where a.pk_seq='" + this.id + "'";
		System.out.println("Init: "+query);
		ResultSet rs =  this.db.get(query);
		try{
			rs.next();      
			coderoute = rs.getString("coderoute");
			routename = rs.getString("routename");
			phuongxa = rs.getString("phuongxa");
			this.mathamchieu = rs.getString("mathamchieu");
			this.isduyet = rs.getString("isduyet");

			this.id = rs.getString("khId");
			//this.id = rs.getString("smartid");
			this.ten = rs.getString("khTen");
			if (rs.getString("dienthoai")!=null)
			{	
				this.sodienthoai = rs.getString("dienthoai");
			}
			else
			{
				this.sodienthoai = "";
			}
			this.masothue = rs.getString("masothue");
			this.diachi = rs.getString("diachi");
			this.tpId = rs.getString("tpId")==null?"": rs.getString("tpId");
			this.qhId = rs.getString("qhId")==null?"": rs.getString("qhId");
			this.ChonChietKhau = rs.getString("chonck")==null?"1": rs.getString("chonck");
			this.trangthai = rs.getString("trangthai");
			this.ngaytao = rs.getString("ngaytao");
			this.nguoitao = rs.getString("nguoitao");
			this.ngaysua = rs.getString("ngaysua");
			this.nguoisua = rs.getString("nguoisua");
			//        	this.mck = rs.getString("chietkhau");
			this.mckId = rs.getString("chietkhau")== null?"":rs.getString("chietkhau");
			this.kbh = rs.getString("kbhTen");
			this.kbhId = rs.getString("kbhId");
			this.hch = rs.getString("hchTen");
			this.hchId = rs.getString("hchId");
			this.lch = rs.getString("lchTen");
			this.lchId = rs.getString("lchId");
			this.ghcn = rs.getString("ghcnTen");
			this.ghcnId = rs.getString("ghcnId");
			this.bgst = rs.getString("bgstTen");
			this.bgstId = rs.getString("bgstId");
			this.vtch = rs.getString("vtchTen");
			this.vtchId = rs.getString("vtchId");
			this.nvgnId = rs.getString("nvgnId");
			this.phuong = rs.getString("phuong");
			this.htkdId = rs.getString("htkdId")==null?"": rs.getString("htkdId");
			this.sdckTT = rs.getString("SUDUNGCKTT")==null?"1":rs.getString("SUDUNGCKTT");
			this.nguoidaidien = rs.getString("nguoidaidien");
			this.pxId =  rs.getString("pxId")==null?"": rs.getString("pxId");
			this.songayno = rs.getString("songayno") == null?"":rs.getString("songayno");
			this.sotienno = rs.getString("sotienno") == null?"":rs.getString("sotienno");
			this.smartid = rs.getString("smartid") == null?"":rs.getString("smartid");
			this.Dientichch = rs.getString("dientichcuahang");
			this.nppId = rs.getString("nppId");
		}
		catch(Exception e)
		{
			e.printStackTrace();
			this.msg = "Lỗi Init: "+ e.toString();
		}

		finally{
			try {
				if (rs != null)
					rs.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		/*      this.nvgnId = "";
        query = "\n select * from NVGN_KH  a inner join khachhang b on a.khachhang_fk = b.pk_seq " +
        		"\n inner join nhanviengiaonhan nv on nv.pk_seq = a.nvgn_fk  " +
        		"\n where a.khachhang_fk = '"+this.id+"' and nv.npp_fk ='"+this.nppId+"'";
        System.out.println("Init NVGN: "+query);
        rs = db.get(query);
        if (rs != null)
        {
        	try {
				if (rs.next())
				{
					this.nvgnId = rs.getString("NVGN_FK");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
        }*/

		createRS(); 

		String sql_getdetail = 	"\n SELECT SP.PK_SEQ as SANPHAM_FK, SP.MA, SP.TEN, DVDL.DIENGIAI as DVT, SPCK.PTCHIETKHAU " +
		"\n FROM KHACHHANG_SANPHAMCK SPCK "+ 
		"\n INNER JOIN SANPHAM SP on SPCK.SANPHAM_FK = SP.PK_SEQ "+
		"\n INNER JOIN DONVIDOLUONG DVDL on SP.DVDL_FK = DVDL.PK_SEQ "+
		"\n WHERE SPCK.KHACHHANG_FK = " + this.id + " AND KENHBANHANG_FK = " + this.kbhId + "";

		System.out.println("LIST SANPHAMCK:  " + sql_getdetail);
		List<IErpKhachHang_SPCK> listsanphamCK = new ArrayList<IErpKhachHang_SPCK>();
		//this.listsanphamCK.clear();
		try
		{
			ResultSet rsInit = db.get(sql_getdetail);
			if (rsInit != null)
			{

				while (rsInit.next())
				{
					IErpKhachHang_SPCK sanphamCK = new ErpKhachHang_SPCK();

					sanphamCK.setIdSanPham(rsInit.getString("sanpham_fk"));
					sanphamCK.setTenSanPham(rsInit.getString("TEN"));
					sanphamCK.setMaSanPham(rsInit.getString("MA"));
					sanphamCK.setDonViTinh(rsInit.getString("DVT"));
					sanphamCK.setPTChietKhau(rsInit.getString("PTCHIETKHAU"));
					System.out.println(" # "+sanphamCK.getMaSanPham()+" # "+sanphamCK.getTenSanPham()+" # "+sanphamCK.getDonViTinh()+" # "+sanphamCK.getPTChietKhau());
					listsanphamCK.add(sanphamCK);
				}
				this.listsanphamCK = listsanphamCK;
				rsInit.close();
			}

			//LOAD CHUNGLOAISP
			if (this.kbhId != null)
				if (this.kbhId.length() >0 )
				{
					query = 	"SELECT COUNT(*) AS NUM FROM KHACHHANG_CHUNGLOAISPCK " +
					"WHERE KHACHHANG_FK = '" + this.id + "' AND KENHBANHANG_FK = " + this.kbhId + "" ;

					System.out.println("Init: " + query);
					rs = this.db.get(query);
					rs.next();

					if (rs.getString("NUM").equals("0")) {
						sql_getdetail = 	" select DISTINCT '" + this.id + "' AS KHACHHANG_FK, CL.PK_SEQ, CL.SMARTID, CL.TEN, cast (0 as nvarchar(50)) AS Ptchietkhau "+
						" from CHUNGLOAI CL "+
						" INNER JOIN SANPHAM SP ON SP.CHUNGLOAI_FK = CL.PK_SEQ " +
						" WHERE CL.TRANGTHAI = 1 " +
						" order by TEN ";

					}else{
						sql_getdetail = 	" select DISTINCT CLSP.KHACHHANG_FK,CL.PK_SEQ,CL.SMARTID,CL.TEN,cast ( CLSP.Ptchietkhau as nvarchar(50)) Ptchietkhau "+
						" from chungloai CL "+
						" inner join KHACHHANG_CHUNGLOAISPCK CLSP on CL.PK_SEQ = CLSP.CHUNGLOAI_FK " +
						" where CLSP.KHACHHANG_FK = '" + this.id + "' AND KENHBANHANG_FK = " + this.kbhId + "" +
						" UNION " +
						" select '" + this.id + "' AS KHACHHANG_FK, CL.PK_SEQ, CL.SMARTID, CL.TEN, cast (0 as nvarchar(50)) AS Ptchietkhau  "+
						" from CHUNGLOAI CL " +
						"INNER JOIN SANPHAM SP ON SP.CHUNGLOAI_FK = CL.PK_SEQ " +

						" WHERE CL.PK_SEQ NOT IN ( "+
						" 	select CL.PK_SEQ "+
						" 	from chungloai CL "+
						" 	inner join KHACHHANG_CHUNGLOAISPCK CLSP on CL.PK_SEQ = CLSP.CHUNGLOAI_FK " +
						" 	where CLSP.KHACHHANG_FK = '" + this.id + "' AND KENHBANHANG_FK = " + this.kbhId + "" +
						")  AND CL.TRANGTHAI = 1 " +
						" order by TEN ";
					}
					System.out.println("Init LIST CLSanPHAM:  " + sql_getdetail);
					rs.close();
				}
			List<IErpKhachHang_ChungLoaiSP> chungloaiSP = new ArrayList<IErpKhachHang_ChungLoaiSP>();
			//this.listCLSanPham.clear();
			rsInit = db.get(sql_getdetail);

			if (rsInit != null)
			{					
				while (rsInit.next())
				{
					IErpKhachHang_ChungLoaiSP ClSanPham = new ErpKhachHang_ChungLoaiSP();

					ClSanPham.setIdChungLoai(rsInit.getString("PK_SEQ"));
					ClSanPham.setTenChungLoai(rsInit.getString("TEN"));
					ClSanPham.setMaChungLoai(rsInit.getString("SMARTID"));
					ClSanPham.setPTChietKhau(rsInit.getString("Ptchietkhau"));
					System.out.println(" # "+ClSanPham.getMaChungLoai()+" # "+ClSanPham.getTenChungLoai()+" # "+ClSanPham.getPTChietKhau());
					chungloaiSP.add(ClSanPham);
				}
			}
			this.listCLSanPham = chungloaiSP;
			rsInit.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			this.msg="Loi Trong Qua Trinh Lay Du Lieu ."+ e.toString();
		}
		finally{try {
			if (rs != null)
				rs.close();
		} catch (Exception e2) {
			// TODO: handle exception
		}}

	}

	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);	
	}

	public String getMasothue() 
	{
		return this.masothue;
	}

	public void setMasothue(String masothue) 
	{
		this.masothue = masothue;
	}


	public ResultSet getBangGiaST() {

		return this.Rsbanggiasieuthi;
	}


	public void DBclose() {

		try {

			if (this.bgsieuthi != null)
				this.bgsieuthi.close();
			if (this.ghcongno != null)
				this.ghcongno.close();
			if (this.hangcuahang != null)
				this.hangcuahang.close();
			if (this.loaicuahang != null)
				this.loaicuahang.close();
			if (this.kenhbanhang != null)
				this.kenhbanhang.close();
			if (this.mucchietkhau != null)
				this.mucchietkhau.close();
			if (this.nhomcuahang != null)
				this.nhomcuahang.close();
			if (this.nkh_khSelected != null)
				this.nkh_khSelected.close();
			if (this.nkh_khList != null)
				this.nkh_khList.close();
			if (this.qh != null)
				this.qh.close();
			if (this.Rsbanggiasieuthi != null)
				this.Rsbanggiasieuthi.close();
			if (this.tp != null)
				this.tp.close();
			if (this.vtcuahang != null)
				this.vtcuahang.close();
			if (nvgnRs!=null) {
				nvgnRs.close();
			}
			if (this.db != null)
				this.db.shutDown();

		} catch (Exception e) {
			// TODO: handle exception
		}

	}


	public ResultSet getNvgnRs() 
	{
		return this.nvgnRs;
	}

	public void setNvgnRs(ResultSet nvgnRs) 
	{
		this.nvgnRs = nvgnRs;
	}

	public String getNvgnId() 
	{
		return this.nvgnId;
	}

	public void setNvgnId(String nvgnId) 
	{
		this.nvgnId = nvgnId;
	}


	public String getPhuong() {

		return this.phuong;
	}


	public void setPhuong(String phuong) {
		this.phuong=phuong;

	}


	public String getNguoidaidien() {

		return this.nguoidaidien;
	}


	public void setNguoidaidien(String nguoidaidien) {

		this.nguoidaidien=nguoidaidien;
	}

	private static char[] SPECIAL_CHARACTERS = { ' ', '!', '"', '#', '$', '%',
		'*', '+', ',', ':', '<', '=', '>', '?', '@', '[', '\\', ']', '^',
		'`', '|', '~', 'À', 'Á', 'Â', 'Ã', 'È', 'É', 'Ê', 'Ì', 'Í', 'Ò',
		'Ó', 'Ô', 'Õ', 'Ù', 'Ú', 'Ý', 'à', 'á', 'â', 'ã', 'è', 'é', 'ê',
		'ì', 'í', 'ò', 'ó', 'ô', 'õ', 'ù', 'ú', 'ý', 'Ă', 'ă', 'Đ', 'đ',
		'Ĩ', 'ĩ', 'Ũ', 'ũ', 'Ơ', 'ơ', 'Ư', 'ư', 'Ạ', 'ạ', 'Ả', 'ả', 'Ấ',
		'ấ', 'Ầ', 'ầ', 'Ẩ', 'ẩ', 'Ẫ', 'ẫ', 'Ậ', 'ậ', 'Ắ', 'ắ', 'Ằ', 'ằ',
		'Ẳ', 'ẳ', 'Ẵ', 'ẵ', 'Ặ', 'ặ', 'Ẹ', 'ẹ', 'Ẻ', 'ẻ', 'Ẽ', 'ẽ', 'Ế',
		'ế', 'Ề', 'ề', 'Ể', 'ể', 'Ễ', 'ễ', 'Ệ', 'ệ', 'Ỉ', 'ỉ', 'Ị', 'ị',
		'Ọ', 'ọ', 'Ỏ', 'ỏ', 'Ố', 'ố', 'Ồ', 'ồ', 'Ổ', 'ổ', 'Ỗ', 'ỗ', 'Ộ',
		'ộ', 'Ớ', 'ớ', 'Ờ', 'ờ', 'Ở', 'ở', 'Ỡ', 'ỡ', 'Ợ', 'ợ', 'Ụ', 'ụ',
		'Ủ', 'ủ', 'Ứ', 'ứ', 'Ừ', 'ừ', 'Ử', 'ử', 'Ữ', 'ữ', 'Ự', 'ự', };

	private static char[] REPLACEMENTS = { ' ', '\0', '\0', '\0', '\0', '\0',
		'\0', '_', '\0', '_', '\0', '\0', '\0', '\0', '\0', '\0', '_',
		'\0', '\0', '\0', '\0', '\0', 'A', 'A', 'A', 'A', 'E', 'E', 'E',
		'I', 'I', 'O', 'O', 'O', 'O', 'U', 'U', 'Y', 'a', 'a', 'a', 'a',
		'e', 'e', 'e', 'i', 'i', 'o', 'o', 'o', 'o', 'u', 'u', 'y', 'A',
		'a', 'D', 'd', 'I', 'i', 'U', 'u', 'O', 'o', 'U', 'u', 'A', 'a',
		'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A',
		'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'E', 'e', 'E', 'e',
		'E', 'e', 'E', 'e', 'E', 'e', 'E', 'e', 'E', 'e', 'E', 'e', 'I',
		'i', 'I', 'i', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o',
		'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O',
		'o', 'U', 'u', 'U', 'u', 'U', 'u', 'U', 'u', 'U', 'u', 'U', 'u',
		'U', 'u', };

	public String replaceAEIOU(String s) 
	{
		int maxLength = Math.min(s.length(), 236);
		char[] buffer = new char[maxLength];
		int n = 0;
		for (int i = 0; i < maxLength; i++) 
		{
			char ch = s.charAt(i);
			int index = Arrays.binarySearch(SPECIAL_CHARACTERS, ch);
			if (index >= 0) 
			{
				buffer[n] = REPLACEMENTS[index];
			} 
			else 
			{
				buffer[n] = ch;
			}
			// skip not printable characters
			if (buffer[n] > 31) {
				n++;
			}
		}

		// skip trailing slashes
		while (n > 0 && buffer[n - 1] == '/') 
		{
			n--;
		}
		return String.valueOf(buffer, 0, n);
	}

	String sonha,tenduong,pxId;

	public String getSonha()
	{
		return sonha;
	}

	public void setSonha(String sonha)
	{
		this.sonha = sonha;
	}

	public String getTenduong()
	{
		return tenduong;
	}

	public void setTenduong(String tenduong)
	{
		this.tenduong = tenduong;
	}

	public String getPxId()
	{
		return pxId;
	}

	public void setPxId(String pxId)
	{
		this.pxId = pxId;
	}

	ResultSet phuongxaRs;

	public ResultSet  getPhuongxaRs()
	{
		return this.phuongxaRs;
	}


	public void setPhuongxaRs(ResultSet phuongxaRs)
	{
		this.phuongxaRs=phuongxaRs;
	}


	public ResultSet getHtkdRs() {

		return this.htkdRs;
	}


	public void setHtkdRs(ResultSet htkdRs) {

		this.htkdRs = htkdRs;
	}


	public String getHtkdId() {

		return this.htkdId;
	}


	public void setHtkdId(String htkdId) {

		this.htkdId = htkdId;
	}

	public void setListSanPhamCK(List<IErpKhachHang_SPCK> list) {
		this.listsanphamCK = list;

	}


	public List<IErpKhachHang_SPCK> getListSanPhamCK() {
		return this.listsanphamCK;
	}

	public void setChonChietKhau(String chonchietkhau) {
		this.ChonChietKhau = chonchietkhau;

	}

	public String getChonChietKhau() {
		return this.ChonChietKhau;
	}

	public void setListChungLoaiCK(List<IErpKhachHang_ChungLoaiSP> list) {

		this.listCLSanPham = list;
	}

	public List<IErpKhachHang_ChungLoaiSP> getListChungLoaiCK() {

		return this.listCLSanPham;
	}
	public void setKbh_Cksp(Hashtable cksp) {
		this.kbh_cksp = cksp;
	}

	public Hashtable getKbh_Cksp() {
		return this.kbh_cksp;
	}

	public void setKbh_Ckcl(Hashtable ckcl) {
		this.kbh_ckcl = ckcl;
	}

	public Hashtable getKbh_Ckcl() {
		return this.kbh_ckcl;
	}

	public String getSudungckTT() {			
		return this.sdckTT;
	}

	public void setSudungckTT(String sudungckTT) {			
		this.sdckTT = sudungckTT;
	}

	public String getsmartid() {			
		return smartid;
	}

	public void setsmartid(String smartid) {
		this.smartid = smartid;			
	}

	public String getDientichch() {
		return Dientichch;
	}

	public void setDientichch(String dientichch) {
		Dientichch = dientichch;
	}

	public static int MaKhachHang(Idbutils db)
	{
		try 
		{			
			int so = -1;
			String query = "select isnull(max(stt)+1,0) max from MaKhachHang"; 
			ResultSet rs = db.get(query);
			if (rs.next())
			{
				so = rs.getInt("max"); // có trống thì return luôn
				rs.close();

				if (so == 0)
				{
					so = 1;
				}

				return so;
			}
			else
			{
				rs.close();
				return -1;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}

	public static String Log_MaKhachHang(Idbutils db,String khId,int stt)
	{	
		String query  = "\n insert makhachhang(KhachHang_FK,Ma,stt) " +
		"\n	select kh.pk_seq, smartid,"+stt+
		"\n from khachhang kh " +
		"\n where kh.pk_seq = "+khId;
		//System.out.println("Query Log: "+query);
		if (db.updateReturnInt(query) <=0)
		{
			return "Không thể lưu Log mã KH";
		}
		return "";
	}
}

