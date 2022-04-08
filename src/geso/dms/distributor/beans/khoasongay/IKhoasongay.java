package geso.dms.distributor.beans.khoasongay;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IKhoasongay extends Serializable
{
	public String getUserId();
	public void setUserId(String userId);
	public String getNgaykhoaso();
	public void setNgaykhoaso(String ngaykhoaso);
	
	public void setFromMonth(String month);
	public String getFromMonth();
	
	public void setFromYear(String fromyear);
	public String getFromYear();
	
	
	public String getMessege();
	public void setMessege(String msg);
	
	public String getNppId();
	public void setNppId(String nppId);
	public String getNppTen();
	public void setNppTen(String nppTen);
	public String getSitecode();
	public void setSitecode(String sitecode);
	
	public String getNgayKhoaSoGanNhat();
	public void setNgayKhoaSoGanNhat(String nksgn);
	
	public boolean isPxkChuaChot();
	public void setIsPxkChuaChot(boolean pxkChuaChot);
	public boolean isPthChuaChot();
	public void setIsPthChuaChot(boolean pthChuaChot);
	
	public ResultSet getDhChuaChotList();
	public void setDhChuaChotList(ResultSet dhcclist);
	public ResultSet getDhDaXuatKhoList();
	public void setDhDaXuatKhoList(ResultSet dhdxklist);
	public ResultSet getDhDaChotList();
	public void setDhDaChotList(ResultSet dhdclist);
	
	public String checkNgayChot(String ngayksgn, String ngayks);
	public boolean KhoaSoNgay(String ngayks);
	public void init();
	public void createRs();
	public void DBclose();
	public String getNgaycuoithang();

	public void setNgaycuoithang(String ngaycuoithang) ;

	public String getNgayhientai();

	public void setNgayhientai(String ngayhientai);
	public String getThanksgn();

	public void setThanksgn(String thanksgn);

	public String getNamksgn();

	public void setNamksgn(String namksgn) ;

}
