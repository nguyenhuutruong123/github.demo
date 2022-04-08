package geso.dms.distributor.beans.denghidathang;

import java.io.Serializable;
import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;

public interface IDenghidathang extends Serializable 
{
	public String getUserId();
	public void setUserId(String userId);
		
	public String getId();
	public void setId(String id);

	public String getSize();
	public void setSize(String size);
	
	public String getNgaydn();
	public void setNgaydn(String ngaydn);
	
	public String getSoct();
	public void setSoct(String soct);
	
	public String getNguoitao();
	public void setNguoitao(String nguoitao);
	
	public String getNguoisua();
	public void setNguoisua(String nguoisua);
	
	public String getNppTen();
	public void setNppTen(String nppTen);

	public String getNppId();	
	public void setNppId(String id);
	
	public String getNccId();
	public void setNccId(String nccId);

	public String getTanso();
	public void setTanso(String tanso);

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

	public String[] getMasp();
	public void setMasp(String[] masp);
	
	public String[] getTensp();
	public void setTensp(String[] tensp);
	
	public String[] getSl();
	public void setSl(String[] sl);
	
	public String[] getTienbVAT();
	public void setTienbVAT(String[] tienbVAT);

	public String getMessage(); 	
	public void setMessage(String msg); 

	public String getMaspstr();
	public void setMaspstr(String maspstr); 
	
	public String[] getDongia();
	public void setDongia(String[] dongia);

	public String[] getDonvi();	
	public void setDonvi(String[] donvi);

	public String[] getTondau();
	public void setTondau(String[] tondau);

	public String[] getToncuoi();
	public void setToncuoi(String[] toncuoi);

	public String[] getBan();
	public void setBan(String[] ban);

	public String[] getTbban();
	public void setTbban(String[] tbban);

	public String[] getTonngay();
	public void setTonngay(String[] tonngay);

	public String[] getDukien();
	public void setDukien(String[] dukien);

	public String[] getDadathang();	
	public void setDadathang(String[] dadathang);
	
	public String[] getConlai();
	public void setConlai(String[] conlai);
	public String getQueryStr();
	public void init0();
	public void init1();
	public void init2();
	public void DBclose();
	public String displayfile(String id,String userId,int loai);
	public int luufile(String id,String name,String userId,int loai);
	
	}
