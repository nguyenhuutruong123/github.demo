package geso.dms.center.beans.tinnhan.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import geso.dms.center.beans.tinnhan.ITinNhan;
import geso.dms.center.db.sql.dbutils;

public class TinNhan implements ITinNhan
{

	public TinNhan()
	{
		this.id="";
		this.msg="";
		this.Sdt="";
		this.NgayGio="";
		this.NoiDung="";
		this.Stt="";
		this.COM="";
		this.gsbhId="";
		this.ddkdId="";
		this.khId="";
		this.smartId="";
	}
	
	public TinNhan(String id)
	{
		this.msg="";
		this.Sdt="";
		this.NgayGio="";
		this.NoiDung="";
		this.Stt="";
		this.COM="";
		this.gsbhId="";
		this.ddkdId="";
		this.khId="";
		this.smartId="";
		this.id=id;
	}
	
	
	public TinNhan(String sdt, String ngayGio, String noiDung, String stt, String cOM)
  {
		this.msg="";
	  Sdt = sdt;
	  NgayGio = ngayGio;
	  NoiDung = noiDung;
	  Stt = stt;
	  COM = cOM;
  }

	String Sdt,NgayGio,NoiDung,Stt,COM,msg;

	public String getMsg()
  {
  	return msg;
  }

	public void setMsg(String msg)
  {
  	this.msg = msg;
  }

	public String getSdt()
  {
  	return Sdt;
  }

	public void setSdt(String sdt)
  {
  	Sdt = sdt;
  }

	public String getNgayGio()
  {
  	return NgayGio;
  }

	public void setNgayGio(String ngayGio)
  {
  	NgayGio = ngayGio;
  }

	public String getNoiDung()
  {
  	return NoiDung;
  }

	public void setNoiDung(String noiDung)
  {
  	NoiDung = noiDung;
  }

	public String getStt()
  {
  	return Stt;
  }

	public void setStt(String stt)
  {
  	Stt = stt;
  }

	public String getCOM()
  {
  	return COM;
  }

	public void setCOM(String cOM)
  {
  	COM = cOM;
  }

	@Override
  public String Save(dbutils db)
  {
		 this.msg="";
		 try
	  {
		  db.getConnection().setAutoCommit(false);
		  String query=
		  		"Insert into Log_InBox(CongCOM,STT,NgayGioTN,SoDT,NoiDung,TrangThai,NgayTao,NPP_FK,DDKD_FK,DienThoai)" +
		  		"select '"+this.COM+"','"+this.Stt+"','"+this.NgayGio+"','"+this.Sdt+"',N'"+this.NoiDung+"','"+0+"','"+this.getDateTime()+"',(select top(1) pk_Seq From DaiDienKinhDoanh where dienthoai='0'+SUBSTRING('"+this.Sdt+"',4,len('"+this.Sdt+"'))) as ddkd_Fk,(select top(1) npp_fk From DaiDienKinhDoanh where dienthoai='0'+SUBSTRING('"+this.Sdt+"',4,len('"+this.Sdt+"')) )as npp_fk,'0'+SUBSTRING('"+this.Sdt+"',4,len('"+this.Sdt+"')) " +
		  		" where not exists ( select 1 from  Log_InBox where SoDT='"+Sdt+"' and NgayGioTN='"+NgayGio+"' and NoiDung='"+this.NoiDung+"' ) ";
		  System.out.println("[Log_InBox]"+query);
		  if(!db.update(query))
		  {
		  	msg="Lỗi phát sinh "+query;
		  }		  
		  db.getConnection().commit();
		  db.getConnection().setAutoCommit(true);
	  } catch (Exception e)
	  {
	  	msg="Lỗi phát sinh !"+e.getMessage();
		  e.printStackTrace();
	  }
		 return msg;
		 
  }
	
	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	@Override
  public String Convert(dbutils db)
  {
		String msg="";
		
		if(this.nppId.length()<=0)
  	{
  		msg +="\n Không xác định được NPP ";
  	}
		
		if(this.ddkdId.length()<=0)
  	{
  		msg +="\n Không xác định được NVBH ";
  	}
		
		if(this.gsbhId.length()<=0)
  	{
  		msg +="\n Không xác định được GSBH";
  	}
		
		if(this.khId.length()<=0)
  	{
  		msg +="\n Không xác định được Khách Hàng";
  	}
		
		if(this.NoiDung.length()<=0)
  	{
  		msg +="\n Không xác định được Nội Dung Tin Nhắn";
  	}
		
		if(this.smartId.length()<=0)
  	{
  		msg +="\n Không xác định được Khách Hàng";
  	}
		String query = NoiDung;
		String kbhId="100025";
		String khoId="100000";
		String spMa="";
		String spSoLuong="";
		String ngayNhap=
				"	select   CONVERT(varchar(10), DATEADD(day,1 ,MAX(ngayks)),20)as NgayGhiNhan  "+ 
				"	from KHOASONGAY where NPP_FK='"+nppId+"'    ";
		String thongtinDH=query.split(",")[1];
		System.out.println("[THONGTIN]"+thongtinDH);
		
		String SANPHAM_SQL="";
		int soSanPham=thongtinDH.split(";").length;
		String[] donhang_sanpham=thongtinDH.split(";");
		for(int i=0;i<soSanPham;i++)
		{
			spMa=donhang_sanpham[i].split(" ")[0];
			spSoLuong=donhang_sanpham[i].split(" ")[1];
			System.out.println("[spMa]"+spMa+"[spSoLuong]"+spSoLuong);
			if(i==0)
			{
				SANPHAM_SQL=" select '"+spMa+"' as spMa,'"+spSoLuong+"' as spSoLuong ,'"+nppId+"' as nppId,'"+khoId+"'  as khoId,'"+kbhId+"' as kbhId \n";
			}
			else 
				SANPHAM_SQL +=" union all select '"+spMa+"' as spMa,'"+spSoLuong+"' as spSoLuong ,'"+nppId+"' as nppId,'"+khoId+"'  as khoId,'"+kbhId+"' as kbhId \n";
		}
		query=
		"	select data.spMa,data.spSoLuong,kho.AVAILABLE,kho.SOLUONG,kho.BOOKED ," +
		" (  "+
		"  	select b.GIABANLENPP from BANGGIABANLENPP a inner join BGBANLENPP_SANPHAM b on b.BGBANLENPP_FK=a.PK_SEQ  "+
		" 		where b.SANPHAM_FK=sp.PK_SEQ and a.NPP_FK=data.nppId and a.DVKD_FK=sp.DVKD_FK  "+
		"  	) as GiaBan \n"+
		"	from  \n"+
		"	( "+SANPHAM_SQL+"	)as data left join SANPHAM sp on sp.MA=data.spMa  \n"+
		"	left join NHAPP_KHO kho on kho.NPP_FK=data.nppId and kho.SANPHAM_FK=sp.PK_SEQ  \n"+
		"	and kho.KBH_FK=data.kbhId and kho.KHO_FK=data.khoId  ";
		
		
		System.out.println("[query]"+query);
		ResultSet rs = db.get(query);
		try
	  {
	    while(rs.next())
	    {
	    	
	    	spMa=rs.getString("spMa");
	    	double _spSoLuong = rs.getDouble("spSoLuong");
	    	double spSoLuongKho = rs.getDouble("AVAILABLE");
	    	double GiaBan = rs.getDouble("GiaBan");
	    	if(rs.getString("SOLUONG")==null)
	    	{
	    		msg +="\n Không xác định được sản phẩm "+spMa ;
	    	}
	    	if(rs.getString("BOOKED")==null)
	    	{
	    		msg +="\n Vui lòng tạo dữ liệu nền kho cho sản phẩm "+spMa ;
	    	}
	    	
	    	if(rs.getString("GiaBan")==null||GiaBan==0)
	    	{
	    		msg +="\n Vui lòng tạo dữ liệu nền Gía Bán cho sản phẩm "+spMa ;
	    	}
	    	
	    	if(_spSoLuong>spSoLuongKho)
	    	{
	    		msg +="\n Số lượng trong kho sản phẩm "+spMa+" ("+_spSoLuong+" || "+spSoLuongKho+" ) không đủ "; 
	    	}
	    }
	    if(rs!=null)rs.close();
	    if(msg.length()>0)
			{
				return msg;
			}
			
	  	query=
		    	"insert into DonHang(ngaynhap, trangthai, ngaytao, ngaysua, nguoitao, nguoisua, vat, tonggiatri, ddkd_fk, gsbh_fk, khachhang_fk, npp_fk, kho_fk, kbh_fk, tinhtrang,BM,ASM,IsChiNhanh,TinNhan_FK,SmartId)" +
		    	" select ("+ngayNhap+"),0,'"+getDateTime()+"','"+getDateTime()+"','"+this.userId+"' as nguoitao,'"+this.userId+"' as nguoisua,0 as vat,0 as tonggiatri,'"+ddkdId+"',"+gsbhId+","+khId+","+this.nppId+",'"+khoId+"','"+kbhId+"',0,NULL,NULL,0,'"+this.id+"','"+this.smartId+"' ";
		    	
		    	if(!db.update(query))
		    	{
		    		msg +=" Không thể tạo đơn hàng"+query;
		    		return msg;
		    	}
		    	query="select scope_identity() as dhId ";
		    	rs=db.get(query);
		    	String dhId="";
		    	while(rs.next())
		    	{
		    		dhId=rs.getString("dhId");
		    	}		    	
		    	query=
		    			" insert into DONHANG_SANPHAM(DONHANG_FK,SANPHAM_FK,SOLUONG,KHO_FK,GIAMUA,CHIETKHAU) " +
		    			"select '"+dhId+"',kho.SANPHAM_FK,data.spSoLuong,kho.KHO_FK,   "+
		    			" (   "+
		    			"	select b.GIABANLENPP from BANGGIABANLENPP a inner join BGBANLENPP_SANPHAM b on b.BGBANLENPP_FK=a.PK_SEQ  "+
		    			"	where b.SANPHAM_FK=sp.PK_SEQ and a.NPP_FK=data.nppId and a.DVKD_FK=sp.DVKD_FK  "+
		    			"  ) as GiaBan,0 as ck  "+
		    		  " from   ( "+SANPHAM_SQL+" ) "+
		    		  " as data left join SANPHAM sp on sp.MA=data.spMa    "+
		    		  " left join NHAPP_KHO kho on kho.NPP_FK=data.nppId and kho.SANPHAM_FK=sp.PK_SEQ  "+  
		    		  " and kho.KBH_FK=data.kbhId and kho.KHO_FK=data.khoId  ";  
		    	if(!db.update(query))
		    	{
		    		msg +=" Không thể tạo đơn hàng"+query;
		    		return msg;
		    	}
		    	query=
				"	update b set BOOKED=b.BOOKED+a.SOLUONG,AVAILABLE=b.AVAILABLE-a.SOLUONG  "+
				"	from  "+
				"	(  "+
				"		select a.NPP_FK,a.KBH_FK,b.KHO_FK,b.SANPHAM_FK,b.SOLUONG  "+ 
				"		from DONHANG a inner join DONHANG_SANPHAM b on b.DONHANG_FK=a.PK_SEQ  "+
				"		where a.PK_SEQ='"+dhId+"'  "+
				"	)as a inner join NHAPP_KHO b on a.NPP_FK=b.NPP_FK and a.KBH_FK=b.KBH_FK  "+ 
				"	and a.KHO_FK=b.KHO_FK and a.SANPHAM_FK=b.SANPHAM_FK ";
		    	
		    	if(!db.update(query))
		    	{
		    		msg +=" Không thể tạo đơn hàng"+query;
		    		return msg;
		    	}
		    	
		    	query = "update Log_InBox set trangthai = '1',NguoiSua='"+userId+"',NgayGioSua=dbo.GetLocalDate(DEFAULT),npp_fk='"+this.nppId+"',ddkd_fk='"+this.ddkdId+"',gsbh_Fk="+this.gsbhId+",KhachHang_fk="+this.khId+",DonHang_fk='"+dhId+"' where pk_seq = '" + id + "' and TrangThai=0 ";
					if(db.updateReturnInt(query)!=1)
					{
						msg += "Tin nhắn đã chuyển thành đơn hàng ,vui lòng kiểm tra lại. "+query;
						return msg;
					}
					
					query= "select GSBH_FK,DDKD_FK,NPP_FK,KHACHHANG_FK from DONHANG where PK_SEQ='"+dhId+"' ";
					rs=db.get(query);
					while(rs.next())
					{
						gsbhId= rs.getString("GSBH_FK")==null?"":rs.getString("GSBH_FK");
						khId = rs.getString("KHACHHANG_FK")==null?"":rs.getString("KHACHHANG_FK");
						nppId  = rs.getString("NPP_FK")==null?"":rs.getString("NPP_FK");
						ddkdId  = rs.getString("DDKD_FK")==null?"":rs.getString("DDKD_FK");
					}
					if(this.nppId.length()<=0)
			  	{
			  		msg +="\n Không xác định được NPP ";
			  	}
					
					if(this.ddkdId.length()<=0)
			  	{
			  		msg +="\n Không xác định được NVBH ";
			  	}
					
					if(this.gsbhId.length()<=0)
			  	{
			  		msg +="\n Không xác định được GSBH";
			  	}
					
					if(this.khId.length()<=0)
			  	{
			  		msg +="\n Không xác định được Khách Hàng";
			  	}
					if(msg.length()>0)
					{
						return msg;
					}
	  } catch (SQLException e)
	  {
	    e.printStackTrace();
	  }
	  return "";
  }
	
	String userId,id,dienthoai,nppId,ddkdId,gsbhId,khId,smartId;

	public String getSmartId()
  {
  	return smartId;
  }

	public void setSmartId(String smartId)
  {
  	this.smartId = smartId;
  }

	public String getNppId()
  {
  	return nppId;
  }


	public String getGsbhId()
  {
  	return gsbhId;
  }


	public void setGsbhId(String gsbhId)
  {
  	this.gsbhId = gsbhId;
  }


	public String getKhId()
  {
  	return khId;
  }


	public void setKhId(String khId)
  {
  	this.khId = khId;
  }


	public void setNppId(String nppId)
  {
  	this.nppId = nppId;
  }


	public String getDdkdId()
  {
  	return ddkdId;
  }


	public void setDdkdId(String ddkdId)
  {
  	this.ddkdId = ddkdId;
  }


	public String getDienthoai()
  {
  	return dienthoai;
  }


	public void setDienthoai(String dienthoai)
  {
  	this.dienthoai = dienthoai;
  }


	public String getId()
  {
  	return id;
  }


	public void setId(String id)
  {
  	this.id = id;
  }


	public String getUserId()
  {
  	return userId;
  }


	public void setUserId(String userId)
  {
  	this.userId = userId;
  }


	@Override
  public void init()
  {
		String query=
				"select a.pk_Seq,SoDT,NoiDung,a.dienthoai,b.NPP_FK,b.pk_Seq as ddkd_fk  "+
        " from Log_InBox a inner join DAIDIENKINHDOANH b on b.DIENTHOAI=a.dienthoai  "+
        " where a.pk_Seq='"+this.id+"' ";
		
		System.out.println("[Query]"+query);
		
		dbutils db = new dbutils();
		ResultSet rs = db.get(query);
		try
    {
	    while(rs.next())
	    {
	    	this.Sdt= rs.getString("SoDT");
	    	this.NoiDung =rs.getString("NoiDung");
	    	this.dienthoai= rs.getString("dienthoai");
	    	this.nppId =  rs.getString("NPP_FK");
	    	this.ddkdId = rs.getString("ddkd_fk");
	    	this.smartId= this.NoiDung.split(",")[0];
	  		this.gsbhId=
	  				"( select top(1) b.GSBH_FK from DDKD_GSBH a inner join NHAPP_GIAMSATBH b on b.GSBH_FK=a.GSBH_FK where b.NGAYBATDAU<='"+getDateTime()+"' and a.DDKD_FK='"+ddkdId+"' order by b.NGAYKETTHUC desc ) ";
	  		 this.khId="(select pk_seq from khachHang where smartId='"+smartId+"' and npp_Fk='"+this.nppId+"' ) ";
	    }
	    if(rs!=null)rs.close();
    } catch (Exception e)
    {
    	this.msg="Lỗi INIT "+e.getMessage();
	    e.printStackTrace();
    }
		finally
		{
			if(db!=null)db.shutDown();
		}
  }
	
}

