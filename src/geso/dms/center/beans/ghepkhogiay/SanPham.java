package geso.dms.center.beans.ghepkhogiay;

public class SanPham {
	private String id;
	private String idDonHang;
	private float dinhLuong;
	private float khoGiay;
	private String tenKhachHang;
	private int soLuong;
	private int tiLe;
	private String ma;
	private String tenMaySanXuat;
	private String idNhanHang;
	private String tenNhanHang;
	private String chungLoai;
	
	public SanPham()
	{
		this.id =  "";
		this.idDonHang= "";
		this.dinhLuong = 0;
		this.khoGiay = 0;
		this.tenKhachHang = "";
		this.soLuong = 0; 
		this.tiLe = 0;
		this.ma = "";
		this.tenMaySanXuat = "";
		this.idNhanHang = "";
		this.tenNhanHang = "";
		this.chungLoai = "";
	}
	
	public SanPham(SanPham sp)
	{
		this.id = sp.id;
		this.idDonHang = sp.idDonHang;
		this.dinhLuong = sp.dinhLuong;
		this.khoGiay = sp.khoGiay;
		this.tenKhachHang = sp.tenKhachHang;
		this.soLuong = sp.soLuong; 
		this.tiLe = 0;
		this.tenMaySanXuat = sp.getTenMaySanXuat();
		this.idNhanHang = sp.getIdNhanHang();
		this.tenNhanHang = sp.getTenNhanHang();
		this.chungLoai = sp.getChungLoai();
		this.ma = sp.getMa();
	}

	//Kiem tra xem 2 san pham co cung id va co nam trong 1 don hang khong?
	public boolean compareId_IdDonHang(SanPham sp)
	{
		//So sanh id
		if (this.id.trim().equals(sp.getId()) == false)
			return false;
		if (this.idDonHang.trim().equals(sp.getIdDonHang()) == false)
			return false;
		return true;
	}
	
	public int compareKhoGiay(SanPham sp)
	{
		if (this.khoGiay < sp.getKhoGiay())
			return -1;
		if (this.khoGiay == sp.getKhoGiay())
			return 0;
		return 1;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIdDonHang() {
		return idDonHang;
	}
	public void setIdDonHang(String idDonHang) {
		this.idDonHang = idDonHang;
	}
	public float getDinhLuong() {
		return dinhLuong;
	}
	public void setDinhLuong(float dinhLuong) {
		this.dinhLuong = dinhLuong;
	}
	public float getKhoGiay() {
		return khoGiay;
	}
	public void setKhoGiay(float khoGiay) {
		this.khoGiay = khoGiay;
	}
	public String getTenKhachHang() {
		return tenKhachHang;
	}
	public void setTenKhachHang(String tenKhachHang) {
		this.tenKhachHang = tenKhachHang;
	}
	public int getSoLuong() {
		return soLuong;
	}
	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}
	public String getMa() {
		return ma;
	}
	public void setMa(String ma) {
		this.ma = ma;
	}

	public void setTiLe(int tiLe) {
		this.tiLe = tiLe;
	}

	public int getTiLe() {
		return tiLe;
	}
	
	public String getTenMaySanXuat() {
		return tenMaySanXuat;
	}

	public void setTenMaySanXuat(String tenMaySanXuat) {
		this.tenMaySanXuat = tenMaySanXuat;
	}

	public String getIdNhanHang() {
		return idNhanHang;
	}

	public void setIdNhanHang(String idNhanHang) {
		this.idNhanHang = idNhanHang;
	}

	public String getTenNhanHang() {
		return tenNhanHang;
	}

	public void setTenNhanHang(String tenNhanHang) {
		this.tenNhanHang = tenNhanHang;
	}

	public void setChungLoai(String chungLoai) {
		this.chungLoai = chungLoai;
	}

	public String getChungLoai() {
		return chungLoai;
	}
}