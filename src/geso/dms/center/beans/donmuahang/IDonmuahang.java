package geso.dms.center.beans.donmuahang;

import java.io.Serializable;
import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;

public interface IDonmuahang extends Serializable 
{
	public String getUserId();
	public void setUserId(String userId);

	public String getNppId();
	public void setNppId(String id);
		
	public String getId();
	public void setId(String id);

	public String getNppTen();
	public void setNppTen(String ten);

	public String getSize();
	public void setSize(String size);
	
	public String getNgaydh();
	public void setNgaydh(String ngaydh);
	
	public String getSoct();
	public void setSoct(String soct);
	
	public String getNguoitao();
	public void setNguoitao(String nguoitao);
	
	public String getNguoisua();
	public void setNguoisua(String nguoisua);
	
	public String getTen();
	public void setTen(String ten);
	
	public String getNccId();
	public void setNccId(String nccId);

	public ResultSet getDvkdIds();
	public void setDvkdIds(ResultSet dvkdIds);

	public String getKbhId();
	public void setKbhId(String kbhId);

	public ResultSet getKbhIds();	
	public void setKbhIds(ResultSet kbhIds);
	
	public String getTongtienbVAT();
	public void setTongtienbVAT(String tongtienbVAT);
	
	public String getVat();
	public void setVat(String vat);
	
	public String getTongtienaVAT();
	public void setTongtienaVAT(String tongtienaVAT);

	public String getDvkdId();
	public void setDvkdId(String dvkdId);
	
	public ResultSet getNcc();
	public void setNcc(ResultSet ncc);

	public String[] getSpId();
	public void setSpId(String[] spId);
	
	public String[] getMasp();
	public void setMasp(String[] masp);
	
	public String[] getTensp();
	public void setTensp(String[] tensp);
	
	public String[] getSl();
	public void setSl(String[] sl);
	
	public String[] getSle();
	public void setSle(String[] sle);
	
	public String[] getTienbVAT();
	public void setTienbVAT(String[] tienbVAT);

	public String getMessage(); 	
	public void setMessage(String msg); 

	public String getMaspstr();
	public void setMaspstr(String maspstr); 
	
	public String[] getDongia();
	public void setDongia(String[] dongia);

	public String[] getDonvi();
	public String[] getQuycach();
	public void setQuycach(String[] qc);

	public void setDonvi(String[] donvi);
	
	public void init();
	public void initDisplay();
	
	public boolean UpdateDdhnpp(HttpServletRequest request);
	public boolean ChotDdhnpp (HttpServletRequest request);
	public void DBclose();
	}
