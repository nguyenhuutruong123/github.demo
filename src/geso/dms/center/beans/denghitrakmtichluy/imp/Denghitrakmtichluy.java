package geso.dms.center.beans.denghitrakmtichluy.imp;

import geso.dms.center.db.sql.dbutils;
import geso.dms.center.beans.denghitrakmtichluy.*;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

public class Denghitrakmtichluy implements IDenghitrakmtichluy, Serializable{
	private static final long serialVersionUID = -9217977546433610214L;
    String userId;
    String userName;
    String nppId;
    String nppTen;
    String ctkmId;
    String id;
    String tungay;
    String denngay;
    ResultSet ctkmIds;
	dbutils db;
	String Msg;
	
	public Denghitrakmtichluy()
	{
		 this.userId="";
	     this.nppId="";
	     this.nppTen="";
		 this.ctkmId ="";
		 this.id = "";
		 this.tungay = "";
		 this.denngay = "";
		 this.Msg ="";
		 this.db = new dbutils();
		
	}
	
	public String getUserId() {
		
		return this.userId;
	}

	public void setUserId(String userId) {
		
		this.userId = userId;
	}

	public String getUserName() {
		
		return this.userName;
	}

	public void setUserName(String userName) {
		
		this.userName = userName;
	}
	
	public String getNppId() {
		
		return this.nppId;
	}

	
    public void setNppId(String nppId) {
		
	   this.nppId = nppId;	
	}

	
	private void getNppInfo(){
		geso.dms.distributor.util.Utility util=new geso.dms.distributor.util.Utility();
		this.nppId=util.getIdNhapp(this.userId);
		this.nppTen=util.getTenNhaPP();
		//this.sitecode=util.getSitecode();
	
	}

	
	public String getNppTen() {
		
		return this.nppTen;
	}
	

	public void setNppTen(String nppTen) {
		
		this.nppTen = nppTen;
	}

	public String getTungay() {
		
		return this.tungay;
	}
	

	public void setTungay(String tungay) {
		
		this.tungay = tungay;
	}

	public String getDenngay() {
		
		return this.denngay;
	}
	

	public void setDenngay(String denngay) {
		
		this.denngay = denngay;
	}

	public void Denghitrakmtichluy()
	{	
		String query = "";
		//query = "SELECT TUNGAY, DENNGAY FROM CTKHUYENMAI WHERE PK_SEQ = '" + this.ctkmId + "'";
		query = "SELECT ngayds as TUNGAY, ngayktds as DENNGAY FROM NGAYTINHDSKM WHERE ctkm_fk = '" + this.ctkmId + "'";
		System.out.println(query);
		ResultSet rs = this.db.get(query);
        if(rs!=null)
		try 
		{
			rs.next();
			this.tungay = rs.getString("tungay");
			this.denngay = rs.getString("denngay");
			//rs.close();
		
			query = "SELECT PK_SEQ AS ID, CTKM_FK FROM DENGHITRAKMTICHLUY WHERE trangthai ='1' and CTKM_FK = '" + this.ctkmId + "'"; 
			System.out.println(query);
			ResultSet rs1 = this.db.get(query);
			
			if(rs1.next())	{
				System.out.println("I am here");
				
				this.id = rs1.getString("ID");
			//	rs1.close();
			}else{
				query = "INSERT INTO DENGHITRAKMTICHLUY(CTKM_FK, NGAYDENGHI, TRANGTHAI, NGAYSUA, NGUOITAO, NGUOISUA) " +
						"VALUES ('" + this.ctkmId + "','" + this.getDateTime() + "','0', '"+ this.getDateTime()+ "','" + this.userId + "','" + this.userId + "')";
				System.out.println(query);
				if(db.update(query)){
					query = "SELECT IDENT_CURRENT('DENGHITRAKMTICHLUY') AS Id";
					rs = db.get(query);
					rs.next();
					this.id = rs.getString("Id");
				}
			}
		
			query = "SELECT DKKMTL.NPP_FK, DKKMTL_KH.KHACHHANG_FK AS KHID, isnull(DKKMTL_KH.SOXUAT,0) AS SOXUAT " +
					   "FROM DANGKYKM_TICHLUY_KHACHHANG DKKMTL_KH " +
					   "INNER JOIN DANGKYKM_TICHLUY DKKMTL ON DKKMTL.PK_SEQ = DKKMTL_KH.DKKMTICHLUY_FK " +
					   "WHERE DKKMTL.TRANGTHAI ='1' AND DKKMTL.CTKM_FK = '" + this.ctkmId + "'"; 
			System.out.println(query);
			ResultSet kh = db.get(query);
			if(kh != null)
			{//
				while(kh.next())
				{
					String khId = "";
					String nppId ="";
					if(kh.getString("khId") != null)
					{
						khId = kh.getString("KHID");
						nppId = kh.getString("NPP_FK");
					}
					
					if(khId.length() > 0)
					{
						int soxuat = Integer.parseInt(kh.getString("SOXUAT"));
						// Kiem tra khach hang co dat so xuat da dang ky khong? Tra ve so xuat thuc dat
						int num = getTinh(khId);
						query="";
						if(num > soxuat) num = soxuat;
						System.out.println("khach hang:"+ khId +"so xuat:" +num  +" Id:"+ this.id );
						//System.out.println(query);
						if (num > 0){	
							query = "INSERT INTO DENGHITRAKMTICHLUY_KHACHHANG ( DENGHITRAKMTL_FK, KHACHHANG_FK, XUATDANGKY, XUATDENGHI, XUATDUYET,NPP_FK ) VALUES('" + this.id + "','" + khId + "','" + soxuat + "','" + num + "','0','"+ nppId +"')";
							System.out.println(query);
							if(!db.update(query)){
							query = "UPDATE DENGHITRAKMTICHLUY_KHACHHANG SET XUATDENGHI = '" + num + "' WHERE KHACHHANG_FK = '" + khId + "' AND DENGHITRAKMTL_FK = '" + this.id + "' AND NPP_FK = '" + nppId + "'";
							}
							query="";
						}	
											
					}
				}
				
			//	kh.close();
			
			}
		}
		catch(Exception e) {}
	}

	public void init() {
		getNppInfo();
		String sql = "select pk_seq, scheme from ctkhuyenmai where loaict='3'";
		System.out.println(sql);
		this.ctkmIds = db.get(sql);
	}
	
	
	
	public void setMessage(String Msg) {
		
		this.Msg =Msg;
	}
	
	public String getMessage() {
		
		return this.Msg;
	}
	

	public void setctkmId(String ctkmId) {
	
		this.ctkmId = ctkmId;
	}
	
	public String getctkmId() {
		
		return this.ctkmId;
	}

	public void setctkmIds(ResultSet ctkmIds) {
		
		this.ctkmIds = ctkmIds;
	}

	public ResultSet getctkmIds() {
		
		return this.ctkmIds;
	}
	
	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	
	public void DBclose() {
		try {			
			
			if(this.ctkmIds != null) this.ctkmIds.close();
			if(this.db != null) this.db.shutDown();
			
		} catch(Exception e) {
			e.printStackTrace();
		}		
	}
	
	public int getTinh(String khId) {
			int soxuat = 0;
			if(this.ctkmId.length()>0)
			{
				String sql ="select * from ctkm_dkkm where ctkhuyenmai_fk ='"+ this.ctkmId +"' order by thutudieukien";
				//	System.out.println(sql);
				ResultSet dieukien = db.get(sql);
				String madk[] = new String[100];
				int i = 0;
				
				String chuoidk = "";
			
				if(dieukien!=null)
					try {
						while(dieukien.next())
						{
							chuoidk = chuoidk + dieukien.getString("dkkhuyenmai_fk") +",";
							if(Integer.parseInt(dieukien.getString("pheptoan"))==0)
							{
								madk[i] = chuoidk; //moi mang la mot chuoi cac dieu kien AND lien tiep
								i++;
								chuoidk ="";
							}
							
						}//while
						if(i==0 && chuoidk.length()>0)
						{
							madk[i] = chuoidk;
							i=1;
						}
						
						dieukien.close();
						
						int j = 0;
						while(j<i)
						{
							
							  // System.out.println("chuoi ma dk:"+madk[j]+"-----------------------------------");
								String arr[] = madk[j].split(",");	//lay ra cac dieu kien co cung phep toan AND lien tiep
								boolean flag = true;
								int lan = 1;
								while(flag)
								{	int h = 0;
								
									while( h < arr.length && flag)
									{
											
										int loai = loaict(arr[h]);
										if(loai==2)	//dieu kien bat ky trong
										{ 
											//System.out.println("so luong:"+kiemtra_or(arr[h],khachhang,lan));
											
											if(!kiemtra_batkytrong(arr[h], khId, lan))//neu la tong luong hoac tong tien
											{   
												flag = false;
												break;
											}
										}
										else
											{  //neu la san pham bat buoc
												if(!kiemtra_batbuoc(arr[h], khId,lan))
												{
													flag = false;
													break;
												}
											}
												
											h++;	
												
									}//end while
									
									if(flag)lan++;//neu thoa thi tiep tuc
									
									if(lan > soxuat && lan >1 )
									{
										soxuat = lan;
									//	System.out.println("so xuat:"+ soxuat);
									}
								}//end while
								j++;//tang dieu kien or tiep theo
								
								
							}//end while
							
					} catch(Exception e) {
						
						e.printStackTrace();
					}

			if(soxuat >1)
				soxuat = soxuat -1;
			}
			return soxuat;	
		}

	boolean kiemtra_batbuoc(String dkkm, String khId, int lan)
	{
		String sql ="select sanpham_fk,case when soluong is null then 0 else soluong*"+ lan +" end as soluong from DIEUKIENKM_SANPHAM where dieukienkhuyenmai_fk ='"+ dkkm +"'";
     //  System.out.println("ham bat buoc "+ sql);
		ResultSet rs = db.get(sql);
		if(rs!=null)
		{
			try {
				while(rs.next())
				{  String sanpham = rs.getString("sanpham_fk");
				   String soluong =rs.getString("soluong");
					
					sql = " select sum(soluong) as sl from donhang_sanpham where  donhang_fk in(select pk_seq from donhang where trangthai ='1' and ngaynhap >= '"+ this.tungay +"' and ngaynhap <='"+ this.denngay +"' and khachhang_fk ='"+ khId +"') " +
						  " and sanpham_fk ='"+ sanpham +"' group by sanpham_fk having sum(soluong) >="+ soluong +"";
					ResultSet tl = db.get(sql);
					//System.out.println("dieu kien:"+ tb.getRow()+"-------"+sql);
				//	System.out.println("dieu kien:-------"+sql);
					if(tl !=null)
						if(!tl.next())
							return false;
					tl.close();
				}
				rs.close();
			} catch(Exception e) {
				
				e.printStackTrace();
				return false;
			}
		}	
		return true;
	}

	boolean kiemtra_batkytrong(String dkkm, String khId,int lan) //voi san pham khong bat buoc
	{
		String sql = "select isnull(tongtien,0) as tien,isnull(tongluong,0) as sl from dieukienkhuyenmai where pk_seq ='"+ dkkm +"'";
		//System.out.println("dk trung bay :"+ sql);
		ResultSet rs = db.get(sql);
		try {
			rs.next();
			float tongtien =Float.parseFloat(rs.getString("tien"))*lan;
			int soluong  =Integer.parseInt(rs.getString("sl"))*lan;
			sql ="select isnull(sum(soluong),0) as tongsoluong,isnull(sum(soluong*giamua*1.1 + isnull(chietkhau,0)),0)as tongtien from donhang_sanpham where donhang_fk in(select pk_seq from donhang where trangthai ='1' and ngaynhap >='"+ this.tungay +"' and ngaynhap <='"+  this.denngay +"' and khachhang_fk ='"+ khId +"')" +
				" and sanpham_fk in(select sanpham_fk from DIEUKIENKM_SANPHAM where dieukienkhuyenmai_fk = '"+ dkkm +"')" ;
					
		//System.out.println("dk trung bay :"+ sql);
			ResultSet tl = db.get(sql);
			if(tl!=null)
			if(tl.next())
			{
				if(tongtien >0)
				{ 
					if(tongtien <= Float.parseFloat(tl.getString("tongtien")))//neu dieu kien la tong tien 
						return true;
				}
				else
				{
					//System.out.println("so luong:"+ soluong +" : tongsoluong:"+tb.getString("tongsoluong"));
					if(soluong <= Integer.parseInt(tl.getString("tongsoluong"))) //neu dieu kien la tong luong
					  return true;
				}
			}
			else
				return false;
			tl.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	int loaict(String cttb)
	{    int st = 0;
		String sql = "select * from dieukienkhuyenmai where pk_seq ='"+ cttb +"'";
		ResultSet rs = db.get(sql);
		if(rs!=null)
		{
			try {
				rs.next();
				st = Integer.parseInt(rs.getString("loai"));
				rs.close();
			} catch(Exception e) {
				
				e.printStackTrace();
			}
			
		}
		return st;
	}
	
	public Hashtable<Integer, String> getNvbhIds() {
		
		return null;
	}
	
	public void setId(String Id) {
		
		this.id = Id;
	}
	
	public String getId() {
		
		return this.id;
	}


}
