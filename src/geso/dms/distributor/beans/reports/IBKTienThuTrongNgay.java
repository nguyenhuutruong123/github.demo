package geso.dms.distributor.beans.reports;

import java.sql.ResultSet;

public interface IBKTienThuTrongNgay {

	public String getNgayKS();
	public String getNPPID();
	public void setUserId(String userId);
	public String getUserId();
	public void setTuNgay(String tuNgay);
	public String getTuNgay();
	public void setDenNgay(String denNgay);
	public String getDenNgay();
	public void init();
	public ResultSet getBKTienThuTrongNgay();
	public void DBclose();
}
