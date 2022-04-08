package geso.dms.center.beans.bandott;

import java.sql.ResultSet;

public interface IBandott {
	public String getMa_tenkh();

	public void setMa_tenkh(String ma_tenkh);

	public ResultSet getMa_tenkhRs();

	public void setMa_tenkhRs(ResultSet ma_tenkhRs);

	public String getUserId();

	public void setUserId(String userId);

	public String getMafast();

	public void setMafast(String mafast);

	public String getdate();

	public void setDate(String date);

	public String getAddress();

	public void setAddress(String address);

	public String getTrungtam();

	public void setTrungtam(String trungtam);

	public String getDenngay();

	public void setDenngay(String denngay);

	public ResultSet getDdkdRs();

	public void setDdkdRs(ResultSet ddkdRs);

	public String getDdkdId();

	public void setDdkdId(String ddkdId);

	public ResultSet getTbhRs();

	public void setTbhRs(ResultSet tbhRs);

	public String getTbhId();

	public void setTbhId(String tbhId);

	public ResultSet getKhDaViengThamRs();

	public void setKhDaViengThamRs(ResultSet KhRs);

	public ResultSet getKhChuaViengThamRs();

	public void setKhChuaViengThamRs(ResultSet KhRs);

	public ResultSet getKhKhongDSTrongThang();

	public void setKhKhongDSTrongThang(ResultSet KhKoRs);

	public ResultSet getDSTbThang();

	public void getDSTbThang(ResultSet dstb3Thang);

	//
	public ResultSet getVungRs();

	public void setVungRs(ResultSet vungRs);

	public String getVungId();

	public void setVungId(String vungId);

	public ResultSet getKvRs();

	public void setKvRs(ResultSet kvRs);

	public String getkvId();

	public void setKvId(String kvId);

	public ResultSet getNppRs();

	public void setNppRs(ResultSet nppRs);

	public String getNppId();

	public void setNppId(String nppId);

	public ResultSet getCttbRs();

	public void setCttbRs(ResultSet cttbRs);

	public String getCttbId();

	public void setCttbId(String cttbId);

	public String getKhachHangSi();

	public void setKhachHangSi(String kHSi);

	public void init();

	public void initTB();

	public void initTB_Excel();

	public void initToaDo();

	public boolean xoaToaDoKh(String khId);

	public String getMsg();

	public void setMsg(String msg);

	public String[] getLatcondition();

	public void setLatcondition(String[] latcondition);

	public String[] getLongconditon();

	public void setLongcondition(String[] longcondition);

	// BO SUNG DEMO
	public ResultSet getInfoRs();

	public void setInfoRs(ResultSet infoRs);

	public ResultSet getDpNganhRs();

	public void setDpNganhRs(ResultSet dpRs);

	public ResultSet getInfoDetailRs();

	public void setInfoDetailRs(ResultSet infoDetailRs);

	public ResultSet getKiemtkRs();

	public void setKiemtkRs(ResultSet kiemtkRs);

	public void DBclose();

	public String getTtId();

	public void setTtId(String ttId);

	public ResultSet getTtRs();

	public void setTtRs(ResultSet ttRs);

	public String getView();

	public void setView(String View);

	public String getAction();

	public void setAction(String action);

	public void initLoTrinhOnline();

	public void initLoTrinhOnlineNPP();

	public ResultSet getNvRs();

	public void setNvRs(ResultSet nvList);

	public String getNvId();

	public void setNvId(String nvId);

	public ResultSet getDvkdRs();

	public void setDvkdRs(ResultSet DvkdRs);

	public String getDvkdId();

	public void setDvkdId(String DvkdId);

	public String getSoNVBH();

	public String getSoNVBHOnline();

	public boolean xoaanhkh(String khId);

	public String getIsTT();

	public void setIsTT(String isTT);

	public String getIsGSBH();

	public void setIsGSBH(String isGSBH);
}