package geso.dms.center.beans.dieuchinhtonkho.imp;

import geso.dms.center.db.sql.*;
import geso.dms.center.util.Utility;

import java.sql.Array;
import java.sql.ResultSet;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;

import geso.dms.center.beans.dieuchinhtonkho.IDieuchinhtonkho;

public class Dieuchinhtonkho implements IDieuchinhtonkho
{
	private static final long serialVersionUID = -9217977546733610214L;
	String id;
	String nppId;
	String nppTen;
	String userId;
	String userTen;
	String ngaydc;
	String trangthai;
	String nccId;
	ResultSet ncc;
	String dvkdId;
	ResultSet dvkd;
	String kbhId;
	ResultSet kbh;
	String khoId;
	ResultSet kho;
	String file;
	String gtdc;

	String msg;
	String[] spId;
	String[] masp;
	String[] tensp;
	String[] tkht;
	String[] dv;
	String[] dc;
	String[] tkm;
	String[] giamua;
	String[] ttien;
	String size;
	String maspstr;
	dbutils db;
	String Lydodc;
	
	public Dieuchinhtonkho(String[] param)
	{
		this.db = new dbutils();
		this.id = param[0];
		this.nppId = param[1];
		this.nppTen = param[2];
		this.userId = param[3];
		this.userTen = param[4];
		this.ngaydc = param[5];
		this.nccId = param[6];
		this.dvkdId = param[7];
		this.kbhId = param[8];
		this.gtdc = param[9];
	}
	
	public Dieuchinhtonkho()
	{
		this.db = new dbutils();
		this.id = "";
		this.nppId = "";
		this.nppTen = "";
		this.userId = "";
		this.userTen = "";
		this.ngaydc = "";
		this.nccId = "";
		this.dvkdId = "";
		this.kbhId = "";
		this.khoId = "";
		this.gtdc = "";
		this.msg = "";
		this.Lydodc="";
	}
	
	public String getId()
	{
		return this.id;
	}
	
	public void setId(String id)
	{
		this.id = id;
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

	public String getUserId()
	{
		return this.userId;
	}
	
	public void setUserId(String userId)
	{
		this.userId = userId;
	}
	
	public String getUserTen()
	{
		return this.userTen;
	}
	
	public void setUserTen(String userTen)
	{
		this.userTen = userTen;
	}
	
	public String getNgaydc()
	{
		return this.ngaydc;
	}
	
	public void setNgaydc(String ngaydc)
	{
		this.ngaydc = ngaydc;
	}
	
	public String getTrangthai()
	{
		return this.trangthai;
	}
	
	public void setTrangthai(String thaitrang)
	{
		this.trangthai = trangthai;
	}

	public String getNccId()
	{
		return this.nccId;
	}
	
	public void setNccId(String nccId)
	{
		this.nccId = nccId;
	}

	public ResultSet getNcc()
	{
		return this.ncc;
	}
	
	public void setNcc(ResultSet ncc)
	{
		this.ncc = ncc;
	}

	public String getDvkdId()
	{
		return this.dvkdId;
	}
	
	public void setDvkdId(String dvkdId)
	{
		this.dvkdId = dvkdId;
	}

	public ResultSet getDvkd()
	{
		return this.dvkd;
	}
	
	public void setDvkd(ResultSet dvkd)
	{
		this.dvkd = dvkd;
	}

	
	public String getKbhId()
	{
		return this.kbhId;
	}
	
	public void setKbhId(String kbhId)
	{
		this.kbhId = kbhId;
	}

	public ResultSet getKbh()
	{
		return this.kbh;
	}
	
	public void setKbh(ResultSet kbh)
	{
		this.kbh = kbh;
	}

	public String getKhoId()
	{
		return this.khoId;
	}
	
	public void setKhoId(String khoId)
	{
		this.khoId = khoId;
	}

	public ResultSet getKho()
	{
		return this.kho;
	}
	
	public void setKho(ResultSet kho)
	{
		this.kho = kho;
	}

	public String getGtdc()
	{
		return this.gtdc;
	}
	
	public void setGtdc(String gtdc)
	{
		this.gtdc = gtdc;
	}
	
	public String getSize()
	{
		return this.size;
	}
	
	public void setSize(String size)
	{
		this.size = size;
	}

	public String getMaspstr()
	{
		return this.maspstr;
	}
	
	public void setMaspstr(String maspstr)
	{
		this.maspstr = maspstr;
	}

	public String[] getSpId()
	{
		return this.spId;
	}
	
	public void setSpId(String[] spId)
	{
		this.spId = spId;
	}

	public String[] getMasp()
	{
		return this.masp;
	}
	
	public void setMasp(String[] masp)
	{
		this.masp = masp;
	}
	
	public String[] getTensp()
	{
		return this.tensp;
	}
	
	public void setTenSp(String[] tensp)
	{
		this.tensp = tensp;
	}
	
	public String[] getTkht()
	{
		return this.tkht;
	}
	
	public void setTkht(String[] tkht)
	{
		this.tkht = tkht;
	}
	
	public String[] getTkm()
	{
		return this.tkm;
	}
	
	public void setTkm(String[] tkm)
	{
		this.tkm = tkm;
	}

	public String[] getDonvi()
	{
		return this.dv;
	}
	
	public void setDonvi(String[] dv)
	{
		this.dv = dv;
	}

	public String[] getDc()
	{
		return this.dc;
	}
	
	public void setDc(String[] dc)
	{
		this.dc = dc;
	}
	
	public String[] getGiamua()
	{
		return this.giamua;
	}
	
	public void setGiamua(String[] giamua)
	{
		this.giamua = giamua;
	}

	public String[] getTtien()
	{
		return this.ttien;
	}
	
	public void setTtien(String[] ttien)
	{
		this.ttien = ttien;
	}
	
	public String getMessage()
	{
		return this.msg;
	}
	
	public void setMessage(String msg)
	{
		this.msg = msg;
	}

	public void init0(){

		String query = "select pk_seq as dvkdId, donvikinhdoanh as dvkd from donvikinhdoanh ";
		this.dvkd = this.db.get(query);

		query = "select pk_seq as kbhId, diengiai as kbh from kenhbanhang";
		this.kbh = this.db.get(query);
	
		query = "select distinct pk_seq as khoId, ten,diengiai from kho" ;
		this.kho = this.db.get(query);
		
	}
	


	public void initDisplay(){
		init0();
		try{
			Hashtable ht = new Hashtable();
			String query;
			ResultSet rs;
			query = "select ngaydc, npp_fk, dvkd_fk,npp.ten as tennpp, d.kbh_fk, d.tongtien,lydodc, d.kho_fk, d.trangthai,isnull(filename,'') as filename from dieuchinhtonkho d inner join nhaphanphoi npp on npp.pk_seq=npp_fk where d.pk_seq='" + this.id + "'";
			
			rs = this.db.get(query);
			
			rs.next();
			this.ngaydc = rs.getString("ngaydc");
			this.dvkdId = rs.getString("dvkd_fk");
			this.nppId	= rs.getString("npp_fk");
			this.kbhId	= rs.getString("kbh_fk");
			this.khoId	= rs.getString("kho_fk");
			this.gtdc 	= rs.getString("tongtien");
			this.trangthai = rs.getString("trangthai");
			this.Lydodc=rs.getString("lydodc");
			this.file = rs.getString("filename");
			this.nppTen =rs.getString("tennpp");
			rs.close();
				
			query = "select count(distinct sanpham_fk) as num from dieuchinhtonkho_sp where dieuchinhtonkho_fk='" + this.id + "'";
			rs = this.db.get(query);

			if (rs != null){
				rs.next();
				int size = Integer.valueOf(rs.getString("num")).intValue();
				this.size = "" + size;
				this.spId = new String[size];
				this.masp = new String[size];
				this.tensp = new String[size];
				this.tkht = new String[size];
				this.dv = new String[size];
				this.dc  = new String[size];
				this.giamua = new String[size];
				this.ttien = new String[size];
				this.tkm = new String[size];
				rs.close();
			}
			
			maspstr = "";
			
			if(this.trangthai.equals("1"))
			{
				
				query =
					"\n SELECT  sp.pk_Seq as spId,sp.MA ,sp.TEN,dvdl.DONVI,SUM(DCSP.DIEUCHINH) AS DC, dcsp.GIAMUA, "+
					"\n isnull(SUM(DCSP.TONHIENTAI),0) AS TONHIENTAI, isnull(SUM(DCSP.TONMOI),0)  AS TONMOI, "+
					"\n	sum(dcsp.THANHTIEN) as thanhtien	" +
					"\n from dieuchinhtonkho_sp dcsp "+
					"\n inner join DIEUCHINHTONKHO dc on dc.PK_SEQ=dcsp.DIEUCHINHTONKHO_FK "+
					"\n inner join SANPHAM sp on sp.PK_SEQ=dcsp.SANPHAM_FK "+
					"\n inner join DONVIDOLUONG dvdl on dvdl.PK_SEQ=sp.DVDL_FK " +
					"\n where dc.pk_seq='"+this.id+"'        " +
					"\n GROUP BY sp.pk_Seq ,sp.MA ,sp.TEN,dvdl.DONVI,dcsp.GIAMUA,DC.NPP_FK,dcsp.SANPHAM_FK,DC.KBH_FK,DC.KHO_FK ";
			}
			else
			{
				query =
					"\n	SELECT  sp.pk_Seq as spId,sp.MA ,sp.TEN,dvdl.DONVI,SUM(DCSP.DIEUCHINH) AS DC, "+
					"\n dcsp.GIAMUA , "+
					"\n isnull((SELECT SUM(SOLUONG) FROM NHAPP_KHO WHERE NPP_FK = DC.NPP_FK AND SANPHAM_FK = DCSP.SANPHAM_FK AND KBH_FK = DC.KBH_FK AND KHO_FK = DC.KHO_FK),0) AS TONHIENTAI , "+
					"\n isnull((SELECT SUM(SOLUONG) FROM NHAPP_KHO WHERE NPP_FK = DC.NPP_FK AND SANPHAM_FK = DCSP.SANPHAM_FK AND KBH_FK = DC.KBH_FK AND KHO_FK = DC.KHO_FK),0) + SUM(dcsp.DIEUCHINH) AS TONMOI	"+			
					"\n	,sum(dcsp.THANHTIEN) as thanhtien	from dieuchinhtonkho_sp dcsp "+
					"\n	inner join DIEUCHINHTONKHO dc on dc.PK_SEQ=dcsp.DIEUCHINHTONKHO_FK "+
					"\n	inner join SANPHAM sp on sp.PK_SEQ=dcsp.SANPHAM_FK "+
					"\n	inner join DONVIDOLUONG dvdl on dvdl.PK_SEQ=sp.DVDL_FK " +
					"\n where dc.pk_seq='"+this.id+"' " +
					"\n GROUP BY sp.pk_Seq ,sp.MA ,sp.TEN,dvdl.DONVI,dcsp.GIAMUA,DC.NPP_FK,dcsp.SANPHAM_FK,DC.KBH_FK,DC.KHO_FK ";
				
				
				
			}
			System.out.println("init sanpham : "+ query);
				rs = this.db.get(query);
			int i = 0;
			while(rs.next()){
				this.spId[i] = rs.getString("spId");
				this.masp[i] = rs.getString("ma");

				if (this.maspstr.length()==0){
					this.maspstr = "'" + this.masp[i] + "'";
				}else{
					this.maspstr = this.maspstr + ",'" + this.masp[i] + "'";
				}
				
				this.tensp[i]= rs.getString("ten");
				this.tkht[i] = rs.getString("tonhientai");
				this.dv[i] = rs.getString("donvi");
				this.giamua[i] = Long.toString(Math.round(rs.getDouble("giamua"))) ;
				this.dc[i] = rs.getString("dc");
				this.ttien[i] = Long.toString(Math.round(rs.getDouble("thanhtien"))) ;
				this.tkm[i]= rs.getString("tonmoi");
				i++;
			}
				
		}catch(Exception e){}
	}

	private String convertDate2(String date){
		String newdate = "";
		int year = Integer.valueOf(date.substring(0, 4)).intValue();
		int month = Integer.valueOf(date.substring(5, 7)).intValue();		
		int day = Integer.valueOf(date.substring(8, 10)).intValue();
	    if (month == 1)
	    	newdate = "" + day + "-Jan-" + year;
	    if (month == 2)
	    	newdate = "" + day + "-Feb-" + year;
	    if (month == 3)
	    	newdate = "" + day + "-Mar-" + year;
	    if (month == 4)
	    	newdate = "" + day + "-Apri-" + year;
	    if (month == 5)
	    	newdate = "" + day + "-May-" + year;
	    if (month == 6)
	    	newdate = "" + day + "-Jun-" + year;
	    if (month == 7)
	    	newdate = "" + day + "-Jul-" + year;
	    if (month == 8)
	    	newdate = "" + day + "-Aug-" + year;
	    if (month == 9)
	    	newdate = "" + day + "-Sep-" + year;
	    if (month == 10)
	    	newdate = "" + day + "-Oct-" + year;
	    if (month == 11)
	    	newdate = "" + day + "-Nov-" + year;
	    if (month == 12)
	    	newdate = "" + day + "-Dec-" + year;

        return newdate;	

	}

	public void DBclose(){
		try{
		if(this.ncc!=null){
			ncc.close();
		}
		if(this.dvkd!=null){
			dvkd.close();
		}
		if(this.kbh!=null){
			kbh.close();
		}
		if(this.kho!=null){
			kho.close();
		}
		if(!(this.db == null)){
			this.db.shutDown();
		}
		}catch(Exception er){
			
		}
	}
	
	@Override
	public void setlydodc(String lydodc) {
		// TODO Auto-generated method stub
		this.Lydodc=lydodc;
	}

	@Override
	public String getLydodc() {
		// TODO Auto-generated method stub
		return this.Lydodc;
	}
	
public String[] getFile() {
		
		return this.file.split(",");
	}

	
	public void setFile(String file) {
		this.file=file;
		
	}
	
}