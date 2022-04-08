package geso.dms.center.beans.dongboctkm.imp;
import geso.dms.center.beans.dongboctkm.IDongboctkm;
import geso.dms.center.beans.dongboctkm.imp.Dongboctkm;
import geso.dms.center.servlets.report.ReportAPI;
import geso.dms.center.util.OracleConnUtils;
import geso.dms.center.db.sql.*;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.Serializable;

public class Dongboctkm implements IDongboctkm, Serializable
{
	private static final long serialVersionUID = -9217977546733690415L;
	String id;
	String ten;
	String diengiai;
	String trangthai;	
	String ngaytao;
	String ngaysua;
	String nguoitao;
	String nguoisua;
	String msg;
	ResultSet spList;
	ResultSet spSelected;
	
	ResultSet dvkdList;
	ResultSet nhList;
	ResultSet clList;
	String dvkdId;
	String nhId;
	String clId;
	String[] nhom;
	String[] sanpham;
	ResultSet kenh;
	String kenhId;
	String type;
	boolean search = false;
	dbutils db ;
	
	String tungay = "";
	String denngay = "";
	String NspTT = "";
	public Dongboctkm(String[] param)
	{
		this.id = param[0];
		this.ten = param[1];	
		this.diengiai = param[2];
		this.trangthai = param[3];
		this.ngaytao = param[4];
		this.ngaysua = param[5];
		this.nguoitao = param[6];
		this.nguoisua = param[7];	
		this.type = param[8];
		this.tungay = param[9];
		this.denngay = param[10];
		this.NspTT = param[11];
		this.msg = "";
		this.dvkdId = "0";
		this.nhId = "0";
		this.clId = "0";
		this.kenhId ="";
		this.db = new dbutils();
		
	}
	
	public Dongboctkm()
	{
		this.id = "";
		this.ten = "";	
		this.diengiai = "";
		this.trangthai = "";
		this.ngaytao = "";
		this.ngaysua = "";
		this.nguoitao = "";
		this.nguoisua = "";	
		this.msg = "";
		this.dvkdId = "0";
		this.nhId = "0";
		this.clId = "0";
		this.kenhId ="";
		this.type = "";
		this.tungay = "";
		this.denngay = "";
		this.db = new dbutils();
		
	}

	
	public String getTungay() {
		return tungay;
	}
	public void setTungay(String tungay) {
		this.tungay = tungay;
	}
	public String getDenngay() {
		return denngay;
	}
	public void setDenngay(String denngay) {
		this.denngay = denngay;
	}
	
	
	public String getId()
	{
		return this.id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getTen()
	{
		return this.ten;
	}

	public void setTen(String ten)
	{
		this.ten = ten;
	}

	public String getDiengiai()
	{
		return this.diengiai;
	}

	public void setDiengiai(String diengiai)
	{
		this.diengiai = diengiai;
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
	
	public String getNgaysua()
	{
		return this.ngaysua;
		
	}

	public void setNgaysua(String ngaysua)
	{
		this.ngaysua = ngaysua;
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
	
	public String getMessage()
	{
		return this.msg;
	}

	public void setMessage(String msg)
	{
		this.msg = msg;
	}
	
	public ResultSet getSpList()
	{
		return this.spList;
	}

	public void setSpList(ResultSet spList)
	{
		this.spList = spList;
	}

	public ResultSet getSpSelected()
	{
		return this.spSelected;
	}

	public void setSpSelected(ResultSet spSelected)
	{
		this.spSelected = spSelected;
	}


	public ResultSet getDvkdList()
	{
		return this.dvkdList;
	}

	public void setDvkdList(ResultSet dvkdList)
	{
		this.dvkdList = dvkdList;
	}
	
	public ResultSet getNhList()
	{
		return this.nhList;
	}

	public void setNhList(ResultSet nhList)
	{
		this.nhList = nhList;
	}
	
	public ResultSet getCLList()
	{
		return this.clList;
	}

	public void setClList(ResultSet clList)
	{
		this.clList = clList;
	}

	public String getDvkdId()
	{
		return this.dvkdId;
	}

	public void setDvkdId(String dvkdId)
	{
		this.dvkdId = dvkdId;
	}

	public String getNhId()
	{
		return this.nhId;
	}

	public void setNhId(String nhId)
	{
		this.nhId = nhId;
	}

	public String getClId()
	{
		return this.clId;
	}

	public void setClId(String clId)
	{
		this.clId = clId;
	}


	public String[] getSanpham()
	{
		return this.sanpham;
	}

	public void setSanpham(String[] sanpham)
	{
		this.sanpham = sanpham;
	}
	
	public String saveNewNkm()
	{
		dbutils db=new dbutils();
		OracleConnUtils db_sgp=new OracleConnUtils();
		try {
		
		db.getConnection().setAutoCommit(false);
		db_sgp.getConnection().setAutoCommit(false);
		
		int flag=0;	
		String query="delete from APPS.SGP_DMS_PROMOTION_SAL_OUT a where  to_char(a.TRANSACTION_DATE, 'yyyy-mm-dd') >= '"+tungay+"' "
				+ "and  to_char(a.TRANSACTION_DATE, 'yyyy-mm-dd') <= '"+denngay+"'";
		if(!db_sgp.update(query))
		{
			flag=1;
			System.out.println("flag delete vao "+flag);
		}
		
		
		query="delete from DongBoCTKM where TRANSACTION_DATE >= '"+tungay+"' and TRANSACTION_DATE <= '"+denngay+"' ";
		if(!db.update(query))
		{
			flag=1;
			System.out.println("flag delete vao "+flag);
		}
		
		
		
		query=   "\n select CUSTOMER_NO,CUSTOMER_Name,TRANSACTION_DATE,PROMOTION_TYPE,METHOD,Start_Date_Active,End_Date_Active "+
				 "\n ,PROMOTION_CODE,PROMOTION_NAME,SALES_ITEM_CODE,SALES_ITEM_NAME"+
				 "\n ,PROMOTION_ITEM_CODE,PROMOTION_ITEM_NAME,UOM_PROMOTION,sum(PROMOTION_QUANTITY) as PROMOTION_QUANTITY,sum(SALES_QUANTITY) as SALES_QUANTITY,UOM_SALE " +      
				 "\n from ( " +      
				 "\n select npp.MA as CUSTOMER_NO, npp.TEN as CUSTOMER_Name, dh.NGAYNHAP as TRANSACTION_DATE, ctkm.LOAICT as PROMOTION_TYPE, " +  
				 "\n N'Trả sau' as METHOD, ctkm.TUNGAY as Start_Date_Active, ctkm.DENNGAY as End_Date_Active, ctkm.PK_SEQ as PROMOTION_CODE, " +  
				 "\n ctkm.SCHEME as PROMOTION_NAME, spmua.MA as SALES_ITEM_CODE, spmua.TEN as SALES_ITEM_NAME, sptra.MA as PROMOTION_ITEM_CODE, " +  
				 "\n sptra.TEN as PROMOTION_ITEM_NAME, dvdl.DONVI as UOM_PROMOTION, b.SOLUONG as PROMOTION_QUANTITY, " +
				 "\n dvdlmua.donvi as UOM_SALE, a.soluongmua as SALES_QUANTITY "+
				 "\n from donhang dh  " +  
				 "\n inner join DONHANG_CTKM_TRAKM b on b.DONHANGID = dh.PK_SEQ " +  
				 "\n left join donhang_ctkm_dkkm a on a.donhang_fk = dh.PK_SEQ and a.ctkm_fk = b.CTKMID " +  
				 "\n inner join ctkhuyenmai ctkm on ctkm.PK_SEQ = a.ctkm_fk and ctkm.PK_SEQ = b.CTKMID " +  
				 "\n inner join NHAPHANPHOI npp on npp.PK_SEQ = dh.NPP_FK  " +  
				 "\n inner join sanpham spmua on spmua.MA = a.sanpham_fk " +  
				 "\n inner join sanpham sptra on sptra.MA = b.SPMA " +  
				 "\n inner join DONVIDOLUONG dvdl on dvdl.PK_SEQ = sptra.DVDL_FK " +  
				 "\n inner join DONVIDOLUONG dvdlmua on dvdlmua.PK_SEQ = spmua.DVDL_FK " +  
				 "\n where dh.NGAYNHAP >= '"+tungay+"' and dh.NGAYNHAP <='"+denngay+"' and dh.trangthai = 1 ) as data "+
				 "\n group by CUSTOMER_NO,CUSTOMER_Name,TRANSACTION_DATE,PROMOTION_TYPE,METHOD,Start_Date_Active,End_Date_Active  "+
				 "\n ,PROMOTION_CODE,PROMOTION_NAME,SALES_ITEM_CODE,SALES_ITEM_NAME "+
				 "\n ,PROMOTION_ITEM_CODE,PROMOTION_ITEM_NAME,UOM_PROMOTION,UOM_SALE "+
				 "\n order by CUSTOMER_NO,TRANSACTION_DATE,PROMOTION_CODE" ;

		 System.out.println("query "+query);
		ResultSet rs=db.get(query);
		int i=0;
			while(rs.next())
			{
				System.out.println("query1111_ ");
				String CUSTOMER_NO = rs.getString("CUSTOMER_NO");
				String CUSTOMER_Name = rs.getString("CUSTOMER_Name");
				String TRANSACTION_DATE = rs.getString("TRANSACTION_DATE");
				String PROMOTION_TYPE = rs.getString("PROMOTION_TYPE");
				String METHOD = rs.getString("METHOD");
				String Start_Date_Active = rs.getString("Start_Date_Active");
				String End_Date_Active = rs.getString("End_Date_Active");
				String UOM_SALE = rs.getString("UOM_SALE");
				String SALES_QUANTITY = rs.getString("SALES_QUANTITY");
				String PROMOTION_CODE = rs.getString("PROMOTION_CODE");
				String PROMOTION_NAME = rs.getString("PROMOTION_NAME");
				String SALES_ITEM_CODE = rs.getString("SALES_ITEM_CODE");
				String SALES_ITEM_NAME = rs.getString("SALES_ITEM_NAME");
				String PROMOTION_ITEM_CODE = rs.getString("PROMOTION_ITEM_CODE");
				String PROMOTION_ITEM_NAME = rs.getString("PROMOTION_ITEM_NAME");
				String UOM_PROMOTION = rs.getString("UOM_PROMOTION");
				String PROMOTION_QUANTITY = rs.getString("PROMOTION_QUANTITY");
				
				

				
				
				query="insert into APPS.SGP_DMS_PROMOTION_SAL_OUT  (CUSTOMER_NO,CUSTOMER_Name,TRANSACTION_DATE,PROMOTION_TYPE,METHOD,Start_Date_Active,End_Date_Active,PROMOTION_CODE,PROMOTION_NAME,SALES_ITEM_CODE,SALES_ITEM_NAME,PROMOTION_ITEM_CODE,PROMOTION_ITEM_NAME,UOM_PROMOTION,PROMOTION_QUANTITY,UOM_SALE,SALES_QUANTITY)"+
						  "values( '"+ CUSTOMER_NO +"',N'"+ CUSTOMER_Name +"',to_date('"+ TRANSACTION_DATE +"','yyyy-mm-dd'),'"+ PROMOTION_TYPE  +"',N'"+ METHOD +"',to_date('"+ Start_Date_Active +"','yyyy-mm-dd'),to_date('"+ End_Date_Active +"','yyyy-mm-dd'),N'"+ PROMOTION_CODE +"',N'"+ PROMOTION_NAME +"','"+ SALES_ITEM_CODE +"',N'"+ SALES_ITEM_NAME +"','"+ PROMOTION_ITEM_CODE +"',N'"+ PROMOTION_ITEM_NAME +"','"+ UOM_PROMOTION +"','"+ PROMOTION_QUANTITY +"' "+
							",'"+ UOM_SALE +"','"+ SALES_QUANTITY +"' )";
					
					if(db_sgp.updateReturnInt(query)!=1)
					{
						flag=1;
						
					}
					
				query="insert into DongBoCTKM  					  (CUSTOMER_NO,CUSTOMER_Name,TRANSACTION_DATE,PROMOTION_TYPE,METHOD,Start_Date_Active,End_Date_Active,PROMOTION_CODE,PROMOTION_NAME,SALES_ITEM_CODE,SALES_ITEM_NAME,PROMOTION_ITEM_CODE,PROMOTION_ITEM_NAME,UOM_PROMOTION,PROMOTION_QUANTITY,UOM_SALE,SALES_QUANTITY)"+
							  "values( '"+ CUSTOMER_NO +"',N'"+ CUSTOMER_Name +"','"     + TRANSACTION_DATE +"','"             + PROMOTION_TYPE  +"',N'"+ METHOD +"','"+         Start_Date_Active +"','"+                        End_Date_Active +"',N'"+             PROMOTION_CODE +"',N'"+ PROMOTION_NAME +"','"+ SALES_ITEM_CODE +"',N'"+ SALES_ITEM_NAME +"','"+ PROMOTION_ITEM_CODE +"',N'"+ PROMOTION_ITEM_NAME +"','"+ UOM_PROMOTION +"','"+ PROMOTION_QUANTITY +"' "+
								",'"+ UOM_SALE +"','"+ SALES_QUANTITY +"' )";
						
						if(db.updateReturnInt(query)!=1)
						{
							flag=1;
							
						}
				
			}
			rs.close();
			if(flag==1)
			{
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
				db_sgp.getConnection().rollback();
				db_sgp.getConnection().setAutoCommit(true);
				System.out.println("rollback "+tungay);
				msg = "lỗi đồng bộ";
				return "lỗi đồng bộ";
			}
			else
			{
				db.getConnection().commit();
				db.getConnection().setAutoCommit(true);
				db_sgp.getConnection().commit();
				db_sgp.getConnection().setAutoCommit(true);
				System.out.println("commit "+tungay);
				msg = "Đồng bộ CTKM thành công";
				return "Đồng bộ CTKM thành công";
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
				db_sgp.getConnection().rollback();
				db_sgp.getConnection().setAutoCommit(true);
				msg = "lỗi đồng bộ";
				return "lỗi đồng bộ";
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				msg = "lỗi đồng bộ";
				return "lỗi đồng bộ";
			}
			
		}
		finally{
			
			db.shutDown();
			db_sgp.shutDown();
		}
		

	}
	
	

	private ResultSet createDvkdRS(){  	
		
		//ResultSet dvkdRS =  this.db.get("select pk_seq, diengiai from donvikinhdoanh where trangthai='1' ");
		ResultSet dvkdRS =  this.db.get("select a.pk_seq, a.diengiai from donvikinhdoanh a,Nhacungcap_dvkd b where a.pk_seq = b.dvkd_fk and a.trangthai='1' and b.checked ='1'");
		
		return dvkdRS;
	}
	
	private ResultSet createNhRS(){
		ResultSet nhRS;
		if (!this.dvkdId.equals("0")){
			nhRS =  this.db.get("select pk_seq, ten from nhanhang where trangthai='1' and dvkd_fk='" + this.dvkdId + "'");
		}else{
			nhRS =  null;	
		}
			
		return nhRS;
		
	}

	private ResultSet createClRS() {  	
		ResultSet clRS;
	
		if(!this.nhId.equals("0")){
			clRS = this.db.get("select distinct a.pk_seq, a.ten from chungloai a, nhanhang_chungloai b where trangthai='1' and a.pk_seq = b.cl_fk and b.nh_fk = '" + this.nhId + "'");
		}else{
			clRS = null;
		}
		return clRS;
			
	}
	
	private void createSpRS(){  	

		String query;
		String temp = "";
		if (this.id.length()>0){
			if (this.sanpham != null){
				query = "select pk_seq, ma, ten from sanpham where";
				temp =  "select pk_seq from sanpham where";
				for(int i=0; i < this.sanpham.length; i++){
					if (i==0){
						query = query + " pk_seq = '" + this.sanpham[i] + "'";
						temp = temp + " pk_seq = '" + this.sanpham[i] + "'";
					}else{						
						query = query + " or pk_seq = '" + this.sanpham[i] + "'";
						temp = temp + " or pk_seq = '" + this.sanpham[i] + "'";
					}
				}				
			}else{
				query = "select a.pk_seq, a.ma, a.ten from sanpham a, NHOMSANPHAMCHITIEU_sanpham b where a.pk_seq = b.sp_fk and b.nsp_fk = '" + this.id + "'";
				
			}
			
			System.out.print(query);
			this.spSelected = this.db.get(query);
		}else{
			if (this.sanpham != null){
				query = "select pk_seq, ma, ten from sanpham where";
				temp =  "select pk_seq from sanpham where";
				for(int i=0; i < this.sanpham.length; i++){
					if (i==0){
						query = query + " pk_seq = '" + this.sanpham[i] + "'";
						temp = temp + " pk_seq = '" + this.sanpham[i] + "'";
					}else{						
						query = query + " or pk_seq = '" + this.sanpham[i] + "'";
						temp = temp + " or pk_seq = '" + this.sanpham[i] + "'";
					}
				}				
				this.spSelected = this.db.get(query);
			}			
		}
		
		//query = "select pk_seq, ma, ten from sanpham  where trangthai = '1' and pk_seq not in (select sp_fk from NHOMSANPHAMCHITIEU_sanpham where nsp_fk = '" + this.id +"')";
		
		if (this.id.length()>0){
			query = "select pk_seq, ma, ten from sanpham  where trangthai = '1' and pk_seq not in (select sp_fk from NHOMSANPHAMCHITIEU_sanpham where nsp_fk = '" + this.id + "')";
		}else{
			query = "select pk_seq, ma, ten from sanpham  where trangthai = '1'";
		}
		if (!this.dvkdId.equals("0")){
			query = query + " and dvkd_fk ='" + this.dvkdId + "'";

		}
		
		if (!this.nhId.equals("0")){
			query = query + " and nhanhang_fk = '" + this.nhId + "'";

		}
		
		if (!this.clId.equals("0")){
			query = query + " and chungloai_fk = '" + this.clId + "'";

		}
		if (this.sanpham != null){
			query = query +  " and pk_seq not in (" + temp + ")";
		}
		if(this.kenhId.length()>0)
		{
			query = query + " and pk_seq in (select sanpham_fk from bgmuanpp_sanpham where bgmuanpp_fk in (select pk_seq from banggiamuanpp where kenh_fk ='"+ kenhId +"')) ";
		}
		query = query + " order by ten";
		System.out.println(query);
		this.spList = this.db.get(query);		
		
	}
	
	public void UpdateRS(){
		this.dvkdList = createDvkdRS();
		this.nhList = createNhRS();
		this.clList = createClRS();	
		CreateKenh();
		createSpRS();
	}

	
	public void setkenhId(String kenhId) {
		
		this.kenhId = kenhId;
	}

	
	public String getKenhId() {
		
		return this.kenhId;
	}

	
	public void setKenh(ResultSet kenh) {
		
		this.kenh = kenh;
	}

	
	public ResultSet getKenh() {
		
		return this.kenh;
	}
	void CreateKenh()
	{
		this.kenh = db.get("select * from kenhbanhang");
	}

	
	public void setType(String type) {
		this.type = type;
	}

	
	public String getType() {
		return this.type;
	}

	
	public String getNspTT() {
		
		return this.NspTT;
	}

	
	public void setNspTT(String NspTT) {
		
		this.NspTT = NspTT;
	}
	
}


