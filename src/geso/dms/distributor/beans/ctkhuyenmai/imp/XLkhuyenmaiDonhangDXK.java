
package geso.dms.distributor.beans.ctkhuyenmai.imp;
import geso.dms.distributor.beans.ctkhuyenmai.*;
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

public class XLkhuyenmaiDonhangDXK 
{
	private dbutils db;
	
	private Hashtable<String,Integer> HashA = new Hashtable<String,Integer>(); //khoi tao tu don hang "abc"	
	private Hashtable<String,Float> HashB = new Hashtable<String,Float>(); //khoi tao tu don hang "abc"
	
	private Hashtable<String,Integer> HashC = new Hashtable<String,Integer>(); //dung trong truong hop Ontop 	
	
	private List<ICtkhuyenmai> ctkmList; //chuong trinh km Avaialble
	private List<ICtkhuyenmai> ctkmResual; //ket qua
	
	private String[] masp;
	private String[] soluong;
	private String[] dongia;
	
	private boolean dieuchinhKM;
	private String idDonhang;
	private float tonggiatriDh;
	private String ngaygiaodich; //ngay giao dich trong don hang
	private String Msg ="";
	String khachhang;
	String nppId;
	String nppTen;
	String sitecode;
	
	public XLkhuyenmaiDonhangDXK(String nppId, String dhId, String date, String[] scheme, String khachhang)
	{
		db = new dbutils();
		this.khachhang = khachhang;
		this.idDonhang = dhId;
		this.nppId = nppId;
		
		this.ctkmList = this.getListCTKM_Avaiable(date, scheme);
	}
		
	
	public List<ICtkhuyenmai> getListCTKM_Avaiable(String date, String[] schemeList) //list chuong trinh khuyen mai con hieu luc
	{   
		String schemes = "";
		for(int i = 0; i < schemeList.length; i++)
		{
			schemes += "'" + schemeList[i].trim() + "',";
		}
		schemes = schemes.substring(0, schemes.length() - 1);
		
		List<ICtkhuyenmai> ctkmList = new ArrayList<ICtkhuyenmai>();
	
		String sql =" select a.pk_seq, a.scheme, a.tungay, a.denngay, a.loaict, a.ngaytao, a.ngaysua, a.nguoitao, a.nguoisua, isnull(a.diengiai, '') as diengiai, d.ngansach, case when d.dasudung is null then '0' else d.dasudung end as dasudung from ctkhuyenmai a,ctkm_npp b, phanbokhuyenmai d ";
        sql = sql + " where a.pk_seq = b.ctkm_fk and b.chon='1' and a.pk_seq = d.ctkm_fk and b.npp_fk = d.npp_fk and b.npp_fk='" + this.nppId + "' and a.tungay <= '" + date + "' and '" + date + "' <= a.denngay ";
        sql = sql + " and a.scheme in (" + schemes + ") and (a.nhomkhnpp_fk is null or a.nhomkhnpp_fk in (select pk_seq from nhomkhachhangnpp where pk_seq in (select nhomkhnpp_fk from ctkm_khachhang where khachhang_fk ='"+ this.khachhang +"')))";
        
        System.out.println("Query lay nhung ct khuyen mai cua dh da xuat kho la: " + sql + "\n");
        
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
				int loaict = Integer.parseInt(ctkm.getString("loaict"));
				float ngansach = Float.parseFloat(ctkm.getString("ngansach"));
				float dasudung = Float.parseFloat(ctkm.getString("dasudung"));
				int soxuatKM = 0;
				
				Ctkhuyenmai km = new Ctkhuyenmai(id, scheme, diengiai, tungay, denngay, loaict, ngansach, dasudung, soxuatKM);
				
				List<IDieukienkhuyenmai> dkkhuyenmai = new ArrayList<IDieukienkhuyenmai>();
				List<ITrakhuyenmai> trakhuyenmai = new ArrayList<ITrakhuyenmai>();
				
				//Lay danh sach dieu kien khuyen mai cua chuong trinh
				ResultSet rs = db.get("select dkkhuyenmai_fk, pheptoan from ctkm_dkkm where Ctkhuyenmai_fk='" + id + "' order by thutudieukien ASC");
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
				ResultSet rsTraKm = db.get("select trakhuyenmai_fk from ctkm_trakm where Ctkhuyenmai_fk = '" + id + "'");
				
				if(rsTraKm != null)
				{
					while(rsTraKm.next())
					{
						String trakmId = rsTraKm.getString("trakhuyenmai_fk");
						Trakhuyenmai trakm = new Trakhuyenmai(trakmId, this.idDonhang, id, nppId);
						trakhuyenmai.add(trakm);
					}
					rsTraKm.close();
				}
				km.setTrakhuyenmai(trakhuyenmai);
				
				ctkmList.add(km);
			}
			ctkm.close();
		} 
		catch (SQLException e) {}
		}
		
		return ctkmList;
	}
	
	public void ApKhuyenMai()
	{	
		List<ICtkhuyenmai> schemeList = new ArrayList<ICtkhuyenmai>();
		if(this.dieuchinhKM)
		{
			for(int i = 0; i < this.ctkmList.size(); i++)
			{
				Ctkhuyenmai ct = (Ctkhuyenmai)this.ctkmList.get(i);
				Ctkhuyenmai ctkm = null;
				if(ct.getLoaict() == 2) //ontop
					ctkm = this.getSoxuattheoScheme(ct, HashC);
				else
					ctkm = this.getSoxuattheoScheme(ct, HashA);
		
				if(ctkm.getSoxuatKM() >= 0) //truong hop soxuat khuyenmai lon hon so xuat duoc huong toi da
				{
					if(ctkm.checkCtkm(tonggiatriDh) >= 0)
					{
						ctkm.setSoxuatKM(ctkm.checkCtkm(tonggiatriDh));
						schemeList.add(ctkm);
					}
				}
			}
		}
		else  //dieuchinh == false
		{
			for(int i=0; i < this.ctkmList.size(); i++)
			{				
				Ctkhuyenmai ct = (Ctkhuyenmai)this.ctkmList.get(i);
				Hashtable<String, Integer> copyHashA = copyHashtable(HashA);				
				Ctkhuyenmai ctkm = this.getSoxuattheoScheme(ct, copyHashA);
				
				if(ctkm.getSoxuatKM() >= 0)
				{
					if(ctkm.checkCtkm(tonggiatriDh) > 0)
					{
						ctkm.setSoxuatKM(ctkm.checkCtkm(tonggiatriDh));
						schemeList.add(ctkm);
					}
				}
			}
		}
		
		//System.out.println("chuong tring tong so:"+schemeList.size());
		this.ctkmResual = schemeList;
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
		for(int i = 0; i < dkkmList.size(); i++)
		{
			Dieukienkhuyenmai dkkm = (Dieukienkhuyenmai)dkkmList.get(i);
			//List<ISanpham> sanphamList = new ArrayList<ISanpham>();
			
			String pt = ((dkkm.getPheptoan() == 1) ? "&&" : "||");
			pheptoan.add(pt);

			boolean flag = false;
			int sl = 0;
						
			if(dkkm.getTongtien() > 0) 								//check tong tien co hop dieu kien ko??
				sl = checkDKKM_TongTien(dkkm, HashB, hashSanpham);
			else   
			{
				if(dkkm.getTongluong() > 0) 						//check tong soluong sp trong dkkm
					sl = checkDKKM_TongLuong(dkkm, hashSanpham);
				else												//check so luong cu the tung san pham trong dkkm
					sl = checkDKKM_SP(dkkm, hashSanpham);
			}
			System.out.println("soxuat "+sl);			
			if(sl >= 0)
			{
				flag = true;
				ctkm.getDkkhuyenmai().get(i).setSoxuatKM(sl);								
				this.dieuChinhKM((Dieukienkhuyenmai)ctkm.getDkkhuyenmai().get(i), hashSanpham, HashB, sl);
								
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
		
		if((checkDieuKien(dieukien, pheptoan) == false))
		{				
			if(this.dieuchinhKM == true)
				this.HashA = copyHashtable(copySp); //under change, phai thay doi truc tiep HashA, ko thong wa tham chieu duoc...
			ctkhuyenmai.setSoxuatKM(0);
		}
		else
		{
			ctkhuyenmai.setSoxuatKM(soXuatKM);
			ctkhuyenmai.setDkkhuyenmai(ctkm.getDkkhuyenmai());
			ctkhuyenmai.setTrakhuyenmai(ctkm.getTrakhuyenmai());
		}	
		System.out.println("soXuatKM = " + soXuatKM);
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
	
	private int checkDKKM_SP(Dieukienkhuyenmai dkkm, Hashtable<String,Integer> sanpham)
	{
		int soxuatKm = 0;
		Hashtable<String,Integer> sp_sl = dkkm.getSanpham_Soluong();
		if(sp_sl.size() > 0)
		{			
			if(sanpham.size() < sp_sl.size())
				return 0;
			
			int min = Integer.MAX_VALUE; 
			Enumeration<String> keyList = sp_sl.keys();
			while(keyList.hasMoreElements())
			{
				String key = keyList.nextElement();
				if(sanpham.get(key) == null)
					return 0; //khong co san pham nay trong dkkhuyenmai bat buoc
				else
				{
					int soSpA = sanpham.get(key);
					int soSpPhaiThoa = sp_sl.get(key);
					if(soSpA < soSpPhaiThoa)
						return 0;	
					else
					{
						int k = soSpA / soSpPhaiThoa;
						if(k < min)
							min = k;
					}
				}
			}				
			soxuatKm = min;	
		}	
		return soxuatKm;
	}
	
	private int checkDKKM_TongLuong(Dieukienkhuyenmai dkkm, Hashtable<String, Integer> sanpham_soluong)
	{
		int soxuatKm = 0;
		Hashtable<String,Integer> sanpham = dkkm.getSanpham_Soluong(); //phai la nhung san pham trong dkkm truoc
		
		if(sanpham.size() > 0)
		{
			int tongluong = dkkm.getTongluong();
			//System.out.print("Tong Luong Theo DKKM: " + Integer.toString(tongluong) + "\n");
			int sum = 0;
			int minSP = Integer.MAX_VALUE; //soxuat bat buoc phai co mat day du cac sanpham trong dkkm
			
			Enumeration<String> keyList = sanpham.keys();	
			while(keyList.hasMoreElements())
			{
				String key = keyList.nextElement();
				
				if(dkkm.getType() == 2)
				{
					//qui dinh mua bat ky sanpham a, sanpham b, sanpham c.... trong nhom ABC dat tongluong = xyz
					if(sanpham_soluong.get(key) != null)
						sum += sanpham_soluong.get(key);
				}
				else
				{
					//qui dinh mua dung sanpham a, sanpham b, sanpham c trong nhom ABC dat tongluong = xyz
					if(!sanpham_soluong.containsKey(key))
						return 0;
					if(sanpham_soluong.get(key) <= 0)
						return 0;					
					sum += sanpham_soluong.get(key);
					
					if(sanpham_soluong.get(key) < minSP)
						minSP = sanpham_soluong.get(key);
				}				
			}
			//System.out.print("dk khuyen mai " + dkkm.getId() + " ---- So xuat " + Integer.toString(sum) + "\n");
			if(sum < tongluong)
				return 0;
			soxuatKm = min((int)(sum / tongluong), minSP);
		}	
		return soxuatKm;
	}
		
	private int checkDKKM_TongTien(Dieukienkhuyenmai dkkm, Hashtable<String,Float> sanpham_dongia, Hashtable<String, Integer> sanpham_soluong)
	{
		int soxuatKm = 0;
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
						sum += sanpham_dongia.get(key) * sanpham_soluong.get(key);
				}
				else
				{
					//dk mua sp qui dinh trong nhom sp dat sotien > y
					if(!sanpham_dongia.containsKey(key))
						return 0;
					if(sanpham_soluong.get(key) <= 0)
						return 0;
					sum += sanpham_dongia.get(key) * sanpham_soluong.get(key); //tong so tien cua tat ca sp trong don hang
					
					if(sanpham_soluong.get(key) < minSP)
						minSP = sanpham_soluong.get(key);
				}
			}
			double tongtiendonhang = sum * 1.1;
			
			if(Math.round(tongtiendonhang) < tongtien)
				return 0;
			soxuatKm = min((int)(Math.round(tongtiendonhang) / tongtien), minSP);
		}				
		return soxuatKm;
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
	
	private void dieuChinhKM(Dieukienkhuyenmai dkkm, Hashtable<String, Integer> sp_sl, Hashtable<String, Float> sp_dongia ,int soXuatKM)
	{
		Hashtable<String, Integer> sanpham_sl = dkkm.getSanpham_Soluong();
		List<ISanpham> sanphamList = new ArrayList<ISanpham>();
		
		if(dkkm.getTongtien() <= 0)
		{
			if(dkkm.getTongluong() > 0) //truong hop dkkm co set tongluong
			{		
				Sanpham[] sanpham = getSanpham_slg(dkkm, sp_sl, soXuatKM);
				if(sanpham != null)
				{
					for(int i=0; i < sanpham.length; i++)
					{
						String key = sanpham[i].getMasp();
						if(sp_sl.containsKey(key))
						{
							int slgconlai = sp_sl.get(key) - sanpham[i].getSoluongcan();
							//luu lai thong tin sl sanpham su dung, sl con lai
							Sanpham sp = new Sanpham(key, sanpham[i].getTensp(), sanpham[i].getSoluongcan(), sp_dongia.get(key), slgconlai, true);
							//System.out.print("San pham " + sp.getMasp() + " --- Slg can " + sp.getSoluongcan() + " --- Avaiable " + sp.getSoluongAvaiable() + "\n");
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
						int slgconlai = sp_sl.get(key) - sanpham_sl.get(key) * soXuatKM;
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
						Sanpham sp = new Sanpham(key, tensp, sanpham_sl.get(key) * soXuatKM, sp_dongia.get(key), slgconlai, false);
						
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
						int slgconlai = sp_sl.get(key) - sanpham[i].getSoluongcan();
						//luu lai thong tin sl sanpham su dung, sl con lai
						Sanpham sp = new Sanpham(key, sanpham[i].getTensp(), sanpham[i].getSoluongcan(), sp_dongia.get(key), slgconlai, false);
						
						//System.out.print("\n" + sanpham[i].getTensp() + " -- " + sanpham[i].getSoluongcan() + "\n");
						
						sp_sl.put(key, slgconlai);
						sanphamList.add(sp);
					}
				}
			}
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
		Sanpham[] sanpham = new Sanpham[sp_sl.size()];

		int m = 0;
		Enumeration<String> keyList = sp_sl.keys();
		while(keyList.hasMoreElements())
		{
			String key = keyList.nextElement();
			int slAvaiable = 0;
			if(sanpham_sl.get(key) != null)
				slAvaiable = sanpham_sl.get(key);
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
				sanpham[m] = new Sanpham(key, tensp, 0, dongia, slAvaiable, flag);
			else
				sanpham[m] = new Sanpham(key, tensp, 1, dongia, slAvaiable, flag); //toi thieu phai co 1sp de thoa dk		
			m++;					
		}
		
		Arrays.sort(sanpham);
		return sanpham;
	}
	
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
		Sanpham[] sanpham = InitSanPham(dkkm, sanpham_sl, true); //Sort sanpham tang dan theo soluong

		if(sanpham == null)
			return null;
		
		int tongluongKM = dkkm.getTongluong() * soxuatKm; //tong luong theo tat ca cac xuat khuyenmai duoc huong
		
		for(int i = 0; i < sanpham.length; i++)
		{
			int sum = getTongluong(sanpham);
			sum = sum - sanpham[i].getSoluongcan();
			
			int soluongcan = min(sanpham[i].getSoluongAvaiable(), tongluongKM - sum);
			sanpham[i].setSoluongcan(soluongcan);
			//System.out.print("San pham " + sanpham[i].getMasp() + " --- Slg can " + sanpham[i].getSoluongcan() + " --- Avaiable " + sanpham[i].getSoluongAvaiable() + "\n");
			if(i==(sanpham.length - 1))
			{
				if(getTongluong(sanpham) < tongluongKM)
					sanpham[i].setSoluongcan(sanpham[i].getSoluongcan() + 1);
			}
		}
		return sanpham;
	}
	
	private int min(int a, int b)
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
	
	private int getTongluong(Sanpham[] sanpham)
	{
		int sum = 0;
		for(int j = 0; j < sanpham.length; j++)
		{
			sum += (sanpham[j].getSoluongcan());
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
	
	public List<ICtkhuyenmai> getCtkmList()
	{
		return this.ctkmList;
	}
	
	public void setCtkmList(List<ICtkhuyenmai> list)
	{
		this.ctkmList = list;
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
	
	public void checkConfirm() 
	{
		for(int i = 0; i < this.ctkmResual.size(); i++)
		{
			ICtkhuyenmai ctkm = this.ctkmResual.get(i);
			
			for(int j = 0; j < ctkm.getTrakhuyenmai().size(); j++)
			{
				ITrakhuyenmai trakm = ctkm.getTrakhuyenmai().get(j);
				
				//ct khuyenmai phai lua chon sanpham
				if(trakm.getType() == 3 && trakm.getHinhthuc() == 2)
				{
					//neu hinh thuc la lua chon san pham, va so luong san pham tung chon lon > 1 thi moi phai qua trang lua chon san pham
					String query = "select count(*) as sosp from donhang_ctkm_trakm where ctkmId = '" + ctkm.getId() + "' and donhangId = '" + this.idDonhang + "'";
					System.out.println("Query check khuyen mai la: " + query + "\n");
					ResultSet rs = db.get(query);
					if(rs != null)
					{
						try 
						{

							rs.next();
							int count = rs.getInt("sosp");
							if(count >= 2)
							{
								this.ctkmResual.get(i).setConfirm(true);
							}
							rs.close();
						} 
						catch (Exception e) {}
					}
				}
			}
		}
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
	
	public static void main(String[] arg) //test
	{
		Hashtable<String, Integer> hashA = new Hashtable<String, Integer>();
		Hashtable<String, Float> hashB = new Hashtable<String, Float>();
		
		hashA.put("1005240260251", 20);
		hashA.put("0905191160232", 10);
		//hashA.put("0101010160321", 10);
		//hashA.put("0101010260322", 10);
		//hashA.put("0101011560323", 12);
		
		hashB.put("1005240260251", 20000f);
		hashB.put("0905191160232", 30000f);
		//hashB.put("0101010160321", 21000f);
		//hashB.put("0101010260322", 20000f);
		//hashB.put("0101011560323", 21000f);
		
		/*XLkhuyenmaiDonhangDXK xlkm = new XLkhuyenmaiDonhangDXK("100020","2011-12-27","100016");
		xlkm.setHashA(hashA);
		xlkm.setHashB(hashB);
		
		xlkm.setDieuchinh(true);
		xlkm.ApKhuyenMai();
		
		List<ICtkhuyenmai> listCT = xlkm.getCtkmResual();
		for(int i = 0; i < listCT.size(); i++)
		{
			Ctkhuyenmai ctkm = (Ctkhuyenmai)listCT.get(i);
			//System.out.print("\n Cuoi cung la: " + ctkm.getscheme() + " ---- " + Integer.toString(ctkm.getSoxuatKM()) + "\n" ); 
		}
		*/
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
		this.Msg = Msg;
	}
	
	public String getMsg()
	{
		return this.Msg;
	}
	
	public void DBclose(){
		
		try {
			if(this.db != null)
				this.db.shutDown();
			
		} catch (Exception e) {
		}
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
	
}
