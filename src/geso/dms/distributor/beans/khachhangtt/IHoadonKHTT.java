package geso.dms.distributor.beans.khachhangtt;

public interface IHoadonKHTT extends Comparable<IHoadonKHTT>
{
	public String getId();
	public void setId(String id);
	public String getTtId();
	
	public void setTtId(String ttId); 
	
	public String getSotienNT();	
	public void setSotienNT(String sotienNT); 
	
	public String getMahopdong();	
	public void setMahopdong(String mahopdong); 
	
	public String getNppId();	
	public void setNppId(String nppId); 
	public String getNppMa();	
	public void setNppMa(String nppMa); 
	
	public String getKhId();	
	public void setKhId(String khId); 
	public String getKhMa();	
	public void setKhMa(String khMa); 

	public String getKyhieu();
	public void setKyhieu(String kyhieu);
	public String getSo();
	public void setSo(String so);
	public String getNgay();
	public void setNgay(String ngay);
	public String getTongtiencoVAT();
	public void setTongtiencoVAT(String aVat);
	
	public String getSotienno();

	public void setSotienno(String sotienno); 
	
	public String getThanhtoan();
	public void setThanhtoan(String thanhtoan);
	public String getConlai();
	public void setConlai(String conlai);
	public String getTrahet();
	public void setTrahet(String trahet);
	public String getThutienId();
	public void setThutienId(String thutienId);
	
	public String getTygia();
	public void setTygia(String Tygia);
	public String getConlai_CantruCN();
	
	public String getLoaihd();
	public void setLoaihd(String loaihd);
	public String getTenloaihd();
	public void setTenloaihd(String tenloaihd);
	
	public String getIsKHLe() ;

	public void setIsKHLe(String isKHLe);
	
}
