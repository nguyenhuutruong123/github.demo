package geso.dms.center.beans.tieuchithuong.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import geso.dms.center.beans.dieukienkhuyenmai.ISanpham;
import geso.dms.center.beans.dieukienkhuyenmai.imp.Sanpham;
import geso.dms.center.beans.tieuchithuong.INhomsp;
import geso.dms.center.beans.tieuchithuong.INhomspDetail;
import geso.dms.center.beans.tieuchithuong.ITieuchithuongTB;
import geso.dms.center.db.sql.dbutils;

public class TieuchithuongTB implements ITieuchithuongTB 
{
	String userId;
	String id;
	String scheme;
	String thang;
	String nam;
	String diengiai;
	
	String[] diengiaiMuc;
	String[] tumuc;
	String[] denmuc;
	String[] thuongSR;
	String[] thuongTDSR;
	String[] thuongSS;
	String[] thuongTDSS;
	String[] thuongASM;
	String[] thuongTDASM;
		
	
	String[] schemeTB;
	String[] SchemeDienGiai;
	
	String msg;

	dbutils db;
	
	public TieuchithuongTB()
	{
		this.id = "";
		this.thang = "";
		this.nam = "";
		this.diengiai = "";
		this.msg = "";
		this.scheme = "";
	
		this.db = new dbutils();
	}
	
	public TieuchithuongTB(String id)
	{
		this.id = id;
		this.thang = "";
		this.nam = "";
		this.diengiai = "";
		this.msg = "";
		this.scheme = "";
	
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

	
	public boolean createTctSKU( ) 
	{
		try
		{
			if(this.thang.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn tháng";
				return false;
			}
			
			if(this.nam.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn năm";
				return false;
			}
			
			if(this.scheme.trim().length() <= 0)
			{
				this.msg = "Vui lòng scheme";
				return false;
			}
			
			//Check Scheme
			String query = "select count(*) as sodong from TIEUCHITHUONGTB where scheme = N'" + this.scheme + "'";
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				int count = 0;
				if(rs.next())
				{
					count = rs.getInt("sodong");
					if(count > 0)
					{
						this.msg = "Scheme: " + this.scheme + " đã tồn tại, vui lòng nhập scheme khác";
						rs.close();
						return false;
					}
				}
				rs.close();
			}
			
			//Check tieu chi
			if(this.diengiaiMuc == null)
			{
				this.msg = "Vui lòng nhập tiêu chí ";
				return false;
			}
			else
			{
				boolean flag = false;
				for(int i = 0; i < this.diengiaiMuc.length; i++)
				{
					if(this.diengiaiMuc[i].trim().length() > 0 && this.tumuc[i].trim().length() > 0 && this.denmuc[i].trim().length() > 0 && this.thuongSR[i].trim().length() > 0)
					{
						flag = true;
					}
				}
				
				if(!flag)
				{
					this.msg = "Vui lòng kiểm tra lại các tiêu chí";
					return false;
				}
			}
			
			
			if(this.schemeTB == null)
			{
				this.msg = "Vui lòng nhập chương trình trưng bày áp dụng ";
				return false;
			}
			else
			{
				boolean flag = false;
				for(int i = 0; i < this.schemeTB.length; i++)
				{
					if(this.schemeTB[i].trim().length() > 0 )
					{
						flag = true;
					}
				}
				
				if(!flag)
				{
					this.msg = "Vui lòng kiểm tra lại các chương trình trưng bày áp dụng.";
					return false;
				}
			}
			
			db.getConnection().setAutoCommit(false);
			
			query = "insert TieuchithuongTB(scheme, thang, nam, diengiai, trangthai, ngaytao, nguoitao, ngaysua, nguoisua) " +
					"values(N'" + this.scheme + "', '" + this.thang + "', '" + this.nam + "', N'" + this.diengiai + "', '0', " +
							"'" + this.getDateTime() + "', '" + this.userId + "', '" + this.getDateTime() + "', '" + this.userId + "')";
			
			System.out.println("1.Insert: " + query);
			if(!db.update(query))
			{
				this.msg = "Khong the tao moi TieuchithuongTB: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			if(this.schemeTB != null)
			{
				String sql = "";
				for(int i = 0; i < this.schemeTB.length; i++)
				{
					sql += " select pk_seq as cttb_fk from CTTRUNGBAY where scheme = '" + this.schemeTB[i] + "' union ";
				}
				
				if(sql.trim().length() > 0)
				{
					sql = sql.substring(0, sql.length() - 6);
					
					query = "insert TieuchithuongTB_CTTB(thuongtb_fk, cttb_fk) " +
							"select IDENT_CURRENT('TieuchithuongTB') as tctId, tieuchi.* from (" + sql + ") tieuchi ";
					
					System.out.println("1.Insert TieuchithuongTB_CTTB: " + query);
					if(!db.update(query))
					{
						this.msg = "Khong the tao moi TieuchithuongTB_CTTB: " + query;
						db.getConnection().rollback();
						return false;
					}
				}
			}
			
			if(this.diengiaiMuc != null)
			{
				String sql = "";
				for(int i = 0; i < this.diengiaiMuc.length; i++)
				{
					//System.out.println("___THUONG SR: " + this.thuongSR[i] + " -- Thuong SS: " + this.thuongSS[i]);
					if(this.diengiaiMuc[i].trim().length() > 0 && this.tumuc[i].trim().length() > 0 && this.denmuc[i].trim().length() > 0 && this.thuongSR[i].trim().length() > 0)
					{
						String thuongTD_SR = "0";
						if(this.thuongTDSR[i].trim().length() > 0)
							thuongTD_SR = this.thuongTDSR[i].trim().replaceAll(",", "");
						
						String thuong_SS = "0";
						if(this.thuongSS[i].trim().length() > 0)
							thuong_SS = this.thuongSS[i].trim().replaceAll(",", "");
						
						String thuongTD_SS = "0";
						if(this.thuongTDSS[i].trim().length() > 0)
							thuongTD_SS = this.thuongTDSS[i].trim().replaceAll(",", "");
						
						String thuong_ASM = "0";
						if(this.thuongASM[i].trim().length() > 0)
							thuong_ASM = this.thuongASM[i].trim().replaceAll(",", "");
						
						String thuongTD_ASM = "0";
						if(this.thuongTDASM[i].trim().length() > 0)
							thuongTD_ASM = this.thuongTDASM[i].trim().replaceAll(",", "");
						
						sql += "select N'" + this.diengiaiMuc[i].replaceAll(",", "") + "' as diengiaiMuc, '" + this.tumuc[i].replaceAll(",", "") + "' as tumuc, '" + this.denmuc[i].replaceAll(",", "") + "' as denmuc,  " +
								"'" + this.thuongSR[i].replaceAll(",", "") + "' as thuongSR, '" + thuongTD_SR + "' as thuongTDSR, '" + thuong_SS + "' as thuongSS, '" + thuongTD_SS + "' as thuongTDSS, '" + thuong_ASM + "' as ASM, '" + thuongTD_ASM + "' as thuongTDASM union ";
						
					}
				}
				
				if(sql.trim().length() > 0)
				{
					sql = sql.substring(0, sql.length() - 6);
					
					query = "insert TieuchithuongTB_TIEUCHI(thuongtb_fk, tieuchi, tumuc, denmuc, thuongSR, thuongTDSR, thuongSS, thuongTDSS, thuongASM, thuongTDASM) " +
							"select IDENT_CURRENT('TieuchithuongTB') as tctId, tieuchi.* from (" + sql + ") tieuchi ";
					
					System.out.println("2.Insert: " + query);
					if(!db.update(query))
					{
						this.msg = "Khong the tao moi TieuchithuongTB_TIEUCHI: " + query;
						db.getConnection().rollback();
						return false;
					}
				}
				
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (SQLException e)
		{
			try 
			{
				db.getConnection().rollback();
				System.out.println("__EXCEPTION UPDATE: " + e.getMessage());
			} 
			catch (SQLException e1) {}
		}
		
		return true;
	}

	public boolean updateTctSKU()
	{
		try
		{
			if(this.thang.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn tháng";
				return false;
			}
			
			if(this.nam.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn năm";
				return false;
			}
			
			if(this.scheme.trim().length() <= 0)
			{
				this.msg = "Vui lòng scheme";
				return false;
			}
			
			
			//Check Scheme
			String query = "select count(*) as sodong from TIEUCHITHUONGTB where scheme = N'" + this.scheme + "' and pk_seq != '" + this.id + "'";
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				int count = 0;
				if(rs.next())
				{
					count = rs.getInt("sodong");
					if(count > 0)
					{
						this.msg = "Scheme: " + this.scheme + " đã tồn tại, vui lòng nhập scheme khác";
						rs.close();
						return false;
					}
				}
				rs.close();
			}
			
			//Check tieu chi
			if(this.diengiaiMuc == null)
			{
				this.msg = "Vui lòng nhập tiêu chí ";
				return false;
			}
			else
			{
				boolean flag = false;
				for(int i = 0; i < this.diengiaiMuc.length; i++)
				{
					if(this.diengiaiMuc[i].trim().length() > 0 && this.tumuc[i].trim().length() > 0 && this.denmuc[i].trim().length() > 0 && this.thuongSR[i].trim().length() > 0)
					{
						flag = true;
					}
				}
				
				if(!flag)
				{
					this.msg = "Vui lòng kiểm tra lại các tiêu chí";
					return false;
				}
			}
			
			
			if(this.schemeTB == null)
			{
				this.msg = "Vui lòng nhập chương trình trưng bày áp dụng ";
				return false;
			}
			else
			{
				boolean flag = false;
				for(int i = 0; i < this.schemeTB.length; i++)
				{
					if(this.schemeTB[i].trim().length() > 0 )
					{
						flag = true;
					}
				}
				
				if(!flag)
				{
					this.msg = "Vui lòng kiểm tra lại các chương trình trưng bày áp dụng.";
					return false;
				}
			}
			
			db.getConnection().setAutoCommit(false);
			
			query = "update TieuchithuongTB set scheme = N'" + this.scheme + "', thang = '" + this.thang + "', nam = '" + this.nam + "', diengiai = N'" + this.diengiai + "', " +
					"ngaysua = '" + this.getDateTime() + "', nguoisua = '" + this.userId + "' where pk_seq = '" + this.id + "'";
					
			System.out.println("1.Update: " + query);
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat TieuchithuongTB: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete TieuchithuongTB_CTTB where thuongtb_fk = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat TieuchithuongTB_CTTB: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete TieuchithuongTB_TIEUCHI where thuongtb_fk = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat TieuchithuongTB_TIEUCHI: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			if(this.schemeTB != null)
			{
				String sql = "";
				for(int i = 0; i < this.schemeTB.length; i++)
				{
					sql += " select pk_seq as cttb_fk from CTTRUNGBAY where scheme = '" + this.schemeTB[i] + "' union ";
				}
				
				if(sql.trim().length() > 0)
				{
					sql = sql.substring(0, sql.length() - 6);
					
					query = "insert TieuchithuongTB_CTTB(thuongtb_fk, cttb_fk) " +
							"select IDENT_CURRENT('TieuchithuongTB') as tctId, tieuchi.* from (" + sql + ") tieuchi ";
					
					System.out.println("1.Insert TieuchithuongTB_CTTB: " + query);
					if(!db.update(query))
					{
						this.msg = "Khong the tao moi TieuchithuongTB_CTTB: " + query;
						db.getConnection().rollback();
						return false;
					}
				}
			}
			
			if(this.diengiaiMuc != null)
			{
				String sql = "";
				for(int i = 0; i < this.diengiaiMuc.length; i++)
				{
					if(this.diengiaiMuc[i].trim().length() > 0 && this.tumuc[i].trim().length() > 0 && this.denmuc[i].trim().length() > 0 && this.thuongSR[i].trim().length() > 0)
					{
						String thuongTD_SR = "0";
						if(this.thuongTDSR[i].trim().length() > 0)
							thuongTD_SR = this.thuongTDSR[i].trim().replaceAll(",", "");
						
						String thuong_SS = "0";
						if(this.thuongSS[i].trim().length() > 0)
							thuong_SS = this.thuongSS[i].trim().replaceAll(",", "");
						
						String thuongTD_SS = "0";
						if(this.thuongTDSS[i].trim().length() > 0)
							thuongTD_SS = this.thuongTDSS[i].trim().replaceAll(",", "");
						
						String thuong_ASM = "0";
						if(this.thuongASM[i].trim().length() > 0)
							thuong_ASM = this.thuongASM[i].trim().replaceAll(",", "");
						
						String thuongTD_ASM = "0";
						if(this.thuongTDASM[i].trim().length() > 0)
							thuongTD_ASM = this.thuongTDASM[i].trim().replaceAll(",", "");
						
						sql += "select N'" + this.diengiaiMuc[i].replaceAll(",", "") + "' as diengiaiMuc, '" + this.tumuc[i].replaceAll(",", "") + "' as tumuc, '" + this.denmuc[i].replaceAll(",", "") + "' as denmuc,  " +
								"'" + this.thuongSR[i].replaceAll(",", "") + "' as thuongSR, '" + thuongTD_SR + "' as thuongTDSR, '" + thuong_SS + "' as thuongSS, '" + thuongTD_SS + "' as thuongTDSS, '" + thuong_ASM + "' as ASM, '" + thuongTD_ASM + "' as thuongTDASM union ";
						
					}
				}
				
				if(sql.trim().length() > 0)
				{
					sql = sql.substring(0, sql.length() - 6);
					
					query = "insert TieuchithuongTB_TIEUCHI(thuongtb_fk, tieuchi, tumuc, denmuc, thuongSR, thuongTDSR, thuongSS, thuongTDSS, thuongASM, thuongTDASM) " +
							"select '" + this.id + "' as tctId, tieuchi.* from (" + sql + ") tieuchi ";
					
					System.out.println("2.Insert: " + query);
					if(!db.update(query))
					{
						this.msg = "Khong the tao moi TieuchithuongTB_TIEUCHI: " + query;
						db.getConnection().rollback();
						return false;
					}
				}
				
			}

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e)
		{
			try 
			{
				db.getConnection().rollback();
				System.out.println("__EXCEPTION UPDATE: " + e.getMessage());
			} catch (SQLException e1) {}
			
			System.out.println("115.Error: " + e.getMessage());
		}
		
		return true;
	}

	public void init() 
	{
		String query = "select scheme, thang, nam, diengiai  " +
					   "from TieuchithuongTB where pk_seq = '" + this.id + "'";
		
		System.out.println("__Khoi tao tieu chi thuong: " + query);
		
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				while(rs.next())
				{
					this.scheme = rs.getString("scheme");
					this.thang = rs.getString("thang");
					this.nam = rs.getString("nam");					
					this.diengiai= rs.getString("diengiai");
				}
				rs.close();
			} 
			catch (Exception e)
			{
				System.out.println("115.Error Meg: " + e.getMessage());
			}
		}
		
		this.createNdk();
		this.createRs();
	}
	
	private void createNdk() 
	{
		String query = "select tieuchi, tumuc, denmuc, thuongSR, thuongTDSR, thuongSS, thuongTDSS, thuongASM, thuongTDASM " +
					   "from TieuchithuongTB_TIEUCHI " +
					   "where thuongtb_fk = '" + this.id + "'";
		
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			NumberFormat format = new DecimalFormat("##,###,###");
			try 
			{
				String tieu_chi = "";
				String tu_muc = "";
				String den_muc = "";
				String thuong_SR = "";
				String thuong_TDSR = "";
				String thuong_SS = "";
				String thuong_TDSS = "";
				String thuong_ASM = "";
				String thuong_TDASM = "";
				
				while(rs.next())
				{
					tieu_chi += rs.getString("tieuchi") + ",,";
					tu_muc += format.format(rs.getDouble("tumuc")) + ",,";
					den_muc += format.format(rs.getDouble("denmuc")) + ",,";
					thuong_SR += format.format(rs.getDouble("thuongSR")) + ",,";
					thuong_TDSR += format.format(rs.getDouble("thuongTDSR")) + ",,";
					thuong_SS += format.format(rs.getDouble("thuongSS")) + ",,";
					thuong_TDSS += format.format(rs.getDouble("thuongTDSS")) + ",,";
					thuong_ASM += format.format(rs.getDouble("thuongASM")) + ",,";
					thuong_TDASM += format.format(rs.getDouble("thuongTDASM")) + ",,";
				}
				rs.close();
				
				if(tieu_chi.trim().length() > 0)
				{
					tieu_chi = tieu_chi.substring(0, tieu_chi.length() - 2);
					this.diengiaiMuc = tieu_chi.split(",,");
					
					tu_muc = tu_muc.substring(0, tu_muc.length() - 2);
					this.tumuc = tu_muc.split(",,");
					
					den_muc = den_muc.substring(0, den_muc.length() - 2);
					this.denmuc = den_muc.split(",,");
					
					thuong_SR = thuong_SR.substring(0, thuong_SR.length() - 2);
					this.thuongSR = thuong_SR.split(",,");
					
					thuong_TDSR = thuong_TDSR.substring(0, thuong_TDSR.length() - 2);
					this.thuongTDSR = thuong_TDSR.split(",,");
					
					thuong_SS = thuong_SS.substring(0, thuong_SS.length() - 2);  System.out.println("___THUONG SS: " + thuong_SS);
					this.thuongSS = thuong_SS.split(",,");
					
					thuong_TDSS = thuong_TDSS.substring(0, thuong_TDSS.length() - 2);
					this.thuongTDSS = thuong_TDSS.split(",,");
					
					thuong_ASM = thuong_ASM.substring(0, thuong_ASM.length() - 2);
					this.thuongASM = thuong_ASM.split(",,");
					
					thuong_TDASM = thuong_TDASM.substring(0, thuong_TDASM.length() - 2);
					this.thuongTDASM = thuong_TDASM.split(",,");
				}
			} 
			catch (Exception e) {
				
				System.out.println("Loi khoi tao: " + e.toString());
			}
		}
		
		query = "select b.scheme, b.diengiai from TieuchithuongTB_CTTB a inner join CTTrungBay b on a.cttb_fk = b.pk_seq where a.thuongtb_fk = '" + this.id + "'";
		ResultSet rsCTTB = db.get(query);
		if(rsCTTB != null)
		{
			try 
			{
				String cttb_fk = "";
				String cttb_dg = "";
				while(rsCTTB.next())
				{
					cttb_fk += rsCTTB.getString("scheme") + ",,";
					cttb_dg += rsCTTB.getString("diengiai") + ",,";
				}
				rsCTTB.close();
				
				if(cttb_fk.trim().length() > 0)
				{
					cttb_fk = cttb_fk.substring(0, cttb_fk.length() - 2);
					this.schemeTB = cttb_fk.split(",,");
					
					cttb_dg = cttb_dg.substring(0, cttb_dg.length() - 2);
					this.SchemeDienGiai = cttb_dg.split(",,");
				}
			} 
			catch (Exception e) { System.out.println("EXCEPTION SCHEME: " + e.getMessage()); }
		}
		
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
	
	public String[] getDiengiaiMuc() {
		
		return this.diengiaiMuc;
	}

	
	public void setDiengiaiMuc(String[] diengiai) {
		
		this.diengiaiMuc = diengiai;
	}

	
	public String[] getTumuc() {
		
		return this.tumuc;
	}

	
	public void setTumuc(String[] tumuc) {
		
		this.tumuc = tumuc;
	}

	
	public String[] getDenmuc() {
		
		return this.denmuc;
	}

	
	public void setDenmuc(String[] denmuc) {
		
		this.denmuc = denmuc;
	}

	
	public String[] getThuongSR() {
		
		return this.thuongSR;
	}

	
	public void setThuongSR(String[] thuongSR) {
		
		this.thuongSR = thuongSR;
	}

	
	public String[] getThuongTDSR() {
		
		return this.thuongTDSR;
	}

	
	public void setThuongTDSR(String[] thuongTDSR) {
		
		this.thuongTDSR = thuongTDSR;
	}

	
	public String[] getThuongSS() {
		
		return this.thuongSS;
	}

	
	public void setThuongSS(String[] thuongSS) {
		
		this.thuongSS = thuongSS;
	}

	
	public String[] getThuongTDSS() {
		
		return this.thuongTDSS;
	}

	
	public void setThuongTDSS(String[] thuongTDSS) {
		
		this.thuongTDSS = thuongTDSS;
	}

	
	public String[] getThuongASM() {
		
		return this.thuongASM;
	}

	
	public void setThuongASM(String[] thuongASM) {
		
		this.thuongASM = thuongASM;
	}

	
	public String[] getThuongTDASM() {
		
		return this.thuongTDASM;
	}

	
	public void setThuongTDASM(String[] thuongTDASM) {
		
		this.thuongTDASM = thuongTDASM;
	}

	public void createRs() {	
		
	}

	
	public String[] getSchemeTB() {
		
		return this.schemeTB;
	}

	
	public void setSchemeTB(String[] schemeTB) {
		
		this.schemeTB = schemeTB;
	}

	
	public String[] getSchemeDiengiai() {
		
		return this.SchemeDienGiai;
	}

	
	public void setSchemeDiengiai(String[] schemeDG) {
		
		this.SchemeDienGiai = schemeDG;
	}

	
}
