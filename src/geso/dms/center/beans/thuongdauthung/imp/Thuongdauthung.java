package geso.dms.center.beans.thuongdauthung.imp;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import geso.dms.center.beans.thuongdauthung.imp.Sanpham;
import geso.dms.center.beans.thuongdauthung.ISanpham;
import geso.dms.center.beans.thuongdauthung.IThuongdauthung;
import geso.dms.distributor.db.sql.dbutils;

public class Thuongdauthung implements IThuongdauthung {

	List<Object> dataSearch = new ArrayList<Object>(); 
	public List<Object> getDataSearch() {
		return dataSearch;
	}
	public void setDataSearch(List<Object> dataSearch) {
		this.dataSearch = dataSearch;
	}
	
	String display = "0";

	String UserID;
	double Id;
	int Thang;
	int Nam;
	String tungay = "";
	String denngay = "";
	String nsp_fk = "null";
	String nsptt_fk = "null";
	String infoDETAILASM = "";
	String infoDETAILSR = "";
	String infoDETAILSS = "";
	ResultSet nspRs ;
	ResultSet nspttRs ;
	String nkh_fk = "null";
	ResultSet nkhRs ;
	double soluong = 0;
	String loaict = "0";



	String NgayTao;
	String NguoiTao;
	String NguoiSua;
	String NgaySua;
	String DienGiai;
	String Message;
	String TrangThai;
	ResultSet luongkhacRs ;
	ResultSet luongkhacChiTietRs ;
	dbutils db;
	double thuongSR = 0;
	double thuongSS = 0;
	double dstoithieu = 0;
	double thuongASM = 0;

	private String view;

	public Thuongdauthung(){

		this.display = "0";
		this.NguoiTao = "";
		this.NguoiSua = "";
		this.NgayTao ="";
		this.NgaySua ="";
		this.Message="";
		this.DienGiai="";
		this.TrangThai = "0";
		this.infoDETAILASM = "";
		this.infoDETAILSR = "";
		this.infoDETAILSS = "";
		this.soluong = 0;
		this.loaict = "0";

		this.thuongSR = 0;
		this.thuongSS = 0;
		this.dstoithieu = 0;
		this.thuongASM = 0;

		this.nsp_fk = "null";
		this.nkh_fk = "null";
		this.nsptt_fk = "null";
		this.tungay = "";
		this.denngay = "";




		db=new dbutils();

		spList= new ArrayList<ISanpham>();
		this.nspRs = db.getScrol("select PK_SEQ, TEN from NHOMSANPHAMCHITIEU where TRANGTHAI = '1'");
		this.nspttRs = db.getScrol("select PK_SEQ, TEN from NHOMSANPHAMCHITIEU where TRANGTHAI = '1'");
		this.nkhRs = db.getScrol("select PK_SEQ,diengiai as TEN from nhomkhachhang where TRANGTHAI = '1'");
		
	}
	



	public String getDisplay() {
		return display;
	}
	public void setDisplay(String display) {
		this.display = display;
	}


	public Thuongdauthung(String id)
	{
		db=new dbutils();
		String  query="";

		query="SELECT   c.trangthai,C.PK_SEQ, C.tungay, C.denngay, C.DIENGIAI,  C.NGAYTAO, C.NGAYSUA,NT.TEN AS NGUOITAO,NS.TEN AS NGUOISUA" +
		"	, c.nsp_fk , loaict, c.nhomkh_fk, c.nsptt_fk" +
		"	,isnull(c.soluong,0) as  soluong" +
		"	,isnull(thuongSR,0) as thuongSR, isnull(dstoithieu, 0) as dstoithieu ,isnull(thuongSS,0) as thuongSS,isnull(thuongASM,0 ) as thuongASM " +
		" FROM thuongdauthung AS C " +
		" INNER JOIN "+
		" dbo.NHANVIEN AS NT ON C.NGUOITAO = NT.PK_SEQ INNER JOIN dbo.NHANVIEN AS NS ON C.NGUOISUA = NS.PK_SEQ where c.trangthai!='2' and c.pk_seq="+ id;

		System.out.println("sql_getdata"+query);

		try
		{

			ResultSet rsLuongKhac=	db.get(query);
			if(rsLuongKhac!=null)
			{
				while(rsLuongKhac.next()){
					this.setID(rsLuongKhac.getDouble("PK_SEQ"));
					this.setTungay(rsLuongKhac.getString("tungay"));
					this.setDenngay(rsLuongKhac.getString("denngay"));
					this.setNgayTao(rsLuongKhac.getString("NGAYTAO"));
					this.setNgaySua(rsLuongKhac.getString("NGAYSUA"));
					this.setDienGiai(rsLuongKhac.getString("DIENGIAI"));
					this.setNguoiTao(rsLuongKhac.getString("NGUOITAO"));
					this.setNguoiSua(rsLuongKhac.getString("NGUOISUA"));
					this.setTrangThai(rsLuongKhac.getString("trangthai"));

					this.soluong = rsLuongKhac.getDouble("soluong");


					this.loaict = rsLuongKhac.getString("loaict");

					this.thuongSR = rsLuongKhac.getDouble("thuongSR");
					this.thuongSS= rsLuongKhac.getDouble("thuongSS");
					this.thuongASM = rsLuongKhac.getDouble("thuongASM");
					
					
					this.dstoithieu = rsLuongKhac.getDouble("dstoithieu");
					this.nkh_fk = rsLuongKhac.getString("nhomkh_fk");
					this.nsp_fk = rsLuongKhac.getString("nsp_fk");
					this.nsptt_fk = rsLuongKhac.getString("nsptt_fk");
				}
			}
			rsLuongKhac.close();	

			query="select nsp.sanpham_fk,sp.MA as spMa,sp.ten as spTen ,nsp.thuong_fk " +
			" from ThuongDauThung_Sp nsp inner join SANPHAM sp on sp.PK_SEQ=nsp.sanpham_fk where nsp.thuong_fk='"+id+"'";
			ResultSet	rs =db.get(query);
			List<ISanpham> spList= new ArrayList<ISanpham>();
			while(rs.next())
			{
				ISanpham sp= new Sanpham();
				sp.setId(rs.getString("sanpham_fk"));
				sp.setMa(rs.getString("spMa"));
				sp.setTen(rs.getString("spTen"));
				spList.add(sp);
			}
			this.setSpList(spList);

			
			
			String tiendoCHITIETASM = "";
			//INIT thưởng ASM loại = 1
			query = "select thuongdt_fk, sokh, thuong from thuongdauthung_mucthuong where thuongdt_fk = '" + id + "' and loai = 1 ";
			ResultSet rsDETAIL = db.get(query);
			if(rsDETAIL != null)
			{
				while(rsDETAIL.next())
				{
					tiendoCHITIETASM += rsDETAIL.getString("sokh") + "_" + rsDETAIL.getString("thuong") + "__" ;
				}
				rsDETAIL.close();
			}
			
			if(tiendoCHITIETASM.trim().length() > 0)
			{
				tiendoCHITIETASM = tiendoCHITIETASM.substring(0, tiendoCHITIETASM.length() - 2);
				this.setinfoDETAILASM(tiendoCHITIETASM);
			}
			
			String tiendoCHITIETSR = "";
			//INIT thưởng SR loại = 2
			query = "select thuongdt_fk, sokh, thuong from thuongdauthung_mucthuong where thuongdt_fk = '" + id + "' and loai = 2 ";
			System.out.println("__+_________- : "+query);
			rsDETAIL = db.get(query);
			if(rsDETAIL != null)
			{
				while(rsDETAIL.next())
				{
					tiendoCHITIETSR += rsDETAIL.getString("sokh") + "_" + rsDETAIL.getString("thuong") + "__" ;
				}
				rsDETAIL.close();
			}
			
			if(tiendoCHITIETSR.trim().length() > 0)
			{
				tiendoCHITIETSR = tiendoCHITIETSR.substring(0, tiendoCHITIETSR.length() - 2);
				this.setinfoDETAILSR(tiendoCHITIETSR);
			}
			
			
			String tiendoCHITIETSS = "";
			//INIT thưởng SS loại = 3
			query = "select thuongdt_fk, sokh, thuong from thuongdauthung_mucthuong where thuongdt_fk = '" + id + "' and loai = 3 ";
			rsDETAIL = db.get(query);
			if(rsDETAIL != null)
			{
				while(rsDETAIL.next())
				{
					tiendoCHITIETSS += rsDETAIL.getString("sokh") + "_" + rsDETAIL.getString("thuong") + "__" ;
				}
				rsDETAIL.close();
			}
			
			if(tiendoCHITIETSS.trim().length() > 0)
			{
				tiendoCHITIETSS = tiendoCHITIETSS.substring(0, tiendoCHITIETSS.length() - 2);
				this.setinfoDETAILSS(tiendoCHITIETSS);
			}
			
			
			

			this.nspRs = db.getScrol("select PK_SEQ, TEN from NHOMSANPHAMCHITIEU where TRANGTHAI = '1' and TYPE = '4'");
			this.nspttRs = db.getScrol("select PK_SEQ, TEN from NHOMSANPHAMCHITIEU where TRANGTHAI = '1' and TYPE = '4'");
			this.nkhRs = db.getScrol("select PK_SEQ, diengiai as TEN from NHOMKHACHHANG where TRANGTHAI = '1' ");
		}catch(Exception er)
		{
			er.printStackTrace();
			this.Message="Error rsLuongKhac.java - line : 88- detail error :"+ er.toString();
			System.out.println("Error Class name : rsLuongKhac.JAVA- LINE 216 :STRING SQL" + er.toString());
		}
		this.Message="";

		

	}



	public void setLuongkhacRs(String luongkhacRs) {
		if(luongkhacRs.equals(""))
		{
			luongkhacRs = "\n SELECT c.trangthai,C.PK_SEQ, C.tungay, C.denngay, C.DIENGIAI,  C.NGAYTAO, " +
			"\n C.NGAYSUA,NT.TEN AS NGUOITAO, NS.TEN AS NGUOISUA " +
			"\n FROM thuongdauthung AS C INNER JOIN " +
			"\n dbo.NHANVIEN AS NT ON C.NGUOITAO = NT.PK_SEQ INNER JOIN dbo.NHANVIEN AS NS ON C.NGUOISUA = NS.PK_SEQ " +
			"\n where 1=1 ";
			luongkhacRs += "\n order by C.PK_SEQ desc";
			this.luongkhacRs = db.get(luongkhacRs);
		}else{
			this.luongkhacRs = db.getByPreparedStatement(luongkhacRs, this.dataSearch);
		}
		
		System.out.println("luongkhacRs: "+luongkhacRs);
	}
	
	public ResultSet getLuongkhacRs() {
		return luongkhacRs;
	}


	@Override
	public void setID(double id) {

		this.Id=id;	
	}

	@Override
	public double getID() {

		return Id;
	}

	@Override
	public void setThang(int thang) {

		this.Thang=thang;
	}

	@Override
	public int getThang() {

		return this.Thang;
	}

	@Override
	public void setNam(int nam) {
		this.Nam=nam;
	}

	@Override
	public int getNam() {

		return this.Nam;
	}



	@Override
	public void setNgayTao(String ngaytao) {

		this.NgayTao =ngaytao;
	}

	@Override
	public String getNgayTao() {

		return this.NgayTao;
	}

	@Override
	public void setNgaySua(String ngaysua) {

		this.NgaySua=ngaysua;
	}

	@Override
	public String getNgaySua() {

		return this.NgaySua;
	}

	@Override
	public void setDienGiai(String diengiai) {

		this.DienGiai=diengiai;
	}

	@Override
	public String getDienGiai() {

		return this.DienGiai;
	}
	public boolean KiemTraHopLe(){

		if(this.NguoiTao.toString().equals("null") || this.NguoiTao.equals("")){
			this.Message="Ten Nguoi Dung Khong Hop Le,Vui Long Dang Xuat De Thu Lai!";
			return false;
		}
		/*	if(this.nsp_fk == null || this.nsp_fk.equals(""))
		{

			this.Message="Loi  chua chọn mã nhóm sản phẩm";			
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return false;
		}*/
	

		if(this.tungay.equals("")|| this.denngay.equals(""))
		{

			this.Message="Chưa chọn thời gian";			
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return false;
		}

		return true;
	}





	public void setUserId(String userid) {

		this.UserID=userid;
	}


	public String getUserId() {

		return this.UserID;
	}





	@Override
	public void setNguoiTao(String nguoitao) {

		this.NguoiTao=nguoitao;		
	}

	@Override
	public String getNguoiTao() {

		return this.NguoiTao;
	}

	@Override
	public void setNguoiSua(String nguoisua) {

		this.NguoiSua=nguoisua;
	}

	@Override
	public String getNguoiSua() {

		return this.NguoiSua;
	}

	@Override
	public void setMessage(String strmessage) {

		this.Message=strmessage;
	}

	@Override
	public String getMessage() {

		return this.Message;
	}


	@Override
	public void setTrangThai(String trangthai) {

		this.TrangThai=trangthai;
	}
	@Override
	public String getTrangThai() {

		return this.TrangThai;
	}

	public boolean CreateLuongKhac() 
	{
		List<Object> data = new ArrayList<Object>();
		
		try
		{

			if(!KiemTraHopLe()){
				return false;
			}
			db.getConnection().setAutoCommit(false);

			String sql = "";
			sql=
				"insert into thuongdauthung(diengiai,tungay,denngay,NGAYTAO,NGUOITAO,NGAYSUA,NGUOISUA,TRANGTHAI,loaict" +
				" ,soluong,thuongASM,nsp_fk, nhomkh_fk,ThuongSR,ThuongSS, dstoithieu) " +
				"values (?,?,?,?,?,?,?,'0',?,?,?,?,?,?,?,?)";
			
			if(this.nkh_fk.trim().equals("null"))
				this.nkh_fk = null;
			if(this.nsp_fk.trim().equals("null"))
				this.nsp_fk = null;
			
			data.clear();
			data.add(this.DienGiai);data.add(this.tungay);data.add(this.denngay);data.add(this.NgayTao);data.add(this.NguoiTao);
			data.add(this.NgaySua);data.add(this.NguoiSua);data.add(this.loaict);data.add(Double.toString(this.soluong));data.add(Double.toString(this.thuongASM));
			data.add(this.nsp_fk);data.add(this.nkh_fk);data.add(Double.toString(this.thuongSR));data.add(Double.toString(this.thuongSS));data.add(Double.toString(this.dstoithieu));
			
			db.viewQuery(sql, data);
			System.out.println("save  thuongdauthung " +sql);
			if(this.db.updateQueryByPreparedStatement(sql, data) != 1)
			{
				this.Message="Loi :"+sql;		
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				return false;
			}		

			sql = "select scope_identity() as Id";
			ResultSet			 rs = db.get(sql);	
			try
			{
				rs.next();
				this.Id=rs.getDouble("Id");
				rs.close();
			}catch(Exception ex)
			{
				ex.printStackTrace();
			}


			if(this.spList != null)
			{
				int size = spList.size();
				int m = 0;
				while(m < size)
				{
					ISanpham sp = spList.get(m);
					sql = "insert into ThuongDauThung_Sp(sanpham_fk, Thuong_fk) " +
					" select ?, ? where ? not in (select sanpham_fk from ThuongDauThung_Sp where thuong_fk=?)";
					
					data.clear();
					data.add(sp.getId());data.add(Double.toString(this.Id));data.add(sp.getId());data.add(Double.toString(this.Id));
					
					m++ ;
					if(this.db.updateQueryByPreparedStatement(sql, data) != 1)
					{				
						this.db.getConnection().rollback();
						this.Message="Vui lòng liên hệ Admin để sửa lỗi "+sql;
						return false;
					}
				}
			}

			
			
			if(this.infoDETAILASM.trim().length() > 0)
			{ 
				String[] info = this.infoDETAILASM.split("__");
				for(int k = 0; k < info.length; k++ )
				{
					String query = "insert thuongdauthung_mucthuong( thuongdt_fk, muc, sokh, thuong, loai ) "
							+ " values ( ?,?, ? , ?  , 1 )";
					
					data.clear();
					data.add(Double.toString(this.Id));data.add(Integer.toString(k));data.add(info[k].split("_")[0].replaceAll(",", ""));data.add(info[k].split("_")[1].replaceAll(",", ""));
					
					if(this.db.updateQueryByPreparedStatement(query, data) != 1)
					{
						db.getConnection().rollback();
						this.Message = "Vui lòng liên hệ Admin để sửa lỗi " + query;
						return false; 
					}
				}
			}
			
			
			if(this.infoDETAILSR.trim().length() > 0)
			{ 
				String[] info = this.infoDETAILSR.split("__");
				for(int k = 0; k < info.length; k++ )
				{
					String query = "insert thuongdauthung_mucthuong( thuongdt_fk, muc,sokh, thuong, loai ) "
							+ " values ( ?,?, ? , ?  , 2)";
					
					data.clear();
					data.add(Double.toString(this.Id));data.add(Integer.toString(k));data.add(info[k].split("_")[0].replaceAll(",", ""));data.add(info[k].split("_")[1].replaceAll(",", ""));
					
					if(this.db.updateQueryByPreparedStatement(query, data) != 1)
					{
						db.getConnection().rollback();
						this.Message = "Vui lòng liên hệ Admin để sửa lỗi " + query;
						return false; 
					}
				}
			}
			
			if(this.infoDETAILSS.trim().length() > 0)
			{ 
				String[] info = this.infoDETAILSS.split("__");
				for(int k = 0; k < info.length; k++ )
				{
					String query = "insert thuongdauthung_mucthuong( thuongdt_fk, muc, sokh, thuong, loai ) "
							+ " values ( ?,?, ? , ?  , 3)";
					
					data.clear();
					data.add(Double.toString(this.Id));data.add(Integer.toString(k));data.add(info[k].split("_")[0].replaceAll(",", ""));data.add(info[k].split("_")[1].replaceAll(",", ""));
					
					if(this.db.updateQueryByPreparedStatement(query, data) != 1)
					{
						db.getConnection().rollback();
						this.Message = "Vui lòng liên hệ Admin để sửa lỗi " + query;
						return false; 
					}
				}
			}
			
			
			
			
			/*sql="delete thuongdauthung_SR where thuongdauthung_fk='"+this.Id+"'";
			if(!this.db.update(sql))
			{				
				this.db.getConnection().rollback();
				this.Message="Vui lòng liên hệ Admin để sửa lỗi "+sql;
				return false;
			}


			sql=
				 "\n insert thuongdauthung_SR(Thuongdauthung_Fk,DDKD_FK,GSBH_FK,DoanhSo,SoLuong,SoLuongThung,asm_fk) -- " +
				 "\n SELECT '"+this.Id+"' as Id,ddkd.pk_seq as ddkd_fk  -- " +
				 "\n 		,isnull(thucdat.gsbh_fk,gsbh.PK_SEQ) gsbh_fk  -- " +
				 "\n 		,isnull(thucdat.ASM_FK,ASM.PK_SEQ) asm_fk -- " +
				 "\n 		,isnull( thucdat.soluong,0 ) as soluong, -- " +
				 "\n 		isnull( thucdat.sothung,0 )as sothung , isnull(doanhso,0) as doanhso -- " +
				 "\n  FROM   -- " +
				 "\n  daidienkinhdoanh ddkd  -- " +
				 "\n  inner join ddkd_gsbh  on ddkd_gsbh.ddkd_fk = ddkd.pk_seq   -- " +
				 "\n  inner join GIAMSATBANHANG gsbh on gsbh.PK_SEQ = ddkd_gsbh.GSBH_FK -- " +
				 "\n  inner join GSBH_KHUVUC gsbhkv on gsbhkv.GSBH_FK = gsbh.PK_SEQ -- " +
				 "\n  inner join ASM_KHUVUC asmkv on asmkv.KHUVUC_FK = gsbhkv.KHUVUC_FK -- " +
				 "\n  inner join ASM on asm.PK_SEQ = asmkv.ASM_FK and gsbh.KBH_FK = asm.KBH_FK -- " +
				 "\n  left join   -- " +
				 "\n  (   -- " +
				 "\n  	SELECT ASM_FK,GSBH_FK ,ddkd_fk,  sum(soluong) as soluong,sum(sothung) as sothung , sum(soluong * giamua) as  doanhso     -- " +
				 "\n  	FROM        -- " +
				 "\n  	(        -- " +
				 "\n  		select asmkv.ASM_FK,dh.GSBH_FK,dh.ddkd_fk , (-1)*isnull(dh_sp.soluong, dh_sp1.soluong) as soluong     ,    -- " +
				 "\n  			case when sp.dvdl_fk= 100018 then   (-1)*isnull(dh_sp.soluong, dh_sp1.soluong) else    -- " +
				 "\n  			(case when  qc.soluong1 >0  then (-1)*isnull(dh_sp.soluong, dh_sp1.soluong) * qc.soluong2/ qc.soluong1 else 0 end  )   end as sothung   -- " +
				 "\n 			,isnull(dh_sp.giamua, dh_sp1.giamua) as giamua   -- " +
				 "\n  		from  donhangtrave dh         -- " +
				 "\n  			left outer join  donhangtrave_sanpham dh_sp on dh_sp.donhangtrave_fk = dh.pk_seq          -- " +
				 "\n  			left outer join  donhang_sanpham dh_sp1 on dh.donhang_fk = dh_sp1.donhang_fk          -- " +
				 "\n  			inner join sanpham sp on sp.pk_seq=isnull(dh_sp.sanpham_fk, dh_sp1.sanpham_fk)   	 			 -- " +
				 "\n  			left join quycach qc on qc.dvdl1_fk=sp.dvdl_fk and qc.sanpham_fk=sp.pk_seq and qc.dvdl2_fk=100018   -- " +
				 "\n  			inner join GSBH_KHUVUC gsbhkv on dh.GSBH_FK = gsbhkv.GSBH_FK -- " +
				 "\n  			inner join ASM_KHUVUC asmkv on asmkv.KHUVUC_FK = gsbhkv.KHUVUC_FK -- " +
				 "\n  			inner join ASM on ASM.PK_SEQ = asmkv.ASM_FK and asm.KBH_FK = dh.KBH_FK	 			 -- " +
				 "\n  		where dh.trangthai = '3' and ngaynhap>='"+this.tungay+"' and ngaynhap <='"+this.denngay+"'   -- " +
				 "\n  		and isnull(dh_sp.sanpham_fk,dh_sp1.sanpham_fk)   in ( select sanpham_fk from thuongdauthung_sp where thuong_fk = '"+this.Id+"' )     -- " +
				 "\n  		union all         -- " +
				 "\n  		select asmkv.ASM_FK,dh.GSBH_FK,dh.ddkd_fk, dh_sp.soluong , case when sp.dvdl_fk= 100018 then   dh_sp.soluong else  -- " +
				 "\n  		(case when  qc.soluong1 >0  then dh_sp.soluong * qc.soluong2/ qc.soluong1 else 0 end  )   end  as sothung   -- " +
				 "\n 		,isnull(dh_sp.giamua, 0) as giamua     -- " +
				 "\n  		from donhang dh  inner join donhang_sanpham  dh_sp on dh.pk_seq = dh_sp.donhang_fk        -- " +
				 "\n  			inner join sanpham sp on sp.pk_seq=dh_sp.sanpham_fk     -- " +
				 "\n  			left join quycach qc on qc.dvdl1_fk=sp.dvdl_fk and qc.sanpham_fk=sp.pk_seq and qc.dvdl2_fk=100018  -- " +
				 "\n  			inner join GSBH_KHUVUC gsbhkv on dh.GSBH_FK = gsbhkv.GSBH_FK -- " +
				 "\n  			inner join ASM_KHUVUC asmkv on asmkv.KHUVUC_FK = gsbhkv.KHUVUC_FK -- " +
				 "\n  			inner join ASM on ASM.PK_SEQ = asmkv.ASM_FK and asm.KBH_FK = dh.KBH_FK -- " +
				 "\n  		where dh.trangthai = '1' and ngaynhap>='"+this.tungay+"' and ngaynhap <='"+this.denngay+"'   -- " +
				 "\n  		and dh_sp.sanpham_fk  in( select sanpham_fk from thuongdauthung_sp where thuong_fk = '"+this.Id+"' )        -- " +
				 "\n  	) dh          -- " +
				 "\n  	group by ASM_FK,GSBH_FK , ddkd_fk    -- " +
				 "\n  ) thucdat on thucdat.ddkd_fk  = ddkd.pk_seq    -- " +
				 "\n  where ddkd.trangthai = 1  -- " ;
			System.out.println("SqlInsert = " + sql);
			if(!this.db.update(sql))
			{				
				this.db.getConnection().rollback();
				this.Message="Vui lòng liên hệ Admin để sửa lỗi "+sql;
				return false;
			}*/

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}catch(Exception er){	
			er.printStackTrace();
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			this.setMessage("Ban khong the them chi tieu moi nay, loi : " + er.toString());
			return false;			
		}
		return true;
	}

	public boolean UpdateLuongKhac()
	{
		List<Object> data = new ArrayList<Object>();
		try
		{
			if(!KiemTraHopLe()){
				return false;
			}
			db.getConnection().setAutoCommit(false);

			String sql = "";

			sql="update thuongdauthung set diengiai =?,tungay =?,denngay =?,NGAYSUA =?,NGUOISUA=? ,"
					+ "loaict =?,soluong =?,nsp_fk =?,nhomkh_fk =?,thuongASM=?,thuongSR =?,thuongSS =?,"
					+ "dstoithieu =? WHERE PK_SEQ=?" ;
			
			if(this.nkh_fk.trim().equals("null"))
				this.nkh_fk = null;
			if(this.nsp_fk.trim().equals("null"))
				this.nsp_fk = null;
			
			data.clear();
			data.add(this.DienGiai);data.add(this.tungay);data.add(this.denngay);data.add(this.NgaySua);
			data.add(this.NguoiSua);data.add(this.loaict);data.add(Double.toString(this.soluong));data.add(this.nsp_fk);
			data.add(this.nkh_fk);data.add(Double.toString(this.thuongASM));data.add(Double.toString(this.thuongSR));data.add(Double.toString(this.thuongSS));
			data.add(Double.toString(this.dstoithieu));data.add(Double.toString(this.Id));
			
			db.viewQuery(sql, data);
			System.out.println("save  thuongdauthung " +sql);
			if(this.db.updateQueryByPreparedStatement(sql, data) != 1){

				this.Message="Loi :"+sql;		
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				return false;
			}		

			sql=" delete from ThuongDauThung_Sp where Thuong_fk="+this.Id+" ";
			System.out.println("__+__ : "+sql);
			if(this.spList != null)
			{
				int size = spList.size();
				int m = 0;
				while(m < size)
				{
					ISanpham sp = spList.get(m);
					sql = "insert into ThuongDauThung_Sp(sanpham_fk, Thuong_fk) " +
					" select ?, ? where ? not in (select sanpham_fk from ThuongDauThung_Sp where thuong_fk=?)";
					
					data.clear();
					data.add(sp.getId());data.add(this.Id);data.add(sp.getId());data.add(this.Id);
					
					m++ ;
					db.viewQuery(sql, data);
					if(this.db.updateQueryByPreparedStatement(sql, data) != 1)
					{				
						this.db.getConnection().rollback();
						this.Message="Vui lòng liên hệ Admin để sửa lỗi "+sql;
						return false;
					}
				}
			}

			
			sql="delete thuongdauthung_mucthuong where thuongdt_fk="+this.Id+" ";
			if(!this.db.update(sql))
			{				
				this.db.getConnection().rollback();
				this.Message="Vui lòng liên hệ Admin để sửa lỗi "+sql;
				return false;
			}
			System.out.println("__+___+___vày đây length"+this.infoDETAILASM.trim().length());
			if(this.infoDETAILASM.trim().length() > 0)
			{ 
				
				String[] info = this.infoDETAILASM.split("__");
				System.out.println("______+______length k : "+info.length);
				for(int k = 0; k < info.length; k++ )
				{
					String query = "insert thuongdauthung_mucthuong( thuongdt_fk, muc, sokh, thuong, loai ) "
							+ " values ( ?,?, ? , ?  , 1 )";

					data.clear();
					data.add(Double.toString(this.Id));data.add(Integer.toString(k));data.add(info[k].split("_")[0].replaceAll(",", ""));data.add(info[k].split("_")[1].replaceAll(",", ""));
					
					db.viewQuery(query, data);
					if(this.db.updateQueryByPreparedStatement(query, data) != 1)
					{
						db.getConnection().rollback();
						this.Message = "Vui lòng liên hệ Admin để sửa lỗi " + query;
						return false; 
					}
				}
			}
			
			
			if(this.infoDETAILSR.trim().length() > 0)
			{ 
				String[] info = this.infoDETAILSR.split("__");
				for(int k = 0; k < info.length; k++ )
				{
					String query = "insert thuongdauthung_mucthuong( thuongdt_fk, muc, sokh, thuong, loai ) "
							+ " values ( ?,?, ? , ?  , 2)";
					
					data.clear();
					data.add(Double.toString(this.Id));data.add(Integer.toString(k));data.add(info[k].split("_")[0].replaceAll(",", ""));data.add(info[k].split("_")[1].replaceAll(",", ""));
					
					db.viewQuery(query, data);
					if(this.db.updateQueryByPreparedStatement(query, data) != 1)
					{
						db.getConnection().rollback();
						this.Message = "Vui lòng liên hệ Admin để sửa lỗi " + query;
						return false; 
					}
				}
			}
			
			if(this.infoDETAILSS.trim().length() > 0)
			{ 
				String[] info = this.infoDETAILSS.split("__");
				for(int k = 0; k < info.length; k++ )
				{
					String query = "insert thuongdauthung_mucthuong( thuongdt_fk, muc, sokh, thuong, loai ) "
							+ " values ( ?,?, ? , ?  , 3)";
					
					data.clear();
					data.add(Double.toString(this.Id));data.add(Integer.toString(k));data.add(info[k].split("_")[0].replaceAll(",", ""));data.add(info[k].split("_")[1].replaceAll(",", ""));
					
					db.viewQuery(query, data);
					if(this.db.updateQueryByPreparedStatement(query, data) != 1)
					{
						db.getConnection().rollback();
						this.Message = "Vui lòng liên hệ Admin để sửa lỗi " + query;
						return false; 
					}
				}
			}
			
			
			/*sql="delete thuongdauthung_SR where thuongdauthung_fk='"+this.Id+"'";
			if(!this.db.update(sql))
			{				
				this.db.getConnection().rollback();
				this.Message="Vui lòng liên hệ Admin để sửa lỗi "+sql;
				return false;
			}


			sql=
				 "\n insert thuongdauthung_SR(Thuongdauthung_Fk,DDKD_FK,GSBH_FK,DoanhSo,SoLuong,SoLuongThung,asm_fk) -- " +
				 "\n SELECT '"+this.Id+"' as Id,ddkd.pk_seq as ddkd_fk  -- " +
				 "\n 		,isnull(thucdat.gsbh_fk,gsbh.PK_SEQ) gsbh_fk  -- " +
				 "\n 		,isnull(thucdat.ASM_FK,ASM.PK_SEQ) asm_fk -- " +
				 "\n 		,isnull( thucdat.soluong,0 ) as soluong, -- " +
				 "\n 		isnull( thucdat.sothung,0 )as sothung , isnull(doanhso,0) as doanhso -- " +
				 "\n  FROM   -- " +
				 "\n  daidienkinhdoanh ddkd  -- " +
				 "\n  inner join ddkd_gsbh  on ddkd_gsbh.ddkd_fk = ddkd.pk_seq   -- " +
				 "\n  inner join GIAMSATBANHANG gsbh on gsbh.PK_SEQ = ddkd_gsbh.GSBH_FK -- " +
				 "\n  inner join GSBH_KHUVUC gsbhkv on gsbhkv.GSBH_FK = gsbh.PK_SEQ -- " +
				 "\n  inner join ASM_KHUVUC asmkv on asmkv.KHUVUC_FK = gsbhkv.KHUVUC_FK -- " +
				 "\n  inner join ASM on asm.PK_SEQ = asmkv.ASM_FK and gsbh.KBH_FK = asm.KBH_FK -- " +
				 "\n  left join   -- " +
				 "\n  (   -- " +
				 "\n  	SELECT ASM_FK,GSBH_FK ,ddkd_fk,  sum(soluong) as soluong,sum(sothung) as sothung , sum(soluong * giamua) as  doanhso     -- " +
				 "\n  	FROM        -- " +
				 "\n  	(        -- " +
				 "\n  		select asmkv.ASM_FK,dh.GSBH_FK,dh.ddkd_fk , (-1)*isnull(dh_sp.soluong, dh_sp1.soluong) as soluong     ,    -- " +
				 "\n  			case when sp.dvdl_fk= 100018 then   (-1)*isnull(dh_sp.soluong, dh_sp1.soluong) else    -- " +
				 "\n  			(case when  qc.soluong1 >0  then (-1)*isnull(dh_sp.soluong, dh_sp1.soluong) * qc.soluong2/ qc.soluong1 else 0 end  )   end as sothung   -- " +
				 "\n 			,isnull(dh_sp.giamua, dh_sp1.giamua) as giamua   -- " +
				 "\n  		from  donhangtrave dh         -- " +
				 "\n  			left outer join  donhangtrave_sanpham dh_sp on dh_sp.donhangtrave_fk = dh.pk_seq          -- " +
				 "\n  			left outer join  donhang_sanpham dh_sp1 on dh.donhang_fk = dh_sp1.donhang_fk          -- " +
				 "\n  			inner join sanpham sp on sp.pk_seq=isnull(dh_sp.sanpham_fk, dh_sp1.sanpham_fk)   	 			 -- " +
				 "\n  			left join quycach qc on qc.dvdl1_fk=sp.dvdl_fk and qc.sanpham_fk=sp.pk_seq and qc.dvdl2_fk=100018   -- " +
				 "\n  			inner join GSBH_KHUVUC gsbhkv on dh.GSBH_FK = gsbhkv.GSBH_FK -- " +
				 "\n  			inner join ASM_KHUVUC asmkv on asmkv.KHUVUC_FK = gsbhkv.KHUVUC_FK -- " +
				 "\n  			inner join ASM on ASM.PK_SEQ = asmkv.ASM_FK and asm.KBH_FK = dh.KBH_FK	 			 -- " +
				 "\n  		where dh.trangthai = '3' and ngaynhap>='"+this.tungay+"' and ngaynhap <='"+this.denngay+"'   -- " +
				 "\n  		and isnull(dh_sp.sanpham_fk,dh_sp1.sanpham_fk)   in ( select sanpham_fk from thuongdauthung_sp where thuong_fk = '"+this.Id+"' )     -- " +
				 "\n  		union all         -- " +
				 "\n  		select asmkv.ASM_FK,dh.GSBH_FK,dh.ddkd_fk, dh_sp.soluong , case when sp.dvdl_fk= 100018 then   dh_sp.soluong else  -- " +
				 "\n  		(case when  qc.soluong1 >0  then dh_sp.soluong * qc.soluong2/ qc.soluong1 else 0 end  )   end  as sothung   -- " +
				 "\n 		,isnull(dh_sp.giamua, 0) as giamua     -- " +
				 "\n  		from donhang dh  inner join donhang_sanpham  dh_sp on dh.pk_seq = dh_sp.donhang_fk        -- " +
				 "\n  			inner join sanpham sp on sp.pk_seq=dh_sp.sanpham_fk     -- " +
				 "\n  			left join quycach qc on qc.dvdl1_fk=sp.dvdl_fk and qc.sanpham_fk=sp.pk_seq and qc.dvdl2_fk=100018  -- " +
				 "\n  			inner join GSBH_KHUVUC gsbhkv on dh.GSBH_FK = gsbhkv.GSBH_FK -- " +
				 "\n  			inner join ASM_KHUVUC asmkv on asmkv.KHUVUC_FK = gsbhkv.KHUVUC_FK -- " +
				 "\n  			inner join ASM on ASM.PK_SEQ = asmkv.ASM_FK and asm.KBH_FK = dh.KBH_FK -- " +
				 "\n  		where dh.trangthai = '1' and ngaynhap>='"+this.tungay+"' and ngaynhap <='"+this.denngay+"'   -- " +
				 "\n  		and dh_sp.sanpham_fk  in( select sanpham_fk from thuongdauthung_sp where thuong_fk = '"+this.Id+"' )        -- " +
				 "\n  	) dh          -- " +
				 "\n  	group by ASM_FK,GSBH_FK , ddkd_fk    -- " +
				 "\n  ) thucdat on thucdat.ddkd_fk  = ddkd.pk_seq    -- " +
				 "\n  where ddkd.trangthai = 1  -- " ;
			System.out.println("tinh thuong=" + sql);
			if(!this.db.update(sql))
			{				
				this.db.getConnection().rollback();
				this.Message="Vui lòng liên hệ Admin để sửa lỗi "+sql;
				return false;
			}*/
			


			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}catch(Exception er){	
			er.printStackTrace();
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			this.setMessage("Ban khong the them chi tieu moi nay, loi : " + er.toString());
			return false;			
		}
		return true;
	}
	public boolean tinhthuongdauthung(dbutils db)
	{
		return true;

	}

	public boolean chotLuongKhac()
	{
		List<Object> data = new ArrayList<Object>();
		try		
		{
			db.getConnection().setAutoCommit(false);

			String sql = "update thuongdauthung set trangthai =1 where  pk_seq = ? and trangthai = 0 ";
			
			data.clear();
			data.add(Integer.toString((int)this.Id));
			
			db.viewQuery(sql, data);
			if(this.db.updateQueryByPreparedStatement(sql, data) != 1)
			{
				this.setMessage("Khong the chot thuong :" + sql);
				this.db.getConnection().rollback();
				return false;
			}
			ResultSet rs = db.get(" select tungay,denngay,loaict from thuongdauthung where pk_Seq= " +this.Id );
			if(rs.next())
			{
				this.tungay = rs.getString("tungay");
				this.denngay = rs.getString("denngay");
				this.loaict = rs.getString("loaict");
			}
			
			
			sql="delete thuongdauthung_SR where thuongdauthung_fk='"+this.Id+"'";
			if(!this.db.update(sql))
			{				
				this.db.getConnection().rollback();
				this.Message="Vui lòng liên hệ Admin để sửa lỗi "+sql;
				return false;
			}
			sql=
				 "\n insert thuongdauthung_SR(Thuongdauthung_Fk,DDKD_FK,GSBH_FK,asm_fk,SoLuong,SoLuongThung,DoanhSo,sokh) -- " +
				 "\n SELECT '"+this.Id+"' as Id,ddkd.pk_seq as ddkd_fk  -- " +
				 "\n 		,isnull(thucdat.gsbh_fk,gsbh.PK_SEQ) gsbh_fk  -- " +
				 "\n 		,isnull(thucdat.ASM_FK,ASM.PK_SEQ) asm_fk -- " +
				 "\n 		,isnull( thucdat.soluong,0 ) as soluong, -- " +
				 "\n 		isnull( thucdat.sothung,0 )as sothung , isnull(doanhso,0) as doanhso,ISNULL(thucdat.sokh,0) as sokh  -- " +
				 "\n  FROM   -- " +
				 "\n  daidienkinhdoanh ddkd  -- " +
				 "\n  inner join ddkd_gsbh  on ddkd_gsbh.ddkd_fk = ddkd.pk_seq   -- " +
				 "\n  inner join GIAMSATBANHANG gsbh on gsbh.PK_SEQ = ddkd_gsbh.GSBH_FK -- " +
				 "\n  inner join GSBH_KHUVUC gsbhkv on gsbhkv.GSBH_FK = gsbh.PK_SEQ -- " +
				 "\n  inner join ASM_KHUVUC asmkv on asmkv.KHUVUC_FK = gsbhkv.KHUVUC_FK -- " +
				 "\n  inner join ASM on asm.PK_SEQ = asmkv.ASM_FK and gsbh.KBH_FK = asm.KBH_FK -- " +
				 "\n  left join   -- " +
				 "\n  (   -- " +
				 "\n  	SELECT ASM_FK,GSBH_FK ,ddkd_fk ,COUNT( distinct kh_fk) as sokh,  sum(soluong) as soluong,sum(sothung) as sothung , sum(soluong * giamua) as  doanhso     -- " +
				 "\n  	FROM        -- " +
				 "\n  	(        -- " +
				 "\n  		select asmkv.ASM_FK,dh.GSBH_FK,dh.ddkd_fk,null as kh_fk , (-1)*isnull(dh_sp.soluong, dh_sp1.soluong) as soluong     ,    -- " +
				 "\n  			case when sp.dvdl_fk= 100018 then   (-1)*isnull(dh_sp.soluong, dh_sp1.soluong) else    -- " +
				 "\n  			(case when  qc.soluong1 >0  then (-1)*isnull(dh_sp.soluong, dh_sp1.soluong) * qc.soluong2/ qc.soluong1 else 0 end  )   end as sothung   -- " +
				 "\n 			,isnull(dh_sp.giamua, dh_sp1.giamua) as giamua   -- " +
				 "\n  		from  donhangtrave dh         -- " +
				 "\n  			left outer join  donhangtrave_sanpham dh_sp on dh_sp.donhangtrave_fk = dh.pk_seq          -- " +
				 "\n  			left outer join  donhang_sanpham dh_sp1 on dh.donhang_fk = dh_sp1.donhang_fk          -- " +
				 "\n  			inner join sanpham sp on sp.pk_seq=isnull(dh_sp.sanpham_fk, dh_sp1.sanpham_fk)   	 			 -- " +
				 "\n  			left join quycach qc on qc.dvdl1_fk=sp.dvdl_fk and qc.sanpham_fk=sp.pk_seq and qc.dvdl2_fk=100018   -- " +
				 "\n  			inner join GSBH_KHUVUC gsbhkv on dh.GSBH_FK = gsbhkv.GSBH_FK -- " +
				 "\n  			inner join ASM_KHUVUC asmkv on asmkv.KHUVUC_FK = gsbhkv.KHUVUC_FK -- " +
				 "\n  			inner join ASM on ASM.PK_SEQ = asmkv.ASM_FK and asm.KBH_FK = dh.KBH_FK	 			 -- " +
				 "\n  		where dh.trangthai = '3' and ngaynhap>='"+this.tungay+"' and ngaynhap <='"+this.denngay+"'   -- " +
				 "\n  		and isnull(dh_sp.sanpham_fk,dh_sp1.sanpham_fk)   in ( select sanpham_fk from thuongdauthung_sp where thuong_fk = '"+this.Id+"' )     -- " +
				 "\n  		union all         -- " +
				 "\n  		select asmkv.ASM_FK,dh.GSBH_FK,dh.ddkd_fk,(select x.KHACHHANG_FK from DONHANG x where x.PK_SEQ = dh.PK_SEQ and not exists(select 1 from donhangtrave where donhang_fk =x.pk_seq) ) kh_fk, dh_sp.soluong , case when sp.dvdl_fk= 100018 then   dh_sp.soluong else  -- " +
				 "\n  		(case when  qc.soluong1 >0  then dh_sp.soluong * qc.soluong2/ qc.soluong1 else 0 end  )   end  as sothung   -- " +
				 "\n 		,isnull(dh_sp.giamua, 0) as giamua     -- " +
				 "\n  		from donhang dh  inner join donhang_sanpham  dh_sp on dh.pk_seq = dh_sp.donhang_fk        -- " +
				 "\n  			inner join sanpham sp on sp.pk_seq=dh_sp.sanpham_fk     -- " +
				 "\n  			left join quycach qc on qc.dvdl1_fk=sp.dvdl_fk and qc.sanpham_fk=sp.pk_seq and qc.dvdl2_fk=100018  -- " +
				 "\n  			inner join GSBH_KHUVUC gsbhkv on dh.GSBH_FK = gsbhkv.GSBH_FK -- " +
				 "\n  			inner join ASM_KHUVUC asmkv on asmkv.KHUVUC_FK = gsbhkv.KHUVUC_FK -- " +
				 "\n  			inner join ASM on ASM.PK_SEQ = asmkv.ASM_FK and asm.KBH_FK = dh.KBH_FK -- " +
				 "\n  		where dh.trangthai = '1' and ngaynhap>='"+this.tungay+"' and ngaynhap <='"+this.denngay+"'   -- " +
				 "\n  		and dh_sp.sanpham_fk  in( select sanpham_fk from thuongdauthung_sp where thuong_fk = '"+this.Id+"' )        -- " +
				 "\n  	) dh          -- " +
				 "\n  	group by ASM_FK,GSBH_FK , ddkd_fk    -- " +
				 "\n  ) thucdat on thucdat.ddkd_fk  = ddkd.pk_seq    -- " +
				 "\n  where ddkd.trangthai = 1  -- " ;

			System.out.println("tinh thuong=" + sql);
			if(!this.db.update(sql))
			{				
				this.db.getConnection().rollback();
				this.Message="Vui lòng liên hệ Admin để sửa lỗi "+sql;
				return false;
			}		
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return  true ;
		}
		catch (Exception e) 
		{
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			this.setMessage("Ban khong the chot Thuong dau thung, loi : " + e.toString());
			return false;			
		}

	}

	public boolean UnchotLuongKhac()
	{
		List<Object> data = new ArrayList<Object>();
		
		String  query = "update thuongdauthung set trangthai =0 where  pk_seq = ?";		
		
		data.clear();
		data.add(Double.toString(this.Id));
		
		if(this.db.updateQueryByPreparedStatement(query, data) != 1)
			return false;
		return true;
	}
	public boolean DeleteLuongKhac()
	{
		List<Object> data = new ArrayList<Object>();
		
		String  query = "update thuongdauthung set trangthai =2 where  pk_seq = ? and trangthai = 0";	
		
		data.clear();
		data.add(Double.toString(this.Id));
		
		if(this.db.updateQueryByPreparedStatement(query, data) != 1)
			return false;
		return true;
	}

	public void closeDB() {
		try{
			this.luongkhacRs.close();
			if(this.db!=null){
				db.shutDown();
			}
		}catch(Exception er){

		}
	}

	//this.soluong = 0;
	public double getSoluong() {
		return soluong;
	}
	public void setSoluong(double soluong) {
		this.soluong = soluong;
	}
	//this.isThung = "0";
	public String getLoaict() {
		return loaict;
	}
	public void setLoaict(String isThung) {
		this.loaict = isThung;
	}

	//this.thuongSR = 0;
	public double getThuongSR() {
		return thuongSR;
	}
	public void setThuongSR(double thuongSR) {
		this.thuongSR = thuongSR;
	}
	//this.thuongSS = 0;
	public double getThuongSS() {
		return thuongSS;
	}
	public void setThuongSS(double thuongSS) {
		this.thuongSS = thuongSS;
	}
	//this.thuongASM = 0;
	public double getThuongASM() {
		return thuongASM;
	}
	public void setThuongASM(double thuongASM) {
		this.thuongASM = thuongASM;
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
	//this.tyledoanhsoSR = 0;
	
	public void setNsptt_fk(String nsptt_fk) {
		this.nsptt_fk = nsptt_fk;
	}
	public String getNsptt_fk() {
		return nsptt_fk;
	}
	
	public String getinfoDETAILASM() {
		return infoDETAILASM;
	}
	public void setinfoDETAILASM(String infoDETAILASM) {
		this.infoDETAILASM = infoDETAILASM;
	}
	
	public String getinfoDETAILSR() {
		return infoDETAILSR;
	}
	public void setinfoDETAILSR(String infoDETAILSR) {
		this.infoDETAILSR = infoDETAILSR;
	}
	
	public String getinfoDETAILSS() {
		return infoDETAILSS;
	}
	public void setinfoDETAILSS(String infoDETAILSS) {
		this.infoDETAILSS = infoDETAILSS;
	}
	
	
	
	
	public void setNsp_fk(String nsp_fk) {
		this.nsp_fk = nsp_fk;
	}
	public String getNsp_fk() {
		return nsp_fk;
	}
	public ResultSet getNspRs() {
		return nspRs;
	}
	public ResultSet getNspttRs() {
		return nspttRs;
	}
	
	public void setNkh_fk(String nkh_fk) {
		this.nkh_fk = nkh_fk;
	}
	public String getNkh_fk() {
		return nkh_fk;
	}
	public ResultSet getNkhRs() {
		return nkhRs;
	}

	
	public void setdstoithieu(double dstoithieu) {
		this.dstoithieu = dstoithieu;
	}
	public double getdstoithieu() {
		return dstoithieu;
	}


	public class thuongSR
	{
		public String ddkd_fk = "";
		public String gsbh_fk = "";
		public double dieukienSR ;
		public double dieukienSS ;
		public double dieukienNPP ;
	}



	List<ISanpham> spList;

	@Override
	public List<ISanpham> getSpList()
	{
		return this.spList;
	}

	@Override
	public void setSpList(List<ISanpham> spList)
	{
		this.spList = spList;	
	}
	String[] sanpham;
	public String[] getSanpham()
	{
		return this.sanpham;
	}

	public void setSanpham(String[] sanpham)
	{
		this.sanpham = sanpham;
	}


	public void createSpList()
	{
		String query = "select b.pk_seq, b.MA, b.TEN from NHOMSANPHAMCHITIEU_SANPHAM a inner join SANPHAM b on a.SP_FK = b.PK_SEQ where a.NSP_FK = '" + this.nsp_fk + "'";	
		ResultSet rs = db.get(query);
		List<ISanpham> splist = new ArrayList<ISanpham>();
		if(rs != null)
		{
			try 
			{
				ISanpham sp = null;
				while(rs.next())
				{
					String spId = rs.getString("pk_seq");
					String spMa = rs.getString("MA");
					String spTen = rs.getString("TEN");
					sp = new Sanpham(spId, spMa, spTen);
					splist.add(sp);
				}
			} 
			catch(Exception e) {}
		}
		this.spList = splist;
	}


	ResultSet dataRs;

	public ResultSet getDataRs()
	{
		String cotlay ="tct.SoLuongThung" ;
		if(this.loaict.equals("1"))
			cotlay ="tct.SoLuong" ;
		else
			if(this.loaict.equals("2"))
				cotlay ="tct.SoKh";
				
		String query= 
			 "\n SELECT ASM.TEN as asm, gsbh.TEN as gsbh, ddkd.TEN as ddkd,"+cotlay+","+cotlay+"*thuongSR as thuongSR,"+cotlay+"*thuongSS as thuongSS,"+cotlay+"*thuongASM as thuongASM -- " +
			 "\n    FROM ThuongDauThung_SR tct     -- " +
			 "\n		inner join ThuongDauThung t on tct.Thuongdauthung_Fk = t.pk_seq -- "+
			 "\n    	left JOIN DaiDienKinhDoanh ddkd on ddkd.PK_SEQ=tct.DDKD_FK           -- " +
			 "\n    	left JOIN GIAMSATBANHANG gsbh on gsbh.PK_SEQ=tct.GSBH_FK     -- " +
			 "\n    	left join ASM  on ASM.PK_SEQ = tct.ASM_FK       -- " +
			 "\n    WHERE Thuongdauthung_Fk =  " + this.Id;
		System.out.println("init baocao =" + query);
		this.dataRs=this.db.get(query);
		return dataRs;
	}



	public void setDataRs(ResultSet dataRs)
	{
		this.dataRs = dataRs;
	}




	public String getView() {
		return view;
	}
	public void setView(String view) {
		this.view = view;
	}

	public void DbClose(){
		db.shutDown();
	}
}
