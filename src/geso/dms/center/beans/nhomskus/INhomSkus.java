package geso.dms.center.beans.nhomskus;
import java.io.Serializable;
import java.sql.ResultSet;

public interface INhomSkus extends Serializable {
	
	public String getId();

	public void setId(String id);
	
	public String getMa();

	public void setMa(String ma);
	
	public String getTen();
	
	public void setTen(String ten);
	
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
	
	public ResultSet getNSkusList();

	public void setNSkusList(ResultSet skusList);
	
	public ResultSet getSpList();

	public void setSpList(ResultSet spList);

	public ResultSet getSpSelected();

	public void setSpSelected(ResultSet spSelected);

	public void UpdateRS();

	public String[] getNhomSkus();

	public void setNhomSkus(String[] nhom);

	public String[] getSanpham();

	public void setSanpham(String[] sanpham);
		
	public boolean saveNewNSkus();
	
	public boolean updateNSkus();
   
}
