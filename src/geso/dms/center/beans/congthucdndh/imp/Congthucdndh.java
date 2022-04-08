package geso.dms.center.beans.congthucdndh.imp;

import java.sql.ResultSet;

import geso.dms.center.beans.congthucdndh.ICongthucdndh;
import geso.dms.center.db.sql.dbutils;

public class Congthucdndh implements ICongthucdndh {

	String Userid;
	String NgayTonKhoToiThieu;
	String PhanTramTangTruong;
	dbutils db;
	String Msg;
	String  thue;
	public  Congthucdndh (){
			db=new dbutils();
		
		 	 NgayTonKhoToiThieu="0";
			 PhanTramTangTruong="0";
			 Msg="";
	}
	@Override
	public String getUserId() {
		// TODO Auto-generated method stub
		return this.Userid;
	}

	@Override
	public void setUserId(String userId) {
		// TODO Auto-generated method stub
		this.Userid=userId;
	}

	@Override
	public String getNgayTonKhoToithieu() {
		// TODO Auto-generated method stub
		return this.NgayTonKhoToiThieu;
	}

	@Override
	public void setNgayTonKhoToithieu(String ngaytktt) {
		// TODO Auto-generated method stub
		this.NgayTonKhoToiThieu=ngaytktt;
	}

	@Override
	public String getMucTangTruong() {
		// TODO Auto-generated method stub
		return this.PhanTramTangTruong;
	}

	@Override
	public void setMucTangTruong(String tangtruong) {
		// TODO Auto-generated method stub
		this.PhanTramTangTruong=tangtruong;
	}
	@Override
	public void init_Update() {
		// TODO Auto-generated method stub
		String sql="select * from CONGTHUCDNDH";
		ResultSet rs=db.get(sql);
		try{
		if(rs!=null){
			if(rs.next()){
				this.NgayTonKhoToiThieu=rs.getString("NGAYTONKHO");
				this.PhanTramTangTruong=rs.getString("MUCTANGTRUONG");
				this.thue=rs.getString("thue");
				
			}
		}
		
			}catch (Exception er){
				
				System.out.println("Error :"+er.toString());
			}
}
	@Override
	public boolean Save() {
		// TODO Auto-generated method stub
		try{
			
			String	sql="update congthucdndh set thue='"+this.thue+"' ,NGAYTONKHO ='"+NgayTonKhoToiThieu+"',MUCTANGTRUONG='"+this.PhanTramTangTruong+"' ";
			if(!db.update(sql)){
				this.Msg="Khong the cap nhat Cong thuc.Loi :" +sql;
				return false;
				
			}else{
				this.Msg="Da Cap Nhat Thanh Cong";
			}
			
		}catch(Exception err){
			this.Msg="Cap Nhat Khong Thanh Cong ." +err.toString();
			return false;
		}
		return true;
	}
	@Override
	public String getMsg() {
		// TODO Auto-generated method stub
		return this.Msg;
	}
	@Override
	public void setMsg(String msg) {
		// TODO Auto-generated method stub
		this.Msg=msg;
	}
	@Override
	public String getmucthue() {
		// TODO Auto-generated method stub
		return this.thue;
	}
	@Override
	public void setMucThue(String _thue) {
		// TODO Auto-generated method stub
		this.thue=_thue;
	}
	

}
