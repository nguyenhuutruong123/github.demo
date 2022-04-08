package geso.dms.center.beans.nganhhang.imp;
import geso.dms.center.beans.nganhhang.INganhHang;
import geso.dms.center.db.sql.*;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.nhat.replacement.TaskReplacement;

public class NganhHang implements INganhHang
{
	private static final long serialVersionUID = -9217977546733610214L;
	String userId;
	String id;
	String ten;
	String diengiai;
	String trangthai;
	String ngaytao;
	String nguoitao;
	String ngaysua;
	String nguoisua;
	String msg;
	double thuexuat;
	String dvkdId;
	String dvkdTen;
	ResultSet dvkdList;
	
	dbutils db;
	
	public NganhHang(String[] param)
	{
		
		this.db = new dbutils();
		
		this.id = param[0];
		this.ten = param[1];
		this.diengiai = param[2];
		this.trangthai = param[3];
		this.ngaytao = param[4];
		this.nguoitao = param[5];
		this.ngaysua = param[6];
		this.nguoisua = param[7];
		this.dvkdId = param[8];
		this.dvkdTen = param[9];
		this.msg = "";
		this.dvkdList = createDvkdList();
		this.db = new dbutils();
	}
	
	public NganhHang(String id)
	{	
		this.db = new dbutils();
		thuexuat=0;
		this.id = id;
		this.ten = "";
		this.diengiai = "";
		this.trangthai = "1";
		this.ngaytao = "";
		this.nguoitao = "";
		this.ngaysua = "";
		this.nguoisua = "";
		this.msg = "";
		this.dvkdId = "";
		this.dvkdTen = "";
		this.dvkdList = createDvkdList();
		if(id.length() > 0)
			this.init();
	}
	
	public double getThuexuat() {
		return thuexuat;
	}
	public void setThuexuat(double thuexuat) {
		this.thuexuat = thuexuat;
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
	
	public String getDiengiai()
	{
		return this.diengiai;
	}

	public void setDiengiai(String diengiai) 
	{
		this.diengiai = diengiai;
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

	public String getMessage() 
	{
		return this.msg;
	}

	public void setMessage(String msg) 
	{
		this.msg = msg;
	}

	@Override
	public String getDvkdId() {
		return this.dvkdId;
	}

	@Override
	public void setDvkdId(String value) {
		this.dvkdId = value;
	}

	@Override
	public ResultSet getDvkdList() {
		return this.dvkdList;
	}

	@Override
	public void setDvkdList(ResultSet dvkdlist) {
		this.dvkdList = dvkdlist;
	}

	@Override
	public String getDvkdTen() {
		return this.dvkdTen;
	}

	@Override
	public void setDvkdTen(String value) {
		this.dvkdTen = value;
	}
	
	public ResultSet createDvkdList(){//chi cho load don vi kinh doanh nao co checked=1
		return db.get("select distinct a.pk_seq, a.donvikinhdoanh from donvikinhdoanh a,nhacungcap_dvkd b where a.pk_seq = b.DVKD_fk and b.checked ='1' and a.trangthai ='1' order by a.donvikinhdoanh");
	}

	public boolean create()
	{
		try{
			this.db.getConnection().setAutoCommit(false);
			this.ngaytao = getDateTime();
			this.nguoitao = this.userId;
			
			/*String command = "insert into nganhhang(thuexuat,ten, diengiai, trangthai, dvkd_fk, ngaytao, ngaysua, nguoitao, nguoisua)" +
					" values("+ this.thuexuat+ " ,N'" + this.ten + "',N'" + this.diengiai + "'," + this.trangthai + ",'" + this.dvkdId + "','" + this.ngaytao + "','" + this.ngaytao + "','" + this.userId + "','" + this.userId + "')"; */
			
			String command = "insert into nganhhang(thuexuat,ten, diengiai, trangthai, dvkd_fk, ngaytao, ngaysua, nguoitao, nguoisua)" +
					" values(? ,?,?,?,?,?,?,?,?)"; 
			
			Object[] data;
			data= new Object[]   { this.thuexuat, this.ten , this.diengiai , this.trangthai , this.dvkdId, this.ngaytao, this.ngaytao , this.userId ,this.userId };
			
			db.viewQuery(command, data);
			if( this.db.updateQueryByPreparedStatement(command, data)!=1 ) {
				//this.msg = "Khong the tao moi nganh hang: " + command;
				this.msg = "1. Lỗi trong quá trình lưu dữ liệu, vui lòng liên hệ admin để xử lý";
				geso.dms.center.util.Utility.rollback_throw_exception(this.db);
				return false;
			}
			
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		}catch(Exception er){
			this.msg="Khong The Them Nganh Hang Nay , Loi Dong Lenh Sau :" + er.toString();
			geso.dms.center.util.Utility.rollback_throw_exception(this.db);
			return false;
		}
		return true;
	}

	public boolean update() 
	{
		this.ngaysua = getDateTime();
		this.nguoisua = this.userId;
		
		//String command ="update nganhhang set  thuexuat="+this.thuexuat+",ten = N'" + this.ten + "', diengiai = N'" + this.diengiai + "', trangthai='" + this.trangthai + "', ngaysua = '" + this.ngaysua + "', nguoisua = '" + this.userId + "' where pk_seq = '" + this.id + "'";
		String command ="update nganhhang set dvkd_fk = ?, thuexuat=?,ten = ?, diengiai =?, trangthai=?, ngaysua = ?, nguoisua = ? where pk_seq = ? ";
		
		Object[] data;
		data= new Object[]   {this.dvkdId ,this.thuexuat, this.ten , this.diengiai , this.trangthai , this.ngaysua , this.userId , this.id };
		
		db.viewQuery(command, data);
		if( this.db.updateQueryByPreparedStatement(command, data)!=1 ) {
			this.msg = "2. Lỗi trong quá trình lưu dữ liệu, vui lòng liên hệ admin để xử lý";
			//this.msg = "Khong the cap nhat: " + command;
			geso.dms.center.util.Utility.rollback_throw_exception(this.db);
			return false;
		}

		return true; 
	}

	private void init()
	{	
		String query = " select a.thuexuat,a.pk_seq as id, a.ten as ten, a.diengiai, a.trangthai, a.dvkd_fk, a.ngaytao, b.ten as nguoitao, a.ngaysua, c.ten as nguoisua from nganhhang a, nhanvien b, nhanvien c ";
		query = query + " where a.nguoitao = b.pk_seq and a.nguoisua = c.pk_seq and a.pk_seq= '"+ this.id + "' "; 
        ResultSet rs =  this.db.get(query);
        try{
            rs.next();        	
        	this.id = rs.getString("id");
        	this.ten = rs.getString("ten");
        	this.diengiai = rs.getString("diengiai");
        	this.dvkdId = rs.getString("dvkd_fk");
        	this.trangthai = rs.getString("trangthai");
        	this.ngaytao = rs.getString("ngaytao");
        	this.nguoitao = rs.getString("nguoitao");
        	this.ngaysua = rs.getString("ngaysua");
        	this.nguoisua = rs.getString("nguoisua");
        	this.thuexuat = rs.getDouble("thuexuat");
       	}catch(Exception e){}
	}
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}	

	public void DBClose(){
		if (this.db != null) 
			this.db.shutDown();
	}
}
