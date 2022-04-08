package geso.dms.center.beans.tieuchithuong.imp;

import java.util.ArrayList;
import java.util.List;

import geso.dms.center.beans.dieukienkhuyenmai.ISanpham;
import geso.dms.center.beans.tieuchithuong.INhomspDetail;

public class NhomspDetail implements INhomspDetail 
{
	String id;
	String diengiai;
	String tongluong;
	
	List<ISanpham> spList;

	public NhomspDetail()
	{
		this.id = "";
		this.diengiai = "";
		this.tongluong = "";
		
		this.spList = new ArrayList<ISanpham>();
	}
	
	public String getId() 
	{
		return this.id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getDiengiai()
	{
		return this.diengiai;
	}

	public void setDiengiai(String diengiai) 
	{
		this.diengiai = diengiai;
	}

	public String getTongluong() 
	{
		return this.tongluong;
	}

	public void setTongluong(String tongluong) 
	{
		this.tongluong = tongluong;
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
