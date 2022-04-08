package geso.dms.center.beans.mokhoaso.imp;
import geso.dms.center.beans.mokhoaso.*;
import geso.dms.center.db.sql.Idbutils;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;
import geso.dms.distributor.beans.khoasongay.imp.Khoasongay;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Mokhoaso implements IMokhoaso{

	String vungId;
	String kvId;
	String kenhId;
	String nppId;
	String msg;
	String ngay;
	ResultSet vung;
	ResultSet kv;
	ResultSet kenh;
	ResultSet npp;
	ResultSet nppRs;
	String userId;
	dbutils db;
	
	String[] nppChonIds ;
	public String[] getNppChonIds() {
		return nppChonIds;
	}
	public void setNppChonIds(String[] nppChonIds) {
		this.nppChonIds = nppChonIds;
	}
	
	public Mokhoaso(){
		this.vungId = "";
		this.kenhId ="";
		this.kvId = "";
		this.nppId = "";
		this.msg = "";
		this.ngay = "";
		this.db = new dbutils();
	}
	public void setVungId(String vungId){
		this.vungId = vungId;
	}

	public String getVungId(){
		return this.vungId;
	}

	public void setKhuvucId(String kvId){
		this.kvId = kvId;
	}

	public String getKhuvucId(){
		return this.kvId;
	}
	
	public void setKenhId(String kenhId){
		this.kenhId = kenhId;
	}

	public String getKenhId(){
		return this.kenhId;
	}
	

	public void setNppId(String nppId){
		this.nppId = nppId;
	}

	public String getNppId(){
		return this.nppId;
	}

	public void setMsg(String msg){
		this.msg = msg;
	}

	public String getMsg(){
		return this.msg;
	}

	public void setVung(ResultSet vung){
		this.vung = vung;
	}

	public ResultSet getVung(){
		return this.vung;
	}

	public void setKhuvuc(ResultSet kv){
		this.kv = kv;
	}

	public ResultSet getKhuvuc(){
		return this.kv;
	}
	
	public void setKenh(ResultSet kenh){
		this.kenh = kenh;
	}

	public ResultSet getKenh(){
		return this.kenh;
	}
	

	public void setNpp(ResultSet npp){
		this.npp = npp;
	}

	public ResultSet getNpp(){
		return this.npp;
	}

	public void init(){
		String vung;
		String kv;
		String npp;
		vung = "SELECT PK_SEQ AS VUNGID, TEN FROM VUNG";
		this.vung = this.db.get(vung);
		
		
		kv = "SELECT PK_SEQ AS KVID, TEN FROM KHUVUC WHERE 1 = 1 ";	
		if(this.vungId.length() > 0)
			kv +=" and VUNG_FK = '" + this.vungId + "' ";
		this.kv = db.get(kv);
		
		
		npp = "SELECT PK_SEQ AS NPPID, TEN FROM NHAPHANPHOI where trangthai = 1 ";
		if(this.vungId.length() > 0)
			npp +=" and KHUVUC_FK in (select pk_seq from khuvuc where vung_fk =  " + this.vungId + ") ";
		if(this.kvId.length() > 0)
			npp +=" and KHUVUC_FK = '" + this.kvId + "' ";
		this.npp = db.get(npp);
		
		
		
		String query=	"\n select a.khoasotudong ,a.pk_Seq,a.manpp,a.ten, a.diachi, "+
						"\n		isnull( (select max(ngayks) from KHOASONGAY where NPP_FK=a.PK_SEQ) ,N'Chưa có ngày KS')as NgayKS , isnull(kst.namthang,N'Chưa KS tháng')namthang  "+
						"\n	from NHAPHANPHOI a " +
						"\n outer apply" +
						"\n (" +
						"\n		select top 1 cast( nam as varchar) + '/' + dbo.PlusZero(thangks,2)  namthang    " +
						"\n		from khoasothang x" +
						"\n		where x.npp_fk = a.pk_seq " +
						"\n		order by namthang desc " +
						"\n )kst" +
						"\n inner join khuvuc b on a.khuvuc_fk = b.pk_seq " +
						"\n inner join vung v on v.pk_seq = b.vung_fk"+
						"\n	where a.trangthai=1 ";
				if(this.vungId.length() > 0){
					query += " and v.pk_seq = "+this.vungId+" ";
				}
				if(this.kvId.length() > 0){
					query += " and b.pk_seq = "+this.kvId+" ";
				}
				if(this.nppId.length() > 0)
				{
					query += " and a.pk_seq = "+this.nppId+" ";
				}
				if(this.kenhId.length() > 0)
				{
					query += " and a.kbh_fk = "+this.kenhId+" ";
				}
		this.nppRs = this.db.get(query);

		if(this.nppId.trim().length() > 0)
		{		
			query = " select max(ngayks)ngayks from khoasongay where npp_fk =  "+ this.nppId;
			ResultSet rs = db.get(query);
			try {
				rs.next();
				this.ngay = rs.getString("ngayks") == null ? "" : rs.getString("ngayks");
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}

	public String MoKhoaSoNgay()
	{		
		try
		{
			ResultSet rs;
			String query = "";
			if(this.nppId.trim().length() <=0)
			{
				return "Vui lòng chọn NPP muốn mở khóa sổ";
			}
			/*if(this.ngay.trim().length() <=0)
			{
				return "Vui lòng chọn ngày muốn mở khóa sổ";
			}*/
			
			db.getConnection().setAutoCommit(false);
			
			/*query =  " SELECT  count(*) data FROM KHOASONGAY WHERE NPP_FK = '"+ this.nppId +"' and ngayks >= '"+this.ngay+"' " ;
			rs = this.db.get(query);
			rs.next();
			if(rs.getInt("data") > 30 )
			{
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
				return "Không thể mở khóa sổ quá 30 ngày";
			}*/
			
			/*query =  		" SELECT  NGAYKS FROM KHOASONGAY WHERE NPP_FK = '"+ this.nppId +"' and ngayks >='"+this.ngay+"' " +
							" ORDER BY NGAYKS DESC";*/
			
			query =  		" SELECT top 1 NGAYKS, isnull(isTonDau,0)isTonDau FROM KHOASONGAY WHERE NPP_FK = '"+ this.nppId +"'  ORDER BY NGAYKS DESC";
			
			System.out.println("MOKS: "+query);
			String ngayks = "";
			rs = this.db.get(query);
			
			boolean codata= false;
			while(rs.next())
			{
				
				ngayks = rs.getString("NGAYKS");
				
				query = " select convert( varchar(10), DATEADD(d, -1, DATEADD(m, DATEDIFF(m, 0, max (ngaydauthang) ) + 1, 0)),120) ngaycuoithang " +
				" from khoasothang where npp_fk =  "+ this.nppId;
				ResultSet rsCheck = db.get(query);
				if(rsCheck.next())
				{
					String ngaycuoithang = rsCheck.getString("ngaycuoithang");
						
					if(ngaycuoithang.compareTo(ngayks) >=0 )
					{
						db.getConnection().rollback();
						db.getConnection().setAutoCommit(true);
						return " Ngày : " + ngayks + " đã thuộc tháng khóa sổ, không thể mở ! ";
					}
				}
				
				int isTonDau = rs.getInt("isTonDau");
				if(isTonDau > 0)
				{
					db.getConnection().rollback();
					db.getConnection().setAutoCommit(true);
					return "Bạn không thể mở khóa sổ của đầu kỳ";
				}
				
				
				
				
				query = "DELETE FROM KHOASONGAY WHERE NPP_FK = ? and ngayks = ? ";
				Object[] data;
				data= new Object[]   {  this.nppId, ngayks};
				if(this.db.updateQueryByPreparedStatement(query, data)!=1)
				{
					db.getConnection().rollback();
					db.getConnection().setAutoCommit(true);
					return "Không thể mở khóa sổ ngày(1): " + ngayks ;
				}
				query = "DELETE FROM TONKHONGAY WHERE NPP_FK = ? and ngay = ? ";
				data= new Object[]   {  this.nppId, ngayks};
				if(this.db.updateQueryByPreparedStatement(query, data)<0)
				{
					db.getConnection().rollback();
					db.getConnection().setAutoCommit(true);
					return "Không thể mở khóa sổ ngày(2): " + ngayks ;
				}
				query = "DELETE FROM TONKHONGAY_Chitiet WHERE NPP_FK = ? and ngay =? ";
				data= new Object[]   {  this.nppId, ngayks};
				
				if(this.db.updateQueryByPreparedStatement(query, data)<0)
				{
					db.getConnection().rollback();
					db.getConnection().setAutoCommit(true);
					return "Không thể mở khóa sổ ngày(3): " + ngayks ;
				}
				query = "insert into mokhoaso(npp_fk,ngayks,nguoitao,ngaytao) "
					+ "\n select  ? ,?, ?, dbo.GetLocalDate(DEFAULT) ";			
				data= new Object[]   {  this.nppId,ngayks, this.userId};	
				System.out.println("__+_ insert query :"+query);
				if(this.db.updateQueryByPreparedStatement(query, data)<=0)
				{
					db.getConnection().rollback();
					db.getConnection().setAutoCommit(true);
					return "Không thể mở khóa sổ ngày(4): " + ngayks ;
				}
				codata = true;
			}
			rs.close();
			if(!codata)
			{
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
				return "Chưa có khóa sổ tới ngày được chọn";
			}
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
			return "Mở khóa sổ thành công";
			
		}
		catch (Exception e) {			
			geso.dms.center.util.Utility.rollback_throw_exception(this.db);
			e.printStackTrace();
			return "Exception :" + e.getMessage();
		}
		
		
		
	}
	public static void main(String[] args) {
		dbutils db= new dbutils();
		try {
		String query = " select pk_seq from nhaphanphoi  c where pk_seq = 1000381 and trangthai = 1  and not exists ( select 1 from khoasongay x where x.ngayks = '2018-11-30' and x.npp_fk = c.pk_seq) ";
		ResultSet rs= db.get(query);
		
			while(rs.next())
			{
				dbutils dbx = new dbutils();
				 System.out.println(KhoasoMain(rs.getString("pk_seq"),"2018-11-30","100002",dbx) );
				 dbx.shutDown();
			}
		
		db.shutDown();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			db.shutDown();
		}
	}
	public static String KhoasoMain(String nppId, String ngayks, String nguoitao,dbutils db)
	{
		String msgX = "";
		String query = "";
		try {
			db.getConnection().setAutoCommit(false);
			//Xóa đơn hàng
			query = "select pk_seq, npp_fk, trangthai from donhang where npp_fk = ? and ngaynhap <= ? and trangthai = 0 ";
			Object[] data;
			data= new Object[]   { nppId, ngayks};
			
			ResultSet rs = db.getByPreparedStatement(query, data);
			if(rs != null)
			{
				while(rs.next())
				{
					if(!Delete(rs.getString("pk_seq"),rs.getString("npp_fk"),db) )
					{
						System.out.println("Khong the xoa don hang nay..."+rs.getString("pk_seq"));
						db.getConnection().rollback();
						return "Khong the xoa don hang: "+rs.getString("pk_seq");
					}
				}
				rs.close();
			}
			// đơn hàng có phiếu xuất kho
			query = "select b.pxk_fk  from donhang a inner join phieuxuatkho_donhang b on a.pk_seq = b.donhang_fk " +
					"inner join PHIEUXUATKHO pxk on pxk.PK_SEQ = b.PXK_FK " +
					"where pxk.npp_fk = ? and pxk.NGAYLAPPHIEU <= ? and pxk.trangthai = 0 ";
			data= new Object[]   { nppId, ngayks}; 
			
			rs = db.getByPreparedStatement(query, data);
			if(rs != null)
			{
				while(rs.next())
				{
					 String msg = DeletePxk(rs.getString("pxk_fk"),db);
					 if(msg.length() > 0)
					{
						System.out.println("Khong the xoa pxk nay..."+rs.getString("pxk_fk"));
						db.getConnection().rollback();
						return "Khong the xoa pxk: "+rs.getString("pxk_fk");
					}
				}
				rs.close();
			}
			
			query = "update donhang set trangthai = 1, FlagModified =1, ngaychot = dbo.GetLocalDate(DEFAULT) where npp_fk = ? and ngaynhap <= ? and trangthai = 3 ";
			data= new Object[]   { nppId, ngayks}; 
			
//			rs = db.getByPreparedStatement(query, data);
			if(db.updateQueryByPreparedStatement(query, data)<0)
			{
				System.out.println("Khong the chot dhDXK: "+query);
				db.getConnection().rollback();
				return "Khong the chot dhDXK: ";
			}
			
			query = "update donhang set trangthai = 2 where npp_fk = ? and ngaynhap <= ? and trangthai = 9 ";
			data= new Object[]   { nppId, ngayks}; 
			if(db.updateQueryByPreparedStatement(query, data)<0)
			{
				System.out.println("Khong the huy dhPDA: "+query);
				db.getConnection().rollback();
				return "Khong the huy dhPDA: ";
			}
			
			// Chốt phiếu thu hồi
			query = "select a.pk_seq, a.npp_fk, a.trangthai from PHIEUTHUHOI a inner join DONHANG b on a.donhang_fk = b.PK_SEQ " +
					"where a.npp_fk = ? and b.ngaynhap <= ? and a.trangthai = 0 ";
			data= new Object[]   { nppId, ngayks}; 

			rs = db.getByPreparedStatement(query, data);
			if(rs != null)
			{
				while(rs.next())
				{
					String msg = Chotphieuthuhoi(rs.getString("npp_fk"),rs.getString("pk_seq"),db);
					if(msg.length() > 0)
					{
						System.out.println("Khong the chot PHIEUTHUHOI nay..."+rs.getString("pk_seq"));
						db.getConnection().rollback();
						return "Khong the chot PHIEUTHUHOI: "+rs.getString("pk_seq");
					}
				}
				rs.close();
			}
			
			// Xóa đctk
			query = " select pk_seq, npp_fk, trangthai from DIEUCHINHTONKHO where NPP_FK = ? and NGAYDC <= ? and trangthai = 0 ";
			data= new Object[]   { nppId, ngayks}; 

			rs = db.getByPreparedStatement(query, data);
			if(rs != null)
			{
				while(rs.next())
				{
					String msg = DeleteDCTK(rs.getString("pk_seq"),db);
					if(msg.length() > 0)
					{
						System.out.println("Khong the xoa DIEUCHINHTONKHO nay..."+rs.getString("pk_seq"));
						db.getConnection().rollback();
						return "Khong the xoa DIEUCHINHTONKHO: "+rs.getString("pk_seq");
					}
				}
				rs.close();
			}
			
			// Xóa cknv
			query = " select pk_seq, npp_fk, trangthai from ChuyenKhoNV where npp_fk = ? and ngaychuyen <= ? and trangthai = 0 and pk_seq <> 8";
			data= new Object[]   { nppId, ngayks}; 

			rs = db.getByPreparedStatement(query, data);
			if(rs != null)
			{
				while(rs.next())
				{
					String msg = DeleteCknv(rs.getString("pk_seq"),db);
					if(msg.length() > 0)
					{
						System.out.println("Khong the xoa ChuyenKhoNV nay..."+rs.getString("pk_seq"));
						db.getConnection().rollback();
						return "Khong the xoa ChuyenKhoNV: "+rs.getString("pk_seq");
					}
				}
				rs.close();
			}
			
			// Khóa sổ			
			query = "select a.npp_fk, Max(a.ngayks) as ngayks from khoasongay a inner join nhaphanphoi b on a.npp_fk = b.pk_seq where b.pk_seq = ?  group by NPP_FK ";
			data= new Object[]   { nppId}; 

			rs = db.getByPreparedStatement(query, data);			if(rs != null)
			{
				while(rs.next())
				{
					query = "\n insert into TONKHONGAY(NPP_FK, KBH_FK, KHO_FK, SANPHAM_FK, SOLUONG, NGAY) " +
							"\n select a.npp_fk, a.kbh_fk, a.kho_fk, a.sanpham_fk, a.cuoiky, a.NGAYKS " +
							"\n from ufn_XNT_TuNgay_DenNgay_FULL_new(?, ?, ?) a left join NHAPP_KHO b " + 
							"\n on a.kho_fk = b.KHO_FK and a.npp_fk = b.NPP_FK and a.kbh_fk = b.KBH_FK and a.sanpham_fk = b.SANPHAM_FK ";
					data= new Object[]   { nppId, ngayks,ngayks}; 
					if(db.updateQueryByPreparedStatement(query, data)<0)
					{
						System.out.println("Loi khong them duoc ton kho ngay"+query);
						db.getConnection().rollback();
						return "Loi khong chen duoc ton kho ngay"+query;
					}
					
					query = "\n insert into TONKHONGAY_Chitiet(NPP_FK, KBH_FK, KHO_FK, SANPHAM_FK, SOLUONG, NGAY,ngaynhapkho) " +
							"\n select a.npp_fk, a.kbh_fk, a.kho_fk, a.sanpham_fk, a.cuoiky, a.NGAYKS, b.ngaynhapkho " +
							"\n from ufn_XNT_TuNgay_DenNgay_FULL_chitiet_new(?, ?, ?) a left join NHAPP_KHO_chitiet b " + 
							"\n on a.kho_fk = b.KHO_FK and a.npp_fk = b.NPP_FK and a.kbh_fk = b.KBH_FK and a.sanpham_fk = b.SANPHAM_FK and a.ngaynhapkho = b.ngaynhapkho ";
					
					data= new Object[]   { nppId, ngayks,ngayks}; 
					if(db.updateQueryByPreparedStatement(query, data)<0)
					{
						System.out.println("Loi khong them duoc ton kho ngay chi tiet"+query);
						db.getConnection().rollback();
						return "Loi khong chen duoc ton kho ngay"+query;
					}
					
					query = "insert into KHOASONGAY(NPP_FK, NGAYKSGANNHAT, NGAYKS, NGAYTAO, NGUOITAO, chon, doanhso, Created_Date) "+
							"select ?,?,?,?, ?, 1, 0, dbo.GetLocalDate(DEFAULT) ";
					
					data= new Object[]   { rs.getString("npp_fk"), rs.getString("ngayks"),ngayks,Utility.getNgayHienTai(),nguoitao}; 
					if(db.updateQueryByPreparedStatement(query, data)<0)

					{
						System.out.println("Lỗi tạo khóa sổ ngày...........");
						db.getConnection().rollback();
						return "Lỗi chèn khóa sổ ngày: ";
					}
				}
			}
			
			query = " select soluong from tonkhongay where NPP_FK = ? and ngay = ? ";
			data= new Object[]   { nppId, ngayks}; 

			rs = db.getByPreparedStatement(query, data);
			if(rs != null)
			{
				while(rs.next())
				{
					if(rs.getFloat("soluong") < 0)
					{
						System.out.println("Sản phẩm bị âm không thể khóa sổ NPP..."+rs.getFloat("soluong"));
						db.getConnection().rollback();
						return "Sản phẩm bị âm không thể khóa sổ NPP: "+rs.getFloat("soluong");
					}
				}
				rs.close();
			}
			query = "\n select a.npp_fk, a.sanpham_fk, a.soluong, a.booked, a.available, b.soluong, b.booked, b.available from NHAPP_KHO a "+
					"\n full outer join (select sanpham_fk, kho_fk, npp_fk, kbh_fk, sum(soluong) as soluong, sum(booked) as booked, sum(available) as available  "+
					"\n from NHAPP_KHO_CHITIET b group by sanpham_fk, kho_fk, npp_fk, kbh_fk ) b on a.sanpham_fk = b.sanpham_fk "+
					"\n and a.npp_fk = b.npp_fk and a.kho_fk = b.kho_fk and a.kbh_fk = b.kbh_fk  "+
					"\n where (a.soluong <> b.soluong or a.booked <> b.booked or a.available <> b.available) ";  
			ResultSet rschkkho = db.get(query);
			if (rschkkho != null) {
				while (rschkkho.next()) {
					msgX += "Lỗi:Số lượng sản phẩm kho tổng lệch với kho chi tiết!";
				}
				rschkkho.close();
			}
			if (msgX.trim().length() > 0) {
				db.getConnection().rollback();
				return msgX;
			}
			
			query = "	select a.npp_fk, a.kbh_fk, a.kho_fk, a.sanpham_fk, a.cuoiky, isnull(b.SOLUONG, 0) soluong, isnull(b.BOOKED, 0) booked, isnull(b.AVAILABLE, 0) avai " +
					"	from ufn_XNT_TuNgay_DenNgay_FULL_new(?, ?, '') a left join NHAPP_KHO b " + 
					"		on a.kho_fk = b.KHO_FK and a.npp_fk = b.NPP_FK and a.kbh_fk = b.KBH_FK and a.sanpham_fk = b.SANPHAM_FK " + 
					"	where a.cuoiky <> isnull(b.SOLUONG, 0) or a.cuoiky <> (isnull(b.AVAILABLE, 0) + isnull(b.BOOKED, 0))";
			System.out.println("câu check tồn: \n"+query);
			String msg = "";
			data= new Object[]   { nppId, TangNgayKs(ngayks)}; 

			rs = db.getByPreparedStatement(query, data);
			while(rs.next())
			{
				msg += "	Sản phẩm: "+rs.getString("sanpham_fk")+" có tồn cuối = "+rs.getString("cuoiky")+"; số lượng = "+rs.getString("soluong")+"; booked = "+rs.getString("booked")+"; avai = "+rs.getString("avai")+".\n";
			} rs.close();
			if(msg.trim().length() > 0)
			{
				System.out.println("Lệch tồn do còn phiếu thu hồi chưa chốt...........\n"+msg);
				db.getConnection().rollback();
				return "Lệch tồn...........\n"+msg;
			}
			System.out.println("END !!!!!!!");
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch (SQLException e) {
			
			e.printStackTrace();
			try
			{
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
				return "Lỗi";
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		System.out.println("-------------------XONG------------------------------");
		return "";
	}
	
	
	private String Khoaso(String nppId, String ngayks, String nguoitao)
	{
		String query = "";
		try {
			db.getConnection().setAutoCommit(false);
			//Xóa đơn hàng
			query = "select pk_seq, npp_fk, trangthai from donhang where npp_fk = ? and ngaynhap <= ? and trangthai = 0 ";
			Object[] data;
			data= new Object[]   { nppId, ngayks};
			
			ResultSet rs = db.getByPreparedStatement(query, data);
			if(rs != null)
			{
				while(rs.next())
				{
					if(!Delete(rs.getString("pk_seq"),rs.getString("npp_fk"),db) )
					{
						System.out.println("Khong the xoa don hang nay..."+rs.getString("pk_seq"));
						db.getConnection().rollback();
						return "Khong the xoa don hang: "+rs.getString("pk_seq");
					}
				}
				rs.close();
			}
			// đơn hàng có phiếu xuất kho
			query = "select b.pxk_fk  from donhang a inner join phieuxuatkho_donhang b on a.pk_seq = b.donhang_fk " +
					"inner join PHIEUXUATKHO pxk on pxk.PK_SEQ = b.PXK_FK " +
					"where pxk.npp_fk = ? and pxk.NGAYLAPPHIEU <= ? and pxk.trangthai = 0 ";
			data= new Object[]   { nppId, ngayks}; 
			
			rs = db.getByPreparedStatement(query, data);
			if(rs != null)
			{
				while(rs.next())
				{
					 String msg = DeletePxk(rs.getString("pxk_fk"),db);
					 if(msg.length() > 0)
					{
						System.out.println("Khong the xoa pxk nay..."+rs.getString("pxk_fk"));
						db.getConnection().rollback();
						return "Khong the xoa pxk: "+rs.getString("pxk_fk");
					}
				}
				rs.close();
			}
			
			query = "update donhang set trangthai = 1, FlagModified =1, ngaychot = dbo.GetLocalDate(DEFAULT) where npp_fk = ? and ngaynhap <= ? and trangthai = 3 ";
			data= new Object[]   { nppId, ngayks}; 
			
//			rs = db.getByPreparedStatement(query, data);
			if(db.updateQueryByPreparedStatement(query, data)<0)
			{
				System.out.println("Khong the chot dhDXK: "+query);
				db.getConnection().rollback();
				return "Khong the chot dhDXK: ";
			}
			
			query = "update donhang set trangthai = 2 where npp_fk = ? and ngaynhap <= ? and trangthai = 9 ";
			data= new Object[]   { nppId, ngayks}; 
			if(db.updateQueryByPreparedStatement(query, data)<0)
			{
				System.out.println("Khong the huy dhPDA: "+query);
				db.getConnection().rollback();
				return "Khong the huy dhPDA: ";
			}
			
			// Chốt phiếu thu hồi
			query = "select a.pk_seq, a.npp_fk, a.trangthai from PHIEUTHUHOI a inner join DONHANG b on a.donhang_fk = b.PK_SEQ " +
					"where a.npp_fk = ? and b.ngaynhap <= ? and a.trangthai = 0 ";
			data= new Object[]   { nppId, ngayks}; 

			rs = db.getByPreparedStatement(query, data);
			if(rs != null)
			{
				while(rs.next())
				{
					String msg = Chotphieuthuhoi(rs.getString("npp_fk"),rs.getString("pk_seq"),db);
					if(msg.length() > 0)
					{
						System.out.println("Khong the chot PHIEUTHUHOI nay..."+rs.getString("pk_seq"));
						db.getConnection().rollback();
						return "Khong the chot PHIEUTHUHOI: "+rs.getString("pk_seq");
					}
				}
				rs.close();
			}
			
			// Xóa đctk
			query = " select pk_seq, npp_fk, trangthai from DIEUCHINHTONKHO where NPP_FK = ? and NGAYDC <= ? and trangthai = 0 ";
			data= new Object[]   { nppId, ngayks}; 

			rs = db.getByPreparedStatement(query, data);
			if(rs != null)
			{
				while(rs.next())
				{
					String msg = DeleteDCTK(rs.getString("pk_seq"),db);
					if(msg.length() > 0)
					{
						System.out.println("Khong the xoa DIEUCHINHTONKHO nay..."+rs.getString("pk_seq"));
						db.getConnection().rollback();
						return "Khong the xoa DIEUCHINHTONKHO: "+rs.getString("pk_seq");
					}
				}
				rs.close();
			}
			
			// Xóa cknv
			query = " select pk_seq, npp_fk, trangthai from ChuyenKhoNV where npp_fk = ? and ngaychuyen <= ? and trangthai = 0 and pk_seq <> 8";
			data= new Object[]   { nppId, ngayks}; 

			rs = db.getByPreparedStatement(query, data);
			if(rs != null)
			{
				while(rs.next())
				{
					String msg = DeleteCknv(rs.getString("pk_seq"),db);
					if(msg.length() > 0)
					{
						System.out.println("Khong the xoa ChuyenKhoNV nay..."+rs.getString("pk_seq"));
						db.getConnection().rollback();
						return "Khong the xoa ChuyenKhoNV: "+rs.getString("pk_seq");
					}
				}
				rs.close();
			}
			
			// Khóa sổ			
			query = "select a.npp_fk, Max(a.ngayks) as ngayks from khoasongay a inner join nhaphanphoi b on a.npp_fk = b.pk_seq where b.pk_seq = ?  group by NPP_FK ";
			data= new Object[]   { nppId}; 

			rs = db.getByPreparedStatement(query, data);			if(rs != null)
			{
				while(rs.next())
				{
					query = "\n insert into TONKHONGAY(NPP_FK, KBH_FK, KHO_FK, SANPHAM_FK, SOLUONG, NGAY) " +
							"\n select a.npp_fk, a.kbh_fk, a.kho_fk, a.sanpham_fk, a.cuoiky, a.NGAYKS " +
							"\n from ufn_XNT_TuNgay_DenNgay_FULL_new(?, ?, ?) a left join NHAPP_KHO b " + 
							"\n on a.kho_fk = b.KHO_FK and a.npp_fk = b.NPP_FK and a.kbh_fk = b.KBH_FK and a.sanpham_fk = b.SANPHAM_FK ";
					data= new Object[]   { nppId, ngayks,ngayks}; 
					if(db.updateQueryByPreparedStatement(query, data)<0)
					{
						System.out.println("Loi khong them duoc ton kho ngay"+query);
						db.getConnection().rollback();
						return "Loi khong chen duoc ton kho ngay"+query;
					}
					
					query = "\n insert into TONKHONGAY_Chitiet(NPP_FK, KBH_FK, KHO_FK, SANPHAM_FK, SOLUONG, NGAY,ngaynhapkho) " +
							"\n select a.npp_fk, a.kbh_fk, a.kho_fk, a.sanpham_fk, a.cuoiky, a.NGAYKS, b.ngaynhapkho " +
							"\n from ufn_XNT_TuNgay_DenNgay_FULL_chitiet_new(?, ?, ?) a left join NHAPP_KHO_chitiet b " + 
							"\n on a.kho_fk = b.KHO_FK and a.npp_fk = b.NPP_FK and a.kbh_fk = b.KBH_FK and a.sanpham_fk = b.SANPHAM_FK and a.ngaynhapkho = b.ngaynhapkho ";
					
					data= new Object[]   { nppId, ngayks,ngayks}; 
					if(db.updateQueryByPreparedStatement(query, data)<0)
					{
						System.out.println("Loi khong them duoc ton kho ngay chi tiet"+query);
						db.getConnection().rollback();
						return "Loi khong chen duoc ton kho ngay"+query;
					}
					
					query = "insert into KHOASONGAY(NPP_FK, NGAYKSGANNHAT, NGAYKS, NGAYTAO, NGUOITAO, chon, doanhso, Created_Date) "+
							"select ?,?,?,?, ?, 1, 0, dbo.GetLocalDate(DEFAULT) ";
					
					data= new Object[]   { rs.getString("npp_fk"), rs.getString("ngayks"),ngayks,getDateTime(),nguoitao}; 
					if(db.updateQueryByPreparedStatement(query, data)<0)

					{
						System.out.println("Lỗi tạo khóa sổ ngày...........");
						db.getConnection().rollback();
						return "Lỗi chèn khóa sổ ngày: ";
					}
				}
			}
			
			query = " select soluong from tonkhongay where NPP_FK = ? and ngay = ? ";
			data= new Object[]   { nppId, ngayks}; 

			rs = db.getByPreparedStatement(query, data);
			if(rs != null)
			{
				while(rs.next())
				{
					if(rs.getFloat("soluong") < 0)
					{
						System.out.println("Sản phẩm bị âm không thể khóa sổ NPP..."+rs.getFloat("soluong"));
						db.getConnection().rollback();
						return "Sản phẩm bị âm không thể khóa sổ NPP: "+rs.getFloat("soluong");
					}
				}
				rs.close();
			}
			query = "\n select a.npp_fk, a.sanpham_fk, a.soluong, a.booked, a.available, b.soluong, b.booked, b.available from NHAPP_KHO a "+
					"\n full outer join (select sanpham_fk, kho_fk, npp_fk, kbh_fk, sum(soluong) as soluong, sum(booked) as booked, sum(available) as available  "+
					"\n from NHAPP_KHO_CHITIET b group by sanpham_fk, kho_fk, npp_fk, kbh_fk ) b on a.sanpham_fk = b.sanpham_fk "+
					"\n and a.npp_fk = b.npp_fk and a.kho_fk = b.kho_fk and a.kbh_fk = b.kbh_fk  "+
					"\n where (a.soluong <> b.soluong or a.booked <> b.booked or a.available <> b.available) ";  
			ResultSet rschkkho = db.get(query);
			if (rschkkho != null) {
				while (rschkkho.next()) {
					msg += "Lỗi:Số lượng sản phẩm kho tổng lệch với kho chi tiết!";
				}
				rschkkho.close();
			}
			if (msg.trim().length() > 0) {
				db.getConnection().rollback();
				return msg;
			}
			
			query = "	select a.npp_fk, a.kbh_fk, a.kho_fk, a.sanpham_fk, a.cuoiky, isnull(b.SOLUONG, 0) soluong, isnull(b.BOOKED, 0) booked, isnull(b.AVAILABLE, 0) avai " +
					"	from ufn_XNT_TuNgay_DenNgay_FULL_new(?, ?, '') a left join NHAPP_KHO b " + 
					"		on a.kho_fk = b.KHO_FK and a.npp_fk = b.NPP_FK and a.kbh_fk = b.KBH_FK and a.sanpham_fk = b.SANPHAM_FK " + 
					"	where a.cuoiky <> isnull(b.SOLUONG, 0) or a.cuoiky <> (isnull(b.AVAILABLE, 0) + isnull(b.BOOKED, 0))";
			System.out.println("câu check tồn: \n"+query);
			String msg = "";
			data= new Object[]   { nppId, TangNgayKs(ngayks)}; 

			rs = db.getByPreparedStatement(query, data);
			while(rs.next())
			{
				msg += "	Sản phẩm: "+rs.getString("sanpham_fk")+" có tồn cuối = "+rs.getString("cuoiky")+"; số lượng = "+rs.getString("soluong")+"; booked = "+rs.getString("booked")+"; avai = "+rs.getString("avai")+".\n";
			} rs.close();
			if(msg.trim().length() > 0)
			{
				System.out.println("Lệch tồn do còn phiếu thu hồi chưa chốt...........\n"+msg);
				db.getConnection().rollback();
				return "Lệch tồn...........\n"+msg;
			}
			System.out.println("END !!!!!!!");
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch (SQLException e) {
			
			e.printStackTrace();
			try
			{
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
				return "Lỗi";
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		System.out.println("-------------------XONG------------------------------");
		return "";
	}
	
	public static String DeleteCknv(String id, dbutils db) 
	{
		try 
		{
			//Cap nhat lai kho NPP truoc
			String query = "select a.npp_fk, a.kho_fk, b.sanpham_fk, b.soluong " +
						"from chuyenkhoNV a inner join chuyenkhoNV_sanpham b on a.pk_seq = b.chuyenkhoNV_fk where a.pk_seq = '" + id + "' and a.trangthai = '0' ";
			
			ResultSet rsUpdate = db.get(query);
			if(rsUpdate != null)
			{
				while(rsUpdate.next())
				{
					query = "update nhapp_kho set available = available + '" + rsUpdate.getInt("soluong") + "', booked = booked - '" + rsUpdate.getInt("soluong") + "' " +
							"where npp_fk = '" + rsUpdate.getString("npp_fk") + "' and kho_fk = '" + rsUpdate.getString("kho_fk") + "' and kbh_fk = '100025' and sanpham_fk = '" + rsUpdate.getString("sanpham_fk") + "' ";
					
					if(!db.update(query))
					{
						return "Không thể trừ chuyển kho: " + query;	
					}
				}
				rsUpdate.close();
			}else
			{
				return  "Lỗi không  xóa chuyển kho: " + query;
			}
			
			query = "update chuyenkhoNV set trangthai = '2' where pk_seq = '" + id + "' ";
					
			if(!db.update(query))
			{
				return "Không thể xóa chuyển kho: " + query;
			}
		} 
		catch (Exception e)
		{
			return "Lỗi"+e.getMessage();
		}
		
		return "";
	}
	private static String DeleteDCTK(String dctkId, dbutils db)
	{		
		
		String msg="";
		String query = "";
		try
		{
			geso.dms.center.util.Utility util = new geso.dms.center.util.Utility();
			// CAP NHAT LAI BOOK DOI VOI NHUNG DCTK AM
			/*String sql = 
					"UPDATE K SET K.BOOKED = BOOKED - DC.DIEUCHINH, K.AVAILABLE = K.AVAILABLE + DC.DIEUCHINH " +
					"FROM NHAPP_KHO K " +
					"INNER JOIN " +
					"( " +
					"	SELECT DC.KHO_FK, DC.KBH_FK, DC.NPP_FK, SANPHAM_FK, (-1)*DCSP.DIEUCHINH AS DIEUCHINH FROM DIEUCHINHTONKHO DC " +
					"	INNER JOIN DIEUCHINHTONKHO_SP DCSP ON DCSP.DIEUCHINHTONKHO_FK = DC.PK_SEQ WHERE DC.PK_SEQ = '"+ dctkId +"' AND DCSP.DIEUCHINH < 0 " +
					") DC ON DC.KHO_FK = K.KHO_FK AND DC.KBH_FK = K.KBH_FK AND DC.NPP_FK = K.NPP_FK AND DC.SANPHAM_FK = K.SANPHAM_FK ";

			System.out.println("Book dieu chinh am : " + sql);

			if(!db.update(sql))
			{
			
				msg="Lỗi khi cập nhật điều chỉnh tồn kho "+sql;
			}*/
			query=	"	SELECT dcsp.ngaynhapkho ,dc.ngaydc,DC.KHO_FK, DC.KBH_FK, DC.NPP_FK, SANPHAM_FK, (-1)*DCSP.DIEUCHINH AS DIEUCHINH FROM DIEUCHINHTONKHO DC " +
					"	INNER JOIN DIEUCHINHTONKHO_SP DCSP ON DCSP.DIEUCHINHTONKHO_FK = DC.PK_SEQ" +
					" WHERE DC.PK_SEQ = '"+ dctkId +"' AND DCSP.DIEUCHINH < 0 " ;
					
		    ResultSet ckRs = db.get(query);
		    while(ckRs.next())
		    {
		    	String kho_fk=ckRs.getString("kho_fk");
				String nppId=ckRs.getString("npp_fk");	
				String kbh_fk=ckRs.getString("kbh_fk");
				String sanpham_fk=ckRs.getString("sanpham_fk");
				String ngaydc  =ckRs.getString("ngaydc");
				String ngaynhapkho  =ckRs.getString("ngaynhapkho");
				
				Double dieuchinh = ckRs.getDouble("dieuchinh");


				msg=util.Update_NPP_Kho(ngaydc,dctkId, "XÓA DIEUCHINHTONKHO", db, kho_fk, sanpham_fk, nppId, kbh_fk,ngaynhapkho, 0.0,  (-1)*dieuchinh, dieuchinh,0, 0.0);
				if(msg.length()>0)
				{
					db.getConnection().rollback();
					db.getConnection().setAutoCommit(true);
					return msg;
				}			
		    }
			
			
			query = "update dieuchinhtonkho set trangthai = '2' where pk_seq = '"+ dctkId +"' and TrangThai=0 ";
			if(db.updateReturnInt(query)!=1)
			{
			
				msg="Điều chỉnh tồn kho đã xóa rồi !";
			}
		}catch(Exception e)
		{
			msg="Exception xảy ra khi cập nhật ";
			e.printStackTrace();
		}
	
		return msg ;
	}
	private static boolean Delete(String id,String nppId,dbutils db)
	{	
		String query = id + "&" + nppId;
		String param[] = query.split("&");

		int kq = db.execProceduce("DeleteDonHang", param);
		if(kq == -1)
			return false;
		return true;
	}
	private static String DeletePxk(String pxkId,dbutils db) 
	{
		//khi moi tao phieuxuatkho chua lam gi het, nen co the xoa thang
		//String query = "update phieuxuatkho set trangthai = '2' where pk_seq = '" + pxkId + "'";
		//db.update(query);
		String query = "";
		//db.update(query);
		try{
			db.getConnection().setAutoCommit(false);
			 query="delete phieuxuatkho_tienkm where pxk_fk = '" + pxkId + "'";
			if(!db.update(query)){
				db.getConnection().rollback();
				return query;
			}
					
			System.out.println("delete phieuxuatkho_tienkm where pxk_fk = '" + pxkId + "'");
			query="delete phieuxuatkho_sanpham where pxk_fk = '" + pxkId + "'";
			System.out.println(query);
			if(! db.update(query)){
				db.getConnection().rollback();
				return query;
			}
			System.out.println("delete phieuxuatkho_sanpham where pxk_fk = '" + pxkId + "'");
			query="delete phieuxuatkho_spkm where pxk_fk = '" + pxkId + "'";
			System.out.println(query);
			if(! db.update(query)){
				db.getConnection().rollback();
				return query;
			}
			query="delete PHIEUXUATKHO_DONHANG where pxk_fk='"+pxkId+"'";
			System.out.println(query);
			if(! db.update(query)){
				db.getConnection().rollback();
				return query;
			}
		
			query="delete phieuxuatkho where pk_seq = '" + pxkId + "' and trangthai=0 ";
			System.out.println(query);
			if(db.updateReturnInt(query)!=1)
			{
				db.getConnection().rollback();
				return query;
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			
		}catch(Exception er){
			System.out.println(er);
		}
		
		return "";		
	}
	private static String Chotphieuthuhoi(String nppId, String pthId,dbutils db) 
	{
		String msg = "";
		try 
		{
			//	cap nhat ton kho sanpham
			String query = "select sanpham_fk as spId, soluong, kho_fk as khoId, kbh_fk as kbhId from phieuthuhoi_sanpham where pth_fk = '" + pthId + "'";
			System.out.println("QR: "+query);
			ResultSet rs = db.get(query);
			/*if(rs != null)*/
			{
				while(rs.next())
				{
					query = "update nhapp_kho set soluong = soluong + " + rs.getString("soluong") + ", available = available + " + rs.getString("soluong") + " where sanpham_fk=" + rs.getString("spId") +" and npp_fk = " + nppId + " and kbh_fk=" + rs.getString("kbhId") + " and kho_fk = " + rs.getString("khoId");
					System.out.println("Cau lenh cap nhat san pham la: " + query + "\n");
					if(db.updateReturnInt(query)!=1)
					{
						msg = "Khong the cap nhat nhapp_kho " + query;
						return msg;
					}
				}
				rs.close();
			}
			
			//cap nhat ton kho sanpham khuyen mai
			query = "select sanpham_fk as spId, soluong, kho_fk as khoId, kbh_fk as kbhId from phieuthuhoi_spkm where pth_fk = '" + pthId + "'";
			System.out.println("Cau lenh lay spkm la: " + query + "\n");
			ResultSet rsKm = db.get(query);
			/*if(rsKm != null)*/
			{
				while(rsKm.next())
				{
					query = "update nhapp_kho set soluong = soluong + " + rsKm.getString("soluong") + ", available = available + " + rsKm.getString("soluong") + " where sanpham_fk=" + rsKm.getString("spId") +" and npp_fk = " + nppId + " and kbh_fk=" + rsKm.getString("kbhId") + " and kho_fk = " + rsKm.getString("khoId");
					System.out.println("Cau lenh cap nhat san pham khuyen mai la: " + query + "\n");
					if(db.updateReturnInt(query)!=1)
					{
						msg = "Khong the cap nhat san pham khuyen mai nhapp_kho " + query;
						return msg;
					}
				}
				rsKm.close();
			}
			
			query="update phieuthuhoi set trangthai = '1' where pk_seq = '" + pthId + "'";
			if(!db.update(query))
			{
				msg = "Khong the cap nhat san pham khuyen mai nhapp_kho " + query;
				return msg;
			}
		} 
		catch(Exception e) {
		
			return "Error Here :"+e.toString();
		}finally{
		
		}
		
		return msg;
	}

	/*public String KhoaSoNgay(String soNgay)
	{
		try {
			String error = "";
			String query = "select a.PK_SEQ, a.TEN from NHAPHANPHOI a  " +
					"\n inner join khuvuc b on a.khuvuc_fk = b.pk_seq " +
					"\n inner join vung c on c.pk_seq = b.vung_fk " +
					"\n inner join khoasongay d on a.pk_seq = d.npp_fk " +
					"\n where a.TRANGTHAI='1'" ;
					
			if(this.nppId.trim().length() > 0)
				query += " and a.pk_seq in ("+this.nppId+")";
			if(this.vungId.trim().length() > 0)
				query += " and c.pk_seq in ("+this.vungId+")";
			if(this.kvId.trim().length() > 0)
				query += " and b.pk_seq in ("+this.kvId+")";
			if(this.kenhId.trim().length() > 0)
				query += " and a.kbh_fk in ("+this.kenhId+")";
			
			
			query +="\n group by a.PK_SEQ, a.TEN ";
			ResultSet rs = db.get(query);
			int i = 0;
			while(rs.next())
			{
				String m = Khoaso(rs.getString("pk_seq"), this.ngay, this.userId);
				i++;
				if(m.trim().length() > 0){
					error += "Nhà phân phối "+rs.getString("ten")+": "+m+" \n";
					i--;
				}
				
			} rs.close();
			
			if(error.trim().length() > 0)
			{
				msg = "Thông báo sau khi khóa sổ: Khóa thành công "+i+" nhà phân phối, còn lại: \n" + error;
			}
			else
				msg = "Thông báo: Khóa sổ thành công "+i+" nhà phân phối ";
		
		} catch (SQLException e) {
			e.printStackTrace();
			return msg;
		} 
	
		return msg;
	}*/
	
	public String KhoaSoNgay(String soNgay)
	{
		try {
			if(this.nppId.trim().length() <=0)
			{
				return "Vui lòng chọn NPP muốn mở khóa sổ";
			}
			/*
			if(this.ngay.trim().length() <=0)
			{
				return "Vui lòng chọn ngày muốn mở khóa sổ";
			}
			
			if(this.ngay.compareTo(Utility.getNgayHienTai()) > 0 )
			{
				return "Không thể khóa sổ ngày ở tương lai!";
			}*/
			
			db.getConnection().setAutoCommit(false);
			ResultSet rs ;
			
			String query =
			"\n select  convert( VARCHAR(10), DATEADD(dd,1, max(ngayks) )    ,120) tungay  from KHOASONGAY where npp_fk  in ("+this.nppId+") ";
			System.out.println("query kstd = "+ query);
			rs = this.db.get(query);
			if(!rs.next())
			{
				this.db.getConnection().rollback();
				this.db.getConnection().setAutoCommit(true);
				return "Nhà phân phối chưa đưa tồn đầu!";
			}
			String tungay = rs.getString ("tungay");
			if(tungay == null)				
			{
				this.db.getConnection().rollback();
				this.db.getConnection().setAutoCommit(true);
				return "Nhà phân phối chưa đưa tồn đầu!";
			}
			/*if(tungay.compareTo(this.ngay) > 0)
			{
				this.db.getConnection().rollback();
				this.db.getConnection().setAutoCommit(true);
				return "Nhà phân phối  đã khóa sổ tới ngày "+this.ngay+" rồi ";
			}*/
			
			query = " select Ngay from dbo.uf_CacNgayTrongKhoangThoiGian('"+tungay+"','"+tungay+"') ";
			rs = db.get(query);
			while(rs.next())
			{
				String ngayks = rs.getString("Ngay");
				String msg = Khoasongay.KhoaSo_NPP(db, this.nppId, ngayks, ngayks, this.userId, 0);
				if(msg.trim().length() > 0)
				{
					this.db.getConnection().rollback();
					this.db.getConnection().setAutoCommit(true);
					return  ngayks + " - " + msg;
				}
			}
			
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
			return "Nhà phân phối  khóa sổ thành công! ";
		
		} catch (Exception e) {
			e.printStackTrace();
			Utility.rollback_throw_exception(this.db);
			return msg;
		} 
	
	}
	
	
	public String KhoaSoThang(String soNgay)
	{
		try {

			String dsNPP = "";
			
			if(this.nppChonIds != null)
			{
				for(int i = 0 ; i < this.nppChonIds.length ; i ++)
					dsNPP += dsNPP.length() > 0 ? "," + this.nppChonIds[i] : this.nppChonIds[i];
			}
			if(dsNPP.length() <=0)
			{
				db.getConnection().setAutoCommit(true);
				return "Vui lòng chọn NPP ";
			}
			String query =  "\n select a.PK_SEQ, a.TEN, ks.ngaydauthangsau " +
							"\n from NHAPHANPHOI a " +
							"\n outer apply" +
							"\n (" +
							"\n		select  convert( VARCHAR(10), DATEADD(month,1, max( ngaydauthang) ) ,120) ngaydauthangsau " +
							"\n		from khoasothang where npp_fk = a.pk_seq " +
							"\n )ks " +
							"\n where a.TRANGTHAI='1' and pk_seq in ("+dsNPP+")";
			System.out.println("query npp AL " + query);
			ResultSet rs = db.get(query);
			
			String thongbao = "";
			while(rs.next())
			{
				db.getConnection().setAutoCommit(false);
				
				String npp = rs.getString("PK_SEQ");
				String nppTen = rs.getString("TEN");
				String ngaydauthangsauks = rs.getString("ngaydauthangsau");
				
				if(ngaydauthangsauks == null)
				{
					db.getConnection().rollback();
					db.getConnection().setAutoCommit(true);
					thongbao += "\n " + nppTen + ": chưa có tồn đầu" ;
				}
				else
				{
					String kq = khoasothang_npp(db ,npp,this.userId,ngaydauthangsauks);
					
					if(kq.length() > 0)
					{
						db.getConnection().rollback();
						db.getConnection().setAutoCommit(true);
						thongbao += "\n " + nppTen + ": " + kq;
					}
					else
					{
						db.getConnection().commit();
						db.getConnection().setAutoCommit(true);
						thongbao += "\n " + nppTen + ": KS tháng thành công ("+ngaydauthangsauks.substring(0,7)+") " ;
					}
				}
			} 
			
			return thongbao;
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			
			return "Exception : "+ e.getMessage();
		} 

	}
	
	public static String khoasothang_npp(Idbutils db ,String nppId,String userId, String ngaydauthangsauks)
	{
		try
		{
			String query = "";
			int thangsauks  = Utility.parseInt(ngaydauthangsauks.split("-")[1]);
			int namsauks  = Utility.parseInt( ngaydauthangsauks.split("-")[0] );
			
			String ngayhientai = Utility.getNgayHienTai();
			int thanghientai =  Utility.parseInt(ngayhientai.split("-")[1]);
			int namhientai = Utility.parseInt( ngayhientai.split("-")[0]);
			
			if(ngaydauthangsauks.compareTo(ngayhientai) >= 0 || ( thangsauks == thanghientai && namsauks == namhientai     ) )
			{
				return  "  : Chỉ có thể khóa sổ  ("+namsauks + "-" + thangsauks +") sau khi kết thúc tháng !";
			}
			
			query ="\n insert khoasothang(NPP_FK,thangks,nam,ngaytao,nguoitao,created_date,ngaydauthang ) " +
			"\n select "+nppId+", "+thangsauks+", "+namsauks +
			"\n				,'"+Utility.getNgayHienTai()+"',"+userId+",dbo.GetLocalDate(default),  '"+namsauks+"-' + dbo.PlusZero("+thangsauks+",2) + '-01'  " ;
			
			if(db.updateReturnInt(query) <=0)
			{
				System.out.println("qu kst= "+ query);
				return  "  : Lỗi lưu khoasothang !";
			}
			return "";
		}
		catch (Exception e) {
			return "Exception : " + e.getMessage(); 
		}
	}

	private static String TangNgayKs(String ngayksgn)
	{
		String ngayks = ngayksgn;

		if (ngayks.equals(""))
			ngayks = Utility.getNgayHienTai();

		String[] ngay = ngayks.split("-");

		Calendar c2 = Calendar.getInstance();

		// trong java thang bat dau bang 0 (thang rieng)
		c2.set(Integer.parseInt(ngay[0]), Integer.parseInt(ngay[1]) - 1, Integer.parseInt(ngay[2]));
		c2.add(Calendar.DATE, 1);

		String month = Integer.toString(c2.get(Calendar.MONTH) + 1);
		if ((c2.get(Calendar.MONTH) + 1) < 10)
		{
			month = "0" + Integer.toString(c2.get(Calendar.MONTH) + 1);
		}
		String date = Integer.toString(c2.get(Calendar.DATE));
		if ((c2.get(Calendar.DATE)) < 10)
		{
			date = "0" + Integer.toString(c2.get(Calendar.DATE));
		}

		System.out.println("ngay chon:" + Integer.toString(c2.get(Calendar.YEAR)) + "-" + month + "-" + date);
		return Integer.toString(c2.get(Calendar.YEAR)) + "-" + month + "-" + date;
	}

	public ResultSet getNppRs()
	{
		return nppRs;
	}
	public void setNppRs(ResultSet nppRs)
	{
		this.nppRs = nppRs;
	}
	public String getUserId()
	{
		return userId;
	}
	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public void DBClose(){
		try{
			if(this.vung != null) this.vung.close();
			if(this.kv != null) this.kv.close();
			if(this.npp != null) this.npp.close();
			if(this.db!=null){
				this.db.shutDown();
			}
		}catch(Exception e){}
	}
	@Override
	public void setNgay(String ngay) {
		this.ngay = ngay;
	}
	@Override
	public String getNgay() {
		return this.ngay;
	}
	
	private String getDateTime() 
	{
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date date = new Date();
		return dateFormat.format(date);
	}
}
