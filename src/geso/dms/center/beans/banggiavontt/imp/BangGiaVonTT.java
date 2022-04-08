package geso.dms.center.beans.banggiavontt.imp;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import geso.dms.center.beans.banggiavontt.IBangGiaVonTT;
import geso.dms.center.beans.banggiavontt.IBangGiaVonTT_SanPham;
import geso.dms.center.beans.phieunhapkhott.imp.PhieuNhapKhoTT_SanPham;
import geso.dms.distributor.db.sql.dbutils;

public class BangGiaVonTT implements IBangGiaVonTT {
    String Id;
    String NguoiTao;
    String NguoiSua;
    String NgayTao;
    String NgaySua;
    String DVKD_ID;
    String DVKD_TEN;
    String  Message="";
    String TenBangGia="";
    List<IBangGiaVonTT> listBangGia=new ArrayList<IBangGiaVonTT>();
    List<IBangGiaVonTT_SanPham> listbanggia_sanpham=new ArrayList<IBangGiaVonTT_SanPham>();
    public BangGiaVonTT(){
    	
    }
    public BangGiaVonTT(String id){
    	String sql_getdata="";
		 sql_getdata="select a.pk_seq,a.dvkd_fk,d.donvikinhdoanh  ,a.ngaytao,a.tenbanggia,a.ngaysua ,ns.ten as nguoisua,nt.ten as nguoitao "+
		" from banggiavontt a inner join nhanvien ns on ns.pk_seq=a.nguoisua inner join nhanvien nt on nt.pk_seq=a.nguoitao inner join donvikinhdoanh d on d.pk_seq =a.dvkd_fk where a.pk_seq ="+ id ;
		dbutils db=new dbutils();
		ResultSet rs_getbanggia=db.get(sql_getdata);
		if(rs_getbanggia!=null){
			try{
				while(rs_getbanggia.next()){
				
					this.setId(rs_getbanggia.getString("pk_seq"));
					this.setDvkdId(rs_getbanggia.getString("dvkd_fk"));
					this.setDvkdTen(rs_getbanggia.getString("donvikinhdoanh"));
					this.setNgaysua(rs_getbanggia.getString("ngaysua"));
					this.setNgaytao(rs_getbanggia.getString("ngaytao"));
					this.setNguoisua(rs_getbanggia.getString("nguoisua"));
					this.setNguoitao(rs_getbanggia.getString("nguoitao"));
					this.setNgaysua(rs_getbanggia.getString("ngaysua"));
					this.setTen(rs_getbanggia.getString("tenbanggia"));		
				}
			}catch(Exception er){
				
			}
		}
		Hashtable< String, IBangGiaVonTT_SanPham> htable=new Hashtable<String, IBangGiaVonTT_SanPham>();
		//Lay thong tin cua BangGiaVon chi tiet
		String sql_getdetail="select a.banggiavontt_fk,a.sanpham_fk,dvdl.donvi,a.giavon,a.chonban,b.ma,b.ten from banggiavontt_sanpham "+ 
		" a inner join sanpham b on a.sanpham_fk=b.pk_seq inner join donvidoluong dvdl on dvdl.pk_seq=b.dvdl_fk  where banggiavontt_fk="+id;
		//System.out.println("Error 64 line- BangGiaVonTT " + sql_getdetail.toString());
		ResultSet rs_getdetail=db.get(sql_getdetail);
		if(rs_getdetail!=null){
			try{
				while(rs_getdetail.next()){
					IBangGiaVonTT_SanPham banggia=new BangGiaVonTT_SanPham();
					banggia.setId(rs_getdetail.getString("banggiavontt_fk"));
					banggia.setChonBan(rs_getdetail.getString("chonban"));
					banggia.setGiaVon(rs_getdetail.getDouble("giavon"));
					//banggia.setMaSanPham(rs_getdetail.getString("ma"));
					//banggia.setTenSanPham(rs_getdetail.getString("ten"));
					//banggia.setDonViTinh(rs_getdetail.getString("donvi"));
					banggia.setGiaMoi(rs_getdetail.getDouble("giavon"));
					banggia.setSanPhamID(rs_getdetail.getString("sanpham_fk"));
					htable.put(rs_getdetail.getString("sanpham_fk"), banggia) ;
				}
			}catch(Exception er){
				System.out.println("Error 64 line- BangGiaVonTT " + er.toString());
			}
		}
		String sqlgetsp="select a.pk_seq,a.ten,d.donvi,ma from sanpham a inner join donvidoluong d on a.dvdl_fk=d.pk_seq where dvkd_fk="+this.DVKD_ID;
		ResultSet rs_getsanpham=db.get(sqlgetsp);
		if(rs_getsanpham!=null){
			try{
			while(rs_getsanpham.next()){
				 IBangGiaVonTT_SanPham sp = new BangGiaVonTT_SanPham();
				   sp.setDonViTinh(rs_getsanpham.getString("donvi"));
				   sp.setMaSanPham(rs_getsanpham.getString("ma"));
				   sp.setTenSanPham(rs_getsanpham.getString("ten"));
				   sp.setSanPhamID(rs_getsanpham.getString("pk_seq"));
				   
				   if(htable.containsKey(sp.getSanPhamID().trim())){
					  IBangGiaVonTT_SanPham spnew=htable.get(sp.getSanPhamID().trim());
					  sp.setGiaMoi(spnew.getGiaMoi());
					  sp.setGiaVon(spnew.getGiaVon());
					  sp.setChonBan(spnew.getChonBan());
				   }else{
					   sp.setGiaMoi(0);
					   sp.setGiaVon(0);
					   sp.setChonBan("0");
				   } 
				   this.listbanggia_sanpham.add(sp);
			}
			}catch(Exception er){
				System.out.println("BangGiaVonTT " +er.toString());
			}
			
		}
    }

    @Override
	public String getUserId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setUserId(String userId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return this.Id;
	}

	@Override
	public void setId(String id) {
		// TODO Auto-generated method stub
		this.Id=id;
	}

	@Override
	public String getTen() {
		// TODO Auto-generated method stub
		return this.TenBangGia;
	}

	@Override
	public void setTen(String ten) {
		// TODO Auto-generated method stub
		this.TenBangGia=ten;
	}

	@Override
	public String getDvkdTen() {
		// TODO Auto-generated method stub
		return this.DVKD_TEN;
	}

	@Override
	public void setDvkdTen(String dvkd) {
		// TODO Auto-generated method stub
		this.DVKD_TEN=dvkd;
	}

	@Override
	public String getDvkdId() {
		// TODO Auto-generated method stub
		return this.DVKD_ID;
	}

	@Override
	public void setDvkdId(String dvkdId) {
		// TODO Auto-generated method stub
		this.DVKD_ID=dvkdId;
	}

	@Override
	public String getNgaytao() {
		// TODO Auto-generated method stub
		return this.NgayTao;
	}

	@Override
	public void setNgaytao(String ngaytao) {
		// TODO Auto-generated method stub
		this.NgayTao=ngaytao;
	}

	@Override
	public String getNguoitao() {
		// TODO Auto-generated method stub
		return this.NguoiTao;
	}

	@Override
	public void setNguoitao(String nguoitao) {
		// TODO Auto-generated method stub
		this.NguoiTao=nguoitao;
	}

	@Override
	public String getNgaysua() {
		// TODO Auto-generated method stub
		return this.NgaySua;
	}

	@Override
	public void setNgaysua(String ngaysua) {
		// TODO Auto-generated method stub
		this.NgaySua=ngaysua;
	}

	@Override
	public String getNguoisua() {
		// TODO Auto-generated method stub
		return this.NguoiSua;
	}

	@Override
	public void setNguoisua(String nguoisua) {
		// TODO Auto-generated method stub
		this.NguoiSua=nguoisua;
	}

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return this.Message;
	}

	@Override
	public void setMessage(String msg) {
		// TODO Auto-generated method stub
		this.Message=msg;
	}

	@Override
	public void setListBangGia(String sql) {
		// TODO Auto-generated method stub
		String sql_getdata="";
		if(sql.equals("")){
		 sql_getdata="select a.pk_seq,a.dvkd_fk,d.donvikinhdoanh  ,a.ngaytao,a.tenbanggia,a.ngaysua ,ns.ten as nguoisua,nt.ten as nguoitao "+
		" from banggiavontt a inner join nhanvien ns on ns.pk_seq=a.nguoisua inner join nhanvien nt on nt.pk_seq=a.nguoitao inner join donvikinhdoanh d on d.pk_seq =a.dvkd_fk ";
		}else{
			sql_getdata=sql;
		}
		dbutils db=new dbutils();
		ResultSet rs_getbanggia=db.get(sql_getdata);
		if(rs_getbanggia!=null){
			try{
				while(rs_getbanggia.next()){
					IBangGiaVonTT banggia= new BangGiaVonTT();
					banggia.setId(rs_getbanggia.getString("pk_seq"));
					banggia.setDvkdId(rs_getbanggia.getString("dvkd_fk"));
					banggia.setDvkdTen(rs_getbanggia.getString("donvikinhdoanh"));
					banggia.setNgaysua(rs_getbanggia.getString("ngaysua"));
					banggia.setNgaytao(rs_getbanggia.getString("ngaytao"));
					banggia.setNguoisua(rs_getbanggia.getString("nguoisua"));
					banggia.setNguoitao(rs_getbanggia.getString("nguoitao"));
					banggia.setNgaysua(rs_getbanggia.getString("ngaysua"));
					banggia.setTen(rs_getbanggia.getString("tenbanggia"));
					this.listBangGia.add(banggia);
				}
				
			}catch(Exception er){
				
			}
		}
	}

	@Override
	public List<IBangGiaVonTT> getListBangGia() {
		// TODO Auto-generated method stub
		return this.listBangGia;
	}

	@Override
	public void setListBangGia_SanPham(List<IBangGiaVonTT_SanPham> list) {
		// TODO Auto-generated method stub
		this.listbanggia_sanpham= list;
	}

	@Override
	public List<IBangGiaVonTT_SanPham> getListBangGia_SanPham() {
		// TODO Auto-generated method stub
		return this.listbanggia_sanpham;
	}
	@Override
	public boolean SaveBangGiaVonTT() {
		dbutils db=new dbutils();	
		try
		{
			String sql_checkexitdvkd="select dvkd_fk from banggiavontt where dvkd_fk="+this.DVKD_ID;
			ResultSet rscheck =db.get(sql_checkexitdvkd);
			if(rscheck!=null){
				if(rscheck.next()){
					this.Message="DVDK Nay Da Co Bang Gia, Vui Long Vao Phan Sua Bang Gia De Sua";
					return false;
				}
			}
		db.getConnection().setAutoCommit(false);
		String sqlSaveBangGiaVon="INSERT INTO [Best].[dbo].[BANGGIAVONTT]([NGAYTAO] ,[NGAYSUA] ,[NGUOITAO] ,[NGUOISUA],[DVKD_FK],[TENBANGGIA]) VALUES "+
		"('"+this.NgayTao+"','"+this.NgaySua+"',"+this.NguoiTao+","+this.NguoiSua+","+this.DVKD_ID+",'"+this.TenBangGia+"')";
			if(!db.update(sqlSaveBangGiaVon)){
			System.out.println("sqlSavePhieuNhapKho : 146 PhieuNhapKhoTT " +sqlSaveBangGiaVon);
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return false;
		     }
		
		//thuc hien insert chi tiet
		String query = "select IDENT_CURRENT('BANGGIAVONTT') as pnkId";
		ResultSet rsDh = db.get(query);	
		try
		{
			rsDh.next();
		this.setId(rsDh.getString("pnkId"));
		}catch(Exception ex){
			return false;
		}
		int count=0;
		if(this.listbanggia_sanpham!=null){
			while (count <this.listbanggia_sanpham.size()){
				IBangGiaVonTT_SanPham sanpham=new BangGiaVonTT_SanPham();
				sanpham=this.listbanggia_sanpham.get(count);
				//Kiem tra san pham nay co ton tai trong bang sanpham khong 	
					String sql_insertdetail="insert into BANGGIAVONTT_SanPham([BANGGIAVONTT_FK],[SANPHAM_FK],[GIAVON],[CHONBAN]) values("+this.getId()+","+sanpham.getSanPhamID()+","+sanpham.getGiaMoi()+",'"+sanpham.getChonBan()+"')";						
					if(!db.update(sql_insertdetail)){
						geso.dms.center.util.Utility.rollback_throw_exception(db);
						this.Message="Ban Khong The Insert Ma San Pham : "+ sanpham.getMaSanPham()+" , Vui long kiem tra lai.";
						System.out.println("sql_insertdetail : 146 PhieuNhapKhoTT " +sql_insertdetail);
						return false;
					}
				count++;
			}
		}
		if(count==0){
			this.Message=" Khong the them moi don hang nay, chua co san pham nao duoc chon";
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return false;
			
		}
		db.getConnection().commit();
		db.getConnection().setAutoCommit(true);
		}catch(Exception er){
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			this.setMessage("Ban khong the them Phieu Nhap Kho nay, loi : " + er.toString());
			return false;
		}
		return true;
	}
	@Override
	public boolean EditBangGiaVonTT() {
		// TODO Auto-generated method stub
		dbutils db=new dbutils();	
		try
		{
		db.getConnection().setAutoCommit(false);
		String sqlSaveBangGiaVon="update  [Best].[dbo].[BANGGIAVONTT] set [NGAYSUA]='"+this.NgaySua+"',[NGUOISUA]="+this.NguoiSua+",[TENBANGGIA]='"+this.TenBangGia+"' where pk_seq="+this.Id;
			if(!db.update(sqlSaveBangGiaVon)){
			System.out.println("sqlSavePhieuNhapKho : 146 PhieuNhapKhoTT " +sqlSaveBangGiaVon);
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return false;
		     }
		
		//thuc hien insert chi tiet
		String query = "delete from banggiavontt_sanpham where banggiavontt_fk= "+this.Id;
		if(!db.update(query)){
			this.Message="Khong The Cap Nhat Bang Gia Moi";
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return false;
		     }
		
		int count=0;
		if(this.listbanggia_sanpham!=null){
			while (count <this.listbanggia_sanpham.size()){
				IBangGiaVonTT_SanPham sanpham=new BangGiaVonTT_SanPham();
				sanpham=this.listbanggia_sanpham.get(count);
				//Kiem tra san pham nay co ton tai trong bang sanpham khong 	
					String sql_insertdetail="insert into BANGGIAVONTT_SanPham([BANGGIAVONTT_FK],[SANPHAM_FK],[GIAVON],[CHONBAN]) values("+this.getId()+","+sanpham.getSanPhamID()+","+sanpham.getGiaMoi()+",'"+sanpham.getChonBan()+"')";						
					if(!db.update(sql_insertdetail)){
						geso.dms.center.util.Utility.rollback_throw_exception(db);
						this.Message="Ban Khong The Insert Ma San Pham : "+ sanpham.getMaSanPham()+" , Vui long kiem tra lai.";
						System.out.println("sql_insertdetail : 146 PhieuNhapKhoTT " +sql_insertdetail);
						return false;
					}
				count++;
			}
		}
		if(count==0){
			this.Message=" Khong the them moi don hang nay, chua co san pham nao duoc chon";
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return false;
			
		}
		db.getConnection().commit();
		db.getConnection().setAutoCommit(true);
		}catch(Exception er){
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			this.setMessage("Ban khong the them Phieu Nhap Kho nay, loi : " + er.toString());
			return false;
		}
		return true;
	}
    
	

}
