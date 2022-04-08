package geso.dms.center.beans.tinhthunhap;

import java.util.List;

public interface ITinhthunhapDetail
{
	public String getId();

	public void setId(String id);

	public String GetNppId();

	public String setNppId(String NppId);

	public String getNppSelected();

	public void setNppSelected(String NppId);

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

	public String getKvTenSelected();

	public void setKvTenSelected(String kvTenSelected);

	public String getLuongCB();

	public void setLuongCB(String luongCB);

	public String getPhantramluongTG();

	public void setPhantramluongTG(String ptltg);

	public String getPhantramluongHQ();

	public void setPhantramluongHQ(String ptlhq);

	public String getBaohiemtu();

	public void setBaohiemtu(String baohiemTu);

	public String getBaohiemDen();

	public void setBaohiemDen(String baohiemDen);

	public String getChucvu();

	public void setChucvu(String chucvu);

	public String getBaohiem();

	public void setBaohiem(String baohiem);

	public String getCongdoan();

	public void setCongdoan(String congdoan);

	public String getThucdatngaycong();

	public void setThucdatngaycong(String tdnc);

	public String[] getMaDetail();

	public void setMaDetail(String[] maDetail);

	public String[] getNoidung();

	public void setNoidung(String[] noidung);

	public String[] getTrongso();

	public void setTrongso(String[] trongso);

	public String[] getMucbaohiem();

	public void setMucbaohiem(String[] mucbaohiem);

	public String[] getThuongSRvuotmuc();

	public void setThuongSRvuotmuc(String[] thuongSRvm);

	public List<IKhuvuc> getKhuvucList();

	public void setKhuvucList(List<IKhuvuc> kvList);

	public List<INhanvien> getNhanvienList();

	public void setNhanvienList(List<INhanvien> nvList);

	public List<IThuongvuotmuc> getThuongvmList();

	public void setThuongvuotmucList(List<IThuongvuotmuc> tvmList);

	public List<INhaPhanPhoi> getNhaPhanPhoiList();

	public void setNhanPhanPhoiList(List<INhaPhanPhoi> nppList);

	public void InitNhanVien();

	public void InitNhaPhanPhoi();

}
