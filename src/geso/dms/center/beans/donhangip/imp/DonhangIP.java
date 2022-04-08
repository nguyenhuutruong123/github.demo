package geso.dms.center.beans.donhangip.imp;

import java.io.Serializable;

import geso.dms.center.beans.donhangip.IDonhangIP;
import geso.dms.center.beans.donhangip.ISanphamIP;
import geso.dms.center.util.OracleConnUtils;
import geso.dms.distributor.beans.donhang.ISanpham;
import geso.dms.distributor.beans.donhang.imp.Sanpham;
import geso.dms.distributor.db.sql.*;
import geso.dms.distributor.util.FixData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Hashtable;

import org.apache.xmlbeans.impl.regex.REUtil;


public class DonhangIP implements IDonhangIP, Serializable
{	
	private static final long serialVersionUID = -9217977546733610214L;

	String userId;
	String id; //ma don hang
	String ngaygiaodich;
	String daidienkd;
	String trangthai;
	String ngaytao;
	String nguoitao;
	String ngaysua;
	String nguoisua;
	String chietkhau;
	String tongchietkhau;
	String VAT;
	String diachigiaohang;
	String msg;
	String ngaygiao;
	String nppId;
	String nppTen;
	String sitecode;
	String muctindung;	

	String ddkdId;
	String gsbhId;

	String tbhId;
	String smartId;
	String khTen;
	ResultSet khlist;
	String khId;
	String bgstId;
	String sotiengiam;
	String khoId;
	ResultSet gsbhs;
	ResultSet kholist;
	ResultSet tbhlist;
	ResultSet ddkdlist;

	List<ISanphamIP> splist;
	float ttCKKM;
	float ttsauCKKM;
	float ttsauCK;
	String tienCK;
	String tongtientruocVAT;
	String tongtiensauVAT;
	String nvgnId;



	double nocu;

	Hashtable<String, Integer> spThieuList;

	///trakhuyen mai
	Hashtable<String, Float> scheme_tongtien = new Hashtable<String, Float>();
	Hashtable<String, Float> scheme_chietkhau = new Hashtable<String, Float>();
	List<ISanphamIP> scheme_sanpham = new ArrayList<ISanphamIP>();

	boolean aplaikm;
	boolean cokm;
	boolean chuaApkm;
	boolean dacoDh;
	boolean daxuatkhoChuachot;

	String ngayks;
	String ghichu =""; 
	dbutils db;

	public DonhangIP(String[] param)
	{
		this.id = param[0];
		this.khId = param[1];
		this.ngaygiaodich = param[2];
		this.nppTen = param[3];
		this.daidienkd = param[4];
		this.trangthai = param[5];
		this.ngaytao = param[6];
		this.nguoitao = param[7];
		this.ngaysua = param[8];
		this.nguoisua = param[9];	
		this.VAT = param[10];
		this.ddkdId = param[11];

		this.tbhId = "";
		this.gsbhId = "";
		this.chietkhau = "";
		this.tongchietkhau = "";
		this.diachigiaohang = "";
		this.bgstId = "0";
		this.khoId = "";
		this.msg = "";
		this.muctindung="0";
		this.spThieuList = new Hashtable<String, Integer>();
		this.aplaikm = false;
		this.cokm = false;
		this.chuaApkm = false;
		this.dacoDh = false;
		this.daxuatkhoChuachot = false;
		this.ngayks = "";
		this.ghichu = "";
		this.db = new dbutils();

	}

	public DonhangIP(String id)
	{
		this.id = id;
		this.khId = "";
		this.ngaygiaodich = "";
		this.diachigiaohang = "";
		this.ngaygiao="";
		this.nppTen = "";
		this.daidienkd = "";
		this.trangthai = "";
		this.ngaytao = "";
		this.nguoitao = "";
		this.ngaysua = "";
		this.nguoisua = "";	
		this.VAT = "";
		this.ddkdId = "";
		this.gsbhId = "";
		this.tbhId = "";
		this.chietkhau = "";
		this.tongchietkhau = "";
		this.tongtiensauVAT = "0";
		this.tongtientruocVAT ="0";
		this.ttsauCKKM = 0;
		this.ttCKKM = 0;
		this.ttsauCK = 0;
		this.nocu = 0;
		this.bgstId = "0";
		this.khoId = "";
		this.msg = "";
		this.khTen = "";
		this.smartId = "";
		this.muctindung="0";
		this.aplaikm = false;
		this.cokm = false;
		this.chuaApkm = false;
		this.dacoDh = false;
		this.daxuatkhoChuachot = false;
		this.spThieuList = new Hashtable<String, Integer>();
		this.ghichu = "";
		this.ngayks = "";
		this.db = new dbutils();	
	}

	public DonhangIP(String id, String nppId)
	{
		this.id = id;
		this.nppId = nppId;
		this.CreateSpList();
	}

	public String getNgaygiao() {
		return ngaygiao;
	}

	public void setNgaygiao(String ngaygiao) {
		this.ngaygiao = ngaygiao;
	}

	public String getUserId() 
	{		
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;		
	}

	public String getSmartId() 
	{		
		return this.smartId;
	}

	public void setSmartId(String smartId) 
	{
		this.smartId = smartId;		
	}

	public String getKhTen() 
	{		
		return this.khTen;
	}

	public void setKhTen(String khTen) 
	{
		this.khTen = khTen;		
	}

	public String getId() 
	{
		return this.id;
	}

	public void setId(String id) 
	{
		this.id = id;		
	}

	public String getKhId() 
	{	
		return this.khId;
	}

	public void setKhId(String khId) 
	{
		this.khId = khId;
	}

	public String getNgaygiaodich() 
	{	
		return this.ngaygiaodich;
	}

	public void setNgaygiaodich(String ngaygiaodich) 
	{
		this.ngaygiaodich = ngaygiaodich;		
	}

	public String getDaidienkd() 
	{	
		return this.daidienkd;
	}

	public void setDaidienkd(String daidienkd) 
	{
		this.daidienkd = daidienkd;		
	}

	public String getTrangthai()
	{	
		return this.trangthai;
	}

	public void setTrangthai(String trangthai)
	{
		this.trangthai = trangthai;		
	}

	public String getNgaytao()
	{	
		return this.ngaytao;
	}

	public void setNgaytao(String ngaytao) 
	{
		this.ngaytao = ngaytao;		
	}

	public String getNguoitao() 
	{		
		return this.nguoitao;
	}

	public void setNguoitao(String nguoitao) 
	{
		this.nguoitao = nguoitao;		
	}

	public String getNgaysua() 
	{		
		return this.ngaysua;
	}

	public void setNgaysua(String ngaysua) 
	{
		this.ngaysua = ngaysua;	
	}

	public String getNguoisua() 
	{		
		return this.nguoisua;
	}

	public void setNguoisua(String nguoisua) 
	{
		this.nguoisua = nguoisua;	
	}

	public String getChietkhau() 
	{
		if(this.chietkhau.length() <= 0)
			this.chietkhau = "0";
		return this.chietkhau;
	}

	public void setChietkhau(String chietkhau) 
	{
		this.chietkhau = chietkhau;		
	}

	public String getVAT() 
	{
		if(this.VAT == "")
		{
			//this.VAT = "10";
			this.VAT = "0"; //TTC, gia trong bang gia da co VAT
		}
		return this.VAT;
	}

	public void setVAT(String vat) 
	{
		this.VAT = vat;	
	}

	public String getMessage() 
	{	
		return this.msg;
	}

	public void setMessage(String msg) 
	{
		this.msg = msg;		
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

	public String getSitecode() 
	{
		return this.sitecode;
	}

	public void setSitecode(String sitecode) 
	{
		this.sitecode = sitecode;
	}

	public ResultSet getDdkdList() 
	{	
		return this.ddkdlist;
	}

	public void setDdkdList(ResultSet ddkdList)
	{
		this.ddkdlist = ddkdList;		
	}

	public String getDdkdId() 
	{		
		return this.ddkdId;
	}

	public void setDdkdId(String ddkdId) 
	{
		this.ddkdId = ddkdId;	
	}

	public List<ISanphamIP> getSpList()
	{	
		return this.splist;
	}

	public void setSpList(List<ISanphamIP> splist) 
	{
		this.splist = splist;
	}

	public float getTongtiensauCK() 
	{	
		float tongtientruocvat=0;	
		try{
			tongtientruocvat= Float.parseFloat(this.tongtientruocVAT);
		}catch(Exception er){

		}
		float tienck=0;	
		try{
			tienck= Float.parseFloat(this.tongchietkhau);
		}catch(Exception er){

		}

		this.ttsauCK = tongtientruocvat -tienck;

		return this.ttsauCK;
	}

	public void setTongtiensauCK(float ttsck) 
	{
		this.ttsauCK = ttsck;	
	}

	public String getTongtientruocVAT() 
	{		
		return this.tongtientruocVAT;
	}

	public void setTongtientruocVAT(String tttvat) 
	{
		this.tongtientruocVAT = tttvat;		
	}

	public String getTongtiensauVAT()
	{		
		return this.tongtiensauVAT;
	}

	public void setTongtiensauVAT(String ttsvat) 
	{
		this.tongtiensauVAT = ttsvat;		
	}

	public float getTongtienCKKM(){
		return this.ttCKKM;
	}
	public void setTongtienCKKM(float ttckkm){
		this.ttCKKM = ttckkm;

	}

	public float getTongtiensauCKKM(){
		this.ttsauCKKM = Float.parseFloat(this.tongtiensauVAT) - this.ttCKKM;
		return this.ttsauCKKM;
	}

	public void setTongtiensauCKKM(float ttsckkm){
		this.ttsauCKKM = ttsckkm;
	}

	public ResultSet getTbhList() 
	{
		return this.tbhlist;
	}

	public void setTbhList(ResultSet tbhList) 
	{
		this.tbhlist = tbhList;
	}

	public String getTbhId() 
	{
		return this.tbhId;
	}

	public void setTbhId(String tbhId) 
	{
		this.tbhId = tbhId;
	}

	public String getGsbhId() 
	{
		return this.gsbhId;
	}

	public void setGsbhId(String gsbhId) 
	{
		this.gsbhId = gsbhId;
	}

	public ResultSet getKhList() 
	{
		return this.khlist;
	}

	public void setKhList(ResultSet khlist) 
	{
		this.khlist = khlist;
	}

	public Hashtable<String, Integer> getSpThieuList() 
	{
		return this.spThieuList;
	}

	public void setSpThieuList(Hashtable<String, Integer> spThieuList) 
	{
		this.spThieuList = spThieuList;
	}

	//tra km
	public Hashtable<String, Float> getScheme_Tongtien() 
	{
		return this.scheme_tongtien;
	}

	public void setScheme_Tongtien(Hashtable<String, Float> scheme_tongtien) 
	{
		this.scheme_tongtien = scheme_tongtien;
	}

	public Hashtable<String, Float> getScheme_Chietkhau() 
	{
		return this.scheme_chietkhau;
	}

	public void setScheme_Chietkhau(Hashtable<String, Float> scheme_chietkhau) 
	{
		this.scheme_chietkhau = scheme_chietkhau;
	}

	public List<ISanphamIP> getScheme_SpList() 
	{
		return this.scheme_sanpham;
	}

	public void setScheme_SpList(List<ISanphamIP> splist) 
	{
		this.scheme_sanpham = splist;
	}

	private void getTrakhuyenmai()
	{


	}


	public boolean Muctindung()
	{ 

		return false;
	}



	public boolean CreateDh(List<ISanphamIP> splist) 
	{
		int khut = 0;
		if(this.spThieuList.size() > 0)
		{
			this.msg = "Trong kho khong du so luong mot so san pham ban chon, vui long chon lai so luong ...";	
			return false;
		}	
		else
		{

			String sqlCHECK = "";
			for(int m = 0; m < splist.size(); m++)
			{
				ISanphamIP sp = splist.get(m);
				sqlCHECK += " select pk_seq as sanpham_fk, '" + sp.getSoluong() +  "' as soluong, '" + sp.getDongia().replaceAll(",", "") + "' as dongia, '" + sp.getChietkhau().replaceAll(",", "") + "' as chietkhau , '"+sp.getGiagoc().replaceAll(",", "")+"' as dongiaGOC,'"+sp.getBgId()+"' as BangGia_fk,  " +
						"'" + sp.getChietkhauDLN().replaceAll(",", "") + "' as chietkhauDLN, '" + sp.getChietkhauTT().replaceAll(",", "") + "' as chietkhauTT, '" + sp.getChietkhauDH().replaceAll(",", "") + "' as chietkhauDH  from SANPHAM where ma = '" + sp.getMasanpham() + "'  ";
				if(m < splist.size() - 1)
					sqlCHECK += " union ";
			}
			System.out.println("SQLCHECK: "+sqlCHECK);
			if(this.sotiengiam.length() <= 0)
			{
				this.sotiengiam = "0";
			}
			try
			{

				db.update("SET TRANSACTION ISOLATION LEVEL SNAPSHOT;");
				db.update("SET LOCK_TIMEOUT 60000;");
				db.getConnection().setAutoCommit(false);

				String query =
						"	declare @ngaynhap nvarchar(10), @kbh_fk numeric(18, 0),@BM_fk numeric(18, 0),@AMS_fk numeric(18, 0) ;  "+
								"	select @kbh_fk = kbh_fk from khachhang where pk_seq ='"+this.khId+"' " +
								" 	select @ngaynhap='"+this.ngaygiaodich+"'   "+
								" 	select @AMS_fk = asm_fk from khachhang where pk_seq = '"+this.khId+"' "+
								"	select top 1 @BM_fk=bm_cn.bm_fk from  asm_khuvuc a "+ 
								"			inner join asm on asm.pk_seq=a.asm_fk AND A.NGAYBATDAU <=@ngaynhap AND A.NGAYKETTHUC >=@ngaynhap "+
								"			inner join khuvuc kv on kv.pk_seq=a.khuvuc_fk "+
								"			inner join bm_chinhanh bm_cn on bm_cn.vung_fk=kv.vung_fk AND bm_cn.NGAYBATDAU <=@ngaynhap AND bm_cn.NGAYKETTHUC >=@ngaynhap "+
								"			inner join bm on bm.pk_seq=bm_cn.bm_fk and bm.kbh_fk=@kbh_fk and bm.dvkd_fk=(select dvkd_fk from giamsatbanhang where pk_seq='"+this.gsbhId+"') "+
								"	where  asm.kbh_fk= @kbh_fk and asm.dvkd_fk=(select dvkd_fk from giamsatbanhang where pk_seq='"+this.gsbhId+"') "+
								"	insert into DonHangIP(nocu,ghichu,ngaynhap, trangthai, ngaytao, ngaysua, nguoitao, nguoisua, vat, tonggiatri, ddkd_fk, gsbh_fk, khachhang_fk, kho_fk, kbh_fk, tinhtrang,BM,ASM, diachigiao,ngaygiao) "+
								"	values("+ this.nocu +",N'" + this.ghichu+ "','"+this.ngaygiaodich+"', '0','"+this.getDateTime()+"','"+this.getDateTime()+"', '"+this.userId+"', '"+this.userId+"','"+this.VAT+"' , '"+this.tongtiensauVAT.replaceAll(",", "")+"','"+this.ddkdId+"','"+this.gsbhId+"','"+this.khId+"', '"+this.khoId+"', @kbh_fk, '0',@BM_fk,@AMS_fk, N'"+this.diachigiaohang+"','"+this.ngaygiao+"') ";
				System.out.println("Tao DHIP: "+query);
				if(!db.update(query))
				{
					this.msg = "Lỗi tạo mới đơn hàng, chưa có ASM hoặc RSM phụ trách kênh hoặc DVKD này !: "+ query;
					this.db.getConnection().rollback();
					return false;
				}

				ResultSet rsDDH = db.get("select scope_identity() as ID ");
				if(rsDDH.next())
				{
					this.id = rsDDH.getString("ID");
				}
				rsDDH.close();


				if(this.khId.length() >0 )
				{

					String makh = "";
					query = " select smartid from khachhang where pk_seq  ="+khId;
					System.out.println("KH: "+query);
					ResultSet rs = db.get(query);
					if(rs != null)
					{
						if(rs.next())
						{
							makh = rs.getString("smartid");
						}
						rs.close();
					}
					OracleConnUtils dbsys=new OracleConnUtils();
					query = "select ITEM_CODE,sum(NVL(COUNT_LOT,0)) as SoLuong from apps.SGP_IP_ONHAND where CUSTOMER_NUMBER = '"+makh+"' or   ASSIGN_CUS IS NULL group by ITEM_CODE ";
					System.out.println("KTTON: "+query);
					ResultSet rssys=dbsys.get(query);
					if(rssys != null)
					{
						query="IF OBJECT_ID('tempdb.dbo.#ERP_TonKhoSANPHAM') IS NOT NULL "
								+"\n DROP TABLE #ERP_TonKhoSANPHAM "
								+"\n CREATE TABLE #ERP_TonKhoSANPHAM ("
								+"\n sanpham_fk numeric(18,0),  "
								+"\n AVAILABLE numeric(18,0) )";

						db.update(query);
						while(rssys.next())
						{

							query=	 " INSERT INTO #ERP_TonKhoSANPHAM "
									+ " select (select pk_seq from sanpham where ma = '"+rssys.getString("ITEM_CODE")+"') as sanpham_fk,  '"+rssys.getString("SoLuong")+"' "
									+ " where  (select pk_seq from sanpham where ma = '"+rssys.getString("ITEM_CODE")+"') is not null "	;
							if(db.updateReturnInt(query)<=0)
							{
								System.out.println("Khong co ton kho: "+ query);
							}

							/*	query=	 " INSERT INTO TonKhoSANPHAM "
							 	+ " select (select pk_seq from sanpham where ma = '"+rssys.getString("ITEM_CODE")+"') as sanpham_fk,  '"+rssys.getString("SoLuong")+"'"
							 	+	 "where  (select pk_seq from sanpham where ma = '"+rssys.getString("ITEM_CODE")+"') is not null "	;
						if(db.updateReturnInt(query)<=0)
						{
							System.out.println("Khong co ton kho: "+ query);
						}*/

						}
						rssys.close();
						/*query = "select ITEM_CODE,COUNT_LOT,CUSTOMER_NUMBER,ASSIGN_CUS from TonKhoIPTEST  ";
					ResultSet rssys = db.get(query);
					if(rssys != null)
					{		

					 query="IF OBJECT_ID('tempdb.dbo.#TonKhoIP') IS NOT NULL "
								+"\n DROP TABLE #TonKhoIP "
								+"\n CREATE TABLE #TonKhoIP ("
								+"\n COUNT_LOT numeric(18,0),  "
								+"\n ITEM_CODE varchar(100),"
								+"\n CUSTOMER_NUMBER varchar(100),"
								+ "\n ASSIGN_CUS varchar(100)"
								+ " )";
					 	db.update(query);
						query=	 " INSERT INTO #TonKhoIP "
							 	+ "select COUNT_LOT,ITEM_CODE,CUSTOMER_NUMBER,ASSIGN_CUS from TonKhoIPTEST "
								+ "  "	;
					 	db.update(query);




						query="IF OBJECT_ID('tempdb.dbo.#ERP_TonKhoSANPHAM') IS NOT NULL "
								+"\n DROP TABLE #ERP_TonKhoSANPHAM "
								+"\n CREATE TABLE #ERP_TonKhoSANPHAM ("
								+"\n sanpham_fk numeric(18,0),  "
								+"\n AVAILABLE numeric(18,0) )";

						db.update(query);

						query = 	"INSERT INTO #ERP_TonKhoSANPHAM "
								+ "select (select pk_seq from sanpham where ma = ITEM_CODE),sum(COUNT_LOT) as SoLuong from #TonKhoIP "
								+ " where (CUSTOMER_NUMBER = '"+makh+"' or ASSIGN_CUS is null) "
								+ " and (select pk_seq from sanpham where ma = ITEM_CODE) is not null "
								+ " group by ITEM_CODE ";
						System.out.println("LayTonKho: "+query);
						if(db.updateReturnInt(query)<=0)
						{
							System.out.println("Khong co Ton Kho #ERP_TonKhoSANPHAM: "+ query);
						}

						query=	 " INSERT INTO TonKhoSANPHAM "
								+ "select (select pk_seq from sanpham where ma = ITEM_CODE),sum(COUNT_LOT) as SoLuong from #TonKhoIP "
								+ " where (CUSTOMER_NUMBER = '"+makh+"' or ASSIGN_CUS is null) "
								+ "  "
								+ " group by ITEM_CODE ";
						if(db.updateReturnInt(query)<=0)
						{
							System.out.println("Khong co ton kho TonKhoSANPHAM: "+ query);
						}*/

						query="IF OBJECT_ID('tempdb.dbo.#ERP_TonKhoSANPHAM_CT') IS NOT NULL "
								+"\n DROP TABLE #ERP_TonKhoSANPHAM_CT "
								+"\n CREATE TABLE #ERP_TonKhoSANPHAM_CT ("
								+"\n sanpham_fk numeric(18,0),  "
								+"\n AVAILABLE numeric(18,0), "
								+"\n MAKH nvarchar(100), "
								+"\n UuTien nvarchar(1), "
								+"\n KHACHHANG_FK numeric(18,0),"
								+ "  ASM nvarchar(100),"
								+ "  smartid varchar(100)"
								+ "  )";

						db.update(query);
						query = "select ITEM_CODE,NVL(COUNT_LOT,0) as SoLuong,CUSTOMER_NUMBER,NVL(ASSIGN_CUS,'0') as Uutien,ASM_NAME,ASM_Code as smartid  from apps.SGP_IP_ONHAND where CUSTOMER_NUMBER = '"+makh+"' or   ASSIGN_CUS IS NULL  ";
						System.out.println("QRTKCT: "+query);
						rssys=dbsys.get(query);

						// query = "select ITEM_CODE,NVL(COUNT_LOT,0) as SoLuong,CUSTOMER_NUMBER,NVL(ASSIGN_CUS,'0') as Uutien,ASM_NAME,ASM_Code as smartid  from apps.SGP_IP_ONHAND where CUSTOMER_NUMBER = '"+makh+"' or   ASSIGN_CUS IS NULL  ";

						//	 query = "select ITEM_CODE,isnull(COUNT_LOT,0) as SoLuong,CUSTOMER_NUMBER,'' as Uutien,'' ASM_NAME,'' as smartid  from TonKhoIPTEST where CUSTOMER_NUMBER = '"+makh+"' or   ASSIGN_CUS IS NULL  ";

						System.out.println("QRTKCT: "+query);
						// rssys=db.get(query);
						while(rssys.next())
						{

							query=	 " INSERT INTO #ERP_TonKhoSANPHAM_CT(sanpham_fk,AVAILABLE,MAKH,UuTien,KHACHHANG_FK,ASM,smartid) "
									+ " select (select pk_seq from sanpham where ma = '"+rssys.getString("ITEM_CODE")+"') as sanpham_fk,  '"+rssys.getString("SoLuong")+"','"+rssys.getString("CUSTOMER_NUMBER")+"','"+(rssys.getString("Uutien").equals("0")?"0":"1")+"',(select pk_seq from khachhang where smartid = '"+rssys.getString("CUSTOMER_NUMBER")+"'),N'"+rssys.getString("ASM_NAME")+"','"+rssys.getString("smartid")+"' "
									+ " where  (select pk_seq from sanpham where ma = '"+rssys.getString("ITEM_CODE")+"') is not null and (select pk_seq from khachhang where smartid = '"+rssys.getString("CUSTOMER_NUMBER")+"' and ismt = '1') is not null "	;
							if(db.updateReturnInt(query)<=0)
							{
								System.out.println("Khong co ton kho: "+ query);
							}
							if(rssys.getString("CUSTOMER_NUMBER")!=null)
								if(rssys.getString("CUSTOMER_NUMBER").equals(makh))
								{
									if(!rssys.getString("Uutien").equals("0"))
										khut = 1;
								}

								query=	 " INSERT INTO TonKhoSANPHAM_CT(sanpham_fk,AVAILABLE,MAKH,UuTien,KHACHHANG_FK,ASM) "
						 	+ " select (select pk_seq from sanpham where ma = '"+rssys.getString("ITEM_CODE")+"') as sanpham_fk,  '"+rssys.getString("SoLuong")+"','"+rssys.getString("CUSTOMER_NUMBER")+"','"+(rssys.getString("Uutien").equals("0")?"0":"1")+"',(select pk_seq from khachhang where smartid = '"+rssys.getString("CUSTOMER_NUMBER")+"'),'"+rssys.getString("ASM_NAME")+"' "
						 	;
					if(db.updateReturnInt(query)<=0)
					{
						System.out.println("Khong co ton kho: "+ query);
					}

						}

						dbsys.shutDown();
					}
				}
				int so = 0;
				query = "select count(*) as so from #ERP_TonKhoSANPHAM ";
				ResultSet rs = db.get(query);
				if(rs != null)
					if(rs.next())
					{
						so = rs.getInt("so");
					}

				if(so == 0)
				{
					db.getConnection().rollback(); 
					this.msg = "Không kiểm tra được tồn kho IP";
					return false; 
				}
				so = 0;
				query = "select count(*) as so from #ERP_TonKhoSANPHAM_CT ";
				rs = db.get(query);
				if(rs != null)
					if(rs.next())
					{
						so = rs.getInt("so");
					}

				if(so == 0)
				{
					db.getConnection().rollback(); 
					this.msg = "Không kiểm tra được tồn kho IP";
					return false; 
				}
				if(khut == 0)
				{
					// trường hợp kh không ưu tiên, chỉ trừ kho của những khách hàng không ưu tiên
					query = "update a set a.AVAILABLE = case when a.AVAILABLE - b.SL > 0 then   a.AVAILABLE - b.SL "
							+ " else 0 end  "+
							" from #ERP_TonKhoSANPHAM a inner join ( "+
							"select SUM(b.SOLUONG) as SL,SANPHAM_FK "+ 
							"\n from  DONHANGIP a inner join  DONHANG_SANPHAMIP_CHITIET b on a.PK_SEQ = b.DONHANG_SANPHAMIP_FK "+
							"\n where isnull(KHACHHANGUUTIEN,'0') = 0   and a.trangthai in (0,9) "+
							"\n group by SANPHAM_FK ) b on a.sanpham_fk = b.SANPHAM_FK ";
					System.out.println("GiamTon1: "+query);
				}
				else
				{
					query = "update a set a.AVAILABLE = case when a.AVAILABLE - b.SL > 0 then   a.AVAILABLE - b.SL "
							+ " else 0 end  "+
							" from #ERP_TonKhoSANPHAM a inner join ( "+
							"select SUM(b.SOLUONG) as SL,SANPHAM_FK "+ 
							"\n from  DONHANGIP a inner join  DONHANG_SANPHAMIP_CHITIET b on a.PK_SEQ = b.DONHANG_SANPHAMIP_FK "+
							"\n where  a.trangthai in (0,9)"+
							"\n group by SANPHAM_FK ) b on a.sanpham_fk = b.SANPHAM_FK ";
					System.out.println("GiamTon2: "+query);

				}

				if(!db.update(query))
				{
					System.out.println("Khong cập nhật được tồn kho ERP_TonKhoSANPHAM: "+ query);
					db.getConnection().rollback(); 
					this.msg = "Không kiểm tra được tồn kho IP !";
					return false; 
				}
				if(khut == 0)
				{
					query = "update a set a.AVAILABLE = case when a.AVAILABLE - b.SL > 0 then   a.AVAILABLE - b.SL "
							+ "else 0 end  "+
							" from #ERP_TonKhoSANPHAM_CT a inner join ( "+
							"select SUM(b.SOLUONG) as SL,SANPHAM_FK,b.khachhang_fk "+ 
							"\n from  DONHANGIP a inner join  DONHANG_SANPHAMIP_CHITIET b on a.PK_SEQ = b.DONHANG_SANPHAMIP_FK "+
							"\n where isnull(KHACHHANGUUTIEN,'0') = 0 and  a.trangthai in (0,9) "+
							"\n group by SANPHAM_FK,b.khachhang_fk ) b on a.sanpham_fk = b.SANPHAM_FK and a.khachhang_fk = b.khachhang_fk ";
					System.out.println("GiamTonCT1: "+query);
				}else
				{
					query = "update a set a.AVAILABLE = case when a.AVAILABLE - b.SL > 0 then   a.AVAILABLE - b.SL "
							+ "else 0 end  "+
							" from #ERP_TonKhoSANPHAM_CT a inner join ( "+
							"select SUM(b.SOLUONG) as SL,SANPHAM_FK,b.khachhang_fk "+ 
							"\n from  DONHANGIP a inner join  DONHANG_SANPHAMIP_CHITIET b on a.PK_SEQ = b.DONHANG_SANPHAMIP_FK "+
							"\n where   a.trangthai in (0,9) "+
							"\n group by SANPHAM_FK,b.khachhang_fk ) b on a.sanpham_fk = b.SANPHAM_FK and a.khachhang_fk = b.khachhang_fk ";
					System.out.println("GiamTonCT2: "+query);
				}
				if(!db.update(query))
				{
					System.out.println("Khong cập nhật được tồn kho #ERP_TonKhoSANPHAM_CT: "+ query);
					db.getConnection().rollback(); 
					this.msg = "Không kiểm tra được tồn kho chi tiết IP ";
					return false; 
				}
				//Chèn DONHANG_SANPHAMSPIP tận dung luôn câu SQL bên trên, kể cả hàm cập nhật tồn kho, cũng chỉ chạy 1 lượt
				query = "if exists (select PK_SEQ from DONHANGIP WHERE PK_SEQ = '"+this.id+"' AND TRANGTHAI = 0)" +
						"insert DONHANG_SANPHAMSPIP( sanpham_fk, donhang_fk, soluong, kho_fk, giamua, chietkhau, dongiaGOC,BangGia_fk, ptCKDLN, ptCKTT, ptCKDH, SLCSX,KHUT ) " +
						"select sp.sanpham_fk, '" + this.id + "', sp.soluong, '" + this.khoId + "', sp.dongia, sp.chietkhau ,sp.dongiaGOC,sp.BangGia_fk, sp.chietkhauDLN, sp.chietkhauTT, sp.chietkhauDH" +
						"		, (SELECT (CASE when K.AVAILABLE <= 0 THEN SP.SOLUONG when K.AVAILABLE > 0 and K.AVAILABLE - SP.SOLUONG > 0 then 0 ELSE SP.SOLUONG - K.AVAILABLE END) FROM #ERP_TonKhoSANPHAM K WHERE SP.SANPHAM_FK = K.SANPHAM_FK),'"+khut+"' \n" +
						"from ( " + sqlCHECK + " ) sp ";

				System.out.println("cau lenh chen: \n" + query + "\n");
				if(db.updateReturnInt(query) <= 0)
				{
					db.getConnection().rollback(); 
					this.msg = "4.Khong the cap nhat table 'DONHANG_SANPHAMSPIP' , " + query;
					return false; 
				}


				query = "select distinct  '" + this.id + "' as ID,sp.soluong, sp.sanpham_fk" +
						"		  \n" +
						"from ( " + sqlCHECK + " ) sp inner join DONHANG_SANPHAMSPIP dhsp on sp.sanpham_fk = dhsp.sanpham_fk "
						+ " where ABS(dhsp.SLCSX) <> sp.soluong and dhsp.donhang_fk = '"+this.id+"' ";
				System.out.println("QRSP: "+query);
				rs = db.get(query);
				if(rs != null)
				{
					while(rs.next())
					{



						
						query = " select sanpham_fk,AVAILABLE,MAKH,UuTien,KHACHHANG_FK,ASM,2 as loai "
								+ "\n from #ERP_TonKhoSANPHAM_CT where sanpham_fk = "+rs.getString("sanpham_fk")+""
								+ "\n  and AVAILABLE > 0 and KHACHHANG_FK = '"+this.khId+"' "
								+ " \n union "
								+ "select sanpham_fk,AVAILABLE,MAKH,UuTien,KHACHHANG_FK,ASM, 1 as loai "
								+ "\n from #ERP_TonKhoSANPHAM_CT where sanpham_fk = "+rs.getString("sanpham_fk")+""
								+ "\n  and AVAILABLE > 0 and KHACHHANG_FK <> '"+this.khId+"' and UuTien <> '1' "
								+ "  order by UuTien desc ,loai desc,AVAILABLE desc ";
						System.out.println("QR: "+query);

						ResultSet rskho = db.get(query);
						if(rskho != null)
						{


							float slcan = 0;
							float soluong  =  rs.getFloat("soluong");
							while(rskho.next())
							{

								float AVAILABLE = rskho.getFloat("AVAILABLE") ;
								

								if(AVAILABLE >= soluong)
								{
									slcan = soluong;
									
								}else 
								{
									slcan = AVAILABLE;
									soluong = soluong - AVAILABLE;
									AVAILABLE = 0;
								
								}



								query = "insert DONHANG_SANPHAMIP_CHITIET(DONHANG_SANPHAMIP_FK,KHACHHANG_FK,SMARTID,KHACHHANGUUTIEN,SANPHAM_FK,TONHT,SOLUONG) " +
										"\n select  '" + this.id + "','"+rskho.getString("KHACHHANG_FK")+"', '"+rskho.getString("MAKH")+"','"+rskho.getString("uutien")+"',"+
										"\n '"+rs.getString("sanpham_fk") +"', '"+ rskho.getFloat("AVAILABLE")+"', '"+ slcan+"' ";
								if(db.updateReturnInt(query) <= 0)
								{
									db.getConnection().rollback(); 
									this.msg = "4.Không thể cập nhật 'DONHANG_SANPHAMIP_CHITIET' , " + query;
									return false; 
								}

								if(AVAILABLE >= soluong)
								{
									
									break;
									
								}


							}
						}



					}
				}



				query = 
						"	update donhangIP set TONGGIATRI= "+
								"			isnull(( "+
								"				select SUM(round(b.soluong*b.giamua,0))  as TongGiaTri "+
								"				from donhangip a inner join DONHANG_SANPHAMSPIP b on b.DONHANG_FK=a.PK_SEQ "+
								"				where  a.PK_SEQ=donhangip.PK_SEQ "+
								"				group by b.DONHANG_FK "+
								"			),0) - "+
								"			round(isnull(( "+
								"				select SUM(round(b.soluong*b.giamua,0))  as TongGiaTri "+
								"				from donhangip a inner join DONHANG_SANPHAMSPIP b on b.DONHANG_FK=a.PK_SEQ "+
								"				where  a.PK_SEQ=donhangip.PK_SEQ "+
								"				group by b.DONHANG_FK "+
								"			),0)*("+this.tongchietkhau+"/100.0),0)  "+
								"		from DONHANGIP "+
								"		where pk_Seq='"+this.id+"' ";
				if(!db.update(query))
				{
					db.getConnection().rollback();
					this.msg = "Lỗi phát sinh khi cập nhật giá trị đơn hàng IP " + query;
					return false; 
				}


				db.getConnection().commit();
				db.getConnection().setAutoCommit(true);
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
			return true;			
		}
	}

	public boolean UpdateDh(List<ISanphamIP> splist, String action) 
	{	
		int khut = 0;
		if(this.spThieuList.size() > 0)
		{
			this.msg = "Trong kho khong du so luong mot so san pham ban chon, vui long chon lai so luong...";
			return false;
		}
		else
		{
			String sqlCHECK = "";
			for(int m = 0; m < splist.size(); m++)
			{
				ISanphamIP sp = splist.get(m);
				sqlCHECK += " select pk_seq as sanpham_fk, '" + sp.getSoluong() +  "' as soluong, '" + sp.getDongia().replaceAll(",", "") + "' as dongia, '" + sp.getChietkhau().replaceAll(",", "") + "' as chietkhau , '"+sp.getGiagoc().replaceAll(",", "")+"' as dongiaGOC,'"+sp.getBgId()+"' as BangGia_fk,  " +
						"'" + sp.getChietkhauDLN().replaceAll(",", "") + "' as chietkhauDLN, '" + sp.getChietkhauTT().replaceAll(",", "") + "' as chietkhauTT, '" + sp.getChietkhauDH().replaceAll(",", "") + "' as chietkhauDH  from SANPHAM where ma = '" + sp.getMasanpham() + "'  ";
				if(m < splist.size() - 1)
					sqlCHECK += " union ";
			}


			String sql = "";


			String query = "";



			try
			{
				db.update("SET TRANSACTION ISOLATION LEVEL SNAPSHOT;");
				db.update("SET LOCK_TIMEOUT 60000;");

				db.getConnection().setAutoCommit(false);
				String kbh_fk = "", BM_fk = "", ASM_fk = "";
				int trangthai=-1;
				if(BM_fk == null || BM_fk.length() == 0)
					BM_fk = "NULL";
				if(ASM_fk == null || ASM_fk.length() == 0)
					ASM_fk = "NULL";

				query = "select   (select trangthai from DonHangIP where pk_Seq ='"+this.id+"') as TrangThai ,(select kbh_fk from khachhang where pk_seq = " + this.khId +" ) as kbh_Fk " ; 
				ResultSet	rs = this.db.get(query);
				rs.next();
				kbh_fk = rs.getString("kbh_fk");
				trangthai= rs.getInt("trangthai");


				query =
						"  update donhangIP set nocu = "+ this.nocu +", ghichu = N'" + this.ghichu+ "', ngaynhap ='"+this.ngaygiaodich+"', ddkd_fk ='"+this.ddkdId+"', gsbh_fk ='"+this.gsbhId+"', khachhang_fk ='"+this.khId+"', KHO_FK = '"+this.khoId+"',  "+ 
								"		  chietkhau ='"+this.tongchietkhau+"',ngaygiao='"+this.ngaygiao+"', diachigiao = N'"+this.diachigiaohang+"', "+
								"				ngaysua ='"+getDateTime()+"', nguoisua ='"+this.userId+"', tonggiatri ='"+this.tongtientruocVAT+"', vat ='"+this.VAT+"', kbh_fk = (select kbh_fk from khachhang where pk_seq = '"+this.khId+"' ) ";
				if(trangthai==9)
				{
					query += "    , TrangThai=0 ";
					query +="	where pk_seq = '"+this.id+"'   and  TrangThai=9  ";
				}else 
				{
					query +="	where pk_seq = '"+this.id+"'  and  TrangThai=0 ";
				}

				System.out.println("[DonHangIP]: "+query);
				if( db.updateReturnInt(query) != 1 )
				{
					db.getConnection().rollback();
					this.msg = "1.Khong the cap nhat table 'Don HangIP'(ĐH đã chốt hoặc xảy ra lỗi) , " + query;
					return false; 
				}
				//Cap nhat kho


				query = "delete from DONHANG_SANPHAMSPIP where donhang_fk = " + this.id + " " +
						"		and donhang_Fk in (select pk_seq from DonHangIP where trangthai = 0 and pk_Seq = '" + this.id + "') ";
				if(!db.update(query))
				{
					db.getConnection().rollback(); 
					this.msg = "3.Lỗi không tạo được đơn hàng IP " + query;
					return false; 
				}
				System.out.println("[DONHANG_SANPHAMSPIP]"+msg);

				query = "delete from DONHANG_SANPHAMIP_CHITIET where DONHANG_SANPHAMIP_FK = " + this.id + " ";

				if(!db.update(query))
				{
					db.getConnection().rollback(); 
					this.msg = "4.Lỗi không tạo được đơn hàng IP " + query;
					return false; 
				}
				if(this.khId.length() >0 )
				{

					String makh = "";
					query = " select smartid from khachhang where pk_seq  ="+khId;
					System.out.println("KH: "+query);
					rs = db.get(query);
					if(rs != null)
					{
						if(rs.next())
						{
							makh = rs.getString("smartid");
						}
						rs.close();
					}


					OracleConnUtils dbsys=new OracleConnUtils();
					query = "select ITEM_CODE,sum(NVL(COUNT_LOT,0)) as SoLuong from apps.SGP_IP_ONHAND where CUSTOMER_NUMBER = '"+makh+"' or   ASSIGN_CUS IS NULL group by ITEM_CODE ";
					System.out.println("QRTK: "+query);
					ResultSet rssys=dbsys.get(query);
					if(rssys != null)
					{
						query="IF OBJECT_ID('tempdb.dbo.#ERP_TonKhoSANPHAM') IS NOT NULL "
								+"\n DROP TABLE #ERP_TonKhoSANPHAM "
								+"\n CREATE TABLE #ERP_TonKhoSANPHAM ("
								+"\n sanpham_fk numeric(18,0),  "
								+"\n AVAILABLE numeric(18,0) )";

						db.update(query);
						while(rssys.next())
						{

							query=	 " INSERT INTO #ERP_TonKhoSANPHAM "
									+ " select (select pk_seq from sanpham where ma = '"+rssys.getString("ITEM_CODE")+"') as sanpham_fk,  '"+rssys.getString("SoLuong")+"' "
									+ " where  (select pk_seq from sanpham where ma = '"+rssys.getString("ITEM_CODE")+"') is not null "	;
							if(db.updateReturnInt(query)<=0)
							{
								System.out.println("Khong co ton kho: "+ query);
							}

							/*query=	 " INSERT INTO TonKhoSANPHAM "
							 	+ " select (select pk_seq from sanpham where ma = '"+rssys.getString("ITEM_CODE")+"') as sanpham_fk,  '"+rssys.getString("SoLuong")+"'"
							 	+	 "where  (select pk_seq from sanpham where ma = '"+rssys.getString("ITEM_CODE")+"') is not null "	;
						if(db.updateReturnInt(query)<=0)
						{
							System.out.println("Khong co ton kho: "+ query);
						}*/

						}
						rssys.close();
					}
					/*	query = "select ITEM_CODE,COUNT_LOT,CUSTOMER_NUMBER,ASSIGN_CUS from TonKhoIPTEST  ";
					ResultSet rssys = db.get(query);
					if(rssys != null)
					{		

					 query="IF OBJECT_ID('tempdb.dbo.#TonKhoIP') IS NOT NULL "
								+"\n DROP TABLE #TonKhoIP "
								+"\n CREATE TABLE #TonKhoIP ("
								+"\n COUNT_LOT numeric(18,0),  "
								+"\n ITEM_CODE varchar(100),"
								+"\n CUSTOMER_NUMBER varchar(100),"
								+ "\n ASSIGN_CUS varchar(100)"
								+ " )";
					 	db.update(query);
						query=	 " INSERT INTO #TonKhoIP "
							 	+ "select COUNT_LOT,ITEM_CODE,CUSTOMER_NUMBER,ASSIGN_CUS from TonKhoIPTEST "
								+ "  "	;
					 	db.update(query);




						query="IF OBJECT_ID('tempdb.dbo.#ERP_TonKhoSANPHAM') IS NOT NULL "
								+"\n DROP TABLE #ERP_TonKhoSANPHAM "
								+"\n CREATE TABLE #ERP_TonKhoSANPHAM ("
								+"\n sanpham_fk numeric(18,0),  "
								+"\n AVAILABLE numeric(18,0) )";

						db.update(query);

						query = 	"INSERT INTO #ERP_TonKhoSANPHAM "
								+ "select (select pk_seq from sanpham where ma = ITEM_CODE),sum(COUNT_LOT) as SoLuong from #TonKhoIP "
								+ " where (CUSTOMER_NUMBER = '"+makh+"' or ASSIGN_CUS is null) "
								+ " and (select pk_seq from sanpham where ma = ITEM_CODE) is not null "
								+ " group by ITEM_CODE ";
						System.out.println("LayTonKho: "+query);
						if(db.updateReturnInt(query)<=0)
						{
							System.out.println("Khong co Ton Kho #ERP_TonKhoSANPHAM: "+ query);
						}

						query=	 " INSERT INTO TonKhoSANPHAM "
								+ "select (select pk_seq from sanpham where ma = ITEM_CODE),sum(COUNT_LOT) as SoLuong from #TonKhoIP "
								+ " where (CUSTOMER_NUMBER = '"+makh+"' or ASSIGN_CUS is null) "
								+ "  "
								+ " group by ITEM_CODE ";
						if(db.updateReturnInt(query)<=0)
						{
							System.out.println("Khong co ton kho TonKhoSANPHAM: "+ query);
						}	*/
					query="IF OBJECT_ID('tempdb.dbo.#ERP_TonKhoSANPHAM_CT') IS NOT NULL "
							+"\n DROP TABLE #ERP_TonKhoSANPHAM_CT "
							+"\n CREATE TABLE #ERP_TonKhoSANPHAM_CT ("
							+"\n sanpham_fk numeric(18,0),  "
							+"\n AVAILABLE numeric(18,0), "
							+"\n MAKH nvarchar(100), "
							+"\n UuTien nvarchar(1), "
							+"\n KHACHHANG_FK numeric(18,0),"
							+ "  ASM nvarchar(100),"
							+ "  smartid varchar(100)"
							+ "  )";

					db.update(query);
					query = "select ITEM_CODE,NVL(COUNT_LOT,0) as SoLuong,CUSTOMER_NUMBER,NVL(ASSIGN_CUS,'0') as Uutien,ASM_NAME,ASM_Code as smartid  from apps.SGP_IP_ONHAND where CUSTOMER_NUMBER = '"+makh+"' or   ASSIGN_CUS IS NULL  ";
					System.out.println("QRTKCT: "+query);
					rssys=dbsys.get(query);

					/* query = "select ITEM_CODE,isnull(COUNT_LOT,0) as SoLuong,CUSTOMER_NUMBER,'' as Uutien,'' ASM_NAME,'' as smartid  from TonKhoIPTEST where CUSTOMER_NUMBER = '"+makh+"' or   ASSIGN_CUS IS NULL  ";

				 System.out.println("QRTKCT: "+query);
				 rssys=db.get(query);*/
					while(rssys.next())
					{

						query=	 " INSERT INTO #ERP_TonKhoSANPHAM_CT(sanpham_fk,AVAILABLE,MAKH,UuTien,KHACHHANG_FK,ASM,smartid) "
								+ " select (select pk_seq from sanpham where ma = '"+rssys.getString("ITEM_CODE")+"') as sanpham_fk,  '"+rssys.getString("SoLuong")+"','"+rssys.getString("CUSTOMER_NUMBER")+"','"+(rssys.getString("Uutien").equals("0")?"0":"1")+"',(select pk_seq from khachhang where smartid = '"+rssys.getString("CUSTOMER_NUMBER")+"'),N'"+rssys.getString("ASM_NAME")+"','"+rssys.getString("smartid")+"' "
								+ " where  (select pk_seq from sanpham where ma = '"+rssys.getString("ITEM_CODE")+"') is not null and (select pk_seq from khachhang where smartid = '"+rssys.getString("CUSTOMER_NUMBER")+"' and ismt = '1') is not null "	;
						if(db.updateReturnInt(query)<=0)
						{
							System.out.println("Khong co ton kho: "+ query);
						}

					/*	query=	 " INSERT INTO TonKhoSANPHAM_CT(sanpham_fk,AVAILABLE,MAKH,UuTien,KHACHHANG_FK,ASM) "
							 	+ " select (select pk_seq from sanpham where ma = '"+rssys.getString("ITEM_CODE")+"') as sanpham_fk,  '"+rssys.getString("SoLuong")+"','"+rssys.getString("CUSTOMER_NUMBER")+"','"+(rssys.getString("Uutien").equals("0")?"0":"1")+"',(select pk_seq from khachhang where smartid = '"+rssys.getString("CUSTOMER_NUMBER")+"'),N'"+rssys.getString("ASM_NAME")+"' "
							 	;
						if(db.updateReturnInt(query)<=0)
						{
							System.out.println("Khong co ton kho: "+ query);
						}*/
						if(rssys.getString("CUSTOMER_NUMBER")!=null)
							if(rssys.getString("CUSTOMER_NUMBER").equals(makh))
							{
								if(!rssys.getString("Uutien").equals("0"))
									khut = 1;
							}

					}

					dbsys.shutDown();
				}
				if(khut == 0)
				{
					// trường hợp kh không ưu tiên, chỉ trừ kho của những khách hàng không ưu tiên
					query = "update a set a.AVAILABLE = case when a.AVAILABLE - b.SL > 0 then   a.AVAILABLE - b.SL "
							+ " else 0 end  "+
							" from #ERP_TonKhoSANPHAM a inner join ( "+
							"select SUM(b.SOLUONG) as SL,SANPHAM_FK "+ 
							"\n from  DONHANGIP a inner join  DONHANG_SANPHAMIP_CHITIET b on a.PK_SEQ = b.DONHANG_SANPHAMIP_FK "+
							"\n where isnull(KHACHHANGUUTIEN,'0') = 0  and a.trangthai in (0,9) "+
							"\n group by SANPHAM_FK ) b on a.sanpham_fk = b.SANPHAM_FK ";
					System.out.println("GiamTon1: "+query);
				}
				else
				{
					query = "update a set a.AVAILABLE = case when a.AVAILABLE - b.SL > 0 then   a.AVAILABLE - b.SL "
							+ " else 0 end  "+
							" from #ERP_TonKhoSANPHAM a inner join ( "+
							"select SUM(b.SOLUONG) as SL,SANPHAM_FK "+ 
							"\n from  DONHANGIP a inner join  DONHANG_SANPHAMIP_CHITIET b on a.PK_SEQ = b.DONHANG_SANPHAMIP_FK "+
							"\n where  a.trangthai in (0,9)"+
							"\n group by SANPHAM_FK ) b on a.sanpham_fk = b.SANPHAM_FK ";
					System.out.println("GiamTon2: "+query);

				}

				if(!db.update(query))
				{
					System.out.println("Khong cập nhật được tồn kho ERP_TonKhoSANPHAM: "+ query);
					db.getConnection().rollback(); 
					this.msg = "Không kiểm tra được tồn kho IP !";
					return false; 
				}
				if(khut == 0)
				{
					query = "update a set a.AVAILABLE = case when a.AVAILABLE - b.SL > 0 then   a.AVAILABLE - b.SL "
							+ "else 0 end  "+
							" from #ERP_TonKhoSANPHAM_CT a inner join ( "+
							"select SUM(b.SOLUONG) as SL,SANPHAM_FK,b.khachhang_fk "+ 
							"\n from  DONHANGIP a inner join  DONHANG_SANPHAMIP_CHITIET b on a.PK_SEQ = b.DONHANG_SANPHAMIP_FK "+
							"\n where isnull(KHACHHANGUUTIEN,'0') = 0 and  a.trangthai in (0,9) "+
							"\n group by SANPHAM_FK,b.khachhang_fk ) b on a.sanpham_fk = b.SANPHAM_FK and a.khachhang_fk = b.khachhang_fk ";
					System.out.println("GiamTonCT1: "+query);
				}else
				{
					query = "update a set a.AVAILABLE = case when a.AVAILABLE - b.SL > 0 then   a.AVAILABLE - b.SL "
							+ "else 0 end  "+
							" from #ERP_TonKhoSANPHAM_CT a inner join ( "+
							"select SUM(b.SOLUONG) as SL,SANPHAM_FK,b.khachhang_fk "+ 
							"\n from  DONHANGIP a inner join  DONHANG_SANPHAMIP_CHITIET b on a.PK_SEQ = b.DONHANG_SANPHAMIP_FK "+
							"\n where   a.trangthai in (0,9) "+
							"\n group by SANPHAM_FK,b.khachhang_fk ) b on a.sanpham_fk = b.SANPHAM_FK and a.khachhang_fk = b.khachhang_fk ";
					System.out.println("GiamTonCT2: "+query);
				}
				if(!db.update(query))
				{
					System.out.println("Khong cập nhật được tồn kho #ERP_TonKhoSANPHAM_CT: "+ query);
					db.getConnection().rollback(); 
					this.msg = "Không kiểm tra được tồn kho chi tiết IP ";
					return false; 
				}


				//Chèn DONHANG_SANPHAMSPIP tận dung luôn câu SQL bên trên, kể cả hàm cập nhật tồn kho, cũng chỉ chạy 1 lượt
				query = "if exists (select PK_SEQ from DONHANGIP WHERE PK_SEQ = '"+this.id+"' AND TRANGTHAI = 0)" +
						"insert DONHANG_SANPHAMSPIP( sanpham_fk, donhang_fk, soluong, kho_fk, giamua, chietkhau, dongiaGOC,BangGia_fk, ptCKDLN, ptCKTT, ptCKDH, SLCSX ,khut ) " +
						"select sp.sanpham_fk, '" + this.id + "', sp.soluong, '" + this.khoId + "', sp.dongia, sp.chietkhau ,sp.dongiaGOC,sp.BangGia_fk, sp.chietkhauDLN, sp.chietkhauTT, sp.chietkhauDH  " +
						"		, (SELECT (CASE when K.AVAILABLE <= 0 THEN SP.SOLUONG when K.AVAILABLE > 0 and K.AVAILABLE - SP.SOLUONG > 0 then 0 ELSE SP.SOLUONG - K.AVAILABLE END) FROM #ERP_TonKhoSANPHAM K WHERE SP.SANPHAM_FK = K.SANPHAM_FK),khut = '"+khut+"' \n" +
						"from ( " + sqlCHECK + " ) sp ";
				if(!db.update(query))
				{
					db.getConnection().rollback(); 
					this.msg = "4.Lỗi không tạo được đơn hàng IP " + query;
					return false; 
				}

				int so = 0;
				query = "select count(*) as so from #ERP_TonKhoSANPHAM ";
				rs = db.get(query);
				if(rs != null)
					if(rs.next())
					{
						so = rs.getInt("so");
					}

				if(so == 0)
				{
					db.getConnection().rollback(); 
					this.msg = "Không kiểm tra được tồn kho IP";
					return false; 
				}
				so = 0;
				query = "select count(*) as so from #ERP_TonKhoSANPHAM_CT ";
				rs = db.get(query);
				if(rs != null)
					if(rs.next())
					{
						so = rs.getInt("so");
					}

				if(so == 0)
				{
					db.getConnection().rollback(); 
					this.msg = "Không kiểm tra được tồn kho IP";
					return false; 
				}

				query = "select distinct  '" + this.id + "' as ID,sp.soluong, sp.sanpham_fk" +
						"		  \n" +
						"from ( " + sqlCHECK + " ) sp inner join DONHANG_SANPHAMSPIP dhsp on sp.sanpham_fk = dhsp.sanpham_fk "
						+ " where ABS(dhsp.SLCSX) <> sp.soluong and dhsp.donhang_fk = '"+this.id+"' ";
				System.out.println("QRSP: "+query);

				rs = db.get(query);
				if(rs != null)
				{
					while(rs.next())
					{


						query = " select sanpham_fk,AVAILABLE,MAKH,UuTien,KHACHHANG_FK,ASM,2 as loai "
								+ "\n from #ERP_TonKhoSANPHAM_CT where sanpham_fk = "+rs.getString("sanpham_fk")+""
								+ "\n  and AVAILABLE > 0 and KHACHHANG_FK = '"+this.khId+"' "
								+ " \n union "
								+ "select sanpham_fk,AVAILABLE,MAKH,UuTien,KHACHHANG_FK,ASM, 1 as loai "
								+ "\n from #ERP_TonKhoSANPHAM_CT where sanpham_fk = "+rs.getString("sanpham_fk")+""
								+ "\n  and AVAILABLE > 0 and KHACHHANG_FK <> '"+this.khId+"' and UuTien <> '1' "
								+ "  order by UuTien desc ,loai desc, AVAILABLE desc ";
						ResultSet rskho = db.get(query);
						if(rskho != null)
						{

							float slcan = 0;
							float soluong  =  rs.getFloat("soluong");
							while(rskho.next())
							{

								float AVAILABLE = rskho.getFloat("AVAILABLE") ;
								if(AVAILABLE >= soluong)
								{
									slcan = soluong;
									
								}else 
								{
									soluong = soluong - AVAILABLE;
									slcan = AVAILABLE;
									AVAILABLE = 0;
								}



								query = "insert DONHANG_SANPHAMIP_CHITIET(DONHANG_SANPHAMIP_FK,KHACHHANG_FK,SMARTID,KHACHHANGUUTIEN,SANPHAM_FK,TONHT,SOLUONG) " +
										"\n select  '" + this.id + "','"+rskho.getString("KHACHHANG_FK")+"', '"+rskho.getString("MAKH")+"','"+rskho.getString("uutien")+"',"+
										"\n '"+rs.getString("sanpham_fk") +"', '"+ rskho.getFloat("AVAILABLE")+"', '"+ slcan+"' ";
								if(db.updateReturnInt(query) <= 0)
								{
									db.getConnection().rollback(); 
									this.msg = "4.Không thể cập nhật 'DONHANG_SANPHAMIP_CHITIET' , " + query;
									return false; 
								}
								if(AVAILABLE >= soluong)
								{
									
									break;
								}
							


							}
						}

					}
				}




				query = 
						"	update donhangIP set TONGGIATRI= "+
								"			isnull(( "+
								"				select SUM(round(b.soluong*b.giamua,0))  as TongGiaTri "+
								"				from donhangip a inner join DONHANG_SANPHAMSPIP b on b.DONHANG_FK=a.PK_SEQ "+
								"				where  a.PK_SEQ=donhangip.PK_SEQ "+
								"				group by b.DONHANG_FK "+
								"			),0) - "+
								"			round(isnull(( "+
								"				select SUM(round(b.soluong*b.giamua,0))  as TongGiaTri "+
								"				from donhangip a inner join DONHANG_SANPHAMSPIP b on b.DONHANG_FK=a.PK_SEQ "+
								"				where  a.PK_SEQ=donhangip.PK_SEQ "+
								"				group by b.DONHANG_FK "+
								"			),0)*("+this.tongchietkhau+"/100.0),0) "+

								"		from DONHANGIP "+
								"		where pk_Seq='"+this.id+"' ";
				if(!db.update(query))
				{
					db.getConnection().rollback();
					this.msg = "Lỗi phát sinh khi cập nhật giá trị đơn hàng " + query;
					return false; 
				}
				db.getConnection().commit();
				db.getConnection().setAutoCommit(true);
			} 
			catch (Exception e)
			{
				e.printStackTrace();
				geso.dms.center.util.Utility.rollback_throw_exception(db);
			}


		}		
		return true;
	}



	public boolean UpdateDhXuatKho(List<ISanphamIP> splist) 
	{
		return true;
	}

	public boolean ChotDh(List<ISanphamIP> splist)
	{
		if(this.spThieuList.size() > 0)
		{
			this.msg = "Sorry, Trong kho khong du so luong mot so san pham ban chon, vui long chon lai so luong...";
			return false;
		}
		try 
		{

			db.update("SET TRANSACTION ISOLATION LEVEL SNAPSHOT;");
			db.update("SET LOCK_TIMEOUT 60000;");
			db.getConnection().setAutoCommit(false);

			String query = "update donhangIP set trangthai= '1' where pk_seq= '" + this.id + "' and TrangThai <> 1 " ;
			if(db.updateReturnInt(query)!=1)
			{
				db.getConnection().rollback();
				this.msg = "Đơn hàng đã chốt rồi";
				return false; 
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);

		} 
		catch(Exception e1) {
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			this.msg="Loi :"+e1.toString();
			return false;
		}
		return true;
	}

	private boolean checkNgayKhoaSo(String ngaygd) 
	{
		geso.dms.distributor.util.Utility util = new geso.dms.distributor.util.Utility();
		String ngayksgn = util.ngaykhoaso(this.nppId);

		if(ngayksgn.equals(""))
			ngayksgn = this.getDateTime();

		String[] ngayks = ngayksgn.split("-");
		String[] ngaydh = ngaygd.split("-");

		Calendar c1 = Calendar.getInstance(); //new GregorianCalendar();
		Calendar c2 = Calendar.getInstance(); //new GregorianCalendar();

		c1.set(Integer.parseInt(ngayks[0]), Integer.parseInt(ngayks[1]) - 1, Integer.parseInt(ngayks[2]));
		c2.set(Integer.parseInt(ngaydh[0]), Integer.parseInt(ngaydh[1]) - 1, Integer.parseInt(ngaydh[2]));

		long songay = (c2.getTime().getTime() - c1.getTime().getTime()) / (24 * 3600 * 1000);

		System.out.print("\nSo ngay la: " + songay + "\n");

		if(songay == 1)
			return true;
		return false;
	}

	private String pxkId = "";
	private String ngayxuatkho = "";
	public void createPxkId()
	{}

	public void createRS() 
	{


		this.createDdkd();	
		this.createChietkhau();
		this.createBgstId();
		this.createKho();	


		String query = "select * from nhanviengiaonhan where NPP_FK = "
				+ this.nppId;
		this.nvgnRs = db.get(query);
	}


	private void createDdkd()
	{
		//tao gsbh
		String sql ="select a.pk_seq,a.ten from giamsatbanhang a  where   a.ismt = '1'  and  a.trangthai = '1' ";
		this.gsbhs = db.get(sql);

		String query = "select ten as ddkdTen, pk_seq as ddkdId from daidienkinhdoanh where trangthai = '1' and ismt = '1'  ";
		this.ddkdlist = db.get(query);
	}

	private void createBgstId()
	{}

	private void createChietkhau()
	{
		if(this.chietkhau.equals("0") || this.chietkhau == "")
		{
			String sql ="select ISNULL(b.chietkhau,0 ) as chietkhau from KHACHHANG a left join MUCCHIETKHAU b on a.CHIETKHAU_FK = b.pk_seq where a.PK_SEQ = '" + khId + "'";
			System.out.println("% ck kh "+sql);
			ResultSet rs = db.get(sql);
			if(rs != null)
			{
				try 
				{
					rs.next();
					if(rs.getString("chietkhau") != null)
						if(this.chietkhau.length() == 0)
							this.chietkhau = rs.getString("chietkhau");
					rs.close();
				} 
				catch(Exception e) {}			
			}
		}
	}

	private void createKho()
	{
		this.kholist = db.get("select distinct PK_SEQ as khoId, Ten, Diengiai from KHO where PK_SEQ in (select kho_fk from NHAPP_KHO where npp_fk = '" + this.nppId + "') order by PK_SEQ ASC");
	}

	public void CreateSpList()
	{	
		DecimalFormat df2 = new DecimalFormat( "#,###,###,##" );
		String makh = "";
		String query = " select smartid from khachhang where pk_seq  ="+khId;
		System.out.println("KH: "+query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try {
				if(rs.next())
				{
					makh = rs.getString("smartid");
				}
				rs.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}

		}
		int khut = 0;
		if(makh.length() >0 )
		{
			OracleConnUtils dbsys=new OracleConnUtils();

			query = "select ITEM_CODE,sum(NVL(COUNT_LOT,0)) as SoLuong from apps.SGP_IP_ONHAND where CUSTOMER_NUMBER = '"+makh+"' or    ASSIGN_CUS IS NULL group by ITEM_CODE "; 

			ResultSet rssys=dbsys.get(query); 

			/* 	query = "select ITEM_CODE,COUNT_LOT,CUSTOMER_NUMBER,ASSIGN_CUS from TonKhoIPTEST  ";
					ResultSet rssys = db.get(query); */
			/* 	if(rssys != null)
					{	 */	

			/*  query="IF OBJECT_ID('tempdb.dbo.#TonKhoIP') IS NOT NULL "
								+"\n DROP TABLE #TonKhoIP "
								+"\n CREATE TABLE #TonKhoIP ("
								+"\n COUNT_LOT numeric(18,0),  "
								+"\n ITEM_CODE varchar(100),"
								+"\n CUSTOMER_NUMBER varchar(100),"
								+ "\n ASSIGN_CUS varchar(100)"
								+ " )";
					 	db.update(query);
						query=	 " INSERT INTO #TonKhoIP "
							 	+ "select COUNT_LOT,ITEM_CODE,CUSTOMER_NUMBER,ASSIGN_CUS from TonKhoIPTEST "
								+ "  "	;
					 	db.update(query);




						query="IF OBJECT_ID('tempdb.dbo.#ERP_TonKhoSANPHAM') IS NOT NULL "
								+"\n DROP TABLE #ERP_TonKhoSANPHAM "
								+"\n CREATE TABLE #ERP_TonKhoSANPHAM ("
								+"\n sanpham_fk numeric(18,0),  "
								+"\n AVAILABLE numeric(18,0) )";

						db.update(query);

						query = 	"INSERT INTO #ERP_TonKhoSANPHAM "
								+ "select (select pk_seq from sanpham where ma = ITEM_CODE),sum(COUNT_LOT) as SoLuong from #TonKhoIP "
								+ " where (CUSTOMER_NUMBER = '"+makh+"' or ASSIGN_CUS is null) "
								+ " and (select pk_seq from sanpham where ma = ITEM_CODE) is not null "
								+ " group by ITEM_CODE ";
						System.out.println("LayTonKho: "+query);
						if(db.updateReturnInt(query)<=0)
						{
							System.out.println("Khong co Ton Kho #ERP_TonKhoSANPHAM: "+ query);
						}

						query=	 " INSERT INTO TonKhoSANPHAM "
								+ "select (select pk_seq from sanpham where ma = ITEM_CODE),sum(COUNT_LOT) as SoLuong from #TonKhoIP "
								+ " where (CUSTOMER_NUMBER = '"+makh+"' or ASSIGN_CUS is null) "
								+ "  "
								+ " group by ITEM_CODE ";
						if(db.updateReturnInt(query)<=0)
						{
							System.out.println("Khong co ton kho TonKhoSANPHAM: "+ query);
						} */
			/*	 try {
							while(rssys.next())
								{
								query=	 " INSERT INTO #TonKhoIP "
									 	+ " select '"+rssys.getString("COUNT_LOT")+"',  '"+rssys.getString("ITEM_CODE")+"','"+rssys.getString("CUSTOMER_NUMBER")+"','"+rssys.getString("ASSIGN_CUS")+"' "
										+ "  "	;
								if(db.updateReturnInt(query)<=0)
								{
									System.out.println("Khong co ton kho: "+ query);
								}
								}
						} catch (SQLException e) {

							e.printStackTrace();
						}*/
			//}

			if(rssys != null)
			{
				query="IF OBJECT_ID('tempdb.dbo.#ERP_TonKhoSANPHAM') IS NOT NULL "
						+"\n DROP TABLE #ERP_TonKhoSANPHAM "
						+"\n CREATE TABLE #ERP_TonKhoSANPHAM ("
						+"\n sanpham_fk numeric(18,0),  "
						+"\n AVAILABLE numeric(18,0) )";

				db.update(query);
				try {
					while(rssys.next())
					{

						query=	 " INSERT INTO #ERP_TonKhoSANPHAM "
								+ " select (select pk_seq from sanpham where ma = '"+rssys.getString("ITEM_CODE")+"') as sanpham_fk,  '"+rssys.getString("SoLuong")+"' "
								+ " where  (select pk_seq from sanpham where ma = '"+rssys.getString("ITEM_CODE")+"') is not null "	;
						if(db.updateReturnInt(query)<=0)
						{
							System.out.println("Khong co ton kho: "+ query);
						}

						/* 	query=	 " INSERT INTO TonKhoSANPHAM "
								 	+ " select (select pk_seq from sanpham where ma = '"+rssys.getString("ITEM_CODE")+"') as sanpham_fk,  '"+rssys.getString("SoLuong")+"'"
								 	+	 "where  (select pk_seq from sanpham where ma = '"+rssys.getString("ITEM_CODE")+"') is not null "	;
							if(db.updateReturnInt(query)<=0)
							{
								System.out.println("Khong co ton kho: "+ query);
							}  */
					
					}
					rssys.close();	
				} catch (SQLException e) {
					
					e.printStackTrace();
				}

			/*	query=	 " INSERT INTO TonKhoSANPHAM "
						+ "select (select pk_seq from sanpham where ma = ITEM_CODE),sum(COUNT_LOT) as SoLuong from #TonKhoIP "
						+ " where (CUSTOMER_NUMBER = '"+makh+"' or ASSIGN_CUS is null) "
						+ "  "
						+ " group by ITEM_CODE ";
				if(db.updateReturnInt(query)<=0)
				{
					System.out.println("Khong co ton kho TonKhoSANPHAM: "+ query);
				}*/


			

			}
			query = "select NVL(ASSIGN_CUS,'0') as Uutien from apps.SGP_IP_ONHAND where CUSTOMER_NUMBER = '"+makh+"'   ";
			rssys=dbsys.get(query); 


			try {
				if(rssys.next())
				{
					if(!rssys.getString("Uutien").equals("0"))
						khut = 1;
				}
				rssys.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}

			dbsys.shutDown();	


			if(!this.trangthai.equals("1"))
			{
				/*query = "update a set a.AVAILABLE = a.AVAILABLE - b.SL "+
				" from #ERP_TonKhoSANPHAM a inner join ( "+
				"select SUM(b.SOLUONG) as SL,SANPHAM_FK "+ 
				"\n from  DONHANGIP a inner join  DONHANG_SANPHAMIP_CHITIET b on a.PK_SEQ = b.DONHANG_SANPHAMIP_FK "+
				"\n where a.KHACHHANG_FK = '"+khId+"' or LEN(isnull(khachhanguutien,''))  <= 0 and a.trangthai in (0,9)"+
				 "\n group by SANPHAM_FK ) b on a.sanpham_fk = b.SANPHAM_FK "; */

				if(khut == 0)
				{
					// trường hợp kh không ưu tiên, chỉ trừ kho của những khách hàng không ưu tiên
				
					query = "update a set a.AVAILABLE = case when a.AVAILABLE - b.SL > 0 then   a.AVAILABLE - b.SL "
							+ " else 0 end  "+
							" from #ERP_TonKhoSANPHAM a inner join ( "+
							"select SUM(b.SOLUONG) as SL,SANPHAM_FK "+ 
							"\n from  DONHANGIP a inner join  DONHANG_SANPHAMIP_CHITIET b on a.PK_SEQ = b.DONHANG_SANPHAMIP_FK "+
							"\n where isnull(KHACHHANGUUTIEN,'0') = 0  and a.trangthai in (0,9) "+
							"\n group by SANPHAM_FK ) b on a.sanpham_fk = b.SANPHAM_FK ";
					db.update(query);

					System.out.println("GiamTon1: "+query);
					
					// Trừ số lượng cần sản xuất 
					query = "update a set a.AVAILABLE = a.AVAILABLE - b.SL  "
							+ "   "+
							" from #ERP_TonKhoSANPHAM a inner join ( "+
							"select SUM(abs(b.SLCSX)) as SL,SANPHAM_FK "+ 
							"\n from  DONHANGIP a inner join  DONHANG_SANPHAMSPIP b on a.PK_SEQ = b.DONHANG_FK "+
							"\n where  a.trangthai in (0,9) "+
							"\n group by SANPHAM_FK ) b on a.sanpham_fk = b.SANPHAM_FK ";
					db.update(query);

					System.out.println("GiamTon1: "+query);
				}
				else
				{
					query = "update a set a.AVAILABLE =  a.AVAILABLE - b.SL  "
							+ "   "+
							" from #ERP_TonKhoSANPHAM a inner join ( "+
							"select SUM(b.SOLUONG) as SL,SANPHAM_FK "+ 
							"\n from  DONHANGIP a inner join  DONHANG_SANPHAMSPIP b on a.PK_SEQ = b.DONHANG_FK "+
							"\n where  a.trangthai in (0,9)"+
							"\n group by SANPHAM_FK ) b on a.sanpham_fk = b.SANPHAM_FK ";
					System.out.println("GiamTon2: "+query);
					db.update(query);

				}
			
			}

		}
		String command = "select DISTINCT b.pk_seq as spId, b.ma as spMa, b.ten as spTen, a.soluong, isnull((select top(1) dv.DONVI from DONVIDOLUONG dv inner join  QUYCACH  qc on dv.PK_SEQ = qc.DVDL2_FK  where DVDL2_FK = (select PK_SEQ from DONVIDOLUONG where DONVI = 'Cuo') and qc.SANPHAM_FK = b.pk_seq),c.donvi) as donvi, a.giamua as dongia, a.chietkhau, a.ptCKDLN, a.ptCKTT, a.ptCKDH , isnull(d.AVAILABLE,0) as hienhuu,isnull(a.DonGiaGoc,0) as GiaGoc,isnull(a.BangGia_FK,0) as  bgID  "
				+ " , isnull((select top(1) qc.SOLUONG1 from  QUYCACH  qc where DVDL2_FK = (select PK_SEQ from DONVIDOLUONG where DONVI = 'Cuo') and qc.SANPHAM_FK = b.pk_seq),1)  as quycachkg " +
				"from DONHANG_SANPHAMSPIP a inner join sanpham b on a.sanpham_fk = b.pk_seq left join donvidoluong c on b.dvdl_fk = c.pk_seq left join #ERP_TonKhoSANPHAM d on b.PK_SEQ = d.SANPHAM_FK " +
				"where a.donhang_fk = '" + this.id + "'   ";

		System.out.println("2.San pham list:" + command);

		ResultSet splistRs = db.get(command);
		float tonggiatri = 0f; 
		List<ISanphamIP> splist = new ArrayList<ISanphamIP>();
		if(splistRs != null)
		{
			String[] param = new String[12];
			ISanphamIP sp = null;	
			try
			{
				while(splistRs.next())
				{
					param[0] = splistRs.getString("spId");
					param[1] = splistRs.getString("spma");
					param[2] = splistRs.getString("spten");
					param[3] = splistRs.getString("soluong");
					param[4] = splistRs.getString("donvi");
					param[5] = splistRs.getString("dongia");
					param[6] = "";
					param[8] = splistRs.getString("ptCKDLN")==null?"0":splistRs.getString("ptCKDLN");
					param[9] = splistRs.getString("ptCKTT")==null?"0":splistRs.getString("ptCKTT");
					param[10] = splistRs.getString("ptCKDH")==null?"0":splistRs.getString("ptCKDH");



					param[7] = "0";
					param[11] = splistRs.getString("quycachkg")==null?"0":splistRs.getString("quycachkg");
					tonggiatri += Float.parseFloat(param[5]) * Float.parseFloat(param[3]);

					sp = new SanphamIP(param);
					sp.setTonhientai(splistRs.getString("hienhuu"));
					sp.setGiagoc(  splistRs.getString("GiaGoc"));
					sp.setBgId(splistRs.getString("bgId"));

					splist.add(sp);

				}
				if(splistRs!=null){
					splistRs.close();
				}
			} 
			catch(Exception e) 
			{
				e.printStackTrace();
				System.out.println("115.Exception khoi tao: " + e.getMessage());
			}
		}
		this.splist = splist;

	}

	public void CreateSpDisplay()
	{		
		DecimalFormat df2 = new DecimalFormat( "#,###,###,##" );
		String command = "";
		command = "select DISTINCT b.pk_seq as spId, b.ma as spMa, b.ten as spTen, a.soluong, isnull(c.donvi, 'Chua xac dinh') as donvi, a.giamua as dongia, isnull(a.chietkhau, '0') as chietkhau,isnull(a.DonGiaGoc, '0')  as GiaGoc,isnull(a.BangGia_FK, '0')  as bgId ";
		command = command + "from DONHANG_SANPHAMSPIP a inner join sanpham b on a.sanpham_fk = b.pk_seq left join donvidoluong c on b.dvdl_fk = c.pk_seq";
		command = command + " where a.donhang_fk = '" + this.id + "'";
		//System.out.println("San pham list:" + command);
		ResultSet splistRs = db.get(command);
		List<ISanphamIP> splist = new ArrayList<ISanphamIP>();
		if(splistRs != null)
		{
			String[] param = new String[12];
			ISanphamIP sp = null;	
			try 
			{
				while(splistRs.next())
				{
					param[0] = splistRs.getString("spId");
					param[1] = splistRs.getString("spma");
					param[2] = splistRs.getString("spten");
					param[3] = splistRs.getString("soluong");
					param[4] = splistRs.getString("donvi");
					param[5] = splistRs.getString("dongia");
					param[6] = "";

					float ck = splistRs.getFloat("chietkhau");
					int slg = Integer.parseInt(param[3]);
					float tt = 0f;
					if(slg != 0)
					{	
						tt = (ck / (Integer.parseInt(param[3]) * Float.parseFloat(param[5]))) * 100;
						tt = new Float(df2.format(tt)).floatValue(); //lam tron 2 so
					}
					this.chietkhau = Float.toString(tt);

					param[7] = this.chietkhau;

					float tonggiatri = Float.parseFloat(param[5]) * Float.parseFloat(param[3]);

					sp = new SanphamIP(param);
					sp.setTonhientai(splistRs.getString("hienhuu"));
					sp.setGiagoc(  splistRs.getString("GiaGoc"));
					sp.setBgId(splistRs.getString("bgId"));

					splist.add(sp);
				}
				if(splistRs!=null){
					splistRs.close();
				}
			} 
			catch(Exception e) {}
		}
		this.splist = splist;
	}

	String kbhId = "";
	public void init()
	{	
		NumberFormat formatter = new DecimalFormat("#,###,###");

		String query = "select isnull(a.ghichu, '') as ghichu,isnull(a.ngaygiao,'') as ngaygiao,a.gsbh_fk,a.pk_seq as dhId, a.ngaynhap, a.trangthai, a.ngaytao, a.ngaysua, a.khachhang_fk as khId, g.ten as khTen, g.diachi, isnull(g.smartid, '') as smartid,  a.kho_fk as khoId, a.tonggiatri, b.ten as nguoitao, c.ten as nguoisua, e.pk_seq as ddkdId, e.ten as ddkdTen, '' as nppTen, a.VAT, isnull(a.chietkhau, 0) as chietkhau, isnull(a.chuyen, 0) as ptck, a.kbh_fk,a.IsChiNhanh, isnull(a.NoCu,0) Nocu,a.nvgn_fk,sotiengiam ";
		query = query + " from donhangip a left join nhanvien b on a.nguoitao = b.pk_seq left join nhanvien c on a.nguoisua = c.pk_seq inner join daidienkinhdoanh e on a.ddkd_fk = e.pk_seq ";
		query = query + " inner join khachhang g on a.khachhang_fk=g.pk_seq ";
		query = query + " where a.pk_seq='" + this.id + "'";

		System.out.println("1.Init don hang:" + query);
		ResultSet rs =  db.get(query);
		if(rs != null)
		{
			try
			{
				rs.next();
				this.id = rs.getString("dhId");
				this.khId = rs.getString("khId");
				this.khTen = rs.getString("khTen") + " - " + rs.getString("diachi");
				this.smartId = rs.getString("smartid"); 
				this.ngaygiaodich = rs.getString("ngaynhap");
				this.daidienkd = rs.getString("ddkdTen");
				this.trangthai = rs.getString("trangthai");
				this.ngaytao = rs.getString("ngaytao");
				this.nguoitao = rs.getString("nguoitao");
				this.ngaysua = rs.getString("ngaysua");
				this.nguoisua = rs.getString("nguoisua");		
				this.ngaygiao = rs.getString("ngaygiao");		
				this.VAT = "0";  //TTC, gia SP da co thue
				this.ddkdId = rs.getString("ddkdId");
				this.khoId = rs.getString("khoId");
				System.out.println("11.Kho id: " + this.khoId);
				this.chietkhau = rs.getString("chietkhau");
				this.tongchietkhau = rs.getString("chietkhau");
				this.tongtiensauVAT = rs.getString("tonggiatri");
				this.tongtientruocVAT = rs.getString("VAT");
				this.gsbhId = rs.getString("gsbh_fk");
				this.kbhId = rs.getString("kbh_fk");
				this.nvgnId = rs.getString("nvgn_fk");
				this.IsChiNhanh= rs.getString("IsChiNhanh")==null?"0":rs.getString("isChiNhanh");
				this.ghichu = rs.getString("ghichu");

				this.sotiengiam = rs.getString("sotiengiam");

				rs.close();
			}
			catch(Exception e)
			{
				System.out.println("33.Exception: " + e.getMessage());
			}
		}
		this.nocu = 0;

		try
		{
			NumberFormat formater = new DecimalFormat("##,###,###");
			OracleConnUtils SYNC = new OracleConnUtils();
			String nppma = "";
			query = "select smartid as ma from khachhang where pk_seq = "+this.khId;
			rs = db.get(query);
			if(rs != null && rs.next())
			{
				nppma = rs.getString("ma");
				query = "select debit as nophaithu from apps.SGP_CUS_DEBIT where customer_number = '"+nppma+"' and BLINE_CODE='01'";
				ResultSet congnoRs = null;
				congnoRs = SYNC.get(query);
				if(congnoRs != null && congnoRs.next())
				{
					this.nocu =congnoRs.getDouble("nophaithu");
				}
			}
			SYNC.shutDown();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		String sql ="select a.pk_seq, a.ten from giamsatbanhang a inner join NHAPP_GIAMSATBH b on a.pk_seq = b.gsbh_fk where  b.ngaybatdau <='"+this.getDateTime()+"' and b.ngayketthuc >='"+getDateTime()+"' and a.trangthai = '1'";
		sql+=" union select a.pk_seq, a.ten from giamsatbanhang a  where a.pk_Seq='"+this.gsbhId+"'  ";
		System.out.println("cau query ............."+sql);
		this.gsbhs = db.get(sql);
		if(this.gsbhId == null)
		{
			this.ddkdlist = db.get("select ten as ddkdTen, pk_seq as ddkdId from daidienkinhdoanh where trangthai = '1' and npp_fk = '" + this.nppId + "'");
		}
		else
		{
			sql="select ten as ddkdTen, pk_seq as ddkdId from daidienkinhdoanh where trangthai = '1'  and pk_seq in (select ddkd_fk from ddkd_gsbh where gsbh_fk ='" + this.gsbhId + "')";
			sql+=" union select ten as ddkdTen, pk_seq as ddkdId from daidienkinhdoanh where pk_Seq='"+this.ddkdId+"'";
			this.ddkdlist = db.get(sql );
			System.out.println("___Ddkd___"+sql);
		}

		this.createChietkhau();
		this.createBgstId();
		this.CreateSpList();
		this.createKho();


	}

	public String getBgstId() 
	{
		return this.bgstId;
	}

	public void setBgstId(String bgstId)
	{
		this.bgstId = bgstId;
	}

	public String getKhoId() 
	{
		return this.khoId;
	}

	public void setKhoId(String khoId) 
	{
		this.khoId = khoId;
	}

	public ResultSet getKhoList() 
	{
		return this.kholist;
	}

	public void setKhoList(ResultSet kholist) 
	{
		this.kholist = kholist;
	}

	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);	
	}

	public void DBclose()
	{
		try 
		{
			if(!(this.kholist==null))
				this.kholist =null;

			if(!(this.ddkdlist == null))
				this.ddkdlist.close();
			if(!(this.tbhlist == null))
				this.tbhlist.close();
			if(this.khlist!=null){
				this.khlist.close();
			}
			if(this.gsbhs!=null){
				gsbhs.close();
			}
			splist=null;
			spThieuList=null;
			scheme_tongtien=null;
			scheme_chietkhau=null;
			scheme_sanpham=null;

			this.db.shutDown();
		} 
		catch(Exception e) {}
	}

	public void setMuctindung(String muctindung) {

		this.muctindung = muctindung;
	}

	public String getMuctindung() {

		return this.muctindung;
	}

	public ResultSet getgsbhs()
	{	
		return this.gsbhs;
	}

	public String getTongChietKhau() 
	{
		return this.tongchietkhau;
	}

	public void setTongChietKhau(String tck) 
	{
		this.tongchietkhau = tck;
	}

	public String getKHList()
	{
		String khId = "";
		String khTen = "";
		String khChietKhau = "";
		String khList ="";
		String command = "select b.pk_seq as khId, b.ten as khTen, c.chietkhau " + 
				"from khachhang_tuyenbh a inner join khachhang b on a.khachhang_fk = b.pk_seq "+ 
				"inner join tuyenbanhang d on a.tbh_fk = d.pk_seq " +
				"left join mucchietkhau c on b.chietkhau_fk = c.pk_seq "+ 
				"where b.npp_fk='"+ this.nppId +"' and d.ddkd_fk= '"+ this.ddkdId +"' order by khId, khTen, chietkhau";

		ResultSet kh = db.get(command);
		try{
			if(kh != null)
			{
				while(kh.next())
				{
					khId = khId + kh.getString("khId") + ",";
					khTen = khTen + kh.getString("khTen") + ",";

					if (kh.getString("chietkhau") != null && !kh.wasNull())
						khChietKhau = khChietKhau + kh.getString("chietkhau") + ",";
					else
						khChietKhau = khChietKhau + "0,";

				}

			}
			khId = khId.substring(0, khId.length() - 1);
			khTen = khTen.substring(0, khTen.length() - 1);
			khChietKhau = khChietKhau.substring(0, khChietKhau.length() - 1);

			String[] khIdList = khId.split(",");
			String[] khTenList = khTen.split(",");
			String[] khChietKhauList = khChietKhau.split(",");

			int cnt=1;
			for(int i=0; i<khTenList.length;i++)
			{
				if (i!= khTenList.length-1){
					khList = khList + "\""+ khIdList[i] + "-->[" + khTenList[i] + "][" + khChietKhauList[i] + "]\",";
				}else{
					khList = khList + "\""+ khIdList[i] + "-->[" + khTenList[i] + "][" + khChietKhauList[i] + "]\"";
				}

			}	
			if(kh!=null){
				kh.close();
			}
		}
		catch(Exception e){}
		return khList;
	}

	public boolean isAplaikhuyenmai() 
	{
		return this.aplaikm;
	}

	public void setAplaikhuyenmai(boolean aplaikm) 
	{
		this.aplaikm = aplaikm;
	}





	public String getPxkId() 
	{
		return this.pxkId;
	}

	public void setPxkId(String pxkId) 
	{
		this.pxkId = pxkId;
	}

	public String DeleteDonHangDxk() 
	{
		try
		{
			db.getConnection().setAutoCommit(true);

			String query = "update donhangIP set trangthai='2' where pk_seq = '" + this.id + "'";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "1.Khong the xoa don hang: " + this.id + ", " + query;
			}



			//cap nhat phan bo km
			query = "select ctkmId, sum(tonggiatri) as tonggiatri from DonHangIP_CTKM_TRAKM where donhangid = '" + this.id + "' group by ctkmId";
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				try
				{
					while(rs.next())
					{
						String ctkmId = rs.getString("ctkmId");
						String tonggiatri = rs.getString("tonggiatri");

						String st ="update Phanbokhuyenmai set DASUDUNG = DASUDUNG - '" + tonggiatri + "' where ctkm_fk='" + ctkmId + "' and npp_fk='" + this.nppId + "'";
						//db.update("update CTKhuyenmai set DASUDUNG = DASUDUNG - '" + tonggiatri + "' where ctkm_fk = '" + ctkmId + "'");
						if(!db.update(st)){
							this.db.getConnection().rollback();
							return "Khong The Cap Nhat :"+ st;
						}
					}
					rs.close();
				} 

				catch(Exception e) {
					this.db.getConnection().rollback();
					return e.toString();
				}

			}

			this.createPxkId();
			if(this.pxkId.length() > 0)
			{
				String msg = this.createPth(this.pxkId, db);
				if(msg.length() > 0)
				{
					db.getConnection().rollback();
					return "4.Khong the tao phieu thu hoi cua don hang: " + this.id + ", " + msg;
				}
			}

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);

		}

		catch (Exception e) 
		{
			try 
			{
				db.getConnection().rollback();
			} 
			catch (Exception e1) {}

			return "5.Khong the xoa don hang: " + this.id + ", " + e.getMessage();
		}

		return "";

	}

	public boolean isCokhuyenmai() 
	{
		return this.cokm;
	}

	public void setCokhuyenmai(boolean cokm) 
	{
		this.cokm = cokm;
	}

	public boolean isChuaApkhuyenmai() 
	{	
		return this.chuaApkm;
	}

	public void setIsChuaApkhuyenmai(boolean chuaApkm) 
	{
		this.chuaApkm = chuaApkm;
	}

	public boolean isDamuahang() 
	{
		return this.dacoDh;
	}

	public void setIsDamuahang(boolean damuahang)
	{
		this.dacoDh = damuahang;
	}

	public boolean isDxkChuaChot()
	{
		return this.daxuatkhoChuachot;
	}

	public void setIsDxkChuaChot(boolean cokm) 
	{
		this.daxuatkhoChuachot = cokm;
	}

	public void setNgayKs(String ngayks) 
	{
		this.ngayks = ngayks;
	}

	public String getNgayKs()
	{
		return this.ngayks;
	}

	String IsChiNhanh;


	public String getIsChiNhanh() 
	{
		return IsChiNhanh;
	}


	public void setIsChiNhanh(String isChiNhanh) 
	{
		this.IsChiNhanh =isChiNhanh;	
	}

	String coTrungBay;
	public void setCotrungbay(String cotrungbay) 
	{
		this.coTrungBay = cotrungbay;
	}

	public String getCotrungbay() 
	{
		return this.coTrungBay;
	}


	boolean aplaitb;
	public boolean isAplaitrungbay() 
	{
		return this.aplaitb;
	}

	public void setAplaitrugnbay(boolean aplaitb) 
	{
		this.aplaitb = aplaitb;
	}




	public String getDiachigiaohang() {
		return this.diachigiaohang;
	}

	public void setDiachigiaohang(String diachigiaohang) {
		this.diachigiaohang = diachigiaohang;
	}
	public String getGhiChu() {
		return this.ghichu;
	}

	public void setGhiChu(String ghichu) {
		this.ghichu = ghichu;
	}

	public double getNoCu() 
	{
		return this.nocu;
	}

	public void setNoCu(double nocu) 
	{
		this.nocu = nocu;
	}
	ResultSet nvgnRs;


	public String getnvgnId() {

		return nvgnId;
	}


	public void setnvgnId(String nvgnId) {
		this.nvgnId = nvgnId;

	}


	public ResultSet getnvgnRs() {

		return  nvgnRs;
	}


	public void setSotiengiam(String Sotiengiam) {

		this.sotiengiam = Sotiengiam;
	}


	public String getSotiengiam() {

		return this.sotiengiam;
	}

	@Override
	public String createPth(String pxkId, dbutils db) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean LuuTrungBay(String scheme, Integer soXuat) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Hashtable<String, Integer> ApTrungBay(String dhId, String khId,
			String nppId, String ngaydh) {
		// TODO Auto-generated method stub
		return null;
	}

}
