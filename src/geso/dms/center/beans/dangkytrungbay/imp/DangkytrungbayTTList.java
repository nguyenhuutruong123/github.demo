package geso.dms.center.beans.dangkytrungbay.imp;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import geso.dms.center.beans.dangkytrungbay.IDangkytrungbayTTList;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;

public class DangkytrungbayTTList implements IDangkytrungbayTTList, Serializable{
	private static final long serialVersionUID = 1L;
		String userId;
	    String nppId;
	    String nppTen;
	    String tungay;
	    String denngay;
	    String diengiai;
	    String ten;
		ResultSet dktbbRs;
		dbutils db;
	public DangkytrungbayTTList()
	{
	     this.userId="";
	     this.nppId="";
	     this.nppTen="";
	     this.tungay="";
	     this.denngay="";
	     this.diengiai="";
	     this.ten="";
	     db = new dbutils();
	    
	}
	public String getUserId() {
		
		return this.userId;
	}

	
	public void setUserId(String userId) {
		
		this.userId = userId;
	}

	
	public String getNppId() {
		
		return this.nppId;
	}

	
    public void setNppId(String nppId) {
		
	   this.nppId = nppId;	
	}

	
	public void setTungay(String tungay) {
		
		this.tungay = tungay;
	}

	
	public String getTungay() {
		
		return this.tungay;
	}

	
	public void setDenngay(String denngay) {
		
		this.denngay = denngay;
	}

	
	public String getDenngay() {
		
		return this.denngay;
	}

	
	public void setTen(String ten) {
		
		this.ten = ten; 
	}

	
	public String getTen() {
		
		return this.ten;
	}

	public void setDsTichluy(ResultSet dktbbRs) {
		
		this.dktbbRs = dktbbRs;
	}

	public void setNppTen(String nppTen) {
		
		this.nppTen = nppTen;		
	}
	
	public String getNppTen() {
		
		return this.nppTen;
	}
	
	public ResultSet getDKTBRs() {
		return this.dktbbRs;
	}

	public void init() 
	{
		Utility uti = new Utility();
		String sql ="\n select isnull(dk.stt,0) stt,dk.pk_seq, tb.scheme, tb.NGAYTRUNGBAY, tb.NGAYKETTHUCTB, dk.trangthai" +
					"\n , case when  convert(varchar(10),dbo.getlocaldate(default),120) between  tb.NgayDangKy and tb.NgayKetThucDK then 1 else 0 end allowEdit " +
		 			"\nfrom DANGKYTRUNGBAY dk inner join CTTRUNGBAY tb on dk.CTTRUNGBAY_FK = tb.pk_seq ";
		
		if(this.tungay.length()>0){
			sql=sql + " and tb.NGAYTRUNGBAY >= '"+this.tungay+"'";
			 
		}
		if(this.denngay.length()>0){
			sql=sql + " and tb.NGAYKETTHUCTB <='"+this.denngay+"' ";
		}
		if(this.diengiai.length()>0){
			sql=sql + " tb.scheme like '%"+this.diengiai+"'%";
		}
		sql += " order by dk.pk_seq desc ";
		ResultSet rs  =db.get(sql);
		System.out.println("Tb :" + sql);
		this.dktbbRs = rs; 
		
	}
	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	public void setDiengiai(String diengiai) {
		this.diengiai = diengiai;
		
	}
	public String getDiengiai() {

		return this.diengiai;
	}
	@Override
	public void DBclose() {
		// TODO Auto-generated method stub
		
		try {
			if(dktbbRs != null)
				dktbbRs.close();
			if(this.db != null)
				this.db.shutDown();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
}
