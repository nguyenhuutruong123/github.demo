package geso.dms.center.beans.chuyennhanvienbanhang.imp;

import java.sql.ResultSet;

import geso.dms.center.beans.chuyennhanvienbanhang.IChuyennhanvienbanhang;
import geso.dms.center.db.sql.dbutils;

public class Chuyennhanvienbanhang implements IChuyennhanvienbanhang{

	
	private String IdNppCu;
	private String IdNppMoi;
	private String IdnvbhChon;
	private String IsCopy;
	private ResultSet rsddkd;
	private ResultSet rsnppcu;
	private ResultSet rsnppmoi;
	private String Userid;
	private dbutils db;
	private String Msg;
	public Chuyennhanvienbanhang(){
		db=new dbutils();
		IdNppCu="";
		IdNppMoi="";
		IdnvbhChon="";
		IsCopy="";
		this.Msg="";
		this.Userid="";
	}
	@Override
	public void setIdNppCu(String _IdNppCu) {
		// TODO Auto-generated method stub
		this.IdNppCu=_IdNppCu;
	}

	@Override
	public String getIdNppCu() {
		// TODO Auto-generated method stub
		return this.IdNppCu;
	}

	@Override
	public void setIdNppMoi(String _IdNppoi) {
		// TODO Auto-generated method stub
		this.IdNppMoi=_IdNppoi;
		
	}

	@Override
	public String getIdNppMoi() {
		// TODO Auto-generated method stub
		return this.IdNppMoi;
	}

	@Override
	public void setIdNvbhChon(String idchon) {
		// TODO Auto-generated method stub
		this.IdnvbhChon=idchon;
	}

	@Override
	public String getIdNvbhChon() {
		// TODO Auto-generated method stub
		return this.IdnvbhChon;
	}

	@Override
	public void setIscopy(String Iscopy) {
		// TODO Auto-generated method stub
		this.IsCopy=Iscopy;
	}

	@Override
	public String getIscopy() {
		// TODO Auto-generated method stub
		return this.IsCopy;
	}

	@Override
	public ResultSet getRsDdkd() {
		// TODO Auto-generated method stub
		return this.rsddkd;
	}

	@Override
	public void setRsDdkd(ResultSet rs) {
		// TODO Auto-generated method stub
		this.rsddkd=rs;
	}

	@Override
	public ResultSet getRsNppCu() {
		// TODO Auto-generated method stub
		return this.rsnppcu;
	}

	@Override
	public void setRsNppCu(ResultSet rs) {
		// TODO Auto-generated method stub
		this.rsnppcu=rs;
	}

	@Override
	public ResultSet getRsNppMoi() {
		// TODO Auto-generated method stub
		return this.rsnppmoi;
	}

	@Override
	public void setRsNppMoi(ResultSet rs) {
		// TODO Auto-generated method stub
		this.rsnppmoi=rs;
	}
	@Override
	public void Init() {
		// TODO Auto-generated method stub
		String sql="select pk_seq,ma,ten from nhaphanphoi ";
		
		this.rsnppcu=db.get(sql);
		 sql="select pk_seq,ma,ten from nhaphanphoi where 1 = 1 ";
		 if(this.IdNppCu.length() > 0)
			 sql += " and pk_seq  <> "+this.IdNppCu;
		this.rsnppmoi=db.get(sql);
		
		if(this.IdNppCu.length() >0){
			sql="select pk_seq,ten from daidienkinhdoanh where npp_fk="+this.IdNppCu;
			this.rsddkd=db.get(sql);
			
		}
	}
	@Override
	public void DbClose() {
		// TODO Auto-generated method stub
		try{
			rsnppcu.close();
			rsnppmoi.close();
			rsddkd.close();
			
			db.shutDown();
		}catch(Exception er){
			
		}
	}
	@Override
	public void setUserId(String UserId) {
		// TODO Auto-generated method stub
		this.Userid=UserId;
	}
	@Override
	public String getUserId() {
		// TODO Auto-generated method stub
		return this.Userid;
	}
	@Override
	public void setMsg(String msg) {
		// TODO Auto-generated method stub
		this.Msg=msg;
	}
	@Override
	public String getMsg() {
		// TODO Auto-generated method stub
		return this.Msg;
	}
	@Override
	public boolean ThucHienChuyen() {
		// TODO Auto-generated method stub
		try{
			if(this.IdnvbhChon.equals("")){
				this.Msg="Vui lòng chọn đại diện kinh doanh cần chuyển ";
				return false;
			}
			db.getConnection().setAutoCommit(false);
			String sql="";
			if(this.IsCopy.equals("0")){
				
				//Chuyển khách hàng
				sql="update  khachhang set npp_fk="+this.IdNppMoi+"  where pk_seq in ( select khachhang_fk "+
				" from khachhang_tuyenbh where tbh_fk in (select pk_seq from tuyenbanhang where "+
				" ddkd_fk in ("+this.IdnvbhChon+") and npp_fk = '"+this.IdNppCu+"')) ";
				if(!db.update(sql)){
					geso.dms.center.util.Utility.rollback_throw_exception(db);
					this.Msg=sql;
					return false;
				}
				// Chuyển tuyến
				
				sql="update tuyenbanhang set npp_fk="+this.IdNppMoi+" where ddkd_fk in ("+this.IdnvbhChon+") and npp_fk = '"+this.IdNppCu+"' ";
				if(!db.update(sql)){
					geso.dms.center.util.Utility.rollback_throw_exception(db);
					this.Msg=sql;
					return false;
				}
				
				//Chuyển Đại diện kinh doanh
				sql="update daidienkinhdoanh set npp_fk="+this.IdNppMoi+" where pk_seq in ("+this.IdnvbhChon+")  ";
				if(!db.update(sql)){
					geso.dms.center.util.Utility.rollback_throw_exception(db);
					this.Msg=sql;
					return false;
				}
				
				
				
			}else{
				
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			
			
		}catch(Exception er){
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			this.Msg=er.toString();
			System.out.println(er.toString());
			return false;
		}
		return true;
	}
	

	
}
