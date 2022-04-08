package geso.dms.center.beans.kehoachbanhang;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.Hashtable;

public interface IKehoachbanhang extends Serializable 
{
	//Cac thuoc tinh 
	public String getUserId();
	public void setUserId(String userId);
	public String getId();
	public void setId(String id);
	public String getDiengiai(); //dien giai tuyen ban hang
	public void setDiengiai(String diengiai);
	public String getDdkd();
	public void setDdkd(String ddkd);
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
	
	public String getTrangthai();

	public void setTrangthai(String trangthai);
	
	public String getIsDisplay();
	public void setIsDisplay(String isDisplay);
	
	public String getNppId();
	public void setNppId(String nppId);
	public String getNppTen();
	public void setNppTen(String nppTen);
	public String getSitecode();
	public void setSitecode(String sitecode);
	
	public String getNgaylamviec();
	public void setNgaylamviec(String ngaylamviec);
	
	public ResultSet getDaidienkd();
	public void setDaidienkd(ResultSet daidienkd);
	public String getDdkdId();
	public void setDdkdId(String ddkdId);
	
	public ResultSet getKh_Tbh_DptList(); //khach hang duoc phan tuyen
	public void setKh_Tbh_DptList(ResultSet kh_tbh_dpt);
	public ResultSet getKh_Tbh_CdptList(); //khach hang chua duoc phan tuyen
	public void setKh_Tbh_CdptList(ResultSet kh_tbh_cdpt);
	public Hashtable<Integer, String> getKh_Tbh_DptIds();
	public void setKh_Tbh_DptIds(String[] kh_tbh_dptIds);
	public Hashtable<Integer, String> getKh_Tbh_CdptIds();
	public void setKh_Tbh_CdptIds(String[] kh_tbh_cdptIds);
	
	public String[] getTansoList();
	public void setTansoList(String[] tansoList);
	
	//tieu chi tim kiem khach hang chua dc phan tuyen
	public String getTenkhachhang();
	public void setTenkhachhang(String tenkhachhang);
	public String getDiachi();
	public void setDiachi(String diachi);
	
	//Move tuyen ban hang
	public String getDdkdNewId();
	public void setDdkdNewId(String ddkdnewId);
	public String getDiengiaiNew();
	public void setDiengiaiNew(String diengiainew);
	public String getNlvNew(); //ngay lam viec New
	public void setNlvNew(String nlvnew);
	
	public ResultSet getNlvList(); //ngay lam viec loc theo ddkdID
	public void setNlvList(ResultSet nlvList);
	public ResultSet getDiengiaiList(); //Ten tuyen ban hang, loc theo ddkd, ngaylamviec
	public void setDiengiaiList(ResultSet diengiaiList);
	
	public boolean CreateTbh();
	public boolean UpdateTbh();
	public boolean MoveTbh(String tbhOld, String tbhNew, String[] khIds, String[] tansoIds ,String[] sotts); //di chuyen khach hang tu tuyen ban hang cu, sang tuyen ban hang moi
	public void init();
	public void createDdkdRS();
	public void createRS();
	
	//move tuyen ban hang
	public void createNlvList();
	public void createDiengiaiList();
	public void DBclose();
	
	public void setSoTT(String[] sott);
	
}
