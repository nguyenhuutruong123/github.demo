package geso.dms.center.beans.congnonpp.imp;
import geso.dms.center.beans.congnonpp.INhaphanphoi;

public class Nhaphanphoi implements INhaphanphoi {

	String id;	
	String tennpp;
	String sotien;		
	
	public Nhaphanphoi()
	{
		this.id = "";		
		this.tennpp = "";
		this.sotien = "";			
	}
	
	public Nhaphanphoi(String[] param)
	{
		this.id = param[0];		
		this.tennpp = param[1];
		this.sotien = param[2];		
	}

	public String getId() {
		
		return this.id;
	}


	public void setId(String id) {
		
		this.id = id;
	}


	public String getTennpp() {
		
		return this.tennpp;
	}


	public void setTennpp(String tennpp) {
		
		this.tennpp = tennpp;
	}


	public String getSotien() {
		
		return this.sotien;
	}


	public void setSotien(String sotien) {
		
		this.sotien = sotien;
	}	
}
