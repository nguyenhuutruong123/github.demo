package demo.test;
import geso.dms.center.db.sql.dbutils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.lowagie.tools.split_pdf;
public class Test
{
	public static void main(String[] args) {
		 
		/*dbutils db = new dbutils();
		String mavung = "11";
		ResultSet rskd = db.get("select pk_seq,quanhuyen_fk from khachhang where pk_seq in (select KHID from KHTEMP ) ");
		int k = 0;
		if(rskd != null)
		{	try {
			while(rskd.next())
			{
				String query;
			
					query = "select distinct case  \n" +  
							"when b.VUNG_FK  = 100030  then 11 \n" +  
							"when b.VUNG_FK = 100032 then 12 \n" +  
							"when b.VUNG_FK = 100031 then 13 \n" +  
							"when b.VUNG_FK = 100029 then 15 \n" +  
							"else 14 \n" +  
							"end as mavung	 \n" +  
							" from KHUVUC_QUANHUYEN a inner join KHUVUC b on a.KHUVUC_FK = b.PK_SEQ \n" +  
							" where a.QUANHUYEN_FK = '"+rskd.getString("quanhuyen_fk")+"' \n";
		
				ResultSet	rs = db.get(query);
				rs.next();
				mavung = rs.getString("mavung");
				rs.close();
				System.out.println("mavung "+mavung);
				query = "select isnull(MAX(cast(subString(isnull(SMARTID,'0'),3,LEN(SMARTID) ) as int))+1,'1') as ma from khachhang where SMARTID like '"+mavung+"%'";
				
					rs = db.get(query);
				rs.next();
				String makh = rs.getString("ma");
				rs.close();
				System.out.println("ma kh "+makh);
				k++;
				String chuoi = "";
				for (int i = 0; i < (5 - makh.length()); i++)
					chuoi += "0";
			
				String ma = mavung+chuoi + makh;
				if(ma.length() > 0)
				{
					query = "update khachhang set SMARTID = '"+ma+"' where pk_seq = '"+rskd.getString("pk_seq")+"' ";
					System.out.println("MHKH "+k);
					db.update(query);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("---------------Xong-------------------");
		}
		db.shutDown();*/
		
		String ngaynhan = "2016-08-31 00:00:00.0";
		String ngayks = "2016-07-31";
		
		if(ngayks.compareTo(ngaynhan.substring(0,10)) >= 0)
		{
			System.out.println("Kq "+ngaynhan.substring(0,10).length());
			
		}
				
	
 
	}
}


