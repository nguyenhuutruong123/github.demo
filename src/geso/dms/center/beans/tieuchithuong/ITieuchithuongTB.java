package geso.dms.center.beans.tieuchithuong;

public interface ITieuchithuongTB 
{
	public String getUserId();
	public void setUserId(String userId);
	public String getId();
	public void setId(String id);
	public String getScheme();
	public void setScheme(String scheme);
	
	public String getThang();
	public void setThang(String thang);
	public String getNam();
	public void setNam(String nam);
	public String getDiengiai();
	public void setDiengiai(String diengiai);
	
	public String getMsg();
	public void setMsg(String msg);
	
    //Cac Muc
    public String[] getDiengiaiMuc();
    public void setDiengiaiMuc(String[] diengiai);
    public String[] getTumuc();
    public void setTumuc(String[] tumuc);
    public String[] getDenmuc();
    public void setDenmuc(String[] denmuc);
    public String[] getThuongSR();
    public void setThuongSR(String[] thuongSR);
    public String[] getThuongTDSR();
    public void setThuongTDSR(String[] thuongTDSR);
    public String[] getThuongSS();
    public void setThuongSS(String[] thuongSS);
    public String[] getThuongTDSS();
    public void setThuongTDSS(String[] thuongTDSS);
    public String[] getThuongASM();
    public void setThuongASM(String[] thuongASM);
    public String[] getThuongTDASM();
    public void setThuongTDASM(String[] thuongTDASM);
    
    //SCHEME
    public String[] getSchemeTB();
    public void setSchemeTB(String[] schemeTB);
    public String[] getSchemeDiengiai();
    public void setSchemeDiengiai(String[] schemeDG);
    
    
	public void init();
	public void createRs();
	
	
	public boolean createTctSKU();
	public boolean updateTctSKU();
	
	///////////////////////////
}
