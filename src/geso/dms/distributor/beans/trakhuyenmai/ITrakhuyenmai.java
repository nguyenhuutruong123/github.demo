package geso.dms.distributor.beans.trakhuyenmai;

import java.io.Serializable;
import java.util.Hashtable;

public interface ITrakhuyenmai extends Serializable
{
	public String getId();
	public void setId(String id);
	public String nppId();
	public void setNppId(String nppId);
	public void setDiengiai(String diengiai);
	public String getDiengiai();
	public int getType();
	public void setType(int type);
	public int getHinhthuc();
	public void setHinhthuc(int hinhthuc);
	public float getTongtien();
	public void setTongtien(int tongtien);
	public float getChietkhau();
	public void setChietkhau(float chietkhau);
	public float getTongluong(String dhId, String ctkmId, int soXUAT);
	public void setTongluong(int tongluong);
	public float getTongluong();
	public int getPheptoan();
	public void setPheptoan(int pheptoan);
	
	public String getHinhthucPrimary();
	public void setHinhthucPrimary(String hinhthuc);
	
	public Hashtable<String, Integer> getSanpham_Soluong(String dhId, String ctkmId, int soXUAT, String hinhthuc);
	public Hashtable<String, Integer> getSanpham_Soluong();
	public void setSanpham_Soluong(Hashtable<String, Integer> sanpham);
	public Hashtable<String, Float> getsanpham_dongia();
	public void setsanpham_dongia(Hashtable<String, Float> sanpham);
	public Hashtable<String, String> getsanpham_tensp();
	public void setsanpham_tensp(Hashtable<String, String> sanpham);
	
	public double getTylequidoi();
	public void setTylequidoi(double tyle);
	public String getSpquidoi();
	public void setSpquidoi(String sp);
	public float getDongiaSpquidoi();
	public void setDongiaSpquidoi(float dongia);
	

	public float getTongGtriKm();
	public void DBclose();
}
