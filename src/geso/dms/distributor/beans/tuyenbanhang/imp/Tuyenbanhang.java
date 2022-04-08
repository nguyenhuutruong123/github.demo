package geso.dms.distributor.beans.tuyenbanhang.imp;

import geso.dms.distributor.beans.tuyenbanhang.ITuyenbanhang;
import geso.dms.distributor.db.sql.*;

import java.io.Serializable;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

public class Tuyenbanhang implements ITuyenbanhang, Serializable
{
	private static final long serialVersionUID = -9217977546733610214L;

	String userId;
	String id;
	String diengiai;
	String ddkd;
	String ngaylamviec;
	String ngaytao;
	String nguoitao;
	String ngaysua;
	String nguoisua;
	String msg;
	
	String nppId;
	String nppTen;
	String sitecode;
	
	ResultSet daidienkd;
	String ddkdId;
	
	String ddkdnewId; //dai dien kinh doanh moi (Move tuyen ban hang)
	String diengiainew; //dien giai cua dai dien kinh doanh moi (Move tuyen ban hang)
	String nlvnew; //ngay lam viec New
	
	String tenkhachhang;
	String diachi;
	
	ResultSet kh_tbh_dpt;
	ResultSet kh_tbh_cdpt;
	String[] kh_tbh_dptIds;
	String[] kh_tbh_cdptIds;
	String[] sott;
	String[] tansoList;
	
	//move tuyen ban hang
	ResultSet nlvList;
	ResultSet diengiaiList;
	ResultSet tanso;
	String view = "";
	dbutils db;
	
	public Tuyenbanhang(String[] param)
	{
		this.id = param[0];
		this.diengiai = param[1];
		this.ddkd = param[2];
		this.ngaylamviec = param[3];
		this.ngaytao = param[4];
		this.nguoitao = param[5];
		this.ngaysua = param[6];
		this.nguoisua = param[7];
		this.ddkdId = param[8];
		
		this.ddkdnewId = "";
		this.diengiainew = "";
		this.nlvnew = "";
		this.tenkhachhang = "";
		this.diachi = "";
		this.msg = "";
		db = new dbutils();
	}
	
	public Tuyenbanhang(String id)
	{
		this.id = id;
		this.diengiai = "";
		this.ddkd = "";
		this.ngaylamviec = "";
		this.ngaytao = "";
		this.nguoitao = "";
		this.ngaysua = "";
		this.nguoisua = "";
		this.ddkdId = "";
		
		this.ddkdnewId = "";
		this.diengiainew = "";
		this.nlvnew = "";
		this.tenkhachhang = "";
		this.diachi = "";
		this.msg = "";
		db = new dbutils();
		//if(id.length() > 0)
			//this.init();
		//else
			//this.createRS();
	}
	
	public String getView() {
		return view;
	}
	public void setView(String view) {
		this.view = view;
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
	
	public String getDdkd() 
	{
		return this.ddkd;
	}

	public void setDdkd(String ddkd) 
	{
		this.ddkd = ddkd;
	}
	
	public String getNgaylamviec() 
	{
		return this.ngaylamviec;
	}

	public void setNgaylamviec(String ngaylamviec) 
	{
		this.ngaylamviec = ngaylamviec;
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

	public String getTenkhachhang() 
	{
		return this.tenkhachhang;
	}

	public void setTenkhachhang(String tenkhachhang) 
	{
		this.tenkhachhang = tenkhachhang;
	}

	public String getDiachi() 
	{
		return this.diachi;
	}

	public void setDiachi(String diachi) 
	{
		this.diachi = diachi;
	}

	public ResultSet getDaidienkd() 
	{
		return this.daidienkd;
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
	
	public void setDaidienkd(ResultSet daidienkd) 
	{
		this.daidienkd = daidienkd;
	}
	
	public String getDdkdId() 
	{
		return this.ddkdId;
	}

	public void setDdkdId(String ddkdId)
	{
		this.ddkdId = ddkdId;
	}
	
	public ResultSet getKh_Tbh_DptList() 
	{
		return this.kh_tbh_dpt;
	}

	public void setKh_Tbh_DptList(ResultSet kh_tbh_dpt) 
	{
		this.kh_tbh_dpt = kh_tbh_dpt;
	}
	
	public ResultSet getKh_Tbh_CdptList() 
	{
		return this.kh_tbh_cdpt;
	}
	
	public void setKh_Tbh_CdptList(ResultSet kh_tbh_cdpt)
	{
		this.kh_tbh_cdpt = kh_tbh_cdpt;
	}
	
	public Hashtable<Integer, String> getKh_Tbh_DptIds() 
	{
		Hashtable<Integer, String> selected = new Hashtable<Integer, String>();
		if(this.kh_tbh_dptIds != null){
			int size = (this.kh_tbh_dptIds).length;
			int m = 0;
			while(m < size){
				selected.put(new Integer(m), this.kh_tbh_dptIds[m]) ;
				m++;
			}
		}else{
			selected.put(new Integer(0), "null");
		}
		return selected;
	}
	
	public void setKh_Tbh_DptIds(String[] kh_tbh_dptIds) 
	{
		this.kh_tbh_dptIds = kh_tbh_dptIds;
	}
	
	public Hashtable<Integer, String> getKh_Tbh_CdptIds() 
	{
		Hashtable<Integer, String> selected = new Hashtable<Integer, String>();
		if(this.kh_tbh_cdptIds != null){
			int size = (this.kh_tbh_cdptIds).length;
			int m = 0;
			while(m < size){
				selected.put(new Integer(m), this.kh_tbh_cdptIds[m]) ;
				m++;
			}
		}else{
			selected.put(new Integer(0), "null");
		}
		return selected;
	}
	
	public void setTansoList(String[] tansoList)
	{
		this.tansoList = tansoList;
	}
	
	public String[] getTansoList()
	{
		return this.tansoList;
	}
	
	public void setKh_Tbh_CdptIds(String[] kh_tbh_cdptIds) 
	{
		this.kh_tbh_cdptIds = kh_tbh_cdptIds;
	}
	
	public String getDdkdNewId() 
	{
		return this.ddkdnewId;
	}
	
	public void setDdkdNewId(String ddkdnewId) 
	{
		this.ddkdnewId = ddkdnewId;
	}

	public String getDiengiaiNew()
	{
		return this.diengiainew;
	}
	
	public void setDiengiaiNew(String diengiainew)
	{
		this.diengiainew = diengiainew;
	}
	
	public String getNlvNew() 
	{
		return this.nlvnew;
	}

	public void setNlvNew(String nlvnew) 
	{
		this.nlvnew = nlvnew;
	}
	
	private void getNppInfo()
	{		
		
		//Phien ban moi
		geso.dms.distributor.util.Utility util=new geso.dms.distributor.util.Utility();
		this.nppId=util.getIdNhapp(this.userId);
		this.nppTen=util.getTenNhaPP();
		//this.dangnhap = util.getDangNhap();
		this.sitecode=util.getSitecode();
	}
	
	public boolean CreateTbh() 
	{ 
		List<Object> data = new ArrayList<Object>();
		//System.out.println("CreateTbh");
		try
		{
			db.getConnection().setAutoCommit(false);

			this.ngaytao = getDateTime();
			this.nguoisua = this.userId;
			String ngayid = get_datenumber(this.ngaylamviec);

			String sql = "\n select count(*) as num from tuyenbanhang a inner join nhaphanphoi b on a.NPP_FK = b.PK_SEQ " +
					"\n where ngayid = ? and ddkd_fk = ? and b.TRANGTHAI = 1";
			data.clear();
			data.add(ngayid); data.add(this.ddkdId);
			ResultSet rs = db.getByPreparedStatement(sql, data);

			if(rs.next())
			{
				if(rs.getInt("num") > 0)
				{
					rs.close();
					this.msg = "Đại diện kinh doanh này đã được phân tuyến trong ngày làm việc.";
					db.getConnection().rollback();
					return false;
				}

			}
			if(rs!=null){
				rs.close();
			}
			String query ="insert into tuyenbanhang(diengiai, ngaylamviec, ngaytao, ngaysua, nguoitao, nguoisua, ddkd_fk, npp_fk,ngayid)";
			query = query + " values(?,?,?,?,?,?,?,?,?)";
			data.clear();
			data.add(this.diengiai); data.add(this.ngaylamviec); data.add(this.ngaytao); data.add(this.ngaytao);
			data.add(this.nguoisua); data.add(this.nguoisua); data.add(this.ddkdId); data.add(this.nppId); data.add(ngayid);
			this.db.viewQuery(query, data);
			if (this.db.updateQueryByPreparedStatement(query, data)<=0)
			{
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				this.msg = "Khong the luu vao table 'Tuyen Ban Hang'. Vui lòng liên hệ quản trị viên ";
				return false;			
			} 

			/*query = "select IDENT_CURRENT('tuyenbanhang') as tbhId";
			ResultSet rsTbh = this.db.get(query);
			rsTbh.next();*/
			this.id = this.db.getPk_seq(); 

			//System.out.println("So kahch hang: " + Integer.toString(this.kh_tbh_dptIds.length) + "\n");
			if(this.kh_tbh_dptIds != null)
			{
				boolean flag = CheckKhachHang(this.kh_tbh_dptIds);
				if(flag == true)
				{
					db.getConnection().rollback();
					this.msg = "Khong the cho khach hang trung nhau vao tuyen, vui long chon lai";
					return false;	
				}


				int size = this.kh_tbh_dptIds.length;
				int m = 0;

				while (m < size )
				{
					String khIds = this.kh_tbh_dptIds[m].substring(0, this.kh_tbh_dptIds[m].indexOf(";"));
					System.out.println("Kh ID : "+khIds);
					String tsIds = this.tansoList[m];

					if(tsIds == null)
						tsIds = "";
					int thutu=0;

					try{
						thutu=Integer.parseInt(this.sott[m]);

					}catch(Exception er){

					}
					query = "insert into khachhang_tuyenbh values(?,?,?,?)";					
					data.clear();
					data.add(khIds); data.add(this.id); data.add(tsIds); data.add(thutu);
					this.db.viewQuery(query, data);
					if(this.db.updateQueryByPreparedStatement(query, data)<=0)
					{
						geso.dms.center.util.Utility.rollback_throw_exception(db);
						this.msg = "Khong the luu vao table 'KhachHang_TuyenBH' ";
						return false;
					}
					
					query =" update khachhang set DDKDTao_fk = "+this.ddkdId+" where pk_seq = "+khIds+" and DDKDTao_fk is null ";
					if(! this.db.update(query))
					{
						geso.dms.center.util.Utility.rollback_throw_exception(db);
						this.msg = "Khong the luu vao table 'khachhang'";
						return false;
					}
					
					m++;	
				}
			}

			
			query = "\n update kh set kh.route_fk = ddkd.route_fk " +
			"\n from khachhang_tuyenbh a " +
			"\n inner join khachhang kh on kh.pk_seq = a.khachhang_fk " +
			"\n inner join tuyenbanhang b on a.tbh_fk = b.pk_seq " +
			"\n inner join daidienkinhdoanh ddkd on ddkd.pk_seq = b.ddkd_fk " +
			"\n where ddkd.pk_seq = "+this.id;
			data.clear();
			data.add(this.id);
			this.db.viewQuery(query, data);
			db.updateQueryByPreparedStatement(query, data);

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);	
		}
		catch(Exception e){
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			this.msg = "Loi khi cap nhat bang "+e.toString();
			return false;
		}
		return true;
	}
	
	private boolean CheckKhachHang(String[] khIds) 
	{
		List<String> kq = new ArrayList<String>();
		for(int  i = 0; i < khIds.length; i++)
		{
			if(!kq.contains(khIds[i].trim()))
			{
				kq.add(khIds[i]);
			}
		}
		if (kq.size() < khIds.length)
			return true;
		return false;
	}

	private String get_datenumber(String ngaylamviec2) {
		
		if(ngaylamviec2.equals("Thu hai")){
			return "2";
		}else if(ngaylamviec2.equals("Thu ba")){
			return "3";
		}else if(ngaylamviec2.equals("Thu tu")){
			return "4";
		}else if(ngaylamviec2.equals("Thu nam")){
			return "5";
		}else if(ngaylamviec2.equals("Thu sau")){
			return "6";
		}else if(ngaylamviec2.equals("Thu bay")){
			return "7";
		}
		return "";
	}
	
	
	public boolean UpdateTbh() 
	{
		String ngayid=get_datenumber(this.ngaylamviec);
		if(ngayid.equals("")){
			this.msg="Không thể lấy ngày làm việc.";
			return false;
		}
		if(!kiemtra_duynhat(ngayid))
		{
			List<Object> data = new ArrayList<Object>();
			try
			{
				db.getConnection().setAutoCommit(false);
				this.ngaysua = getDateTime();
				this.nguoisua = this.userId;

				// Update table "Tuyen Ban Hang"
				String query = "update tuyenbanhang set diengiai= ?, ngaylamviec= ?, ddkd_fk= ?, nguoisua= ?, ngayid=?  where pk_seq = ?" ;
				data.clear();
				data.add(this.diengiai); data.add(this.ngaylamviec); data.add(this.ddkdId); data.add(this.nguoisua); 
				data.add(ngayid); data.add(this.id);
				this.db.viewQuery(query, data);
				if(this.db.updateQueryByPreparedStatement(query, data)<=0){
					geso.dms.center.util.Utility.rollback_throw_exception(db);
					this.msg = "Khong the cap nhat table 'Tuyen Ban Hang'";
					return false; 
				}
				
				query = "delete from khachhang_tuyenbh where tbh_fk= ?" ;
				data.clear();
				data.add(this.id);
				this.db.viewQuery(query, data);
				this.db.updateQueryByPreparedStatement(query, data);
				
				/*if(this.db.updateQueryByPreparedStatement(query, data)<=0){
					geso.dms.center.util.Utility.rollback_throw_exception(db);
					this.msg = "Loi khi cap nhat bang ";
					return false; 
				}*/
				
				// Update table "khachhang_tuyenbh"
				if(this.kh_tbh_dptIds != null)
				{
					int size = this.kh_tbh_dptIds.length;
					int m = 0;

					while (m < size )
					{
						String khIds = this.kh_tbh_dptIds[m].substring(0, this.kh_tbh_dptIds[m].indexOf(";"));
						System.out.println("Khachhang Id : "+khIds );
						String tsIds = this.tansoList[m];
						int thutu=0;

						try{
							thutu=Integer.parseInt(this.sott[m]);

						}catch(Exception er){

						}
						if(tsIds == null)
							tsIds = "";
						query = "insert into khachhang_tuyenbh(KHACHHANG_FK,TBH_FK,TANSO,SOTT) values("+khIds+","+this.id+",'"+tsIds+"','"+thutu+"')";
						
						if(! this.db.update(query))
						{
							geso.dms.center.util.Utility.rollback_throw_exception(db);
							this.msg = "Khong the luu vao table 'KhachHang_TuyenBH'";
							return false;
						}
						
						query =" update khachhang set DDKDTao_fk = "+this.ddkdId+" where pk_seq = "+khIds+" and DDKDTao_fk is null ";
						if(! this.db.update(query))
						{
							geso.dms.center.util.Utility.rollback_throw_exception(db);
							this.msg = "Khong the luu vao table 'khachhang'";
							return false;
						}

						m++;	
					}
				}
				/*query = 
					" select  * from"
					+"\n  ("
					+"\n  select kh.PK_SEQ,  kh.SMARTID,kh.TEN, a.TANSO from khachhang_tuyenbh a inner join TUYENBANHANG tbh on"
					+"\n  a.TBH_FK = tbh.PK_SEQ "
					+"\n  inner join khachhang kh on kh.PK_SEQ = a.KHACHHANG_FK"
					+ "\n  where tbh.ddkd_fk = ? and tbh.npp_fk =? and tbh.pk_seq = ? "
					+"\n  ) kh inner join "
					+"\n  ("
					+"\n  select khachhang_fk,count(TBH_FK) as sotuyen from khachhang_tuyenbh a inner join TUYENBANHANG tbh on"
					+"\n  a.TBH_FK = tbh.PK_SEQ "
					+ "\n  where tbh.ddkd_fk = ? and tbh.npp_fk = ? and tbh.pk_seq <> ?"
					+"\n  group by khachhang_fk"
					+"\n  ) a on a.KHACHHANG_FK = kh.PK_SEQ"
					+"\n  inner join"
					+"\n  ("
					+"\n  select 'F2-1' as tanso,1 soluong union"
					+"\n  select 'F2-2' as tanso,1 soluong union"
					+"\n  select 'F24' as tanso,6 soluong union"
					+"\n  select 'F4' as tanso, 1 as soluong union "
					+"\n  select 'F8' as tanso, 2 as soluong union "
					+"\n  select 'F1' as tanso,1 soluong"
					+"\n  ) b on kh.TANSO = b.tanso"
					+"\n  where isnull(a.sotuyen,0) >= b.soluong "; 
				data.clear();
				data.add(this.ddkdId); data.add(this.nppId); data.add(this.id);
				data.add(this.ddkdId); data.add(this.nppId); data.add(this.id);
				this.db.viewQuery(query, data);
				ResultSet rs = db.getByPreparedStatement(query, data);
				String ttkh = "";
				if(rs != null)
				{
					while(rs.next())
					{
						ttkh+= rs.getString("SMARTID") +" - "+rs.getString("TEN")+" - "+rs.getString("TANSO")+". Sai tần số.\n";
					}
				}
				if(ttkh.length() > 0)
				{
					geso.dms.center.util.Utility.rollback_throw_exception(db);
					this.msg = "Sai tần số khách hàng \n" + ttkh;
					return false;
				}*/
				
				query = "\n update kh set kh.route_fk = ddkd.route_fk " +
				"\n from khachhang_tuyenbh a " +
				"\n inner join khachhang kh on kh.pk_seq = a.khachhang_fk " +
				"\n inner join tuyenbanhang b on a.tbh_fk = b.pk_seq " +
				"\n inner join daidienkinhdoanh ddkd on ddkd.pk_seq = b.ddkd_fk " +
				"\n where ddkd.pk_seq = ( select top 1  ddkd_fk from tuyenbanhang where pk_seq = ? )";
				data.clear();
				data.add(this.id);
				this.db.viewQuery(query, data);
				db.updateQueryByPreparedStatement(query, data);
				
				
				
				
				
				
				
				query =  "\n with a as " + 
				"\n (" + 
				"\n select * from khachhang_tuyenbh  a" + 
				"\n where tbh_fk in  ( select x.pk_seq from tuyenbanhang x inner join tuyenbanhang now on now.ddkd_fk = x.ddkd_fk and now.npp_fk = x.npp_fk " +
				"\n								where now.pk_seq = "+this.id+" ) " + 
				"\n ) " +
				"\n 	update a set TANSO ='F4'" + 
				"\n  	where a.KHACHHANG_FK in (" + 
				"\n 			select khachhang_fk " + 
				"\n 			from a" + 
				"\n 			where  TANSO in ('F8','F12','F16','F20','F24')" + 
				"\n 			group by khachhang_fk" + 
				"\n 			having count(*) =1 " + 
				"\n 		)" ;
				if(!db.update(query))
				{
					db.update("rollback");
					this.msg = "Khong the cap nhat tan so F4 , " + query;
					return false;
				}
				
				query =  "\n with a as " + 
				"\n (" + 
				"\n select * from khachhang_tuyenbh  a" + 
				"\n where tbh_fk in  ( select x.pk_seq from tuyenbanhang x inner join tuyenbanhang now on now.ddkd_fk = x.ddkd_fk and now.npp_fk = x.npp_fk " +
				"\n								where now.pk_seq = "+this.id+" ) " + 
				"\n ) " +
				"\n 	update a set TANSO ='F8' 		" + 
				"\n  	where a.KHACHHANG_FK in (		" + 
				"\n 			select khachhang_fk 	" + 
				"\n 			from a" + 
				"\n 			where  TANSO != 'F8' 	" + 
				"\n 			group by khachhang_fk	" + 
				"\n 			having count(*) =2 		" + 
				"\n 		)" ;
				if(!db.update(query))
				{
					db.update("rollback");
					this.msg = "Khong the cap nhat tan so F8 , " + query;
					return false;
				}
				
				query =  "\n with a as " + 
				"\n (" + 
				"\n select * from khachhang_tuyenbh  a" + 
				"\n where tbh_fk in  ( select x.pk_seq from tuyenbanhang x inner join tuyenbanhang now on now.ddkd_fk = x.ddkd_fk and now.npp_fk = x.npp_fk " +
				"\n								where now.pk_seq = "+this.id+" ) " + 
				"\n ) " +
				"\n 	update a set TANSO ='F12' 		" + 
				"\n  	where a.KHACHHANG_FK in (		" + 
				"\n 			select khachhang_fk 	" + 
				"\n 			from a" + 
				"\n 			where  TANSO != 'F12' 	" + 
				"\n 			group by khachhang_fk	" + 
				"\n 			having count(*) =3 		" + 
				"\n 		)" ;
				if(!db.update(query))
				{
					db.update("rollback");
					this.msg = "Khong the cap nhat tan so F12 , " + query;
					return false;
				}
				
				query =  "\n with a as " + 
				"\n (" + 
				"\n select * from khachhang_tuyenbh  a" + 
				"\n where tbh_fk in  ( select x.pk_seq from tuyenbanhang x inner join tuyenbanhang now on now.ddkd_fk = x.ddkd_fk and now.npp_fk = x.npp_fk " +
				"\n								where now.pk_seq = "+this.id+" ) " + 
				"\n ) " +
				"\n 	update a set TANSO ='F16' 		" + 
				"\n  	where a.KHACHHANG_FK in (		" + 
				"\n 			select khachhang_fk 	" + 
				"\n 			from a" + 
				"\n 			where  TANSO != 'F16' 	" + 
				"\n 			group by khachhang_fk	" + 
				"\n 			having count(*) =4 		" + 
				"\n 		)" ;
				if(!db.update(query))
				{
					db.update("rollback");
					this.msg = "Khong the cap nhat tan so F16 , " + query;
					return false;
				}
				
				query =  "\n with a as " + 
				"\n (" + 
				"\n select * from khachhang_tuyenbh  a" + 
				"\n where tbh_fk in  ( select x.pk_seq from tuyenbanhang x inner join tuyenbanhang now on now.ddkd_fk = x.ddkd_fk and now.npp_fk = x.npp_fk " +
				"\n								where now.pk_seq = "+this.id+" ) " + 
				"\n ) " +
				"\n 	update a set TANSO ='F20' 		" + 
				"\n  	where a.KHACHHANG_FK in (		" + 
				"\n 			select khachhang_fk 	" + 
				"\n 			from a" + 
				"\n 			where  TANSO != 'F20' 	" + 
				"\n 			group by khachhang_fk	" + 
				"\n 			having count(*) =5		" + 
				"\n 		)" ;
				if(!db.update(query))
				{
					db.update("rollback");
					this.msg = "Khong the cap nhat tan so F20 , " + query;
					return false;
				}
				
				query =  "\n with a as " + 
				"\n (" + 
				"\n select * from khachhang_tuyenbh  a" + 
				"\n where tbh_fk in  ( select x.pk_seq from tuyenbanhang x inner join tuyenbanhang now on now.ddkd_fk = x.ddkd_fk and now.npp_fk = x.npp_fk " +
				"\n								where now.pk_seq = "+this.id+" ) " + 
				"\n ) " +
				"\n 	update a set TANSO ='F24' 		" + 
				"\n  	where a.KHACHHANG_FK in (		" + 
				"\n 			select khachhang_fk 	" + 
				"\n 			from a" + 
				"\n 			where  TANSO != 'F24' 	" + 
				"\n 			group by khachhang_fk	" + 
				"\n 			having count(*) =6		" + 
				"\n 		)" ;
				if(!db.update(query))
				{
					db.update("rollback");
					this.msg = "Khong the cap nhat tan so F24 , " + query;
					return false;
				}


				
				
				
				
				
				
				db.getConnection().commit();
				db.getConnection().setAutoCommit(true);
				
			}catch(Exception er){
				er.printStackTrace();
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				this.msg="Khong The Sua Tuyen Ban Hang Nay,Vui Long Thu Lai,Neu Khong Duoc Lien He Voi Admin De Duoc Giup Do. Loi Dong Lenh Sau :" +er.toString() ;
				return false;
			}
		}
		else
		{   
			this.msg="khach hang nay da phan tuyen ngay lam "+ this.ngaylamviec.trim()+" viec roi";
			return false;
		}
		return true;
	}
	
	boolean kiemtra()
	{
		String sql ="select count(*) as num from tuyenbanhang where ngaylamviec = '"+ this.ngaylamviec.trim() + "' and ddkd_fk = '"+ this.ddkdId +"'";
		/*System.out.println("tuyen " + sql);*/
		ResultSet rs = db.get(sql);
		try 
		{
			if(rs.next())
			{
				if(rs.getInt("num") > 0)
					return true;
			}
			rs.close();
		} 
		catch(Exception e) {}
		
		return false;
	}
	boolean kiemtra_duynhat(String ngayid)
	{
		String sql =" select count(*) as num from tuyenbanhang a inner join nhaphanphoi b on a.NPP_FK = b.PK_SEQ" +
					" inner join daidienkinhdoanh ddkd on ddkd.pk_seq = a.ddkd_fk and a.NPP_FK = ddkd.NPP_FK " +
					" where a.ngayid ='"+ ngayid+"' and a.ddkd_fk ='"+ this.ddkdId +"' and a.pk_seq <>'"+ this.id +"' and b.TRANGTHAI = 1";
		/*System.out.println("tuyen " + sql);*/
		ResultSet rs = db.get(sql);
		try 
		{
			rs.next();
			if(Integer.parseInt(rs.getString("num")) >0)
				return true;
			rs.close();
		} 
		catch(Exception e) {}
		
		return false;
	}
	public boolean MoveTbh(String tbhOld, String tbhNew, String[] khIds, String[] tansoIds,String [] sotts ) //Move cac khach hang thuoc tuyen ban hang cu sang tuyen ban hang moi 
	{
		
		try{
			this.db.getConnection().setAutoCommit(false);
			List<Object> data = new ArrayList<Object>();
			/*String query="delete from khachhang_tuyenbh where tbh_fk= '" + tbhOld + "'";*/
			String query="delete from khachhang_tuyenbh where tbh_fk= ?";
			data.clear();
			data.add(tbhOld);
			if(this.db.updateQueryByPreparedStatement(query, data)<0)
			{
	    		geso.dms.center.util.Utility.rollback_throw_exception(this.db);
				this.msg = "Khong the di chuyen sang tuyen ban hang moi , ";
				return false;
			}
	
		if(khIds != null)
		{
			int size = khIds.length;
			int m = 0;	
			//"Thu hai", "Thu ba", "Thu tu", "Thu nam", "Thu sau", "Thu bay"
			
			
			
			while (m < size )
			{
				
				 if(!kiemtratrungtuyen(khIds[m]))	 
				 {
					 int sott=0;
					 try{
						 sott=Integer.parseInt(sotts[m]);
						 
					 }
					 catch(Exception er){
						 
					 }
			    	/*query = "insert into khachhang_tuyenbh values('" + khIds[m] + "','" + tbhNew + "','" + tansoIds[m] + "','"+sott+"')";*/
			    	query = "insert into khachhang_tuyenbh values(?,?,?,?)";
			    	data.clear();
			    	data.add(khIds[m]); data.add(tbhNew); data.add(tansoIds[m]); data.add(sott);
			    	this.db.viewQuery(query, data);
			    	if(this.db.updateQueryByPreparedStatement(query, data)<0)
					{
			    		geso.dms.center.util.Utility.rollback_throw_exception(this.db);
						this.msg = "Khong the di chuyen sang tuyen ban hang moi , ";
						return false;
					}
				 }
				//}
				m++;	
			}
		}
		
		this.db.getConnection().commit();
		this.db.getConnection().setAutoCommit(true);
		return true;
		}catch(Exception er){
			geso.dms.center.util.Utility.rollback_throw_exception(this.db);
			this.msg="Loi : "+ er.toString();
			return false;
		
		}
		
		
	}
	boolean kiemtratrungtuyen(String kh)
	{
		String sql ="select count(*) as num from khachhang_tuyenbh where khachhang_fk ='"+ kh +"'";
		ResultSet rs= db.get(sql);
		try {
			rs.next();
			if(rs.getString("num").equals("0"))
				return false;
		} catch(Exception e) {
			
			e.printStackTrace();
		}
		finally{try {
			if(rs != null)
				rs.close();
		} catch (Exception e2) {
			
		}}
		
		
		return true;
	}
	
	public void createDdkdRS()
	{
		String query = "select pk_seq as ddkdId, ten as ddkdTen from daidienkinhdoanh where npp_fk='" + this.nppId + "' order by ten";
		this.daidienkd = this.db.get(query);
	}
	
	public void createKh_TbhList()
	{ 
		String query = "\n select a.pk_seq as khId,a.smartid, a.ten, a.diachi, b.tanso,isnull(b.sott,0) as sott ";
		query += "\n from khachhang a inner join khachhang_tuyenbh b on a.pk_seq = b.khachhang_fk where b.tbh_fk='" + this.id + "' order by b.sott";
		
		System.out.println("2.Query tuyen: " + query);
		this.kh_tbh_dpt = this.db.get(query);
		
		
		// Chinh lai : 1 KH thuoc nhieu tuyen ban hang cua 1 DDKD
		String query2 = "\n select pk_seq as khId, smartid, ten, diachi "
				      + "\n from khachhang "
				      + "\n where trangthai = '1' and npp_fk='" + this.nppId + "' "
				      + "\n 	and pk_seq not in (select khachhang_fk from khachhang_tuyenbh where tbh_fk in (select distinct pk_seq from tuyenbanhang where npp_fk = '" + this.nppId + "' ";
		
		if(this.ddkdId.trim().length() > 0){
			query2 += "\n and DDKD_FK ! = "+ this.ddkdId +" ";
		}
		
		query2 += " )) ";
		
		if(this.id != "")
			query2 += "\n and pk_seq not in ( select khachhang_fk from khachhang_tuyenbh where tbh_fk='" + this.id + "')";
		/*
		if(this.tenkhachhang != "")
		{  
			String st = util.replaceAEIOU(this.tenkhachhang.trim());
		  	query2 = query2 + " and upper(ten) like upper(N'%" + st + "%')";
		}
		if(this.diachi != "")
		{
			query2 = query2 + " and upper(diachi) like upper(N'%" + util.replaceAEIOU(this.diachi.trim()) + "%')";
		}
		*/
		
		//Sửa không lấy KH đã có tuyến 2018-07-05 (Diễm yêu cầu)
		query2 += "\n and not exists (select 1 from khachhang_tuyenbh where khachhang_fk = khachhang.pk_seq) ";
		
		query2 += "\n order by pk_seq desc";
		
		System.out.println("3.Query lay khach hang: " + query2 + "\n");
		
		this.kh_tbh_cdpt = this.db.get(query2);
	}
	public void createRS() 
	{
		if ( view.trim().length() <=0 && (nppId == null || nppId.length() <= 0)) {
			this.getNppInfo();
		}
		this.createDdkdRS();
		this.createKh_TbhList();
	}

	public void init()
	{
		String query = "select a.pk_seq as tbhId, a.diengiai as tbhTen, a.ngaytao, a.ngaysua, a.ngaylamviec, b.ten as nguoitao, c.ten as nguoisua, d.pk_seq as ddkdId, d.ten as ddkdTen, e.ten as nppTen, e.pk_seq as nppId";
	    query = query + " from tuyenbanhang a inner join nhanvien b on a.nguoitao = b.pk_seq inner join nhanvien c on a.nguoisua = c.pk_seq inner join daidienkinhdoanh d on a.ddkd_fk = d.pk_seq";
	    query = query + " inner join nhaphanphoi e on a.npp_fk = e.pk_seq where a.pk_seq='" + this.id + "'";
        
	    System.out.println("1.Khoi tao tuyen ban hang: " + query);
	    ResultSet rs =  this.db.get(query);
        try{
            rs.next();        	
        	this.id = rs.getString("tbhId");
        	this.ddkd = rs.getString("ddkdTen");
        	this.diengiai = rs.getString("tbhTen");
        	this.ngaylamviec = rs.getString("ngaylamviec");
        	this.ngaytao = rs.getDate("ngaytao").toString();
        	this.nguoitao = rs.getString("nguoitao");
        	this.ngaysua = rs.getDate("ngaysua").toString();
        	this.nguoisua = rs.getString("nguoisua");       	
        	this.ddkdId = rs.getString("ddkdId");   
        	this.nppId = rs.getString("nppId");
        	//this.msg = "";
       	}
        catch(Exception e){}
        finally{try {
			if(rs != null)
				rs.close();
		} catch (Exception e2) {

		}}
       	this.createRS(); 
	}
	
	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	public ResultSet getNlvList()
	{
		return this.nlvList;
	}

	public void setNlvList(ResultSet nlvList) 
	{
		this.nlvList = nlvList;
	}

	public ResultSet getDiengiaiList() 
	{
		return this.diengiaiList;
	}

	public void setDiengiaiList(ResultSet diengiaiList) 
	{
		this.diengiaiList = diengiaiList;
	}

	public void createNlvList()
	{
		String query = "select distinct ngaylamviec as nlvNewTen from tuyenbanhang";
		if(this.ddkdnewId != "")
			query = query + " where ddkd_fk='" + this.ddkdnewId + "'";
		this.nlvList = this.db.get(query + " order by ngaylamviec");
	}

	public void createDiengiaiList()
	{
		String query = "select diengiai as tbhNewTen from tuyenbanhang where npp_fk='" + this.nppId + "'";
		if(this.nlvnew != "")
			query = query + " and ngaylamviec like '%" + this.nlvnew + "%'";
		if(this.ddkdnewId != "")
			query = query + " and ddkd_fk = '" + this.ddkdnewId + "'";
		this.diengiaiList = this.db.get(query + " order by diengiai");
	}

	@Override
	public void DBclose() {
		
		try {

			if(this.daidienkd != null)
				this.daidienkd.close();
			if(this.diengiaiList != null)
				this.diengiaiList.close();
			if(this.kh_tbh_cdpt != null)
				this.kh_tbh_cdpt.close();
			if(this.kh_tbh_dpt != null)
				this.kh_tbh_dpt.close();
			if(this.nlvList != null)
				this.nlvList.close();
			if(this.tanso != null)
				this.tanso.close();
			if(this.db != null)
				this.db.shutDown();
			
		} catch (Exception e) {
			
		}
		
	}
	
	@Override
	public void setSoTT(String[] _sott) {
		
		this.sott=_sott;
	}

	
	
}
