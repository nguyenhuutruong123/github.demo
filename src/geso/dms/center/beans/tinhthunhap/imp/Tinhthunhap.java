package geso.dms.center.beans.tinhthunhap.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import geso.dms.center.beans.tinhthunhap.*;
import geso.dms.center.db.sql.dbutils;

public class Tinhthunhap implements ITinhthunhap
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
	
	List<ITinhthunhapDetail> tcDetailList;
	
	dbutils db;
	
	public Tinhthunhap()
	{
		this.userId = "";
		this.id = "";
		this.thang = "";
		this.nam = "";
		this.trangthai = "";
		this.tenkhoa = "";
		this.diengiai = "";
		this.msg = "";
		
		this.tcDetailList = new ArrayList<ITinhthunhapDetail>();
		
		this.db = new dbutils();
	}
	
	public Tinhthunhap(String id)
	{
		this.userId = "";
		
		this.thang = "";
		this.nam = "";
		this.id = id;
		this.trangthai = "";
		this.tenkhoa = "";
		this.diengiai = "";
		this.msg = "";
		
		this.tcDetailList = new ArrayList<ITinhthunhapDetail>();
		
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
			//Kiem tra tong trong so co dat 100% khong
			if(this.tcDetailList.size() <= 0)
			{
				this.msg = "Bạn phải nhập tiêu chí của tính thu nhập trong tháng";
				return false;
			}			
			//Check thang nay da co TIEUCHIDANHGIA
			String sql = "select count(*) as sodong from TINHTHUNHAP where thang = '" + this.thang + "' and nam = '" + this.nam + "' and trangthai != 2 ";
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
				this.msg = "Tháng " + this.thang + ", Năm " + this.nam + " đã thiết lập quy trình tính thu nhập, bạn chỉ có thể cập nhật";
				return false;
			}
			db.getConnection().setAutoCommit(false);
			String query = "insert TINHTHUNHAP(thang, nam, diengiai, trangthai, nguoitao, nguoisua, ngaytao, ngaysua) " +
							"values('" + this.thang + "', '" + this.nam + "', N'" + this.diengiai + "', '0', '" + this.userId + "', '" + this.userId + "', '" + getDateTime() + "', '" + getDateTime() + "')";
			
			if(!db.update(query))
			{
				this.msg = "Khong the tao moi TINHTHUNHAP, " + query;
				this.db.getConnection().rollback();
				return false;
			}
			//lay dkkm current
			String khlCurrent = "";
			query = "select IDENT_CURRENT('TINHTHUNHAP') as tcdgId";
			
			ResultSet rsDkkm = this.db.get(query);						
			rsDkkm.next();
			khlCurrent = rsDkkm.getString("tcdgId");
			rsDkkm.close();
			
			if(this.tcDetailList.size() > 0)
			{
				for(int i = 0; i < this.tcDetailList.size(); i++)
				{
					ITinhthunhapDetail tcDetail = this.tcDetailList.get(i);
					if(tcDetail.getKvId().trim().length() > 0)
					{
							//Phai xoa TrongSoKPI neu da ton tai
							if(tcDetail.getChucvu().equals("SR")&&tcDetail.GetNppId().trim().length()>0)
								query = "delete trongsokpi_chitiet where trongsokpi_fk in ( select pk_seq from TrongSoKpi kpi inner join TrongSoKPI_NPP NPP ON NPP.TRONGSOKPI_FK=KPI.PK_SEQ where KPI.tinhthunhap_fk = '" + khlCurrent + "'  and ChucVu = '" + tcDetail.getChucvu() + "' AND NPP.NPP_FK IN ("+tcDetail.GetNppId()+" ) ) ";
							else if(tcDetail.getChucvu().equals("SS") &&tcDetail.getKvId().trim().length()>0)
								query = "delete trongsokpi_chitiet where trongsokpi_fk in ( select pk_seq from TrongSoKpi kpi inner join TrongSoKPI_KHUVUC KV on KV.TRONGSOKPI_FK=kpi.pk_seq  where KPI.tinhthunhap_fk = '" + khlCurrent + "'  and ChucVu = '" + tcDetail.getChucvu() + "' and kv.KHUVUC_FK IN("+tcDetail.getKvId()+") )";
							if(!db.update(query))
							{
								this.msg = "Khong the tao moi trongsokpi_chitiet, " + query;
								this.db.getConnection().rollback();
								return false;
							}
							System.out.println("Xoa TrongSoKPI ChiTiet_"+query);
							if(tcDetail.getChucvu().equals("SR")&&tcDetail.GetNppId().trim().length()>0)
								query = "delete TrongSoKpi from TrongSoKpi kpi inner join TrongSoKPI_NPP NPP ON NPP.TRONGSOKPI_FK=KPI.PK_SEQ where KPI.tinhthunhap_fk = '" + khlCurrent + "'  and ChucVu = '" + tcDetail.getChucvu() + "' AND NPP.NPP_FK IN ("+tcDetail.GetNppId()+" )  ";
							else if(tcDetail.getChucvu().equals("SS") &&tcDetail.getKvId().trim().length()>0)
								query = "delete TrongSoKpi from TrongSoKpi kpi inner join TrongSoKPI_KHUVUC KV on KV.TRONGSOKPI_FK=kpi.pk_seq  where KPI.tinhthunhap_fk = '" + khlCurrent + "'  and ChucVu = '" + tcDetail.getChucvu() + "' and kv.KHUVUC_FK IN("+tcDetail.getKvId()+") ";
							if(query.length()>0)
							{	
								if(!db.update(query))
								{
									this.msg = "Khong the tao moi TrongSoKpi, " + query;
									this.db.getConnection().rollback();
									return false;
								}
							}
							System.out.println("Xoa TrongSoKpi___"+query);
							
							query = "insert TrongSoKpi(thang, nam, dvkd_fk, kbh_fk, luongcoban, ptluongtg, ptluonghieuqua, baohiemtu, baohiemden, chucvu, baohiem, congdoan, tdnc, tinhthunhap_fk)" +
									" values('" + this.thang + "', '" + this.nam + "', '" + tcDetail.getDvkdId() + "', '" + tcDetail.getKbhId() + "'," +
											" '" + tcDetail.getLuongCB() + "', '" + tcDetail.getPhantramluongTG() + "', '" + tcDetail.getPhantramluongHQ() + "'," +
													" '" + tcDetail.getBaohiemtu() + "', '" + tcDetail.getBaohiemDen() + "', '" + tcDetail.getChucvu() + "', '" + tcDetail.getBaohiem() + "', '" + tcDetail.getCongdoan() + "', '" + tcDetail.getThucdatngaycong() + "', '" + khlCurrent + "') ";
							if(!db.update(query))
							{
								this.msg = "Khong the tao moi TrongSoKpi, " + query;
								this.db.getConnection().rollback();
								return false;
							}
							String[] maDetail = tcDetail.getMaDetail();
							String[] noidung = tcDetail.getNoidung();
							String[] trongso = tcDetail.getTrongso();
							String[] mucbaohiem = tcDetail.getMucbaohiem();
							String[] thuongvuotmuc = tcDetail.getThuongSRvuotmuc();
							
							//lay dkkm current
							String kpiId = "";
							query = "select IDENT_CURRENT('TrongSoKpi') as kpiId";
							
							rsDkkm = this.db.get(query);						
							rsDkkm.next();
							kpiId = rsDkkm.getString("kpiId");
							rsDkkm.close();
							
							
							query="INSERT INTO TRONGSOKPI_KHUVUC(TinhThuNhap_FK,TRONGSOKPI_FK,KHUVUC_FK) " +
									"select '"+khlCurrent+"','"+kpiId+"' as kpiId,PK_SEQ " +
									"FROM KhuVuc Where pk_seq in ("+tcDetail.getKvId().trim() +" )";
							if(!db.update(query))
							{
								this.msg = "Khong the tao moi TrongSoKpi, " + query;
								this.db.getConnection().rollback();
								return false;
							}
							
							if(tcDetail.getChucvu().equals("SR")&&tcDetail.GetNppId().trim().length()>0)
							{
								query="INSERT INTO TRONGSOKPI_NPP(TinhThuNhap_FK,TrongSoKPI_FK,NPP_FK) " +
									"select '"+khlCurrent+"','"+kpiId+"' as kpiId,PK_SEQ " +
										" FROM NHAPHANPHOI WHERE PK_SEQ in ("+tcDetail.GetNppId()+")";
								if(!db.update(query))
								{
									this.msg = "Khong the tao moi TrongSoKpi, " + query;
									this.db.getConnection().rollback();
									return false;
								}
							}
							//Thuong vuot muc co tu muc --> den muc
							List<IThuongvuotmuc> tvmList = tcDetail.getThuongvmList();
							if(tvmList.size() > 0)
							{
								for(int ii = 0; ii < tvmList.size(); ii++)
								{
									IThuongvuotmuc tvm = tvmList.get(ii);
									
									query = "";
									if(tvm.getNhomthuong().trim().length() > 0)
									{
										String nt_fk = "";
										if(tvm.getNhomthuong().trim().indexOf("] - ") > 0)
										{
											nt_fk = tvm.getNhomthuong().substring(1, tvm.getNhomthuong().indexOf("] - "));
											
											query = "insert TinhThuNhap_ThuongVuotMuc(trongsokpi_fk, nhomthuong_fk, tumuc, denmuc, thuong) " +
													"values('" + kpiId + "', '" + nt_fk + "', '" + tvm.getTumuc() + "', '" + tvm.getDenmuc() + "', '" + tvm.getThuong() + "')";
										}
									}
									else
									{
										query = "insert TinhThuNhap_ThuongVuotMuc(trongsokpi_fk, nhomthuong_fk, tumuc, denmuc, thuong) " +
												"values('" + kpiId + "', null, '" + tvm.getTumuc() + "', '" + tvm.getDenmuc() + "', '" + tvm.getThuong() + "')";
									}
									
									if(query.trim().length() > 0)
									{
										if(!db.update(query))
										{
											this.msg = "Khong the tao moi TinhThuNhap_ThuongVuotMuc, " + query;
											this.db.getConnection().rollback();
											return false;
										}
									}
									
								}
							}
							
							
							for(int j = 0; j < maDetail.length; j++)
							{
								String nhomthuong_fk = "null";
								
								if(noidung[j].indexOf("] - ") > 0)
									nhomthuong_fk = noidung[j].substring(1, noidung[j].indexOf("] - "));
								
								if(thuongvuotmuc[j].trim().length() <= 0)
									thuongvuotmuc[j] = "0";
								
								if(mucbaohiem[j].trim().length() <= 0)
									mucbaohiem[j] = "0";
								
								/*if(Double.parseDouble(tcDetail.getBaohiemDen()) > 0)
									mucbaohiem[j] = "0";*/
								
								trongso[j] = trongso[j].replaceAll(",", "");
								mucbaohiem[j] = mucbaohiem[j].replaceAll(",", "");
								thuongvuotmuc[j] = thuongvuotmuc[j].replaceAll(",", "");
								
								query = "insert trongsokpi_chitiet(trongsokpi_fk, ma, nhomthuong_fk, noidung, trongso, mucbaohiem, thuongvuotmuc) " +
										"values('" + kpiId + "', '" + maDetail[j] + "', " + nhomthuong_fk + ", N'" + noidung[j] + "', '" + trongso[j] + "', '" + mucbaohiem[j] + "', '" + thuongvuotmuc[j] + "')";
								
								if(!db.update(query))
								{
									this.msg = "Khong the tao moi trongsokpi_chitiet, " + query;
									this.db.getConnection().rollback();
									return false;
								}
							}
					//Luong tinh theo GSBH + DDKD
					List<INhanvien> nvList = tcDetail.getNhanvienList();
					for(int ii = 0; ii < nvList.size(); ii++)
					{
						INhanvien nv = nvList.get(ii);
						if(tcDetail.getChucvu().equals("SS"))  //SS
						{
							//Delete neu khu vuc nay da co luong cua GSBH
							query = "delete TinhThuNhap_GSBH where tinhthunhap_fk = '" + khlCurrent + "' and gsbh_fk = '" + nv.getId() + "'";
							
							if(!db.update(query))
							{
								this.msg = "Khong the tao moi TinhThuNhap_GSBH, " + query;
								this.db.getConnection().rollback();
								return false;
							}
							
							
							query = "Insert TinhThuNhap_GSBH(TrongSoKPI_FK,tinhthunhap_fk, gsbh_fk, luongcb ) values ('"+kpiId+"','" + khlCurrent + "', '" + nv.getId() + "', '" + nv.getLuongCB().replaceAll(",", "") + "') ";
							
							if(!db.update(query))
							{
								this.msg = "Khong the tao moi TinhThuNhap_GSBH, " + query;
								this.db.getConnection().rollback();
								return false;
							}
						}
						else //SR
						{
							query = "delete TinhThuNhap_DDKD where tinhthunhap_fk = '" + khlCurrent + "' and ddkd_fk = '" + nv.getId() + "'";
							
							if(!db.update(query))
							{
								this.msg = "Khong the tao moi TinhThuNhap_GSBH, " + query;
								this.db.getConnection().rollback();
								return false;
							}
							query = "Insert TinhThuNhap_DDKD(TrongSoKPI_FK, tinhthunhap_fk, ddkd_fk, luongcb ) values ('"+kpiId+"','" + khlCurrent + "', '" + nv.getId() + "', '" + nv.getLuongCB().replaceAll(",", "") + "') ";
							if(!db.update(query))
							{
								this.msg = "Khong the tao moi TinhThuNhap_DDKD, " + query;
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
				this.msg = "Bạn phải nhập tiêu chí của tính thu nhập trong tháng";
				return false;
			}
			
			//Check thang nay da co TIEUCHIDANHGIA
			String sql = "select count(*) as sodong from TINHTHUNHAP where thang = '" + this.thang + "' and nam = '" + this.nam + "' and trangthai != 2 and pk_seq != '" + this.id + "' ";
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
				this.msg = "Tháng " + this.thang + ", Năm " + this.nam + " đã thiết lập quy trình tính thu nhập, bạn không thể cập nhật";
				return false;
			}
			
			db.getConnection().setAutoCommit(false);
			
			String query = "update TinhThuNhap set thang = '" + this.thang + "', nam = '" + this.nam + "', " +
								"diengiai = N'" + this.diengiai + "', nguoisua = '" + this.userId + "', ngaysua = '" + getDateTime() + "' where pk_seq = '" + this.id + "' ";
			
			System.out.println("1.Buoc 1: " + query);
			
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat TinhThuNhap, " + query;
				this.db.getConnection().rollback();
				return false;
			}
			
			query = "delete TrongSoKpi_ChiTiet where trongsokpi_fk in( select pk_seq from trongsokpi where tinhthunhap_fk = '" + this.id + "')";
			
			System.out.println("2.Buoc 2: " + query);
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat TrongSoKpi_ChiTiet, " + query;
				this.db.getConnection().rollback();
				return false;
			}
			
			query = "delete TinhThuNhap_ThuongVuotMuc where trongsokpi_fk in ( select pk_seq from TrongSoKpi where tinhthunhap_fk = '" + this.id + "' ) ";
			System.out.println("3.Buoc 3: " + query);
			if(!db.update(query))
			{
				this.msg = "Khong the tao moi TinhThuNhap_ThuongVuotMuc, " + query;
				this.db.getConnection().rollback();
				return false;
			}
			
			query = "delete trongsokpi where tinhthunhap_fk = '" + this.id + "'";
			
			System.out.println("4.Buoc 4: " + query);
			
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat trongsokpi, " + query;
				this.db.getConnection().rollback();
				return false;
			}
			
			//Delete Tong
			query = "delete TinhThuNhap_GSBH where tinhthunhap_fk = '" + this.id + "'";
			
			System.out.println("5.Buoc 5: " + query);
			
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat TinhThuNhap_GSBH, " + query;
				this.db.getConnection().rollback();
				return false;
			}
			
			query = "delete TinhThuNhap_DDKD where tinhthunhap_fk = '" + this.id + "'";
			
			System.out.println("6.Buoc 6: " + query);
			
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat TinhThuNhap_DDKD, " + query;
				this.db.getConnection().rollback();
				return false;
			}
			
			
			if(this.tcDetailList.size() > 0)
			{
				for(int i = 0; i < this.tcDetailList.size(); i++)
				{
					ITinhthunhapDetail tcDetail = this.tcDetailList.get(i);
					if(tcDetail.getKvId().trim().length() > 0)
					{
							//Phai xoa TrongSoKPI neu da ton tai
							if(tcDetail.getChucvu().equals("SR")&&tcDetail.GetNppId().trim().length()>0)
								query = "delete trongsokpi_chitiet where trongsokpi_fk in ( select pk_seq from TrongSoKpi kpi inner join TrongSoKPI_NPP NPP ON NPP.TRONGSOKPI_FK=KPI.PK_SEQ where KPI.tinhthunhap_fk = '" + this.id + "'  and ChucVu = '" + tcDetail.getChucvu() + "' AND NPP.NPP_FK IN ("+tcDetail.GetNppId()+" ) ) ";
							else if(tcDetail.getChucvu().equals("SS") &&tcDetail.getKvId().trim().length()>0)
								query = "delete trongsokpi_chitiet where trongsokpi_fk in ( select pk_seq from TrongSoKpi kpi inner join TrongSoKPI_KHUVUC KV on KV.TRONGSOKPI_FK=kpi.pk_seq  where KPI.tinhthunhap_fk = '" + this.id + "'  and ChucVu = '" + tcDetail.getChucvu() + "' and kv.KHUVUC_FK IN("+tcDetail.getKvId()+") )";
							if(!db.update(query))
							{
								this.msg = "Khong the tao moi trongsokpi_chitiet, " + query;
								this.db.getConnection().rollback();
								return false;
							}
							System.out.println("Xoa TrongSoKPI ChiTiet_"+query);
							if(tcDetail.getChucvu().equals("SR")&&tcDetail.GetNppId().trim().length()>0)
								query = "delete TrongSoKpi from TrongSoKpi kpi inner join TrongSoKPI_NPP NPP ON NPP.TRONGSOKPI_FK=KPI.PK_SEQ where KPI.tinhthunhap_fk = '" + this.id + "'  and ChucVu = '" + tcDetail.getChucvu() + "' AND NPP.NPP_FK IN ("+tcDetail.GetNppId()+" )  ";
							else if(tcDetail.getChucvu().equals("SS") &&tcDetail.getKvId().trim().length()>0)
								query = "delete TrongSoKpi from TrongSoKpi kpi inner join TrongSoKPI_KHUVUC KV on KV.TRONGSOKPI_FK=kpi.pk_seq  where KPI.tinhthunhap_fk = '" + this.id + "'  and ChucVu = '" + tcDetail.getChucvu() + "' and kv.KHUVUC_FK IN("+tcDetail.getKvId()+") ";
							if(query.length()>0)
							{	
								if(!db.update(query))
								{
									this.msg = "Khong the tao moi TrongSoKpi, " + query;
									this.db.getConnection().rollback();
									return false;
								}
							}
							System.out.println("Xoa TrongSoKpi___"+query);
							
							query = "insert TrongSoKpi(thang, nam, dvkd_fk, kbh_fk, luongcoban, ptluongtg, ptluonghieuqua, baohiemtu, baohiemden, chucvu, baohiem, congdoan, tdnc, tinhthunhap_fk)" +
									" values('" + this.thang + "', '" + this.nam + "', '" + tcDetail.getDvkdId() + "', '" + tcDetail.getKbhId() + "'," +
											" '" + tcDetail.getLuongCB() + "', '" + tcDetail.getPhantramluongTG() + "', '" + tcDetail.getPhantramluongHQ() + "'," +
													" '" + tcDetail.getBaohiemtu() + "', '" + tcDetail.getBaohiemDen() + "', '" + tcDetail.getChucvu() + "', '" + tcDetail.getBaohiem() + "', '" + tcDetail.getCongdoan() + "', '" + tcDetail.getThucdatngaycong() + "', '" + this.id + "') ";
							if(!db.update(query))
							{
								this.msg = "Khong the tao moi TrongSoKpi, " + query;
								this.db.getConnection().rollback();
								return false;
							}
							String[] maDetail = tcDetail.getMaDetail();
							String[] noidung = tcDetail.getNoidung();
							String[] trongso = tcDetail.getTrongso();
							String[] mucbaohiem = tcDetail.getMucbaohiem();
							String[] thuongvuotmuc = tcDetail.getThuongSRvuotmuc();
							
							//lay dkkm current
							String kpiId = "";
							query = "select IDENT_CURRENT('TrongSoKpi') as kpiId";
							
							ResultSet rsDkkm = this.db.get(query);						
							rsDkkm.next();
							kpiId = rsDkkm.getString("kpiId");
							rsDkkm.close();
							
							
							query="INSERT INTO TRONGSOKPI_KHUVUC(TinhThuNhap_FK,TRONGSOKPI_FK,KHUVUC_FK) " +
									"select '"+this.id+"','"+kpiId+"' as kpiId,PK_SEQ " +
									"FROM KhuVuc Where pk_seq in ("+tcDetail.getKvId().trim() +" )";
							if(!db.update(query))
							{
								this.msg = "Khong the tao moi TrongSoKpi, " + query;
								this.db.getConnection().rollback();
								return false;
							}
							
							if(tcDetail.getChucvu().equals("SR")&&tcDetail.GetNppId().trim().length()>0)
							{
								query="INSERT INTO TRONGSOKPI_NPP(TinhThuNhap_FK,TrongSoKPI_FK,NPP_FK) " +
									"select '"+this.id+"','"+kpiId+"' as kpiId,PK_SEQ " +
										" FROM NHAPHANPHOI WHERE PK_SEQ in ("+tcDetail.GetNppId()+")";
								if(!db.update(query))
								{
									this.msg = "Khong the tao moi TrongSoKpi, " + query;
									this.db.getConnection().rollback();
									return false;
								}
							}
							//Thuong vuot muc co tu muc --> den muc
							List<IThuongvuotmuc> tvmList = tcDetail.getThuongvmList();
							if(tvmList.size() > 0)
							{
								for(int ii = 0; ii < tvmList.size(); ii++)
								{
									IThuongvuotmuc tvm = tvmList.get(ii);
									
									query = "";
									if(tvm.getNhomthuong().trim().length() > 0)
									{
										String nt_fk = "";
										if(tvm.getNhomthuong().trim().indexOf("] - ") > 0)
										{
											nt_fk = tvm.getNhomthuong().substring(1, tvm.getNhomthuong().indexOf("] - "));
											
											query = "insert TinhThuNhap_ThuongVuotMuc(trongsokpi_fk, nhomthuong_fk, tumuc, denmuc, thuong) " +
													"values('" + kpiId + "', '" + nt_fk + "', '" + tvm.getTumuc() + "', '" + tvm.getDenmuc() + "', '" + tvm.getThuong() + "')";
										}
									}
									else
									{
										query = "insert TinhThuNhap_ThuongVuotMuc(trongsokpi_fk, nhomthuong_fk, tumuc, denmuc, thuong) " +
												"values('" + kpiId + "', null, '" + tvm.getTumuc() + "', '" + tvm.getDenmuc() + "', '" + tvm.getThuong() + "')";
									}
									
									if(query.trim().length() > 0)
									{
										if(!db.update(query))
										{
											this.msg = "Khong the tao moi TinhThuNhap_ThuongVuotMuc, " + query;
											this.db.getConnection().rollback();
											return false;
										}
									}
									
								}
							}
							
							
							for(int j = 0; j < maDetail.length; j++)
							{
								String nhomthuong_fk = "null";
								
								if(noidung[j].indexOf("] - ") > 0)
									nhomthuong_fk = noidung[j].substring(1, noidung[j].indexOf("] - "));
								
								if(thuongvuotmuc[j].trim().length() <= 0)
									thuongvuotmuc[j] = "0";
								
								if(mucbaohiem[j].trim().length() <= 0)
									mucbaohiem[j] = "0";
								
								/*if(Double.parseDouble(tcDetail.getBaohiemDen()) > 0)
									mucbaohiem[j] = "0";*/
								
								trongso[j] = trongso[j].replaceAll(",", "");
								mucbaohiem[j] = mucbaohiem[j].replaceAll(",", "");
								thuongvuotmuc[j] = thuongvuotmuc[j].replaceAll(",", "");
								
								query = "insert trongsokpi_chitiet(trongsokpi_fk, ma, nhomthuong_fk, noidung, trongso, mucbaohiem, thuongvuotmuc) " +
										"values('" + kpiId + "', '" + maDetail[j] + "', " + nhomthuong_fk + ", N'" + noidung[j] + "', '" + trongso[j] + "', '" + mucbaohiem[j] + "', '" + thuongvuotmuc[j] + "')";
								
								if(!db.update(query))
								{
									this.msg = "Khong the tao moi trongsokpi_chitiet, " + query;
									this.db.getConnection().rollback();
									return false;
								}
							}
					//Luong tinh theo GSBH + DDKD
					List<INhanvien> nvList = tcDetail.getNhanvienList();
					for(int ii = 0; ii < nvList.size(); ii++)
					{
						INhanvien nv = nvList.get(ii);
						if(tcDetail.getChucvu().equals("SS"))  //SS
						{
							//Delete neu khu vuc nay da co luong cua GSBH
							query = "delete TinhThuNhap_GSBH where tinhthunhap_fk = '" + this.id + "' and gsbh_fk = '" + nv.getId() + "'";
							
							if(!db.update(query))
							{
								this.msg = "Khong the tao moi TinhThuNhap_GSBH, " + query;
								this.db.getConnection().rollback();
								return false;
							}
							
							
							query = "Insert TinhThuNhap_GSBH(TrongSoKPI_FK,tinhthunhap_fk, gsbh_fk, luongcb ) values ('"+kpiId+"','" + this.id + "', '" + nv.getId() + "', '" + nv.getLuongCB().replaceAll(",", "") + "') ";
							
							if(!db.update(query))
							{
								this.msg = "Khong the tao moi TinhThuNhap_GSBH, " + query;
								this.db.getConnection().rollback();
								return false;
							}
						}
						else //SR
						{
							query = "delete TinhThuNhap_DDKD where tinhthunhap_fk = '" + this.id + "' and ddkd_fk = '" + nv.getId() + "'";
							
							if(!db.update(query))
							{
								this.msg = "Khong the tao moi TinhThuNhap_GSBH, " + query;
								this.db.getConnection().rollback();
								return false;
							}
							query = "Insert TinhThuNhap_DDKD(TrongSoKPI_FK, tinhthunhap_fk, ddkd_fk, luongcb ) values ('"+kpiId+"','" + this.id + "', '" + nv.getId() + "', '" + nv.getLuongCB().replaceAll(",", "") + "') ";
							if(!db.update(query))
							{
								this.msg = "Khong the tao moi TinhThuNhap_DDKD, " + query;
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


	public void createRs() 
	{
		this.dvkdRs = db.getScrol("select pk_seq, donvikinhdoanh as donvi from donvikinhdoanh where trangthai = '1' ");
		this.kbhRs = db.getScrol("select pk_seq, diengiai from kenhbanhang where trangthai = '1'");
		this.kvRs = db.getScrol("select pk_seq, ten from khuvuc where trangthai = '1' order by ten ");
	}

	public void init() 
	{
		String query = "select thang, nam, diengiai from TinhThuNhap where pk_seq = '" + this.id + "'";
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
		 List<ITinhthunhapDetail> tnDetaiList = new ArrayList<ITinhthunhapDetail>();
		 
		 String query = "select a.pk_seq, a.thang, a.nam, a.dvkd_fk, a.kbh_fk, a.chucvu, a.luongcoban, a.ptluongtg, a.ptluonghieuqua, a.baohiemtu, a.baohiemden, a.baohiem, a.congdoan, a.tdnc " +
		 				"from trongsokpi a where tinhthunhap_fk = '" + this.id + "' ";
		 ResultSet rs = db.get(query);
		 if(rs != null)
		 {
			 try 
			 {
				 ITinhthunhapDetail detail = null;
				 while(rs.next())
				 {
					 String pk_seq = rs.getString("pk_seq");
					 String dvkd_fk = rs.getString("dvkd_fk");
					 String kbh_fk = rs.getString("kbh_fk");
					 String khuvuc_fk = "";
					 String NppId="";
					 query="select kv.KHUVUC_FK as kvId,kpi.PK_SEQ as kpiId from TRONGSOKPI kpi inner join "+
						 "TRONGSOKPI_KhuVuc kv on kv.TRONGSOKPI_FK=kpi.PK_SEQ and kv.TINHTHUNHAP_FK=kpi.tinhthunhap_fk " +
						 " where kpi.TinhThuNhap_FK='"+this.id+"' and kpi.PK_SEQ='"+pk_seq+"'  ";
					 
					 ResultSet rsKv =this.db.get(query);
					 if(rsKv!=null)
					 {	
						while(rsKv.next())
						{
							khuvuc_fk+=rsKv.getString("kvId")+",";
						}
						 
					 }rsKv.close();rsKv=null;
					 System.out.println("__KhuVuc__"+query);
					if(khuvuc_fk.trim().length()>0)
						khuvuc_fk=khuvuc_fk.substring(0,khuvuc_fk.length()-1);
					
					query="select npp.TRONGSOKPI_FK as TrongSoId,Isnull(npp.NPP_FK,'-1') as NppId "+ 
						 "from TRONGSOKPI kpi inner join TRONGSOKPI_NPP npp on npp.TRONGSOKPI_FK=kpi.PK_SEQ "+
						"where kpi.PK_SEQ='"+pk_seq+"' and kpi.tinhthunhap_fk='"+this.id+"'";
					rsKv=this.db.get(query);
					 if(rsKv!=null)
					 {	
						while(rsKv.next())
						{
							NppId+=rsKv.getString("NppId")+",";
						}
						 
					 }rsKv.close();rsKv=null;
					System.out.println("__NppId__"+query);
					 if(NppId.trim().length()>0)
						 NppId=NppId.substring(0,NppId.length()-1);
					 
					 String chucvu = rs.getString("chucvu");
					 String luongcoban = rs.getString("luongcoban");
					 String ptluongtg = rs.getString("ptluongtg");
					 String ptluonghieuqua = rs.getString("ptluonghieuqua");
					 String baohiemtu = rs.getString("baohiemtu");
					 String baohiemden = rs.getString("baohiemden");
					 String baohiem = rs.getString("baohiem");
					 String congdoan = rs.getString("congdoan");
					 String tdnc = rs.getString("tdnc");
					 String kvTen ="";
					 
					 detail = new TinhthunhapDetail();
		    			
					detail.setId(pk_seq);
	    			detail.setDvkdId(dvkd_fk);
	    			detail.setKbhId(kbh_fk);
	    			detail.setKvId(khuvuc_fk);
	    			detail.setNppId(NppId);
	    			detail.setNppSelected(NppId);
	    			detail.setChucvu(chucvu);
	    			detail.setLuongCB(luongcoban);
	    			detail.setPhantramluongTG(ptluongtg);
	    			detail.setPhantramluongHQ(ptluonghieuqua);
	    			detail.setBaohiemtu(baohiemtu);
	    			detail.setBaohiemDen(baohiemden);
	    			detail.setBaohiem(baohiem);
	    			detail.setCongdoan(congdoan);
	    			detail.setThucdatngaycong(tdnc);
	    			detail.setKvTenSelected(kvTen);
	    			
	    			String[] maDetail = new String[4];
	    			String[] noidung = new String[4];
	    			String[] trongso = new String[4];
	    			String[] mucbaohiem = new String[4];
	    			String[] thuongvuotmuc = new String[4];
	    			
	    			query = "select ma, isnull(noidung,'')as NoiDung,isnull( trongso,0) as TrongSo, isnull(mucbaohiem, 0) as mucbaohiem, " +
	    					"thuongvuotmuc from trongsokpi_chitiet where trongsokpi_fk = '" + pk_seq + "'";
	    			ResultSet rsDetail = db.get(query);
	    			if(rsDetail != null)
	    			{
	    				int count = 0;
	    				while(rsDetail.next())
	    				{
	    					maDetail[count] = rsDetail.getString("ma");
	    					noidung[count] = rsDetail.getString("noidung");
	    					trongso[count] = rsDetail.getString("trongso");
	    					mucbaohiem[count] = rsDetail.getString("mucbaohiem");
	    					thuongvuotmuc[count] = rsDetail.getString("thuongvuotmuc");
	    					count++;
	    				}
	    				rsDetail.close();
	    			}
	    			
	    			detail.setMaDetail(maDetail);
	    			detail.setNoidung(noidung);
	    			detail.setTrongso(trongso);
	    			detail.setMucbaohiem(mucbaohiem);
	    			detail.setThuongSRvuotmuc(thuongvuotmuc);
	    			
	    			
	    			//Nhan vien
	    			List<INhanvien> nvList = new ArrayList<INhanvien>();
	    			if(chucvu.equals("SS"))
	    			{
	    				query = "select '' as nppTen, b.PK_SEQ, b.TEN, a.luongcb  " +
	    						"from TinhThuNhap_GSBH a inner join GIAMSATBANHANG b on a.gsbh_fk = b.PK_SEQ  " +
	    						"where a.tinhthunhap_fk = '" + this.id + "' and a.TrongSoKPI_FK="+pk_seq+"";
	    			}
	    			else
	    			{
	    				query = "select c.ten as nppTen, b.PK_SEQ, b.TEN, a.luongcb  " +
	    						"from TinhThuNhap_DDKD a inner join DAIDIENKINHDOANH b on a.ddkd_fk = b.PK_SEQ inner join NhaPhanPhoi c on b.npp_fk = c.pk_seq  " +
	    						"where a.tinhthunhap_fk = '" + this.id + "' and a.TrongSoKPI_FK="+pk_seq+"";
	    			}
	    			
	    			ResultSet rsNv = db.get(query);
	    			if(rsNv != null)
	    			{
	    				try 
	    				{
	    					INhanvien nv = null;
	    					
	    					while(rsNv.next())
	    					{
	    						nv = new Nhanvien();
	    						
	    						nv.setNppTen(rsNv.getString("nppTen"));
	    						nv.setId(rsNv.getString("PK_SEQ"));
	    						nv.setTen(rsNv.getString("TEN"));
	    						nv.setLuongCB(rsNv.getString("luongcb"));
	    						
	    						nvList.add(nv);
	    					}
	    					rsNv.close();
	    				} 
	    				catch (Exception e) {}
	    			}
	    			
	    			detail.setNhanvienList(nvList);
	    			
	    			
	    			//Thuong vuot muc
	    			List<IThuongvuotmuc> tvmList = new ArrayList<IThuongvuotmuc>();
	    			query = "select case when a.nhomthuong_fk is not null then '[' +  CAST( nhomthuong_fk as varchar(10)) + '] - ' + b.TEN else '' end as nhomTen, tumuc, denmuc, thuong   " +
	    					"from TinhThuNhap_ThuongVuotMuc a left join NHOMTHUONG b on a.nhomthuong_fk = b.PK_SEQ   " +
	    					"where trongsokpi_fk = '" + pk_seq + "'";
	    			
	    			ResultSet rsTvm = db.get(query);
	    			if(rsTvm != null)
	    			{
	    				try 
	    				{
	    					IThuongvuotmuc tvm = null;
	    					
	    					while(rsTvm.next())
	    					{
	    						tvm = new Thuongvuotmuc();
	    						tvm.setNhomthuong(rsTvm.getString("nhomTen").trim());
	    						tvm.setTumuc(rsTvm.getString("tumuc").trim());
	    						tvm.setDenmuc(rsTvm.getString("denmuc").trim());
	    						tvm.setThuong(rsTvm.getString("thuong").trim());
	    						
	    						tvmList.add(tvm);
	    					}
	    					rsTvm.close();
	    				} 
	    				catch (Exception e) {e.printStackTrace();}
	    			}
	    			
	    			
	    			detail.setThuongvuotmucList(tvmList);
	    			/***********************************************Nha Phan Phoi************************************************************/
	    			List<INhaPhanPhoi> nppList = new ArrayList<INhaPhanPhoi>();
	    			query="select n.PK_SEQ,n.TEN as nppTen,k.PK_SEQ  as TrongSoId  from TRONGSOKPI_NPP t "+
	    					"inner join TRONGSOKPI k on k.PK_SEQ=t.TRONGSOKPI_FK "+
	    					"inner join NHAPHANPHOI n on n.PK_SEQ=t.NPP_FK " +
	    					"where k.pk_seq='"+pk_seq+"' and k.TinhThuNhap_FK='"+this.id+"'";
	    			ResultSet rsNpp=db.get(query);
					if(rsNpp != null)
					{
						try 
						{
							INhaPhanPhoi npp = null;
							while(rsNpp.next())
							{
								npp = new NhaPhanPhoi();
								npp.setTen(rsNpp.getString("nppTen"));
								npp.setId(rsNpp.getString("PK_SEQ"));
								nppList.add(npp);
							}
							rsNpp.close();
							rsNpp=null;
						} 
						catch (Exception e) {e.printStackTrace();}
					}
	    			detail.setNhanPhanPhoiList(nppList);
	  /***********************************************Nha Phan Phoi************************************************************/
	    			tnDetaiList.add(detail);
				 }
				 rs.close();
			} 
			catch (Exception e) 
			{
				System.out.println("115.Error: " + e.getMessage());
				e.printStackTrace();
			}
		 }
		 
		 System.out.println("__So Tieu chi: " + tnDetaiList.size());
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

	public List<ITinhthunhapDetail> getTieuchiDetail() 
	{
		return this.tcDetailList;
	}

	public void setTieuchiDetail(List<ITinhthunhapDetail> tcDetail) 
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
