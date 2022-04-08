package geso.dms.center.beans.khachhang;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.Hashtable;

public interface IKhachhang extends Serializable
{
	//Cac thuoc tinh 
	public String getUserId();
	public void setUserId(String userId);
	public String getId();
	public void setId(String id);

	public String getTen();
	public void setTen(String ten);
	public String getDiachi();
	public void setDiachi(String diachi);
	public String getTpId(); 
	public void setTpId(String tpId); 
	public String getQhId(); 
	public void setQhId(String qhId); 
	
	public String getEmail(); 
	public void setEmail(String Email); 
	
	public String getTpTen(); 
	public void setTpTen(String tpTen); 
	public String getQhTen(); 
	public void setQhTen(String qhTen); 	
	
	public String getSodienthoai();
	public void setSodienthoai(String sodienthoai);	
	public String getMasothue();
	public void setMasothue(String masothue);	
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
	public String getMessage();
	public void setMessage(String msg);
	public String getDientichch();
	public void setDientichch(String Dientichch);
	
	public String getMaDDT();
	public void setMaDDT(String maddt);
	public String getNguoidaidien();
	public void setNguoidaidien(String nguoidaidien);
	public String getDcgiaohang();
	public void setDcgiaohang(String dcgiaohang);
	public String getDcgiaohang2();
	public void setDcgiaohang2(String dcgiaohang2);
	public String getDcxuathd();
	public void setDcxuathd(String dcxuathd);
	public String getKhodathang();
	public void setKhodathang(String khodathang);
	public String getKvId();
	public void setKvId(String kvId);
	public String getTansuatdh();
	public void setTansuatdh(String tansuatdh);
	public String getSongayno();
	public void setSongayno(String songayno);
	public String getSotienno();
	public void setSotienno(String sotienno);
	public ResultSet getKhuvucList();
	public void setKhuvucList(ResultSet kvrs);
	public ResultSet getKhoRs();
	public void setKhoRs(ResultSet khors);
	
	public String getIsMT();
	public void setIsMT(String isMT);
	
	public String getHch();
	public void setHch(String hch);
	public String getKbh();
	public void setKbh(String kbh);
	public String getBgst();
	public void setBgst(String bgst);
	public String getVtch();
	public void setVtch(String vtch);
	public String getLch();
	public void setLch(String lch);
	public String getNch();
	public void setNch(String nch);
	
	public String getMck();
	public void setMck(String mck);
	public String getGhcn();
	public void setGhcn(String ghcn);
	
	public String getChucuahieu();
	public void setChucuahieu(String Chucuahieu);
	
	public String getPhuongxa();
	public void setPhuongxa(String Phuongxa);
	
	public String getNppId();
	public void setNppId(String nppId);
	public String getNppTen();
	public void setNppTen(String nppTen);
	public String getSitecode();
	public void setSitecode(String sitecode);
	public ResultSet getPhuongxaRS();
	//Cac phuong thuc Get, Set tra ve ResultSet tuong ung
	public ResultSet getTp(); 
	public void setTp(ResultSet tp); 
	public ResultSet getQh(); 
	public void setQh(ResultSet qh); 	
	public ResultSet getHangcuahang();
	public void setHangcuahang(ResultSet hangcuahang);	
	public ResultSet getKenhbanhang();
	public void setKenhbanhang(ResultSet kenhbanhang);
	public ResultSet getBgsieuthi();
	public void setBgsieuthi(ResultSet bgsieuthi);
	public ResultSet getVtcuahang();
	public void setVtcuahang(ResultSet vtcuahang);
	public ResultSet getLoaicuahang();
	public void setLoaicuahang(ResultSet loaicuahang);
	public ResultSet getNhomcuahang();
	public void setNhomcuahang(ResultSet nhomcuahang);
	public ResultSet getMucchietkhau();
	public void setMucchietkhau(ResultSet mucchietkhau);
	public ResultSet getGhcongno();
	public void setGhcongno(ResultSet ghcongno);
	public ResultSet getBangGiaST();
	
	public ResultSet getNhaphanphoi();
	public void setNhaphanphoi(ResultSet nhaphanphoi);
	//Cac phuong thuc Get, Set cua thuoc tinh duoc chon
	public String getHchId();
	public void setHchId(String hchId);
	public String getKbhId();
	public void setKbhId(String kbhId);
	public String getBgstId(); //bang gia sieu thi cua kenh hien dai
	public void setBgstId(String bgstId);
	public String getVtchId();
	public void setVtId(String vtchId);
	public String getLchId();
	public void setLchId(String lchId);
	public String getNchId();
	public void setNchId(String nchId);
	public String getMckId();
	public void setMckId(String mckId);
	public String getGhcnId();
	public void setGhcnId(String ghcnId);
	
	public String getASMId();
	public void setASMId(String ASMId);
	
	public ResultSet getNkh_khList();
	
	public void setNkh_khList(ResultSet nkh_khlist);
	public ResultSet getNkh_KhSelected(); //cap dvkd_ncc duoc chon trong trang Update
	public void setNkh_KhSelected(ResultSet nkh_khselected);
	public Hashtable<Integer, String> getNkh_KhIds();
	public void setNkh_KhIds(String[] nkh_khIds);
	
	public ResultSet getNvgnRs();
	public ResultSet getASMRs();
	public void setNvgnRs(ResultSet nvgnRs);
	public String getNvgnId();
	public void setNvgnId(String nvgnId);
	
	public boolean CreateKh();
	public boolean UpdateKh();
	public void createRS();
	public void init();
	public void DBclose();
}