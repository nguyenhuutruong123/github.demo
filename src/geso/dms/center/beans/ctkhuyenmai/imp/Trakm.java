package geso.dms.center.beans.ctkhuyenmai.imp;

import geso.dms.center.beans.ctkhuyenmai.ITrakm;
import geso.dms.center.beans.ctkhuyenmai.ITrakmDetail;

public class Trakm implements ITrakm 
{
	String id;
	String diengiai;
	String tongluong;
	String tongtien;
	String chietkhau;
	String pheptoan;
	String thutudk;
	boolean theothung;
	
	ITrakmDetail traDetail;
	
	public Trakm()
	{
		this.id = "";
		this.diengiai = "";
		this.tongluong = "";
		this.tongtien = "";
		this.chietkhau = "";
		this.pheptoan = "";
		this.thutudk = "";
		this.theothung = false;
		
		this.traDetail = new TrakmDetail();
	}
	
	public Trakm(String id, String diengiai, String tongluong, String tongtien, String chietkhau, String pheptoan, String thutudk)
	{
		this.id = id;
		this.diengiai = diengiai;
		this.tongluong = tongluong;
		this.tongtien = tongtien;
		this.chietkhau = chietkhau;
		this.pheptoan = pheptoan;
		this.thutudk = thutudk;
		this.theothung = false;
		
		this.traDetail = new TrakmDetail();
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

	public ITrakmDetail getTraDetail() 
	{
		return this.traDetail;
	}

	public void setTraDetail(ITrakmDetail dieukienDetail) 
	{
		this.traDetail = dieukienDetail;
	}

	public String getChietkhau() 
	{
		return this.chietkhau;
	}

	public void setChietkhau(String chietkhau) 
	{
		this.chietkhau = chietkhau;
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
