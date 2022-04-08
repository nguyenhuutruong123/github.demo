package geso.dms.center.beans.nhomsanpham;

import java.io.Serializable;
import java.util.List;;

public interface INhomsanphamList extends Serializable {

	
	public String getDiengiai();
	
	public void setDiengiai (String diengiai);

	public String getLoaithanhvien();

	public void setLoaithanhvien(String thanhvien);	

	public String getLoainhom();

	public void setLoainhom(String lnhom);

	public String getTrangthai();

	public void setTrangthai(String trangthai);
	
	public String getTungay();
	
	public void setTungay(String tungay);

	public String getDenngay();
	
	public void setDenngay(String denngay);	

	public List<INhomsanpham> getNspList();
	
	public void setNspList(List<INhomsanpham> nsplist);
	
	public boolean getSearch();

	public void setSearch(boolean search);	
	public String getView() ;
	public void setView(String view) ;
}