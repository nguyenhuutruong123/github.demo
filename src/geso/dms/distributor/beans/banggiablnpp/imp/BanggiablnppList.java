package geso.dms.distributor.beans.banggiablnpp.imp;

import geso.dms.center.util.Phan_Trang;
import geso.dms.distributor.db.sql.*;
import geso.dms.distributor.util.Utility;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import geso.dms.distributor.beans.banggiablnpp.*;

public class BanggiablnppList extends Phan_Trang implements IBanggiablnppList
{
List<Object> dataSearch = new ArrayList<Object>(); 
	
	public List<Object> getDataSearch() {
		return dataSearch;
	}
	public void setDataSearch(List<Object> dataSearch) {
		this.dataSearch = dataSearch;
	}
	private static final long serialVersionUID = -9217977546733610214L;
	String msg = "";
	String userId; //nppId
	
	String tenbanggia;
	
	ResultSet nhacungcap;
	String nccId;
		
	ResultSet donvikinhdoanh;
	String dvkdId;
	
	ResultSet bgblList;
	String view="";
	public String getView() {
		return view;
	}

	public void setView(String view) {
		this.view = view;
	}
	String nppId;
	String nppTen;
	String sitecode;
	dbutils db;
	
	public BanggiablnppList(String[] param)
	{
		this.tenbanggia = param[0];
		this.nccId = param[1];
		this.dvkdId = param[2];
		db = new dbutils();
	}
	
	public BanggiablnppList()
	{
		this.tenbanggia = "";
		this.nccId = "";
		this.dvkdId = "";
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
	
	public String getTenbanggia()
	{
		return this.tenbanggia;
	}
	
	public void setTenbanggia(String tenbanggia)
	{
		this.tenbanggia = tenbanggia;
	}
	
	public ResultSet getNcc() 
	{	
		return this.nhacungcap;
	}

	
	public void setNcc(ResultSet ncc) 
	{
		this.nhacungcap = ncc;		
	}
	
	public String getNccId() 
	{		
		return this.nccId;
	}
	
	public void setNccId(String nccId) 
	{
		this.nccId = nccId;		
	}
	
	public ResultSet getDvkd() 
	{		
		return this.donvikinhdoanh;
	}
	
	public void setDvkd(ResultSet dvkd) 
	{
		this.donvikinhdoanh = dvkd;
	}
	
	public String getDvkdId() 
	{
		return this.dvkdId;
	}
	
	public void setDvkdId(String dvkdId) 
	{
		this.dvkdId = dvkdId;		
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
	
	private void getNppInfo()
	{
		
		Utility util=new Utility();
		this.nppId=util.getIdNhapp(this.userId);
		this.nppTen=util.getTenNhaPP();
		this.sitecode=util.getSitecode();
	}
	
	public void init(String search) 
	{
		this.getNppInfo();
		
		String query;	
		if (search.length() == 0)
		{
			// Sua : Chi cho hien ra nhung bang gia theo don vi kinh doanh cua nha phan phoi.them dieu kien where npp in (dieu kien ket nha phan phoi thụuoc nha cung cap _dvkd)
			
				query = "\n select a.trangthai,a.pk_seq, a.ten as BGBLTEN, d.ten as nguoitao, a.ngaytao as ngaytao, e.ten as nguoisua, a.ngaysua as ngaysua,  " +
						"\n	b.donvikinhdoanh as DVKDTENVIETTAT, b.pk_seq as dvkdId  " +
						"\n from BANGGIABANLENPP a, donvikinhdoanh b, nhanvien d, nhanvien e " +
						"\n where a.dvkd_fk = b.pk_seq  and a.nguoitao = d.pk_seq and a.nguoisua = e.pk_seq and a.NPP_FK = '" + this.nppId +  "'";
			//	query += " order by BGBLID desc ";
		}
		else
		{
			query = search;
		}

		System.out.println("___LAY BANG GIA: " + query);
		String subquery = "  ";
		this.bgblList = super.createSplittingData_v2(db, super.getItems(), super.getSplittings(),"pk_seq desc", query, dataSearch, subquery); //this.db.get(query);
		this.createResualset();
		System.out.println("Chan qua: " + this.bgblList);


	}

	/*private void createBggstBeanList(String query) 
	{
		ResultSet rs =  db.get(query);
		List<IBanggiablnpp> bgstlist = new ArrayList<IBanggiablnpp>();			
		if(rs != null)
		{
			String[] param = new String[10];
			IBanggiablnpp bgstBean = null;			
			try {
				while(rs.next())
				{		
					param[0]= rs.getString("bgblId");
					param[1]= rs.getString("bgblTen");
					param[2]= rs.getString("dvkdTenVietTat");
					param[3]= rs.getString("ngaytao");
					param[4]= rs.getString("nguoitao");
					param[5]= rs.getString("ngaysua");
					param[6]= rs.getString("nguoisua");								
					//param[7]= rs.getString("nppId");
					param[7] = this.nppId;
					param[8]= rs.getString("dvkdId");
						
					bgstBean = new Banggiablnpp(param);
					bgstBean.setTrangthai(rs.getString("trangthai"));
					//bgstBean.setKenh(rs.getString("kenh"));
					//bgstBean.setTungay(rs.getString("tungay"));
					
					bgstlist.add(bgstBean);
				}
				rs.close();
			}
			catch(Exception e) 
			{
				System.out.println("___EXCEPTION INIT: " + e.getMessage());
			}		
		}
		this.bgblList = bgstlist;
	}*/
	
	private void createNhacungcap()
	{
		this.nhacungcap = db.get("select pk_seq as nccId, ten as nccTen from nhacungcap");
	}
	
	private void createDonvikd()
	{
		//this.donvikinhdoanh = db.get("select pk_seq as dvkdId, donvikinhdoanh as dvkdTenviettat, diengiai as dvkdTen from Donvikinhdoanh");
		this.donvikinhdoanh = db.get("select a.pk_seq as dvkdId, a.donvikinhdoanh as dvkdTenviettat, a.diengiai as dvkdTen from donvikinhdoanh a,Nhacungcap_dvkd b where a.pk_seq = b.dvkd_fk and a.trangthai='1' and b.checked ='1'");
	}
	
	private void createResualset()
	{
		this.createNhacungcap();
		this.createDonvikd();
	}


	public ResultSet getBgblList() {
		return bgblList;
	}
	public void setBgblList(ResultSet bgblList) {
		this.bgblList = bgblList;
	}
	public void DBclose() 
	{
		try 
		{
			if(!(nhacungcap == null))
				nhacungcap.close();
			if(!(donvikinhdoanh == null))
				donvikinhdoanh.close();
			if(db != null)
				db.shutDown();
		} 
		catch(Exception e) {}	
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
	
}
