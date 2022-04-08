package geso.dms.distributor.beans.denghidathangnpp;

import java.sql.ResultSet;

public interface IDathangnpp {
	public String getUserId();
	public void setUserId(String userId);
		
	public String getId();
	public void setId(String id);

	public String getNgaydn();
	public void setNgaydn(String ngaydn);
	
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
	public void setSoluong(String [] soluong);
	public String[] getSoluong();
	public void setTonkho(String [] tonkho);
	public String[] getTonkho();
	public ResultSet getNcc();
	public void setNcc(ResultSet ncc);

	public String getMessage(); 	
	public void setMessage(String msg); 
   
	public void setMasp(String [] masp);
    public String[] getMasp();
    
    public void setDonvi(String [] donvi);
    public String[] getDonvi();
    
    public void setTongtien(String[] tongtien);
    public String[] getTongtien();
    
    public void setDongia(String[] dongia);
    public String[] getDongia();
    
	public void setdanhsach(ResultSet danhsach);
	public ResultSet getdanhsach();
	
	public void setTrangthai(String trangthai);
	public String getTrangthai();
	
	public void setGsbhId(String gsbhId);
	public String getGsbhId();
	
	public void setGsbh(ResultSet gsbh);
	public ResultSet getGsbh();
	
	public void init0();
	public void init1();
	public void init2();
    public boolean kiemtra_thieuhang();
    public boolean savedathang();
	public void DBclose();
}
