package geso.dms.center.beans.report.imp;

import geso.dms.center.beans.report.IBcHoaDon;
import geso.dms.center.db.sql.db_Sync;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.OracleConnUtils;
import geso.dms.center.util.Phan_TrangOracle;
import geso.dms.center.util.Utility;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

public class BcHoaDon extends Phan_TrangOracle implements IBcHoaDon, Serializable
{
	private static final long serialVersionUID = -3237541992706452258L;
	public BcHoaDon()
	{
		this.spId="";
		this.nppId="";
		this.userId="";
		this.khId="";
		this.msg="";
		this.ddkdId="";
		this.tuNgay="";
		this.denNgay="";
		this.kbhId="";
		this.ttId="";
		this.nhomId="";
		this.vungId="";
		this.loaiHoaDon="";
		this.action="";
		this.xemtheo = 1;
		this.soId ="";
		this.hoadon ="";
		this.pxkxoa = "";
		db = new dbutils();
	}
	String tuNgay,denNgay,spId,nppId,ddkdId,userId,khId, pxkxoa;
	int xemtheo=1; //=1 xem mac dinh theo khach hang, -1 xem theo trinh duoc vien
	public String getKhId()
	{
		return khId;
	}
	public void setKhId(String khId)
	{
		this.khId = khId;
	}
	public String getTuNgay()
	{
		return tuNgay;
	}
	public void setTuNgay(String tuNgay)
	{
		this.tuNgay = tuNgay;
	}
	public String getDenNgay()
	{
		return denNgay;
	}
	public void setDenNgay(String denNgay)
	{
		this.denNgay = denNgay;
	}
	public String getSpId()
	{
		return spId;
	}
	public void setSpId(String spId)
	{
		this.spId = spId;
	}
	public String getNppId()
	{
		return nppId;
	}
	public void setNppId(String nppId)
	{
		this.nppId = nppId;
	}
	public String getDdkdId()
	{
		return ddkdId;
	}
	public void setDdkdId(String ddkdId)
	{
		this.ddkdId = ddkdId;
	}
	public String getUserId()
	{
		return userId;
	}
	public void setUserId(String userId)
	{
		this.userId = userId;
	}
	public ResultSet getSpRs()
	{
		return spRs;
	}
	public void setSpRs(ResultSet spRs)
	{
		this.spRs = spRs;
	}
	public ResultSet getDdkdRs()
	{
		return ddkdRs;
	}
	public void setDdkdRs(ResultSet ddkdRs)
	{
		this.ddkdRs = ddkdRs;
	}
	ResultSet spRs,ddkdRs,nhaphangRs,hoadonRs;
	public ResultSet getHoadonRs()
	{
		return hoadonRs;
	}
	public void setHoadonRs(ResultSet hoadonRs)
	{
		this.hoadonRs = hoadonRs;
	}

	public ResultSet getNhaphangRs()
	{
		return nhaphangRs;
	}
	public void setNhaphangRs(ResultSet nhaphangRs)
	{
		this.nhaphangRs = nhaphangRs;
	}

	public void closeDB()
	{

	}

	dbutils db = new dbutils();

	public void createRs()
	{
		try
		{
			Utility util = new Utility();
			String query="select pk_Seq,ma,ten from sanpham where trangthai=1";
			this.spRs=this.db.get(query);
	
			query="select pk_Seq,ten,DIENGIAI from KENHBANHANG where TRANGTHAI=1 ";
			this.kbhRs = this.db.get(query);	
	
	
			query="select PK_SEQ,TEN from tinhthanh   where 1=1 ";
			if(vungId.length()>0)
				query+=" and vung_fk='"+vungId+"'";
			this.ttRs= this.db.get(query);
	
			query="select pk_seq ,ten,VUNG_FK from khuvuc  where 1=1 ";
			this.kvRs=this.db.get(query);
	
			query="select pk_seq,ten from vung where 1=1 ";
			this.vungRs=this.db.get(query);
	
	
			query="select pk_seq,ma,diachi,ten from nhaphanphoi where trangthai=1 ";
			if(this.view.length()>0)
			{
				query+="and pk_Seq in "+util.quyen_npp(userId)+"" ;
			}
	
			if(this.ttId.length()>0)
				query+=" and tinhthanh_Fk='"+this.ttId+"' ";
	
			if(this.vungId.length()>0)
				query+=" and khuvuc_fk in (select PK_SEQ from khuvuc where vung_fk='"+this.vungId+"' ) ";		
			this.nppRs=this.db.get(query);
			
			query = "select * from PHIEUXUATKHO_XOA";
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				while(rs.next())
				{
					this.pxkxoa += rs.getString("sochungtu") + ",";
				}
				if(this.pxkxoa.length() > 0)
					this.pxkxoa = this.pxkxoa.substring(0, this.pxkxoa.length() - 1);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	String queryHd="";
	public String getQueryHd()
	{
		return queryHd;
	}
	public void setQueryHd(String query)
	{
		this.queryHd=query;
	}

	Utility Ult = new Utility();
	public void init(String query, String order)
	{
		this.hoadonRs=super.createSplittingData(50, super.getSplittings(), order, query);
		/*OracleConnUtils SYNC = new OracleConnUtils();
		this.hoadonRs = SYNC.get(query);*/
		createRs();
	}
	
	public void initDisplay()
	{
		OracleConnUtils SYNC = new OracleConnUtils();
		String query = 
				" 	select a.*, c.tennpp "+ 
				"	from apps.TBL_PHIEUXUATKHO a inner join apps.V_DONHANG b on a.soso = b.soso " +
				"	inner join apps.V_KHACHHANGERP c on a.MANHAPP = c.MANPP "+
				"   where 1=1 and a.loai_phieu like N'%PHIEU XUAT KHO%'  "+
				"	and a.SOCHUNGTU = '"+this.hoadon+"' and a.soso = '"+this.soId+"'";
		this.hoadonRs = SYNC.get(query);
	}

	String view;
	static String msg;
	public String getMsg()
	{
		return msg;
	}
	public void setMsg(String msg)
	{
		this.msg = msg;
	}
	public String getView()
	{
		return view;
	}
	public void setView(String view)
	{
		this.view = view;
	}

	String kbhId;
	ResultSet kbhRs;
	public String getKbhId()
	{
		return kbhId;
	}
	public void setKbhId(String kbhId)
	{
		this.kbhId = kbhId;
	}
	public ResultSet getKbhRs()
	{
		return kbhRs;
	}
	public void setKbhRs(ResultSet kbhRs)
	{
		this.kbhRs = kbhRs;
	}

	public void setTotal_Query(String searchquery) 
	{

		if(this.action.length()>0)
		{
			String	query=
					"	select   \n "+      
							"   SUM(SoLuong)as SoLuong,AVG(DonGia) as DonGia,SUM(AVAT)as AVAT,SUM(BVAT)as BVAT,SUM(VAT)as VAT   \n "+      
							"   from   \n "+      
							"   (   "+searchquery+"   ) as HD   \n ";
			this.totalRs= db.get(query);
		}
	}


	ResultSet totalRs;
	public ResultSet getTotalRs()
	{
		return totalRs;
	}
	public void setTotalRs(ResultSet totalRs)
	{
		this.totalRs=totalRs;
	}

	String vungId,nhomId,ttId,kvId;
	ResultSet vungRs,nhomRs,ttRs,kvRs;
	public String getKvId()
	{
		return kvId;
	}
	public void setKvId(String kvId)
	{
		this.kvId = kvId;
	}
	public ResultSet getKvRs()
	{
		return kvRs;
	}
	public void setKvRs(ResultSet kvRs)
	{
		this.kvRs = kvRs;
	}
	public String getVungId()
	{
		return vungId;
	}
	public void setVungId(String vungId)
	{
		this.vungId = vungId;
	}
	public String getNhomId()
	{
		return nhomId;
	}
	public void setNhomId(String nhomId)
	{
		this.nhomId = nhomId;
	}
	public String getTtId()
	{
		return ttId;
	}
	public void setTtId(String ttId)
	{
		this.ttId = ttId;
	}
	public ResultSet getVungRs()
	{
		return vungRs;
	}
	public void setVungRs(ResultSet vungRs)
	{
		this.vungRs = vungRs;
	}
	public ResultSet getNhomRs()
	{
		return nhomRs;
	}
	public void setNhomRs(ResultSet nhomRs)
	{
		this.nhomRs = nhomRs;
	}
	public ResultSet getTtRs()
	{
		return ttRs;
	}
	public void setTtRs(ResultSet ttRs)
	{
		this.ttRs = ttRs;
	}

	ResultSet nppRs;
	@Override
	public ResultSet getNppRs()
	{
		return nppRs;
	}
	@Override
	public void setNppRs(ResultSet nppRs)
	{
		this.nppRs=nppRs;

	}

	String loaiHoaDon;
	public String getLoaiHoaDon()
	{
		return loaiHoaDon;
	}
	public void setLoaiHoaDon(String loaiHoaDon)
	{
		this.loaiHoaDon = loaiHoaDon;
	}


	String action;
	public String get_Action()
	{
		return action;
	}
	public void set_Action(String timkiem)
	{
		this.action = timkiem;
	}

	String soId,hoadon;
	@Override
	public String getSoId()
	{
		return soId;
	}
	@Override
	public void setSoId(String soId)
	{
		this.soId = soId;
	}
	@Override
	public String getSohoadon()
	{
		return this.hoadon;
	}
	@Override
	public void setSohoadon(String sohoadon)
	{
		this.hoadon = sohoadon;
	}

	String activeTab="";
	public String getActiveTab() {

		return this.activeTab;
	}

	public void setActiveTab(String active) 
	{
		this.activeTab= active ;
	}

	String InvoiceId="";
	@Override
	public String getInvoiceId() {
		return InvoiceId;
	}
	@Override
	public void setInvoiceId(String InvoiceId) {
		this.InvoiceId =InvoiceId;
	}

	String Invoice_RevertFor="";
	@Override
	public String getInvoice_RevertFor() {
		return Invoice_RevertFor;
	}
	@Override
	public void setInvoice_RevertFor(String Invoice_RevertFor) 
	{
		this.Invoice_RevertFor =Invoice_RevertFor;
	}

	String LegalNumber="";
	@Override
	public String getLegalNumber() {

		return LegalNumber;
	}
	@Override
	public void setLegalNumber(String LegalNumber) 
	{
		this.LegalNumber =LegalNumber;
	}
	String CustomerId="";
	@Override
	public String getCustomerId() {

		return CustomerId;
	}
	@Override
	public void setCustomerId(String CustomerId) 
	{
		this.CustomerId =CustomerId;		
	}

	String CustomerName="";
	@Override
	public String getCustomerName() 
	{
		return CustomerName;
	}
	@Override
	public void setCustomerName(String CustomerName) 
	{
		this.CustomerName  =CustomerName;
	}

	String InVoiceDate="";
	@Override
	public String getInVoiceDate() {

		return InVoiceDate;
	}
	@Override
	public void setInVoiceDate(String InVoiceDate) 
	{
		this.InVoiceDate =InVoiceDate;	
	}

	String InvoiceType="";
	@Override
	public String getInvoiceType() 
	{
		return InvoiceType;
	}
	@Override
	public void setInvoiceType(String InvoiceType) 
	{
		this.InvoiceType =InvoiceType;
	}
	@Override
	public String getSO_Number() {
		return SO_Number;
	}
	String SO_Number="";
	@Override
	public void setSO_Number(String SO_Number) {
		this.SO_Number=SO_Number;
	}

	String PO_Number="";
	@Override
	public String getPO_Number() {

		return PO_Number;
	}
	@Override
	public void setPO_Number(String PO_Number) {
		this.PO_Number =PO_Number;
	}

	String Status="";
	@Override
	public String getStatus() 
	{
		return Status;
	}
	@Override
	public void setStatus(String Status)
	{
		this.Status =Status;
	}

	public static void main(String[] arg)
	{
		
		//System.out.println(  setStatus() );
		/*dbutils db = new dbutils();
		OracleConnUtils SYNC = new OracleConnUtils();
		try
		{
			String query = 
					"	select distinct a.soso as so, b.sopo as po, a.ngaychungtu, a.manhapp as manpp, sochungtu, a.dvkinhdoanh as donvikinhdoanh, a.kenh, "+
					"	'100368' as nguoitao, '100368' as nguoisua  "+
					"	from apps.TBL_PHIEUXUATKHO a inner join apps.V_DONHANG b on a.soso=b.soso where a.trangthai=0 and a.loai_phieu like N'%PHIEU XUAT KHO%' ";
			System.out.println("[pxk] "+query);
			ResultSet rs=SYNC.get(query);

			if(rs != null)
			{
				while(rs.next())
				{
					String po = rs.getString("po");
					String so = rs.getString("so");
					System.out.println("[po] "+po+"; [so] "+so);
					if(po != null)
					{
						if(po.trim().equals("100731") && so.trim().equals("45351"))
							System.out.println("[co ton tai]");
					}
				}
			}
					
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		finally
		{
			SYNC.shutDown();
			db.shutDown();
		}*/
		////////////////
		dbutils db = new dbutils();
		OracleConnUtils SYNC = new OracleConnUtils(); 

		//Hashtable<String, String> htNppId=gethtbNpp(db);
		//<String, String> htKbhId=getHtbKenhId(db);

		String query="", sql = "";
		String LegalNumber_ThanhCong="";

		ResultSet rs;
		query = "select * from PHIEUXUATKHO_XOA";
		rs = db.get(query);
		String pxkxoa = "";
		if(rs != null)
		{
			try 
			{
				while(rs.next())
				{
					pxkxoa += rs.getString("sochungtu") + ",";
				}
				if(pxkxoa.length() > 0)
					pxkxoa = pxkxoa.substring(0, pxkxoa.length() - 1);
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		/*String pxkxoa = "", pxkdanhap = "";
		query = "select * from PHIEUXUATKHO_XOA";
		rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				while(rs.next())
				{
					pxkxoa += "'"+rs.getString("sochungtu") + "',";
				}
				if(pxkxoa.length() > 0)
					pxkxoa = pxkxoa.substring(0, pxkxoa.length() - 1);
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		query = "select a.* from lognhap a inner join nhaphang b on a.NHAPHANG_FK = b.PK_SEQ \n"+ 
				"inner join \n"+
				"( select NPP_FK, MAX(NGAYKS) ngayks from KHOASONGAY group by NPP_FK) c on b.NPP_FK = c.NPP_FK \n"+
				"where left(ngaynhan, 10) >= ngayks";
		rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				while(rs.next())
				{
					pxkdanhap += "'"+rs.getString("sochungtu") + "',";
				}
				if(pxkdanhap.length() > 0)
					pxkdanhap = pxkdanhap.substring(0, pxkdanhap.length() - 1);
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}*/
		//Những HD có thể tạo nhận hàng cho npp.
		query=
				"	select distinct a.soso as so, b.sopo as po, a.ngaychungtu, a.manhapp as manpp, sochungtu, a.dvkinhdoanh as donvikinhdoanh, a.kenh, "+
				"	'100368' as nguoitao, '100368' as nguoisua  "+
				"	from apps.TBL_PHIEUXUATKHO a inner join apps.V_DONHANG b on a.soso=b.soso where a.trangthai=0 and a.loai_phieu like N'%PHIEU XUAT KHO%'  " +
				" AND to_char(a.ngaychungtu,'YYYY-MM-DD')  >= '2016-12-01' ";
		//if(pxkxoa.trim().length() > 0 || pxkdanhap.trim().length() > 0)
			//query += "	and a.sochungtu not in ("+pxkxoa+pxkdanhap+")";
		query += "   order by a.ngaychungtu desc ";
		System.out.println("[pxk] "+query);
		rs=SYNC.get(query);
		
		if(rs != null)
		{
			try 
			{
				int stt = 1;
				while(rs.next())
				{
					db.getConnection().setAutoCommit(false);
					int flag=0;
					String ngaytra=rs.getString("ngaychungtu");
					String nguoitao=rs.getString("nguoitao");
					String nguoisua=rs.getString("nguoisua");
					String po = rs.getString("po");
					int Trangthai=0;
					String npp_fk=rs.getString("manpp");
					String ncc_fk="100022";
					String so=rs.getString("so");
					String sochungtu=rs.getString("sochungtu");
					String donvikinhdoanh=rs.getString("donvikinhdoanh");
					String kbh=rs.getString("kenh");
					
					//if(sochungtu.equals("PX.SOI.400102.1611.00107") || sochungtu.equals("PX.SOI.400102.1611.00160") )
					//if(sochungtu.equals("PX.SOI.306101.1605.00036"))
					if(pxkxoa.indexOf(sochungtu) < 0)
					{
						//System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
						//System.out.println("[ngaychungtu] "+ngaytra+", [sopo] "+po);
						if((ngaytra.compareTo("2015-12-01") >= 0 && ngaytra.compareTo("2016-01-31") <= 0) || (po != null && po.trim().length() > 0))
						{
							query = "select 1 from PHIEUXUATKHO_XOA where SOCHUNGTU = '"+sochungtu+"' " +
									"union " +
									"select 1 from lognhap a inner join nhaphang b on a.NHAPHANG_FK = b.PK_SEQ where SOCHUNGTU = '"+sochungtu+"'";
							ResultSet rs_check = db.get(query);
							if(rs_check.next() == false)
							{
								sql = "select  (select count(*) as sl from nhaphang where chungtu = '"+sochungtu+"'  ) as sl, "+
									  "(select pk_seq from nhaphang where chungtu = '"+sochungtu+"' ) as pk_seq,(select chungtu from nhaphang where chungtu = '"+sochungtu+"' ) as so";
								System.out.println("[check sochungtu] "+sql);
								ResultSet rscheck=db.get(sql);
								
								rscheck.next();
								int check=rscheck.getInt("sl");
								String socheck=rscheck.getString("so");
								String nhaphang_fk=rscheck.getString("pk_seq");
								if(check==0)
								{
									sql = "insert into nhaphang(ngaynhan, ngaychungtu, nguoitao, nguoisua, trangthai, npp_fk, ncc_fk, chungtu, hdtaichinh, SO_NUMBER , dondathang_fk, ngaytao, ngaysua, created_date, dvkd_fk, kbh_fk) "+
										  "values ('"+ngaytra+"', '"+ngaytra+"', '"+nguoitao+"', '"+nguoisua+"', "+Trangthai+", (select pk_seq from nhaphanphoi where ma='"+npp_fk+"'), "+
										  "'"+ncc_fk+"', '"+sochungtu+"', '"+sochungtu+"','"+so+"', "+po+", '"+getDateTime()+"', '"+getDateTime()+"', dbo.GetLocalDate(DEFAULT), "+
										  "(select pk_seq from donvikinhdoanh where donvikinhdoanh like N'"+donvikinhdoanh+"'), (select pk_seq from kenhbanhang where diengiai like N'"+kbh+"'))";
								}
								else
								{
									sql= "update nhaphang set trangthai = "+Trangthai+", ngaynhan='"+ngaytra+"', ngaychungtu = '"+ngaytra+"', ngaysua = '"+getDateTime()+"', created_date = dbo.GetLocalDate(DEFAULT), "+
										 "dvkd_fk = (select pk_seq from donvikinhdoanh where donvikinhdoanh like N'"+donvikinhdoanh+"'), kbh_fk = (select pk_seq from kenhbanhang where diengiai like N'"+kbh+"'), "+
										 "npp_fk= (select pk_seq from nhaphanphoi where ma='"+npp_fk+"'), chungtu='"+sochungtu+"', hdtaichinh='"+sochungtu+"' where chungtu = '"+sochungtu+"' and trangthai = 0";
								}
								rscheck.close();
								//System.out.println("[nhaphang] "+sql);
								if(db.updateReturnInt(sql)<=0)
								{
									flag=1;
								}
								
								if(check==0)
								{
									sql="select scope_identity() as dhid";
									ResultSet rs11=db.get(sql);
									rs11.next();
									nhaphang_fk=rs11.getString("dhid");
									rs11.close();
								}
								else
								{
									sql="delete from nhaphang_sp where nhaphang_fk="+nhaphang_fk;
									if(db.updateReturnInt(sql)<=0)
									{
										flag=1;
									}
								}
								if(flag==0)
								{
									OracleConnUtils SYNC1 = new OracleConnUtils(); 
									sql = "select a.kenh, a.manhapp as manpp, a.soluong, a.dvt as donvitinh, a.masp as masanpham, a.sheme as scheme, "+
										  "a.dongia, a.thanhtien, a.thue, a.tongcong "+
										  "from apps.TBL_PHIEUXUATKHO a where a.soso='"+so+"' and a.sochungtu = '"+sochungtu+"'";
									ResultSet rs1 = SYNC1.get(sql);
									
									while(rs1.next())
									{
										String kenh=rs1.getString("kenh");
										String manpp=rs1.getString("manpp");
										String soluong=rs1.getString("soluong");
										double soluongnhan=0;
										String masanpham=rs1.getString("masanpham");
										String donvi=rs1.getString("donvitinh");
										double dongia=rs1.getDouble("dongia");
										double thanhtien=rs1.getDouble("thanhtien");
										double thue=rs1.getDouble("thue");
										double tongcong=rs1.getDouble("tongcong");
										String scheme=rs1.getString("scheme")==null?"":rs1.getString("scheme");
										String kho_fk="100000";
										
										sql = "select isnull(d.donvi, '') dvdl1, isnull(soluong1,0) soluong1, isnull(c.donvi, '') dvdl2, isnull(soluong2, 0) soluong2 "+
										      "from quycach a inner join sanpham b on a.sanpham_fk = b.pk_seq "+
											  "inner join donvidoluong c on a.dvdl2_fk = c.pk_seq "+
											  "inner join donvidoluong d on a.dvdl1_fk = d.pk_seq "+
											  "where b.ma = '"+masanpham+"' and c.donvi = '"+donvi+"'";
										//System.out.println("[quycach] "+sql);
										ResultSet rs2 = db.get(sql);
										if(rs2 != null)
										{
											if(rs2.next())
											{
												String dvdl2 = rs2.getString("dvdl2"); 
												if(!dvdl2.equals("") && dvdl2.equals(donvi))
												{
													soluongnhan = Double.parseDouble(soluong)*rs2.getDouble("soluong1")/rs2.getDouble("soluong2");
												}
												else if(!dvdl2.equals("") && !dvdl2.equals(donvi))
												{
													flag=1;
												}
												else
													soluongnhan = Double.parseDouble(soluong);
											}
											else
												soluongnhan = Double.parseDouble(soluong);
										}
										if(scheme.trim().length()>0)
										{
											String sqlkm = "select "+ 
														   "(select count(*) as sl from ctkhuyenmai where SCHEMEERP=N'"+scheme+"')  as sl, "+
														   "(select top(1) KHO_FK from ctkhuyenmai where SCHEMEERP=N'"+scheme+"') as kho_fk";
											ResultSet rskm=db.get(sqlkm);
											rskm.next();
											if(rskm.getInt("sl")==0)
											{
												//System.out.println("sp scheme ko ton tai"+scheme +" so so la "+so);
												flag=1;
											}
											else
											{
												kho_fk=rskm.getString("kho_fk");
											}
										}
										sql="insert into NHAPHANG_SP(nhaphang_fk, sanpham_fk, soluong, soluongnhan, donvi, gianet, tienbvat, vat, tienavat, kbh_fk, kho_fk, scheme) "+
											"values ("+nhaphang_fk+", '"+masanpham+"', '"+soluong+"', '"+soluongnhan+"', '"+donvi+"', '"+dongia+"', '"+thanhtien+"', '"+thue+"', '"+tongcong+"', "+
											"(select pk_seq from kenhbanhang where diengiai like N'"+kenh+"'),'"+kho_fk+"',N'"+scheme+"') ";
										System.out.println("[NHAPHANG_SP] "+sql);
										if(db.updateReturnInt(sql)<=0 )
										{
											flag=1;
										}
									}
									SYNC1.shutDown();
								}
								
								if(flag==1)
								{
									db.getConnection().rollback();
									db.getConnection().setAutoCommit(true);
									//System.out.println("vao rollback");
								}
								else
								{
									db.getConnection().commit();
									db.getConnection().setAutoCommit(true);
									//LegalNumber_ThanhCong += nhaphang_fk + ", ";
									//System.out.println("vao commit");
								}
							}
							if(rs_check != null)
								rs_check.close();
						}
					}
					System.out.println("end " + stt);
					stt++;
				}			
	
			} catch (Exception e) 
			{
				System.out.println("loi o day: ");
				e.printStackTrace();
				
			}
			finally
			{
				SYNC.shutDown();
				db.shutDown();
			}
		}
	}
	
	
	@Override
	public String Save() 
	{
		dbutils db = new dbutils();
		OracleConnUtils SYNC = new OracleConnUtils(); 

		Hashtable<String, String> htNppId=gethtbNpp(db);
		Hashtable<String, String> htKbhId=getHtbKenhId(db);

		String query="", sql = "";
		String LegalNumber_ThanhCong="";

		ResultSet rs;
		query = "select * from PHIEUXUATKHO_XOA";
		rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				while(rs.next())
				{
					this.pxkxoa += rs.getString("sochungtu") +  ",";
				}
				if(this.pxkxoa.length() > 0)
					this.pxkxoa = this.pxkxoa.substring(0, this.pxkxoa.length() - 1);
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		/*String pxkxoa = "", pxkdanhap = "";
		query = "select * from PHIEUXUATKHO_XOA";
		rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				while(rs.next())
				{
					pxkxoa += "'"+rs.getString("sochungtu") + "',";
				}
				if(pxkxoa.length() > 0)
					pxkxoa = pxkxoa.substring(0, pxkxoa.length() - 1);
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}*/
		String pxkdanhap = "";
		query = "select a.* from lognhap a inner join nhaphang b on a.NHAPHANG_FK = b.PK_SEQ \n"+ 
				"inner join \n"+
				"( select NPP_FK, MAX(NGAYKS) ngayks from KHOASONGAY group by NPP_FK) c on b.NPP_FK = c.NPP_FK \n"+
				"where left(ngaynhan, 10) >= ngayks";
		rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				while(rs.next())
				{
					pxkdanhap += "'"+rs.getString("sochungtu") + "',";
				}
				if(pxkdanhap.length() > 0)
					pxkdanhap = pxkdanhap.substring(0, pxkdanhap.length() - 1);
			}catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		//Những HD có thể tạo nhận hàng cho npp.
		query=
				"	select distinct a.soso as so, b.sopo as po, a.ngaychungtu, a.manhapp as manpp, sochungtu, a.dvkinhdoanh as donvikinhdoanh, a.kenh, "+
				"	'100368' as nguoitao, '100368' as nguoisua  "+
				"	from apps.TBL_PHIEUXUATKHO a inner join apps.V_DONHANG b on a.soso=b.soso where a.trangthai=0 and a.loai_phieu like N'%PHIEU XUAT KHO%'  " +
				" AND to_char(a.ngaychungtu,'YYYY-MM-DD')  >= '2018-01-01' and a.trangthai=0 ";
		/*if(pxkxoa.trim().length() > 0 )
			query += "	and a.sochungtu not in ("+pxkxoa+")";*/
		query += "   order by a.ngaychungtu desc ";
		System.out.println("[pxk] "+query);
		rs=SYNC.get(query);
		
		if(rs != null)
		{
			try 
			{
				while(rs.next())
				{
					try{
						db.getConnection().setAutoCommit(false);
						int flag=0;
						String ngaytra=rs.getString("ngaychungtu");
						String nguoitao=rs.getString("nguoitao");
						String nguoisua=rs.getString("nguoisua");
						String po = rs.getString("po");
						int Trangthai=0;
						String npp_fk=rs.getString("manpp");
						String ncc_fk="100022";
						String so=rs.getString("so");
						String sochungtu=rs.getString("sochungtu");
						String donvikinhdoanh=rs.getString("donvikinhdoanh");
						String kbh=rs.getString("kenh");
						System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaanbbb");
						//if(sochungtu.equals("PX.SOI.400102.1611.00107") || sochungtu.equals("PX.SOI.400102.1611.00160") )
						//if(sochungtu.equals("PX.SOI.306101.1605.00036"))
						if(this.pxkxoa.indexOf(sochungtu) < 0 && pxkdanhap.indexOf(sochungtu) < 0)
						{
							System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
							//System.out.println("[ngaychungtu] "+ngaytra+", [sopo] "+po);
							if((ngaytra.compareTo("2015-12-01") >= 0 && ngaytra.compareTo("2016-01-31") <= 0) || (po != null && po.trim().length() > 0))
							{
								query = "select 1 from PHIEUXUATKHO_XOA where SOCHUNGTU = '"+sochungtu+"' " +
										"union " +
										"select 1 from lognhap a inner join nhaphang b on a.NHAPHANG_FK = b.PK_SEQ where SOCHUNGTU = '"+sochungtu+"' and soso = '"+so+"'";
								ResultSet rs_check = this.db.get(query);
								if(rs_check.next() == false)
								{
									sql = "select  (select count(*) as sl from nhaphang where chungtu = '"+sochungtu+"' and so_number = '"+so+"'  ) as sl, "+
										  "(select pk_seq from nhaphang where chungtu = '"+sochungtu+"' and so_number = '"+so+"') as pk_seq,(select distinct chungtu from nhaphang where chungtu = '"+sochungtu+"' ) as so";
									System.out.println("[check sochungtu] "+sql);
									ResultSet rscheck=db.get(sql);
									
									rscheck.next();
									int check=rscheck.getInt("sl");
									String socheck=rscheck.getString("so");
									String nhaphang_fk=rscheck.getString("pk_seq");
									if(check==0)
									{
										sql = "insert into nhaphang(ngaynhan, ngaychungtu, nguoitao, nguoisua, trangthai, npp_fk, ncc_fk, chungtu, hdtaichinh, SO_NUMBER , dondathang_fk, ngaytao, ngaysua, created_date, dvkd_fk, kbh_fk) "+
											  "values ('"+ngaytra+"', '"+ngaytra+"', '"+nguoitao+"', '"+nguoisua+"', "+Trangthai+", (select pk_seq from nhaphanphoi where ma='"+npp_fk+"'), "+
											  "'"+ncc_fk+"', '"+sochungtu+"', '"+sochungtu+"','"+so+"', "+po+", '"+getDateTime()+"', '"+getDateTime()+"', dbo.GetLocalDate(DEFAULT), "+
											  "(select pk_seq from donvikinhdoanh where donvikinhdoanh like N'"+donvikinhdoanh+"'), (select pk_seq from kenhbanhang where diengiai like N'"+kbh+"'))";
									}
									else
									{
										sql= "update nhaphang set trangthai = "+Trangthai+", ngaynhan='"+ngaytra+"', ngaychungtu = '"+ngaytra+"', ngaysua = '"+getDateTime()+"', created_date = dbo.GetLocalDate(DEFAULT), "+
											 "dvkd_fk = (select pk_seq from donvikinhdoanh where donvikinhdoanh like N'"+donvikinhdoanh+"'), kbh_fk = (select pk_seq from kenhbanhang where diengiai like N'"+kbh+"'), "+
											 "npp_fk= (select pk_seq from nhaphanphoi where ma='"+npp_fk+"'), chungtu='"+sochungtu+"', hdtaichinh='"+sochungtu+"' where chungtu = '"+sochungtu+"' and trangthai = 0 and so_number = '"+so+"'";
									}
									rscheck.close();
									//System.out.println("[nhaphang] "+sql);
									if(db.updateReturnInt(sql)<=0)
									{
										flag=1;
									}
									
									if(check==0)
									{
										sql="select scope_identity() as dhid";
										ResultSet rs11=db.get(sql);
										rs11.next();
										nhaphang_fk=rs11.getString("dhid");
										rs11.close();
									}
									else
									{
										sql="delete from nhaphang_sp where nhaphang_fk="+nhaphang_fk;
										if(db.updateReturnInt(sql)<=0)
										{
											flag=1;
										}
									}
									if(flag==0)
									{
										OracleConnUtils SYNC1 = new OracleConnUtils(); 
										sql = "select a.kenh, a.manhapp as manpp, a.soluong, a.dvt as donvitinh, a.masp as masanpham, a.sheme as scheme, "+
											  "a.dongia, a.thanhtien, a.thue, a.tongcong "+
											  "from apps.TBL_PHIEUXUATKHO a where a.soso='"+so+"' and a.sochungtu = '"+sochungtu+"'";
										ResultSet rs1 = SYNC1.get(sql);
										
										while(rs1.next())
										{
											String kenh=rs1.getString("kenh");
											String manpp=rs1.getString("manpp");
											String soluong=rs1.getString("soluong");
											double soluongnhan=0;
											String masanpham=rs1.getString("masanpham");
											String donvi=rs1.getString("donvitinh");
											double dongia=rs1.getDouble("dongia");
											double thanhtien=rs1.getDouble("thanhtien");
											double thue=rs1.getDouble("thue");
											double tongcong=rs1.getDouble("tongcong");
											String scheme=rs1.getString("scheme")==null?"":rs1.getString("scheme");
											String kho_fk="100000";
											
											sql = "select isnull(d.donvi, '') dvdl1, isnull(soluong1,0) soluong1, isnull(c.donvi, '') dvdl2, isnull(soluong2, 0) soluong2 "+
											      "from quycach a inner join sanpham b on a.sanpham_fk = b.pk_seq "+
												  "inner join donvidoluong c on a.dvdl2_fk = c.pk_seq "+
												  "inner join donvidoluong d on a.dvdl1_fk = d.pk_seq "+
												  "where b.ma = '"+masanpham+"' and c.donvi = '"+donvi+"'";
											//System.out.println("[quycach] "+sql);
											ResultSet rs2 = db.get(sql);
											if(rs2 != null)
											{
												if(rs2.next())
												{
													String dvdl2 = rs2.getString("dvdl2"); 
													if(!dvdl2.equals("") && dvdl2.equals(donvi))
													{
														soluongnhan = Double.parseDouble(soluong)*rs2.getDouble("soluong1")/rs2.getDouble("soluong2");
													}
													else if(!dvdl2.equals("") && !dvdl2.equals(donvi))
													{
														flag=1;
													}
													else
														soluongnhan = Double.parseDouble(soluong);
												}
												else
													soluongnhan = Double.parseDouble(soluong);
											}
											if(scheme.trim().length()>0)
											{
												String sqlkm = "select "+ 
															   "(select count(*) as sl from ctkhuyenmai where SCHEMEERP=N'"+scheme+"')  as sl, "+
															   "(select top(1) KHO_FK from ctkhuyenmai where SCHEMEERP=N'"+scheme+"') as kho_fk";
												ResultSet rskm=db.get(sqlkm);
												rskm.next();
												if(rskm.getInt("sl")==0)
												{
													//System.out.println("sp scheme ko ton tai"+scheme +" so so la "+so);
													flag=1;
												}
												else
												{
													kho_fk=rskm.getString("kho_fk");
												}
											}
											sql="insert into NHAPHANG_SP(nhaphang_fk, sanpham_fk, soluong, soluongnhan, donvi, gianet, tienbvat, vat, tienavat, kbh_fk, kho_fk, scheme) "+
												"values ("+nhaphang_fk+", '"+masanpham+"', '"+soluong+"', '"+soluongnhan+"', '"+donvi+"', '"+dongia+"', '"+thanhtien+"', '"+thue+"', '"+tongcong+"', "+
												"(select pk_seq from kenhbanhang where diengiai like N'"+kenh+"'),'"+kho_fk+"',N'"+scheme+"') ";
											System.out.println("[NHAPHANG_SP] "+sql);
											if(db.updateReturnInt(sql)<=0 )
											{
												flag=1;
											}
										}
										SYNC1.shutDown();
									}
									
									if(flag==1)
									{
										db.getConnection().rollback();
										db.getConnection().setAutoCommit(true);
										//System.out.println("vao rollback");
									}
									else
									{
										db.getConnection().commit();
										db.getConnection().setAutoCommit(true);
										//LegalNumber_ThanhCong += nhaphang_fk + ", ";
										//System.out.println("vao commit");
									}
								}
								if(rs_check != null)
									rs_check.close();
							}
						}
					} catch(Exception e)
					{
						db.getConnection().rollback();
						db.getConnection().setAutoCommit(true);
					}
					
				}			
	
			} catch (Exception e) 
			{
				System.out.println("loi o day: ");
				e.printStackTrace();
				return "Exception "+e.getMessage();
			}
			finally
			{
				SYNC.shutDown();
				db.shutDown();
			}
		}
		return "Tạo thành công các chứng từ "+LegalNumber_ThanhCong;
	}


	public String TaoDonHang()
	{
		dbutils db = new dbutils();
		db_Sync SYNC = new db_Sync();
		String DMS_PO_Number="";
		try 
		{

			String query="select [DMS_PO_Number] from [SAP_SO_REJECT] where [DMS_DonDatHang_FK] is null and NgayReject  is null and SoStatus='REJ' and Status='OPEN' ";
			ResultSet rs =SYNC.get(query);
			while(rs.next())
			{
				db.getConnection().setAutoCommit(false);	
				DMS_PO_Number =rs.getString("DMS_PO_Number");
				query=
						"		insert ERP_Dondathang(ngaydonhang, ngaydenghi, loaidonhang, ghichu, trangthai, dvkd_fk, kbh_fk, npp_fk, kho_fk, chietkhau, vat, ngaytao,  "+
								"			nguoitao, ngaysua, nguoisua,NguonGoc_TaoDH,Revert_DonDatHang_FK) "+
								"		select '"+getDateTime()+"','"+getDateTime()+"' ,0,N'Đơn hàng Reject ("+DMS_PO_Number+")' as GhiChu,4, "+
								"			a.DVKD_FK,a.KBH_FK,a.NPP_FK,a.Kho_FK,0 as ck,0 as VAT,'"+getDateTime()+"','"+userId+"','"+getDateTime()+"','"+userId+"',N'Reject "+DMS_PO_Number+" ','"+DMS_PO_Number+"' "+
								"		from dbo.ERP_Dondathang a  "+
								"		where PK_SEQ='"+DMS_PO_Number+"'  ";
				if(!db.update(query))
				{
					db.getConnection().rollback();
					return "Lỗi tạo đơn hàng Reject "+query;
				}

				String HeaderID=null;
				ResultSet rsDDH = db.get("select IDENT_CURRENT( 'ERP_Dondathang' ) as ID ");
				if(rsDDH.next())
				{
					HeaderID= rsDDH.getString("ID");
				}
				rsDDH.close();

				SYNC.getConnection().setAutoCommit(false);	
				query=
						"		insert dbo.ERP_Dondathang_SANPHAM( Dondathang_fk, SANPHAM_FK,  "+ 
								"			soluong, soluongttduyet, dvdl_fk,thueVAT,dongia,DonGiaGoc )  "+
								"		select  '"+HeaderID+"' , "+
								"				(select pk_seq from dbo.sanpham  where ma=dms.Product_ID) as SANPHAM_FK, "+ 
								"				(-1)*(ISNULL(dms.Quantity,0)-ISNULL(hd.Quantity,0)) as Quantity, "+
								"				(-1)*(ISNULL(dms.Quantity,0)-ISNULL(hd.Quantity,0)) as Quantity, "+
								"				(select dvdl_fk from dbo.sanpham  where ma=dms.Product_ID) as dvdl_fk, "+
								"				(select ptThue from dbo.sanpham  where ma=dms.Product_ID) as ptThue, "+
								"  isnull( ( select GIAMUANPP *(1-isnull(sp.ptChietKhau,0)/100) from  "+
								"				dbo.BGMUANPP_SANPHAM sp where SANPHAM_FK = dms.sanpham_fk and BGMUANPP_FK in "+ 
								"		(	select top(1) PK_SEQ from dbo.BANGGIAMUANPP bg  "+
								"				inner join dbo.BANGGIAMUANPP_NPP bg_npp on bg.PK_SEQ = bg_npp.BANGGIAMUANPP_FK "+ 
								"					where bg.TRANGTHAI = '1' and bg_npp.NPP_FK =  "+
								"					(  select NPP_FK from  dbo.ERP_DONDATHANG where PK_SEQ='"+DMS_PO_Number+"') "+ 					 					
								"					and bg.DVKD_FK =(  select DVKD_FK from  dbo.ERP_DONDATHANG where PK_SEQ='"+DMS_PO_Number+"')    "+
								"					and bg.KENH_FK = (  select KBH_FK from  dbo.ERP_DONDATHANG where PK_SEQ='"+DMS_PO_Number+"')  "+
								"						 order by bg.TUNGAY desc )  "+
								"		),0 ) as giamua, "+
								"		isnull( ( select GIAMUANPP from "+ 
								"				dbo.BGMUANPP_SANPHAM sp where SANPHAM_FK = dms.sanpham_fk and BGMUANPP_FK in "+ 
								"		(	select top(1) PK_SEQ from dbo.BANGGIAMUANPP bg  "+
								"				inner join dbo.BANGGIAMUANPP_NPP bg_npp on bg.PK_SEQ = bg_npp.BANGGIAMUANPP_FK "+ 
								"					where bg.TRANGTHAI = '1' and bg_npp.NPP_FK =  "+
								"					(  select NPP_FK from  dbo.ERP_DONDATHANG where PK_SEQ='"+DMS_PO_Number+"') "+ 										
								"					and bg.DVKD_FK =(  select DVKD_FK from  dbo.ERP_DONDATHANG where PK_SEQ='"+DMS_PO_Number+"')  "+
								"					and bg.KENH_FK = (  select KBH_FK from  dbo.ERP_DONDATHANG where PK_SEQ='"+DMS_PO_Number+"')  "+
								"						 order by bg.TUNGAY desc )  "+
								"		),0 ) "+
								"		from "+SYNC.DBNAME+".dbo.SAP_SO_REJECT sap "+
								"		inner join  "+
								"		( "+
								"			select b.dondathang_fk as PO_Number , c.MA as Product_ID ,SUM(b.soluongttduyet) as Quantity,b.sanpham_fk "+ 
								"			from dbo.ERP_DONDATHANG a  "+
								"				inner join dbo.ERP_DONDATHANG_SANPHAM b on b.dondathang_fk=a.PK_SEQ "+
								"				inner join dbo.sanpham c on c.PK_SEQ=b.sanpham_fk "+
								"			where a.PK_SEQ='"+DMS_PO_Number+"' "+
								"			group by b.dondathang_fk,c.MA,b.sanpham_fk "+
								"		)as dms on sap.DMS_PO_Number=dms.PO_Number "+
								"		left join  "+
								"		( "+
								"			select sum(InVoice_BVAT+Invoice_VAT) as Amount,PO_Number ,b.Product_ID,sum(b.Invoice_Quantity) as Quantity  "+
								"			from  "+SYNC.DBNAME+".dbo.SAP_Invoice_Header a inner join "+SYNC.DBNAME+".dbo.SAP_Invoice_Item b on b.InVoiceID=a.InvoiceId "+
								"			where a.PO_Number='"+DMS_PO_Number+"' "+
								"				and a.InvoiceId not in (select Invoice_RevertFor from DMSSAP.dbo.SAP_Invoice_Header where Invoice_RevertFor is not null) "+
								"			group by b.Product_ID,a.PO_Number "+
								"		)as hd on hd.PO_Number=sap.DMS_PO_Number and dms.Product_ID=hd.Product_ID "+
								"		where ISNULL(dms.Quantity,0)-ISNULL(hd.Quantity,0)>0  ";
				if(!db.update(query))
				{
					db.getConnection().rollback();
					return "Lỗi tạo đơn hàng Reject "+query;
				}
				query ="update [SAP_SO_REJECT] set [DMS_DonDatHang_FK]="+HeaderID+" ,[NgayReject]=dbo.GetLocalDate(DEFAULT) where DMS_PO_Number='"+DMS_PO_Number+"' ";
				if(!SYNC.update(query))
				{
					db.getConnection().rollback();
					SYNC.getConnection().rollback();
					return "Lỗi tạo đơn hàng Reject "+query;
				}

				/*query=
					" insert into SAP_Invoice_Header_Log (charnnel, invoiceid, invoice_revertfor, legalnumber, customerid, customername, invoi)  "+
					" from  "+SYNC.DBNAME+".dbo.SAP_Invoice_Header a inner join "+SYNC.DBNAME+".dbo.SAP_Invoice_Item b on b.InVoiceID=a.InvoiceId "+
					"			where a.PO_Number='"+DMS_PO_Number+"' "+
					"				and a.InvoiceId not in (select Invoice_RevertFor from DMSSAP.dbo.SAP_Invoice_Header where Invoice_RevertFor is not null) "+
					"			group by b.Product_ID,a.PO_Number";*/
				
				db.getConnection().commit();
				db.getConnection().setAutoCommit(true);	

				SYNC.getConnection().commit();
				SYNC.getConnection().setAutoCommit(true);	
			}
		} catch (SQLException e) 
		{
			e.printStackTrace();
			return "Lỗi tạo đơn hàng Reject "+e.getMessage();
		}
		finally
		{
			db.shutDown();
			SYNC.shutDown();
		}
		return "";

	}



	public static Hashtable<String, String> gethtbNpp(dbutils db)
	{
		Hashtable<String, String> htb = new Hashtable<String, String>();
		ResultSet rs = db.get("select pk_seq,Ma from nhaphanphoi");
		try
		{
			while (rs.next())
			{
				htb.put(rs.getString("Ma"), rs.getString("pk_seq"));
			}
			rs.close();
		} catch (Exception e)
		{
			System.out.println("Nhung Loi Thong Thuong NPP : " + e.toString());
		}
		return htb;
	}


	public static Hashtable<String, String> getHtbKenhId(dbutils db)
	{
		Hashtable<String, String> htb = new Hashtable<String, String>();
		ResultSet rs = db.get("select PK_SEQ,SMARTID from KENHBANHANG");
		try
		{
			while (rs.next())
			{
				htb.put(rs.getString("SMARTID"), rs.getString("PK_SEQ"));
			}
			rs.close();
		} catch (Exception e)
		{
			System.out.println("Nhung Loi Thong Thuong NPP : " + e.toString());
		}
		return htb;
	}

	//@Override
	private static String setStatus() 
	{
		dbutils db = new dbutils();
		db_Sync SYNC = new db_Sync(); 

		Hashtable< String, String> htNppId=gethtbNpp(db);
		Hashtable< String, String> htKbhId=getHtbKenhId(db);

		String query="";

		query=
				"	SELECT [Charnnel],[InvoiceId],[Invoice_RevertFor],[LegalNumber],[CustomerId],[CustomerName], " +
						"  convert(varchar(10), CAST( InVoiceDate as datetime),120) as [InVoiceDate]   "+
						"		      ,[InvoiceType],[InVoice_BVAT],[Invoice_VAT],[SO_Number],[PO_Number] "+
						"		  FROM [SAP_Invoice_Header]" +
						"  where isnull(Readed,0) <>1  ";
		ResultSet rs=SYNC.get(query);


		try 
		{
			while(rs.next())
			{

				db.getConnection().setAutoCommit(false);
				SYNC.getConnection().setAutoCommit(false);

				String Charnnel=rs.getString("Charnnel");
				String InvoiceId=rs.getString("InvoiceId");
				String Invoice_RevertFor=rs.getString("Invoice_RevertFor");
				String LegalNumber=rs.getString("LegalNumber");
				String CustomerId=rs.getString("CustomerId");
				String CustomerName=rs.getString("CustomerName");
				String InVoiceDate=rs.getString("InVoiceDate");
				String InvoiceType=rs.getString("InvoiceType");
				double InVoice_BVAT=rs.getDouble("InVoice_BVAT");
				double Invoice_VAT=rs.getDouble("Invoice_VAT");
				double Invoice_AVAT =InVoice_BVAT+Invoice_VAT;
				String SO_Number=rs.getString("SO_Number");
				String PO_Number=rs.getString("PO_Number");		
				String nppId = htNppId.get(CustomerId);

				String userId="100368";
				String nccId="100022";
				String kbhId =htKbhId.get(Charnnel);

				if(nppId!=null)
				{

					System.out.println("[nppId]"+nppId);
					query=
							" insert into nhaphang(NGAYNHAN,SOTIENBVAT,NGUOITAO,NGUOISUA,TRANGTHAI,NPP_FK,NCC_FK,VAT,SOTIENAVAT,DVKD_FK,KBH_FK,CHUNGTU,NGAYCHUNGTU, "+
									"		HDTAICHINH,LOAIHOADON,NGAYTAO,NGAYSUA,SO_Number,PO_Number,InvoiceId,Invoice_RevertFor,InvoiceType,CustomerName) " +
									"  select    ?, ?, ?, ? ,? ,? , ?, ? , ?, ? , ? ,? , ?, ? , ?, ? ,? ,? , ?, ?, ?, ?, ?    ";
					int	SoDong=0;	
					SoDong=	db.executeUpdate(query, InVoiceDate,InVoice_BVAT,userId,userId,0,nppId,nccId,Invoice_VAT,Invoice_AVAT,100068,kbhId,LegalNumber,InVoiceDate,LegalNumber,
							0,getDateTime(),getDateTime(),SO_Number,PO_Number,InvoiceId,Invoice_RevertFor,InvoiceType,CustomerName);

					if(SoDong!=1)
					{
						msg = "Không thể tạo nhập hàng!Vui lòng kiểm tra lại kết nối !"+query;
						SYNC.getConnection().rollback();
						db.getConnection().rollback();
						return msg;
					}
					System.out.println(SoDong +"[nhaphang]"+query);

					String NhapHangId ="";
					ResultSet rsDDH = db.get("select IDENT_CURRENT( 'nhaphang' ) as ID ");
					if(rsDDH.next())
					{
						NhapHangId = rsDDH.getString("ID");
					}
					rsDDH.close();

					query="update SAP_Invoice_Header set Readed='1',NhapHang_FK='"+NhapHangId+"',ReadedTime=dbo.GetLocalDate(DEFAULT) where InvoiceId='"+InvoiceId+"' ";
					System.out.println("[SAP_Invoice_Header]"+query);
					SoDong=SYNC.updateReturnInt(query);
					if(SoDong!=1)
					{
						msg = "Không thể tạo nhập hàng!Vui lòng kiểm tra lại kết nối !"+query;
						SYNC.getConnection().rollback();
						db.getConnection().rollback();
						return msg;
					}
					int nROW=0;
					int n_Row=0;
					query="select  (select count(*) from SAP_Invoice_Item where InVoiceID='"+InvoiceId+"') as nROW ,SO_NUMBER,InVoiceID,Line_Number,Product_ID,Product_Name,UOM_CODE,Invoice_Quantity,Price,DisCount,VAT,scheme "+  
							"	from SAP_Invoice_Item where InVoiceID='"+InvoiceId+"' ";
					ResultSet rsDetail =SYNC.get(query);
					while(rsDetail.next())
					{
						nROW=rsDetail.getInt("nROW");
						String SO_NUMBER=rsDetail.getString("SO_NUMBER");
						String InVoiceID=rsDetail.getString("InVoiceID");
						String Line_Number=rsDetail.getString("Line_Number");
						String Product_ID=rsDetail.getString("Product_ID");
						String Product_Name=rsDetail.getString("Product_Name");
						String UOM_CODE=rsDetail.getString("UOM_CODE");
						double Invoice_Quantity=rsDetail.getDouble("Invoice_Quantity");
						double Price=rsDetail.getDouble("Price");
						double DisCount=rsDetail.getDouble("DisCount");
						double VAT=rsDetail.getDouble("VAT");
						double BVAT= Invoice_Quantity*Price;
						double AVAT= VAT +BVAT;
						String scheme=rsDetail.getString("scheme");

						query=
								"	insert into nhaphang_sp(SO_NUMBER,InVoiceID,Line_Number,NHAPHANG_FK,SANPHAM_FK,soluong,DONVI,GIANET,TIENBVAT,VAT,TIENAVAT,GIAGROSS,SCHEME,ChietKhau)  " +
										"  select ?,?,?,?,?,?,?,?,?,?,?,?,?,?  " ;

						SoDong=	db.executeUpdate(query, SO_NUMBER,InVoiceID,Line_Number,NhapHangId,Product_ID,Invoice_Quantity,UOM_CODE,Price,BVAT,VAT,AVAT,Price,scheme,DisCount );
						if(SoDong!=1)
						{
							msg = "Không thể tạo nhập hàng!Vui lòng kiểm tra lại kết nối !"+query;
							SYNC.getConnection().rollback();
							db.getConnection().rollback();
							return msg;
						}
						n_Row++;
					}

					if(nROW!=n_Row)
					{
						msg = "Không đọc đủ thông tin hóa đơn '"+InvoiceId+"' !";;
						SYNC.getConnection().rollback();
						db.getConnection().rollback();
						return msg;
					}
					SYNC.getConnection().commit();
					db.getConnection().commit();
				}			
			}
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		finally
		{
			SYNC.shutDown();
			db.shutDown();

		}
		return "";
	}


	private static String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);	
	}
	@Override
	public String XoaPXK() {
		dbutils db = new dbutils();
		OracleConnUtils SYNC = new OracleConnUtils(); 
		String query="";

		try 
		{
			db.getConnection().setAutoCommit(false);
			SYNC.getConnection().setAutoCommit(false);
			
			String[] s = this.pxkxoa.split(",");
			for(int i = 0; i < s.length; i++)
			{
				query = "update nhaphang set trangthai = 2 where trangthai = 0 and isnull(isdms,0) != 1 and CHUNGTU = '"+s[i].split(";")[0]+"' and SO_Number = '"+s[i].split(";")[1]+"'";
				if (this.db.updateReturnInt(query) != 1) {
					msg = "Không thể xóa phiếu xuất kho! "+query;
					db.getConnection().rollback();
					return msg;
				}
				
				query = "insert into PHIEUXUATKHO_XOA(sochungtu, soso, created_date) "+
						"select '"+s[i].split(";")[0]+"', '"+s[i].split(";")[1]+"', dbo.GetLocalDate(DEFAULT)";
				if (!db.update(query)) {
					msg = "Không thể xóa phiếu xuất kho! "+query;
					db.getConnection().rollback();
					return msg;
				}
				
				query = "update apps.tbl_phieuxuatkho set trangthai = 1 where sochungtu = '"+s[i].split(";")[0]+"' and soso = '"+s[i].split(";")[1]+"'";
				System.out.println("1.Update PXK: " + query);
				if(!SYNC.update(query)){
					msg="Không thể xóa phiếu xuất kho! "+ query;
					
					SYNC.update("rollback");
					geso.dms.center.util.Utility.rollback_throw_exception(db);
					
					return msg;
				}
			}
			
			SYNC.getConnection().commit();
			SYNC.getConnection().setAutoCommit(true);
			SYNC.shutDown();
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);

		} catch (Exception e) 
		{
			e.printStackTrace();
			return "Exception "+e.getMessage();
		}
		finally
		{
			db.shutDown();
		}
		
		return "Xóa thành công các chứng từ ";
	}
	@Override
	public String getPxkXoa() {
		return this.pxkxoa;
	}
	@Override
	public void setPxkXoa(String pxkxoa) {
		this.pxkxoa = pxkxoa;
	}

}
