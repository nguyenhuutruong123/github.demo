/**
 * author: khoand : date 2011-10-20
 */
package geso.dms.distributor.beans.donhangnhapp.imp;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;
import geso.dms.distributor.beans.donhangnhapp.IDonhangnpp;
import geso.dms.distributor.beans.donhangnhapp.ISanPhamDhNpp;
import geso.dms.distributor.db.sql.dbutils;
import geso.dms.distributor.util.Utility;

public  class DonHangNPP implements IDonhangnpp, Serializable
{

	dbutils db ;
	private String userID="0";
	private String _ID="0";
	private String NgayTao ="";
	private String Trangthai="" ;
	private String Nguoitao="0";
	private String NgaySua="0" ;
	private String Nguoisua ="0";
	private String Chietkhau="0" ;
	private String Vat ="0";
	private String Id_NhaPPMua ="0";
	private String Id_NhaPPBan="0" ;
	private String Kho="0";
	private String KenhBanhang="";
	private float DaThanhToan=0;
	private float TongGiaTri=0;
	private String TenNhaPPMua="";
	private String TenNhaPPBan="";
	private String NgayGiaoDich="";
	private double _TongTien=0;
	private String thongbao="";
	private Hashtable<String, Integer>  sanphamthieulist;//chua nhung san pham co so luong ton kho khong du cua don hang
	private List<ISanPhamDhNpp> sanphamlist=new ArrayList<ISanPhamDhNpp>() ;
	//don hang nha phan phoi nhan
	ResultSet rs_listdonhang;
	String ngaynhanhang="";
	ResultSet rskenh;
	ResultSet rsKho;
	String IdKenh_Nhapp_Nhan=""; //kho va kenh co the khac nhau
	String KhoId_nhapp_Nhan="";
	String vungid="";
	String khuvucid="";
	
	ResultSet rsvung;
	ResultSet rskhuvuc;
	ResultSet rsnhapp;
	String TuNgay="";
	String DenNgay="";
	//list don hang nha phan phoi ban ra
	ResultSet rslistdhnppban;
	//danh sach nha phan phoi sẽ được chọn để bán hàng trong chức năng bán hàng nhà phân phối. 
	ResultSet rsnhappban;
	
	public DonHangNPP(String id){
		db = new dbutils();	
		
		String sql= "SELECT     A.PK_SEQ, M.TEN, T.TEN AS NGUOITAO, S.TEN AS NGUOISUA,ISNULL(A.KHO_FK_NHAN,0) AS KHO_FK_NHAN ,ISNULL(A.KBH_FK_NHAN,0) AS KBH_FK_NHAN, A.NGAYNHAP,A.NGAYNHAN, A.TRANGTHAI, A.NGAYTAO, A.NGAYSUA, A.VAT, A.TONGGIATRI,"+ 
        "A.DATHANHTOAN, A.NPP_FK_MUA, A.NPP_FK_BAN, A.KHO_FK, A.KBH_FK "+
        "FROM         dbo.DONHANG_NPP AS A INNER JOIN "+
                              "dbo.NHAPHANPHOI AS M ON A.NPP_FK_MUA = M.PK_SEQ INNER JOIN "+
                              "dbo.NHANVIEN AS T ON T.PK_SEQ = A.NGUOITAO INNER JOIN " +
                              "dbo.NHANVIEN AS S ON S.PK_SEQ = A.NGUOISUA "+
        "WHERE (A.PK_SEQ="+id+")";
		System.out.println("cau lenh sql "+ sql);
	 //ResultSet rs=	
		
	
		ResultSet rs= db.get(sql);
		try
		{
		if(rs!=null){
		while (rs.next())
		{
			//DonHangNPP dh= new DonHangNPP();
			this.set_NppId_Mua(rs.getString("NPP_FK_MUA")); 
			this.setDaThanhToan(rs.getFloat("DATHANHTOAN"));
			this.setId(rs.getString("PK_SEQ"));
			this.setKenhBanHang(rs.getString("KBH_FK"));
			this.setKho(rs.getString("KHO_FK"));
			this.setIdKho_Nhan(rs.getString("KHO_FK_NHAN"));
			this.setIdKenh_Nhan(rs.getString("KBH_FK_NHAN"));
			this.setNgaysua(rs.getString("NGAYSUA"));
			this.setNgaytao(rs.getString("NGAYTAO"));
			this.setNguoitao(rs.getString("NGUOITAO"));
			this.setNguoisua(rs.getString("NGUOISUA"));
			this.setNppId_Ban(rs.getString("NPP_FK_BAN"));
			this.setTongGiaTri(rs.getFloat("TONGGIATRI"));
			this.setTrangthai(rs.getString("TRANGTHAI"));
			this.setVAT(rs.getString("VAT"));
			this.setTenNPPMua(rs.getString("TEN"));
			this.setNgayGiaoDich(rs.getString("NGAYNHAP"));
			//this.setChietkhau(rs.getString(""));
			this.ngaynhanhang=rs.getString("ngaynhan");
			System.out.println("1 :"+this.ngaynhanhang);
			if(this.ngaynhanhang==null ||this.ngaynhanhang.equals("")){
				
				this.ngaynhanhang=this.getngaynhanhang(this.Id_NhaPPMua);
				System.out.println("2 :"+this.ngaynhanhang);
			}
			this.setListSanPham();
			this.sanphamthieulist=new Hashtable<String, Integer>();
		}	
		}
		}
		catch(Exception e)
		{
			System.out.println("ERROR : class DonHangNPP : line 77" +e.toString());
		}
	}
	
	public DonHangNPP(){
		Chietkhau="";
		DaThanhToan=0;
		_ID="";
		Id_NhaPPBan="";
		Id_NhaPPMua="";
		KenhBanhang="";
		Kho="";
		NgaySua="";
		Nguoisua="";
		NgayTao="";
		Nguoitao="";
		TongGiaTri=0;
		Trangthai="";
		Vat="";
		TenNhaPPMua="";
		NgayGiaoDich="";
		_TongTien=0;
		TongGiaTri=0;
		db = new dbutils();
		
	}
	
	public String getUserId() {
		
		
		return userID; 
	}

	@Override
	public void setUserId(String userId) {
		
		this.userID=userId;
	
	}

	@Override
	public String getId() {
		
		return _ID;
	}

	@Override
	public void setId(String id) {
		
		_ID=id;
	}
	/*
	 * (non-Javadoc)
	 * @see geso.dms.distributor.beans.donhangnhapp.IDonhangnpp#getTrangthai()
	 * Trangthai la tinh trng cua don hang, trong truong hop don hang chua duoc duyet thi mang gia tri 0,sau khi dc duyet thi mang gia tri 1
	 */
	@Override
	public String getTrangthai() {
		
		return this.Trangthai;
	}

	@Override
	public void setTrangthai(String trangthai) {
		
		this.Trangthai=trangthai;
	}
	@Override
	public void setNgaytao(String ngaytao) {
		
		this.NgayTao=ngaytao;
	}
	@Override
	public String getNguoitao() {
		
		return Nguoitao;
	}

	@Override
	public void setNguoitao(String nguoitao) {
		
	this.Nguoitao=nguoitao;	
	}

	@Override
	public String getNgaysua() {
		
		return NgaySua;
	}

	@Override
	public void setNgaysua(String ngaysua) {
		
		this.NgaySua=ngaysua;
	}

	@Override
	public String getNguoisua() {
		
		return Nguoisua; 
	}

	@Override
	public void setNguoisua(String nguoisua) {
		
		this.Nguoisua=nguoisua;
		
	}

	@Override
	public String getChietkhau() {
		
		return Chietkhau;
	}

	@Override
	public void setChietkhau(String chietkhau) {
		
		this.Chietkhau=chietkhau;
	}

	@Override
	public String getVAT() {
		
		if(Vat==""){
			this.Vat="10";
		}
			
		return Vat;
	}

	@Override
	public void setVAT(String vat) {
		
		this.Vat=vat;
	}

	@Override
	public String getKho() {
		
		return Kho;
	}

	@Override
	public void setKho(String kho) {
		
		Kho=kho;
	}

	@Override
	public String getNppId_Ban() {
		
		return Id_NhaPPBan;
		
	}

	@Override
	public void setNppId_Ban(String npp_ban) {
		
		Id_NhaPPBan=npp_ban;
		String sql="select a.pk_seq, a.ten, a.sitecode from nhaphanphoi a where a.pk_seq="+npp_ban ;
		dbutils db = new dbutils();
		ResultSet rs =db.get(sql);
		try{
			if (!(rs == null)){
				if(rs.next())
				{
		
				this.TenNhaPPBan=rs.getString("ten");
				//this.sitecode = rs.getString("sitecode");
				}
				rs.close();
			}else
			{
				System.out.println("ERROR : class DonHangNPP : line 359,Khong Co Du Lieu  " );			
			}
			
		}catch(Exception e)
		{ 
			System.out.println("ERROR : class DonHangNPP : line 359 - "+ e.toString() );
		}
		finally{try {
			if(rs != null)
				rs.close();
		} catch (Exception e2) {
			// TODO: handle exception
		}}
	}

	@Override
	public String getNppId_Mua() {
		
		return Id_NhaPPMua ;
	}

	

	@Override
	public void setTongGiaTri(float  tonggiatri) {
		
		TongGiaTri=tonggiatri ;
	}

	@Override
	public float getDaThanhToan() {
		
		return DaThanhToan;
	}

	@Override
	public void setDaThanhToan(float dathanhtoan) {
		
	   this.DaThanhToan=dathanhtoan;
	}

	
	@Override
	public String getKenhBanHang() {
		
		return KenhBanhang;
	}

	@Override
	public void setKenhBanHang(String kenhbanhang) {
		
		this.KenhBanhang=kenhbanhang;
	}
	
	/**
	 * Phuong thuc lay ma cua nha phan phoi, dua theo ma cua user dang nhap
	 * @return
	 */
	public void getNppInfo()
	{		
	
		//Phien ban moi
		geso.dms.distributor.util.Utility util=new geso.dms.distributor.util.Utility();
		this.Id_NhaPPBan=util.getIdNhapp(this.userID);
		this.TenNhaPPBan=util.getTenNhaPP();
	
		//this.sitecode=util.getSitecode();
	}
	

	public void set_NppId_Mua(String Id_NppMua) {
		
		this.Id_NhaPPMua=Id_NppMua;
	}

	public float getTongGiaTri() {
		
		return TongGiaTri;
	}

	@Override
	public String getNgaytao() {
		
		return NgayTao;
	}




	public String getTenNPPMua() {
		
		return TenNhaPPMua;
	}


	public void setTenNPPMua(String TenNPPMua) {
		
		TenNhaPPMua=TenNPPMua;
	}

	public String getTenNPPBan() {
		
		return TenNhaPPBan;
	}


	public void setTenNPPBan(String TenNPPBan) {
		
			this.TenNhaPPBan =TenNPPBan ;
	}
	
	public void setListSanPham() {
		
		String sql= "SELECT     D.SANPHAM_FK, D.DONHANGNPP_FK, D.SOLUONG,D.SOLUONGNHAN, D.KHO_FK, D.GIAMUA, S.TEN,S.Ma, DVT.DONVI "+
					" FROM         dbo.DONHANGNPP_SANPHAM AS D INNER JOIN "+
                    " dbo.SANPHAM AS S ON D.SANPHAM_FK = S.PK_SEQ INNER JOIN "+
                    " dbo.DONVIDOLUONG AS DVT ON S.DVDL_FK = DVT.PK_SEQ "+
                    " WHERE     (D.DONHANGNPP_FK = "+this.getId()+")";
		//System.out.println("Form : DonHangNPP line 430 " +sql);
		//dbutils db=new dbutils();
		ResultSet rs=db.get(sql);
		try
		{
			this.TongGiaTri=0;
			
		while (rs.next())
		{
			ISanPhamDhNpp sp= new SanPhamDhNpp();
			sp.setDonHangNPP(rs.getString("DONHANGNPP_FK"));
			sp.setDVT(rs.getString("DONVI"));
			double giamua=rs.getDouble("GIAMUA");
			sp.setGiaMua(giamua);
			sp.setKho(rs.getString("KHO_FK"));
			sp.setIdSanPham(rs.getString("SANPHAM_FK"));
			int soluong=rs.getInt("SOLUONG");
			int soluongnhan=rs.getInt("SOLUONGNHAN");
			sp.setSoLuongNhan(soluongnhan);
			sp.setSoLuong(soluong);
			sp.setTenSanPham(rs.getString("TEN"));//Lay ten san pham
			double thanhtien=soluongnhan* giamua;//thanh tien tung san pham, bang don gia nhan voi so luong nhan/soluong chi la so luong khi dat,soluongnhan moi la so luong thuc cua don hang
			sp.setMaSanPham(rs.getString("MA"));
			sp.setThanhTien(thanhtien);
			sanphamlist.add(sp);
			this.TongGiaTri=this.TongGiaTri+(float)thanhtien;//tong gia tri cua don hang, cong don thanh tien cua cac san pham	
		}
		}
		catch(Exception e)
		{
			
		}
		
		finally{try {
			if(rs != null)
				rs.close();
		} catch (Exception e2) {
			// TODO: handle exception
		}}
	}

	public List<ISanPhamDhNpp> getSanPhamList() {
		
		return sanphamlist;
	}

	@Override
	public String getNgayGiaoDich() {
		
		return NgayGiaoDich;
	}

	@Override
	public void setNgayGiaoDich(String ngaygiaodich) {
		
		this.NgayGiaoDich=ngaygiaodich;
	}

	@Override
	public void setTongTien(double tongtien) {
		
		_TongTien=tongtien;
	}

	@Override
	public double getTongTien() {
		
		return _TongTien;
	}

	@Override
	public void setSPThieuList(Hashtable<String, Integer> spThieuList) {
		
		this.sanphamthieulist=spThieuList;
	}

	@Override
	public Hashtable<String, Integer> getSPThieuList() {
		
		return sanphamthieulist;
	}

	@Override
	public void setListSanPhamNew(List<ISanPhamDhNpp> list) {
			this.sanphamlist=list;
		
	}

	@Override
	public boolean saveDonHangNPP() {
		
		if(CheckNgayBanHang() == false)
		{
			this.thongbao = "Ngay ban hang phai sau ngay khoa so gan nhat";
			return false;
		}
		if(this.sanphamthieulist.size() > 0)
		{
			this.thongbao="So luong ton kho khong du xuat, vui long nhap lai so luong";
			return false;
		}
		try
		{
			  db.getConnection().setAutoCommit(false);
	   
		String sql=" insert into donhang_npp(NGAYNHAP,TRANGTHAI,NGAYTAO,NGAYSUA,NGUOITAO,NGUOISUA,VAT,TONGGIATRI,DATHANHTOAN,NPP_FK_MUA,NPP_FK_BAN,KHO_FK,KBH_FK) "+ 
		 " values ('"+this.NgayGiaoDich+"',0,'"+this.NgayTao+"','"+this.NgaySua+"',"+this.Nguoitao+","+this.Nguoisua+","+this.Vat+","+this.TongGiaTri+","+this.DaThanhToan+","+this.Id_NhaPPMua+","+this.Id_NhaPPBan+","+this.Kho+"," + this.KenhBanhang+")  ";
		//System.out.print("Cua lenh insert :  "+sql);
		if(!db.update(sql)){
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			this.setthongbao("Loi :" + sql);
			return false;
		}
			
		//Save chi tiet don hang
		int count = 0;
		if(sanphamlist.size()==0){
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			//System.out.println("Error : DonHangNpp: line 291 : " + er.toString());
			this.setthongbao("Loi: Vui Long Chon San Pham Ban.");
			return false;
		}
		while(count < sanphamlist.size())
		{
			ISanPhamDhNpp sanpham = new SanPhamDhNpp();
			sanpham=sanphamlist.get(count);
			String query = "select IDENT_CURRENT('donhang_npp') as dhId";
			ResultSet rsDh = db.get(query);	
			try
			{
				rsDh.next();
			this._ID = rsDh.getString("dhId");
			rsDh.close();
			}
			catch(Exception er){
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				//System.out.println("Error : DonHangNpp: line 291 : " + er.toString());
				this.setthongbao("Loi  - Clasname :DonHangNPP - line 293 : "+ er.toString());
				return false;
			}
			finally{try {
				if(rsDh != null)
					rsDh.close();
			} catch (Exception e) {
				// TODO: handle exception
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				this.setthongbao("Loi :"+e.toString());
				return false;
			}}
			sanpham.setDonHangNPP(this._ID);
			
			sanpham.setKho(this.getKho());
			String sql_insertchitiet="insert into donhangnpp_sanpham (sanpham_fk,donhangnpp_fk,soluong,kho_fk,giamua,soluongnhan)values ("+sanpham.getIdSanPham()+","+sanpham.getDonHangNPP()+","+sanpham.getSoLuong()+","+sanpham.getKho()+","+sanpham.getGiaMua()+","+sanpham.getSoLuongNhan()+" )";
			//System.out.println("Error line 49 :Classname:  SanPhamDHNpp : Cau lenh SQL " + sql);
			if(!db.update(sql_insertchitiet)){
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				this.setthongbao("Loi :"+sql_insertchitiet);
				return false;
			}
		//Cap nhat lai kho, book tang con avaliable giam
			 String query_updatekho = "update nhapp_kho set booked = booked + '" + sanpham.getSoLuong() + "', available = available - '" + sanpham.getSoLuong() + "' where sanpham_fk='" + sanpham.getIdSanPham() +"' and npp_fk='" + this.getNppId_Ban() + "' and kho_fk='" + this.getKho() + "'";
			 System.out.println("Error update KHO : line -672 : DonHangNPP : "+query_updatekho);
			 if(!db.update(query_updatekho)){
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				this.setthongbao("Loi :"+sql_insertchitiet);
				return false;
			 }
			
		    //sanpham.SavaSanPhamDhNpp();
		count++;
		
		}
		
		db.getConnection().commit();
		db.getConnection().setAutoCommit(true);
		}
		catch(Exception er){
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			System.out.println("Errorr : DonHangNPP: line 545 "+ er.toString());
			this.setthongbao(er.toString());
			return false;
		}
		return true;

	}

	@Override
	public boolean editDonHangNPP() {
		
		//dbutils db1=new dbutils();
		ResultSet rs_sp = null;
		if(this.sanphamthieulist.size() > 0)
		{
			
			this.setthongbao("So luong ton kho khong du xuat, vui long nhap lai so luong");
			return false;
		}
		try
		{
			db.getConnection().setAutoCommit(false);//set khong cho autocommit 
		String sql=" update donhang_npp set NGAYNHAP='"+this.NgayGiaoDich+"',TRANGTHAI="+this.Trangthai+",NGAYTAO='"+this.NgayTao+
		"',NGAYSUA='"+this.NgaySua+"',NGUOISUA="+this.Nguoisua +",VAT="+this.Vat+",TONGGIATRI="+this.TongGiaTri+",DATHANHTOAN="+this.DaThanhToan+
		",NPP_FK_MUA="+this.Id_NhaPPMua+",NPP_FK_BAN="+this.Id_NhaPPBan+",KHO_FK="+this.Kho+",KBH_FK="+this.KenhBanhang+" where pk_seq= "+this._ID ;
		System.out.print("Cua lenh update :  "+sql);
		if(!db.update(sql))
		{
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			this.setthongbao("Khong The Cap Nhat Don Hang,Xay Ra Loi Trong Qua Trinh Update .Error :" + sql);
			return false;
			}
		//kiem tra hang vao co khong?
		if(sanphamlist.size()==0){
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			//System.out.println("Error : DonHangNpp: line 291 : " + er.toString());
			this.setthongbao("Loi: Vui Long Chon San Pham Ban.");
			return false;
		}
		//thuc hien cap nhat lai kho
		
		
	
		
		//Truoc khi xoa thi thuc hien cap nhat lai kho nhung mat hang xoa
		//Cap nhat lai kho  
		String sql_getchitietsp="select * from donhangnpp_sanpham where donhangnpp_fk="+this.getId();
		//System.out.println(" Errorr :Edit DonHangNPP: CAP NHAT KHO :  " + sql_getchitietsp);
		rs_sp=db.get(sql_getchitietsp);
		while (rs_sp.next())
		{
		int soluong=rs_sp.getInt("soluong");
		String idsp=rs_sp.getString("sanpham_fk");
		 String query = "update nhapp_kho set booked = booked -'" + soluong + "', available = available + '" + soluong + "' where sanpham_fk='" + idsp +"' and npp_fk='" + this.getNppId_Ban() + "' and kho_fk='" + this.getKho() + "'";
		 //System.out.println(" Errorr :Edit DonHangNPP: CAP NHAT KHO :  " + query);
		 if(!db.update(query)){
			this.setthongbao("Khong the sua don hang, do khong cap nhat duoc so luong ton kho ");
		 	geso.dms.center.util.Utility.rollback_throw_exception(db);
			return false;
		}
		}
		//Sau khi cap nhat kho,thi thuc hien xoa nhung chi tiet cu
		//xoa chi tiet don hang
		String sql_xoachitiet="delete  from donhangnpp_sanpham where donhangnpp_fk="+ this.getId();
		//System.out.print("Cua lenh update :  "+sql_xoachitiet);
		if(!db.update(sql_xoachitiet)){
			geso.dms.center.util.Utility.rollback_throw_exception(db);
		this.setthongbao("Error update command :  "+sql_xoachitiet);
			return false;
		}
		//save chi tiet don hang moi
		int count = 0;
		while(count < sanphamlist.size())
		{
			ISanPhamDhNpp sanpham = new SanPhamDhNpp();
			sanpham=sanphamlist.get(count);
			
			//sanpham.setDonHangNPP(this._ID);
			sanpham.setKho(this.getKho());
			String sql_insert_chitiet="insert into donhangnpp_sanpham (sanpham_fk,donhangnpp_fk,soluong,kho_fk,giamua,soluongnhan)values ("+sanpham.getIdSanPham()+","+this._ID+","+sanpham.getSoLuong()+","+sanpham.getKho()+","+sanpham.getGiaMua()+","+sanpham.getSoLuongNhan()+" )";
				if( !db.update(sql_insert_chitiet)){
				  geso.dms.center.util.Utility.rollback_throw_exception(db);
				this.setthongbao("Error line 674 :Classname:  sql_insert_chitiet :  SQL command " + sql_insert_chitiet);
				return false;
			}
			//Cap nhat lai kho, book tang con avaliable giam
			 String query_updatekho = "update nhapp_kho set booked = booked + '" + sanpham.getSoLuong() + "', available = available - '" + sanpham.getSoLuong() + "' where sanpham_fk='" + sanpham.getIdSanPham() +"' and npp_fk='" + this.getNppId_Ban() + "' and kho_fk='" + this.getKho() + "'";
			 if(!db.update(query_updatekho)){
				 this.setthongbao("Error update KHO : line -672 : DonHangNPP : "+query_updatekho);
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				return false;
			 }
		    //sanpham.SavaSanPhamDhNpp();
		count++;	
		}

		db.getConnection().commit();
		db.getConnection().setAutoCommit(true);
		}
		catch(Exception er){
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			try {
				if(rs_sp != null)
					rs_sp.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			er.printStackTrace();
		//	System.out.println("Errorr :Edit DonHangNPP: line 630 "+ er.getLocalizedMessage());
			this.setthongbao("BAN SUA DON HANG KHONG THANH CONG : "+ er.toString());
			return false;
		}
		finally{try {
			if(rs_sp != null)
				rs_sp.close();
		} catch (Exception e) {
			// TODO: handle exception
		}}
		return true;
	}

	@Override
	public boolean DeleteDonHangNPP() {
		
		// dbutils cn= 	new dbutils();
		ResultSet rs_sp = null;
		try
		{
	  //Cap nhat lai xoa cho ==2
	   db.getConnection().setAutoCommit(false);
		String sql=" update donhang_npp set TRANGTHAI=2,NGAYSUA='"+this.NgaySua+"',NGUOISUA="+this.Nguoisua +" where pk_seq= "+this._ID ;
		//System.out.print("Cua lenh DELETE :  "+sql);
		if(!db.update(sql)){
			System.out.println(" Errorr :Edit DonHangNPP: CAP NHAT KHO :  " + sql);
			
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return false;
		}
		
		//Cap nhat lai kho  khi xoa kho ,se giam so luong booked va tang lai avaliable
		String sql_getchitietsp="select * from donhangnpp_sanpham where donhangnpp_fk="+this._ID;
		//System.out.println(" Errorr :Edit DonHangNPP: CAP NHAT KHO :  " + sql_getchitietsp);
		rs_sp=db.get(sql_getchitietsp);
		while (rs_sp.next())
		{
		int soluong=rs_sp.getInt("soluong");
		String idsp=rs_sp.getString("sanpham_fk");
		 String query = "update nhapp_kho set booked = booked -'" + soluong + "', available = available + '" + soluong + "' where sanpham_fk='" + idsp +"' and npp_fk='" + this.getNppId_Ban() + "' and kho_fk='" + this.getKho() + "'";
		 //System.out.println(" Errorr :Delete DonHangNPP: CAP NHAT KHO :  " + query);
		 if(!db.update(query)){
			this.setthongbao("Khong the sua don hang, do khong cap nhat duoc so luong ton kho ");
		 	geso.dms.center.util.Utility.rollback_throw_exception(db);
			return false;
		}
		}
		db.getConnection().commit();
		db.getConnection().setAutoCommit(true);
		}
		catch(Exception er){
			try {
				if(rs_sp != null)
					rs_sp.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
			geso.dms.center.util.Utility.rollback_throw_exception(db);
		    System.out.println("Errorr :Edit DonHangNPP: line 578: BAN XOA DON HANG KHONG THANH CONG : "+ er.getLocalizedMessage());
			this.setthongbao( er.toString());
			return false;
		}
		finally{try {
			if(rs_sp != null)
				rs_sp.close();
		} catch (Exception e) {
			// TODO: handle exception
		}}
		return true;
	}

	@Override
	public void setthongbao(String msg) {
		
		this.thongbao=msg;
	}

	@Override
	public String getthongbao() {
		
		return thongbao;
	}

	@Override
	public ResultSet getListNhaPPMua() {
		
		//dbutils db = new dbutils();
		return rs_listdonhang;
	}

	@Override
	public boolean ChotDonHangNPP_Mua() {
		
		db=new dbutils();
		ResultSet rs_sp = null;
		try
		{
			if(this.IdKenh_Nhapp_Nhan.equals("")){
				
			      this.setthongbao("Khong The Cap Nhat Nhat,Vui Long Chon Kenh");
			      return false;
			}
			if(this.KhoId_nhapp_Nhan.equals("")){
				
			      this.setthongbao("Khong The Cap Nhat Nhat,Vui Long Chon Kho");
			      return false;
			}
			
			//set ngaynhap la ngay khoa so +1 cua nha phan phoi ban 
			
			String ngaybanhang=this.getngaybanhang(this.Id_NhaPPBan);
			
			//set ngay nhan hang la ngay khoa so + 1 cua nha phan phoi mua
			
			String ngaynhanhang=this.getngaynhanhang(this.Id_NhaPPMua);
			
			db.getConnection().setAutoCommit(false);//set khong cho autocommit 
		    String sql=" update donhang_npp set  ngaynhap='"+ngaybanhang+"',NGAYNHAN='"+ngaynhanhang+"',TRANGTHAI="+this.Trangthai+",TONGGIATRI="+this.TongGiaTri+",DATHANHTOAN="+this.DaThanhToan+
		    " ,KBH_FK_NHAN='"+this.IdKenh_Nhapp_Nhan+"',KHO_FK_NHAN='"+this.KhoId_nhapp_Nhan+"' where pk_seq= "+this._ID ;
		    System.out.print("Cua lenh update :  "+sql);
		
		if(!db.update(sql)){
			  geso.dms.center.util.Utility.rollback_throw_exception(db);
		      this.setthongbao("Khong The Cap Nhat Nhat,Vui Long Kiem Tra Lai Du Lieu.Error ;" + sql);
		      return false;
		
			}
		    //lay kho va kenh cua npp ban
		    sql="select kbh_fk ,kho_fk,NPP_FK_BAN from donhang_npp where pk_seq="+this._ID+"";
		   ResultSet rs_getkenhandkho=db.get(sql);
		   if(rs_getkenhandkho.next()){
			   
			   this.KenhBanhang=rs_getkenhandkho.getString("kbh_fk");
			   this.Kho=rs_getkenhandkho.getString("kho_fk");
			   this.Id_NhaPPBan=rs_getkenhandkho.getString("NPP_FK_BAN");//lay nhapp ban
			   
		   }else{
			      geso.dms.center.util.Utility.rollback_throw_exception(db);
			      this.setthongbao("Khong The Cap Nhat,Khong Lay Duoc Kho Va Kenh,Vui Long Kiem Tra Lai Du Lieu.Error ;" + sql);
			      return false; 
		   }
		//thuc hien cap nhat lai kho
		//Truoc khi xoa thi thuc hien cap nhat lai kho nhung mat hang xoa
		//Cap nhat lai kho  
		    
		String sql_getchitietsp="select * from donhangnpp_sanpham where donhangnpp_fk="+this.getId();
		//System.out.println(" Errorr :Edit DonHangNPP: CAP NHAT KHO :  " + sql_getchitietsp);
		rs_sp =db.get(sql_getchitietsp);
		//Cap nhat lai kho,so luong cu gio khong con gia tri, so luong nhan ben nha phan phoi ban co hieu luc khi chot don hang
		while (rs_sp.next())
		{
		int soluong=rs_sp.getInt("soluong");
		String idsp=rs_sp.getString("sanpham_fk");
		 String query = "update nhapp_kho set booked = booked -'" + soluong + "', available = available + '" + soluong + "' where sanpham_fk='" + idsp +"' and npp_fk='" + this.getNppId_Ban() + "' and kho_fk='" + this.getKho() + "' and kbh_fk='"+this.getKenhBanHang()+"'";
		 //System.out.println(" Errorr :Edit DonHangNPP: CAP NHAT KHO Nha pp mua :  " + query);
		 if(!db.update(query)){
			
			this.setthongbao("Khong the sua don hang, do khong cap nhat duoc so luong ton kho .Loi :" + query);
		 	geso.dms.center.util.Utility.rollback_throw_exception(db);
			return false;
		}
		}
		///
		
		//sua chi tiet don hang moi
		int count = 0;
		while(count < sanphamlist.size())
		{
			ISanPhamDhNpp sanpham = new SanPhamDhNpp();
			sanpham=sanphamlist.get(count);
			
			//sanpham.setDonHangNPP(this._ID);
			sanpham.setKho(this.getKho());
			String sql_insert_chitiet="update donhangnpp_sanpham set soluongnhan="+sanpham.getSoLuongNhan() +
					"  where sanpham_fk="+sanpham.getIdSanPham()+" and donhangnpp_fk= "+this._ID;
			if( !db.update(sql_insert_chitiet)){
				System.out.println("Error line 893 :Classname:  sql_insert_chitiet : Cau lenh SQL " + sql_insert_chitiet);
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				return false;
			}
			//Cap nhat lai kho cua nha phan phoi ban, book khong doi vi luc nay da cap nhat lai book phia tren khi,,  con avaliable giam,soluong giam
			 String query_updatekho = "update nhapp_kho set  available = available - '" + sanpham.getSoLuongNhan() + "',soluong=soluong-"+sanpham.getSoLuongNhan()+" where sanpham_fk='" + sanpham.getIdSanPham() +"' and npp_fk='" + this.getNppId_Ban() + "' and kho_fk='" + this.getKho() + "' and kbh_fk='"+this.getKenhBanHang()+"'  ";
			System.out.println("Error update KHO : line -672 : DonHangNPP : "+query_updatekho);
			 
			 if(!db.update(query_updatekho)){
				 this.setthongbao("Khong the sua don hang, do khong cap nhat duoc so luong ton kho .Loi :" + query_updatekho);

				geso.dms.center.util.Utility.rollback_throw_exception(db);
				return false;
			 }

			//Cap nhat lai kho cua nha phan phoi mua, book khong doi,so luong tang,avaliable tang
			 String query_updatekho_npp_mua="update nhapp_kho set soluong=soluong+"+sanpham.getSoLuongNhan()+" ,available=available+ " +sanpham.getSoLuongNhan() +" where sanpham_fk='" + sanpham.getIdSanPham() +"' and npp_fk='" + this.getNppId_Mua() + "' and kho_fk='" + this.getIdKho_Nhan() + "' and kbh_fk='"+this.getIdKenh_Nhan()+"'";
			 System.out.println("Error update KHO : line -858 : DonHangNPP : "+query_updatekho_npp_mua);
			 if(!db.update(query_updatekho_npp_mua))
			{
				 this.setthongbao("Khong the sua don hang, do khong cap nhat duoc so luong ton kho .Loi :" + query_updatekho_npp_mua);
				 geso.dms.center.util.Utility.rollback_throw_exception(db);	
				 return false;
			}
			count++;	
		}

		///
		
		db.getConnection().commit();
		db.getConnection().setAutoCommit(true);
		}
		catch(Exception er){
			
			try {
				if(rs_sp != null)
					rs_sp.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			this.setthongbao("BAN SUA DON HANG KHONG THANH CONG : "+ er.toString());
			return false;
		}
		finally{try {
			if(rs_sp != null)
				rs_sp.close();
		} catch (Exception e) {
			// TODO: handle exception
		}}
		return true;
	}


	private String getngaynhanhang(String id_NhaPPMua2) {

		String ngayksthem1="";
		try{
	
			String query="select Replace(convert(char(10), DATEADD(day, 1, cast(max(ngayks) as datetime)) , 102) , '.', '-' ) as ngay from khoasongay where npp_fk="+id_NhaPPMua2;
			
			 System.out.println(query);
			
			 ResultSet rs1=db.get(query);
			if(rs1!=null){
				if(rs1.next()){
					ngayksthem1=rs1.getString("ngay");
					//System.out.println("Ngay Khoa So Them " +ngayksthem1);
			 	}else{
			 		geso.dms.center.util.Utility.rollback_throw_exception(db);		
					this.thongbao= "I. Khong The Nhan Hang,Xay Ra Loi Cap Nhat Sau :"+ query;
					return "";
			 	}
			}else{
				geso.dms.center.util.Utility.rollback_throw_exception(db);		
				this.thongbao=  "II.Khong The Nhan Hang,Xay Ra Loi Cap Nhat Sau :"+ query;
				return "";
			}
			rs1.close();
		
		}catch(Exception er){
			this.thongbao="KHong Lay Duoc Ngay Khoa So Cong 1 Cua Nha PP Mua .Loi :"+er.toString();
			return "";
		}
		return ngayksthem1;
	}

	private String getngaybanhang(String id_NhaPPBan2) {

		String ngayksthem1="";
		try{
	
			String query="select Replace(convert(char(10), DATEADD(day, 1, cast(max(ngayks) as datetime)) , 102) , '.', '-' ) as ngay from khoasongay where npp_fk="+id_NhaPPBan2;
			
			 System.out.println(query);
			
			 ResultSet rs1=db.get(query);
			if(rs1!=null){
				if(rs1.next()){
					ngayksthem1=rs1.getString("ngay");
					//System.out.println("Ngay Khoa So Them " +ngayksthem1);
			 	}else{
			 		geso.dms.center.util.Utility.rollback_throw_exception(db);		
					this.thongbao= "I. Khong The Nhan Hang,Xay Ra Loi Cap Nhat Sau :"+ query;
					return "";
			 	}
			}else{
				geso.dms.center.util.Utility.rollback_throw_exception(db);		
				this.thongbao=  "II.Khong The Nhan Hang,Xay Ra Loi Cap Nhat Sau :"+ query;
				return "";
			}
			rs1.close();
		
		}catch(Exception er){
			this.thongbao="KHong Lay Duoc Ngay Khoa So Cong 1 Cua Nha PP Ban .Loi :"+er.toString();
			return "";
		}
		return ngayksthem1;
	}

	@Override
	public void DBclose() {
	
		try{
				if(sanphamthieulist!=null){
					sanphamthieulist.clear();
				}
				if(sanphamlist!=null){
					sanphamlist.clear();
				}
				if(rs_listdonhang!=null){
					rs_listdonhang.close();
				}
				if(rskenh!=null){
					rskenh.close();
				}
				if(rsKho!=null){
					rsKho.close();
				}
				if(rsvung!=null){
					rsvung.close();
				}
				if(rskhuvuc!=null){
					rskhuvuc.close();
				}
				if(rsnhapp!=null){
					rsnhapp.close();
				}
				if(rslistdhnppban!=null){
					rslistdhnppban.close();
				}
				if(rsnhappban!=null){
					rsnhappban.close();
				}
					if(this.db != null)
						db.shutDown();
		}catch(Exception er){
			
		}
		
		
	}
	@Override
	public void setListNhaPPMua(String sql) {
		String query="";
		if(sql.equals("")){
		//thuc te day la phuong thuc lay idnpp cua npp nay nen lay nppban,hay nppmua deu dung
		 query=" SELECT     A.PK_SEQ, M.TEN, T.TEN AS NGUOITAO, S.TEN AS NGUOISUA, A.NGAYNHAP, A.TRANGTHAI, A.NGAYTAO, A.NGAYSUA, "+
		" A.VAT, A.TONGGIATRI,  A.DATHANHTOAN, A.NPP_FK_MUA, A.NPP_FK_BAN, A.KHO_FK, A.KBH_FK "+
        " FROM         dbo.DONHANG_NPP AS A INNER JOIN  dbo.NHAPHANPHOI AS M ON A.NPP_FK_BAN = M.PK_SEQ INNER JOIN "+
                            "  dbo.NHANVIEN AS T ON T.PK_SEQ = A.NGUOITAO INNER JOIN "+
                             "  dbo.NHANVIEN AS S ON S.PK_SEQ = A.NGUOISUA "+
       " WHERE   NppDaChot=1 and   (A.NPP_FK_MUA = " + this.getNppId_Mua()+")";
		 
		} else{
			 query=sql;
		 }
		System.out.println("Dang sach don hang mua : "+query);
	this.rs_listdonhang=	db.get(query);

	
		
	}

	@Override
	public void createRs() {
		
		String sql_=" select pk_Seq,ten,diengiai from kho inner join ( select distinct kho_fk from nhapp_kho where npp_fk="+this.Id_NhaPPBan+") as a on a.kho_fk=kho.pk_seq";
		//System.out.println("Get Kho :"+sql_);
		this.rsKho=this.db.get(sql_);
		String sql="select distinct kbh_fk,ten from kenhbanhang  inner join nhapp_kho on kbh_fk=pk_seq where npp_fk="+this.Id_NhaPPBan;
		this.rskenh=this.db.get(sql);
		
		this.rsvung=this.db.get("select pk_seq,ten from vung");
		
		sql="select pk_seq,ten from khuvuc ";
		if(this.vungid.length()>0){
			sql=sql+" where vung_fk="+ this.vungid;
		}
		this.rskhuvuc=this.db.get(sql);
		CreateRs_NhappBan();
	}

	@Override
	public ResultSet getrskho() {
		
		return this.rsKho;
	}

	
	public String getIdKho_Nhan() {
		
		return this.KhoId_nhapp_Nhan;
	}

	
	public String getIdKenh_Nhan() {
		
		return this.IdKenh_Nhapp_Nhan;
	}

	
	public void setIdKho_Nhan(String khoidnhan) {
		
		this.KhoId_nhapp_Nhan=khoidnhan;
	}

	
	public void setIdKenh_Nhan(String kenhidnhan) {
		
		this.IdKenh_Nhapp_Nhan=kenhidnhan;
	}

	private boolean CheckNgayBanHang()
	{
		Utility util = new Utility();
		String ngayksgn = util.ngaykhoaso(this.Id_NhaPPBan);
		
		//System.out.print("\nNgay khoa so gan nhat la: " + this.ngaychungtu + "\n");
		
		if(ngayksgn.equals(""))
			return false;
		
		String[] ngayks = ngayksgn.split("-");
		String[] ngayct = this.NgayGiaoDich.split("-");
		
		//System.out.print("\nNgay chung tu la: " + this.ngaychungtu + "\n");
		
		Calendar c1 = Calendar.getInstance(); //new GregorianCalendar();
		Calendar c2 = Calendar.getInstance(); //new GregorianCalendar();

		//NGAY KHOA SO
		
		c1.set(Integer.parseInt(ngayks[0]), Integer.parseInt(ngayks[1]) - 1, Integer.parseInt(ngayks[2]));
	
		//NGAY thuc hien tra hang
		c2.set(Integer.parseInt(ngayct[0]), Integer.parseInt(ngayct[1]) - 1, Integer.parseInt(ngayct[2]));
	
		c1.add(Calendar.DATE, 1);//ngay tra don hang bang ngay khoa so them 1 ngay
		//phep tinh ngay nhan hang - ngay khoa so
			
		long songay = ( c1.getTime().getTime() - c2.getTime().getTime()) / (24 * 3600 * 1000);
		   
		if(songay < 0) //ngay chung tu khong duoc nho hon hoac bang ngay khoa so gan nhat 
		{
			//this.msg = "Ngay Tra Don Hang Phai Lon Hon Ngay Khoa So Gan Nhat.";
			return false;
		}
		return true;
	}

	public void createRs_BenNhanHang() 
	{
			String sql=" select pk_Seq,ten,diengiai from kho inner join ( select distinct kho_fk from nhapp_kho where npp_fk="+this.Id_NhaPPMua+") as a on a.kho_fk=kho.pk_seq";
			
			this.rsKho=this.db.get(sql);
		
		
			 sql="select distinct kbh_fk,ten from kenhbanhang  inner join nhapp_kho on kbh_fk=pk_seq where npp_fk="+this.Id_NhaPPMua;
			 this.rskenh=this.db.get(sql);
		
			 sql="select pk_seq,ten from nhaphanphoi where trangthai=1 and priandsecond=0 and pk_seq <>"+ this.Id_NhaPPMua;
			System.out.println("Get Du Lieu :"+sql);
			 this.rsnhapp=this.db.get(sql);
		 
	}
	
	public String displayfile(String id,String userId,int loai)
	{
		String tenfile="";
		String idnpp="";
		String sql="select npp.pk_seq from nhanvien nv , nhaphanphoi npp where nv.CONVSITECODE=npp.SITECODE and nv.PK_SEQ="+userId;
		ResultSet r=db.get(sql);
		try {
			while (r.next()){
				idnpp=r.getString("pk_seq");
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		sql="select name from xemfile where pk_seq="+id+" and npp_fk="+idnpp+" and loai="+loai;
		ResultSet rs=this.db.get(sql);
		try {
			while (rs.next())
			{
				tenfile=rs.getString("name");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tenfile;
	}
	public int luufile(String id,String name,String userId,int loai)
	{
		String idnpp="";
		String sql="select npp.pk_seq from nhanvien nv , nhaphanphoi npp where nv.CONVSITECODE=npp.SITECODE and nv.PK_SEQ="+userId;
		ResultSet r=db.get(sql);
		try {
			while (r.next()){
				idnpp=r.getString("pk_seq");
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		int flag=0;
		try {
			
			sql="select * from XEMFILE where PK_SEQ <>"+id+" and name='"+name+"' and loai <> "+loai+" and npp_fk <> "+idnpp;
			System.out.println(sql);
			ResultSet rs=this.db.get("select * from XEMFILE where PK_SEQ <>"+id+" and name='"+name+"' and loai <> "+loai+" and npp_fk <> "+idnpp );

			if(rs.next()){
				flag=flag+1;
				this.thongbao="vui long doi ten file";
			}
			rs.close();
			if(flag>0)
				return 1;
			this.db.getConnection().setAutoCommit(false);
			sql="delete from XEMFILE where PK_SEQ="+id+" and loai="+loai+" and npp_fk="+idnpp ; 
			System.out.println(sql);
			if(!this.db.update(sql))
			{
				 geso.dms.center.util.Utility.rollback_throw_exception(db);
				 this.thongbao="loi trong qua trinh upload";
				 return 2;
			}
			sql="insert into XEMFILE(PK_SEQ,NPP_FK,NAME,LOAI,DIENGIAI) values ("+id+","+idnpp+",'"+name+"',"+loai+",'tra Hang ve nha cung cap')";
			if(!this.db.update(sql))
			{
				 geso.dms.center.util.Utility.rollback_throw_exception(db);
				 this.thongbao="loi trong qua trinh upload";
				 return 2;
			}
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
			return 3;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}
	
	public ResultSet getrskenh() 
	{
		return this.rskenh;
	}

	@Override
	public String getNgayNhanhang() {

		return this.ngaynhanhang;
	}

	@Override
	public ResultSet GetRsNpp() {

		return this.rsnhapp;
	}

	@Override
	public String GetTungay() {

		return this.TuNgay;
	}

	@Override
	public String GetDenNgay() {

		return this.DenNgay;
	}

	@Override
	public void setTuNgay(String tungay) {

		this.TuNgay=tungay;
	}

	@Override
	public void setDenNgay(String denngay) {

		this.DenNgay=denngay;
	}

	@Override
	public void SetDonHangNPP(String sql) {
		String query="";
		if(sql.equals("")){
			query= "SELECT    a.NppDaChot, A.PK_SEQ, M.TEN, T.TEN AS NGUOITAO, S.TEN AS NGUOISUA, A.NGAYNHAP, A.TRANGTHAI, A.NGAYTAO, A.NGAYSUA, A.VAT, A.TONGGIATRI,"+ 
        "A.DATHANHTOAN, A.NPP_FK_MUA, A.NPP_FK_BAN, A.KHO_FK, A.KBH_FK "+
        "FROM         dbo.DONHANG_NPP AS A INNER JOIN "+
                              "dbo.NHAPHANPHOI AS M ON A.NPP_FK_MUA = M.PK_SEQ INNER JOIN "+
                              "dbo.NHANVIEN AS T ON T.PK_SEQ = A.NGUOITAO INNER JOIN " +
                              "dbo.NHANVIEN AS S ON S.PK_SEQ = A.NGUOISUA "+
        "WHERE     (A.NPP_FK_BAN = "+ this.Id_NhaPPBan +")";
		}else{
			query=sql;
		}
	
		this.rslistdhnppban=this.db.get(query);
	}

	@Override
	public ResultSet GetDonHangNPP() {

		return this.rslistdhnppban;
	}

	@Override
	public void CreateRs_NhappBan() {

		String sql="select pk_seq,ten from nhaphanphoi where trangthai=1 and priandsecond=0 and pk_seq <>" +this.Id_NhaPPBan;
		
			this.rsnhappban=db.get(sql);
	}

	@Override
	public ResultSet GetRs_NhappBan() {

		return this.rsnhappban;
	}

	@Override
	public String ChotDh()
	{
		geso.dms.center.db.sql.dbutils db = new geso.dms.center.db.sql.dbutils();
		String msg="";
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String query =" update DONHANG_NPP set NppDaChot=1 where pk_seq='"+this._ID+"' and TrangThai=0 ";
			if(db.updateReturnInt(query)!=1)
			{
				msg= "Đơn hàng đã chốt vui lòng kiểm tra lại ! ";
				db.getConnection().rollback();
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return msg;
	}

}
