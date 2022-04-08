package geso.dms.center.beans.khott.imp;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import geso.dms.center.beans.khott.ICapNhatGiaKhoTT;
import geso.dms.center.beans.khott.ITonKhoTT;
import geso.dms.distributor.db.sql.dbutils;

public class CapNhatGiaKhoTT implements ICapNhatGiaKhoTT{

	String SanPhamId="";
	String TenSanPham="";
	String MaSanPham="";
	String KhoTTId="";
	String TenKhoTT="";
	double GiaGoc=0;
	double GiaMoi=0;
	String Message="";
	List<ICapNhatGiaKhoTT> listCapNhatGia=new ArrayList<ICapNhatGiaKhoTT>();
	@Override
	public void setSanPhamId(String sanphamid) {
		// TODO Auto-generated method stub
	this.SanPhamId=sanphamid;
	}

	@Override
	public String getSanPhamId() {
		// TODO Auto-generated method stub
		return this.SanPhamId;
	}

	@Override
	public void setTenSanPham(String tensanpham) {
		// TODO Auto-generated method stub
		this.TenSanPham=tensanpham;
		
	}

	@Override
	public String getTenSanPham() {
		// TODO Auto-generated method stub
		return this.TenSanPham;
	}

	@Override
	public void setMaSanPham(String masanpham) {
		// TODO Auto-generated method stub
		this.MaSanPham=masanpham;
	}

	@Override
	public String getMaSanPham() {
		// TODO Auto-generated method stub
		return this.MaSanPham;
	}

	@Override
	public void setKhoTTId(String khottid) {
		// TODO Auto-generated method stub
		this.KhoTTId=khottid;
	}

	@Override
	public String getKhoTTId() {
		// TODO Auto-generated method stub
		return this.KhoTTId;
	}

	@Override
	public void setTenKhoTT(String tenkhott) {
		// TODO Auto-generated method stub
		this.TenKhoTT=tenkhott;
	}

	@Override
	public String getTenKhoTT() {
		// TODO Auto-generated method stub
		return this.TenKhoTT;
	}

	@Override
	public void setGiaGoc(double giagoc) {
		// TODO Auto-generated method stub
		this.GiaGoc=giagoc;
	}

	@Override
	public double getGiaGoc() {
		// TODO Auto-generated method stub
		return this.GiaGoc;
	}

	@Override
	public void setGiaMoi(double giamoi) {
		// TODO Auto-generated method stub
		this.GiaMoi=giamoi;
	}

	@Override
	public double getGiaMoi() {
		// TODO Auto-generated method stub
		return this.GiaMoi;
	}

	

	@Override
	public List<ICapNhatGiaKhoTT> getListTonKhoTT() {
		// TODO Auto-generated method stub
		return listCapNhatGia ;
	}

	@Override
	public void setListCapNhatGiaBySql(String sql_getdata) {
		// TODO Auto-generated method stub
		dbutils db=new dbutils();
		ResultSet rs_getdata=	db.get(sql_getdata);
		listCapNhatGia.clear();
		if(rs_getdata!=null){
			try{
				while(rs_getdata.next()){
					ICapNhatGiaKhoTT tonkho=new CapNhatGiaKhoTT();
					tonkho.setKhoTTId(rs_getdata.getString("khott_fk"));
					tonkho.setMaSanPham(rs_getdata.getString("ma"));
					tonkho.setTenSanPham(rs_getdata.getString("ten"));
					tonkho.setSanPhamId(rs_getdata.getString("sanpham_fk"));
					tonkho.setGiaGoc(rs_getdata.getDouble("giagoc"));
					tonkho.setGiaMoi(rs_getdata.getDouble("giamoi"));
					listCapNhatGia.add(tonkho);	
				}
				
			}catch(Exception er){
				System.out.println(er.toString());
			}
		}
	}

	@Override
	public void setListTonKhoTT(List<ICapNhatGiaKhoTT> list) {
		// TODO Auto-generated method stub
		this.listCapNhatGia=list;
	}

	@Override
	public boolean SaveBangGia() {
		// TODO Auto-generated method stub
		dbutils db=new dbutils();
		try	{
			db.getConnection().setAutoCommit(false);
		int m=0;
		int size= this.listCapNhatGia.size();
		while(m<size){
			ICapNhatGiaKhoTT obj=this.listCapNhatGia.get(m);
			String sql="update khott_sp set giagoc="+obj.getGiaMoi()+" where sanpham_fk="+obj.getSanPhamId()+" and khott_fk="+this.KhoTTId;
			if(!db.update(sql)){
				this.Message="Khong The Cap Nhat Bang Gia Moi, Loi Tai Ma San Pham :"+ obj.getMaSanPham();
				return false;
			}
			m++;
		}
		db.getConnection().commit();
		db.getConnection().setAutoCommit(true);
		
		}catch(Exception er){
			
			return false;
		}
		return true;
	}

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return this.Message;
	}

	@Override
	public void setMessage(String message) {
		// TODO Auto-generated method stub
		this.Message=message;
	}

}
