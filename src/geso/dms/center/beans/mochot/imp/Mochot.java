package geso.dms.center.beans.mochot.imp;
import geso.dms.center.beans.mochot.*;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;

public class Mochot implements IMochot{
	ResultSet ddkdlist;
	String ddkdId;
	String id; //ma don hang
	String dhId;
	ResultSet nppList;
	ResultSet donhangList;
	ResultSet gsbhList;
	String gsbhId;
	String smartId;
	String khTen;
	ResultSet khlist;
	String khId;
	String vungId;
	String kvId;
	String kenhId;
	String nppId;
	String msg;
	String ngay;
	String tungay;
    String denngay;
	ResultSet vung;
	ResultSet kv;
	ResultSet kenh;
	ResultSet npp;
	ResultSet nppRs;
	String userId;
	dbutils db;

	public Mochot(){
		this.vungId = "";
		this.kenhId ="";
		this.kvId = "";
		this.nppId = "";
		this.msg = "";
		this.ngay = "";
		this.id = id;
		this.khId = "";
		this.ddkdId = "";
		this.gsbhId = "";
		this.nppId = "";
		 this.tungay="";
    	 this.denngay="";
		this.msg = "";
		this.khTen = "";
		this.smartId = "";
		
		
		this.dhId = "";
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
	
	public ResultSet getGsbhList()
	{	
		return this.gsbhList;
	}
	
	public void setNppList(ResultSet nppList) 
	{
		this.nppList = nppList;
	}
	
	public ResultSet getNppList()
	{	
		return this.nppList;
	}
	
	public String getSmartId() 
	{		
		return this.smartId;
	}

	public void setSmartId(String smartId) 
	{
		this.smartId = smartId;		
	}

	public String getKhTen() 
	{		
		return this.khTen;
	}

	public void setKhTen(String khTen) 
	{
		this.khTen = khTen;		
	}
	
	public void setGsbhList(ResultSet gsbhList) 
	{
		this.gsbhList = gsbhList;
	}
	
	public void setDonhangList(ResultSet donhangList) 
	{
		this.donhangList = donhangList;
	}
	
	public ResultSet getDonhangList()
	{	
		return this.donhangList;
	}
	
	public String getDonhangId() 
	{
		return this.dhId;
	}

	public void setDonhangId(String dhId) 
	{
		this.dhId = dhId;
	}
	

	public String getGsbhId() 
	{
		return this.gsbhId;
	}

	public void setGsbhId(String gsbhId) 
	{
		this.gsbhId = gsbhId;
	}

	public ResultSet getKhList() 
	{
		return this.khlist;
	}

	public void setKhList(ResultSet khlist) 
	{
		this.khlist = khlist;
	}
	
	public ResultSet getDdkdList() 
	{	
		return this.ddkdlist;
	}
	
	public void setDdkdList(ResultSet ddkdList)
	{
		this.ddkdlist = ddkdList;		
	}
	
	public String getDdkdId() 
	{		
		return this.ddkdId;
	}

	public void setDdkdId(String ddkdId) 
	{
		this.ddkdId = ddkdId;	
	}
	
	public String getKhId() 
	{	
		return this.khId;
	}

	public void setKhId(String khId) 
	{
		this.khId = khId;
	}
	
	public void setNppId(String nppId){
		this.nppId = nppId;
	}

	public String getNppId(){
		return this.nppId;
	}

	
	public void settungay(String tungay) {
		
		this.tungay = tungay;
	}

	
	public String gettungay() {
		
		return this.tungay;
	}

	
	public void setdenngay(String denngay) {
		
		this.denngay = denngay;
	}

	
	public String getdenngay() {
		
		return this.denngay;
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
		String kenh;
		vung = "SELECT PK_SEQ AS VUNGID, TEN FROM VUNG";
		this.vung = this.db.get(vung);
		//System.out.println(vung);
		kv ="SELECT PK_SEQ AS KVID, TEN FROM KHUVUC ";
		this.kv = db.get(kv);
		npp = "SELECT PK_SEQ AS NPPID, TEN FROM NHAPHANPHOI ";
		this.npp = db.get(npp);
		kenh = "SELECT PK_SEQ AS KENHID, TEN FROM KenhBANHAng ";
		this.kenh = db.get(kenh);
		if(this.vungId.length() > 0){

			kv = "SELECT PK_SEQ AS KVID, TEN FROM KHUVUC WHERE VUNG_FK = '" + this.vungId + "'";			
			this.kv = db.get(kv);
			
			npp = "SELECT PK_SEQ AS NPPID, TEN FROM NHAPHANPHOI WHERE KHUVUC_FK in (select pk_seq from khuvuc where vung_fk =  " + this.vungId + ") ";
			this.npp = db.get(npp);

		}
		if(this.kvId.length() > 0){
			npp = "SELECT PK_SEQ AS NPPID, TEN FROM NHAPHANPHOI WHERE KHUVUC_FK = '" + this.kvId + "'";
			this.npp = db.get(npp);
		}
		
		if(this.kenhId.length() > 0){
			npp = "SELECT PK_SEQ AS KENHID, TEN FROM KenhBanHang WHERE pk_seq = '" + this.kenhId + "'";
			this.npp = db.get(npp);
		}

		String query;
		if(this.nppId.length() > 0)
		{
		query = "select a.pk_seq as khId, a.ten as khTen, a.diachi, ISNULL(b.CHIETKHAU,0) as chietkhau ";
		query = query + "from KHACHHANG a left join MUCCHIETKHAU b on a.CHIETKHAU_FK = b.PK_SEQ where a.npp_fk = '" + this.nppId + "' ";
		}
		else 
		{
		query = "select a.pk_seq as khId, a.ten as khTen, a.diachi, ISNULL(b.CHIETKHAU,0) as chietkhau ";
		query = query + "from KHACHHANG a left join MUCCHIETKHAU b on a.CHIETKHAU_FK = b.PK_SEQ  ";
		}
//		if(this.tbhId.length() > 0)
//			query = query + "and a.PK_SEQ in (select distinct khachhang_fk from KHACHHANG_TUYENBH where TBH_FK = '" + this.tbhId + "')";
		
		//System.out.println("Tao Khach Hang list:" + query);
		this.khlist = db.get(query);
		
		if(this.nppId.length() > 0)
		{
		String sql ="select distinct a.ten  ,a.pk_seq from giamsatbanhang a inner join NHAPP_GIAMSATBH b on a.pk_seq = b.gsbh_fk where  npp_fk ='"+ this.nppId +"'";
		this.gsbhList = db.get(sql);
		
			if(this.gsbhId.length() > 0)
			{
				query = "select ten as ddkdTen, pk_seq as ddkdId from daidienkinhdoanh where npp_fk ='"+ this.nppId +" ' and pk_seq in (select ddkd_fk from ddkd_gsbh where gsbh_fk in (select gsbh_fk from nhapp_giamsatbh where  gsbh_fk ='"+ this.gsbhId +"' and npp_fk = '" + this.nppId + "') )";
				this.ddkdlist = db.get(query);
			}
			else 
			{
				 query = "select ten as ddkdTen, pk_seq as ddkdId from daidienkinhdoanh where npp_fk ='"+ this.nppId +" ' ";
				this.ddkdlist = db.get(query);
			}
		}
		else
		{
			String sql ="select distinct a.ten, a.pk_seq from giamsatbanhang a inner join NHAPP_GIAMSATBH b on a.pk_seq = b.gsbh_fk ";
			this.gsbhList = db.get(sql);
			
			if(this.gsbhId.length() > 0)
			{
				query = "select distinct ten as ddkdTen, pk_seq as ddkdId from daidienkinhdoanh where  pk_seq in (select ddkd_fk from ddkd_gsbh where gsbh_fk in (select gsbh_fk from nhapp_giamsatbh where  gsbh_fk ='"+ this.gsbhId +"' ) )";
				this.ddkdlist = db.get(query);
			}
			else 
			{
				query = "select ten as ddkdTen, pk_seq as ddkdId from daidienkinhdoanh  ";
				this.ddkdlist = db.get(query);
			}
		}
		
		
		
		
		query="select a.pk_Seq,a.ma,a.ten, a.diachi, "+
				"\n		isnull( (select max(ngayks) from KHOASONGAY where NPP_FK=a.PK_SEQ) ,N'Chưa có ngày KS')as NgayKS  "+
				"\n	from NHAPHANPHOI a " +
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
		
		String qqs = "select pk_seq as dhId  from donhang where npp_fk = '"+nppId+"' and ngaynhap >='"+tungay+"'   and ngaynhap <='"+denngay+"'  and trangthai = 1";
		System.out.println("vao day k " + qqs);
		this.donhangList = db.get(qqs);
		qqs = "select ten as nppTen, pk_seq as nppId from nhaphanphoi ";
		System.out.println("vao day k " + qqs);
		this.nppList = db.get(qqs);

	}

	public String MochotDH()
	{
		String query = "SELECT ngayks FROM KHOASONGAY WHERE NPP_FK = '"+ this.nppId +"' and ngayks >= (select ngaynhap from donhang where pk_seq = '"+this.dhId+"')"
				+ " ORDER BY NGAYKS DESC";
		System.out.println("MOKS: "+query);
		String ngayks = "";
		ResultSet rs = this.db.get(query);
		try
		{
			if(rs.next()){
				ngayks = rs.getString("NGAYKS");				
			}
			rs.close();

			String msg = "";
			String msg1= "";
			if(msg.trim().length() <= 0 && ngayks.length() <= 0)
			{
				this.db.getConnection().setAutoCommit(false);

				query = "update donhang set trangthai = 0 WHERE  pk_seq = '" + this.dhId + "' and trangthai=1 " ;
				if(db.updateReturnInt(query)<=0)
				{
					geso.dms.center.util.Utility.rollback_throw_exception(db);
					return "Không thể mở chốt đơn hàng trạng thái đơn hàng không hợp lệ " ;
				}
				
				query = "insert into trangthaidonhang (donhang_fk, nghiepvu, nguoisua, ngaysua)"
						+ "\n select "+this.dhId+", N'mở chốt', '" + this.userId + "', dbo.GetLocalDate(DEFAULT) ";
				if(db.updateReturnInt(query)<=0)
				{
					geso.dms.center.util.Utility.rollback_throw_exception(db);
					return "Không thể mở chốt đơn hàng: " + query;
				}
				
				query = "delete phieuxuatkho_donhang WHERE donhang_fk = '"+this.dhId+"' ";
				if(db.updateReturnInt(query)<=0)
				{
					geso.dms.center.util.Utility.rollback_throw_exception(db);
					return "Không thể mở chốt đơn hàng: " + query;
				}
				
				
				geso.dms.center.util.Utility util = new geso.dms.center.util.Utility();
				query="select c.ngaynhap,c.npp_fk,c.kho_fk as khoId, c.kbh_fk as kbhId, b.pk_seq as spId, sum(a.soluong) as soluong from donhang_sanpham a "
						+ " inner join sanpham b on a.sanpham_fk = b.pk_seq "
						+ " inner join donhang c on a.donhang_fk = c.pk_seq  "
						+ " where c.trangthai = 0 and a.donhang_fk ="+this.dhId+" group by c.kho_fk, c.kbh_fk, b.pk_seq,c.ngaynhap,c.npp_fk";
				rs=db.get(query);
				while(rs.next())
				{
					String _kho_fk=rs.getString("khoId");
			    	String _kbh_fk=rs.getString("kbhId");
			    	String _sanpham_fk=rs.getString("spId");
			    	String _npp_fk=rs.getString("npp_fk");
			    	String _ngaynhap=rs.getString("ngaynhap");
			    	Double _soluong = rs.getDouble("soluong");
			    	msg=util.Update_NPP_Kho_Sp(_ngaynhap,this.dhId, "mở chốt don hang"+dhId, db, _kho_fk, _sanpham_fk, _npp_fk, _kbh_fk,  _soluong, _soluong, 0 , 0.0);
			    	if(msg.length()>0)
			    	{
			    		db.getConnection().rollback();
						db.getConnection().setAutoCommit(true);
						db.shutDown();
			    		return msg;
			    	}	
				}
				
				query=	 " SELECT DH.NGAYNHAP,SP.SANPHAM_FK,DH.KHO_FK,DH.NPP_FK,DH.KBH_FK,SP.SOLUONG, a.ten as tensp, sp.ngaynhapkho " 
						+ "\n from DONHANG dh inner join DONHANG_SANPHAM_chitiet sp on dh.PK_SEQ = sp.DONHANG_FK    "
						+ "\n inner join sanpham a on a.pk_seq = sp.sanpham_fk  "
						+ "\n where dh.PK_SEQ = "+ this.dhId+ " and dh.trangthai=0";
						
					System.out.println("UPDATE NPP KHO: " + query  );
				  ResultSet  ckRs = db.get(query);
				    while(ckRs.next())
				    {
				    	String kho_fk=ckRs.getString("kho_fk");
						String _npp_fk=ckRs.getString("npp_fk");	
						String _kbh_fk =ckRs.getString("kbh_fk");
						String sanpham_fk=ckRs.getString("sanpham_fk");
						String ngaynhap  =ckRs.getString("ngaynhap");
						String tensp  =ckRs.getString("tensp");
						Double soluong = ckRs.getDouble("soluong");
				    	String ngaynhapkho=ckRs.getString("NGAYNHAPKHO");    	
							 msg1=util.Update_NPP_Kho_Sp_Chitiet(ngaynhap, "mở chốt DHID: "+this.dhId ,  db, kho_fk, sanpham_fk, _npp_fk, _kbh_fk, ngaynhapkho, soluong,soluong, 0, 0, 0);
							if (msg1.length()> 0) 
							{
								db.getConnection().rollback();
								db.getConnection().setAutoCommit(true);
								db.shutDown();
					    		return msg1;
							}
				    }	
					
				  // hang khuyen mai
				    
				    query="	select c.ngaynhap,c.npp_fk,c.kho_fk as khoId, c.kbh_fk as kbhId, b.pk_seq as spId, sum(a.soluong) as soluong from donhang_ctkm_trakm a \n"+
				    	  "	 inner join sanpham b on a.SPMA = b.Ma \n"+
				    	  "	 inner join donhang c on a.DONHANGID = c.pk_seq   \n"+
				          "		 where c.trangthai =0 and a.DONHANGID ="+this.dhId+" \n"+
				    	  "	  group by c.kho_fk, c.kbh_fk, b.pk_seq,c.ngaynhap,c.npp_fk";
					rs=db.get(query);
					while(rs.next())
					{
						String _kho_fk=rs.getString("khoId");
				    	String _kbh_fk=rs.getString("kbhId");
				    	String _sanpham_fk=rs.getString("spId");
				    	String _npp_fk=rs.getString("npp_fk");
				    	String _ngaynhap=rs.getString("ngaynhap");
				    	Double _soluong = rs.getDouble("soluong");
				    	msg=util.Update_NPP_Kho_Sp(_ngaynhap,this.dhId, "mở chốt don hang km ", db, _kho_fk, _sanpham_fk, _npp_fk, _kbh_fk,  _soluong,_soluong , 0, 0.0);
				    	if(msg.length()>0)
				    	{
				    		db.getConnection().rollback();
							db.getConnection().setAutoCommit(true);
							db.shutDown();
				    		return msg;
				    	}	
					}
				    
					
					query=  " SELECT DH.NGAYNHAP,a.PK_SEQ as sanpham_fk,DH.KHO_FK,DH.NPP_FK,DH.KBH_FK,SP.SOLUONG, a.ten as tensp, sp.ngaynhapkho \n"+
							"  from DONHANG dh inner join DONHANG_CTKM_TRAKM_CHITIET sp on dh.PK_SEQ = sp.DONHANGID     \n"+
							" inner join sanpham a on a.MA = sp.SPMA \n"+
							" where dh.PK_SEQ = "+this.dhId+" and dh.trangthai= 0 \n";
							
						System.out.println("UPDATE NPP KHO: " + query  );
					    ckRs = db.get(query);
					    while(ckRs.next())
					    {
					    	String kho_fk=ckRs.getString("kho_fk");
							String _npp_fk=ckRs.getString("npp_fk");	
							String _kbh_fk =ckRs.getString("kbh_fk");
							String sanpham_fk=ckRs.getString("sanpham_fk");
							String ngaynhap  =ckRs.getString("ngaynhap");
							String tensp  =ckRs.getString("tensp");
							Double soluong = ckRs.getDouble("soluong");
					    	String ngaynhapkho=ckRs.getString("NGAYNHAPKHO");    	
								 msg1=util.Update_NPP_Kho_Sp_Chitiet(ngaynhap, "mo chot DHID: km  "+this.dhId ,  db, kho_fk, sanpham_fk, _npp_fk, _kbh_fk, ngaynhapkho, soluong,soluong, 0, 0, 0);
								if (msg1.length()> 0) 
								{
									db.getConnection().rollback();
									db.getConnection().setAutoCommit(true);
									db.shutDown();
						    		return msg1;
								}
					    }
					
					    ckRs.close();


				this.db.getConnection().commit();
				this.db.getConnection().setAutoCommit(true);

				return "Mở chốt đơn hàng thành công";
			}
			else
			{
				return "Không thể mở chốt đơn hàng do ngày đơn hàng không hợp lệ!";
			}
		}
		catch(Exception e)
		{	
			geso.dms.center.util.Utility.rollback_throw_exception(this.db);
			return "Không thể mở chốt đơn hàng: " + e.getMessage();
		}
	}

	public String HuyDH()
	{
		String query = "SELECT ngayks FROM KHOASONGAY WHERE NPP_FK = '"+ this.nppId +"' and ngayks >= (select ngaynhap from donhang where pk_seq = '"+this.dhId+"')"
				+ " ORDER BY NGAYKS DESC";
		System.out.println("MOKS: "+query);
		String ngayks = "";
		ResultSet rs = this.db.get(query);
		try
		{
			if(rs.next()){
				ngayks = rs.getString("NGAYKS");				
			}
			rs.close();

			String msg = "";
			String msg1= "";
			if(msg.trim().length() <= 0 && ngayks.length() <= 0)
			{
				this.db.getConnection().setAutoCommit(false);

				query = "update donhang set trangthai = 2 WHERE NPP_FK = '" + this.nppId + "' and pk_seq = '" + this.dhId + "' and trangthai=1 ";
				if(db.updateReturnInt(query)<=0)
				{
					geso.dms.center.util.Utility.rollback_throw_exception(db);
					return "Trạng thái đơn hàng không hợp lệ ";
				}
				query = "insert into trangthaidonhang (donhang_fk, nghiepvu, nguoisua, ngaysua)"
						+ "\n select "+this.dhId+", N'hủy đơn chốt', '" + this.userId + "', dbo.GetLocalDate(DEFAULT) ";
				if(db.updateReturnInt(query)<=0)
				{
					geso.dms.center.util.Utility.rollback_throw_exception(db);
					return "Không thể mở chốt đơn hàng: " + query;
				}
				
				geso.dms.center.util.Utility util = new geso.dms.center.util.Utility();
				query="select c.ngaynhap,c.npp_fk,c.kho_fk as khoId, c.kbh_fk as kbhId, b.pk_seq as spId, sum(a.soluong) as soluong from donhang_sanpham a "
						+ " inner join sanpham b on a.sanpham_fk = b.pk_seq "
						+ " inner join donhang c on a.donhang_fk = c.pk_seq  "
						+ " where c.trangthai = 2 and a.donhang_fk ="+this.dhId+" group by c.kho_fk, c.kbh_fk, b.pk_seq,c.ngaynhap,c.npp_fk";
				rs=db.get(query);
				while(rs.next())
				{
					String _kho_fk=rs.getString("khoId");
			    	String _kbh_fk=rs.getString("kbhId");
			    	String _sanpham_fk=rs.getString("spId");
			    	String _npp_fk=rs.getString("npp_fk");
			    	String _ngaynhap=rs.getString("ngaynhap");
			    	Double _soluong = rs.getDouble("soluong");
			    	msg=util.Update_NPP_Kho_Sp(_ngaynhap,this.dhId, "huy don hang"+dhId, db, _kho_fk, _sanpham_fk, _npp_fk, _kbh_fk,  _soluong, 0 , _soluong, 0.0);
			    	if(msg.length()>0)
			    	{
			    		db.getConnection().rollback();
						db.getConnection().setAutoCommit(true);
						db.shutDown();
			    		return msg;
			    	}	
				}
				
				query=	 " SELECT DH.NGAYNHAP,SP.SANPHAM_FK,DH.KHO_FK,DH.NPP_FK,DH.KBH_FK,SP.SOLUONG, a.ten as tensp, sp.ngaynhapkho " 
						+ "\n from DONHANG dh inner join DONHANG_SANPHAM_chitiet sp on dh.PK_SEQ = sp.DONHANG_FK    "
						+ "\n inner join sanpham a on a.pk_seq = sp.sanpham_fk  "
						+ "\n where dh.PK_SEQ = "+ this.dhId+ " and dh.trangthai=2";
						
					System.out.println("UPDATE NPP KHO: " + query  );
				  ResultSet  ckRs = db.get(query);
				    while(ckRs.next())
				    {
				    	String kho_fk=ckRs.getString("kho_fk");
						String _npp_fk=ckRs.getString("npp_fk");	
						String _kbh_fk =ckRs.getString("kbh_fk");
						String sanpham_fk=ckRs.getString("sanpham_fk");
						String ngaynhap  =ckRs.getString("ngaynhap");
						String tensp  =ckRs.getString("tensp");
						Double soluong = ckRs.getDouble("soluong");
				    	String ngaynhapkho=ckRs.getString("NGAYNHAPKHO");    	
							 msg1=util.Update_NPP_Kho_Sp_Chitiet(ngaynhap, "huy  DHID: "+this.dhId ,  db, kho_fk, sanpham_fk, _npp_fk, _kbh_fk, ngaynhapkho, soluong, 0,soluong, 0, 0);
							if (msg1.length()> 0) 
							{
								db.getConnection().rollback();
								db.getConnection().setAutoCommit(true);
								db.shutDown();
					    		return msg1;
							}
				    }	
					
				  // hang khuyen mai
				    
				    query="	select c.ngaynhap,c.npp_fk,c.kho_fk as khoId, c.kbh_fk as kbhId, b.pk_seq as spId, sum(a.soluong) as soluong from donhang_ctkm_trakm a \n"+
				    	  "	 inner join sanpham b on a.SPMA = b.Ma \n"+
				    	  "	 inner join donhang c on a.DONHANGID = c.pk_seq   \n"+
				          "		 where c.trangthai = 0 and a.DONHANGID ="+this.dhId+" \n"+
				    	  "	  group by c.kho_fk, c.kbh_fk, b.pk_seq,c.ngaynhap,c.npp_fk";
					rs=db.get(query);
					while(rs.next())
					{
						String _kho_fk=rs.getString("khoId");
				    	String _kbh_fk=rs.getString("kbhId");
				    	String _sanpham_fk=rs.getString("spId");
				    	String _npp_fk=rs.getString("npp_fk");
				    	String _ngaynhap=rs.getString("ngaynhap");
				    	Double _soluong = rs.getDouble("soluong");
				    	msg=util.Update_NPP_Kho_Sp(_ngaynhap,this.dhId, "huy don hang km ", db, _kho_fk, _sanpham_fk, _npp_fk, _kbh_fk,  _soluong, 0,_soluong , 0.0);
				    	if(msg.length()>0)
				    	{
				    		db.getConnection().rollback();
							db.getConnection().setAutoCommit(true);
							db.shutDown();
				    		return msg;
				    	}	
					}
				    
					
					query=  " SELECT DH.NGAYNHAP,a.PK_SEQ as sanpham_fk,DH.KHO_FK,DH.NPP_FK,DH.KBH_FK,SP.SOLUONG, a.ten as tensp, sp.ngaynhapkho \n"+
							"  from DONHANG dh inner join DONHANG_CTKM_TRAKM_CHITIET sp on dh.PK_SEQ = sp.DONHANGID     \n"+
							" inner join sanpham a on a.MA = sp.SPMA \n"+
							" where dh.PK_SEQ = "+this.dhId+" and dh.trangthai=0\n";
							
						System.out.println("UPDATE NPP KHO: " + query  );
					    ckRs = db.get(query);
					    while(ckRs.next())
					    {
					    	String kho_fk=ckRs.getString("kho_fk");
							String _npp_fk=ckRs.getString("npp_fk");	
							String _kbh_fk =ckRs.getString("kbh_fk");
							String sanpham_fk=ckRs.getString("sanpham_fk");
							String ngaynhap  =ckRs.getString("ngaynhap");
							String tensp  =ckRs.getString("tensp");
							Double soluong = ckRs.getDouble("soluong");
					    	String ngaynhapkho=ckRs.getString("NGAYNHAPKHO");    	
								 msg1=util.Update_NPP_Kho_Sp_Chitiet(ngaynhap, "huy DHID: km  "+this.dhId ,  db, kho_fk, sanpham_fk, _npp_fk, _kbh_fk, ngaynhapkho, soluong, 0,soluong, 0, 0);
								if (msg1.length()> 0) 
								{
									db.getConnection().rollback();
									db.getConnection().setAutoCommit(true);
									db.shutDown();
						    		return msg1;
								}
					    }
					
					    ckRs.close();


				this.db.getConnection().commit();
				this.db.getConnection().setAutoCommit(true);

				return "Hủy đơn hàng thành công";
			}
			else
			{
				return "Không thể hủy đơn hàng do ngày đơn hàng không hợp lệ!";
			}
		}
		catch(Exception e)
		{	
			geso.dms.center.util.Utility.rollback_throw_exception(this.db);
			return "Không thể hủy đơn hàng: " + e.getMessage();
		}
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
