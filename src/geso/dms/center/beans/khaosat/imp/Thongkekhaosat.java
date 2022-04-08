package geso.dms.center.beans.khaosat.imp;

import geso.dms.center.beans.khaosat.IThongkekhaosat;
import geso.dms.center.db.sql.dbutils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

public class Thongkekhaosat implements IThongkekhaosat
{
	String userId;
	String id;
	
	String tieude;
	String diengiai;
	String bophan;
	String socauhoi;
	
	String trangthai;
	String msg;
	
	Hashtable<String, String> noidungcaihoi;
	Hashtable<String, String> noidungcautraloi;
	
	String tennguoitraloi;
	String sodienthoai;
	String diachi;
	String doituong;
	String muctieu;
	String thunhap;
	String gioitinh;
	String tuoi;
	
	dbutils db;
	
	public Thongkekhaosat()
	{
		this.userId = "";
		this.id = "";
		this.tieude = "";
		this.diengiai = "";
		this.bophan = "";
		this.socauhoi = "";

		this.trangthai = "1";
		this.msg = "";
		
		this.tennguoitraloi = "";
		this.sodienthoai = "";
		this.diachi = "";
		this.doituong = "";
		this.muctieu = "";
		this.thunhap = "";
		this.gioitinh = "";
		this.tuoi = "";
		
		this.noidungcaihoi = new Hashtable<String, String>();
		this.noidungcautraloi = new Hashtable<String, String>();
		this.db = new dbutils();
	}
	
	public Thongkekhaosat(String id)
	{
		this.userId = "";
		this.id = id;
		this.tieude = "";
		this.diengiai = "";
		this.bophan = "";
		this.socauhoi = "";

		this.trangthai = "1";
		this.msg = "";
		
		this.tennguoitraloi = "";
		this.sodienthoai = "";
		this.diachi = "";
		this.doituong = "";
		this.muctieu = "";
		this.thunhap = "";
		this.gioitinh = "";
		this.tuoi = "";
		
		this.noidungcaihoi = new Hashtable<String, String>();
		this.noidungcautraloi = new Hashtable<String, String>();
		this.db = new dbutils();
	}
	
	public String getId() 
	{
		return this.id;
	}

	public void setId(String Id) 
	{
		this.id = Id;
	}
	
	public String getUserId() 
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;	
	}

	public String getMsg() 
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}

	public void init() 
	{
		String query = "select tieude, diengiai, bophan, socauhoi, trangthai from KHAOSAT where PK_SEQ = '" + this.id + "'";
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				while(rs.next())
				{
					this.tieude = rs.getString("tieude");
					this.diengiai = rs.getString("diengiai");
					this.bophan = rs.getString("bophan");
					this.socauhoi = rs.getString("socauhoi");
					this.trangthai = rs.getString("trangthai");
				}
				rs.close();
				
				//Init thong tin khach hang
				query = "select distinct b.TEN as nguoithuchien, a.ten, a.sodienthoai, case when a.gioitinh = 0 then N'Nữ' else N'Nam' end as gioitinh,  " +
							"a.diachi, a.tuoi, a.doituong, a.muctieu, a.thunhap  " +
						"from KHAOSAT_CAUHOI_THUCHIEN a inner join NHANVIEN b on a.NHANVIEN_FK = b.PK_SEQ " +
						"where khaosat_cauhoi_fk in ( select pk_seq from KHAOSAT_CAUHOI where KHAOSAT_FK = '" + this.id + "' ) and sodienthoai = '" + this.sodienthoai + "' and a.ten = N'" + this.tennguoitraloi + "'";
				rs = db.get(query);
				System.out.println("KH: " + query);
				if(rs != null)
				{
					if(rs.next())
					{
						this.gioitinh = rs.getString("gioitinh");
						this.diachi = rs.getString("diachi");
						this.tuoi = rs.getString("tuoi");
						this.doituong = rs.getString("doituong");
						this.muctieu = rs.getString("muctieu");
						this.thunhap = rs.getString("thunhap");
					}
				}
				
				
				if(this.socauhoi.trim().length() > 0 )
				{
					Hashtable<String, String> cauhoi_noidung = new Hashtable<String, String>();
					Hashtable<String, String> cauhoi_traloi = new Hashtable<String, String>();
					
					query = "select pk_seq, STT, LOAICAUHOI, CAUHOI, HUONGDANTRALOI from KHAOSAT_CAUHOI where KHAOSAT_FK = '" + this.id + "' order by STT asc";
					ResultSet rsCauHoi = db.get(query);
					if(rsCauHoi != null)
					{
						while(rsCauHoi.next())
						{
							String khaosat_cauhoi_fk = rsCauHoi.getString("pk_seq");
							String cauhoiId = "cau" + rsCauHoi.getString("STT");
							String loaicauhoi = rsCauHoi.getString("LOAICAUHOI");
							
							String cauhoi = rsCauHoi.getString("CAUHOI");
							if(cauhoi.trim().length() <= 0)
								cauhoi = " ";
							
							String huongdantraloi = rsCauHoi.getString("HUONGDANTRALOI");
							if(huongdantraloi.trim().length() <= 0)
								huongdantraloi = " ";
							
							String cautraloi = "";
							if(!loaicauhoi.equals("0"))
							{
								query = "select luachon1, luachon2, luachon3, luachon4, luachon5 from KHAOSAT_CAUHOI_DAPAN where khaosat_cauhoi_fk = '" + khaosat_cauhoi_fk + "'";
								ResultSet rsLuachon = db.get(query);
								
								if(rsLuachon != null)
								{
									while(rsLuachon.next())
									{
										String luachon1 = rsLuachon.getString("luachon1");
										if(luachon1.trim().length() <= 0)
											luachon1 = " ";
										
										String luachon2 = rsLuachon.getString("luachon2");
										if(luachon2.trim().length() <= 0)
											luachon2 = " ";
										
										String luachon3 = rsLuachon.getString("luachon3");
										if(luachon3.trim().length() <= 0)
											luachon3 = " ";
										
										String luachon4 = rsLuachon.getString("luachon4");
										if(luachon4.trim().length() <= 0)
											luachon4 = " ";
										
										String luachon5 = rsLuachon.getString("luachon5");
										if(luachon5.trim().length() <= 0)
											luachon5 = " ";
										
										cautraloi = luachon1 + "__" + luachon2 + "__" + luachon3 + "__" + luachon4 + "__" + luachon5;
									}
									rsLuachon.close();
								}
							}
							else
							{
								cautraloi = " ";
							}
							
							cauhoi_noidung.put(cauhoiId, loaicauhoi + ",," + cauhoi + ",," + huongdantraloi + ",," + cautraloi );
							
							
							String noidungthuchien = "";
							
							query = "select luachon1_chon, luachon2_chon, luachon3_chon, luachon4_chon, luachon5_chon, traloi " +
									"from KHAOSAT_CAUHOI_THUCHIEN " +
									"where khaosat_cauhoi_fk in ( select pk_seq from KHAOSAT_CAUHOI where KHAOSAT_FK = '" + this.id + "' ) and sodienthoai = '" + this.sodienthoai + "' and ten = N'" + this.tennguoitraloi + "' " +
										"and khaosat_cauhoi_fk = '" + khaosat_cauhoi_fk + "'";
							ResultSet rsLuachon = db.get(query);

							if(rsLuachon != null)
							{
								if(rsLuachon.next())
								{
									if(!loaicauhoi.equals("0"))
									{
										String luachon1 = rsLuachon.getString("luachon1_chon");
										if(luachon1.trim().length() <= 0)
											luachon1 = " ";
										
										String luachon2 = rsLuachon.getString("luachon2_chon");
										if(luachon2.trim().length() <= 0)
											luachon2 = " ";
										
										String luachon3 = rsLuachon.getString("luachon3_chon");
										if(luachon3.trim().length() <= 0)
											luachon3 = " ";
										
										String luachon4 = rsLuachon.getString("luachon4_chon");
										if(luachon4.trim().length() <= 0)
											luachon4 = " ";
										
										String luachon5 = rsLuachon.getString("luachon5_chon");
										if(luachon5.trim().length() <= 0)
											luachon5 = " ";
										
										noidungthuchien = luachon1 + "__" + luachon2 + "__" + luachon3 + "__" + luachon4 + "__" + luachon5;
									}
									else
									{
										noidungthuchien = rsLuachon.getString("traloi");
									}
								}
								rsLuachon.close();
							}
								
							System.out.println("___Cau hoi: " + cauhoiId + "  -- noi dung TH: " + noidungthuchien );
							cauhoi_traloi.put(cauhoiId, noidungthuchien);
							
						}
						rsCauHoi.close();
					}
					
					this.noidungcaihoi = cauhoi_noidung;
					this.noidungcautraloi = cauhoi_traloi;
				}
				
			} 
			catch (Exception e) 
			{
				System.out.println("__Exception Init: " + e.getMessage());
			}
		}
		
	}
	
	public String getDiengiai()
	{
		return this.diengiai;
	}

	public void setDiengiai(String diengiai)
	{
		this.diengiai = diengiai;
	}

	
	public void DbClose() 
	{
		try 
		{
			this.db.shutDown();
		} 
		catch (Exception e) {}
		
	}
	
	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}

	
	public String getTieude() {
		
		return this.tieude;
	}

	
	public void setTieude(String tieude) {
		
		this.tieude = tieude;
	}

	
	public String getBophan() {
		
		return this.bophan;
	}

	
	public void setBophan(String bophan) {
		
		this.bophan = bophan;
	}

	
	public String getSocauhoi() {
		
		return this.socauhoi;
	}

	
	public void setSocauhoi(String socauhoi) {
		
		this.socauhoi = socauhoi;
	}

	
	public Hashtable<String, String> getNoidungcauhoi() {
		
		return this.noidungcaihoi;
	}

	
	public void setNoidungcauhoi(Hashtable<String, String> noidung) {
		
		this.noidungcaihoi = noidung;
	}

	
	public String getTennguoiks() {
		
		return this.tennguoitraloi;
	}

	
	public void setTennguoiks(String trangthai) {
		
		this.tennguoitraloi = trangthai;
	}

	
	public String getSodienthoai() {
		
		return this.sodienthoai;
	}

	
	public void setSodienthoai(String sodienthoai) {
		
		this.sodienthoai = sodienthoai;
	}

	
	public String getDiachi() {
		
		return this.diachi;
	}

	
	public void setDiachi(String diachi) {
		
		this.diachi = diachi;
	}

	
	public String getDoituong() {
		
		return this.doituong;
	}

	
	public void setDoituong(String doituong) {
		
		this.doituong = doituong;
	}

	
	public String getMuctieu() {
		
		return this.muctieu;
	}

	
	public void setMuctieu(String muctieu) {
		
		this.muctieu = muctieu;
	}

	
	public String getThunhap() {
		
		return this.thunhap;
	}

	
	public void setThunhap(String thunhap) {
		
		this.thunhap = thunhap;
	}

	
	public String getGioitinh() {
		
		return this.gioitinh;
	}

	
	public void setGioitinh(String gioitinh) {
		
		this.gioitinh = gioitinh;
	}

	
	public String getTuoi() {
		
		return this.tuoi;
	}

	
	public void setTuoi(String tuoi) {
		
		this.tuoi = tuoi;
	}

	
	public Hashtable<String, String> getNoidungcautraloi() {
		
		return this.noidungcautraloi;
	}

	
	public void setNoidungcautraloi(Hashtable<String, String> cautraloi) {
		
		this.noidungcautraloi = cautraloi;
	}
	
	
}
