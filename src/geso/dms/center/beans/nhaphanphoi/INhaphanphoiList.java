package geso.dms.center.beans.nhaphanphoi;

import geso.dms.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;




import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface INhaphanphoiList extends Serializable, IPhan_Trang
{	
	HttpServletRequest getRequestObj();
	void setRequestObj(HttpServletRequest request);
	
	public String getUserId();
	public void setUserId(String userId);
	public String getTen();
	public void setTen(String ten);
	public String getSodienthoai();
	public void setSodienthoai(String sodienthoai);
	public String getTrangthai();
	public void setTrangthai(String trangthai);
		
	public ResultSet getKhuvuc();
	public void setKhuvuc(ResultSet khuvuc);
	
	public void createLoaiNppRs();
	public ResultSet getLoaiNPP();
	public void setLoaiNPP(ResultSet loainpp);
	
	public String getLoaiNppId();
	public void setLoaiNppId(String loainppId); 
	
	public String getNccId();
	public void setNccId(String nccId);
	public String getKvId();
	public void setKvId(String kvId);
	public String getDvkdId();
	public void setDvkdId(String dvkdId);
	
    public void setMsg(String Msg);
    public String getMsg();
    
	public ResultSet getNppList(); 

	public void setNppList(ResultSet npplist); 

	public void init(String search);
	public void DBclose();
	public void setMaSoThue(String masothue);
	public String getMaSoThue();
	//Dia chi giao hang
	public void setDiaChi(String diachi);
	public String getDiaChi();
	public void setMucChietKhau(double mucchietkhau);
	public double getMucChietKhau();
	public void setDiaChiXuatHD(String diachixhd);
	public String getDiaChiXuatHD();
	public List<Object> getDataSearch() ;
	public void setDataSearch(List<Object> dataSearch);
}
