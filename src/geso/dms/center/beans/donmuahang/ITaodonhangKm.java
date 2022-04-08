package geso.dms.center.beans.donmuahang;



import java.sql.ResultSet;
import java.util.List;


public interface ITaodonhangKm {
	public String getId();
	public void setId(String id);	
	
	public String getNam();
	public void setNam(String nam);	
	
	
	public String getThang();
	public void setThang(String thang);	
	
	public String getUserId();
	public void setUserId(String userId);	
	
	public ResultSet getRsCTKM();
	public void getRsCTKM(ResultSet rs);
	

	public ResultSet getRsSp();
	public void getRsSp(ResultSet rs);
	
	public void Init();
	public void LoadSpKm();
	
	public boolean ThucHien();
	
	public String getMsg();
	
	public void setMsg(String msg);	
	
	public void setCTKMChon(String str);
	
	public String getCTKMChon();
	

	
	public void setListSanpham( List<ISanPhamTraKM> list);
	
	public List<ISanPhamTraKM>  getListSanPham();
	
	
}
