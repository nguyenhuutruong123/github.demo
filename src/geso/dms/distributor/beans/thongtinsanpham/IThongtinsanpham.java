package geso.dms.distributor.beans.thongtinsanpham;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.Hashtable;

public interface IThongtinsanpham extends Serializable {
	public void init();
	public String getId();
	public void setId(String id);
	public String getUserId();
	public void setUserId(String userId);
	
	public String getNppId();

	public void setNppId(String nppId);

	public String getNppTen();
	
	public void setNppTen(String nppTen);
	
	public String getMasp();
	public void setMasp(String masp);
	
	public String getMaddt();
	public void setMaddt(String maddt);
	
	public String getTen();
	public void setTen(String ten);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public String getDvdlId();
	public void setDvdlId(String dvdlId);
	
	public String getDvdlTen();
	public void setDvdlTen(String dvdlTen);

	public ResultSet getDvdl();
	public void setDvkd(ResultSet dvkd);
	
	public void setDvkdId(String dvkdId);
	public String getDvkdId();
	
	public String getDvkdTen();
	public void setDvkdTen(String dvkdTen);

	public ResultSet getDvkd();
	
	public String getNhId();
	public void setNhId(String nhId);
	
	public String getNhTen();
	public void setNhTen(String nhTen);
	
	public String getClId();
	public void setClId(String clId);
	
	public String getClTen();
	public void setClTen(String clTen);
	
	public String getNgaytao();
	public void setNgaytao(String ngaytao);
	
	public String getNgaysua();
	public void setNgaysua(String ngaysua);
	
	public String getNguoitao();
	public void setNguoitao(String nguoitao);
	
	public String getNguoisua();
	public void setNguoisua(String nguoisua);
	
	public void setGiablc(String giablc);
	public String getGiablc();	
	
	public String getMessage();
	public void setMessage(String msg);
	
	public void setNh(ResultSet nh);
	public ResultSet getNh();
	
	public void setCl(ResultSet cl);
	public ResultSet getCl();
	
	public void setNsp(ResultSet nsp);
	public ResultSet getNsp();
	
	public void setNspSelected(ResultSet nsp);
	public ResultSet getNspSelected();
	
	public Hashtable<Integer, String> getNspIds();
	public void setNspIds(String[] nspIds);
	
	public String[] getSl1();

	public void setSl1(String[] sl1);

	public String[] getDvdl1();

	public void setDvdl1(String[] dvdl1);
	
	public String[] getSl2();

	public void setSl2(String[] sl2);

	public String[] getDvdl2();

	public void setDvdl2(String[] dvdl2);

	public void CreateRS();
	
	public ResultSet createDvdlRS()throws java.sql.SQLException;
	
	
	//them type
	public void setType(String type);
	public String getType();
	
	public ResultSet getSanphamRs();
	public void setSanphamRs(ResultSet spRs);
	public Hashtable<Integer, String> getSpIds();
	public void setSpIds(String[] spIds);
	
	public ResultSet getSanphamSelectedRs();
	public void setSanphamSelectedRs(ResultSet spRs);
	
	public void DBClose();
}