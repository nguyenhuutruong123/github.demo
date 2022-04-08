package geso.dms.center.beans.ghepkhogiay;

import java.util.ArrayList;
import java.util.List;

public class ToHopN {
	private String id;
	private float haoHut;
	private int soLuong;
	private float khoGiayNguyenLieu;
	private float dinhLuong;
	private String maySanXuatId;
	private List<SanPham> sanPhamList;
	
	public ToHopN()
	{
		this.id = "";
		haoHut = 0;
		soLuong = 0;
		khoGiayNguyenLieu = 0;
		dinhLuong = 0;
		this.maySanXuatId = "";
		this.sanPhamList = new ArrayList<SanPham>();
	}

	public ToHopN(ToHopN toHop)
	{
		this.id = toHop.getId();
		haoHut = toHop.getHaoHut();
		soLuong = toHop.getSoLuong();
		khoGiayNguyenLieu = toHop.getKhoGiayNguyenLieu();
		dinhLuong = toHop.getDinhLuong();
		this.maySanXuatId = toHop.getMaySanXuatId();
		this.sanPhamList = new ArrayList<SanPham>();
		for (SanPham sp : toHop.getSanPhamList())
		{
			SanPham sanPham = new SanPham(sp);
			this.sanPhamList.add(sanPham);
		}
	}
	
	public ToHopN(float haoHut, int soLuong, float khoGiayNguyenLieu, float dinhLuong)
	{
		this.id = "";
		this.haoHut = haoHut;
		this.soLuong = soLuong;
		this.khoGiayNguyenLieu = khoGiayNguyenLieu;
		this.dinhLuong = dinhLuong;
		maySanXuatId = "";
		this.sanPhamList = new ArrayList<SanPham>();
	}
	
	public SanPham getSanPhamById_Kh_Dh(String id, String tenKhachHang, String idDonHang)
	{
		for (SanPham sp : this.sanPhamList)
		{
			if (sp.getId().trim().equals(id) == true
			&& sp.getTenKhachHang().trim().equals(tenKhachHang) == true
			&& sp.getIdDonHang().trim().equals(idDonHang) == true)
				return sp;
		}
		return null;
	}
	
	public ToHopN truSoLuong(int num)
	{
		ToHopN toHop = new ToHopN(this);
		toHop.setSoLuong(num);
		//Lay id cua nhung san pham bi chia ra do khac don hang
		List<String> ids = getIdListSpChia();
// 		Set<Float> keys = this.sanPhamHash.keySet();
		this.soLuong = this.soLuong - num;
		//Tru so Luong cho nhung san pham khong bi chia the odon hang
		for (SanPham sp : this.sanPhamList)
		{
			if (sp.getId().trim().equals("2036970") == true)
			{
				System.out.println("");
			}
			//Neu san pham khong bi chia theo don hang
			if (ids.contains(sp.getId()) == false)
			{
				sp.setSoLuong(sp.getSoLuong() - num * sp.getTiLe());
				SanPham spTmp = toHop.getSanPhamById_Kh_Dh(sp.getId(), sp.getTenKhachHang(), sp.getIdDonHang());
				spTmp.setSoLuong(num * sp.getTiLe());
//				toHop.getSanPhamHash().get(key).setSoLuong(num * sp.getTiLe());
			}
//			sp.setSoLuong(soLuong - num * sp.getTiLe()); 
		}
		List<SanPham> removeObjects = new ArrayList<SanPham>();
		//Tru so luong cho nhung san pham bi chia theo don hang
		for (String id : ids)
		{
//			int num1 = num;
			int num1 = this.soLuong;
			boolean isFirst = true;
			//Tru so Luong cho nhung san pham khong bi chia theodon hang
			for (SanPham sp : this.sanPhamList)
			{
				//Neu san pham khong bi chia theo don hang
				if (sp.getId().trim().equals(id) == true)
				{
					if (num1 > 0)
					{
						if (isFirst == true){
							isFirst = false;
							num1 = num1 * sp.getTiLe();
						}
						if (sp.getSoLuong() >= num1)//Truong hop san pham co so luong lon hon so luong can tru
						{
							sp.setSoLuong(num1);
							SanPham spTmp = toHop.getSanPhamById_Kh_Dh(sp.getId(), sp.getTenKhachHang(), sp.getIdDonHang());
							int sl = spTmp.getSoLuong();
							spTmp.setSoLuong(sl - num1);
//							int sl = toHop.getSanPhamHash().get(key).getSoLuong();
//							toHop.getSanPhamHash().get(key).setSoLuong(sl - num1);
							num1 = 0;
						}else
						{
							num1 = num1 - sp.getSoLuong();
							sp.setSoLuong(0);
							removeObjects.add(sp);
						}
					}
					else
					{
						sp.setSoLuong(0);
						removeObjects.add(sp);
					}
				}
			}
		}
		
		//Remove nhung san pham co so luong bang 0
		this.sanPhamList.removeAll(removeObjects);
		return toHop;
	}
	
	public String getTenMaySanXuatSp()
	{
		for (SanPham sp : this.sanPhamList)
			return sp.getTenMaySanXuat();
		return null;
	}
	
	private List<String> getIdListSpChia()
	{
		List<String> ids = new ArrayList<String>();
		List<String> idsTmp = new ArrayList<String>();
		for (SanPham sanPham : this.sanPhamList)
		{
			if (sanPham.getId().trim().equals("2036970") == true)
			{
				System.out.println();
			}
			if (idsTmp.contains(sanPham.getId()) == false)
				idsTmp.add(sanPham.getId());
			else
				if (ids.contains(sanPham.getId()) == false)
					ids.add(sanPham.getId());
		}
		
		return ids;
	}
	
	public void tinhHaoHut()
	{
		this.haoHut = 0;
		for (SanPham sp : this.sanPhamList)
		{
			this.haoHut += sp.getKhoGiay() * sp.getSoLuong();
		}
		this.haoHut = this.khoGiayNguyenLieu - this.haoHut;
	}
	
	public float getTrongLuong()
	{
		float trongLuong = 0;
		for (SanPham sp : this.sanPhamList)
		{
			trongLuong += sp.getTiLe() * (sp.getKhoGiay() * 7.5 + (sp.getDinhLuong() - 115));
		}
		return trongLuong;
	}
	
	public float getHaoHut() {
		tinhHaoHut();
		return haoHut;
	}

	public void setHaoHut(float haoHut) {
		this.haoHut = haoHut;
	}

	public int getSoLuong() {
		return soLuong;
	}

	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}

	public float getKhoGiayNguyenLieu() {
		return khoGiayNguyenLieu;
	}

	public void setKhoGiayNguyenLieu(float khoGiayNguyenLieu) {
		this.khoGiayNguyenLieu = khoGiayNguyenLieu;
		tinhHaoHut();
	}

	public float getDinhLuong() {
		return dinhLuong;
	}

	public void setDinhLuong(float dinhLuong) {
		this.dinhLuong = dinhLuong;
	}

	public void setMaySanXuatId(String maySanXuatId) {
		this.maySanXuatId = maySanXuatId;
	}

	public String getMaySanXuatId() {
		return maySanXuatId;
	}

	public void setSanPhamList(List<SanPham> sanPhamList) {
		this.sanPhamList = sanPhamList;
	}

	public List<SanPham> getSanPhamList() {
		return sanPhamList;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}
}