package geso.dms.center.beans.baocao.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import geso.dms.center.db.sql.*;
import geso.dms.center.util.*;
import geso.dms.center.beans.baocao.IBaocao;

public class Baocao implements IBaocao 
{
	

	String congtyId;
	String userId;
	String userTen;
	ResultSet LsxRs;
	ResultSet loaiSpRs;
	ResultSet spRs;
	ResultSet npp;
	ResultSet dvthRs;
	String dvthId="";
	ResultSet lsxRs;
	ResultSet UrlRs;
	String url="";
	String username="";
	String password="";
	String pk_seq_url="";		
	


	public String getDvthId() {
		return dvthId;
	}


	public void setDvthId(String dvthId) {
		this.dvthId = dvthId;
	}

	ResultSet khoRs;
	ResultSet khonhanRs;
	String khonhanIds;	
	String loaibaocao="1";
	ResultSet chungloaiRs;
	ResultSet dvkdRs;
	String Layhangkho_CXL;
	String lenhsanxuat;
	String loaisanphamIds;
	String Check_SpCoPhatSinh;
	String spIds;
	String khoIds;
	String khoId_CXL;
	String chungloaiIds;
	String khoTen;
	String dvkdIds;
	String NhamayId;
	ResultSet giaRs;
	String tungay;
	String denngay;
	String msg;
	String flag;
	String laychokiem;
	String NppId;
	String lsxId;
	ResultSet RsReportName;
	String  ReportName;	
	
	ResultSet ndnhapRs;
	String ndnhapIds;
	ResultSet RsNhamay;
	
	ResultSet ndxuatRs;
	String ndxuatIds;
	
	String pivot = "0";
	
	String Xemtheolo;
	
	
	ResultSet maspRS;
	String maspIds;
	String IsHoDaPhanBo;
	String viewtchitiet="";
	dbutils db;
	Utility util;
	String lsxID;
	String year;
	String month;
	
	String loaidoituong="";
	String codoituong="";
	String doituongid="";
	ResultSet doituongRs;
	
	public String getLoaidoituong() {
		return loaidoituong;
	}


	public void setLoaidoituong(String loaidoituong) {
		this.loaidoituong = loaidoituong;
	}
	public String getCodoituong() {
		return codoituong;
	}


	public void setCodoituong(String codoituong) {
		this.codoituong = codoituong;
	}


	public String getDoituongid() {
		return doituongid;
	}


	public void setDoituongid(String doituongid) {
		this.doituongid = doituongid;
	}


	public ResultSet getDoituongRs() {
		return doituongRs;
	}


	public void setDoituongRs(ResultSet doituongRs) {
		this.doituongRs = doituongRs;
	}

	
	
	public String getLsxID() {
		return lsxID;
	}


	public void setLsxID(String lsxID) {
		this.lsxID = lsxID;
	}


	public String getSoluong() {
		return soluong;
	}


	public void setSoluong(String soluong) {
		this.soluong = soluong;
	}


	public String getMaSp() {
		return maSp;
	}


	public void setMaSp(String maSp) {
		this.maSp = maSp;
	}


	public String getTenSp() {
		return tenSp;
	}


	public void setTenSp(String tenSp) {
		this.tenSp = tenSp;
	}
	public ResultSet getDvthRs() {
		return dvthRs;
	}


	public void setDvthRs(ResultSet dvthRs) {
		this.dvthRs = dvthRs;
	}

	String soluong;
	String maSp;
	String tenSp;
	ResultSet nccRs;
	String nccIds;
	
	public Baocao()
	{
		this.userId = "";
		this.NhamayId="";
		this.IsHoDaPhanBo="";
		this.userTen = "";
		this.loaisanphamIds = "";
		this.dvkdIds = "";
		this.chungloaiIds = "";
		this.spIds = "";
		this.khoIds = "";
		this.Layhangkho_CXL="";
		this.khoTen = "";
		this.tungay = "";
		this.NhamayId="";
		this.denngay = "";
		this.msg = "";
		this.khoId_CXL="";
		this.flag = "1";
		this.Xemtheolo="0";
		this.laychokiem = "0";
		this.maspIds = "";
		this.ndnhapIds = "";
		this.ndxuatIds= "";
		this.Check_SpCoPhatSinh="";
		this.viewtchitiet="";
		this.nccIds = "";
		this.NppId="";
		this.month="";
		this.year="";
		this.db = new dbutils();
		this.util = new Utility();
		init();
	}
	
	public void init()
	{
		String query = "select pk_seq,ten from nhaphanphoi where trangthai ='1' ";
		this.npp = db.get(query);
	}
	
	public String getUserId()
	{
		return this.userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;	
	}
	public void Init_ReportDoichieu() {
		
	/*	if(url.length()>0){
			db=new dbutils(url,username,password);
		}*/
		
		String query = "SELECT TEN_THUTUC,TENCHUCNANG FROM ERP_BAOCAODOICHIEU";
		System.out.println("::: LAY KHO : "+ query);
		
		this.RsReportName = db.get(query);
		
		query="SELECT PK_SEQ,USERNAME,PASS,URL ,duan FROM TBL_KETNOI " ;
		this.UrlRs=db.get(query);
		
		
		
	}
	public String getUserTen() 
	{
		return this.userTen;
	}

	public void setUserTen(String userTen) 
	{
		this.userTen = userTen;
	}
	
	public ResultSet getLoaiSanPhamRs() 
	{
		return this.loaiSpRs;
	}
	
	public void setLoaiSanPhamRs(ResultSet loaisp) 
	{
		this.loaiSpRs = loaisp;
	}

	public ResultSet getDvkdRs() 
	{
		return this.dvkdRs;
	}
	
	public void setDvkdRs(ResultSet dvkdRs) 
	{
		this.dvkdRs = dvkdRs;
	}

	public String getLoaiSanPhamIds() 
	{
		return this.loaisanphamIds;
	}

	public void setLoaiSanPhamIds(String loaispIds)
	{
		this.loaisanphamIds = loaispIds; 
	}

	public String getDvkdIds() 
	{
		return this.dvkdIds;
	}

	public void setDvkdIds(String dvkdIds)
	{
		this.dvkdIds = dvkdIds; 
	}

	public String getTuNgay()
	{
		return this.tungay;
	}

	public void setTuNgay(String tungay)
	{
		this.tungay = tungay;
	}

	public String getDenNgay() 
	{
		return this.denngay;
	}

	public void setDenNgay(String denngay) 
	{
		this.denngay = denngay;
	}
	
	public ResultSet getSanPhamRs() 
	{
		return this.spRs;
	}

	public void setSanPhamRs(ResultSet sanpham) 
	{
		this.spRs = sanpham;	
	}

	public String getSanPhamIds() 
	{	
		return this.spIds;
	}

	public void setSanPhamIds(String spIds) 
	{
		this.spIds = spIds;
	}
	
	public void createRs_XUATKHO() 
	{ }


	public void createRs() 
	{ }
	
	public void createRsBCHSD() 
	{ } 
	
	public void createRsBCKHO() 
	{ }

	public void close()
	{
		
		
		try 
		{
		/*	if(this.loaiSpRs != null)
				this.loaiSpRs.close();
			
			if(this.chungloaiRs != null)
				this.chungloaiRs.close();
				
			if(this.spRs != null)
				this.spRs.close();*/
			
			if(LsxRs!=null) LsxRs.close();
			if(loaiSpRs!=null) loaiSpRs.close();
			if(spRs!=null) spRs.close();
			if(dvthRs!=null) dvthRs.close();
			if(lsxRs!=null) lsxRs.close();
			if(khoRs!=null) khoRs.close();
			if(khonhanRs!=null) khonhanRs.close();
			if(chungloaiRs!=null) chungloaiRs.close();
			if(dvkdRs!=null) dvkdRs.close();
			if(giaRs!=null) giaRs.close();
			if(RsReportName!=null) RsReportName.close();
			if(ndnhapRs!=null) ndnhapRs.close();
			if(RsNhamay!=null) RsNhamay.close();
			if(ndxuatRs!=null) ndxuatRs.close();
			if(maspRS!=null) maspRS.close();
			if(doituongRs!=null) doituongRs.close();
			if(nccRs!=null) nccRs.close();
		} 
		catch (SQLException e) {e.printStackTrace();}
		
		finally{
			if(this.db!=null) this.db.shutDown();
		}
	}

	public ResultSet getKhoRs() 
	{
		return this.khoRs;
	}

	public void setKhoRs(ResultSet khoRs) 
	{
		this.khoRs = khoRs;
	}

	public String getKhoIds() 
	{
		return this.khoIds;
	}

	public void setKhoIds(String khoId) 
	{
		this.khoIds = khoId;
	}

	public String getKhoTen() 
	{
		return this.khoTen;
	}

	public void setKhoTen(String khoTen) 
	{
		this.khoTen = khoTen;
	}

	
	public ResultSet getChungloaiRs() 
	{
		return this.chungloaiRs;
	}

	public void setChungloaiRs(ResultSet clRs)
	{
		this.chungloaiRs = clRs;
	}

	
	public String getChungloaiIds() {
		
		return this.chungloaiIds;
	}

	
	public void setChungloaiIds(String loaispIds) {
		
		this.chungloaiIds = loaispIds;
	}

	public String getMsg() {

		return this.msg;
	}

	public void setMsg(String msg) {
		
		this.msg = msg;
	}

	public String getFlag() {

		return this.flag;
	}

	public void setFlag(String flag) {
	
		this.flag = flag;
	}

	public String getHangchokiem() {

		return this.laychokiem;
	}

	public void setHangchokiem(String chokiem) {
		
		this.laychokiem = chokiem;
	}
 
	public String getXemtheolo() {
		 
		return this.Xemtheolo;
	}

 
	public void setXemtheolo(String Xemtheolo) {
		this.Xemtheolo=Xemtheolo;
	}

	
	public String getPivot() {
		return this.pivot;
	}

	
	public void setPivot(String pivot) {
		this.pivot = pivot;
	}

	
	public ResultSet getMaSanPhamRs() {
		
		return this.maspRS;
	}

	
	public void setMaSanPhamRs(ResultSet loaisp) {
		
		this.maspRS = loaisp;
	}

	
	public String getMaSanPhamIds() {
		
		return this.maspIds;
	}

	
	public void setMaSanPhamIds(String loaispIds) {
		
		this.maspIds = loaispIds;
	}

	public ResultSet getNdnhapRs()
	{
		return ndnhapRs;
	}
	
	public void setNdnhapRs(ResultSet ndnhapRs) 
	{
		this.ndnhapRs = ndnhapRs;
	}
	
	public String getNdnhapIds()
	{
		return ndnhapIds;
	}
	
	public void setNdnhapIds(String ndnhapIds) 
	{
		this.ndnhapIds = ndnhapIds;
	}
	
	public ResultSet getNdxuatRs()
	{
		return ndxuatRs;
	}
	
	public void setNdxuatRs(ResultSet ndxuatRs) 
	{
		this.ndxuatRs = ndxuatRs;
	}
	
	public String getNdxuatIds()
	{
		return ndxuatIds;
	}
	
	public void setNdxuatIds(String ndxuatIds) 
	{
		this.ndxuatIds = ndxuatIds;
	}



	
	public String getCheck_SpCoPhatSinh() {
		
		return this.Check_SpCoPhatSinh;
	}



	
	public void setCheck_SpCoPhatSinh(String sp_cophatsinh) {
		
		this.Check_SpCoPhatSinh=sp_cophatsinh;
	}



	
	public String getHoPhanBo() {
		
		return this.IsHoDaPhanBo;
	}



	
	public void setHoDaPhanBo(String hodaphanbo) {
		
		this.IsHoDaPhanBo=hodaphanbo;
	}



	
	public ResultSet getRsNhamay() {
		
		return RsNhamay;
	}



	
	public String getNhamayId() {
		
		return NhamayId;
	}



	
	public void setNhamayId(String NhamayId) {
		
		this.NhamayId=NhamayId;
	}



	
	public String getLayHangKho_CXL() {
		
		return this.Layhangkho_CXL;
	}



	
	public void setLayHangKho_CXL(String layhangkho_cxl) {
		
		 this.Layhangkho_CXL=layhangkho_cxl;
	}



	
	public String getKhoId_CXL() {
		
		return khoId_CXL;
	}



	
	public void setKhoId_CXL(String khoId_CXL) {
		
		this.khoId_CXL=khoId_CXL;
	}



	
	public void set_view_chitiet(String view_chitiet) {
		
		this.viewtchitiet=view_chitiet;
	}



	
	public String get_view_chitiet() {
		
		return this.viewtchitiet;
	}


	
	public ResultSet getNccRs() {
		
		return this.nccRs;
	}


	
	public void setNccRs(ResultSet nccRs) {
		
		this.nccRs = nccRs;
	}


	
	public String getNccIds() {
		
		return this.nccIds;
	}


	
	public void setNccIds(String nccId) {
		
		this.nccIds = nccId;
	}


	@Override
	public void setLenhsanxuat(String lsxID) {
		this.lenhsanxuat= lsxID;
	}


	@Override
	public String getLenhsanxuat() {
		return this.lenhsanxuat;
	}


	@SuppressWarnings("null")
	@Override
	public ResultSet getListGia (String lenhsanxuat) {
		String[] lenhsanxuatID = null;
		lenhsanxuatID[0]=lenhsanxuat;
			  
		this.giaRs= db.getRsByPro("REPORT_CHITIET_LSX", lenhsanxuatID);
		
		
		return this.giaRs;
	}


	@Override
	public String getMonth() {
		return this.month;
	}


	@Override
	public void setMonth(String month) {
		this.month=month;
	}


	@Override
	public String getYear() {
		return this.year;
	}


	@Override
	public void setYear(String year) {
		this.year=year;
	}


	@Override
	public void setNppId(String nppid) {
		this.NppId=nppid;
	}


	@Override
	public String getNppId() {
		return this.NppId;
	}


	@Override
	public String getCongtyId() {
		return this.congtyId;
	}
	public ResultSet getRsReportName() {
		
		return 	this.RsReportName;
	}
	
	public void setRsReportName(ResultSet rs) {
		
		this.RsReportName=rs;
	}
	
	public String getReportName() {
		
		return this.ReportName;
	}
	
	public void setReportName(String ReportName) {
		
		this.ReportName=ReportName;
	}

	@Override
	public void setCongtyId(String congtyId) {
		this.congtyId = congtyId;
	}
	public void Init_ReportNoNCC() {
		
		String query = "SELECT TEN_THUTUC,TENCHUCNANG FROM ERP_BAOCAONONCC";
		System.out.println("::: LAY KHO : "+ query);
		this.RsReportName = db.get(query);
		query="select pk_seq, ma + ' - ' + ten as ten from ERP_NHACUNGCAP where trangthai = '1' and congty_fk = "+this.congtyId+" order by ten asc ";
		System.out.println("::: LAY NCC : "+ query);
		this.nccRs = this.db.get(query);
	 	
	}	
	
	public void createRsBC_GiaThanh() 
	{
		String[] _date = this.getdate().split("-");
		
		if( this.month.trim().length() <= 0 )
			this.month = Integer.toString( Integer.parseInt( _date[1] ) );
		
		if( this.year.trim().length() <= 0 )
			this.year = Integer.toString( Integer.parseInt( _date[0] ) );
		
		String query = "select pk_seq, ma, ten from ERP_SANPHAM where congty_fk = '" + this.congtyId + "' " + 
						" and pk_seq in ( select sanpham_fk from ERP_GIATHANH_LENHSANXUAT where congty_fk = '" + this.congtyId + "' and thang = '" + this.month + "' and nam = '" + this.year + "' ) ";
		
		System.out.println(":: LAY SAN PHAM: " + query);
		this.spRs = db.get(query);
		
		if( this.spIds.trim().length() > 0 )
		{
			query = "select pk_seq, diengiai " +
					"from ERP_LENHSANXUAT_GIAY  " +
					"where pk_seq in (  select lenhsanxuat_fk from ERP_GIATHANH_LENHSANXUAT " + 
					" 					where sanpham_fk in ( " + this.spIds + " ) and congty_fk = '" + this.congtyId + "' and thang = '" + this.month + "' and nam = '" + this.year + "' ) " + 
					"order by pk_seq ";
			
			System.out.println(":: LAY LSX: " + query);
			this.lsxRs = db.get(query);
		}
	}
	
	public ResultSet getLsxRs() {
		return lsxRs;
	}


	public void setLsxRs(ResultSet lsxRs) {
		this.lsxRs = lsxRs;
	}
	
	
	public String getLsxIds() {
		
		return this.lsxId;
	}

	public void setLsxIds(String lsxIds) {
		
		this.lsxId = lsxIds;
	}
	public String getdate() 
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
 
	public void createRsBC_CPSX() 
	{
		String query = "SELECT DISTINCT DVKD.PK_SEQ AS DVKDID, DVKD.DONVIKINHDOANH AS DVKD, DVKD.DIENGIAI  " +
						"FROM DONVIKINHDOANH DVKD  Where dvkd.congty_fk = "+this.congtyId;
	
		this.dvkdRs = this.db.get(query);

	}
	
	public void createRsBC_QuanTri(){
		String query = "SELECT PK_SEQ DVKDID, DONVIKINHDOANH DVKD FROM DONVIKINHDOANH WHERE CONGTY_FK = " +this.congtyId;
		this.dvkdRs = this.db.get(query);
	}
	public ResultSet getKhonhanRs() {
		return khonhanRs;
	}
	public void setKhonhanRs(ResultSet khonhanRs) {
		this.khonhanRs = khonhanRs;
	}
	public String getLoaibaocao() {
		return loaibaocao;
	}
	public void setLoaibaocao(String loaibaocao) {
		this.loaibaocao = loaibaocao;
	}
	public String getKhonhanIds() {
		return khonhanIds;
	}
	public void setKhonhanIds(String khonhanIds) {
		this.khonhanIds = khonhanIds;
	}


	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.username;
	}


	@Override
	public void setUsername(String Username) {
		// TODO Auto-generated method stub
		this.username=Username;
	}


	@Override
	public String getpassword() {
		// TODO Auto-generated method stub
		return this.password;
	}


	@Override
	public void setpassword(String password) {
		// TODO Auto-generated method stub
		this.password=password;
	}


	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return this.url;
	}


	@Override
	public void setUrl(String Url) {
		// TODO Auto-generated method stub
		this.url=Url;
	}
	

	@Override
	public ResultSet getUrlRs() {
		// TODO Auto-generated method stub
		return this.UrlRs;
	}


	@Override
	public void setUrlRs(ResultSet UrlRs) {
		// TODO Auto-generated method stub
		this.UrlRs= UrlRs;
	}


	@Override
	public String getPk_seqUrl() {
		// TODO Auto-generated method stub
		return pk_seq_url;
	}


	@Override
	public void setPk_seqUrl(String Pk_seqUrl) {
		// TODO Auto-generated method stub
		pk_seq_url=Pk_seqUrl;
	}
	
	public void setnpp(ResultSet npp) {

		this.npp = npp;
	}

	public ResultSet getnpp() {

		return this.npp;
	}
}
