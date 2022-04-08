package geso.dms.distributor.beans.dondathang.imp;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import geso.dms.center.util.SendMail;
import geso.dms.distributor.beans.dondathang.IDondathang;
import geso.dms.distributor.db.sql.dbutils;
import geso.dms.distributor.util.*;

import javax.servlet.http.HttpServletRequest;
public class Dondathang implements IDondathang, Serializable
{
	private static final long serialVersionUID = -9217977546733610214L;
	String userId;
	String nppId;
	String kho;
	String id;
	String ngaydh;
	String NgayDeNghiGH="";
	String soct;
	String nguoitao;
	String nguoisua;
	String nppTen;
	String nccId;
	ResultSet ncc;
	ResultSet dvkdIds;
	String dvkdId;
	ResultSet kbhIds;
	String kbhId;
	String tongtienaVAT;
	String vat;
	String tongtienbVAT;
	String dndhId;
	String LoaiDonhang;
	String DoiHang;
	String ThueSuat;
	
	String LyDoHuy;
	
	ResultSet cnList;
	
	String[] spId;
	String[] masp;
	String[] tensp;
	String[]denghi;
	String[] ton;
	String[] sl;
	String[] slduyet;
	String[] tspId;
	String[] tmasp;
	String[] ttensp;
	String[] tdenghi;
	String[] tton;
	String[] tsl;
	String[] ttsl;
	String[] tdongia;
	String[] ttienbVAT;
	String[] tdonvi;
	String[] tkhoiluong;
	String[] tthetich;
	
	String[] dongia;
	String[] qc;
	String[] dv1;
	String[] donvi;
	String[] tienbVAT;
	String[] scheme;
	
	String size;
	String msg;
	String maspstr;
	dbutils db;
	String contygiaohang="0";
	String GhiChu="";
	public Dondathang()
	{
		
		this.db = new dbutils();
		try{
			ResultSet	rs=this.db.get("select NGAYTONKHO,MUCTANGTRUONG,isnull(thue,0) as thue from CONGTHUCDNDH ");
			if(rs!=null){
				if(rs.next()){
					
					this.ThueSuat= (rs.getFloat("thue")/100)+"";
				}
			}
		rs.close();
		}catch(Exception er){
			
		}
		this.id = "";
		this.ngaydh = getDateTime();
		this.soct = "";
		this.nguoitao = "";
		this.nguoisua = "";
		this.nppTen = "";
		this.nccId = "";
		this.dvkdId = "";
		this.tongtienaVAT = "0";		
		this.vat = "0";
		this.tongtienbVAT = "0";
		this.dndhId = "0";
		this.size = "";
		this.msg="";
		this.maspstr = "";
		this.NgayDeNghiGH="";
		this.DoiHang="0";
		this.LoaiDonhang="0";
		this.contygiaohang="0";
		
	}
	public String getUserId()
	{
		return this.userId;
	}
	
	public void setUserId(String userId)
	{
		this.userId = userId;
		this.nguoitao = userId;
		this.nguoisua = userId;
	}
	
	public String getNppId()
	{
		return this.nppId;
	}
	
	public void setNppId(String id)
	{
		this.nppId = id;
	}

	public String getId()
	{
		return this.id;
	}
	
	public void setId(String id)
	{
		this.id = id;
	}

	public String getSize()
	{
		return this.size;
	}
	
	public void setSize(String size)
	{
		this.size = size;
	}
	
	
	public String getNgaydh()
	{
		return this.ngaydh;
	}
	
	public void setNgaydh(String ngaydh)
	{
		this.ngaydh = ngaydh;
	}
	
	public String getSoct()
	{
		return this.soct;
	}
	
	public void setSoct(String soct)
	{
		this.soct = soct;
	}
			
	public String getNguoitao()
	{
		return this.nguoitao;
	}
	
	public void setNguoitao(String nguoitao)
	{
		this.nguoitao = nguoitao;
	}
	
	public String getNguoisua()
	{
		return this.nguoisua;
	}
	
	public void setNguoisua(String nguoisua)
	{
		this.nguoisua = nguoisua;
	}
	
	public String getNppTen()
	{
		return this.nppTen;
	}
	
	public void setNppTen(String nppTen)
	{
		this.nppTen = nppTen;
	}
	
	public String getNccId()
	{
		return this.nccId;
	}
	
	public void setNccId(String nccId)
	{
		this.nccId = nccId;
	}

	public String getDvkdId()
	{
		return this.dvkdId;
	}
	
	public void setDvkdId(String dvkdId)
	{
		this.dvkdId = dvkdId;
	}

	public ResultSet getDvkdIds()
	{
		return this.dvkdIds;
	}
	
	public void setDvkdIds(ResultSet dvkdIds)
	{
		this.dvkdIds = dvkdIds;
	}

	public String getKbhId()
	{
		return this.kbhId;
	}
	
	public void setKbhId(String kbhId)
	{
		this.kbhId = kbhId;
	}


	public ResultSet getKbhIds()
	{
		return this.kbhIds;
	}
	
	public void setKbhIds(ResultSet kbhIds)
	{
		this.kbhIds = kbhIds;
	}
	
	public String getTongtienbVAT()
	{
		return this.tongtienbVAT;
	}

	public void setTongtienbVAT(String tongtienbVAT)
	{
		this.tongtienbVAT = tongtienbVAT;
	}
	
	public String getVat()
	{
		return this.vat;
	}
	
	public void setVat(String vat)
	{
		this.vat = vat;
	}
	
	public String getTongtienaVAT()
	{
		return this.tongtienaVAT;
	}
	
	public void setTongtienaVAT(String tongtienaVAT)
	{
		this.tongtienaVAT = tongtienaVAT;
	}
	
	public ResultSet getNcc()
	{
		return this.ncc;
	}
	
	public void setNcc(ResultSet ncc)
	{
		this.ncc = ncc;
	}
	
	public String[] getSpId()
	{
		return this.spId;
	}
	
	public void setSpId(String[] spId)
	{
		this.spId = spId;
	}

	public String[] gettSpId()
	{
		return this.tspId;
	}
	
	public void settSpId(String[] tspId)
	{
		this.tspId = tspId;
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
	
	public void setTensp(String[] tensp)
	{
		this.tensp = tensp;
	}
	
	public String[] gettTensp()
	{
		return this.ttensp;
	}
	
	public void settTensp(String[] ttensp)
	{
		this.ttensp =ttensp;
	}

	public String[] getDenghi()
	{
		return this.denghi;
	}
	
	public void setDenghi(String[] denghi)
	{
		this.denghi = denghi;
	}

	public String[] gettDenghi()
	{
		return this.tdenghi;
	}
	
	public void settDenghi(String[] tdenghi)
	{
		this.tdenghi = tdenghi;
	}

	public String[] getTonicp()
	{
		return this.ton;
	}
	
	public void setTonicp(String[] ton)
	{
		this.ton = ton;
	}
	
	public String[] gettTonicp()
	{
		return this.tton;
	}
	
	public void settTonicp(String[] tton)
	{
		this.tton = tton;
	}

	public Hashtable<String, String> getThieuMasp()
	{
		Hashtable<String, String > tmasp = new Hashtable();
		if (this.tmasp != null){
			for(int i = 0; i < this.tmasp.length; i++){
				if (this.tmasp[i] != null){
					tmasp.put(this.tmasp[i], "1");
				}
			}
		}
		return tmasp;
	}
	
	public String[] getThieuMaspArray()
	{
		return this.tmasp;
	}

	public void setThieuMasp(String[] tmasp)
	{
		this.tmasp = tmasp;
	}

	public String[] getQuycach()
	{
		return this.qc;
	}
	
	public void setQuycach(String[] qc)
	{
		this.qc = qc;
	}

	public String[] getDv1()
	{
		return this.dv1;
	}
	
	public void setDv1(String[] dv1)
	{
		this.dv1 = dv1;
	}

	public String[] getSl()
	{
		return this.sl;
	}
	
	public void setSl(String[] sl)
	{
		this.sl = sl;
	}

	public String[] gettSl()
	{
		return this.ttsl;
	}
	
	public void settSl(String[] ttsl)
	{
		this.ttsl = ttsl;
	}

	public String[] getDongia()
	{
		return this.dongia;
	}
	
	public void setDongia(String[] dongia)
	{
		this.dongia = dongia;
	}
	
	public String[] gettDongia()
	{
		return this.tdongia;
	}
	
	public void settDongia(String[] tdongia)
	{
		this.tdongia = tdongia;
	}

	public String[] getTienbVAT()
	{
		return this.tienbVAT;
	}
	
	public void setTienbVAT(String[] tienbVAT)
	{
		this.tienbVAT = tienbVAT;
	}

	public String[] gettTienbVAT()
	{
		return this.ttienbVAT;
	}
	
	public void settTienbVAT(String[] ttienbVAT)
	{
		this.ttienbVAT = ttienbVAT;
	}

	public String[] getDonvi()
	{
		return this.donvi;
	}
	
	public void setDonvi(String[] donvi)
	{
		this.donvi = donvi;
	}

	public String[] gettDonvi()
	{
		return this.tdonvi;
	}
	
	public void settDonvi(String[] tdonvi)
	{
		this.tdonvi = tdonvi;
	}

	public String getDndhId()
	{
		return this.dndhId;
	}
	
	public void setDndhId(String dndhId)
	{
		this.dndhId = dndhId;
	}

	public String getMessage() 
	{
		return this.msg;
	}
	
	public void setMessage(String msg) 
	{
		this.msg = msg;
	}

	public String getMaspstr() 
	{
		return this.maspstr;
	}
	
	public void setMaspstr(String maspstr) 
	{
		this.maspstr = maspstr;
	}

	// Tao moi
	public void init0(){
		getNppInfo();
		String query = "select d.pk_seq as nccId, d.ten as nccTen from nhaphanphoi a, nhacungcap_dvkd b, nhapp_nhacc_donvikd c, nhacungcap d where c.ncc_dvkd_fk = b.pk_seq and a.pk_seq = c.npp_fk and b.ncc_fk = d.pk_seq and a.pk_seq = '"+ this.nppId + "'";
		this.ncc = this.db.get(query);
		
		if(nccId.length()>0){
			query = "select e.pk_seq as dvkdId, e.donvikinhdoanh as dvkd from nhaphanphoi a, nhacungcap b, nhacungcap_dvkd c, nhapp_nhacc_donvikd d, donvikinhdoanh e where a.pk_seq = '"+ this.nppId + "' and b.pk_seq = '" + this.nccId + "' and a.pk_seq = d.npp_fk and b.pk_seq = c.ncc_fk and c.pk_seq = d.ncc_dvkd_fk and c.dvkd_fk = e.pk_seq";
			this.dvkdIds = this.db.get(query);
		}else{
			this.dvkdIds = this.db.get("select pk_seq as dvkdId, donvikinhdoanh as dvkd from donvikinhdoanh");
		}

		query = "select c.pk_seq as kbhId, c.diengiai as kbh from nhaphanphoi a, nhapp_kbh b, kenhbanhang c where a.pk_seq = '"+ this.nppId +"' and a.pk_seq = b.npp_fk and b.kbh_fk = c.pk_seq";
		this.kbhIds = this.db.get(query);			
	}
	
	public void createCongnoList()
	{
		String query = 
				"SELECT ROW_NUMBER() OVER (ORDER BY (SELECT 1)) AS STT, A.DIENGIAI, B.SOTIEN, " +
				"A.HINHTHUCTT, A.THOIGIAN FROM CONGNONPP A " +
				"INNER JOIN CONGNONPP_CT B ON B.CONGNONPP_FK = A.PK_SEQ " +
				"WHERE TRANGTHAI = '1' AND B.NPP_FK = '"+ this.nppId +"' ORDER BY A.THOIGIAN DESC ";
		System.out.println("cong no npp : "+query);
		this.cnList = this.db.get(query);
	}
	
	//truong hop xem lai 1 don dat hang co de nghi dat hang
	public void init1()
	{
		getNppInfo();
		Hashtable ht = new Hashtable<String, String>();
		String query; 	
		ResultSet rs = null;
		try{
			rs= this.db.get("select count(ma) as num from sanpham where trangthai='1'");
			rs.next();
			this.size = rs.getString("num");		
			int i = new Integer(this.size).intValue();
			this.spId = new String[i];
			this.masp = new String[i];
			this.denghi = new String[i];
			this.ton = new String[i];
			this.tensp = new String[i];
			this.sl = new String[i];
			this.qc = new String[i];
			this.dv1 = new String[i];
			this.dongia = new String[i];
			this.tienbVAT = new String[i];
			this.donvi = new String[i];
			this.tkhoiluong = new String[i];
			this.tthetich = new String[i];
			rs.close();
			
			query = "select isnull(ghichu,'') as ghichu , isnull(ctvanchuyen,'1') as vanchuyen ,isnull(loaidonhang,'0') as loaidonhang,isnull( doihang,'0')  as doihang ,ngaydenghigh,ngaydat, ncc_fk, dvkd_fk, kbh_fk, sotienbvat, vat, sotienavat, isnull(denghidathang_fk, '0') as dndhId from dondathang where pk_seq='"+ this.id + "'";
			System.out.println("Get Dat hang :"+query);
			String dndhId = "0";
			rs = this.db.get(query);
			rs.next();
			this.ngaydh = rs.getString("ngaydat");
			this.dvkdId = rs.getString("dvkd_fk");
			this.nccId = rs.getString("ncc_fk");
			this.kbhId = rs.getString("kbh_fk");
			this.tongtienbVAT = rs.getString("sotienbvat");
			this.vat = rs.getString("vat");
			this.tongtienaVAT = rs.getString("sotienavat");
			System.out.println("TONG TIEN A VAT : "+	this.tongtienaVAT );
			this.NgayDeNghiGH=rs.getString("ngaydenghigh");
			this.DoiHang=rs.getString("doihang");
			this.LoaiDonhang=rs.getString("loaidonhang");
			this.contygiaohang=rs.getString("vanchuyen");
			this.GhiChu=rs.getString("ghichu");
			
			dndhId = rs.getString("dndhId");
			rs.close();
			
			query = "";
			
		
			query=" select distinct a.sanpham_fk, b.ma ,b.trongluong as khoiluong,b.thetich, b.ten as tensp, "+
			" isnull(e.denghidat,0) as denghi, c.soluong1 as qc, d.donvi as dv1, a.SOLUONG as sl, "+
			" case when isnull(pbdh.soluong, 0) = 0 or isnull(pbdh.soluong, 0) > ISNULL(h.available,0)  "+
			" then ISNULL( h.available,0) else isnull(pbdh.soluong, 0) end as ton,  a.donvi,isnull(( BG.GIAMUANPP),0) as dongia,  "+
			" a.sotienbvat, a.vat, a.sotienavat, ISNULL(TON.TONKHO, 0) AS TONHT from dondathang_sp a inner join dondathang f on a.dondathang_fk = f.pk_seq "+
			" inner join sanpham b on a.sanpham_fk = b.pk_seq inner join quycach c on b.pk_seq = c.sanpham_fk  AND B.DVDL_FK=C.DVDL1_FK "+
			" inner join donvidoluong d on c.dvdl2_fk = d.pk_seq left join denghidathang_sp e "+ 
			" on e.sanpham_fk=a.sanpham_fk and e.denghidathang_fk = f.denghidathang_fk  " +
			" LEFT JOIN  "+
			" ( SELECT  B.KENH_FK,B.DVKD_FK ,D.SANPHAM_FK,D.GIAMUANPP FROM BANGGIAMUANPP B "+ 
			" INNER JOIN BANGGIAMUANPP_NPP C ON B.PK_SEQ=C.BANGGIAMUANPP_FK "+
			" INNER JOIN BGMUANPP_SANPHAM D ON B.PK_SEQ=D.BGMUANPP_FK "+
			" WHERE NPP_FK="+this.nppId+" and kenh_fk="+this.kbhId+" and dvkd_fk="+this.dvkdId+" AND D.TRANGTHAI=1  and D.GIAMUANPP >0"+
			" ) BG ON BG.SANPHAM_FK=b.PK_SEQ "+ 
			" LEFT join ERP_KHOTT_SANPHAM h "+
			" on b.PK_SEQ = h.SANPHAM_FK AND H.KHOTT_FK=(SELECT KHOSAP FROM NHAPHANPHOI WHERE PK_SEQ="+this.nppId+") "+
			" left join (   "+
			" select pb.kbh_fk as kbhId, pb.npp_fk as nppId, pb.sanpham_fk as spId, pb.soluong - "+ 
			" isnull((select sum(soluong) from dondathang_sp  "+
			" inner join dondathang ddh on ddh.pk_seq=dondathang_fk "+
			" where sanpham_fk=pb.sanpham_fk and pb.npp_fk=ddh.npp_fk and ngaydat >=pb.tungay "+ 
			" and ngaydat<=pb.denngay  ),0) as soluong  from phanbodathang pb "+  
			" inner join quycach qc on qc.sanpham_fk=pb.sanpham_fk "+  
			" where pb.tungay <= '"+this.ngaydh+"' and '"+this.ngaydh+"' <= pb.denngay   "+
			" )pbdh on pbdh.spId = b.pk_seq and pbdh.nppId = f.npp_fk and pbdh.kbhId = f.kbh_fk " +
			
			" LEFT JOIN " +
			"( " +
			"	SELECT SANPHAM_FK, AVAILABLE AS TONKHO FROM NHAPP_KHO " +
			"	WHERE NPP_FK = '" + this.nppId + "' AND KHO_FK = '100000' AND KBH_FK = '" + this.kbhId + "' " +
			") TON ON TON.SANPHAM_FK = B.PK_SEQ " +
			
			" where a.dondathang_fk='"+this.id+"' order by b.ma ";
			rs = this.db.get(query);				
			System.out.println("Query lay du lieu la: " + query);
			int m =0;

			while(rs.next()){
				this.spId[m] = rs.getString("sanpham_fk");			 
				this.masp[m] = rs.getString("ma");
				if (this.maspstr.length()==0){
					this.maspstr = "'" + this.masp[m] + "'";
				}else{
					this.maspstr = this.maspstr + ",'" + masp[m] + "'";
				}				
				this.tensp[m]= rs.getString("tensp");
				this.denghi[m] =""+ Math.round( Double.parseDouble( rs.getString("denghi")));
				
				this.qc[m] = rs.getString("qc");
				this.dv1[m]= rs.getString("dv1");
				this.sl[m] = "" + Math.round(Double.valueOf(rs.getString("sl")).doubleValue());///Double.valueOf(rs.getString("qc")).doubleValue());
				
				//this.ton[m]  = "" + (Math.round(Double.valueOf(rs.getString("ton")).doubleValue()));///Double.valueOf(rs.getString("qc")).doubleValue()) + Double.valueOf(sl[m]).doubleValue());
				this.ton[m]  = "" + (Math.round(Double.valueOf(rs.getString("TONHT")).doubleValue()));
				
				this.donvi[m] = rs.getString("donvi");
				
				this.dongia[m] = "" + Math.round(Double.valueOf(rs.getString("dongia")).doubleValue());//*Double.valueOf(rs.getString("qc")).doubleValue());
				
				this.tienbVAT[m] = rs.getString("sotienbvat");
				this.tkhoiluong[m] = rs.getString("khoiluong");
				this.tthetich[m] = rs.getString("thetich");
				m++;
			}
			this.size = "" + m;
			rs.close();
			
		
			query = "select pk_seq as nccId, ten as nccTen from nhacungcap";
			this.ncc = this.db.get(query);

			if(nccId.length()>0){
				query = "select e.pk_seq as dvkdId, e.donvikinhdoanh as dvkd from nhaphanphoi a, nhacungcap b, nhacungcap_dvkd c, nhapp_nhacc_donvikd d, donvikinhdoanh e where a.pk_seq = '"+ this.nppId + "' and b.pk_seq = '" + this.nccId + "' and a.pk_seq = d.npp_fk and b.pk_seq = c.ncc_fk and c.pk_seq = d.ncc_dvkd_fk and c.dvkd_fk = e.pk_seq";
				this.dvkdIds = this.db.get(query);
			}else{
				this.dvkdIds = db.get("select pk_seq as dvkdId, donvikinhdoanh as dvkd from donvikinhdoanh");
			}

			query = "select c.pk_seq as kbhId, c.diengiai as kbh from nhaphanphoi a, nhapp_kbh b, kenhbanhang c where a.pk_seq = '"+ this.nppId +"' and a.pk_seq = b.npp_fk and b.kbh_fk = c.pk_seq";
			this.kbhIds = db.get(query);
		
			createCongnoList();
			
		}catch(Exception e){
			System.out.println("Error Don Dat Hang .jav : "+e.toString());
		}
		finally{try {
			if(rs != null)
				rs.close();
		} catch (Exception e2) {
			
		}}
	}
	
	public void initDisplay(){
		getNppInfo();
		Hashtable ht = new Hashtable<String, String>();
		String query; 	
		ResultSet rs = null;
		try{
			rs= this.db.get("select count(a.sanpham_fk) as num from dondathang_sp a, sanpham b, quycach c, donvidoluong d where c.dvdl2_fk=100018 and a.sanpham_fk = b.pk_seq and a.dondathang_fk='"+ this.id +"' and b.pk_seq = c.sanpham_fk and c.dvdl1_fk = d.pk_seq and a.soluong > 0");
//			this.msg = "select count(a.sanpham_fk) as num from dondathang_sp a, sanpham b, quycach c, donvidoluong d where a.sanpham_fk = b.pk_seq and a.dondathang_fk='"+ this.id +"' and b.pk_seq = c.sanpham_fk and c.dvdl1_fk = d.pk_seq and a.soluong > 0";
			rs.next();
			this.size = rs.getString("num");		
			int i = new Integer(this.size).intValue();
			this.spId = new String[i];
			this.masp = new String[i];
			this.tensp = new String[i];
			this.sl = new String[i];
			this.qc = new String[i];
			this.dv1 = new String[i];
			this.dongia = new String[i];
			this.tienbVAT = new String[i];
			this.slduyet=new String[i];
			this.donvi = new String[i];
			this.scheme = new String[i];
			
			query = "select  isnull(ghichu,'') as ghichu,isnull(ctvanchuyen,'1') as vanchuyen , isnull(lydohuy,'') as lydohuy,isnull(doihang,'0') as doihang,isnull(loaidonhang,'0') as loaidonhang ,ngaydenghigh,ngaydat, ncc_fk, dvkd_fk, kbh_fk, sotienbvat, vat, sotienavat from dondathang where pk_seq='"+ this.id + "'";
			System.out.println("Soddiak 11 : "+query);
			rs = this.db.get(query);
			rs.next();
			this.NgayDeNghiGH=rs.getString("ngaydenghigh");
			this.LyDoHuy=rs.getString("lydohuy");
			
			
			this.ngaydh = rs.getString("ngaydat");
			this.dvkdId = rs.getString("dvkd_fk");
			this.nccId = rs.getString("ncc_fk");
			this.kbhId = rs.getString("kbh_fk");
			this.tongtienbVAT = rs.getString("sotienbvat");
			this.vat = rs.getString("vat");
			this.tongtienaVAT = rs.getString("sotienavat");
			this.DoiHang=rs.getString("doihang");
			this.LoaiDonhang=rs.getString("loaidonhang");
			this.contygiaohang=rs.getString("vanchuyen");
			this.GhiChu=rs.getString("ghichu");
			
			query ="select a.sanpham_fk, b.ma, b.ten as tensp, c.soluong1 as qc, d.donvi as dv1,isnull(a.soluongduyet,0) as soluongduyet , a.soluong as sl, a.donvi, a.dongia as dongia, a.sotienbvat, a.vat, a.sotienavat, a.scheme from dondathang_sp a, sanpham b, quycach c, donvidoluong d where c.dvdl2_fk=100018  and  a.sanpham_fk = b.pk_seq and a.dondathang_fk='"+ this.id +"' and b.pk_seq = c.sanpham_fk and c.dvdl1_fk = d.pk_seq and a.soluong > 0 order by b.ma";
			System.out.println("Soddiak : "+query);
			rs = this.db.get(query);				
			int m =0;
//			this.msg = query;
			while(rs.next()){
				this.spId[m] = rs.getString("sanpham_fk");			 
				this.masp[m] = rs.getString("ma");
				if (this.maspstr.length()==0){
					this.maspstr = "'" + this.masp[m] + "'";
				}else{
					this.maspstr = this.maspstr + ",'" + masp[m] + "'";
				}				
				this.tensp[m]= rs.getString("tensp");
				this.qc[m] = rs.getString("qc");
				this.dv1[m]= rs.getString("dv1");

				this.sl[m] = "" + Math.round(Double.valueOf(rs.getString("sl")).doubleValue()); 

				this.donvi[m] = rs.getString("donvi");

				this.dongia[m] = "" + Math.round(Double.valueOf(rs.getString("dongia")).doubleValue());//Double.valueOf(rs.getString("qc")).doubleValue());

				this.tienbVAT[m] = rs.getString("sotienbvat");
				this.slduyet[m]=rs.getString("soluongduyet");
				this.scheme[m] = rs.getString("scheme");
				
				m++;
			}
			this.size = "" + m;

			query = "select pk_seq as nccId, ten as nccTen from nhacungcap";
			this.ncc = this.db.get(query);

			if(nccId.length()>0){
				query = "select e.pk_seq as dvkdId, e.donvikinhdoanh as dvkd from nhaphanphoi a, nhacungcap b, nhacungcap_dvkd c, nhapp_nhacc_donvikd d, donvikinhdoanh e where a.pk_seq = '"+ this.nppId + "' and b.pk_seq = '" + this.nccId + "' and a.pk_seq = d.npp_fk and b.pk_seq = c.ncc_fk and c.pk_seq = d.ncc_dvkd_fk and c.dvkd_fk = e.pk_seq";
				this.dvkdIds = this.db.get(query);
			}else{
				this.dvkdIds = db.get("select pk_seq as dvkdId, donvikinhdoanh as dvkd from donvikinhdoanh");
			}
//			this.msg = query;
			query = "select c.pk_seq as kbhId, c.diengiai as kbh from nhaphanphoi a, nhapp_kbh b, kenhbanhang c where a.pk_seq = '"+ this.nppId +"' and a.pk_seq = b.npp_fk and b.kbh_fk = c.pk_seq";
			this.kbhIds = db.get(query);
		
			createCongnoList();
			
		}catch(Exception e){
			System.out.println("Errrorr : "+e.toString());
		}
		finally{try {
			if(rs != null)
				rs.close();
		} catch (Exception e2) {
			
		}}
	}

	public void initSelectboxData(){
		getNppInfo();
		String query = "select ncc_fk, dvkd_fk, kbh_fk from denghidathang where pk_seq = '"+this.dndhId+"'";
		ResultSet rs = this.db.get(query);
		try{
			rs.next();
			this.dvkdId = rs.getString("dvkd_fk");
			this.nccId = rs.getString("ncc_fk");
			this.kbhId = rs.getString("kbh_fk");
			rs.close();
		}catch(Exception e){}
		finally{try {
			if(rs != null) rs.close();
		} catch (Exception e2) {
			
		}}
		
		query = "select pk_seq as nccId, ten as nccTen from nhacungcap";
		this.ncc = this.db.get(query);

		if(this.nccId.length()>0){
			query = "select e.pk_seq as dvkdId, e.donvikinhdoanh as dvkd from nhaphanphoi a, nhacungcap b, nhacungcap_dvkd c, nhapp_nhacc_donvikd d, donvikinhdoanh e where a.pk_seq = '"+ this.nppId + "' and b.pk_seq = '" + this.nccId + "' and a.pk_seq = d.npp_fk and b.pk_seq = c.ncc_fk and c.pk_seq = d.ncc_dvkd_fk and c.dvkd_fk = e.pk_seq";
			this.dvkdIds = this.db.get(query);
		}else{
			this.dvkdIds = db.get("select pk_seq as dvkdId, donvikinhdoanh as dvkd from donvikinhdoanh");
		}
//		this.msg = query;
		query = "select c.pk_seq as kbhId, c.diengiai as kbh from nhaphanphoi a, nhapp_kbh b, kenhbanhang c where a.pk_seq = '"+ this.nppId +"' and a.pk_seq = b.npp_fk and b.kbh_fk = c.pk_seq";
		this.kbhIds = db.get(query);
		
	}
	// Chuyen tu PR sang PO
	public void init2(){
		System.out.println("vo init2");
		getNppInfo();
		Hashtable ht = new Hashtable<String, String>();
		String query; 

		ResultSet rs = null;
		try{

			rs= this.db.get("select count(ma) as num from sanpham where trangthai='1'");
			rs.next();
			this.size = rs.getString("num");		
			int i = new Integer(this.size).intValue();
			this.spId = new String[i];
			this.masp = new String[i];
			this.tensp = new String[i];
			this.denghi = new String[i];
			this.ton   = new String[i];
			this.sl = new String[i];
			this.qc = new String[i];
			this.dongia = new String[i];
			this.tienbVAT = new String[i];
			this.donvi = new String[i];
			rs.close();
			
			query = "select ncc_fk, dvkd_fk, kbh_fk from denghidathang where pk_seq = '"+this.dndhId+"'";
			System.out.println("de nghi: "+ query);
			rs = this.db.get(query);
			if(rs.next()){
				this.dvkdId = rs.getString("dvkd_fk");
				this.nccId = rs.getString("ncc_fk");
				this.kbhId = rs.getString("kbh_fk");
			}
			rs.close();
			
			query ="select distinct sanpham_fk, denghidat, dadathang, dongia from denghidathang_sp where denghidathang_fk='"+ this.dndhId +"' and dongia >0";
			rs = this.db.get(query);
			System.out.println("s: kj: "+ query);
			
			while(rs.next()){
				double tmp = Double.valueOf(rs.getString("dadathang")).doubleValue()/Double.valueOf(rs.getString("dongia")).doubleValue(); 
				tmp = Double.valueOf(rs.getString("denghidat")).doubleValue() - tmp;
				if (tmp < 0)
					tmp = 0;
				String sl = "" + Math.round(tmp);
				ht.put(rs.getString("sanpham_fk"), sl);
			}
			rs.close();
			
			maspstr = "";
			this.tongtienbVAT = this.tongtienbVAT.replace(",", "");
			this.vat = this.vat.replace(",", "");
			this.tongtienaVAT = this.tongtienaVAT.replace(",", "");

	
			
			String t="select distinct a.pk_seq as id, a.ma as ma, a.ten as ten, isnull(bg.giamuanpp,0)  as dongia, c.soluong1 as qc,  "+
				"	 d.donvi as dv,    "+
				"	 ISNULL(TON.TONKHO, 0) AS TONHT " +
				" 	from sanpham a  "+ 
				"	inner join quycach c on a.pk_seq= c.sanpham_fk and c.dvdl1_fk=a.dvdl_fk  "+
				"	inner join donvidoluong d   "+
				"	on a.dvdl_fk = d.pk_seq left join ERP_KHOTT_SANPHAM e on e.SANPHAM_FK = a.PK_SEQ  "+ 
				"	INNER JOIN   "+  
				"	(   "+
				"	SELECT  B.KENH_FK,B.DVKD_FK ,D.SANPHAM_FK,D.GIAMUANPP FROM BANGGIAMUANPP B   "+ 
				"	INNER JOIN BANGGIAMUANPP_NPP C ON B.PK_SEQ=C.BANGGIAMUANPP_FK   "+
				"	INNER JOIN BGMUANPP_SANPHAM D ON B.PK_SEQ=D.BGMUANPP_FK   "+
				"	WHERE NPP_FK="+this.nppId+" and kenh_fk="+this.kbhId+" and dvkd_fk="+this.dvkdId+" AND D.TRANGTHAI=1  and D.GIAMUANPP >0  "+
				"	) BG ON BG.SANPHAM_FK=a.PK_SEQ  "+ 
				"	left join (   "+
				"	select pb.kbh_fk as kbhId, pb.npp_fk as nppId, pb.sanpham_fk as spId, pb.soluong from phanbodathang pb  "+ 
				"	where pb.tungay < '"+ this.ngaydh +"' and '"+ this.ngaydh +"' < pb.denngay and  "+
				"	pb.npp_fk=  "+this.nppId +
				"	)pbdh on pbdh.spId = a.pk_seq  "+
				"	and pbdh.kbhId = bg.kenh_fk  " +
				
				" LEFT JOIN " +
				"( " +
				"	SELECT SANPHAM_FK, AVAILABLE AS TONKHO FROM NHAPP_KHO " +
				"	WHERE NPP_FK = '" + this.nppId + "' AND KHO_FK = '100000' AND KBH_FK = '" + this.kbhId + "' " +
				") TON ON TON.SANPHAM_FK = A.PK_SEQ " +
				
				"where a.trangthai='1'  order by a.ma ";
			
			System.out.println("query lay ton: "+t);
			
			
			rs= this.db.get(t);
			
			i = 0;		
			double tmpbvat = 0;
			double tmpvat = 0;
			double tmpavat = 0;
			
			while(rs.next()){
				this.spId[i] = rs.getString("id");
				this.masp[i] = rs.getString("ma");
				if (this.maspstr.length()==0){
					this.maspstr = "'" + this.masp[i] + "'";
				}else{
					this.maspstr = this.maspstr + ",'" + masp[i] + "'";
				}
			
				this.tensp[i] = rs.getString("ten");
				
				this.qc[i] = rs.getString("qc");
				//this.ton[i]   = "" + Math.round((Double.valueOf(rs.getString("tonicp").trim()).doubleValue())); ///Double.valueOf(this.qc[i].trim()).doubleValue()));
				this.ton[i]   = "" + Math.round((Double.valueOf(rs.getString("TONHT").trim()).doubleValue()));
				
				if (Double.valueOf(this.ton[i].trim()).doubleValue() < 0){
					this.ton[i] = "0";
				}
				
				if(ht.containsKey(spId[i])){
					this.denghi[i] =""+ Math.round(Double.parseDouble((String)ht.get(this.spId[i]))) ;
				}else{
					this.denghi[i]	 = "0";
				}
				
				
//				this.denghi[i] = sl[i];
				
				this.sl[i]	 = "0";
//				if (Double.valueOf(sl[i]).doubleValue()> Double.valueOf(ton[i]).doubleValue()){
//					sl[i] = ton[i];
//				}
				
//				this.sl[i] = "" + Double.valueOf(this.sl[i]).doubleValue(); 
				this.dongia[i]	 = "" + Math.round((Double.valueOf(rs.getString("dongia")).doubleValue()));//*Double.valueOf(this.qc[i]).doubleValue());

				if (this.sl[i].length()>0){
					Double tmp = (Double.valueOf(this.sl[i]).doubleValue())*Math.round((Double.valueOf(this.dongia[i]).doubleValue()));
					this.tienbVAT[i] = "" + tmp;
					tmpbvat = tmpbvat + Math.round(Double.valueOf(this.tienbVAT[i]).doubleValue()) ;
				
				}else{
					this.tienbVAT[i] = "0";
				}
				
				this.donvi[i] = rs.getString("dv");
				i++;
			}
			rs.close();
			
			this.size = "" + i;	
			this.tongtienbVAT = "" + tmpbvat; 
			tmpvat = Math.round(tmpbvat*Float.parseFloat(this.ThueSuat));
			this.vat = "" + tmpvat;
			tmpavat = Math.round(tmpbvat)+ Math.round(tmpbvat*Float.parseFloat(this.ThueSuat));	
			this.tongtienaVAT = "" + tmpavat;
			
			query = "select pk_seq as nccId, ten as nccTen from nhacungcap";
			this.ncc = this.db.get(query);
		
			query = "select pk_seq as dvkdId, donvikinhdoanh as dvkd from donvikinhdoanh";
			this.dvkdIds = this.db.get(query);
			
			query = "select pk_seq as kbhId, diengiai as kbh from kenhbanhang";
			this.kbhIds = this.db.get(query);

			createCongnoList();
			
		}catch(Exception e){
			this.msg="Loi : "+e.toString();
			
			System.out.println("Loi : "+e.toString());
		}
		finally{try {
			if(rs != null) rs.close();
		} catch (Exception e2) {
			
		}}
	}

	// Tao Dondathang khong qua Denghidathang
	public void init3(){
		getNppInfo();
		Hashtable ht = new Hashtable<String, String>();
		String query; 

		ResultSet rs = null;
		try{

			rs= this.db.get("select count(ma) as num from sanpham where trangthai='1'");
			rs.next();
			this.size = rs.getString("num");		
			int i = new Integer(this.size).intValue();
			this.spId = new String[i];
			this.masp = new String[i];
			this.tensp = new String[i];
			this.denghi = new String[i];
			this.ton   = new String[i];
			this.sl = new String[i];
			this.qc = new String[i];
			this.dongia = new String[i];
			this.tienbVAT = new String[i];
			this.donvi = new String[i];
			rs.close();
		
			
			String sql=" SELECT SP.PK_SEQ,SP.TEN,SP.MA,QC.SOLUONG2 AS SOLUONGCHAN,QC.SOLUONG1 AS SOLUONGLE,DVDL1_FK AS DVDLCHAN "+ 
				" , ISNULL(BG.GIAMUANPP,0)  AS DONGIA,DVDL.DONVI AS DONVICHAN,ISNULL(KHO.SOLUONG,0) AS TONKHOCHAN, ISNULL(TON.TONKHO, 0) AS TONHT "+
				" FROM SANPHAM SP  "+
				" INNER JOIN  "+
				" QUYCACH QC ON QC.SANPHAM_FK=SP.PK_SEQ AND QC.DVDL1_FK=SP.DVDL_FK "+
				" INNER JOIN DONVIDOLUONG DVDL ON DVDL.PK_SEQ=QC.DVDL1_FK "+
				" INNER  JOIN   "+
				" ( SELECT  B.KENH_FK,B.DVKD_FK ,D.SANPHAM_FK,D.GIAMUANPP FROM BANGGIAMUANPP B "+ 
				" INNER JOIN BANGGIAMUANPP_NPP C ON B.PK_SEQ=C.BANGGIAMUANPP_FK "+
				" INNER JOIN BGMUANPP_SANPHAM D ON B.PK_SEQ=D.BGMUANPP_FK "+
				" WHERE D.GIAMUANPP >0 AND  NPP_FK="+this.nppId+" AND D.TRANGTHAI=1  and B.KENH_FK="+this.kbhId+" AND B.DVKD_FK= "+ this.dvkdId +
				"  ) BG ON BG.SANPHAM_FK=SP.PK_SEQ "+
				" LEFT JOIN ERP_KHOTT_SANPHAM KHO ON KHO.SANPHAM_FK=SP.PK_SEQ "+ 
				" AND KHOTT_FK=(SELECT KHOSAP FROM NHAPHANPHOI NPP WHERE PK_SEQ="+this.nppId+")  " +
				" LEFT JOIN " +
				"( " +
				"	SELECT SANPHAM_FK, AVAILABLE AS TONKHO FROM NHAPP_KHO " +
				"	WHERE NPP_FK = '" + this.nppId + "' AND KHO_FK = '100000' AND KBH_FK = '" + this.kbhId + "' " +
				") TON ON TON.SANPHAM_FK = SP.PK_SEQ " +
				"ORDER by SP.MA";
			
			System.out.println("ton: "+sql);
			rs= this.db.get(sql);
			
			i = 0;		
			double tmpbvat = 0;
			double tmpvat = 0;
			double tmpavat = 0;
			
			while(rs.next()){
				this.spId[i] = rs.getString("pk_seq");
				this.masp[i] = rs.getString("ma");
				if (this.maspstr.length()==0){
					this.maspstr = "'" + this.masp[i] + "'";
				}else{
					this.maspstr = this.maspstr + ",'" + masp[i] + "'";
				}
			
				this.tensp[i] = rs.getString("ten");
				
				this.qc[i] = rs.getString("SOLUONGLE");
				//this.ton[i]   = "" + Math.round(Double.valueOf(rs.getString("tonkhochan").trim()).doubleValue());//Double.valueOf(this.qc[i].trim()).doubleValue()));
				this.ton[i]   = "" + Math.round(Double.valueOf(rs.getString("TONHT").trim()).doubleValue());
				
				System.out.println("Ton Kho : " + ton[i]);
				if (Double.valueOf(this.ton[i].trim()).doubleValue() < 0){
					this.ton[i] = "0";
				}
				
				this.denghi[i]	 = "0";
				
				
				this.sl[i]	 = "0";

				this.dongia[i]	 = "" + Math.round((Double.valueOf(rs.getString("dongia")).doubleValue()));//*Double.valueOf(this.qc[i]).doubleValue());

				if (this.sl[i].length()>0){
					Double tmp = (Double.valueOf(this.sl[i]).doubleValue())*Math.round((Double.valueOf(this.dongia[i]).doubleValue()));
					this.tienbVAT[i] = "" + tmp;
					tmpbvat = tmpbvat + Math.round(Double.valueOf(this.tienbVAT[i]).doubleValue()) ;
				
				}else{
					this.tienbVAT[i] = "0";
				}
				
				this.donvi[i] = rs.getString("DONVICHAN");
				i++;
			}
			rs.close();
			
			this.size = "" + i;	
			this.tongtienbVAT = "0"; 		
			this.vat = "0" ;
			this.tongtienaVAT = "0";
			
			query = "select pk_seq as nccId, ten as nccTen from nhacungcap";
			this.ncc = this.db.get(query);
		
			query = "select pk_seq as dvkdId, donvikinhdoanh as dvkd from donvikinhdoanh";
			this.dvkdIds = this.db.get(query);
			
			query = "select pk_seq as kbhId, diengiai as kbh from kenhbanhang";
			this.kbhIds = this.db.get(query);

			createCongnoList();
		}catch(Exception e){}
		finally{try {
			if(rs != null) rs.close();
		} catch (Exception e2) {
			
		}}
	}

	private Hashtable Quycach(){
		String query = "select distinct a.ma, b.soluong1 from sanpham a, quycach b where a.pk_seq = b.sanpham_fk and b.dvdl2_fk=100018 order by a.ma";
		ResultSet rs = this.db.get(query);
		Hashtable h = new Hashtable();
		try{
			while(rs.next()){
				h.put(rs.getString("ma"), rs.getString("soluong1"));
			}
		}catch(Exception e){}
		return h;
	}
	
	public boolean CreateDdhnpp(HttpServletRequest request) {
		
		getNppInfo();
		Utility util = new Utility();
		Hashtable h = Quycach();
		ResultSet rs = null;
		String query;
		try{
			
			if(this.LoaiDonhang.equals("1")){
				this.ThueSuat="0";
			}
			this.tongtienbVAT = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tongtienbvat"));	
			this.vat = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vat"));
			this.tongtienaVAT = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tongtienavat"));

			this.tongtienbVAT = this.tongtienbVAT.replace(",", "");
			this.vat = this.vat.replace(",", "");
			this.tongtienaVAT = this.tongtienaVAT.replace(",", "");
//			String date = convertDate();
			String gsbh = "null"; //db.get("select top(1) a.gsbh_fk from giamsatbanhang gsbh inner join nhapp_giamsatbh a on gsbh.pk_seq = a.gsbh_fk inner join nhapp_kbh b on a.npp_fk = b.npp_fk where a.npp_fk = '"+this.nppId+"' and b.kbh_fk='"+this.kbhId+"' and gsbh.dvkd_fk = '"+this.dvkdId+"'").getString("gsbh_fk");
			String asm = "null";//db.get("select top(1) pk_seq from  asm").getString("pk_seq");
			String bm = "null";//db.get("select top(1) pk_seq from  bm").getString("pk_seq");

			double tmpbvat = 0;
			double tmpvat = 0;
			double tmpavat = 0;
			double total = 0;
			
			this.masp = request.getParameterValues("masp");
			this.spId = request.getParameterValues("spId");	
			
			this.size = "" + this.masp.length;
			int size = this.masp.length;
			this.tspId = new String[size];
			
			this.denghi = request.getParameterValues("denghi");
			this.tdenghi = new String[size];
			
			this.tensp = request.getParameterValues("tensp");
			this.ttensp = new String[size];
			
			this.ton = new String[size];
			this.tton = new String[size];
			
			this.sl = new String[size];
			
			this.tmasp = new String[size];
			this.tsl = new String[size];
			this.ttsl = new String[size];
			
			this.dongia = new String[size];
			this.tdongia = new String[size];
			
			this.tienbVAT = new String[size];
			this.ttienbVAT = new String[size];
			
			this.donvi = new String[size];
			this.tdonvi = new String[size];
			this.qc=new String [size];
			boolean checkTon = true;  
			int n = 0;
			this.maspstr = "";
			
			for (int i = 0; i < size ; i++){
				if (this.maspstr.length()==0){
					this.maspstr = "'" + this.masp[i] + "'";
				}else{
					this.maspstr = this.maspstr + ",'" + masp[i] + "'";
				}
				System.out.println("qc" + this.masp[i]);
				
				this.qc[i]=request.getParameter("qc" + this.masp[i]);
				
				System.out.println("Quy cach : "+this.qc[i]);
				
				this.sl[i] =request.getParameter("sl" + this.masp[i]);
				this.ton[i] = request.getParameter("ton" + this.masp[i]);
				
			
				this.donvi[i] = request.getParameter("dv" + this.masp[i]);
				
				this.dongia[i] =request.getParameter("dg" + this.masp[i]);
				
				if (this.dongia[i].length()==0){
					this.dongia[i] = "0";
				}else{
					this.dongia[i] = this.dongia[i].replace(",", "");
				}
				
				this.tienbVAT[i] = request.getParameter("t" + this.masp[i]);
				this.tienbVAT[i] = this.tienbVAT[i].replace(",", "");
				this.sl[i]=this.sl[i].replaceAll(",", "");
				if (this.sl[i].length()==0)
					this.sl[i] = "0";
				
			}
			
			db.getConnection().setAutoCommit(false);
		
			query = "insert into dondathang (ghichu,CTVANCHUYEN,doihang,loaidonhang,ngaydenghigh,ngaydat,sotienbvat,nguoitao,nguoisua,trangthai,npp_fk,ncc_fk,vat,sotienavat,dvkd_fk,denghidathang_fk,kbh_fk,soid,chietkhau,gsbh_fk,asm,bm,tinhtrang,iskm, khott_fk) " +
					" values(N'"+this.GhiChu+"','"+this.contygiaohang+"','"+this.DoiHang+"','"+this.LoaiDonhang+"','"+this.NgayDeNghiGH+"','" + this.ngaydh + "', '" + this.tongtienbVAT + "','" + this.nguoitao + "','" + this.nguoisua + "','0'," +
							"  '" + this.nppId + "','"+this.nccId+"','" + this.vat + "','" + this.tongtienaVAT +"' " +
									",'" + this.dvkdId + "','" + this.dndhId + "','" + this.kbhId + "', '0', '0', "+gsbh+","+asm+", " + bm + ", '0','0','"+this.kho+"')";//ncc: 100046
			System.out.print("Cau Lenh Inser  : "+query);
			
			if(!this.db.update(query)){
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				this.msg = "Loi:" +query;
				return false;
			}

			query = "select IDENT_CURRENT('dondathang') as ddhId";
			System.out.println("cau select: "+query);
			rs = this.db.get(query);		
			try{
			rs.next();
			this.id = rs.getString("ddhId");
			System.out.println("Id don dat hang la: " + this.id + "\n");
			}catch(Exception er){}
			
			
			String slg;
			String dg;
			String tienbvat = "0";
			String vat = "0";
			String tienavat = "0";
			System.out.println("2.Chen san pham vao ddh");
			
			for (int i = 0; i < this.masp.length ; i++)
			{
				
						System.out.println("this.sl[i]: "+this.sl[i]);
						System.out.println("this.ton[i]: "+this.ton[i]);
						System.out.println("this.dongia[i]: "+this.dongia[i]);
					
						double quycach=0;
						try{
							 quycach=Double.parseDouble(qc[i]);
						}catch(Exception er){
							
						}
							slg = "" + Math.round((Double.valueOf(this.sl[i]).doubleValue())); //* Double.valueOf((String)h.get(this.masp[i])).doubleValue());
							
							dg = "" + Math.round(Double.valueOf(this.dongia[i]).doubleValue());/// Double.valueOf((String)h.get(this.masp[i])).doubleValue());
							//System.out.println("t1: "+this.sl[i]);			
							tienbvat = "" + Double.valueOf(slg).doubleValue()*Double.valueOf(dg).doubleValue();
							//System.out.println("t2]: "+this.sl[i]);
							vat = "" + Math.round(Double.valueOf(tienbvat).doubleValue()* Float.parseFloat(this.ThueSuat));
							//System.out.println("vat : "+vat);
							//System.out.println("tmpbvat : "+tmpbvat);
							tmpbvat = tmpbvat + (Double.valueOf(tienbvat).doubleValue());				
							
							//System.out.println("tmpvat : "+tmpvat);
							
							tmpvat = tmpvat + Math.round(Double.valueOf(tienbvat).doubleValue()* Float.parseFloat(this.ThueSuat));
							
							//System.out.println("tienbvat : "+tienbvat);
							
							tienavat = "" + ( Math.round(Double.valueOf(tienbvat).doubleValue())+Math.round(Double.valueOf(tienbvat).doubleValue()* Float.parseFloat(this.ThueSuat)));
							//System.out.println("tienavat : "+tienavat);
							
							tmpavat = tmpavat + Math.round(Double.valueOf(tienavat).doubleValue());
							//System.out.println("tmpavat : "+tmpavat);
							
							
							query = "insert into dondathang_sp (dondathang_fk,sanpham_fk,soluong,soluongduyet,donvi,dongia,sotienbvat,vat,sotienavat,khott,scheme) " +
									" values('" + this.id + "','" + this.spId[i] + "','" + slg + "','"+slg+"',N'"+donvi[i]+"','" + dg + "','" + tienbvat + "','" + vat +"','" + tienavat + "','"+this.kho+"','')";		
							System.out.println("query2: "+query);

							//}
						
							System.out.println("\nsQL: "+ query + "row: "+ i);
							
							if(!this.db.update(query)){
								this.msg = query;
								System.out.println("sQL: "+ query + "row: "+ i);
								geso.dms.center.util.Utility.rollback_throw_exception(this.db);
								return false;
									
							}

							query = "update denghidathang_sp set dadathang ='" + tienbvat + "' where sanpham_fk='" + this.spId[i] + "' and denghidathang_fk='" + dndhId + "'";
							if(this.db.update(query)){
								total = total + Double.valueOf(tienbvat).doubleValue();
							}
							System.out.println("sQL2: "+ query + "row: "+ i);
				

			}
		 query = "update dondathang set "+(this.LoaiDonhang.equals("1")?"ISKM=1":"ISKM=0")+", sotienbvat='" + tmpbvat + "', vat = '" + tmpvat + "', sotienavat ='" + tmpavat + "' where pk_seq ='" + this.id + "'";
		 if(!this.db.update(query)){
			 this.msg = query;
				System.out.println("sQL: "+ query  );
				geso.dms.center.util.Utility.rollback_throw_exception(this.db);
				return false;
				
		 }
					
		 if (this.dndhId.length()>0){
			query = "update denghidathang set trangthai='2', dadathang= cast(dadathang as float) + " +total + " where pk_seq = '" + this.dndhId + "'";
			 if(!this.db.update(query)){
				 this.msg = query;
				 geso.dms.center.util.Utility.rollback_throw_exception(this.db);
					System.out.println("sQL: "+ query  );
					return false;
			 }
		 }

		System.out.println("Error query" + query);				
		db.getConnection().commit();
		db.getConnection().setAutoCommit(true);		
				
		/*if(!checkTon){
			return false;
		}else{
			return true;
		}*/
		return true;
		
		}catch(Exception  e){
			System.out.println("Println ErroerR: "+e.toString());
			geso.dms.center.util.Utility.rollback_throw_exception(db);
		
			try {
				if(rs != null)
					rs.close();
			} catch (Exception e2) {
				
				System.out.println("Println ErroerR: "+e2.toString());
			}
			return false;	
		}
		
		
	}
	
	public boolean ChotDdhnpp(HttpServletRequest request)
	{
		if (UpdateDdhnpp(request))
		{
			try 
			{
				db.getConnection().setAutoCommit(false);
				
				String query = "update dondathang set trangthai='1',"+(this.LoaiDonhang.equals("1")?"ISKM=1":"ISKM=0")+" where pk_seq='" + this.id + "'";
				if(!db.update(query))
				{
					this.msg = "Không thể chốt đơn đặt hàng: " + query;
					db.getConnection().rollback();
					return false;
				}
				
				query = "select b.ma,a.sanpham_fk, a.soluong from dondathang_sp a, sanpham b where a.sanpham_fk = b.pk_seq and a.dondathang_fk='" + this.id + "'";
				ResultSet rs = this.db.get(query);
				
				while (rs.next())
				{
					query = "update erp_khott_sanpham set booked = booked + " + rs.getString("soluong") + ", available = available - " + rs.getString("soluong") + " where sanpham_fk = '" + rs.getString("sanpham_fk") + "' and khott_fk = '" + this.kho + "'";
					db.update(query);
					/*if(!db.update(query))
					{
						this.msg = "Không thể chốt đơn đặt hàng: " + query;
						db.getConnection().rollback();
						return false;
					}*/
				}
				db.getConnection().commit();
				
				//TAO DON HANG KHUYEN MAI
				this.ApkhuyenMai();
			} 
			catch (Exception e) 
			{
				System.out.println("EXCEPTION: " + e.getMessage());
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				return false;
			}
			
			return true;
		}
		else
		{
			return false;
		}
	}
	
	
	private void ApkhuyenMai() 
	{
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String query =  " select a.PK_SEQ as CTKM_FK, a.scheme, a.TUNGAY, a.DENNGAY " +
							" from CTKHUYENMAI a inner join PHANBOKHUYENMAI b on a.PK_SEQ = b.CTKM_FK " +
							" where a.kho_fk = '100001' and b.NPP_FK = '" + this.nppId + "' and a.TUNGAY <= '" + this.getNgaydh() + "' and a.DENNGAY >= '" + this.getNgaydh() + "'  ";
			
			System.out.println("___Check CTKM: " + query);
			
			ResultSet rs = db.get(query);
			
			String schemeStr = "";
			String soxuatStr = "";
			
			if(rs != null)
			{
				while(rs.next())
				{
					String ctkm_fk = rs.getString("CTKM_FK");
					int soXuat = getSoXuatTheoScheme(this.id, ctkm_fk);
		
					if( soXuat > 0 )
					{
						System.out.println("__CTKM: " + ctkm_fk + "____Soxuat KM la: " + soXuat);
						
						schemeStr += rs.getString("CTKM_FK") + "__";
						soxuatStr += Integer.toString(soXuat) + "__";
					}
				}
				rs.close();
			}
			
			//CO SCHME KHUYEN MAI
			String donKMID = "";
			
			if( schemeStr.trim().length() > 0 )
			{
				query = " insert DONDATHANG(DONDATHANG_FROM_FK, NGAYDAT, NGUOITAO, NGUOISUA, TRANGTHAI, NPP_FK, NCC_FK, DVKD_FK, DENGHIDATHANG_FK, KBH_FK, SOID, TINHTRANG, ISKM, LOAIDONHANG, GHICHU, KHOTT_FK, ngaydenghigh, DACHUYEN, CTVANCHUYEN, SOTIENBVAT, VAT, SOTIENAVAT, CHIETKHAU)  " +
						"select '" + this.id + "', NGAYDAT, '" + this.userId + "' as NGUOITAO, '" + this.userId + "' as NGUOISUA, TRANGTHAI, NPP_FK, NCC_FK, DVKD_FK, DENGHIDATHANG_FK, KBH_FK, SOID, TINHTRANG, '1' as ISKM, LOAIDONHANG, N'Đơn hàng khuyến mại của đơn đặt hàng " + this.id + "', KHOTT_FK, ngaydenghigh, DACHUYEN, CTVANCHUYEN, 0, 0, 0, 0 " +
						"from DONDATHANG  " +
						"where PK_SEQ = '" + this.id + "' ";
				
				System.out.println("___TAO DON KM: " + query);
				if(!db.update(query))
				{
					this.msg = "Lỗi khi áp khuyến mại: " + query;
					System.out.println(this.msg);
					db.getConnection().rollback();
					return;
				}
				else
				{
					query = "select IDENT_CURRENT('DONDATHANG') as donkhuyenmaiID";
					ResultSet rsDDH = db.get(query);
					if(rsDDH.next())
					{
						donKMID = rsDDH.getString("donkhuyenmaiID");
					}
					rsDDH.close();
				}
				
				schemeStr = schemeStr.substring(0, schemeStr.length() - 2);
				soxuatStr = soxuatStr.substring(0, soxuatStr.length() - 2);
				
				String[] schemeARR = schemeStr.split("__");
				String[] soxuatARR = soxuatStr.split("__");
				
				for(int i = 0; i < schemeARR.length; i++)
				{
					int sx = Integer.parseInt(soxuatARR[i]);
					
					
					//DUYET TUNG DIEU KIEN TRA
					query = "select TRAKHUYENMAI_FK from CTKM_TRAKM where CTKHUYENMAI_FK = '" + schemeARR[i] + "'";
					ResultSet rsTRA = db.get(query);
					while(rsTRA.next())
					{
						//NEU DIEU KIEN LA BAT KY TRONG CHI CO 1 SP THI LAY TONG LUONG
						double tongluong = 0;
						
						query = " select SUM(distinct b.TONGLUONG) as totalLuong, COUNT(*) as SoSP  " +
								" from TRAKHUYENMAI_SANPHAM a inner join TRAKHUYENMAI b on a.TRAKHUYENMAI_FK = b.PK_SEQ  " +
								" where TRAKHUYENMAI_FK = '" + rsTRA.getString("TRAKHUYENMAI_FK") + "' ";
						
						System.out.println("CHECK SO SP: " + query);
						ResultSet rsCheck = db.get(query);
						
						int soSPtrongTRA = 0;
						if(rsCheck.next())
						{
							soSPtrongTRA = rsCheck.getInt("SoSP");
							if( soSPtrongTRA <= 1 )
							{
								tongluong = sx * rsCheck.getDouble("totalLuong");
							}
						}
						rsCheck.close();
						
						if(soSPtrongTRA <= 1) //BAT KY TRONG CHI CO 1 SP
						{
							if(tongluong > 0)
							{
								query = " insert DONDATHANG_SP(DONDATHANG_FK, SANPHAM_FK, SOLUONG, DONVI, DONGIA, SOTIENBVAT, VAT, SOTIENAVAT, CHIETKHAU, SCHEME, KHOTT, SOLUONGDUYET) " +
										" select '" + donKMID + "', c.SANPHAM_FK, " + tongluong + ", e.DONVI, f.GIAMUANPP as DONGIA, f.GIAMUANPP * " + tongluong + " as SOTIENBVAT, '0' as VAT, f.GIAMUANPP * " + tongluong + " as SOTIENAVAT, 0, SCHEME, '100000' as KHOTT, '0' as SOLUONGDUYET " +
										" from CTKHUYENMAI a inner join CTKM_TRAKM b on a.PK_SEQ = b.CTKHUYENMAI_FK  " +
										" 	inner join TRAKHUYENMAI_SANPHAM c on b.TRAKHUYENMAI_FK = c.TRAKHUYENMAI_FK " +
										"	inner join SANPHAM d on c.SANPHAM_FK = d.PK_SEQ " +
										"	inner join DONVIDOLUONG e on d.DVDL_FK = e.PK_SEQ " +
										"	inner join BGMUANPP_SANPHAM f on c.SANPHAM_FK = f.SANPHAM_FK  " +
										"where a.PK_SEQ = '" + schemeARR[i] + "' and b.TRAKHUYENMAI_FK = '" + rsTRA.getString("TRAKHUYENMAI_FK") + "'" +
												" and f.BGMUANPP_FK in ( select BANGGIAMUANPP_FK from BANGGIAMUANPP_NPP where NPP_FK = '" + this.nppId + "' and BANGGIAMUANPP_FK in ( select PK_SEQ from BANGGIAMUANPP where TRANGTHAI = '1' ) ) ";
								
								System.out.println("1.INSERT TRA KM: " + query);
								if(!db.update(query))
								{
									this.msg = "Lỗi trong quá trình Áp khuyến mại: " + query;
									db.getConnection().rollback();
									System.out.println(this.msg);
									return;
								}
							}
						}
						else
						{
							//CHEN THANG SP LUON
							query = " insert DONDATHANG_SP(DONDATHANG_FK, SANPHAM_FK, SOLUONG, DONVI, DONGIA, SOTIENBVAT, VAT, SOTIENAVAT, CHIETKHAU, SCHEME, KHOTT, SOLUONGDUYET) " +
									" select '" + donKMID + "', c.SANPHAM_FK, " + sx + " * SOLUONG, e.DONVI, f.GIAMUANPP as DONGIA, ( f.GIAMUANPP * SOLUONG * " + sx + " ) as SOTIENBVAT, '0' as VAT, ( f.GIAMUANPP * SOLUONG * " + sx + " ) as SOTIENAVAT, 0, SCHEME, '100000' as KHOTT, '0' as SOLUONGDUYET " +
									" from CTKHUYENMAI a inner join CTKM_TRAKM b on a.PK_SEQ = b.CTKHUYENMAI_FK  " +
									" 	inner join TRAKHUYENMAI_SANPHAM c on b.TRAKHUYENMAI_FK = c.TRAKHUYENMAI_FK " +
									"	inner join SANPHAM d on c.SANPHAM_FK = d.PK_SEQ " +
									"	inner join DONVIDOLUONG e on d.DVDL_FK = e.PK_SEQ " +
									"	inner join BGMUANPP_SANPHAM f on c.SANPHAM_FK = f.SANPHAM_FK " +
									"where a.PK_SEQ = '" + schemeARR[i] + "' and b.TRAKHUYENMAI_FK = '" + rsTRA.getString("TRAKHUYENMAI_FK") + "' and isnull(c.SOLUONG, 0) > 0 " +
											" and f.BGMUANPP_FK in ( select BANGGIAMUANPP_FK from BANGGIAMUANPP_NPP where NPP_FK = '" + this.nppId + "' and BANGGIAMUANPP_FK in ( select PK_SEQ from BANGGIAMUANPP where TRANGTHAI = '1' ) ) ";
							
							System.out.println("2.INSERT TRA KM: " + query);
							if(!db.update(query))
							{
								this.msg = "Lỗi trong quá trình Áp khuyến mại: " + query;
								db.getConnection().rollback();
								System.out.println(this.msg);
								return;
							}
							
						}
					}
					rsTRA.close();	
				}
				
				query = " select count(*) as SoDong from DONDATHANG_SP where DONDATHANG_FK = '" + donKMID + "' ";
				ResultSet rsCheck = db.get(query);
				int sosongSP = 0;
				if(rsCheck.next())
				{
					sosongSP = rsCheck.getInt("SoDong");
				}
				
				if(sosongSP <= 0 ) //KHONG CO SP KHUYEN MAI NAO
				{
					db.getConnection().rollback();
					this.msg = "Không có sản phẩm khuyến mại nào được lưu. Vi lòng kiểm tra lại khai báo điều kiện trả.";
					System.out.println(this.msg);
					return;
				}
				
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch (Exception e) 
		{
			db.update("roolback");
			this.msg = "Lỗi xảy ra trong quá trình áp khuyến mại: " + e.getMessage();
			System.out.println("MSG: " + this.msg);
		}
		
	}
	
	private int getSoXuatTheoScheme(String dhId, String ctkmId)
	{	
		int soxuat = 0;
		
		String query = "";
		query =  " select '" + ctkmId + "' as ctkmId, chuongtrinhkhuyenmai.dkkmId as dkkmId, chuongtrinhkhuyenmai.pheptoan as pheptoan,     " + 
				 " 	floor( case  when (chuongtrinhkhuyenmai.LOAI = 2 and chuongtrinhkhuyenmai.tongluongPhaiMua > 0) then chuongtrinhkhuyenmai.tongluong / chuongtrinhkhuyenmai.tongluongPhaiMua       " + 
				 " 	when (chuongtrinhkhuyenmai.LOAI = 2 and chuongtrinhkhuyenmai.tongtienPhaiMua > 0) then chuongtrinhkhuyenmai.tongtien / chuongtrinhkhuyenmai.tongtienPhaiMua     " + 
				 " 	else chuongtrinhkhuyenmai.soxuatAnd end ) as Soxuat      " + 
				 " from     " + 
				 " (    " + 
				 "      select dieukienkhuyenmai.dkkmId as dkkmId, dieukienkhuyenmai.LOAI as loai, dieukienkhuyenmai.tongluongPhaiMua as tongluongPhaiMua, dieukienkhuyenmai.tongtienPhaiMua as tongtienPhaiMua,   " + 
				 "         dieukienkhuyenmai.sospbatbuoc as sospbatbuoc, dieukienkhuyenmai.pheptoan as pheptoan,   " + 
				 "         case when dieukienkhuyenmai.isthung='0' then ( case when SUM(muatheodk.tongluong) is null then 0 else SUM(muatheodk.tongluong) end )   " + 
				 " 												else ( case when SUM(muatheodk.trongluong) is null then 0 else SUM(muatheodk.trongluong) end  )  " + 
				 " 		end  as tongluong,    " + 
				 " 		case when SUM(muatheodk.tongtien) is null then 0 else SUM(muatheodk.tongtien) end as tongtien,       " + 
				 " 				  COUNT(case when muatheodk.batbuoc > 0 then 1 else null end ) sospphaimua,       " + 
				 " 		case when dieukienkhuyenmai.isthung='0' then (  case when MIN (muatheodk.tongluong / muatheodk.batbuoc) is null then 0   else MIN (muatheodk.tongluong / muatheodk.batbuoc) end  )  " + 
				 " 												else (  " + 
				 " 														case when MIN (muatheodk.trongluong / muatheodk.batbuoc) is null then 0   " + 
				 " 														else MIN (muatheodk.trongluong / muatheodk.batbuoc) end  " + 
				 " 													 )  " + 
				 "          end as soxuatAnd  " + 
				 "     from      " + 
				 "     (      " + 
				 "         select b.PK_SEQ as dkkmId, b.LOAI as loai, a.pheptoan as pheptoan,  b.isthung,   " + 
				 "             sum( distinct case isnull(tongluong, -1) when -1 then 0 else tongluong end ) as 'tongluongPhaiMua',      " + 
				 "             SUM( distinct case isnull(tongtien, -1) when -1 then 0 else tongtien end ) as 'tongtienPhaiMua',       " + 
				 "             count( case when c.soluong > 0 then 1 else null end ) as 'sospbatbuoc'     " + 
				 "         from CTKM_DKKM a inner join DIEUKIENKHUYENMAI b on a.DKKHUYENMAI_FK = b.PK_SEQ     " + 
				 "             inner join DIEUKIENKM_SANPHAM c on a.DKKHUYENMAI_FK = c.DIEUKIENKHUYENMAI_FK      " + 
				 "         where a.CTKHUYENMAI_FK = '" + ctkmId + "'    " + 
				 "         group by b.PK_SEQ, b.LOAI, a.pheptoan ,b.isthung       " + 
				 "     )       " + 
				 "     dieukienkhuyenmai left join      " + 
				 "     (     " + 
				 "         select muatrongnhom.dkkmId as dkkm_fk, muatrongnhom.spId as spId, muatrongnhom.tongtien as tongtien,   " + 
				 "             muatrongnhom.tongluong as tongluong, muatrongnhom.trongluong, batbuocmua.batbuoc as batbuoc    " + 
				 "         from      " + 
				 "         (      " + 
				 "             select dk.DIEUKIENKHUYENMAI_FK as dkkmId, donhang.SANPHAM_FK as spId, SUM(donhang.SOLUONG * donhang.DONGIA) as tongtien,  " + 
				 " 					SUM( donhang.SOLUONG * donhang.trongluong ) as trongluong  , SUM( donhang.SOLUONG ) as tongluong       " + 
				 "             from      " + 
				 "             (     " + 
				 " 				select SANPHAM_FK, SOLUONG, DONGIA, ISNULL(b.TRONGLUONG, 0) as trongluong  " + 
				 " 				from DONDATHANG_SP a inner join SANPHAM b on a.SANPHAM_FK = b.PK_SEQ  " + 
				 " 				where DONDATHANG_FK = '" + dhId + "' " + 
				 "             )      " + 
				 "             donhang	inner join DIEUKIENKM_SANPHAM dk on donhang.sanpham_fk = dk.SANPHAM_FK      " + 
				 "             where dk.DIEUKIENKHUYENMAI_FK in ( select DKKHUYENMAI_FK from CTKM_DKKM where CTKHUYENMAI_FK = '" + ctkmId + "' )       " + 
				 "             group by dk.DIEUKIENKHUYENMAI_FK, donhang.sanpham_fk    " + 
				 "          )      " + 
				 "          muatrongnhom left join       " + 
				 "          (     " + 
				 "              select DIEUKIENKHUYENMAI_FK as dkkmId, SANPHAM_FK as spId, case when isnull(soluong, '0') <= 0 then -1 else soluong end as batbuoc        " + 
				 " 			 from DIEUKIENKM_SANPHAM       " + 
				 "              where DIEUKIENKHUYENMAI_FK in ( select DKKHUYENMAI_FK from CTKM_DKKM where CTKHUYENMAI_FK = '" + ctkmId + "' )      " + 
				 "         )       " + 
				 "         batbuocmua on muatrongnhom.spId = batbuocmua.spId and muatrongnhom.dkkmId = batbuocmua.dkkmId      " + 
				 "         where cast( muatrongnhom.tongluong as INTEGER ) >= cast( batbuocmua.batbuoc as INTEGER )      " + 
				 "     )      " + 
				 "     muatheodk on muatheodk.dkkm_fk = dieukienkhuyenmai.dkkmId       " + 
				 "     group by  dieukienkhuyenmai.dkkmId, dieukienkhuyenmai.LOAI,  dieukienkhuyenmai.isthung, dieukienkhuyenmai.tongluongPhaiMua, dieukienkhuyenmai.tongtienPhaiMua,      " + 
				 "          dieukienkhuyenmai.sospbatbuoc, dieukienkhuyenmai.pheptoan       " + 
				 "     having COUNT(case when muatheodk.batbuoc > 0 then 1 else null end ) >= dieukienkhuyenmai.sospbatbuoc      " + 
				 "  )     " + 
				 " chuongtrinhkhuyenmai ";
		
		System.out.println("___Lay so xuat KM THEO SCHEME: " + query);
		
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				if(rs.next())
				{
					soxuat = rs.getInt("Soxuat");
				}
				rs.close();
			} 
			catch (Exception e) 
			{
				System.out.println("115.Error: " + e.getMessage());
			}
		}
		
		return soxuat;
	}
	
	public boolean UpdateDdhnpp(HttpServletRequest request) {
		getNppInfo();
		ResultSet rs = null;
		boolean checkTon = true;
		try{
		
			Utility util = new Utility();
			Hashtable h = Quycach();
			this.masp = request.getParameterValues("masp");
			if(this.LoaiDonhang.equals("1")){
				this.ThueSuat="0";
			}
			this.spId = request.getParameterValues("spId");	
			this.denghi = request.getParameterValues("denghi");
			this.tensp = request.getParameterValues("tensp");

			this.size = "" + masp.length;
			int size = masp.length;
			
			this.tspId = new String[size];	
			this.ton = new String[size];
			this.sl = new String[size];
			this.qc=new String[size];
			this.tmasp = new String[size];
			this.tsl = new String[size];
			this.ttsl = new String[size];
			this.dongia = new String[size];
			this.tienbVAT = new String[size];
			this.dongia = new String[size];
			this.donvi = new String[size];

			this.ttensp = new String[size];
			this.tdenghi = new String[size];
			this.tton = new String[size];
			this.tdonvi = new String[size];
			this.tdongia = new String[size];
			this.ttienbVAT = new String[size];
			
			this.tongtienbVAT = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tongtienbvat"));
			this.tongtienbVAT = this.tongtienbVAT.replace(",", "");
			
			this.vat = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vat"));
			this.vat = this.vat.replace(",", "");
			
			this.tongtienaVAT = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tongtienavat"));
			this.tongtienaVAT = this.tongtienaVAT.replace(",", "");

			String ddhId = this.id; 
							
			double tmpbvat = 0;
			double tmpvat = 0;
			double tmpavat = 0;
			double total = 0;
			checkTon = true;  
			int n = 0;
			this.maspstr = "";
			
			for (int i = 0; i < size ; i++){
				if (this.maspstr.length()==0){
					this.maspstr = "'" + this.masp[i] + "'";
				}else{
					this.maspstr = this.maspstr + ",'" + masp[i] + "'";
				}
				
				this.sl[i] = request.getParameter("sl" + this.masp[i]);
				this.qc[i]=request.getParameter("qc" + this.masp[i]);
				this.ton[i] = request.getParameter("ton" + this.masp[i]);
				this.donvi[i] = request.getParameter("dv" + this.masp[i]);
				this.dongia[i] = request.getParameter("dg" + this.masp[i]);
				if (this.dongia[i].length()==0){
					this.dongia[i] = "0";
				}else{
					this.dongia[i] = this.dongia[i].replace(",", "");
				}
				if (this.sl[i] .length()==0){
					this.sl[i]  = "0";
				}else{
					this.sl[i]  = this.sl[i].replaceAll(",", "");
				}
				
				this.tienbVAT[i] = request.getParameter("t" + this.masp[i]);
				this.tienbVAT[i] = this.tienbVAT[i].replace(",", "");
				
				if (this.sl[i].length()==0)
					this.sl[i] = "0";
				
			}
		
		db.getConnection().setAutoCommit(false);
		String query = "select denghidathang_fk as dndhId from dondathang where pk_seq = '" + this.id + "'";
		rs = this.db.get(query);
		System.out.println("query3: "+query);
		
		rs.next();
		String dndhId = rs.getString("dndhId");
		rs.close();
		query = "";
/*		
			
		
		}*/
		query = "select count(dondathang_fk) as num from dondathang_sp where dondathang_fk='"+ this.id + "'";
		rs = this.db.get(query);
		System.out.println("query5: "+ query);
		rs.next();
		int ddh_sp = Integer.valueOf(rs.getString("num")).intValue();
		
		this.db.update("update dondathang_sp set soluong='0' ,soluongduyet='0' where dondathang_fk='" + this.id + "'");
		String slg;
		String dg;
		String tienbvat = "0";
		String vat = "0";
		String tienavat = "0";
		for (int i = 0; i < this.masp.length ; i++){
			
				double quycach=0;
				try{
					quycach=Double.parseDouble(this.qc[i]);
				}catch (Exception e) {
					// TODO: handle exception
				}
				slg = "" + (Double.valueOf(this.sl[i]).doubleValue()); //* Double.valueOf((String)h.get(this.masp[i])).doubleValue());
				
				dg = "" + Double.parseDouble(this.dongia[i])  ;// Double.valueOf((String)h.get(this.masp[i])).doubleValue());
								
				tienbvat = "" + Double.valueOf(slg).doubleValue()*Double.valueOf(dg).doubleValue();
					
				vat = "" + Math.round(Double.valueOf(tienbvat).doubleValue()* Float.parseFloat(this.ThueSuat));
				
				tmpbvat = tmpbvat + (Double.valueOf(tienbvat).doubleValue());				
				
				tmpvat = tmpvat + Math.round(Double.valueOf(tienbvat).doubleValue()* Float.parseFloat(this.ThueSuat));
						
				tienavat = "" +( Math.round(Double.valueOf(tienbvat).doubleValue()) +Math.round(Double.valueOf(tienbvat).doubleValue()* Float.parseFloat(this.ThueSuat)));
				
				tmpavat = tmpavat + Math.round(Double.valueOf(tienavat).doubleValue());

				if (ddh_sp == 0){
					query = "insert into dondathang_sp (dondathang_fk,sanpham_fk,soluong,soluongduyet,donvi,dongia,sotienbvat,vat,sotienavat,khott,scheme) " +
							"values('" + this.id + "','" + this.spId[i] + "','" + slg + "','"+slg+"','Le','" + dg + "','" + tienbvat + "','" + vat +"','" + tienavat + "','"+this.kho+"','')";				
				}else{
					query = "update dondathang_sp set soluongduyet='"+slg+"',soluong ='" + slg + "', sotienbvat = '" + tienbvat + "', vat ='" + vat +"', sotienavat = '" + tienavat + "' where sanpham_fk = '" + this.spId[i] + "' and dondathang_fk = '"+ this.id + "'";
				}

			System.out.println("sQL: "+ query + "row: "+ i);
			
			if(!this.db.update(query)){
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				this.msg = query;
				System.out.println("sQL: "+ query + "row: "+ i);
				return false;
					
			}

			
			query = "update denghidathang_sp set dadathang ='" + tienbvat + "' where sanpham_fk='" + this.spId[i] + "' and denghidathang_fk='" + dndhId + "'";
			if(this.db.update(query)){
				total = total + Double.valueOf(tienbvat).doubleValue();
			}
			System.out.println("sQL: "+ query + "row: "+ i);
		
		}
		
		query = "update dondathang set  ghichu=N'"+this.GhiChu+"',CTVANCHUYEN ='"+this.contygiaohang+"',ngaydenghigh='"+this.NgayDeNghiGH+"',loaidonhang='"+this.LoaiDonhang+"',"+(this.LoaiDonhang.equals("1")?"ISKM=1":"ISKM=0")+",doihang='"+this.DoiHang+"' , NGAYGIODAT=dbo.GetLocalDate(DEFAULT) , ngaydat= '" + this.getDateTime() + "', sotienbvat = '" + tmpbvat + "',  nguoisua = '" + this.nguoisua + "', vat = '" + tmpvat + "', sotienavat = '" + tmpavat + "' where pk_seq = '" + this.id + "'";		
		System.out.println("Cau Lenh Lay Du Lieu : "+query);
		if(!this.db.update(query)){
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			this.msg = query;
			return false;
		}

		total = Math.round(total* Float.parseFloat(this.ThueSuat));
		query = "update denghidathang set dadathang='" + total + "', trangthai='1' where pk_seq ='" + dndhId + "'";
		if(!this.db.update(query)){
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			this.msg = query;
			return false;
		}
		db.getConnection().commit();
		db.getConnection().setAutoCommit(true);

		if(!checkTon){
			return false;
		}else{
			return true;
		}

		
	}catch(Exception  e){
		geso.dms.center.util.Utility.rollback_throw_exception(db);
		try {
			if(rs != null)
				rs.close();
		} catch (Exception e2) {
			
			System.out.println("Error DonDatHang : "+e.toString());
		}
		
		return false;}
	finally{try {
		if(rs != null)
			rs.close();
	} catch (Exception e2) {
		
	}}
		
		
	}

	

	private void getNppInfo(){
		
		
		geso.dms.distributor.util.Utility util=new geso.dms.distributor.util.Utility();
		this.nppId = util.getIdNhapp(this.userId);
		this.nppTen = util.getTenNhaPP();
		this.kho = util.getKhoSAP();
		
	}
	
	public boolean isInteger( String input )  
	{  
	   try  
	   {  
	      Integer.parseInt( input );  
	      return true;  
	   }  
	   catch(Exception e)  
	   {  
	      return false;  
	   }  
	 }  

	public String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	private String convertDate2(String date){
		String d = "";
		String year = date.substring(0, 4);
		String month = date.substring(5, 7);
		String day = date.substring(8, 10);
		d = "" + day + "-" + month + "-" + year;
		return d;
	}
	
	
	private String convertDate() {
        String newdate ="";
	    int day = Integer.valueOf(this.ngaydh.substring(0, 2)).intValue();		
	    int month = Integer.valueOf(this.ngaydh.substring(3, 5)).intValue();
	    int year = Integer.valueOf(this.ngaydh.substring(6, 10)).intValue();
        
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

	
	public void DBclose(){

		
		try {
			if(this.dvkdIds != null)
				this.dvkdIds.close();
			if(this.kbhIds != null)
				this.kbhIds.close();
			if(this.ncc != null)
				this.ncc.close();
			if(!(this.db == null)){
				this.db.shutDown();
			}
			
		} catch (Exception e) {
			
		}
	}
	
	public String[] getKhoiluong() 
	{	
		return this.tkhoiluong;
	}
	
	public void setKhoiluong(String[] khoiluong) 
	{
		this.tkhoiluong = khoiluong;
	}
	
	public String[] getThetich() 
	{
		return this.tthetich;
	}
	
	public void setThetich(String[] thetich) 
	{
		this.tthetich = thetich;
	}
	
	public String[] getSlDuyet() {
		
		return this.slduyet;
	}
	
	public void setSlDuyet(String[] tslduyet) {
		
		this.slduyet=tslduyet;
	}
	
	public String getNgaydenghigh() {
		
		return this.NgayDeNghiGH;
	}
	
	public void setNgaydenghigh(String ngaydenghigh) {
		
		this.NgayDeNghiGH=ngaydenghigh;
		
	}
	
	public String getDoiHang() {
		
		return this.DoiHang;
	}
	
	public void setDoiHang(String doihang) {
		
		this.DoiHang=doihang;
	}
	
	public String getLoaiDonHang() {
		
		return this.LoaiDonhang;
	}
	
	public void setLoaiDonHang(String loaidonhang) {
		
		this.LoaiDonhang=loaidonhang;
		
	}
	
	public String getThueSuat() {
		
		return this.ThueSuat;
	}
	
	public void setThueSuat(String thuesuat) {
		
		this.ThueSuat=thuesuat;
	}
	
	public String getLyDoHuy() {
		
		return this.LyDoHuy;
	}
	
	public void setLyDoHuy(String _LyDoHuy) {
		
		this.LyDoHuy=_LyDoHuy;
	}
	
	public String getcongTyGiaohang() {
		
		return this.contygiaohang;
	}
	
	public void setcongTyGiaohang(String congTyGiaohang) {
		
		this.contygiaohang =congTyGiaohang;
	}
	
	public String getGhiChu() {
		
		return this.GhiChu;
	}
	
	public void setGhiChu(String GhiChu) {
		
		this.GhiChu=GhiChu;
	}
	
	public String[] getScheme() {
		
		return this.scheme;
	}
	
	public void setScheme(String[] scheme) {
		
		this.scheme = scheme;
	}
	
	public ResultSet getCongnoList() {
		
		return this.cnList;
	}
	
	public void setCongnoList(ResultSet cnlist) {
		
		this.cnList = cnlist;
	}

}