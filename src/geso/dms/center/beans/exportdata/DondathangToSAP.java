package geso.dms.center.beans.exportdata;
import java.util.Timer;
import java.util.TimerTask;
import geso.dms.center.db.sql.*;
import java.io.*;
import java.sql.ResultSet;
import java.util.Date;
public class DondathangToSAP {
	  Timer timer;  
	
	  public DondathangToSAP ()   {
		   	try{
		   		dbutils db = new dbutils();
		   		File f = new File("z:\\so_upload.txt");
		   		OutputStream os = (OutputStream)new FileOutputStream(f);
		   		String encoding = "UTF8";
		   		OutputStreamWriter osw = new OutputStreamWriter(os, encoding);
		   		BufferedWriter bw = new BufferedWriter(osw);
		   		ResultSet rs = db.get("select count(pk_seq) as num from dondathang where trangthai='1'");
		   		rs.next();
		   		String[] poIds = new String[Integer.valueOf(rs.getString("num")).intValue()];
		   		rs = db.get("select a.pk_seq as po, c.ten as kbh, b.ma as nppId, e.ma as masp, d.soluong, b.khosap from dondathang a, nhaphanphoi b, kenhbanhang c, dondathang_sp d, sanpham e where a.npp_fk = b.pk_seq and a.kbh_fk=c.pk_seq and a.pk_seq= d.dondathang_fk and d.sanpham_fk = e.pk_seq  and d.soluong > 0 and a.trangthai = '1' order by a.pk_seq");
		   		int i = 0;
		    		
		   		while (rs.next() ) {
		   			String tmp = rs.getString("po");
		   			if (i==0){
		   				poIds[0] = rs.getString("po");    				
		   				i++;
		   			}else{
		   				if (!(tmp.equals(poIds[i-1]))){				
		   					poIds[i] = tmp;
		   					i++;    
		   				}
		   			}
		   			bw.write(rs.getString("po") + "\t" + rs.getString("kbh") + "\t" + rs.getString("nppId")+ "\t" + rs.getString("masp")+ "\t" + rs.getString("soluong")+ "\t" + rs.getString("khosap"));
		   			
		   			System.out.println(rs.getString("po") + "\t" + rs.getString("kbh") + "\t" + rs.getString("nppId")+ "\t" + rs.getString("masp")+ "\t" + rs.getString("soluong")+ "\t" + rs.getString("khosap"));
		   			bw.newLine();
		   			
		   		}
		   		bw.flush();
		   		bw.close();
		   		System.out.println(poIds.length);
		   		for (int m = 0; m < poIds.length; m++){
		   			db.update("update dondathang set trangthai='2' where pk_seq ='" + poIds[m] + "'");
		   			System.out.println("update dondathang set trangthai='2' where pk_seq ='" + poIds[m] + "'");
		   		}

		   		String command = "update tonkhoicp set booked = '0'"; 
	    		if (!db.update(command)){
	    			System.out.println(command);
	    		}
		   		db.shutDown();
		   	}catch (Exception e){
		    	      System.err.println("Error: " + e.getMessage());
		   	}
		  
	    timer = new Timer (  ) ;
	    timer.schedule ( new ToDoTask (  ), 0 , 60000) ;
	  }


	  class ToDoTask extends TimerTask  {
	    public void run (  )   {
			Date d = new Date();
			int n = Integer.valueOf(d.toString().substring(14, 16)).intValue();			
			if (n%6 == 0){
				System.out.println("" + d.toString());
			   	System.out.println ( "Export data to SAP..." ) ;
			   	try{
			   		dbutils db = new dbutils();
			   		File f = new File("z:\\so_upload.txt");
			   		OutputStream os = (OutputStream)new FileOutputStream(f);
			   		String encoding = "UTF8";
			   		OutputStreamWriter osw = new OutputStreamWriter(os, encoding);
			   		BufferedWriter bw = new BufferedWriter(osw);
			   		ResultSet rs = db.get("select count(pk_seq) as num from dondathang where trangthai='1'");
			   		rs.next();
			   		String[] poIds = new String[Integer.valueOf(rs.getString("num")).intValue()];
			   		rs = db.get("select a.pk_seq as po, c.ten as kbh, b.ma as nppId, e.ma as masp, d.soluong, b.khosap from dondathang a, nhaphanphoi b, kenhbanhang c, dondathang_sp d, sanpham e where a.npp_fk = b.pk_seq and a.kbh_fk=c.pk_seq and a.pk_seq= d.dondathang_fk and d.sanpham_fk = e.pk_seq  and d.soluong > 0 and a.trangthai = '1' order by a.pk_seq");
			   		int i = 0;
			    		
			   		while (rs.next() ) {
			   			String tmp = rs.getString("po");
			   			if (i==0){
			   				poIds[0] = rs.getString("po");    				
			   				i++;
			   			}else{
			   				if (!(tmp.equals(poIds[i-1]))){				
			   					poIds[i] = tmp;
			   					i++;    
			   				}
			   			}
			   			bw.write(rs.getString("po") + "\t" + rs.getString("kbh") + "\t" + rs.getString("nppId")+ "\t" + rs.getString("masp")+ "\t" + rs.getString("soluong")+ "\t" + rs.getString("khosap"));
			   			System.out.println(rs.getString("po") + "\t" + rs.getString("kbh") + "\t" + rs.getString("nppId")+ "\t" + rs.getString("masp")+ "\t" + rs.getString("soluong")+ "\t" + rs.getString("khosap"));
			   			bw.newLine();
			    			
			   		}
			   		bw.flush();
			   		bw.close();
			   		System.out.println(poIds.length);
			   		for (int m = 0; m < poIds.length; m++){
			   			db.update("update dondathang set trangthai='2' where pk_seq ='" + poIds[m] + "'");
			   			System.out.println("update dondathang set trangthai='2' where pk_seq ='" + poIds[m] + "'");
			   		}

			   		String command = "update tonkhoicp set booked = '0'"; 
		    		if (!db.update(command)){
		    			System.out.println(command);
		    		}
			   		
			   		db.shutDown();
			   	}catch (Exception e){
			    	      System.err.println("Error: " + e.getMessage());
			   	}

			}
	    }
	  }  
	

	  public static void main ( String args [  ]  )   {
	    new DondathangToSAP () ;
	  }
	

}
