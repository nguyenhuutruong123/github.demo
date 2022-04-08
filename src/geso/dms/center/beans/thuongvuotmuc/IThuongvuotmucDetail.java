package geso.dms.center.beans.thuongvuotmuc;

import geso.dms.center.beans.tinhthunhap.IKhuvuc;
import geso.dms.center.beans.tinhthunhap.INhanvien;

import java.util.List;

public interface IThuongvuotmucDetail
{
	public String getId();

	public void setId(String id);

	public String getMa();

	public void setMa(String ma);

	public String getDiengiai();

	public void setDiengiai(String diengiai);

	public String getDvkdId();

	public void setDvkdId(String dvkdId);

	public String getKbhId();

	public void setKbhId(String kbhId);

	public String getKvId();

	public void setKvId(String vungId);

	public String getChucvu();

	public void setChucvu(String chucvu);

	public String[] getNhomthuong();

	public void setNhomthuong(String[] nhomthuong);

	public String[] getTumuc();

	public void setTumuc(String[] tumuc);

	public String[] getDenmuc();

	public void setDenmuc(String[] denmuc);

	public String[] getThuong();

	public void setThuong(String[] thuong);

	public List<IKhuvuc> getKhuvucList();

	public void setKhuvucList(List<IKhuvuc> kvList);

	public List<INhanvien> getNhanvienList();

	public void setNhanvienList(List<INhanvien> nvList);

	public String getGsbhSelected();

	public void setGsbhSelected(String gsbhIds);

	public String getDdkdSelected();

	public void setDdkdSelected(String ddkdIds);

	public String getNhanvienIds();

	public void setNhanvienIds(String nvIds);

	public String getSTT();

	public void setSTT(String stt);

	public void InitNhanVien();

	public void InitNhanVienSelected();

}
