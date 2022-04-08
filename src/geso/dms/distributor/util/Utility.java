package geso.dms.distributor.util;

import geso.dms.center.beans.stockintransit.IStockintransit;
import geso.dms.center.db.sql.Idbutils;
import geso.dms.center.util.GlobalValue;
import geso.dms.distributor.db.sql.dbutils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Hashtable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import java.lang.Math;
import java.net.URLEncoder;

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
public class Utility implements Serializable
{

	public static final int THEM = 0;
	public static final int XOA = 1;
	public static final int SUA = 2;
	public static final int XEM = 3;
	public static final int CHOT = 4;
	public static final int HUYCHOT = 5;

	private static final long serialVersionUID = 1L;
	String nppId="";
	String nppTen="";
	String sitecode="";
	String dangnhap="";
	private String khoSAP;

	String ngayKs="";
	public String getNgayKs()
	{
		return ngayKs;
	}

	public void setNgayKs(String ngayKs)
	{
		this.ngayKs = ngayKs;
	}

	String isChiNhanh="";

	public String getIsChinhNhanh()
	{
		return isChiNhanh;

	}

	public String getIdNhapp(String userId, dbutils db) 
	{
		System.out.println("UserID: "+userId);
		String sql=" select nv.dangnhap, npp.khosap, npp.pk_seq,npp.sitecode,npp.ten,'' tengs, npp.IsChiNhanh, 0 as loai "
			+ " from nhanvien nv inner join nhaphanphoi "
			+ " npp on nv.convsitecode=sitecode where nv.pk_seq='"+userId+"' ";
		sql+=    "   union all  "+
		"   select nv.dangnhap,npp.khosap, npp.pk_seq,npp.sitecode,npp.ten as nppten, gs.ten as tengs,'' as ischinhanh, 1 as loai  "+ 
		"   from nhanvien nv inner join GIAMSATBANHANG gs on nv.GSBH_FK=gs.PK_SEQ  "+
		"    inner join NHAPHANPHOI npp on npp.SITECODE=nv.CONVSITECODE  "+
		"   where nv.pk_seq='"+userId+"' and nv.trangthai='1'  and nv.PHANLOAI=2 and gs.TRANGTHAI=1   "+
		"   and npp.TRANGTHAI=1 ";
		System.out.println("Get Thong Tin NPP :"+sql);
		ResultSet rs= db.get(sql);
		try{
			while(rs.next()){
				this.nppId=rs.getString("pk_seq");
				if(rs.getString("loai").equals("1"))
				{
					this.nppTen=  rs.getString("Tengs")+" - "+rs.getString("ten");
				}
				else
					this.nppTen= rs.getString("ten");
				this.sitecode=rs.getString("sitecode");
				this.dangnhap = rs.getString("dangnhap");
				this.setKhoSAP(rs.getString("khosap"));
				this.isChiNhanh= rs.getString("IsChiNhanh")==null?"0":rs.getString("isChiNhanh");

			}
			if(rs != null)
				rs.close();
		}catch(Exception er)
		{
			er.printStackTrace();
		}
		return this.nppId;
	}

	public String getIdNhapp(String userId) 
	{
		dbutils db = new dbutils();
		String sql = "\n select nv.dangnhap, npp.khosap, npp.pk_seq,npp.sitecode,npp.ten,'' tengs, npp.IsChiNhanh, 0 as loai, nv.PHANLOAI "
			+ "\n from nhanvien nv inner join nhaphanphoi "
			+ "\n npp on nv.convsitecode=sitecode where nv.pk_seq='"+userId+"' ";
		sql+=    "\n   union all  "+
		"\n   select nv.dangnhap,npp.khosap, npp.pk_seq,npp.sitecode,npp.ten as nppten, gs.ten as tengs,'' as ischinhanh, 1 as loai, nv.PHANLOAI  "+ 
		"\n   from nhanvien nv inner join GIAMSATBANHANG gs on nv.GSBH_FK=gs.PK_SEQ  "+
		"\n    inner join NHAPHANPHOI npp on npp.SITECODE=nv.CONVSITECODE  "+
		"\n   where nv.pk_seq='"+userId+"' and nv.trangthai='1'  and nv.PHANLOAI=2 and gs.TRANGTHAI=1   "+
		"\n   and npp.TRANGTHAI=1 ";
		//System.out.println("Get NPP: "+sql);
		ResultSet rs= db.get(sql);
		try{
			while(rs.next()){
				this.nppId=rs.getString("pk_seq");
				if(rs.getInt("PHANLOAI") == 2){
					this.nppId = "";
					this.nppTen = "";
				}
				if(rs.getString("loai").equals("1"))
				{
					this.nppTen=  rs.getString("Tengs")+" - "+rs.getString("ten");
				}
				else
					this.nppTen= rs.getString("ten");
				this.sitecode=rs.getString("sitecode");
				this.dangnhap = rs.getString("dangnhap");
				this.setKhoSAP(rs.getString("khosap"));
				this.isChiNhanh= rs.getString("IsChiNhanh")==null?"0":rs.getString("isChiNhanh");

			}
			if(rs != null)
				rs.close();
		}catch(Exception er)
		{
			er.printStackTrace();
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

	public String getDangNhap(){
		return this.dangnhap;
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
		}else{
			userId = "";
		}
		return userId;
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
				tmp = querystring.split("&")[1];
				id = tmp.split("=")[1];
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
	public String[] compareArrayString(String[] s1, String[] s2)
	{
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
		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy");
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
	/*
	public String replaceAEIOU(String s)
	{
		String result = s;
		boolean flag = false;
		for(int i=0; i < result.length(); i++){
			if (!result.substring(i, i+1).contains("a")&!result.substring(i, i+1).contains("e")&!result.substring(i, i+1).contains("i")&!result.substring(i, i+1).contains("o")&!result.substring(i, i+1).contains("u")){
				flag = true;
			    break;
			}
		}

		if(flag){

			for(int i=0; i < result.length(); i++){

				//result= result.replace("d", "_");
				result= result.replace("a", "_");
				result= result.replace("e", "_");
				result= result.replace("i", "_");
				result= result.replace("o", "_");
				result= result.replace("u", "_");
			}
		}
		return result;
	}
	 */

	public boolean checkHopLe(String userId)
	{
		dbutils db = new dbutils();
		String query = "select npp.pk_seq from nhanvien nv inner join nhaphanphoi npp on nv.convsitecode = sitecode  where nv.pk_seq = '" + userId + "'";
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
			rs = db.get(query);

			if(rs.next())
			{
				dakhoaso30 = rs.getInt("dakhoaso");
				rs.close();
			}

			if(dakhoaso30 == 0)  //chua khoa so ngay nay
				return true;

			query = "select count(npp_fk) as sodong from dieuchinhtonkho where npp_fk = '" + nppId + "' and trangthai = '1' and ngaydc = '2012-05-01'";
			rs = db.get(query);

			if(rs.next())
			{
				dacodctk01 = rs.getInt("sodong");
				rs.close();
			}

			if(dacodctk01 == 0)
				return false;

		} 
		catch(Exception e) { return false; }
		return true;
	}

	public String ngaykhoaso(String nhaphanphoi)
	{   
		String ngay = "";
		dbutils db = new dbutils();
		String sql ="select isnull(max(ngayks), '') as ngay from khoasongay where npp_fk ='"+ nhaphanphoi+"'";
		ResultSet rs = db.get(sql);
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

	public static boolean IsNumeric(String so)
	{   
		try {

			double a = Double.parseDouble(so);


		} catch (Exception e) {
			return false;
		}
		return true;
	}
	public void initSanPhamSearch(HttpServletRequest request,String npp)
	{
		dbutils db = new dbutils();
		HttpSession session = request.getSession();

		String query = "";
		query = "select DISTINCT a.ma, a.ten, c.giabanlenpp as dongia, isnull(b.donvi, 'Chua xac dinh') as donvi from sanpham a left join donvidoluong b on a.dvdl_fk = b.pk_seq ";
		query = query + "inner join bgbanlenpp_sanpham c on a.pk_seq = c.sanpham_fk inner join nhapp_kho d on d.npp_fk= '"+ npp +"' and d.sanpham_fk=a.pk_seq and d.available > 0 ";
		query = query + "where c.bgbanlenpp_fk in (select distinct pk_seq from banggiabanlenpp where npp_fk = '"+ npp +"' ) and c.giabanlenpp > '0' and a.trangthai='1' ";

		ResultSet spList = db.get(query);

		String list = "";
		//List<ISanpham> list = new ArrayList<ISanpham>();
		if(spList != null)
		{		
			//ISanpham sanpham = null;
			String[] param = new String[8];

			try 
			{
				while(spList.next())
				{
					/*
				    param[0] = "";
					param[1] = spList.getString("ma");
					param[2] = spList.getString("ten");
					param[3] = "";
					param[4] = spList.getString("donvi");
					param[5] = spList.getString("dongia");
					param[6] = "";
					param[7] = "";

					sanpham = new Sanpham(param);
					list.add(sanpham);
					 */
					//dung ky tu dac biet, tranh truong hop trong ten sp co nhung ky tu do
					list += spList.getString("ma") + "&&" + spList.getString("ten") + "&&" + spList.getString("donvi") + "&&" + spList.getString("dongia") + "\n";
				}
				spList.close();
			} 
			catch(Exception e) {}	
		}
		if(list.length() > 0)
		{
			list = list.substring(0, list.length() - 2);
			session.setAttribute("ListSP", list);
		}
		session.setAttribute("bgstId", "0");

		//neu nhapp co kenh sieu thi
		query = "select pk_seq from banggiasieuthi where npp_fk = '"+ npp +"'";

		////System.out.println("Kenh sieu thi la: " + query + "\n");

		ResultSet rsBgst = db.get(query);
		//List<ISanpham> listBgst = null;

		String listBgst = "";
		if(rsBgst != null)
		{
			try 
			{
				while(rsBgst.next())
				{
					//listBgst = new ArrayList<ISanpham>();
					//Khoi gan lai bgst moi
					listBgst = "";

					query = "select DISTINCT b.ma, b.ten, a.GIASIEUTHI as dongia, isnull(c.donvi, 'Chua xac dinh') as donvi from BANGGIAST_SANPHAM a inner join SANPHAM b on a.SANPHAM_FK = b.PK_SEQ left join donvidoluong c on b.DVDL_FK = c.pk_seq inner join nhapp_kho d on d.npp_fk= '"+ npp +"' and d.sanpham_fk=b.pk_seq and d.available > 0 ";
					query += " where a.BGST_FK = '" + rsBgst.getString("pk_seq") + "' and a.GIASIEUTHI > '0' and b.trangthai = '1'";

					//System.out.print("\nQuery cua ban La: " + query + "\n");

					ResultSet rsSt = db.get(query);
					if(rsSt != null)
					{		
						//ISanpham sanpham = null;
						//String[] param = new String[8];
						try 
						{
							while(rsSt.next())
							{
								/*
							    param[0] = "";
								param[1] = rsSt.getString("ma");
								param[2] = rsSt.getString("ten");
								param[3] = "";
								param[4] = rsSt.getString("donvi");
								param[5] = rsSt.getString("dongia");
								param[6] = "";
								param[7] = "";

								sanpham = new Sanpham(param);
								listBgst.add(sanpham);
								 */
								listBgst += rsSt.getString("ma") + "&&" + rsSt.getString("ten") + "&&" + rsSt.getString("donvi") + "&&" + rsSt.getString("dongia") + "\n";
							}
							rsSt.close();
						} 
						catch(Exception e) {}	
					}
					////System.out.println("List san pham la: " + listBgst + "\n");
					if(listBgst.length() > 0)
					{
						listBgst = listBgst.substring(0, listBgst.length() - 2);
						session.setAttribute("bgst" + rsBgst.getString("pk_seq"), listBgst);
					}
				}
				rsBgst.close();
			} 
			catch(Exception e) {}
		}
	}

	public void setKhoSAP(String khoSAP) {
		this.khoSAP = khoSAP;
	}
	public String getKhoSAP() {
		return khoSAP;
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
		'Ủ', 'ủ', 'Ứ', 'ứ', 'Ừ', 'ừ', 'Ử', 'ử', 'Ữ', 'ữ', 'Ự', 'ự', };

	private static char[] REPLACEMENTS ={ '-', '\0','\0','\0','\0','\0', '\0', '\0', '\0', '\0',
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

	public boolean daKhoaSoNgay30(String nppId)
	{
		/*dbutils db1 = new dbutils();
		String query = "select count(*) as dakhoaso from khoasongay where ngayks >= '2012-04-30' and npp_fk = '" + nppId + "'";

		//System.out.println("Cau lenh check: " + query);
		ResultSet rs = db1.get(query);

		int dakhoaso30 = 0;
		try
		{
			if(rs.next())
			{
				dakhoaso30 = rs.getInt("dakhoaso");
				rs.close();
			}

			if(dakhoaso30 > 0)  //chua khoa so ngay nay
			{
				query = "select count(*) as sodong from nppdaduyet where npp_fk = '" + nppId + "'";
				//System.out.println("Query check :" + query);
				rs = db1.get(query);
				int daduyet = 0;
				if(rs.next())
				{
					daduyet = rs.getInt("sodong");
					rs.close();
				}
				if(daduyet > 0) //da duyet roi thi mo chuc nang ban hang
					return false;
				return true;
			}
			db1.shutDown();
		} 
		catch(Exception e) 
		{ 
			db1.shutDown(); 
			return false; 
		}*/
		return false;
	}

	public boolean checkDaduyet(String userId)
	{
		dbutils db = new dbutils();
		String query = "select npp.pk_seq from nhanvien nv inner join nhaphanphoi npp on nv.convsitecode = sitecode  where nv.pk_seq = '" + userId + "'";
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

	public String setTieuDe(IStockintransit obj  )
	{
		String tieude="";
		dbutils db = new dbutils();


		if(obj.getkenhId()!=null && obj.getkenhId().length()>0)
		{
			tieude +=this.getTieuDe( "KenhBanHang","Ten", obj.getkenhId(), db )+ "_"; 
		}
		if(obj.getdvkdId()!=null && obj.getdvkdId().length()>0)
		{
			tieude += this.getTieuDe( "DonViKinhDoanh","DienGiai", obj.getdvkdId(),db ) + "_" ;
		}

		if(obj.getvungId()!=null && obj.getvungId().length()>0)
		{
			tieude += this.getTieuDe( "Vung","Ten", obj.getvungId(),db ) + "_" ;
		}

		if(obj.getkhuvucId()!=null && obj.getkhuvucId().length()>0)
		{
			tieude += this.getTieuDe( "KhuVuc","Ten", obj.getkhuvucId(),db ) + "_" ;
		}

		if(obj.getnppId()!=null && obj.getnppId().length()>0)
		{
			tieude += this.getTieuDe( "NhaPhanPhoi","Ten", obj.getnppId(), db ) + "_" ;
		}

		if(obj.getASMId()!=null && obj.getASMId().length()>0)
		{
			tieude +=this.getTieuDe( "ASM","Ten", obj.getASMId(), db )+ "_"; 
		}
		if(obj.getBMId()!=null && obj.getBMId().length()>0)
		{
			tieude +=this.getTieuDe( "BM","Ten", obj.getBMId(), db )+ "_"; 
		}

		if(obj.getgsbhId()!=null && obj.getgsbhId().length()>0)
		{
			tieude += this.getTieuDe( "GiamSatBanHang","Ten", obj.getgsbhId(),db ) + "_" ;
		}
		if(obj.getDdkd()!=null && obj.getDdkd().length()>0)
		{
			tieude += this.getTieuDe( "DaiDienKinhDoanh","Ten", obj.getDdkd(),db ) + "_" ;
		}
		if(obj.getnhanhangId()!=null && obj.getnhanhangId().length()>0 )
		{
			tieude += this.getTieuDe( "NhanHang","Ten", obj.getnhanhangId(),db ) + "_" ;
		}
		if(obj.getchungloaiId()!=null && obj.getchungloaiId().length()>0 )
		{
			tieude += this.getTieuDe( "ChungLoai","Ten", obj.getchungloaiId(),db ) + "_" ;
		}
		if(obj.getNspId()!=null && obj.getNspId().length()>0 )
		{
			tieude += this.getTieuDe( "NhomSanPham","Ten", obj.getNspId(),db ) + "_" ;
		}
		if(obj.getsanphamId()!=null && obj.getsanphamId().length()>0 )
		{
			tieude += this.getTieuDe( "SanPham","Ten", obj.getsanphamId(),db ) + "_" ;
		}
		if(obj.getPrograms()!=null && obj.getPrograms().length()>0)
		{
			tieude +=this.getTieuDe( "CTKHUYENMAI","DienGiai", obj.getPrograms(), db )+ "_"; 
		}
		if(obj.getpromotion()!=null && obj.getpromotion().length()>0)
		{
			tieude +=this.getTieuDe( "CTKHUYENMAI","DienGiai", obj.getpromotion(), db )+ "_"; 
		}

		if(obj.getdvdlId()!=null && obj.getdvdlId().length()>0)
		{
			tieude +=this.getTieuDe( "DonViDoLuong","DonVi", obj.getdvdlId(), db )+ "_"; 
		}
		if(obj.gettungay()!=null && obj.gettungay().length()>0)
		{
			tieude += obj.gettungay() + "_";
		}
		if(obj.getdenngay()!=null && obj.getdenngay().length()>0)
		{
			tieude += obj.getdenngay() + "_";
		}
		if(obj.getFromMonth()!=null && obj.getFromMonth().length()>0)
		{
			tieude += obj.getFromMonth() + "_";
		}
		if(obj.getFromYear()!=null && obj.getFromYear().length()>0)
		{
			tieude += obj.getFromYear() + "_";
		}
		if(obj.getToMonth()!=null && obj.getToMonth().length()>0)
		{
			tieude += obj.getToMonth() + "_";
		}
		if(obj.getToYear()!=null && obj.getToYear().length()>0)
		{
			tieude += obj.getToYear() + "_";
		}


		db.shutDown();
		return tieude;
	}
	public String getLoaiNv()
	{
		return loaiNv;
	}

	public void setLoaiNv(String loaiNv)
	{
		this.loaiNv = loaiNv;
	}
	public String getTieuDe(String table,String column,String id, geso.dms.distributor.db.sql.dbutils db)
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
			er.printStackTrace();
		}		
		return tieude;
	}
	String loaiNpp="";

	String loaiNv="";
	
	public void getUserInfo(String userId,Idbutils db)
	{		
		String sql=
			"			select nv.dangnhap,NULL LoaiNPP, NULL  as khosap , NULL pk_seq,NULL  sitecode,NV.ten ,nv.Loai as loaiNv  "+ 
			"			from nhanvien nv   "+
			"			where nv.pk_seq='"+userId+"' and nv.trangthai='1'  and nv.PHANLOAI=2 ";
		ResultSet rs= db.get(sql);
		try
		{
			if(rs.next())
			{
				this.nppId=rs.getString("pk_seq");
				this.nppTen= rs.getString("ten");
				this.sitecode=rs.getString("sitecode")==null?"": rs.getString("sitecode");
				this.dangnhap = rs.getString("dangnhap");
				this.setKhoSAP(rs.getString("khosap"));
				this.loaiNpp =  rs.getString("LoaiNPP")==null?"": rs.getString("LoaiNPP");
				this.loaiNv =rs.getString("loaiNv")==null?"": rs.getString("loaiNv");
				rs.close();
			}
		}catch(Exception er)
		{
			er.printStackTrace();
		}
	}
	
	public void getUserInfo(String userId,dbutils db)
	{		
		String sql = "\n select nv.dangnhap, NULL LoaiNPP, NULL as khosap, " +
		"\n NULL pk_seq, NULL sitecode, NV.ten, nv.Loai as loaiNv " + 
		"\n from nhanvien nv   "+
		"\n where nv.pk_seq='"+userId+"' and nv.trangthai='1' and nv.PHANLOAI = 2 ";
		ResultSet rs= db.get(sql);
		try
		{
			if(rs.next())
			{
				this.nppId=rs.getString("pk_seq");
				this.nppTen= rs.getString("ten");
				this.sitecode=rs.getString("sitecode")==null?"": rs.getString("sitecode");
				this.dangnhap = rs.getString("dangnhap");
				this.setKhoSAP(rs.getString("khosap"));
				this.loaiNpp =  rs.getString("LoaiNPP")==null?"": rs.getString("LoaiNPP");
				this.loaiNv =rs.getString("loaiNv")==null?"": rs.getString("loaiNv");
				rs.close();
			}
		}catch(Exception er)
		{
			er.printStackTrace();
		}
	}

	public String getDDKD_ASM(String userId){
		String ddkdIds = "";
		String gsbh_fk = "0";
		dbutils db = new dbutils();
		String sql = "select ISNULL(GSBH_FK,0) AS GSBH_FK from NHANVIEN where PHANLOAI = 2 and LOAI = 3 AND PK_SEQ = '"+userId+"' and trangthai='1'";

		ResultSet rs = db.get(sql);
		try
		{
			if(rs != null)
				if(rs.next())
				{
					gsbh_fk = rs.getString("GSBH_FK");
				}
			if(rs!=null)rs.close();
			if(!gsbh_fk.equals("0"))
				ddkdIds = "\n ( " +
				"\n 	select PK_SEQ from DAIDIENKINHDOANH d " +
				"\n 	where d.npp_fk in ( " +
				"\n 		select NPP_FK from nhapp_giamsatbh where KHUVUC_FK = ( " +
				"\n 					select KHUVUC_FK from GIAMSATBANHANG where PK_SEQ = '"+gsbh_fk+"' " +
				"\n 			) " +
				"\n 	) " +
				"\n )";
		}
		catch(Exception er)
		{
			er.printStackTrace();
		}
		finally
		{
			db.shutDown();
		}
		return ddkdIds;
	}
	public boolean KiemKeChuaDuyet(String nppId)
	{
		System.out.println("[KiemKeChuaDuyet]");
		dbutils db = new dbutils();
		String query = "select COUNT(*) as SoDong from KiemKe where NPP_FK="+nppId+" and TRANGTHAI=0";
		ResultSet rs = db.get(query);
		System.out.println("Query"+query);
		int sodong=0;
		try
		{
			while(rs.next())
			{
				sodong = rs.getInt("SoDong");
				if(sodong>0)
				{
					return  true;
				}
			}
			if(rs!=null) rs.close();
			System.out.println("[KiemKeChuaDuyet]"+query +"SoDong"+sodong);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	public String quyen_npp(String userId)
	{   String sql =" ( select npp_fk from phamvihoatdong where nhanvien_fk ='"+ userId +"') ";
	return sql;
	}


	public String Update_NPP_Kho_Sp(String ngaychungtu,String MaDonNghiepVu ,String nghiepvu ,Idbutils db, String khott_fk, String spId,String npp_fk,String kbh_fk,
			double soluong ,double booked,double available , double dongia) 
	{
		// TODO Phương thức đưa số lượng nhập vào kho,bảng ERP_KHOTT_SANPHAM
		try{
			int flag=0;
			String querylog="";
			String query="  select sanpham_fk ,available,soluong , sp.ma+ ' '+ sp.ten as ten , ISNULL(KHO.GIAMUA,0) AS GIATON   " +
			"  from NHAPP_KHO kho " +
			"  inner join sanpham sp  on kho.sanpham_fk=sp.pk_seq  where KBH_FK ="+kbh_fk+" AND NPP_FK="+npp_fk+" AND  " +
			"  KHO_FK="+khott_fk+" and sanpham_fk= "+spId;
			System.out.println("[UTILITY KHO : QUERY LAY SAN PHAM KHO TONG]" +query);
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


				querylog="insert into log_kho (MaDonNghiepVu,nghiepvu,sanpham_fk,kbh_fk,kho_fk,npp_fk,soluong,book,avai,ngaychungtu,soluongNV,bookNv,avaiNV)"+
				"select "+MaDonNghiepVu+",N'"+nghiepvu+"',sanpham_fk,kbh_fk,kho_fk,npp_fk,SOLUONG,BOOKED,AVAILABLE,'"+ngaychungtu+"',round(" + soluong + ",1),round("+booked+",1),round(" + available + ",1) from nhapp_kho where  npp_fk='"+npp_fk+"' and kbh_fk='"+kbh_fk+"' and kho_fk='"+khott_fk+"' and sanpham_fk='"+spId+"'  ";

				query = " Update NHAPP_KHO set booked=round(isnull(booked,0),1)+ round("+booked+",1) , soluong =round(ISNULL(soluong,0),1) + round(" + soluong + ",1), " +
				" AVAILABLE = round(ISNULL(AVAILABLE,0),1) + round(" + available + ",1), GIAMUA="+(giaton >0?giaton:dongia)+"  "+
				"  where KBH_FK="+kbh_fk+" AND NPP_FK="+npp_fk+" AND  " +
				"  KHO_FK="+khott_fk+" and sanpham_fk= "+spId;



			}else{
				query=  " INSERT INTO NHAPP_KHO ( KHO_FK,SANPHAM_FK,NPP_FK,KBH_FK,GIAMUA,SOLUONG,BOOKED,AVAILABLE ) VALUES  " +
				" ("+khott_fk+","+ spId+","+npp_fk+","+kbh_fk+","+dongia+",round("+soluong+",1),round("+booked+",1),round("+available+",1))";
				flag=1;
				querylog="insert into log_kho (MaDonNghiepVu,nghiepvu,sanpham_fk,kbh_fk,kho_fk,npp_fk,soluong,book,avai,ngaychungtu,soluongNV,bookNv,avaiNV)"+
				"select "+MaDonNghiepVu+",N'"+nghiepvu+"',sanpham_fk,kbh_fk,kho_fk,npp_fk,SOLUONG,BOOKED,AVAILABLE,'"+ngaychungtu+"',round(" + soluong + ",1),round("+booked+",1),round(" + available + ",1) from nhapp_kho where npp_fk='"+npp_fk+"' and kbh_fk='"+kbh_fk+"' and kho_fk='"+khott_fk+"' and sanpham_fk='"+spId+"'  ";

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

				System.out.println("Zipping " + filePath);
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

	//CAP NHAT TONG GIA TRI DON HANG
	public static String Update_GiaTri_DonHang(String dhId, Idbutils db)
	{

		try
		{

			String query = 	     "\n 	update DONHANG set TONGGIATRI= " + 
								 "\n 							 		round(	isnull(( " + 
								 "\n 							 				select SUM(round(b.soluong*b.giamua,0))  as TongGiaTri " + 
								 "\n 							 				from  DONHANG_SANPHAM b where b.DONHANG_FK=DONHANG.PK_SEQ " + 
								 "\n 							 			),0) - " + 
								 "\n 							 			isnull(( " + 
								 "\n 							 				select SUM(TONGGIATRI)  as TongGiaTri " + 
								 "\n 							 				from DONHANG_CTKM_TRAKM a" + 
								 "\n 							 				where  a.DONHANGID=DONHANG.PK_SEQ and SPMA is null" + 
								 "\n 							 			),0)  ,0)" + 
								 "\n 	" + 
								 "\n 	from DONHANG where pk_Seq= "+ dhId;
			System.out.println("_+_+__ tong gia tri tao moi" + query);
			if (!db.update(query)) {	
				return "Lỗi phát sinh khi cập nhật giá trị đơn hàng "+ query;	
			}
			return "";
		}
		catch(Exception ex){
			ex.printStackTrace();
			return "Exception:"+ ex.getMessage();
		}
	}
	
	public static String Update_GiaTri_ERP_DonDatHang(String dhId, Idbutils db,boolean isNPP)
	{

		try
		{

			String query = 	     "\n update erp_dondathang set" + 
								 "\n 		AVAT =  round( (dhsp.thanhtien - isnull(km.tienkm,0)) * (1 - isnull(dh.chietkhau,0)/100.0) *  ( 1 + isnull(dhsp.thuevat,0)/100.0)  ,0)" + 
								 "\n 		, BVAT  =  round( (dhsp.thanhtien - isnull(km.tienkm,0)) * (1 - isnull(dh.chietkhau,0)/100.0)  ,0)" + 
								 "\n 		, ckThanhToan = (dhsp.thanhtien - isnull(km.tienkm,0)) * ( isnull(dh.chietkhau,0)/100.0)" + 
								 "\n 		, vat = (dhsp.thanhtien - isnull(km.tienkm,0)) * (1 - isnull(dh.chietkhau,0)/100.0) *  (  isnull(dhsp.thuevat,0)/100.0)" + 
								 "\n  from erp_dondathang dh" + 
								 "\n  cross apply" + 
								 "\n  (" + 
								 "\n 	select sum( soluong * dongia)thanhtien, max(isnull(thuevat,0))thuevat  from ERP_DONDATHANG_SANPHAM dhsp where dhsp.dondathang_fk = dh.pk_seq" + 
								 "\n  )dhsp" + 
								 "\n  outer apply" + 
								 "\n  (	" + 
								 "\n 	 select sum(tonggiatri) tienkm from erp_dondathang_ctkm_trakm where spma is null and DONDATHANGID = dh.pk_seq" + 
								 "\n  )km" +
								 "\n  where dh.pk_seq= 	 ?";
			//System.out.println("_+_+__ tong gia tri tao moi" + query);
			dbutils.viewQuery(query, new Object[]{dhId} );
			if (db.updateQueryByPreparedStatement(query, new Object[]{dhId}) < 1) {	
				return "Lỗi phát sinh khi cập nhật giá trị đơn đặt hàng ";	
			}
			if(isNPP)
			{
				query = " update ERP_DONDATHANG_SANPHAM set soluongNppdat = soluong where dondathang_fk  = ?";
				if (db.updateQueryByPreparedStatement(query, new Object[]{dhId}) < 1) {	
					return "Lỗi phát sinh khi cập nhật giá trị đơn đặt hàng 2 ";	
				}
			}
			return "";
		}
		catch(Exception ex){
			ex.printStackTrace();
			return "Lỗi";
		}
	}
	
	public static String Check_Huy_NghiepVu_KhoaSo_TheoNgay_NPP(String nppId,String date, Idbutils db)
	{
		
		String query="select count(*) as sl from khoasongay where npp_fk="+nppId;
		ResultSet rsck=db.get(query);
		try {
			rsck.next();
			if(rsck.getInt("sl")==0)
			{
				rsck.close();
				return "Chưa thiết lập khóa sổ ngày vui lòng thiết lập.";
			}
		} catch (SQLException e1) {
			
			e1.printStackTrace();
			  return "Lỗi phát sinh khi check khóa sổ;";
		}
		query =  "\n  if (select count(*) from khoasongay where npp_fk = "+nppId+" and ngayks >= '"+date+"'  ) > 0" + 
				 "\n 	select 0 kq " + 
				 "\n  else" + 
				 "\n 	select 1 kq" ;
		
		String msg = "";
		System.out.println("______"+query);
		ResultSet rs = db.get(query);
		try
	    {
			rs.next();
			int kq = rs.getInt("kq");
			if(kq <=0)
				msg =  "Bạn không được thực hiện nghiệp vụ trong ngày đã khóa sổ !";
		    rs.close();
	    } 
		catch (Exception e)
	    {
		    e.printStackTrace();
		    return "Lỗi phát sinh khi check khóa sổ;";
	    }
		return msg;
		
	
	}
	
	public static String Check_Huy_NghiepVu_KhoaSo_NPP(String id,String columnNppId,String columnNgay,String table, Idbutils db)
	{
		
		String query="select count(*) as sl from khoasongay where npp_fk=( select "+columnNppId+" from "+table+" where pk_seq = "+id+"   )  "; 
		ResultSet rsck=db.get(query);
		try {
			rsck.next();
			if(rsck.getInt("sl")==0)
			{
				rsck.close();
				return "Chưa thiết lập khóa sổ ngày vui lòng thiết lập.";
			}
		} catch (SQLException e1) {
			
			e1.printStackTrace();
			  return "Lỗi phát sinh khi check khóa sổ;";
		}
		query =  "\n  if (select count(*) from khoasongay where npp_fk =( select "+columnNppId+" from "+table+" where pk_seq = "+id+"   ) and ngayks >=( select "+columnNgay+" from "+table+" where pk_seq = "+id+"     )  ) > 0" + 
				 "\n 	select 0 kq " + 
				 "\n  else" + 
				 "\n 	select 1 kq" ;
		
		String msg = "";
	//	System.out.println("______"+query);
		ResultSet rs = db.get(query);
		try
	    {
			rs.next();
			int kq = rs.getInt("kq");
			if(kq <=0)
				msg =  "Bạn không được thực hiện nghiệp vụ trong ngày đã khóa sổ !";
		    rs.close();
	    } 
		catch (Exception e)
	    {
		    e.printStackTrace();
		    return "Lỗi phát sinh khi check khóa sổ;";
	    }
		return msg;
		
	
	}
	
	public String[] antiSQLInspection_Array(String[] arr){
		
		if(arr != null)
		{
			for(int i = 0; i < arr.length; i++ )
			{
				if( arr[i] != null)
					arr[i] = antiSQLInspection(  arr[i] );
			}
			
		}
		return arr;
		
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
		int[] q =  geso.dms.center.util.Utility.Getquyen(svl, param,userId);
		return q[quyen]!=1;
	}
	
	private Object[] appendObjectArrayValue(Object[] obj, Object[] newObj) {
		ArrayList<Object> temp = new ArrayList<Object>(Arrays.asList(obj));
		for (int i = 0; i < newObj.length; i++) {
			temp.add(newObj[i]);
		}
		return temp.toArray();
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
	
	 public static String dongMa(String ma) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		   
		    SecretKeySpec skeySpec = new SecretKeySpec(GlobalValue.SECRET_KEY.getBytes(), "DES");
		    
		    Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5PADDING");
		    cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		    byte[] byteEncrypted = cipher.doFinal(ma.getBytes());
		    String encrypted =  DatatypeConverter.printBase64Binary(byteEncrypted);
		    return replaceSupser(encrypted);
	}
	 
	 public static String giaiMa(String ma) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		 	
		    SecretKeySpec skeySpec = new SecretKeySpec(GlobalValue.SECRET_KEY.getBytes(), "DES");
		    
		    Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5PADDING");
		    cipher.init(Cipher.DECRYPT_MODE, skeySpec);
		    byte[] byteDecrypted = cipher.doFinal(DatatypeConverter.parseBase64Binary(ma));
		    String decrypted = new String(byteDecrypted);
		   
		    return decrypted;
	 }
	 
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return in;
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

		 String query =	"\n SELECT sum(ISNULL(cast(THEM as int),0)) AS THEM, sum(ISNULL(cast(XOA as int),0)) AS XOA, sum(ISNULL(cast(SUA as int),0)) AS SUA, "+
		 "\n	sum(ISNULL(cast(XEM as int),0)) AS XEM, sum(ISNULL(cast(CHOT as int),0)) AS CHOT, sum(ISNULL(cast(HUYCHOT as int),'0')) AS HUYCHOT "+
		 "\n	FROM NHOMQUYEN  A INNER JOIN PHANQUYEN B ON A.DMQ_FK = B.DMQ_FK  "+ 
		 "\n	INNER JOIN UNGDUNG UD ON UD.PK_SEQ=A.UNGDUNG_FK  "+
		 "\n WHERE B.NHANVIEN_FK='"+nhanvien+"' AND UD.SERVLET='"+ungdung+"' AND UD.PARAMETERS='"+parameters+"' ";
		 System.out.println("[QUERY quyen]"+query);

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
		public static String PhanQuyenKBH(String userId)
		{
			String sql = 	"\n select pk_seq " +
							"\n from kenhbanhang kx " +
							"\n where exists ( " +
			
							"\n select 1 from  " +
							"\n nhanvien x " +
							"\n where x.pk_seq = "+userId+"  and " +
							"\n 		(  " +
							"\n 			( isnull(x.PHANLOAI,0) = 1 ) " +
							"\n 			or ( isnull(x.loai,0) = 1 and ( select top 1 kbh_fk from bm where pk_seq = x.bm_fk )= kx.pk_seq )   " +
							"\n 			or ( isnull(x.loai,0) = 2 and ( select top 1 kbh_fk from asm where pk_seq = x.asm_fk )= kx.pk_seq )   " +
							"\n 			or ( isnull(x.loai,0) = 3 and (  select top 1 kbh_fk from GIAMSATBANHANG where pk_seq = x.GSBH_FK )= kx.pk_seq )   " +

							"\n 		or ( isnull(x.loai,0) not in (1,2,3) ) " +
							"\n 	) " +
							"\n ) " ;
			return sql;
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

}
