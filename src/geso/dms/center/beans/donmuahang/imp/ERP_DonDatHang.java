package geso.dms.center.beans.donmuahang.imp;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import geso.dms.center.beans.donmuahang.IERP_DonDatHang;
import geso.dms.center.beans.donmuahang.IERP_DonDatHang_SP;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;

public class ERP_DonDatHang  implements IERP_DonDatHang{

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
	String NppId;
	String NppTen;
	String khoId;
	String KhoTen;
	String so;
	String DvkdId;
	
	String Diachi;
	String DiaChiXhd;
	String Masothue;
	String NgayDeNghiGH;
	double TongTienTruocVAT=0;
	double TongTienSauVAT=0;
	String IDNhaCungCap;
	String TenNhaCungCap;
	String KenhBanHangId;
	String ISKM;
	
	String Loaidonhang;
	String Doihang;
	
	/*
	 * loai chietkhau =0 la chiet khau tien,1 la chiet khau%
	 */
	String loaichietkhau="0";
	
	String SoSO="";
	
	ResultSet rskenh;
	ResultSet rskho;
	ResultSet rsnhapp;
	ResultSet rsnhacc;
	ResultSet rsdvkd;
	String userten;
	String ghichu;
	String noidungchietkhau;
	String[] Scheme;
	String[] Sotien;
	List<IERP_DonDatHang_SP> listsanpham=new ArrayList<IERP_DonDatHang_SP>();
	
	String LyDoHuy;
	
	Hashtable<String, Integer> spThieuList;
	

	Utility util;
	dbutils db;
/*
 * Phuong thuc khoi tao
 */
	public ERP_DonDatHang(String id)
	{
		util = new Utility();
		db=new dbutils();
		this.Id=id;
		spThieuList=new Hashtable<String, Integer>();
		
		String sql=
		"SELECT ISNULL(LYDOHUY,'') AS LYDOHUY ,ISNULL(DOIHANG,'0') AS DOIHANG, NPP.KHOSAP,ISNULL(DDH.GHICHU,'') AS GHICHU, "+
		"	ISNULL(DDH.NOIDUNGCHIETKHAU,'') AS NOIDUNGCHIETKHAU ,DDH.PK_SEQ,NGAYDAT,NGAYDENGHIGH,ISKM,DDH_SP.SOTIENBVAT AS SOTIENBVAT,DDH.NGUOITAO,DDH.NGUOISUA,DDH.TRANGTHAI, "+  
		"	NPP_FK,NCC_FK,DDH_SP.VAT AS VAT ,DDH_SP.SOTIENAVAT AS SOTIENAVAT,DVKD_FK,DENGHIDATHANG_FK "+  
		"	,KBH_FK,ISNULL(DDH_SP.CHIETKHAU,0) AS CHIETKHAU,ISNULL(LOAIDONHANG,'0') AS LOAIDONHANG, ISNULL(LOAICHIETKHAU,'0') AS LOAICHIETKHAU,NPP.TEN AS TENNPP,NPP.DIACHIXHD,NPP.DIACHI,NPP.MASOTHUE "+ 
		"FROM DONDATHANG DDH INNER JOIN "+ 
		"	( SELECT DONDATHANG_FK ,SUM(SOTIENAVAT) SOTIENAVAT,SUM(SOTIENBVAT)SOTIENBVAT,SUM(VAT)VAT ,SUM(CHIETKHAU)CHIETKHAU "+
		"		FROM  DONDATHANG_SP "+  
		"		GROUP BY DONDATHANG_FK "+
		"	)DDH_SP ON DDH_SP.DONDATHANG_FK=DDH.PK_SEQ  INNER JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ=NPP_FK   WHERE  DDH.PK_SEQ='"+this.Id+"'";
			
			
		ResultSet rs=db.get(sql);
		try{
			String khosap=null;
			if(rs.next()){
				this.Loaidonhang=rs.getString("loaidonhang");
				this.Doihang=rs.getString("doihang");
				
				this.NgayGiaoDich=rs.getString("ngaydat");
				this.NppId=rs.getString("npp_fk");
				this.setIdNhaCungCap(rs.getString("ncc_fk"));
 				this.setMessage("");
 				try{
 				this.setChietkhau(rs.getDouble("chietkhau"));
 				}catch(Exception er){
 					this.setChietkhau(0);
 				}
 				this.setISKM(rs.getString("iskm"));
 				this.setNgaygiaodich(rs.getString("ngaydat"));
 				this.setNppId(rs.getString("npp_fk"));
 				this.setIDKenhBanHang(rs.getString("kbh_fk"));
 				this.setdvkdid(rs.getString("dvkd_fk"));
 				this.setNgaydenghigh(rs.getString("ngaydenghigh"));
 				
 				this.setLydohuy(rs.getString("lydohuy"));
 				if(rs.getString("loaidonhang").equals("0")){
	 				
	 				try{
	 				this.setTongtientruocVAT(rs.getDouble("sotienbvat"));
	 				}catch(Exception er){
	 					this.setTongtientruocVAT(0);
	 				}
	 				try{
	 					this.setTongtiensauVAT(rs.getDouble("sotienavat"));
	 					System.out.println("tien sau vat :"+rs.getDouble("sotienavat"));
	 				}catch(Exception er){
	 					this.setTongtiensauVAT(0);
	 				}
	 				//System.out.println("hdhđ :"+this.getTongtiensauVAT());
	 				Double tiensauck=rs.getDouble("sotienbvat")-rs.getDouble("chietkhau");
	 				this.VAT=Math.round(rs.getDouble("vat")/ tiensauck *100);
	 				double tienchietkhau=Double.parseDouble(rs.getString("chietkhau"));
	 				if(this.getloaichietkhau().equals("1")){//chiet khauth %
	 					this.ChietKhau=tienchietkhau/this.getTongtientruocVAT() *100;
	 				}else{
	 					this.ChietKhau=tienchietkhau;
	 				}
	 				
 				}
 				
 				this.setLoaidonhang(rs.getString("loaidonhang"));
 				
 				this.setloaichietkhau(rs.getString("loaichietkhau"));
 				
 				
 				
 				
 			
 				this.setNppTen(rs.getString("tennpp"));
 				
 				this.setdiachi(rs.getString("diachi"));
 				this.setdiachixhd(rs.getString("diachixhd"));
 				this.setmasothue(rs.getString("masothue"));
 				
 				 khosap=rs.getString("khosap");
 				 this.setGhichu(rs.getString("ghichu"));
 				this.setNoidungchietkhau(rs.getString("noidungchietkhau"));
 				/*
 				 * thiet lap 3 thuoc tinh de truyen vao trang jsp list san pham
 				 */
 				
			}
			
			//Thuc hien lay tong tin don hang
		    	String sql_getdetail="select isnull(soluongduyet,0) as soluongduyet,isnull(ddh_sp.khott,0) as khott, isnull(erp_khott.ten,'') as tenkho, " +
		    			"dondathang_fk, ddh_sp.sanpham_fk, ma, a.ten, donvi, ddh_sp.soluong as soluong, scheme, isnull(a.trongluong, '0') as trongluong, isnull(a.thetich, '0') as thetich, " +
		    			" dongia  as dongia,qc.soluong1/qc.soluong2 as qc , (select available from erp_khott_sanpham  " +
		    			"where sanpham_fk=ddh_sp.sanpham_fk and khott_fk="+khosap+") as available " +
		    		"from dondathang_sp ddh_sp  inner join sanpham a on a.pk_seq=sanpham_fk  inner join dondathang " +
		    			"ddh on ddh.pk_Seq = ddh_sp.dondathang_fk inner join quycach qc on ddh_sp.sanpham_fk = qc.sanpham_fk  " +
		    			" left join erp_khott on  cast(erp_khott.pk_seq as varchar(10))= cast (ddh_sp.khott  as varchar(10)) " +
		    			" where (ddh_sp.soluongduyet > 0 or ddh_sp.soluong >0 )  and ddh_sp.dondathang_fk="+id;
		    	 System.out.println("Cau select la ngan vo hoai "+sql_getdetail);
		    	
		    	 rs=db.get(sql_getdetail);
		    	if(rs!=null)
		    	{
		    		while(rs.next()){
		    			
		    			IERP_DonDatHang_SP sanpham = new ERP_DonDatHang_SP();
		    			if(this.GetLoaidonhang().equals("0")){
		    			sanpham.setDonGia(rs.getDouble("dongia"));
		    			}else{
		    				sanpham.setDonGia(0);
		    			}
		    			sanpham.setDonViTinh(rs.getString("donvi"));
		    			sanpham.setId(rs.getString("dondathang_fk"));
		    			sanpham.setIdSanPham(rs.getString("sanpham_fk"));
		    			sanpham.setTenSanPham(rs.getString("ten"));
		    			sanpham.setMaSanPham(rs.getString("ma"));
		    			sanpham.setSoLuong(rs.getInt("soluong"));
		    			sanpham.setSHEME(rs.getString("scheme"));
		    			sanpham.setTrongluong(rs.getString("trongluong"));
		    			sanpham.setThetich(rs.getString("thetich"));
		    			sanpham.setSoluongton(rs.getInt("soluong") + rs.getInt("available"));
		    			sanpham.setKhoTT(rs.getString("khott"));
		    			sanpham.setTenKhoTT(rs.getString("tenkho"));
		    			sanpham.setSoluongduyet(rs.getInt("soluongduyet"));
		    			sanpham.setQuyCach(rs.getString("qc"));
		    			
		    			this.listsanpham.add(sanpham);
		    		}
		    	}
		    	//Thuc hien lay scheme
		    	String query="select * from DONDATHANG_SCHEME where DONDATHANG_FK=" + id;
		    	rs=db.get(query);
		    	if(rs!=null)
		    	{
		    		String a="";
		    		String b="";
		    		while(rs.next())
		    		{
		    			a += rs.getString("scheme")+ ",";
		    			b += rs.getString("sotien")+ ",";
		    			
		    		}
		    		if(a.length() > 0)
		    			a = a.substring(0, a.length() - 1);
		    		if(b.length() > 0)
		    			b = b.substring(0, b.length() - 1);	    			
		    		this.Scheme = a.split(",");
		    		this.Sotien = b.split(",");
		    	}
			if(rs!=null){
				rs.close();
			}
		}catch(Exception er){
			System.out.println("Errrro  here: Erp_dondathang.java line  : 129 "+er.toString());
		}
	}
	public ERP_DonDatHang(){
		db=new dbutils();
		util = new Utility();
		 Id="";
		 this.ISKM="";
		 NgayGiaoDich="";
		 TrangThai="";
		 NgayTao="";
		 NguoiTao="";
		 NgaySua="";
		 NguoiSua="";
		 ChietKhau=0;
		 VAT=10;
		 Msg="";
		 NppId="";
		 NppTen="";
		 khoId="";
		 KhoTen="";
		 so="";
		 DvkdId="";
		 TongTienTruocVAT=0;
		 TongTienSauVAT=0;
		 IDNhaCungCap="";
		 TenNhaCungCap="";
		 KenhBanHangId="";
		 SoSO="";
		  Diachi="";
			 DiaChiXhd="";
			 Masothue="";
			 this.ghichu="";
			 this.noidungchietkhau="";
			
	}
	
	public String getId() {
		
		return this.Id;
	}

	
	public void setId(String id) {
		
		this.Id=id;
	}

	
	public String getNgaygiaodich() {
		
		return this.NgayGiaoDich;
	}

	
	public void setNgaygiaodich(String ngaygiaodich) {
		
		this.NgayGiaoDich=ngaygiaodich;
	}

	
	public String getNppTen() {
		
		return this.NppTen;
	}

	
	public void setNppTen(String _nppTen) {
		
		this.NppTen=_nppTen;
	}

	
	public String getTrangthai() {
		
		return this.TrangThai;
	}

	
	public void setTrangthai(String trangthai) {
		
		this.TrangThai=trangthai;
	}

	
	public String getNgaytao() {
		
		return this.NgayTao;
	}

	
	public void setNgaytao(String ngaytao) {
		
		this.NgayTao=ngaytao;
	}

	
	public String getNguoitao() {
		
		return this.NguoiTao;
	}

	
	public void setNguoitao(String nguoitao) {
		
		this.NguoiTao=nguoitao;
	}

	
	public String getNgaysua() {
		
		return this.NgaySua;
	}

	
	public void setNgaysua(String ngaysua) {
		
		this.NgaySua=ngaysua;
	}

	
	public String getNguoisua() {
		
		return this.NguoiSua;
	}

	
	public void setNguoisua(String nguoisua) {
		
		this.NguoiSua=nguoisua;
	}

	
	public double getChietkhau() {
		
		return this.ChietKhau;
	}

	
	public void setChietkhau(double chietkhau) {
		
		this.ChietKhau=chietkhau;
	}

	
	public double getVAT() {
		
		return this.VAT;
	}

	
	public void setVAT(double vat) {
		
		this.VAT=vat;
	}

	
	public String getMessage() {
		
		return this.Msg;
	}

	
	public void setMessage(String msg) {
		
		this.Msg=msg;
	}

	
	public void setrs_nhacc(ResultSet rsncc) {
		
		this.rsnhacc=rsncc;
	}

	
	public ResultSet GetRsnhacc() {
		
		return this.rsnhacc;
	}

	
	public String getIdNhaCungCap() {
		
		return this.IDNhaCungCap;
	}

	
	public void setIdNhaCungCap(String idnhacc) {
		
		this.IDNhaCungCap=idnhacc;
	}

	
	public String getTenNhaCungCap() {
		
		return this.TenNhaCungCap;
	}

	
	public void setTenNhaCungCap(String tennhacc) {
		
		this.TenNhaCungCap=tennhacc;
	}

	
	public void setListSanPham(List<IERP_DonDatHang_SP> list) {
		
		this.listsanpham=list;
	}

	
	public String getIDKenhBanHang() {
		
		return this.KenhBanHangId;
	}

	

	
	public void setIDKenhBanHang(String kenhbanhangid) {
		
		this.KenhBanHangId=kenhbanhangid;
	}
	
	public void setrs_kbh(ResultSet _rskenh) {
		
		this.rskenh=_rskenh;
	}

	
	public ResultSet GetRsKbh() {
		
		return this.rskenh;
	}
	
	public List<IERP_DonDatHang_SP> getListSanPham() {
		
		return this.listsanpham;
	}

	
	public String getNppId() {
		
		return this.NppId;
	}

	
	public void setNppId(String nppId) {
		
		this.NppId=nppId;
	}

	
	public void setrs_nhapp(ResultSet rsnpp) {
		
		this.rsnhapp=rsnpp;
	}

	
	public ResultSet GetRsnhapp() {
		
		return this.rsnhapp;
	}

	
	public String getKhottId() {
		
		return this.khoId;
	}

	
	public void setKhottId(String khottid) {
		
		this.khoId=khottid;
	}

	
	public void setrs_khott(ResultSet rs_kho) {
		
		this.rskho=rs_kho;
	}

	
	public ResultSet GetRskhott() {
		
		return this.rskho;
	}

	
	public String getKhottTen() {
		
		return this.KhoTen;
	}

	
	public void setKhottTen(String KhottTen) {
		
		this.KhoTen=KhottTen;
	}

	
	public double getTongtientruocVAT() {
		
		return this.TongTienTruocVAT;
	}

	
	public void setTongtientruocVAT(double tttvat) {
		
		this.TongTienTruocVAT=tttvat;
	}

	
	public double getTongtiensauVAT() {
		
		return this.TongTienSauVAT;
	}

	
	public void setTongtiensauVAT(double ttsvat) {
		
		this.TongTienSauVAT=ttsvat;
	}

	
	public void Init() {
		
		CreateRs();
	}
	
	private void CreateRs(){
		
		String sql="select pk_seq,ten from nhaphanphoi where trangthai!='2'";
		 this.rsnhapp=db.get(sql);
	
		
		 sql="select pk_seq,ten from nhacungcap where trangthai!='2'";
		this.rsnhacc=db.get(sql);
	
		
		sql="select pk_seq,ten from kenhbanhang where trangthai!='2' ";
		this.rskenh=db.get(sql);

		 sql="select pk_seq,donvikinhdoanh as ten from donvikinhdoanh where trangthai='1'";
		this.rsdvkd =db.get(sql);
		
		this.rskho=db.get("select pk_seq,ten from erp_khott");
		
	}
	
	
	public Hashtable<String, Integer> getSpThieuList() {
		
		return this.spThieuList;
	}

	
	public void setSpThieuList(Hashtable<String, Integer> spThieuList) {
		
		this.spThieuList=spThieuList;
	}

	
	public boolean Save() {
		
		
		String sql="";
			
			try
			{
				if(this.spThieuList!=null)
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
				
				  db.getConnection().setAutoCommit(false);
				 
				  
				
					  
				 
			 sql=" insert into dondathang(NGAYDAT,TRANGTHAI,NGUOITAO,NGUOISUA,NPP_FK,DVKD_FK,NCC_FK,kbh_FK,loaidonhang,iskm,tinhtrang,KHOTT_FK) "+
			 " values ('"+this.NgayGiaoDich+"','1',"+this.NguoiTao+","+this.NguoiSua+","+this.NppId+","+this.DvkdId+","+this.IDNhaCungCap+"," + this.KenhBanHangId+" ,'1','0','1','"+this.khoId+"')  ";
			
			if(!db.update(sql)){
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				this.Msg="Khong The Cap Nhat Hoa Don ,Loi Tren Dong Lenh Sau :" + sql;
				return false;
			}	
			//Save chi tiet don hang
			
			
			String query = "select IDENT_CURRENT('dondathang') as dhId";
			ResultSet rsDh = db.get(query);	
			try
			{
				rsDh.next();
		     	this.Id = rsDh.getString("dhId");
		     	rsDh.close();
			}
			catch(Exception er){
				
				System.out.println("Kho :"+sql);
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				//System.out.println("Error : DonHangNpp: line 291 : " + er.toString());
				this.setMessage("Loi  - Clasname :Erp_DonMuaHang - line 293 : "+ er.toString());
				return false;
			}
			String chuoi_scheme="";
			
			if(this.Scheme != null)
			{
				for(int j = 0; j < this.Scheme.length;j++)
				{
					if(this.Scheme[j]!="" && this.Sotien[j]!="")
					{
							if (j==0){
							chuoi_scheme=this.Scheme[j] ;
							}else{
								chuoi_scheme=chuoi_scheme +"    /  "+ ""+this.Scheme[j];
							}
						String caulenh ="insert into DONDATHANG_SCHEME(DONDATHANG_FK,SCHEME,SOTIEN) VALUES( "+this.Id+",'"+this.Scheme[j]+"',"+this.Sotien[j]+ " )";
						if(!db.update(caulenh)){
							
							this.Msg="Loi Nhap Lieu,Vui Long Xem Lai.Error :" +caulenh;
							 geso.dms.center.util.Utility.rollback_throw_exception(db);
								return false;
						}
					}
				}
			}
			if(chuoi_scheme.equals("")){
				chuoi_scheme=this.noidungchietkhau;
			}
			
			//
			
			int count = 0;
			double tongtien=0;
			
			while(count < this.listsanpham.size())
			{

				IERP_DonDatHang_SP sanpham = new ERP_DonDatHang_SP();
				sanpham =listsanpham.get(count);
				if(sanpham.getSoLuong() <=0  || sanpham.getDonGia() <=0)
				{
					this.Msg="Khong the luu ma san pham : "+ sanpham.getMaSanPham()+" ,Cap Nhat So Luong Va Gia Lon hon 0";
					geso.dms.center.util.Utility.rollback_throw_exception(db);
					return false;
				}
				tongtien=tongtien+ sanpham.getSoLuong()*sanpham.getDonGia();
				count=count+1;
				
			}
			double tienchietkhau=0;
			
			
			if(this.loaichietkhau.equals("1")){
				tienchietkhau=tongtien * this.ChietKhau /100; 
			}else{
				tienchietkhau=this.ChietKhau;
			}
			
			count = 0;
			double phantramck=tienchietkhau/tongtien*100;
			
			System.out.println("Phan tran chiet khau :"+ phantramck);
		
					while(count < this.listsanpham.size())
					{
						IERP_DonDatHang_SP sanpham = new ERP_DonDatHang_SP();
						sanpham =listsanpham.get(count);
						sanpham.setId(this.Id);
						double thanhtien=sanpham.getSoLuong()* sanpham.getDonGia();
						double chietkhausp=thanhtien/100 * phantramck;
						double vatsp=(thanhtien-chietkhausp)*this.VAT /100;
						double tienavat=(thanhtien-chietkhausp) +vatsp;
						
						sql="insert into dondathang_sp (sanpham_fk,dondathang_fk,soluong,dongia,donvi,sotienbvat ,chietkhau,vat,sotienavat,scheme)values (" 
							+sanpham.getIdSanPham()+","+sanpham.getId()+","+sanpham.getSoLuong()+","+Math.round(sanpham.getDonGia())+",N'"+sanpham.getDonViTinh()+"','"+thanhtien+"','"+ chietkhausp  +"','"+vatsp+"','"+tienavat+"','"+chuoi_scheme+"')";
						System.out.println("them chi tiet :"+sql);
						if(!db.update(sql)){
							System.out.println("Kho :"+sql);
							this.Msg="Khong the luu ma san pham : "+ sanpham.getMaSanPham()+" ,Loi trong dong lenh sau : " + sql;
							geso.dms.center.util.Utility.rollback_throw_exception(db);
							return false;
						}
						
					//Cap nhat lai kho, book tang con avaliable giam,day la chinh la dat hang do nha phan phoi dat len,phai cap nhat lai kho giong nhu nha phan phoi chot phieu dat hang
							 sql = "update erp_khott_sanpham set booked = booked + '" + sanpham.getSoLuong() + "', available = available - '" + sanpham.getSoLuong() + "' where sanpham_fk='" + sanpham.getIdSanPham() +"' and khott_fk='" + this.khoId + "'";
						//	System.out.println("Kho :"+sql);
							 if(!db.update(sql)){
								 this.Msg="Error update KHO : line -672 : ErpDonMuaHang : "+sql;
						
								   geso.dms.center.util.Utility.rollback_throw_exception(db);
									return false;
						    }
						
					  
					count++;
					
					}
			double vat=(tongtien -tienchietkhau) * this.VAT /100;
			double tongtiencovat=(tongtien -tienchietkhau)+ vat ;
			
			
			if(tienchietkhau > tongtien )
			{
				this.Msg="Bạn đã nhập tổng tiền chiết khấu lớn hơn 100% giá trị đơn hàng,vui lòng kiểm tra lại";
				geso.dms.center.util.Utility.rollback_throw_exception(this.db);
				return false;
			}
			sql = "update dondathang set sotienavat='"+tongtiencovat+"',vat='"+vat+"',sotienbvat='"+Math.round(tongtien)+"',chietkhau='"+tienchietkhau+"',loaichietkhau='"+this.loaichietkhau+"',ghichu = N'"+this.ghichu+"',noidungchietkhau = N'"+this.noidungchietkhau+"'  where pk_Seq="+this.Id;
			System.out.println("tien VAT (erp_dondathang - 725):"+sql);
			if(!db.update(sql)){
				
				this.Msg="Loi Nhap Lieu,Vui Long Xem Lai.Error :" +sql;
				 geso.dms.center.util.Utility.rollback_throw_exception(db);
					return false;
			}
		/*
		 * Chuong trinh 
		 */
			
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			}
			catch(Exception er){
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				this.setMessage( er.toString() + " Error : " +  sql);
				
				
				return false;
			}
			return true;
	}

	public boolean SaveKm() {
		
		
		String sql="";
			
			try
			{
				if(this.spThieuList!=null)
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
				
				  db.getConnection().setAutoCommit(false);
				  //String vatnew="";
				  this.VAT=10;
			
			 sql=" insert into dondathang(NGAYDAT,TRANGTHAI,NGUOITAO,NGUOISUA,NPP_FK,DVKD_FK,NCC_FK,kbh_FK,loaidonhang,ISKM, ghichu,tinhtrang,khott_fk) "+
			 " values ('"+this.NgayGiaoDich+"','1',"+this.NguoiTao+","+this.NguoiSua+","+this.NppId+","+this.DvkdId+","+this.IDNhaCungCap+"," + this.KenhBanHangId+" ,'1','1', N'" + this.ghichu + "','1','"+this.khoId+"')  ";

			
			if(!db.update(sql)){
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				this.Msg="Khong The Cap Nhat Hoa Don ,Loi Tren Dong Lenh Sau :" + sql;
				return false;
			}	
			//Save chi tiet don hang
			int count = 0;
			double tongtien=0;
			
			
			String query = "select IDENT_CURRENT('dondathang') as dhId";
			ResultSet rsDh = db.get(query);	
			try
			{
				rsDh.next();
		     	this.Id = rsDh.getString("dhId");
		     	rsDh.close();
			}
			catch(Exception er){
				
				System.out.println("Kho :"+sql);
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				//System.out.println("Error : DonHangNpp: line 291 : " + er.toString());
				this.setMessage("Loi  - Clasname :Erp_DonMuaHang - line 293 : "+ er.toString());
			}
			while(count < this.listsanpham.size())
			{
				IERP_DonDatHang_SP sanpham = new ERP_DonDatHang_SP();
				sanpham =listsanpham.get(count);
				
				sanpham.setId(this.Id);
				double thanhtien=sanpham.getSoLuong()* sanpham.getDonGia();
				sql="insert into dondathang_sp (sanpham_fk,dondathang_fk,soluong,dongia,donvi,sotienbvat,vat,sotienavat,scheme)values (" 
					+sanpham.getIdSanPham()+","+sanpham.getId()+","+sanpham.getSoLuong()+","+Math.round(sanpham.getDonGia())+",N'"+sanpham.getDonViTinh()+"','"+thanhtien+"','"+thanhtien*0.1+"','"+thanhtien* 1.1+"','"+sanpham.getSHEME()+ "')";
				//System.out.println("Error line 49 :Classname:  SanPhamDHNpp : Cau lenh SQL " + sql);
				System.out.println("Them chi tiet :"+sql);
				
				tongtien=tongtien+ sanpham.getSoLuong()*sanpham.getDonGia();
				if(!db.update(sql)){
					System.out.println("Kho :"+sql);
					this.Msg="Khong the luu ma san pham : "+ sanpham.getMaSanPham()+" ,Loi trong dong lenh sau : " + sql;
					geso.dms.center.util.Utility.rollback_throw_exception(db);
					return false;
				}
				
			//Cap nhat lai kho, book tang con avaliable giam,day la chinh la dat hang do nha phan phoi dat len,phai cap nhat lai kho giong nhu nha phan phoi chot phieu dat hang
					 sql = "update erp_khott_sanpham set booked = booked + '" + sanpham.getSoLuong() + "', available = available - '" + sanpham.getSoLuong() + "' where sanpham_fk='" + sanpham.getIdSanPham() +"' and khott_fk='" + this.khoId + "'";
					//System.out.println("Kho :"+sql);
					 if(!db.update(sql)){
						 this.Msg="Error update KHO : line -672 : ErpDonMuaHang : "+sql;
				
						 geso.dms.center.util.Utility.rollback_throw_exception(db);
							return false;
				    }
				
			    //sanpham.SavaSanPhamDhNpp();
			count++;
			
			}
			double tienchietkhau=0;
			
			
			if(this.loaichietkhau.equals("1")){
				tienchietkhau=tongtien * this.ChietKhau /100; 
			}else{
				tienchietkhau=this.ChietKhau;
			}
			//System.out.println("tien chiet khau"+tienchietkhau);
			//System.out.println("tien "+tongtien);
			double tongtiencovat=(tongtien -tienchietkhau) *1.1;
			double vat=(tongtien -tienchietkhau) *0.1;
			
			sql="update dondathang set sotienavat='"+tongtiencovat+"',vat='"+vat+"',sotienbvat='"+tongtien+"',chietkhau='"+tienchietkhau+"',loaichietkhau='"+this.loaichietkhau+"' where pk_Seq="+this.Id;
			//System.out.println("Kho :"+sql);
			if(!db.update(sql)){
				
				this.Msg="Loi Nhap Lieu,Vui Long Xem Lai.Error :" +sql;
				 geso.dms.center.util.Utility.rollback_throw_exception(db);
					return false;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			}
			catch(Exception er){
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				this.setMessage( er.toString() + " Error : " +  sql);
				
				
				return false;
			}
			return true;
	}
	
	public boolean Edit(String ischot) 
	{
		
		
			
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
				if(this.NguoiTao.toString().equals("null")|| this.NguoiTao.equals("")){
					this.Msg="Nguoi Dung Dang Nhap Da Bi Log Vi ly Do Bao Mat He Thong, Vui Long Dang Nhap Lai De Thuc Hien Chuong Trinh";
				    return false;
				}
				  db.getConnection().setAutoCommit(false);//chu y
				
					
			
			
			//Khi Chot Don Hang thi thuc hien cap nhat lai sotien ton duoc tra khuyen mai cua nha phan phoi
			

			/*//lay chi tiet don hang cu ra va cap nhat lai so luong check va available ,dong thoi cap nhat lai cot dadathang trong bang denghidathang_sp
			String sql="select soluong,sanpham_fk,npp.khosap from dondathang_sp inner join dondathang ddh on ddh.pk_seq=dondathang_fk "+ 
			 " inner join nhaphanphoi npp on npp.pk_Seq=ddh.npp_fk  where dondathang_fk= "+this.Id ;
			//System.out.println("Get Detail :"+ sql_getdetail_ddh);
			ResultSet rs_detail_ddh=db.get(sql);
			if(rs_detail_ddh!=null){
				while (rs_detail_ddh.next())
				{
				sql = "update  erp_khott_sanpham set booked = booked - '" +rs_detail_ddh.getInt("soluong") + "', available = available + '" + rs_detail_ddh.getInt("soluong") + "' where sanpham_fk='" + rs_detail_ddh.getString("sanpham_fk") +"' and khott_fk='" + rs_detail_ddh.getString("khosap")+ "'";
					//Truoc khi xoa chi tiet hang cu nay di thi cap nhap lai so luong dat va available sau do them chi tiet hang moi vao va cap nhat lai so luong trong kho
					//System.out.println("Error update KHO : line -524 : HoaDon : "+query_updatekho);
					if(!db.update(sql)){
						System.out.println("Error update KHO : line -524 : HoaDon : "+sql);
						geso.dms.center.util.Utility.rollback_throw_exception(db);
						return false;
					}
					
					
				}
			}*/
			//xoa het chi tiet cu 
			String sql="delete from dondathang_sp where dondathang_fk="+ this.Id;
			if(!db.update(sql)){
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				this.Msg="Khong The Duyet Hoa Don ,Loi Tren Dong Lenh Sau :" + sql;
				return false;
			}
			//Xoa scheme
			sql="delete from DONDATHANG_SCHEME where dondathang_fk="+ this.Id;
			if(!db.update(sql)){
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				this.Msg="Khong The Duyet Scheme ,Loi Tren Dong Lenh Sau :" + sql;
				return false;
			}
			//se luu trong dondathang_sp neu co scheme cua chiet khau 
			String chuoi_scheme="";
		
			
			if(this.Scheme != null)
			{
				for(int j = 0; j < this.Scheme.length;j++)
				{
					if(this.Scheme[j]!="" && this.Sotien[j]!="")
					{
						if (j==0){
						chuoi_scheme=this.Scheme[j] ;
						}else{
							chuoi_scheme=chuoi_scheme +"    /  "+ ""+this.Scheme[j];
						}
					
						String caulenh ="insert into DONDATHANG_SCHEME(DONDATHANG_FK,SCHEME,SOTIEN) VALUES( "+this.Id+",N'"+this.Scheme[j]+"',"+this.Sotien[j]+ " )";
						if(!db.update(caulenh)){
							
							this.Msg="Loi Nhap Lieu,Vui Long Xem Lai.Error :" +caulenh;
							 geso.dms.center.util.Utility.rollback_throw_exception(db);
								return false;
						}
					}
				}
			}
			
			if(chuoi_scheme.equals("")){
				chuoi_scheme=this.noidungchietkhau;
			}
			//Save chi tiet don hang
			int count = 0;
			double tongtien=0;
			
			while(count < this.listsanpham.size())
			{
				IERP_DonDatHang_SP sanpham = new ERP_DonDatHang_SP();
				sanpham =listsanpham.get(count);
				if(sanpham.getsoluongduyet() <=0  || sanpham.getDonGia() <=0)
				{
					this.Msg="Khong the luu ma san pham : "+ sanpham.getMaSanPham()+" ,Cap Nhat So Luong Va Gia Lon hon 0";
					geso.dms.center.util.Utility.rollback_throw_exception(db);
					return false;
				}
				tongtien=tongtien+ sanpham.getsoluongduyet() *sanpham.getDonGia();
				count=count+1;
				
			}
			double tienchietkhau=0;
			
			
			if(this.loaichietkhau.equals("1")){
				tienchietkhau=tongtien * this.ChietKhau /100; 
			}else{
				tienchietkhau=this.ChietKhau;
			}
			
			count = 0;
			double phantramck=tienchietkhau/tongtien*100;
			
			while(count < this.listsanpham.size())
			{
				IERP_DonDatHang_SP sanpham = new ERP_DonDatHang_SP();
				
				sanpham=listsanpham.get(count);	
				
				///if(sanpham.getSoLuong()>0){
				
					double thanhtien=sanpham.getsoluongduyet()* sanpham.getDonGia();
					double chietkhausp=thanhtien/100 * phantramck;
					double vatsp=(thanhtien-chietkhausp)*this.VAT /100;
					double tienavat=(thanhtien-chietkhausp) +vatsp;
					double quycach=0;
					try{
						quycach=Double.parseDouble(sanpham.getQuyCach());
					}catch (Exception e) {
						// TODO: handle exception
					}
					sql="insert into dondathang_sp (sanpham_fk,dondathang_fk,soluong,soluongduyet,dongia,donvi,sotienbvat ,chietkhau,vat,sotienavat,scheme,khott)values (" 
						+sanpham.getIdSanPham()+","+this.Id+","+sanpham.getSoLuong()* quycach+",'"+sanpham.getsoluongduyet()*quycach+"' ,"+Math.round(sanpham.getDonGia())+",N'"+sanpham.getDonViTinh()+"','"+thanhtien+"','"+ chietkhausp  +"','"+vatsp+"','"+tienavat+"','"+chuoi_scheme+"','"+sanpham.getKhoTT()+"')";
					System.out.println("them chi tiet (update) :"+sql);
					if(!db.update(sql)){
						System.out.println("Kho :"+sql);
						this.Msg="Khong the luu ma san pham : "+ sanpham.getMaSanPham()+" ,Loi trong dong lenh sau : " + sql;
						geso.dms.center.util.Utility.rollback_throw_exception(db);
						return false;
					}
					
					/*//Cap nhat lai kho, book tang con avaliable giam,day la chinh la dat hang do nha phan phoi dat len,phai cap nhat lai kho giong nhu nha phan phoi chot phieu dat hang
					 sql = "update erp_khott_sanpham set booked = booked + '" + sanpham.getSoLuong() + "', available = available - '" + sanpham.getSoLuong() + "' where sanpham_fk='" + sanpham.getIdSanPham() +"' and khott_fk='" + this.khoId + "'";
					//System.out.println("Kho :"+sql);
					 if(!db.update(sql)){
						 this.Msg="Error update KHO : line -672 : ErpDonMuaHang : "+sql;
						 geso.dms.center.util.Utility.rollback_throw_exception(db);
							return false;
				    }*/
				//}
			  
			count++;
			
			}
		
			
			
			
			//System.out.println("tien chiet khau"+tienchietkhau);
			//System.out.println("tien "+tongtien);
			double vat=(tongtien -tienchietkhau) *this.VAT /100;
			double tongtiencovat=(tongtien -tienchietkhau) +vat;
			
			
			if(tienchietkhau > tongtien )
			{
				this.Msg="Bạn đã nhập tổng tiền chiết khấu lớn hơn 50% giá trị đơn hàng,vui lòng kiểm tra lại";
				geso.dms.center.util.Utility.rollback_throw_exception(this.db);
				return false;
			}
			
						
			if(ischot.equals("1")){
				 sql="  update dondathang set khott_fk='"+this.khoId+"', trangthai='2', NGUOISUA="+this.NguoiSua+",VAT='"+vat+
					"',SOTIENBVAT="+Math.round(tongtien)+" ,SOTIENAVAT="+tongtiencovat+", npp_fk='"+this.NppId+"',dvkd_fk='"+this.DvkdId+"',kbh_fk='"+this.KenhBanHangId+"' ,chietkhau='"+tienchietkhau+"',loaichietkhau='"+this.loaichietkhau+"',ghichu = N'" + this.ghichu + "',noidungchietkhau = N'"+this.noidungchietkhau+"',NGAYDAT='"+this.NgayGiaoDich+"' where pk_seq="+this.Id ;
				 System.out.println("SQL ischot=1 :"+sql);
			}else{
				 sql="  update dondathang set khott_fk='"+this.khoId+"',  NGUOISUA="+this.NguoiSua+",VAT='"+vat+
					"',SOTIENBVAT="+Math.round(tongtien)+" ,SOTIENAVAT="+tongtiencovat+" ,npp_fk='"+this.NppId+"',dvkd_fk='"+this.DvkdId+"',kbh_fk='"+this.KenhBanHangId+"' ,chietkhau='"+tienchietkhau+"',loaichietkhau='"+this.loaichietkhau+"',ghichu = N'" + this.ghichu + "',noidungchietkhau = N'"+this.noidungchietkhau+"',NGAYDAT='"+this.NgayGiaoDich+"' where pk_seq="+this.Id ;
				 System.out.println("SQL ischot#1 :"+sql);
			}
			 
			if(!db.update(sql)){
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				this.Msg="Khong The Duyet Hoa Don ,Loi Tren Dong Lenh Sau :" + sql;
				return false;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			}
			catch(Exception er){
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				this.setMessage(er.toString());
				System.out.println("Errorr : HoaDon: line 625 "+ er.toString());
				
				return false;
			}
			return true;
	}
	public boolean Editkm(String ischot) 
	{
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
				if(this.NguoiTao.toString().equals("null")|| this.NguoiTao.equals("")){
					this.Msg="Nguoi Dung Dang Nhap Da Bi Log Vi ly Do Bao Mat He Thong, Vui Long Dang Nhap Lai De Thuc Hien Chuong Trinh";
				    return false;
				}
				  db.getConnection().setAutoCommit(false);//chu y
				
					
			
			
			//Khi Chot Don Hang thi thuc hien cap nhat lai sotien ton duoc tra khuyen mai cua nha phan phoi
			

			//lay chi tiet don hang cu ra va cap nhat lai so luong check va available ,dong thoi cap nhat lai cot dadathang trong bang denghidathang_sp
			String sql="select soluong,sanpham_fk from dondathang_sp where dondathang_fk="+ this.Id;
			//System.out.println("Get Detail :"+ sql_getdetail_ddh);
			ResultSet rs_detail_ddh=db.get(sql);
			if(rs_detail_ddh!=null){
				while (rs_detail_ddh.next())
				{
				sql = "update  erp_khott_sanpham set booked = booked - '" +rs_detail_ddh.getInt("soluong") + "', available = available + '" + rs_detail_ddh.getInt("soluong") + "' where sanpham_fk='" + rs_detail_ddh.getString("sanpham_fk") +"' and khott_fk='" + this.khoId + "'";
					//Truoc khi xoa chi tiet hang cu nay di thi cap nhap lai so luong dat va available sau do them chi tiet hang moi vao va cap nhat lai so luong trong kho
					//System.out.println("Error update KHO : line -524 : HoaDon : "+query_updatekho);
					if(!db.update(sql)){
						System.out.println("Error update KHO : line -524 : HoaDon : "+sql);
						geso.dms.center.util.Utility.rollback_throw_exception(db);
						return false;
					}
					
					
				}
			}
			//xoa het chi tiet cu 
			 sql="delete from dondathang_sp where dondathang_fk="+ this.Id;
			if(!db.update(sql)){
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				this.Msg="Khong The Duyet Hoa Don ,Loi Tren Dong Lenh Sau :" + sql;
				return false;
			}
			//Save chi tiet don hang
			int count = 0;
			double tongtien=0;
			while(count < this.listsanpham.size())
			{
				IERP_DonDatHang_SP sanpham = new ERP_DonDatHang_SP();
				sanpham=listsanpham.get(count);	
				if(sanpham.getSoLuong()>0){
				
					double thanhtien=sanpham.getSoLuong()* sanpham.getDonGia();
					sql="insert into dondathang_sp (sanpham_fk,dondathang_fk,soluong,dongia,donvi,sotienbvat,vat,sotienavat,scheme)values (" 
						+sanpham.getIdSanPham()+","+this.Id+","+sanpham.getSoLuong()+","+Math.round(sanpham.getDonGia())+",N'"+sanpham.getDonViTinh()+"','"+thanhtien+"','"+thanhtien*0.1+"','"+thanhtien* 1.1+"','"+sanpham.getSHEME()+"')";
					//System.out.println("Error line 49 :Classname:  SanPhamDHNpp : Cau lenh SQL " + sql);
					//System.out.println("Cau lenh sql la xxxxxxxngan" +sql);
					if(!db.update(sql)){
						this.Msg="Khong the luu ma san pham : "+ sanpham.getMaSanPham()+" ,Loi trong dong lenh sau : " + sql;
						geso.dms.center.util.Utility.rollback_throw_exception(db);
						return false;
					}
					
					tongtien=tongtien+ thanhtien;
					//Cap nhat lai kho, book tang con avaliable giam,day la chinh la dat hang do nha phan phoi dat len,phai cap nhat lai kho giong nhu nha phan phoi chot phieu dat hang
					 sql = "update erp_khott_sanpham set booked = booked + '" + sanpham.getSoLuong() + "', available = available - '" + sanpham.getSoLuong() + "' where sanpham_fk='" + sanpham.getIdSanPham() +"' and khott_fk='" + this.khoId + "'";
					//System.out.println("Kho :"+sql);
					 if(!db.update(sql)){
						 this.Msg="Error update KHO : line -672 : ErpDonMuaHang : "+sql;
				
						 geso.dms.center.util.Utility.rollback_throw_exception(db);
							return false;
				    }
				}
			    //sanpham.SavaSanPhamDhNpp();
			count++;
			
			}
			double tienchietkhau=0;
			

			double tongtiencovat=(tongtien -tienchietkhau) *1.1;
			double vat=(tongtien -tienchietkhau) *0.1;
						
			if(ischot.equals("1")){
				 sql="  update dondathang set khott_fk='"+this.khoId+"', trangthai='2', NGUOISUA="+this.NguoiSua+",VAT='"+vat+
					"',SOTIENBVAT="+Math.round(tongtien)+" ,SOTIENAVAT="+Math.round(tongtiencovat)+" ,npp_fk='"+this.NppId+"',dvkd_fk='"+this.DvkdId+"',kbh_fk='"+this.KenhBanHangId+"' ,chietkhau='"+tienchietkhau+"',loaichietkhau='"+this.loaichietkhau+"', ghichu = N'" + this.ghichu + "' where pk_seq="+this.Id ;

			}else{
				 sql="  update dondathang set khott_fk='"+this.khoId+"',  NGUOISUA="+this.NguoiSua+",VAT='"+vat+
					"',SOTIENBVAT="+Math.round(tongtien)+" ,SOTIENAVAT="+Math.round(tongtiencovat)+" ,npp_fk='"+this.NppId+"',dvkd_fk='"+this.DvkdId+"',kbh_fk='"+this.KenhBanHangId+"' ,chietkhau='"+tienchietkhau+"',loaichietkhau='"+this.loaichietkhau+"', ghichu = N'" + this.ghichu + "' where pk_seq="+this.Id ;

			}
			 
			if(!db.update(sql)){
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				this.Msg="Khong The Duyet Hoa Don ,Loi Tren Dong Lenh Sau :" + sql;
				return false;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			}
			catch(Exception er){
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				this.setMessage(er.toString());
				System.out.println("Errorr : HoaDon: line 625 "+ er.toString());
				
				return false;
			}
			return true;
	}
	
	public boolean Delete() {
		
		return false;
	}
	
	public ResultSet getrsdvkd() {
		
		return this.rsdvkd;
	}
	
	public void setrsdvkd(ResultSet rsdvkd) {
		
		this.rsdvkd=rsdvkd;
	}
	
	public void setdvkdid(String dvkdid) {
		
		this.DvkdId=dvkdid;
	}
	
	public String getdvkdid() {
		
		return DvkdId;
	}
	
	public void setUserTen(String _Userten) {
		
		this.userten=_Userten;
	}
	
	public String getUserten() {
		
		return this.userten;
	}
	
	public void DBClose() {
		try{
		
		if( rskenh!=null){
			rskenh.close();
		}
		if( rskho!=null){
			rskho.close();
		}
		
		if( rsnhapp!=null){
			rsnhapp.close();
		}

		if( rsnhacc!=null){
			rsnhacc.close();
		}

		if( rsdvkd!=null){
			rsdvkd.close();
		}
		if(db!=null){
			db.shutDown();
		}
		}catch(Exception err){
			
		}
	}
	
	public String GetLoaidonhang() {
		
		return this.Loaidonhang;
	}
	
	public void setLoaidonhang(String loaidonhang) {
		this.Loaidonhang=loaidonhang;
		
	}
	
	public void setloaichietkhau(String _loaichietkhau) {
		
		loaichietkhau=_loaichietkhau;
	}
	
	public String getloaichietkhau() {
		
		return this.loaichietkhau;
	}
	
	public String getdiachi() {
		
		return this.Diachi;
	}
	
	public String getdiachixhd() {
		
		return this.DiaChiXhd;
	}
	
	public String getmasothue() {
		
		return this.Masothue;
	}
	
	public void setdiachi(String diachi) {
		
		this.Diachi=diachi;
	}
	
	public void setdiachixhd(String diachixhd) {
		
		this.DiaChiXhd=diachixhd;
	}
	
	public void setmasothue(String mst) {
		
		this.Masothue=mst;
	}
	
	public String getISKM() {
		
		return this.ISKM;
	}
	
	public void setISKM(String iskm) {
		this.ISKM=iskm;
		
	}

	public String[] getSotien() 
	{
		return this.Sotien;
	}

	public void setSotien(String[] sotien) 
	{
		this.Sotien=sotien;
	}

	public String[] getScheme() 
	{
		return this.Scheme;
	}

	public void setScheme(String[] scheme)
	{
		this.Scheme=scheme;
		
	}
	public String getGhichu() 
	{
		return this.ghichu;
	}
	public void setGhichu(String ghichu) 
	{
		this.ghichu=ghichu;
	}
	public String getNoidungchietkhau() 
	{
		return this.noidungchietkhau;
	}
	public void setNoidungchietkhau(String noidungchietkhau) 
	{
		this.noidungchietkhau = noidungchietkhau;
	}
	@Override
	public String getNgaydenghigh() {
		// TODO Auto-generated method stub
		return this.NgayDeNghiGH;
	}
	@Override
	public void setNgaydenghigh(String ngaydenghigh) {
		// TODO Auto-generated method stub
		this.NgayDeNghiGH=ngaydenghigh;
	}
	@Override
	public void setDoihang(String doihang) {
		// TODO Auto-generated method stub
		this.Doihang=doihang;
	}
	@Override
	public String getDoihang() {
		// TODO Auto-generated method stub
		return this.Doihang;
	}
	@Override
	public void setLydohuy(String Lydohuy) {
		// TODO Auto-generated method stub
		this.LyDoHuy=Lydohuy;
	}
	@Override
	public String getLyDohuy() {
		// TODO Auto-generated method stub
		return this.LyDoHuy;
	}
	

}
