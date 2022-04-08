/*
 * author : KHOAND
 * Interface name : IChiTieuNPP;
 * Description : 
 * Date create : 2011-10-20 
 * Modification Log : Store infomation about money of distributor which distributor have to get in month of year 
 *--- date--- name------ description  
 *2011-10-21 khoand  --re build 
 */
package geso.dms.center.beans.chitieu;

public interface IChiTieuNPP
{
	public void setId(double id);
	public double getid();

	public void setNhaPPId(String nppid);
	public String getNhaPPId();

	public void setSoTien(double sotien);
	public double getSoTien();

	public void setTenNhaPP(String tennpp);
	public String getTenNhaPP();

	public void setSoDonHang(String sodonhang);
	public String getSodonhang();
	
	
	public void setSoKhachHang_MuaHang(String sodonhang);
	public String getSoKhachHang_MuaHang();

	
	public void setSoKhachHang_PhatSinh(String sodonhang);
	public String getSoKhachHang_PhatSinh();
	
	public void setSoSku(String sosku);
	public String getsoSku();

	public void setSanluongtrendh(String sanluongdh);
	public String getSanluongtrendh();

	public void setTrungBinhThang(double trungbinhthang);
	public double getTrungBinhThang();

	public void setPhantram(double phantram);
	public double getPhantram();

}
