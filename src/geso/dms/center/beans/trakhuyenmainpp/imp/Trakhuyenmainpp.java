package geso.dms.center.beans.trakhuyenmainpp.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import geso.dms.center.beans.trakhuyenmainpp.ITrakhuyenmainpp;
import geso.dms.distributor.db.sql.dbutils;

public class Trakhuyenmainpp implements ITrakhuyenmainpp{

	
	public String getUserId() {
		
		return null;
	}
  String id;
  String userId;
  String nppId;
  String ctkmId;
  String diengiai;
  String tongtien;
  ResultSet ctkmIds;
  ResultSet nppIds;
  ResultSet sanpham;
  String type;
  String chon[];
  String[] thanhtoan;
  dbutils db;
  String msg;
  String[] mact;
	public Trakhuyenmainpp()
	{
		  this.userId ="";
		  this.nppId ="";
		  this.ctkmId ="0";
		  this.diengiai ="";
		  this.type ="";
		  this.tongtien ="0";
		  this.id ="";
		  this.msg ="";
		  this.type ="";
		 
		  db = new dbutils();
		 		
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

	
	public String getId() {
		
		return this.id;
	}

	
	public void setId(String id) {
		
		this.id =id;
	}

	
	public void setnppId(String nppId) {
		
		this.nppId = nppId;
	}

	
	public String getnppId() {
		
		return this.nppId;
	}

	
	public void setnppIds(ResultSet nppIds) {
		
	   this.nppIds = nppIds;	
	}

	
	public ResultSet getnppIds() {
		
		return this.nppIds;
	}

	
	public void setctkmId(String ctkmId) {
		
		this.ctkmId = ctkmId;
	}

	
	public String getctkmId() {
		
		return ctkmId;
	}

	
	public void setctkmIds(ResultSet ctkmIds) {
		
		this.ctkmIds = ctkmIds;
	}
	public void init() {
		thuchien();
		 String sql ="select * from nhaphanphoi";
		 this.nppIds = db.get(sql);
		 if(this.nppId.length()>0)
		    sql ="select * from ctkhuyenmai where pk_seq in (select ctkm_fk from ctkm_npp where npp_fk ='"+ this.nppId +"')";
		 else
			 sql ="select * from ctkhuyenmai";
		 
			 this.ctkmIds = db.get(sql);
			 
			 sql ="select * from trakhuyenmai where pk_seq in (select trakhuyenmai_fk from ctkm_trakm where ctkhuyenmai_fk ='"+ this.ctkmId +"')";
			 System.out.println(sql);
			 ResultSet rs;
			 rs = db.get(sql);
			 try {
				rs.next();
				this.type = rs.getString("loai");
				rs.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
			// sql ="select ctkmid,trakmid,a.spma,b.ten,sum(soluong) as soluong from DONHANG_CTKM_TRAKM a, sanpham b  where a.ctkmid = '"+ this.ctkmId +"' and a.spma = b.ma and a.donhangid in (select pk_seq from donhang where npp_'"+ this.nppId +"') group by ctkmid,trakmid,a.spma,b.ten";
			if(this.id.length()>0)
			{

				sql ="select c.pk_seq,c.scheme,c.diengiai,b.ngansach,b.dasudung,a.thanhtoan from duyettrakm_ctkm a,phanbokhuyenmai b,ctkhuyenmai c where a.ctkm_fk = b.ctkm_fk and b.ctkm_fk= c.pk_seq and b.npp_fk ='"+ this.nppId +"' and a.duyettrakm_fk ='"+ this.id +"'";

				 sql = sql + "union select b.pk_seq, b.scheme, b.diengiai,a.ngansach,a.dasudung,'' as thanhtoan from phanbokhuyenmai a,ctkhuyenmai b where b.denngay < '"+ getDateTime() +"'  and a.ctkm_fk = b.pk_seq and a.npp_fk ='"+ this.nppId +"' and a.ctkm_fk not in (select ctkm_fk from duyettrakm_ctkm e,duyettrakm g where e.duyettrakm_fk =g.pk_seq and g.npp_fk ='"+ this.nppId +"') and b.pk_seq not in (select c.pk_seq from duyettrakm_ctkm a,phanbokhuyenmai b,ctkhuyenmai c where a.ctkm_fk = b.ctkm_fk and b.ctkm_fk= c.pk_seq and b.npp_fk ='"+ this.nppId +"' and a.duyettrakm_fk ='"+ this.id +"')";

			//	sql ="select c.pk_seq,c.scheme,c.diengiai,b.ngansach,b.dasudung,a.thanhtoan from duyettrakm_ctkm a,phanbokhuyenmai b,ctkhuyenmai c where a.ctkm_fk = b.ctkm_fk and b.ctkm_fk= c.pk_seq and b.npp_fk ='"+ this.nppId +"' and a.duyettrakm_fk ='"+ this.id +"'";
				// sql = sql + "union select b.pk_seq, b.scheme, b.diengiai,a.ngansach,a.dasudung,'' as thanhtoan from phanbokhuyenmai a,ctkhuyenmai b where b.denngay < '"+ getDateTime() +"'  and a.ctkm_fk = b.pk_seq and a.npp_fk ='"+ this.nppId +"' and b.pk_seq not in (select c.pk_seq from duyettrakm_ctkm a,phanbokhuyenmai b,ctkhuyenmai c where a.ctkm_fk = b.ctkm_fk and b.ctkm_fk= c.pk_seq and b.npp_fk ='"+ this.nppId +"' and a.duyettrakm_fk ='"+ this.id +"')";


				// sql = sql + "union select b.pk_seq, b.scheme, b.diengiai,a.ngansach,a.dasudung,'' as thanhtoan from phanbokhuyenmai a,ctkhuyenmai b where b.denngay < '"+ getDateTime() +"'  and a.ctkm_fk = b.pk_seq and a.npp_fk ='"+ this.nppId +"' and a.ctkm_fk not in (select ctkm_fk from duyettrakm_ctkm e,duyettrakm g where e.duyettrakm_fk =g.pk_seq and g.npp_fk ='"+ this.nppId +"') and b.pk_seq not in (select c.pk_seq from duyettrakm_ctkm a,phanbokhuyenmai b,ctkhuyenmai c where a.ctkm_fk = b.ctkm_fk and b.ctkm_fk= c.pk_seq and b.npp_fk ='"+ this.nppId +"' and a.duyettrakm_fk ='"+ this.id +"')";

					
			}
			else			
			 sql ="select b.pk_seq,b.scheme, b.diengiai,a.ngansach,a.dasudung, '' as thanhtoan from phanbokhuyenmai a,ctkhuyenmai b where b.denngay < '"+ getDateTime() +"'  and a.ctkm_fk = b.pk_seq and a.npp_fk ='"+ this.nppId +"' and a.ctkm_fk not in (select ctkm_fk from duyettrakm_ctkm e,duyettrakm g where e.duyettrakm_fk =g.pk_seq and g.npp_fk ='"+ this.nppId +"')";

			// sql ="select b.pk_seq,b.scheme, b.diengiai,a.ngansach,a.dasudung, '' as thanhtoan from phanbokhuyenmai a,ctkhuyenmai b where b.denngay < '"+ getDateTime() +"'  and a.ctkm_fk = b.pk_seq and a.npp_fk ='"+ this.nppId +"'";

		//	 sql ="select b.pk_seq,b.scheme, b.diengiai,a.ngansach,a.dasudung, '' as thanhtoan from phanbokhuyenmai a,ctkhuyenmai b where b.denngay < '"+ getDateTime() +"'  and a.ctkm_fk = b.pk_seq and a.npp_fk ='"+ this.nppId +"' and a.ctkm_fk not in (select ctkm_fk from duyettrakm_ctkm e,duyettrakm g where e.duyettrakm_fk =g.pk_seq and g.npp_fk ='"+ this.nppId +"')";

			System.out.println(sql);
			this.sanpham = db.get(sql);
	//	 }
		
	}

	public void thuchien()
	{
		if(this.id.length()>0)
		{
			String sql ="select * from duyettrakm where pk_seq ='"+ this.id +"'";
			ResultSet rs = db.get(sql);
			try {
				while(rs.next())
				{
					this.diengiai = rs.getString("diengiai");
					this.nppId = rs.getString("npp_fk");
					this.tongtien = rs.getString("tongtien");
				}
				if(rs!=null){
					rs.close();
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	public ResultSet getctkmIds() {
		return this.ctkmIds;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getType() {
		
		return this.type;
	}
	
	public void setSanpham(ResultSet Sanpham) {
		this.sanpham = Sanpham;		
	}
	
	public ResultSet getSanpham() {
		
		return this.sanpham;
	}
	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	public void setTongtien(String tongtien) {
		
	 this.tongtien = tongtien;	
    }
   public String getTongtien() {
		return this.tongtien;
	}

public boolean save() {
	String sql="";
	String ma="";  
	if(this.id.length() <= 0)
	{
		sql = "insert into DUYETTRAKM values('"+ this.nppId +"','"+getDateTime()+"','"+ this.userId +"','"+ getDateTime() +"','"+ this.userId +"','"+ this.diengiai +"','0')";
		System.out.println(sql);
		if(!db.update(sql))
		{this.msg = sql;
		  geso.dms.center.util.Utility.rollback_throw_exception(this.db);
		  return false;
		}
			String query = "select IDENT_CURRENT('DUYETTRAKM') as pk_seq";
			ResultSet rs = this.db.get(query);
			try {
				rs.next();
				ma = rs.getString("pk_seq");
				rs.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
	}
	else
	{
		sql = "update DUYETTRAKM set nguoisua = '"+ this.userId +"',ngaysua = '"+ getDateTime() +"',diengiai = '"+ this.diengiai +"',tongtien ='"+ this.tongtien +"' where pk_seq ='"+ this.id +"'";
		System.out.println(sql);
		if(!db.update(sql))
		{this.msg = sql;
		  geso.dms.center.util.Utility.rollback_throw_exception(this.db);
		  return false;
		}
		sql ="delete from DUYETTRAKM_CTKM where duyettrakm_fk ='"+ this.id +"'";
		if(!db.update(sql))
		{this.msg = sql;
		  geso.dms.center.util.Utility.rollback_throw_exception(this.db);
		  return false;
		}
		ma = this.id;
	}
	for(int i = 0;i< thanhtoan.length;i++)
	{  if(thanhtoan[i].length()>0)
    	{
		sql ="insert into DUYETTRAKM_CTKM values('"+ ma +"','"+ mact[i]+"','"+ thanhtoan[i]+"')";
		System.out.println(sql);
		
		if(!db.update(sql))
		{
		  geso.dms.center.util.Utility.rollback_throw_exception(this.db);
		  this.msg = sql;
		  return false;
		}
    	}
	}
	return true;
}

public boolean update() {
	
	return false;
}
public void setChon(String[] chon) {
	
	this.chon = chon;
}
public String[] getChon() {
	
	return this.chon;
}

public void setMsg(String msg) {
	
	this.msg = msg;
}

public String getMsg() {
	
	return this.msg;
}

public void setThangtoan(String[] thanhtoan) {

  this.thanhtoan = thanhtoan;	
}

public String[] getThanhtoan() {

	return this.thanhtoan;
}

public void setMact(String[] mact) {
	this.mact = mact;
	
}

public String[] getMact() {
	
	return this.mact;
}
@Override
public void DBClose() {
	// TODO Auto-generated method stub
	try{
	if(ctkmIds!=null){
		ctkmIds.close();
	}
	if(nppIds!=null){
		nppIds.close();
	}
	if(sanpham!=null){
		sanpham.close();
	}
	if(db!=null){
		db.shutDown();
	}
	
	}catch(Exception er){
		
	}
	

	
}

}
