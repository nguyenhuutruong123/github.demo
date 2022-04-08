package geso.dms.center.beans.congnonpp.imp;

import geso.dms.distributor.db.sql.dbutils;
import geso.dms.center.beans.congnonpp.ICongnonpp;
import geso.dms.center.beans.congnonpp.INhaphanphoi;
import geso.dms.center.beans.congnonpp.imp.Nhaphanphoi;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Congnonpp implements ICongnonpp{

	private String Id, thoigian, Msg, nguoitao, nguoisua, ngaytao, ngaysua;
	private String userId;	
	ResultSet DvkdList, KbhList;
	
	List<INhaphanphoi> nppList;
	
	private String DvkdId, KbhId, diengiai, hinhthuctt;	
	dbutils db;	
	
	public Congnonpp()
	{ 
		db = new dbutils();		
		this.thoigian = "";	  		
		this.DvkdId = "";
		this.KbhId = "";
		this.userId = "";
		this.Msg = "";
		this.Id = "";
		this.diengiai = "";
		this.hinhthuctt = "";
	}
	public Congnonpp(String Id)
	{ 
		if(Id.equals("upload"))
		{
			db = new dbutils();							
			this.userId = "";
			this.Msg = "";		
			this.thoigian = "";	  
			this.DvkdId = "";
			this.KbhId = "";
			this.diengiai = "";
			this.hinhthuctt = "";
			createRS();
		}		
		else
		{
			this.Id = Id;
			this.userId = "";
			this.Msg = "";		
			this.thoigian = "";	
			this.DvkdId = "";
			this.KbhId = "";	
			this.diengiai = "";
			this.hinhthuctt = "";
			CreateCongnonppList();
		
		}
	}	 
	
	public void CreateCongnonppList()
	{
		createRS();
		String sql = 

			 "SELECT A.PK_SEQ AS ID, A.DIENGIAI, A.THOIGIAN, A.HINHTHUCTT,A.DVKD_FK, A.KBH_FK, " +
			 "NPP.PK_SEQ AS NPPID, NPP.TEN AS NPPTEN, B.SOTIEN " +
			 "FROM CONGNONPP A " +
			 "INNER JOIN CONGNONPP_CT B ON A.PK_SEQ = B.CONGNONPP_FK " +
			 "INNER JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ = B.NPP_FK " +
			 "WHERE A.PK_SEQ = '"+ this.Id +"' "; 
		System.out.println("Init : "+sql);
		db = new dbutils();
		ResultSet npplistRs = db.get(sql);		
		List<INhaphanphoi> npplist = new ArrayList<INhaphanphoi>();
		if(npplistRs != null)
		{
			String[] param = new String[4];
			INhaphanphoi npp = null;	
			try
			{
				while(npplistRs.next())
				{
					param[0] = npplistRs.getString("NPPID");
					param[1] = npplistRs.getString("NPPTEN");
					param[2] = npplistRs.getString("SOTIEN");					
					this.Id = npplistRs.getString("ID");
					this.diengiai = npplistRs.getString("DIENGIAI");
					this.thoigian = npplistRs.getString("THOIGIAN");
					this.hinhthuctt = npplistRs.getString("HINHTHUCTT");
					
					this.DvkdId = npplistRs.getString("DVKD_FK");
					this.KbhId = npplistRs.getString("KBH_FK");
					npp = new Nhaphanphoi(param);
					npplist.add(npp);
				}
				if(npplistRs!=null){
					npplistRs.close();
				}
			} catch(Exception e) {	
				e.printStackTrace();
			}
			this.nppList = npplist;
		}
	}

	public void setuserId(String userId) {

       this.userId = userId;
		
	}

	public String getuserId() {
		
		return this.userId;
	}

	public void setMsg(String Msg) {
		
		this.Msg = Msg;
		
	}

	public String getMsg() {
		
		return this.Msg;
	}
	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	public void setId(String Id) {
		
		this.Id = Id;
	}

	public String getId() {
		
		return this.Id;
	}
	
	public void DBclose() 
	{		
		if(db!=null) db.shutDown();
	}
	
	public void setNguoitao(String nguoitao) {
		
		this.nguoitao = nguoitao;
	}
	
	public String getNguoitao() {
		
		return this.nguoitao;
	}
	
	public void setNguoisua(String nguoisua) {
		
		this.nguoisua = nguoisua;
	}
	
	public String getNguoisua() {
		
		return this.nguoisua;
	}
	
	public void setNgaytao(String ngaytao) {
		
		this.ngaytao = ngaytao;
	}
	
	public String getNgaytao() {
		
		return this.ngaytao;
	}
	
	public void setNgaysua(String ngaysua) {
		
		this.ngaysua = ngaysua;
	}
	
	public String getNgaysua() {
		
		return this.ngaysua;
	}	
	
	public void createRS() {		
		CreateDvkdList();
		CreateKbhList();
	}
	
	public void CreateDvkdList()
	{
		String query = "SELECT * FROM DONVIKINHDOANH WHERE TRANGTHAI = '1'";
		db = new dbutils();
		this.DvkdList = db.get(query);
	}
	
	public void CreateKbhList()
	{
		String query = "SELECT * FROM KENHBANHANG WHERE TRANGTHAI = '1'";
		db = new dbutils();
		this.KbhList = db.get(query);
	}			
	
	public boolean update(List<INhaphanphoi> npplist) {
		
		try{
			String sql = "";
			db.getConnection().setAutoCommit(false);	
			
			sql = "SELECT COUNT(*) AS NUM FROM CONGNONPP WHERE THOIGIAN = '"+ this.thoigian +"' AND PK_SEQ != '"+ this.Id +"'";
			ResultSet rs = db.get(sql);
			rs.next();
			String count = rs.getString("NUM");
			if(count.equals("0"))
			{
				sql = "UPDATE CONGNONPP SET THOIGIAN = '"+ this.thoigian +"', DVKD_FK = '"+ this.DvkdId +"', KBH_FK = '"+ this.KbhId +"', DIENGIAI = N'"+ this.diengiai +"', HINHTHUCTT = N'"+ this.hinhthuctt +"' WHERE PK_SEQ = '"+ this.Id +"'";
				System.out.println("update CHITIEUPG : "+sql);
				if(!db.update(sql))
				{
					geso.dms.center.util.Utility.rollback_throw_exception(db);
					this.Msg = "Không thể sửa !";				
					return false;
				}
				
				for(int m = 0; m < npplist.size(); m++)
				{
					INhaphanphoi ctnpp = npplist.get(m);
					sql = "UPDATE CONGNONPP_CT SET SOTIEN = "+ ctnpp.getSotien() +" WHERE NPP_FK = '"+ ctnpp.getId() +"' AND CONGNONPP_FK = '"+ this.Id +"'";
					System.out.println("update CHITIEUPG_CT : "+sql);
					if(!db.update(sql))
					{
						geso.dms.center.util.Utility.rollback_throw_exception(db);
						this.Msg = "Không thể sửa !";				
						return false;
					}
				}			
			}
			else
			{
				this.Msg = "Đã có công nợ cho thời gian "+ this.thoigian +" !";				
				return false;
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);				
			return true;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return false;
		}				
	}
	
	public boolean SaveUpload(String thoigian, String dvkdId, String kbhId)
	{
		try
		{
			String query = "";			
			db.getConnection().setAutoCommit(false);					
			
			query = "SELECT COUNT(*) AS NUM FROM CONGNONPP WHERE THOIGIAN = '"+ this.thoigian +"' AND DVKD_FK = '"+ this.DvkdId +"' AND KBH_FK = '"+ this.KbhId +"'";
			System.out.println("num : "+query);
			ResultSet rsktra = db.get(query);
			rsktra.next();
			String count = rsktra.getString("NUM");
			if(count.equals("0"))
			{
						
				query = "INSERT INTO CONGNONPP(DIENGIAI, THOIGIAN, DVKD_FK, KBH_FK, HINHTHUCTT, NGUOITAO, NGAYTAO, NGUOISUA, NGAYSUA, TRANGTHAI) " +
						"SELECT TOP(1) DIENGIAI, THOIGIAN, DVKD_FK, KBH_FK, HINHTHUCTT, NGUOITAO, '"+ getDateTime() +"' AS NGAYTAO, NGUOITAO, '"+ getDateTime() +"' AS NGAYSUA, '0' AS TRANGTHAI " +
						"FROM CONGNONPP_CT_TMP WHERE THOIGIAN = '"+ this.thoigian +"' AND DVKD_FK = '"+ this.DvkdId +"' AND KBH_FK = '"+ this.KbhId +"' AND NGUOITAO = '"+ this.userId +"'";
				System.out.println("insert chitieupg : "+query);
				if (!this.db.update(query)) 
				{
					this.db.getConnection().rollback();
					this.Msg = "Lỗi chèn dữ liệu vào bảng (1) " + query;
					return false;							
				}
				
				String id = "";
				query = "SELECT SCOPE_IDENTITY() AS ID";
				ResultSet rs = this.db.get(query);
				rs.next();
				id = rs.getString("ID");
				query = "INSERT INTO CONGNONPP_CT " +
						"SELECT '"+ id +"' AS CONGNONPP_FK, NPP_FK, SOTIEN FROM CONGNONPP_CT_TMP " +
						"WHERE THOIGIAN = '"+ this.thoigian +"' AND DVKD_FK = '"+ this.DvkdId +"' AND KBH_FK = '"+ this.KbhId +"' AND NGUOITAO = '"+ this.userId +"'";
				System.out.println("insert CONGNONPP_CT : "+query);
				if (!this.db.update(query)) 
				{
					this.db.getConnection().rollback();
					this.Msg = "Vui lòng chọn file cần upload";
					return false;							
				}
			}
			else
			{
				query = "SELECT DONVIKINHDOANH FROM DONVIKINHDOANH WHERE TRANGTHAI = '1' AND PK_SEQ = '"+ this.DvkdId +"'";
				ResultSet rsdvkd = db.get(query);
				rsdvkd.next();
				String dvkd = rsdvkd.getString("DONVIKINHDOANH");
				query = "SELECT DIENGIAI AS KENHBANHANG FROM KENHBANHANG WHERE TRANGTHAI = '1' AND PK_SEQ = '"+ this.KbhId +"'";
				ResultSet rskbh = db.get(query);
				rskbh.next();
				String kbh = rskbh.getString("KENHBANHANG");
				this.Msg = "Đã có công nợ cho thời gian "+ this.thoigian +", đơn vị kinh doanh "+ dvkd +" và kênh bán hàng "+ kbh +"";				
				return false;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);				
			return true;
			
		}catch(Exception ex)
		{	ex.printStackTrace();
			return false;
		}	
	}
	
	public List<INhaphanphoi> getnppList() {
		
		return this.nppList;
	}
	
	public void setnppList(List<INhaphanphoi> npplist) {
		
		this.nppList = npplist;
	}
	
	public void setThoigian(String thoigian) {
		
		this.thoigian = thoigian;
	}
	
	public String getThoigian() {
		
		return this.thoigian;
	}
	
	public void setDvkdId(String dvkdId) {
		
		this.DvkdId = dvkdId;
	}
	
	public String getDvkdId() {
		
		return this.DvkdId;
	}
	
	public void setKbhId(String kbhId) {
		
		this.KbhId = kbhId;
	}
	
	public String getKbhId() {
		
		return this.KbhId;
	}
	
	public void setDiengiai(String diengiai) {
		
		this.diengiai = diengiai;
	}
	
	public String getDiengiai() {
		
		return this.diengiai;
	}
	
	public void setHinhthuctt(String httt) {
		
		this.hinhthuctt = httt;
	}
	
	public String getHinhthuctt() {
		
		return this.hinhthuctt;
	}
	
	public void setDvkdList(ResultSet dvkdlist) {
		
		this.DvkdList = dvkdlist;
	}
	
	public ResultSet getDvkdList() {
		
		return this.DvkdList;
	}
	
	public void setKbhList(ResultSet kbhlist) {
		
		this.KbhList = kbhlist;
	}
	
	public ResultSet getKbhList() {
		
		return this.KbhList;
	}	
}
