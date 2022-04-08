package geso.dms.center.beans.baocao;

import java.sql.ResultSet;

public interface IBaocao 
{
	public ResultSet getChungloaiRs();
	public void setChungloaiRs(ResultSet clRs);
	public String getChungloaiIds();
	public void setChungloaiIds(String loaispIds);
	public void Init_ReportDoichieu() ;
	public ResultSet getLoaiSanPhamRs();
	public void setLoaiSanPhamRs(ResultSet loaisp);
	public String getLoaiSanPhamIds();
	public void setLoaiSanPhamIds(String loaispIds);
	
	public String getUsername();
	public void setUsername(String Username);
	public String getpassword();
	public void setpassword(String password);
	public String getUrl();
	public void setUrl(String Url);
	
	public String getPk_seqUrl();
	public void setPk_seqUrl(String Pk_seqUrl);
	public ResultSet getUrlRs();
	public void setUrlRs(ResultSet UrlRs);
	
	public ResultSet getMaSanPhamRs();
	public void setMaSanPhamRs(ResultSet loaisp);
	public String getMaSanPhamIds();
	public void setMaSanPhamIds(String maspIds);
	public ResultSet getDvthRs();
	public void setDvthRs(ResultSet dvthRs) ;
	public String getNhamayId();
	public void setNhamayId(String  NhamayId);
	public ResultSet getNdnhapRs();
	public void setNdnhapRs(ResultSet ndnhapRs);
	public String getNdnhapIds();
	public void setNdnhapIds(String ndnhapIds);
	
	public ResultSet getNdxuatRs();
	public void setNdxuatRs(ResultSet ndxuatRs);
	public String getNdxuatIds();
	public void setNdxuatIds(String ndxuatIds);
	
	public ResultSet getSanPhamRs();
	public void setSanPhamRs(ResultSet sanpham);
	public String getSanPhamIds();
	public void setSanPhamIds(String spIds);
	
	public String getCheck_SpCoPhatSinh();
	public void setCheck_SpCoPhatSinh(String sp_cophatsinh);
	
	public void setNppId(String nppid);
	public String getNppId();
	
	public void setnpp(ResultSet npp);
	public ResultSet getnpp();
	
	public ResultSet getKhoRs();
	public void setKhoRs(ResultSet khoRs);
	public String getKhoIds();
	public void setKhoIds(String khoId);
	
	public String getKhoId_CXL();
	public void setKhoId_CXL(String khoId_CXL);
	public String getKhoTen();
	public void setKhoTen(String khoTen);
	
	public String getDvkdIds();
	public void setDvkdIds(String dvkdIds);

	public ResultSet getDvkdRs();
	public void setDvkdRs(ResultSet dvkdRs); 

	public String getUserId();
	public void setUserId(String userId);
	public String getUserTen();
	public void setUserTen(String userTen);
	public String getCongtyId();
	public void setCongtyId(String congtyId);
	
	public String getTuNgay();
	public void setTuNgay(String tungay);
	public String getDenNgay();
	public void setDenNgay(String denngay);
	
	public String getFlag();
	public void setFlag(String flag);
	
	public String getLayHangKho_CXL();
	public void setLayHangKho_CXL(String layhangkho_cxl);
	
	public String getHangchokiem();
	public void setHangchokiem(String chokiem);
	public String getPivot();
	public void setPivot(String pivot);
	
	public String getXemtheolo();
	public void setXemtheolo(String Xemtheolo);
	
	public String getMsg();
	public void setMsg(String msg);
	
	public ResultSet getRsNhamay();
	public void createRs();
	public void createRsBCKHO();
	public void createRsBCHSD();
	
	public void close();
	public String getHoPhanBo();
	public void setHoDaPhanBo(String hodaphanbo);
	public void set_view_chitiet(String view_chitiet);
	public String get_view_chitiet();
	
	public ResultSet getNccRs();
	public void setNccRs(ResultSet nccRs);
	public String getNccIds();
	public void setNccIds(String nccId);
	
	public void setLenhsanxuat(String lsxID);
	public String getLenhsanxuat();
	
	public ResultSet getListGia(String lenhsanxuatID);
	public String getLsxID() ;

	public void setLsxID(String lsxID);


	public String getSoluong();


	public void setSoluong(String soluong);
	

	public String getDvthId();
	public void setDvthId(String dvthId);
	public ResultSet getRsReportName();
	public void setRsReportName(ResultSet rs);
	public String getReportName();
	public void setReportName(String ReportName);
	public String getMaSp() ;


	public void setMaSp(String maSp);

	public String getTenSp() ;

	public void setTenSp(String tenSp);
	public String getMonth();
	public void setMonth(String month);
	public String getYear();
	public void setYear(String year);
	public void Init_ReportNoNCC();
	public ResultSet getLsxRs();
	public void setLsxRs(ResultSet lsx);
	
	public void createRsBC_GiaThanh();
	
	public String getLsxIds();
	public void setLsxIds(String lsxIds);
	
	public void createRsBC_CPSX();
	public void createRsBC_QuanTri();
	
	public ResultSet getKhonhanRs() ;
	public void setKhonhanRs(ResultSet khonhanRs);
	public String getKhonhanIds() ;
	public void setKhonhanIds(String khonhanIds) ;
	public void createRs_XUATKHO() ;
	public String getLoaibaocao() ;
	public void setLoaibaocao(String loaibaocao) ;
	
	
	public String getCodoituong();


	public void setCodoituong(String codoituong) ;


	public String getDoituongid() ;


	public void setDoituongid(String doituongid) ;


	public ResultSet getDoituongRs() ;


	public void setDoituongRs(ResultSet doituongRs);
	
	public String getLoaidoituong();


	public void setLoaidoituong(String loaidoituong) ;
}
