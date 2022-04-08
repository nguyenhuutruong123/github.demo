package geso.dms.center.beans.kehoachnhanvien;

import java.io.Serializable;
import java.util.List;

public interface IKeHoachNhanVienNgay extends Serializable 
{
	public String getKeHoachId();
	public void setKeHoachId(String khId);
	public String getNgay();
	public void setNgay(String ngay);
	
	public List<IKeHoachNhanVienChiTiet> getNppList();
	public void setNppList(List<IKeHoachNhanVienChiTiet> nppList);
	
	public List<IKeHoachNhanVienChiTiet> getThiTruongList();
	public void setThiTruongList(List<IKeHoachNhanVienChiTiet> thitruongList);
	
	public List<IKeHoachNhanVienChiTiet> getTBHList();
	public void setTBHList(List<IKeHoachNhanVienChiTiet> TBHList);
	
}

