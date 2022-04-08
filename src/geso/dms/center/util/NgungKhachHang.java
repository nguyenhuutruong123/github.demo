package geso.dms.center.util;

import geso.dms.center.db.sql.dbutils;

public class NgungKhachHang {
	

	public NgungKhachHang() {
		// TODO Auto-generated constructor stub
		
	}
	
	public static void main(String[] args) {
		
	}
	
	public static void NgungkhachhangkhongphatsinhDS()
	{
		dbutils db=new dbutils();
		try
		{
			
			db.getConnection().setAutoCommit(false);
			String query=" update a set a.trangthai=0 from khachhang a where a.PK_SEQ not in ( \n"+
						 "	select a.KHACHHANG_FK from donhang a where a.NGAYNHAP <= CONVERT(char(10),getdate(),126) and  \n"+
						 " 	a.NGAYNHAP >= convert(char(10),DATEADD(dd,-60,getdate()),126) \n"+
						 "	) and a.TRANGTHAI=1 ";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
			}
			else
			{
				db.getConnection().commit();
				db.getConnection().setAutoCommit(true);
			}
			db.shutDown();
			//System.out.println("olala ");
			
		} catch(Exception e)
		{
			
		}
		
		
	}
	
}
