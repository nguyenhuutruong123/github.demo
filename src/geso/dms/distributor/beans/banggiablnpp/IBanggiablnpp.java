package geso.dms.distributor.beans.banggiablnpp;

import geso.dms.distributor.beans.banggiablnpp.ISanpham;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

public interface IBanggiablnpp extends Serializable
{
	public String getUserId();
	public void setUserId(String userId);
	public String getView();

	public void setView(String view) ;
	public String getId();
	public void setId(String id);

	public String getTenbanggia();
	public void setTenbanggia(String tenbanggia);

	public String getDonvikinhdoanh();
	public void setDonvikinhdoanh(String donvikinhdoanh);

	public String getNgaytao();
	public void setNgaytao(String Ngaytao);

	public String getNguoitao();
	public void setNguoitao(String nguoitao);

	public String getNgaysua();
	public void setNgaysua(String ngaysua);

	public String getNguoisua();
	public void setNguoisua(String nguoisua);

	public ResultSet getDvkd();
	public void setDvkd(ResultSet dvkd);

	public String getDvkdId();
	public void setDvkdId(String dvkdId);

	public List<ISanpham> getSpList();
	public void setSpList(List<ISanpham> splist);

	public String getNppId();
	public void setNppId(String nppId);

	public String getNppTen();
	public void setNppTen(String nppTen);

	public String getSitecode();
	public void setSitecode(String sitecode);

	public String getMessage();
	public void setMessage(String msg);
	
	public String getTungay();
	public void setTungay(String tungay);
	
	public String getKenh();
	public void setKenh(String kenh);

	public boolean CreateBgbl(String[] spId, String[] spGiabanlechuan, String[] spGiabanlenpp);
	public boolean UpdateBgbl(String[] spId, String[] spGiabanlechuan, String[] spGiabanlenpp,String[]giathung,String[] quycach);

	public void init();

	public void createRS();

	public void DBclose();
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);

}
