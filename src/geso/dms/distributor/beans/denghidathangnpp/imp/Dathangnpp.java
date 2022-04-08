package geso.dms.distributor.beans.denghidathangnpp.imp;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import javax.mail.MessagingException;

import geso.dms.center.util.SendMail;
import geso.dms.distributor.beans.denghidathangnpp.IDathangnpp;
import geso.dms.distributor.db.sql.dbutils;

public class Dathangnpp implements IDathangnpp, Serializable{
	String userId;
	String nppId;
	String id;
	String ngaydn;
	String soct;
	String nguoitao;
	String nguoisua;
	String nppTen;
	String nccId;
	ResultSet ncc;
	ResultSet dvkdIds;
	String dvkdId;
	ResultSet kbhIds;
	String kbhId;
	String tongtienaVAT;
	String vat;
	String tongtienbVAT;
	String khoId;
	String kho;
	dbutils db;
	String msg;
	String[] soluong;
	String[] tonkho;
	String[] masp;
	String[] donvi;
	String[] tongtien;
	String[] dongia;
	String[] recipient; 
	ResultSet danhsach;
	String trangthai;
	String gsbhId;
	ResultSet gsbh;
	Hashtable tensp;
	public Dathangnpp(String id)
	{
		this.id = id;
		this.ngaydn = "";
		this.soct = "";
		this.gsbhId ="";
		this.nguoitao = "";
		this.nguoisua = "";
		this.nppTen = "";
		this.nccId = "";
		this.dvkdId = "";
		this.kbhId = "";
		this.msg ="";
		this.trangthai ="";
		this.tongtienaVAT = "0";		
		this.vat = "0";
		this.tongtienbVAT = "0";
		
		tonkho = new String[2000];
		soluong = new String[2000];
		for(int i =0;i<500;i++)
		{
			tonkho[i]="0";
			soluong[i]="0";
		}
		db = new dbutils();
		String sql ="select dndh.*, ktt.pk_seq as khoId, ktt.ten as kho from denghidathang dndh";
		sql = sql + " inner join nhaphanphoi npp on npp.pk_seq = dndh.npp_fk";
		sql = sql + " inner join khott ktt on ktt.pk_seq = npp.khosap where dndh.pk_seq ='"+ this.id +"'";

		System.out.println(sql);
		ResultSet rs = db.get(sql);
		
			try {
				rs.next();
				this.ngaydn = rs.getString("ngaydat");
				this.nccId  = rs.getString("ncc_fk");
				this.dvkdId  = rs.getString("dvkd_fk");
				this.kbhId = rs.getString("kbh_fk");
				this.trangthai =rs.getString("trangthai");
				this.khoId = rs.getString("khoId");
				this.kho = rs.getString("kho");
				
			} catch(Exception e) {
				
				e.printStackTrace();
			}
			sql = "select * from dondathang where denghidathang_fk ='"+ this.id +"'";
		    System.out.println("giam sat:"+ sql);
			ResultSet tb = db.get(sql);
			if(tb !=null)
			{  try {
				tb.next();
				 this.gsbhId = tb.getString("gsbh_fk");
			} catch (Exception e) {
			
				e.printStackTrace();
			}
			
			}
			else
				this.gsbhId ="";
			
		   if(kietrachot())
			   this.trangthai = "3";
		   
		   System.out.println( "trang thai ------:" + this.trangthai);
		   
		   this.tensp = this.Tensp();
	}
	public String getUserId() {
	
		return this.userId;
	}

	
	public void setUserId(String userId) {
	this.userId = userId;	
		
	}
	private Hashtable Tensp(){
		String query = "select pk_seq, ma, ten from sanpham where trangthai='1'";
		ResultSet rs = this.db.get(query);
		Hashtable h = new Hashtable();
		try{
			while(rs.next()){
				h.put(rs.getString("pk_seq"), rs.getString("ma")+" - " + rs.getString("ten"));
			}
		}catch(Exception e){}
		return h;
	}

	
	public String getId() {
		
		return this.id;
	}

	
	public void setId(String id) {
		
		this.id = id;
	}

	
	public String getNgaydn() {
		
		return this.ngaydn;
	}

	
	public void setNgaydn(String ngaydn) {
		
		this.ngaydn = ngaydn;
	}

	
	public String getNguoitao() {
		
		return null;
	}

	
	public void setNguoitao(String nguoitao) {
		
		
	}

	
	public String getNguoisua() {
		
		return null;
	}

	
	public void setNguoisua(String nguoisua) {
		
		
	}

	
	public String getNppTen() {
		
		return this.nppTen;
	}

	
	public void setNppTen(String nppTen) {
		
		this.nppTen = nppTen;
	}

	
	public String getNppId() {
		
		return this.nppId;
	}

	
	public void setNppId(String id) {
		
		this.nppId = id;
	}

	
	public String getNccId() {
		
		return this.nccId;
	}

	
	public void setNccId(String nccId) {
		
		this.nccId = nccId;
	}

	
	public ResultSet getDvkdIds() {
		
		return this.dvkdIds;
	}

	
	public void setDvkdIds(ResultSet dvkdIds) {
		
		this.dvkdIds = dvkdIds;
	}

	
	public String getKbhId() {
		
		return this.kbhId;
	}

	
	public void setKbhId(String kbhId) {
		
		this.kbhId = kbhId;
	}

	
	public ResultSet getKbhIds() {
		
		return this.kbhIds;
	}

	
	public void setKbhIds(ResultSet kbhIds) {
		
		this.kbhIds = kbhIds;
	}

	
	public String getTongtienbVAT() {
		
		return this.tongtienbVAT;
	}

	
	public void setTongtienbVAT(String tongtienbVAT) {
		
		this.tongtienbVAT = tongtienbVAT;
	}

	
	public String getVat() {
		
		return this.vat;
	}

	
	public void setVat(String vat) {
		
		this.vat = vat;
	}

	
	public String getTongtienaVAT() {
		
		return this.tongtienaVAT;
	}

	
	public void setTongtienaVAT(String tongtienaVAT) {
		
		this.tongtienaVAT = tongtienaVAT;
	}

	
	public String getDvkdId() {
		
		return this.dvkdId;
	}

	
	public void setDvkdId(String dvkdId) {
		
		this.dvkdId = dvkdId;
	}

	
	public ResultSet getNcc() {
		
		return this.ncc;
	}

	
	public void setNcc(ResultSet ncc) {
		
		this.ncc = ncc;
	}

	
	public String getMessage() {
		
		return this.msg;
	}

	
	public void setMessage(String msg) {
		
		this.msg = msg;
	}

	
	public void init0() {
		
		getNppInfo();
		String query = "select d.pk_seq as nccId, d.ten as nccTen from nhaphanphoi a, nhacungcap_dvkd b, nhapp_nhacc_donvikd c, nhacungcap d where c.ncc_dvkd_fk = b.pk_seq and a.pk_seq = c.npp_fk and b.ncc_fk = d.pk_seq and a.pk_seq = '"+ this.nppId + "'";
		this.ncc = this.db.get(query);
		System.out.println(query);
		if(nccId.length()>0){
			query = "select e.pk_seq as dvkdId, e.donvikinhdoanh as dvkd from nhaphanphoi a, nhacungcap b, nhacungcap_dvkd c, nhapp_nhacc_donvikd d, donvikinhdoanh e where a.pk_seq = '"+ this.nppId + "' and b.pk_seq = '" + this.nccId + "' and a.pk_seq = d.npp_fk and b.pk_seq = c.ncc_fk and c.pk_seq = d.ncc_dvkd_fk and c.dvkd_fk = e.pk_seq";
			System.out.println(query);
			
			this.dvkdIds = this.db.get(query);
		}else{
			this.dvkdIds = this.db.get("select pk_seq as dvkdId, donvikinhdoanh as dvkd from donvikinhdoanh");
		}
      
		query = "select distinct c.pk_seq as kbhId, c.diengiai as kbh from nhaphanphoi a, nhapp_kbh b, kenhbanhang c where a.pk_seq = '"+ this.nppId +"' and a.pk_seq = b.npp_fk and b.kbh_fk = c.pk_seq";
		System.out.println(query);
		
		this.kbhIds = this.db.get(query);
		
	    query ="select pk_seq ,ten from giamsatbanhang where pk_seq in (select gsbh_fk from NHAPP_GIAMSATBH where npp_fk ='"+ this.nppId +"' and ngaybatdau <='"+this.getDateTime()+"' and ngayketthuc >='"+getDateTime()+"' )";
		this.gsbh = this.db.get(query);
	}
	private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	public void init1() {
		String sql="";
			sql ="select distinct b.pk_seq,b.ten,a.denghidat,b.soluong,a.dongia,b.donvi,b.ma from denghidathang_sp a,(select e.pk_seq,e.ten,c.available as soluong,f.donvi,e.ma from tonkhoicp c,sanpham e,donvidoluong f where c.masp = e.ma and e.dvdl_fk = f.pk_seq and c.kho in (select khosap from nhaphanphoi where pk_seq ='"+ this.nppId +"' )) b where a.sanpham_fk = b.pk_seq and denghidathang_fk='"+ this.id +"'";
			System.out.println(sql);
		ResultSet rs = db.get(sql);
		this.danhsach = rs;
		
	}

	@Override
	public void DBclose() {
		
			try {
				if(this.danhsach != null)
					this.danhsach.close();
				if(this.dvkdIds != null)
					this.dvkdIds.close();
				if(this.gsbh != null)
					this.gsbh.close();
				if(this.kbhIds != null)
					this.kbhIds.close();
				if(this.ncc != null)
					this.ncc.close();
				if(this.db != null)
					db.shutDown();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
	}
private void getNppInfo(){
		/*String sql ="select a.pk_seq, a.ten, a.sitecode from nhaphanphoi a, nhanvien b where b.dangnhap = a.ma and b.pk_seq ='" + this.userId + "'";
		System.out.println(sql);
		ResultSet rs = this.db.get(sql);
		try{
			if (!(rs == null)){
				rs.next();
				this.nppId = rs.getString("pk_seq");
				this.nppTen = rs.getString("ten");
				
			}else{
				this.msg = "select a.pk_seq, a.ten, a.sitecode from nhaphanphoi a, nhanvien b where b.dangnhap = a.ma and b.pk_seq ='" + this.userId + "'";
				this.nppId = "";
				this.nppTen = "";
			}
			
		}catch(Exception e){}	
		*/
		//Phien ban moi
		geso.dms.distributor.util.Utility util=new geso.dms.distributor.util.Utility();
		this.nppId=util.getIdNhapp(this.userId);
		this.nppTen=util.getTenNhaPP();
		//this.sitecode=util.getSitecode();
	}


public void setSoluong(String[] soluong) {
	
	this.soluong = soluong;
}

public String[] getSoluong() {
	
	return this.soluong;
}

public void setTonkho(String[] tonkho) {
	
	this.tonkho = tonkho;
}

public String[] getTonkho() {
	
	return this.tonkho;
}

public void setdanhsach(ResultSet danhsach) {

    this.danhsach = danhsach;	
}

public ResultSet getdanhsach() {
	
	return this.danhsach;
}

public void init2() {
	String sql ="select distinct b.pk_seq,b.ten,a.denghidat,b.soluong,a.dongia,b.donvi from denghidathang_sp a,(select e.pk_seq,e.ten,c.available as soluong,f.donvi from tonkhoicp c,sanpham e,donvidoluong f where c.masp = e.ma and e.dvdl_fk = f.pk_seq and c.kho in (select khosap from nhaphanphoi where pk_seq ='"+ this.nppId +"' )) b where a.sanpham_fk = b.pk_seq and denghidathang_fk='"+ this.id +"'";
	System.out.println(sql);
	ResultSet tk = db.get(sql);
	
	
	String st =kiemtra();
	if(st.length()>0)
	{
		try {
			
			int i = 0;
			
			while(tk.next())
			{
				
                soluong[i] =soluongdat(tk.getString("pk_seq"),st);// 				System.out.println("so dong:" +i);
				i++;
			}
		} catch(Exception e) {
			
			System.out.println("loi:"+ e);
		}
		
		
	}
	}

String soluongdat(String masp1,String dondathang)
{
    	String sql ="select soluong from dondathang_sp where sanpham_fk = '"+ masp1 +"' and dondathang_fk ='"+ dondathang +"'";
		//System.out.println(sql);
		ResultSet td = db.get(sql);
		if(td != null)
		{  try {
			td.next();
			return td.getString("soluong");
		} catch(Exception e) {
			return "0";
			//System.out.println("loi 1:"+ e);
		}
			
		}
       return "0";
	}
String kiemtra()
{
	String sql ="select * from dondathang where denghidathang_fk ='"+ this.id +"'";
	System.out.print("don:"+sql);
    ResultSet rs = db.get(sql);
    if(rs!= null)
    {	try {
			rs.next();
			return rs.getString("pk_seq");
		} catch(Exception e) {
			return "";
			//System.out.println("loi don dat hang:"+ e);
		}
   }
    return "";
	}
/*
public boolean savedathang() {
	}
*/
public void setMasp(String[] masp) {
	
	this.masp = masp;
}
/*
boolean kiemtra_thieuhang()
{ 
}
*/
public String[] getMasp() {
	
	return this.masp;
}

public void setDonvi(String[] donvi) {
	
	this.donvi = donvi;
}

public String[] getDonvi() {
	
	return this.donvi;
}

public void setTongtien(String[] tongtien) {
	
	this.tongtien = tongtien;
}

public String[] getTongtien() {
	
	return this.tongtien;
}

public void setDongia(String[] dongia) {
	
	this.dongia = dongia;
}

public String[] getDongia() {
	
	return this.dongia;
}

public boolean kiemtra_thieuhang() {
	boolean tr = true;
	this.getEmail();
	Hashtable ht = this.getThieuhang();
	SendMail sm = new SendMail();
	boolean send = false;
	
	String mesg = "Chao Ban, \n\nSan pham ";

//	System.out.println("do dai:" + masp.length);
	
	try{
	for(int i =0;i< this.masp.length;i++)
	{  
	 String sl = "";	
	 if(soluong[i].length()>0)
	  { float thieu =Float.parseFloat(soluong[i]) -  Float.parseFloat(tonkho[i]);
		if(thieu > 0)
		{   
			String sql ="insert into thieuhang values('"+ this.id +"','"+ masp[i] +"','"+ thieu +"')";
			if(db.update(sql)){
				System.out.println(sql);
			
				if(ht.containsKey(this.masp[i])){
					sl = (String) ht.get(this.masp[i]);
					System.out.println(sql);
					int n = Integer.valueOf(sl.split(";")[0]) + Integer.valueOf(soluong[i]);
					if( n > Integer.valueOf(sl.split(";")[1])){
						if(tensp.containsKey(this.masp[i])){
							sql = "update theodoithieuhang set soluongthieu ='" + n + "' where sanpham_fk='" + this.masp[i] + "' and khott ='" + this.khoId + "'";
							mesg = mesg + tensp.get(this.masp[i]) + " da het hang\n\n";
							db.update(sql);
							System.out.println(sql);
							send = true;
						}	
					}
				}else{
				
					if(tensp.containsKey(this.masp[i])){
						mesg = mesg + tensp.get(this.masp[i]) + " da het hang\n\n";
						send = true;
					}
				
					String tmp;
					if(sl.length()>0){
						tmp = "" + Integer.valueOf(sl.split(";")[0]) + Math.round(thieu);
					}else{
						tmp = "" + Math.round(thieu);
					}
					
					sql = "insert into theodoithieuhang (sanpham_fk, soluongthieu, khott, ngaydt, soluongdt, emailnpp, trangthai) values('"+ masp[i] +"', '" + tmp + "', '" + this.khoId + "', '', '0','chinhntk@geso.us','1')";
					db.update(sql);
					System.out.println(sql);
				
				}
			
			}
			
			tr=false;
		}
	 }
	}
		if(send){
			mesg = mesg + "Vui long vao he thong BEST de xac nhan khi nao co hang lai. \n\nCam on va tran trong\n\nHe thong BEST";
			sm.postMail(recipient, "Canh bao thieu hang", mesg);
		}
	}catch(MessagingException me){
		System.out.println(me.toString());
	};
  return tr;
}

public boolean savedathang() {
	String ma="";  
	//ngaydn se la ngay dat hang
	if(kiemtra().length()>0)
	{   ma = kiemtra();
		String sql ="update dondathang set nguoisua ='"+ this.userId+"',SOTIENBVAT='"+ this.tongtienbVAT +"',SOTIENAVAT='"+ this.tongtienaVAT +"',gsbh_fk ='"+ this.gsbhId +"'  where denghidathang_fk ='"+ this.id +"'";
		System.out.println(sql);
		 if(!db.update(sql))
		 {
				this.msg = "Loi khi cap nhat bang dondathang, " + sql;
				return false; 
			
		 }
		  sql ="delete from dondathang_sp where dondathang_fk ='"+ ma +"'"; //dondathang_fk  in (select pk_seq from dondathang where denghidathang_fk ='"+ ma +"')";
		  System.out.println(sql);
		  if(!db.update(sql))
		  {     geso.dms.center.util.Utility.rollback_throw_exception(this.db);
				this.msg = "Loi khi cap nhat bang dondathang, " + sql;
				return false; 
			
		  }
	}
	else
	{
	   String sql ="insert into dondathang values('"+ this.ngaydn +"','"+ this.tongtienbVAT +"','"+ this.userId +"','"+ this.userId +"','0','"+ this.nppId +"','"+ this.nccId +"','10','"+ this.tongtienaVAT +"','"+ this.dvkdId +"','"+ this.id +"','"+ this.kbhId +"','0','0','"+ this.gsbhId +"',0,0,'1')";
	   System.out.println("chuoi nhap" + sql);
		    if(!db.update(sql))
			{
				geso.dms.center.util.Utility.rollback_throw_exception(this.db);
				this.msg = "Loi khi cap nhat bang dondathang, " + sql;
				return false; 
			}
			String query = "select IDENT_CURRENT('dondathang') as pk_seq";
			System.out.println(query);
			ResultSet rs = db.get(query);
			try {
				rs.next();
				ma = rs.getString("pk_seq");
	       //   ////System.out.println("ma:"+ma);
			} catch (Exception e) {
	       // ////System.out.print("loi:" + e);
			}
	 }
	
	  
	
		int i = 0;
		try {
			while(i < masp.length)
			{
                if(this.soluong[i].length() >0)
                {
					if(Float.parseFloat(this.soluong[i])>0)
					{
					 	float sotien = Float.parseFloat(this.soluong[i])* Float.parseFloat(this.dongia[i]);
						float vat = sotien * 10/100;
						float sotienvat = sotien + vat;
						System.out.println("toi o day:" +sotien +"  vat  " + vat + " sotien vat " + sotienvat);
					    String  sql =" insert into dondathang_sp values('"+ ma +"','"+ masp[i]+"','"+ this.soluong[i]+"','"+ this.donvi[i]+"','"+ this.dongia[i]+"','"+ sotien +"','"+ vat +"','"+ sotienvat +"',null)";
					    System.out.println("lu so luong: "+ sql);
					    if(!db.update(sql))
					  	{
					  		//geso.dms.center.util.Utility.rollback_throw_exception(this.db);
					  		this.msg = "Loi khi cap nhat bang dondathang_sp " + sql;
					  		//return false; 
					  	}
				  }
		    	}
                i++;
			 }
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	return true;

}

public void setTrangthai(String trangthai) {

	this.trangthai = trangthai;
}

public String getTrangthai() {
	return this.trangthai;
}

public void setGsbhId(String gsbhId) {
	
	this.gsbhId = gsbhId; 
}

public String getGsbhId() {
	
	return this.gsbhId;
}

public void setGsbh(ResultSet gsbh) {
	
	this.gsbh = gsbh;
}

public ResultSet getGsbh() {
	
	return this.gsbh;
}
boolean kietrachot()
{
	String sql ="select count(*) as num from dondathang where trangthai = 0 and denghidathang_fk ='"+ this.id +"'";
	System.out.print("don:"+sql);
    ResultSet rs = db.get(sql);
    if(rs!= null)
    {	try {
			rs.next();
			if(rs.getString("num").trim().equals("1"))
				return true;
		} catch(Exception e) {
		
			//System.out.println("loi don dat hang:"+ e);
		}
   }
    return false;
	}

private void getEmail(){
	 ResultSet rs = this.db.get("select count(email) as num from email");
	 try{
		 rs.next();
		 int n = Integer.valueOf(rs.getString("num")).intValue();
		 if(n > 0){
			 this.recipient = new String[n+1];
			 this.recipient[0] = "" + n;
			 
			 rs = this.db.get("select email from email");
			 int i = 1;
			 
			 while(rs.next()){
				 this.recipient[i] = rs.getString("email");
				 i++;
			 }				 
		 }
	 }catch(Exception e){}		 
	 		 
}

private Hashtable getThieuhang(){
	//anh sua lai phan nay giup em voi, vi CSDL khong co 'soluongdt', em thay tam 1 so de cho chuong trinh Test
	//ResultSet th = db.get("select sanpham_fk, soluongthieu, soluongdt from theodoithieuhang");
	ResultSet th = db.get("select sanpham_fk, soluongthieu,'1' as  soluongdt from theodoithieuhang");
	Hashtable ht = new Hashtable<String, String>();
	ht.put("0", "0");
	try{
		while(th.next()){
			ht.put(th.getString("sanpham_fk"), th.getString("soluongthieu")+ ";" + th.getString("soluongdt"));
		}
		
	}catch(Exception e){}
	
	return ht;
}

}
