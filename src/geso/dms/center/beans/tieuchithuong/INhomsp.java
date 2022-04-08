package geso.dms.center.beans.tieuchithuong;

public interface INhomsp 
{
	public String getId();
	public void setId(String id);
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	public String getTongluong();
	public void setTongluong(String tongluong);
	public String getTrongso();
	public void setTrongso(String trongso);
	public String getTumuc();
	public void setTumuc(String tumuc);
	public String getDenmuc();
	public void setdenmuc(String denmuc);
	
	public String getNspId();
	public void setNspId(String nspId);
	
	public String getThuongSS();
	public void setThuongSS(String thuongSS);
	public String getThuongTDSS();
	public void setThuongTDSS(String thuongTDSS);
	public String getThuongSR();
	public void setThuongSR(String thuongSR);
	public String getThuongTDSR();
	public void setThuongTDSR(String thuongTDSR);
	public String getThuongASM();
	public void setThuongASM(String thuongASM);
	public String getThuongTDASM();
	public void setThuongTDASM(String thuongTDASM);
	
	public INhomspDetail getSpDetail();
	public void setSpDetail(INhomspDetail spDetail);
}
