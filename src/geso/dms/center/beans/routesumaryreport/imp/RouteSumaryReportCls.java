package geso.dms.center.beans.routesumaryreport.imp;

import java.sql.ResultSet;
import java.util.Hashtable;

import geso.dms.center.beans.routesumaryreport.IRouteSumaryReport;
import geso.dms.center.db.sql.dbutils;

public class RouteSumaryReportCls implements IRouteSumaryReport {
	String userId;
	String userTen;
	String nppId;
	String nppTen;
	String erea;
	String status;
	Hashtable<String, String> HashStatus;
	ResultSet rsArea;
	ResultSet rsDistributor;
	
	
	dbutils db;

	public RouteSumaryReportCls() {		
		this.setuserId("");
		this.setuserTen("");
		this.setnppId("");
		this.setNppTen("");
		this.setStatus("");
		this.setKhuVuc("");
		this.setArea(null);
		this.setDistributor(null);
		db = new dbutils();
	}

	public void setuserId(String userId) {
		this.userId = userId;

	}

	public String getuserId() {

		return this.userId;
	}

	public void setuserTen(String userTen) {
		this.userTen = userTen;

	}

	public String getuserTen() {
		return this.userTen;
	}

	public void setnppId(String nppId) {
		this.nppId = nppId;
		if(this.nppId.length()>0){
			String sql="select ten from nhaphanphoi where pk_seq="+this.nppId;
			ResultSet rs=this.db.get(sql);
			try{
			if(rs!=null){
				if(rs.next()){
				this.nppTen=rs.getString("ten");	
				}
				rs.close();
			}
			}catch(Exception er){
				
			}
		}
	}

	public String getnppId() {
		return this.nppId;
	}

	public void setNppTen(String nppTen) {
		this.nppTen = nppTen;
	}

	public String getNppTen() {
		return this.nppTen;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {

		return this.status;
	}

	public void setArea(ResultSet erea) {
		this.rsArea = erea;
	}

	public ResultSet getArea() {

		return this.rsArea;
	}

	public void setDistributor(ResultSet distributor) {
		this.rsDistributor = distributor;
	}

	public ResultSet getDistributor() {

		return this.rsDistributor;
	}

	public void init() {
		this.setArea(db.get("select * from khuvuc"));
		String query = "select * from nhaphanphoi where 1=1 and priandsecond=0 ";	
		
		if(this.status!=null){
			query +=" and TRANGTHAI='" + this.status +"'";
		}
		if(this.erea!=null){
			query += " and KHUVUC_FK='" + this.getKhuVuc()+ "'";			
		}
		this.setDistributor(db.get(query));
	}

	public void setKhuVuc(String erea) {
		this.erea = erea;
	}

	public String getKhuVuc() {
		return this.erea;
	}

	
	public void setHashStatus() {
		
		
	}

	
	public Hashtable<String, String> getHashStatus() {
		HashStatus=null;
		HashStatus = new Hashtable<String, String>();
		HashStatus.put("1", "Hoạt động");
		HashStatus.put("0", "Không hoạt động");
		return HashStatus;
	}

	@Override
	public void DBClose() {
		// TODO Auto-generated method stub
		try{
			
			if(rsArea!=null){
				rsArea.close();
			}
			if(rsDistributor!=null){
				rsDistributor.close();
			}
			if(db!=null){
				db.shutDown();
			}

		}catch(Exception er){
			
		}
	}

	
}
