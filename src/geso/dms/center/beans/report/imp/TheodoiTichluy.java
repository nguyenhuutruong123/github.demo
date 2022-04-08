package geso.dms.center.beans.report.imp;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import geso.dms.center.beans.report.ITheodoiTichluy;
import geso.dms.center.db.sql.dbutils;
import geso.dms.distributor.util.Utility;

public class TheodoiTichluy implements ITheodoiTichluy
{
	String userId;
	String nppId;
	String kenhId;

	String tungay;
	String denngay;
	String userTen;
	String msg;
	ResultSet kenh;
	String vungId;
	String khuvucId;
	
	ResultSet khuvuc;
	ResultSet vung;
	
	ResultSet nppRs;
	dbutils db;

	String programsId;
	ResultSet rsPrograms;
	private ResultSet ctkmRs;
	private String ddkdId;
	private ResultSet ddkdRs;
	private String phanloai;
	private String loainv;
	private String nppTen;
	
	public TheodoiTichluy() {
		this.userId = "";
		this.nppId = "";
		this.kenhId = "";
		this.tungay = "";
		this.denngay = "";
		this.userTen = "";
		this.msg = "";
		this.vungId = "";
		this.khuvucId = "";
		this.programsId = "";
		this.ddkdId = "";
		this.db = new dbutils();

	}

	@Override
	public void setuserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String getuserId() {
		return this.userId;
	}

	@Override
	public void setuserTen(String userTen) {
		this.userTen = userTen;
	}

	@Override
	public String getuserTen() {
		return this.userTen;
	}

	@Override
	public void setnppId(String nppId) {
		this.nppId = nppId;
	}

	@Override
	public String getnppId() {
		// TODO Auto-generated method stub
		return this.nppId;
	}

	@Override
	public void setkenhId(String kenhId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getkenhId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void settungay(String tungay) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String gettungay() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setdenngay(String denngay) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getdenngay() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String getMsg() {
		return this.msg;
	}

	@Override
	public ResultSet getkenhRs() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setpromotion(String promotion) {
		this.programsId = promotion;
	}

	@Override
	public String getpromotion() {
		return this.programsId;
	}

	@Override
	public ResultSet getPromotionRs() {
		// TODO Auto-generated method stub
		return this.ctkmRs;
	}

	@Override
	public void setvungId(String vungId) {
		this.vungId = vungId;
	}

	@Override
	public String getvungId() {
		// TODO Auto-generated method stub
		return this.vungId;
	}

	@Override
	public ResultSet getvungRs() {
		// TODO Auto-generated method stub
		return this.vung;
	}

	@Override
	public void setkhuvucId(String khuvucId) {
		this.khuvucId = khuvucId;
	}

	@Override
	public String getkhuvucId() {
		// TODO Auto-generated method stub
		return this.khuvucId;
	}

	@Override
	public ResultSet getkhuvucRs() {
		// TODO Auto-generated method stub
		return this.khuvuc;
	}

	@Override
	public ResultSet getNppRs() {
		// TODO Auto-generated method stub
		return this.nppRs;
	}

	@Override
	public String getDateTime() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void init() {
		Utility Ult = new Utility();
		String sql="";
		 
		 try{
			 
			 sql="select phanloai,loai from nhanvien where pk_seq="+this.userId;
			 ResultSet rs=this.db.get(sql);
			 if(rs!=null){
				 if(rs.next()){
					 
					 this.phanloai = rs.getString("phanloai");
					 System.out.println("Phan loai : "+this.phanloai);					 				
					 this.loainv =  rs.getString("loai");
					 if(rs.getString("phanloai").equals("1")){
						 this.nppId = Ult.getIdNhapp(this.userId);
							this.nppTen = Ult.getTenNhaPP();
					 }
					 rs.close();
				 }
			 }
		 }catch(Exception er){
			 
		 }
		 String quyenvung = "";
		 String quyenkhuvuc = "";
		 if(this.phanloai.equals("2") )
		 {
			 if(this.loainv.equals("2"))
			 {
				 quyenvung = " and pk_seq in (select distinct kv.VUNG_FK from ASM_KHUVUC a inner join KHUVUC kv on a.KHUVUC_FK = kv.PK_SEQ where a.ASM_FK = (select top(1) asm_fk from NHANVIEN where PK_SEQ ='"+userId+"'  )  )";
				 
				 quyenkhuvuc = " and pk_seq in (select distinct kv.pk_seq from ASM_KHUVUC a inner join KHUVUC kv on a.KHUVUC_FK = kv.PK_SEQ where a.ASM_FK = (select top(1) asm_fk from NHANVIEN where PK_SEQ ='"+userId+"'  )  )";
			 }
			 
			 if(this.loainv.equals("3"))
			 {
				 quyenvung = " and pk_seq in (select distinct kv.VUNG_FK from GSBH_KHUVUC a inner join KHUVUC kv on a.KHUVUC_FK = kv.PK_SEQ where a.GSBH_FK = (select top(1) gsbh_fk from NHANVIEN where PK_SEQ ='"+userId+"'  )  )";
				 
				 quyenkhuvuc = " and pk_seq in (select distinct kv.pk_seq from GSBH_KHUVUC a inner join KHUVUC kv on a.KHUVUC_FK = kv.PK_SEQ where a.GSBH_FK = (select top(1) gsbh_fk from NHANVIEN where PK_SEQ ='"+userId+"'  )  )";
			 }
		 }
			 
		this.kenh = db.get(" select pk_seq,ten,diengiai from kenhbanhang ");

		this.vung = db.get("select pk_seq,ten,diengiai from vung where 1 = 1"+quyenvung);
		System.out.println("Vung: "+"select pk_seq,ten,diengiai from vung where 1 = 1"+quyenvung);

		
		if (this.vungId.length() > 0) {
			this.khuvuc = db.get("select pk_seq,ten from khuvuc where vung_fk in ("+ this.vungId + ") "+quyenvung);
		} 
		else
			this.khuvuc = db.get("select pk_seq,ten from khuvuc where 1 = 1 "+quyenkhuvuc);

		 sql = "select pk_seq, ten from nhaphanphoi where trangthai ='1' ";
		if (this.khuvucId.length() > 0) {
			sql = sql + " and khuvuc_fk in (" + this.khuvucId + " )";
		}
		if (this.vungId.length() > 0) {
			sql = sql + " and khuvuc_fk in (select pk_seq from khuvuc where vung_fk in ("+ this.vungId + ") )";
		}
		
		
		if (this.kenhId.length() > 0)
			sql = sql+ " and pk_seq in (select npp_fk from NHAPP_KBH where kbh_fk ='"+ this.kenhId + "')";
		geso.dms.center.util.Utility util =new geso.dms.center.util.Utility();
		
		if(this.phanloai.equals("1")){
			sql = sql+" and pk_seq = '"+ this.nppId +"'";			
		}
		else{
			sql=sql+" and pk_seq in "+util.quyen_npp(this.userId);
		}
		sql+=" order by ltrim(ten) ";
		System.out.println("Get NPP (stockintransit ):"+sql);
		this.nppRs = db.getScrol(sql);
		
		sql = "select PK_SEQ, TEN from DAIDIENKINHDOANH WHERE TRANGTHAI = 1";
		if(this.nppId.length() > 0)
			sql += " and NPP_FK = " + this.nppId;
		this.ddkdRs = this.db.get(sql);
		
		sql = "select PK_SEQ, SCHEME from TIEUCHITHUONGTL";
		this.ctkmRs = this.db.get(sql);
	}

	@Override
	public void DBclose() {
		// TODO Auto-generated method stub
		if(this.db != null)
			this.db.shutDown();
	}

	@Override
	public void setDdkdId(String ddkd) {
		this.ddkdId = ddkd;
	}

	@Override
	public String getDdkdId() {
		// TODO Auto-generated method stub
		return this.ddkdId;
	}

	@Override
	public ResultSet getDdkdRs() {
		// TODO Auto-generated method stub
		return this.ddkdRs;
	}

	
	
}
