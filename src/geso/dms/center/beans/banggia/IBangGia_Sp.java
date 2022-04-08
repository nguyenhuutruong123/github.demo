package geso.dms.center.beans.banggia;

public interface IBangGia_Sp
{
	public String getHopdong();
	public void setHopdong(String hopdong);
	
	public String getIdsp();
	public void setIdsp(String idsp);
	public String getMasp();
	public void setMasp(String masp);
	public String getTensp();
	public void setTensp(String tensp);
	public String getTenNewsp();
	public void setTenNew(String tenNew);
	public String getGiaban();
	public void setGiaban(String giaban);
	public String getGiabanNew();
	public void setGiabanNew(String giabanNew);
	public String getDonvi();
	public void setDonvi(String donvi);
	public String getChonban();
	public void setChonban(String chonban);
	
	//Hopdong
	public String getSoluong();
	public void setSoluong(String soluong);
	public String getDongia();
	public void setDongia(String dongia);
	public String getNgaygiao();
	public void setNgaygiao(String ngaygiao);
	public String getNgaygiaoDen();
	public void setNgaygiaoDen(String ngaygiaoDen);
	public String getSoluong_Dagiao();
	public void setSoluong_Dagiao(String soluong);
	public String getSoluong_Conlai();
	public void setSoluong_Conlai(String soluong);
	public String getDvdlId();
	public void setDvdlId(String dvdlId);
	
	
	//So luong, don gia ap dung trong hop dong
	public String[] getSoluong_Thaydoigia();
	public void setSoluong_Thaydoigia(String[] soluong_thaydoigia);
	public String[] getSoluong_Thaydoigia_Den();
	public void setSoluong_Thaydoigia_Den(String[] soluong_thaydoigia);
	
	public String[] getDongia_Thaydoigia();
	public void setDongia_Thaydoigia(String[] dongia_thaydoigia);
	
	public String getSoluong_Tang();
	public void setSoluong_Tang(String soluong_tang);
	public String getDongia_Moi();
	public void setDongia_Moi(String dongia_moi);
	public String getSpchietkhau();

	public void setSpchietkhau(String spchietkhau);
}
