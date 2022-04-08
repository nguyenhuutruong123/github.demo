package geso.dms.distributor.beans.nhanviengiaonhan.imp;

import geso.dms.distributor.beans.nhanviengiaonhan.INhanviengiaonhan;
import geso.dms.distributor.db.sql.dbutils;
import geso.dms.distributor.util.Utility;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

public class Nhanviengiaonhan implements INhanviengiaonhan, Serializable
{
	private static final long serialVersionUID = -9217977546733610214L;
	String userId;
	String id;
	String tennv;
	String trangthai;
	String diachi;
	String ngaytao;
	String nguoitao;
	String ngaysua;
	String nguoisua;
	String dienthoai;
	String msg;
	
	String nppId;
	String nppTen;
	String sitecode;
	
	ResultSet ddkdlist;
	String ddkdId;
	ResultSet tbhlist;
	String tbhId;
	String[] tbhIds;
	String[] ddkdIds;
	
	ResultSet khlist;
	ResultSet khSelectedList; //khach hang cua nhan vien giao nhan
	String[] khIds;
	
	dbutils db;
	
	public Nhanviengiaonhan(String[] param)
	{
		this.id = param[0];
		this.tennv = param[1];
		this.trangthai = param[2];
		this.diachi = param[3];
		this.ngaytao = param[4];
		this.nguoitao = param[5];
		this.ngaysua = param[6];
		this.nguoisua = param[7];
		this.dienthoai = param[8];
		this.ddkdId = "";
		this.tbhId = "";
		this.msg = "";
		db = new dbutils();
	}
	
	public Nhanviengiaonhan(String id)
	{
		this.id = id;
		this.tennv = "";
		this.trangthai = "2";
		this.diachi = "";
		this.ngaytao = "";
		this.nguoitao = "";
		this.ngaysua = "";
		this.nguoisua = "";
		this.dienthoai = "";
		this.ddkdId = "";
		this.tbhId = "";
		this.msg = "";
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
	
	public String getTennv()
	{
		return this.tennv;
	}
	
	public void setTennv(String tennv)
	{
		this.tennv = tennv;
	}
	
	public String getTrangthai()
	{
		return this.trangthai;
	}
	
	public void setTrangthai(String trangthai)
	{
		this.trangthai = trangthai;
	}
	
	public String getDiachi()
	{
		return this.diachi;
	}
	
	public void setDiachi(String diachi)
	{
		this.diachi = diachi;
	}
	
	public String getNgaytao()
	{
		return this.ngaytao;
	}
	
	public void setNgaytao(String ngaytao)
	{
		this.ngaytao = ngaytao;
	}
	
	public String getNguoitao()
	{
		return this.nguoitao;
	}
	
	public void setNguoitao(String nguoitao)
	{
		this.nguoitao = nguoitao;
	}
	
	public String getNgaysua()
	{
		return this.ngaysua;
	}
	
	public void setNgaysua(String ngaysua)
	{
		this.ngaysua = ngaysua;
	}
	
	public String getNguoisua()
	{
		return this.nguoisua;
	}
	
	public void setNguoisua(String nguoisua)
	{
		this.nguoisua = nguoisua;
	}
	
	public String getDienthoai()
	{
		return this.dienthoai;
	}
	
	public void setDienthoai(String dienthoai)
	{
		this.dienthoai = dienthoai;
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

	public ResultSet getDdkdList() 
	{
		return this.ddkdlist;
	}
	
	public void setDdkdList(ResultSet ddkdlist) 
	{
		this.ddkdlist = ddkdlist;
	}
	
	public String getDdkdId() 
	{		
		return this.ddkdId;
	}
	
	public void setDdkdId(String ddkdId) 
	{
		this.ddkdId = ddkdId;		
	}
	
	public ResultSet getTuyenbanhang() 
	{		
		return this.tbhlist;
	}
	
	public void setTuyenhang(ResultSet tuyenbanhang) 
	{
		this.tbhlist = tuyenbanhang;		
	}
	
	public Hashtable<Integer, String> getTbhIds() 
	{
		Hashtable<Integer, String> selected = new Hashtable<Integer, String>();
		if(this.tbhIds != null){
			int size = (this.tbhIds).length;
			int m = 0;
			while(m < size){
				selected.put(new Integer(m), this.tbhIds[m]) ;
				m++;
			}
		}else{
			selected.put(new Integer(0), "null");
		}
		return selected;
	}

	public void setTbhIds(String[] tbhIds)
	{
		this.tbhIds = tbhIds;
	}

	public void setDdkdIds(String[] ddkdIds)
	{
		this.ddkdIds = ddkdIds;
	}

	public Hashtable<Integer, String> getDdkdIds() 
	{
		Hashtable<Integer, String> selected = new Hashtable<Integer, String>();
		if(this.ddkdIds != null){
			int size = (this.ddkdIds).length;
			int m = 0;
			while(m < size){
				selected.put(new Integer(m), this.ddkdIds[m]) ;
				//System.out.println("Danh Sach DDKD : "+ this.ddkdIds[m] );
				m++;
			}
		}else{
			selected.put(new Integer(0), "null");
		}
		return selected;
	}

	public ResultSet getKhList() 
	{		
		return this.khlist;
	}
	
	public void setKhList(ResultSet khlist) 
	{
		this.khlist = khlist;		
	}

	public Hashtable<Integer, String> getKhIds() 
	{		
		Hashtable<Integer, String> selected = new Hashtable<Integer, String>();
		if(this.khIds != null){
			int size = (this.khIds).length;
			int m = 0;
			while(m < size){
				selected.put(new Integer(m), this.khIds[m]) ;
				m++;
			}
		}else{
			selected.put(new Integer(0), "null");
		}
		return selected;
	}
	
	public void setKhIds(String[] khIds) 
	{
		this.khIds = khIds;
	}
	
	private void getNppInfo()
	{	
		/*ResultSet rs = this.db.get("select a.pk_seq, a.ten, a.sitecode from nhaphanphoi a, nhanvien b where b.dangnhap = a.ma and b.pk_seq ='" + this.userId + "'");
		try
		{
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
		}
		catch(Exception e){}
		*/
		//Phien ban moi
		geso.dms.distributor.util.Utility util=new geso.dms.distributor.util.Utility();
		this.nppId=util.getIdNhapp(this.userId);
		this.nppTen=util.getTenNhaPP();
		this.sitecode=util.getSitecode();
	}
	
	public boolean CreateNvgn(String[] khIds)
	{	
		List<Object> data = new ArrayList<Object>();
		this.ngaytao = getDateTime();
		this.nguoitao = this.userId;
		ResultSet rsNvgn = null;
		try 
		{
			db.getConnection().setAutoCommit(false);
		
			String query = "insert into nhanviengiaonhan(ten, trangthai, diachi, ngaytao, ngaysua, nguoitao, nguoisua, dienthoai, npp_fk) " ;
			query = query + "values(?,'1',?,?,?,?,?,?,?)";
			data.clear();
			data.add(this.tennv); data.add(this.diachi); data.add(this.ngaytao); data.add(this.ngaytao); data.add(this.nguoitao);
			data.add(this.nguoitao);data.add(this.dienthoai);data.add(this.nppId);
			this.db.viewQuery(query, data);
			if(this.db.updateQueryByPreparedStatement(query, data)<=0){
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				this.msg = "Khong the tao moi 'NhanVienGiaoNhan'. Vui lòng liên hệ quản trị viên " ;
				return false; 
			}
			
/*			query = "select SCOPE_identity() nvgnId";
			rsNvgn = this.db.get(query);					
			rsNvgn.next();*/
			this.id = this.db.getPk_seq(); 
			
			if(khIds != null)
			{
				for(int m = 0; m < khIds.length; m++)
				{
					
					/*String sql = "insert into nvgn_kh(nvgn_fk, khachhang_fk) values('" + this.id + "', '" + khIds[m] + "')";*/
					String sql = "insert into nvgn_kh(nvgn_fk, khachhang_fk) values(?,?)";
					data.clear();
					data.add(this.id); data.add(khIds[m]);
					this.db.viewQuery(query, data);
					if(this.db.updateQueryByPreparedStatement(query, data)<=0)
					{
						geso.dms.center.util.Utility.rollback_throw_exception(db);
						this.msg = "Khong the cap nhat 'nvgn_kh' cua NhanVienGiaoHang. Vui lòng liên hệ quản trị viên " ;
						return false; 
					}
				}
			}
			
			if (ddkdIds != null && ddkdIds.length >0) {
				int length = ddkdIds.length;
				String _ddkdIds = "";
				for (int i = 0; i < length; i ++) {
					_ddkdIds += ","+ddkdIds[i];
				}
				if (_ddkdIds != null && _ddkdIds.length() > 0) {
					_ddkdIds = _ddkdIds.substring(1);
				}
				
/*				query = "\n insert into nvgn_kh(nvgn_fk, khachhang_fk) " +
						"\n select "+this.id+",pk_seq from khachhang a where trangthai = 1" +
						"\n and exists (select 1 from khachhang_tuyenbh aa inner join tuyenbanhang bb on aa.tbh_fk = bb.pk_seq" +
						"\n where khachhang_fk = a.pk_seq and bb.ddkd_fk in ("+_ddkdIds+") )";*/
				query = "\n insert into nvgn_kh(nvgn_fk, khachhang_fk) " +
						"\n select ?,pk_seq from khachhang a " +
						"\n where exists (select 1 from khachhang_tuyenbh aa inner join tuyenbanhang bb on aa.tbh_fk = bb.pk_seq" +
						"\n where khachhang_fk = a.pk_seq and bb.ddkd_fk in ( ";
				data.clear();
				data.add(this.id);
				for(int i =0;i<ddkdIds.length;i++){
					if(i==(ddkdIds.length-1)){
						data.add(ddkdIds[i]);
						query += "?";
					}
					else{
						data.add(ddkdIds[i]);
						query += "?,";
					}
				}
				query+=" ) )";
				this.db.viewQuery(query, data);
				if (this.db.updateQueryByPreparedStatement(query, data)<0) {
					geso.dms.center.util.Utility.rollback_throw_exception(db);
					this.msg = "Không thể tạo mới NVGN thông qua NVBH. Vui lòng liên hệ quản trị viên " ;
					return false; 
				}
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch(Exception e) {
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			this.msg = "Loi khi cap nhat bang "+e.toString();
			return false; 
		}
		finally{try {
			if(rsNvgn != null)
				rsNvgn.close();
		} catch (Exception e2) {
			// TODO: handle exception
		}}
		return true;	
	}
	
	public boolean UpdateNvgn(String[] khIds)
	{
		List<Object> data = new ArrayList<Object>();
		this.ngaysua = getDateTime();
		this.nguoisua = this.userId;
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			/*String query = "update NhanVienGiaoNhan set ten=N'" + this.tennv + "', trangthai='" + this.trangthai + "', diachi=N'" + this.diachi + "', dienthoai='";
			query = query + this.dienthoai + "', nguoisua='" + this.nguoisua + "',  ngaysua='" + this.ngaysua + "' where pk_seq='" + this.id + "'";
			*/
			String query = "update NhanVienGiaoNhan set ten=?, trangthai=?, diachi=?, dienthoai=?";
			query = query + ", nguoisua=?,  ngaysua=? where pk_seq=?";
			data.clear();
			data.add(this.tennv); data.add(this.trangthai); data.add(this.diachi); data.add(this.dienthoai);
			data.add(this.nguoisua); data.add(this.ngaysua); data.add(this.id);
			this.db.viewQuery(query, data);
			if(this.db.updateQueryByPreparedStatement(query, data)<=0)
			{
				db.getConnection().rollback();
				this.msg = "Khong the cap nhat 'NhanVienGiaoNhan'. Vui lòng liên hệ quản trị viên " ;
				return false; 
			}	
			
			/*query = "delete nvgn_kh where nvgn_fk = '" + this.id + "'";*/
			query = "delete nvgn_kh where nvgn_fk = ?";
			data.clear();
			data.add(this.id);
			this.db.viewQuery(query, data);
			if(this.db.updateQueryByPreparedStatement(query, data)<0)
			{
				db.getConnection().rollback();
				this.msg = "Lỗi query";
				return false;
			}
			
			if(khIds != null)
			{
				for(int m = 0; m < khIds.length; m++)
				{
					/*String sql = "insert into nvgn_kh(nvgn_fk, khachhang_fk) values('" + this.id + "','" + khIds[m] + "')";*/
					String sql = "insert into nvgn_kh(nvgn_fk, khachhang_fk) values(?,?)";
					data.clear();
					data.add(this.id); data.add(khIds[m]);
					this.db.viewQuery(sql, data);
					if(this.db.updateQueryByPreparedStatement(sql, data)<=0)
					{
						db.getConnection().rollback();
						this.msg = "Khong the cap nhat 'nvgn_kh'. Vui lòng liên hệ quản trị viên ";
						return false; 
					}
				}
			}
			
			if (ddkdIds != null && ddkdIds.length >0) {
				int length = ddkdIds.length;
				String _ddkdIds = "";
				for (int i = 0; i < length; i ++) {
					_ddkdIds += ","+ddkdIds[i];
				}
				if (_ddkdIds != null && _ddkdIds.length() > 0) {
					_ddkdIds = _ddkdIds.substring(1);
				}
				
/*				query = "\n insert into nvgn_kh(nvgn_fk, khachhang_fk) " +
						"\n select "+this.id+",pk_seq from khachhang a where trangthai = 1" +
						"\n and exists (select 1 from khachhang_tuyenbh aa inner join tuyenbanhang bb on aa.tbh_fk = bb.pk_seq" +
						"\n where khachhang_fk = a.pk_seq and bb.ddkd_fk in ("+_ddkdIds+") )" +
						"\n and not exists (select 1 from nvgn_kh where khachhang_fk = a.pk_seq and nvgn_fk = "+this.id+") ";*/
				query = "\n insert into nvgn_kh(nvgn_fk, khachhang_fk) " +
						"\n select ?,pk_seq from khachhang a where " +
						"\n exists (select 1 from khachhang_tuyenbh aa inner join tuyenbanhang bb on aa.tbh_fk = bb.pk_seq" +
						"\n where khachhang_fk = a.pk_seq and bb.ddkd_fk in (";
				data.clear();
				data.add(this.id);
				for(int i =0;i<ddkdIds.length;i++){
					if(i==(ddkdIds.length-1)){
						data.add(ddkdIds[i]);
						query += "?";
					}
					else{
						data.add(ddkdIds[i]);
						query += "?,";
					}
				}
				data.add(this.id);
				query+=" ) )";
				query += "\n and not exists (select 1 from nvgn_kh where khachhang_fk = a.pk_seq and nvgn_fk = ?) ";
				
				this.db.viewQuery(query, data);
				if (this.db.updateQueryByPreparedStatement(query, data)<0) {
					geso.dms.center.util.Utility.rollback_throw_exception(db);
					this.msg = "Không thể cập nhật NVGN thông qua NVBH ";
					return false; 
				}
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch(Exception e) {
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			this.msg = "Loi khi cap nhat bang "+e.toString();
			return false; 
		}
		return true;
	}
	
	private void createDdkdRs() 
	{
		this.ddkdlist = db.get("select pk_seq as ddkdId, ten as ddkdTen, dienthoai, diachi from daidienkinhdoanh where npp_fk='" + this.nppId + "' and trangthai = '1'");
	}
	
	private void createTbhRs()
	{
		String sql = "";

		if(this.ddkdId.length() > 0)
		{
			//sql = "select tuyenbanhang.pk_seq as tbhId, diengiai as tbhTen,ddkd.ten, ngaylamviec from tuyenbanhang inner join daidienkinhdoanh ddkd on dkkd.pk_seq=tuyenbanhang.ddkd_fk  where npp_fk = '" + this.nppId + "'";	
			sql="select tuyenbanhang.pk_seq as tbhId,tuyenbanhang.diengiai as tbhTen, ddkd.ten, ngaylamviec "+
			" from tuyenbanhang inner join daidienkinhdoanh ddkd on ddkd.pk_seq =tuyenbanhang.ddkd_fk  "+
			" where tuyenbanhang.npp_fk = '" + this.nppId + "' ";
			
			sql = sql + " and tuyenbanhang.ddkd_fk in ("+ this.ddkdId +") ";
				
			this.tbhlist = db.get(sql);			
		}
		else
		{
			this.tbhlist = null;
		}			
		
		System.out.println("Lay tuyen ban hang la: " + sql + "\n");
	}
	
	private void createKhRs()
	{
		String sql = "";
		if(this.tbhId.length() > 0)
		{
			 
			sql = 	" select distinct a.smartid,a.pk_seq as khId, a.ten as khTen, a.diachi, a.dienthoai from khachhang a, " +
					" khachhang_tuyenbh b, tuyenbanhang c where a.pk_seq = b.khachhang_fk and b.tbh_fk = c.pk_seq and c.npp_fk='" + this.nppId + "'";
 
			sql += " and c.pk_seq in (" + this.tbhId + ")";
			sql = sql + " and a.pk_seq not in (select khachhang_fk from NVGN_KH where nvgn_fk in (select pk_seq from nhanviengiaonhan where npp_fk = '" + this.nppId + "' and trangthai = '1'))";
			System.out.println("Khach hang chua phan tuyen: " + sql + "\n");
			this.khlist = db.get(sql);
	
		}
		else{
			this.khlist = null;
		}
		
	}
	
	public void init() 
	{
		this.getNppInfo();
		String query = "select a.pk_seq as nvgnId, a.ten as nvgnTen, a.trangthai, a.diachi, a.npp_fk as nppId, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua, a.dienthoai ";
		query = query + "from nhanviengiaonhan a inner join nhanvien b on a.nguoitao = b.pk_seq inner join nhanvien c on a.nguoisua = c.pk_seq where a.npp_fk='" + this.nppId + "'";
		ResultSet rs =  db.get(query + " and a.pk_seq='" + this.id + "'");
		System.out.println(query + " and a.pk_seq='" + this.id + "'");
		try
        {
            rs.next();        	
            this.id = rs.getString("nvgnId");
			this.tennv = rs.getString("nvgnTen");
			this.trangthai = rs.getString("trangthai");
			this.diachi = rs.getString("diachi");
			this.dienthoai = rs.getString("dienthoai");			
			this.ngaytao = rs.getString("ngaytao");
			this.nguoitao = rs.getString("nguoitao");
			this.ngaysua = rs.getString("ngaysua");
			this.nguoisua = rs.getString("nguoisua");					
			rs.close();
			
			if(this.tbhId == "")
			{
				//query = "select distinct a.pk_seq as tbhId from tuyenbanhang a, nvgn_kh b, khachhang_tuyenbh c where a.pk_seq = c.pk_seq and c.khachhang_fk=b.khachhang_fk and a.npp_fk='"+ this.nppId+ "'";
				query = "select distinct c.TBH_FK as tbhId from NHANVIENGIAONHAN a inner join NVGN_KH b on a.PK_SEQ = b.NVGN_FK inner join KHACHHANG_TUYENBH c on b.KHACHHANG_FK = c.KHACHHANG_FK where a.PK_SEQ = '" + this.id + "'";
				
				rs = db.get(query);
				int i = 0;
				while(rs.next())
				{
					//this.tbhIds[i] = rs.getString("tbhId");
					this.tbhId += rs.getString("tbhId") + ",";
					i++;
				}
				rs.close();
				
				if(this.tbhId.length() > 0)
					this.tbhId = this.tbhId.substring(0, this.tbhId.length() - 1);
			}
			
			if(this.tbhId.length() > 0)
			{
				/*
				query = "select count( distinct a.ddkd_fk) as num from tuyenbanhang a, nvgn_kh b, khachhang_tuyenbh c where a.pk_seq = c.tbh_fk and c.khachhang_fk=b.khachhang_fk and a.npp_fk='"+ this.nppId + "'" ;
				rs = this.db.get(query);
				rs.next();			
				this.ddkdIds = new String[Integer.valueOf(rs.getString("num")).intValue()];
				rs.close();
				*/
				
				//query = "select distinct a.ddkd_fk as ddkdId from tuyenbanhang a, nvgn_kh b, khachhang_tuyenbh c where a.pk_seq = c.tbh_fk and c.khachhang_fk=b.khachhang_fk and a.npp_fk= '"+ this.nppId + "' and b.nvgn_fk = '" + this.id + "'";
				query = "select DDKD_FK as ddkdId from TUYENBANHANG where PK_SEQ in (" + this.tbhId + ")";
				System.out.println("Query lay ddkd: " + query + "\n");
				rs = this.db.get(query);
				int i = 0;
				while(rs.next())
				{
					//this.ddkdIds[i] = rs.getString("ddkdId");
					this.ddkdId += rs.getString("ddkdId") + ",";
					i++;
				}
				rs.close();
				if(this.ddkdId.length() > 0)
					this.ddkdId = this.ddkdId.substring(0, this.ddkdId.length() - 1);
			}
			
			//khach hang thuoc nhan vien giao nhan nay
			query = "select kh.smartid,kh.pk_seq as khId, kh.ten as khTen, kh.diachi, kh.dienthoai from NVGN_KH a inner join khachhang kh on a.khachhang_fk = kh.pk_seq ";
			query = query + "where a.nvgn_fk = '" + this.id + "'";
			System.out.println(query);
			this.khSelectedList = this.db.get(query);
			createRS();
			
       	}
        catch(Exception e){}
        finally{try {
			if(rs != null)
				rs.close();
		} catch (Exception e2) {
			// TODO: handle exception
		}}
        
//        this.createDdkdIds();
		this.createDdkdRs();
		this.createTbhRs();	
		this.createKhRs();
	}

	public void createRS() 
	{
		this.getNppInfo();
		this.createDdkdRs();
		this.createTbhRs();
		this.createKhRs();
	}
	
	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	public void DBclose()
	{
		try 
		{
			if(this.ddkdlist != null)
				this.ddkdlist.close();
			if(this.khlist != null)
				this.khlist.close();
			if(this.tbhlist != null)
				this.tbhlist.close();
			
			if(this.khSelectedList != null)
				this.khSelectedList.close();
			if(this.db != null)
				this.db.shutDown();
			
		} 
		catch(Exception e) {}
	}
	public ResultSet getKhSelectedList() 
	{
		return this.khSelectedList;
	}

	public void setKhSelectedList(ResultSet khSelectlist)
	{
		this.khSelectedList = khSelectlist;
	}

	public String getTbhId() 
	{
		return this.tbhId;
	}

	public void setTbhId(String ddkdId) 
	{
		this.tbhId = ddkdId;
	}

		
}
