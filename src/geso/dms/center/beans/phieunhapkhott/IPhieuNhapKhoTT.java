package geso.dms.center.beans.phieunhapkhott;

import geso.dms.center.beans.phieunhapkhott.imp.PhieuNhapKhoTT;
import geso.dms.center.beans.phieunhapkhott.imp.PhieuNhapKhoTT_SanPham;

import java.util.List;

public interface IPhieuNhapKhoTT {
	public String getId();
	public void setId(String id);		
	public String getNgayNhap();
	public void setNgayNhap(String ngaynhap);
	public String getTenKho();
	public void setTenKho(String TenKho);	
	public String getKhoID();
	public void setKhoId(String KhoId);	
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
	public void setMessage(String message);
	public String getMessage();
	public void setListNhapKho(String sql_get);
	public List<PhieuNhapKhoTT> getListNhapKho();
	public void setListSanPham(List<PhieuNhapKhoTT_SanPham> list);
	public void setListSanPhamById();
	public List<PhieuNhapKhoTT_SanPham> getListSanPham();
	public boolean SavePhieuNhapKhoTT();
	public boolean EditPhieuNhapKhoTT();
	public boolean DeletePhieuNhapKhoTT();
	public boolean ChotPhieuNhapKhoTT();
}
