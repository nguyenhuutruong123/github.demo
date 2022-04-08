package geso.dms.center.beans.tieuchidanhgia.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import geso.dms.center.beans.tieuchidanhgia.ITieuchiDetail;
import geso.dms.center.beans.tieuchidanhgia.ITieuchidanhgia;
import geso.dms.center.db.sql.dbutils;

public class Tieuchidanhgia implements ITieuchidanhgia
{
	String userId;
	String id;
	
	String thang;
	String nam;
	
	String tungay;
	String denngay;
	String trangthai; 
	String tenkhoa;
	String diengiai;
	String msg;
	
	ResultSet gsbhRs;
	String gsbhIds;
	
	ResultSet vungRs;
	String vungId;
	ResultSet kvRs;
	String kvId;

	
	List<ITieuchiDetail> tcDetailList;
	
	dbutils db;
	
	public Tieuchidanhgia()
	{
		this.userId = "";
		this.id = "";
		this.thang = "";
		this.nam = "";
		this.tungay = "";
		this.denngay = "";
		this.trangthai = "";
		this.tenkhoa = "";
		this.diengiai = "";
		this.msg = "";
		
		this.vungId = "";
		this.gsbhIds = "";
		this.kvId = "";
		
		this.tcDetailList = new ArrayList<ITieuchiDetail>();
		
		this.db = new dbutils();
	}
	
	public Tieuchidanhgia(String id)
	{
		this.userId = "";
		
		this.thang = "";
		this.nam = "";
		this.id = id;
		this.tungay = "";
		this.denngay = "";
		this.trangthai = "";
		this.tenkhoa = "";
		this.diengiai = "";
		this.msg = "";
		
		this.vungId = "";
		this.gsbhIds = "";
		this.kvId = "";
		
		this.tcDetailList = new ArrayList<ITieuchiDetail>();
		
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

	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}

	public String getTenkhoa() 
	{
		return this.tenkhoa;
	}

	public void setTenkhoa(String tenkhoa) 
	{
		this.tenkhoa = tenkhoa;
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

	public void DbClose() 
	{
		
		try 
		{
			if(this.vungRs != null)
				this.vungRs.close();
			if(this.gsbhRs != null)
				this.gsbhRs.close();
			if(this.kvRs != null)
				this.kvRs.close();
			this.db.shutDown();
		} 
		catch (SQLException e) {}
		
	}

	public ResultSet getGsbhRs() 
	{
		return this.gsbhRs;
	}

	public void setGsbhRs(ResultSet gsbhRs)
	{
		this.gsbhRs = gsbhRs;
	}

	public String getGsbhIds() 
	{
		return this.gsbhIds;
	}

	public void setGsbhIds(String gsbhIds)
	{
		this.gsbhIds = gsbhIds;
	}


	public boolean createKhl()
	{
		try 
		{
			//Kiem tra tong trong so co dat 100% khong
			if(this.tcDetailList.size() <= 0)
			{
				this.msg = "Bạn phải nhập tiêu chí của quy trình làm việc trong tháng";
				return false;
			}
			
			float trongso = 0f;
			for(int i = 0; i < this.tcDetailList.size(); i++)
			{
				ITieuchiDetail deTail = this.tcDetailList.get(i);
				trongso += Float.parseFloat(deTail.getTrongso());
			}
			if(trongso != 100.0f)
			{
				this.msg = "Tổng trọng số của các tiêu chí trong quy trình làm việc phải bằng 100%";
				return false;
			}
			
			//Check thang nay da co TIEUCHIDANHGIA
			String sql = "select count(*) as sodong from TIEUCHIDANHGIA where thang = '" + this.thang + "' and nam = '" + this.nam + "' and trangthai != 2 ";
			ResultSet rs = db.get(sql);
			int sodong = 0;
			if(rs != null)
			{
				if(rs.next())
				{
					sodong = rs.getInt("sodong");
				}
				rs.close();
			}
			
			if(sodong > 0)
			{
				this.msg = "Tháng " + this.thang + ", Năm " + this.nam + " đã thiết lập quy trình làm việc, bạn chỉ có thể cập nhật";
				return false;
			}
			
			db.getConnection().setAutoCommit(false);
			
			String query = "insert TIEUCHIDANHGIA(thang, nam, diengiai, trangthai, nguoitao, nguoisua, ngaytao, ngaysua) " +
							"values('" + this.thang + "', '" + this.nam + "', N'" + this.diengiai + "', '0', '" + this.userId + "', '" + this.userId + "', '" + getDateTime() + "', '" + getDateTime() + "')";
			
			if(!db.update(query))
			{
				this.msg = "Khong the tao moi TIEUCHIDANHGIA, " + query;
				this.db.getConnection().rollback();
				return false;
			}
			
			//lay dkkm current
			String khlCurrent = "";
			query = "select IDENT_CURRENT('TIEUCHIDANHGIA') as tcdgId";
			
			ResultSet rsDkkm = this.db.get(query);						
			rsDkkm.next();
			khlCurrent = rsDkkm.getString("tcdgId");
			rsDkkm.close();
			
			if(this.tcDetailList.size() > 0)
			{
				for(int i = 0; i < this.tcDetailList.size(); i++)
				{
					ITieuchiDetail tcDetail = this.tcDetailList.get(i);
					
					query = "insert TIEUCHIDANHGIA_TIEUCHI(tieuchidanhgia_fk, ma, diengiai, trongso) " +
							"values('" + khlCurrent + "', N'" + tcDetail.getMa() + "', N'" + tcDetail.getDiengiai() + "', '" + tcDetail.getTrongso() + "') ";
					if(!db.update(query))
					{
						this.msg = "Khong the tao moi TIEUCHIDANHGIA_TIEUCHI, " + query;
						this.db.getConnection().rollback();
						return false;
					}
				}
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			this.msg = "Loi: " + e.getMessage();
			System.out.println(this.msg);
			try 
			{
				db.getConnection().rollback();
			} 
			catch (SQLException e1) {}
			return false;
		}
		
		return true;
	}

	
	public boolean updateKhl() 
	{
		try 
		{
			float trongso = 0f;
			for(int i = 0; i < this.tcDetailList.size(); i++)
			{
				ITieuchiDetail deTail = this.tcDetailList.get(i);
				trongso += Float.parseFloat(deTail.getTrongso());
			}
			if(trongso != 100.0f)
			{
				this.msg = "Tổng trọng số của các tiêu chí trong quy trình làm việc phải bằng 100%";
				return false;
			}
			
			//Check thang nay da co TIEUCHIDANHGIA
			String sql = "select count(*) as sodong from TIEUCHIDANHGIA where thang = '" + this.thang + "' and nam = '" + this.nam + "' and trangthai !=2 and pk_seq != '" + this.id + "' ";
			System.out.println("Query Check___"+sql);
			ResultSet rs = db.get(sql);
			int sodong = 0;
			if(rs != null)
			{
				if(rs.next())
				{
					sodong = rs.getInt("sodong");
				}
				rs.close();
			}
			
			if(sodong > 0)
			{
				this.msg = "Tháng " + this.thang + ", Năm " + this.nam + " đã thiết lập quy trình làm việc, bạn chỉ có thể cập nhật";
				return false;
			}
			
			db.getConnection().setAutoCommit(false);
			
			String query = "update TIEUCHIDANHGIA set thang = '" + this.thang + "', nam = '" + this.nam + "', " +
								"diengiai = N'" + this.diengiai + "', nguoisua = '" + this.userId + "', ngaysua = '" + getDateTime() + "' where pk_seq = '" + this.id + "' ";
			System.out.print("Cap Nhat TieuChi"+query);
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat TIEUCHIDANHGIA, " + query;
				this.db.getConnection().rollback();
				return false;
			}
			
			if(this.gsbhIds.trim().length() <= 0)
			{
				if(this.tcDetailList.size() > 0)
				{
					for(int i = 0; i < this.tcDetailList.size(); i++)
					{
						ITieuchiDetail tcDetail = this.tcDetailList.get(i);
						
						query = "select count(*) as sodong from TIEUCHIDANHGIA_TIEUCHI " +
								"where tieuchidanhgia_fk = '" + this.id + "' and pk_seq = '" + tcDetail.getId() + "' ";
						
						ResultSet rsCheck = db.get(query);
						System.out.println("Kiem tra tieu chi co chua____ "+query);
						boolean flag = false;
						if(rsCheck!=null)
						{
							rsCheck.next();
							if(rsCheck.getInt("sodong") > 0)
								flag = true;
							rsCheck.close();
						}
						
						if(flag)
						{
							query = "update TIEUCHIDANHGIA_TIEUCHI set ma = N'" + tcDetail.getMa() + "', diengiai = N'" + tcDetail.getDiengiai() + "', trongso = '" + tcDetail.getTrongso() + "' " +
									"where tieuchidanhgia_fk = '" + this.id + "' and pk_seq = '" + tcDetail.getId() + "' ";
						
						}
						else
						{
							query = "insert TIEUCHIDANHGIA_TIEUCHI(tieuchidanhgia_fk, ma, diengiai, trongso) " +
									"values('" + this.id + "', N'" + tcDetail.getMa() + "', N'" + tcDetail.getDiengiai() + "', '" + tcDetail.getTrongso() + "') ";
						}
						
						System.out.println("3.Insert: " + query);
						
						if(!db.update(query))
						{
							this.msg = "Khong the tao moi TIEUCHIDANHGIA_TIEUCHI, " + query;
							this.db.getConnection().rollback();
							return false;
						}
					}
				}
			}
			else
			{
				if(this.tcDetailList.size() > 0)
				{
					for(int i = 0; i < this.tcDetailList.size(); i++)
					{
						ITieuchiDetail tcDetail = this.tcDetailList.get(i);
						int MaxDiemCham=5;
						Float DiemCham=Float.parseFloat(tcDetail.getChamlan1());
						if(MaxDiemCham<DiemCham)
						{
							this.msg = "Diem cham lon hon vuot muc cho phep!("+DiemCham+")";
							this.db.getConnection().rollback();
							return false;
						}
						query = "select count(*) as sodong from TIEUCHIDANHGIA_TIEUCHI_GSBH " +
								"where gsbh_fk = '" + this.gsbhIds + "' and tieuchidanhgia_tieuchi_fk = '" + tcDetail.getId() + "' ";
						
						ResultSet rsCheck = db.get(query);
						boolean flag = false;
						if(rsCheck.next())
						{
							if(rsCheck.getInt("sodong") > 0)
								flag = true;
							rsCheck.close();
						}
						
						if(flag)
						{
							query = "update TIEUCHIDANHGIA_TIEUCHI_GSBH set chamlan1 = '" + tcDetail.getChamlan1() + "', chamlan2 = '" + tcDetail.getChamlan2() + "', chamlan3 = '" + tcDetail.getChamlan3() + "' " +
									"where gsbh_fk = '" + this.gsbhIds + "' and tieuchidanhgia_tieuchi_fk = '" + tcDetail.getId() + "' ";
						
						}
						else
						{
							query = "insert TIEUCHIDANHGIA_TIEUCHI_GSBH(tieuchidanhgia_tieuchi_fk, gsbh_fk, chamlan1, chamlan2, chamlan3) " +
									"values('" + tcDetail.getId() + "', '" + this.gsbhIds + "', '" + tcDetail.getChamlan1() + "', '" + tcDetail.getChamlan2() + "', '" + tcDetail.getChamlan3() + "') ";
						}
						
						System.out.println("4.Insert: " + query);
						
						if(!db.update(query))
						{
							this.msg = "Khong the tao moi TIEUCHIDANHGIA_TIEUCHI_GSBH, " + query;
							this.db.getConnection().rollback();
							return false;
						}
					}
				}
			}
			
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			this.msg = "Loi: " + e.getMessage();
			try 
			{
				db.getConnection().rollback();
			} 
			catch (SQLException e1) {}
			return false;
		}
		
		return true;
	}

	
	public void createRs() 
	{
		this.vungRs = db.get("select pk_seq,ten,diengiai from vung where trangthai = '1' ");

		if (this.vungId.length() > 0) 
		{
			this.kvRs = db.get("select pk_seq,ten from khuvuc where vung_fk = '"+ this.vungId + "' and trangthai = '1'");
		} 
		else
			this.kvRs = db.get("select pk_seq,ten from khuvuc where trangthai = '1' ");
		
		String query = "select pk_seq, ten, isnull(dienthoai, 'na') as dienthoai, isnull(email, 'na') as email, isnull(diachi, 'na') as diachi, '0' as trangthai " +
				  "from GIAMSATBANHANG where trangthai = '1' ";

		if(this.kvId.length() > 0)
			query += " and khuvuc_fk = '" + this.kvId + "' ";
			
		/*if(this.id.trim().length() > 0)
			query += " and PK_SEQ not in ( select gsbh_fk from KhoaHuanLuyen_GSBH where khoahuanluyen_fk = '" + this.id + "' ) ";*/

		System.out.println("__Khoi tao GSBH: " + query);

		this.gsbhRs = db.get(query);
		
		
		//Khoi tao cac tieu chi
		List<ITieuchiDetail> tcDetailList = new ArrayList<ITieuchiDetail>();
		if(this.id.length() > 0)
		{
			if(this.gsbhIds.trim().length() > 0)
			{
				query = "select b.pk_seq, b.ma, b.diengiai, b.trongso, isnull(c.chamlan1, '') as chamlan1, isnull(c.chamlan2, '') as chamlan2, isnull(c.chamlan3, '') as chamlan3 " +
						"from TIEUCHIDANHGIA a inner join TIEUCHIDANHGIA_TIEUCHI b on a.pk_seq = b.tieuchidanhgia_fk  " +
							"left join ( select * from TIEUCHIDANHGIA_TIEUCHI_GSBH where gsbh_fk = '" + this.gsbhIds + "' ) c on b.pk_seq = c.tieuchidanhgia_tieuchi_fk " +
						"where a.pk_seq = '" + this.id + "'";
			}
			else
			{
				query = "select b.pk_seq, b.ma, b.diengiai, b.trongso, '' as chamlan1, '' as chamlan2, '' as chamlan3 " +
						"from TIEUCHIDANHGIA a inner join TIEUCHIDANHGIA_TIEUCHI b on a.pk_seq = b.tieuchidanhgia_fk " +
						"where a.pk_seq = '" + this.id + "'";
			}
			
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				try 
				{
					ITieuchiDetail tcDetail = null;
					while(rs.next())
					{
						String pk_seq = rs.getString("pk_seq");
						String ma = rs.getString("ma");
						String diengiai = rs.getString("diengiai");
						String trongso = rs.getString("trongso");
						String chamlan1 = rs.getString("chamlan1");
						String chamlan2 = rs.getString("chamlan2");
						String chamlan3 = rs.getString("chamlan3");
						
						tcDetail = new TieuchiDetail();
						tcDetail.setId(pk_seq);
						tcDetail.setMa(ma);
						tcDetail.setDiengiai(diengiai);
						tcDetail.setTrongso(trongso);
						tcDetail.setChamlan1(chamlan1);
						tcDetail.setChamlan2(chamlan2);
						tcDetail.setChamlan3(chamlan3);
						
						tcDetailList.add(tcDetail);
					}
					rs.close();
				} 
				catch (Exception e) 
				{
					System.out.println("__Loi: " + e.getMessage());
				}
			}
		}
		
		this.tcDetailList = tcDetailList;
	}

	public void init() 
	{
		String query = "select thang, nam, trangthai, diengiai from TieuChiDanhGia where pk_seq = '" + this.id + "'";
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				if(rs.next())
				{
					this.thang = rs.getString("thang");
					this.nam = rs.getString("nam");
					this.diengiai = rs.getString("diengiai");	
					this.trangthai = rs.getString("trangthai");
				}
				rs.close();
			} 
			catch (Exception e) 
			{
				System.out.println("115.Error: " + e.getMessage());
			}
		}
		
		this.createRs();
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
	
	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	public List<ITieuchiDetail> getTieuchiDetail() 
	{
		return this.tcDetailList;
	}

	public void setTieuchiDetail(List<ITieuchiDetail> tcDetail) 
	{
		this.tcDetailList = tcDetail;
	}

	public ResultSet getVungRs() 
	{
		return this.vungRs;
	}

	public void setVungRs(ResultSet vungRs)
	{
		this.vungRs = vungRs;
	}

	public String getVungId() 
	{
		return this.vungId;
	}

	public void setVungId(String vungId) 
	{
		this.vungId = vungId;
	}

	public ResultSet getKhuvucRs() 
	{
		return this.kvRs;
	}

	public void setKhuvucRs(ResultSet kvRs) 
	{
		this.kvRs = kvRs;
	}

	public String getKvId()
	{
		return this.kvId;
	}

	public void setKvId(String kvId) 
	{
		this.kvId = kvId;
	}
}
