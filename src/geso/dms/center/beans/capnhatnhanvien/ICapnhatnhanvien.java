package geso.dms.center.beans.capnhatnhanvien;

import java.sql.ResultSet;

public interface ICapnhatnhanvien {

	public void setId(String Id);
	public String getId();
	
	public void setuserId(String userId);
	public String getuserId();
	
	public void setnppId(String nppId);
	public String getnppId();
	
    public void setTen(String Ten);
    public String  getTen();
    
    public void setngaysinh(String ngaysinh);
    public String getngaysinh();
    
    public void setemail(String email);
    public String getemail();
    
    public void setdienthoai(String dienthoai);
    public String getdienthoai();
    
    public void settendangnhap(String tendangnhap);
    public String gettendangnhap();
    
	public void setmatkhau(String matkhau);
	public String getmatkhau();
    
    public void setphanloai(String phanloai);
    public String getphanloai();
    
    public void setquyen(ResultSet quyen);
    public ResultSet getquyen();
    
    public void setquyenchon(ResultSet quyenchon);
    public ResultSet getquyenchon();
    
    public void settrangthai(String trangthai);
    public String gettrangthai();
    
    public void setmsg(String msg);
    public String getmsg();
    
    public void CreateQuyen(String[] chuoi);
    
    public void setkenh(ResultSet kenh);
    public ResultSet getkenh();
    
    
    public void setkenhchon(ResultSet kenhchon);
    public ResultSet getkenhchon();
    
    public void CreateKenh(String[] chuoi);
    
    
    public void setnpp(ResultSet npp);
    public ResultSet getnpp();
    
    public void setnppchon(ResultSet nppchon);
    public ResultSet getnppchon();
    
    public void CreateNpp(String[] chuoi);
    
    public void setsanpham(ResultSet sanpham);
    public ResultSet getsanpham();
    
    public void setsanphamchon(ResultSet sanphamchon);
    public ResultSet getsanphamchon();
    
    public void setKho(ResultSet khors);
    public ResultSet getKhoRs();
    
    public void setKhochon(ResultSet khochonrs);
    public ResultSet getKhochonrs();
    
    public void setNhomskus(ResultSet nhomskusrs);
    public ResultSet getNhomskus();
    
    public void setNhomskuschon(ResultSet nhomskuschonrs);
    public ResultSet getNhomskuschonrs();
    
    public void CreateSanpham(String[] chuoi);
    
    public void CreateKho(String[] chuoi);
    
    public void CreateNhomskus(String[] chuoi);
    
    public void setvungId(String vungId);
    public String getvungId();

    public void setvung(ResultSet vung);
    public ResultSet getvung();
    
    public void setkhuvucId(String khuvucId);
    public String getkhuvucId();

    public void setkhuvuc(ResultSet khuvuc);
    public ResultSet getkhuvuc();
    
    public void setnhanhangId(String nhanhangId);
    public String getnhanhangId();
    
    public void setnhanhang(ResultSet nhanhang);
    public ResultSet getnhanhang();
    
    public void setchungloaiId(String chungloaiId);
    public String getchungloaiId();
    
    public void setchungloai(ResultSet chungloai);
    public ResultSet getchungloai();
    
    public void setchon(String chon);
    public String getchon();
   
    public void setnhaphanphoi(ResultSet nhaphanphoi);
    public ResultSet getnhaphanphoi();
    
    public void setLoai(String loai);
    public String getLoai();
    public void setLoaiId(String loaiId);
    public String getLoaiId();
    public void setLoaiRs(ResultSet loaiRs);
    public ResultSet getLoaiRs();
    
    public void CreateRS();
    
    public boolean save();
    public boolean update();
    
    public void init();
    public void DBClose();
    
    public String getNppIds();
    public void setNppIds(String nppId);
    
    public ResultSet getKenhRs();
    public void setKenhRs(ResultSet kenhRs);
    
    public String getKenhId();
    public void setKenhId(String kenhId);
    
    public String getActiveTab();
    public void setActiveTab(String active);
    
    //Khai báo số hóa đơn cho từng user
    
    public void setSohoadontu(String sohoadontu);
    public String getSohoadontu();
    public void setSohoadonden(String sohoadonden);
    public String getSohoadonden();
    
    //quyen nhan vien
    public void setdmquyenId(String quyenId);
    public String getdmquyenId();
   
    public void setDanhmucquyenRs(ResultSet Rsdanhmucquyen);
    public ResultSet getDanhmucquyenRs();
    
    public String getTtId();
    public void setTtId(String ttId);
    
    public ResultSet getTtRs();
    public void setTtRs(ResultSet ttRs);
    
    public String getQhId();
    public void setQhId(String qhId);
    
    public ResultSet getQhRs();
    public void setQhRs(ResultSet qhRs);
    
    public void closeDB();
    
    public String getVungId();
    public void setVungId(String VungId);
    
    public String getKhuvucId();
    public void setKhuvucId(String KhuvucId);
    
    public String getNhanmaildms();
    public void setNhanmaildms(String nhanmaildms);
}
