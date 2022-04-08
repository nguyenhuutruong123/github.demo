package geso.dms.distributor.beans.nhanviengiaonhan;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.Hashtable;

public interface INhanviengiaonhan extends Serializable
{
	public String getUserId();
	public void setUserId(String userId);
	public String getId();
	public void setId(String id);	
	public String getTennv();
	public void setTennv(String tennv);	
	public String getTrangthai();
	public void setTrangthai(String trangthai);	
	public String getNgaytao();
	public void setNgaytao(String ngaytao);	
	public String getNguoitao();
	public void setNguoitao(String nguoitao);	
	public String getNgaysua();
	public void setNgaysua(String ngaysua);
	public String getNguoisua();
	public void setNguoisua(String nguoisua);	
	public String getDiachi();
	public void setDiachi(String diachi);
	public String getDienthoai();
	public void setDienthoai(String dienthoai);
	public String getMessage();
	public void setMessage(String msg);
	
	public String getNppId();
	public void setNppId(String nppId);
	public String getNppTen();
	public void setNppTen(String nppTen);
	public String getSitecode();
	public void setSitecode(String sitecode);
	
	public ResultSet getDdkdList();
	public void setDdkdList(ResultSet ddkdlist);
	public String getDdkdId();
	public void setDdkdId(String ddkdId);
	
	public ResultSet getTuyenbanhang();
	public void setTuyenhang(ResultSet tuyenbanhang);	
	public String getTbhId();
	public void setTbhId(String ddkdId);
	public Hashtable<Integer, String> getTbhIds();
	public void setTbhIds(String[] tbhIds);
	
	public ResultSet getKhList();
	public void setKhList(ResultSet khlist);
	public Hashtable<Integer, String> getKhIds();
	public void setKhIds(String[] khIds);
	public ResultSet getKhSelectedList();
	public void setKhSelectedList(ResultSet khSelectlist);
	
	public void setDdkdIds(String[] ddkdIds);
	public Hashtable<Integer, String> getDdkdIds(); 
	
	public boolean CreateNvgn(String[] ddkdIds);
	public boolean UpdateNvgn(String[] ddkdIds);
	public void createRS();
	public void init();
	public void DBclose();
	
}
