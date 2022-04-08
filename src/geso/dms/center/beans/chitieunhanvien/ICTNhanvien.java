package geso.htp.center.beans.chitieunhanvien;

import java.util.List;

public interface ICTNhanvien {

	public String getId();
	public void setId(String id) ;
	public void setTen(String ten) ;
	public String getTen();
	public String getManv();
	public void setManv(String manv);
	public double getChitieuluong();
	public void setChitieuluong(double chitieuluong);	
	public double getSalesin();
	public void setSalesin(double salesin) ;
	public double getSalesout();
	public void setSalesout(double salesout);
	public double getDonhang();
	public void setDonhang(double donhang);
	public void setSkudonhang(double skudonhang);
	public double getSkudonhang() ;
	public void setSokhmoi(double sokhmoi) ;
	public double getSokhmoi() ;
	
	public List<ICTNhanvien_NSP> getCtNspList() ;
	public void setCtNspList(List<ICTNhanvien_NSP> ctNspList);

}
