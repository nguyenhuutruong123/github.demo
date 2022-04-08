package geso.dms.distributor.beans.dieukienkhuyenmai.imp;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import geso.dms.distributor.beans.ctkhuyenmai.ISanpham;
import geso.dms.distributor.beans.dieukienkhuyenmai.IDieukienkhuyenmai;
import geso.dms.distributor.db.sql.dbutils;

public class Dieukienkhuyenmai implements IDieukienkhuyenmai, Comparable<Object> , Serializable
{
	private static final long serialVersionUID = 1L;
	private String id;
	private String diengiai;
	private int pheptoan; //1 and, 2 or
	private float tongtien;
	private float ttTraTheoDK;
	private int tongluong;
	private int type;
	private int soxuatKM;
	private String IsThung;
	private float trongso;  //Chi dms moi xai trong so
	
	private Hashtable<String, Integer> sanpham_soluong; //ma --> soluong(tuong ung voi id)
	private Hashtable<String, Float> sanpham_sotien; //ma -->so tien toi thieu de huong khuyen mai
	
	private Hashtable<String, Float> sanpham_trongso; //ma -->trong so trong DKKM
	
	//luu so luong sudung, soluong con lai cua sp sau moi lan dieu chinh
	private List<ISanpham> sanphamList = new ArrayList<ISanpham>();
	
	//truong hop dkkm ko co xuat KM nao, luu thong tin cac sp trong dkkm co mat trong donhang neu co su dieu chinh
	private List<ISanpham> spList = new ArrayList<ISanpham>();
	
	private dbutils db;
	
	public Dieukienkhuyenmai()
	{
		this.id = "";
		this.diengiai = "";
		this.pheptoan = 2;
		this.tongtien = 0;
		this.tongluong = 0;
		this.type = 1; //bat buoc phai mua cac sp liet ke trong dkkm, 2 - mua bat ky
		this.soxuatKM = 0;
		this.ttTraTheoDK = 0;
		this.IsThung = "0";
		this.sanpham_soluong = new Hashtable<String, Integer>();
		this.sanpham_sotien = new Hashtable<String, Float>();
		this.sanpham_trongso = new Hashtable<String, Float>();
		
		this.trongso = 0;
		this.db = new dbutils();
	}
	
	public Dieukienkhuyenmai(String id)
	{
		db = new dbutils();		
		ResultSet dkkm = db.get("select pk_seq, diengiai, tongluong, tongtien, loai, IsThung, isnull(trongso, 0) as trongso from DieuKienKhuyenMai where pk_seq = '" + id + "'");
		/*if(dkkm != null)*/
		{
			try
			{
				dkkm.next();
				this.id = dkkm.getString("pk_seq");
				this.setDiengiai(dkkm.getString("diengiai"));
				if(dkkm.getString("tongluong") != null)
					this.tongluong = Integer.parseInt(dkkm.getString("tongluong"));
				else
					this.tongluong = 0;
				if(dkkm.getString("tongtien") != null)
					this.tongtien = Float.parseFloat(dkkm.getString("tongtien"));
				else
					this.tongtien = 0;
				this.pheptoan = 2; //default or
				this.type = Integer.parseInt(dkkm.getString("loai"));
				this.soxuatKM = 0;
				this.IsThung = dkkm.getString("IsThung");
				this.trongso = dkkm.getFloat("trongso");
				
				dkkm.close();
			}
			catch(Exception e) {e.printStackTrace();}
			finally{if(dkkm != null)
				try {
					dkkm.close();
				} catch(Exception e) {
					e.printStackTrace();
				}}
		}
		//if(this.tongtien <= 0) //neu ko set tongtien thi phai set soluong hoac sotien
		//{
			//create sanphamList theo dkkm
			this.sanpham_soluong = new Hashtable<String, Integer>();
			this.sanpham_sotien = new Hashtable<String, Float>();
			this.sanpham_trongso = new Hashtable<String, Float>();
			
			String query = "select a.dieukienkhuyenmai_fk, a.soluong, a.sotien, b.ma as spMa, isnull(a.batbuoc, 0) as batbuoc " +
							"from dieukienkm_sanpham a inner join sanpham b on a.sanpham_fk = b.pk_seq " +
							"where a.dieukienkhuyenmai_fk = '" + id + "' order by isnull(a.batbuoc, 0) desc ";
			ResultSet spList = db.get(query);
			
			//System.out.println("___khoi tao SP DKKM: " + query);
			try 
			{
				while(spList.next())
				{
					String masp = spList.getString("spMa");
					
					int soluong = 0;
					if(spList.getString("soluong") != null)
						soluong = Integer.parseInt(spList.getString("soluong"));
					
					float sotien = 0;
					if(spList.getString("sotien") != null)
						sotien = Float.parseFloat(spList.getString("sotien"));
					
					float batbuoc =Float.parseFloat(spList.getString("batbuoc"));
					
					this.sanpham_soluong.put(masp, soluong); //soluong toithieu cua sanpham thoa dk trong chuong trinh khuyen mai
					this.sanpham_sotien.put(masp, sotien); //so tien toi thieu
					
					//if(batbuoc > 0)
					this.sanpham_trongso.put(masp, batbuoc);
					
				}
				spList.close();
			}
			catch(Exception e) {}
			
			finally{if(spList != null)
				try {
					spList.close();
				} catch(Exception e) {
					e.printStackTrace();
				}}
		//}
		
	}
		
	public String getId()
	{
		return this.id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public void setDiengiai(String diengiai) 
	{
		this.diengiai = diengiai;
	}
	public String getDiengiai()
	{
		return diengiai;
	}
	public int getPheptoan()
	{
		return this.pheptoan;
	}
	public void setPheptoan(int pheptoan)
	{
		this.pheptoan = pheptoan;
	}
	public float getTongtien()
	{
		return this.tongtien;
	}
	public void setTongtien(int tongtien)
	{
		this.tongtien = tongtien;
	}
	public int getTongluong()
	{
		return this.tongluong;
	}
	public void setTongluong(int tongluong)
	{
		this.tongluong = tongluong;
	}
	public int getType()
	{
		return this.type;
	}
	public void setType(int type)
	{
		this.type = type;
	}
	public int getSoxuatKM()
	{
		return this.soxuatKM;
	}
	public void setSoxuatKM(int soxuatKM)
	{
		this.soxuatKM = soxuatKM;
	}
	public Hashtable<String, Integer> getSanpham_Soluong()
	{
		return this.sanpham_soluong;
	}
	public void setSanpham_Soluong(Hashtable<String, Integer> sanpham)
	{
		this.sanpham_soluong = sanpham;
	}
	public Hashtable<String, Float> getSanpham_Sotien()
	{
		return this.sanpham_sotien;
	}
	public void setSanpham_Sotien(Hashtable<String, Float> sanpham)
	{
		this.sanpham_sotien = sanpham;
	}
	public List<ISanpham> getSanphamList()
	{
		return this.sanphamList;
	}
	public void setSanphamList(List<ISanpham> spList)
	{
		this.sanphamList = spList;
	}
	public List<ISanpham> getSpList()
	{
		return this.spList;
	}
	public void setSpList(List<ISanpham> spList)
	{
		this.spList = spList;
	}
	
	public int compareTo(Object obj) 
	{
		Dieukienkhuyenmai dkkm = (Dieukienkhuyenmai)obj;
		if(!this.getId().equals(dkkm.getId()))
			return -1;
		return 0;
	}

	public void DBclose() 
	{
		try 
		{
			if(db != null)
				db.shutDown();	
		} 
		catch (Exception e) {}
	}

	public float getTongtienTraTheoDK()
	{
		return this.ttTraTheoDK;
	}

	public void setTongtienTraTheoDK(float ttTraTheoDK)
	{
		this.ttTraTheoDK = ttTraTheoDK;
	}

	public String getIsThung()
	{
		return this.IsThung;
	}

	public void setIsThung(String isThung)
	{
		this.IsThung = isThung;
	}

	public float getTrongso() 
	{
		return this.trongso;
	}

	public void setTrongso(float trongso) 
	{
		this.trongso = trongso;
	}

	public Hashtable<String, Float> getSanpham_Trongso()
	{
		return this.sanpham_trongso;
	}

	public void setSanpham_TrongSo(Hashtable<String, Float> sanpham) 
	{
		this.sanpham_trongso = sanpham;
	}
}
