package geso.dms.distributor.beans.chuyenkhonew;



import java.sql.ResultSet;
import java.util.Hashtable;
import java.util.List;

public interface IChuyenKho
{
	public String getNppCk();
	public void setNppCk(String nppCk);
	
	public String getId();
	public void setId(String id);

	public String getNgaynhap();
	public void setNgaynhap(String ngaynhap);
	public String getThuevat() ;

	public void setThuevat(String thuevat) ;

	public String getUserId();
	public void setUserId(String userId);

	public String[] getFile();
	public void setFile(String file);
	public String getVat();
	public void setVat(String vat);

	public String getNppTen();
	public void setNppTen(String nppTen);

	public String getNppId();
	public void setNppId(String id);

	public String getNccId();
	public void setNccId(String nccId);

	public ResultSet getDvkdRs();
	public void setDvkdRs(ResultSet dvkdRs);

	public String getKbhId();
	public void setKbhId(String kbhId);

	public ResultSet getKbhRs();
	public void setKbhRs(ResultSet kbhRs);

	public String getDvkdId();
	public void setDvkdId(String dvkdId);

	public ResultSet getNccRs();
	public void setNccRs(ResultSet ncc);

	public String getMsg();
	public void setMsg(String msg);

	public String getGhichu();
	public void setGhichu(String Ghichu);

	public List<ISanPham> getSpList();
	public void setSpList(List<ISanPham> spList);

	public ResultSet getKhoRs();
	public void setKhoRs(ResultSet khoRs);


	public String getTienBVAT();
	public void setTienBVAT(String tienBVAT);

	public String getTienAVAT();
	public void setTienAVAT(String tienAVAT);

	public Hashtable<String, String> getDvdlList();
	public void setDvdlList( Hashtable<String, String> dvdlList);
	
	
	public String getKhochuyenId();
	public void setKhochuyenId(String khochuyenId);
	
	public String getKhonhanId();
	public void setKhonhanId(String khonhanId);
	
	public String getDhId();
	public void setDhId( String dhId );
	
	public String getTrangThai();
	public void setTrangThai( String trangThai );
	
	public String getView();
	public void setView( String view );


	public void init( String nppId );

	public boolean save();

	public boolean edit();

	public void DBclose();
	void createRs(String nppId);
	
	public String getNgayDc();
	public void setNgayDc( String ngayDc);
}
