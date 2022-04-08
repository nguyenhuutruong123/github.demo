package geso.dms.center.beans.duyettratrungbay.imp;

import geso.dms.center.beans.duyettratrungbay.IDuyettratrungbay;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;

public class Duyettratrungbay implements IDuyettratrungbay, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8158813770943365236L;
	String schemeId;
	String scheme;
	int solantt;
	int lantt;
	String msg;
	ResultSet schemeRS;
	ResultSet kv;
	String kvId;
	ResultSet vung;
	String vungId;
	ResultSet npplist;
	String nppId;
	ResultSet khlist;
	String[] khIds;
	String trangthai,userId;
	dbutils db ;
	Utility Ult = new Utility();
	String daduyet;
	String diengiai = "";



	Hashtable<String, Integer> tratb;
	private String id;
	public Duyettratrungbay()
	{
		this.schemeId = "";
		this.id = "";
		this.scheme = "";
		this.solantt = 1;
		this.lantt = 1;
		this.msg = "";
		this.kvId = "0";
		this.vungId = "0";
		this.npplist = null;
		this.nppId = "0";
		this.trangthai = "";
		this.daduyet = "0";
		this.db = new dbutils();
		Ult	= new Utility();
	}

	public String getSchemeId()
	{
		return this.schemeId;
	}

	public void setSchemeId(String schemeId)
	{
		this.schemeId = schemeId;
	}

	public int getSolantt() 
	{		
		return this.getSolanthanhtoan();
	}

	public void setSolantt(int solantt)
	{
		this.solantt = solantt;		
	}

	public String getNppId() 
	{		
		return this.nppId;
	}

	public void setNppId(String nppId)
	{
		this.nppId = nppId;		
	}

	public String[] getKhIds() 
	{		
		return this.khIds;
	}

	public void setKhIds(String[] khIds)
	{
		this.khIds = khIds;		
	}

	public int getLantt() 
	{	
		return this.lantt;
	}

	public void setLantt(int lantt)
	{
		this.lantt = lantt;		
	}

	public Hashtable<String, Integer> getTraTb()
	{
		return this.tratb;
	}

	public String getTrangthai(){

		return this.trangthai;
	}

	public void setTraTb(Hashtable<String, Integer> tratb)
	{
		this.tratb = tratb;
	}

	public String getScheme()
	{
		return this.scheme;
	}

	public void setScheme(String scheme)
	{
		this.scheme = scheme;
	}

	public String getMessage()
	{
		return this.msg;
	}

	public void setMessage(String msg)
	{
		this.msg = msg;
	}

	public ResultSet getSchemeRS() 
	{
		return this.schemeRS;
	}

	public void setSchemeRS(ResultSet schemeRS) 
	{
		this.schemeRS = schemeRS;
	}

	public ResultSet getVung() 
	{
		return this.vung;
	}

	public void setVung(ResultSet vung) 
	{
		this.vung = vung;
	}

	public String getVungId() 
	{
		return this.vungId;
	}

	public void setVungId(String vungId) 
	{
		this.vungId = vungId;
	}

	public ResultSet getKv() 
	{
		return this.kv;
	}

	public void setKv(ResultSet khuvuc) 
	{
		this.kv = khuvuc;
	}

	public String getKvId() 
	{
		return this.kvId;
	}

	public void setKvId(String kvId) 
	{
		this.kvId = kvId;
	}

	public ResultSet getNpp() 
	{
		return this.npplist;
	}

	public void setNpp(ResultSet npplist) 
	{
		this.npplist = npplist;
	}

	public ResultSet getKhRs() 
	{
		return this.khlist;
	}
	private ResultSet createSchemeRS()
	{
		String idX = "0";
		if(this.id.trim().length() > 0)
			idX = this.id;
		String sql_scheme = " SELECT  PK_SEQ, SCHEME, DIENGIAI " +
							" FROM CTTRUNGBAY TB " +
							" where isnull(TB.SOLANTHANHTOAN,0) > ( select count(*) from DENGHITRATRUNGBAY x where x.pk_seq != "+idX+" and x.cttrungbay_fk = TB.pk_seq  )  "; 

		ResultSet schemeRS = this.db.get(sql_scheme );
		System.out.println("__scheme__"+ sql_scheme);
		return schemeRS;
	}

	private ResultSet createVungRS(){  	
		String sql_vung="select pk_seq, diengiai from vung  where trangthai='1'";
		ResultSet vungRS =  this.db.get(sql_vung);
		System.out.println("__vung__"+sql_vung);
		return vungRS;
	}


	private ResultSet createKvRS(){
		ResultSet kvRS;
		if (!this.vungId.equals("0")){
			kvRS =  this.db.get("select pk_seq, diengiai from khuvuc where trangthai='1' and vung_fk='" + this.vungId + "'");
		}else{
			kvRS =  this.db.get("select pk_seq, diengiai from khuvuc where trangthai='1'");
		}
		return kvRS;

	}

	private ResultSet createNppRS(){
		ResultSet nppRS = null;
		String sql_npp="";
		if(this.schemeId.trim().length()>0)
		{
			sql_npp=
				"select  pk_seq, ten from nhaphanphoi NPP " +
				" where NPP.pk_seq in  (SELECT  NPP_FK FROM DENGHITRATRUNGBAY WHERE CTTRUNGBAY_FK='"+this.schemeId+"' " ;
			if(this.lantt!=0)
				sql_npp+=" AND LANTHANHTOAN='"+this.lantt+"' ";
			sql_npp+=
				" ) "+
				" and NPP.trangthai='1' and NPP.pk_seq in "+Ult.quyen_npp(this.userId)+" ";
		}
		else 
			sql_npp="select pk_seq, ten from nhaphanphoi where trangthai='1' and pk_seq in "+Ult.quyen_npp(this.userId)+" ";

		if (!this.kvId.equals("0"))
		{
			sql_npp+=" and khuvuc_fk='" + this.kvId + "' ";
		}
		nppRS =  this.db.get(sql_npp);
		System.out.println("__npp___"+sql_npp);
		return nppRS;
	}

	public void init()
	{
		if(this.id.length() > 0){
			String query = "select * from DENGHITRATRUNGBAY where PK_SEQ = " + this.id;
			ResultSet rs = this.db.get(query);
			try {
				if(rs != null && rs.next()){
					this.diengiai = rs.getString("DIENGIAI");
					this.schemeId = rs.getString("CTTRUNGBAY_FK");
					this.daduyet = rs.getString("TRANGTHAI");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.khlist = this.createKhRs();
		}
		createRs();
	}

	public String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);	
	}

	public void getLanthanhtoan()
	{
		if(this.schemeId.length()>0)
		{
			String query  = "select max(lanthanhtoan) as num from denghitratrungbay where cttrungbay_fk='"+ this.schemeId + "' and trangthai = 1"; 
			ResultSet rs = db.get(query);
			int lanthanhtoan = 0;
			try
			{
				if(rs != null)
				{
					rs.next();
					lanthanhtoan = rs.getInt("num") + 1;
				}
			}
			catch(Exception e){}

			this.lantt =  lanthanhtoan;
		}
		else
		{
			this.lantt = 0;
		}
	}

	private int getSolanthanhtoan()
	{
		if(this.schemeId.length()>0)
		{
			String query  = "select solanthanhtoan as num from cttrungbay where pk_seq='"+ this.schemeId + "'"; 
			ResultSet rs = db.get(query);
			int solanthanhtoan = 0;
			try
			{
				if(rs != null){
					rs.next();
					solanthanhtoan = rs.getInt("num");
				}
			}catch(Exception e){}

			return solanthanhtoan;
		}else{
			return 0;
		}
	}

	//Loc ra nhung khach hang thuoc NPP ma User do quan ly NPP
	private ResultSet createKhRs()
	{	
		String query;

		if (this.id.trim().length() > 0)
		{
			
			String subq =  	  	 "\n 	'['+ STUFF((" + 
			 "\n                 SELECT ',{\"thoidiem\":' + '''' + isnull(convert(varchar,t1.thoidiem,120),'') + ''''  " + 

			 "\n 							+ ',\"imagePath\":'''+isnull(AnhTrungBay,'')  + '''}' " + 
			 "\n                     FROM CTTB_KHACHHANG_ANHCHUP t1  " + 
			 "\n 					 where t1.DKTRUNGBAY_FK  = dk.DKTRUNGBAY_FK and t1.KHACHHANG_FK= dk.KHACHHANG_FK FOR XML PATH(''), TYPE" + 
			 "\n                   ).value('.', 'varchar(max)'),1,1,''" + 
			 "\n               ) + ']' ";
			
			
			
			query= "select ddkd.DDKDMA,ddkd.DDKDTEN,a.STT,isnull(duyet.duyetAnh,0)duyetAnh,b.pk_seq khId, b.SMARTID AS MAFAST, b.ten as khTen, P.TEN AS NPP, dk.DANGKY, dk.SUATDUYETDK,a.XuatDeNghi, a.XUATDUYET as SOXUAT, a.soluong,a.doanhso" +
			" , isnull(dk.ImageFilePath,'') AnhDK ,isnull(dk.thoidiem,'') thoidiemAnhDK  "+		
			" , "+subq+" hinhanh "+
			" from denghitratb_khachhang a  "+
			" inner join khachhang b on a.khachhang_fk = b.pk_seq " +
			" INNER JOIN NHAPHANPHOI p on p.PK_SEQ = b.NPP_FK "+
			" inner join denghitratrungbay c on  a.denghitratb_fk = c.pk_seq " +
			" inner join DANGKYTRUNGBAY tb on tb.CTTRUNGBAY_FK = c.CTTRUNGBAY_FK"+ 
			" inner join DKTRUNGBAY_KHACHHANG dk on tb.PK_SEQ = dk.DKTRUNGBAY_FK and dk.KHACHHANG_FK = a.KHACHHANG_FK " +	
			" outer apply ( select top 1 duyetAnh from DENGHITRATB_KHACHHANG_DUyetAnh x where a.denghitratb_fk =x.denghitratb_fk and x.KHACHHANG_FK = a.KHACHHANG_FK  ) duyet " +
			
			"\n outer apply" +
			"\n (" +
			"\n		select top 1 ddkd.ten DDKDTEN ,ddkd.smartId DDKDMA" +
			"\n 	from daidienkinhdoanh ddkd" +
			"\n		inner join tuyenbanhang tbh on tbh.ddkd_fk = ddkd.pk_seq" +
			"\n 	inner join khachhang_tuyenbh x on x.tbh_fk = tbh.pk_seq " +
			"\n		where x.khachhang_fk  = b.pk_seq and tbh.npp_fk = b.npp_fk " +
			"\n )ddkd " +
			
			" where c.PK_SEQ ='"+ this.id +"' order by a.STT";

			System.out.println("_ds khach hang dk trung bay__"+query  );
			return db.get(query);
		}else{
			return null;
		}

	}

	public boolean Luutratb()
	{
		if(this.diengiai.length() == 0){
			this.msg = "Vui lòng nhập diễn giải";
			return false;
		}
		if(this.schemeId.length() == 0){
			this.msg = "Vui lòng chọn chương trình trưng bày";
			return false;
		}

		try
		{
			db.getConnection().setAutoCommit(false);
			
			
			String query = 	"\n select SOLANTHANHTOAN" +
							"\n 	,( select count(*) from DENGHITRATRUNGBAY where trangthai in (0,1) and CTTRUNGBAY_FK  = x.pk_seq )datra  " +
							"\n from CTTRUNGBAY x  where pk_seq = "+this.schemeId;
			ResultSet rs= db.get(query);
			rs.next();
			int SOLANTHANHTOAN = rs.getInt("SOLANTHANHTOAN");
			int datra = rs.getInt("datra");
			if(datra >=SOLANTHANHTOAN )
			{
				this.msg = " Chương trình trưng bày đã hết số lần thanh toán ";
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
				return false;
			}
			
			
			String insert_="insert into DENGHITRATRUNGBAY (CTTRUNGBAY_FK, DIENGIAI, NGAYDENGHI, TRANGTHAI, NGAYSUA, NGUOITAO, NGUOISUA) " +
			" values("+this.schemeId+", N'"+this.diengiai+"', '"+this.getDateTime()+"', 0, '"+this.getDateTime()+"', "+this.userId+", "+this.userId+")";

			if(!db.update(insert_)){

				this.msg = "Khong The Thuc Hien Insert ,Vui Long Thu Lai. Loi "+ insert_;
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);

				return false;

			}

			rs = db.get(" select scope_identity() ii ");
			rs.next();
			this.id = rs.getString("ii");


			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch(Exception er)
		{
			this.msg = "Khong The Thuc Hien Insert ,Vui Long Thu Lai. Loi "+ er.toString();
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return false;
		}
		return true;

	}

	public boolean Chot( HttpServletRequest request)
	{
		try 
		{
			db.getConnection().setAutoCommit(false);
			for(int i = 0; i < this.khIds.length ; i++)
			{
				String tratb = request.getParameter("duyet" + this.khIds[i]);
				String dangky = request.getParameter("dangky" + this.khIds[i]);

				if(Integer.parseInt(dangky) >= Integer.parseInt(tratb))
				{
					String sql = "update denghitratb_khachhang set xuatduyet = '"+ tratb + " ' " +
					"where khachhang_fk = '" + this.khIds[i] + "' and denghitratb_fk in (select pk_seq from denghitratrungbay where trangthai='0' and cttrungbay_fk='"+ this.schemeId +"' and lanthanhtoan='"+ this.lantt +"')";

					System.out.println("__Duyet tra trung bay__"+sql);

					if(this.db.updateReturnInt(sql)<=0)
					{
						this.msg = "Không thể cập nhật " + sql;
						db.getConnection().rollback();
						return false;
					}
				}
				else
				{
					this.msg = "So xuat duyet khong the lon hon so xuat dang ky";
					db.getConnection().rollback();
					return false;
				}
			}

			String sql = "update denghitratrungbay set trangthai = '1', NguoiSua = '" + this.userId + "', NgaySua='" + getDateTime() + "' " +
			"where cttrungbay_fk = '" + this.schemeId + "' and lanthanhtoan = '" + this.lantt + "'" ;
			System.out.println("Cap nhat DE NGHI: " + sql);

			if(!this.db.update(sql))
			{
				this.msg=" Khong the cap nhat duyet tra trung bay nay "+sql;
				this.db.getConnection().rollback();
				return false;
			}

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			geso.dms.center.util.Utility.rollback_throw_exception(this.db);
			System.out.println("__Exception Chot: " + e.getMessage());
		}

		this.msg = "Chốt duyệt trả trưng bày thành công";
		this.daduyet = "1";
		return true;
	}




	public String getUserId()
	{

		return this.userId;
	}
	public void setUserId(String UserId)
	{
		this.userId=UserId;

	}


	public void closeDB()
	{
		try
		{	
			if(this.schemeRS!=null)
				this.schemeRS.close();

			if(this.npplist!=null)
				this.npplist.close();

			if(this.khlist!=null)
				this.khlist.close();

			if(this.vung!=null)
				this.vung.close();

			if(this.kv!=null)
				this.kv.close();				

		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String getDaduyet()
	{
		return this.daduyet;
	}

	public void setDaduyet(String daduyet) 
	{
		this.daduyet = daduyet;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public void createRs() {
		this.schemeRS = this.createSchemeRS();
	}

	@Override
	public void setKhRs() {
		/*if(this.schemeId.length() == 0){
			this.msg = "Vui lòng chọn chương trình trưng bày.";
			return;
		}
		try{
			if(this.khIds.length > 0){			
				String query = "" +
				"IF OBJECT_ID('tempdb.dbo.#DATA') IS NOT NULL DROP TABLE #DATA " +
				"CREATE TABLE #DATA " +
				"( " +
				"  	STT INT, SMARTID varchar(50), SUATDUYET INT " +
				") ";


				for(int i=0; i<this.khIds.length; i++)
					query += "\n INSERT #DATA(STT, SMARTID, SUATDUYET) VALUES("+i+", '"+this.khIds[i]+"', "+this.tratb.get(this.khIds[i])+")";
				System.out.println(query);
				this.db.update(query);
				String sqlcheck = "select d.SMARTID from KHACHHANG k right join #DATA d on d.SMARTID = k.SMARTID where k.SMARTID is null";
				ResultSet rscheck = this.db.get(sqlcheck);
				String khcheck = "";
				while(rscheck.next())
					khcheck += rscheck.getString("SMARTID") + ", ";

				query = "select d.STT, k.SMARTID AS MAFAST, k.TEN AS khTen, p.TEN AS NPP, dk.DANGKY, dk.SUATDUYETDK, d.SUATDUYET AS SOXUAT " +
				"from DKTRUNGBAY_KHACHHANG dk inner join DANGKYTRUNGBAY tb on tb.PK_SEQ = dk.DKTRUNGBAY_FK " +
				"inner join KHACHHANG k on dk.KHACHHANG_FK = k.PK_SEQ " +
				"inner join NHAPHANPHOI p on p.PK_SEQ = k.NPP_FK " +
				"left join #DATA d on d.SMARTID = k.SMARTID " +
				"where tb.CTTRUNGBAY_FK = '"+this.schemeId+"'";

				this.khlist = db.get(query);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}*/
	}

	@Override
	public String getDiengiai() {
		// TODO Auto-generated method stub
		return this.diengiai;
	}

	@Override
	public void setDiengiai(String value) {
		this.diengiai = value;
	}


	public boolean Upload()
	{
		if(this.khIds.length == 0){
			this.msg = "Chưa có khách hàng nào được upload";
			return false;
		}
		try
		{

			String query = "";	

			query = "UPDATE DENGHITRATRUNGBAY  SET trangthai = 0 WHERE trangthai = 0 and PK_SEQ = " + this.id;

			if(db.updateReturnInt(query)!=1){

				this.msg = "Khong The Cap Nhat ,Vui Long Thu Lai. Loi (1)";
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
				return false;

			}
			
			
			for(int i = 0; i < this.khIds.length ; i++)
			{
				query = " update  denghitratb_khachhang set XUATDUYET = "+this.tratb.get(this.khIds[i])+"  " +
						" where  DENGHITRATB_FK= "+this.id+" and  KHACHHANG_FK = (select pk_seq from khachhang where smartId = '"+this.khIds[i]+"' )" ;
				if(db.updateReturnInt(query)!=1)
				{

					this.msg = "Khong The Thuc Hien Insert ,Vui Long Thu Lai. Loi "+ query;
					db.getConnection().rollback();
					db.getConnection().setAutoCommit(true);
					return false;

				}
			}


			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			this.msg = " Upload thành công";
			return true;
		}catch (Exception e) {
			this.msg = " Lỗi : " + e.getMessage();
			e.printStackTrace();
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return false;
		}
	}

	@Override
	public boolean UpdateTb() {
		if(this.diengiai.length() == 0){
			this.msg = "Vui lòng nhập diễn giải";
			return false;
		}
		if(this.schemeId.length() == 0){
			this.msg = "Vui lòng chọn chương trình trưng bày";
			return false;
		}

		try
		{
			db.getConnection().setAutoCommit(false);
			String update = "UPDATE DENGHITRATRUNGBAY  SET DIENGIAI = N'"+this.diengiai+"', NGAYSUA = '"+this.getDateTime()+"', NGUOISUA = "+this.userId+" WHERE PK_SEQ = " + this.id;

			if(!db.update(update)){

				this.msg = "Khong The Cap Nhat ,Vui Long Thu Lai. Loi (1)";
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				return false;

			}

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return true;
		}
		catch(Exception er)
		{
			this.msg = "Khong The Thuc Hien Insert ,Vui Long Thu Lai. Loi "+ er.toString();
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return false;
		}

	}

	public boolean Aptrungbay() 
	{

		if(this.schemeId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn chương trình trưng bày ";
			return false;
		}
		if(this.id.trim().length() <= 0)
		{
			this.msg = "Vui lòng lưu đề nghị trước ";
			return false;
		}

		try 
		{
			db.getConnection().setAutoCommit(false);
			String query = "delete from denghitratb_khachhang where denghitratb_fk = " + this.id;
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới denghitratb_khachhang (1)";
				db.getConnection().rollback();
				return false;
			}

			boolean cotrungbay = false;
			int dong = 0;
			query =  "\n select a.diengiai,a.PK_SEQ,a.LOAI, isnull(a.TONGTIEN,0)TONGTIEN,isnull(a.TONGLUONG,0)TONGLUONG , b.pheptoan , cb.ngaytds,cb.ngaykttds,cb.phaimuadh " + 
			"\n from nhomsptrungbay a" + 
			"\n inner join  CTTB_NHOMSPTRUNGBAY b on a.PK_SEQ = b.NHOMSPTRUNGBAY_FK " +
			"\n inner join cttrungbay cb on cb.pk_seq = b.CTTRUNGBAY_FK  " + 
			"\n where b.CTTRUNGBAY_FK =" +  this.schemeId + 
			"\n order by thutudieukien asc ";
			System.out.println("query All = "+ query);
			ResultSet rs = db.get(query);

			while(rs.next())
			{

				cotrungbay = true;
				String  dieukienmuaId =  rs.getString("PK_SEQ");  
				String ngaytds = rs.getString("ngaytds");
				String ngaykttds = rs.getString("ngaykttds");
				int LOAI = rs.getInt("LOAI");
				int pheptoan = rs.getInt("pheptoan"); // 1 là and thì lấy min, 2 là or thì lấy sum
				String diengiai = rs.getString("diengiai");
				double TONGLUONG = rs.getDouble("TONGLUONG");
				double TONGTIEN = rs.getDouble("TONGTIEN");
				String queryAp = "";
				
				if(LOAI == 2)// áp tổng tiền
				{
					
					
					
					if( TONGLUONG == 0 && TONGTIEN == 0 )
					{
						this.msg = "Tổng tiền hoặc tổng lượng của CTKM phải có giá trị";
						db.getConnection().rollback();
						return false;
					}
					if( TONGLUONG > 0 && TONGTIEN > 0 )
					{
						this.msg = "Tổng tiền hoặc tổng lượng của CTKM  không được cùng có giá trị";
						db.getConnection().rollback();
						return false;
					}
					int hinhthuc = 1;
					if(TONGLUONG > 0) hinhthuc = 2;

					queryAp  =  "\n select "+this.id+" denghitratb_fk , dk.khachhang_fk, null DDKD_FK , dk.dangky  XuatDangKy " +
								"\n			, floor( isnull( case "+hinhthuc+" when 1 then doanhso/"+TONGTIEN+" else soluong/"+TONGLUONG+" end,0)) XuatDeNghi" +
								"\n					, null XuatDuyet, null Dat, null STT,isnull(mua.soluong,0)soluong,isnull(mua.doanhso,0)doanhso  " + 
								"\n from DKTRUNGBAY_KHACHHANG dk" + 
								"\n outer apply " + 
								"\n (" + 
								"\n 	select sum(dhsp.soluong)soluong,sum(dhsp.soluong*dhsp.giamua) doanhso " + 
								"\n 	from DONHANG dh" + 
								"\n 	inner join DONHANG_SANPHAM dhsp on dh.PK_SEQ = dhsp.DONHANG_FK " + 
								"\n 	inner join nhomsptrungbay_sanpham nsp on dhsp.SANPHAM_FK = nsp.SANPHAM_FK and nsp.NHOMSPTRUNGBAY_FK = " + dieukienmuaId + 
								"\n 	where dh.trangthai = 1 and dh.KHACHHANG_FK = dk.KHACHHANG_FK" + 
								"\n 		and dh.NGAYNHAP >='"+ngaytds+"' and dh.NGAYNHAP <='"+ngaykttds+"'" + 
								"\n )mua" + 
								"\n where dk.DKTRUNGBAY_FK in (select pk_seq from DANGKYTRUNGBAY where trangthai = 1 and CTTRUNGBAY_FK = "+this.schemeId+")" ;

				}
				else
				{
					queryAp  = "\n select "+this.id+" denghitratb_fk , dk.khachhang_fk, null DDKD_FK , dk.dangky  XuatDangKy " +
					"\n			,  floor( isnull( mua.sosuat  ,0)) XuatDeNghi" +
					"\n					, null XuatDuyet, null Dat, null STT,isnull(mua.soluong,0)soluong,isnull(mua.doanhso,0)doanhso " + 
					"\n from DKTRUNGBAY_KHACHHANG dk" + 
					"\n outer apply " + 
					"\n (" + 
					"\n 	select min (kq)sosuat,sum(soluong)soluong,sum(doanhso)doanhso " + 
					"\n 	from" + 
					"\n 	(" + 
					"\n 		select nsp.sanpham_fk,dbo.phepchia (sum(dhsp.soluong),  max( nsp.soluong)) kq,sum(dhsp.soluong)soluong,sum(dhsp.soluong*dhsp.giamua) doanhso " + 
					"\n 		from DONHANG dh" + 
					"\n 		inner join DONHANG_SANPHAM dhsp on dh.PK_SEQ = dhsp.DONHANG_FK " + 
					"\n 		inner join nhomsptrungbay_sanpham nsp on dhsp.SANPHAM_FK = nsp.SANPHAM_FK and nsp.NHOMSPTRUNGBAY_FK =" + dieukienmuaId + 
					"\n 		where dh.trangthai = 1 and dh.KHACHHANG_FK = dk.KHACHHANG_FK	" +
					"\n 			and dh.NGAYNHAP >='"+ngaytds+"' and dh.NGAYNHAP <='"+ngaykttds+"'" +
					"\n 		group by nsp.sanpham_fk " + 
					"\n 	) kq" + 
					"\n )mua" + 
					"\n where dk.DKTRUNGBAY_FK in (select pk_seq from DANGKYTRUNGBAY where trangthai = 1 and CTTRUNGBAY_FK = "+this.schemeId+")" ;

				}

				if(dong == 0)// dog dau tien thi insert
					query = "\n with data as ( "+queryAp+" )  " +
					"\n insert denghitratb_khachhang (denghitratb_fk,khachhang_fk,DDKD_FK,XuatDangKy,XuatDeNghi,XuatDuyet,Dat,STT,soluong,doanhso) " +
					"\n select denghitratb_fk,khachhang_fk,DDKD_FK,XuatDangKy,XuatDeNghi,XuatDuyet,Dat,STT,soluong,doanhso " +
					"\n from data " ;
				else
					query = "\n with data as ( "+queryAp+" )  " +
					"\n update dn set  dn.XuatDeNghi = case  when 1 = "+pheptoan+" then dbo.MinNum(dn.XuatDeNghi,data.XuatDeNghi) else dn.XuatDeNghi  + data.XuatDeNghi end, " +
					"\n	dn.soluong = dn.soluong + data.soluong, dn.doanhso= dn.doanhso + data.doanhso  " +
					"\n from data inner join denghitratb_khachhang dn on data.denghitratb_fk = dn.denghitratb_fk and data.khachhang_fk = dn.khachhang_fk" ;

				System.out.println("query ap = "+ query);
				if(db.updateReturnInt(query)<=0)
				{
					this.msg = "Không có dữ liệu phát sinh, vui lòng kiểm trả lại!";
					db.getConnection().rollback();
					db.getConnection().setAutoCommit(true);
					return false;
				}

				dong ++;
			}


			if(!cotrungbay)
			{
				this.msg = "Vui lòng kiểm tra đăng ký trưng bày!";
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
				return false;
			}

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			this.msg ="Áp trưng bày thành công";
			return true;
		}
		catch (Exception e) 
		{
			try {
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			this.msg = "Không thể duyệt trưng bày.";
			e.printStackTrace();
			return false;
		}


	}

	public dbutils getDb() {
		return db;
	}
	
}
