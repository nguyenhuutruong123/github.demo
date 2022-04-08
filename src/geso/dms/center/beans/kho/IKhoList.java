package geso.dms.center.beans.kho;

import java.io.Serializable;

import geso.dms.center.beans.kho.IKho;

import java.util.List;

public interface IKhoList extends Serializable {
		
	public List<IKho> getKhoList();
	
	public void setKhoList(List<IKho> kholist);
	
	public String getTen();
	
	public void setTen(String ten);
	
	public String getDiengiai();
	
	public void setDiengiai(String diengiai);
	
	public String getTungay();
	
	public void setTungay(String tungay);

	public String getDenngay();
	
	public void setDenngay(String denngay);
	
	public String getTrangthai();
	
	public void setTrangthai(String trangthai);
	
	public void setMsg(String Msg);
	
	public String getMsg();

	public List<Object> getDataSearch();
	public void setDataSearch(List<Object> dataSearch);
   
}