package geso.dms.distributor.beans.phieuthanhtoan.imp;

import java.sql.ResultSet;
import geso.dms.distributor.beans.phieuthanhtoan.IPhieuthanhtoanList;
import geso.dms.distributor.db.sql.dbutils;

public class PhieuthanhtoanList implements IPhieuthanhtoanList
{
	private static final long serialVersionUID = -9217977546733610214L;

	String userId;
	String nppId;
	String nppTen;
	String tungay;
	String denngay;
	String nvgnId;
	ResultSet nvgn;
	String pttId;
	ResultSet ptt;
	String khId;
	String sitecode;
	String query;
	String khquery;
	ResultSet ttoan;
	String tongthu;
	String tongttoan;
	dbutils db;

	public PhieuthanhtoanList()
	{
		this.userId = "";
		this.nppId = "";
		this.nppTen = "";
		this.tungay = "";
		this.denngay = "";
		this.nvgnId = "";
		this.pttId = "";
		this.khId = "";
		this.sitecode = "";
		this.khquery = "";
		this.query = "";
		this.tongthu = "";
		this.tongttoan = "";
		db = new dbutils();
		 
	}

	public String getUserId() {
		
		return this.userId;
	}

	
	public void setUserId(String userId) {
		
		this.userId = userId;
		this.getNppInfo();
		
/*		this.query = 	"SELECT KH.PK_SEQ AS KHID, KH.TEN AS KHTEN, KH.DIACHI AS DIACHI, DH.PK_SEQ AS DHID, " + 
						"DH.NGAYNHAP AS NGAY, ROUND(DH.TONGGIATRI-ISNULL(DH_CTKM.CHIETKHAU,0),0) AS TONGSOTIEN, PTHTOAN.PTTKH_FK AS PHIEUTHU, " +
						"PTHTOAN.SOTIEN AS DATHANHTOAN, PTTNVGN.NGAYTHU, NVGN.TEN AS NVGNTEN, CEILING(DH.TONGGIATRI-PTHTOAN.SOTIEN) AS SODU " +
						"FROM DONHANG DH " +
						"LEFT JOIN DONHANG_CTKM_TRAKM DH_CTKM ON DH_CTKM.DONHANGID = DH.PK_SEQ " +
						"INNER JOIN KHACHHANG KH ON DH.KHACHHANG_FK = KH.PK_SEQ " +
						"INNER JOIN PHIEUTHANHTOAN PTHTOAN ON PTHTOAN.DONHANG_FK = DH.PK_SEQ " + 
						"INNER JOIN PHIEUTHUTIEN_NVGN PTTNVGN ON PTTNVGN.PK_SEQ = PTHTOAN.PTTKH_FK " +
						"INNER JOIN NHANVIENGIAONHAN NVGN ON NVGN.PK_SEQ = PTTNVGN.NVGN_FK " + 
						"WHERE DH.NPP_FK = '" + this.nppId + "' ";*/
		
		this.query =	"SELECT KH.PK_SEQ AS KHID, KH.TEN AS KHTEN, KH.DIACHI AS DIACHI, KH_CN.DONHANG_FK AS DHID, " +   
						"KH_CN.NGAYNO AS NGAY, " +
						"ROUND(KH_CN.SOTIENNO,0) AS TONGSOTIEN, " + 
						"PTHTOAN.PTTKH_FK AS PHIEUTHU,  " +
						"PTHTOAN.SOTIEN AS DATHANHTOAN, " +
						"PTTNVGN.NGAYTHU, NVGN.TEN AS NVGNTEN, " + 
						"ROUND(CEILING(KH_CN.SOTIENNO),0)-ROUND(CEILING(PTHTOAN.SOTIEN),0) AS SODU " +  
						"FROM KHACHHANG_CONGNO KH_CN  " +
						"INNER JOIN KHACHHANG KH ON KH_CN.KHACHHANG_FK = KH.PK_SEQ " +  
						"INNER JOIN PHIEUTHANHTOAN PTHTOAN ON PTHTOAN.DONHANG_FK = KH_CN.DONHANG_FK " +  
						"INNER JOIN PHIEUTHUTIEN_NVGN PTTNVGN ON PTTNVGN.PK_SEQ = PTHTOAN.PTTKH_FK  " + 
						"INNER JOIN NHANVIENGIAONHAN NVGN ON NVGN.PK_SEQ = PTTNVGN.NVGN_FK  " + 
						"WHERE KH_CN.KHACHHANG_FK IN (SELECT PK_SEQ FROM KHACHHANG WHERE NPP_FK= '" + this.nppId + "')" ;		

		System.out.println(this.query);
		
		this.khquery = 	"SELECT DISTINCT KH.PK_SEQ AS KHID " +
						"FROM KHACHHANG_CONGNO KH_CN " +
						"INNER JOIN KHACHHANG KH ON KH_CN.KHACHHANG_FK = KH.PK_SEQ " +
						"INNER JOIN PHIEUTHANHTOAN PTHTOAN ON PTHTOAN.DONHANG_FK = KH_CN.DONHANG_FK " + 
						"INNER JOIN PHIEUTHUTIEN_NVGN PTTNVGN ON PTTNVGN.PK_SEQ = PTHTOAN.PTTKH_FK " +
						"INNER JOIN NHANVIENGIAONHAN NVGN ON NVGN.PK_SEQ = PTTNVGN.NVGN_FK " + 
						"WHERE KH_CN.KHACHHANG_FK IN (SELECT PK_SEQ FROM KHACHHANG WHERE NPP_FK= '" + this.nppId + "')" ;	
		System.out.println(this.khquery);
	}

	public String getKhId() {
		
		return this.khId;
	}

	
	public void setKhId(String khId) {
		
		this.khId = khId;
	}

	public String getTungay() {
		
		return this.tungay;
	}

	public void setTungay(String tungay) {
	
		this.tungay = tungay;
	}

	
	public String getDenngay() {
		
		return this.denngay;
	}

	
	public void setDenngay(String denngay) {
		
		this.denngay = denngay;
	}

	public String getNvgnId() {		
		return this.nvgnId;
	}

	
	public void setNvgnId(String nvgnId) {
		
		this.nvgnId = nvgnId;
	}
	
	public String getPttId() {
		
		return this.pttId;
	}

	
	public void setPttId(String pttId) {
		
		this.pttId = pttId;
	}

	public String getNppId() {
		
		return this.nppId;
	}

	
	public void setNppId(String nppId) {
		
		this.nppId = nppId;
	}

	
	public String getNppTen() {
		
		return this.nppTen;
	}

	
	public void setNppTen(String nppTen) {
		
		this.nppTen = nppTen;
	}

	public void setPhieuthu(ResultSet ptt) {
		
		this.ptt = ptt;
	}

	public String getQuery() {		
		return this.query;
	}

	
	public void setQuery(String query) {
		
		this.query = query;
	}
	
	public String getKHquery() {		
		return this.khquery;
	}

	
	public void setKHquery(String khquery) {
		
		this.khquery = khquery;
	}

	public ResultSet getPhieuthu() {
		return this.ptt;
	}
	
	public void setTtoan(ResultSet ttoan) {
		
		this.ttoan = ttoan;
	}

	
	public ResultSet getTtoan() {
		return this.ttoan;
	}


	public void setTongthu(String tongthu) {
		
		this.tongthu = tongthu;
	}

	
	public String getTongthu() {
	    String sql = "SELECT isnull(SUM(PTTKH.SOTIEN),0) AS TONGTHU  FROM PHIEUTHUTIEN_KH PTTKH " +
		 			 "INNER JOIN PHIEUTHUTIEN_NVGN PTTNVGN ON PTTNVGN.PK_SEQ = PTTKH.PTTNVGN_FK " + 
		 			 "INNER JOIN KHACHHANG KH ON KH.PK_SEQ = PTTKH.KHACHHANG_FK " +
		 			 "WHERE PTTNVGN.TRANGTHAI = '1' ";
	    
	    if(this.khquery.length() > 0) {
	    	System.out.println(this.khquery);
	    	sql = sql + " AND KH.PK_SEQ IN (" + this.khquery + ")";
	    }
	    if (this.tungay.length() > 0)
	    	sql = sql + " AND PTTNVGN.NGAYTHU >= '" + this.tungay + "'";

	    if (this.denngay.length() > 0)
	    	sql = sql + " AND PTTNVGN.NGAYTHU <= '" + this.denngay + "'";
	    
	    System.out.println("Tong thu: " + sql);
	    ResultSet rs =  this.db.get(sql);
	    this.tongthu ="0";
	    try{
	    	if(rs!=null)
	    	{
		    	if(rs.next())
		    	this.tongthu = rs.getString("tongthu");
	    	}
	    }catch(Exception e){}
	    
		return this.tongthu;
	}

	public void setTongTToan(String tongttoan) {
		
		this.tongttoan = tongttoan;
	}

	
	public String getTongTToan() {
		
		String sql = "SELECT ISNULL(SUM(PTT.SOTIEN),0) AS TONGTHANHTOAN  FROM PHIEUTHANHTOAN PTT " +
					 "INNER JOIN PHIEUTHUTIEN_NVGN PTTNVGN ON PTTNVGN.PK_SEQ = PTT.PTTKH_FK " + 
					 "INNER JOIN KHACHHANG_CONGNO KH_CN ON KH_CN.DONHANG_FK = PTT.DONHANG_FK " +
					 "INNER JOIN KHACHHANG KH ON KH.PK_SEQ = KH_CN.KHACHHANG_FK " +
					 "WHERE PTTNVGN.TRANGTHAI = '1' "; 

		if(this.khquery.length() > 0) 
	    	sql = sql + " AND KH.PK_SEQ IN (" + this.khquery + ")";
	    
	    if (this.tungay.length() > 0)
	    	sql = sql + " AND PTT.NGAY >= '" + this.tungay + "'";

	    if (this.denngay.length() > 0)
	    	sql = sql + " AND PTT.NGAY <= '" + this.denngay + "'";
	    
	    ResultSet rs =  this.db.get(sql);
	    this.tongttoan="0";
	    try{
	    	if(rs!=null)
	    	if(rs.next())
	    	this.tongttoan = rs.getString("TONGTHANHTOAN");
	    	
	    }catch(Exception e){}
	    
	//	return this.tongthu;
	  return   this.tongttoan;

	}

	public void setNvgn(ResultSet nvgn) {
		
		this.nvgn = nvgn;
	}

	
	public ResultSet getNvgn() {
		return this.nvgn;
	}
	
	public void init(String search) {
//		getNppInfo();
		
		if(search.length()> 0){
			this.query = search;
		}else{
			this.query = this.query + "    ORDER BY KHTEN, NGAY DESC";
		}
		
	    this.ttoan = this.db.get(this.query);
	    
	    this.nvgn = this.db.get("select pk_seq as nvgnId, ten as nvgnTen from nhanviengiaonhan where npp_fk='" + this.nppId + "'");
	    
	    this.ptt = this.db.get("select pk_seq as pttId, diengiai from phieuthutien_nvgn where npp_fk='" + this.nppId + "' and nvgn_fk='" + this.nvgnId + "' and trangthai='1'");
	    	    
	    

	}
   
	
	public void DBclose() {
		try{
			if(this.ptt != null) this.ptt.close();
			if(this.nvgn != null) this.nvgn.close();
			if(this.ttoan != null) this.ttoan.close();
			if(db != null)
				this.db.shutDown();
		}catch(Exception e){}
	}

	private void getNppInfo()
	{	
		/*String sql="select a.pk_seq, a.ten, a.sitecode from nhaphanphoi a, nhanvien b where b.dangnhap = a.ma and b.pk_seq ='" + this.userId + "'";
	    System.out.println("sql"+sql);
		ResultSet rs = this.db.get(sql);
		try{
			if (!(rs == null)){
				rs.next();
				this.nppId = rs.getString("pk_seq");
				this.nppTen = rs.getString("ten");
				this.sitecode = rs.getString("sitecode");
				
			}else
			{
				this.nppId = "";
				this.nppTen = "";
				this.sitecode = "";				
			}
			
		}catch(Exception e){}	
		*/
		//Phien ban moi
		geso.dms.distributor.util.Utility util=new geso.dms.distributor.util.Utility();
		this.nppId=util.getIdNhapp(this.userId);
		this.nppTen=util.getTenNhaPP();
		//this.dangnhap = util.getDangNhap();
		this.sitecode=util.getSitecode();
	}
	

}

