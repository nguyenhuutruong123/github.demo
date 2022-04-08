package geso.dms.center.beans.nhacungcap.imp;
import geso.dms.center.beans.nhacungcap.INhacungcap;
import geso.dms.center.db.sql.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class Nhacungcap implements INhacungcap
{
	private static final long serialVersionUID = -9217977546733610214L;
	String userId;
	String id;
	String ten;
	String diachi;
	String masothue;
	String tenviettat;
	String trangthai;
	String ngaytao;
	String ngaysua;
	String nguoitao;
	String nguoisua;
	String sotaikhoan;
	String dienthoai;
	String fax;
	String nguoidaidien;
	String msg;
	dbutils db;
	
	public Nhacungcap(String[] param)
	{
		this.id = param[0];
		this.ten = param[1];
		this.diachi = param[2];
		this.masothue = param[3];
		this.tenviettat = param[4];
		this.trangthai = param[5];
		this.ngaytao = param[6];
		this.nguoitao = param[7];
		this.ngaysua = param[8];
		this.nguoisua = param[9];
		this.sotaikhoan=param[10];
		this.dienthoai=param[11];
		this.fax=param[12];
		this.nguoidaidien=param[13];
		this.msg = "";
		this.db = new dbutils();
	}
	
	public Nhacungcap()
	{
		this.id = "";
		this.ten = "";
		this.tenviettat = "";
		this.diachi = "";
		this.masothue = "";
		this.tenviettat = "";
		this.trangthai = "";
		this.ngaytao = "";
		this.nguoitao = "";
		this.ngaysua = "";
		this.nguoisua = "";
		this.msg = "";
		this.sotaikhoan="";
		this.dienthoai="";
		this.fax="";
		this.nguoidaidien="";
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
	
	public String getMasothue()
	{
		return this.masothue;
	}
	
	public void setMasothue(String masothue)
	{
		this.masothue = masothue;
	}
	
	public String getTenviettat()
	{
		return this.tenviettat;
	}

	public void setTenviettat(String tenviettat)
	{
		this.tenviettat = tenviettat;
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
	boolean kiemtraten()
	{ 
		List<Object> data = new ArrayList<Object>();
		
		String sql ="select * from nhacungcap where ten = N'"+this.ten+"'";
		 
		ResultSet rs = db.get(sql);
		try {
			while(rs.next())
				  return true;
		if(rs!=null) rs.close();    	
		} catch(Exception e) {		
			e.printStackTrace();
		}
		 	return false;
	}
	public boolean saveNewNcc(){
		try{
			this.db.getConnection().setAutoCommit(false);
			 
			System.out.println("NGUOI TAO: " + this.nguoitao);
			System.out.println("NGUOI SUA: " + this.nguoisua);
			
			List<Object> data = new ArrayList<Object>();
			
			String query;
			//  System.out.print("-----------------------chuoi:" + kiemtraten());
				
			if(kiemtraten()) return false;
			
			query ="insert into nhacungcap(TEN,DIACHI,MASOTHUE,SOTAIKHOAN,DIENTHOAI,FAX,NGUOIDAIDIEN,NGAYTAO,NGAYSUA,NGUOITAO,NGUOISUA,TENVIETTAT,TRANGTHAI) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
			data.clear();
			data.add(this.ten); data.add(this.diachi); data.add(this.masothue); data.add(this.sotaikhoan); data.add(this.dienthoai);
			data.add(this.fax); data.add(this.nguoidaidien); data.add(this.ngaytao); data.add(this.ngaysua); data.add(this.nguoitao);
			data.add(this.nguoisua); data.add(this.tenviettat); data.add(this.trangthai);
			System.out.print(" chen them "+query);
			
			if( this.db.updateQueryByPreparedStatement(query, data)!=1 )
			{
				this.msg="Lỗi tạo mới NCC";
				this.db.getConnection().rollback();
				return false;
			}
			
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
			
			/*if (!db.update(query)){
			//	System.out.print(query);
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				return false;			
			}*/
			
			String mancc_dvkd = "select  max(pk_seq)   as  ma from nhacungcap";
			System.out.print(" chen them "+mancc_dvkd);
			ResultSet rs = db.get(mancc_dvkd);
 
			if (rs != null)
			{
				String mancc = "";
				while (rs.next()){
					mancc = rs.getString("ma");
				}
				if(rs!=null) rs.close();	        	
				String dvkd = "select dvkd_fk from nhacungcap_dvkd group by  dvkd_fk";
				ResultSet rs1 = db.get(dvkd);
				try {
					if (rs1 != null)
					{
						while (rs1.next())
						{
							
						
							String madvkd= rs1.getString("dvkd_fk");
							query = "insert into nhacungcap_dvkd values('" + mancc + " ' , '"+ madvkd +"', '0')";
							System.out.print(" chen them "+query);
							if (!db.update(query)){
							//	System.out.print(query);
								geso.dms.center.util.Utility.rollback_throw_exception(db);
								return false;			
							}
						}if(rs!=null) rs.close();
			        	if(db!=null) db.shutDown();
					}
						  
				} catch(Exception e) {
					
					e.printStackTrace();
				}
			}
				  
		} catch(Exception e) {
			
			e.printStackTrace();
		}
		
		return true;
		
	}
	
	public boolean UpdateNcc() {
		String query;
 
		this.nguoitao = this.userId;
		try{
//			if(kiemtraten()) return false;
			this.db.getConnection().setAutoCommit(false);
			List<Object> data = new ArrayList<Object>();
			query = "update nhacungcap set ten=?, diachi= ?, masothue = ?,sotaikhoan=?,dienthoai=?,fax=?,nguoidaidien=?, tenviettat = ?, trangthai = ?, ngaysua = ?, nguoisua = ?  where pk_seq = ?" ;
			data.clear();
			data.add(this.ten); data.add(this.diachi); data.add(this.masothue);
			data.add(this.sotaikhoan); data.add(this.dienthoai); data.add(this.fax);
			data.add(this.nguoidaidien); data.add(this.tenviettat); data.add(this.trangthai);
			data.add(this.ngaysua); data.add(this.nguoisua); data.add(this.id);
			
			System.out.println("aaaaaaaaaaa: " + data.size());
			System.out.print("Cau update: " + query);
			
			if( this.db.updateQueryByPreparedStatement(query, data)!=1 )
			{
				this.msg="Lỗi update NCC";
				this.db.getConnection().rollback();
				return false;
			}
			
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
			
		}catch(Exception e){
			e.printStackTrace();
			try
			{
				this.db.getConnection().rollback();
			} catch (SQLException e1)
			{
				e1.printStackTrace();
			}
			return false;
		}
		
		return true;
 
	}
	
	public void init(){
		String query = "select a.pk_seq, a.ten, a.diachi,isnull(a.sotaikhoan,'') as sotaikhoan,isnull(a.dienthoai,'') as dienthoai,isnull(a.fax,'') as fax,isnull(a.nguoidaidien,'') as nguoidaidien,a.masothue, a.tenviettat, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua from nhacungcap a, nhanvien b, nhanvien c where a.nguoitao = b.pk_seq and a.nguoisua = c.pk_seq and a.pk_seq="+ this.id +" order by ngaytao";
		System.out.println(query);
   		ResultSet ncc = this.db.get(query);
   		
   		try{
   			ncc.next();
   			this.ten = ncc.getString("ten");
   			this.diachi = ncc.getString("diachi");
   			this.masothue = ncc.getString("masothue");
   			this.tenviettat = ncc.getString("tenviettat");
   			this.trangthai = ncc.getString("trangthai");
   			this.ngaytao = ncc.getString("ngaytao");
   			this.ngaysua = ncc.getString("ngaysua");
   			this.nguoitao = ncc.getString("nguoitao");
   			this.nguoisua = ncc.getString("nguoisua");
   			this.sotaikhoan=ncc.getString("sotaikhoan");
   			if(this.sotaikhoan==null||this.sotaikhoan.trim().length()==0||this.sotaikhoan.equals("null")) this.sotaikhoan="";
   			System.out.println(this.sotaikhoan);
   			this.dienthoai=ncc.getString("dienthoai");
   			if(this.dienthoai==null||this.dienthoai.trim().length()==0||this.dienthoai.equals("null") )this.dienthoai="";
   			this.fax=ncc.getString("fax");

   			if(this.fax==null||this.fax.trim().length()==0||this.fax.equals("null")) this.fax="";
   			this.nguoidaidien=ncc.getString("nguoidaidien");
   			if(this.nguoidaidien==null||this.nguoidaidien.trim().length()==0||this.nguoidaidien.equals("null")) this.nguoidaidien="";
   			if(ncc!=null) ncc.close();   			        	       
   		}catch(Exception e){}
	}
	
	public void DBClose(){				
		this.db.shutDown();
	}

	
	public String getSotaikhoan() {
		
		return this.sotaikhoan;
	}

	
	public void setSotaikhoan(String sotaikhoan) {
		
		this.sotaikhoan=sotaikhoan;
	}

	
	public String getDienthoai() {
		
		return this.dienthoai;
	}

	
	public void setDienthoai(String dienthoai) {
		
		this.dienthoai=dienthoai;
	}

	
	public String getFax() {
		
		return this.fax;
	}

	
	public void setFax(String fax) {
		
		this.fax=fax;
	}

	
	public String getNguoidaidien() {
		
		return this.nguoidaidien;
	}

	
	public void setNguoidaidien(String nguoidaidien) {
		
		this.nguoidaidien=nguoidaidien;
	}
	public static void main(String[] args) {
		System.out.println("vo");
		dbutils db = new dbutils();
		String query="SELECT NPP_FK FROM CHAYHOTRO order by npp_fk desc ";
		ResultSet rs=db.get(query);
		
		try {
			db.getConnection().setAutoCommit(false);
			while(rs.next()){
				
				query="UPDATE CTKHUYENMAI SET KHO_FK=100001 WHERE SCHEME='FL6.TD90.0920'";
				if(!db.update(query)){
 					db.getConnection().rollback();
					return ;
				}
				query="UPDATE CTKHUYENMAI  SET KHO_FK=100001 WHERE SCHEME='FL10.TD190.0920'";
				if(!db.update(query)){
 					db.getConnection().rollback();
					return ;
				}
				query=" UPDATE A SET A.kho_fk=100001 FROM DONHANG_CTKM_TRAKM_CHITIET A INNER JOIN CTKHUYENMAI "+
						" B ON A.CTKMID=B.PK_SEQ  "+
						" INNER JOIN DONHANG C ON C.PK_SEQ=A.DONHANGID "+
						" WHERE scheme='FL10.TD190.0920' AND C.NPP_FK="+rs.getString("NPP_FK")+"";
				if(!db.update(query)){
 					db.getConnection().rollback();
					return ;
				}
				query=" UPDATE A SET A.kho_fk=100001 FROM DONHANG_CTKM_TRAKM_CHITIET A INNER JOIN CTKHUYENMAI "+
						" B ON A.CTKMID=B.PK_SEQ  "+
						" INNER JOIN DONHANG C ON C.PK_SEQ=A.DONHANGID "+
						" WHERE scheme='FL10.TD190.0920' AND C.NPP_FK="+rs.getString("NPP_FK")+"";
				if(!db.update(query)){
 					db.getConnection().rollback();
					return ;
				}
				query=" update A set A.BOOKED= isnull(C.SOLUONG,0) from nhapp_kho a left join ufnBooked() c on a.kho_fk = c.KHO_FK "+
						" 	and a.npp_fk = c.NPP_FK and   "+
						" 					a.sanpham_fk = c.SANPHAM_FK and a.kbh_fk = c.KBH_FK   "+
						" 					where a.NPP_FK = "+rs.getString("NPP_FK")+" and isnull(a.booked,0) <> isnull(c.SOLUONG,0)   ";
				if(!db.update(query)){
 					db.getConnection().rollback();
					return ;
				}
				query=" update a set a.BOOKED= isnull(c.SOLUONG,0) from nhapp_kho_chitiet a 	 "+
					"	left join ufnBooked_chitiet() c on a.kho_fk = c.KHO_FK and a.npp_fk = c.NPP_FK and   "+
					"	a.sanpham_fk = c.SANPHAM_FK and a.kbh_fk = c.KBH_FK   "+
					"	and a.ngaynhapkho = c.ngaynhapkho  "+
					"	where a.NPP_FK = "+rs.getString("NPP_FK")+" and isnull(a.booked,0) <> isnull(c.SOLUONG,0)  ";
				if(!db.update(query)){
 					db.getConnection().rollback();
					return ;
				}
				

				query=" update b set b.SOLUONG= isnull(a.cuoiky,0) ,b.AVAILABLE=isnull(a.cuoiky,0)-b.BOOKED 	 "+
						" from ufn_XNT_TuNgay_DenNgay_FULL_New( "+rs.getString("NPP_FK")+", ( select    (select  convert(char(10),dateadd (DAY,1,max(a.NGAYKS)),126) as ngayks  "+
						" from KHOASONGAY a where NGAYKS >='2019-01-01'  "+
						" and  a.npp_Fk=aa.pk_seq ) as tungay from nhaphanphoi aa where trangthai = 1   "+
						" and aa.pk_seq= "+rs.getString("NPP_FK")+"), '') a right join NHAPP_KHO b on a.kho_fk = b.KHO_FK  "+
						" 	and a.npp_fk = b.NPP_FK and a.sanpham_fk = b.SANPHAM_FK and a.kbh_fk = b.KBH_FK   "+
						" 	where (isnull(a.cuoiky,0) <> ISNULL(b.soluong, 0) or isnull(a.cuoiky,0) <> ISNULL(b.booked,0)+ISNULL(b.available,0))  "+
						" 	and b.NPP_FK= "+rs.getString("NPP_FK")+"   ";
				if(!db.update(query)){
 					db.getConnection().rollback();
					return ;
				}

				query=" update b set b.SOLUONG= isnull(a.cuoiky,0) ,b.AVAILABLE=isnull(a.cuoiky,0)-b.BOOKED   "+
						"  from ufn_XNT_TuNgay_DenNgay_FULL_Chitiet_New("+rs.getString("NPP_FK")+",( select   (select  convert(char(10),dateadd (DAY,1,max(a.NGAYKS)),126) as ngayks  "+
						" from KHOASONGAY a where NGAYKS >='2019-01-01'  "+
						" and  a.npp_Fk=aa.pk_seq ) as tungay from nhaphanphoi aa where trangthai = 1   "+
						" and aa.pk_seq="+rs.getString("NPP_FK")+"), '') a  "+
						" 	right join NHAPP_KHO_chitiet b on a.kho_fk = b.KHO_FK  "+
						" 	and a.npp_fk = b.NPP_FK and a.sanpham_fk = b.SANPHAM_FK and a.kbh_fk = b.KBH_FK and a.ngaynhapkho = b.ngaynhapkho  "+
						" 	where (isnull(a.cuoiky,0) <> ISNULL(b.soluong, 0) or isnull(a.cuoiky,0) <> ISNULL(b.booked,0)+ISNULL(b.available,0))  "+
						" and b.NPP_FK="+rs.getString("NPP_FK")+"  ";
				if(!db.update(query)){
 					db.getConnection().rollback();
					return ;
				}
				query=" UPDATE nk SET nk.AVAILABLE=nk.SOLUONG-nk.BOOKED FROM NHAPP_KHO nk WHERE nk.SOLUONG <> available+booked and npp_Fk="+rs.getString("NPP_FK")+"  ";
				if(!db.update(query)){
 					db.getConnection().rollback();
					return ;
				}
				query="UPDATE nk SET nk.AVAILABLE=nk.SOLUONG-nk.BOOKED FROM NHAPP_KHO_CHITIET nk WHERE nk.SOLUONG <> available+booked and npp_Fk="+rs.getString("NPP_FK")+" ";
				if(!db.update(query)){
 					db.getConnection().rollback();
					return ;
				}
				
				query=" select  count(*) as sl "+
						" from    "+
						" (   "+
						" 	select npp_fk, kbh_fk, kho_fk, sanpham_fk, sum(available) as available, sum(soluong) as soluong, sum(booked) as booked_ct   "+
						" 	from nhapp_kho_chitiet where npp_fk = '"+rs.getString("NPP_FK")+"'  "+
						" 	group by kbh_fk, npp_fk, kho_fk, sanpham_fk	 "+
						" 	)  "+
						" 	CT full outer join nhapp_kho total on total.npp_fk=ct.npp_fk and total.kbh_fk=ct.kbh_fk "+
						" 	and total.sanpham_fk=ct.sanpham_fk and total.kho_fk=ct.kho_fk  "+
						" where  "+
						" (round( isnull(ct.available,0),2) + round( isnull(ct.booked_ct,0),2) != round(isnull(total.available,0),2) + round(isnull(total.booked ,0),2) "+
						" or round(isnull(total.soluong,0),2) != round(isnull(ct.soluong,0),2)   "+
						" ) and  isnull(total.npp_fk, ct.npp_fk) = '"+rs.getString("NPP_FK")+"'  ";
				ResultSet rscheck=db.get(query);
				 rscheck.next();
				 if(rscheck.getInt("sl")>0){
					 db.getConnection().rollback();
						return ;
				 }
				 
					
					System.out.println("done"+rs.getString("NPP_FK"));

			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
