package geso.dms.center.beans.hdnhaphang;


import java.io.Serializable;
import java.sql.ResultSet;

public interface IHDnhaphang extends Serializable {
	
	public String getUserId();

	public void setUserId(String userId);
	
	public String getNppId();
	
	public void setNppId(String id);

	public String getId();
	
	public void setId(String id);

	public String getSize();
	
	public void setSize(String size);
	
	public String getNgaychungtu();
	
	public void setNgaychungtu(String ngaychungtu);
	
	public String getNgaynhanhang();
	
	public void setNgaynhanhang(String ngaynhanhang);
		
	public String getSohoadon();
	
	public void setSohoadon(String sohoadon);

	public String getNguoitao();
	
	public void setNguoitao(String nguoitao);
	
	public String getNguoisua();
	
	public void setNguoisua(String nguoisua);
	
	public String getNppTen();
	
	public void setNppTen(String nppTen);
	
	public String getNccId();
	
	public void setNccId(String nccId);

	public String getDvkdId();
	
	public void setDvkdId(String dvkdId);

	public ResultSet getDvkdIds();
	
	public void setDvkdIds(ResultSet dvkdIds);

	public String getKbhId();
	
	public void setKbhId(String kbhId);

	public ResultSet getKbhIds();
	
	public void setKbhIds(ResultSet kbhIds);

	public ResultSet getKhoIds();

	public void setKhoIds(ResultSet khoIds);

	public String getKhoId();
	
	public void setKhoId(String khoId);
	
	public String getTongtienbVAT();

	public void setTongtienbVAT(String tongtienbVAT);
	
	public String getTongVat();
	
	public void setTongVat(String tongvat);
	
	public String getTongtienaVAT();
	
	public void setTongtienaVAT(String tongtienaVAT);
	
	public ResultSet getNcc();
	
	public ResultSet getGSBH();
	public String getGsbhId();
	public void setGsbhId(String gsbhid);
	
	public void setNcc(ResultSet ncc);
	
	public String[] getMasp();
	
	public void setMasp(String[] masp);
	
	public String[] getTensp();
	
	public void setTensp(String[] tensp);
	
	public String[] getSl();
	
	public void setSl(String[] sl);

	public String[] getGiamua();
	
	public void setGiamua(String[] giamua);
	
	public String[] getTienbVAT();
	
	public void setTienbVAT(String[] tienbVAT);

	public String[] getDonvi();
	
	public void setDonvi(String[] donvi);

	public String getMessage(); 
	
	public void setMessage(String msg); 
	
	//public boolean UpdateNhaphangNpp();
	
	public void init();
	public void DBclose();

}

