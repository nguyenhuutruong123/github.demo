package geso.dms.distributor.beans.mucchietkhau.imp;

import geso.dms.distributor.beans.mucchietkhau.IMucchietkhau;
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

public class Mucchietkhau implements IMucchietkhau, Serializable
{
	private static final long serialVersionUID = -9217977546733610214L;

	String userId;
	String id;
	String chietkhau;
	String diengiai;
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
	String[] kh_mckId;
	
	dbutils db;
	
	public Mucchietkhau(String[] param)
	{
		this.id = param[0];
		this.chietkhau = param[1];
		this.diengiai = param[2];
		this.ngaytao = param[3];
		this.nguoitao = param[4];
		this.ngaysua = param[5];
		this.nguoisua = param[6];
		this.ddkdId = "";
		this.msg = "";
		db = new dbutils();
	}
	
	public Mucchietkhau(String id)
	{
		this.id = id;
		this.chietkhau = "";
		this.diengiai = "";
		this.ngaytao = "";
		this.nguoitao = "";
		this.ngaysua = "";
		this.nguoisua = "";
		this.ddkdId = "";
		this.msg = "";
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
	
	public String getId() 
	{
		return this.id;
	}
	
	public void setId(String id) 
	{
		this.id = id;
	}

	public String getChietkhau()
	{
		return this.chietkhau;
	}
	
	public void setChietkhau(String chietkhau)
	{
		this.chietkhau = chietkhau;
	}
	
	public String getDiengiai() 
	{
		return this.diengiai;
	}
	
	public void setDiengiai(String diengiai) 
	{
		this.diengiai = diengiai;
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

	public Hashtable<Integer, String> getKh_MckIds() 
	{
		Hashtable<Integer, String> Id = new Hashtable<Integer, String>();
		if(this.kh_mckId != null){
			int size = (this.kh_mckId).length;
			int m = 0;
			while(m < size){
				Id.put(new Integer(m), this.kh_mckId[m]) ;
				m++;
			}
		}else{
			Id.put(new Integer(0), "null");
		}
		return Id;
	}

	public void setKh_MckIds(String[] kh_mckId)
	{
		this.kh_mckId = kh_mckId;
	}
	
	public ResultSet getSelectedKhlist() 
	{
		return this.selectedKhlist;
	}

	public void setSelectedKhlist(ResultSet selectedKhlist) 
	{
		this.selectedKhlist = selectedKhlist;
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

	public boolean CreateMck(String[] khIds) 
	{
		this.ngaytao = getDateTime();
		this.nguoitao = this.userId;
		
		String query = "insert into mucchietkhau(diengiai, chietkhau, ngaytao, ngaysua, nguoitao, nguoisua, npp_fk) " ;
		query = query + " values(N'" + this.diengiai + "','" + this.chietkhau + "','" + this.ngaytao + "','" + this.ngaytao + "','" + this.nguoitao +"','" + this.nguoitao +"','" + this.nppId + "')";
		if(!db.update(query)){
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			this.msg = "Khong the tao moi 'Muc Chiet Khau' , " + query;
			return false; 
		}

		query = "select IDENT_CURRENT('mucchietkhau')as mckId";
		ResultSet rs = this.db.get(query);
		
		try{
			rs.next();
			this.id = rs.getString("mckId");
		
			if(khIds != null)
			{
				for(int m = 0; m < khIds.length; m++)
				{
					String sql = "Update KhachHang set chietkhau_fk = '" + this.id + "' where pk_seq='" + khIds[m] + "'";
					if(!this.db.update(sql))
					{
						geso.dms.center.util.Utility.rollback_throw_exception(this.db);
						this.msg = "Khong the cap nhat mucchietkhau cua khachhang, " + sql;
						return false; 
					}

				}
			}
		}catch(Exception e){}
		finally{try {
			if(rs != null)
				rs.close();
		} catch (Exception e2) {
			// TODO: handle exception
		}}
		return true;
	}

	public boolean UpdateMck(String[] khIds,List<String> list) 
	{
		this.ngaysua = getDateTime();
		this.nguoisua = this.userId;
		
		String query = "update mucchietkhau set chietkhau='" + this.chietkhau + "', diengiai=N'" + this.diengiai + "', ngaysua='" + this.ngaysua + "', nguoisua='" + this.nguoisua + "' where pk_seq='" + this.id + "'" ;
		if(!this.db.update(query)){
			geso.dms.center.util.Utility.rollback_throw_exception(this.db);
			this.msg = "Khong the cap nhat 'Muc Chiet Khau' , " + query;
			return false; 
		}	
		Hashtable<String,String> hash = new Hashtable<String, String>();
		if(khIds != null)
		{
			for(int m = 0; m < khIds.length; m++)
			{
				String sql = "Update KhachHang set chietkhau_fk = '" + this.id + "' where pk_seq='" + khIds[m] + "'";
				System.out.println(sql);
				if(!this.db.update(sql))
				{
					geso.dms.center.util.Utility.rollback_throw_exception(this.db);
					this.msg = "Khong the cap nhat 	 cua khachhang, " + sql;
					return false; 
				}
				hash.put( khIds[m],  khIds[m]);
			}
			for(String obj:list){
				if(!hash.contains(obj)){
					String q = "update khachhang set chietkhau_fk=NULL where pk_seq= '" + obj +"'";
					if(!this.db.update(q))
					{
						geso.dms.center.util.Utility.rollback_throw_exception(this.db);
						this.msg = "Khong the cap nhat mucchietkhau cua khachhang, " +q;
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
	
		ResultSet rs =  this.db.get("select * from vwMucchietkhau where mckId='" + this.id + "'");
		try
        {
            rs.next();        	
            this.id = rs.getString("mckId");
			this.chietkhau = rs.getString("chietkhau");
			this.diengiai = rs.getString("diengiai");
			
			this.ngaytao = rs.getString("ngaytao");
			this.nguoitao = rs.getString("nguoitao");
			this.ngaysua = rs.getString("ngaysua");
			this.nguoisua = rs.getString("nguoisua");					
			rs.close();
       	}
        catch(Exception e){}
        finally{try {
			if(rs != null)
				rs.close();
		} catch (Exception e2) {
			// TODO: handle exception
		}}
        
        this.createDdkdRS();
        this.createKhachhangRS();
        this.createKh_MckIds();
	}
	
	private void createKh_MckIds()
	{
		String kh_mckIds = "";
		//int m = 0;
		ResultSet kh_mck = this.db.get("select pk_seq from khachhang where chietkhau_fk='" + this.id  + "'");
		if (kh_mck != null) {
			try {
				while (kh_mck.next()) {
					kh_mckIds = kh_mckIds + kh_mck.getString("pk_seq") + ",";
				}
				kh_mck.close();
			} catch(Exception e) {
			} finally {
				try {
					kh_mck.close();
				} catch(Exception e) {

				}
			}
		}
		if(kh_mckIds != "")
		{
			kh_mckIds = kh_mckIds.trim().substring(0, kh_mckIds.length() - 1); //bo ky tu "," cuoi cung
			this.kh_mckId = kh_mckIds.split(",");
		}
	}
	
	private void createDdkdRS()
	{
		this.ddkdlist = this.db.get("select pk_seq as ddkdId, ten as ddkdTen from daidienkinhdoanh where npp_fk='" + this.nppId + "'");
	}
	
	private void createKhachhangRS()
	{
		 String sql = "select pk_seq as khId, ten as khTen, diachi from khachhang where npp_fk = '" + this.nppId + "'"; 

		 
		//	if(this.ddkdId.length() > 0)
				sql = sql + " and pk_seq in (" +
						"						select kh_tbh.khachhang_fk " +
						"						from  khachhang_tuyenbh kh_tbh " +
						"							inner join tuyenbanhang tbh on kh_tbh.tbh_fk = tbh.pk_seq " +
						"							inner join daidienkinhdoanh ddkd on ddkd.pk_seq = tbh.ddkd_fk " +
						"						where ddkd.pk_seq = '" + this.ddkdId + "')";
			if(!this.id.equals("")){
				sql = sql + "  and isnull(chietkhau_fk,0) not in (select isnull(chietkhau_fk,0) from khachhang where npp_fk='" + this.nppId + "' and chietkhau_fk = '"+this.id+"')";
			}
			
			String chuoi="";
			if(this.kh_mckId != null){
				for(int i=0;i<this.kh_mckId.length ;i++){
					if(i==0){
						chuoi= kh_mckId[i];
					}else{
						chuoi=chuoi+","+ kh_mckId[i];
					}
				}
				sql=sql + " and pk_seq not in ("+chuoi+")";
			}			
			
			sql = sql + "  order by pk_seq";
			System.out.println(sql);
			this.khachhanglist = db.get(sql);
			if(this.kh_mckId == null){
				sql="select pk_seq as khId, ten as khTen, diachi from khachhang where npp_fk='"+ this.nppId + "' and chietkhau_fk='" + this.id+"'";
			}else{
				sql="select pk_seq as khId, ten as khTen, diachi from khachhang where pk_seq in (" +chuoi +")";
				
			}
			
			System.out.println("abc"+sql);
			this.selectedKhlist =  this.db.get(sql);
		/*
				
		 
		if(this.ddkdId.length()>0){
			String query ="select pk_seq as khId, ten as khTen, diachi " +
					"from khachhang " +
					"where npp_fk = '" + this.nppId +"'" +
					"	and pk_seq in (" +
					"					select kh_tbh.khachhang_fk" +
					"					from  khachhang_tuyenbh kh_tbh" +
					"						inner join tuyenbanhang tbh on kh_tbh.tbh_fk = tbh.pk_seq" +
					"						inner join daidienkinhdoanh ddkd on ddkd.pk_seq = tbh.ddkd_fk" +
					"					where ddkd.pk_seq = '" + this.ddkdId + "'" +
					"					) order by pk_seq";
			this.khachhanglist =  this.db.get(query);
		}
		String sql="select pk_seq as khId, ten as khTen, diachi from khachhang where npp_fk='"+ this.nppId + "' and chietkhau_fk='" + this.id+"'"; 
		this.selectedKhlist =  this.db.get(sql);*/
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

	
	public List<String> getListKHCK(String ckId) {
		ResultSet rs = this.db.get("SELECT * FROM KHACHHANG k WHERE k.CHIETKHAU_FK='" + ckId +"'");
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

