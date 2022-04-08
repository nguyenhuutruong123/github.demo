package geso.dms.center.beans.chitieu.imp;

import geso.dms.center.beans.chitieu.IChuLuc;
import geso.dms.center.db.sql.dbutils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

public class ChuLuc  implements IChuLuc
{

	String userId;
	String id;
	String scheme;
	String thang;
	String nam;
	String diengiai;

	ResultSet sanphamRs;
	String spIds;
	ResultSet nppRs;
	String nppIds;

	ResultSet vungRs;
	String vungIds;
	ResultSet kvRs;
	String kvIds;

	String[] tumuc;
	String[] denmuc;

	String msg;

	dbutils db;
	NumberFormat formater = new DecimalFormat("##,###,###");

	public ChuLuc()
	{
		this.id = "";
		this.thang = "";
		this.nam = "";
		this.diengiai = "";
		this.msg = "";
		this.scheme = "";
		this.spIds = "";
		this.nppIds = "";
		this.vungIds = "";
		this.kvIds = "";
		this.doituong="";
		this.apdung="0";
		this.quy="";
		this.loaichitieu="0";
		this.view="";
		this.asmIds="";
		this.rsmIds="";
		this.tdvIds="";
		this.ssIds="";
		this.sql="";
		this.sqlNhom="";
		this.thuongSR="";
		this.thuongSS="";
		this.thuongASM="";
		this.thuongRSM="";
		this.sonhomdat="";
		this.db = new dbutils();
	}

	public ChuLuc(String id)
	{
		this.id = id;
		this.thang = "";
		this.nam = "";
		this.diengiai = "";
		this.msg = "";
		this.scheme = "";
		this.spIds = "";
		this.nppIds = "";
		this.vungIds = "";
		this.kvIds = "";
		this.doituong="";
		this.apdung="0";
		this.quy="";
		this.loaichitieu="0";
		this.view="";
		this.asmIds="";
		this.rsmIds="";
		this.tdvIds="";
		this.ssIds="";
		this.sql="";
		this.sqlNhom="";
		this.thuongSR="";
		this.thuongSS="";
		this.thuongASM="";
		this.thuongRSM="";
		this.sonhomdat="";
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

	public String getThang()
	{
		return this.thang;
	}

	public void setThang(String thang)
	{
		this.thang = thang;
	}

	public String getNam()
	{
		return this.nam;
	}

	public void setNam(String nam)
	{
		this.nam = nam;
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

	public String getMsg()
	{
		return this.msg;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}

	public boolean save()
	{
		try
		{
			if (this.nam.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn kết thúc";
				return false;
			}

			if (this.scheme.trim().length() <= 0)
			{
				this.msg = "Vui lòng scheme";
				return false;
			}			

			if (this.doituong.trim().length() <= 0)
			{
				this.msg = "Vui lòng đối tượng ";
				return false;
			}

			db.getConnection().setAutoCommit(false);

			String	query =
					" INSERT INTO [ChiTieuChuLuc]([Ky],[Nam],[Quy],[Ma],[Ten],[NguoiTao],[NguoiSua],[NgayTao],[NgaySua],[TrangThai],[ApDung],[LoaiChiTieu],[DoiTuong],ThuongSR,ThuongSS,ThuongASM,ThuongBM,SoNhomDat)" +
							" select "+(this.thang.length()<=0?"NULL":this.thang)+","+(this.nam.length()<=0?"NULL":this.nam)+","+(this.quy.length()<=0?"NULL":this.quy)+",N'"+this.scheme+"',N'"+this.diengiai+"','"+this.userId+"','"+this.userId+"','"+this.getDateTime()+"','"+this.getDateTime()+"',0,'"+this.apdung+"','"+this.loaichitieu+"','"+this.doituong+"',"+(this.thuongSR.length()<=0?"NULL":this.thuongSR)+"," +
							" "+(this.thuongSS.length()<=0?"NULL":this.thuongSS)+","+(this.thuongASM.length()<=0?"NULL":this.thuongASM)+","+(this.thuongRSM.length()<=0?"NULL":this.thuongRSM)+","+(this.sonhomdat.length()<=0?"NULL":this.sonhomdat)+" ";
			System.out.println("1.Insert: " + query);
			if (!db.update(query))
			{
				this.msg = "Khong the tao moi ChiTieu: " + query;
				db.getConnection().rollback();
				return false;
			}

			query = "select scope_identity() as pxkId";
			ResultSet	rs = db.get(query);
			rs.next();
			this.id = rs.getString("pxkId");
			rs.close();

			String table="";
			if(doituong.equals("SR"))
			{
				table ="DDKD";
			}
			else  if(doituong.equals("SS"))
			{
				table ="GSBH";
			}
			else  if(doituong.equals("ASM"))
			{
				table ="ASM";
			}
			else  if(doituong.equals("RSM"))
			{
				table ="BM";
			}
			Enumeration<String> keys =this.tdv_chitieu.keys();
			while(keys.hasMoreElements())
			{
				String key = keys.nextElement();
				String nvId =key.split("__")[0];
				String nhomId =key.split("__")[1];
				String value=tdv_chitieu.get(key);

				query ="insert into ChiTieuChuLuc_"+table+"(ChiTieuChuLuc_FK,"+table+"_FK,NHOMSP_FK,ChiTieu)" +
						" select '"+this.id+"','"+nvId+"','"+nhomId+"','"+value+"' ";
				System.out.println("[ChiTieuChuLuc_"+table+"]"+query);
				if (!db.update(query))
				{
					this.msg = "Khong the tao moi ChiTieu: " + query;
					db.getConnection().rollback();
					return false;
				}	
			}							
			keys =this.nhomChon.keys();
			while(keys.hasMoreElements())
			{
				String key = keys.nextElement();
				query ="insert into  ChiTieuChuLuc_SanPham(ChiTieuChuLuc_FK,SANPHAM_FK,NHOMSP_FK)" +
						"	select "+this.id+",SP_FK,NSP_FK from NHOMSANPHAM_SANPHAM "+
						"	where NSP_FK in "+ 
						"	("+key+" )  ";
				System.out.println("[ChiTieuChuLuc_SanPham]"+query);
				if (!db.update(query))
				{
					this.msg = "Khong the tao moi ChiTieuChuLuc_SanPham: " + query;
					db.getConnection().rollback();
					return false;
				}	
			}		

			keys =this.nhomChon.keys();
			while(keys.hasMoreElements())
			{
				String key = keys.nextElement();
				String value = this.nhomChon.get(key);
				String trongso= this.trongso.get(key);

				query ="insert into  ChiTieuChuLuc_NHOMSP(ChiTieuChuLuc_FK,NHOMSP_FK,TrongSo,NhomTen)" +
						" select '"+this.id+"','"+key+"','"+trongso+"',N'"+value+"' "; 
				System.out.println("[ChiTieuChuLuc_NHOMSP]"+query);
				if (!db.update(query))
				{
					this.msg = "Khong the tao moi ChiTieuChuLuc_SanPham: " + query;
					db.getConnection().rollback();
					return false;
				}	
			}	

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch (Exception e)
		{
			e.printStackTrace();
			try
			{
				db.getConnection().rollback();
				System.out.println("__EXCEPTION UPDATE: " + e.getMessage());
			} catch (SQLException e1)
			{
			}
			return false;
		}

		return true;
	}

	public boolean edit()
	{
		try
		{
			if (this.nam.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn kết thúc";
				return false;
			}

			if (this.scheme.trim().length() <= 0)
			{
				this.msg = "Vui lòng scheme";
				return false;
			}			

			db.getConnection().setAutoCommit(false);

			String	query =
					" update [ChiTieuChuLuc] set [Ky]="+(this.thang.trim().length()<=0?"NULL":this.thang)+",[Nam]='"+(this.nam.trim().length()<=0?"NULL":this.nam)+"',[Quy]="+(this.quy.trim().length()<=0?"NULL":this.quy)+",[Ma]=N'"+this.scheme+"',[Ten]=N'"+this.diengiai+"',[NguoiSua]='"+this.userId+"',[NgaySua]='"+this.getDateTime()+"',[ApDung]='"+this.apdung+"',DoiTuong='"+this.doituong+"'," +
							"  ThuongSR ="+(this.thuongSR.length()<=0?"NULL":this.thuongSR)+"," +
							" ThuongSS="+(this.thuongSS.length()<=0?"NULL":this.thuongSS)+",ThuongASM="+(this.thuongASM.length()<=0?"NULL":this.thuongASM)+",THUONGRSM="+(this.thuongRSM.length()<=0?"NULL":this.thuongRSM)+",SoNhomDat="+(this.sonhomdat.length()<=0?"NULL":this.sonhomdat)+" "+		
							" where pk_seq='"+this.id+"'  " ;
			System.out.println("1.update: " + query);
			if (!db.update(query))
			{
				this.msg = "Khong the tao moi ChiTieu: " + query;
				db.getConnection().rollback();
				return false;
			}

			query="delete from ChiTieuChuLuc_DDKD where ChiTieuChuLuc_FK='"+this.id+"'";
			if (!db.update(query))
			{
				this.msg = "Khong the tao moi ChiTieu: " + query;
				db.getConnection().rollback();
				return false;
			}

			query="delete from ChiTieuChuLuc_GSBH where ChiTieuChuLuc_FK='"+this.id+"'";
			if (!db.update(query))
			{
				this.msg = "Khong the tao moi ChiTieu: " + query;
				db.getConnection().rollback();
				return false;
			}

			query="delete from ChiTieuChuLuc_ASM where ChiTieuChuLuc_FK='"+this.id+"'";
			if (!db.update(query))
			{
				this.msg = "Khong the tao moi ChiTieu: " + query;
				db.getConnection().rollback();
				return false;
			}

			query="delete from ChiTieuChuLuc_BM where ChiTieuChuLuc_FK='"+this.id+"'";
			if (!db.update(query))
			{
				this.msg = "Khong the tao moi ChiTieu: " + query;
				db.getConnection().rollback();
				return false;
			}

			query="delete from ChiTieuChuLuc_SanPham where ChiTieuChuLuc_FK='"+this.id+"'";
			if (!db.update(query))
			{
				this.msg = "Khong the tao moi ChiTieu: " + query;
				db.getConnection().rollback();
				return false;
			}

			query="delete from ChiTieuChuLuc_NHOMSP where ChiTieuChuLuc_FK='"+this.id+"'";
			if (!db.update(query))
			{
				this.msg = "Khong the tao moi ChiTieu: " + query;
				db.getConnection().rollback();
				return false;
			}
			String table="";
			if(doituong.equals("SR"))
			{
				table ="DDKD";
			}
			else  if(doituong.equals("SR"))
			{
				table ="GSBH";
			}
			else  if(doituong.equals("ASM"))
			{
				table ="ASM";
			}
			else  if(doituong.equals("RSM"))
			{
				table ="BM";
			}
			Enumeration<String> keys =this.tdv_chitieu.keys();
			while(keys.hasMoreElements())
			{
				String key = keys.nextElement();
				String nvId =key.split("__")[0];
				String nhomId =key.split("__")[1];
				String value=tdv_chitieu.get(key);

				query ="insert into ChiTieuChuLuc_"+table+"(ChiTieuChuLuc_FK,"+table+"_FK,NHOMSP_FK,ChiTieu)" +
						" select '"+this.id+"','"+nvId+"','"+nhomId+"','"+value+"' ";
				System.out.println("[ChiTieuChuLuc_"+table+"]"+query);
				if (!db.update(query))
				{
					this.msg = "Khong the tao moi ChiTieu: " + query;
					db.getConnection().rollback();
					return false;
				}	
			}							
			keys =this.nhomChon.keys();
			while(keys.hasMoreElements())
			{
				String key = keys.nextElement();
				query ="insert into  ChiTieuChuLuc_SanPham(ChiTieuChuLuc_FK,SANPHAM_FK,NHOMSP_FK)" +
						"	select "+this.id+",SP_FK,NSP_FK from NHOMSANPHAM_SANPHAM "+
						"	where NSP_FK in "+ 
						"	("+key+" )  ";
				System.out.println("[ChiTieuChuLuc_SanPham]"+query);
				if (!db.update(query))
				{
					this.msg = "Khong the tao moi ChiTieuChuLuc_SanPham: " + query;
					db.getConnection().rollback();
					return false;
				}	
			}		

			keys =this.nhomChon.keys();
			while(keys.hasMoreElements())
			{
				String key = keys.nextElement();
				String value = this.nhomChon.get(key);
				String trongso= this.trongso.get(key);

				query ="insert into  ChiTieuChuLuc_NHOMSP(ChiTieuChuLuc_FK,NHOMSP_FK,TrongSo,NhomTen)" +
						" select '"+this.id+"','"+key+"','"+trongso+"',N'"+value+"' "; 
				System.out.println("[ChiTieuChuLuc_NHOMSP]"+query);
				if (!db.update(query))
				{
					this.msg = "Khong the tao moi ChiTieuChuLuc_SanPham: " + query;
					db.getConnection().rollback();
					return false;
				}	
			}	
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch (Exception e)
		{
			e.printStackTrace();
			try
			{
				db.getConnection().rollback();
				System.out.println("__EXCEPTION UPDATE: " + e.getMessage());
			} catch (SQLException e1)
			{
			}
			return false;
		}

		return true;
	}

	public void init()
	{
		String query = 
				"select ApDung,ma as ma, Ky, nam,Quy, ten,loaichitieu,trangthai,DoiTuong,ThuongSR,ThuongSS,ThuongASM,ThuongRSM,SoNhomDat  " + 
						"from ChiTieuChuLuc where pk_seq = '" + this.id + "'";
		System.out.println("__Khoi tao tieu chi thuong: " + query);
		ResultSet rs = db.get(query);
		if (rs != null)
		{
			try
			{
				while (rs.next())
				{
					this.scheme = rs.getString("ma");
					this.thang = rs.getString("Ky")==null?"":rs.getString("Ky");
					this.nam = rs.getString("nam");
					this.diengiai = rs.getString("ten");
					this.quy=rs.getString("quy")==null?"":rs.getString("quy");
					this.apdung=rs.getString("apdung")==null?"":rs.getString("apdung");
					this.doituong=rs.getString("doituong")==null?"":rs.getString("doituong");
					this.thuongSR=  rs.getString("thuongSR")!=null?formater.format(rs.getDouble("thuongSR")):"";
					this.thuongSS=  rs.getString("thuongSS")!=null?formater.format(rs.getDouble("thuongSS")):"";
					this.thuongASM=  rs.getString("thuongASM")!=null?formater.format(rs.getDouble("thuongASM")):"";
					this.thuongRSM=  rs.getString("thuongRSM")!=null?formater.format(rs.getDouble("thuongRSM")):"";
					this.sonhomdat=  rs.getString("sonhomdat")!=null?formater.format(rs.getDouble("sonhomdat")):"";
				}
				rs.close();		

				query = "select ChiTieuChuLuc_FK,NHOMSP_FK,TrongSo,NhomTen " +
						" from ChiTieuChuLuc_NHOMSP where ChiTieuChuLuc_FK = '" + this.id + "' ";
				rs = db.get(query);
				if(rs != null)
				{
					this.nhomChon = new Hashtable<String, String>();
					this.trongso = new Hashtable<String, String>();
					while(rs.next())
					{
						this.nhomChon.put(rs.getString("NHOMSP_FK"),rs.getString("NhomTen") );
						this.trongso.put(rs.getString("NHOMSP_FK"),rs.getString("TrongSo") );
					}
					rs.close();
				}

				String table="";
				if(doituong.equals("SR"))
				{
					table ="DDKD";
				}
				else  if(doituong.equals("SS"))
				{
					table ="GSBH";
				}
				else  if(doituong.equals("ASM"))
				{
					table ="ASM";
				}
				else  if(doituong.equals("RSM"))
				{
					table ="BM";
				}

				query = "select ChiTieuChuLuc_FK,"+table+"_FK as nvId,ChiTieu,NHOMSP_FK " +
						" from ChiTieuChuLuc_"+table+" where ChiTieuChuLuc_FK = '" + this.id + "' and ChiTieu>0 ";;
						rs = db.get(query);
						if(rs != null)
						{
							this.tdv_chitieu = new Hashtable<String, String>();
							while(rs.next())
							{
								this.tdv_chitieu.put(rs.getString("nvId")+"__"+rs.getString("NHOMSP_FK"), formater.format( rs.getDouble("ChiTieu") ));
							}
							rs.close();
						}
			} catch (Exception e)
			{
				e.printStackTrace();
				System.out.println("115.Error Meg: " + e.getMessage());
			}
		}
		this.createRs();
	}

	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public String getScheme()
	{
		return this.scheme;
	}

	public void setScheme(String scheme)
	{
		this.scheme = scheme;
	}


	public String[] getTumuc()
	{

		return this.tumuc;
	}

	public void setTumuc(String[] tumuc)
	{

		this.tumuc = tumuc;
	}

	public String[] getDenmuc()
	{

		return this.denmuc;
	}

	public void setDenmuc(String[] denmuc)
	{

		this.denmuc = denmuc;
	}

	public void createRs()
	{
		String query = "select PK_SEQ, MA, TEN, TRANGTHAI, NHANHANG_FK, CHUNGLOAI_FK  from SanPham where trangthai = '1' ";

		if (this.id.trim().length() > 0)
		{
			query += " union select PK_SEQ, MA, TEN, TRANGTHAI, NHANHANG_FK, CHUNGLOAI_FK from SanPham where pk_seq in ( select sanpham_fk from ChiTieuChuLuc_SANPHAM where ChiTieuChuLuc_FK = '" + this.id + "' ) ";
		}

		query += " order by TRANGTHAI desc, NHANHANG_FK asc, CHUNGLOAI_FK asc ";
		this.sanphamRs = db.getScrol(query);

		this.vungRs = db.get("select pk_seq, ten from VUNG where trangthai = '1'");

		query = "select pk_seq, ten from KHUVUC where trangthai = '1'";
		if (this.vungIds.trim().length() > 0)
			query += " and vung_fk in ( " + this.vungIds + " ) ";
		this.kvRs = db.get(query);

		
		String table="";
		if(doituong.equals("SR"))
		{
			table ="DaiDienKinhDoanh";
		}
		else  if(doituong.equals("SS"))
		{
			table ="GiamSatBanHang";
		}
		else  if(doituong.equals("ASM"))
		{
			table ="ASM";
		}
		else  if(doituong.equals("RSM"))
		{
			table ="BM";
		}
		query="select pk_seq as nvId,ten as nvTen,SMARTID as nvMa from "+table+" where trangthai=1  order by ten";
		this.tdvRs=this.db.get(query);

		query="select TLNTC_FK,TenKy ,Thang,Nam,TuNgay,DenNgay from Ky";
		this.kyRs=this.db.get(query);

		query="select TLNTC_FK,Ten,Quy,Nam,TuNgay,DenNgay from Quy";
		this.quyRs=this.db.get(query);

		query= " select PK_SEQ,TEN from NHOMSANPHAM where loainhom=3 and TYPE=0 ";
		this.nhomRs=this.db.get(query);

	}

	public void setSanphamRs(ResultSet spRs)
	{

		this.sanphamRs = spRs;
	}

	public ResultSet getSanphamRs()
	{

		return this.sanphamRs;
	}

	public String getSpIds()
	{

		return this.spIds;
	}

	public void setSpIds(String spIds)
	{

		this.spIds = spIds;
	}

	public void setNppRs(ResultSet nppRs)
	{

		this.nppRs = nppRs;
	}

	public ResultSet getNppRs()
	{

		return this.nppRs;
	}

	public String getNppIds()
	{

		return this.nppIds;
	}

	public void setNppIds(String nppIds)
	{

		this.nppIds = nppIds;
	}
	public void setVungRs(ResultSet vungRs)
	{

		this.vungRs = vungRs;
	}

	public ResultSet getVungRs()
	{

		return this.vungRs;
	}

	public String getVungIds()
	{

		return this.vungIds;
	}

	public void setVungIds(String vungIds)
	{

		this.vungIds = vungIds;
	}

	public void setKhuvucRs(ResultSet kvRs)
	{

		this.kvRs = kvRs;
	}

	public ResultSet getKhuvucRs()
	{

		return this.kvRs;
	}

	public String getKhuvucIds()
	{

		return this.kvIds;
	}

	public void setKhuvucIds(String kvIds)
	{

		this.kvIds = kvIds;
	}

	String doituong,quy,apdung;

	public String getQuy()
	{
		return quy;
	}

	public void setQuy(String quy)
	{
		this.quy = quy;
	}

	public String getApdung()
	{
		return apdung;
	}

	public void setApdung(String apdung)
	{
		this.apdung = apdung;
	}

	public String getDoituong()
	{
		return doituong;
	}

	public void setDoituong(String doituong)
	{
		this.doituong = doituong;
	}
	String[] donvi_tinh_ds,donvi_tinh_thuong,thuong;

	public String[] getThuong()
	{
		return thuong;
	}

	public void setThuong(String[] thuong)
	{
		this.thuong = thuong;
	}

	public String[] getDonvi_tinh_ds()
	{
		return donvi_tinh_ds;
	}

	public void setDonvi_tinh_ds(String[] donvi_tinh_ds)
	{
		this.donvi_tinh_ds = donvi_tinh_ds;
	}

	public String[] getDonvi_tinh_thuong()
	{
		return donvi_tinh_thuong;
	}

	public void setDonvi_tinh_thuong(String[] donvi_tinh_thuong)
	{
		this.donvi_tinh_thuong = donvi_tinh_thuong;
	} 

	Hashtable<String, String> npp_chitieu= new Hashtable<String, String>();
	Hashtable<String, String> npp_donvi_chitieu= new Hashtable<String, String>();
	public Hashtable<String, String> getNpp_donvi_chitieu()
	{
		return npp_donvi_chitieu;
	}

	public void setNpp_donvi_chitieu(Hashtable<String, String> npp_donvi_chitieu)
	{
		this.npp_donvi_chitieu = npp_donvi_chitieu;
	}

	public Hashtable<String, String> getNpp_chitieu()
	{
		return npp_chitieu;
	}

	public void setNpp_chitieu(Hashtable<String, String> npp_chitieu)
	{
		this.npp_chitieu = npp_chitieu;
	}

	String loaichitieu;

	public String getLoaichitieu()
	{
		return loaichitieu;
	}

	public void setLoaichitieu(String loaichitieu)
	{
		this.loaichitieu = loaichitieu;
	}

	String view;

	public String getView()
	{
		return view;
	}

	public void setView(String view)
	{
		this.view = view;
	}

	String tructhuocId;
	@Override
	public String getTructhuocId()
	{
		return tructhuocId;
	}

	@Override
	public void setTructhuocId(String tructhuocId)
	{
		this.tructhuocId=tructhuocId;
	}

	String tdvIds;
	ResultSet tdvRs;

	public String getTdvIds()
	{
		return tdvIds;
	}

	public void setTdvIds(String tdvIds)
	{
		this.tdvIds = tdvIds;
	}

	public ResultSet getTdvRs()
	{
		return tdvRs;
	}

	public void setTdvRs(ResultSet tdvRs)
	{
		this.tdvRs = tdvRs;
	}

	Hashtable<String, String> tdv_chitieu= new Hashtable<String, String>();
	public Hashtable<String, String> getTdv_chitieu()
	{
		return tdv_chitieu;
	}

	public void setTdv_chitieu(Hashtable<String, String> tdv_chitieu)
	{
		this.tdv_chitieu = tdv_chitieu;
	}

	Hashtable<String, String> tdv_donvi_chitieu= new Hashtable<String, String>();

	public Hashtable<String, String> getTdv_donvi_chitieu()
	{
		return tdv_donvi_chitieu;
	}

	public void setTdv_donvi_chitieu(Hashtable<String, String> tdv_donvi_chitieu)
	{
		this.tdv_donvi_chitieu = tdv_donvi_chitieu;
	}




	ResultSet AsmRs,RsmRs,SsRs;

	public ResultSet getAsmRs()
	{
		return AsmRs;
	}

	public void setAsmRs(ResultSet asmRs)
	{
		AsmRs = asmRs;
	}

	public ResultSet getRsmRs()
	{
		return RsmRs;
	}

	public void setRsmRs(ResultSet rsmRs)
	{
		RsmRs = rsmRs;
	}

	public ResultSet getSsRs()
	{
		return SsRs;
	}

	public void setSsRs(ResultSet ssRs)
	{
		SsRs = ssRs;
	}

	Hashtable<String, String> Rsm_chitieu= new Hashtable<String, String>();
	public Hashtable<String, String> getRsm_chitieu()
	{
		return Rsm_chitieu;
	}

	public void setRsm_chitieu(Hashtable<String, String> Rsm_chitieu)
	{
		this.Rsm_chitieu = Rsm_chitieu;
	}

	Hashtable<String, String> asm_chitieu= new Hashtable<String, String>();
	public Hashtable<String, String> getAsm_chitieu()
	{
		return asm_chitieu;
	}
	public void setAsm_chitieu(Hashtable<String, String> asm_chitieu)
	{
		this.asm_chitieu = asm_chitieu;
	}

	Hashtable<String, String> ss_chitieu= new Hashtable<String, String>();
	public Hashtable<String, String> getSs_chitieu()
	{
		return ss_chitieu;
	}

	public void setSs_chitieu(Hashtable<String, String> ss_chitieu)
	{
		this.ss_chitieu = ss_chitieu;
	}

	String rsmIds,asmIds,ssIds;

	public String getRsmIds()
	{
		return rsmIds;
	}

	public void setRsmIds(String rsmIds)
	{
		this.rsmIds = rsmIds;
	}

	public String getAsmIds()
	{
		return asmIds;
	}

	public void setAsmIds(String asmIds)
	{
		this.asmIds = asmIds;
	}

	public String getSsIds()
	{
		return ssIds;
	}

	public void setSsIds(String ssIds)
	{
		this.ssIds = ssIds;
	}


	ResultSet kyRs,quyRs;

	public ResultSet getKyRs()
	{
		return kyRs;
	}

	public void setKyRs(ResultSet kyRs)
	{
		this.kyRs = kyRs;
	}

	public ResultSet getQuyRs()
	{
		return quyRs;
	}

	public void setQuyRs(ResultSet quyRs)
	{
		this.quyRs = quyRs;
	}

	public String getSql()
	{
		return sql;
	}

	public void setSql(String sql)
	{
		this.sql = sql;
	}

	public String getSqlNhom()
	{
		return sqlNhom;
	}

	public void setSqlNhom(String sqlNhom)
	{
		this.sqlNhom = sqlNhom;
	}

	String sql,sqlNhom;

	ResultSet nhomRs;
	String[] nhomId,nhomTen;

	public ResultSet getNhomRs()
	{
		return nhomRs;
	}

	public void setNhomRs(ResultSet nhomRs)
	{
		this.nhomRs = nhomRs;
	}

	public String[] getNhomId()
	{
		return nhomId;
	}

	public void setNhomId(String[] nhomId)
	{
		this.nhomId = nhomId;
	}

	public String[] getNhomTen()
	{
		return nhomTen;
	}

	public void setNhomTen(String[] nhomTen)
	{
		this.nhomTen = nhomTen;
	}


	Hashtable<String, String> trongso= new Hashtable<String, String>();
	@Override
	public Hashtable<String, String> getNhomTrongso()
	{
		return trongso;
	}

	@Override
	public void setNhomTrongso(Hashtable<String, String> trongso)
	{
		this.trongso=trongso;
	}

	Hashtable<String, String> nhomChon= new Hashtable<String, String>();
	@Override
	public Hashtable<String, String> getNhomchon()
	{
		return this.nhomChon;
	}

	@Override
	public void setNhomchon(Hashtable<String, String> trongso)
	{
		this.nhomChon= trongso;

	}

	String thuongSR;

	@Override
	public String getThuongSR()
	{
		return this.thuongSR;
	}

	@Override
	public void setThuongSR(String thuongSR)
	{
		this.thuongSR  =thuongSR;
	}

	String thuongSS;
	@Override
	public String getThuongSS()
	{
		return this.thuongSS;
	}

	@Override
	public void setThuongSS(String thuongSS)
	{
		this.thuongSS =thuongSS;
	}

	String thuongASM;
	@Override
	public String getThuongASM()
	{
		return this.thuongASM;
	}

	@Override
	public void setThuongASM(String thuongASM)
	{
		this.thuongASM =thuongASM;
	}

	String thuongRSM;
	@Override
	public String getThuongRSM()
	{
		return this.thuongRSM;
	}

	@Override
	public void setThuongRSM(String thuongRSM)
	{
		this.thuongRSM =thuongRSM;
	}


	String sonhomdat;

	public String getSonhomdat()
	{
		return sonhomdat;
	}

	public void setSonhomdat(String sonhomdat)
	{
		this.sonhomdat = sonhomdat;
	}



}
