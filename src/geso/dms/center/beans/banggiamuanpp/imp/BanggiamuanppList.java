package geso.dms.center.beans.banggiamuanpp.imp;

import geso.dms.center.beans.banggiamuanpp.IBanggiamuanppList;
import geso.dms.center.beans.banggiamuanpp.imp.BanggiamuanppList;
import geso.dms.center.beans.banggiamuanpp.IBanggiamuanpp;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;
import geso.dms.center.db.sql.dbutils;

public class BanggiamuanppList implements IBanggiamuanppList
{
	private static final long serialVersionUID = -927977546783610214L;
	
	String userId;
	String userTen;
	String ten;
	ResultSet dvkdIds;
	String dvkdId;
	ResultSet kenhIds;
	String kenhId;	
	String trangthai;
	List<IBanggiamuanpp> bgmuanpplist;
	ResultSet bglist;
	dbutils db;
	
	//Constructor
	public BanggiamuanppList(String[] param)
	{
		this.ten = param[0];		
		this.dvkdId = param[1];
		this.kenhId = param[2];
		this.trangthai = param[3];
		this.db = new dbutils();
	}
	
	public BanggiamuanppList()
	{
		this.ten = "";		
		this.dvkdId = "";
		this.kenhId = "";
		this.trangthai = "";
		this.db = new dbutils();
		
		init("");
	}
	
	public String getUserId() 
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;
		this.userTen = "Nobody";
		ResultSet rs = this.db.get("select ten from nhanvien where pk_seq = "+ this.userId);
		System.out.println("select ten from nhanvien where pk_seq ='" + this.userId + "'");
		try{
			rs.next();
			this.userTen = rs.getString("ten");
			rs.close();
		}catch(Exception er){}
	}

	public String getUserTen() 
	{
		return this.userTen;
	}
	
	public void setUserTen(String userTen) 
	{
		this.userTen = userTen;
		
	}
	
	public String getTen() 
	{
		return this.ten;
	}

	public void setTen(String ten) 
	{
		this.ten = ten;
	}

	
	public String getDvkdId() 
	{
		return this.dvkdId;
	}

	public void setDvkdId(String dvkdId) 
	{
		this.dvkdId = dvkdId;
	}
	
	public String getKenhId() 
	{
		return this.kenhId;
	}

	public void setKenhId(String kenhId) 
	{
		this.kenhId = kenhId;
	}

	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}

	public List<IBanggiamuanpp> getBgmuanppList() 
	{
		return this.bgmuanpplist;
	}

	public void setBgmuanppList(List<IBanggiamuanpp> bgmuanpplist) 
	{
		this.bgmuanpplist = bgmuanpplist;
	}


	public ResultSet getDvkd() 
	{
		return this.dvkdIds;
	}

	public void setDvkd(ResultSet dvkdIds) 
	{
		this.dvkdIds = dvkdIds;
	}

	public ResultSet getKenh() 
	{
		return this.kenhIds;
	}

	public void setKenh(ResultSet kenhIds) 
	{
		this.kenhIds = kenhIds;
	}

	public ResultSet getBglist() 
	{
		return this.bglist;
	}

	public void setBglist(ResultSet bglist) 
	{
		this.bglist = bglist;
	}

	public boolean saveNewBgblc() 
	{
		return false;
	}

	public boolean UpdateBgblc() 
	{
		return false;
	}

	
	private void createDvkdRS(){  				
		
		//this.dvkdIds  =  this.db.get("select donvikinhdoanh as dvkd, pk_seq as dvkdId from donvikinhdoanh where trangthai='1' ");;
		this.dvkdIds  =  this.db.get("select distinct a.donvikinhdoanh as dvkd, a.pk_seq as dvkdId from donvikinhdoanh a,nhacungcap_dvkd b where a.pk_seq = b.DVKD_fk and b.checked ='1' and a.trangthai ='1' order by a.donvikinhdoanh");
	}

	private void createKenhRS(){  				
		
		this.kenhIds  =  this.db.get("select diengiai as kenh, pk_seq as kenhId from kenhbanhang where trangthai='1'");
	}

	public void createBanggiamuanppBeanList(String query){  	
		
		ResultSet rs =  this.db.get(query);
		List<IBanggiamuanpp> bgmuanpplist = new ArrayList<IBanggiamuanpp>();
		if (rs != null){		
			IBanggiamuanpp bgmuanppBean;
			String[] param = new String[15];
			try{
				while(rs.next()){
					param[0]= rs.getString("id");
					param[1]= rs.getString("ten");
					param[2]= rs.getString("dvkd");
					param[3]= rs.getString("kenh");
					param[4]= rs.getString("trangthai");
					param[5]= rs.getString("ngaytao");
					param[6]= rs.getString("nguoitao");
					param[7]= rs.getString("ngaysua");
					param[8]= rs.getString("nguoisua");
					
					bgmuanppBean = new Banggiamuanpp(param);
					bgmuanpplist.add(bgmuanppBean);															
				}
				rs.close();
			}catch(Exception e){
		
			}
			 
		}
		
		this.bgmuanpplist = bgmuanpplist;
	}

	public void init(String search){
		
		String query;
		
		if (search.length()>0){
			query = search;
		}else{
			query = "select a.pk_seq as id, a.ten as ten, a.trangthai as trangthai, d.ten as nguoitao, a.ngaytao as ngaytao, e.ten as nguoisua, a.ngaysua as ngaysua, b.donvikinhdoanh as dvkd, c.ten as kenh, c.pk_seq as kenhId from banggiamuanpp a, donvikinhdoanh b, kenhbanhang c, nhanvien d, nhanvien e where a.dvkd_fk=b.pk_seq and a.kenh_fk = c.pk_seq and a.nguoitao = d.pk_seq and a.nguoisua = e.pk_seq";
		}
//		createBanggiamuanppBeanList(query);
		this.bglist = this.db.get(query);
		
		createDvkdRS();
		createKenhRS();
	}
	
	public void closeDB(){
		try{
			Statement st;
			if(dvkdIds != null) {
				st = dvkdIds.getStatement();
				st.close();
				dvkdIds.close();
			}
			
			if (kenhIds != null){
				st = kenhIds.getStatement();
				st.close();				
				kenhIds.close();
			}
			
			if (bglist != null){
				st = bglist.getStatement();
				st.close();				
				bglist.close();
			}
			
/*			int size = this.bgmuanpplist.size();
			int m = 0;
			IBanggiamuanpp bgmuanpp;
			while (m < size){				
				bgmuanpp = (IBanggiamuanpp)bgmuanpplist.get(m);
				bgmuanpp.closeDB();
				m++;
			}*/
			
			this.db.shutDown();
		}catch(Exception e){}

	}
	
}

