package geso.dms.center.beans.thongbao;

import java.sql.ResultSet;

public interface IThongbao 
{
	public String getId();
	public void setId(String id);
	
	public String getUserId();
	public void setUserId(String userId);
	
	public String getTieude();
	public void setTieude(String tieude);
	
	public String[] getFile();
	public void setFile(String file);
	
	public String getKhuvuc();
	public void setKhuvuc(String khvuc);
	
	public String getVung();
	public void setVung(String vung);
	
	public String getNoidung();
	public void setNoidung(String noidung);
	
	public String getNgaybatdau();
	public void setNgaybatdau(String ngaybatdau);
	
	public String getNgayketthuc();
	public void setNgayketthuc(String ngayketthuc);
	
	public String getSoluong();
	public void setSoluong(String soluong);
	
	public ResultSet getThongbaonhanvienList();
	public ResultSet getNppdangnhapList();
	public void setThongbaonhanvienList(ResultSet thongbaonhanvien);
	public String getNhanvienIds();
	public void setNhanvienIds(String nhanvien);

	public String getnppIds();
	public void setnppIds(String nppIds);
	
	public ResultSet getKhuvucList();
	public void setKhuvucList(ResultSet khuvucrs);
	
	public ResultSet getVungList();
	public void setVungList(ResultSet vungrs);
	
	public void createRs();
	public void init();
	public void initDisplay();
	
	public void initThaoLuan();
	public void initThaoLuanTT();

	public boolean createTb();
	public boolean updateTb();
	public boolean guitraloiTb();
	public boolean guitraloiTbTT();
	
	public void setMsg(String Msg);
	public String getMsg();
	
	public void setLoaithongbao(String loaitb);
	public String getLoaithongbao();
	public void setHethieuluc(String hethieuluc);
	public String getHethieuluc();
	
	public void setChiNhanh(String chinhanh);
	public String getChiNhanh();
	
	public void setDoiTac(String doitac);
	public String getDoiTac();
	
	public void setVbhdId(String vbhdId);
	public String getVbhdId();
	public ResultSet getVbhdRs();
	public void setVbhdRs(ResultSet vbhdRs);
	
	public void setVbccId(String vbccId);
	public String getVbcId();
	public ResultSet getVbccRs();
	public void setVbccRs(ResultSet vccRs);
	
	public void setVbttId(String vbttId);
	public String getVbttId();
	public ResultSet getVbttRs();
	public void setVbttRs(ResultSet vbttRs);
	
	public void setVbsdbsId(String vbttId);
	public String getVbsdbsId();
	public ResultSet getVbsdbsRs();
	public void setVbsdbsRs(ResultSet vbsdbsRs);
	
	public String getViewMode();
	public void setViewMode(String viewMode);
	
	public String getHienCanCu();
	public void setHienCanCu(String cancu);
	public String getHienHuongDan();
	public void setHienHuongDan(String huongdan);
	public String getThayThe();
	public void setThayThe(String thaythe);
	public String getSuaDoiBoSung();
	public void setSuaDoiBoSung(String suadoiBS);
	
	public String getDuocCanCuTu();
	public void setDuocCanCuTu(String duoccancuTu);
	public String getDuocHuongDanTu();
	public void setDuocHuongDanTu(String duochuongdanTu);
	public String getDuocSuaDoiTu();
	public void setDuocSuaDoiTu(String duocsuadoiTu);
	public String getDuocThayTheTu();
	public void setDuocThayTheTu(String duocthaytheTu);
	
	public String getNoidungtraloi();
	public void setNoidungtraloi(String noidungTL);
	public ResultSet getThaoluan();
	public void setThaoluan(ResultSet thaoluan);
	
	public ResultSet getNguoinhanTLRs();
	public void setNguoinhanTLRs(ResultSet nguoinhanTLrs);
	public String getNguoinhanTLId();
	public void setNguoinhanTLId(String nguoinhanId);
	
	public ResultSet getDdkdRs();
	public void setDdkdRs(ResultSet ddkdRs);
	public String getDdkdId();
	public void setDdkdId(String ddkdId);
	
	public ResultSet getNhomNvRs();
	public void setNhomNvRs(ResultSet NhomNvRs);
	public String getNhomNvId();
	public void setNhomNvId(String NhomNvId);
	
	public void DBClose();
	
	public String getLoaiNv();
	public void setLoaiNv(String loaiNv);
	
	public String getTtId();
	public void setTtId(String ttId);
	
	public ResultSet getTtRs();
	public void setTtRs(ResultSet ttRs);
	
	
}
