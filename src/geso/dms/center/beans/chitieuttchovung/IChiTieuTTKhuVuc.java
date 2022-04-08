/*
 * creater : khoana
 * date create ;2011- 24-11
 * interface name :IChiTieuTTVung.
 * Description: include infomation detail target for each region
 */
package geso.dms.center.beans.chitieuttchovung;
public interface IChiTieuTTKhuVuc {
public void setID(String id);
public String getId();
public void setKhuVucId(String khuvucid);
public String getKhuVucId();
public void setTenKhuVucId(String tenkhuvucid);
public String getTenKhuVucId();
public void setChiTieu(double chitieu);
public  double getChiTieu();
public void setSoDonHang(String sodonhang);
public String getSoDonHang();
public void setSoSKU(String soSku);
public String getSoSKU();
public void setTrungBinhThang(double trungbinhthang);
public double getTrungBinhThang();
}
