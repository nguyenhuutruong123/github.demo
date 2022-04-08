package geso.dms.distributor.beans.denghidathangnpp.imp;

import java.io.Serializable;
import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;

import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;
import geso.dms.distributor.beans.denghidathangnpp.IDenghidathangnppList;

public class DenghidathangnppList implements IDenghidathangnppList, Serializable {
	private String userId;
	private String nppTen;
	private String nppId;
	private String sku;
	private String tungay;
	private String denngay;
	private String trangthai;
	private ResultSet dndhList;
	private String malist;
	private String ordered;
	private dbutils db;
	private String maspstr;
	private String msg;
	public DenghidathangnppList(String[] param)
	{
		this.db = new dbutils();
		this.sku = param[0];
		this.tungay = param[1];
		this.denngay = param[2];
		this.trangthai = param[3];
		this.userId = "";
		this.nppTen = "";
		this.nppId = "";
		
	}
	
	public DenghidathangnppList()
	{
		this.db = new dbutils();
		this.userId = "";
		this.nppTen = "";
		this.nppId = "";
		this.sku = "";
		this.tungay = "";
		this.denngay = "";
		this.trangthai = "";
		this.maspstr = "";
	}
	
	public ResultSet getDndhList()
	{
		return this.dndhList;
	}
	
	public void setDndhList(ResultSet dndhList)
	{
		this.dndhList = dndhList;
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

	public String getOrdered()
	{
		return this.ordered;
	}
	
	public void setOrdered(String ordered)
	{
		this.ordered = ordered;
	}

	public String getMalist()
	{
		return this.malist;
	}
	
	public void setMalist(String malist)
	{
		this.malist = malist;
	}

	public String getMaspstr() 
	{
		String query = "select pk_seq as id, ma, ten from sanpham order by pk_seq";
		ResultSet rs = this.db.get(query);
		try{
			while(rs.next()){
				if (this.maspstr.length()==0){
					this.maspstr = "\"" + rs.getString("ma") +  " - " + rs.getString("ten") + "\"";
				}else{
					this.maspstr = this.maspstr + ",\"" + rs.getString("ma") +  " - " + rs.getString("ten") + "\"";
				}
			}
		}catch(Exception e){}
		
		
		return this.maspstr;
	}
	
	public void setMaspstr(String maspstr) 
	{
		this.maspstr = maspstr;
	}
	
	public String getMessage(){
		return this.msg;
	}
	public void createDndhlist(){
		
		getNppInfo();
		String query; 
			String st ="";
		if(this.tungay.length()>0)
			st = st + " and a.ngaydat >= '"+ this.tungay +"'";
		if(this.denngay.length()>0)
			st = st + " and a.ngaydat <='"+ this.denngay +"'";
		if(this.trangthai.length() >0)
			st = st + " and a.trangthai ='"+ this.trangthai +"'";
		if(st.length()>0){
			query = "select distinct a.ngaydat, a.pk_seq as chungtu,e.donvikinhdoanh, a.sotienavat, b.ten as nguoitao, c.ten as nguoisua, "+
			 " a.trangthai,ddh.pk_seq as dondathang, a.dadathang,a.SOTIENAVAT,a.SOTIENBVAT,a.VAT,convert(numeric(18,0),a.SOTIENAVAT) - convert(numeric(18,0),a.dadathang) "+
			 " as conlai from denghidathang a inner join  nhanvien b on a.nguoitao = b.pk_seq  inner join nhanvien c on a.nguoisua = c.pk_seq  inner join "+
			 " donvikinhdoanh e on a.dvkd_fk = e.pk_seq left join dondathang ddh on ddh.denghidathang_fk=a.pk_seq  where a.npp_fk="+ this.nppId + st +"  order by a.ngaydat,trangthai, chungtu ";

			//query = "select distinct a.ngaydat, a.pk_seq as chungtu,e.donvikinhdoanh, a.sotienavat, b.ten as nguoitao, c.ten as nguoisua, a.trangthai, a.dadathang,a.SOTIENAVAT,a.SOTIENBVAT,a.VAT,convert(numeric(18,0),a.SOTIENAVAT) - convert(numeric(18,0),a.dadathang) as conlai from denghidathang a, nhanvien b, nhanvien c, denghidathang_sp d, donvikinhdoanh e where a.nguoitao = b.pk_seq and a.nguoisua = c.pk_seq and a.pk_seq = d.denghidathang_fk and a.dvkd_fk = e.pk_seq and a.npp_fk='"+ this.nppId +"'"+ st +"  order by trangthai, chungtu";
		}else{
			query = "select distinct a.ngaydat, a.pk_seq as chungtu,e.donvikinhdoanh, a.sotienavat, b.ten as nguoitao, c.ten as nguoisua, "+
			" a.trangthai,ddh.pk_seq as dondathang, a.dadathang,a.SOTIENAVAT,a.SOTIENBVAT,a.VAT,convert(numeric(18,0),a.SOTIENAVAT) - convert(numeric(18,0),a.dadathang) "+
			" as conlai from denghidathang a inner join  nhanvien b on a.nguoitao = b.pk_seq  inner join nhanvien c on a.nguoisua = c.pk_seq  inner join "+
			" donvikinhdoanh e on a.dvkd_fk = e.pk_seq left join dondathang ddh on ddh.denghidathang_fk=a.pk_seq  where a.npp_fk="+this.nppId+"  order by a.ngaydat,trangthai, chungtu ";
		}
		System.out.println("chuoi in :"+ query);
		this.dndhList =  this.db.get(query);
		System.out.println("chuoi in :"+ query);
			
	}
	
	private void getNppInfo(){
		/*String query = "select a.pk_seq, a.ten from nhaphanphoi a, nhanvien b where a.ma = b.dangnhap and b.pk_seq = '" + this.userId + "'";
	
		ResultSet rs = this.db.get(query);
		try{
			if (rs != null){
				rs.next();
				this.nppId = rs.getString("pk_seq");
				this.nppTen = rs.getString("ten");
			}else{
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

	@Override
	public void DBclose(){
	try{
		if(!(this.dndhList == null)) 
			this.dndhList.close();
			
		if(!(this.db == null)){
			this.db.shutDown();
		}
	}catch(Exception e){}
	}
	
}
