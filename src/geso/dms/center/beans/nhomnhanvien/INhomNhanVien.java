package geso.dms.center.beans.nhomnhanvien;

import java.sql.ResultSet;

public interface INhomNhanVien
{
	public String getId();
	public void setId(String id);

	public String getMa();
	public void setMa(String ma);

	public String getTen();
	public void setTen(String ten);

	public String getMsg();
	public void setMsg(String msg);

	public String getUserId();
	public void setUserId(String userId);
	
	public String getNhanHangId();
	public void setNhanHangId(String nhanhangId);
	
	public String getLoaiNv();
	public void setLoaiNv(String LoaiNv);
	
	public ResultSet getNhanhangRs();
	public void setNhanhangRs(ResultSet nhanhangRs);

	public String getNganhHangId();
	public void setNganhHangId(String nganhangId);
	
	public void setNganhHangRs(ResultSet nganhhangRs);
	public ResultSet getNganhHangRs();
	
	public String getDvdlId();
	public void setDvdlId(String dvdlId);
		
	public boolean save();
	
	public boolean edit();

	public void init();

	public void createRs();

	public String getSpId();
	public void setSpId(String spId);
	
	public ResultSet getSpRs();
	public void setSpRs(ResultSet spRs);
	public String getLoainvId() ;
	public void setLoainvId(String loainvId);
	public ResultSet getLoaiNV();
	public void setLoaiNV(ResultSet loaiNV);
	public void DBclose();
	public String getNvselected() ;
	public void setNvselected(String nvselected);
	public ResultSet getKenhbanhang() ;
	public void setKenhbanhang(ResultSet kenhbanhang);
	public String getKbhId();
	public void setKbhId(String kbhId) ;
}
