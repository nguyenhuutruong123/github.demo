package geso.dms.center.beans.thongbao;

import geso.dms.center.util.IPhan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IThongbaoList extends Serializable, IPhan_Trang 
{
	public String getId();
	public void setId(String id);
	
	public String getUserId();
	public void setUserId(String userId);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public String getTieude();
	public void setTieude(String tieude);
	
	public String getNoidung();
	public void setNoidung(String noidung);
	
	public String getNgaybatdau();
	public void setNgaybatdau(String ngaybatdau);
	
	public String getNgayketthuc();
	public void setNgayketthuc(String ngayketthuc);
	
	public String getNgaytao();
	public void setNgaytao(String ngaytao);
	
	public void setMsg(String Msg);
	public String getMsg();
	
	public ResultSet getThongbaoList();
	public void setThongbaoList(ResultSet thongbao);
	
	public ResultSet getThongbaonhanvienList();
	public void setThongbaonhanvienList(ResultSet thongbaonhanvien);
	
	public int getCurrentPage();
	public void setCurrentPage(int current);
	
	public int[] getListPages();
	public void setListPages(int[] listPages);
	
	public void init(String query);
	
	public void initNhanvien(String query);
	
	public String getTask();
	public void setTask(String task);
	
	public String getViewMode();
	public void setViewMode(String viewMode);
	
	public String getLoaithongbao();
	public void setLoaithongbao(String loaithongbao);
	
	public void createRs();
	public void DBClose();
	
}
