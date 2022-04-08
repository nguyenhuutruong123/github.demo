package geso.dms.distributor.beans.denghidathangnpp;

public interface IDanhsachsanpham {
   
	public void setMasp(String masp);//masanpham
	public String getMasp();
	
	public void setIdSanPham(String  idsanpham);
	public String getIdSanPham();
	
	public void setTensp(String tensp);
	public String getTensp();
	
	public void setTondau(String tondau);
	public String geTondau();
	
	public void setBantheongay(String bantheongay);
	public String getBantheongay();
	public void setSL_BanTuanTruoc(int sl_bantuantruoc);
	public int getSL_BanTuanTruoc();
	public void setTonngay(String tonngay);
	public String getTonngay();
	
	public void setDukienban(String dukienban);
	public String getDukienban();
	
	public void setDenghi(String denghi);
	public String getDenghi();
	
	public void setDonvi(String donvi);
	public String getDonvi();
	
	public void setDongia(String dongia);
	public String getDongia();
	
	public void setTongtien(String tongtien);
	public String getTongtien();
	
	public void setDadat(String dadat);
	public String getDadat();
	
	public void setSoluong(String soluong);
	public String getSoluong();
	
	public void setConlai(String conlai);
	public String getConlai();
	
	public void setTonicp(String tonicp);
	public String getTonicp();
	
	public void setnppId(String nppId);
	public String getnppId();
	public void setthieuhang(String thieuhang);
	public String getthieuhang();
	public void init();
	public int getTonCuoi();
	public void setTonCuoi(int toncuoi);
	public void DBclose();
	
}
