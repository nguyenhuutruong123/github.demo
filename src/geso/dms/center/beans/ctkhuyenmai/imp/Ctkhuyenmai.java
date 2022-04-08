package geso.dms.center.beans.ctkhuyenmai.imp;

import geso.dms.center.beans.ctkhuyenmai.*;
import geso.dms.center.beans.dieukienkhuyenmai.ISanpham;
import geso.dms.center.beans.dieukienkhuyenmai.imp.Sanpham;
import geso.dms.center.db.sql.Idbutils;
import geso.dms.center.util.OracleConnUtils;
import geso.dms.center.util.Utility;
import geso.dms.distributor.db.sql.dbutils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import jxl.write.Label;

public class Ctkhuyenmai implements ICtkhuyenmai
{
	String userId;
	String id;
	String scheme;
	String schemeErp = "";
	String diengiai;
	String tungay;
	String denngay;
	String type;
	String ngansach;
	String dasudung;
	String nguoitao;
	String nguoisua;
	String ngaytao;
	String ngaysua;
	String msg;
	ResultSet Dsnpp;
	ResultSet DsnppIds;
	String[] npp;
	List<IDieukienkm> dkkmList = new ArrayList<IDieukienkm>();
	List<ITrakm> trakmList = new ArrayList<ITrakm>();
	ResultSet trakmRs;
	String trakmId;
	ResultSet loaiKhRs;
	String loaiKhIds = "";
	String ngayngunghoatdong="";
	
	//Search trakhuyenmai
	String tkm_diengiai;
	String tkm_tungay;
	String tkm_denngay;
	String ngayds;
	String ngayktds;
	//Search dieukienkhuyenmai
	String dkkm_diengiai;
	String dkkm_tungay;
	String dkkm_denngay;
	String khoId;
	ResultSet khoIds;
	dbutils db;
	ResultSet Nhomkhnpp;
	String NhomkhnppId;
	String vungId;
	String khuvucId;
	ResultSet vungs;
    ResultSet khuvucs;
    String load;
    
    ResultSet kenhRs;
	String kbhIds;
	String kbhId = "";
	
	ResultSet nhomspRs;
	
	//Loai ngan sach default 1 -- Ap phan bo
	String loains;
	String active;
	
	String phanTramToida;
	
	boolean dacotrongdh;
	
	String loaikhuyenmai;
	String tylevoiPrimary;
	String nppTuchay;
	String ngansachkehoach;
	
	String mucphanbo;
	Utility util;
	String apdungcho;
	
	public Ctkhuyenmai(String[] param)
	{
		this.id = param[0];
		this.scheme = param[1];
		this.diengiai = param[2];
		this.tungay = param[3];
		this.denngay = param[4];
		this.type = param[5];
		this.ngansach = param[6];
		this.dasudung = param[7];
		this.ngaytao = param[8];
		this.nguoitao = param[9];
		this.ngaysua = param[10];
		this.nguoisua = param[11];		
		this.nguoisua = param[11];		
		this.msg = "";
			
		this.trakmId = "";
		this.tkm_diengiai = "";
		this.tkm_tungay = "";
		this.tkm_denngay = "";
		
		this.dkkm_diengiai = "";
		this.dkkm_tungay = "";
		this.dkkm_denngay = "";
		this.khoId= "";
		this.vungId ="";
		this.khuvucId ="";
		this.ngayds ="";
		this.ngayktds="";
		this.kbhIds = "";
		this.load = "0";
		this.loains = "1";
		this.active = "0";
		this.dacotrongdh = false;
		this.loaikhuyenmai = "0";  //0 sencond, 1 Primary, 2 Ap dung cho ca 2
		this.tylevoiPrimary = "0";
		
		this.ngansachkehoach = "";
		
		this.phanTramToida = "100";
		this.nppTuchay = "0";
		this.mucphanbo = "0";
		this.apdungcho = "0";
		db = new dbutils();
		util = new Utility();
	}
	
	public Ctkhuyenmai(String id)
	{
		this.id = id;
		this.scheme = "";
		this.diengiai = "";
		this.tungay = "";
		this.denngay = "";
		this.type = "";
		this.ngansach = "";
		this.dasudung = "";
		this.ngaytao = "";
		this.nguoitao = "";
		this.ngaysua = "";
		this.nguoisua = "";
		this.msg = "";
		this.NhomkhnppId ="";
		this.trakmId = "";
		this.tkm_diengiai = "";
		this.tkm_tungay = "";
		this.tkm_denngay = "";
		
		this.dkkm_diengiai = "";
		this.dkkm_tungay = "";
		this.dkkm_denngay = "";
		this.khoId= "";
		this.vungId ="";
		this.khuvucId ="";
		this.load = "0";
		this.ngayds ="";
		this.ngayktds="";
		this.kbhIds = "";
		this.loains = "1";
		this.active = "0";
		this.dacotrongdh = false;
		this.loaikhuyenmai = "0";  //0 sencond, 1 Primary, 2 Ap dung cho ca 2
		this.tylevoiPrimary = "0";
		this.phanTramToida = "100";
		this.nppTuchay = "0";
		this.mucphanbo = "0";
		
		this.ngansachkehoach = "";
		this.apdungcho = "0";
		this.db = new dbutils();
		util = new Utility();
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
	
	public String getScheme()
	{
		return this.scheme;
	}
	
	public void setScheme(String scheme)
	{
		this.scheme = scheme;
	}
	
	public void setDiengiai(String diengiai) 
	{
		this.diengiai = diengiai;
	}
	
	public String getDiengiai()
	{
		return diengiai;
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
	
	public String getType()
	{
		return this.type;
	}
	
	public void setType(String type)
	{
		this.type = type;
	}
	
	public String getNgansach()
	{
		return this.ngansach;
	}
	
	public void setNgansach(String ngansach)
	{
		this.ngansach = ngansach;
	}
	
	public String getDasudung()
	{
		return this.dasudung;
	}
	
	public void setDasudung(String dasudung)
	{
		this.dasudung = dasudung;
	}
	
	public List<IDieukienkm> getDkkmList() 
	{		
		return this.dkkmList;
	}
	
	public void setDkkmList(List<IDieukienkm> dkkmlist) 
	{
		this.dkkmList = dkkmlist;		
	}

	public ResultSet getTrakmRs()
	{		
		return this.trakmRs;
	}
	
	public void setTrakmRs(ResultSet trakmRs) 
	{
		this.trakmRs = trakmRs;	
	}
	
	public String getTrakmId()
	{		
		return this.trakmId;
	}
	
	public void setTrakmId(String trakmId) 
	{
		this.trakmId = trakmId;		
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
		
	public boolean CreateCtkm() 
	{  
		if(!kiemtra_scheme(this.db,this.scheme,this.id))
		{		
			this.msg = "Scheme khuyến mại đã tồn tại, vui lòng nhập lại";
			return false;
		}
		
		
		if(!apdungcho.trim().equals("2") )
		if(!kiemtra_schemErp())
		{		
			this.msg = "SchemeERP và Kho đã tồn tại, vui lòng nhập lại ";
			return false;
		}
		
		dbutils db = new dbutils();

		this.ngaytao = getDateTime();
		this.nguoitao = this.userId;
		
		try 
		{
			String query;
			db.getConnection().setAutoCommit(false);
			if(this.NhomkhnppId.length() <= 0 )
			{
				this.NhomkhnppId = "null";
			}
			String _nhom_kh_loai_tru = "null";
			if(this.nhom_kh_loai_tru.length()  > 0 )
			{
				_nhom_kh_loai_tru =this.nhom_kh_loai_tru ;
			}
				
			query = "Insert into Ctkhuyenmai(nhom_kh_loai_tru,scheme, diengiai, tungay, denngay, loaict, ngansach, ngaytao, nguoitao, ngaysua, nguoisua,kho_fk,nhomkhnpp_fk, loaingansach, tilevoiprimary, NPPTUCHAY,ApDUNGCHODHLE,PHANBOTHEODONHANG, LEVEL_PHANBO, IO,tungay_dathang,denngay_dathang, ngansachkehoach, apdungcho,SCHEMEERP) " +
					"values("+_nhom_kh_loai_tru+",'" + this.scheme + "', N'" + this.diengiai + "', '" + this.tungay + "' , '" + this.denngay + "' , '" + this.type + "', '" + this.ngansach + "', " +
							"'" + this.ngaytao + "', '" + this.nguoitao + "', '" + this.ngaytao + "', '" + this.nguoitao + "','"+ this.khoId +"'," + this.NhomkhnppId + ", '" + this.loains + "', '" + this.tylevoiPrimary + "', '" + this.nppTuchay + "','" + this.apDungDHLe + "','"+this.phanbotheoDH+"', '" + this.mucphanbo + "', N'" + this.io + "','"+this.TuNgay_DatHang+"','"+this.DenNgay_DatHang+"', '"+this.ngansachkehoach+"', '" + this.apdungcho + "','" + this.schemeErp + "')";


			System.out.println(query);
			if(!db.update(query))
			{
				db.getConnection().rollback();
				this.msg = "Khong the tao moi 'Ctkhuyenmai', " + query;
				return false; 
			}
			
			//lay dkkm current
			String ctkmCurrent = "";
			query = "select IDENT_CURRENT('Ctkhuyenmai') as ctkmId";
			System.out.println(query);
			ResultSet rsCtkm = this.db.get(query);						
			rsCtkm.next();
			ctkmCurrent = rsCtkm.getString("ctkmId");
			
			rsCtkm.close();
			if(this.loaiKhIds.trim().length() > 0)
			{
				query = "Insert CTKHUYENMAI_LOAIKH(ctkm_fk, LOAIKH_Fk)  " +
						"select '" + ctkmCurrent + "', pk_seq from LoaiCuaHang where pk_seq >= 0  ";
		    	if(this.loaiKhIds.trim().length() > 0)
		    		query += " and pk_seq in ( " + this.loaiKhIds + " ) ";
		    	
				if(!db.update(query))
				{
					db.getConnection().rollback();
					this.msg = "4.Khong the tao moi 'CTKHUYENMAI_LOAIKH', " + query;
					return false; 
				}
			}
			
			if(this.type.equals("3"))
			{
				query = "insert into NGAYTINHDSKM(ctkm_fk, ngayds, ngayktds, phantramtoida) " +
						"values('" + ctkmCurrent + "','" + this.ngayds + "','" + this.ngayktds + "', '" + this.phanTramToida + "')";
				if(!db.update(query))
				{
					db.getConnection().rollback();
					this.msg = "Khong the tao moi 'NGAYTINHDSKM', " + query;
					return false; 
				}	
			}
			
			for(int i = 0; i < this.dkkmList.size(); i++)
			{
				Dieukienkm dkkm = (Dieukienkm)this.dkkmList.get(i);	
				query = "Insert into ctkm_dkkm(ctkhuyenmai_fk, dkkhuyenmai_fk, pheptoan, thutudieukien) values('" + ctkmCurrent + "', '" + dkkm.getId() + "', '" + dkkm.getPheptoan() + "', '" + dkkm.getThutudk() + "')";
				if(!db.update(query))
				{
					db.getConnection().rollback();
					this.msg = "2.Khong the tao moi 'ctkm_dkkm', " + query;
					return false; 
				}
			}
			
			for(int i = 0; i < this.trakmList.size(); i++)
			{
				Trakm tkm = (Trakm)this.trakmList.get(i);	
				
				if(tkm.getId().length()>0  && tkm.getPheptoan().length()>0 )
				{
					query = "Insert into ctkm_trakm(ctkhuyenmai_fk, trakhuyenmai_fk, pheptoan, thutu) values('" + ctkmCurrent + "', '" + tkm.getId() + "', '" + tkm.getPheptoan() + "', '" + tkm.getThutudk() + "')";
					if(!db.update(query))
					{
						db.getConnection().rollback();
						this.msg = "3.Khong the tao moi 'ctkm_trakm', " + query;
						return false; 
					}
				}
				
			}
			

			query="delete from ctkm_npp where ctkm_fk = '" + ctkmCurrent + "'";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				this.msg = "4.Khong the tao moi 'ctkm_npp', " + query;
				return false; 
			}
			
			System.out.println(query);
	    	for(int i = 0; i < this.npp.length; i++)
	    	{
	    		query ="insert into ctkm_npp values('" + ctkmCurrent +"','"+ this.npp[i] +"','1',"+this.nguoitao+",'"+this.nguoitao+"','"+this.ngaytao+"','"+this.ngaytao+"', dbo.GetLocalDate(DEFAULT))";
	    		if(!db.update(query))
	    		{
	    			db.getConnection().rollback();
	    			this.setMessage("5.Khong The tao moi Ctkm loi :"+ query);
	    			return false;
	    		}
	    	}
	    	if(this.kbhIds.length() > 0)
	    	{
	    		query = "Insert CTKHUYENMAI_KBH(ctkm_fk, kbh_fk)  " +
					"select '" + ctkmCurrent + "', pk_seq from KenhBanHang where pk_seq > 0 and pk_seq in ( " + this.kbhIds + " ) ";
	    		
	    	
				if(!db.update(query))
				{
					db.getConnection().rollback();
					this.msg = "4.Khong the tao moi 'CTKHUYENMAI_KBH', " + query;
					return false; 
				}
	    	}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return true;
		} 
		catch (Exception e) 
		{
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			this.msg = "Khong them moi chuong trinh khuyen mai nay, vui long kiem tra lai. Loi dong lenh sau :" + e.toString();
			return false;
		}
	}

	boolean kiemtra_scheme()
	{
		String sql = "select count(*) as num  from ctkhuyenmai where scheme ='" + this.scheme + "'";
		ResultSet rs = db.get(sql);
		try 
		{
			rs.next();
			if(rs.getString("num").equals("0")){
				rs.close();
				return true;
			}
		
		} 
		catch (SQLException e) {}
		return false;
		
	}
	
	boolean kiemtra_schemErp()
	{
		String sql = "select count(*) as num ,KHO_FK   from ctkhuyenmai where SCHEMEERP = N'" + this.schemeErp + "' group by KHO_FK";
		ResultSet rs = db.get(sql);
		
		try 
		{
			if(rs != null)
			{
				if(rs.next())
				{
					if(rs.getString("num").equals("0")){
						rs.close();
						return true;
					}
					if(rs.getString("KHO_FK").equals(this.khoId))
					{
						rs.close();
						return true;
					}
				}
				else
					return true;
			}
			else
				return true;
					
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
		
	}
	
	boolean kiemtra_schemErpUpdate()
	{
		String sql = "select count(*) as num  from ctkhuyenmai where SCHEMEERP = N'" + this.schemeErp + "' and pk_seq <> '"+this.id+"' ";
		ResultSet rs = db.get(sql);
		
		try 
		{
			rs.next();
			if(rs.getString("num").equals("0")){
				rs.close();
				return true;
			}
		
		} 
		catch (SQLException e) {}
		return false;
		
	}
	public static boolean  kiemtra_scheme(Idbutils db,String scheme ,String Id)
	{
		String sql = "select count(*) as num  from ctkhuyenmai where scheme ='" + scheme + "'";
		if(Id != null && Id.trim().length() > 0)
			sql += " and pk_seq != " + Id + " ";
		
		int count = 0;
		ResultSet rs = db.get(sql);
		try 
		{
			rs.next();
			count = rs.getInt("num");
		
		} 
		catch (Exception e) 
		{
			return false;
		}
		
		if(count > 0)
			return false;
		
		return true;
		
	}
	
	public boolean updateNgayNgungHoatDong() {
		try {
			if(id != null && !id.equals("")) {
				String query = "update CTKHUYENMAI set denngay='2010-01-01', ngayngunghoatdong=GETDATE() where pk_seq=" + this.id;
				db.getConnection().setAutoCommit(false);
				if(!db.update(query)) {
					db.getConnection().rollback();
					return false;
				}
				db.getConnection().commit();
				db.getConnection().setAutoCommit(true);
			}
			else {
				return false;
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
		return true;
	}
	// Kiểm tra cột ngayngunghoatdong có null hay không
	// True -> ngayngunghoatdong = null
	public boolean checkNgayNgungHoatDong() {
		try {
			String query = "select * from CTKHUYENMAI where pk_seq=" + this.id + " and ngayngunghoatdong is null";
			ResultSet set = db.get(query);
			if(set != null) {
				if(set.next()) {
					return true;
				}
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}
		return false;
	}
	public String getNgayngunghoatdong() {
		try {
			if(id != null && !id.equals("")) {
				String query = "select ngayngunghoatdong from CTKHUYENMAI where pk_seq=" + this.id;
				ResultSet set = db.get(query);
				if(set != null) {
					if(set.next()) {
						ngayngunghoatdong = set.getString("ngayngunghoatdong");
						if(ngayngunghoatdong == null) {
							ngayngunghoatdong = "";
						}
						else {
							ngayngunghoatdong = ngayngunghoatdong.trim().split(" ")[0];
						}
						
						
					}
				}
			}
			
		}
		catch(Exception ex) {
			ex.printStackTrace();
			
		}
		return ngayngunghoatdong;
	}

	public boolean UpdateCtkm() 
	{
		if(!apdungcho.trim().equals("2") )
		if(!kiemtra_schemErp())
		{		
			this.msg = "SchemeERP và Kho đã tồn tại, vui lòng nhập lại ";
			return false;
		}
		if(!kiemtra_scheme(this.db,this.scheme,this.id))
		{		
			this.msg = "Scheme khuyến mại/Tích lũy đã tồn tại, vui lòng nhập lại";
			return false;
		}
		this.ngaysua = getDateTime();
		this.nguoisua = this.userId;
		try 
		{
			db.getConnection().setAutoCommit(false);
			boolean flag = false;
			String	sql="select count(*) as num from donhang_ctkm_trakm where  ctkmid='" + id+ "' and DONHANGID in (select PK_SEQ from DONHANG where TRANGTHAI!=2) ";
			System.out.println(sql);
	    	ResultSet rs = db.get(sql);
			try 
			{
				rs.next();
				if(rs.getInt("num") > 0)
				{
					flag = true;
					rs.close();
				}
				rs.close();
			}
			catch (Exception e) {e.printStackTrace();}
			
			sql="select count(*) as num from ERP_DONDATHANG_CTKM_TRAKM where  ctkmid='" + id+ "' and DONDATHANGID in (select PK_SEQ from ERP_DONDATHANG where TRANGTHAI!=3) ";
			System.out.println(sql);
	    	 rs = db.get(sql);
			try 
			{
				rs.next();
				if(rs.getInt("num") > 0)
				{
					flag = true;
					rs.close();
				}
				rs.close();
			}
			catch (Exception e) {e.printStackTrace();}
			boolean ktpb = true;
			sql="select COUNT(*) as ispb  "+
					"		from PHANBOKHUYENMAI pb where pb.CTKM_FK= '"+this.id+"' "+
					"						and NGANSACH!=0 ";
			System.out.println(sql);
	    	 rs = db.get(sql);
			try 
			{
				rs.next();
				if(rs.getInt("ispb") > 0)
				{
					ktpb = false;
					rs.close();
				}
				rs.close();
			}
			catch (Exception e) {e.printStackTrace();}
			//khuyen mai tich luy
			if(this.loaikhuyenmai.trim().equals("3"))
			{
				sql="select * from DANGKYKM_TICHLUY where ctkm_fk="+this.id;
				rs=db.get(sql);
				if(rs.next())
				{
					flag = true;
				}
				rs.close();
			}
			boolean flagerp = false;
			if(!this.type.equals("4"))
			{
					try 
					{
						OracleConnUtils or = new OracleConnUtils();
						sql="select count(a.SHEME) as num1, count(b.SHEME) as num2 from apps.tbl_hoadon a,apps.tbl_phieuxuatkho b where ( a.SHEME= '"+this.schemeErp+"' or b.SHEME= '"+this.schemeErp+"' )";
						rs = or.get(sql);
						rs.next();
						if(rs.getInt("num1") > 0 && rs.getInt("num2") > 0)
						{
							flagerp = true;
							rs.close();
						}
						rs.close();
						or.shutDown();
					}
					catch (Exception e) 
					{
						e.printStackTrace();
						
					}
			}

			String queryl = "insert into ctkhuyenmai_log (scheme, tungay, denngay, ngaysua, nguoisua) select scheme, tungay, denngay, dbo.GetLocalDate(DEFAULT),'"+getUserId()+"'  from ctkhuyenmai "+
					"where pk_seq = '" + this.id + "'";
					if(!db.update(queryl))
					{
						geso.dms.center.util.Utility.rollback_throw_exception(db);
						this.msg = "1.Khong the cap nhat 'Ctkhuyenmai', " + queryl;
						return false; 
					}
			
					
					
			
			// 1. CTKM khi chưa đồng bộ hóa đơn/ PXK nào có scheme ERP thì dc sửa mã này
			if(!flagerp)
			{
				String query = "Update Ctkhuyenmai set SCHEMEERP = N'" + this.schemeErp + "' " +
						"where pk_seq = '" + this.id + "'";
						System.out.println("Truong Hop 1 :"+query);
						if(!db.update(query))
						{
							geso.dms.center.util.Utility.rollback_throw_exception(db);
							this.msg = "1.Khong the cap nhat 'Ctkhuyenmai', " + query;
							return false; 
						}
			}
			if(!flag )
			{
				if(this.NhomkhnppId.length() == 0)
					this.NhomkhnppId = "null";
				
				String _nhom_kh_loai_tru = "null";
				if(this.nhom_kh_loai_tru.length()  > 0 )
				{
					_nhom_kh_loai_tru =this.nhom_kh_loai_tru ;
				}
				
				this.ngansach= this.ngansach.replaceAll(",","");
				String query = "Update Ctkhuyenmai set  nhom_kh_loai_tru = "+_nhom_kh_loai_tru+",  tungay_dathang='"+this.TuNgay_DatHang+"',denngay_dathang='"+this.DenNgay_DatHang+"' ,  IO=N'"+this.io+"', scheme = '" + this.scheme + "', diengiai = N'" + this.diengiai + "', tungay = '" + this.tungay + "', " +
								"denngay = '" + this.denngay + "', loaict = '" + this.type + "', ngansach = '" + this.ngansach + "', ngaysua = '" + this.ngaysua + "', nguoisua = '" + this.nguoisua + "', ngansachkehoach = '"+this.ngansachkehoach+"'," +
								"kho_fk ='"+ this.khoId +"',nhomkhnpp_fk ="+ this.NhomkhnppId +", tilevoiprimary = '" + this.tylevoiPrimary + "', NPPTUCHAY = '" + this.nppTuchay + "', ApDUNGCHODHLE = '" + this.apDungDHLe + "',PHANBOTHEODONHANG='"+this.phanbotheoDH+"', LEVEL_PHANBO = '" + this.mucphanbo + "', apdungcho = '" + this.apdungcho + "' " +
								"where pk_seq = '" + this.id + "'";
				System.out.println("Truong Hop 1 :"+query);
				if(!db.update(query))
				{
					geso.dms.center.util.Utility.rollback_throw_exception(db);
					this.msg = "1.Khong the cap nhat 'Ctkhuyenmai', " + query;
					return false; 
				}
				if(ktpb == false)
				{
					query = "select count(*) as so from Ctkhuyenmai where loaingansach <> '"+loains+"' and pk_seq = '" + this.id + "' ";
					ResultSet rsckpb = db.get(query);
					if(rsckpb != null)
					{
						rsckpb.next();
						if(rsckpb.getInt("so") > 0)
						{
							query = "delete from phanbokhuyenmai where ctkm_FK = '" +this.id + "'";
							if(!flag)
							if(!db.update(query))
							{
								geso.dms.center.util.Utility.rollback_throw_exception(db);
								this.msg = "1.Khong the cap nhat 'Ctkhuyenmai', " + query;
								return false; 
							}
							
							query = "Update Ctkhuyenmai set LOAINGANSACH ='"+this.loains+"' " +
									"where pk_seq = '" + this.id + "'";
							System.out.println("Truong Hop 1 :"+query);
							if(!flag)
							if(!db.update(query))
							{
								geso.dms.center.util.Utility.rollback_throw_exception(db);
								this.msg = "1.Khong the cap nhat 'Ctkhuyenmai', " + query;
								return false; 
							}
							
						}
						rsckpb.close();
					}
					
				}
				else
				{
					query = "Update Ctkhuyenmai set LOAINGANSACH ='"+this.loains+"' " +
							"where pk_seq = '" + this.id + "'";
					System.out.println("Truong Hop 1 :"+query);
					if(!flag)
					if(!db.update(query))
					{
						geso.dms.center.util.Utility.rollback_throw_exception(db);
						this.msg = "1.Khong the cap nhat 'Ctkhuyenmai', " + query;
						return false; 
					}
				}
				if(this.type.equals("3"))
				{
					 sql ="update NGAYTINHDSKM set NGAYDS = '"+ this.ngayds +"',NGAYKTDS = '"+ this.ngayktds +"', phantramtoida = '" + this.phanTramToida + "' where ctkm_fk ='"+ this.id +"'";
					 if(!db.update(sql))
					 {
						geso.dms.center.util.Utility.rollback_throw_exception(db);
						this.msg = "2.Khong the tao moi 'NGAYTINHDSKM', " + query;
						return false; 
					 }	
				}
				
				query = "delete from ctkm_dkkm where ctkhuyenmai_fk = '" + this.id + "'";
				db.update(query);
				
				for(int i = 0; i < this.dkkmList.size(); i++)
				{
					Dieukienkm dkkm = (Dieukienkm)this.dkkmList.get(i);	
					query = "Insert into ctkm_dkkm(ctkhuyenmai_fk, dkkhuyenmai_fk, pheptoan, thutudieukien) values('" + this.id + "', '" + dkkm.getId() + "', '" + dkkm.getPheptoan() + "', '" + dkkm.getThutudk() + "')";
					if(!db.update(query))
					{
						geso.dms.center.util.Utility.rollback_throw_exception(db);
						this.msg = "3.Khong the tao moi 'ctkm_dkkm', " + query;
						return false; 
					}
				}
				
				query = "delete from ctkm_trakm where ctkhuyenmai_fk = '" + this.id + "'";
				db.update(query);
				
				for(int i = 0; i < this.trakmList.size(); i++)
				{
					Trakm tkm = (Trakm)this.trakmList.get(i);	
					if(tkm.getId().length()>0 &&  tkm.getPheptoan().length()>0 )
					{
						query = "Insert into ctkm_trakm(ctkhuyenmai_fk, trakhuyenmai_fk, pheptoan, thutu) values('" + this.id + "', '" + tkm.getId() + "', '" + tkm.getPheptoan() + "', '" + tkm.getThutudk() + "')";
						if(!db.update(query))
						{
							db.getConnection().rollback();
							this.msg = "4.Khong the tao moi 'ctkm_trakm', " + query;
							return false; 
						}
					}
					
				}
				query = "delete CTKHUYENMAI_LOAIKH where ctkm_fk = '" + this.id + "'";
				db.update(query);
				if(this.loaiKhIds.trim().length() > 0)
				{
					query = "Insert CTKHUYENMAI_LOAIKH(ctkm_fk, LOAIKH_Fk)  " +
							"select '" + this.id + "', pk_seq from LoaiCuaHang where pk_seq >= 0  ";
			    	if(this.loaiKhIds.trim().length() > 0)
			    		query += " and pk_seq in ( " + this.loaiKhIds + " ) ";
			    	
					if(!db.update(query))
					{
						db.getConnection().rollback();
						this.msg = "4.Khong the tao moi 'CTKHUYENMAI_LOAIKH', " + query;
						return false; 
					}
				}
				/*query = "Insert into ctkm_trakm(ctkhuyenmai_fk, trakhuyenmai_fk) values('" + this.id + "', '" + this.trakmId + "')";
				if(!db.update(query))
				{
					geso.dms.center.util.Utility.rollback_throw_exception(db);
					this.msg = "Khong the tao moi 'ctkm_trakm', " + query;
					return false; 
				}		*/
				
				query = "delete ctkm_npp where ctkm_fk = '" + this.id + "'";
				db.update(query);
				query = "delete CTKHUYENMAI_KBH where ctkm_fk = '" + this.id + "'";
				db.update(query);
				if(this.kbhIds.length() > 0)
		    	{
		    		query = "Insert CTKHUYENMAI_KBH(ctkm_fk, kbh_fk)  " +
						"select '"+ this.id + "', pk_seq from KenhBanHang where pk_seq > 0 and pk_seq in ( " + this.kbhIds + " ) ";
		    		
		    	
					if(!db.update(query))
					{
						db.getConnection().rollback();
						this.msg = "4.Khong the tao moi 'CTKHUYENMAI_KBH', " + query;
						return false; 
					}
		    	}
			}
			else
			{
				
				if(this.loaikhuyenmai.trim().equals("3")){
					String query	 =  " Update Ctkhuyenmai set  diengiai = N'" + this.diengiai + "',denngay = '" + this.denngay + 
										" ' , ngaysua = '" + this.ngaysua + "', nguoisua = '" + this.nguoisua + "' " +
										" where pk_seq = '" + this.id + "' and  '"+this.denngay+"' >= denngay";
					System.out.println("Update Truong Hop 2"+query);
					if(!db.update(query))
					{
						db.getConnection().rollback();
						this.msg = "II. Khong the cap nhat  CT Khuyen Mai, " + query;
						return false; 
					}
				}else{
				//kiem tra denngay cua ctkhuyenmai co lon hon ngay lon nhat cua don hang khong?
						String query="select max(ngaynhap)  as maxngaynhap from donhang  a inner join donhang_ctkm_trakm b on "+
									" a.pk_seq=b.donhangid where    ctkmid="+this.id +
									" having max(ngaynhap) <='"+this.denngay+"'"; 
						System.out.println("Check Ngay 1 :"+query);
						
						 rs = db.get(query);
						try 
						{
							
							if(rs.next()){
								    
								
									String maxngaynhap = rs.getString("maxngaynhap");
									query = "Update Ctkhuyenmai set DienGiai=N'"+this.diengiai+"',denngay = '" + this.denngay + "' , ngaysua = '" + this.ngaysua + "', nguoisua = '" + this.nguoisua + "' where pk_seq = '" + this.id + "' and '"+maxngaynhap+"' <= '"+this.denngay+"' ";
									System.out.println("Update Truong Hop 2"+query);
									if(!db.update(query))
									{
										db.getConnection().rollback();
										this.msg = "II. Khong the cap nhat  CT Khuyen Mai, " + query;
										return false; 
									}
									rs.close();
								}
							else{
								//this.msg = "II. Khong the cap nhat CT Khuyen Mai, Da Co Nha Phan Phoi Ap Chuong Trinh Khuyen Mai Toi Ngay Nay";
								//return false;
								query="Update Ctkhuyenmai set diengiai = N'" + this.diengiai + "' where pk_seq = '" + this.id + "'";
								if(!db.update(query))
								{
									geso.dms.center.util.Utility.rollback_throw_exception(db);
									this.msg = "1.Khong the cap nhat 'Ctkhuyenmai', " + query;
									return false; 
								}
							}
							rs.close();
						}
						catch (Exception e) {
							geso.dms.center.util.Utility.rollback_throw_exception(db);
							this.msg = "II. Khong the cap nhat CT Khuyen Mai, " + query;
							return false;
						}
				}
			}
			
			String query="UPDATE Ctkm_npp SET CHON=0,NGAYSUA='"+this.ngaysua+"',nguoisua='"+this.nguoisua+"' where ctkm_fk = '" + this.id + "' and chon='1'";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				this.msg = "II. Khong the cap nhat  CT Khuyen Mai, " + query;
				return false; 
			}
			
			for(int i = 0; i < this.npp.length; i++)
			{
				query ="insert into ctkm_npp values('" + this.id +"','"+ this.npp[i] +"','1','"+this.nguoisua+"','"+this.nguoisua+"','"+this.ngaysua+"','"+this.ngaysua+"', dbo.GetLocalDate(DEFAULT))";
				if(!db.update(query))
				{
					query="update ctkm_npp set chon='1',nguoisua='"+this.nguoisua+"',ngaysua='"+this.ngaysua+"', thoigian = dbo.GetLocalDate(DEFAULT) where npp_fk='"+this.npp[i]+"' and ctkm_fk='"+this.id+"' ";
					if(!db.update(query))
					{
						db.getConnection().rollback();
						this.msg = "Khong The Cap Nhat 'CTKM_NPP', " + query;
						return false; 
					}
				}
			}
			
			String sqlCheck = " SELECT COUNT(*) NUM FROM DONHANG DH  " +
					     	" INNER JOIN DONHANG_CTKM_TRAKM CT ON CT.DONHANGID = DH.PK_SEQ WHERE CT.CTKMID = '" + this.id +"' " +
					     	" AND NOT EXISTS ( SELECT 1 FROM CTKM_NPP L WHERE L.CTKM_FK = CT.CTKMID AND L.NPP_FK = DH.NPP_FK AND L.CHON = '1') "; 
			ResultSet rsCheck = this.db.get(sqlCheck);
			rsCheck.next();
			if(rsCheck.getInt("NUM") > 0)
			{
				this.db.getConnection().rollback();
				this.msg = "Không thể bỏ nhà phân phối đã áp CTKM này ra khỏi danh sách.";
				return false; 
			}
		
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return true;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			this.msg="Loi xay Ra Trong Qua Trinh Cap Nhat :"+ e.toString();
			return false; 
	}
	
	}

	public void createRS() 
	{
		CreateNhomkhnpp();
		CreateVung();
		String query = "select pk_seq as loaiId,diengiai as loaiten from loaicuahang ";
		this.loaiKhRs = db.get(query);
		this.kenhRs = db.getScrol("select pk_seq, ten from kenhbanhang where trangthai = '1' and pk_seq in "+util.quyen_kenh(userId));
		query = "select pk_seq as nspId, diengiai as nspTen from nhomsanphamkm where trangthai='1'";
		this.nhomspRs = db.getScrol(query);	
		
		System.out.println("Lay nhom san pham: " + query);
	}

	private void createDkkmList()
	{
		String query = "select a.pk_seq as dkkmId, a.diengiai, cast(isnull(a.tongluong, 0) as numeric(18, 0)) as tongluong, " +
						"cast(isnull(a.tongtien, 0) as numeric(18, 0)) as tongtien, " +
						"isnull(b.pheptoan, '2') as pheptoan, b.thutudieukien, a.loai, a.isThung " +
					"from dieukienkhuyenmai a inner join ctkm_dkkm b on a.pk_seq = b.dkkhuyenmai_fk " +
					"where b.ctkhuyenmai_fk = '" + this.id + "' order by b.thutudieukien asc";
		
		System.out.println("Khoi tao dieu kien khuyen mai: " + query);
		ResultSet rs = db.get(query);
		List<IDieukienkm> listDkkm = new ArrayList<IDieukienkm>();
		if(rs != null)
		{
			try 
			{
				IDieukienkm dkkm = null;
				while(rs.next())
				{
					String dkkmId = rs.getString("dkkmId");
					String diengiai = rs.getString("diengiai");
					String loai = rs.getString("loai");
					String tongluong = rs.getString("tongluong");
					String tongtien = rs.getString("tongtien");
					String pheptoan = rs.getString("pheptoan");
					String thutudk = rs.getString("thutudieukien");
					dkkm = new Dieukienkm(dkkmId, diengiai, tongluong, tongtien, pheptoan, thutudk);
					
					if(rs.getString("isThung").equals("1") )
						dkkm.setTheothung(true);
					
					IDieukienDetail dkkmDetail;
					dkkmDetail = new DieukienDetail();
					dkkmDetail.setDiengiai(diengiai);
					dkkmDetail.setLoaidieukien(loai);
					
					if(Double.parseDouble(tongluong) > 0 || (Double.parseDouble(tongluong) == 0 && Double.parseDouble(tongtien) == 0) )
					{
						dkkmDetail.setSotong(tongluong);
						dkkmDetail.setHinhthuc("1");
					}
					else
					{
						dkkmDetail.setSotong(tongtien);
						dkkmDetail.setHinhthuc("2");
					}
					
					//Khoi tao SP
					query = "select b.PK_SEQ as spId, b.MA as spMa, b.TEN as spTen, ISNULL(a.soluong, 0) as soluong   " +
							"from DIEUKIENKM_SANPHAM a inner join SANPHAM b on a.SANPHAM_FK = b.PK_SEQ where a.DIEUKIENKHUYENMAI_FK = '" + dkkmId + "'";
					ResultSet spDetail = db.get(query);
					
					List<ISanpham> sp_dkkmList = new ArrayList<ISanpham>();
					while(spDetail.next())
					{
						ISanpham sp_dkkm = new Sanpham();
						sp_dkkm.setId(spDetail.getString("spId"));
						sp_dkkm.setMasanpham(spDetail.getString("spMa"));
						sp_dkkm.setTensanpham(spDetail.getString("spTen"));
						if(spDetail.getDouble("soluong") > 0)
							sp_dkkm.setSoluong(spDetail.getString("soluong"));
						
						sp_dkkmList.add(sp_dkkm);
					}
					spDetail.close();
					
					dkkmDetail.setSpList(sp_dkkmList);
					
					
					dkkm.setDieukineDetail(dkkmDetail);
					listDkkm.add(dkkm);
					
				}
				if(rs!=null){
					rs.close();
				}
			} 
			catch (Exception e) 
			{
				System.out.println("____Exception: " + e.getMessage());
			}
		}
		
		this.dkkmList = listDkkm;
	}
	
	private void createTrakmList()
	{
		String query = "select a.pk_seq as trakmId, a.diengiai, cast(isnull(a.tongluong, 0) as float) as tongluong, " +
							"cast(isnull(a.tongtien, 0) as float) as tongtien, a.hinhthuc, " +
							"cast(isnull(a.chietkhau, 0) as float) as chietkhau, isnull(b.pheptoan, '1') as pheptoan, a.loai, b.thutu " +
						"from trakhuyenmai a inner join ctkm_trakm b on a.pk_seq = b.trakhuyenmai_fk " +
						"where b.ctkhuyenmai_fk = '" + this.id + "' order by b.thutu asc";
		
		System.out.println("Khoi tao tra khuyen mai: " + query);
		ResultSet rs = db.get(query);
		List<ITrakm> listTrakm = new ArrayList<ITrakm>();
		if(rs != null)
		{ 
			try 
			{
				ITrakm trakm = null;
				while(rs.next())
				{
					String trakmId = rs.getString("trakmId");
					String diengiai = rs.getString("diengiai");
					String tongluong = rs.getString("tongluong");
					String tongtien = rs.getString("tongtien");
					String chietkhau = rs.getString("chietkhau");
					String pheptoan = rs.getString("pheptoan");
					String thutudk = rs.getString("thutu");
					trakm = new Trakm(trakmId, diengiai, tongluong, tongtien, chietkhau, pheptoan, thutudk);
					
					
					ITrakmDetail tkmDetail;
					tkmDetail = new TrakmDetail();
					tkmDetail.setDiengiai(diengiai);
					tkmDetail.setLoaitra(rs.getString("loai"));
					tkmDetail.setHinhthuc(rs.getString("hinhthuc"));
					
					if(Double.parseDouble(tongluong) > 0)
					{
						tkmDetail.setSotong(tongluong);
					}
					else
					{
						tkmDetail.setSotong(tongtien);
					}
					
					//Khoi tao SP
					query = "select b.PK_SEQ as spId, b.MA as spMa, b.TEN as spTen, ISNULL(a.soluong, 0) as soluong  " +
							"from TRAKHUYENMAI_SANPHAM a inner join SANPHAM b on a.SANPHAM_FK = b.PK_SEQ where a.TRAKHUYENMAI_FK = '" + trakmId + "'";
					ResultSet spDetail = db.get(query);
					
					List<ISanpham> sp_dkkmList = new ArrayList<ISanpham>();
					while(spDetail.next())
					{
						ISanpham sp_dkkm = new Sanpham();
						sp_dkkm.setId(spDetail.getString("spId"));
						sp_dkkm.setMasanpham(spDetail.getString("spMa"));
						sp_dkkm.setTensanpham(spDetail.getString("spTen"));
						if(spDetail.getDouble("soluong") > 0)
							sp_dkkm.setSoluong(spDetail.getString("soluong"));
						
						sp_dkkmList.add(sp_dkkm);
					}
					spDetail.close();
					
					tkmDetail.setSpList(sp_dkkmList);
					
					trakm.setTraDetail(tkmDetail);
					listTrakm.add(trakm);
					
				}
				if(rs!=null){
					rs.close();
				}
			} 
			catch (Exception e) 
			{
				System.out.println("____Exception: " + e.getMessage());
			}
		}
		this.trakmList = listTrakm;
	}
	
	public void init() 
	{
		String query = "select a.nhom_kh_loai_tru,a.PK_SEQ as ctkmId, a.SCHEME,a.SCHEMEERP, isnull(a.DIENGIAI, '') as diengiai, a.TUNGAY, a.DENNGAY, a.ngansachkehoach, " +
						"case when a.LOAICT is null then '1' else a.LOAICT end as type, a.NGANSACH, isnull(a.loaingansach, '') as loaingansach, " +
						"a.DASUDUNG, a.NGAYTAO, a.NGAYSUA, a.tilevoiprimary, b.TEN as nguoitao, c.TEN as nguoisua, a.kho_fk,a.nhomkhnpp_fk, " +
						"isnull(d.NGAYDS, '') as NGAYDS, isnull(NGAYKTDS, '') as NGAYKTDS, " +
						"isnull(d.phantramtoida, '100') as phantramtoida, isnull( ( select count(pk_seq) from donhang_ctkm_trakm where ctkmId = a.pk_seq ) , 0) as donhangId, NPPTUCHAY " +
						 " ,a.PHANBOTHEODONHANG ,a.ApDUNGCHODHLE, a.LEVEL_PHANBO,a.IO,a.TuNgay_DatHang,a.DenNgay_DatHang, a.kbh_fk, a.apdungcho  "+
				"from CTKHUYENMAI a inner join NHANVIEN b on a.NGUOITAO = b.PK_SEQ inner join NHANVIEN c on a.NGUOISUA = c.PK_SEQ " +
				"left join ngaytinhdskm d on a.pk_seq = d.ctkm_fk  " +
				"where a.pk_seq = '" + this.id + "' order by a.NGAYTAO DESC, a.pk_seq DESC ";
		
		System.out.println("ctkhuyen mai:"+query);
		ResultSet rs = db.get(query);
		try
        {
            while(rs.next())
            {    	
            	this.id = rs.getString("ctkmId");
            	this.scheme = rs.getString("scheme");
            	this.diengiai = rs.getString("diengiai");
            	this.tungay = rs.getString("tungay");
            	this.denngay = rs.getString("denngay");
            	this.type = rs.getString("type");
            	this.ngansach = rs.getString("ngansach");
            	this.loains = rs.getString("loaingansach");
            	this.dasudung = rs.getString("dasudung");
            	this.ngaytao = rs.getString("ngaytao");
            	this.nguoitao = rs.getString("nguoitao");
            	this.ngaysua = rs.getString("ngaysua");
            	this.schemeErp = rs.getString("SCHEMEERP")==null?"":rs.getString("SCHEMEERP");
            	this.nguoisua = rs.getString("nguoisua");
            	this.khoId = rs.getString("kho_fk");
            	this.kbhId = rs.getString("kbh_fk")==null?"":rs.getString("kbh_fk");
            	this.NhomkhnppId = rs.getString("nhomkhnpp_fk");
            	this.nhom_kh_loai_tru = rs.getString("nhom_kh_loai_tru")==null?"":rs.getString("nhom_kh_loai_tru");
            	this.tylevoiPrimary = rs.getString("tilevoiprimary");
            	this.ngansachkehoach = rs.getString("ngansachkehoach")==null?"":rs.getString("ngansachkehoach");
            	this.ngayds = rs.getString("NGAYDS");
        		this.ngayktds = rs.getString("NGAYKTDS");
        		this.phanTramToida = rs.getString("phantramtoida");
        		this.nppTuchay = rs.getString("NPPTUCHAY");
        		this.mucphanbo = rs.getString("LEVEL_PHANBO");
        		
        		if(rs.getInt("donhangId") >= 1)
        			this.dacotrongdh = true;
        		
        		this.apDungDHLe = rs.getString("ApDUNGCHODHLE");
            	this.phanbotheoDH = rs.getString("PHANBOTHEODONHANG")==null?"0":rs.getString("PHANBOTHEODONHANG");
            	this.TuNgay_DatHang = rs.getString("TuNgay_DatHang")==null?"":rs.getString("TuNgay_DatHang");
            	this.DenNgay_DatHang = rs.getString("DenNgay_DatHang")==null?"":rs.getString("DenNgay_DatHang");
            	
            	this.io = rs.getString("IO")==null?"":rs.getString("IO");
            	this.apdungcho = rs.getString("apdungcho");
        		
            }
            rs.close();
       	}
        catch (Exception e)
		{
        	e.printStackTrace();
		}
        
		query = "select top(1) kbh_fk from ctkm_npp a inner join nhapp_kbh b on a.npp_fk = b.npp_fk where a.ctkm_fk = "+ this.id;
		System.out.println("init kbh:"+query);
		rs = db.get(query);
		try
        {
			rs.next();
			this.kbhId = rs.getString("kbh_fk");
        }
        catch (Exception e)
		{
        	e.printStackTrace();
        }
        this.createRS();
        this.createDkkmList();
        this.createTrakmList();
        //CreateNhomkhnpp();
       ResultSet checkDh = db.get("select LOAIKH_Fk from CTKHUYENMAI_LOAIKH where ctkm_fk ='" + this.id + "'");
        if(checkDh != null)
        {
        	try 
        	{
        		String LOAIKH_Fk = "";
				while(checkDh.next())
				{
					LOAIKH_Fk += checkDh.getString("LOAIKH_Fk") + ",";
				}
				checkDh.close();
				System.out.println("LOAIKH_Fk "+"select LOAIKH_Fk from CTKHUYENMAI_LOAIKH where ctkm_fk ='" + this.id + "'");
				if(LOAIKH_Fk.trim().length() > 0)
				{
					LOAIKH_Fk = LOAIKH_Fk.substring(0, LOAIKH_Fk.length() - 1);
					this.loaiKhIds = LOAIKH_Fk;
				}
				checkDh.close();
			} 
        	catch (Exception e) 
        	{
        		System.out.println("Exception Loai Khach Hang: " + e.getMessage());
        	}
        }
        
         checkDh = db.get("select kbh_fk from CTKHUYENMAI_KBH where ctkm_fk ='" + this.id + "'");
        if(checkDh != null)
        {
        	try 
        	{
        		String kbhIds = "";
				while(checkDh.next())
				{
					kbhIds += checkDh.getString("kbh_fk") + ",";
				}
				checkDh.close();
				
				if(kbhIds.trim().length() > 0)
				{
					kbhIds = kbhIds.substring(0, kbhIds.length() - 1);
					this.kbhIds = kbhIds;
				}
				checkDh.close();
			} 
        	catch (Exception e) 
        	{
        		System.out.println("Exception Kenh Ban Hang: " + e.getMessage());
        	}
        }
	}

	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	public void setkhoId(String khoId) 
	{
	   this.khoId = khoId;
	}
	
	public String getkhoId()
	{
		return this.khoId;
	}

	public ResultSet getkhoIds() 
	{
		ResultSet rs = db.get("select * from kho where trangthai='1'");
		return rs;
	}

	public void setDsnpp(ResultSet Dsnpp) 
	{
		this.Dsnpp = Dsnpp;
	}

	public ResultSet getDsnpp()
	{
		return this.Dsnpp;
	}
	
    public void CreateDsnpp()
    {
    	Utility Ult = new Utility();
    	String query = "";
    	System.out.println("kbh " + kbhIds);
    	if(this.kbhIds.indexOf("100021") >= 0)
   		{
    		query = "select manpp as nppMa,pk_seq, ten from nhaphanphoi where trangthai = '1' and sitecode=convsitecode and priandsecond=0 ";
   		}
   		else
   		{
   			query = "select manpp as nppMa,pk_seq, ten from nhaphanphoi where trangthai = '1' and sitecode=convsitecode and priandsecond=0  and pk_seq in " + Ult.quyen_npp(this.userId);
   		}
	   	//loc theo khu vuc
	   	if(khuvucId.length() > 0)
	   	{
	   		if(this.kbhIds.indexOf("100021") >= 0)
	   		{
	   			query = "select  manpp as nppMa,pk_seq, ten from nhaphanphoi where trangthai = '1' and sitecode=convsitecode  and priandsecond=0 and khuvuc_fk in (" + khuvucId + ")";
	   		}
	   		else
	   		{
	   			query = "select  manpp as nppMa,pk_seq, ten from nhaphanphoi where trangthai = '1' and sitecode=convsitecode  and priandsecond=0 and khuvuc_fk in (" + khuvucId + ") and pk_seq in "+ Ult.quyen_npp(this.userId);
	   		}
	   	}
	   	
	   	//khong chon khu vuc, loc theo vung
	   	if(vungId.length() > 0)
	   	{
	   		if(this.kbhIds.indexOf("100021") >= 0)
	   		{
	   			query = "select  manpp as nppMa,pk_seq, ten from nhaphanphoi where trangthai = '1' and sitecode=convsitecode  and priandsecond=0 and khuvuc_fk in (select pk_seq from khuvuc where vung_fk in (" + vungId + ") )";
	   		}
	   		else
	   		{
	   			query = "select  manpp as nppMa,pk_seq, ten from nhaphanphoi where trangthai = '1' and sitecode=convsitecode  and priandsecond=0 and khuvuc_fk in (select pk_seq from khuvuc where vung_fk in (" + vungId + ") )and pk_seq in "+ Ult.quyen_npp(this.userId);
	   		}
	   	}
	    
		/*if(khuvucId.length() > 0 && vungId.length() > 0)
	   	{
	   		if(this.kbhIds.indexOf("100021") >= 0)
	   		{
	   			query = "select  ma as nppMa,pk_seq, ten from nhaphanphoi where trangthai = '1' and sitecode=convsitecode  and priandsecond=0 and khuvuc_fk in (" + khuvucId + ")";
	   		}
	   		else
	   		{
	   			query = "select  ma as nppMa,pk_seq, ten from nhaphanphoi where trangthai = '1' and sitecode=convsitecode  and priandsecond=0 and khuvuc_fk in (" + khuvucId + ") and pk_seq in "+ Ult.quyen_npp(this.userId);
	   		}
	   	}*/
	   	
	   	if(kbhIds.length() > 0)
	   		query = query + " and pk_seq in (select npp_fk from nhapp_kbh where kbh_fk in (" + this.kbhIds + "))";
	   	
	   	if(this.id.length() > 0)
	   	{
	   		query = query + " and pk_seq not in (select NPP_FK from CTKM_NPP  where chon='1' and  CTKM_FK = '" + this.id + "')";
	   		this.DsnppIds = db.get("select manpp as nppMa, pk_seq, ten from nhaphanphoi where pk_seq in (select NPP_FK from CTKM_NPP where  chon='1' and CTKM_FK = '" + this.id + "')");
	   	}
	   	System.out.println("dsnpp " + query);
	   	this.Dsnpp = db.get(query);
	   
    	if(this.id.length() > 0)
    	{
    		//tao nppIds
    		query = "select npp_fk as nppId from ctkm_npp where chon='1' and ctkm_fk ='"+ this.id +"'";    		
    		ResultSet rs = db.get(query);
    		String nppIds = "";
    		if(rs != null)
    		{
    			try 
    			{
					while(rs.next())
					{
						if(rs.getString("nppId") != null)
							nppIds += rs.getString("nppId") + ",";
					}
					rs.close();
				} 
    			catch (SQLException e) {}
    			   			
    			if(nppIds.length() > 0)
    			{
    				nppIds = nppIds.substring(0, nppIds.length() - 1);
    				this.npp = nppIds.split(",");
    			}
    		}
    	}
    }

	public void setNpp(String[] npp) 
	{	
		this.npp = npp;
	}

	public Hashtable<Integer, String> getnpp()
	{
		Hashtable<Integer, String> selected = new Hashtable<Integer, String>();
		if(this.npp != null){
			int size = (this.npp).length;
			int m = 0;
			while(m < size){
				selected.put(new Integer(m), this.npp[m]) ;
				m++;
			}
		}else{
			selected.put(new Integer(0), "null");
		}
		return selected;	
	}

   public ResultSet getNhomkhnpp() 
   {	
		return this.Nhomkhnpp;
   }
   
   private void CreateNhomkhnpp()
   { 
	   this.Nhomkhnpp = db.getScrol("select * from nhomkhachhangnpp");
   }
	
	public void setNhomkhnppId(String NhomkhnppId) 
	{	
		this.NhomkhnppId = NhomkhnppId;
	}

	
	public String getNhomkhnppId() {
		
		return this.NhomkhnppId;
	}

	
	public void setVungId(String vungId) {
		
		this.vungId = vungId;
	}

	
	public String getVungId() {
		
		return this.vungId;
	}

	
	public void setKhuvucId(String khuvucId) {
		
		this.khuvucId = khuvucId;
	}

	
	public String getKhuvucId() {
		
		return this.khuvucId;
	}

	
	public void setVungs(ResultSet vungs) {
		
		this.vungs = vungs;
	}

	
	public ResultSet getVungs() {
		
		return this.vungs;
	}

	
	public void setKhuvuc(ResultSet khuvucs) {
		
		this.khuvucs = khuvucs;
	}

	
	public ResultSet getKhuvuc() {
		
		return this.khuvucs;
	}
   
	public void CreateVung()
	{
		db = new dbutils();
		String sql = "select * from vung";
		
		this.vungs = db.get(sql);
		if(this.vungId.length() > 0)
		{
			sql = "select * from khuvuc where vung_fk in (" + this.vungId + ")";
			this.khuvucs = db.get(sql); 
		}
		else
		{
			sql = "select * from khuvuc";
			this.khuvucs = db.get(sql);
		}
		CreateDsnpp();
	}

	
	public void setload(String load) {
		
		this.load = load;
	}

	public String getload() {
		
		return this.load;
	}

	
	public void setngayds(String ngayds) {
		
	      this.ngayds = ngayds;	
	}

	
	public String getngayds() {
		
		return this.ngayds;
	}

	
	public void setngayktds(String ngayktds) {
		
		this.ngayktds = ngayktds;
	}

	
	public String getngayktds() {
		
		return this.ngayktds;
	}

	public void setKbhRs(ResultSet kbh)
	{
		this.kenhRs = kbh;
	}

	public ResultSet getKbhRs()
	{
		return this.kenhRs;
	}

	public void setKbhIds(String kbhIds) 
	{
		this.kbhIds = kbhIds;
	}

	public String getKbhIds() 
	{
		return this.kbhIds;
	}
	
	public void setKbhId(String kbhId) 
	{
		this.kbhId = kbhId;
	}

	public String getKbhId() 
	{
		return this.kbhId;
	}

	public void setLoaiNganSach(String loains)
	{
		this.loains = loains;
	}

	public String getLoaiNganSach() 
	{
		return this.loains;
	}

	public void setActive(String active) 
	{
		this.active = active;
	}

	public String getActive()
	{
		return this.active;
	}

	public ResultSet getDsnppSelected() 
	{
		return this.DsnppIds;
	}

	
	public void setDsnppSelected(ResultSet DsnppSelected) 
	{
		this.DsnppIds = DsnppSelected;
	}

	public void setDacotrongdh(boolean dacotrongdh) 
	{
		this.dacotrongdh = dacotrongdh;
	}

	public boolean getDacotrongdh() 
	{
		return this.dacotrongdh;
	}

	
	public void DbClose() 
	{
		try
		{
			if(Dsnpp!=null){
				Dsnpp.close();
			}
			if(DsnppIds!=null){
				DsnppIds.close();
			}
			if(trakmRs!=null){
				trakmRs.close();
			}
			if(khoIds!=null){
				khoIds.close();
			}
			if(Nhomkhnpp!=null){
				Nhomkhnpp.close();
			}
			if(vungs!=null){
				vungs.close();
			}
			if(khuvucs!=null){
				khuvucs.close();
			}
			if(kenhRs!=null){
				kenhRs.close();
			}
			if(this.nhomspRs != null)
				nhomspRs.close();
			
			 if(db!=null){
				 db.shutDown();
			 }
		}
		catch(Exception er){}
	}

	
	public String getLoaikhuyenmai() 
	{
		return this.loaikhuyenmai;
	}

	public void setLoaikhuyenmai(String loaikm) 
	{
		this.loaikhuyenmai = loaikm;
	}

	public String getTylevoiPrimary()
	{
		return this.tylevoiPrimary;
	}

	public void setTylevoiPrimary(String tyle)
	{
		this.tylevoiPrimary = tyle;
	}

	public List<ITrakm> getTrakmList() 
	{
		return this.trakmList;
	}

	public void setTrakmList(List<ITrakm> trakmList) 
	{
		this.trakmList = trakmList;
	}

	public void setNhomspRs(ResultSet nspRs) 
	{
		this.nhomspRs = nspRs;
	}

	public ResultSet getNhomspRs()
	{
		return this.nhomspRs;
	}

	public String getPTToida() 
	{
		return this.phanTramToida;
	}

	public void setPTToida(String ptToida) 
	{
		this.phanTramToida = ptToida;
	}

	public String getNppTuchay() 
	{
		return this.nppTuchay;
	}

	public void setNppTuchay(String nppTuchay) 
	{
		this.nppTuchay = nppTuchay;
	}

	
	public void setLoaikhIds(String lkhIds) {
		
		this.loaiKhIds = lkhIds;
	}

	
	public String getLoaikhIds() {
		
		return this.loaiKhIds;
	}

	
	public void setLoaikhRs(ResultSet loaiKh) {
		
		this.loaiKhRs = loaiKh;
	}

	
	public ResultSet getLoaikhRs() {
		
		return this.loaiKhRs;
	}
	
	String apDungDHLe="";
	public void setApdungchoDHLe(String flag) 
	{
		this.apDungDHLe = flag;
	}

	public String getApdungchoDHLe() 
	{
		return this.apDungDHLe;
	}

	
	String phanbotheoDH="";
	public void setPPhanbotheoDH(String pbtheoDH) {
		
		this.phanbotheoDH = pbtheoDH;
	}


	public String getPhanbotheoDH() {
		
		return this.phanbotheoDH;
	}
	
	public void setMucphanbo(String mucpb) {
		
		this.mucphanbo = mucpb;
	}
	
	public String getMucphanbo() {
		
		return this.mucphanbo;
	}
	

	String io="";

	public String getIo() {
		return io;
	}

	public void setIo(String io) {
		this.io = io;
	}

	String TuNgay_DatHang="",DenNgay_DatHang="";

	public String getTuNgay_DatHang()
	{
		return TuNgay_DatHang;
	}

	public void setTuNgay_DatHang(String tuNgay_DatHang)
	{
		TuNgay_DatHang = tuNgay_DatHang;
	}

	public String getDenNgay_DatHang()
	{
		return DenNgay_DatHang;
	}

	public void setDenNgay_DatHang(String denNgay_DatHang)
	{
		DenNgay_DatHang = denNgay_DatHang;
	}
	
	public String getNgansachkehoach()
	{
		return this.ngansachkehoach;
	}

	public void setNgansachkehoach(String ngansachkehoach) 
	{
		this.ngansachkehoach = ngansachkehoach;
	}


	public String getApdungcho() {

		return this.apdungcho;
	}


	public void setApdungcho(String apdungcho) {
		
		this.apdungcho = apdungcho;
	}

	
	public String getSchemeErp() {
		
		return this.schemeErp;
	}

	
	public void setSchemeErp(String SchemeErp) {
		this.schemeErp = SchemeErp;
		
	}
	String nhom_kh_loai_tru="";
	public String getNhom_kh_loai_tru() {
		return nhom_kh_loai_tru;
	}
	public void setNhom_kh_loai_tru(String nhom_kh_loai_tru) {
		this.nhom_kh_loai_tru = nhom_kh_loai_tru;
	}
	
}
