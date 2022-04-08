package geso.dms.center.beans.dieukienkhuyenmai.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import geso.dms.center.beans.dieukienkhuyenmai.IDieukienkhuyenmai;
import geso.dms.center.beans.dieukienkhuyenmai.ISanpham;
import geso.dms.center.util.Utility;
import geso.dms.distributor.db.sql.dbutils;

public class Dieukienkhuyenmai implements IDieukienkhuyenmai
{
	String userId;
	String id;
	String diengiai;
	String hinhthuc; //0 nhap tong tien, 1 nhap tong luong
	String tongtien;
	String tongluong;
	String type;
	String nguoitao;
	String nguoisua;
	String ngaytao;
	String ngaysua;
	String msg;
	
	ResultSet nhomspRs;
	String nhomspId;
	
	List<ISanpham> spList;
	Hashtable<String, Integer> sp_nhomspIds;
	List<ISanpham> spSudungList; //list sanpham nguoi dung nhap
	
	dbutils db;
	
	public Dieukienkhuyenmai(String[] param)
	{
		this.id = param[0];
		this.diengiai = param[1];
		this.tongtien = param[2];
		this.tongluong = param[3];
		this.type = param[4];
		this.ngaytao = param[5];
		this.nguoitao = param[6];
		this.ngaysua = param[7];
		this.nguoisua = param[8];		
		this.msg = "";
		this.hinhthuc = "";
		this.nhomspId = "";
		
		sp_nhomspIds = new Hashtable<String, Integer>();
		spSudungList = new ArrayList<ISanpham>();
		db = new dbutils();
	}
	
	public Dieukienkhuyenmai(String id)
	{
		this.id = id;
		this.diengiai = "";
		this.tongtien = "";
		this.tongluong = "";
		this.type = "";
		this.ngaytao = "";
		this.nguoitao = "";
		this.ngaysua = "";
		this.nguoisua = "";
		this.msg = "";
		this.hinhthuc = "";
		this.nhomspId = "";
		
		sp_nhomspIds = new Hashtable<String, Integer>();
		spSudungList = new ArrayList<ISanpham>();
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
	public void setDiengiai(String diengiai) 
	{
		this.diengiai = diengiai;
	}
	public String getDiengiai()
	{
		return diengiai;
	}
	public String getTongtien()
	{
		return this.tongtien;
	}
	public void setTongtien(String tongtien)
	{
		this.tongtien = tongtien;
	}
	public String getTongluong()
	{
		return this.tongluong;
	}
	public void setTongluong(String tongluong)
	{
		this.tongluong = tongluong;
	}
	public String getType()
	{
		return this.type;
	}
	public void setType(String type)
	{
		this.type = type;
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
	
	public List<ISanpham> getSpList() 
	{
		return this.spList;
	}

	public void setSpList(List<ISanpham> spList)
	{
		this.spList = spList;
	}

	public boolean CreateDkkm(String[] masp, String[] soluong) 
	{
		dbutils db = new dbutils();
		this.ngaytao = getDateTime();
		this.nguoitao = this.userId;
		
		if(this.type.equals("1"))
			this.tongluong = this.tongtien = "";
		if(this.tongluong == "")
			this.tongluong = null;
		if(this.tongtien == "")
			this.tongtien = null;
		if(!this.type.equals("3"))
		{
		try 
		{
			db.getConnection().setAutoCommit(false);
				
/*			String query = "Insert into Dieukienkhuyenmai(diengiai, tongluong, tongtien, loai, ngaytao, nguoitao, ngaysua, nguoisua) values(";
			query = query + "N'" + this.diengiai + "', " + this.tongluong + ", " + this.tongtien + ", '" + this.type + "', '" + this.ngaytao + "', '" + this.nguoitao + "', '" + this.ngaytao + "', '" + this.nguoitao + "')";
			
			if(!db.update(query))
			{
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				this.msg = "Khong the tao moi 'Dieukienkhuyenmai', " + query;
				return false; 
			}*/
			
			String query = "INSERT INTO DIEUKIENKHUYENMAI(diengiai, tongluong, tongtien, loai, ngaytao, nguoitao, ngaysua, nguoisua) select ?,?,?,?,?,?,?,?";

			Object[] data = {this.diengiai,this.tongluong,this.tongtien,this.type,this.ngaytao,this.nguoitao,this.ngaytao,this.nguoitao};
			dbutils.viewQuery(query,data);
			if (this.db.updateQueryByPreparedStatement(query,data) != 1) {
				this.msg = "Lỗi trong quá trình lưu dữ liệu, vui lòng liên hệ admin để xử lý!";
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				return false;
			}
			
			if(masp.length > 0)
			{
				//lay dkkm current
				String dkkkmCurrent = "";
/*				query = "select IDENT_CURRENT('Dieukienkhuyenmai') as dkkmId";
				
				ResultSet rsDkkm = this.db.get(query);						
				rsDkkm.next();
				dkkkmCurrent = rsDkkm.getString("dkkmId");
				rsDkkm.close();*/
				
				dkkkmCurrent = this.db.getPk_seq();
				
				for(int i = 0; i < masp.length; i++)
				{
					if(masp[i].length() > 0)
					{
						ResultSet rs = db.get("select pk_seq from sanpham where ma='" + masp[i].trim() + "'");
						String pk_seq = "";
						if(rs != null) 
						{
							try
							{
								rs.next();
								pk_seq = rs.getString("pk_seq");
								rs.close();
							}
							catch(Exception e) {}
						} 
					/*if(this.type.equals("2"))
							soluong[i] = "null";
						if(pk_seq != "")
							query = "Insert into dieukienkm_sanpham(dieukienkhuyenmai_fk, sanpham_fk, soluong, sotien) values('" + dkkkmCurrent + "', '" + pk_seq + "', " + soluong[i].trim() + ", null)";
							*/
						String batbuoc = "null";
						Object[] data1;
				
						if(this.type.equals("2") && soluong[i].length() >0)
	                  	{ 	
//							query = "Insert into dieukienkm_sanpham(dieukienkhuyenmai_fk, sanpham_fk, soluong,batbuoc) values('" + dkkkmCurrent + "', '" + pk_seq + "', " + soluong[i].trim() + ",1)";
							
							query = "INSERT INTO DIEUKIENKM_SANPHAM(dieukienkhuyenmai_fk, sanpham_fk, soluong,batbuoc) select ?,?,?,1";

							data1 = new Object[] {dkkkmCurrent,pk_seq,soluong[i].trim()};
	             		}
						else
						{
							if(soluong[i].length()> 0) {
	//						 	query = "Insert into dieukienkm_sanpham(dieukienkhuyenmai_fk, sanpham_fk, soluong,batbuoc) values('" + dkkkmCurrent + "', '" + pk_seq + "', " + soluong[i].trim() + ",null)";
							
							 	query = "INSERT INTO DIEUKIENKM_SANPHAM(dieukienkhuyenmai_fk, sanpham_fk, soluong,batbuoc) select ?,?,?,null";

							 	data1 = new Object[] {dkkkmCurrent,pk_seq,soluong[i].trim()};
							}    else {
	//						    	query = "Insert into dieukienkm_sanpham(dieukienkhuyenmai_fk, sanpham_fk, soluong,batbuoc) values('" + dkkkmCurrent + "', '" + pk_seq + "',null,null)";
							
							    	query = "INSERT INTO DIEUKIENKM_SANPHAM(dieukienkhuyenmai_fk, sanpham_fk, soluong,batbuoc) select ?,?,null,null";

							    	data1 = new Object[] {dkkkmCurrent,pk_seq};
							}
						}
						
				/*		if(!db.update(query))
						{
							geso.dms.center.util.Utility.rollback_throw_exception(db);
							this.msg = "Loi khi them moi bang dieukienkm_sanpham, " + query;
							return false; 
						}*/
						
						dbutils.viewQuery(query,data1);
						if (this.db.updateQueryByPreparedStatement(query,data1) != 1) {
							this.msg = "Lỗi trong quá trình lưu dữ liệu, vui lòng liên hệ admin để xử lý!";
							geso.dms.center.util.Utility.rollback_throw_exception(db);
							return false;
						}
					}					
				}
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch(Exception e) {
			geso.dms.center.util.Utility.rollback_throw_exception(this.db);
			this.msg="Khong The Thuc Hien Luu Dieu Kien Khuyen Mai Nay,Vui Long Lien He Voi Admin De Sua Loi Nay";

			return false;
		}
		}
		else
		{
			//db.getConnection().setAutoCommit(false);
			
/*			String query = "Insert into Dieukienkhuyenmai(diengiai, tongtien, loai, ngaytao, nguoitao, ngaysua, nguoisua) values(";
			query = query + "N'" + this.diengiai + "'," + this.tongtien + ", '" + this.type + "', '" + this.ngaytao + "', '" + this.nguoitao + "', '" + this.ngaytao + "', '" + this.nguoitao + "')";
			
			if(!db.update(query))
			{
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				this.msg = "Khong the tao moi 'Dieukienkhuyenmai', " + query;
				return false; 
			}*/
			
			String query = "INSERT INTO DIEUKIENKHUYENMAI(diengiai, tongtien, loai, ngaytao, nguoitao, ngaysua, nguoisua) select ?,?,?,?,?,?,?";

			Object[] data = {this.diengiai,this.tongtien,this.type,this.ngaytao,this.nguoitao,this.ngaytao,this.nguoitao};
			dbutils.viewQuery(query,data);
			if (this.db.updateQueryByPreparedStatement(query,data) != 1) {
				this.msg = "Lỗi trong quá trình lưu dữ liệu, vui lòng liên hệ admin để xử lý!";
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				return false;
			}
		}
			
		return true;
	}

	public boolean UpdateDkkm(String[] masp, String[] soluong) 
	{
		dbutils db = new dbutils();
		
		if(checkExits(db))
		{
			this.msg = "Điều kiện khuyến mại đã được sử dụng cho chương trình khuyến mại. Bạn không thể sửa lại.";
			return false;
		}
		
		this.ngaysua = getDateTime();
		this.nguoisua = this.userId;
		
		if(this.type.equals("1"))
			this.tongluong = this.tongtien = "";
		if(this.tongluong == "")
			this.tongluong = null;
		if(this.tongtien == "")
			this.tongtien = null;
		if(!this.type.equals("3"))
		{		
		try 
		{
			db.getConnection().setAutoCommit(false);
				
/*			String query = "Update Dieukienkhuyenmai set diengiai = N'" + this.diengiai + "', tongluong = " + this.tongluong + ", tongtien = " + this.tongtien + ", loai = '" + this.type + "', ngaysua = '" + this.ngaysua + "', nguoisua = '" + this.nguoisua + "' where pk_seq='" + this.id + "'";
		
			if(!db.update(query))
			{
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				this.msg = "Khong the cap nhat 'Dieukienkhuyenmai', " + query;
				return false; 
			}*/
			
			String query = "Update Dieukienkhuyenmai set diengiai = ?, tongluong = ?, tongtien = ?, loai = ?, ngaysua = ?, nguoisua = ? where pk_seq= ?";

			Object[] data = {this.diengiai,this.tongluong,this.tongtien,this.type,this.ngaysua,this.nguoisua,this.id};
			dbutils.viewQuery(query,data);
			if (this.db.updateQueryByPreparedStatement(query,data) != 1) {
				this.msg = "Lỗi trong quá trình lưu dữ liệu, vui lòng liên hệ admin để xử lý!";
				this.db.getConnection().rollback();
				return false;
			}
			
			if(masp.length > 0)
			{
				query = "delete from dieukienkm_sanpham where dieukienkhuyenmai_fk = '" + this.id + "'";
				System.out.println("dkakdkadk "+query);
				db.update(query);
				db.update("commit");
				System.out.println("masp.length "+masp.length);
				for(int i = 0; i < masp.length; i++)
				{
					System.out.println("thu tu " + i);
					if(masp[i].length() > 0)
					{
						ResultSet rs = db.get("select pk_seq from sanpham where ma='" + masp[i].trim() + "'");
						String pk_seq = "";
						
							try
							{
								rs.next();
								pk_seq = rs.getString("pk_seq");
								rs.close();
							}
							catch(Exception e) {
								e.printStackTrace();
							}
						 
					/*	if(this.type.equals("2"))
							soluong[i] = "null";
						if(pk_seq != "")
							query = "Insert into dieukienkm_sanpham(dieukienkhuyenmai_fk, sanpham_fk, soluong, sotien) values('" + this.id + "', '" + pk_seq + "', " + soluong[i].trim() + ", null)";
							*/
						
						Object[] data1;
						
						if(this.type.equals("2") && soluong[i].length()> 0)
						{
							//soluong[i] = "null";
//							query = "Insert into dieukienkm_sanpham(dieukienkhuyenmai_fk, sanpham_fk, soluong,batbuoc) values('" + this.id + "', '" + pk_seq + "', " + soluong[i].trim() + ",1)";
								
							query = "INSERT INTO DIEUKIENKM_SANPHAM(dieukienkhuyenmai_fk, sanpham_fk, soluong,batbuoc) select ?,?,?,1";

							data1 = new Object[] {this.id,pk_seq,soluong[i].trim()};
						}
						else
					//	if(pk_seq != "")
						{
							if(soluong[i].length()> 0) {
//								query = "Insert into dieukienkm_sanpham(dieukienkhuyenmai_fk, sanpham_fk, soluong,batbuoc) values('" + this.id + "', '" + pk_seq + "', " + soluong[i].trim() + ",null)";
							
								query = "INSERT INTO DIEUKIENKM_SANPHAM(dieukienkhuyenmai_fk, sanpham_fk, soluong,batbuoc) select ?,?,?,null";

							 	data1 = new Object[] {this.id,pk_seq,soluong[i].trim()};
							} else {
//								query = "Insert into dieukienkm_sanpham(dieukienkhuyenmai_fk, sanpham_fk, soluong,batbuoc) values('" + this.id + "', '" + pk_seq + "',null,null)";
						
								query = "INSERT INTO DIEUKIENKM_SANPHAM(dieukienkhuyenmai_fk, sanpham_fk, soluong,batbuoc) select ?,?,null,null";

						    	data1 = new Object[] {this.id,pk_seq};
							}
						}
			/*			if(!db.update(query))
						{
							geso.dms.center.util.Utility.rollback_throw_exception(db);
							this.msg = "Loi khi cap nhat bang dieukienkm_sanpham, " + query;
							return false; 
						}*/
						
						dbutils.viewQuery(query,data1);
						if (this.db.updateQueryByPreparedStatement(query,data1) != 1) {
							this.msg = "Lỗi trong quá trình lưu dữ liệu, vui lòng liên hệ admin để xử lý!";
							db.getConnection().rollback();
							return false;
						}
					}					
				}
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);

		} 
		catch(Exception e) {
			e.printStackTrace();
			try {
				this.db.getConnection().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			this.msg="Khong The Thuc Hien Luu Dieu Kien Khuyen Mai Nay,Vui Long Lien He Voi Admin De Sua Loi Nay";

			return false;
			
		}
		}
		else
		{
			//db.getConnection().setAutoCommit(false);
			
/*			String query = "Update Dieukienkhuyenmai set diengiai = N'" + this.diengiai + "', tongtien = " + this.tongtien + ", loai = '" + this.type + "', ngaysua = '" + this.ngaysua + "', nguoisua = '" + this.nguoisua + "' where pk_seq='" + this.id + "'";
			
			if(!db.update(query))
			{
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				this.msg = "Khong the cap nhat 'Dieukienkhuyenmai', " + query;
				return false; 
			}*/
			
			String query = "Update Dieukienkhuyenmai set diengiai = ?, tongtien = ?, loai = ?, ngaysua = ?, nguoisua = ? where pk_seq=?";

			Object[] data = {this.diengiai,this.tongtien,this.type,this.ngaysua,this.nguoisua,this.id};
			dbutils.viewQuery(query,data);
			if (this.db.updateQueryByPreparedStatement(query,data) != 1) {
				this.msg = "Lỗi trong quá trình lưu dữ liệu, vui lòng liên hệ admin để xử lý!";
				try {
					db.getConnection().rollback();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return false;
			}
		}
		return true;
	}

	private boolean checkExits(dbutils db) 
	{
		String query = "SELECT COUNT(pb.NPP_FK) AS SL "
				+ "\nFROM dbo.CTKM_DKKM ctdk "
				+ "\n	INNER JOIN dbo.PHANBOKHUYENMAI pb ON pb.CTKM_FK = ctdk.CTKHUYENMAI_FK "
				+ "\nWHERE ctdk.DKKHUYENMAI_FK = " + this.id;
			ResultSet rs = db.get(query);
		int sodong = 0;
		if(rs != null)
		{
			try 
			{
				if(rs.next())
				{
					sodong = rs.getInt("sl");
					rs.close();
				}
			} catch(Exception e) { sodong = 0; }
		}
		if(sodong > 0)
			return true;
		return false;
	}

	public void createRS() 
	{   
		Utility Ult = new Utility();
		String query = "select pk_seq as nspId, diengiai as nspTen from nhomsanphamkm where type = '1' and trangthai='1' ";
		System.out.println("lay nhom sp " + query);
		//				+ "and pk_seq in (select nsp_fk from NHOMSANPHAM_SANPHAM where sp_fk in "+ Ult.quyen_sanpham(this.userId)+")";
		this.nhomspRs = db.get(query);		
		
//		System.out.println("Lay Nhom san pham la: select pk_seq as nspId, diengiai as nspTen from nhomsanpham where type = '1' and trangthai='1' and pk_seq in (select nsp_fk from NHOMSANPHAM_SANPHAM where sp_fk in "+ Ult.quyen_sanpham(this.userId)+")");
		if(this.nhomspId.length() > 0)
			this.createSpList();
	}
	
	private void createSpList()
	{
		String query = "select b.pk_seq, b.MA, b.TEN from NHOMSANPHAMKM_SANPHAM a inner join SANPHAM b on a.SP_FK = b.PK_SEQ where a.NSP_FK = '" + this.nhomspId + "'";	
		ResultSet rs = db.get(query);
		List<ISanpham> splist = new ArrayList<ISanpham>();
		if(rs != null)
		{
			try 
			{
				ISanpham sp = null;
				while(rs.next())
				{
					String spId = rs.getString("pk_seq");
					String ma = rs.getString("MA");
					String ten = rs.getString("TEN");
					sp = new Sanpham(spId, ma, ten, "", "");
					splist.add(sp);
				}
			} 
			catch(Exception e) {}
		}
		this.spList = splist;
	}
	
	public void createDkkmSpList()
	{		
		String query = "select a.soluong, b.pk_seq, b.MA, b.TEN from dieukienkm_sanpham a inner join SANPHAM b on a.SanPham_FK = b.PK_SEQ where a.dieukienkhuyenmai_fk = '" + this.id + "'";				
		ResultSet rs = db.get(query);
		List<ISanpham> splist = new ArrayList<ISanpham>();
		if(rs != null)
		{
			try 
			{
				ISanpham sp = null;
				while(rs.next())
				{
					String spId = rs.getString("pk_seq");
					String ma = rs.getString("MA");
					String ten = rs.getString("TEN");
					String soluong = "";
					if(rs.getString("soluong") != null)
						soluong = rs.getString("soluong");
					sp = new Sanpham(spId, ma, ten, soluong, "");
					splist.add(sp);
				}
			} 
			catch(Exception e) {}
		}
		this.spSudungList = splist;
	}

	public void init() 
	{
		String query = "select a.PK_SEQ as dkkmId, a.DIENGIAI, tongluong, tongtien, a.LOAI, a.NGAYTAO, a.NGAYSUA, b.TEN as nguoitao, c.TEN as nguoisua ";
		query = query + " from DIEUKIENKHUYENMAI a inner join NHANVIEN b on a.NGUOITAO = b.PK_SEQ inner join NHANVIEN c on a.NGUOISUA = c.PK_SEQ where a.PK_SEQ='" + this.id + "'";
		ResultSet rs = db.get(query);
		try
        {
            rs.next();        	
            this.id = rs.getString("dkkmId");
			this.diengiai = rs.getString("diengiai");
			if(rs.getString("tongluong") != null)
				this.tongluong = rs.getString("tongluong");
			if(rs.getString("tongtien") != null)
				this.tongtien = rs.getString("tongtien");
			
			this.type = rs.getString("loai");
			this.ngaytao = rs.getString("ngaytao");
			this.nguoitao = rs.getString("nguoitao");
			this.ngaysua = rs.getString("ngaysua");
			this.nguoisua = rs.getString("nguoisua");		
			
			
			
			 if(this.tongtien.length() > 0)
		        	this.hinhthuc = "2";
		        else
		        	this.hinhthuc = "1";
			 
			 
			rs.close();
       	}
        catch(Exception e){
        	e.printStackTrace();
        }
        
        Utility Ult = new Utility();
        query = "select pk_seq as nspId, diengiai as nspTen from nhomsanphamkm where type = '1' and trangthai='1' ";
        System.out.println("lay nhom sp " + query);
//		+ "and pk_seq in (select nsp_fk from NHOMSANPHAM_SANPHAM where sp_fk in "+ Ult.quyen_sanpham(this.userId)+")";
        this.nhomspRs = db.get(query);	
 //       this.nhomspRs = db.get("select pk_seq as nspId, diengiai as nspTen from nhomsanpham where type = '1' and trangthai='1' and a.pk_seq in (select nsp_fk from NHOMSANPHAM_SANPHAM where sp_fk in"+ Ult.quyen_sanpham(this.userId)+")");	
        this.createDkkmSpList();
	}
	
	public ResultSet getNhomspRs()
	{
		return this.nhomspRs;
	}

	public void setNhomspRs(ResultSet nhomspRs) 
	{
		this.nhomspRs = nhomspRs;
	}

	public String getNhomspId() 
	{
		return this.nhomspId;
	}

	public void setNhomspId(String nhomspId) 
	{
		this.nhomspId = nhomspId;
	}

	public String getHinhthuc() 
	{
		return this.hinhthuc;
	}

	public void setHinhthuc(String hinhthuc) 
	{
		this.hinhthuc = hinhthuc;
	}

	public Hashtable<String, Integer> getSp_nhomspIds()
	{
		return this.sp_nhomspIds;
	}

	public void setSp_nhomspIds(Hashtable<String, Integer> sp_nhomspIds)
	{
		this.sp_nhomspIds = sp_nhomspIds;
	}

	public List<ISanpham> getSpSudungList() 
	{
		return this.spSudungList;
	}

	public void setSpSudungList(List<ISanpham> splist) 
	{
		this.spSudungList = splist;
	}
	
	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	
}
