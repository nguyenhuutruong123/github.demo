package geso.dms.center.util;


import geso.dms.center.db.sql.dbutils;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Webservice_UTN
{
	public enum loaidon {otc,doitac}
	
	public String error = "";
	public String id = null;
	
	private JSONObject create_Json_Object_APISalesInvoice(String query_tong, String query_sp_ban
			, String query_sp_km, String query_km_tratien, geso.dms.distributor.db.sql.dbutils db) {
		JSONObject obj = new JSONObject();
		JSONArray o_arr = new JSONArray();
		try {
			
			if(query_tong.trim().length() <= 0) throw new Exception("Chưa có query tổng");
			if(query_sp_ban.trim().length() <= 0) throw new Exception("Chưa có query hàng bán");
			
			int sd = 0;
			ResultSet rs_tong = db.get(query_tong);
			if(rs_tong != null ) {
				if(rs_tong.next()) {
					obj.put("order_code", rs_tong.getString("pk_seq"));
					obj.put("customer_code", rs_tong.getString("smartid"));
					obj.put("discount_amount", rs_tong.getString("sotiengiam"));
					//obj.put("province_name", rs_tong.getString("tinhthanh"));
					//obj.put("district_name", rs_tong.getString("quanhuyen"));
					//obj.put("ward_name", rs_tong.getString("phuongxa"));
					obj.put("customer_name", rs_tong.getString("tenkh"));	
					obj.put("address", rs_tong.getString("DIACHI"));
					obj.put("email", rs_tong.getString("email"));	
					obj.put("phone", rs_tong.getString("DIENTHOAI"));
					obj.put("channel", rs_tong.getString("diengiai"));
					obj.put("delivery_date", rs_tong.getString("ngaynhap"));
					obj.put("combo_name", rs_tong.getString("tenSpComBo"));
					obj.put("combo_price", rs_tong.getString("giaSpComBo"));
					obj.put("combo_qty", rs_tong.getString("soluongSpComBo"));
					obj.put("combo_total", rs_tong.getString("tongtienspcombo"));
					String discount_name="";
					if(!rs_tong.getString("sotiengiam").equals("0")){
						discount_name="giảm tổng đơn";
					}
					obj.put("discount_name", discount_name);					
					obj.put("customer_note", rs_tong.getString("ghichu"));	
					obj.put("order_return_code", rs_tong.getString("dontrahang"));
					obj.put("return_date", rs_tong.getString("ngaytra"));
				
				
					
					
				
					
					
					
					
				
					

					sd ++;
				}
				rs_tong.close();
			}
			
			if(sd == 0) throw new Exception("2. Lỗi lấy dữ liệu dòng tổng");
			
			sd = 0;
			JSONObject obj2 = null;
			ResultSet rsCt = db.get(query_sp_ban);
			if(rsCt != null) {
				while(rsCt.next()) {
					obj2 = new JSONObject();
					obj2.put("geso_product_code", rsCt.getString("ma"));
					obj2.put("product_name", rsCt.getString("ten"));
					obj2.put("quantity", rsCt.getString("soluong"));
					obj2.put("price", rsCt.getString("giamua"));
					obj2.put("discount_name", rsCt.getString("scheme"));
					obj2.put("discount_amount", rsCt.getString("chietkhau"));
					obj2.put("difference_price", rsCt.getString("tiengiamtru"));

					o_arr.put(obj2);
					
					sd++;
				}
				rsCt.close();
			}
			
			if(sd == 0) throw new Exception("Không có dữ liệu sản phẩm hàng bán"); 

			obj.put("line_items", o_arr);
		}
		catch (Exception e) {
			// TODO: handle exception
			obj = null;
			error = e.getMessage();
			e.printStackTrace();
		}
		return obj;
	}
	
	private JSONObject create_Json_Object_APIISSUES(String query_tong, String query_sp_ban, geso.dms.distributor.db.sql.dbutils db) {

		JSONObject obj = new JSONObject();
		JSONArray o_arr = new JSONArray();
		try {
			
			if(query_tong.trim().length() <= 0) throw new Exception("Chưa có query tổng");
			if(query_sp_ban.trim().length() <= 0) throw new Exception("Chưa có query hàng bán");
			
			int sd = 0;
			ResultSet rs_tong = db.get(query_tong);
			if(rs_tong != null ) {
				if(rs_tong.next()) {
					obj.put("id_dms", rs_tong.getString("id_dms"));
					obj.put("ngay_ct", rs_tong.getString("ngay_ct"));
					obj.put("so_ct", rs_tong.getString("so_ct"));
					obj.put("ma_kh", rs_tong.getString("ma_kh"));
					obj.put("ong_ba", rs_tong.getString("ong_ba"));
					obj.put("dien_giai", rs_tong.getString("dien_giai"));
					obj.put("ma_dvcs", rs_tong.getString("ma_dvcs"));
					
					sd ++;
				}
				rs_tong.close();
			}
			
			if(sd == 0) throw new Exception("2. Lỗi lấy dữ liệu dòng tổng");
			
			sd = 0;
			JSONObject obj2 = null;
			ResultSet rsCt = db.get(query_sp_ban);
			if(rsCt != null) {
				while(rsCt.next()) {
					obj2 = new JSONObject();
					obj2.put("ma_vt", rsCt.getString("ma_vt"));
					obj2.put("dvt", rsCt.getString("dvt"));
					obj2.put("ma_kho", rsCt.getString("ma_kho"));
					obj2.put("ma_lo", rsCt.getString("ma_lo"));
					obj2.put("SOLUONG", rsCt.getString("SOLUONG"));
					obj2.put("gia_nt", rsCt.getString("gia_nt"));
					obj2.put("tien_nt", rsCt.getString("tien_nt"));
					
					o_arr.put(obj2);
					
					sd++;
				}
				rsCt.close();
			}
			
			if(sd == 0) throw new Exception("Không có dữ liệu sản phẩm hàng bán"); 
			
			
			obj.put("details", o_arr);
		}
		catch (Exception e) {
			// TODO: handle exception
			obj = null;
			error = e.getMessage();
			e.printStackTrace();
		}
		return obj;
	
	}
	
	private JSONObject create_Json_Object_API_Chuyenkho(String query_tong, String query_sp_ban, geso.dms.distributor.db.sql.dbutils db) {

		JSONObject obj = new JSONObject();
		JSONArray o_arr = new JSONArray();
		try {
			
			if(query_tong.trim().length() <= 0) throw new Exception("Chưa có query tổng");
			if(query_sp_ban.trim().length() <= 0) throw new Exception("Chưa có query hàng bán");
			
			int sd = 0;
			ResultSet rs_tong = db.get(query_tong);
			if(rs_tong != null ) {
				if(rs_tong.next()) {
					obj.put("id_dms", rs_tong.getString("id_dms"));
					obj.put("ngay_ct", rs_tong.getString("ngay_ct"));
					obj.put("so_ct", rs_tong.getString("so_ct"));
					obj.put("ong_ba", rs_tong.getString("ong_ba"));
					obj.put("dien_giai", rs_tong.getString("dien_giai"));
					obj.put("ma_kho_x", rs_tong.getString("ma_kho_x"));
					obj.put("ma_kho_n", rs_tong.getString("ma_kho_n"));
					obj.put("ma_dvcs", rs_tong.getString("ma_dvcs"));
					
					sd ++;
				}
				rs_tong.close();
			}
			
			if(sd == 0) throw new Exception("2. Lỗi lấy dữ liệu dòng tổng");
			
			sd = 0;
			JSONObject obj2 = null;
			ResultSet rsCt = db.get(query_sp_ban);
			if(rsCt != null) {
				while(rsCt.next()) {
					obj2 = new JSONObject();
					obj2.put("ma_vt", rsCt.getString("ma_vt"));
					obj2.put("dvt", rsCt.getString("dvt"));
					obj2.put("ma_lo", rsCt.getString("ma_lo"));
					obj2.put("so_luong", rsCt.getString("so_luong"));
					obj2.put("gia_nt", rsCt.getString("gia_nt"));
					obj2.put("tien_nt", rsCt.getString("tien_nt"));
					
					o_arr.put(obj2);
					
					sd++;
				}
				rsCt.close();
			}
			
			if(sd == 0) throw new Exception("Không có dữ liệu sản phẩm hàng bán"); 
			
			
			obj.put("details", o_arr);
		}
		catch (Exception e) {
			// TODO: handle exception
			obj = null;
			error = e.getMessage();
			e.printStackTrace();
		}
		return obj;
	
	}
	
	public String call_api_Issues(loaidon type, geso.dms.distributor.db.sql.dbutils db, String pxk_id) {
		JSONObject obj = null;
		try {
			String queryTong = "";
			String queryChitiet = "";
			if(type == loaidon.otc) {
				queryTong = "select k.ma_erp as ma_dvcs,a.pk_seq as id_dms, a.NGAYLAPPHIEU as ngay_ct, a.PK_SEQ as so_ct , d.maFAST as ma_kh, d.TEN as ong_ba, '' as dien_giai\r\n" + 
						"from PHIEUXUATKHO a \r\n" + 
						"inner join PHIEUXUATKHO_DONHANG b on a.PK_SEQ=b.PXK_FK \r\n" + 
						"inner join DONHANG c on c.PK_SEQ=b.DONHANG_FK\r\n" + 
						"inner join KHACHHANG d on d.PK_SEQ=c.KHACHHANG_FK\r\n" + 
						"inner join kho  k on k.pk_seq=c.kho_fk \r\n" +
						"where a.trangthai = 1 and a.PK_SEQ=" + pxk_id;
				
				queryChitiet = "select distinct sp.MA as ma_vt, dvt.DONVI as dvt, k.ma_erp_2 as ma_kho, a.SOLO as ma_lo, a.SOLUONG\r\n" + 
						", c.DONGIA as gia_nt, round(c.DONGIA * a.SOLUONG,0) as tien_nt\r\n" + 
						"from PHIEUXUATKHO_SANPHAM_CHITIET a \r\n" + 
						"inner join PHIEUXUATKHO_DONHANG b on a.PXK_FK=b.PXK_FK \r\n" + 
						"inner join hoadon f on f.PK_SEQ=b.hoadon_fk\r\n" + 
						"inner join nhaphanphoi npp on npp.pk_seq=f.npp_fk \r\n" +
						"inner join kho k on k.pk_seq=f.kho_fk \n" +
						"inner join sanpham sp on sp.PK_SEQ=a.SANPHAM_FK\r\n" + 
						"inner join HOADON_SP_CHITIET c on c.hoadon_fk=f.PK_SEQ and c.ma=sp.ma \r\n" + 
						"and c.ngayhethan=a.NGAYHETHAN and c.ngaynhapkho=a.NGAYNHAPKHO and c.solo=a.SOLO and a.KHO_FK=c.Kho_FK  \r\n" + 
						" and a.kbh_fk = (case when isnull(npp.dungchungkenh, 0) = 0 then f.kbh_fk else 100025 end) \r\n" +
						"inner join DONVIDOLUONG dvt on dvt.PK_SEQ=sp.DVDL_FK\r\n" + 
						"inner join phieuxuatkho g on g.pk_Seq=a.PXK_FK \r\n" +
						"where g.trangthai = 1 and a.PXK_FK="+pxk_id+"\r\n" + 
						"union all\r\n" + 
						"select sp.MA as ma_vt, dvt.DONVI as dvt, k.ma_erp_2 as ma_kho, a.SOLO as ma_lo, a.SOLUONG\r\n" + 
						", c.DONGIA as gia_nt, round(c.DONGIA * a.SOLUONG,0) as tien_nt\r\n" + 
						"from PHIEUXUATKHO_SPKM_CHITIET a \r\n" + 
						"inner join PHIEUXUATKHO_DONHANG b on a.PXK_FK=b.PXK_FK \r\n" + 
						"inner join hoadon f on f.PK_SEQ=b.hoadon_fk\r\n" + 
						"inner join kho k on k.pk_seq=f.kho_fk \n" +
						"inner join HOADON_CTKM_TRAKM_CHITIET c on c.hoadon_fk=f.PK_SEQ and c.sanpham_fk=a.SANPHAM_FK \r\n" + 
						"and c.ngayhethan=a.NGAYHETHAN and c.ngaynhapkho=a.NGAYNHAPKHO and c.solo=a.SOLO and a.KHO_FK=c.kho_fk and c.kbh_fk=a.kbh_fk \r\n" + 
						"inner join sanpham sp on sp.PK_SEQ=c.sanpham_fk\r\n" + 
						"inner join DONVIDOLUONG dvt on dvt.PK_SEQ=sp.DVDL_FK\r\n" + 
						"inner join phieuxuatkho g on g.pk_Seq=a.PXK_FK \r\n" +
						"where g.trangthai = 1 and a.PXK_FK="+pxk_id+"\r\n" + 
						"";
				
			}
			else if(type == loaidon.doitac) {
				queryTong = "select k.ma_erp as ma_dvcs,a.pk_seq as id_dms, a.NgayYeuCau as ngay_ct, a.PK_SEQ as so_ct , d.ma as ma_kh, d.TEN as ong_ba, '' as dien_giai\r\n" + 
						"from ERP_YCXUATKHONPP a \r\n" + 
						"inner join ERP_YCXUATKHONPP_DDH b on a.PK_SEQ=b.ycxk_fk \r\n" + 
						"inner join ERP_DONDATHANGNPP c on c.PK_SEQ=b.ddh_fk\r\n" + 
						"inner join NHAPHANPHOI d on d.PK_SEQ=c.NPP_DAT_FK\r\n" + 
						"inner join kho  k on k.pk_seq=c.kho_fk \r\n" +
						"where a.PK_SEQ="+pxk_id+" and a.TRANGTHAI = 2";
				queryChitiet = "select distinct sp.MA as ma_vt, dvt.DONVI as dvt, k.ma_erp_2 as ma_kho, a.SOLO as ma_lo, a.SOLUONG\r\n" + 
						", c.DONGIA as gia_nt, round(c.DONGIA * a.SOLUONG,0) as tien_nt\r\n" + 
						"from ERP_YCXUATKHONPP_SANPHAM_CHITIET a \r\n" + 
						"inner join ERP_YCXUATKHONPP_DDH b on a.ycxk_fk=b.ycxk_fk \r\n" + 
						"inner join ERP_HOADONNPP f on f.PK_SEQ=b.hoadon_fk\r\n" + 
						"inner join kho k on k.pk_seq=f.kho_fk \n" +
						"inner join sanpham sp on sp.PK_SEQ=a.sanpham_fk\r\n" + 
						"inner join ERP_HOADONNPP_SP_CHITIET c on c.hoadon_fk=f.PK_SEQ and c.MA=sp.MA\r\n" + 
						"and c.ngayhethan=a.NGAYHETHAN and c.ngaynhapkho=a.NGAYNHAPKHO and c.solo=a.SOLO and a.KHO_FK=c.Kho_FK\r\n" + 
						"inner join DONVIDOLUONG dvt on dvt.PK_SEQ=sp.DVDL_FK\r\n" + 
						"inner join ERP_YCXUATKHONPP g on g.PK_SEQ=a.ycxk_fk\r\n" + 
						"where a.ycxk_fk="+pxk_id+" and g.TRANGTHAI = 2";
			}
			obj = create_Json_Object_APIISSUES(queryTong,queryChitiet, db);
			
			if(obj == null) {
				return error;
			}
			else {
				error = sendPost_Okhttp_2(obj.toString(), "Issues","","");
			}
			
		}
		catch(Exception ex) {
			ex.printStackTrace();
			error = ex.getMessage();
		}
		
		return error;
		
	}
	
	public String call_api_chuyenkho(int type, geso.dms.distributor.db.sql.dbutils db, String pxk_id) {
		JSONObject obj = null;
		try {
			String queryTong = "";
			String queryChitiet = "";
			if(type == 1) {
				queryTong ="\n select a.PK_SEQ as id_dms,a.NgayChuyen  as ngay_ct,a.PK_SEQ so_ct ,c.TEN as ong_ba,c.GHICHU dien_giai,d.MA_ERP_2 as ma_kho_n,d.MA_ERP_2 as ma_kho_x,e.MA_ERP ma_dvcs"+
							"\n	from ERP_CHUYENKENH a "+
							"\n	inner join NHAPHANPHOI c on c.PK_SEQ=a.NPP_FK"+
							"\n	inner join KHO d on d.PK_SEQ=a.KHONHAN_FK "+
							"\n	inner join kho e on e.PK_SEQ=a.KhoXuat_FK"+
							"\n	where a.PK_SEQ=" + pxk_id;
				
				queryChitiet  ="\n select a.PK_SEQ as id_dms,a.NgayChuyen  as ngay_ct,a.PK_SEQ so_ct ,c.TEN as ong_ba,c.GHICHU dien_giai,d.MA_ERP_2 as ma_kho_n,d.MA_ERP_2 as ma_kho_x,"+
						"\n	sp.MA as ma_vt,dv.DONVI as dvt,b.solo as ma_lo,b.soluong as so_luong,0 as gia_nt,0 tien_nt"+
						"\n	from ERP_CHUYENKENH a "+
						"\n	inner join ERP_CHUYENKENH_SANPHAM_CHITIET b on a.PK_SEQ=b.chuyenkenh_fk"+
						"\n	inner join SANPHAM sp on sp.PK_SEQ=b.SANPHAM_FK"+
						"\n	inner join NHAPHANPHOI c on c.PK_SEQ=a.NPP_FK"+
						"\n	inner join KHO d on d.PK_SEQ=a.KHONHAN_FK "+
						"\n	inner join kho e on e.PK_SEQ=a.KhoXuat_FK"+
						"\n	inner join DONVIDOLUONG dv on dv.PK_SEQ=sp.DVDL_FK"+
						"\n	where a.PK_SEQ=" + pxk_id;
				
			}
			obj = create_Json_Object_API_Chuyenkho(queryTong,queryChitiet, db);
			
			if(obj == null) {
				return error;
			}
			else {
				error = sendPost_Okhttp_2(obj.toString(), "StockTranfer","","");
			}
			
		}
		catch(Exception ex) {
			ex.printStackTrace();
			error = ex.getMessage();
		}
		
		return error;
		
	}
	
	
	
	public  String call_api_salesInvoice(String loai, geso.dms.distributor.db.sql.dbutils db, String id,String dhId,String isdh) {
		try {
			String query_tong ="";
			String query_sp ="";
		 
			JSONObject obj = null;
			if(loai.equals("OTC")) {
				query_tong = "select isnull(tenSpComBo,'') as tenSpComBo,isnull(giaSpComBo,0) as giaSpComBo,isnull(soluongSpComBo,0) as soluongSpComBo,round(soluongSpComBo*giaSpComBo,0) as tongtienspcombo ,"
						+ "\n kh.DIACHI+CASE WHEN ISNULL(d.ten,'')<>'' THEN N', Phường/Xã '+ISNULL(d.ten,'') ELSE '' END+N', Quận/Huyện  '+c.ten+N', Tỉnh/Thành phố '+b.ten as diachi,kbh.diengiai,'' ngaytra,'' dontrahang,isnull( dh.ghichu,'') as ghichu,isnull( kh.email,'') as email,kh.ten as tenkh,kh.DIENTHOAI,dh.ngaynhap,dh.pk_seq,kh.smartid,isnull(dh.sotiengiam,0) as sotiengiam,kh.smartid,isnull(b.ten,'') as tinhthanh,isnull(c.ten,'') as quanhuyen,isnull(d.ten,'') as phuongxa " 
						+ "\n from donhang dh "
						+ "\n inner join khachhang kh on kh.pk_seq=dh.khachhang_fk "
						+ "\n inner join TINHTHANH b on kh.TINHTHANH_FK=b.pk_seq "
						+ "\n inner join QUANHUYEN c on c.pk_seq=kh.QUANHUYEN_FK "
						+ "\n left join PhuongXa d on d.pk_seq=kh.PhuongXa_FK "
						+ "\n inner join kenhbanhang kbh on kbh.pk_seq=dh.kbh_fk "
 						+ "\n where dh.pk_seq=" + id + "  and dh.trangthai =1";
				
				query_sp =  "select b.ten,a.tiengiamtru,b.ma,a.soluong,a.giamua,'' scheme, 0 chietkhau "
						+ "\n from donhang_sanpham a "
						+ "\n inner join sanpham b on a.sanpham_fk=b.pk_seq where donhang_fk="+id+" "
								+ "\n union all"+
				" select sp.ten,0,isnull(a.spma,'') as ma,a.soluong,0 giamua,b.scheme,case when a.spma is null then isnull(tonggiatri,0) else 0 end as chietkhau"
				+ "\n from donhang_ctkm_trakm a "
				+ "\n inner join ctkhuyenmai b on a.ctkmid=b.pk_seq "
				+ "\n inner join sanpham sp on a.spma=sp.ma "
				+ "\n where donhangid="+id+" ";	
			}else{
				query_tong = "select  isnull(tenSpComBo,'') as tenSpComBo,isnull(giaSpComBo,0) as giaSpComBo,isnull(soluongSpComBo,0) as soluongSpComBo,round(soluongSpComBo*giaSpComBo,0) as tongtienspcombo ,"
						+ "\n kh.DIACHI+CASE WHEN ISNULL(d.ten,'')<>'' THEN N', Phường/Xã '+ISNULL(d.ten,'') ELSE '' END+N', Quận/Huyện  '+c.ten+N', Tỉnh/Thành phố '+b.ten as diachi,kbh.diengiai,a.ngaynhap as ngaytra,a.pk_seq as dontrahang,isnull( dh.ghichu,'') as ghichu,isnull( kh.email,'') as email,kh.ten as tenkh,kh.DIENTHOAI,dh.ngaynhap,dh.pk_seq,kh.smartid,isnull(dh.sotiengiam,0) as sotiengiam,kh.smartid,isnull(b.ten,'') as tinhthanh,isnull(c.ten,'') as quanhuyen,isnull(d.ten,'') as phuongxa " 
						+ "\n from  donhangtrave a"
						+ "\n inner join donhang dh on dh.pk_seq=a.DONHANG_FK "
						+ "\n inner join khachhang kh on kh.pk_seq=dh.khachhang_fk " 
						+ "\n inner join TINHTHANH b on kh.TINHTHANH_FK=b.pk_seq "
						+ "\n inner join QUANHUYEN c on c.pk_seq=kh.QUANHUYEN_FK "
						+ "\n left join PhuongXa d on d.pk_seq=kh.PhuongXa_FK "
						+ "\n inner join kenhbanhang kbh on kbh.pk_seq=dh.kbh_fk "
 						+ "\n where a.pk_seq=" + id + "  and a.trangthai =3";
				
				query_sp =  "select b.ten,a.tiengiamtru,b.ma,a.soluong,a.giamua,'' scheme, 0 chietkhau "
						+ "\n from donhang_sanpham a "
						+ "\n inner join sanpham b on a.sanpham_fk=b.pk_seq where donhang_fk="+dhId+" "
								+ "\n union all"+
				" select sp.ten,0 ,isnull(a.spma,'') as ma,a.soluong,0 giamua,b.scheme,case when a.spma is null then isnull(tonggiatri,0) else 0 end as chietkhau"
				+ "\n from donhang_ctkm_trakm a "
				+ "\n inner join ctkhuyenmai b on a.ctkmid=b.pk_seq "
				+ "\n inner join sanpham sp on a.spma=sp.ma "
				+ "\n where donhangid="+dhId+" ";	
			}
			System.out.println("query_tong:"+query_tong);
			System.out.println("query_sp:"+query_sp);
			obj = create_Json_Object_APISalesInvoice(query_tong,query_sp,"","", db);
			System.out.println("HAHAHA: " + obj.toString());
			if(obj == null) {
				return error;
			}
			else {
				error = sendPost_Okhttp_2(obj.toString(), "orders",isdh,id);
			}
			
		}
		catch(Exception ex) {
			ex.printStackTrace();
			error = ex.getMessage();
		}
		return error;
	}
	
	public  String call_api_salesInvoice_void(loaidon type, String hdid) {
		String msg = "";
		try {
			if(type == loaidon.otc) {
				msg = sendPost_Okhttp_3(hdid,"salesInvoice");
			}
			else if(type == loaidon.doitac) {
				msg = sendPost_Okhttp_3(hdid,"salesInvoice");
			}
			
			if(msg.trim().length() > 0) {
				return msg;
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return ex.getMessage();
		}
		return "";
	}
	
	public  String sendPost_Okhttp_2(String bodyString,String method,String isdh,String dhid)
	{
		String msg="";
		System.out.println("bodystring "+bodyString);
		System.out.println("method "+"http://utn.vn/wp-json/oa/woo/v1/"+method);
		dbutils db = new dbutils();
		String sql="INSERT DONGBO_LOG(LOG_PHATHANH,PK_SEQ,ISDONHANG,CREATEDATE) SELECT N'"+bodyString+"',"+dhid+","+isdh+",'"+getDateTime1()+"'"
				+ " "; 
		db.update(sql);
		db.shutDown();
		OkHttpClient client = new OkHttpClient().newBuilder()
				  .connectTimeout(60000, TimeUnit.MILLISECONDS)
				  .readTimeout(60000, TimeUnit.MILLISECONDS)
				  .build();
				MediaType mediaType = MediaType.parse("application/json");
				RequestBody body = RequestBody.create(mediaType, bodyString);
				Request request = new Request.Builder()
				  //.url("http://utn.onweb.vn/wp-json/oa/woo/v1/"+method)
				  .url("http://utn.vn/wp-json/oa/woo/v1/"+method)
				  .method("POST", body) 
				  .addHeader("Authorization", "Bearer xZOtLuN_r5nUokGtBaDWCz-t15xDMwrrBa")
				  .addHeader("Content-Type", "text/plain")
				  .build();
				Response response;
				try {
					response = client.newCall(request).execute();
					 if (response.code() == 200) {
					JSONObject jsonObj = new JSONObject(response.body().string());  
					int id = jsonObj.getInt("id");
					if(id>0)
					{				 
						return "";
					}else {  
							msg  = jsonObj.getString("message");					 
						return msg;
					}
					 }else{
							JSONObject jsonObj = new JSONObject(response.body().string());  
							msg=jsonObj.getString("message");
						 return msg; 
					 }
				} catch (IOException e) { 
					e.printStackTrace();
					Writer writer = new StringWriter();
					e.printStackTrace(new PrintWriter(writer));
					String s = writer.toString();

					return  s;
				} 
			
	}
	
	public  String sendPost_Okhttp_3(String id,String method)
	{
		OkHttpClient client = new OkHttpClient().newBuilder()
				  .connectTimeout(60000, TimeUnit.MILLISECONDS)
				  .build();
				MediaType mediaType = MediaType.parse("application/json");
				RequestBody body = RequestBody.create(mediaType, "");
				Request request = new Request.Builder()
				  .url("http://103.238.71.39:6788/api/"+method+"/"+id)
				  .method("PUT", body)
				  .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYW1lIjoiZG1zIiwiaWF0IjoxNjA2NDY2NDgyfQ.9zBSWjbq5laLO5T9B-smIhsEPBlV6cZHdc2THfSZkrU")
				  .addHeader("Content-Type", "text/plain")
				  .build();
				Response response;
				try {
					response = client.newCall(request).execute();
					JSONObject jsonObj = new JSONObject(response.body().string());  
					String status = jsonObj.getString("status");
					if(status.equals("E0"))
					{
						return "";
					}else {  
						String msg = jsonObj.getString("message");
						return msg;
					}
				} catch (IOException e) { 
					e.printStackTrace();
					return "Lỗi";
				} 
			
	}
	
	
	public static String sendPost_Okhttp(String bodyString,String method)
	{
		OkHttpClient client = new OkHttpClient().newBuilder()
				  .connectTimeout(60000, TimeUnit.MILLISECONDS)
				  .build();
				MediaType mediaType = MediaType.parse("application/json");
				RequestBody body = RequestBody.create(mediaType, bodyString);
				Request request = new Request.Builder()
				  .url("http://103.238.71.39:6788/api/"+method)
				  .method("POST", body)
				  .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYW1lIjoiZG1zIiwiaWF0IjoxNjA2NDY2NDgyfQ.9zBSWjbq5laLO5T9B-smIhsEPBlV6cZHdc2THfSZkrU")
				  .addHeader("Content-Type", "text/plain")
				  .build();
				Response response;
				try {
					response = client.newCall(request).execute();
					JSONObject jsonObj = new JSONObject(response.body().string());  
					String status = jsonObj.getString("status");
					if(status.equals("E0"))
					{
						return "";
					}else {  
						JSONArray jsonObj1 = jsonObj.getJSONArray("message");
						String msg="";
						for (int i=0;i<jsonObj1.length();i++)
						{
							msg += jsonObj1.getJSONObject(i).getString("msg")+", ";
						}
						return msg;
					}
				} catch (IOException e) { 
					e.printStackTrace();
					return "Lỗi";
				} 
			
	}
	
	public String getDateTime1()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-hh:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);	
	}
}
