package geso.dms.center.beans.tinhphucap.imp;

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

import geso.dms.center.beans.tinhphucap.*;
import geso.dms.center.beans.tinhthunhap.INhanvien;
import geso.dms.center.db.sql.dbutils;

public class Tinhphucap implements ITinhphucap
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
	
	List<ITinhphucapDetail> tcDetailList;
	
	dbutils db;
	
	public Tinhphucap()
	{
		this.userId = "";
		this.id = "";
		this.thang = "";
		this.nam = "";
		this.trangthai = "";
		this.tenkhoa = "";
		this.diengiai = "";
		this.msg = "";
		
		this.tcDetailList = new ArrayList<ITinhphucapDetail>();
		
		this.db = new dbutils();
	}
	
	public Tinhphucap(String id)
	{
		this.userId = "";
		
		this.thang = "";
		this.nam = "";
		this.id = id;
		this.trangthai = "";
		this.tenkhoa = "";
		this.diengiai = "";
		this.msg = "";
		
		this.tcDetailList = new ArrayList<ITinhphucapDetail>();
		
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
				this.msg = "Bạn phải nhập tiêu chí của phụ cấp trong tháng";
				return false;
			}
			
			//Check thang nay da co TIEUCHIDANHGIA
			String sql = "select count(*) as sodong from TINHPHUCAP where thang = '" + this.thang + "' and nam = '" + this.nam + "' and trangthai != 2 ";
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
				this.msg = "Tháng " + this.thang + ", Năm " + this.nam + " đã thiết lập tính phụ cấp, bạn chỉ có thể cập nhật";
				return false;
			}
			
			db.getConnection().setAutoCommit(false);
			
			String query = "insert TINHPHUCAP(thang, nam, diengiai, trangthai, nguoitao, nguoisua, ngaytao, ngaysua) " +
							"values('" + this.thang + "', '" + this.nam + "', N'" + this.diengiai + "', '0', '" + this.userId + "', '" + this.userId + "', '" + getDateTime() + "', '" + getDateTime() + "')";
			
			if(!db.update(query))
			{
				this.msg = "Khong the tao moi TINHPHUCAP, " + query;
				this.db.getConnection().rollback();
				return false;
			}
			
			//lay dkkm current
			String khlCurrent = "";
			query = "select IDENT_CURRENT('TINHPHUCAP') as tcdgId";
			
			ResultSet rsDkkm = this.db.get(query);						
			rsDkkm.next();
			khlCurrent = rsDkkm.getString("tcdgId");
			rsDkkm.close();
			
			if(this.tcDetailList.size() > 0)
			{
				for(int i = 0; i < this.tcDetailList.size(); i++)
				{
					ITinhphucapDetail tcDetail = this.tcDetailList.get(i);
					
					if(tcDetail.getKvId().trim().length() > 0)
					{
						query = "insert TINHPHUCAP_CHUCVU(thang, nam, dvkd_fk, kbh_fk, chucvu, tinhphucap_fk)" +
								" values('" + this.thang + "', '" + this.nam + "', '" + tcDetail.getDvkdId() + "', '" + tcDetail.getKbhId() + "', " +
								"'" + tcDetail.getChucvu() + "', '" + khlCurrent + "') ";
						
						if(!db.update(query))
						{
							this.msg = "Khong the tao moi TINHPHUCAP_CHUCVU, " + query;
							this.db.getConnection().rollback();
							return false;
						}
						
						query = "insert TINHPHUCAP_CHUCVU_KHUVUC(tinhphucap_chucvu_fk, khuvuc_fk) " +
								"select IDENT_CURRENT('TINHPHUCAP_CHUCVU'), pk_seq from KHUVUC where PK_SEQ in (" + tcDetail.getKvId() + ") ";
						
						if(!db.update(query))
						{
							this.msg = "Khong the tao moi TINHPHUCAP_CHUCVU_KHUVUC, " + query;
							this.db.getConnection().rollback();
							return false;
						}
						
						
						String[] maDetail = tcDetail.getMaDetail();
						String[] noidung = tcDetail.getNoidung();
						String[] trongso = tcDetail.getTrongso();
						String[] tinhtheonc = tcDetail.getTinhtheongaycong();
		
						//lay dkkm current
						String kpiId = "";
						query = "select IDENT_CURRENT('TINHPHUCAP_CHUCVU') as kpiId";
						
						rsDkkm = this.db.get(query);						
						rsDkkm.next();
						kpiId = rsDkkm.getString("kpiId");
						rsDkkm.close();
						
						for(int j = 0; j < maDetail.length; j++)
						{
							if (noidung[j].trim().length() > 0 && trongso[j].trim().length() > 0)
							{
								trongso[j] = trongso[j].replaceAll(",", "");
		
								query = "insert TINHPHUCAP_CHUCVU_PHUCAP(tinhphucap_chucvu_fk, ma, noidung, trongso, tinhtheongaycong) " +
										"values('" + kpiId + "', '" + maDetail[j] + "', N'" + noidung[j] + "', '" + trongso[j] + "', '" + tinhtheonc[j] + "')";
								
								if(!db.update(query))
								{
									this.msg = "Khong the tao moi TINHPHUCAP_CHUCVU_PHUCAP, " + query;
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
								query = "delete TINHPHUCAP_CHUCVU_GSBH where tinhphucap_chucvu_fk = '" + kpiId + "' and gsbh_fk = '" + nvIds[count].trim() + "'";
								if(!db.update(query))
								{
									this.msg = "Khong the tao moi TINHPHUCAP_CHUCVU_GSBH, " + query;
									this.db.getConnection().rollback();
									return false;
								}
								
								query = "Insert TINHPHUCAP_CHUCVU_GSBH(tinhphucap_chucvu_fk, gsbh_fk) values('" + kpiId + "', '" + nvIds[count].trim() + "')";
								if(!db.update(query))
								{
									this.msg = "Khong the tao moi TINHPHUCAP_CHUCVU_GSBH, " + query;
									this.db.getConnection().rollback();
									return false;
								}
								
							}
							else   //SR
							{
								query = "delete TINHPHUCAP_CHUCVU_DDKD where tinhphucap_chucvu_fk = '" + kpiId + "' and ddkd_fk = '" + nvIds[count].trim() + "'";
								if(!db.update(query))
								{
									this.msg = "Khong the tao moi TINHPHUCAP_CHUCVU_DDKD, " + query;
									this.db.getConnection().rollback();
									return false;
								}
								
								query = "Insert TINHPHUCAP_CHUCVU_DDKD(tinhphucap_chucvu_fk, ddkd_fk) values('" + kpiId + "', '" + nvIds[count].trim() + "')";
								if(!db.update(query))
								{
									this.msg = "Khong the tao moi TINHPHUCAP_CHUCVU_DDKD, " + query;
									this.db.getConnection().rollback();
									return false;
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
			//Kiem tra tong trong so co dat 100% khong
			if(this.tcDetailList.size() <= 0)
			{
				this.msg = "Bạn phải nhập tiêu chí của phụ cấp trong tháng";
				return false;
			}
			
			//Check thang nay da co TIEUCHIDANHGIA
			String sql = "select count(*) as sodong from TINHPHUCAP where thang = '" + this.thang + "' and nam = '" + this.nam + "' and trangthai != 2 and pk_seq != '" + this.id + "' ";
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
				this.msg = "Tháng " + this.thang + ", Năm " + this.nam + " đã thiết lập tính phụ cấp, bạn chỉ có thể cập nhật";
				return false;
			}
			
			db.getConnection().setAutoCommit(false);
			
			String query = "update TINHPHUCAP set thang = '" + this.thang + "', nam = '" + this.nam + "', " +
								"diengiai = N'" + this.diengiai + "', nguoisua = '" + this.userId + "', ngaysua = '" + getDateTime() + "' where pk_seq = '" + this.id + "' ";
			
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat TINHPHUCAP, " + query;
				this.db.getConnection().rollback();
				return false;
			}
			
			query = "delete TINHPHUCAP_CHUCVU_PHUCAP where tinhphucap_chucvu_fk in( select pk_seq from TINHPHUCAP_CHUCVU where tinhphucap_fk = '" + this.id + "')";
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat TINHPHUCAP_CHUCVU_PHUCAP, " + query;
				this.db.getConnection().rollback();
				return false;
			}
			
			query = "delete TINHPHUCAP_CHUCVU_KHUVUC where tinhphucap_chucvu_fk in( select pk_seq from TINHPHUCAP_CHUCVU where tinhphucap_fk = '" + this.id + "')";
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat TINHPHUCAP_CHUCVU_KHUVUC, " + query;
				this.db.getConnection().rollback();
				return false;
			}
			
			query = "delete TINHPHUCAP_CHUCVU_GSBH where tinhphucap_chucvu_fk in( select pk_seq from TINHPHUCAP_CHUCVU where tinhphucap_fk = '" + this.id + "')";
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat TINHPHUCAP_CHUCVU_GSBH, " + query;
				this.db.getConnection().rollback();
				return false;
			}
			
			query = "delete TINHPHUCAP_CHUCVU_DDKD where tinhphucap_chucvu_fk in( select pk_seq from TINHPHUCAP_CHUCVU where tinhphucap_fk = '" + this.id + "')";
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat TINHPHUCAP_CHUCVU_DDKD, " + query;
				this.db.getConnection().rollback();
				return false;
			}
			
			query = "delete TINHPHUCAP_CHUCVU where tinhphucap_fk = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat TINHPHUCAP_CHUCVU, " + query;
				this.db.getConnection().rollback();
				return false;
			}
			
			
			if(this.tcDetailList.size() > 0)
			{
				for(int i = 0; i < this.tcDetailList.size(); i++)
				{
					ITinhphucapDetail tcDetail = this.tcDetailList.get(i);
					
					if(tcDetail.getKvId().trim().length() > 0)
					{
						query = "insert TINHPHUCAP_CHUCVU(thang, nam, dvkd_fk, kbh_fk, chucvu, tinhphucap_fk)" +
								" values('" + this.thang + "', '" + this.nam + "', '" + tcDetail.getDvkdId() + "', '" + tcDetail.getKbhId() + "', " +
								"'" + tcDetail.getChucvu() + "', '" + this.id + "') ";
						
						if(!db.update(query))
						{
							this.msg = "Khong the cap nhat TINHPHUCAP_CHUCVU, " + query;
							this.db.getConnection().rollback();
							return false;
						}
						
						query = "insert TINHPHUCAP_CHUCVU_KHUVUC(tinhphucap_chucvu_fk, khuvuc_fk) " +
								"select IDENT_CURRENT('TINHPHUCAP_CHUCVU'), pk_seq from KHUVUC where PK_SEQ in (" + tcDetail.getKvId() + ") ";
						
						if(!db.update(query))
						{
							this.msg = "Khong the tao moi TINHPHUCAP_CHUCVU_KHUVUC, " + query;
							this.db.getConnection().rollback();
							return false;
						}
						
						
						String[] maDetail = tcDetail.getMaDetail();
						String[] noidung = tcDetail.getNoidung();
						String[] trongso = tcDetail.getTrongso();
						String[] tinhtheonc = tcDetail.getTinhtheongaycong();
		
						//lay dkkm current
						String kpiId = "";
						query = "select IDENT_CURRENT('TINHPHUCAP_CHUCVU') as kpiId";
						
						ResultSet rsDkkm = this.db.get(query);						
						rsDkkm.next();
						kpiId = rsDkkm.getString("kpiId");
						rsDkkm.close();
						
						for(int j = 0; j < maDetail.length; j++)
						{
							if (noidung[j].trim().length() > 0 && trongso[j].trim().length() > 0)
							{
								trongso[j] = trongso[j].replaceAll(",", "");
		
								query = "insert TINHPHUCAP_CHUCVU_PHUCAP(tinhphucap_chucvu_fk, ma, noidung, trongso, tinhtheongaycong) " +
										"values('" + kpiId + "', '" + maDetail[j] + "', N'" + noidung[j] + "', '" + trongso[j] + "', '" + tinhtheonc[j] + "')";
								
								if(!db.update(query))
								{
									this.msg = "Khong the tao moi TINHPHUCAP_CHUCVU_PHUCAP, " + query;
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
								query = "delete TINHPHUCAP_CHUCVU_GSBH where tinhphucap_chucvu_fk = '" + kpiId + "' and gsbh_fk = '" + nvIds[count].trim() + "'";
								if(!db.update(query))
								{
									this.msg = "Khong the tao moi TINHPHUCAP_CHUCVU_GSBH, " + query;
									this.db.getConnection().rollback();
									return false;
								}
								
								query = "Insert TINHPHUCAP_CHUCVU_GSBH(tinhphucap_chucvu_fk, gsbh_fk) values('" + kpiId + "', '" + nvIds[count].trim() + "')";
								if(!db.update(query))
								{
									this.msg = "Khong the tao moi TINHPHUCAP_CHUCVU_GSBH, " + query;
									this.db.getConnection().rollback();
									return false;
								}
								
							}
							else   //SR
							{
								query = "delete TINHPHUCAP_CHUCVU_DDKD where tinhphucap_chucvu_fk = '" + kpiId + "' and ddkd_fk = '" + nvIds[count].trim() + "'";
								if(!db.update(query))
								{
									this.msg = "Khong the tao moi TINHPHUCAP_CHUCVU_DDKD, " + query;
									this.db.getConnection().rollback();
									return false;
								}
								
								query = "Insert TINHPHUCAP_CHUCVU_DDKD(tinhphucap_chucvu_fk, ddkd_fk) values('" + kpiId + "', '" + nvIds[count].trim() + "')";
								if(!db.update(query))
								{
									this.msg = "Khong the tao moi TINHPHUCAP_CHUCVU_DDKD, " + query;
									this.db.getConnection().rollback();
									return false;
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
		String query = "select thang, nam, diengiai from TinhPhuCap where pk_seq = '" + this.id + "'";
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
		 List<ITinhphucapDetail> tnDetaiList = new ArrayList<ITinhphucapDetail>();
		 
		 String query = "select pk_seq, thang, nam, dvkd_fk, kbh_fk, KHUVUC_fk, chucvu " +
		 				"from TINHPHUCAP_CHUCVU where tinhphucap_fk = '" + this.id + "' ";
		 
		 System.out.println("1.Tinh phu cap init: " + query);
		 
		 ResultSet rs = db.get(query);
		 if(rs != null)
		 {
			 try 
			 {
				 ITinhphucapDetail detail = null;
				 int stt = 0;
				 while(rs.next())
				 {
					 String pk_seq = rs.getString("pk_seq");
					 String dvkd_fk = rs.getString("dvkd_fk");
					 String kbh_fk = rs.getString("kbh_fk");
					 //String khuvuc_fk = rs.getString("khuvuc_fk");
					 String chucvu = rs.getString("chucvu");
					
					 detail = new TinhphucapDetail();
		    			
					detail.setId(pk_seq);
	    			detail.setDvkdId(dvkd_fk);
	    			detail.setKbhId(kbh_fk);
	    			//detail.setKvId(khuvuc_fk);
	    			detail.setChucvu(chucvu);
	    			
	    			String[] maDetail = new String[]{"TC01", "TC02", "TC03", "TC04", "TC05", "TC06", "TC07", "TC08", "TC09", "TC10"};
	    			String[] noidung = new String[]{"", "", "", "", "", "", "", "", "", ""};
	    			String[] trongso = new String[]{"", "", "", "", "", "", "", "", "", ""};
	    			String[] tinhtheongaycong = new String[]{"0", "0", "0", "0", "0", "0", "0", "0", "0", "0"};
	    			
	    			query = "select ma, noidung, trongso, tinhtheongaycong " +
	    					"from TINHPHUCAP_CHUCVU_PHUCAP where TINHPHUCAP_CHUCVU_FK = '" + pk_seq + "'";
	    			
	    			System.out.println("2.Khoi tao Detail: " + query);
	    			
	    			ResultSet rsDetail = db.get(query);
	    			if(rsDetail != null)
	    			{
	    				int count = 0;
	    				while(rsDetail.next())
	    				{
	    					maDetail[count] = rsDetail.getString("ma");
	    					noidung[count] = rsDetail.getString("noidung");
	    					trongso[count] = rsDetail.getString("trongso");
	    					tinhtheongaycong[count] = rsDetail.getString("tinhtheongaycong");
	    					
	    					//System.out.println("333.Toi dang vao day: " + maDetail[count] + " - Noi dung: " + noidung[count] + " -- Trong So: " + trongso[count]);
	    					count++;
	    				}
	    				rsDetail.close();
	    			}
	    			
	    			detail.setMaDetail(maDetail);
	    			detail.setNoidung(noidung);
	    			detail.setTrongso(trongso);
	    			detail.setTinhtheongaycong(tinhtheongaycong);
	    			
	    			//khoi tao khu vuc
	    			query = "select khuvuc_fk from TINHPHUCAP_CHUCVU_KHUVUC where tinhphucap_chucvu_fk = '" + pk_seq + "'";
	    			
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
	    				query = "select gsbh_fk as nvId from TINHPHUCAP_CHUCVU_GSBH where tinhphucap_chucvu_fk = '" + pk_seq + "'";
	    			}
	    			else
	    			{
	    				query = "select ddkd_fk as nvId from TINHPHUCAP_CHUCVU_DDKD where tinhphucap_chucvu_fk = '" + pk_seq + "'";
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

	public List<ITinhphucapDetail> getTieuchiDetail() 
	{
		return this.tcDetailList;
	}

	public void setTieuchiDetail(List<ITinhphucapDetail> tcDetail) 
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
