package geso.dms.distributor.beans.chuyentuyen;

import java.sql.ResultSet;

public interface IChuyenTuyen
{

	public String getNppId();

	public void setNppId(String nppId);

	public String getNppTen();

	public void setNppTen(String nppTen);

	public String getUserId();

	public void setUserId(String userId);

	public String getDddkdFromId();

	public void setDdkdFromId(String ddkdId);

	public String getDdkdToId();

	public void setDdkdToId(String ddkdId);

	public ResultSet getDkdFromRs();

	public void setDdkdFromRs(ResultSet ddkdRs);

	public ResultSet getDdkdToRs();

	public void setDdkdToRs(ResultSet ddkdRs);

	public ResultSet getTuyenFromRs();

	public void setTuyenFromRs(ResultSet tuyenRs);

	public ResultSet getTuyenToRs();

	public void setTuyenToRs(ResultSet tuyenRs);

	public String getTuyenFromId();

	public void setTuyenFromId(String tuyenId);

	public String getTuyenToId();

	public void setTuyenToId(String tuyenId);

	public String getMessage();

	public void setMessage(String msg);

	public ResultSet getKh_Tbh_DptList(); // khach hang duoc phan tuyen

	public void setKh_Tbh_DptList(ResultSet kh_tbh_dpt);
	
	public boolean MoveTbh(String[] khIds, String[] tansoIds, String[] sotts);
	public boolean MoveTbh_All();
	
	public String getType();
	public void setType(String type);
	
	//LUU LAI DANH SACH SO HOA DON THEO NHAN VIEN
	public String[] getNvId();
	public void setNvId(String[] nvId);
	public String[] getNvMa();
	public void setNvMa(String[] nvMa);
	public String[] getNvTen();
	public void setNvTen(String[] nvTen);
	public String[] getNvMauHD();
	public void setNvMauHD(String[] nvMauHD);
	public String[] getNvKyhieu();
	public void setNvKyhieu(String[] nvKyhieu);
	public String[] getNvSotu();
	public void setNvSotu(String[] nvSotu);
	public String[] getNvSoden();
	public void setNvSoden(String[] nvSoden);
	public String[] getNvNgayHD();
	public void setNvNgayHD(String[] nvngayHD);
	
	// CN HCM cho khai bao them mau hd 2
	public String[] getNvMauHD2();
	public void setNvMauHD2(String[] nvMauHD2);
	public String[] getNvKyhieu2();
	public void setNvKyhieu2(String[] nvKyhieu2);
	public String[] getNvSotu2();
	public void setNvSotu2(String[] nvSotu2);
	public String[] getNvSoden2();
	public void setNvSoden2(String[] nvSoden2);
	public String[] getNvNgayHD2();
	public void setNvNgayHD2(String[] nvngayHD2);
	
	public boolean saveSohoadon();
	
	public void createRs();
	public void initSohoadon();
	
	public String getNppFromId();
	public void setNppFromId(String value);
	
	public String getNppToId();
	public void setNppToId(String value);
	
	public ResultSet getNppRs();

}
