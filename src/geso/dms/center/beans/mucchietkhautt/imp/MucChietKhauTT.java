package geso.dms.center.beans.mucchietkhautt.imp;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import geso.dms.center.beans.mucchietkhautt.IMucChietKhauTT;
import geso.dms.center.beans.mucchietkhautt.IMucChietKhauTT_NhaPP;
import geso.dms.distributor.db.sql.dbutils;

public class MucChietKhauTT implements IMucChietKhauTT {

	String Id;
	String NgayTao;
	String NguoiTao;
	String NgaySua;
	String NguoiSua;
	String DienGiai;
	double ChietKhau;
	String Message;
	List<IMucChietKhauTT> list=new ArrayList<IMucChietKhauTT>();
	List<IMucChietKhauTT_NhaPP> listnpp=new ArrayList<IMucChietKhauTT_NhaPP>();
	public MucChietKhauTT(){
		 Id="";
		 NgayTao="";
		 NguoiTao="";
		 NgaySua="";
		 NguoiSua="";
		 DienGiai="";
		 ChietKhau=0;
		 Message="";
	}
	public MucChietKhauTT(String Id_){
		this.listnpp.clear();
		String	 sql_getdata ="select a.pk_seq,a.diengiai,a.chietkhau,a.ngaytao,a.ngaysua ,nt.ten "+
		" as nguoitao,ns.ten as nguoisua from mucchietkhautt a inner join nhanvien as nt on nt.pk_seq=a.nguoitao "+ 
		" inner join nhanvien as ns on ns.pk_seq=a.nguoisua where a.pk_seq="+ Id_;
		//System.out.println(sql_getdata);
			dbutils db=new dbutils();
			ResultSet rs_getdata=db.get(sql_getdata);
			if(rs_getdata!=null){
				try{
					if(rs_getdata.next()){
					this.setDiengiai(rs_getdata.getString("diengiai"));
					this.setId(rs_getdata.getString("pk_seq"));
					this.setMessage("");
					this.setNgaysua(rs_getdata.getString("ngaysua"));
					this.setNgaytao(rs_getdata.getString("ngaytao"));
					this.setNguoisua(rs_getdata.getString("nguoisua"));
					this.setNguoitao(rs_getdata.getString("nguoitao"));
					this.setChietKhau(rs_getdata.getDouble("chietkhau"));
					//thuc hien load danh sach cac nha pp co cong no
					String sql_getdetail=" select a.pk_Seq,a.ma,a.ten,a.diachi,b.ten as khuvuc from nhaphanphoi a inner join khuvuc b on a.khuvuc_fk=b.pk_seq where mucchietkhautt_fk= "+Id_;
					//System.out.println("GioiHanCongNoTT :"+sql_getdetail);
					ResultSet rs=db.get(sql_getdetail);
					while(rs.next()){
						IMucChietKhauTT_NhaPP npp=new MucChietKhauTT_NhaPP();
						npp.setDiaChi(rs.getString("diachi"));
						npp.setID("1");
						npp.setIDNhaPP(rs.getString("pk_seq"));
						npp.setTenNhaPP(rs.getString("ten"));
						npp.setKhuVuc(rs.getString("khuvuc"));
						this.listnpp.add(npp);
					}
					}
				}catch(Exception er){
				System.out.println("Error 54 GioiHanCongNo " +er.toString());
				}
			}
	}
	
	public String getUserId() {
		
		return null;
	}

	
	public void setUserId(String userId) {
		
		
	}

	
	public String getId() {
		
		return this.Id;
	}

	
	public void setId(String id) {
		
		this.Id=id;
	}

	
	public String getDiengiai() {
		
		return this.DienGiai;
	}

	
	public void setDiengiai(String diengiai) {
		
		this.DienGiai=diengiai;
	}

	
	
	public String getNgaytao() {
		
		return this.NgayTao;
	}

	
	public void setNgaytao(String ngaytao) {
		
		this.NgayTao=ngaytao;
	}

	
	public String getNguoitao() {
		
		return this.NguoiTao;
	}

	
	public void setNguoitao(String nguoitao) {
		
		this.NguoiTao=nguoitao;
	}

	
	public String getNgaysua() {
		
		return this.NgaySua;
	}

	
	public void setNgaysua(String ngaysua) {
		
		this.NgaySua=ngaysua;
	}

	
	public String getNguoisua() {
		
		return this.NguoiSua;
	}

	
	public void setNguoisua(String nguoisua) {
		
		this.NguoiSua=nguoisua;
	}

	
	public String getMessage() {
		
		return this.Message;
	}

	
	public void setMessage(String msg) {
		
		this.Message=msg;
	}

	
	public void setListMucChietKhau(String sql) {
		list.clear();
		
		String sql_getdata="";
		if(sql.equals("")){
		 sql_getdata ="select a.pk_seq,a.diengiai,a.chietkhau,a.ngaytao,a.ngaysua ,nt.ten "+
			" as nguoitao,ns.ten as nguoisua from mucchietkhautt a inner join nhanvien as nt on nt.pk_seq=a.nguoitao "+ 
			 " inner join nhanvien as ns on ns.pk_seq=a.nguoisua";	
			}else{
			sql_getdata=sql;
		}
		dbutils db=new dbutils();
		ResultSet rs_getdata=db.get(sql_getdata);
		if(rs_getdata!=null){
			try{
				while(rs_getdata.next()){
				IMucChietKhauTT gioihan=new MucChietKhauTT();
				gioihan.setDiengiai(rs_getdata.getString("diengiai"));
				gioihan.setId(rs_getdata.getString("pk_seq"));
				gioihan.setMessage("");
				gioihan.setNgaysua(rs_getdata.getString("ngaysua"));
				gioihan.setNgaytao(rs_getdata.getString("ngaytao"));
				gioihan.setNguoisua(rs_getdata.getString("nguoisua"));
				gioihan.setNguoitao(rs_getdata.getString("nguoitao"));
				gioihan.setChietKhau(rs_getdata.getDouble("chietkhau"));
				list.add(gioihan);
				}
			}catch(Exception er){
				System.out.println("Error 168 GioiHanCongNo " +er.toString());
			}
		}
	}
	public boolean SaveMucChietKhauTT() {
		
		dbutils db=new dbutils();	
		try
		{
		db.getConnection().setAutoCommit(false);
		
		// thuc hien insert
		String sqlSave="insert into MUCCHIETKHAUTT(NGAYTAO,NGUOITAO,NGAYSUA,NGUOISUA,DIENGIAI,chietkhau) " +
		"values ('"+this.NgayTao+"','"+this.NguoiTao+"','"+this.NgaySua+"','"+this.NguoiSua+"',N'"+this.DienGiai+"','1')";
		if(!db.update(sqlSave)){
			this.Message="Khong The Tao Moi Gioi Han Cong No Nay,Vui Long Kiem Tra Lai"  ;
			System.out.println("sqlSaveGioiHanNoTT: 146  " +sqlSave);
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return false;
		}
		
		//thuc hien insert chi tiet
		String query = "select IDENT_CURRENT('MUCCHIETKHAUTT') as dhId";
		ResultSet rsDh = db.get(query);	
		try
		{
			rsDh.next();
		this.setId(rsDh.getString("dhId"));
		}catch(Exception ex){
			return false;
		}
		int count=0;
		if(this.listnpp!=null){
			while (count <this.listnpp.size()){
				IMucChietKhauTT_NhaPP npp;
				npp=this.listnpp.get(count);
				if(npp.getId().equals("1")){
				String sql_insertdetail="update nhaphanphoi set MUCCHIETKHAUTT_fk ="+ this.getId()+ " where pk_seq="+ npp.getIdNhaPp();
				//System.out.println(sql_insertdetail);
				if(!db.update(sql_insertdetail)){
					this.Message="Khong The Cap Nhat Duoc Muc Chiet Khau Cho Nha Phan Phoi" + sql_insertdetail;
					geso.dms.center.util.Utility.rollback_throw_exception(db);					
					return false;
				}
				}
			count++;
			}
		}
		db.getConnection().commit();
		db.getConnection().setAutoCommit(true);
		}catch(Exception er){
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			this.setMessage("Ban khong the thiet lap gioi han cong no moi nay, loi : " + er.toString());
			return false;
		}
		return true;
	}
	
	public boolean EditMucChietKhauTT() {
		
		dbutils db=new dbutils();	
		try
		{
		db.getConnection().setAutoCommit(false);
		
		// thuc hien insert
		String sqlSave="update MUCCHIETKHAUTT set NGAYSUA='"+this.NgaySua+"' ,NGUOISUA="+this.NguoiSua+" ,DIENGIAI='"+DienGiai+"',chietkhau="+this.ChietKhau+" where pk_seq= " + this.Id;
		if(!db.update(sqlSave)){
			this.Message="Khong The Tao Moi Muc Chiet Khau Nay,Vui Long Kiem Tra Lai"  ;
			System.out.println("sqlSaveGioiHanNoTT: 146  " +sqlSave);
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return false;
		}
		
		String sql="select pk_seq from nhaphanphoi where mucchietkhau_fk="+ this.Id;
		ResultSet rs=db.get(sql);
		if(rs!=null){
			while(rs.next()){
				String sql_insertdetail="update nhaphanphoi set mucchietkhautt_fk =0 where pk_seq="+ rs.getString("pk_seq");
				if(!db.update(sql_insertdetail)){
					this.Message="Khong The Cap Nhat Duoc Muc Chiet Khau Cho Nha Phan Phoi" + sql_insertdetail;
					geso.dms.center.util.Utility.rollback_throw_exception(db);					
					return false;
				}	
			}
		}
		
		int count=0;
		if(this.listnpp!=null){
			while (count <this.listnpp.size()){
				IMucChietKhauTT_NhaPP npp;
				npp=this.listnpp.get(count);
				if(npp.getId().equals("1")){
				String sql_insertdetail="update nhaphanphoi set mucchietkhautt_fk ="+ this.getId()+ " where pk_seq="+ npp.getIdNhaPp();
				System.out.println(sql_insertdetail);
				if(!db.update(sql_insertdetail)){
					this.Message="Khong The Cap Nhat Duoc Muc Chiet Khau Cho Nha Phan Phoi" + sql_insertdetail;
					geso.dms.center.util.Utility.rollback_throw_exception(db);					
					return false;
				}
			
				}
				count++;
			}
		}
		db.getConnection().commit();
		db.getConnection().setAutoCommit(true);
		}catch(Exception er){
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			this.setMessage("Ban khong the thiet lap muc chiet khau moi nay, loi : " + er.toString());
			return false;
		}
		return true;
	}
	
	public boolean DeleteMucChietKhauTT() {
		
		dbutils db=new dbutils();	
		try
		{
		db.getConnection().setAutoCommit(false);
		
		// thuc hien insert
		String sqlSave="delete from MUCCHIETKHAUTT  where pk_seq= " + this.Id;
		if(!db.update(sqlSave)){
			this.Message="Khong The Tao Moi Muc Chiet Khau Nay Nay,Vui Long Kiem Tra Lai"  ;
			System.out.println("sqlSaveGioiHanNoTT: 146  " +sqlSave);
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return false;
		}
		
		String sql="select pk_seq from nhaphanphoi where mucchietkhautt_fk="+ this.Id;
		ResultSet rs=db.get(sql);
		if(rs!=null){
			while(rs.next()){
				String sql_insertdetail="update nhaphanphoi set mucchietkhautt_fk =0 where pk_seq="+ rs.getString("pk_seq");
				if(!db.update(sql_insertdetail)){
					this.Message="Khong The Cap Nhat Duoc Muc Chiet Khau Cho Nha Phan Phoi" + sql_insertdetail;
					geso.dms.center.util.Utility.rollback_throw_exception(db);					
					return false;
				}	
			}
		}
		
		db.getConnection().commit();
		db.getConnection().setAutoCommit(true);
		}catch(Exception er){
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			this.setMessage("Ban khong the thiet lap muc chiet khau  moi nay, loi : " + er.toString());
			return false;
		}
		return true;
	}
	
	public double getChietKhau() {
		
		return this.ChietKhau;
	}
	
	public void setChietKhau(double chietkhau) {
		
		this.ChietKhau=chietkhau;
	}
	
	public List<IMucChietKhauTT> getListMucChietKhau() {
		
		return this.list;
	}
	
	public void setListNhaPhanPhoi(List<IMucChietKhauTT_NhaPP> listnpp_new) {
		
		this.listnpp=listnpp_new;
	}
	
	public List<IMucChietKhauTT_NhaPP> getListNhaPhanPhoi() {
		
		return this.listnpp;
	}
	
	public void setListNhaPhanPhoiInit() {
		
		
	}
	
}
