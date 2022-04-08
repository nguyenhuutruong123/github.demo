package geso.dms.center.beans.tieuchithuong.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import geso.dms.center.beans.tieuchithuong.ITieuchithuongDP;
import geso.dms.center.db.sql.dbutils;

public class TieuchithuongDP implements ITieuchithuongDP 
{
	String userId;
	String id;
	String scheme;
	String thang;
	String nam;
	String diengiai;
	
	ResultSet nhomspRs;
	
	String dkId;
	
	String[] diengiaiMuc;
	String[] tumuc;
	String[] denmuc;
	String[] doanhso;
	String[] thuongSR;
	String[] thuongTDSR;
	String[] thuongSS;
	String[] thuongTDSS;
	String[] thuongASM;
	String[] thuongTDASM;
		
	
	String diengiaiNhom;
	String soluongNhom;
	String sotienNhom;
	String isThung;
	String[] spMa;
	String[] spTen;
	
	String msg;

	dbutils db;
	
	public TieuchithuongDP()
	{
		this.id = "";
		this.thang = "";
		this.nam = "";
		this.diengiai = "";
		this.msg = "";
		this.scheme = "";
		this.dkId = "";
		
		this.diengiaiNhom = "";
		this.soluongNhom = "";
		this.sotienNhom = "";
		this.isThung = "0";
		this.db = new dbutils();
	}
	
	public TieuchithuongDP(String id)
	{
		this.id = id;
		this.thang = "";
		this.nam = "";
		this.diengiai = "";
		this.msg = "";
		this.scheme = "";
		this.dkId = "";
		
		this.diengiaiNhom = "";
		this.soluongNhom = "";
		this.sotienNhom = "";
		this.isThung = "0";
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
			String query = "select count(*) as sodong from TIEUCHITHUONGDP where scheme = N'" + this.scheme + "'";
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
			
			if(this.dkId.trim().length() <= 0)
			{
				this.msg = "Vui lòng tạo nhóm điều kiện";
				return false;
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
					if(this.diengiaiMuc[i].trim().length() > 0 && this.tumuc[i].trim().length() > 0 && this.denmuc[i].trim().length() > 0 && this.doanhso[i].trim().length() > 0 && this.thuongSR[i].trim().length() > 0)
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
			
			db.getConnection().setAutoCommit(false);
			
			query = "insert TIEUCHITHUONGDP(scheme, thang, nam, diengiai, trangthai, ngaytao, nguoitao, ngaysua, nguoisua, NHOMDIEUKIEN_FK) " +
					"values(N'" + this.scheme + "', '" + this.thang + "', '" + this.nam + "', N'" + this.diengiai + "', '0', " +
							"'" + this.getDateTime() + "', '" + this.userId + "', '" + this.getDateTime() + "', '" + this.userId + "', '" + this.dkId + "')";
			
			System.out.println("1.Insert: " + query);
			if(!db.update(query))
			{
				this.msg = "Khong the tao moi TIEUCHITHUONGDP: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			if(this.diengiaiMuc != null)
			{
				String sql = "";
				for(int i = 0; i < this.diengiaiMuc.length; i++)
				{
					//System.out.println("___THUONG SR: " + this.thuongSR[i] + " -- Thuong SS: " + this.thuongSS[i]);
					if(this.diengiaiMuc[i].trim().length() > 0 && this.tumuc[i].trim().length() > 0 && this.denmuc[i].trim().length() > 0 && this.doanhso[i].trim().length() > 0 && this.thuongSR[i].trim().length() > 0)
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
						
						sql += "select N'" + this.diengiaiMuc[i].replaceAll(",", "") + "' as diengiaiMuc, '" + this.tumuc[i].replaceAll(",", "") + "' as tumuc, '" + this.denmuc[i].replaceAll(",", "") + "' as denmuc, '" + this.doanhso[i].replaceAll(",", "") + "' as doanhso, " +
								"'" + this.thuongSR[i].replaceAll(",", "") + "' as thuongSR, '" + thuongTD_SR + "' as thuongTDSR, '" + thuong_SS + "' as thuongSS, '" + thuongTD_SS + "' as thuongTDSS, '" + thuong_ASM + "' as ASM, '" + thuongTD_ASM + "' as thuongTDASM union ";
						
					}
				}
				
				if(sql.trim().length() > 0)
				{
					sql = sql.substring(0, sql.length() - 6);
					
					query = "insert TIEUCHITHUONGDP_TIEUCHI(thuongdp_fk, tieuchi, tumuc, denmuc, doanhso, thuongSR, thuongTDSR, thuongSS, thuongTDSS, thuongASM, thuongTDASM) " +
							"select IDENT_CURRENT('TIEUCHITHUONGDP') as tctId, tieuchi.* from (" + sql + ") tieuchi ";
					
					System.out.println("2.Insert: " + query);
					if(!db.update(query))
					{
						this.msg = "Khong the tao moi TIEUCHITHUONGDP_TIEUCHI: " + query;
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
			String query = "select count(*) as sodong from TIEUCHITHUONGDP where scheme = N'" + this.scheme + "' and pk_seq != '" + this.id + "'";
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
			
			if(this.dkId.trim().length() <= 0)
			{
				this.msg = "Vui lòng tạo nhóm điều kiện";
				return false;
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
					if(this.diengiaiMuc[i].trim().length() > 0 && this.tumuc[i].trim().length() > 0 && this.denmuc[i].trim().length() > 0 && this.doanhso[i].trim().length() > 0 && this.thuongSR[i].trim().length() > 0)
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
			
			db.getConnection().setAutoCommit(false);
			
			query = "update TIEUCHITHUONGDP set scheme = N'" + this.scheme + "', thang = '" + this.thang + "', nam = '" + this.nam + "', diengiai = N'" + this.diengiai + "', " +
					"ngaysua = '" + this.getDateTime() + "', nguoisua = '" + this.userId + "', NHOMDIEUKIEN_FK = '" + this.dkId + "' where pk_seq = '" + this.id + "'";
					
			System.out.println("1.Update: " + query);
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat TIEUCHITHUONGDP: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete TIEUCHITHUONGDP_TIEUCHI where thuongdp_fk = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat TIEUCHITHUONGDP_TIEUCHI: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			if(this.diengiaiMuc != null)
			{
				String sql = "";
				for(int i = 0; i < this.diengiaiMuc.length; i++)
				{
					if(this.diengiaiMuc[i].trim().length() > 0 && this.tumuc[i].trim().length() > 0 && this.denmuc[i].trim().length() > 0 && this.doanhso[i].trim().length() > 0 && this.thuongSR[i].trim().length() > 0)
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
						
						sql += "select N'" + this.diengiaiMuc[i].replaceAll(",", "") + "' as diengiaiMuc, '" + this.tumuc[i].replaceAll(",", "") + "' as tumuc, '" + this.denmuc[i].replaceAll(",", "") + "' as denmuc, '" + this.doanhso[i].replaceAll(",", "") + "' as doanhso, " +
								"'" + this.thuongSR[i].replaceAll(",", "") + "' as thuongSR, '" + thuongTD_SR + "' as thuongTDSR, '" + thuong_SS + "' as thuongSS, '" + thuongTD_SS + "' as thuongTDSS, '" + thuong_ASM + "' as ASM, '" + thuongTD_ASM + "' as thuongTDASM union ";
						
					}
				}
				
				if(sql.trim().length() > 0)
				{
					sql = sql.substring(0, sql.length() - 6);
					
					query = "insert TIEUCHITHUONGDP_TIEUCHI(thuongdp_fk, tieuchi, tumuc, denmuc, doanhso, thuongSR, thuongTDSR, thuongSS, thuongTDSS, thuongASM, thuongTDASM) " +
							"select '" + this.id + "' as tctId, tieuchi.* from (" + sql + ") tieuchi ";
					
					System.out.println("2.Insert: " + query);
					if(!db.update(query))
					{
						this.msg = "Khong the tao moi TIEUCHITHUONGDP_TIEUCHI: " + query;
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
		String query = "select scheme, thang, nam, diengiai, nhomdieukien_fk  " +
					   "from tieuchithuongDP where pk_seq = '" + this.id + "'";
		
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
					this.dkId = rs.getString("nhomdieukien_fk");
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
		String query = "select tieuchi, tumuc, denmuc, doanhso, thuongSR, thuongTDSR, thuongSS, thuongTDSS, thuongASM, thuongTDASM " +
					   "from TIEUCHITHUONGDP_TIEUCHI " +
					   "where thuongdp_fk = '" + this.id + "'";
		
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			NumberFormat format = new DecimalFormat("##,###,###");
			try 
			{
				String tieu_chi = "";
				String tu_muc = "";
				String den_muc = "";
				String doanh_so = "";
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
					doanh_so += format.format(rs.getDouble("doanhso")) + ",,";
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
					
					doanh_so = doanh_so.substring(0, doanh_so.length() - 2);
					this.doanhso = doanh_so.split(",,");
					
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
	
	public String getIsthung()
	{
		return this.isThung;
	}

	public void setIsthung(String isthung) 
	{
		this.isThung = isthung;
	}
	
	public void setNhomspRs(ResultSet nspRs) 
	{
		this.nhomspRs = nspRs;
	}

	public ResultSet getNhomspRs()
	{
		return this.nhomspRs;
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
		
		String query = "select pk_seq as nspId, diengiai as nspTen from nhomsanpham where nguoitao is not null and trangthai='1'";
		this.nhomspRs = db.getScrol(query);	
		
		if(this.dkId.trim().length() > 0)
		{
			query = "select diengiai, soluong, sotien, isthung from DieuKienDoPhu where pk_seq = '" + this.dkId + "'";
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				try 
				{
					if(rs.next())
					{
						this.diengiaiNhom = rs.getString("diengiai");
						this.soluongNhom = rs.getDouble("soluong") > 0 ? rs.getString("soluong") : "";
						this.sotienNhom = rs.getDouble("sotien") > 0 ? rs.getString("sotien") : "";
						this.isThung = rs.getString("isthung");
						
						//Init SANPHAM
						query = "select b.MA, b.TEN from DieuKienDoPhu_SanPham a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ where dieukien_fk = '" + this.dkId + "'";
						ResultSet rsSP = db.get(query);
						if(rsSP != null)
						{
							String sanphamMa = "";
							String sanphamTen = "";
							
							while(rsSP.next())
							{
								sanphamMa += rsSP.getString("ma") + ",,";
								sanphamTen += rsSP.getString("ten") + ",,";
							}
							rsSP.close();
							
							if(sanphamMa.trim().length() > 0 )
							{
								sanphamMa = sanphamMa.substring(0, sanphamMa.length() - 2);
								sanphamTen = sanphamTen.substring(0, sanphamTen.length() - 2);
								
								this.spMa = sanphamMa.split(",,");
								this.spTen = sanphamTen.split(",,");
							}
						}
						
					}
					rs.close();
				} 
				catch (Exception e)
				{
					System.out.println("__EXCEPTION: " + e.getMessage());
				}
			}
		}
	}

	public String[] getDoanhso() {
		
		return this.doanhso;
	}

	public void setDoanhso(String[] doanhso) {
		
		this.doanhso = doanhso;
	}

	public String getNhomdkId() {
		
		return this.dkId;
	}

	public void setNhomdkId(String nhomdkId) {
		
		this.dkId = nhomdkId;
	}

	
	public String getDiengiaiNhom() {
		
		return this.diengiaiNhom;
	}

	
	public void setDiengiaiNhom(String diengiai) {
		
		this.diengiaiNhom = diengiai;
	}

	
	public String getSoluongNhom() {
		
		return this.soluongNhom;
	}

	
	public void setSoluongNhom(String soluongNhom) {
		
		this.soluongNhom = soluongNhom;
	}

	
	public String getSotienNhom() {
		
		return this.sotienNhom;
	}

	
	public void setSotienNhom(String sotienNhom) {
		
		this.sotienNhom = sotienNhom;
	}

	
	public String getIsThung() {
		
		return this.isThung;
	}

	
	public void setIsThung(String isThung) {
		
		this.isThung = isThung;
	}

	
	public String[] getSpMa() {
		
		return this.spMa;
	}

	
	public void setSpMa(String[] spMa) {
		
		this.spMa = spMa;
	}

	
	public String[] getSpTen() {
		
		return this.spTen;
	}

	
	public void setSpTen(String[] spTen) {
		
		this.spTen = spTen;
	}

}
