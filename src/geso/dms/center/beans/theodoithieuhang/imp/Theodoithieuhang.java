package geso.dms.center.beans.theodoithieuhang.imp;
import java.sql.ResultSet;

import geso.dms.center.beans.theodoithieuhang.*;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;

public class Theodoithieuhang implements ITheodoithieuhang{
	private static final long serialVersionUID = -9217977546733610415L;
	
	String sku;
	ResultSet thlist;
	String tungay;
	String denngay;	
	String Msg;
	dbutils db;
	String userId;
	
	public Theodoithieuhang(String[] param)
	{
		this.sku = param[0];
		this.tungay = param[1];
		this.denngay = param[2];
		this.Msg = param[3];
		this.db = new dbutils();
	}
	
	public Theodoithieuhang()
	{		
		this.sku = "";
		this.tungay = "";
		this.denngay = "";
		this.Msg ="";
		this.userId ="";
		this.db = new dbutils();
		createThList();
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
	

	public void setThlist(ResultSet thlist)
	{
		this.thlist = thlist;
	}

	public ResultSet getThlist()
	{
		return this.thlist;
	}
	
		
	private void createThList(){		
		Utility Ult = new Utility();
		String sql = "select tdth.pk_seq as id, sp.ma as masp, sp.ten as tensp, ktt.pk_seq as khoId, ktt.ten as kho, tdth.soluongthieu, tdth.soluongdt, tdth.soluongtt, tdth.ngaydt, tdth.ngaytt, tdth.trangthai ";
			   sql = sql + " from theodoithieuhang tdth ";
			   sql = sql + " inner join sanpham sp on sp.pk_seq=tdth.sanpham_fk";
			   sql = sql + " inner join khott ktt on tdth.khott= ktt.pk_seq";
			   sql = sql +" where tdth.sanpham in "+ Ult.quyen_npp(this.userId);
			   sql = sql + " order by tdth.trangthai, ktt.pk_seq, sp.ma";
			   
	    System.out.println(sql);
		this.thlist =  this.db.get(sql);
		
	}
	
	public void DBClose(){
		try{
			if(this.thlist != null) this.thlist.close();
			this.db.shutDown();
		}catch(Exception e){}
	}

	public void setMsg(String Msg) {
		this.Msg =Msg;
		
	}

	
	public String getMsg() {
		return this.Msg;
	}
	
	public void InitEdit(String id){
		String sql = "select tdth.pk_seq as id, sp.ma as masp, sp.ten as tensp, ktt.pk_seq as khoId, ktt.ten as kho, tdth.soluongthieu,tdth.soluongdt, tdth.soluongtt, tdth.ngaydt, tdth.ngaytt ";
		   sql = sql + "from theodoithieuhang tdth ";
		   sql = sql + " inner join sanpham sp on sp.pk_seq=tdth.sanpham_fk";
		   sql = sql + " inner join khott ktt on tdth.khott= ktt.pk_seq";
		   sql = sql + " where tdth.pk_seq='" + id + "'";
 
		System.out.println(sql);
		this.thlist = this.db.get(sql);
	}
	
	public void InitSearch(){
		
		String sql = "select sp.pk_seq as id, sp.ma as masp, sp.ten as tensp, ktt.pk_seq as khoId, ktt.ten as kho, tdth.soluongthieu,tdth.soluongdt, tdth.soluongtt, tdth.ngaydt, tdth.ngaytt, tdth.trangthai  ";
		   sql = sql + "from theodoithieuhang tdth ";
		   sql = sql + " inner join sanpham sp on sp.pk_seq=tdth.sanpham_fk";
		   sql = sql + " inner join khott ktt on tdth.khott= ktt.pk_seq";
		   sql = sql + " where '1'='1'";

		if(this.sku.length() > 0){
			sql = sql + " and (sp.ma like '%" + this.sku + "%' or sp.ten like '%" + this.sku + "%')"; 
		}
		
		if(this.tungay.length() > 0){
			sql = sql + " and tdth.ngaydt >= '" + this.tungay + "'"; 
		}
		
		if(this.denngay.length() > 0){
			sql = sql + " and tdth.ngaydt <= '" + this.denngay + "'"; 
		}

		System.out.println(sql);
		this.thlist = this.db.get(sql);
	}

	
	public void setuserId(String userId) {
	
		this.userId = userId;
	}

		public String getuserId() {
		
		return this.userId;
	}
	
}



