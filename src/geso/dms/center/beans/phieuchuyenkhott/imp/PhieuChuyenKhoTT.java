package geso.dms.center.beans.phieuchuyenkhott.imp;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import geso.dms.center.beans.phieuchuyenkhott.IPhieuChuyenKhoTT;
import geso.dms.center.beans.phieuchuyenkhott.IPhieuChuyenKhoTT_SanPham;
import geso.dms.distributor.db.sql.dbutils;

public class PhieuChuyenKhoTT implements IPhieuChuyenKhoTT {

	String Id;
	String Id_PhieuNhapKhoTT;
	String NgayChuyenKho;
	String NgayTao;
	String NguoiTao;
	String NgaySua;
	String NguoiSua;
	String IDKhoTTChuyen;
	String TenKhoTTChuyen;
	String IdKhoNhan;
	String TenKhoNhan;
	String TrangThai;
	String Message;
	List<IPhieuChuyenKhoTT> listphieuchuyen=new ArrayList<IPhieuChuyenKhoTT>();
	List<IPhieuChuyenKhoTT_SanPham> listsanphamchuyen=new ArrayList<IPhieuChuyenKhoTT_SanPham>();
	public PhieuChuyenKhoTT(){
		 Id="";
		 Id_PhieuNhapKhoTT="";
		 NgayChuyenKho="";
		 NgayTao="";
		 NguoiTao="";
		 NgaySua="";
		 NguoiSua="";
		 IDKhoTTChuyen="";
		 TenKhoTTChuyen="";
		 IdKhoNhan="";
		 TenKhoNhan="";
		 TrangThai="";
		 Message="";	
	}
	public PhieuChuyenKhoTT(String id){
		
		String sql_getlistchuyenkho="select a.pk_seq,a.ngaychuyen,a.trangthai,a.ngaytao,nt.ten as nguoitao," +
			" a.ngaysua,ns.ten as nguoisua,a.khottchuyen_fk,kc.ten as tenkhochuyen,"+
			" kn.ten as tenkhonhan,a.khottnhan_fk from phieuchuyenkhott a "+
			" inner join nhanvien ns on ns.pk_seq=a.nguoisua inner join nhanvien nt on nt.pk_seq=a.nguoitao "+
			" inner join khott kc on kc.pk_seq=a.khottchuyen_fk inner join khott kn on kn.pk_seq=a.khottnhan_fk where a.pk_seq="+id;
		
		// TODO Auto-generated method stub
		dbutils db=new dbutils();
				ResultSet rs_getlist=db.get(sql_getlistchuyenkho);
		if(rs_getlist!=null){
			try{
			while (rs_getlist.next()){
				this.setId(rs_getlist.getString("pk_seq"));
				this.setKhoId_Chuyen(rs_getlist.getString("khottchuyen_fk"));
				this.setKhoId_Nhan(rs_getlist.getString("khottnhan_fk"));
				this.setMessage("");
				this.setNgayChuyen(rs_getlist.getString("ngaychuyen"));
				this.setNgaysua(rs_getlist.getString("ngaysua"));
				this.setNgaytao(rs_getlist.getString("ngaytao"));
				this.setNguoitao(rs_getlist.getString("nguoitao"));
				this.setTenKhoChuyen(rs_getlist.getString("tenkhochuyen"));
				this.setTenKhoNhan(rs_getlist.getString("tenkhonhan"));
				this.setNguoisua(rs_getlist.getString("nguoisua"));
				this.setTrangthai(rs_getlist.getString("trangthai"));
			}
			}catch(Exception er){
				
			}
		}
		this.listsanphamchuyen.clear();
		//Thuc hien add listsanpham
		String sql_detail=" select a.phieuchuyenkhott_fk,b.ma, a.sanpham_fk,ten,a.soluong,a.dongia from " +
						  " phieuchuyenkhott_sanpham a inner join sanpham b on b.pk_seq =sanpham_fk where phieuchuyenkhott_fk="+ id;
		ResultSet rs_getdetail=db.get(sql_detail);
		if(rs_getdetail!=null){
			try{
				while(rs_getdetail.next()){
				IPhieuChuyenKhoTT_SanPham sanpham=new PhieuChuyenKhoTT_SanPham();
				sanpham.setDonGia(rs_getdetail.getDouble("dongia"));
				sanpham.setId(rs_getdetail.getString("phieuchuyenkhott_fk"));
				sanpham.setSanPhamId(rs_getdetail.getString("sanpham_fk"));
				sanpham.setSoLuongChuyen(rs_getdetail.getInt("soluong"));
				sanpham.setTenSanPham(rs_getdetail.getString("ten"));
				sanpham.setMaSanPham(rs_getdetail.getString("ma"));
				this.listsanphamchuyen.add(sanpham);
				}
			}catch(Exception er){
				System.out.println("Error 82 PhieuChuyenKhoTT : " + er.toString());
			}
		}
	}
	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return this.Id ;
	}

	@Override
	public void setId(String id) {
		// TODO Auto-generated method stub
		this.Id=id;
	}

	@Override
	public String getId_PhieuNhap() {
		// TODO Auto-generated method stub
		return this.Id_PhieuNhapKhoTT;
	}

	@Override
	public void setId_PhieuNhap(String id_PhieuNhap) {
		// TODO Auto-generated method stub
		this.Id_PhieuNhapKhoTT=id_PhieuNhap;
	}

	@Override
	public String getNgayChuyen() {
		// TODO Auto-generated method stub
		return this.NgayChuyenKho;
	}

	@Override
	public void setNgayChuyen(String ngaychuyen) {
		// TODO Auto-generated method stub
		this.NgayChuyenKho=ngaychuyen;
	}

	@Override
	public String getTenKhoChuyen() {
		// TODO Auto-generated method stub
		return this.TenKhoTTChuyen;
	}

	@Override
	public void setTenKhoChuyen(String TenKho) {
		// TODO Auto-generated method stub
		this.TenKhoTTChuyen=TenKho;
	}

	@Override
	public String getKhoID_Chuyen() {
		// TODO Auto-generated method stub
		return this.IDKhoTTChuyen;
	}

	@Override
	public void setKhoId_Chuyen(String KhoId_chuyen) {
		// TODO Auto-generated method stub
		this.IDKhoTTChuyen=KhoId_chuyen;
	}

	@Override
	public String getTenKhoNhan() {
		// TODO Auto-generated method stub
		return this.TenKhoNhan;
	}

	@Override
	public void setTenKhoNhan(String TenKho_nhan) {
		// TODO Auto-generated method stub
		this.TenKhoNhan=TenKho_nhan;
	}

	@Override
	public String getKhoID_Nhan() {
		// TODO Auto-generated method stub
		return this.IdKhoNhan;
	}

	@Override
	public void setKhoId_Nhan(String KhoId_nhan) {
		// TODO Auto-generated method stub
		this.IdKhoNhan=KhoId_nhan;
	}

	@Override
	public String getTrangthai() {
		// TODO Auto-generated method stub
		return this.TrangThai;
	}

	@Override
	public void setTrangthai(String trangthai) {
		// TODO Auto-generated method stub
		this.TrangThai=trangthai;
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
	public void setMessage(String message) {
		// TODO Auto-generated method stub
		this.Message=message;
	}

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return this.Message;
	}

	@Override
	public void setListChuyenKho(String sql_get) {
		String sql_getlistchuyenkho="";
		if(!sql_get.equals("")){
			sql_getlistchuyenkho=sql_get;
		}else{
			 sql_getlistchuyenkho="select a.pk_seq,a.ngaychuyen,a.trangthai,a.ngaytao,nt.ten as nguoitao," +
			 " a.ngaysua,ns.ten as nguoisua,a.khottchuyen_fk,kc.ten as tenkhochuyen,"+
			 " kn.ten as tenkhonhan,a.khottnhan_fk from phieuchuyenkhott a "+
			 " inner join nhanvien ns on ns.pk_seq=a.nguoisua inner join nhanvien nt on nt.pk_seq=a.nguoitao "+
			 " inner join khott kc on kc.pk_seq=a.khottchuyen_fk inner join khott kn on kn.pk_seq=a.khottnhan_fk where a.trangthai!='2'";
		}
		// TODO Auto-generated method stub
		dbutils db=new dbutils();
				ResultSet rs_getlist=db.get(sql_getlistchuyenkho);
		if(rs_getlist!=null){
			try{
			while (rs_getlist.next()){
				IPhieuChuyenKhoTT phieuchuyen=new PhieuChuyenKhoTT();
				phieuchuyen.setId(rs_getlist.getString("pk_seq"));
				phieuchuyen.setKhoId_Chuyen(rs_getlist.getString("khottchuyen_fk"));
				phieuchuyen.setKhoId_Nhan(rs_getlist.getString("khottnhan_fk"));
				phieuchuyen.setMessage("");
				phieuchuyen.setNgayChuyen(rs_getlist.getString("ngaychuyen"));
				phieuchuyen.setNgaysua(rs_getlist.getString("ngaysua"));
				phieuchuyen.setNgaytao(rs_getlist.getString("ngaytao"));
				phieuchuyen.setNguoitao(rs_getlist.getString("nguoitao"));
				phieuchuyen.setTenKhoChuyen(rs_getlist.getString("tenkhochuyen"));
				phieuchuyen.setTenKhoNhan(rs_getlist.getString("tenkhonhan"));
				phieuchuyen.setNguoisua(rs_getlist.getString("nguoisua"));
				phieuchuyen.setTrangthai(rs_getlist.getString("trangthai"));
				listphieuchuyen.add(phieuchuyen);
			}
			}catch(Exception er){
				
			}
		}
	}

	@Override
	public List<IPhieuChuyenKhoTT> getListChuyenKho() {
		// TODO Auto-generated method stub
		return this.listphieuchuyen;
	}

	@Override
	public void setListSanPham(List<IPhieuChuyenKhoTT_SanPham> list) {
		// TODO Auto-generated method stub
		this.listsanphamchuyen=list;
	}

	@Override
	public List<IPhieuChuyenKhoTT_SanPham> getListSanPham() {
		// TODO Auto-generated method stub
		return this.listsanphamchuyen;
	}

	@Override
	public boolean SavePhieuChuyenKhoTT() {
		// TODO Auto-generated method stub
		dbutils db=new dbutils();	
		try
		{
		db.getConnection().setAutoCommit(false);
		String sqlSaveChuyenKho="INSERT INTO [Best].[dbo].[PHIEUCHUYENKHOTT]([NGAYCHUYEN],[TRANGTHAI],[NGAYTAO] ,[NGAYSUA] ,[NGUOITAO] ,[NGUOISUA],[KHOTTCHUYEN_FK],[KHOTTNHAN_FK])  VALUES "+
		"('"+this.NgayChuyenKho+"','0','"+this.NgayTao+"','"+this.NgaySua+"',"+this.NguoiTao+","+this.NguoiSua+","+this.IDKhoTTChuyen+","+this.IdKhoNhan+")";
			if(!db.update(sqlSaveChuyenKho)){
				this.Message="Khong The Tao Moi Phieu Nhap Kho Nay,  Vui Long Kiem Tra Lai Thong Tin Phieu Chuyen";
			//System.out.println("sqlSavePhieuNhapKho : 146 PhieuNhapKhoTT " +sqlSaveChuyenKho);
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return false;
		}
		
		//thuc hien insert chi tiet
		String query = "select IDENT_CURRENT('PHIEUCHUYENKHOTT') as pckId";
		ResultSet rsDh = db.get(query);	
		try
		{
			rsDh.next();
		this.setId(rsDh.getString("pckId"));
		}catch(Exception ex){
			return false;
		}
		int dem=0;//bien nay dung de dem so lan insert detail vao csdl
		int count=0;
		if(this.listsanphamchuyen!=null){
			while (count <this.listsanphamchuyen.size()){
				IPhieuChuyenKhoTT_SanPham sanpham=new PhieuChuyenKhoTT_SanPham();
				sanpham=this.listsanphamchuyen.get(count);
				//Kiem tra san pham nay co ton tai trong bang sanpham khong 
					if(sanpham.getSoLuongChuyen()>0){// chi luu nhung san pham co so luong lon hon 0
						String sql_insertdetail="insert into PhieuChuyenKhoTT_SanPham([PHIEUCHUYENKHOTT_FK],[SANPHAM_FK],[SOLUONG],[DONGIA]) values("+this.getId()+","+sanpham.getSanPhamId()+","+sanpham.getSoLuongChuyen()+","+sanpham.getDonGia()+")";								
						if(!db.update(sql_insertdetail)){
						geso.dms.center.util.Utility.rollback_throw_exception(db);
						this.Message="Khong The Them Moi San Pham :"+ sanpham.getMaSanpham()+" Vao Phieu Chuyen Nay, Vui Long Kiem Tra Lai";
						//System.out.println("sql_insertdetail : 146 PhieuNhapKhoTT " +sql_insertdetail);
						return false;
						}
						String sql_updatekhochuyen="update tonkhoicp set available=available- "+sanpham.getSoLuongChuyen()+" where masp='"+sanpham.getMaSanpham() +"' and kho='"+ this.getKhoID_Chuyen()+"'" ;		
						if(!db.update(sql_updatekhochuyen)){
							geso.dms.center.util.Utility.rollback_throw_exception(db);
							this.Message="Khong The Them Moi San Pham :"+ sanpham.getMaSanpham()+" Vao Phieu Chuyen Nay, Vui Long Kiem Tra Lai";
							//System.out.println("sql_insertdetail : 146 PhieuNhapKhoTT " +sql_insertdetail);
							return false;
							}
						dem++;
					}
					
			count++;
			}
		}
		if(dem==0){
			this.Message=" Khong the them moi phieu chuyen kho nay, chua co san pham nao duoc chon";
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return false;
		}			
		//thuc hien cap nhat lai kho chuyen khi nay avalibale se giam
	
		db.getConnection().commit();
		db.getConnection().setAutoCommit(true);
		}catch(Exception er){
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			this.setMessage("Ban khong the them Phieu Chuyen Kho nay, loi : " + er.toString());
			return false;
		}
		return true;
	}

	@Override
	public boolean EditPhieuChuyenKhoTT() {
		// TODO Auto-generated method stub
		dbutils db=new dbutils();	
		try
		{
		db.getConnection().setAutoCommit(false);
		String sqlSaveChuyenKho="update [Best].[dbo].[PHIEUCHUYENKHOTT] set [NGAYCHUYEN]='"+this.NgayChuyenKho+"'  ,[NGAYSUA] ='"+this.NgaySua+"' ,[NGUOISUA]="+this.NguoiSua+",[KHOTTCHUYEN_FK]='"+this.IDKhoTTChuyen+"',[KHOTTNHAN_FK]="+this.IdKhoNhan+" where pk_seq= "+this.Id;
			if(!db.update(sqlSaveChuyenKho)){
				this.Message="Khong The Sua Phieu Chuyen Kho, Vui Long Nhap Day Du Thong Tin Truoc Khi Luu";
			//System.out.println("sqlSavePhieuNhapKho : 146 PhieuNhapKhoTT " +sqlSaveChuyenKho);
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return false;
		}
		//thuc hien cap nhat lai avaliable trong kho
			String sql_getdetail="select soluong,ma from phieunhapkhott_sanpham inner join sanpham sp on sp.pk_seq=sanpham_fk  where phieunhapkhott_fk="+ this.Id;
			ResultSet rs_detail=db.get(sql_getdetail);
			if(rs_detail!=null){
				while(rs_detail.next()){
					String sql_updatekhochuyen="update tonkhoicp set available=available+ "+rs_detail.getInt("soluong")+"  where masp='"+rs_detail.getString("ma") +"' and kho='"+ this.getKhoID_Chuyen()+"'" ;		
					if(!db.update(sql_updatekhochuyen)){
						geso.dms.center.util.Utility.rollback_throw_exception(db);
						this.Message="Khong The Them Moi San Pham :"+rs_detail.getString("ma")+" Vao Phieu Chuyen Nay, Vui Long Kiem Tra Lai";
						//System.out.println("sql_insertdetail : 146 PhieuNhapKhoTT " +sql_insertdetail);
						return false;
						}
				}
				
			}
		//thuc hien xoa chi tiet
		String query = "delete from PHIEUCHUYENKHOTT_SANPHAM where Phieuchuyenkhott_fk="+this.Id;
		if(!db.update(query)){
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			this.Message="Khong The Xoa Chi Tiet San Pham Cu Trong Qua Trinh Sua, Vui Long Thu Lai";
			return false;
			}
		
		int dem=0;//bien nay dung de dem so lan insert detail vao csdl
		int count=0;
		if(this.listsanphamchuyen!=null){
			while (count <this.listsanphamchuyen.size()){
				IPhieuChuyenKhoTT_SanPham sanpham=new PhieuChuyenKhoTT_SanPham();
				sanpham=this.listsanphamchuyen.get(count);
				//Kiem tra san pham nay co ton tai trong bang sanpham khong 
					if(sanpham.getSoLuongChuyen()>0){// chi luu nhung san pham co so luong lon hon 0
						String sql_insertdetail="insert into PhieuChuyenKhoTT_SanPham([PHIEUCHUYENKHOTT_FK],[SANPHAM_FK],[SOLUONG],[DONGIA]) values("+this.getId()+","+sanpham.getSanPhamId()+","+sanpham.getSoLuongChuyen()+","+sanpham.getDonGia()+")";								
						if(!db.update(sql_insertdetail)){
						geso.dms.center.util.Utility.rollback_throw_exception(db);
						this.Message="Qua Trinh Sua Chi Tiet Don Hang Bi Loi, San Pham :" +sanpham.getMaSanpham()+" Khong The Them Vao CSDL";
						//System.out.println("sql_insertdetail : 146 PhieuNhapKhoTT " +sql_insertdetail);
						return false;
						}
						String sql_updatekhochuyen="update tonkhoicp set available=available- "+sanpham.getSoLuongChuyen()+" where masp='"+sanpham.getMaSanpham() +"' and kho='"+ this.getKhoID_Chuyen()+"'" ;		
						if(!db.update(sql_updatekhochuyen)){
							geso.dms.center.util.Utility.rollback_throw_exception(db);
							this.Message="Khong The Them Moi San Pham :"+ sanpham.getMaSanpham()+" Vao Phieu Chuyen Nay, Vui Long Kiem Tra Lai";
							//System.out.println("sql_insertdetail : 146 PhieuNhapKhoTT " +sql_insertdetail);
							return false;
							}
						dem++;
					}
			count++;
			}
		}
		if(dem==0){
			this.Message=" Khong the them moi phieu chuyen kho nay, chua co san pham nao duoc chon";
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return false;
			
		}

		db.getConnection().commit();
		db.getConnection().setAutoCommit(true);
		}catch(Exception er){
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			this.setMessage("Ban khong the them Phieu Chuyen Kho nay, loi : " + er.toString());
			return false;
		}
		return true;
	}

	@Override
	public boolean DeletePhieuChuyenKhoTT() {
		// TODO Auto-generated method stub
		dbutils db=new dbutils();
		try{
			db.getConnection().setAutoCommit(false);
		   String sql_delete="update phieuchuyenkhott set trangthai='2' where pk_seq="+ this.Id ;
		if(!db.update(sql_delete)){
			this.Message=" xoa khong thanh cong" ;
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return false;
		}
		//thuc hien cap nhat lai avaliable trong kho
		String sql_getdetail="select soluong,ma from phieunhapkhott_sanpham inner join sanpham sp on sp.pk_seq=sanpham_fk  where phieunhapkhott_fk="+ this.Id;
		ResultSet rs_detail=db.get(sql_getdetail);
		if(rs_detail!=null){
			while(rs_detail.next()){
				String sql_updatekhochuyen="update tonkhoicp set available=available+ "+rs_detail.getInt("soluong")+"  where masp='"+rs_detail.getString("ma") +"' and kho='"+ this.getKhoID_Chuyen()+"'" ;		
				if(!db.update(sql_updatekhochuyen)){
					geso.dms.center.util.Utility.rollback_throw_exception(db);
					this.Message="Khong The Them Moi San Pham :"+rs_detail.getString("ma")+" Vao Phieu Chuyen Nay, Vui Long Kiem Tra Lai";
					//System.out.println("sql_insertdetail : 146 PhieuNhapKhoTT " +sql_insertdetail);
					return false;
					}
			}
			
		}
			
		db.getConnection().commit();
		db.getConnection().setAutoCommit(true);
		}catch(Exception er){
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			this.setMessage("Ban khong the them Phieu Chuyen Kho nay, loi : " + er.toString());
			return false;
		}
		return true;
	}
	private String getMaSanPham(String idsp) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		String sqlcheckexitsp="select ma from sanpham where pk_seq='"+idsp+"' and trangthai!='2'";
		dbutils db=new dbutils();
		ResultSet rs_db= db.get(sqlcheckexitsp);
		if(rs_db!=null){
			try{
			if(rs_db.next()){
				return rs_db.getString("ma");
			}
			}catch(Exception er){
				return "";
			}
		}
		return "";
	}
	@Override
	public boolean ChotPhieuChuyenKhoTT() {
		// TODO Auto-generated method stub
		dbutils db=new dbutils();
		try{
			db.getConnection().setAutoCommit(false);
			//khi chot set trangthai =1
		String sql_delete="update phieuchuyenkhott set trangthai='1',ngaysua='"+this.NgaySua+"',nguoisua='"+this.NguoiSua+"' where pk_seq="+ this.Id ;
		if(!db.update(sql_delete)){
			this.Message=" Chot khong thanh cong" ;
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return false;
		}
		//thuc hien cap nhat lai kho khi chot phieu chuyen hang
		int m=0;
		int size=this.listsanphamchuyen.size();
		while (m<size){
			IPhieuChuyenKhoTT_SanPham sanpham=this.listsanphamchuyen.get(m);
			
			//cap nhat tang so luong kho nhap
			String sql_updatekhonhan="update tonkhoicp set stock=stock+ "+sanpham.getSoLuongChuyen()+ ",available=available+ "+sanpham.getSoLuongChuyen()+" where masp='"+sanpham.getMaSanpham() +"' and kho='"+ this.getKhoID_Nhan()+ "'";
			String sql_updatekhochuyen="update tonkhoicp set stock=stock- "+sanpham.getSoLuongChuyen()+ ",available=available- "+sanpham.getSoLuongChuyen()+" where masp='"+sanpham.getMaSanpham() +"' and kho='"+ this.getKhoID_Chuyen()+"'" ;		
			if(!db.update(sql_updatekhonhan)){
				this.Message=" Chot khong thanh cong,khong cap nhat lai duoc so luong trong kho" ;
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				return false;
			}
			if(!db.update(sql_updatekhochuyen)){
				this.Message=" Chot khong thanh cong,khong cap nhat lai duoc so luong trong kho" ;
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				return false;
			}
			m++;
		}
		db.getConnection().commit();
		db.getConnection().setAutoCommit(true);
		}catch(Exception er){
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			this.setMessage("Ban khong the them Phieu Chuyen Kho nay, loi : " + er.toString());
			return false;
		}
		return true;
	}
	@Override
	public void setListSanPhamById() {
		// TODO Auto-generated method stub
		//Thuc hien add listsanpham
		listsanphamchuyen.clear();
		dbutils db=new dbutils();
		String sql_detail="select a.phieuchuyenkhott_fk,b.ma, a.sanpham_fk,ten,a.soluong,a.dongia from " +
					" phieuchuyenkhott_sanpham a inner join sanpham b on b.pk_seq =sanpham_fk where phieuchuyenkhott_fk="+ this.Id;
		ResultSet rs_getdetail=db.get(sql_detail);
		if(rs_getdetail!=null){
			try{
				while(rs_getdetail.next()){
				IPhieuChuyenKhoTT_SanPham sanpham=new PhieuChuyenKhoTT_SanPham();
				sanpham.setDonGia(rs_getdetail.getDouble("dongia"));
				sanpham.setId(rs_getdetail.getString("phieuchuyenkhott_fk"));
				sanpham.setSanPhamId(rs_getdetail.getString("sanpham_fk"));
				sanpham.setSoLuongChuyen(rs_getdetail.getInt("soluong"));
				sanpham.setTenSanPham(rs_getdetail.getString("ten"));
				sanpham.setMaSanPham(rs_getdetail.getString("ma"));
				this.listsanphamchuyen.add(sanpham);
				}
			}catch(Exception er){
				System.out.println("Error 82 PhieuChuyenKhoTT : " + er.toString());
			}
		}
	}

}
