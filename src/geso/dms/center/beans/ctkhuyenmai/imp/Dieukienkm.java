package geso.dms.center.beans.ctkhuyenmai.imp;

import java.util.ArrayList;

import geso.dms.center.beans.ctkhuyenmai.IDieukienDetail;
import geso.dms.center.beans.ctkhuyenmai.IDieukienkm;

public class Dieukienkm implements IDieukienkm
{
	String id;
	String diengiai;
	String tongluong;
	String tongtien;
	String pheptoan;
	String thutudk;
	boolean theothung;
	
	IDieukienDetail dkDetail;
	
	public Dieukienkm()
	{
		this.id = "";
		this.diengiai = "";
		this.tongluong = "";
		this.tongtien = "";
		this.pheptoan = "";
		this.thutudk = "";
		this.theothung = false;
		
		this.dkDetail = new DieukienDetail();
	}
	
	public Dieukienkm(String id, String diengiai, String tongluong, String tongtien, String pheptoan, String thutudk)
	{
		this.id = id;
		this.diengiai = diengiai;
		this.tongluong = tongluong;
		this.tongtien = tongtien;
		this.pheptoan = pheptoan;
		this.thutudk = thutudk;
		this.theothung = false;
		
		this.dkDetail = new DieukienDetail();
	}
	
	public String getId() 
	{	
		return this.id;
	}
	
	public void setId(String Id) 
	{
		this.id = Id;		
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
	
	public String getTongtien() 
	{		
		return this.tongtien;
	}
	
	public void setTongtien(String tongtien) 
	{
		this.tongtien = tongtien;	
	}
	
	public String getThutudk() 
	{		
		return this.thutudk;
	}
	
	public void setThutudk(String thutu) 
	{
		this.thutudk = thutu;		
	}
	
	public String getPheptoan() 
	{		
		return this.pheptoan;
	}
	
	public void setPheptoan(String pheptoan) 
	{
		this.pheptoan = pheptoan;		
	}

	public IDieukienDetail getDieukienDetail() 
	{
		return this.dkDetail;
	}

	public void setDieukineDetail(IDieukienDetail dieukienDetail) 
	{
		this.dkDetail = dieukienDetail;
	}

	public boolean isTheothung() 
	{
		return this.theothung;
	}

	public void setTheothung(boolean theothung) 
	{
		this.theothung = theothung;
	}

}
