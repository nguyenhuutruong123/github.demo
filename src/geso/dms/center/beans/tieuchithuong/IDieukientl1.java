package geso.dms.center.beans.tieuchithuong;

public interface IDieukientl1 
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
	
	public IDieukientlDetail getDieukienDetail();
	public void setDieukienDetail(IDieukientlDetail dieukienDetail);
	
	public IDieukientlDetail getDieukienDetail1();
	public void setDieukienDetail1(IDieukientlDetail dieukienDetail1);
	
	public IDieukientlDetail getDieukienDetail2();
	public void setDieukienDetail2(IDieukientlDetail dieukienDetail2);
	
	public IDieukientlDetail getDieukienDetail3();
	public void setDieukienDetail3(IDieukientlDetail dieukienDetail3);
	
	public IDieukientlDetail getDieukienDetail4();
	public void setDieukienDetail4(IDieukientlDetail dieukienDetail4);
	
}
