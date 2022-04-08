package geso.dms.center.beans.cttrungbay.imp;

import geso.dms.center.beans.cttrungbay.INhomsptrungbay;

public class Nhomsptrungbay implements INhomsptrungbay 
{
	String id;
	String diengiai;
	String tongluong;
	String tongtien;
	String dasudung;
	String pheptoan;
	
	public Nhomsptrungbay()
	{
		this.id = "";
		this.diengiai = "";
		this.tongluong = "";
		this.tongtien = "";
		this.pheptoan = "1"; //mac dinh la phep toan And
	}
	
	public Nhomsptrungbay(String id, String diengiai, String tongluong, String tongtien)
	{
		this.id = id;
		this.diengiai = diengiai;
		this.tongluong = tongluong;
		this.tongtien = tongtien;
		this.pheptoan = "1";
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

	
	public void setDasudung(String dasudung) {
		
		this.dasudung = dasudung;
	}

	
	public String getDasudung() {
		
		return this.dasudung;
	}
	
public void setPheptoan(String pheptoan) {
		
		this.pheptoan = pheptoan;
	}

	public String getPheptoan() {

		return this.pheptoan;
	}

	
}
