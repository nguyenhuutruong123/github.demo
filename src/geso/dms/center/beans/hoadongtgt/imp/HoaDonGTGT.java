package geso.dms.center.beans.hoadongtgt.imp;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import geso.dms.center.beans.hoadon.IHoaDon_SanPham;
import geso.dms.center.beans.hoadon.imp.HoaDon_SanPham;
import geso.dms.center.beans.hoadongtgt.IHoaDonGTGT;
import geso.dms.center.beans.hoadongtgt.IHoaDonGTGT_SP;
import geso.dms.center.beans.nhaphanphoi.INhaphanphoi;
import geso.dms.center.beans.nhaphanphoi.INhaphanphoiList;
import geso.dms.center.beans.nhaphanphoi.imp.NhaphanphoiList;
import geso.dms.distributor.db.sql.dbutils;

public class HoaDonGTGT implements IHoaDonGTGT{
	String Id;
	String NgayGiaoDich="";
	String TrangThai;
	String NgayTao;
	String NguoiTao;
	String NgaySua;
	String NguoiSua;
	String IDDonDatHang="";
	String SoHoaDon="";
	String Message="";
	String IDNhaCungCap="";
	String IDNhaPhanPhoi="";
	String TenNhaCungCap="";
	String TenNhaPhanPhoi="";
	String IDKHoTT="";
	String TenKHoTT="";
	String IdKenhBanHang="";
	double TongTienChuaVAT;
	double TongTienSauVAT;
	double SoTienHuongKM;
	String Vat;
	String GhiChuHuongTB;
	String GhiCHuHuongKM;
	INhaphanphoiList nhapp=new NhaphanphoiList();
	List<IHoaDonGTGT> list=new ArrayList<IHoaDonGTGT>();
	List<IHoaDonGTGT_SP> listsanpham =new ArrayList<IHoaDonGTGT_SP>();
	public HoaDonGTGT(){
		//Chua lam gi het
	}
	public HoaDonGTGT(String id){

		String	sql_getdata="select a.ghichuhuongkm,a.ghichuhuongtb,a.pk_Seq,a.ngayxuathd,a.trangthai ,nt.ten as nguoitao,a.ngaytao,a.ngaysua,ns.ten as nguoisua,a.dondathang_fk,Sohoadon from hoadon a inner join nhanvien  nt on nt.pk_seq=a.nguoitao inner join nhanvien as ns on ns.pk_seq=a.nguoisua where a.trangthai!='2' and a.pk_seq= "+ id;
	 System.out.println(sql_getdata);
		dbutils db=new dbutils();
	    ResultSet rs=db.get(sql_getdata);
	if(rs!=null){
		try{
			while(rs.next()){
				this.setId(rs.getString("pk_seq"));
				this.setIdDonHangDat(rs.getString("dondathang_fk"));
				this.setNgaygiaodich(rs.getString("ngayxuathd"));
				this.setNgaysua(rs.getString("ngaysua"));
				this.setNgaytao(rs.getString("ngaytao"));
				this.setNguoisua(rs.getString("nguoisua"));
				this.setNguoitao(rs.getString("nguoitao"));
				this.setTrangthai(rs.getString("trangthai"));
				this.setSoHoaDon(rs.getString("sohoadon"));
				this.setGhiChuHuongKM(rs.getString("ghichuhuongkm"));
				this.setGhiChuHuongTB(rs.getString("ghichuhuongtb"));
			}
		}catch(Exception er){
		    	System.out.println("Error HoaDonGTGT : 151 line :"+er.toString());
		}
	}
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
	public String getNgaygiaodich() {
		// TODO Auto-generated method stub
		return this.NgayGiaoDich;
	}

	@Override
	public void setNgaygiaodich(String ngaygiaodich) {
		// TODO Auto-generated method stub
		this.NgayGiaoDich=ngaygiaodich;
	}

	@Override
	public String getNppTen() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public void setNppTen(String nppTen) {
		// TODO Auto-generated method stub
		
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
	public String getIdDonDatHang() {
		// TODO Auto-generated method stub
		return this.IDDonDatHang;
	}

	@Override
	public void setIdDonHangDat(String iddonhangdat) {
		// TODO Auto-generated method stub
		this.IDDonDatHang=iddonhangdat;
	}

	@Override
	public List<IHoaDonGTGT> getListHoaDonGTGT() {
		// TODO Auto-generated method stub
		return this.list;
	}

	@Override
	public void setListHoaDonGTGT(String sql) {
		// TODO Auto-generated method stub
		String sql_getdata;
		if(sql.equals("")){
			//khongo lay nhung don hang da xoa la 2
		sql_getdata="select a.pk_Seq,a.ngayxuathd,a.trangthai ,nt.ten as nguoitao,a.ngaytao,a.ngaysua,ns.ten as nguoisua,a.dondathang_fk,Sohoadon from hoadon a inner join nhanvien  nt on nt.pk_seq=a.nguoitao inner join nhanvien as ns on ns.pk_seq=a.nguoisua where a.trangthai!='2' ";
		
		}else{
			sql_getdata=sql;
		}
		dbutils db=new dbutils();
		ResultSet rs=db.get(sql_getdata);
		if(rs!=null){
			try{
				while(rs.next()){
					IHoaDonGTGT hoadon=new HoaDonGTGT();
					hoadon.setId(rs.getString("pk_seq"));
					hoadon.setIdDonHangDat(rs.getString("dondathang_fk"));
					hoadon.setNgaygiaodich(rs.getString("ngayxuathd"));
					hoadon.setNgaysua(rs.getString("ngaysua"));
					hoadon.setNgaytao(rs.getString("ngaytao"));
					hoadon.setNguoisua(rs.getString("nguoisua"));
					hoadon.setNguoitao(rs.getString("nguoitao"));
					hoadon.setTrangthai(rs.getString("trangthai"));
					hoadon.setSoHoaDon(rs.getString("sohoadon"));
					list.add(hoadon);
				}
			}catch(Exception er){
				System.out.println("Error HoaDonGTGT : 151 line :"+er.toString());
			}
		}
	}

	@Override
	public boolean SaveHoaDonGTGT() {
		// TODO Auto-generated method stub
		 dbutils cn= 	new dbutils();
			
			try
			{
			 cn.getConnection().setAutoCommit(false);
		   if(this.NguoiTao.toString().equals("null") || this.NguoiTao==""){
			   this.Message="Nguoi Dung Dang Nhap Hien Tai Bi Log,Vui Long Dang Nhap Lai De Thuc Hien Chuc Nang Nay";
			   
		   }
		   
		   if(this.NgayGiaoDich==null || this.NgayGiaoDich==""){
			   this.Message="Ngay xuat hoa don khong duoc rong, vui long kiem tra lai";
		   }
		   if(this.IDDonDatHang==null|| this.IDDonDatHang==""){
			   this.Message="Don dat hang de xuat hoa don khong ton tai, vui long kiem tra lai";
		   }
		   //Kiem tra so seri hoa don gtgt da co hay chua?
		   String sql_check_exitsohd="select pk_seq from hoadon where sohoadon='"+this.SoHoaDon+"'";
		   ResultSet rs_checkexitsohd=cn.get(sql_check_exitsohd);
		   try{
			   if(rs_checkexitsohd.next()){
				   this.Message="So Hoa Don Gia Tri Gia Tang Da Ton Tai,Vui Long Kiem Tra Lai... ";	
				   cn.update("rollback");
				   return false;  
			   }
		   }catch(Exception er){
			   cn.update("rollback");
				this.setMessage(er.toString());
				System.out.println("Errorr : HoaDon: line 247 "+ er.toString());
				return false;  
		   }
		   //kiem tra xem co hoa don nao co dondathang nay khong, va don dat hang nay phai trang thai la chua xoa
		   String sql_checkexitddh="select pk_seq from hoadon where dondathang_fk="+this.IDDonDatHang +" and trangthai!='2'";
		   ResultSet rs_checkexitddh=cn.get(sql_checkexitddh);
		   if(rs_checkexitddh!=null){
		   if(rs_checkexitddh.next()){
			   this.Message="Da xuat hoa don tai chinh cho don dat hang nay, vui long chon don dat hang khac";
			   return false;
		   }
		   }
		   
			String sql=" insert into hoadon(NGAYXUATHD,TRANGTHAI,NGUOITAO,NGUOISUA,NGAYTAO,NGAYSUA,DONDATHANG_FK,SOHOADON) "+
			 " values ('"+this.NgayGiaoDich+"','0',"+this.NguoiTao+","+this.NguoiSua+",'"+this.NgayTao+"','"+this.NgaySua+"',"+this.IDDonDatHang+",'"+this.SoHoaDon+"')  ";
			
			if(!cn.update(sql)){
				cn.update("rollback");
				this.Message="Khong The Cap Nhat Hoa Don ,Loi Tren Dong Lenh Sau :"+sql;
				return false;
			}

			cn.getConnection().commit();
			cn.getConnection().setAutoCommit(true);
			}
			catch(Exception er){
				cn.update("rollback");
				this.setMessage(er.toString());
				System.out.println("Errorr : HoaDon: line 545 "+ er.toString());
				
				return false;
			}
			return true;
	}

	@Override
	public boolean EditHoaDonGTGT() {
		// TODO Auto-generated method stub
		 dbutils cn= 	new dbutils();
			try
			{
			 cn.getConnection().setAutoCommit(false);
		   if(this.NguoiTao.toString().equals("null") || this.NguoiTao==""){
			   this.Message="Nguoi Dung Dang Nhap Hien Tai Bi Log,Vui Long Dang Nhap Lai De Thuc Hien Chuc Nang Nay";
			   
		   }
		   //Truoc het kiem tra xem so hd gtgt co trung voi shd gtgt cu khong
		    
		   //Kiem tra so seri hoa don gtgt da co hay chua?
		   String sql_check_exitsohd="select pk_seq from hoadon where sohoadon='"+this.SoHoaDon.trim()+"' and pk_seq!="+ this.Id;
		   
		   ResultSet rs_checkexitsohd=cn.get(sql_check_exitsohd);
		   try{
			   if(rs_checkexitsohd.next()){
				   this.Message="So Hoa Don Gia Tri Gia Tang Da Ton Tai,Vui Long Kiem Tra Lai... " ;	
				   cn.update("rollback");
				   return false;  
			   }
		   }catch(Exception er){
			   cn.update("rollback");
				this.setMessage(er.toString());
				System.out.println("Errorr : HoaDon: line 247 "+ er.toString());
				return false;  
		   }
		   if(this.NgayGiaoDich==null || this.NgayGiaoDich==""){
			   this.Message="Ngay xuat hoa don khong duoc rong, vui long kiem tra lai";
		   }
		   if(this.IDDonDatHang==null|| this.IDDonDatHang==""){
			   this.Message="Don dat hang de xuat hoa don khong ton tai, vui long kiem tra lai";
		   }
		   
			String sql=" update  hoadon  set NGAYXUATHD='"+this.NgayGiaoDich+"',NGUOISUA="+this.NguoiSua+",NGAYSUA='"+this.NgaySua+"',SOHOADON='"+this.SoHoaDon+"' where pk_Seq="+this.Id;
			if(!cn.update(sql)){
				cn.update("rollback");
				this.Message="Khong The Cap Nhat Hoa Don ,Loi Tren Dong Lenh Sau :" + sql;
				return false;
			}

			cn.getConnection().commit();
			cn.getConnection().setAutoCommit(true);
			}
			catch(Exception er){
				cn.update("rollback");
				this.setMessage(er.toString());
				System.out.println("Errorr : HoaDon: line 545 "+ er.toString());
				return false;
			}
			return true;
	}

	@Override
	public String getSoHoaDon() {
		// TODO Auto-generated method stub
		return this.SoHoaDon;
	}

	@Override
	public void setSoHoaDon(String sohoadon) {
		// TODO Auto-generated method stub
		this.SoHoaDon=sohoadon;
	}

	@Override
	public List<IHoaDonGTGT_SP> getListSanPham() {
		// TODO Auto-generated method stub
		return this.listsanpham;
	}

	@Override
	public void setListSanPham(List<IHoaDonGTGT_SP> list) {
		// TODO Auto-generated method stub
		this.listsanpham=list;
	}

	@Override
	public void setInfoNhaPhoiPhoi(INhaphanphoiList npp) {
		// TODO Auto-generated method stub
		this.nhapp=npp;
	}

	@Override
	public INhaphanphoiList getInfoNhaPhoiPhoi() {
		// TODO Auto-generated method stub
		return this.nhapp;
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
	public String getIdNhaCungCap() {
		// TODO Auto-generated method stub
		return this.IDNhaCungCap ;
	}

	@Override
	public void setIdNhaCungCap(String idnhacc) {
		// TODO Auto-generated method stub
		this.IDNhaCungCap=idnhacc;
	}

	@Override
	public String getTenNhaCungCap() {
		// TODO Auto-generated method stub
		return this.TenNhaCungCap;
	}

	@Override
	public void setTenNhaCungCap(String tennhacc) {
		// TODO Auto-generated method stub
		this.TenNhaCungCap=tennhacc;
	}

	@Override
	public String getIDKenhBanHang() {
		// TODO Auto-generated method stub
		return this.IdKenhBanHang;
	}

	@Override
	public void setIDKenhBanHang(String kenhbanhangid) {
		// TODO Auto-generated method stub
		this.IdKenhBanHang=kenhbanhangid;
	}

	@Override
	public String getNppId() {
		// TODO Auto-generated method stub
		return this.IDNhaPhanPhoi;
	}

	@Override
	public void setNppId(String nppId) {
		// TODO Auto-generated method stub
		this.IDNhaPhanPhoi=nppId;
	}

	@Override
	public String getKhottId() {
		// TODO Auto-generated method stub
		return this.IDKHoTT;
	}

	@Override
	public void setKhottId(String khottid) {
		// TODO Auto-generated method stub
		this.IDKHoTT=khottid;
	}

	@Override
	public String getKhottTen() {
		// TODO Auto-generated method stub
		return this.TenKHoTT;
	}

	@Override
	public void setKhottTen(String KhottTen) {
		// TODO Auto-generated method stub
		this.TenKHoTT=KhottTen;
	}

	@Override
	public double getTongtienchuaCK() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setTongtienchuaCK(double ttcck) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double getTienCK() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setTienCK(double tienck) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double getTongtientruocVAT() {
		// TODO Auto-generated method stub
		return this.TongTienChuaVAT;
	}

	@Override
	public void setTongtientruocVAT(double tttvat) {
		// TODO Auto-generated method stub
		this.TongTienChuaVAT=tttvat;
	}

	@Override
	public double getTongtiensauVAT() {
		// TODO Auto-generated method stub
		return this.TongTienSauVAT;
	}

	@Override
	public void setTongtiensauVAT(double ttsvat) {
		// TODO Auto-generated method stub
		this.TongTienSauVAT=ttsvat;
	}

	@Override
	public void setVat(String vat) {
		// TODO Auto-generated method stub
		this.Vat=vat;
	}

	@Override
	public String getVat() {
		// TODO Auto-generated method stub
		return this.Vat;
	}

	@Override
	public void setSoTienHuongKM(double sotienhuongkm) {
		// TODO Auto-generated method stub
		this.SoTienHuongKM=sotienhuongkm;
	}

	@Override
	public double getSoTienHuongKM() {
		// TODO Auto-generated method stub
		return this.SoTienHuongKM;
	}

	@Override
	public void setListSanPhamDDH(String ddhid) {
		// TODO Auto-generated method stub
		this.listsanpham.clear();
		String sql_getdetail="select dondathang_fk,sanpham_fk,s.ma,soluong,s.ten,a.donvi,a.dongia,d.chietkhau from dondathang_sp a inner join dondathang d on d.pk_seq=a.dondathang_fk  inner join sanpham s on a.sanpham_fk=s.pk_seq  where dondathang_fk="+ ddhid;
		dbutils db=new dbutils();
		ResultSet rs= db.get(sql_getdetail);
		if(rs!=null){
			try{
				while(rs.next()){
					IHoaDonGTGT_SP sp=new HoaDonGTGT_SP();
					sp.setDonGia(rs.getDouble("dongia"));
					sp.setDonViTinh(rs.getString("donvi"));
					sp.setIdSanPham(rs.getString("sanpham_fk"));
					sp.setMaSanPham(rs.getString("ma"));
					sp.setTenSanPham(rs.getString("ten"));
					sp.setSoLuong(rs.getInt("soluong"));
					double ck=(sp.getSoLuong()* sp.getDonGia()) /100 * rs.getDouble("chietkhau");
					sp.setChietKhau(ck);
					double thanhtien=sp.getSoLuong()* sp.getDonGia() -ck;
					sp.setThanhTien(thanhtien);
					listsanpham.add(sp);
					
				}
			}catch(Exception er){
				System.out.println("Error HoaDonGTGT : line 401 " + er.toString());
			}
		}
	}
	@Override
	public boolean ChotHoaDonGTGT() {
		// TODO Auto-generated method stub
		 dbutils cn= 	new dbutils();
			try
			{
			 cn.getConnection().setAutoCommit(false);
		      if(this.NguoiTao==null || this.NguoiTao==""){
			   this.Message="Nguoi Dung Dang Nhap Hien Tai Bi Log,Vui Long Dang Nhap Lai De Thuc Hien Chuc Nang Nay";
			   
		   }
		      //Sau khi chot hoa don tai chinh thi cap nhat lai don dat hang la 5
		    //sau khi chot hoa don thi thuc hien update lai gia tri cua xuat kho
			  String sql_updateddh="update dondathang set trangthai='5' where pk_seq="+ this.IDDonDatHang;
		    if(!cn.update(sql_updateddh)){
			cn.update("rollback");
				this.Message="Khong The Cap Nhat Hoa Don GTGT ,Loi Tren Dong Lenh Sau :" + sql_updateddh;
				return false;
	        }
		      // cap nhat lai hoa don la 1
			String sql=" update  hoadon  set Trangthai=1 ,NGUOISUA="+this.NguoiSua+",NGAYSUA='"+this.NgaySua+"' where pk_Seq= "+this.Id;
			if(!cn.update(sql)){
				cn.update("rollback");
				this.Message="Khong The Chot Hoa Don ,Loi Tren Dong Lenh Sau :" + sql;
				return false;
			}
			String sql_getdonhang="select a.pk_seq as chungtu,a.ngayxuathd as ngaychungtu,a.dondathang_fk ,a.sohoadon,b.sotienbvat, "+
				" sotienavat,b.npp_fk,b.ncc_fk,b.vat,b.dvkd_fk,kbh_fk from hoadon a inner join dondathang b on a.dondathang_fk=b.pk_seq where a.pk_Seq="+this.Id;
			ResultSet getdata=cn.get(sql_getdonhang);
			if(getdata.next()){
			//thuc hien insert vao bang nhap hang va cap nhat trang thai cho no =1
				
				
			String sql_insertvaonhaphang ="INSERT INTO NHAPHANG ([NGAYNHAN],[SOTIENBVAT],[NGUOITAO] ,[NGUOISUA],[TRANGTHAI]  ,[NPP_FK] ,[NCC_FK] ,[VAT],[SOTIENAVAT],[DVKD_FK],[KBH_FK] "+
				" ,[DATHANG_FK],[CHUNGTU],[NGAYCHUNGTU]) VALUES('"+this.NgaySua+"','"+getdata.getString("sotienbvat")+"','"+this.NguoiTao+"','"+this.NguoiSua+"','0', "+
				getdata.getString("npp_fk")+","+getdata.getString("NCC_FK")+ ",'"+ getdata.getString("vat")+"',"+getdata.getString("SOTIENAVAT")+","+getdata.getString("DVKD_FK")+","+getdata.getString("KBH_FK")+","+getdata.getString("dondathang_fk")+","+getdata.getString("chungtu")+",'"+getdata.getString("NGAYCHUNGTU")+"' )";	
				if(!cn.update(sql_insertvaonhaphang)){
					cn.update("rollback");
					this.Message="Khong The Chot Hoa Don ,Loi Tren Dong Lenh Sau :" + sql_insertvaonhaphang;
					return false;
				}
				//thuc hien lay khoa chinh vua them chi tiet
				String query = "select IDENT_CURRENT('NHAPHANG') as dhId";
				ResultSet rsDh = cn.get(query);	
				try
				{
					rsDh.next();
				this.setId(rsDh.getString("dhId"));
				}catch(Exception ex){
					return false;
				}
				//  lay danh sach san pham    
				this.listsanpham.clear();
				String sql_getdetail="select dondathang_fk,sanpham_fk,s.ma,soluong,s.ten,a.donvi,a.dongia,d.chietkhau from dondathang_sp a inner join dondathang d on d.pk_seq=a.dondathang_fk  inner join sanpham s on a.sanpham_fk=s.pk_seq  where dondathang_fk="+ getdata.getString("dondathang_fk");
				ResultSet rs= cn.get(sql_getdetail);
				if(rs!=null){
					try{
						while(rs.next()){
							IHoaDonGTGT_SP sp=new HoaDonGTGT_SP();
							sp.setDonGia(rs.getDouble("dongia"));
							sp.setDonViTinh(rs.getString("donvi"));
							sp.setIdSanPham(rs.getString("sanpham_fk"));
							sp.setMaSanPham(rs.getString("ma"));
							sp.setTenSanPham(rs.getString("ten"));
							sp.setSoLuong(rs.getInt("soluong"));
							
							double ck=(sp.getSoLuong()* sp.getDonGia()) /100 * rs.getDouble("chietkhau");
							sp.setChietKhau(ck);
							double thanhtien=sp.getSoLuong()* sp.getDonGia() -ck;
							sp.setThanhTien(thanhtien);
							listsanpham.add(sp);
							
						}
					}catch(Exception er){
						System.out.println("Error HoaDonGTGT : line 401 " + er.toString());
					}
				}
				//
				int k=0;
				while (k< listsanpham.size()) {
					IHoaDonGTGT_SP sp=new HoaDonGTGT_SP();
					sp=listsanpham.get(k);
					long thue=10;
					try{
						thue=	Long.parseLong(this.Vat);
					}catch(Exception er){
						
					}
					long sotienbvat=(long) (sp.getSoLuong()* sp.getDonGia());
					long sotienavat=(long) sotienbvat+ sotienbvat/100 * thue;
					String sql_insertdetail="insert into nhaphang_sp (nhaphang_fk,sanpham_fk,soluong,donvi,giamua,tienbvat,vat,tienavat) values ("+this.getId()+",'"+sp.getMaSanPham()+"',"+sp.getSoLuong()+",'"+sp.getDonViTinh()+"',"+Math.round(sp.getDonGia())+","+sotienbvat+","+Math.round(thue)+","+sotienavat+")";
					//System.out.println(sql_insertdetail);
					if(!cn.update(sql_insertdetail)){
						cn.update("rollback");
						this.Message="Khong The Chot Hoa Don ,Loi Tren Dong Lenh Sau :" + sql_insertdetail;
						return false;
					}
					k++;
				}
			}
			else{
				this.Message="KHong The Thuc Hien Chot Hoa Don GTGT Nay,Vui Long Kiem Tra Lai";
				return false;
				
			}
			//thhuc hien insert chi tiet
			
			cn.getConnection().commit();
			cn.getConnection().setAutoCommit(true);
			}
			catch(Exception er){
				cn.update("rollback");
				this.setMessage(er.toString());
				System.out.println("Errorr : HoaDon: line 545 "+ er.toString());
				return false;
			}
			return true;
	}
	@Override
	public void setGhiChuHuongKM(String ghichuhuongkm) {
		// TODO Auto-generated method stub
		this.GhiCHuHuongKM=ghichuhuongkm;
	}
	@Override
	public String getGhiChuCHuongKm() {
		// TODO Auto-generated method stub
		return this.GhiCHuHuongKM;
	}
	@Override
	public void setGhiChuHuongTB(String ghichuhuongtb) {
		// TODO Auto-generated method stub
		this.GhiChuHuongTB=ghichuhuongtb;
	}
	@Override
	public String getGhiChuCHuongTB() {
		// TODO Auto-generated method stub
		return this.GhiChuHuongTB;
	}

}
