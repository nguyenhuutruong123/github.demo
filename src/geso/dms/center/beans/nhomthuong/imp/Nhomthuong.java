package geso.dms.center.beans.nhomthuong.imp;
import geso.dms.center.beans.nhomthuong.INhomthuong;
import geso.dms.center.beans.nhomthuong.imp.Nhomthuong;
import geso.dms.center.db.sql.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class Nhomthuong implements INhomthuong, Serializable
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
	String type;
	boolean search = false;
	dbutils db ;
	
	String tungay = "";
	String denngay = "";
	String NspTT = "";
	public Nhomthuong(String[] param)
	{
		this.id = param[0];
		this.ten = param[1];	
		this.diengiai = param[2];
		this.trangthai = param[3];
		this.ngaytao = param[4];
		this.ngaysua = param[5];
		this.nguoitao = param[6];
		this.nguoisua = param[7];	
		this.type = param[8];
		this.tungay = param[9];
		this.denngay = param[10];
		this.NspTT = param[11];
		this.msg = "";
		this.dvkdId = "0";
		this.nhId = "0";
		this.clId = "0";
		this.kenhId ="";
		this.db = new dbutils();
		
	}
	
	public Nhomthuong()
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
		this.type = "";
		this.tungay = "";
		this.denngay = "";
		this.db = new dbutils();
		
	}

	
	public String getTungay() {
		return tungay;
	}
	public void setTungay(String tungay) {
		this.tungay = tungay;
	}
	public String getDenngay() {
		return denngay;
	}
	public void setDenngay(String denngay) {
		this.denngay = denngay;
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
		List<Object> data = new ArrayList<Object>();
		
		try
		{
			db.getConnection().setAutoCommit(false);
		this.type = "4";	
		command = "insert into NHOMSANPHAMCHITIEU(tungay,denngay,diengiai,nsp_parent_fk,loaithanhvien,ngaytao,ngaysua,nguoitao,nguoisua,trangthai,ten,type,loainhom,NHOMSPTT) " +
				"	values(?,?,?,'0','2',?,?,?,?,'0',?,?,'0',?)";
		System.out.println("Insert NKM: "+command);
		
		data.clear();
		data.add(this.tungay);data.add(this.denngay);data.add(this.diengiai);data.add(this.ngaytao);data.add(this.ngaysua);data.add(this.nguoitao);
		data.add(this.nguoisua);data.add(this.ten);data.add(this.type);data.add(this.NspTT);
		
		 if(this.db.updateQueryByPreparedStatement(command, data) != 1)
		 {
			 	this.msg = "Không thể cập nhật "+command;
				db.getConnection().rollback();
				return false;
		 }

			this.id = db.getPk_seq();
	
			if(this.sanpham != null){
				String[] sanphamList = this.sanpham; 
				int size = (this.sanpham).length;
				int m = 0;
				
				while(m < size){
					command = "insert into NHOMSANPHAMCHITIEU_sanpham(sp_fk, nsp_fk) values(?,?)";
					data.clear();
					data.add(sanphamList[m]);data.add(this.id);
					
					System.out.println("insert NKM_SP :"+command);
					 if(this.db.updateQueryByPreparedStatement(command, data) != 1)
					 {
						 	this.msg = "Không thể cập nhật "+command;
							db.getConnection().rollback();
							return false;
					 }
					m++ ;
				}
				
				// kiem tra nhom san pham co 1 san pham thi ghi nhan lai ma sanpham cho nhom
				command ="select count(*) as sodong from NHOMSANPHAMCHITIEU_sanpham where nsp_fk = '"+ this.id + "'";
				ResultSet sqlcheck = db.get(command);
				if(sqlcheck.next())
				{
					if(sqlcheck.getInt("sodong") == 1)
					{
						command = "update a set a.sanpham_fk = c.PK_SEQ,a.spma = c.MA,a.spten = c.TEN from NHOMSANPHAMCHITIEU a inner join NHOMSANPHAMCHITIEU_SANPHAM b on a.PK_SEQ = b.NSP_FK inner join SANPHAM c on c.PK_SEQ = b.SP_FK and a.PK_SEQ =?";
						data.clear();
						data.add(this.id);
						
						if(this.db.updateQueryByPreparedStatement(command, data) != 1)
						{
							this.msg = "Không thể cập nhật "+command;
							db.getConnection().rollback();
							return false;
						}
					}
				}
				sqlcheck.close();
				
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
		List<Object> data = new ArrayList<Object>();
		
		System.out.println(this.diengiai+"xxx"+this.ten);
		try 
    	{
		db.getConnection().setAutoCommit(false);
		
	
		command ="update NHOMSANPHAMCHITIEU set sanpham_fk = null, spma= null,spten = null , tungay =? ,denngay = ?, ten =?, diengiai = ?,ngaysua =? , nguoisua =?, NHOMSPTT = ? " +
			" where pk_seq = ? and trangthai = 0";
		data.clear();
		data.add(this.tungay);data.add(this.denngay);data.add(this.ten);data.add(this.diengiai);data.add(this.ngaysua);data.add(this.nguoisua);data.add(this.NspTT);data.add(this.id);
		
		System.out.println("update NKM : "+command);
		if(this.db.updateQueryByPreparedStatement(command, data) != 1)
		 {
			 	this.msg = "Không thể cập nhật "+command;
				db.getConnection().rollback();
				return false;
		 }
				
		command = "delete from NHOMSANPHAMCHITIEU_sanpham where nsp_fk = " + this.id;
		data.clear();
		data.add(this.id);
		
		if(!this.db.update(command))
		 {
			 	this.msg = "Không thể cập nhật "+command;
				db.getConnection().rollback();
				return false;
		 }

		if(this.sanpham != null)
		{
			String[] sanphamList = this.sanpham; 
			int size = (this.sanpham).length;
			int m = 0;
				
			while(m < size){
				command = "insert into NHOMSANPHAMCHITIEU_sanpham(sp_fk, nsp_fk) values(?,?)";
				data.clear();
				data.add(sanphamList[m]);data.add(this.id);
				
				if(this.db.updateQueryByPreparedStatement(command, data) != 1)
				 {
					 	this.msg = "Không thể cập nhật "+command;
						db.getConnection().rollback();
						return false;
				 }
				m++ ;
			}
			
			// kiem tra nhom san pham co 1 san pham thi ghi nhan lai ma sanpham cho nhom
			command ="select count(*) as sodong from NHOMSANPHAMCHITIEU_sanpham where nsp_fk = '"+ this.id + "'";
			ResultSet sqlcheck = db.get(command);
			if(sqlcheck.next())
			{
				if(sqlcheck.getInt("sodong") == 1)
				{
					command = "update a set a.sanpham_fk = c.PK_SEQ,a.spma = c.MA,a.spten = c.TEN from NHOMSANPHAMCHITIEU a inner join NHOMSANPHAMCHITIEU_SANPHAM b on a.PK_SEQ = b.NSP_FK inner join SANPHAM c on c.PK_SEQ = b.SP_FK and a.PK_SEQ = ?";
					data.clear();
					data.add(this.id);
					
					if(this.db.updateQueryByPreparedStatement(command, data) != 1)
					{
						this.msg = "Không thể cập nhật "+command;
						db.getConnection().rollback();
						return false;
					}
				}
			}
			sqlcheck.close();
				
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
				query = "select a.pk_seq, a.ma, a.ten from sanpham a, NHOMSANPHAMCHITIEU_sanpham b where a.pk_seq = b.sp_fk and b.nsp_fk = '" + this.id + "'";
				
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
		
		//query = "select pk_seq, ma, ten from sanpham  where trangthai = '1' and pk_seq not in (select sp_fk from NHOMSANPHAMCHITIEU_sanpham where nsp_fk = '" + this.id +"')";
		
		if (this.id.length()>0){
			query = "select pk_seq, ma, ten from sanpham  where trangthai = '1' and pk_seq not in (select sp_fk from NHOMSANPHAMCHITIEU_sanpham where nsp_fk = '" + this.id + "')";
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

	
	public void setType(String type) {
		this.type = type;
	}

	
	public String getType() {
		return this.type;
	}

	
	public String getNspTT() {
		
		return this.NspTT;
	}

	
	public void setNspTT(String NspTT) {
		
		this.NspTT = NspTT;
	}
	
}


