package geso.dms.center.beans.nhomkhachhang.imp;
import geso.dms.center.beans.nhomkhachhang.INhomkhachhang;
import geso.dms.center.db.sql.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.Serializable;

public class Nhomkhachhang implements INhomkhachhang, Serializable
{
	private static final long serialVersionUID = -9217977546733690415L;
	String id;
	String diengiai;
	String trangthai;	
	String ngaytao;
	String ngaysua;
	String nguoitao;
	String nguoisua;
	String msg;
	ResultSet khList;
	ResultSet khSelected;
	
	ResultSet vungList;
	ResultSet kvList;
	ResultSet nppList;
	
	ResultSet RsVTCh;
	ResultSet RsHCh;
	ResultSet RsLCh;
	
	String VtchId;
	String hchId;
	String LchId;
	String TenNhom = "";
	String vungId;
	String kvId;
	String nppId;
	String[] khachhang;
	boolean search = false;
	
	dbutils db ;
	
	public Nhomkhachhang(String[] param)
	{
		this.id = param[0];
		this.diengiai = param[1];
		this.trangthai = param[2];
		this.ngaytao = param[3];
		this.ngaysua = param[4];
		this.nguoitao = param[5];
		this.nguoisua = param[6];	
		this.TenNhom = param[7];
		this.msg = "";
		this.vungId = "0";
		this.kvId = "0";
		this.nppId = "0";
		this.db = new dbutils();
		this.VtchId="";
		this.LchId="";
		this.hchId="";
	}
	
	public Nhomkhachhang()
	{
		this.id = "";
		this.diengiai = "";
		this.trangthai = "";
		this.ngaytao = "";
		this.ngaysua = "";
		this.nguoitao = "";
		this.nguoisua = "";	
		this.msg = "";
		this.vungId = "0";
		this.kvId = "0";
		this.nppId = "0";
		this.db = new dbutils();
		
		this.VtchId="";
		this.LchId="";
		this.hchId="";
	}

	public String getId()
	{
		return this.id;
	}

	public void setId(String id)
	{
		this.id = id;
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
	
	public ResultSet getKhList()
	{
		return this.khList;
	}

	public void setKhList(ResultSet khList)
	{
		this.khList = khList;
	}

	public ResultSet getKhSelected()
	{
		return this.khSelected;
	}

	public void setKhSelected(ResultSet KhSelected)
	{
		this.khSelected = KhSelected;
	}


	public ResultSet getVungList()
	{
		return this.vungList;
	}

	public void setVungList(ResultSet vungList)
	{
		this.vungList = vungList;
	}
	
	public ResultSet getKvList()
	{
		return this.kvList;
	}

	public void setKvList(ResultSet kvList)
	{
		this.kvList = kvList;
	}
	
	public ResultSet getNppList()
	{
		return this.nppList;
	}

	public void setNppList(ResultSet nppList)
	{
		this.nppList = nppList;
	}

	public String getVungId()
	{
		return this.vungId;
	}

	public void setVungId(String vungId)
	{
		this.vungId = vungId;
	}

	public String getKvId()
	{
		return this.kvId;
	}

	public void setKvId(String kvId)
	{
		this.kvId = kvId;
	}

	public String getNppId()
	{
		return this.nppId;
	}

	public void setNppId(String nppId)
	{
		this.nppId = nppId;
	}


	public String[] getKhachhang()
	{
		return this.khachhang;
	}

	public void setKhachhang(String[] khachhang)
	{
		this.khachhang = khachhang;
	}
	
	public boolean saveNewNkh(){
		String command;
		try{
			db.getConnection().setAutoCommit(false);
			command = "insert into nhomkhachhang (ten, diengiai, NGAYTAO, NGAYSUA, NGUOITAO, NGUOISUA, TRANGTHAI) "
					+ "\n values(N'" + this.TenNhom + "',N'" + this.diengiai + "', '" + this.ngaytao + "', '" + this.ngaysua +"', '" + this.nguoitao + "', '" + this.nguoisua + "', '"+ this.trangthai +"')";			
			System.out.print(command);
//			this.db.update(command);
			if (!db.update(command)){
				db.getConnection().rollback();
				this.msg = "Lỗi tạo mới nhóm khách hàng: " + command;
				return false; 
			}

			command = "select SCOPE_IDENTITY() as nkhId";
			ResultSet rs = this.db.get(command);
			rs.next();
			this.id = rs.getString("nkhId");

			if(this.khachhang != null){
				String[] khachhangList = this.khachhang; 
				int size = (this.khachhang).length;
				int m = 0;

				while(m < size){
					command = "insert into nhomkhachhang_khachhang(kh_fk, nkh_fk) values('" + khachhangList[m] + "', '"+ this.id + "')";
					System.out.print(command);
//					this.db.update(command);
					if (!db.update(command)){
						db.getConnection().rollback();
						this.msg = "Lỗi tạo mới nhóm khách hàng: " + command;
						return false; 
					}
					m++ ;
				}
			}
			this.db.getConnection().commit();
		}catch(Exception e){
			e.printStackTrace();
			try {
				this.db.getConnection().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return false;
		}finally{ try { this.db.getConnection().setAutoCommit(true); } catch (SQLException e) { e.printStackTrace(); } }
		return true;
	}
	
	public boolean updateNkh(){

		String command;		
		try {
			db.getConnection().setAutoCommit(false);
			command ="update nhomkhachhang set ten = N'" + this.TenNhom + "', diengiai = N'"+ this.diengiai +"',ngaysua ='"+ this.ngaysua +"', trangthai ='" + this.trangthai + "' where pk_seq = '" + this.id + "'";
			System.out.println("update: " + command);
			//		this.db.update(command);
			if (!db.update(command)){
				db.getConnection().rollback();
				this.msg = "Lỗi cập nhật nhóm khách hàng: " + command;
				return false; 
			}

			command = "delete from nhomkhachhang_khachhang where nkh_fk = '"+ this.id + "'";
//			this.db.update(command);
			if (!db.update(command)){
				db.getConnection().rollback();
				this.msg = "Lỗi cập nhật nhóm khách hàng: " + command;
				return false; 
			}

			if(this.khachhang != null){
				String[] khachhangList = this.khachhang; 
				int size = (this.khachhang).length;
				int m = 0;

				while(m < size){
					command = "insert into nhomkhachhang_khachhang(kh_fk, nkh_fk) values('" + khachhangList[m] + "','" + this.id + "')";
//					this.db.update(command);
					if (!db.update(command)){
						db.getConnection().rollback();
						this.msg = "Lỗi cập nhật nhóm khách hàng: " + command;
						return false; 
					}
					m++ ;
				}
			}
			this.db.getConnection().commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				this.db.getConnection().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return false;
		}finally{ try { this.db.getConnection().setAutoCommit(true); } catch (SQLException e) { e.printStackTrace(); } }
		return true;
	}

	private void createVungRS(){  	
		
	this.vungList =  this.db.get("select pk_seq, diengiai from vung  where trangthai='1'");
		
	
	}
	
	private void createKvRS(){
		ResultSet kvRS;
		if (!this.vungId.equals("0")){
			this.kvList =  this.db.get("select pk_seq, diengiai from khuvuc where trangthai='1' and vung_fk='" + this.vungId + "'");
		}else{
			this.kvList  =  null;	
		}
	
		
	}

	private void createNppRS() {  	
		ResultSet nppRS;
	
		if(!this.kvId.equals("0")){
			nppRS = this.db.get("select distinct pk_seq, ten from nhaphanphoi where trangthai='1' and khuvuc_fk ='" + this.kvId + "'");
		}else{
			nppRS = null;
		}
		this.nppList=nppRS;
	
			
	}
	
	
	private void createKhRS(){  	

		String query = "";
		String temp = "";
		if (this.khachhang != null){
			query = "select pk_seq,smartid, ten from khachhang where trangthai=1 and ";
			temp =  "select pk_seq,smartid from khachhang where trangthai=1 and ";
			
			String chuoi="";
			for(int i=0; i < this.khachhang.length; i++){
				
			
				if (i==0){
					chuoi =  this.khachhang[i];
					
				}else{						
					chuoi = chuoi + "," + this.khachhang[i];
				}
			}			
			
			if(chuoi.length() >0){
				query=query+ "  and pk_seq  in ("+chuoi+")";
				temp = temp + "  and pk_seq  in ("+chuoi+")";
			}
			query=query+ "  and npp_fk="+this.nppId;
			temp = temp + " and npp_fk="+this.nppId;
			
			this.khSelected = this.db.get(query);
		}else{
			this.khSelected = null;
		}
		
		
		if (this.id.length() > 0){
			if (this.khachhang == null){
				query = "select a.pk_seq, a.ten,a.smartid from khachhang a, nhomkhachhang_khachhang b where a.pk_seq = b.kh_fk and b.nkh_fk = '" + this.id + "'";
				this.khSelected = this.db.get(query);	
			
				query = "select a.pk_seq, a.ten,a.smartid from khachhang a, nhaphanphoi b, khuvuc c, vung d  where c.vung_fk=d.pk_seq and b.khuvuc_fk=c.pk_seq and a.npp_fk=b.pk_seq and a.trangthai = '1' and a.pk_seq not in (select kh_fk from nhomkhachhang_khachhang where nkh_fk = '" + this.id + "')";
			}else{
				query = "select a.pk_seq, a.ten,a.smartid from khachhang a, nhaphanphoi b, khuvuc c, vung d  where c.vung_fk=d.pk_seq and b.khuvuc_fk=c.pk_seq and a.npp_fk=b.pk_seq and a.trangthai = '1' and a.pk_seq not in (select kh_fk from nhomkhachhang_khachhang where nkh_fk = '" + this.id + "') and a.pk_seq not in(" + temp + ")";

			}
		}else{
			if (this.khachhang != null){
				System.out.println("vao1");
				query = "select a.pk_seq, a.ten,a.smartid from khachhang a, nhaphanphoi b, khuvuc c, vung d  where c.vung_fk=d.pk_seq and b.khuvuc_fk=c.pk_seq and a.npp_fk=b.pk_seq and a.trangthai = '1' and a.pk_seq not in(" + temp + ")";
			}else{
				System.out.println("vao2");
				query = "" ;
			}
		}
		
		if (!this.vungId.equals("0")){
			System.out.println("vao3");
			query =  "select a.pk_seq, a.ten,a.smartid from khachhang a "+
			 "inner join nhaphanphoi b on a.npp_fk = b.pk_seq "+ 
			 "inner join khuvuc c on b.khuvuc_fk = c.pk_seq "+
			 "inner join vung d on d.pk_seq = c.vung_fk "+ 
			 "left join hangcuahang hch on hch.pk_seq = a.hch_fk "+
			 "left join loaicuahang lch on lch.pk_seq = a.lch_fk "+
			 "left join vitricuahang vtch on vtch.pk_seq = a.vtch_fk  where 1=1 ";
			query = query + " and d.pk_seq ='" + this.vungId + "'";
		}
		if (!this.kvId.equals("0")){
			query = query + " and c.pk_seq = '" + this.kvId + "'";
		}
		if (this.khachhang != null){
			query = query +  " and a.pk_seq not in (" + temp + ")";
		}
		
		if(this.hchId.length() >1){
			query +=   " and hch_fk="+this.hchId;
		}
		
		if(this.VtchId.length() >1){
			query +=   " and vtch_fk="+this.VtchId;
		}
		
		if(this.LchId.length() >1){
			query +=   " and lch_fk="+this.LchId;
		}
		
		query +=   " and a.trangthai = '1'  and b.pk_seq = '" + this.nppId + "' order by a.ten";
		System.out.print( "Lay Danh Sach Khashc Hang : "+ query);
		this.khList = this.db.get(query);		
		
	}
	
	public void UpdateRS(){
			createVungRS();
			createKvRS();
			 createNppRS();
			this.RsVTCh =  this.db.get("select diengiai , pk_seq from vitricuahang where trangthai='1'");
			this.RsLCh =  this.db.get("select diengiai , pk_seq  from loaicuahang where trangthai='1' ");
			this.RsHCh =  this.db.get("select diengiai, pk_seq from hangcuahang where trangthai='1' ");
		createKhRS();
	}
	public ResultSet getRsViTriCuaHang() {
		return this.RsVTCh;
	}
	public ResultSet getRsHangCuaHang() {
	
		return this.RsHCh;
	}


	public ResultSet getRsLoaiCuaHang() {
	
		return this.RsLCh;
	}


	public String getHchID() {
	
		return this.hchId;
	}


	public void setHchID(String HchID) {
	
		this.hchId=HchID;
	}


	public String getVtchID() {
	
		return this.VtchId;
	}


	public void setVtch(String Vtch) {
	
		this.VtchId=Vtch;
	}


	public String getLchID() {
	
		return this.LchId;
	}


	public void setLchID(String LchID) {
	
		this.LchId=LchID;
	}


	public String getTenNhom() {
	
		return this.TenNhom;
	}


	public void setTenNhom(String TenNhom) {
	
		this.TenNhom = TenNhom;
	}
	
}


