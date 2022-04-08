package geso.dms.center.beans.importdata;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;
import geso.dms.center.db.sql.*;
import java.io.*;
import java.sql.ResultSet;
import java.util.Date;
import java.util.Hashtable;

public class DatafromSAP {
	  Timer timer;  
	
	  public DatafromSAP()   {
	   		dbutils db = new dbutils("best");
	    	Date d = new Date();
			System.out.println("" + d.toString());
		   	System.out.println ( "Import data to SAP..." ) ;

	   		System.out.println("---------------- Products & Prices-----------------");
//	   		importPrice(db);
	   		System.out.println("---------------- Distributors -------------");
//	   		importDistributors(db);
	   		System.out.println("---------------- Sales orders-------------");
//	   		importSO(db);
	   		System.out.println("---------------- Invoices -------------");
	   		importInvoice(db);   		
	   		System.out.println("---------------- Stocks -------------");
//	   		importStock(db);
	   		db.shutDown();

   		
//	    timer = new Timer (  ) ;
//	    timer.schedule ( new ToDoTask (  ), 0 , 60000) ;
	  }


	  class ToDoTask extends TimerTask  {
	    public void run (  ){
	    	Date d = new Date();
	    	int n = Integer.valueOf(d.toString().substring(14, 16)).intValue();
			if (n%6 == 0){
		   		dbutils db = new dbutils();
				System.out.println("" + d.toString());
			   	System.out.println ( "Import data to SAP..." ) ;

		   		System.out.println("---------------- Products & Prices-----------------");
//		   		importPrice(db);
		   		System.out.println("---------------- Distributors -------------");
//		   		importDistributors(db);
		   		System.out.println("---------------- Sales orders-------------");
//		   		importSO(db);
		   		System.out.println("---------------- Invoices -------------");
		   		importInvoice(db);   		
		   		System.out.println("---------------- Stocks -------------");
//		   		importStock(db);
		   		db.shutDown();

			}
	    }	
	  }
	
	  private void importDistributors(dbutils db){
		try{
			FileInputStream fstream = new FileInputStream("c:\\import\\customer.txt");
	   		DataInputStream in = new DataInputStream(fstream);
	        BufferedReader br = new BufferedReader(new InputStreamReader(in));
	        String date = getDateTime().toString();
	        String strLine;
	        String query; 
	        String[] temp = new String[20];
/*    		query = "select count(pk_seq) as num from sanpham where trangthai='1'";
    		ResultSet rs= db.get(query);
    		rs.next();
    		int count = Integer.valueOf(rs.getString("num")).intValue();
    		String[] spIds = new String[count];
        	query = "select pk_seq as spId from sanpham where trangthai='1'";
        	rs= db.get(query);
        	
        	int i = 0;
        	while (rs.next()){
        		spIds[i] = rs.getString("spId");
        		i++;
    		}
        	rs.close();*/
	        while ((strLine = br.readLine()) != null)   {
	        	temp[0] = strLine.split("\t")[0];
	        	temp[1] = strLine.split("\t")[1];
//	        	System.out.println(temp[0]);
//	        	System.out.println(temp[1]);
	            query = "insert into nhaphanphoi values('','" + temp[1] + "', '" + date + "', '" + date + "', '100000', '100000', '', '1' ,'"+ temp[0] + "', '"+ temp[0] + "', '"+ temp[0] + "','', '', '' , '')";
	        	if (!(db.update(query))){
	        		System.out.println(query);
	        	}
//	Kho se duoc tao ngay sau khi dinh Kenh ban hang, va san pham se duoc dinh ngay sau khi dinh Don vi kinh doanh
/*	        	else{
	        		String[] khoIds = new String[2];
	        		khoIds[0] = 	"100004";
	        		khoIds[1] =  	"100010";
	        		for(int m = 0; m < 2; m++){
	        			String khoId = khoIds[m];	        		
	        			for(int n = 0; n < count; n++){
	        				query = "insert into nhapp_kho values('" + khoId + "', s_nhaphanphoi.currval,'" + spIds[n] + "', '0','0','0', '0','0')";
	        				if (!(db.update(query))){
	        					System.out.println(query);
	        				}
	        			}
	        		}*/
	        	
	          }
	          
	          in.close();			   		
	   	}catch(Exception e){
	   		System.err.println("Error: " + e.getMessage());
	   	}

	  }

	  private void importInvoice(dbutils db){
	        float vat = 0;
	        float tienbvat = 0;
	        float tienavat = 0;
	        
	        float totalvat = 0;
	        float totaltienbvat = 0;
	        float totaltienavat = 0;
	        
	        String nhaphangId = "";
	        String dhId = "";
	        String khoId = "";
	        String nppId;
	        
	        boolean enable;
	        String query; 	
		  try{
				FileInputStream fstream = new FileInputStream("c:\\invoice.txt");
		   		DataInputStream in = new DataInputStream(fstream);
		        BufferedReader br = new BufferedReader(new InputStreamReader(in));
		        String date = getDateTime();
		        String strLine;
		        
		        String[] temp = new String[30];
		        String  chungtu = "";
		        String  chungtu_bk = "";
		        
		        String[] masp = new String[200];
		        String[] sl = new String[200];
		        String[] giamua = new String[200];
		        

		        
		        while ((strLine = br.readLine()) != null)   {


			        
		        	String tmp = strLine.split("\t")[0].trim().replace(".", "");
		        	tmp = tmp.substring(tmp.indexOf("4"), tmp.length());
		        	enable = true;
		        	temp[0] = tmp;
		        	
		        	// So hoa don
		        	if(temp[0].substring(0, 4).equals("4100")){
		        		khoId = "100000";
		        	}else{
		        		khoId = "100001";
		        	}
		        	temp[1] = strLine.split("\t")[1];
		        	temp[2] = strLine.split("\t")[2];
		        	
		        	//ngay hoa don
		        	temp[3] = convertDate(strLine.split("\t")[3]);
		        	
		        	//scheme KM	        	
		        	temp[4] = strLine.split("\t")[4];

		        	temp[5] = strLine.split("\t")[5];
		        	
		        	temp[6] = strLine.split("\t")[6];
		        	
		        	// kenh
		        	temp[7] = strLine.split("\t")[7];		        	
		        	
		        	// 
		        	temp[8] = strLine.split("\t")[8];
		        	
		        	// Ma NPP
		        	temp[9] = strLine.split("\t")[9];
		        		
		        	temp[10] = strLine.split("\t")[10];		        	
		        	temp[11] = strLine.split("\t")[11];
		        	temp[12] = strLine.split("\t")[12];
		        	temp[13] = strLine.split("\t")[13];
		        	temp[14] = strLine.split("\t")[14];
		        	temp[15] = strLine.split("\t")[15];	        	
		        	temp[16] = strLine.split("\t")[16];
		        	
		        	// ma sap
		        	temp[17] = strLine.split("\t")[17];	        	
		        	temp[18] = strLine.split("\t")[18];
		        	
		        	// so luong
		        	temp[19] = strLine.split("\t")[19];
		        	
		        	// donvi
		        	temp[20] = strLine.split("\t")[20];
		        	
		        	//dongia net
		        	temp[21] = strLine.split("\t")[21];		   
		        	
		        	//VND
		        	temp[22] = strLine.split("\t")[22];
		        	
		        	//vat(%)
		        	temp[23] = strLine.split("\t")[23];
		        	
		        	// Don gua gross
		        	temp[24] = strLine.split("\t")[24].trim();

		        	//SO number
		        	temp[25] = strLine.split("\t")[25].trim();
		        	
		        	String dvkdId = "";
		        	String kbhId = null;
		        		        	
		        	ResultSet count;		        	
	        			        
		        	if(temp[7].equals("GT")){
		        		kbhId = "100000";
		        	}else{
		        		kbhId = null;
		        		enable = false;
		        	}
		        
		        	if(temp[6].equals("HPC")){
		        		dvkdId = "100000";
		        	}else{
		        		dvkdId = "100001";
		        	}
	        		chungtu = temp[0];
		        	if(enable){
			        	tmp = "select pk_seq from nhaphanphoi where sitecode in (select CONVSiteCode from nhaphanphoi where sitecode='" + temp[9] + "')";
			        	ResultSet rs = db.get(tmp);
			        	rs.next();
			        	nppId= rs.getString("pk_seq");

		        		if (!(chungtu_bk.equals(chungtu))){
		        			query = "update nhaphang set sotienbvat='" + totaltienbvat + "', vat= '" + totalvat + "', sotienavat = '" + totaltienavat + "' where pk_seq ='" + nhaphangId + "'";

		        			if(!(db.update(query))){
		        				System.out.println("Khong thanh cong:" + query);	
		        			}else{
		        				System.out.println("Thanh cong:" + query);	
		        				totalvat = 0;
		        				totaltienbvat = 0;
		        				totaltienavat = 0;
	    		        
		        				if (dhId.length() > 0){
		        					query = "update dondathang set trangthai='4' where pk_seq ='" + dhId + "'";

		        					if(!(db.update(query))){
		        						System.out.println("Khong thanh cong:" + query);	
		        					}
		        				}
		        			}
		        		}
		        	
			    	
		        		if(temp[4].trim().length()>0){
		        			tmp = "select count(*) as num from ctkhuyenmai a inner join ctkm_npp b on a.pk_seq=b.ctkm_fk and a.scheme='" + temp[4]+"' and b.npp_fk='"+ nppId + "'";
		        			ResultSet ctkm = db.get(tmp);
		        			if(ctkm.next()){	        		
		        				if(ctkm.getString("num").equals("0")){
		        					enable = false;
		        				}
		        			}
		        		}
		        	    
		        	

//		        		if (enable)
//		        			System.out.println("0."+ temp[0] + ",1." + temp[1]+ ",2." + temp[2]+ ",3." + temp[3]+ ",4." + temp[4]+ ",5." + temp[5]+ ",6." + temp[6]+ ",7." + temp[7]+ ",8." + temp[8]+ ",9." + temp[9]+ ",10." + temp[10]+ ",11." + temp[11]+ ",12." + temp[12]+ ",13." + temp[13]+ ",14." + temp[14]+ ",15." + temp[15]+ ",16." + temp[16]+ ",17." + temp[17]+ ",18." + temp[18]+ ",19." + temp[19]+ ",20." + temp[20]+ ",21." + temp[21]+ ",22." + temp[22]+ ",23." + temp[23]+ ",24." + temp[24]+ ",25." + temp[25]);		        	

		        		if(temp[25].length() >0){
		        		
		        			query = "";
		        			if (!(chungtu_bk.equals(chungtu))){
		        				
		        				query = "select count(pk_seq) as num from dondathang where soid='" + temp[25] + "'";				        
//				        		System.out.println(query);	
		        				count = db.get(query);
				        
		        				count.next();
		        				if (!(count.getString("num").equals("0"))){		        	
				        			query = "select pk_seq as dhId, dvkd_fk as dvkdId, npp_fk as nppId, ncc_fk as nccId, kbh_fk as kbhId from dondathang where soid='" + temp[25] + "'";
//		        					System.out.println(query);	
				        			rs = db.get(query);
				        			if(rs.next()){			
		        			
				        				dhId = rs.getString("dhId");
		        							        			
				        				if(enable){
			        						query = "insert into nhaphang (ngaynhan, sotienbvat, nguoitao, nguoisua, trangthai, npp_fk, ncc_fk, vat, sotienavat, dvkd_fk, kbh_fk, dathang_fk, chungtu, ngaychungtu, kho_fk, hdtaichinh)" + 
			        						"values('" + date + "', '0', '100000', '100000', '0' ,'" + rs.getString("nppId") + "','" + rs.getString("nccId")+ "','0' , '0', '" + rs.getString("dvkdId")+ "', '"+ kbhId + "', " + dhId + ",'"+temp[0]+"','" + temp[3]+"','"+ khoId + "','"+ temp[1]+"')";
			        						System.out.println("Truong hop 1:" + query);			        							        					
				        				}
				        			}
		        				}else{
		        					if(enable){	        			        		 
		        						query = "insert into nhaphang (ngaynhan, sotienbvat, nguoitao, nguoisua, trangthai, npp_fk, ncc_fk, vat, sotienavat, kbh_fk, chungtu, ngaychungtu, kho_fk, hdtaichinh)" + 
		        								"values('" + date + "', '0', '100000', '100000', '0' ,'" + nppId + "','100000','0' , '0', '"+ kbhId + "', '" + temp[0] + "','" + temp[3]+"','"+ khoId + "','"+ temp[1]+"')";
		        						System.out.println("Truong hop 2:" + query);	
		        					}
		        				}
		        		
     				
		        				if(query.length()>0){
    					
		        					if(!(db.update(query))){
		        						System.out.println("Khong thanh cong:"+ query);	
		        					}else{			   
		        						query = "select IDENT_CURRENT('nhaphang') as nhaphangId";
		        						rs = db.get(query);
		        						if(rs.next()){
		        							nhaphangId = rs.getString("nhaphangId");
		        			
		        							System.out.println("Thanh cong:"+ query);
		        						}
		        					}
		        				}
		        				chungtu_bk=chungtu;
		        			}
		        			
		        		}
		        		
		        		if(temp[25].length() == 0){
		        			if (!(chungtu_bk.equals(chungtu))){
		        				chungtu_bk=chungtu;

		        			if(enable){
		        				query = "insert into nhaphang (ngaynhan, sotienbvat, nguoitao, nguoisua, trangthai, npp_fk, ncc_fk, vat, sotienavat, kbh_fk, chungtu, ngaychungtu, kho_fk, hdtaichinh) " + 
		        				"values('" + date + "', '0', '100000', '100000', '0' ,'" + nppId + "','100000','0' , '0', '"+ kbhId + "','"+temp[0]+"','" + temp[3]+"','"+ khoId + "','"+ temp[1]+"')";
		        				System.out.println("Truong hop 3:" + query);	

		        				if(!(db.update(query))){
		        					System.out.println("Khong thanh cong:"+ query);	
		        				}else{			   
		        						        					        		
		        					query = "select IDENT_CURRENT('nhaphang') as nhaphangId";
		        					rs = db.get(query);
		        					if(rs.next()){
		        						nhaphangId = rs.getString("nhaphangId");
		        						chungtu = temp[0];
		        					}
		        				}
		        			}
		        		}
		        	}
					        	
			    	if(enable){
			    		tienbvat = Float.valueOf(temp[19].trim().replace(",", "")).floatValue()*Float.valueOf(temp[21].trim().replace(",", "")).floatValue();
			    		totaltienbvat = totaltienbvat + tienbvat;
		        	
			    		vat  = Math.round(tienbvat * 0.1);
			    		totalvat = totalvat + vat;
		        	
			    		tienavat = Math.round(tienbvat*1.1);
			    		totaltienavat = totaltienavat + tienavat;
			    		
			    		long quantity = Math.round(Double.valueOf(temp[19].trim().replace(",", "")).doubleValue());
			    		String scheme;
			    		
			    		if(temp[4].length()>0)
			    			scheme = temp[4];
			    		else
			    			scheme = "";
			    				
			    		if (quantity > 0){
			    			query = "insert into nhaphang_sp values('"+ nhaphangId + "', '" + temp[17] + "', '" + quantity + "', '"+ temp[20] +"', '" + temp[21].replace(",", "") + "', '" + tienbvat + "', '"+ vat + "', '" + tienavat +"','"+temp[24].replace(",", "")+"','" + scheme + "')";
		        			System.out.println(query);
			    			if(!(db.update(query))){
			    				System.out.println("Khong thanh cong:"+ query);	
			    			}else{
			    				System.out.println("Thanh cong:"+ query);
			    			}
			    		}
			    	}
		     
		        }
		        }
    			query = "update nhaphang set sotienbvat='" + totaltienbvat + "', vat= '" + totalvat + "', sotienavat = '" + totaltienavat + "' where pk_seq ='" + nhaphangId + "'";

    			if(!(db.update(query))){
    				System.out.println("Khong thanh cong:" + query);	
    			}else{
    				System.out.println("Thanh cong:" + query);	
    				totalvat = 0;
    				totaltienbvat = 0;
    				totaltienavat = 0;
	        
    				if (dhId.length() > 0){
    					query = "update dondathang set trangthai='4' where pk_seq ='" + dhId + "'";

    					if(!(db.update(query))){
    						System.out.println("Khong thanh cong:" + query);	
    					}
    				}
    			}
		        
		}catch(Exception e){
			System.err.println("Error: " + e.toString()+"");
		}

	 }

	  private void importProducts(dbutils db){
			try{
				FileInputStream fstream1 = new FileInputStream("c:\\import\\product.txt");
				FileInputStream fstream2 = new FileInputStream("c:\\import\\salesunit.txt");
				
				DataInputStream in1 = new DataInputStream(fstream1);
				DataInputStream in2 = new DataInputStream(fstream2);
				
		        BufferedReader br1 = new BufferedReader(new InputStreamReader(in1));
		        BufferedReader br2 = new BufferedReader(new InputStreamReader(in2));
		        	        
		        String date = getDateTime().toString();
		        String strLine;
		        String[] temp = new String[20];
		        Hashtable ht = new Hashtable();
		        while ((strLine = br2.readLine()) != null)   {
		        	temp[0] = strLine.split("\t")[0];
		        	strLine = strLine.split("\t")[1];
		        	temp[1] = strLine.split("\t")[0];
//		        	System.out.println("dvkd=" + temp[1]);
		        	
		        	if(!(ht.contains(temp[0]))){
		        		if(temp[1].equals("HPC")){
		        			ht.put(temp[0], "100000");
//		        			System.out.println("Key: " + temp[0] + " , Value: " + "100026");
		        		}else{
		        			if(temp[1].equals("TP")){
		        				ht.put(temp[0], "100001");
		        			}
		        		}
		        	}
		        }
		        
		        String query; 		        
		        while ((strLine = br1.readLine()) != null)   {
		        	temp[0] = strLine.split("\t")[0];
		        	temp[1] = strLine.split("\t")[1];
//		        	System.out.println(temp[0] + "   " + temp[1]);
		        	
		        	String dvkdId="";
		        	if(ht.containsKey(temp[0])){
		        		dvkdId = (String)ht.get(temp[0]);
			            query = "insert into sanpham values('', '"+ temp[0] +"', '"+ temp[1] +"', '" + date + "', '" + date + "', '100000', '100000', '', '0', '"+ dvkdId +"', '', '')";
//			            System.out.println(query);
			        	if (!(db.update(query))){
			        		System.out.println(query);
			        	}
		        		
		        	}
		        	
		          }
		          
		          in1.close();
		          in2.close();	
		   	}catch(Exception e){
		   		System.err.println("Error: " + e.getMessage()+";");
		   	}

		  }

	  private void importSO(dbutils db){
			try{
				FileInputStream fstream = new FileInputStream("y:\\sovspo.txt");
				
				DataInputStream in = new DataInputStream(fstream);

		        BufferedReader br = new BufferedReader(new InputStreamReader(in));
	        	        
		        String date = getDateTime().toString();
		        String strLine;
		        String[] temp = new String[20];
		        Hashtable ht = new Hashtable();
		        while ((strLine = br.readLine()) != null)   {
		        	temp[0] = strLine.split("\t")[0];
		        	temp[1] = strLine.split("\t")[1];
		        	System.out.println(temp[0] + ";" + temp[1]);
		        	String query = "update dondathang set soId='" + temp[1] + "' where pk_seq='" + temp[0] + "'";
		        	db.update(query);
		          }
		          
		          in.close();	
		   	}catch(Exception e){
		   		System.err.println("Error: " + e.getMessage());
		   	}

		  }

	  private void importStock(dbutils db){
			try{
				FileInputStream fstream = new FileInputStream("y:\\stock.txt");
				
				DataInputStream in = new DataInputStream(fstream);

		        BufferedReader br = new BufferedReader(new InputStreamReader(in));
	        	        
		        String date = getDateTime().toString();
		        String strLine;
		        String[] temp = new String[20];
		        Hashtable ht = new Hashtable();
		        while ((strLine = br.readLine()) != null)   {
		        	temp[0] = strLine.split("\t")[0];
		        	temp[1] = strLine.split("\t")[1];
		        	temp[2] = strLine.split("\t")[2];
		        	temp[3] = strLine.split("\t")[3].trim().replace(",", "");
		        	System.out.println(temp[0] + ";" + temp[1]+ ";" + temp[2]+ ";" + temp[3]);
		        	String query = "insert into tonkhoicp values('" + temp[0] + "', '" + temp[2] + "','" + temp[3] + "','0','" + temp[3]+"')";
		        	if(!db.update(query)){
		        		
		        		query = "update tonkhoicp set stock ='" + temp[3] +"', available = (stock - booked) where masp='" + temp[0]+ "' and kho = '" + temp[2] + "'";		        	

		        		db.update(query);
		        	}
		        	
		        	query = "update tonkhoicp set available = 0 where available < 0";
		        	db.update(query);
		          }
		          
		          in.close();	
		   	}catch(Exception e){
		   		System.err.println("Error: " + e.getMessage());
		   	}

		  }

	  private void importPrice(dbutils db){
			try{
				FileInputStream fstream = new FileInputStream("y:\\price.txt");
				
				DataInputStream in = new DataInputStream(fstream);

		        BufferedReader br = new BufferedReader(new InputStreamReader(in));
	        	        
		        String date = getDateTime().toString();
		        String strLine;
		        String[] temp = new String[20];
		        Hashtable ht = new Hashtable();		        
		        String query = "select pk_seq, ma from sanpham";
		        ResultSet rs = db.get(query);
		        try{
		        	while(rs.next()){
		        		ht.put(rs.getString("ma"), rs.getString("pk_seq"));
		        	}
		        	rs.close();
		        }catch(Exception e){}
		        String bgId = "";
		        String spId = "";
		        String dvkdId = "";
		        while ((strLine = br.readLine()) != null)   {
		        	temp[0] = strLine.split("\t")[0];
		        	temp[1] = strLine.split("\t")[1];
		        	temp[2] = strLine.split("\t")[2];
		        	temp[3] = strLine.split("\t")[3];
		        	temp[4] = strLine.split("\t")[4].replace(",", "");
//		        	System.out.println(temp[0] + ";" + temp[1]+ ";" + temp[2]+ ";" + temp[3]+ ";" + temp[4]);
		        	
		        	if(ht.containsKey(temp[3])){
			        	spId = (String)ht.get(temp[3]);		        		
		        	}else{
		        		spId = "";
		        	}
		        	
        			if(spId.length()==0){
        				String ngay = getDateTime();
        				query = "insert into sanpham values('','" + temp[3] + "', 'Not defined', '" + ngay + "','" + ngay + "', '100000', '100000','','0','" + dvkdId + "','','')";
        				if(!db.update(query)){
        					System.out.println(query);
        				}
        				query = "select IDENT_CURRENT('sanpham') as spId";
        				
        				rs = db.get(query);
        				rs.next();
        				spId = rs.getString("spId"); 
        				
        				System.out.println("spId = " + spId);
        			}
        			
        			
        			query = "insert into tonkhoicp values('"+  temp[3] + "','DWF1', '0', '0', '0')";
        			db.update(query);
        			
        			query = "insert into tonkhoicp values('"+  temp[3] + "','DWP2', '0', '0', '0')";
        			db.update(query);
        			
		          }
		          
		          in.close();	
		   	}catch(Exception e){
		   		System.err.println("Error: " + e.getMessage());
		   	}

		  }


		public String getDateTime() {
	        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	        Date date = new Date();
	        return dateFormat.format(date);	
		}
	  
		private void cleandata(dbutils db){
			ResultSet rs = db.get("select pk_seq from nhaphanphoi ");
		}
		
		private String convertDate(String date) {
	        String newdate ="";
		    int day = Integer.valueOf(date.substring(0, 2)).intValue();		
		    int month = Integer.valueOf(date.substring(3, 5)).intValue();
		    int year = Integer.valueOf(date.substring(6, 10)).intValue();
		    String daystr;
		    
		    if (day < 10){
		    	daystr = "0" + day;
		    }else{
		    	daystr = "" + day;
		    }
	        if (month < 10){
	        	newdate = "" + year + "-0" + month + "-" + daystr;
	        }else{
	        	newdate = "" + year + "-" + month + "-" + daystr;
	        }

	        return newdate;	
		}

		  public static void main ( String args [  ]  )   {
			    new DatafromSAP () ;
			  }
		
}
