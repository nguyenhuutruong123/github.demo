package geso.dms.center.beans.duyetdontrahangnpp;

import java.sql.ResultSet;

public interface IDuyetdontrahangnpp 
{
	  void setNgay(String ngay);
	  String getNgay();
	  void setnppId(String nppId);
	  String getnppId();
	  String getddkdTen();
	  String getKahchhang();
	  String getTongtien();
	  String getchietkhau();
	  String gettongbVat();
	  String getvat();
	  String getavat();
	  ResultSet getsanpham();
	  void init();
}
