package geso.dms.center.beans.nhomkhachhangkm.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import jxl.write.Label;

import geso.dms.center.beans.nhomkhachhangkm.INhomkhachhangkm;
import geso.dms.distributor.db.sql.dbutils;

public class Nhomkhachhangkm implements INhomkhachhangkm {
    
	

	private String Diengiai;
	private String Trangthai;
	private String Tungay;
	private String Denngay;
	private String Ten;
	private String userId;
	private String Msg;
	private String Id,active;
	String kenhId,vungId,khuvucId,nppId,ddkdId,khId;
	ResultSet kenhRs,vungRs,khuvucRs,nppRs,ddkdRs,khRs;
	dbutils db ;
	
	
	String khMaUpload = "";
	String action = "";
	
	public String getKhId()
	{
		return khId;
	}
	public void setKhId(String khId)
	{
		this.khId = khId;
	}
	public ResultSet getKhRs()
	{
		return khRs;
	}
	public void setKhRs(ResultSet khRs)
	{
		this.khRs = khRs;
	}

	
	public Nhomkhachhangkm()
	{ this.Diengiai ="";
	  this.Trangthai="";
	  this.Ten = "";
	  this.userId ="";
	  this.Msg ="";
	  this.Id ="";
	  this.nppId="";
	  this.vungId="";
	  this.kenhId="";
	  this.khuvucId="";
	  this.ddkdId="";
	  this.khId="";
	  db = new dbutils();
	}
	public Nhomkhachhangkm(String Id)
	{ 
	 db = new dbutils();
	 ResultSet rs = db.get("select * from NHOMKHACHHANGNPP where pk_seq ='"+ Id +"'");
	 System.out.println("select * from NHOMKHACHHANGNPP where pk_seq ='"+ Id +"'");
	  try 
	  {
		  rs.next();
		  this.Id = Id;
		  this.Ten = rs.getString("ten");
		  this.Diengiai = rs.getString("diengiai");
		  this.Trangthai = rs.getString("trangthai");
		  this.Msg ="";
		  this.nppId="";
		  this.vungId="";
		  this.kenhId="";
		  this.khuvucId="";
		  this.ddkdId="";
		  this.khId="";
		  
		  String query=
		  "	select b.PK_SEQ ,b.smartId as khMa,b.TEN,b.DIACHI,b.DIENTHOAI,c.MANPP as nppMa,c.TEN as nppTen " +
			"	from CTKM_KHACHHANG a  "+
			"		inner join KHACHHANG b on a.KHACHHANG_FK=b.PK_SEQ "+ 
			"		inner join NHAPHANPHOI c on c.PK_SEQ=b.NPP_FK "+
			"	where a.NHOMKHNPP_FK ='"+this.Id+"' "; 
		  System.out.println(query);
		  this.khRs=this.db.get(query);
		  query=" select KHACHHANG_FK from CTKM_KHACHHANG where NHOMKHNPP_FK ='"+this.Id+"' ";
		  rs=db.get(query);
		  while(rs.next())
		  {
			  this.khId+= rs.getString("KHACHHANG_FK") +",";
		  }
		  if(rs!=null)rs.close();
		  
		  createRs();
	} catch(Exception e) 
	{
		e.printStackTrace();
	}
		
	}
	public void setDiengiai(String Diengiai) 
	{
		
		this.Diengiai = Diengiai;
	}

	public String getDiengiai() {

		return this.Diengiai;
	}

	public void setTrangthai(String Trangthai) {

		this.Trangthai = Trangthai;
	}

	public String getTrangthai() {
		
		return this.Trangthai;
	}

	public void setTungay(String Tungay) {
		
		this.Tungay = Tungay;
	}

	public String getTungay() {
		
		return this.Tungay;
	}

	public void setDenngay(String Denngay) {
		this.Denngay = Denngay;
		
	}

	public String getDenngay() {
		
		return this.Denngay;
	}
    
	public void init() {
		// TODO Auto-generated method stub
		
	}
	public String getKenhId()
	{
		return kenhId;
	}
	public void setKenhId(String kenhId)
	{
		this.kenhId = kenhId;
	}
	public String getVungId()
	{
		return vungId;
	}
	public void setVungId(String vungId)
	{
		this.vungId = vungId;
	}
	public String getKhuvucId()
	{
		return khuvucId;
	}
	public void setKhuvucId(String khuvucId)
	{
		this.khuvucId = khuvucId;
	}
	public String getNppId()
	{
		return nppId;
	}
	public void setNppId(String nppId)
	{
		this.nppId = nppId;
	}
	public ResultSet getKenhRs()
	{
		return kenhRs;
	}
	public void setKenhRs(ResultSet kenhRs)
	{
		this.kenhRs = kenhRs;
	}
	public ResultSet getVungRs()
	{
		return vungRs;
	}
	public void setVungRs(ResultSet vungRs)
	{
		this.vungRs = vungRs;
	}
	public ResultSet getKhuvucRs()
	{
		return khuvucRs;
	}
	public void setKhuvucRs(ResultSet khuvucRs)
	{
		this.khuvucRs = khuvucRs;
	}
	public ResultSet getNppRs()
	{
		return nppRs;
	}
	public void setNppRs(ResultSet nppRs)
	{
		this.nppRs = nppRs;
	}

	
	
	public boolean Save() 
	{
		System.out.print("[Save]");
		
		String	query = "select COUNT(*) AS SODONG from NHOMKHACHHANGNPP where ten=N'"+this.Ten+"'";
		if(this.Id.length()>0)
			query+=" and pk_seq!='"+this.Id+"'";
		ResultSet rsCheck=this.db.get(query);
		try
		{
			while(rsCheck.next())
			{
				if(rsCheck.getInt(1)>0)
				{
					this.Msg="Tên nhóm này đã có,vui lòng nhập diễn giải khác !";
					return false;
				}
			}
			if(rsCheck!=null)rsCheck.close();
		} catch (SQLException e1)
		{
			e1.printStackTrace();
		}
			
		try
		{
			this.db.getConnection().setAutoCommit(false);
				query ="insert into NHOMKHACHHANGNPP values(N'"+ this.Ten +"',N'"+ this.Diengiai +"','"+ getDateTime() +"','"+ getDateTime()+"','"+ this.userId +"','"+ this.userId +"','"+ this.Trangthai +"')";
			
			if(!this.db.update(query))
			{
				geso.dms.center.util.Utility.rollback_throw_exception(this.db);
				this.Msg="Lỗi hệ thống "+query;
				return false;
			}
			query = "select scope_identity() as dmhId";
			
			ResultSet rsDmh = db.get(query);						
			if(rsDmh.next())
			{
				this.Id = rsDmh.getString("dmhId");
				rsDmh.close();
			}
			
			if(this.action.equals("upload"))
			{
				
				query=
					 " select * from ("+this.khMaUpload+") up where not exists (select 1 from khachhang where smartId = up.makh )	";
				ResultSet checkSql = db.get(query);
				String khLoi = "";
				while(checkSql.next())
					khLoi =khLoi+","+checkSql.getString("makh");
				checkSql.close();
				
				if(khLoi.trim().length() > 0)
				{
					geso.dms.center.util.Utility.rollback_throw_exception(this.db);
					this.Msg="Ma kh khong co trong hệ thống :"+khLoi;
					return false;
				}
				
				
				query=
					"	INSERT INTO CTKM_KHACHHANG(NHOMKHNPP_FK, KHACHHANG_FK) "+
					"  SELECT '"+this.Id+"' ,PK_SEQ "+  
					"  FROM KHACHHANG WHERE smartId IN ("+this.khMaUpload+") ";
				
				System.out.print("[Save]"+query);
				if(this.db.updateReturnInt(query) <= 0)
				{
					geso.dms.center.util.Utility.rollback_throw_exception(this.db);
					this.Msg="Lỗi hệ thống "+query;
					return false;
				}
			}
			else
			{
				query=
					"	INSERT INTO CTKM_KHACHHANG(NHOMKHNPP_FK, KHACHHANG_FK) "+
					"  SELECT '"+this.Id+"' ,PK_SEQ "+  
					"  FROM KHACHHANG WHERE PK_SEQ IN ("+this.khId+") ";
				if(this.db.updateReturnInt(query) <= 0)
				{
					geso.dms.center.util.Utility.rollback_throw_exception(this.db);
					this.Msg="Lỗi hệ thống "+query;
					return false;
				}
			}
			
			
			
			
			
		this.db.getConnection().commit();
		this.db.getConnection().setAutoCommit(true);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return true;
	}

	public boolean Update()
	{
		String 	query=
		" select COUNT(*) AS SODONG  from NHOMKHACHHANGNPP where ten=N'"+this.Ten+"'";
		if(this.Id.length()>0)
			query+=" and pk_seq!='"+this.Id+"'";
		ResultSet rsCheck=this.db.get(query);
		System.out.println("[Check]"+query);
		try
		{
			while(rsCheck.next())
			{
				if(rsCheck.getInt(1)>0)
				{
					this.Msg="Tên nhóm này đã có,vui lòng nhập diễn giải khác !";
					return false;
				}
			}
			if(rsCheck!=null)rsCheck.close();
		} catch (SQLException e1)
		{
			e1.printStackTrace();
		}
		System.out.println("[Edit]"+query);
		try
		{
			this.db.getConnection().setAutoCommit(false);
			query ="update NHOMKHACHHANGNPP set ten = N'"+ this.Ten +"',diengiai = N'"+ this.Diengiai +"',ngaysua = '"+ getDateTime()+"',nguoisua = '"+ this.userId +"',trangthai = '"+ this.Trangthai +"' where pk_seq ='"+ this.Id +"'";
			if(!this.db.update(query))
			{ 
				geso.dms.center.util.Utility.rollback_throw_exception(this.db);
				this.Msg="Lỗi hệ thống "+query;
				return false;
			}
			System.out.println("[Edit]"+query);
			query=" delete a from CTKM_KHACHHANG a  where NHOMKHNPP_FK= '"+this.Id+"'"+
					  "	and not exists (select 1			from ctkhuyenmai ctkm "+ 
					  "	inner join donhang_CTKM_trakm dhkm on dhkm.ctkmId =  ctkm.PK_SEQ "+
					  "	inner join DONHANG dh on dh.PK_SEQ = dhkm.DONHANGID "+
					  "	where ctkm.NHOMKHNPP_FK = a.NHOMKHNPP_FK and a.KHACHHANG_FK = dh.KHACHHANG_FK "+
															 "	and dh.TRANGTHAI <> 2  ) ";
			System.out.println("[Edit]"+query);

			if(!this.db.update(query))
			{
				geso.dms.center.util.Utility.rollback_throw_exception(this.db);
				this.Msg="Lỗi hệ thống "+query;
				return false;
			}
			System.out.println("[Edit]"+query);
			if(this.action.equals("upload"))
			{
				
				query=
					 " select * from ("+this.khMaUpload+") up where not exists (select 1 from khachhang where smartId = up.makh )	";
				ResultSet checkSql = db.get(query);
				String khLoi = "";
				while(checkSql.next())
					khLoi =khLoi+","+checkSql.getString("makh");
				checkSql.close();
				
				if(khLoi.trim().length() > 0)
				{
					geso.dms.center.util.Utility.rollback_throw_exception(this.db);
					this.Msg="Ma kh khong co trong hệ thống :"+khLoi;
					return false;
				}
				
				
				query=
					"	INSERT INTO CTKM_KHACHHANG(NHOMKHNPP_FK, KHACHHANG_FK) "+
					"  SELECT '"+this.Id+"' ,PK_SEQ "+  
					"  FROM KHACHHANG WHERE smartId IN ("+this.khMaUpload+") AND PK_SEQ NOT IN (SELECT KHACHHANG_FK FROM CTKM_KHACHHANG WHERE NHOMKHNPP_FK="+this.Id+") ";
				 
				System.out.println("[Save]"+query);
				if(this.db.updateReturnInt(query) <= 0)
				{
					geso.dms.center.util.Utility.rollback_throw_exception(this.db);
					this.Msg="Lỗi hệ thống "+query;
					return false;
				}
			}
			else
			{
				query=
						"	delete CTKM_KHACHHANG where NHOMKHNPP_FK='"+this.Id+"' and KHACHHANG_fK  IN ("+this.khId+")";
				System.out.println(query);
				if(!this.db.update(query))
					{
						geso.dms.center.util.Utility.rollback_throw_exception(this.db);
						this.Msg="Lỗi hệ thống "+query;
						return false;
					}
				query=
					"	INSERT INTO CTKM_KHACHHANG(NHOMKHNPP_FK, KHACHHANG_FK) "+
					"  SELECT '"+this.Id+"' ,PK_SEQ "+  
					"  FROM KHACHHANG WHERE PK_SEQ IN ("+this.khId+") ";
				System.out.println(query);
				if(this.db.updateReturnInt(query) <= 0)
				{
					geso.dms.center.util.Utility.rollback_throw_exception(this.db);
					this.Msg="Lỗi hệ thống "+query;
					return false;
				}
			}
			
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		} catch (Exception e)
		{
			geso.dms.center.util.Utility.rollback_throw_exception(this.db);
			this.Msg="Lỗi hệ thống "+e.getMessage();
			e.printStackTrace();
		}
		return true;
	}

	public void setTen(String Ten) {
		
		this.Ten = Ten;
		
	}

	public String getTen() {
      		
		return this.Ten;
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
	public String getDdkdId()
	{
		return ddkdId;
	}
	public void setDdkdId(String ddkdId)
	{
		this.ddkdId = ddkdId;
	}
	public ResultSet getDdkdRs()
	{
		return ddkdRs;
	}
	public void setDdkdRs(ResultSet ddkdRs)
	{
		this.ddkdRs = ddkdRs;
	}
	
	@Override
	public void createRs()
	{
		String query=" select TEN ,PK_SEQ from KHUVUC ";
		this.khuvucRs=this.db.get(query);
		query="select ten,PK_SEQ  from VUNG ";
		this.vungRs=this.db.get(query);
		query="select PK_SEQ ,TEN  from KENHBANHANG ";
		this.kenhRs=this.db.get(query);
	}
	public void createNppRs()
	{
		String	query="select PK_SEQ,manpp MA,TEN  from NHAPHANPHOI npp where 1=1 ";
		if(this.kenhId.length()>0)
		{
			query += " and npp.pk_seq in ( select NPP_FK from NHAPP_KBH  where kbh_fk in("+this.kenhId+" )) ";
		}
		if(this.vungId.length()>0)
		{
			query += " and npp.KhuVuc_FK in (   select pk_seq from khuvuc where vung_fk in ( "+this.vungId+") ) ";
		}
		if(this.khuvucId.length()>0)
		{
			query += " and npp.KhuVuc_FK in ( "+this.khuvucId+" ) ";
		}
		
		if(this.nppId.length()>0 )
		{
			query+=" and npp.pk_seq not in ("+this.nppId+") union all select PK_SEQ,MA,TEN  from NHAPHANPHOI npp where npp.pk_seq in ("+this.nppId+") ";
		}
		
		System.out.println("[Npp]"+query);
		this.nppRs=this.db.get(query);
	}
	
	public void createDdkdRs()
	{
		String query=
		"select   ddkd.PK_SEQ,ddkd.TEN , ddkd.DIACHI , ddkd.smartId  as Ma,npp.MA as nppMa,npp.TEN  as nppTen "+
		" 	from daidienkinhdoanh ddkd inner join NHAPHANPHOI npp on npp.PK_SEQ=ddkd.NPP_FK " +
		" where 1=1 ";
		if(this.nppId.length()>0)
		{
			query+= " and ddkd.npp_fk in ("+this.nppId+") ";
		}
		if(this.ddkdId.length()>0 )
		{
			query+=" and ddkd.pk_seq not in ("+this.ddkdId+") union all" +
			" select   ddkd.PK_SEQ,ddkd.TEN , ddkd.DIACHI , ddkd.smartId  as Ma,npp.MA as nppMa,npp.TEN  as nppTen "+
			" 	from daidienkinhdoanh ddkd inner join NHAPHANPHOI npp on npp.PK_SEQ=ddkd.NPP_FK " +
			" where 1=1 and ddkd.pk_seq in ("+this.ddkdId+") ";
		}
		
		System.out.println("[createDdkdRs]"+query);
		this.ddkdRs=this.db.get(query);
	}	
	
	public void createKhRs()
	{
		String query=
			"	select kh.PK_SEQ,kh.smartId as khMa ,kh.TEN,kh.DIACHI,kh.DIENTHOAI,npp.MA as nppMa ,npp.TEN as nppTen "+
			"	from KHACHHANG "+
			"	kh inner join NHAPHANPHOI npp on npp.PK_SEQ=kh.NPP_FK " +
			"  where 1=1  					";
		if(this.nppId.length()>0)
		{
			query+="  and kh.npp_fk in ("+this.nppId+")";
		}
		if(this.ddkdId.length() > 0)
			query+= " AND kh.pk_seq in ( select KHACHHANG_FK from KHACHHANG_TUYENBH WHERE TBH_FK IN( SELECT PK_SEQ FROM TUYENBANHANG WHERE DDKD_FK IN ("+this.ddkdId+") )) ";
		
		if(this.khId.length()>0 )
		{
			query+=
				" and kh.pk_Seq not in ("+this.khId+") union all " +
				"	select kh.PK_SEQ,kh.smartId as khMa ,kh.TEN,kh.DIACHI,kh.DIENTHOAI,npp.MA as nppMa ,npp.TEN as nppTen "+
				"	from KHACHHANG "+
				"	kh inner join NHAPHANPHOI npp on npp.PK_SEQ=kh.NPP_FK " +
				"  where 1=1 and kh.pk_seq in ("+this.khId+") ";
		}
		
		System.out.println("[createKhRs]"+query);
		this.khRs=this.db.get(query);
	}
	
	public void setActive(String active) 
	{
		this.active = active;
	}

	public String getActive()
	{
		return this.active;
	}
	
	public String getKhMaUpload() {
		return khMaUpload;
	}
	public void setKhMaUpload(String khMaUpload) {
		this.khMaUpload = khMaUpload;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	
}
