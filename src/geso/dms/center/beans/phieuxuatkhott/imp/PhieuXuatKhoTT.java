package geso.dms.center.beans.phieuxuatkhott.imp;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.cete.dynamicpdf.imaging.tiff.d;

import geso.dms.center.beans.phieuxuatkhott.IPhieuXuatKhoTT;
import geso.dms.center.beans.phieuxuatkhott.IPhieuXuatKhoTT_SP;
import geso.dms.distributor.db.sql.dbutils;

public class PhieuXuatKhoTT implements IPhieuXuatKhoTT{

	String Id;
	String DonDatHangId="";
	String NgayTao="";
	String NguoiTao="";
	String NguoiSua="";
	String NgaySua="";
	String NgayLapPhieu="";
	String TrangThai;
	String Message="";
	String GhiChu="";
	String LyDoXuat="";
	String SoID="";
	String KhoTTId="";
	String HoTenNguoiNhan="";
	String DiaChiNguoiNhan="";
	String TenKho;
	
	List<IPhieuXuatKhoTT> listphieuxuatkho=new ArrayList<IPhieuXuatKhoTT>();
	List<IPhieuXuatKhoTT_SP> listSanPhamXuatKho=new ArrayList<IPhieuXuatKhoTT_SP>();
	public PhieuXuatKhoTT(String id){
		listphieuxuatkho.clear();
		String sql_getdata="";
		
			sql_getdata="select a.pk_Seq,a.ngayxuat,a.ngaytao,a.nguoitao,a.nguoisua,a.ngaysua,a.trangthai,ddh_fk,lydoxuat,ghichu,khott_fk from phieuxuatkhott  a where pk_seq= "+id;
		
		dbutils db=new dbutils();
		ResultSet rs=db.get(sql_getdata);
		if(rs!=null){
			try{
				while(rs.next()){
					
					this.setId(rs.getString("pk_seq"));
					this.setDonDatHangId(rs.getString("ddh_fk"));
					this.setNgaylap(rs.getString("ngayxuat"));
					this.setMessage("");
					this.setNgaysua(rs.getString("ngaysua"));
					this.setNgaytao(rs.getString("ngaytao"));
					this.setNguoisua(rs.getString("nguoisua"));
					this.setNguoisua(rs.getString("nguoisua"));
					this.setTrangthai(rs.getString("trangthai"));
					this.setLyDoXuat(rs.getString("lydoxuat"));
					this.setGhiChu(rs.getString("ghichu"));
					this.setKhoTTId(rs.getString("khott_fk"));
					this.setListSanPham();
					String sql_getdiachi="select b.diachi,b.ten from dondathang d inner join nhaphanphoi b on d.npp_fk= b.pk_Seq where d.pk_seq="+this.getDonDatHangID() ;
					ResultSet rs_getinfo=db.get(sql_getdiachi);
					if(rs_getinfo!=null){
						if(rs_getinfo.next())
						{
							this.setDiaChiNguoinhan(rs_getinfo.getString("diachi"));
							this.setHoTenNguoiNhan(rs_getinfo.getString("ten"));
						}
					}
				}
			}catch(Exception er){
				System.out.println("Loi Xay ra :" +er.toString());
			}
		}
	}
	public  PhieuXuatKhoTT(){
		this.TrangThai="";
		this.DonDatHangId="";
		this.NgayLapPhieu="";
		this.Message="";
		this.GhiChu="";
		this.LyDoXuat="";
	}
	
	@Override
	public String getID() {
		// TODO Auto-generated method stub
		return this.Id;
	}

	@Override
	public void setId(String id) {
		// TODO Auto-generated method stub
		this.Id=id;
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
	public String getNgaylap() {
		// TODO Auto-generated method stub
		return this.NgayLapPhieu;
	}

	@Override
	public void setNgaylap(String ngaylap) {
		// TODO Auto-generated method stub
		this.NgayLapPhieu=ngaylap;
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
	public void setDonDatHangId(String ddhid) {
		// TODO Auto-generated method stub
		
		this.DonDatHangId=ddhid;
	}

	@Override
	public String getDonDatHangID() {
		// TODO Auto-generated method stub
		return this.DonDatHangId;
	}

	@Override
	public void init(String sql) {
		// TODO Auto-generated method stub
		listphieuxuatkho.clear();
		String sql_getdata="";
		if(sql.equals("")){
				sql_getdata="select a.pk_Seq,a.ngayxuat,a.ngaytao,nt.ten as nguoitao,ns.ten as nguoisua,a.ngaysua,a.trangthai,ddh_fk from phieuxuatkhott  a inner join nhanvien as nt on nt.pk_seq=a.nguoitao inner join nhanvien as ns on ns.pk_seq=a.nguoisua  ";
		}else{
			sql_getdata=sql;
		}
		dbutils db=new dbutils();
		ResultSet rs=db.get(sql_getdata);
		if(rs!=null){
			try{
				while(rs.next()){
					IPhieuXuatKhoTT phieuxuat=new PhieuXuatKhoTT();
					phieuxuat.setId(rs.getString("pk_seq"));
					phieuxuat.setDonDatHangId(rs.getString("ddh_fk"));
					phieuxuat.setNgaylap(rs.getString("ngayxuat"));
					phieuxuat.setMessage("");
					phieuxuat.setNgaysua(rs.getString("ngaysua"));
					phieuxuat.setNgaytao(rs.getString("ngaytao"));
					phieuxuat.setNguoitao(rs.getString("nguoitao"));
					phieuxuat.setNguoisua(rs.getString("nguoisua"));
					phieuxuat.setTrangthai(rs.getString("trangthai"));
					
					listphieuxuatkho.add(phieuxuat);
				}
			}catch(Exception er){
				
			}
		}
	}

	@Override
	public List<IPhieuXuatKhoTT> getListPhieuXuatKho() {
		// TODO Auto-generated method stub
		return this.listphieuxuatkho;
	}

	@Override
	public void setGhiChu(String ghichu) {
		// TODO Auto-generated method stub
		this.GhiChu=ghichu;
	}

	@Override
	public String getGhiChu() {
		// TODO Auto-generated method stub
		return this.GhiChu;
	}

	@Override
	public void setLyDoXuat(String lydoxuat) {
		// TODO Auto-generated method stub
		this.LyDoXuat=lydoxuat;
	}

	@Override
	public String getLyDoXuat() {
		// TODO Auto-generated method stub
		return this.LyDoXuat;
	}

	@Override
	public boolean SavePhieuXuatKho() {
		// TODO Auto-generated method stub
		  dbutils cn= 	new dbutils();
			
			try
			{
				if(this.DonDatHangId.equals(""))
				{
					this.Message="Don Dat Hang Khong Duoc Mang Gia Tri Rong, Vui Long Chon Don Dat Hang";
					return false;
					
				}
				if(this.NguoiTao==null || this.NguoiTao.equals("")){
					this.Message="Ten Nguoi Dung Bi Mat Phien Giao Dich,Vui Long Dang Nhap Lai De Su Dung Chuc Nang Nay";
					return false;
				}
					cn.getConnection().setAutoCommit(false);
				  
			String sql_check="select pk_seq from phieuxuatkhott where ddh_fk =" +this.DonDatHangId + " and trangthai!='2'";
			ResultSet rs_check=cn.get(sql_check);
			if(rs_check.next()){
				this.Message="Don Dat Hang Nay Da Duoc Xu Ly, Vui Long Chon Don Dat Hang Khac";
				return false;
			}
		   //Khi moi insert vao thi cho gia tri =1,bo qua buoc chot nha phan phoi
			String sql=" insert into phieuxuatkhott(ngayxuat,ngaytao,NGUOITAO,ngaysua,nguoisua,trangthai,ddh_fk,lydoxuat,ghichu,soid,khott_fk) "+
			 " values ('"+this.NgayLapPhieu+"','"+this.NgayTao+"',"+this.NguoiTao+",'"+this.NgaySua+"',"+this.NguoiSua+",'0',"+this.DonDatHangId+",N'"+this.LyDoXuat+"',N'" + this.getGhiChu()+"','"+this.SoID+"',"+this.KhoTTId+")";
			
			if(!cn.update(sql)){
				cn.update("rollback");
				this.Message="Khong The Cap Nhat Phieu Xuat Kho ,Loi Tren Dong Lenh Sau :" + sql;
				return false;
			}
			String query = "select IDENT_CURRENT('phieuxuatkhott') as dhId";
			ResultSet rsDh = cn.get(query);	
			
				if(rsDh.next()){
			    this.Id = rsDh.getString("dhId");
				}
			rsDh.close();
			
			
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
	public boolean EditPhieuXuatKho() {
		// TODO Auto-generated method stub
		 dbutils cn= 	new dbutils();
			try
			{
				
				if(this.NguoiSua==null || this.NguoiSua.equals("")){
					this.Message="Ten Nguoi Dung Bi Mat Phien Giao Dich,Vui Long Dang Nhap Lai De Su Dung Chuc Nang Nay";
					return false;
				}
					cn.getConnection().setAutoCommit(false);
				  
		   
			String sql=" update  phieuxuatkhott set ngayxuat ='"+this.NgayLapPhieu+"',ngaysua='"+this.NgaySua+"',nguoisua="+this.NguoiSua+",lydoxuat=N'"+this.LyDoXuat+"',ghichu=N'"+this.LyDoXuat+"',khott_fk="+this.KhoTTId+" where pk_seq=" + this.Id;
			
			if(!cn.update(sql)){
				cn.update("rollback");
				this.Message="Khong The Cap Nhat Phieu Xuat Kho ,Loi Tren Dong Lenh Sau :" + sql;
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
	public void setSoId(String soid) {
		// TODO Auto-generated method stub
		this.SoID=soid;
	}

	@Override
	public String getSoId() {
		// TODO Auto-generated method stub
		return this.SoID;
	}
	@Override
	public boolean ChotPhieuXuatKho() {
		// TODO Auto-generated method stub
		  dbutils cn= 	new dbutils();
			
			try
			{
				
				if( this.Id==null || this.Id.equals("")){
			   	this.Message="So Don Hang Sua khong Ton Tai";
				return false;
			   }
				if(this.NguoiTao==null || this.NguoiTao.equals("")){
					this.Message="Ten Nguoi Dung Bi Mat Phien Giao Dich,Vui Long Dang Nhap Lai De Su Dung Chuc Nang Nay";
					return false;
				}
					cn.getConnection().setAutoCommit(false);
				  //Trang thai cua don hang la chot 
					String sql="update phieuxuatkhott set trangthai='1' where  pk_Seq="+ this.Id;
				  
				  //System.out.println(sql);
				  if(!cn.update(sql)){
						cn.update("rollback");
						this.Message="Khong The Chot Phieu Xuat Kho Nay ,Loi Tren Dong Lenh Sau :" + sql;
						return false;
					}
			//sau khi chot hoa don thi thuc hien update lai gia tri cua xuat kho
				  String sql_updateddh="update dondathang set trangthai='4' where pk_seq="+ this.DonDatHangId;
			    if(!cn.update(sql_updateddh)){
				cn.update("rollback");
				this.Message="Khong The Cap Nhat Phieu Xuat Kho ,Loi Tren Dong Lenh Sau :" + sql_updateddh;
				return false;
		        }
			    //Dong Thoi Thuc Hien Up Dat Lai So Luong Trong Kho 
			    String sql_getdatail="select soluong,sanpham_fk,ma from dondathang_sp a inner join sanpham s on a.sanpham_fk=s.pk_seq where dondathang_fk=" + this.DonDatHangId;
			   System.out.println(" TEst: "+sql_getdatail);
			    ResultSet rsgetdetail= cn.get(sql_getdatail);
			    if(rsgetdetail!=null){
			    	while(rsgetdetail.next()){
			    		String soluong=rsgetdetail.getString("soluong");
			    		String masp=rsgetdetail.getString("ma");
			    		String sql_update="update tonkhoicp set stock=stock-'" +soluong +"' ,booked=booked-'"+ soluong + "' where kho='"+this.KhoTTId+"' and masp='"+masp+"'";
			    		System.out.println(" tru san pham"+ sql_update);
			    		
			    		if(!cn.update(sql_update)){
			    			cn.update("rollback");
							this.Message="Khong The Chot Phieu Xuat Kho ,Loi Tren Dong Lenh Sau :" + sql_update;
							return false;
			    		}
			    	}
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
	public String getKhoTTId() {
		// TODO Auto-generated method stub
		return this.KhoTTId;
	}
	@Override
	public void setKhoTTId(String khottid) {
		// TODO Auto-generated method stub
		this.KhoTTId=khottid;
		String sql="select ten from khott where pk_seq="+ khottid;
		dbutils db=new dbutils();
		ResultSet rs_getdata=db.get(sql);
		
		try{
		if(rs_getdata.next()){
			this.TenKho=rs_getdata.getString("ten");
		}
		}catch(Exception er){
			
		}
	}
	@Override
	public List<IPhieuXuatKhoTT_SP> getListSanPham() {
		// TODO Auto-generated method stub
		return this.listSanPhamXuatKho;
	}
	@Override
	public void setListSanPham() {
		// TODO Auto-generated method stub
		this.listSanPhamXuatKho.clear();
		String sql_getdetail="select s.ten as tensp,dondathang_fk,a.sanpham_fk,soluong,s.dvdl_fk,dvdl1_fk,soluong,dvdl2_fk,soluong1,soluong2,s.ma,d1.donvi as donvi1,d2.donvi as donvi2 from dondathang_sp a inner join sanpham s "+ 
		" on a.sanpham_fk=s.pk_Seq inner join quycach q on q.sanpham_fk=a.sanpham_fk inner join donvidoluong as d2 on d2.pk_Seq=dvdl2_fk inner join donvidoluong d1 on d1.pk_seq=dvdl1_fk "+
		" where dondathang_fk="+ this.DonDatHangId ;
		//System.out.println("PhieuXuatKhoTT 175 line :"+ sql_getdetail);
		dbutils db=new dbutils();
	   ResultSet rs_detail=db.get(sql_getdetail);
 	     if(rs_detail!=null){
 	    	 try{
 	    	 while(rs_detail.next()){
 	    		 IPhieuXuatKhoTT_SP sanpham=new PhieuXuatkhoTT_SP();
 	    		 sanpham.setDonViTinh(rs_detail.getString("donvi1"));
 	    		 sanpham.setDVDL2(rs_detail.getString("donvi2"));
 	    		 sanpham.setId(rs_detail.getString("dondathang_fk"));
 				sanpham.setIDSanPham(rs_detail.getString("sanpham_fk"));
 				sanpham.setTenSanPham(rs_detail.getString("tensp"));
 				sanpham.setMaSanPham(rs_detail.getString("ma"));
 				sanpham.setQuyCach(rs_detail.getInt("soluong1"));
 				sanpham.setSoLuong(rs_detail.getInt("soluong"));
 				int soluongquydoi=sanpham.getSoLuong()/sanpham.getQuyCach();
 				int le=sanpham.getSoLuong() % sanpham.getQuyCach();
 				sanpham.setSoLuongQuyDoi(soluongquydoi);
 				sanpham.setLe(le);
 				this.listSanPhamXuatKho.add(sanpham);
 	    	}
 	    	 }catch(Exception er){
 	    		 System.out.println(er.toString());
 	    	 }
 	     }
	}
	@Override
	public void setHoTenNguoiNhan(String hotennguoinhan) {
		// TODO Auto-generated method stub
		this.HoTenNguoiNhan=hotennguoinhan;
	}
	@Override
	public String getHoTenNguoiNhan() {
		// TODO Auto-generated method stub
		return this.HoTenNguoiNhan;
	}
	@Override
	public void setDiaChiNguoinhan(String diachi) {
		// TODO Auto-generated method stub
		this.DiaChiNguoiNhan=diachi;
	}
	@Override
	public String getDiaChiNguoiNhan() {
		// TODO Auto-generated method stub
		return this.DiaChiNguoiNhan;
	}
	@Override
	public String getTenKho() {
		// TODO Auto-generated method stub
		return this.TenKho;
	}
	@Override
	public void setTenKho(String tenkho) {
		// TODO Auto-generated method stub
		this.TenKho=tenkho;
	}
	
}
