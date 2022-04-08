package geso.dms.center.beans.donmuahang.imp;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import geso.dms.center.beans.donmuahang.ISanPhamTraKM;
import geso.dms.center.beans.donmuahang.ITaodonhangKm;
import geso.dms.center.db.sql.dbutils;
public class TaodonhangKm implements ITaodonhangKm{

	String thang;
	String nam;
	String userid;
	ResultSet rsctkm;
	ResultSet rssp;
	String msg="";
	dbutils db;
	
	String ctkmchon="";
	List<ISanPhamTraKM> listsp;
	
	
	public TaodonhangKm(){
		db=new dbutils();	
		this.thang="";
		
		this.nam="";
		listsp=new ArrayList<ISanPhamTraKM>() ;	
	}
	public String getId() {

		return null;
	}

	@Override
	public void setId(String id) {
	
		
	}

	@Override
	public String getNam() {

		return this.nam;
	}

	@Override
	public void setNam(String _nam) {

		this.nam=_nam;
	}

	@Override
	public String getThang() {

		return this.thang;
	}

	@Override
	public void setThang(String _thang) {

		this.thang=_thang;
	}

	@Override
	public String getUserId() {

		return this.userid;
	}

	@Override
	public void setUserId(String userId) {

		this.userid=userId;
	}

	@Override
	public ResultSet getRsCTKM() {

		return this.rsctkm;
	}

	@Override
	public void getRsCTKM(ResultSet rs) {

		this.rsctkm=rs;
	}

	@Override
	public void Init() 
	{

		String sql=" select * from ctkhuyenmai " +
				" where loaict =1   and denngay like '"+this.nam+"-"+(thang.length()>1? thang:"0"+thang)+"%'";
		 
		this.rsctkm=db.get(sql);
		
		sql="select pk_seq,ma,tenviettat as Ten from sanpham where trangthai=1";
		this.rssp=db.getScrol(sql);
	}

	@Override
	public boolean ThucHien() 
	{
			dbutils db=new dbutils();
		try
		{
			db.getConnection().setAutoCommit(false);
		  	String sql="";
		  	
		  	sql="select  * from SANPHAMTRAKM where CTKM_FK ="+this.ctkmchon;
		  	ResultSet rs=db.get(sql);
		  	if(rs.next())
		  	{
		  		// co dữ liệu ,tiếp tục kiểm tra xem đã tạo đơn hàng khuyến mãi chưa?
		  		sql=" select * from DUYETDONHANGKM_CTKM  where TinhTrang=1 and CTKM_FK="+this.ctkmchon;
		  		rs=db.get(sql);
		  		if(rs.next())
		  		{
		  			db.getConnection().rollback();
		  			this.msg="Chương trình khuyến mãi này đã được duyệt rồi, không được duyệt lại chương trình này ";
		  			return false;
		  		}else
		  		{
		  			sql=
  					" delete d from  SANPHAMTRAKM d "+
  					" where  "+
  					" not exists "+
  					" ( "+
  					"	select * from DONDATHANG a inner join DONDATHANG_SP b  "+
  					"	on b.DONDATHANG_FK=a.PK_SEQ inner join CTKHUYENMAI c on c.SCHEME=b.SCHEME "+	
  					"	where a.NPP_FK=d.NPP_FK and d.THANG=a.thang and d.NAM=a.nam "+
  					"	and b.SANPHAM_FK=d.SPTT_FK  and c.PK_SEQ=d.CTKM_FK "+
  					" )and d.CTKM_FK="+this.ctkmchon+" ";
		  			
		  			System.out.println("[SQL]"+sql);
		  			
		  			if( !db.update(sql))
		  			{
		    	  		this.msg="Lỗi dòng lệnh : " +sql;
						geso.dms.center.util.Utility.rollback_throw_exception(db);
						return false;
		    	  	}
		  		}
		  	}
		  	
			int i=0;
			if(this.listsp.size() ==0)
			{
				this.msg="Chương trình khuyến mãi này chưa được nhà phân phối nào sử dụng để thanh toán.\n Vui lòng chọn chương trình khác.";
				return false;
			}
		      while (i<listsp.size()) 
		      { 
		    	  ISanPhamTraKM sp=listsp.get(i);
		    	  	 sql=	" insert into SANPHAMTRAKM (THANG,NAM,NPP_FK ,KBH_FK,CTKM_FK,SANPHAM_FK,SOLUONG,SPTT_FK,SOLUONGTT) values  " +
		    	  	 		" ("+this.thang+","+this.nam+","+sp.getNPPId()+","+sp.getKBHId()+","+sp.getCtkm()+","+sp.getSpId()+","+sp.getsoluong()+","+sp.getSpIdTT()+","+sp.getsoluongtt()+" ) ";
		    	  	if( !db.update(sql))
		    	  	{
		    	  		this.msg="Lỗi dòng lệnh : " +sql;
						geso.dms.center.util.Utility.rollback_throw_exception(db);
						return false;
		    	  	}
		    	  	i++;
		      }
  				db.getConnection().commit();
    			db.getConnection().setAutoCommit(true);
    			listsp.clear();
    				
		}catch(Exception er){
			er.printStackTrace();
			this.msg=er.toString();
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return false;
  
		}
		return true;	
	}
	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	@Override
	public String getMsg() {

		return this.msg;
	}
	@Override
	public void setMsg(String _msg) {

		this.msg=_msg;
		
	}
	@Override
	public void setCTKMChon(String str) {

		this.ctkmchon=str;
	}
	@Override
	public String getCTKMChon() {

		return this.ctkmchon;
	}
	@Override
	public void setListSanpham(List<ISanPhamTraKM> list) {

		listsp=list;
	}
	@Override
	public List<ISanPhamTraKM> getListSanPham() {

		return listsp;
	}
	@Override
	public ResultSet getRsSp() {

		return this.rssp;
	}
	@Override
	public void getRsSp(ResultSet rs) {

		this.rssp=rs;
	}
	@Override
	public void LoadSpKm() 
	{

		this.listsp.clear();
		String sql="SELECT DATA.CTKMID,DATA.DIENGIAI,DATA.KBH_FK,DATA.MA,DATA.NPPID,DATA.NPPTEN,DATA.SCHEME,DATA.SOLUONG,DATA.SPID,DATA.TEN, " +  
				   "  isnull(SPTRA.SPTT_FK, DATA.SPID) as SPTT_FK , isnull(SPTRA.SOLUONGTT,DATA.SOLUONG) as SOLUONGTT " +  
				   "   FROM ( " +  
				   "  SELECT NPP.KHUVUC_FK ,DH.KBH_FK,NPP.PK_SEQ AS NPPID, NPP.TENTAT AS NPPTEN,CTKM.PK_SEQ AS CTKMID,CTKM.DIENGIAI    " +  
				   "  , CTKM.SCHEME ,SP.PK_SEQ AS SPID,SP.MA,SP.TEN,    " +  
				   "  SUM( DHSP.SOLUONG) as SOLUONG      " +  
				   "  FROM DONHANG DH      " +  
				   "  INNER JOIN DONHANG_CTKM_TRAKM DHSP  ON DH.PK_SEQ=DHSP.DONHANGID          " +  
				   "  INNER JOIN SANPHAM SP ON DHSP.SPMA=SP.MA       	    " +  
				   "  INNER JOIN CTKHUYENMAI CTKM ON CTKM.PK_SEQ=DHSP.CTKMID        " +  
				   "  INNER JOIN DONVIDOLUONG DVDL ON DVDL.PK_SEQ=SP.DVDL_FK  	    " +  
				   "  INNER JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ=DH.NPP_FK    " +  
				   "  WHERE DH.TRANGTHAI=1 AND CTKM.DENNGAY LIKE  '"+this.nam+"-"+(this.thang.length()>1? this.thang:"0"+this.thang)+"%' AND DH.PK_SEQ NOT IN        " +  
				   "  (   	    " +  
				   " 	 SELECT DONHANG_FK FROM DONHANGTRAVE DHTV    	    " +  
				   " 	 WHERE  DHTV.TRANGTHAI='3'     " +  
				   " 		 AND DONHANG_FK IS NOT NULL      " +  
				   "  ) AND CTKM.PK_SEQ IN ("+this.ctkmchon+")    " +  
				   "  GROUP BY NPP.KHUVUC_FK ,DH.NPP_FK,DH.KBH_FK,SP.DVKD_FK,CTKM.SCHEME,SP.PK_SEQ,SP.MA     " +  
				   "  ,CTKM.PK_SEQ ,CTKM.DIENGIAI ,NPP.PK_SEQ, NPP.TENTAT,SP.TEN,DH.KBH_FK  " +  
				   "  ) AS DATA  " +  
				   "  LEFT JOIN 	SANPHAMTRAKM SPTRA ON SPTRA.CTKM_FK=DATA.CTKMID  AND SPTRA.KBH_FK=DATA.KBH_FK  " +  
				   "   AND SPTRA.NPP_FK=DATA.NPPID AND SPTRA.SANPHAM_FK=DATA.SPID  " +
				   "  WHERE   "+
				   "  NOT EXISTS  "+
				   " (  "+
				   "			SELECT *   "+ 
				   "		FROM DONDATHANG DH INNER JOIN DONDATHANG_SP  DHSP ON DHSP.DONDATHANG_FK=DH.PK_SEQ  "+
				   "		WHERE DHSP.SCHEME=DATA.SCHEME AND DH.NPP_FK=DATA.NPPID  AND DHSP.SANPHAM_FK=ISNULL(SPTRA.SPTT_FK,DATA.SPID )  " +
				   "		AND DH.TRANGTHAI!=6 AND DH.THANG='"+this.thang+"' AND DH.NAM='"+this.nam+"'  "+
				   "  )  "+
				   " order by  DATA.khuvuc_fk ,DATA.NPPTEN  ";
		
		System.out.println("San pham tra km :" +sql);
		List<ISanPhamTraKM> listsp1	=new ArrayList<ISanPhamTraKM>() ;
		ResultSet rs=db.get(sql);
		try
		{
			if(rs!=null)
			{
				while (rs.next())
				{
					ISanPhamTraKM a=new SanPhamTraKm();
					a.setNPPId(rs.getString("nppid"));
					a.setNPPTen(rs.getString("nppten"));
					a.setctkm(rs.getString("ctkmid"));
					a.setScheme(rs.getString("scheme"));
					a.setSpId(rs.getString("SPID"));
					a.setdiengiai(rs.getString("diengiai"));
					a.setsoluongtt(rs.getFloat("soluongtt"));
					a.setsoluong(rs.getFloat("soluong"));
					a.setspma(rs.getString("ma"));
					a.setspten(rs.getString("ten"));
					a.setSpIdTT(rs.getString("SPTT_FK"));
					a.setKBHId(rs.getString("KBH_FK"));
					listsp1.add(a);
				}
			}
			this.listsp=listsp1;
		}catch(Exception er)
		{
			er.printStackTrace();
		}
	}
}
