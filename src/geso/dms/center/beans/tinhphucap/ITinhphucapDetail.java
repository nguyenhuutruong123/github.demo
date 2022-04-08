package geso.dms.center.beans.tinhphucap;

import geso.dms.center.beans.tinhthunhap.IKhuvuc;
import geso.dms.center.beans.tinhthunhap.INhanvien;

import java.util.List;

public interface ITinhphucapDetail 
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
	public String[] getTinhtheongaycong();
	public void setTinhtheongaycong(String[] tinhtheonc);
	
	
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
