/*
 *  Name : IDonhangnpp
 * 	Modification Logs:
 *	DATE	AUTHOR	DESCRIPTION
 *	--------------------------------------------------------
 *	15-Oct-2011	KHOAND	IDonhangnpp Interface
 */
	package geso.dms.distributor.beans.donhangnhapp;
	import java.io.Serializable;
import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.List;
	public interface IDonhangnpp extends Serializable {
	
	public String getUserId();
	public void setUserId(String userId);
	public String getId(); //primary key 
	public void setId(String id);
	public String getNgayGiaoDich();
	public void setNgayGiaoDich(String ngaygiaodich);
	public void setTongTien(double tongtien);
	public double getTongTien();

	public String getNgaytao();
	public void setNgaytao(String ngaytao);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);

	
	public String getNguoitao();
	public void setNguoitao(String nguoitao);
	
	public String getNgaysua();
	public void setNgaysua(String ngaysua);	
	
	public String getNguoisua();
	public void setNguoisua(String nguoisua);
	
	public String getChietkhau();
	public void setChietkhau(String chietkhau);
	
	public String getVAT();
	public void setVAT(String vat);	
	
	public String getKho();
	public void setKho(String kho);	
	
	public String getNppId_Ban();
	public void setNppId_Ban(String npp_id_mua);
	
	public String getNppId_Mua();
	public void set_NppId_Mua(String NppId_Mua);

	public float getTongGiaTri();
	public void setTongGiaTri(float tonggiatri);
	
	public float  getDaThanhToan();
	public void setDaThanhToan(float dathanhtoan);
	
	public String getTenNPPMua();
	public void setTenNPPMua(String TenNPPMua);
	public String getTenNPPBan();
	public void setTenNPPBan(String TenNPPBan);

	public String getKenhBanHang();
	public void setKenhBanHang(String KenhBanHang);
	
	public boolean saveDonHangNPP();
	public boolean editDonHangNPP();
	public boolean DeleteDonHangNPP();
	public  void SetDonHangNPP(String sql);
	
	public ResultSet GetDonHangNPP();
	public void setListSanPham();
	public void setListSanPhamNew (List<ISanPhamDhNpp> list);//Co doi so
	public List<ISanPhamDhNpp> getSanPhamList();
	/**
	 * 
	 */
   	public void setSPThieuList(  Hashtable<String, Integer> spThieuList );
   	public Hashtable<String, Integer>  getSPThieuList();
   	public void setthongbao(String msg);
   	public String getthongbao();
   	/*
   	 * Phuong thuc lay danh sach cac don hang mua nha phan phoi cua nha phan phoi mua
   	 */
   	
   	public ResultSet getListNhaPPMua();//phuong thuc nay lay du lieu cho form http://localhost:8080/AHF/pages/Distributor/NhanHangMuaNhaPP.jsp
   	public void setListNhaPPMua(String sql);
   	public boolean ChotDonHangNPP_Mua();
   	public String getNgayNhanhang(); 	
   	public ResultSet GetRsNpp();
	
   	public void createRs();
   	
   	public void createRs_BenNhanHang();
   	
   	public ResultSet getrskho();
   	public ResultSet getrskenh();
   	public void DBclose();
   	
   	public String getIdKho_Nhan();//Phuong thuc get KHo Nhan hang cua nha phan phoi,kho nay co the se khac voi kho ben npp ban
   	public String getIdKenh_Nhan();
   	public void setIdKho_Nhan(String khoidnhan);
   	public void setIdKenh_Nhan(String kenhidnhan);
   	
   	public String GetTungay();
   	public String GetDenNgay();
   	public void setTuNgay(String tungay);
   	public void setDenNgay(String denngay);
   	//lấy danh sach nhà phân phối cho form bán hàng nhà phân phối.lấy tất cả các nhà phân phối trừ nhà phân phối ban
   	public void CreateRs_NhappBan();
   	public ResultSet GetRs_NhappBan();
   	public int luufile(String id,String name,String userId,int i);
   	public String displayfile(String id,String userId,int i);
   	
   	//Cập nhật trạng thái DH đã chốt để npp Mua thấy ĐH
   	public String ChotDh();
   	
}
