package geso.dms.center.beans.phieuthanhtoantt;

import java.sql.ResultSet;
import java.util.List;

public interface IPhieuThanhToanTT {

	public String getUserId();
	public void setUserId(String userId);
	
	public void setId(String id);
	public String getId();
	
	public String getMsg();
	public void setMsg(String msg);
	
	public String getNgayTao();
	public void setNgayTao(String ngaytao);
	
	public String getNgaySua();
	public void setNgaySua(String ngaysua);
	
	public String getNguoiSua();
	public void setNguoiSua(String nguoisua);
	
	public String getNguoiTao();
	public void setNguoiTao(String nguoitao);
	
	
	public String getTungay();
	public void setTungay(String tungay);
	
	public String getDenngay();
	public void setDenngay(String denngay);
	
	public void setSotien(double sotien);
	public double getSotien();
	
	public void setHinhthuc(String hinhthuc);
	public String getHinhthuc();
	
	public void setDiengiai(String diengiai);
	public String getDiengiai();
	
	public void setNgaythanhtoan(String ngaythanhtoan);
	public String getNgaythanhtoan();
	/**
	 * Nếu trạng thái =1 : đã chốt phiếu thanh toán
	 *                =0 : Chưa xử lý 
	 * @return
	 */
	public String getTrangThai();
	public void setTrangThai(String trangthai);
	
	public void setMessage(String message);
	public String getMessage();
	public boolean saveThanhToan();
	public boolean UpdateThanhToan();
	public boolean ChotThanhToan();
	public boolean DeleteThanhToan();
	public List<IPhieuThanhToanTT> getListThanhToan();
	public void setListThanhToan(String sql);
	
	public ResultSet getRs_vung();
	public ResultSet getRs_KhuVuc();
	public ResultSet getRs_NhaPhanPhoi();
	public void setListDonHang(List<IPhieuThanhToanTT_DH> listdonhang);
	public List<IPhieuThanhToanTT_DH> getListDonHang();
	public void setListDonHangInit();
	public void setIdNhaPhanPhoi(String idnhapp);
	public String getIdNhaPhanPhoi();
	/**
	 * Số tiền thanh toán của 1 đơn hàng sẽ được tính bằng công thức :
	 *  sotienavat(sau khi đã tính thuế)- tienhuongkm(tiền được hưởng khuyến mãi)- tienhuongtb(tiền hưởng trưng bày)
	 * Những đơn hàng sau khi trong tình trạng nợ là những đơn hàng sau khi đã xuất hóa đơn,trangthai của đơn hàng trong trường hợp này là 5
	 * Để tính được nợ của khách hàng sẽ được tính bằng tổng tiền các đơn hàng trong tình trạng phải thanh toán- tổng tiền đã
	 *  thanh toán của khách hàng cho các đơn hàng và các thanh toán này phải trong tình trạng đã chốt
	 * 
	 * @return
	 */
	public void setNoCu(double nocu);
	public double getNoCu();
	
	public void setNoCuInit();
	public void setKhuVucId(String khuvucid);
	public String getKhuVucId();

	
	
}
