package geso.dms.center.beans.Router;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public interface IDRouter {
   
	public void setkhuvucId(String khuvucId);
	public void setnppId(String nppId);
	public String getkhuvucId();
	public String getnppId();
	public void setddkdId(String ddkdId);
	public String getddkdId();
	public ResultSet getkhuvuc();
	public ResultSet getnpp();
	public ResultSet getddkd();
	public void setKenhId(String kenhId);
	public String getkenhId();
	public void settuyenId(String tuyenId);
	public String gettuyenId();
	public ResultSet getdanhsach();
	public ResultSet getTuyen();
	public ResultSet getKenh();
	public void init();
	public void DBclose();
	public void createNPP();
	public void setStatus(String status);
	public String getStatus();
	public String getTungay();

	public void setTungay(String tungay);

	public String getDenngay() ;

	public void setDenngay(String denngay);
	public void setvungId(String vungId);
	public String getvungId();
	public ResultSet getvung();
	
	public void setMessage(String msg);
	public String getMessage();
	
	public void setuserId(String userid);
	public String getuserId();
	
	public void setId(String id);
	public String getId();
	
	public ResultSet getCapnhatKHRs();
	public void setCapnhatKHRs(ResultSet cnkhRs);		
	
	public void createCapnhatKHList();
	
	public ResultSet getCnkhList();
	public void setCnkhList(ResultSet cnkhlist);
	
	public List<IDRouter> getcnkhValue();
	public void setcnkhValue(List<IDRouter> cnkhvalue);
	
	public boolean CreateCnkh(List<IDRouter> valuelist);
	public boolean UpdateCnkh(List<IDRouter> valuelist);

	public void initDisplay();
	
	public String getNgaytao();
	public void setNgaytao(String ngaytao);
		
	public String getNgaysua();
	public void setNgaysua(String ngaysua);
	
	public String getNguoitao();
	public void setNguoitao(String nguoitao);
	
	public String getNguoisua();
	public void setNguoisua(String nguoisua);
	
	public String getmaKH();
	public void setmaKH(String makh);
	
	public String getSanpham();
	public void setSanpham(String sp);
	
	public String getSoluong();
	public void setSoluong(String sl);
	
	public String getDiengiai();
	public void setDiengiai(String dg);
	
	public String getView();
	public void setView(String view);
}
