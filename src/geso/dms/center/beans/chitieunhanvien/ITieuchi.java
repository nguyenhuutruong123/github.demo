package geso.htp.center.beans.chitieunhanvien;

import java.util.List;

public interface ITieuchi {

	public void setID(String id);
	public String getID();
	
	public void setDiengiai(String diengiai);
	public String getDiengiai();
	
	public List<INhanvien> getNhanvien();
	public void setNhanvien(List<INhanvien> nhanvien);

}
