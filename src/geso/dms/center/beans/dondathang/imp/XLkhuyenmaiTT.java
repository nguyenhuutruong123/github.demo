package geso.dms.center.beans.dondathang.imp;

import geso.dms.distributor.beans.ctkhuyenmai.*;
import geso.dms.distributor.beans.ctkhuyenmai.imp.Ctkhuyenmai;
import geso.dms.distributor.beans.ctkhuyenmai.imp.Sanpham;
import geso.dms.distributor.beans.dieukienkhuyenmai.IDieukienkhuyenmai;
import geso.dms.distributor.beans.dieukienkhuyenmai.imp.*;
import geso.dms.distributor.beans.trakhuyenmai.ITrakhuyenmai;
import geso.dms.distributor.beans.trakhuyenmai.imp.*;
import geso.dms.distributor.db.sql.dbutils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Hashtable;

public class XLkhuyenmaiTT 
{
	private dbutils db;
	
	private Hashtable<String, Integer> HashA = new Hashtable<String,Integer>(); //khoi tao tu don hang "abc"	
	private Hashtable<String, Float> Hash_QuyCach = new Hashtable<String,Float>(); //khoi tao tu don hang "abc"
	private Hashtable<String,Float> HashB = new Hashtable<String,Float>(); //khoi tao tu don hang "abc"
	
	private Hashtable<String,Integer> HashC = new Hashtable<String,Integer>(); //dung trong truong hop Ontop 	
	
	private List<ICtkhuyenmai> ctkmList; //chuong trinh km Avaialble
	
	private List<ICtkhuyenmai> ctkmResual; //ket qua
	private List<ICtkhuyenmai> ctkmOntopList; //ontop
	private List<ICtkhuyenmai> ctkmBTList; //chuong trinh km Binh Thuong
	private List<ICtkhuyenmai> ctkmCKList; //chuong trinh km Binh Thuong
	
	private List<ICtkhuyenmai> ctkmOntopResual; //ket qua
	private List<ICtkhuyenmai> ctkmBTResual; //ket qua
	private List<ICtkhuyenmai> ctkmCKResual; //ket qua
	
	private boolean dieuchinhKM;
	private boolean dungdieukien;
	
	//luu lai cac thong tin
	private String[] masp;
	private String[] soluong;
	private String[] dongia;
	private String[] quycach;
	private String idDonhang;
	private float tonggiatriDh;
	private String ngaygiaodich; //ngay giao dich trong don hang
	private String Msg = "";
	String userId;
	String nppId;
	String nppTen;
	String sitecode;
	String khachhang;
	String kenh_kh;
	String loaidonhang;
	
	public XLkhuyenmaiTT(String userId, String date, String nppId, String idDonhang)
	{
		db = new dbutils();
		
		this.loaidonhang = "0";
		
		this.nppId = nppId;
			
		this.userId = userId;
		this.idDonhang = idDonhang;
		
		this.ctkmList = this.getListCTKM_Avaiable(date);
		
		this.ctkmOntopList = new ArrayList<ICtkhuyenmai>();    		
		this.ctkmBTList = new ArrayList<ICtkhuyenmai>();
		this.ctkmCKList = new ArrayList<ICtkhuyenmai>();
		
		
		
		this.dungdieukien = false;
	}
		
	public List<ICtkhuyenmai> getListCTKM_Avaiable(String date) //list chuong trinh khuyen mai con hieu luc
	{   
		List<ICtkhuyenmai> ctkmList = new ArrayList<ICtkhuyenmai>();
		
		String sql = "";
		
		//CTKM BEN VIFON AP DUNG CHO KENH TU TRUOC, NEN CHI LOC RA NHUNG SCHEME NAO CO KENH CHUA KENH CUA KHACH HANG
		// Loai CT = 4 là CTKM đặc biệt không lấy vào
		/*sql = " select a.pk_seq, a.scheme, a.TuNgay_DatHang, a.denngay, a.loaict, a.kho_fk,  isnull(a.diengiai, '') as diengiai, d.ngansach, isnull(d.dasudung, '0') as dasudung from ctkhuyenmai a, ctkm_npp b, phanbokhuyenmai d ";
		sql = sql + " where a.loaict <> '3' and b.chon='1' and a.pk_seq = b.ctkm_fk and a.pk_seq = d.ctkm_fk and b.npp_fk = d.npp_fk and b.npp_fk='" + this.nppId + "' and a.TuNgay_DatHang <= '" + date + "' and '" + date + "' <= a.denngay ";
		sql = sql + " and (a.nhomkhnpp_fk is null or a.nhomkhnpp_fk in (select pk_seq from nhomkhachhangnpp where pk_seq in (select nhomkhnpp_fk from ctkm_khachhang where khachhang_fk ='"+ this.khachhang +"'))) order by a.pk_seq desc, isnull(d.ngansach, '0') - isnull(d.dasudung, '0') desc";*/
			
		sql =  "select a.pk_seq, a.scheme, a.TuNgay as TuNgay, a.DenNgay as DenNgay, a.loaict, a.kho_fk,  isnull(a.diengiai, '') as diengiai,  " +
							"d.ngansach, trakhuyenmai.ChietKhau, a.PHANBOTHEODONHANG,  " +
							"	case a.PHANBOTHEODONHANG  " + 
							"		when 0 then ISNULL( 	( select SUM(tonggiatri)    " + 
							" 								  from ERP_DONDATHANG_CTKM_TRAKM    " + 
							" 								  where CTKMID = d.CTKM_FK and DONDATHANGID in ( select PK_SEQ from ERP_DONDATHANG where NPP_FK = d.NPP_FK and TRANGTHAI != 3 )   ), 0 )  " + 
							"		else ISNULL( 	( select sum( SOLUONG)    " + 
							" 						  from ERP_DONDATHANG_CTKM_TRAKM    " + 
							" 						  where  SPMA IS NOT NULL AND CTKMID = d.CTKM_FK and DONDATHANGID in ( select PK_SEQ from ERP_DONDATHANG where NPP_FK = d.NPP_FK and TRANGTHAI != 3 )   ), 0 ) end as dasudung   " + 
				"from ctkhuyenmai a inner join ctkm_npp b on a.pk_seq = b.ctkm_fk and b.NPP_FK = ?  " +
						"inner join  phanbokhuyenmai d on b.CTKM_FK = d.CTKM_FK and d.NPP_FK = ?  " +
				"left join " +
				"( " +
					"select a.CTKHUYENMAI_FK, MAX(ISNULL(b.chietkhau, 0)) as ChietKhau   " +
					"from CTKM_TRAKM a inner join TRAKHUYENMAI b on a.TRAKHUYENMAI_FK = b.PK_SEQ  " +
					"group by a.CTKHUYENMAI_FK  " +
				")  " +
				"trakhuyenmai on a.PK_SEQ = trakhuyenmai.CTKHUYENMAI_FK  " +
				"where  a.apdungcho in ( 0, 1 ) and isnull(a.NPPTUCHAY,0)=0  and   a.loaict <> '3' and a.TuNgay <= ? and ? <= a.DenNgay  and isnull(a.loaict,'') <> 4 " +
				"order by trakhuyenmai.ChietKhau desc";
			
		dbutils.viewQuery(sql, new Object[]{this.nppId,this.nppId,date,date});
		
		ResultSet ctkm = db.getByPreparedStatement(sql, new Object[]{this.nppId,this.nppId,date,date});
	
		if(ctkm != null)
		{
		try 
		{
			while(ctkm.next())
			{
				String id = ctkm.getString("pk_seq");
				String scheme = ctkm.getString("scheme");
				String tungay = ctkm.getDate("tungay").toString();
				String denngay = ctkm.getDate("denngay").toString();
				String diengiai = ctkm.getString("diengiai");
				int khoId = ctkm.getInt("kho_fk");
				int loaict = ctkm.getInt("loaict");
				float ngansach = ctkm.getFloat("ngansach");
				float dasudung = ctkm.getFloat("dasudung");
				float chietkhau = ctkm.getFloat("chietkhau");
				int soxuatKM = 0;
				
				//System.out.println("Id chuong trinh: " + ctkm.getString("pk_seq") + " -- Ngan sach: " + ngansach + " -- dasudung: " + dasudung + "\n");
				
				if( ngansach - dasudung > 0 )
				{
					Ctkhuyenmai km = new Ctkhuyenmai(id, scheme, diengiai, tungay, denngay, loaict, ngansach, dasudung, soxuatKM);
					km.setCK(chietkhau);
					km.setPhanbotheoDH(ctkm.getString("PHANBOTHEODONHANG"));
					
					List<IDieukienkhuyenmai> dkkhuyenmai = new ArrayList<IDieukienkhuyenmai>();
					List<ITrakhuyenmai> trakhuyenmai = new ArrayList<ITrakhuyenmai>();
					
					//Lay danh sach dieu kien khuyen mai cua chuong trinh
					ResultSet rs = db.getByPreparedStatement("select dkkhuyenmai_fk, pheptoan from ctkm_dkkm where Ctkhuyenmai_fk=? order by thutudieukien ASC", new Object[]{id});
					System.out.println("Lay tra km: select dkkhuyenmai_fk, pheptoan from ctkm_dkkm where Ctkhuyenmai_fk='" + id + "' order by thutudieukien ASC");
					/*if(rs != null)*/
					{
						while(rs.next())
						{
							String dkkmId = rs.getString("dkkhuyenmai_fk");
							int pheptoan = Integer.parseInt(rs.getString("pheptoan"));
							
							Dieukienkhuyenmai dkkm = new Dieukienkhuyenmai(dkkmId);
							dkkm.setPheptoan(pheptoan);
							dkkhuyenmai.add(dkkm);
						}
						rs.close();
					}
					km.setDkkhuyenmai(dkkhuyenmai);
					
					//Lay danh sach tra khuyen mai cua chuong trinh
					ResultSet rsTraKm = db.getByPreparedStatement("select trakhuyenmai_fk, pheptoan from ctkm_trakm where Ctkhuyenmai_fk=? order by thutu ASC ", new Object[]{id});
					//System.out.println("2323.Get tra khuyen mai: select trakhuyenmai_fk, pheptoan from ctkm_trakm where Ctkhuyenmai_fk='" + id + "' order by thutu ASC ");
					if(rsTraKm != null)
					{
						int pos = 0;
						while(rsTraKm.next())
						{
							String trakmId = rsTraKm.getString("trakhuyenmai_fk");
							int pheptoan = Integer.parseInt(rsTraKm.getString("pheptoan"));
							
							Trakhuyenmai trakm = new Trakhuyenmai(trakmId, this.nppId, date, this.idDonhang, "1");
							trakm.setPheptoan(pheptoan);
							
							if(pos > 0 && pheptoan == 2)
							{
								km.setTra_OR(true);
								//System.out.println("Co tra khuyen mai OR trong CTKM...");
							}
							
							trakhuyenmai.add(trakm);
							pos++;
						}
						rsTraKm.close();
					}
					km.setTrakhuyenmai(trakhuyenmai);
					
					ctkmList.add(km);
				}
				
			}
			if(ctkm!=null){
				ctkm.close();
			}
		} 
		
		catch (Exception e) { 
			e.printStackTrace();
			System.out.println("115.Error: " + e.getMessage()); }
		}
		
		return ctkmList;
	}
	
	/*public List<ICtkhuyenmai> getListCTKM_Avaiable(String date) //list chuong trinh khuyen mai con hieu luc
	{   
		List<ICtkhuyenmai> ctkmList = new ArrayList<ICtkhuyenmai>();
		
		String sql = "";
		
		//CTKM BEN VIFON AP DUNG CHO KENH TU TRUOC, NEN CHI LOC RA NHUNG SCHEME NAO CO KENH CHUA KENH CUA KHACH HANG
		// Loai CT = 4 là CTKM đặc biệt không lấy vào
		sql = " select a.pk_seq, a.scheme, a.TuNgay_DatHang, a.denngay, a.loaict, a.kho_fk,  isnull(a.diengiai, '') as diengiai, d.ngansach, isnull(d.dasudung, '0') as dasudung from ctkhuyenmai a, ctkm_npp b, phanbokhuyenmai d ";
		sql = sql + " where a.loaict <> '3' and b.chon='1' and a.pk_seq = b.ctkm_fk and a.pk_seq = d.ctkm_fk and b.npp_fk = d.npp_fk and b.npp_fk='" + this.nppId + "' and a.TuNgay_DatHang <= '" + date + "' and '" + date + "' <= a.denngay ";
		sql = sql + " and (a.nhomkhnpp_fk is null or a.nhomkhnpp_fk in (select pk_seq from nhomkhachhangnpp where pk_seq in (select nhomkhnpp_fk from ctkm_khachhang where khachhang_fk ='"+ this.khachhang +"'))) order by a.pk_seq desc, isnull(d.ngansach, '0') - isnull(d.dasudung, '0') desc";
			
		sql =  "select a.pk_seq, a.scheme, a.TuNgay as TuNgay, a.DenNgay as DenNgay, a.loaict, a.kho_fk,  isnull(a.diengiai, '') as diengiai,  " +
							"d.ngansach, trakhuyenmai.ChietKhau, a.PHANBOTHEODONHANG,  " +
							"	case a.PHANBOTHEODONHANG  " + 
							"		when 0 then ISNULL( 	( select SUM(tonggiatri)    " + 
							" 								  from ERP_DONDATHANG_CTKM_TRAKM    " + 
							" 								  where CTKMID = d.CTKM_FK and DONDATHANGID in ( select PK_SEQ from ERP_DONDATHANG where NPP_FK = d.NPP_FK and TRANGTHAI != 3 )   ), 0 )  " + 
							"		else ISNULL( 	( select sum( SOLUONG)    " + 
							" 						  from ERP_DONDATHANG_CTKM_TRAKM    " + 
							" 						  where  SPMA IS NOT NULL AND CTKMID = d.CTKM_FK and DONDATHANGID in ( select PK_SEQ from ERP_DONDATHANG where NPP_FK = d.NPP_FK and TRANGTHAI != 3 )   ), 0 ) end as dasudung   " + 
				"from ctkhuyenmai a inner join ctkm_npp b on a.pk_seq = b.ctkm_fk and b.NPP_FK = '" + this.nppId + "'  " +
						"inner join  phanbokhuyenmai d on b.CTKM_FK = d.CTKM_FK and d.NPP_FK = '" + this.nppId + "'  " +
				"left join " +
				"( " +
					"select a.CTKHUYENMAI_FK, MAX(ISNULL(b.chietkhau, 0)) as ChietKhau   " +
					"from CTKM_TRAKM a inner join TRAKHUYENMAI b on a.TRAKHUYENMAI_FK = b.PK_SEQ  " +
					"group by a.CTKHUYENMAI_FK  " +
				")  " +
				"trakhuyenmai on a.PK_SEQ = trakhuyenmai.CTKHUYENMAI_FK  " +
				"where  a.apdungcho in ( 0, 1 ) and isnull(a.NPPTUCHAY,0)=0  and   a.loaict <> '3' and a.TuNgay <= '" + date + "' and '" + date + "' <= a.DenNgay  and isnull(a.loaict,'') <> 4 " +
				"order by trakhuyenmai.ChietKhau desc";
			
		System.out.println("1.Chuoi lay ctkm hien huu: " + sql);
		
		ResultSet ctkm = db.get(sql);
	
		if(ctkm != null)
		{
		try 
		{
			while(ctkm.next())
			{
				String id = ctkm.getString("pk_seq");
				String scheme = ctkm.getString("scheme");
				String tungay = ctkm.getDate("tungay").toString();
				String denngay = ctkm.getDate("denngay").toString();
				String diengiai = ctkm.getString("diengiai");
				int khoId = ctkm.getInt("kho_fk");
				int loaict = ctkm.getInt("loaict");
				float ngansach = ctkm.getFloat("ngansach");
				float dasudung = ctkm.getFloat("dasudung");
				float chietkhau = ctkm.getFloat("chietkhau");
				int soxuatKM = 0;
				
				//System.out.println("Id chuong trinh: " + ctkm.getString("pk_seq") + " -- Ngan sach: " + ngansach + " -- dasudung: " + dasudung + "\n");
				
				if( ngansach - dasudung > 0 )
				{
					Ctkhuyenmai km = new Ctkhuyenmai(id, scheme, diengiai, tungay, denngay, loaict, ngansach, dasudung, soxuatKM);
					km.setCK(chietkhau);
					km.setPhanbotheoDH(ctkm.getString("PHANBOTHEODONHANG"));
					
					List<IDieukienkhuyenmai> dkkhuyenmai = new ArrayList<IDieukienkhuyenmai>();
					List<ITrakhuyenmai> trakhuyenmai = new ArrayList<ITrakhuyenmai>();
					
					//Lay danh sach dieu kien khuyen mai cua chuong trinh
					ResultSet rs = db.get("select dkkhuyenmai_fk, pheptoan from ctkm_dkkm where Ctkhuyenmai_fk='" + id + "' order by thutudieukien ASC");
					System.out.println("Lay tra km: select dkkhuyenmai_fk, pheptoan from ctkm_dkkm where Ctkhuyenmai_fk='" + id + "' order by thutudieukien ASC");
					if(rs != null)
					{
						while(rs.next())
						{
							String dkkmId = rs.getString("dkkhuyenmai_fk");
							int pheptoan = Integer.parseInt(rs.getString("pheptoan"));
							
							Dieukienkhuyenmai dkkm = new Dieukienkhuyenmai(dkkmId);
							dkkm.setPheptoan(pheptoan);
							dkkhuyenmai.add(dkkm);
						}
						rs.close();
					}
					km.setDkkhuyenmai(dkkhuyenmai);
					
					//Lay danh sach tra khuyen mai cua chuong trinh
					ResultSet rsTraKm = db.get("select trakhuyenmai_fk, pheptoan from ctkm_trakm where Ctkhuyenmai_fk='" + id + "' order by thutu ASC ");
					//System.out.println("2323.Get tra khuyen mai: select trakhuyenmai_fk, pheptoan from ctkm_trakm where Ctkhuyenmai_fk='" + id + "' order by thutu ASC ");
					if(rsTraKm != null)
					{
						int pos = 0;
						while(rsTraKm.next())
						{
							String trakmId = rsTraKm.getString("trakhuyenmai_fk");
							int pheptoan = Integer.parseInt(rsTraKm.getString("pheptoan"));
							
							Trakhuyenmai trakm = new Trakhuyenmai(trakmId, this.nppId, date, this.idDonhang, "1");
							trakm.setPheptoan(pheptoan);
							
							if(pos > 0 && pheptoan == 2)
							{
								km.setTra_OR(true);
								//System.out.println("Co tra khuyen mai OR trong CTKM...");
							}
							
							trakhuyenmai.add(trakm);
							pos++;
						}
						rsTraKm.close();
					}
					km.setTrakhuyenmai(trakhuyenmai);
					
					ctkmList.add(km);
				}
				
			}
			if(ctkm!=null){
				ctkm.close();
			}
		} 
		
		catch (Exception e) { 
			e.printStackTrace();
			System.out.println("115.Error: " + e.getMessage()); }
		}
		
		return ctkmList;
	}*/
	
	/*public void ApKhuyenMai()
	{		
		int pos_schemeBT = Integer.MAX_VALUE;
		int pos_schemeCK = Integer.MAX_VALUE;
		
		for(int i = 0; i < this.ctkmList.size(); i++)
		{
			ICtkhuyenmai ctkm = this.ctkmList.get(i);
			if(ctkm.getLoaict() == 1)
			{
				if(ctkm.getCK() > 0)
				{
					this.ctkmCKList.add(ctkm);
					
					if(pos_schemeCK == Integer.MAX_VALUE)
						pos_schemeCK = i;
				}
				else
				{
					this.ctkmBTList.add(ctkm);
					
					if(pos_schemeBT == Integer.MAX_VALUE)
						pos_schemeBT = i;
				}
			}
			else
			{
				this.ctkmOntopList.add(ctkm);
			}
		}
		
		this.ctkmResual = new ArrayList<ICtkhuyenmai>();
		this.ctkmBTResual = new ArrayList<ICtkhuyenmai>();
		this.ctkmOntopResual = new ArrayList<ICtkhuyenmai>();
		this.ctkmCKResual = new ArrayList<ICtkhuyenmai>();
		
		//Neu Co sort phai xet la Ap KM CHiet Khau hay Binh thuong truoc (CK cua dms chay dac biet)
		
		if(pos_schemeBT < pos_schemeCK)
		{
			ApBinhThuong();
			ApOnTop();
			ApChietKhau();
		}
		else
		{
			ApChietKhau();
			ApBinhThuong();
			ApOnTop();
		}

		for(int  i = 0; i < this.ctkmBTResual.size(); i++)
		{
			ICtkhuyenmai ctkm = this.ctkmBTResual.get(i);
			System.out.println("a. soxuat "+ ctkm.getSoxuatKM());
			String sql = 
					" select a.CTKM_FK, b.scheme, b.PHANBOTHEODONHANG, a.NGANSACH, "+  	 
							"	case when b.PHANBOTHEODONHANG=1 then "+
							"	 ISNULL( 	( select SUM(SOLUONG)  	  "+
							"	  from ERP_DONDATHANG_CTKM_TRAKM  	  "+
							"	 where CTKMID = a.CTKM_FK AND SPMA IS NOT NULL and DONDATHANGID in  "+
							"	 ( select PK_SEQ from ERP_DONDATHANG where NPP_FK = a.NPP_FK and TRANGTHAI != 3 )),0) "+ 
							"	 else  "+
							"		  ISNULL( 	( select SUM(tonggiatri)  "+  
							"		  from ERP_DONDATHANG_CTKM_TRAKM   "+
							"		  where CTKMID = a.CTKM_FK and DONDATHANGID in ( select PK_SEQ from ERP_DONDATHANG where NPP_FK = a.NPP_FK and TRANGTHAI != 3 )),0)  "+
							"	END as DASUDUNG  "+
							"	 from phanbokhuyenmai a inner join CTKHUYENMAI b on a.CTKM_FK = b.PK_SEQ   "+
							"where npp_fk = '" + nppId + "' and ctkm_fk = '" + ctkm.getId() + "'  ";

			System.out.println("1.Cau lenh check ngan sach: " + sql );

			ResultSet rs = db.get(sql);
			String scheme = "";

			try 
			{
				double NganSach=0;
				double DaSuDung =0;
				double ngansachconlai =0;
				if(rs.next())
				{
					scheme = rs.getString("scheme");
					NganSach=rs.getDouble("NganSach");
					DaSuDung=rs.getDouble("DaSuDung");
					rs.close();	
				}
				ngansachconlai = NganSach-DaSuDung;
				
				double soxuatht = ctkm.getSoxuatKM();
				if(ngansachconlai < soxuatht)
				{
				
					List<ITrakhuyenmai> lsttrakm = ctkm.getTrakhuyenmai();
					ITrakhuyenmai trakm = lsttrakm.get(0);
					int slduochuong = 0;
					System.out.println("hinh thuc tra " + trakm.getHinhthuc());
					if(trakm.getHinhthuc()==1)
					{
						Hashtable<String, Integer> sp_sl = trakm.getSanpham_Soluong();
						Enumeration<String> keys = sp_sl.keys();
						while(keys.hasMoreElements())
						{
							String key = keys.nextElement();
							int value=sp_sl.get(key);
							slduochuong += value;
						}
						System.out.println("soluong duoc huong " + slduochuong);
						ctkm.setSoxuatKM((int)(ngansachconlai/slduochuong));
					}
					else
					{
						System.out.println("loai tra " + trakm.getType());
						if(trakm.getType() == 1 && ngansachconlai >= trakm.getTongtien())
							ctkm.setSoxuatKM((int)(ngansachconlai/trakm.getTongtien()));
						else if(trakm.getType() == 3 && ngansachconlai >= trakm.getTongluong() )
						{
							System.out.println("tong luong " + trakm.getTongluong());
							ctkm.setSoxuatKM((int)(ngansachconlai/trakm.getTongluong()));
						}
					}
				}
			} 
			catch (Exception e) 
			{ 
				e.printStackTrace();
				System.out.println("__EXCEPTION CHECK NGAN SACH: " + e.getMessage());
			}
			System.out.println("b. soxuat "+ ctkm.getSoxuatKM());
			this.ctkmResual.add(ctkm);
		}
		
		for(int  i = 0; i < this.ctkmCKResual.size(); i++)
		{
			ICtkhuyenmai ctkm = this.ctkmCKResual.get(i);
			this.ctkmResual.add(ctkm);
		}
		
		for(int  i = 0; i < this.ctkmOntopResual.size(); i++)
		{
			ICtkhuyenmai ontop = this.ctkmOntopResual.get(i);
			this.ctkmResual.add(ontop);
		}
		
		System.out.println("115___So CTKM duoc huong: " + this.ctkmResual.size());
		
		//DDT Them Trong So
		for(int i = 0; i < this.ctkmResual.size(); i++)
		{
			Ctkhuyenmai ctkm = (Ctkhuyenmai)this.ctkmResual.get(i);
			
			List<IDieukienkhuyenmai> dkkmList = ctkm.getDkkhuyenmai();
			for(int j = 0; j < dkkmList.size(); j++ )
			{
				IDieukienkhuyenmai dkkm = dkkmList.get(j);
				if(dkkm.getTrongso() > 0)
				{
					System.out.println("1.Dkkm: " + dkkm.getDiengiai() + " -- Trong so: " + dkkm.getTrongso());

					float tongluong = 0;
					
					if(dkkm.getTongluong() > 0)
					{
						tongluong = ctkm.getSoxuatKM() * dkkm.getTongluong();
					}
					else
					{
						tongluong = ctkm.getSoxuatKM() * dkkm.getTongtien();
					}
					
					float minRequest = 0;
					
					if(dkkm.getTongluong() > 0)
					{
						minRequest = dkkm.getTrongso() * dkkm.getTongluong() / 100;
					}
					else
					{
						minRequest = dkkm.getTrongso() * dkkm.getTongtien() / 100;
					}
					
					System.out.println("__Tong so luong yeu cau nho nhat: " + minRequest  );
					
					//Tong trong so cua cac san pham bat buoc mua
					Hashtable<String, Float> sanpham_trongso = dkkm.getSanpham_Trongso();
					
					//boolean flag = true;  //Neu SP la bat buoc ma ko su dung thi cung ko dc khuyen mai
					List<ISanpham> spList = dkkm.getSanphamList();	
					float totalRequest = 0;
					for(int k = 0; k < spList.size(); k++)
					{
						System.out.println("__San pham: " + spList.get(k).getMasp() + " -- Su dung:  " + spList.get(k).getSoluongcan() );
						
						if(sanpham_trongso.get(spList.get(k).getMasp()) != null)
						{
							if(sanpham_trongso.get(spList.get(k).getMasp()) > 0)
							{
								if(dkkm.getTongluong() > 0)
									totalRequest += spList.get(k).getSoluongcan();
								else
									totalRequest += spList.get(k).getSoluongcan() * spList.get(k).getDongia();
							}
						}
					}
					
					System.out.println("__Tong so luong yeu cau: " + totalRequest  );
					System.out.println("__Tong trong so yeu cau: " + ( totalRequest * 100 / tongluong ) );
					
					float trongso = ( totalRequest * 100 / tongluong );
					if ( trongso < dkkm.getTrongso() )
					{
						//System.out.println("__CTKM khong thoa trong so va so xuat se duoc cap nhat la: " );
						
						//tinh lai so xuat
						long soXuat = 0;
						
						if(totalRequest < minRequest)
						{
							soXuat = 0;
						}
						else
						{
							soXuat = Math.round( ( ( ctkm.getSoxuatKM() * totalRequest * 100 / tongluong ) / dkkm.getTrongso() ) - 0.5 );
						}

						if(soXuat > 0)
						{
							ctkm.setSoxuatKM((int)soXuat);
						}
						else
						{
							this.ctkmResual.remove(ctkm);
							i -= 1;
						}
					}
				}
			}
		}
		
		//bo 2 chiet khau trung nhau, chi lay chiet khau lon nhat ---->> dms
		//List<ICtkhuyenmai> ctkmBTCK = this.soCtkmCKBinhThuong(this.ctkmBTResual);
		//List<ICtkhuyenmai> ctkmBTCK = this.ctkmCKResual;
		
		List<ICtkhuyenmai> ctkmBTCK = this.soCtkmCKBinhThuong(this.ctkmResual, "1");
		
		//System.out.println("11-----.So CTKM CK Binh Thuong: " + ctkmBTCK.size());
		if(ctkmBTCK.size() >= 2)
		{
			int i = 0;
			while(i < ctkmBTCK.size())
			{
				ICtkhuyenmai ctCurrent = ctkmBTCK.get(i);
				ICtkhuyenmai ctDungChungdk = findCT_CK_CungDK(ctCurrent, ctkmBTCK);
				
				if(ctDungChungdk != null)
				{
					System.out.println("1___Toi tim thay ctkm: " + ctDungChungdk.getscheme());
					if(ctDungChungdk.getTrakhuyenmai().get(0).getChietkhau() > ctCurrent.getTrakhuyenmai().get(0).getChietkhau() )
					{
						this.ctkmResual.remove(ctCurrent);
						ctkmBTCK.remove(ctCurrent);
						i -= 1;
						System.out.println("2___Da go bo ctkm: " + ctCurrent.getscheme());
					}
					else
					{
						this.ctkmResual.remove(ctDungChungdk);
						ctkmBTCK.remove(ctDungChungdk);
						i -= 1;
						System.out.println("3___Da go bo ctkm tim they: " + ctDungChungdk.getscheme());
					}
				}
				i++;
			}
		}
		ctkmBTCK.clear();
		//System.out.println("4____Sau khi remove con: " + this.ctkmResual.size());
		
		
		//bo 2 chiet khau trung nhau, chi lay chiet khau lon nhat ---->> dms
		//List<ICtkhuyenmai> ctkmOT = this.soCtkmCKBinhThuong(this.ctkmOntopResual);
		
		List<ICtkhuyenmai> ctkmOT = this.soCtkmCKBinhThuong(this.ctkmResual, "2");
		if(ctkmOT.size() >= 2)
		{
			int i = 0;
			while(i < ctkmOT.size())
			{
				ICtkhuyenmai ctCurrent = ctkmOT.get(i);
				ICtkhuyenmai ctDungChungdk = findCT_CK_CungDK(ctCurrent, ctkmOT);
				
				if(ctDungChungdk != null)
				{
					//System.out.println("1___Toi tim thay ctkm: " + ctDungChungdk.getscheme());
					if(ctDungChungdk.getTrakhuyenmai().get(0).getChietkhau() > ctCurrent.getTrakhuyenmai().get(0).getChietkhau() )
					{
						this.ctkmResual.remove(ctCurrent);
						ctkmOT.remove(ctCurrent);
						i -= 1;
						//System.out.println("2___Da go bo ctkm: " + ctCurrent.getscheme());
					}
					else
					{
						this.ctkmResual.remove(ctDungChungdk);
						ctkmOT.remove(ctDungChungdk);
						i -= 1;
						//System.out.println("3___Da go bo ctkm tim thay: " + ctDungChungdk.getscheme());
					}
				}
				i++;
			}
		}
		ctkmOT.clear();
		
	}*/
	
	public void ApKhuyenMai()
	{		
		int pos_schemeBT = Integer.MAX_VALUE;
		int pos_schemeCK = Integer.MAX_VALUE;
		
		for(int i = 0; i < this.ctkmList.size(); i++)
		{
			ICtkhuyenmai ctkm = this.ctkmList.get(i);
			if(ctkm.getLoaict() == 1)
			{
				if(ctkm.getCK() > 0)
				{
					this.ctkmCKList.add(ctkm);
					
					if(pos_schemeCK == Integer.MAX_VALUE)
						pos_schemeCK = i;
				}
				else
				{
					this.ctkmBTList.add(ctkm);
					
					if(pos_schemeBT == Integer.MAX_VALUE)
						pos_schemeBT = i;
				}
			}
			else
			{
				this.ctkmOntopList.add(ctkm);
			}
		}
		
		this.ctkmResual = new ArrayList<ICtkhuyenmai>();
		this.ctkmBTResual = new ArrayList<ICtkhuyenmai>();
		this.ctkmOntopResual = new ArrayList<ICtkhuyenmai>();
		this.ctkmCKResual = new ArrayList<ICtkhuyenmai>();
		
		//Neu Co sort phai xet la Ap KM CHiet Khau hay Binh thuong truoc (CK cua dms chay dac biet)
		
		if(pos_schemeBT < pos_schemeCK)
		{
			ApBinhThuong();
			ApOnTop();
			ApChietKhau();
		}
		else
		{
			ApChietKhau();
			ApBinhThuong();
			ApOnTop();
		}

		for(int  i = 0; i < this.ctkmBTResual.size(); i++)
		{
			ICtkhuyenmai ctkm = this.ctkmBTResual.get(i);
			System.out.println("a. soxuat "+ ctkm.getSoxuatKM());
			String sql = 
					" select a.CTKM_FK, b.scheme, b.PHANBOTHEODONHANG, a.NGANSACH, "+  	 
							"	case when b.PHANBOTHEODONHANG=1 then "+
							"	 ISNULL( 	( select SUM(SOLUONG)  	  "+
							"	  from ERP_DONDATHANG_CTKM_TRAKM  	  "+
							"	 where CTKMID = a.CTKM_FK AND SPMA IS NOT NULL and DONDATHANGID in  "+
							"	 ( select PK_SEQ from ERP_DONDATHANG where NPP_FK = a.NPP_FK and TRANGTHAI != 3 )),0) "+ 
							"	 else  "+
							"		  ISNULL( 	( select SUM(tonggiatri)  "+  
							"		  from ERP_DONDATHANG_CTKM_TRAKM   "+
							"		  where CTKMID = a.CTKM_FK and DONDATHANGID in ( select PK_SEQ from ERP_DONDATHANG where NPP_FK = a.NPP_FK and TRANGTHAI != 3 )),0)  "+
							"	END as DASUDUNG  "+
							"	 from phanbokhuyenmai a inner join CTKHUYENMAI b on a.CTKM_FK = b.PK_SEQ   "+
							"where npp_fk = ? and ctkm_fk = ?  ";

			//System.out.println("1.Cau lenh check ngan sach: " + sql );
			dbutils.viewQuery(sql, new Object[]{nppId,ctkm.getId()});

			ResultSet rs = db.getByPreparedStatement(sql, new Object[]{nppId,ctkm.getId()});
			String scheme = "";

			try 
			{
				double NganSach=0;
				double DaSuDung =0;
				double ngansachconlai =0;
				if(rs.next())
				{
					scheme = rs.getString("scheme");
					NganSach=rs.getDouble("NganSach");
					DaSuDung=rs.getDouble("DaSuDung");
					rs.close();	
				}
				ngansachconlai = NganSach-DaSuDung;
				
				double soxuatht = ctkm.getSoxuatKM();
				if(ngansachconlai < soxuatht)
				{
				
					List<ITrakhuyenmai> lsttrakm = ctkm.getTrakhuyenmai();
					ITrakhuyenmai trakm = lsttrakm.get(0);
					int slduochuong = 0;
					System.out.println("hinh thuc tra " + trakm.getHinhthuc());
					if(trakm.getHinhthuc()==1)
					{
						Hashtable<String, Integer> sp_sl = trakm.getSanpham_Soluong();
						Enumeration<String> keys = sp_sl.keys();
						while(keys.hasMoreElements())
						{
							String key = keys.nextElement();
							int value=sp_sl.get(key);
							slduochuong += value;
						}
						System.out.println("soluong duoc huong " + slduochuong);
						ctkm.setSoxuatKM((int)(ngansachconlai/slduochuong));
					}
					else
					{
						System.out.println("loai tra " + trakm.getType());
						if(trakm.getType() == 1 && ngansachconlai >= trakm.getTongtien())
							ctkm.setSoxuatKM((int)(ngansachconlai/trakm.getTongtien()));
						else if(trakm.getType() == 3 && ngansachconlai >= trakm.getTongluong() )
						{
							System.out.println("tong luong " + trakm.getTongluong());
							ctkm.setSoxuatKM((int)(ngansachconlai/trakm.getTongluong()));
						}
					}
				}
			} 
			catch (Exception e) 
			{ 
				e.printStackTrace();
				System.out.println("__EXCEPTION CHECK NGAN SACH: " + e.getMessage());
			}
			System.out.println("b. soxuat "+ ctkm.getSoxuatKM());
			this.ctkmResual.add(ctkm);
		}
		
		for(int  i = 0; i < this.ctkmCKResual.size(); i++)
		{
			ICtkhuyenmai ctkm = this.ctkmCKResual.get(i);
			this.ctkmResual.add(ctkm);
		}
		
		for(int  i = 0; i < this.ctkmOntopResual.size(); i++)
		{
			ICtkhuyenmai ontop = this.ctkmOntopResual.get(i);
			this.ctkmResual.add(ontop);
		}
		
		System.out.println("115___So CTKM duoc huong: " + this.ctkmResual.size());
		
		//DDT Them Trong So
		for(int i = 0; i < this.ctkmResual.size(); i++)
		{
			Ctkhuyenmai ctkm = (Ctkhuyenmai)this.ctkmResual.get(i);
			
			List<IDieukienkhuyenmai> dkkmList = ctkm.getDkkhuyenmai();
			for(int j = 0; j < dkkmList.size(); j++ )
			{
				IDieukienkhuyenmai dkkm = dkkmList.get(j);
				if(dkkm.getTrongso() > 0)
				{
					System.out.println("1.Dkkm: " + dkkm.getDiengiai() + " -- Trong so: " + dkkm.getTrongso());

					float tongluong = 0;
					
					if(dkkm.getTongluong() > 0)
					{
						tongluong = ctkm.getSoxuatKM() * dkkm.getTongluong();
					}
					else
					{
						tongluong = ctkm.getSoxuatKM() * dkkm.getTongtien();
					}
					
					float minRequest = 0;
					
					if(dkkm.getTongluong() > 0)
					{
						minRequest = dkkm.getTrongso() * dkkm.getTongluong() / 100;
					}
					else
					{
						minRequest = dkkm.getTrongso() * dkkm.getTongtien() / 100;
					}
					
					System.out.println("__Tong so luong yeu cau nho nhat: " + minRequest  );
					
					//Tong trong so cua cac san pham bat buoc mua
					Hashtable<String, Float> sanpham_trongso = dkkm.getSanpham_Trongso();
					
					//boolean flag = true;  //Neu SP la bat buoc ma ko su dung thi cung ko dc khuyen mai
					List<ISanpham> spList = dkkm.getSanphamList();	
					float totalRequest = 0;
					for(int k = 0; k < spList.size(); k++)
					{
						System.out.println("__San pham: " + spList.get(k).getMasp() + " -- Su dung:  " + spList.get(k).getSoluongcan() );
						
						if(sanpham_trongso.get(spList.get(k).getMasp()) != null)
						{
							if(sanpham_trongso.get(spList.get(k).getMasp()) > 0)
							{
								if(dkkm.getTongluong() > 0)
									totalRequest += spList.get(k).getSoluongcan();
								else
									totalRequest += spList.get(k).getSoluongcan() * spList.get(k).getDongia();
							}
						}
					}
					
					System.out.println("__Tong so luong yeu cau: " + totalRequest  );
					System.out.println("__Tong trong so yeu cau: " + ( totalRequest * 100 / tongluong ) );
					
					float trongso = ( totalRequest * 100 / tongluong );
					if ( trongso < dkkm.getTrongso() )
					{
						//System.out.println("__CTKM khong thoa trong so va so xuat se duoc cap nhat la: " );
						
						//tinh lai so xuat
						long soXuat = 0;
						
						if(totalRequest < minRequest)
						{
							soXuat = 0;
						}
						else
						{
							soXuat = Math.round( ( ( ctkm.getSoxuatKM() * totalRequest * 100 / tongluong ) / dkkm.getTrongso() ) - 0.5 );
						}

						if(soXuat > 0)
						{
							ctkm.setSoxuatKM((int)soXuat);
						}
						else
						{
							this.ctkmResual.remove(ctkm);
							i -= 1;
						}
					}
				}
			}
		}
		
		//bo 2 chiet khau trung nhau, chi lay chiet khau lon nhat ---->> dms
		//List<ICtkhuyenmai> ctkmBTCK = this.soCtkmCKBinhThuong(this.ctkmBTResual);
		//List<ICtkhuyenmai> ctkmBTCK = this.ctkmCKResual;
		
		List<ICtkhuyenmai> ctkmBTCK = this.soCtkmCKBinhThuong(this.ctkmResual, "1");
		
		//System.out.println("11-----.So CTKM CK Binh Thuong: " + ctkmBTCK.size());
		if(ctkmBTCK.size() >= 2)
		{
			int i = 0;
			while(i < ctkmBTCK.size())
			{
				ICtkhuyenmai ctCurrent = ctkmBTCK.get(i);
				ICtkhuyenmai ctDungChungdk = findCT_CK_CungDK(ctCurrent, ctkmBTCK);
				
				if(ctDungChungdk != null)
				{
					System.out.println("1___Toi tim thay ctkm: " + ctDungChungdk.getscheme());
					if(ctDungChungdk.getTrakhuyenmai().get(0).getChietkhau() > ctCurrent.getTrakhuyenmai().get(0).getChietkhau() )
					{
						this.ctkmResual.remove(ctCurrent);
						ctkmBTCK.remove(ctCurrent);
						i -= 1;
						System.out.println("2___Da go bo ctkm: " + ctCurrent.getscheme());
					}
					else
					{
						this.ctkmResual.remove(ctDungChungdk);
						ctkmBTCK.remove(ctDungChungdk);
						i -= 1;
						System.out.println("3___Da go bo ctkm tim they: " + ctDungChungdk.getscheme());
					}
				}
				i++;
			}
		}
		ctkmBTCK.clear();
		//System.out.println("4____Sau khi remove con: " + this.ctkmResual.size());
		
		
		//bo 2 chiet khau trung nhau, chi lay chiet khau lon nhat ---->> dms
		//List<ICtkhuyenmai> ctkmOT = this.soCtkmCKBinhThuong(this.ctkmOntopResual);
		
		List<ICtkhuyenmai> ctkmOT = this.soCtkmCKBinhThuong(this.ctkmResual, "2");
		if(ctkmOT.size() >= 2)
		{
			int i = 0;
			while(i < ctkmOT.size())
			{
				ICtkhuyenmai ctCurrent = ctkmOT.get(i);
				ICtkhuyenmai ctDungChungdk = findCT_CK_CungDK(ctCurrent, ctkmOT);
				
				if(ctDungChungdk != null)
				{
					//System.out.println("1___Toi tim thay ctkm: " + ctDungChungdk.getscheme());
					if(ctDungChungdk.getTrakhuyenmai().get(0).getChietkhau() > ctCurrent.getTrakhuyenmai().get(0).getChietkhau() )
					{
						this.ctkmResual.remove(ctCurrent);
						ctkmOT.remove(ctCurrent);
						i -= 1;
						//System.out.println("2___Da go bo ctkm: " + ctCurrent.getscheme());
					}
					else
					{
						this.ctkmResual.remove(ctDungChungdk);
						ctkmOT.remove(ctDungChungdk);
						i -= 1;
						//System.out.println("3___Da go bo ctkm tim thay: " + ctDungChungdk.getscheme());
					}
				}
				i++;
			}
		}
		ctkmOT.clear();
		
	}
		
	private ICtkhuyenmai findCT_CK_CungDK(ICtkhuyenmai ctCurrent, List<ICtkhuyenmai> ctkmBTCK)
	{
		for(int j = 0; j < ctkmBTCK.size(); j++)
		{
			ICtkhuyenmai ct = ctkmBTCK.get(j);
			
			if(!ct.getId().equals(ctCurrent.getId()))
			{	
				boolean flag = checkDkConfirm(ct.getDkkhuyenmai(), ctCurrent.getDkkhuyenmai());
				if(flag)
				{
					System.out.println("111___Tim duoc chuong trinh trung, ct Old: " + ctCurrent.getId() + " ----  Ct New Id: " + ct.getId());
					return ct;
				}
			}
		}
		
		return null;
	}

	private List<ICtkhuyenmai> soCtkmCKBinhThuong(List<ICtkhuyenmai> ctkmList, String type)
	{
		List<ICtkhuyenmai> ctkmCKBT = new ArrayList<ICtkhuyenmai>();
		for(int i = 0; i < ctkmList.size(); i++)
		{
			ITrakhuyenmai trakm = ctkmList.get(i).getTrakhuyenmai().get(0);

			if(trakm.getChietkhau() > 0 && ctkmList.get(i).equals(type) )
			{
				//if(checkDKTrongScheme(ctkmList.get(i), ctkmList))
				//{
					//System.out.println("3.Dieu kien chung chiet khau bi trung roi DK..., ct cu: " + ctkmList.get(i).getscheme());
					ctkmCKBT.add(ctkmList.get(i));
				//}
			}
		}
		//System.out.println("=====So CT chiet khau binh thuong tai buoc nay: " + ctkmCKBT.size());
		return ctkmCKBT;
	}
	
	private boolean checkDKTrongScheme(ICtkhuyenmai ctkm, List<ICtkhuyenmai> ctkmList)
	{
		boolean flag = false;
		for(int j = 0; j < ctkmList.size(); j++)
		{
			ICtkhuyenmai ct = ctkmList.get(j);
			
			if(!ct.getId().equals(ctkm.getId()))
			{	
				flag = checkDkConfirm(ct.getDkkhuyenmai(), ctkm.getDkkhuyenmai());
				if(flag)
				{
					System.out.println("1___Hai dieu kien dung, ct Old: " + ctkm + " ----  Ct New Id: " + ct.getId());
					break;
				}
			}
		}
		
		//System.out.println("11___Ket qua check: " + flag);
		return flag;
	}
	
	private void ApOnTop() 
	{
		List<ICtkhuyenmai> schemeOnTopList = new ArrayList<ICtkhuyenmai>();
		// Không tick == true,tick == false
		if(1==2 && this.dieuchinhKM ) // ontop thi ko dieu chinh cho nen de luon vo else
		{
			//System.out.println("Vo day de check ne...\n");
			for(int i = 0; i < this.ctkmOntopList.size(); i++)
			{
				Ctkhuyenmai ct = (Ctkhuyenmai)this.ctkmOntopList.get(i);
				Ctkhuyenmai ctkm = this.getSoxuattheoScheme(ct, HashC);
				
				if(ctkm.getSoxuatKM() > 0) //truong hop soxuat khuyenmai lon hon so xuat duoc huong toi da
				{
					int sx = ctkm.checkCtkm(tonggiatriDh);
					//System.out.println("So xuat check dc la: " + sx + "\n");
					if(sx > 0)
					{
						ctkm.setSoxuatKM(ctkm.checkCtkm(tonggiatriDh));
						schemeOnTopList.add(ctkm);
					}
				}
			}
		}
		else  //dieuchinh == false
		{
			for(int i=0; i < this.ctkmOntopList.size(); i++)
			{				
				Ctkhuyenmai ct = (Ctkhuyenmai)this.ctkmOntopList.get(i);
				Hashtable<String, Integer> copyHashA = copyHashtable(HashC);				
				Ctkhuyenmai ctkm = this.getSoxuattheoScheme(ct, copyHashA);
				
				if(ctkm.getSoxuatKM() > 0)
				{
					if(ctkm.checkCtkm(tonggiatriDh) > 0)
					{
						ctkm.setSoxuatKM(ctkm.checkCtkm(tonggiatriDh));
						schemeOnTopList.add(ctkm);
					}
				}
			}
		}
		
		System.out.println("Chuong trinh ontop tong so: " + schemeOnTopList.size() + "\n");
		this.ctkmOntopResual = schemeOnTopList;
		if(this.dieuchinhKM == true)  //buoc nay doi lai, mot don hang co the huong nhieu ontop (neu khong dung chung 1 dk)
		{
			System.out.println("So khuyen mai ontop la: " + this.ctkmOntopResual.size() + "\n");
			/*
			if(this.ctkmOntopResual.size() > 1) //chi duoc 1 ontop trong list Ontop
			{
				int size = this.ctkmOntopResual.size();
				while(this.ctkmOntopResual.size() > 1)
				{
					this.ctkmOntopResual.remove(size-1);
					size--;
				}
			}*/
			
			//Neu 2 ontop cung su dung dieu kien thi chi duoc huong 1 ontop
					//1 don hang co the duoc huogn cung luc nhieu ontop khac dieu kien
			for(int i = 0; i < this.ctkmOntopResual.size() - 1; i++)
			{
				ICtkhuyenmai ctkmA = this.ctkmOntopResual.get(i);
				String dkkmAId = ctkmA.getDkkhuyenmai().get(0).getId();  //Sau nay ICP su dung nhieu dk tong hop cho 1 ontop thi phai sua lai
				System.out.println("Dieu kien khuyen mai A: " + dkkmAId);
				
				for(int j = i+1; j < this.ctkmOntopResual.size(); j++)
				{
					ICtkhuyenmai ctkmB = this.ctkmOntopResual.get(j);
					String dkkmBId = ctkmB.getDkkhuyenmai().get(0).getId();
					System.out.println("Dieu kien khuyen mai B: " + dkkmBId);
					
					if(dkkmAId.equals(dkkmBId))
					{
						this.ctkmOntopResual.remove(j);
						System.out.println("Da remove ctkm B: " + ctkmB.getscheme());
						i = 0;
						j = 0;
					}
				}
			}
		}
		System.out.println("Chuong trinh ontop sau do: " + schemeOnTopList.size() + "\n");
	}

	private void ApBinhThuong() 
	{
		List<ICtkhuyenmai> schemeList = new ArrayList<ICtkhuyenmai>();
		
		System.out.println("114.So CTKM binh thuong: " + this.ctkmBTList.size());
		
		if(this.dieuchinhKM)
		{
			for(int i = 0; i < this.ctkmBTList.size(); i++)
			{
				Ctkhuyenmai ct = (Ctkhuyenmai)this.ctkmBTList.get(i);
				Ctkhuyenmai ctkm = this.getSoxuattheoScheme(ct, HashA);
				
				/*Enumeration<String> keys = HashA.keys();
				while(keys.hasMoreElements())
				{
					String key = keys.nextElement();
					System.out.println("__Sau Dieu CHinh, San pham: " + key + ", Con lai: " + HashA.get(key));
				}*/
		
				if(ctkm.getSoxuatKM() > 0) //truong hop soxuat khuyenmai lon hon so xuat duoc huong toi da
				{
					if(ctkm.checkCtkm(tonggiatriDh) > 0)
					{
						ctkm.setSoxuatKM(ctkm.checkCtkm(tonggiatriDh));
						schemeList.add(ctkm);
					}
				}
			}
		}
		else  //dieuchinh == false
		{
			for(int i=0; i < this.ctkmBTList.size(); i++)
			{				
				Ctkhuyenmai ct = (Ctkhuyenmai)this.ctkmBTList.get(i);
				Hashtable<String, Integer> copyHashA = copyHashtable(HashA);				
				Ctkhuyenmai ctkm = this.getSoxuattheoScheme(ct, copyHashA);
				
				if(ctkm.getSoxuatKM() > 0)
				{
					if(ctkm.checkCtkm(tonggiatriDh) > 0)
					{
						ctkm.setSoxuatKM(ctkm.checkCtkm(tonggiatriDh));
						schemeList.add(ctkm);
					}
				}
			}
		}
		
		System.out.println("chuong tring binh thuong: " + schemeList.size() + "\n");
		this.ctkmBTResual = schemeList;
	}
	
	private void ApChietKhau() 
	{
		List<ICtkhuyenmai> schemeList = new ArrayList<ICtkhuyenmai>();
		
		System.out.println("114.So CTKM chiet khau: " + this.ctkmCKList.size() + "  -- Che do dieu chinh: " + this.dieuchinhKM);
		
		if(this.dieuchinhKM)
		{
			for(int i = 0; i < this.ctkmCKList.size(); i++)
			{
				Ctkhuyenmai ct = (Ctkhuyenmai)this.ctkmCKList.get(i);
				Ctkhuyenmai ctkm = this.getSoxuattheoScheme(ct, HashA);
			
				if(ctkm.getSoxuatKM() > 0) //truong hop soxuat khuyenmai lon hon so xuat duoc huong toi da
				{
					if(ctkm.checkCtkm(tonggiatriDh) > 0)
					{
						ctkm.setSoxuatKM(ctkm.checkCtkm(tonggiatriDh));
						schemeList.add(ctkm);
					}
				}
			}
		}
		else  //dieuchinh == false
		{
			
			for(int i = 0; i < this.ctkmCKList.size(); i++)
			{
				Ctkhuyenmai ct = (Ctkhuyenmai)this.ctkmCKList.get(i);
				Hashtable<String, Integer> hashCK = copyHashtable(HashA);	
				Ctkhuyenmai ctkm = this.getSoxuattheoScheme(ct, hashCK);
			
				if(ctkm.getSoxuatKM() > 0) //truong hop soxuat khuyenmai lon hon so xuat duoc huong toi da
				{
					if(ctkm.checkCtkm(tonggiatriDh) > 0)
					{
						ctkm.setSoxuatKM(ctkm.checkCtkm(tonggiatriDh));
						schemeList.add(ctkm);
					}
				}
			}
		}
		
		System.out.println("123.chuong tring binh thuong chiet khau: " + schemeList.size() + "\n");
		this.ctkmCKResual = schemeList;
	}
	
	public Ctkhuyenmai getSoxuattheoScheme(Ctkhuyenmai ctkm, Hashtable<String, Integer> hashSanpham)
	{
		Ctkhuyenmai ctkhuyenmai = new Ctkhuyenmai();
		List<IDieukienkhuyenmai> dkkmList = ctkm.getDkkhuyenmai();
			
		//Xu ly tat cac cac dieukienkhuyenmai cua ctkhuyenmai
		ArrayList<Boolean> dieukien = new ArrayList<Boolean>(); //luu cac ket qua dieukien
		ArrayList<String> pheptoan = new ArrayList<String>(); //luu cac pheptoan
		int soXuatKM = 0;
		
		Hashtable<String, Integer> copySp = copyHashtable(hashSanpham);
		long tongtientheodk = 0;
		for(int i = 0; i < dkkmList.size(); i++)
		{
			Dieukienkhuyenmai dkkm = (Dieukienkhuyenmai)dkkmList.get(i);
			//List<ISanpham> sanphamList = new ArrayList<ISanpham>();
			
			String pt = ((dkkm.getPheptoan() == 1) ? "&&" : "||");
			pheptoan.add(pt);

			boolean flag = false;
			int sl = 0;
			long ttTheodk = 0;
						
			if(dkkm.getTongtien() > 0) 								//check tong tien co hop dieu kien ko??
			{
				//System.out.println("1111111.Thu tu Tong tien");
				String[] str = checkDKKM_TongTien(dkkm, HashB, hashSanpham).split("-");
				sl = Integer.parseInt(str[0]);
				ttTheodk = Long.parseLong(str[1]);
			}
			else   
			{
				if(dkkm.getTongluong() > 0) 						//check tong soluong sp trong dkkm
				{
					//System.out.println("2222222.Thu tu Tong luong");
					//sl = checkDKKM_TongLuong(dkkm, hashSanpham);
					String[] str = checkDKKM_TongLuong(dkkm, HashB, hashSanpham).split("-");
					sl = Integer.parseInt(str[0]);
					ttTheodk = Long.parseLong(str[1]);
				}
				else												//check so luong cu the tung san pham trong dkkm
				{
					//sl = checkDKKM_SP(dkkm, hashSanpham);
					String[] str = checkDKKM_SP(dkkm, HashB, hashSanpham).split("-");
					sl = Integer.parseInt(str[0]);
					ttTheodk = Long.parseLong(str[1]);
				}
			}
			
			System.out.println("113.So xuat KM khi dang xet scheme ( " + ctkm.getscheme() + " ) la: " + sl);
			
			if(sl > 0)
			{
				flag = true;
				ctkm.getDkkhuyenmai().get(i).setSoxuatKM(sl);	
				tongtientheodk += ttTheodk;
				this.dieuChinhKM((Dieukienkhuyenmai)ctkm.getDkkhuyenmai().get(i), hashSanpham, HashB, sl, ctkm);
								
				if(i > 0 && pt == "&&") //dieu kien and cac sanpham
				{
					//cap nhat lai so xuat km
					soXuatKM = min(soXuatKM, sl);
					for(int j = i; j >= 0; j--)
					{							
						//dieu chinh lai slg sanpham trong hashSanpham (phai thay doi theo soxuat)
						this.dieuChinhKM2((Dieukienkhuyenmai)ctkm.getDkkhuyenmai().get(j), hashSanpham, soXuatKM);
					}
				}
				else //dieu kien or
				{
					soXuatKM += sl;
				}
			}
			dieukien.add(flag);	
		}	
		
		//tong hop cac dieu kien
		ctkhuyenmai.setId(ctkm.getId());
		ctkhuyenmai.setscheme(ctkm.getscheme());	
		ctkhuyenmai.setTungay(ctkm.getTungay());
		ctkhuyenmai.setDenngay(ctkm.getDenngay());
		ctkhuyenmai.setLoaict(ctkm.getLoaict());
		ctkhuyenmai.setNgansach(ctkm.getNgansach());
		ctkhuyenmai.setDasudung(ctkm.getDasudung());
		ctkhuyenmai.setDiengiai(ctkm.getDiengiai());
		ctkhuyenmai.setTra_OR(ctkm.getTra_OR());
		ctkhuyenmai.setPhanbotheoDH(ctkm.getPhanbotheoDH());
		
		//System.out.println("Tong tien theo dieu kien khuyen mai la: " + tongtientheodk + "\n");
		
		if((checkDieuKien(dieukien, pheptoan) == false))
		{				
			if(this.dieuchinhKM == true)  //under change, phai thay doi truc tiep HashA, ko thong wa tham chieu duoc...
			{
				if(ctkm.getLoaict() == 1 ) //khuyen mai binh thuong
					this.HashA = copyHashtable(copySp);
				else
					this.HashC = copyHashtable(copySp);
			}
			ctkhuyenmai.setSoxuatKM(0);
			ctkhuyenmai.setTongTienTheoDKKM(0);
		}
		else
		{
			ctkhuyenmai.setSoxuatKM(soXuatKM);
			ctkhuyenmai.setTongTienTheoDKKM(tongtientheodk); //truong hop tra chiet khau
			ctkhuyenmai.setDkkhuyenmai(ctkm.getDkkhuyenmai());
			ctkhuyenmai.setTrakhuyenmai(ctkm.getTrakhuyenmai());
		}	
		return ctkhuyenmai;
	}
		
	public Hashtable<String, Integer> copyHashtable(Hashtable<String, Integer> hash)
	{
		Hashtable<String, Integer> copy = new Hashtable<String, Integer>();		
		Enumeration<String> keys = hash.keys();
		while(keys.hasMoreElements())
		{
			String key = keys.nextElement();
			copy.put(key, hash.get(key));
		}
		return copy;
	}
	
	private String checkDKKM_SP(Dieukienkhuyenmai dkkm, Hashtable<String,Float> sanpham_dongia, Hashtable<String,Integer> sanpham)
	{
		int soxuatKm = 0;
		float tongtientheoDK = 0;
		Hashtable<String,Integer> sp_sl = dkkm.getSanpham_Soluong();
		
		if(sp_sl.size() > 0)
		{			
			if(sanpham.size() < sp_sl.size())
				return "0-0";
			
			float min = Integer.MAX_VALUE; 
			Enumeration<String> keyList = sp_sl.keys();
			while(keyList.hasMoreElements())
			{
				String key = keyList.nextElement();
				if(sanpham.get(key) == null)
					return "0-0"; //khong co san pham nay trong dkkhuyenmai bat buoc
				else
				{
					float soSpA = 0;
					if(dkkm.getIsThung().equals("0"))
					{
						soSpA = sanpham.get(key);
					}
					else
					{
						soSpA = sanpham.get(key) / this.Hash_QuyCach.get(key);
					}
					
					float soSpPhaiThoa = sp_sl.get(key);
					
					if(soSpA < soSpPhaiThoa)
						return "0-0";	
					else
					{
						float k = soSpA / soSpPhaiThoa;
						
						tongtientheoDK += sanpham.get(key) * sanpham_dongia.get(key);
						if(k < min)
							min = k;
					}
				}
			}				
			soxuatKm = (int)min;	
		}	
		//long tongtiendh = Math.round(tongtientheoDK * 1.1);
		long tongtiendh = Math.round(tongtientheoDK);
		
		return Integer.toString(soxuatKm) + "-" + Long.toString(tongtiendh);
	}
	
	private String checkDKKM_TongLuong(Dieukienkhuyenmai dkkm, Hashtable<String,Float> sanpham_dongia, Hashtable<String, Integer> sanpham_soluong)
	{
		int soxuatKm = 0;
		double tongtientheoDK = 0;
		Hashtable<String,Integer> sanpham = dkkm.getSanpham_Soluong(); //phai la nhung san pham trong dkkm truoc
		
		if(sanpham.size() > 0)
		{
			float tongluong = (float)dkkm.getTongluong();
			//System.out.print("Tong Luong Theo DKKM: " + Integer.toString(tongluong) + "\n");
			float sum = 0;
			int minSP = Integer.MAX_VALUE; //soxuat bat buoc phai co mat day du cac sanpham trong dkkm
			
			Enumeration<String> keyList = sanpham.keys();	
			while(keyList.hasMoreElements())
			{
				String key = keyList.nextElement();
				
				if(dkkm.getType() == 2)
				{
					//qui dinh mua bat ky sanpham a, sanpham b, sanpham c.... trong nhom ABC dat tongluong = xyz
					if(sanpham_soluong.get(key) != null)
					{
						if(dkkm.getIsThung().equals("0"))
						{
							sum += sanpham_soluong.get(key);
						}
						else
						{
							sum += sanpham_soluong.get(key) / this.Hash_QuyCach.get(key);
						}
						
						tongtientheoDK += sanpham_soluong.get(key) * sanpham_dongia.get(key);
					}
				}
				else
				{
					//qui dinh mua dung sanpham a, sanpham b, sanpham c trong nhom ABC dat tongluong = xyz
					if(!sanpham_soluong.containsKey(key))
						return "0-0";
					if(sanpham_soluong.get(key) <= 0)
						return "0-0";	
					
					if(dkkm.getIsThung().equals("0"))
					{
						sum += sanpham_soluong.get(key);
					}
					else
					{
						sum += sanpham_soluong.get(key) / this.Hash_QuyCach.get(key);
					}
					
					tongtientheoDK += sanpham_soluong.get(key) * sanpham_dongia.get(key);
					
					if(sanpham_soluong.get(key) < minSP)
						minSP = sanpham_soluong.get(key);
				}				
			}
			//System.out.print("dk khuyen mai " + dkkm.getId() + " ---- Tong thung " + sum + ", Phai dat: " + tongluong + "\n");
			if(sum < tongluong)
				return "0-0";
			soxuatKm = min((int)(sum / tongluong), minSP);
		}
		
		//long tongtiendh = Math.round(tongtientheoDK * 1.1);
		long tongtiendh = Math.round(tongtientheoDK);
		System.out.println("TOng gia tri cua cac san pham mua la: " + tongtiendh);
		
		return Integer.toString(soxuatKm) + "-" + Long.toString(tongtiendh);
	}
		
	private String checkDKKM_TongTien(Dieukienkhuyenmai dkkm, Hashtable<String,Float> sanpham_dongia, Hashtable<String, Integer> sanpham_soluong)
	{
		/*Enumeration<String> keys = sanpham_soluong.keys();
		while(keys.hasMoreElements())
		{
			String key = keys.nextElement();
			System.out.println("__________San pham: " + key + ", So luong: " + sanpham_soluong.get(key));
		}*/
		
		int soxuatKm = 0;
		long tongtientheodk = 0;
		Hashtable<String,Integer> sanpham = dkkm.getSanpham_Soluong(); //phai la nhung san pham trong dkkm truoc
		if(sanpham.size() > 0)
		{
			float tongtien = dkkm.getTongtien();
			float sum = 0F;			
			int minSP = Integer.MAX_VALUE; //soxuat bat buoc phai co mat day du cac sanpham trong dkkm
			
			Enumeration<String> keyList = sanpham.keys();
			while(keyList.hasMoreElements())
			{
				String key = keyList.nextElement();
				
				if(dkkm.getType() == 2)
				{
					//Mua 1, 2, 3...sp bat ky trong nhom san pham
					if(sanpham_dongia.get(key) != null)
					{
						System.out.println("-----Su dung san pham: " + key + " -- So luong: " + sanpham_soluong.get(key) + " -- Don gia: " + sanpham_dongia.get(key));
						sum += sanpham_dongia.get(key) * sanpham_soluong.get(key);
					}
				}
				else
				{
					//dk mua sp qui dinh trong nhom sp dat sotien > y  (co dinh san pham)
					if(!sanpham_dongia.containsKey(key))
						return "0-0"; //so xuat - tong tien theo dieu kien
					if(sanpham_soluong.get(key) <= 0)
						return "0-0";
					sum += sanpham_dongia.get(key) * sanpham_soluong.get(key); //tong so tien cua tat ca sp trong don hang
					
					if(sanpham_soluong.get(key) < minSP)
						minSP = sanpham_soluong.get(key);
				}
			}
			
			//double tongtiendonhang = sum * 1.1;
			double tongtiendonhang = sum;
			tongtientheodk = Math.round(tongtiendonhang);
			System.out.println("Tong tien theo dieu kien khuyen mai (" + dkkm.getDiengiai() + ") trong ham la: " + tongtientheodk + "\n");
			
			if(Math.round(tongtiendonhang) < tongtien)
				return "0-0";
			soxuatKm = min((int)(Math.round(tongtiendonhang) / tongtien), minSP);
		}				
		//return soxuatKm;
		return Integer.toString(soxuatKm) + "-" + Long.toString(tongtientheodk);
	}
	
	private boolean checkDieuKien(ArrayList<Boolean> dieukien, ArrayList<String> pheptoan)
	{
		if(dieukien.size() <= 1)
		{
			if(dieukien.contains(true))
				return true;
			return false;
		}	
		int i = 0;
		while( i < dieukien.size())
		{
			int j = i + 1;
			if(j < pheptoan.size())
			{
				if(pheptoan.get(j).equals("&&"))
					dieukien.set(j, dieukien.get(i) && dieukien.get(j));
				else
					dieukien.set(j, dieukien.get(i) || dieukien.get(j));
			}
			i++;
		}
		return dieukien.get(dieukien.size() - 1);
	}
	
	private void dieuChinhKM(Dieukienkhuyenmai dkkm, Hashtable<String, Integer> sp_sl, Hashtable<String, Float> sp_dongia, int soXuatKM, ICtkhuyenmai ctkm)
	{
		Hashtable<String, Integer> sanpham_sl = dkkm.getSanpham_Soluong();
		List<ISanpham> sanphamList = new ArrayList<ISanpham>();
		
		if(dkkm.getTongtien() <= 0)
		{
			if(dkkm.getTongluong() > 0) //truong hop dkkm co set tongluong
			{		
				Sanpham[] sanpham = getSanpham_slg(dkkm, sp_sl, soXuatKM);
				
				/*for(int i = 0; i < sanpham.length; i++)
				{
					System.out.println("----4444-----Dieu kien: " + dkkm.getDiengiai() + " : su dung " + sanpham[i].getMasp() + ", voi so luong: " + sanpham[i].getSoluongThungCan() );
				}*/
				
				if(sanpham != null)
				{
					for(int i=0; i < sanpham.length; i++)
					{
						String key = sanpham[i].getMasp();
						if(sp_sl.containsKey(key))
						{
							int slgconlai = 0;
							int soluongsudung = 0;
							
							if(dkkm.getIsThung().equals("0"))
							{
								slgconlai = (int) ( sp_sl.get(key) -  sanpham[i].getSoluongThungCan() );
								soluongsudung = (int)(sanpham[i].getSoluongThungCan());
								/*slgconlai = sp_sl.get(key) - sanpham[i].getSoluongcan();
								soluongsudung = sanpham[i].getSoluongcan();*/
							}
							else
							{
								slgconlai = (int) (sp_sl.get(key) - ( sanpham[i].getSoluongThungCan() * ( this.Hash_QuyCach.get(key)) ));
								soluongsudung = (int)(sanpham[i].getSoluongThungCan() * this.Hash_QuyCach.get(key));
							}
							
							//System.out.print("110.San pham " + key + " --- Slg ban dau: " + sp_sl.get(key) + " --- Con lai " + slgconlai + "\n");
							
							//luu lai thong tin sl sanpham su dung, sl con lai
							if(ctkm.getTrakhuyenmai().get(0).getChietkhau() > 0)
							{
								System.out.println("-----------------CTKM nay la CK-------------------------------");
								slgconlai = 0;
							}
							
							Sanpham sp = new Sanpham(key, sanpham[i].getTensp(), soluongsudung, sp_dongia.get(key), slgconlai, true);
							
							//System.out.print("111.San pham " + sp.getMasp() + " --- Slg can " + sp.getSoluongcan() + " --- Avaiable " + sp.getSoluongAvaiable() + "\n");
							System.out.print("111.San pham " + key + " --- Slg su dung " + soluongsudung + " --- Avaiable " + slgconlai + "\n");
							
							sp_sl.put(key, slgconlai); //dieu chinh lai soluong tuong ung
							sanphamList.add(sp);
							
						}
					}
				}
			}
			else
			{
				Enumeration<String> keyList = sanpham_sl.keys();
				while(keyList.hasMoreElements())
				{
					String key = keyList.nextElement();
					if(sp_sl.containsKey(key))
					{
						int slgconlai = 0;
						int soluongsudung = 0;
						
						if(dkkm.getIsThung().equals("0"))
						{
							slgconlai = sp_sl.get(key) - sanpham_sl.get(key) * soXuatKM;
							soluongsudung = sanpham_sl.get(key) * soXuatKM;
						}
						else
						{
							slgconlai = (int) (sp_sl.get(key) - sanpham_sl.get(key) * this.Hash_QuyCach.get(key) * soXuatKM);
							soluongsudung = (int)( sanpham_sl.get(key) * this.Hash_QuyCach.get(key) * soXuatKM );
							
						}
						
						//luu lai thong tin sl sanpham su dung, sl con lai
						ResultSet rs = db.get("select ten from sanpham where ma='" + key + "'");
						String tensp = "";
						try 
						{
							rs.next();
							tensp = rs.getString("ten");
							rs.close();
						} 
						catch (SQLException e) {}
						
						if(ctkm.getTrakhuyenmai().get(0).getChietkhau() > 0)
						{
							System.out.println("-----------------CTKM nay la CK-------------------------------");
							slgconlai = 0;
						}
						
						Sanpham sp = new Sanpham(key, tensp, soluongsudung, sp_dongia.get(key), slgconlai, false);
						
						sp_sl.put(key, slgconlai);
						sanphamList.add(sp);
					}
				}
			}
		}
		else //truong hop tong tien
		{
			Sanpham[] sanpham = getSanpham_soluong(dkkm, sp_sl, soXuatKM);
			if(sanpham != null)
			{
				for(int i=0; i < sanpham.length; i++)
				{
					String key = sanpham[i].getMasp();
					if(sp_sl.containsKey(key))
					{
						/*int slgconlai = sp_sl.get(key) - sanpham[i].getSoluongcan();
						//luu lai thong tin sl sanpham su dung, sl con lai
						Sanpham sp = new Sanpham(key, sanpham[i].getTensp(), sanpham[i].getSoluongcan(), sp_dongia.get(key), slgconlai, false);*/
						
						int slgconlai = 0;
						int soluongsudung = 0;
						
						if(dkkm.getIsThung().equals("0"))
						{
							/*slgconlai = (int) ( sp_sl.get(key) -  sanpham[i].getSoluongThungCan() );
							soluongsudung = (int)(sanpham[i].getSoluongThungCan());*/
							slgconlai = sp_sl.get(key) - sanpham[i].getSoluongcan();
							soluongsudung = sanpham[i].getSoluongcan();
						}
						else
						{
							slgconlai = (int) (sp_sl.get(key) - ( sanpham[i].getSoluongThungCan() * ( this.Hash_QuyCach.get(key)) ));
							soluongsudung = (int)(sanpham[i].getSoluongThungCan() * this.Hash_QuyCach.get(key));
							
							System.out.print("110.San pham " + key + " --- Slg thung can " + sanpham[i].getSoluongThungCan() + " --- Quy Cach " + this.Hash_QuyCach.get(key) + "\n");
						}
						
						if(ctkm.getTrakhuyenmai().get(0).getChietkhau() > 0)
						{
							System.out.println("-----------------CTKM nay la CK-------------------------------");
							slgconlai = 0;
						}
						
						Sanpham sp = new Sanpham(key, sanpham[i].getTensp(), soluongsudung, sp_dongia.get(key), slgconlai, false);
						
						//System.out.print("\n" + sanpham[i].getTensp() + " -- " + sanpham[i].getSoluongcan() + "\n");
						
						System.out.print("111.San pham " + key + " --- Slg su dung " + soluongsudung + " --- Avaiable " + slgconlai + "\n");
						
						sp_sl.put(key, slgconlai);
						sanphamList.add(sp);
					}
				}
			}
		}
		
		for(int i = 0; i < sanphamList.size(); i++)
		{
			ISanpham sp = sanphamList.get(i);
			System.out.println("3333.Dieu kien Id: " + dkkm.getId() + ", Su dung san pham: " + sp.getMasp() + ", Voi so luong: " + sp.getSoluongcan() + ", Thung tuong ung: " + sp.getSoluongThungAvaiable());
		}
		
		dkkm.setSanphamList(sanphamList);
	}
	
	private void dieuChinhKM2(Dieukienkhuyenmai dkkm,  Hashtable<String, Integer> hashSanpham, int soXuat)
	{
		int old = dkkm.getSoxuatKM();
		List<ISanpham> sanpham = dkkm.getSanphamList();
		
		for(int i = 0; i < sanpham.size(); i++ )
		{
			Sanpham sp = (Sanpham)sanpham.get(i);
			if(dkkm.getType() == 2 ) //mua san pham co slg bat ky trong nhom san pham...
			{
				if(dkkm.getTongluong() > 0)
				{
					int sum = dkkm.getTongluong() * soXuat;
					if(sum > sp.getSoluongcan())
						sum = sum - sp.getSoluongcan();
					else
					{						
						int slgCan = sp.getSoluongcan();
						int avai = sp.getSoluongAvaiable();
						sp.setSoluongcan(sum);
						sp.setSoluongAvaiable(avai + slgCan - sum);
						
						//System.out.print(sp.getMasp() + " ---- " + sp.getSoluongcan() + " ----- " + sp.getSoluongAvaiable() + "\n"); 
						
						//dieu chinh lai hashSanpham
						hashSanpham.put(sp.getMasp(), hashSanpham.get(sp.getMasp()) + slgCan - sum);
						
						for(int j = i + 1; j < sanpham.size(); j++)
						{
							int slgCanOld = sanpham.get(j).getSoluongcan();
							sanpham.get(j).setSoluongcan(0);
							sanpham.get(j).setSoluongAvaiable(sanpham.get(j).getSoluongAvaiable() + slgCanOld);
							
							hashSanpham.put(sanpham.get(j).getMasp(), hashSanpham.get(sanpham.get(j).getMasp()) + slgCanOld);
							
							//System.out.print("So luong can Old: " + Integer.toString(slgCanOld) + " ---- Masp: " + sanpham.get(j).getMasp() + " ---- " + sanpham.get(j).getSoluongcan() + " ----- " + sanpham.get(j).getSoluongAvaiable() + "\n"); 
						}
						dkkm.setSoxuatKM(soXuat);
						return;
					}
				}
			}
			else //mua sp co soluong bat buoc
			{
				int slgCan = sp.getSoluongcan() / old;
				int sum = sp.getSoluongcan() + sp.getSoluongAvaiable();
				
				dkkm.getSanphamList().get(i).setSoluongcan(slgCan * soXuat);
				dkkm.getSanphamList().get(i).setSoluongAvaiable(sum - (slgCan * soXuat));
				
				//dieu chinh lai hashSanpham
				hashSanpham.put(sp.getMasp(), hashSanpham.get(sp.getMasp()) + (old - soXuat) * slgCan);
			}
			//System.out.print("So luong sau dieu chinh 2 la: " + sp.getMasp() + " --- " + Integer.toString(slgCan * soXuat) + "\n");
		}
		dkkm.setSoxuatKM(soXuat);
	}
	
	private Sanpham[] InitSanPham(Dieukienkhuyenmai dkkm, Hashtable<String, Integer> sanpham_sl, boolean flag)
	{
		Hashtable<String, Integer> sp_sl = dkkm.getSanpham_Soluong();
		Hashtable<String, Float> sp_trongso = dkkm.getSanpham_Trongso();
		
		Sanpham[] sanpham = new Sanpham[sp_sl.size()];

		int m = 0;
		Enumeration<String> keyList = sp_sl.keys();
		while(keyList.hasMoreElements())
		{
			String key = keyList.nextElement();
			int slAvaiable = 0;
			if(sanpham_sl.get(key) != null)
				slAvaiable = sanpham_sl.get(key);
			
			float slThungAvai = 0;
			if(sanpham_sl.get(key) != null)
			{
				if(dkkm.getIsThung().equals("0"))
					slThungAvai = sanpham_sl.get(key);
				else
				{
					slThungAvai = sanpham_sl.get(key) / this.Hash_QuyCach.get(key);
				}
			}
			
			float dongia = 0f;
			if(HashB.get(key) != null)
				dongia = HashB.get(key);
			
			ResultSet rs = db.get("select ten from sanpham where ma='" + key + "'");
			String tensp = "";
			try {
				rs.next();
				tensp = rs.getString("ten");
				rs.close();
			} catch (SQLException e) {}
						
			if(dkkm.getType() == 2) //sp co soluong bat ky
			{
				sanpham[m] = new Sanpham(key, tensp, 0, dongia, slAvaiable, flag);
				sanpham[m].setSoluongThungAvaiable(slThungAvai);
				sanpham[m].setSoluongThungCan(0);
				sanpham[m].setTrongso(sp_trongso.get(key));
			}
			else
			{
				sanpham[m] = new Sanpham(key, tensp, 1, dongia, slAvaiable, flag); //toi thieu phai co 1sp de thoa dk	
				sanpham[m].setSoluongThungAvaiable(slThungAvai);
				sanpham[m].setSoluongThungCan(1);
				sanpham[m].setTrongso(sp_trongso.get(key));
			}
			m++;					
		}
		
		Arrays.sort(sanpham);
		
		/*for(int i = 0; i < sanpham.length; i++)
		{
			System.out.println("__Thu tu: " + i + " -- " + sanpham[i].getMasp());
		}*/

		return sanpham;
	}
	
	/******** Chua dung toi *********/
	private List<ISanpham> convertToList(Dieukienkhuyenmai dkkm, Hashtable<String, Integer> sanpham_sl)
	{
		List<ISanpham> spList = new ArrayList<ISanpham>();
		
		Hashtable<String, Float> sp_trongso = dkkm.getSanpham_Trongso();
		
		Enumeration<String> keyList = sanpham_sl.keys();
		while(keyList.hasMoreElements())
		{
			String key = keyList.nextElement();
			ISanpham sp;
			
			if(sp_trongso.get(key) != null)
			{
				if(sp_trongso.get(key) > 0)
				{
					sp = new Sanpham();
					
					sp.setMasp(key);
					sp.setSoluongcan(sanpham_sl.get(key));
					
					spList.add(sp);
				}
			}
		}
		
		keyList = sanpham_sl.keys();
		while(keyList.hasMoreElements())
		{
			String key = keyList.nextElement();
			boolean flag = checkMaSP(key, spList);
			
			if(!flag)
			{
				ISanpham sp = new Sanpham();
		
				sp.setMasp(key);
				sp.setSoluongcan(sanpham_sl.get(key));
						
				spList.add(sp);
			}
		}
		
		return spList;
	}
	
	private boolean checkMaSP(String key, List<ISanpham> spList)
	{
		for(int i = 0; i < spList.size(); i++)
		{
			if(spList.get(i).getMasp().equals(key) )
				return true;
		}
		return false;
	}
	/******** End Chua dung toi *********/
	
	
	private Sanpham[] getSanpham_soluong(Dieukienkhuyenmai dkkm, Hashtable<String, Integer> sanpham_sl, int soxuatKm) //truong hon tong tien > 0
	{
		Sanpham[] sanpham = InitSanPham(dkkm, sanpham_sl, false); //Sort sanpham tang dan theo tongtien
		if(sanpham == null)
			return null;
		
		float tongtienKM = dkkm.getTongtien() * soxuatKm; //tong tien theo tat ca cac xuat khuyenmai duoc huong
		
		for(int i = 0; i < sanpham.length; i++)
		{
			float sum = getTongtien(sanpham);
			sum = sum - (sanpham[i].getDongia() * sanpham[i].getSoluongcan());
			
			int soluongcan = min(sanpham[i].getSoluongAvaiable(), (int)((tongtienKM - sum) / sanpham[i].getDongia()));
			sanpham[i].setSoluongcan(soluongcan);
			/*
			if(i==(sanpham.length - 1))
			{
				if(getTongtien(sanpham) < tongtienKM)
					sanpham[i].setSoluongcan(sanpham[i].getSoluongcan() + 1);
			}
			*/
		}
		return sanpham;
	}
	
	public Sanpham[] getSanpham_slg(Dieukienkhuyenmai dkkm, Hashtable<String, Integer> sanpham_sl, int soxuatKm) //truong hop tongluong > 0
	{
		Sanpham[] sanpham = InitSanPham(dkkm, sanpham_sl, true); //Sort sanpham tang dan theo trong so & soluong

		if(sanpham == null)
			return null;
		
		float tongluongKM = dkkm.getTongluong() * soxuatKm; //tong luong theo tat ca cac xuat khuyenmai duoc huong
		
		for(int i = 0; i < sanpham.length; i++)
		{
			float sum = getTongluong(sanpham);
			
			sum = sum - sanpham[i].getSoluongThungCan();
			
			//System.out.println("113. Sum: " + sum + ", Tong luong: " + tongluongKM + ", Avai:  " + sanpham[i].getSoluongThungAvaiable());
			float soluongcan = min(sanpham[i].getSoluongThungAvaiable(), tongluongKM - sum);
			sanpham[i].setSoluongThungCan(soluongcan);
			
			//System.out.print("115.San pham " + sanpham[i].getMasp() + " --- Slg can " + sanpham[i].getSoluongcan() + " --- Avaiable " + sanpham[i].getSoluongAvaiable() + "\n");
			//System.out.print("---116.San pham " + sanpham[i].getMasp() + " --- Slg can " + sanpham[i].getSoluongThungCan()+ " --- Avaiable " + sanpham[i].getSoluongThungAvaiable() + "\n");
			if(i==(sanpham.length - 1))
			{
				if(getTongluong(sanpham) < tongluongKM)
				{
					if(dkkm.getIsThung().equals("0"))
					{
						sanpham[i].setSoluongcan(sanpham[i].getSoluongcan() + 1);
					}
				}
			}
		}
		return sanpham;
	}
	
	private int min(int a, int b)
	{
		return (a >= b ? b : a);
	}
	
	private float min(float a, float b)
	{
		return (a >= b ? b : a);
	}
	
	private float getTongtien(Sanpham[] sanpham)
	{
		float sum = 0f;
		for(int j = 0; j < sanpham.length; j++)
		{
			sum += (sanpham[j].getSoluongcan() * sanpham[j].getDongia());
		}
		return sum;
	}
	
	private float getTongluong(Sanpham[] sanpham)
	{
		float sum = 0;
		for(int j = 0; j < sanpham.length; j++)
		{
			sum += (sanpham[j].getSoluongThungCan());
		}
		return sum;
	}
	
	//Sort theo Scheme nao uu tien truoc
	public List<ICtkhuyenmai> SortList(List<ICtkhuyenmai> list, String[] scheme)
	{
		//List<ICtkhuyenmai> list = List;
		List<ICtkhuyenmai> ctkmList = new ArrayList<ICtkhuyenmai>();
		int k = 0;
		for(int i = 0; i < scheme.length; i++)
		{
			for(int j = 0; j < list.size(); j++)
			{
				Ctkhuyenmai ctkm = (Ctkhuyenmai)list.get(j);
				if(ctkm.getscheme().equals(scheme[i]))
				{
					ctkmList.add(k, ctkm);
					k ++;
					//list.remove(j);
				}
			}
		}		
		for(int j = 0; j < list.size(); j++)
		{
			Ctkhuyenmai ctkm = (Ctkhuyenmai)list.get(j);
			if(!ctkmList.contains(ctkm))
			{ 
				ctkmList.add(ctkm);
			}
		}

		System.out.println("7878787878. SO CTKM SAU KHI SORT: " + ctkmList.size());
		return ctkmList;
	}
	
	public Hashtable<String,Integer> getHashA()
	{
		return this.HashA;
	}
	
	public void setHashA(Hashtable<String,Integer> hash)
	{
		this.HashA = hash;
		
		//dung trong truong hop Ontop
		this.HashC = this.copyHashtable(HashA);
	}
	
	public Hashtable<String,Float> getHashB()
	{
		return this.HashB;
	}
	
	public void setHashB(Hashtable<String,Float> hash)
	{
		this.HashB = hash;
	}
	
	public Hashtable<String,Float> getHash_QuyCach()
	{
		return this.Hash_QuyCach;
	}
	
	public void setHash_QuyCach(Hashtable<String,Float> hashThung)
	{
		this.Hash_QuyCach = hashThung;
	}
	
	public List<ICtkhuyenmai> getCtkmList()
	{
		return this.ctkmList;
	}
	
	public void setCtkmList(List<ICtkhuyenmai> list)
	{
		this.ctkmList.clear();
		this.ctkmList = list;
		
		this.ctkmBTList.clear();
		this.ctkmOntopList.clear();
		this.ctkmCKList.clear();
		
		/*for(int i = 0; i < this.ctkmList.size(); i++)
		{
			ICtkhuyenmai ctkm = this.ctkmList.get(i);
			if(ctkm.getLoaict() == 1)
			{
				System.out.println("111111.CTKM: " + ctkm.getscheme() + " : " + ctkm.getCK());
				if(ctkm.getCK() > 0)
					this.ctkmCKList.add(ctkm);
				else
					this.ctkmBTList.add(ctkm);
				//this.ctkmBTList.add(ctkm);
			}
			else
				this.ctkmOntopList.add(ctkm);
		}*/
		
		//System.out.println("222-222.CTKM Binh Thuong: " + this.ctkmBTList.size());
		//System.out.println("333-333.CTKM Binh Thuong Chiet Khau: " + this.ctkmCKList.size());
	}	
	
	public void setKhachhang(String khachhang)
	{
		this.khachhang = khachhang;
	}
	
	public String getKhachhang()
	{
		return this.khachhang;
	}
	
	public List<ICtkhuyenmai> getCtkmResual()
	{
		return this.ctkmResual;
	}	
	
	public void setCtkmResual(List<ICtkhuyenmai> list)
	{
		this.ctkmResual = list;
	}	
	
	public boolean getDieuchinh()
	{
		return this.dieuchinhKM;
	}	
	
	public void setDieuchinh(boolean flag)
	{
		this.dieuchinhKM = flag;
	}
	
	public boolean getDungDieuKien()
	{
		return this.dungdieukien;
	}	
	
	public int getRowspan(Ctkhuyenmai ctkm)
	{
		int num = 0;
		List<IDieukienkhuyenmai> listDK = ctkm.getDkkhuyenmai();
		for(int i = 0; i < listDK.size(); i++)
		{
			Dieukienkhuyenmai dkkm = (Dieukienkhuyenmai)listDK.get(i);
			if(dkkm.getSoxuatKM() > 0)
			{
				List<ISanpham> sanpham = dkkm.getSanphamList();
				num += sanpham.size();				
			}
			else
				num += 1;
		}
		return num;
	}	
	
	public float getTonggiatriDh()
	{
		return this.tonggiatriDh;
	}
	
	public void setTonggiatriDh(float tonggiatri)
	{
		this.tonggiatriDh = tonggiatri;
	}
	
	public float getTonggiatri(Dieukienkhuyenmai dkkm)
	{
		float sum = 0;
		List<ISanpham> spList = dkkm.getSanphamList();
		for(int i = 0; i < spList.size(); i++)
		{
			Sanpham sp = (Sanpham)spList.get(i);
			sum += sp.getSoluongcan() * sp.getDongia();
		}
		return sum;
	}
	
	public int getTongsoluong(Dieukienkhuyenmai dkkm)
	{
		int sum = 0;
		List<ISanpham> spList = dkkm.getSanphamList();
		for(int i = 0; i < spList.size(); i++)
		{
			Sanpham sp = (Sanpham)spList.get(i);
			sum += sp.getSoluongcan();
		}
		return sum;
	}
	
	public boolean checkConfirm() 
	{
		boolean flag = false;
		if(this.ctkmResual.size() == 1)
		{
			ICtkhuyenmai ctkm = this.ctkmResual.get(0);
			
			List<ITrakhuyenmai> trakmList = ctkm.getTrakhuyenmai();
			for(int i = 0; i < trakmList.size(); i++)
			{
				//ITrakhuyenmai trakm = ctkm.getTrakhuyenmai().get(0);
				ITrakhuyenmai trakm = ctkm.getTrakhuyenmai().get(i);
				
				if(trakm.getPheptoan()== 2)
				{
					this.ctkmResual.get(0).setConfirm(true);
					flag = true;
				}
				
				//ctkhuyenmai phai lua chon sanpham
				if(trakm.getType() == 3 && trakm.getHinhthuc() == 2)
				{
					//cai tien: neu duoi nhapp da chon sanpham cho loai trakm la trakhuyenmai co chon sp
					boolean selected = checkTrakm(ctkm, trakm);
					if(selected == false)
					{
						this.ctkmResual.get(0).setConfirm(true);
						flag = true;
					}
				}
			}
		}
		
		
		for(int i = 0; i < this.ctkmResual.size() - 1; i++)
		{
			ICtkhuyenmai ctkm = this.ctkmResual.get(i);
			List<IDieukienkhuyenmai> dkkmList = ctkm.getDkkhuyenmai();
			
			List<ITrakhuyenmai> trakmList = ctkm.getTrakhuyenmai();
			
			for(int j = 0; j < trakmList.size(); j++)
			{
				ITrakhuyenmai trakm = ctkm.getTrakhuyenmai().get(j);
				
				if(trakm.getPheptoan()== 2)
				{
					this.ctkmResual.get(i).setConfirm(true);
					flag = true;
				}
				
				
				//ct khuyenmai phai lua chon sanpham
				if(trakm.getType() == 3 && trakm.getHinhthuc() == 2)
				{
					boolean selected = checkTrakm(ctkm, trakm);
					if(selected == false)
					{
						this.ctkmResual.get(i).setConfirm(true);
						flag = true;
					}
				}
			}
			
			for(int j = i+1; j < this.ctkmResual.size(); j++)
			{
				ICtkhuyenmai ctkm2 = this.ctkmResual.get(j);
				List<IDieukienkhuyenmai> dkList = ctkm2.getDkkhuyenmai();
				
				ITrakhuyenmai trakm2 = ctkm2.getTrakhuyenmai().get(0);
				
				if(trakm2.getPheptoan()== 2)
				{
					this.ctkmResual.get(i).setConfirm(true);
					flag = true;
				}
				
				
				if(trakm2.getType() == 3 && trakm2.getHinhthuc() == 2)
				{
					boolean selected = checkTrakm(ctkm2, trakm2);
					if(selected == false)
					{
						this.ctkmResual.get(j).setConfirm(true);
						flag = true;
					}
				}
				
				if(ctkm2.getLoaict() == ctkm.getLoaict() )
				{
					if(checkDkConfirm(dkkmList, dkList))
					{
						this.ctkmResual.get(i).setConfirm(true);
						this.ctkmResual.get(j).setConfirm(true);
						flag = true;
						this.dungdieukien = true;
					}
				}


				
				/*if(ctkm2.getLoaict() != 2)
				{
					if(checkDkConfirm(dkkmList, dkList))
					{
						this.ctkmResual.get(i).setConfirm(true);
						this.ctkmResual.get(j).setConfirm(true);
						flag = true;
						this.dungdieukien = true;
					}
				}
				else
				{
					if(this.ctkmOntopResual.size() >= 2) //neu ontop chi co 1 ct thi ko tinh la dung san pham
					{
						if(checkDkConfirm(dkkmList, dkList))
						{
							this.ctkmResual.get(i).setConfirm(true);
							this.ctkmResual.get(j).setConfirm(true);
							flag = true;
							this.dungdieukien = true;
						}
					}
				}*/
			}
		}
		
		System.out.println("Confgi la: " + flag);
		return flag;
	}
	
	private boolean checkTrakm(ICtkhuyenmai ctkm, ITrakhuyenmai trakm) 
	{
		String query = "select COUNT(*) as sodong from TRAKM_NHAPP where trakm_fk = '" + trakm.getId() + "' and npp_fk = '" + this.nppId + "' and ctkm_fk = '" + ctkm.getId() + "'";
		System.out.println("Check tra khuyen mai: " + query + "\n");
		ResultSet rs = db.get(query);
		int sodong = 0;
		if(rs != null)
		{
			try
			{
				if(rs.next())
				{
					sodong = rs.getInt("sodong");
				}
				rs.close();
			} 
			catch (SQLException e) {}
		}
		if(sodong > 0)
		{
			System.out.println("Query la true\n");
			return true;
		}
		System.out.println("Query la false\n");
		return false;
	}

	private boolean checkDkConfirm(List<IDieukienkhuyenmai> dkkm1, List<IDieukienkhuyenmai> dkkm2)
	{
		Hashtable<String, Integer> spList = new Hashtable<String, Integer>();
		for(int i=0; i < dkkm1.size(); i++)
		{   
			IDieukienkhuyenmai dkkm = dkkm1.get(i);
			for(int j = 0; j < dkkm.getSanphamList().size(); j++)
			{
				ISanpham sp = dkkm.getSanphamList().get(j);
				System.out.println("DKKM 1: SP ( " + sp.getMasp() + " ) - SO LUONG: ( " + sp.getSoluongcan() + " ) ");
				if(sp.getSoluongcan() > 0)
				{
					if(!spList.containsKey(sp.getMasp().trim()))
					{
						spList.put(sp.getMasp().trim(), sp.getSoluongcan());
					}
				}
			}
		}
		
		for(int i=0; i < dkkm2.size(); i++)
		{
			IDieukienkhuyenmai dk = dkkm2.get(i);
			
			for(int j = 0; j < dk.getSanphamList().size(); j++)
			{
				ISanpham sp = dk.getSanphamList().get(j);
				System.out.println("DKKM 2: SP ( " + sp.getMasp() + " ) - SO LUONG: ( " + sp.getSoluongcan() + " ) ");
				if(sp.getSoluongcan() > 0 && spList.containsKey(sp.getMasp()))
				{
					System.out.println("____Ket qua check DKKM trung la: true\n");
					return true;
				}	
			}
			
		}
		
		System.out.println("____Ket qua check DKKM la: false\n");
		return false;
	}
	
	
	private boolean checkDkConfirm2(List<IDieukienkhuyenmai> dkkm1, List<IDieukienkhuyenmai> dkkm2)
	{
		/*List<IDieukienkhuyenmai> dkkm1 = ct.getDkkhuyenmai();
		List<IDieukienkhuyenmai> dkkm2 = ct2.getDkkhuyenmai();	
			*/
		//Dieu kien su dung san pham nao???
		/*for(int i = 0; i < dkkm1.get(0).getSanphamList().size(); i++)
		{
			ISanpham sp = dkkm1.get(0).getSanphamList().get(i);
			System.out.println("----3333----.Dieu kien Id: " + dkkm1.get(0).getId() + ", Su dung san pham: " + sp.getMasp() + ", Voi so luong: " + sp.getSoluongcan() + ", Thung tuong ung: " + sp.getSoluongThungAvaiable());
		}
		
		for(int i = 0; i < dkkm2.get(0).getSanphamList().size(); i++)
		{
			ISanpham sp = dkkm2.get(0).getSanphamList().get(i);
			System.out.println("----7777----.Dieu kien Id: " + dkkm2.get(0).getId() + ", Su dung san pham: " + sp.getMasp() + ", Voi so luong: " + sp.getSoluongcan() + ", Thung tuong ung: " + sp.getSoluongThungAvaiable());
		}
		System.out.println("_____________________________Het Check 1 Ct__________________________________\n");*/
		
		
		Hashtable<String, Integer> spList = new Hashtable<String, Integer>();
		for(int i=0; i < dkkm1.size(); i++)
		{   
			IDieukienkhuyenmai dkkm = dkkm1.get(i);
			for(int j = 0; j < dkkm.getSanphamList().size(); j++)
			{
				ISanpham sp = dkkm.getSanphamList().get(j);
				if(sp.getSoluongcan() > 0)
				{
					if(!spList.containsKey(sp.getMasp().trim()))
					{
						spList.put(sp.getMasp().trim(), sp.getSoluongcan());
					}
				}
			}
		}
		
		for(int i=0; i < dkkm2.size(); i++)
		{
			IDieukienkhuyenmai dk = dkkm2.get(i);
			
			for(int j = 0; j < dk.getSanphamList().size(); j++)
			{
				ISanpham sp = dk.getSanphamList().get(j);
				if(sp.getSoluongcan() > 0 && spList.containsKey(sp.getMasp()))
				{
					System.out.println("____Ket qua check DKKM trung la: true\n");
					return true;
				}	
			}
			
		}
		
		System.out.println("____Ket qua check DKKM la: false\n");
		return false;
	}
	
	public boolean CheckSP_Mua_Va_DK(Hashtable<String, Integer> spList)
	{
		Enumeration<String> keys = this.HashA.keys();
		while(keys.hasMoreElements())
		{
			String key = keys.nextElement();
			if(spList.containsKey(key))
			{
				System.out.println("__12123434__Ket qua check SP trong DKKM trung la: true\n");
				return true;
			}
		}
		return false;
	}
	
	public String[] getMasp()
	{
		return this.masp;
	}
	
	public void setMasp(String[] masp)
	{
		this.masp = masp;
	}
	
	public String[] getSoluong()
	{
		return this.soluong;
	}
	
	public void setSoluong(String[] soluong)
	{
		this.soluong = soluong;
	}
	
	public String[] getDongia()
	{
		return this.dongia;
	}
	
	public void setDongia(String[] dongia)
	{
		this.dongia = dongia;
	}
	
	public String[] getQuycah()
	{
		return this.quycach;
	}
	
	public void setQuycach(String[] quycach)
	{
		this.quycach = quycach;
	}
	
	public String getIdDonhang()
	{
		return this.idDonhang;
	}
	
	public void setIdDonhang(String id)
	{
		this.idDonhang = id;
	}
	
	public String getNgaygiaodich()
	{
		return this.ngaygiaodich;
	}
	
	public void setNgaygiaodich(String ngd)
	{
		this.ngaygiaodich = ngd;
	}
	
	public String getUserId()
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;
	}
	
	public String getNppId() 
	{
		return this.nppId;
	}

	public void setNppId(String nppId) 
	{
		this.nppId = nppId;
	}
	
	public String getNppTen() 
	{
		return this.nppTen;
	}

	public void setNppTen(String nppTen) 
	{
		this.nppTen = nppTen;
	}
	
	public String getSitecode() 
	{
		return this.sitecode;
	}

	public void setSitecode(String sitecode) 
	{
		this.sitecode = sitecode;
	}
	
	private void getNppInfo()
	{	
		//Phien ban moi
		geso.dms.distributor.util.Utility util = new geso.dms.distributor.util.Utility();
		this.nppId=util.getIdNhapp(this.userId);
		this.nppTen=util.getTenNhaPP();
		this.sitecode=util.getSitecode();		
	}
	
	public static void main(String[] arg) //test
	{
		Hashtable<String, Integer> hashA = new Hashtable<String, Integer>();
		Hashtable<String, Float> hashB = new Hashtable<String, Float>();
		Hashtable<String, Float> hash_Quycach = new Hashtable<String, Float>();
		
		/*hashA.put("01.01.11.01.00.002", 235);
		hashA.put("01.01.11.01.00.003", 24);   //01.01.11.01.00.003 ct tien

		hashB.put("01.01.11.01.00.002", 20000f);
		hashB.put("01.01.11.01.00.003", 20000f);

		hash_Quycach.put("01.01.11.01.00.002", 48f);
		hash_Quycach.put("01.01.11.01.00.003", 16f);*/
		
		hashA.put("65020001", 60);
		hashA.put("01.01.11.01.00.003", 50);
		hashA.put("01.01.11.01.00.004", 150);

		hashB.put("65020001", 24000f);
		hashB.put("01.01.11.01.00.003", 24000f);
		hashB.put("01.01.11.01.00.004", 30000f);

		hash_Quycach.put("65020001", 5f);
		hash_Quycach.put("01.01.11.01.00.003", 12f);
		hash_Quycach.put("01.01.11.01.00.004", 12f);
		
		XLkhuyenmaiTT xlkm = new XLkhuyenmaiTT("100374","2013-01-08","468033", "0");
		xlkm.setHashA(hashA);
		xlkm.setHashB(hashB);
		xlkm.setHash_QuyCach(hash_Quycach);
		
		xlkm.setDieuchinh(false);
		xlkm.ApKhuyenMai();
		
		List<ICtkhuyenmai> listCT = xlkm.getCtkmResual();
		for(int i = 0; i < listCT.size(); i++)
		{
			System.out.println("___Ma CTKM: " + listCT.get(i).getscheme() + "  -- Dien giai: " + listCT.get(i).getDiengiai() );
		}
		/*List<ICtkhuyenmai> listCT = xlkm.getCtkmResual();
		for(int i = 0; i < listCT.size(); i++)
		{
			Ctkhuyenmai ctkm = (Ctkhuyenmai)listCT.get(i);
			
			List<IDieukienkhuyenmai> dkkmList = ctkm.getDkkhuyenmai();
			for(int j = 0; j < dkkmList.size(); j++ )
			{
				IDieukienkhuyenmai dkkm = dkkmList.get(j);
				if(dkkm.getTrongso() > 0)
				{
					//System.out.println("1.Dkkm: " + dkkm.getDiengiai() + " -- Trong so: " + dkkm.getTrongso());

					float tongluong = ctkm.getSoxuatKM() * dkkm.getTongluong();
					
					//Tong trong so cua cac san pham bat buoc mua
					Hashtable<String, Float> sanpham_trongso = dkkm.getSanpham_Trongso();
					
					//boolean flag = true;  //Neu SP la bat buoc ma ko su dung thi cung ko dc khuyen mai
					List<ISanpham> spList = dkkm.getSanphamList();	
					float totalRequest = 0;
					for(int k = 0; k < spList.size(); k++)
					{
						System.out.println("__San pham: " + spList.get(k).getMasp() + " -- Su dung:  " + spList.get(k).getSoluongcan() );
						
						if(sanpham_trongso.get(spList.get(k).getMasp()) != null)
						{
							if(sanpham_trongso.get(spList.get(k).getMasp()) > 0)
							{
								totalRequest += spList.get(k).getSoluongcan();
							}
						}
					}
					
					System.out.println("__Tong trong so yeu cau: " + ( totalRequest * 100 / tongluong ) );
					
					if ( ( totalRequest * 100 / tongluong ) < dkkm.getTrongso() )
					{
						System.out.println("__CTKM khong thoa trong so va so xuat se duoc cap nhat la: " );
						
						//tinh lai so xuat
						long soXuat = Math.round( ( ( ctkm.getSoxuatKM() * totalRequest * 100 / tongluong ) / dkkm.getTrongso() ) - 0.5 );
						
						//System.out.println("__So xuat dieu chinh la: " + soXuat);
						ctkm.setSoxuatKM((int)soXuat);
					}
					
					//check xem co thoa trong so khong
					
				}
			}
			
			List<ITrakhuyenmai> traKmList = ctkm.getTrakhuyenmai();
			for(int j = 0; j < traKmList.size(); j++)
			{
				ITrakhuyenmai tkm = traKmList.get(j);
				System.out.println("Ctkm id: " + ctkm.getId() + " -- So xuat: " + ctkm.getSoxuatKM() + " -- Tra km ID: " + tkm.getId() + " -- Dien giai: " + tkm.getDiengiai());
			}
			
			//System.out.println("Cuoi cung la: " + ctkm.getscheme() + " ---- " + Integer.toString(ctkm.getSoxuatKM())); 
		}*/
		
		
		/*List<ICtkhuyenmai> listCTAvai = xlkm.getCtkmList();
		
		for(int i = 0; i < listCTAvai.size(); i++)
		{
			Ctkhuyenmai ctkm = (Ctkhuyenmai)listCTAvai.get(i);
			List<ITrakhuyenmai> traKmList = ctkm.getTrakhuyenmai();
			for(int j = 0; j < traKmList.size(); j++)
			{
				ITrakhuyenmai tkm = traKmList.get(j);
				System.out.println("Ctkm id: " + ctkm.getId() + " -- Tra km ID: " + tkm.getId() + " -- Dien giai: " + tkm.getDiengiai());
			}
			
			//System.out.print("\n Cuoi cung la: " + ctkm.getscheme() + " ---- " + Integer.toString(ctkm.getSoxuatKM()) + "\n" ); 
		}*/
		
		/*
		Enumeration<String> keys = test.keys();
		while(keys.hasMoreElements())
		{
			String key = keys.nextElement();
			System.out.print(key + " -- " + Integer.toString(test.get(key)) + "\n");
		}
*/
	}
	
	public void setMsg(String Msg)
	{
		this.Msg =Msg;
	}
	
	public String getMsg()
	{
		return this.Msg;
	}
	
	public void setLoaiDonHang(String loaidonhang)
	{
		this.loaidonhang = loaidonhang;
	}
	
	public String getLoaiDonHang()
	{
		return this.loaidonhang;
	}
	
	public void DBclose(){
		
		try {
			if(this.db != null)
				this.db.shutDown();
			
		} catch (Exception e) {
		}
	}
	
	public void SetDungSanPham()
	{
		try
		{
			// xet cac ct loai bt bị đụng
			String schemeDung = "";
			for(int i = 0 ; i < this.ctkmResual.size(); i++)
			{
				ICtkhuyenmai ctkm = this.ctkmResual.get(i);
				if(ctkm.getLoaict() == 1) 
				{
					if(schemeDung.trim().length() > 0 )
						schemeDung += "," + ctkm.getId();
					else
						schemeDung =  ctkm.getId();
				}
			}


			if (schemeDung.trim().length() > 0)
			{

				String sanphamCheck = "";       
				Enumeration<String> keyList = this.HashA.keys();
				while(keyList.hasMoreElements())
				{
					String key = keyList.nextElement();
					if(sanphamCheck.trim().length() >  0)
						sanphamCheck += ",'" + key + "'";
					else
						sanphamCheck = "'" + key + "'";

				}

				if (schemeDung.trim().length()  > 0 && sanphamCheck.trim().length()  > 0)
				{
					String sqlCheckDung = "\n select b.MA, COUNT(b.ma) as soDong " +
					"\n from DIEUKIENKM_SANPHAM a  " +
					"\n      inner join SANPHAM b on a.SANPHAM_FK = b.PK_SEQ    " +
					"\n      inner join DIEUKIENKHUYENMAI c on a.DIEUKIENKHUYENMAI_FK = c.pk_seq " +
					"\n      inner join CTKM_DKKM d on c.pk_seq = d.DKKHUYENMAI_FK " +
					"\n      inner join CtKhuyenMai ctkm on ctkm.pk_seq = d.CTKHUYENMAI_FK and ctkm.loaict = 1  " +  
					"\n where   d.CTKHUYENMAI_FK in ( " + schemeDung + " )                " +
					"\n and b.MA in ( " + sanphamCheck + " )     " +
					"\n group by b.MA    " +
					"\n having COUNT(b.ma) >= 2";

					ResultSet dtCheck = db.get(sqlCheckDung);
					while(dtCheck.next())
					{
						String ma =dtCheck.getString("MA");
						String queryCon =    "\n select distinct ctkm.pk_seq   " +
						"\n from DIEUKIENKM_SANPHAM a " +
						"\n inner join SANPHAM b on a.SANPHAM_FK = b.PK_SEQ   " +
						"\n inner join DIEUKIENKHUYENMAI c on a.DIEUKIENKHUYENMAI_FK = c.pk_seq " +
						"\n inner join CTKM_DKKM d on c.pk_seq = d.DKKHUYENMAI_FK" +
						"\n inner join CTKHUYENMAI ctkm on ctkm.pk_seq = d.ctkhuyenmai_fk and ctkm.loaict = 1  " +
						"\n where d.CTKHUYENMAI_FK in ( " + schemeDung + " )      and b.MA = '" + ma + "'     ";
						ResultSet  dtct = db.get(queryCon);
						while (dtct.next())
						{
							String ctkmId = dtct.getString("pk_seq");
							for(int i = 0 ; i < this.ctkmResual.size(); i++)
							{
								ICtkhuyenmai ctkmTmp = this.ctkmResual.get(i);
								if(this.ctkmResual.get(i).getLoaict() == 1 &&    ctkmId.equals( this.ctkmResual.get(i).getId() )  ) 
								{
									this.ctkmResual.get(i).setDungsanpham("1");
								}
							}

						}
					}

				}

			}
			
			
			
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
	
}
