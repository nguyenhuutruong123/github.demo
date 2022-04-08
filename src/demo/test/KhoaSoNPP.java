
package demo.test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import geso.dms.center.db.sql.dbutils;

public class KhoaSoNPP {

	public static void main(String[] args) throws SQLException {
		try
		{
			dbutils db = new dbutils();
			String query = "select PK_SEQ from NHAPHANPHOI where TRANGTHAI='1' --and PK_seq = 113287  ";
			ResultSet rs = db.get(query);
			while(rs.next())
			{
				Khoaso(rs.getString("pk_seq"), "2017-03-31", "100368");
			} rs.close();
			db.shutDown();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private static boolean Khoaso(String nppId, String ngayks, String nguoitao)
	{
		dbutils db = new dbutils();	
		
		String query = "";
		try {
			db.getConnection().setAutoCommit(false);
			//Xóa đơn hàng
			query = "select pk_seq, npp_fk, trangthai from donhang where npp_fk = '"+nppId+"' and ngaynhap <= '"+ngayks+"' and trangthai = 0 ";
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				while(rs.next())
				{
					if(!Delete(rs.getString("pk_seq"),rs.getString("npp_fk"),db) )
					{
						System.out.println("Khong the xoa don hang nay..."+rs.getString("pk_seq"));
						db.getConnection().rollback();
						return false;
					}
				}
				rs.close();
			}
			// đơn hàng có phiếu xuất kho
			query = "select b.pxk_fk  from donhang a inner join phieuxuatkho_donhang b on a.pk_seq = b.donhang_fk " +
					"inner join PHIEUXUATKHO pxk on pxk.PK_SEQ = b.PXK_FK " +
					"where pxk.npp_fk = '"+nppId+"' and pxk.NGAYLAPPHIEU <= '"+ngayks+"' and pxk.trangthai = 0 ";
			 rs = db.get(query);
			if(rs != null)
			{
				while(rs.next())
				{
					 String msg = DeletePxk(rs.getString("pxk_fk"),db);
					 if(msg.length() > 0)
					{
						System.out.println("Khong the xoa pxk nay..."+rs.getString("pxk_fk"));
						db.getConnection().rollback();
						return false;
					}
				}
				rs.close();
			}
			
			query = "update donhang set trangthai = 1, FlagModified =1, ngaychot = getdate() where npp_fk = '"+nppId+"' and ngaynhap <= '"+ngayks+"' and trangthai = 3 ";
			if(!db.update(query))
			{
				System.out.println("Khong the chot dhDXK: "+query);
				db.getConnection().rollback();
				return false;
			}
			
			query = "update donhang set trangthai = 2 where npp_fk = '"+nppId+"' and ngaynhap <= '"+ngayks+"' and trangthai = 9 ";
			if(!db.update(query))
			{
				System.out.println("Khong the huy dhPDA: "+query);
				db.getConnection().rollback();
				return false;
			}
			
			// Chốt phiếu thu hồi
			query = "select a.pk_seq, a.npp_fk, a.trangthai from PHIEUTHUHOI a inner join DONHANG b on a.donhang_fk = b.PK_SEQ " +
					"where a.npp_fk = '"+nppId+"' and b.ngaynhap <= '"+ngayks+"' and a.trangthai = 0 ";
			rs = db.get(query);
			if(rs != null)
			{
				while(rs.next())
				{
					String msg = Chotphieuthuhoi(rs.getString("npp_fk"),rs.getString("pk_seq"),db);
					if(msg.length() > 0)
					{
						System.out.println("Khong the chot PHIEUTHUHOI nay..."+rs.getString("pk_seq"));
						db.getConnection().rollback();
						return false;
					}
				}
				rs.close();
			}
			
			// Xóa đctk
			query = " select pk_seq, npp_fk, trangthai from DIEUCHINHTONKHO where NPP_FK = '"+nppId+"' and NGAYDC <= '"+ngayks+"' and trangthai = 0 ";
			 rs = db.get(query);
			if(rs != null)
			{
				while(rs.next())
				{
					String msg = DeleteDCTK(rs.getString("pk_seq"),db);
					if(msg.length() > 0)
					{
						System.out.println("Khong the xoa DIEUCHINHTONKHO nay..."+rs.getString("pk_seq"));
						db.getConnection().rollback();
						return false;
					}
				}
				rs.close();
			}
			
			// Xóa cknv
			query = " select pk_seq, npp_fk, trangthai from ChuyenKhoNV where npp_fk = '"+nppId+"' and ngaychuyen <= '"+ngayks+"' and trangthai = 0 and pk_seq <> 8";
			rs = db.get(query);
			if(rs != null)
			{
				while(rs.next())
				{
					String msg = DeleteCknv(rs.getString("pk_seq"),db);
					if(msg.length() > 0)
					{
						System.out.println("Khong the xoa ChuyenKhoNV nay..."+rs.getString("pk_seq"));
						db.getConnection().rollback();
						return false;
					}
				}
				rs.close();
			}
			
			// Khóa sổ			
			query = "select a.npp_fk, Max(a.ngayks) as ngayks from khoasongay a inner join nhaphanphoi b on a.npp_fk = b.pk_seq where b.pk_seq = '"+nppId+"' group by NPP_FK ";
			rs = db.get(query);
			if(rs != null)
			{
				while(rs.next())
				{
					query = "insert into TONKHONGAY(NPP_FK, KBH_FK, KHO_FK, SANPHAM_FK, SOLUONG, NGAY) \n" +
							"select a.npp_fk, a.kbh_fk, a.kho_fk, a.sanpham_fk, a.cuoiky, a.NGAYKS \n" +
							"from ufn_XNT_TuNgay_DenNgay_FULL_new("+nppId+", '"+ngayks+"', '"+ngayks+"') a left join NHAPP_KHO b \n" + 
							"	on a.kho_fk = b.KHO_FK and a.npp_fk = b.NPP_FK and a.kbh_fk = b.KBH_FK and a.sanpham_fk = b.SANPHAM_FK \n";
					
					if(!db.update(query)  )
					{
						System.out.println("Loi khong them duoc ton kho ngay"+query);
						db.getConnection().rollback();
						return false;
					}
					query = "\n insert into KHOASONGAY(NPP_FK, NGAYKSGANNHAT, NGAYKS, NGAYTAO, NGUOITAO, chon, doanhso, Created_Date) "+
							"\n select "+rs.getString("npp_fk")+",'"+rs.getString("ngayks")+"','"+ngayks+"','"+getDateTime()+"', '"+nguoitao+"', 1, 0, getdate() ";
					System.out.println("_____ton kho ngay" + query);
					if(db.updateReturnInt(query) <= 0 )
					{
						System.out.println("Lỗi tạo khóa sổ ngày...........");
						db.getConnection().rollback();
						return false;
						
					}
						
				}
			}
			
			query = "	select a.npp_fk, a.kbh_fk, a.kho_fk, a.sanpham_fk, a.cuoiky, isnull(b.SOLUONG, 0) soluong, isnull(b.BOOKED, 0) booked, isnull(b.AVAILABLE, 0) avai \n" +
					"	from ufn_XNT_TuNgay_DenNgay_FULL_new("+nppId+", '"+ngayks+"', '') a left join NHAPP_KHO b \n" + 
					"		on a.kho_fk = b.KHO_FK and a.npp_fk = b.NPP_FK and a.kbh_fk = b.KBH_FK and a.sanpham_fk = b.SANPHAM_FK \n" + 
					"	where a.cuoiky <> isnull(b.SOLUONG, 0) or a.cuoiky <> (isnull(b.AVAILABLE, 0) + isnull(b.BOOKED, 0))";
			System.out.println("câu check tồn: \n"+query);
			String msg = "";
			rs = db.get(query);
			while(rs.next())
			{
				msg += "	Sản phẩm: "+rs.getString("sanpham_fk")+" có tồn cuối = "+rs.getString("cuoiky")+"; số lượng = "+rs.getString("soluong")+"; booked = "+rs.getString("booked")+"; avai = "+rs.getString("avai")+".\n";
			} rs.close();
			if(msg.trim().length() > 0)
			{
				System.out.println("Lệch tồn...........\n"+msg);
				db.getConnection().rollback();
				return false;
			}
			System.out.println("END !!!!!!!");
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch (SQLException e) {
			
			e.printStackTrace();
			try
			{
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
				return false;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		System.out.println("-------------------XONG------------------------------");
		db.shutDown();
		return true;
	}

	public static String DeleteCknv(String id, dbutils db) 
	{
		try 
		{
			//Cap nhat lai kho NPP truoc
			String query = "select a.npp_fk, a.kho_fk, b.sanpham_fk, b.soluong " +
						"from chuyenkhoNV a inner join chuyenkhoNV_sanpham b on a.pk_seq = b.chuyenkhoNV_fk where a.pk_seq = '" + id + "' and a.trangthai = '0' ";
			
			ResultSet rsUpdate = db.get(query);
			if(rsUpdate != null)
			{
				while(rsUpdate.next())
				{
					query = "update nhapp_kho set available = available + '" + rsUpdate.getInt("soluong") + "', booked = booked - '" + rsUpdate.getInt("soluong") + "' " +
							"where npp_fk = '" + rsUpdate.getString("npp_fk") + "' and kho_fk = '" + rsUpdate.getString("kho_fk") + "' and kbh_fk = '100025' and sanpham_fk = '" + rsUpdate.getString("sanpham_fk") + "' ";
					
					if(!db.update(query))
					{
						return "Không thể trừ chuyển kho: " + query;	
					}
				}
				rsUpdate.close();
			}else
			{
				return  "Lỗi không  xóa chuyển kho: " + query;
			}
			
			query = "update chuyenkhoNV set trangthai = '2' where pk_seq = '" + id + "' ";
					
			if(!db.update(query))
			{
				return "Không thể xóa chuyển kho: " + query;
			}
		} 
		catch (Exception e)
		{
			return "Lỗi"+e.getMessage();
		}
		
		return "";
	}
	private static String DeleteDCTK(String dctkId, dbutils db)
	{		
		
		String msg="";
		String query = "";
		try
		{
			
			// CAP NHAT LAI BOOK DOI VOI NHUNG DCTK AM
			String sql = 
					"UPDATE K SET K.BOOKED = BOOKED - DC.DIEUCHINH, K.AVAILABLE = K.AVAILABLE + DC.DIEUCHINH " +
					"FROM NHAPP_KHO K " +
					"INNER JOIN " +
					"( " +
					"	SELECT DC.KHO_FK, DC.KBH_FK, DC.NPP_FK, SANPHAM_FK, (-1)*DCSP.DIEUCHINH AS DIEUCHINH FROM DIEUCHINHTONKHO DC " +
					"	INNER JOIN DIEUCHINHTONKHO_SP DCSP ON DCSP.DIEUCHINHTONKHO_FK = DC.PK_SEQ WHERE DC.PK_SEQ = '"+ dctkId +"' AND DCSP.DIEUCHINH < 0 " +
					") DC ON DC.KHO_FK = K.KHO_FK AND DC.KBH_FK = K.KBH_FK AND DC.NPP_FK = K.NPP_FK AND DC.SANPHAM_FK = K.SANPHAM_FK ";

			System.out.println("Book dieu chinh am : " + sql);

			if(!db.update(sql))
			{
			
				msg="Lỗi khi cập nhật điều chỉnh tồn kho "+sql;
			}
			query = "update dieuchinhtonkho set trangthai = '2' where pk_seq = '"+ dctkId +"' and TrangThai=0 ";
			if(db.updateReturnInt(query)!=1)
			{
			
				msg="Điều chỉnh tồn kho đã xóa rồi !";
			}
		}catch(Exception e)
		{
			msg="Exception xảy ra khi cập nhật ";
			e.printStackTrace();
		}
	
		return msg ;
	}
	private static boolean Delete(String id,String nppId,dbutils db)
	{	
		String query = id + "&" + nppId;
		String param[] = query.split("&");

		int kq = db.execProceduce("DeleteDonHang", param);
		if(kq == -1)
			return false;
		return true;
	}
	private static String DeletePxk(String pxkId,dbutils db) 
	{
		//khi moi tao phieuxuatkho chua lam gi het, nen co the xoa thang
		//String query = "update phieuxuatkho set trangthai = '2' where pk_seq = '" + pxkId + "'";
		//db.update(query);
		String query = "";
		//db.update(query);
		try{
			db.getConnection().setAutoCommit(false);
			 query="delete phieuxuatkho_tienkm where pxk_fk = '" + pxkId + "'";
			if(!db.update(query)){
				db.getConnection().rollback();
				return query;
			}
					
			System.out.println("delete phieuxuatkho_tienkm where pxk_fk = '" + pxkId + "'");
			query="delete phieuxuatkho_sanpham where pxk_fk = '" + pxkId + "'";
			System.out.println(query);
			if(! db.update(query)){
				db.getConnection().rollback();
				return query;
			}
			System.out.println("delete phieuxuatkho_sanpham where pxk_fk = '" + pxkId + "'");
			query="delete phieuxuatkho_spkm where pxk_fk = '" + pxkId + "'";
			System.out.println(query);
			if(! db.update(query)){
				db.getConnection().rollback();
				return query;
			}
			query="delete PHIEUXUATKHO_DONHANG where pxk_fk='"+pxkId+"'";
			System.out.println(query);
			if(! db.update(query)){
				db.getConnection().rollback();
				return query;
			}
		
			query="delete phieuxuatkho where pk_seq = '" + pxkId + "' and trangthai=0 ";
			System.out.println(query);
			if(db.updateReturnInt(query)!=1)
			{
				db.getConnection().rollback();
				return query;
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			
		}catch(Exception er){
			System.out.println(er);
		}
		
		return "";
		
	}
	private static String Chotphieuthuhoi(String nppId, String pthId,dbutils db) 
	{
		String msg = "";
		try 
		{
			//	cap nhat ton kho sanpham
			String query = "select sanpham_fk as spId, soluong, kho_fk as khoId, kbh_fk as kbhId from phieuthuhoi_sanpham where pth_fk = '" + pthId + "'";
			System.out.println("QR: "+query);
			ResultSet rs = db.get(query);
			/*if(rs != null)*/
			{
				while(rs.next())
				{
					query = "update nhapp_kho set soluong = soluong + " + rs.getString("soluong") + ", available = available + " + rs.getString("soluong") + " where sanpham_fk=" + rs.getString("spId") +" and npp_fk = " + nppId + " and kbh_fk=" + rs.getString("kbhId") + " and kho_fk = " + rs.getString("khoId");
					System.out.println("Cau lenh cap nhat san pham la: " + query + "\n");
					if(db.updateReturnInt(query)!=1)
					{
						msg = "Khong the cap nhat nhapp_kho " + query;
						return msg;
					}
				}
				rs.close();
			}
			
			//cap nhat ton kho sanpham khuyen mai
			query = "select sanpham_fk as spId, soluong, kho_fk as khoId, kbh_fk as kbhId from phieuthuhoi_spkm where pth_fk = '" + pthId + "'";
			System.out.println("Cau lenh lay spkm la: " + query + "\n");
			ResultSet rsKm = db.get(query);
			/*if(rsKm != null)*/
			{
				while(rsKm.next())
				{
					query = "update nhapp_kho set soluong = soluong + " + rsKm.getString("soluong") + ", available = available + " + rsKm.getString("soluong") + " where sanpham_fk=" + rsKm.getString("spId") +" and npp_fk = " + nppId + " and kbh_fk=" + rsKm.getString("kbhId") + " and kho_fk = " + rsKm.getString("khoId");
					System.out.println("Cau lenh cap nhat san pham khuyen mai la: " + query + "\n");
					if(db.updateReturnInt(query)!=1)
					{
						msg = "Khong the cap nhat san pham khuyen mai nhapp_kho " + query;
						return msg;
					}
				}
				rsKm.close();
			}
			
			query="update phieuthuhoi set trangthai = '1' where pk_seq = '" + pthId + "'";
			if(!db.update(query))
			{
				msg = "Khong the cap nhat san pham khuyen mai nhapp_kho " + query;
				return msg;
			}
		} 
		catch(Exception e) {
		
			return "Error Here :"+e.toString();
		}finally{
		
		}
		
		return msg;
	}

	private static String getDateTime() 
	{
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date date = new Date();
		return dateFormat.format(date);
	}
}
