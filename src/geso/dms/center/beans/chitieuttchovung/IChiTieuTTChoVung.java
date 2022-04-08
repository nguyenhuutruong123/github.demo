/*
 * design : khoana.
 * interface name : ChiTieuTTChoVung:  to identify level of money which distributor have to sell in identify time
 * Date : 2011-10-24 
 */
package geso.dms.center.beans.chitieuttchovung;
import geso.dms.center.beans.chitieuttchovung.imp.ChiTieuTTChoVung;
import geso.dms.center.beans.chitieuttchovung.imp.ChiTieuTTKhuVuc;

import java.sql.ResultSet;
import java.util.List;

public interface IChiTieuTTChoVung {
	public void setChitieu(double chitieu);
	public double getChitieu();

	public void setID(double id);
	public double getID();
	public void setNguoiTao(String nguoitao);
	public String getNguoiTao();
	public void setNguoiSua(String nguoisua);
	public String getNguoiSua();
	public void setThang(int thang);
	public int getThang();
	public void setNam(int nam);
	public int getNam();
	public void setMessage(String strmessage);
	public String getMessage();
	public void setSoNgayLamViec(String songaylamviec);
	public String getSoNgayLamViec();
	public void setTenKenh(String tenkenh);
	public String getTenKenh();
	/**
	 * this is  time finish a chitiet , type of data is datetime ,default type of date : yyyy-MM-dd
	 * @param ngayketthuc
	 */
	public void  setNgayKetThuc(String ngayketthuc);
	public String getNgayKetThuc();
	/**
	 * this is the time create a chitiet, default is day curent 
	 * @param ngaytao
	 */
	public void setNgayTao(String ngaytao);
	public String getNgayTao();
	/**
	 * this is the time edit record chitiet in database, you only can edit chitieu where month of current smaller than month and year of chitieu
	 * @param nguoisua
	 */
	public void setNgaySua(String nguoisua);
	public String getNgaySua();
	/**
	 * description about chitieu.
	 * @param diengiai
	 */
	public void setUserId(String userid);
	public String getUserId();

	public void setDienGiai(String diengiai);
	public String getDienGiai();
	
	public void setTrangThai(String trangthai);
	public String getTrangThai();
	
	
	/**
	 * 
	 * @return true if save chitieu success in to database
	 */
	public boolean  SaveChiTieu();

	/**
	 * 
	 * @return true if edit Save chitieu success ,you only access this method when datetime current smaller than datetime(month and year) of chitieu.
	 */
	public boolean EditChiTieu();
	public boolean EditChiTieuSec();
	/**
	 * 
	 * @return true if delete success chitieu,you only access this method when datetime current  smaller than time(month and year) of chitieu();
	 */
	public boolean DeleteChitieu();
	public boolean DeleteChitieu_Sec();
	/**
	 * funtion return list of chiiet depent of String of sql 
	 * Note: sql in this method have same as same, only different obout condition " where..."
	 * @return
	 */
	public List<ChiTieuTTChoVung> getChiTieu();
	public void setListChiTieu(String sql,String loaichitieu);
	public List<ChiTieuTTKhuVuc> getListKhuVuc();
	public void setListKhuVuc(List<ChiTieuTTKhuVuc> list);
	public void setVungID(String vungid);
	public String getVungID();
	public String getTenVungID();
	public String getDVKDID();
	public void setDvkdID(String dvkdId);
	public String getTenDVKD();
	public void setTenDvkd(String tendvkd);
	public void setTenVungID(String tenvungid);
	public boolean SaveChiTieu_Sec();
	public String getKenhID();
	public void setKenhID(String kenhid);
	public ResultSet getRsKenh();
	/*
	 * Chot chi tieu
	 */
	public boolean chotChitieu();
	public boolean chotChitieu_Sec();
	//Tinh trung binh doanh so cua khu vuc theo 3 thang gan nhat
	public void setTrungBinhThang(double trungbinhthang);
	public double getTrungBinhThang();	
}
