package geso.dms.center.beans.Router.imp;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import geso.dms.center.beans.Router.IDRouter;
import geso.dms.center.db.sql.dbutils;

public class Router implements IDRouter{

	String nppId;
	String ddkdId;
	String tuyenId;
	String maKH, soluong, nguoitao, nguoisua, ngaytao, ngaysua, diengiai, sanpham;
	List<IDRouter> cnkhValue;
	ResultSet cnkhList;

	String id;
	String userId;
	String tungay;
	
	String denngay;

	String message;	
	ResultSet capnhatKH;
	
	ResultSet tuyen;
	ResultSet npp;
	ResultSet ddkd;
	dbutils db;
	ResultSet danhsach;
	ResultSet kenh;
	String kenhId;
	private String khuvucId;
	private ResultSet khuvuc;
	
	private String vungId;
	private ResultSet vung;
	
	private String status;
	
	String view;
	
	public Router()
	{
		this.id = "";
		this.userId = "";
		this.message = "";
		this.khuvucId="";
		this.tungay="";
		this.denngay="";
		this.vungId = "";
		this.status = "";
		this.nppId ="";
		this.ddkdId ="";
		this.tuyenId ="";
		this.kenhId ="";
		this.diengiai = "";
		this.view = "";
		db = new dbutils();
	}
	
	public Router(String id)
	{
		this.id = id;
		this.userId = "";
		this.message = "";
		this.tungay="";
		this.denngay="";
		this.khuvucId="";
		this.vungId = "";
		this.status = "";
		this.nppId ="";
		this.ddkdId ="";
		this.tuyenId ="";
		this.kenhId ="";
		this.diengiai = "";
		this.view = "";
		db = new dbutils();
	}
	
	public Router(String nppId, String ddkdId, String tuyenId, String maKH, String sanphamId, String soluong)
	{
		db = new dbutils();
		this.nppId = nppId;		
		this.ddkdId = ddkdId;
		this.tuyenId = tuyenId;
		this.maKH = maKH;
		this.sanpham = sanphamId;
		this.soluong = soluong;
		this.view = "";
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

	
	public void init() 
	{
		 String phanloai = "",loainv = "";
		 try{
			
		String sql="select phanloai,loai from nhanvien where pk_seq="+this.userId;
		System.out.println("Phan Loai: "+sql);
	 ResultSet rs=this.db.get(sql);
		 if(rs!=null){
			 if(rs.next()){
				 
				 phanloai = rs.getString("phanloai");	 				
				 loainv =  rs.getString("loai");
				 rs.close();
			 }
		 }
	 }catch(Exception er){
		 
	 }
	 String quyenvung = "";
	 String quyenkhuvuc = "";
	 if(phanloai.equals("2") )
	 {
		 if(loainv.equals("2"))
		 {
			 quyenvung = " and pk_seq in (select distinct kv.VUNG_FK from ASM_KHUVUC a inner join KHUVUC kv on a.KHUVUC_FK = kv.PK_SEQ where a.ASM_FK = (select top(1) asm_fk from NHANVIEN where PK_SEQ ='"+userId+"'  )  )";
			 
			 quyenkhuvuc = " and pk_seq in (select distinct kv.pk_seq from ASM_KHUVUC a inner join KHUVUC kv on a.KHUVUC_FK = kv.PK_SEQ where a.ASM_FK = (select top(1) asm_fk from NHANVIEN where PK_SEQ ='"+userId+"'  )  )";
		 }
		 
		 if(loainv.equals("3"))
		 {
			 quyenvung = " and pk_seq in (select distinct kv.VUNG_FK from GSBH_KHUVUC a inner join KHUVUC kv on a.KHUVUC_FK = kv.PK_SEQ where a.GSBH_FK = (select top(1) gsbh_fk from NHANVIEN where PK_SEQ ='"+userId+"'  )  )";
			 
			 quyenkhuvuc = " and pk_seq in (select distinct kv.pk_seq from GSBH_KHUVUC a inner join KHUVUC kv on a.KHUVUC_FK = kv.PK_SEQ where a.GSBH_FK = (select top(1) gsbh_fk from NHANVIEN where PK_SEQ ='"+userId+"'  )  )";
		 }
	 }
		String vung = "select * from vung where 1 = 1 "+quyenvung;
		this.vung = db.get(vung);
		
		String kenh = "select* from kenhbanhang  ";				
		this.kenh = db.get(kenh);
		
		String khuvuc = "select * from khuvuc where 1=1 "+quyenkhuvuc;
		if(this.vungId.trim().length() > 0) khuvuc += " and vung_fk = "+ this.vungId +" ";
		khuvuc += " order by ten ";
		System.out.println("Khu vuc Rs "+khuvuc);
		this.khuvuc = db.get(khuvuc);
		
		String sql = "select * from nhaphanphoi where trangthai='"+this.status+"' and priandsecond = 0 ";
		if(this.khuvucId.trim().length() > 0) sql += " and khuvuc_fk ='" + this.khuvucId + "' ";
		if(this.vungId.trim().length() > 0) sql += " and khuvuc_fk in (select pk_seq from khuvuc where vung_fk ="+ this.vungId +") ";
		sql += " order by ten ";
		this.npp = db.get(sql);
		
		
        if(this.nppId.length()>0)
        sql ="select * from daidienkinhdoanh where npp_fk ='"+ this.nppId+"'";
        else
        	sql ="select * from daidienkinhdoanh";
        this.ddkd = db.get(sql);
        if(this.ddkdId.length()>0)
        	sql ="select distinct ngaylamviec,ngayid from tuyenbanhang where ddkd_fk ='"+ this.ddkdId +"' order by ngayid asc";
        else
        	sql ="select distinct ngaylamviec,ngayid from tuyenbanhang order by ngayid asc ";
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
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		this.khuvucId = khuvucId;
		
	}

	@Override
	public String getkhuvucId() {
		// TODO Auto-generated method stub
		return this.khuvucId;
	}

	public ResultSet getkhuvuc() {
		// TODO Auto-generated method stub
		return this.khuvuc;
	}


	public void createNPP() {
		this.khuvuc = db.get("select * from khuvuc order by ten");
		
		if(this.vungId.trim().length() > 0) 
			this.khuvuc = db.get("select * from khuvuc where vung_fk = "+ this.vungId +" order by ten");
		
		String sql = "select * from nhaphanphoi where trangthai='"+this.status+"' order by ten";
		if(this.vungId.trim().length() > 0)
			sql = "select * from nhaphanphoi where khuvuc_fk in (select pk_seq from khuvuc where vung_fk = "+ this.vungId +") and trangthai='"+this.status+"' order by ten";
		if(this.khuvucId.trim().length() > 0)
			sql = "select * from nhaphanphoi where khuvuc_fk = '"+this.khuvucId+"' and trangthai='"+this.status+"' order by ten";
		
		
		this.npp = db.get(sql);
		
		//System.out.println("nhapp: select * from nhaphanphoi where khuvuc_fk = '"+this.khuvucId+"' order by ten");
	}

	@Override
	public String getStatus() {
		// TODO Auto-generated method stub
		return this.status;
	}

	@Override
	public void setStatus(String status) {
		// TODO Auto-generated method stub
		this.status = status;
	}
	
	public ResultSet getCapnhatKHRs() {
		
		return this.capnhatKH;
	}
	
	public void setCapnhatKHRs(ResultSet cnkhRs) {
		
		this.capnhatKH = cnkhRs;
	}
	
	public void createCapnhatKHList()
	{
		String query = "select a.PK_SEQ, nt.TEN as NGUOITAO, ns.TEN as NGUOISUA, a.NGAYSUA, a.NGAYTAO, a.DIENGIAI, a.TRANGTHAI from CAPNHATKHACHHANG a "+
					   "inner join NHANVIEN nt on nt.PK_SEQ = a.NGUOITAO "+
					   "inner join NHANVIEN ns on ns.PK_SEQ = a.NGUOISUA";
		System.out.println("List : "+query);
		this.capnhatKH = db.get(query);
	}
	
	public void initDisplay() {
		String sql = "select npp.TEN as tennpp, ddkd.TEN as tenddkd, a.tbh_fk as tbh, kh.TEN as tenkh, sp.TEN as tensp, a.soluong from CAPNHATKH_CHITIET a "+
				"inner join CAPNHATKHACHHANG cnkh on cnkh.PK_SEQ = a.capnhatkh_fk "+
				"inner join NHAPHANPHOI npp on npp.PK_SEQ = a.npp_fk "+
				"inner join DAIDIENKINHDOANH ddkd on ddkd.PK_SEQ = a.ddkd_fk "+
				"inner join KHACHHANG kh on kh.SMARTID = a.makh "+
				"inner join KHACHHANG_TUYENBH kh_tbh on kh_tbh.KHACHHANG_FK = kh.PK_SEQ "+
				"inner join TUYENBANHANG tbh on tbh.PK_SEQ = kh_tbh.TBH_FK and tbh.DDKD_FK = ddkd.PK_SEQ and tbh.NPP_FK = npp.PK_SEQ "+
				"inner join SANPHAM sp on sp.MA = a.masp "+
				"where cnkh.PK_SEQ = '"+ this.id +"'";
		
		this.cnkhList = this.db.get(sql);
	
	}

	public void setMessage(String msg) {		
		this.message = msg;
	}

	public String getMessage() {
	
		return this.message;
	}

	
	public void setuserId(String userid) {
	
		this.userId = userid;
	}

	
	public String getuserId() {
		
		return this.userId;
	}

	
	public void setId(String id) {
		
		this.id = id;
	}

	
	public String getId() {
		
		return this.id;
	}

	@Override
	public List<IDRouter> getcnkhValue() {		
		
		return this.cnkhValue;
	}

	@Override
	public void setcnkhValue(List<IDRouter> cnkhvalue) {		
		
		this.cnkhValue = cnkhvalue;
	}

	@Override
	public boolean CreateCnkh(List<IDRouter> valuelist) {
		
		dbutils db = new dbutils();
		try
		{
			this.ngaytao = getDateTime();
			this.nguoitao = this.userId;
			db.getConnection().setAutoCommit(false);
			
			String query = "INSERT INTO CAPNHATKHACHHANG(DIENGIAI, TRANGTHAI, NGAYTAO, NGAYSUA, NGUOITAO, NGUOISUA) "+
				       	   "VALUES('"+this.diengiai+"' , '0' , '"+this.ngaytao+"' , '"+this.ngaytao+"' , '"+this.nguoitao+"' , '"+this.nguoitao+"')";
		
		if(!db.update(query)){
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			this.message = "Không thể tạo mới cập nhật khách hàng: " + query;
			return false; 
		}
		
		if(valuelist.size() > 0)
		{
			query = "select IDENT_CURRENT('capnhatkhachhang') as cnkhId";
			
			ResultSet rsV = this.db.get(query);
			rsV.next();
			this.id = rsV.getString("cnkhId");
			rsV.close();
			System.out.println("valuesList size : "+valuelist.size());
			for(int m=0; m < valuelist.size(); m++)
			{
				IDRouter value = valuelist.get(m);
				
				query = "INSERT INTO CAPNHATKH_CHITIET(CAPNHATKH_FK, NPP_FK, DDKD_FK, TBH_FK, MAKH, MASP, SOLUONG) "+
						"VALUES('" + this.id + "', '" + value.getnppId() + "', '" + value.getddkdId() + "', '" + value.gettuyenId() + "', '"+ value.getmaKH() +"', '"+ value.getSanpham() +"', '"+ value.getSoluong() +"')";
				//System.out.println("Query : "+query);
				if(!db.update(query))
				{
					geso.dms.center.util.Utility.rollback_throw_exception(db);
					this.message = "Lỗi khi cập nhật bảng CAPNHATKH_CHITIET , " + query;
					return false; 
				}				
			}			
		}			
		db.getConnection().commit();
		db.getConnection().setAutoCommit(true);
		}
		catch (Exception e) 
		{
			System.out.println("Loi : "+e.toString());
			return false;
		}
		
		return true;
	}

	@Override
	public boolean UpdateCnkh(List<IDRouter> valuelist) {
		dbutils db = new dbutils();
		try{
			this.ngaysua = getDateTime();
			this.nguoisua = this.userId;
			db.getConnection().setAutoCommit(false);
		String query = "update CAPNHATKHACHHANG set diengiai = '"+this.diengiai+"', ngaysua = '"+this.ngaysua+"', nguoisua = '"+this.nguoisua+"' where pk_seq = '"+this.id+"' ";
		System.out.println("1.update : "+query);
		
		if(!db.update(query)){
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			this.message = "Khong the cap nhat : " + query;
			return false; 
		}
		
		String sql = "delete CAPNHATKH_CHITIET where CAPNHATKH_FK = '"+this.id+"'";
		System.out.println("1.delete : "+sql);
		if(!db.update(sql)){
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			this.message = "Khong the xoa : " + sql;
			return false; 
		}
		
		if(valuelist.size() > 0)
		{					
			for(int m=0; m < valuelist.size(); m++)
			{
				IDRouter value = valuelist.get(m);
				
				query = "insert into CAPNHATKH_CHITIET(CAPNHATKH_FK, NPP_FK, DDKD_FK, TBH_FK, MAKH, MASP, SOLUONG) values('" + this.id + "','" + value.getnppId() + "','" + value.getddkdId() + "','" + value.gettuyenId() + "', '"+ value.getmaKH() +"','"+ value.getSanpham() +"',"+ value.getSoluong() +")";
				System.out.println("Query : "+query);
				if(!db.update(query))
				{
					geso.dms.center.util.Utility.rollback_throw_exception(db);
					this.message = "Loi khi cap nhat bang CAPNHATKH_CHITIET, " + query;
					return false; 
				}				
			}			
		}
		
		
		db.getConnection().commit();
		db.getConnection().setAutoCommit(true);
	}catch(Exception e){	
		
		this.message = "Loi : "+e.toString();		
		geso.dms.center.util.Utility.rollback_throw_exception(db);
	return false;						
	}
	return true;
	}

	@Override
	public String getNgaytao() {
		
		return this.ngaytao;
	}

	@Override
	public void setNgaytao(String ngaytao) {
		
		this.ngaytao = ngaytao;
	}

	@Override
	public String getNgaysua() {
		
		return this.ngaysua;
	}

	@Override
	public void setNgaysua(String ngaysua) {
		
		this.ngaysua = ngaysua;
	}

	@Override
	public String getNguoitao() {
		
		return this.nguoitao;
	}

	@Override
	public void setNguoitao(String nguoitao) {
		
		this.nguoitao = nguoitao;
	}

	@Override
	public String getNguoisua() {
		
		return this.nguoisua;
	}

	@Override
	public void setNguoisua(String nguoisua) {
		
		this.nguoisua= nguoisua;
	}

	@Override
	public String getmaKH() {
		
		return this.maKH;
	}

	@Override
	public void setmaKH(String makh) {
		
		this.maKH = makh;
	}

	@Override
	public String getSoluong() {
		
		return this.soluong;
	}

	@Override
	public void setSoluong(String sl) {
		
		this.soluong = sl;
	}
	
	private String getDateTime() 
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	@Override
	public String getDiengiai() {
		
		return this.diengiai;
	}

	@Override
	public void setDiengiai(String dg) {
		
		this.diengiai = dg;
	}

	@Override
	public String getSanpham() {
		
		return this.sanpham;
	}

	@Override
	public void setSanpham(String sp) {
		
		this.sanpham = sp;
	}

	@Override
	public ResultSet getCnkhList() {
		
		return this.cnkhList;
	}

	@Override
	public void setCnkhList(ResultSet cnkhlist) {
		
		this.cnkhList = cnkhlist;
	}


	public String getvungId() 
	{
		return this.vungId;
	}

	public void setvungId(String vungId) 
	{
		this.vungId = vungId;
	}

	public ResultSet getvung() 
	{
		return this.vung;
	}

	public String getView() {
		return view;
	}

	public void setView(String view) {
		this.view = view;
	}

	
}
