
package demo.test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.util.SystemOutLogger;

import geso.dms.center.db.sql.dbutils;

public class XoaDonHang {

	public static void main(String[] args) throws SQLException {
		try
		{
			dbutils db = new dbutils();
			String ngay = "2017-02-02", ngayks = "2017-02-01";
			String query = "select npp_fk from khoasongay where ngayks = '"+ngayks+"'";
<<<<<<< .mine
			query += " and npp_fk in (113159)";
||||||| .r39949
			query += " and npp_fk in ( 113155 )";
=======
			query += " and npp_fk in ( 113125,113127,113144,113162,113167,113197,113200,113280,113299,113316,113337 )";
>>>>>>> .r41698
			String nppId = "", kho = "", kbh = "", sanpham = "";
			double cuoiky = 0, booked = 0;
			ResultSet rs = db.get(query);
			while(rs.next())
			{
				nppId = rs.getString("npp_fk");
				query = "update a set a.booked = isnull(c.SOLUONG,0) " +
						"from nhapp_kho a inner join nhaphanphoi b on a.NPP_FK = b.PK_SEQ " + 
						"left join ufnBooked() c on a.kho_fk = c.KHO_FK and a.npp_fk = c.NPP_FK and a.sanpham_fk = c.SANPHAM_FK and a.kbh_fk = c.KBH_FK " +
						"where b.PK_SEQ = "+nppId+" and a.booked <> isnull(c.SOLUONG,0)";
				if(!db.update(query))
				{
					System.out.println("Lỗi: "+query);
					return;
				}
				
				cuoiky = 0; booked = 0;
				db.getConnection().setAutoCommit(false);
				while(1==1)
				{
					query = "select a.npp_fk, a.kbh_fk, a.kho_fk, a.sanpham_fk, cuoiky, BOOKED " +		
							"from ufn_XNT_TuNgay_DenNgay_FULL_New("+nppId+", '"+ngay+"', '') a left join NHAPP_KHO b on a.kho_fk = b.KHO_FK " +
							"and a.npp_fk = b.NPP_FK and a.sanpham_fk = b.SANPHAM_FK and a.kbh_fk = b.KBH_FK " +
							"where a.cuoiky < ISNULL(b.booked,0) or a.cuoiky < 0";
					System.out.println("layspsai="+query);
					ResultSet rssp = db.get(query);
					if(rssp.next())
					{
						cuoiky = rssp.getDouble("cuoiky") != 0 ? rssp.getDouble("cuoiky") * -1 : rssp.getDouble("cuoiky");
						booked = rssp.getDouble("booked");
						kho = rssp.getString("kho_fk");
						sanpham = rssp.getString("sanpham_fk");
						kbh = rssp.getString("kbh_fk");
					} rssp.close();
					System.out.println("1.cuoiky="+cuoiky+";booked="+booked);
					if(cuoiky==0 && booked==0)
						break;
					
					/*db.getConnection().setAutoCommit(false);*/
					
					query = "select a.pk_seq, b.soluong from donhang a inner join donhang_sanpham b on a.pk_seq = b.donhang_fk " + 
							"where a.NPP_FK = "+nppId+" and a.KHO_FK = "+kho+" and a.KBH_FK = "+kbh+" and SANPHAM_FK = "+sanpham+" " +
							"and a.TRANGTHAI in (1,3) and NGAYNHAP >= '"+ngay+"' order by b.SOLUONG ";
					System.out.println("laydh="+query);
					ResultSet rsdh = db.get(query);
					while(rsdh.next())
					{
						System.out.println("in đơn hàng " + rsdh.getString("pk_seq" ));
						//hủy đơn hàng
						String msg = huydonhang(rsdh.getString("pk_seq"), db);
						if(msg.trim().length() > 0)
						{
							System.out.println(msg);
							db.getConnection().rollback();
							return;
						}
						cuoiky = cuoiky - rsdh.getDouble("soluong");
						if(cuoiky <= 0)
							break;
					} rsdh.close();
					
					query = "SELECT a.pk_seq, b.soluong FROM DONHANG_CTKM_TRAKM b INNER JOIN DONHANG a ON a.PK_SEQ = b.DONHANGID INNER JOIN SANPHAM SP ON SP.MA = b.SPMA INNER JOIN CTKHUYENMAI CTKM ON CTKM.PK_SEQ=b.CTKMID " + 
							"where a.NPP_FK = "+nppId+" and CTKM.KHO_FK = "+kho+" and a.KBH_FK = "+kbh+" and sp.PK_SEQ = "+sanpham+" " +
							"and a.TRANGTHAI in (1,3) and a.NGAYNHAP >= '"+ngay+"' order by b.SOLUONG ";
					System.out.println("laydh="+query);
					rsdh = db.get(query);
					while(rsdh.next())
					{
						System.out.println("in đơn hàng " + rsdh.getString("pk_seq" ));
						//hủy đơn hàng
						String msg = huydonhang(rsdh.getString("pk_seq"), db);
						if(msg.trim().length() > 0)
						{
							System.out.println(msg);
							db.getConnection().rollback();
							return;
						}
						cuoiky = cuoiky - rsdh.getDouble("soluong");
						if(cuoiky <= 0)
							break;
					} rsdh.close();
					
					if(cuoiky < 0) cuoiky = cuoiky * -1;
					if(booked > cuoiky)
					{
						double tong = booked - cuoiky;
						query = "select a.pk_seq, b.soluong from donhang a inner join donhang_sanpham b on a.pk_seq = b.donhang_fk " + 
								"where a.NPP_FK = "+nppId+" and a.KHO_FK = "+kho+" and a.KBH_FK = "+kbh+" and SANPHAM_FK = "+sanpham+" " +
								"and a.TRANGTHAI in (0) and a.NGAYNHAP >= '"+ngay+"' order by b.SOLUONG ";
						System.out.println("laydhbooked="+query);
						rsdh = db.get(query);
						while(rsdh.next())
						{
							//hủy đơn hàng
							System.out.println("in đơn hàng " + rsdh.getString("pk_seq" ));
							String msg = huydonhang(rsdh.getString("pk_seq"), db);
							if(msg.trim().length() > 0)
							{
								System.out.println(msg);
								db.getConnection().rollback();
								return;
							}
							tong = tong - rsdh.getDouble("soluong");
							System.out.println("3.cuoiky="+cuoiky+";tong="+tong);
							if(tong <= 0)
								break;
						} rsdh.close();
					}
					
					if(booked > cuoiky)
					{
						double tong = booked - cuoiky;
						query = "SELECT a.pk_seq, b.soluong FROM DONHANG_CTKM_TRAKM b INNER JOIN DONHANG a ON a.PK_SEQ = b.DONHANGID INNER JOIN SANPHAM SP ON SP.MA = b.SPMA INNER JOIN CTKHUYENMAI CTKM ON CTKM.PK_SEQ=b.CTKMID " + 
								"where a.NPP_FK = "+nppId+" and CTKM.KHO_FK = "+kho+" and a.KBH_FK = "+kbh+" and sp.PK_SEQ = "+sanpham+" " +
								"and a.TRANGTHAI in (0) and a.NGAYNHAP >= '"+ngay+"' order by b.SOLUONG ";
						System.out.println("laydhbooked="+query);
						rsdh = db.get(query);
						while(rsdh.next())
						{
							System.out.println("in đơn hàng " + rsdh.getString("pk_seq" ));
							//hủy đơn hàng
							String msg = huydonhang(rsdh.getString("pk_seq"), db);
							if(msg.trim().length() > 0)
							{
								System.out.println(msg);
								db.getConnection().rollback();
								return;
							}
							tong = tong - rsdh.getDouble("soluong");
							System.out.println("3.cuoiky="+cuoiky+";tong="+tong);
							if(tong <= 0)
								break;
						} rsdh.close();
					}
					
					/*query = "update a set a.booked = isnull(c.SOLUONG,0) " +
							"from nhapp_kho a inner join nhaphanphoi b on a.NPP_FK = b.PK_SEQ " + 
							"left join ufnBooked() c on a.kho_fk = c.KHO_FK and a.npp_fk = c.NPP_FK and a.sanpham_fk = c.SANPHAM_FK and a.kbh_fk = c.KBH_FK " +
							"where b.PK_SEQ = "+nppId+" and a.booked <> isnull(c.SOLUONG,0)";
					if(!db.update(query))
					{
						System.out.println("Lỗi: "+query);
						db.getConnection().rollback();
						return;
					}*/
					
					/*query = "update b set b.SOLUONG = a.cuoiky, b.AVAILABLE = a.cuoiky - isnull(b.booked,0) " +
							"from ufn_XNT_TuNgay_DenNgay_FULL_New("+nppId+", '"+ngay+"', '') a left join NHAPP_KHO b on a.kho_fk = b.KHO_FK " +
							"and a.npp_fk = b.NPP_FK and a.sanpham_fk = b.SANPHAM_FK and a.kbh_fk = b.KBH_FK where (a.cuoiky <> ISNULL(b.soluong, 0) or a.cuoiky <> ISNULL(b.booked,0)+ISNULL(b.available,0)) and a.cuoiky >= 0 " +
							"and a.kho_fk = "+kho+" and a.sanpham_fk = "+sanpham;
					if(!db.update(query))
					{
						System.out.println("Lỗi: "+query);
						db.getConnection().rollback();
						return;
					}*/
					
					/*db.getConnection().commit();
					db.getConnection().setAutoCommit(true);*/
					
					cuoiky = 0; booked = 0;
				}
				db.getConnection().rollback();
				db.getConnection().commit();
				db.getConnection().setAutoCommit(true);
			} rs.close();
			System.out.println("xong..............");
			db.shutDown();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	private static String huydonhang(String dhId, dbutils db)
	{
		try
		{
<<<<<<< .mine
			String query = "update donhang set trangthai='2', thoidiemxoa=getdate() where pk_seq = '" + dhId + "'";
||||||| .r39949
			String query = "update donhang set trangthai='2', ngayhuy=getdate() where pk_seq = '" + dhId + "'";
=======
			String query = "update donhang set trangthai='2', nguoixoa = '100368', thoidiemxoa=getdate() where pk_seq = '" + dhId + "'";
>>>>>>> .r41698
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "1.Khong the xoa don hang: " + dhId + ", " + query;
			}
			
			query = "select distinct a.pxk_fk, b.ngaylapphieu from phieuxuatkho_donhang a inner join phieuxuatkho b on a.pxk_fk = b.pk_seq where a.donhang_fk = '" + dhId + "'";
			ResultSet rs = db.get(query);	
			if(rs != null && rs.next())
			{
				String pxk = rs.getString("pxk_fk");
				rs.close();
				
				query="insert phieuxuatkho_donhang_log select * from PHIEUXUATKHO_DONHANG where pxk_fk = '"+pxk+"'";
				System.out.println(query);
				if(! db.update(query)){
					db.getConnection().rollback();
					return query;
				}
				
				// hủy pxk
				query="delete PHIEUXUATKHO_DONHANG where pxk_fk = '"+pxk+"'";
				System.out.println(query);
				if(! db.update(query)){
					db.getConnection().rollback();
					return query;
				}
				
				query="update PHIEUXUATKHO set trangthai = 2, nguoixoa = '100368', thoidiemxoa=getdate() where pk_seq = '"+pxk+"'";
				System.out.println(query);
				if(! db.update(query)){
					db.getConnection().rollback();
					return query;
				}
				
				query = "update donhang set trangthai='0', ngaysua=getdate() where pk_seq in (select a.donhang_fk from phieuxuatkho_donhang a where a.pxk_fk = '" + pxk + "') and trangthai in (1,3)";
				if(!db.update(query))
				{
					db.getConnection().rollback();
					return "2.Khong the xoa don hang: " + dhId + ", " + query;
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";
	}
	
	private static String getDateTime() 
	{
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date date = new Date();
		return dateFormat.format(date);
	}
}
