package geso.dms.center.beans.tieuchidanhgia.imp;

import geso.dms.center.beans.tieuchidanhgia.ITieuchiDetail;

public class TieuchiDetail implements ITieuchiDetail 
{
	String id;
	String ma;
	String diengiai;
	String trongso;
	
	String chamlan1;
	String chamlan2;
	String chamlan3;
	
	public TieuchiDetail()
	{
		this.id = "";
		this.ma = "";
		this.diengiai = "";
		this.trongso = "";
		
		this.chamlan1 = "";
		this.chamlan2 = "";
		this.chamlan3 = "";
	}

	public String getMa() 
	{
		return this.ma;
	}

	public void setMa(String ma) 
	{
		this.ma = ma;
	}

	public String getDiengiai() 
	{
		return this.diengiai;
	}

	public void setDiengiai(String diengiai) 
	{
		this.diengiai = diengiai;
	}

	public String getTrongso() 
	{
		return this.trongso;
	}

	public void setTrongso(String trongso) 
	{
		this.trongso = trongso;
	}

	public String getChamlan1()
	{
		return this.chamlan1;
	}

	public void setChamlan1(String chamlan1) 
	{
		this.chamlan1 = chamlan1;
	}

	public String getChamlan2() 
	{
		return this.chamlan2;
	}

	public void setChamlan2(String chamlan2)
	{
		this.chamlan2 = chamlan2;
	}

	public String getChamlan3()
	{
		return this.chamlan3;
	}

	public void setChamlan3(String chamlan3) 
	{
		this.chamlan3 = chamlan3;
	}

	public String getId() 
	{
		return this.id;
	}

	public void setId(String id)
	{
		this.id = id;
	}
}
