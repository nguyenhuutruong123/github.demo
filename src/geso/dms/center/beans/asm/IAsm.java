package geso.dms.center.beans.asm;

import java.sql.ResultSet;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;

public interface IAsm {
	public String getId();
		
	public void setId(String Id);

	public String getMa();
	
	public void setMa(String asmMa);
	
	public String getTen();
		
	public void setTen(String asmTen);
		
	public String getDienthoai();
		
	public void setDienthoai(String dienthoai);
		
	public String getEmail();
		
	public void setEmail(String email);

	public String getDiachi();
	
	public void setDiachi(String diachi);

	public String getKbhId();
		
	public void setKbhId(String kbhId);

	public ResultSet getKbh();
		
	public void setKbh(ResultSet kbh);

	public String getDvkdId();
		
	public void setDvkdId(String dvkdId);

	public ResultSet getDvkd();
		
	public void setDvkd(ResultSet dvkd);
	
	public ResultSet getTrungtamphanphoiList();
	
	public String getTtppId();
	
	public void setTtppId(String ttppId);

	public String[] getKvId();
		
	public void setKvId(String[] kvId);

	public ResultSet getKv();
		
	public void setKv(ResultSet kv);

	public String getTrangthai();
		
	public void setTrangthai(String trangthai);

	public String getMsg();
		
	public void setMsg(String msg);
		
	public String getUserId();
		
	public void setUserId(String userId);
	
	public String getThuviec();
	public void setThuviec(String thiec);
	
	public String getCmnd();
	public void setCmnd(String cmnd);	
	public String getNgaysinh();
	public void setNgaysinh(String ngaysinh);
	public String getQuequan();
	public void setQuequan(String quequan);
	public String getNgaybatdau();
	public void setNgaybatdau(String ngaybatdau);
	public String getNgayketthuc();
	public void setNgayketthuc(String ngayketthuc);
	

	public void init_New();
		
	public void init_Update();
	
	public boolean Save(HttpServletRequest request);
	
	public Hashtable<String, String> getHTKvId();
	
	public void DBClose();

	public String getBmId() ;
	public void setBmId(String bmId) ;
	public ResultSet getBmRs();
}
