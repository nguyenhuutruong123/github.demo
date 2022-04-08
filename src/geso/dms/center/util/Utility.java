package geso.dms.center.util;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import geso.dms.center.beans.routesumaryreport.IRouteSumaryReport;
import geso.dms.center.beans.tieuchithuong.ITieuchithuongTBList;
import geso.dms.center.db.sql.Idbutils;
import geso.dms.distributor.beans.report.Ireport;
import geso.dms.distributor.db.sql.dbutils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;

import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;

import com.nhat.replacement.TaskReplacement;

public class Utility implements Serializable
{

	public static final int THEM = 0;
	public static final int XOA = 1;
	public static final int SUA = 2;
	public static final int XEM = 3;
	public static final int CHOT = 4;
	public static final int HUYCHOT = 5;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String nppId;
	String nppTen;
	String sitecode;
	String tructhuoc_fk;
	public String getTructhuoc_fk() {
		return tructhuoc_fk;
	}
	public void setTructhuoc_fk(String tructhuoc_fk) {
		this.tructhuoc_fk = tructhuoc_fk;
	}

	DecimalFormat format_1 = new DecimalFormat( "##########.#" );

	String loainpp;

	public String getLoainpp() {
		return loainpp;
	}
	public void setLoainpp(String loainpp) {
		this.loainpp = loainpp;
	}
	public String getIdNhapp(String userid){
		String sql = "select npp.pk_seq,npp.sitecode,npp.ten,isnull(npp.loainpp,0) as loainpp,isnull(npp.tructhuoc_fk,0) as tructhuoc_fk from nhanvien nv inner join nhaphanphoi npp on nv.convsitecode=sitecode where nv.pk_seq='"+userid+"' and nv.trangthai='1'";
		//System.out.println("999999999999999999___"+sql);
		dbutils db=new dbutils();
		ResultSet rs= db.get(sql);
		try{
			if(rs.next()){
				this.nppId=rs.getString("pk_seq");
				this.nppTen=rs.getString("ten");
				this.sitecode=rs.getString("sitecode");
				this.tructhuoc_fk=rs.getString("tructhuoc_fk");
				this.loainpp=rs.getString("loainpp");
				rs.close();
			}
		}catch(Exception er){

		}
		db.shutDown();
		return this.nppId;
	}
	public String getTenNhaPP(){
		return this.nppTen;
	}
	public String getSitecode(){
		return this.sitecode;
	}
	public String quyen_kho(String userId)
	{	
		String sql ="( select KHO_fK from NHANVIEN_KHO where nhanvien_fk ='"+ userId +"')";
		return sql;
	}
	public String quyen_nhomskus(String userId)
	{	
		String sql ="( select NHOMSKUS_FK from NHANVIEN_NHOMSKUS where nhanvien_fk ='"+ userId +"')";
		return sql;
	}
	public String quyen_khoTT(String userId)
	{	
		//String sql ="( select KHOTT_fK from NHANVIEN_KHOTT where nhanvien_fk ='"+ userId +"')";
		String sql ="( select PK_SEQ from ERP_KHOTT where trangthai ='1')";
		return sql;
	}
	public static String getNgayHienTai()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	public String NgayKhoaSo_Add(String nppId,int add)
	{
		String ngay = "";
		dbutils db = new dbutils();
		String query="select   convert(varchar(10),DATEADD(DAY,"+add+",MAX(ngayks)),20) as ngayKs  from KHOASONGAY where NPP_FK='"+nppId+"' ";
		ResultSet rs = db.get(query);
		try
		{
			if(rs != null)
			{
				rs.next();
				ngay = rs.getString("ngay");	
			}
			db.shutDown();
		}
		catch(Exception e){ db.shutDown(); }
		return ngay;
	}
	public boolean coQuyen(int[] quyen, int tacvu) 
	{
		return quyen != null && quyen.length > tacvu && quyen[tacvu] == 1;
	}
	public String Update_NPP_Kho_Sp(String ngaychungtu ,String nghiepvu ,Idbutils db, String khott_fk, String spId,String npp_fk,String kbh_fk,
			double soluong ,double booked,double available , double dongia) {
		// TODO Phương thức đưa số lượng nhập vào kho,bảng ERP_KHOTT_SANPHAM
		try{
			int flag=0;
			String querylog="";
			String query="  select sanpham_fk ,available,soluong , sp.ma+ ' '+ sp.ten as ten , ISNULL(KHO.GIAMUA,0) AS GIATON   " +
			"  from NHAPP_KHO kho " +
			"  inner join sanpham sp  on kho.sanpham_fk=sp.pk_seq  where KBH_FK ="+kbh_fk+" AND NPP_FK="+npp_fk+" AND  " +
			"  KHO_FK="+khott_fk+" and sanpham_fk= "+spId;
			//System.out.println("[UTILITY KHO : QUERY LAY SAN PHAM KHO TONG]" +query);
			double available_ton=0;
			double giaton=0;
			double soluongton=0;

			ResultSet rsCheck = db.get(query);
			if(rsCheck.next()){
				soluongton=rsCheck.getDouble("soluong");
				available_ton=rsCheck.getDouble("available");
				giaton=rsCheck.getDouble("GIATON");

				if(available < 0 && available_ton < (-1)*available ){
					return "Số lượng tồn hiện tại trong kho của sản phẩm : "+rsCheck.getString("ten") + "  ["+available_ton+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
				}

				if(soluong < 0 && soluongton <(-1)*soluong ){
					return "Số lượng tồn  trong kho của sản phẩm : "+rsCheck.getString("ten") + "  ["+soluongton+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
				}


				querylog="insert into log_kho (nghiepvu,sanpham_fk,kbh_fk,kho_fk,npp_fk,soluong,book,avai,ngaychungtu,soluongNV,bookNv,avaiNV)"+
				"select N'"+nghiepvu+"',sanpham_fk,kbh_fk,kho_fk,npp_fk,SOLUONG,BOOKED,AVAILABLE,'"+ngaychungtu+"',round(" + soluong + ",1),round("+booked+",1),round(" + available + ",1) from nhapp_kho where  npp_fk='"+npp_fk+"' and kbh_fk='"+kbh_fk+"' and kho_fk='"+khott_fk+"' and sanpham_fk='"+spId+"'  ";

				query = " Update NHAPP_KHO set booked=round(isnull(booked,0),1)+ round("+booked+",1) , soluong =round(ISNULL(soluong,0),1) + round(" + soluong + ",1), " +
				" AVAILABLE = round(ISNULL(AVAILABLE,0),1) + round(" + available + ",1), GIAMUA="+(giaton >0?giaton:dongia)+"  "+
				"  where KBH_FK="+kbh_fk+" AND NPP_FK="+npp_fk+" AND  " +
				"  KHO_FK="+khott_fk+" and sanpham_fk= "+spId;



			}else{
				query=  " INSERT INTO NHAPP_KHO ( KHO_FK,SANPHAM_FK,NPP_FK,KBH_FK,GIAMUA,SOLUONG,BOOKED,AVAILABLE ) VALUES  " +
				" ("+khott_fk+","+ spId+","+npp_fk+","+kbh_fk+","+dongia+",round("+soluong+",1),round("+booked+",1),round("+available+",1))";
				flag=1;
				querylog="insert into log_kho (nghiepvu,sanpham_fk,kbh_fk,kho_fk,npp_fk,soluong,book,avai,ngaychungtu,soluongNV,bookNv,avaiNV)"+
				"select N'"+nghiepvu+"',sanpham_fk,kbh_fk,kho_fk,npp_fk,SOLUONG,BOOKED,AVAILABLE,'"+ngaychungtu+"',round(" + soluong + ",1),round("+booked+",1),round(" + available + ",1) from nhapp_kho where npp_fk='"+npp_fk+"' and kbh_fk='"+kbh_fk+"' and kho_fk='"+khott_fk+"' and sanpham_fk='"+spId+"'  ";

				if(available < 0 && available_ton < (-1)*available ){
					return "Số lượng tồn hiện tại trong kho của sản phẩm : "+rsCheck.getString("ten") + "  ["+available_ton+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
				}

				if(soluong < 0 && soluongton <(-1)*soluong ){
					return "Số lượng tồn  trong kho của sản phẩm : "+rsCheck.getString("ten") + "  ["+soluongton+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
				}

			}
			rsCheck.close();
			if(flag==0)
			{
				int resultInt = db.updateReturnInt(querylog);
				if(resultInt != 1)
				{
					return  " Không thể cập nhật log_kho " + querylog;

				}
			}

			int resultInt = db.updateReturnInt(query);
			if(resultInt != 1)
			{
				return  " Không thể cập nhật NHAPP_KHO " + query;

			}
			if(flag==1)
			{
				resultInt = db.updateReturnInt(querylog);
				if(resultInt != 1)
				{
					return  " Không thể cập nhật log_kho " + querylog;

				}
			}


		}catch(Exception er){
			er.printStackTrace();
			return  "không thể thực hiện cập nhật kho  Util.Nhap_Kho_Sp : "+er.getMessage();
		}
		return "";
	}
	public String getUrl(String servlet,String parametes)
	{
		String query=
			"	select   B.PK_SEQ as DanhMuc_FK,a.PK_SEQ as pk_Seq  , c.ten + ' > '+  b.ten as TENDANHMUC, a.ten , a.servlet,  "+  
			"			a.parameters, c.sott as stt1, b.sott as stt2, a.sott           "+
			"	from ungdung a inner join ungdung b on a.ungdung_fk = b.pk_seq        "+       
			"	inner join ungdung c on b.ungdung_fk = c.pk_seq               "+
			"	where a.level = '3' and b.level = '2'  and a.TrangThai=1 and a.servlet='"+servlet+"' and a.parameters='"+parametes+"' "+           
			"	union all            "+
			"	select c.PK_SEQ as DanhMuc_FK ,a.PK_SEQ ,  c.ten   as TENDANHMUC, a.ten, a.servlet, a.parameters, c.sott as stt1, a.sott as stt2, a.sott "+ 
			"	from ungdung a  inner join ungdung c on a.ungdung_fk = c.pk_seq               "+
			"	where a.level = '3' and c.level = '1'    and a.TrangThai=1  and a.servlet='"+servlet+"' and a.parameters='"+parametes+"'    "+         
			"	order by stt1 asc, stt2 asc, sott asc  " ;
		//System.out.println("query getUrl: " + query);
		dbutils db = new dbutils();
		String url="";
		ResultSet rs =db.get(query);
		try
		{
			while(rs.next())
			{
				url=rs.getString("TENDANHMUC")+ " > " + rs.getString("ten");
			}
			if(rs!=null)rs.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			db.shutDown();
		}
		return url;
	}
	
	public String getChuyenNguUrl(String servlet,String parametes,String nnId)
	{
		String query=
			"\n	select   B.PK_SEQ as DanhMuc_FK,a.PK_SEQ as pk_Seq  , c.ten ten1,  b.ten ten2 , a.ten , a.servlet,  "+  
			"\n			a.parameters, c.sott as stt1, b.sott as stt2, a.sott           "+
			"\n	from ungdung a inner join ungdung b on a.ungdung_fk = b.pk_seq        "+       
			"\n	inner join ungdung c on b.ungdung_fk = c.pk_seq               "+
			"\n	where a.level = '3' and b.level = '2'  and a.TrangThai=1 and a.servlet='"+servlet+"' and a.parameters='"+parametes+"' "+           
			"\n	union all            "+
			"\n	select c.PK_SEQ as DanhMuc_FK ,a.PK_SEQ ,  c.ten   as ten1 , '' ten2 , a.ten, a.servlet, a.parameters, c.sott as stt1, a.sott as stt2, a.sott "+ 
			"\n	from ungdung a  inner join ungdung c on a.ungdung_fk = c.pk_seq               "+
			"\n	where a.level = '3' and c.level = '1'    and a.TrangThai=1  and a.servlet='"+servlet+"' and a.parameters='"+parametes+"'    "+         
			"\n	order by stt1 asc, stt2 asc, sott asc  " ;
		//System.out.prrintln("query getUrl: " + query);
		dbutils db = new dbutils();
		String url="";
		ResultSet rs =db.get(query);
		try
		{
			while(rs.next())
			{
				String ten1 = rs.getString("ten1");
				String ten2 = rs.getString("ten2");
				String ten3 = rs.getString("ten");
				if(ten2.trim().length() >0)
					url = ChuyenNgu.get(ten1,nnId) + " > " + ChuyenNgu.get(ten2,nnId)  + " > " + ChuyenNgu.get(ten3,nnId);
				else		
					url=ChuyenNgu.get(ten1,nnId)  + " > " + ChuyenNgu.get(ten3,nnId);
			}
			if(rs!=null)rs.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			db.shutDown();
		}
		return url;
	}


	public String Check_Kho_Tong_VS_KhoCT(String nppId, Idbutils db)
	{
		String query =  " select count(*) as sodong "+
		" from  "+ 
		" ( " +
		"	 select npp_fk, kbh_fk, kho_fk, sanpham_fk, sum(available) as available, sum(soluong) as soluong, sum(booked) as booked_ct  "+
		"	 from nhapp_kho_chitiet where npp_fk = '" + nppId + "' "+
		"	 group by kbh_fk, npp_fk, kho_fk, sanpham_fk	  "+
		" ) " +
		" CT full outer join nhapp_kho total on total.npp_fk=ct.npp_fk and total.kbh_fk=ct.kbh_fk  "+
		"		and total.sanpham_fk=ct.sanpham_fk and total.kho_fk=ct.kho_fk   "+
		" where    "+
		"		(round( isnull(ct.available,0),1) + round( isnull(ct.booked_ct,0),1) != round(isnull(total.available,0),1) + round(isnull(total.booked ,0),1)  "+ 
		"			or round(isnull(total.soluong,0),0) != round(isnull(ct.soluong,0),0)  "+ 
		"		) and  isnull(total.npp_fk, ct.npp_fk) = '" + nppId + "' ";

		//System.out.prrintln("Check_Kho_Tong_VS_KhoCT " + query);
		String msg = "";
		ResultSet rs = db.get(query);
		try
		{
			while(rs.next())
			{
				if(rs.getInt("soDONG") > 0 )
				{
					msg += "Lỗi phát sinh do lệch Số lượng ";
					return msg;

				}
			}
			rs.close();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			return "Lỗi phát sinh khi check Tồn Kho";
		}
		return msg;

	}


	public String Check_Kho_Tong_VS_KhoCT(String nppId)
	{
		String query=
			"		select npp.ten as nppten,sp.ma as spma,sp.ten as spten,total.npp_fk,total.kho_fk,ct.soluong as ct,total.soluong as total  "+
			"		from  "+ 
			"		( "+
			"			select npp_fk,kbh_fk,kho_fk,sanpham_fk,sum(available)as available,sum(soluong) as soluong,  sum(booked) as booked_ct,N'tồn hiện tại_ct' as type  "+
			"			from nhapp_kho_chitiet  "+
			"			group by kbh_fk, npp_fk, kho_fk, sanpham_fk	  "+
			"		)as ct full outer join nhapp_kho total on total.npp_fk=ct.npp_fk and total.kbh_fk=ct.kbh_fk  "+
			"		and total.sanpham_fk=ct.sanpham_fk and total.kho_fk=ct.kho_fk   "+
			"		inner join sanpham sp on sp.pk_seq=isnull(ct.sanpham_fk,total.sanpham_fk)  "+
			"		inner join nhaphanphoi npp on npp.pk_seq=isnull(total.npp_fk,ct.npp_fk)  "+
			"		where    "+
			"		( isnull(ct.available,0)+isnull(ct.booked_ct,0)!= isnull(total.available ,0)+isnull(total.booked ,0)  "+ 
			"			or isnull(total.soluong,0)!=isnull(ct.soluong,0)  "+ 
			"		) and  isnull(total.npp_fk,ct.npp_fk) ='"+nppId+"' ";
		//System.out.prrintln("Check_Kho_Tong_VS_KhoCT"+query);
		String msg="";
		dbutils db = new dbutils();
		ResultSet rs =db.get(query);
		try
		{
			while(rs.next())
			{
				msg+=""+rs.getString("spMa") +" - "+rs.getString("spTen")+" \n";
			}
			rs.close();
			if(msg.length()>0)
			{
				msg +="Lỗi phát sinh do lệch Số lượng của sản phẩm "+msg ;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
			return "Lỗi phát sinh khi check khóa sổ;";
		}
		finally
		{
			if(db!=null)db.shutDown();
		}
		return msg;
	}

	public static int[] Getquyen(String ungdung, String parameters, String nhanvien)
	{
		int[] quyen = new int[6];
		int them =0;
		int xoa=0;
		int sua=0;
		int xem=0;
		int chot=0;
		int huychot=0;

		/*String query ="select isnull(THEM,0) as THEM,isnull(XOA,0) as XOA,isnull(SUA,0) as SUA,isnull(XEM,0) as XEM,isnull(CHOT,0) as CHOT "+
		"from NHOMQUYEN  a inner join PHANQUYEN b on a.DMQ_fk = b.DMQ_fk where b.Nhanvien_fk='"+nhanvien+"' and UngDung_fk='"+ ungdung +"'";
		dbutils db = new  dbutils();

		System.out.println("Getquyen: " + query);
		ResultSet rscheck= db.get(query);
		if(rscheck!=null)
		{
			try 
			{
				while(rscheck.next())
				{
					if(rscheck.getInt("THEM")!=0)
						them=rscheck.getInt("THEM");

					if(rscheck.getInt("XOA")!=0)
						xoa=rscheck.getInt("XOA");

					if(rscheck.getInt("SUA")!=0)
						sua=rscheck.getInt("SUA");

					if(rscheck.getInt("XEM")!=0)
						xem=rscheck.getInt("XEM");

					if(rscheck.getInt("CHOT")!=0)
						chot=rscheck.getInt("CHOT");
				}
				rscheck.close();
			} 
			catch 
			(Exception e) 
			{
				System.out.println(e.toString());
			}
		}*/

		/*db.shutDown();*/

		String query =	"\n 	SELECT sum(ISNULL(cast(THEM as int),0)) AS THEM, sum(ISNULL(cast(XOA as int),0)) AS XOA, sum(ISNULL(cast(SUA as int),0)) AS SUA, "+
		"\n	sum(ISNULL(cast(XEM as int),0)) AS XEM, sum(ISNULL(cast(CHOT as int),0)) AS CHOT, sum(ISNULL(cast(HUYCHOT as int),'0')) AS HUYCHOT "+
		"\n	FROM NHOMQUYEN  A INNER JOIN PHANQUYEN B ON A.DMQ_FK = B.DMQ_FK  "+ 
		"\n	INNER JOIN UNGDUNG UD ON UD.PK_SEQ=A.UNGDUNG_FK  "+
		"\n WHERE B.NHANVIEN_FK='"+nhanvien+"' AND UD.SERVLET='"+ungdung+"' AND UD.PARAMETERS='"+parameters+"' ";
		//System.out.prrintln("[QUERY quyen]"+query);

		dbutils db = new  dbutils();
		ResultSet rscheck = db.get(query);
		try
		{
			while(rscheck.next())
			{
				if(rscheck.getInt("THEM")!=0)
					them=rscheck.getInt("THEM");

				if(rscheck.getInt("XOA")!=0)
					xoa=rscheck.getInt("XOA");

				if(rscheck.getInt("SUA")!=0)
					sua=rscheck.getInt("SUA");

				if(rscheck.getInt("XEM")!=0)
					xem=rscheck.getInt("XEM");

				if(rscheck.getInt("CHOT")!=0)
					chot=rscheck.getInt("CHOT");

				if(rscheck.getInt("HuyChot")!=0)
					huychot=rscheck.getInt("HuyChot");


			}
			rscheck.close();

		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(db!=null)db.shutDown();
		}

		quyen[0]=them;
		quyen[1]=xoa;
		quyen[2]=sua;
		quyen[3]=xem;
		quyen[4]=chot;
		quyen[5]=huychot;

		return quyen;

	}

	public String ValidateParam(String param){		
		String result;
		if (param == null){
			result="";
		}else{
			if (param.indexOf("=") > 0){
				result = "";
			}else{
				result = param;
			}
		}
		return result;
	}


	public boolean isValidDate(String inDate) {

		if (inDate == null)
			return false;

		//set the format to use as a constructor argument
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");

		if (inDate.trim().length() != dateFormat.toPattern().length())
			return false;

		dateFormat.setLenient(false);

		try {
			//parse the inDate parameter
			dateFormat.parse(inDate.trim());
		}
		catch (ParseException pe) {
			return false;
		}
		return true;
	}

	public String getUserId(String querystring){
		String userId;
		String tmp;
		if (querystring != null){
			if (querystring.contains("&")){
				tmp = querystring.split("&")[0];
			}else{
				tmp = querystring;
			}

			userId = tmp.split("=")[1];
			if(userId.contains(";"))
				userId = userId.split(";")[0];
		}else{
			userId = "";
		}
		return userId;
	}

	public static String getParameterFromSearch(String querystring, String action){

		if (querystring != null){
			String []tmp = querystring.split("&");
			if(tmp != null){
				for(String t : tmp){
					if(t.contains(action)){
						if(t.split("=").length < 2)
							return "";
						return t.split("=")[1];
					}
				}
			}
		}
		return "";
	}


	public String getAction(String querystring){
		String action;
		String tmp;
		if (querystring != null){
			if (querystring.contains("&")){
				tmp = querystring.split("&")[1];
				action = tmp.split("=")[0];
			}else{
				action = "";
			}
		}else{
			action = "";
		}
		return action;

	}

	public String getId(String querystring){
		String id;
		String tmp;
		if (querystring != null){
			if (querystring.contains("&")){
				try {
					tmp = querystring.split("&")[1];
					id = tmp.split("=")[1];
				}
				catch (Exception e) {
					id = "";
				}
			}else{
				id = "";
			}
		}else{
			id = "";
		}
		return id;

	}

	public Hashtable<Integer, String>  ArraystringToHashtable(String[] s){
		Hashtable<Integer, String> h = new Hashtable<Integer, String>();
		if(s != null){
			int size = s.length;
			int m = 0;
			while(m < size){
				h.put(new Integer(m), s[m]) ;
				m++;
			}
		}else{
			h.put(new Integer(0), "null");
		}
		return h;
	}

	public String[]  ResultSetToArrayString(ResultSet rs){
		String[] s = new String[10];
		try{
			int m = rs.getFetchSize();
			s = new String[m+1];		 	
			while(rs.next()){
				s[1] = rs.getString(1);
			}
		}catch(Exception e){}
		return s;
	}

	// tra ve nhung thanh phan cua s1 khong nam trong s2
	public String[] compareArrayString(String[] s1, String[] s2){
		int i = s1.length;
		int j = s2.length;	

		String[] s = new String[i];
		int k = 0;
		for (int m = 0; m < i; m++){
			boolean result = true;
			for (int n = 0; n < j; n++){
				if (s1[m].equals(s2[n])){
					result = false;
				}
				if (result){
					s[k++]=s1[m];
				}
			}
		}
		return s;
	}

	public String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy");
		Date date = new Date();
		return dateFormat.format(date);	
	}
	public String getDataNow() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		return dateFormat.format(date);	
	}

	public	boolean isNumeric(String input){ 
		boolean result = true;
		char[] all = input.toCharArray();

		for(int i = 0; i < all.length;i++) {
			if(!(Character.isDigit(all[i]))) {
				result = false;
			}
		}
		return result;
	}

	public String calSum(String a, String b){
		if(a==null||b==null){return "";}
		if(a.length() == 0) {return "";}
		else {String s = "" + (a.length()+ b.length())/a.length(); return s;}
	}

	public boolean check(String a, String b, String c){

		//System.out.prrintln("UserId: "+a);
		String tmp = calSum(a, b);
		if (tmp.equals(c)){
			return true;
		}else{
			return false;
		}
	}

	public boolean isSessionAlive(HttpSession session){
		String userId = (String)session.getAttribute("userId");
		String userTen = (String)session.getAttribute("userTen");
		String sum = (String)session.getAttribute("sum");
		if(check(userId, userTen, sum)){		
			return true;
		}else{			
			return false;
		}
	}

	public String quyen_sanpham(String userId)
	{String sql ="( select sanpham_fk from nhanvien_sanpham where nhanvien_fk ='"+ userId +"')";
	return sql;
	}

	public String quyen_npp(String userId)
	{   String sql =" ( select npp_fk from phamvihoatdong where nhanvien_fk ='"+ userId +"') ";
		return sql;
	}
	public static String Quyen_npp(String userId)
	{   String sql =" ( select npp_fk from phamvihoatdong where nhanvien_fk ='"+ userId +"') ";
		return sql;
	}

	public String quyen_ddkd(String userId)
	{  
		String sql =" ( SELECT NPP_FK FROM PHAMVIHOATDONG WHERE Nhanvien_fk="+userId+")  ";
		return sql;
	}

	public String quyen_gsbh(String userId)
	{
		String sql =" (SELECT GSBH_FK FROM NHAPP_GIAMSATBH WHERE NPP_FK IN (SELECT NPP_FK FROM PHAMVIHOATDONG WHERE Nhanvien_fk="+userId+")) ";
		return sql;
	}

	public String quyen_asm(String userId)
	{
		String sql ="( SELECT ASM_FK FROM  ASM_KHUVUC WHERE "+ 
		"	KHUVUC_FK IN (SELECT KHUVUC_FK FROM NHAPHANPHOI  "+
		"	WHERE  KHUVUC_FK IS NOT NULL AND PK_SEQ IN (SELECT NPP_FK FROM PHAMVIHOATDONG WHERE Nhanvien_fk="+userId+") )) ";
		return sql;
	}
	public String quyen_bm(String userId)
	{
		String sql =" ( SELECT BM_FK FROM  BM_CHINHANH WHERE VUNG_FK IN (SELECT VUNG_FK FROM KHUVUC WHERE PK_SEQ IN "+
		" (SELECT KHUVUC_FK FROM NHAPHANPHOI WHERE PK_SEQ IN (SELECT NPP_FK FROM PHAMVIHOATDONG WHERE NHANVIEN_FK="+userId+")))) ";
		return sql;
	}



	public String quyen_kenh(String userId)
	{
		String sql ="( select kenh_fk as kbh_fk from nhanvien_kenh where nhanvien_fk ='"+ userId +"' )";
		return sql;
	}
	public int[] quyen_ungdung(String userId)
	{  
		int mang[] = new int[140];
		String sql ="select ungdung_fk from nhomquyen where dmq_fk in (select pk_seq from danhmucquyen where pk_seq in (select dmq_fk from phanquyen where nhanvien_fk ='"+ userId +"'))";
		//System.out.println("chuoi phan quyen :" + sql);
		dbutils db=new dbutils();
		ResultSet rs= db.get(sql);
		for(int j = 0;j<140;j++)
			mang[j] = 1;
		/*	int i = 0;
		if(rs!=null)
			try {
				while(rs.next())
				{
					i = Integer.parseInt(rs.getString("ungdung_fk"));
					mang[i] = 1;

				}
			} catch(Exception e) {
				e.printStackTrace();
			}
			db.shutDown();*/
		return mang;
	}


	//chuyen tieng viet khong dau
	private static char[] SPECIAL_CHARACTERS = { ' ', '!', '"', '#', '$', '%',
		'*', '+', ',', ':', '<', '=', '>', '?', '@', '[', '\\', ']', '^',
		'`', '|', '~', 'À', 'Á', 'Â', 'Ã', 'È', 'É', 'Ê', 'Ì', 'Í', 'Ò',
		'Ó', 'Ô', 'Õ', 'Ù', 'Ú', 'Ý', 'à', 'á', 'â', 'ã', 'è', 'é', 'ê',
		'ì', 'í', 'ò', 'ó', 'ô', 'õ', 'ù', 'ú', 'ý', 'Ă', 'ă', 'Đ', 'đ',
		'Ĩ', 'ĩ', 'Ũ', 'ũ', 'Ơ', 'ơ', 'Ư', 'ư', 'Ạ', 'ạ', 'Ả', 'ả', 'Ấ',
		'ấ', 'Ầ', 'ầ', 'Ẩ', 'ẩ', 'Ẫ', 'ẫ', 'Ậ', 'ậ', 'Ắ', 'ắ', 'Ằ', 'ằ',
		'Ẳ', 'ẳ', 'Ẵ', 'ẵ', 'Ặ', 'ặ', 'Ẹ', 'ẹ', 'Ẻ', 'ẻ', 'Ẽ', 'ẽ', 'Ế',
		'ế', 'Ề', 'ề', 'Ể', 'ể', 'Ễ', 'ễ', 'Ệ', 'ệ', 'Ỉ', 'ỉ', 'Ị', 'ị',
		'Ọ', 'ọ', 'Ỏ', 'ỏ', 'Ố', 'ố', 'Ồ', 'ồ', 'Ổ', 'ổ', 'Ỗ', 'ỗ', 'Ộ',
		'ộ', 'Ớ', 'ớ', 'Ờ', 'ờ', 'Ở', 'ở', 'Ỡ', 'ỡ', 'Ợ', 'ợ', 'Ụ', 'ụ',
		'Ủ', 'ủ', 'Ứ', 'ứ', 'Ừ', 'ừ', 'Ử', 'ử', 'Ữ', 'ữ', 'Ự', 'ự', 'Ý', 'Ỳ', 'Ỵ', 'ỳ', 'ỵ', 'ý'};

	private static char[] REPLACEMENTS = { '-', '\0', '\0', '\0', '\0', '\0',
		'\0', '_', '\0', '_', '\0', '\0', '\0', '\0', '\0', '\0', '_',
		'\0', '\0', '\0', '\0', '\0', 'A', 'A', 'A', 'A', 'E', 'E', 'E',
		'I', 'I', 'O', 'O', 'O', 'O', 'U', 'U', 'Y', 'a', 'a', 'a', 'a',
		'e', 'e', 'e', 'i', 'i', 'o', 'o', 'o', 'o', 'u', 'u', 'y', 'A',
		'a', 'D', 'd', 'I', 'i', 'U', 'u', 'O', 'o', 'U', 'u', 'A', 'a',
		'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A',
		'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'E', 'e', 'E', 'e',
		'E', 'e', 'E', 'e', 'E', 'e', 'E', 'e', 'E', 'e', 'E', 'e', 'I',
		'i', 'I', 'i', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o',
		'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O',
		'o', 'U', 'u', 'U', 'u', 'U', 'u', 'U', 'u', 'U', 'u', 'U', 'u',
		'U', 'u', 'Y', 'Y', 'Y', 'y', 'y', 'y'};

	public String replaceAEIOU(String s) 
	{
		int maxLength = Math.min(s.length(), 236);
		char[] buffer = new char[maxLength];
		int n = 0;
		for (int i = 0; i < maxLength; i++) 
		{
			char ch = s.charAt(i);
			int index = Arrays.binarySearch(SPECIAL_CHARACTERS, ch);
			if (index >= 0) 
			{
				buffer[n] = REPLACEMENTS[index];
			} 
			else 
			{
				buffer[n] = ch;
			}
			// skip not printable characters
			if (buffer[n] > 31) {
				n++;
			}
		}

		// skip trailing slashes
		while (n > 0 && buffer[n - 1] == '/') 
		{
			n--;
		}
		return String.valueOf(buffer, 0, n);
	}

	public static String antiSQLInspection(String param){
		String tmp = param;
		PolicyFactory policy = Sanitizers.FORMATTING.and(Sanitizers.LINKS);
		return (tmp==null?null:policy.sanitize(tmp));

	}


	public String[] antiSQLInspection_Array(String[] arr){

		if(arr != null)
		{
			for(int i = 0; i < arr.length; i++ )
			{
				if( arr[i] != null)
					arr[i] =     antiSQLInspection(  arr[i] );
			}

		}
		return arr;

	}

	public boolean CheckNppIsMT(String userId)
	{
		dbutils db = new dbutils();
		this.nppId=this.getIdNhapp(userId);

		String sql=" select gsbh.pk_seq,gsbh.kbh_fk  from nhapp_giamsatbh inner join giamsatbanhang gsbh on gsbh.pk_seq=gsbh_fk "+ 
		" where  npp_fk= "+this.nppId+" and ngayketthuc >='"+this.getDataNow()+"' ";
		//System.out.prrintln(sql);
		try{
			ResultSet rs=db.get(sql);
			while (rs.next()){
				if(rs.getString("kbh_fk").trim().equals("100021")){
					return true;
				}

			}
			rs.close();
			db.shutDown();
		}catch(Exception er){
			return false;
		}

		return false;

	}
	public boolean checkHopLe(String userId)
	{
		dbutils db = new dbutils();
		String query = "select npp.pk_seq from nhanvien nv inner join nhaphanphoi npp on nv.convsitecode = sitecode  where nv.pk_seq = '" + userId + "'";
		//System.out.println(query);
		ResultSet rs = db.get(query);
		String nppId = "";
		int dakhoaso30 = 0;
		int dacodctk01 = 0;
		try 
		{
			if(rs.next())
			{
				nppId = rs.getString("pk_seq");
				rs.close();
			}
			query = "select count(*) as dakhoaso from khoasongay where ngayks = '2012-04-30' and npp_fk = '" + nppId + "'";
			//System.out.println(query);

			//System.out.println("1 :"+query);
			rs = db.get(query);

			if(rs.next())
			{
				dakhoaso30 = rs.getInt("dakhoaso");
				rs.close();
			}

			if(dakhoaso30 == 0)  //chua khoa so ngay nay
				return true;

			query = "select count(npp_fk) as sodong from dieuchinhtonkho where npp_fk = '" + nppId + "' and trangthai = '1' and ngaydc = '2012-05-01'";
			//System.out.println("2 :"+query);
			rs = db.get(query);

			if(rs.next())
			{
				dacodctk01 = rs.getInt("sodong");
				rs.close();
			}

			if(dacodctk01 == 0)
				return false;
			/*
					query = "select count(*) as sodong from nppdaduyet where npp_fk = '" + nppId + "'";
					rs = db.get(query);
					int daduyet = 0;
					if(rs.next())
					{
						daduyet = rs.getInt("sodong");
						rs.close();
					}
					if(daduyet == 0)
						return false;
			 */
		} 
		catch(Exception e) { return false; }
		return true;
	}

	public String Doisangchuoi(String[] checknpp)
	{
		// TODO Auto-generated method stub
		String str = "";
		if (checknpp != null)
		{
			for (int i = 0; i < checknpp.length; i++)
			{
				if (i == 0)
				{
					str = checknpp[i];
				} else
				{
					str = str + "," + checknpp[i];
				}
			}
		}
		return str;

	}

	public boolean checkDaduyet(String userId)
	{
		dbutils db = new dbutils();
		String query = "select npp.pk_seq from nhanvien nv inner join nhaphanphoi npp on nv.convsitecode = sitecode  where nv.pk_seq = '" + userId + "'";
		//System.out.println(query);
		ResultSet rs = db.get(query);
		String nppId = "";
		try 
		{
			if(rs.next())
			{
				nppId = rs.getString("pk_seq");
				rs.close();
			}

			query = "select count(*) as sodong from nppdaduyet where npp_fk = '" + nppId + "'";
			//System.out.println(query);
			rs = db.get(query);
			int daduyet = 0;
			if(rs.next())
			{
				daduyet = rs.getInt("sodong");
				rs.close();
			}
			if(daduyet == 0)
				return false;

		} 

		catch(Exception e) { return false; }
		return true;
	}
	public String getNgayKs_CongMot(String nppId2,
			geso.dms.center.db.sql.dbutils db) 
	{

		String query=
			"	SELECT "+
			"		( select convert(varchar(10), DATEADD(dd, 1, ( select MAX(ngayks) from KHOASONGAY where NPP_FK = a.PK_SEQ )), 120) ) as ngayKS,A.pk_Seq "+
			"	FROM NHAPHANPHOI A "+
			"	where A.PK_sEQ='"+nppId2+"' ";
		String ngayKsCongMot="";

		System.out.println("[Query]"+query);

		ResultSet rs =db.get(query);
		try
		{
			while(rs.next())
			{
				ngayKsCongMot=rs.getString("ngayKS");
			}
			if(rs!=null)rs.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return ngayKsCongMot;

	}

	public String getNgayKs_CongMot(String nppId2,
			geso.dms.distributor.db.sql.dbutils db) 
	{

		String query=
			"	SELECT "+
			"		( select convert(varchar(10), DATEADD(dd, 1, ( select MAX(ngayks) from KHOASONGAY where NPP_FK = a.PK_SEQ )), 120) ) as ngayKS,A.pk_Seq "+
			"	FROM NHAPHANPHOI A "+
			"	where A.PK_sEQ='"+nppId2+"' ";
		String ngayKsCongMot="";

		System.out.println("[Query]"+query);

		ResultSet rs =db.get(query);
		try
		{
			while(rs.next())
			{
				ngayKsCongMot=rs.getString("ngayKS");
			}
			if(rs!=null)rs.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return ngayKsCongMot;

	}


	public static String Check_Huy_NghiepVu_KhoaSo_Tao_Moi_NV(String nppId , String ngay_nghiep_vu, Idbutils db)
	{

		String query="select count(*) as sl from khoasongay where npp_fk="+nppId;
		ResultSet rs=db.get(query);
		try {
			rs.next();
			if(rs.getInt("sl")==0)
			{
				rs.close();
				return "Chưa thiết lập khóa sổ ngày vui lòng thiết lập.";
			}
		} catch (SQLException e1) {

			e1.printStackTrace();
			return "Lỗi phát sinh khi check khóa sổ;";
		}

		query = " select 1 "+
		" where  exists  "+
		" (  "+
		"	select 1 from khoasongay where ngayks>= '"+ngay_nghiep_vu+"'  "+
		"	 and  NPP_FK= "+  nppId +
		" )  ";

		System.out.println("Check_Huy_NghiepVu_KhoaSo_Tao_Moi_NV: " + query);
		String msg = "";
		rs = db.get(query);
		try
		{
			if(rs.next())
			{
				msg = "Bạn không được thực hiện nghiệp vụ trong ngày đã khóa sổ !";
			}
			rs.close();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			return "Lỗi phát sinh khi check khóa sổ;";
		}
		return msg;
	}

	public static String Check_Huy_NghiepVu_KhoaSo(String table, String id, String column, Idbutils db)
	{

		String query="select count(*) as sl from khoasongay where npp_fk in ( select  npp_fk from "+table+" where  PK_SEQ = '" + id + "'  ) " ;
		ResultSet rs=db.get(query);
		try {
			rs.next();
			if(rs.getInt("sl")==0)
			{
				rs.close();
				return "Chưa thiết lập khóa sổ ngày vui lòng thiết lập.";
			}
		} catch (SQLException e1) {

			e1.printStackTrace();
			return "Lỗi phát sinh khi check khóa sổ;";
		}
		query = " select * "+
		" from " + table + "  a  "+
		" where PK_SEQ = '" + id + "' and exists  "+
		" (  "+
		"	select 1 from khoasongay where ngayks>= "+column+" "+
		"	 and  NPP_FK=a.NPP_FK "+
		" )  ";

		System.out.println("Check_Huy_NghiepVu_KhoaSo: " + query);
		String msg = "";
		rs = db.get(query);
		try
		{
			while(rs.next())
			{
				msg = "Bạn không được thực hiện nghiệp vụ trong ngày đã khóa sổ !";
			}
			rs.close();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			return "Lỗi phát sinh khi check khóa sổ;";
		}
		return msg;
	}

	public String setTieuDe(ITieuchithuongTBList obj)
	{
		String tieude="";
		dbutils db = new dbutils();




		/* if(obj.getVungId()!=null && obj.getVungId().length()>0)
		    {
		    	tieude += this.getTieuDe( "Vung","Ten", obj.getVungId(),db ) + "_" ;
		    }

		    if(obj.getKvId()!=null && obj.getKvId().length()>0)
		    {
		    	tieude += this.getTieuDe( "KhuVuc","Ten", obj.getKvId(),db ) + "_" ;
		    }

		    if(obj.getNppIds()!=null && obj.getNppIds().length()>0)
		    {
		    	tieude += this.getTieuDe( "NhaPhanPhoi","Ten", obj.getNppIds(), db ) + "_" ;
		    }
		    if(obj.getSchemeIds()!=null && obj.getSchemeIds().length()>0)
		    {
		    	tieude += this.getTieuDe( "CTKhuyenMai","DienGiai", obj.getSchemeIds(), db ) + "_" ;
		    }*/
		if(obj.getNam()!=null && obj.getNam().length()>0)
		{
			tieude += obj.getNam() +"_";
		}
		if(obj.getThang()!=null && obj.getThang().length()>0)
		{
			tieude += obj.getThang() +"_";
		}

		if(obj.getTungay()!=null && obj.getTungay().length()>0)
		{
			tieude += obj.getTungay() +"_";
		}
		if(obj.getDenngay()!=null && obj.getDenngay().length()>0)
		{
			tieude += obj.getDenngay() +"_";
		}
		db.shutDown();
		return tieude;
	}

	public String setTieuDe(Ireport obj)
	{

		String tieude="";
		dbutils db = new dbutils();


		/* if(obj.getkenhId()!=null && obj.getkenhId().length()>0)
	    {
	    	tieude +=this.getTieuDe( "KenhBanHang","Ten", obj.getkenhId(), db )+ "_"; 
	    }
	    if(obj.getdvkdId()!=null && obj.getdvkdId().length()>0)
	    {
	    	tieude += this.getTieuDe( "DonViKinhDoanh","DienGiai", obj.getdvkdId(),db ) + "_" ;
	    }



	    if(obj.getkhachhangId()!=null && obj.getkhachhangId().length()>0)
	    {
	    	tieude += this.getTieuDe( "KhachHang","Ten", obj.getkhachhangId(),db ) + "_" ;
	    }

	    if(obj.getnhanhangId()!=null && obj.getnhanhangId().length()>0 )
	    {
	    	tieude += this.getTieuDe( "NhanHang","Ten", obj.getnhanhangId(),db ) + "_" ;
	    }
	    if(obj.getchungloaiId()!=null && obj.getchungloaiId().length()>0 )
	    {
	    	tieude += this.getTieuDe( "ChungLoai","Ten", obj.getchungloaiId(),db ) + "_" ;
	    }
	    if(obj.getVungId()!=null && obj.getVungId().length()>0)
	    {
	    	tieude += this.getTieuDe( "Vung","Ten", obj.getVungId(),db ) + "_" ;
	    }

	    if(obj.getKvId()!=null && obj.getKvId().length()>0)
	    {
	    	tieude += this.getTieuDe( "KhuVuc","Ten", obj.getKvId(),db ) + "_" ;
	    }

	    if(obj.getnppId()!=null && obj.getnppId().length()>0)
	    {
	    	tieude += this.getTieuDe( "NhaPhanPhoi","Ten", obj.getnppId(), db ) + "_" ;
	    }*/

		if(obj.gettungay()!=null && obj.gettungay().length()>0)
		{
			tieude += obj.gettungay() + "_";
		}
		if(obj.getdenngay()!=null && obj.getdenngay().length()>0)
		{
			tieude += obj.getdenngay() + "_";
		}



		db.shutDown();
		return tieude;
	}

	public String setTieuDe(IRouteSumaryReport obj)
	{
		String tieude="";
		/*  dbutils db = new dbutils();
	    if(obj.getKhuVuc()!=null && obj.getKhuVuc().length()>0)
	    {
	    	tieude += this.getTieuDe( "KhuVuc","Ten", obj.getKhuVuc(), db ) + "_" ;
	    }

	    if(obj.getnppId()!=null && obj.getnppId().length()>0)
	    {
	    	tieude += this.getTieuDe( "NhaPhanPhoi","Ten", obj.getnppId(), db ) + "_" ;
	    }


	    db.shutDown();*/
		return tieude;
	}

	public String getTieuDe(String table,String column,String id ,geso.dms.center.db.sql.dbutils db)
	{
		String query=" select  dbo.ftBoDau("+column+")  from "+table+" where pk_seq in ("+id+")";
		ResultSet rs= db.get(query);
		String tieude="";

		try
		{
			while(rs.next())
			{
				tieude +=java.net.URLDecoder.decode(rs.getString(1).replaceAll("%(?![0-9a-fA-F]{2})", "%25").replace(" ", "-"), "UTF-8");
			}
			rs.close();
		}catch(Exception er)
		{
			System.out.print("[TieuDe]"+query);
			er.printStackTrace();
		}
		return tieude;
	}

	public ResultSet getMenuRs_ver2(String userId,String loaiMenu,dbutils db )
	{
		String queryWith = 
			"\n with ungdungcon as "+ 
			"\n ( "+ 
			"\n 	select a.level, a.loaimenu, a.trangthai, a.ten, a.servlet, a.parameters, "+ 
			"\n 	ROW_NUMBER () OVER ( PARTITION BY a.ungdung_fk order by a.ungdung_fk, a.pk_seq )  as SoTT, a.Ungdung_Fk, a.PK_SEQ "+ 
			"\n 	from ungdung a inner join ungdung b on a.ungdung_fk = b.pk_seq "+ 
			"\n 	inner join ungdung c on b.ungdung_fk = c.pk_seq "+ 
			"\n 	where a.level = '3' and b.level = '2' and a.loaiMenu='0' and a.TrangThai=1 and b.TrangThai=1 and c.TrangThai=1  "+ 
			"\n 	and a.pk_seq in  (select ungdung_Fk from NhanVien_UngDung where NhanVien_fk='"+ userId +"')  "+ 
			"\n ) ";

		String query = queryWith +
		" SELECT * \n"+
		" FROM ( \n"+
		" select pk_seq as ungdungcha, '' as level1, '' as level2, ten, servlet, parameters, sott as stt1, -1 as stt2, -1 as sott, level,       \n"+   
		"   	isnull((select count(*)  from ungdung a inner join ungdung b on a.ungdung_fk = b.pk_seq inner join ungdung c on b.ungdung_fk = c.pk_seq   \n"+   
		"   		where c.pk_seq = ud.pk_seq and a.level = '3' and a.PK_SEQ in  (select ungdung_Fk from NhanVien_UngDung where NhanVien_fk='"+userId+"')),0) as				cosubmenu,0 as maxstt, 0 as totalstt, 1 as max_total_stt, 1 as molevel1, 0 as molevel2       \n"+   
		"   from ungdung ud       \n"+   
		"   where level = '1'   and ud.loaiMenu='"+loaiMenu+"' and ud.TrangThai=1    \n"+  
		"   and ud.PK_SEQ in 	  \n"+  
		"   (  "+   
		"   	select PK_SEQ from UNGDUNG where ungdung_fk is null and Level=1 \n"+   
		"   	and PK_SEQ in (select distinct UNGDUNG.ungdung_fk from nhanvien_ungdung inner join UNGDUNG on nhanvien_ungdung.ungdung_fk=UNGDUNG.PK_SEQ  \n"+   
		"   	where nhanvien_ungdung.nhanvien_Fk='"+userId+"' and ungdung.level=3 )  and UNGDUNG.TrangThai=1   \n"+   
		"   	union  \n"+   
		"   	 \n"+   
		"   	select PK_SEQ from UNGDUNG where ungdung_fk is null and Level=1  \n"+   
		"   	and PK_SEQ in (select distinct ud.ungdung_fk from nhanvien_ungdung inner join UNGDUNG on nhanvien_ungdung.ungdung_fk=UNGDUNG.PK_SEQ \n"+   
		"   	inner join UNGDUNG ud on ud.PK_SEQ=ungdung.ungdung_fk  \n"+  
		"   	where nhanvien_ungdung.nhanvien_Fk='"+userId+"' and ungdung.level=3 ) and UNGDUNG.TrangThai=1  \n"+   
		"    )  \n"+   
		"     \n"+   
		"   union all         \n"+   
		"   select b.pk_seq as ungdungcha, '' as level1, '' as level2, a.ten, a.servlet, a.parameters, b.sott as stt1, a.sott as stt2, -1 as sott, a.level,   \n"+   
		"   0, 0 as		maxstt, 0 as totalstt, 1 as max_total_stt, 0 as molevel1, 1 as molevel2         \n"+   
		"   from ungdung a inner join ungdung b on a.ungdung_fk = b.pk_seq       \n"+   
		"   where a.level = '2' 	 and a.loaiMenu='"+loaiMenu+"' 	and a.TrangThai=1 and b.TrangThai=1 \n"+   
		"   and exists  	  \n"+   
		"   (   "+   
		"   	select nvud.ungdung_Fk from NhanVien_UngDung nvud inner join UNGDUNG ud on nvud.ungdung_fk=ud.PK_SEQ  \n"+   
		"   	 where nvud.NhanVien_fk='"+userId+"'    and ud.UNGDUNG_FK=a.PK_SEQ     \n"+   
		"   )  \n"+   
		"   union all \n"+   
		"   select b.pk_seq as ungdungcha, c.ten as level1, b.ten as level2, a.ten, a.servlet, a.parameters, c.sott as stt1, b.sott as stt2, a.sott, a.level, 0,  \n"+   
		"   ( select max(sott) from ungdungcon where ungdung_fk = b.pk_seq and PK_SEQ 	in    \n"+   
		"   (select ungdung_Fk from NhanVien_UngDung where NhanVien_fk='"+userId+"')  )   as maxstt, b.sott + a.sott as totalstt,   \n"+

		"		isnull(( select max(sott) from ungdungcon where ungdung_fk=b.pk_seq and \n"+
		"	PK_SEQ IN (select ungdung_Fk from NhanVien_UngDung where NhanVien_fk='"+userId+"')   ),0) \n"+

		" + isnull(( "+ 
		" select count(distinct l1.PK_SEQ) from ungdung l1  where ungdung_fk = c.PK_SEQ "+
		" AND PK_sEQ IN (select b.ungdung_fk from NhanVien_UngDung a inner join UNGDUNG b on b.PK_SEQ=a.UngDung_fk where NhanVien_fk='"+ userId +"') "+
		" and not exists ( select 1 from ungdung udc where udc.Ungdung_Fk = l1.PK_SEQ having sum(udc.TrangThai) = '0' ) ),0) as max_total_stt, 0 as molevel1, 0 as molevel2 "+

		//" + isnull(( select max(sott) from ungdung l1  where ungdung_fk =c.PK_SEQ   \n"+
		//"		AND PK_sEQ IN (select b.ungdung_fk from NhanVien_UngDung a inner join UNGDUNG b on b.PK_SEQ=a.UngDung_fk where NhanVien_fk='"+userId+"') ),0)  as max_total_stt, 0 as molevel1, 0 as molevel2	      \n"+   
		"   from ungdungcon a inner join ungdung b on a.ungdung_fk = b.pk_seq          \n"+   
		"   inner join ungdung c on b.ungdung_fk = c.pk_seq         \n"+   
		"   where a.level = '3' and b.level = '2'     and a.loaiMenu='"+loaiMenu+"' and a.TrangThai=1 and b.TrangThai=1 and c.TrangThai=1 \n"+   
		"   and a.pk_seq    \n"+  
		"   in  (select ungdung_Fk from NhanVien_UngDung where NhanVien_fk='"+userId+"')  \n"+  
		"   union all      \n"+   
		"   select a.pk_seq as ungdungcha, c.ten as level1, '' as level2, a.ten, a.servlet, a.parameters, c.sott as stt1, a.sott as stt2, a.sott, a.level, 0,       \n"+  
		"   ( select max(sott) from ungdung where ungdung_fk = c.pk_seq ) as maxstt, a.sott as totalstt, ( select max(sott) from ungdung where ungdung_fk = c.pk_seq ) as max_total_stt, 0 as molevel1, 0 as molevel2	      \n"+   
		"   from ungdung a  inner join ungdung c on a.ungdung_fk = c.pk_seq         \n"+   
		"   where a.level = '3' and c.level = '1'     and a.loaiMenu='"+loaiMenu+"'  and a.TrangThai=1  and c.TrangThai=1     \n"+   
		"   and a.pk_seq  in    \n"+   
		"   (select ungdung_Fk from NhanVien_UngDung where NhanVien_fk= '"+userId+"' )      \n"+   
		" ) MENU \n"+
		" ORDER BY stt1 asc, stt2 asc, sott asc  ";	

		ResultSet rs =db.get(query);
		System.out.println("Menu Query: "+query);
		return rs;
	}

	public ResultSet getMenuRs(String userId,String loaiMenu,dbutils db )
	{
		String query = " select count(*) d from PHANQUYEN where Nhanvien_fk = "+userId+" and DMQ_fk in (select pk_seq from DANHMUCQUYEN where HoatDong = 1) ";
		ResultSet rs =db.get(query);
		try {
			rs.next();
			int sd = rs.getInt("d");
			rs.close();
			if(sd <=0)
				return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 query=
			" SELECT * \n"+
			" FROM ( \n"+
			" select pk_seq as ungdungcha, '' as level1, '' as level2, ten, servlet, parameters, sott as stt1, -1 as stt2, -1 as sott, level,       \n"+   
			"   	isnull((select count(*)  from ungdung a inner join ungdung b on a.ungdung_fk = b.pk_seq inner join ungdung c on b.ungdung_fk = c.pk_seq   \n"+   
			"   		where c.pk_seq = ud.pk_seq and a.level = '3' and a.PK_SEQ in  (select ungdung_Fk from NhanVien_UngDung where NhanVien_fk='"+userId+"')),0) as				cosubmenu,0 as maxstt, 0 as totalstt, 1 as max_total_stt, 1 as molevel1, 0 as molevel2       \n"+   
			"   from ungdung ud       \n"+   
			"   where level = '1'   and ud.loaiMenu='"+loaiMenu+"' and ud.TrangThai=1    \n"+  
			"   and ud.PK_SEQ in 	  \n"+  
			"   (  "+   
			"   	select PK_SEQ from UNGDUNG where ungdung_fk is null and Level=1 \n"+   
			"   	and PK_SEQ in (select distinct UNGDUNG.ungdung_fk from nhanvien_ungdung inner join UNGDUNG on nhanvien_ungdung.ungdung_fk=UNGDUNG.PK_SEQ  \n"+   
			"   	where nhanvien_ungdung.nhanvien_Fk='"+userId+"' and ungdung.level=3 )  and UNGDUNG.TrangThai=1   \n"+   
			"   	union  \n"+   
			"   	 \n"+   
			"   	select PK_SEQ from UNGDUNG where ungdung_fk is null and Level=1  \n"+   
			"   	and PK_SEQ in (select distinct ud.ungdung_fk from nhanvien_ungdung inner join UNGDUNG on nhanvien_ungdung.ungdung_fk=UNGDUNG.PK_SEQ \n"+   
			"   	inner join UNGDUNG ud on ud.PK_SEQ=ungdung.ungdung_fk  \n"+  
			"   	where nhanvien_ungdung.nhanvien_Fk='"+userId+"' and ungdung.level=3 ) and UNGDUNG.TrangThai=1  \n"+   
			"    )  \n"+   
			"     \n"+   
			"   union all         \n"+   
			"   select b.pk_seq as ungdungcha, '' as level1, '' as level2, a.ten, a.servlet, a.parameters, b.sott as stt1, a.sott as stt2, -1 as sott, a.level,   \n"+   
			"   0, 0 as		maxstt, 0 as totalstt, 1 as max_total_stt, 0 as molevel1, 1 as molevel2         \n"+   
			"   from ungdung a inner join ungdung b on a.ungdung_fk = b.pk_seq       \n"+   
			"   where a.level = '2' 	 and a.loaiMenu='"+loaiMenu+"' 	and a.TrangThai=1 and b.TrangThai=1 \n"+   
			"   and exists  	  \n"+   
			"   (   "+   
			"   	select nvud.ungdung_Fk from NhanVien_UngDung nvud inner join UNGDUNG ud on nvud.ungdung_fk=ud.PK_SEQ  \n"+   
			"   	 where nvud.NhanVien_fk='"+userId+"'    and ud.UNGDUNG_FK=a.PK_SEQ     \n"+   
			"   )  \n"+   
			"   union all         \n"+   
			"   select b.pk_seq as ungdungcha, c.ten as level1, b.ten as level2, a.ten, a.servlet, a.parameters, c.sott as stt1, b.sott as stt2, a.sott, a.level, 0,  \n"+   
			"   ( select max(sott) from ungdung where ungdung_fk = b.pk_seq and PK_SEQ 	in    \n"+   
			"   (select ungdung_Fk from NhanVien_UngDung where NhanVien_fk='"+userId+"') and trangthai = '1' )   as maxstt, b.sott + a.sott as totalstt,   \n"+

			"		isnull(( select max(sott) from ungdung where ungdung_fk=b.pk_seq and trangthai = '1' and \n"+
			"	PK_SEQ IN (select ungdung_Fk from NhanVien_UngDung where NhanVien_fk='"+userId+"') ),0) \n"+
			" + isnull(( select max(sott) from ungdung l1  where  trangthai = '1' and ungdung_fk =c.PK_SEQ   \n"+
			"		AND PK_sEQ IN (select b.ungdung_fk from NhanVien_UngDung a inner join UNGDUNG b on b.PK_SEQ=a.UngDung_fk where NhanVien_fk='"+userId+"') ),0)  as max_total_stt, 0 as molevel1, 0 as molevel2	      \n"+   
			"   from ungdung a inner join ungdung b on a.ungdung_fk = b.pk_seq \n"+   
			"   inner join ungdung c on b.ungdung_fk = c.pk_seq \n"+   
			"   where a.level = '3' and b.level = '2' and a.loaiMenu='"+loaiMenu+"' and a.TrangThai=1 and b.TrangThai=1 and c.TrangThai=1 \n"+   
			"   and a.pk_seq    \n"+  
			"   in  (select ungdung_Fk from NhanVien_UngDung where NhanVien_fk='"+userId+"')  \n"+  
			"   union all      \n"+   
			"   select a.pk_seq as ungdungcha, c.ten as level1, '' as level2, a.ten, a.servlet, a.parameters, c.sott as stt1, a.sott as stt2, a.sott, a.level, 0, \n"+  
			"   ( select max(sott) from ungdung where ungdung_fk = c.pk_seq  and trangthai = '1' ) as maxstt, a.sott as totalstt, ( select max(sott) from ungdung where ungdung_fk = c.pk_seq ) as max_total_stt, 0 as molevel1, 0 as molevel2 \n"+   
			"   from ungdung a  inner join ungdung c on a.ungdung_fk = c.pk_seq         \n"+   
			"   where a.level = '3' and c.level = '1' and a.loaiMenu='"+loaiMenu+"'  and a.TrangThai=1  and c.TrangThai=1     \n"+   
			"   and a.pk_seq  in    \n"+   
			"   (select ungdung_Fk from NhanVien_UngDung where NhanVien_fk= '"+userId+"' )      \n"+   
			" ) MENU \n"+
			" ORDER BY stt1 asc, stt2 asc, sott asc  ";	

		rs =db.get(query);
		System.out.println("Menu Query: "+query);
		return rs;
	}

	public String Update_NPP_Kho_Sp(String ngaychungtu,String MaDonNghiepVu ,String nghiepvu ,Idbutils db, String khott_fk, String spId,String npp_fk,String kbh_fk,double soluong ,double booked,double available , double dongia) 
	{
		// TODO Phương thức đưa số lượng nhập vào kho,bảng ERP_KHOTT_SANPHAM
		try{
			int flag=0;
			String querylog="";
			/*String query="  select sanpham_fk ,available,soluong , sp.ma+ ' '+ sp.ten as ten , ISNULL(KHO.GIAMUA,0) AS GIATON   " +
			"  from NHAPP_KHO kho " +
			"  inner join sanpham sp  on kho.sanpham_fk=sp.pk_seq  where KBH_FK ="+kbh_fk+" AND NPP_FK="+npp_fk+" AND  " +
			"  KHO_FK="+khott_fk+" and sanpham_fk= "+spId;*/
			String query="  select sanpham_fk ,available,soluong , sp.ma+ ' '+ sp.ten as ten , ISNULL(KHO.GIAMUA,0) AS GIATON   " +
			"  from NHAPP_KHO kho " +
			"  inner join sanpham sp  on kho.sanpham_fk=sp.pk_seq  where KBH_FK =? AND NPP_FK=? AND  " +
			"  KHO_FK=? and sanpham_fk= ? ";
			List<Object> dataSearch = new ArrayList<Object>(); 
			dataSearch.add(kbh_fk);
			dataSearch.add(npp_fk);
			dataSearch.add(khott_fk);
			dataSearch.add(spId);

			//System.out.println("[UTILITY KHO : QUERY LAY SAN PHAM KHO TONG]" +query);
			double available_ton=0;
			double giaton=0;
			double soluongton=0;
			//ResultSet rsCheck = db.get(query);
			Object[] khodataquerylog;
			Object[] khodata;
			ResultSet rsCheck = db.get_v2(query,dataSearch);
			dataSearch.clear();
			if(rsCheck.next()){
				soluongton=rsCheck.getDouble("soluong");
				available_ton=rsCheck.getDouble("available");
				giaton=rsCheck.getDouble("GIATON");

				if(available < 0 && available_ton < (-1)*available ){
					return "Số lượng tồn hiện tại trong kho của sản phẩm : "+rsCheck.getString("ten") + "  ["+available_ton+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
				}

				if(soluong < 0 && soluongton <(-1)*soluong ){
					return "Số lượng tồn  trong kho của sản phẩm : "+rsCheck.getString("ten") + "  ["+soluongton+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
				}


				/*querylog="insert into log_kho (MaDonNghiepVu,nghiepvu,sanpham_fk,kbh_fk,kho_fk,npp_fk,soluong,book,avai,ngaychungtu,soluongNV,bookNv,avaiNV)"+
				"select "+MaDonNghiepVu+",N'"+nghiepvu+"',sanpham_fk,kbh_fk,kho_fk,npp_fk,SOLUONG,BOOKED,AVAILABLE,'"+ngaychungtu+"',round(" + soluong + ",1),round("+booked+",1),round(" + available + ",1) from nhapp_kho where  npp_fk='"+npp_fk+"' and kbh_fk='"+kbh_fk+"' and kho_fk='"+khott_fk+"' and sanpham_fk='"+spId+"'  ";
				 */
				querylog="insert into log_kho (MaDonNghiepVu,nghiepvu,sanpham_fk,kbh_fk,kho_fk,npp_fk,soluong,book,avai,ngaychungtu,soluongNV,bookNv,avaiNV)"+
				"select ?,?,sanpham_fk,kbh_fk,kho_fk,npp_fk,SOLUONG,BOOKED,AVAILABLE,?,round(?,1),round(?,1),round(?,1) from nhapp_kho where  npp_fk=? and kbh_fk=? and kho_fk=? and sanpham_fk=?  ";


				khodataquerylog = new Object[]{MaDonNghiepVu,nghiepvu,ngaychungtu,soluong,booked,available,npp_fk,kbh_fk,khott_fk,spId};
				/*query = " Update NHAPP_KHO set booked=round(isnull(booked,0),1)+ round("+booked+",1) , soluong =round(ISNULL(soluong,0),1) + round(" + soluong + ",1), " +
				" AVAILABLE = round(ISNULL(AVAILABLE,0),1) + round(" + available + ",1), GIAMUA="+(giaton >0?giaton:dongia)+"  "+
				"  where KBH_FK="+kbh_fk+" AND NPP_FK="+npp_fk+" AND  " +
				"  KHO_FK="+khott_fk+" and sanpham_fk= "+spId;*/
				double gia = giaton >0?giaton:dongia;
				query = " Update NHAPP_KHO set booked=round(isnull(booked,0),1)+ round(?,1) , soluong =round(ISNULL(soluong,0),1) + round(?,1), " +
				" AVAILABLE = round(ISNULL(AVAILABLE,0),1) + round(?,1), GIAMUA=?  "+
				"  where KBH_FK=? AND NPP_FK=? AND  " +
				"  KHO_FK=? and sanpham_fk=? ";
				khodata = new Object[]{booked,soluong,available,gia,kbh_fk,npp_fk,khott_fk,spId};

			}else{
				/*query=  " INSERT INTO NHAPP_KHO ( KHO_FK,SANPHAM_FK,NPP_FK,KBH_FK,GIAMUA,SOLUONG,BOOKED,AVAILABLE ) VALUES  " +
				" ("+khott_fk+","+ spId+","+npp_fk+","+kbh_fk+","+dongia+",round("+soluong+",1),round("+booked+",1),round("+available+",1))";
				 */
				query=  " INSERT INTO NHAPP_KHO ( KHO_FK,SANPHAM_FK,NPP_FK,KBH_FK,GIAMUA,SOLUONG,BOOKED,AVAILABLE ) VALUES  " +
				" (?,?,?,?,?,round(?,1),round(?,1),round(?,1))";
				khodata = new Object[]{khott_fk,spId,npp_fk,kbh_fk,dongia,soluong,booked,available};
				flag=1;
				/*querylog="insert into log_kho (MaDonNghiepVu,nghiepvu,sanpham_fk,kbh_fk,kho_fk,npp_fk,soluong,book,avai,ngaychungtu,soluongNV,bookNv,avaiNV)"+
				"select "+MaDonNghiepVu+",N'"+nghiepvu+"',sanpham_fk,kbh_fk,kho_fk,npp_fk,SOLUONG,BOOKED,AVAILABLE,'"+ngaychungtu+"',round(" + soluong + ",1),round("+booked+",1),round(" + available + ",1) from nhapp_kho where npp_fk='"+npp_fk+"' and kbh_fk='"+kbh_fk+"' and kho_fk='"+khott_fk+"' and sanpham_fk='"+spId+"'  ";
				 */
				querylog="insert into log_kho (MaDonNghiepVu,nghiepvu,sanpham_fk,kbh_fk,kho_fk,npp_fk,soluong,book,avai,ngaychungtu,soluongNV,bookNv,avaiNV)"+
				"select ?,?,sanpham_fk,kbh_fk,kho_fk,npp_fk,SOLUONG,BOOKED,AVAILABLE,?,round(?,1),round(?,1),round(?,1) from nhapp_kho where npp_fk=? and kbh_fk=? and kho_fk=? and sanpham_fk=?  ";
				khodataquerylog = new Object[]{MaDonNghiepVu,nghiepvu,ngaychungtu,soluong,booked,available,npp_fk,kbh_fk,khott_fk,spId};
				if(available < 0 && available_ton < (-1)*available ){
					return "Số lượng tồn hiện tại trong kho của sản phẩm : "+rsCheck.getString("ten") + "  ["+available_ton+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
				}

				if(soluong < 0 && soluongton <(-1)*soluong ){
					return "Số lượng tồn  trong kho của sản phẩm : "+rsCheck.getString("ten") + "  ["+soluongton+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
				}

			}
			rsCheck.close();
			if(flag==0)
			{
				int resultInt = db.updateQueryByPreparedStatement(querylog,khodataquerylog);
				if(resultInt < 1)
				{
					return  " Không thể cập nhật log_kho ";

				}
			}

			int resultInt = db.updateQueryByPreparedStatement(query,khodata);
			if(resultInt < 1)
			{
				return  " Không thể cập nhật NHAPP_KHO ";

			}
			if(flag==1)
			{
				resultInt = db.updateQueryByPreparedStatement(querylog,khodataquerylog);
				if(resultInt < 1)
				{
					return  " Không thể cập nhật log_kho " + querylog;

				}
			}

			/*/// check xuât nhập tồn có bị âm hay khong
			if(soluong<0){
				query=" select soluong ,(select ma from sanpham where pk_seq="+spId+") as ten from [ufn_XNT_TuNgay_DenNgay_V2018] "
						+ "("+npp_fk+","+kbh_fk+","+khott_fk+","+ spId+",'"+ngaychungtu+"','"+ngaychungtu+"')  a where a.soluong< 0 ";

				ResultSet rs=db.get(query);
				String msg1="";
				if(rs.next()){

					msg1="Sản phẩm : "+rs.getString("ten") +" Xuất nhập tồn tới thời điểm :"+ngaychungtu +" chỉ còn :"+ ((soluong*-1)+rs.getDouble("soluong"))+"";

				}
				rs.close();
				if(msg1.length() >0){
					return msg1;
				}

			}*/


			/*if(soluong != 0){
				query=	  " select   (select  sp.MA  from sanpham sp where sp.pk_seq="+ spId +") as MaSP, "
						+ " isnull( a.soluong,0)   "+
						  " from ufn_XNT_TuNgay_DenNgay_V2018( "+npp_fk+","+kbh_fk+","+khott_fk+","+ spId+",'"+ngaychungtu+"','2100-01-01') a  "+
						  " full outer join  "
						+ " ( select soluong from NHAPP_KHO  a where  a.kho_fk ="+khott_fk +" and a.npp_fk = "+npp_fk+"  "
								+ " and a.sanpham_fk = "+spId+"  and a.kbh_fk = "+kbh_fk+"  ) b   on 1=1  " +

						" where ( isnull( a.soluong,0)  <> ISNULL(b.soluong, 0)   ";
				System.out.println("____+_________"+query);
				ResultSet rsck=db.get(query);
				String msg1="";

				if(rsck.next()){

					msg1="Sản phẩm : "+rsck.getString("MaSP") +" Xuất nhập tồn lệch với tồn hiện tại ";

				}
				rsck.close();
				if(msg1.length() >0){
					return msg1;
				}

			}*/


		}catch(Exception er){
			er.printStackTrace();
			return  "không thể thực hiện cập nhật kho  Util.Nhap_Kho_Sp : "+er.getMessage();
		}
		return "";
	}


	public String Update_NPP_Kho_Sp_Chitiet(String ngaychungtu ,String nghiepvu ,Idbutils db, String khott_fk, String spId,String npp_fk,String kbh_fk,
			String ngaynhapkho,double soluong ,double booked,double available ,double tonthoidiem , double dongia) {
		try{

			int flag=0;
			String query="";
			String querylog="";
			if(ngaynhapkho==null || ngaynhapkho.length() != 10 ){
				return "Ngày nhập kho không hợp lệ, vui lòng chec lại ngày nhập kho";
			}

			query="  select tonthoidiem,sanpham_fk ,available,soluong,solo ,ngayhethan, sp.ma+ ' '+ sp.ten as ten , " +
			" ISNULL(KHO.GIAMUA,0) AS GIATON   " +
			"  from NHAPP_KHO_CHITIET kho " +
			"  inner join sanpham sp  on kho.sanpham_fk=sp.pk_seq  where KBH_FK =? AND NPP_FK=? AND  " +
			"  KHO_FK=? and sanpham_fk=?   and ngaynhapkho =? ";

			List<Object> dataSearch = new ArrayList<Object>(); 
			dataSearch.add(kbh_fk);
			dataSearch.add(npp_fk);
			dataSearch.add(khott_fk);
			dataSearch.add(spId);
			dataSearch.add(ngaynhapkho);
			double available_ton=0;
			double giaton=0;
			double soluongton=0;
			double tonthoidiem_=0;
			Object[] khodataquerylog;
			Object[] khodata;
			//System.out.println("[UTILITY KHO : QUERY LAY SAN PHAM KHO CHI TIET]" +query);
			ResultSet rsCheck = db.get_v2(query,dataSearch);
			dataSearch.clear();
			if(rsCheck.next()){
				tonthoidiem_= rsCheck.getDouble("tonthoidiem");
				soluongton=rsCheck.getDouble("soluong");
				available_ton=rsCheck.getDouble("available");
				giaton=rsCheck.getDouble("GIATON");

				if(available < 0 &&  Double.parseDouble(format_1.format(available_ton))  < (-1)*  Double.parseDouble(format_1.format(available))  ){
					//System.out.println("ton hien tai "+available_ton +" luong xuat ban "+ (-1)*available);
					return "Số lượng tồn hiện tại \n trong kho của sản phẩm : "+rsCheck.getString("ten") + " \n Ngày nhập kho: "+ngaynhapkho+" "+ 
					"  ["+available_ton+"]  \n không đủ để trừ kho,vui lòng kiểm tra lại xuất \n nhập tồn của sản phẩm này " ;
				}

				if(soluong < 0  &&  Double.parseDouble(format_1.format(soluongton)) <(-1)* Double.parseDouble(format_1.format(soluong))  ){
					return "Số lượng tồn  trong kho của sản phẩm : "+rsCheck.getString("ten") + " Ngày nhập kho: "+ngaynhapkho+"  "+ "  ["+soluongton+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
				}

				if(available <0 || soluong <0){
					int i= this.CompareDATE(ngaynhapkho,ngaychungtu);
					if(i>0){
						//System.out.println("ngay nhap kho "+ngaynhapkho +"ngay chung tu "+ngaychungtu);
						return "  Chỉ được xuất những lô có ngày nhập nhỏ hơn ngày làm nghiệp vụ:  sản phẩm  : "+rsCheck.getString("ten") + " - Ngày nhập kho : "+ ngaynhapkho +"  Không hợp lệ. " ;
					}
				}


				/*query = " Update NHAPP_KHO_CHITIET set booked=round(isnull(booked,0),2)+ round("+booked+",2) , soluong =round(ISNULL(soluong,0),2) + round(" + soluong + ",2), " +
				" AVAILABLE = round(ISNULL(AVAILABLE,0),2) + round(" + available + ",2) "+
				"  where KBH_FK="+kbh_fk+" AND NPP_FK="+npp_fk+" AND  " +
				"  KHO_FK="+khott_fk+" and sanpham_fk= "+spId +"   and ngaynhapkho= '"+ngaynhapkho+"'";
				System.out.println("updare kho la "+query);

				querylog="insert into log_kho_solo (nghiepvu,sanpham_fk,kbh_fk,kho_fk,npp_fk,ngaynhapkho,soluong,book,avai,ngaychungtu,soluongNV,bookNv,avaiNV)"+
				"select N'"+nghiepvu+"',sanpham_fk,kbh_fk,kho_fk,npp_fk,ngaynhapkho,SOLUONG,BOOKED,AVAILABLE,'"+ngaychungtu+"',round(" + soluong + ",2),round("+booked+",2),round(" + available + ",2) from nhapp_kho_chitiet where  ngaynhapkho='"+ngaynhapkho+"' and npp_fk='"+npp_fk+"' and kbh_fk='"+kbh_fk+"' and kho_fk='"+khott_fk+"' and sanpham_fk='"+spId+"'  ";
				 */
				query = " Update NHAPP_KHO_CHITIET set booked=round(isnull(booked,0),2)+ round(?,2) , soluong =round(ISNULL(soluong,0),2) + round(?,2), " +
				" AVAILABLE = round(ISNULL(AVAILABLE,0),2) + round(?,2) "+
				"  where KBH_FK=? AND NPP_FK=? AND  " +
				"  KHO_FK=? and sanpham_fk=?   and ngaynhapkho= ? ";
				//System.out.println("updare kho la "+query);

				querylog="insert into log_kho_solo (nghiepvu,sanpham_fk,kbh_fk,kho_fk,npp_fk,ngaynhapkho,soluong,book,avai,ngaychungtu,soluongNV,bookNv,avaiNV)"+
				"select ?,sanpham_fk,kbh_fk,kho_fk,npp_fk,ngaynhapkho,SOLUONG,BOOKED,AVAILABLE,?,round(?,2),round(?,2),round(?,2) from nhapp_kho_chitiet where  ngaynhapkho=? and npp_fk=? and kbh_fk=? and kho_fk=? and sanpham_fk=? ";

				khodataquerylog = new Object[]{nghiepvu,ngaychungtu,soluong,booked,available,ngaynhapkho,npp_fk,kbh_fk,khott_fk,spId};
				khodata = new Object[]{booked,soluong,available,kbh_fk,npp_fk,khott_fk,spId,ngaynhapkho};

			}else{
				query="SELECT  sp.ma+ ' '+ sp.ten as ten from sanpham sp where pk_seq="+spId;
				ResultSet rssp1=db.get(query);
				String tensp="";


				if(rssp1.next()){
					tensp=rssp1.getString("ten");
				}
				rssp1.close();


				if(available <0 || soluong <0){
					int i= this.CompareDATE(ngaynhapkho,ngaychungtu);
					if(i>0){
						return "  Chỉ được xuất những lô có ngày nhập nhỏ hơn ngày làm nghiệp vụ:  sản phẩm  : "+tensp + " - Ngày nhập kho : "+ ngaynhapkho +" Không hợp lệ. " ;
					}
				}
				flag=1;
				/*query=  " INSERT INTO NHAPP_KHO_CHITIET ( KHO_FK,SANPHAM_FK,NPP_FK,KBH_FK, NGAYNHAPKHO,GIAMUA,SOLUONG,BOOKED,AVAILABLE ,TONTHOIDIEM) VALUES  " +
				" ("+khott_fk+","+ spId+","+npp_fk+","+kbh_fk+", '"+ ngaynhapkho +"',"+dongia+",round("+soluong+",2),round("+booked+",2),round("+available+",2),round("+tonthoidiem+",2))";

				querylog="insert into log_kho_solo (nghiepvu,sanpham_fk,kbh_fk,kho_fk,npp_fk,ngaynhapkho,soluong,book,avai,ngaychungtu,soluongNV,bookNv,avaiNV)"+
				"select N'"+nghiepvu+"',sanpham_fk,kbh_fk,kho_fk,npp_fk,ngaynhapkho,SOLUONG,BOOKED,AVAILABLE,'"+ngaychungtu+"',round(" + soluong + ",2),round("+booked+",2),round(" + available + ",2) from nhapp_kho_chitiet where   ngaynhapkho='"+ngaynhapkho+"' and npp_fk='"+npp_fk+"' and kbh_fk='"+kbh_fk+"' and kho_fk='"+khott_fk+"' and sanpham_fk='"+spId+"'  ";
				 */
				query=  " INSERT INTO NHAPP_KHO_CHITIET ( KHO_FK,SANPHAM_FK,NPP_FK,KBH_FK, NGAYNHAPKHO,GIAMUA,SOLUONG,BOOKED,AVAILABLE ,TONTHOIDIEM) VALUES  " +
				" (?,?,?,?, ?,?,round(?,2),round(?,2),round(?,2),round(?,2))";

				querylog="insert into log_kho_solo (nghiepvu,sanpham_fk,kbh_fk,kho_fk,npp_fk,ngaynhapkho,soluong,book,avai,ngaychungtu,soluongNV,bookNv,avaiNV)"+
				"select ?,sanpham_fk,kbh_fk,kho_fk,npp_fk,ngaynhapkho,SOLUONG,BOOKED,AVAILABLE,?,round(?,2),round(?,2),round(?,2) from nhapp_kho_chitiet where   ngaynhapkho=? and npp_fk=? and kbh_fk=? and kho_fk=? and sanpham_fk=?  ";

				khodataquerylog = new Object[]{nghiepvu,ngaychungtu,soluong,booked,available,ngaynhapkho,npp_fk,kbh_fk,khott_fk,spId};
				khodata = new Object[]{khott_fk ,  spId , npp_fk , kbh_fk ,   ngaynhapkho  , dongia , soluong , booked , available , tonthoidiem};


				if(available < 0 && available_ton < (-1)*available ){
					return "Số lượng tồn hiện tại trong kho của sản phẩm : "+tensp+ "Ngày nhập kho: "+ngaynhapkho+" "+   "  ["+available_ton+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
				}

				if(soluong < 0 && soluongton <(-1)*soluong ){
					return "Số lượng tồn  trong kho của sản phẩm : "+tensp+ "Ngày nhập kho: "+ngaynhapkho+"  "+  "  ["+soluongton+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
				}

			}
			rsCheck.close();
			//System.out.println( nghiepvu+ " CAP NHAT KHO : "+query);

			if(flag==0)
			{	
				int resultInt = db.updateQueryByPreparedStatement(querylog,khodataquerylog);
				if(resultInt < 1)
				{
					return  " Không thể cập nhật log_kho_solo " + querylog;

				}

			}
			int resultInt = db.updateQueryByPreparedStatement(query,khodata);
			if(resultInt < 1)
			{
				return  " Không thể cập nhật NHAPP_KHO_CHITIET " + query;

			}

			if(flag==1)
			{	
				resultInt = db.updateQueryByPreparedStatement(querylog,khodataquerylog);
				if(resultInt != 1)
				{
					return  " Không thể cập nhật log_kho_solo " + querylog;

				}

			}


		}catch(Exception er){
			er.printStackTrace();
			return  "không thể thực hiện cập nhật kho  Util.Nhap_Kho_Sp : "+er.getMessage();
		}
		return "";
	}


	public String Update_NPP_Kho(String ngaychungtu ,String MaDonNghiepVu,String nghiepvu ,Idbutils db, String khott_fk, String spId,String npp_fk,String kbh_fk,
			String ngaynhapkho,double soluong ,double booked,double available ,double tonthoidiem , double dongia) {
		try{
			String kq=Update_NPP_Kho_Sp( ngaychungtu, MaDonNghiepVu , nghiepvu , db,  khott_fk,  spId, npp_fk, kbh_fk, soluong , booked, available ,0);
			if(kq.trim().length()>0)
			{
				return kq;
			}
			kq=Update_NPP_Kho_Sp_Chitiet( ngaychungtu , nghiepvu , db,  khott_fk,  spId, npp_fk, kbh_fk,
					ngaynhapkho, soluong , booked, available , tonthoidiem ,  dongia);
			if(kq.trim().length()>0)
			{
				return kq;
			}
		}catch(Exception er){
			er.printStackTrace();
			return  "không thể thực hiện cập nhật kho  Util.Nhap_Kho_Sp : "+er.getMessage();
		}
		return "";
	}



	public int CompareDATE(String _date1, String _date2)
	{
		try
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			//Date date1 = sdf.parse("2014-10-01");
			//Date date2 = sdf.parse("2014-10-01");

			Date date1 = sdf.parse(_date1);
			Date date2 = sdf.parse(_date2);

			//System.out.println(sdf.format(date1));
			//System.out.println(sdf.format(date2));

			return date1.compareTo(date2);
		}
		catch (Exception e) {
			return 0;
		}
	}

	public static Double parseDouble(String s)
	{
		try{
			return Double.parseDouble(s);
		}catch (Exception e) {
			return 0.0;
		}
	}

	public void zipDirectory(File inputDir, File outputZipFile) {
		// Tạo thư mục cha cho file đầu ra (output file).
		outputZipFile.getParentFile().mkdirs();

		String inputDirPath = inputDir.getAbsolutePath();
		byte[] buffer = new byte[1024];

		FileOutputStream fileOs = null;
		ZipOutputStream zipOs = null;
		try {

			List<File> allFiles = this.listChildFiles(inputDir);

			// Tạo đối tượng ZipOutputStream để ghi file zip.
			fileOs = new FileOutputStream(outputZipFile);
			// 
			zipOs = new ZipOutputStream(fileOs);
			for (File file : allFiles) {
				String filePath = file.getAbsolutePath();

				//System.out.println("Zipping " + filePath);
				// entryName: is a relative path.
				String entryName = filePath.substring(inputDirPath.length() + 1);

				ZipEntry ze = new ZipEntry(entryName);
				// Thêm entry vào file zip.
				zipOs.putNextEntry(ze);
				// Đọc dữ liệu của file và ghi vào ZipOutputStream.
				FileInputStream fileIs = new FileInputStream(filePath);

				int len;
				while ((len = fileIs.read(buffer)) > 0) {
					zipOs.write(buffer, 0, len);
				}
				fileIs.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeQuite(zipOs);
			closeQuite(fileOs);
		}

	}
	private void closeQuite(OutputStream out) {
		try {
			out.close();
		} catch (Exception e) {
		}
	}
	private List<File> listChildFiles(File dir) throws IOException {
		List<File> allFiles = new ArrayList<File>();

		File[] childFiles = dir.listFiles();
		for (File file : childFiles) {
			if (file.isFile()) {
				allFiles.add(file);
			} else {
				List<File> files = this.listChildFiles(file);
				allFiles.addAll(files);
			}
		}
		return allFiles;
	}

	public String getngayhoadon(String userid,Idbutils db1,String  ngaydonhang ,String khachhangid,int loaichot)
	{
		return Utility.getNgayHienTai();
	}

	public static String CheckVat(Idbutils db ,String tableName,String columnVat, String columnId, String Id )
	{
		return "";
		/*try
		{
			String query =  " select count(distinct "+columnVat+")kq from " + tableName +
			" where "+columnId+" = "+Id+"  ";
			ResultSet rs = db.get(query);
			if(rs.next())
			{
				if( rs.getInt("kq") > 1 )
				{
					rs.close();
					return "Lỗi tồn tại 2 thuế xuất trong đơn hàng";
				}
				else
				{
					rs.close();
					return "";
				}
			}
			else
			{
				rs.close();
				return "Không lấy được thông tin thuế xuất ĐH ";
			}

		}catch(Exception e)
		{
			return " Lỗi ngoại lệ khi kiểm tra vat ("+e.getMessage()+") ";
		}*/
	}

	public String Check_Kho_Tong_VS_KhoCT_TT(String nppId, dbutils db)
	{
		String query =  " select count(*) as sodong "+
		" from  "+ 
		" ( " +
		"	 select npp_fk, kbh_fk, kho_fk, sanpham_fk, sum(available) as available, sum(soluong) as soluong, sum(booked) as booked_ct  "+
		"	 from nhapp_kho_chitiet where npp_fk = '" + nppId + "' "+
		"	 group by kbh_fk, npp_fk, kho_fk, sanpham_fk	  "+
		" ) " +
		" CT full outer join nhapp_kho total on total.npp_fk=ct.npp_fk and total.kbh_fk=ct.kbh_fk  "+
		"		and total.sanpham_fk=ct.sanpham_fk and total.kho_fk=ct.kho_fk   "+
		" where    "+
		"		(round( isnull(ct.available,0),1) + round( isnull(ct.booked_ct,0),1) != round(isnull(total.available,0),1) + round(isnull(total.booked ,0),1)  "+ 
		"			or round(isnull(total.soluong,0),0) != round(isnull(ct.soluong,0),0)  "+ 
		"		) and  isnull(total.npp_fk, ct.npp_fk) = '" + nppId + "' ";

		//System.out.println("Check_Kho_Tong_VS_KhoCT " + query);
		String msg = "";
		ResultSet rs = db.get(query);
		try
		{
			while(rs.next())
			{
				if(rs.getInt("soDONG") > 0 )
				{
					//System.out.println();
					//FIX tự động nếu có bị lệch tổng và chi tiết
					/*FixData fixed = new FixData();
					String error = fixed.ProcessDATA(nppId, "");

					if(error.trim().length() > 0) //Không thể tự động FIX được
					{
						rs.close();
						msg = "Lỗi phát sinh do lệch Số lượng của sản phẩm " + msg ;

						//SEND MAIL CANH BAO
						SendMail mail = new SendMail();

						String tb = "1.Hệ thống đã chạy chế độ tự sửa lỗi tồn kho. Nhưng gặp lỗi khi chạy của NPP ( " + nppId + " ). Vui lòng kiểm tra và xử lý gấp.";
						mail.postMailHTML("vudq@geso.us,xuantvt@geso.us,taiba@geso.us,hienttd@geso.us", "haind@geso.us,luonghv@geso.us", "TraphacoDMS chạy tồn kho tự động ", tb);

						return msg;
					}*/


					msg += "Lỗi phát sinh do lệch Số lượng ";//của sản phẩm  "+rs.getInt("SANPHAM_FK");
				}
			}
			rs.close();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			return "Lỗi phát sinh khi check Tồn Kho";
		}

		return msg;
	}

	public String Quyen_Ddkd(String userId) {
		String sql = "( select PK_SEQ from DAIDIENKINHDOANH where npp_fk in  ( select npp_fk from phamvihoatdong where nhanvien_fk ='"
			+ userId + "'      )) ";
		return sql;
	}
	public static boolean CheckSessionUser(HttpSession session, HttpServletRequest request,HttpServletResponse response,String userId) throws IOException
	{
		String ssUserId = (String)session.getAttribute("userId");
		return ssUserId == null || !ssUserId.equals(userId);
	}
	public static boolean CheckSessionUser(HttpSession session, HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		String ssUserId = (String)session.getAttribute("userId");
		String userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
		return ssUserId == null || !ssUserId.equals(userId);
	}
	public static void RedireactLogin(HttpSession session, HttpServletRequest request,HttpServletResponse response) throws IOException
	{
		session.setAttribute("userId", null);
		String nextJSP = request.getServletContext().getInitParameter("RedirectNoScript") ;
		session.setAttribute("logoutMsg", "Lỗi dữ liệu");
		response.sendRedirect(nextJSP);
	}

	public static boolean CheckRuleUser(HttpSession session, HttpServletRequest request,HttpServletResponse response, String svl, String param, int quyen ) throws IOException
	{
		String userId = (String)session.getAttribute("userId");		
		int[] q = Utility.Getquyen(svl, param,userId);
		return q[quyen]!=1;
	}

	public static boolean KiemTra_PK_SEQ_HopLe(String id, String table, Idbutils db)
	{
		try
		{
			String query =  " select count(*)x from "+table+"  where pk_seq = ?  ";
			Object[] data = { id };			
			ResultSet rs = db.getByPreparedStatement(query, data);
			rs.next();
			if(rs.getInt("x") ==1 )
			{
				rs.close();
				return true;
			}
			return false;
		}
		catch (Exception e) {
			return false;
		}
	}

	//Script chạy kho

	public static String replaceSupser(String in){
		try {
			return URLEncoder.encode(in, "UTF-8")
			.replaceAll("\\+", "%20")
			.replaceAll("\\%21", "!")
			.replaceAll("\\%27", "'")
			.replaceAll("\\%28", "(")
			.replaceAll("\\%29", ")")
			.replaceAll("\\%7E", "~");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return in;
	}

	public static String dongMa_ver1(String ma) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

		SecretKeySpec skeySpec = new SecretKeySpec(GlobalValue.SECRET_KEY.getBytes(), "DES");

		Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5PADDING");
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		byte[] byteEncrypted = cipher.doFinal(ma.getBytes());
		String encrypted =  DatatypeConverter.printBase64Binary(byteEncrypted);
		return   replaceSupser(encrypted);
	}

	public static String giaiMa_ver1(String ma) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

		SecretKeySpec skeySpec = new SecretKeySpec(GlobalValue.SECRET_KEY.getBytes(), "DES");		    
		Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5PADDING");
		cipher.init(Cipher.DECRYPT_MODE, skeySpec);
		byte[] byteDecrypted = cipher.doFinal(DatatypeConverter.parseBase64Binary(ma));
		String decrypted = new String(byteDecrypted);

		return decrypted;
	}

	private Object[] appendObjectArrayValue(Object[] obj, Object[] newObj) {
		ArrayList<Object> temp = new ArrayList<Object>(Arrays.asList(obj));
		for (int i = 0; i < newObj.length; i++) {
			temp.add(newObj[i]);
		}
		return temp.toArray();
	}

	//userId=1&delete=1&vungId=1 
	public String getParameter(String querystring, String string) {
		if (querystring != null){
			if (querystring.contains("&")){
				String [] l = querystring.split("&");

				for(String s : l){
					if(s.contains(string)){
						return s.split("=")[1];
					}
				}
			}
		}

		return "";
	}

	public static String PhanQuyenNPP(String userId)
	{
		String sql = "\n select pq_npp.PK_SEQ    " + 
		"\n from NHAPHANPHOI pq_npp " + 
		"\n where exists" + 
		"\n (	select 1  from nhanvien pb_npp_nv where pb_npp_nv.pk_seq = " + userId + 
		"\n 		and (   " + 
		"\n 					( isnull(pb_npp_nv.phanloai,2) = 2 and exists (select 1 from phamvihoatdong pvhd where pvhd.npp_fk = pq_npp.PK_SEQ  and pvhd.Nhanvien_fk = pb_npp_nv.pk_seq    )   )" + 
		"\n 				or ( isnull(pb_npp_nv.phanloai,2) = 1 and pb_npp_nv.CONVSITECODE = pq_npp.SITECODE   )" + 
		"\n 			)" + 
		"\n ) " ;

		return sql;
	}
	public static String PhanQuyenKBH(String userId)
	{
		return 	"   select Kenh_fk from NHANVIEN_KENH   where nhanvien_fk = " + userId ;
		

	}
	
	public static String PhanQuyenDDKD(String userId)
	{
		String sql =  	 "\n select yddkd.PK_SEQ    " + 
		"\n 						  from DAIDIENKINHDOANH yddkd   " + 
		"\n 						  where exists    " + 
		"\n 						  (   " + 
		"\n 						  	select 1 from nhanvien xnv where xnv.pk_seq =   " + userId + 
		"\n 						  		and (  	   (  isnull(xnv.loai,0) not in (3) and  yddkd.NPP_FK in ( "+PhanQuyenNPP(userId)+"  )          )	 	    " + 
		"\n 						  				or (  isnull(xnv.loai,0) = 3 and  exists (select 1 from ddkd_gsbh where gsbh_fk = xnv.gsbh_fk and ddkd_fk = yddkd.pk_seq   )        )	   " + 		
		"\n 						  		)   " + 
		"\n 						  		   " + 
		"\n 						  )  " ;

		return sql;
	}

	public static String PhanQuyenKH(String userId)
	{
		return   "\n select xkh.PK_SEQ   " + 
		"\n from KhachHang xkh  " + 
		"\n where exists   " + 
		"\n (  " + 
		"\n 	select 1 from nhanvien xnkh where xnkh.pk_seq = " + userId + 
		"\n 		and (  	   (  isnull(xnkh.loai,0) not in (3) and  xkh.NPP_FK   in ( "+PhanQuyenNPP(userId)+"  )        )	  " + 
		"\n 				 or (  isnull(xnkh.loai,0) in(3) and	exists ( select 1 from KHACHHANG_TUYENBH where khachhang_fk = xkh.PK_SEQ and TBH_FK in ( select PK_SEQ from TUYENBANHANG where NPP_FK = xkh.NPP_FK and DDKD_FK in ("+PhanQuyenDDKD(userId)+")   )   )  )    " + 
		"\n 				   " + 
		"\n 			)  " + 
		"\n )  ";


	}

	public static PrivateKey getPrivateKey() throws Exception {
		byte[] keyBytes = Files.readAllBytes(new File(GenerateKeys.PRIVATE_KEY_FILE).toPath());
		PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		return kf.generatePrivate(spec);
	}

	public static PublicKey getPublicKey() throws Exception {
		byte[] keyBytes = Files.readAllBytes(new File(GenerateKeys.PUBLIC_KEY_FILE).toPath());
		X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		return kf.generatePublic(spec);
	}

	public static String dongMa_ver2(String ma) throws Exception {

		PublicKey publicKey = getPublicKey();
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);

		byte[] byteEncrypted = cipher.doFinal(ma.getBytes());
		String encrypted = DatatypeConverter.printBase64Binary(byteEncrypted);

		return replaceSupser(encrypted);
	}

	public static String giaiMa_ver2(String ma) throws Exception {

		PrivateKey privateKey = getPrivateKey();
		Cipher cipher = Cipher.getInstance("RSA");

		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		byte[] byteDecrypted = cipher.doFinal(DatatypeConverter.parseBase64Binary(ma));
		String decrypted = new String(byteDecrypted);

		return decrypted;
	}

	public static String validatePassword(String password){


		if(password.trim().length() != password.length()) return "Mật khẩu không được có khoảng trắng, tab, xuống dòng";
		if(password.replace(" ","").replace("\n","").replace("\t","").length() != password.length() ) return "Mật khẩu không được có khoảng trắng, tab, xuống dòng";	
		if(password.contains("@")) return "Mật khẩu không được tồn tại ký tự @ ";

		String regexp = "^(?=.*[0-9])(?=.*[!#$&*])(?=.*[a-z])(?=.*[A-Z]).{8,20}$";
		if(!password.matches(regexp))
			return "Mật khẩu phải từ 8 đến 20 ký tự. Có ít nhất 1 ký tự đặc biệt, 1 ký tự hoa, 1 ký tự thường, một ký tự số";
		return "";


	}
	public static String dongMa(String ma) {
		if (GlobalValue.loaiMaHoa.equals("1")) {
			try {
				return dongMa_ver1(ma);
			} catch (InvalidKeyException e) {

				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {

				e.printStackTrace();
			} catch (NoSuchPaddingException e) {

				e.printStackTrace();
			} catch (IllegalBlockSizeException e) {

				e.printStackTrace();
			} catch (BadPaddingException e) {

				e.printStackTrace();
			}
		} else if (GlobalValue.loaiMaHoa.equals("2")) {
			try {
				return dongMa_ver2(ma);
			} catch (Exception e) {

				e.printStackTrace();
			}
		}
		return "";
	}

	public static String giaiMa(String ma) {
		if (GlobalValue.loaiMaHoa.equals("1")) {
			try {
				return giaiMa_ver1(ma);
			} catch (InvalidKeyException e) {
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (NoSuchPaddingException e) {
				e.printStackTrace();
			} catch (IllegalBlockSizeException e) {
				e.printStackTrace();
			} catch (BadPaddingException e) {				
				e.printStackTrace();
			}
		} 
		else if (GlobalValue.loaiMaHoa.equals("2")) {
			try {
				return giaiMa_ver2(ma);
			} catch (Exception e) {

				e.printStackTrace();
			}
		}
		return "";
	}

	public static String dongMa_ver2_noreplace(String ma) throws Exception {

		PublicKey publicKey = getPublicKey();
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);

		byte[] byteEncrypted = cipher.doFinal(ma.getBytes());
		String encrypted = DatatypeConverter.printBase64Binary(byteEncrypted);

		return encrypted;
	}

	public static String giaiMa_ver2_noreplace(String ma) throws Exception {

		PrivateKey privateKey = getPrivateKey();
		Cipher cipher = Cipher.getInstance("RSA");

		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		byte[] byteDecrypted = cipher.doFinal(DatatypeConverter.parseBase64Binary(ma));
		String decrypted = new String(byteDecrypted);

		return decrypted;
	}

	public static String dongMa_ver1_noreplace(String ma) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

		SecretKeySpec skeySpec = new SecretKeySpec(GlobalValue.SECRET_KEY.getBytes(), "DES");

		Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5PADDING");
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		byte[] byteEncrypted = cipher.doFinal(ma.getBytes());
		String encrypted =  DatatypeConverter.printBase64Binary(byteEncrypted);
		return encrypted;
	}

	public static String giaiMa_ver1_noreplace(String ma) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

		SecretKeySpec skeySpec = new SecretKeySpec(GlobalValue.SECRET_KEY.getBytes(), "DES");

		Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5PADDING");
		cipher.init(Cipher.DECRYPT_MODE, skeySpec);
		byte[] byteDecrypted = cipher.doFinal(DatatypeConverter.parseBase64Binary(ma));
		String decrypted = new String(byteDecrypted);

		return decrypted;
	}

	public static boolean isValid(Object obj) {
		try {
			if (obj == null) {
				return false;
			}
			else {
				if (obj instanceof String) {
					String temp = (String) obj;
					if (temp != null && temp.trim().length() > 0) 
						return true;
					else 
						return false;
				}
				else if (obj instanceof String[]) {
					String[] temp = (String[]) obj;
					if (temp != null && temp.length > 0) 
						return true;
					else 
						return false;
				}
				else if (obj instanceof int[]) {
					int[] temp = (int[]) obj;
					if (temp != null && temp.length > 0) 
						return true;
					else 
						return false;
				}
				else if (obj instanceof double[]) {
					double[] temp = (double[]) obj;
					if (temp != null && temp.length > 0) 
						return true;
					else 
						return false;
				}
				else if (obj instanceof float[]) {
					float[] temp = (float[]) obj;
					if (temp != null && temp.length > 0) 
						return true;
					else                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
						return false;
				}
				else if (obj instanceof ArrayList<?>) {
					ArrayList<?> temp = (ArrayList<?>) obj;
					if (temp != null && temp.size() > 0) 
						return true;
					else 
						return false;
				}
				else if (obj instanceof Hashtable<?, ?>) {
					Hashtable<?, ?> temp = (Hashtable<?, ?>) obj;
					if (temp != null && temp.size() > 0) 
						return true;
					else 
						return false;
				}
				else {
					return true;
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static Integer parseInt(String s)
	{
		try{
			return Integer.parseInt(s);
		}catch (Exception e) {
			return 0;
		}
	}

	public static String sendPost( String url ,String urlParameters ) throws Exception {

		byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setDoOutput(true);
		con.setRequestMethod( "POST" );
		con.setRequestProperty("User-Agent", "Java client");
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		con.setRequestProperty( "charset", "utf-8");
		con.setRequestProperty("Content-Length", Integer.toString(postData.length));

		//System.out.println("\nSending 'POST' request to URL : " + url);
		//System.out.println("Post parameters : " + urlParameters);


		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(con.getOutputStream(), "UTF-8"));
		bw.write(urlParameters);
		bw.flush();
		bw.close();

		StringBuilder content;
		BufferedReader in = new BufferedReader(
				new InputStreamReader(con.getInputStream()));
		String line;
		content = new StringBuilder();

		while ((line = in.readLine()) != null) {
			content.append(line);
			content.append(System.lineSeparator());
		}

		return content.toString();
	}



	public static String rollback_throw_exception(Idbutils db)
	{
		try {
			db.getConnection().rollback();
			db.getConnection().setAutoCommit(true);
			return "";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Exception:"+ e.getMessage();
		}
	}
	public static String rollback_and_shutdown(Idbutils db)
	{
		try {
			db.getConnection().rollback();
			db.shutDown();
			return "";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Exception:"+ e.getMessage();
		}
	}

	public static String commit_and_shutdown(Idbutils db)
	{
		try {
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();
			return "";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Exception:"+ e.getMessage();
		}
	}
	public static void main(String[] args) {
		dbutils db = new dbutils();
		ResultSet rs = null;
		try {
			boolean pass = true;
			String nppId = "";
			String ngay = "";
			String query = "\n select npp.pk_seq, max(x.NGAYKS) as ngayks, convert(varchar(10),DATEADD(day,1,max(x.NGAYKS)),121) ngay " +
			"\n from NHAPHANPHOI npp " +
			"\n inner join KHOASONGAY x on x.NPP_FK= npp.pk_seq " +
			"\n where npp.TRANGTHAI = 1 and npp.pk_seq =  " +
			"\n group by npp.pk_seq ";
			rs = db.get(query);
			while (rs.next()) {
				pass = true;
				db.getConnection().setAutoCommit(false);				

				nppId = rs.getString("pk_seq");
				ngay = rs.getString("ngay");						

				query = "\n insert NHAPP_KHO_CHITIET(KHO_FK, NPP_FK, SANPHAM_FK, KBH_FK, SOLO, " +
				"\n SOLUONG, BOOKED, AVAILABLE, GIAMUA, NGAYNHAPKHO, Tonthoidiem) " +
				"\n select KHO_FK, NPP_FK, SANPHAM_FK, KBH_FK, null, 0, 0, 0, 0, NGAYNHAPKHO, 0 " +
				"\n from ufn_XNT_TuNgay_DenNgay_FULL_Chitiet_New(" + nppId + ", '" + ngay + "', '') a  " +
				"\n where not exists " +
				"\n ( " +
				"\n     select 1 from NHAPP_KHO_CHITIET b " +
				"\n     where a.npp_fk = b.NPP_FK and a.sanpham_fk = b.SANPHAM_FK " +
				"\n     and a.kbh_fk = b.KBH_FK " +
				"\n     and a.kho_fk = b.KHO_FK and a.NGAYNHAPKHO = b.NGAYNHAPKHO " +
				"\n ) ";
				if (!db.update(query)) {
					pass = false;
				}


				/*query = "\n select distinct '" + ngay + "', 100002, " + nppId + ", 1, "+nppId+", 100001, a.kbh_fk, 0, a.kho_fk, 100002, N'Fix âm XNT', getdate(), npp.tructhuoc_fk, 0 " + 
				"\n from ufn_XNT_TuNgay_DenNgay_FULL_Chitiet_New(" + nppId + ", '" + ngay + "', '') a " +
				"\n inner join nhaphanphoi npp on npp.pk_seq = a.npp_fk " +
				"\n right join NHAPP_KHO_chitiet b on a.kho_fk = b.KHO_FK " +
				"\n 	and a.npp_fk = b.NPP_FK and a.sanpham_fk = b.SANPHAM_FK and a.kbh_fk = b.KBH_FK and a.ngaynhapkho = b.ngaynhapkho " +
				"\n where (isnull(a.cuoiky,0) <> ISNULL(b.soluong, 0) or isnull(a.cuoiky,0) <> ISNULL(b.booked,0)+ISNULL(b.available,0)) " +
				"\n and b.npp_fk = " + nppId;
				ResultSet  rs1 = db.get(query); 
				if(rs1.next())
				{
					System.out.println("query: "  + query);
				}*/


				query = "\n select distinct '" + ngay + "', 100001, " + nppId + ", 1, "+nppId+", 100001, a.kbh_fk, 0, a.kho_fk, 100002, N'Fix âm XNT', getdate(), npp.tructhuoc_fk, 0 " + 
						"\n from ufn_XNT_TuNgay_DenNgay_FULL_Chitiet_New(" + nppId + ", '" + ngay + "', '') a " +
						"\n inner join nhaphanphoi npp on npp.pk_seq = a.npp_fk " +
						"\n right join NHAPP_KHO_chitiet b on a.kho_fk = b.KHO_FK " +
						"\n 	and a.npp_fk = b.NPP_FK and a.sanpham_fk = b.SANPHAM_FK and a.kbh_fk = b.KBH_FK and a.ngaynhapkho = b.ngaynhapkho " +
						"\n where (isnull(a.cuoiky,0) <> ISNULL(b.soluong, 0) or isnull(a.cuoiky,0) <> ISNULL(b.booked,0)+ISNULL(b.available,0)) " +
						"\n and b.npp_fk = " + nppId + "  and isnull(a.cuoiky,0) < 0  "; // 
				ResultSet rs1 = db.get(query); 
				while (rs1.next()) {
					String kbh_fk = rs1.getString("kbh_fk");
					String kho_fk = rs1.getString("kho_fk");

					query = "\n insert DIEUCHINHTONKHO(ngaydc, nguoitao, nguoisua, trangthai, npp_fk, dvkd_fk, kbh_fk, tongtien, kho_fk, nguoiduyet, lydodc, nppdachot, modified_date) " + 
					"\n select distinct '" + ngay + "', 100001, 100001, 1, " + nppId + ", 100001, a.kbh_fk, 0, a.kho_fk, 100001 ,  N' Fix lệch XNT ngay " + getNgayHienTai()+" Huy lam ', 1, getdate() " + 
					"\n from ufn_XNT_TuNgay_DenNgay_FULL_Chitiet_New(" + nppId + ", '" + ngay + "', '') a " +
					"\n right join NHAPP_KHO_chitiet b on a.kho_fk = b.KHO_FK " +
					"\n 	and a.npp_fk = b.NPP_FK and a.sanpham_fk = b.SANPHAM_FK and a.kbh_fk = b.KBH_FK and a.ngaynhapkho = b.ngaynhapkho " +
					"\n inner join nhaphanphoi npp on npp.pk_seq = a.npp_fk " + 
					"\n where isnull(a.cuoiky,0) <> ISNULL(b.soluong, 0)" + // and isnull(a.cuoiky,0) < 0 
					"\n and a.kbh_fk = " + kbh_fk + " and a.kho_fk = " + kho_fk;

					if (db.updateReturnInt(query) != 1) {
						pass = false;
					}

					query = "\n insert DIEUCHINHTONKHO_sp(DIEUCHINHTONKHO_fk, sanpham_fk, dieuchinh, donvi, giamua, thanhtien, tonhientai, tonmoi, ngaynhapkho) " +
					"\n select distinct SCOPE_IDENTITY() id, a.sanpham_fk,  b.soluong - isnull(a.cuoiky,0), null, -404, -404, 0, 0, a.ngaynhapkho " +
					"\n from ufn_XNT_TuNgay_DenNgay_FULL_Chitiet_New(" + nppId + ", '" + ngay + "', '') a " +
					"\n right join NHAPP_KHO_chitiet b on a.kho_fk = b.KHO_FK " +
					"\n 	and a.npp_fk = b.NPP_FK and a.sanpham_fk = b.SANPHAM_FK and a.kbh_fk = b.KBH_FK and a.ngaynhapkho = b.ngaynhapkho " +
					"\n inner join nhaphanphoi npp on npp.pk_seq = a.npp_fk " +
					"\n where isnull(a.cuoiky,0) <> ISNULL(b.soluong, 0)  and a.kbh_fk = " + kbh_fk + " and a.kho_fk = " + kho_fk; // and isnull(a.cuoiky,0) < 0
					System.out.println("DIEUCHINHTONKHO_sp: " + query);
					if (!db.update(query)) {
						pass = false;
					}					
				}
				rs1.close();

				query = "\n update b set b.soluong = isnull(a.cuoiky,0) " +
				"\n from ufn_XNT_TuNgay_DenNgay_FULL_Chitiet_New(" + nppId + ", '" + ngay + "', '') a " +
				"\n right join NHAPP_KHO_chitiet b on a.kho_fk = b.KHO_FK " +
				"\n 	and a.npp_fk = b.NPP_FK and a.sanpham_fk = b.SANPHAM_FK and a.kbh_fk = b.KBH_FK and a.ngaynhapkho = b.ngaynhapkho " +
				"\n where (isnull(a.cuoiky,0) <> ISNULL(b.soluong, 0) or isnull(a.cuoiky,0) <> ISNULL(b.booked,0)+ISNULL(b.available,0)) " +
				"\n and b.NPP_FK = " +nppId;
				if (!db.update(query)) {
					pass = false;
				}	

				query = "\n update b set b.soluong = isnull(a.cuoiky,0) " +
						"\n from ufn_XNT_TuNgay_DenNgay_FULL_New(" + nppId + ", '" + ngay + "', '') a " +
						"\n right join NHAPP_KHO b on a.kho_fk = b.KHO_FK " +
						"\n 	and a.npp_fk = b.NPP_FK and a.sanpham_fk = b.SANPHAM_FK and a.kbh_fk = b.KBH_FK " +
						"\n where (isnull(a.cuoiky,0) <> ISNULL(b.soluong, 0) or isnull(a.cuoiky,0) <> ISNULL(b.booked,0)+ISNULL(b.available,0)) " + 
						"\n and b.NPP_FK = " + nppId;
				if (!db.update(query)) {
					pass = false;
				}					



				query = "\n update NHAPP_KHO set AVAILABLE = SOLUONG - BOOKED where NPP_FK = " + nppId;
				if (!db.update(query)) {
					pass = false;
				}

				query = "\n update NHAPP_KHO_CHITIET set AVAILABLE = SOLUONG - BOOKED where NPP_FK = " + nppId;
				if (!db.update(query)) {
					pass = false;
				}				

				if (pass) {
					db.getConnection().commit();
					db.getConnection().setAutoCommit(true);
				}
				else {
					db.getConnection().rollback();
					db.getConnection().setAutoCommit(true);
				}
				System.out.println("nppId: " + nppId + " -- pass: " + pass);
			}
			rs.close();		

			System.out.println("Done!");
		}
		catch (Exception e) {
			try {
				db.getConnection().rollback();
				db.shutDown();
			}
			catch (Exception e1) {
				//
			}
			e.printStackTrace();
		}		
		finally {
			if (db != null) {
				db.shutDown();
			}
		}
	}

	/*public static void main(String[] args) {
		String str = "Á";
		Utility  util = new Utility();
		str = util.replaceAEIOU(str);
		System.out.println(str);
	}*/

	public static String[] BaoCao_log (Idbutils db , String baocaoId, String query ,String userId, String _link,String _fileName,String _path,String cot_bat_dau  )
	{
		try{

			db.getConnection().setAutoCommit(false);
			String sql = " insert [BaoCao_log](baocao_fk,[query],[trangthai],[userId],[_link],_fileName,[_path],cot_bat_dau,[thoidiem]) " +
			"  select ?,?,0,?,?,?,? ,?,getdate() where not exists ( select 1 from BaoCao_log x where x.trangthai in (0,1,2) and x.userId  = "+userId+" ) ";

			Object[] _data = {baocaoId,query,userId,_link,_fileName ,_path ,cot_bat_dau};			
			int sodong = db.updateQueryByPreparedStatement(sql, _data);
			if( sodong == 0)
			{
				db.getConnection().rollback();
				return new String[] { "Báo cáo tương tự đang được xử lý , vui lòng đợi xử lý xong trước" ,"" } ;
			}
			String pk_seq = db.getPk_seq();
			db.getConnection().commit();			
			return new String[] { "1" ,pk_seq } ;

		}
		catch(Exception e)
		{

			Utility.rollback_throw_exception(db);
			e.printStackTrace();
			return new String[] {  "Exception:" + e.getMessage()  ,"" } ;
		}
	}
	
	public static int KiemTraTransaction(String tableName,String columnName, String id, Idbutils db) throws Exception
	{
		String query = " select isnull(dangcapnhat,0) dangcapnhat from "+tableName+" where "+columnName+" = "+id;
		ResultSet rsCheck = db.get(query);
		rsCheck.next();
		String dangcapnhat = rsCheck.getString("dangcapnhat");
		rsCheck.close();
		
		query = "\n update "+tableName+" set dangcapnhat =  isnull(dangcapnhat,0) + 1 " +
		"\n where "+columnName+" = "+id+" and isnull(dangcapnhat,0) = " + dangcapnhat;
		
		return db.updateReturnInt(query);
	}
	
	
	public static String get_DDKD_from_NPP(String nppStr)
	{
		return " select pk_seq from  daidienkinhdoanh where npp_fk in ("+nppStr+") ";
	}
	public static String get_DDKD_from_GSBH(String gsbhStr)
	{
		return " select ddkd_fk from  ddkd_gsbh where gsbh_fk in ("+gsbhStr+") ";
	}
	public static String get_DDKD_from_ASM(String asmStr)
	{
		return get_DDKD_from_GSBH( get_GSBH_from_ASM ( asmStr));
	}
	
	public static String get_DDKD_from_BM(String bmStr)
	{
		return get_DDKD_from_GSBH( get_GSBH_from_ASM ( get_ASM_from_BM(bmStr)));
	}
	
	public static String get_NPP_from_GSBH (String gsbhStr)
	{
		return " select npp_fk from  NHAPP_GIAMSATBH where gsbh_fk in ("+gsbhStr+") ";
	}
	public static String get_NPP_from_ASM (String asmStr)
	{
		return  get_NPP_from_GSBH( get_GSBH_from_ASM ( asmStr) );
	}
	public static String get_NPP_from_BM (String bmStr)
	{
		return  get_NPP_from_GSBH( get_GSBH_from_ASM (  get_ASM_from_BM ( bmStr) ) );
	}
	public static String get_GSBH_from_ASM (String asmStr)
	{
		return " select pk_seq from  giamsatbanhang where asm_fk in ("+asmStr+") ";
	}
	public static String get_GSBH_from_BM (String bmStr)
	{
		return  get_GSBH_from_ASM (get_ASM_from_BM ( bmStr));
	}
	
	public static String get_NPP_from_User (String userId,String loai)
	{
		if(loai.equals("1"))
		{	
			return get_NPP_from_BM( " select top 1 bm_fk from nhanvien where pk_seq ="+userId+" " ) ;				
		}
		else if(loai.equals("2"))
		{
			
			return get_NPP_from_ASM( " select top 1 asm_fk from nhanvien where pk_seq ="+userId+" " ) ;
		}
		else if(loai.equals("3"))
		{
			
			return get_NPP_from_GSBH( " select top 1 gsbh_fk from nhanvien where pk_seq ="+userId+" " ) ;
		}
		else
		{
			return Quyen_npp(userId);
		}
	}
	
	public static String get_DDKD_from_User (String userId,String loai)
	{
		if(loai.equals("1"))
		{	
			return get_DDKD_from_BM( " select top 1 bm_fk from nhanvien where pk_seq ="+userId+" " ) ;				
		}
		else if(loai.equals("2"))
		{
			
			return get_DDKD_from_ASM( " select top 1 asm_fk from nhanvien where pk_seq ="+userId+" " ) ;
		}
		else if(loai.equals("3"))
		{
			
			return get_DDKD_from_GSBH( " select top 1 gsbh_fk from nhanvien where pk_seq ="+userId+" " ) ;
		}
		else
		{
			return  " select pk_seq from daidienkinhdoanh where npp_fk in ( " + Quyen_npp(userId) +")";
		}
	}
	
	
	
	
	
	public static String get_ASM_from_BM (String bmStr)
	{
		return " select pk_seq from  asm where bm_fk in ("+bmStr+") ";
	}
	
	public static String get_VUNG_from_KHUVUC( String kvStr)
	{
		return " select vung_fk from  khuvuc where pk_seq in ("+kvStr+") ";
	}
	
	public static String get_KHUVUC_from_NPP( String nppStr)
	{
		return " select khuvuc_fk from  nhaphanphoi where pk_seq in ("+nppStr+") ";
	}
	
	public static String get_KHUVUC_from_GSBH( String gsbhStr)
	{
		return get_KHUVUC_from_NPP( get_NPP_from_GSBH(gsbhStr));
	}
	public static String get_KHUVUC_from_ASM( String asmStr)
	{
		return get_KHUVUC_from_NPP( get_NPP_from_GSBH(get_GSBH_from_ASM ( asmStr)));
	}
	
	public static String get_KHUVUC_from_BM( String bmStr)
	{
		return get_KHUVUC_from_NPP( get_NPP_from_GSBH(get_GSBH_from_ASM ( get_ASM_from_BM(bmStr) )));
	}
	public static String get_VUNG_from_GSBH( String gsbhStr)
	{
		return  get_VUNG_from_KHUVUC( get_KHUVUC_from_GSBH(gsbhStr) );
	}
	public static String get_VUNG_from_ASM( String asmStr)
	{
		return  get_VUNG_from_KHUVUC( get_KHUVUC_from_ASM(asmStr) );
	}
	public static String get_VUNG_from_BM( String bmStr)
	{
		return  get_VUNG_from_KHUVUC( get_KHUVUC_from_BM(bmStr) );
	}
	
}
