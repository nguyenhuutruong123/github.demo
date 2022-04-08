package geso.dms.center.beans.phieuchuyenkhott;
import java.util.List;

public interface IPhieuChuyenKhoTT {
	public String getId();
	public void setId(String id);		
	public String getId_PhieuNhap();
	public void setId_PhieuNhap(String id_PhieuNhap);	
	public String getNgayChuyen();
	public void setNgayChuyen(String ngaychuyen);
	public String getTenKhoChuyen();
	public void setTenKhoChuyen(String TenKho);	
	public String getKhoID_Chuyen();
	public void setKhoId_Chuyen(String KhoId_chuyen);
	//kho nhan
	public String getTenKhoNhan();
	public void setTenKhoNhan(String TenKho_nhan);	
	public String getKhoID_Nhan();
	public void setKhoId_Nhan(String KhoId_nhan);
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
	public void setListChuyenKho(String sql_get);
	public List<IPhieuChuyenKhoTT> getListChuyenKho();
	public void setListSanPham(List<IPhieuChuyenKhoTT_SanPham> list);
	public List<IPhieuChuyenKhoTT_SanPham> getListSanPham();
	public boolean SavePhieuChuyenKhoTT();
	public boolean EditPhieuChuyenKhoTT();
	public boolean DeletePhieuChuyenKhoTT();
	public boolean ChotPhieuChuyenKhoTT();
	public void setListSanPhamById();
	
}
