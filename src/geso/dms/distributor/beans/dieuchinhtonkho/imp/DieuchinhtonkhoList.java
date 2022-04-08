package geso.dms.distributor.beans.dieuchinhtonkho.imp;

import java.io.Serializable;
import java.sql.ResultSet;

import geso.dms.center.db.sql.dbutils;
import geso.dms.distributor.beans.dieuchinhtonkho.IDieuchinhtonkhoList;
import geso.dms.distributor.db.sql.*;
public class DieuchinhtonkhoList implements IDieuchinhtonkhoList, Serializable
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
	
	
	private ResultSet ncc;
	private ResultSet dvkd;
	private ResultSet kbh;
	private ResultSet kho;
	String msg = "";
	private ResultSet dctkList;
	private dbutils db;
	
	public DieuchinhtonkhoList(String[] param)
	{
		this.db = new dbutils();
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
	
	public DieuchinhtonkhoList()
	{
		this.db = new dbutils();
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
		this.msg = "";
		
	}
	public String getUserId()
	{
		return this.userId;
	}
	
	public void setUserId(String userId)
	{
		this.userId = userId;
		getNppInfo();
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
		
			getNppInfo();
	    	String query;
		
			if (querystr.length()>0){
				query = querystr;
			}else{
				query =
						"select distinct a.Modified_Date, a.ngaydc, a.pk_seq as chungtu,d.donvikinhdoanh as dvkd, e.ten as kbh, "+
						    	" f.ten, a.tongtien, a.trangthai, b.ten as nguoitao, c.ten as nguoisua, isnull(g.ten,'0') as nguoiduyet ,a.NppDaChot"+ 
						    	" from dieuchinhtonkho a inner join  nhanvien b on b.pk_Seq=a.nguoitao inner join nhanvien c on a.nguoisua=c.pk_Seq "+ 
						    	" inner join donvikinhdoanh d on d.pk_Seq=a.dvkd_fk  "+
						    	" inner join  kenhbanhang e on e.pk_seq=a.kbh_fk inner join  kho f  on f.pk_Seq= a.kho_fk "+ 
						    	" left join  nhanvien g  on g.pk_seq=a.nguoiduyet "+
						    	" where  a.npp_fk='"+nppId+"'";
						
						
			}
			// sort descending
			query += " order by a.Modified_Date desc ";
			//System.out.println("======Vuna===query crelist===="+ query);
			this.dctkList =  this.db.get(query);
	}

	public void init0(){
		
		String query = "select e.pk_seq as dvkdId, e.donvikinhdoanh as dvkd from nhaphanphoi a, nhacungcap b, nhacungcap_dvkd c, nhapp_nhacc_donvikd d, donvikinhdoanh e where a.pk_seq = '"+ this.nppId + "' and a.pk_seq = d.npp_fk and b.pk_seq = c.ncc_fk and c.pk_seq = d.ncc_dvkd_fk and c.dvkd_fk = e.pk_seq";
		this.dvkd = this.db.get(query);

		query = "select c.pk_seq as kbhId, c.diengiai as kbh from nhaphanphoi a, nhapp_kbh b, kenhbanhang c where a.pk_seq = '"+ this.nppId +"' and a.pk_seq = b.npp_fk and b.kbh_fk = c.pk_seq";
		this.kbh = this.db.get(query);
	
		query = "select distinct a.pk_seq as khoId, a.ten ,a.diengiai from kho a, nhapp_kho b where a.pk_seq = b.kho_fk and b.npp_fk = '"+ this.nppId + "' and a.trangthai ='1'" ;
		this.kho = this.db.get(query);
			
	}
	
	private void getNppInfo(){
		
		/*ResultSet rs = this.db.get("select a.pk_seq, a.ten, a.sitecode from nhaphanphoi a, nhanvien b where b.dangnhap = a.ma and b.pk_seq ='" + this.userId + "'");
		try{
			if (!(rs == null)){
				rs.next();
				this.nppId = rs.getString("pk_seq");
				this.nppTen = rs.getString("ten");
				
			}else{
				this.msg = "select a.pk_seq, a.ten, a.sitecode from nhaphanphoi a, nhanvien b where b.dangnhap = a.ma and b.pk_seq ='" + this.userId + "'";
				this.nppId = "";
				this.nppTen = "";
			
			}
			
		}catch(Exception e){}	
		
		*/
		//Phien ban moi
		geso.dms.distributor.util.Utility util=new geso.dms.distributor.util.Utility();
		this.nppId=util.getIdNhapp(this.userId);
		this.nppTen=util.getTenNhaPP();
		//this.sitecode=util.getSitecode();
	}
	
	public void DBclose(){
		
		try {
			if(this.dctkList != null)
				this.dctkList.close();
			if(this.dvkd != null)
				this.dvkd.close();
			if(this.kho != null)
				this.kho.close();
			if(this.ncc != null)
				this.ncc.close();
			if(this.kbh!=null){
				kbh.close();
			}
			if(!(this.db == null)){
				this.db.shutDown();
			}
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	
	public String getMsg()
	{
		return msg;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}

	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}
	
}
