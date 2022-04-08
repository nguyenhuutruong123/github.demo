package geso.dms.center.beans.gioihancongnott;
import java.util.List;

public interface IGioiHanCongNoTT {
	public String getUserId();
	public void setUserId(String userId);
	public String getId();
	public void setId(String id);
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	public String getSongayno();
	public void setSongayno(String songayno);
	public String getSotienno();
	public void setSotienno(String sotienno);
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
	public void setListGioHanCongNo(String sql);
	public List<IGioiHanCongNoTT> getListGioiHanCongNo();
	public void setListNhapp(List<IGioiHangCongNoTT_Npp> list);
	public List<IGioiHangCongNoTT_Npp> getListNhaPP();
	public boolean SaveGioiHanCongNoTT();
	public boolean EditGioiHanCongNoTT();
	public boolean DeleteGioiHanCongNoTT();
}
