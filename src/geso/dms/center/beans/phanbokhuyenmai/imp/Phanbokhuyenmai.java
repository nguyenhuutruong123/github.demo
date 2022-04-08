package geso.dms.center.beans.phanbokhuyenmai.imp;

import geso.dms.center.beans.phanbokhuyenmai.IPhanbokhuyenmai;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

public class Phanbokhuyenmai implements IPhanbokhuyenmai, Serializable
{
	
	private static final long serialVersionUID = 1L;
	String userId;
	String schemeId;
	String scheme;
	String msg;
	ResultSet schemeRS;
	ResultSet kv;
	String kvId;
	ResultSet vung;
	String vungId;
	private ResultSet nppRS;
	dbutils db ;
	
	String tungay;
	String denngay;
	ResultSet schemeKhongGhRS; //scheme khong gioi han ngan sach
	ResultSet phanboRs;
	String nppId,nppIdSearch,phanbo,loaingansach;
	
	Utility util;


	public String getLoaingansach()
	{
		return loaingansach;
	}

	public void setLoaingansach(String loaingansach)
	{
		this.loaingansach = loaingansach;
	}

	//xet ctkm co phai la ngan sach thoai mai, hoac phai phan bo.  0 = ko gioi han ngan sach, 1 = phan bo
	String flag;
	
	Hashtable<String, String> usedPro;
	public Phanbokhuyenmai()
	{
		this.userId = "";
		this.schemeId = "";
		this.scheme = "";
		this.msg = "";
		this.kvId = "";
		this.vungId = "";
		this.tungay = "";
		this.denngay = "";
		this.flag = "1"; //mac dinh la chuong trinh phan bo khuyen mai ( gioi han ngan sach )
		this.phanbo="";
		this.nppRS = null;
		this.loaingansach="";
		this.db = new dbutils();
		this.util = new Utility();
	}
	
	public String getUserId()
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;
	}

	public String getSchemeId()
	{
		return this.schemeId;
	}

	public void setSchemeId(String schemeId)
	{
		this.schemeId = schemeId;
	}
		
	public Hashtable<String, String> getusedPro()
	{
		return this.usedPro;
	}

	public void setusedPro(Hashtable<String, String> usedPro)
	{
		this.usedPro = usedPro;
	}

	public String getScheme()
	{
		ResultSet rs = this.db.get("select scheme from ctkhuyenmai where pk_seq='" + this.schemeId + "'");
		try
		{
			rs.next();
			this.scheme = rs.getString("scheme");
		}
		catch(Exception e){}
		return this.scheme;
	}

	public void setScheme(String scheme)
	{
		this.scheme = scheme;
	}

	public String getMessage()
	{
		return this.msg;
	}

	public void setMessage(String msg)
	{
		this.msg = msg;
	}

	public ResultSet getSchemeRS() 
	{
		return this.schemeRS;
	}

	public void setSchemeRS(ResultSet schemeRS) 
	{
		this.schemeRS = schemeRS;
	}

	public ResultSet getVung() 
	{
		return this.vung;
	}

	public void setVung(ResultSet vung) 
	{
		this.vung = vung;
	}

	public String getVungId() 
	{
		return this.vungId;
	}

	public void setVungId(String vungId) 
	{
		this.vungId = vungId;
	}
	
	public ResultSet getKv() 
	{
		return this.kv;
	}

	public void setKv(ResultSet khuvuc) 
	{
		this.kv = khuvuc;
	}

	public String getKvId() 
	{
		return this.kvId;
	}

	public void setKvId(String kvId) 
	{
		this.kvId = kvId;
	}

	String activeTab = "0";

	public String getActiveTab()
	{
		return activeTab;
	}
	public void setActiveTab(String activeTab)
	{
		this.activeTab = activeTab;
	}
	public void createSchemeRS()
	{
		String query=
		"SELECT  DISTINCT PK_SEQ, SCHEME, DIENGIAI, ISNULL(LOAINGANSACH, '1') AS LOAINGANSACH,TUNGAY,DENNGAY, A.NGAYSUA, CASE WHEN A.PK_SEQ IN (SELECT TOP(1) CTKM_FK "
		+ "FROM PHANBOKHUYENMAI WHERE CTKM_FK=A.PK_SEQ ) THEN 1 ELSE 0 END AS DAPHANBO FROM CTKHUYENMAI A  ";
		if(vungId.length()>0 |kvId.length()>0 ||nppId.length()>0||phanbo.length()>0)
    	{
	    	query+=
	    	"		inner join "+
	    	"		( "+
	    	"			select CTKM_FK "+
	    	"			from CTKM_NPP km  "+
	    	"				inner join NHAPHANPHOI npp on npp.PK_SEQ=km.NPP_FK	 "+
	    	"			where  1=1 and isnull(chon,0)=1  " ;
	    	if(nppId.length()>0)
	    	query+=
	    	" and  km.NPP_FK in ( "+nppId+" ) ";
	    	if(kvId.length()>0)
	    	query+=" and npp.KHUVUC_FK ="+kvId+"  ";
	    	if(vungId.length()>0)
	    	query+=" and npp.KHUVUC_FK in ( select pk_seq from KHUVUC where VUNG_FK="+vungId+" ) ";
	    	
	    	if(phanbo.equals("0"))
	    	{
		    	query+=
		    	"	and km.CTKM_FK not in ( select ctkm_fk from PHANBOKHUYENMAI where 1=1 " ;
		    	if(nppId.length()>0)
		    		query+=" and npp_fk ="+nppId+"  ";
		    	query+=" ) ";
	    	}else if(phanbo.equals("1"))
	    	{
	    		query+=
		    	"	and km.CTKM_FK  in ( select ctkm_fk from PHANBOKHUYENMAI where 1=1 " ;
		    	if(nppId.length()>0)
		    		query+=" and npp_fk ="+nppId+"  ";
		    	query+=" ) ";
	    	}
	    	query+=
	    	" ) pb on pb.CTKM_FK=a.PK_SEQ ";
    	}	
		
		// Hiển thị những CTMK đã duyệt
    	query+="	WHERE 1=1 and loaict <> '4' AND ISNULL(A.ISDUYET,0) = '1'  ";
			    
    	System.out.println("QR: "+query);	
    	String sql = "select count(distinct e.kbh_fk) as sokenh from CTKHUYENMAI a inner join CTKM_NPP d on a.PK_SEQ = d.CTKM_FK "+
				"inner join NHAPP_KBH e on d.NPP_FK = e.NPP_FK and a.PK_SEQ = d.CTKM_FK";
		int sokenh = 0;
		ResultSet rs = db.get(sql);
		if(rs != null)
		{
			try
			{
				rs.next();
				sokenh = rs.getInt("sokenh");
			}
			catch(Exception e){e.printStackTrace();}
		}
		if(sokenh < 2)
			query+=" and (select distinct e.kbh_fk from CTKM_NPP d inner join NHAPP_KBH e on d.NPP_FK = e.NPP_FK and a.PK_SEQ = d.CTKM_FK) in " +util.quyen_kenh(userId);
		
    	if (tungay.length()>0)
    	{
			query = query + " and a.tungay >= '" + tungay + " '";			
    	}
    	if (denngay.length()>0){
			query = query + " and a.denngay <= '" + denngay + " '";		
    	}
    	if(this.loaingansach.length()>0)
    	{	query+= " and loaingansach="+this.loaingansach+" "; }
    	query += " ORDER BY DAPHANBO, A.NGAYSUA DESC ";
    	System.out.println("[CTKM]"+query);
		this.schemeRS = this.db.get(query);		
		this.schemeKhongGhRS = this.db.get(query);			
	}
	
	private ResultSet createVungRS()
	{  
		ResultSet vungRS =  this.db.get("select pk_seq as vungId, diengiai as vungTen from vung where trangthai='1'");
		return vungRS;
	}
	
	
	private ResultSet createKvRS()
	{
		ResultSet kvRS;
		String query="select pk_seq as khuvucId, diengiai as khuvucTen from khuvuc where trangthai='1' ";
		if(this.vungId.length()>0)
			query+= "and vung_fk = '" + this.vungId + "'";
		
		kvRS=this.db.get(query);
		return kvRS;
	}

	private ResultSet createNppRS()
	{
		ResultSet nppRS = null;
		String query = "select PK_SEQ, MA, TEN FROM NHAPHANPHOI where 1=1";
		if(this.vungId.length() > 0)
			query += " and KHUVUC_FK IN (SELECT PK_SEQ FROM KHUVUC WHERE VUNG_FK = "+this.vungId+")";
		if(this.kvId.length() >0)
			query += " and KHUVUC_FK = " + this.kvId;
		System.out.println("chuoi co :"+ flag.trim());
		nppRS = this.db.get(query);
		return nppRS;
	}
	
	public void init()
	{
		this.vung = this.createVungRS();
		this.kv = this.createKvRS();
		this.nppRS = this.createNppRS();
	}
	
	public String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	public ResultSet getSchemeKoGioiHanRs()
	{
		return this.schemeKhongGhRS;
	}

	public void setSchemeKoGioiHanRS(ResultSet schemeKoghRS)
	{
		this.schemeKhongGhRS = schemeKoghRS;
	}

	public String getTungay()
	{
		return this.tungay;
	}

	public void setTungay(String tungay) 
	{
		this.tungay = tungay;
	}

	public String getDenngay() 
	{
		return this.denngay;
	}

	public void setDenngay(String denngay) 
	{
		this.denngay = denngay;
	}

	public String getFlag()
	{
		return this.flag;
	}

	public void setFlag(String flag) 
	{
		this.flag = flag;
	}
	public ResultSet getPhanboRs()
	{
		return phanboRs;
	}

	public void setPhanboRs(ResultSet phanboRs)
	{
		this.phanboRs = phanboRs;
	}
	
	public String getNppId()
	{
		return nppId;
	}

	public void setNppId(String nppId)
	{
		this.nppId = nppId;
	}

	public String getNppIdSearch()
	{
		return nppIdSearch;
	}

	public void setNppIdSearch(String nppIdSearch)
	{
		this.nppIdSearch = nppIdSearch;
	}
	
	@Override
	public void DBClose()
	{
		
			try
			{
				if(this.nppRS!=null)this.nppRS.close();
				if(this.schemeRS!=null)this.schemeRS.close();
				if(this.schemeKhongGhRS!=null)this.schemeKhongGhRS.close();
				if(this.kv!=null)this.kv.close();
				if(this.vung!=null)this.vung.close();
				if(this.nppRS!=null)this.nppRS.close();
				if(this.phanboRs!=null)this.phanboRs.close();
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
		
	}
	
	public String getPhanbo()
	{
		return phanbo;
	}

	public void setPhanbo(String phanbo)
	{
		this.phanbo = phanbo;
	}

	@Override
	public void createPhanBoRs()
	{
		if(this.schemeId.length()>0)
		{
			String query=
			"\n	select a.ctkm_fk,c.loaingansach, c.scheme,c.diengiai,a.npp_fk as nppId,d.ma as nppMa,d.ten as nppTen, "+
					"\n		case   "+
					"\n		when c.loaingansach=1 then b.NganSach    "+
					"\n		when c.loaingansach=0 then 99999999999 end as   NganSach ,  "+		
					"\n		case when c.phanbotheodonhang =0 then isnull(ss.tonggiatri,0)  else isnull(ss.soxuat,0) end as dasudung " +
					"\n		,0 as ConLai, isnull( e.SMARTID + ' - ' + e.TEN, '') as khTEN "+
					"\n	from ctkm_npp a  "+
					"\n		left join phanbokhuyenmai b on b.ctkm_fk=a.ctkm_fk and a.npp_fk=b.npp_fk "+
					"\n		inner join ctkhuyenmai c on c.pk_seq=a.ctkm_fk "+
					"\n		inner join nhaphanphoi d on d.pk_seq=a.npp_fk  "+
					"\n		left join KHACHHANG e on e.pk_seq=b.khachhang_fk  and a.npp_fk=e.npp_fk "+
					
					"\n outer apply " +
					"\n (	" +
					"\n		select sum(soxuat)soxuat, sum(tonggiatri)tonggiatri " +
					"\n		from  " +
					"\n 	(" +
					"\n			select dhkm.donhangId,max(soxuat) soxuat, sum(dhkm.tonggiatri) tonggiatri " +
					"\n			from donhang_ctkm_trakm dhkm inner join donhang dh on dh.pk_seq = dhkm.donhangId " +
					"\n			where dh.trangthai !=2 and dh.npp_fk =  a.npp_fk and dhkm.ctkmid = a.ctkm_fk and not exists ( select 1 from donhangtrave where trangthai = 3 and donhang_fk = dh.pk_seq) " +
					"\n			group by dhkm.donhangId " +
					"\n 	)ss " +
					"\n )ss	" +
					
					"\n	where a.chon=1  "; 
					
					
				 
			if(this.kvId.length()>0)
			{
				query += " and d.khuvuc_fk in ("+this.kvId+")";
			}
			if(this.vungId.length()>0)
			{
				query+=" and d.khuvuc_fk in (select pk_seq from khuvuc where vung_fk="+this.vungId+" )";
			}
			if(this.schemeId.length()>0)
			{
				query += " and c.pk_seq in ("+this.schemeId+") ";
			}
			if(this.loaingansach.length()>0)
				query+=" and c.loaingansach='"+this.loaingansach+"' ";
			
			if(this.nppIdSearch.length() > 0)
				query += " and a.npp_fk = " + this.nppIdSearch;
			this.phanboRs=this.db.get(query);
			System.out.println("[PHANBOKHUYENMAI]"+query);
		}
	}

	@Override
	public ResultSet getNppRs() {
		// TODO Auto-generated method stub
		return this.nppRS;
	}
	
	
}
