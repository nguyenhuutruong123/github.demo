package geso.dms.center.beans.tieuchithuong.imp;

import java.util.ArrayList;
import java.util.List;

import geso.dms.center.beans.ctkhuyenmai.IDieukienDetail;
import geso.dms.center.beans.dieukienkhuyenmai.ISanpham;
import geso.dms.center.beans.tieuchithuong.IDieukientlDetail;

public class DieukientlDetail implements IDieukientlDetail 
{
	String diengiai;
	String loaidieukien;
	String hinhthuc;
	String sotong;
	
	String diengiai2;
	String loaidieukien2;
	String hinhthuc2;
	String sotong2;
	
	
	String diengiai1;
	String loaidieukien1;
	String hinhthuc1;
	String sotong1;
	
	
	String diengiai3;
	String loaidieukien3;
	String hinhthuc3;
	String sotong3;
	
	String diengiai4;
	String loaidieukien4;
	String hinhthuc4;
	String sotong4;
	String nspId;
	
	String nspId1;
	String nspId2;
	String nspId3;
	String nspId4;
	
	List<ISanpham> spList;
	List<ISanpham> spList1;
	List<ISanpham> spList2;
	List<ISanpham> spList3;
	List<ISanpham> spList4;
	
	
	public DieukientlDetail()
	{
		this.diengiai = "";
		this.loaidieukien = "";
		this.hinhthuc = "";
		this.sotong = "";
		
		this.diengiai1 = "";
		this.loaidieukien1 = "";
		this.hinhthuc1 = "";
		this.sotong1 = "";
		
		
		this.diengiai2 = "";
		this.loaidieukien2 = "";
		this.hinhthuc2 = "";
		this.sotong2 = "";
		
		this.diengiai3 = "";
		this.loaidieukien3 = "";
		this.hinhthuc3 = "";
		this.sotong3 = "";
		
		this.diengiai4 = "";
		this.loaidieukien4 = "";
		this.hinhthuc4 = "";
		this.sotong4 = "";
		this.nspId = "";
		this.nspId1 = "";
		this.nspId2 = "";
		this.nspId3 = "";
		this.nspId4 = "";
		this.spList = new ArrayList<ISanpham>();
		this.spList1 = new ArrayList<ISanpham>();
		this.spList2 = new ArrayList<ISanpham>();
		this.spList3 = new ArrayList<ISanpham>();
		this.spList4 = new ArrayList<ISanpham>();
	}
	
	public DieukientlDetail(String diengiai, String loaidk, String hinhthuc, String sotong, String nspId)
	{
		this.diengiai = diengiai;
		this.loaidieukien = loaidk;
		this.hinhthuc = hinhthuc;
		this.sotong = sotong;
		

		this.diengiai1 = "";
		this.loaidieukien1 = "";
		this.hinhthuc1 = "";
		this.sotong1 = "";
		
		
		this.diengiai2 = "";
		this.loaidieukien2 = "";
		this.hinhthuc2 = "";
		this.sotong2 = "";
		
		this.diengiai3 = "";
		this.loaidieukien3 = "";
		this.hinhthuc3 = "";
		this.sotong3 = "";
		
		this.diengiai4 = "";
		this.loaidieukien4 = "";
		this.hinhthuc4 = "";
		this.sotong4 = "";
		
		
		this.nspId = "";
		this.nspId1 = "";
		this.nspId2 = "";
		this.nspId3 = "";
		this.nspId4 = "";
		this.spList = new ArrayList<ISanpham>();
		this.spList1 = new ArrayList<ISanpham>();
		this.spList2 = new ArrayList<ISanpham>();
		this.spList3 = new ArrayList<ISanpham>();
		this.spList4 = new ArrayList<ISanpham>();
	}

	public String getDiengiai()
	{
		return this.diengiai;
	}

	public void setDiengiai(String diengiai)
	{
		this.diengiai = diengiai;
	}
	
	public String getLoaidieukien() 
	{
		return this.loaidieukien;
	}

	public void setLoaidieukien(String loaidieukien) 
	{
		this.loaidieukien = loaidieukien;
	}

	public String getHinhthuc() 
	{
		return this.hinhthuc;
	}

	public void setHinhthuc(String hinhthuc) 
	{
		this.hinhthuc = hinhthuc;
	}

	public String getSotong() 
	{
		return this.sotong;
	}

	public void setSotong(String sotong) 
	{
		this.sotong = sotong;
	}

	
	
	
	public String getDiengiai1()
	{
		return this.diengiai1;
	}

	public void setDiengiai1(String diengiai1)
	{
		this.diengiai1 = diengiai1;
	}
	
	public String getLoaidieukien1() 
	{
		return this.loaidieukien1;
	}

	public void setLoaidieukien1(String loaidieukien1) 
	{
		this.loaidieukien1 = loaidieukien1;
	}

	public String getHinhthuc1() 
	{
		return this.hinhthuc1;
	}

	public void setHinhthuc1(String hinhthuc1) 
	{
		this.hinhthuc1 = hinhthuc1;
	}

	public String getSotong1() 
	{
		return this.sotong1;
	}

	public void setSotong1(String sotong1) 
	{
		this.sotong1 = sotong1;
	}

	
	
	
	public String getDiengiai2()
	{
		return this.diengiai2;
	}

	public void setDiengiai2(String diengiai2)
	{
		this.diengiai2 = diengiai2;
	}
	
	public String getLoaidieukien2() 
	{
		return this.loaidieukien2;
	}

	public void setLoaidieukien2(String loaidieukien2) 
	{
		this.loaidieukien2 = loaidieukien2;
	}

	public String getHinhthuc2() 
	{
		return this.hinhthuc2;
	}

	public void setHinhthuc2(String hinhthuc2) 
	{
		this.hinhthuc2 = hinhthuc2;
	}

	public String getSotong2() 
	{
		return this.sotong2;
	}

	public void setSotong2(String sotong2) 
	{
		this.sotong2 = sotong2;
	}

	
	
	
	public String getDiengiai3()
	{
		return this.diengiai3;
	}

	public void setDiengiai3(String diengiai3)
	{
		this.diengiai3 = diengiai3;
	}
	
	public String getLoaidieukien3() 
	{
		return this.loaidieukien3;
	}

	public void setLoaidieukien3(String loaidieukien3) 
	{
		this.loaidieukien3 = loaidieukien3;
	}

	public String getHinhthuc3() 
	{
		return this.hinhthuc3;
	}

	public void setHinhthuc3(String hinhthuc3) 
	{
		this.hinhthuc3 = hinhthuc3;
	}

	public String getSotong3() 
	{
		return this.sotong3;
	}

	public void setSotong3(String sotong3) 
	{
		this.sotong3 = sotong3;
	}

	
	
	
	
	public String getDiengiai4()
	{
		return this.diengiai4;
	}

	public void setDiengiai4(String diengiai4)
	{
		this.diengiai4 = diengiai4;
	}
	
	public String getLoaidieukien4() 
	{
		return this.loaidieukien4;
	}

	public void setLoaidieukien4(String loaidieukien4) 
	{
		this.loaidieukien4 = loaidieukien4;
	}

	public String getHinhthuc4() 
	{
		return this.hinhthuc4;
	}

	public void setHinhthuc4(String hinhthuc4) 
	{
		this.hinhthuc4 = hinhthuc4;
	}

	public String getSotong4() 
	{
		return this.sotong4;
	}

	public void setSotong4(String sotong4) 
	{
		this.sotong4 = sotong4;
	}

	public String getNhomspId() 
	{
		return this.nspId;
	}

	public void setNhomspId(String nspId) 
	{
		this.nspId = nspId;
	}
	
	public String getNhomspId1() 
	{
		return this.nspId1;
	}

	public void setNhomspId1(String nspId1) 
	{
		this.nspId1 = nspId1;
	}
	
	public String getNhomspId2() 
	{
		return this.nspId2;
	}

	public void setNhomspId2(String nspId2) 
	{
		this.nspId2 = nspId2;
	}
	
	public String getNhomspId3() 
	{
		return this.nspId3;
	}

	public void setNhomspId3(String nspId3) 
	{
		this.nspId3 = nspId3;
	}
	
	public String getNhomspId4() 
	{
		return this.nspId4;
	}

	public void setNhomspId4(String nspId4) 
	{
		this.nspId4 = nspId4;
	}

	public List<ISanpham> getSpList() 
	{
		return this.spList;
	}

	public void setSpList(List<ISanpham> spList) 
	{
		this.spList = spList;
	}
	
	public List<ISanpham> getSpList1() 
	{
		return this.spList1;
	}

	public void setSpList1(List<ISanpham> spList1) 
	{
		this.spList1 = spList1;
	}
	public List<ISanpham> getSpList2() 
	{
		return this.spList2;
	}

	public void setSpList2(List<ISanpham> spList2) 
	{
		this.spList2 = spList2;
	}
	public List<ISanpham> getSpList3() 
	{
		return this.spList3;
	}

	public void setSpList3(List<ISanpham> spList3) 
	{
		this.spList3 = spList3;
	}
	public List<ISanpham> getSpList4() 
	{
		return this.spList4;
	}

	public void setSpList4(List<ISanpham> spList4) 
	{
		this.spList4 = spList4;
	}

}
