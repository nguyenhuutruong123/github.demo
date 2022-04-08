package geso.dms.center.beans.duyettrasanpham;

import geso.dms.distributor.beans.donhangtrave.ISanpham;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

public interface IDuyettrasanpham extends Serializable 
{
	//Cac thuoc tinh 
	public String getUserId();
	public void setUserId(String userId);
	public String getId();
	public void setId(String id);
		
	public String getNgaygiaodich();
	public void setNgaygiaodich(String ngaygiaodich);
	public String getDaidienkd();
	public void setDaidienkd(String daidienkd);
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	public String getNgaytao();
	public void setNgaytao(String ngaytao);
	public String getNguoitao();
	public void setNguoitao(String nguoitao);
	public String getNgaysua();
	public void setNgaysua(String ngaysua);	
	public String getNguoisua();
	public void setNguoisua(String nguoisua);
	
	public String getChietkhau();
	public void setChietkhau(String chietkhau);
	public String getVAT();
	public void setVAT(String vat);	
	public String getMessage();
	public void setMessage(String msg);
		
	public String getNppId();
	public void setNppId(String nppId);
	public String getNppTen();
	public void setNppTen(String nppTen);
	public String getSitecode();
	public void setSitecode(String sitecode);
	
	//Cac phuong thuc Get, Set tra ve ResultSet tuong ung
	public String getKhId();
	public void setKhId(String khId);
	public ResultSet getKhList();
	public void setKhList(ResultSet khlist);
	public String getKhoId();
	public void setKhoId(String khoId);
	public ResultSet getKhoList();
	public void setKhoList(ResultSet kholist);
	
	public ResultSet getGsbhList();
	public void setGsbhList(ResultSet gsbhList);
	public String getGsbhId();
	public void setGsbhId(String gsbhId);
	public ResultSet getDdkdList();
	public void setDdkdList(ResultSet ddkdList);	
	public String getDdkdId();
	public void setDdkdId(String ddkdId);
	
	public List<ISanpham> getSpList();
	public void setSpList(List<ISanpham> splist);
	
	public boolean duyetSptrave(String id, String userId);
	
	//Tinh tong tien, chiet khau, tong tien VAT
	public String getTongtientruocVAT();
	public void setTongtientruocVAT(String tttvat);

	public String getTongtiensauVAT();
	public void setTongtiensauVAT(String ttsvat);


	public void init();
	public void createRS();
	public void DBclose();

	public String getSmartId(); 
	public void setSmartId(String smartId);
	public String getKhTen();
	public void setKhTen(String khTen); 
}
