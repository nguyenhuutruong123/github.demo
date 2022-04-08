package geso.dms.center.beans.thuongvuotmuc.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.soap.Detail;

import org.apache.xmlbeans.impl.regex.REUtil;

import com.cete.dynamicpdf.tc;

import geso.dms.center.beans.thuongvuotmuc.*;
import geso.dms.center.beans.tinhthunhap.INhanvien;
import geso.dms.center.db.sql.dbutils;

public class Thuongvuotmuc implements IThuongvuotmuc
{
	String userId;
	String id;
	String thang;
	String nam;
	
	String trangthai; 
	String tenkhoa;
	String diengiai;
	String msg;
	
	ResultSet dvkdRs;	
	ResultSet kbhRs;
	ResultSet kvRs;
	
	List<IThuongvuotmucDetail> tcDetailList;
	
	dbutils db;
	
	public Thuongvuotmuc()
	{
		this.userId = "";
		this.id = "";
		this.thang = "";
		this.nam = "";
		this.trangthai = "";
		this.tenkhoa = "";
		this.diengiai = "";
		this.msg = "";
		
		this.tcDetailList = new ArrayList<IThuongvuotmucDetail>();
		
		this.db = new dbutils();
	}
	
	public Thuongvuotmuc(String id)
	{
		this.userId = "";
		
		this.thang = "";
		this.nam = "";
		this.id = id;
		this.trangthai = "";
		this.tenkhoa = "";
		this.diengiai = "";
		this.msg = "";
		
		this.tcDetailList = new ArrayList<IThuongvuotmucDetail>();
		
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
			if(this.dvkdRs != null)
				this.dvkdRs.close();
			if(this.kbhRs != null)
				this.kbhRs.close();
			if(this.kvRs != null)
				this.kvRs.close();
			this.db.shutDown();
		} 
		catch (SQLException e) {}
		
	}

	public boolean createKhl()
	{
		try 
		{
			if(this.tcDetailList.size() <= 0)
			{
				this.msg = "Bạn phải nhập tiêu chí thưởng";
				return false;
			}
			
			//Check thang nay da co TIEUCHIDANHGIA
			String sql = "select count(*) as sodong from ThuongVuotMuc where thang = '" + this.thang + "' and nam = '" + this.nam + "' and trangthai != 2 ";
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
				this.msg = "Tháng " + this.thang + ", Năm " + this.nam + " đã thiết lập thưởng vượt mức, bạn chỉ có thể cập nhật";
				return false;
			}
			
			db.getConnection().setAutoCommit(false);
			
			String query = "insert ThuongVuotMuc(thang, nam, diengiai, trangthai, nguoitao, nguoisua, ngaytao, ngaysua) " +
							"values('" + this.thang + "', '" + this.nam + "', N'" + this.diengiai + "', '0', '" + this.userId + "', '" + this.userId + "', '" + getDateTime() + "', '" + getDateTime() + "')";
			
			if(!db.update(query))
			{
				this.msg = "Khong the tao moi ThuongVuotMuc, " + query;
				this.db.getConnection().rollback();
				return false;
			}
			
			//lay dkkm current
			String khlCurrent = "";
			query = "select IDENT_CURRENT('ThuongVuotMuc') as tcdgId";
			
			ResultSet rsDkkm = this.db.get(query);						
			rsDkkm.next();
			khlCurrent = rsDkkm.getString("tcdgId");
			rsDkkm.close();
			
			if(this.tcDetailList.size() > 0)
			{
				for(int i = 0; i < this.tcDetailList.size(); i++)
				{
					IThuongvuotmucDetail tcDetail = this.tcDetailList.get(i);
					
					if(tcDetail.getKvId().trim().length() > 0)
					{
						query = "insert THUONGVUOTMUC_CHUCVU(thang, nam, dvkd_fk, kbh_fk, chucvu, thuongvuotmuc_fk)" +
								" values('" + this.thang + "', '" + this.nam + "', '" + tcDetail.getDvkdId() + "', '" + tcDetail.getKbhId() + "', " +
								"'" + tcDetail.getChucvu() + "', '" + khlCurrent + "') ";
						
						if(!db.update(query))
						{
							this.msg = "Khong the tao moi THUONGVUOTMUC_CHUCVU, " + query;
							this.db.getConnection().rollback();
							return false;
						}
						
						query = "insert THUONGVUOTMUC_CHUCVU_KHUVUC(thuongvuotmuc_chucvu_fk, khuvuc_fk) " +
								"select IDENT_CURRENT('THUONGVUOTMUC_CHUCVU'), pk_seq from KHUVUC where PK_SEQ in (" + tcDetail.getKvId() + ") ";
						
						if(!db.update(query))
						{
							this.msg = "Khong the tao moi THUONGVUOTMUC_CHUCVU, " + query;
							this.db.getConnection().rollback();
							return false;
						}
						
						
						String[] nhomthuong = tcDetail.getNhomthuong();
						String[] tumuc = tcDetail.getTumuc();
						String[] denmuc = tcDetail.getDenmuc();
						String[] thuong = tcDetail.getThuong();
		
						//lay dkkm current
						String kpiId = "";
						query = "select IDENT_CURRENT('THUONGVUOTMUC_CHUCVU') as kpiId";
						
						rsDkkm = this.db.get(query);						
						rsDkkm.next();
						kpiId = rsDkkm.getString("kpiId");
						rsDkkm.close();
						
						for(int j = 0; j < nhomthuong.length; j++)
						{
							if (tumuc[j].trim().length() > 0 && denmuc[j].trim().length() > 0 && thuong[j].trim().length() > 0 )
							{
								String nhomthuong_fk = "null";
								
								if(nhomthuong[j].indexOf("] - ") > 0)
									nhomthuong_fk = nhomthuong[j].substring(1, nhomthuong[j].indexOf("] - "));
								
								tumuc[j] = tumuc[j].replaceAll(",", "");
								denmuc[j] = denmuc[j].replaceAll(",", "");
								thuong[j] = thuong[j].replaceAll(",", "");
		
								query = "insert THUONGVUOTMUC_CHUCVU_TIEUCHI(thuongvuotmuc_chucvu_fk, nhomthuong_fk, tumuc, denmuc, thuong) " +
										"values('" + kpiId + "', " + nhomthuong_fk + ", '" + tumuc[j] + "', '" + denmuc[j] + "', '" + thuong[j] + "')";
								
								if(!db.update(query))
								{
									this.msg = "Khong the tao moi THUONGVUOTMUC_CHUCVU_TIEUCHI, " + query;
									this.db.getConnection().rollback();
									return false;
								}
							}
						}
						
						
						//Insert GSBH
						String[] nvIds = tcDetail.getNhanvienIds().trim().split(",");
						
						for(int count = 0; count < nvIds.length; count++)
						{
							if(tcDetail.getChucvu().equals("SS")) 
							{
								//delete neu da ton tai o truoc
								if(nvIds[count] !=null&&nvIds[count].trim().length()>0)
								{
									query = "delete THUONGVUOTMUC_CHUCVU_GSBH where thuongvuotmuc_chucvu_fk = '" + kpiId + "' and gsbh_fk = '" + nvIds[count].trim() + "'";
									if(!db.update(query))
									{
										this.msg = "Khong the tao moi THUONGVUOTMUC_CHUCVU_GSBH, " + query;
										this.db.getConnection().rollback();
										return false;
									}
									
									query = "Insert THUONGVUOTMUC_CHUCVU_GSBH(thuongvuotmuc_chucvu_fk, gsbh_fk) values('" + kpiId + "', '" + nvIds[count].trim() + "')";
									if(!db.update(query))
									{
										this.msg = "Khong the tao moi THUONGVUOTMUC_CHUCVU_GSBH, " + query;
										this.db.getConnection().rollback();
										return false;
									}
								}
							}
							else   //SR
							{
								if(nvIds[count] !=null&&nvIds[count].trim().length()>0)
								{
									query = "delete THUONGVUOTMUC_CHUCVU_DDKD where thuongvuotmuc_chucvu_fk = '" + kpiId + "' and ddkd_fk = '" + nvIds[count].trim() + "'";
									if(!db.update(query))
									{
										this.msg = "Khong the tao moi THUONGVUOTMUC_CHUCVU_DDKD, " + query;
										this.db.getConnection().rollback();
										return false;
									}
									query = "Insert THUONGVUOTMUC_CHUCVU_DDKD(thuongvuotmuc_chucvu_fk, ddkd_fk) values('" + kpiId + "', '" + nvIds[count].trim() + "')";
									if(!db.update(query))
									{
										this.msg = "Khong the tao moi THUONGVUOTMUC_CHUCVU_DDKD, " + query;
										this.db.getConnection().rollback();
										return false;
									}
								}
							}
							
						}
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
			if(this.tcDetailList.size() <= 0)
			{
				this.msg = "Bạn phải nhập tiêu chí thưởng";
				return false;
			}
			
			//Check thang nay da co TIEUCHIDANHGIA
			String sql = "select count(*) as sodong from THUONGVUOTMUC where thang = '" + this.thang + "' and nam = '" + this.nam + "' and trangthai != 2 and pk_seq != '" + this.id + "' ";
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
				this.msg = "Tháng " + this.thang + ", Năm " + this.nam + " đã thiết lập thưởng vượt mức, bạn chỉ có thể cập nhật";
				return false;
			}
			
			db.getConnection().setAutoCommit(false);
			
			String query = "update THUONGVUOTMUC set thang = '" + this.thang + "', nam = '" + this.nam + "', " +
								"diengiai = N'" + this.diengiai + "', nguoisua = '" + this.userId + "', ngaysua = '" + getDateTime() + "' where pk_seq = '" + this.id + "' ";
			
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat THUONGVUOTMUC, " + query;
				this.db.getConnection().rollback();
				return false;
			}
			
			query = "delete THUONGVUOTMUC_CHUCVU_TIEUCHI where thuongvuotmuc_chucvu_fk in( select pk_seq from THUONGVUOTMUC_CHUCVU where thuongvuotmuc_fk = '" + this.id + "')";
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat THUONGVUOTMUC_CHUCVU_TIEUCHI, " + query;
				this.db.getConnection().rollback();
				return false;
			}
			
			query = "delete THUONGVUOTMUC_CHUCVU_KHUVUC where thuongvuotmuc_chucvu_fk in( select pk_seq from THUONGVUOTMUC_CHUCVU where thuongvuotmuc_fk = '" + this.id + "')";
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat THUONGVUOTMUC_CHUCVU_KHUVUC, " + query;
				this.db.getConnection().rollback();
				return false;
			}
			
			query = "delete THUONGVUOTMUC_CHUCVU_GSBH where thuongvuotmuc_chucvu_fk in( select pk_seq from THUONGVUOTMUC_CHUCVU where thuongvuotmuc_fk = '" + this.id + "')";
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat THUONGVUOTMUC_CHUCVU_GSBH, " + query;
				this.db.getConnection().rollback();
				return false;
			}
			
			query = "delete THUONGVUOTMUC_CHUCVU_DDKD where thuongvuotmuc_chucvu_fk in( select pk_seq from THUONGVUOTMUC_CHUCVU where thuongvuotmuc_fk = '" + this.id + "')";
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat THUONGVUOTMUC_CHUCVU_DDKD, " + query;
				this.db.getConnection().rollback();
				return false;
			}
			
			query = "delete THUONGVUOTMUC_CHUCVU where thuongvuotmuc_fk = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat THUONGVUOTMUC_CHUCVU, " + query;
				this.db.getConnection().rollback();
				return false;
			}
			
			
			if(this.tcDetailList.size() > 0)
			{
				for(int i = 0; i < this.tcDetailList.size(); i++)
				{
					IThuongvuotmucDetail tcDetail = this.tcDetailList.get(i);
					
					if(tcDetail.getKvId().trim().length() > 0)
					{
						query = "insert THUONGVUOTMUC_CHUCVU(thang, nam, dvkd_fk, kbh_fk, chucvu, thuongvuotmuc_fk)" +
								" values('" + this.thang + "', '" + this.nam + "', '" + tcDetail.getDvkdId() + "', '" + tcDetail.getKbhId() + "', " +
								"'" + tcDetail.getChucvu() + "', '" + this.id + "') ";
						
						if(!db.update(query))
						{
							this.msg = "Khong the tao moi THUONGVUOTMUC_CHUCVU, " + query;
							this.db.getConnection().rollback();
							return false;
						}
						
						query = "insert THUONGVUOTMUC_CHUCVU_KHUVUC(thuongvuotmuc_chucvu_fk, khuvuc_fk) " +
								"select IDENT_CURRENT('THUONGVUOTMUC_CHUCVU'), pk_seq from KHUVUC where PK_SEQ in (" + tcDetail.getKvId() + ") ";
						
						if(!db.update(query))
						{
							this.msg = "Khong the tao moi THUONGVUOTMUC_CHUCVU, " + query;
							this.db.getConnection().rollback();
							return false;
						}
						
						
						String[] nhomthuong = tcDetail.getNhomthuong();
						String[] tumuc = tcDetail.getTumuc();
						String[] denmuc = tcDetail.getDenmuc();
						String[] thuong = tcDetail.getThuong();
		
						//lay dkkm current
						String kpiId = "";
						query = "select IDENT_CURRENT('THUONGVUOTMUC_CHUCVU') as kpiId";
						
						ResultSet rsDkkm = this.db.get(query);						
						rsDkkm.next();
						kpiId = rsDkkm.getString("kpiId");
						rsDkkm.close();
						
						for(int j = 0; j < nhomthuong.length; j++)
						{
							if (tumuc[j].trim().length() > 0 && denmuc[j].trim().length() > 0 && thuong[j].trim().length() > 0 )
							{
								String nhomthuong_fk = "null";
								
								if(nhomthuong[j].indexOf("] - ") > 0)
									nhomthuong_fk = nhomthuong[j].substring(1, nhomthuong[j].indexOf("] - "));
								
								tumuc[j] = tumuc[j].replaceAll(",", "");
								denmuc[j] = denmuc[j].replaceAll(",", "");
								thuong[j] = thuong[j].replaceAll(",", "");
		
								query = "insert THUONGVUOTMUC_CHUCVU_TIEUCHI(thuongvuotmuc_chucvu_fk, nhomthuong_fk, tumuc, denmuc, thuong) " +
										"values('" + kpiId + "', " + nhomthuong_fk + ", '" + tumuc[j] + "', '" + denmuc[j] + "', '" + thuong[j] + "')";
								
								if(!db.update(query))
								{
									this.msg = "Khong the tao moi THUONGVUOTMUC_CHUCVU_TIEUCHI, " + query;
									this.db.getConnection().rollback();
									return false;
								}
							}
						}
						
						
						//Insert GSBH
						String[] nvIds = tcDetail.getNhanvienIds().trim().split(",");
						
						for(int count = 0; count < nvIds.length; count++)
						{
							if(tcDetail.getChucvu().equals("SS")) 
							{
								if(nvIds[count] !=null&&nvIds[count].trim().length()>0)
								{
									query = "delete THUONGVUOTMUC_CHUCVU_GSBH where thuongvuotmuc_chucvu_fk = '" + kpiId + "' and gsbh_fk = '" + nvIds[count].trim() + "'";
									if(!db.update(query))
									{
										this.msg = "Khong the tao moi THUONGVUOTMUC_CHUCVU_GSBH, " + query;
										this.db.getConnection().rollback();
										return false;
									}
									
									query = "Insert THUONGVUOTMUC_CHUCVU_GSBH(thuongvuotmuc_chucvu_fk, gsbh_fk) values('" + kpiId + "', '" + nvIds[count].trim() + "')";
									if(!db.update(query))
									{
										this.msg = "Khong the tao moi THUONGVUOTMUC_CHUCVU_GSBH, " + query;
										this.db.getConnection().rollback();
										return false;
									}
								}
								
							}
							else   //SR
							{
								if(nvIds[count] !=null&&nvIds[count].trim().length()>0)
								{
									query = "delete THUONGVUOTMUC_CHUCVU_DDKD where thuongvuotmuc_chucvu_fk = '" + kpiId + "' and ddkd_fk = '" + nvIds[count].trim() + "'";
									if(!db.update(query))
									{
										this.msg = "Khong the tao moi THUONGVUOTMUC_CHUCVU_DDKD, " + query;
										this.db.getConnection().rollback();
										return false;
									}
									query = "Insert THUONGVUOTMUC_CHUCVU_DDKD(thuongvuotmuc_chucvu_fk, ddkd_fk) values('" + kpiId + "', '" + nvIds[count].trim() + "')";
									if(!db.update(query))
									{
										this.msg = "Khong the tao moi THUONGVUOTMUC_CHUCVU_DDKD, " + query;
										this.db.getConnection().rollback();
										return false;
									}
								}
							}
							
						}
					}
					
				}
			}
			
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
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
		this.dvkdRs = db.getScrol("select pk_seq, donvikinhdoanh as donvi from donvikinhdoanh where trangthai = '1' ");
		this.kbhRs = db.getScrol("select pk_seq, diengiai from kenhbanhang where trangthai = '1'");
		this.kvRs = db.getScrol("select pk_seq, ten from khuvuc where trangthai = '1' order by ten ");
	}

	public void init() 
	{
		String query = "select thang, nam, diengiai from ThuongVuotMuc where pk_seq = '" + this.id + "'";
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
				}
				rs.close();
			} 
			catch (Exception e) 
			{
				System.out.println("115.Error: " + e.getMessage());
			}
		}
		
		this.iniiThuNhap();
		
		this.createRs();
	}
	
	private void iniiThuNhap()
	{
		 List<IThuongvuotmucDetail> tnDetaiList = new ArrayList<IThuongvuotmucDetail>();
		 
		 String query = "select pk_seq, thang, nam, dvkd_fk, kbh_fk, KHUVUC_fk, chucvu " +
		 				"from THUONGVUOTMUC_CHUCVU where thuongvuotmuc_fk = '" + this.id + "' ";
		 
		 System.out.println("1.Tinh phu cap init: " + query);
		 
		 ResultSet rs = db.get(query);
		 if(rs != null)
		 {
			 try 
			 {
				 IThuongvuotmucDetail detail = null;
				 int stt = 0;
				 while(rs.next())
				 {
					 String pk_seq = rs.getString("pk_seq");
					 String dvkd_fk = rs.getString("dvkd_fk");
					 String kbh_fk = rs.getString("kbh_fk");
					 String chucvu = rs.getString("chucvu");
					
					 detail = new ThuongvuotmucDetail();
		    			
					detail.setId(pk_seq);
	    			detail.setDvkdId(dvkd_fk);
	    			detail.setKbhId(kbh_fk);
	    			detail.setChucvu(chucvu);
	    			
	    			String[] nhomthuong = new String[]{"", "", "", "", "", "", "", "", "", ""};
	    			String[] tumuc = new String[]{"", "", "", "", "", "", "", "", "", ""};
	    			String[] denmuc = new String[]{"", "", "", "", "", "", "", "", "", ""};
	    			String[] thuong = new String[]{"", "", "", "", "", "", "", "", "", ""};
	    			
	    			query = "select case when a.nhomthuong_fk is not null then '[' +  CAST( nhomthuong_fk as varchar(10)) + '] - ' + b.TEN else '' end as nhomTen, tumuc, denmuc, thuong " +
	    					"from THUONGVUOTMUC_CHUCVU_TIEUCHI a left join NhomThuong b on a.nhomthuong_fk = b.pk_seq " +
	    					"where THUONGVUOTMUC_CHUCVU_FK = '" + pk_seq + "'";
	    			
	    			System.out.println("2.Khoi tao Detail: " + query);
	    			
	    			ResultSet rsDetail = db.get(query);
	    			if(rsDetail != null)
	    			{
	    				int count = 0;
	    				while(rsDetail.next())
	    				{
	    					nhomthuong[count] = rsDetail.getString("nhomTen");
	    					tumuc[count] = rsDetail.getString("tumuc");
	    					denmuc[count] = rsDetail.getString("denmuc");
	    					thuong[count] = rsDetail.getString("thuong");
	    					
	    					//System.out.println("333.Toi dang vao day: " + nhomthuong[count] + " - Noi dung: " + tumuc[count] + " -- Trong So: " + denmuc[count]);
	    					count++;
	    				}
	    				rsDetail.close();
	    			}
	    			
	    			detail.setNhomthuong(nhomthuong);
	    			detail.setTumuc(tumuc);
	    			detail.setDenmuc(denmuc);
	    			detail.setThuong(thuong);
	    			
	    			//khoi tao khu vuc
	    			query = "select khuvuc_fk from THUONGVUOTMUC_CHUCVU_KHUVUC where thuongvuotmuc_chucvu_fk = '" + pk_seq + "'";
	    			
	    			System.out.println("3.Khoi tao khu vuc: " + query);
	    			rsDetail = db.get(query);
	    			
	    			if(rsDetail != null)
	    			{
	    				String kvId = "";
	    				while(rsDetail.next())
	    				{
	    					kvId += rsDetail.getString("khuvuc_fk") + ",";
	    				}
	    				rsDetail.close();
	    				
	    				if(kvId.trim().length() > 0 )
	    				{
	    					kvId = kvId.substring(0, kvId.length() - 1);
	    					detail.setKvId(kvId);
	    				}
	    			}
	    			
	    			
	    			//Khoi tao NvIds
	    			if(chucvu.equals("SS"))
	    			{
	    				query = "select gsbh_fk as nvId from THUONGVUOTMUC_CHUCVU_GSBH where thuongvuotmuc_chucvu_fk = '" + pk_seq + "'";
	    			}
	    			else
	    			{
	    				query = "select ddkd_fk as nvId from THUONGVUOTMUC_CHUCVU_DDKD where thuongvuotmuc_chucvu_fk = '" + pk_seq + "'";
	    			}
	    			
	    			System.out.println("5.Khoi tao nhan vien: " + query);
	    			
	    			rsDetail = db.get(query);
	    			if(rsDetail != null)
	    			{
	    				String nvId = "";
	    				while(rsDetail.next())
	    				{
	    					nvId += rsDetail.getString("nvId") + ",";
	    				}
	    				rsDetail.close();
	    				
	    				//System.out.println("________Nhan vien Id: " + nvId);
	    				
	    				if(nvId.trim().length() > 0 )
	    				{
	    					nvId = nvId.substring(0, nvId.length() - 1);
	    					detail.setNhanvienIds(nvId);
	    					
	    					if(chucvu.equals("SS"))
	    					{
	    						detail.setGsbhSelected(nvId);
	    					}
	    					else
	    					{
	    						detail.setDdkdSelected(nvId);
	    					}
	    					
	    					detail.InitNhanVienSelected();
	    				}
	    			}

	    			detail.setSTT(Integer.toString(stt));
	    			stt++;
	    			
	    			tnDetaiList.add(detail);
				 }
				 rs.close();
			} 
			catch (Exception e) 
			{
				System.out.println("115.Error: " + e.getMessage());
			}
		 }
		 
		 //System.out.println("__So Tieu chi: " + tnDetaiList.size());
		 this.tcDetailList = tnDetaiList;
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

	public List<IThuongvuotmucDetail> getTieuchiDetail() 
	{
		return this.tcDetailList;
	}

	public void setTieuchiDetail(List<IThuongvuotmucDetail> tcDetail) 
	{
		this.tcDetailList = tcDetail;
	}


	public ResultSet getKhuvucRs() 
	{
		return this.kvRs;
	}

	public void setKhuvucRs(ResultSet kvRs) 
	{
		this.kvRs = kvRs;
	}

	
	public ResultSet getDvkdRs() {
		
		return this.dvkdRs;
	}

	
	public void setDvkdRs(ResultSet gsbhRs) {
		
		this.dvkdRs = gsbhRs;
	}

	
	public ResultSet getKbhRs() {
		
		return this.kbhRs;
	}

	
	public void setKbhRs(ResultSet kbhRs) {
		
		this.kbhRs = kbhRs;
	}

	
}
