package geso.dms.center.beans.donmuahang.imp;

import geso.dms.center.beans.donmuahang.ISanPhamTraKM;

public class SanPhamTraKm implements ISanPhamTraKM{

	
	String ctkmid="";
	String Spid="";
	String SpidTT="";
	String scheme="";
	String diengiai="";
	String spma="";
	String spten="";
	String Spmatt="";
	String Sptentt="";
	float soluongtt=0;
	float soluong=0;
	String Nppid="";
	String KbhId="";
	String NppTen="";
	
	@Override
	public String getCtkm() {
		// TODO Auto-generated method stub
		return this.ctkmid;
	}

	@Override
	public void setctkm(String ctkm) {
		// TODO Auto-generated method stub
		this.ctkmid=ctkm;
	}

	@Override
	public String getSpId() {
		// TODO Auto-generated method stub
		return this.Spid;
	}

	@Override
	public void setSpId(String SpId) {
		// TODO Auto-generated method stub
		this.Spid=SpId;
	}

	@Override
	public String getSpIdTT() {
		// TODO Auto-generated method stub
		return this.SpidTT;
	}

	@Override
	public void setSpIdTT(String SpIdtt) {
		// TODO Auto-generated method stub
		this.SpidTT=SpIdtt;
	}

	@Override
	public String getdiengiai() {
		// TODO Auto-generated method stub
		return this.diengiai;
	}

	@Override
	public void setdiengiai(String diengiai) {
		// TODO Auto-generated method stub
		this.diengiai=diengiai;
	}

	@Override
	public String getScheme() {
		// TODO Auto-generated method stub
		return this.scheme;
	}

	@Override
	public void setScheme(String scheme) {
		// TODO Auto-generated method stub
		this.scheme=scheme;
	}

	@Override
	public String getspma() {
		// TODO Auto-generated method stub
		return this.spma;
	}

	@Override
	public void setspma(String spma) {
		// TODO Auto-generated method stub
		this.spma=spma;
		
	}

	@Override
	public String getspten() {
		// TODO Auto-generated method stub
		return this.spten;
	}

	@Override
	public void setspten(String spten) {
		// TODO Auto-generated method stub
		this.spten=spten;
	}

	@Override
	public String getspmatt() {
		// TODO Auto-generated method stub
		return this.Spmatt;
	}

	@Override
	public void setspmatt(String spmatt) {
		// TODO Auto-generated method stub
		this.Spmatt=spmatt;
	}

	@Override
	public String getsptentt() {
		// TODO Auto-generated method stub
		return this.Sptentt;
	}

	@Override
	public void setsptentt(String sptentt) {
		// TODO Auto-generated method stub
		this.Sptentt=sptentt;
	}

	@Override
	public float getsoluongtt() {
		// TODO Auto-generated method stub
		return this.soluongtt;
	}

	@Override
	public void setsoluongtt(float soluong) {
		// TODO Auto-generated method stub
		this.soluongtt=soluong;
	}

	@Override
	public void setNPPId(String npp) {
		// TODO Auto-generated method stub
		this.Nppid=npp;
	}

	@Override
	public String getNPPId() {
		// TODO Auto-generated method stub
		return this.Nppid;
	}

	@Override
	public void setNPPTen(String nppten) {
		// TODO Auto-generated method stub
		this.NppTen=nppten;
	}

	@Override
	public String getNPPTen() {
		// TODO Auto-generated method stub
		return this.NppTen;
	}

	@Override
	public float getsoluong() {
		// TODO Auto-generated method stub
		return this.soluong;
	}

	@Override
	public void setsoluong(float sl) {
		// TODO Auto-generated method stub
		this.soluong=sl;
	}

	@Override
	public void setKBHId(String KBHID) {
		// TODO Auto-generated method stub
		this.KbhId=KBHID;
	}

	@Override
	public String getKBHId() {
		// TODO Auto-generated method stub
		return this.KbhId;
	}


}
