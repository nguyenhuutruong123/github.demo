package geso.dms.distributor.beans.report;

import geso.dms.distributor.db.sql.dbutils;

import java.sql.ResultSet;

public interface Ireport {

	public ResultSet getLogBaoCaoRs();
	public void init_don_hang_ban_trong_ky_queue(String baocaoId);
	public dbutils getDb() ;
	
	public String getSt();


	public void setSt(String st);

	public String getSt_loc();

	public void setSt_loc(String st_loc) ;

	public String getSt_v() ;

	public void setSt_v(String st_v) ;


	public void setuserId(String userId);
	public String getuserId();
	
	public void setuserTen(String userTen);
	public String getuserTen();
		
	public void setnppId(String nppId);
	public String getnppId();
	
	public void setnppTen(String nppTen);
	public String getnppTen();
	
	public void setkenhId(String kenhId);
	public String getkenhId();
	
	public void setnhomspct(String nhomspct);
	public String getnhomspct();
	
	public void setdvkdId(String dvkdId);
	public String getdvkdId();
	
	public void setnhanhangId(String nhanhangId);
	public String getnhanhangId();
	
	public void setchungloaiId(String chungloaiId);
	public String getchungloaiId();
	
	public void settungay(String tungay);
	public String gettungay();
	
	public void setdenngay(String denngay);
	public String getdenngay();
	
	public void setkhachhangId(String khachhangId);
	public String getkhachhangId();
	
	public void setkhachhang(ResultSet khachhang);
	public ResultSet getkhachhang();
	public void setMsg(String msg);
	public String getMsg();
	
	public void setkenh(ResultSet kenh);
	public ResultSet getkenh();
	
	public void setddkdId(String ddkdId);
	public String getddkdId();
	
	public void setddkd(ResultSet ddkd);
	public ResultSet getddkd();
	
	public void setvungId(String vungId);

	public String getvungId();

	public void setvung(ResultSet vung);

	public ResultSet getvung();

	public void setkhuvucId(String khuvucId);

	public String getkhuvucId();

	public void setkhuvuc(ResultSet khuvuc);

	public ResultSet getkhuvuc();
	
	public void setFieldShow(String[] fieldShow);
	public String[] getFieldShow();
	
	public void setFieldHidden(String[] fieldHidden);
	public String[] getFieldHidden();
	public String getDateTime() ;
	
	public void setngayton(String ngayton);
	public String getngayton();
	
	public void setNppRs(ResultSet nppRs);
	public ResultSet getNppRs();
	
	public void setLoaiNppRs(ResultSet nppRs);
	public ResultSet getLoaiNppRs();
	
	public void setLoaiNppId(String loainppId);
	public String getLoaiNppId();

	public void init();
	
	public String getView();
	public void setView(String view);
	public String getUrl();
	public void setUrl(String url);
	public String getKey();
	public void setKey(String key);
	
	public String getNhomskusId();

	public void setNhomskusId(String nhomskusId);

	public ResultSet getNhomskus();

	public void setNhomskus(ResultSet nhomskus);
	public void initPerformance();
}
