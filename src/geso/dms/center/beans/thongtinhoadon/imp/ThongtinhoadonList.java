package geso.dms.center.beans.thongtinhoadon.imp;

import java.io.Serializable;
import java.sql.ResultSet;

import geso.dms.center.db.sql.db_Sync;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.beans.thongtinhoadon.IThongtinhoadonList;
import geso.dms.center.util.OracleConnUtils;
import geso.dms.center.util.Utility;
public class ThongtinhoadonList implements IThongtinhoadonList, Serializable
{
	private static final long serialVersionUID = -9217977546733610214L;
	private String userId;
	private String userTen;
	private String nppTen;
	private String nppId;
	
	private String nccId;
	private String dvkdId;
	private String kbhId;
	private String khoId;
	
	private String tungay;
	private String denngay;
	private String trangthai;
	private String msg;
	
	private ResultSet ncc;
	private ResultSet dvkd;
	private ResultSet kbh;
	private ResultSet kho;
	private ResultSet nhaPhanPhoiRs;
	
	private ResultSet dctkList;
	private OracleConnUtils db;
	
	public ThongtinhoadonList(String[] param)
	{
		this.db = new OracleConnUtils();
		this.userId = param[0];
		this.nppTen = param[1];
		this.nppId 	= param[2];
		this.nccId = param[3];
		this.dvkdId = param[4];
		this.kbhId = param[5];
		this.khoId = param[6];
		this.tungay = param[7];
		this.denngay = param[8];
	}
	
	public ThongtinhoadonList()
	{
		this.db = new  OracleConnUtils();
		this.userId = "";
		this.nppTen = "";
		this.nppId = "";
		this.nccId = "";
		this.dvkdId = "";
		this.kbhId = "";
		this.khoId = "";
		this.tungay = "";
		this.denngay = "";
		this.trangthai = "";
		
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
	
	public ResultSet getDctkList()
	{
		return this.dctkList;
	}
	
	public void setDctkList(ResultSet dctkList)
	{
		this.dctkList = dctkList;
	}
	
	public String getDvkdId()
	{
		return this.dvkdId;
	}
	
	public void setDvkdId(String dvkdId)
	{
		this.dvkdId = dvkdId;
	}
	
	public String getKbhId()
	{
		return this.kbhId;
	}
	
	public void setKbhId(String kbhId)
	{
		this.kbhId = kbhId;
	}

	public String getKhoId()
	{
		return this.khoId;
	}
	
	public void setKhoId(String khoId)
	{
		this.khoId = khoId;
	}

	public ResultSet getKho()
	{
		return this.kho;
	}
	
	public void setKho(ResultSet kho)
	{
		this.kho = kho;
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
	
	public ResultSet getDvkd()
	{
		return this.dvkd;
	}
	
	public void setDvkd(ResultSet dvkd)
	{
		this.dvkd = dvkd;
	}

	public ResultSet getKbh()
	{
		return this.kbh;
	}
	
	public void setKbh(ResultSet kbh)
	{
		this.kbh = kbh;
	}
	
	public void createDctklist(String querystr){
		String query;
		Utility Ult = new Utility();
		if (querystr.length()>0){
			query = querystr;
		}else
		{
			query="  select a.NGAY_HD as NGAYHD,a.SO_HD as SOHOADON, a.LOAIHD as LOAIHOADON,a.MANPP,a.KENH,a.SOSO as SO,sum(a.TIENSAUTHUE) as TIENSAUTHUE "+ 
			" from  apps.tbl_hoadon  a where a.MANPP in (3638,2724,2542,2910,7262,6632,3243) and to_char(created_date, 'yyyy-mm-dd') > '2015-12-06' "+
			" group by a.NGAY_HD,a.SO_HD, a.LOAIHD,a.SOSO,a.MANPP,a.KENH ";
			
		}
	
		this.dctkList =  this.db.get(query);
		System.out.print("Init TTHD: "+ query);
			
	}

	public String createQueryString(){
		//String query = "select distinct a.ngaydc, a.pk_seq as chungtu, d.donvikinhdoanh as dvkd, e.ten as kbh, f.ten, a.tongtien, a.trangthai, b.ten as nguoitao, c.ten as nguoisua from dieuchinhtonkho a, nhanvien b, nhanvien c, donvikinhdoanh d, kenhbanhang e, kho f where a.nguoitao = b.pk_seq and a.nguoisua = c.pk_seq and a.dvkd_fk = d.pk_seq and a.kbh_fk = e.pk_seq and a.kho_fk=f.pk_seq ";
		String query="";
		if (this.dvkdId.length()>0){
			query = query + " and d.pk_seq = '" + this.dvkdId + "'";
		}
		
		if (this.kbhId.length() >0){
			query = query + " and e.pk_seq= '" + this.kbhId + "'";
		}
		
		if (this.khoId.length() > 0){
			query = query + " and f.pk_seq = '" + this.khoId + "'";
		}
		
		if (this.trangthai.length() > 0){
			query = query + " and a.trangthai = '" + this.trangthai + "'";
		}

		if (this.tungay.length() > 0){
				query = query + " and to_char(a.NGAYHD, 'yyyy-mm-dd') >= '" + this.tungay + "'";
		}
		
		if (this.denngay.length() > 0){
			query = query + " and to_char(a.NGAYHD, 'yyyy-mm-dd') <= '" + this.denngay + "'";
		}
		
		if (this.nppId.length() > 0)
		{
			query += " and a.npp_fk = '" + this.nppId + "'";
		}
		
			//Sua lai ket kieu moi va co ket phan quyen
		String query1 =" select a.NGAY_HD as NGAYHD,a.SO_HD as SOHOADON, a.LOAIHD as LOAIHOADON,a.MANPP,a.KENH,a.SOSO as SO,sum(a.TIENSAUTHUE) as TIENSAUTHUE "+ 
				" from  apps.tbl_hoadon   a where 1 = 1 "+query;
		
				
		query1 +=		" group by a.NGAY_HD,a.SO_HD, a.LOAIHD,a.SOSO,a.MANPP,a.KENH ";


		
		return query1;
	}
	
	public void init0(){
		Utility  Ult = new Utility();
		//String query = "select pk_seq as dvkdId, donvikinhdoanh as dvkd from donvikinhdoanh ";
		String query ="select a.pk_seq as dvkdId, a.donvikinhdoanh as dvkd from donvikinhdoanh a,Nhacungcap_dvkd b where a.pk_seq = b.dvkd_fk and a.trangthai='1' and b.checked ='1'";
		this.dvkd = this.db.get(query);

		query = "select pk_seq as kbhId, diengiai as kbh from kenhbanhang where pk_seq in" + Ult.quyen_kenh(this.userId);
		this.kbh = this.db.get(query);
	
		query = "select distinct pk_seq as khoId, ten,diengiai from kho" ;
		this.kho = this.db.get(query);
		
		query = "select pk_seq, ten from NhaPhanPhoi";
		this.nhaPhanPhoiRs = this.db.get(query);
		
		System.out.println("===Vuna===");
		System.out.println("===Vuna==quety=resuftSet=NhaPhanPhoi====" + query);
		System.out.println("===Vuna===" + this.nhaPhanPhoiRs);
	}
	
	
	public void DBclose(){
		if(!(this.db == null)){
			this.db.shutDown();
		}
	}

	@Override
	public ResultSet getNhaPhanPhoiRs() {		
		return nhaPhanPhoiRs;
	}


}