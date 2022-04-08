package geso.dms.center.beans.gioihancongnott.imp;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import geso.dms.center.beans.chitieu.imp.ChiTieuNPP;
import geso.dms.center.beans.gioihancongnott.IGioiHanCongNoTT;
import geso.dms.center.beans.gioihancongnott.IGioiHangCongNoTT_Npp;
import geso.dms.distributor.db.sql.dbutils;

public class GioiHanCongNoTT implements IGioiHanCongNoTT{

	String Id;
	String NgayTao;
	String NguoiTao;
	String NgaySua;
	String NguoiSua;
	String DienGiai;
	String SoNgayNo;
	String SoTienNo;
	String Message;
	List<IGioiHanCongNoTT> list=new ArrayList<IGioiHanCongNoTT>();
	List<IGioiHangCongNoTT_Npp> listnpp=new ArrayList<IGioiHangCongNoTT_Npp>();
	public GioiHanCongNoTT(){
		 Id="";
		 NgayTao="";
		 NguoiTao="";
		 NgaySua="";
		 NguoiSua="";
		 DienGiai="";
		 SoNgayNo="";
		 SoTienNo="";
		 Message="";
	}
	public GioiHanCongNoTT(String Id_){
		String	 sql_getdata ="select a.pk_seq,a.diengiai,a.songayno,a.sotienno,a.ngaytao,a.ngaysua ,nt.ten as nguoitao,ns.ten as nguoisua from gioihancongnott a inner join nhanvien as nt on nt.pk_seq=a.nguoitao  inner join nhanvien as ns on ns.pk_seq=a.nguoisua where a.pk_seq="+ Id_;
			System.out.println(sql_getdata);
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
					this.setSongayno(rs_getdata.getString("songayno"));
					this.setSotienno(rs_getdata.getString("sotienno"));
					//thuc hien load danh sach cac nha pp co cong no
					String sql_getdetail="select a.pk_Seq,a.ma,a.ten,a.diachi,b.ten as khuvuc from nhaphanphoi a inner join khuvuc b on b.pk_seq=a.khuvuc_fk  where ghcntt_fk= "+Id_;
					System.out.println("GioiHanCongNoTT :"+sql_getdetail);
					ResultSet rs=db.get(sql_getdetail);
					while(rs.next()){
						IGioiHangCongNoTT_Npp npp=new GioiHangCongNo_Npp();
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
	@Override
	public String getUserId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setUserId(String userId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return this.Id;
	}

	@Override
	public void setId(String id) {
		// TODO Auto-generated method stub
		this.Id=id;
	}

	@Override
	public String getDiengiai() {
		// TODO Auto-generated method stub
		return this.DienGiai;
	}

	@Override
	public void setDiengiai(String diengiai) {
		// TODO Auto-generated method stub
		this.DienGiai=diengiai;
	}

	@Override
	public String getSongayno() {
		// TODO Auto-generated method stub
		return this.SoNgayNo;
	}

	@Override
	public void setSongayno(String songayno) {
		// TODO Auto-generated method stub
		this.SoNgayNo=songayno;
	}

	@Override
	public String getSotienno() {
		// TODO Auto-generated method stub
		return this.SoTienNo;
	}

	@Override
	public void setSotienno(String sotienno) {
		// TODO Auto-generated method stub
		this.SoTienNo=sotienno;
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
	public void setListGioHanCongNo(String sql) {
		list.clear();
		// TODO Auto-generated method stub
		String sql_getdata="";
		if(sql.equals("")){
		 sql_getdata ="select a.pk_seq,a.diengiai,a.songayno,a.sotienno,a.ngaytao,a.ngaysua ,nt.ten as nguoitao,ns.ten as nguoisua from gioihancongnott a inner join nhanvien as nt on nt.pk_seq=a.nguoitao  inner join nhanvien as ns on ns.pk_seq=a.nguoisua ";
		}else{
			sql_getdata=sql;
		}
		dbutils db=new dbutils();
		ResultSet rs_getdata=db.get(sql_getdata);
		if(rs_getdata!=null){
			try{
				while(rs_getdata.next()){
				IGioiHanCongNoTT gioihan=new GioiHanCongNoTT();
				gioihan.setDiengiai(rs_getdata.getString("diengiai"));
				gioihan.setId(rs_getdata.getString("pk_seq"));
				gioihan.setMessage("");
				gioihan.setNgaysua(rs_getdata.getString("ngaysua"));
				gioihan.setNgaytao(rs_getdata.getString("ngaytao"));
				gioihan.setNguoisua(rs_getdata.getString("nguoisua"));
				gioihan.setNguoitao(rs_getdata.getString("nguoitao"));
				gioihan.setSongayno(rs_getdata.getString("songayno"));
				gioihan.setSotienno(rs_getdata.getString("sotienno"));
				list.add(gioihan);
				}
			}catch(Exception er){
				System.out.println("Error 168 GioiHanCongNo " +er.toString());
			}
		}
	}

	@Override
	public List<IGioiHanCongNoTT> getListGioiHanCongNo() {
		// TODO Auto-generated method stub
		return this.list;
	}
	@Override
	public void setListNhapp(List<IGioiHangCongNoTT_Npp> list) {
		// TODO Auto-generated method stub
		this.listnpp=list;
	}
	@Override
	public List<IGioiHangCongNoTT_Npp> getListNhaPP() {
		// TODO Auto-generated method stub
		return this.listnpp;
	}
	@Override
	public boolean SaveGioiHanCongNoTT() {
		// TODO Auto-generated method stub
		dbutils db=new dbutils();	
		try
		{
		db.getConnection().setAutoCommit(false);
		
		// thuc hien insert
		String sqlSave="insert into GIOIHANCONGNOTT(NGAYTAO,NGUOITAO,NGAYSUA,NGUOISUA,DIENGIAI,songayno,sotienno) " +
		"values ('"+this.NgayTao+"','"+this.NguoiTao+"','"+this.NgaySua+"','"+this.NguoiSua+"',N'"+this.DienGiai+"','"+this.SoNgayNo+"','"+this.SoTienNo+"')";
		if(!db.update(sqlSave)){
			this.Message="Khong The Tao Moi Gioi Han Cong No Nay,Vui Long Kiem Tra Lai"  ;
			System.out.println("sqlSaveGioiHanNoTT: 146  " +sqlSave);
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return false;
		}
		
		//thuc hien insert chi tiet
		String query = "select IDENT_CURRENT('GIOIHANCONGNOTT') as dhId";
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
				IGioiHangCongNoTT_Npp npp;
				npp=this.listnpp.get(count);
				if(npp.getId().equals("1")){
				String sql_insertdetail="update nhaphanphoi set ghcntt_fk ="+ this.getId()+ " where pk_seq="+ npp.getIdNhaPp();
				System.out.println(sql_insertdetail);
				if(!db.update(sql_insertdetail)){
					this.Message="Khong The Cap Nhat Duoc Gioi Han Cong No Cho Nha Phan Phoi" + sql_insertdetail;
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
	@Override
	public boolean EditGioiHanCongNoTT() {
		// TODO Auto-generated method stub
		dbutils db=new dbutils();	
		try
		{
		db.getConnection().setAutoCommit(false);
		
		// thuc hien insert
		String sqlSave="update GIOIHANCONGNOTT set NGAYSUA='"+this.NgaySua+"' ,NGUOISUA="+this.NguoiSua+" ,DIENGIAI='"+this.DienGiai+"',songayno='"+this.SoNgayNo+"',sotienno='"+this.SoTienNo+"' where pk_seq= " + this.Id;
		if(!db.update(sqlSave)){
			this.Message="Khong The Tao Moi Gioi Han Cong No Nay,Vui Long Kiem Tra Lai"  ;
			System.out.println("sqlSaveGioiHanNoTT: 146  " +sqlSave);
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return false;
		}
		
		String sql="select pk_seq from nhaphanphoi where ghcntt_fk="+ this.Id;
		ResultSet rs=db.get(sql);
		if(rs!=null){
			while(rs.next()){
				String sql_insertdetail="update nhaphanphoi set ghcntt_fk =0 where pk_seq="+ rs.getString("pk_seq");
				if(!db.update(sql_insertdetail)){
					this.Message="Khong The Cap Nhat Duoc Gioi Han Cong No Cho Nha Phan Phoi" + sql_insertdetail;
					geso.dms.center.util.Utility.rollback_throw_exception(db);					
					return false;
				}	
			}
		}
		
		int count=0;
		if(this.listnpp!=null){
			while (count <this.listnpp.size()){
				IGioiHangCongNoTT_Npp npp;
				npp=this.listnpp.get(count);
				if(npp.getId().equals("1")){
				String sql_insertdetail="update nhaphanphoi set ghcntt_fk ="+ this.getId()+ " where pk_seq="+ npp.getIdNhaPp();
				System.out.println(sql_insertdetail);
				if(!db.update(sql_insertdetail)){
					this.Message="Khong The Cap Nhat Duoc Gioi Han Cong No Cho Nha Phan Phoi" + sql_insertdetail;
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
	@Override
	public boolean DeleteGioiHanCongNoTT() {
		// TODO Auto-generated method stub
		dbutils db=new dbutils();	
		try
		{
		db.getConnection().setAutoCommit(false);
		
		// thuc hien insert
		String sqlSave="delete from GIOIHANCONGNOTT  where pk_seq= " + this.Id;
		if(!db.update(sqlSave)){
			this.Message="Khong The Tao Moi Gioi Han Cong No Nay,Vui Long Kiem Tra Lai"  ;
			System.out.println("sqlSaveGioiHanNoTT: 146  " +sqlSave);
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return false;
		}
		
		String sql="select pk_seq from nhaphanphoi where ghcntt_fk="+ this.Id;
		ResultSet rs=db.get(sql);
		if(rs!=null){
			while(rs.next()){
				String sql_insertdetail="update nhaphanphoi set ghcntt_fk =0 where pk_seq="+ rs.getString("pk_seq");
				if(!db.update(sql_insertdetail)){
					this.Message="Khong The Cap Nhat Duoc Gioi Han Cong No Cho Nha Phan Phoi" + sql_insertdetail;
					geso.dms.center.util.Utility.rollback_throw_exception(db);					
					return false;
				}	
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
	

}
