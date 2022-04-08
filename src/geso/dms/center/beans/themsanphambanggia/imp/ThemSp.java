package geso.dms.center.beans.themsanphambanggia.imp;


import geso.dms.center.beans.themsanphambanggia.IThemSp;

import java.sql.ResultSet;
import java.sql.SQLException;

import geso.dms.center.db.sql.*;
public class ThemSp implements IThemSp
{
	private static final long serialVersionUID = -9217977546733610214L;
	ResultSet rsbanggiamua;
	ResultSet rsbanggiaban;
	String gia;
	String loai;
	String chietkhau;
	String userid;
	String msg;
	String idbanggiaban;
	String idbanggiamua;
	ResultSet rssanpham;
	
	String spid;

	public String getSpid() {
		return spid;
	}
	public void setSpid(String spid) {
		this.spid = spid;
	}
	dbutils dbutils;
	public ResultSet getRssanpham() {
		return rssanpham;
	}
	public void setRssanpham(ResultSet rssanpham) {
		this.rssanpham = rssanpham;
	}
	public String getIdbanggiaban() {
		return idbanggiaban;
	}
	public void setIdbanggiaban(String idbanggiaban) {
		this.idbanggiaban = idbanggiaban;
	}
	public String getIdbanggiamua() {
		return idbanggiamua;
	}
	public void setIdbanggiamua(String idbanggiamua) {
		this.idbanggiamua = idbanggiamua;
	}
	
	
	public ResultSet getRsbanggiamua() {
		return rsbanggiamua;
	}
	public void setRsbanggiamua(ResultSet rsbanggiamua) {
		this.rsbanggiamua = rsbanggiamua;
	}
	public ResultSet getRsbanggiaban() {
		return rsbanggiaban;
	}
	public void setRsbanggiaban(ResultSet rsbanggiaban) {
		this.rsbanggiaban = rsbanggiaban;
	}
	public String getGia() {
		return gia;
	}
	public void setGia(String gia) {
		this.gia = gia;
	}
	public String getLoai() {
		return loai;
	}
	public void setLoai(String loai) {
		this.loai = loai;
	}
	public String getChietkhau() {
		return chietkhau;
	}
	public void setChietkhau(String chietkhau) {
		this.chietkhau = chietkhau;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public ThemSp()
	{
		this.gia="";
		this.loai="1";
		this.chietkhau="";
		this.msg="";
		this.idbanggiaban="";
		this.idbanggiamua="";
		this.spid="";
		dbutils=new dbutils();
	}
	public void init()
	{
		System.out.println("da vao init ");
		this.rsbanggiaban=dbutils.get("select PK_SEQ,TEN+'('+tungay+')' as ten from BANGGIABANLECHUAN where TRANGTHAI=1");
		this.rsbanggiamua=dbutils.get("select PK_SEQ,TEN+'('+tungay+')' as ten from BANGGIAMUANPP where TRANGTHAI=1");
		this.rssanpham=dbutils.get("select PK_SEQ,MA+'-'+TEN as ten from SANPHAM where trangthai=1 ");
		
	}
	public boolean save()
	{
		try {
			
			if(this.gia.trim().length()==0)
			{
				this.msg="Vui lòng nhập giá !!!";
				return false;
			}
			if(this.spid.trim().length()==0)
			{
				this.msg="Vui lòng Chọn sản phẩm !!!";
				return false;
			}
			dbutils.getConnection().setAutoCommit(false);
			
			if(this.loai.equals("2"))
			{
				String query="select count(*) as sl from BGMUANPP_SANPHAM where SANPHAM_FK="+this.spid+" and GIAMUANPP<>0";
				ResultSet ch=dbutils.get(query);
				ch.next();
				int sl=ch.getInt("sl");
				ch.close();
				if(sl>0)
				{
					dbutils.getConnection().rollback();
					dbutils.getConnection().setAutoCommit(true);
					this.msg="Sản phẩm này đã bán không thể cập nhật giá !!!";
					return false;
				}
				else
				{
					query="select count(*) as sl from BGMUANPP_SANPHAM where SANPHAM_FK="+this.spid+" and BGMUANPP_FK="+this.idbanggiamua+" ";
					ch=dbutils.get(query);
					ch.next();
					 sl=ch.getInt("sl");
					ch.close();
					if(sl>0)
					{
						query="update BGMUANPP_SANPHAM set GIAMUANPP="+this.gia+",chietkhau="+(this.chietkhau.equals("")?"0":this.chietkhau)+",trangthai=1 where SANPHAM_FK="+this.spid+" and BGMUANPP_FK="+this.idbanggiamua+" ";
						if(dbutils.updateReturnInt(query)!=1)
						{
							dbutils.getConnection().rollback();
							dbutils.getConnection().setAutoCommit(true);
							this.msg="lỗi trong quá trình cập nhật giá !!!";
							return false;
						}
					}
					else
					{
						query=" insert into BGMUANPP_SANPHAM (trangthai,BGMUANPP_FK,SANPHAM_FK,GIAMUANPP,chietkhau) values (1,"+this.idbanggiamua+","+this.spid+","+this.gia+","+(this.chietkhau.equals("")?"0":this.chietkhau)+") ";
						System.out.println(query);
						if(dbutils.updateReturnInt(query)!=1)
						{
							dbutils.getConnection().rollback();
							dbutils.getConnection().setAutoCommit(true);
							this.msg="lỗi trong quá trình thêm mới giá !!!";
							return false;
						}
					}
					
					dbutils.getConnection().commit();
					dbutils.getConnection().setAutoCommit(true);
					return true;
				}
			}
			else
			{

				String query="select count(*) as sl from BANGGIABLC_SANPHAM a inner join BANGGIABANLECHUAN b on a.BGBLC_FK=b.pk_seq where a.SANPHAM_FK="+this.spid+" and b.kbh_fk=(select kbh_fk from BANGGIABANLECHUAN where pk_seq="+ this.idbanggiaban +") "
						+ "and GIABANLECHUAN<>0 AND exists(select 1 from donhang a inner join donhang_sanpham b on a.pk_seq=b.donhang_fk where  trangthai!=2 and  b.SANPHAM_FK="+this.spid+" and a.ngaytao>=(select tungay from BANGGIABANLECHUAN where pk_seq="+ this.idbanggiaban +"))";
				System.out.println("check banggia"+query);
				ResultSet ch=dbutils.get(query);
				ch.next();
				int sl=ch.getInt("sl");
				ch.close();
				if(sl>0)
				{
					dbutils.getConnection().rollback();
					dbutils.getConnection().setAutoCommit(true);
					this.msg="Sản phẩm này đã bán không thể cập nhật giá !!!";
					return false ;
				}
				else
				{
					query="select count(*) as sl from BANGGIABLC_SANPHAM where SANPHAM_FK="+this.spid+" and BGBLC_FK="+this.idbanggiaban+" ";
					 
					//query="select count(*) as sl from BANGGIABLC_SANPHAM a inner join BANGGIABANLECHUAN b on a.BGBLC_FK=b.pk_seq where a.SANPHAM_FK="+this.spid+"  and a.BGBLC_FK="+this.idbanggiaban+"";
					
					System.out.println("query check bang gia "+query);
					ch=dbutils.get(query);
					ch.next();
					 sl=ch.getInt("sl");
					ch.close();
					if(sl>0)
					{
						query="update BANGGIABLC_SANPHAM set GIABANLECHUAN="+this.gia+",checkban=1 where SANPHAM_FK="+this.spid+" and BGBLC_FK="+this.idbanggiaban+" ";
						if(dbutils.updateReturnInt(query)!=1)
						{
							dbutils.getConnection().rollback();
							dbutils.getConnection().setAutoCommit(true);
							this.msg="lỗi trong quá trình cập nhật giá !!!";
							return false;
						}
						
						query=" delete b  from BANGGIABANLENPP a inner join BGBANLENPP_SANPHAM b "+
							  "	on a.PK_SEQ=b.BGBANLENPP_FK "+
							  "	where a.BANGGIABANLECHUAN_FK="+this.idbanggiaban+" and b.SANPHAM_FK="+this.spid+" ";
						
						if(dbutils.updateReturnInt(query)<=0)
						{
							dbutils.getConnection().rollback();
							dbutils.getConnection().setAutoCommit(true);
							this.msg="lỗi trong quá trình cập nhật giá !!!";
							return false;
						}
						query=	"INSERT INTO BGBANLENPP_SANPHAM(BGBANLENPP_FK,SANPHAM_FK,GIABANLENPP,GIABANLECHUAN,checkban) "+
								"SELECT    B.PK_SEQ as BGBANLENPP_FK ,A.SANPHAM_FK,A.GIABANLECHUAN,A.GIABANLECHUAN,A.checkban "+
								" FROM BANGGIABLC_SANPHAM A inner join BANGGIABANLENPP b on a.BGBLC_FK = b.BANGGIABANLECHUAN_FK "+
								" where  a.BGBLC_FK = '"+this.idbanggiaban+"'  and a.sanpham_fk="+this.spid+" ";
							
						if(dbutils.updateReturnInt(query)<=0)
						{
							dbutils.getConnection().rollback();
							dbutils.getConnection().setAutoCommit(true);
							this.msg="lỗi trong quá trình thêm mới giá !!!";
							return false;
						}
						
					}
					else
					{
						query=" insert into BANGGIABLC_SANPHAM (BGBLC_FK,SANPHAM_FK,GIABANLECHUAN,checkban) values ("+this.idbanggiaban+","+this.spid+","+this.gia+",1) ";
						if(dbutils.updateReturnInt(query)!=1)
						{
							dbutils.getConnection().rollback();
							dbutils.getConnection().setAutoCommit(true);
							this.msg="lỗi trong quá trình thêm mới giá !!!";
							return false;
						}


						query=	"INSERT INTO BGBANLENPP_SANPHAM(BGBANLENPP_FK,SANPHAM_FK,GIABANLENPP,GIABANLECHUAN,checkban) "+
								"SELECT    B.PK_SEQ as BGBANLENPP_FK ,A.SANPHAM_FK,A.GIABANLECHUAN,A.GIABANLECHUAN,A.checkban "+
								" FROM BANGGIABLC_SANPHAM A inner join BANGGIABANLENPP b on a.BGBLC_FK = b.BANGGIABANLECHUAN_FK "+
								" where  a.BGBLC_FK = '"+this.idbanggiaban+"'  and a.sanpham_fk="+this.spid+" ";
						if(dbutils.updateReturnInt(query)<=0)
						{
							dbutils.getConnection().rollback();
							dbutils.getConnection().setAutoCommit(true);
							this.msg="lỗi trong quá trình thêm mới giá !!!";
							return false;
						}

					}
					
					
					dbutils.getConnection().commit();
					dbutils.getConnection().setAutoCommit(true);
					return true;
				}
			
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	@Override
	public void Dbclose() {
		// TODO Auto-generated method stub
		dbutils.shutDown();
	}

}


