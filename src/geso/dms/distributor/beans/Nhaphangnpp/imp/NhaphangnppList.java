package geso.dms.distributor.beans.Nhaphangnpp.imp;

import geso.dms.center.db.sql.dbutils;
import geso.dms.distributor.beans.Nhaphangnpp.INhaphangnppList;
import geso.dms.distributor.util.Utility;

import java.io.Serializable;
import java.sql.ResultSet;

public class NhaphangnppList implements INhaphangnppList, Serializable
{
	private static final long serialVersionUID = -9217977546733610214L;
	private String userId;
	private String nppTen;
	private String nppId;
	private String dangnhap;
	private String sku;
	private String tungay;
	private String denngay;
	private String trangthai;
	private ResultSet nhaphangList;
	private String maList;
	private dbutils db;
	private String IdDonHang;
	private String soct;
	private String sodh;

	public String getSodh() {
		return sodh;
	}

	public void setSodh(String sodh) {
		this.sodh = sodh;
	}

	String Msg ;
	public NhaphangnppList(String[] param)
	{
		this.db = new dbutils();
		this.sku = param[0];
		this.tungay = param[1];
		this.denngay = param[2];
		this.trangthai = param[3];
		this.userId = "";
		this.nppTen = "";
		this.nppId = "";
		
		createNhaphanglist("");
	}
	
	public NhaphangnppList()
	{
		this.db = new dbutils();
		this.soct = "";
		this.userId = "";
		this.nppTen = "";
		this.nppId = "";
		this.sku = "";
		this.tungay = "";
		this.denngay = "";
		this.trangthai = "";
		this.Msg ="";
		this.IdDonHang="";
		this.sodh = "";
	}
	
	public ResultSet getNhaphangList()
	{
		return this.nhaphangList;
	}
	
	public void setNhaphangList(ResultSet nhaphangList)
	{
		this.nhaphangList = nhaphangList;
	}

	public String getUserId()
	{
		return this.userId;
	}
	
	public void setUserId(String userId)
	{
		this.userId = userId;
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
	
	public String getSKU()
	{
		return this.sku;
	}
	
	public void setSKU(String sku)
	{
		this.sku = sku;
	}
	
	public String getTungay()
	{
		return this.tungay;
	}
	
	public void setTungay(String tungay)
	{
		this.tungay = tungay;
	}
	
	public String getDenngay()
	{
		return this.denngay;
	}
	
	public void setDenngay(String denngay)
	{
		this.denngay = denngay;
	}
	
	public String getTrangthai()
	{
		return this.trangthai;
	}
	
	public void setTrangthai(String trangthai)
	{
		this.trangthai = trangthai;
	}

	public String getMalist()
	{
		return this.maList;
	}
	
	public void setMalist(String malist)
	{
		this.maList = malist;
	}

	public String getDangnhap()
	{
		return this.dangnhap;
	}
	
	public void setDangnhap(String dangnhap)
	{
		this.dangnhap = dangnhap;
	}

	public void createNhaphanglist(String querystr)
	{
		String query;
		Utility util=new Utility();
		geso.dms.center.util.Utility utilCenter = new geso.dms.center.util.Utility();
		this.nppId = util.getIdNhapp(userId);
		
		this.nppTen = util.getTenNhaPP();

		this.dangnhap = util.getDangNhap();
		
		if (querystr.length()>0){
			query = querystr;
		} 
		else 
		{
			/*query =  "  select  a.ngaychungtu, isnull( isnull( CAST(a.YCXK_FK as varchar(10)), CAST(a.CHUYENKHO_FK as varchar(10)) ), 'NA' ) as dathang_fk,    "+ 
					 "  	a.ngaynhan, a.pk_seq as chungtu, isnull(e.donvikinhdoanh, '') as donvikinhdoanh, f.ten as kbh ,  0 as sotienavat, b.ten as nguoitao, c.ten as nguoisua, a.trangthai  "+ 
					 "  from nhaphang a  inner join   nhanvien b  on  a.nguoitao = b.pk_seq   "+ 
					 "  	inner  join  nhanvien c on a.nguoisua = c.pk_seq   "+ 
					 "  	left join  donvikinhdoanh e on e.pk_seq = a.dvkd_fk    "+ 
					 "  	left join  kenhbanhang f  on f.pk_seq = a.kbh_fk  "+ 
					 "  where a.npp_fk = '" + nppId + "'  " + 
					 "  order by trangthai, ngaychungtu, chungtu ";*/

			query=
				"	select  a.ngaychungtu,  "+ 
				"	 case     "+
				"		when A.YCXK_FK IS not null then   'XKHO_' + CAST( A.YCXK_FK   AS VARCHAR(50) )  "+
				"		when A.CHUYENKHO_FK IS not null then 'XCNB_' + ( SELECT SoChungTu from ERP_CHUYENKHO where PK_SEQ=A.CHUYENKHO_FK)  "+  
				"		when A.YCXKNPP_FK IS not null then  "+  
				"		'HD_' + (     "+
						"			select SOHOADON from ERP_HOADONNPP_DDH aa      "+
						"			inner join  ERP_YCXUATKHONPP_DDH b on aa.DDH_FK=b.ddh_fk     "+
						"			inner join ERP_HOADONNPP c on c.PK_SEQ=aa.HOADONNPP_FK      "+   
						"			where c.TRANGTHAI in (2,4)	and b.ycxk_fk=a.YCXKNPP_FK  "+			
				"		) when a.CHUYENKHONPP_FK IS NOT NULL THEN 'XCNB_' +(SELECT SoChungTu from ERP_CHUYENKHONPP where PK_SEQ=A.CHUYENKHONPP_FK)  "+
				"	end as dathang_fk ,  "+
				"		a.ngaynhan, a.pk_seq as chungtu, isnull(e.donvikinhdoanh, '') as donvikinhdoanh, f.ten as kbh ,  0 as sotienavat, b.ten as nguoitao,  "+
				"		 c.ten as	nguoisua, a.trangthai  ,a.YCXK_FK,a.YCXKNPP_FK,a.CHUYENKHO_FK,a.CHUYENKHONPP_FK  "+
				"	from nhapkho a  inner join   nhanvien b  on  a.nguoitao = b.pk_seq "+   
				"	inner  join  nhanvien c on a.nguoisua = c.pk_seq     "+
				"	left join  donvikinhdoanh e on e.pk_seq = a.dvkd_fk  "+   
				"	left join  kenhbanhang f  on f.pk_seq = a.kbh_fk  "+  
				"	where a.npp_fk = '" + nppId + "' "+// and a.kho_fk in "+utilCenter.quyen_kho(this.userId)+
				"	order by ngaychungtu desc,trangthai desc, chungtu desc "; 
			
		}
		System.out.println("NHAPHANG: "+query);
		this.nhaphangList =  this.db.get(query);
	}
	
	public void DBclose(){
		try {
			if(this.nhaphangList != null)
				this.nhaphangList.close();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		if(!(this.db == null)){
			this.db.shutDown();
		}
	}

	public void setMsg(String Msg) {
	  this.Msg = Msg;
		
	}

   public String getMsg() {
		
		return this.Msg;
	}

@Override
public void setDonHangId(String iddonhang) 
{

	this.IdDonHang=iddonhang;
}

@Override
public String getDonHangId() {

	return this.IdDonHang;
}

	public String getSoct() {
		return soct;
	}

	public void setSoct(String soct) {
		this.soct = soct;
	}
	


}
