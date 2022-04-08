package geso.dms.center.beans.upload;
import java.sql.ResultSet;
public interface IUpload
{

	public String getId();

	public void setId(String id);

	public String getThang();

	public void setThang(String thang);

	public String getNam();

	public void setNam(String nam);

	public String getTrangthai();

	public void setTrangthai(String trangthai);

	public String getMsg();

	public void setMsg(String msg);

	public String getUserId();

	public void setUserId(String userId);
	
	public String getNppMa();
	public void setNppMa(String nppMa);

	public String getNppId();

	public void setNppId(String nppId);

	public String getKhId();

	public void setKhId(String khId);

	public ResultSet getRsKh();

	public void setRsKh(ResultSet rsKh);

	public ResultSet getRsNpp();

	public void setRsNpp(ResultSet rsNpp);

	public String getType();

	public void setType(String type);

	public boolean Stock();

	public void createRs();

	public void closeDB();

	public void createRs_Tonkho();

	public ResultSet getRsTonkho();

	public void setRsTonkho(ResultSet rsTonkho);

	public ResultSet getRsSalesIn();

	public void setRsSalesIn(ResultSet rsSalesIn);

	public void createRs_SalesIn();

	public ResultSet getRsForeCast();

	public void setRsForeCast(ResultSet rsforecast);

	public void createRs_ForeCast();
	
	public void setNguoiTao(String nguoitao);
	public String getNguoiTao();
	public void setNguoiSua(String nguoisua);
	public String getNguoiSua();
	
	public void setNgayTao(String ngaytao);
	public String getNgayTao();
	public void setNgaySua(String nguoisua);
	public String getNgaySua();
	
	public void setTask(String task);
	public String getTask();
	
	public void setSpNpp(String value);
	public String getSpNpp();
	public String createSpNpp();
	
	public ResultSet createNppList();
	public boolean checkThangNam();
	
	public void init();
	public boolean create();
	public boolean update();
	
	
	public String getVungId();
	public void setVungId(String id);
	
	public String getKhuvucId();
	public void setKhuvucId(String khuvucId);
	
	public ResultSet getVungRs();
	public void setVungRs(ResultSet vungRs);
	
	public ResultSet getKhuvucRs();
	public void setKhuvucRs(ResultSet khuvucRs);
	
	public ResultSet getNppCoTonKhoRs();
	public void setNppCoTonkhoRs(ResultSet tonkhoRs);
	
	public void initImportRs();
	
	public void importTonKho();
	
	public void NewImport();
	
	public String  DuaLaiTonKhoNgay();
	
	public String getNgaykhoaso();
	public void setNgaykhoaso(String ngaykhoaso);

	
	public String getGhichu();
	public void setGhichu(String ghichu);

	
	
}
