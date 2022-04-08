package geso.dms.center.beans.mokhaibaotrungbay.imp;

import geso.dms.center.beans.mokhaibaotrungbay.IMokhaibaotrungbay;
import geso.dms.center.db.sql.*;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Mokhaibaotrungbay implements IMokhaibaotrungbay {
	public String userId;
	public ResultSet scheme;
	public String schemeId;
	public ResultSet npp;
	public String nppId;
	public String msg;
	public dbutils db;
	
	public Mokhaibaotrungbay(){
		this.schemeId = "";
		this.nppId = "";
		this.msg = "";
		this.db = new dbutils();
	}
	
	public String getUserId(){
		return this.userId;		
	}
	
	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getSchemeId(){
		return this.schemeId;		
	}
	
	public void setSchemeId(String schemeId){
		this.schemeId = schemeId;
	}
	
	public String getNppId(){
		return this.nppId;		
	}
	
	public void setNppId(String nppId){
		this.nppId = nppId;
	}

	public String getMsg(){
		return this.msg;		
	}
	
	public void setMsg(String msg){
		this.msg = msg;
	}

	public ResultSet getScheme(){
		return this.scheme;		
	}
	
	public void setScheme(ResultSet scheme){
		this.scheme = scheme;
	}

	public ResultSet getNpp(){
		return this.npp;		
	}
	
	public void setNpp(ResultSet npp){
		this.npp = npp;
	}

	public void init(){
		String query =  "select distinct cttb.pk_seq, cttb.scheme, cttb.diengiai " +
						"from dangkytrungbay dktb " +
						"inner join cttrungbay cttb on cttb.pk_seq = dktb.cttrungbay_fk " +
						"where cttb.ngayketthuctb >= '"+ getDateTime() + "' " +
						"order by cttb.scheme, cttb.diengiai"	 ;
			
		System.out.println(query);
		
		this.scheme = this.db.get(query);
		
		query = "select distinct npp.pk_seq, npp.ten " + 
				"from dangkytrungbay dktb " +
				"inner join nhaphanphoi npp on npp.pk_seq = dktb.npp_fk and npp.trangthai='1' " +
				"where dktb.cttrungbay_fk = '" + this.schemeId + "' order by npp.ten";
 
		System.out.println(query);
		if(this.schemeId.length()> 0)
			this.npp = this.db.get(query);
		else
			this.npp = null;
	}
	
	public void Execute(){
		if(this.nppId.length()>0 & this.schemeId.length() > 0){
			String query = "update dangkytrungbay set trangthai = '0' where npp_fk='" + this.nppId + "' and cttrungbay_fk = '" + this.schemeId + "'";
			System.out.println(query);
			if(this.db.update(query)){
				this.msg = "Thực hiện thành công";
			}else{
				this.msg = "Thực hiện không thành công";
			}
		}
	}
	public String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
}
