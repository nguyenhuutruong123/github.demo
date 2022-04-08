package geso.dms.distributor.beans.dieuchinhtonkho.imp;

import geso.dms.distributor.db.sql.*;
import geso.dms.distributor.util.FixData;
import geso.dms.distributor.util.Utility;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import geso.dms.distributor.beans.dieuchinhtonkho.IDieuchinhtonkho;


public class Dieuchinhtonkho implements IDieuchinhtonkho, Serializable
{
	private static final long serialVersionUID = -9217977546733610214L;
	String id;
	String nppId;
	String nppTen;
	String userId;
	String userTen;
	String ngaydc;
	String file;
	String nccId;
	ResultSet ncc;
	String dvkdId;
	ResultSet dvkd;
	String kbhId;
	ResultSet kbh;
	String khoId;
	ResultSet kho;

	String gtdc;

	String lydodc;

	String msg;
	String[] spId;
	String[] masp;
	String[] tensp;
	String[] tkht;
	String[] dv;
	String[] dc;
	String[] tkm;
	String[] giamua;
	String[] ttien;
	String[] soluongle;
	String[] ngaynhapkho ;

	String size;
	String maspstr;

	String sodong;

	Hashtable<String, String> dvdlList;
	NumberFormat formatter = new DecimalFormat("#,###,###");
	dbutils db;


	public Dieuchinhtonkho(String[] param)
	{
		this.db = new dbutils();
		this.id = param[0];
		this.nppId = param[1];
		this.nppTen = param[2];
		this.userId = param[3];
		this.userTen = param[4];
		this.ngaydc = param[5];
		this.nccId = param[6];
		this.dvkdId = param[7];
		this.kbhId = param[8];
		this.gtdc = param[9];
		this.lydodc = "";
		this.sodong = "30";
		this.file = "";
	}

	public Dieuchinhtonkho()
	{
		this.db = new dbutils();
		this.id = "";
		this.nppId = "";
		this.nppTen = "";
		this.userId = "";
		this.userTen = "";
		this.nccId = "";
		this.dvkdId = "";
		this.kbhId = "";
		this.khoId = "";
		this.gtdc = "";
		this.msg = "";
		this.ngaydc = getDateTime();
		this.lydodc = "";
		this.sodong = "30";
		dvdlList= new Hashtable<String, String>();
		db = new dbutils();
		this.file = "";
	}

	public String getId()
	{
		return this.id;
	}

	public void setId(String id)
	{
		this.id = id;
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

	public String getUserId()
	{
		return this.userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
		geso.dms.distributor.util.Utility util=new geso.dms.distributor.util.Utility();
		this.nppId = util.getIdNhapp(this.userId);
		this.nppTen = util.getTenNhaPP();
		//this.dangnhap = util.getDangNhap();
		//this.sitecode=util.getSitecode();
	}

	public String getUserTen()
	{
		return this.userTen;
	}

	public void setUserTen(String userTen)
	{
		this.userTen = userTen;
	}

	public String getNgaydc()
	{
		return this.ngaydc;
	}

	public void setNgaydc(String ngaydc)
	{
		this.ngaydc = ngaydc;
	}

	public String getNccId()
	{
		return this.nccId;
	}

	public void setNccId(String nccId)
	{
		this.nccId = nccId;
	}

	public ResultSet getNcc()
	{
		return this.ncc;
	}

	public void setNcc(ResultSet ncc)
	{
		this.ncc = ncc;
	}

	public String getDvkdId()
	{
		return this.dvkdId;
	}

	public void setDvkdId(String dvkdId)
	{
		this.dvkdId = dvkdId;
	}

	public ResultSet getDvkd()
	{
		return this.dvkd;
	}

	public void setDvkd(ResultSet dvkd)
	{
		this.dvkd = dvkd;
	}


	public String getKbhId()
	{
		return this.kbhId;
	}

	public void setKbhId(String kbhId)
	{
		this.kbhId = kbhId;
	}

	public ResultSet getKbh()
	{
		return this.kbh;
	}

	public void setKbh(ResultSet kbh)
	{
		this.kbh = kbh;
	}

	public String getKhoId()
	{
		return this.khoId;
	}

	public void setKhoId(String khoId)
	{
		this.khoId = khoId;
	}

	public ResultSet getKho()
	{
		return this.kho;
	}

	public void setKho(ResultSet kho)
	{
		this.kho = kho;
	}

	public String getGtdc()
	{
		return this.gtdc;
	}

	public void setGtdc(String gtdc)
	{
		this.gtdc = gtdc;
	}

	public String getSize()
	{
		return this.size;
	}

	public void setSize(String size)
	{
		this.size = size;
	}

	public String getMaspstr()
	{
		return this.maspstr;
	}

	public void setMaspstr(String maspstr)
	{
		this.maspstr = maspstr;
	}

	public String[] getSpId()
	{
		return this.spId;
	}

	public void setSpId(String[] spId)
	{
		this.spId = spId;
	}

	public String[] getMasp()
	{
		return this.masp;
	}

	public void setMasp(String[] masp)
	{
		this.masp = masp;
	}

	public String[] getTensp()
	{
		return this.tensp;
	}

	public void setTenSp(String[] tensp)
	{
		this.tensp = tensp;
	}

	public String[] getTkht()
	{
		return this.tkht;
	}

	public void setTkht(String[] tkht)
	{
		this.tkht = tkht;
	}

	public String[] getDonvi()
	{
		return this.dv;
	}

	public void setDonvi(String[] dv)
	{
		this.dv = dv;
	}

	public String[] getDc()
	{
		return this.dc;
	}

	public void setDc(String[] dc)
	{
		this.dc = dc;
	}

	public String[] getGiamua()
	{
		return this.giamua;
	}

	public void setGiamua(String[] giamua)
	{
		this.giamua = giamua;
	}

	public String[] getTtien()
	{
		return this.ttien;
	}

	public void setTtien(String[] ttien)
	{
		this.ttien = ttien;
	}

	public String getMessage()
	{
		return this.msg;
	}

	public void setMessage(String msg)
	{
		this.msg = msg;
	}

	public void init0(){
		getNppInfo();
		String query = "select e.pk_seq as dvkdId, e.donvikinhdoanh as dvkd from nhaphanphoi a, nhacungcap b, nhacungcap_dvkd c, nhapp_nhacc_donvikd d, donvikinhdoanh e where a.pk_seq = '"+ this.nppId + "' and a.pk_seq = d.npp_fk and b.pk_seq = c.ncc_fk and c.pk_seq = d.ncc_dvkd_fk and c.dvkd_fk = e.pk_seq";
		this.dvkd = this.db.get(query);

		query = "select c.pk_seq as kbhId, c.diengiai as kbh from  kenhbanhang c where c.TRANGTHAI = 1 ";
		this.kbh = this.db.get(query);

		query = "select distinct a.pk_seq as khoId, a.ten as ten,a.diengiai from kho a, nhapp_kho b where a.pk_seq = b.kho_fk and b.npp_fk = '"+ this.nppId + "' and a.trangthai ='1'" ;
		this.kho = this.db.get(query);
	}

	public void initNew(){

		init0();

	}

	public void initUpdate(String kt){
		init0();
		ResultSet rs = null;
		try{
			Hashtable ht = new Hashtable();
			String query;

			query = "select ngaydc, npp_fk, dvkd_fk, kbh_fk, tongtien,lydodc, kho_fk,isnull(filename,'') as filename from dieuchinhtonkho where pk_seq='" + this.id + "' and trangthai='0'";
			rs = this.db.get(query);
			System.out.print(query);

			rs.next();
			this.ngaydc = rs.getString("ngaydc");
			this.dvkdId = rs.getString("dvkd_fk");
			this.nppId	= rs.getString("npp_fk");
			this.kbhId	= rs.getString("kbh_fk");
			this.khoId	= rs.getString("kho_fk");
			this.gtdc 	= rs.getString("tongtien");
			this.lydodc=rs.getString("lydodc");
			if(kt.equals("1"))
				this.file = rs.getString("filename");

			rs.close();

			query = "select sanpham_fk as id, sum(dieuchinh) as dc from dieuchinhtonkho_sp where dieuchinhtonkho_fk='" + this.id + "' group by sanpham_fk ";
			rs = this.db.get(query);
			while(rs.next()){
				ht.put(rs.getString("id"), rs.getString("dc"));
			}
			rs.close();
			query = "select count(distinct sanpham_fk) as num from dieuchinhtonkho_sp where dieuchinhtonkho_fk='" + this.id + "'";

			rs = this.db.get(query);
			if (rs != null){
				rs.next();
				int size = Integer.valueOf(rs.getString("num")).intValue();
				this.size = "" + size;
				this.spId = new String[size];
				this.masp = new String[size];
				this.ngaynhapkho = new String[size];
				this.tensp = new String[size];
				this.tkht = new String[size];
				this.dv = new String[size];
				this.dc  = new String[size];
				this.giamua = new String[size];
				this.ttien = new String[size];
				this.soluongle =new String[size];
				rs.close();
				maspstr = "";
				query =
					"\n		SELECT  sp.pk_Seq as spId,sp.MA ,sp.TEN,dvdl.DONVI,SUM(DCSP.DIEUCHINH) AS DIEUCHINH, "+
					"\n  dcsp.GIAMUA , "+
					"\n  isnull((SELECT SUM(SOLUONG) FROM NHAPP_KHO WHERE NPP_FK = DC.NPP_FK AND SANPHAM_FK = DCSP.SANPHAM_FK AND KBH_FK = DC.KBH_FK AND KHO_FK = DC.KHO_FK),0) AS soluong , "+
					"\n isnull((SELECT SUM(SOLUONG) FROM NHAPP_KHO WHERE NPP_FK = DC.NPP_FK AND SANPHAM_FK = DCSP.SANPHAM_FK AND KBH_FK = DC.KBH_FK AND KHO_FK = DC.KHO_FK),0) + SUM(dcsp.DIEUCHINH) AS TONMOI	"+			
					"\n		,sum(dcsp.thanhtien) from dieuchinhtonkho_sp dcsp "+
					"\n			inner join DIEUCHINHTONKHO dc on dc.PK_SEQ=dcsp.DIEUCHINHTONKHO_FK "+
					"\n			inner join SANPHAM sp on sp.PK_SEQ=dcsp.SANPHAM_FK "+
					"\n			inner join DONVIDOLUONG dvdl on dvdl.PK_SEQ=sp.DVDL_FK " +
					"\n       where dc.pk_seq='"+this.id+"'        " +
					"\n GROUP BY sp.pk_Seq ,sp.MA ,sp.TEN,dvdl.DONVI,dcsp.GIAMUA,DC.NPP_FK,dcsp.SANPHAM_FK,DC.KBH_FK,DC.KHO_FK ";

				System.out.println("______init: "+query);
				rs = this.db.get(query);
				int i = 0;
				while(rs.next()){
					this.spId[i] = rs.getString("spId");
					this.masp[i] = rs.getString("ma");
					if (this.maspstr.length()==0){
						this.maspstr = "'" + this.masp[i] + "'";
					}else{
						this.maspstr = this.maspstr + ",'" + this.masp[i] + "'";
					}

					this.tensp[i]= rs.getString("ten");
					this.tkht[i] = rs.getString("soluong");
					this.dv[i] = rs.getString("donvi");
					this.giamua[i] = rs.getString("giamua");
					this.soluongle[i]=rs.getString("TONMOI");
					/*this.ngaynhapkho[i]=rs.getString("ngaynhapkho");*/

					//sua lai gia mua thanh kieu float,de luu gia lai cho chinh xac,va khoi bi loi trong nhug truong hop gia co dau .dong kieu long ko parse ra dc
					if(ht.containsKey(this.spId[i])){
						this.dc[i] = (String) ht.get(this.spId[i]);
						long  soluongdc=0;
						float giamua_=0;
						try{
							soluongdc=Long.parseLong(this.dc[i]);
						}catch(Exception er){

						}
						try{
							giamua_=Float.parseFloat(giamua[i]);
						}catch(Exception er){

						}
						this.ttien[i] = "" + soluongdc* giamua_;
						NumberFormat formatter = new DecimalFormat("#,###,###.##");
						this.ttien[i] = formatter.format(soluongdc* giamua_);
						System.out.println("_____init dc: "+this.ttien[i]);
					}else{
						this.dc[i] = "0";
						this.ttien[i] = "0";
					}


					i++;
				}

			}else{
				this.masp = new String[0];
				this.tensp = new String[0];
				this.tkht = new String[0];
				this.dv = new String[0];
				this.dc  = new String[0];
				this.giamua  = new String[0];
				this.ttien = new String[0];
				this.soluongle = new String[0];
				this.ngaynhapkho = new String[0];

			}
		}catch(Exception e){}
		finally{if(rs != null)
			try {
				rs.close();
			} catch(Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}}
	}

	public void initDisplay(){
		init0();ResultSet rs = null;
		try{
			Hashtable ht = new Hashtable();
			String query;

			query = "select ngaydc, npp_fk, dvkd_fk, kbh_fk, tongtien,lydodc, kho_fk,isnull(filename,'') as filename from dieuchinhtonkho where pk_seq='" + this.id + "' ";

			rs = this.db.get(query);

			rs.next();
			this.ngaydc = rs.getString("ngaydc");
			this.dvkdId = rs.getString("dvkd_fk");
			this.nppId	= rs.getString("npp_fk");
			this.kbhId	= rs.getString("kbh_fk");
			this.khoId	= rs.getString("kho_fk");
			this.gtdc 	= rs.getString("tongtien");
			this.lydodc=rs.getString("lydodc");
			this.file = rs.getString("filename");
			System.out.println("Filename: "+this.file);
			rs.close();

			query = "select count(distinct sanpham_fk) as num from dieuchinhtonkho_sp where dieuchinhtonkho_fk='" + this.id + "'";
			rs = this.db.get(query);

			if (rs != null){
				rs.next();
				int size = Integer.valueOf(rs.getString("num")).intValue();
				this.size = "" + size;
				this.spId = new String[size];
				this.masp = new String[size];
				this.ngaynhapkho = new String[size];
				this.soluongle = new String[size];
				this.tensp = new String[size];
				this.tkht = new String[size];
				this.dv = new String[size];
				this.dc  = new String[size];
				this.giamua = new String[size];
				this.ttien = new String[size];
				rs.close();
			}

			maspstr = "";

			/*query = 
					"select a.ngaynhapkho ,a.sanpham_fk as spId, b.ma, b.ten, a.dieuchinh as dc, c.donvi, a.giamua, a.thanhtien, a.tonhientai, a.tonmoi  "+ 
					" from dieuchinhtonkho_sp a inner join sanpham b on b.pk_Seq=a.sanpham_fk   "+
					"	inner join donvidoluong c on c.pk_seq=b.dvdl_Fk where dieuchinhtonkho_fk='" + this.id + "' and a.sanpham_fk=b.pk_seq";
			 */	query =
				 "\n		SELECT  sp.pk_Seq as spId,sp.MA ,sp.TEN,dvdl.DONVI,SUM(dcsp.DIEUCHINH) AS dc, "+
				 "\n  dcsp.GIAMUA , isnull(max(dcsp.TONHIENTAI),0) AS TONHIENTAI ,(SELECT SUM(SOLUONG) FROM NHAPP_KHO WHERE NPP_FK = DC.NPP_FK AND SANPHAM_FK = DCSP.SANPHAM_FK AND KBH_FK = DC.KBH_FK AND KHO_FK = DC.KHO_FK) AS soluong  "+
				 "\n   , isnull(max(dcsp.TONHIENTAI),0) +  SUM(dcsp.DIEUCHINH)  TONMOI	"+			
				 "\n	,sum(dcsp.thanhtien) as thanhtien	from dieuchinhtonkho_sp dcsp "+
				 "\n			inner join DIEUCHINHTONKHO dc on dc.PK_SEQ=dcsp.DIEUCHINHTONKHO_FK "+
				 "\n			inner join SANPHAM sp on sp.PK_SEQ=dcsp.SANPHAM_FK "+
				 "\n			inner join DONVIDOLUONG dvdl on dvdl.PK_SEQ=sp.DVDL_FK " +
				 "\n       where dc.pk_seq='"+this.id+"'        "+
				 "\n GROUP BY dcsp.TONHIENTAI,sp.pk_Seq ,sp.MA ,sp.TEN,dvdl.DONVI,dcsp.GIAMUA,DC.NPP_FK,dcsp.SANPHAM_FK,DC.KBH_FK,DC.KHO_FK";

			 System.out.println("initD: " + query);
			 rs = this.db.get(query);
			 int i = 0;
			 while(rs.next()){
				 this.spId[i] = rs.getString("spId");
				 this.masp[i] = rs.getString("ma");

				 //this.ngaynhapkho[i] = rs.getString("ngaynhapkho");
				 this.soluongle[i] = rs.getString("tonmoi");

				 if (this.maspstr.length()==0){
					 this.maspstr = "'" + this.masp[i] + "'";
				 }else{
					 this.maspstr = this.maspstr + ",'" + this.masp[i] + "'";
				 }

				 this.tensp[i]= rs.getString("ten");
				 this.tkht[i] = rs.getString("tonhientai");
				 this.dv[i] = rs.getString("donvi");
				 this.giamua[i] = formatter.format(rs.getDouble("giamua"));
				 this.dc[i] = rs.getString("dc");

				 this.ttien[i] =  formatter.format( rs.getDouble("thanhtien") );
				 i++;
			 }
			 rs.close();	
		}catch(Exception e)
		{
			e.printStackTrace();

		}
		finally{if(rs != null)
			try {
				rs.close();
			} catch(Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}}
	}

	public boolean CreateDctk(HttpServletRequest request)
	{
		getNppInfo();
		ResultSet rs=null;
		try
		{

			if(this.dvkdId.equals(""))
			{
				this.msg="Vui lòng chọn đơn vị kinh doanh";
				return false;
			}
			if(this.kbhId.equals(""))
			{
				this.msg="Vui lòng chọn kênh bán hàng";
				return false;
			}
			if(this.khoId.equals(""))
			{
				this.msg="Vui lòng chọn kho";
				return false;
			}
			

			String query ="select count(pk_seq) as num from dieuchinhtonkho where npp_fk='" + this.nppId + "' and dvkd_fk = '" + this.dvkdId + "' and kbh_fk = '" + this.kbhId + "' and trangthai='0' and kho_fk='"+this.khoId+"'";
			System.out.println("Lay dieu chinh ton kho :"+query);
			rs = this.db.get(query);
			rs.next();
			if (rs.getString("num").equals("0"))
			{
				rs.close();

				/*FixData fixed = new FixData();
				fixed.ProcessBOOKED( this.nppId, "", db);*/

				this.db.getConnection().setAutoCommit(false);

				this.ngaydc = request.getParameter("ngaydc");	
				if (this.ngaydc == null)
					this.ngaydc = this.getDateTime();


				
				
				String msgKS  = geso.dms.center.util.Utility.Check_Huy_NghiepVu_KhoaSo_Tao_Moi_NV(this.nppId, this.ngaydc, db);
				if( msgKS.length() > 0)
				{
					db.getConnection().rollback();
					db.getConnection().setAutoCommit(true);
					this.msg = msgKS;
					return false;
				}

				this.dvkdId = request.getParameter("dvkdId");
				this.kbhId 	= request.getParameter("kbhId");
				this.khoId  = request.getParameter("khoId");
				this.gtdc 	= request.getParameter("ttien").replace(",", "");
				query = "insert into dieuchinhtonkho(NGAYDC,NGUOITAO,NGUOISUA,TRANGTHAI,NPP_FK,DVKD_FK,KBH_FK,TONGTIEN,KHO_FK,NGUOIDUYET,LYDODC,filename,ngaygiotao)" +
				" values('" + this.ngaydc + "', '" + this.userId + "','" + this.userId + "','0', '" + this.nppId + "','" + this.dvkdId + "','" + this.kbhId + "', '" + this.gtdc + "','" + this.khoId + "','0', N'" + this.lydodc + "',N'" + this.file + "', CONVERT(char(10),GETDATE(),121) + ' ' + CONVERT(CHAR( 5),GETDATE(),114) )";
				System.out.println("Command"+query);
				this.msg= query;
				if(!this.db.update(query))
				{
					this.msg = "Khong The Insert,Vui Long Kiem Tra Lai Du Lieu.Loi :"+query;
					geso.dms.center.util.Utility.rollback_throw_exception(this.db);
					return false;
				}
				query = "select SCOPE_IDENTITY() as dctkId";
				rs = this.db.get(query);
				rs.next();
				this.id = rs.getString("dctkId");
				for (int i = 0; i < Integer.parseInt(size) ; i++)
				{
					double tonm = Double.parseDouble(this.soluongle[i].replaceAll(",", ""));
					double tonht = Double.parseDouble(this.tkht[i].replaceAll(",", ""));
					double luongdieuchinh = tonm - tonht;
					System.out.println("spma : "+ this.masp[i] +" - luongdieuchinh : "+ luongdieuchinh +" - tonmoi : "+ tonm +" - tkht : "+ tonht);
					/*if (dc[i].length()>0)
					{
						if(Math.abs(Double.parseDouble(this.dc[i].replaceAll(",", ""))) > 0 )*/
						if(Math.abs(luongdieuchinh) > 0 )
						{
							double dieuchinh_ = 0;
							//dieuchinh_= Integer.parseInt(this.dc[i].replaceAll(",", ""));
							dieuchinh_ = luongdieuchinh;
  
							geso.dms.center.util.Utility util = new geso.dms.center.util.Utility();
							if(dieuchinh_ < 0)
							{

								///x1
								//double soluongdenghi = Integer.parseInt(this.dc[i].replaceAll(",", "")) ;
								double soluongdenghi = luongdieuchinh;
								soluongdenghi =soluongdenghi*(-1);
								double soluongbandau=soluongdenghi;

								/////////////////////////////// ĐỀ XUẤT LÔ
								query=  " select KHO_FK,SANPHAM_FK,KBH_FK,NGAYNHAPKHO,available,soluong" +
								"  ,(SELECT SUM(SOLUONG) FROM NHAPP_KHO t WHERE t.NPP_FK = ct.NPP_FK AND t.SANPHAM_FK = ct.SANPHAM_FK AND t.KBH_FK = ct.KBH_FK AND t.KHO_FK = ct.KHO_FK) sltong    " +
								" from NHAPP_KHO_CHITIET ct "+  
								" where NPP_FK ="+nppId+" and KBH_FK= " +this.kbhId +
								" and KHO_FK="+this.khoId+"  and SANPHAM_FK =  "+ this.spId[i] +
								" AND AVAILABLE >0  and NGAYNHAPKHO<='"+this.ngaydc+"'"+
								" order by NGAYNHAPKHO desc,AVAILABLE ";
								ResultSet rssp=db.get(query);
								System.out.println("Ton : "+ query);
								int sosanh=0;
								while(rssp.next() && soluongdenghi >0){
									double soluong_avai= rssp.getDouble("AVAILABLE");
									double soluongcapnhat=0;
									if(soluong_avai >soluongdenghi){
										soluongcapnhat= soluongdenghi;
										soluongdenghi=0;
									}else{
										soluongcapnhat =soluong_avai;
										soluongdenghi =soluongdenghi - soluong_avai;
									}

									String _khoid=rssp.getString("kho_fk");
									String _kbhid=rssp.getString("KBH_FK");
									String ngaynhapkho=rssp.getString("NGAYNHAPKHO");
									String tonhientai=rssp.getString("sltong");
									double slct=  rssp.getDouble("soluong");
									String thanhtien = Double.toString(Double.parseDouble(giamua[i].replaceAll(",",""))*soluongcapnhat );
									String tonmoi = Double.toString( slct - soluongcapnhat );
									System.out.println("____dieuchinh: "+soluongcapnhat*(-1));
									sosanh+=soluongcapnhat;
									String command = 
										"INSERT INTO dieuchinhtonkho_sp(DIEUCHINHTONKHO_FK,SANPHAM_FK,DIEUCHINH,GIAMUA,THANHTIEN,TONHIENTAI,TONMOI,NGAYNHAPKHO)" +
										" values('" + this.id + "','" +this.spId[i] + "','" + soluongcapnhat*(-1) + "','"+giamua[i].replaceAll(",","")+"','"+thanhtien+"','" + tonhientai+ "','" + tonmoi+ "','"+ngaynhapkho+"')";
									System.out.println("Command"+command);
									if(!this.db.update(command))
									{
										this.msg = command;
										geso.dms.center.util.Utility.rollback_throw_exception(this.db);
										return false;
									}

									msg=util.Update_NPP_Kho(ngaydc,this.id, "TẠO DIEUCHINHTONKHO", db, _khoid, this.spId[i] , this.nppId, _kbhid,ngaynhapkho, 0.0,  soluongcapnhat, (-1)*soluongcapnhat,0, 0.0);
									if(msg.length()>0)
									{
										geso.dms.center.util.Utility.rollback_throw_exception(this.db);
										return false;	
									}	
								}	
								//System.out.println("____soluongdenghi: "+soluongbandau);
								//System.out.println("____sosanh: "+sosanh);
								if(soluongbandau!=sosanh)
								{
									geso.dms.center.util.Utility.rollback_throw_exception(this.db);
									this.msg="Không đủ tồn để điều chỉnh. Mã sản phẩm: " +this.masp[i];
									return false;
								}

							}
							else
							{
								
								String tht = " ( SELECT SUM(SOLUONG) FROM NHAPP_KHO t WHERE t.NPP_FK = "+this.nppId+" AND t.SANPHAM_FK = "+this.spId[i]+" AND t.KBH_FK = "+this.kbhId+" AND t.KHO_FK = "+this.khoId+" )";
								
								String command = 
									"INSERT INTO dieuchinhtonkho_sp(DIEUCHINHTONKHO_FK,SANPHAM_FK,DIEUCHINH,GIAMUA,THANHTIEN,TONHIENTAI,TONMOI,NGAYNHAPKHO)" +
									" values('" + this.id + "','" +this.spId[i] + "','" + luongdieuchinh + "','"+giamua[i].replaceAll(",","")+"','"+this.ttien[i].replaceAll(",", "")+"',"+ tht +","  + tht + " + "+ luongdieuchinh + ",'"+this.ngaydc+"')";
									System.out.println("Command"+command);
									if(!this.db.update(command))
									{
										this.msg = command;
										geso.dms.center.util.Utility.rollback_throw_exception(this.db);
										return false;
									}							
							}
						}
					//}
				}	

				this.db.getConnection().commit();
				this.db.getConnection().setAutoCommit(true);

				/*this.db.getConnection().rollback();
				this.db.getConnection().setAutoCommit(true);*/
			}else
			{
				this.msg = "Ban khong the tao moi dieu chinh ton kho khi co 1 dieu chinh dang cho duyet";
				return false;
			}
		}catch(Exception e)
		{
			this.msg = "Error :" +e.getMessage();
			geso.dms.center.util.Utility.rollback_throw_exception(this.db);
			e.printStackTrace();
			return false;
		}
		finally
		{
			if(rs != null)
				try 
			{
					rs.close();
			} catch(Exception e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return true;
	}
	public boolean UpdateDctk(HttpServletRequest request)
	{
		getNppInfo();
		try
		{
	
			System.out.println("File in bean: "+this.file);
			this.db.getConnection().setAutoCommit(false);

			String query = "update dieuchinhtonkho set trangthai = 9, ngaygiosua = CONVERT(char(10),GETDATE(),121) + ' ' + CONVERT(CHAR( 5),GETDATE(),114) "
				+ "  where pk_seq ='" + this.id + "' and trangthai= 0 and nppDaChot = '0' ";
			System.out.println("quretret______"+query);
			if(this.db.updateReturnInt(query)!=1 )
			{
				this.msg ="Không thể cập nhật phiếu do trạng thái không hợp lệ ";
				geso.dms.center.util.Utility.rollback_throw_exception(this.db);
				return false;
			}

			this.ngaydc = request.getParameter("ngaydc");	
			this.gtdc 	= request.getParameter("ttien").replace(",", "");
			System.out.println("vào đây_____________-s");
			
			
			
			

			query = "update dieuchinhtonkho set ngaydc='" + this.ngaydc + "', nguoisua = '" + this.userId + "', tongtien='" + this.gtdc + "', lydodc = N'" + this.lydodc + "',filename = N'" + this.file + "' "
			+ "  where pk_seq ='" + this.id + "' and trangthai= 9 ";

			if(this.db.updateReturnInt(query)!=1 )
			{
				this.msg ="Không thể cập nhật phiếu,vui lòng reload lại phiếu, Lỗi dòng lệnh : "+query;
				geso.dms.center.util.Utility.rollback_throw_exception(this.db);
				return false;
			}
			
			
			String msgKS  = geso.dms.center.util.Utility.Check_Huy_NghiepVu_KhoaSo("dieuchinhtonkho", this.id, "ngaydc", db);
			if( msgKS.length() > 0)
			{
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
				this.msg = msgKS;
				return false;
			}
			

			geso.dms.center.util.Utility util = new geso.dms.center.util.Utility();
			//
			query=	  "	SELECT  dcsp.ngaynhapkho , DC.KHO_FK, DC.KBH_FK, DC.NPP_FK, SANPHAM_FK, (-1)*DCSP.DIEUCHINH AS DIEUCHINH, dc.ngaydc FROM DIEUCHINHTONKHO DC " +
			"	INNER JOIN DIEUCHINHTONKHO_SP DCSP ON DCSP.DIEUCHINHTONKHO_FK = DC.PK_SEQ  "
			+ "  WHERE DC.PK_SEQ = '"+ this.id +"' AND DCSP.DIEUCHINH < 0  and dc.trangthai = 9" ;


			ResultSet ckRs = db.get(query);
			while(ckRs.next())
			{
				String kho_fk=ckRs.getString("kho_fk");
				String nppId=ckRs.getString("npp_fk");	
				String kbh_fk=ckRs.getString("kbh_fk");
				String sanpham_fk=ckRs.getString("sanpham_fk");
				String ngaydc  =ckRs.getString("ngaydc");
				String ngaynhapkho  =ckRs.getString("ngaynhapkho");
				Double dieuchinh = ckRs.getDouble("dieuchinh");


				msg=util.Update_NPP_Kho(ngaydc,this.id, "CẬP NHẬT ĐIỀU CHỈNH TỒN KHO", db, kho_fk, sanpham_fk, nppId, kbh_fk,ngaynhapkho, 0.0,  (-1)*dieuchinh, dieuchinh,0, 0.0);
				if(msg.length()>0)
				{
					db.getConnection().rollback();
					return false;
				}			
			}
			//

			query = "delete from dieuchinhtonkho_sp where dieuchinhtonkho_fk='" + this.id + "'";
			if(	!this.db.update(query)){
				this.msg = "KHong The Update,Vui Long Kiem Tra Lai Du Lieu.Loi :"+query;
				geso.dms.center.util.Utility.rollback_throw_exception(this.db);
				return false;
			}
			for (int i = 0; i < Integer.parseInt(size) ; i++)
			{
				double tonm = Double.parseDouble(this.soluongle[i].replaceAll(",", ""));
				double tonht = Double.parseDouble(this.tkht[i].replaceAll(",", ""));
				double luongdieuchinh = tonm - tonht;
				/*if (dc[i].length()>0)
				{*/
					if (Math.abs(luongdieuchinh) > 0 )
					{
						//int dieuchinh_=0;
						//dieuchinh_= Integer.parseInt(this.dc[i].replaceAll(",", ""));
						double dieuchinh_ = 0;
						dieuchinh_ = luongdieuchinh;
 
						if(dieuchinh_ <0)
						{
							///x1
							//double soluongdenghi=Integer.parseInt(this.dc[i].replaceAll(",", "")) ;	
							double soluongdenghi = dieuchinh_;
							soluongdenghi =soluongdenghi*(-1);
							double soluongbandau = soluongdenghi;
							/////////////////////////////// ĐỀ XUẤT LÔ
/////////////////////////////// ĐỀ XUẤT LÔ
							query=  " select KHO_FK,SANPHAM_FK,KBH_FK,NGAYNHAPKHO,available,soluong" +
							"  ,(SELECT SUM(SOLUONG) FROM NHAPP_KHO t WHERE t.NPP_FK = ct.NPP_FK AND t.SANPHAM_FK = ct.SANPHAM_FK AND t.KBH_FK = ct.KBH_FK AND t.KHO_FK = ct.KHO_FK) sltong    " +
							" from NHAPP_KHO_CHITIET ct "+  
							" where NPP_FK ="+nppId+" and KBH_FK= " +this.kbhId +
							" and KHO_FK="+this.khoId+"  and SANPHAM_FK =  "+ this.spId[i] +
							" AND AVAILABLE >0  and NGAYNHAPKHO<='"+this.ngaydc+"'"+
							" order by NGAYNHAPKHO desc,AVAILABLE ";
							ResultSet rssp=db.get(query);
							int sosanh=0;

							while(rssp.next() && soluongdenghi >0){
								double soluong_avai= rssp.getDouble("AVAILABLE");
								double soluongcapnhat=0;
								System.out.println("__soluongdenghi: "+soluongdenghi);
								if(soluong_avai >soluongdenghi){
									soluongcapnhat= soluongdenghi;
									soluongdenghi=0;
								}else{
									soluongcapnhat =soluong_avai;
									soluongdenghi =soluongdenghi - soluong_avai;
								}
								sosanh+=soluongcapnhat;
								
								
								String _khoid=rssp.getString("kho_fk");
								String _kbhid=rssp.getString("KBH_FK");
								String ngaynhapkho=rssp.getString("NGAYNHAPKHO");
								String tonhientai=rssp.getString("sltong");
								double slct=  rssp.getDouble("soluong");
								String thanhtien = Double.toString(Double.parseDouble(giamua[i].replaceAll(",",""))*soluongcapnhat );
								String tonmoi = Double.toString( slct - soluongcapnhat );
								
								
								String command = 
									"if exists (select PK_SEQ from dieuchinhtonkho WHERE PK_SEQ = '"+ this.id+ "' AND TRANGTHAI = 9)"+
									"INSERT INTO dieuchinhtonkho_sp(DIEUCHINHTONKHO_FK,SANPHAM_FK,DIEUCHINH,GIAMUA,THANHTIEN,TONHIENTAI,TONMOI,NGAYNHAPKHO)" +
									" values('" + this.id + "','" +this.spId[i] + "','" + soluongcapnhat*(-1) + "','"+giamua[i].replaceAll(",","")+"','"+thanhtien+"','" + tonhientai+ "','" + tonmoi+ "','"+ngaynhapkho+"')";
								System.out.println("Command"+command);
								System.out.println("__soluongcapnhat: "+soluongdenghi);
								if(!this.db.update(command))
								{
									this.msg = command;
									geso.dms.center.util.Utility.rollback_throw_exception(this.db);
									return false;
								}

								msg=util.Update_NPP_Kho(ngaydc,this.id, "TẠO DIEUCHINHTONKHO", db, _khoid, this.spId[i] , this.nppId, _kbhid,ngaynhapkho, 0.0,  soluongcapnhat, (-1)*soluongcapnhat,0, 0.0);
								if(msg.length()>0)
								{
									geso.dms.center.util.Utility.rollback_throw_exception(this.db);
									return false;	
								}	
							}	
							//System.out.println("____soluongdenghi: "+soluongbandau);
							//System.out.println("____sosanh: "+sosanh);
							if(soluongbandau!=sosanh)
							{
								geso.dms.center.util.Utility.rollback_throw_exception(this.db);
								this.msg="Không đủ tồn để điều chỉnh. Mã sản phẩm: " +this.masp[i];
								return false;
							}
						}
						else
						{
							String tht = " ( SELECT SUM(SOLUONG) FROM NHAPP_KHO t WHERE t.NPP_FK = "+this.nppId+" AND t.SANPHAM_FK = "+this.spId[i]+" AND t.KBH_FK = "+this.kbhId+" AND t.KHO_FK = "+this.khoId+" )";
							
							String command = 
								"if exists (select PK_SEQ from dieuchinhtonkho WHERE PK_SEQ = '"+ this.id+ "' AND TRANGTHAI = 9)"+
								"INSERT INTO dieuchinhtonkho_sp(DIEUCHINHTONKHO_FK,SANPHAM_FK,DIEUCHINH,GIAMUA,THANHTIEN,TONHIENTAI,TONMOI,NGAYNHAPKHO)" +

								/*" values('" + this.id + "','" +this.spId[i] + "','" + this.dc[i].replaceAll(",", "") + "','"+giamua[i].replaceAll(",","")+"','"+this.ttien[i].replaceAll(",", "")+"','0','" + this.dc[i].replaceAll(",", "")+ "','"+this.ngaydc+"')";*/
								/*" values('" + this.id + "','" +this.spId[i] + "','" + luongdieuchinh + "','"+giamua[i].replaceAll(",","")+"','"+this.ttien[i].replaceAll(",", "")+"',"+ tht +"," + ( tht + luongdieuchinh ) + ",'"+this.ngaydc+"')";*/
								" values('" + this.id + "','" +this.spId[i] + "','" + luongdieuchinh + "','"+giamua[i].replaceAll(",","")+"','"+this.ttien[i].replaceAll(",", "")+"',"+ tht +"," + tht + " + "+ luongdieuchinh +",'"+this.ngaydc+"')";
								System.out.println("Command"+command);
								System.out.println("__soluongdenghi: "+ luongdieuchinh);
								if(!this.db.update(command))
								{
									this.msg = command;
									geso.dms.center.util.Utility.rollback_throw_exception(this.db);
									return false;
								}							
						}
					}
				//}
			}			
			query = "update dieuchinhtonkho set trangthai = 0, ngaydc='" + this.ngaydc + "', nguoisua = '" + this.userId + "', tongtien='" + this.gtdc + "', lydodc = N'" + this.lydodc + "',filename = N'" + this.file + "' "
			+ "  where pk_seq ='" + this.id + "' and trangthai= 9   and nppDaChot = '0'";

			if(this.db.updateReturnInt(query)!=1 )
			{
				this.msg ="Không thể cập nhật phiếu,vui lòng reload lại phiếu, Lỗi dòng lệnh : "+query;
				geso.dms.center.util.Utility.rollback_throw_exception(this.db);
				return false;
			}

			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		}catch(Exception e){
			this.msg = "Error :" +e.toString();
			geso.dms.center.util.Utility.rollback_throw_exception(this.db);
			return false;
		}	
		return true;
	}
	
	private void getNppInfo()
	{
		geso.dms.distributor.util.Utility util=new geso.dms.distributor.util.Utility();
		this.nppId=util.getIdNhapp(this.userId);
		this.nppTen=util.getTenNhaPP();
	}

	private String convertDate2(String date){
		String newdate = "";
		int year = Integer.valueOf(date.substring(0, 4)).intValue();
		int month = Integer.valueOf(date.substring(5, 7)).intValue();		
		int day = Integer.valueOf(date.substring(8, 10)).intValue();
		if (month == 1)
			newdate = "" + day + "-Jan-" + year;
		if (month == 2)
			newdate = "" + day + "-Feb-" + year;
		if (month == 3)
			newdate = "" + day + "-Mar-" + year;
		if (month == 4)
			newdate = "" + day + "-Apri-" + year;
		if (month == 5)
			newdate = "" + day + "-May-" + year;
		if (month == 6)
			newdate = "" + day + "-Jun-" + year;
		if (month == 7)
			newdate = "" + day + "-Jul-" + year;
		if (month == 8)
			newdate = "" + day + "-Aug-" + year;
		if (month == 9)
			newdate = "" + day + "-Sep-" + year;
		if (month == 10)
			newdate = "" + day + "-Oct-" + year;
		if (month == 11)
			newdate = "" + day + "-Nov-" + year;
		if (month == 12)
			newdate = "" + day + "-Dec-" + year;

		return newdate;	

	}

	private String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);	
	}

	public void DBclose(){


		try {
			if(this.dvkd != null)
				this.dvkd.close();
			if(this.kbh != null)
				this.kbh.close();
			if(this.kho != null)
				this.kho.close();
			if(this.ncc != null)
				this.ncc.close();
			if(!(this.db == null)){
				this.db.shutDown();
			}

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private boolean CheckNgayDieuChinh()
	{
		Utility util = new Utility();
		String ngayksgn = util.ngaykhoaso(this.nppId);

		//System.out.print("\nNgay khoa so gan nhat la: " + this.ngaychungtu + "\n");

		if(ngayksgn.equals(""))
			return false;

		String[] ngayks = ngayksgn.split("-");
		String[] ngayct = this.ngaydc.split("-");

		//System.out.print("\nNgay chung tu la: " + this.ngaychungtu + "\n");

		Calendar c1 = Calendar.getInstance(); //new GregorianCalendar();
		Calendar c2 = Calendar.getInstance(); //new GregorianCalendar();

		//NGAY KHOA SO

		c1.set(Integer.parseInt(ngayks[0]), Integer.parseInt(ngayks[1]) - 1, Integer.parseInt(ngayks[2]));

		//NGAY thuc hien tra hang
		c2.set(Integer.parseInt(ngayct[0]), Integer.parseInt(ngayct[1]) - 1, Integer.parseInt(ngayct[2]));

		c1.add(Calendar.DATE, 1);//ngay tra don hang bang ngay khoa so them 1 ngay
		//phep tinh ngay nhan hang - ngay khoa so

		long songay = ( c1.getTime().getTime() - c2.getTime().getTime()) / (24 * 3600 * 1000);

		if(songay < 0) //ngay chung tu khong duoc nho hon hoac bang ngay khoa so gan nhat 
		{
			this.msg = "Ngay Tra Don Hang Phai Lon Hon Ngay Khoa So Gan Nhat.";
			return false;
		}
		return true;
	}

	public String getLydodc() 
	{
		return this.lydodc;
	}

	public void setLydodc(String lydodc) 
	{
		this.lydodc = lydodc;
	}





	public String[] getSoluongle()
	{
		return soluongle;
	}

	public void setSoluongle(String[] soluongle)
	{
		this.soluongle = soluongle;
	}


	public String getSodong() {
		return this.sodong;
	}


	public void setSodong(String sodong) {
		this.sodong = sodong;
	}


	public String[] getFile() {

		return this.file.split(",");
	}


	public void setFile(String file) {
		this.file=file;

	}

	@Override
	public String[] getNgaynhapkho() {
		// TODO Auto-generated method stub
		return this.ngaynhapkho;
	}

	@Override
	public void setNgaynhapkho(String[] spNGAYNHAPKHO) {
		// TODO Auto-generated method stub
		this.ngaynhapkho=spNGAYNHAPKHO;
	}

}