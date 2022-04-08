package geso.dms.distributor.beans.gioihancongno.imp;

import geso.dms.distributor.beans.gioihancongno.IGioihancongno;
import geso.dms.distributor.db.sql.dbutils;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

public class Gioihancongno implements IGioihancongno, Serializable
{
	private static final long serialVersionUID = -9217977546733610214L;

	String userId;
	String id;
	String diengiai;
	String songayno; 
	String sotienno;
	String ngaytao;
	String nguoitao;
	String ngaysua;
	String nguoisua;
	String msg;
	
	String nppId;
	String nppTen;
	String sitecode;
	
	ResultSet ddkdlist;
	String ddkdId;
	
	ResultSet khachhanglist;
	ResultSet selectedKhlist;
	String[] kh_ghcnId;
	
	dbutils db;
	
	public Gioihancongno(String[] param)
	{
		this.id = param[0];
		this.diengiai = param[1];
		this.songayno = param[2];
		this.sotienno = param[3];
		this.ngaytao = param[4];
		this.nguoitao = param[5];
		this.ngaysua = param[6];
		this.nguoisua = param[7];
		this.ddkdId = "";
		this.msg = "";
		this.db = new dbutils();
	}
	
	public Gioihancongno(String id)
	{
		this.id = id;
		this.diengiai = "";
		this.songayno = "";
		this.sotienno = "";
		this.ngaytao = "";
		this.nguoitao = "";
		this.ngaysua = "";
		this.nguoisua = "";
		this.ddkdId = "";
		this.msg = "";
		this.kh_ghcnId = null;
		this.db = new dbutils();
	}
	
	public String getUserId()
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;
	}
	
	public String getId() 
	{
		return this.id;
	}
	
	public void setId(String id) 
	{
		this.id = id;
	}
	
	public String getDiengiai() 
	{
		return this.diengiai;
	}
	
	public void setDiengiai(String diengiai) 
	{
		this.diengiai = diengiai;
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
	
	public String getNgaytao()
	{
		return this.ngaytao;		
	}

	public void setNgaytao(String ngaytao)
	{
		this.ngaytao = ngaytao;
	}
	
	public String getNgaysua()
	{
		return this.ngaysua;	
	}

	public void setNgaysua(String ngaysua)
	{
		this.ngaysua = ngaysua;
	}
	
	public String getNguoitao()
	{
		return this.nguoitao;
	}
	
	public void setNguoitao(String nguoitao)
	{
		this.nguoitao = nguoitao;
	}

	public String getNguoisua()
	{
		return this.nguoisua;
	}

	public void setNguoisua(String nguoisua)
	{
		this.nguoisua = nguoisua;
	}
	
	public String getMessage() 
	{
		return this.msg;
	}
	
	public void setMessage(String msg) 
	{
		this.msg = msg;
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
	
	public String getDdkdId() 
	{
		return this.ddkdId;
	}

	public void setDdkdId(String ddkdId) 
	{
		this.ddkdId = ddkdId;
	}

	public ResultSet getDdkdList() 
	{
		return this.ddkdlist;
	}

	public void setDdkdList(ResultSet ddkdlist) 
	{
		this.ddkdlist = ddkdlist;
	}

	public ResultSet getKhachhangList() 
	{
		return this.khachhanglist;
	}

	public void setKhachhangList(ResultSet khachhanglist) 
	{
		this.khachhanglist = khachhanglist;
	}

	public ResultSet getSelectedKhlist() 
	{
		return this.selectedKhlist;
	}

	public void setSelectedKhlist(ResultSet selectedKhlist) 
	{
		this.selectedKhlist = selectedKhlist;
	}

	public Hashtable<Integer, String> getKh_GhcnIds() 
	{
		Hashtable<Integer, String> Id = new Hashtable<Integer, String>();
		if(this.kh_ghcnId != null){
			
			int size = (this.kh_ghcnId).length;
			int m = 0;
			while(m < size){
				Id.put(new Integer(m), this.kh_ghcnId[m]) ;
				m++;
			}
			System.out.print("m = " + m);
		}
		return Id;
	}

	public void setKh_GhcnIds(String[] kh_ghcnId)
	{
		this.kh_ghcnId = kh_ghcnId;
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

	public boolean CreateGhcn(String[] khIds) 
	{
		this.ngaytao = getDateTime();
		this.nguoitao = this.userId;
		
		String query = "insert into gioihancongno(diengiai, songayno, sotienno, ngaytao, ngaysua, nguoitao, nguoisua, npp_fk) " ;
		query = query + "values(N'" + this.diengiai + "','" + this.songayno + "','" + this.sotienno + "','" + this.ngaytao + "','" + this.ngaytao + "','" + this.nguoitao +"','" + this.nguoitao +"','" + this.nppId + "')";
		if(!this.db.update(query)){
			geso.dms.center.util.Utility.rollback_throw_exception(this.db);
			this.msg = "Khong the tao moi 'GioiHanCongNo' , " + query;
			return false; 
		}

		query = "select IDENT_CURRENT('gioihancongno')as ghcnId";
		ResultSet rs = this.db.get(query);
		try{
			rs.next();
			this.id = rs.getString("ghcnId");
		
			if(khIds != null)
			{	
				for(int m = 0; m < khIds.length; m++)
				{
					String sql = "Update KhachHang set ghcn_fk = '" + this.id + "' where pk_seq='" + khIds[m] + "'";
					if(!this.db.update(sql))
					{
						geso.dms.center.util.Utility.rollback_throw_exception(this.db);
						this.msg = "Khong the cap nhat gioihancongno cua khachhang, " + sql;
						return false; 
					}

				}
			}
		}catch(Exception e){}
		finally{try {
			if(rs!=null)
				rs.close();
		} catch (Exception e2) {
			// TODO: handle exception
		}}
		return true;
	}

	public boolean UpdateGhcn(String[] khIds, List<String> list) 
	{
		this.ngaysua = getDateTime();
		this.nguoisua = this.userId;
		
		String query = "update gioihancongno set diengiai=N'" + this.diengiai + "', songayno='" + this.songayno + "', sotienno='" + this.sotienno + "', ngaysua='" + this.ngaysua + "', nguoisua='" + this.nguoisua + "' where pk_seq='" + this.id + "'" ;
		if(!db.update(query)){
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			this.msg = "Khong the cap nhat 'GioiHanCongNo' , " + query;
			return false; 
		}
		
		Hashtable<String,String> hash = new Hashtable<String, String>();
		
		if(khIds != null)
		{
			for(int m = 0; m < khIds.length; m++)
			{
				String sql = "Update KhachHang set ghcn_fk = '" + this.id + "' where pk_seq='" + khIds[m] + "'";
				if(!db.update(sql))
				{
					geso.dms.center.util.Utility.rollback_throw_exception(db);
					this.msg = "Khong the cap nhat gioihancongno cua khachhang, " + sql;
					return false; 
				}
				hash.put( khIds[m],  khIds[m]);
			}
			for(String obj:list){
				if(!hash.contains(obj)){
					String q = "update khachhang set GHCN_FK=NULL where pk_seq= '" + obj +"'";
					if(!this.db.update(q))
					{
						geso.dms.center.util.Utility.rollback_throw_exception(this.db);
						this.msg = "Khong the cap nhat gioi han cong no cua khachhang, " +q;
						return false; 
					}
				}
			}		
		}
		return true;
	}

	public void init() 
	{
		this.getNppInfo();
	
		ResultSet rs =  this.db.get("select * from vwGioihancongno where ghcnId='" + this.id + "'");
		try
        {
            rs.next();        	
            this.id = rs.getString("ghcnId");
			this.diengiai = rs.getString("diengiai");
			this.songayno = rs.getString("songayno");
			this.sotienno = rs.getString("sotienno");
			this.ngaytao = rs.getString("ngaytao");
			this.nguoitao = rs.getString("nguoitao");
			this.ngaysua = rs.getString("ngaysua");
			this.nguoisua = rs.getString("nguoisua");		
			
			rs.close();
       	}
        catch(Exception e){}
        finally{try {
			if(rs!=null)
				rs.close();
		} catch (Exception e2) {
			// TODO: handle exception
		}}
        
        this.createDdkdRS();
        this.createKhachhangRS();
        this.createKh_GhcnIds();
	}
	
	private void createKh_GhcnIds()
	{
		String kh_ghcnIds = "";
		//int m = 0;
		ResultSet kh_ghcn = this.db.get("select pk_seq from khachhang where ghcn_fk='" + this.id  + "'");
		if(kh_ghcn != null)
		{		
			try 
			{
				while(kh_ghcn.next())
				{
					kh_ghcnIds = kh_ghcnIds + kh_ghcn.getString("pk_seq") + ",";
					//m++;
				}
			} catch(Exception e) {}
			finally{try {
				if(kh_ghcn!=null)
					kh_ghcn.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}}
		}
		if(kh_ghcnIds != "")
		{
			kh_ghcnIds = kh_ghcnIds.trim().substring(0, kh_ghcnIds.length() - 1); //bo ky tu "," cuoi cung
			this.kh_ghcnId = kh_ghcnIds.split(",");
		}
	}
	
	private void createDdkdRS()
	{
		this.ddkdlist = this.db.get("select pk_seq as ddkdId, ten as ddkdTen from daidienkinhdoanh where npp_fk='" + this.nppId + "'");
	}
	
	private void createKhachhangRS()
	{
		String query = "select pk_seq as khId, ten as khTen, diachi, ghcn_fk  " +
				"		from khachhang  " +
				"		where npp_fk = '" + this.nppId + "' and pk_seq in (" +
				"			select kh_tbh.khachhang_fk" +
				"			from  khachhang_tuyenbh kh_tbh" +
				"				inner join tuyenbanhang tbh on kh_tbh.tbh_fk = tbh.pk_seq" +
				"				inner join daidienkinhdoanh ddkd on ddkd.pk_seq = tbh.ddkd_fk" +
				"			where ddkd.pk_seq = '" + this.ddkdId + "') " +
				"		order by pk_seq";		
		this.khachhanglist = this.db.get(query);
		
		String sql="select pk_seq as khId, ten as khTen, diachi " +
				"		from khachhang where npp_fk='"+ this.nppId + "' and ghcn_fk='" + this.id+"'"; 
		this.selectedKhlist =  this.db.get(sql);
	}
	
	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	public void createRS() 
	{
		this.getNppInfo();
		this.createDdkdRS();
		this.createKhachhangRS();
	}

	public void DBclose()
	{
		try 
		{

			if(this.selectedKhlist != null)
				this.selectedKhlist.close();
			if(!(ddkdlist == null))
				ddkdlist.close();
			if(!(khachhanglist == null))
				khachhanglist.close();
			if(this.db != null)
				this.db.shutDown();
		} 
		catch(Exception e) {}
	}

	@Override
	public List<String> getListTinDung(String ttId) {
		ResultSet rs = this.db.get("SELECT * FROM KHACHHANG k WHERE k.GHCN_FK='" + ttId +"'");
		List<String> list = new ArrayList<String>();
		try {
			while(rs.next()){
				list.add(rs.getString("PK_SEQ"));
			}
		} catch(Exception e) {
			return null;
		}
		return list;
	}
			
	
}


