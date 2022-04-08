package geso.dms.center.beans.duyetdhkm.imp;
  
import geso.dms.center.beans.duyetdhkm.*;
import geso.dms.center.db.sql.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;


public class DuyetDhKm implements IDuyetDhKm
{
	private static final long serialVersionUID = -9217977546733610214L;
	String userId;
	String id;
 
	String diengiai;
	String trangthai;
	String ngaytao;
	String nguoitao;
	String ngaysua;
	String nguoisua;
	String msg;
	String bm;
	ResultSet bms;
	String Thang;
	String Nam;
	ResultSet RsCtKm;
	ResultSet Rslistsp;
	String StrCtkmChon;
	 
	dbutils db;
	
	public DuyetDhKm()
	{
		this.db = new dbutils();
		this.diengiai = "";
		this.trangthai = "";
		this.ngaytao = "";
		this.nguoitao = "";
		this.ngaysua = "";
		this.Thang="";
		this.Nam="";
		this.nguoisua = "";
		this.bm ="";
		this.msg = "";
		this.StrCtkmChon="";
		this.selected="";
		this.id="";
			this.init();
 
	}
 
	public String getUserId()
	{
		return this.userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}
	
	public String getId() 
	{
		return this.id;
	}

	public void setId(String id) 
	{
		this.id = id;
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

	public String getMessage() 
	{
		return this.msg;
	}

	public void setMessage(String msg) 
	{
		this.msg = msg;
	}
 
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}	
	
	public void closeDB(){
		if (this.db != null)
			this.db.shutDown();
	}
 
	@Override
	public String getNam() {

		return this.Nam;
	}

	@Override
	public void setNam(String nam) {

		this.Nam=nam;
	}

	@Override
	public String getThang() {

		return this.Thang;
	}

	@Override
	public void setThang(String thang) {

		this.Thang=thang;
	}

	@Override
	public ResultSet getRsCTKM() {

		return this.RsCtKm;
	}

	@Override
	public void setRsCTKM(ResultSet rs) {

		this.RsCtKm=rs;
	}

	@Override
	public void setCTKMChon(String str) {

		this.StrCtkmChon=str;
	}

	@Override
	public String getCTKMChon() {

		return this.StrCtkmChon;
	}

	 
	public void init() {

		if(this.Thang.length()>0 && this.Nam.length() > 0)
		{
			String	sql=
					" SELECT  DISTINCT PK_SEQ,SCHEME,DIENGIAI FROM CTKHUYENMAI CTKM  "+ 
   					" LEFT JOIN SANPHAMTRAKM SP ON SP.CTKM_FK=CTKM.PK_SEQ  "+  
					" WHERE  SP.THANG="+this.Thang+" AND SP.NAM="+this.Nam+"   "+
					"  AND   "+
					" (  "+
					"	NOT EXISTS   "+
					" (  "+
					"	SELECT CTKM_FK FROM DUYETDONHANGKM_CTKM A INNER JOIN DUYETDONHANGKM B ON A.DUYETDONHANGKM_FK=B.PK_SEQ   "+
					"	AND B.TRANGTHAI <>2 AND A.CTKM_FK=CTKM.PK_SEQ AND A.TINHTRANG='1' )   "+ 
					"	 OR  NOT EXISTS  "+
					"	 ( "+
					"		 SELECT * FROM DONDATHANG DH INNER JOIN DONDATHANG_SP "+
					"			DHSP ON DHSP.DONDATHANG_FK=DH.PK_SEQ   "+
					"			INNER JOIN CTKHUYENMAI KM ON KM.SCHEME=DHSP.SCHEME "+
					"			WHERE DH.thang="+this.Thang+" AND DH.nam="+this.Nam+" "+
					"			AND KM.PK_SEQ=CTKM.PK_SEQ AND DH.TRANGTHAI!=6 AND DH.NPP_FK=SP.NPP_FK  "+
					"			AND DHSP.SANPHAM_FK=SP.SPTT_FK  "+
					"	 ) "+
					" ) ";
			
			this.RsCtKm=db.get(sql);
		}
		if(this.StrCtkmChon.length() >0)
		{
			String sql=   
					" SELECT  cast(npp.pk_seq as varchar(20))+'_'+cast(ctkm.pk_seq as varchar(20)) +'_'+cast(sp.pk_seq as varchar(20)) as nppScheme ,npp.pk_seq as nppId,npp.ten as tennpp , sum(SOLUONGTT) as SOLUONG ,sp.ma +'-'+sp.ten as ten ,DVDL.DONVI,CTKM.SCHEME   " +  
				   " FROM SANPHAMTRAKM A     " +  
				   " INNER JOIN SANPHAM SP ON SP.PK_SEQ=A.SPTT_FK    " +  
				   " INNER JOIN DONVIDOLUONG DVDL ON DVDL.PK_SEQ=SP.DVDL_FK    " +  
				   " INNER JOIN CTKHUYENMAI CTKM ON CTKM.PK_SEQ=A.CTKM_FK  " +
				   " inner join nhaphanphoi npp on npp.pk_seq=npp_fk    " +  
				   " WHERE THANG= "+this.Thang+" AND NAM= "+this.Nam+"  AND A.CTKM_FK  in("+this.StrCtkmChon+") " +  
				   " AND  "+ 
					" NOT EXISTS  "+
					"	(  "+
					"			SELECT *  "+
					"			FROM DONDATHANG DH INNER JOIN DONDATHANG_SP  DHSP ON DHSP.DONDATHANG_FK=DH.PK_SEQ  "+
					"			WHERE CTKM.SCHEME=DHSP.SCHEME AND DH.NPP_FK=A.NPP_FK AND DH.THANG='"+this.Thang+"' AND DH.NAM='"+this.Nam+"' "+
					"				AND DH.TRANGTHAI!=6  AND DHSP.SANPHAM_FK=A.SPTT_FK "+
					"	)   "+
				   " group by    ctkm.pk_Seq ,sp.pk_seq ,npp.pk_seq ,npp.ten , sp.ma , sp.ten ,DVDL.DONVI,CTKM.SCHEME " +
				   "  ORDER BY npp.ten,sp.ma   ";
			
			System.out.println("[SanPham]"+sql);
			
			this.Rslistsp=db.get(sql);
		} 
	}
	@Override
	public ResultSet getlistsp() {

		return 	this.Rslistsp;
	}
	@Override
	public void setlistsp(ResultSet listsp) {

		this.Rslistsp=listsp;
	}
	
	
	public static void main(String[] arg)
	{
		String sql="199_1000,12200_1000";
		System.out.println(sql.split("_")[0]);
	}

	@Override
	public boolean createDhKm() 
	{
		dbutils db=new dbutils();
		try
		{
		
			db.getConnection().setAutoCommit(false);
		  	String sql="";
		  	
			if(this.table.length() ==0||this.StrCtkmChon.length()==0)
			{
				this.msg="Chương trình khuyến mãi này chưa được nhà phân phối nào sử dụng để thanh toán.\n Vui lòng chọn chương trình khác.";
				return false;
			}
			sql=" INSERT INTO DUYETDONHANGKM(THANG,NAM,NGAYTAO,NGAYSUA,NGUOITAO,NGUOISUA,TRANGTHAI,DIENGIAI)VALUES " +
					"("+this.Thang+","+this.Nam+",'"+this.getDateTime()+"','"+this.getDateTime()+"',"+this.userId+","+this.userId+",1,'')";
			if(!db.update(sql)){
				this.msg="Lỗi dòng lệnh : " +sql;
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				return false;
			}	
			sql = "select IDENT_CURRENT('DUYETDONHANGKM') as dhId";
			ResultSet rsDh = db.get(sql);	
			rsDh.next();
	     	this.id = rsDh.getString("dhId");
	     	rsDh.close();
		 
			sql=" insert into DUYETDONHANGKM_CTKM ( DUYETDONHANGKM_FK,CTKM_FK,TINHTRANG) "+
				" select "+this.id+",PK_seq,'1' from CTKHUYENMAI where PK_SEQ in ("+this.StrCtkmChon+")";
			if(!db.update(sql))
			{
				this.msg="Lỗi dòng lệnh : " +sql;
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				return false;
			}		
			sql=
			" insert into DuyetDonHangKm_ChiTiet(DUYETDONHANGKM_FK,NPP_FK,SANPHAM_FK,CTKM_FK) " +
			" select "+this.id+",nppId,spId,ctkmId " +
			" from (  "+this.table+"   ) as tb ";
			if(!db.update(sql))
			{
				this.msg="Lỗi dòng lệnh : " +sql;
				geso.dms.center.util.Utility.rollback_throw_exception(db);
				return false;
			}		
		
	      sql= " SELECT THANG,NAM,NPP_FK,KBH_FK,CTKM_FK,SPTT_FK,sum(SOLUONGTT) as SOLUONGTT,SP.DVKD_FK,SP.PK_SEQ  AS spid,DVDL.DONVI,CTKM.SCHEME  ,  " +  
			   "  ISNULL((SELECT TOP 1  GIAMUANPP  FROM               	   	  " +  
			   "  DBO.BGMUANPP_SANPHAM  D INNER JOIN BANGGIAMUANPP P ON P.PK_SEQ=D.BGMUANPP_FK      " +  
			   "  INNER JOIN BANGGIAMUANPP_NPP  NPP ON NPP.BANGGIAMUANPP_FK=P.PK_SEQ     " +  
			   "  WHERE P.KENH_FK=A.KBH_FK   AND  NPP.NPP_FK=A.NPP_FK AND D.SANPHAM_FK=A.SPTT_FK AND P.TRANGTHAI=1  ),0  ) AS GIAMUA    " +  
			   "  FROM SANPHAMTRAKM A     " +  
			   "  INNER JOIN SANPHAM SP ON SP.PK_SEQ=A.SPTT_FK    " +  
			   "  INNER JOIN DONVIDOLUONG DVDL ON DVDL.PK_SEQ=SP.DVDL_FK    " +  
			   "  INNER JOIN CTKHUYENMAI CTKM ON CTKM.PK_SEQ=A.CTKM_FK    " +
			   " INNER JOIN "+
			   "(  "+this.table+"   ) tb on tb.nppId=a.npp_fk and tb.spId=a.SPTT_FK and tb.ctkmId=a.ctkm_fk "+
			   "  WHERE THANG= "+this.Thang+" AND NAM= "+this.Nam+" " +
				" AND   "+	   
				" NOT EXISTS  "+
				"	(  "+
				"			SELECT *  "+
				"			FROM DONDATHANG DH INNER JOIN DONDATHANG_SP  DHSP ON DHSP.DONDATHANG_FK=DH.PK_SEQ  "+
				"			WHERE CTKM.SCHEME=DHSP.SCHEME AND DH.NPP_FK=A.NPP_FK AND DH.THANG='"+this.Thang+"' AND DH.NAM='"+this.Nam+"' "+
				"				AND DH.TRANGTHAI!=6  AND DHSP.SANPHAM_FK=A.SPTT_FK "+
				"	)   "+
			   "  group by  THANG,NAM,NPP_FK,KBH_FK,CTKM_FK,SPTT_FK ,SP.DVKD_FK,SP.PK_SEQ ,DVDL.DONVI,CTKM.SCHEME ,SPTT_FK " +
			   " ORDER BY A.NPP_FK   ";
      
      			System.out.println("______DonDatHang____"+sql);
				
				ResultSet rs=db.get(sql);
				String chuoitaomoi="";
				String chuoitaomoibk="";
				String dhId="";
				String nppid="";
				String dvkdid="";
				String kbhid="";
				while(rs.next())
				{
					  chuoitaomoi=rs.getString("NPP_FK")+rs.getString("KBH_FK")+rs.getString("DVKD_FK");
					  if(!chuoitaomoi.equals(chuoitaomoibk))
					  {
						  chuoitaomoibk=chuoitaomoi;
						  nppid=rs.getString("NPP_FK");
						  dvkdid=rs.getString("DVKD_FK");
						  kbhid=rs.getString("KBH_FK");
						  sql=" insert into dondathang(NGAYDAT,TRANGTHAI,NGUOITAO,NGUOISUA,NPP_FK,DVKD_FK,NCC_FK,kbh_FK,loaidonhang,iskm,tinhtrang,KHOTT_FK,thang,nam,SOTIENBVAT, VAT, SOTIENAVAT, CHIETKHAU) "+
							 " values ('"+this.getDateTime()+"','1',"+this.userId+","+this.userId+","+nppid+","+dvkdid+",100014," + kbhid+" ,'1','1','1',100001,"+Thang+","+Nam+",0,0,0,0)  ";
							if(!db.update(sql))
							{
								this.msg="Lỗi dòng lệnh : " +sql;
								geso.dms.center.util.Utility.rollback_throw_exception(db);
								return false;
							}	
							String query = "select IDENT_CURRENT('dondathang') as dhId";
						    rsDh = db.get(query);	
							rsDh.next();
					     	dhId = rsDh.getString("dhId");
					     	rsDh.close();
					  }
					  double thanhtien=rs.getDouble("SOLUONGTT")*rs.getDouble("GIAMUA");
					  sql="insert into dondathang_sp (sanpham_fk,dondathang_fk,soluong,soluongduyet,dongia,donvi,sotienbvat ,chietkhau,vat,sotienavat,scheme,khott)values (" 
							+rs.getString("spid")+","+dhId+","+rs.getString("SOLUONGTT")+",'"+rs.getString("SOLUONGTT")+"' ,"+Math.round(rs.getDouble("giamua"))+",N'"+rs.getString("donvi")+"',"+Math.round(thanhtien)+",'0',0,'"+Math.round(thanhtien)+"','"+rs.getString("scheme")+"','100001')";
						if(!db.update(sql))
						{
							this.msg="Lỗi dòng lệnh : " +sql;
							geso.dms.center.util.Utility.rollback_throw_exception(db);
							return false;
						}
				}
				rs.close();
				
				db.getConnection().commit();
				db.getConnection().setAutoCommit(true);
		 
				
	}catch(Exception er){
		er.printStackTrace();
		this.msg=er.toString();
		geso.dms.center.util.Utility.rollback_throw_exception(db);
		return false;

	}
	return true;	
	}

	@Override
	public void init_view() {

		String sql="select thang,nam,trangthai from DUYETDONHANGKM where pk_seq="+this.id;
		
		ResultSet rs=db.get(sql);
		try{
		if(rs.next()){
			this.Thang=rs.getString("thang");
				this.Nam=rs.getString("nam");
				this.trangthai=rs.getString("trangthai");
		}
		rs.close();
		}catch(Exception err){
			err.printStackTrace();
		}
		
		if(this.Thang.length()>0 && this.Nam.length() > 0)
		{
			  sql= " SELECT PK_SEQ,SCHEME,DIENGIAI FROM CTKHUYENMAI CTKM "+
						" WHERE PK_SEQ IN (SELECT CTKM_FK FROM DUYETDONHANGKM_CTKM WHERE  DUYETDONHANGKM_FK ="+this.id+" ) "; 
			this.RsCtKm=db.get(sql);
		}
		 
			  sql=   
				   " SELECT  cast(npp.pk_seq as varchar(20))+'_'+cast(ctkm.pk_seq as varchar(20)) +'_'+cast(sp.pk_seq as varchar(20)) as nppScheme ,npp.pk_seq as nppId,npp.ten as tennpp , sum(SOLUONGTT) as SOLUONG ,sp.ma +'-'+sp.ten as ten ,DVDL.DONVI,CTKM.SCHEME   " +  
				   " FROM SANPHAMTRAKM A     " +  
				   " INNER JOIN SANPHAM SP ON SP.PK_SEQ=A.SPTT_FK    " +  
				   " INNER JOIN DONVIDOLUONG DVDL ON DVDL.PK_SEQ=SP.DVDL_FK    " +  
				   " INNER JOIN CTKHUYENMAI CTKM ON CTKM.PK_SEQ=A.CTKM_FK  " +
				   " inner join nhaphanphoi npp on npp.pk_seq=npp_fk    " +  
				   " WHERE  A.CTKM_FK  in((SELECT CTKM_FK FROM DUYETDONHANGKM_CTKM WHERE  DUYETDONHANGKM_FK ="+this.id+" ) ) " +  
				   " group by    ctkm.pk_Seq ,sp.pk_seq ,npp.pk_seq ,npp.ten , sp.ma , sp.ten ,DVDL.DONVI,CTKM.SCHEME "+
				   "  ORDER BY npp.ten,sp.ma   ";
			this.Rslistsp=db.get(sql);
  
			sql= " select NPP_FK,SANPHAM_FK,CTKM_FK   from DuyetDonHangKm_ChiTiet where DUYETDONHANGKM_FK="+this.id+" ";
			rs =db.get(sql);
			try
			{
				int i=0;
				while(rs.next())
				{
					if(i==0)
					table= rs.getString("npp_fk")+"_"+rs.getString("CTKM_FK")+"_"+ rs.getString("SANPHAM_FK");
					else 
						table +="," +rs.getString("npp_fk")+"_"+rs.getString("CTKM_FK")+"_"+ rs.getString("SANPHAM_FK");
					i++;
				}
			} catch (SQLException e)
			{
				
				e.printStackTrace();
			}
	}

	String table,selected;
	

	public String getTable()
	{
		return table;
	}

	public void setTable(String table)
	{
		this.table = table;
	}

	@Override
	public String getSelected()
	{
		return selected;
	}

	@Override
	public void setSelected(String spId)
	{
		this.selected=spId;
	}
	
	
}

