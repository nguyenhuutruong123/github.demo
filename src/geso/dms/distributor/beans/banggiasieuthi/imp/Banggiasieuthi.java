package geso.dms.distributor.beans.banggiasieuthi.imp;

import geso.dms.center.db.sql.dbutils;
import geso.dms.distributor.beans.banggiasieuthi.IBanggiasieuthi;
import geso.dms.distributor.beans.banggiasieuthi.IKhachHang_Bgst;
import geso.dms.distributor.beans.banggiasieuthi.ISanpham;
import geso.dms.distributor.beans.banggiasieuthi.imp.Sanpham;

import geso.dms.distributor.db.sql.*;
import geso.dms.distributor.util.Utility;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class Banggiasieuthi implements IBanggiasieuthi
{
	private static final long serialVersionUID = -9217977546733610214L;
	String userId;
	String id;
	String tenbangia;
	String nhacungcap;
	String donvikinhdoanh;
	String ngaytao;
	String nguoitao;
	String ngaysua;
	String nguoisua;
	String nccId;
	String dvkdId;
	String msg;
	String npplist = "";
	ResultSet ncc;
	ResultSet dvkd;
	
	List<IKhachHang_Bgst> listkh_bgstList =new ArrayList<IKhachHang_Bgst>(); 
Hashtable< String, String> htb_dskh_has_bgst=new Hashtable<String, String>();
	ResultSet rs_khachhang_bgst;
	ResultSet rs_khachhang_new;
	List<ISanpham> splist;
	
	String nppId;
	String nppTen;
	String sitecode;
	dbutils db;
	ResultSet rshch;
	ResultSet rslch;
	ResultSet rsvtch;
	ResultSet rsquanhuyen;
	ResultSet rstinhthanh;
	ResultSet rs_daidienkinhdoanh;
	String hchid="";
	String lchid="";
	String vtchId="";
	String quanhuyenId="";
	String tinhthanhId="";
	String ddkdId="";
	
	public Banggiasieuthi(String[] param)
	{
		this.id = param[0];
		this.tenbangia = param[1];
		this.nhacungcap = param[2];
		this.donvikinhdoanh = param[3];
		this.ngaytao = param[4];
		this.nguoitao = param[5];
		this.ngaysua = param[6];
		this.nguoisua = param[7];
		this.nccId = param[8];
		this.dvkdId = param[9];
		this.msg = "";
		this.db = new dbutils();	
	}
	
	public Banggiasieuthi(String id)
	{
		this.id = id;
		this.tenbangia = "";
		this.nhacungcap = "";
		this.donvikinhdoanh = "";
		this.ngaytao = "";
		this.nguoitao = "";
		this.ngaysua = "";
		this.nguoisua = "";
		this.nccId = "";
		this.dvkdId = "";
		this.msg = "";
		this.db = new dbutils();	
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
	
	public String getTenbanggia()
	{
		return this.tenbangia;
	}
	
	public void setTenbanggia(String tenbanggia)
	{
		this.tenbangia = tenbanggia;
	}
	
	public String getnhacungcap()
	{
		return this.nhacungcap;
	}
	
	public void setNhacungcap(String nhacungcap)
	{
		this.nhacungcap = nhacungcap;
	}
	
	public String getDonvikinhdoanh()
	{
		return this.donvikinhdoanh;
	}
	
	public void setDonvikinhdoanh(String donvikinhdoanh)
	{
		this.donvikinhdoanh = donvikinhdoanh;
	}
	
	public String getNgaytao()
	{
		return this.ngaytao;
	}
	
	public void setNgaytao(String ngaytao)
	{
		this.ngaytao = ngaytao;
	}
	
	public String getNguoitao()
	{
		return this.nguoitao;
	}
	
	public void setNguoitao(String nguoitao)
	{
		this.nguoitao = nguoitao;
	}
	
	public String getNgaysua()
	{
		return this.ngaysua;
	}
	
	public void setNgaysua(String ngaysua)
	{
		this.ngaysua = ngaysua;
	}
	
	public String getNguoisua()
	{
		return this.nguoisua;
	}
	
	public void setNguoisua(String nguoisua)
	{
		this.nguoisua = nguoisua;
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
	
	public ResultSet getNcc()
	{
		return this.ncc;
	}
	
	public void setNcc(ResultSet ncc)
	{
		this.ncc = ncc;
	}
	
	public ResultSet getDvkd()
	{
		return this.dvkd;
	}
	
	public void setDvkd(ResultSet dvkd)
	{
		this.dvkd = dvkd;
	}
	
	public String getNhacungcap() 
	{		
		return this.nhacungcap;
	}
	
	public String getNppId() 
	{
		return this.nppId;
	}

	public void setNppId(String nppId) 
	{
		this.nppId = nppId;
	}
	
	public String getNppTen() 
	{
		return this.nppTen;
	}

	public void setNppTen(String nppTen) 
	{
		this.nppTen = nppTen;
	}
	
	public String getSitecode() 
	{
		return this.sitecode;
	}

	public void setSitecode(String sitecode) 
	{
		this.sitecode = sitecode;
	}
	
	public String getMessage() 
	{
		return this.msg;
	}

	public void setMessage(String msg) 
	{
		this.msg = msg;
	}
	
	private void getNppInfo()
	{
		
		geso.dms.distributor.util.Utility util=new geso.dms.distributor.util.Utility();
		this.nppId=util.getIdNhapp(this.userId);
		this.nppTen=util.getTenNhaPP();
		this.sitecode=util.getSitecode();
	}
	
	public boolean CreateBgst(String[] spId, String[] spGiasieuthi) 
	{
		this.ngaytao = getDateTime();
		this.nguoitao = this.userId;
		try{
		// Update table "Don Hang"
			this.db.getConnection().setAutoCommit(false);
				String query = "insert into Banggiasieuthi( ten, ngaytao, ngaysua, nguoitao, nguoisua, dvkd_fk, npp_fk) " ;
				query = query + "values( N'" + this.tenbangia + "','" + this.ngaytao + "','" + this.ngaytao + "','" + this.nguoitao +"','" + this.nguoitao +"','" + this.dvkdId + "','" + this.nppId + "')";
				if(!this.db.update(query))
				{
					geso.dms.center.util.Utility.rollback_throw_exception(this.db);
					this.msg = "Khong the tao moi 'Bang Gia Sieu Thi' , " + query;
					return false; 
				}
		
					query = "select IDENT_CURRENT('Banggiasieuthi') as bgsieuthiId";
					ResultSet rs = this.db.get(query);
		
		
					rs.next();
			this.id = rs.getString("bgsieuthiId");

			if(spId != null)
			{
				int m = 0;
				while(m < spId.length)
				{
					if(spGiasieuthi[m].length() > 0) //chi them nhung san pham co gia
					{
						
						String sql = "insert into banggiast_sanpham(bgst_fk, sanpham_fk, giasieuthi) values('"+ this.id +"','" + spId[m] + "','" + spGiasieuthi[m] + "')";
						if(!db.update(sql))
						{
							geso.dms.center.util.Utility.rollback_throw_exception(db);
							this.msg = "Loi khi cap nhat bang banggiast_sanpham, " + sql;
							return false; 
						}
						
					}
					m++;
				}
			}
			rs.close();
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		}catch(Exception e){
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			this.msg = "Loi khi cap nhat bang banggiast_sanpham, " + e.toString();
			return false; 
		}
		return true;
	}
	
	public boolean UpdateBgst(String[] spId, String[] spGiasieuthi) 
	{		
		this.ngaysua = getDateTime();
		this.nguoisua = this.userId;
		try{
		this.db.getConnection().setAutoCommit(false);
		
		// Update table "Don Hang"
		String query = "update Banggiasieuthi set ten=N'" + this.tenbangia + "', ngaysua='" + this.ngaysua + "', nguoisua='" + this.nguoisua + "' where pk_seq='" + this.id + "'";
		if(!this.db.update(query))
		{
			geso.dms.center.util.Utility.rollback_throw_exception(this.db);
			this.msg = "Khong the cap nhat 'Bang Gia Sieu Thi' , " + query;
			return false; 
		}

		if(spId != null)
		{
			query = "delete from banggiast_sanpham where bgst_fk= '" + this.id + "'" ;
			if(!this.db.update(query)){
				geso.dms.center.util.Utility.rollback_throw_exception(this.db);
				this.msg = "Khong the cap nhat 'Bang Gia Sieu Thi' , " + query;
				return false; 
			}

			int m = 0;
			while(m < spId.length)
			{
				if(spGiasieuthi[m].length() > 0) //chi them nhung san pham co gia
				{


					String sql = "insert into banggiast_sanpham(bgst_fk, sanpham_fk, giasieuthi) values('" + this.id + "','" + spId[m] + "','" + spGiasieuthi[m] + "')";
					if(!this.db.update(sql))
					{
						geso.dms.center.util.Utility.rollback_throw_exception(this.db);
						this.msg = "Loi khi cap nhat bang banggiast_sanpham, " + sql;
						return false; 
					}

				}
				m++;
			}
		}
		this.db.getConnection().commit();
		this.db.getConnection().setAutoCommit(true);
		}catch(Exception er){
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			this.msg="Vui Long Dang Xuat De Thu Lai,Neu Khong Duoc Vui Long Voi Admin Tren Trung Tam De Duoc Giup Do. Loi :"+er.toString();
			return false;
		}
		return true;
	}
		
	public void init() 
	{
		this.getNppInfo();
	//	ResultSet rs =  db.get("select * from vwbanggiasieuthi where nppId='" + this.nppId + "' and bgstId='" + this.id + "'");
		ResultSet rs =  db.get("select * from vwbanggiasieuthi where nppId='" + this.nppId + "' and bgstId='" + this.id + "'");
		try
        {
            rs.next();        	
            this.id = rs.getString("bgstId");
			this.tenbangia = rs.getString("bgstTen");
			this.dvkdId = rs.getString("dvkdId");			
			this.ngaytao = rs.getDate("ngaytao").toString();
			this.nguoitao = rs.getString("nguoitao");
			this.ngaysua = rs.getDate("ngaysua").toString();
			this.nguoisua = rs.getString("nguoisua");					
			rs.close();
       	}
        catch(Exception e){
        	
        }
        this.createDonvikd();
        this.createNhacungcap();
        this.createSanphamList();
        this.createkhachhang_List();
        this.createKhachhang_new();
        this.setStringkh();
        this.setKhCoBangGia();
	}
	private void setKhCoBangGia(){
	try{	
		String sql="select dvkd_fk from banggiasieuthi where pk_Seq='"+this.id+"'";
		
		ResultSet rs=db.get(sql);
			if(rs.next()){
				this.dvkdId=rs.getString("dvkd_fk");
			}
			sql="select khachhang_fk from bgst_khachhang a inner join banggiasieuthi b on a.banggiasieuthi_fk=b.pk_Seq  where dvkd_fk='"+this.dvkdId+"' and banggiasieuthi_fk <>'"+this.id+"' ";
			rs=db.get(sql);		
			while(rs.next()){		
			htb_dskh_has_bgst.put(rs.getString(1), rs.getString(1));
			}
		if(rs!=null){
			rs.close();
		}
		}catch(Exception err){
			System.out.println("Loi nek :"+err.toString());
		}
	}
	private void setStringkh(){
		try{
		ResultSet rs= db.get("select khachhang_fk as pk_seq ,kh.ten,1  from bgst_khachhang a inner join khachhang kh on a.khachhang_fk=kh.pk_Seq where banggiasieuthi_fk='"+this.id+"'");

		while (rs.next()){
			if (npplist.length()==0){
				npplist = npplist + "'" + rs.getString("pk_seq") + "'";
			}else{
				npplist = npplist + ",'" +  rs.getString("pk_seq") + "'";
			}
		}
		String sql="select pk_seq,ten,0  from khachhang where trangthai='1' and kbh_fk='100002' and npp_fk='"+this.nppId+"' and pk_Seq not in (select khachhang_fk from bgst_khachhang where banggiasieuthi_fk ='"+this.id+"')";
		rs = db.get(sql);
		while (rs.next()){
			if (npplist.length()==0){
				npplist = npplist + "'" + rs.getString("pk_Seq") + "'";
			}else{
				npplist = npplist + ",'" +  rs.getString("pk_seq") + "'";
			}
		}
		if(rs!=null){
			rs.close();
		}
		}catch(Exception er){
			
		}
	}
	private void createKhachhang_new()
	{
		String sql="select pk_seq,ten,0  from khachhang where trangthai='1' and kbh_fk='100002' and npp_fk='"+this.nppId+"' and pk_Seq not in (select khachhang_fk from bgst_khachhang where banggiasieuthi_fk ='"+this.id+"' ) order by pk_Seq";
	
		this.rs_khachhang_new= db.get(sql);
		try{
			while (rs_khachhang_new.next()){
				IKhachHang_Bgst kh=new KhachHang_Bgst();
				kh.setCheck("0");
				kh.setBGST(this.id);
				kh.setTenKh(rs_khachhang_new.getString("ten"));
				kh.setIdKh(rs_khachhang_new.getString("pk_seq"));
				listkh_bgstList.add(kh);
			}
		}catch(Exception er){
			System.out.println("Error here : "+er.toString());
		}
		
		
	}
	private void createkhachhang_List()
	{
		this.rs_khachhang_bgst= db.get("select khachhang_fk as pk_seq ,kh.ten,1  from bgst_khachhang  a inner join khachhang kh on a.khachhang_fk=kh.pk_Seq where banggiasieuthi_fk='"+this.id+"'  order by kh.pk_Seq");
	try{
		while (rs_khachhang_bgst.next()){
			IKhachHang_Bgst kh=new KhachHang_Bgst();
			kh.setCheck("1");
			kh.setBGST(this.id);
			kh.setTenKh(rs_khachhang_bgst.getString("ten"));
			kh.setIdKh(rs_khachhang_bgst.getString("pk_seq"));
			 listkh_bgstList.add(kh);
		}
	}catch(Exception er){
		System.out.println("Error here : "+er.toString());
	}
		
	}
	private void createNhacungcap()
	{
		this.ncc = db.get("select pk_seq as nccId, ten as nccTen from nhacungcap ");
	}
	
	private void createDonvikd()
	{
		String sql = "select a.pk_seq as dvkdId, a.donvikinhdoanh as dvkdTenviettat, a.diengiai as dvkdTen from donvikinhdoanh a,Nhacungcap_dvkd b where a.pk_seq = b.dvkd_fk and a.trangthai='1' and b.checked ='1'";
		this.dvkd = this.db.get(sql);
	}
	private void createSanphamList()
	{ boolean daco = false;
		//lay san pham co gia trong bang gia
	  
	
	  String sql = "select bgst_sp.sanpham_fk as spId, sp.ma as spMa, sp.ten as spTen, bgst_sp.giasieuthi from banggiast_sanpham bgst_sp " +
	  		"inner join sanpham sp on bgst_sp.sanpham_fk = sp.pk_seq " ;
			sql = sql +	"where bgst_sp.bgst_fk ='" + this.id + "' and sp.dvkd_fk = '" + this.dvkdId.trim() + "' order by bgst_sp.sanpham_fk";
	
		ResultSet sp_bgst = this.db.get(sql);
		List<ISanpham> splist = new ArrayList<ISanpham>();
		if(sp_bgst != null)
		{
			String[] param = new String[5];
			ISanpham sp = null;	
			try 
			{
				while(sp_bgst.next())
				{
					param[0] = sp_bgst.getString("spId");
					param[1] = sp_bgst.getString("spMa");
					param[2] = sp_bgst.getString("spTen");
					param[3] ="0";
					param[4] = sp_bgst.getString("giasieuthi");
			
					sp = new Sanpham(param);
					splist.add(sp);
					daco = true;
				}
				sp_bgst.close();
			} 
			catch(Exception e) {}
		}
		if(daco==true)
		{
		
		//Lay danh sach san pham chua co gia trong bang gia
		String query = "select sp.pk_seq as spId,  sp.ma as spMa, sp.ten as spTen  " +
				" from sanpham sp " ;
		query = query + "where sp.dvkd_fk = '" + this.dvkdId + "' and sp.trangthai=1 and sp.pk_seq not in (select sanpham_fk from banggiast_sanpham";
		if(this.id.length() > 0)
			query = query + " where bgst_fk = '" + this.id  + "'";
		query = query + ") order by sp.pk_seq";
		
		ResultSet sp_List = db.get(query);
		if(sp_List != null)
		{
			String[] param = new String[5];
			ISanpham sp = null;	
			try 
			{
				while(sp_List.next())
				{
					param[0] = sp_List.getString("spId");
					param[1] = sp_List.getString("spMa");
					param[2] = sp_List.getString("spTen");
					param[3] = "0";
					param[4] = "0"; //set bang gia
			
					sp = new Sanpham(param);
					splist.add(sp);
				}
				sp_List.close();
			} 
			catch(Exception e) {}
		}
		}
		else
		{

			//Lay danh sach san pham chua co gia trong bang gia
			String query = "select sp.pk_seq as spId,  sp.ma as spMa, sp.ten as spTen from sanpham sp where  sp.trangthai=1 and sp.dvkd_fk = '" + this.dvkdId.trim() + "'";
			//System.out.println( "Lay Du lieu : " +query);
			ResultSet sp_List = db.get(query);
			if(sp_List != null)
			{
				String[] param = new String[5];
				ISanpham sp = null;	
				try 
				{
					while(sp_List.next())
					{
						param[0] = sp_List.getString("spId");
						param[1] = sp_List.getString("spMa");
						param[2] = sp_List.getString("spTen");
						param[3] = "0";
						param[4] = "0"; //set bang gia
				
						sp = new Sanpham(param);
						splist.add(sp);
					}
					sp_List.close();
				} 
				catch(Exception e) {}
			}
		}
		this.splist = splist;		
	}
	
	public void createRS() 
	{
		this.getNppInfo();
		this.createNhacungcap();
		this.createDonvikd();
		this.createSanphamList();
	}
	public List<ISanpham> getSpList() 
	{
		return this.splist;
	}

	public void setSpList(List<ISanpham> splist) 
	{
		this.splist = splist;
	}
	
	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	@Override
	public void DBclose() 
	{
		try 
		{
			if(!(ncc == null))	
				ncc.close();
			if(!(dvkd == null))
				dvkd.close();
			if(db != null)
				db.shutDown();
			if(listkh_bgstList!=null){
				listkh_bgstList.clear();				
			}
			if(htb_dskh_has_bgst!=null){
				htb_dskh_has_bgst.clear();
			}
			if(rs_khachhang_bgst!=null){
				rs_khachhang_bgst.close();
			}
			if(rs_khachhang_new!=null){
				rs_khachhang_new.close();
			}
			if(splist!=null){
				splist.clear();
			}
			if(rshch!=null){
				rshch.close();
			}
			if(rslch!=null){
				rslch.close();
			}
			if(rsvtch!=null){
				rsvtch.close();
			}
			if(rsquanhuyen!=null){
				rsquanhuyen.close();
			}
			if(rstinhthanh!=null){
				rstinhthanh.close();
			}
			if(rs_daidienkinhdoanh!=null){
				rs_daidienkinhdoanh.close();
			}
			
		} 
		catch(Exception e) {}
	}
	@Override
	public ResultSet getrskhachhang_bgst() {
		// TODO Auto-generated method stub
		return this.rs_khachhang_bgst;
	}

	@Override
	public ResultSet getrskhachhang_new() {
		// TODO Auto-generated method stub
		return this.rs_khachhang_new; 
	}
	
	public String getkhachhangString(){
		
		return npplist;
	}

	@Override
	public boolean update_bg_kh(HttpServletRequest request) {
		// TODO Auto-generated method stub
		dbutils cn=new dbutils();
		try{
		
			cn.getConnection().setAutoCommit(false);
			//Dem so nha cung cap dang co trong bang gia,neu co bang gia thi thuc hien buoc 1
			String query = "select count(khachhang_fk) as num from bgst_khachhang where banggiasieuthi_fk ='" + this.id +"'";
			//System.out.println(query);
			ResultSet rs = cn.get(query);
			rs.next();
			int num = new Integer(rs.getString("num")).intValue();
			rs.close();
			
			if(num > 0){ //Buoc 1; xoa cac nha phan phoi khong duoc chon trong form dieu chuyen bang gia cho npp
				String[] tmp = new String[num];
				//lay ra danh sach cac khach hang dang co trong bang gia
				query = "select khachhang_fk,banggiasieuthi_fk from bgst_khachhang where banggiasieuthi_fk='"+this.id+"'";			
				rs = cn.get(query);
				
				int i = 0;
				while(rs.next()){
				//	System.out.println("chbox" + rs.getString("nppId"));
						//Kiem tra nhung nha phan phoi nao co trong bang gia,va o ngoai form co cot check =null,khong the xoa het mot luot roi cap nhat lai, 
						//vi ngoai man hinh co chuc nang chon cac nhapp theo khu vuc,thi ta chi xet tren pham vi cac nha pp dang hien ra tren form
					if (geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("chbox" + rs.getString("khachhang_fk"))) == null ){
//						System.out.println("chbox" + rs.getString("nppId"));
						tmp[i] = rs.getString("khachhang_fk");
						i++;
					}					
				}	
				rs.close();
				
				if(i > 0){
					for (int n = 0; n < i; n++){
					query="delete from bgst_khachhang where banggiasieuthi_fk ='" + this.id + "' and khachhang_fk = '" + tmp[n] + "'";
						if(!cn.update(query)){
							cn.update("rollback");
							System.out.println("Error banggiasieuthi.java - line 712- error :" + query);
							return false;
						}
									
					}
				}

			}
			String sql_getnew="select pk_seq,ten,0  from khachhang where trangthai='1' and npp_fk='"+this.nppId+"' and pk_Seq not in (select khachhang_fk from bgst_khachhang where banggiasieuthi_fk ='"+this.id+"')";
			
			 
			rs = cn.get(sql_getnew);
			
			while(rs.next()){
				
				if (geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("chbox" + rs.getString("pk_seq"))) != null ){
					//Kiem tra khachhang nay da co bang gia thuoc don vi kinh doanh nay chua
					
					query = "select count(*) as num from bgst_khachhang where khachhang_fk='"+rs.getString("pk_Seq")+"' and banggiasieuthi_fk in (select pk_Seq from banggiasieuthi where npp_fk='"+this.nppId+"' and dvkd_fk='"+this.dvkdId+"' )";
					//System.out.println("Get khachhang:  "+query);
						ResultSet rs2 = cn.get(query);
					
						rs2.next();					
						if(!rs2.getString("num").equals("0")){
							String sql="delete bgst_khachhang where khachhang_fk='"+rs.getString("pk_Seq")+"' and banggiasieuthi_fk in (select pk_Seq from banggiasieuthi where npp_fk='"+this.nppId+"' and dvkd_fk='"+this.dvkdId+"' )";	
							if(!cn.update(sql)){
								cn.update("rollback");
								System.out.println("Error banggiasieuthi.java - line 712- error :" + sql);
								return false;
							}
						}
						String sql_insert="insert into bgst_khachhang(khachhang_fk,banggiasieuthi_fk)"+
						" values( '" +rs.getString("pk_seq") + "','" + this.id + "')";
						//System.out.println("Get insset:  "+sql_insert);
						if(!cn.update(sql_insert)){
							cn.update("rollback");
							//System.out.println("Error banggiasieuthi.java - line 712- error :" + sql_insert);
							return false;
						}
					
							//System.out.println("insert into BANGGIAMUANPP_NPP values('" + this.id + "', '" + geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("chbox" + rs.getString("nppId")) + "')"));

				}					
			}
			
			cn.getConnection().commit();
			cn.getConnection().setAutoCommit(true);
			
		}catch(Exception e){
				cn.update("rollback");
				System.out.println("Error Banggiamuanpp_npp - line 307: error" + e.toString());
				return false;
			}
		
		return true;
	}

	@Override
	public void CreateRsSearch() {
		// TODO Auto-generated method stub
		this.rshch=db.get("select pk_seq,diengiai as ten from hangcuahang");
		this.rslch=db.get("select pk_seq,diengiai as ten from loaicuahang");
		this.rsvtch=db.get("select pk_seq,diengiai  as ten from vitricuahang");
		if(this.getTinhthanh()!=""){
		this.rsquanhuyen=db.get("select pk_Seq,ten  from quanhuyen where tinhthanh_fk='"+this.tinhthanhId+"'");
		}else{
			this.rsquanhuyen=db.get("select pk_Seq,ten  from quanhuyen");
		}
		this.rstinhthanh=db.get("select pk_seq,ten from tinhthanh");
		this.rs_daidienkinhdoanh=db.get("select pk_seq,ten from daidienkinhdoanh where npp_fk='"+this.nppId+"' and trangthai='1'");
		this.setKhCoBangGia();
	}

	@Override
	public String getHangCuaHang() {
		// TODO Auto-generated method stub
		return this.hchid;
	}

	@Override
	public void setHangCuaHang(String hangcuahang) {
		// TODO Auto-generated method stub
		this.hchid=hangcuahang;
	}

	@Override
	public String getLoaiCuahang() {
		// TODO Auto-generated method stub
		return this.lchid;
	}

	@Override
	public void setLoaiCuahang(String loaicuahang) {
		// TODO Auto-generated method stub
		this.lchid=loaicuahang;
	}

	@Override
	public String getVitricuahang() {
		// TODO Auto-generated method stub
		return this.vtchId;
	}

	@Override
	public void setViTriCuahang(String vtchid) {
		// TODO Auto-generated method stub
		this.vtchId=vtchid;
	}

	@Override
	public String getQuanhuyen() {
		// TODO Auto-generated method stub
		return this.quanhuyenId;
	}

	@Override
	public void setquanhuyen(String quanhuyen) {
		// TODO Auto-generated method stub
		this.quanhuyenId=quanhuyen;
	}

	@Override
	public String getTinhthanh() {
		// TODO Auto-generated method stub
		return this.tinhthanhId;
	}

	@Override
	public void setTinhThanh(String tinhthanh) {
		// TODO Auto-generated method stub
		this.tinhthanhId=tinhthanh;
	}

	@Override
	public ResultSet getrsloaicuahang() {
		// TODO Auto-generated method stub
		return rslch;
	}

	@Override
	public ResultSet getrshangcuahang() {
		// TODO Auto-generated method stub
		return rshch;
	}

	@Override
	public ResultSet getrsvitricuahang() {
		// TODO Auto-generated method stub
		return this.rsvtch;
	}

	@Override
	public ResultSet getrsquanhuyen() {
		// TODO Auto-generated method stub
		return this.rsquanhuyen;
	}

	@Override
	public ResultSet getrstinhthanh() {
		// TODO Auto-generated method stub
		return rstinhthanh;
	}

	@Override
	public void setKhList(List<IKhachHang_Bgst> khlist) {
		// TODO Auto-generated method stub
		this.listkh_bgstList=khlist;
	}

	@Override
	public List<IKhachHang_Bgst> getListKh() {
		// TODO Auto-generated method stub
		return this.listkh_bgstList;
	}

	@Override
	public void setkhachhangString(String ss) {
		// TODO Auto-generated method stub
		this.npplist=ss;
	}

	@Override
	public ResultSet getrsddkd() {
		// TODO Auto-generated method stub
		return this.rs_daidienkinhdoanh;
	}

	@Override
	public String getddkdid() {
		// TODO Auto-generated method stub
		return this.ddkdId;
	}

	@Override
	public void setddkdid(String ddkdid) {
		// TODO Auto-generated method stub
		this.ddkdId=ddkdid;
	}

	@Override
	public Hashtable<String, String> getDSKH_CO_BGST() {
		// TODO Auto-generated method stub
		return this.htb_dskh_has_bgst;
	}
	
}