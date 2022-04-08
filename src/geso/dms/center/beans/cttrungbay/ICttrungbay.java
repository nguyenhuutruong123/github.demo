package geso.dms.center.beans.cttrungbay;

import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.List;

public interface ICttrungbay
{
	public String getUserId();
	public void setUserId(String userId);
	public String getId();
	public void setId(String id);
	public String getScheme();
	public void setScheme(String scheme);
	public String getDiengiai();
	public void setDiengiai(String diengiai);	
	
	public String getNgayTds();
	public void setNgayTds(String ngaytds);
	public String getNgayktTds();
	public void setNgayktTds(String ngayktTds);
	public String getNgayTb();
	public void setNgayTb(String ngaytb);
	public String getNgayktTb();
	public void setNgayktTb(String ngaykttb);
	public String getNgayBddk();
	public void setNgayBddk(String ngayBddk);
	public String getNgayKtdk();
	public void setNgayKtdk(String ngayKtdk);
	
	public String getType();
	public void setType(String type);
	public String getNgansach();
	public void setNgansach(String ngansach);
	public String getDasudung();
	public void setDasudung(String dasudung);
	public String getSolantt();
	public void setSolantt(String solantt);
	
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
	
	public List<INhomsptrungbay> getNsptbList();
	public void setNsptbList(List<INhomsptrungbay> nsptblist);
	
	public ResultSet getTratbRs();
	public void setTratbRs(ResultSet tratbRs);
	public Hashtable<String, Integer> getTratbId();
	public void setTratbId(String[] tratbId, String[] pheptoan);
	
	//search tratrungbay
	public String getTtb_diengiai();
	public void setTtb_diengiai(String ttb_diengiai);
	public String getTtb_tungay();
	public void setTtb_tungay(String ttb_tungay);
	public String getTtb_denngay();
	public void setTtb_denngay(String ttb_denngay);
	
	//search nhomsptrungbay
	public String getNsptb_diengiai();
	public void setNsptb_diengiai(String nsptb_diengiai);
	public String getNsptb_tungay();
	public void setNsptb_tungay(String nsptb_tungay);
	public String getNsptb_denngay();
	public void setNsptb_denngay(String nsptb_denngay);
	
	public boolean CreateCttb();
	public boolean UpdateCttb();
	public void createRS();
	public void init();
	
	public String isDangkythem();
	public void setDangkythem(String flag);
	
	public void setKbhRs(ResultSet kbh);
	public ResultSet getKbhRs();
	public void setVungRs(ResultSet vung);
	public ResultSet getVungRs();
	public void setKhuvucRs(ResultSet khuvuc);
	public ResultSet getKhuvucRs();
	public void setNppRs(ResultSet kbh);
	public ResultSet getNppRs();
	
	public String getKbhIds();
	public void setKbhIds(String kenhIds);
	public String getVungIds();
	public void setVungIds(String vungIds);	
	public String getKhuvucIds();
	public void setKhuvucIds(String kvIds);	
	public String getNppIds();
	public void setNppIds(String nppIds);	
	
	public void setMucphanbo(String mucpb);
	public String getMucphanbo();
	
	public String getDangkyPDA() ;
	public void setDangkyPDA(String dangkyPDA) ;
	
	public void setLoaikhRs(ResultSet loaiKh);
    public ResultSet getLoaikhRs();
    public void setLoaikhIds(String lkhIds);
    public String getLoaikhIds();
    
    public void setHangkhRs(ResultSet hangKh);
    public ResultSet getHangkhRs();
    public void setHangkhIds(String hangIds);
    public String getHangkhIds();
    
    public void setVitrikhRs(ResultSet vtKh);
    public ResultSet getVitrikhRs();
    public void setVitrikhIds(String vtIds);
    public String getVitrikhIds();
    
    public String getPhaimuadonhang() ;
	public void setPhaimuadonhang(String phaimuadonhang) ;
	
	//Tab Active jquery
	public void setActive(String active);
    public String getActive();
    public void DbClose();
}

