package geso.dms.center.beans.nhomsptrungbay.imp;
import geso.dms.center.beans.nhomsptrungbay.ISanpham; ;

public class Sanpham implements ISanpham
{	
	private static final long serialVersionUID = -9217977546733610214L;
	String id;
	String masp;
	String tensp;
	String soluong;
	String dongia;
		
	public Sanpham()
	{
		this.id = "";
		this.masp = "";
		this.tensp = "";
		this.soluong = "";
		this.dongia = "";
	}
	
	public Sanpham(String[] param)
	{
		this.id = param[0];
		this.masp = param[1];
		this.tensp = param[2];
		this.soluong = param[3];
		this.dongia = param[4];
	}
	
	public Sanpham(String spId, String spMa, String spTen, String soluong, String dongia)
	{
		this.id = spId;
		this.masp = spMa;
		this.tensp = spTen;
		this.soluong = soluong;
		this.dongia = dongia;
	}
	
	public String getId() 
	{	
		return this.id;
	}

	public void setId(String id) 
	{
		this.id = id;	
	}
	
	public String getMasanpham()
	{	
		return this.masp;
	}
	
	public void setMasanpham(String masp)
	{
		this.masp = masp;		
	}
	
	public String getTensanpham() 
	{	
		return this.tensp;
	}
	
	public void setTensanpham(String tensp) 
	{
		this.tensp = tensp;	
	}

	public String getSoluong()
	{
		return this.soluong;
	}

	public void setSoluong(String soluong) 
	{
		this.soluong = soluong;
	}

	public String getDongia()
	{
		return this.dongia;
	}

	public void setDongia(String dongia) 
	{
		this.dongia = dongia;
	}
	
}

