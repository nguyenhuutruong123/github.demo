package geso.dms.center.beans.donmuahang.imp;
import geso.dms.center.beans.donmuahang.IDonmuahang;
import geso.dms.center.db.sql.*;
import geso.dms.center.util.Utility;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;
public class Donmuahang implements IDonmuahang
{
	private static final long serialVersionUID = -9217977546733610214L;
	String userId;
	String ten;
	String id;
	String nppTen;
	String nppId;
	String ngaydh;
	String soct;
	String nguoitao;
	String nguoisua;	
	String nccId;
	ResultSet ncc;
	ResultSet dvkdIds;
	String dvkdId;
	ResultSet kbhIds;
	String kbhId;
	String tongtienaVAT;
	String vat;
	String tongtienbVAT;
	String[] spId;
	String[] masp;
	String[] tensp;
	String[] sl;
	String[] sle;
	String[] dongia;
	String[] qc;
	String[] donvi;
	String[] tienbVAT;
	String size;
	String msg;
	String maspstr;
	dbutils db;
	public Donmuahang()
	{
		this.id = "";
		this.ngaydh = getDateTime();
		this.soct = "";
		this.nguoitao = "";
		this.nguoisua = "";
		this.nppTen = "";
		this.nccId = "";
		this.dvkdId = "";
		this.tongtienaVAT = "0";		
		this.vat = "0";
		this.tongtienbVAT = "0";
		this.size = "";
		this.msg="";
		this.maspstr = "";
		this.db = new dbutils();
	}
	public String getUserId()
	{
		return this.userId;
	}
	
	public void setUserId(String userId)
	{
		this.userId = userId;
		this.nguoitao = userId;
		this.nguoisua = userId;
	}

	public String getNppId()
	{
		return this.nppId;
	}
	
	public void setNppId(String id)
	{
		this.nppId = id;
	}
	
	public String getId()
	{
		return this.id;
	}
	
	public void setId(String id)
	{
		this.id = id;
	}

	public String getNppTen()
	{
		return this.nppTen;
	}
	
	public void setNppTen(String ten)
	{
		this.nppTen = ten;
	}

	public String getSize()
	{
		return this.size;
	}
	
	public void setSize(String size)
	{
		this.size = size;
	}
	
	
	public String getNgaydh()
	{
		return this.ngaydh;
	}
	
	public void setNgaydh(String ngaydh)
	{
		this.ngaydh = ngaydh;
	}
	
	public String getSoct()
	{
		return this.soct;
	}
	
	public void setSoct(String soct)
	{
		this.soct = soct;
	}
			
	public String[] getQuycach()
	{
		return this.qc;
	}
	
	public void setQuycach(String[] qc)
	{
		this.qc = qc;
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
	
	public String getTen()
	{
		return this.ten;
	}
	
	public void setTen(String ten)
	{
		this.ten = ten;
	}
	
	public String getNccId()
	{
		return this.nccId;
	}
	
	public void setNccId(String nccId)
	{
		this.nccId = nccId;
	}

	public String getDvkdId()
	{
		return this.dvkdId;
	}
	
	public void setDvkdId(String dvkdId)
	{
		this.dvkdId = dvkdId;
	}

	public ResultSet getDvkdIds()
	{
		return this.dvkdIds;
	}
	
	public void setDvkdIds(ResultSet dvkdIds)
	{
		this.dvkdIds = dvkdIds;
	}

	public String getKbhId()
	{
		return this.kbhId;
	}
	
	public void setKbhId(String kbhId)
	{
		this.kbhId = kbhId;
	}


	public ResultSet getKbhIds()
	{
		return this.kbhIds;
	}
	
	public void setKbhIds(ResultSet kbhIds)
	{
		this.kbhIds = kbhIds;
	}
	
	public String getTongtienbVAT()
	{
		return this.tongtienbVAT;
	}

	public void setTongtienbVAT(String tongtienbVAT)
	{
		this.tongtienbVAT = tongtienbVAT;
	}
	
	public String getVat()
	{
		return this.vat;
	}
	
	public void setVat(String vat)
	{
		this.vat = vat;
	}
	
	public String getTongtienaVAT()
	{
		return this.tongtienaVAT;
	}
	
	public void setTongtienaVAT(String tongtienaVAT)
	{
		this.tongtienaVAT = tongtienaVAT;
	}
	
	public ResultSet getNcc()
	{
		return this.ncc;
	}
	
	public void setNcc(ResultSet ncc)
	{
		this.ncc = ncc;
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
	
	public void setTensp(String[] tensp)
	{
		this.tensp = tensp;
	}
	
	public String[] getSl()
	{
		return this.sl;
	}
	
	public void setSl(String[] sl)
	{
		this.sl = sl;
	}

	public String[] getDongia()
	{
		return this.dongia;
	}
	
	public void setDongia(String[] dongia)
	{
		this.dongia = dongia;
	}
	
	public String[] getTienbVAT()
	{
		return this.tienbVAT;
	}
	
	public void setTienbVAT(String[] tienbVAT)
	{
		this.tienbVAT = tienbVAT;
	}

	public String[] getDonvi()
	{
		return this.donvi;
	}
	
	public void setDonvi(String[] donvi)
	{
		this.donvi = donvi;
	}

	public String getMessage() 
	{
		return this.msg;
	}
	
	public void setMessage(String msg) 
	{
		this.msg = msg;
	}

	public String getMaspstr() 
	{
		return this.maspstr;
	}
	
	public void setMaspstr(String maspstr) 
	{
		this.maspstr = maspstr;
	}
	
	private Hashtable Quycach(){
		String query = "select distinct a.ma, b.soluong1 from sanpham a, quycach b where a.pk_seq = b.sanpham_fk order by a.ma";
		ResultSet rs = this.db.get(query);
		Hashtable h = new Hashtable();
		try{
			while(rs.next()){
				h.put(rs.getString("ma"), rs.getString("soluong1"));
			}
		}catch(java.sql.SQLException e){}
		return h;
	}
	
	public void init(){
		getNppInfo();
		Hashtable h = Quycach();
		
		String query; 	
		ResultSet rs;
		try{
			rs= this.db.get("select count(ma) as num from sanpham where trangthai='1'");
			rs.next();
			this.size = rs.getString("num");		
			int i = new Integer(this.size).intValue();
			this.spId = new String[i];
			this.masp = new String[i];
			this.tensp = new String[i];
			this.sl = new String[i];
			this.qc = new String[i];
			this.dongia = new String[i];
			this.tienbVAT = new String[i];
			this.donvi = new String[i];
			
			query = "select ngaydat, ncc_fk, dvkd_fk, kbh_fk, sotienbvat, vat, sotienavat from dondathang where pk_seq='"+ this.id + "'";
			rs = db.get(query);
			rs.next();
			this.ngaydh = rs.getString("ngaydat");
			this.dvkdId = rs.getString("dvkd_fk");
			this.nccId = rs.getString("ncc_fk");
			this.kbhId = rs.getString("kbh_fk");
			this.tongtienbVAT = rs.getString("sotienbvat");
			this.vat = rs.getString("vat");
			this.tongtienaVAT = rs.getString("sotienavat");
			
			query ="select a.sanpham_fk, b.ma, b.ten as tensp, a.soluong as sl, a.donvi, a.dongia, a.sotienbvat, a.vat, a.sotienavat from dondathang_sp a, sanpham b where a.sanpham_fk = b.pk_seq and a.dondathang_fk='" + this.id + "' order by b.ma";
			rs = db.get(query);				
			int m =0;
//			this.msg = query;
			while(rs.next()){
				this.spId[m] = rs.getString("sanpham_fk");			 
				this.masp[m] = rs.getString("ma");
				if (this.maspstr.length()==0){
					this.maspstr = "'" + this.masp[m] + "'";
				}else{
					this.maspstr = this.maspstr + ",'" + masp[m] + "'";
				}				
				this.tensp[m]= rs.getString("tensp");
				this.sl[m] = rs.getString("sl");
				this.sl[m] = "" + (Long.valueOf(this.sl[m]).longValue()/ Long.valueOf((String)h.get(this.masp[m])).longValue());				
				this.donvi[m] = rs.getString("donvi");
				this.dongia[m] = rs.getString("dongia");
				this.dongia[m] = "" + (Long.valueOf(this.dongia[m]).longValue()* Long.valueOf((String)h.get(this.masp[m])).longValue());
				
				this.tienbVAT[m] = rs.getString("sotienbvat");
				m++;
			}
			this.size = "" + m;

			query = "select pk_seq as nccId, ten as nccTen from nhacungcap";
			this.ncc = this.db.get(query);

			if(nccId.length()>0){
				query = "select e.pk_seq as dvkdId, e.donvikinhdoanh as dvkd from nhaphanphoi a, nhacungcap b, nhacungcap_dvkd c, nhapp_nhacc_donvikd d, donvikinhdoanh e where a.pk_seq = '"+ this.nppId + "' and b.pk_seq = '" + this.nccId + "' and a.pk_seq = d.npp_fk and b.pk_seq = c.ncc_fk and c.pk_seq = d.ncc_dvkd_fk and c.dvkd_fk = e.pk_seq";
				this.dvkdIds = db.get(query);
			}else{
				this.dvkdIds = db.get("select pk_seq as dvkdId, donvikinhdoanh as dvkd from donvikinhdoanh");
			}
//			this.msg = query;
			query = "select c.pk_seq as kbhId, c.diengiai as kbh from nhaphanphoi a, nhapp_kbh b, kenhbanhang c where a.pk_seq = '"+ this.nppId +"' and a.pk_seq = b.npp_fk and b.kbh_fk = c.pk_seq";
			this.kbhIds = db.get(query);
		}catch(java.sql.SQLException e){}
	}
	
	public void initDisplay(){
		getNppInfo();
		Hashtable h = Quycach();
		
		String query; 	
		ResultSet rs;
		try{
			rs= this.db.get("select count(a.sanpham_fk) as num from dondathang_sp a, sanpham b where a.sanpham_fk = b.pk_seq and a.dondathang_fk='" + this.id + "' and a.soluong > 0");
			rs.next();
			this.size = rs.getString("num");		
			int i = new Integer(this.size).intValue();
			this.spId = new String[i];
			this.masp = new String[i];
			this.tensp = new String[i];
			this.sl = new String[i];
			this.sle=new String[i];
			this.qc = new String[i];
			this.dongia = new String[i];
			this.tienbVAT = new String[i];
			this.donvi = new String[i];
			
			query = "select ngaydat, ncc_fk, dvkd_fk, kbh_fk, sotienbvat, vat, sotienavat from dondathang where pk_seq='"+ this.id + "'";
			rs = db.get(query);
			rs.next();
			this.ngaydh = rs.getString("ngaydat");
			this.dvkdId = rs.getString("dvkd_fk");
			this.nccId = rs.getString("ncc_fk");
			this.kbhId = rs.getString("kbh_fk");
			this.tongtienbVAT = rs.getString("sotienbvat");
			this.vat = rs.getString("vat");
			this.tongtienaVAT = rs.getString("sotienavat");
			
			query ="select a.sanpham_fk, b.ma, b.ten as tensp, a.soluong as sl, a.donvi, a.dongia, a.sotienbvat, a.vat, a.sotienavat from dondathang_sp a, sanpham b where a.sanpham_fk = b.pk_seq and a.dondathang_fk='" + this.id + "' and a.soluong > 0 order by b.ma";
			rs = db.get(query);				
			int m =0;
//			this.msg = query;
			while(rs.next()){
				this.spId[m] = rs.getString("sanpham_fk");			 
				this.masp[m] = rs.getString("ma");
				if (this.maspstr.length()==0){
					this.maspstr = "'" + this.masp[m] + "'";
				}else{
					this.maspstr = this.maspstr + ",'" + masp[m] + "'";
				}				
				this.tensp[m]= rs.getString("tensp");
				this.sle[m] = rs.getString("sl");
				this.sl[m] = rs.getString("sl");
				
				this.sl[m] = "" + (Long.valueOf(this.sl[m]).longValue()  / Long.valueOf((String)h.get(this.masp[m])).longValue());				
				this.donvi[m] = rs.getString("donvi");
				this.dongia[m] = rs.getString("dongia");
				this.dongia[m] = "" + (Long.valueOf(this.dongia[m]).longValue()* Long.valueOf((String)h.get(this.masp[m])).longValue());
				
				this.tienbVAT[m] = rs.getString("sotienbvat");
				m++;
			}
			this.size = "" + m;

			query = "select pk_seq as nccId, ten as nccTen from nhacungcap";
			this.ncc = this.db.get(query);

			if(nccId.length()>0){
				query = "select e.pk_seq as dvkdId, e.donvikinhdoanh as dvkd from nhaphanphoi a, nhacungcap b, nhacungcap_dvkd c, nhapp_nhacc_donvikd d, donvikinhdoanh e where a.pk_seq = '"+ this.nppId + "' and b.pk_seq = '" + this.nccId + "' and a.pk_seq = d.npp_fk and b.pk_seq = c.ncc_fk and c.pk_seq = d.ncc_dvkd_fk and c.dvkd_fk = e.pk_seq";
				this.dvkdIds = db.get(query);
			}else{
				this.dvkdIds = db.get("select pk_seq as dvkdId, donvikinhdoanh as dvkd from donvikinhdoanh");
			}
//			this.msg = query;
			query = "select c.pk_seq as kbhId, c.diengiai as kbh from nhaphanphoi a, nhapp_kbh b, kenhbanhang c where a.pk_seq = '"+ this.nppId +"' and a.pk_seq = b.npp_fk and b.kbh_fk = c.pk_seq";
			this.kbhIds = db.get(query);
		}catch(java.sql.SQLException e){}
	}

	public boolean ChotDdhnpp (HttpServletRequest request) {
		if(!(UpdateDdhnpp(request))){
			return false;
		}
		String command = "update dondathang set trangthai='2' where pk_seq = '" + this.id + "'";
		if (!(this.db.update(command))){
			this.msg = command;
			return false;
			}
		
		return true;
	}
	public boolean UpdateDdhnpp(HttpServletRequest request) {
		getNppInfo();
		
		try{
			Utility util = new Utility();
			Hashtable h = Quycach();
			this.tongtienbVAT = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tongtienbvat"));
			this.tongtienbVAT = this.tongtienbVAT.replace(",", "");
			
			this.vat = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vat"));
			this.vat = this.vat.replace(",", "");
			
			this.tongtienaVAT = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tongtienavat"));
			this.tongtienaVAT = this.tongtienaVAT.replace(",", "");
		
			String ddhId = this.id; 
					
			ResultSet rs = db.get("select denghidathang_fk as dndhId from dondathang where pk_seq = '" + this.id + "'");
			rs.next();
			String dndhId = rs.getString("dndhId");
			rs.close();
			
			this.db.update("update dondathang_sp set soluong='0' where dondathang_fk='" + this.id + "'");
			
			this.msg = "update dondathang_sp set soluong='0' where dondathang_fk='" + this.id + "'";
			
			long tmpbvat = 0;
			long tmpvat = 0;
			long tmpavat = 0;
			long total = 0;
			this.spId = request.getParameterValues("spId");
			this.masp = request.getParameterValues("masp");
			this.sl = new String[this.spId.length];
			this.dongia = new String[this.spId.length];
			this.tienbVAT = new String[this.spId.length];
			
			for (int i = 0; i < this.masp.length ; i++){
				this.sl[i] = request.getParameter("sl" + this.masp[i]);
				if (this.sl[i].length()==0)
					this.sl[i] = "0";
				
				if (!(util.isNumeric(this.sl[i]))){
					sl[i] = "0";
				}
				
				this.sl[i] = "" + (Long.valueOf(this.sl[i]).longValue()* Long.valueOf((String)h.get(this.masp[i])).longValue());			
				
				this.dongia[i]	= request.getParameter("dg" + this.masp[i]);
				this.dongia[i]  = this.dongia[i].replace(",", "");
				this.dongia[i] = "" + Math.round(Long.valueOf(this.dongia[i]).longValue()/ Long.valueOf((String)h.get(this.masp[i])).longValue());
				
				this.tienbVAT[i] = request.getParameter("t" + this.masp[i]);
				this.tienbVAT[i] = this.tienbVAT[i].replace(",", "");
				
				String vat = "" + Math.round(Long.valueOf(this.tienbVAT[i]).longValue() * 0.1);
				tmpbvat = tmpbvat + (Long.valueOf(sl[i]).longValue())*(Long.valueOf(dongia[i]).longValue());						
				tmpvat = tmpvat + Math.round(Long.valueOf(this.tienbVAT[i]).longValue() * 0.1);
					
				String tienavat = "" + Math.round(Long.valueOf(this.tienbVAT[i]).longValue()*1.1);
				tmpavat = tmpavat + Math.round(Long.valueOf(this.tienbVAT[i]).longValue()*1.1);
					
				String query = "update dondathang_sp set soluong ='" +  sl[i] + "', sotienbvat = '" + this.tienbVAT[i]+ "', vat ='" + vat +"', sotienavat = '" + tienavat + "' where sanpham_fk = '" + this.spId[i] + "' and dondathang_fk = '"+ this.id + "'";
	
				if(!db.update(query)){
					this.msg = query;
				
					return false;
				}
				
				query = "update denghidathang_sp set dadathang ='" +this.tienbVAT[i] + "' where sanpham_fk='" + this.spId[i] + "' and denghidathang_fk='" + dndhId + "'";
				if(db.update(query)){
					total = total + Long.valueOf(this.tienbVAT[i]).longValue();
				}else{
					return false;
				}
				
			}	
		
			String date = convertDate();
			String query = "update dondathang set ngaydat= '" + date + "', sotienbvat = '" + this.tongtienbVAT+ "',  nguoisua = '" + this.nguoisua + "', vat = '" + this.vat + "', sotienavat = '" + this.tongtienaVAT + "' where pk_seq = '" + this.id + "'";		
			if(!db.update(query)){
				this.msg = query;
				return false;
			}

			
			total = Math.round(total*1.1);
			query = "update denghidathang set dadathang='" + total + "', trangthai='1' where pk_seq ='" + dndhId + "'";
			if(!db.update(query)){
				this.msg = query;
				
				return false;
			}
			
		}catch(java.sql.SQLException e){}
		return true;
	}

	private String convertDate2(String date){
		String d = "";
		String year = date.substring(0, 4);
		String month = date.substring(5, 7);
		String day = date.substring(8, 10);
		d = "" + day + "/" + month + "/" + year;
		return d;
	}

	private String convertDate() {
        String newdate ="";
	    int day = Integer.valueOf(this.ngaydh.substring(0, 2)).intValue();		
	    int month = Integer.valueOf(this.ngaydh.substring(3, 5)).intValue();
	    int year = Integer.valueOf(this.ngaydh.substring(6, 10)).intValue();
        
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
	
	private void getNppInfo(){
		
		ResultSet rs = this.db.get("select b.pk_seq, b.ten from dondathang a, nhaphanphoi b where a.npp_fk=b.pk_seq and a.pk_seq='"+this.id+"'");
		try{
			if (rs != null){
				rs.next();
				this.nppId = rs.getString("pk_seq");
				this.nppTen = rs.getString("ten");
			}else{
				this.nppId = "";
				this.nppTen = "";				
			}
		}catch(java.sql.SQLException e){}				
	}
	
	public boolean isInteger( String input )  
	{  
	   try  
	   {  
	      Integer.parseInt( input );  
	      return true;  
	   }  
	   catch(Exception e)  
	   {  
	      return false;  
	   }  
	 }  

	public String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	public void DBclose()
	{
		try 
			{
				if(this.ncc != null)
				this.ncc.close();
				
				if(this.dvkdIds != null)
				this.dvkdIds.close();   
				
				if(this.kbhIds != null)
				this.kbhIds.close(); 
				
			} catch (SQLException e) {}
		
		if(!(this.db == null)){
			this.db.shutDown();
		}
	}
	@Override
	public String[] getSle() {
		
		return this.sle;
	}
	@Override
	public void setSle(String[] _sle) {
		
		this.sle=_sle;
	}
	
}