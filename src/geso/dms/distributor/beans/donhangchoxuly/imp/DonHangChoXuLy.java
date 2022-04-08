package geso.dms.distributor.beans.donhangchoxuly.imp;
import java.io.Serializable;


import geso.dms.distributor.beans.donhangchoxuly.IDonHangChoXuLy;
import geso.dms.distributor.beans.donhangchoxuly.ISanpham;
import geso.dms.distributor.beans.donhangchoxuly.imp.Sanpham;
import geso.dms.distributor.db.sql.*;
import geso.dms.distributor.util.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Hashtable;


public class DonHangChoXuLy implements IDonHangChoXuLy, Serializable
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

	String khoId;
	ResultSet gsbhs;
	ResultSet kholist;
	ResultSet tbhlist;
	ResultSet ddkdlist;

	List<ISanpham> splist;
	float ttCKKM;
	float ttsauCKKM;
	float ttsauCK;
	String tienCK;
	String tongtientruocVAT;
	String tongtiensauVAT;

	Hashtable<String, Integer> spThieuList;

	///trakhuyen mai
	Hashtable<String, Float> scheme_tongtien = new Hashtable<String, Float>();
	Hashtable<String, Float> scheme_chietkhau = new Hashtable<String, Float>();
	List<ISanpham> scheme_sanpham = new ArrayList<ISanpham>();

	boolean aplaikm;
	boolean cokm;
	boolean chuaApkm;
	boolean dacoDh;
	boolean daxuatkhoChuachot;

	String ngayks;

	dbutils db;

	public DonHangChoXuLy(String[] param)
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

		this.db = new dbutils();

	}

	public DonHangChoXuLy(String id)
	{
		this.id = id;
		this.khId = "";
		this.ngaygiaodich = "";
		this.diachigiaohang = "";
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

		this.ngayks = "";
		this.db = new dbutils();	
	}

	public DonHangChoXuLy(String id, String nppId)
	{
		this.id = id;
		this.nppId = nppId;
		this.CreateSpList();
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

	public List<ISanpham> getSpList()
	{	
		return this.splist;
	}

	public void setSpList(List<ISanpham> splist) 
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

	public List<ISanpham> getScheme_SpList() 
	{
		return this.scheme_sanpham;
	}

	public void setScheme_SpList(List<ISanpham> splist) 
	{
		this.scheme_sanpham = splist;
	}

	private void getTrakhuyenmai()
	{
		
		DecimalFormat df2 = new DecimalFormat( "#,###,###,##0.00" );
		List<ISanpham> scheme_sp = new ArrayList<ISanpham>();
		String query = "select  a.DaGiao,a.ConLai, a.ctkmId, a.trakmId, a.soxuat, a.soluong,donvi, a.spMa, a.tonggiatri, b.scheme, c.loai, c.hinhthuc, c.chietkhau,  c.tongluong, c.tongtien, d.ten, d.pk_seq as spId ";
		query = query + "from donhangChoXuLy_ctkm_trakm a inner join ctkhuyenmai b on a.ctkmid = b.pk_seq inner join trakhuyenmai c on a.trakmid = c.pk_seq left join sanpham d on a.spMa = d.ma ";
		query = query +" left join donvidoluong dv on dv.pk_seq=d.dvdl_fk ";
		query = query +	"where a.DHCXLID = '" + this.id + "'";

		System.out.println("1.Khoi tao TKM:"+ query);

		ResultSet rs = db.get(query);
		/*if( rs != null)*/
		{
			try 
			{
				while(rs.next())
				{
					String schemeName = rs.getString("scheme");				
					String trakmId = rs.getString("trakmId");
					String soxuat = rs.getString("soxuat");	
					String soluong = rs.getString("soluong");
					String loai = rs.getString("loai");
					String hinhthuc = rs.getString("hinhthuc");
					String tongiatri = rs.getString("tonggiatri");
					String donvi=rs.getString("donvi");
					float tongtien = 0;
					float chietkhau = 0;
					String spMa = "";
					this.ttCKKM = 0;

					if (loai == null){
						if(rs.getString("spMa") == null){
							if(rs.getString("tongtien") != null)
								tongtien = Float.parseFloat(rs.getString("tongtien"));
							this.scheme_tongtien.put(schemeName, tongtien * Float.parseFloat(soxuat));
							this.aplaikm = true; //co ctkm
							this.cokm = true;
						}
					}else{
						if(loai.equals("1")) //tra tien
						{
							if(rs.getString("tongtien") != null)
								tongtien = Float.parseFloat(rs.getString("tongtien"));

							this.scheme_tongtien.put(schemeName, tongtien *  Float.parseFloat(soxuat));
							this.aplaikm = true;
							this.cokm = true;
						}
						else
						{
							if(loai.equals("2")) //tra chiet khau
							{
								if(rs.getString("chietkhau") != null)
									chietkhau = Float.parseFloat(rs.getString("chietkhau"));

								this.scheme_chietkhau.put(schemeName, Float.parseFloat(tongiatri));
								this.ttCKKM = this.ttCKKM + Float.parseFloat(tongiatri);
								this.aplaikm = true;
								this.cokm = true;

								System.out.println("1.Tra chiet khau");

							}
							else //tra sp
							{
								//String sql = "select a.sanpham_fk as spId, a.soluong, b.ma, b.ten from trakhuyenmai_sanpham a inner join sanpham b on a.sanpham_fk = b.pk_seq where a.trakhuyenmai_fk = '" + trakmId + "'";
								//String sql = "select a.spMa, a.soluong, b.pk_seq as spId, b.ten from donhang_ctkm_trakm a inner join sanpham b on a.spMa = b.ma where ";

								//ResultSet sanphamRs = db.get(sql);					
								String[] param = new String[11];
								ISanpham sp = null;	

								param[0] = rs.getString("spId");
								param[1] = rs.getString("spMa");
								param[2] = rs.getString("ten");
								param[3] = soluong;
								param[4] = donvi;
								param[5] = "0";
								param[6] = schemeName;
								param[7] = "0";
								param[8] = "0";
								param[9] = "0";
								param[10] = "0";


								sp = new Sanpham(param);
								scheme_sp.add(sp);
								
								sp.setConLai( df2.format(  rs.getDouble("ConLai"))   );
								sp.setDaGiao(df2.format(  rs.getDouble("DaGiao"))   );
								
								this.aplaikm = true;
								this.cokm = true;

							}
						}
					}
				}
				rs.close();
			} 
			catch(Exception e) {e.printStackTrace();}
		}
		this.scheme_sanpham = scheme_sp;

	}

	private void getNppInfo()
	{	
		geso.dms.distributor.util.Utility util = new geso.dms.distributor.util.Utility();
		this.nppId = util.getIdNhapp(this.userId);
		this.nppTen = util.getTenNhaPP();
		this.sitecode = util.getSitecode();
		this.IsChiNhanh =util.getIsChinhNhanh();
		//lay ngay khoa so
		this.ngayks = util.ngaykhoaso(this.nppId);
	}

	public boolean Muctindung()
	{ 
		if(this.nppId.equals("102961"))
		{
			if(this.khId.length()>0)
			{
				float sotienno =0;
				if(khId.length()>0)
				{
					String sql =" select sotienno,a.pk_seq from khachhang a inner join gioihancongno b on a.ghcn_fk = b.pk_seq where a.pk_seq ='"+ this.khId +"'";
					//System.out.println("sotienno:"+sql);
					ResultSet tb = db.get(sql);
					if(tb != null)
					{
						try {
							if(tb.next())
								sotienno = Float.parseFloat(tb.getString("sotienno"));
							else
								sotienno = 0;
							tb.close();
						} catch(Exception e1) {

							e1.printStackTrace();
						}
						if(this.id.length()>0)
							sql =" select khachhang_fk ,sum(tonggiatri-dathanhtoan) as num from donhang where pk_seq <>'"+ this.id +"' and tonggiatri > dathanhtoan and khachhang_fk ='"+ this.khId +"' group by khachhang_fk";
						else
							sql =" select khachhang_fk ,sum(tonggiatri-dathanhtoan) as num from donhang where tonggiatri > dathanhtoan and khachhang_fk ='"+ this.khId +"' group by khachhang_fk";
						//System.out.println("tongtienno:"+sql);
						ResultSet rs= db.get(sql);
						try {

							if(rs != null)
							{
								if(rs.next()){
									if(rs.getString("num").length() > 0)
										sotienno = sotienno -  Float.parseFloat(rs.getString("num"));
								}
							}	
							rs.close();
						} catch(Exception e) {
							e.printStackTrace();
						}
					}


				}
				this.muctindung = sotienno + "";
			}
		}
		else
			this.muctindung = "99999999999"; //gia tri don hang khong the vuot qua
		return false;
	}

	private String kenh()
	{ 
		//	String sql ="select a.kbh_fk from giamsatbanhang a,ddkd_gsbh b where a.pk_seq = b.gsbh_fk and b.ddkd_fk ='"+ this.ddkdId +"'";
		String sql ="select kbh_fk from giamsatbanhang where pk_seq ='"+ this.gsbhId +"'";
		ResultSet rs = db.get(sql);
		if(rs != null)
		{
			try 
			{
				rs.next();
				String kbhfk= rs.getString("kbh_fk");
				rs.close();
				return kbhfk;
			} 

			catch(Exception e) {}
		}
		return "null";
	}

	public boolean CreateDh(List<ISanpham> splist) 
	{
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
				ISanpham sp = splist.get(m);
				sqlCHECK += " select pk_seq as sanpham_fk, '" + sp.getSoluong() +  "' as soluong, '" + sp.getDongia().replaceAll(",", "") + "' as dongia, '" + sp.getChietkhau().replaceAll(",", "") + "' as chietkhau , '"+sp.getGiagoc().replaceAll(",", "")+"' as dongiaGOC,'"+sp.getBgId()+"' as BangGia_fk,  " +
						"'" + sp.getChietkhauDLN().replaceAll(",", "") + "' as chietkhauDLN, '" + sp.getChietkhauTT().replaceAll(",", "") + "' as chietkhauTT, '" + sp.getChietkhauDH().replaceAll(",", "") + "' as chietkhauDH  from SANPHAM where ma = '" + sp.getMasanpham() + "'  ";
				if(m < splist.size() - 1)
					sqlCHECK += " union ";
			}

			try
			{

				db.update("SET TRANSACTION ISOLATION LEVEL SNAPSHOT;");
				db.update("SET LOCK_TIMEOUT 60000;");
				db.getConnection().setAutoCommit(false);
				String query =
						"	declare @ngaynhap nvarchar(10), @kbh_fk numeric(18, 0),@BM_fk numeric(18, 0),@AMS_fk numeric(18, 0)  ,@IsChiNhanh bit ;  "+
								"	select @kbh_fk = kbh_fk from khachhang where pk_seq ='"+this.khId+"' " +
								" 	select @ngaynhap='"+this.ngaygiaodich+"'   "+
								"	select @IsChiNhanh = IsChiNhanh from NHAPHANPHOI where PK_SEQ='"+this.nppId+"' "+
								"	select top 1 @BM_fk=bm_cn.bm_fk,@AMS_fk= a.asm_fk from  asm_khuvuc a "+ 
								"			inner join asm on asm.pk_seq=a.asm_fk AND A.NGAYBATDAU <=@ngaynhap AND A.NGAYKETTHUC >=@ngaynhap "+
								"			inner join khuvuc kv on kv.pk_seq=a.khuvuc_fk "+
								"			inner join bm_chinhanh bm_cn on bm_cn.vung_fk=kv.vung_fk AND bm_cn.NGAYBATDAU <=@ngaynhap AND bm_cn.NGAYKETTHUC >=@ngaynhap "+
								"			inner join nhaphanphoi npp on npp.khuvuc_fk=kv.pk_seq "+
								"			inner join bm on bm.pk_seq=bm_cn.bm_fk and bm.kbh_fk=@kbh_fk and bm.dvkd_fk=(select dvkd_fk from giamsatbanhang where pk_seq='"+this.gsbhId+"') "+
								"	where npp.pk_seq='"+this.nppId+"' and asm.kbh_fk=@kbh_fk and asm.dvkd_fk=(select dvkd_fk from giamsatbanhang where pk_seq='"+this.gsbhId+"') "+
								"	insert into DonHang(ngaynhap, trangthai, ngaytao, ngaysua, nguoitao, nguoisua, vat, tonggiatri, ddkd_fk, gsbh_fk, khachhang_fk, npp_fk, kho_fk, kbh_fk, tinhtrang,BM,ASM,IsChiNhanh, diachigiao) "+
								"	values('"+this.ngaygiaodich+"', '0','"+this.getDateTime()+"','"+this.getDateTime()+"', '"+this.userId+"', '"+this.userId+"','"+this.VAT+"' , '"+this.tongtiensauVAT.replaceAll(",", "")+"','"+this.ddkdId+"','"+this.gsbhId+"','"+this.khId+"', '"+this.nppId+"', '"+this.khoId+"', @kbh_fk, '0',@BM_fk,@AMS_fk,@IsChiNhanh, N'"+this.diachigiaohang+"') ";
				if(!db.update(query))
				{
					this.msg = "Lỗi cập nhật đơn hàng"+ query;
					this.db.getConnection().rollback();
					return false;
				}

				ResultSet rsDDH = db.get("select scope_identity() as ID ");
				if(rsDDH.next())
				{
					this.id = rsDDH.getString("ID");
				}
				rsDDH.close();


				//Chèn donhang_sanpham tận dung luôn câu SQL bên trên, kể cả hàm cập nhật tồn kho, cũng chỉ chạy 1 lượt
				query = "if exists (select PK_SEQ from DONHANG WHERE PK_SEQ = '"+this.id+"' AND TRANGTHAI = 0)" +
						"insert donhang_sanpham( sanpham_fk, donhang_fk, soluong, kho_fk, giamua, chietkhau, dongiaGOC,BangGia_fk, ptCKDLN, ptCKTT, ptCKDH ) " +
						"select sp.sanpham_fk, '" + this.id + "', sp.soluong, '" + this.khoId + "', sp.dongia, sp.chietkhau ,sp.dongiaGOC,sp.BangGia_fk, sp.chietkhauDLN, sp.chietkhauTT, sp.chietkhauDH " +
						"from ( " + sqlCHECK + " ) sp ";
				if(db.updateReturnInt(query) <= 0)
				{
					db.getConnection().rollback(); 
					this.msg = "4.Khong the cap nhat table 'Don Hang San pham' , " + query;
					return false; 
				}

				//Cap nhat kho
				query = "update kho set kho.booked = kho.booked + sp.SOLUONG, kho.available = kho.available - sp.SOLUONG " + 
						"from DONHANG dh inner join DONHANG_SANPHAM sp on dh.PK_SEQ = sp.DONHANG_FK   " + 
						"inner join nhapp_kho kho on sp.SANPHAM_FK = kho.SANPHAM_FK and sp.KHO_FK = kho.KHO_FK and kho.NPP_FK = dh.NPP_FK and kho.KBH_FK = dh.KBH_FK " + 
						"where dh.PK_SEQ = " + this.id +" and dh.trangthai=0" ;
				if(db.updateReturnInt(query) <= 0)
				{
					db.getConnection().rollback(); 
					this.msg = "Lỗi khi cập nhật tồn kho: " + query;
					return false; 
				}

				query = 
						"	update DONHANG set TONGGIATRI= "+
								"			isnull(( "+
								"				select SUM(b.soluong*b.giamua)  as TongGiaTri "+
								"				from DONHANG a inner join DONHANG_SANPHAM b on b.DONHANG_FK=a.PK_SEQ "+
								"				where  a.PK_SEQ=DONHANG.PK_SEQ "+
								"				group by b.DONHANG_FK "+
								"			),0)- "+
								"			isnull "+
								"				(( "+
								"					select SUM(TONGGIATRI) from DONHANG_CTKM_TRAKM km  "+
								"					where km.DONHANGID=DONHANG.PK_SEQ and SPMA is null "+
								"				),0)  "+
								"		from DONHANG  "+
								"		where pk_Seq='"+this.id+"' ";
				if(!db.update(query))
				{
					db.getConnection().rollback();
					this.msg = "Lỗi phát sinh khi cập nhật giá trị đơn hàng " + query;
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

	public boolean UpdateDh(List<ISanpham> splist) 
	{	
		if(this.spThieuList.size() > 0)
		{
			this.msg = "Trong kho khong du so luong mot so san pham ban chon, vui long chon lai so luong...";
			return false;
		}
		else
		{

			try{
				String sql="select kbh_fk from donhang where pk_seq= '"+this.id+"' and kbh_fk  =  (select kbh_fk from khachhang where pk_seq='"+this.khId+"') ";

				System.out.println(sql);
				ResultSet rs= this.db.get(sql);
				if(rs!=null){
					if(!rs.next()){
						this.msg="Khong The Luu Don Hang Voi Khach Hang Co Kenh  Khac Kenh Luc Tao Moi Don Hang,Vui Long Chon Lai Khach Hang,Hay Doi Kenh Cua Khach Hang";
						return false;
					}
					rs.close();
				}
			}catch(Exception er)
			{
				this.msg="Khong The Luu Don Hang,Khong xac Dinh Kenh Ban Hang Cua Khach Hang :"+this.khId+".Loi :"+er.toString() ; 
				return false;
			}

			String sqlCHECK = "";
			for(int m = 0; m < splist.size(); m++)
			{
				ISanpham sp = splist.get(m);
				sqlCHECK += " select pk_seq as sanpham_fk, '" + sp.getSoluong() +  "' as soluong, '" + sp.getDongia().replaceAll(",", "") + "' as dongia, '" + sp.getChietkhau().replaceAll(",", "") + "' as chietkhau , '"+sp.getGiagoc().replaceAll(",", "")+"' as dongiaGOC,'"+sp.getBgId()+"' as BangGia_fk,  " +
						"'" + sp.getChietkhauDLN().replaceAll(",", "") + "' as chietkhauDLN, '" + sp.getChietkhauTT().replaceAll(",", "") + "' as chietkhauTT, '" + sp.getChietkhauDH().replaceAll(",", "") + "' as chietkhauDH  from SANPHAM where ma = '" + sp.getMasanpham() + "'  ";
				if(m < splist.size() - 1)
					sqlCHECK += " union ";
			}

			String query = "";



			try
			{
				db.update("SET TRANSACTION ISOLATION LEVEL SNAPSHOT;");
				db.update("SET LOCK_TIMEOUT 60000;");

				db.getConnection().setAutoCommit(false);
				String kbh_fk = "", BM_fk = "", ASM_fk = "";
				int trangthai=-1;
				query = "select  (select trangthai from DonHang where pk_Seq ='"+this.id+"') as TrangThai ,(select kbh_fk from khachhang where pk_seq = " + this.khId +" ) as kbh_Fk " ; 
				ResultSet	rs = this.db.get(query);
				rs.next();
				kbh_fk = rs.getString("kbh_fk");
				trangthai= rs.getInt("trangthai");

				query = "select top 1 bm_cn.bm_fk, a.asm_fk from  asm_khuvuc a " +
						"inner join asm on asm.pk_seq=a.asm_fk AND A.NGAYBATDAU <= '"+this.ngaygiaodich+"' AND A.NGAYKETTHUC >= '"+this.ngaygiaodich+"' " +
						"inner join khuvuc kv on kv.pk_seq=a.khuvuc_fk " +
						"inner join bm_chinhanh bm_cn on bm_cn.vung_fk=kv.vung_fk AND bm_cn.NGAYBATDAU <='"+this.ngaygiaodich+"' AND bm_cn.NGAYKETTHUC >= '"+this.ngaygiaodich+"' " +
						"inner join nhaphanphoi npp on npp.khuvuc_fk=kv.pk_seq " +
						"inner join bm on bm.pk_seq=bm_cn.bm_fk and bm.kbh_fk = "+kbh_fk+" and bm.dvkd_fk=(select dvkd_fk from giamsatbanhang where pk_seq = "+this.gsbhId+") " +
						"where npp.pk_seq = "+this.nppId+" and asm.kbh_fk = "+kbh_fk+" and asm.dvkd_fk=(select dvkd_fk from giamsatbanhang where pk_seq = "+this.gsbhId+" )	";

				System.out.println("__"+query);

				rs = this.db.get(query);
				while(rs.next())
				{
					BM_fk = rs.getString("bm_fk");
					ASM_fk = rs.getString("asm_fk");
				}

				if(BM_fk == null || BM_fk.length() == 0)
					BM_fk = "NULL";
				if(ASM_fk == null || ASM_fk.length() == 0)
					ASM_fk = "NULL";


				if(trangthai==0)
				{
					query = "update kho set kho.booked = kho.booked - sp.SOLUONG, kho.available = kho.available + sp.SOLUONG " + 
							"from DONHANG dh inner join DONHANG_SANPHAM sp on dh.PK_SEQ = sp.DONHANG_FK   " + 
							"inner join nhapp_kho kho on sp.SANPHAM_FK = kho.SANPHAM_FK and sp.KHO_FK = kho.KHO_FK and kho.NPP_FK = dh.NPP_FK and kho.KBH_FK = dh.KBH_FK " + 
							"where dh.PK_SEQ = " + this.id +" " ;
					query +=" and dh.trangthai=0 ";
					if(db.updateReturnInt(query) <= 0)
					{
						db.getConnection().rollback(); 
						this.msg = "Lỗi khi cập nhật tồn kho: " + query;
						return false; 
					}
				}


				query =
						"  update donhang set ngaynhap ='"+this.ngaygiaodich+"', ddkd_fk ='"+this.ddkdId+"', gsbh_fk ='"+this.gsbhId+"', khachhang_fk ='"+this.khId+"', KHO_FK = '"+this.khoId+"',  "+ 
								"		  chietkhau ='"+this.tongchietkhau+"',  diachigiao = N'"+this.diachigiaohang+"', "+
								"				ngaysua ='"+getDateTime()+"', nguoisua ='"+this.userId+"', tonggiatri ='"+this.tongtientruocVAT+"', vat ='"+this.VAT+"', kbh_fk ='"+kbh_fk+"'  ";
				if(trangthai==9)
				{
					query += "    , TrangThai=0 ";
					query +="	where pk_seq = '"+this.id+"'   and  TrangThai=9  ";
				}else 
				{
					query +="	where pk_seq = '"+this.id+"'  and  TrangThai=0 ";
				}
				System.out.println("[DonHang]"+query);
				if( db.updateReturnInt(query) != 1 )
				{
					db.getConnection().rollback();
					this.msg = "1.Khong the cap nhat table 'Don Hang'(ĐH đã chốt hoặc xảy ra lỗi) , " + query;
					return false; 
				}
				//Cap nhat kho


				query = "delete from donhang_sanpham where donhang_fk = " + this.id + " " +
						"		and donhang_Fk in (select pk_seq from DonHang where trangthai = 0 and pk_Seq = '" + this.id + "') ";
				if(db.updateReturnInt(query)<=0)
				{
					db.getConnection().rollback(); 
					this.msg = "3.Khong the cap nhat table 'Don Hang San pham' , " + query;
					return false; 
				}
				System.out.println("[donhang_sanpham]"+msg);

				//Chèn donhang_sanpham tận dung luôn câu SQL bên trên, kể cả hàm cập nhật tồn kho, cũng chỉ chạy 1 lượt
				query = "if exists (select PK_SEQ from DONHANG WHERE PK_SEQ = '"+this.id+"' AND TRANGTHAI = 0)" +
						"insert donhang_sanpham( sanpham_fk, donhang_fk, soluong, kho_fk, giamua, chietkhau, dongiaGOC,BangGia_fk, ptCKDLN, ptCKTT, ptCKDH  ) " +
						"select sp.sanpham_fk, '" + this.id + "', sp.soluong, '" + this.khoId + "', sp.dongia, sp.chietkhau ,sp.dongiaGOC,sp.BangGia_fk, sp.chietkhauDLN, sp.chietkhauTT, sp.chietkhauDH  " +
						"from ( " + sqlCHECK + " ) sp ";
				if(db.updateReturnInt(query) <= 0)
				{
					db.getConnection().rollback(); 
					this.msg = "4.Khong the cap nhat table 'Don Hang San pham' , " + query;
					return false; 
				}

				System.out.println("[DONHANG]"+msg);



				query=	
						"	select		p.ma as spMa,p.ten as spTen,sp.SoLuong,kho.Available,kho.booked  "+
								"	from DONHANG dh inner join DONHANG_SANPHAM sp on dh.PK_SEQ = sp.DONHANG_FK     "+
								"	inner join nhapp_kho kho on sp.SANPHAM_FK = kho.SANPHAM_FK and sp.KHO_FK = kho.KHO_FK  "+ 
								"		and kho.NPP_FK = dh.NPP_FK and kho.KBH_FK = dh.KBH_FK  "+ 
								"		inner join sanpham p on p.pk_Seq=sp.sanpham_fk  "+
								"	where dh.PK_SEQ = '"+this.id+"' and sp.SoLuong>kho.Available and dh.trangthai=0 "+
								"	union all  "+
								"	select p.ma as spMa,p.ten as spTen,sp.SoLuong,kho.Available,kho.booked  "+
								"	from DONHANG dh inner join donhang_Ctkm_trakm sp on dh.PK_SEQ = sp.donhangid "+   
								"		inner join ctkhuyenmai km on km.pk_Seq=sp.ctkmid "+
								"		inner join sanpham p on p.ma=sp.spMa "+
								"		inner join nhapp_kho kho on p.pk_Seq = kho.SANPHAM_FK and km.KHO_FK = kho.KHO_FK  "+ 
								"		and kho.NPP_FK = dh.NPP_FK and kho.KBH_FK = dh.KBH_FK   "+
								"	where dh.PK_SEQ = '"+this.id+"' and sp.SoLuong>kho.Available  ";


				System.out.println("[DONHANG]"+msg);
				rs=db.get(query);
				while(rs.next())
				{
					this.msg += "Sản phẩm  ["+rs.getString("spMa")+"]-["+rs.getString("spTen")+"]  không đủ tồn kho ( "+rs.getDouble("Available")+" |"+rs.getDouble("SoLuong")+" ) \n";

					System.out.println("[msg]"+msg);

				}

				if(this.msg.length()>0)
				{
					db.getConnection().rollback(); 
					return false; 
				}



				//Cap nhat kho
				query = "update kho set kho.booked = kho.booked + sp.SOLUONG, kho.available = kho.available - sp.SOLUONG " + 
						"from DONHANG dh inner join DONHANG_SANPHAM sp on dh.PK_SEQ = sp.DONHANG_FK   " + 
						"inner join nhapp_kho kho on sp.SANPHAM_FK = kho.SANPHAM_FK and sp.KHO_FK = kho.KHO_FK and kho.NPP_FK = dh.NPP_FK and kho.KBH_FK = dh.KBH_FK " + 
						"where dh.PK_SEQ = " + this.id +" and dh.trangthai=0" ;
				if(db.updateReturnInt(query) <= 0)
				{
					db.getConnection().rollback(); 
					this.msg = "Lỗi khi cập nhật tồn kho: " + query;
					return false; 
				}

				query = 
						"	update DONHANG set TONGGIATRI= "+
								"			isnull(( "+
								"				select SUM(b.soluong*b.giamua)  as TongGiaTri "+
								"				from DONHANG a inner join DONHANG_SANPHAM b on b.DONHANG_FK=a.PK_SEQ "+
								"				where  a.PK_SEQ=DONHANG.PK_SEQ "+
								"				group by b.DONHANG_FK "+
								"			),0)- "+
								"			isnull "+
								"				(( "+
								"					select SUM(TONGGIATRI) from DONHANG_CTKM_TRAKM km  "+
								"					where km.DONHANGID=DONHANG.PK_SEQ and SPMA is null "+
								"				),0)  "+
								"		from DONHANG  "+
								"		where pk_Seq='"+this.id+"' ";
				if(!db.update(query))
				{
					db.getConnection().rollback();
					this.msg = "Lỗi phát sinh khi cập nhật giá trị đơn hàng " + query;
					return false; 
				}


				query ="select count(*) as SoDong  "+
						"	from  "+
						"	(  "+
						"		select sanpham_fk,SoLuong,Kho_FK  "+
						"		from donhangchoxuly_sanpham where donhang_fk='"+this.id+"'  "+
						"	)a full outer  join  "+ 
						" 	(  "+
						"	 	select sanpham_fk,SoLuong,Kho_FK  "+
						"		from DonHang_SanPham where donhang_fk='"+this.id+"'  "+
						"	)b on a.SanPham_FK=b.SanPham_FK  "+
						"	where isnull(a.SoLuong,0)!=isnull(b.SoLuong,0)" ;
				System.out.println("[donhangchoxuly_sanpham]"+query);
				rs =db.get(query);
				int  SoDong=0;
				while(rs.next())
				{
					SoDong= rs.getInt("SoDong");
				}
				if(SoDong!=0)
				{

					query =
							"	declare @ngaynhap nvarchar(10), @kbh_fk numeric(18, 0),@BM_fk numeric(18, 0),@AMS_fk numeric(18, 0)  ,@IsChiNhanh bit ;  "+
									"	select @kbh_fk = kbh_fk from khachhang where pk_seq ='"+this.khId+"' " +
									" 	select @ngaynhap='"+this.ngaygiaodich+"'   "+
									"	select @IsChiNhanh = IsChiNhanh from NHAPHANPHOI where PK_SEQ='"+this.nppId+"' "+
									"	select top 1 @BM_fk=bm_cn.bm_fk,@AMS_fk= a.asm_fk from  asm_khuvuc a "+ 
									"			inner join asm on asm.pk_seq=a.asm_fk AND A.NGAYBATDAU <=@ngaynhap AND A.NGAYKETTHUC >=@ngaynhap "+
									"			inner join khuvuc kv on kv.pk_seq=a.khuvuc_fk "+
									"			inner join bm_chinhanh bm_cn on bm_cn.vung_fk=kv.vung_fk AND bm_cn.NGAYBATDAU <=@ngaynhap AND bm_cn.NGAYKETTHUC >=@ngaynhap "+
									"			inner join nhaphanphoi npp on npp.khuvuc_fk=kv.pk_seq "+
									"			inner join bm on bm.pk_seq=bm_cn.bm_fk and bm.kbh_fk=@kbh_fk and bm.dvkd_fk=(select dvkd_fk from giamsatbanhang where pk_seq='"+this.gsbhId+"') "+
									"	where npp.pk_seq='"+this.nppId+"' and asm.kbh_fk=@kbh_fk and asm.dvkd_fk=(select dvkd_fk from giamsatbanhang where pk_seq='"+this.gsbhId+"') "+
									"	insert into DonHang(ngaynhap, trangthai, ngaytao, ngaysua, nguoitao, nguoisua, vat, tonggiatri, ddkd_fk, gsbh_fk, khachhang_fk, npp_fk, kho_fk, kbh_fk, tinhtrang,BM,ASM,IsChiNhanh, diachigiao,FromDonHang_FK) "+
									"	values('"+this.ngaygiaodich+"', '7','"+this.getDateTime()+"','"+this.getDateTime()+"', '"+this.userId+"', '"+this.userId+"','"+this.VAT+"' , '"+this.tongtiensauVAT.replaceAll(",", "")+"','"+this.ddkdId+"','"+this.gsbhId+"','"+this.khId+"', '"+this.nppId+"', '"+this.khoId+"', @kbh_fk, '0',@BM_fk,@AMS_fk,@IsChiNhanh, N'"+this.diachigiaohang+"','"+this.id+"') ";
					if(!db.update(query))
					{
						this.msg = "Lỗi cập nhật đơn hàng"+ query;
						this.db.getConnection().rollback();
						return false;
					}

					String dhId="";
					ResultSet rsDDH = db.get("select scope_identity() as ID ");
					if(rsDDH.next())
					{
						dhId = rsDDH.getString("ID");
					}
					rsDDH.close();


					query=
							"	insert into DonHang_SanPham(DonHang_fk,SanPham_FK,Kho_FK,GiaMua,ChietKhau,DonGiaGoc,BangGia_FK,ptCKTT,ptCKDLN,ptCKDH,SoLuong)  "+
									"	select  "+dhId+", "+
									"	isnull(a.sanpham_fk,b.sanpham_fk) as sanpham_fk,isnull(a.KHO_FK,b.KHO_FK) as KHO_FK, "+
									"	isnull(a.GIAMUA,b.GIAMUA) as GIAMUA,isnull(a.CHIETKHAU,b.CHIETKHAU) as CHIETKHAU, "+
									"	isnull(a.DonGiaGoc,b.DonGiaGoc) as DonGiaGoc,isnull(a.BangGia_FK,b.BangGia_FK) as BangGia_FK, "+
									"	isnull(a.ptCKTT,b.ptCKTT) as ptCKTT, "+
									"	isnull(a.ptCKDLN,b.ptCKDLN) as ptCKDLN,isnull(a.ptCKDH,b.ptCKDH) as ptCKDH,isnull(a.SoLuong,0)-isnull(b.SoLuong,0) as SoLuong  "+
									" from "+
									"( "+
									"	select SanPham_FK,DonHang_FK,SoLuong,KHO_FK,GIAMUA,CHIETKHAU,DonGiaGoc,BangGia_FK,ptCKTT,ptCKDLN,ptCKDH  "+
									"	from donhangchoxuly_sanpham where donhang_fk='"+this.id+"'  "+
									" )a full outer  join  "+ 
									" ( "+
									"	select SanPham_FK,DonHang_FK,SoLuong,KHO_FK,GIAMUA,CHIETKHAU,DonGiaGoc,BangGia_FK,ptCKTT,ptCKDLN,ptCKDH "+
									"	from DonHang_SanPham where donhang_fk='"+this.id+"'  "+
									" )b on a.SanPham_FK=b.SanPham_FK and a.KHO_FK=b.Kho_FK  "+
									" where isnull(a.SoLuong,0)>isnull(b.SoLuong,0) ";
					if(!db.update(query))
					{
						this.msg = "Lỗi cập nhật đơn hàng"+ query;
						this.db.getConnection().rollback();
						return false;
					}



					query=
							"insert into donhangchoxuly_sanpham(DonHangChoXuLy_FK,DonHang_fk,SanPham_FK,Kho_FK,GiaMua,ChietKhau,DonGiaGoc,BangGia_FK,ptCKTT,ptCKDLN,ptCKDH,SoLuong) "+ 
									"	select  (select pk_seq From DonHangChoXuLy where donhang_fk='"+this.id+"'),'"+this.id+"', "+
									"	isnull(a.sanpham_fk,b.sanpham_fk) as sanpham_fk,isnull(a.KHO_FK,b.KHO_FK) as KHO_FK, "+
									"	isnull(a.GIAMUA,b.GIAMUA) as GIAMUA,isnull(a.CHIETKHAU,b.CHIETKHAU) as CHIETKHAU, "+
									"	isnull(a.DonGiaGoc,b.DonGiaGoc) as DonGiaGoc,isnull(a.BangGia_FK,b.BangGia_FK) as BangGia_FK, "+
									"	isnull(a.ptCKTT,b.ptCKTT) as ptCKTT, "+
									"	isnull(a.ptCKDLN,b.ptCKDLN) as ptCKDLN,isnull(a.ptCKDH,b.ptCKDH) as ptCKDH,NULL as SoLuong  "+
									" from "+
									"( "+
									"	select SanPham_FK,DonHang_FK,SoLuong,KHO_FK,GIAMUA,CHIETKHAU,DonGiaGoc,BangGia_FK,ptCKTT,ptCKDLN,ptCKDH  "+
									"	from donhangchoxuly_sanpham where donhang_fk='"+this.id+"'  "+
									" )a full outer  join  "+ 
									" ( "+
									"	select SanPham_FK,DonHang_FK,SoLuong,KHO_FK,GIAMUA,CHIETKHAU,DonGiaGoc,BangGia_FK,ptCKTT,ptCKDLN,ptCKDH "+
									"	from DonHang_SanPham where donhang_fk='"+this.id+"'  "+
									" )b on a.SanPham_FK=b.SanPham_FK and a.KHO_FK=b.Kho_FK  "+
									" where a.sanpham_fk is null ";

					if(!db.update(query))
					{
						this.msg = "Lỗi cập nhật đơn hàng"+ query;
						this.db.getConnection().rollback();
						return false;
					}


					query=
							"	update a set DaGiao=b.SoLuong  "+
									"		from donhangchoxuly_sanpham a   "+
									"			inner join DonHang_SanPham b on b.donhang_fk=a.donhang_fk and a.sanpham_fk=b.sanpham_fk " +
									"   where a.donhang_fk='"+this.id+"'  ";

					if(!db.update(query))
					{
						this.msg = "Lỗi cập nhật đơn hàng"+ query;
						this.db.getConnection().rollback();
						return false;
					}



					query=
							"	INSERT INTO DonHang_CTKM_TRAKM(	DonHangID,CTKMID,TRAKMID,SOXUAT,TONGGIATRI,SPMA,SOLUONG,CHIETKHAU,LOAI) "+
									"	select  "+dhId+", "+
									"		isnull(a.CTKMID,b.CTKMID) as CTKMID,isnull(a.TRAKMID,b.TRAKMID) as TRAKMID, "+
									"		isnull(a.SOXUAT,b.SOXUAT) as SOXUAT,isnull(a.TONGGIATRI,b.TONGGIATRI) as TONGGIATRI, "+
									"		isnull(a.SPMA,b.SPMA) as SPMA,isnull(a.SOLUONG,b.SOLUONG) as SOLUONG, "+
									"		isnull(a.CHIETKHAU,b.CHIETKHAU) as CHIETKHAU, "+
									"		isnull(a.LOAI,b.LOAI) as LOAI "+
									"	from "+
									"	( "+
									"		select CTKMID,TRAKMID,SOXUAT,TONGGIATRI,SPMA,SOLUONG,CHIETKHAU,LOAI,DONHANG_FK "+
									"		from [DonHangChoXuLy_CTKM_TRAKM] where donhang_fk='"+this.id+"' "+
									"	)a full outer  join "+ 
									"	( "+
									"	 	select CTKMID,TRAKMID,SOXUAT,TONGGIATRI,SPMA,SOLUONG,CHIETKHAU,LOAI,DONHANGID as DONHANG_FK "+
									"		from [DonHang_CTKM_TRAKM] where DONHANGID= '"+this.id+"' "+
									"	)b on a.CTKMID=b.CTKMID and a.TRAKMID=b.TRAKMID AND A.SPMA=B.SPMA "+
									" where isnull(a.SoLuong,0)>isnull(b.SoLuong,0) ";

					if(!db.update(query))
					{
						this.msg = "Lỗi cập nhật đơn hàng"+ query;
						this.db.getConnection().rollback();
						return false;
					}


					query=
							"INSERT INTO DonHangChoXuLy_CTKM_TRAKM(	DHCXLID ,DonHang_FK,CTKMID,TRAKMID,SOXUAT,TONGGIATRI,SPMA,SOLUONG,CHIETKHAU,LOAI) "+ 
									"	select    (select pk_seq From DonHangChoXuLy where donhang_fk='"+this.id+"'),'"+this.id+"', "+
									"		isnull(a.CTKMID,b.CTKMID) as CTKMID,isnull(a.TRAKMID,b.TRAKMID) as TRAKMID, "+
									"		isnull(a.SOXUAT,b.SOXUAT) as SOXUAT,isnull(a.TONGGIATRI,b.TONGGIATRI) as TONGGIATRI, "+
									"		isnull(a.SPMA,b.SPMA) as SPMA,NULL as SOLUONG, "+
									"		isnull(a.CHIETKHAU,b.CHIETKHAU) as CHIETKHAU, "+
									"		isnull(a.LOAI,b.LOAI) as LOAI "+
									"	from "+
									"	( "+
									"		select CTKMID,TRAKMID,SOXUAT,TONGGIATRI,SPMA,SOLUONG,CHIETKHAU,LOAI,DONHANG_FK "+
									"		from [DonHangChoXuLy_CTKM_TRAKM] where donhang_fk='"+this.id+"' "+
									"	)a full outer  join "+ 
									"	( "+
									"	 	select CTKMID,TRAKMID,SOXUAT,TONGGIATRI,SPMA,SOLUONG,CHIETKHAU,LOAI,DONHANGID as DONHANG_FK "+
									"		from [DonHang_CTKM_TRAKM] where DONHANGID= '"+this.id+"' "+
									"	)b on a.CTKMID=b.CTKMID and a.TRAKMID=b.TRAKMID AND A.SPMA=B.SPMA "+
									" where a.CTKMID is null  ";

					if(!db.update(query))
					{
						this.msg = "Lỗi cập nhật đơn hàng"+ query;
						this.db.getConnection().rollback();
						return false;
					}

					query=
							"	update a set DaGiao=b.SoLuong  "+
									"		from DonHang_CTKM_TRAKM a   "+
									"			inner join DonHangChoXuLy_CTKM_TRAKM b on b.CTKMID=a.CTKMID and a.TRAKMID=b.TRAKMID AND A.SPMA=B.SPMA " +
									"   where B.donhang_fk='"+this.id+"'  ";

					if(!db.update(query))
					{
						this.msg = "Lỗi cập nhật đơn hàng"+ query;
						this.db.getConnection().rollback();
						return false;
					}



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

	public boolean UpdateDhXuatKho(List<ISanpham> splist) 
	{
		this.ngaysua = getDateTime();
		this.nguoisua = this.userId;

		try 
		{

			db.update("SET TRANSACTION ISOLATION LEVEL SNAPSHOT;");
			db.update("SET LOCK_TIMEOUT 60000;");


			db.getConnection().setAutoCommit(false);

			long tt = Math.round(Float.parseFloat(this.tongtiensauVAT));
			long ttVAT = Math.round(tt / (1 + Float.parseFloat(this.VAT) / 100));
			//long tonggiatriDh = Math.round(this.ttsauCKKM); da lam trong ben javascript roi, lam tron nua se lech chut it
			String query = "update donhang set ngaynhap = '" + this.ngaygiaodich + "', ddkd_fk = '"+ this.ddkdId + "', gsbh_fk = " + this.gsbhId + ", khachhang_fk = '" + this.khId + "', chietkhau='" + this.tongchietkhau + "', ngaysua = '" + this.ngaysua + "', nguoisua = '" + this.nguoisua + "', tonggiatri = '" + Float.toString(this.ttsauCKKM) +"', vat = '" + Long.toString(ttVAT) + "',kbh_fk ='"+ kenh() +"' where pk_seq = '" + this.id + "'";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				this.msg = "1.Khong the cap nhat table 'Don Hang' , " + query;
				return false; 
			}

			if(splist.size() > 0)
			{	
				query = "delete from donhang_sanpham where donhang_fk = '" + this.id + "'" ;
				System.out.println("2.Cap nhat don hang DXK: " + query);

				if(!db.update(query)){
					db.getConnection().rollback();
					this.msg = "2.Khong the cap nhat table 'donhang_sanpham' , " + query;
					return false; 
				}

				for(int m = 0; m < splist.size(); m++)
				{
					ISanpham sp = splist.get(m);
					String pk_seq = "";
					ResultSet rs = db.get("select pk_seq from sanpham where ma='" + sp.getMasanpham() + "'");
					if(rs != null) 
					{
						rs.next();
						pk_seq = rs.getString("pk_seq");
						rs.close();
					} 
					if(pk_seq != "")
					{
						query = "insert into donhang_sanpham(sanpham_fk, donhang_fk, soluong, kho_fk, giamua, chietkhau) values('" + pk_seq + "','" + this.id + "','" + sp.getSoluong() + "','" + this.khoId + "','" + sp.getDongia() + "', '" + sp.getChietkhau() + "')";
						System.out.println("3.Insert san pham don hang DXK: " + query);
						if(!db.update(query))
						{
							db.getConnection().rollback();
							this.msg = "Loi khi them moi bang donhang_sanpham, " + query;
							return false; 
						}
					}
				}
				//tao phieu thu hoi neu co
				if(this.trangthai.equals("3"))
				{
					this.createPxkId();
					String msg = this.createPth(this.pxkId, db);
					System.out.println("Messege Phieu thu hoi: " + msg);
					if(msg.length() > 0)
					{
						db.getConnection().rollback();
						this.msg = msg;
						return false; 
					}
				}

				query = 
						"	update DONHANG set TONGGIATRI= "+
								"			isnull(( "+
								"				select SUM(b.soluong*b.giamua)  as TongGiaTri "+
								"				from DONHANG a inner join DONHANG_SANPHAM b on b.DONHANG_FK=a.PK_SEQ "+
								"				where  a.PK_SEQ=DONHANG.PK_SEQ "+
								"				group by b.DONHANG_FK "+
								"			),0)- "+
								"			isnull "+
								"				(( "+
								"					select SUM(TONGGIATRI) from DONHANG_CTKM_TRAKM km  "+
								"					where km.DONHANGID=DONHANG.PK_SEQ and SPMA is null "+
								"				),0)  "+
								"		from DONHANG  "+
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
		} 
		catch(Exception e1) 
		{
			try 
			{
				db.getConnection().rollback();
			} 
			catch (Exception e) {}
			this.msg="Loi :" + e1.toString();

			return false;
		}
		return true;
	}

	public boolean ChotDh(List<ISanpham> splist)
	{
		if(this.spThieuList.size() > 0)
		{
			this.msg = "Sorry, Trong kho khong du so luong mot so san pham ban chon, vui long chon lai so luong...";
			return false;
		}

		//kiem tra ngay khoa so
		if(checkNgayKhoaSo(this.ngaygiaodich) == false)
		{
			this.msg = "Bạn chỉ có thể chốt đơn hàng sau ngày khóa sổ gần nhất một ngày.";
			return false;
		}

		try 
		{

			db.update("SET TRANSACTION ISOLATION LEVEL SNAPSHOT;");
			db.update("SET LOCK_TIMEOUT 60000;");
			db.getConnection().setAutoCommit(false);

			String query = "update donhang set trangthai= '1',FlagModified =1 where pk_seq= '" + this.id + "' and TrangThai=3 " ;
			if(db.updateReturnInt(query)!=1)
			{
				db.getConnection().rollback();
				this.msg = "Đơn hàng đã chốt rồi";
				return false; 
			}

			if(this.nppId.equals("102961"))
			{
				//long tt = Math.round(Float.parseFloat(this.tongtiensauVAT));

				db.update("delete khachhang_congno where khachhang_fk='" + this.khId + "' and donhang_fk = '" + this.id + "'");
				String sql = "Insert into khachhang_congno(khachhang_fk, sotienno, ngayno, diengiai, donhang_fk) values('" + this.khId + "', '" + Float.toString(this.ttsauCKKM) + "', '" + this.ngaygiaodich + "', 'No Don Hang', '" + this.id + "')";			
				if(!db.update(sql))
				{
					db.getConnection().rollback();
					this.msg = "Loi khi cap nhat bang 'khachhang_congno': " + sql;
					return false;
				}

				query = "Update nvgn_congno set datra = datra + '" + Float.toString(this.ttsauCKKM) + "' where nvgn_fk = (select nvgn_fk from phieuxuatkho where pk_seq in (select pxk_fk from phieuxuatkho_donhang where donhang_fk = '" + this.id + "'))";
				if(!db.update(query))
				{
					geso.dms.center.util.Utility.rollback_throw_exception(db);
					this.msg = "Loi khi cap nhat bang 'nvgn_congno': " + query;
					return false;
				}
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
		Utility util = new Utility();
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
	{
		String query = "select distinct a.pxk_fk, b.ngaylapphieu from phieuxuatkho_donhang a inner join phieuxuatkho b on a.pxk_fk = b.pk_seq where a.donhang_fk = '" + this.id + "'";
		ResultSet rs = db.get(query);		
		System.out.println("Query vao lay Phieu Xuat Kho La: " + query + "\n");
		try 
		{
			rs.next();
			this.pxkId = rs.getString("pxk_fk");
			this.ngayxuatkho = rs.getString("ngaylapphieu");
			rs.close();
		}
		catch(Exception e) {}
	}

	public void createRS() 
	{
		this.getNppInfo();

		this.createDdkd();	
		this.createChietkhau();
		this.createBgstId();
		this.createKho();	
		this.checkInfo();
		this.Muctindung();
	}

	private void checkInfo() 
	{
		if(this.ngaygiaodich.length() > 0 && this.khId.length() > 0)
		{
			String query = "select count(*) as sodong from donhang where khachhang_fk = '" + this.khId + "' and ngaynhap = '" + this.ngaygiaodich + "'";
			ResultSet rs = db.get(query);
			try 
			{
				if(rs.next())
				{
					if(rs.getInt("sodong") > 0)
						this.dacoDh = true; //khach hang da mua hang trong ngay
					rs.close();
				}
				if(this.id.length() > 0 && this.trangthai.equals("0")) //da xuat kho nhung chua chot pxk
				{
					query = "select count(*) as sodong from phieuxuatkho_donhang where donhang_fk = '" + this.id + "'";
					System.out.println("Cau lenh check phieuxuatkho: " + query);
					ResultSet xuatkho = db.get(query);
					if(xuatkho.next())
					{
						if(xuatkho.getInt("sodong") > 0)
							this.daxuatkhoChuachot = true;
						xuatkho.close();
					}
				}
			}
			catch(Exception e) {e.printStackTrace();}
		}
	}

	private void createDdkd()
	{
		//tao gsbh
		String sql ="select a.pk_seq,a.ten from giamsatbanhang a inner join NHAPP_GIAMSATBH b on a.pk_seq = b.gsbh_fk where   b.ngaybatdau <='"+this.getDateTime()+"' and b.ngayketthuc >='"+getDateTime()+"' and   a.trangthai = '1' and npp_fk ='"+ this.nppId +"'";
		this.gsbhs = db.get(sql);

		String query = "select ten as ddkdTen, pk_seq as ddkdId from daidienkinhdoanh where trangthai = '1' and npp_fk ='" + this.nppId + " ' and pk_seq in (select ddkd_fk from ddkd_gsbh where gsbh_fk in (select gsbh_fk from  nhapp_giamsatbh where ngaybatdau <='"+this.getDateTime()+"' and ngayketthuc >='"+getDateTime()+"' and  gsbh_fk ='" + this.gsbhId + "') )";
		this.ddkdlist = db.get(query);
	}

	private void createBgstId()
	{
		ResultSet rs = db.get("select banggiasieuthi_fk from bgst_khachhang where khachhang_fk = '" + this.khId + "'");
		String st ="";
		if(rs != null)
		{
			try 
			{
				while(rs.next())
				{
					st = st + rs.getString("banggiasieuthi_fk")+"-";
				}
				//	if(rs.getString("bgst_fk") != null)
				if(st.length()>0)
					this.bgstId = st;//rs.getString("bgst_fk");
				else
					this.bgstId ="0";
				rs.close();
			} 
			catch(Exception e) {

				System.out.println("loi..........."+e.toString());
			}			
		}
		else
			this.bgstId ="0";
	}

	private void createChietkhau()
	{
		if(this.chietkhau.equals("0") || this.chietkhau == "")
		{
			String sql ="select ISNULL(b.chietkhau,0 ) as chietkhau from KHACHHANG a left join MUCCHIETKHAU b on a.CHIETKHAU_FK = b.pk_seq where a.PK_SEQ = '" + khId + "'";
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
		DecimalFormat df2 = new DecimalFormat( "#,###,###,##0.00" );

		String command = 
				"select DISTINCT b.pk_seq as spId, b.ma as spMa, b.ten as spTen, a.soluong, isnull(c.donvi, 'Chua xac dinh') as donvi," +
						" a.giamua as dongia, a.chietkhau, a.ptCKDLN, a.ptCKTT, a.ptCKDH , d.AVAILABLE as hienhuu,isnull(a.DonGiaGoc,0) as GiaGoc," +
						"  isnull(a.BangGia_FK,0) as  bgID,a.DaGiao,a.ConLai  " +
						"from DonHangChoXuLy_sanpham a " +
						"inner join sanpham b on a.sanpham_fk = b.pk_seq " +
						"left join donvidoluong c on b.dvdl_fk = c.pk_seq " +
						"inner join NHAPP_KHO d on b.PK_SEQ = d.SANPHAM_FK " +
						"where a.DonHangChoXuLy_FK = '" + this.id + "' and d.KHO_FK = '" + this.khoId + "' and d.NPP_FK = '" + this.nppId + "' and d.KBH_FK = '" + this.kbhId + "'";

		System.out.println("2.San pham list:" + command);

		ResultSet splistRs = db.get(command);
		float tonggiatri = 0f; 
		List<ISanpham> splist = new ArrayList<ISanpham>();
		if(splistRs != null)
		{
			String[] param = new String[11];
			ISanpham sp = null;	
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


					param[8] = splistRs.getString("DaGiao");
					param[9] = splistRs.getString("ConLai");

					tonggiatri += Float.parseFloat(param[5]) * Float.parseFloat(param[3]);

					sp = new Sanpham(param);
					sp.setTonhientai(splistRs.getString("hienhuu"));
					sp.setGiagoc(  splistRs.getString("GiaGoc"));
					sp.setBgId(splistRs.getString("bgId"));

					sp.setConLai( df2.format(  splistRs.getDouble("ConLai"))   );
					sp.setDaGiao(df2.format(  splistRs.getDouble("DaGiao"))   );


					splist.add(sp);

				}
				if(splistRs!=null){
					splistRs.close();
				}
			} 
			catch(Exception e) 
			{
				System.out.println("115.Exception khoi tao: " + e.getMessage());
			}
		}
		this.splist = splist;
	}

	public void CreateSpDisplay()
	{		
		DecimalFormat df2 = new DecimalFormat( "#,###,###,##0.00" );
		String command = "";
		command = "select DISTINCT a.DaGiao,a.ConLai,b.pk_seq as spId, b.ma as spMa, b.ten as spTen, a.soluong, isnull(c.donvi, 'Chua xac dinh') as donvi, a.giamua as dongia, isnull(a.chietkhau, '0') as chietkhau,isnull(a.DonGiaGoc, '0')  as GiaGoc,isnull(a.BangGia_FK, '0')  as bgId ";
		command = command + "from donhang_sanpham a inner join sanpham b on a.sanpham_fk = b.pk_seq left join donvidoluong c on b.dvdl_fk = c.pk_seq";
		command = command + " where a.donhang_fk = '" + this.id + "'";
		//System.out.println("San pham list:" + command);
		ResultSet splistRs = db.get(command);
		List<ISanpham> splist = new ArrayList<ISanpham>();
		if(splistRs != null)
		{
			String[] param = new String[11];
			ISanpham sp = null;	
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

					sp = new Sanpham(param);
					sp.setTonhientai(splistRs.getString("hienhuu"));
					sp.setGiagoc(  splistRs.getString("GiaGoc"));
					sp.setBgId(splistRs.getString("bgId"));
					
					sp.setConLai( df2.format(  splistRs.getDouble("ConLai"))   );
					sp.setDaGiao(df2.format(  splistRs.getDouble("DaGiao"))   );

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
		this.getNppInfo();

		String query = "select a.gsbh_fk,a.pk_seq as dhId, a.ngaynhap, a.trangthai, a.ngaytao, a.ngaysua, a.khachhang_fk as khId, g.ten as khTen, g.diachi, isnull(g.smartid, '') as smartid,  a.kho_fk as khoId, a.tonggiatri, b.ten as nguoitao, c.ten as nguoisua, e.pk_seq as ddkdId, e.ten as ddkdTen, f.ten as nppTen, a.VAT, isnull(a.chietkhau, 0) as chietkhau, isnull(a.chuyen, 0) as ptck, a.kbh_fk,a.IsChiNhanh ";
		query = query + " from donhangChoXuLy a left join nhanvien b on a.nguoitao = b.pk_seq left join nhanvien c on a.nguoisua = c.pk_seq inner join daidienkinhdoanh e on a.ddkd_fk = e.pk_seq inner join nhaphanphoi f on a.npp_fk = f.pk_seq";
		query = query + " inner join khachhang g on a.khachhang_fk=g.pk_seq ";
		query = query + " where a.npp_fk='" + this.nppId + "' and a.pk_seq='" + this.id + "'";

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
				this.VAT = "0";  //TTC, gia SP da co thue
				this.ddkdId = rs.getString("ddkdId");
				this.khoId = rs.getString("khoId");
				System.out.println("11.Kho id: " + this.khoId);
				this.chietkhau = rs.getString("ptck");
				this.tongchietkhau = rs.getString("chietkhau");
				this.tongtiensauVAT = rs.getString("tonggiatri");
				this.tongtientruocVAT = rs.getString("VAT");
				this.gsbhId = rs.getString("gsbh_fk");
				this.kbhId = rs.getString("kbh_fk");
				this.IsChiNhanh= rs.getString("IsChiNhanh")==null?"0":rs.getString("isChiNhanh");
				rs.close();
			}
			catch(Exception e)
			{
				System.out.println("33.Exception: " + e.getMessage());
			}
		}
		String sql ="select a.pk_seq, a.ten from giamsatbanhang a inner join NHAPP_GIAMSATBH b on a.pk_seq = b.gsbh_fk where  b.ngaybatdau <='"+this.getDateTime()+"' and b.ngayketthuc >='"+getDateTime()+"' and npp_fk ='" + this.nppId + "' and a.trangthai = '1'";
		sql+=" union select a.pk_seq, a.ten from giamsatbanhang a  where a.pk_Seq='"+this.gsbhId+"'  ";
		System.out.println("cau query ............."+sql);
		this.gsbhs = db.get(sql);
		if(this.gsbhId == null)
		{
			this.ddkdlist = db.get("select ten as ddkdTen, pk_seq as ddkdId from daidienkinhdoanh where trangthai = '1' and npp_fk = '" + this.nppId + "'");
		}
		else
		{
			sql="select ten as ddkdTen, pk_seq as ddkdId from daidienkinhdoanh where trangthai = '1' and npp_fk = '" + this.nppId + "' and pk_seq in (select ddkd_fk from ddkd_gsbh where gsbh_fk ='" + this.gsbhId + "')";
			sql+=" union select ten as ddkdTen, pk_seq as ddkdId from daidienkinhdoanh where pk_Seq='"+this.ddkdId+"'";
			this.ddkdlist = db.get(sql );
			System.out.println("___Ddkd___"+sql);
		}

		this.createChietkhau();
		this.createBgstId();
		this.CreateSpList();
		this.createKho();
		this.checkInfo();
		this.getTrakhuyenmai();
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

	public String createPth(String pxkId, dbutils db)
	{		
		String msg = "";
		try 
		{
			db.getConnection().setAutoCommit(false);

			List<ISanpham> spThuhoiList = this.getSpthuhoiList(pxkId, db);
			List<ISanpham> spkmThuhoiList = this.getSpkmthuhoiList(pxkId, db);
			System.out.println("[spThuhoiList]"+spThuhoiList.size()+"[spkmThuhoiList]"+spkmThuhoiList.size());
			if(spThuhoiList.size() > 0 || spkmThuhoiList.size() > 0)
			{	
				//Xoa cac phieu thu hoi cu cua pxk nay co trang thai = 0
				String query = "delete from phieuthuhoi_sanpham where pth_fk in (select pk_seq from phieuthuhoi where phieuxuatkho_fk = '" + pxkId + "' and trangthai = 0)";
				if(!db.update(query))
				{
					db.getConnection().rollback();
					msg = "1.Lỗi tạo phiếu thu hồi: " + query;
					return msg;
				}

				query= "delete from phieuthuhoi_spkm where pth_fk in (select pk_seq from phieuthuhoi where phieuxuatkho_fk = '" + pxkId + "' and trangthai = 0)";
				if(!db.update(query))
				{
					db.getConnection().rollback();
					msg = "2.Lỗi tạo phiếu thu hồi: " + query;
					return msg;
				}

				query = "select DONHANG_FK as dhId from PHIEUTHUHOI_DONHANG where pth_fk in (select pk_seq from phieuthuhoi where phieuxuatkho_fk = '" + pxkId + "' and trangthai = 0) and donhang_fk != '" + this.id + "'";
				ResultSet rsDh = db.get(query);
				String dhs = "";
				if(rsDh != null)
				{
					while(rsDh.next())
					{
						if(rsDh.getString("dhId") != null)
							dhs += rsDh.getString("dhId") + ",";
					}
					rsDh.close();
				}

				query= "delete from phieuthuhoi_donhang where pth_fk in (select pk_seq from phieuthuhoi where phieuxuatkho_fk = '" + pxkId + "' and trangthai = 0)";
				if(!db.update(query))
				{
					db.getConnection().rollback();
					msg = "3.Lỗi tạo phiếu thu hồi: " + query;
					return msg;
				}

				query= "delete from phieuthuhoi where phieuxuatkho_fk = '" + pxkId + "' and trangthai = 0";
				if(!db.update(query))
				{
					db.getConnection().rollback();
					msg = "4.Lỗi tạo phiếu thu hồi: " + query;
					return msg;
				}


				query = "insert into phieuthuhoi(phieuxuatkho_fk, trangthai, ngaytao, ngaysua, nguoitao, nguoisua, donhang_fk, npp_fk) ";
				query = query + "values('" + pxkId + "','0','" + this.ngayxuatkho + "','" + this.getDateTime() + "','" + this.userId + "','" + this.userId + "', '" + this.id + "', '" + this.nppId + "')";
				if(!db.update(query))
				{
					db.getConnection().rollback();
					msg = "5.Loi khi them moi bang 'phieuthuhoi', " + query;
					return msg; 
				}

				query = "select IDENT_CURRENT('phieuthuhoi') as pthId";
				String pthId = "";
				ResultSet rsPth = db.get(query);									
				rsPth.next();
				pthId = rsPth.getString("pthId");
				rsPth.close();

				//luu vao bang phieuthuhoi_sp
				for(int i = 0; i < spThuhoiList.size(); i++)
				{
					Sanpham sp  = (Sanpham)spThuhoiList.get(i);

					if(Integer.parseInt(sp.getSoluong()) != 0)
					{
						//DOn vi tinh luu kho_fk, don gia se luu kbh_fk	
						query = "Insert into phieuthuhoi_sanpham(pth_fk, sanpham_fk, soluong, kho_fk, kbh_fk) values(" + pthId + ", '" + sp.getId() + "','" + sp.getSoluong() + "', '" + sp.getDonvitinh() + "', '" + sp.getDongia() + "')";

						if(!db.update(query))
						{
							db.getConnection().rollback();
							msg = "6.Loi khi them moi bang 'phieuthuhoi_sanpham', " + query;
							return msg; 
						}
					}
				}

				//luu vao bang phieuthuhoi_spkm (chi tao khi in phieu thu hoi cuoi cung)
				for(int i = 0; i < spkmThuhoiList.size(); i++)
				{
					Sanpham sp  = (Sanpham)spkmThuhoiList.get(i);

					if(Integer.parseInt(sp.getSoluong()) != 0)
					{
						//DOn vi tinh luu kho_fk, don gia se luu kbh_fk	
						query = "Insert into phieuthuhoi_spkm(pth_fk, sanpham_fk, soluong, kho_fk, kbh_fk) values('" + pthId + "', '" + sp.getId() + "','" + sp.getSoluong() + "', '" + sp.getDonvitinh() + "', '" + sp.getDongia() + "')";
						if(!db.update(query))
						{
							db.getConnection().rollback();
							msg = "7.Loi khi them moi bang 'phieuthuhoi_spkm', " + query;
							return msg; 
						}
					}
				}

				query = "insert PHIEUTHUHOI_DONHANG(pth_fk, pxk_fk, donhang_fk) values('" + pthId + "', '" + this.pxkId + "', '" + this.id + "')";
				if(!db.update(query))
				{
					db.getConnection().rollback();
					msg = "Loi khi them moi bang 'PHIEUTHUHOI_DONHANG', " + query;
					return msg; 
				}

				if(dhs.length() > 0)
				{
					String[] donhangs = dhs.split(",");
					for(int i = 0; i < donhangs.length; i++)
					{
						query = "insert PHIEUTHUHOI_DONHANG(pth_fk, pxk_fk, donhang_fk) values('" + pthId + "', '" + this.pxkId + "', '" + donhangs[i].trim() + "')";
						if(!db.update(query))
						{
							db.getConnection().rollback();
							msg = "8.Loi khi them moi bang 'PHIEUTHUHOI_DONHANG', " + query;
							return msg; 
						}
					}
				}
			} 
		}
		catch(Exception e1) 
		{
			e1.printStackTrace();
			try 
			{
				db.getConnection().rollback();
			} 
			catch (Exception e) {}

			msg = "Loi khi tao moi phieu thu hoi :, " + e1.toString();
			return msg; 
		}
		return msg;
	}

	private List<ISanpham> getSpthuhoiList(String pxkId, dbutils db)
	{
		List<ISanpham> spOldList = new ArrayList<ISanpham>();
		String query = "select sanpham_fk, soluong, kbh_fk, kho_fk from phieuxuatkho_sanpham where pxk_fk = '" + pxkId + "'";

		System.out.println("Cau lenh lay du lieu la: " + query + "\n");
		ResultSet spThuhoi = db.get(query);
		if(spThuhoi!= null)
		{
			try 
			{
				while(spThuhoi.next())
				{								
					ISanpham sp = new Sanpham(spThuhoi.getString("sanpham_fk"), "", "", spThuhoi.getString("soluong"), spThuhoi.getString("kho_fk"), spThuhoi.getString("kbh_fk"), "", "");
					spOldList.add(sp);	
				}
				spThuhoi.close();
			} 
			catch(Exception e) {e.printStackTrace();}
		}

		//System.out.println("Size san pham thu hoi buoc 1 la: " + spOldList.size() + "\n");

		//Nhung phieu thu hoi cua phieu xuat kho nay
		query = "select sanpham_fk as spId, soluong, kho_fk as khoId, kbh_fk as kbhId from phieuthuhoi_sanpham where pth_fk in (select pk_seq from phieuthuhoi where phieuxuatkho_fk = '" + pxkId + "' and trangthai = '1')";
		query += " union all ";
		query += "select c.sanpham_fk as spId, soluong, b.kho_fk as khoId, b.kbh_fk as kbhId from phieuxuatkho_donhang a inner join donhang b on a.donhang_fk = b.pk_seq inner join donhang_sanpham c on b.pk_seq = c.donhang_fk where b.trangthai != '2' and a.pxk_fk = '" + pxkId + "'";

		query = "select spId, sum(soluong) as soluong, khoId, kbhId from (" + query + ") kh group by spId, khoId, kbhId";

		System.out.println("Query lay san pham aaaaa la: " + query + "\n");

		List<ISanpham> spNewList = new ArrayList<ISanpham>();
		ResultSet spThuhoi2 = db.get(query);
		if(spThuhoi2 != null)
		{
			try 
			{
				while(spThuhoi2.next())
				{								
					ISanpham sp = new Sanpham(spThuhoi2.getString("spId"), "", "", spThuhoi2.getString("soluong"), spThuhoi2.getString("khoId"), spThuhoi2.getString("kbhId"), "", "");

					spNewList.add(sp);	
				}
				spThuhoi2.close();
			} 
			catch(Exception e) {}
		}

		List<ISanpham> spkmList = new ArrayList<ISanpham>();

		for(int i = 0; i < spOldList.size(); i++)
		{
			Sanpham spA  = (Sanpham)spOldList.get(i);
			for(int j = 0; j < spNewList.size(); j++)
			{				
				Sanpham spB = (Sanpham)spNewList.get(j);
				if((spB.getId().trim().equals(spA.getId().trim())) && (spB.getDonvitinh().trim().equals(spA.getDonvitinh().trim())) && (spB.getDongia().trim().equals(spA.getDongia().trim())))
				{
					int slg = Math.abs(Integer.parseInt(spA.getSoluong()) - Integer.parseInt(spB.getSoluong()));

					spOldList.get(i).setSoluong(Integer.toString(slg));

					spNewList.remove(j);
					j = 0;
				}
			}
			if(Integer.parseInt(spOldList.get(i).getSoluong()) > 0)
			{
				ISanpham sp = new Sanpham(spOldList.get(i).getId(), "", "", spOldList.get(i).getSoluong(), spOldList.get(i).getDonvitinh(), spOldList.get(i).getDongia(), "", "");
				spkmList.add(sp);

				//System.out.println("So luong luc dau thu hoi co so luong la: " + sp.getSoluong() + "\n");
			}
		}

		//System.out.println("Size san pham thu hoi buoc 2 la: " + spkmList.size() + "\n");
		return spkmList;
	}

	private List<ISanpham> getSpkmthuhoiList(String pxkId, dbutils db)
	{
		//spkm trong phieuxuatkho cu
		List<ISanpham> spkmOldList = new ArrayList<ISanpham>();
		String query = "select kho_fk as khoId, kbh_fk as kbhId, sanpham_fk as spId, sum(soluong) as soluong from phieuxuatkho_spkm where pxk_fk = '" + pxkId + "' group by kho_fk, kbh_fk, sanpham_fk";

		System.out.println("Query lan 1: " + query + "\n");

		ResultSet spOld = db.get(query);
		if(spOld != null)
		{
			try 
			{
				while(spOld.next())
				{										
					ISanpham sp = new Sanpham(spOld.getString("spId"), "", "", spOld.getString("soluong"), spOld.getString("khoId"), spOld.getString("kbhId"), "", "");
					spkmOldList.add(sp);
				}
				spOld.close();
			} 
			catch(Exception e) {}
		}

		//tinh lai so lg cac spkm phai thu hoi (nhung donhang daxuatkho ma huy thi ko can lay spkm, khi do spkmOldList se thu hoi dung soluong daxuatkho bandau cua nhung don hang da huy nay)
		//List<ISanpham> spkmNewList = new ArrayList<ISanpham>();
		//nhung phieu thu hoi da chot cua phieu xuat kho nay
		query = "select sanpham_fk as spId, soluong, kho_fk as khoId, kbh_fk as kbhId from phieuthuhoi_spkm where pth_fk in (select pk_seq from phieuthuhoi where phieuxuatkho_fk = '" + pxkId + "' and trangthai = '1')";
		query += " union all ";
		query += "select d.pk_seq as spId, soluong, b.kho_fk as khoId, e.kbh_fk as kbhId from donhang_ctkm_trakm a inner join ctkhuyenmai b on a.ctkmid = b.pk_seq inner join sanpham d on a.spMa = d.ma inner join donhang e on a.donhangId = e.pk_seq where a.spMa is not null and e.trangthai != '2' and a.donhangId in (select donhang_fk from phieuxuatkho_donhang where pxk_fk = '" + pxkId + "') ";

		query = "select spId, sum(soluong) as soluong, khoId, kbhId from (" + query + ") kh group by spId, khoId, kbhId";

		System.out.println("Query lay spkm: " + query +  "\n");

		List<ISanpham> spkmNewList = new ArrayList<ISanpham>();
		ResultSet spThuhoi2 = db.get(query);
		if(spThuhoi2 != null)
		{
			try 
			{
				while(spThuhoi2.next())
				{								
					ISanpham sp = new Sanpham(spThuhoi2.getString("spId"), "", "", spThuhoi2.getString("soluong"), spThuhoi2.getString("khoId"), spThuhoi2.getString("kbhId"), "", "");

					spkmNewList.add(sp);	
				}
				spThuhoi2.close();
			} 
			catch(Exception e) {}
		}

		//thu hoi sp trakhyenmai (ve kho_fk, kbh_fk)
		List<ISanpham> spkmList = new ArrayList<ISanpham>();

		for(int i = 0; i < spkmOldList.size(); i++)
		{
			Sanpham spA  = (Sanpham)spkmOldList.get(i);
			for(int j = 0; j < spkmNewList.size(); j++)
			{				
				Sanpham spB = (Sanpham)spkmNewList.get(j);
				if((spB.getId().trim().equals(spA.getId().trim())) && (spB.getDonvitinh().trim().equals(spA.getDonvitinh().trim())) && (spB.getDongia().trim().equals(spA.getDongia().trim())))
				{

					int slg = Math.abs(Integer.parseInt(spA.getSoluong()) - Integer.parseInt(spB.getSoluong()));

					spkmOldList.get(i).setSoluong(Integer.toString(slg));
					spkmNewList.remove(j);
					j = 0; //quet lai
				}
			}
			if(Integer.parseInt(spkmOldList.get(i).getSoluong()) > 0)
			{
				ISanpham sp = new Sanpham(spkmOldList.get(i).getId(), "", "", spkmOldList.get(i).getSoluong(), spkmOldList.get(i).getDonvitinh(), spkmOldList.get(i).getDongia(), "", "");
				spkmList.add(sp);
			}
		}
		return spkmList;
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

			String query = "update donhang set trangthai='2' where pk_seq = '" + this.id + "'";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "1.Khong the xoa don hang: " + this.id + ", " + query;
			}



			//cap nhat phan bo km
			query = "select ctkmId, sum(tonggiatri) as tonggiatri from donhang_ctkm_trakm where donhangid = '" + this.id + "' group by ctkmId";
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

	@Override
	public String getIsChiNhanh() 
	{
		return IsChiNhanh;
	}

	@Override
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
	private int getSoXuatTheoScheme(String dhId, String ctkmId)
	{	
		int soxuat = 0;		

		String query = "select isnull(MIN(Soxuat), '0') as SoXuatThoa, COUNT(nsptbId) as sonhom " +
				"from " +
				"( " +
				"select nsptbId, case when pheptoan = 1 then 'AND' else 'OR' end as pheptoan,  " +
				"case  when (LOAI = 2 and tongluongPhaiMua > 0) then tongluong / tongluongPhaiMua  " +
				"when (LOAI = 2 and tongtienPhaiMua > 0) then tongtien / tongtienPhaiMua  " +
				"else soxuatAnd end as Soxuat " +
				"from" +
				"( " +
				"select dieukientrungbay.*, trungbaytheodk.KHACHHANG_FK, SUM(trungbaytheodk.tongluong) as tongluong, SUM(trungbaytheodk.tongtien) as tongtien, " +
				"COUNT(case when trungbaytheodk.batbuoc > 0 then 1 else null end ) sospphaimua, " +
				"MIN (trungbaytheodk.tongluong / trungbaytheodk.batbuoc) as soxuatAnd " +
				"from " +
				"( " +
				"select b.PK_SEQ as nsptbId, b.LOAI, a.pheptoan, sum( distinct ISNULL(TONGLUONG, '0')) as tongluongPhaiMua,  " +
				"SUM( distinct ISNULL(tongtien, 0) ) as tongtienPhaiMua,  " +
				"count(case when isnull(c.soluong, '0') > 0 then 1 else null end) as sospbatbuoc  " +
				"from CTTB_NHOMSPTRUNGBAY a inner join NHOMSPTRUNGBAY b on a.NHOMSPTRUNGBAY_FK = b.PK_SEQ " +
				"inner join NHOMSPTRUNGBAY_SANPHAM c on a.NHOMSPTRUNGBAY_FK = c.NHOMSPTRUNGBAY_FK " +
				"where CTTRUNGBAY_FK = '" + ctkmId + "'  " +
				"group by b.PK_SEQ, b.LOAI, a.pheptoan " +
				")  " +
				"dieukientrungbay inner join " +
				"( " +
				"select muatrongnhom.*, batbuocmua.batbuoc " +
				"from ( " +
				"select a.KHACHHANG_FK, c.NHOMSPTRUNGBAY_FK as nspId, b.SANPHAM_FK, SUM(b.SOLUONG * b.GIAMUA) as tongtien, SUM(b.soluong) as tongluong, '1' as type  " +
				"from DONHANG a inner join DONHANG_SANPHAM b on a.PK_SEQ = b.DONHANG_FK " +
				"inner join NHOMSPTRUNGBAY_SANPHAM c on b.SANPHAM_FK = c.SANPHAM_FK " +
				"where a.PK_SEQ = '" + dhId + "' and  " +
				"c.NHOMSPTRUNGBAY_FK in ( select NHOMSPTRUNGBAY_FK from CTTB_NHOMSPTRUNGBAY where CTTRUNGBAY_FK = '" + ctkmId + "' )  " +
				"group by KHACHHANG_FK, c.NHOMSPTRUNGBAY_FK, b.SANPHAM_FK " +
				") " +
				"muatrongnhom left join  " +
				"( " +
				"select NHOMSPTRUNGBAY_FK as nspId, SANPHAM_FK, case when isnull(soluong, '0') <= 0 then -1 else soluong end as batbuoc  " +
				"from NHOMSPTRUNGBAY_SANPHAM  " +
				"where NHOMSPTRUNGBAY_FK in ( select NHOMSPTRUNGBAY_FK from CTTB_NHOMSPTRUNGBAY where CTTRUNGBAY_FK = '" + ctkmId + "' ) " +
				")  " +
				"batbuocmua on muatrongnhom.SANPHAM_FK = batbuocmua.SANPHAM_FK and muatrongnhom.nspId = batbuocmua.nspId " +
				"where muatrongnhom.tongluong > batbuocmua.batbuoc " +
				") " +
				"trungbaytheodk on dieukientrungbay.nsptbId = trungbaytheodk.nspId  " +
				"group by  dieukientrungbay.nsptbId, dieukientrungbay.LOAI, dieukientrungbay.tongluongPhaiMua, dieukientrungbay.tongtienPhaiMua,  " +
				"dieukientrungbay.sospbatbuoc, dieukientrungbay.pheptoan, trungbaytheodk.KHACHHANG_FK  " +
				"having COUNT(case when trungbaytheodk.batbuoc > 0 then 1 else null end ) >= dieukientrungbay.sospbatbuoc " +
				") chuongtrinhtrungbay " +
				") ngansachkhuyenmai";

		System.out.println("___Lay so xuat CTTB: " + query);

		ResultSet rs = db.get(query);
		int soDK = 0;
		if(rs != null)
		{
			try 
			{
				if(rs.next())
				{
					soxuat = rs.getInt("SoXuatThoa");
					soDK = rs.getInt("sonhom");
				}
				rs.close();
			} 
			catch (Exception e) 
			{
				System.out.println("115.Error: " + e.getMessage());
			}
		}

		//Check So Dieu Kien AND
		int sophaithoa = 0;
		query = "select SUM(case pheptoan when 1 then 1 else 0 end) as sodieukien from CTTB_NHOMSPTRUNGBAY where CTTRUNGBAY_FK = '" + ctkmId + "' ";
		ResultSet rsCheck = db.get(query);
		if(rsCheck != null)
		{
			try 
			{
				while(rsCheck.next())
				{
					sophaithoa = rsCheck.getInt("sodieukien");
				}
				rsCheck.close();
			} 
			catch (Exception e) 
			{
				System.out.println("115.Errror: Loi: " + e.getMessage());
			}
		}

		System.out.println("_____________So dieu kien Ly thuyet: " + soDK + "  --- So bat buoc: " + sophaithoa);
		if(soDK < sophaithoa)
		{
			soxuat = 0;
		}


		return soxuat;
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

	public Hashtable<String, Integer> ApTrungBay(String dhId, String khId, String nppId, String ngaydh) 
	{
		/*******************       0 - Khong co trung bay,  1 - Co trung bay, -1 - Loi khi cap nhat trung bay                 *********************/		 

		Hashtable<String, Integer> kq = new Hashtable<String, Integer>();

		//truong hop cap nhat, phai xoa so xuat cu
		String query = "select DENGHITRATB_FK, khachhang_fk, dat from DENGHITRATB_DONHANG where donhang_fk = '" + dhId + "'";
		ResultSet rsDelete = db.get(query);
		if(rsDelete != null)
		{
			try 
			{
				db.getConnection().setAutoCommit(false);
				while(rsDelete.next())
				{
					String dk_fk = rsDelete.getString("DENGHITRATB_FK");
					String kh_fk = rsDelete.getString("khachhang_fk");
					String dat = rsDelete.getString("dat");

					query = "delete DENGHITRATB_DONHANG where DENGHITRATB_FK = '" + dk_fk + "' and khachhang_fk = '" + kh_fk + "' and donhang_fk = '" + dhId + "'";
					if(!db.update(query))
					{
						this.msg = "1.Không thể cập nhật DENGHITRATB_DONHANG " + query;
						db.getConnection().rollback();
						return kq;
					}

					query = "update DENGHITRATB_KHACHHANG set dat = dat - " + dat + " where DENGHITRATB_FK = '" + dk_fk + "' and khachhang_fk = '" + kh_fk + "'";
					if(!db.update(query))
					{
						this.msg = "2.Không thể cập DENGHITRATB_KHACHHANG " + query;
						db.getConnection().rollback();
						return kq;
					}

				}
				rsDelete.close();

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

				this.msg = "115. Loi: " + e.getMessage();
				return kq;
			}
		}

		query =
				"select a.CTTRUNGBAY_FK, b.DENGHITRATB_FK, c.scheme, c.NGAYTDS as TuNgay, c.NGAYKTTDS as DenNgay, isnull(b.DAT, 0) as DaDat, b.XUATDUYET - isnull(b.DAT, 0) as NganSachConLai " +
						"from DENGHITRATRUNGBAY a inner join DENGHITRATB_KHACHHANG b on a.PK_SEQ = b.DENGHITRATB_FK  " +
						"inner join CTTRUNGBAY c on a.CTTRUNGBAY_FK = c.PK_SEQ " +
						"where KHACHHANG_FK = '" + khId + "' and a.NPP_FK = '" + this.nppId + "' and c.NGAYTDS <= '" + ngaydh + "' " +
						"and c.NGAYKTTDS >= '" + ngaydh + "' and isnull(b.DAT, 0) < b.XUATDUYET and a.trangthai=1 ";

		System.out.println("___Check CTTB: " + query);

		ResultSet rs = db.getScrol(query);

		String cttb_fk = "";
		String dktb_fk = "";

		int ngansach = 0;
		int NganSachConLai = 0;
		int soXuat = 0;

		int i = 0;

		if(rs != null) // ap trung bay repair
		{
			try 
			{
				db.getConnection().setAutoCommit(false);

				while(rs.next())
				{

					cttb_fk = rs.getString("CTTRUNGBAY_FK");
					dktb_fk = rs.getString("DENGHITRATB_FK");
					NganSachConLai = rs.getInt("NganSachConLai");

					//LAY NGAN SACH CON LAI
					query =  "select a.LEVEL_PHANBO, " + 
							"	case a.LEVEL_PHANBO when 0 then  " + 
							"		( select NGANSACH from PHANBOTRUNGBAY where NPP_FK = '" + this.nppId + "' and CTTB_FK = a.PK_SEQ ) " + 
							"	when 1 then   " + 
							"		( select NGANSACH from PHANBOTRUNGBAY where VUNG_FK = ( select VUNG_FK from KHUVUC where pk_seq in ( select KHUVUC_FK from NHAPHANPHOI where PK_SEQ = '" + this.nppId + "' )  ) and CTTB_FK = a.PK_SEQ ) " + 
							"	else " + 
							"		( select NGANSACH from PHANBOTRUNGBAY where KHUVUC_FK = ( select KHUVUC_FK from NHAPHANPHOI where PK_SEQ = '" + this.nppId + "' ) and CTTB_FK = a.PK_SEQ ) " + 
							"	end as NGANSACH, " + 
							"	ISNULL( ( " + 
							"		select SUM(ISNULL( b.DAT , 0 ) )  " + 
							"		from DENGHITRATRUNGBAY a inner join DENGHITRATB_KHACHHANG b on a.PK_SEQ = b.DENGHITRATB_FK " + 
							"				inner join CTTRUNGBAY c on a.CTTRUNGBAY_FK = c.PK_SEQ " + 
							"				inner join NHAPHANPHOI d on a.NPP_FK = d.PK_SEQ " + 
							"				inner join KHUVUC e on d.KHUVUC_FK = e.PK_SEQ " + 
							"		where CTTRUNGBAY_FK = '" + cttb_fk + "' and " + 
							"				( case c.LEVEL_PHANBO   when 0 then a.NPP_FK when 1 then e.VUNG_FK when 2 then d.KHUVUC_FK end  )    " + 
							"			  = ( case c.LEVEL_PHANBO   when 0 then '" + this.nppId + "'   " + 
							"										when 1 then ( select VUNG_FK from KHUVUC where PK_SEQ = ( select KHUVUC_FK from NHAPHANPHOI where PK_SEQ = '" + this.nppId + "' ) )   " + 
							"										when 2 then ( select KHUVUC_FK from NHAPHANPHOI where PK_SEQ = '" + this.nppId + "' )  end  )    " + 
							"	), 0 ) as DASUDUNG " + 
							"from CTTRUNGBAY a   " + 
							"where a.PK_SEQ = '" + cttb_fk + "' ";
					ResultSet rsNGANSACH = db.get(query);
					if(rsNGANSACH.next())
					{
						ngansach = rsNGANSACH.getInt("NGANSACH") - rsNGANSACH.getInt("DASUDUNG");
					}
					rsNGANSACH.close();

					soXuat = getSoXuatTheoScheme(dhId, cttb_fk);

					System.out.println("__-____Soxuat la: " + soXuat);

					if(soXuat > 0)
					{
						if(ngansach - soXuat <= 0)
						{
							soXuat = ngansach;
						}

						if(soXuat > 0)
						{
							if(soXuat > NganSachConLai)
								soXuat = NganSachConLai;

							kq.put(rs.getString("scheme") , soXuat);
						}	
					}
				}
				rs.close();		

				db.getConnection().commit();
				db.getConnection().setAutoCommit(true);
			}
			catch (Exception e) 
			{
				System.out.println("__Exception: " + e.getMessage());
				this.msg = "__Exception: " + e.getMessage();

				try 
				{
					db.getConnection().rollback();
				}
				catch (SQLException e1) {}

				return kq;
			}
		}

		return kq;
	}

	public boolean LuuTrungBay(String scheme, Integer soXuat)
	{

		String dktb_fk = "";
		String query =  "select a.CTTRUNGBAY_FK, b.DENGHITRATB_FK, c.scheme, c.NGAYTDS as TuNgay, c.NGAYKTTDS as DenNgay, isnull(b.DAT, 0) as DaDat, b.DANGKY - isnull(b.DAT, 0) as NganSachConLai " +
				"from DENGHITRATRUNGBAY a inner join DENGHITRATB_KHACHHANG b on a.PK_SEQ = b.DENGHITRATB_FK  " +
				"inner join CTTRUNGBAY c on a.CTTRUNGBAY_FK = c.PK_SEQ " +
				"where KHACHHANG_FK = '" + khId + "' and a.NPP_FK = '" + this.nppId + "' and c.NGAYTDS <= '" + this.ngaygiaodich + "' " +
				"and c.NGAYKTTDS >= '" + this.ngaygiaodich + "'  and c.scheme = '"+ scheme +"'";
		System.out.println("Luu trung bay : "+query);

		try
		{
			ResultSet rs = db.get(query);
			while(rs.next())
			{

				dktb_fk = rs.getString("DENGHITRATB_FK");

				query = "insert DENGHITRATB_DONHANG(DENGHITRATB_FK, KHACHHANG_FK, DONHANG_FK, DAT) " +
						"values('" + dktb_fk + "', '" + khId + "', '" + this.id + "', '" + soXuat + "')";
				if(!db.update(query))
				{
					this.msg = "2.Không thể cập nhật trưng bày";
					rs.close();
					//db.getConnection().rollback();
					return false;
				}


				query = "update DENGHITRATB_KHACHHANG set dat = isnull( ( select SUM(DAT) from DENGHITRATB_DONHANG  " +
						"where DENGHITRATB_FK = '" + dktb_fk + "' and KHACHHANG_FK = '" + khId + "' group by DENGHITRATB_FK, KHACHHANG_FK ) , 0 )  " +
						"where DENGHITRATB_FK = '" + dktb_fk + "' and KHACHHANG_FK = '" + khId + "'";

				System.out.println("11.-------Update DK trung bay: " + query);
				if(!db.update(query))
				{
					this.msg = "3.Không thể cập nhật trưng bày";
					rs.close();
					//db.getConnection().rollback();
					return false;
				}
			}
		}catch(Exception ex)
		{			
			ex.printStackTrace();			
			return false;
		}


		return false;
	}

	@Override
	public String getDiachigiaohang() {
		return this.diachigiaohang;
	}

	@Override
	public void setDiachigiaohang(String diachigiaohang) {
		this.diachigiaohang = diachigiaohang;
	}

}
