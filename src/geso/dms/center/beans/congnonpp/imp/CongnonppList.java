package geso.dms.center.beans.congnonpp.imp;

import java.sql.ResultSet;

import geso.dms.center.beans.congnonpp.ICongnonppList;
import geso.dms.center.db.sql.dbutils;

public class CongnonppList implements ICongnonppList {

	dbutils db;
	String userId, Id, tungay, denngay, trangthai, Msg, userTen;
	ResultSet cnList;
	
	
	public CongnonppList(){ 
		 this.db = new dbutils();
		 this.Id = "";
		 this.tungay = "";
		 this.denngay = "";
		 this.trangthai = "";
		 this.Msg = "";
	}
	
	
	public void setuserId(String userId) {
		
		this.userId = userId;
	}

	
	public String getuserId() {
		
		return this.userId;
	}

	
	public String getId() {
		
		return this.Id;
	}

	
	public void setId(String id) {
		
		this.Id = id;
	}

	public ResultSet getCnList() {
		
		return this.cnList;
	}

	
	public void setCnList(ResultSet cnlist) {
		
		this.cnList = cnlist;
	}

	public void init(String st) {	
		
		String query="";
		if(st.length()>0)
		{
			String tk="";						
			
			if(this.tungay.length()>0)
			{
				tk = tk + " AND A.THOIGIAN >= '"+ this.tungay +"' ";
			}
  	   		
			if(this.denngay.length()>0)
			{				
				tk = tk + " AND A.THOIGIAN <= '"+ this.denngay +"' ";
			}
			
			if(this.trangthai.length()>0)
			{				
				tk = tk + " AND A.TRANGTHAI = '"+ this.trangthai +"' ";
			}
			
			query = 
				"SELECT A.PK_SEQ, A.THOIGIAN, A.DIENGIAI, DVKD.DONVIKINHDOANH AS DVKDTEN, KBH.DIENGIAI AS KBHTEN, NT.TEN AS NGUOITAO, NS.TEN AS NGUOISUA, A.NGAYTAO, A.NGAYSUA, A.TRANGTHAI " +
				"FROM CONGNONPP A " +
				"INNER JOIN NHANVIEN NT ON NT.PK_SEQ = A.NGUOITAO " +
				"INNER JOIN NHANVIEN NS ON NS.PK_SEQ = A.NGUOISUA " +
				"INNER JOIN DONVIKINHDOANH DVKD ON DVKD.PK_SEQ = A.DVKD_FK " +
				"INNER JOIN KENHBANHANG KBH ON KBH.PK_SEQ = A.KBH_FK " +				
				"WHERE 1=1 " + tk;
								   
       }
       else
       {
    	   query = 
				"SELECT A.PK_SEQ, A.THOIGIAN, A.DIENGIAI, DVKD.DONVIKINHDOANH AS DVKDTEN, KBH.DIENGIAI AS KBHTEN, NT.TEN AS NGUOITAO, NS.TEN AS NGUOISUA, A.NGAYTAO, A.NGAYSUA, A.TRANGTHAI " +
				"FROM CONGNONPP A " +
				"INNER JOIN NHANVIEN NT ON NT.PK_SEQ = A.NGUOITAO " +
				"INNER JOIN NHANVIEN NS ON NS.PK_SEQ = A.NGUOISUA " +
				"INNER JOIN DONVIKINHDOANH DVKD ON DVKD.PK_SEQ = A.DVKD_FK " +
				"INNER JOIN KENHBANHANG KBH ON KBH.PK_SEQ = A.KBH_FK ";
       }
		query += " ORDER BY A.THOIGIAN DESC ";
		   System.out.println("Search : " + query);      
		   
      this.cnList = db.get(query);		
          
	}
	
	public void setmsg(String msg) {
		
		this.Msg = msg;
	}

	public String getmsg() {
		
		return this.Msg;
	}
	
	public void DBclose() {
		
		try{
			if(cnList!=null) cnList.close();			
			if(!(this.db == null)){
				this.db.shutDown();
			}
			}catch(Exception e){}
	}
	
	public void setuserTen(String userTen) {
		
		this.userTen = userTen;
	}

	public String getuserTen() {
		
		return this.userTen;
	}

	public String getTungay() {
		
		return this.tungay;
	}
		
	public String getDenngay() {
		
		return this.denngay;
	}

	public void setDenngay(String denngay) {
		
		this.denngay = denngay;
	}


	
	public void setTungay(String tungay) {
		
		this.tungay = tungay;
	}


	
	public String getTrangthai() {
		
		return this.trangthai;
	}


	
	public void setTrangthai(String trangthai) {
		
		this.trangthai = trangthai;
	}	
}
