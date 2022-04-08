package geso.dms.distributor.beans.chitieunpp.imp;
import geso.dms.distributor.beans.chitieunpp.IChitieusku;

public class ChitieuSku implements IChitieusku
{
	String id,tensp,masp,soluong;
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id=id;
	}

	public String getMasanpham() {
		return this.masp;
	}


	public void setMasanpham(String masp) {
		this.masp=masp;
	}

	
	public String getTensanpham() 
	{	
		return this.tensp;
	}	
	public void setTensanpham(String tensp) {
		this.tensp=tensp;
		
	}
	public String getSoluong() {
		
		return this.soluong;
	}
	public void setSoluong(String soluong) 
	{
		
		this.soluong=soluong;
	}

}
