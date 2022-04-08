package geso.dms.center.beans.phieuthanhtoantt.imp;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import geso.dms.center.beans.phieuthanhtoantt.IPhieuThanhToanTT;
import geso.dms.center.beans.phieuthanhtoantt.IPhieuThanhToanTT_DH;
import geso.dms.distributor.db.sql.dbutils;

public class PhieuThanhToanTT implements IPhieuThanhToanTT {

	String UserId;
	String IdNhaPhanPhoi;
	String Id;
	String Message;
	String NguoiTao;
	String NguoiSua;
	String NgayTao;
	String NgaySua;
	double SoTien;
	String NgayThuTien;
	String HinhThuc;
	String TuNgay;
	String DenNgay;
	String DienGiai;
	String trangthai;
	String KhuVucId;
	double NoCu;

	List<IPhieuThanhToanTT> ListPhieu=new ArrayList<IPhieuThanhToanTT>();
	List<IPhieuThanhToanTT_DH> listDonDatHang=new ArrayList<IPhieuThanhToanTT_DH>();
	
	public PhieuThanhToanTT(String id){
		String sql_getdata="";
		
			sql_getdata="select  a.nhapp_fk,a.pk_seq,a.ngaythanhtoan,a.hinhthuctt,a.diengiai,a.trangthai,a.sotien, a.ngaytao,a.ngaysua,nt.ten as nguoitao,ns.ten as nguoisua from phieuthanhtoantt a "+
			" inner join nhanvien as nt on nt.pk_seq=a.nguoitao inner join nhanvien as ns on ns.pk_seq=a.nguoisua where a.trangthai!='2' and a.pk_seq="+id ;
			//System.out.println("Cua Lenh :"+sql_getdata);
		dbutils db=new dbutils();
		ResultSet rs=db.get(sql_getdata);
		if(rs!=null){
			try{
				if(rs.next()){
				this.setDiengiai(rs.getString("diengiai"));
				this.setHinhthuc(rs.getString("hinhthuctt"));
				this.setId(rs.getString("pk_Seq"));
				this.setTrangThai(rs.getString("trangthai"));
				this.setNgaySua(rs.getString("ngaysua"));
				this.setNgayTao(rs.getString("ngaytao"));
				this.setNguoiTao(rs.getString("nguoitao"));
				this.setNguoiSua(rs.getString("nguoisua"));
				this.setNgaythanhtoan(rs.getString("ngaythanhtoan"));
				this.setSotien(rs.getDouble("sotien"));
				this.setIdNhaPhanPhoi(rs.getString("nhapp_fk"));
				this.setTungay("");
				this.setDenngay("");
				this.setKhuVucId("");
				this.setNoCuInit();
				/**
				 * khi lọc phiếu thanh toán  ra để sửa :,Chúng ta có một chú ý trong trường hợp sửa là ; Một nhà phân phối không được 
				 * tạo mới phiếu thanh toán khi phiếu thanh toán cũ chưa chôt hết, Sau khi chốt hết đơn đặt hàng thì mới được tạo thêm mới,
				 * vì thế trong trường hợp sửa phiếu thanh toán, 1 nhà phân phối luôn luôn chỉ có 1 phiếu để sửa hoặc không có phiếu nào.
				 * Ý nghĩa câu lệnh bên dưới : Lọc ra những đơn đặt hàng trong bảng phieuthanhtoantt_dh, đồng thời sẽ lọc ra số tiền của 
				 * các đơn hàng này trong những lần thanh toán trước với điều kiện là phiếu thanh toán đó đã chốt va trang thai của no là chưa xóa nghĩa là trangthai 
				 * của bảng PHIEUTHANHTOANTT chưa xóa
				 * 
				 */
			
				String sql_getdetail="select  ISNULL((SELECT SUM(THANHTOAN) FROM PHIEUTHANHTOANTT_DH c inner join phieuthanhtoantt d on c.phieuthanhtoantt_fk=d.pk_seq "+  
				" WHERE d.trangthai!='2' and  Dondathang_fk=b.PK_SEQ AND CHOT='0'),0)AS DATHANHTOAN , "+
				" phieuthanhtoantt_fk,dondathang_fk,thanhtoan,chot,ngaydat,sotienavat from phieuthanhtoantt_dh a inner join dondathang b "+
				" on a.dondathang_fk=b.pk_Seq  where phieuthanhtoantt_fk= "+ id;
				ResultSet rs_getdetail=db.get(sql_getdetail);
				//System.out.println("Detail PhieuThanhToanTT line 70 :"+sql_getdetail);
				
				if(rs_getdetail!=null){
					while( rs_getdetail.next()){
					  IPhieuThanhToanTT_DH thanhtoan=new PhieuThanhToanTT_DH();
					  thanhtoan.setIdDonHang(rs_getdetail.getString("dondathang_fk"));
					  thanhtoan.setNgayDat(rs_getdetail.getString("ngaydat"));
					  thanhtoan.setTienDonHang(rs_getdetail.getDouble("sotienavat"));
					  thanhtoan.setTienThanhToan(rs_getdetail.getDouble("sotienavat"));
					  thanhtoan.setDaThanhToan(rs_getdetail.getDouble("dathanhtoan"));
					  thanhtoan.setId(rs_getdetail.getString("chot"));
					 listDonDatHang.add(thanhtoan);
					}	
				}
				}
			}catch(Exception er){
				this.Message="Error 286 PhieuThanhToanTT :"+ er.toString();
			}
		}	
	}
	public PhieuThanhToanTT(){
		this.HinhThuc="";
		this.DenNgay="";
		this.Message="";
		this.TuNgay="";
		this.Id="";
		this.IdNhaPhanPhoi="";
		this.NgaySua="";
		this.SoTien=0;
		this.DienGiai="";
		this.NgayTao="";
		this.NgayThuTien="";
		this.KhuVucId="";
		this.TuNgay="";
	
			
	}
	@Override
	public String getUserId() {
		// TODO Auto-generated method stub
		return this.UserId;
	}

	@Override
	public void setUserId(String userId) {
		// TODO Auto-generated method stub
		this.UserId=userId;
	}

	@Override
	public void setId(String id) {
		// TODO Auto-generated method stub
		this.Id=id;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return this.Id;
	}

	@Override
	public String getMsg() {
		// TODO Auto-generated method stub
		return this.Message;
	}

	@Override
	public void setMsg(String msg) {
		// TODO Auto-generated method stub
		this.Message=msg;
	}

	@Override
	public String getNgayTao() {
		// TODO Auto-generated method stub
		return this.NgayTao;
	}

	@Override
	public void setNgayTao(String ngaytao) {
		// TODO Auto-generated method stub
		this.NgayTao=ngaytao;
	}

	@Override
	public String getNgaySua() {
		// TODO Auto-generated method stub
		return this.NgaySua;
	}

	@Override
	public void setNgaySua(String ngaysua) {
		// TODO Auto-generated method stub
		this.NgaySua=ngaysua;
	}

	@Override
	public String getNguoiSua() {
		// TODO Auto-generated method stub
		return this.NguoiSua;
	}

	@Override
	public void setNguoiSua(String nguoisua) {
		// TODO Auto-generated method stub
		this.NguoiSua=nguoisua;
	}

	@Override
	public String getNguoiTao() {
		// TODO Auto-generated method stub
		return this.NguoiTao;
	}

	@Override
	public void setNguoiTao(String nguoitao) {
		// TODO Auto-generated method stub
		this.NguoiTao=nguoitao;
	}

	@Override
	public String getTungay() {
		// TODO Auto-generated method stub
		return this.TuNgay ;
	}

	@Override
	public void setTungay(String tungay) {
		// TODO Auto-generated method stub
		this.TuNgay=tungay;
	}

	@Override
	public String getDenngay() {
		// TODO Auto-generated method stub
		return this.DenNgay;
	}

	@Override
	public void setDenngay(String denngay) {
		// TODO Auto-generated method stub
		this.DenNgay=denngay;
	}

	@Override
	public void setSotien(double sotien) {
		// TODO Auto-generated method stub
		this.SoTien=sotien;
	}

	@Override
	public double getSotien() {
		// TODO Auto-generated method stub
		return this.SoTien;
	}

	@Override
	public void setHinhthuc(String hinhthuc) {
		// TODO Auto-generated method stub
		this.HinhThuc=hinhthuc;
	}

	@Override
	public String getHinhthuc() {
		// TODO Auto-generated method stub
		return this.HinhThuc;
	}

	@Override
	public void setDiengiai(String diengiai) {
		// TODO Auto-generated method stub
		this.DienGiai=diengiai;
	}

	@Override
	public String getDiengiai() {
		// TODO Auto-generated method stub
		return this.DienGiai;
	}

	@Override
	public void setNgaythanhtoan(String ngaythanhtoan) {
		// TODO Auto-generated method stub
		this.NgayThuTien=ngaythanhtoan;
	}

	@Override
	public String getNgaythanhtoan() {
		// TODO Auto-generated method stub
		return this.NgayThuTien;
	}

	@Override
	public boolean saveThanhToan() {
		// TODO Auto-generated method stub
		dbutils db=new dbutils();	
		try
		{
			
		db.getConnection().setAutoCommit(false);
		if(this.NguoiTao==null || this.NguoiTao.equals("")){
			this.Message="Nguoi Dung Dang Nhap Da Bi Log, Vui Long Dang Nhap Lai De Thuc Hien Chuc Nang Nay";
			return false;
		}
		
		// thuc hien insert
		String sqlSaveThanhToan="insert into phieuthanhtoantt (ngaythanhtoan,diengiai,trangthai,sotien,ngaytao,ngaysua,nguoitao,nguoisua,hinhthuctt,nhapp_fk) "+
		" values('"+this.NgayThuTien+"',N'"+this.DienGiai+"','0',"+this.SoTien+",'"+this.NgayTao+"','"+this.NgaySua+"',"+this.NguoiTao+","+this.NguoiSua+",'"+this.HinhThuc+"',"+this.IdNhaPhanPhoi+")";
				if(!db.update(sqlSaveThanhToan)){
						this.Message="Khong The Tao Phieu Thanh Toan Nay, Vui Long Kiem Tra Lai Du Lieu Nhap Vao"+sqlSaveThanhToan;
					geso.dms.center.util.Utility.rollback_throw_exception(db);
			return false;
		}
		
		//thuc hien insert chi tiet
		String query = "select IDENT_CURRENT('phieuthanhtoantt') as dhId";
		ResultSet rsDh = db.get(query);	
		try
		{
			rsDh.next();
		this.setId(rsDh.getString("dhId"));
		}catch(Exception ex){
			return false;
		}
		if(this.listDonDatHang==null || this.listDonDatHang.size()==0){
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			this.Message="Ban Khong Save Duoc Phieu Thanh Toan Nay, Chua Co Dannh Sach Phieu Thanh Toan" ;
			return false;
		}else{
			int m=0;
			while(m<this.listDonDatHang.size()){
				IPhieuThanhToanTT_DH donhang=new PhieuThanhToanTT_DH();
				donhang=this.listDonDatHang.get(m);
				if(donhang.getTienDonHang()-donhang.getDaThanhToan()== donhang.getTienThanhToan()){
					this.trangthai="1";// bang 1 la nhung don hang da tra het
				}
				else{
					this.trangthai="0";}
				
				String sql_insertdetail="insert into phieuthanhtoantt_dh (phieuthanhtoantt_fk,dondathang_fk,thanhtoan,chot ) values ("+this.Id+","+donhang.getIdDonHang()+","+donhang.getTienThanhToan()+",'"+this.trangthai+"')";
					if(donhang.getTienThanhToan()>0){
					if(!db.update(sql_insertdetail)){
					this.Message="Khong The Tao Phieu Thanh Toan Nay, Vui Long Kiem Tra Lai Du Lieu Nhap Vao : " +sql_insertdetail;
				    geso.dms.center.util.Utility.rollback_throw_exception(db);
					return false;
					}
					}
				m++;
			}
			if(m==0){
				this.Message="Phieu Thanh Toan Nay Khong Hop Le, Khong Thanh Toan Cho Don Hang Nao";
				return false;
			}
		}
		db.getConnection().commit();
		db.getConnection().setAutoCommit(false);
		}catch(Exception er){
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			this.Message="Ban khong the them chi tieu moi nay, loi : " + er.toString();
			return false;
		}
		return true;
	}

	@Override
	public boolean UpdateThanhToan() {
		// TODO Auto-generated method stub
		dbutils db=new dbutils();	
		try
		{
			
		db.getConnection().setAutoCommit(false);
		if(this.NguoiTao==null || this.NguoiTao.equals("")){
			this.Message="Nguoi Dung Dang Nhap Da Bi Log, Vui Long Dang Nhap Lai De Thuc Hien Chuc Nang Nay";
			return false;
		}
		
		// thuc hien insert
		String sqlEditThanhToan="update  phieuthanhtoantt set ngaythanhtoan='"+this.NgayThuTien+"',diengiai=N'"+this.DienGiai+"',sotien="+this.SoTien+",ngaysua='"+this.NgaySua+
		"',nguoisua="+this.NguoiSua+",hinhthuctt='"+this.HinhThuc+"',nhapp_fk="+this.IdNhaPhanPhoi +" where pk_seq= " +this.Id;
				if(!db.update(sqlEditThanhToan)){
						this.Message="Khong The Sửa Phieu Thanh Toan Nay, Vui Long Kiem Tra Lai Du Lieu Nhap Vao"+sqlEditThanhToan;
					geso.dms.center.util.Utility.rollback_throw_exception(db);
			return false;
		}
		
		//thuc hien insert chi tiet
		String query = "delete phieuthanhtoantt_dh where phieuthanhtoantt_fk="+this.Id;
		if(!db.update(query)){
			this.Message="Khong The Sua Phieu Thanh Toan Nay, Vui Long Kiem Tra Lại,Loi Cau Lenh: " +query;
		    geso.dms.center.util.Utility.rollback_throw_exception(db);
			return false;
			}
		if(this.listDonDatHang==null || this.listDonDatHang.size()==0){
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			this.Message="Bạn Không Sửa Được Phiếu Thanh Toán Này, CHưa Có Danh Sách Đơn Hàng Thanh Toán" ;
			return false;
		}else{
			int m=0;
			while(m<this.listDonDatHang.size()){
				IPhieuThanhToanTT_DH donhang=new PhieuThanhToanTT_DH();
				donhang=this.listDonDatHang.get(m);
				if(donhang.getTienDonHang()-donhang.getDaThanhToan()== donhang.getTienThanhToan()){
					this.trangthai="1";// bang 1 la nhung don hang da tra het
				}
				else{
					this.trangthai="0";}
				
				String sql_insertdetail="insert into phieuthanhtoantt_dh (phieuthanhtoantt_fk,dondathang_fk,thanhtoan,chot ) values ("+this.Id+","+donhang.getIdDonHang()+","+donhang.getTienThanhToan()+",'"+this.trangthai+"')";
					if(donhang.getTienThanhToan()>0){
					if(!db.update(sql_insertdetail)){
					this.Message="Khong The Tao Phieu Thanh Toan Nay, Vui Long Kiem Tra Lai Du Lieu Nhap Vao : " +sql_insertdetail;
				    geso.dms.center.util.Utility.rollback_throw_exception(db);
					return false;
					}
					}
				m++;
			}
			if(m==0){
				this.Message="Phieu Thanh Toan Nay Khong Hop Le, Khong Thanh Toan Cho Don Hang Nao";
				return false;
			}
		}
		db.getConnection().commit();
		db.getConnection().setAutoCommit(false);
		}catch(Exception er){
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			this.Message="Ban khong the them chi tieu moi nay, loi : " + er.toString();
			return false;
		}
		return true;
	}

	@Override
	public boolean ChotThanhToan() {
		// TODO Auto-generated method stub
		dbutils db=new dbutils();	
		try
		{
			
		db.getConnection().setAutoCommit(false);
		
		
		String sqlchot="update phieuthanhtoantt set trangthai=1 where pk_seq="+ this.Id;		
			if(!db.update(sqlchot)){
						this.Message="Khong The Tao Phieu Thanh Toan Nay, Vui Long Kiem Tra Lai Du Lieu Nhap Vao"+sqlchot;
					geso.dms.center.util.Utility.rollback_throw_exception(db);
			return false;
		}
			db.getConnection().commit();
		db.getConnection().setAutoCommit(false);
		}catch(Exception er){
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			this.Message="Ban khong the them chi tieu moi nay, loi : " + er.toString();
			return false;
		}
		return true;
	}

	@Override
	public boolean DeleteThanhToan() {
		// TODO Auto-generated method stub
	 
	 dbutils db=new dbutils();	
		try
		{
		db.getConnection().setAutoCommit(false);
		// thuc hien insert
		String sqldelete="update phieuthanhtoantt set trangthai='2' where pk_Seq="+this.Id;
				if(!db.update(sqldelete)){
						System.out.println("Khong The Thuc Hien XOA  Thanh Toan Nay, Vui Long Thu Lai ,Loi Cau Lenh :" + sqldelete);
					geso.dms.center.util.Utility.rollback_throw_exception(db);
			return false;
		}
		
		
	
		db.getConnection().commit();
		db.getConnection().setAutoCommit(false);
		}catch(Exception er){
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			this.Message="Ban khong the them chi tieu moi nay, loi : " + er.toString();
			return false;
		}
		return true;
	}

	@Override
	public List<IPhieuThanhToanTT> getListThanhToan() {
		// TODO Auto-generated method stub
		return this.ListPhieu;
	}

	@Override
	public void setListThanhToan(String sql) {
		// TODO Auto-generated method stub
		String sql_getdata="";
		if(sql.equals("")){
			sql_getdata="select a.pk_seq,a.ngaythanhtoan,a.hinhthuctt,a.diengiai,a.trangthai,a.sotien, a.ngaytao,a.ngaysua,nt.ten as nguoitao,ns.ten as nguoisua from phieuthanhtoantt a "+
			" inner join nhanvien as nt on nt.pk_seq=a.nguoitao inner join nhanvien as ns on ns.pk_seq=a.nguoisua where a.trangthai!='2'";
		}else{
			sql_getdata=sql;
		}
		dbutils db=new dbutils();
		ResultSet rs=db.get(sql_getdata);
		if(rs!=null){
			try{
				while(rs.next()){
				IPhieuThanhToanTT phieu=new PhieuThanhToanTT();
				phieu.setDiengiai(rs.getString("diengiai"));
				phieu.setHinhthuc(rs.getString("hinhthuctt"));
				phieu.setId(rs.getString("pk_Seq"));
				phieu.setTrangThai(rs.getString("trangthai"));
				phieu.setNgaySua(rs.getString("ngaysua"));
				phieu.setNgayTao(rs.getString("ngaytao"));
				phieu.setNguoiTao(rs.getString("nguoitao"));
				phieu.setNguoiSua(rs.getString("nguoisua"));
				phieu.setNgaythanhtoan(rs.getString("ngaythanhtoan"));
				phieu.setSotien(rs.getDouble("sotien"));
				ListPhieu.add(phieu);
				}
			}catch(Exception er){
				this.Message="Error 286 PhieuThanhToanTT :"+ er.toString();
			}
		}
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
/**
 * Trang thai =0 : chua duyệt
 * 			  =1 : đã chốt
 * 			  =2 : Đã xóa 	
 * @return
 */
	@Override
	public String getTrangThai() {
		// TODO Auto-generated method stub
		return this.trangthai;
	}

	@Override
	public void setTrangThai(String _trangthai) {
		// TODO Auto-generated method stub
		this.trangthai=_trangthai;
	}

	@Override
	public ResultSet getRs_vung() {
		// TODO Auto-generated method stub
		dbutils db=new dbutils();
	    return	db.get("select pk_seq,ten from vung where trangthai=1");
	}

	@Override
	public ResultSet getRs_KhuVuc() {
		// TODO Auto-generated method stub
		dbutils db=new dbutils();
	    return	db.get("select pk_seq,ten from khuvuc where trangthai=1");
	}
	//sẽ lấy rs_nhaphanphoi theo khu vuc
	@Override
	public ResultSet getRs_NhaPhanPhoi() {
		// TODO Auto-generated method stub
		dbutils db=new dbutils();
		String sql_getnpp="";
		if(this.KhuVucId!=null && !this.KhuVucId.equals("")){
			sql_getnpp=	"select pk_seq,ten from nhaphanphoi where trangthai=1 and khuvuc_fk="+ this.KhuVucId;
		}
		else{
			sql_getnpp=	"select pk_seq,ten from nhaphanphoi where trangthai=1 ";
		}
	    return	db.get(sql_getnpp);
	}

	@Override
	public void setListDonHang(List<IPhieuThanhToanTT_DH> listdonhang) {
		// TODO Auto-generated method stub
		listDonDatHang=listdonhang;
	}
    
	@Override
	public List<IPhieuThanhToanTT_DH> getListDonHang() {
		// TODO Auto-generated method stub
		return this.listDonDatHang;
	}

	@Override
	public void setListDonHangInit() {
		/**
		 * Y nghia cau lenh : lay nhung don hang chua thanh toan het cua nha phan phoi,lay tu bang DONDATHANG,co trang thai lon hon 2
		 *  nghia la nhung don hang da duoc trung tam duyet và những đơn hàng này chưa xóa theo nhà phân phối
		 *  Nhung don hang nay se co nhung lan duoc thanh toan, lay tu bang PHIEUTHANHTOANTT_DH, field THANHTOAN,chung ta sum(THANHTOAN) lai voi dieu kien la
		 *  CHOT cua don hang nay la 0(chua thanh toan xong) dong thoi phai kiem tra bảng cha của phieuthanhtoantt_dh này là bảng PHIEUTHANHTOANTT
		 *   đã xóa chưa(TRANGTHAI ='2')
		 *  Chính vì vậy ta phải inner join với bảng PHIEUTHANHTOANTT để  kiem tra trang thái
		 * Trong truong hợp nếu có xét các đơn hàng từ ngày tới ngày,thì sẽ thực hiện duyệt các đơn hàng trong khoảng thời gian từ ngày tới ngày 
		 * 
		 */
		
		
		// TODO Auto-generated method stub
		String sql=	"select D.PK_SEQ as donhang,D.NGAYDAT,isnull(D.SOTIENAVAT,0) as tongtien,D.NPP_FK,P.TEN , "+
		" ISNULL((SELECT SUM(THANHTOAN) FROM PHIEUTHANHTOANTT_DH a inner join phieuthanhtoantt b on a.phieuthanhtoantt_fk=b.pk_seq   WHERE b.trangthai!='2' and  Dondathang_fk=D.PK_SEQ AND CHOT='0'),0)AS DATHANHTOAN "+ 
		" from DONDATHANG D INNER  JOIN  NHAPHANPHOI P ON P.PK_SEQ=D.NPP_FK WHERE D.TRANGTHAI>2 AND D.PK_SEQ  NOT IN "+
		" (SELECT DONDATHANG_FK FROM PHIEUTHANHTOANTT_DH  a inner join phieuthanhtoantt b on a.phieuthanhtoantt_fk=b.pk_seq  WHERE b.trangthai!='2' and  CHOT='1') and  d.npp_fk="+this.IdNhaPhanPhoi ;
		System.out.println("Cau Lenh Sql :" + sql);
		if(!this.TuNgay.equals("")){ 
			sql=sql+" and d.ngaydat>='"+this.TuNgay+"'";
		}
		if(!this.DenNgay.equals("")){
			sql=sql+" and d.ngaydat<= '"+this.DenNgay+"'";
		}
		
		//System.out.println("PhieuThanhToanTT line 366 :" + sql);
			dbutils db=new dbutils();
			ResultSet rs= db.get(sql);
		if(rs!=null){
			try{
			while(rs.next()){
				IPhieuThanhToanTT_DH donhang=new PhieuThanhToanTT_DH();
				donhang.setDaThanhToan(rs.getDouble("dathanhtoan"));
				donhang.setNgayDat(rs.getString("ngaydat"));
				donhang.setIdDonHang(rs.getString("donhang"));
				donhang.setIdNhaPP(rs.getString("npp_fk"));
				donhang.setTenNhaPP(rs.getString("ten"));
				donhang.setTienDonHang(rs.getDouble("tongtien"));
				donhang.setId("0");
				listDonDatHang.add(donhang);
			}
			}catch(Exception er){
				System.out.println("THONG BAO LOI :"+er.toString());
			}
		}
	}

	@Override
	public void setIdNhaPhanPhoi(String idnhapp) {
		// TODO Auto-generated method stub
		this.IdNhaPhanPhoi=idnhapp;
	}

	@Override
	public String getIdNhaPhanPhoi() {
		// TODO Auto-generated method stub
		return this.IdNhaPhanPhoi;
	}
	@Override
	public void setNoCu(double nocu) {
		// TODO Auto-generated method stub
		this.NoCu=nocu;
	}
	@Override
	public double getNoCu() {
		// TODO Auto-generated method stub
		return this.NoCu;
	}
/** 
 * phuong thuc nay lay no cu mac dinh cua nha phan phoi
 */
	@Override
	public void setNoCuInit() {
		// TODO Auto-generated method stub
		String sql="select isnull(sum(isnull(sotienavat,0)-isnull(tienhuongtb,0)-isnull(tienhuongkm,0)),0)- isnull((select sum(sotien) from phieuthanhtoantt  where trangthai='1' and nhapp_fk= "+this.IdNhaPhanPhoi+" ),0) as tongsotien from dondathang " + 
					" where npp_fk="+this.IdNhaPhanPhoi+" and trangthai>2";
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
	}
@Override
public String getKhuVucId() {
	// TODO Auto-generated method stub
	return this.KhuVucId;
}
@Override
public void setKhuVucId(String makhuvuc) {
	// TODO Auto-generated method stub
	this.KhuVucId=makhuvuc;
}
}