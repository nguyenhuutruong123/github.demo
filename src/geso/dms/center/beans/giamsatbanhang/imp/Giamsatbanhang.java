package geso.dms.center.beans.giamsatbanhang.imp;

import geso.dms.center.beans.giamsatbanhang.IGiamsatbanhang;
import geso.dms.center.db.sql.*;
import geso.dms.center.util.Utility;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.xmlbeans.impl.regex.REUtil;



public class Giamsatbanhang implements IGiamsatbanhang
{
	private static final long serialVersionUID = -9217977546733610214L;

	String userId;
	String querycu;
	String nppIdcu;
	public String getNppIdcu() {
		return nppIdcu;
	}

	public void setNppIdcu(String nppIdcu) {
		this.nppIdcu = nppIdcu;
	}

	public String getQuerycu() {
		return querycu;
	}

	public void setQuerycu(String querycu) {
		this.querycu = querycu;
	}

	String id;
	String ten;
	String diachi;
	String sodienthoai;
	String email;
	String nhacungcap;
	String kenhbanhang;
	String trangthai;
	String ngaytao;
	String nguoitao;
	String ngaysua;
	String nguoisua;
	String cmnd;
	String ngaysinh;
	String quequan;
	String ngaybatdau;
	String ngayketthuc;
	String msg;
	String thuviec;
	/*Thêm vào*/
	String chutaikhoan;
	String sotaikhoan;
	String nganhang;
	String NganHangId,ChiNhanhId;
	
	String hinhanh;
	
	/*########*/
	dbutils db;
	
	ResultSet nhacungcaplist,rsNganHang,rsChiNhanh,trungtamphanphoilist;
	String nccId;
	String TtppId;
	
	ResultSet dvkdlist;
	String dvkdId;
	
	ResultSet kenhbanhanglist;
	String kbhId;
	String vungId;
	ResultSet vung;
	String kvId;
	ResultSet khuvuc;
	String smartid;
	String diaban_fk = "";
	String folderPath = "";
	ArrayList<String> imgList = new ArrayList<String>();
	ResultSet diabanRs;
	
	public Giamsatbanhang(dbutils db) {
		this.db = db;
	}
	
	public Giamsatbanhang(String[] param)
	{
		this.db = new dbutils();
		this.id = param[0];
		this.ten = param[1];
		this.diachi = param[2];
		this.sodienthoai = param[3];
		this.email = param[4];
		this.nhacungcap = param[5];
		this.kenhbanhang = param[6];
		this.trangthai = param[7];
		this.ngaytao = param[8];
		this.nguoitao = param[9];
		this.ngaysua = param[10];
		this.nguoisua = param[11];
		this.nccId = param[12];
		this.kbhId = param[13];
		
		this.thuviec=param[14];
		this.smartid = param[15];
		this.hinhanh = param[16];
		this.ngaybatdau = param[17];
		this.ngayketthuc = param[18];
		this.msg = "";
		this.gsbhTnId="";
		this.querycu="";
		this.nppIds="";
		this.nppIdcu="";
		this.TtppId = "";
		
		this.cothechonTN = "0";
		this.tendangnhap="";
		this.matkhau ="";
	}
	
	public Giamsatbanhang(String id)
	{
		this.db = new dbutils();
		this.id = id;
		this.ten = "";
		this.diachi = "";
		this.sodienthoai = "";
		this.email = "";
		this.nppIdcu="";
		this.nhacungcap = "";
		this.kenhbanhang = "";
		this.trangthai = "0";
		this.ngaytao = "";
		this.nguoitao = "";
		this.querycu="";
		this.ngaysua = "";
		this.nguoisua = "";
		this.nccId = "";
		this.TtppId = "";
		this.dvkdId = "";
		this.kbhId = "";
		this.vungId = "";
		this.kvId = "";
		this.msg = "";
		this.cmnd="";
		this.quequan="";
		this.ngaybatdau="";
		this.ngayketthuc="";
		this.ngaysinh="";
		this.thuviec="0";
		this.sotaikhoan="";
		this.chutaikhoan="";
		this.NganHangId="";
		this.ChiNhanhId="";
		this.hinhanh = "";
		this.gsbhTnId="";
		this.nppIds="";
		this.cothechonTN = "0";
		this.tendangnhap="";
		this.matkhau ="";
		this.smartid = "";
		if(id.length() > 0)
			this.init();
		else
			this.createRS();
	}
	
	public dbutils getDb() {
		return db;
	}
	public void setImgList(ArrayList<String> imgList) {
		this.imgList = imgList;
	}
	public ArrayList<String> getImgList() {
		return imgList;
	}
	public String getFolderPath() {
		return folderPath;
	}
	public void setFolderPath(String folderPath) {
		this.folderPath = folderPath;
	}
	public String getDiaban_fk() {
		return diaban_fk;
	}
	public void setDiaban_fk(String diaban_fk) {
		this.diaban_fk = diaban_fk;
	}
	public ResultSet getDiabanRs() {
		return db.get("select pk_seq,ten from diaban where trangthai = 1");
	}
	public void setDiabanRs(ResultSet diabanRs) {
		this.diabanRs = diabanRs;
	}
	public String getUserId() 
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;
	}
	
	public String getId() 
	{
		return this.id;
	}
	
	public void setId(String id) 
	{
		this.id = id;
	}

	public String getTen() 
	{
		return this.ten;
	}

	public void setTen(String ten) 
	{
		this.ten = ten;
	}
	
	public String getDiachi() 
	{
		return this.diachi;
	}

	public void setDiachi(String diachi) 
	{
		this.diachi = diachi;
	}
	
	public String getSodienthoai() 
	{
		return this.sodienthoai;
	}

	public void setSodienthoai(String sodienthoai) 
	{
		this.sodienthoai = sodienthoai;
	}

	public String getEmail() 
	{
		return this.email;
	}
	
	public void setEmail(String email) 
	{
		this.email = email;
	}

	public String getNhacungcap() 
	{
		return this.nhacungcap;
	}

	public void setNhacungcap(String nhacungcap) 
	{
		this.nhacungcap = nhacungcap;
	}
	
	public String getKenhbanhang() 
	{
		return this.kenhbanhang;
	}

	public void setKenhbanhang(String kenhbanhang) 
	{
		this.kenhbanhang = kenhbanhang;
	}
	
	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}

	
	public String getNgaytao()
	{
		return this.ngaytao;
		
	}

	public void setNgaytao(String ngaytao)
	{
		this.ngaytao = ngaytao;
	}
	
	public String getNgaysua()
	{
		return this.ngaysua;
		
	}

	public void setNgaysua(String ngaysua)
	{
		this.ngaysua = ngaysua;
	}
	
	public String getNguoitao()
	{
		return this.nguoitao;
	}
	
	public void setNguoitao(String nguoitao)
	{
		this.nguoitao = nguoitao;
	}

	public String getNguoisua()
	{
		return this.nguoisua;
	}

	public void setNguoisua(String nguoisua)
	{
		this.nguoisua = nguoisua;
	}
	
	public String getMessage() 
	{
		return this.msg;
	}
	
	public void setMessage(String msg) 
	{
		this.msg = msg;
	}
	
	public void setChuTaiKhoan(String chutaikhoan)
	{
		this.chutaikhoan=chutaikhoan;
	}
	public String getChuTaiKhoan()
	{
		return this.chutaikhoan;
	}
	public void setSoTaiKhoan(String sotaikhoan)
	{
		this.sotaikhoan=sotaikhoan;
	}
	public String getSoTaiKhoan()
	{
		return this.sotaikhoan;
	}
	public void setNganHang(String nganhang)
	{
		this.nganhang=nganhang;
	}
	public String getNganHang()
	{
		return this.nganhang;
	}
	
	public ResultSet getTrungtamphanphoiList()
	{
		return this.trungtamphanphoilist;	
	}
	public void setTrungtamphanphoiList(ResultSet trungtamphanphoilist)
	{
		this.trungtamphanphoilist = trungtamphanphoilist;
	}
	
	public ResultSet getNhacungcapList() 
	{
		return this.nhacungcaplist;
	}

	public void setNhacungcapList(ResultSet nhacungcaplist) 
	{
		this.nhacungcaplist = nhacungcaplist;
	}
	
	public ResultSet getDvkdList() 
	{
		return this.dvkdlist;
	}

	public void setDvkdList(ResultSet dvkdlist) 
	{
		this.dvkdlist = dvkdlist;
	}
	
	public ResultSet getKenhbanhangList() 
	{
		return this.kenhbanhanglist;
	}

	public void setKenhbanhangList(ResultSet kenhbanhanglist) 
	{
		this.kenhbanhanglist = kenhbanhanglist;
	}
	
	public String getTtppId()
	{
		return this.TtppId;
	}
	
	public void setTtppId(String TtppId)
	{
		this.TtppId = TtppId;
	}
	
	public String getNccId()
	{
		return this.nccId;
	}
	
	public void setNccId(String nccId)
	{
		this.nccId = nccId;
	}
	
	public String getDvkdId()
	{
		return this.dvkdId;
	}
	
	public void setDvkdId(String dvkdId)
	{
		this.dvkdId = dvkdId;
	}

	public String getKbhId() 
	{
		return this.kbhId;
	}

	public void setKbhId(String kbhId) 
	{
		this.kbhId = kbhId;
	}

	public String getVungId() 
	{
		return this.vungId;
	}

	public void setVungId(String vungId) 
	{
		this.vungId = vungId;
	}
	
	public String getNgaybatdau() 
	{
		return this.ngaybatdau;
	}

	public void setNgaybatdau(String ngaybatdau) 
	{
		this.ngaybatdau = ngaybatdau;
	}
	
	public String getNgayketthuc() 
	{
		return this.ngayketthuc;
	}

	public void setNgayketthuc(String ngayketthuc) 
	{
		this.ngayketthuc = ngayketthuc;
	}
	
	public String getKvId() 
	{
		return this.kvId;
	}

	public void setKvId(String kvId) 
	{
		this.kvId = kvId;
	}
	
	public ResultSet createNccRS(){  			
		
		ResultSet nccRS = db.get("select pk_seq as nccId, tenviettat as nccTen from nhacungcap where trangthai='1' order by ten");

		return nccRS;
	}
	public ResultSet createTtppRS()
	{
		ResultSet ttppRS = db.getScrol("select a.PK_SEQ,a.MA,a.TEN from TRUNGTAMPHANPHOI a where TRANGTHAI=1");
		return ttppRS;
	}
	
	public ResultSet createDvkdRS(){  			
		ResultSet dvkdRS = null;
		if(this.nccId == null)
			this.nccId = "";
		if (this.nccId.length() > 0){
			dvkdRS = db.get("select a.pk_seq as dvkdId, a.donvikinhdoanh as dvkdTen from donvikinhdoanh a, nhacungcap_dvkd b where a.trangthai='1' and a.pk_seq = b.dvkd_fk and b.ncc_fk = '" + this.nccId + "' order by donvikinhdoanh");
		}else{
			dvkdRS = db.get("select pk_seq as dvkdId, donvikinhdoanh as dvkdTen from donvikinhdoanh where trangthai='1' order by donvikinhdoanh");
		}
		return dvkdRS;
	}

	public ResultSet createKbhRS(){  			
		
		ResultSet kbhRS = db.get("select pk_seq as kbhId, diengiai as kbhTen from kenhbanhang where trangthai='1' order by diengiai");

		return kbhRS;
	}
	
	public ResultSet createVungRS(){  			
		
		ResultSet vungRS = db.get("select pk_seq as vungId, ten as vung from vung where trangthai='1' order by ten");

		return vungRS;
	}

	public ResultSet createKhuvucRS(){  			
		ResultSet kvRS;
		
		if(this.vungId.length() > 0)
		{
			kvRS = db.get("select pk_seq as kvId, ten as  kvTen from khuvuc where trangthai='1' and vung_fk in("+ this.vungId + ") order by ten");
		}else{
			kvRS = db.get("select pk_seq as kvId, ten as kvTen from khuvuc where trangthai='1' order by ten");
		}
		return kvRS;
	}
	
	public ResultSet createKVByGSBHRS(){
		ResultSet kvRS=null;
		
		if(this.id.length() > 0)
		{
			kvRS = db.get("select * from GSBH_khuvuc where gsbh_fk='"+this.id+"'");
		}
		return kvRS;
	}
	
	public Hashtable<Integer, String> getKvIds(){
		
		Hashtable<Integer, String> selected = new Hashtable<Integer, String>();
		ResultSet kv = createKVByGSBHRS();
		int m = 0;
		if(kv != null)
		try {while (kv.next()) 
		{
				selected.put(m++, kv.getString("khuvuc_fk"));
		}} catch(Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return selected;
	}
	

	public void createRS() 
	{		
		this.nhacungcaplist = this.createNccRS();
		this.dvkdlist = this.createDvkdRS();
		this.kenhbanhanglist = this.createKbhRS();
		this.trungtamphanphoilist = this.createTtppRS();

		String query="select * from GiamSatBanHang where trangthai=1  ";
		if(this.id.length()>0)
			query += " and pk_seq !='"+this.id+"'";

		this.gsbhTnRs = this.db.get(query);

		if(this.gsbhTnId.length()>0)
		{
			query = "\n SELECT v.ten as vTen,kv.Ten as kvTen ,b.pk_Seq as nppId,b.ma as nppMA,b.Ten as nppTen,0 as PhanTram " +
				"\n FROM NHAPP_GIAMSATBH A INNER JOIN NHAPHANPHOI B ON B.PK_SEQ=A.NPP_FK  "+
				"\n left join khuvuc kv on kv.pk_Seq=b.khuvuc_Fk   "+
				"\n left join vung v on v.pk_Seq=kv.vung_fk  "+
				"\n WHERE A.GSBH_FK='"+this.gsbhTnId+"' " ;	

			if(this.id.length()>0)
				query += "\n AND NOT EXISTS  "+
					"\n (  "+
					"\n 	SELECT * FROM GiamSatBanHang_ChiTieu  "+
					"\n	WHERE GSBHTN_FK='"+this.gsbhTnId+"' AND GSBH_FK="+this.id+" "+
					"\n ) ";

			if(this.id.length()>0)
				query += "\n union " +
					"\n SELECT v.ten as vTen,kv.Ten as kvTen ,npp.pk_Seq as nppId,npp.ma as nppMA, npp.Ten as nppTen,a.PhanTram  "+
					"\n	FROM GiamSatBanHang_ChiTieu a   "+
					"\n	inner join nhaphanphoi npp on npp.pk_seq=a.npp_fk "+
					"\n	left join khuvuc kv on kv.pk_Seq=npp.khuvuc_fk  "+
					"\n	left join vung v on v.pk_Seq=kv.vung_fk "+ 
					"\n	where a.gsbhtn_fk='"+this.gsbhTnId+"'  and a.gsbh_fk='"+this.id+"' ";

			System.out.println("[GSBH_TN]"+query);			
			this.nppTnQl = this.db.get(query);
		}

		/*
		 * Nhà phân phối đã quản lý trong lịch sử
		 */
		if(this.id.length()>0)
		{
			query =	"\n SELECT v.ten as vTen,kv.Ten as kvTen ,b.pk_Seq as nppId,b.ma as nppMA,b.Ten as nppTen, " +
			"\n a.NgayBatDau,a.NgayKetThuc "+
			"\n FROM nhapp_giamsatbh A INNER JOIN NHAPHANPHOI B ON B.PK_SEQ=A.NPP_FK  "+
			"\n   left join khuvuc kv on kv.pk_Seq=b.khuvuc_Fk "+
			"\n		left join vung v on v.pk_Seq=kv.vung_fk "+
			"\n WHERE A.GSBH_FK='"+this.id+"' ";
			this.nppQuanLyRs = this.db.get(query);
		}

		/*
		 * Nhà phân phối muốn thay đổi giám sát quản lý
		 */
		query = "\n	SELECT v.ten as vTen,kv.Ten as kvTen ,A.pk_Seq as nppId,A.MANPP as nppMA,A.Ten as nppTen ";
		if(this.id.length() >0){
			query += "\n ,isnull(b.NgayBatDau, '') as ngaybatdau, isnull(b.NgayKetThuc, '') as ngayketthuc  ";
		}else{
		query += "\n ,'' as ngaybatdau, '' as ngayketthuc  ";
		}
		query += "\n	FROM NHAPHANPHOI A  " ;
		if(this.id.length() >0){
			query +=	"\n left join nhapp_giamsatbh b on a.pk_seq = b.npp_fk " +
			"\n and b.gsbh_fk = '"+this.id+"'";
		}
		query += "\n	left join khuvuc kv on kv.pk_Seq=A.khuvuc_Fk   		" +
		"\n left join vung v on v.pk_Seq=kv.vung_fk  WHERE 1=1  ";   
		if(this.kvId.length()>0)
		{
			query +="\n AND a.KHUVUC_FK IN ("+this.kvId+") ";
		}
		if(this.vungId.length()>0)
		{
			query +="\n AND a.KHUVUC_FK IN (SELECT pk_seq FROM KHUVUC WHERE VUNG_FK IN ("+this.vungId+")  )";
		}
		if (diaban_fk != null && diaban_fk.length() > 0) {
			query += "\n and a.diaban_fk = "+diaban_fk;
		}
	   if(this.nppIdcu.length()>0){
		   query += " and a.pk_seq not in("+this.nppIdcu+")";
	   }
		query += this.querycu;
		System.out.println("NPPRS: "+query);
		this.nppRs = this.db.get(query);
		
		
		if(this.id != null && this.id.trim().length() > 0 )
		{
			query = "\n select l.trangthai,npp.MANPP , npp.TEN NPP, convert ( varchar(19),l.thoidiem,121) ThoiDiem  " + 
					"\n from GiamSatBanHang_LOG l " + 
					"\n left join NHAPHANPHOI npp on npp.PK_SEQ = l.npp_fk " +  
					"\n where l.PK_SEQ = " + this.id + 
					"\n order by l.thoidiem desc ";
			this.logRs = db.get(query);
		}
	}
	
	public boolean CreateGsbh( HttpServletRequest request ) 
	{		
		try
		{
			this.db.getConnection().setAutoCommit(false);	
			this.ngaytao = getDateTime();
			this.nguoitao = this.userId;
			List<Object> data = new ArrayList<Object>();	
			String sql = "select smartid from giamsatbanhang where smartid = '"+this.smartid+"'";
			ResultSet ktrs = db.get(sql);
			if(ktrs.next())
			{
				this.db.getConnection().rollback();
				this.msg = "Trùng mã GSBH, vui lòng sửa lại mã GSBH!";
				return false;
			}
			ktrs.close();
			
			
			if(this.kbhId.length() <=0)
			{
				this.db.getConnection().rollback();
				this.msg = "Vui lòng chọn kênh bán hàng!";
				return false; 
			}
			if(this.asmId.length() <=0)
			{
				this.db.getConnection().rollback();
				this.msg = "Vui lòng chọn ASM trực thuộc!";
				return false; 
			}
			
			if(this.smartid == null || this.smartid.trim().length() <=0)
			{
				this.db.getConnection().rollback();
				this.msg = "Vui lòng nhập mã GSBH!";
				return false;
			}
			this.smartid = this.smartid.trim();
			this.tendangnhap = this.smartid;
			
			String query = "insert into giamsatbanhang(asm_fk,ten,tinhtrang,cmnd,ngaysinh,quequan,dienthoai,email,trangthai,ngaytao,ngaysua,nguoitao,nguoisua,ncc_fk,kbh_fk,diachi,dvkd_fk,taikhoan,chutaikhoan,NganHang,ChiNhanh,hinhanh,TenDangNhap,MatKhau,SmartId";
			if(this.TtppId.length()>0)
			{
				query += ",TTPP_FK";
			}
			query += "\n ) ";

			query += "\n select ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,PWDENCRYPT(?),?";
			data.clear();
			data.add(this.asmId);
			data.add(this.ten);
			data.add(this.thuviec);
			data.add(this.cmnd);
			data.add(this.ngaysinh);
			data.add(this.quequan);
			data.add(this.sodienthoai);
			data.add(this.email);
			data.add(this.trangthai);
			data.add(this.ngaytao);
			data.add(this.ngaytao);
			data.add(this.nguoitao);
			data.add(this.nguoitao);
			data.add(this.nccId);
			data.add(this.kbhId.length() <= 0 ?null:this.kbhId);
			data.add(this.diachi);
			data.add(this.dvkdId);
			data.add(this.sotaikhoan);
			data.add(this.chutaikhoan);
			data.add(this.NganHangId);
			data.add(this.ChiNhanhId);
			data.add(this.hinhanh);
			data.add(this.tendangnhap);
			data.add(this.matkhau);
			data.add(this.smartid);
			if(this.TtppId.length()>0)
			{
				query += ",?";
				data.add(this.TtppId);
			}
			System.out.println("Insert query: ");
			dbutils.viewQuery(query, data);
			if( this.db.updateQueryByPreparedStatement(query, data)!=1 )
			{
				this.msg = "Lỗi tạo mới Giám sát bán hàng 1";
				this.db.getConnection().rollback();
				return false;
			}

			this.id = db.getPk_seq();
			
			//THUC HIEN THEM VAO BANG GIAMSATBANHANG_KHUVUC
			if(this.kvId != null)
				if(this.kvId.length()>0)
				{
					query =" insert into gsbh_khuvuc(gsbh_fk, khuvuc_fk) select "+this.id+",pk_seq from KhuVuc where pk_Seq in ("+this.kvId+") ";
					if(!db.update(query))
					{
						this.msg = "Lỗi tạo mới Giám sát bán hàng 2";
						this.db.getConnection().rollback();
						return false;
					}
				}

			if(this.nppIds != null && this.nppIds.length( ) > 0)
			{
				query = "delete from NHAPP_GIAMSATBH WHERE gsbh_fk IN ("+this.id+")";
				if(!db.update(query))
				{
					this.msg = "Lỗi tạo mới Giám sát bán hàng 3";
					this.db.getConnection().rollback();
					return false;
				}

				query = "delete from DDKD_GSBH WHERE gsbh_fk = "+this.id+" ";
				if(!db.update(query))
				{
					this.msg = "Lỗi tạo mới Giám sát bán hàng 4";
					this.db.getConnection().rollback();
					return false;
				}

				String[] nppId = request.getParameterValues("nppId");
				if(nppId != null)
				{
					int n=0;
					int i= 0;
					int size=nppId.length;
					while(n < size)
					{
						String ngaybatdau=request.getParameter("ngaybatdau"+i)==null?"":request.getParameter("ngaybatdau"+i).trim();
						String ngayketthuc=request.getParameter("ngayketthuc"+i)==null?"":request.getParameter("ngayketthuc"+i).trim();
						System.out.println("_____ngay ket thuc ______" + ngayketthuc);
						if(ngayketthuc.trim().length() > 0 ){
							System.out.println("____+vao k?_____");
							query="insert into nhapp_giamsatbh(npp_fk, gsbh_fk, ngaybatdau, ngayketthuc) " +
							"\n	select ?,?,?,?";
							List<Object> data1 = new ArrayList<Object>();	
							data1.add(nppId[n]);
							data1.add(this.id);
							data1.add(ngaybatdau);
							data1.add(ngayketthuc);
							if( this.db.updateQueryByPreparedStatement(query, data1) < 0)
							{
								this.msg = "Lỗi tạo mới Giám sát bán hàng 5";
								this.db.getConnection().rollback();
								return false;
							}
						}
						n++;
						i++;
					}
				}

				query =	"\n insert into DDKD_GSBH(DDKD_FK,GSBH_FK) "+
				"\n SELECT pk_seq,"+this.id+" " +
				"\n FROM DAIDIENKINHDOANH WHERE NPP_FK IN ("+this.nppIds+")  " +
				"\n and pk_seq not in (select ddkd_fk from ddkd_gsbh)";
				if(!db.update(query))
				{
					this.msg = "Lỗi tạo mới Giám sát bán hàng 6";
					this.db.getConnection().rollback();
					return false;
				}			
			}
			
			if(this.gsbhTnId != null && this.gsbhTnId.length() > 0)
			{
				String [] nppGsTnQl = request.getParameterValues("nppGsTnQl");
				String [] phantramchitieu = request.getParameterValues("phantramchitieu");
				for(int i=0;nppGsTnQl!=null && i<nppGsTnQl.length;i++)
				{
					String nppId = nppGsTnQl[i];
					float phantram =Float.parseFloat(phantramchitieu[i].length()<=0?"0":phantramchitieu[i]);
					query = "insert into GiamSatBanHang_ChiTieu(GSBH_FK,GSBHTN_FK,PhanTram,NPP_FK) " +
					"\n	select ?,?,?,?";
					data.add(this.id);
					data.add(this.gsbhTnId);
					data.add(phantram);
					data.add(nppId);
					if( this.db.updateQueryByPreparedStatement(query, data)!=1 )
					{
						this.msg = "Lỗi tạo mới Giám sát bán hàng 7";
						this.db.getConnection().rollback();
						return false;
					}
				}

				String thang =this.getDateTime().substring(5,7);
				String nam = this.getDateTime().substring(0,4) ;
				query = "\n	INSERT INTO CHITIEUSEC_GSBH (chitieusec_fk,gsbh_fk,dophu,donhang,chitieu,sku,sanluongtrendh ) "+   
				"\n	SELECT  CTSEC.PK_SEQ,E.GSBH_FK, "+
				"\n	SUM(ISNULL(DOPHU,0)*E.PhanTram/100), "+
				"\n	SUM(ISNULL(A.SODONHANG,0)*E.PhanTram/100), "+
				"\n	SUM(ISNULL(A.CHITIEU,0)*E.PhanTram/100), "+
				"\n	SUM(ISNULL(A.SKU,0)*E.PhanTram/100), "+
				"\n	SUM(ISNULL(A.sanluongtrendh,0)*E.PhanTram/100) "+
				"\n	FROM  "+
				"\n	CHITIEUNPP_DDKD A INNER JOIN CHITIEUNPP CT ON CT.PK_SEQ=CHITIEUNPP_FK "+  
				"\n	INNER JOIN CHITIEU_SEC CTSEC ON CTSEC.DVKD_FK=CT.DVKD_FK  "+
				"\n	AND CTSEC.KENH_FK=CT.KENH_FK AND CT.THANG=CTSEC.THANG AND CTSEC.NAM=CT.NAM "+ 
				"\n	INNER JOIN GiamSatBanHang_ChiTieu E ON E.NPP_FK=CT.NHAPP_FK "+
				"\n	WHERE  ct.THANG='"+thang+"' AND CT.NAM='"+nam+"' AND E.GSBH_FK="+this.id+" "+   
				"\n	GROUP BY E.GSBH_FK, CTSEC.PK_SEQ  " ;
				System.out.println("[SQL]"+query);
				if(!db.update(query))
				{
					this.msg = "Lỗi tạo mới Giám sát bán hàng 9";
					this.db.getConnection().rollback();
					return false;
				}
				
				query = "\n	insert into CHITIEUSEC_GSBH_NHOMSP(CHITIEUSEC_FK,GSBH_FK,NHOMSANPHAM_FK,CHITIEU)  "+
				"\n	SELECT  CTSEC.PK_SEQ,e.GSBH_FK,A.NHOMSANPHAM_FK,sum(isnull(a.chitieu,0)*ISNULL(e.PhanTram,0)/100) "+
				"\n	FROM CHITIEUNPP_DDKD_NHOMSP A INNER JOIN   "+
				"\n	CHITIEUNPP CT ON CT.PK_SEQ=CHITIEUNPP_FK   "+
				"\n	INNER JOIN CHITIEU_SEC CTSEC ON CTSEC.DVKD_FK=CT.DVKD_FK   "+
				"\n	AND CTSEC.KENH_FK=CT.KENH_FK AND CT.THANG=CTSEC.THANG AND CTSEC.NAM=CT.NAM "+  
				"\n	INNER JOIN GiamSatBanHang_ChiTieu E ON E.NPP_FK=CT.NHAPP_FK  "+
				"\n	WHERE ct.THANG='"+thang+"' AND CT.NAM='"+nam +"' AND E.GSBH_FK="+this.id+" "+  
				"\n	GROUP BY CTSEC.PK_SEQ,A.NHOMSANPHAM_FK,e.GSBH_FK  " ; 
				System.out.println("[SQL]"+query);
				if(!db.update(query))
				{
					this.msg = "Lỗi tạo mới Giám sát bán hàng 10";
					this.db.getConnection().rollback();
					return false;
				}

				query = "\n insert into CHITIEU_GSBH_NHOMSP(CHITIEU_FK,GSBH_FK,NHOMSANPHAM_FK,CHITIEU) "+
				"\n select ct.PK_SEQ, c.gsbh_fk,nhomsp_fk , sum(isnull(b.chitieu,0)*ISNULL(c.PhanTram,0)/100 ) as chitieu "+  
				"\n from chitieu ct inner join  "+
				"\n CHITIEU_NHAPP_NHOMSP  b on ct.pk_seq=b.chitieu_fk  "+  
				"\n inner join GiamSatBanHang_ChiTieu c on c.NPP_FK=b.NPP_FK "+
				"\n WHERE ct.THANG='"+thang+"' and ct.NAM='"+nam+"' and c.GSBH_FK="+this.id+" "+
				"\n group by ct.PK_SEQ,c.gsbh_fk,nhomsp_fk " ;
				if(!db.update(query))
				{
					this.msg = "Lỗi tạo mới Giám sát bán hàng 11";
					this.db.getConnection().rollback();
					return false;
				}

				query = " update giamsatbanhang set GSBHTN_FK = ? WHERE PK_sEQ ="+this.id+" "; 
				data.add(this.gsbhTnId);
				if( this.db.updateQueryByPreparedStatement(query, data) != 1)
				{
					this.msg = "Lỗi tạo mới Giám sát bán hàng 12";
					this.db.getConnection().rollback();
					return false;
				}

				query = "\n insert into nhapp_giamsatbh(npp_fk, gsbh_fk, ngaybatdau, ngayketthuc) "+ 
				"\n	select a.NPP_FK,'"+this.id+"','"+getDateTime()+"','2100-01-01' "+
				"\n	from NHAPP_GIAMSATBH a " +
				"\n where npp_fk in (select npp_fk from GiamSatBanHang_ChiTieu b  " +
				"\n where b.gsbh_fk='"+this.id+"' and b.PhanTram>0  ) ";
				System.out.println("[SQL]"+query);
				if(!db.update(query))
				{
					this.msg = "Lỗi tạo mới Giám sát bán hàng 13";
					this.db.getConnection().rollback();
					return false;
				}

				query = "\n insert into DDKD_GSBH(DDKD_FK,GSBH_FK) "+
				"\n select PK_SEQ,'"+this.id+"' "+ 
				"\n from DAIDIENKINHDOANH "+
				"\n where NPP_FK in "+
				"\n ( "+
				"\n		select NPP_FK from GiamSatBanHang_ChiTieu "+
				"\n		where PhanTram>0 and gsbh_fk='"+this.id+"' "+
				"\n ) ";
				System.out.println("[SQL]"+query);
				if(!db.update(query))
				{
					this.msg = "Lỗi tạo mới Giám sát bán hàng 14";
					this.db.getConnection().rollback();
					return false;
				}

				query = "\n	delete from NHAPP_GIAMSATBH "+
				"\n	where NPP_FK in  "+
				"\n	( "+
				"\n		select NPP_FK from GiamSatBanHang_ChiTieu a "+
				"\n		where PhanTram>0 and gsbh_fk='"+this.id+"' "+
				"\n	) and gsbh_fk!='"+this.id+"' ";
				System.out.println("[SQL]"+query);
				if(!db.update(query))
				{
					this.msg = "Lỗi tạo mới Giám sát bán hàng 15";
					this.db.getConnection().rollback();
					return false;
				}

				query = "\n delete a from DDKD_GSBH a inner join daidienkinhdoanh b on b.pk_Seq=a.ddkd_fk "+
				"\n	where b.NPP_FK in  	( select NPP_FK from GiamSatBanHang_ChiTieu a 	 "+
				"\n	where PhanTram>0 and gsbh_fk='"+this.id+"' 	) and gsbh_fk!='"+this.id+"'  ";
				System.out.println("[SQL]"+query);
				if(!db.update(query))
				{
					this.msg = "Lỗi tạo mới Giám sát bán hàng 16";
					this.db.getConnection().rollback();
					return false;
				}
			}

			query = "\n INSERT INTO LichSu_GSBH_NPP(NPP_FK,GSBH_FK,NGAYBATDAU,NGAYKETTHUC) "+
			"\n	SELECT NPP_FK,GSBH_FK,NGAYBATDAU,NGAYKETTHUC FROM NHAPP_GIAMSATBH A  "+
			"\n	WHERE NOT EXISTS  "+
			"\n	(  "+
			"\n		SELECT * FROM LichSu_GSBH_NPP B  "+
			"\n		WHERE B.NPP_FK=A.NPP_FK AND A.GSBH_FK=B.GSBH_FK "+
			"\n	) AND A.GSBH_FK="+this.id+" " ;
			System.out.println("[SQL]"+query);

			if(!db.update(query))
			{
				this.msg = "Lỗi tạo mới Giám sát bán hàng 17";
				this.db.getConnection().rollback();
				return false;
			}	

			if(KiemTra_TenDangNhap()!=0)
			{
				this.msg = "Tên đăng nhập này đã có người sử dụng, vui lòng đổi lại!";
				geso.dms.center.util.Utility.rollback_throw_exception(this.db);
				return false;
			}
			String matkhau_macdinh = "123456";// mat khau mac dinh
			query= "\n INSERT INTO NHANVIEN (TEN,NGAYSINH,DANGNHAP,MATKHAU,EMAIL,DIENTHOAI,TRANGTHAI,NGAYTAO," +
			"\n NGAYSUA,NGUOITAO,NGUOISUA,PHANLOAI,ISLOGIN,SESSIONID,GSBH_FK,LOAI) "+
			"\n	select ?,?,?,pwdencrypt(?),?,?,1,?,?,?,?,2,0,?,?,3";
			data.clear();
			data.add(this.getTen());
			data.add(this.ngaysinh);
			data.add(this.tendangnhap);
			data.add(matkhau_macdinh); 
			data.add(this.getEmail());
			data.add(this.sodienthoai);
			data.add(this.getDateTime());
			data.add(this.getDateTime());
			data.add(this.userId);
			data.add(this.userId);
			data.add(this.getDateTime());
			data.add(this.id);
			if( this.db.updateQueryByPreparedStatement(query, data) !=1)
			{
				this.msg = "Lỗi tạo mới Giám sát bán hàng 18";
				this.db.getConnection().rollback();
				return false;
			}

			query = "\n insert into NHANVIEN_KENH(Nhanvien_fk,Kenh_fk) " +
				"\n select nv.pk_seq,gs.kbh_fk  from nhanvien nv inner join giamsatbanhang gs on gs.pk_Seq=nv.gsbh_fk where gs.pk_Seq='"+this.id+"' ";
			if(!this.db.update(query))
			{
				this.msg = "Lỗi tạo mới Giám sát bán hàng 19";
				this.db.getConnection().rollback();
				return false; 
			}

			query = "\n insert into PHAMVIHOATDONG(Npp_fk,Nhanvien_fk) "+
				"\n select b.NPP_FK,a.PK_SEQ "+
				"\n from  NHANVIEN a inner join NHAPP_GIAMSATBH  b on b.GSBH_FK=a.GSBH_FK " +
				"\n where a.gsbh_fk="+this.id+"  ";
			if(!this.db.update(query))
			{
				this.msg = "Lỗi tạo mới Giám sát bán hàng 20";
				this.db.getConnection().rollback();
				return false; 
			}
			
			query = "\n insert into NHANVIEN_SANPHAM(Sanpham_fk,Nhanvien_fk) "+
				"\n select sp.PK_SEQ,nv.PK_SEQ "+
				"\n from SANPHAM sp,NHANVIEN nv "+
				"\n where nv.GSBH_FK='"+this.id+"' ";
			if(!this.db.update(query))
			{
				this.msg = "Lỗi tạo mới Giám sát bán hàng 21";
				this.db.getConnection().rollback();
				return false; 
			}
			
			query = " UPDATE GIAMSATBANHANG SET TIMKIEM =upper(dbo.ftBoDau(ten))+' '+UPPER(dbo.ftBoDau(diachi))" +
			"\n +' '+UPPER(dbo.ftBoDau(ISNULL(CMND,'')))+' '+UPPER(dbo.ftBoDau(isnull(Email,''))) " +
			"\n +' '+UPPER(dbo.ftBoDau(isnull(DIENTHOAI,''))) " +
			"\n where pk_seq='"+this.id+"'";
			if(!db.update(query))
			{
				this.msg = "Lỗi tạo mới Giám sát bán hàng 22";
				this.db.getConnection().rollback();
				return false;
			}
			
			query = "\n	insert GiamSatBanHang_LOG(PK_SEQ,trangthai,npp_fk) " +
			"\n			select a.pk_seq,a.trangthai, b.npp_fk " +
			"\n			from GiamSatBanHang a " +
			"\n			left join NHAPP_GIAMSATBH b on a.PK_SEQ = b.GSBH_FK" +
			"\n	  		where a.pk_Seq = "+ this.id;
			if(!(this.db.update(query)))
			{
				this.msg="Không thể ghi nhận log chuyển tiền nhiệm"+ query;
				geso.dms.center.util.Utility.rollback_throw_exception(this.db);
				return false;
			}
	
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		}catch (Exception e) 
		{
			this.msg = "Lỗi ngoại lệ khi tạo mới GSBH, vui lòng liên hệ Admin để xử lý!";			
			geso.dms.center.util.Utility.rollback_throw_exception(this.db);
			return false;
		}
		return true;
	}
	
	public int KiemTra_TenDangNhap()
	{
		int soDong=0;
		String	query=			
		"\n	select COUNT(*) as SoDong "+
		"\n	from NHANVIEN  "+
		"\n	where DANGNHAP=N'"+this.tendangnhap+"'   ";
		
		if(this.id.length()>0)
			query +="\n and dangnhap!=(select tendangnhap from GiamSatBanHang where pk_Seq='"+this.id+"') ";
		
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
	

	public boolean UpdateGsbh(HttpServletRequest request) 
	{
		try
		{	
			this.db.getConnection().setAutoCommit(false);	
			this.ngaysua = getDateTime();
			this.nguoisua = this.userId;
			String query = "";
			List<Object> data = new ArrayList<Object>();	

			if(this.kbhId.length() <=0)
			{
				this.db.getConnection().rollback();
				this.msg = "Vui lòng chọn kênh bán hàng!";
				return false; 
			}
			if(this.asmId.length() <=0)
			{
				this.db.getConnection().rollback();
				this.msg = "Vui lòng chọn ASM trực thuộc!";
				return false; 
			}
			
			if(!Utility.KiemTra_PK_SEQ_HopLe(this.id, "giamsatbanhang", db))
			{
				this.db.getConnection().rollback();
				this.msg = "Lỗi định dang không hợp lệ!";
				return false; 
			}

			query = "delete gsbh_khuvuc where gsbh_fk='"+this.id+"'";
			if(!db.update(query))
			{
				this.msg = "Lỗi cập nhật Giám sát bán hàng 1";
				this.db.getConnection().rollback();
				return false;
			}			

			String sql = "select smartid from giamsatbanhang where smartid = '"+this.smartid+"' and pk_seq <> "+this.id+"";
			ResultSet ktrs = db.get(sql);
			if(ktrs.next())
			{
				this.msg = "Trùng mã GSBH, vui lòng sửa lại mã GSBH!";
				return false;
			}
			ktrs.close();
			
			if(this.kvId != null)
				if(this.kvId.length()>0)
				{
					query = "insert into gsbh_khuvuc(gsbh_fk, khuvuc_fk) select "+this.id+",pk_seq from KhuVuc where pk_Seq in ("+this.kvId+") ";
					if(!db.update(query))
					{
						this.msg = "Lỗi cập nhật Giám sát bán hàng 2";
						this.db.getConnection().rollback();
						return false;
					}
				}
			System.out.println("[nppId]"+nppIds);

			if(this.nppIds != null && this.nppIds.length()>0)
			{
				query = "insert into NHAPP_GIAMSATBH_log select *, '"+getDateTime()+"', '"+this.userId+"' from NHAPP_GIAMSATBH WHERE gsbh_fk IN ("+this.id+")";
				if(!db.update(query))
				{
					this.msg = "Lỗi cập nhật Giám sát bán hàng 3";
					this.db.getConnection().rollback();
					return false;
				}

				query = "delete from NHAPP_GIAMSATBH WHERE gsbh_fk IN ("+this.id+")";
				if(!db.update(query))
				{
					this.msg = "Lỗi cập nhật Giám sát bán hàng 4";
					this.db.getConnection().rollback();
					return false;
				}

				query="delete from DDKD_GSBH WHERE gsbh_fk = "+this.id+" ";
				if(!db.update(query))
				{
					this.msg = "Lỗi cập nhật Giám sát bán hàng 5";
					this.db.getConnection().rollback();
					return false;
				}

				String[] nppId = request.getParameterValues("nppId");
				if(nppId!=null)
				{
					int n=0;
					int i= 0;
					int size=nppId.length;
					while(n < size)
					{
						String ngaybatdau=request.getParameter("ngaybatdau"+i)==null?"":request.getParameter("ngaybatdau"+i).trim();
						String ngayketthuc=request.getParameter("ngayketthuc"+i)==null?"":request.getParameter("ngayketthuc"+i).trim();
						System.out.println("_____ngay ket thuc ______" + ngayketthuc);
						if(ngayketthuc.trim().length() > 0 ){
							query = "insert into nhapp_giamsatbh(npp_fk, gsbh_fk, ngaybatdau, ngayketthuc) " +
							"\n select ?,?,?,?";
							data.clear();
							data.add( nppId[n]);
							data.add(this.id);
							data.add(ngaybatdau);
							data.add(ngayketthuc);
							if( this.db.updateQueryByPreparedStatement(query,data) < 0)
							{
								this.msg = "Lỗi cập nhật Giám sát bán hàng 6";
								this.db.getConnection().rollback();
								return false;
							}
						}
						n++;
						i++;
					}
				}
				
				query =	"\n insert into DDKD_GSBH(DDKD_FK,GSBH_FK) "+
					"\n SELECT pk_seq,"+this.id+" " +
					"\n FROM DAIDIENKINHDOANH WHERE NPP_FK IN ("+this.nppIds+")  "+
					"\n and pk_seq not in (select ddkd_fk from ddkd_gsbh)";
				if(!db.update(query))
				{
					this.msg = "Lỗi cập nhật Giám sát bán hàng 7";
					this.db.getConnection().rollback();
					return false;
				}		
			}
			
			query = " select d.ten, k.TEN kenhDDKD from ddkd_gsbh x " +
					" inner join daidienkinhdoanh d on d.pk_seq = x.ddkd_fk " +
					" inner join KENHBANHANG k on k.PK_SEQ = d.KBH_FK " +
					" where d.trangthai = 1 and x.gsbh_fk ="+this.id+"	and d.KBH_FK != "+this.kbhId+"";
			ResultSet rs = db.get(query);
			if(rs.next()) 
			{
				this.msg =  "NVBH   " + rs.getString("ten") + " kênh (" + rs.getString("kenhDDKD") + ")  đang trực thuộc, bạn không thể đổi kênh!  ";
				this.db.getConnection().rollback();
				return false;
				
			}
			
			Object[] obj_data;
			if (TtppId != null && TtppId.length() > 0) {
				obj_data = dbutils.setObject(this.trangthai,ten,sodienthoai,matkhau,thuviec,cmnd,quequan,
						email,ngaysinh,ngaysua,userId,nccId,sotaikhoan,chutaikhoan,NganHangId,ChiNhanhId,
						hinhanh,smartid,TtppId,diachi,this.asmId);
				query = "\n update giamsatbanhang set trangthai = ?, " +
				"\n ten = ?, dienthoai = ?, MatKhau=PWDENCRYPT(?)," +
				"\n tinhtrang=?, CMND=?,quequan=?," +
				"\n email=?, ngaysinh=?, ngaysua = ?, " +
				"\n nguoisua = ?, ncc_fk = ?, taikhoan = ?, " +
				"\n chutaikhoan = ?, NganHang = ?, ChiNhanh = ?, " +
				"\n hinhanh = ?, SmartId = ?, TtppId = ?, diachi = ?, asm_fk = ? " +
				"\n where pk_seq = "+id;
			}
			else {
				obj_data = dbutils.setObject(this.trangthai,ten,sodienthoai,matkhau,thuviec,cmnd,quequan,
						email,ngaysinh,ngaysua,userId,nccId,sotaikhoan,chutaikhoan,NganHangId,ChiNhanhId,
						hinhanh,smartid,diachi,this.asmId);
				query = "\n update giamsatbanhang set trangthai = ?,  " +
				"\n ten = ?, dienthoai = ?, MatKhau=PWDENCRYPT(?)," +
				"\n tinhtrang=?, CMND=?,quequan=?," +
				"\n email=?, ngaysinh=?, ngaysua = ?, " +
				"\n nguoisua = ?, ncc_fk = ?, taikhoan = ?, " +
				"\n chutaikhoan = ?, NganHang = ?, ChiNhanh = ?, " +
				"\n hinhanh = ?, SmartId = ? ,diachi = ? , asm_fk = ? " +
				"\n where pk_seq = "+id;
			}
			if (db.update_v2(query, obj_data) != 1)
			{
				this.msg = "Lỗi cập nhật Giám sát bán hàng 21";
				this.db.getConnection().rollback();
				return false;
			}
			
			query = "\n if((select isnull(DVKD_FK,0) from GIAMSATBANHANG where PK_SEQ =  "+this.id+") = 0) " +
			"\n	update giamsatbanhang set ten = N'"+ this.ten +"', DVKD_FK = '"+this.dvkdId+"' " +
			"\n where  PK_SEQ =  "+this.id+" ";
			System.out.println("2. Update GSBH : "+query);
			if (!db.update(query))
			{
				this.msg = "Lỗi cập nhật Giám sát bán hàng 22";
				this.db.getConnection().rollback();
				return false;
			}

			query = "\n if((select isnull(DVKD_FK,0) from GIAMSATBANHANG where PK_SEQ =  "+this.id+") <> 0) " +
			"\n	update 	giamsatbanhang set ten = N'"+ this.ten +"', DVKD_FK = '"+this.dvkdId+"' , kbh_fk = '"+this.kbhId+"' " +
			"\n where  PK_SEQ =  "+this.id+" ";
			System.out.println("2. Update GSBH : "+query);
			if (!db.update(query))
			{
				this.msg = "Lỗi cập nhật Giám sát bán hàng 23";
				this.db.getConnection().rollback();
				return false;
			}
			
			query = "\n INSERT INTO LichSu_GSBH_NPP(NPP_FK,GSBH_FK,NGAYBATDAU,NGAYKETTHUC) "+
			"\n	SELECT NPP_FK,GSBH_FK,NGAYBATDAU,NGAYKETTHUC FROM NHAPP_GIAMSATBH A  "+
			"\n	WHERE NOT EXISTS  "+
			"\n	(  "+
			"\n		SELECT * FROM LichSu_GSBH_NPP B  "+
			"\n		WHERE B.NPP_FK=A.NPP_FK AND A.GSBH_FK=B.GSBH_FK "+
			"\n	) AND A.GSBH_FK="+this.id+" " ;
			if(!db.update(query))
			{
				this.msg = "Lỗi cập nhật Giám sát bán hàng 24";
				this.db.getConnection().rollback();
				return false;
			}

			

			query = "delete from  NHANVIEN_KENH where Nhanvien_fk in (select pk_seq from NHANVIEN where GSBH_FK='"+this.id+"')" ;
			if(!this.db.update(query))
			{
				this.msg = "Lỗi cập nhật Giám sát bán hàng 25";
				this.db.getConnection().rollback();
				return false; 
			}

			query = "delete from  PHAMVIHOATDONG where Nhanvien_fk in (select pk_seq from NHANVIEN where GSBH_FK='"+this.id+"')" ;
			if(!this.db.update(query))
			{
				this.msg = "Lỗi cập nhật Giám sát bán hàng 26";
				this.db.getConnection().rollback();
				return false; 
			}

			query = "delete from  NHANVIEN_SANPHAM where Nhanvien_fk in (select pk_seq from NHANVIEN where GSBH_FK='"+this.id+"')" ;
			if(!this.db.update(query))
			{
				this.msg = "Lỗi cập nhật Giám sát bán hàng 27";
				this.db.getConnection().rollback();
				return false; 
			}

			query =	"\n insert into NHANVIEN_KENH(Nhanvien_fk,Kenh_fk) " +
				"\n select nv.pk_seq,gs.kbh_fk  from nhanvien nv inner join giamsatbanhang gs on gs.pk_Seq=nv.gsbh_fk where gs.pk_Seq='"+this.id+"' ";
			if(!this.db.update(query))
			{
				this.msg = "Lỗi cập nhật Giám sát bán hàng 28";
				this.db.getConnection().rollback();
				return false; 
			}

			query =	"\n insert into PHAMVIHOATDONG(Npp_fk,Nhanvien_fk) "+
				"\n select b.NPP_FK,a.PK_SEQ "+
				"\n from  NHANVIEN a inner join NHAPP_GIAMSATBH  b on b.GSBH_FK=a.GSBH_FK " +
				"\n where a.gsbh_fk="+this.id+"  ";
			if(!this.db.update(query))
			{
				this.msg = "Lỗi cập nhật Giám sát bán hàng 29";
				this.db.getConnection().rollback();
				return false; 
			}
			
			query = "\n insert into NHANVIEN_SANPHAM(Sanpham_fk,Nhanvien_fk) "+
				"\n select sp.PK_SEQ,nv.PK_SEQ "+
				"\n from SANPHAM sp,NHANVIEN nv "+
				"\n where nv.GSBH_FK='"+this.id+"' ";
			if(!this.db.update(query))
			{
				this.msg = "Lỗi cập nhật Giám sát bán hàng 30";
				this.db.getConnection().rollback();
				return false; 
			}
			
			/*query = "\nupdate NHANVIEN set TEN=?,NGAYSINH=?,DANGNHAP=?,MATKHAU=pwdencrypt(?),EMAIL=?,DIENTHOAI=?, " +
			"\n TRANGTHAI=?,NGAYSUA=?,NGUOISUA =?, " +
			"\n SESSIONID = case when ?='0' then  (SELECT CONVERT(VARCHAR(10),DATEADD(day,-65,dbo.GetLocalDate(DEFAULT)),120)) end , " +
			"\n Loai=3 where gsbh_fk=? ";
			data.clear();
			data.add( this.ten);
			data.add( this.ngaysinh);
			data.add( this.tendangnhap);
			data.add( this.matkhau);
			data.add( this.email);
			data.add( this.sodienthoai);
			data.add( this.trangthai);
			data.add( this.getDateTime());
			data.add( this.userId);
			data.add( this.trangthai);
			data.add( this.id);
			if( this.db.updateQueryByPreparedStatement(query, data) < 0)
			{
				this.msg = "Lỗi cập nhật Giám sát bán hàng 31";
				this.db.getConnection().rollback();
				return false;
			}*/

			query = "\n UPDATE GIAMSATBANHANG SET TIMKIEM =upper(dbo.ftBoDau(ten))+' '+UPPER(dbo.ftBoDau(diachi)) " +
					"\n 	+' '+UPPER(dbo.ftBoDau(ISNULL(CMND,'')))+' '+UPPER(dbo.ftBoDau(isnull(Email,''))) " +
					"\n 	+' '+UPPER(dbo.ftBoDau(isnull(DIENTHOAI,'')))  " +
					"\n where pk_seq='"+this.id+"'";
			if(!db.update(query))
			{
				this.msg = "Lỗi cập nhật Giám sát bán hàng 32";
				this.db.getConnection().rollback();
				return false;
			}	

			query = "\n	insert GiamSatBanHang_LOG(PK_SEQ,trangthai,npp_fk) " +
			"\n			select a.pk_seq,a.trangthai, b.npp_fk " +
			"\n			from GiamSatBanHang a " +
			"\n			left join NHAPP_GIAMSATBH b on a.PK_SEQ = b.GSBH_FK" +
			"\n	  		where a.pk_Seq = "+ this.id;
			if(!(this.db.update(query)))
			{
				this.msg="Không thể ghi nhận log chuyển tiền nhiệm"+ query;
				geso.dms.center.util.Utility.rollback_throw_exception(this.db);
				return false;
			}
			
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		}catch(Exception er)
		{
			this.msg = "Lỗi ngoại lệ khi cập nhật GSBH, vui lòng liên hệ Admin để xử lý!";
			geso.dms.center.util.Utility.rollback_throw_exception(this.db);
			this.db.shutDown();
			er.printStackTrace();
			return false;
		}

		return true; 
	}

	public void init()
	{
		String query = "\n select a.asm_fk,a.smartid, isnull(a.tinhtrang,'0') as tinhtrang, isnull(a.cmnd,'') as cmnd, " +
		"\n isnull(a.quequan,'') as quequan, isnull(a.ngaysinh,'') as ngaysinh ,a.pk_seq as id, a.ten, " +
		"\n a.dienthoai as sodienthoai, a.email, a.diachi as diachi, a.trangthai as trangthai, a.hinhanh, a.ngaytao, a.ngaysua, " + 
		"\n isnull(a.nganhang,'') as nganhang, isnull(b.ngaybatdau, ' ') as ngaybatdau, isnull(b.ngayketthuc, ' ') as ngayketthuc, " +
		"\n isnull(a.taikhoan,'') as taikhoan, a.ncc_fk as nccId, a.kbh_fk as kbhId, a.dvkd_fk as dvkdId,  " +
		"\n isnull(a.chutaikhoan,'') as chutaikhoan, a.NganHang, a.ChiNhanh, a.GSBHTN_FK, a.TenDangNhap, a.MatKhau, a.TTPP_FK"+
		"\n from giamsatbanhang a " +
		"\n left join nhapp_giamsatbh b on a.pk_seq = b.gsbh_fk "+
		"\n where a.pk_seq= '"+this.id+"' "; 		
		System.out.println("Init List: "+query);
        ResultSet rs =  this.db.get(query);
        try{
        	
        	if(rs!=null)
        	{
        		while(rs.next())
        		{
        			this.asmId = rs.getString("asm_fk") == null ? "" :   rs.getString("asm_fk");
        			this.smartid = rs.getString("smartid");
        			this.id = rs.getString("id");
                	this.ten = rs.getString("ten");
                	this.sodienthoai = rs.getString("sodienthoai");
                	this.email = rs.getString("email");
                	this.diachi = rs.getString("diachi");
                	this.trangthai = rs.getString("trangthai");
                	this.TtppId = rs.getString("TTPP_FK");
                	this.nccId = rs.getString("nccId");
                	this.dvkdId = rs.getString("dvkdId");
                	this.kbhId = rs.getString("kbhId");    
                	this.cmnd = rs.getString("cmnd");
                	this.ngaysinh = rs.getString("ngaysinh");
                	this.quequan = rs.getString("quequan");
                	this.thuviec = rs.getString("tinhtrang");
                	this.nganhang = rs.getString("nganhang");
                	this.sotaikhoan = rs.getString("taikhoan");
                	this.chutaikhoan = rs.getString("chutaikhoan");
                	this.NganHangId = rs.getString("NganHang")==null?"":rs.getString("NganHang");
                	this.ChiNhanhId = rs.getString("ChiNhanh")==null?"":rs.getString("ChiNhanh");                	
                	
                	this.hinhanh = rs.getString("hinhanh")==null?"":rs.getString("hinhanh");   
                	if (this.hinhanh != null && this.hinhanh.length() > 0) {
                		imgList.clear();
                		imgList.add(this.hinhanh);
                	}
                	
                	this.gsbhTnId = rs.getString("GSBHTN_FK")==null?"":rs.getString("GSBHTN_FK");
                	this.tendangnhap = rs.getString("tendangnhap")==null?"":rs.getString("tendangnhap");
                	this.ngaybatdau = rs.getString("ngaybatdau");
                	this.ngayketthuc = rs.getString("ngayketthuc");
                	this.matkhau = rs.getString("matkhau")==null?"":rs.getString("matkhau");
        		}
        		rs.close();
        		
        		 query="select KhuVuc_fk from GSBH_KHUVUC WHERE GSBH_FK="+this.id+"" ;
                 rs=db.get(query);
                 while(rs.next())
                 {
                	 kvId +=rs.getString("KhuVuc_fk")+ ",";
                 }
                 if(kvId.length()>0)
                	 kvId=kvId.substring(0,kvId.length()-1);
                 rs.close();
                 
                 
            	 query="select NPP_FK from NHAPP_GIAMSATBH WHERE GSBH_FK="+this.id+"" ;
                 rs=db.get(query);
                 System.out.println("IN NPP " +query);
                 while(rs.next())
                 {
                	 this.nppIds +=rs.getString("NPP_FK")+ ",";
                 }
                 if(nppIds.length()>0)
                	 nppIds=nppIds.substring(0,nppIds.length()-1);
                 rs.close();
                 
               //CHECK CO THE CHON NHAN VIEN TIEN NHIEM HAY KHONG
         		if(this.id.trim().length() > 0)
         		{
         			 query = " select COUNT(*) as soDong " +
         					       "from " +
         					       "( " +
         					       		"select PK_SEQ, GSBHTN_FK from GIAMSATBANHANG where  PK_SEQ = '" + this.id + "' and GSBHTN_FK is not null " +
         					       " union all " +
         					       		"select PK_SEQ, GSBH_FK from DONHANG where GSBH_FK = '" + this.id + "' " +
         					       	") " +
         					       	"TienNhiem ";
         			
         			System.out.println("___CHECK CO THE CHON TIEN NHIEM: " + query);
         			ResultSet rsTN = db.get(query);
         			try 
         			{
         				if(rsTN.next())
         				{
         					if(rsTN.getInt("soDong") <= 0)
         					{
         						this.cothechonTN = "1";
         					}
         				}
         				rsTN.close();
         			} 
         			catch (Exception e) {}
         			
         		}
        	}
       	}catch(Exception e){e.printStackTrace();}
        
       
       	createRS(); 
       	
	}
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
public String getCmnd() {
		
		return this.cmnd;
	}

	
	public void setCmnd(String cmnd) {
		
		this.cmnd=cmnd;
	}

	
	public String getNgaysinh() {
		
		return this.ngaysinh;
	}

	
	public void setNgaysinh(String ngaysinh) {
		
		this.ngaysinh=ngaysinh;
	}

	
	public String getQuequan() {
		
		return this.quequan;
	}

	
	public void setQuequan(String quequan) {
		this.quequan=quequan;
		
	}

	public String getThuviec() 
	{
		return this.thuviec;
	}


	public void setThuviec(String thiec) {
		this.thuviec=thiec;
	}

	
	public String getNganHangId()
	{
		return this.NganHangId;
	}

	
	public void setChiNhanhId(String ChiNhanhId)
	{
		this.ChiNhanhId=ChiNhanhId;
		
	}

	
	public String getChiNhanhId()
	{
		return this.ChiNhanhId;
	}

	
	public void setNganHangId(String ChiNhanhId)
	{
		this.NganHangId=ChiNhanhId;
		
	}

	
	public ResultSet getRsNganHang()
	{
		return this.rsNganHang;
	}

	
	public ResultSet getRsChiNhanh()
	{
		return this.rsChiNhanh;
	}

	
	public void setRsNganHang(ResultSet rsNganHang)
	{
		this.rsNganHang=rsNganHang;
		
	}

	
	public void setRsChiNhanh(ResultSet rsChiNhanh)
	{
		this.rsChiNhanh=rsChiNhanh;
		
	}

	
	

	public void setHinhanh(String ha) 
	{
		this.hinhanh = ha;
	}

	public String getHinhanh() 
	{
		return this.hinhanh;
	}
	
	String nppIds,gsbhTnId;
	ResultSet nppRs,nppQuanLyRs,gsbhTnRs;

	public String getNppIds()
	{
		return nppIds;
	}

	public void setNppIds(String nppIds)
	{
		this.nppIds = nppIds;
	}

	public String getGsbhTnId()
	{
		return gsbhTnId;
	}

	public void setGsbhTnId(String gsbhTnId)
	{
		this.gsbhTnId = gsbhTnId;
	}

	public ResultSet getNppRs()
	{
		return nppRs;
	}

	public void setNppRs(ResultSet nppRs)
	{
		this.nppRs = nppRs;
	}

	public ResultSet getNppQuanLyRs()
	{
		return nppQuanLyRs;
	}

	public void setNppQuanLyRs(ResultSet nppQuanLyRs)
	{
		this.nppQuanLyRs = nppQuanLyRs;
	}

	public ResultSet getGsbhTnRs()
	{
		return gsbhTnRs;
	}

	public void setGsbhTnRs(ResultSet gsbhTnRs)
	{
		this.gsbhTnRs = gsbhTnRs;
	}

	public void closeDB()
	{
			try
			{
				if(gsbhTnRs!=null)
				this.gsbhTnRs.close();
				
				if(nppQuanLyRs!=null)
					this.nppQuanLyRs.close();
				
				if(this.vung!=null)
					this.vung.close();
				
				if(this.khuvuc!=null)
					this.khuvuc.close();
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
			finally
			{
				if(db!=null)db.shutDown();
			}
	}
	
	ResultSet nppTnQl;
	public ResultSet getNppGsTnQuanLy()
	{
		return nppTnQl;
	}
	public void setNppGsTnQuanLy(ResultSet nppTnRs)
	{
		this.nppQuanLyRs =nppTnRs;
	}
	
	String cothechonTN;
	public void setCothechonTN(String mathuviec) {
		
		this.cothechonTN = mathuviec;
	}

	
	public String getCothechonTN() {
		
		return this.cothechonTN;
	}

	String tendangnhap,matkhau;
	
	@Override
	public String getTenDangNhap()
	{
		
		return tendangnhap;
	}

	@Override
	public void setTenDangNhap(String dangnhap)
	{
		this.tendangnhap =dangnhap;
	}

	@Override
	public String getMatKhau()
	{
		
		return this.matkhau;
	}

	@Override
	public void setMatKhau(String matkhau)
	{
		this.matkhau =matkhau;
	}

	@Override
	public String getSmartId() {
		// TODO Auto-generated method stub
		return this.smartid;
	}

	@Override
	public void setSmartId(String smartid) {
		this.smartid = smartid;
		
	}
	
	public boolean saveImage(dbutils db, ArrayList<String> imgList) {
		if (id != null && id.length() > 0) {
			//Cho chạy vòng for biết đâu mai mốt nó yêu cầu Up nhiều ảnh.
			for (int i = 0; i < imgList.size(); i++) { 
				String query = "update giamsatbanhang set hinhanh = '"+imgList.get(i)+"' where pk_seq = "+id;
				if (!db.update(query)) {
					this.msg = "Lỗi cập nhật hình ảnh cho GSBH!";
					return false;
				}
			}
		}
		else {
			this.msg = "Không tìm thấy định danh của GSBH!";
			return false;
		}
		
		return true;
	}
	ResultSet logRs;
	public ResultSet getLogRs() {
		return logRs;
	}
	public ResultSet getAsmRs()
	{
		if(this.kbhId != null && this.kbhId.length() > 0)
		{
			String query = " select pk_seq, ten from asm where trangthai = 1 and kbh_fk = " + this.kbhId;
			return db.get(query);
		}
		return null;
	}
	String asmId = "";
	public String getAsmId() {
		return asmId;
	}
	public void setAsmId(String asmId) {
		this.asmId = asmId;
	}
}

