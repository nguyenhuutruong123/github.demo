package geso.dms.center.beans.ctkhuyenmai;

public interface IDieukienkm 
{
	public String getId();
	public void setId(String Id);
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	public String getTongluong();
	public void setTongluong(String tongluong);
	public String getTongtien();
	public void setTongtien(String tongtien);
	public String getThutudk();
	public void setThutudk(String thutu);
	public String getPheptoan();
	public void setPheptoan(String pheptoan);
	
	public boolean isTheothung();
	public void setTheothung(boolean theothung);
	
	public IDieukienDetail getDieukienDetail();
	public void setDieukineDetail(IDieukienDetail dieukienDetail);
	
}
