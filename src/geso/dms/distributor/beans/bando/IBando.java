package geso.dms.distributor.beans.bando;

import java.sql.ResultSet;

public interface IBando 
{
	public String getUserId();
	public void setUserId(String userId);
	
	public String getNppId();
	public void setNppId(String nppId);
	public String getNppTen();
	public void setNppTen(String nppTen);
	public String getSitecode();
	public void setSitecode(String sitecode);
	
	public String getKhachHangSi();
	public void setKhachHangSi(String kHSi);
	
	public String getdate();
	public void setDate(String date);
	public String getAddress();
	public void setAddress(String address);
	public String getTrungtam();
	public void setTrungtam(String trungtam);
	
	public String[] getLatcondition();
	public void setLatcondition(String[] latcondition);
	public String[] getLongconditon();
	public void setLongcondition(String[] longcondition);
	
	public String getDenngay();
	public void setDenngay(String denngay);
	
	public ResultSet getDdkdRs();
	public void setDdkdRs(ResultSet ddkdRs);
	public String getDdkdId();
	public void setDdkdId(String ddkdId);	
	
	public ResultSet getTbhRs();
	public void setTbhRs(ResultSet tbhRs);
	public String getTbhId();
	public void setTbhId(String tbhId);	
	
	public ResultSet getKhDaViengThamRs();
	public void setKhDaViengThamRs(ResultSet KhRs);
	
	public ResultSet getKhChuaViengThamRs();
	public void setKhChuaViengThamRs(ResultSet KhRs);
	
	public ResultSet getKhKhongDSTrongThang();
	public void setKhKhongDSTrongThang(ResultSet KhKoRs);
	public ResultSet getDSTbThang();
	public void getDSTbThang(ResultSet dstb3Thang);
	
	//Xem nganh hang
	public ResultSet getChungloaiRs();
	public void setChungloaiRs(ResultSet chungloaiRs);
	public String getChungloaiId();
	public void setChungloaiId(String clId);
	public ResultSet getDSTbNganhhang();
	public void getDSTbNganhhang(ResultSet dstb3Thang);
	
	public ResultSet getNppRs();
	public void setNppRs(ResultSet nppRs);
	public String getNppLocId();
	public void setNppLocId(String nppLocId);	
	
	public ResultSet getVungRs();
	public void setVungRs(ResultSet vungRs);
	public String getVungId();
	public void setVungId(String vungId);	
	
	public ResultSet getKhuvucRs();
	public void setKhuvucRs(ResultSet kvRs);
	public String getKhuvucId();
	public void setKhuvucId(String kvId);	
	
	public ResultSet getSanphamRs();
	public void setSanphamRs(ResultSet spRs);
	public String getSanphamId();
	public void setSanphamId(String spId);	
	
	public String getView();
	public void setView(String view);
	
	public void init();
	public void initBCDoPhu();
	public void initBCDoPhuTT();
	public void createRS();
	public void DBclose();
}