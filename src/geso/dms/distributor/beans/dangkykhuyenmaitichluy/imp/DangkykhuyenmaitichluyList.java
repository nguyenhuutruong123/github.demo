package geso.dms.distributor.beans.dangkykhuyenmaitichluy.imp;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import geso.dms.center.beans.dangkykhuyenmaitichluy.IDangkykhuyenmaitichluyTTList;
import geso.dms.center.util.Utility;
import geso.dms.distributor.beans.dangkykhuyenmaitichluy.IDangkykhuyenmaitichluyList;
import geso.dms.distributor.db.sql.dbutils;

public class DangkykhuyenmaitichluyList implements IDangkykhuyenmaitichluyList, Serializable{
	private static final long serialVersionUID = 1L;
		String userId;
	    String nppId;
	    String nppTen;
	    String tungay;
	    String denngay;
	    String diengiai;
	    String trangthai;
	    String ten;
		ResultSet dstichluy;
		dbutils db;
	public DangkykhuyenmaitichluyList()
	{
	     this.userId="";
	     this.nppId="";
	     this.nppTen="";
	     this.tungay="";
	     this.denngay="";
	     this.diengiai="";
	     this.trangthai = "";
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

	public void setDsTichluy(ResultSet dstichluy) {
		
		this.dstichluy = dstichluy;
	}

	public void setNppTen(String nppTen) {
		
		this.nppTen = nppTen;		
	}
	
	public String getNppTen() {
		
		return this.nppTen;
	}
	
	public ResultSet getDsTichluy() {
		return this.dstichluy;
	}

	private void getNppInfo()
	{	
		geso.dms.distributor.util.Utility util = new geso.dms.distributor.util.Utility();
		this.nppId = util.getIdNhapp(this.userId);
	}
	public void init() 
	{
		Utility uti = new Utility();
		this.getNppInfo();
		String sql ="select dkkm.pk_seq, ctkm.scheme, ctkm.diengiai, ctkm.thang as tungay, ctkm.nam as denngay, dkkm.trangthai " +
		 			"from DANGKYKM_TICHLUY_NPP dkkm inner join TIEUCHITHUONGTL ctkm on dkkm.tieuchitl_fk = ctkm.pk_seq " +
		 			"where dkkm.npp_fk = '"+ nppId +"'";
		
		if(this.tungay.length()>0){
			sql=sql + "and ctkm.thang>='"+this.tungay+"'";
			 
		}
		if(this.denngay.length()>0){
			sql=sql + "and ctkm.nam <='"+this.denngay+"'";
		}
		if(this.diengiai.length()>0){
			sql=sql + "and ctkm.diengiai like N'%"+this.diengiai+"%' ";
		}
		if(this.trangthai.length()>0){
			sql=sql + "and dkkm.trangthai = '"+this.trangthai +"' ";
		}
		
		ResultSet rs  =db.get(sql);
		System.out.println("Get Du Lieu :"+sql);
		this.dstichluy = rs; 
		
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
			if(dstichluy != null)
				dstichluy.close();
			if(this.db != null)
				this.db.shutDown();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}
	
	public String getTrangthai() 
	{
		return this.trangthai;
	}
}
