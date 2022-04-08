package geso.dms.center.beans.stockintransit.imp;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


import geso.dms.center.beans.stockintransit.IStockintransit;
import geso.dms.center.db.sql.dbutils;
import geso.dms.distributor.util.Utility;

public class Stockintransit implements IStockintransit
{
	String view = "";
	
	String ttId = "";
	String dhchot;
	String dhchot1;
	String userId;
	String thuongId;
	String nppId;
	String nppTen;
	String kenhId;
	String dvkdId;
	String nhanhangId= "";
	String chungloaiId= "";
	String loainppId;
	String tungay = "";
	String denngay = "";
	String tumuc;
	String denmuc;
	String userTen;
	String khoId;
	String book;
	String unghang;
	String covat;
	String check;
	ResultSet kho;
	String msg;
	ResultSet kenh;
	ResultSet dvkd;
	ResultSet nhanhang;
	ResultSet chungloai;
	ResultSet loainpp;
	String vungId;
	String khuvucId;
	String nhomkhId;
	ResultSet khuvuc;
	ResultSet vung;
	ResultSet thuong;
	ResultSet nhomkh;
	ResultSet tinhthanhRs;
	ResultSet npp;
	String vat;
	String gsbhId = "";
	ResultSet gsbh;

	String nvgnId;
	ResultSet nvgn;
	
	String trangthaispId;
	
	
	String asmId = "";
	ResultSet asm;
	String bmId = "";
	ResultSet bm;
	String sanphamId;
	ResultSet sanpham;
	String dvdlId;
	String[] FieldShow;
	String[] FieldHidden;
	String ngayton;
	String promotion;
	String discount;
	ResultSet dvdl;
	String lessday;
	String moreday;
	dbutils db;
	String year;
	String month;
	String QuyCachId;
	String ddkd;
	ResultSet rsddkd;
	String loaiMenu = "";
	String tinhthanhid = "";
	String khId = "",spid = "";
	ResultSet DlnRs ; 
	String url;
	String key;
	String cttbId;
	ResultSet cttbRs;

	String tuyenbhId;
	
	ResultSet rstuyenbh;



	//
	String dlnid = "";
	String unit;
	String groupCus;
	//
	String programsId;
	ResultSet rsPrograms;

	String donviTinh;

	String fromMonth;
	String toMonth;
	String type;
	String chon;
	String ToYear="";
	String FromYear="";
	String Nhospid="";
	ResultSet RsNhomSp;
	ResultSet RsQuyCach;

	ResultSet nspRs;
	String nspIds;

	String phanloai;

	String DMQid;
	String TenDMQ;

	String task;

	String nhomskusId;
	ResultSet nhomskus;

	public Stockintransit() {
		this.nhomskusId = "";

		this.dhchot = "1";
		this.dhchot1 = "1";
		this.userId = "";
		this.nppId = "";
		this.nppTen = "";
		this.kenhId = "";
		this.dvkdId = "";
		this.nhanhangId = "";
		this.chungloaiId = "";
		this.loainppId = "";
		this.tungay = "";
		this.denngay = "";
		this.tumuc = "";
		this.denmuc = "";
		this.userTen = "";
		this.khoId = "";
		this.book = "";
		this.trangthaispId = "";
		this.msg = "";
		this.vungId = "";
		this.khuvucId = "";
		this.vat = "";
		this.gsbhId = "";
		this.nvgnId = "";
		this.asmId = "";
		this.bmId = "";
		this.thuongId = "";
		this.sanphamId = "";
		this.dvdlId = "";
		this.ngayton = "1";
		this.promotion = "0";
		this.discount = "0";
		this.lessday = "0";
		this.moreday = "0";
		this.ddkd="";
		this.unit = "";
		this.groupCus = "";
		this.programsId = "";
		this.donviTinh = "";
		this.month = "";
		this.year = "";
		this.fromMonth = "";
		this.toMonth = "";
		this.unghang = "1";
		this.covat = "0";
		this.chon="0";
		this.type="";
		this.DMQid="";
		this.check = "";
		this.TenDMQ="";
		this.nspIds = "";
		this.year = ""; // getdate().substring(0, 4);
		this.FromYear = this.year;
		this.ToYear = this.year;
		this.toMonth = ""; // getdate().substring(5, 7);
		this.phanloai = "";
		this.task = "";
		this.cttbId = "";
		this.url="";
		this.key="_CHECK";
		//System.out.println("11.Month la: " + this.toMonth);
		this.db = new dbutils();
		String sql = "select PK_SEQ,Ten from UNGDUNG where pk_seq in (100168,100025) union select 1 as PK_seq,N'MCP' as Ten ";
		this.DlnRs = db.get(sql);
		this.tuyenbhId="";
	}

	public ResultSet getRstuyenbh() {
		return rstuyenbh;
	}

	public void setRstuyenbh(ResultSet rstuyenbh) {
		this.rstuyenbh = rstuyenbh;
	}
	
	

	public String getTrangthaispId() {
		return trangthaispId;
	}

	public void setTrangthaispId(String trangthaispId) {
		this.trangthaispId = trangthaispId;
	}



	public String getNhomskusId() {
		return nhomskusId;
	}

	public void setNhomskusId(String nhomskusId) {
		this.nhomskusId = nhomskusId;
	}

	public ResultSet getNhomskus() {
		return nhomskus;
	}

	public void setNhomskus(ResultSet nhomskus) {
		this.nhomskus = nhomskus;
	}

	public void setDMQid(String dmqid)
	{
		this.DMQid=dmqid;
	}
	public String getDMQid()
	{
		return this.DMQid;
	}
	public void setTenDMQ(String tendmq)
	{
		this.TenDMQ=tendmq;
	}
	public String getTenDMQ()
	{
		return this.TenDMQ;
	}


	public void setuserId(String userId) {

		this.userId = userId;
	}

	public String getuserId() {

		return this.userId;
	}

	public void setthuongId(String thuongId) {

		this.thuongId = thuongId;
	}

	public String getthuongId() {

		return this.thuongId;
	}

	public void setnppId(String nppId) {

		this.nppId = nppId;
	}

	public String getnppId() {

		return this.nppId;
	}

	public void setnppTen(String nppTen) {

		this.nppTen = nppTen;
	}

	public String getnppTen() {

		return this.nppTen;
	}

	public void setkenhId(String kenhId) {

		this.kenhId = kenhId;
	}

	public String getkenhId() {

		return this.kenhId;
	}

	public void setdvkdId(String dvkdId) {

		this.dvkdId = dvkdId;
	}

	public String getdvkdId() {

		return this.dvkdId;
	}

	public void setASMId(String asmId) {

		this.asmId = asmId;
	}

	public String getASMId() {

		return this.asmId;
	}

	public void setBMId(String bmId) {

		this.bmId = bmId;
	}

	public String getBMId() {

		return this.bmId;
	}

	public void setnhanhangId(String nhanhangId) {

		this.nhanhangId = nhanhangId;
	}

	public String getnhanhangId() {

		return this.nhanhangId;
	}

	public void setchungloaiId(String chungloaiId) {

		this.chungloaiId = chungloaiId;
	}

	public String getchungloaiId() {

		return this.chungloaiId;
	}

	public void setkenh(ResultSet kenh) {

		this.kenh = kenh;
	}

	public ResultSet getkenh() {

		return this.kenh;
	}

	public void setdvkd(ResultSet dvkd) {

		this.dvkd = dvkd;
	}

	public ResultSet getdvkd() {

		return this.dvkd;
	}

	public ResultSet getcttbRs(){
		return this.cttbRs;
	}

	public void setASM(ResultSet asm) {

		this.asm = asm;
	}

	public ResultSet getASM() {

		return this.asm;
	}

	public void setBM(ResultSet bm) {

		this.bm = bm;
	}

	public ResultSet getBM() {

		return this.bm;
	}

	public void setnhanhang(ResultSet nhanhang) {

		this.nhanhang = nhanhang;
	}

	public ResultSet getnhanhang() {

		return db.get(" select pk_seq, ten from nhanhang where trangthai = 1  ");
	}

	public void setchungloai(ResultSet chungloai) {

		this.chungloai = chungloai;
	}

	public ResultSet getchungloai() {

		String query = " select pk_seq, ten from chungloai where trangthai = 1  ";
		if(this.nhanhangId.length() > 0)
			query +=" and pk_seq in (select CL_FK from nhanhang_chungloai where NH_FK = "+this.nhanhangId+") ";
		return db.get(query);
	}

	public void setMonth(String month) {

		this.month = month;
	}

	public String getMonth() {
		if(this.month.length() > 0){
			return this.month;	
		}else{
			return this.getdate().substring(5, 7);
		}

	}

	public void setUnghang(String unghang) {

		this.unghang = unghang;
	}

	public String getUnghang() {

		return this.unghang;
	}


	public void setcovat(String covat) {

		this.covat = covat;
	}

	public String getcovat() {

		return this.covat;
	}


	public void setDHchot(String dhchot1) {

		this.dhchot1 = dhchot1;
	}

	public String getDHchot() {

		return this.dhchot1;
	}

	public void setYear(String year) {

		this.year = year;
	}

	public String getYear() {
		if(this.year.length() > 0){
			return this.year;	
		}else{
			return this.getdate().substring(0, 4);
			//return "2013"; //2013 hoac 2012 co du lieu test
		}
	}

	public void initChuyenTuyen()
	{
		geso.dms.center.util.Utility util =new geso.dms.center.util.Utility();
		String sql = 	" select pk_seq,ten, ma from nhaphanphoi where trangthai ='1' " +
						" 	and pk_seq in "+util.quyen_npp(this.userId);
		this.npp = db.get(sql);
		
		if(this.nppId.length()>0)
		{
			sql = 	"\n select pk_seq, ten from daidienkinhdoanh dd " +
					"\n where npp_fk = "+this.nppId+"" +
					"\n 		and ( trangthai = 1  or exists ( select 1 from tuyenbanhang t where t.ddkd_fk = dd.pk_seq and exists (select 1 from khachhang_tuyenbh x where x.tbh_fk = t.pk_seq  )  ) ) ";
			this.rsddkd = db.getScrol(sql);
		}
	}
	
	public ResultSet kbhRs_create()
	{
		String query = " select pk_seq,ten,diengiai from kenhbanhang where  trangthai = 1   and  ( pk_seq in ("+geso.dms.center.util.Utility.PhanQuyenKBH(this.userId)+") or (select phanloai from nhanvien where pk_seq ="+this.userId+")=1 ) ";
		
		return db.get(query);
	}
	
	public ResultSet bmRs_Create()
	{
		String query = 	" SELECT BM.PK_SEQ, BM.TEN  " +
		" FROM BM BM " +
		" WHERE BM.TRANGTHAI ='1'";
		
		
		
		if (this.kenhId.length() > 0) {
		query += " AND BM.KBH_FK ='" + this.kenhId + "'";
		} 
		
		if(this.vungId.length() > 0){
		query +=  " AND BM.pk_seq in ( select BM_FK from bm_chinhanh where vung_fk =  '" + this.vungId + "') "; 
		}
		if(this.phanloai.equals("2") )
		{
			query += "\n and kbh_fk in ("+geso.dms.center.util.Utility.PhanQuyenKBH(this.userId)+")  ";
			
			if(this.loainv.equals("2")) //asm
				query += "\n and	 BM.pk_seq in ( select top 1 BM_FK from asm where pk_seq in (  select top(1) asm_fk from NHANVIEN where PK_SEQ ='"+userId+"'  ) )  ";
			else if(this.loainv.equals("3"))// gsbh
				query += "\n and	 BM.pk_seq in (   select top 1 BM_FK from asm where pk_seq in ( select asm_fk from  GIAMSATBANHANG where pk_seq in (  select top(1) gsbh_fk from NHANVIEN where PK_SEQ ='"+userId+"'  )   )   ) ";
			else if(this.loainv.equals("1"))// rsm
				query += "\n and	 BM.pk_seq in (select top(1) BM_FK from NHANVIEN where PK_SEQ ='"+userId+"'  ) ";
		}	
		return db.get(query);
	}
	public ResultSet asmRs_Create()
	{
		String query = 	"SELECT ASM.PK_SEQ, ASM.TEN  " +
		" FROM ASM ASM " +		
		" WHERE ASM.TRANGTHAI ='1'";
		
		if(this.bmId.length() > 0)
		{
			query += " and asm.pk_seq in ("+geso.dms.center.util.Utility.get_ASM_from_BM(this.bmId)+") ";
		}
		
		if (this.kenhId.length() > 0) {
			query += " AND ASM.KBH_FK ='" + this.kenhId + "'";
		} 
		
		if(this.khuvucId.length() > 0){
			query += " AND ASM.pk_Seq in ( select khuvuc_fk from asm_khuvuc where khuvuc_fk  ='" + this.khuvucId + "' ) "; 
		}
		else
			if(this.vungId.length()>0){
				query +=  " AND ASM.pk_Seq in ( select khuvuc_fk from asm_khuvuc where khuvuc_fk in (select pk_seq from khuvuc where vung_fk = ='" + this.vungId + "'  ) ) ";
			}
		if(this.phanloai.equals("2") )
		{
			query += "\n and kbh_fk in ("+geso.dms.center.util.Utility.PhanQuyenKBH(this.userId)+")  ";
			
			if(this.loainv.equals("2")) //asm
				query += "\n and	ASM.pk_Seq in (select top(1) asm_fk from NHANVIEN where PK_SEQ ='"+userId+"'  ) ";
			else if(this.loainv.equals("3"))// gsbh
				query += "\n and	 ASM.pk_Seq in ( select top 1 asm_fk from GIAMSATBANHANG where pk_seq in (  select top(1) gsbh_fk from NHANVIEN where PK_SEQ ='"+userId+"'  ) ) ";
			else if(this.loainv.equals("1"))// rsm
				query += "\n and	 ASM.BM_FK in (select top(1) BM_FK from NHANVIEN where PK_SEQ ='"+userId+"'  ) ";
		}	
		return db.get(query);
	}
	public ResultSet gsbhRs_Create()
	{
		String query = 	"SELECT  GSBH.PK_SEQ, GSBH.TEN " +
		"FROM GIAMSATBANHANG GSBH " +
		"WHERE GSBH.TRANGTHAI = '1'" ;

		if(this.asmId.length() > 0)
		{
			query += " and GSBH.pk_seq in ("+geso.dms.center.util.Utility.get_GSBH_from_ASM(this.asmId)+") ";
		}
		else
		if(this.bmId.length() > 0)
		{
			query += " and GSBH.pk_seq in ("+geso.dms.center.util.Utility.get_GSBH_from_BM(this.bmId)+") ";
		}
		
			
		
		if (this.kenhId.length() > 0) {
			query += " AND GSBH.KBH_FK ='" + this.kenhId + "'";
		} 
		if(this.khuvucId.length()>0){
			query +=  " AND gsbh.pk_Seq in ( select khuvuc_fk from gsbh_khuvuc where khuvuc_fk  ='" + this.khuvucId + "' ) ";
		}
		else
			if(this.vungId.length()>0){
				query +=  " AND gsbh.pk_Seq in ( select khuvuc_fk from gsbh_khuvuc where khuvuc_fk in (select pk_seq from khuvuc where vung_fk ='" + this.vungId + "'  ) ) ";
			}
		if(this.phanloai.equals("2") )
		{
			query += "\n and kbh_fk in ("+geso.dms.center.util.Utility.PhanQuyenKBH(this.userId)+")  ";
			
			
			if(this.loainv.equals("2")) //asm
				query += "\n and	GSBH.asm_fk  = (select top(1) asm_fk from NHANVIEN where PK_SEQ ='"+userId+"'  ) ";
			else if(this.loainv.equals("3"))// gsbh
				query += "\n and	 GSBH.pk_seq = (select top(1) gsbh_fk from NHANVIEN where PK_SEQ ='"+userId+"'  ) ";
			else if(this.loainv.equals("1"))// rsm
				query += "\n and	 GSBH.asm_fk  in (select  pk_seq from asm a  where a.bm_fk = (select top(1) bm_fk from NHANVIEN where PK_SEQ ='"+userId+"'  )  ) ";
		}	
		return db.get(query);
	}
	
	public ResultSet vungRs_create()
	{
		String query = "\n select pk_seq,ten,diengiai from vung " +
				"\n where 1 = 1 ";
		if(this.phanloai.equals("2") )
		{
			if(this.loainv.equals("2")) //asm
				query += "\n	  and pk_seq in ( "+ geso.dms.center.util.Utility.get_VUNG_from_ASM(" select top(1) asm_fk from NHANVIEN where PK_SEQ ='"+userId+"' ")  +" ) ";
			else if(this.loainv.equals("3"))// gsbh
				query += "\n	  and pk_seq in ( "+ geso.dms.center.util.Utility.get_VUNG_from_GSBH(" select top(1) gsbh_fk from NHANVIEN where PK_SEQ ='"+userId+"' ")  +" ) ";
			else if(this.loainv.equals("1"))// rsm
				query += "\n	  and pk_seq in ( "+ geso.dms.center.util.Utility.get_VUNG_from_BM(" select top(1) bm_fk from NHANVIEN where PK_SEQ ='"+userId+"' ")  +" ) ";
		}
		return db.get(query);
	}
	public ResultSet khuvucRs_create()
	{
		String query = " select pk_seq,ten from khuvuc where 1 = 1  ";
		if(this.vungId.length() > 0)
			query += " and vung_fk =  " + this.vungId;
		if(this.phanloai.equals("2") )
		{
			if(this.loainv.equals("2")) //asm
				query += "\n	  and pk_seq in ( "+ geso.dms.center.util.Utility.get_KHUVUC_from_ASM(" select top(1) asm_fk from NHANVIEN where PK_SEQ ='"+userId+"' ")  +" ) ";
			else if(this.loainv.equals("3"))// gsbh
				query += "\n	  and pk_seq in ( "+ geso.dms.center.util.Utility.get_KHUVUC_from_GSBH(" select top(1) gsbh_fk from NHANVIEN where PK_SEQ ='"+userId+"' ")  +" ) ";
			else if(this.loainv.equals("1"))// rsm
				query += "\n	  and pk_seq in ( "+ geso.dms.center.util.Utility.get_KHUVUC_from_BM(" select top(1) bm_fk from NHANVIEN where PK_SEQ ='"+userId+"' ")  +" ) ";
	
		}	
		return db.get(query);
	}
	
	public ResultSet nppRs_create()
	{
		Utility Ult = new Utility();
		String query = "select pk_seq,ten, ma from nhaphanphoi where trangthai ='1' ";
		if(this.view.equals("NPP")){
			this.nppId = Ult.getIdNhapp(this.userId);
			query += " and pk_seq ="+this.nppId+""; 
		}
		if(this.gsbhId.length() > 0)
			query += " and pk_seq in ( "+geso.dms.center.util.Utility.get_NPP_from_GSBH(this.gsbhId)+" ) ";
		else if(this.asmId.length() > 0)
			query += " and pk_seq in ( "+geso.dms.center.util.Utility.get_NPP_from_ASM(this.asmId)+" ) ";
		
		else if(this.bmId.length() > 0)
			query += " and pk_seq in ( "+geso.dms.center.util.Utility.get_NPP_from_BM(this.bmId)+" ) ";
		 
		 
		
		if (this.khuvucId.length() > 0) {
			query += " and khuvuc_fk in (" + this.khuvucId + " )";
		}
		else if (this.vungId.length() > 0) {
			query += " and khuvuc_fk in (select pk_seq from khuvuc where vung_fk in (" + this.vungId + ") )";
		}


		geso.dms.center.util.Utility util =new geso.dms.center.util.Utility();

		if((this.phanloai).equals("1")){
			query += " and pk_seq = '"+ this.nppId +"'";			
		}
		else
		{
			if(this.loainv.equals("2")) //asm
				query += "\n	 and pk_seq in ( " + geso.dms.center.util.Utility.get_NPP_from_ASM("select top(1) asm_fk from NHANVIEN where PK_SEQ ='"+userId+"'") + ") ";
			else if(this.loainv.equals("3"))// gsbh
				query += "\n	 and pk_seq in ( " + geso.dms.center.util.Utility.get_NPP_from_GSBH("select top(1) gsbh_fk from NHANVIEN where PK_SEQ ='"+userId+"'") + ") ";
			else if(this.loainv.equals("1"))// rsm
				query += "\n	 and pk_seq in ( " + geso.dms.center.util.Utility.get_NPP_from_BM("select top(1) BM_FK from NHANVIEN where PK_SEQ ='"+userId+"'") + ") ";
			else
				query += " and pk_seq in "+util.quyen_npp(this.userId);
		}
		
		query += " order by ltrim(ten) ";
		System.out.println(query);
		return db.get(query);
	}
	
	public ResultSet ddkdRs_create()
	{
		Utility Ult = new Utility();
		String query = " SELECT pk_seq,ten FROM DAIDIENKINHDOANH d where  d.trangthai=1 ";
		if(this.view.equals("NPP")){
			this.nppId = Ult.getIdNhapp(this.userId);
 		}
		if(this.gsbhId.length() > 0)
			query += " and pk_seq in ( "+geso.dms.center.util.Utility.get_DDKD_from_GSBH(this.gsbhId)+" ) ";
		else if(this.asmId.length() > 0)
			query += " and pk_seq in ( "+geso.dms.center.util.Utility.get_DDKD_from_ASM(this.asmId)+" ) ";
		else
		if(this.bmId.length() > 0)
			query += " and pk_seq in ( "+geso.dms.center.util.Utility.get_DDKD_from_BM(this.bmId)+" ) ";
		
		else 		
		if(this.nppId.length() > 0)
		{
			query +=  " and pk_seq in ( "+geso.dms.center.util.Utility.get_DDKD_from_NPP(this.nppId)+" ) ";
		}
		
		
		if(this.phanloai.equals("2") )
		{
			query += " and d.kbh_fk in ("+geso.dms.center.util.Utility.PhanQuyenKBH(this.userId)+")  ";
			
			if(this.loainv.equals("2")) //asm
				query += "\n	and pk_seq in ( "+geso.dms.center.util.Utility.get_DDKD_from_ASM("select top(1) asm_fk from NHANVIEN where PK_SEQ ='"+userId+"'  ")+" )   ";
			else if(this.loainv.equals("3"))// gsbh
				query += "\n	and pk_seq in ( "+geso.dms.center.util.Utility.get_DDKD_from_GSBH("select top(1) gsbh_fk from NHANVIEN where PK_SEQ ='"+userId+"'  ")+" )   ";
			else if(this.loainv.equals("1"))// rsm
				query += "\n	and pk_seq in ( "+geso.dms.center.util.Utility.get_DDKD_from_BM("select top(1) bm_fk from NHANVIEN where PK_SEQ ='"+userId+"'  ")+" )   ";
			else
				query += "\n	and d.npp_fk in ( "+ geso.dms.center.util.Utility.Quyen_npp(this.userId) +" )   ";
				
		}	
		
		return db.get(query);
	}
	
	public void init_user()
	{
		try{
			
			Utility Ult = new Utility();
			String query="select phanloai,loai from nhanvien where pk_seq="+this.userId;
			System.out.println(" user :" + query);
			ResultSet rs=this.db.get(query);
			if(rs!=null){
				if(rs.next()){

					this.phanloai = rs.getString("phanloai");
					System.out.println("Phan loai : "+this.phanloai);					 				
					this.loainv =  rs.getString("loai");
					if( rs.getString("phanloai").equals("1")){
						this.nppId = Ult.getIdNhapp(this.userId);
						this.nppTen = Ult.getTenNhaPP();
					}
					rs.close();
				}
			}
		}catch(Exception er){

		}
	}
	
	public void init() {

		init_user();
		
		Utility Ult = new Utility();
		String query = " ";

		
		this.vung = vungRs_create();
		
		
		this.khuvuc =  khuvucRs_create();
		
		this.npp = nppRs_create();
		
		this.kenh = kbhRs_create();
		
		
		this.dvkd = db.get("select pk_seq,diengiai,donvikinhdoanh from donvikinhdoanh ");
		this.tinhthanhRs = db.get("select pk_seq,ten from tinhthanh");
		this.nhomkh = db.get("select pk_seq, diengiai from nhomkhachhang");


		
		
		
		query = " select pk_seq, ten from nhanviengiaonhan where 1=1  ";
		if(this.nppId.length() > 0)
			query += " and  npp_fk in ("+this.nppId+") ";
		this.nvgn  = db.get(query);
		


	
		query = " select * from nhanhang where 1=1  ";
		if(this.dvkdId.length() > 0)
			query += " and  dvkd_fk in ("+this.dvkdId+") ";
		this.nhanhang  = db.get(query);



		this.chungloai = db.get("select pk_Seq,ten  from chungloai");
		if(this.nhanhangId.length() > 0)
			this.chungloai = db.get("select a.pk_Seq,a.ten  from chungloai a inner join nhanhang_chungloai b on a.pk_seq = b.cl_fk where b.nh_fk = '"+this.nhanhangId+"' ");

		this.loainpp = db.get("select pk_Seq,ten  from LoaiNPP");

		
		//System.out.println(sql);

		this.gsbh = gsbhRs_Create();		
		this.asm = asmRs_Create();
		this.bm = bmRs_Create();		
		
		this.setRsddkd(ddkdRs_create() );
		
		
		

		String st = "select pk_seq,ma,ten from sanpham where trangthai ='1'  ";
		if (this.nhanhangId.length() > 0)
			st = st + " and nhanhang_fk ='" + this.nhanhangId + "'";
		if (this.trangthaispId.length() > 0)
			st = st + " and trangthai='" + this.trangthaispId + "'";
		if (this.chungloaiId.length() > 0)
			st = st + " and chungloai_fk ='" + this.chungloaiId + "'";
		if (this.dvkdId.length() > 0)
			st = st + " and dvkd_fk ='" + this.dvkdId + "'";
		if(this.dvdlId != null)
			if (this.dvdlId.length() > 0)
				st = st + " and dvdl_fk ='" + this.dvdlId + "'";

		if (this.Nhospid.length() > 0)
			st = st + " and pk_seq in  (select sp_fk from nhomsanpham_sanpham where nsp_fk='"+this.Nhospid+"')";

		this.sanpham = db.get(st);

	
		this.dvdl = db.get( "select pk_seq,donvi from donvidoluong ");
		this.kho = db.get("select * from kho ");


		

		//Lay chuong trinh khuyen mai
		this.rsPrograms = db.get("SELECT pk_seq,diengiai,scheme FROM CTKHUYENMAI c where TUNGAY>='2018-01-01'");

		this.RsNhomSp=db.get("select pk_seq,diengiai from nhomsanpham where trangthai=1 and type='0' and loainhom='0'");

		this.RsQuyCach=db.get("select pk_seq,donvi as ten from donvidoluong where trangthai=1 ");

		this.cttbRs = db.get("select pk_seq, scheme from cttrungbay");

		this.thuong = db.get("select pk_seq,diengiai from thuongdauthung where trangthai = 1");

		geso.dms.center.util.Utility utils = new geso.dms.center.util.Utility();
		String querySkus = "select a.pk_seq, a.ten, a.ma, a.trangthai,"
			+ " a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua from nhomskus a, nhanvien b,"
			+ " nhanvien c where a.nguoitao = b.PK_SEQ and " + " a.nguoisua = c.PK_SEQ ";
		querySkus += " and a.pk_seq in " + utils.quyen_nhomskus(this.userId);
		querySkus += " order by pk_seq ";
		this.nhomskus = db.get(querySkus);
		if(this.ddkd!=null && this.ddkd.trim().length()>0)
			this.rstuyenbh=db.get("select * from TUYENBANHANG where DDKD_FK="+this.ddkd);

	}
	
	
	public String getLoaiMenu() {
		return loaiMenu;
	}

	public void setLoaiMenu(String loaiMenu) {
		this.loaiMenu = loaiMenu;
	}
	public void settungay(String tungay) {

		this.tungay = tungay;
	}

	public String gettungay() 
	{
		return tungay;
	}

	public void setdenngay(String denngay) {

		this.denngay = denngay;
	}

	public String getdenngay() {

		return this.denngay;
	}

	public void setMsg(String msg) {

		this.msg = msg;
	}

	public String getMsg() {

		return this.msg;
	}

	public void setuserTen(String userTen) {

		this.userTen = userTen;
	}

	public String getuserTen() {

		return this.userTen;
	}

	public void setkhoId(String khoId) {

		this.khoId = khoId;
	}

	public String getkhoId() {

		return this.khoId;
	}

	public void setkho(ResultSet kho) {

		this.kho = kho;
	}

	public ResultSet getkho() {

		return this.kho;
	}

	public void setbook(String book) {

		this.book = book;
	}

	public String getbook() {

		return this.book;
	}

	public void setvat(String vat) {

		this.vat = vat;
	}

	public void DBclose() {
		try {
			if(chungloai != null)
				chungloai.close();
			if(loainpp != null)
				loainpp.close();
			if(dvdl != null)
				dvdl.close();
			if(dvkd != null)
				dvkd.close();
			if(gsbh != null)
				gsbh.close();
			if(kenh != null)
				kenh.close();
			if(kho != null)
				kho.close();
			if(khuvuc != null)
				khuvuc.close();
			if(nhanhang != null)
				nhanhang.close();
			if(npp != null)
				npp.close();
			if(rsddkd != null)
				rsddkd.close();
			if(rsPrograms != null)
				rsPrograms.close();
			if(sanpham != null)
				sanpham.close();
			if(vung != null)
				vung.close();
			if(RsNhomSp!=null){
				RsNhomSp.close();
			}
			if(RsQuyCach!=null){
				RsQuyCach.close();
			}
			if(cttbRs!=null){
				cttbRs.close();
			}
			if(db != null)
				db.shutDown();

			if(asm!=null){
				asm.close();
			}
			if(bm!=null){
				bm.close();
			}

		} catch (Exception e) {
			// TODO: handle exception

			System.out.println("khong the dong ket noi");
		}
	}


	public void setvungId(String vungId) {

		this.vungId = vungId;
	}
	public String getvungId() {

		return this.vungId;
	}

	public void setcttbId(String cttbId){
		this.cttbId = cttbId;
	}
	public void setMultiCttbId(String[] arr)
	{
		String tmp = "";
		if(arr != null && arr.length > 0 )
		{
			for(int i = 0 ; i< arr.length ; i ++)
			{
				if(arr[i] != null)
				{
					tmp +=  tmp.length() > 0 ? "," + arr[i] :  arr[i]; 
				}
			}
		}
		this.cttbId = tmp;
	}
	public String getcttbId(){
		return this.cttbId;
	}

	public void setvung(ResultSet vung) {

		this.vung = vung;
	}

	public ResultSet getvung() {

		return this.vung;
	}


	public void setthuong(ResultSet thuong) {

		this.thuong = thuong;
	}

	public ResultSet getthuong() {

		return this.thuong;
	}

	public void setkhuvucId(String khuvucId) {

		this.khuvucId = khuvucId;
	}

	public String getkhuvucId() {

		return this.khuvucId;
	}

	public void setkhuvuc(ResultSet khuvuc) {

		this.khuvuc = khuvuc;
	}

	public ResultSet getkhuvuc() {

		return this.khuvuc;
	}

	public void setnpp(ResultSet npp) {

		this.npp = npp;
	}

	public ResultSet getnpp() {

		return this.npp;
	}

	public void setgsbhId(String gsbhId) {

		this.gsbhId = gsbhId;
	}

	public String getgsbhId() {

		return this.gsbhId;
	}

	public void setgsbh(ResultSet gsbh) {

		this.gsbh = gsbh;
	}

	public ResultSet getgsbh() {

		return this.gsbh;
	}

	public void setsanphamId(String sanphamId) {

		this.sanphamId = sanphamId;
	}

	public String getsanphamId() {

		return this.sanphamId;
	}

	public void setsanpham(ResultSet sanpham) {

		this.sanpham = sanpham;
	}

	public ResultSet getsanpham() {

		return this.sanpham;
	}

	public void setdvdlId(String dvdlId) {

		this.dvdlId = dvdlId;
	}

	public String getdvdlId() {

		return this.dvdlId;
	}

	public void setdvdl(ResultSet dvdl) {

		this.dvdl = dvdl;
	}

	public ResultSet getdvdl() {

		return this.dvdl;
	}

	public void setFieldShow(String[] fieldShow) {

		this.FieldShow = fieldShow;
	}

	public String[] getFieldShow() {
		return this.FieldShow;

	}

	public void setFieldHidden(String[] fieldHidden) {

		this.FieldHidden = fieldHidden;
	}

	public String[] getFieldHidden() {

		return this.FieldHidden;
	}

	public void setngayton(String ngayton) {

		this.ngayton = ngayton;
	}

	public String getngayton() {

		return this.ngayton;
	}

	public String getDateTime() 
	{
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public String getdate() 
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public String getdiscount() {

		return this.discount;
	}

	public String getpromotion() {

		return this.promotion;
	}

	public void setdiscount(String discount) {

		this.discount = discount;
	}

	public void setpromotion(String promotion) {

		this.promotion = promotion;
	}

	public String getvat() {

		return this.vat;
	}

	public String getlessday() {

		return this.lessday;
	}

	public String getmoreday() {

		return this.moreday;
	}

	public void setlessday(String lessday) {

		this.lessday = lessday;
	}

	public void setmoreday(String moreday) {

		this.moreday = moreday;
	}

	public void setDdkd(String ddkd) {
		this.ddkd = ddkd;
	}

	public String getDdkd() {

		return this.ddkd;
	}

	public void setRsddkd(ResultSet rs) {
		this.rsddkd = rs;
	}

	public ResultSet getRsddkd() {
		return this.rsddkd;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getUnit() {

		return this.unit;
	}

	public void setGroupCus(String groupCus) {
		this.groupCus = groupCus;
	}

	public String getGroupCus() {

		return this.groupCus;
	}


	public void setPrograms(String programs) {
		this.programsId = programs;
	}

	public String getPrograms() {

		return this.programsId;
	}

	public void setRsPrograms(ResultSet RsPrograms) {
		this.rsPrograms = RsPrograms;
	}

	public ResultSet getRsPrograms() {

		return this.rsPrograms;
	}


	public void setDonViTinh(String donviTinh) {
		this.donviTinh = donviTinh;

	}

	public String getDonViTinh() 
	{
		return this.donviTinh;
	}

	public void setFromMonth(String month) 
	{
		this.fromMonth = month;
	}

	public String getFromMonth() 
	{
		return this.fromMonth;
	}

	public void setToMonth(String month)
	{
		this.toMonth = month;
	}

	public String getToMonth() 
	{
		return this.toMonth;
	}


	public void settype(String _type) {

		this.type=_type;
	}


	public String gettype() {

		return this.type;
	}

	public void setchon(String chon) {

		this.chon=chon;
	}


	public String getchon() {

		return this.chon;
	}


	public void setFromYear(String fromyear) {

		this.FromYear=fromyear;
	}


	public String getFromYear() {

		return this.FromYear;
	}


	public void setToYear(String toyear) {

		this.ToYear=toyear;
	}


	public String getToYear() {

		return this.ToYear;
	}


	public void SetNhoSPId(String nhomspid) {

		this.Nhospid=nhomspid;
	}


	public String GetNhoSPId() {

		return this.Nhospid;
	}


	public void setRsNhomSP(ResultSet rs) {

		this.RsNhomSp=rs;
	}


	public ResultSet GetRsNhomSP() {

		return this.RsNhomSp;
	}


	public void setRsQuyCach(ResultSet QuyCach) {

		this.RsQuyCach=QuyCach;
	}


	public ResultSet GetRsQuyCach() {

		return this.RsQuyCach;
	}


	public void SetQuyCachId(String _QuyCachId) {

		this.QuyCachId=_QuyCachId;
	}


	public String GetQuyCachId() {

		return this.QuyCachId;
	}


	public ResultSet getNhomspRs() 
	{
		return this.nspRs;
	}

	public void setNhomspRs(ResultSet nspRs) 
	{
		this.nspRs = nspRs;
	}

	public String getNspId() 
	{
		return this.nspIds;
	}

	public void setNspId(String nspId) 
	{
		this.nspIds = nspId;
	}


	public void settumuc(String tumuc) {

		this.tumuc = tumuc;
	}


	public String gettumuc() {

		return tumuc;
	}


	public void setdenmuc(String denmuc) {

		this.denmuc = denmuc;
	}


	public String getdenmuc() {

		return denmuc;
	}


	public void setNhomkhId(String nhomkhId) {

		this.nhomkhId = nhomkhId;
	}


	public String getNhomkhId() {

		return nhomkhId;
	}


	public void setNhomkh(ResultSet nhomkh) {

		this.nhomkh = nhomkh;
	}


	public ResultSet getNhomkh() {

		return nhomkh;
	}


	public void setnvgn(ResultSet nvgn) {

		this.nvgn = nvgn;
	}


	public ResultSet getnvgn() {

		return this.nvgn;
	}


	public void setnvgnId(String nvgnId) {

		this.nvgnId = nvgnId;
	}


	public String getnvgnId() {

		return this.nvgnId;
	}


	public void setCheck(String check) {
		this.check = check;

	}


	public String getCheck() {

		return check;
	}


	public void setPhanloai(String pl) {

		this.phanloai = pl;
	}


	public String getPhanloai() {

		return this.phanloai;
	}


	public void setDhChot(String dhchot) {

		this.dhchot = dhchot;		
	}


	public String getDhChot() {

		return this.dhchot;
	}


	public void setloainppId(String loainppId) {
		this.loainppId = loainppId;
	}


	public String getloainppId() {
		return this.loainppId;
	}


	public void setloainpp(ResultSet loainpp) {
		this.loainpp = loainpp;
	}


	public ResultSet getloainpp() {
		return this.loainpp;
	}

	String xemtheo;

	public void setxemtheo(String xemtheo) 
	{
		this.xemtheo =xemtheo;
	}

	public String getxemtheo() 
	{
		return this.xemtheo;
	}

	public void setTask(String task) 
	{
		this.task =task;
	}

	public String getTask() 
	{
		return this.task;
	}
	public String getTinhthanhid() {
		return tinhthanhid;
	}

	public void setTinhthanhid(String tinhthanhid) {
		this.tinhthanhid = tinhthanhid;
	}




	public void setSpId(String spid) {

		this.spid = spid;
	}


	public String getSpId() {

		return this.spid;
	}


	public void setkhId(String khId) {

		this.khId = khId;
	}


	public String getkhId() {

		return this.khId;
	}

	public ResultSet GetTinhThanh() {

		String query = "  select pk_seq,ten from tinhthanh  where 1 = 1 ";
		if(this.khuvucId !=  null && this.khuvucId.length() > 0)
			query += " and pk_seq in (  select qh.TINHTHANH_FK  from KHUVUC_QUANHUYEN x inner join QUANHUYEN qh on qh.PK_SEQ = x.QUANHUYEN_FK where x.KHUVUC_FK = " + this.khuvucId + ")";
		return db.get(query);
	}

	public void setDlnId(String DlnId) {

		this.dlnid = DlnId;
	}


	public String getDlnId() {

		return this.dlnid;
	}
	public String getTuyenbhId() {
		return tuyenbhId;
	}

	public void setTuyenbhId(String tuyenbhId) {
		this.tuyenbhId = tuyenbhId;
	}

	public ResultSet GetDlnRs() {
		// TODO Auto-generated method stub
		return this.DlnRs;
	}
	String loainv = "1";
	public void setLoaiNv(String LoaiNv) {

		this.loainv = LoaiNv;
	}


	public String getLoaiNv() {

		return this.loainv;
	}

	ResultSet  anhtrungbayRs ;
	public void setAnhtrungbayRs(String query) 
	{
		this.anhtrungbayRs = db.get(query);
	}
	public ResultSet getAnhtrungbayRs() 
	{
		return this.anhtrungbayRs;
	}

	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTtId() {
		return ttId;
	}
	public void setTtId(String ttId) {
		this.ttId = ttId;
	}

	public dbutils getDb() {
		return db;
	}
	
	String text_baocaoSR = "";
	ArrayList<String> arr_text_baocaoSR;
	
	public ArrayList<String> getArr_text_baocaoSR() {
		return arr_text_baocaoSR;
	}
	public void setArr_text_baocaoSR(ArrayList<String> arr_text_baocaoSR) {
		this.arr_text_baocaoSR = arr_text_baocaoSR;
	}
	public String getText_baocaoSR() {
		return text_baocaoSR;
	}
	public void setText_baocaoSR(String text_baocaoSR) {
		this.text_baocaoSR = text_baocaoSR;
	}

	public void initUploadMCP()
	{
		

		Utility Ult = new Utility();
		String sql="";

		try{
			sql = "select pk_seq,ten from tinhthanh ";

			this.tinhthanhRs = db.get(sql);


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


		this.vung = db.get("select pk_seq,ten,diengiai from vung where 1 = 1"+quyenvung);
		System.out.println("Vung: "+"select pk_seq,ten,diengiai from vung where 1 = 1"+quyenvung);



		if (this.vungId.length() > 0) {
			this.khuvuc = db.get("select pk_seq,ten from khuvuc where vung_fk in ("
					+ this.vungId + ") "+quyenvung);
		} else
			this.khuvuc = db.get("select pk_seq,ten from khuvuc where 1 = 1 "+quyenkhuvuc);

		
		
		sql = "select pk_seq,ten, ma from nhaphanphoi where 1=1 ";
		if (this.khuvucId.length() > 0) {
			sql += " and khuvuc_fk in (" + this.khuvucId + " )";
		}
		if (this.vungId.length() > 0) {
			sql += " and khuvuc_fk in (select pk_seq from khuvuc where vung_fk in ("	+ this.vungId + ") )";
		}
		geso.dms.center.util.Utility util =new geso.dms.center.util.Utility();

		if((this.phanloai).equals("1")){
			sql += " and pk_seq = '"+ this.nppId +"'";			
		}		
		else
		{
			sql=sql+" and pk_seq in "+util.quyen_npp(this.userId);
			
		}
		sql+=" order by ltrim(ten) ";
		System.out.println("Get NPP (stockintransit ):"+sql);
		this.npp = db.getScrol(sql);

	
	

	
	}
	public String getView() {
		return view;
	}
	public void setView(String view) {
		this.view = view;
	}
	public ResultSet getTieuchithuongTlRs()
	{
		String query =  " select pk_seq,scheme, diengiai " +
						" from tieuchithuongtl  tl" +
						" where exists ( select 1 from duyettrakhuyenmai x where x.ctkm_fk = tl.pk_seq and x.trangthai = 1 )" ;
		if(this.tungay != null && this.tungay.trim().length() > 0)
			query +=" and tl.ngayds_tungay >='"+this.tungay+"'";
			
		if(this.denngay != null && this.denngay.trim().length() > 0)
			query +=" and tl.ngayds_denngay <='"+this.denngay+"'";			
		return db.get(query);
		
	}
	
}
