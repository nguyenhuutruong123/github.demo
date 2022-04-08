package geso.dms.center.beans.tieuchithuong.imp;
import geso.dms.center.beans.tieuchithuong.INhomsp;
import geso.dms.center.beans.tieuchithuong.INhomspDetail;

public class Nhomsp implements INhomsp 
{
	String id;
	String trongso;
	String diengiai;
	String tongluong;
	String tumuc;
	String denmuc;
	
	String nspId;
	String thuongSS;
	String thuongTDSS;
	String thuongSR;
	String thuongTDSR;
	String thuongASM;
	String thuongTDASM;
	
	INhomspDetail spDetail;
	
	public Nhomsp()
	{
		this.id = "";
		this.trongso = "";
		this.diengiai = "";
		this.tongluong = "";
		this.tumuc = "";
		this.denmuc = "";
		
		this.thuongSS = "";
		this.thuongTDSS = "";
		this.thuongSR = "";
		this.thuongTDSR = "";
		this.thuongASM = "";
		this.thuongTDASM = "";
		
		this.spDetail = new NhomspDetail();
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

	public String getId() 
	{
		return this.id;
	}

	public void setId(String id) 
	{
		this.id = id;
	}

	public String getTrongso() 
	{
		return this.trongso;
	}

	public void setTrongso(String trongso) 
	{
		this.trongso = trongso;
	}

	public INhomspDetail getSpDetail() 
	{
		return this.spDetail;
	}

	public void setSpDetail(INhomspDetail spDetail) 
	{
		this.spDetail = spDetail;
	}

	public String getTumuc() 
	{
		return this.tumuc;
	}

	public void setTumuc(String tumuc) 
	{
		this.tumuc = tumuc;
	}

	public String getDenmuc() 
	{
		return this.denmuc;
	}

	public void setdenmuc(String denmuc) 
	{
		this.denmuc = denmuc;
	}

	public String getNspId() 
	{
		return this.nspId;
	}

	public void setNspId(String nspId) 
	{
		this.nspId = nspId;
	}
	
	public String getThuongSS() 
	{
		return this.thuongSS;
	}

	public void setThuongSS(String thuongSS) 
	{
		this.thuongSS = thuongSS;
	}

	public String getThuongTDSS() 
	{
		return this.thuongTDSS;
	}

	public void setThuongTDSS(String thuongTDSS) 
	{
		this.thuongTDSS = thuongTDSS;
	}

	public String getThuongSR()
	{
		return this.thuongSR;
	}

	public void setThuongSR(String thuongSR) 
	{
		this.thuongSR = thuongSR;
	}

	public String getThuongTDSR() 
	{
		return this.thuongTDSR;
	}

	public void setThuongTDSR(String thuongTDSR)
	{
		this.thuongTDSR = thuongTDSR;
	}

	public String getThuongASM() 
	{
		return this.thuongASM;
	}

	public void setThuongASM(String thuongASM) 
	{
		this.thuongASM = thuongASM;
	}

	public String getThuongTDASM()
	{
		return this.thuongTDASM;
	}

	public void setThuongTDASM(String thuongTDASM) 
	{
		this.thuongTDASM = thuongTDASM;
	}
	
}
