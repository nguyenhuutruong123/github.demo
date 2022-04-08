package geso.dms.center.beans.banggiablc;

import java.io.Serializable;
import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;

public interface IBanggiablc extends Serializable
{
	
	public String getDisplay();
	public void setDisplay(String display);

	public String[] getCheckban() ;

	public void setCheckban(String[] checkban) ;
	//Cac thuoc tinh 
	public String getUserId();
	public void setUserId(String userId);
	public String getId();
	public void setId(String id);
	public String getTen();
	public void setTen(String ten);
	public String getDvkd();
	public void setDvkd(String dvkd);
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
	public ResultSet getDvkdIds();
	public void setDvkdIds(ResultSet dvkdIds);
	public String[] getGiablc();
	public void setGiablc(String[] giablc);
	
	public String[] getQuycach();
	public void setQuycach(String[] quycach);
	
	public String getDonViDoLuong();
	public void setDonViDoLuong(String donvi);
	
	public ResultSet getSanPhamList();
	public void setSanPhamList(ResultSet sanphamlist);
	public void setTrangthai(String trangthai);
	public String getTrangthai(); 
	public boolean CreateBgblc(HttpServletRequest request);
	public boolean UpdateBgblc(HttpServletRequest request);
	public void createRS();
	public void init();
	public ResultSet getNewSanPhamList(); 
	public void setNewSanPhamList(ResultSet newsplist); 
	public void DbClose();
	public String getTungay() ;

	public void setTungay(String tungay);

	
	public String[] getSpIdArr();
	public void setSpIdArr(String[] spIdArr);
	public String[] getSpMaArr() ;
	public void setSpMaArr(String[] spMaArr);
	public String[] getSpTenArr();
	public void setSpTenArr(String[] spTenArr);
	public String[] getDongiaArr();
	public void setDongiaArr(String[] dongiaArr);
	public String[] getDonviArr();
	public void setDonviArr(String[] donviArr);
	public String getKbhId() ;
	public void setKbhId(String kbhId);
	public ResultSet getKbhRs();
	
	public ResultSet getLoaiKhRs();
	public void setLoaiKhRs(ResultSet loaiKhRs);
	public void createLkhRs();
	public String getLoaiKhIds();
	public void setLoaiKhIds(String loaiKhIds);
	public String[] getCheckluonhien();
	public void setCheckluonhien(String[] checkluonhien) ;

}
