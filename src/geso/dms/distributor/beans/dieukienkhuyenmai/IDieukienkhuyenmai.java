package geso.dms.distributor.beans.dieukienkhuyenmai;

import geso.dms.distributor.beans.ctkhuyenmai.ISanpham;

import java.util.Hashtable;
import java.util.List;

public interface IDieukienkhuyenmai 
{
	public String getId();
	public void setId(String id);	
	public void setDiengiai(String diengiai);
	public String getDiengiai();
	public int getPheptoan();
	public void setPheptoan(int pheptoan);	
	public float getTongtien();
	public void setTongtien(int tongtien);	
	public int getTongluong();
	public void setTongluong(int tongluong);	
	public int getType();
	public void setType(int type);
	
	public float getTrongso();
	public void setTrongso(float trongso);
	
	public int getSoxuatKM();
	public void setSoxuatKM(int soxuatKM);
	
	public Hashtable<String, Integer> getSanpham_Soluong();
	public void setSanpham_Soluong(Hashtable<String, Integer> sanpham);
	public Hashtable<String, Float> getSanpham_Sotien();
	public void setSanpham_Sotien(Hashtable<String, Float> sanpham);
	
	
	public List<ISanpham> getSanphamList();
	public void setSanphamList(List<ISanpham> spList);
	
	//truong hop dkkm ko co xuat KM nao, luu thong tin cac sp trong dkkm co mat trong donhang neu co su dieu chinh
	public List<ISanpham> getSpList();
	public void setSpList(List<ISanpham> spList);
	
	
	//Them trong so
	public Hashtable<String, Float> getSanpham_Trongso();
	public void setSanpham_TrongSo(Hashtable<String, Float> sanpham);
	
	public void DBclose();
	
	public float getTongtienTraTheoDK();
	public void setTongtienTraTheoDK(float ttTraTheoDK);
	
	public String getIsThung();
	public void setIsThung(String isThung);
}
