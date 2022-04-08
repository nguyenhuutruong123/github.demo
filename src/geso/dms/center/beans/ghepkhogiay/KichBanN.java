package geso.dms.center.beans.ghepkhogiay;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class KichBanN {
	private float tongHaoHut;
	private List<ToHopN> toHopList;
	public KichBanN()
	{
		tongHaoHut = 0;
		this.toHopList = new ArrayList<ToHopN>();
	}
	
	public void addToHop(ToHopN toHop)
	{
		this.toHopList.add(toHop);
		this.tongHaoHut += toHop.getHaoHut() * toHop.getSoLuong();
	}
	
	public float getTongHaoHut() {
		return tongHaoHut;
	}
	public void setTongHaoHut(float tongHaoHut) {
		this.tongHaoHut = tongHaoHut;
	}

	public void setToHopList(List<ToHopN> toHopList) {
		this.toHopList = toHopList;
	}

	public List<ToHopN> getToHopList() {
		return toHopList;
	}
}