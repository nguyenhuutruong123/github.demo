package geso.dms.distributor.beans.khachhang.imp;

import geso.dms.center.util.Phan_Trang;
import geso.dms.center.util.Utility;
import geso.dms.distributor.beans.khachhang.IKhachhangList;
import geso.dms.distributor.db.sql.*;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class KhachhangList extends Phan_Trang implements IKhachhangList, Serializable
{
	private static final long serialVersionUID = -9217977546733610214L;

	String sodt;
	String userId;
	String ten;
	String trangthai;
	String mathamchieu;


	String msg;	
	String nppId;
	String npp1Ten;
	String loaikhachhang;
	String Route;
	String sitecode;	
	String kbhId;
	String hchId;	
	String vtchId;
	String npp1Id;
	String lchId;
	String nchId;
	String view = "";
	String npp_search = "";
	String vungId = "";
	String kvId = "";
	String gsbhId = "";
	String ddkdId = "";
	String isDuyet = "";
	String isToado = "";
	String ttId = "";
	String coderoute = "";
	List<Object> datasearch = new ArrayList<Object>();
	ResultSet khlist;	
	ResultSet hangcuahang;	
	ResultSet kenhbanhang;	
	ResultSet vitricuahang;	
	ResultSet loaicuahang;	
	ResultSet nhapp;	
	ResultSet nhomcuahang;
	ResultSet tinhthanh;
	ResultSet vung;
	ResultSet khuvuc;
	ResultSet ddkd;
	ResultSet gsbh;
	ResultSet coderouteRs;
	String tinhthanhId = "";
	
	List<Object> data = new ArrayList<Object>();
	dbutils db;
	
	int currentPages;
	int[] listPages;
	
	public KhachhangList()
	{
		this.ten = "";
		this.trangthai = "";
		this.hchId = "";
		this.kbhId = "";
		this.mathamchieu = "";

		this.vtchId = "";
		this.lchId = "";
		this.npp1Id = "";
		this.loaikhachhang = "";
		this.Route = "";
		this.nchId = "";
		this.msg = "";
		this.sodt = "";
		currentPages = 1;
		listPages = new int[]{1, 2 , 3 , 4, 5, 6, 7, 8, 9, 10};		
		this.db = new dbutils();
	}

	public String getMathamchieu() {
		return mathamchieu;
	}

	public void setMathamchieu(String mathamchieu) {
		this.mathamchieu = mathamchieu;
	}
	public String getTinhthanhId() {
		return tinhthanhId;
	}
	
	public void setTinhthanhId(String tinhthanhId) {
		this.tinhthanhId = tinhthanhId;
	}
	
	public String getVungId() {
		return vungId;
	}

	public void setVungId(String vungId) {
		this.vungId = vungId;
	}

	public String getKvId() {
		return kvId;
	}

	public void setKvId(String kvId) {
		this.kvId = kvId;
	}

	public String getGsbhId() {
		return gsbhId;
	}

	public void setGsbhId(String gsbhId) {
		this.gsbhId = gsbhId;
	}

	public String getDdkdId() {
		return ddkdId;
	}

	public void setDdkdId(String ddkdId) {
		this.ddkdId = ddkdId;
	}

	public String getIsDuyet() {
		return isDuyet;
	}

	public void setIsDuyet(String isDuyet) {
		this.isDuyet = isDuyet;
	}

	public String getIsToado() {
		return isToado;
	}

	public void setIsToado(String isToado) {
		this.isToado = isToado;
	}

	public String getTtId() {
		return ttId;
	}

	public void setTtId(String ttId) {
		this.ttId = ttId;
	}

	public String getCoderoute() {
		return coderoute;
	}

	public void setCoderoute(String coderoute) {
		this.coderoute = coderoute;
	}

	public ResultSet getKhlist() {
		return khlist;
	}

	public void setKhlist(ResultSet khlist) {
		this.khlist = khlist;
	}

	public ResultSet getTinhthanh() {
		return tinhthanh;
	}

	public void setTinhthanh(ResultSet tinhthanh) {
		this.tinhthanh = tinhthanh;
	}

	public ResultSet getVung() {
		return vung;
	}

	public void setVung(ResultSet vung) {
		this.vung = vung;
	}

	public ResultSet getKhuvuc() {
		return khuvuc;
	}

	public void setKhuvuc(ResultSet khuvuc) {
		this.khuvuc = khuvuc;
	}

	public ResultSet getDdkd() {
		return ddkd;
	}

	public void setDdkd(ResultSet ddkd) {
		this.ddkd = ddkd;
	}

	public ResultSet getGsbh() {
		return gsbh;
	}

	public void setGsbh(ResultSet gsbh) {
		this.gsbh = gsbh;
	}

	public ResultSet getCoderouteRs() {
		return coderouteRs;
	}

	public void setCoderouteRs(ResultSet coderouteRs) {
		this.coderouteRs = coderouteRs;
	}

	public void setVtchId(String vtchId) {
		this.vtchId = vtchId;
	}
	public String getLoaikhachhang() {
		return loaikhachhang;
	}

	public void setLoaikhachhang(String loaikhachhang) {
		this.loaikhachhang = loaikhachhang;
	}

	public String getNpp_search() {
		return npp_search;
	}
	
	public void setNpp_search(String npp_search) {
		this.npp_search = npp_search;
	}
	
	public String getRoute() {
		return Route;
	}

	public void setRoute(String route) {
		Route = route;
	}

	public String getNpp1Ten() {
		return npp1Ten;
	}

	public void setNpp1Ten(String npp1Ten) {
		this.npp1Ten = npp1Ten;
	}

	public String getNpp1Id() {
		return npp1Id;
	}

	public void setNpp1Id(String npp1Id) {
		this.npp1Id = npp1Id;
	}

	public ResultSet getNhapp() {
		return nhapp;
	}

	public void setNhapp(ResultSet nhapp) {
		this.nhapp = nhapp;
	}

	public String getView() {
		return view;
	}
	
	public void setView(String view) {
		this.view = view;
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
		return this.npp1Ten;
	}

	public void setNppTen(String nppTen) 
	{
		this.npp1Ten = nppTen;
	}
	
	public String getSitecode() 
	{
		return this.sitecode;
	}

	public void setSitecode(String sitecode) 
	{
		this.sitecode = sitecode;
	}
	
	public List<Object> getDatasearch() {
		return datasearch;
	}

	public void setDatasearch(List<Object> datasearch) {
		this.datasearch = datasearch;
	}

	public void createHchRS()
	{
		this.hangcuahang =  this.db.get("select hang as hchTen, pk_seq as hchId from hangcuahang where trangthai='1' order by hang");
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
		this.loaicuahang =  this.db.get("select diengiai as lchTen, pk_seq as lchId from loaicuahang where trangthai='1' order by loai");
	}
	
	public void createNchRS()
	{
		this.nhomcuahang =  this.db.get("select diengiai as nchTen, pk_seq as nchId from nhomcuahang order by diengiai");
	}
	
	public void createNppRS()
	{
		String query = "select ten as npp1Ten, pk_seq as npp1Id from NHAPHANPHOI where pk_seq in ("+Utility.PhanQuyenNPP(this.userId)+") order by PK_SEQ ";
		//System.out.println("createNppRS =  " + query );
		this.nhapp =  this.db.get(query);
	}
	
	public void createRS()
	{
		if (view != null && view.length() > 0) {
			//Trung tâm
		}
		else {
			this.getNppInfo();
		}
		
		this.createHchRS();
		this.createKbhRS();
		this.createLchRS();
		this.createVtchRS();
		this.createNppRS();
		createVung();
		createKhuvuc();
		createGsbh();
		createDdkd();
		createTinhthanh();
		createCodeRouteRs();
	}
	
	public void createTinhthanh() {
		String query = "select pk_seq, ten from tinhthanh";
		this.tinhthanh = db.get(query);
	}
	
	public void createCodeRouteRs() {
		data.clear();
		String query = "";
		if (nppId != null && nppId.length() > 0)
			data.add(nppId);
		if (npp_search != null && npp_search.length() > 0)
			data.add(npp_search);
		
		if ((nppId != null && nppId.length() > 0) || (npp_search != null && npp_search.length() > 0)) {
			query = "\n select pk_seq, ten from dms_route where 1=1";
			if (nppId != null && nppId.length() > 0) {
				query += "\n and npp_fk = ?";
			}
			if (npp_search != null && npp_search.length() > 0) {
				query += "\n and npp_fk = ?";
			}
			//System.out.println("coderouteRs: "+query);
			this.coderouteRs = db.get_v2(query, data);
		}
	}
	
	public void createVung() {
		String query = "";
		query = "select pk_seq, ten from vung";
		this.vung = db.get(query);		
	}
	
	public void createKhuvuc() {
		data.clear();
		String query = "\n select pk_seq, ten from khuvuc where 1=1";
		if (this.vungId != null && this.vungId.length() > 0) {
			data.add(vungId);
			query += "\n and vung_fk = ?";
		}
		
		this.khuvuc = db.get_v2(query, data);
	}
	
	public void createGsbh() {
		data.clear();
		String query = "\n select pk_seq, ten from giamsatbanhang where trangthai = 1";
		if (npp_search != null && npp_search.length() > 0) {
			query += "\n and exists (select 1 from NHAPP_GIAMSATBH where gsbh_fk = giamsatbanhang.pk_seq and npp_fk = ?)";
			data.add(npp_search);
		}
		if (nppId != null && nppId.length() > 0) {
			query += "\n and exists (select 1 from NHAPP_GIAMSATBH where gsbh_fk = giamsatbanhang.pk_seq and npp_fk = ?)";
			data.add(nppId);
		}

		//System.out.println("gsbh rs: "+query);
		this.gsbh = db.get_v2(query, data);
	}
	
	public void createDdkd() {
		data.clear();
		String query = "\n select pk_seq, ten from daidienkinhdoanh where 1=1";
		if (npp_search != null && npp_search.length() > 0) {
			query += "\n and npp_fk = ?";
			data.add(npp_search);
		}
		if (nppId != null && nppId.length() > 0) {
			query += "\n and npp_fk = ?";
			data.add(nppId);
		}
		if (gsbhId != null && gsbhId.length() > 0) {
			query += "\n and exists (select 1 from DDKD_GSBH where ddkd_fk = daidienkinhdoanh.pk_seq and gsbh_fk = ?)";
			data.add(gsbhId);
		}
		//System.out.println("ddkdrs: "+query);
		this.ddkd = db.get_v2(query, data);
	}
	
	private void getNppInfo(){
		geso.dms.distributor.util.Utility util=new geso.dms.distributor.util.Utility();
		this.nppId=util.getIdNhapp(this.userId);
		this.npp1Ten=util.getTenNhaPP();
		this.sitecode=util.getSitecode();
	}
	
	public void init(String search) 
	{
		if (view != null && view.length() > 0) {
			//Trung tâm
		}
		else {
			this.getNppInfo();
		}
		
		String conditionTT = "";
		String query;	
		if (search.length() == 0)
		{		
			query = "\n select isnull(a.mathamchieu,'') as mathamchieu, isnull(a.lat,0)lat, isnull(a.long,0)long, " +
			"\n ddkd.ten ddkdten, ddkd.smartid ddkdma, gs.smartid gsbhma, gs.ten gsbhten, " +
			"\n isnull(route.coderoute,'')coderoute, " +
			"\n 	isnull(route.ten,'')routename, isnull(a.daduyet,0)daduyet, " +
			"\n  	--ROW_NUMBER() OVER(ORDER BY a.pk_seq DESC) AS 'stt', " +
			"\n		a.pk_seq as khId, " +
			"\n  	a.smartid, a.ten as khTen, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, " +
			"\n  	c.ten as nguoisua, d.chietkhau, d.pk_seq as mckId,isnull(a.ANHCUAHANG,'') as ANHCUAHANG, " +
			"\n  	e.diengiai as kbhTen, e.pk_seq as kbhId, f.hang as hchTen, f.pk_seq as hchId, " +
			"\n  	g.DIENGIAI as lchTen, g.pk_seq as lchId, h.ten as nppTen, h.pk_seq as nppId, h.manpp manpp, " +
			"\n  	k.sotienno as ghcnTen, k.pk_seq as ghcnId, l.ten as bgstTen, l.pk_seq as bgstId,  " +
			"\n  	m.vitri as vtchTen, m.pk_seq as vtchId, a.dienthoai, a.diachi, " +
			"\n  	--isnull((select top(1) ten from DAIDIENKINHDOANH where pk_seq = a.DDKD_FK),'') as ddkdtao, " +
			"\n  	--isnull((select top(1) ten from DAIDIENKINHDOANH where pk_seq = a.DDKDTAO_FK),'') as ddkdsua, " +
			"\n  	isnull(isPDASua,'') as  isPDASua  " +
			"\n  from khachhang a inner join nhanvien b on a.nguoitao = b.pk_seq  " +
			"\n  inner join nhanvien c on a.nguoisua = c.pk_seq  " +
			"\n  left join mucchietkhau d on a.chietkhau_fk = d.pk_seq  " +
			"\n  left join kenhbanhang e on a.kbh_fk = e.pk_seq  " +
			"\n  left join hangcuahang f on a.hch_fk = f.pk_seq  " +
			"\n  left join loaicuahang g on a.lch_fk = g.pk_seq " +
			"\n  inner join nhaphanphoi h on a.npp_fk = h.pk_seq  " +
			"\n  left join gioihancongno k on a.ghcn_fk = k.pk_seq  " +
			"\n  left join banggiasieuthi l on a.bgst_fk = l.pk_seq  " +
			"\n  left join vitricuahang m on a.vtch_fk = m.pk_seq  " +
			"\n  left join DMS_Route route on route.pk_seq = a.route_fk " +
			"\n  outer apply " +
			"\n  ( " +
			"\n		SELECT KHACHHANG_FK,BB.DDKD_FK, CC.GSBH_FK " +
			"\n		FROM KHACHHANG_TUYENBH AA " +
			"\n		INNER JOIN TUYENBANHANG BB ON BB.PK_SEQ = AA.TBH_FK " +
			"\n		INNER JOIN DDKD_GSBH CC ON CC.DDKD_FK = BB.DDKD_FK " +
			"\n		where KHACHHANG_FK = a.pk_seq " +
			"\n		GROUP BY KHACHHANG_FK, BB.DDKD_FK, CC.GSBH_FK " +
			"\n  ) DDKD_GSBH " +
			"\n  left join daidienkinhdoanh ddkd on ddkd.pk_seq = DDKD_GSBH.ddkd_fk " +
			"\n  left join giamsatbanhang gs on gs.pk_seq = DDKD_GSBH.GSBH_FK " +
			"\n  where 1=1 ";
		}
		else
		{
			query = search;
		}
		System.out.println("View:"+view);
		if (view != null && view.length() > 0) {
			//Trung tâm
			conditionTT += "\n and a.npp_fk in ("+Utility.PhanQuyenNPP(this.userId)+") ";
			conditionTT += "\n and a.kbh_fk in ("+Utility.PhanQuyenKBH(this.userId)+") ";
		}
		else {
			conditionTT += "\n and a.npp_fk='"+ this.nppId +"' ";
		}

		query += conditionTT;
		//this.createKhBeanList(query);

		if(search.trim().length()>0){
			//System.out.println("Query search: ");
			dbutils.viewQuery(query, datasearch);
			this.khlist = super.createSplittingData_v2(db,super.getItems(), super.getSplittings(), "khId desc", query, this.datasearch, "");
		}
		else{
			//System.out.println("Init List: "+query);
			this.khlist =  super.createSplittingData(super.getItems(), super.getSplittings(), "khId desc", query);
		}
		this.createRS();
	}
	
	public void khChuaPhanTuyen(String dk)
	{
		this.getNppInfo();
		String query;
		dk = dk==null||dk.length()<=0?"":dk;
		if(dk.trim().length()<=0)
		{
			query = "\n select  a.smartid as khId, a.ten as khTen, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua, d.chietkhau, d.pk_seq as mckId," 
				+ "\n e.diengiai as kbhTen, e.pk_seq as kbhId, f.hang as hchTen, f.pk_seq as hchId, g.loai as lchTen, g.pk_seq as lchId, h.ten as nppTen, h.pk_seq as nppId,"
				+ "\n k.sotienno as ghcnTen, k.pk_seq as ghcnId, l.ten as bgstTen, l.pk_seq as bgstId, m.vitri as vtchTen, m.pk_seq as vtchId, a.dienthoai, a.diachi"
				+ "\n from khachhang a inner join nhanvien b on a.nguoitao = b.pk_seq inner join nhanvien c on a.nguoisua = c.pk_seq left join mucchietkhau d on a.chietkhau_fk = d.pk_seq"
				+ "\n left join kenhbanhang e on a.kbh_fk = e.pk_seq left join hangcuahang f on a.hch_fk = f.pk_seq left join loaicuahang g on a.lch_fk = g.pk_seq"
				+ "\n inner join nhaphanphoi h on a.npp_fk = h.pk_seq left join gioihancongno k on a.ghcn_fk = k.pk_seq left join banggiasieuthi l on a.bgst_fk = l.pk_seq left join vitricuahang m on a.vtch_fk = m.pk_seq where a.npp_fk='"+ this.nppId +"' "
				+ "\n and a.trangthai = 1 and a.PK_SEQ NOT IN (SELECT KHACHHANG_FK FROM KHACHHANG_TUYENBH) ";
			this.khlist = super.createSplittingData(super.getItems(), super.getSplittings(), "khId desc", query);
		}
		else
		{
			query = "\n select  a.smartid as khId, a.ten as khTen, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua, d.chietkhau, d.pk_seq as mckId," 
					+ "\n e.diengiai as kbhTen, e.pk_seq as kbhId, f.hang as hchTen, f.pk_seq as hchId, g.loai as lchTen, g.pk_seq as lchId, h.ten as nppTen, h.pk_seq as nppId,"
					+ "\n k.sotienno as ghcnTen, k.pk_seq as ghcnId, l.ten as bgstTen, l.pk_seq as bgstId, m.vitri as vtchTen, m.pk_seq as vtchId, a.dienthoai, a.diachi"
					+ "\n from khachhang a inner join nhanvien b on a.nguoitao = b.pk_seq inner join nhanvien c on a.nguoisua = c.pk_seq left join mucchietkhau d on a.chietkhau_fk = d.pk_seq"
					+ "\n left join kenhbanhang e on a.kbh_fk = e.pk_seq left join hangcuahang f on a.hch_fk = f.pk_seq left join loaicuahang g on a.lch_fk = g.pk_seq"
					+ "\n inner join nhaphanphoi h on a.npp_fk = h.pk_seq left join gioihancongno k on a.ghcn_fk = k.pk_seq left join banggiasieuthi l on a.bgst_fk = l.pk_seq left join vitricuahang m on a.vtch_fk = m.pk_seq where a.npp_fk='"+ this.nppId +"' "
					+ "\n and a.trangthai = 1 and a.PK_SEQ NOT IN (SELECT KHACHHANG_FK FROM KHACHHANG_TUYENBH) "+dk;
			this.khlist = super.createSplittingData_v2(db,super.getItems(), super.getSplittings(), "khId desc", query, this.datasearch, "");
		}		
		 
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

	@Override
	public void DBclose() {
		
		try {
			if(this.hangcuahang != null)
				this.hangcuahang.close();
			if(this.kenhbanhang != null)
				this.kenhbanhang.close();
			if(this.loaicuahang != null)
				this.loaicuahang.close();
			if(this.nhomcuahang != null)
				this.nhomcuahang.close();
			if(this.vitricuahang != null)
				this.vitricuahang.close();
			if(this.nhomcuahang!=null){
				nhomcuahang.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		finally {
			if(this.db != null)
				db.shutDown();
		}
		
	}

	@Override
	public void setKhList(ResultSet khlist) {
		this.khlist = khlist;
		
	}

	@Override
	public ResultSet getKhList() {		
		return khlist;
	}

	@Override
	public void setMsg(String msg) 
	{
		this.msg = msg;
	}

	@Override
	public String getMsg() 
	{	
		return msg;
	}

	@Override
	public String getSodienthoai() {
		// TODO Auto-generated method stub
		return this.sodt;
	}

	@Override
	public void setSodienthoai(String sodt) {
		// TODO Auto-generated method stub
		this.sodt = sodt;
	}
	
}

