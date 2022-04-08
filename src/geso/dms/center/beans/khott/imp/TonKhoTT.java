package geso.dms.center.beans.khott.imp;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import geso.dms.center.beans.khott.ITonKhoTT;
import geso.dms.distributor.db.sql.dbutils;

public class TonKhoTT implements ITonKhoTT {

	String SanPhamId="";
	String TenSanPham="";
	String MaSanPham="";
	String KhoTTId="";
	String TenKhoTT="";
	int SoLuong=0;
	int Booked=0;
	int Available=0;
	double GiaGoc=0;
	ResultSet dvkd;
	String dvkdId="";
	List<ITonKhoTT> listTonkhoTT=new ArrayList<ITonKhoTT>();
	dbutils db ;
	public TonKhoTT()
	{
		db = new dbutils();
		String sql ="select * from donvikinhdoanh";
		dvkd = db.get(sql);
	}
	public void setSanPhamId(String sanphamid) {
		
		this.SanPhamId=sanphamid;
	}

	
	public String getSanPhamId() {
		
		return this.SanPhamId;
	}

	
	public void setTenSanPham(String tensanpham) {
		
		this.TenSanPham=tensanpham;
	}

	
	public String getTenSanPham() {
		
		return this.TenSanPham;
	}

	
	public void setMaSanPham(String masanpham) {
		
		this.MaSanPham=masanpham;
	}

	
	public String getMaSanPham() {
		
		return this.MaSanPham;
	}

	
	public void setKhoTTId(String khottid) {
		
		this.KhoTTId=khottid;
	}

	
	public String getKhoTTId() {
		
		return this.KhoTTId;
	}

	
	public void setTenKhoTT(String tenkhott) {
		
		this.TenKhoTT=tenkhott;
	}

	
	public String getTenKhoTT() {
		
		return this.TenKhoTT;
	}

	
	public void setSoLuong(int soluong) {
		
		this.SoLuong=soluong;
	}

	
	public int getSoluong() {
		
		return this.SoLuong;
	}

	
	public void setBooked(int booked) {
		
		this.Booked=booked;
	}

	
	public int getBooked() {
		
		return this.Booked;
	}

	
	public void setAvailable(int available) {
		
		this.Available=available;
	}

	
	public int getAvailable() {
		
		return this.Available;
	}

	
	public void setListTonKhoTT(String sql) {
		
		String sql_getdata="";
		if(sql.equals("")){
			sql_getdata="select a.kho,a.masp,a.stock,a.booked,a.available,s.ten,s.pk_seq from tonkhoicp a inner join sanpham s on s.ma=a.masp order by a.kho ";
		}else{
			sql_getdata=sql;
		}
		dbutils db=new dbutils();
		ResultSet rs_getdata=	db.get(sql_getdata);
		if(rs_getdata!=null){
			try{
				while(rs_getdata.next()){
					ITonKhoTT tonkho=new TonKhoTT();
					tonkho.setKhoTTId(rs_getdata.getString("kho"));
					tonkho.setMaSanPham(rs_getdata.getString("masp"));
					tonkho.setTenSanPham(rs_getdata.getString("ten"));
					tonkho.setSanPhamId(rs_getdata.getString("pk_seq"));
					tonkho.setSoLuong(rs_getdata.getInt("stock"));
					tonkho.setAvailable(rs_getdata.getInt("available"));
					tonkho.setBooked(rs_getdata.getInt("booked"));
					listTonkhoTT.add(tonkho);	
				}
				
			}catch(Exception er){
				System.out.println(er.toString());
			}
		}
		}

	
	public List<ITonKhoTT> getListTonKhoTT() {
		
		return listTonkhoTT;
	}

	
	public void setGiaGoc(double giagoc) {
		
		this.GiaGoc=giagoc;
	}

	
	public double getGiaGoc() {
		
		return this.GiaGoc;
	}
	public void setdvkdId(String dvkdId) {
		
		this.dvkdId = dvkdId;
	}
	public String getdvkdId() {
		
		return this.dvkdId;
	}

	public void setdvkd(ResultSet dvkd) {
		
		this.dvkd = dvkd;
	}
	public ResultSet getdvkd() {
		
		return this.dvkd;
	}

}
