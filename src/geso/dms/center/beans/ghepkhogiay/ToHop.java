package geso.dms.center.beans.ghepkhogiay;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

public class ToHop {
	private float haoHut;
	private int soLuong;
	private float khoGiayNguyenLieu;
	private float dinhLuong;
	private String maySanXuatId;
	private Hashtable<Float , SanPham> sanPhamHash;//Key: kho giay
	
	public ToHop()
	{
		haoHut = 0;
		soLuong = 0;
		khoGiayNguyenLieu = 0;
		dinhLuong = 0;
		this.maySanXuatId = "";
		sanPhamHash = new Hashtable<Float, SanPham>();
	}

	public ToHop(ToHop toHop)
	{
		haoHut = toHop.getHaoHut();
		soLuong = toHop.getSoLuong();
		khoGiayNguyenLieu = toHop.getKhoGiayNguyenLieu();
		dinhLuong = toHop.getDinhLuong();
		this.maySanXuatId = toHop.getMaySanXuatId();
		sanPhamHash = new Hashtable<Float, SanPham>();
		Set<Float> keys = toHop.getSanPhamHash().keySet();
		for (float key : keys)
		{
			SanPham sp = new SanPham(toHop.getSanPhamHash().get(key));
			this.sanPhamHash.put(key, sp);
		}
	}
	
	public ToHop(float haoHut, int soLuong, float khoGiayNguyenLieu, float dinhLuong)
	{
		this.haoHut = haoHut;
		this.soLuong = soLuong;
		this.khoGiayNguyenLieu = khoGiayNguyenLieu;
		this.dinhLuong = dinhLuong;
		maySanXuatId = "";
		sanPhamHash = new Hashtable<Float, SanPham>();
	}
	
	public ToHop truSoLuong(int num)
	{
//		if (num > this.soLuong)
//			return false;
		ToHop toHop = new ToHop(this);
		toHop.setSoLuong(num);
		//Lay id cua nhung san pham bi chia ra do khac don hang
		List<String> ids = getIdListSpChia();
		List<String> l = new ArrayList<String>();
 		Set<Float> keys = this.sanPhamHash.keySet();
		this.soLuong = this.soLuong - num;
		//Tru so Luong cho nhung san pham khong bi chia the odon hang
		for (float key : keys)
		{
			SanPham sp = this.sanPhamHash.get(key);
			if (sp.getId().trim().equals("2036970") == true)
			{
				System.out.println("");
			}
			//Neu san pham khong bi chia theo don hang
			if (ids.contains(sp.getId()) == false)
			{
				sp.setSoLuong(sp.getSoLuong() - num * sp.getTiLe());
				toHop.getSanPhamHash().get(key).setSoLuong(num * sp.getTiLe());
			}
//			sp.setSoLuong(soLuong - num * sp.getTiLe()); 
		}
		List<Float> removeKeys = new ArrayList<Float>();
		//Tru so luong cho nhung san pham bi chia theo don hang
		for (String id : ids)
		{
//			int num1 = num;
			int num1 = this.soLuong;
			boolean isFirst = true;
			//Tru so Luong cho nhung san pham khong bi chia theodon hang
			for (float key : keys)
			{
				SanPham sp = this.sanPhamHash.get(key);
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
							int sl = toHop.getSanPhamHash().get(key).getSoLuong();
							toHop.getSanPhamHash().get(key).setSoLuong(sl - num1);
							num1 = 0;
						}else
						{
							num1 = num1 - sp.getSoLuong();
							sp.setSoLuong(0);
							removeKeys.add(key);
						}
					}
					else
					{
						sp.setSoLuong(0);
						removeKeys.add(key);
					}
				}
//				sp.setSoLuong(soLuong - num * sp.getTiLe()); 
			}
		}
		
		//Remove nhung san pham co so luong bang 0
		for (float key : removeKeys)
		{
			this.sanPhamHash.remove(key);
		}
		return toHop;
	}
	
	private List<String> getIdListSpChia()
	{
		List<String> ids = new ArrayList<String>();
		List<String> idsTmp = new ArrayList<String>();
		Set<Float> keys = this.sanPhamHash.keySet();
		for (float key : keys)
		{
			SanPham sanPham = this.sanPhamHash.get(key);
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
		Set<Float> sanPhamKeys = this.sanPhamHash.keySet();
		this.haoHut = 0;
		for (float khoGiay : sanPhamKeys)
		{
			this.haoHut += sanPhamHash.get(khoGiay).getKhoGiay() * sanPhamHash.get(khoGiay).getSoLuong();
		}
		this.haoHut = this.khoGiayNguyenLieu - this.haoHut;
	}
	
	public float getTrongLuong()
	{
		float trongLuong = 0;
		Set<Float> keys = this.sanPhamHash.keySet();
		for (float key : keys)
		{
			SanPham sp = this.sanPhamHash.get(key);
			trongLuong += sp.getTiLe() * (sp.getKhoGiay() * 7.5 + (sp.getDinhLuong() - 115));
		}
		return trongLuong;
	}
	
	public float getHaoHut() {
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

	public Hashtable<Float, SanPham> getSanPhamHash() {
		return sanPhamHash;
	}

	public void setSanPhamHash(Hashtable<Float, SanPham> sanPhamHash) {
		this.sanPhamHash = sanPhamHash;
	}

	public void setMaySanXuatId(String maySanXuatId) {
		this.maySanXuatId = maySanXuatId;
	}

	public String getMaySanXuatId() {
		return maySanXuatId;
	}
}