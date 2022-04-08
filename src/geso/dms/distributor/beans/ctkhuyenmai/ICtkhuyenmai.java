package geso.dms.distributor.beans.ctkhuyenmai;

import geso.dms.distributor.beans.dieukienkhuyenmai.IDieukienkhuyenmai;
import geso.dms.distributor.beans.trakhuyenmai.ITrakhuyenmai;

import java.util.List;

public interface ICtkhuyenmai 
{
	public int getLoaingansach() ;
	public void setLoaingansach(int loaingansach) ;
	public double getSo_suat_toi_da();
	public void setSo_suat_toi_da(double so_suat_toi_da) ;
	public String getId();
	public void setId(String id);
	public String getscheme();
	public void setscheme(String scheme);
	public String getKhoId();
	public void setKhoId(String khoId);
	public String getTungay();
	public void setTungay(String tungay);
	public String getDenngay();
	public void setDenngay(String denngay);
	public int getLoaict();
	public void setLoaict(int loaict);
	public float getNgansach();
	public void setNgansach(float ngansach);
	public float getDasudung();
	public void setDasudung(float dasudung);
	public int getSoxuatKM();
	public void setSoxuatKM(int soxuatKM);
	
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	
	public long getTongTienTheoDKKM();
	public void setTongTienTheoDKKM(long tongtien);
	
	//kiem tra 1 ctkm co dung chung sp hoac thoa ngan sach hay ko
	public boolean getConfirm();
	public void setConfirm(boolean confirm);
	public int checkCtkm(float tonggiatri);
	
	public void setTra_OR(boolean tra_OR);
	public boolean getTra_OR();
	public String getHinhthucPrimary();
	public void setHinhthucPrimary(String hinhthucPrimary);
	
	public List<IDieukienkhuyenmai> getDkkhuyenmai();
	public void setDkkhuyenmai(List<IDieukienkhuyenmai> dkkm);
	public List<ITrakhuyenmai> getTrakhuyenmai();
	public void setTrakhuyenmai(List<ITrakhuyenmai> trakm);
	
	public String getPhanbotheoDH();
	public void setPhanbotheoDH(String phanbotheoDH);
	
	public float getCK();
	public void setCK(float ck);
	
	public String getDungsanpham() ;
	public void setDungsanpham(String dungsanpham) ;
	
}
