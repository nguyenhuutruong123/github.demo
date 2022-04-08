package geso.dms.center.beans.capnhatnhanvien.imp;

import geso.dms.center.beans.capnhatnhanvien.ICapnhatnhanvien;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Capnhatnhanvien implements ICapnhatnhanvien {

	String Ten;
	String Ngaysinh;
	String Diachi;
	String Dienthoai;
	String Email;
	String Tendangnhap;
	String matkhau;
	//String Trangthai;
	String Phanloai;
	String userId;
	ResultSet quyen;
	String nppId;
	String userTen;
	String trangthai;
	ResultSet quyenchon;
	String Id;
	String msg;
	ResultSet kenh;
	ResultSet kenhchon;
	ResultSet npp;
	ResultSet nppchon;
	ResultSet sanpham;
	ResultSet sanphamchon;
	ResultSet kho;
	ResultSet khochon;
	ResultSet nhomskus;
	ResultSet nhomskuschon;
	String vungId;
	ResultSet vung;
	String khuvucId;
	ResultSet khuvuc;
	String nhanhangId;
	ResultSet nhanhang;
	String chungloaiId;
	ResultSet chungloai;
	ResultSet nhaphanphoi;
	
	String VungId;
	String KhuvucId;
	
	//dành cho lập kế hoạch nhân viên
	String loai = "0"; //Loại nhân viên: 0: nhân viên | 1: RSM | 2: ASM | 3: SS
	String loaiId = ""; //Mã RSM | ASM | SS tùy loại nhân viên
	ResultSet loaiRs;
	ResultSet DanhmucquyenRs;
	
	String sohoadontu;
	String sohoadonden;
	
	dbutils db;
	String chon;
	String nppIds;
	
	//danh muc quyen
	String quyenId;
	String nhanmaildms;
	
	public Capnhatnhanvien()
	{   this.Id ="";
		this.Ten="";
		this.Ngaysinh="";
		this.Dienthoai="";
		this.Email="";
		this.Tendangnhap="";
		this.matkhau = "";
		this.Phanloai="";
		this.userId="";
		this.nppId="";
		this.userTen="";
		this.trangthai="";
		this.vungId ="";
		this.khuvucId ="";
		this.nhanhangId ="";
		this.chungloaiId="";
		this.Phanloai ="";
		this.chon ="1";
		this.msg="";
		this.nppIds="";
		this.kenhId="";
		this.activeTab="0";
		
		this.quyenId = "";
		this.sohoadontu="";
		this.sohoadonden = "";
		this.qhId="";
		this.ttId="";
		
		this.VungId = "";
		this.KhuvucId = "";
		this.nhanmaildms = "0";
		
		db = new dbutils();
	}
	public Capnhatnhanvien(String Id)
	{   this.Id = Id;
		this.userId="";
		this.nppId="";
		this.matkhau = "";
		this.userTen="";
		this.trangthai="";
		this.vungId ="";
		this.khuvucId ="";
		this.nhanhangId ="";
		this.chungloaiId="";
		this.chon ="1";
		this.Phanloai="";
		this.msg="";
		this.nppIds="";
		this.kenhId="";
		this.activeTab="0";
		
		this.sohoadontu="";
		this.sohoadonden = "";
		this.quyenId = "";
		this.qhId="";
		this.ttId="";
		
		this.VungId = "";
		this.KhuvucId = "";
		this.nhanmaildms = "0";
		
		db = new dbutils();
	}
	
	
	public void setuserId(String userId) {
		
		this.userId = userId;
	}
	
	public String getuserId() {
		
		return userId;
	}
	
	public void setnppId(String nppId) {
		
		this.nppId = nppId;
	}
	
	public String getnppId() {
		
		return this.nppId;
	}
	
	public void setTen(String Ten) {
		
		this.Ten = Ten;
	}
	
	public String getTen() {
		
		return this.Ten;
	}
	
	public void setngaysinh(String ngaysinh) {
		
		this.Ngaysinh = ngaysinh;
	}
	
	public String getngaysinh() {
		
		return this.Ngaysinh;
	}
	
	public void setemail(String email) {
		
		this.Email = email;
	}
	
	public String getemail() {
		
		return this.Email;
	}
	
	public void setdienthoai(String dienthoai) {
		
		this.Dienthoai = dienthoai;
	}
	
	public String getdienthoai() {
		
		return this.Dienthoai;
	}
	
	public void settendangnhap(String tendangnhap) {
		
		this.Tendangnhap = tendangnhap;
	}
	
	public String gettendangnhap() {
		
		return this.Tendangnhap;
	}
	
	public void setmatkhau(String matkhau) {
		
		this.matkhau = matkhau;
	}
	
	public String getmatkhau() {
		
		return this.matkhau;
	}

	public void setphanloai(String phanloai) {
		
		this.Phanloai = phanloai;
	}
	
	public String getphanloai() {
		
		return this.Phanloai;
	}
	
	public void setquyen(ResultSet quyen) {
		
		this.quyen = quyen;
	}
	
	
	public void init() {
		
		if(this.Id.length()>0)
		{
			String sql = "\n select ten, isnull(ngaysinh, ' ') as ngaysinh, isnull(dienthoai,' ') as dienthoai, isnull(email, ' ' ) as email, dangnhap, "+
						"\n isnull(phanloai,'') as phanloai,isnull(convsitecode,'') as convsitecode,trangthai, isnull(loai, 0) as loai, bm_fk, asm_fk, gsbh_fk," +
					    "\n isnull(sohoadontu,'') as sohoadontu, isnull(sohoadonden,'') as sohoadonden, nhanmaildms " +
					    "\n from nhanvien where pk_seq ='"+ this.Id+"'";
			System.out.println("Init NV: "+sql);
			ResultSet rs = db.get(sql);
			try {
				while(rs.next())
				{
					this.Ten= rs.getString("ten");
					if(rs.getString("ngaysinh") == null || rs.getString("ngaysinh").equals("null") || rs.getString("ngaysinh").equals("NULL") )
						this.Ngaysinh = " ";
					else
						this.Ngaysinh = rs.getString("ngaysinh");
					
					if(rs.getString("dienthoai") == null || rs.getString("dienthoai").equals("null") || rs.getString("dienthoai").equals("NULL") )
						this.Dienthoai = " ";
					else
						this.Dienthoai=rs.getString("dienthoai");
									
					if(rs.getString("email") == null || rs.getString("email").equals("null") || rs.getString("email").equals("NULL") )
						this.Email = " ";
					else
						this.Email = rs.getString("email");
					
					this.Tendangnhap=rs.getString("dangnhap");
					
					this.Phanloai=rs.getString("phanloai");
					
					this.sohoadontu = rs.getString("sohoadontu");
					
					this.sohoadonden = rs.getString("sohoadonden");
					
					this.nhanmaildms = rs.getString("nhanmaildms")==null?"0":rs.getString("nhanmaildms");
					
					this.nppId = rs.getString("convsitecode");
					System.out.println("NPPID "+this.nppId );
					
					this.userId="";
					
					this.userTen="";
					
					this.trangthai=rs.getString("trangthai");
					
					if(this.Phanloai.equals("2")) {
						this.loai = rs.getString("loai");
						if(this.loai == null) this.loai = "0";
						if(this.loai.equals("1")) {
							this.loaiId = rs.getString("bm_fk");
						} else if(this.loai.equals("2")) {
							this.loaiId = rs.getString("asm_fk");
						} else if(this.loai.equals("3")) {
							this.loaiId = rs.getString("gsbh_fk");
						}
					}
					CreateLoaiRs();
					
					this.msg="";
				}
				rs.close();
			String	query="select npp_fk from phamvihoatdong where nhanvien_fk='"+this.Id+"'";
			System.out.println("++++++______________"+query);
			rs=db.get(query);
			while(rs.next())
			{
				this.nppIds+=rs.getString("npp_fk")+",";
			}
			if(this.nppIds.length()>0)
			{
				this.nppIds=this.nppIds.substring(0,this.nppIds.length()-1);
			}
			if(rs!=null)
			{
				rs.close();
			}
				System.out.println("[NppId]"+nppIds+"[query]"+query);
				
			if(this.Phanloai.equals("1"))
			{
				query =
					" SELECT a.Ten, a.PK_SEQ FROM DANHMUCQUYEN a inner join PHANQUYEN b on a.PK_SEQ = b.DMQ_fk \n"+
					"  where Nhanvien_fk ='"+this.Id+"' \n";
				rs=db.get(query);
				
				while(rs.next())
				{
					this.quyenId+=rs.getString("PK_SEQ");
				}
				
				if(rs!=null)
				{
					rs.close();
				}
				System.out.println(query);
				
			}	
			
			} catch(Exception e) {
				
				e.printStackTrace();
			}
		}
	}
   
	public ResultSet getquyen() {
		
		return this.quyen;
	}
	public void setquyenchon(ResultSet quyenchon) {
	
		this.quyenchon = quyenchon;
	}

	public ResultSet getquyenchon() {
	
		return this.quyenchon;
	}
	
	public void settrangthai(String trangthai) {
		
		this.trangthai = trangthai;
	}
	
	public String gettrangthai() {
		
		return this.trangthai;
	}
	
	public void setmsg(String msg) 
	{
		this.msg = msg;
	}
	
	public String getmsg() {
		
		return this.msg;
	}
	boolean xoa()
	{
		String sql ="delete from phanquyen where nhanvien_fk ='"+this.Id +"'";
		
		if(!db.update(sql))
			{
			this.msg = sql;
			return false;
			}
		System.out.println(sql);
		sql ="delete from phamvihoatdong where nhanvien_fk ='"+ this.Id+"'";
		
		if(!db.update(sql))
		{
			this.msg = sql;
			return false;
		}
		System.out.println(sql);
		sql ="delete from nhanvien_kenh where nhanvien_fk ='"+ this.Id +"'";
		
		if(!db.update(sql))
		{
			this.msg = sql;
			return false;
		}
		System.out.println(sql);
		sql ="delete from nhanvien_sanpham where nhanvien_fk ='"+ this.Id +"'";
		
		if(!db.update(sql))
		{
			this.msg = sql;
			return false;
		}
		System.out.println(sql);
		
		sql ="delete from nhanvien_kho where nhanvien_fk ='"+ this.Id +"'";
		
		if(!db.update(sql))
		{
			this.msg = sql;
			return false;
		}
		System.out.println(sql);
		
		return true;
	}
	boolean kiemtra()
	{ 
		
		String sql = " select count(*) as num  from nhanvien where pk_seq ='"+ this.Id +"' and pk_seq ='"+ this.userId +"' and pk_seq != '100368' ";
		ResultSet rs = db.get(sql);
		try {
			if(rs.next())
			{
				if(rs.getString("num").equals("0"))
					return false;
			}
			rs.close();
		} catch(Exception e) {
			
		}
		return true;
	}
	
	public boolean save() 
	{
		if(this.Id.length()>0 )
		{
			if(kiemtra())
			{
				this.msg ="Bạn không được cập nhật quyền cho chính mình";
				return false;
			}
		}
		
		if(this.Ten ==""|| this.Ngaysinh =="" ||this.Tendangnhap=="")
		{  
			this.msg ="Bạn phải nhập đầy đủ thông tin";
			return false;
		}
		
		if(KiemTra_TenDangNhap()!=0)
		{
			this.msg = "Tên đăng nhập này đã có người sử dụng,vui lòng đổi lại";
			return false;
		}			
		
		if(this.loai == null) {
			this.loai = "0";
		}
		
		
		if(this.Id.length() >0)
		{	
			String sql;
			Object[] data;
			String bm_fk = null, asm_fk = null, gsbh_fk = null;
			if(this.Phanloai.equals("2"))
			{
				if(loai.equals("1")) {
					bm_fk = loaiId;
				} else if (loai.equals("2")) {
					asm_fk = loaiId;
				} else if (loai.equals("3")) {
					gsbh_fk = loaiId;
				}
				
				sql ="update nhanvien set ten = ?,ngaysinh = ?,dangnhap = ? " ;
						if(!this.matkhau.trim().equals("")){
							
							String vl = Utility.validatePassword(this.matkhau.trim());			
							if(vl.trim().length() > 0)
							{
								this.msg =vl;
								return false;
							}
							
							
							sql=sql+ " , matkhau = pwdencrypt(?) " ;
						}
				sql = sql +	" , email = ?, dienthoai = ?, trangthai = ?, ngaysua =?, "
						+ " nguoisua =?, phanloai= ?, sessionid=?, loai = ?, bm_fk = ?, "
						+ " asm_fk = ?, gsbh_fk = ?, nhanmaildms = ? where pk_seq = ? ";
				
			
				if(!this.matkhau.trim().equals(""))
				{
					data= new Object[]   {   this.Ten ,this.Ngaysinh,this.Tendangnhap,this.matkhau.trim(),
							this.Email,this.Dienthoai,this.trangthai,this.getDateTime(),this.userId,this.Phanloai,this.getDateTime(),loai
							,bm_fk,asm_fk,gsbh_fk,this.nhanmaildms,this.Id};
				}else
				{
					data= new Object[]   {   this.Ten ,this.Ngaysinh,this.Tendangnhap,
							this.Email,this.Dienthoai,this.trangthai,this.getDateTime(),this.userId,this.Phanloai,this.getDateTime(),loai
							,bm_fk,asm_fk,gsbh_fk,this.nhanmaildms,this.Id};
				}
				
//				sql ="update nhanvien set ten = N'"+ this.Ten +"',ngaysinh = '"+ this.Ngaysinh +"',dangnhap = N'"+this.Tendangnhap+"'" ;
//				if(!this.matkhau.trim().equals("")){
//					sql=sql+ " , matkhau = pwdencrypt('" + this.matkhau.trim() + "') " ;
//				}
//				sql = sql +	" , email ='"+ this.Email+"', dienthoai = '"+this.Dienthoai+"', trangthai = '"+this.trangthai+"', ngaysua ='"+ this.getDateTime() +"', "
//				+ " nguoisua ='"+ this.userId+"', phanloai= '"+ this.Phanloai +"', sessionid='"+this.getDateTime()+"', loai = "+loai+", bm_fk = "+bm_fk+", "
//				+ " asm_fk = "+asm_fk+", gsbh_fk = "+gsbh_fk+", nhanmaildms = "+this.nhanmaildms+" where pk_seq ='"+ this.Id +"'";
				
				
				System.out.println(sql);
			}
			else
			{
			
				sql = "update nhanvien set ten = ?,ngaysinh = ?,dangnhap = ?, " ;
				if(!this.matkhau.trim().equals("")){
					String vl = Utility.validatePassword(this.matkhau.trim());			
					if(vl.trim().length() > 0)
					{
					
						this.msg =vl;
						return false;
					}
					
					sql = sql + " matkhau = pwdencrypt(?), " ;
				}
					sql=sql + " email =?, dienthoai = ?, trangthai = ?, ngaysua =?, "
							+ " nguoisua =?, phanloai= ?, convsitecode = ?, sessionid=?, "
							+ " nhanmaildms = ? " 
							+ " where pk_seq =?";
				if(!this.matkhau.trim().equals(""))
				{
					data= new Object[]   {   this.Ten ,this.Ngaysinh,this.Tendangnhap,this.matkhau.trim(),
							this.Email,this.Dienthoai,this.trangthai,this.getDateTime(),this.userId, this.Phanloai,this.nppId,this.getDateTime()
							,this.nhanmaildms,this.Id };
				}else
				{
					data= new Object[]   {   this.Ten ,this.Ngaysinh,this.Tendangnhap,
							this.Email,this.Dienthoai,this.trangthai,this.getDateTime(),this.userId, this.Phanloai,this.nppId,this.getDateTime()
							,this.nhanmaildms,this.Id };
				}
//				sql = "update nhanvien set ten = N'"+ this.Ten +"',ngaysinh = '"+ this.Ngaysinh +"',dangnhap = N'"+this.Tendangnhap+"', " ;
//				if(!this.matkhau.trim().equals("")){
//					sql = sql + " matkhau = pwdencrypt('" + this.matkhau.trim() + "'), " ;
//				}
//					sql=sql + " email ='"+ this.Email+"', dienthoai = '"+this.Dienthoai+"', trangthai = '"+this.trangthai+"', ngaysua ='"+ this.getDateTime() +"', "
//							+ " nguoisua ='"+ this.userId+"', phanloai= '"+ this.Phanloai +"', convsitecode = N'"+ this.nppId +"', sessionid='"+this.getDateTime()+"', "
//							+ " nhanmaildms = "+this.nhanmaildms+" " 
//							+ " where pk_seq ='"+ this.Id +"'";	
				
			
			}
			System.out.println("lenh update:"+ sql);
			try {
				db.getConnection().setAutoCommit(false);
				
				if(!xoa()) 
				{
					return false ;// khong xoa duoc
				}
				else
				{
					if(this.Phanloai.equals("2"))
					{
						if(!createUpdate(db))
						{
							return false;
				
						}
					}
				}	
				
				if(db.updateQueryByPreparedStatement(sql, data)!=1)
				{	geso.dms.center.util.Utility.rollback_throw_exception(db);
					this.msg ="Không thể cập nhật nhân viên";
					return false;
				}
				System.out.println("quyền: "+this.quyenId);
			
				if(this.quyenId.trim().length()>0)
				{
					
					sql = "delete phanquyen where nhanvien_fk='"+this.Id+"'";
					System.out.println(sql);
					if (!db.update(sql))
					{
						this.msg = "không thể xóa phanquyen";
						geso.dms.center.util.Utility.rollback_throw_exception(db);
						return false;
					}
					
					sql="	delete from NhanVien_UngDung  where nhanvien_Fk   = "+this.Id ;
					
					System.out.println(sql);
					
					if (!db.update(sql))
					{
						this.msg = "không thể xóa NhanVien_UngDung";
						geso.dms.center.util.Utility.rollback_throw_exception(db);
						return false;
					}
					
					
					sql ="insert phanquyen values ("+this.Id+","+this.quyenId+")";
					
					System.out.println(sql);
					
					if (!db.update(sql))
					{
						this.msg = "không thể xóa phanquyen";
						geso.dms.center.util.Utility.rollback_throw_exception(db);
						return false;
					}
						
					sql=
						"		insert into NhanVien_UngDung(UngDung_fk,NhanVien_fk)  "+
						"  select distinct nq.UngDung_fk,pq.Nhanvien_fk from NHOMQUYEN nq inner join phanquyen pq on pq.DMQ_fk=nq.DMQ_fk  "+
						"  inner join UNGDUNG ud on ud.PK_SEQ=nq.UngDung_fk  "+
					   " 	where nq.HienThi=1 and ud.TRANGTHAI=1 and pq.Nhanvien_fk ='"+this.Id+"' and nq.DMQ_fk ='"+this.quyenId+"'";
					System.out.println(sql);
					if (!db.update(sql))
					{
						this.msg = "không thể thêm mới NhanVien_UngDung";
						geso.dms.center.util.Utility.rollback_throw_exception(db);
						return false;
					}
					
					if(this.qhId.length()>0)
					{
						sql=
								"  insert into NhanVien_QuanHuyen(QuanHuyen_fK,NhanVien_fk)  "+
								"  select pk_Seq ,'"+this.Id+"' From QuanHuyen where pk_Seq in ("+this.qhId+")";
							System.out.println(sql);
							if (!db.update(sql))
							{
								this.msg = "không thể thêm mới NhanVien_QuanHuyen";
								geso.dms.center.util.Utility.rollback_throw_exception(db);
								return false;
							}
					}
					
					if(this.ttId.length()>0)
					{
						sql=
								"  insert into NhanVien_TinhThanh(TinhThanh_fK,NhanVien_fk)  "+
								"  select pk_Seq ,'"+this.Id+"' From TinhThanh where pk_Seq in ("+this.ttId+")";
							System.out.println(sql);
							if (!db.update(sql))
							{
								this.msg = "không thể thêm mới NhanVien_TinhThanh";
								geso.dms.center.util.Utility.rollback_throw_exception(db);
								return false;
							}
					}
					if(this.khochon != null)
					{ 
						while(khochon.next())
						{
							sql = "insert into nhanvien_kho(nhanvien_fk,kho_fk) values ('"+ this.Id+"','"+ khochon.getString("pk_seq")+"')";
							System.out.println(sql);
							if(!db.update(sql))
							{	geso.dms.center.util.Utility.rollback_throw_exception(db);
								this.msg ="Không thể cập nhật nhân viên";
								return false;
							}
						}
					}
					if(this.nhomskuschon != null)
					{ 
						sql = "delete nhanvien_nhomskus where nhanvien_fk = '" + this.Id + "'";
						db.update(sql);
						while(nhomskuschon.next())
						{
							sql = "insert into nhanvien_nhomskus(nhanvien_fk,nhomskus_fk) values ('"+ this.Id+"','"+ nhomskuschon.getString("pk_seq")+"')";
							System.out.println(sql);
							if(!db.update(sql))
							{	geso.dms.center.util.Utility.rollback_throw_exception(db);
								this.msg ="Không thể cập nhật nhân viên";
								return false;
							}
						}
					}
					
				sql=
				"	update nhanvien set timkiem=  "+
				"			upper(dbo.ftBoDau(isnull(kh.TEN,''))) +   '-' +  "+
				"							upper(dbo.ftBoDau(isnull(kh.DANGNHAP,''))) +   '-' +  "+
				"							upper(dbo.ftBoDau(isnull(kh.DIENTHOAI,''))) +   '-' +  "+
				"							upper(dbo.ftBoDau(isnull(kh.EMAIL,''))) +   '-' +  "+
				"							upper(dbo.ftBoDau(isnull(kh.CONVSITECODE,'')))  "+
				"			from NHANVIEN kh				  "+
				"			where pk_Seq='"+this.Id+"'";
				if(!db.update(sql))
				{	
					geso.dms.center.util.Utility.rollback_throw_exception(db);
					this.msg ="Không thể cập nhật nhân viên";
					return false;
				}
			}
				
				//ghi log doi mat khau, doi- them thong tin nhan vien...
				String matkhau="NULL";
				if(!this.matkhau.trim().equals("")){
					matkhau= this.matkhau.trim();
				}
				sql="insert into LOGDOITHONGTINNV(NGUOIDOI,NHANVIEN_FK,THOIDIEMDOI,MATKHAU,ghichu) "+
				"	values(?,?,dbo.GetLocalDate(DEFAULT),?,?)  ";
	
				data= new Object[]   {  this.userId,this.Id,matkhau,
						"Cập nhật"};
//					"\n	values('"+this.userId+"','"+this.Id+"',dbo.GetLocalDate(DEFAULT),N'"+matkhau+"',N'Tạo mới')";
				System.out.println(sql);
				if(db.updateQueryByPreparedStatement(sql, data)!=1)
				{	
					geso.dms.center.util.Utility.rollback_throw_exception(db);
					this.msg ="Không thể tạo mới nhân viên";
					return false;
				}		
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			} catch(Exception e) 
			{
				db.get("rollback");
				e.printStackTrace();
			}
		}
		else
		{      
			String sql= "";
			String bm_fk = null, asm_fk = null, gsbh_fk = null;
			Object[] data;
			
			String vl = Utility.validatePassword(this.matkhau.trim());			
			if(vl.trim().length() > 0)
			{
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				this.msg =vl;
				return false;
			}
			
			if(this.Phanloai.equals("2"))
			{
				if(loai.equals("1")) {
					bm_fk = loaiId;
				} else if (loai.equals("2")) {
					asm_fk = loaiId;
				} else if (loai.equals("3")) {
					gsbh_fk = loaiId;
				}
				
				
				
				sql = " insert into nhanvien(ten, ngaysinh, dangnhap, matkhau, email, dienthoai, trangthai, ngaytao, ngaysua, nguoitao, nguoisua, phanloai, sessionid, "+
					  "	loai, bm_fk, asm_fk, gsbh_fk, nhanmaildms) " +
					  "	values(?, ?, ?,  pwdencrypt(?), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)  ";

						data= new Object[]   {  this.Ten,this.Ngaysinh,this.Tendangnhap.trim(),this.matkhau.trim(),
								this.Email,this.Dienthoai,this.trangthai,this.getDateTime(),this.getDateTime(),this.userId,this.userId,
								this.Phanloai,"2012-01-01",this.loai,bm_fk,asm_fk,gsbh_fk,this.nhanmaildms};
					  
//					  " values(N'"+ this.Ten +"', '"+ this.Ngaysinh +"', '"+this.Tendangnhap.trim()+"', pwdencrypt('"+ this.matkhau.trim() + "'),' "+ this.Email+"', "+
//					  " '"+this.Dienthoai+"', '"+this.trangthai+"', '"+ this.getDateTime() +"', '"+ this.getDateTime() +"', '"+ this.userId+"', '"+ this.userId+"', "+
//					  " '"+ this.Phanloai +"', '2012-01-01', " + this.loai + ", " + bm_fk + ", " + asm_fk + ", " + gsbh_fk + ", "+this.nhanmaildms+") ";
			}
			else
			{
			   sql = " insert into nhanvien(ten, ngaysinh, dangnhap, matkhau, email, dienthoai, trangthai, ngaytao, ngaysua, nguoitao, nguoisua, phanloai, convsitecode, "+
					 " sessionid, nhanmaildms) " +
					 "	values(?, ?, ?, pwdencrypt(?), ?, ?, ?, ?, ?, ?, ?, ?, ?,  ?, ?)  ";
			   		
			   		data= new Object[]   {   this.Ten,this.Ngaysinh,this.Tendangnhap.trim(),this.matkhau.trim(),
			   				this.Email,this.Dienthoai,this.trangthai, this.getDateTime() ,this.getDateTime(),this.userId,this.userId,
						this.Phanloai, this.nppId,"2012-01-01",this.nhanmaildms};
//					 " values(N'"+ this.Ten +"', '"+ this.Ngaysinh +"', '"+this.Tendangnhap.trim()+"', pwdencrypt('"+ this.matkhau.trim() + "'), '"+ this.Email+"', "+
//					 " '"+this.Dienthoai+"', '"+this.trangthai+"', '"+ this.getDateTime() +"', '"+ this.getDateTime() +"', '"+ this.userId+"', '"+ this.userId+"', "+
//					 " '"+ this.Phanloai +"',N'"+ this.nppId +"','2012-01-01', "+this.nhanmaildms+")";		   
			   
			}
			System.out.println("[Capnhatnhanvien.save] sql = " + sql);
			
			try {
				
				db.getConnection().setAutoCommit(false);
				if(db.updateQueryByPreparedStatement(sql, data)!=1)
				{	geso.dms.center.util.Utility.rollback_throw_exception(db);
					this.msg ="Không thể tạo mới nhân viên";
					return false;
				}
				
//				sql = "select IDENT_CURRENT('nhanvien') as nv";
//				
//				ResultSet rsDh = this.db.get(sql);						
//				rsDh.next();
				this.Id = db.getPk_seq();
//				rsDh.close();
				
				if(this.Phanloai.equals("2"))
				{
						if(!createUpdate(db))
						{  this.Id="";
							return false;
						}
				}
				else
				{
				if(this.quyenId.trim().length()>0){
					
					sql = "delete phanquyen where nhanvien_fk='"+this.Id+"'";
					System.out.println(sql);
					if (!db.update(sql))
					{
						this.msg = "không thể xóa phanquyen";
						geso.dms.center.util.Utility.rollback_throw_exception(db);
						return false;
					}
					
					sql="	delete from NhanVien_UngDung  	where nhanvien_Fk   = "+this.Id ;
					
					System.out.println(sql);
					
					if (!db.update(sql))
					{
						this.msg = "không thể xóa NhanVien_UngDung";
						geso.dms.center.util.Utility.rollback_throw_exception(db);
						return false;
					}
					
					
					sql =" insert phanquyen values ("+this.Id+","+this.quyenId+")";
					System.out.println(sql);
					
					if (!db.update(sql))
					{
						this.msg = "không thể xóa phanquyen";
						geso.dms.center.util.Utility.rollback_throw_exception(db);
						return false;
					}
					
					sql=
						"  insert into NhanVien_UngDung(UngDung_fk,NhanVien_fk)  "+
						"  select distinct nq.UngDung_fk,pq.Nhanvien_fk from NHOMQUYEN nq inner join phanquyen pq on pq.DMQ_fk=nq.DMQ_fk  "+
						"  inner join UNGDUNG ud on ud.PK_SEQ=nq.UngDung_fk  "+
					   " 	where nq.HienThi=1 and ud.TRANGTHAI=1 and pq.Nhanvien_fk ='"+this.Id+"' and nq.DMQ_fk ='"+this.quyenId+"'";
					System.out.println(sql);
					if (!db.update(sql))
					{
						this.msg = "không thể thêm mới NhanVien_UngDung";
						geso.dms.center.util.Utility.rollback_throw_exception(db);
						return false;
					}
					
					if(this.khochon != null)
					{ 
						while(khochon.next())
						{
							sql = "insert into nhanvien_kho(nhanvien_fk,kho_fk) values ('"+ this.Id+"','"+ khochon.getString("pk_seq")+"')";
							System.out.println(sql);
							if(!db.update(sql))
							{	geso.dms.center.util.Utility.rollback_throw_exception(db);
							    this.msg =sql;
								return false;
							}
						}
					}
					
					if(this.nhomskuschon != null)
					{ 
						sql = "delete nhanvien_nhomskus where nhanvien_fk = '" + this.Id + "'";
						db.update(sql);
						while(nhomskuschon.next())
						{
							sql = "insert into nhanvien_nhomskus(nhanvien_fk,nhomskus_fk) values ('"+ this.Id+"','"+ nhomskuschon.getString("pk_seq")+"')";
							System.out.println(sql);
							if(!db.update(sql))
							{	geso.dms.center.util.Utility.rollback_throw_exception(db);
							this.msg ="Không thể tạo mới nhân viên";
								return false;
							}
						}
					}
				}
			}
				sql=
						"	update nhanvien set timkiem=  "+
						"			upper(dbo.ftBoDau(isnull(kh.TEN,''))) +   '-' +  "+
						"							upper(dbo.ftBoDau(isnull(kh.DANGNHAP,''))) +   '-' +  "+
						"							upper(dbo.ftBoDau(isnull(kh.DIENTHOAI,''))) +   '-' +  "+
						"							upper(dbo.ftBoDau(isnull(kh.EMAIL,''))) +   '-' +  "+
						"							upper(dbo.ftBoDau(isnull(kh.CONVSITECODE,'')))  "+
						"			from NHANVIEN kh				  "+
						"			where pk_Seq='"+this.Id+"'";
						if(!db.update(sql))
						{	
							geso.dms.center.util.Utility.rollback_throw_exception(db);
							this.msg ="Không thể tạo mới nhân viên";
							return false;
						}
			
				//ghi log doi mat khau, doi- them thong tin nhan vien...
				String matkhau="NULL";
				if(!this.matkhau.trim().equals("")){
					matkhau= this.matkhau.trim();
				}
				sql="insert into LOGDOITHONGTINNV(NGUOIDOI,NHANVIEN_FK,THOIDIEMDOI,MATKHAU,ghichu) "+
				"	values(?,?,dbo.GetLocalDate(DEFAULT),?,?)  ";
	
				data= new Object[]   {  this.userId,this.Id,matkhau,
						"Tạo mới"};
//					"\n	values('"+this.userId+"','"+this.Id+"',dbo.GetLocalDate(DEFAULT),N'"+matkhau+"',N'Tạo mới')";
				System.out.println(sql);
				if(db.updateQueryByPreparedStatement(sql, data)!=1)
				{	
					geso.dms.center.util.Utility.rollback_throw_exception(db);
					this.msg ="Không thể tạo mới nhân viên";
					return false;
				}		
				
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			} catch(Exception e) 
			{
				geso.dms.center.util.Utility.rollback_throw_exception(this.db);
				this.Id="";
				e.printStackTrace();
			}
		}
		return true;
	}
	
	public boolean update() {
		return false;
	}
	
	
	public void CreateQuyen(String[] chuoi) {
		String st="(";
		if(chuoi!=null)
		{
			for(int i =0;i< chuoi.length;i++)
				st =st + chuoi[i]+",";
			st =st.substring(0,st.length()-1);
			st = st +")";	               
			
		}
		String sql;
			
		if(this.Id.length()>0)
	    	{
		     sql =" select * from danhmucquyen where pk_seq not in (select dmq_fk from phanquyen where nhanvien_fk ='"+ this.Id +"')";
			}
		else
			 sql = "select * from danhmucquyen";
		 if(chuoi !=null)
			sql = "select * from danhmucquyen where pk_seq not in " +st;
		 
		 quyen = db.get(sql);
		   
		   if(this.Id.length()>0)
		   {
		     sql = "select * from danhmucquyen a ,phanquyen b where a.pk_seq = b.dmq_fk and b.nhanvien_fk='"+ this.Id +"'";
		     quyenchon = db.get(sql);
		   }
		   if(chuoi !=null)
			 {
			   sql = "select * from danhmucquyen where pk_seq in " +st;
			   quyenchon = db.get(sql);
			 }
	}

	
	public String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	public void setId(String Id) {
		this.Id = Id;
		
	}
	
	public String getId() {
		
		return this.Id;
	}
	
	public void setkenh(ResultSet kenh) {
		
		this.kenh= kenh;
	}
	
	public ResultSet getkenh() {
		
		return this.kenh;
	}
	
	public void setkenhchon(ResultSet kenhchon) {
		
		this.kenhchon = kenhchon;
	}
	
	public ResultSet getkenhchon() {
		
		return this.kenhchon;
	}
	
	public void CreateKenh(String[] chuoi) {
		
		String st="";
		if(chuoi!=null)
		{ st="(";
			for(int i =0;i< chuoi.length;i++)
				st =st + chuoi[i]+",";
			st =st.substring(0,st.length()-1);
			st = st +")";	               
			
		}
		System.out.println("dieu kien"+ st);
		String sql;
			
		if(this.Id.length()>0)
	    	{
		     sql =" select * from kenhbanhang where pk_seq not in (select kenh_fk from NHANVIEN_KENH where nhanvien_fk ='"+ this.Id +"')";
			}
		else
			 sql = "select * from kenhbanhang";
		 if(chuoi !=null)
			sql = "select * from kenhbanhang where pk_seq not in " +st;
		 
		 kenh = db.get(sql);
		  if(this.Id.length()>0)
		   {
		     sql = "select * from kenhbanhang a ,NHANVIEN_KENH b where a.pk_seq = b.kenh_fk and b.nhanvien_fk='"+ this.Id +"'";
		     System.out.println(sql);
		     kenhchon = db.get(sql);
		   }
		   if(chuoi !=null)
			 {
			   sql = "select * from kenhbanhang where pk_seq in " +st;
			   System.out.println(sql);
			   kenhchon = db.get(sql);
			 }
		   System.out.println("ngoai:"+sql);
	}
	
	public void setnpp(ResultSet npp) {
		
		this.npp = npp;
	}
	
	public ResultSet getnpp() {
		
		return this.npp;
	}
	
	public void setnppchon(ResultSet nppchon) {
		
		this.nppchon = nppchon;
	}
	
	public ResultSet getnppchon() {
		
		return this.nppchon;
	}
	
	public void CreateNpp(String[] chuoi) {

		String st = "";
		if(chuoi!=null)
		{
			for(int i =0;i< chuoi.length;i++)
				st =st + chuoi[i]+",";
			st =st.substring(0,st.length()-1);
			st = st +"";	               
			System.out.println("[DanhSachNppChon]"+st);
		}
		String sql;
		sql = "\n select distinct npp.convsitecode,npp.ma as nppMa, v.ten as Vung,kv.ten as KhuVuc,npp.Ten as NppTen,npp.pk_seq as nppId,case when pv.Nhanvien_fk is null then 0 else 1 end as ispv "+
			"\n from nhaphanphoi npp  " +
			"\n left join phamvihoatdong pv on pv.npp_fk = npp.pk_seq ";
		if(this.Id.length() > 0){
			sql += "\n and pv.nhanvien_fk = "+this.Id+" ";
		}
		
		sql += "\n	left join khuvuc kv on kv.pk_seq=npp.khuvuc_Fk " +
		"\n	left join vung v on v.pk_seq=kv.vung_fk  "+
		"\n	left join nhapp_kbh k on k.npp_fk=npp.pk_seq  "+
		"\n where ( npp.trangthai=1 or  exists  (select 1 from donhang dh where dh.trangthai !=2 and dh.npp_fk = npp.pk_seq ) ) and npp.loainpp<>4 ";
		String v="";
		if(this.VungId.length()>0)
			v = "\n and npp.khuvuc_fk in (select pk_seq from khuvuc where vung_fk in ("+ this.VungId +")) ";
		if(this.KhuvucId.length()>0)
			v = "\n and npp.khuvuc_fk in("+ this.KhuvucId +") ";
		if(this.kenhId.length()>0)
			sql += "\n and k.kbh_fk in("+ this.kenhId +") ";
		if(v.length()>0)
			sql = sql + v;

		if(st.length()>0)
		{
			sql += "\n and npp.pk_seq not in ("+st+")  ";
			sql += "\n union  "+
			"\n select v.ten as Vung,kv.ten as KhuVuc,npp.ma as nppMa,npp.Ten as NppTen,npp.pk_seq as nppId, case when pv.Nhanvien_fk is null then 0 else 1 end as ispv "+
			"\n from nhaphanphoi npp  " +
			"\n left join phamvihoatdong pv on pv.npp_fk = npp.pk_seq and pv.nhanvien_fk = "+this.Id+" "+ 
			"\n	left join khuvuc kv  on kv.pk_seq=npp.khuvuc_Fk " +
			"\n	left join vung v on v.pk_seq=kv.vung_fk  "+
			"\n	left join nhapp_kbh k on k.npp_fk=npp.pk_seq  "+
			"\n where npp.pk_Seq in ("+st+") and npp.loainpp = '1' ";
		}
		sql+="\n order by ispv desc, kv.ten,v.ten,npp.ma ";
		System.out.println("[SqlNpp]"+sql);
		this.npp = db.get(sql);
	}
	
	public void setsanpham(ResultSet sanpham) {
		
		this.sanpham = sanpham;
	}
	
	public ResultSet getsanpham() {
		
		return this.sanpham;		
	}
	
	public void setsanphamchon(ResultSet sanphamchon) 
	{
		
		this.sanphamchon = sanphamchon;
	}
	
	public ResultSet getsanphamchon() {
		
		return this.sanphamchon;
	}
	
	public void CreateSanpham(String[] chuoi) {
		
		String st="(";
		if(chuoi!=null)
		{
			for(int i =0;i< chuoi.length;i++)
				st =st + chuoi[i]+",";
			st =st.substring(0,st.length()-1);
			st = st +")";	               		
		}
		String sql;
		String nh ="";
		if(this.nhanhangId.length()>0)
		{
			nh = nh +" and nhanhang_fk ='"+ this.nhanhangId +"'";
		}
		if(this.chungloaiId.length()>0)
		{
			nh = nh + " and chungloai_fk ='"+ this.chungloaiId +"'";
		}
		if(this.Id.length()>0)
	    {
		    sql =" select * from sanpham where   pk_seq not in (select sanpham_fk from nhanvien_sanpham where nhanvien_fk ='"+ this.Id +"')";
			}
		else
			 sql = "select * from sanpham where 1 = 1  ";
		 if(chuoi !=null)
			sql = "select * from sanpham where  pk_seq not in " +st;
		 if(nh.length()>0)
			 sql =sql + nh;
		 this.sanpham = db.get(sql);
		   
		   if(this.Id.length()>0)
		   {
		     sql = "select * from sanpham a ,nhanvien_sanpham b where  a.pk_seq = b.sanpham_fk and b.nhanvien_fk='"+ this.Id +"'";
		   //  if(nh.length()>0)
			//	 sql =sql + nh;
		     this.sanphamchon = db.get(sql);
		   }
		   if(chuoi !=null)
			 {
			   sql = "select * from sanpham where  pk_seq in " +st;
			 //  if(nh.length()>0)
				//	 sql =sql + nh;
			  this.sanphamchon = db.get(sql);
			 }
	}
	
	boolean createUpdate(dbutils db)
	{
		String sql;
		try {
		if(quyenchon !=null)
		{
			while(quyenchon.next())
			{
				sql ="insert into phanquyen(dmq_fk,nhanvien_fk)values('"+ quyenchon.getString("pk_seq")+"','"+ this.Id +"')";
				if(!db.update(sql))
				{	geso.dms.center.util.Utility.rollback_throw_exception(db);
				    this.msg =sql;
					return false;
				}
				
			}
		}
		else
		{
		//	 this.msg ="Ban phai chon quyen truy cap";
		//	 geso.dms.center.util.Utility.rollback_throw_exception(db);
		//		return false;
		}
		sql="delete from NhanVien_UngDung where NhanVien_fk='"+this.Id+"'" ;
		if (!db.update(sql))
		{
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			this.msg = sql;
			return false;
		}
		
		sql=
		"		insert into NhanVien_UngDung(UngDung_fk,NhanVien_fk)  "+
		"  select distinct nq.UngDung_fk,pq.Nhanvien_fk from NHOMQUYEN nq inner join phanquyen pq on pq.DMQ_fk=nq.DMQ_fk  "+
		"  inner join UNGDUNG ud on ud.PK_SEQ=nq.UngDung_fk  "+
	  " 	where nq.HienThi=1 and ud.TRANGTHAI=1 and pq.Nhanvien_fk ='"+this.Id+"' ";
		if (!db.update(sql))
		{
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			this.msg = sql;
			return false;
		}
	if(this.kenhchon !=null)
	{ while(kenhchon.next())
		{
			sql ="insert into nhanvien_kenh(nhanvien_fk,kenh_fk)values('"+ this.Id +"','"+ this.kenhchon.getString("pk_seq")+"')";
			System.out.println(sql);
			if(!db.update(sql))
			{	geso.dms.center.util.Utility.rollback_throw_exception(db);
			    this.msg =sql;
				return false;
			}
		}
		}
		else
		{
		
		}
	
		if(this.nppId!=null&& this.nppIds.length()>0)
		{
			sql ="insert into phamvihoatdong(nhanvien_fk,npp_fk) select '"+this.Id+"',pk_seq from nhaphanphoi where pk_Seq in ("+this.nppIds+")  ";
			System.out.println(sql);
			if(!db.update(sql))
			{	geso.dms.center.util.Utility.rollback_throw_exception(db);
			    this.msg =sql;
				return false;
			}
		}
		
		if(this.sanphamchon != null)
		{ 
			while(sanphamchon.next())
			{
				sql = "insert into nhanvien_sanpham(nhanvien_fk,sanpham_fk)values('"+ this.Id+"','"+ sanphamchon.getString("pk_seq")+"')";
				System.out.println(sql);
				if(!db.update(sql))
				{	geso.dms.center.util.Utility.rollback_throw_exception(db);
				    this.msg =sql;
					return false;
				}
			}
		}
		else
			{
				//geso.dms.center.util.Utility.rollback_throw_exception(db);
			//	 this.msg ="Ban phai san pham";
				//	return false;
			}
		if(this.khochon != null)
		{ 
			while(khochon.next())
			{
				sql = "insert into nhanvien_kho(nhanvien_fk,kho_fk) values ('"+ this.Id+"','"+ khochon.getString("pk_seq")+"')";
				System.out.println(sql);
				if(!db.update(sql))
				{	geso.dms.center.util.Utility.rollback_throw_exception(db);
				    this.msg =sql;
					return false;
				}
			}
		}
		else
			{
				//geso.dms.center.util.Utility.rollback_throw_exception(db);
			//	 this.msg ="Ban phai san pham";
				//	return false;
			}
		if(this.nhomskuschon != null)
		{ 
			sql = "delete nhanvien_nhomskus where nhanvien_fk = '" + this.Id + "'";
			db.update(sql);
			while(nhomskuschon.next())
			{
				sql = "insert into nhanvien_nhomskus(nhanvien_fk,nhomskus_fk) values ('"+ this.Id+"','"+ nhomskuschon.getString("pk_seq")+"')";
				System.out.println(sql);
				if(!db.update(sql))
				{	geso.dms.center.util.Utility.rollback_throw_exception(db);
				    this.msg =sql;
					return false;
				}
			}
		}
		else
			{
				//geso.dms.center.util.Utility.rollback_throw_exception(db);
			//	 this.msg ="Ban phai san pham";
				//	return false;
			}
		} catch (Exception e) {
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return false;
		}
		return true;
	}
	public void setvungId(String vungId) {
		
		this.vungId = vungId;
	}
	
	public String getvungId() {
		
		return this.vungId;
	}
	
	public void setvung(ResultSet vung) {
		
		this.vung = vung;
	}
	
	public ResultSet getvung() {
		
		return this.vung;
	}
	
	public void setkhuvucId(String khuvucId) {
		
		this.khuvucId = khuvucId;
	}
	
	public String getkhuvucId() {
		
		return this.khuvucId;
	}
	
	public void setkhuvuc(ResultSet khuvuc) {
		
		this.khuvuc = khuvuc;
	}
	
	public ResultSet getkhuvuc() {
		
		return this.khuvuc;
	}
	
	void CreateVung()
	{
		String sql = "select * from vung";
		this.vung = db.get(sql);
		
		if(this.vungId.length()>0)
			sql ="select * from khuvuc where vung_fk ='"+ this.vungId+"'";
		else
			sql ="select * from khuvuc";
		this.khuvuc = db.get(sql);
	}

	public void setnhanhangId(String nhanhangId) {
		
		this.nhanhangId = nhanhangId;
	}

	public String getnhanhangId() {
		
		return this.nhanhangId;
	}

	public void setnhanhang(ResultSet nhanhang) {
		
		this.nhanhang = nhanhang;
	}

	public ResultSet getnhanhang() {
		
		return this.nhanhang;
	}

	public void setchungloaiId(String chungloaiId) {
		
		this.chungloaiId = chungloaiId;
	}

	public String getchungloaiId() {
		
		return this.chungloaiId;
	}

	public void setchungloai(ResultSet chungloai) {
		
		this.chungloai = chungloai;
	}

	public ResultSet getchungloai() {
		
		return this.chungloai;
	}
	
	public void CreateNhanhang()
	{
		String sql ="select * from nhanhang";
		this.nhanhang = db.get(sql);
		//if(this.nhanhangId.length()>0)
			sql ="select * from chungloai ";
			this.chungloai = db.get(sql);
	}
	
	public void setchon(String chon) {
		this.chon = chon;
		
	}
	
	public String getchon() {
		
		return this.chon;
	}
	
	public void setnhaphanphoi(ResultSet nhaphanphoi) {
		
		this.nhaphanphoi = nhaphanphoi;
	}
	
	public ResultSet getnhaphanphoi() {
		
		return this.nhaphanphoi;
	}
	
	public void CreateRS() {
		CreateVung();
		CreateNhanhang();				
		String sql;

		sql = "select * from nhaphanphoi where  TRANGTHAI=1 ";
		String v = "";
		if(this.vungId.length()>0)
			v = " and khuvuc_fk in (select pk_seq from khuvuc where vung_fk ='"+ this.VungId +"')";
		if(this.khuvucId.length()>0)
			v = " and khuvuc_fk ='"+ this.KhuvucId +"'";
		if(v.length()>0)
			sql = sql + v;

		System.out.println("NPPRS: "+sql);
		this.nhaphanphoi = db.get(sql);

		String query = " select PK_SEQ, DienGiai from DANHMUCQUYEN ";

		this.DanhmucquyenRs =  db.get(query);

		CreateLoaiRs();
		sql="select * from kenhbanhang ";
		this.kenhRs=this.db.get(sql);


		sql="select * from TinhThanh ";
		this.ttRs=this.db.get(sql);

		sql="select * from QuanHuyen ";
		this.qhRs=this.db.get(sql);

	}
	
	
	private void CreateLoaiRs() {
		
		String query = "";
		if(this.loai == null) this.loai = "0";
		if(this.loai.equals("1")) 
		{
			query = " SELECT PK_SEQ, TEN FROM BM WHERE TRANGTHAI = 1 ";
		} 
		else if(this.loai.equals("2")) 
		{
			query = " SELECT PK_SEQ, TEN FROM ASM WHERE TRANGTHAI = 1 ";
		} 
		else if(this.loai.equals("3")) 
		{
			query = " SELECT PK_SEQ, TEN FROM GIAMSATBANHANG WHERE TRANGTHAI = 1 ";
		}
		if(query.length() > 0) {
			this.loaiRs = this.db.get(query);
		}
	}
	public void DBClose() {
		
		try{
		if(kenh!=null){
			kenh.close();
		}
		if(kenh!=null){
			kenh.close();
		}
	
		if(npp!=null){
			npp.close();
		}

		if(nppchon!=null){
			nppchon.close();
		}

		if(sanpham!=null){
			sanpham.close();
		}

		if(sanphamchon!=null){
			sanphamchon.close();
		}
	
		

		if(vung!=null){
			vung.close();
		}
	
	

		if(khuvuc!=null){
			khuvuc.close();
		}
		
	
		if(nhanhang!=null){
			nhanhang.close();
		}
		
	
		if(chungloai!=null){
			chungloai.close();
		}
		
		if(nhaphanphoi!=null){
			nhaphanphoi.close();
		}
		
		if(loaiRs != null){
			loaiRs.close();
		}
		
		if(db!=null){
			db.shutDown();
		}
		}catch(Exception er){
			
		}
		finally
		{
			db.shutDown();
		}
		
	}
	
	public String getNppIds()
	{
		return this.nppIds;
	}
	
	public void setNppIds(String nppId)
	{
		this.nppIds=nppId;
	}
	
	public void setLoai(String loai) {
		this.loai = loai;
	}
	
	public String getLoai() {
		return this.loai;
	}
	
	public void setLoaiId(String loaiId) {
		this.loaiId = loaiId;
	}
	
	public String getLoaiId() {
		return this.loaiId;
	}
	
	public void setLoaiRs(ResultSet loaiRs) {
		this.loaiRs = loaiRs;
	}
	
	public ResultSet getLoaiRs() {
		return this.loaiRs;
	}
	
	String kenhId;

	public String getKenhId()
	{
		return kenhId;
	}
	public void setKenhId(String kenhId)
	{
		this.kenhId = kenhId;
	}
	
	
	ResultSet kenhRs;

	public ResultSet getKenhRs()
	{
		return kenhRs;
	}
	public void setKenhRs(ResultSet kenhRs)
	{
		this.kenhRs = kenhRs;
	}
	
	String activeTab;

	public String getActiveTab()
	{
		return activeTab;
	}
	public void setActiveTab(String activeTab)
	{
		this.activeTab = activeTab;
	}
	
	public int KiemTra_TenDangNhap()
	{
		int soDong=0;
		String	query=			
		"	select COUNT(*) as SoDong "+
		"	from NHANVIEN  "+
		"	where DANGNHAP=N'"+this.Tendangnhap+"'   ";
		
		if(this.Id.length() > 0)
			query += " and pk_seq != '" + this.Id + "' ";
		
		System.out.println("[KiemTra]"+query);
		
		ResultSet rs = this.db.get(query);
		try
		{
			while(rs.next())
			{
				soDong=rs.getInt("SoDong");
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
			soDong=-1;
		}
		return soDong;
	}
	
	public String getSohoadonden() 
	{
		return sohoadonden;
	}
	public void setSohoadonden(String sohoadonden) 
	{
		this.sohoadonden = sohoadonden;
	}
	
	public String getSohoadontu()
	{
		return sohoadontu;
	}
	
	public void setSohoadontu(String sohoadontu) 
	{
		this.sohoadontu = sohoadontu;
	}
	
	public void setdmquyenId(String quyenId) {
		
		this.quyenId = quyenId;
	}
	
	public String getdmquyenId() {
		
		return this.quyenId;
	}
	
	public void setDanhmucquyenRs(ResultSet Rsdanhmucquyen) {
		
		this.DanhmucquyenRs = Rsdanhmucquyen;
	}
	
	public ResultSet getDanhmucquyenRs() {
		
		return this.DanhmucquyenRs;
	}
	String qhId,ttId;
	ResultSet qhRs,ttRs;

	public String getQhId()
  {
  	return qhId;
  }
	public void setQhId(String qhId)
  {
  	this.qhId = qhId;
  }
	public String getTtId()
  {
  	return ttId;
  }
	public void setTtId(String ttId)
  {
  	this.ttId = ttId;
  }
	public ResultSet getQhRs()
  {
  	return qhRs;
  }
	public void setQhRs(ResultSet qhRs)
  {
  	this.qhRs = qhRs;
  }
	public ResultSet getTtRs()
  {
  	return ttRs;
  }
	public void setTtRs(ResultSet ttRs)
  {
  	this.ttRs = ttRs;
  }

  public void closeDB()
  {
		
	    try
      {
	    	if(ttRs!=null)    ttRs.close();
	      if(qhRs!=null)	qhRs.close();
	      if(quyen!=null)quyen.close();
	      if(quyenchon!=null)quyenchon.close();
	      if(vung!=null)vung.close();
	      if(khuvuc!=null)khuvuc.close();
	      if(DanhmucquyenRs!=null)DanhmucquyenRs.close();
	      if(nhanhang!=null)nhanhang.close();
	      if(nhaphanphoi!=null)nhaphanphoi.close();
	      if(kho!=null)kho.close();
	      if(khochon!=null)khochon.close();
	      
      } catch (SQLException e)
      {
	      e.printStackTrace();
      }
  }

	public void setKho(ResultSet khors) {
		this.kho=khors;
	}

	public ResultSet getKhoRs() {
		return this.kho;
	}

	public void setKhochon(ResultSet khochonrs) {
		this.khochon=khochonrs;
	}

	public ResultSet getKhochonrs() {
		return this.khochon;
	}
	public void CreateKho(String[] chuoi) {
		
		Utility util = new Utility(); 
		String st="";
		if(chuoi!=null)
		{
			for(int i =0;i< chuoi.length;i++)
				st =st + chuoi[i]+",";
			st =st.substring(0,st.length()-1);
			st = st +"";	               
			System.out.println("[DanhSachKhoChon]"+st);
		}
		String sql="";		
		
		if(this.Id.length()>0)	
			sql =" select * from kho where trangthai ='1' and pk_seq not in (select kho_fk from nhanvien_kho where nhanvien_fk ='"+ this.Id +"')";
		else
			sql = "select * from kho where trangthai ='1' and pk_seq  in (select kho_fk from nhanvien_kho where nhanvien_fk ='"+ this.userId +"')";
		if(chuoi !=null)
			sql = "select * from kho where trangthai ='1' and pk_seq not in "+ util.quyen_kho(this.Id);
		this.kho = db.get(sql);
	   
	   if(this.Id.length()>0)
	   {
	     sql = "select * from kho a ,nhanvien_kho b where a.trangthai ='1' and a.pk_seq = b.kho_fk and b.nhanvien_fk='"+ this.Id +"'";
	     this.khochon = db.get(sql);
	   }
	   if(chuoi !=null)
	   {
		   sql = "select * from kho where trangthai ='1' and pk_seq in ( " +st+" )";
		  this.khochon = db.get(sql);
	   }
	}
	
	public void setNhomskus(ResultSet nhomskusrs) {
		this.nhomskus = nhomskusrs;
	}

	public ResultSet getNhomskus() {
		return this.nhomskus;
	}

	public void setNhomskuschon(ResultSet nhomskuschonrs) {
		this.nhomskuschon = nhomskuschonrs;
	}

	public ResultSet getNhomskuschonrs() {
		return this.nhomskuschon;
	}
	
	public void CreateNhomskus(String[] chuoi) {
		
		Utility util = new Utility(); 
		String st="";
		if(chuoi!=null)
		{
			for(int i =0;i< chuoi.length;i++)
				st =st + chuoi[i]+",";
			st =st.substring(0,st.length()-1);
			st = st +"";	               
			System.out.println("[DanhSachNhomSKUChon]"+st);
		}
		String sql="";		
		
		if(this.Id.length()>0)	
			sql =" select * from nhomskus where pk_seq not in (select nhomskus_fk from nhanvien_nhomskus where nhanvien_fk ='"+ this.Id +"')";
		else
			sql = "select * from nhomskus where pk_seq  in (select nhomskus_fk from nhanvien_nhomskus where nhanvien_fk ='"+ this.userId +"')";
		if(chuoi !=null)
			sql = "select * from nhomskus where pk_seq not in "+ util.quyen_nhomskus(this.Id);
		System.out.println("Lay nhom sku: " + sql);
		this.nhomskus = db.get(sql);
	   
	   if(this.Id.length()>0)
	   {
	     sql = "select * from nhomskus a ,nhanvien_nhomskus b where a.pk_seq = b.nhomskus_fk and b.nhanvien_fk='"+ this.Id +"'";
	     this.nhomskuschon = db.get(sql);
	   }
	   if(chuoi !=null)
	   {
		  sql = "select * from nhomskus where pk_seq in ( " +st+" )";
		  this.nhomskuschon = db.get(sql);
	   }
	   System.out.println("Lay nhom sku đã chọn: " + sql);
	}
	
	@Override
	public String getVungId() {
		return this.VungId;
	}
	@Override
	public void setVungId(String VungId) {
		this.VungId = VungId;
	}
	@Override
	public String getKhuvucId() {
		return this.KhuvucId;
	}
	@Override
	public void setKhuvucId(String KhuvucId) {
		this.KhuvucId = KhuvucId;
	}
	@Override
	public String getNhanmaildms() {
		return this.nhanmaildms;
	}
	@Override
	public void setNhanmaildms(String nhanmaildms) {
		this.nhanmaildms = nhanmaildms;
	}
	
}
