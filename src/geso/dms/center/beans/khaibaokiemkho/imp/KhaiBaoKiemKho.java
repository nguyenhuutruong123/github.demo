package geso.dms.center.beans.khaibaokiemkho.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import geso.dms.center.beans.khaibaokiemkho.IKhaiBaoKiemKho;
import geso.dms.center.db.sql.dbutils;

public class KhaiBaoKiemKho implements IKhaiBaoKiemKho
{
	String userId;
	String id;	
	String diengiai;
	String tungay;
	String denngay;
	
	String trangthai;
	String msg;
	String spIds;
	ResultSet spRs;
	ResultSet KhRs;
	dbutils db;
	String khId = "";
	NumberFormat formater = new DecimalFormat("##,###,###");
	NumberFormat formater2 = new DecimalFormat("##,###,###.####");
	private String[] dvtDoithu;
	private String[] spDothu;
	public KhaiBaoKiemKho()
	{
		this.userId = "";
		this.id = "";
		this.diengiai = "";
		this.trangthai = "1";
		this.msg = "";
		this.tungay="";
		this.denngay ="";
		this.spIds = "";
		this.spDothu = new String[0];
		this.dvtDoithu = new String[0];
		this.db = new dbutils();
	}
	
	
	public KhaiBaoKiemKho(String id)
	{
		this.userId = "";
		this.id = id;
		this.diengiai = "";
		this.trangthai = "1";
		this.msg = "";
		this.tungay="";
		this.denngay ="";
		this.spIds = "";
		this.spDothu = new String[0];
		this.dvtDoithu = new String[0];
		this.db = new dbutils();
	}
	
	public String getId() 
	{
		return this.id;
	}

	public void setId(String Id) 
	{
		this.id = Id;
	}
	
	public String getUserId() 
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;	
	}

	public String getMsg() 
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}


	public void init() 
	{
		String query = "select Diengiai, TuNgay, Denngay from KHAIBAOKIEMKHO where PK_SEQ = '" + this.id + "'";
		ResultSet rs = db.get(query);
		query = "select * from KHAIBAOKIEMKHOKHACHHANG_SANPHAM where KHAIBAOKIEMKHO_FK = " + this.id;
		ResultSet rssp = this.db.get(query);
		query = "select * from KHAIBAOKIEMKHODOITHU_SANPHAM where KHAIBAOKIEMKHO_FK = " + this.id;
		ResultSet rsdt = this.db.get(query);
		query = "select * from KHAIBAOKIEMKHO_Khachhang where KHAIBAOKIEMKHO_FK = " + this.id;
		ResultSet rskh = this.db.get(query);
		try 
		{
			while(rs.next())
			{
				this.diengiai = rs.getString("Diengiai");
				this.tungay = rs.getString("TuNgay");
				this.denngay = rs.getString("Denngay");
				
			}
			while(rssp.next()){
				this.spIds += rssp.getString("SANPHAM_FK") + ",";
			}
			if(this.spIds.length() > 0)
				this.spIds = this.spIds.substring(0, this.spIds.length() - 1);
			String sp = "", dv = "";
			while(rsdt.next()){
				sp += rsdt.getString("SANPHAM") + ",";
				dv += (rsdt.getString("DONVITINH").length() > 0 ? rsdt.getString("DONVITINH"): " ") + ",";
			}
			//System.out.println("sp = " + sp + ", dv = " + dv);
			if(sp.length() > 0){
				sp = sp.substring(0, sp.length() - 1);
				dv = dv.substring(0, dv.length() - 1);
				this.spDothu = sp.split(",");
				this.dvtDoithu = dv.split(",");
			}
			while(rskh.next()){
				this.khId += rskh.getString("khachhang_FK") + ",";
			}
			if(this.khId.length() > 0)
				this.khId = this.khId.substring(0, this.khId.length() - 1);
			rssp.close();
			rsdt.close();
			rs.close();
			rskh.close();
		}
		catch (Exception e) 
		{
			System.out.println("__Exception: " + e.getMessage());
			e.printStackTrace();
		}
		
		System.out.println("KHid "+khId);
		this.createRs();
	}
	
	public boolean create()
	{	
		try 
		{
			
			
			if(this.tungay.trim().length() <= 0)
			{
				this.msg = "Vui lòng ngày bắt đầu";
				return false;
			}
			
			if(this.denngay.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn ngày kết thúc";
				return false;
			}
			
			if(this.diengiai.trim().length() <= 0)
			{
				this.msg = "Vui lòng nhập diễn giải";
				return false;
			}
			boolean coSp = false;
			for(int i = 0; i< this.spDothu.length; i++)
				if(this.spDothu[i].trim().length() > 0)
					coSp = true;
			if(this.spIds.length() <= 0 && !coSp)
			{
				this.msg = "Không có sản phẩm nào được chọn";
				return false;
			}
			String query = "select COUNT(*) as num from KHAIBAOKIEMKHO where '"+this.tungay+"' <= DENNGAY and '"+this.denngay+"' >= TUNGAY";
			ResultSet rs = this.db.get(query);
			rs.next();
			int num = rs.getInt("num");
			if(num != 0){
				this.msg = "Khoảng thời gian này bị trùng với khai báo khác.";
				rs.close();
				return false;
			}
			db.getConnection().setAutoCommit(false);
			
			query = "insert into KHAIBAOKIEMKHO(DIENGIAI, NGAYTAO, NGAYSUA, TUNGAY ,DENNGAY, NGUOITAO, NGUOISUA, TRANGTHAI) " +
							"values(N'" + this.diengiai + "','" + getDateTime() + "','" + getDateTime() + "','" + this.tungay + "', " +
								"'" + this.denngay + "','" + this.userId + "','" + this.userId + "', 0)";
			
			System.out.println("__Tao moi KHAIBAOKIEMKHO: " + query);
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới " + query;
				db.getConnection().rollback();
				return false;
			}
			
			ResultSet rsBg = this.db.get("select IDENT_CURRENT('KHAIBAOKIEMKHO') as Id");
			rsBg.next();
			
			String Id = rsBg.getString("Id");
			String[] sp = this.spIds.split(",");
			
			for(int i = 0; i < sp.length; i++)
			{
				query = "insert into KHAIBAOKIEMKHOKHACHHANG_SANPHAM (KHAIBAOKIEMKHO_FK, sanpham_fk)" +
						"values('" + Id + "', '" + sp[i].trim() + "')";
				if(!this.db.update(query))
				{				
					this.msg = "Không thể tạo mới " + query;
					db.getConnection().rollback();
					return false;
				}
				
			}
			for(int i = 0; i < this.spDothu.length; i++)
			{
				if(this.spDothu[i].trim().length() > 0){
					query = "insert into KHAIBAOKIEMKHODOITHU_SANPHAM (KHAIBAOKIEMKHO_FK, SANPHAM, DONVITINH)" +
							"values('" + Id + "', N'" + this.spDothu[i].trim() + "', N'" + this.dvtDoithu[i].trim() + "')";
					if(!this.db.update(query))
					{				
						this.msg = "Không thể tạo mới " + query;
						db.getConnection().rollback();
						return false;
					}
				}
				
			}	
			
			if(this.khId.trim().length() > 0)
			{
				query = "insert into KHAIBAOKIEMKHO_KHACHHANG (KHAIBAOKIEMKHO_FK, KHACHHANG_FK)" +
						"select '" + Id + "',pK_seq from khachhang where pk_seq in ("+this.khId+")";
				if(!this.db.update(query))
				{				
					this.msg = "Không thể tạo mới " + query;
					db.getConnection().rollback();
					return false;
				}
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			this.msg = "Loi: " + e.getMessage();
			try 
			{
				db.getConnection().rollback();
			} 
			catch (SQLException e1) {}
			return false;
		}
		return true;
	}
	
	
	public boolean update() 
	{
		try 
		{
			
			if(this.tungay.trim().length() <= 0)
			{
				this.msg = "Vui lòng ngày bắt đầu";
				return false;
			}
			
			if(this.denngay.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn ngày kết thúc";
				return false;
			}
			
			if(this.diengiai.trim().length() <= 0)
			{
				this.msg = "Vui lòng nhập diễn giải";
				return false;
			}
			boolean coSp = false;
			for(int i = 0; i< this.spDothu.length; i++)
				if(this.spDothu[i].trim().length() > 0)
					coSp = true;
			if(this.spIds.length() <= 0 && !coSp)
			{
				this.msg = "Không có sản phẩm nào được chọn";
				return false;
			}
			String query = "select COUNT(*) as num from KHAIBAOKIEMKHO where '"+this.tungay+"' <= DENNGAY and '"+this.denngay+"' >= TUNGAY and PK_SEQ != " + this.id;
			System.out.println(query);
			ResultSet rs = this.db.get(query);
			rs.next();
			int num = rs.getInt("num");
			if(num != 0){
				this.msg = "Khoảng thời gian này bị trùng với khai báo khác.";
				rs.close();
				return false;
			}
			db.getConnection().setAutoCommit(false);
			
			query = "UPDATE KHAIBAOKIEMKHO SET DIENGIAI = N'" + this.diengiai + "', NGAYSUA = '" + getDateTime() + "', TUNGAY = '" + this.tungay + "',DENNGAY = '" + this.denngay + "', NGUOISUA = '" + this.userId + "', TRANGTHAI = 0 " +
							"WHERE PK_SEQ = '" + this.id + "'";
			
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật " + query;
				db.getConnection().rollback();
				return false;
			}
			query = "DELETE FROM KHAIBAOKIEMKHOKHACHHANG_SANPHAM WHERE KHAIBAOKIEMKHO_FK = " + this.id;
			if(!this.db.update(query))
			{				
				this.msg = "Không thể cập nhật " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "DELETE FROM KHAIBAOKIEMKHODOITHU_SANPHAM WHERE KHAIBAOKIEMKHO_FK = " + this.id;
			if(!this.db.update(query))
			{				
				this.msg = "Không thể cập nhật " + query;
				db.getConnection().rollback();
				return false;
			}
			
			String[] sp = this.spIds.split(",");
			
			for(int i = 0; i < sp.length; i++)
			{
				query = "insert into KHAIBAOKIEMKHOKHACHHANG_SANPHAM (KHAIBAOKIEMKHO_FK, sanpham_fk)" +
						"values('" + this.id + "', '" + sp[i].trim() + "')";
				if(!this.db.update(query))
				{				
					this.msg = "Không thể cập nhật " + query;
					db.getConnection().rollback();
					return false;
				}
				
			}
			for(int i = 0; i < this.spDothu.length; i++)
			{
				if(this.spDothu[i].trim().length() > 0){
					query = "insert into KHAIBAOKIEMKHODOITHU_SANPHAM (KHAIBAOKIEMKHO_FK, SANPHAM, DONVITINH)" +
							"values('" + this.id + "', N'" + this.spDothu[i].trim() + "', N'" + this.dvtDoithu[i].trim() + "')";
					if(!this.db.update(query))
					{				
						this.msg = "Không thể tạo mới " + query;
						db.getConnection().rollback();
						return false;
					}
				}
				
			}	
			query = "DELETE FROM KHAIBAOKIEMKHO_KHACHHANG WHERE KHAIBAOKIEMKHO_FK = " + this.id;
			if(!this.db.update(query))
			{				
				this.msg = "Không thể cập nhật " + query;
				db.getConnection().rollback();
				return false;
			}
			if(this.khId.trim().length() > 0)
			{
				query = "insert into KHAIBAOKIEMKHO_KHACHHANG (KHAIBAOKIEMKHO_FK, KHACHHANG_FK)" +
						"select '" + this.id + "',pK_seq from khachhang where pk_seq in ("+this.khId+")";
				if(!this.db.update(query))
				{				
					this.msg = "Không thể tạo mới " + query;
					db.getConnection().rollback();
					return false;
				}
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			this.msg = "Loi: " + e.getMessage();
			try 
			{
				db.getConnection().rollback();
			} 
			catch (SQLException e1) {}
			return false;
		}
		return true;
	}
	
	public void DbClose() 
	{
		try 
		{
			this.db.shutDown();
		} 
		catch (Exception e) {e.printStackTrace();}
		
	}
	
	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}


	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}
	
	public void createRs()
	{
		if(this.spIds.length() > 0)
		{
		String querySP = 
				"	select a.PK_SEQ as spId, a.MA as spMa, isnull(a.TEN, '') as spTen , ISNULL(b.donvi, 'NA') as donvi "+
				"	from SanPham a left join DONVIDOLUONG b on a.DVDL_FK = b.PK_SEQ "+
				"where a.pk_seq in ("+this.spIds+")";
				
		querySP +=	" order by spMa asc, spTen asc  ";
		System.out.println("San pham "+querySP);
		this.spRs = db.get(querySP);
		}
		
		if(khId.length() > 0)
		{
			String	query = "select PK_SEQ, Mafast, TEN, DIACHI from khachhang";
			query+= " where pk_seq in ("+khId+")";
			System.out.println("Init KH "+query);
			this.KhRs = db.get(query);
		}

	
	}
	public void getId_Khachhang(String maKh) {
		
		String nppids = "", khIds = "";
		String query = "select pk_seq, maFAST,DiaChi from KHACHHANG where maFAST in ("+maKh+")" ;
		//				" and PK_SEQ in (select KHACHHANG_FK from KHACHHANG_NPP where NPP_FK in (select NPP_FK from CTTB_NPP where CTTB_FK = '"+this.cttbId+"'))";
		System.out.println("KH " + query);
		ResultSet rs = this.db.get(query);
		int sokh = 0;
		try{
			while(rs.next()){
				khIds += rs.getString("PK_SEQ") + ",";
				String mafast = rs.getString("maFAST");
				String diachi = rs.getString("diachi");
				sokh++;
			}
			if(khIds.length() > 0)
				khIds = khIds.substring(0, khIds.length() - 1);
			if(sokh > 0)
				this.msg = "Upload thành công! Có " + sokh + " khách hàng được upload.";
			if(sokh == 0)
				this.msg = "Upload thất bại! Không có khách hàng nào được chọn";
			this.khId = khIds;
		}
		catch(Exception ex){ex.printStackTrace();}
		if(maKh.length() > 0 )
		{
			String makhup[] = maKh.split(",");
			String query_er = "";
			for(int i = 0; i< makhup.length; i++)
			{
				query_er += "select "+makhup[i]+" as Mafast ";
				if(i < makhup.length - 1)
					query_er += "union all \n";

			}
			query_er = "select kh.PK_SEQ,d.mafast from KHACHHANG kh right join (" + query_er + ") as d on d.mafast = kh.maFAST where kh.PK_SEQ is null";
//			System.out.println("Check KH "+query_er);
			if(query_er.length() > 0){
				ResultSet khlist_error = db.get(query_er);
				if(khlist_error != null){
					try {
						this.msg = "";
						while(khlist_error.next()){
							this.msg += khlist_error.getString("MAFAST") + "\n";
						}
						if(this.msg.length() > 0)
							this.msg = "Không xác định được khách hàng có mã này:\n" + this.msg+ "";
					} catch (SQLException e) {

						e.printStackTrace();
					}
				}
			}
		}
	}
	public String getTuNgay() 
	{
		return tungay;
	}


	
	public void setTuNgay(String tungay) 
	{
		this.tungay =tungay;
	}


	
	public String getDiengiai() {
		return this.diengiai;
	}


	
	public void setDiengiai(String value) {
		this.diengiai = value;
	}


	
	public ResultSet getSpRs() {
		return this.spRs;
	}


	
	public String getSpIds() {
		return this.spIds;
	}


	
	public void setSpIds(String value) {
		this.spIds = value;
	}


	
	public String[] getSpDoithu() {
		
		return this.spDothu;
	}


	
	public void setSpDoithu(String[] value) {
		this.spDothu = value;
	}


	
	public String[] getDvtDoithu() {
		return this.dvtDoithu;
	}


	
	public void setDvtDoithu(String[] value) {
		this.dvtDoithu = value;
	}


	
	public String getDenNgay() {
		
		return this.denngay;
	}


	
	public void setDenNgay(String value) {
		this.denngay = value;
	}


	
	public ResultSet getKhList() {
		
		return this.KhRs;
	}


	
	public String getKhId() {
		
		return this.khId;
	}


	
	public void setKhId(String khId) {
		
		this.khId = khId;
		
	}
	
	
}
