package geso.dms.center.beans.hoadon.imp;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import geso.dms.center.beans.hoadon.IHoaDon;
import geso.dms.center.beans.hoadon.IHoaDon_CTKM;
import geso.dms.center.beans.hoadon.IHoaDon_SanPham;
import geso.dms.center.beans.nhaphanphoi.INhaphanphoiList;
import geso.dms.center.beans.nhaphanphoi.imp.NhaphanphoiList;
import geso.dms.distributor.db.sql.dbutils;
public class HoaDon implements IHoaDon{
	
	String Id;
	String NgayGiaoDich;
	String TrangThai;
	String NgayTao;
	String NguoiTao;
	String NgaySua;
	String NguoiSua;
	double ChietKhau;
	double VAT;
	String Msg;
	String IdDonHangDat;
	String NppId;
	String NppTen;
	String khoId;
	String KhoTen;
	String LoaiDonHang;
	String DeNghiDatHangId;
	List<IHoaDon_SanPham> listsanpham=new ArrayList<IHoaDon_SanPham>();
	List<IHoaDon> listHoaDon=new ArrayList<IHoaDon>();
	List<IHoaDon_CTKM> listctkm=new ArrayList<IHoaDon_CTKM>();
	double TongTienChuaCK=0;
	double TienCK=0;
	double TongTienTruocVAT=0;
	double TongTienSauVAT=0;
	double TTTienTraKM=0;
	String IDNhaCungCap;
	String TenNhaCungCap;
	String KenhBanHangId;
	String DVKDID;
	String SoSO="";
	double NoCu=0;
	double SoTienTraTrungBay;
	String GSBHid="";
	Hashtable<String,INhaphanphoiList> hashtnpp;
	Hashtable<String, Integer> spThieuList;
	INhaphanphoiList nhapp=new NhaphanphoiList();
	
	public HoaDon(String id){
		String Sql_getData="";
			
			Sql_getData="select a.loaidonhang, a.tienhuongkm,a.tienhuongtb,chietkhau,a.gsbh_fk, a.pk_seq,a.ngaydat,a.sotienbvat,a.nguoitao,a.nguoisua,a.trangthai,a.npp_fk,a.ncc_fk,a.vat,a.sotienavat,a.dvkd_fk,isnull(a.denghidathang_fk,0) as denghidathang ,a.kbh_fk,a.soid from dondathang a where trangthai!='2' and a.pk_seq="+id;	
		 	System.out.println("Get Data Don Hang ID :"+Sql_getData);
			dbutils db=new dbutils();
		 	ResultSet rs_getdata=db.get(Sql_getData);
		 	if(rs_getdata!= null){
		 		try{
		 			while(rs_getdata.next()){
		 				this.setId( rs_getdata.getString("pk_seq"));
		 				this.setIdNhaCungCap(rs_getdata.getString("ncc_fk"));
		 				this.setMessage("");
		 				try{
		 				this.setChietkhau(rs_getdata.getDouble("chietkhau"));
		 				}catch(Exception er){
		 					this.setChietkhau(0);
		 				}
		 				this.setSoTienTraKM(rs_getdata.getDouble("tienhuongkm"));
		 				this.setSoTienTraTB(rs_getdata.getDouble("tienhuongtb"));
		 				this.setNgaygiaodich(rs_getdata.getString("ngaydat"));
		 				this.setNppId(rs_getdata.getString("npp_fk"));
		 				this.setIDKenhBanHang(rs_getdata.getString("kbh_fk"));
		 				this.setIdDVKD(rs_getdata.getString("dvkd_fk"));
		 				this.setVAT(rs_getdata.getDouble("vat"));
		 				this.setTongtientruocVAT(rs_getdata.getDouble("sotienbvat"));
		 				this.setTongtiensauVAT(rs_getdata.getDouble("sotienavat"));
		 				this.setSoSO(rs_getdata.getString("soid"));
		 				this.setLoaiDonHang(rs_getdata.getString("loaidonhang"));
		 				this.setGiamSatBanHang(rs_getdata.getString("gsbh_fk"));
		 				this.setDeNghiDatHang(rs_getdata.getString("denghidathang"));
		 				//Thuc hien lay thong tin nha phan phoi
		 		    	String sql_infonpp="select b.pk_seq,b.ten,b.diachi,b.diachixhd,b.dienthoai,b.masothue,KHOSAP,(select chietkhau from mucchietkhautt m where m.pk_Seq=b.mucchietkhautt_fk) as chietkhau from nhaphanphoi b where pk_seq="+ this.getNppId();
		 		    	//System.out.println("HoaDon.java: 74 "+sql_infonpp);
		 		    	ResultSet rs_infonpp=db.get(sql_infonpp);
		 		    	if(rs_infonpp!=null){
		 		    		try{
		 		    		  if(rs_infonpp.next()){
		 		    			  INhaphanphoiList nhapp=new NhaphanphoiList();
		 		    			  nhapp.setDiaChi(rs_infonpp.getString("diachi"));
		 		    			  nhapp.setDiaChiXuatHD(rs_infonpp.getString("diachixhd"));
		 		    			  nhapp.setSodienthoai(rs_infonpp.getString("dienthoai"));
		 		    			  nhapp.setTen(rs_infonpp.getString("ten"));
		 		    			  nhapp.setMaSoThue(rs_infonpp.getString("masothue"));
		 		    			  nhapp.setMucChietKhau(rs_infonpp.getDouble("chietkhau"));
		 		    			  this.setKhottId(rs_infonpp.getString("khosap"));
		 		    			  this.setInfoNhaPhoiPhoi(nhapp);
		 		    		  }
		 		    		}catch(Exception er){
		 		    		}
		 		    	}
		 		    	//Thuc hien lay tong tin don hang
		 		    	String sql_getdetail="select ct as idct,dondathang_fk,vat,sotienavat,sanpham_fk,ma,ten,donvi,soluong,dongia from dondathang_sp inner join sanpham a on a.pk_seq=sanpham_fk where dondathang_fk="+id;
		 		    	System.out.println(sql_getdetail);
		 		    	ResultSet rs_detail=db.get(sql_getdetail);
		 		    	if(rs_detail!=null){
		 		    		while(rs_detail.next()){
		 		    			
		 		    			IHoaDon_SanPham sanpham=new HoaDon_SanPham();
		 		    			sanpham.setDonGia(rs_detail.getDouble("dongia"));
		 		    			sanpham.setDonViTinh(rs_detail.getString("donvi"));
		 		    			sanpham.setId(rs_detail.getString("dondathang_fk"));
		 		    			sanpham.setIdSanPham(rs_detail.getString("sanpham_fk"));
		 		    			sanpham.setTenSanPham(rs_detail.getString("ten"));
		 		    			sanpham.setMaSanPham(rs_detail.getString("ma"));
		 		    			sanpham.setSoLuong(rs_detail.getInt("soluong"));
		 		    			sanpham.setSoLuongDat(rs_detail.getInt("soluong"));
		 		    			sanpham.setVAT(rs_detail.getDouble("vat"));
		 		    			sanpham.setThanhTien(rs_detail.getDouble("sotienavat"));
		 		    			sanpham.setCTKMID(rs_detail.getString("idct"));
		 		    			this.listsanpham.add(sanpham);
		 		    		}
		 		    	}
		 		    	String sql="select a.dondathang_fk,a.nhapp_fk,a.ctkhuyenmai_fk ,b.scheme,b.diengiai,pb.dasudung from dondathang_ctkm a inner join ctkhuyenmai b on "+
		 		    	" a.ctkhuyenmai_fk=b.pk_seq inner join dondathang ddh on ddh.pk_seq=a.dondathang_fk "+
		 		    	" inner join phanbokhuyenmai pb on pb.ctkm_fk=a.ctkhuyenmai_fk and pb.npp_fk=ddh.npp_fk where dondathang_fk="+this.Id;
		 		    	System.out.println("Load Ra Cac DVKD :"+sql);
		 		    	this.listctkm.clear();
		 		    	ResultSet rs_getdvkd=db.get(sql);
		 		    	if(rs_getdvkd!=null){
		 		    		while(rs_getdvkd.next()){
		 		    			IHoaDon_CTKM ctkm=new HoaDon_CTKM();
		 		    			ctkm.setCTKM(rs_getdvkd.getString("ctkhuyenmai_fk"));
		 		    			ctkm.setDaSuDung(rs_getdvkd.getDouble("dasudung"));
		 		    			ctkm.setTenChuongTrinh(rs_getdvkd.getString("scheme"));
		 		    			ctkm.setDienGiai(rs_getdvkd.getString("diengiai"));
		 		    			ctkm.setID("1");//Truong hop nay la check =true
		 		    			this.listctkm.add(ctkm);
		 		    		}
		 		    	}
		 			}
		 		}catch(Exception er){
		 			System.out.println("Error : HoaDon.java in line 308:  " + er.toString());
		 		}
		 		
		 		
		 	}
	}
	public HoaDon(){
		this.Msg="";
		this.Id="";
		this.IdDonHangDat="";
		this.IDNhaCungCap="";
		this.khoId="";
		this.NgayGiaoDich="";
		this.NppId="";
		this.TienCK=0;
		this.KenhBanHangId="";
		this.NoCu=0;
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
		return this.NppTen;
	}

	@Override
	public void setNppTen(String nppTen) {
		// TODO Auto-generated method stub
		this.NppTen=nppTen;
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
	public double getChietkhau() {
		// TODO Auto-generated method stub
		return this.ChietKhau;
	}

	@Override
	public void setChietkhau(double chietkhau) {
		// TODO Auto-generated method stub
		this.ChietKhau=chietkhau;
	}

	@Override
	public double getVAT() {
		// TODO Auto-generated method stub
		return this.VAT;
	}

	@Override
	public void setVAT(double vat) {
		// TODO Auto-generated method stub
		this.VAT=vat;
	}

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return this.Msg;
	}

	@Override
	public void setMessage(String msg) {
		// TODO Auto-generated method stub
		this.Msg=msg;
	}

	@Override
	public String getNppId() {
		// TODO Auto-generated method stub
		return this.NppId;
	}

	@Override
	public void setNppId(String nppId) {
		// TODO Auto-generated method stub
		this.NppId=nppId;
	}
	
	

	@Override
	public double getTongtienchuaCK() {
		// TODO Auto-generated method stub
		return this.TongTienChuaCK;
	}

	@Override
	public void setTongtienchuaCK(double ttcck) {
		// TODO Auto-generated method stub
		this.TongTienChuaCK=ttcck;
	}

	@Override
	public double getTienCK() {
		// TODO Auto-generated method stub
		return this.TienCK;
	}

	@Override
	public void setTienCK(double tienck) {
		// TODO Auto-generated method stub
		this.TienCK=tienck;
	}

	@Override
	public double getTongtientruocVAT() {
		// TODO Auto-generated method stub
		return this.TongTienTruocVAT;
	}

	@Override
	public void setTongtientruocVAT(double tttvat) {
		// TODO Auto-generated method stub
		this.TongTienTruocVAT=tttvat;
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
	public Hashtable<String, Integer> getSpThieuList() {
		// TODO Auto-generated method stub
		return spThieuList;
	}

	@Override
	public void setSpThieuList(Hashtable<String, Integer> spthieulist) {
		// TODO Auto-generated method stub
		this.spThieuList=spthieulist;
	}

	@Override
	public boolean SaveHoaDon() {
		// TODO Auto-generated method stub
		  dbutils cn= 	new dbutils();
		
			try
			{
				
				if(this.spThieuList.size() > 0)
				{
					this.Msg="So luong ton kho khong du xuat, vui long nhap lai so luong";
					return false;
				}
				if(this.khoId.equals("")){
					this.Msg="Kho Ban Hang Khong Duoc Rong";
				}
				if(this.NguoiTao==null || this.NguoiTao.equals("")){
					this.Msg="User Dang Nhap Tam Thoi Bi Log Vi Che Do Bao Mat, Vui Long Dang Nhap Lai De Thuc Hien Chuc Nang Nay";
					return false;
				}
				
				if(this.NppId==null || this.NppId.equals("")){
					this.Msg="Nha Phan Phoi Khong Duoc Rong";
				}
				/*Kiem tra so tong tien km con du de xuat khong?
				 * bo qua phan nay vi se kiem tra khi chot don hang 
				String sql_getdatakm="select npp_fk,tongtien-datra as conlai from tienkhuyenmai_npp where npp_fk= "+ this.NppId;
				ResultSet rs_getdatakm=cn.get(sql_getdatakm);
				if(rs_getdatakm!=null){
					if(rs_getdatakm.next()){
						double tongtienkm=rs_getdatakm.getDouble("conlai");
						if(this.getSoTienTraKM()>tongtienkm){
							this.Msg="Tien Thanh Toan KM Vuot Qua Tong Tien KM Nha Phan Phoi Duoc Huong, Vui Long Kiem Tra Lai";
							return false;
						}
					}else{//neu khong co cung co nghia la thanh toan chua duoc 
							if(this.getSoTienTraKM()>0){				
							this.Msg="Tien Thanh Toan KM Vuot Qua Tong Tien KM Nha Phan Phoi Duoc Huong, Vui Long Kiem Tra Lai";
							return false;
							}
					}
				}
				*/
				  cn.getConnection().setAutoCommit(false);
				  String vatnew="";
				  if(this.VAT==0){
					  vatnew="0";
				  }else{
					  vatnew=Integer.toString((int)this.VAT);
				  }
					  
				  String chietkhaunew="0";
				  try{
				  chietkhaunew= Integer.toString((int)this.ChietKhau);
				  }catch(Exception er){
					  
				  }
				  //loai don hang trong truong hop nay la 1
			String sql=" insert into dondathang(NGAYDAT,TRANGTHAI,NGUOITAO,NGUOISUA,VAT,SOTIENBVAT,NPP_FK,SOTIENAVAT,DVKD_FK,NCC_FK,kbh_FK,chietkhau,tienhuongkm,tienhuongtb,loaidonhang,gsbh_fk) "+
			 " values ('"+this.NgayGiaoDich+"','1',"+this.NguoiTao+","+this.NguoiSua+",'"+vatnew+"',"+Math.round(this.TongTienTruocVAT)+","+this.NppId+","+Math.round(this.TongTienSauVAT)+","+this.DVKDID+","+this.IDNhaCungCap+"," + this.KenhBanHangId+","+chietkhaunew+","+this.getSoTienTraKM()+","+this.getSoTienTraTB()+" ,'1',"+this.GSBHid+")  ";
			
			if(!cn.update(sql)){
				cn.update("rollback");
				this.Msg="Khong The Cap Nhat Hoa Don ,Loi Tren Dong Lenh Sau :" + sql;
				return false;
			}	
			//Save chi tiet don hang
			int count = 0;
			while(count < this.listsanpham.size())
			{
				IHoaDon_SanPham sanpham = new HoaDon_SanPham();
				sanpham=listsanpham.get(count);
				String query = "select IDENT_CURRENT('dondathang') as dhId";
				ResultSet rsDh = cn.get(query);	
				try
				{
					rsDh.next();
			     	this.Id = rsDh.getString("dhId");
				rsDh.close();
				}
				catch(Exception er){
					cn.update("rollback");
					//System.out.println("Error : DonHangNpp: line 291 : " + er.toString());
					this.setMessage("Loi  - Clasname :HoaDon - line 293 : "+ er.toString());
				}
				sanpham.setId(this.Id);
				String sql_insertchitiet="insert into dondathang_sp (sanpham_fk,dondathang_fk,soluong,dongia,donvi)values ("+sanpham.getIdSanPham()+","+sanpham.getId()+","+sanpham.getSoLuong()+","+Math.round(sanpham.getDonGia())+",'"+sanpham.getDonViTinh()+"')";
				//System.out.println("Error line 49 :Classname:  SanPhamDHNpp : Cau lenh SQL " + sql);
				if(!cn.update(sql_insertchitiet)){
					this.Msg="Khong the luu ma san pham : "+ sanpham.getMaSanPham()+" ,Loi trong dong lenh sau : " + sql_insertchitiet;
					cn.update("rollback");
					return false;
				}
				
			//Cap nhat lai kho, book tang con avaliable giam,day la chinh la dat hang do nha phan phoi dat len,phai cap nhat lai kho giong nhu nha phan phoi chot phieu dat hang
			String query_updatekho = "update tonkhoicp set booked = booked + '" + sanpham.getSoLuong() + "', available = available - '" + sanpham.getSoLuong() + "' where masp='" + sanpham.getMaSanPham() +"' and kho='" + this.khoId + "'";
			 if(!cn.update(query_updatekho)){
				 System.out.println("Error update KHO : line -672 : HoaDon : "+query_updatekho);
				cn.update("rollback");
					return false;
		    }
				
			    //sanpham.SavaSanPhamDhNpp();
			count++;
			
			}
			
			cn.getConnection().commit();
			cn.getConnection().setAutoCommit(true);
			}
			catch(Exception er){
				cn.update("rollback");
				this.setMessage(er.toString());
				System.out.println("Errorr : HoaDon: line 481 "+ er.toString());
				
				return false;
			}
			return true;
	}

	@Override
	public boolean EditHoaDon() {
		  dbutils cn= 	new dbutils();
			
			try
			{
				if(this.getSoTienTraKM() >this.getTienHuongKhuyenMai()){
					this.Msg="Tien Tra KHuyen Mai Vuot Qua So Tong Tien Duoc Huong ,Vui Long Nhap Lai";
					return false;
				}
				if(this.getSoTienTraTB()>this.getTienHuongTrungBay()){
					this.Msg="Tien Tra Trung Bay Vuot Qua So Tong Tien Trung Bay Duoc Huong, Vui Long Nhap Lai ";
					return false;
				}
				if(this.spThieuList.size() > 0)
				{
					this.Msg="So luong ton kho khong du xuat, vui long nhap lai so luong";
					return false;
				}
				if(this.khoId==null || this.khoId.equals("")){
					this.Msg="Kho Ban Hang Khong Duoc Rong";
					return false;
				}
				if(this.NguoiTao.toString().equals("null")|| this.NguoiTao.equals("")){
					this.Msg="Nguoi Dung Dang Nhap Da Bi Log Vi ly Do Bao Mat He Thong, Vui Long Dang Nhap Lai De Thuc Hien Chuong Trinh";
				    return false;
				}
				  cn.getConnection().setAutoCommit(false);
				String sql_getmaxid="select max(soid) so from dondathang";
				ResultSet rs_getmaxid=cn.get(sql_getmaxid);
				if(rs_getmaxid!=null){
					if(rs_getmaxid.next()){
						this.SoSO=Long.toString(rs_getmaxid.getLong("so")+1);
					}
					if(this.SoSO.equals("1")){
						this.SoSO="2000000000";
					}
					
				}
				
				String sqlupdatedenghi="  update denghidathang set TRANGTHAI='3' where pk_seq=(select denghidathang_fk from dondathang where pk_seq="+this.Id+" )" ;
				
				if(!cn.update(sqlupdatedenghi)){
					cn.update("rollback");
					this.Msg="Khong The Duyet Hoa Don ,Loi Tren Dong Lenh Sau :" + sqlupdatedenghi;
					return false;
				}	  
				String vatnew="0";
				if(this.VAT>0){
					vatnew=Integer.toString((int)this.VAT);
				}
					
			String sql="  update dondathang set TRANGTHAI='3',NGUOISUA="+this.NguoiSua+",VAT='"+vatnew+
			"',SOTIENBVAT="+Math.round(this.TongTienTruocVAT)+" ,SOTIENAVAT="+Math.round(this.TongTienSauVAT)+", CHIETKHAU="+this.ChietKhau+ ", Soid= '" 
			+this.SoSO +"' ,tienhuongkm="+this.getSoTienTraKM()+",gsbh_fk="+this.GSBHid+",tienhuongtb= "+ this.getSoTienTraTB() 
			+"  where pk_seq="+this.Id ;
			
			if(!cn.update(sql)){
				cn.update("rollback");
				this.Msg="Khong The Duyet Hoa Don ,Loi Tren Dong Lenh Sau :" + sql;
				return false;
			}
			
			//Khi Chot Don Hang thi thuc hien cap nhat lai sotien ton duoc tra khuyen mai cua nha phan phoi
			String sql_update_khuyenmai="update TIENKHUYENMAI_NPP set datra=datra+"+this.getSoTienTraKM() +"where npp_fk="+ this.IDNhaCungCap;
			if(!cn.update(sql_update_khuyenmai)){
				cn.update("rollback");
				this.Msg="Khong The Duyet Hoa Don ,Loi Tren Dong Lenh Sau :" + sql_update_khuyenmai;
				return false;
			}
			//Lay thong tin denghidathang
			String sqlgetdenghidh="select isnull(denghidathang_fk,0) as denghidathangid from dondathang where pk_seq=" + this.Id;
			ResultSet rs_getdndh=cn.get(sqlgetdenghidh);
			if(rs_getdndh!=null){
				if(rs_getdndh.next()){
					this.DeNghiDatHangId=rs_getdndh.getString("denghidathangid");
				}
			}
			//Thuc hien up dat lai cot dadathang trong bang denghidathang_sp
		/*	if(this.DeNghiDatHangId!=null && !this.DeNghiDatHangId.equals("0")){
			String sql_update_denghi_sp="update denghidathang set dadathang= '"+this.TongTienSauVAT +"'  where pk_seq=" +this.DeNghiDatHangId;
			if(!cn.update(sql_update_denghi_sp)){
				System.out.println("Error update De NGhi Dat Hang  : line -577 : HoaDon : "+sql_update_denghi_sp);
				cn.update("rollback");
				return false;
			}
			}
			*/
			//lay chi tiet don hang cu ra va cap nhat lai so luong check va available ,dong thoi cap nhat lai cot dadathang trong bang denghidathang_sp
			String sql_getdetail_ddh="select a.pk_seq,a.ma,soluong from dondathang_sp b inner join sanpham a on a.pk_seq=b.sanpham_fk  where dondathang_fk="+ this.Id;
			//System.out.println("Get Detail :"+ sql_getdetail_ddh);
			ResultSet rs_detail_ddh=cn.get(sql_getdetail_ddh);
			if(rs_detail_ddh!=null){
				while (rs_detail_ddh.next()){
					String query_updatekho = "update tonkhoicp set booked = booked - '" +rs_detail_ddh.getInt("soluong") + "', available = available + '" + rs_detail_ddh.getInt("soluong") + "' where masp='" + rs_detail_ddh.getString("ma") +"' and kho='" + this.khoId + "'";
					//Truoc khi xoa chi tiet hang cu nay di thi cap nhap lai so luong dat va available sau do them chi tiet hang moi vao va cap nhat lai so luong trong kho
					//System.out.println("Error update KHO : line -524 : HoaDon : "+query_updatekho);
					if(!cn.update(query_updatekho)){
						System.out.println("Error update KHO : line -524 : HoaDon : "+query_updatekho);
						cn.update("rollback");
						return false;
					}
					
					
				}
			}
			//xoa het chi tiet cu 
			String sql_deletechitietdh="delete from dondathang_sp where dondathang_fk="+ this.Id;
			if(!cn.update(sql_deletechitietdh)){
				cn.update("rollback");
				this.Msg="Khong The Duyet Hoa Don ,Loi Tren Dong Lenh Sau :" + sql;
				return false;
			}
			//Save chi tiet don hang
			int count = 0;
			while(count < this.listsanpham.size())
			{
				IHoaDon_SanPham sanpham = new HoaDon_SanPham();
				sanpham=listsanpham.get(count);	
				if(sanpham.getSoLuong()>0){
					String sql_insertchitiet="insert into dondathang_sp (sanpham_fk,dondathang_fk,soluong,dongia,donvi)values ("+sanpham.getIdSanPham()+","+this.Id+","+sanpham.getSoLuong()+","+Math.round(sanpham.getDonGia())+",'"+sanpham.getDonViTinh()+"')";
					//System.out.println("Error line 49 :Classname:  SanPhamDHNpp : Cau lenh SQL " + sql);
					if(!cn.update(sql_insertchitiet)){
						this.Msg="Khong the luu ma san pham : "+ sanpham.getMaSanPham()+" ,Loi trong dong lenh sau : " + sql_insertchitiet;
						cn.update("rollback");
						return false;
					}
					//Cap nhat lai kho, book tang con avaliable giam
					String query_updatekho = "update tonkhoicp set booked = booked + '" +sanpham.getSoLuong() + "', available = available - '" + sanpham.getSoLuong() + "' where masp='" + sanpham.getMaSanPham() +"' and kho='" + this.khoId + "'";
					if(!cn.update(query_updatekho)){
						System.out.println("Error update KHO : line -672 : HoaDon : "+query_updatekho);
						cn.update("rollback");
						return false;
					}
				}
			    //sanpham.SavaSanPhamDhNpp();
			count++;
			
			}
			
			cn.getConnection().commit();
			cn.getConnection().setAutoCommit(true);
			}
			catch(Exception er){
				cn.update("rollback");
				this.setMessage(er.toString());
				System.out.println("Errorr : HoaDon: line 625 "+ er.toString());
				
				return false;
			}
			return true;
	}

	@Override
	public boolean DeleteHoaDon() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<IHoaDon> getListHoaDon() {
		// TODO Auto-generated method stub
		return this.listHoaDon;
	}

	@Override
	public void setListHoaDon(String sql) {
		// TODO Auto-generated method stub
		String Sql_getData="";
	if(sql.equals("")){
		Sql_getData="select a.PK_seq,a.ngaynhap,a.trangthai,a.ngaytao,a.ngaysua,a.vat,a.chietkhau,a.tonggiatri,a.dathanhtoan,a.npp_fk, "+
			" a.khott_fk,a.tienduoctrakm,dondathang_fk,nhacc_fk,ns.ten as nguoisua,nt.ten as nguoitao,ncc.ten as tennhacc,npp.ten as tennhapp,k.ten as tenkho from hoadon a inner join nhanvien as ns "+
			" on ns.pk_seq=a.nguoisua inner join nhanvien as nt  "+
			" on nt.pk_seq=a.nguoitao inner join nhaphanphoi npp on npp.pk_seq=a.npp_fk inner join nhacungcap ncc on ncc.pk_seq=nhacc_fk inner join khott  k on k.pk_seq=a.khott_fk where a.trangthai!='2'";
			}else{
				Sql_getData=sql;
	}
	listHoaDon.clear();
	 	dbutils db=new dbutils();
	 	ResultSet rs_getdata=db.get(Sql_getData);
	 	if(rs_getdata!= null){
	 		try{
	 			while(rs_getdata.next()){
	 				IHoaDon hd=new HoaDon();
	 				hd.setChietkhau( rs_getdata.getInt("chietkhau"));
	 				hd.setId( rs_getdata.getString("pk_seq"));
	 				hd.setIdDonHangDat(rs_getdata.getString("dondathang_fk"));
	 				hd.setIdNhaCungCap(rs_getdata.getString("nhacc_fk"));
	 				hd.setKhottId(rs_getdata.getString("khott_fk"));
	 				hd.setKhottTen(rs_getdata.getString("k.ten"));
	 				hd.setMessage("");
	 				hd.setNgaygiaodich(rs_getdata.getString("ngaynhap"));
	 				hd.setNgaysua(rs_getdata.getString("ngaysua"));
	 				hd.setNgaytao(rs_getdata.getString("ngaytao"));
	 				hd.setNguoisua(rs_getdata.getString("nguoisua"));
	 				hd.setNguoitao(rs_getdata.getString("nguoitao"));
	 				hd.setNppId(rs_getdata.getString("npp_fk"));
	 				hd.setNppTen(rs_getdata.getString("tennhapp"));
	 				hd.setSoTienTraKM(rs_getdata.getDouble("tienduoctrakm"));
	 				hd.setTenNhaCungCap(rs_getdata.getString("tennhacc"));
	 				hd.setTrangthai(rs_getdata.getString("trangthai"));
	 				hd.setVAT(rs_getdata.getDouble("vat"));
	 				listHoaDon.add(hd);
	 				
	 			}
	 		}catch(Exception er){
	 			System.out.println("Error : HoaDon.java in line 308:  " + er.toString());
	 		}
	 	}
	}

	@Override
	public void setSoTienTraKM(double tientrakm) {
		// TODO Auto-generated method stub
		this.TTTienTraKM=tientrakm;
	}

	@Override
	public double getSoTienTraKM() {
		// TODO Auto-generated method stub
		return this.TTTienTraKM;
	}

	@Override
	public String getKhottId() {
		// TODO Auto-generated method stub
		return this.khoId;
	}

	@Override
	public void setKhottId(String khottid) {
		// TODO Auto-generated method stub
		this.khoId=khottid;
	}

	@Override
	public String getKhottTen() {
		// TODO Auto-generated method stub
		return this.KhoTen;
	}

	@Override
	public void setKhottTen(String KhottTen) {
		// TODO Auto-generated method stub
		this.KhoTen=KhottTen;
	}

	@Override
	public String getIdDonDatHang() {
		// TODO Auto-generated method stub
		return this.IdDonHangDat;
	}

	@Override
	public void setIdDonHangDat(String iddonhangdat) {
		// TODO Auto-generated method stub
		this.IdDonHangDat=iddonhangdat;
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
	public void setListSanPham(List<IHoaDon_SanPham> list) {
		// TODO Auto-generated method stub
		this.listsanpham=list;
	}

	@Override
	public List<IHoaDon_SanPham> getListSanPham() {
		// TODO Auto-generated method stub
		return listsanpham;
	}
	@Override
	public String getIDKenhBanHang() {
		// TODO Auto-generated method stub
		return this.KenhBanHangId;
	}
	@Override
	public void setIDKenhBanHang(String kenhbanhangid) {
		// TODO Auto-generated method stub
		this.KenhBanHangId=kenhbanhangid;
	}
	@Override
	public void setHashtableNhaPhanPhoi() {
		// TODO Auto-generated method stub
		String sql="select  a.pk_seq,ten,a.diachi,a.dienthoai,a.masothue,d.chietkhau from nhaphanphoi a left join(select chietkhau,b.nhapp_fk from "+ 
   " mucchietkhautt_nhapp b inner join mucchietkhautt c on c.pk_seq=b.mucchietkhautt_fk) d on a.pk_seq=d.nhapp_fk";
		dbutils db=new dbutils();
	 ResultSet rs=	db.get(sql);
		if(rs!=null){
			try{
				while(rs.next()){
					INhaphanphoiList npp=new NhaphanphoiList();
					npp.setNccId(rs.getString("pk_seq"));
					npp.setTen(rs.getString("ten"));
					npp.setSodienthoai(rs.getString("dienthoai"));
					npp.setDiaChi(rs.getString("diachi"));
					npp.setMaSoThue(rs.getString("masothue"));
					npp.setMucChietKhau(rs.getDouble("chietkhau"));
					hashtnpp.put(rs.getString("pk_seq"), npp);
				}
			}catch(Exception er){
				
			}
		}
	}
	@Override
	public Hashtable<String, INhaphanphoiList> getHashtableNhaPP() {
		// TODO Auto-generated method stub
		return this.hashtnpp;
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
	public void setIdDVKD(String dvkdid) {
		// TODO Auto-generated method stub
		this.DVKDID=dvkdid;
	}
	@Override
	public String getIdDVKD() {
		// TODO Auto-generated method stub
		return this.DVKDID;
	}
	@Override
	public void setSoSO(String soso) {
		// TODO Auto-generated method stub
		this.SoSO=soso;
	}
	@Override
	public String getSoSO() {
		// TODO Auto-generated method stub
		return this.SoSO;
	}
	@Override
	public double getTienNo() {
		// TODO Auto-generated method stub
		/**
		 * lay tien no cua nha phan phoi,lay tong tien cac don hang cua nha phan phoi da duoc duyet tru di tong tien thanh toan cua nha phan phoi do(nhung phieu thanh toan da chot)
		 */
		String sql="select isnull(sum(isnull(sotienavat,0)-isnull(tienhuongtb,0)-isnull(tienhuongkm,0)),0)- isnull((select sum(sotien) from phieuthanhtoantt  where trangthai='1' and nhapp_fk= "+this.NppId+" ),0) as tongsotien from dondathang " + 
					" where npp_fk="+this.NppId+" and trangthai>2";
		dbutils db=new dbutils();
		ResultSet rs= db.get(sql);
		if(rs!=null){
			try{
				if(rs.next()){
				this.NoCu= rs.getDouble("tongsotien");
				}
			}catch(Exception er){
				this.NoCu=0;
			}
		}
		return this.NoCu;
	}
	@Override
	public double getGioiHanCongNo(){
		if(this.NppId==null || this.NppId.equals("")){
			return 0;
		}else{
			String sql="select sotienno  from gioihancongnott a inner join nhaphanphoi b on a.pk_seq=b.ghcntt_fk where b.pk_seq="+ this.NppId;
			dbutils db=new dbutils();
			ResultSet rs=db.get(sql);
			try{
				rs.next();
				return rs.getDouble("sotienno");
			}catch(Exception er){
				return 0;
			}
			
		}
		
	}
	@Override
	public double getTienHuongKhuyenMai() {
		// TODO Auto-generated method stub
		try{
			String sql="select (tongtien-datra) as conlai from TIENKHUYENMAI_NPP";
			dbutils db=new dbutils();

			ResultSet rs=	db.get(sql);
			if(rs.next()){
				return rs.getDouble("conlai");
			}
			return 0;
		}
		catch(Exception er){
			return 0;	
		}
		
	}
	@Override
	public double getTienHuongTrungBay() {
		// TODO Auto-generated method stub
		try{
			String sql="select (tongtien-datra) as conlai from TIENTRUNGBAY_NPP";
			dbutils db=new dbutils();
			ResultSet rs=	db.get(sql);
			if(rs.next()){
				return rs.getDouble("conlai");
			}
			return 0;
		}
		catch(Exception er){
			return 0;	
		}
	}
	@Override
	public void setSoTienTraTB(double tientratb) {
		// TODO Auto-generated method stub
		this.SoTienTraTrungBay=tientratb;	
	}
	@Override
	public double getSoTienTraTB() {
		// TODO Auto-generated method stub
		return this.SoTienTraTrungBay;
	}
	@Override
	public void setListCTKM(List<IHoaDon_CTKM> _listctkm) {
		// TODO Auto-generated method stub
		this.listctkm=_listctkm;
	}
	@Override
	public void setListCTKMInit() {
		// TODO Auto-generated method stub
		this.listctkm.clear();
		String sql= "select a.ctkm_fk,pb.dasudung,ck.scheme,isnull(ck.diengiai,' ') as diengiai from DUYETTRAKM_CTKM a inner join duyettrakm d on d.pk_seq= a.duyettrakm_fk   inner  join "+
					" phanbokhuyenmai  pb on pb.ctkm_fk=a.ctkm_fk and pb.npp_fk=d.npp_fk inner join ctkhuyenmai ck on ck.pk_seq=a.ctkm_fk   where d.npp_fk="+this.NppId+" and "+
					"  d.trangthai=1 and  a.thanhtoan=2 and  not exists (select distinct ct from dondathang_sp ddh_sp inner join dondathang ddh on "+
	                "  ddh.pk_seq=ddh_sp.dondathang_fk where ddh.trangthai=1 and ct!=null and ddh.npp_fk="+this.NppId+") ";
		System.out.println("HoaDon.java 905: Sql get List CTKM :" + sql);
		dbutils db=new dbutils();
		ResultSet rs=db.get(sql);
		if(rs!=null){
			listctkm.clear();
			try{
			while(rs.next()){
			  IHoaDon_CTKM ctkm=new HoaDon_CTKM();
			  ctkm.setCTKM(rs.getString("ctkm_fk"));
			  ctkm.setDienGiai(rs.getString("diengiai"));
			 // ctkm.setNhaPP(rs.getString("nhapp_fk"));
			  ctkm.setTenChuongTrinh(rs.getString("scheme"));
			  ctkm.setDaSuDung(rs.getDouble("dasudung"));
			  //mac dinh gia tri check =0( Muon cot id de luu)
			  ctkm.setID("0");
			  listctkm.add(ctkm);
			}
			}catch(Exception er){
				
			}
		}
	}
	@Override
	public List<IHoaDon_CTKM> getListCTKM() {
		// TODO Auto-generated method stub
		return this.listctkm;
	}
	@Override
	public boolean saveDonHangKhuyenMai() {
		// TODO Auto-generated method stub
		  dbutils cn= 	new dbutils();
		
			try
			{
				
				if(this.spThieuList.size() > 0)
				{
					this.Msg="So luong ton kho khong du xuat, vui long nhap lai so luong";
					return false;
				}
				if(this.khoId.equals("")){
					this.Msg="Kho Ban Hang Khong Duoc Rong";
				}
				if(this.NguoiTao.toString().equals("null") || this.NguoiTao.equals("")){
					this.Msg="User Dang Nhap Tam Thoi Bi Log Vi Che Do Bao Mat, Vui Long Dang Nhap Lai De Thuc Hien Chuc Nang Nay";
					return false;
				}
				
				if(this.NppId.toString().equals("null") || this.NppId.equals("")){
					this.Msg="Nha Phan Phoi Khong Duoc Rong";
				}
				/*Kiem tra so tong tien km con du de xuat khong?
				 * bo qua phan nay vi se kiem tra khi chot don hang 
				String sql_getdatakm="select npp_fk,tongtien-datra as conlai from tienkhuyenmai_npp where npp_fk= "+ this.NppId;
				ResultSet rs_getdatakm=cn.get(sql_getdatakm);
				if(rs_getdatakm!=null){
					if(rs_getdatakm.next()){
						double tongtienkm=rs_getdatakm.getDouble("conlai");
						if(this.getSoTienTraKM()>tongtienkm){
							this.Msg="Tien Thanh Toan KM Vuot Qua Tong Tien KM Nha Phan Phoi Duoc Huong, Vui Long Kiem Tra Lai";
							return false;
							
						}
					}else{//neu khong co cung co nghia la thanh toan chua duoc 
							if(this.getSoTienTraKM()>0){				
							this.Msg="Tien Thanh Toan KM Vuot Qua Tong Tien KM Nha Phan Phoi Duoc Huong, Vui Long Kiem Tra Lai";
							return false;
							}
					}
				}
				*/
				  cn.getConnection().setAutoCommit(false);
				  String vatnew="";
				  if(this.VAT==0){
					  vatnew="0";
				  }else{
					  vatnew=Integer.toString((int)this.VAT);
				  }
					  
				  String chietkhaunew="0";
				  try{
				  chietkhaunew= Integer.toString((int)this.ChietKhau);
				  }catch(Exception er){
					  
				  }
	  
			String sql=" insert into dondathang(NGAYDAT,TRANGTHAI,NGUOITAO,NGUOISUA,VAT,SOTIENBVAT,NPP_FK,SOTIENAVAT,DVKD_FK,NCC_FK,kbh_FK,chietkhau,tienhuongkm,tienhuongtb,loaidonhang) "+
			 " values ('"+this.NgayGiaoDich+"','1',"+this.NguoiTao+","+this.NguoiSua+",'"+vatnew+"',"+Math.round(this.TongTienTruocVAT)+","+this.NppId+","+Math.round(this.TongTienSauVAT)+","+this.DVKDID+","+this.IDNhaCungCap+"," + this.KenhBanHangId+","+chietkhaunew+","+this.getSoTienTraKM()+","+this.getSoTienTraTB()+",'2')  ";
			
			if(!cn.update(sql)){
				cn.update("rollback");
				this.Msg="Khong The Cap Nhat Hoa Don ,Loi Tren Dong Lenh Sau :" + sql;
				return false;
			}	
			//Save chi tiet don hang
			int count = 0;
			while(count < this.listsanpham.size())
			{
				IHoaDon_SanPham sanpham = new HoaDon_SanPham();
				sanpham=listsanpham.get(count);
				String query = "select IDENT_CURRENT('dondathang') as dhId";
				ResultSet rsDh = cn.get(query);	
				try
				{
					rsDh.next();
			     	this.Id = rsDh.getString("dhId");
				rsDh.close();
				}
				catch(Exception er){
					cn.update("rollback");
					//System.out.println("Error : DonHangNpp: line 291 : " + er.toString());
					this.setMessage("Loi  - Clasname :HoaDon - line 293 : "+ er.toString());
				}
				sanpham.setId(this.Id);
				String sql_insertchitiet="insert into dondathang_sp (sanpham_fk,dondathang_fk,soluong,dongia,donvi,ct)values ("+sanpham.getIdSanPham()+","+sanpham.getId()+","+sanpham.getSoLuong()+","+Math.round(sanpham.getDonGia())+",'"+sanpham.getDonViTinh()+"',"+sanpham.getCTKMId()+")";
				//System.out.println("Error line 49 :Classname:  SanPhamDHNpp : Cau lenh SQL " + sql);
				if(!cn.update(sql_insertchitiet)){
					this.Msg="Khong the luu ma san pham : "+ sanpham.getMaSanPham()+" ,Loi trong dong lenh sau : " + sql_insertchitiet;
					cn.update("rollback");
					return false;
				}
				
			//Cap nhat lai kho, book tang con avaliable giam,day la chinh la dat hang do nha phan phoi dat len,phai cap nhat lai kho giong nhu nha phan phoi chot phieu dat hang
			String query_updatekho = "update tonkhoicp set booked = booked + '" + sanpham.getSoLuong() + "', available = available - '" + sanpham.getSoLuong() + "' where masp='" + sanpham.getMaSanPham() +"' and kho='" + this.khoId + "'";
			 if(!cn.update(query_updatekho)){
				 System.out.println("Error update KHO : line -672 : HoaDon : "+query_updatekho);
				 cn.update("rollback");
					return false;
			 }
			 
			 /* Phan luu vao bang dondathang_ctkm
				//Sau khi thuc hien save lai don hang thi thuc hien insert vao bang DONDATHANG_CTKM
			 	if(this.listctkm!=null && this.listctkm.size()>0){
				 int m=0;
				 int size=this.listctkm.size();
				 while(m<size){
					 IHoaDon_CTKM ctkm=listctkm.get(m);
					 
					 //Neu nhung chuong trinh nao duoc chon thi cot id la 1;ta dang muon cot ID de luu tam nhung cot check trong form HoaDonKhuyenMaiNew.jps
					 //Thuc hien kiem tra tong tien cua cac san pham cac don hang nay  co lon hon tong tien cua cac san pham cong lai khong
					 String sql_gettongtientheoct="select sum(soluong* dongia) as sum from dondathang_sp sp inner join dondathang ddh on  ddh.pk_seq=dondathang_fk where dondathang_fk="+this.Id+" and ct="+ ctkm.getCTKM();
					 ResultSet rs_sum=cn.get(sql_gettongtientheoct);
					 double sumtien=0;
					 if(rs_sum!= null){
						 rs_sum.next();
						 sumtien=rs_sum.getDouble("sum");
					 }
					 if(sumtien>ctkm.getDaSuDung()){
						 //khong the thuc hien them don dat hang nay
						 this.Msg="Ban Da Nhap Qua  So Tien Cho Chuong Trinh Khuyen Mai :" + ctkm.getTenChuongTrinh()+". Vui Long Kiem Tra Lai Cac San Pham Cho Chuong Trinh Nay";
						 cn.update("rollback"); 
						 return false; 
					 }
					 if( ctkm.getId().equals("1")){
						String sql_insertctkm="insert into dondathang_ctkm (dondathang_fk,nhapp_fk,ctkhuyenmai_fk) values ("+this.Id+","+this.NppId+","+ctkm.getCTKM()+")";
						if(!cn.update(sql_insertctkm)){
							this.Msg="Error insert DONDATHANG_CTKM - line: 1044 - ERROR COMMAND:" +sql_insertctkm;
							cn.update("rollback");
							return false;
						}
					 }
					 m++;
				 }
			 }
			 */
			    //sanpham.SavaSanPhamDhNpp();
			count++;
			
			}
			
			cn.getConnection().commit();
			cn.getConnection().setAutoCommit(true);
			}
			catch(Exception er){
				cn.update("rollback");
				this.setMessage(er.toString());
				System.out.println("Errorr : HoaDon: line 1110 "+ er.toString());
				
				return false;
			}
			return true;
		
	}
	@Override
	public void setLoaiDonHang(String loaidonhang) {
		// TODO Auto-generated method stub
		this.LoaiDonHang=loaidonhang;
	}
	@Override
	public String getLoaiDonHang() {
		// TODO Auto-generated method stub
		return this.LoaiDonHang;
	}
	@Override
	public boolean EditDonHangKhuyenMai() {
		  dbutils cn= 	new dbutils();
			try
			{
			
				if(this.spThieuList.size() > 0)
				{
					this.Msg="So luong ton kho khong du xuat, vui long nhap lai so luong";
					return false;
				}
				if(this.khoId==null || this.khoId.equals("")){
					this.Msg="Kho Ban Hang Khong Duoc Rong";
					return false;
				}
				if(this.NguoiTao.toString().equals("null") || this.NguoiTao.equals("")){
					this.Msg="Nguoi Dung Dang Nhap Da Bi Log Vi ly Do Bao Mat He Thong, Vui Long Dang Nhap Lai De Thuc Hien Chuong Trinh";
				    return false;
				}
				  cn.getConnection().setAutoCommit(false);
				String sql_getmaxid="select max(soid) so from dondathang";
				ResultSet rs_getmaxid=cn.get(sql_getmaxid);
				if(rs_getmaxid!=null){
					if(rs_getmaxid.next()){
						this.SoSO=Long.toString(rs_getmaxid.getLong("so")+1);
					}
					if(this.SoSO.equals("1")){
						this.SoSO="2000000000";
					}
					
				}
				
				String sqlupdatedenghi="  update denghidathang set TRANGTHAI='3' where pk_seq=(select denghidathang_fk from dondathang where pk_seq="+this.Id+" )" ;
				
				if(!cn.update(sqlupdatedenghi)){
					cn.update("rollback");
					this.Msg="Khong The Duyet Hoa Don ,Loi Tren Dong Lenh Sau :" + sqlupdatedenghi;
					return false;
				}	  
				String vatnew="0";
				if(this.VAT>0){
					vatnew=Integer.toString((int)this.VAT);
				}
					
			String sql="  update dondathang set TRANGTHAI='3',NGUOISUA="+this.NguoiSua+",VAT='"+vatnew+
			"',SOTIENBVAT="+Math.round(this.TongTienTruocVAT)+" ,SOTIENAVAT="+Math.round(this.TongTienSauVAT)+", CHIETKHAU="+this.ChietKhau+ ", Soid= '" 
			+this.SoSO +"' where pk_seq="+this.Id ;
			if(!cn.update(sql)){
				cn.update("rollback");
				this.Msg="Khong The Duyet Hoa Don ,Loi Tren Dong Lenh Sau :" + sql;
				return false;
			}
			//Khi Chot Don Hang thi thuc hien cap nhat lai sotien ton trong kho
			String sql_update_khuyenmai="update TIENKHUYENMAI_NPP set datra=datra+"+this.getSoTienTraKM() +"where npp_fk="+ this.IDNhaCungCap;
			if(!cn.update(sql_update_khuyenmai)){
				cn.update("rollback");
				this.Msg="Khong The Duyet Hoa Don ,Loi Tren Dong Lenh Sau :" + sql_update_khuyenmai;
				return false;
			}
			//lay chi tiet don hang cu ra va cap nhat lai so luong check va available 
			
			String sql_getdetail_ddh="select a.ma,soluong from dondathang_sp b inner join sanpham a on a.pk_seq=b.sanpham_fk  where dondathang_fk="+ this.Id;
			System.out.println("Get Detail :"+ sql_getdetail_ddh);
			ResultSet rs_detail_ddh=cn.get(sql_getdetail_ddh);
			if(rs_detail_ddh!=null){
				while (rs_detail_ddh.next()){
					String query_updatekho = "update tonkhoicp set booked = booked - '" +rs_detail_ddh.getInt("soluong") + "', available = available + '" + rs_detail_ddh.getInt("soluong") + "' where masp='" + rs_detail_ddh.getString("ma") +"' and kho='" + this.khoId + "'";
					//Truoc khi xoa chi tiet hang cu nay di thi cap nhap lai so luong dat va available sau do them chi tiet hang moi vao va cap nhat lai so luong trong kho
					//System.out.println("Error update KHO : line -524 : HoaDon : "+query_updatekho);
					if(!cn.update(query_updatekho)){
						System.out.println("Error update KHO : line -524 : HoaDon : "+query_updatekho);
						cn.update("rollback");
						return false;
					}
				}
			}
			//xoa het chi tiet cu 
			String sql_deletechitietdh="delete from dondathang_sp where dondathang_fk="+ this.Id;
			if(!cn.update(sql_deletechitietdh)){
				cn.update("rollback");
				this.Msg="Khong The Duyet Hoa Don ,Loi Tren Dong Lenh Sau :" + sql;
				return false;
			}
			//Save chi tiet don hang
			int count = 0;
			while(count < this.listsanpham.size())
			{
				IHoaDon_SanPham sanpham = new HoaDon_SanPham();
				sanpham=listsanpham.get(count);	
				if(sanpham.getSoLuong()>0){
					String sql_insertchitiet="insert into dondathang_sp (sanpham_fk,dondathang_fk,soluong,dongia,donvi,ct)values ("+sanpham.getIdSanPham()+","+this.Id+","+sanpham.getSoLuong()+","+Math.round(sanpham.getDonGia())+",'"+sanpham.getDonViTinh()+"',"+sanpham.getCTKMId()+")";
					//System.out.println("Error line 49 :Classname:  SanPhamDHNpp : Cau lenh SQL " + sql);
					if(!cn.update(sql_insertchitiet)){
						this.Msg="Khong the luu ma san pham : "+ sanpham.getMaSanPham()+" ,Loi trong dong lenh sau : " + sql_insertchitiet;
						cn.update("rollback");
						return false;
					}
					//Cap nhat lai kho, book tang con avaliable giam
					String query_updatekho = "update tonkhoicp set booked = booked + '" +sanpham.getSoLuong() + "', available = available - '" + sanpham.getSoLuong() + "' where masp='" + sanpham.getMaSanPham() +"' and kho='" + this.khoId + "'";
					if(!cn.update(query_updatekho)){
						System.out.println("Error update KHO : line -672 : HoaDon : "+query_updatekho);
						cn.update("rollback");
						return false;
					}
				}
			    //sanpham.SavaSanPhamDhNpp();
			count++;
			
			}
			
			cn.getConnection().commit();
			cn.getConnection().setAutoCommit(true);
			}
			catch(Exception er){
				cn.update("rollback");
				this.setMessage(er.toString());
				System.out.println("Errorr : HoaDon: line 1234 "+ er.toString());
				
				return false;
			}
			return true;
	}
	@Override
	public ResultSet getReS_GSBH() {
		// TODO Auto-generated method stub
		dbutils db=new dbutils();
		return db.get("select distinct gsbh_fk ,gsbh.ten,npp_fk from  nhapp_giamsatbh a inner join giamsatbanhang gsbh on a.gsbh_fk=gsbh.pk_seq where npp_fk="+ this.NppId);
	}
	@Override
	public void setGiamSatBanHang(String gsbhid) {
		// TODO Auto-generated method stub
		this.GSBHid=gsbhid;
	}
	@Override
	public String getGiamSatBanHang() {
		// TODO Auto-generated method stub
		return this.GSBHid;
	}
	@Override
	public void setDeNghiDatHang(String denghidathang) {
		// TODO Auto-generated method stub
		this.DeNghiDatHangId=denghidathang;
	}
	@Override
	public String getDeNghiDatHang() {
		// TODO Auto-generated method stub
		return this.DeNghiDatHangId;
	}
}
