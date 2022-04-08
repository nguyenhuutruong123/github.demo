package geso.dms.distributor.beans.thongtinsanpham.imp;

import geso.dms.distributor.beans.thongtinsanpham.IThongtinsanpham;
import geso.dms.distributor.db.sql.*;
import geso.dms.distributor.util.Utility;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.Serializable;
import java.util.Hashtable;

public class Thongtinsanpham implements IThongtinsanpham, Serializable
{
	private static final long serialVersionUID = -9217977546733690415L;
	String userId;
	private String nppTen;
	private String nppId;
	
	String id;
	String masp;
	String maddt;
	String ten;

	String dvdlId;
	String dvdlTen;
	ResultSet dvdl;
	
	String dvkdId;
	String dvkdTen;
	ResultSet dvkd;
	
	String nhId;
	String nhTen;
	ResultSet nh;
	
	String clId;
	String clTen;
	ResultSet cl;
	
	String trangthai;
	String giablc;
		
	String msg;

	String ngaytao;
	String nguoitao;
	String ngaysua;
	String nguoisua;
	
	String[] nspIds;
	ResultSet nsp;
	
	ResultSet nspSelected;
	
	String[] sl1;
	String[] dvdl1;
	String[] sl2;
	String[] dvdl2;
	
	String type;
	String[] spIds;
	String[] spStt;
	ResultSet spList;
	ResultSet spSelectedList; //update
	
	dbutils db;
	
	public Thongtinsanpham(String[] param)
	{	
		this.db = new dbutils();
		this.id = param[0];
		this.masp = param[1];
		this.ten = param[2];
		this.dvdlTen = param[3];
		this.dvkdTen = param[4];
		this.dvkdId = param[5];
		this.nhTen = param[6];
		this.nhId = param[7];
		this.clId = param[8];
		this.clTen = param[9];	
		this.giablc = param[10];
		this.trangthai=param[11];
		this.dvdlId = param[12];
    	this.ngaysua = param[13];
    	this.nguoisua = param[14];
    	this.sl1 = new String[5];
    	this.sl2 = new String[5];
    	this.dvdl1 = new String[5];
    	this.dvdl2 = new String[5];
    	this.type = param[15]; //default
    	this.msg = "";
    	
    	this.getNppInfo();
	}
	
	public Thongtinsanpham()
	{	
		this.db = new dbutils();
		this.id = "";
		this.nppId = "";
		this.nppTen = "";
		this.masp = "";
		this.maddt = "";
		this.ten = "";
		this.dvdlTen = "";
		this.dvkdTen = "";
		this.dvkdId = "";
		this.nhTen = "";
		this.nhId = "";
		this.clId = "";
		this.clTen = "";	
		this.giablc = "";
		this.trangthai= "1";
		this.dvdlId = "";
    	this.ngaysua = "";
    	this.nguoisua = "";
    	this.msg = "";
    	this.type = "";
    	this.sl1 = new String[5];
    	this.sl2 = new String[5];
    	this.dvdl1 = new String[5];
    	this.dvdl2 = new String[5]; 	
    	
    	this.getNppInfo();
	}
	
	public String getUserId()
	{
		return this.userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
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
	
	public String getId()
	{
		return this.id;
	}

	public void setId(String id)
	{
		this.id = id;
	}
	
	public String getMasp()
	{
		return this.masp;
	}

	public void setMasp(String masp)
	{
		this.masp = masp;
	}

	public String getMaddt()
	{
		return this.maddt;
	}

	public void setMaddt(String maddt)
	{
		this.maddt = maddt;
	}
	
	public String getTen()
	{
		return this.ten;
	}

	public void setTen(String ten)
	{
		this.ten = ten;
	}

	public String getTrangthai()
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai)
	{
		this.trangthai = trangthai;
	}
	
	public String getDvdlId()
	{
		return this.dvdlId;
	}

	public void setDvdlId(String dvdlId)
	{
		this.dvdlId = dvdlId;
	}

	public String getDvdlTen()
	{
		return this.dvdlTen;
	}

	public void setDvdlTen(String dvdlTen)
	{
		this.dvdlTen = dvdlTen;
	}

	public ResultSet getDvdl()
	{
		return this.dvdl;
	}

	public void setDvdl(ResultSet dvdl)
	{
		this.dvdl = dvdl;
	}

	public String getDvkdId()
	{
		return this.dvkdId;
	}

	public void setDvkdId(String dvkdId)
	{
		this.dvkdId = dvkdId;
	}

	public String getDvkdTen()
	{
		return this.dvkdTen;
	}

	public void setDvkdTen(String dvkdTen)
	{
		this.dvkdTen = dvkdTen;
	}
	
	public String getNhId()
	{
		return this.nhId;
	}

	public void setNhId(String nhId)
	{
		this.nhId = nhId;
	}

	public String getNhTen()
	{
		return this.nhTen;
	}

	public void setNhTen(String nhTen)
	{
		this.nhTen = nhTen;
	}
	
	public String getClId()
	{
		return this.clId;
	}

	public void setClId(String clId)
	{
		this.clId = clId;
	}

	public String getClTen()
	{
		return this.clTen;
	}

	public void setClTen(String clTen)
	{
		this.clTen = clTen;
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
	
	public void setDvkd(ResultSet dvkd)
	{
		this.dvkd = dvkd;
	}

	public ResultSet getDvkd()
	{
		return this.dvkd;
	}

	public void setNh(ResultSet nh)
	{
		this.nh = nh;
	}

	public ResultSet getNh()
	{
		return this.nh;
	}

	public void setCl(ResultSet cl)
	{
		this.cl = cl;
	}
	
	public ResultSet getCl()
	{
		return this.cl;
	}

	public void setGiablc(String giablc)
	{
		this.giablc = giablc;
	}
	
	public String getGiablc()
	{
		return this.giablc;
	}

	public void setNsp(ResultSet nsp)
	{
		this.nsp = nsp;
	}

	public ResultSet getNsp()
	{
		return this.nsp;
	}

	public Hashtable<Integer, String> getNspIds()
	{
		Hashtable<Integer, String> selected = new Hashtable<Integer, String>();
		if(this.nspIds != null){
			int size = (this.nspIds).length;
			int m = 0;
			while(m < size){
				selected.put(new Integer(m), this.nspIds[m]) ;
				m++;
			}
		}
		else{
			selected.put(new Integer(0), "null");
		}
		return selected;
	}

	public void setNspIds(String[] nspIds)
	{
		this.nspIds = nspIds;
	}

	public String[] getSl1()
	{
		return this.sl1 ;
	}

	public void setSl1(String[] sl1)
	{
		this.sl1 = sl1;
	}
	
	public String[] getDvdl1()
	{
		return this.dvdl1 ;
	}

	public void setDvdl1(String[] dvdl1)
	{
		this.dvdl1 = dvdl1;
	}

	public String[] getSl2()
	{
		return this.sl2 ;
	}

	public void setSl2(String[] sl2)
	{
		this.sl2 = sl2;
	}

	public String[] getDvdl2()
	{
		return this.dvdl2 ;
	}

	public void setDvdl2(String[] dvdl2)
	{
		this.dvdl2 = dvdl2;
	}
	
	public ResultSet createDvdlRS(){  			
		
		ResultSet dvdlRS = this.db.get("select pk_seq as dvdlId, donvi as dvdlTen from donvidoluong where trangthai='1' order by donvi");

		return dvdlRS;
	}
	
	private ResultSet createDvkdRS(){  	
		//ResultSet dvkdRS =  this.db.get("select pk_seq as dvkdId, donvikinhdoanh as dvkdTen from donvikinhdoanh where trangthai='1' ");
		ResultSet dvkdRS =  this.db.get("select a.pk_seq as dvkdId, a.donvikinhdoanh as dvkdTen from donvikinhdoanh a,nhacungcap_dvkd b where a.pk_seq = b.DVKD_fk and b.checked ='1' and a.trangthai ='1' order by a.donvikinhdoanh");
		return dvkdRS;
	}
	
	private ResultSet createNhRS(){  	
		ResultSet nhRS;
		if (dvkdId.length()>0){
			nhRS =  this.db.get("select pk_seq as nhId, ten as nhTen from nhanhang where trangthai='1' and dvkd_fk='" + this.dvkdId + "'");
		}else{
			nhRS =  this.db.get("select pk_seq as nhId, ten as nhTen from nhanhang where trangthai='1'");
		}
		return nhRS;
		
	}

	private ResultSet createClRS() {  	
		
		String query = "select distinct a.pk_seq as clId, a.ten as clTen from chungloai a, nhanhang_chungloai b where trangthai='1' and a.pk_seq = b.cl_fk ";
		
		if(this.nhId.length()>0){
			query = query + "  and b.nh_fk = '" + this.nhId + "'";
		
		}
		 
		return this.db.get(query);
			
	}
	
	private void createSpIds() 
	{
		ResultSet rs = db.get("select sanpham_fk from Bundle_Sanpham where bundle_fk = '" + this.id + "'");
		if(rs != null)
		{
			try 
			{
				String str = "";
				while(rs.next())
				{
					str = str + rs.getString("sanpham_fk") + ",";
				}
				if(str.length() > 0)
				{
					str = str.substring(0, str.length() - 1);
					this.spIds = str.split(",");
				}
			} 
			catch(Exception e) {e.printStackTrace();}
			finally{try {
				if(rs != null)
					rs.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}}
		}
	}

	private void createNspIds() 
	{
		ResultSet rs = db.get("select nsp_fk from nhomsanpham_sanpham where sp_fk = '" + this.id + "'");
		if(rs != null)
		{
			try 
			{
				String str = "";
				while(rs.next())
				{
					str = str + rs.getString("nsp_fk") + ",";
				}
				if(str.length() > 0)
				{
					str = str.substring(0, str.length() - 1);
					this.nspIds = str.split(",");
				}
			} 
			catch(Exception e) {e.printStackTrace();}
			finally{try {
				if(rs != null)
					rs.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}}
		}
	}

	public void CreateRS()
	{
		this.dvdl = createDvdlRS();
		
		this.dvkd = createDvkdRS();
		this.nh = createNhRS();
		this.cl= createClRS();

		
		this.createNspRS();
		this.createSpList();		
	}
	
	boolean masanpham()
	{   
		String sql = "select * from sanpham where ma ='" + this.masp + "'";
		ResultSet rs = db.get(sql);
		if(rs != null)
		try 
		{
			if(rs.getRow() > 0)
				return true;
		} 
		catch(Exception e){e.printStackTrace();}
		
		finally{try {
			if(rs != null)
				rs.close();
		} catch (Exception e2) {
			// TODO: handle exception
		}}
		return false;
	}

	public void DBClose(){
		
		try{
			
			if (this.nh != null)
				this.nh.close();
			if (this.cl != null)
				this.cl.close();
			if (this.dvdl != null)
				this.dvdl.close();
			if (this.dvkd != null)
				this.dvkd.close();
			if (this.nsp != null)
				this.nsp.close();
			if(this.spSelectedList!=null){
				this.spSelectedList.close();
			}
			if(this.spList!=null){
				this.spList.close();
				
			}
			if(this.nspSelected!=null){
				this.nspSelected.close();
			}
			
			if(this.db != null)
				this.db.shutDown();	
			
		}catch(Exception e){}
	}

	public void setType(String type) 
	{	
		this.type = type;
	}
	
	public String getType() 
	{	
		return this.type;
	}
	
	public ResultSet getSanphamRs()
	{
		return this.spList;
	}
		
	public void setSanphamRs(ResultSet spRs) 
	{
		this.spList = spRs;	
	}
		
	public Hashtable<Integer, String> getSpIds() 
	{	
		Hashtable<Integer, String> selected = new Hashtable<Integer, String>();
		if(this.spIds != null){
			int size = (this.spIds).length;
			int m = 0;
			while(m < size){
				selected.put(new Integer(m), this.spIds[m]) ;
				m++;
			}
		}
		else{
			selected.put(new Integer(0), "null");
		}
		return selected;
	}
	
	public void setSpIds(String[] spIds)
	{
		this.spIds = spIds;
	}


	public void setNspSelected(ResultSet nsp)
	{
		this.nspSelected = nsp;
	}

	public ResultSet getNspSelected() 
	{
		return this.nspSelected;
	}

	public ResultSet getSanphamSelectedRs() 
	{
		return this.spSelectedList;
	}

	public void setSanphamSelectedRs(ResultSet spRs) 
	{
		this.spSelectedList = spRs;
	}

	public void init() 
	{
		
		ResultSet rs = null;
		try
		{
			getNppInfo();
			String query ="select a.pk_seq as id, a.ma as masp, a.ma_ddt, a.ten as tensp, b.donvikinhdoanh as dvkd, b.pk_seq as dvkdId, c.pk_seq as clId, c.ten as chungloai, e.pk_seq as nhId, d.donvi, e.ten as nhanhang, d.pk_seq as dvdlId, a.trangthai, isnull(f.giabanlechuan, 0) as giablc, a.type ";
			query = query + " from sanpham a left join donvikinhdoanh b on a.dvkd_fk = b.pk_seq left join chungloai c on a.chungloai_fk = c.pk_seq left join donvidoluong d on a.dvdl_fk = d.pk_seq left join nhanhang e on a.nhanhang_fk = e.pk_seq ";
			query = query  + " left join banggiablc_sanpham f on a.pk_seq = f.sanpham_fk left join banggiabanlechuan g on f.bgblc_fk = g.pk_seq ";
				     
			query = query + " where a.pk_seq = '" + this.id + "'";
		System.out.println("Thong TIn San Pham Nha Phan Phoi:  "+query);
			rs =  this.db.get(query);
	    
	        rs.next();        	
	    	this.id = rs.getString("id");
	    	this.masp = rs.getString("masp");
	    	this.maddt = rs.getString("ma_ddt");
	    	this.ten = rs.getString("tensp");
	    	if(rs.getString("dvkdId")== null){
	    		this.dvkdId = "";
	    	}else{
	    		this.dvkdId = rs.getString("dvkdId");
	    	}
	    	if(rs.getString("nhId")== null){
	    		this.nhId = "";
	    	}else{
	    		this.nhId = rs.getString("nhId");
	    	}

	    	if(rs.getString("clId")== null){
	    		this.clId = "";
	    	}else{
	    		this.clId = rs.getString("clId");
	    	}

	    	this.giablc = rs.getString("giablc");
	    	this.trangthai = "";
	    	if( rs.getString("trangthai") != null)
	    		this.trangthai = rs.getString("trangthai");
	    	
	    	this.type = "";
	    	if(rs.getString("type") != null)
	    		this.type = rs.getString("type");

	    	if(rs.getString("dvdlId")== null){
	    		this.dvdlId = "";
	    	}else{
	    		this.dvdlId = rs.getString("dvdlId");
	    	}

	    	rs.close();
	    	
	    	query = "select soluong1 as sl1, dvdl1_fk as dvdl1, soluong2 as sl2, dvdl2_fk as dvdl2 from quycach where sanpham_fk = '" + id + "'"; 
//	   	this.msg =query;
	    	rs= this.db.get(query);
	    	int i = 0;
	    	while (rs.next()){
	    		this.sl1[i] = rs.getString("sl1");
	    		this.dvdl1[i] = rs.getString("dvdl1");
	    		this.sl2[i] = rs.getString("sl2");
	    		this.dvdl2[i] = rs.getString("dvdl2");
	    		i++;
	    	}
	    	rs.close();
	    	
	    	this.dvdl = createDvdlRS();
			
			this.dvkd = createDvkdRS();
			this.nh = createNhRS();
			this.cl= createClRS();

			this.createNspRS();
			this.createSpList();	
			
	    	createNspIds();
			createSpIds();
	    	
	   	}catch(Exception e){}
	   	finally{try {
			if(rs != null)
				rs.close();
		} catch (Exception e2) {
			// TODO: handle exception
		}}
	}

	private void createSpList() 
	{
		String query = "select pk_seq, ma as spMa, ten as spTen, ma_ddt from sanpham where pk_seq in(select sanpham_fk from Bundle_Sanpham where bundle_fk='" + this.id + "')";
		this.spSelectedList = this.db.get(query);					
	}

	private void createNspRS() 
	{
		this.nspSelected = this.db.get("select a.nsp_fk as nspId, b.TEN as nspTen, b.diengiai as diengiai from nhomsanpham_sanpham a inner join nhomsanpham b on a.nsp_fk = b.pk_seq where a.sp_fk = '" + this.id + "'");
	}
	
	private void getNppInfo()
	{		
		
		/*ResultSet rs = this.db.get("select a.pk_seq, a.ten, a.sitecode from nhaphanphoi a, nhanvien b where b.dangnhap = a.ma and b.pk_seq ='" + this.userId + "'");
		System.out.println("select a.pk_seq, a.ten, a.sitecode from nhaphanphoi a, nhanvien b where b.dangnhap = a.ma and b.pk_seq ='" + this.userId + "'");
		try{
			if (!(rs == null)){
				rs.next();
				this.nppId = rs.getString("pk_seq");
				this.nppTen = rs.getString("ten");				
			}else
			{
				this.nppId = "";
				this.nppTen = "";			
			}
			
		}catch(Exception e){}	
		/*
		Utility util=new Utility();
		this.nppId=util.getIdNhapp(this.userId);
		this.nppTen=util.getTenNhaPP();
		*/
		
		//Phien ban moi
		geso.dms.distributor.util.Utility util=new geso.dms.distributor.util.Utility();
		this.nppId=util.getIdNhapp(this.userId);
		System.out.println("User Bi Rong :"+this.userId);
		this.nppTen=util.getTenNhaPP();
		//this.dangnhap = util.getDangNhap();
		//this.sitecode=util.getSitecode();
	
	}


	
}


