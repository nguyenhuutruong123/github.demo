package geso.dms.center.beans.dieuchuyenkhuyenmai.imp;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;



import geso.dms.center.beans.dieuchuyenkhuyenmai.IDieuchuyenkhuyenmai;
import geso.dms.center.db.sql.dbutils;

public class Dieuchuyenkhuyenmai implements IDieuchuyenkhuyenmai{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String userId;
	String schemeId;
	String scheme;
	String msg;
	String rest;
	String tieuchi;
	ResultSet schemeRS;
	ResultSet kv;
	String kvId;
	ResultSet vung;
	String vungId;
	ResultSet nppRS;
	dbutils db ;

	String tungay;
	String denngay;

	public Dieuchuyenkhuyenmai()
	{
		this.userId = "";
		this.schemeId = "";
		this.scheme = "";
		this.msg = "";
		this.rest = "0";
		this.tieuchi = "";
		this.kvId = "";
		this.vungId = "";
		this.nppRS = null;
		this.db = new dbutils();
		
		this.tungay = "";
		this.denngay = "";
	}
	
	List<Object> dataSearch = new ArrayList<Object>();
	public List<Object> getDataSearch() {
		return dataSearch;
	}
	public void setDataSearch(List<Object> dataSearch) {
		this.dataSearch = dataSearch;
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

	public String getScheme()
	{
		ResultSet rs = this.db.get("select scheme +'-' +  diengiai as scheme from ctkhuyenmai where pk_seq='" + this.schemeId + "'");
		try{
			rs.next();
			this.scheme = rs.getString("scheme");
		}catch(java.sql.SQLException e){}
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

	public ResultSet getNpp() 
	{
		return this.nppRS;
	}

	public void setNpp(ResultSet nppRS) 
	{
		this.nppRS = nppRS;
	}

	private ResultSet createSchemeRS()
	{
		String query = "select pk_seq, scheme +'-' +  diengiai as scheme  from ctkhuyenmai where LoaiNganSach=1 \n";
		this.dataSearch.clear();
		
		if(this.tungay.trim().length() > 0 || this.denngay.trim().length() > 0)
		{
			if(this.tungay.trim().length() > 0){
				query += " and tungay >=?  \n";
				this.dataSearch.add(this.tungay);
			}
			if(this.denngay.trim().length() > 0){
				query += " and denngay <= ? \n";
				this.dataSearch.add(this.denngay);
			}
		}
		else
		{
			query += " and tungay <=? and denngay >= ?  \n";
			this.dataSearch.add(getDateTime());
			this.dataSearch.add(getDateTime());
		}
		
		if(this.kvId.trim().length() > 0){
			query += " and pk_seq in ( select a.CTKM_FK from CTKM_NPP a inner join NHAPHANPHOI b on a.NPP_FK = b.PK_SEQ where b.KHUVUC_FK = ? ) \n";
			this.dataSearch.add(this.kvId);
		}
		if(this.vungId.trim().length() > 0){
			query += " and pk_seq in ( select a.CTKM_FK from CTKM_NPP a inner join NHAPHANPHOI b on a.NPP_FK = b.PK_SEQ inner join KHUVUC c on b.KHUVUC_FK = c.PK_SEQ where c.VUNG_FK = ? ) \n";
			this.dataSearch.add(this.vungId);
		}
		query += " order by DENNGAY desc";
		System.out.println("CTKM "+ query);
		ResultSet schemeRS = this.db.getByPreparedStatement(query, this.dataSearch);
		return schemeRS;
	}


	private ResultSet createKvRS()
	{
		ResultSet kvRS;
		
		String query = "select pk_seq, diengiai from khuvuc where pk_seq in (select khuvuc_fk from nhaphanphoi where trangthai='1' and pk_seq in (select npp_fk from phamvihoatdong where nhanvien_fk='" + this.userId +"'))" ;
		this.dataSearch.clear();

		if(this.vungId.trim().length() > 0){
			query += " and vung_fk = ? ";
			this.dataSearch.add(this.vungId);
		}
		kvRS =  this.db.getByPreparedStatement(query, this.dataSearch);
			
		System.out.println(query);	
		return kvRS;

	}

	private ResultSet createNppRS()
	{
		ResultSet nppRS = null;
		String query="";
		this.dataSearch.clear();
		if(schemeId.trim().length()>0){
			query= "select     kv.ten as kvTen ,v.ten as vungTen, a.ctkm_fk as ctkmId,c.loaingansach, c.scheme,c.diengiai,a.npp_fk as nppId,d.ma as nppMa,d.ten as nppTen,  "+ 
					"	case    "+
					"	when c.loaingansach=1 then b.NganSach "+    
					"	when c.loaingansach=0 then 99999999999 end as   NganSach , "+  		
					"	case when c.phanbotheodonhang =0 then   "+
					"		isnull((select sum(trakm.tonggiatri)   "+
					"				from donhang_ctkm_trakm  trakm   "+
					"					inner join donhang dh on dh.pk_seq=trakm.donhangid "+  
					"				where dh.npp_fk=a.npp_fk and trakm.ctkmid = a.ctkm_fk and dh.trangthai!=2 ),0) "+  		
					"	else isnull((select sum(trakm.soluong)  "+
					"					from donhang_ctkm_trakm  trakm "+  
					"						inner join donhang dh on dh.pk_seq=trakm.donhangid "+  
					"					where dh.npp_fk=a.npp_fk and trakm.ctkmid = a.ctkm_fk   "+ 
					"					and spma is not null and dh.trangthai !=2 ),0	)end  		"+
					"		as dasudung,0 as ConLai "+
					" from ctkm_npp a   "+
					"	left join phanbokhuyenmai b on b.ctkm_fk=a.ctkm_fk and a.npp_fk=b.npp_fk "+ 
					"	inner join ctkhuyenmai c on c.pk_seq=a.ctkm_fk  "+
					"	inner join nhaphanphoi d on d.pk_seq=a.npp_fk   " +
					"   left join khuvuc kv on kv.pk_Seq=d.khuvuc_fk" +
					" 	left join vung v on v.pk_Seq=kv.vung_fk															"+
					" where a.chon=1 and a.ctkm_Fk=? " ;
			this.dataSearch.add(this.schemeId);
		}
					if(kvId.length()>0)
					{
						query+= " and kv.pk_Seq= ? ";
						this.dataSearch.add(kvId);
					}
					query +=" order by kv.TEN,v.ten,d.ten  " ; 
		System.out.println("Câu hiển thị : "+query);
		nppRS=this.db.getByPreparedStatement(query, this.dataSearch);
		return nppRS;
	}


	public void init()
	{
		this.vung = this.createVungRS();
		this.kv = this.createKvRS();
		this.schemeRS = this.createSchemeRS();		
		this.nppRS = this.createNppRS();
	}



	private ResultSet createVungRS() 
	{
		ResultSet vungRS;
		vungRS =  this.db.get("select pk_seq, ma + ' - ' + ten as diengiai from vung where  trangthai = '1' ");
		System.out.println("select pk_seq, ma + ' - ' + ten as diengiai from vung where  trangthai = 1");	
		return vungRS;
	}
	public boolean save(HttpServletRequest request)
	{
		try
		{
			this.db.getConnection().setAutoCommit(false);

			String query="	insert into DieuChuyenKhuyenMai(CTKM_FK,UserId) "+
					" select '"+this.schemeId+"','"+this.userId+"'  " ;
			System.out.println("[DieuChuyenKhuyenMai]"+query);
			if(!this.db.update(query))
			{
				this.msg =" Lỗi phát sinh khi cập nhật thông tin "+query;
				this.db.getConnection().rollback();
				return false;
			}

			String id="";
			ResultSet rsDDH = db.get("select Scope_identity() as ID ");
			if(rsDDH.next())
			{
				id = rsDDH.getString("ID");
			}
			rsDDH.close();

			String sql="";
			Enumeration<String> keys = this.dieuchuyen.keys();
			while(keys.hasMoreElements())
			{
				String key = keys.nextElement();
				String value=this.dieuchuyen.get(key);
				String ngansach=value.split("__")[0];
				String dasudung=value.split("__")[1];
				String conlai=value.split("__")[2];
				String ngansachmoi=value.split("__")[3];
				String dieuchuyen=value.split("__")[4];
				sql ="select '"+key+"' as nppId,'"+ngansach+"' as ngansach,'"+dasudung+"' as dasudung,'"+ngansachmoi+"' as ngansachmoi,'"+dieuchuyen+"' as dieuchuyen,'"+conlai+"' as conlai "; 
				System.out.println("KEY____"+key);
				
				query=
						" insert into DieuChuyenKhuyenMai_ChiTiet(DCKM_FK,NPP_FK,CTKM_FK,NganSach,DaSuDung,ConLai,ngansachmoi,DieuChuyen)" +
								"select '"+id+"', nppId,'"+this.schemeId+"',NganSach,DaSuDung,ConLai,ngansachmoi,DieuChuyen from (" + sql + ") chietkhaudathang_sp ";
				System.out.println("[DieuChuyenKhuyenMai]"+query);
				if(!this.db.update(query))
				{
					this.msg =" Lỗi phát sinh khi cập nhật thông tin "+query;
					this.db.getConnection().rollback();
					return false;
				}
			}
		/*	query="select count(*) as numb from   DieuChuyenKhuyenMai_ChiTiet where DCKM_FK='"+id+"'  having sum(ngansachmoi)!= sum(NganSach) ";
			ResultSet rs=db.get(query);
			while(rs.next())
			{
				this.msg ="Vui lòng điều chuyển ngân sách trong mức giới hạn cho phép !";
				this.db.getConnection().rollback();
				return false;
			}*/
			query=
					" update b set b.NganSach=a.NganSachMoi  "+
							"		from  DieuChuyenKhuyenMai_ChiTiet a "+ 
							"			inner join PhanBoKhuyenMai b on b.npp_fk=a.npp_fk and a.ctkm_fk=b.ctkm_fk "+
							"		where a.dckm_fk='"+id+"' ";
			if(!this.db.update(query))
			{
				this.msg =" Lỗi phát sinh khi cập nhật thông tin "+query;
				this.db.getConnection().rollback();
				return false;
			}

			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		} catch (Exception e)
		{
			try 
			{
				this.db.getConnection().rollback();
			} catch (SQLException e1) 
			{
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return true;
	}

	public String getDateTime() 
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);	
	}

	@Override
	public void DBClose()
	{

		try
		{
			if(db!=null)
				db.shutDown();

			if(nppRS!=null)
				nppRS.close();

			if(kv!=null)
				kv.close();

			if(schemeRS!=null)
				schemeRS.close();
		} catch (SQLException e)
		{
			if(db!=null)
				db.shutDown();
			e.printStackTrace();
		}
	}


	Hashtable<String, String> dieuchuyen= new Hashtable<String, String>();
	@Override
	public Hashtable<String, String> getDieuchuyen() 
	{
		return dieuchuyen;
	}
	@Override
	public void setDieuchuyen(Hashtable<String, String> dieuchuyen) 
	{
		this.dieuchuyen=dieuchuyen;
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

	public ResultSet getVung() 
	{
		return this.vung;
	}

	public void setVung(ResultSet vung) 
	{
		this.vung = vung ;
	}

	public String getVungId() 
	{
		return this.vungId;
	}

	public void setVungId(String vungId) 
	{
		this.vungId = vungId;
	}

}
