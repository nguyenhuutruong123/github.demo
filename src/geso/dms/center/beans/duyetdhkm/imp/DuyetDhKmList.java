package geso.dms.center.beans.duyetdhkm.imp;
import java.sql.ResultSet;
import geso.dms.center.beans.duyetdhkm.IDuyetDhKmList;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Phan_Trang;

public class DuyetDhKmList  extends Phan_Trang implements IDuyetDhKmList
{
	private static final long serialVersionUID = -9217977546733610214L;
 
	String userId;
	String vungmien;
	String diengiai;
	String trangthai;
	String Msg;
 
	dbutils db;
	
	ResultSet rslist;
	
 
	
	public  DuyetDhKmList()
	{
		this.db = new dbutils();
		this.vungmien = "";
		this.diengiai = "";
		this.trangthai = "";
		this.Msg = "";
		this.thang="";
		this.nam="";
		this.nppId="";
		this.ctkmId="";
		init("");
	}
	
	 
	
	public String getUserId() 
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;
	}
	
	public String getVungmien() 
	{
		return this.vungmien;
	}

	public void setVungmien(String vungmien) 
	{
		this.vungmien = vungmien;
	}

	public String getDiengiai() 
	{
		return this.diengiai;
	}

	public void setDiengiai(String diengiai) 
	{
		this.diengiai = diengiai;
	}

	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}
 
	public void init(String search) 
	{
		String query;	
		if (search.length() == 0)
		{
			query = "  SELECT   D.PK_SEQ ,D.DIENGIAI, D.NGAYTAO,D.NGAYSUA,D.TRANGTHAI ,d.Thang,d.Nam,NT.TEN AS NGUOITAO,NS.TEN AS NGUOISUA  "+
					" FROM DUYETDONHANGKM   D INNER JOIN NHANVIEN NT ON NT.PK_SEQ=D.NGUOITAO  "+
					" INNER JOIN NHANVIEN AS NS ON NS.PK_SEQ=D.NGUOISUA";
			
		}
		else
		{
			query = search;
		}
		
		this.rslist=createSplittingData(50, 10, "trangthai asc, NGAYSUA desc, PK_SEQ desc", query); 
		
		query=
				"SELECT A.PK_SEQ AS NPPID, A.TEN AS NPPTEN,A.MA AS NPPMA, B.TEN AS KVTEN "+ 
				"FROM  "+
				" NHAPHANPHOI A INNER JOIN KHUVUC B ON A.KHUVUC_FK = B.PK_SEQ "+ 
				"WHERE A.TRANGTHAI = '1' AND A.SITECODE = A.CONVSITECODE ";
			/*if(this.kvId.length()>0)
			{
				query += " and A.khuvuc_fk ='"+this.kvId+"' ";
			}*/
			query+= "order by b.ten,a.ten asc ";
			this.nppRs=this.db.get(query);
			
			query=
					"SELECT A.PK_SEQ AS NPPID, A.TEN AS NPPTEN,A.MA AS NPPMA, B.TEN AS KVTEN "+ 
					"FROM  "+
					" NHAPHANPHOI A INNER JOIN KHUVUC B ON A.KHUVUC_FK = B.PK_SEQ "+ 
					"WHERE A.TRANGTHAI = '1' AND A.SITECODE = A.CONVSITECODE ";
				/*if(this.kvId.length()>0)
				{
					query += " and A.khuvuc_fk ='"+this.kvId+"' ";
				}*/
			
			
				query+= "order by b.ten,a.ten asc ";
				this.nppRs=this.db.get(query);
				
			query=" SELECT pk_seq,diengiai,scheme FROM CTKHUYENMAI c where 1=1  ";
			
			if(this.thang.length()>0)
			{
				query+= " and DENNGAY like '%"+this.thang+"%' ";
			}
			if(this.nam.length()>0)
			{
				query+= " and DENNGAY like '%"+this.nam+"%' ";
			}
			
			query+=" order by TuNgay desc ";
			
			this.ctkmRs = this.db.get(query);
			
	}

	public void closeDB(){
		
		try{
		if(rslist!=null){
			rslist.close();
		}
		if (this.db != null)
			this.db.shutDown();
		}catch(Exception err){
			
		}
	}

	public void setMsg(String Msg) {
		this.Msg = Msg;		
	}

	public String getMsg() {
		return this.Msg;
	}



	@Override
	public ResultSet getrslist() {
		// TODO Auto-generated method stub
		return this.rslist;
	}



	@Override
	public void setrslist(ResultSet rs) {
		// TODO Auto-generated method stub
		this.rslist=rs;
	}

	String thang,nam,nppId,ctkmId;



	public String getThang() {
		return thang;
	}



	public void setThang(String thang) {
		this.thang = thang;
	}



	public String getNam() {
		return nam;
	}



	public void setNam(String nam) {
		this.nam = nam;
	}



	public String getNppId() {
		return nppId;
	}



	public void setNppId(String nppId) {
		this.nppId = nppId;
	}



	public String getCtkmId() {
		return ctkmId;
	}



	public void setCtkmId(String ctkmId) {
		this.ctkmId = ctkmId;
	}
	
	ResultSet ctkmRs,nppRs;



	public ResultSet getCtkmRs() {
		return ctkmRs;
	}



	public void setCtkmRs(ResultSet ctkmRs) {
		this.ctkmRs = ctkmRs;
	}



	public ResultSet getNppRs() {
		return nppRs;
	}



	public void setNppRs(ResultSet nppRs) {
		this.nppRs = nppRs;
	}
	
}

