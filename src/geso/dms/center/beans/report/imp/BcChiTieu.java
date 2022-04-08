package geso.dms.center.beans.report.imp;

import geso.dms.center.beans.report.IBcChiTieu;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Phan_Trang;
import geso.dms.center.util.Utility;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BcChiTieu  extends Phan_Trang implements IBcChiTieu, Serializable
{
	public BcChiTieu()
	{
		this.spId="";
		this.nppId="";
		this.userId="";
		this.khId="";
		this.msg="";
		this.ddkdId="";
		this.tuNgay="";
		this.denNgay="";
		this.kbhId="";
		this.ttId="";
		this.nhomId="";
		this.vungId="";
		this.loaiHoaDon="";
		this.action="";
		this.ctkmId="";
		this.kvId="";
		this.muclay="0";
		db = new dbutils();
	}
	/**
   * 
   */
  private static final long serialVersionUID = 1L;
	String tuNgay,denNgay,spId,nppId,ddkdId,userId,khId;
	public String getKhId()
  {
  	return khId;
  }
	public void setKhId(String khId)
  {
  	this.khId = khId;
  }
	public String getTuNgay()
  {
  	return tuNgay;
  }
	public void setTuNgay(String tuNgay)
  {
  	this.tuNgay = tuNgay;
  }
	public String getDenNgay()
  {
  	return denNgay;
  }
	public void setDenNgay(String denNgay)
  {
  	this.denNgay = denNgay;
  }
	public String getSpId()
  {
  	return spId;
  }
	public void setSpId(String spId)
  {
  	this.spId = spId;
  }
	public String getNppId()
  {
  	return nppId;
  }
	public void setNppId(String nppId)
  {
  	this.nppId = nppId;
  }
	public String getDdkdId()
  {
  	return ddkdId;
  }
	public void setDdkdId(String ddkdId)
  {
  	this.ddkdId = ddkdId;
  }
	public String getUserId()
  {
  	return userId;
  }
	public void setUserId(String userId)
  {
  	this.userId = userId;
  }
	public ResultSet getSpRs()
  {
  	return spRs;
  }
	public void setSpRs(ResultSet spRs)
  {
  	this.spRs = spRs;
  }
	public ResultSet getDdkdRs()
  {
  	return ddkdRs;
  }
	public void setDdkdRs(ResultSet ddkdRs)
  {
  	this.ddkdRs = ddkdRs;
  }
	ResultSet spRs,ddkdRs,khRs,hoadonRs;
	public ResultSet getHoadonRs()
  {
  	return hoadonRs;
  }
	public void setHoadonRs(ResultSet hoadonRs)
  {
  	this.hoadonRs = hoadonRs;
  }
	public ResultSet getKhRs()
  {
  	return khRs;
  }
	public void setKhRs(ResultSet khRs)
  {
  	this.khRs = khRs;
  }
	
	public void closeDB()
	{
		
	}
	
	dbutils db = new dbutils();
	public void createRs()
	{
		Utility util = new Utility();
		String query="select pk_Seq,ma,ten from sanpham where trangthai=1";
		this.spRs=this.db.get(query);
		
		query="select pk_seq,ten from DaiDienKinhDoanh a where 1=1  ";
		
		if(this.nppId.length()>0)
		{
			query+=" and a.npp_fk='"+this.nppId+"' ";
		}
		if(this.view.length()>0)
		{
		/*	query+=" and pk_Seq in  "+util.Quyen_Ddkd(this.userId)+" ";*/
		}
		this.ddkdRs = this.db.get(query);
		
		if(this.nppId.length()>0)
		{
			
			query="select pk_seq,isnull(smartId,'') +' ' + ten + ' ' + isnull(diachi,'') as Ten from khachhang where 1=1 ";
			if(this.view.length()>0)
			{
				query+=" and npp_fk in "+util.quyen_npp(userId);
			}
			
			if(this.nppId.length()>0)
			query+=" and npp_fk='"+this.nppId+"' ";
			
			this.khRs= this.db.get(query);
		}
		
		
	
		
		
		query="select pk_Seq,ten,DIENGIAI from KENHBANHANG where TRANGTHAI=1 ";
		this.kbhRs = this.db.get(query);	
		
		
		query="select PK_SEQ,TEN from tinhthanh   where 1=1 ";
		if(vungId.length()>0)
			query+=" and vung_fk='"+vungId+"'";
		
		this.ttRs= this.db.get(query);
		
		query="select pk_seq ,ten,VUNG_FK from khuvuc  where 1=1 ";
		this.kvRs=this.db.get(query);
		
		query="select pk_seq,ten from vung where 1=1 ";
		this.vungRs=this.db.get(query);
		
		
		query="select pk_seq,ma,diachi,ten from nhaphanphoi where trangthai=1 ";
		if(this.view.length()>0)
		{
			query+="and pk_Seq in "+util.quyen_npp(userId)+"" ;
		}
		if(this.ttId.length()>0)
		query+=" and tinhthanh_Fk='"+this.ttId+"' ";
		
		if(this.vungId.length()>0)
			query+=" and khuvuc_fk in (select pk_seq from khuvuc where vung_fk='"+this.vungId+"' ) ";		
		this.nppRs=this.db.get(query);
		
		query="select pk_Seq,SCHEME,DIENGIAI as TEN,TUNGAY,DENNGAY,KHO_FK from ctkhuyenmai";
		
		if(this.tuNgay.length()>0)
			query+=" and TuNgay>='"+this.tuNgay+"'";
		
		if(this.denNgay.length()>0)
			query+=" and DenNgay<='"+this.denNgay+"'";
		
		if(this.nppId.length()>0)
		{
			query+= " and pk_seq in (select ctkm_fk from ctkm_npp where npp_fk='"+this.nppId+"')";
		}
		this.ctkmRs=this.db.get(query);
		
		
	}
	
	String queryHd="";
	public String getQueryHd()
	{
		return queryHd;
	}
	public void setQueryHd(String query)
	{
		this.queryHd=query;
	}
	Utility Ult = new Utility();
	public void init(String search)
	{
		String query;
		
		String condition="";  
		if(this.ctkmId.length()>0)
		{
			condition+=" and a.ctkm_Fk='"+this.ctkmId+"' ";
		}
		
		if(this.nppId.length()>0)
		{
			condition+=" and a.npp_fk='"+this.ctkmId+"' ";
		}
		
		if(this.kvId.length()>0)
		{
			condition+=" and b.khuvuc_fk='"+this.kvId+"' ";
		}
		
		
	   query="";
	   if(this.action.length()>0)
	   {
				query=
						" WITH KM_CTE (CTKM_FK,NPP_FK,SoLuong_KM,TrongLuong_KM,SoLuong_BAN,TrongLuong_BAN)   \n "+      
						"   AS   \n "+      
						"   (   \n "+      
						"   select isnull(km.ctkmid,ban.ctkmId) as ctKmId,   \n "+      
						"   		isnull(km.npp_fk,ban.NPP_FK) as nppId,   \n "+      
						"   		km.soluong as soluong_km,km.trongluong as trongluong_km,ban.soluong as soluong_ban,ban.trongluong as trongluong_ban   \n "+      
						"   from    \n "+      
						"   (   \n "+      
						"   	select a.npp_fk,b.ctkmid,sum(b.soluong) as soluong,sum(b.soluong*c.trongluong) as trongluong   \n "+      
						"   	from donhang a inner join donhang_ctkm_trakm b on b.donhangid=a.pk_seq   \n "+      
						"   		inner join sanpham c on c.ma=b.spma   \n "+      
						"   	where a.trangthai!=2 and b.soluong!=0   \n "+      
						"   	group by a.npp_fk,b.ctkmid   \n "+      
						"   )as km full outer join    \n "+      
						"   (   \n "+      
						"   	select a.npp_fk,d.ctkhuyenmai_fk as ctkmId,sum(b.soluong) as soluong,sum(b.soluong*f.trongluong) as trongluong   \n "+      
						"   	from donhang a inner join donhang_sanpham b on b.donhang_fk=a.pk_seq   \n "+      
						"   	inner join    \n "+      
						"   	(   \n "+      
						"   		select donhangid ,ctkmid,sum(soluong) as soluong   \n "+      
						"   		from donhang_ctkm_trakm    \n "+      
						"   		where spma is not null   \n "+      
						"   		group by donhangid,ctkmid   \n "+      
						"   	)c on c.donhangid=b.donhang_fk   \n "+      
						"   		inner join ctkm_dkkm d on d.ctkhuyenmai_fk=c.ctkmid   \n "+      
						"   		inner join dieukienkm_sanpham e on e.dieukienkhuyenmai_fk=d.dkkhuyenmai_fk and e.sanpham_fk=b.sanpham_fk   \n "+      
						"   		inner join sanpham f on f.pk_seq=e.sanpham_fk   \n "+      
						"   	where a.trangthai!=2   \n "+      
						"   	group by a.npp_fk,d.ctkhuyenmai_fk   \n "+      
						"   ) as ban on ban.npp_fk=km.npp_fk and ban.ctkmId=km.ctkmid   \n "+      
						"   )   \n "+      
						"      \n "+      
						"   SELECT a.CTKM_FK,d.DIENGIAI as kmDIENGIAI,d.SCHEME,c.TEN as kvTEN,b.MA as nppMA,b.TEN as nppTEN,a.SoLuong_BAN,a.TrongLuong_BAN,SoLuong_KM,TrongLuong_KM,   \n "+      
						"   	0 as STT   \n "+      
						" ,COUNT(c.ten)  OVER(PARTITION BY a.CTKM_FK,c.ten ) AS Row "+
						"   FROM KM_CTE a INNER JOIN NHAPHANPHOI b on a.NPP_FK=b.PK_SEQ   \n "+      
						"   	inner join KHUVUC c on c.PK_SEQ=b.KHUVUC_FK   \n "+      
						"   	inner join CTKHUYENMAI d on d.PK_SEQ=a.CTKM_FK   \n "+      
						"   WHERE 1=1 "+condition+"   \n "+      
						"      \n "+      
						"   union all   \n "+      
						"      \n "+      
						"   SELECT a.CTKM_FK,d.DIENGIAI as kmDIENGIAI,d.SCHEME,c.TEN  +' Total' as kvTEN ,'' as nppMA,'' as nppTEN,   \n "+      
						"   	sum(a.SoLuong_BAN) as SoLuong_BAN,sum(a.TrongLuong_BAN) as TrongLuong_BAN,   \n "+      
						"   	sum(SoLuong_KM) as SoLuong_KM,sum(TrongLuong_KM) as TrongLuong_KM,1 AS STT   \n "+     
						" ,COUNT(c.ten)  OVER(PARTITION BY a.CTKM_FK,c.ten ) AS Row"+ 
						"   FROM KM_CTE a INNER JOIN NHAPHANPHOI b on a.NPP_FK=b.PK_SEQ   \n "+      
						"   	inner join KHUVUC c on c.PK_SEQ=b.KHUVUC_FK   \n "+      
						"   	inner join CTKHUYENMAI d on d.PK_SEQ=a.CTKM_FK   \n "+      
						"   WHERE 1=1 "+condition+"   \n "+    
						"   group by a.CTKM_FK,d.DIENGIAI ,d.SCHEME,c.TEN     \n "+      
						"      \n "+      
						"   --union all    \n "+      
						"   --SELECT a.CTKM_FK ,d.DIENGIAI +'__Total' as kmDIENGIAI,SCHEME + '__Total'  AS SCHEME,'' as kvTEN ,'' as nppMA,'' as nppTEN,   \n "+      
						"   --	sum(a.SoLuong_BAN) as SoLuong_BAN,sum(a.TrongLuong_BAN) as TrongLuong_BAN,   \n "+      
						"   --	sum(SoLuong_KM) as SoLuong_KM,sum(TrongLuong_KM) as TrongLuong_KM,2 AS STT   \n "+      
						"   --FROM KM_CTE a INNER JOIN NHAPHANPHOI b on a.NPP_FK=b.PK_SEQ   \n "+      
						"   --	inner join KHUVUC c on c.PK_SEQ=b.KHUVUC_FK   \n "+      
						"   --	inner join CTKHUYENMAI d on d.PK_SEQ=a.CTKM_FK   \n "+      
						"   -- WHERE 1=1 "+condition+"   \n "+     
						"   --group by d.DIENGIAI ,D.SCHEME ,A.CTKM_FK   \n ";
					this.queryHd=query+" ORDER BY CTKM_FK DESC,kvTEN , STT,b.MA ";
				
					String spKmId__="";
					String spKmTEN__="";
					String spKmMa__="";
					condition ="";
					if(ctkmId.length()>0)
					{
						condition+= " and b.ctkmId in ("+ctkmId+")" ;
					}
					
					query=
							"	select c.PK_SEQ as SANPHAM_FK,c.ma as spMa,c.ten as spTen  "+   
							"	from donhang a inner join donhang_ctkm_trakm b on b.donhangid=a.pk_seq  "+   
							"		inner join sanpham c on c.ma=b.spma     "+
							"	where a.trangthai!=2 and b.soluong!=0  "+
							"	group by  c.PK_SEQ,c.ma,c.ten ";
						ResultSet rs=db.get(query);
						try
						{
						    while(rs.next())
						    {
						    	spKmId__= rs.getString("SANPHAM_FK");
						    	spKmTEN__ = rs.getString("spTen");
						    	spKmMa__ = rs.getString("spMa");
						    }
						   if(spKmId__.length()>0)
						   {
						  	 spKmId=spKmId__.split("__#__");
						  	 spKmTen=spKmTEN__.split("__#__");
						  	 spKmMa=spKmMa__.split("__#__");
						   }
						   if(rs!=null)rs.close();
					    } catch (SQLException e)
					    {
						    e.printStackTrace();
					    }
						
						
						query=
						"		select  b.SANPHAM_FK,f.ma as spMa,f.ten as spTen   "+
						"			from donhang a inner join donhang_sanpham b on b.donhang_fk=a.pk_seq "+   
						"			inner join "+    
						"			(    "+
						"				select donhangid ,ctkmid,sum(soluong) as soluong "+   
						"				from donhang_ctkm_trakm     "+
						"				where spma is not null    "+
						"				group by donhangid,ctkmid    "+
						"			)c on c.donhangid=b.donhang_fk   "+
						"				inner join ctkm_dkkm d on d.ctkhuyenmai_fk=c.ctkmid    "+
						"				inner join dieukienkm_sanpham e on e.dieukienkhuyenmai_fk=d.dkkhuyenmai_fk and e.sanpham_fk=b.sanpham_fk "+   
						"				inner join sanpham f on f.pk_seq=e.sanpham_fk "+   
						"			where a.trangthai!=2   "+
						"			group by b.SANPHAM_FK,f.ten,f.ma  "; ;
							rs=db.get(query);
							try
							{
							    while(rs.next())
							    {
							    	spKmId__= rs.getString("SANPHAM_FK");
							    	spKmTEN__ = rs.getString("spTen");
							    	spKmMa__ = rs.getString("spMa");
							    }
							   if(spKmId__.length()>0)
							   {
							  	 spBanId=spKmId__.split("__#__");
							  	 spBanMa=spKmTEN__.split("__#__");
							  	 spBanTen=spKmMa__.split("__#__");
							   }
							   if(rs!=null)rs.close();
						    } catch (SQLException e)
						    {
							    e.printStackTrace();
						    }		
					
					
	   }
	   /*System.out.println(":::::::"+query);*/
		createRs();
		/*setTotal_Query(this.queryHd);*/
		
	
		
	}
	
	String[] spKmId,spKmTen,spKmMa;
	String[] spBanId,spBanTen,spBanMa;
	
	String view,msg;
	public String getMsg()
  {
  	return msg;
  }
	public void setMsg(String msg)
  {
  	this.msg = msg;
  }
	public String getView()
  {
  	return view;
  }
	public void setView(String view)
  {
  	this.view = view;
  }
	
	String kbhId;
	ResultSet kbhRs;
	public String getKbhId()
  {
  	return kbhId;
  }
	public void setKbhId(String kbhId)
  {
  	this.kbhId = kbhId;
  }
	public ResultSet getKbhRs()
  {
  	return kbhRs;
  }
	public void setKbhRs(ResultSet kbhRs)
  {
  	this.kbhRs = kbhRs;
  }
	
	public void setTotal_Query(String searchquery) 
	{
	   /*
	    * 
	    * Lấy HD Chi nhánh cấp 1,Chi nhánh cấp 2,Quầy bán buôn,Quầy Traphaco
	    * 
	    */
	   String query="";
	   if(this.action.length()>0)
	   {
						query=
									 " select SUM(SoLuong)as SoLuong,AVG(DonGia) as DonGia,SUM(AVAT)as AVAT,SUM(BVAT)as BVAT,SUM(VAT)as VAT      \n "+      
										"   from      \n "+      
										"   (      \n "+      
										" "+searchquery+" "+
 										"   ) as HD      \n ";
	
				this.totalRs=this.db.get(query);
	   }
	}
	
	
	ResultSet totalRs;
	public ResultSet getTotalRs()
	{
		return totalRs;
	}
	public void setTotalRs(ResultSet totalRs)
	{
		this.totalRs=totalRs;
	}
	
	String vungId,nhomId,ttId,kvId;
	ResultSet vungRs,nhomRs,ttRs,kvRs;
	public String getKvId()
  {
  	return kvId;
  }
	public void setKvId(String kvId)
  {
  	this.kvId = kvId;
  }
	public ResultSet getKvRs()
  {
  	return kvRs;
  }
	public void setKvRs(ResultSet kvRs)
  {
  	this.kvRs = kvRs;
  }
	public String getVungId()
  {
  	return vungId;
  }
	public void setVungId(String vungId)
  {
  	this.vungId = vungId;
  }
	public String getNhomId()
  {
  	return nhomId;
  }
	public void setNhomId(String nhomId)
  {
  	this.nhomId = nhomId;
  }
	public String getTtId()
  {
  	return ttId;
  }
	public void setTtId(String ttId)
  {
  	this.ttId = ttId;
  }
	public ResultSet getVungRs()
  {
  	return vungRs;
  }
	public void setVungRs(ResultSet vungRs)
  {
  	this.vungRs = vungRs;
  }
	public ResultSet getNhomRs()
  {
  	return nhomRs;
  }
	public void setNhomRs(ResultSet nhomRs)
  {
  	this.nhomRs = nhomRs;
  }
	public ResultSet getTtRs()
  {
  	return ttRs;
  }
	public void setTtRs(ResultSet ttRs)
  {
  	this.ttRs = ttRs;
  }
	
	ResultSet nppRs;
	@Override
  public ResultSet getNppRs()
  {

	  return nppRs;
  }
	@Override
  public void setNppRs(ResultSet nppRs)
  {
		this.nppRs=nppRs;
	  
  }
	
	String loaiHoaDon;
	public String getLoaiHoaDon()
  {
  	return loaiHoaDon;
  }
	public void setLoaiHoaDon(String loaiHoaDon)
  {
  	this.loaiHoaDon = loaiHoaDon;
  }

	
	String action,ctkmId;
	public String getAction()
  {
  	return action;
  }
	public void setAction(String timkiem)
  {
  	this.action = timkiem;
  }
	
	ResultSet ctkmRs;
	public String getCtkmId()
  {
  	return ctkmId;
  }
	public void setCtkmId(String ctkmId)
  {
  	this.ctkmId = ctkmId;
  }
	public ResultSet getCtkmRs()
  {
  	return ctkmRs;
  }
	public void setCtkmRs(ResultSet ctkmRs)
  {
  	this.ctkmRs = ctkmRs;
  }
	public String[] getSpKmId()
  {
  	return spKmId;
  }
	public void setSpKmId(String[] spKmId)
  {
  	this.spKmId = spKmId;
  }
	public String[] getSpKmTen()
  {
  	return spKmTen;
  }
	public void setSpKmTen(String[] spKmTen)
  {
  	this.spKmTen = spKmTen;
  }
	public String[] getSpKmMa()
  {
  	return spKmMa;
  }
	public void setSpKmMa(String[] spKmMa)
  {
  	this.spKmMa = spKmMa;
  }
	public String[] getSpBanId()
  {
  	return spBanId;
  }
	public void setSpBanId(String[] spBanId)
  {
  	this.spBanId = spBanId;
  }
	public String[] getSpBanTen()
  {
  	return spBanTen;
  }
	public void setSpBanTen(String[] spBanTen)
  {
  	this.spBanTen = spBanTen;
  }
	public String[] getSpBanMa()
  {
  	return spBanMa;
  }
	public void setSpBanMa(String[] spBanMa)
  {
  	this.spBanMa = spBanMa;
  }
	
	
	String muclay;
	public String getMuclay()
  {
  	return muclay;
  }
	public void setMuclay(String muclay)
  {
  	this.muclay = muclay;
  }
	
}
