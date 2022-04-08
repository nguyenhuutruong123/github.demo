package geso.dms.center.beans.tieuchithuong.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import geso.dms.center.beans.ctkhuyenmai.imp.Ctkhuyenmai;
import geso.dms.center.beans.tieuchithuong.ITichLuyItem;
import geso.dms.center.beans.tieuchithuong.ITieuchithuongTL;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;

public class TieuchithuongTL implements ITieuchithuongTL 
{
	String userId;
	String id;
	String scheme;
	String thang;
	String nam;
	String diengiai;
	String kt = "0";
	ResultSet sanphamRs;
	String spIds;
	ResultSet nppRs;
	
	String active = "0";
	ResultSet kenhRs;
	String kenhIds;
	ResultSet vungRs;
	String vungIds;
	ResultSet kvRs;
	String kvIds;
	String mucnpp = "0";
	ResultSet nhomsanphamRs;
	String nhomspIds;	
	String ghighu = "";
	
	Hashtable<String, String> maspTraTT = new Hashtable<String, String>();;
	Hashtable<String, String> tenspTraTT = new Hashtable<String, String>();;
	Hashtable<String, String> soluongTT = new Hashtable<String, String>();;
	
	String doanhsotheoThung;
	String msg;
	String httt;
	String ptchietkhau;	
	String tungay;
	String dengay;
	String khoId;
	ResultSet khoRs;	
	String phanloai;
	
	dbutils db;
	
	String[] DKKMTICHLUY_KHACHHANG_Id;	
	String[] khDcDuyet;
	String[] khDcDuyetDisplay;
	ResultSet khDangKyRs;	
	String ngaytb_tungay;
	String ngaytb_dengay;	
	String phaidangky;
	String tinhtheosl;
	Utility  util =new Utility();	
	ArrayList<ITichLuyItem> tichluyItemList = new ArrayList();
	
	ArrayList<String> spMuaList = new ArrayList();
	public ResultSet sanphamMuaRs ;
	
	public void setSanphamMuaRs() {
		String data = "";
		List<Object> list = new ArrayList<Object>();
		for(int i = 0; i < spMuaList.size(); i++ )
		{			
			list.add(spMuaList.get(i));
			if(data.trim().length() > 0 )
				data +=",?";
			else
				data= "?";
		}
		if(data.trim().length() > 0)
		{
			String query = " select pk_seq,ma,ten from sanpham where ma in ("+data+")";
			this.sanphamMuaRs = db.getByPreparedStatement(query, list);
			
		}
		
	}
	public ResultSet getSanphamMuaRs() {
		return sanphamMuaRs;
	}
	public ArrayList<String> getSpMuaList() {
		return spMuaList;
	}
	public void setSpMuaList(ArrayList<String> spMuaList) {
		this.spMuaList = spMuaList;
	}
	
	
	public TieuchithuongTL()
	{
		this.id = "";
		this.thang = "";
		this.nam = "";
		this.diengiai = "";
		this.msg = "";
		this.scheme = "";
		this.spIds = "";
		
		
		this.vungIds = "";
		this.kvIds = "";
		this.kenhIds = "";
		
		this.doanhsotheoThung = "0";
		this.httt = "0";
		this.ptchietkhau = "0";
	
		this.tungay = "";
		this.dengay = "";
		this.khoId = "";
		this.phanloai = "0";
		this.nhomspIds = "";
		this.mucnpp = "0";
		this.phaidangky = "0";
		
		this.ngaytb_tungay = "";
		this.ngaytb_dengay = "";
		this.tinhtheosl = "0";
		this.db = new dbutils();
	}
	
	public TieuchithuongTL(String id)
	{
		this.id = id;
		this.thang = "";
		this.nam = "";
		this.diengiai = "";
		this.msg = "";
		this.scheme = "";
		this.spIds = "";
	
		
		
		
		this.vungIds = "";
		this.kvIds = "";
		this.kenhIds = "";
		
		
		this.doanhsotheoThung = "0";
		this.httt = "0";
		this.ptchietkhau = "0";
		
	
		this.mucnpp = "0";
		
		this.tungay = "";
		this.dengay = "";
		this.khoId = "";
		this.phanloai = "0";
		this.nhomspIds = "";
		this.phaidangky = "0";
		this.tinhtheosl = "0";
		this.ngaytb_tungay = "";
		this.ngaytb_dengay = "";
		
		this.db = new dbutils();
	}
	
	public String getUserId()
	{
		return this.userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public String getThang() 
	{
		return this.thang;
	}
	
	public void setThang(String thang) 
	{
		this.thang = thang;
	}
	
	public String getNam() 
	{
		return this.nam;
	}
	
	public void setNam(String nam)
	{
		this.nam = nam;
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

	public String getMsg()
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}

	
	public boolean createTctSKU( ) 
	{
		try
		{
			

			Object[] data;
			
			int[] quyen = util.Getquyen("TieuchithuongTLSvl", "",this.userId);
			if(quyen[Utility.THEM]!=1)
			{
				this.msg = "User không được phân quyền sửa!";
				return false;
			}
			
			if(!Ctkhuyenmai.kiemtra_scheme(this.db,this.scheme,this.id))
			{		
				this.msg = "Scheme khuyến mại đã tồn tại, vui lòng nhập lại";
				return false;
			}
			
			
			if(this.thang.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn ngày bắt đầu CT";
				return false;
			}
			
			if(this.nam.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn ngày kết thúc CT";
				return false;
			}
			
			if(this.tungay.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn ngày bắt đầu tính doanh số";
				return false;
			}
			
			if(this.dengay.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn ngày kết thúc tính doanh số";
				return false;
			}
			
			
			if(this.scheme.trim().length() <= 0)
			{
				this.msg = "Vui lòng scheme";
				return false;
			}
			
			//Check Scheme
			String query = "select count(*) as sodong from TIEUCHITHUONGTL where scheme =? ";
			data = new Object[]{this.scheme};
			ResultSet rs = db.getByPreparedStatement(query, data);
			if(rs != null)
			{
				int count = 0;
				if(rs.next())
				{
					count = rs.getInt("sodong");
					if(count > 0)
					{
						this.msg = "Scheme: " + this.scheme + " đã tồn tại, vui lòng nhập scheme khác";
						rs.close();
						return false;
					}
				}
				rs.close();
			}
			
			//Check tieu chi
			if(this.tichluyItemList.size()<=0)
			{
				this.msg = "Vui lòng nhập tiêu chí ";
				return false;
			}
			
			double tuMin = -1;
			for(int i = 0; i < this.tichluyItemList.size(); i++)
			{
				 ITichLuyItem tl = this.tichluyItemList.get(i);
				 if( tl.getTumuc() < tuMin )
				 {
					 this.msg = "  [Doanh số từ mức] của "+tl.getDiengiai()+" phải lớn hơn ("+ format.format(tuMin) +")";
					 return false;
				 }
				
				 if( tl.getDenmuc() <= tl.getTumuc() )
				 {
					 this.msg = " [Doanh số đến mức] của "+tl.getDiengiai()+" phải lớn hơn ("+ format.format(tl.getTumuc()) +")";
					 return false;
				 }
				 tuMin =  tl.getDenmuc();
				 if(tl.getLoaitra() ==2  )
				 {
					 if(tl.getSpList().size() <=0)
					 {
						 this.msg = " Vui lòng chọn sản phẩm trả của "+tl.getDiengiai()+" ";
						 return false;
					 }
				 }
				 
				 if(tl.getTratichluy() <=0 )
				 {
					 if(tl.getLoaitra() == 0 || tl.getLoaitra() == 1) // trả tiên hoac ck
					 {
						 this.msg = " Vui lòng nhập trả tích lũy lớn hơn 0  của "+tl.getDiengiai()+" ";
						 return false;
					 }
					 if(tl.getLoaitra() ==2  )// trả sp và bất kỳ trong
					 {
						 if(tl.getHinhthuc() == 0)// trả bất kỳ trong
						 {
							 this.msg = " Vui lòng nhập tổng lượng trả cho sản phẩm  của "+tl.getDiengiai()+" ";
							 return false;
						 }
						 if(tl.getHinhthuc() == 1) // tra bắt buộc
						 {
							 for(int x = 0 ; x< tl.getSpList().size();x++)
							 {
								 String matra= tl.getSpList().get(x)[0].toString();
								 double slbatbuoc = Utility.parseDouble(tl.getSpList().get(x)[1].toString());
								 if( slbatbuoc  <=0 )
								 {
									 this.msg = " Vui lòng nhập số lượng  trả cho sản phẩm "+matra +"  , của "+tl.getDiengiai()+" ";
									 return false;
								 }
							 }
						 }
					 }
					 
				 }
					 
			}
			if(this.spMuaList.size() <= 0)
			{
				this.msg = "Vui lòng chọn sản phẩm trong điều kiện mua";
				return false;
			}
		
		
			if(this.khoId.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn kho áp dụng";
				return false;
			}
			
			db.getConnection().setAutoCommit(false);
			
			
			query = "insert TieuchithuongTL(scheme, thang, nam, diengiai, trangthai, ngaytao, nguoitao, ngaysua, nguoisua, ngayds_tungay, ngayds_denngay, khoId, phanloai ,ghichu, DOANHSOTHEOLUONG) " +
				"select ?,?,?,?,0, '" + this.getDateTime() + "', ?, '" + this.getDateTime() + "', ?, ?,?, ?,0 ,?,? ";
			
			
			data= new Object[]   {  this.scheme,this.thang,this.nam ,this.diengiai
								,this.userId,this.userId,this.tungay,this.dengay,this.khoId, this.ghighu , this.tinhtheosl};
			if(db.updateQueryByPreparedStatement(query, data) <= 0)
			{
				this.msg = "Lỗi  TieuchithuongTL (1) ";
				db.getConnection().rollback();
				return false;
			}
			
			
			this.id=db.getPk_seq();
			
			
			
			query = " insert CTKHUYENMAI(TUNGAY,DENNGAY,scheme,DIENGIAI ,thuongtl_fk,kho_fk) " +
			" select '2000-01-01','2100-12-31',scheme,DIENGIAI,PK_SEQ,khoId from TIEUCHITHUONGTL where pk_seq= " + this.id;
			if(db.updateReturnInt(query) <= 0)
			{
				this.msg = "Lỗi  CTKHUYENMAI (1) ";
				db.getConnection().rollback();
				return false;
			}
			
			for(int i = 0; i < this.tichluyItemList.size(); i++)
			{
				 ITichLuyItem tl = this.tichluyItemList.get(i);	 
				 query = " insert TieuchithuongTL_TIEUCHI(thuongtl_fk,  tumuc, denmuc, chietkhau, donvi, muc,hinhthuc) " +
						 " select '" + this.id + "' as tctId,?, ?, ?, ?, ?,? ";
				 System.out.println(query);
				 	data= new Object[]   { tl.getTumuc(),tl.getDenmuc(),tl.getTratichluy(),tl.getLoaitra(),i,tl.getHinhthuc()  };
				 	if(db.updateQueryByPreparedStatement(query, data)<=0 )
					{
						this.msg = "Lỗi  TieuchithuongTL 4.1."+i+" ";
						db.getConnection().rollback();
						return false;
					}			
					if(tl.getLoaitra() == 2)
					{
						List<Object> dataTra = new ArrayList<Object>();
						String sqlSpTra = "";
						for(int x = 0 ; x < tl.getSpList().size(); x++ )
						{
							Object[] spX = tl.getSpList().get(x);
							if(sqlSpTra.trim().length() > 0)
								sqlSpTra += "\n union all select "+this.id+", pk_seq ,?,?,? from sanpham where ma = ? ";
							else
								sqlSpTra += " select "+this.id+", pk_seq ,?,?,? from sanpham where ma = ?  ";
							
							dataTra.add(""+Utility.parseDouble(spX[1].toString()));
							dataTra.add(i+"");
							dataTra.add(tl.getHinhthuc() +"");
							dataTra.add(spX[0]+"");// ma sp
							
						}
						query = " insert TIEUCHITHUONGTL_SPTRA( thuongtl_fk, sanpham_fk, soluong, muctra, hinhthuctra ) " + sqlSpTra;
						if(db.updateQueryByPreparedStatement(query, dataTra)<=0 )
						{
							dbutils.viewQuery(query, dataTra);
							this.msg = "Lỗi  TieuchithuongTL 4.2."+i+" ";
							db.getConnection().rollback();
							return false;
						}		
					}
					
			}
			
			
			for(int i = 0; i < this.spMuaList.size(); i ++)
			{
				query = "Insert TIEUCHITHUONGTL_SANPHAM(thuongtl_fk, sanpham_fk) select '" + this.id + "', pk_seq from SanPham where ma = ? ";
				data= new Object[]   { this.spMuaList.get(i)  };				
				if(db.updateQueryByPreparedStatement(query, data)<=0 )
				{
					dbutils.viewQuery(query, data);
					this.msg = "Lỗi  TieuchithuongTL 5";
					db.getConnection().rollback();
					return false;
				}
			}

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return true;

		} 
		catch (Exception e)
		{
			try 
			{
				db.getConnection().rollback();
				System.out.println("__EXCEPTION UPDATE: " + e.getMessage());
				e.printStackTrace();
				this.msg = "Lỗi khi tạo mới CT tích lũy";
			} 
			catch (SQLException e1) {}
			
			return false;
		}
		
		
	}
	NumberFormat format = new DecimalFormat("#,###,###");
	public boolean updateTctSKU()
	{
		try
		{
			Object[] data;
			
			int[] quyen = util.Getquyen("TieuchithuongTLSvl", "",this.userId);
			if(quyen[Utility.SUA]!=1)
			{
				this.msg = "User không được phân quyền sửa!";
				return false;
			}
			
			if(!Ctkhuyenmai.kiemtra_scheme(this.db,this.scheme," ( select top 1 pk_seq from ctkhuyenmai where thuongtl_fk = "+this.id+" ) "))
			{		
				this.msg = "Scheme khuyến mại đã tồn tại, vui lòng nhập lại";
				return false;
			}
			
			if(this.thang.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn ngày bắt đầu";
				return false;
			}
			
			if(this.nam.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn ngày kết thúc";
				return false;
			}
			
			if(this.scheme.trim().length() <= 0)
			{
				this.msg = "Vui lòng scheme";
				return false;
			}
			
			//Check Scheme
			String query = "select count(*) as sodong from TIEUCHITHUONGTL where scheme =?  and pk_seq != '" + this.id + "'";
			data = new Object[]{this.scheme};
			ResultSet rs = db.getByPreparedStatement(query, data);
			if(rs != null)
			{
				int count = 0;
				if(rs.next())
				{
					count = rs.getInt("sodong");
					if(count > 0)
					{
						this.msg = "Scheme: " + this.scheme + " đã tồn tại, vui lòng nhập scheme khác";
						rs.close();
						return false;
					}
				}
				rs.close();
			}
			
			//Check tieu chi
			if(this.tichluyItemList.size()<=0)
			{
				this.msg = "Vui lòng nhập tiêu chí ";
				return false;
			}
			
			double tuMin = -1;
			for(int i = 0; i < this.tichluyItemList.size(); i++)
			{
				 ITichLuyItem tl = this.tichluyItemList.get(i);
				 if( tl.getTumuc() < tuMin )
				 {
					 this.msg = "  [Doanh số từ mức] của "+tl.getDiengiai()+" phải lớn hơn ("+ format.format(tuMin) +")";
					 return false;
				 }
				
				 if( tl.getDenmuc() <= tl.getTumuc() )
				 {
					 this.msg = " [Doanh số đến mức] của "+tl.getDiengiai()+" phải lớn hơn ("+ format.format(tl.getTumuc()) +")";
					 return false;
				 }
				 tuMin =  tl.getDenmuc();
				 if(tl.getLoaitra() ==2  )
				 {
					 if(tl.getSpList().size() <=0)
					 {
						 this.msg = " Vui lòng chọn sản phẩm trả của "+tl.getDiengiai()+" ";
						 return false;
					 }
				 }
				 
				 if(tl.getTratichluy() <=0 )
				 {
					 if(tl.getLoaitra() == 0 || tl.getLoaitra() == 1) // trả tiên hoac ck
					 {
						 this.msg = " Vui lòng nhập trả tích lũy lớn hơn 0  của "+tl.getDiengiai()+" ";
						 return false;
					 }
					 if(tl.getLoaitra() ==2  )// trả sp và bất kỳ trong
					 {
						 if(tl.getHinhthuc() == 0)// trả bất kỳ trong
						 {
							 this.msg = " Vui lòng nhập tổng lượng trả cho sản phẩm  của "+tl.getDiengiai()+" ";
							 return false;
						 }
						 if(tl.getHinhthuc() == 1) // tra bắt buộc
						 {
							 for(int x = 0 ; x< tl.getSpList().size();x++)
							 {
								 String matra= tl.getSpList().get(x)[0].toString();
								 double slbatbuoc = Utility.parseDouble(tl.getSpList().get(x)[1].toString());
								 if( slbatbuoc  <=0 )
								 {
									 this.msg = " Vui lòng nhập số lượng  trả cho sản phẩm "+matra +"  , của "+tl.getDiengiai()+" ";
									 return false;
								 }
							 }
						 }
					 }
					 
				 }
					 
			}
			if(this.spMuaList.size() <= 0)
			{
				this.msg = "Vui lòng chọn sản phẩm trong điều kiện mua";
				return false;
			}
		
		
			if(this.khoId.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn kho áp dụng";
				return false;
			}
			
			db.getConnection().setAutoCommit(false);
			
			query = 	"\n update TieuchithuongTL set scheme =? , thang = ?, nam = ?, diengiai =? , " +
						"\n 	ngaysua = ? , nguoisua =?,ngayds_tungay = ? , ngayds_denngay =? , khoId = ?, ghichu =? , DOANHSOTHEOLUONG =?  " +
						"\n where pk_seq = '" + this.id + "' and trangthai = 0";
			data= new Object[]   {  this.scheme,this.thang,this.nam ,this.diengiai, this.getDateTime() 
								,this.userId,this.tungay,this.dengay,this.khoId, this.ghighu , this.tinhtheosl};
			if(db.updateQueryByPreparedStatement(query, data) <= 0)
			{
				this.msg = "Lỗi  TieuchithuongTL (1) ";
				db.getConnection().rollback();
				return false;
			}
			
			
			query = "delete TieuchithuongTL_TIEUCHI where thuongtl_fk = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Lỗi  TieuchithuongTL (2) ";
				db.getConnection().rollback();
				return false;
			}
			query = "delete TIEUCHITHUONGTL_SANPHAM where thuongtl_fk = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Lỗi  TieuchithuongTL (3) ";
				db.getConnection().rollback();
				return false;
			}
			query = "delete TIEUCHITHUONGTL_SPTRA where thuongtl_fk = '" + this.id + "' ";
			if(!db.update(query))
			{
				this.msg = "Lỗi  TieuchithuongTL (3.1) ";
				db.getConnection().rollback();
				return false;
			}
			query = "delete CTKHUYENMAI where thuongtl_fk = '" + this.id + "' ";
			if(!db.update(query))
			{
				this.msg = "Xóa km cũ! ";
				db.getConnection().rollback();
				return false;
			}
			
			
			query = " insert CTKHUYENMAI(TUNGAY,DENNGAY,scheme,DIENGIAI ,thuongtl_fk,kho_fk) " +
			" select '2000-01-01','2100-12-31',scheme,DIENGIAI,PK_SEQ,khoId from TIEUCHITHUONGTL where pk_seq= " + this.id;
			if(db.updateReturnInt(query) <= 0)
			{
				this.msg = "Lỗi  CTKHUYENMAI (1) ";
				db.getConnection().rollback();
				return false;
			}
			
			
			for(int i = 0; i < this.tichluyItemList.size(); i++)
			{
				 ITichLuyItem tl = this.tichluyItemList.get(i);	 
				 query = " insert TieuchithuongTL_TIEUCHI(thuongtl_fk,  tumuc, denmuc, chietkhau, donvi, muc,hinhthuc) " +
						 " select '" + this.id + "' as tctId,?, ?, ?, ?, ?,? ";
				 
				 	data= new Object[]   { tl.getTumuc(),tl.getDenmuc(),tl.getTratichluy(),tl.getLoaitra(),i,tl.getHinhthuc()  };
				 	if(db.updateQueryByPreparedStatement(query, data)<=0 )
					{
						this.msg = "Lỗi  TieuchithuongTL 4.1."+i+" ";
						db.getConnection().rollback();
						return false;
					}			
					if(tl.getLoaitra() == 2)
					{
						List<Object> dataTra = new ArrayList<Object>();
						String sqlSpTra = "";
						for(int x = 0 ; x < tl.getSpList().size(); x++ )
						{
							Object[] spX = tl.getSpList().get(x);
							if(sqlSpTra.trim().length() > 0)
								sqlSpTra += "\n union all select "+this.id+", pk_seq ,?,?,? from sanpham where ma = ? ";
							else
								sqlSpTra += " select "+this.id+", pk_seq ,?,?,? from sanpham where ma = ?  ";
							
							dataTra.add(""+Utility.parseDouble(spX[1].toString()));
							dataTra.add(i+"");
							dataTra.add(tl.getHinhthuc() +"");
							dataTra.add(spX[0]+"");// ma sp
							
						}
						query = " insert TIEUCHITHUONGTL_SPTRA( thuongtl_fk, sanpham_fk, soluong, muctra, hinhthuctra ) " + sqlSpTra;
						if(db.updateQueryByPreparedStatement(query, dataTra)<=0 )
						{
							dbutils.viewQuery(query, dataTra);
							this.msg = "Lỗi  TieuchithuongTL 4.2."+i+" ";
							db.getConnection().rollback();
							return false;
						}		
					}
					
			}
			
			
			for(int i = 0; i < this.spMuaList.size(); i ++)
			{
				query = "Insert TIEUCHITHUONGTL_SANPHAM(thuongtl_fk, sanpham_fk) select '" + this.id + "', pk_seq from SanPham where ma = ? ";
				data= new Object[]   { this.spMuaList.get(i)  };				
				if(db.updateQueryByPreparedStatement(query, data)<=0 )
				{
					this.msg = "Lỗi  TieuchithuongTL 5";
					db.getConnection().rollback();
					return false;
				}
			}

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return true;
		} 
		catch (Exception e)
		{
			try 
			{
				db.getConnection().rollback();
				System.out.println("__EXCEPTION UPDATE: " + e.getMessage());
				e.printStackTrace();
				
				this.msg = "Exception " ;
			} 
			catch (SQLException e1) {}
			
			return false;
		}
		
		
	}

	public void init() 
	{
		String query = "\n select scheme, thang, nam,isnull(ghichu,'') as ghichu, diengiai, mucvuot, " +
		"\n chietkhauMucVuot, donviMucVuot, hinhthuctra, DOANHSOTHEOTHUNG, HTTT, PT_TRATL, " +
		"\n ngayds_tungay, ngayds_denngay, khoId, phanloai, ngaytb_tungay, ngaytb_denngay, " +
		"\n batbuocdangky, DOANHSOTHEOLUONG " +
		"\n from TieuchithuongTL where pk_seq = '" + this.id + "'";		
		System.out.println("__Khoi tao tieu chi thuong: " + query);

		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				NumberFormat format = new DecimalFormat("#,###,###");
				while(rs.next())
				{
					this.scheme = rs.getString("scheme");
					this.thang = rs.getString("thang");
					this.nam = rs.getString("nam");					
					this.diengiai= rs.getString("diengiai");
					this.doanhsotheoThung = rs.getString("DOANHSOTHEOTHUNG");					
					this.httt = rs.getString("HTTT");
					this.ptchietkhau = rs.getString("PT_TRATL");
					this.ghighu = rs.getString("ghichu");
					this.tungay = rs.getString("ngayds_tungay");
					this.dengay = rs.getString("ngayds_denngay");
					this.khoId = rs.getString("khoId");					
					this.phanloai = rs.getString("phanloai");
					this.ngaytb_tungay = rs.getString("ngaytb_tungay");
					this.ngaytb_dengay = rs.getString("ngaytb_denngay");
					this.phaidangky = rs.getString("batbuocdangky");
					this.tinhtheosl = rs.getString("DOANHSOTHEOLUONG");
				}
				rs.close();
			} 
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		this.createNdk();
		this.createRs();
		this.createKhachHangDK();
	}
	
	private void createKhachHangDK()
	{
		try
		{

			String DKKMTICHLUY_KHACHHANG_IdTmp ="";
			String khDcDuyetTmp ="";
			
			String query = "\n select khdk.DKKMTICHLUY_FK, khdk.KHACHHANG_FK, npp.TEN as nhaphanphoi, kh.TEN as khachhang, isnull(khdk.DANGKY, 0) as dangky, nppdk.Muc " + 
			 "\n from DANGKYKM_TICHLUY_KHACHHANG khdk  " + 
			 "\n inner join DANGKYKM_TICHLUY nppdk on khdk.DKKMTICHLUY_FK = nppdk.PK_SEQ " + 
			 "\n inner join TIEUCHITHUONGTL thuong on thuong.PK_SEQ = nppdk.tieuchitl_fk " + 
			 "\n inner join KHACHHANG kh on kh.PK_SEQ = khdk.KHACHHANG_FK " + 
			 "\n inner join NHAPHANPHOI npp on npp.PK_SEQ = nppdk.npp_fk " + 
			 "\n where  thuong.PK_SEQ = "+ this.id ;
			System.out.println(":::: lay khach hang dang ky: " + query);
			ResultSet rs = db.get(query); 
			this.khDangKyRs = db.get(query); 
			while(rs.next())
			{
				DKKMTICHLUY_KHACHHANG_IdTmp += rs.getString("DKKMTICHLUY_FK") + "," + rs.getString("KHACHHANG_FK")+"__" ;
				khDcDuyetTmp += "0" + "__" ;
			}
			
			if(DKKMTICHLUY_KHACHHANG_IdTmp.trim().length() >2)
			{
				DKKMTICHLUY_KHACHHANG_IdTmp =DKKMTICHLUY_KHACHHANG_IdTmp.substring(0, DKKMTICHLUY_KHACHHANG_IdTmp.length() - 2);
				khDcDuyetTmp =khDcDuyetTmp.substring(0, khDcDuyetTmp.length() - 2);
				DKKMTICHLUY_KHACHHANG_Id = DKKMTICHLUY_KHACHHANG_IdTmp.split("__");
				khDcDuyet = khDcDuyetTmp.split("__");
				
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private void createNdk() 
	{
		tichluyItemList.clear();
		spMuaList.clear();
		
		String query = "select hinhthuc,ghichu, tumuc, denmuc, chietkhau, donvi, muc,isnull(mucphanbo,'') as mucphanbo  " +
					   "from TieuchithuongTL_TIEUCHI " +
					   "where thuongtl_fk = '" + this.id + "' order by muc ";
		
		System.out.println("___Khoi tao tieu chi: " + query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			NumberFormat format = new DecimalFormat("##,###,###");
			NumberFormat format2 = new DecimalFormat("##,###,###.##");
			try 
			{
				while(rs.next())
				{
					ResultSet rsDETAIL = null;
					ITichLuyItem item = new TichLuyItem();
					item.setMuc(rs.getInt("muc"));
					item.setLoaitra(rs.getInt("donvi")); 
					item.setHinhthuc(rs.getInt("hinhthuc"));
					item.setTumuc(rs.getDouble("tumuc"));
					item.setDenmuc(rs.getDouble("denmuc"));
					item.setTratichluy(rs.getDouble("chietkhau"));
					
					if(item.getLoaitra() == 2)// trasp
					{
						query = "\n select sp.ma, soluong " +
								"\n from TIEUCHITHUONGTL_SPTRA a inner join sanpham sp on a.sanpham_fk = sp.pk_seq " +
								"\n where a.muctra = "+item.getMuc()+" and a.thuongtl_fk = '" + this.id + "'  ";
						rsDETAIL = db.get(query);
						item.getSpList().clear();
						while(rsDETAIL.next())
						{
							Object[]  a = { rsDETAIL.getString("ma"),rsDETAIL.getDouble("soluong")  };
							item.getSpList().add(a);
						}	
						item.setSanPhamRs(db);
					}	
					tichluyItemList.add(item);
					
					
					
				}
				
				query  = " select sp.ma from TIEUCHITHUONGTL_SANPHAM a inner join sanpham sp on a.sanpham_fk = sp.pk_seq where a.thuongtl_fk =  "+ this.id;
				rs  = db.get(query);
				while(rs.next())
					spMuaList.add ( rs.getString("ma") );
				this.setSanphamMuaRs();
				
				
			} 
			catch (Exception e) {
				
				e.printStackTrace();
			}
		}
		
		
		
		
		
	}

	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	public String getScheme()
	{
		return this.scheme;
	}

	public void setScheme(String scheme) 
	{
		this.scheme = scheme;
	}
	
	

	

	public void createRs() {
		

		String query = "select pk_seq, ten from KHO where trangthai = '1'";
		this.khoRs = db.get(query);
		
		query = "select pk_seq, ten from NHOMSANPHAMKM where trangthai = '1'";
		this.nhomsanphamRs = db.get(query);
		
	}
	
	public void setSanphamRs(ResultSet spRs) {
		
		this.sanphamRs = spRs;
	}

	
	public ResultSet getSanphamRs() {
		
		return this.sanphamRs;
	}

	
	public String getSpIds() {
		
		return this.spIds;
	}

	
	public void setSpIds(String spIds) {
		
		this.spIds = spIds;
	}

	
	public void setNppRs(ResultSet nppRs) {
		
		this.nppRs = nppRs;
	}

	
	public ResultSet getNppRs() {
		
		return this.nppRs;
	}

	
	
	
	public void setVungRs(ResultSet vungRs) {
		
		this.vungRs = vungRs;
	}

	
	public ResultSet getVungRs() {
		
		return this.vungRs;
	}

	
	public String getVungIds() {
		
		return this.vungIds;
	}

	
	public void setVungIds(String vungIds) {
		
		this.vungIds = vungIds;
	}

	
	public void setKhuvucRs(ResultSet kvRs) {
		
		this.kvRs = kvRs;
	}

	
	public ResultSet getKhuvucRs() {
		
		return this.kvRs;
	}

	
	public String getKhuvucIds() {
		
		return this.kvIds;
	}

	
	public void setKhuvucIds(String kvIds) {
		
		this.kvIds = kvIds;
	}

	
	

	
	public String getDoanhsotheoThung() {
		
		return this.doanhsotheoThung;
	}

	
	public void setDoanhsotheoThung(String isThung) {
		
		this.doanhsotheoThung = isThung;
	}

	
	public String getHTTT() {
		
		return this.httt;
	}

	
	public void setHTTT(String httt) {
		
		this.httt = httt;
	}

	
	public String getPT_TRACK() {
		
		return this.ptchietkhau;
	}

	
	public void setPT_TRACK(String ptTRACK) {
		
		this.ptchietkhau = ptTRACK;
	}



	
	
	public String getNgayDS_Tungay() {
		
		return this.tungay;
	}

	
	public void setNgayDS_Tungay(String tungay) {
		
		this.tungay = tungay;
	}

	
	public String getNgayDS_Denngay() {
		
		return this.dengay;
	}

	
	public void setNgayDS_Denngay(String denngay) {
		
		this.dengay = denngay;
	}

	
	public void setKhoRs(ResultSet khoRs) {
		
		this.khoRs = khoRs;
	}

	
	public ResultSet getKhoRs() {
		
		return this.khoRs;
	}

	
	public String getKhoIds() {
		
		return this.khoId;
	}

	
	public void setKhoIds(String khoIds) {
		
		this.khoId = khoIds;
	}

	



	
	public String getPhanloai() {

		return this.phanloai;
	}


	public void setPhanloai(String phanloai) {

		this.phanloai = phanloai;
	}
	
	
	
	public void setNhomsanphamRs(ResultSet spRs) {
		
		this.nhomsanphamRs = spRs;
	}
	
	public ResultSet getNhomsanphamRs() {
		
		return this.nhomsanphamRs;
	}
	
	public String getNhomsanphamIds() {
		
		return this.nhomspIds;
	}
	
	public void setNhomsanphamIds(String nhomspIds) {
		
		this.nhomspIds = nhomspIds;
	}
	
	public void loadSP_NHOM() 
	{
		if(this.nhomspIds.trim().length() > 0)
		{
			this.spMuaList.clear();

			String query = "\n select b.MA, b.TEN " + 
			"\n from NHOMSANPHAMKM_SANPHAM a inner join SANPHAM b on a.SP_FK = b.PK_SEQ " +
			"\n where a.NSP_FK = '" + this.nhomspIds + "' ";
			System.out.println("query = "+ query);
			ResultSet rs = db.get(query);
			if(rs != null )
			{
				try 
				{
					while(rs.next())
					{
						spMuaList.add(rs.getString("MA"));	
					}
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
			this.setSanphamMuaRs();
		}
	}
	
	public void setKenhRs(ResultSet kenhRs) {
		
		this.kenhRs = kenhRs;
	}
	
	public ResultSet getKenhRs() {
		
		return this.kenhRs;
	}
	
	public String getKenhIds() {
		
		return this.kenhIds;
	}
	
	public void setKenhIds(String kenhIds) {
		
		this.kenhIds = kenhIds;
	}
	
	
	public String getGhichu() {
		
		return this.ghighu;
	}
	
	public void setGhichu(String ghichu) {
		
		this.ghighu = ghichu;
	}
	
	public String getKT() {
		
		return this.kt;
	}
	
	public void setKT(String kt) {
		
		this.kt = kt;
	}
	
	public String getActiveTab() {
		
		return this.active;
	}
	
	public void setActiveTab(String active) {
		
		this.active = active;
	}
	
	public String getMucNPP() {
		
		return this.mucnpp;
	}
	
	public void setMucNPP(String MucNpp) {
		
		this.mucnpp = MucNpp;
	}
	
	
	
	

	
	public String getNgayTB_Tungay() {
		
		return this.ngaytb_tungay;
	}

	
	public void setNgayTB_Tungay(String tungay) {
		
		this.ngaytb_tungay = tungay;
	}

	
	public String getNgayTB_Denngay() {
		
		return this.ngaytb_dengay;
	}

	
	public void setNgayTB_Denngay(String denngay) {
		
		this.ngaytb_dengay = denngay;
	}

	
	public String getPhaidangky() {
		
		return this.phaidangky;
	}

	
	public void setPhandangky(String phaidangky) {
		
		this.phaidangky = phaidangky;
	}

	@Override
	public String getTinhtheoSl() {
		// TODO Auto-generated method stub
		return this.tinhtheosl;
	}

	@Override
	public void setTinhtheoSl(String value) {
		this.tinhtheosl = value;
	}


	public ArrayList<ITichLuyItem> getTichluyItemList() {
		return tichluyItemList;
	}
	public void setTichluyItemList(ArrayList<ITichLuyItem> tichluyItemList) {
		this.tichluyItemList = tichluyItemList;
	}
	
	public dbutils getDb() {
		return db;
	}
	List<Object> dataUpload = new ArrayList<Object>();
	public List<Object> getDataUpload() {
		return dataUpload;
	}
	public void setDataUpload(List<Object> dataUpload) {
		this.dataUpload = dataUpload;
	}
	
	public boolean uploadPhanbo(String values)
	{
		String query = "";
		try
		{
			db.getConnection().setAutoCommit(false);
			
			Object[] data;
			
			query = " update TIEUCHITHUONGTL set ThoiDiemPhanBo = dbo.GetLocalDate(default) , NguoiPhanBo = '"+this.userId+"',ngaysua= '"+getDateTime()+"', nguoisua = '"+this.userId+"'  where trangthai = 0 and pk_seq =  "+ this.id;
			if(db.updateReturnInt(query)!=1)
			{
				this.msg = " Loi Upload (1) ";
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
				return false;
			}
			
			String error = "";
			query  = " select nppId from ("+values+") data where nppId not in (select pk_seq from  nhaphanphoi) ";
			ResultSet rs = db.get_v2(query, this.dataUpload);
			while(rs.next())
			{
				if(error.trim().length() > 0)
					error += ", "  + rs.getString("nppId");
				else
					error += rs.getString("nppId");
				
			}
			if(error.trim().length() > 0)
			{
				this.msg = " Các id NPP sau không tồn tại : "+ error;
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
				return false;
			}
			
		//	db.viewQuery(query, this.dataUpload);
			
			query  = " select nppId,muc from ("+values+") data" +
					 " group by nppId,muc " +
					 " having count(*) > 1 ";
			rs = db.get_v2(query, this.dataUpload);
			while(rs.next())
			{
				if(error.trim().length() > 0)
					error += ", "  + rs.getString("nppId") + ", mức  "+(rs.getDouble("muc") +1) ;
				else
					error +=  rs.getString("nppId") + ", mức  "+(rs.getDouble("muc") +1) ;
				
			}
			if(error.trim().length() > 0)
			{
				this.msg = " Các id NPP sau  bị lặp dòng : "+ error;
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
				return false;
			}
			
			query  = " select nppId , sosuat , muc from ("+values+") data where cast( sosuat as float)  < 0.0 order by nppId " ;
			db.viewQuery(query, this.dataUpload);
			
				rs = db.get_v2(query, this.dataUpload);
				while(rs.next())
				{
					if(error.trim().length() > 0)
						error += ", "  + rs.getString("nppId") +" , mức "+(rs.getDouble("muc") +1)+"  ";
					else
						error += rs.getString("nppId") +" , mức "+(rs.getDouble("muc") +1)+"  ";
					
				}
				if(error.trim().length() > 0)
				{
					this.msg = " Các NPP sau đang có số suất bé hơn 0 : "+ error;
					db.getConnection().rollback();
					db.getConnection().setAutoCommit(true);
					return false;
				}
			
			query = " delete TIEUCHITHUONGTL_NPP where thuongtl_fk =  "+ this.id;
			if(!db.update(query))
			{
				this.msg = " Loi Upload (2) ";
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
				return false;
			}
			
			query = " insert TIEUCHITHUONGTL_NPP (thuongtl_fk,npp_fk,soluong,muc) select "+this.id+",nppId, sosuat,muc from (" + values + ") a where cast( sosuat as float)  > 0 ";
			
			if( db.updateQueryByPreparedStatement(query, this.dataUpload) < 0 )
			{
				this.msg = " Loi Upload (3) ";
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
				return false;
			}
			
			this.msg = "Upload thành công!";
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			geso.dms.center.util.Utility.rollback_throw_exception(db);
			this.msg = "Lỗi ngoại lệ!";
			return false;
		}
	}
	
	

}
