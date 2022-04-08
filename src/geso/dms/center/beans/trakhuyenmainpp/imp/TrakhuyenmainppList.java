package geso.dms.center.beans.trakhuyenmainpp.imp;

import java.sql.ResultSet;
import java.util.List;

import geso.dms.center.beans.trakhuyenmai.ITrakhuyenmai;
import geso.dms.center.beans.trakhuyenmainpp.ITrakhuyenmainppList;
import geso.dms.distributor.db.sql.dbutils;

public class TrakhuyenmainppList implements ITrakhuyenmainppList{

	String userId; 
	String diengiai;
	String tungay;
	String denngay;
	ResultSet dsduyet;
	dbutils db;
	public TrakhuyenmainppList()
	{
		this.userId = "";  
		this.diengiai ="";
		this.tungay ="";
		this.denngay="";
		db = new dbutils();

	}
	public String getUserId() {
		
		return this.userId;
	}
	public void setUserId(String userId) {
	 this.userId = userId;	
	}

	public String getDiengiai() {
		
		return this.diengiai;
	}
	public void setDiengiai(String diengiai) {
	
		this.diengiai = diengiai;
	}

	public String getTungay() {
		return this.tungay;
	}

	public void setTungay(String tungay) {
           this.tungay = tungay;		
	}

	public String getDenngay() {
		return this.denngay;
	}

	public void setDenngay(String denngay) {
      this.denngay = denngay;		
	}

	public List<ITrakhuyenmai> getTrakmList() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setTrakmList(List<ITrakhuyenmai> trakmlist) {
		// TODO Auto-generated method stub
		
	}
	public void init(String search) {
		String st="";
		String sql="";
		int dem = 0 ;
		geso.dms.distributor.util.Utility util = new geso.dms.distributor.util.Utility();

		if(this.diengiai.length()>0)
		{
			if(dem == 0)
			st =" where upper(dbo.ftBoDau(diengiai)) like '%"+ util.replaceAEIOU(this.diengiai) +"%'";
			 dem = 1;
		}
		if(this.tungay.length()>0)
		{   if(dem == 0)
			st  = st + " where ngaytao >='"+ this.tungay+"'";
		else
			st  = st + " and ngaytao >='"+ this.tungay+"'";
		    dem =1;
		}
		if(this.denngay.length()>0)
		{ if(dem ==0)
			st  = st + " where ngaytao >='"+ this.denngay +"'";
		else
			st  = st + " and ngaytao >='"+ this.denngay +"'";
		}
		if(st.length()>0)
			sql ="select * from DUYETTRAKM " + st;
		else
		   sql ="select * from DUYETTRAKM";
		System.out.println(sql);
		this.dsduyet = db.get(sql);
	}

	public void DBclose() {
		// TODO Auto-generated method stub
		
	}
	
	public void setDanhsachduyet(ResultSet dsduyet) {
		
		
		this.dsduyet = dsduyet;
	}
	public ResultSet getDanhsachduyet() {
		return this.dsduyet;
	}

}
