package geso.dms.center.beans.chart;

import java.sql.ResultSet;
import java.util.Hashtable;

public interface IChart 
{
	public String getUserId();
	public void setUserId(String userId);
	public String getUserTen();
	public void setUserTen(String userTen);
	
	public String getThang();
	public void setThang(String thang);
	public String getNam();
	public void setNam(String nam);
	
	public String getTungay();
	public void setTungay(String tungay);
	public String getDenngay();
	public void setDenngay(String denngay);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public String getMuclay();
	public void setMuclay(String muclay);
	
	public String getMsg();
	public void setMsg(String msg);
	
	public Hashtable<String, Long> getData();
	public void setData(Hashtable<String, Long> data);
	
	public Hashtable<String, Long> getNVHoatDong();
	public void setNVHoatDong(Hashtable<String, Long> data);
	public Hashtable<String, Long> getNVNgungHoatDong();
	public void setNVNgungHoatDong(Hashtable<String, Long> data);
	
	public ResultSet getDataChart();
	public void setDataChart(ResultSet data);
	
	public void init(String query);
	
	public void initChartTK();
	
	public void initChartNV();
	
	public void initChartCT();
	public void initChartCT_Thang();
	public ResultSet GetRsDSSecYeer();
	public ResultSet GetRsDSPriYeer();
	public ResultSet GetRsChiTieu();
	public ResultSet GetRsDSSKU();
	public String[] getmangthang();
	public String[] getmangsku();
	public String[] getmangTensku();
	public String[] getmangTenKhuvuc();
	public String[] getmangIDKhuvuc();
	public ResultSet getRsTonKhoKhuVuc();
	
	public void DbClose();
}
