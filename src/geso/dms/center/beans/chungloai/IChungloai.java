package geso.dms.center.beans.chungloai;

import geso.dms.center.beans.chungloai.IChungloai;
import java.io.Serializable;
import java.util.Hashtable;
import java.sql.ResultSet;

public interface IChungloai extends Serializable {
	
	public String getId();

	public void setId(String id);
	
	public String getChungloai();
	
	public void setChungloai(String chungloai);
	
	public String  getNhanhang();
	
	public void  setNhanhang(String nhanhang);
	
	public String getTrangthai();
	
	public void setTrangthai(String trangthai);
	
	public String getNgaytao();
	
	public void setNgaytao(String ngaytao);
	
	public String getNgaysua();
	
	public void setNgaysua(String ngaysua);
	
	public String getNguoitao();
	
	public void setNguoitao(String nguoitao);
	
	public String getMa();
	
	public void setMa(String Ma);
	
	public String getNguoisua();
	
	public void setNguoisua(String nguoisua);

	public ResultSet  getNhList();
	
	public void setNhList(ResultSet nhlist);	
	
	public ResultSet  getNh_clList();
	
	public void setNh_clList(ResultSet nh_cllist);	

	public String getMessage();
	
	public void setMessage(String msg);
	
	public String  getNhanhangId();
	
	public void setNhanhangId(String nhanhangId);
	
	public void setNhanhangSelected(String[] nhanhangSelected);
	
	public Hashtable<Integer, String>  getNhanhangSelected();

	public boolean saveNewChungloai();
	
	public boolean UpdateChungloai();
	
	public void createNhList();
   
	public void DBClose();
}