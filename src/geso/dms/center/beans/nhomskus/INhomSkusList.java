package geso.dms.center.beans.nhomskus;
import java.io.Serializable;
import java.util.List;;

public interface INhomSkusList extends Serializable {

	public String getTrangthai();

	public void setTrangthai(String trangthai);
	
	public String getTungay();
	
	public void setTungay(String tungay);

	public String getDenngay();
	
	public void setDenngay(String denngay);	

	public List<INhomSkus> getSkusList();
	
	public void setSkusList(List<INhomSkus> nskuslist);
	
	public boolean getSearch();

	public void setSearch(boolean search);	
}