package geso.dms.center.beans.khott.imp;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import geso.dms.center.beans.khott.IKhoTT;
import geso.dms.distributor.db.sql.dbutils;

public class KhoTT implements IKhoTT{

	 String id;
	String TenKho="";
	 String DienGiai="";
	String TrangThai="";
	String NgayTao="";
	String NgaySua="";
	String NguoiTao="";
	String NguoiSua="";
	String Message="";
	List<KhoTT> listkho=new ArrayList<KhoTT>();
	public KhoTT(){
	
	}
	public KhoTT(String id){
		// TODO Auto-generated method stub
		String sql_getdata="SELECT     K.PK_SEQ, K.TEN, K.NGAYTAO, K.NGAYSUA, K.TRANGTHAI, K.DIENGIAI, NT.TEN AS NGUOITAO, NS.TEN AS NGUOISUA "+
						   " FROM         dbo.KHOTT AS K INNER JOIN  dbo.NHANVIEN AS NT ON K.NGUOITAO = NT.PK_SEQ INNER JOIN "+
                           " dbo.NHANVIEN AS NS ON K.NGUOISUA = NS.PK_SEQ WHERE     (K.TRANGTHAI <> '2') and K.PK_SEQ= "+id;
		
		//System.out.println(sql_getdata);
		dbutils db=new dbutils();
		ResultSet rs=db.get(sql_getdata);
		if(rs!=null){
			try{
				while(rs.next()){
				
				this.DienGiai=rs.getString("diengiai");
				this.id= rs.getString("pk_seq");
			    this.Message="";
				this.NgaySua= rs.getString("ngaysua");
			 	this.NgayTao= rs.getString("ngaytao");
				this.NguoiSua= rs.getString("nguoisua");
				this.NguoiTao= rs.getString("nguoitao");
				this.TenKho= rs.getString("ten");
			    this.TrangThai=	rs.getString("trangthai");

				}
			}catch(Exception er){
				
			}
		}
	}
	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return this.id;
	}

	@Override
	public void setId(String id) {
		// TODO Auto-generated method stub
		this.id=id;
	}

	@Override
	public String getTen() {
		// TODO Auto-generated method stub
		return this.TenKho;
	}

	@Override
	public void setTen(String ten) {
		// TODO Auto-generated method stub
		this.TenKho=ten;
	}

	@Override
	public String getDiengiai() {
		// TODO Auto-generated method stub

	return this.DienGiai;
	}

	@Override
	public void setDiengiai(String _diengiai) {
		// TODO Auto-generated method stub
		this.DienGiai=_diengiai;
	}

	@Override
	public String getTrangthai() {
		// TODO Auto-generated method stub
		return this.TrangThai;
	}

	@Override
	public void setTrangthai(String trangthai) {
		// TODO Auto-generated method stub
		this.TrangThai=trangthai;
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
	public String getNguoitao() {
		// TODO Auto-generated method stub
		return this.NguoiTao;
	}

	@Override
	public void setNguoitao(String _nguoitao) {
		// TODO Auto-generated method stub
		this.NguoiTao =_nguoitao;
	}

	@Override
	public String getNguoisua() {
		// TODO Auto-generated method stub
		return this.NguoiTao;
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
	public boolean saveNewKho() {
		// TODO Auto-generated method stub
		dbutils db=new dbutils();
		try{
		db.getConnection().setAutoCommit(false);
		String query ="insert into khoTT values(N'" + this.TenKho + "','"+ this.NgayTao + "','" + this.NgaySua + "','" + this.NguoiTao + "','" + this.NguoiSua + "','" + this.TrangThai + "',N'" + this.DienGiai + "')";
		if(!db.update(query)){//cap nhat khong dc
			this.Message="Khong The tao moi kho trung tam nay, vui long kiem tra lai";
			return false;
		}
		//Thuc hien insert hang vao kho
		query = "select IDENT_CURRENT('khott')as khottId";
		ResultSet rs = db.get(query);
		try{
			rs.next();
			String khoId = rs.getString("khottId");
			
			query = "select pk_seq,ma from sanpham where trangthai!='2'";
			rs = db.get(query);
		
			while(rs.next()){
				query = "insert into tonkhoicp(kho,masp,stock,booked,AVAILABLE) values("+ khoId +",'" + rs.getString("ma") + "',0,0,0)";
				if(!db.update(query)){
					db.get("rollback");
					this.Message="San pham dua vao kho khong the thuc hien";
					return false;
				}
				//System.out.println(query);
			}
		}catch(Exception e){}
		db.getConnection().commit();
		db.getConnection().setAutoCommit(true);
	
		}catch(Exception er){
			db.get("rollback");
			this.Message=er.toString();
			return false;
		}
		return true;
	}

	@Override
	public boolean UpdateKho() {
		// TODO Auto-generated method stub
		dbutils db=new dbutils();
		String query ="update  khoTT set Ten=N'" + this.TenKho + "',Ngaysua= '" + this.NgaySua + "',nguoisua= '" + this.NguoiSua + "',trangthai= '" + this.TrangThai + "',diengiai= N'" + this.DienGiai + "'  where pk_seq=" + this.id;
		if(!db.update(query)){//cap nhat khong dc
			System.out.println("Khong sua duoc :" + query);
			return false;
		}
		return true;
	}

	@Override
	public boolean Delete() {
		// TODO Auto-generated method stub
		dbutils db=new dbutils();
		String query ="update  khoTT set trangthai= '2'where pk_seq=" + this.id;
		if(!db.update(query)){//cap nhat khong dc
			System.out.println("Khong sua duoc :" + query);
			return false;
		}
		return true;
	}


	public void setListkho(String sql) {
		// TODO Auto-generated method stub
		String sql_getdata="SELECT     K.PK_SEQ, K.TEN, K.NGAYTAO, K.NGAYSUA, K.TRANGTHAI, K.DIENGIAI, NT.TEN AS NGUOITAO, NS.TEN AS NGUOISUA "+
						   " FROM         dbo.KHOTT AS K INNER JOIN  dbo.NHANVIEN AS NT ON K.NGUOITAO = NT.PK_SEQ INNER JOIN "+
                           " dbo.NHANVIEN AS NS ON K.NGUOISUA = NS.PK_SEQ WHERE     (K.TRANGTHAI <> '2') ";
		if(!sql.equals("")){
			sql_getdata=sql;
		}
		//System.out.println(sql_getdata);
		dbutils db=new dbutils();
		ResultSet rs=db.get(sql_getdata);
		if(rs!=null){
			try{
				while(rs.next()){
				KhoTT kho=new KhoTT();
				kho.setDiengiai(rs.getString("diengiai"));
				kho.setId(rs.getString("pk_seq"));
				kho.setMessage("");
				kho.setNgaysua(rs.getString("ngaysua"));
				kho.setNgaytao(rs.getString("ngaytao"));
				kho.setNguoisua(rs.getString("nguoisua"));
				kho.setNguoitao(rs.getString("nguoitao"));
				kho.setTen(rs.getString("ten"));
				kho.setTrangthai(rs.getString("trangthai"));
				this.listkho.add(kho);
				}
			}catch(Exception er){
				
			}
		}
	}

	@Override
	public List<KhoTT> getListKho() {
		// TODO Auto-generated method stub
		return this.listkho;
	}

}
