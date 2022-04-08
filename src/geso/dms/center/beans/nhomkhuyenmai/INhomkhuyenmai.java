package geso.dms.center.beans.nhomkhuyenmai;
import java.io.Serializable;
import java.sql.ResultSet;

public interface INhomkhuyenmai extends Serializable {
	
	
	public String getMsg();

	public void setMsg(String msg);
	
	public String getId();

	public void setId(String id);
	
	public String getTen();
	
	public void setTen(String ten);
	
	public String getDiengiai();
	
	public void setDiengiai(String diengiai);

	public String getTrangthai();
	
	public void setTrangthai(String trangthai);
	
	public String getNgaytao();
	
	public void setNgaytao(String ngaytao);
	
	public String getNgaysua();
	
	public void setNgaysua(String ngaysua);
	
	public String getNguoitao();
	
	public void setNguoitao(String nguoitao);
	
	public String getNguoisua();
	
	public void setNguoisua(String nguoisua);

	public String getMessage();
	
	public void setMessage(String msg);
		
	public ResultSet getSpList();

	public void setSpList(ResultSet spList);

	public ResultSet getSpSelected();

	public void setSpSelected(ResultSet spSelected);

	public ResultSet getDvkdList();

	public void setDvkdList(ResultSet dvkdList);
	
	public ResultSet getNhList();

	public void setNhList(ResultSet nhList);
	
	public ResultSet getCLList();

	public void setClList(ResultSet clList);
	
	public String getDvkdId();

	public void setDvkdId(String dvkdId);

	public String getNhId();

	public void setNhId(String nhId);

	public String getClId();

	public void setClId(String clId);
	
	public void UpdateRS();

	public String[] getSanpham();

	public void setSanpham(String[] sanpham);
		
	public boolean saveNewNkm();
	
	public boolean updateNkm();
	public void setkenhId(String kenhId);
	public String getKenhId();
	public void setKenh(ResultSet kenh);
	public ResultSet getKenh();
	public ResultSet getNganhhang();
	public void setNganhhang(ResultSet nganhhang);
	public String getNganhhang_fk();
	public void setNganhhang_fk(String nganhhang_fk);
   
}