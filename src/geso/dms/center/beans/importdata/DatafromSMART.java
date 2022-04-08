package geso.dms.center.beans.importdata;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Hashtable;
import java.util.Timer;
import java.util.TimerTask;
import geso.dms.center.db.sql.dbutils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.sql.ResultSet;
import java.util.Date;

import org.eclipse.jdt.internal.compiler.ast.ThisReference;

public class DatafromSMART {
	  Timer timer;  
	  dbutils best;
	  dbutils smart;
	  
	  public DatafromSMART()   {
	   		best = new dbutils("best");
	   		smart = new dbutils("smart");
	    	Date d = new Date();
	    	this.Congnokhachhang();
	   		best.shutDown();
	   		smart.shutDown();

	  }


	  class ToDoTask extends TimerTask  {
	    public void run (  ){
	    	Date d = new Date();
	    	int n = Integer.valueOf(d.toString().substring(14, 16)).intValue();
	    	
			if (n%6 == 0){
		   		best = new dbutils("best");
		   		smart = new dbutils("smart");

		   		smart.shutDown();
		   		best.shutDown();

			}
	    }	
	  }
	  private void Congnokhachhang(){
		  Hashtable kh = this.getKhachhang();
		  Hashtable applydoc = new Hashtable();
		  String khId = "";
		  String dhId = "";
		  
		  String query="	  SELECT APPLYTODOCNO, CUSTOMER_KEY, TRNTYP, DOCDATE, AGEDATE, "+ 
		  			" ISNULL(DAYSTILLDUE,0) AS DAYSTILLDUE, DOCAMT, SALESPKEY  "+
				 " FROM ARTRAN   "+
				  " WHERE rtrim(CUSTOMER_KEY) <> '1_VENDORPR' AND "+
				  " APPLYTODOCNO IN (SELECT APPLYTODOCNO FROM ARTRAN "+ 
				  " WHERE (cast (SUBSTRING(AGEDATE, 4,2) as int) >5 AND  SUBSTRING(AGEDATE,7,4) ='2011') or (SUBSTRING(AGEDATE,7,4) ='2012') ) "+
				    " order by APPLYTODOCNO,DOCAMT desc ";		  				 
		  			 
		  		
		  ResultSet rs = this.smart.get(query);
		  String applytodoc = "";
		  int i=1;
		  try{
			  while(rs.next()){
				  applytodoc = rs.getString("APPLYTODOCNO");
//				  System.out.println("applytodoc 1:" + applytodoc);
				  
				  String smartId = "K-T-KH-LG_"+ rs.getString("CUSTOMER_KEY");
				  khId = (String)kh.get(smartId);
				  
				  smartId = smartId + "_" + rs.getString("SALESPKEY") + "_" + this.convertDate(rs.getString("DOCDATE"));
//				  System.out.println(smartId);
				  
				  query = "SELECT PK_SEQ AS DHID FROM DONHANG WHERE SMARTID ='" + smartId + "'";
				  ResultSet dh = this.best.get(query);
				  
//				  System.out.println(query);
				  
				  if(dh.next()) 
					  dhId = dh.getString("DHID");
				  else{
					  dhId = null;
					 // System.out.println("Khong lay duoc don hang " + smartId   );
				  }
				  if(rs.getString("TRNTYP").equals("I")){
					  applydoc.put(rs.getString("APPLYTODOCNO").trim(), dhId);
					  
					  query = "INSERT INTO KHACHHANG_CONGNO (KHACHHANG_FK, SOTIENNO, NGAYNO, DIENGIAI, DONHANG_FK) " +
				  		  "VALUES('" + khId + "','" + rs.getString("DOCAMT")+ "','" + this.convertDate(rs.getString("AGEDATE")) + 
				  		  "','" + rs.getString("DAYSTILLDUE") + "','" + dhId + "')";
					  
					  
					  if(!this.best.update(query)){
						  System.out.println("Smart Id " + i + ": " + smartId);
						  System.out.println("APPLYTODOCNO  " + i + ": " + rs.getString("APPLYTODOCNO"));
						  System.out.println("bi loi " + i + ": " + query   );
						  i++;
					  
				  	}
				  }else{
					  
					  String tmp = (String) applydoc.get(rs.getString("APPLYTODOCNO").trim());
					  query = "INSERT INTO PHIEUTHANHTOAN (NGAY, DONHANG_FK, SOTIEN) VALUES('" +
								  "" +this.convertDate(rs.getString("AGEDATE")) + "','" + tmp + "'," + Float.parseFloat(rs.getString("DOCAMT"))*(-1) + ")";
					  					  
					  if(!this.best.update(query)){
						  System.out.println("bi loi" + i + ": " + query);
						  System.out.println("Smart Id " + i + ": " + smartId);
						  System.out.println("APPLYTODOCNO  " + i + ": " + rs.getString("APPLYTODOCNO"));
					  }
					  i++;
				  }					  
			  }  
		  }catch(Exception err){
			  System.out.println(err.toString());
		  }
	  }
	  
	  private void exportDINtoFile(dbutils db1){
	   	try{
	   		File f = new File("c:\\DIN.txt");
	   		OutputStream os = (OutputStream)new FileOutputStream(f);
	   		String encoding = "UTF8";
	   		OutputStreamWriter osw = new OutputStreamWriter(os, encoding);
	   		BufferedWriter bw = new BufferedWriter(osw);
	   		ResultSet rs = db1.get("select SITECODE,WHSECODE,SKUCODE,INVDATE,ONORDQTY,ONHANDQTY,ONHANDAMT,PURQTY,PURCOGS,SALESQTY,SALESCOGS,SALESAMT,RETURNQTY,RETURNCOGS,RETURNAMT,MOVEINQTY,MOVEINCOGS,MOVEOUTQTY,MOVEOUTCOGS,ADJQTY,ADJCOGS,DEBQTY,DEBCOGS,CHANEL from DIN201101");
	   		int i = 0;
	    	try{	
	    		while (rs.next() ) {
	    			System.out.print("" + i++);
	    			bw.write(rs.getString("SITECODE") + "\t" + rs.getString("WHSECODE")+ "\t" + rs.getString("SKUCODE")+ "\t" + rs.getString("INVDATE")+ "\t" + rs.getString("ONORDQTY")+ "\t" + rs.getString("ONHANDQTY")+ "\t" + rs.getString("ONHANDAMT")+ "\t" +rs.getString("PURQTY")+ "\t" +rs.getString("PURCOGS")+ "\t" +rs.getString("SALESQTY")+ "\t" +rs.getString("SALESCOGS")+ "\t" +rs.getString("SALESAMT")+ "\t" +rs.getString("RETURNQTY")+ "\t" +rs.getString("RETURNCOGS")+ "\t" +rs.getString("RETURNAMT")+ "\t" +rs.getString("MOVEINQTY")+ "\t" +rs.getString("MOVEINCOGS")+ "\t" +rs.getString("MOVEOUTQTY")+ "\t" +rs.getString("MOVEOUTCOGS")+ "\t" +rs.getString("ADJQTY")+ "\t" +rs.getString("ADJCOGS")+ "\t" +rs.getString("DEBQTY")+ "\t" + rs.getString("DEBCOGS")+ "\t" + rs.getString("CHANEL") );
	    			bw.newLine();
	    		}
	    		bw.flush();
	    		bw.close();
	    		rs.close();
	    	}catch(Exception e){}
	   		db1.shutDown();
	   	}catch (Exception e){
	    	      System.err.println("Error: " + e.getMessage());
	   	}

		  
	  }
	  
	  private void Donvikinhdoanh(){
		  ResultSet isbu = smart.get("select * from insbu");
		  try{
			  
			  while (isbu.next()){
				  String tmp = "insert into donvikinhdoanh values('" + isbu.getString("description")+"','" +this.getdate() + "','" +this.getdate() + "','100000','100000', '1','" + isbu.getString("description")+"','" + isbu.getString("sbukey") + "')";
				  if(best.update(tmp))
					  System.out.println(tmp);
			  }
			  
			  ResultSet dvkd = best.get("select pk_seq as id from donvikinhdoanh");
			  while(dvkd.next()){
				  String tmp = "insert into NHACUNGCAP_DVKD values('100000','"+ dvkd.getString("id") + "', '1')";
				  if(best.update(tmp))
					  System.out.println(tmp);
			  }
			  
		  }catch(Exception e){
			  System.out.println(e.toString());			  
		  }
	  }
	  
	  private void Nhanhang(){
		  ResultSet incommod = smart.get("select commodkey, description, SBUKey from INCOMMOD");  
		  
		  try{
			  while(incommod.next()){
				  String tmp = "select pk_seq as dvkdId from donvikinhdoanh where smartid='"+ incommod.getString("sbukey")+"'";
				  System.out.println(tmp);
				  ResultSet dvkd = best.get(tmp);
				  dvkd.next();
				  
				  tmp = "insert into NHANHANG values('"+ incommod.getString("description")  +"','1', '"+ this.getdate()+"','"+ this.getdate()+"', '100000', '100000', '"+ dvkd.getString("dvkdid") + "','"+ incommod.getString("commodkey")+"')";
				  if(best.update(tmp))
					  System.out.println(tmp);
			  }
		  }catch(Exception e){
			  System.out.println(e.toString());
		  }
	  }
	  
	  private void Chungloai(){
		  ResultSet inclass = smart.get("select inclasskey, description, CommodKey from INCLASS");  
		  String tmp;		  
		  try{
			  while(inclass.next()){
				  
				  tmp = "insert into CHUNGLOAI values('"+ inclass.getString("description")  +"', '"+ this.getdate()+"','"+ this.getdate()+"', '100000', '100000','1','"+ inclass.getString("inclasskey")+"')";				 				  
				  System.out.println(tmp);
				  if(best.update(tmp)){
					  System.out.println(tmp);
				  
					  tmp = "select IDENT_CURRENT('CHUNGLOAI')as clId";
					  ResultSet cl = best.get(tmp);
					  cl.next();

					  tmp = "select pk_seq as nhId from NHANHANG where smartid='"+ inclass.getString("commodkey")+"'";
					  
					  ResultSet nh = best.get(tmp);
					  nh.next();

					  tmp = "insert into nhanhang_chungloai values('" + cl.getString("clId") + "', '" + nh.getString("nhId")+ "')";
					  if(best.update(tmp))
						  System.out.println(tmp);
				  }
			  }
		  }catch(Exception e){
			  System.out.println(e.toString());
		  }
	  }
	  
	  private void Sanpham(){
		  Hashtable<String, String> nh = this.getNhanhang();
		  Hashtable<String, String> cl = this.getChungloai();
		  Hashtable<String, String> dvkd = this.getDonvikinhdoanh();
		  String nhId = "";
		  String clId = "";
		  String dvkdId = "";
		  String tmp = "select distinct a.itemkey as masp, a.description as tensp, b.commodkey as nhId, b.description, c.inclasskey as clId, c.description, b.sbukey as dvkdId, d.description from inmast a  left join incommod b on b.commodkey=a.commodkey left join inclass c on c.inclasskey=a.inclasskey  inner join insbu d on d.sbukey=b.sbukey where len(a.itemkey)>1 order by masp";
		  ResultSet rs = smart.get(tmp);
		  
		  try{
			  while(rs.next()){
				  tmp = "insert into sanpham values('"+ rs.getString("masp")+"','"+ rs.getString("tensp") + "','"+ this.getdate() +"','"+ this.getdate() +"','100000','100000', null , '1', ";
				  if(dvkd.containsKey(rs.getString("dvkdId"))){
					  dvkdId = dvkd.get(rs.getString("dvkdId"));
					  tmp = tmp + "'"+ dvkdId + "'";
				  }else{
					  dvkdId = "";
					  tmp = tmp + "null";
				  }

				  if(nh.containsKey(rs.getString("nhId"))){
					  nhId = nh.get(rs.getString("nhId")); 
					  tmp = tmp+ ", '"+ nhId +"'";
				  }else{
					  nhId = "";
					  tmp = tmp + "null";
				  }
				  
				  if(cl.containsKey(rs.getString("clId"))){
					  clId = cl.get(rs.getString("clId"));
					  tmp = tmp + ", '"+ clId +"'";
				  }else{
					  clId = "";
					  tmp = tmp + "null";  
				  }
				  
				  tmp = tmp + ",'0')";

				  if(best.update(tmp))
					  System.out.println(tmp);
			  }
		  }catch(Exception e){
			  System.out.println(e.toString());
			  
		  }
		  
	  }
	  
	  private void UpdateTrangthaiSanpham(){
		  best.update("update sanpham set trangthai='0' where pk_seq in (select pk_seq from sanpham where len(ma)<13 or len(ten)<2)");
		  
	  }
	  
	  private void Banggiabanlechuan(){
		  Hashtable dvkd = this.getDonvikinhdoanh();
		  Hashtable sp = this.getSanpham();
		  
/*		  ResultSet dvkd_rs = best.get("select pk_seq, donvikinhdoanh, smartid from donvikinhdoanh");
		  
		  try{
			  while(dvkd_rs.next()){
				  String tmp= "insert into banggiabanlechuan values('Bang gia ban le cua Don vi kinh doanh "+ dvkd_rs.getString("donvikinhdoanh") +"','2012-01-01','2012-01-01', '100000','100000', '"+dvkd_rs.getString("pk_seq")+"','1')";
				  
				  System.out.println(tmp);
				  best.update(tmp);

				  tmp = "select IDENT_CURRENT('banggiabanlechuan')as bgblcId";
				  ResultSet bgblc = best.get(tmp);
				  bgblc.next();
				  
				  String bgblcId = bgblc.getString("bgblcId");

				  String smartBU = dvkd_rs.getString("smartid");
				  tmp = "select a.itemkey, a.price, b.sbukey from inmast a inner join incommod b on b.commodkey = a.commodkey and b.sbukey='"+smartBU+"'";
				  System.out.println();
				  
				  ResultSet spRS = smart.get(tmp);
				  while(spRS.next()){
					  tmp = "insert into BANGGIABLC_SANPHAM values('" +sp.get(spRS.getString("itemkey"))+"','"+ bgblcId +"','"+spRS.getString("price")+"')";
					  System.out.println(tmp);
					  best.update(tmp);
				  }
			  }
		  }catch(Exception e){}*/
		  
		  ResultSet bgblc = best.get("select pk_seq, dvkd_fk from banggiabanlechuan");
		  String tmp="";
		  try{
			  while(bgblc.next()){
				  
				  String bgblcId = bgblc.getString("pk_seq");

				  String dvkd_fk = bgblc.getString("dvkd_fk");
				  
				  if(dvkd_fk.equals("100000")){
					  tmp = "select a.itemkey, a.price, b.sbukey from inmast a inner join incommod b on b.commodkey = a.commodkey and b.sbukey='001' where a.description not like '%MT%' and a.price>0 and len(itemkey)=13 and len(a.description)>1 ";
				  }
				  
				  if(dvkd_fk.equals("100001")){
					  tmp = "select a.itemkey, a.price, b.sbukey from inmast a inner join incommod b on b.commodkey = a.commodkey and b.sbukey='002' where a.description not like '%MT%' and a.price>0 and len(itemkey)=13 and len(a.description)>1 ";
				  }

				  if(dvkd_fk.equals("100002")){
					  tmp = "select a.itemkey, a.price, b.sbukey from inmast a inner join incommod b on b.commodkey = a.commodkey and b.sbukey='003' where a.description not like '%MT%' and a.price>0 and len(itemkey)=13 and len(a.description)>1 ";
				  }

				  
				  System.out.println(tmp);
				  
				  ResultSet spRS = smart.get(tmp);
				  while(spRS.next()){
					  tmp = "insert into BANGGIABLC_SANPHAM values('" +sp.get(spRS.getString("itemkey"))+"','"+ bgblcId +"','"+spRS.getString("price")+"')";
					  if(best.update(tmp))
						  System.out.println(tmp);
				  }
			  }
		  }catch(Exception e){}

	  }
	  private void Kenhbanhang(){
		  ResultSet channel = smart.get("select rchanel,active,chanelID from chanel");
		  try{
			  
			  while (channel.next()){
				  String tmp = "insert into Kenhbanhang values('" + channel.getString("rchanel")+"','" + channel.getString("active")+"','2012-01-01','2012-01-01','100000','100000','" + channel.getString("rchanel")+"','" + channel.getString("chanelID") + "')";
				  if(best.update(tmp))
					  System.out.println(tmp);
			  }
			  
			  
		  }catch(Exception e){
			  System.out.println(e.toString());			  
		  }
	  }
	  
	  private void Nhaphanphoi(){
		  Hashtable kv = this.getKhuvuc();
		  String tmp = "select SiteName, Area, stop, ERPCode, Sitecode, CNVSiteCode, Address, Tel from distributor "; 
		  						  
		  ResultSet rs = smart.get(tmp);
		  try{
			  while(rs.next()){
				  String erpcode = rs.getString("erpcode");
				  String sitecode = rs.getString("sitecode");
				  String kvId = (String)kv.get(rs.getString("Area"));
				  String CNVSite = rs.getString("CNVSiteCode");
				  String ten = rs.getString("SiteName");
				  String diachi = rs.getString("Address");
				  String dienthoai = rs.getString("tel");
				  
				  tmp = "select count(pk_seq) as num from nhaphanphoi where ma='" + erpcode + "'";
				  ResultSet rs2 = best.get(tmp); 
				  rs2.next();
				  
				  if(erpcode == null) erpcode = sitecode;
				  else{ 
					  if(erpcode.equals("NULL") || erpcode.equals("null")||erpcode.length()==0){
						  erpcode = sitecode;
					  }
				  }
				  tmp = "insert into nhaphanphoi values('" + ten + "','"+ this.getdate() +"','"+ this.getdate() +"','100000','100000','"+ kvId + "','1','"+ erpcode + "','"+ erpcode + "','"+ sitecode + "','null','null','NULL','DWF1',null,null,null,'',null,'',null,null,'" + CNVSite + "')";
				  if(best.update(tmp))
					  System.out.println(tmp);
						  
			  }
			  
		  }catch(Exception e){
			  System.out.println(e.toString());
		  }
	  }
	  
	  private void updateCNVSiteCode(){
		  ResultSet rs1 = best.get("select pk_seq, sitecode from nhaphanphoi");
		  
		  try{
			 while(rs1.next()){
				 String sitecode = rs1.getString("sitecode");		 
				 String tmp = "select CNVSiteCode from distributor where sitecode='"+ sitecode + "'";
				 System.out.println(tmp);
				 ResultSet rs2 = smart.get(tmp);
				 rs2.next();
				 tmp = "update nhaphanphoi set CONVSiteCode='" + rs2.getString("CNVSiteCode")+"' where pk_seq = '"+ rs1.getString("pk_seq")+"'";
				 System.out.println(tmp);
				 best.update(tmp);
			 }
		  }catch(Exception e){}
		  
	  }
	  
	  private void Daidienkinhdoanh(){
		  Hashtable npp = this.getNPP();
		  ResultSet rs1 = smart.get("select sitecode, salesp_key, Salesp_Name, active from ARSALESP");
		  String tmp ="";
		  try{
			  while(rs1.next()){
				  if(npp.containsKey(rs1.getString("sitecode"))){
					  tmp = "insert into daidienkinhdoanh (ten, dienthoai, diachi, npp_fk, trangthai, nguoitao, nguoisua, ngaytao, ngaysua) values('" + rs1.getString("Salesp_Name") + "','','','" + npp.get(rs1.getString("sitecode"))+"','" + rs1.getString("active")+ "','100000','100000','"+ this.getdate() +"','"+ this.getdate() +"','"+rs1.getString("sitecode")+"_" + rs1.getString("salesp_key")+"')";
				  }else{
					  String nppId = this.InsertDistributor(rs1.getString("sitecode"));
					  npp = this.getNPP();
					  
					  tmp = "insert into daidienkinhdoanh (ten, dienthoai, diachi, npp_fk, trangthai, nguoitao, nguoisua, ngaytao, ngaysua) values('" + rs1.getString("Salesp_Name") + "','','', '" + nppId + "','" + rs1.getString("active")+ "','100000','100000','"+ this.getdate() +"','"+ this.getdate() +"','"+rs1.getString("sitecode")+"_" + rs1.getString("salesp_key")+"')";
				  }
				  
				  if(best.update(tmp)){
					  System.out.println("Thanh cong: " + tmp);
				  }else{
					  System.out.println("Khong thanh cong: " + tmp);
				  }
			  }
		  }catch(Exception e){}
	  }

	  private String InsertDistributor(String sitecode){
		  Hashtable kv = this.getKhuvuc();
		  String tmp = "select SiteName, Area, stop, ERPCode, Sitecode, CNVSiteCode, Address, Tel from distributor where sitecode = '" + sitecode+"'";
		  System.out.println(tmp);
		  String nppId="";
		  ResultSet rs = smart.get(tmp);
		  try{
			  while(rs.next()){
				  String erpcode = rs.getString("erpcode");
				  String site = rs.getString("sitecode");
				  String kvId = (String)kv.get(rs.getString("Area"));
				  String CNVSite = rs.getString("CNVSiteCode");
				  String ten = rs.getString("SiteName");
				  String diachi = rs.getString("Address");
				  String dienthoai = rs.getString("tel");
				  
				  tmp = "insert into nhaphanphoi values('" + ten + "','"+ this.getdate() +"','"+ this.getdate() +"','100000','100000','"+ kvId + "','1','"+ erpcode + "','"+ erpcode + "','"+ sitecode + "','null','null','NULL','DWF1',null,null,null,'',null,'',null,null,'" + CNVSite + "')";
				  if(best.update(tmp))
					  System.out.println(tmp);
				  
				  tmp = "select IDENT_CURRENT('NHAPHANPHOI')as nppId";
				  ResultSet npp = best.get(tmp);
				  npp.next();
				  nppId = npp.getString("nppId");
			  }
			  
		  }catch(Exception e){
			  System.out.println(e.toString());
		  }

		return nppId;
	  }

	  private void GiamSatBanHang(){
		  Hashtable kv = this.getKhuvuc();
		  Hashtable kbh = this.getKenhbanhang();
		  String tmp = "select distinct b.SSCode, b.SSName, c.area, c.chanel from ARSALESP a inner join ARSS b on a.SSCode = b.SSCode" +
		  	 			" inner join distributor c on c.sitecode=a.sitecode";
		  ResultSet rs = smart.get(tmp);
		  try{
			  while(rs.next()){
				  tmp = "insert into Giamsatbanhang values('"+ rs.getString("SSName")+"','','','1','"+ this.getdate() +"','"+ this.getdate() +"','100000','100000','100000','"+kbh.get(rs.getString("chanel"))+"','','"+ kv.get(rs.getString("area"))+"','100000','"+rs.getString("SScode")+"_"+rs.getString("Area")+"')" ;
				  if(best.update(tmp))
					  System.out.println(tmp);
			  }
		  
		  }catch(Exception e){
			  System.out.println(e.toString());
		  }
	  }
	  
	  private void GSBH_DDKD(){
		  Hashtable ddkd = this.getDaidienkinhdoanh();
		  Hashtable gsbh = this.getGSBH();
		  
		  String tmp = "select  b.SSCode,c.area, a.sitecode,a.salesp_key "+
		  			   "from ARSALESP a inner join ARSS b on a.SSCode = b.SSCode "+
		  			   "inner join distributor c on c.sitecode=a.sitecode order by SSNAme";
		  ResultSet rs = smart.get(tmp);
		  try{
			  while(rs.next()){
				  String ddkdId = (String)ddkd.get(rs.getString("sitecode")+"_"+rs.getString("salesp_key"));
				  String gsbhId = (String)gsbh.get(rs.getString("sscode")+"_"+rs.getString("area"));
				  tmp = "insert into ddkd_gsbh values('"+ddkdId+"','"+gsbhId+"')";
				  if(best.update(tmp))
					  System.out.println(tmp);
			  }
		  }catch(Exception e){
			  System.out.println(e.toString());
		  }
	  }
	  
	  private void Loaicuahang(){
		  String tmp = "select class_key, Class_Descr, active from arclass";
		  ResultSet rs = smart.get(tmp);
		  try{
			  while(rs.next()){
				 tmp = "insert into loaicuahang values('"+rs.getString("class_descr")+"','"+rs.getString("active")+"','" + this.getdate() +"','"+ this.getdate() +"','100000','100000','"+ rs.getString("class_descr") +"', '"+rs.getString("class_key")+"')";
				 
				 if(best.update(tmp))
				 	System.out.println(tmp);
			  }
		  }catch(Exception e){}
	  }

	  private void Hangcuahang(){
		  String tmp = "select otypekey, OtypeName from otype";
		  ResultSet rs = smart.get(tmp);
		  try{
			  while(rs.next()){
				 tmp = "insert into hangcuahang values('"+rs.getString("OtypeName")+"','1','"+ this.getdate() +"','"+ this.getdate() +"','100000','100000','"+ rs.getString("OtypeName") +"', '"+rs.getString("otypekey")+"')";
				 if(best.update(tmp))
					 	System.out.println(tmp);
			  }
		  }catch(Exception e){}
	  }


	  private void Vitricuahang(){
		  String tmp = "select otypekey, OtypeName from otype";
		  ResultSet rs = smart.get(tmp);
		  try{
			  while(rs.next()){
				 tmp = "insert into hangcuahang values('"+rs.getString("OtypeName")+"','1','"+ this.getdate() +"','"+ this.getdate() +"','100000','100000','"+ rs.getString("OtypeName") +"', '"+rs.getString("otypekey")+"')";
				 if(best.update(tmp))
					 	System.out.println(tmp);
			  }
		  }catch(Exception e){}
	  }
	  
	  private void Khachhang(){
		  Hashtable kbh = this.getChannel();
		  Hashtable hch = this.getHangcuahang();
		  Hashtable lch = this.getLoaicuahang();
		  Hashtable vtch = this.getVitricuahang();
		  Hashtable npp = this.getNPP();
		  String loai = "";
		  String hang = "";
		  String vitri = "";
		  
		  String tmp = "select b.customername, b.active, b.chanel, b.otype, b.Tradesegment, b.sitecode,b.subtradesegment, b.tel, b.address, b.customerKey from arsalesp a inner join route b on a.sitecode=b.sitecode and a.salesp_key = b.salepersonKey order by a.sitecode";
		  
		  ResultSet rs = smart.get(tmp);
		  long i=1;
		  try{
			  while(rs.next()){
				  if(rs.getString("Tradesegment")==null){
					  loai = null;
				  }else{
					  if (!lch.containsKey(rs.getString("Tradesegment"))){
						  loai = null;
					  }else{
						  loai = (String)lch.get(rs.getString("Tradesegment"));
					  }
				  }
				  
				  if(rs.getString("otype")==null){
					  hang = null;
				  }else{
					  if(!hch.containsKey(rs.getString("otype"))){
						  hang = null;
					  }else{
						  hang = (String)hch.get(rs.getString("otype"));
					  }
				  }
				  
				  if(rs.getString("subtradesegment")==null){
					  vitri = null;
				  }else{
					  if(!vtch.containsKey(rs.getString("subtradesegment"))){
						  vitri = null;
					  }else{
						  vitri = (String)vtch.get(rs.getString("subtradesegment"));
					  }
				  }
				  tmp="insert into khachhang" +
				  	  "(ten,trangthai,ngaytao,ngaysua,nguoitao,nguoisua,kbh_fk,hch_fk,lch_fk,npp_fk,vtch_fk,dienthoai,diachi,smartid) values('" + rs.getString("customername")+"','" + rs.getString("active")+"','"+ this.getdate() + "', '"+ this.getdate() + "','100000','100000','" + kbh.get(rs.getString("chanel"))+"',"+ hang +"," + loai + ",'"+ npp.get(rs.getString("sitecode"))+ "',"+ vitri+",'" +rs.getString("tel")+"','"+ rs.getString("address") + "','"+rs.getString("sitecode") +"_"+rs.getString("customerkey")+"')";
				  
				  if(best.update(tmp)) 
					  System.out.println(tmp);
			  }
		  }catch(Exception e){
			  System.out.println(e.toString());
		  }
	  }
	  
	  private void Donhang(){
		  Hashtable kh = this.getKhachhang();
		  Hashtable kbh = this.getChannel();
		  Hashtable ctkm = this.getCTkhuyenmai(); 
		  Hashtable npp = this.getNPP();
		  Hashtable ddkd = this.getDaidienkinhdoanh();
		  Hashtable sp = this.getSanpham();

		  
	  for(int n = 2011; n <= 2011; n++){
			 for (int i=11; i<=12; i++){
				 String table = "Door" + n;
				 
				 if(i<10){
					 table = table + "0" + i;
				 }else{
					 table = table + i;
				 }
//				 this.TaoCTKM(table);
// 				 this.TaoKH(table, kh, kbh);
//				 this.TaoDDKD(table, npp, ddkd, kbh);
//				 this.TaoDonhang(table, kh, kbh, npp, ddkd);
//			     this.CapnhatCTKhuyenmai(table, npp);				 
//				 this.CapnhatCTKM_TraKM();
//				 this.Donhang_Sanpham(table, kh, kbh, npp, ddkd, sp,  ctkm);
//				 this.update_chietkhau(table, kh, kbh, npp, ddkd, sp, ctkm);
				 
			 }
		 }

	  }
	  
	  private void TaoCTKM(String table){
		  String tmp = "select distinct program_id  from " + table + " where len(program_id)>1";
		  System.out.println(tmp);
		  ResultSet rs = smart.get(tmp);
		  try{
			  while(rs.next()){
				  tmp = "insert into CTKhuyenmai(scheme, nguoitao, nguoisua) values('"+ rs.getString("program_id").trim()+"','100000','100000')"; 
				 
				  if(best.update(tmp)){
					  System.out.println(tmp);
				  
					  tmp = "select IDENT_CURRENT('CTKhuyenmai')as ctKMId";
					  ResultSet ctKM = best.get(tmp);
					  ctKM.next();
					  String ctKMId = ctKM.getString("ctKMId");
				  
					  tmp = "insert into Trakhuyenmai(diengiai, nguoitao, nguoisua) values('"+ rs.getString("program_id").trim()+"','100000','100000')";
					  if(best.update(tmp))
						  System.out.println(tmp);
				  
					  tmp = "select IDENT_CURRENT('Trakhuyenmai')as traKMId";
					  ResultSet traKM = best.get(tmp);
					  traKM.next();
					  String traKMId = traKM.getString("traKMId");
				  
					  tmp = "insert into CTKM_TRAKM values('" + ctKMId + "','"+traKMId+"')";
					  if(best.update(tmp))
						  System.out.println(tmp);
				  }
			  }
		  }catch(Exception e){
			  System.out.println(e.toString());
		  }
		  
	  }
	  
	  private void TaoKH(String table, Hashtable kh, Hashtable kbh){
		  String kbhId = "";
		  String tmp = "select distinct sitecode, custkey, salespKey, offdate, Chanel from " + table + " order by sitecode,custkey, salespKey, offdate";
//		  String tmp = "select distinct sitecode, custkey, chanel from " + table + " order by sitecode,custkey";
		  System.out.println(tmp);
		  ResultSet rs = smart.get(tmp);
		  
		  try{
			  while(rs.next()){
				  String sitecode="null";
				  String custkey="null";
				  
//				  if (rs.getString("sitecode") != null){
					  
					  sitecode= rs.getString("sitecode").trim();
//					  System.out.println("Site code:" + sitecode);
//				  }
				  
//				  if(rs.getString("custkey") != null){
					  custkey = rs.getString("custkey").trim();
//					  System.out.println("Customer Key:" + custkey);
//				  }
				  if(!kh.containsKey(sitecode+"_"+ custkey)){
					  if(kbh.containsKey(rs.getString("chanel"))){
						  kbhId = (String)kbh.get(rs.getString("chanel"));
					  }else{
						  kbhId = null;
					  }
					  String smartid = rs.getString("sitecode").trim()+"_"+rs.getString("custkey").trim();
					  tmp = "insert into khachhang(ten, ngaytao, ngaysua, nguoitao, nguoisua, trangthai, kbh_fk, smartid) values('" + rs.getString("sitecode")+"','"+ this.getdate() +"','"+ this.getdate() +"','100000','100000','0',"+ kbhId + ",'"+ smartid +"')";
					  if(best.update(tmp)){
						  tmp = "select IDENT_CURRENT('khachhang')as Id";
						  ResultSet khachhang = best.get(tmp);
						  khachhang.next();
						  String khId = khachhang.getString("Id");
						  
						  kh.put(smartid, khId);
						  
						  System.out.println("Thanh cong:" + tmp);
					  }else{
						  System.out.println("Khong thanh cong:" + tmp);
					  }
				  }else{
					  System.out.println("Da co khach hang: " + sitecode+"_"+ custkey );
				  }
			  }
		  }catch(Exception e){
			  System.out.println(e.toString());
			  
		  }
	  }
	  
	  private void TaoDDKD(String table, Hashtable npp, Hashtable ddkd, Hashtable kbh){
		  String kbhId = "";
		  String tmp = "select distinct sitecode, salespkey, chanel from " + table + " order by sitecode,salespkey";
		  System.out.println(tmp);
		  ResultSet rs = smart.get(tmp);
		  
		  try{
			  while(rs.next()){
				  if(!ddkd.containsKey(rs.getString("sitecode").trim()+"_"+rs.getString("salespkey").trim())){

					  String smartid = rs.getString("sitecode").trim()+"_"+rs.getString("salespkey").trim();
					  tmp = "insert into daidienkinhdoanh (ten, npp_fk, trangthai, nguoitao, nguoisua, ngaytao, ngaysua, smartid) values('" + rs.getString("salespkey") + "', " + npp.get(rs.getString("sitecode"))+",'0', '100000','100000','"+ this.getdate() +"','"+ this.getdate() +"','"+rs.getString("sitecode")+"_" + rs.getString("salespkey")+"')";

					  if(best.update(tmp)){
						  tmp = "select IDENT_CURRENT('daidienkinhdoanh')as Id";
						  ResultSet salesmen = best.get(tmp);
						  salesmen.next();
						  String salesmenId = salesmen.getString("Id");
						  
						  ddkd.put(smartid, salesmenId);
						  
						  System.out.println("Thanh cong:" + tmp);
					  }else{
						  System.out.println("Khong thanh cong:" + tmp);
					  }
				  }else{
					  System.out.println("Dai dien kinh doanh da ton tai");
				  }
			  }
		  }catch(Exception e){
			  System.out.println(e.toString());
		  }
	  }

	  private void TaoDonhang(String table, Hashtable kh, Hashtable kbh, Hashtable npp, Hashtable ddkd){
		  String tmp = "select distinct sitecode, custkey, salespKey, offdate, Chanel from " + table + " order by sitecode,custkey, salespKey, offdate";
		  
//  	      String tmp = "select distinct sitecode, custkey, salespKey, offdate, Chanel from door201004 where offdate='2010/04/29' order by sitecode,custkey, salespKey, offdate";
		  String kbhId="";
		  String ddkdId="";
		  String khId="";
		  String nppId="";
		  
		  ResultSet rs = smart.get(tmp);
		  try{
			  while(rs.next()){

				  String ngaynhap = rs.getString("offdate").replace("/", "-");

				  if(rs.getString("Chanel")==null){
					  kbhId = null;
				  }else{
					  if(!kbh.containsKey(rs.getString("Chanel"))){
						  kbhId = null;
					  }else{
						  kbhId = (String)kbh.get(rs.getString("Chanel"));
					  }
				  }

				  if(rs.getString("salespKey")==null){
					  ddkdId = null;
				  }else{
					  if(!ddkd.containsKey(rs.getString("sitecode").trim()+ "_"+ rs.getString("salespKey").trim())){
						  ddkdId = null;
						  System.out.println("Dai dien kinh doanh o ton tai");
					  }else{
						  ddkdId = (String)ddkd.get(rs.getString("sitecode").trim()+ "_"+ rs.getString("salespKey").trim());
					  }
				  }

				  if(rs.getString("custKey")==null){
					  khId = null;
				  }else{
					  if(!kh.containsKey(rs.getString("sitecode").trim()+ "_"+rs.getString("custKey").trim())){
						  khId = null;
					  }else{
						  khId = (String)kh.get(rs.getString("sitecode").trim()+ "_"+rs.getString("custKey").trim());
					  }
				  }
				  
				  if(rs.getString("sitecode")==null){
					  nppId = null;
				  }else{
					  if(!npp.containsKey(rs.getString("sitecode").trim())){
						  nppId = null;
					  }else{
						  nppId = (String)npp.get(rs.getString("sitecode").trim());
					  }
				  }
				  String smartid = rs.getString("sitecode").trim()+ "_" + rs.getString("custKey").trim() + "_" + rs.getString("salespKey").trim()+ "_" + ngaynhap;
				  
				  tmp = "insert into donhang (ngaynhap, trangthai, ngaytao, ngaysua, nguoitao, nguoisua, ddkd_fk, khachhang_fk, npp_fk, kbh_fk, smartid) values('"+ngaynhap+"','1', '"+this.getdate()+"','"+this.getdate()+"','100000','100000'," + ddkdId + ","+ khId + "," + nppId + ","+ kbhId +",'" + smartid +"')";				  
				  
				  if(best.update(tmp)){
//					  System.out.println("Tao thanh cong:" + tmp);  
				  }else{
					  System.out.println("Khong thanh cong:" + tmp);
				  }
			  }
		  }catch(Exception e){}
	  }

	  private void Donhang_Sanpham(String table, Hashtable kh, Hashtable kbh, Hashtable npp, Hashtable ddkd, Hashtable sp,  Hashtable ctkm){
		  
		  String tmp = "select sitecode, WhseCode, custkey, salespKey, offdate, Chanel, SKUCode, Offqty, OffAmt,OffDsc, Offtaxamt, upper(program_id) as program_id from " + table + "  order by sitecode,custkey, salespKey, offdate";
//		  String tmp = "select sitecode, WhseCode, custkey, salespKey, offdate, Chanel, SKUCode, Offqty, OffAmt,OffDsc, Offtaxamt, upper(program_id) as program_id from door201004 where offdate='2010/04/29' and sitecode='K-D-BP-MT' order by sitecode,custkey, salespKey, offdate";
		  
		  String spId="";
		  String kbhId="";
		  String ddkdId="";
		  String khId="";
		  String nppId="";
		  String ngaynhap="";
		  String khoId="";
		  String smartId="";
		  String smartId_bk="";
		  String dhId="";
		  System.out.println(tmp);
		  ResultSet rs = smart.get(tmp);
		  float tonggiatri = 0;
		  float vat = 0;
		  float chietkhau = 0;
		  try{
			  while(rs.next()){

				  ngaynhap = rs.getString("offdate").replace("/", "-");
				  
				  if (rs.getString("WhseCode") == null){
					  khoId = null ;
				  }else{
					  if(rs.getString("WhseCode").equals("WH001")){
						  khoId="100000";
					  }else{
						  khoId="100001";
					  }
				  }
				  if(rs.getString("SKUCode")==null || rs.getString("SKUCode").length()==0){
					  spId = null;
				  }else{
					  if(!sp.containsKey(rs.getString("SKUCode").trim())){
						  spId = null;
						  System.out.println("Khong co san pham nay");
					  }else{
						  spId = (String)sp.get(rs.getString("SKUCode").trim());
					  }
				  }

				  if(rs.getString("Chanel")==null){
					  kbhId = null;
				  }else{
					  if(!kbh.containsKey(rs.getString("Chanel"))){
						  kbhId = null;
					  }else{
						  kbhId = (String)kbh.get(rs.getString("Chanel"));
					  }
				  }

				  if(rs.getString("salespKey")==null){
					  ddkdId = null;
				  }else{
					  if(!ddkd.containsKey(rs.getString("sitecode").trim()+ "_"+ rs.getString("salespKey").trim())){
						  ddkdId = null;
					  }else{
						  ddkdId = (String)ddkd.get(rs.getString("sitecode").trim()+ "_"+ rs.getString("salespKey").trim());
					  }
				  }

				  if(rs.getString("custKey")==null){
					  khId = null;
				  }else{
					  if(!kh.containsKey(rs.getString("sitecode").trim()+ "_"+rs.getString("custKey").trim())){
						  khId = null;
					  }else{
						  khId = (String)kh.get(rs.getString("sitecode").trim()+ "_"+rs.getString("custKey").trim());
					  }
				  }
				  
				  if(rs.getString("sitecode")==null){
					  nppId = null;
				  }else{
					  if(!npp.containsKey(rs.getString("sitecode").trim())){
						  nppId = null;
					  }else{
						  nppId = (String)npp.get(rs.getString("sitecode").trim());
					  }
				  }
				  

				  smartId = rs.getString("sitecode").trim()+ "_" + rs.getString("custKey").trim() + "_" + rs.getString("salespKey").trim()+ "_" + ngaynhap;
				  System.out.println(smartId);
				  
				  if(!smartId.equals(smartId_bk)){
					  if(dhId.length() > 0 ){
						  tmp = "update donhang set chietkhau=" + chietkhau + ", vat = "+ vat + ", tonggiatri = "+ tonggiatri + " where pk_seq='"+ dhId + "'" ;

						  if(best.update(tmp)){
//							  System.out.println("thanh cong:"+ tmp);
						  }else{
							  System.out.println("khong thanh cong:"+ tmp);
						  }
					  }
					  
					  tmp = "select pk_seq from donhang where smartid = '"+smartId+"'";
					  System.out.println(tmp);  
					  tonggiatri = 0;
					  vat = 0;
					  chietkhau = 0;
					  
					  ResultSet dhRS = best.get(tmp);
					  if (dhRS.next()){				  
						  dhId = dhRS.getString("pk_seq");						  
					  }else{
						  System.out.println(tmp);
						  System.out.println("Khong co don hang:" + tmp);
						  dhId="";
					  }
					  smartId_bk = smartId;
				  }
				  
				  if(dhId.length() > 0){
						  float soluong = Float.parseFloat(rs.getString("Offqty"));
						  float thanhtien = Float.parseFloat(rs.getString("OffAmt"));					  
						  float dongia;
						  if(soluong != 0){
							  dongia = thanhtien/soluong;
						  }else{
							  dongia = thanhtien;
						  }
						  
						  float ck = Float.parseFloat(rs.getString("OffDsc"));
						  
//						  System.out.println("Don gia:" + Math.round(dongia));
						  if(spId != null){
							  if(rs.getString("program_id").length()< 2){
				
								  tmp = "insert into donhang_sanpham values('" + spId + "','" + dhId + "','" + soluong + "'," + khoId + ","+ dongia+",'"+rs.getString("OffDsc")+"')";				  
								  
								  if(best.update(tmp)){
									  System.out.println(tmp);  
								  }else{
									  System.out.println("Khong tao duoc:"+ tmp);
								  }
								  
								  tonggiatri = tonggiatri + thanhtien;
								  chietkhau = chietkhau + ck;
								  vat = tonggiatri*10/100;
								  
							  }
							  else{
								  String ctkmId;
								  String trakmId;
								  
								  if(!ctkm.containsKey(rs.getString("program_id").trim())){
									  this.addCTKM(rs.getString("program_id"));
									  ctkm = this.getCTkhuyenmai();
								  }
								  tmp = (String)ctkm.get(rs.getString("program_id").trim());
								  System.out.println("Scheme:" +rs.getString("program_id")+" -- "+tmp );
								  ctkmId = tmp.split("_")[0];
								  trakmId = tmp.split("_")[1];

								  tmp = "insert into DONHANG_CTKM_TRAKM values('"+ dhId + "','"+ ctkmId + "','"+trakmId+"','1',"+ thanhtien +",'" + rs.getString("SKUCode").trim() + "','"+ soluong +"',0)";
								  if(best.update(tmp)){
								  System.out.println(tmp);  
								  }else{
									  System.out.println("Khong tao duoc: "+ tmp);
								  }

							  }
						  }  
						  else{
							  if(rs.getString("program_id").length()>= 2){
								  String ctkmId;
								  String trakmId;

								  if(!ctkm.containsKey(rs.getString("program_id").trim())){
									  this.addCTKM(rs.getString("program_id"));
									  ctkm = this.getCTkhuyenmai();
								  }

								  tmp = (String)ctkm.get(rs.getString("program_id").trim());
								  System.out.println("Scheme:" +rs.getString("program_id")+" -- "+tmp );
								  ctkmId = tmp.split("_")[0];
								  trakmId = tmp.split("_")[1];

								  
								  if(dhId.length()>0){
									  tmp = "insert into DONHANG_CTKM_TRAKM values('"+ dhId + "','"+ ctkmId + "','"+trakmId+"','1',"+ thanhtien +",null,'1',0)";
									  if(best.update(tmp)){
										  System.out.println(tmp);  
									  }else{
										  System.out.println("Khong tao duoc: "+ tmp);
									  }
								  }
							  }
						  }
				  	}
			  }
			  if(dhId.length() > 0){
				  tmp = "update donhang set chietkhau=" + chietkhau + ", vat = "+ vat + ", tonggiatri = "+ tonggiatri + " where pk_seq='"+ dhId + "'" ;

				  if(best.update(tmp)){
					  System.out.println(tmp);
				  }else{
					  System.out.println("Khong tao duoc: "+ tmp);
				  }
			  }

		  }catch(Exception e){
			  System.out.println(e.toString());
		  }
	  }	 
	  
	  private void addCTKM(String program_id){
		  try{
			  String tmp = "insert into CTKhuyenmai(scheme, nguoitao, nguoisua) values('"+ program_id.trim()+"','100000','100000')"; 
			 
			  if(best.update(tmp)){
				  System.out.println(tmp);
		  
				  tmp = "select IDENT_CURRENT('CTKhuyenmai')as ctKMId";
				  ResultSet ctKM = best.get(tmp);
				  ctKM.next();
				  String ctKMId = ctKM.getString("ctKMId");
		  
				  tmp = "insert into Trakhuyenmai(diengiai, nguoitao, nguoisua) values('"+ program_id.trim()+"','100000','100000')";
				  if(best.update(tmp))
					  System.out.println(tmp);
		  
				  tmp = "select IDENT_CURRENT('Trakhuyenmai')as traKMId";
				  ResultSet traKM = best.get(tmp);
				  traKM.next();
				  String traKMId = traKM.getString("traKMId");
		  
				  tmp = "insert into CTKM_TRAKM values('" + ctKMId + "','"+traKMId+"')";
				  if(best.update(tmp))
					  System.out.println(tmp);
			  }
		  }catch(Exception e){}
		  
	  }
	  
/*	  private void Donhang_Sanpham(String table, Hashtable kh, Hashtable kbh, Hashtable npp, Hashtable ddkd, Hashtable sp,  Hashtable ctkm){
		  
		  String tmp = "select sitecode, WhseCode, custkey, salespKey, offdate, Chanel, SKUCode, Offqty, OffAmt,OffDsc, Offtaxamt, upper(program_id) as program_id from " + table + "  order by sitecode,custkey, salespKey, offdate";
//		  String tmp = "select sitecode, WhseCode, custkey, salespKey, offdate, Chanel, SKUCode, Offqty, OffAmt,OffDsc, Offtaxamt, upper(program_id) as program_id from door201004 where offdate='2010/04/29' and sitecode='K-D-BP-MT' order by sitecode,custkey, salespKey, offdate";
		  
		  String spId="";
		  String kbhId="";
		  String ddkdId="";
		  String khId="";
		  String nppId="";
		  String ngaynhap="";
		  String khoId="";
		  String smartId="";
		  String smartId_bk="";
		  String dhId="";
		  System.out.println(tmp);
		  ResultSet rs = smart.get(tmp);
		  float tonggiatri = 0;
		  float vat = 0;
		  float chietkhau = 0;
		  try{
			  while(rs.next()){

				  ngaynhap = rs.getString("offdate").replace("/", "-");
				  
				  if (rs.getString("WhseCode") == null){
					  khoId = null ;
				  }else{
					  if(rs.getString("WhseCode").equals("WH001")){
						  khoId="100000";
					  }else{
						  khoId="100001";
					  }
				  }
				  if(rs.getString("SKUCode")==null || rs.getString("SKUCode").length()==0){
					  spId = null;
				  }else{
					  if(!sp.containsKey(rs.getString("SKUCode").trim())){
						  tmp = "insert into sanpham(ma,ten) values('"+ rs.getString("SKUCode") + "','"+ rs.getString("SKUCode") + "')";
						  best.update(tmp);

						  tmp = "select IDENT_CURRENT('sanpham')as Id";
						  ResultSet sanpham = best.get(tmp);
						  sanpham.next();
						  spId = sanpham.getString("Id");
						  sp = this.getSanpham();

					  }else{
						  spId = (String)sp.get(rs.getString("SKUCode").trim());
					  }
				  }

				  if(rs.getString("Chanel")==null){
					  kbhId = null;
				  }else{
					  if(!kbh.containsKey(rs.getString("Chanel"))){
						  kbhId = null;
					  }else{
						  kbhId = (String)kbh.get(rs.getString("Chanel"));
					  }
				  }

				  if(rs.getString("salespKey")==null){
					  ddkdId = null;
				  }else{
					  if(!ddkd.containsKey(rs.getString("sitecode").trim()+ "_"+ rs.getString("salespKey").trim())){
						  ddkdId = null;
					  }else{
						  ddkdId = (String)ddkd.get(rs.getString("sitecode").trim()+ "_"+ rs.getString("salespKey").trim());
					  }
				  }

				  if(rs.getString("custKey")==null){
					  khId = null;
				  }else{
					  if(!kh.containsKey(rs.getString("sitecode").trim()+ "_"+rs.getString("custKey").trim())){
						  khId = null;
					  }else{
						  khId = (String)kh.get(rs.getString("sitecode").trim()+ "_"+rs.getString("custKey").trim());
					  }
				  }
				  
				  if(rs.getString("sitecode")==null){
					  nppId = null;
				  }else{
					  if(!npp.containsKey(rs.getString("sitecode").trim())){
						  nppId = null;
					  }else{
						  nppId = (String)npp.get(rs.getString("sitecode").trim());
					  }
				  }
				  

				  smartId = rs.getString("sitecode").trim()+ "_" + rs.getString("custKey").trim() + "_" + rs.getString("salespKey").trim()+ "_" + ngaynhap;
				  //System.out.println(smartId);
				  
				  if(!smartId.equals(smartId_bk)){
					  if(dhId.length() > 0 ){
						  tmp = "update donhang set chietkhau=" + chietkhau + ", vat = "+ vat + ", tonggiatri = "+ tonggiatri + " where pk_seq='"+ dhId + "'" ;

						  if(best.update(tmp)){
//							  System.out.println("thanh cong:"+ tmp);
						  }else{
							  System.out.println("khong thanh cong:"+ tmp);
						  }
					  }
					  
					  tmp = "select pk_seq from donhang where smartid = '"+smartId+"'";
					 // System.out.println(tmp);  
					  tonggiatri = 0;
					  vat = 0;
					  chietkhau = 0;
					  
					  ResultSet dhRS = best.get(tmp);
					  if (dhRS.next()){				  
						  dhId = dhRS.getString("pk_seq");						  
					  }else{
//						  System.out.println(tmp);
						  System.out.println("Khong co don hang:" + tmp);
						  dhId="";
					  }
					  smartId_bk = smartId;
				  }
				  
				  if(dhId.length() > 0){
						  float soluong = Float.parseFloat(rs.getString("Offqty"));
						  float thanhtien = Float.parseFloat(rs.getString("OffAmt"));					  
						  float dongia;
						  if(soluong != 0){
							  dongia = thanhtien/soluong;
						  }else{
							  dongia = thanhtien;
						  }
						  
						  float ck = Float.parseFloat(rs.getString("OffDsc"));
						  
//						  System.out.println("Don gia:" + Math.round(dongia));

						  if(rs.getString("program_id").length()< 2){
							  tmp = "insert into donhang_sanpham values('" + spId + "','" + dhId + "','" + soluong + "'," + khoId + ","+ dongia+",'"+rs.getString("OffDsc")+"')";				  

							  if(best.update(tmp)){
//								  System.out.println(tmp);  
							  }else{
								  System.out.println("Khong tao duoc:"+ tmp);
							  }
//							  System.out.println("tong gia tri cu: " + tonggiatri);
//							  System.out.println("So luong: " + soluong);
//							  System.out.println("Gia mua: " + dongia);
//							  System.out.println("cong them: " + (soluong*dongia));
								  
							  tonggiatri = tonggiatri + thanhtien;
							  chietkhau = chietkhau + ck;
							  vat = tonggiatri*10/100;
								  
//							  System.out.println("tong gia tri: " + Math.round(tonggiatri));
//							  System.out.println("vat: " + vat);
//							  System.out.println("chiet khau: " + chietkhau);
							  
						  }else{
								  String ctkmId = "";
								  String trakmId = "";
								  
//								  System.out.println("Scheme:" +rs.getString("program_id")+" -- "+tmp );

								  if(ctkm.contains(rs.getString("program_id").trim())){
									  tmp = (String)ctkm.get(rs.getString("program_id").trim());
									  System.out.println("Scheme:" +rs.getString("program_id")+" -- "+tmp );
									  ctkmId = tmp.split("_")[0];
									  trakmId = tmp.split("_")[1];

									  tmp = "insert into  DONHANG_CTKM_TRAKM values('"+ dhId + "','"+ ctkmId + "','"+trakmId+"','1',"+ thanhtien +",'" + rs.getString("SKUCode").trim() + "','"+ soluong +"','"+ rs.getString("OffDsc") +"')";
									  if(!best.update(tmp)){
//										  System.out.println(tmp);  
//										  }else{
										  System.out.println("Khong tao duoc: "+ tmp);
									 }
									
								  }else{
									  tmp = "insert into CTKhuyenmai(scheme, nguoitao, nguoisua) values('"+ rs.getString("program_id").trim()+"','100000','100000')"; 
//									  System.out.println(tmp);
									  if(best.update(tmp)){
//										  System.out.println(tmp);
									  
										  tmp = "select IDENT_CURRENT('CTKhuyenmai')as ctKMId";
										  ResultSet ctKM = best.get(tmp);
										  ctKM.next();
										  ctkmId = ctKM.getString("ctKMId");
									  
										  tmp = "insert into Trakhuyenmai(diengiai, nguoitao, nguoisua) values('"+ rs.getString("program_id").trim()+"','100000','100000')";
										  if(!best.update(tmp))
											  System.out.println("Khong thanh cong: "+tmp);
									  
										  tmp = "select IDENT_CURRENT('Trakhuyenmai')as traKMId";
										  ResultSet traKM = best.get(tmp);
										  traKM.next();
										  trakmId = traKM.getString("traKMId");
									  
										  tmp = "insert into CTKM_TRAKM values('" + ctkmId + "','"+trakmId+"')";
										  if(!best.update(tmp))
											  System.out.println("1.Khong thanh cong: "+ tmp);
										  
										  ctkm=this.getCTkhuyenmai();

										  tmp = "insert into  DONHANG_CTKM_TRAKM values('"+ dhId + "','"+ ctkmId + "','"+trakmId+"','1',"+ thanhtien +",'" + rs.getString("SKUCode").trim() + "','"+ soluong +"','"+ rs.getString("OffDsc") +"')";
										  if(!best.update(tmp)){
//											  System.out.println(tmp);  
//											  }else{
											  System.out.println("Khong tao duoc: "+ tmp);
										 }
										  
									  }else{
										  System.out.println("2.Khong thanh cong: "+ rs.getString("program_id"));
									  }
								  }
								

							  }
						  }				  	
			  }
			  
			  if(dhId.length() > 0){
				  tmp = "update donhang set chietkhau=" + chietkhau + ", vat = "+ vat + ", tonggiatri = "+ tonggiatri + " where pk_seq='"+ dhId + "'" ;

				  if(best.update(tmp)){
//					  System.out.println(tmp);
				  }else{
					  System.out.println("Khong tao duoc: "+ tmp);
				  }
			  }

		  }catch(Exception e){
			  System.out.println(e.toString());
		  }
	  }*/

	  private void updateDonhang(){
		  String tmp = "select pk_seq as dhId from donhang where tonggiatri is null";
		  ResultSet rs = best.get(tmp);
		  if (rs == null) System.out.println(tmp);
		  try{
			  while(rs.next()){	  
				  String donhang_fk = rs.getString("dhId");
				  
				  tmp = "select donhang_fk as dhId, sum(chietkhau) as chietkhau, sum(soluong*giamua) as tonggiatri from donhang_sanpham where donhang_fk='" + donhang_fk +"' group by donhang_fk";
				  System.out.println(tmp);
				  
				  ResultSet ok = best.get(tmp);
				  if(ok != null){
					  if(ok.next()){
						  long tonggiatri = Math.round(ok.getFloat("tonggiatri"));
						  long vat = Math.round(tonggiatri*10/100);
						  long chietkhau = Math.round(ok.getFloat("chietkhau"));
				  
						  tmp = "update donhang set chietkhau=" + chietkhau + ", vat = "+ vat + ", tonggiatri = "+ tonggiatri + " where pk_seq='"+ donhang_fk + "'" ;
				  
						  System.out.println(tmp);
						  if(best.update(tmp)) 
							  System.out.println(tmp);
					  }
				  }
			  }
		  }catch(Exception e){
			  System.out.println(e.toString());
		  }
	  }
	  
	  private void Tonkhohientai(){
		  Hashtable sp = this.getSanpham();
		  Hashtable kbh = this.getChannel();
		  Hashtable npp = this.getNPP();
		  Hashtable kho = this.getKho();
		  
		  //String ngay = this.getdate(); 
		 // String ngay = this.getdate().replace("-", "/");
	      //String n= this.getdate().substring(0, 4)+ this.getdate().substring(5, 7);
		  
		  
		  String table = "DIN201112";
		  String ngay="2011/12/31";
		  String tmp = "select upper(WhseCode) as WhseCode, sitecode,skuCode,invDate, onhandqty,chanel, onhandCOGS from "+ table + " where invDate='" + ngay + "' and sitecode='K-H-TP-KD'";//kien phat
		 //String tmp = "select upper(WhseCode) as WhseCode, sitecode,skuCode,invDate, onhandqty,chanel, onhandCOGS from "+ table + " where invDate='" + ngay + "' and sitecode='K-H-TP-CLM'";//sitecode cua tri thuc

		  System.out.println(tmp);
		  ResultSet rs = smart.get(tmp);
		  try{
			  while(rs.next()){
				 String khoId;
				 String nppId;
				 String kbhId;
				 String spId;
										 
				 String whsecode = rs.getString("WhseCode").trim();
				 
				 if (whsecode == null){
					 khoId = null;
				 }else{
					  if(!kho.containsKey(whsecode)){
						  khoId = null;
					  }else{
						  khoId = (String)kho.get(whsecode);
					  }
					 
				 }
							 
				 if(rs.getString("sitecode")==null){
					  nppId = null;
				 }else{
					  if(!npp.containsKey(rs.getString("sitecode").trim())){
						  nppId = null;
					  }else{
						  nppId = (String)npp.get(rs.getString("sitecode").trim());
					  }
				 }

			     if(rs.getString("Chanel")==null){
					  kbhId = null;
				 }else{
					  if(!kbh.containsKey(rs.getString("Chanel"))){
						  kbhId = null;
					  }else{
						  kbhId = (String)kbh.get(rs.getString("Chanel"));
					  }
				 }
							 
			     if(rs.getString("SKUCode")==null){
					  spId = null;
				 }else{
					  if(!sp.containsKey(rs.getString("SKUCode").trim())){
						  tmp = "insert into sanpham(ma,ten) values('"+ rs.getString("SKUCode") + "','"+ rs.getString("SKUCode") + "')";
						  best.update(tmp);

						  tmp = "select IDENT_CURRENT('sanpham')as Id";
						  ResultSet sanpham = best.get(tmp);
						  sanpham.next();
						  spId = sanpham.getString("Id");
						  sp = this.getSanpham();

					  }else{
						  spId = (String)sp.get(rs.getString("SKUCode").trim());
					  }
				  }
			     
			      double giamua;
			      if (rs.getString("onhandCOGS") != null)
			    	  giamua = Float.parseFloat(rs.getString("onhandCOGS"))/Float.parseFloat(rs.getString("onhandqty"));
			      else
			    	  giamua =0;
				  tmp = "update nhapp_kho set soluong='" + rs.getString("onhandqty")+"', giamua = '" + giamua+ "', booked='0', available=soluong where npp_fk=" + nppId + " and kho_fk=" + khoId + " and sanpham_fk=" +spId+ "";

				  if(!best.update(tmp)){
					  tmp = "insert into nhapp_kho values('" + khoId + "','"+ nppId + "','" +spId+"',"+rs.getString("onhandqty")+",'0',"+rs.getString("onhandqty")+"," + giamua + ",'"+kbhId+"')"; 
					  if(best.update(tmp))
						  System.out.println("thanhcong:" + tmp);
					  else
								  
						  System.out.println("khong thanh cong:" + tmp);
				  }else{
					  System.out.println("thanh cong:" + tmp);
				  }
			 }
		 }catch(Exception e){
			 System.out.println(e.toString());
		 }
		  
	  }

	  private void Tonkhongay(){
		  Hashtable sp = this.getSanpham();
		  Hashtable kbh = this.getChannel();
		  Hashtable npp = this.getNPP();
		  Hashtable kho = this.getKho();
		  for(int n = 2011; n <= 2011; n++){
				 for (int i=1; i<=12; i++){
					 String table = "DIN" + n;
					 if(i<10){
						 table = table + "0" + i;
					 }else{
						 table = table + i;
					 }
					 String tmp = "select upper(WhseCode) as WhseCode, sitecode,skuCode,invDate, onhandqty,chanel from "+ table + " where Whsecode='km02'";
					 System.out.println(tmp);
					 ResultSet rs = smart.get(tmp);
					 try{
						 while(rs.next()){
							 String khoId="";
							 String nppId="";
							 String kbhId="";
							 String spId="";
							 String ngay = rs.getString("invDate").replace("/", "-");
							 
							 String whsecode = rs.getString("WhseCode").trim();
							 
							 if (whsecode == null){
								 khoId = null;
							 }else{
								  if(!kho.containsKey(whsecode)){
									  khoId = null;
								  }else{
									  khoId = (String)kho.get(whsecode);
								  }
								 
							 }
							 
							 String sitecode = rs.getString("sitecode").trim();
							 if(sitecode == null){
								  nppId = null;
							 }else{
								  if(!npp.containsKey(sitecode)){
									  nppId = null;
								  }else{
									  nppId = (String)npp.get(sitecode);
								  }
							 }
							  				  
							 if(rs.getString("Chanel")==null){
								  kbhId = null;
							 }else{
								  if(!kbh.containsKey(rs.getString("Chanel"))){
									  kbhId = null;
								  }else{
									  kbhId = (String)kbh.get(rs.getString("Chanel"));
								  }
							 }
							 
							 if(rs.getString("SKUCode")==null){
								  spId = null;
							 }else{
								  if(!sp.containsKey(rs.getString("SKUCode").trim())){
									  tmp = "insert into sanpham(ma,ten) values('"+ rs.getString("SKUCode") + "','"+ rs.getString("SKUCode") + "')";
									  best.update(tmp);

									  tmp = "select IDENT_CURRENT('sanpham')as Id";
									  ResultSet sanpham = best.get(tmp);
									  sanpham.next();
									  spId = sanpham.getString("Id");
									  sp = this.getSanpham();
								  }else{
									  spId = (String)sp.get(rs.getString("SKUCode").trim());
								  }
							 }
							  
							  tmp = "insert into tonkhongay values('" + khoId + "','"+ nppId + "','" +spId+"','"+ngay+"','"+rs.getString("onhandqty")+"','"+kbhId+"')";
//							  System.out.println("thanh cong:" + tmp);
							  if(best.update(tmp)){
//								  System.out.println("thanh cong:" + tmp);
							  }else{
								  System.out.println("khong thanh cong:" + whsecode + "," +sitecode+ ","+rs.getString("SKUCode")+","+rs.getString("invDate")+","+rs.getString("onhandqty")+"'" );
								  System.out.println(tmp);
								  
							  }
						 }
						 System.out.println("Da hoan thanh table: " + table);
					 }catch(Exception e){}
				 }
			 }
	  }
	  
	  private void CapnhatCTKhuyenmai(String table,  Hashtable npp){
		  Hashtable ctkm = this.getSchemeKM();
//		  Hashtable npp = this.getNPP();
		  
		  String tmp = "select * from ARPRODEF";
		  ResultSet rs = smart.get(tmp);
		  try{
/*			  while(rs.next()){
				  if(ctkm.containsKey(rs.getString("program_id").trim())){
					  tmp = "update ctkhuyenmai set diengiai='" + rs.getString("program_des") + "',tungay='"+ rs.getString("from").replace("/", "-") + "', denngay = '"+ rs.getString("to").replace("/", "-") +"' where pk_seq='" + ctkm.get(rs.getString("program_id").trim())+ "'";
					  if(best.update(tmp)){
						  System.out.println(tmp);					
					  }else{
//						  System.out.println("Khong thanh cong: " + tmp);
					  }
				  }
			  }*/
			  
			  tmp="select distinct sitecode, upper(program_id) as program_id from "+ table + " where len(program_id)>1 order by program_id";
			  System.out.println(tmp);
			  rs = smart.get(tmp);
			  while(rs.next()){
				  if(!ctkm.containsKey(rs.getString("program_id").trim())){
					  	tmp = "insert into CTKhuyenmai(scheme, nguoitao, nguoisua) values('"+ rs.getString("program_id").trim()+"','100000','100000')"; 
//					  	System.out.println(tmp);
					  	if(best.update(tmp)){
//					  		System.out.println(tmp);
				  
					  		tmp = "select IDENT_CURRENT('CTKhuyenmai')as ctKMId";
					  		ResultSet ctKM = best.get(tmp);
					  		ctKM.next();
					  		String ctkmId = ctKM.getString("ctKMId");
				  
					  		tmp = "insert into Trakhuyenmai(diengiai, nguoitao, nguoisua) values('"+ rs.getString("program_id").trim()+"','100000','100000')";
					  		if(!best.update(tmp))
					  			System.out.println("Khong thanh cong: " + tmp);
					  		
					  		tmp = "select IDENT_CURRENT('Trakhuyenmai')as traKMId";
					  		ResultSet traKM = best.get(tmp);
					  		traKM.next();
					  		String trakmId = traKM.getString("traKMId");
					  		
					  		tmp = "insert into CTKM_TRAKM values('" + ctkmId + "','"+trakmId+"')";
					  		if(!best.update(tmp))
					  			System.out.println("Khong thanh cong: " + tmp);
					  
					  		 ctkm = this.getSchemeKM();

					  
					  	}
				  }
				  
				  if(!npp.containsKey(rs.getString("sitecode").trim())){
						  Hashtable kv = this.getKhuvuc();
						  tmp = "select SiteName, Area, stop, ERPCode, Sitecode, CNVSiteCode, Address, Tel from distributor where sitecode='" + rs.getString("sitecode")+"'"; 
						  						  
						  ResultSet rs2 = smart.get(tmp);
						  rs2.next();
						  String erpcode = rs2.getString("erpcode");
						  String sitecode = rs2.getString("sitecode");
						  String kvId = (String)kv.get(rs2.getString("Area"));
						  String CNVSite = rs2.getString("CNVSiteCode");
						  String ten = rs2.getString("SiteName");
						  String diachi = rs2.getString("Address");
						  String dienthoai = rs2.getString("tel");
								  
						  if(erpcode == null) erpcode = sitecode;
						  else{ 
							  if(erpcode.equals("NULL") || erpcode.equals("null")||erpcode.length()==0){
									  erpcode = sitecode;
							  }
						  }
						  tmp = "insert into nhaphanphoi values('" + ten + "','"+ this.getdate() +"','"+ this.getdate() +"','100000','100000','"+ kvId + "','1','"+ erpcode + "','"+ erpcode + "','"+ sitecode + "','null','null','NULL','DWF1',null,null,null,'',null,'',null,null,'" + CNVSite + "')";
						  if(best.update(tmp)){
//							  System.out.println(tmp);
						  }else{
							  System.out.println("Khong thanh cong: "+tmp);
						  }		
						  npp = this.getNPP();
				 }

				
				tmp = "insert into CTKM_NPP values('" + ctkm.get(rs.getString("program_id").trim())+ "','" + npp.get(rs.getString("sitecode").trim()) + "')";
				if(best.update(tmp)){
//				 		System.out.println("CTKM_NPP:" + tmp);
				}else{
				  		System.out.println("Khong thanh cong: " + tmp);
				  		System.out.println("Khong thanh cong: " + rs.getString("program_id")+"','"+ rs.getString("sitecode") +"'");
				}
					  				  				  
			  }
			  
		  }catch(Exception e){}
		  
	  }
	  
	  
	  private void CapnhatCTKM_TraKM(){
		  String tmp = "select pk_seq, scheme from ctkhuyenmai where pk_seq not in (select distinct ctkhuyenmai_fk  from ctkm_trakm)";
		  ResultSet rs = best.get(tmp);
		  try{
			  while(rs.next()){
				  String ctkmId = rs.getString("pk_seq");
				  String scheme = rs.getString("scheme");
				  
				  tmp = "select pk_seq from trakhuyenmai where diengiai = '"+scheme+"'";
				  ResultSet rs2 = best.get(tmp);
				  
				  rs2.next();
				  String trakmId = rs2.getString("pk_seq");
				  
				  tmp = "insert into ctkm_trakm values('" + ctkmId + "', '"+ trakmId + "')";
				  if(!best.update(tmp))
					  System.out.println("Khong thanh cong :" + tmp);
			  }
			  
		  }catch(Exception e){
			  
		  }
	  }
	  
	  private void Phanbokhuyenmai(){
		  Hashtable ctkm = this.getSchemeKM();
		  Hashtable npp = this.getNPP();
		  String tmp = "select program_id, sitecode, allocateVal, approval from ARPROAllocate";
		  
		  ResultSet rs = smart.get(tmp);
		  try{
			  while(rs.next()){
				  if(ctkm.containsKey(rs.getString("program_id").trim())){
					  tmp = "insert into phanbokhuyenmai values('"+ ctkm.get(rs.getString("program_id").trim()) + "'," + npp.get(rs.getString("sitecode").trim())+",'"+ rs.getString("allocateVal")+"',null, '"+rs.getString("approval")+"')";
//					  System.out.println(tmp);
					  if(!best.update(tmp)){					  
						  tmp = "update phanbokhuyenmai set ngansach='"+ rs.getString("allocateVal")+"' where ctkm_fk ='"+ ctkm.get(rs.getString("program_id").trim()) + "' and npp_fk = '" + npp.get(rs.getString("sitecode").trim())+"'";
						  if(!best.update(tmp)){
							  System.out.println("Khong thanh cong: " + tmp);
							 
						  }
					  }
				  }else{
					  tmp = "insert into ctkhuyenmai ";
					  System.out.println("Khong co chuong trinh: " + rs.getString("program_id"));
				  }
			  }
		  }catch(Exception e){}
		  
	  }
	  
	  private void Nhaphang(){
		  Hashtable npp = this.getNPP();
		  
		  for(int n = 2010; n <= 2011; n++){
			 for (int i=1; i<=9; i++){
				 String table = "DATASHIP" + n;
				 if(i<10){
					 table = table + "0" + i;
				 }else{
					 table = table + i;
				 }
				 TaoNhaphang(table, npp);
			 }
		 }
	  }

	  private void TaoNhaphang(String table, Hashtable npp){
		  
		  String InvNo = "";
		  String InvNo_bk = ""; 
		  String ngaynhap;
		  String spId;
		  String kbhId;
		  String nppId;
		  String kbhMT = "100002";
		  String kbhGT = "100000";
		  String nhId = ""; 
		  float sotienavat = 0;
		  float vat = 0;
		  
		  
		  String tmp = "select Sitecode, InvNo, RedInvoiceNo, InvDate, Chanel, SKUCode, quantity, SalesUnit, Price, quantity*price, tax as thanhtien from "+ table +" order by sitecode, invdate, InvNo";
		  ResultSet rs = best.get(tmp);
		  try{
			  while(rs.next()){
				  ngaynhap = rs.getString("offdate").replace(".", "-");				  
				  		  
				  if(rs.getString("Chanel")=="MT"){
					  kbhId = kbhMT;
				  }else{
					  kbhId = kbhGT;
				  }
				  
				  
				  if(rs.getString("sitecode")==null){
					  nppId = null;
				  }else{
					  if(!npp.containsKey(rs.getString("sitecode").trim())){
						  nppId = null;
					  }else{
						  nppId = (String)npp.get(rs.getString("sitecode").trim());
					  }
				  }
				  
				  InvNo = rs.getString("InvNo");
				  if(!InvNo.equals(InvNo_bk)){
					  
					  if(nhId.length() >0){
						tmp = "update nhaphang set sotienavat='" + sotienavat + "', vat='" + vat + "' where pk_seq='" + nhId + "'";
						System.out.println(tmp);
						
/*						if(best.update(tmp))
							System.out.println("Thanh cong: " + tmp);
						else
							System.out.println("Khong thanh cong: " + tmp);*/
						
					  }

					  sotienavat = 0;
					  vat = 0;
					  
					  tmp = "insert into nhaphang (ngaynhan, nguoitao, nguoisua, trangthai, npp_fk, ncc_fk, kbh_fk, chungtu, ngaychungtu, hdtaichinh) values(" ;
					  tmp = tmp + " '" + ngaynhap + "','100000','100000','2','" + nppId + "','100000', '" + kbhId + "','" + InvNo + "','"+ rs.getString("RedInvoiceNo")+"')";
					
					  System.out.println(tmp);
					  
/*					  if(best.update(tmp)){
						  System.out.println("Thanh cong: " + tmp);
					  }else{
						  System.out.println("Khong thanh cong: " + tmp);
					  }*/
					
					  tmp = "select IDENT_CURRENT('nhaphang')as Id";
					  ResultSet nhaphang = best.get(tmp);
					  nhaphang.next();
					  nhId = nhaphang.getString("Id");
					  
				  }
				  sotienavat = sotienavat + Float.parseFloat(rs.getString("thanhtien")); 
				  vat = vat + Float.parseFloat(rs.getString("tax"));
				  
				  tmp="insert into nhaphang_sp (nhaphang_fk, sanpham_fk, soluong, donvi, giamua, tienavat, vat) values('"+ nhId + "','" + rs.getString("SKUCode") + "','" + rs.getString("quantity") + "','" + rs.getString("salesunit") + "','" + rs.getString("price")+ "','" + rs.getString("thanhtien") +"','"+ rs.getString("tax") +"')" ;
				  System.out.println(tmp);
				  
/*				  if(best.update("tmp"))
					  System.out.println("Thanh cong:" + tmp);
				  else
					  System.out.println("Khong thanh cong:" + tmp);*/
			  }
			  
			  
		  }catch(Exception e){
			  System.out.println(e.toString());
		  }
	  }
	  
private void update_chietkhau(String table, Hashtable kh, Hashtable kbh, Hashtable npp, Hashtable ddkd, Hashtable sp,  Hashtable ctkm){
		  
		  String tmp = "select sitecode, WhseCode, custkey, salespKey, offdate, Chanel, SKUCode, Offtaxamt,offdsc, upper(program_id) as program_id from " + table + " where len(program_id)> 1 and offdsc<>0  order by sitecode,custkey, salespKey, offdate  ";
//		  String tmp = "select sitecode, WhseCode, custkey, salespKey, offdate, Chanel, SKUCode, Offqty, OffAmt,OffDsc, Offtaxamt, upper(program_id) as program_id from door201004 where offdate='2010/04/29' and sitecode='K-D-BP-MT' order by sitecode,custkey, salespKey, offdate";
		  
		  String spId="";
		  String kbhId="";
		  String ddkdId="";
		  String khId="";
		  String nppId="";
		  String ngaynhap="";
		  String khoId="";
		  String smartId="";
		  String smartId_bk="";
		  String dhId="";
		  System.out.println(tmp);
		  ResultSet rs = smart.get(tmp);
		  float tonggiatri = 0;
		  float vat = 0;
		  float chietkhau = 0;
		  try{
			  while(rs.next()){

				  ngaynhap = rs.getString("offdate").replace("/", "-");
				  
				 
				  if(rs.getString("SKUCode")==null || rs.getString("SKUCode").length()==0){
					  spId = null;
				  }else{
					  if(!sp.containsKey(rs.getString("SKUCode").trim())){
						  spId = null;
						  System.out.println("Khong co san pham nay");
					  }else{
						  spId = (String)sp.get(rs.getString("SKUCode").trim());
					  }
				  }
				  smartId = rs.getString("sitecode").trim()+ "_" + rs.getString("custKey").trim() + "_" + rs.getString("salespKey").trim()+ "_" + ngaynhap;
				  System.out.println(smartId);
				  
				  if(!smartId.equals(smartId_bk)){

					  tmp = "select pk_seq from donhang where smartid = '"+smartId+"'";
					  System.out.println(tmp);  
					  tonggiatri = 0;
					  vat = 0;
					  chietkhau = 0;
					  
					  ResultSet dhRS = best.get(tmp);
					  if (dhRS.next()){				  
						  dhId = dhRS.getString("pk_seq");						  
					  }else{
						  System.out.println(tmp);
						  System.out.println("Khong co don hang:" + tmp);
						  dhId="";
					  }
					  smartId_bk = smartId;
				  }
				  tmp = (String)ctkm.get(rs.getString("program_id"));
				  System.out.println("Scheme:" +rs.getString("program_id")+" -- "+tmp );
				  String ctkmId = tmp.split("_")[0];
				  String trakmId = tmp.split("_")[1];
				  
				  if(dhId.length() > 0){//co don hang
						 
						  float ck = Float.parseFloat(rs.getString("OffDsc"));
						  
//						  System.out.println("Don gia:" + Math.round(dongia));
						  if(spId != null){ // co san pham
							 
								 // tmp = "insert into DONHANG_CTKM_TRAKM values('"+ dhId + "','"+ ctkmId + "','"+trakmId+"','1',"+ thanhtien +",'" + rs.getString("SKUCode").trim() + "','"+ soluong +"')";
								  String update_chietkhau="update DONHANG_CTKM_TRAKM set CHIETKHAU="+ck+" where donhangid="+ dhId +" and ctkmid="+ctkmId+" and spma='"+ rs.getString("SKUCode").trim()+"'"; 
								  if(best.update(update_chietkhau)){
								  System.out.println(update_chietkhau);  
								  }else{
									  System.out.println("Khong tao duoc: "+ update_chietkhau);
								  }
						  }  
						  else{ //khong co san pham
							  
							  String update_chietkhau="update DONHANG_CTKM_TRAKM set CHIETKHAU="+ck+" where donhangid="+ dhId +" and ctkmid="+ctkmId ; 
									 // tmp = "insert into DONHANG_CTKM_TRAKM values('"+ dhId + "','"+ ctkmId + "','"+trakmId+"','1',"+ thanhtien +",null,'1')";
									  if(best.update(update_chietkhau)){
										  System.out.println(update_chietkhau);  
									  }else{
										  System.out.println("Khong tao duoc: "+ update_chietkhau);
									  }
								  
						  }
						}
				  	}
			  
			  

		  }catch(Exception e){
			  System.out.println(e.toString());
		  }
	  }
	  
	  
	  private Hashtable<String, String> getNhanhang(){
		  Hashtable nh = new Hashtable<String, String>();
		  
		  ResultSet rs = best.get("select pk_seq as nhId, smartid from nhanhang");
		  
		  try{
			  while(rs.next()){
				  nh.put(rs.getString("smartid"), rs.getString("nhId"));
			  }
		  }catch(Exception e){}
		  return nh;
		  
	  }
	  
	  private Hashtable<String, String> getKho(){
		  Hashtable kho = new Hashtable<String, String>();
		  
		  ResultSet rs = best.get("select pk_seq as khoId, ten from kho");
		  
		  try{
			  while(rs.next()){
				  kho.put(rs.getString("ten"), rs.getString("khoId"));
			  }
		  }catch(Exception e){}
		  return kho;
		  
	  }

	  private Hashtable<String, String> getChungloai(){
		  Hashtable cl = new Hashtable<String, String>();
		  
		  ResultSet rs = best.get("select pk_seq as clId, smartid from chungloai");
		  
		  try{
			  while(rs.next()){
				  cl.put(rs.getString("smartid"), rs.getString("clId"));
			  }
		  }catch(Exception e){}
		  return cl;
		  
	  }
	  
	  private Hashtable<String, String> getDonvikinhdoanh(){
		  Hashtable dvkd = new Hashtable<String, String>();
		  
		  ResultSet rs = best.get("select pk_seq as dvkdId, smartid from donvikinhdoanh");
		  
		  try{
			  while(rs.next()){
				  dvkd.put(rs.getString("smartid"), rs.getString("dvkdId"));
			  }
		  }catch(Exception e){}
		  return dvkd;
		  
	  }

	  private Hashtable<String, String> getKhuvuc(){
		  Hashtable kv = new Hashtable<String, String>();
		  
		  ResultSet rs = best.get("select pk_seq as kvId, ten from khuvuc");
		  
		  try{
			  while(rs.next()){
				  kv.put(rs.getString("ten"), rs.getString("kvId"));
			  }
		  }catch(Exception e){}
		  return kv;
		  
	  }

	  private Hashtable<String, String> getSanpham(){
		  Hashtable sp = new Hashtable<String, String>();
		  ResultSet rs = best.get("select pk_seq, ma from sanpham");
		  try{
			  while(rs.next()){
				  sp.put(rs.getString("ma").trim(), rs.getString("pk_seq").trim());
			  }
			  rs.close();
		  }catch(Exception e){}
		  return sp;

	  }
	  
	  private Hashtable<String, String> getNPP(){
		  Hashtable npp = new Hashtable<String, String>();
		  ResultSet rs = best.get("select pk_seq, sitecode from nhaphanphoi");
		  try{
			  while(rs.next()){
				  npp.put(rs.getString("sitecode").trim(), rs.getString("pk_seq").trim());
			  }
			  rs.close();
		  }catch(Exception e){}
		  return npp;

	  }

	  private Hashtable<String, String> getDaidienkinhdoanh(){
		  Hashtable ddkd = new Hashtable<String, String>();
		  ResultSet rs = best.get("select pk_seq, smartid from daidienkinhdoanh");
		  try{
			  while(rs.next()){
				  ddkd.put(rs.getString("smartid"), rs.getString("pk_seq"));
			  }
		  }catch(Exception e){}
		  return ddkd;
	  }

	  private Hashtable<String, String> getGSBH(){
		  Hashtable gsbh = new Hashtable<String, String>();
		  
		  ResultSet rs = best.get("select pk_seq, smartid from giamsatbanhang");
		  try{
			  while(rs.next()){
				  gsbh.put(rs.getString("smartid"), rs.getString("pk_seq"));
			  }
		  }catch(Exception e){}
		  return gsbh;
	  }

	  private Hashtable<String, String> getLoaicuahang(){
		  Hashtable lch = new Hashtable<String, String>();
		  
		  ResultSet rs = best.get("select pk_seq, smartid from loaicuahang");
		  try{
			  while(rs.next()){
				  lch.put(rs.getString("smartid"), rs.getString("pk_seq"));
			  }
		  }catch(Exception e){}
		  return lch;
	  }

	  private Hashtable<String, String> getHangcuahang(){
		  Hashtable hch = new Hashtable<String, String>();
		  
		  ResultSet rs = best.get("select pk_seq, smartid from hangcuahang");
		  try{
			  while(rs.next()){
				  hch.put(rs.getString("smartid"), rs.getString("pk_seq"));
			  }
		  }catch(Exception e){}
		  return hch;
	  }

	  private Hashtable<String, String> getVitricuahang(){
		  Hashtable vtch = new Hashtable<String, String>();
		  
		  ResultSet rs = best.get("select pk_seq, smartid from vitricuahang");
		  try{
			  while(rs.next()){
				 vtch.put(rs.getString("smartid"), rs.getString("pk_seq"));
			  }
		  }catch(Exception e){}
		  return vtch;
	  }

	  private Hashtable<String, String> getChannel(){
		  Hashtable kbh = new Hashtable<String, String>();
		  
		  ResultSet rs = best.get("select pk_seq, smartid from kenhbanhang");
		  try{
			  while(rs.next()){
				 kbh.put(rs.getString("smartid"), rs.getString("pk_seq"));
			  }
		  }catch(Exception e){}
		  return kbh;
	  }

	  private Hashtable<String, String> getSchemeKM(){
		  Hashtable ctkm = new Hashtable<String, String>();
		  
		  ResultSet rs = best.get("select pk_seq, scheme from ctkhuyenmai");
		  try{
			  while(rs.next()){
				 ctkm.put(rs.getString("scheme").trim(), rs.getString("pk_seq"));
			  }
		  }catch(Exception e){}
		  return ctkm;
	  }

	  private Hashtable<String, String> getKenhbanhang(){
		  Hashtable kbh = new Hashtable<String, String>();
		  kbh.put("010", "100000");
		  kbh.put("020", "100001");
		  kbh.put("030", "100002");
		  kbh.put("040", "100003");
		  kbh.put("050", "100004");
		  kbh.put("060", "100005");
		  kbh.put("070", "100006");
		  return kbh;
	  }
	  
	  private Hashtable<String, String> getKhachhang(){
		  Hashtable kh = new Hashtable<String, String>();
		  
		  ResultSet rs = best.get("select pk_seq, smartid from khachhang");
		  try{
			  while(rs.next()){
				 kh.put(rs.getString("smartid"), rs.getString("pk_seq"));
			  }
		  }catch(Exception e){}
		  return kh;
	  }
	  
	  private Hashtable<String, String> getDonhang(){
		  Hashtable dh = new Hashtable<String, String>();
		  
		  ResultSet rs = best.get("select pk_seq, smartid from donhang");
		  try{
			  while(rs.next()){
				 dh.put(rs.getString("smartid").trim(), rs.getString("pk_seq"));
			  }
		  }catch(Exception e){}
		  return dh;
	  }

	  private Hashtable<String, String> getCTkhuyenmai(){
		  Hashtable ctkm = new Hashtable<String, String>();
		  
		  ResultSet rs = best.get("select upper(a.scheme) as scheme, a.pk_seq as ctkmId, c.pk_seq as trakmId from ctkhuyenmai a inner join ctkm_trakm b on a.pk_seq=b.ctkhuyenmai_fk inner join trakhuyenmai c on c.pk_seq=b.trakhuyenmai_fk");
		  try{
			  while(rs.next()){
				 ctkm.put(rs.getString("scheme").trim(), rs.getString("ctkmId").trim()+"_"+rs.getString("trakmId").trim());
			  }
		  }catch(Exception e){}
		  return ctkm;
	  }

	  
	  private void importDIN(dbutils db2){
			try{
				FileInputStream fstream = new FileInputStream("c:\\DIN.txt");
				
				DataInputStream in = new DataInputStream(fstream);

		        BufferedReader br = new BufferedReader(new InputStreamReader(in));
	        	        
		        String strLine;
		        String[] temp = new String[24];
		        int i = 0;
		        while ((strLine = br.readLine()) != null)   {
		        	System.out.println("" + i);
		        	temp[0] = strLine.split("\t")[0];
		        	temp[1] = strLine.split("\t")[1];
		        	temp[2] = strLine.split("\t")[2];
		        	temp[3] = strLine.split("\t")[3];
		        	temp[4] = strLine.split("\t")[4];
		        	temp[5] = strLine.split("\t")[5];
		        	temp[6] = strLine.split("\t")[6];
		        	temp[7] = strLine.split("\t")[7];
		        	temp[8] = strLine.split("\t")[8];
		        	temp[9] = strLine.split("\t")[9];
		        	temp[10] = strLine.split("\t")[10];
		        	temp[11] = strLine.split("\t")[11];
		        	temp[12] = strLine.split("\t")[12];
		        	temp[13] = strLine.split("\t")[13];
		        	temp[14] = strLine.split("\t")[14];
		        	temp[15] = strLine.split("\t")[15];
		        	temp[16] = strLine.split("\t")[16];
		        	temp[17] = strLine.split("\t")[17];
		        	temp[18] = strLine.split("\t")[18];
		        	temp[19] = strLine.split("\t")[19];
		        	temp[20] = strLine.split("\t")[20];
		        	temp[21] = strLine.split("\t")[21];
		        	temp[22] = strLine.split("\t")[22];
		        	temp[23] = strLine.split("\t")[23];
		        	
		        	String query = "insert into DIN201101 values('" + temp[0] + "', '" + temp[1] + "', '" + temp[2] + "', '" + temp[3] + "', '" + temp[4] + "', '" + temp[5] + "', '" + temp[6] + "', '" + temp[7] + "', '" + temp[8] + "', '" + temp[9] + "', '" + temp[10] + "', '" + temp[11] + "', '" + temp[12] + "', '" + temp[13] + "', '" + temp[14] + "', '" + temp[15] + "', '" + temp[16] + "', '" + temp[17] + "', '" + temp[18] + "', '" + temp[19] + "', '" + temp[20] + "', '" + temp[21] + "', '" + temp[22] + "', '" + temp[23] + "')";
		        	db2.update(query);

		          }
		          in.close();	
		   	}catch(Exception e){
		   		System.err.println("Error: " + e.getMessage());
		   	}
	   	}

		private String getdate() {
	        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	        Date date = new Date();
	        return dateFormat.format(date);	
		}

		private String convertDate(String d){
			String tmp = d;
//			System.out.println(d);
			String day = tmp.substring(0, 2);
			String month = tmp.substring(3, 5);
			String year = tmp.substring(6, 10);
			tmp = year + "-" + month + "-" + day;
			return tmp;
		}
		
	  public static void main ( String args [  ]  )   {
		    new DatafromSMART () ;
		  }

}
