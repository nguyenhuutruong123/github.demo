package geso.dms.distributor.beans.gioihancongno.imp;

import geso.dms.distributor.beans.gioihancongno.*;
import geso.dms.distributor.db.sql.dbutils;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GioihancongnoList implements IGioihancongnoList , Serializable
{
	private static final long serialVersionUID = -9217977546733610214L;

	String userId; 
	
	String songayno;
	String sotienno;
	
	ResultSet tbh; //loc khach hang theo tuyen
	String tbhId;
	String khachhang;
		
	List<IGioihancongno> ghcnlist;
	
	String nppId;
	String nppTen;
	String sitecode;
	String Msg;
	dbutils db;
	
	public GioihancongnoList(String[] param)
	{
		this.songayno = param[0];
		this.sotienno = param[1];
		this.khachhang = param[2];
		db = new dbutils();
	}
	
	public GioihancongnoList()
	{
		this.songayno = "";
		this.sotienno = "";
		this.khachhang = "";
		this.Msg ="";
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
	
	public String getSongayno() 
	{
		return this.songayno;
	}

	public void setSongayno(String songayno) 
	{
		this.songayno = songayno;
	}
	
	public String getSotienno() 
	{
		return this.sotienno;
	}

	public void setSotienno(String sotienno) 
	{
		this.sotienno = sotienno;
	}

	public String getKhachhang() 
	{
		return this.khachhang;
	}

	public void setKhachhang(String khachhang) 
	{
		this.khachhang = khachhang;
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
	
	public ResultSet getTuyenbanhang()
	{
		return this.tbh;
	}

	public void setTuyenbanhang(ResultSet tuyenbanhang) 
	{
		this.tbh = tuyenbanhang;
	}

	public String getTbhId() 
	{
		return this.tbhId;
	}

	public void setTbhId(String tbhId) 
	{
		this.tbhId = tbhId;
	}
	
	public List<IGioihancongno> getGhcnList()
	{
		return this.ghcnlist;
	}
	
	public void setGhcnList(List<IGioihancongno> ghcnlist) 
	{
		this.ghcnlist = ghcnlist;
	}
	
	private void getNppInfo()
	{		
		/*ResultSet rs = this.db.get("select a.pk_seq, a.ten, a.sitecode from nhaphanphoi a, nhanvien b where b.dangnhap = a.ma and b.pk_seq ='" + this.userId + "'");
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
		this.sitecode=util.getSitecode();
	}

	public void init(String search) 
	{
		//db = new dbutils();
		this.getNppInfo();
		
		String query = "";	
		if (search.length() == 0)
		{
			query = "select * from vwGioihancongno where nppId = '" + this.nppId + "' order by songayno";
		}
		else
		{
			query = search;
		}		
		this.createGhcnBeanList(query);
		this.createTbhResualset();
	}

	private void createGhcnBeanList(String query) 
	{
		ResultSet rs =  db.get(query);
		List<IGioihancongno> ghcnlist = new ArrayList<IGioihancongno>();
			
		if(rs != null)
		{
			String[] param = new String[8];
			IGioihancongno ghcnBean = null;			
			try {
				while(rs.next())
				{	
					param[0]= rs.getString("ghcnId");
					param[1]= rs.getString("diengiai");
					param[2]= rs.getString("songayno");
					param[3]= rs.getString("sotienno");
					param[4]= rs.getDate("ngaytao").toString();
					param[5]= rs.getString("nguoitao");
					param[6]= rs.getDate("ngaysua").toString();
					param[7]= rs.getString("nguoisua");
																
					ghcnBean = new Gioihancongno(param);
					ghcnlist.add(ghcnBean);
				}
				rs.close();
			}
			catch(Exception e) {}
			finally{try {
				if(rs!= null)
					rs.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}}
			
		}
		this.ghcnlist = ghcnlist;		
	}

	private void createTbhResualset()
	{
		String sql = "select pk_seq as tbhId, diengiai as tbhTen from Tuyenbanhang where npp_fk = '" + this.nppId + "'";
		this.tbh = db.get(sql);
	}
 @Override
	public void DBclose() 
	{
		try 
		{

			
			if(!(tbh == null))
				tbh.close();
			if(this.db != null)
				this.db.shutDown();
		} 
		catch(Exception e) {}
	}

	
	public void setMsg(String Msg) {
	this.Msg = Msg;
	}


	public String getMsg() {
		return this.Msg;
	}

}

