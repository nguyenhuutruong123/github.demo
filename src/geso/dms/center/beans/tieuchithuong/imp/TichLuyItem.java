package geso.dms.center.beans.tieuchithuong.imp;

import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import java.util.Hashtable;
import geso.dms.center.beans.tieuchithuong.*;
import geso.dms.center.db.sql.Idbutils;
import geso.dms.center.db.sql.dbutils;


public class TichLuyItem implements ITichLuyItem
{
	DecimalFormat df2 = new DecimalFormat("#,###,###,###");
	DecimalFormat df = new DecimalFormat("#,###,###,###.###");
	int muc  = 0;
	String diengiai = "";
	Double tumuc = 0.0;
	Double denmuc = 0.0;
	Double tratichluy = 0.0;
	int loaitra = 0  ; // 0 ck , 1 tien ,2 sanpham
	int hinhthuc  = 0; // 1 là bắt buôc, 0 bất kỳ trong
	List<Object[]> spList = new ArrayList(); // object { spid, soluong}
	ResultSet sanphamRs;
	
	
	
	public TichLuyItem()
	{
		
	}
	public void setMuc(int muc) {
		this.muc = muc;
		this.diengiai = "Mức " + (muc +1);
	}
	
	public String getDiengiai() {
		return diengiai;
	}
	
	public int getMuc() {
		return muc;
	}
	
	public Double getTumuc() {
		return tumuc;
	}
	public void setTumuc(Double tumuc) {
		this.tumuc = tumuc;
	}
	public String getTumucStr() {
		return tumuc > 0  ?  df2.format(tumuc) : "";
	}	
	public Double getDenmuc() {
		return denmuc;
	}
	public void setDenmuc(Double denmuc) {
		this.denmuc = denmuc;
	}
	public String getDenmucStr() {
		return denmuc > 0  ?  df2.format(denmuc) : "";
	}
	
	public Double getTratichluy() {
		return tratichluy;
	}
	public void setTratichluy(Double tratichluy) {
		this.tratichluy = tratichluy;
	}
	public String getTratichluyStr() {
		return tratichluy > 0  ?  df.format(tratichluy) : "";
	}	
	
	public int getLoaitra() {
		return loaitra;
	}
	public void setLoaitra(int loaitra) {
		this.loaitra = loaitra;
	}
	
	public int getHinhthuc() {
		return hinhthuc;
	}
	public void setHinhthuc(int hinhthuc) {
		this.hinhthuc = hinhthuc;
	}
	public List<Object[]>  getSpList() {
		return spList;
	}
	public void setSpList(List<Object[]>  hashSp) {
		this.spList = hashSp;
	}
	
	public void setSanPhamRs(Idbutils db)
	{
		String data = "";
		List<Object> list = new ArrayList<Object>();
		for(int i = 0; i < spList.size(); i++ )
		{			
			list.add(spList.get(i)[0] );
			if(data.trim().length() > 0 )
				data +=",?";
			else
				data= "?";
		}
		if(data.trim().length() > 0)
		{
			String query = " select ma,ten from sanpham where Ma in ("+data+")";
			this.sanphamRs = db.getByPreparedStatement(query, list);
			
		}

	}
	public ResultSet getSanPhamRs() {
		// TODO Auto-generated method stub
		return sanphamRs;
	}
	
	
}
