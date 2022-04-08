package geso.dms.center.beans.ctkhuyenmai;

import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.List;

public interface ICtkhuyenmai
{
	
	public String getNhom_kh_loai_tru() ;
	public void setNhom_kh_loai_tru(String nhom_kh_loai_tru) ;
	public String getUserId();
	public void setUserId(String userId);
	public String getId();
	public void setId(String id);
    public void setLoaikhIds(String lkhIds);
    public String getLoaikhIds();
    public void setLoaikhRs(ResultSet loaiKh);
    public ResultSet getLoaikhRs();
	public String getScheme();
	public void setScheme(String scheme);
	public String getDiengiai();
	public void setDiengiai(String diengiai);			
	public String getTungay();
	public void setTungay(String tungay);
	public String getDenngay();
	public void setDenngay(String denngay);
	public String getType();
	public void setType(String type);
	public String getNgansach();
	public void setNgansach(String ngansach);
	public String getDasudung();
	public void setDasudung(String dasudung);
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
	
	public String getLoaikhuyenmai();
	public void setLoaikhuyenmai(String loaikm);
	public String getTylevoiPrimary();
	public void setTylevoiPrimary(String tyle);
	
	public List<IDieukienkm> getDkkmList();
	public void setDkkmList(List<IDieukienkm> dkkmlist);
	
	public List<ITrakm> getTrakmList();
	public void setTrakmList(List<ITrakm> trakmList);
	
	public ResultSet getTrakmRs();
	public void setTrakmRs(ResultSet trakmRs);
	public String getTrakmId();
	public void setTrakmId(String trakmId);
	
	public boolean CreateCtkm();
	public boolean UpdateCtkm();
	public void createRS();
	public void init();
	
	public void setkhoId(String khoId);
	public String getkhoId();
    public ResultSet getkhoIds();
    
    public void setDsnppSelected(ResultSet DsnppSelected);
    public ResultSet getDsnppSelected();
    public void setDsnpp(ResultSet Dsnpp);
    public ResultSet getDsnpp();
    
    public void setNpp(String[] npp);   
    public Hashtable<Integer, String> getnpp();
    
    public ResultSet getNhomkhnpp();
    
    public void setNhomkhnppId(String NhomkhnppId);
    public String getNhomkhnppId();
    
    public void setVungId(String vungId);
    public String getVungId();
    
    public void setKhuvucId(String khuvucId);
    public String getKhuvucId();
    
    public void setVungs(ResultSet vungs);
    public ResultSet getVungs();
    
    public void setKhuvuc(ResultSet khuvucs);
    public ResultSet getKhuvuc();
    
    public void CreateVung();
    
    public void setload(String load);
    public String getload();
    
    public void setngayds(String ngayds);
    public String getngayds();
    public void setngayktds(String ngayktds);
    public String getngayktds();
    
    public void setKbhRs(ResultSet kbh);
    public ResultSet getKbhRs();
    public void setKbhIds(String kbhIds);
    public String getKbhIds();
    public void setKbhId(String kbhId);
    public String getKbhId();
    
    public void setNhomspRs(ResultSet nspRs);
    public ResultSet getNhomspRs();
    
    public void setLoaiNganSach(String loains);
    public String getLoaiNganSach();
    public void setActive(String active);
    public String getActive();
    
    public String getPTToida();
	public void setPTToida(String ptToida);
	
	public String getNppTuchay();
	public void setNppTuchay(String nppTuchay);
    
    public void  DbClose(); 
    
    public void setPPhanbotheoDH(String pbtheoDH);
    public String getPhanbotheoDH();
    
    public void setApdungchoDHLe(String flag);
    public String getApdungchoDHLe();
    
    public void setMucphanbo(String mucpb);
    public String getMucphanbo();
    
    //Mã SAP
    public String getIo();
    public void setIo(String IO);
    //MaERP
    public String getSchemeErp();
    public void setSchemeErp(String SchemeErp);
    
    public String getTuNgay_DatHang();
    public void setTuNgay_DatHang(String tungay_dathang);
    
    public String getDenNgay_DatHang();
    public void setDenNgay_DatHang(String denngay_dathang);
    
    public String getNgansachkehoach();
	public void setNgansachkehoach(String ngansachkehoach);
	
	public String getApdungcho();
	public void setApdungcho(String apdungcho);
	
	public boolean updateNgayNgungHoatDong();
	public boolean checkNgayNgungHoatDong();
	public String getNgayngunghoatdong();
    
}
