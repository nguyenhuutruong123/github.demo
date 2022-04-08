package geso.dms.center.beans.thuongdauthung;

import java.sql.ResultSet;
import java.util.List;

public interface IThuongdauthung {

	public List<Object> getDataSearch();
	public void setDataSearch(List<Object> dataSearch);

	public String getDisplay();
	public void setDisplay(String display);
	
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


	public void setNgayTao(String ngaytao);
	public String getNgayTao();

	public void setNgaySua(String nguoisua);
	public String getNgaySua();

	public void setUserId(String userid);
	public String getUserId();

	public void setDienGiai(String diengiai);
	public String getDienGiai();


	public void setTrangThai(String trangthai);
	public String getTrangThai();


	public void closeDB();

	public void setLuongkhacRs(String luongkhacRs) ;
	public ResultSet getLuongkhacRs() ;

	public boolean CreateLuongKhac();
	public boolean UpdateLuongKhac();


	public boolean chotLuongKhac();
	public boolean UnchotLuongKhac();
	public boolean DeleteLuongKhac();

	//this.soluong = 0;
	public double getSoluong();
	public void setSoluong(double soluong) ;
	//this.isThung = "0";
	public String getLoaict();
	public void setLoaict(String isThung) ;
	//this.thuongSR = 0;
	public double getThuongSR() ;
	public void setThuongSR(double thuongSR) ;
	//this.thuongSS = 0;
	public double getThuongSS() ;
	public void setThuongSS(double thuongSS) ;
	//this.thuongASM = 0;
	public double getThuongASM() ;
	public void setThuongASM(double thuongASM);
	
	
	public String getTungay() ;
	public void setTungay(String tungay) ;
	public String getDenngay();
	public void setDenngay(String denngay) ;

	
	public String getinfoDETAILASM() ;
	public void setinfoDETAILASM(String infoDETAILASM) ;
	public String getinfoDETAILSR() ;
	public void setinfoDETAILSR(String infoDETAILSR) ;
	public String getinfoDETAILSS() ;
	public void setinfoDETAILSS(String infoDETAILSS) ;
	
	public void setNsp_fk(String nsp_fk) ;
	public String getNsp_fk() ;
	public ResultSet getNspRs() ;
	public ResultSet getNspttRs() ;
	
	
	public void setNsptt_fk(String nsptt_fk) ;
	public String getNsptt_fk() ;
	
	public void setNkh_fk(String nkh_fk) ;
	public String getNkh_fk() ;
	public ResultSet getNkhRs() ;
	
	public void setdstoithieu(double dstoithieu) ;
	public double getdstoithieu() ;
	
	
	public List<ISanpham> getSpList();
	public void setSpList(List<ISanpham> spList);
	
	public String[] getSanpham();
	public void setSanpham(String[] sanpham);
	
	
	public void createSpList();
	
	public ResultSet getDataRs();
	public void setDataRs(ResultSet dataRs);
	
	public String getView();
	public void setView(String view);
	public void DbClose();
}
