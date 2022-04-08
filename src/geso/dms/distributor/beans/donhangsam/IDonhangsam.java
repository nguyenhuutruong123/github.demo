package geso.dms.distributor.beans.donhangsam;

import geso.dms.distributor.db.sql.dbutils;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.List;

public interface IDonhangsam extends Serializable
{
	//Cac thuoc tinh 
	public String getUserId();
	public void setUserId(String userId);
	public String getId();
	public void setId(String id);
		
	public String getDiachigiaohang();
	public void setDiachigiaohang(String diachigiaohang);
	public String getNgaygiaodich();
	public void setNgaygiaodich(String ngaygiaodich);
	public String getNppTen();
	public void setNppTen(String nppTen);
	public String getDaidienkd();
	public void setDaidienkd(String daidienkd);			
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
	public String getChietkhau();
	public void setChietkhau(String chietkhau);
	public String getVAT();
	public void setVAT(String vat);	
	public String getMessage();
	public void setMessage(String msg);
	public String getBgstId();
	public void setBgstId(String bgstId);
		
	public String getNppId();
	public void setNppId(String nppId);
	public String getSitecode();
	public void setSitecode(String sitecode);
	
	//Cac phuong thuc Get, Set tra ve ResultSet tuong ung
	public String getKhId();
	public void setKhId(String khId);
	public ResultSet getKhList();
	public void setKhList(ResultSet khlist);
	public String getKhoId();
	public void setKhoId(String khoId);
	public ResultSet getKhoList();
	public void setKhoList(ResultSet kholist);
	
	public ResultSet getDdkdList();
	public void setDdkdList(ResultSet ddkdList);	
	public String getDdkdId();
	public void setDdkdId(String ddkdId);
	public String getGsbhId();
	public void setGsbhId(String gsbhId);
	
	public ResultSet getTbhList();
	public void setTbhList(ResultSet tbhList);	
	public String getTbhId();
	public void setTbhId(String tbhId);
	public void setMuctindung(String muctindung);
	public String getMuctindung();
	
	public void setSotiengiam(String Sotiengiam);
	public String getSotiengiam();
	

	public List<ISanpham> getSpList();
	public void setSpList(List<ISanpham> splist);
	
	//Tinh tong tien, chiet khau, tong tien VAT
	public String getTongtientruocVAT();
	public void setTongtientruocVAT(String tttvat);

	public float getTongtiensauCK();
	public void setTongtiensauCK(float ttsck);

	public String getTongtiensauVAT();
	public void setTongtiensauVAT(String ttsvat);
	
	public float getTongtienCKKM();
	public void setTongtienCKKM(float ttckkm);
	
	public float getTongtiensauCKKM();
	public void setTongtiensauCKKM(float ttsckkm);
	
	public double getNoCu();
	public void setNoCu(double nocu);
	
	public String getnvgnId();
	public void setnvgnId(String nvgnId);
	public ResultSet getnvgnRs();
	
	public Hashtable<String, Integer> getSpThieuList();
	public void setSpThieuList(Hashtable<String, Integer> spThieuList);
	
	//tra khuyen mai
	public Hashtable<String, Float> getScheme_Tongtien();
	public void setScheme_Tongtien(Hashtable<String, Float> scheme_tongtien);
	public Hashtable<String, Float> getScheme_Chietkhau();
	public void setScheme_Chietkhau(Hashtable<String, Float> scheme_chietkhau);
	public List<ISanpham> getScheme_SpList();
	public void setScheme_SpList(List<ISanpham> splist);
	
	public boolean CreateDh(List<ISanpham> splist);
	public boolean UpdateDh(List<ISanpham> splist, String action);
	
	public boolean UpdateDhXuatKho(List<ISanpham> splist);
	public boolean ChotDh(List<ISanpham> splist);
	public void init();
	public void createRS();
	public void CreateSpList();
	public void DBclose();
	public boolean Muctindung();
	public ResultSet getgsbhs();
	
	public String getTongChietKhau();
	public void setTongChietKhau(String tck);
	public void CreateSpDisplay();
	public String getKHList();
	public String getSmartId(); 
	public void setSmartId(String smartId);
	public String getKhTen();
	public void setKhTen(String khTen); 
	
	public boolean isAplaikhuyenmai(); //bien dung bat nguoi dung phai ap lai khuyen mai neu vao` sua don hang da co km
	public void setAplaikhuyenmai(boolean aplaikm);
	public boolean isCokhuyenmai(); //bien xet truong hop nguoi dung sau khi da ap lai khuyen mai, duoc khuyen mai, ma thay doi san pham ban --> bat ap lai km
	public void setCokhuyenmai(boolean cokm);
	public boolean isChuaApkhuyenmai(); //neu chua ap khuyen mai ma luu --> canh bao ap khuyen mai
	public void setIsChuaApkhuyenmai(boolean chuaApkm);
	public boolean isDamuahang(); //khach hang da mua hang, ---> canh bao
	public void setIsDamuahang(boolean damuahang);
	public boolean isDxkChuaChot(); //don hang da co trong phieuxuatkho, ma phieu xuat kho chua chot --> khong cho sua ngay
	public void setIsDxkChuaChot(boolean cokm);
	
	public void setNgayKs(String ngayks);
	public String getNgayKs();
	
	public String createPth(String pxkId, dbutils db);
	public String getPxkId();
	public void setPxkId(String pxkId);
	public void createPxkId();
	public String DeleteDonHangDxk();
	
	public String getIsChiNhanh();
	public void setIsChiNhanh(String isChiNhanh);
	
	
	public String getChietkhauDH();
	public void setChietkhauDH(String ckdh);
	
	public boolean LuuTrungBay(String scheme, Integer soXuat);
	public void setCotrungbay(String cotrungbay);
	public String getCotrungbay();
	public Hashtable<String, Integer> ApTrungBay(String dhId, String khId, String nppId, String ngaydh);
	public boolean isAplaitrungbay(); //bien dung bat nguoi dung phai ap lai trung bay neu vao` sua don hang da co trung bay
	public void setAplaitrugnbay(boolean aplaitb);
	
	public String getGhiChu();
	public void setGhiChu(String ghichu);
	
	public String getNgaygiaohang();
	public void setNgaygiaohang(String ngaygh);
	
	public String ApTrungBay();
	public String ApTichLuy();
	

}
