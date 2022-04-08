/*
 * interface name : ChiTieuTTChoVung:  to identify level of money which distributor have to sell in identify time
 * Date : 2011-10-24 
 */
package geso.dms.center.beans.chitieuttchovung.imp;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import geso.dms.center.beans.chitieuttchovung.IChiTieuTTChoVung;
import geso.dms.center.util.Utility;
import geso.dms.distributor.db.sql.dbutils;

public class ChiTieuTTChoVung implements IChiTieuTTChoVung {

	String UserID;
	double Id;
	int Thang;
	int Nam;
	String NgayTao;
	String NguoiTao;
	String NguoiSua;
	String NgaySua;
	String NgayKetThuc;
	String DienGiai;
	String Message;
	String VungID;
	double ChiTieu;
	double TrungBinhThang;
	String TenVung;
	String DdkdID;
	String TenDvdk;
	String SoNgayLamViec;
	String KenhId;
	String TenKenh;
	String TrangThai;
	 List<ChiTieuTTChoVung> listchitieu =new ArrayList<ChiTieuTTChoVung>();
	 List<ChiTieuTTKhuVuc> listkhuvuc=new ArrayList<ChiTieuTTKhuVuc>();
	public ChiTieuTTChoVung(String id,String loaict){
		String  sql_getdata="";
		String sql_chitiet="";
		if(loaict.equals("0")){
			//Day La Loai Chi Tieu Primary
		  sql_getdata=" SELECT    C.KENH_FK, C.PK_SEQ, C.THANG, C.NAM, C.CHITIEU,C.SONGAYLAMVIEC, C.DIENGIAI,C.DVKD_FK,D.DONVIKINHDOANH,C.trangthai, C.NGAYKETTHUC, C.NGAYTAO, C.NGAYSUA, NT.TEN AS NGUOITAO, "+
        " NT.PK_SEQ AS NGUOISUA FROM  dbo.CHITIEU_TRUNGTAM AS C INNER JOIN "+
                    " dbo.NHANVIEN AS NT ON C.NGUOITAO = NT.PK_SEQ INNER JOIN "+
                    " dbo.NHANVIEN AS NS ON C.NGUOISUA = NS.PK_SEQ inner join DONVIKINHDOANH D on D.PK_SEQ=C.DVKD_FK   where C.PK_SEQ=" + id;

		}else{
			//Day La Chi Tieu Seconday
			sql_getdata=" SELECT    C.KENH_FK, C.PK_SEQ, C.THANG, C.NAM, C.CHITIEU,C.SONGAYLAMVIEC, C.DIENGIAI,c.trangthai, C.NGAYKETTHUC,C.DVKD_FK,D.DONVIKINHDOANH, C.NGAYTAO, C.NGAYSUA, NT.TEN AS NGUOITAO, "+
	        " NT.PK_SEQ AS NGUOISUA FROM  dbo.CHITIEU_TRUNGTAM_SEC AS C INNER JOIN "+
	                    " dbo.NHANVIEN AS NT ON C.NGUOITAO = NT.PK_SEQ INNER JOIN "+
	                    " dbo.NHANVIEN AS NS ON C.NGUOISUA = NS.PK_SEQ inner join DONVIKINHDOANH D on D.PK_SEQ=C.DVKD_FK  where C.PK_SEQ=" + id;
		
		}
		dbutils db=new dbutils();
		ResultSet rs_chitieu=db.get(sql_getdata);
		System.out.println("Chi Tieu :ChiTieuTTChoVung.java : 55 -" + sql_getdata);
		if(rs_chitieu!=null){
			try{
			while(rs_chitieu.next()){
				this.Id=rs_chitieu.getDouble("PK_SEQ");
				this.setThang(rs_chitieu.getInt("THANG"));
				this.setNam(rs_chitieu.getInt("NAM"));
				this.setChitieu(rs_chitieu.getDouble("CHITIEU"));
				this.setNgayKetThuc(rs_chitieu.getString("NGAYKETTHUC"));
				this.setNgayTao(rs_chitieu.getString("NGAYTAO"));
				this.setNgaySua(rs_chitieu.getString("NGAYSUA"));
				this.setDienGiai(rs_chitieu.getString("DIENGIAI"));
				this.setNguoiTao(rs_chitieu.getString("NGUOITAO"));
				this.setNguoiSua(rs_chitieu.getString("NGUOISUA"));
				this.setTrangThai(rs_chitieu.getString("trangthai"));
				this.setDvkdID(rs_chitieu.getString("DVKD_FK"));
				this.setTenDvkd(rs_chitieu.getString("donvikinhdoanh"));
				this.setSoNgayLamViec(rs_chitieu.getString("songaylamviec"));
				this.setKenhID(rs_chitieu.getString("kenh_fk"));
				this.setTrangThai(rs_chitieu.getString("trangthai"));
			}
			}catch(Exception er){
				this.Message="Error ChiTieuTTChoVung.java - line 59 : "+ er.toString();
			}
		}
		 Calendar c2 = Calendar.getInstance();
	        if(this.Thang<3){
	        	
	        }
	        c2.set(this.Nam,this.Thang-1, 1);//Lay ngay thang la thang nam dat chi tieu
	        //lay nguoc lai cach 3 thang
	        c2.roll(Calendar.MONTH, -3);//get datetime before 3 month
	        if(this.Thang<3){
	        	c2.roll(Calendar.YEAR,-1);
	        }
	        DateFormat formatdate=new SimpleDateFormat("yyyy-MM-dd");
	        String ngaythang=formatdate.format(c2.getTime());
	        c2.set(this.Nam,this.Thang-1,1);
	        String toingay=formatdate.format(c2.getTime());
	if(loaict.equals("0")){
		 sql_chitiet="select chitieu_trungtam_fk,khuvuc_fk,chitieu,k.ten ,ISNULL((SELECT SUM(TONGGIATRI) FROM DONHANG A INNER JOIN  NHAPHANPHOI P ON A.NPP_FK=P.PK_SEQ"+
		 " WHERE P.KHUVUC_FK=c.khuvuc_fk and a.trangthai='1' and a.kbh_fk="+this.KenhId+" AND (a.ngaynhap< '"+toingay+"') and  (A.NGAYNHAP >= '"+ngaythang+"') ),0) AS TONGCHITIEU from chitieutt_khuvuc c inner join khuvuc k on c.khuvuc_fk=k.pk_seq where chitieu_trungtam_fk ="+id;

	}else{
		 sql_chitiet="select CHITIEU_TRUNGTAM_SEC_fk,khuvuc_fk,chitieu,k.ten,sodonhang,sku,ISNULL((SELECT SUM(TONGGIATRI) FROM DONHANG A INNER JOIN  NHAPHANPHOI P ON A.NPP_FK=P.PK_SEQ"+
		 " WHERE P.KHUVUC_FK=c.khuvuc_fk and a.trangthai='1' and a.kbh_fk="+this.KenhId+" AND (a.ngaynhap< '"+toingay+"') and  (A.NGAYNHAP >= '"+ngaythang+"') ),0) AS TONGCHITIEU from CHITIEUTT_KHUVUC_SEC c inner join khuvuc k on c.khuvuc_fk=k.pk_seq where CHITIEU_TRUNGTAM_SEC_fk ="+id;

	}
	System.out.println("Bao Cao Chi Tiet : 108 ChiTieuTTChoVung:  "+sql_chitiet);
			ResultSet rs_chitiet=db.get(sql_chitiet);
		if(rs_chitiet!=null){
			try{
				while(rs_chitiet.next()){
					ChiTieuTTKhuVuc khuvuc=new ChiTieuTTKhuVuc();
					khuvuc.setID(id);
					khuvuc.setChiTieu(rs_chitiet.getDouble("chitieu"));
					khuvuc.setKhuVucId(rs_chitiet.getString("khuvuc_fk"));
					khuvuc.setTenKhuVucId(rs_chitiet.getString("ten"));
					khuvuc.setTrungBinhThang(rs_chitiet.getDouble("tongchitieu")/3);
					try{
					khuvuc.setSoDonHang(rs_chitiet.getString("sodonhang"));
					khuvuc.setSoSKU(rs_chitiet.getString("sku"));
					}catch(Exception er){
						
					}
					listkhuvuc.add(khuvuc);
				}
			}catch(Exception er){
				
			}
		}
		this.Message="";
		//this.DienGiai=rs_chitieu.gets
	}
	 public ChiTieuTTChoVung(){
		NgayKetThuc="";
		this.DienGiai="";
		this.ChiTieu=0;
		this.Message="";
		this.VungID="0";
		this.SoNgayLamViec="";
		this.NgayKetThuc="";
	 }
	@Override
	public void setChitieu(double chitieu) {
		// TODO Auto-generated method stub
	 this.ChiTieu=chitieu;
	}

	@Override
	public double getChitieu() {
		// TODO Auto-generated method stub
		return this.ChiTieu;
	}

	@Override
	public void setID(double id) {
		// TODO Auto-generated method stub
	 this.Id=id;	
	}

	@Override
	public double getID() {
		// TODO Auto-generated method stub
		return Id;
	}

	@Override
	public void setThang(int thang) {
		// TODO Auto-generated method stub
		this.Thang=thang;
	}

	@Override
	public int getThang() {
		// TODO Auto-generated method stub
		return this.Thang;
	}

	@Override
	public void setNam(int nam) {
		// TODO Auto-generated method stub
		this.Nam=nam;
	}

	@Override
	public int getNam() {
		// TODO Auto-generated method stub
		return this.Nam;
	}


	@Override
	public void setNgayKetThuc(String ngayketthuc) {
		// TODO Auto-generated method stub
		if(ngayketthuc==null){
			this.NgayKetThuc="";
		}else{
		  this.NgayKetThuc=ngayketthuc;
		}
	}

	@Override
	public String getNgayKetThuc() {
		// TODO Auto-generated method stub
		return this.NgayKetThuc;
	}

	@Override
	public void setNgayTao(String ngaytao) {
		// TODO Auto-generated method stub
		this.NgayTao =ngaytao;
	}

	@Override
	public String getNgayTao() {
		// TODO Auto-generated method stub
		return this.NgayTao;
	}

	@Override
	public void setNgaySua(String ngaysua) {
		// TODO Auto-generated method stub
		this.NgaySua=ngaysua;
	}

	@Override
	public String getNgaySua() {
		// TODO Auto-generated method stub
		return this.NgaySua;
	}

	@Override
	public void setDienGiai(String diengiai) {
		// TODO Auto-generated method stub
		this.DienGiai=diengiai;
	}

	@Override
	public String getDienGiai() {
		// TODO Auto-generated method stub
		return this.DienGiai;
	}
	/**
	 * function kiemtrahople xem dữ liệu đưa vào có hợp lệ không?
	 * @return
	 */
	 public boolean KiemTraHopLe(){
		 
		 	if(this.NguoiTao.toString().equals("null") || this.NguoiTao==""){
				this.Message="Tên đăng nhập người dùng đã bị log vì lý do bảo mật!";
				return false;
			}
			
			if(this.KenhId==null || this.KenhId==""){
				this.Message="Vui lòng chọn kênh để tạo chỉ tiêu!";
				return false;
			}
			if(this.DdkdID==null || this.DdkdID.equals("")){
				this.Message="Vui lòng chọn đơn vị kinh doanh để tạo chỉ tiêu!";
				return false;
			}
			if(this.Thang==0){
				this.Message="Tháng đặt chỉ tiêu không hợp lệ";
				return false;
			}
			if(this.Nam==0){
				this.Message="Năm đặt chỉ tiêu không hợp lệ";
				return false;
			}
			try{
			  Integer.parseInt(this.SoNgayLamViec);
			 }catch(Exception er){
				 this.Message="So Ngay Lam Viec Khong Hop Le,Vui Long Kiem Tra Lai";
				 return false;
			 }
			
		 return true;
	 }
	@Override
	public boolean SaveChiTieu() {
		// TODO Auto-generated method stub
		dbutils db=new dbutils();	
		try
		{
			if(!KiemTraHopLe()){
				return false;	
			}
	
		db.getConnection().setAutoCommit(false);
		//kiem tra xenm chi tieu trong thoi gian nay cua dvkd nay da co chua(1 chi tieu dc xac dinh cho 1 dvkd trong 1 thoi gian)
		String sql_checkexit="select pk_seq from ChiTieu_trungtam where thang= " +this.Thang+" and nam="+ this.Nam +"  and trangthai!=2 and dvkd_fk="+this.DdkdID+" and kenh_fk="+ this.KenhId;
	    ResultSet rs_check=db.get(sql_checkexit);
		if(rs_check!=null){
			if(rs_check.next()){//co du lieu
				this.setMessage("Chi Tieu Trong Thang Da Thiet Lap Cho Don Vi Kinh Doanh Theo Kenh Này Rồi, Vui Long Kiem Tra Lai");
			 return false;
			}
		}
		
		String sqlSaveChiTieu="insert into CHITIEU_TRUNGTAM (THANG,NAM,CHITIEU,NGAYKETTHUC,NGAYTAO,NGUOITAO,NGAYSUA,NGUOISUA,DIENGIAI,TRANGTHAI,DVKD_FK,SONGAYLAMVIEC,KENH_FK) " +
		"values ("+this.Thang+","+this.Nam+","+this.ChiTieu+",'"+this.NgayKetThuc+"','"+this.NgayTao+"','"+this.NguoiTao+"','"+this.NgaySua+"','"+this.NguoiSua+"',N'"+this.DienGiai+"','0',"+this.DdkdID+","+this.SoNgayLamViec+","+this.KenhId+")";
		if(!db.update(sqlSaveChiTieu)){
			this.Message=" Ban khong the tao chi tieu ,vui long kiem tra lai du lieu nhap vao";
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return false;
		}
		
		//thuc hien insert chi tiet
		String query = "select IDENT_CURRENT('CHITIEU_TRUNGTAM') as dhId";
		ResultSet rsDh = db.get(query);	
		try
		{
			rsDh.next();
		this.setID(rsDh.getDouble("dhId"));
		}catch(Exception ex){
			return false;
		}
		int count=0;
		if(this.listkhuvuc!=null){
			while (count <this.listkhuvuc.size()){
				ChiTieuTTKhuVuc khuvuc=new ChiTieuTTKhuVuc();
				khuvuc=this.listkhuvuc.get(count);
				String sql_insertdetail="insert into chitieutt_khuvuc(chitieu_trungtam_fk,khuvuc_fk,chitieu) values("+this.getID()+","+khuvuc.getKhuVucId()+","+khuvuc.getChiTieu()+")";
				if(!db.update(sql_insertdetail)){
					geso.dms.center.util.Utility.rollback_throw_exception(db);
					this.Message="Ban Khong The Them Chi Tieu  Cho Khu Vuc :" + khuvuc.getTenKhuVucId()+" .Loi Tren Dong Lenh Sau : "+ sql_insertdetail;
					System.out.println("sqlSaveChiTieu : 146 Chitieuttchovung " +sql_insertdetail);
					return false;
				}
				count++;
			}
		}
		db.getConnection().commit();
		db.getConnection().setAutoCommit(true);
		}catch(Exception er){
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			this.setMessage("Ban khong the them chi tieu moi nay, loi : " + er.toString());
			return false;
		}
		return true;
	}
	@Override
	public boolean EditChiTieu() {
		// TODO Auto-generated method stub
		dbutils db=new dbutils();
		try{
			if(!KiemTraHopLe()){
				return false;	
			}
			db.getConnection().setAutoCommit(false);
		//updsssate lai bang chitieu_trungtam;
			int thangold=0;
			int namold=0;
			String dvkd_fkold="";
			String kenh_fkold="";
			String strgetthangnam="select thang,nam,dvkd_fk,kenh_fk from chitieu_trungtam where pk_seq="+ this.Id;
			ResultSet rs_getthangnam=db.get(strgetthangnam);
			if(rs_getthangnam!=null){
				if(rs_getthangnam.next()){
				  thangold=rs_getthangnam.getInt("thang");
				  namold=rs_getthangnam.getInt("nam");
				  dvkd_fkold=rs_getthangnam.getString("dvkd_fk").trim();
				  kenh_fkold=rs_getthangnam.getString("kenh_fk").trim();
				}
			}
			//System.out.println(" CU : " +dvkd_fkold );
			//System.out.println(" Moi : " +this.DdkdID );
			if(this.Thang==thangold && this.Nam==namold && this.KenhId.trim().equals(kenh_fkold) && this.DdkdID.trim().equals(dvkd_fkold)){////neu thang nam khong thay doi thi khong can kiem tra
				//không làm gì hết
			}else{
				//kiem tra xenm chi tieu trong thoi gian nay da co chua
				String sql_checkexit="select pk_seq from ChiTieu_trungtam where thang= " +this.Thang+" and nam="+ this.Nam  +" and trangthai!=2 and dvkd_fk="+this.DdkdID +" and kenh_fk="+ this.KenhId;
			    ResultSet rs_check=db.get(sql_checkexit);
			    
				if(rs_check!=null){
					if(rs_check.next()){//co du lieu
						this.setMessage("Chi Tieu Trong Thang Da Thiet Lap cho DVKD này, Vui Long Kiem Tra Lai");
					 return false;
					}
				}
			}
			
		String sqlEditChiTieu="update CHITIEU_TRUNGTAM set THANG="+this.Thang+",NAM="+this.Nam+",CHITIEU="+this.ChiTieu+",NGAYKETTHUC='"+this.NgayKetThuc+
		"',NGAYSUA='"+this.NgaySua+"',NGUOISUA='"+this.NguoiSua+"',DIENGIAI='"+this.DienGiai+"',dvkd_fk="+this.DdkdID+",songaylamviec="+this.SoNgayLamViec+",kenh_fk="+this.KenhId+"  where pk_seq="+ this.Id;
		//System.out.println("SQL : "+ sqlEditChiTieu);
		if(!db.update(sqlEditChiTieu)){
			this.Message="Khong The Chinh Sua Chi Tieu Nay,Vui Long Kiem Tra Lai. Loi Dong Lenh Sau :"+ sqlEditChiTieu;
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return false;
		}
		String sql_deletechitiet="delete chitieutt_khuvuc where chitieu_trungtam_fk="+ this.Id;
		if(!db.update(sql_deletechitiet)){
			this.Message="Xay Ra Loi Trong Qua Trinh Them Moi,Vui Long Kiem Tra Lai.";
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return false;
		}
		int count=0;
		if(this.listkhuvuc!=null){
			while (count <this.listkhuvuc.size()){
				ChiTieuTTKhuVuc khuvuc=new ChiTieuTTKhuVuc();
				khuvuc=this.listkhuvuc.get(count);
				String sql_insertdetail="insert into chitieutt_khuvuc(chitieu_trungtam_fk,khuvuc_fk,chitieu) values("+this.getID()+","+khuvuc.getKhuVucId()+","+khuvuc.getChiTieu()+")";
				if(!db.update(sql_insertdetail)){
					geso.dms.center.util.Utility.rollback_throw_exception(db);
					this.Message="Khong The Tao Chi Tieu Cho Khu vuc :" + khuvuc.getTenKhuVucId()+". Vui Long Kiem Tra Lai";
					System.out.println("sqlSaveChiTieu : 146 Chitieuttchovung " +sql_insertdetail);
					return false;
				}
				count++;
			}
		}
		db.getConnection().commit();
		db.getConnection().setAutoCommit(true);
		}catch(Exception er){
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return false;
		}
		return true;
	}
	@Override
	public boolean DeleteChitieu() {
		// TODO Auto-generated method stub
		
		dbutils db=new dbutils();	
		try{
			
		db.getConnection().setAutoCommit(false);
		int thang_=0;
		int nam_=0;
		String dvkd_fk_="";
		String sql_getthangnam_dvkd="select a.pk_seq from chitieu_trungtam a inner join chitieu b on  a.thang=b.thang and a.nam=b.nam "+
		" and a.dvkd_fk=b.dvkd_fk and a.kenh_fk=b.kenh_fk  where b.trangthai!='2' and  a.pk_seq="+ this.Id;
		ResultSet rs_check=db.get(sql_getthangnam_dvkd);
		if(rs_check!=null){
			if(rs_check.next()){
				this.setMessage("Chi Tieu Trong Thang Da Thiet Lap Cho Cac Khu Vuc Thuoc Vung Nay, Vui Long Kiem Tra Lai");
				return false;
			}
		}
		/*	
		ResultSet rs_check=db.get(sql_getthangnam_dvkd);
		if(rs_check!=null){
			try{
				while (rs_check.next()){
					thang_=rs_check.getInt("thang");
					nam_=rs_check.getInt("nam");
					dvkd_fk_=rs_check.getString("dvkd_fk");
				}
			}catch(Exception er){
				System.out.println("Error ChiTieuTTChoVung 344 line : "+ er.toString());
			}
		}
		String sql_check_exitctkhuvuc_npp="select pk_seq from chitieu where thang="+ thang_+ " and nam="+nam_+" and trangthai!='2'  and dvkd_fk="+ dvkd_fk_; 
		ResultSet rs_chechexitct_kv_npp=db.get(sql_check_exitctkhuvuc_npp);
		if(rs_chechexitct_kv_npp!=null){
			if(rs_chechexitct_kv_npp.next()){
				this.Message="Chi Tieu Nay Da Duoc Thiet Lap Xuong Nha Phan Phoi, Khong Duoc Phep Xoa";
				return false;
			}
		}
		*/
		String sqlDelChiTieu="update CHITIEU_TRUNGTAM set trangthai='2' where pk_seq="+ this.Id;
		if(!db.update(sqlDelChiTieu)){//khong xoa duoc
			  geso.dms.center.util.Utility.rollback_throw_exception(db);
		  return false;
		}
		db.getConnection().commit();
		db.getConnection().setAutoCommit(true);
		}catch(Exception er){
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			this.setMessage("Ban khong the xoa chi tieu moi nay, loi : " + er.toString());
			return false;
		}
        return false;
	}

	
	public void setUserId(String userid) {
		// TODO Auto-generated method stub
		this.UserID=userid;
	}


	public String getUserId() {
		// TODO Auto-generated method stub
		return this.UserID;
	}


	public List<ChiTieuTTChoVung> getChiTieu() {
		// TODO Auto-generated method stub
		return listchitieu;
	}

	public void setListChiTieu(String sql,String loaichitieu) {	
		String sql_getdata="";
	
	if(loaichitieu.equals("0")){	
	  sql_getdata= " SELECT    C.KENH_FK,C.TRANGTHAI, C.PK_SEQ, C.THANG, C.NAM, C.CHITIEU, C.DIENGIAI,C.DVKD_FK,D.DONVIKINHDOANH, C.NGAYKETTHUC, C.NGAYTAO ,C.SONGAYLAMVIEC, C.NGAYSUA, NT.TEN AS NGUOITAO, "+
                         " NS.TEN AS NGUOISUA    FROM  dbo.CHITIEU_TRUNGTAM AS C INNER JOIN "+
                         " dbo.NHANVIEN AS NT ON C.NGUOITAO = NT.PK_SEQ INNER JOIN  "+
                         " dbo.NHANVIEN AS NS ON C.NGUOISUA = NS.PK_SEQ inner join DONVIKINHDOANH D on D.PK_SEQ=C.DVKD_FK  where 1=1 ";
	
	}else{
		  sql_getdata= " SELECT    C.KENH_FK,C.TRANGTHAI, C.PK_SEQ, C.THANG, C.NAM, C.CHITIEU, C.DIENGIAI,C.DVKD_FK,D.DONVIKINHDOANH, C.NGAYKETTHUC,C.SONGAYLAMVIEC, C.NGAYTAO, C.NGAYSUA, NT.TEN AS NGUOITAO, "+
          " NS.TEN AS NGUOISUA    FROM  dbo.CHITIEU_TRUNGTAM_SEC AS C INNER JOIN "+
          " dbo.NHANVIEN AS NT ON C.NGUOITAO = NT.PK_SEQ INNER JOIN  "+
          " dbo.NHANVIEN AS NS ON C.NGUOISUA = NS.PK_SEQ inner join DONVIKINHDOANH D on D.PK_SEQ=C.DVKD_FK  where 1=1 ";

	}
    	String sql_tk="";
    	
	if(sql!=""){//in cercumstance if you set sql!="" then this method will chance defauld string of sql to get data
	sql_tk=sql;
	} 
	else{
		sql_tk=sql_getdata;
	}
	
	//phanquyen
	Utility ut = new Utility();
	sql_tk += " and C.KENH_FK in "+ ut.quyen_kenh(UserID);
	System.out.println("SQL : "+sql_tk);
	dbutils db=new dbutils();
	  try{
		
	  ResultSet rsChiTieu=	db.get(sql_tk);
	  if(rsChiTieu!=null){
		  while(rsChiTieu.next()){
		    ChiTieuTTChoVung CT=new ChiTieuTTChoVung();
		    CT.setID(rsChiTieu.getDouble("PK_SEQ"));
		    CT.setThang(rsChiTieu.getInt("THANG"));
		    CT.setNam(rsChiTieu.getInt("NAM"));
		    CT.setChitieu(rsChiTieu.getDouble("CHITIEU"));
		    CT.setNgayKetThuc(rsChiTieu.getString("NGAYKETTHUC"));
		    CT.setNgayTao(rsChiTieu.getString("NGAYTAO"));
		    CT.setNgaySua(rsChiTieu.getString("NGAYSUA"));
		    CT.setDienGiai(rsChiTieu.getString("DIENGIAI"));
		    CT.setNguoiTao(rsChiTieu.getString("NGUOITAO"));
		    CT.setNguoiSua(rsChiTieu.getString("NGUOISUA"));
		    CT.setDvkdID(rsChiTieu.getString("DVKD_FK"));
		    CT.setTenDvkd(rsChiTieu.getString("DONVIKINHDOANH"));
		    CT.setSoNgayLamViec(rsChiTieu.getString("SONGAYLAMVIEC"));
		    CT.setKenhID(rsChiTieu.getString("kenh_fk"));
		    CT.setTrangThai(rsChiTieu.getString("trangthai"));
		    listchitieu.add(CT);
		  }
	  }
	  }catch(Exception er){
		  System.out.println("Error Class name : CHITIEU.JAVA- LINE 216 :STRING SQL" + er.toString());
		
	  }
	}
	@Override
	public void setNguoiTao(String nguoitao) {
		// TODO Auto-generated method stub
		this.NguoiTao=nguoitao;		
	}

	@Override
	public String getNguoiTao() {
		// TODO Auto-generated method stub
		return this.NguoiTao;
	}

	@Override
	public void setNguoiSua(String nguoisua) {
		// TODO Auto-generated method stub
		this.NguoiSua=nguoisua;
	}

	@Override
	public String getNguoiSua() {
		// TODO Auto-generated method stub
		return this.NguoiSua;
	}

	@Override
	public void setMessage(String strmessage) {
		// TODO Auto-generated method stub
		this.Message=strmessage;
	}

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return this.Message;
	}
	public List<ChiTieuTTKhuVuc> getListKhuVuc() {
		// TODO Auto-generated method stub
		return listkhuvuc;
	}
	public void setListKhuVuc(List<ChiTieuTTKhuVuc> list) {
		this.listkhuvuc=list;
		
	}
	@Override
	public void setVungID(String vungid) {
		// TODO Auto-generated method stub
		this.VungID=vungid;
	}
	@Override
	public String getVungID() {
		// TODO Auto-generated method stub
		return this.VungID;
	}
	public String getTenVungID() {
		// TODO Auto-generated method stub
		return this.TenVung;
	}
	public void setTenVungID(String tenvung) {
		// TODO Auto-generated method stub
		this.TenVung=tenvung;
	}
	@Override
	public boolean EditChiTieuSec() {
		// TODO Auto-generated method stub
		dbutils db=new dbutils();
		try{
			db.getConnection().setAutoCommit(false);
		//update lai bang chitieu_trungtam;
			if(!KiemTraHopLe()){
				return false;
			}
			int thangold=0;
			int namold=0;
			String dvkdold="";
			String kenhold="";
	
			String strgetthangnam="select thang,nam,dvkd_fk,kenh_fk from CHITIEU_TRUNGTAM_SEC where pk_seq="+ this.Id;
			ResultSet rs_getthangnam=db.get(strgetthangnam);
			if(rs_getthangnam!=null){
				if(rs_getthangnam.next()){
				  thangold=rs_getthangnam.getInt("thang");
				  namold=rs_getthangnam.getInt("nam");
				  dvkdold=rs_getthangnam.getString("dvkd_fk").trim();
				  kenhold=rs_getthangnam.getString("kenh_fk").trim();
				}
			}
			if(this.Thang==thangold && this.Nam==namold &&this.KenhId.trim().equals(kenhold) && this.DdkdID.trim().equals(dvkdold)){////neu thang nam khong thay doi thi khong can kiem tra
				
			}else{
				//kiem tra xenm chi tieu trong thoi gian nay da co chua
				String sql_checkexit="select pk_seq from CHITIEU_TRUNGTAM_SEC where thang= " +this.Thang+" and nam="+ this.Nam  +" and kenh_fk="+this.KenhId+" and trangthai!=2 and dvkd_fk="+this.DdkdID;
			    ResultSet rs_check=db.get(sql_checkexit);
				if(rs_check!=null){
					if(rs_check.next()){//co du lieu
						this.setMessage("Chi Tieu Trong Thang Da Thiet Lap Cho Cac Khu Vuc Thuoc Vung Nay, Vui Long Kiem Tra Lai");
					 return false;
					}
				}
			}
		String sqlEditChiTieu="update CHITIEU_TRUNGTAM_SEC set THANG="+this.Thang+",NAM="+this.Nam+",CHITIEU="+this.ChiTieu+",NGAYKETTHUC='"+this.NgayKetThuc+
		"',NGAYSUA='"+this.NgaySua+"',NGUOISUA='"+this.NguoiSua+"',DIENGIAI='"+this.DienGiai+"',dvkd_fk="+this.DdkdID+",songaylamviec="+this.SoNgayLamViec+",kenh_fk="+this.KenhId+" where pk_seq="+ this.Id;
		//System.out.println("SQL : "+ sqlEditChiTieu);
		if(!db.update(sqlEditChiTieu)){
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return false;
		}
		String sql_deletechitiet="delete CHITIEUTT_KHUVUC_SEC where CHITIEU_TRUNGTAM_SEC_fk="+ this.Id;
		if(!db.update(sql_deletechitiet)){
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return false;
		}
		int count=0;
		if(this.listkhuvuc!=null){
			while (count <this.listkhuvuc.size()){
				ChiTieuTTKhuVuc khuvuc=new ChiTieuTTKhuVuc();
				khuvuc=this.listkhuvuc.get(count);
				String sql_insertdetail="insert into CHITIEUTT_KHUVUC_SEC(CHITIEU_TRUNGTAM_SEC_fk,khuvuc_fk,chitieu,sodonhang,sku) values("+this.getID()+","+khuvuc.getKhuVucId()+","+khuvuc.getChiTieu()+","+khuvuc.SoDonHang+","+khuvuc.getSoSKU()+")";
				if(!db.update(sql_insertdetail)){
					geso.dms.center.util.Utility.rollback_throw_exception(db);
					this.Message="Khong The Thiet Lap Chi Tieu Cho Khu Vuc "+ khuvuc.getTenKhuVucId()+ " .Loi Du Lieu Dua Vao,Vui Long Thu Lai";
					
					System.out.println("sqlSaveChiTieu : 146 Chitieuttchovung " +sql_insertdetail);
					return false;
				}
				count++;
			}
		}
		
	
		db.getConnection().commit();
		db.getConnection().setAutoCommit(true);
		}catch(Exception er){
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return false;
		}
		return true;
	}
	@Override
	public boolean DeleteChitieu_Sec() {
		// TODO Auto-generated method stub
		dbutils db=new dbutils();	
		try{
			
		db.getConnection().setAutoCommit(false);
		int thang_=0;
		int nam_=0;
		String dvkd_fk_="";
		//Kiểm tra xem các chỉ tiêu của khu vực này đã phân bổ xuống nhà phân phối chưa?
		String sql_getthangnam_dvkd="select a.pk_seq from chitieu_trungtam_sec a inner join chitieu_sec b on  a.thang=b.thang and a.nam=b.nam "+
		" and a.dvkd_fk=b.dvkd_fk and a.kenh_fk=b.kenh_fk  where b.trangthai!='2' and  a.pk_seq="+ this.Id;
		ResultSet rs_check=db.get(sql_getthangnam_dvkd);
		if(rs_check!=null){
			if(rs_check.next()){
				this.setMessage("Chi Tieu Trong Thang Da Thiet Lap Cho Cac Khu Vuc Thuoc Vung Nay, Vui Long Kiem Tra Lai");
				return false;
			}
		}
		/*
		String sql_getthangnam_dvkd="select thang,nam,dvkd_fk from CHITIEU_TRUNGTAM_SEC where pk_seq="+ this.Id;
		ResultSet rs_check=db.get(sql_getthangnam_dvkd);
		if(rs_check!=null){
			try{
				while (rs_check.next()){
					thang_=rs_check.getInt("thang");
					nam_=rs_check.getInt("nam");
					dvkd_fk_=rs_check.getString("dvkd_fk");
				}
			}catch(Exception er){
				System.out.println("Error ChiTieuTTChoVung 344 line : "+ er.toString());
			}
		}
		String sql_check_exitctkhuvuc_npp="select pk_seq from chitieu_pri where thang="+ thang_+ " and nam="+nam_+" and trangthai!='2'  and dvkd_fk="+ dvkd_fk_; 
		ResultSet rs_chechexitct_kv_npp=db.get(sql_check_exitctkhuvuc_npp);
		if(rs_chechexitct_kv_npp!=null){
			if(rs_chechexitct_kv_npp.next()){
				this.Message="Chi Tieu Nay Da Duoc Thiet Lap Xuong Nha Phan Phoi, Khong Duoc Phep Xoa";
				return false;
			}
		}
		*/
		String sqlDelChiTieu="update CHITIEU_TRUNGTAM_SEC set trangthai='2' where pk_seq="+ this.Id;
		if(!db.update(sqlDelChiTieu)){//khong xoa duoc
			  geso.dms.center.util.Utility.rollback_throw_exception(db);
		  return false;
		}
		db.getConnection().commit();
		db.getConnection().setAutoCommit(true);
		}catch(Exception er){
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			this.setMessage("Ban khong the xoa chi tieu moi nay, loi : " + er.toString());
			return false;
		}
        return false;
	}
	@Override
	public boolean SaveChiTieu_Sec() {
		// TODO Auto-generated method stub
		
		dbutils db=new dbutils();	
		try
		{
			if(!KiemTraHopLe()){
				return false;
			}
		db.getConnection().setAutoCommit(false);
		//kiem tra xenm chi tieu trong thoi gian nay da co chua
		String sql_checkexit="select pk_seq from CHITIEU_TRUNGTAM_SEC where thang= " +this.Thang+" and nam="+ this.Nam +"  and trangthai!=2 and dvkd_fk="+this.DdkdID +" and kenh_fk= "+this.KenhId;
	    ResultSet rs_check=db.get(sql_checkexit);
		if(rs_check!=null){
			if(rs_check.next()){//co du lieu
				this.setMessage("Chi Tieu Trong Thang Da Thiet Lap Theo Kenh Va Don Vi Kinh Doanh Nay, Vui Long Kiem Tra Lai");
			 return false;
			}
		}
		
		String sqlSaveChiTieu="insert into CHITIEU_TRUNGTAM_SEC (THANG,NAM,CHITIEU,NGAYKETTHUC,NGAYTAO,NGUOITAO,NGAYSUA,NGUOISUA,DIENGIAI,TRANGTHAI,dvkd_fk,songaylamviec,kenh_fk) " +
		"values ("+this.Thang+","+this.Nam+","+this.ChiTieu+",'"+this.NgayKetThuc+"','"+this.NgayTao+"','"+this.NguoiTao+"','"+this.NgaySua+"','"+this.NguoiSua+"',N'"+this.DienGiai+"','0',"+this.DdkdID+","+this.SoNgayLamViec+","+this.KenhId+")";
		if(!db.update(sqlSaveChiTieu)){
			this.Message="KHong The Them Chi Tieu Cho Khu Vuc Nay,Vui Long Kiem Tra Lai Du Lieu :" + sqlSaveChiTieu;
			System.out.println("sqlSaveChiTieu : 146 Chitieuttchovung " +sqlSaveChiTieu);
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return false;
		}
		
		//thuc hien insert chi tiet
		String query = "select IDENT_CURRENT('CHITIEU_TRUNGTAM_SEC') as dhId";
		ResultSet rsDh = db.get(query);	
		try
		{
			rsDh.next();
		this.setID(rsDh.getDouble("dhId"));
		}catch(Exception ex){
			return false;
		}
		int count=0;
		if(this.listkhuvuc!=null){
			while (count <this.listkhuvuc.size()){
				ChiTieuTTKhuVuc khuvuc=new ChiTieuTTKhuVuc();
				khuvuc=this.listkhuvuc.get(count);
				String sql_insertdetail="insert into CHITIEUTT_KHUVUC_SEC(CHITIEU_TRUNGTAM_SEC_fk,khuvuc_fk,chitieu,sodonhang,sku) values("+this.getID()+","+khuvuc.getKhuVucId()+","+khuvuc.getChiTieu()+","+khuvuc.getSoDonHang()+","+khuvuc.getSoSKU()+" )";
				if(!db.update(sql_insertdetail)){
					geso.dms.center.util.Utility.rollback_throw_exception(db);
					this.Message="Khong The Luu Chi Tieu Cho Khu Vuc :" + khuvuc.getTenKhuVucId();
					System.out.println("sqlSaveChiTieu : 146 Chitieuttchovung " +sql_insertdetail);
					return false;
				}
				count++;
			}
		}
		db.getConnection().commit();
		db.getConnection().setAutoCommit(true);
		}catch(Exception er){
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			this.setMessage("Ban khong the them chi tieu moi nay, loi : " + er.toString());
			return false;
		}
		return true;
	}
	@Override
	public String getDVKDID() {
		// TODO Auto-generated method stub
		return this.DdkdID;
	}
	@Override
	public void setDvkdID(String dvkdId) {
		// TODO Auto-generated method stub
		this.DdkdID=dvkdId;
	}
	@Override
	public String getTenDVKD() {
		// TODO Auto-generated method stub
		return this.TenDvdk;
	}
	@Override
	public void setTenDvkd(String tendvkd) {
		// TODO Auto-generated method stub
		this.TenDvdk=tendvkd ;
	}
	@Override
	public void setSoNgayLamViec(String songaylamviec) {
		// TODO Auto-generated method stub
		this.SoNgayLamViec=songaylamviec;
	}
	@Override
	public String getSoNgayLamViec() {
		// TODO Auto-generated method stub
		return this.SoNgayLamViec;
	}
	@Override
	public String getKenhID() {
		// TODO Auto-generated method stub
		return this.KenhId;
	}
	@Override
	public void setKenhID(String kenhid) {
		// TODO Auto-generated method stub
		this.KenhId=kenhid;
		
	}
	@Override
	public ResultSet getRsKenh() {
		// TODO Auto-generated method stub
		String sql="select pk_Seq,ten from  kenhbanhang where trangthai='1'";
		dbutils db=new dbutils();
		return  db.get(sql);
	}
	@Override
	public void setTenKenh(String tenkenh) {
		// TODO Auto-generated method stub
		this.TenKenh=tenkenh;
	}
	@Override
	public String getTenKenh() {
		// TODO Auto-generated method stub
		String sql="select pk_seq,ten from kenhbanhang where pk_seq="+this.KenhId;
		dbutils db=new dbutils();
		ResultSet rs=db.get(sql);
		if(rs!=null){
			try{
			if(rs.next()){
				return rs.getString("ten");
			}
			}catch(Exception er){
				return "";
			}
		}
	   return "";
	}
	@Override
	public void setTrangThai(String trangthai) {
		// TODO Auto-generated method stub
		this.TrangThai=trangthai;
	}
	@Override
	public String getTrangThai() {
		// TODO Auto-generated method stub
		return this.TrangThai;
	}
	@Override
	public boolean chotChitieu() {
		// TODO Auto-generated method stub
		dbutils db=new dbutils();
		String sqlDelChiTieu="update CHITIEU_TRUNGTAM set trangthai='1' where pk_seq="+ this.Id;
		if(!db.update(sqlDelChiTieu)){//khong xoa duoc
			  geso.dms.center.util.Utility.rollback_throw_exception(db);
		  return false;
		}
		return true;
	}
	@Override
	public boolean chotChitieu_Sec(){
		// TODO Auto-generated method stub
		dbutils db=new dbutils();
		String sqlDelChiTieu="update CHITIEU_TRUNGTAM_SEC set trangthai='1' where pk_seq="+ this.Id;
		if(!db.update(sqlDelChiTieu)){//khong xoa duoc
			  geso.dms.center.util.Utility.rollback_throw_exception(db);
		  return false;
		}
		return true;
	}
	@Override
	public void setTrungBinhThang(double trungbinhthang) {
		// TODO Auto-generated method stub
	    this.TrungBinhThang=trungbinhthang;	
	}
	@Override
	public double getTrungBinhThang() {
		// TODO Auto-generated method stub
		return this.TrungBinhThang;
	}

}
