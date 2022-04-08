package geso.dms.center.beans.thietlapnamtaichinh.imp;

import java.sql.ResultSet;

import geso.dms.center.beans.thietlapnamtaichinh.IThietLapNamTaiChinh;
import geso.dms.center.db.sql.dbutils;

public class ThietLapNamTaiChinh implements IThietLapNamTaiChinh {
	String userId;
	String msg;
	String Ngaytl;
	String diengiai;
	dbutils db;
	ResultSet ngaytllist;
	ResultSet kylist;
	ResultSet quylist;
	String tab = "0";
	public ThietLapNamTaiChinh()
	{
		msg = "";
		Ngaytl="";
		diengiai = "";
		db = new dbutils();
		this.init();
	}
	@Override
	public String getUserId() {
		return this.userId;
	}

	@Override
	public void setUserId(String userId) {
		this.userId = userId;	
		
	}

	@Override
	public String getMsg() {
		return this.msg;
	}

	@Override
	public String setMsg(String msg) {
		return this.msg = msg;
	}

	@Override
	public void init() {
		this.createRs();
		String sql="select NgayTL,DienGiai from  TLNTC ";
		ResultSet rs=db.get(sql);
		
		
		try{
			if(rs!=null){
			if(rs.next()){
				this.Ngaytl=rs.getString("NgayTL");
				this.diengiai=rs.getString("DienGiai");
				
				
			}
			rs.close();
		}
		}catch(Exception er){
			
		}
		sql = "Select * from ky order by nam";
		this.kylist = db.get(sql);
		System.out.println("In sql "+sql);
		sql = "Select * from quy order by nam";
		this.quylist = db.get(sql);
		System.out.println("In sql "+sql);
	}

	@Override
	public void createRs() {
		
	}

	@Override
	public boolean updateThietLap() {
		
		String	sql="Update THIETLAPKHOASO set DienGiai ="+"'"+this.diengiai+"'"+",NgayTL="+""+this.Ngaytl+"'";
			if(!db.update(sql))
			{
				this.setMsg("Vui lòng kiểm tra lại .Lỗi: "+ sql );
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				return false;
			}
		return true;
	}

	@Override
	public void DBclose() {

		try{
		
			if(db!=null) db.shutDown();
		}catch (Exception e) {System.out.println("Loi :"+e.toString());}
			}

	

	@Override
	public void SetNgayThietLap(String NgayTL) {

		this.Ngaytl=NgayTL;
	}

	@Override
	public String GetNgayThietLap() {

		return this.Ngaytl;
	}
	

	@Override
	public ResultSet getRsListKs() {

		this.ngaytllist=db.get("select DienGiai,NgayTL from TLNTC order by NgayTL");
		return this.ngaytllist;
	}
	@Override
	public void SetDienGiai(String Diengiai) {
		this.diengiai = Diengiai;
		
	}
	@Override
	public String GetDienGiai() {

		return this.diengiai;
	}
	@Override
	public String getActiveTab() {
		// TODO Auto-generated method stub
		return this.tab;
	}
	@Override
	public String setActiveTab(String tab) {
		// TODO Auto-generated method stub
		return this.tab = tab;
	}
	@Override
	public ResultSet getKyList() {
		// TODO Auto-generated method stub
		return this.kylist;
	}
	@Override
	public ResultSet SetKyList(ResultSet kylist) {
		// TODO Auto-generated method stub
		return this.kylist = kylist;
	}
	@Override
	public ResultSet getQuyList() {
		// TODO Auto-generated method stub
		return this.quylist;
	}
	@Override
	public ResultSet SetQuyList(ResultSet quylist) {
		// TODO Auto-generated method stub
		return this.quylist = quylist;
	}

}
