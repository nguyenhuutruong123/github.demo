package geso.dms.center.beans.report;

import geso.dms.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IBcHoaDon extends IPhan_Trang, Serializable
{
	public String getTuNgay();
	public void setTuNgay(String denngay);
	
	public String getDenNgay();
	public void setDenNgay(String denngay);
	
	public String getNppId();
	public void setNppId(String nppId);
	
	public ResultSet getNppRs();
	public void setNppRs(ResultSet nppRs);
	
	public String getUserId();
	public void setUserId(String userId);
	
	public String getSpId();
	public void setSpId(String spId);
	
	public String getDdkdId();
	public void setDdkdId(String ddkdId);
	
	public String getKhId();
	public void setKhId(String khId);
	
	public ResultSet getSpRs();
	public void setSpRs(ResultSet spRs);
	
	public ResultSet getNhaphangRs();
	public void setNhaphangRs(ResultSet khRs);
	
	public ResultSet getDdkdRs();
	public void setDdkdRs(ResultSet ddkdRs);
	
	public ResultSet getHoadonRs();
	public void setHoadonRs(ResultSet hdRs);
	
	public String getView();
	public void setView(String view);
	
	public void closeDB();

	public void createRs();
	
	public String getMsg();
	public void setMsg(String msg);
	
	public String getKbhId();
	public void setKbhId(String kbhId);
	
	public ResultSet getKbhRs();
	public void setKbhRs(ResultSet kbhRs);	

	public void init(String query, String order);
	public void initDisplay();
	
	public String getQueryHd();
	public void setQueryHd(String query);
	
	public void setTotal_Query(String searchquery);
	public ResultSet getTotalRs();
	public void setTotalRs(ResultSet totalRs);
	
	public String getVungId();
	public void setVungId(String vungId);
	
	public ResultSet getVungRs();
	public void setVungRs(ResultSet vungRs);
	
	public String getKvId();
	public void setKvId(String kvId);
	
	public ResultSet getKvRs();
	public void setKvRs(ResultSet kvRs);
	
	public String getTtId();
	public void setTtId(String ttId);
	
	public ResultSet getTtRs();
	public void setTtRs(ResultSet ttRs);
	
	public String getNhomId();
	public void setNhomId(String nhomId);
	
	public ResultSet getNhomRs();
	public void setNhomRs(ResultSet nhomRs);
	
	public String getLoaiHoaDon();
	public void setLoaiHoaDon(String loaiHoaDon);
	
	public String get_Action();
	public void set_Action(String action);
	
	public String getSoId();
	public void setSoId(String soId);

	public String getSohoadon();
	public void setSohoadon(String sohoadon);
	
	public String getActiveTab();
	public void setActiveTab(String active);
	 
	public String getInvoiceId();
	public void setInvoiceId(String InvoiceId);
	
	public String getInvoice_RevertFor ();
	public void setInvoice_RevertFor (String Invoice_RevertFor );
	
	public String getLegalNumber();
	public void setLegalNumber(String LegalNumber);
	
	public String getCustomerId();
	public void setCustomerId(String CustomerId);
	
	public String getCustomerName();
	public void setCustomerName(String CustomerName);
	
	public String getInVoiceDate();
	public void setInVoiceDate(String InVoiceDate);
	
	public String getInvoiceType();
	public void setInvoiceType(String InvoiceType);
	
	public String getSO_Number();
	public void setSO_Number(String SO_Number);
	
	public String getPO_Number ();
	public void setPO_Number (String PO_Number );
	
	public String getStatus ();
	public void setStatus (String Status );
	
	public String Save();
	public String TaoDonHang();
	public String XoaPXK();
	
	public String getPxkXoa();
	public void setPxkXoa(String pxkxoa);
}