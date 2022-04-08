package geso.dms.center.beans.khaibaockdathang;

import java.sql.ResultSet;
import java.util.Hashtable;

public interface IKhaibaoCKdathang {

	public String getUserId();
	public void setUserId(String userId);
	
	public String getId();
	public void setId(String id);
	
	public String[] getChietkhauSP();
	public void setChietkhauSP(String[] chietkhau);
	public String[] getChietkhauDh();
	public void setChietkhauDh(String[] chietkhau);
	public String[] getTumuc();
	public void setTumuc(String[] tumuc);
	public String[] getDenmuc();
	public void setDenmuc(String[] denmuc);
	public String[] getGhichu();
	public void setGhichu(String[] ghichu);
	
	public String getActiveTab();
	public void setActiveTab(String activeTab);
	
	public String getLoaiCK();
	public void setLoaiCK(String loaick);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public String getNgaytao();
	public void setNgaytao(String ngaytao);
	
	public String getNgaysua();
	public void setNgaysua(String ngaysua);
	
	public String getNguoitao();
	public void setNguoitao(String nguoitao);
	
	public String getNguoisua();
	public void setNguoisua(String nguoisua);	
	
	public String getTungay();
	public void setTungay(String tungay);	
	
	public String getDenngay();
	public void setDenngay(String denngay);	
	
	public String getScheme();
	public void setScheme(String scheme);
	
	public String getMessage();
	public void setMessage(String msg);
	
	public String getDiengiaiMucvuot();
	public void setDiengiaiMucvuot(String diengiaimucvuot);
	
	public String getMucvuot();
	public void setMucvuot(String mucvuot);
	
	public String getCKMucvuot();
	public void setCKMucvuot(String ckmucvuot);
	
	public ResultSet getNppRs();
	public void setNppRs(ResultSet nppRs);
	
	public String getNppIds();
	public void setNppIds(String nppIds);
	
	public ResultSet getSpRs();
	public void setSpRs(ResultSet spRs);
	
	public String getSpIds();
	public void setSpIds(String spIds);
	
	public ResultSet getKvRs();
	public void setKvRs(ResultSet kvRs);
	
	public String getKvIds();
	public void setKvIds(String kvIds);
	
	public ResultSet getVungRs();
	public void setVungRs(ResultSet vungRs);
	
	public String getVungIds();
	public void setVungIds(String vungIds);
	
	public ResultSet getNganhhangRs();
	public void setNganhhangRs(ResultSet nganhhangRs);
	
	public String getNganhhangIds();
	public void setNganhhangIds(String nganhhangIds);
	
	public ResultSet getNhanhangRs();
	public void setNhanhangRs(ResultSet nhanhangRs);
	
	public String getNhanhangIds();
	public void setNhanhangIds(String nhanhangIds);
	
	public ResultSet getNhomspRs();
	public void setNhomspRs(ResultSet nhomspRs);
	
	public String getNhomspIds();
	public void setNhomspIds(String nhomspIds);
	
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	
	public boolean CreateCkdh();
	public boolean UpdateCkdh();
	public void DBClose();
	public void createRs();
	public void createCkdh();
		
	public Hashtable<String, String> getChitieu();
	public void setChitieu(Hashtable<String, String> chitieu);
}
