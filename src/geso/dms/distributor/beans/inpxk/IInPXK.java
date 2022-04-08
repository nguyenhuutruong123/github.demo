package geso.dms.distributor.beans.inpxk;

import geso.dms.distributor.beans.donhang.ISanpham;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.List;

public interface IInPXK extends Serializable
{
	//Cac thuoc tinh 
	public String getUserId();
	public void setUserId(String userId);
	public String getId();
	public void setId(String id);
	
	public ResultSet getNhanvienGN();
	public void setNhanvienGN(ResultSet nhanviengn);	
	public String getNvgnId();
	public void setNvgnId(String nvgnId);
	public String getNvgnTen();
	public void setNvgnTen(String nvgnTen);
	
	public ResultSet getNvBanhang();
	public void setNvBanhang(ResultSet nvbanhang);	
	public String getNvbhId();
	public void setNvbhId(String nvbhId);
	
	public ResultSet getTuyenbanhang();
	public void setNvTuyenhang(ResultSet tuyenbanhang);	
	public String getTbhId();
	public void setTbhId(String tbhId);
	
	public ResultSet getDonhangList();
	public void setDonhangList(ResultSet dhlist);
	public ResultSet getDhIdsList();
	public void setDhIdsList(ResultSet dhIdsList);
	public Hashtable<Integer, String> getDonhangIds();
	public void setDonhangIds(String[] donhangIds);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	public String getNgaylap();
	public void setNgaylap(String ngaylap);
	
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
	
	public String getNppId();
	public void setNppId(String nppId);
	public String getNppTen();
	public void setNppTen(String nppTen);
	public String getSitecode();
	public void setSitecode(String sitecode);
	
	//dung trong trang tao file PDF
	public String[] getDhIds();
	public List<ISanpham> getPxk_spList();
	public void setPxk_spList(List<ISanpham> spList);
	public List<ISanpham> getPxk_spkmList();
	public void setPxk_spkmList(List<ISanpham> spkmList);
	public List<ISanpham> getPxk_tienkmList();
	public void setPxk_tienkmList(List<ISanpham> tienkmList);
	
	public boolean CreatePxk();
	public boolean UpdatePxk();
	
	public ResultSet getSpkmList(); 
	
	public void setSpkmList(ResultSet spkmList); 

	public ResultSet getTienkmList(); 
	
	public void setTienkmList(ResultSet tienkmList); 
	public Hashtable<String, Long> getCredits();
	public void createRS();
	public void init();
	public void init2();
	public void init3(); //dung de view phieu xuat kho (khong in file pdf)
	public void DBclose();
	public void setngaykhoaso(String ngaykhoaso);
    public String getngaykhoaso();
	
}
