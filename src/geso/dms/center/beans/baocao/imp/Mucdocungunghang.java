package geso.dms.center.beans.baocao.imp;

import geso.dms.center.beans.baocao.IMucdocungunghang;
import geso.dms.center.db.sql.*;
import java.sql.ResultSet;
public class Mucdocungunghang implements IMucdocungunghang{
	String userId;
	String masp;
	String tensp;
	String tungay;
	String denngay;
	String khoId;
	String vungId;
	String kvId;
	String nppId;
	String search; 
	
	ResultSet mdcu;
	ResultSet khoIds;
	ResultSet vungIds;
	ResultSet kvIds;
	ResultSet nppIds;
	dbutils db;
	
	public Mucdocungunghang(){
		this.masp = "";
		this.tensp = "";
		this.tungay = "";
		this.denngay = "";
		this.khoId = "";
		this.vungId = "0";
		this.kvId = "0";
		this.nppId = "0";
		this.search = "";
		this.db = new dbutils();
	}

	public String getUserId(){
		return this.userId;
	}
	
	public void setUserId(String userId){
		this.userId = userId;
	}
	
	public String getMasp(){
		return this.masp;
	}
	
	public void setMasp(String masp){
		this.masp = masp;
	}
	
	public String getTensp(){
		return this.tensp;
	}
	
	public void setTensp(String tensp){
		this.tensp = tensp;
	}

	public String getTuNgay(){
		return this.tungay;
	}
	
	public void setTuNgay(String tungay){
		this.tungay = tungay;
	}

	public String getDenNgay(){
		return this.denngay;
	}
	
	public void setDenNgay(String denngay){
		this.denngay = denngay;
	}

	public String getKhoId(){
		return this.khoId;
	}
	
	public void setKhoId(String khoId){
		this.khoId = khoId;
	}

	public ResultSet getKhoIds(){
		return this.khoIds;
	}
	
	public void setKhoIds(ResultSet khoIds){
		this.khoIds = khoIds;
	}

	public String getVungId(){
		return this.vungId;
	}
	
	public void setVungId(String vungId){
		this.vungId = vungId;
	}

	public ResultSet getVungIds(){
		return this.vungIds;
	}
	
	public void setVungIds(ResultSet vungIds){
		this.vungIds = vungIds;
	}

	public String getKvId(){
		return this.kvId;
	}
	
	public void setKvId(String kvId){
		this.kvId = kvId;
	}

	public ResultSet getKvIds(){
		return this.kvIds;
	}
	
	public void setKvIds(ResultSet kvIds){
		this.kvIds = kvIds;
	}

	public String getNppId(){
		return this.nppId;
	}
	
	public void setNppId(String nppId){
		this.nppId = nppId;
	}
	
	public ResultSet getNppIds(){
		return this.nppIds;
	}
	
	public void setNppIds(ResultSet nppIds){
		this.nppIds = nppIds;
	}

	public String getSearch(){
		return this.search;
	}
	
	public void setSearch(String search){
		this.search = search;
	}

	public ResultSet getMucdocungung(){
		String query;
		if(this.search.length()== 0 ){
			query = "select distinct " + 
					"ddh.pk_seq as ddhId, " + 
					"sp.ma as masp, sp.ten as tensp, ddh.ngaydat,  cast (ddh_sp.soluong/qc.soluong1 as decimal) as dagiao, " + 
					" cast( (ddh_sp.soluong/qc.soluong1 + th.soluong) as decimal) as dadat, qc.soluong1 as quycach, npp.khosap as kho, " +
					"v.ten as vung, kv.ten as khuvuc, npp.ten as nppTen, th.loai " +
					"from thieuhang th " +
					"inner join dondathang ddh on th.dondathang_fk=ddh.pk_seq " + 
					"inner join dondathang_sp ddh_sp on ddh.pk_seq = ddh_sp.dondathang_fk " +  
					"inner join sanpham sp on sp.pk_seq = th.sanpham_fk and th.sanpham_fk=ddh_sp.sanpham_fk " +
					"inner join quycach qc on sp.dvdl_fk = qc.dvdl1_fk and qc.dvdl2_fk='100018' and qc.sanpham_fk=sp.pk_seq " +
					"inner join nhaphanphoi npp on npp.pk_seq = ddh.npp_fk " +
					"inner join khuvuc kv on npp.khuvuc_fk = kv.pk_seq " +
					"inner join vung v on v.pk_seq = kv.vung_fk order by ddh.ngaydat desc, loai" ;
		}else{
			query = search;
		
		}
		System.out.println(query);
		this.mdcu = this.db.get(query);
		
		return this.mdcu;
	}
	
	public void setMucdocungung(ResultSet mdcu){
		this.mdcu = mdcu;
	}
	
	public void init(){

		this.khoIds = this.db.get("select distinct khosap as khoid from nhaphanphoi");
		
		this.vungIds = this.db.get("select pk_seq as vungId, ten as vungTen from vung");
		
		if(!this.vungId.equals("0")){
			this.kvIds = this.db.get("select pk_seq as kvId, ten as kvTen from khuvuc where vung_fk='" + this.vungId + "'");
		}else{
			this.kvIds = this.db.get("select pk_seq as kvId, ten as kvTen from khuvuc");
		}
		
		String query = "select pk_seq as nppId, ten as nppTen from nhaphanphoi where 'a'='a'";
		
		if(!this.kvId.equals("0")){
			query = query + " and khuvuc_fk='" + this.kvId + "'";
		}
		
		if (this.khoId.length() > 0){
			query = query + " and khosap ='" + this.khoId + "'";
		}
		System.out.println(query);
		this.nppIds = this.db.get(query);
		
	}

	@Override
	public void DBClose() {
		// TODO Auto-generated method stub
		try{
		if( mdcu!=null){
			mdcu.close();
		}
		
		if( khoIds!=null){
			khoIds.close();
		}

		if( vungIds!=null){
			vungIds.close();
		}
		
		if( kvIds!=null){
			kvIds.close();
		}
	
		if( nppIds!=null){
			nppIds.close();
		}
		 if( db!=null){
			 db.shutDown();
			 }
		}catch(Exception er){
		
		}
	}
}
