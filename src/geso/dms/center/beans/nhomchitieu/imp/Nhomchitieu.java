package geso.dms.center.beans.nhomchitieu.imp;
import geso.dms.center.beans.nhomchitieu.INhomchitieu;
import geso.dms.center.beans.nhomchitieu.imp.Nhomchitieu;
import geso.dms.center.db.sql.*;

import java.sql.ResultSet;
import java.util.Enumeration;
import java.util.Hashtable;
import java.io.Serializable;

public class Nhomchitieu implements INhomchitieu, Serializable
{
	private static final long serialVersionUID = -9217977546733690415L;
	String id;
	String ten;
	String diengiai;
	String trangthai;	
	String ngaytao;
	String ngaysua;
	String nguoitao;
	String nguoisua;
	String msg;
	ResultSet spList;
	ResultSet spSelected;
	
	ResultSet dvkdList;
	ResultSet nhList;
	ResultSet clList;
	String dvkdId;
	String nhId;
	String clId;
	String[] nhom;
	String[] sanpham;
	ResultSet kenh;
	String kenhId;
	boolean search = false;
	String loaidk;
	String tongluong;
	Hashtable<String, String> sanpham_soluong;
	dbutils db ;
	
	public Nhomchitieu(String[] param)
	{
		this.id = param[0];
		this.ten = param[1];	
		this.diengiai = param[2];
		this.trangthai = param[3];
		this.ngaytao = param[4];
		this.ngaysua = param[5];
		this.nguoitao = param[6];
		this.nguoisua = param[7];	
		this.msg = "";
		this.dvkdId = "0";
		this.nhId = "0";
		this.clId = "0";
		this.kenhId ="";
		this.loaidk = "";
		this.tongluong = "";
		this.db = new dbutils();
		
	}
	
	public Nhomchitieu()
	{
		this.id = "";
		this.ten = "";	
		this.diengiai = "";
		this.trangthai = "";
		this.ngaytao = "";
		this.ngaysua = "";
		this.nguoitao = "";
		this.nguoisua = "";	
		this.msg = "";
		this.dvkdId = "0";
		this.nhId = "0";
		this.clId = "0";
		this.kenhId ="";
		this.db = new dbutils();
		this.TuNgay="";
		this.DenNgay="";
		this.tinhthuong="";
		this.loaidk = "";
		this.tongluong = "";		
		
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
	
	public ResultSet getSpList()
	{
		return this.spList;
	}

	public void setSpList(ResultSet spList)
	{
		this.spList = spList;
	}

	public ResultSet getSpSelected()
	{
		return this.spSelected;
	}

	public void setSpSelected(ResultSet spSelected)
	{
		this.spSelected = spSelected;
	}


	public ResultSet getDvkdList()
	{
		return this.dvkdList;
	}

	public void setDvkdList(ResultSet dvkdList)
	{
		this.dvkdList = dvkdList;
	}
	
	public ResultSet getNhList()
	{
		return this.nhList;
	}

	public void setNhList(ResultSet nhList)
	{
		this.nhList = nhList;
	}
	
	public ResultSet getCLList()
	{
		return this.clList;
	}

	public void setClList(ResultSet clList)
	{
		this.clList = clList;
	}

	public String getDvkdId()
	{
		return this.dvkdId;
	}

	public void setDvkdId(String dvkdId)
	{
		this.dvkdId = dvkdId;
	}

	public String getNhId()
	{
		return this.nhId;
	}

	public void setNhId(String nhId)
	{
		this.nhId = nhId;
	}

	public String getClId()
	{
		return this.clId;
	}

	public void setClId(String clId)
	{
		this.clId = clId;
	}


	public String[] getSanpham()
	{
		return this.sanpham;
	}

	public void setSanpham(String[] sanpham)
	{
		this.sanpham = sanpham;
	}
	
	public boolean saveNewNkm()
	{
		String command;
		try
		{
			db.getConnection().setAutoCommit(false);
		command = "insert into nhomsanpham(diengiai, nsp_parent_fk, loaithanhvien, ngaytao, ngaysua, nguoitao, nguoisua, trangthai, ten, type, loainhom,TuNgay,DenNgay,TinhThuong, Loaidk, Tongluong) " +
				"values(N'" + this.diengiai + "', '0', '2', '" + this.ngaytao + "', '" + this.ngaysua +"', " +
				"	'" + this.nguoitao + "', '" + this.nguoisua + "', '0', N'" + this.ten + "','0','3','"+this.TuNgay+"','"+this.DenNgay+"',"+this.tinhthuong+", '"+this.loaidk+"', '"+this.tongluong+"')";
		System.out.println("Insert NKM: "+command);
		 if(!this.db.update(command))
		 {
			 	this.msg = "Không thể cập nhật "+command;
				db.getConnection().rollback();
				return false;
		 }

		command = "select IDENT_CURRENT('nhomsanpham')as nkmId";		
		ResultSet rs = this.db.get(command);
			
			rs.next();
			this.id = rs.getString("nkmId");
	
			if(this.sanpham != null){
				String[] sanphamList = this.sanpham; 
				int size = (this.sanpham).length;
				int m = 0;
				
				while(m < size){
					command = "insert into nhomsanpham_sanpham(sp_fk, nsp_fk) values('" + sanphamList[m] + "', '"+ this.id + "')";
					System.out.println("insert NKM_SP :"+command);
					 if(!this.db.update(command))
					 {
						 	this.msg = "Không thể cập nhật "+command;
							db.getConnection().rollback();
							return false;
					 }
					m++ ;
				}
			}
			
			if(this.sanpham_soluong.size() > 0)
			{
				Enumeration<String> keys = this.sanpham_soluong.keys();
				while(keys.hasMoreElements())
				{
					String key = keys.nextElement();

					command = "update nhomsanpham_sanpham set soluong = '" + sanpham_soluong.get(key).replaceAll(",", "") + "' " + 
							" where nsp_fk = '" + this.id + "' and sp_fk = '" + key + "' ";
					if(!db.update(command))
					{
						this.msg = "3.Khong the tao moi nhomsanpham_sanpham: " + command;
						db.getConnection().rollback();
						return false;
					}
				}
			}
			
			db.getConnection().commit();						
			db.getConnection().setAutoCommit(true);	
		}
		catch (Exception e)
    	{
			geso.dms.center.util.Utility.rollback_throw_exception(db);	
    			this.msg = "Không thể cập nhật "+e.toString();
				return false;
    	}
			
		return true;

	}
	
	public boolean updateNkm(){

		String command;	
		System.out.println(this.diengiai+"xxx"+this.ten);
		try 
    	{
		db.getConnection().setAutoCommit(false);
	
		command ="update nhomsanpham set TuNgay='"+this.TuNgay+"',DenNgay='"+this.DenNgay+"',TinhThuong="+this.tinhthuong+",ten =N'"+ this.ten +"', diengiai = N'"+ this.diengiai +"',ngaysua ='"+ this.ngaysua +"'," +
			" loaidk = '"+this.loaidk+"', tongluong = '"+this.tongluong+"' where pk_seq = '" + this.id + "'";
		System.out.println("update NKM : "+command);
		 if(!this.db.update(command))
		 {
			 	this.msg = "Không thể cập nhật "+command;
				db.getConnection().rollback();
				return false;
		 }
				
		command = "delete from nhomsanpham_sanpham where nsp_fk = '"+ this.id + "'";
		 if(!this.db.update(command))
		 {
			 	this.msg = "Không thể cập nhật "+command;
				db.getConnection().rollback();
				return false;
		 }

		if(this.sanpham != null){
			String[] sanphamList = this.sanpham; 
			int size = (this.sanpham).length;
			int m = 0;
				
			while(m < size){
				command = "insert into nhomsanpham_sanpham(sp_fk, nsp_fk) values('" + sanphamList[m] + "','" + this.id + "')";
				 if(!this.db.update(command))
				 {
					 	this.msg = "Không thể cập nhật " + command;
						db.getConnection().rollback();
						return false;
				 }
				m++ ;
			}
				
		}
		
		if(this.sanpham_soluong.size() > 0)
		{
			Enumeration<String> keys = this.sanpham_soluong.keys();
			while(keys.hasMoreElements())
			{
				String key = keys.nextElement();

				command = "update nhomsanpham_sanpham set soluong = '" + sanpham_soluong.get(key).replaceAll(",", "") + "' " + 
						" where nsp_fk = '" + this.id + "' and sp_fk = '" + key + "' ";
				if(!db.update(command))
				{
					this.msg = "3.Khong the tao moi nhomsanpham_sanpham: " + command;
					db.getConnection().rollback();
					return false;
				}
			}
		}
		
		db.getConnection().commit();						
		db.getConnection().setAutoCommit(true);	
    	} 
    	catch (Exception e)
    	{

    			this.msg = "Không thể cập nhật "+e.toString();
    			geso.dms.center.util.Utility.rollback_throw_exception(db);	
				return false;

    	}
			
		return true;
	}

	private ResultSet createDvkdRS(){  	
		
		//ResultSet dvkdRS =  this.db.get("select pk_seq, diengiai from donvikinhdoanh where trangthai='1' ");
		ResultSet dvkdRS =  this.db.get("select a.pk_seq, a.diengiai from donvikinhdoanh a,Nhacungcap_dvkd b where a.pk_seq = b.dvkd_fk and a.trangthai='1' and b.checked ='1'");
		
		return dvkdRS;
	}
	
	private ResultSet createNhRS(){
		ResultSet nhRS;
		if (!this.dvkdId.equals("0")){
			nhRS =  this.db.get("select pk_seq, ten from nhanhang where trangthai='1' and dvkd_fk='" + this.dvkdId + "'");
		}else{
			nhRS =  null;	
		}
			
		return nhRS;
		
	}

	private ResultSet createClRS() {  	
		ResultSet clRS;
	
		if(!this.nhId.equals("0")){
			clRS = this.db.get("select distinct a.pk_seq, a.ten from chungloai a, nhanhang_chungloai b where trangthai='1' and a.pk_seq = b.cl_fk and b.nh_fk = '" + this.nhId + "'");
		}else{
			clRS = null;
		}
		return clRS;
			
	}
	
	private void createSpRS(){  	

		String query;
		String temp = "";
		if (this.id.length()>0){
			if (this.sanpham != null){
				query = "select pk_seq, ma, ten from sanpham where";
				temp =  "select pk_seq from sanpham where";
				for(int i=0; i < this.sanpham.length; i++){
					if (i==0){
						query = query + " pk_seq = '" + this.sanpham[i] + "'";
						temp = temp + " pk_seq = '" + this.sanpham[i] + "'";
					}else{						
						query = query + " or pk_seq = '" + this.sanpham[i] + "'";
						temp = temp + " or pk_seq = '" + this.sanpham[i] + "'";
					}
				}				
			}else{
				query = "select a.pk_seq, a.ma, a.ten, b.soluong from sanpham a, nhomsanpham_sanpham b where a.pk_seq = b.sp_fk and b.nsp_fk = '" + this.id + "'";
				
				this.sanpham_soluong = new Hashtable<String, String>();
				
				ResultSet rs = db.get(query);
				if(rs != null)
				{
					try
					{
						while(rs.next())
						{
							sanpham_soluong.put(rs.getString("pk_seq"), rs.getString("soluong"));
							System.out.println("sanpham_soluong "+rs.getString("pk_seq")+", "+rs.getString("soluong"));
						}
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
				}
			}
			
			System.out.print(query);
			this.spSelected = this.db.get(query);
		}else{
			if (this.sanpham != null){
				query = "select pk_seq, ma, ten from sanpham where";
				temp =  "select pk_seq from sanpham where";
				for(int i=0; i < this.sanpham.length; i++){
					if (i==0){
						query = query + " pk_seq = '" + this.sanpham[i] + "'";
						temp = temp + " pk_seq = '" + this.sanpham[i] + "'";
					}else{						
						query = query + " or pk_seq = '" + this.sanpham[i] + "'";
						temp = temp + " or pk_seq = '" + this.sanpham[i] + "'";
					}
				}				
				this.spSelected = this.db.get(query);
			}			
		}
		
		//query = "select pk_seq, ma, ten from sanpham  where trangthai = '1' and pk_seq not in (select sp_fk from nhomsanpham_sanpham where nsp_fk = '" + this.id +"')";
		
		if (this.id.length()>0){
			query = "select pk_seq, ma, ten from sanpham  where trangthai = '1' and pk_seq not in (select sp_fk from nhomsanpham_sanpham where nsp_fk = '" + this.id + "')";
		}else{
			query = "select pk_seq, ma, ten from sanpham  where trangthai = '1'";
		}
		if (!this.dvkdId.equals("0")){
			query = query + " and dvkd_fk ='" + this.dvkdId + "'";

		}
		
		if (!this.nhId.equals("0")){
			query = query + " and nhanhang_fk = '" + this.nhId + "'";

		}
		
		if (!this.clId.equals("0")){
			query = query + " and chungloai_fk = '" + this.clId + "'";

		}
		if (this.sanpham != null){
			query = query +  " and pk_seq not in (" + temp + ")";
		}
		if(this.kenhId.length()>0)
		{
			query = query + " and pk_seq in (select sanpham_fk from bgmuanpp_sanpham where bgmuanpp_fk in (select pk_seq from banggiamuanpp where kenh_fk ='"+ kenhId +"')) ";
		}
		query = query + " order by ten";
		System.out.println(query);
		this.spList = this.db.get(query);		
		
	}
	
	public void UpdateRS(){
		this.dvkdList = createDvkdRS();
		this.nhList = createNhRS();
		this.clList = createClRS();	
		CreateKenh();
		createSpRS();
	}

	
	public void setkenhId(String kenhId) {
		
		this.kenhId = kenhId;
	}

	
	public String getKenhId() {
		
		return this.kenhId;
	}

	
	public void setKenh(ResultSet kenh) {
		
		this.kenh = kenh;
	}

	
	public ResultSet getKenh() {
		
		return this.kenh;
	}
	void CreateKenh()
	{
		this.kenh = db.get("select * from kenhbanhang");
	}

	String TuNgay,DenNgay,tinhthuong;
	
	@Override
	public String getTuNgay()
	{
		
		return TuNgay;
	}

	@Override
	public void setTuNgay(String TuNgay)
	{
		this.TuNgay = TuNgay;
	}

	@Override
	public String getDenNgay()
	{
		return this.DenNgay;
	}

	@Override
	public void setDenNgay(String DenNgay)
	{
		this.DenNgay =  DenNgay;
	}

	@Override
	public String getTinhThuong()
	{
		return this.tinhthuong;
	}

	@Override
	public void setTinhThuong(String tinhthuong)
	{
		this.tinhthuong =tinhthuong;
	}

	@Override
	public String getLoaiDk() {
		return this.loaidk;
	}

	@Override
	public void setLoaiDk(String loaidk) {
		this.loaidk = loaidk;
	}

	@Override
	public String getTongluong() {
		return this.tongluong;
	}

	@Override
	public void setTongluong(String tongluong) {
		this.tongluong = tongluong;
	}

	@Override
	public Hashtable<String, String> getSanpham_Soluong() {
		return this.sanpham_soluong;
	}

	@Override
	public void setSanpham_Soluong(Hashtable<String, String> sanpham_soluong) {
		this.sanpham_soluong = sanpham_soluong;
	}
	
}


