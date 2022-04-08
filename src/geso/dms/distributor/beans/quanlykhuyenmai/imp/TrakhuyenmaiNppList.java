package geso.dms.distributor.beans.quanlykhuyenmai.imp;

import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;

import geso.dms.center.util.Phan_Trang;
import geso.dms.distributor.beans.quanlykhuyenmai.ITrakhuyenmaiNppList;
import geso.dms.distributor.db.sql.dbutils;

public class TrakhuyenmaiNppList extends Phan_Trang implements ITrakhuyenmaiNppList 
{
	String userId;
	String tungay;
	String denngay;
	String scheme;
	String nppId;
	String nppTen;
	String sitecode;
	String msg;
	
	ResultSet tkmList;
	dbutils db;
	private int[] listPages;
	private int num;

	private int currentPages;

	private HttpServletRequest request;
	
	public TrakhuyenmaiNppList()
	{
		this.tungay = "";
		this.denngay = "";
		this.scheme = "";
		this.msg = "";
		this.currentPages = 1;
		this.num = 1;
		db = new dbutils();
	}
	
	public String getUserId() 
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;
	}

	public String getScheme() 
	{
		return this.scheme;
	}

	public void setScheme(String scheme) 
	{
		this.scheme = scheme;
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

	public String getNppId() 
	{
		return this.nppId;
	}

	public void setNppId(String nppId) 
	{
		this.nppId = nppId;
	}

	public String getNppTen() 
	{
		return this.nppTen;
	}

	public void setNppTen(String nppTen) 
	{
		this.nppTen = nppTen;
	}

	public String getSitecode() 
	{
		return this.sitecode;
	}

	public void setSitecode(String sitecode) 
	{
		this.sitecode = sitecode;
	}

	public ResultSet getTrakmList() 
	{
		return this.tkmList;
	}

	public void setTrakmList(ResultSet tkm) 
	{
		this.tkmList = tkm;
	}

	public void setMsg(String Msg) 
	{
		this.msg = Msg;
	}

	public String getMsg() 
	{
		return this.msg;
	}
	
	public void init(String search) 
	{
		String query = "";
		this.getNppInfo();
		if(search.length() == 0)
		{
			//query = "select a.pk_seq as ctkmId, a.SCHEME, a.diengiai as ctkmDiengiai, c.PK_SEQ as tkmId, c.DIENGIAI, c.HINHTHUC, a.TUNGAY, a.DENNGAY, isnull(d.thutuuutien, '0') as thutu from CTKHUYENMAI a inner join CTKM_TRAKM b on a.PK_SEQ = b.CTKHUYENMAI_FK inner join TRAKHUYENMAI c on b.TRAKHUYENMAI_FK = c.PK_SEQ  " + 
					//" left join TRAKM_NHAPP d on d.trakm_fk = a.pk_seq and d.ctkm_fk = c.PK_SEQ and d.npp_fk = '" + nppId + "' " +
					//" where c.LOAI = 3 and c.HINHTHUC = 2 and a.PK_SEQ in (select CTKHUYENMAI_FK from PHANBOKHUYENMAI where npp_fk = '" + this.nppId + "') and a.PK_SEQ in (select ctkm_fk from ctkm_npp where npp_fk = '" + this.nppId +"') and a.denngay >= (select max(ngayks) from khoasongay where npp_fk = '" + this.nppId + "')";
			query = "\n select a.pk_seq as ctkmId, a.SCHEME, a.diengiai as ctkmDiengiai, c.PK_SEQ as tkmId, c.DIENGIAI, c.HINHTHUC, a.TUNGAY, a.DENNGAY, isnull(d.trakm_fk, '0') as dathietlap " +
					"\n from CTKHUYENMAI a inner join CTKM_TRAKM b on a.PK_SEQ = b.CTKHUYENMAI_FK inner join TRAKHUYENMAI c " +
					"\n on b.TRAKHUYENMAI_FK = c.PK_SEQ  left join (select distinct trakm_fk, ctkm_fk, npp_fk from TRAKM_NHAPP where npp_fk = '" + nppId + "') d on d.ctkm_fk = a.pk_seq and d.trakm_fk = c.PK_SEQ " +
					"\n where c.LOAI = 3 and c.HINHTHUC = 2 and a.PK_SEQ in  " +
					"\n (select CTKHUYENMAI_FK from PHANBOKHUYENMAI where npp_fk = '" + nppId + "') and a.PK_SEQ in " +
					"\n (select ctkm_fk from ctkm_npp where npp_fk = '" + nppId + "') and a.denngay >= (select max(ngayks) from khoasongay where npp_fk = '" + nppId + "')";
			System.out.println("Init TKM: " + query);
		}
		else
			query = search;
		//System.out.println("Query la: " + query +  "\n");
		this.tkmList = createSplittingData(100, 10, "denngay desc", query);
	}

	private void getNppInfo()
	{	
		geso.dms.distributor.util.Utility util = new geso.dms.distributor.util.Utility();
		this.nppId=util.getIdNhapp(this.userId);
		this.nppTen=util.getTenNhaPP();
		//System.out.println(this.nppTen);
		this.sitecode=util.getSitecode();
	}
	
	public void DBclose()
	{
		try{
			if(tkmList!=null){
				tkmList.close();
			}
			
			if(db!=null){
				db.shutDown();
			}
			
			
		}catch(Exception er){
			
		}
		
	}

	public int getNum(){
		return this.num;
	}
	public void setNum(int num){
		this.num = num;
		listPages = new int[num];
		for(int i = 0; i < this.num; i++)
			listPages[i]=i+1;
	}
	

}
