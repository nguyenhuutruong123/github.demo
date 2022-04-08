package geso.dms.center.beans.tieuchithuong;

import geso.dms.center.beans.dieukienkhuyenmai.ISanpham;

import java.util.List;

public interface IDieukientlDetail 
{
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	public String getLoaidieukien();
	public void setLoaidieukien(String loaidieukien);
	public String getHinhthuc();
	public void setHinhthuc(String hinhthuc);
	public String getSotong();
	public void setSotong(String sotong);
	
	public String getDiengiai1();
	public void setDiengiai1(String diengiai1);
	public String getLoaidieukien1();
	public void setLoaidieukien1(String loaidieukien1);
	public String getHinhthuc1();
	public void setHinhthuc1(String hinhthuc1);
	public String getSotong1();
	public void setSotong1(String sotong1);
	
	public String getDiengiai2();
	public void setDiengiai2(String diengiai2);
	public String getLoaidieukien2();
	public void setLoaidieukien2(String loaidieukien2);
	public String getHinhthuc2();
	public void setHinhthuc2(String hinhthuc2);
	public String getSotong2();
	public void setSotong2(String sotong2);
	
	
	public String getDiengiai3();
	public void setDiengiai3(String diengiai3);
	public String getLoaidieukien3();
	public void setLoaidieukien3(String loaidieukien3);
	public String getHinhthuc3();
	public void setHinhthuc3(String hinhthuc3);
	public String getSotong3();
	public void setSotong3(String sotong3);
	
	
	public String getDiengiai4();
	public void setDiengiai4(String diengiai4);
	public String getLoaidieukien4();
	public void setLoaidieukien4(String loaidieukien4);
	public String getHinhthuc4();
	public void setHinhthuc4(String hinhthuc4);
	public String getSotong4();
	public void setSotong4(String sotong4);
	
	
	
	public String getNhomspId();
	public void setNhomspId(String nspId);

	public String getNhomspId1();
	public void setNhomspId1(String nspId1);

	public String getNhomspId2();
	public void setNhomspId2(String nspId2);

	public String getNhomspId3();
	public void setNhomspId3(String nspId3);

	public String getNhomspId4();
	public void setNhomspId4(String nspId4);
	
	public List<ISanpham> getSpList();
	public void setSpList(List<ISanpham> spList);
	public List<ISanpham> getSpList1();
	public void setSpList1(List<ISanpham> spList1);
	public List<ISanpham> getSpList2();
	public void setSpList2(List<ISanpham> spList2);
	public List<ISanpham> getSpList3();
	public void setSpList3(List<ISanpham> spList3);
	public List<ISanpham> getSpList4();
	public void setSpList4(List<ISanpham> spList4);
	
}
