package geso.dms.distributor.beans.doihang;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;

public interface IErpKhachhangdoihang
{
	public String getUserId();
	public void setUserId(String userId);
	
	public String getId();
	public void setId(String Id);

	public String getNgayyeucau();
	public void setNgayyeucau(String ngayyeucau);
	public String getNgaydenghi();
	public void setNgaydenghi(String ngaydenghi);

	public String getGhichu();
	public void setGhichu(String ghichu);
	
	public String getKhoNhapId();
	public void setKhoNhapId(String khonhaptt);
	public ResultSet getKhoNhapRs();
	public void setKhoNHapRs(ResultSet khonhapRs);
	
	public String getKhoDoiId();
	public void setKhoDoiId(String khodoitt);
	public ResultSet getKhoDoiRs();
	public void setKhoDoiRs(ResultSet khodoiRs);
	
	public String getNppId();
	public void setNppId(String nppId);
	public ResultSet getNppRs();
	public void setNppRs(ResultSet nppRs);
	
	public String getNppTen();
	public void setNppTen(String nppTen);
	public String getSitecode();
	public void setSitecode(String sitecode);
	
	public String getKhId();
	public void setKhId(String khId);
	public ResultSet getKhRs();
	public void setKhRs(ResultSet khRs);
	
	public ResultSet getLydoRs();
	public void setLydoRs(ResultSet lydoRs);
	public String getLydoId();
	public void setLydoId(String lydoId);
	
	public String getDvkdId();
	public void setDvkdId(String dvkdId);
	public ResultSet getDvkdRs();
	public void setDvkdRs(ResultSet dvkdRs);
	
	public String getKbhId();
	public void setKbhId(String kbhId);
	public ResultSet getKbhRs();
	public void setKbhRs(ResultSet kbhRs);
	
	public ResultSet getDvtRs();
	public void setDvtRs(ResultSet dvtRs);
	
	public String getSchemeId();
	public void setSchemeId(String kbhId);
	public ResultSet getSchemeRs();
	public void setSchemeRs(ResultSet schemeRs);
	
	public String getLoaidonhang();
	public void setLoaidonhang(String loaidonhang);
	public String getChietkhau();
	public void setChietkhau(String chietkhau);
	public String getVat();
	public void setVat(String vat);
	
	
	public ResultSet getSanphamRs();
	public void setSanphamRs(ResultSet spRs);
	
	public ResultSet getSanphamDoiRs();
	public void setSanphamDoiRs(ResultSet spDoiRs);
	
	public void setSanpham_soluong(Hashtable<String, String> sp_soluong);
	public Hashtable<String, String> getSanpham_soluong();
	
	public void setSanpham_ngaysanxuat(Hashtable<String, String> sp_ngaysanxuat);
	public Hashtable<String, String> getSanpham_ngaysanxuat();
		
	public void setSanpham_solo(Hashtable<String, String> sp_solo);
	public Hashtable<String, String> getSanpham_solo();
	
	public void setSanpham_NSX(Hashtable<String, String> sp_nsx);
	public Hashtable<String, String> getSanpham_NSX();
	
	
	//
	public void setSanpham_id_duyet(Hashtable<String, String> Sanpham_id_duyet);
	public Hashtable<String, String> getSanpham_id_duyet();
	
	public void setSanpham_soluong_duyet(Hashtable<String, String> Sanpham_soluong_duyet);
	public Hashtable<String, String> getSanpham_soluong_duyet();
	
	public void setSanpham_solo_duyet(Hashtable<String, String> Sanpham_solo_duyet);
	public Hashtable<String, String> getSanpham_solo_duyet();
	
	//
	
	public ResultSet getSoloRs( String spId, String khoId );
	
	public String getMsg();
	public void setMsg(String msg);
	
	public boolean createNK(HttpServletRequest request);
	public boolean updateNK(HttpServletRequest request);
	public boolean duyetNK(HttpServletRequest request);
	

	public void createRs();
	public void createRsDuyet();
	
	public void init();
	public void initCapNhat();
	public void initDuyet();
	public String gettenkh();
	
	public void DBclose();
	
}
