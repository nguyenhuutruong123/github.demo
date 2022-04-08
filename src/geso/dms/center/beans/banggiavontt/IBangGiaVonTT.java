package geso.dms.center.beans.banggiavontt;

import java.util.List;

public interface IBangGiaVonTT {
	public String getUserId();
	public void setUserId(String userId);
	public String getId();
	public void setId(String id);
	public String getTen();
	public void setTen(String ten);
	public String getDvkdTen();
	public void setDvkdTen(String dvkd);
	public String getDvkdId();
	public void setDvkdId(String dvkdId); 
	public String getNgaytao();
	public void setNgaytao(String ngaytao);
	public String getNguoitao();
	public void setNguoitao(String nguoitao);
	public String getNgaysua();
	public void setNgaysua(String ngaysua);	
	public String getNguoisua();
	public void setNguoisua(String nguoisua);
	public String getMessage();
	public void setMessage(String msg);	
	public void setListBangGia(String sql);
	public List<IBangGiaVonTT> getListBangGia();
	public void setListBangGia_SanPham(List<IBangGiaVonTT_SanPham> list);
	public List<IBangGiaVonTT_SanPham> getListBangGia_SanPham();
	public boolean SaveBangGiaVonTT();
	public boolean EditBangGiaVonTT();
}
