package geso.dms.center.beans.ctkhuyenmai.imp;

import java.util.ArrayList;
import java.util.List;

import geso.dms.center.beans.ctkhuyenmai.ITrakmDetail;
import geso.dms.center.beans.dieukienkhuyenmai.ISanpham;

public class TrakmDetail implements ITrakmDetail 
{
	String diengiai;
	String loaitra;
	String hinhthuc;
	String sotong;
	String nspId;
	
	List<ISanpham> spList;
	
	public TrakmDetail()
	{
		this.diengiai = "";
		this.loaitra = "";
		this.hinhthuc = "";
		this.sotong = "";
		this.nspId = "";
		this.spList = new ArrayList<ISanpham>();
	}
	
	public TrakmDetail(String diengiai, String loaitra, String hinhthuc, String sotong, String nspId)
	{
		this.diengiai = diengiai;
		this.loaitra = loaitra;
		this.hinhthuc = hinhthuc;
		this.sotong = sotong;
		this.nspId = nspId;
		this.spList = new ArrayList<ISanpham>();
	}

	public String getDiengiai()
	{
		return this.diengiai;
	}

	public void setDiengiai(String diengiai)
	{
		this.diengiai = diengiai;
	}
	
	public String getLoaitra() 
	{
		return this.loaitra;
	}

	public void setLoaitra(String loaitra) 
	{
		this.loaitra = loaitra;
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

	public String getNhomspId() 
	{
		return this.nspId;
	}

	public void setNhomspId(String nspId) 
	{
		this.nspId = nspId;
	}

	public List<ISanpham> getSpList() 
	{
		return this.spList;
	}

	public void setSpList(List<ISanpham> spList) 
	{
		this.spList = spList;
	}

}
