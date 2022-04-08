package geso.dms.center.beans.tieuchithuong;

import java.util.List;

public interface ITieuChiThuongDetails
{
	public String getId();

	public void setId(String id);

	public String getNhomSpId();

	public void setNhomSpId(String NhomSpId);

	public String getDvkdId();

	public void setDvkdId(String dvkdId);

	public String getKbhId();

	public void setKbhId(String kbhId);

	public String getKvId();

	public void setKvId(String vungId);

	public String getChucvu();

	public void setChucvu(String chucvu);

	public List<IKhuVuc> getKhuvucList();

	public void setKhuvucList(List<IKhuVuc> kvList);

	public List<INhanVien> getNhanvienList();

	public void setNhanvienList(List<INhanVien> nvList);

}
