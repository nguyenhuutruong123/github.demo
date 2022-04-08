package geso.dms.center.servlets.donmuahang;

import java.util.*;
import org.openswing.swing.domains.java.*;
import org.openswing.swing.internationalization.java.EnglishOnlyResourceFactory;
import java.sql.*;
import org.openswing.swing.table.profiles.client.*;
import org.openswing.swing.util.client.*;
import java.io.*;
import geso.dms.center.db.sql.*;


public class toExcel 
{
	  public toExcel() 
	  {
	    Hashtable<String, Domain> domains = new Hashtable<String, Domain>();
	    Properties props = new Properties();
	    props.setProperty("NgayDat","Ngay Dat");
	    props.setProperty("NPP","Nha Phan Phoi");
	    props.setProperty("KhuVuc","Khu Vuc");
	    props.setProperty("Vung","Vung");
	    props.setProperty("MaCode","Ma Code");
	    props.setProperty("DienGiai","Dien Giai");
	    props.setProperty("Kho","Kho");
	    props.setProperty("Dat","Slg Dat");
	    props.setProperty("Giao","Slg Giao");
	    props.setProperty("ChenhLech","Chenh Lech");
	
	    ClientSettings clientSettings = new ClientSettings(
	        new EnglishOnlyResourceFactory("£",props,true),
	        domains
	    );
	    ClientSettings.FILTER_PANEL_ON_GRID = true;
	    ClientSettings.AUTO_EXPAND_TREE_MENU = true;
	
	    Domain orderStateDomain = new Domain("ORDERSTATE");
	    orderStateDomain.addDomainPair(new Integer(0),"opened");
	    orderStateDomain.addDomainPair(new Integer(1),"suspended");
	    orderStateDomain.addDomainPair(new Integer(2),"delivered");
	    orderStateDomain.addDomainPair(new Integer(3),"closed");
	    domains.put(orderStateDomain.getDomainId(),orderStateDomain);
	    ClientSettings.ALLOW_OR_OPERATOR = false;
	    ClientSettings.INCLUDE_IN_OPERATOR = false;
	    ClientSettings.GRID_PROFILE_MANAGER = new FileGridProfileManager();
	
	    this.createData();
	    
	    new GridFrame();
	  }
		
	  /**
	   * Create data.
	   */	  
	  public void createData() 
	  {
		  	dbutils db = new dbutils();
		    try 
		    {	  			      
		    	  String sql = "select distinct a.dondathang_fk, a.sanpham_fk as masp, d.ten as tensp, b.ngaydat, a.soluong as dadat, c.soluong as dagiao, h.soluong1 as quycach, e.khosap as kho, g.ten as vung, f.ten as khuvuc, e.ten as nppTen"
					+ " from thieuhang a, dondathang b, dondathang_sp c, sanpham d, nhaphanphoi e, khuvuc f, vung g, quycach h"
					+ " where a.dondathang_fk=b.pk_seq and b.pk_seq=c.dondathang_fk and c.sanpham_fk = d.pk_seq and a.sanpham_fk = d.ma and b.npp_fk = e.pk_seq and e.khuvuc_fk = f.pk_seq and f.vung_fk=g.pk_seq and h.sanpham_fk=d.pk_seq and h.dvdl2_fk='100018' order by b.ngaydat, a.sanpham_fk, e.khosap, g.ten, f.ten, e.ten";
		      
		    	  ResultSet rs = db.get(sql);

		    	  FileOutputStream out = new FileOutputStream("orders.txt");
			      String line = null;
			      
			      if(rs != null)
			      {
			    	  while(rs.next())
			    	  {
			    		  String ngaydat = rs.getString("ngaydat");
			    		  String nppTen = rs.getString("nppTen");
			    		  String khuvuc = rs.getString("khuvuc");			    		  
			    		  String vung = rs.getString("vung");
			    		  String kho = rs.getString("kho");
			    		  String masp = rs.getString("masp");
			    		  String tensp = rs.getString("tensp");			    		  
			    		  String dadat = rs.getString("dadat");
			    		  String dagiao = rs.getString("dagiao");
			    		  String chenhlech = "" + (Long.valueOf(rs.getString("dadat")).longValue() - Long.valueOf(rs.getString("dagiao")).longValue());
			    		  
			    		  line = masp + ";" + tensp + ";" + ngaydat + ";" + dadat + ";" + dagiao + ";" + chenhlech + ";" + kho + ";" + vung + ";" + khuvuc + ";" + nppTen + ";\n";
			  			 
			    		  out.write(line.getBytes());
				          out.flush();
			    	  }
			    	  rs.close();
			    	  db.shutDown();
			      }
			      out.close();
			 }
		    catch (Exception ex) {ex.printStackTrace();}
	  }
	  
	  private String convertDate(String date) //chuyen kieu 2011-12-20 thanh 20/12/2011
	  {
		  String kq = "";
		  if(date.indexOf("-") > 0)
		  {
			  String[] str = date.split("-");
			  kq = str[2] + "/" + str[1] + "/" + str[0];
		  }
		  return kq;
	  }
	  
	  //test
	  public static void main(String[] argv) 
	  {
		  	toExcel clien = new toExcel();
		  	System.out.print("Create thanh cong....");
	  }
	


}
