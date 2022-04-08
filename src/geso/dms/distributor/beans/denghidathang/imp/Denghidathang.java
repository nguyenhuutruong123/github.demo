package geso.dms.distributor.beans.denghidathang.imp;
import geso.dms.distributor.beans.denghidathang.IDenghidathang;
import geso.dms.distributor.db.sql.dbutils;
import geso.dms.distributor.util.*;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;

public class Denghidathang implements IDenghidathang, Serializable
{
	private static final long serialVersionUID = -9217977546733610214L;
	String userId;
	String nppId;
	String id;
	String ngaydn;
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
	String tanso;
	String[] spId;
	String[] masp;
	String[] tensp;
	String[] sl;
	String[] dongia;
	String[] qc;
	String[] donvi;
	String[] tienbVAT;
	String[] tondau;
	String[] toncuoi;
	String[] ban;
	String[] tbban;
	String[] tonngay;
	String[] dukien;
	String[] dadat;
	String[] conlai;
	String size;
	String msg;
	String maspstr;
	
	String sitecode;
	
   	String tdday ;
	String tdmonth ;
	String tdyear ;
	String tddate ;
	
	String tcday ;
	String tcmonth ;
	String tcyear ;
	String tcdate ;
	
	String dsdday ;
	String dsdmonth ;
	String dsdyear ;
	String dsddate;
	
	String dscday ;
	String dscmonth ;
	String dscyear ;
	String dscdate ;

	String ds3mday;
	String ds3mmonth;
	String ds3myear;
	String ds3mdate;
	String querystr="";
	float songaytonkho=0;
	float muctangtruong=0;
	dbutils db;
	public Denghidathang()
	{
		this.db = new dbutils();
		
		
		Utility util = new Utility();
		this.id = "";
		this.ngaydn = "";
		this.soct = "";
		this.nguoitao = "";
		this.nguoisua = "";
		this.nppTen = "";
		this.nccId = "";
		this.dvkdId = "";
		this.kbhId = "";
		this.tongtienaVAT = "0";		
		this.vat = "0";
		this.tongtienbVAT = "0";
		this.size = "";
		this.msg="";
		this.tanso = "1";
		
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
	
	
	public String getNgaydn()
	{
		return this.ngaydn;
	}
	
	public void setNgaydn(String ngaydn)
	{
		this.ngaydn = ngaydn;
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
	
	public String getTanso()
	{
		return this.tanso;
	}
	
	public void setTanso(String tanso)
	{
		this.tanso = tanso;
	}

	public String[] getDadathang()
	{
		return this.dadat;
	}
	
	public void setDadathang(String[] dadathang)
	{
		this.dadat = dadathang;
	}

	public String[] getConlai()
	{
		return this.conlai;
	}
	
	public void setConlai(String[] conlai)
	{
		this.conlai = conlai;
	}

	public ResultSet getNcc()
	{
		return this.ncc;
	}
	
	public void setNcc(ResultSet ncc)
	{
		this.ncc = ncc;
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
	
	public String[] getSl()
	{
		return this.sl;
	}
	
	public void setSl(String[] sl)
	{
		this.sl = sl;
	}

	public String[] getDongia()
	{
		return this.dongia;
	}
	
	public void setDongia(String[] dongia)
	{
		this.dongia = dongia;
	}
	
	public String[] getTienbVAT()
	{
		return this.tienbVAT;
	}
	
	public void setTienbVAT(String[] tienbVAT)
	{
		this.tienbVAT = tienbVAT;
	}

	public String[] getDonvi()
	{
		return this.donvi;
	}
	
	public void setDonvi(String[] donvi)
	{
		this.donvi = donvi;
	}

	public String[] getTondau()
	{
		return this.tondau;
	}
	
	public void setTondau(String[] tondau)
	{
		this.tondau = tondau;
	}

	public String[] getToncuoi()
	{
		return this.toncuoi;
	}

	public void setToncuoi(String[] toncuoi)
	{
		this.toncuoi = toncuoi;
	}

	public String[] getBan()
	{
		return this.ban;
	}

	public void setBan(String[] ban)
	{
		this.ban = ban;
	}

	public String[] getTbban()
	{
		return this.tbban;
	}

	public void setTbban(String[] tbban)
	{
		this.tbban = tbban;
	}

	public String[] getTonngay()
	{
		return this.tonngay;
	}

	public void setTonngay(String[] tonngay)
	{
		this.tonngay = tonngay;
	}

	public String[] getDukien()
	{
		return this.dukien;
	}

	public void setDukien(String[] dukien)
	{
		this.dukien = dukien;
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
	
	public String getQueryStr(){
		return this.querystr;
	}

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
	
	public void init1(){
		
		/*
		 * tồn đầu : lấy tồn kho  quay lai so ngay (12,14..khong quan tam)
		 * Tồn cuối : quay lai 2 ngày so với ngày làm đề nghị.
		 * Tb bán 3 tháng.
		 * tb ngay =tb3t/6*12 
		 * Tồn ngày : tồn cuối/trung bình bán 
		 */
		getNppInfo();
		initDates(this.ngaydn);
		System.out.println("toi dang o day cac ban");
		Hashtable ht = new Hashtable<String, String>();
		String query; 
		float thuesuat=0;
		ResultSet rs;
		try{
			rs=this.db.get("select NGAYTONKHO,MUCTANGTRUONG,isnull(thue,0) as thue from CONGTHUCDNDH ");
			if(rs!=null){
				if(rs.next()){
					this.muctangtruong=rs.getFloat("MUCTANGTRUONG")/100;
					this.songaytonkho=rs.getFloat("NGAYTONKHO");
					thuesuat=rs.getFloat("thue")/100;
				}
			}
			
			
			rs= this.db.get("select count(ma) as num from sanpham where trangthai='1'");
			rs.next();
			this.size = rs.getString("num");		
			int i = new Integer(this.size).intValue();
			
			this.spId = new String[i];
			this.masp = new String[i];
			this.tensp = new String[i];
			this.sl = new String[i];
			this.qc = new String[i];
			this.dongia = new String[i];
			this.tienbVAT = new String[i];
			this.donvi = new String[i];
			this.tondau = new String[i];
			this.toncuoi = new String[i];
			this.ban  = new String[i];
			this.tbban  = new String[i];
			this.tonngay  = new String[i];
			this.dukien  = new String[i];
			this.dadat  = new String[i];
			this.conlai  = new String[i];

			query = "select d.pk_seq as nccId, d.ten as nccTen from nhaphanphoi a, nhacungcap_dvkd b, nhapp_nhacc_donvikd c, nhacungcap d where c.ncc_dvkd_fk = b.pk_seq and a.pk_seq = c.npp_fk and b.ncc_fk = d.pk_seq and a.pk_seq = '"+ this.nppId + "'";
			this.ncc = this.db.get(query);
			
			if(nccId.length()>0){
				query = "select e.pk_seq as dvkdId, e.donvikinhdoanh as dvkd from nhaphanphoi a, nhacungcap b, nhacungcap_dvkd c, nhapp_nhacc_donvikd d, donvikinhdoanh e where a.pk_seq = '"+ this.nppId + "' and b.pk_seq = '" + this.nccId + "' and a.pk_seq = d.npp_fk and b.pk_seq = c.ncc_fk and c.pk_seq = d.ncc_dvkd_fk and c.dvkd_fk = e.pk_seq";
				this.dvkdIds = this.db.get(query);
			}else{
				this.dvkdIds = this.db.get("select pk_seq as dvkdId, donvikinhdoanh as dvkd from donvikinhdoanh");
			}

			query = "select c.pk_seq as kbhId, c.diengiai as kbh from nhaphanphoi a, nhapp_kbh b, kenhbanhang c where a.pk_seq = '"+ this.nppId +"' and a.pk_seq = b.npp_fk and b.kbh_fk = c.pk_seq";
			this.kbhIds = this.db.get(query);
			
			String sp = createSplist();

			// Tinh ton dau
			String k= "(select distinct kho_fk from nhapp_kho inner join kho on nhapp_kho.kho_fk = kho.pk_seq where npp_fk = "+this.nppId+" and kbh_fk = "+this.kbhId+" and kho.ten='WH001')";
			
			String qr = "select ma, soluong from tonkhongay inner join sanpham sp on sp.pk_seq=sanpham_fk  where ngay='"+ this.tddate.replace("/","-") + "' and npp_fk='" + this.nppId + "' and kho_fk="+k+" and sp.ma in " + sp + " order by sp.ma";
		

			ResultSet rs2 = db.get(qr);			
			
			System.out.println("get ton dau  : " +  qr);
			//bảng ht nay luu mang cach nhau dau ";" ben trai là ton dau ben phai la ton cuoi. moi vao set ton cuoi=0 (rs2.getString("soluong") + ";0") )
			while(rs2.next()){
				ht.put((rs2.getString("ma")+"tk"), (rs2.getString("soluong") + ";0") );
			}
			rs2.close();
			
			// Tinh ton cuoi
			String sql="select ma, soluong from tonkhongay inner join sanpham sp on sp.pk_seq=tonkhongay.sanpham_fk where ngay='"+ this.tcdate.replace("/","-") + "' and npp_fk='" + this.nppId + "' and kho_fk="+ k +" and  sp.ma in " + sp + " order by sp.ma";
			System.out.println("Get Ton Cuoi : "+sql);
			rs2 = db.get(sql);
			
			while(rs2.next()){				
				if (ht.containsKey(rs2.getString("ma")+"tk")){
					String s = (String)ht.get(rs2.getString("ma")+"tk");
					s = s.split(";")[0] + ";" + rs2.getString("soluong");
					ht.put((rs2.getString("ma")+"tk"), s);
				}else{
					String s = "0;" + rs2.getString("soluong");
					ht.put((rs2.getString("ma")+"tk"), s);
				}
					
			}
			rs2.close();
			String tmp = "";
			String ban = "0";

			this.querystr="select ma, sum(soluong) as ban  from donhang_sanpham dh_sp "+  
	        	" inner join donhang dh on dh_sp.donhang_fk = dh.pk_seq  "+
	        	" inner join sanpham sp on sp.pk_seq = dh_sp.sanpham_fk  where dh.ngaynhap >='"+this.dsddate+"' "+ 
	        	" and dh.ngaynhap <='"+this.dscdate+"' and dh.npp_fk='"+this.nppId+"' and dh_sp.giamua >0 " +	        	
	        	" group by ma " + 
	        	" order by ma";
	        
			System.out.println("Doanh so tuan: " + this.querystr);
			rs2 = db.get(this.querystr);

			tmp = "";
			ban = "0";

			while(rs2.next()){
				if (rs2.getString("ma").equals(tmp)){
					ban = "" + (Double.valueOf(rs2.getString("ban")).doubleValue() + Double.valueOf(ban).doubleValue()); 
				}else{
					ban = rs2.getString("ban");
				}
				ht.put((rs2.getString("ma")+ "ban"),(ban + ";" +"0"));
				
				tmp = rs2.getString("ma");
				ban = rs2.getString("ban");
			}				
			rs2.close();
			this.querystr = avgQueryStr();
			
			System.out.println("this.querystr:" + this.querystr);
			rs2 = db.get(this.querystr);

			tmp = "";
			String tbban = "0";

			while(rs2.next()){				
				tbban = "" + Double.valueOf(rs2.getString("ban")).doubleValue()/(12*6); 
					
				if (ht.containsKey(rs2.getString("ma")+"ban")){
					String s = (String)ht.get(rs2.getString("ma")+"ban");
					s = s.split(";")[0] + ";" + tbban;
					ht.put((rs2.getString("ma")+"ban"), s);
				}else{
					String s = "0;" + tbban;
					ht.put((rs2.getString("ma")+"ban"), s);
				}
				
			}
					
		
			
			 sql =" SELECT SP.PK_SEQ as  id,SP.TEN,SP.MA,QC.SOLUONG1 AS QC,QC.SOLUONG2 AS SOLUONGLE,DVDL1_FK AS DVDLCHAN "+ 
			" , ISNULL(BG.GIAMUANPP,0)  AS DONGIA,DVDL.DONVI AS DV,ISNULL(KHO.SOLUONG,0) AS TONKHOCHAN "+
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
			" AND KHOTT_FK=(SELECT KHOSAP FROM NHAPHANPHOI NPP WHERE PK_SEQ="+this.nppId+")  ORDER by SP.MA";
			System.out.println("get de nghi dat hang :"+sql);
			
			rs= this.db.get(sql);
			
			i = 0;
			maspstr = "";
			this.tongtienbVAT = this.tongtienbVAT.replace(",", "");
			this.vat = this.vat.replace(",", "");
			this.tongtienaVAT = this.tongtienaVAT.replace(",", "");
			long tmpbvat = 0;
			long tmpvat = 0;
			long tmpavat = 0;
			
			while(rs.next()){
				this.spId[i] = rs.getString("id");
				this.masp[i] = rs.getString("ma");
				if (this.maspstr.length()==0){
					this.maspstr = "'" + this.masp[i] + "'";
				}else{
					this.maspstr = this.maspstr + ",'" + masp[i] + "'";
				}
			
				this.tensp[i] = rs.getString("ten");
				if(ht.containsKey(masp[i])){
					this.sl[i] = (String)ht.get(this.masp[i]);					
					this.dadat[i] = this.sl[i].split(";")[1];
					this.sl[i] = this.sl[i].split(";")[0];				
				}else{
					this.sl[i]	 = "0";
					this.dadat[i] = "0";
				}
				
				if(ht.containsKey(masp[i]+"tk")){
					this.tondau[i] = (String)ht.get(this.masp[i]+"tk");					
					this.toncuoi[i] = this.tondau[i].split(";")[1];
					this.tondau[i] = this.tondau[i].split(";")[0];				
				}else{
					this.tondau[i] = "0";					
					this.toncuoi[i] = "0";
				}

				if(ht.containsKey(masp[i]+"ban")){
					this.ban[i] = (String)ht.get(this.masp[i]+"ban");					
					this.tbban[i] = this.ban[i].split(";")[1];
					this.ban[i] = this.ban[i].split(";")[0];				
				}else{
					this.ban[i] = "0";					
					this.tbban[i] = "0";
				}

				
				
				if (Double.valueOf(this.toncuoi[i]).doubleValue()== 0){
					this.tonngay[i] = "0";
				}else{
					if (Double.valueOf(this.tbban[i]).doubleValue()==0){
						this.tonngay[i] ="NaN";
					}else{
						this.tonngay[i] = "" + (Double.valueOf(this.toncuoi[i]).doubleValue()/Double.valueOf(this.tbban[i]).doubleValue());
					}
				}										
				this.dukien[i] = "" + (Double.valueOf(this.tbban[i]).doubleValue()* this.muctangtruong *(6/Double.valueOf(this.tanso).longValue()));
				
				long d = Math.round(Double.valueOf(rs.getString("dongia")).doubleValue()) ;
				
				this.qc[i] = rs.getString("qc");
				long dg = d;
				this.dongia[i]	 = "" + dg ;
				System.out.println(tonngay[i]);
				
				if (!(this.tonngay[i].equals("NaN"))){
					if ((this.songaytonkho-Double.valueOf(this.tonngay[i]).doubleValue()) > 0){
						this.sl[i] = "" + Math.round(((this.songaytonkho*Double.valueOf(this.tbban[i]).doubleValue() - Double.valueOf(this.toncuoi[i]).doubleValue()) + Double.valueOf(this.dukien[i]).doubleValue()));	
						
					}else{
						this.sl[i] = "0";
					}
					
					if(Double.parseDouble(this.sl[i]) <0){
						this.sl[i]="0";
					}
				}
				
				if (this.sl[i].length()>0){
					long ltmp = (Double.valueOf(this.sl[i]).longValue())*(Math.round(Double.valueOf(this.dongia[i]).doubleValue()));
					this.tienbVAT[i] = "" + ltmp;
					tmpbvat = tmpbvat + Double.valueOf(this.tienbVAT[i]).longValue() ;
					
					
				
				}else{
					this.tienbVAT[i] = "0";
				}
				this.conlai[i] = "" + (Long.valueOf(this.tienbVAT[i]).longValue() - Long.valueOf(this.dadat[i]).longValue());
				this.donvi[i] = rs.getString("dv");
				i++;
			}
			this.size = "" + i;
			rs.close();
			
			this.tongtienbVAT = "" + tmpbvat; 
			tmpvat = Math.round(tmpbvat*thuesuat);
			this.vat = "" + tmpvat;
			tmpavat = Math.round(tmpbvat+ tmpbvat*thuesuat);	
			this.tongtienaVAT = "" + tmpavat;
	
//			String date = convertDate();
		
			query = "insert into denghidathang values('" + this.ngaydn + "', '" + this.tongtienbVAT + "','"+ this.userId + "','"+ this.userId + "','1', '" + this.nppId + "',"+this.nccId+",'" + this.vat + "','" + this.tongtienaVAT +"','" + this.dvkdId + "',0,'"+  this.kbhId + "')";//"+this.dadat[0]+", ncc: 100046
			System.out.print(query);
			if(!(this.db.update(query))){
				System.out.println("Khong the luu de nghi dat hang");
				System.out.println(query);
			}
			
			query = "select IDENT_CURRENT('denghidathang')as dndhId";
			rs = this.db.get(query);
			String dndhId = "";
			rs.next();
			dndhId = rs.getString("dndhId");
			this.id = dndhId;
			System.out.println("dndhId = " + this.id);
			rs.close();
				
			for (i = 0; i < new Integer(this.size).intValue() ; i++){
				String vat = "" + Math.round(Long.valueOf(this.tienbVAT[i]).longValue() * thuesuat);
				String tienavat = "" +( Math.round(Long.valueOf(this.tienbVAT[i]).longValue()+Long.valueOf(this.tienbVAT[i]).longValue()*thuesuat));
			
				String command = "insert into denghidathang_sp values(" + dndhId + ",'" +this.spId[i] + "'," + sl[i] + ",N'"+this.donvi[i]+"'," + this.dongia[i] + ",'" + this.tienbVAT[i]+ "','" + vat +"','" + tienavat + "','" + this.tondau[i] +  "','" + this.toncuoi[i] +  "','" + this.ban[i] +  "','" + this.tbban[i]+  "','" + this.tonngay[i]+  "','" + this.dukien[i] + "', '0')";
//				this.msg =command;
				if(!this.db.update(command)){
					this.msg = "Khong the luu de nghi dat hang";
					System.out.println("Loi dong lenh: " + command + "\n");
				}
			}
			rs.close();
			rs2.close();
			
		}catch(Exception  e){
			System.out.println("Error Denghidathang.java :"+ e.toString());
		}
		}

	//Xem lai de nghi dat hang	
	public void init2(){
		getNppInfo();
		Hashtable ht = new Hashtable<String, String>();
		String query; 
				
		ResultSet rs;
		try{
			rs= this.db.get("select count(ma) as num from sanpham where trangthai='1'");
			rs.next();
			this.size = rs.getString("num");		
			int i = new Integer(this.size).intValue();
			
			this.spId = new String[i];
			this.masp = new String[i];
			this.tensp = new String[i];
			this.sl = new String[i];
			this.qc = new String[i];
			this.dongia = new String[i];
			this.tienbVAT = new String[i];
			this.donvi = new String[i];
			this.tondau = new String[i];
			this.toncuoi = new String[i];
			this.ban  = new String[i];
			this.tbban  = new String[i];
			this.tonngay  = new String[i];
			this.dukien  = new String[i];
			this.dadat  = new String[i];
			this.conlai  = new String[i];

			query = "select d.pk_seq as nccId, d.ten as nccTen from nhaphanphoi a, nhacungcap_dvkd b, nhapp_nhacc_donvikd c, nhacungcap d where c.ncc_dvkd_fk = b.pk_seq and a.pk_seq = c.npp_fk and b.ncc_fk = d.pk_seq and a.pk_seq = '"+ this.nppId + "'";
			this.ncc = this.db.get(query);
			
			if(nccId.length()>0){
				query = "select e.pk_seq as dvkdId, e.donvikinhdoanh as dvkd from nhaphanphoi a, nhacungcap b, nhacungcap_dvkd c, nhapp_nhacc_donvikd d, donvikinhdoanh e where a.pk_seq = '"+ this.nppId + "' and b.pk_seq = '" + this.nccId + "' and a.pk_seq = d.npp_fk and b.pk_seq = c.ncc_fk and c.pk_seq = d.ncc_dvkd_fk and c.dvkd_fk = e.pk_seq";
				this.dvkdIds = this.db.get(query);
			}else{
				this.dvkdIds = this.db.get("select pk_seq as dvkdId, donvikinhdoanh as dvkd from donvikinhdoanh");
			}

			query = "select c.pk_seq as kbhId, c.diengiai as kbh from nhaphanphoi a, nhapp_kbh b, kenhbanhang c where a.pk_seq = '"+ this.nppId +"' and a.pk_seq = b.npp_fk and b.kbh_fk = c.pk_seq";
			this.kbhIds = this.db.get(query);
			
			query = "select ngaydat, ncc_fk, dvkd_fk, kbh_fk, sotienbvat, vat, sotienavat from denghidathang where pk_seq='"+ this.id + "'";
			rs = this.db.get(query);
			rs.next();
			this.ngaydn = rs.getString("ngaydat");
			this.dvkdId = rs.getString("dvkd_fk");
			this.nccId = rs.getString("ncc_fk");
			this.kbhId = rs.getString("kbh_fk");
			this.tongtienbVAT = rs.getString("sotienbvat");
			this.vat = rs.getString("vat");
			this.tongtienaVAT = rs.getString("sotienavat");
					
			query ="select a.sanpham_fk, b.ma,b.ten as tensp, a.denghidat as sl, a.dadathang, a.tondau, a.toncuoi, a.ban, " +
					"a.tbban, a.tonngay, a.dukien, a.donvi, a.dongia, a.sotienbvat from denghidathang_sp a, sanpham b " +
					"where denghidathang_fk='"+ this.id +"' and a.sanpham_fk=b.pk_seq order by b.ma";
			rs = this.db.get(query);				
			int m =0;
			while(rs.next()){
				this.spId[m] = rs.getString("sanpham_fk");			 
				this.masp[m] = rs.getString("ma");
				this.tensp[m]= rs.getString("tensp");
				this.tondau[m] = rs.getString("tondau");
				this.toncuoi[m] = rs.getString("toncuoi");
				this.tbban[m] = rs.getString("tbban");
				this.tonngay[m] = rs.getString("tonngay");
				this.dukien[m] = rs.getString("dukien");
				this.dadat[m] = rs.getString("dadathang");
				this.ban[m] = rs.getString("ban");
				this.sl[m] = rs.getString("sl");
				this.donvi[m] = rs.getString("donvi");
				this.dongia[m] = rs.getString("dongia");
				this.tienbVAT[m] = rs.getString("sotienbvat");
				this.conlai[m] = "" + (Double.parseDouble( this.tienbVAT[m]) -Double.parseDouble(this.dadat[m]));
				m++;
			}
			this.size = "" + m;
			
			rs.close();
			

		}catch(java.sql.SQLException e){}
	}
	

	private void getNppInfo(){
		geso.dms.distributor.util.Utility util=new geso.dms.distributor.util.Utility();
		this.nppId = util.getIdNhapp(this.userId);
		this.nppTen = util.getTenNhaPP();
		this.sitecode=util.getSitecode();
		
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
	private Calendar initDates(String date){
		System.out.println("initDates :"+date);
		
		int year = Integer.valueOf(date.substring(0, 4)).intValue();
	    int month = Integer.valueOf(date.substring(5, 7)).intValue();
	    int day = Integer.valueOf(date.substring(8, 10)).intValue();
	    

		Calendar calendar = Calendar.getInstance() ;

		calendar.set(Calendar.YEAR,year);
		calendar.set(Calendar.MONTH, month-1);
		calendar.set(Calendar.DATE, day);
		
		calendar.add(Calendar.DAY_OF_MONTH, (-2));
		
		if(calendar.get(Calendar.DAY_OF_WEEK)==1){
			calendar.add(Calendar.DAY_OF_MONTH, (-1));
		}
		if ((calendar.get(Calendar.DATE) + 1) < 10){
			this.tcday = "0" + calendar.get(Calendar.DATE);
		}else{
			this.tcday = "" + calendar.get(Calendar.DATE);
		}

		
//		this.msg = "ton cuoi =" + this.tcday;
		
		if ((calendar.get(Calendar.MONTH) + 1 )<10){
			this.tcmonth = "0" + (calendar.get(Calendar.MONTH) + 1);	
		}else{
			this.tcmonth = "" + (calendar.get(Calendar.MONTH) + 1);
		}
		
		this.tcyear = "" + calendar.get(Calendar.YEAR);
		this.tcdate= this.tcyear + "-" + this.tcmonth + "-" + this.tcday;
		System.out.println("Ngày Tồn Cuối : "+tcdate);
		
		System.out.println("Day Of WEEK : "+Calendar.DAY_OF_WEEK);
		if(calendar.get(Calendar.DAY_OF_WEEK)==7){
			calendar.add(Calendar.DAY_OF_MONTH, 2);
		}
		
		int tmp = 14 -(7 - calendar.get(Calendar.DAY_OF_WEEK));
		System.out.println(" Ngay Kho Hieu :"+Calendar.DAY_OF_MONTH+"");
		
		System.out.println("Ngay : "+ tmp);
		
		calendar.add(Calendar.DAY_OF_MONTH, (-tmp));
		if ((calendar.get(Calendar.DATE) + 1 )< 10){
			this.tdday = "0" + calendar.get(Calendar.DATE);
		}else{
			this.tdday = "" + calendar.get(Calendar.DATE);
		}

//		this.msg = this.msg + " ton dau =" + this.tdday;
		
		if ((calendar.get(Calendar.MONTH) + 1 )<10){
			this.tdmonth = "0" + (calendar.get(Calendar.MONTH) + 1);	
		}else{
			this.tdmonth = "" + (calendar.get(Calendar.MONTH) + 1);
		}

		this.tdyear = "" + calendar.get(Calendar.YEAR);
		this.tddate= this.tdyear + "-" + this.tdmonth + "-" + this.tdday;
		System.out.println("Ngay Tồn Đầu : "+ tddate);
		
		System.out.println(" Ngay Trong thang roi cong them 7 :"+Calendar.DAY_OF_MONTH+"");
		
		calendar.add(Calendar.DAY_OF_MONTH, 7);
		System.out.println(" SAU KHI  cong them 7 :"+Calendar.DAY_OF_MONTH+"");
		if ((calendar.get(Calendar.DATE) + 1 )< 10){
			this.dscday = "0" + calendar.get(Calendar.DATE);
		}else{
			this.dscday = "" + calendar.get(Calendar.DATE);
		}
		
//		this.msg = this.msg + "<br> doanh so cuoi =" + this.dscday;
		
		if ((calendar.get(Calendar.MONTH) + 1) <10){
			this.dscmonth = "0" + (calendar.get(Calendar.MONTH) + 1);	
		}else{
			this.dscmonth = "" + (calendar.get(Calendar.MONTH) + 1);
		}
		this.dscyear = "" + calendar.get(Calendar.YEAR);
		this.dscdate= this.dscyear + "-" + this.dscmonth + "-" + this.dscday;
		System.out.println("Doanh Sô  dscdate : "+ tddate);
		calendar.add(Calendar.DAY_OF_MONTH, (-5));
		
		if ((calendar.get(Calendar.DATE) + 1) < 10){
			this.dsdday = "0" + calendar.get(Calendar.DATE);
		}else{
			this.dsdday = "" + calendar.get(Calendar.DATE);
		}
//		this.msg = this.msg + "<br> doanh so dau =" + this.dsdday;

		if ((calendar.get(Calendar.MONTH) + 1) <10){
			this.dsdmonth = "0" + (calendar.get(Calendar.MONTH) + 1);	
		}else{
			this.dsdmonth = "" + (calendar.get(Calendar.MONTH) + 1);
		}

		this.dsdyear = "" + calendar.get(Calendar.YEAR);
		this.dsddate= this.dsdyear + "-" + this.dsdmonth + "-" + this.dsdday;
		System.out.println(" Doanh So dau : dsddate :"+dsddate+"");
		calendar.add(Calendar.DAY_OF_MONTH, 5);
		calendar.add(Calendar.DAY_OF_MONTH, (-12*7 + 1));

		if ((calendar.get(Calendar.DATE) + 1) < 10){
			this.ds3mday = "0" + calendar.get(Calendar.DATE);
		}else{
			this.ds3mday = "" + calendar.get(Calendar.DATE);
		}
		
		if ((calendar.get(Calendar.MONTH) + 1) <10){
			this.ds3mmonth = "0" + (calendar.get(Calendar.MONTH) + 1);	
		}else{
			this.ds3mmonth = "" + (calendar.get(Calendar.MONTH) + 1);
		}
		
		
		this.ds3myear = "" + calendar.get(Calendar.YEAR);
		
		
		this.ds3mdate = this.ds3myear + "-" + this.ds3mmonth + "-" + this.ds3mday;
		
		System.out.println(" Doanh So ds3mdate :"+ds3mdate+"");
		
		return calendar;
	}
	
	private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	private String convertDate() {
        String newdate ="";
	    int day = Integer.valueOf(this.ngaydn.substring(0, 2)).intValue();		
	    int month = Integer.valueOf(this.ngaydn.substring(3, 5)).intValue();
	    int year = Integer.valueOf(this.ngaydn.substring(6, 10)).intValue();
        
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


	private String createSplist(){
		String sp = "(";
		ResultSet rs = this.db.get("select ma from sanpham order by ma");
		try{
			while (rs.next()){
				if (sp.equals("(")){
					sp = sp + "'" + rs.getString("ma") + "'" ;
				}else{
					sp = sp + ",'" + rs.getString("ma") + "'" ;
				}
			}
			sp = sp + ")";
			
		}catch(java.sql.SQLException e){}
		
		return sp;
	}
	
	private String avgQueryStr(){
		String querystr = "";
		System.out.println("da vao day");

		String date1 ; 
		String date2 ; 
		date1 = this.ds3myear + "-" + this.ds3mmonth + "-" + this.ds3mday;
		date2 = this.dscyear + "-" + this.dscmonth + "-" + this.dscday;

		querystr =	"select ma, sum(soluong)as ban  from donhang_sanpham dh_sp " +
					"inner join donhang dh on dh_sp.donhang_fk = dh.pk_seq " +
					"inner join sanpham sp on sp.pk_seq = dh_sp.sanpham_fk  " +
					"where dh.ngaynhap >='" + date1 + "' and dh.ngaynhap <='" + date2 + "' and dh.npp_fk='" + this.nppId + "' " +
					"and dh_sp.giamua <>0 group by ma " ;
		System.out.println("Doanh So Trung Binh  avgQueryStr : "+querystr);
//				
		return querystr;		
	}
	
	private String QueryStr(String year, String month1, String day1, String month2, String day2){
		String querystr = "";
		String date1 = year + "-" + month1 + "-" + day1;
		String date2 = year + "-" + month2 + "-" + day2;

		int i = Integer.valueOf(month2).intValue() - Integer.valueOf(month1).intValue();
		for (int n = 0; n <= i; n++){
			if(n==0){
				if (i >0){
					querystr = "select ma, sum(soluong) as ban  from donhang_sanpham dh_sp inner join donhang dh on dh_sp.donhang_fk = dh.pk_seq inner join sanpham sp on sp.pk_seq = dh_sp.sanpham_fk  where dh.ngaynhap >='" + date1 + "' and dh.npp_fk='" + this.nppId + "' and dh_sp.soluong*dh_sp.giamua*0.1 >0 group by ma";
				}else{
					querystr = "select ma, sum(soluong) as ban  from donhang_sanpham dh_sp inner join donhang dh on dh_sp.donhang_fk = dh.pk_seq inner join sanpham sp on sp.pk_seq = dh_sp.sanpham_fk  where dh.ngaynhap >='" + date1 + "' and dh.ngaynhap <='" + date2 + "' and dh.npp_fk='" + this.nppId + "' and dh_sp.soluong*dh_sp.giamua*0.1 >0 group by ma";
				}
			}else{ 
				if (n==i){
					querystr = querystr + " union select ma, sum(soluong) as ban  from donhang_sanpham dh_sp inner join donhang dh on dh_sp.donhang_fk = dh.pk_seq inner join sanpham sp on sp.pk_seq = dh_sp.sanpham_fk  where dh.ngaynhap <='" + date2 + "' and dh.npp_fk='" + this.nppId + "' and dh_sp.soluong*dh_sp.giamua*0.1 >0 group by ma";
				}else{
					String month = "";
					int m = Integer.valueOf(month1).intValue() + n;
					if (m < 10) 
						month = "0" + m;
					else
						month = "" + m;
						
					querystr = querystr + " union select ma, sum(soluong) as ban  from donhang_sanpham dh_sp inner join donhang dh on dh_sp.donhang_fk = dh.pk_seq inner join sanpham sp on sp.pk_seq = dh_sp.sanpham_fk  where dh.npp_fk='" + this.nppId + "' and dh_sp.soluong*dh_sp.giamua*0.1 >0 group by ma" ;
					}
				}
		}
		
		return querystr;
	}
	
	public void DBclose(){
		try{
			if(!(this.ncc  == null))
				this.ncc.close();
			
			if(!(this.dvkdIds == null))
				this.dvkdIds.close();
		
			if(!(this.kbhIds == null))
				this.kbhIds.close();
		
			if(!(this.db == null)){
				this.db.shutDown();
			}

			if(!(this.db == null)){
				this.db.shutDown();
			}
			
		}catch(java.sql.SQLException e){}
	}
	public int luufile(String id,String name,String userId,int loai)
	{
		String idnpp="";
		String sql="select npp.pk_seq from nhanvien nv , nhaphanphoi npp where nv.CONVSITECODE=npp.SITECODE and nv.PK_SEQ="+userId;
		ResultSet r=db.get(sql);
		try {
			while (r.next()){
				idnpp=r.getString("pk_seq");
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		int flag=0;
		try {
			
			sql="select * from XEMFILE where PK_SEQ <>"+id+" and name='"+name+"' and loai <> "+loai+" and npp_fk <> "+idnpp;
			System.out.println(sql);
			ResultSet rs=this.db.get("select * from XEMFILE where PK_SEQ <>"+id+" and name='"+name+"' and loai <> "+loai+" and npp_fk <> "+idnpp );

			if(rs.next()){
				flag=flag+1;
				this.msg="vui long doi ten file";
			}
			rs.close();
			if(flag>0)
				return 1;
			this.db.getConnection().setAutoCommit(false);
			sql="delete from XEMFILE where PK_SEQ="+id+" and loai="+loai+" and npp_fk="+idnpp ; 
			System.out.println(sql);
			if(!this.db.update(sql))
			{
				 geso.dms.center.util.Utility.rollback_throw_exception(db);
				 this.msg="loi trong qua trinh upload";
				 return 2;
			}
			sql="insert into XEMFILE(PK_SEQ,NPP_FK,NAME,LOAI,DIENGIAI) values ("+id+","+idnpp+",'"+name+"',"+loai+",'de nghi doi hang')";
			System.out.println("____________sqo"+sql);
			if(!this.db.update(sql))
			{
				 geso.dms.center.util.Utility.rollback_throw_exception(db);
				 this.msg="loi trong qua trinh upload";
				 return 2;
			}
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
			return 3;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}
	public String displayfile(String id,String userId,int loai)
	{
		String tenfile="";
		String idnpp="";
		String sql="select npp.pk_seq from nhanvien nv , nhaphanphoi npp where nv.CONVSITECODE=npp.SITECODE and nv.PK_SEQ="+userId;
		ResultSet r=db.get(sql);
		try {
			while (r.next()){
				idnpp=r.getString("pk_seq");
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		sql="select name from xemfile where pk_seq="+id+" and npp_fk="+idnpp+" and loai="+loai;
		ResultSet rs=this.db.get(sql);
		try {
			while (rs.next())
			{
				tenfile=rs.getString("name");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tenfile;
	}
}
