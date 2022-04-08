package geso.dms.distributor.beans.donhangchoxuly.imp;

import geso.dms.center.db.sql.dbutils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;

public class XLTrungbay
{
	private String donhangId;
	
	String userId;
	String nppId;
	String nppTen;
	String sitecode;
	String khId;
	String kenhId;
	String khoId;
	String ngaydh;
	String Msg;	
	dbutils db;	
	
	Hashtable<String, Integer> cotrungbay;	
	
	String cttbId;
	
	String coTrungBay = "";
		
	
	ResultSet cttbList;
	
	public Hashtable<String, Integer> getTrungbay() {		
		return this.cotrungbay;
	}
	
	public void setTrungbay(Hashtable<String, Integer> cotrungbay) {		
		this.cotrungbay = cotrungbay;
	}
	
	public void setMsg(String Msg)
	{
		this.Msg = Msg;
	}
	
	public String getMsg()
	{
		return this.Msg;
	}
	
	public String getUserId()
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;
	}
	
	
	public void setCttbId(String cttbid)
	{
		this.cttbId = cttbid;
	}
	
	public String getCttbId()
	{
		return this.cttbId;
	}
	
	public String getIdDonhang()
	{
		return this.donhangId;
	}
	
	public void setIdDonhang(String dhId)
	{
		this.donhangId = dhId;		
	}
	
	public String getIdkenh()
	{
		return this.kenhId;
	}
	
	public void setIdkenh(String kenhid)
	{
		this.kenhId = kenhid;		
	}
	

	public String getIdKh()
	{
		return this.khId;
	}
	
	public void setIdkh(String khid)
	{
		this.khId = khid;		
	}
	
	public String getNgaydh()
	{
		return this.ngaydh;
	}
	
	public void setNgaydh(String ngaydh)
	{
		this.ngaydh = ngaydh;		
	}
	

	public String getIdkho()
	{
		return this.khoId;
	}
	
	public void setIdkho(String khoid)
	{
		this.khoId = khoid;		
	}
	

	public String getIdnpp()
	{
		return this.nppId;
	}
	
	public void setIdnpp(String nppid)
	{
		this.nppId = nppid;		
	}
	
	public String getnppTen()
	{
		return this.nppTen;
	}
	
	public void setnppTen(String nppten)
	{
		this.nppTen = nppten;		
	}
	
	public void setCttbList(ResultSet cttblist)
	{
		this.cttbList = cttblist;
	}
	
	public ResultSet getCttbList()
	{
		return this.cttbList;
	}
	public XLTrungbay()
	{
		db = new dbutils();
		this.donhangId = "";
		this.khId = "";
		this.nppId = "";
		this.ngaydh = "";
	}
	
	public XLTrungbay(String dhId, String khId, String nppId, String ngaydh, String khoId)
	{
		this.cotrungbay = new Hashtable<String, Integer>();
		db = new dbutils();				
		String sql = "select khachhang.kbh_fk, khachhang.npp_fk, npp.ten from khachhang " +
					 "inner join nhaphanphoi npp on npp.pk_Seq = npp_fk " +
					 "where khachhang.pk_Seq="+khId;
		System.out.println("1.Lay Kenh Trong Xly khuyen Mai" + sql);
		
		ResultSet rs = db.get(sql);
		try
		{
			if(rs.next())
			{
				this.kenhId = rs.getString("kbh_fk");
				//this.nppId = rs.getString("npp_fk");
				this.nppTen = rs.getString("ten");
				System.out.println("nppten : "+this.nppTen);
			}
			rs.close();
		System.out.println("nppId : "+nppId);
		this.ngaydh = ngaydh;
		this.nppId = nppId;
		this.khId = khId;
		this.donhangId = dhId;
		this.khoId = khoId;
		sql=
		"	select a.CTTRUNGBAY_FK as cttbId,  c.DIENGIAI as cttbTen, b.DENGHITRATB_FK, c.scheme, c.NGAYTDS as TuNgay, c.NGAYKTTDS as DenNgay,  "+ 
		"		isnull(b.DAT, 0) as DaDat, b.XUATDUYET - isnull(b.DAT, 0) as NganSachConLai   "+
		"	from DENGHITRATRUNGBAY a inner join DENGHITRATB_KHACHHANG b on a.PK_SEQ = b.DENGHITRATB_FK  "+ 
		"	inner join CTTRUNGBAY c on a.CTTRUNGBAY_FK = c.PK_SEQ   "+
		"	inner join PHANBOTRUNGBAY d on c.PK_SEQ = d.CTTB_FK   "+
		"	where KHACHHANG_FK = '"+ khId +"' and a.NPP_FK = '"+ nppId +"' and c.NGAYTDS <= '"+ ngaydh +"' and d.NPP_FK='"+nppId+"'  "+ 
		"	and c.NGAYKTTDS >= '"+ ngaydh +"' and isnull(b.DAT, 0) < b.XUATDUYET and a.trangthai=1 ";
		
		System.out.println("[XLTrungbay]"+sql);
		
			this.cttbList = db.get(sql);
		}
		catch(Exception exception)
		{
			System.out.println("115.Error Herer: " + exception.toString());
		}
	}
	
	public void setCotrungbay(String cotrungbay) 
	{
		this.coTrungBay = cotrungbay;
	}

	public String getCotrungbay() 
	{
		return this.coTrungBay;
	}
	
	public int luutrungbay(String cttbid)
	{
			int flag = 0;
			//truong hop cap nhat, phai xoa so xuat cu
			String query = "select DENGHITRATB_FK, khachhang_fk, dat from DENGHITRATB_DONHANG where donhang_fk = '" + this.donhangId + "'";
			ResultSet rsDelete = db.get(query);
			if(rsDelete != null)
			{
				try 
				{
					db.getConnection().setAutoCommit(false);
					while(rsDelete.next())
					{
						String dk_fk = rsDelete.getString("DENGHITRATB_FK");
						String kh_fk = rsDelete.getString("khachhang_fk");
						String dat = rsDelete.getString("dat");
						
						query = "delete DENGHITRATB_DONHANG where DENGHITRATB_FK = '" + dk_fk + "' and khachhang_fk = '" + kh_fk + "' and donhang_fk = '" + this.donhangId + "'";
						if(!db.update(query))
						{
							this.Msg = "1.Không thể cập nhật DENGHITRATB_DONHANG " + query;
							db.getConnection().rollback();
							return -1;
						}
						query = "update DENGHITRATB_DONHANG set dat = dat - " + dat + " where DENGHITRATB_FK = '" + dk_fk + "' and khachhang_fk = '" + kh_fk + "'";
						if(!db.update(query))
						{
							this.Msg = "2.Không thể cập DENGHITRATB_DONHANG " + query;
							db.getConnection().rollback();
							return -1;
						}
					}
					rsDelete.close();
					
					db.getConnection().commit();
					db.getConnection().setAutoCommit(true);
				}
				catch (Exception e)
				{
					try 
					{
						db.getConnection().rollback();
					} 
					catch (Exception e1) {}
					
					this.Msg = "115. Loi: " + e.getMessage();
					return -1;
				}
			}
		 query =
				"select a.CTTRUNGBAY_FK, b.DENGHITRATB_FK, c.scheme, c.NGAYTDS as TuNgay, c.NGAYKTTDS as DenNgay, isnull(b.DAT, 0) as DaDat, b.XUATDUYET - isnull(b.DAT, 0) as NganSachConLai " +
				"from DENGHITRATRUNGBAY a inner join DENGHITRATB_KHACHHANG b on a.PK_SEQ = b.DENGHITRATB_FK  " +
				"inner join CTTRUNGBAY c on a.CTTRUNGBAY_FK = c.PK_SEQ " +
				"where KHACHHANG_FK = '" + khId + "' and a.NPP_FK = '" + this.nppId + "' and c.NGAYTDS <= '" + ngaydh + "' " +
				"and c.NGAYKTTDS >= '" + ngaydh + "'  and c.pk_seq = '"+ cttbid +"' and isnull(b.DAT, 0) < b.XUATDUYET and a.trangthai=1 ";
		 
			System.out.println("[Check cttb XLTB ]: " + query);
			
			ResultSet rs = db.getScrol(query);
			String cttb_fk = "";
			String dktb_fk = "";
			
			int ngansach = 0;
			int NganSachConLai = 0;
			int dadat = 0;
			int soXuat = 0;
					
			if(rs != null) // ap trung bay repair
			{
				try 
				{
					db.getConnection().setAutoCommit(false);
					
					while(rs.next())
					{
					
						cttb_fk = rs.getString("CTTRUNGBAY_FK");
						dktb_fk = rs.getString("DENGHITRATB_FK");
						NganSachConLai = rs.getInt("NganSachConLai");
						
						//LAY NGAN SACH CON LAI
						query =  "select a.LEVEL_PHANBO, " + 
								 "	case a.LEVEL_PHANBO when 0 then  " + 
								 "		( select NGANSACH from PHANBOTRUNGBAY where NPP_FK = '" + this.nppId + "' and CTTB_FK = a.PK_SEQ ) " + 
								 "	when 1 then   " + 
								 "		( select NGANSACH from PHANBOTRUNGBAY where VUNG_FK = ( select VUNG_FK from KHUVUC where pk_seq in ( select KHUVUC_FK from NHAPHANPHOI where PK_SEQ = '" + this.nppId + "' )  ) and CTTB_FK = a.PK_SEQ ) " + 
								 "	else " + 
								 "		( select NGANSACH from PHANBOTRUNGBAY where KHUVUC_FK = ( select KHUVUC_FK from NHAPHANPHOI where PK_SEQ = '" + this.nppId + "' ) and CTTB_FK = a.PK_SEQ ) " + 
								 "	end as NGANSACH, " + 
								 "	ISNULL( ( " + 
								 "		select SUM(ISNULL( b.DAT , 0 ) )  " + 
								 "		from DENGHITRATRUNGBAY a inner join DENGHITRATB_KHACHHANG b on a.PK_SEQ = b.DENGHITRATB_FK " + 
								 "				inner join CTTRUNGBAY c on a.CTTRUNGBAY_FK = c.PK_SEQ " + 
								 "				inner join NHAPHANPHOI d on a.NPP_FK = d.PK_SEQ " + 
								 "				inner join KHUVUC e on d.KHUVUC_FK = e.PK_SEQ " + 
								 "		where CTTRUNGBAY_FK = '" + cttb_fk + "' and " + 
								 "				( case c.LEVEL_PHANBO   when 0 then a.NPP_FK when 1 then e.VUNG_FK when 2 then d.KHUVUC_FK end  )    " + 
								 "			  = ( case c.LEVEL_PHANBO   when 0 then '" + this.nppId + "'   " + 
								 "										when 1 then ( select VUNG_FK from KHUVUC where PK_SEQ = ( select KHUVUC_FK from NHAPHANPHOI where PK_SEQ = '" + this.nppId + "' ) )   " + 
								 "										when 2 then ( select KHUVUC_FK from NHAPHANPHOI where PK_SEQ = '" + this.nppId + "' )  end  )    " + 
								 "	), 0 ) as DASUDUNG " + 
								 "from CTTRUNGBAY a   " + 
								 "where a.PK_SEQ = '" + cttb_fk + "' ";
						
						ResultSet rsNGANSACH = db.get(query);
						if(rsNGANSACH.next())
						{
							ngansach = rsNGANSACH.getInt("NGANSACH") - rsNGANSACH.getInt("DASUDUNG");
						}
						rsNGANSACH.close();
						
						soXuat = getSoXuatTheoScheme(this.donhangId, cttb_fk);
						
						System.out.println("__-____Soxuat la: " + soXuat);
						
						if(soXuat > 0)
						{
							if(ngansach - soXuat <= 0)
							{
								soXuat = ngansach;
							}
							
							if(soXuat > NganSachConLai)
								soXuat = NganSachConLai;
							
							System.out.println("SCHEME : "+rs.getString("scheme"));
							this.coTrungBay += rs.getString("scheme") + " ( đạt " + soXuat + " xuất) ";
							flag = 1;
							
							query = "insert DENGHITRATB_DONHANG(DENGHITRATB_FK, KHACHHANG_FK, DONHANG_FK, DAT) " +
									"values('" + dktb_fk + "', '" + khId + "', '" + this.donhangId + "', '" + soXuat + "')";
							if(!db.update(query))
							{
								this.Msg = "2.Không thể cập nhật trưng bày";
								rs.close();
								db.getConnection().rollback();
								return -1;
							}
							
							query = "update DENGHITRATB_KHACHHANG set dat = isnull( ( select SUM(DAT) from DENGHITRATB_DONHANG  " +
																		"where DENGHITRATB_FK = '" + dktb_fk + "' and KHACHHANG_FK = '" + khId + "' group by DENGHITRATB_FK, KHACHHANG_FK ) , 0 )  " +
									"where DENGHITRATB_FK = '" + dktb_fk + "' and KHACHHANG_FK = '" + khId + "'";
							
							System.out.println("11.-------Update DK trung bay: " + query);
							if(!db.update(query))
							{
								this.Msg = "3.Không thể cập nhật trưng bày";
								rs.close();
								db.getConnection().rollback();
								return -1;
							}
						}
						else
						{
							this.coTrungBay += rs.getString("scheme");
						}
						
					}
					rs.close();					
					db.getConnection().commit();
					db.getConnection().setAutoCommit(true);
				}
				catch (Exception e) 
				{
					System.out.println("__Exception: " + e.getMessage());
					this.Msg = "__Exception: " + e.getMessage();
					
					try 
					{
						db.getConnection().rollback();
					}
					catch (SQLException e1) {}
					
					return -1;
				}
			}
			
			return flag;		
	}

	private int getSoXuatTheoScheme(String dhId, String ctkmId)
	{	
		int soxuat = 0;
		
	String query = "select isnull(MIN(Soxuat), '0') as SoXuatThoa, COUNT(nsptbId) as sonhom " +
				"from " +
				"( " +
					"select nsptbId, case when pheptoan = 1 then 'AND' else 'OR' end as pheptoan,  " +
						"case  when (LOAI = 2 and tongluongPhaiMua > 0) then tongluong / tongluongPhaiMua  " +
						"when (LOAI = 2 and tongtienPhaiMua > 0) then tongtien / tongtienPhaiMua  " +
						"else soxuatAnd end as Soxuat " +
					"from" +
					"( " +
						"select dieukientrungbay.*, trungbaytheodk.KHACHHANG_FK, SUM(trungbaytheodk.tongluong) as tongluong, SUM(trungbaytheodk.tongtien) as tongtien, " +
							"COUNT(case when trungbaytheodk.batbuoc > 0 then 1 else null end ) sospphaimua, " +
							"MIN (trungbaytheodk.tongluong / trungbaytheodk.batbuoc) as soxuatAnd " +
						"from " +
						"( " +
							"select b.PK_SEQ as nsptbId, b.LOAI, a.pheptoan, sum( distinct ISNULL(TONGLUONG, '0')) as tongluongPhaiMua,  " +
								"SUM( distinct ISNULL(tongtien, 0) ) as tongtienPhaiMua,  " +
								"count(case when isnull(c.soluong, '0') > 0 then 1 else null end) as sospbatbuoc  " +
							"from CTTB_NHOMSPTRUNGBAY a inner join NHOMSPTRUNGBAY b on a.NHOMSPTRUNGBAY_FK = b.PK_SEQ " +
								"inner join NHOMSPTRUNGBAY_SANPHAM c on a.NHOMSPTRUNGBAY_FK = c.NHOMSPTRUNGBAY_FK " +
							"where CTTRUNGBAY_FK = '" + ctkmId + "'  " +
							"group by b.PK_SEQ, b.LOAI, a.pheptoan " +
						")  " +
						"dieukientrungbay inner join " +
						"( " +
							"select muatrongnhom.*, batbuocmua.batbuoc " +
							"from ( " +
									"select a.KHACHHANG_FK, c.NHOMSPTRUNGBAY_FK as nspId, b.SANPHAM_FK, SUM(b.SOLUONG * b.GIAMUA) as tongtien, SUM(b.soluong) as tongluong, '1' as type  " +
									"from DONHANG a inner join DONHANG_SANPHAM b on a.PK_SEQ = b.DONHANG_FK " +
										"inner join NHOMSPTRUNGBAY_SANPHAM c on b.SANPHAM_FK = c.SANPHAM_FK " +
									"where a.PK_SEQ = '" + dhId + "' and  " +
										"c.NHOMSPTRUNGBAY_FK in ( select NHOMSPTRUNGBAY_FK from CTTB_NHOMSPTRUNGBAY where CTTRUNGBAY_FK = '" + ctkmId + "' )  " +
									"group by KHACHHANG_FK, c.NHOMSPTRUNGBAY_FK, b.SANPHAM_FK " +
								") " +
							"muatrongnhom left join  " +
							"( " +
								"select NHOMSPTRUNGBAY_FK as nspId, SANPHAM_FK, case when isnull(soluong, '0') <= 0 then -1 else soluong end as batbuoc  " +
								"from NHOMSPTRUNGBAY_SANPHAM  " +
								"where NHOMSPTRUNGBAY_FK in ( select NHOMSPTRUNGBAY_FK from CTTB_NHOMSPTRUNGBAY where CTTRUNGBAY_FK = '" + ctkmId + "' ) " +
							")  " +
							"batbuocmua on muatrongnhom.SANPHAM_FK = batbuocmua.SANPHAM_FK and muatrongnhom.nspId = batbuocmua.nspId " +
							"where muatrongnhom.tongluong > batbuocmua.batbuoc " +
						") " +
					"trungbaytheodk on dieukientrungbay.nsptbId = trungbaytheodk.nspId  " +
					"group by  dieukientrungbay.nsptbId, dieukientrungbay.LOAI, dieukientrungbay.tongluongPhaiMua, dieukientrungbay.tongtienPhaiMua,  " +
								"dieukientrungbay.sospbatbuoc, dieukientrungbay.pheptoan, trungbaytheodk.KHACHHANG_FK  " +
					"having COUNT(case when trungbaytheodk.batbuoc > 0 then 1 else null end ) >= dieukientrungbay.sospbatbuoc " +
				") chuongtrinhtrungbay " +
			") ngansachkhuyenmai";
		
		System.out.println("___Lay so xuat CTTB: " + query);
		
		ResultSet rs = db.get(query);
		int soDK = 0;
		if(rs != null)
		{
			try 
			{
				if(rs.next())
				{
					soxuat = rs.getInt("SoXuatThoa");
					soDK = rs.getInt("sonhom");
				}
				rs.close();
			} 
			catch (Exception e) 
			{
				System.out.println("115.Error: " + e.getMessage());
			}
		}
		
		//Check So Dieu Kien AND
		int sophaithoa = 0;
		query = "select SUM(case pheptoan when 1 then 1 else 0 end) as sodieukien from CTTB_NHOMSPTRUNGBAY where CTTRUNGBAY_FK = '" + ctkmId + "' ";
		ResultSet rsCheck = db.get(query);
		if(rsCheck != null)
		{
			try 
			{
				while(rsCheck.next())
				{
					sophaithoa = rsCheck.getInt("sodieukien");
				}
				rsCheck.close();
			} 
			catch (Exception e) 
			{
				System.out.println("115.Errror: Loi: " + e.getMessage());
			}
		}
		
		System.out.println("_____________So dieu kien Ly thuyet: " + soDK + "  --- So bat buoc: " + sophaithoa);
		if(soDK < sophaithoa)
		{
			soxuat = 0;
		}
		
	
		return soxuat;
	}
	

}