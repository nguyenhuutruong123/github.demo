package geso.dms.center.beans.duyetdontrahangnpp.imp;

import java.sql.ResultSet;
import java.sql.SQLException;

import geso.dms.center.beans.duyetdontrahangnpp.IDuyetdontrahangnpp;
import geso.dms.center.db.sql.dbutils;
import geso.dms.distributor.beans.donhangtrave.IDonhangtrave;

public class DuyetdontrahangnppDislay implements IDuyetdontrahangnpp {
    String ngay;
    String nppId;
    String ddkd;
    String khId;
    String tongtien;
    String chietkhau;
    String tongtienavat;
    String vat;
    String tongtienbvat;
    ResultSet sanpham;
    dbutils db;
    public DuyetdontrahangnppDislay(String id)
    {  db = new dbutils();
        this.tongtien = "0";
        this. tongtienavat = "0";
        this.tongtienbvat ="0";
        String npp="";
    	String sql =" select a.npp_fk,a.pk_seq as dhtvId, a.ngaynhap, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua, d.ten as khTen, d.pk_seq as khId, e.pk_seq as ddkdId, e.ten as ddkdTen, f.ten as nppTen, a.VAT,ck.chietkhau ";
			   sql = sql + " from donhangtrave a inner join nhanvien b on a.nguoitao = b.pk_seq "; 
               sql = sql + " inner join nhanvien c on a.nguoisua = c.pk_seq inner join khachhang d on a.khachhang_fk = d.pk_seq inner join daidienkinhdoanh e on a.ddkd_fk = e.pk_seq inner join nhaphanphoi f on a.npp_fk = f.pk_seq ";
               sql = sql + "inner join mucchietkhau ck on ck.pk_seq = d.chietkhau_fk ";
		       sql = sql + " where a.pk_seq = '"+ id +"' and a.trangthai = '1' or a.trangthai = '2' order by a.pk_seq ";
		       System.out.println(sql);
		       ResultSet rs = db.get(sql);
		       try {
				rs.next();
				this.ngay =  rs.getString("ngaynhap");
				this.ddkd =  rs.getString("ddkdTen");
				this.nppId = rs.getString("nppTen");
				this.khId = rs.getString("khTen");
				this.vat = rs.getString("vat");
				this.chietkhau = rs.getString("chietkhau");
				npp= rs.getString("npp_fk");
				sql = "	select b.pk_seq as spId, b.ma as spMa, b.ten as spTen, a.soluong, c.donvi, a.giamua as thanhtien, d.giabanlenpp as dongia,("+ this.chietkhau +" * a.giamua) /100 as chietkhau "; 
	            sql = sql + " from donhangtrave_sanpham a inner join sanpham b on a.sanpham_fk = b.pk_seq inner join donvidoluong c on b.dvdl_fk = c.pk_seq left join bgbanlenpp_sanpham d on b.pk_seq = d.sanpham_fk "; 
	            sql = sql + " where d.bgbanlenpp_fk = (select top(1) pk_seq from banggiabanlenpp where npp_fk = '"+ npp +"' order by NGAYTAO) and a.donhangtrave_fk = '"+ id +"'";
	            System.out.println(sql);
				this.sanpham = db.get(sql);
			} catch(Exception e) {
				e.printStackTrace();
			}
		       
		       
	    	
    }
	public void setNgay(String ngay) {
		
		this.ngay = ngay;
	}

	
	public String getNgay() {
		
		return this.ngay;
	}

	
	public void setnppId(String nppId) {
		
		this.nppId = nppId;
	}

	
	public String getnppId() {
		
		return this.nppId;
	}

	
	public String getddkdTen() {
		
		return this.ddkd;
	}

	
	public String getKahchhang() {
		
		return this.khId;
	}

	
	public String getTongtien() {
		
		return this.tongtien;
	}

	
	public String getchietkhau() {
		
		return this.chietkhau;
	}

	
	public String gettongbVat() {
		
		return this.tongtienbvat;
	}

	
	public String getvat() {
		
		return this.vat;
	}

	
	public String getavat() {
		
		return this.tongtienavat;
	}

	
	public void init() {
		
		
	}

	public ResultSet getsanpham() {
	
		return this.sanpham;
	}

}
