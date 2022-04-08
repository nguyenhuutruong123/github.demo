package geso.dms.center.beans.hdnhaphang.imp;


import geso.dms.center.beans.hdnhaphang.IHDnhaphang;
import geso.dms.distributor.db.sql.dbutils;
import java.io.Serializable;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;


public class HDnhaphang implements IHDnhaphang, Serializable
{
	private static final long serialVersionUID = -9217977546733610214L;
	String userId;
	String nppId;
	String nppTen;
	String id;
	String ngaychungtu;
	String sohoadon;
	String ngaynhaphang;	
	String nguoitao;
	String nguoisua;	
	String nccId;
	String dangnhap;
	ResultSet ncc;
	ResultSet dvkdIds;
	String dvkdId;
	ResultSet kbhIds;
	String kbhId;
	ResultSet khoIds;
	String khoId;
	String Gsbhid;
	
	ResultSet RSGSBH;
	String tongtienaVAT;
	String tongvat;
	String tongtienbVAT;

//	String ddhId;
//	String[] spId;
	String[] masp;
	String[] tensp;
	String[] sl;
	String[] giamua;
	String[] donvi;
	String[] tienbVAT;
	String[] tienaVAT;
	String[] vat;
	String size;
	String msg;
	dbutils db;
	public HDnhaphang()
	{
		this.db = new dbutils();
		this.userId = "";
		this.nppId = "";
		this.nppTen = "";
		this.id = "";
		this.ngaychungtu = "";
		this.sohoadon ="";
		this.ngaynhaphang = "";
		this.nguoitao = "";
		this.nguoisua = "";	
		this.nccId = "";
		this.dvkdId = "";
		this.kbhId = "";
		this.tongtienaVAT = "0";
		this.tongvat  = "0";
		this.tongtienbVAT  = "0";
		this.msg ="";
		
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
		/*geso.dms.distributor.util.Utility util=new geso.dms.distributor.util.Utility();
		this.nppId = util.getIdNhapp(this.userId);
		this.nppTen = util.getTenNhaPP();*/
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
	
	
	public String getNgaychungtu()
	{
		return this.ngaychungtu;
	}
	
	public void setNgaychungtu(String ngaychungtu)
	{
		this.ngaychungtu = ngaychungtu;
	}
		
	public String getSohoadon()
	{
		return this.sohoadon;
	}
	
	public void setSohoadon(String sohoadon)
	{
		this.sohoadon = sohoadon;
	}

	public String getNgaynhaphang()
	{
		return this.ngaynhaphang;
	}
	
	public void setNgaynhaphang(String ngaynhaphang)
	{
		this.ngaynhaphang = ngaynhaphang;
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


	public String getKhoId()
	{
		return this.khoId;
	}
	
	public void setKhoId(String khoId)
	{
		this.khoId = khoId;
	}


	public ResultSet getKbhIds()
	{
		return this.kbhIds;
	}

	public void setKbhIds(ResultSet kbhIds)
	{
		this.kbhIds = kbhIds;
	}
	
	public ResultSet getKhoIds()
	{
		return this.khoIds;
	}

	public void setKhoIds(ResultSet khoIds)
	{
		this.khoIds = khoIds;
	}

	public String getTongtienbVAT()
	{
		return this.tongtienbVAT;
	}

	public void setTongtienbVAT(String tongtienbVAT)
	{
		this.tongtienbVAT = tongtienbVAT;
	}
	
	public String getTongVat()
	{
		return this.tongvat;
	}
	
	public void setTongVat(String tongvat)
	{
		this.tongvat = tongvat;
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

	public String[] getGiamua()
	{
		return this.giamua;
	}
	
	public void setGiamua(String[] giamua)
	{
		this.giamua = giamua;
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

	public String getMessage() 
	{
		return this.msg;
	}
	
	public void setMessage(String msg) 
	{
		this.msg = msg;
	}

	public String getDangnhap()
	{
		return this.dangnhap;
	}
	
	public void setDangnhap(String dangnhap)
	{
		this.dangnhap = dangnhap;
	}
	

	public void init()
	{		
		Hashtable ht = new Hashtable<String, String>();
		String query; 	
		ResultSet rs = null;
		String trangthai="0";
		try
		{
			String sql = "select count(nhaphang_fk) as num from hdnhaphang_sp where nhaphang_fk='"+ this.id + "'";
			rs= this.db.get(sql);		
			rs.next();
			this.size = rs.getString("num");		
			int i = new Integer(this.size).intValue();			
			this.masp = new String[i];			
			this.tensp = new String[i];
			this.sl = new String[i];
			this.donvi = new String[i];
			this.giamua = new String[i];
			this.tienbVAT = new String[i];
			this.vat = new String[i];
			this.tienaVAT = new String[i];
			
			query = "select ncc_fk, dvkd_fk, kbh_fk,npp.ten as tenNPP, chungtu, ngaychungtu, ngaynhan, sotienbvat, vat, sotienavat, kho_fk, hd.trangthai from hdnhaphang hd inner join nhaphanphoi npp on npp.pk_seq = hd.npp_fk where hd.pk_seq='"+ this.id + "' order by ngaychungtu,hd.trangthai";
			System.out.println("Init HDNHAPHANG : "+query);
			//System.out.println("get ngay nhan hang  sai :"+query);
			rs = this.db.get(query);
			rs.next();
			trangthai=rs.getString("trangthai");
			this.ngaychungtu = rs.getString("ngaychungtu");
			this.dvkdId = rs.getString("dvkd_fk");
			this.nccId = rs.getString("ncc_fk");
			this.kbhId = rs.getString("kbh_fk");
			this.tongtienaVAT = rs.getString("sotienavat"); 
			this.tongtienbVAT = rs.getString("sotienbvat");
			this.tongvat = rs.getString("vat");
			this.khoId = rs.getString("kho_fk");
			if(rs.getString("trangthai").equals("1"))
			this.sohoadon =rs.getString("chungtu");
			this.ngaynhaphang = rs.getString("ngaynhan");
			this.nppTen = rs.getString("tenNPP");
			
						
			query ="select a.sanpham_fk as ma, b.ten as tensp, a.soluong as sl, a.donvi, a.gianet, a.tienbvat, a.vat, a.tienavat from hdnhaphang_sp a, sanpham b where a.sanpham_fk = b.ma and a.nhaphang_fk='"+ this.id +"' order by a.sanpham_fk";
			////System.out.printlnln(query);
			//this.msg = query;
			rs = this.db.get(query);				
			int m =0;
//			this.msg = query;
			while(rs.next()){
				this.masp[m] = rs.getString("ma");
				this.tensp[m]= rs.getString("tensp");
				this.sl[m] = rs.getString("sl");
				this.donvi[m] = rs.getString("donvi");
				this.giamua[m] = rs.getString("gianet");
				this.tienbVAT[m] = rs.getString("tienbvat");
				this.tienaVAT[m] = rs.getString("tienavat");
				this.vat[m] = rs.getString("vat");
				m++;
			}
			this.size = "" + m;
			System.out.println("Size : "+this.size);
			query = "select pk_seq as nccId, ten as nccTen from nhacungcap";
			this.ncc = this.db.get(query);

			this.dvkdIds = this.db.get("select pk_seq as dvkdId, donvikinhdoanh as dvkd from donvikinhdoanh where trangthai='1'");

//			this.msg = query;
			query = "select c.pk_seq as kbhId, c.diengiai as kbh from nhaphanphoi a, nhapp_kbh b, kenhbanhang c where a.pk_seq = b.npp_fk and b.kbh_fk = c.pk_seq";
			////System.out.printlnln(query);
			this.kbhIds = this.db.get(query);

		
			
			query="select nhaphang_fk from hdnhaphang_sp where nhaphang_fk='"+this.id+"' and scheme like '%DG%'";
			
			ResultSet rscheck=this.db.get(query);
			if(rscheck!=null){
				if(rscheck.next()){
					query = "select distinct a.kho_fk as khoId, b.ten as kho from nhapp_kho a, kho b where a.kho_fk=b.pk_seq and a.kbh_fk='" + this.kbhId + "' and b.ten='DG001'";

				}else{
					query = "select distinct a.kho_fk as khoId, b.ten as kho from nhapp_kho a, kho b where a.kho_fk=b.pk_seq and a.kbh_fk='" + this.kbhId + "' and  b.ten <> 'DG001'";

				}
				rscheck.close();
			}
			
			System.out.println(query);
			this.khoIds = this.db.get(query);
			
			query="select pk_seq,ten from giamsatbanhang inner join nhapp_giamsatbh a on a.gsbh_fk=pk_seq  where trangthai=1 and a.ngayketthuc >='"+this.getDateTime()+"'";
			this.RSGSBH=db.get(query);
			
			
		}
		catch(Exception e){}
		finally{try {
			if(rs != null)
				rs.close();
		} catch (Exception e2) {
			
		}}
		/*if(trangthai.equals("0")){
			if(CheckNgayNhanHang() == false)
			{
				this.msg = "Ngay chung tu phai sau ngay khoa so gan nhat";
			}
		}*/
	}

	String kenhbanhang()
	{
		String st ="";
		String sql ="select distinct kbh_fk from hdnhaphang where pk_seq ='"+ this.id +"'";
		ResultSet rs = db.get(sql);
		try {
			rs.next();
			st = rs.getString("kbh_fk");
		
			} catch(Exception e) {
			e.printStackTrace();
			}
			finally{try {
				if(rs != null)
					rs.close();
			} catch (Exception e2) {
				
			}}
		return st;
	}
	/*public boolean UpdateNhaphangNpp() 
	{ 	getNppInfo();
		if(CheckNgayNhanHang() == false)
		{
			return false;
		}
		ResultSet rs = null;
		try
		{    //  //System.out.printlnln("toi dang o day------------------------------------ bat dau");
		
			this.db.getConnection().setAutoCommit(false);	
			String query = "select count(pk_seq) as num from hdnhaphang where chungtu='" + this.sohoadon + "' and trangthai='0' and pk_seq = '" + this.id + "' and ngaychungtu <= '"+ this.getDateTime() + "'";
			////System.out.printlnln("kiem tra:"+query);
			rs = this.db.get(query);
			
			rs.next();
			if (rs.getString("num").equals("1")){
				
				query="select nhaphang_fk from hdnhaphang_sp where nhaphang_fk='"+this.id+"' and scheme like '%DG%'";
				
				ResultSet rscheck=this.db.get(query);
				if(rscheck!=null){
					if(rscheck.next()){
						query="select pk_seq from kho where ten ='DG001'";
						ResultSet rskho=this.db.get(query);
						if(rskho!=null){
							if(rskho.next()){
								this.khoId=rskho.getString("pk_seq");
							}
							rskho.close();
						}
					}
					rscheck.close();
				}
				System.out.println("Kho Nek Cac Bac : "+this.khoId);
				query ="select b.pk_seq as spId, a.sanpham_fk as ma, b.ten as tensp, a.soluong as sl, "+
				" a.donvi, a.gianet, a.tienbvat, a.vat, a.tienavat,nh.npp_fk,nh.kbh_fk from hdnhaphang_sp a inner join  sanpham b "+
				" on a.sanpham_fk = b.ma inner join hdnhaphang nh on nh.pk_seq=a.nhaphang_fk "+
				" where a.nhaphang_fk='"+this.id+"' and a.soluong >0 order by a.sanpham_fk ";
				rs = this.db.get(query);
				////System.out.println(query);
				
				String spId = "";
				float gia = 0;
				long sl = 0;
				
				ResultSet rs_getsp_kho;
				while(rs.next())
				{
					spId = rs.getString("spId");
					gia = Float.parseFloat(rs.getString("gianet"));
					sl = Math.round(rs.getDouble("sl"));
					
					//query = "update nhapp_kho set giamua = (soluong*giamua + " + sl*gia + ")/(soluong+ " + sl + "), soluong = soluong + " + sl + ", available=available+ " + sl + " where sanpham_fk='" + spId + "' and npp_fk='" + this.nppId + "' and kho_fk='"+ this.khoId +"' and kbh_fk ='"+ kenhbanhang() +"'";
					if(sl>0)
					{
						query="select *  from nhapp_kho where sanpham_fk='" + spId + "' and npp_fk='" +rs.getString("npp_fk")+ "' and kho_fk='"+ this.khoId +"' and kbh_fk ='"+ rs.getString("kbh_fk") +"'";
						 rs_getsp_kho=this.db.get(query);
						 if( rs_getsp_kho!=null){
							if(rs_getsp_kho.next()){
								
								query = "update nhapp_kho set giamua = (soluong*giamua + " + sl*gia + ")/(soluong+ " + sl + "), soluong = soluong + " + sl + ", available=available+ " + sl + " where sanpham_fk='" + spId + "' and npp_fk='" + rs.getString("npp_fk")+ "' and kho_fk='"+ this.khoId +"' and kbh_fk ='"+ rs.getString("kbh_fk") +"'";
								////System.out.println("Update "+query);
								if(!this.db.update(query)){
									geso.dms.center.util.Utility.rollback_throw_exception(db);
									this.msg = "Vui long chon kho nhan hang";
									//System.out.println(query);
									return false;
								}
							}else{
								
								query="insert into nhapp_kho (kho_fk,npp_fk,sanpham_fk,soluong,booked,available,giamua,kbh_fk) "+
										"values('"+this.khoId+"','"+rs.getString("npp_fk")+"','"+spId+"','0','0','0','0','"+rs.getString("kbh_fk")+"')";
								//System.out.println("INsert san pham "+query);
								this.msg = "Khong Lay Duoc San Pham [ " +rs.getString("tensp")  +" ] Trong Kho,Vui Long Lien He Voi Admin De Duoc Cap Nhat Hang Vao Kho,Sau Do Nhan Hang";
								if(!db.update(query)){
									
									geso.dms.center.util.Utility.rollback_throw_exception(db);
									this.msg="Khong Nhap Hang Duoc,Vui Long Thu Lai.Loi :"+ query;
									return false; 
										
								}
								query = "update nhapp_kho set giamua = (soluong*giamua + " + sl*gia + ")/(soluong+ " + sl + "), soluong = soluong + " + sl + ", available=available+ " + sl + " where sanpham_fk='" + spId + "' and npp_fk='" + rs.getString("npp_fk") + "' and kho_fk='"+ this.khoId +"' and kbh_fk ='"+ rs.getString("kbh_fk") +"'";
								//System.out.println(query);
								if(!this.db.update(query)){
									geso.dms.center.util.Utility.rollback_throw_exception(db);
									this.msg = "Vui long chon kho nhan hang";
									//System.out.println(query);
									return false;
								}
							}
							rs_getsp_kho.close();
						 }else{
							 this.msg = "Khong Lay Duoc San Pham [ " +rs.getString("tensp")  +" ] Trong Kho,Vui Long Lien He Voi Admin De Duoc Cap Nhat Hang Vao Kho,Sau Do Nhan Hang";
							 geso.dms.center.util.Utility.rollback_throw_exception(db);
								return false; 
						 }
					}
					
				}
				query="INSERT INTO LOGNHAP (NHAPHANG_FK,USERID,NPP_FK) VALUES('"+this.id+"','"+this.nguoisua+"','"+this.nppId+"')";
				if(!this.db.update(query)){
					
					this.msg="Khong the Cap Nhat Nhap Hang ,Vui Long Thu Lai : "+ query;
					geso.dms.center.util.Utility.rollback_throw_exception(db);
					return false;
					
				}
				query = "update nhaphang set gsbh_fk='"+this.Gsbhid+"',trangthai='1',ngaynhan ='"+ this.ngaynhaphang +"', kho_fk='"+ this.khoId + "' where pk_seq ='" + id + "'";
				if(!this.db.update(query)){
					
					this.msg="Khong the Cap Nhat Nhap Hang ,Vui Long Thu Lai : "+ query;
					geso.dms.center.util.Utility.rollback_throw_exception(db);
					return false;
					
				}
				
			}else{
				this.msg="So Hoa Don Cua Don Hang Khong Chinh Xac, Vui Long Nhap Lai";
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				return false;
				
			}
						
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);	
		}catch(Exception e){
			this.msg="Qua Trinh Nhan Hang Bi Loi,Vui Long Kiem Tra Lai.Loi :"+ e.toString();
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return false;
		}
		finally{try {
			
			if(rs != null)
				rs.close();
			} catch (Exception e2) {
			
			}
			}
		return true;
	}
	
	private boolean CheckNgayNhanHang()
	{
		Utility util = new Utility();
		String ngayksgn = util.ngaykhoaso(this.nppId);
		
		////System.out.println("\nNgay khoa so gan nhat la: " + this.ngaychungtu + "\n");
		
		if(ngayksgn.equals(""))
			return false;
		
		String[] ngayks = ngayksgn.split("-");
		String[] ngayct = this.ngaychungtu.split("-");
		
		////System.out.println("\nNgay chung tu la: " + this.ngaychungtu + "\n");
		
		Calendar c1 = Calendar.getInstance(); //new GregorianCalendar();
		Calendar c2 = Calendar.getInstance(); //new GregorianCalendar();
		
		//NGAY KHOA SO
		
		c1.set(Integer.parseInt(ngayks[0]), Integer.parseInt(ngayks[1]) - 1, Integer.parseInt(ngayks[2]));
		//

		//NGAY CHỨNG TỪ
		
		c2.set(Integer.parseInt(ngayct[0]), Integer.parseInt(ngayct[1]) - 1, Integer.parseInt(ngayct[2]));

		
			c1.add(Calendar.DATE, 1);//ngay nhan hang bang ngay kho so them 1 ngay
			//phep tinh ngay nhan hang - ngay khoa so
			
			long songay = ( c1.getTime().getTime() - c2.getTime().getTime()) / (24 * 3600 * 1000);
		   
			if(songay < 0) //ngay chung tu khong duoc nho hon hoac bang ngay khoa so gan nhat 
			{
				this.msg = "Ngay Nhan Hang Phai Lon Hon Hoac Bang Ngay Chung Tu.";
				return false;
			}

			String month = "";
			if(c1.get(Calendar.MONTH) < 9){
				month = "0" + Integer.toString(c1.get(Calendar.MONTH) + 1);
			}else{
				month = Integer.toString(c1.get(Calendar.MONTH) + 1);
			}
			String date = "";
			if(c1.get(Calendar.DATE) < 10){
				date = "0" + Integer.toString(c1.get(Calendar.DATE));
			}else{
				date=Integer.toString(c1.get(Calendar.DATE));
			}
			this.ngaynhaphang = Integer.toString(c1.get(Calendar.YEAR)) + "-" + month + "-" + date;
			
			////System.out.println("\nNgay nhap la: " + this.ngaynhaphang + "\n");
		
		return true;
	}*/
	
	/*private void getNppInfo(){
		
	
		geso.dms.distributor.util.Utility util=new geso.dms.distributor.util.Utility();
		this.nppId=util.getIdNhapp(this.userId);
		System.out.println("NppID : "+this.nppId);
		this.nppTen=util.getTenNhaPP();
		this.dangnhap = util.getDangNhap();
		//this.sitecode=util.getSitecode();
	}*/
	
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


	public void DBclose(){
		try {
			
			if(this.dvkdIds != null)
				this.dvkdIds.close();
			if(this.kbhIds != null)
				this.kbhIds.close();
			if(this.khoIds != null)
				this.khoIds.close();
			if(this.ncc != null)
				this.ncc.close();
			if(this.RSGSBH != null)
				this.RSGSBH.close();
			 masp=null;
			 tensp=null;
			sl=null;
		 giamua=null;
		 donvi=null;
			 tienbVAT=null;
			 tienaVAT=null;
			 vat=null;
				if(this.db != null){
					this.db.shutDown();
				}
			 
		} catch (Exception e) {
			
		}
	
	}

	public String getNgaynhanhang() 
	{
		return this.ngaynhaphang;
	}
	
	public void setNgaynhanhang(String ngaynhanhang) 
	{
		this.ngaynhaphang = ngaynhanhang;
	}
	
	public ResultSet getGSBH() {
		
		return this.RSGSBH;
	}
	
	public void setGsbhId(String gsbhid) {
		
		this.Gsbhid=gsbhid;
	}
	
	public String getGsbhId() {
		
		return this.Gsbhid;
	}
	

}

