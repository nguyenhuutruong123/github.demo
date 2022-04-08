package geso.dms.center.beans.ghepkhogiay;

import java.util.Hashtable;

public class KichBan {
	private float tongHaoHut;
	private Hashtable<Float, ToHop> toHopHash;//Key: so tang dan
	private float increateNum;
	public KichBan()
	{
		tongHaoHut = 0;
		toHopHash = new Hashtable<Float, ToHop>();
		increateNum = 0;
	}
	
	public void addToHop(ToHop toHop)
	{
		this.toHopHash.put(increateNum++, toHop);
		this.tongHaoHut += toHop.getHaoHut() * toHop.getSoLuong();
	}
	
	public float getTongHaoHut() {
		return tongHaoHut;
	}
	public void setTongHaoHut(float tongHaoHut) {
		this.tongHaoHut = tongHaoHut;
	}
	public Hashtable<Float, ToHop> getToHopHash() {
		return toHopHash;
	}
	public void setToHopHash(Hashtable<Float, ToHop> toHopHash) {
		this.toHopHash = toHopHash;
	}
}