package geso.dms.distributor.beans.denghidathangnpp.imp;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

import geso.dms.distributor.beans.denghidathangnpp.IDanhsachsanpham;
import geso.dms.distributor.beans.denghidathangnpp.IDenghidathangnpp;
import geso.dms.distributor.db.sql.dbutils;
import geso.dms.distributor.util.Utility;
import 	java.util.*;
public class Denghidathangnpp implements IDenghidathangnpp, Serializable{
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
	String[] thieuhang;
	String size;
	String msg;
	String maspstr;
	String trangthai;
	
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
	dbutils db;
    List<IDanhsachsanpham> Danhsachsanpham =new ArrayList<IDanhsachsanpham>();
  
	public Denghidathangnpp()
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
		this.trangthai ="0";
		this.thieuhang = new String[205] ;
		for(int i =0;i<200;i++)
		this.thieuhang[i] =i+"";
		Danhsachsanpham = new ArrayList<IDanhsachsanpham>();
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
	/*
	 * (non-Javadoc)
	 * @see geso.dms.distributor.beans.denghidathangnpp.IDenghidathangnpp#init0()
	 * Nuoi sua: KHOAND,Ban nao trong nhom lap trinh sưa cho nao thi ghi ro chu thich ra nhe!Thank
	 * Phuong thuc nay dung de load ra cac resultset
	 */
	public void init0() {
		getNppInfo();
		String query = "select d.pk_seq as nccId, d.ten as nccTen from nhaphanphoi a, nhacungcap_dvkd b, nhapp_nhacc_donvikd c, nhacungcap d where c.ncc_dvkd_fk = b.pk_seq and a.pk_seq = c.npp_fk and b.ncc_fk = d.pk_seq and a.pk_seq = '"+ this.nppId + "'";
		this.ncc = this.db.get(query);
		//////System.out.println(query);
		if(nccId.length()>0){
			query = "select e.pk_seq as dvkdId, e.donvikinhdoanh as dvkd from nhaphanphoi a, nhacungcap b, nhacungcap_dvkd c, nhapp_nhacc_donvikd d, donvikinhdoanh e where a.pk_seq = '"+ this.nppId + "' and b.pk_seq = '" + this.nccId + "' and a.pk_seq = d.npp_fk and b.pk_seq = c.ncc_fk and c.pk_seq = d.ncc_dvkd_fk and c.dvkd_fk = e.pk_seq";
			//////System.out.println(query);
			
			this.dvkdIds = this.db.get(query);
		}else{
			this.dvkdIds = this.db.get("select pk_seq as dvkdId, donvikinhdoanh as dvkd from donvikinhdoanh");
		}
      
		query = "select distinct c.pk_seq as kbhId, c.diengiai as kbh from nhaphanphoi a, nhapp_kbh b, kenhbanhang c where a.pk_seq = '"+ this.nppId +"' and a.pk_seq = b.npp_fk and b.kbh_fk = c.pk_seq";
		////System.out.println(query);
		
		this.kbhIds = this.db.get(query);
	}
	public void init1() {
		IDanhsachsanpham sp;
		String query;
		//query ="select * from npp_kho where npp_fk ='"+ this.nppId +"'";
		if(this.dvkdId.length()>0 && this.kbhId.length()>0)
		{
		//query = "select b.pk_seq,b.ten,a.giamuanpp from bgmuanpp_sanpham a,sanpham b where a.sanpham_fk = b.pk_seq and a.trangthai ='1' and a.bgmuanpp_fk in (select pk_seq from banggiamuanpp where dvkd_fk='"+ this.dvkdId +"' and kenh_fk ='"+ this.kbhId +"')";
		query ="select b.pk_seq,b.ten,a.giamuanpp,g.donvi " +
				"from bgmuanpp_sanpham a,sanpham b,BANGGIAMUANPP_NPP d, " +
				"BANGGIAMUANPP e,donvidoluong g where b.dvdl_fk = g.pk_seq and a.BGMUANPP_FK = e.pk_seq " +
				"and d.BANGGIAMUANPP_FK = e.pk_seq and a.sanpham_fk = b.pk_seq and a.trangthai ='1' " +
				"and d.npp_fk ='"+ this.nppId +"' and  e.dvkd_fk='"+ this.dvkdId +"' and e.kenh_fk ='"+ this.kbhId +"'";
			////System.out.println(query);
		ResultSet rs = db.get(query);
		String sql;
		String masp;
		try {
		while(rs.next())
		{
			sp = new Danhsachsanpham();
			masp =rs.getString("pk_seq");
		    sp.setMasp(masp);
		    sp.setTensp(rs.getString("ten"));
		    sp.setBantheongay(soluong(this.kbhId,masp));
		    
		    // sql ="select * from nhapp_kho where npp_fk ='"+ this.nppId +"' and sanpham_fk ='"+ rs.getString("pk_seq")+"' and kbh_fk ='"+ this.kbhId +"' and ngay =dateadd(day,-1,'"+ this.ngaydn+"');";
		    sql ="select * from nhapp_kho where npp_fk ='"+ this.nppId +"' and sanpham_fk ='"+ rs.getString("pk_seq")+"' " +
		    		"and kbh_fk ='"+ this.kbhId +"';";
			////System.out.println(sql);
		    ResultSet tb= db.get(sql);
		    if(tb != null)
		    {
		    	tb.next();
		    	sp.setTondau(tb.getString("soluong"));
		    }
		  //  sp.setTondau(rs.getString("soluong"));
		    
		    sp.setDonvi(rs.getString("donvi"));
		  //  sp.setTondau(tondau(masp));
		 //   System.out.println("ton dau:"+tondau(masp)+" ban theo ngay :"+soluong(this.kbhId,masp) );
		    if(!sp.getBantheongay().equals("0"))
		    { int tonngay =(int) Math.ceil(Double.parseDouble(sp.geTondau())/Double.parseDouble(soluong(this.kbhId,masp)));
			  sp.setTonngay(tonngay+"");
		    }
		    else
		    	sp.setTonngay("0");
		   
		    sp.setDongia(rs.getString("giamuanpp"));
		     String so ;
		     if(this.tanso.equals("2"))
		    	 so ="3";
		     else
		    	 so="6";
		    //float dukienban = Float.parseFloat(sp.getBantheongay())* Float.parseFloat("102") * Float.parseFloat(so);
		   int dukienban = (int) Math.ceil(Double.parseDouble(sp.getBantheongay())* Double.parseDouble("1.02") * Double.parseDouble(so));
		    sp.setDukienban(dukienban+"");
		    int denghi = (int) Math.ceil(Double.parseDouble(sp.getBantheongay())* Double.parseDouble(ngay(this.ngaydn))+ Double.parseDouble(sp.getDukienban()) - Double.parseDouble(sp.geTondau()));
		    if(denghi <0)
		    	denghi = 0;
		    sp.setDenghi(denghi+"");
		    int tongtien = (int) Math.ceil(Double.parseDouble(sp.getDongia())* Double.parseDouble(sp.getDenghi()));
		    sp.setTongtien(tongtien+"");
		    sp.setConlai(tongtien+"");
	        sp.setnppId(this.nppId);
	        sp.init(); 
		   
		    this.Danhsachsanpham.add(sp);
		}
		} catch(Exception e) {
		////System.out.println("thong bao loi" + e);
		}
	}
}
	public void khoitaodenghi() {
		
		IDanhsachsanpham sp;
		 NumberFormat formater =new DecimalFormat("#.##");//Loai 1 la format sau dau .
		 
		NumberFormat	formater2=new DecimalFormat("#,###,###"); //Loai 2 la format kieu so dang tien te
		Calendar c2 = Calendar.getInstance();
       //System.out.println("Ngay dat hang :"+this.ngaydn);
        int nam=Integer.parseInt(this.ngaydn.substring(0, 4));
        int thang=Integer.parseInt(this.ngaydn.substring(5, 7));
        int ngay=Integer.parseInt(this.ngaydn.substring(8, 10));
        //Lay thoi gian trong khoang 3 thang truoc co,tinh tu dau thang tro lai 3 thang
        c2.set(nam, thang-1, 1);//Lay ngay thang la thang nam dat chi tieu
        //lay nguoc lai cach 3 thang
        c2.add(Calendar.MONTH, -3);//get datetime before 3 month
        
        DateFormat formatdate=new SimpleDateFormat("yyyy-MM-dd");
        String tungay=formatdate.format(c2.getTime());
        c2.set(nam,thang-1,ngay);
        String toingay=formatdate.format(c2.getTime());
        //Thuc hien lay khoang thoi gian la 1 tuan truoc
        int ngaytrongtuan=c2.get(Calendar.DAY_OF_WEEK)-1;
        //System.out.println("Ngay TRong Tuan :" +ngaytrongtuan);
        c2.add(Calendar.DATE,-ngaytrongtuan);
        String ngaycuoituan=formatdate.format(c2.getTime());
        c2.add(Calendar.DATE,-(ngaytrongtuan+6));
        String ngaydautuan=formatdate.format(c2.getTime());
        //System.out.println("Ngay Cuoi Tuan :"+ngaycuoituan );
		String query="select isnull( sl_tb_ngay.sltbngay,0) as sltb_ngay,isnull(sl_tb_tuan.sltuan_truoc,0) as sltuan_truoc," +
		"  sp.ma,bg_sp.sanpham_fk,sp.ten,dvdl.donvi,bg_sp.giamuanpp as dongia,k.available as toncuoi  from  banggiamuanpp bg inner join banggiamuanpp_npp bg_npp on bg.pk_seq=bg_npp.banggiamuanpp_fk "+ 
		" inner join bgmuanpp_sanpham bg_sp on bg_sp.bgmuanpp_fk=bg.pk_seq "+
		" inner join sanpham sp on bg_sp.sanpham_fk=sp.pk_seq "+
		" inner join donvidoluong dvdl on dvdl.pk_Seq=sp.dvdl_fk "+
		" inner join  nhapp_kho k on k.sanpham_fk=bg_sp.sanpham_fk and k.npp_fk="+this.nppId+" and k.kbh_fk="+this.kbhId+" and k.kho_fk = (select distinct kho_fk from nhapp_kho inner join kho on nhapp_kho.kho_fk = kho.pk_seq where npp_fk = "+this.nppId+" and kbh_fk = "+this.kbhId+" and kho.ten='wh001') "+//100000
		" left join (select  dh_sp.sanpham_fk, sum(soluong) as sltbngay from donhang_sanpham dh_sp inner join donhang dh on dh.pk_seq=dh_sp.donhang_fk inner join giamsatbanhang gsbh on gsbh.pk_seq=dh.gsbh_fk where "+
		" dh.npp_fk="+this.nppId+" and ngaynhap >='"+tungay+"' and ngaynhap <'"+toingay+"' and gsbh.dvkd_fk="+this.dvkdId+"  and dh.kbh_fk="+this.kbhId+" group by sanpham_fk  "+
		" ) as sl_tb_ngay on sl_tb_ngay.sanpham_fk=bg_sp.sanpham_fk "+
		" left join (select  dh_sp.sanpham_fk, sum(soluong) as sltuan_truoc from donhang_sanpham dh_sp inner join donhang dh on dh.pk_seq=dh_sp.donhang_fk inner join giamsatbanhang gsbh on gsbh.pk_seq=dh.gsbh_fk  where"+
		" dh.npp_fk="+this.nppId+" and ngaynhap >='"+ngaydautuan+"' and ngaynhap <='"+ngaycuoituan+"' and gsbh.dvkd_fk="+this.dvkdId+"  and dh.kbh_fk="+this.kbhId+" group by sanpham_fk  " +
		" ) as sl_tb_tuan on sl_tb_tuan.sanpham_fk=bg_sp.sanpham_fk   where bg_npp.npp_fk="+this.nppId+" and bg.dvkd_fk="+this.dvkdId+" and bg.kenh_fk="+this.kbhId+" and bg_sp.trangthai='1'";
	     System.out.println("Cau Lenh Lay De Nghi: "+query);
		ResultSet rs_getsolieu=	db.get(query);
		if(rs_getsolieu!=null){
			try{
			while(rs_getsolieu.next()){
				sp=new Danhsachsanpham();
				double soluongtb_ngay=rs_getsolieu.getDouble("sltb_ngay")/90;
				if(soluongtb_ngay>0){
					System.out.println("So Luong TB Ngay :"+ formater.format(soluongtb_ngay));
				}
				sp.setBantheongay(formater.format(soluongtb_ngay));//so luong ban trung binh tung ngay trong 3 thang
				
				sp.setSL_BanTuanTruoc(rs_getsolieu.getInt("sltuan_truoc"));//so luong hang ban duoc trong tuan truoc
				sp.setMasp(rs_getsolieu.getString("ma"));//set ma san pham
				sp.setIdSanPham(rs_getsolieu.getString("sanpham_fk"));
				sp.setTensp(rs_getsolieu.getString("ten"));
				sp.setDonvi(rs_getsolieu.getString("donvi"));
				
				sp.setTonCuoi(rs_getsolieu.getInt("toncuoi"));
				//Ton ngay::    Số ngày tồn kho của từng SKU, tính theo công thức: Ton cuoi/ TTBan theo ngay
				
				double tonngay=0;
				if(soluongtb_ngay>0)
				{
					tonngay= rs_getsolieu.getDouble("toncuoi")/soluongtb_ngay;
				}else{
					tonngay=0;
				}
				
				sp.setTonngay(formater.format(tonngay));
				//Du Kien Ba Se Bang Trung Binh Ban Theo Ngay *102 % songaytrong tuan
				int tanso=1;
				try{
				 tanso=Integer.parseInt(this.getTanso());
				}catch(Exception er){
					tanso=1;
				}
				Double soluongdukienban=soluongtb_ngay*(102/100)* (6/tanso);
				//TB Ban/ ngay * 18 + Du Kien Ban – Ton Cuoi
				double soluongdenghi=soluongtb_ngay*18+ soluongdukienban- rs_getsolieu.getInt("toncuoi");
				if(soluongdenghi<0){
					soluongdenghi=0;//Neu so luong de nghi sau khi tinh nho hon 0 thi cho =0;
				}
				sp.setDenghi(formater.format(soluongdenghi));
				sp.setDukienban(formater.format(soluongdukienban));
				double tongtien=soluongdenghi * rs_getsolieu.getDouble("dongia");
			

				sp.setDongia(formater2.format(rs_getsolieu.getDouble("dongia")) );
				sp.setTongtien(formater2.format(tongtien));
				Danhsachsanpham.add(sp);
				
			}
			}catch(Exception er){
				System.out.println("Loi Denghidathangnpp.java,Lien He Voi KHOAND ... ERROR :" + er.toString());
			}
			finally{
				
				try {
					rs_getsolieu.close();
				} catch(Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}
		
	
	String tondau(String masp)
	{//cau lenh lay ra tong so luong 3 thang  roi chia deu cho so ngay 3 thang do
		// lay ra tong so ngay 3 thang: DATEDIFF(day,CONVERT(VARCHAR(10),dateadd(month,-3 ,dateadd(day,-day(getdate()),getdate())),120),CONVERT(VARCHAR(10),dateadd(day,-day(getdate()),getdate()),120))
		
		String sql ="select soluong as num from donhang_sanpham where sanpham_fk ='"+ masp +"' and donhang_fk in (select pk_seq from donhang where npp_fk ='"+ this.nppId +"' and kbh_fk='"+ this.kbhId +"')";
		//////System.out.println(sql);
		
		ResultSet rs = db.get(sql);
		if(rs != null)
		{
			try {
				rs.next();
				return rs.getString("num");
			} catch(Exception e) {
				
				e.printStackTrace();
			}
			finally{try {
				rs.close();
			} catch(Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}}
			
		}
		return "0";
	}
	
	String soluong(String kenh, String masp)
	{//cau lenh lay ra tong so luong 3 thang  roi chia deu cho so ngay 3 thang do
		// lay ra tong so ngay 3 thang: DATEDIFF(day,CONVERT(VARCHAR(10),dateadd(month,-3 ,dateadd(day,-day(getdate()),getdate())),120),CONVERT(VARCHAR(10),dateadd(day,-day(getdate()),getdate()),120))
		
		//String sql ="select sum(soluong)/DATEDIFF(day,CONVERT(VARCHAR(10),dateadd(month,-3 ,dateadd(day,-day(getdate()),getdate())),120),CONVERT(VARCHAR(10),dateadd(day,-day(getdate()),getdate()),120)) as num from donhang_sanpham where sanpham_fk ='"+ masp +"' and donhang_fk in (select pk_seq from donhang where npp_fk ='"+ this.nppId +"' and kbh_fk='"+ kenh +"' and ngaynhap > CONVERT(VARCHAR(10),dateadd(month,-3 ,dateadd(day,-day(getdate()),getdate())),120) and ngaynhap <= CONVERT(VARCHAR(10),dateadd(day,-day(getdate()),getdate()),120))";
		///convert(numeric(18),)
		String sql ="select sum(soluong) as num from donhang_sanpham where sanpham_fk ='"+ masp +"' and donhang_fk in (select pk_seq from donhang where npp_fk ='"+ this.nppId +"' and kbh_fk='"+ kenh +"' and ngaynhap >= '"+ this.ngaybatdau()+"' and ngaynhap <= '"+ this.ngayketthuc() +"') group by sanpham_fk";
		System.out.println("tinh so luong:"+sql);
		ResultSet rs = db.get(sql);
		if(rs!=null)
			try {
				rs.next();
				
				int num =Integer.parseInt(rs.getString("num"))/Integer.parseInt(tongsongay());
				//System.out.print("soluong tb:"+ num +" tong songay" +tongsongay());
			//	if(st == null)
		//			return "0";
				return num+"";
			} catch(Exception e) {
				
				e.printStackTrace();
			}
			finally {try {
				rs.close();
			} catch(Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}}
		return "0";
	}
	/*
	String ngayconlai()
	{
		//String sql ="select dbo.funDaysInMonth (month('"+ this.ngaydn +"'),year('"+ this.ngaydn +"')) - day('"+ this.ngaydn+"') as songay";
		
		String sql ="SELECT DATEDIFF(day, '"+ this.ngaydn +"',CONVERT(VARCHAR(25),DATEADD(dd,-(DAY(DATEADD(mm,1,getdate()))),DATEADD(mm,1,getdate())),101)) as songay";
		
		ResultSet rs = db.get(sql);
		try {
			rs.next();
			System.out.println(" songay co" + rs.getString("songay"));
			return rs.getString("songay");
		} catch(Exception e) {
		   e.printStackTrace();
		}
	   return "0";
	}
	*/
	public void init2() {
		getNppInfo();
	
		if(this.id.length()>0)
		{
			String sql ="select * from denghidathang where pk_seq ='"+ this.id +"'";
		//	////System.out.println(sql);
			ResultSet rs = db.get(sql);
			try {
				rs.next();
				this.ngaydn = rs.getString("ngaydat");
				this.nccId  = rs.getString("ncc_fk");
				this.dvkdId  = rs.getString("dvkd_fk");
				this.kbhId = rs.getString("kbh_fk");
			//	this.tanso = rs.getString("");
				
			sql = "select a.*,b.ten,b.ma from denghidathang_sp a, sanpham b where a.sanpham_fk = b.pk_seq and a.denghidathang_fk ='"+ this.id +"'";
			//////System.out.println(sql);
			int i=0;
			   ResultSet tb = db.get(sql);
			   IDanhsachsanpham sp;
			   while(tb.next())
			   {
				   sp = new Danhsachsanpham();
				   String Idsanpham = tb.getString("sanpham_fk");
				   sp.setIdSanPham(Idsanpham);
				   String masp = tb.getString("ma");
				    sp.setMasp(masp);
				    sp.setTensp(tb.getString("Ten"));
				    sp.setBantheongay(tb.getString("TBBAN"));
				    sp.setDonvi(tb.getString("DONVI"));
				    sp.setTonngay(tb.getString("TONNGAY"));
				    sp.setDongia(tb.getString("DONGIA"));
				    sp.setnppId(nppId);
				    sp.init();
				    String so ;
				     if(this.tanso.equals("2"))
				    	 so ="3";
				     else
				    	 so="6";
				     sp.setSoluong(Soluong(masp));
				    sp.setDukienban(tb.getString("DUKIEN"));
				    sp.setDenghi(tb.getString("DENGHIDAT"));
				    //float tongtien = Float.parseFloat(sp.getDenghi()) * Float.parseFloat(sp.getDongia());
				    sp.setTongtien(tb.getString("SOTIENBVAT"));
				    sp.setConlai(tb.getString("SOTIENBVAT"));
				    
				  	sp.setTondau(tb.getString("TONDAU"));
				  	sp.setTonCuoi(tb.getInt("toncuoi"));
				  	sp.setnppId(nppId);
				  	sp.init();
				  
				    //	sp.setTonicp("1");
				  	//	System.out.println("tonh:"+sp.getTonicp());
				  
				  	this.Danhsachsanpham.add(sp);
				  	i++;
				 }
			   tb.close();
			 //  ////System.out.println("so:"+this.Danhsachsanpham);
			} catch(Exception e) {
				e.printStackTrace();
			}
			finally{try {
				rs.close();
			} catch(Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}}
		}
	 
	}
	@Override
	public void DBclose() {
		try {
			if(this.dvkdIds != null)
				this.dvkdIds.close();
			if(this.kbhIds != null)
				this.kbhIds.close();
			if(this.ncc != null)
				this.ncc.close();
			if(this.db != null)
				this.db.shutDown();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
private void getNppInfo(){
		
		/*ResultSet rs = this.db.get("select a.pk_seq, a.ten, a.sitecode from nhaphanphoi a, nhanvien b where b.dangnhap = a.ma and b.pk_seq ='" + this.userId + "'");
		try{
			if (!(rs == null)){
				rs.next();
				this.nppId = rs.getString("pk_seq");
				this.nppTen = rs.getString("ten");
				this.sitecode = rs.getString("sitecode");
				
			}else{
				this.msg = "select a.pk_seq, a.ten, a.sitecode from nhaphanphoi a, nhanvien b where b.dangnhap = a.ma and b.pk_seq ='" + this.userId + "'";
				this.nppId = "";
				this.nppTen = "";
				this.sitecode = "";				
			}
			
		}catch(Exception e){}	
		
		*/
		//Phien ban moi
		geso.dms.distributor.util.Utility util=new geso.dms.distributor.util.Utility();
		this.nppId=util.getIdNhapp(this.userId);
		this.nppTen=util.getTenNhaPP();
		this.sitecode=util.getSitecode();
	}

public void setDanhsachsanpham(List<IDanhsachsanpham> danhsachsanpham) {
	
	this.Danhsachsanpham = danhsachsanpham;
}

public List<IDanhsachsanpham> getDanhsachsanpham() {
	
	return this.Danhsachsanpham;
}

public boolean save() {
   
	db=new dbutils();
	try{
	db.getConnection().setAutoCommit(false);	
	
	String sql ="insert into denghidathang values('"+ this.ngaydn +"','"+ this.tongtienbVAT +"','"+ this.userId +"','"+ this.userId +"','0','"+ this.nppId +"','"+ this.nccId +"','"+ this.vat +"','"+ this.tongtienaVAT +"','"+ this.dvkdId +"','0','"+ this.kbhId +"')";
	if(!db.update(sql))
	{
		geso.dms.center.util.Utility.rollback_throw_exception(this.db);
		this.msg = "Loi khi cap nhat bang bgbanlenpp_sanpham, " + sql;
		return false; 
	}
	else
	{
		String ma="";  
		String query = "select IDENT_CURRENT('denghidathang') as pk_seq";
		ResultSet rs = this.db.get(query);
		try 
		{
			rs.next();
			ma = rs.getString("pk_seq");
		} catch(Exception e) {
			e.printStackTrace();
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return false;
		}
		IDanhsachsanpham obj;
		int size = Danhsachsanpham.size();
		for(int i=0 ; i<size ; i++ )
		{ obj = new Danhsachsanpham();
		  obj = Danhsachsanpham.get(i);	
		 // if(Float.parseFloat(obj.getDenghi())>0)
		 // {
			  int SOTIENBVAT = Integer.parseInt(obj.getDongia().replace(",", ""))* Integer.parseInt(obj.getDenghi());
			  int VAT = (int)SOTIENBVAT* 10/1000;
			  int SOTIENAVAT = SOTIENBVAT + VAT;
			//  float 
			sql ="insert into denghidathang_sp(DENGHIDATHANG_FK,SANPHAM_FK,DENGHIDAT,DONVI,DONGIA,TONDAU,TONCUOI,TBBAN,TONNGAY,DUKIEN,SOTIENBVAT,VAT,SOTIENAVAT,dadathang)"+
			" values('"+ ma +"','"+ obj.getIdSanPham() +"','"+ obj.getDenghi() +"','"+ obj.getDonvi() +"','"+ obj.getDongia().replace(",","") +"','"+obj.geTondau()+"','"+obj.getTonCuoi()+"','"+ obj.getBantheongay()+"','"+ obj.getTonngay()+"','"+ obj.getDukienban() +"','"+ SOTIENBVAT +"','"+ VAT +"','"+ SOTIENBVAT +"','0')";
			if(!db.update(sql))
			{
				geso.dms.center.util.Utility.rollback_throw_exception(this.db);
				this.msg = "Loi khi cap nhat bang bgbanlenpp_sanpham, " + sql;
				return false; 
			}
		//  }
		  
		}
		db.getConnection().commit();
		db.getConnection().setAutoCommit(true);
	}
	
	}catch(Exception er)
	{	
		geso.dms.center.util.Utility.rollback_throw_exception(db);
		this.msg="Khong The Tao De Nghi Dat Hang " +er.toString();
		return false;
	}
	return true;
}



public void init3() {
	getNppInfo();
	
	if(this.id.length()>0)
	{
		String sql ="select * from denghidathang where pk_seq ='"+ this.id +"'";
		//////System.out.println(sql);
		ResultSet rs = db.get(sql);
		try {
			rs.next();
			this.ngaydn = rs.getString("ngaydat");
			this.nccId  = rs.getString("ncc_fk");
			this.dvkdId  = rs.getString("dvkd_fk");
			this.kbhId = rs.getString("kbh_fk");
		//	this.tanso = rs.getString("");
			
		sql = "select a.*,b.ten,b.ma from denghidathang_sp a, sanpham b where a.sanpham_fk = b.pk_seq and a.denghidathang_fk ='"+ this.id +"'";
		////System.out.println(sql);
		   ResultSet tb = db.get(sql);
		   IDanhsachsanpham sp;
		   while(tb.next())
		   {
			    sp = new Danhsachsanpham();
			    String Idsanpham = tb.getString("ma");
				   sp.setIdSanPham(Idsanpham);
				 
			    sp.setMasp(tb.getString("ma"));
			    sp.setTensp(tb.getString("Ten"));
			    sp.setBantheongay(tb.getString("TBBAN"));
			    sp.setDonvi(tb.getString("DONVI"));
			    sp.setTonngay(tb.getString("TONNGAY"));
			    sp.setDongia(tb.getString("DONGIA"));
			    sp.setSoluong(tb.getString("SANPHAM_FK"));
			    String so ;
			     if(this.tanso.equals("2"))
			    	 so ="3";
			     else
			    	 so="6";
			    sp.setDukienban(tb.getString("DUKIEN"));
			    sp.setDenghi(tb.getString("DENGHIDAT"));
			    //float tongtien = Float.parseFloat(sp.getDenghi()) * Float.parseFloat(sp.getDongia());
			   sp.setTongtien(tb.getString("SOTIENBVAT"));
			    sp.setConlai(tb.getString("SOTIENBVAT"));
			    
			  	sp.setTondau(tb.getString("TONDAU"));
			  	sp.setnppId(nppId);
			  	sp.init();
			  	this.Danhsachsanpham.add(sp);
			 }
		   ////System.out.println("so:"+this.Danhsachsanpham);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
boolean kiemtra()
{
	String sql ="select * from dondathang where denghidathang_fk ='"+ this.id +"'";
    ResultSet rs = db.get(sql);
    if(rs!= null)
    	return true;
    return false;
	}
String Soluong(String masp)
{
   String sql ="select soluong from dondathang_sp where sanpham_fk ='"+ masp +"' and dondathang_fk in (select pk_seq from dondathang where denghidathang_fk ='"+ this.id +"')";
   ////System.out.println(sql);
   ResultSet rs = db.get(sql);
   if(rs !=null)
   {  try {
	rs.next();
	 return rs.getString("soluong");
} catch(Exception e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
	  
   }
	return "0";
}

public String ngay(String ngaytinh)
{  int songay =0;
	String[] arr = ngaytinh.trim().split("-");
	int nam = Integer.parseInt(arr[0]);
	int thang = Integer.parseInt(arr[1]);
	int ngay =Integer.parseInt(arr[2]);
	if(thang==2)
	{
		if((nam % 4 == 0 &&  nam %100 != 0)|| (nam % 400 == 0))
		{
			songay = 29;
		}
		else
			songay =28;
	}
	else
	{
		if(thang ==3 )songay = 31;
		if(thang ==5 )songay = 31;
		if(thang ==7 )songay = 31;
		if(thang ==8 )songay = 31;
		if(thang ==10 )songay = 31;
		if(thang ==12 )songay = 31;
		if(thang ==4 )songay = 30;
		if(thang ==6 )songay = 30;
		if(thang ==9 )songay = 30;
		if(thang ==11 )songay = 30;
		
		
	}
	ngay = songay - ngay;
	System.out.println("so ngay:" + ngay);
	return ngay+"";
}

String ngaybatdau()
{
	String[] arr = this.ngaydn.trim().split("-");
	int nam = Integer.parseInt(arr[0]);
	int thang = Integer.parseInt(arr[1]);
	int ngay =Integer.parseInt(arr[2]);
	int thangbd = thang - 4;
	if(thangbd < 0)
	{
		thangbd = -thangbd;
		nam = nam -1;
	}
	if(thangbd<10)
		return nam+"-0"+ thangbd +"-"+ "01";
  return nam+"-"+ thangbd +"-"+ "01";
}
String ngayketthuc()
{
	String[] arr = this.ngaydn.trim().split("-");
	int nam = Integer.parseInt(arr[0]);
	int thang = Integer.parseInt(arr[1]);
	int ngay =Integer.parseInt(arr[2]);
	int thangbd = thang - 1;
	if(thangbd < 0)
	{
		thangbd = -thangbd;
		nam = nam -1;
	}
   String thang1=""; 
	if(thangbd<10)
		thang1 ="0"+thang;
	else
		thang1 = thang+"";
	ngay = Integer.parseInt(ngay(nam +"-"+ thangbd +"-"+"0"));
	if(ngay <10)
  return nam+"-"+ thangbd +"-0"+ngay ;
	return nam+"-"+ thangbd +"-"+ngay ;
}

String tongsongay()
{
	String sql ="select DATEDIFF ('"+ this.ngaybatdau()+"','"+ this.ngayketthuc()+"') as songay";
	System.out.println("tinh so ngay:"+ sql);
	ResultSet rs = db.get(sql);
	 if(rs !=null)
	   {  try {
		rs.next();
		 return rs.getString("songay");
	} catch(Exception e) {
		e.printStackTrace();
	}
	   }
   return "1";
}

public void init4() {
	getNppInfo();
	
	if(this.id.length()>0)
	{
		String sql ="select * from denghidathang where pk_seq ='"+ this.id +"'";
	//	////System.out.println(sql);
		ResultSet rs = db.get(sql);
		try {
			rs.next();
			this.ngaydn = rs.getString("ngaydat");
			this.nccId  = rs.getString("ncc_fk");
			this.dvkdId  = rs.getString("dvkd_fk");
			this.kbhId = rs.getString("kbh_fk");
		//	this.tanso = rs.getString("");
			
		sql = "select a.*,b.ten,b.ma from denghidathang_sp a, sanpham b where a.sanpham_fk = b.pk_seq and a.denghidathang_fk ='"+ this.id +"'";
		//////System.out.println(sql);
		int i=0;
		   ResultSet tb = db.get(sql);
		   IDanhsachsanpham sp;
		   while(tb.next())
		   {
			   sp = new Danhsachsanpham();
			   String Idsanpham = tb.getString("ma");
			   sp.setIdSanPham(Idsanpham);
			 
			   String masp = tb.getString("ma");
			    sp.setMasp(masp);
			    sp.setTensp(tb.getString("Ten"));
			    sp.setBantheongay(tb.getString("TBBAN"));
			    sp.setDonvi(tb.getString("DONVI"));
			    sp.setTonngay(tb.getString("TONNGAY"));
			    sp.setDongia(tb.getString("DONGIA"));
			    sp.setnppId(nppId);
			    sp.init();
			    String so ;
			     if(this.tanso.equals("2"))
			    	 so ="3";
			     else
			    	 so="6";
			     sp.setSoluong(ban[i]);
			    sp.setDukienban(tb.getString("DUKIEN"));
			    sp.setDenghi(tb.getString("DENGHIDAT"));
			    //float tongtien = Float.parseFloat(sp.getDenghi()) * Float.parseFloat(sp.getDongia());
			    sp.setTongtien(tb.getString("SOTIENBVAT"));
			    sp.setConlai(tb.getString("SOTIENBVAT"));
			    
			  	sp.setTondau(tb.getString("TONDAU"));
			  	sp.setnppId(nppId);
			  	sp.init();
			  
			    //	sp.setTonicp("1");
			  	//	System.out.println("tonh:"+sp.getTonicp());
			  
			  	this.Danhsachsanpham.add(sp);
			  	i++;
			 }
		   tb.close();
		 //  ////System.out.println("so:"+this.Danhsachsanpham);
		} catch(Exception e) {
			e.printStackTrace();
		}
		finally{try {
			rs.close();
		} catch(Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}}
	}
}


public boolean savedathang() {
/*for(int i=0;i<dongia.length;i++)
	{
		////System.out.println("dongia:"+ dongia[i]);
	}
	for(int i=0;i<ban.length;i++)
	{
		////System.out.println("soluong:"+ ban[i]);
	}
	*/
	//ngaydn se la ngay dat hang
	/* if(kiemtra())
	{
		String sql ="update dondathang set nguoisua ='"+ this.userId+"' where denghidathang_fk ='"+ this.id +"'";
		//////System.out.println(sql);
		 if(!db.update(sql))
		 {
				this.msg = "Loi khi cap nhat bang dondathang, " + sql;
				return false; 
			
		 }
		  sql ="delete from dondathang_sp where dondathang_fk  in (select pk_seq from dondathang where denghidathang_fk ='"+ this.id +"')";
		//  ////System.out.println(sql);
		  if(!db.update(sql))
		  {     geso.dms.center.util.Utility.rollback_throw_exception(this.db);
				this.msg = "Loi khi cap nhat bang dondathang, " + sql;
				return false; 
			
		  }
	}
	else
	{
	String sql ="insert into dondathang values('"+ this.ngaydn +"','"+ this.tongtienbVAT +"','"+ this.userId +"','"+ this.userId +"','0','"+ this.nppId +"','"+ this.nccId +"','"+ this.vat +"','"+ this.tongtienaVAT +"','"+ this.dvkdId +"','"+ this.id +"','"+ this.kbhId +"','0')";
	//////System.out.println(sql);
	if(!db.update(sql))
	{
		geso.dms.center.util.Utility.rollback_throw_exception(this.db);
		this.msg = "Loi khi cap nhat bang dondathang, " + sql;
		return false; 
	}
	}
	//////System.out.println("so luong san pham:" + masp.length);
	
	 String ma="";  
		String query = "select IDENT_CURRENT('dondathang') as pk_seq";
		//////System.out.println(query);
		ResultSet rs = db.get(query);
		try {
			rs.next();
			ma = rs.getString("pk_seq");
       //   ////System.out.println("ma:"+ma);
		} catch (Exception e) {
       // ////System.out.print("loi:" + e);
		}
	// ////System.out.println("iam here");
	 
	/* for(int i =0;i< 2;i++)
	 {
		  ////System.out.println("so luong:");
			
	 }*/
	/*	for(int i = 0;i< masp.length;i++)
		{
			if(Float.parseFloat(this.ban[i])>0)
			{
			 // ////System.out.println("so luong:"+this.ban[i] +" dongia: "+this.dongia[i]);
				float sotien = Float.parseFloat(this.ban[i])* Float.parseFloat(this.dongia[i]);
				float vat = sotien * 10/100;
				float sotienvat = sotien + vat;
				//////System.out.println("iam here1");
				
		    String  sql =" insert into dondathang_sp values('"+ ma +"','"+ masp[i]+"','"+ this.ban[i]+"','"+ this.donvi[i]+"','"+ this.dongia[i]+"','"+ sotien +"','"+ vat +"','"+ sotienvat +"')";
		  //	////System.out.println(sql);

		    if(!db.update(sql))
		  	{
		  		geso.dms.center.util.Utility.rollback_throw_exception(this.db);
		  		this.msg = "Loi khi cap nhat bang dondathang_sp " + sql;
		  		return false; 
		  	}
			}
		}
		*/
	return true;
}

public String getTrangthai() {
	
	return this.trangthai;
}

public void setTrangthai(String trangthai) {

	this.trangthai = trangthai;
}
@Override
public void InitDeNghiByID(String id) {
	// TODO Auto-generated method stub
		String query="select  sp.ma,bg_sp.sanpham_fk,sp.ten,dvdl.donvi,bg_sp.giamuanpp ,isnull(dn_sp.tondau,0) as tondau, "+
		" isnull(dn_sp.toncuoi,0)as toncuoi,isnull(dn_sp.tbban,0) as tbban_ngay,isnull(dn_sp.tonngay,0) as sltb_ngay,isnull(dn_sp.dukien,0) as dukien,"+
		" isnull(dn_sp.denghidat,0) as soluongdat,isnull(ddh_dn.soluong,0) as soluongdat  from  banggiamuanpp bg "+ 
		" inner join banggiamuanpp_npp bg_npp on bg.pk_seq=bg_npp.banggiamuanpp_fk "+
		" inner join bgmuanpp_sanpham bg_sp on bg_sp.bgmuanpp_fk=bg.pk_seq "+ 
		" inner join sanpham sp on bg_sp.sanpham_fk=sp.pk_seq "+
		" inner join donvidoluong dvdl on dvdl.pk_Seq=sp.dvdl_fk "+
		" left join denghidathang_sp dn_sp on dn_sp.sanpham_fk=bg_sp.sanpham_fk  and dn_sp.denghidathang_fk= "+ id+ 
		"	left join (select sanpham_fk,soluong from dondathang_sp  dh_sp inner join dondathang ddh on  "+
		" ddh.pk_seq=dh_sp.dondathang_fk where ddh.denghidathang_fk="+this.id+" ) as ddh_dn on ddh_dn.sanpham_fk= dn_sp.sanpham_fk "+
		" where bg_npp.npp_fk="+this.nppId+" and bg.dvkd_fk="+this.dvkdId+" and bg.kenh_fk="+this.dvkdId+" and bg_sp.trangthai='1'  ";
		dbutils db=new dbutils();
		db.get(query);
		  System.out.println("Cau Lenh Lay De Nghi: "+query);
			ResultSet rs_getsolieu=	db.get(query);
			if(rs_getsolieu!=null){
				try{
				while(rs_getsolieu.next()){
				IDanhsachsanpham	sp=new Danhsachsanpham();
					sp.setBantheongay(rs_getsolieu.getString("sltb_ngay"));//so luong ban trung binh tung ngay trong 3 thang
					sp.setSL_BanTuanTruoc(rs_getsolieu.getInt("sltuan_truoc"));//so luong hang ban duoc trong tuan truoc
					sp.setMasp(rs_getsolieu.getString("ma"));//set ma san pham
					sp.setIdSanPham(rs_getsolieu.getString("sanpham_fk"));
					sp.setTensp(rs_getsolieu.getString("ten"));
					sp.setDonvi(rs_getsolieu.getString("donvi"));
					sp.setDongia(rs_getsolieu.getString("dongia"));
					sp.setTonCuoi(rs_getsolieu.getInt("toncuoi"));
					//Ton ngay::    Số ngày tồn kho của từng SKU, tính theo công thức: Ton cuoi/ TTBan theo ngay
					int tonngay=0;
					try{
						tonngay= rs_getsolieu.getInt("toncuoi")/rs_getsolieu.getInt("sltb_ngay");
					}catch(Exception er){
						tonngay=0;
					}
					sp.setTonngay(Integer.toString(tonngay));
					//Du Kien Ba Se Bang Trung Binh Ban Theo Ngay *102 % songaytrong tuan
					int tanso=1;
					try{
					 tanso=Integer.parseInt(this.getTanso());
					}catch(Exception er){
						tanso=1;
					}
					int soluongdukienban=rs_getsolieu.getInt("sltb_ngay")*(102/100)* (6/tanso);
					//TB Ban/ ngay * 18 + Du Kien Ban – Ton Cuoi
					int soluongdenghi= rs_getsolieu.getInt("sltb_ngay")*18+ soluongdukienban- rs_getsolieu.getInt("toncuoi");
					sp.setDenghi(Integer.toString(soluongdenghi));
					sp.setDukienban(Integer.toString(soluongdukienban));
					double tongtien=soluongdenghi * rs_getsolieu.getDouble("dongia");
					sp.setTongtien(Double.toString(tongtien));
					Danhsachsanpham.add(sp);
					
				}
				}catch(Exception er){
					System.out.println("Loi Denghidathangnpp.java,Lien He Voi KHOAND ... ERROR :" + er.toString());
				}

			}

	}
}
