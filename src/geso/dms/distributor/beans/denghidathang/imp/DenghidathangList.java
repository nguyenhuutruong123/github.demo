package geso.dms.distributor.beans.denghidathang.imp;
import java.io.Serializable;
import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;

import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.*;
import geso.dms.distributor.beans.denghidathang.IDenghidathangList;

public class DenghidathangList extends Phan_Trang implements IDenghidathangList, Serializable
{
	private static final long serialVersionUID = -9217977546733610214L;
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
	public DenghidathangList(String[] param)
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
	
	public DenghidathangList()
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
		}catch(java.sql.SQLException e){}
		
		
		return this.maspstr;
	}
	
	public void setMaspstr(String maspstr) 
	{
		this.maspstr = maspstr;
	}
	
	public String getMessage(){
		return this.msg;
	}
	public void createDndhlist(HttpServletRequest request, String type){
		
		getNppInfo();
		String query; 
		if (type.equals("2")){
			query = getSearchQuery(request);
			this.msg = query;
		}else{
			query = "select distinct a.ngaydat, a.pk_seq as chungtu,e.donvikinhdoanh, a.sotienavat, b.ten as nguoitao, c.ten as nguoisua, a.trangthai, a.dadathang from denghidathang a, nhanvien b, nhanvien c, denghidathang_sp d, donvikinhdoanh e where a.nguoitao = b.pk_seq and a.nguoisua = c.pk_seq and a.pk_seq = d.denghidathang_fk and a.dvkd_fk = e.pk_seq and a.npp_fk='"+ this.nppId +"'";
		}
		this.dndhList =  createSplittingData(10, 15, "chungtu desc", query);//this.db.get(query);
			
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
		}catch(java.sql.SQLException e){}
		*/
		geso.dms.distributor.util.Utility util=new geso.dms.distributor.util.Utility();
		this.nppId = util.getIdNhapp(this.userId);
		this.nppTen = util.getTenNhaPP();
		//this.dangnhap = util.getDangNhap();
		//this.sitecode=util.getSitecode();
	
	}

	private String getSearchQuery(HttpServletRequest request){
//	    PrintWriter out = response.getWriter();
		Utility util = new Utility();
		getNppInfo();
		
		String sku = util.ValidateParam(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("sku")));
    	if (sku == null)
    		sku = "";
    	this.sku = sku.split("-")[0].trim();
    	
    	String tungay = util.ValidateParam(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay")));
    	if (tungay == null)
    		tungay = "";    	
    	this.tungay = tungay;

    	String denngay = util.ValidateParam(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay")));
    	if (denngay == null)
    		denngay = "";    	
    	this.denngay = denngay;
    	   	
    	String trangthai = util.ValidateParam(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai")));   	
    	if (trangthai == null)
    		trangthai = "";    	
	
    	if (trangthai.equals("0"))
    		trangthai = "";
    	
    	this.trangthai = trangthai;
    	
	    String query = "select distinct a.ngaydat, a.pk_seq as chungtu,e.donvikinhdoanh, a.sotienavat, b.ten as nguoitao, c.ten as nguoisua, a.trangthai, a.dadathang from denghidathang a, nhanvien b, nhanvien c, denghidathang_sp d, donvikinhdoanh e where a.nguoitao = b.pk_seq and a.nguoisua = c.pk_seq and a.pk_seq = d.denghidathang_fk and a.dvkd_fk = e.pk_seq and a.npp_fk='"+ this.nppId +"'  ";
    	
    	
    	if (this.tungay.length()>0){
			query = query + " and a.ngaydat >= '" + tungay+ "'";
    		
    	}

    	if (this.denngay.length()>0){
			query = query + " and a.ngaydat <= '" + denngay+ "'";
    		
    	}

    	if(this.trangthai.length() > 0){
    		query = query + " and a.trangthai = '" + trangthai + "'";
    		
    	}

    	if (this.sku.length()>0){
			query = query + " and  exists ( select e.sanpham_fk from denghidathang_sp e, sanpham f where e.sanpham_fk=f.pk_seq and f.ma = '" + this.sku + "' and e.denghidat >0)" ;
			
    	}
    	
    	//query = query + " order by trangthai, chungtu";

    	return query;
	}	

	public void DBclose(){
	try{
		if(!(this.dndhList == null)) 
			this.dndhList.close();
			
		if(!(this.db == null)){
			this.db.shutDown();
		}
	}catch(java.sql.SQLException e){}
	}
	
}
