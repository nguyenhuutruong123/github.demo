package geso.dms.center.beans.tieuchithuong;
import java.sql.ResultSet;
import java.util.List;

public interface ITieuchithuongSKU 
{
	public String getUserId();
	public void setUserId(String userId);
	public String getId();
	public void setId(String id);
	public String getScheme();
	public void setScheme(String scheme);
	
	public String getThang();
	public void setThang(String thang);
	public String getNam();
	public void setNam(String nam);
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	
	public String getPhaidat();
	public void setPhaidat(String phaidat);
	
	public String[] getdaidienkinhdoanhimport();
	public void setdaidienkinhdoanhimport(String[] daidienkinhdoanhimport);
	
	public String[] getPhaidatimport();
	public void setPhaidatimport(String[] Phaidatimport);
	
	public String getThuong();
	public void setThuong(String thuong);
	public String getThuongtoida();
	public void setThuongtoida(String thuongtoida);
	
	public String getThuongGS();
	public void setThuongGS(String thuong);
	public String getThuongtoidaGS();
	public void setThuongtoidaGS(String thuongtoida);
	
	public List<INhomsp> getNhomspList();
	public void setNhomspList(List<INhomsp> nspList);
	
	public String getIsthung();
	public void setIsthung(String isthung);
	public String getMsg();
	public void setMsg(String msg);
	
	public void setNhomspRs(ResultSet nspRs);
    public ResultSet getNhomspRs();
	
	public void init();
	public void initUpdate();
	public boolean createTctSKU(String[] nspId, String[] nspTongluong, String[] nspTrongso, String[] nspTumuc, String[] nspDenmuc, String[] nspTSR, String[] nspTTDSR, String[] nspTSS, String[] nspTTDSS, String[] nspTASM, String[] nspTTDASM);
	public boolean updateTctSKU(String[] nspId, String[] nspTongluong, String[] nspTrongso, String[] nspTumuc, String[] nspDenmuc, String[] nspTSR, String[] nspTTDSR, String[] nspTSS, String[] nspTTDSS, String[] nspTASM, String[] nspTTDASM);
	
	///////////////////////////
}
