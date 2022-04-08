package geso.dms.center.beans.dieukienkhuyenmai;
import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.List;

public interface IDieukienkhuyenmai
{
	public String getUserId();
	public void setUserId(String userId);
	public String getId();
	public void setId(String id);
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	public String getHinhthuc();
	public void setHinhthuc(String hinhthuc);		
	public String getTongtien();
	public void setTongtien(String tongtien);
	public String getTongluong();
	public void setTongluong(String tongluong);	
	public String getType();
	public void setType(String type);
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
	
	public ResultSet getNhomspRs();
	public void setNhomspRs(ResultSet nhomspRs);
	public String getNhomspId();
	public void setNhomspId(String nhomspId);
	
	//sp loc theo nhomsp
	public List<ISanpham> getSpList();
	public void setSpList(List<ISanpham> spList);
	
	//luu lai cac sanpham duoc chon(neu co error...)
	public Hashtable<String, Integer> getSp_nhomspIds();
	public void setSp_nhomspIds(Hashtable<String, Integer> sp_nhomspIds);
	public List<ISanpham> getSpSudungList(); //list sanpham nguoi dung nhap
	public void setSpSudungList(List<ISanpham> splist);
	
	public boolean CreateDkkm(String[] masp, String[] soluong);
	public boolean UpdateDkkm(String[] masp, String[] soluong);
	public void createRS();
	public void createDkkmSpList();
	public void init();
}
