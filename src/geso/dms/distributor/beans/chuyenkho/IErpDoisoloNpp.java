package geso.dms.distributor.beans.chuyenkho;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;

public interface IErpDoisoloNpp extends Serializable
{
	public String getNgaychuyen();
	public void setNgaychuyen(String ngaychuyen);
	
	public String getUserId();
	public void setUserId(String userId);
	public String getId();
	public void setId(String id);
	
	public ResultSet getNvBanhang();
	public void setNvBanhang(ResultSet nvbanhang);	
	public String getNvbhId();
	public void setNvbhId(String nvbhId);
	
	public ResultSet getKhoRs();
	public void setKhoRs(ResultSet khoRs);	
	public String getKhoId();
	public void setKhoId(String khoId);
	
	public ResultSet getSpRs();
	public void setSpRs(ResultSet spRs);	
	public String getSpIds();
	public void setSpIds(String spIds);
	
	public Hashtable<String, String> getSp_SoloOLD();
	public void setSSp_SoloOLD(Hashtable<String, String> sp_soloOLD);
	public Hashtable<String, String> getSp_Solo();
	public void setSSp_Solo(Hashtable<String, String> sp_solo);
	public Hashtable<String, Integer> getSp_Soluong();
	public void setSSp_Soluong(Hashtable<String, Integer> sp_sl);
	public Hashtable<String, String> getSp_Ngayhethan();
	public void setSSp_Ngayhethan(Hashtable<String, String> sp_ngayhethan);
	
	public Hashtable<String, String> getSp_ngayhethanOLD();
	public void setSp_ngayhethanOLD(Hashtable<String, String> sp_ngayhethanOLD);
	
	
	
	public ResultSet getSoloTheoSp(String spIds, String tongluong);
	
	public String getNppId();
	public void setNppId(String nppId);
	public String getNppTen();
	public void setNppTen(String nppTen);
	public String getSitecode();
	public void setSitecode(String sitecode);
	
	public boolean CreateCk( HttpServletRequest request );
	public boolean UpdateCk( HttpServletRequest request );
	public boolean ChotCk();
	public boolean DeleteCk();
	
	public void createRS();
	public void init();
	public void initDisplay();

	public String getMessage();
	public void setMessage(String msg);
	
	public void DBclose();
	
}
