package geso.dms.center.beans.phanbodathang.imp;

import geso.dms.center.beans.phanbodathang.IPhanbodathang;
import geso.dms.center.db.sql.*;
import java.sql.ResultSet;

public class Phanbodathang implements IPhanbodathang {
	
	public String tungay;
	
	public String denngay;

	public String spId;
	
	public String pk_seq;
	
	public ResultSet sp;
	
	public String kenhId;
	
	public ResultSet kenh;

	public String vungId;
	
	public ResultSet vung;
	
	public String kvId;
	
	public ResultSet kv;
	
	public dbutils db;
	
	public String msg;
	
	public ResultSet pb;
	
	public Phanbodathang(){
		this.tungay = "";
		this.denngay = "";
		this.spId = "";
		this.pk_seq = "";
		this.kenhId = "100000";
		this.vungId = "";
		this.kvId = "";
		this.msg = "";
		this.db = new dbutils();		
	}
	
	public String getTungay(){
		return this.tungay;
	}
	
	public void setTungay(String tungay){
		this.tungay = tungay;
	}

	public String getDenngay(){
		return this.denngay;
	}

	public void setDenngay(String denngay){
		this.denngay = denngay;
	}
	
	public String getSpId(){
		return this.spId;
	}

	public void setSpId(String spId){
		this.spId = spId;
		ResultSet rs = this.db.get("SELECT SP.PK_SEQ " + 
				  "FROM SANPHAM SP " +
				  "WHERE SP.MA='"+ spId + "' " +
				  "AND SP.PK_SEQ IN (SELECT SANPHAM_FK FROM BGMUANPP_SANPHAM WHERE TRANGTHAI = '1' AND GIAMUANPP > 0)") ;
		try{
			if(rs.next()){
				this.pk_seq = rs.getString("PK_SEQ");
				rs.close();
			}else{
				this.pk_seq = "";
			}
		}catch(Exception err){
			this.pk_seq = "";
		}
	}
	
	public ResultSet getSp(){
		return this.sp;
	}

	public void setSp(ResultSet sp){
		this.sp = sp;
	}

	public String getKenhId(){
		return this.kenhId;
	}

	public void setKenhId(String kenhId){
		this.kenhId = kenhId;
	}

	public String getVungId(){
		return this.vungId;
	}

	public void setVungId(String vungId){
		this.vungId = vungId;
	}

	public ResultSet getKenh(){
		return this.kenh;
	}

	public void setKenh(ResultSet kenh){
		this.kenh = kenh;
	}

	public ResultSet getVung(){
		return this.vung;
	}

	public void setVung(ResultSet vung){
		this.vung = vung;
	}

	public String getKvId(){
		return this.kvId;
	}

	public void setKvId(String kvId){
		this.kvId = kvId;
	}

	public ResultSet getKv(){
		return this.kv;
	}

	public void setKv(ResultSet kv){
		this.kv = kv;
	}
	
	public String getMsg(){
		return this.msg;
	}

	public void setMsg(String msg){
		this.msg = msg;
	}

	public ResultSet getPhanbo(){
		return this.pb;
	}

	public void setPhanbo(ResultSet pb){
		this.pb = pb;
	}
	
	public void init(){
		String query = "";
		
		query = "SELECT SP.PK_SEQ, SP.MA, SP.TEN " + 
				"FROM SANPHAM SP " +
				"INNER JOIN ( " +
					"SELECT DISTINCT SANPHAM_FK AS SPID " +
					"FROM BGMUANPP_SANPHAM " +
					"WHERE TRANGTHAI ='1' " +
				")SPBAN ON SPBAN.SPID = SP.PK_SEQ " +
				"WHERE SP.TRANGTHAI = '1' ";
		
		System.out.println(query);
		
		this.sp = this.db.get(query);
		
		this.kenh = this.db.get("SELECT PK_SEQ, DIENGIAI FROM KENHBANHANG WHERE TRANGTHAI='1'");
		
		this.vung = this.db.get("SELECT PK_SEQ, DIENGIAI FROM VUNG WHERE TRANGTHAI='1'");
		
		if(this.vungId.length() > 0)
			this.kv = this.db.get("SELECT PK_SEQ, DIENGIAI FROM KHUVUC WHERE VUNG_FK='" + this.vungId + "' AND TRANGTHAI='1'");
		else
			this.kv = null;
		
		query = "SELECT NPP.PK_SEQ, NPP.MA, NPP.TEN, KBH.DIENGIAI AS KBH, PBDH.SANPHAM_FK, SP.MA AS MASP, PBDH.SOLUONG, ISNULL((SELECT SUM(SOLUONG) " +  
				"FROM DONDATHANG_SP " +
				"INNER JOIN DONDATHANG DDH ON DDH.PK_SEQ = DONDATHANG_FK " +    
				"WHERE SANPHAM_FK=PBDH.SANPHAM_FK AND PBDH.NPP_FK=DDH.NPP_FK AND NGAYDAT >=PBDH.TUNGAY AND NGAYDAT <=PBDH.DENNGAY ),0) / QC.SOLUONG1 AS DADAT , " +   
				"PBDH.SOLUONG - ISNULL((SELECT SUM(SOLUONG)  " +
					"FROM DONDATHANG_SP INNER JOIN DONDATHANG DDH ON DDH.PK_SEQ = DONDATHANG_FK " +    
					"WHERE SANPHAM_FK = PBDH.SANPHAM_FK AND PBDH.NPP_FK=DDH.NPP_FK AND NGAYDAT >=PBDH.TUNGAY AND NGAYDAT<=PBDH.DENNGAY ),0)/ QC.SOLUONG1 AS CONLAI , " +  
				"PBDH.TUNGAY,  " +
				"PBDH.DENNGAY   " +
				"FROM  PHANBODATHANG PBDH " +     
				"INNER JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ = PBDH.NPP_FK " +   
				"INNER JOIN KENHBANHANG KBH ON KBH.PK_SEQ = PBDH.KBH_FK " +
				"INNER JOIN NHANVIEN NV1 ON NV1.PK_SEQ = PBDH.NGUOITAO  " +
				"INNER JOIN QUYCACH QC ON QC.SANPHAM_FK=PBDH.SANPHAM_FK " +
				"INNER JOIN SANPHAM SP ON SP.PK_SEQ=PBDH.SANPHAM_FK " +
				"WHERE SP.MA= '"+this.spId+"'";


		
		
		if(this.spId.length() > 0){
			System.out.println(query);
			this.pb =  this.db.get(query);
		}		
		else
			this.pb = null;
	}

	@Override
	public void DBClose() {
		// TODO Auto-generated method stub
		try{
			if(kenh!=null){
				kenh.close();
				
			}
			if(kv!=null){
				kv.close();
			}
			if(sp!=null){
				sp.close();
			}
			if(vung!=null){
				vung.close();
				
			}
			if(db!=null){
				db.shutDown();
			}
		}catch(Exception er){
			
		}
	}

}
