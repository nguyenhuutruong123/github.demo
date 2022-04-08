package geso.dms.center.beans.thaydoikhuyenmai;

import java.sql.ResultSet;
import java.util.List;

public interface IThayDoiKhuyenMai
{
	public String getId();
	public void setId(String id);
	
	public String getUserId();
	public void setUserId(String userId);
	
	public String getLoai();
	public void setLoai(String loai);
	
	public String getTrakmId();
	public void setTrakmId(String trakmId);
	
	public String getDkkmId();
	public void setDkkmId(String dkkmId);
	
	public boolean save();
	public boolean edit();
	
	public String getMsg();
	public void setMsg(String msg);

	public String getHinhthuc();
	public void setHinhthuc(String hinhthuc);
	
	public String getType();
	public void setType(String type);
	
	
	public List<ISanPham> getSpList();
	public void setSpList(List<ISanPham> spList);
	
	
	public List<ISanPham> getSpListOld();
	public void setSpListOld(List<ISanPham> spListOld);

	public ResultSet getCtkmRs();
	public void setCtkmRs(ResultSet ctkmRs);
	
	public ResultSet getTrakmRs();
	public void setTrakmRs(ResultSet trakmRs);
	
	
	public ResultSet getDkkmRs();
	public void setDkkmRs(ResultSet dkkmRs);	
	
	public void createRs();
	
	public void init();
	
	public String getCtkmId();
	public void setCtkmId(String ctkmId);
	
	
	public String getTongtien();
	public void setTongtien(String tongtien);
	
	public String getTongluong();
	public void setTongluong(String tongluong);
	
	public String getChietkhau();
	public void setChietkhau(String chietkhau);
	
	public void closeDB();
	
	
	public ResultSet getCtkmRsSuDung();
	public void setCtkmRsSuDung(ResultSet ctkmRsSuDung);
	
}
