package geso.dms.distributor.beans.ctkhuyenmai.imp;

import java.util.List;

import geso.dms.distributor.beans.ctkhuyenmai.ICtkhuyenmai;
import geso.dms.distributor.beans.dieukienkhuyenmai.IDieukienkhuyenmai;
import geso.dms.distributor.beans.trakhuyenmai.ITrakhuyenmai;
import geso.dms.distributor.beans.trakhuyenmai.imp.Trakhuyenmai;

public class Ctkhuyenmai implements ICtkhuyenmai 
{
	private static final long serialVersionUID = -9217977546733610214L;
	
	// Huy chỉnh nếu đụng bằng 1 thì qua trang KM mới cho enable điều chỉnh
	public String dungsanpham="0";
	
	private String id;
	private String scheme;
	private String tungay;
	private String denngay;
	private String diengiai;
	private String khoId;
	
	private int loaict;
	private float ngansach;
	private float dasudung;
	private int soxuatKM; //luu so xuat khuyen mai duoc huong theo chuong trinh nay
	
	private long tongtientheodkkm;
	
	private boolean confirm; //check ctkm co thoa ngan sach, dong thoi ko dung sp su dung o cac dkkm khac
	private boolean tra_OR;
	
	private String hinhthucPrimary;
	
	private List<IDieukienkhuyenmai> dkkhuyenmai; //dkkhuyenmai cua chuong trinh
	private List<ITrakhuyenmai> trakhuyenmai; //trakhuyenmai theo chuong trinh
	
	double so_suat_toi_da = 999999;
	public double getSo_suat_toi_da() {
		return so_suat_toi_da;
	}
	public void setSo_suat_toi_da(double so_suat_toi_da) {
		this.so_suat_toi_da = so_suat_toi_da;
	}
	int loaingansach = 0;
	public int getLoaingansach() {
		return loaingansach;
	}
	public void setLoaingansach(int loaingansach) {
		this.loaingansach = loaingansach;
	}
	
	public Ctkhuyenmai()
	{
		this.id = "";
		this.scheme = "";
		this.tungay = "";
		this.denngay = "";
		this.khoId = "";
		this.loaict = 1; //binh thuong
		this.ngansach = 0;
		this.dasudung = 0;
		this.soxuatKM = 0;
		this.diengiai = "";
		this.tongtientheodkkm = 0;
		this.hinhthucPrimary = "0";
		this.tra_OR = false;
		this.confirm = false;
	}
	
	public Ctkhuyenmai(String id, String scheme, String diengiai, String tungay, String denngay, int loaict, float ngansach, float dasudung, int soxuatKM)
	{
		this.id = id;
		this.scheme = scheme;
		this.tungay = tungay;
		this.denngay = denngay;
		this.loaict = loaict;
		this.ngansach = ngansach;
		this.dasudung = dasudung;
		this.soxuatKM = soxuatKM;
		this.diengiai = diengiai;
		this.khoId = "";
		this.hinhthucPrimary = "0";
		this.tra_OR = false;
		this.confirm = false;
	}
	
	
	
	public String getId()
	{
		return this.id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public String getscheme()
	{
		return this.scheme;
	}
	public void setscheme(String scheme)
	{
		this.scheme = scheme;
	}
	public String getTungay()
	{
		return this.tungay;
	}
	public void setTungay(String tungay)
	{
		this.tungay = tungay;
	}
	public String getDenngay()
	{
		return this.denngay;
	}
	public void setDenngay(String denngay)
	{
		this.denngay = denngay;
	}
	public int getLoaict()
	{
		return this.loaict;
	}
	public void setLoaict(int loaict)
	{
		this.loaict = loaict;
	}
	public float getNgansach()
	{
		return this.ngansach;
	}
	public void setNgansach(float ngansach)
	{
		this.ngansach = ngansach;
	}
	public float getDasudung()
	{
		return this.dasudung;
	}
	public void setDasudung(float dasudung)
	{
		this.dasudung = dasudung;
	}
	public List<IDieukienkhuyenmai> getDkkhuyenmai()
	{
		return this.dkkhuyenmai;
	}
	public void setDkkhuyenmai(List<IDieukienkhuyenmai> dkkm)
	{
		this.dkkhuyenmai = dkkm;
	}
	public List<ITrakhuyenmai> getTrakhuyenmai()
	{
		return this.trakhuyenmai;
	}
	public void setTrakhuyenmai(List<ITrakhuyenmai> trakm)
	{
		this.trakhuyenmai = trakm;
	}

	public int getSoxuatKM() 
	{
		return this.soxuatKM;
	}

	public void setSoxuatKM(int soxuatKM) 
	{
		this.soxuatKM = soxuatKM;
	}

	public boolean getConfirm() 
	{
		return this.confirm;
	}

	public void setConfirm(boolean confirm)
	{
		this.confirm = confirm;
	}

	//kiem tra ngansach thoa duoc bao nhieu xuat
	public int checkCtkm(float tonggiatri) 
	{
		List<ITrakhuyenmai> trakm = this.getTrakhuyenmai();
		int sx = this.soxuatKM;
		
		/*List<ITrakhuyenmai> trakm = this.getTrakhuyenmai();
		int sx = this.soxuatKM;
		
		System.out.println("Tra khuyen mai size la: " + trakm.size() + "\n");
		//check ngansach
		for(int i = 0; i < trakm.size(); i++)
		{
			Trakhuyenmai tkm = (Trakhuyenmai)trakm.get(i);
			System.out.println("[1:Tien,2 CK,3 sp][TraID]"+tkm.getId()+"[TraType]"+tkm.getType() +"[tkm.getHinhthuc()]"+tkm.getHinhthuc()  );
			if(tkm.getType() == 1) //tra tien
			{ 
				float sum = this.soxuatKM * tkm.getTongtien();
				if(sum > (this.ngansach - this.dasudung))
					sx = (int) ((this.soxuatKM * (this.ngansach - this.dasudung)) / sum);
			}
			if(tkm.getType() == 2) //tra chiet khau
			{
				float sum = tkm.getChietkhau() * tonggiatri / 100;
				if(sum > (this.ngansach - this.dasudung))
					sx = -1;
			}
			
			if(tkm.getType() == 3) //san pham
			{
				if(tkm.getHinhthuc() == 1) //chua chon san pham thi khong can biet tong ngan sach
				{
					float tongGtriKm = tkm.getTongGtriKm();
					float sum = this.soxuatKM * tongGtriKm;
					
					System.out.println("[soxuatKM]"+soxuatKM+"[tongGtriKm]"+tongGtriKm +"[ToTal]"+sum);
					if(sum > (this.ngansach - this.dasudung))
					{
						sx = (int) ((this.soxuatKM * (this.ngansach - this.dasudung)) / sum);	
					}
					System.out.println("So xuat khuyen mai la: " + sx + "\n");
				}
			}
		}*/
		return sx;
	}

	public String getDiengiai()
	{
		return this.diengiai;
	}

	public void setDiengiai(String diengiai) 
	{
		this.diengiai = diengiai;
	}

	public long getTongTienTheoDKKM() 
	{
		return this.tongtientheodkkm;
	}

	public void setTongTienTheoDKKM(long tongtien)
	{
		this.tongtientheodkkm = tongtien;
	}
	
	public String getKhoId() 
	{
		return this.khoId;
	}

	public void setKhoId(String khoId) 
	{
		this.khoId = khoId;
	}

	public String getHinhthucPrimary() 
	{
		return this.hinhthucPrimary;
	}

	public void setHinhthucPrimary(String hinhthucPrimary) 
	{
		this.hinhthucPrimary = hinhthucPrimary;
	}

	public void setTra_OR(boolean tra_OR)
	{
		this.tra_OR = tra_OR;
	}

	public boolean getTra_OR() 
	{
		return this.tra_OR;
	}
	
	String phanbotheoDH="";
	public String getPhanbotheoDH() {

		return this.phanbotheoDH;
	}


	public void setPhanbotheoDH(String phanbotheoDH) {
		
		this.phanbotheoDH = phanbotheoDH;
	}
	float chietkhau;

	public float getCK() {

		return this.chietkhau;
	}


	public void setCK(float ck) {
	
		this.chietkhau = ck;
	}

	public String getDungsanpham() {
		return dungsanpham;
	}
	public void setDungsanpham(String dungsanpham) {
		this.dungsanpham = dungsanpham;
	}
	
}
