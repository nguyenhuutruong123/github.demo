package geso.dms.distributor.beans.denghitratb;


import java.sql.ResultSet;
import java.util.Hashtable;
public interface IDenghitratb
{
	//Cac thuoc tinh 
	public String getUserId();
	public void setUserId(String userId);
	public String getId();
	public void setId(String id);
	
	public ResultSet getCttrungbay();
	public void setCttrungbay(ResultSet cttrungbay);	
	public String getCttbId();
	public void setCttbId(String cttbId);
	public String getCttbTen();
	public void setCttbTen(String cttbTen);
	
	public ResultSet getNvBanhang();
	public void setNvBanhang(ResultSet nvbanhang);	
	public Hashtable<Integer, String> getNvbhIds();
	public void setNvbhIds(String[] nvbhIds);
	
	public ResultSet getKhList();
	public void setKhList(ResultSet khlist);
	public ResultSet getKhSelectedList();
	public void setKhSelectedList(ResultSet khSelectedlist);

	public String getTrangthai();
	public void setTrangthai(String trangthai);
	public String getThoigianbd();
	public void setThoigianbd(String tgBatdau);
	public String getThoigiankt();
	public void setThoigiankt(String tgKetthuc);
	
	public String getTgbdTinhds();
	public void setTgbdTinhds(String tgbdTinhds);
	public String getTgktTinhds();
	public void setTgktTinhds(String tgktTinhds);
	
	public String getSolantt();
	public void setSolantt(String solantt);
	public int getType();
	public void setType(int type);
	
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
	
	public String getNppId();
	public void setNppId(String nppId);
	public String getNppTen();
	public void setNppTen(String nppTen);
	public String getSitecode();
	public void setSitecode(String sitecode);
		
	public boolean CreateDnttb(String[] khIds, String[] ddkdIds, String[] soxuat, String[] dangky);
	public boolean UpdateDnttb(String[] khIds, String[] ddkdIds, String[] soxuat, String[] dangky);
	public void createRS();
	public void createKhRs();
	public void init();
	public void DBclose();
	
	public void setdiengiai(String diengiai);
	public String getdiengiai();
	public void setscheme(String scheme);
	public String getcheme();
	
	public void setSosuatphanbo(String sosuatphanbo);
	public String getSosuatphanbo();
	
	public void setSosuatdaphanbo(String sosuatdaphanbo);
	public String getSosuatdaphanbo();
}



