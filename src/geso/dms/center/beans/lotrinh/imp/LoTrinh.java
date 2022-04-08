package geso.dms.center.beans.lotrinh.imp;

import java.sql.ResultSet;





import geso.dms.center.beans.lotrinh.ILoTrinh;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;

public class LoTrinh implements ILoTrinh{

	String userId;
	String nppId;
	String ddkdId;
	String tuyenId;

	String tungay;
	String denngay;
	ResultSet tuyen;
	ResultSet npp;
	ResultSet ddkd;
	dbutils db;
	ResultSet danhsach;
	ResultSet kenh;
	String kenhId;
	private String vungId;
	private ResultSet vung;
	private String khuvucId;
	private ResultSet khuvuc;
	private String status;
	private String vitri;
	String view;
	public LoTrinh()
	{
		this.vungId = "";
		this.khuvucId="";
		this.status = "";
		this.nppId ="";
		this.ddkdId ="";
		this.tuyenId ="";
		this.kenhId ="";
		this.tungay = "";
		this.denngay = "";
		this.vitri = "";
		this.view = "TT";
		db = new dbutils();
	}
	
	public void setKenhId(String kenhId){
		this.kenhId = kenhId;
	}
	public void setnppId(String nppId) {
		
		this.nppId = nppId;
	}

	
	public String getnppId() {
		
		return this.nppId;
	}

	
	public void setddkdId(String ddkdId) {
		this.ddkdId = ddkdId;
		
	}

	
	public String getddkdId() {
		
		return this.ddkdId;
	}

	
	public ResultSet getnpp() {
		
		return this.npp;
	}

	
	public ResultSet getddkd() {
		
		return this.ddkd;
	}

	
	public void init() {
		Utility util = new Utility();
		// BC lấy từ npp
		if(this.view.equals("NPP"))
		{
			this.nppId = util.getIdNhapp(this.userId);
		}
		
		String phanloai = "",loai = "";
		try
		{
			

			String			sql = "select phanloai,LOAI from nhanvien where pk_seq=" + this.userId;
			ResultSet rs = this.db.get(sql);
			if (rs != null)
			{
				if (rs.next())
				{
					phanloai= rs.getString("phanloai");
					loai= rs.getString("LOAI")==null?"":rs.getString("LOAI");

					rs.close();
				}
			}
			//System.out.println("sql : " + sql);
		} catch (Exception er)
		{

		}
		
		
		String quyenvung = "";
		 String quyenkhuvuc = "";
		 if(phanloai.equals("2") )
		 {
			 if(loai.equals("2"))
			 {
				 quyenvung = " and pk_seq in (select distinct kv.VUNG_FK from ASM_KHUVUC a inner join KHUVUC kv on a.KHUVUC_FK = kv.PK_SEQ where a.ASM_FK = (select top(1) asm_fk from NHANVIEN where PK_SEQ ='"+userId+"'  )  )";
				 
				 quyenkhuvuc = " and pk_seq in (select distinct kv.pk_seq from ASM_KHUVUC a inner join KHUVUC kv on a.KHUVUC_FK = kv.PK_SEQ where a.ASM_FK = (select top(1) asm_fk from NHANVIEN where PK_SEQ ='"+userId+"'  )  )";
			 }
			 
			 if(loai.equals("3"))
			 {
				 quyenvung = " and pk_seq in (select distinct kv.VUNG_FK from GSBH_KHUVUC a inner join KHUVUC kv on a.KHUVUC_FK = kv.PK_SEQ where a.GSBH_FK = (select top(1) gsbh_fk from NHANVIEN where PK_SEQ ='"+userId+"'  )  )";
				 
				 quyenkhuvuc = " and pk_seq in (select distinct kv.pk_seq from GSBH_KHUVUC a inner join KHUVUC kv on a.KHUVUC_FK = kv.PK_SEQ where a.GSBH_FK = (select top(1) gsbh_fk from NHANVIEN where PK_SEQ ='"+userId+"'  )  )";
			 }
		 }
		 
		 
		String sql = "select * from nhaphanphoi where trangthai='"+this.status+"' and priandsecond = 0 and PK_SEQ IN "+util.quyen_npp(this.userId)+" order by ten";
		System.out.println("NPP "+sql);
		this.npp = db.get(sql);

		String vung = "select * from vung  where 1 = 1 "+quyenvung;
		this.vung = db.get(vung);
		
		String kenh = "select* from kenhbanhang";
		this.kenh = db.get(kenh);
		
		String khuvuc = "select * from khuvuc where 1 = 1 "+quyenkhuvuc;
		this.khuvuc = db.get(khuvuc);
		
		
		sql ="select * from daidienkinhdoanh where 1=1 ";
        
		 if(phanloai.equals("2") )
		 {
			 if(loai.equals("3"))
				 sql += " and pk_seq in ( select ddkd_fk from ddkd_gsbh where gsbh_fk = (select gsbh_fk from nhanvien where pk_seq ="+this.userId+" )  )  ";
		 }
		
		if(this.nppId != null && this.nppId.length()>0)
        	sql += "and npp_fk ='"+ this.nppId+"' ";
        if(this.vitri.length()>0)
        	sql += "and VITRI = '"+this.vitri+"' ";
        
        
        
        this.ddkd = db.get(sql);
        if(this.ddkdId.length()>0)
        	sql ="select distinct ngaylamviec,ngayid from tuyenbanhang where ddkd_fk ='"+ this.ddkdId +"' order by ngayid asc";
        else
        	sql ="select distinct ngaylamviec,ngayid from tuyenbanhang order by ngayid asc ";
        System.out.println("Lấy tuyến" + sql);
        this.tuyen = db.get(sql);
        
       String st="";
       String tableKH = "";
       String tableDH = "";
        if(this.nppId.length()>0)
	    {
        	st = st + " tbh.npp_fk ='"+ this.nppId +"'";
        	tableKH = tableKH + "where npp_fk = '"+this.nppId+"'";
        	tableDH = tableDH + "where npp_fk = '"+this.nppId+"'";
	    	
	    }
       
        
	    if(this.tuyenId.length()>0)
	    {
	    	if(st.length()>0)
	    		st = st + " and tbh.ngaylamviec ='"+ this.tuyenId +"' ";
	    	else
	    		st ="tbh.ngaylamviec ='"+ this.tuyenId +"' ";
	    }
	    if(this.ddkdId.length()>0)
	    {
	    	if(st.length()>0)
	    		st = st + " and tbh.ddkd_fk ='"+ this.ddkdId +"' ";
	    	else
	    		st = st + " tbh.ddkd_fk ='"+ this.ddkdId +"' ";
	    	
	    	if (tableDH.length() > 0)
	    		tableDH = tableDH + " and ddkd_fk = '"+this.ddkdId+"' ";
	    	else 
	    		tableDH = tableDH + " where ddkd_fk = '"+this.ddkdId+"' ";
	    }
	    
	    if (this.kenhId.length() > 0)
	    {
	    	if (tableDH.length() > 0)
	    	{
	    		tableDH = tableDH + " and kbh_fk = '"+this.kenhId+"' ";
	    	}
	    	else
	    	{
	    		tableDH = tableDH + " where kbh_fk = '"+this.kenhId+"' ";
	    	}
	    	if (tableKH.length()>0)
	    	{
	    		tableKH = tableKH + " and kbh_fk = '"+this.kenhId+"'";
	    	}
	    	else
	    	{
	    		tableKH = tableKH + " where kbh_fk = '"+this.kenhId+"'";
	    	}
	    }
	    //loc bang khachhang
	    
	    
	    
	    if(st.length()>0)
	    {
	    	st = " where " + st;
	    //khoi tao ket noi csdl
	    	sql  = "select tbh.ngaylamviec,kh.pk_seq as Customer_Key,kh.ten as Customer_Name,kh.diachi as Address,qh.ten as province,case when ds.tonggiatri is null then 0 else ds.tonggiatri end as Average_Volume,lch.diengiai as Outlet_Type,"+
			" vt.vitri as Outlet_Location,hch.hang as Outlet_Class,kh_tuyen.tanso as Frequency"+
			" from (select * from khachhang "+tableKH+") kh"+
			" left join quanhuyen qh on kh.quanhuyen_fk = qh.pk_seq"+
			" left join loaicuahang lch on lch.pk_seq = kh.lch_fk"+
			" left join vitricuahang vt on vt.pk_seq = kh.vtch_fk"+
			" left join hangcuahang hch on hch.pk_seq = kh.hch_fk"+
			" left join KHACHHANG_TUYENBH kh_tuyen on kh_tuyen.khachhang_fk = kh.pk_seq"+
			" left join (select a.khachhang_fk,cast(sum(a.tonggiatri)/3 as int) as tonggiatri from (select * from donhang "+tableDH+") a where a.ngaynhap >'2011-08-01' and a.ngaynhap < '2011-12-15' group by khachhang_fk) as ds"+
			" on ds.khachhang_fk = kh.pk_seq"+
			" left join tuyenbanhang tbh on tbh.pk_seq = kh_tuyen.tbh_fk "+ st +
			" order by tbh.ngaylamviec desc";
	    	System.out.println("Lay Du Lieu :"+sql);
			//this.danhsach = db.get(sql);
			
	    }
	    else
	    {
	    	sql  = "select tbh.ngaylamviec,kh.pk_seq as Customer_Key,kh.ten as Customer_Name,kh.diachi as Address,qh.ten as province,case when ds.tonggiatri is null then 0 else ds.tonggiatri end as Average_Volume,lch.diengiai as Outlet_Type,"+
			" vt.vitri as Outlet_Location,hch.hang as Outlet_Class,kh_tuyen.tanso as Frequency"+
			" from (select * from khachhang "+tableKH+") kh"+
			" left join quanhuyen qh on kh.quanhuyen_fk = qh.pk_seq"+
			" left join loaicuahang lch on lch.pk_seq = kh.lch_fk"+
			" left join vitricuahang vt on vt.pk_seq = kh.vtch_fk"+
			" left join hangcuahang hch on hch.pk_seq = kh.hch_fk"+
			" left join KHACHHANG_TUYENBH kh_tuyen on kh_tuyen.khachhang_fk = kh.pk_seq"+
			" left join (select a.khachhang_fk,cast(sum(a.tonggiatri)/3 as int) as tonggiatri from (select * from donhang "+tableDH+") a where a.ngaynhap >'2011-08-01' and a.ngaynhap < '2011-12-15' group by khachhang_fk) as ds"+
			" on ds.khachhang_fk = kh.pk_seq"+
			" left join tuyenbanhang tbh on tbh.pk_seq = kh_tuyen.tbh_fk "+ st +
			" order by tbh.ngaylamviec desc";
	    	System.out.println("Lay Du Lieu :"+sql);
			//this.danhsach = db.get(sql);
	    }
	   


		
	}


	
	public void settuyenId(String tuyenId) {
		
		this.tuyenId = tuyenId;
	}


	
	public String gettuyenId() {
		
		return this.tuyenId;
	}


	
	public ResultSet getTuyen() {
		
		return this.tuyen;
	}


	
	public ResultSet getdanhsach() {
		
		return this.danhsach;
	}

	
	public ResultSet getKenh() {
		
		return this.kenh;
	}
	
	public String getkenhId() {
		
		return this.kenhId;
	}

	@Override
	public void DBclose() {
		
		try {
			if(this.db != null)
				this.db.shutDown();
			if(this.danhsach != null)
				this.danhsach.close();
			if(this.ddkd != null)
				this.ddkd.close();
			if(this.kenh != null)
				this.kenh.close();
			if(this.npp != null)
				this.npp.close();
			if(this.tuyen != null)
				this.tuyen.close();
			
				
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public void setkhuvucId(String khuvucId) {
		
		this.khuvucId = khuvucId;
		
	}

	@Override
	public String getkhuvucId() {
		
		return this.khuvucId;
	}

	@Override
	public ResultSet getkhuvuc() {
		
		return this.khuvuc;
	}

	@Override
	public void createNPP() {
		
		Utility util = new Utility();
		
		this.vung = db.get("select * from vung order by ten");
		
		this.khuvuc = db.get("select * from khuvuc order by ten");
		if(this.vungId.trim().length() > 0)
			this.khuvuc = db.get("select * from khuvuc where vung_fk = "+ this.vungId +" order by ten");
		
		String sql = "select * from nhaphanphoi where trangthai='"+this.status+"' and PK_SEQ IN " +util.quyen_npp(this.userId) +"  order by ten";
		if(this.vungId.trim().length() > 0)
			sql = "select * from nhaphanphoi where khuvuc_fk in ( select pk_seq from khuvuc where vung_fk = '"+this.vungId+"') and trangthai='"+this.status+"' and PK_SEQ IN " +util.quyen_npp(this.userId) +" order by ten";
		if(this.khuvucId.trim().length() > 0)
			sql = "select * from nhaphanphoi where khuvuc_fk = '"+this.khuvucId+"' and trangthai='"+this.status+"' and PK_SEQ IN " +util.quyen_npp(this.userId) +" order by ten";
		this.npp = db.get(sql);
		
		//System.out.println("nhapp: select * from nhaphanphoi where khuvuc_fk = '"+this.khuvucId+"' order by ten");
	}

	
	public String getStatus() {
		
		return this.status;
	}

	
	public void setStatus(String status) {
		
		this.status = status;
	}

	
	public String getTungay(){
		return this.tungay;
	}
	
	public void setTungay(String tungay){
		this.tungay = tungay;
	}

	public String getDenngay(){
		return this.denngay;
	}
	
	public void setDenngay(String denngay){
		this.denngay = denngay;
	}

	@Override
	public String getViTri() {
		return this.vitri;
	}

	@Override
	public void setVitri(String vitri) {
		this.vitri = vitri;
	}

	@Override
	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String getUserId() {
		
		return this.userId;
	}

	public void setvungId(String vungId) 
	{
		this.vungId = vungId;
	}
	
	public String getvungId() 
	{
		return vungId;
	}

	public ResultSet getvung() {
		return this.vung;
	}

	public String getView() {
		
		return this.view;
	}

	
	public void setView(String view) {
		
		this.view = view;
	}
	
	public dbutils getDb() {
		return db;
	}
}
