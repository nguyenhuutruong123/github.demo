package geso.dms.center.servlets.reports;
import geso.dms.center.beans.stockintransit.IStockintransit;
import geso.dms.center.beans.stockintransit.imp.Stockintransit;
import geso.dms.center.util.Utility;
import geso.dms.distributor.beans.khachhang.imp.Khachhang;
import geso.dms.distributor.db.sql.dbutils;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.Label;
import jxl.write.WriteException;

import com.oreilly.servlet.MultipartRequest;
public class UploadTuyenBanHangSvl_2 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UploadTuyenBanHangSvl_2() 
	{
		super();
	}
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();
		Utility util = new Utility();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		obj.setuserId(userId);
		obj.init();

		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);
		String nextJSP = "/AHF/pages/Center/UploadTuyenBanHang.jsp";
		response.sendRedirect(nextJSP);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		String contentType = request.getContentType();

		String userId =geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		IStockintransit obj = new Stockintransit();
		obj.setuserId(userId);
		Utility util = new Utility();

		obj.setvungId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId"))==null? "":util.antiSQLInspection(request.getParameter("vungId"))));	   	 
		obj.setkhuvucId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId"))==null? "":util.antiSQLInspection(request.getParameter("khuvucId"))));	 
		obj.setnppId(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId"))==null? "":util.antiSQLInspection(request.getParameter("nppId"))));


		if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0)) 
		{	
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=THONGTINUPLOAD.xls");
			WritableWorkbook w = Workbook.createWorkbook(response.getOutputStream());


			dbutils db=new dbutils();
			//MultipartRequest multi = new MultipartRequest(request, "C:\\java-tomcat\\data\\", 20000000, "UTF-8"); 
			//getServletContext().getRealPath("\\pages\\Templates")+"\\BCDonHangBanTrongKyNPP.xlsm"
			
			MultipartRequest multi = new MultipartRequest(request, getServletContext().getRealPath("\\upload\\"), 20000000, "UTF-8"); 
			userId = util.antiSQLInspection(multi.getParameter("userId"));
			obj.setuserId(userId);
			obj.setvungId(util.antiSQLInspection(multi.getParameter("vungId"))==null? "":util.antiSQLInspection(multi.getParameter("vungId")));	   	 

			obj.setkhuvucId(util.antiSQLInspection(multi.getParameter("khuvucId"))==null? "":util.antiSQLInspection(multi.getParameter("khuvucId")));	 

			obj.setnppId(util.antiSQLInspection(multi.getParameter("nppId"))==null? "":util.antiSQLInspection(multi.getParameter("nppId")));

			Enumeration files = multi.getFileNames(); 
			String filenameu  ="";
			while (files.hasMoreElements())
			{
				String name = (String)files.nextElement();
				filenameu = multi.getFilesystemName(name); 
				System.out.println("name :   "+name);;
			}

			//String filename=    "C:\\java-tomcat\\data\\"+ filenameu;
			
			String filename=    getServletContext().getRealPath("\\upload\\")+ "\\"+ filenameu;
			if (filename.length() > 0)
			{
				//doc file excel
				File file = new File(filename);
				System.out.println("filename  "+file);
				Workbook workbook;

				int indexRow = 9;

				try 
				{
					ResultSet temprs;
					String sott="";
					String makh="";
					String tencuahieu="";
					String chucuahieu="";
					String diachi="";
					String phuong="";
					String quanhuyen="";
					String tinhthanh="";
					String sodienthoai="";
					String vitricuahang="";
					String loaicuahang="";
					String hangcuahang="";
					String tuyenbanhang="";
					String tanso="";
					String kbh_fk="100025";
					String vtch_fk="";
					String hch_fk="";
					String lch_fk="";
					String nhomkh_fk = "";
					String trangthai;
					String toado;
					String htkd="";
					String songayno = "";
					String sotienno = "";
					String dientichcuahang = "";
					String nhomkhachhhang = "";
					String tbh_fk="";
					String coderoute = "";
					String routename = "";
					ResultSet rs;
					System.out.println(file);
					workbook = Workbook.getWorkbook(file);
					Sheet[] sheet1 = workbook.getSheets();
					
					Hashtable<String, String> htp_kbh=new Hashtable<String, String>();
					Hashtable<String, String> htp_hch=gethtpHangcuahang(db);
					Hashtable<String, String> htp_lch=gethtpLoaicuahang(db);
					Hashtable<String, String> htp_vtch=gethtpVitricuahang(db);
					Hashtable<String, String> htp_Tinhthanh=gethtpTinhThanh(db);
					Hashtable<String, String> htp_quanhuyen=gethtpQuanHuyen(db);
					Hashtable<String, String> htp_nhomhk=getNhomKh(db);
					
					boolean error=false;
					boolean flag = true;
					// Kiểm tra các mã nhân viên này có đúng mã và phải của nhà
					// phân phối đã chọn ko?
					int sokh = 0;
					db.update("SET TRANSACTION ISOLATION LEVEL SNAPSHOT;");
					db.update("SET LOCK_TIMEOUT 60000;");
					db.getConnection().setAutoCommit(false);
					for (int t = 0; t < sheet1.length; t++)
					{
						Sheet sheet = sheet1[t];
						String nvbhid = sheet.getName().trim();
						String sql = 
								"\n select ddkd.pk_seq, npp.kbh_fk  from daidienkinhdoanh ddkd inner join ddkd_gsbh b on ddkd.pk_seq=b.ddkd_fk " +
								"\n inner join nhaphanphoi npp on npp.pk_seq = ddkd.npp_fk "+ 
										" inner join giamsatbanhang gsbh on gsbh.pk_seq = b.gsbh_fk  " + 
										" where npp_fk=" + obj.getnppId() + " and ddkd.pk_seq = " + nvbhid+" and gsbh.TRANGTHAI = 1 and npp.kbh_fk is not null ";
						System.out.println("Query Get DDKD: "+sql);
						rs = db.get(sql);
						if (rs == null || !rs.next())
						{
							obj.setMsg("Mã Nhân viên bán hàng : " + nvbhid+ " không xác định trong nhà phân phối bạn đang chọn,"
									+ " hoặc nhân viên này chưa có GSBH, vui lòng kiểm tra lại thông tin kênh bán hàng, trạng thái hoạt động nếu có GSBH.");
							error=true;
						} 
						else
						{
							htp_kbh.put(rs.getString("pk_seq").trim(), rs.getString("kbh_fk").trim());
						}
						if(rs!=null)rs.close();

					}
					if(!error)
					{
						for(int t=0;t<sheet1.length;t++)
						{
							//TAO RA 1 SHEET LUU THONG TIN UPLOAD KHONG THANH CONG
							WritableSheet sheetwrite = w.createSheet(sheet1[t].getName(), t);
							sheetwrite.addCell(new Label(0, 0, "THÔNG TIN CẬP NHẤT TUYẾN CỦA NHÂN VIÊN BÁN HÀNG CÓ MÃ: "+ sheet1[t].getName()));
							Sheet sheet=sheet1[t];

							String ddkdid=sheet.getName().trim();
							// Xóa tuyến cũ, tạo các tuyến chưa có cho nhân viên bán hàng !
							Hashtable<String,String> htbtuyen= Htb_TuyenBH(obj.getnppId(),userId,ddkdid,db);

							Cell[] cells ;
							indexRow=5;

							for(int i= indexRow; i < sheet.getRows();i++)
							{
								cells = sheet.getRow(i);
								if (cells.length>0)
								{
									int rowstart = 0;
									if(cells[0] !=null && cells[0].getContents().toString().length() >0 )
									{	
										sott = getValue(sheet, rowstart++,i);
										coderoute = getValue(sheet, rowstart++,i);
										routename = getValue(sheet, rowstart++,i);
										
										//Không đọc
										String codenvbh = getValue(sheet, rowstart++,i);
										String tennvbh = getValue(sheet, rowstart++,i);
										
										String _makh = getValue(sheet, rowstart++,i);
										if (_makh != null && _makh.trim().equals("NA")) {
											_makh = "NA_Tmp" + getDateTime_MaTmp() +"_" +i;
										}
										makh = _makh;
										
										//Không đọc 
										String mathamchieu =  getValue(sheet, rowstart++,i); 
										
										tencuahieu = getValue(sheet, rowstart++,i).trim().replace("'", "''");
										chucuahieu = getValue(sheet, rowstart++,i).trim().replace("'", "''");
										sodienthoai = getValue(sheet, rowstart++,i).trim().replace("'", "''");
										diachi = getValue(sheet, rowstart++,i).trim().replace("'", "''");
										phuong = getValue(sheet, rowstart++,i).trim().replace("'", "''");
										
										//quanhuyen=htp_quanhuyen.get(quanhuyen).trim().replace("'", "''");
										//tinhthanh = htp_Tinhthanh.get(tinhthanh).trim().replace("'", "''");
										
										quanhuyen = getValue(sheet, rowstart++,i).trim().replace("'", "''");
										tinhthanh = getValue(sheet, rowstart++,i).trim().replace("'", "''");
										vitricuahang = getValue(sheet, rowstart++,i).trim().replace("'", "''");
										loaicuahang	= getValue(sheet, rowstart++,i).trim().replace("'", "''");
										
										String _hangcuahang = getValue(sheet, rowstart++,i);
										hangcuahang = _hangcuahang == null?"": _hangcuahang.trim().replace("'", "''");
										tuyenbanhang = getValue(sheet, rowstart++,i,false).trim().replace("'", "''");
										tanso = getValue(sheet, rowstart++,i).trim().replace("'", "''");
										
										String _trangthai = getValue(sheet, rowstart++,i);
										trangthai = _trangthai==null || _trangthai.trim().length()<=0?"0":_trangthai;
										toado = getValue(sheet, rowstart++,i).trim().replace("'", "''");
										htkd = getValue(sheet, rowstart++,i,false).trim().replace("'", "''");
										
										String _songayno = getValue(sheet, rowstart++,i);
										songayno = _songayno==null||_songayno.trim().length()<=0?"0":_songayno.trim().replace("'", "''");
										String _sotienno = getValue(sheet, rowstart++,i);
										sotienno = _sotienno==null||_sotienno.trim().length()<=0?"0":_sotienno.trim().replace("'", "''");
										dientichcuahang = getValue(sheet, rowstart++,i,false).trim().replace("'", "''");
										nhomkhachhhang = getValue(sheet, rowstart++,i,false).trim().replace("'", "''");
										
										kbh_fk = htp_kbh.get(ddkdid);
										lch_fk = htp_lch.get(loaicuahang);
										hch_fk = htp_hch.get(hangcuahang);
										vtch_fk = htp_vtch.get(vitricuahang);
										nhomkh_fk = htp_nhomhk.get(nhomkhachhhang);
										String errror = "";
										int kt = 0;	
										String query = "";
										String mavung = "", ma = "";
										ResultSet rs1 = null;
										
										int stt_max = Khachhang.MaKhachHang(db);
										query = "select dbo.PlusZero("+stt_max+",7) ma";
										temprs = db.get(query);
										temprs.next();
										String makh_tutang = temprs.getString("ma");
										temprs.close();
										if (makh.contains("NA_Tmp")) {
											ma = makh_tutang;
										}
										else {
											ma = makh;
										}

										//Không xài mã dạng này
										/*if(quanhuyen != null && makh.length() <= 0)
										{
											query = "\n select distinct case  " +  
													"\n when b.VUNG_FK  = 100030  then 11 " +  
													"\n when b.VUNG_FK = 100032 then 12 " +  
													"\n when b.VUNG_FK = 100031 then 13 " +  
													"\n when b.VUNG_FK = 100029 then 15 " +  
													"\n else 14 " +  
													"\n end as mavung	" +  
													"\n from KHUVUC_QUANHUYEN a inner join KHUVUC b on a.KHUVUC_FK = b.PK_SEQ " +  
													"\n where a.QUANHUYEN_FK = '"+quanhuyen+"' \n" ;
											//System.out.println("ma vung "+query);
											rs1 = db.get(query);
											if(rs1 != null)
											{
												if(rs1.next())
												{
													mavung = rs1.getString("mavung");
												}
												rs1.close();
											}

											if(mavung.length() <= 0)
											{
												errror=errror+ " Không tạo được mã khách hàng trong dòng "+ (indexRow+1) +"! Quận huyện chưa được thiết lập trong khu vực ! " ;
											}
											else
											{
												query = "select isnull(MAX(cast(subString(isnull(SMARTID,'0'),3,LEN(SMARTID) ) as int))+1,'1') as ma from khachhang where SMARTID like '"+mavung+"%'";

												rs1 = db.get(query);
												String matd =  "";
												if(rs1 != null)
												{
													rs1.next();
													if(makh == null || makh.trim().length() <= 0)
													{
														matd = rs1.getString("ma");
														kt = 1;
													}
													rs1.close();
												}
												else
													errror=errror+ " Không tạo được mã khách hàng trong dòng "+ (indexRow+1) +"! Quận huyện chưa được thiết lập trong khu vực ! " ;
												String chuoi = "";
												for (int k = 0; k < (5 - matd.length()); k++)
													chuoi += "0";
												ma = mavung+chuoi + matd;
												if(ma.length() < 7)
												{
													errror=errror+ "Lỗi tạo mã khách hàng trong dòng "+ (indexRow+1) +"!" ;
												}

											}

										}
										// Nếu điền mã khách hàng, mã tự động tăng bằng với mã kh
										if(makh.length() > 0)
										{
											ma = makh;
										}*/

										if(phuong.length() >50 )
										{
											errror=errror+  " . Độ dài ký tự phường không hợp lệ "+ (indexRow+1);
										}
										
										if(nhomkhachhhang.length() >50 )
										{
											errror=errror+  " . Độ dài ký tự nhóm khách hàng không hợp lệ "+ (indexRow+1);
										}
										
										if(!errror.equals(""))
										{
											sheetwrite.addCell(new Label(0, indexRow, errror));
											flag = false;
											System.out.println("1");
										}
										else
										{
											int soDong = -1;
											if(tinhthanh == null) tinhthanh = "";
											if(quanhuyen == null) quanhuyen = "";
											String	sql="";
											if(makh.length() <= 0 || makh.contains("NA_Tmp"))
											{

												// Trường hợp không điền mã kh, tự động tạo mã kh, và thêm mới khách hàng.
												sql = "\n insert into khachhang (daduyet,coderoute,routename,mathamchieu,ten,trangthai,ngaytao,ngaysua,nguoitao,nguoisua,kbh_fk,"+ 
														"\n hch_fk,lch_fk,npp_fk,vtch_fk,dienthoai,diachi,tinhthanh_fk, "+
														"\n quanhuyen_fk,smartid,phuongxa_fk,chucuahieu,songayno,sotienno,dientichcuahang,import) " +
														"\n select 1,N'"+coderoute+"',N'"+routename+"',(select manpp from nhaphanphoi where pk_seq = "+obj.getnppId()+")+'"+ma+"'," +
														"\n N'"+tencuahieu+"','"+trangthai+"','"+this.getDateTime()+"','"+this.getDateTime()+"'," +
														"\n "+userId+","+userId +","+kbh_fk+","+hch_fk+","+lch_fk+","+obj.getnppId()+","+vtch_fk+"," +
														"\n '"+sodienthoai+"',N'"+diachi+"',"+(tinhthanh.length() <= 0?null:tinhthanh)+", " +
														"\n "+(quanhuyen.length() <= 0?null:quanhuyen)+",'"+ma+"',"+(phuong.length() <= 0?null:phuong)+"," +
														"\n N'"+chucuahieu+"','"+songayno+"','"+sotienno+"','"+dientichcuahang+"','1'  " +
														"\n where not exists (select 1 from khachhang where npp_fk="+obj.getnppId().trim() +" " +
														"\n 					and smartid='"+ma.trim()+"') ";
												//System.out.println("Query Insert: "+query);
												soDong=db.updateReturnInt(sql);
												if(soDong<0)
												{
													System.out.println("Tạo khách hàng không thành công:" + sql);
													sheetwrite.addCell(new Label(0, indexRow , "Tạo mới khách hàng không thành công, vui lòng kiểm tra lại các thông tin: " + sql));
													flag = false;
												}
												
												query = "select scope_identity() abc";
												temprs = db.get(query);
												String khId = "";
												temprs.next();
												khId = temprs.getString("abc");
												temprs.close();
												
												if (khId != null == khId.length() > 0) {
													String kq = Khachhang.Log_MaKhachHang(db,khId,stt_max);
													if(kq.trim().length() > 0)
													{	
														sheetwrite.addCell(new Label(0, indexRow , "Không thể tạo Log_MaKhachHang"));
														flag = false;
													}
												}
												
											}else
											{
												// Trường hợp điền mã kh, cập nhật khách hàng theo mã đã điền trong File Excel
												if(!toado.equals("0"))
												{
													sql = "\n update khachhang set daduyet=1,coderoute=N'"+coderoute+"',routename=N'"+routename+"',trangthai='"+trangthai+"', ten=N'"+tencuahieu+"',hch_fk="+hch_fk+",lch_fk="+lch_fk+",vtch_fk="+vtch_fk+", " +
															"\n dienthoai=N'"+sodienthoai+"',diachi=N'"+diachi+"',tinhthanh_fk= " +(tinhthanh.length() <= 0?null:tinhthanh)+ "," +
															"\n quanhuyen_fk="+(quanhuyen.length() <= 0?null:quanhuyen)+" ,phuongxa_fk ="+(phuong.length() <= 0?null:phuong)+",chucuahieu=N'"+chucuahieu+"'," +
															"\n songayno ='"+songayno+"' , sotienno = '"+sotienno+"', dientichcuahang = '"+dientichcuahang+"', ngaysua = '"+this.getDateTime()+"' " +
															"\n where npp_fk="+obj.getnppId().trim() +" and smartid ='"+makh.trim()+"'";
												}else
												{
													sql = "\n update khachhang set coderoute=N'"+coderoute+"',routename=N'"+routename+"',trangthai='"+trangthai+"', ten=N'"+tencuahieu+"',hch_fk="+hch_fk+",lch_fk="+lch_fk+",vtch_fk="+vtch_fk+", " +
															"\n dienthoai=N'"+sodienthoai+"',diachi=N'"+diachi+"',tinhthanh_fk= " +(tinhthanh.length() <= 0?null:tinhthanh)+ "," +
															"\n quanhuyen_fk="+(quanhuyen.length() <= 0?null:quanhuyen)+" ,phuongxa_fk ="+(phuong.length() <= 0?null:phuong)+",chucuahieu=N'"+chucuahieu+"', " +
															"\n songayno ='"+songayno+"' , sotienno = '"+sotienno+"', dientichcuahang = '"+dientichcuahang+"', ngaysua = '"+this.getDateTime()+"' " +
															"\n where npp_fk="+obj.getnppId().trim() +" and smartid ='"+makh.trim()+"'";
												}
												//System.out.println("Query Update: " + sql);
												if(db.updateReturnInt(sql) <= 0 )
												{
													sheetwrite.addCell(new Label(0, indexRow, "Cập nhật khách hàng không thành công, vui lòng kiểm tra mã khách hàng hoặc các thông tin liên quan :" + sql));
													flag = false;

												}
											}
											//if(flag)
											
											sql = "\n update khachhang  set TimKiem = dbo.ftBoDau(ten)+'-'+SMARTID "+
													"\n where npp_fk="+obj.getnppId().trim() +" and smartid='"+ma.trim()+"'";
											if(flag)
												if(!db.update(sql))
												{
													sheetwrite.addCell(new Label(0, indexRow, "Cập nhật khách hàng không thành công:" + sql));
													System.out.println("4");
													flag = false;

												}

											sql=" delete from NHOMKHACHHANG_KHACHHANG where kh_FK = (select pk_seq from KhachHang where smartid ='"+ma+"' and npp_fk='"+obj.getnppId()+"') ";
											if(flag)
											if(!db.update(sql))
											{
												System.out.println("Tạo khách hàng không thành công :" + sql);
												sheetwrite.addCell(new Label(0, indexRow , "Xóa NHOMKHACHHANG_KHACHHANG không thành công  :" + sql));
												System.out.println("5");
												flag = false;

											}
											if(nhomkhachhhang.length()>0)
											{
												sql = "\n insert into NHOMKHACHHANG_KHACHHANG(NKH_FK,KH_FK) "+
												"\n	 select b.pk_seq,a.pk_seq "+
												"\n	 from "+
												"\n	 ( "+
												"\n		select pk_seq from KhachHang where smartid ='"+ma+"' and npp_fk='"+obj.getnppId()+"'  "+
												"\n	 )a, "+
												"\n	 ( "+
												"\n		select pk_seq  "+
												"\n		from nhomkhachhang "+
												"\n		where diengiai in ( select dbo.trim( data) from dbo.Split(N'"+nhomkhachhhang+"',','))  "+
												"\n	 )b ";
												if(flag)
												if(db.updateReturnInt(sql) <= 0)
												{
													System.out.println("Tạo khách hàng không thành công :" + sql);
													sheetwrite.addCell(new Label(0, indexRow , "Tạo nhóm khách hàng không thành công, nhóm khách hàng không tồn tại: " + sql));
													System.out.println("6");
													flag = false;


												}
											}

											sql=" delete from khachhang_htkd where khachhang_Fk =(select pk_seq from KhachHang where smartId='"+ma+"' and npp_fk='"+obj.getnppId()+"') ";
											if(flag)
											if(!db.update(sql))
											{
												System.out.println("Tạo khách hàng không thành công :" + sql);
												sheetwrite.addCell(new Label(0, indexRow , "Xóa khách hàng - HTKD không thành công  :" + sql));
												System.out.println("7");
												flag = false;

											}

											if(htkd.length()>0)
											{
												sql = "\n	insert into khachhang_htkd(KhachHang_FK,HTKD_FK) "+
												"\n	 select a.pk_seq,b.pk_seq "+
												"\n	 from "+
												"\n	 ( "+
												"\n		select pk_seq from KhachHang where smartid ='"+ma+"' and npp_fk='"+obj.getnppId()+"'  "+
												"\n	 )a , "+
												"\n	 ( "+
												"\n		select pk_seq  "+
												"\n		from HINHTHUCKINHDOANH "+
												"\n		where DienGiai in ( select dbo.trim( data) from dbo.Split(N'"+htkd+"',','))  "+
												"\n	 )b ";
												if(flag)
												if(db.updateReturnInt(sql) <= 0)
												{
													System.out.println("Tạo khách hàng không thành công :" + sql);
													sheetwrite.addCell(new Label(0, indexRow , "Tạo khách hàng - hình thức kinh doanh không thành công, sai hình thức kinh doanh:" + sql));
													flag = false;
													System.out.println("8");
												}
											}

											sql=" delete from NVGN_KH where khachhang_Fk =(select pk_seq from KhachHang where smartid ='"+ma+"' and npp_fk='"+obj.getnppId()+"') ";
											if(flag)
											if(!db.update(sql))
											{
												System.out.println("Tạo khách hàng không thành công :" + sql);
												sheetwrite.addCell(new Label(0, indexRow , "Xóa NVGN không thành công  :" + sql));
												System.out.println("9");
												flag = false;

											}


											sql = "\n	insert into NVGN_KH(KhachHang_FK,NVGN_FK) "+
											"\n	 select a.pk_seq,b.pk_seq "+
											"\n	 from "+
											"\n	 ( "+
											"\n		select pk_seq from KhachHang where smartid ='"+ma+"' and npp_fk='"+obj.getnppId()+"'  "+
											"\n	 )a , "+
											"\n	 ( "+
											"\n		select top(1) pk_seq  "+
											"\n		from NHANVIENGIAONHAN "+
											"\n		where npp_fk='"+obj.getnppId()+"'   "+
											"\n	 )b where a.pk_seq  not in (select Khachhang_fk from NVGN_KH)";
											if(flag)
											if(db.updateReturnInt(sql) <= 0)
											{
												System.out.println("Tạo khách hàng không thành công :" + sql);
												sheetwrite.addCell(new Label(0, indexRow , "Tạo khách hàng - NVGN không thành công:" + sql));
												flag = false;
												System.out.println("10");

											}

											if(flag)
											{
												// Lưu lại lịch sử tuyến bán hàng trước khi xóa
												sql = "\n insert khachhang_tuyenbanhang_Update (khachhang_fk,tbh_fk,tanso,ddkd_fk,NPP_FK,ngayid,NGUOISUA,islog) " + 
														"\n select a.khachhang_fk,a.tbh_fk,a.tanso,'"+ddkdid+"',c.npp_fk,c.ngayid,'"+userId+"','0' from khachhang_tuyenbh a inner join khachhang b on b.pk_seq=a.khachhang_fk " +
														"\n inner join tuyenbanhang c on c.pk_seq=a.tbh_fk          				"+  
														"\n	where c.ddkd_fk='"+ddkdid+"' and b.npp_fk=" + obj.getnppId() + " and b.smartid = '" + ma.trim() + "'  ";
												//System.out.println("luu lich su tuyen: "+sql);
												//if(flag)
												if (!db.update(sql))
												{
													System.out.println("Khong thanh cong do :" + sql);
													sheetwrite.addCell(new Label(1, indexRow, "Lưu lịch sử tuyến không thành công  :" + sql));
													System.out.println("11");
													flag = false;

												}	
											}
											sql = "\n	delete  a from khachhang_tuyenbh a inner join khachhang b on b.pk_seq=a.khachhang_fk " +
											"\n inner join tuyenbanhang c on c.pk_seq=a.tbh_fk          				"+  
											"\n where c.ddkd_fk='"+ddkdid+"' and b.npp_fk=" + obj.getnppId() + " and b.smartid = '" + ma.trim() + "'  ";

											//if(flag)
											if (!db.update(sql))
											{
												System.out.println("Khong thanh cong do :" + sql);
												sheetwrite.addCell(new Label(1, indexRow, "Xóa tuyến không thành công  :" + sql));
												System.out.println("12");
												flag = false;

											}	
											if(tanso.length() <=  0)
											{
												System.out.println("Khong thanh cong do :" + sql);
												sheetwrite.addCell(new Label(1, indexRow, "Tần số sai, không được để trống:" + sql));
												System.out.println("13");
												flag = false;
											}
											sql = "insert into khachhang_tuyenbanhang_Update (khachhang_fk,tbh_fk,tanso,ddkd_fk,NPP_FK,ngayid,NGUOISUA,islog) " + 
													"	 select a.pk_seq,b.pk_seq,'"+tanso+"','"+ddkdid+"',b.npp_fk,b.ngayid,'"+userId+"','1' "+
													"	 from "+
													"	 ( "+
													"		select pk_seq from KhachHang where smartid ='"+ma.trim()+"' and npp_fk='"+obj.getnppId()+"'  "+
													"	 )a , "+
													"	 ( "+
													"		select pk_seq, NPP_FK, ngayid  "+
													"		from tuyenbanhang "+
													"		where npp_Fk='"+obj.getnppId()+"' and ddkd_fk='"+ddkdid+"' and NgayId in ( select dbo.trim( data) from dbo.Split(N'"+tuyenbanhang+"',',') )  "+
													"	 )b ";
											// System.out.println("Insert tuyen :" + sql);
											if(flag)
												if (db.updateReturnInt(sql) <= 0)
												{
													System.out.println("Khong thanh cong do :" + sql);
													sheetwrite.addCell(new Label(1, indexRow, "Tuyến bán hàng không tồn tại hoặc không được để trống tuyến:" + sql));
													System.out.println("14");
													flag = false;

												}	
											
											sql = " select top 1 ddkd.ten from TUYENBANHANG a inner join KHACHHANG_TUYENBH b on a.PK_SEQ  = b.TBH_FK "
												+ "\n inner join khachhang kh on kh.pk_seq = b.khachhang_fk "
												+ "\n inner join DAIDIENKINHDOANH ddkd on ddkd.PK_SEQ = a.DDKD_FK"+
												  "\n where a.DDKD_FK <> '"+ddkdid+"' and  kh.smartid = '"+ma.trim()+"' and kh.npp_fk='"+obj.getnppId()+"' " ;
											
											ResultSet rskt = db.get(sql);
											if(rskt != null)
											{
												if(rskt.next())
												{
													System.out.println("Khong thanh cong do :" + sql);
													sheetwrite.addCell(new Label(1, indexRow, "Khách hàng đang thuộc tuyến của NVBH: "+rskt.getString("ten")));
													System.out.println("15");
													flag = false;
												}
												rskt.close();
											}
											sql = "insert into khachhang_tuyenbh (khachhang_fk,tbh_fk,tanso,sott) " + 
													"	 select a.pk_seq,b.pk_seq,'"+tanso+"','"+sott+"' "+
													"	 from "+
													"	 ( "+
													"		select pk_seq from KhachHang where smartid ='"+ma.trim()+"' and npp_fk='"+obj.getnppId()+"'  "+
													"	 )a , "+
													"	 ( "+
													"		select pk_seq  "+
													"		from tuyenbanhang "+
													"		where npp_Fk='"+obj.getnppId()+"' and ddkd_fk='"+ddkdid+"' and NgayId in ( select dbo.trim( data) from dbo.Split(N'"+tuyenbanhang+"',',') )  "+
													"	 )b ";
											//System.out.println("Insert tuyen :" + sql);
											if(flag)
												if (db.updateReturnInt(sql) <= 0)
												{
													System.out.println("Khong thanh cong do :" + sql);
													sheetwrite.addCell(new Label(1, indexRow, "Tuyến bán hàng không tồn tại hoặc không được để trống tuyến:" + sql));
													System.out.println("15");
													flag = false;

												}	
										}
										sokh++;
									}

								}
								indexRow++;

							}
						}
						System.out.println("flag: "+flag+" - "+sokh);
						if(flag && sokh > 0)
						{
							obj.setMsg("Upload thành công "+sokh+" khách hàng!");
							db.getConnection().commit();
							db.getConnection().setAutoCommit(true);
						}
						else
						{
							db.getConnection().rollback();
							obj.setMsg("Upload không thành công! Vui lòng xem File!");
							w.write();
							w.close();
						}
					}
					db.shutDown();

				}catch(Exception er)
				{
					er.printStackTrace();
					try {
						db.getConnection().rollback();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					obj.setMsg("Upload không thành công ! Vui lòng xem file !");
					w.write();
					try {
						w.close();
					} catch (WriteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}

		if(action != null)
		{
			if(action.equals("exportmcp")){			
				obj.setuserId(userId);
				XuatFileExcelSR(response,obj.getnppId());
			}
			if(action.equals("exportmcpds")){			
				obj.setuserId(userId);
				XuatFileExcelSRDS(response,obj.getnppId());
			}
		}

		obj.init();

		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);
		String nextJSP = "/AHF/pages/Center/UploadTuyenBanHang.jsp";
		response.sendRedirect(nextJSP);
	}

	private void XuatFileExcelSR(HttpServletResponse response,String NppId) throws IOException 
	{
		OutputStream out1 = null;
		try {

			dbutils db=new dbutils();
			String npp =  "select dbo.ftbodau2(ten) ten from nhaphanphoi where pk_seq = "+NppId+" ";
			String ten = "";
			ResultSet tennppRs = db.get(npp);
			if(tennppRs != null)
				if(tennppRs.next())
				{
					ten = tennppRs.getString("ten");
					tennppRs.close();
				}
			response.setContentType("application/vnd.ms-excel");
			response.setHeader
			("Content-Disposition", "attachment; filename=MCP_"+ten+".xls");
			WritableWorkbook w = 
					jxl.Workbook.createWorkbook(response.getOutputStream());


			String sql = "\n select npp.ten as tennpp ,npp.ma,kv.ten as tenkv,ddkd.pk_seq as ddkdid,ddkd.ten  as ddkdten "+
					"\n from nhaphanphoi npp  "+
					"\n left join khuvuc kv  on kv.pk_Seq=npp.khuvuc_fk "+ 
					"\n inner join daidienkinhdoanh  ddkd on ddkd.npp_fk=npp.pk_seq   "+
					"\n where npp.pk_seq="+NppId ;	

			System.out.println("sql upload : "+sql);

			ResultSet rs=db.get(sql);
			int k=0;
			while (rs.next()){
				WritableSheet sheet = w.createSheet(rs.getString("ddkdid"), k);

				sheet.addCell(new Label(0, 1, "NPP : "));sheet.addCell(new Label(1, 1, ""+rs.getString("tennpp") ));

				sheet.addCell(new Label(0, 2, "KHU VỰC : "));sheet.addCell(new Label(1, 2, ""+rs.getString("tenkv") ));

				sheet.addCell(new Label(0, 3, "NVBH : "));sheet.addCell(new Label(1, 3, ""+rs.getString("ddkdten") ));

				WritableFont cellFont = new WritableFont(WritableFont.TIMES, 12);
				cellFont.setColour(Colour.BLACK);

				WritableCellFormat cellFormat = new WritableCellFormat(cellFont);

				cellFormat.setBackground(jxl.format.Colour.GRAY_25);
				cellFormat.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
				cellFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
				cellFormat.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
				cellFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);

				//cellFormat.setFont()
				int count = 0;
				sheet.addCell(new Label(count++, 4, "SỐ TT",cellFormat));
				sheet.addCell(new Label(count++, 4, "Code Route",cellFormat));
				sheet.addCell(new Label(count++, 4, "Route Name",cellFormat));
				sheet.addCell(new Label(count++, 4, "Code NVBH",cellFormat));
				sheet.addCell(new Label(count++, 4, "Họ tên NVBH",cellFormat));
				sheet.addCell(new Label(count++, 4, "MÃ KH",cellFormat));
				sheet.addCell(new Label(count++, 4, "MÃ THAM CHIẾU",cellFormat));
				sheet.addCell(new Label(count++, 4 , "TÊN CỬA HIỆU",cellFormat));
				sheet.addCell(new Label(count++, 4, "CHỦ CỬA HIỆU",cellFormat));
				sheet.addCell(new Label(count++, 4, "SỐ ĐIỆN THOẠI",cellFormat));
				sheet.addCell(new Label(count++, 4, "ĐỊA CHỈ",cellFormat));
				sheet.addCell(new Label(count++, 4, "PHƯỜNG XÃ",cellFormat));
				sheet.addCell(new Label(count++, 4, "QUẬN/HUYỆN",cellFormat));
				sheet.addCell(new Label(count++, 4, "TỈNH/THÀNH PHỐ",cellFormat));
				sheet.addCell(new Label(count++, 4, "VỊ TRÍ CỬA HÀNG",cellFormat));
				sheet.addCell(new Label(count++, 4, "LOẠI CỬA HÀNG",cellFormat));
				sheet.addCell(new Label(count++, 4, "HẠNG CỬA HÀNG",cellFormat));
				sheet.addCell(new Label(count++, 4, "TUYẾN THỨ",cellFormat));
				sheet.addCell(new Label(count++, 4, "TẦN SUẤT VIẾNG THĂM",cellFormat));
				sheet.addCell(new Label(count++, 4, "TRẠNG THÁI",cellFormat));
				sheet.addCell(new Label(count++, 4, "TỌA ĐỘ",cellFormat));
				sheet.addCell(new Label(count++, 4, "HÌNH THỨC KINH DOANH",cellFormat));
				sheet.addCell(new Label(count++, 4, "SỐ NGÀY NỢ",cellFormat));
				sheet.addCell(new Label(count++, 4, "SỐ TIỀN NỢ",cellFormat));
				sheet.addCell(new Label(count++, 4, "DIỆN TÍCH CỬA HÀNG",cellFormat));
				sheet.addCell(new Label(count++, 4, "NHÓM KHÁCH HÀNG",cellFormat));

				/*	sql="select vt.vitri ,hch.hang as hangcuahang,lch.loai as loaicuahang,kh.phuong,  "+ 
						" kh.nguoidaidien,kh.chucuahieu,kh.tinhthanh_fk,kh.quanhuyen_fk,  "+ 
						" kh.ten as tencuahieu,kh.smartid,kh.diachi,kh.dienthoai,tbh.ngayid,khtbh.tanso,khtbh.sott,kh.trangthai, " +
						" CASE WHEN kh.LAT IS NOT NULL AND kh.LONG IS NOT NULL THEN '1' ELSE '0' END AS TOADO ,htkd.DienGiai as HTKD " +
						" from tuyenbanhang tbh "+ 
						" inner join khachhang_tuyenbh khtbh on tbh.pk_seq=khtbh.tbh_fk "+ 
						" inner join khachhang kh on kh.pk_seq=khtbh.khachhang_fk "+ 
						" left join hangcuahang hch on hch.pk_seq=kh.hch_fk "+ 
						" left join loaicuahang lch on lch.pk_seq=kh.lch_fk "+ 
						" left join vitricuahang vt on vt.pk_seq=kh.vtch_fk " +
						"  left join HINHTHUCKINHDOANH htkd on khachhang_htkd bb on bb.pk_seq=htkd_fk	and bb.khachhang_fk=kh.pk_Seq "+ 
						" where tbh.npp_fk= "+NppId +" and tbh.ddkd_fk=" + rs.getString("ddkdid") +
						" order by ddkd_fk,ngayid,khtbh.sott ";*/


				sql= "\n select distinct ddkd.SMARTID codenvbh,ddkd.ten tennvbh,kh.coderoute,kh.routename,isnull(kh.mathamchieu,'')mathamchieu,vt.vitri ,hch.hang as hangcuahang,lch.loai as loaicuahang,kh.phuongxa_FK as phuong,  "+  
								"\n	kh.nguoidaidien,kh.chucuahieu,kh.tinhthanh_fk,kh.quanhuyen_fk,   "+
								"\n	kh.ten as tencuahieu,kh.smartid,kh.diachi,kh.dienthoai,kh.trangthai, "+ 
								"\n	case when kh.lat is not null and kh.long is not null then '1' else '0' end as toado , "+
								"\n	stuff      "+
								"\n	(     "+
								"\n		(    "+	
								"\n			select distinct top 100 percent ' , ' + N''+cast(bb.ngayid as varchar(2)) "+
								"\n			from khachhang_tuyenbh aa inner join tuyenbanhang bb on bb.pk_seq=aa.tbh_fk "+ 
								"\n			where   aa.khachhang_fk=kh.pk_seq and bb.npp_fk=tbh.npp_fk and bb.ddkd_fk=tbh.ddkd_fk "+
								"\n			order by ' , ' + N''+cast(bb.ngayid as varchar(2)) "+
								"\n			for xml path('')       "+
								"\n		), 1, 2, ''    "+
								"\n	) + ' '  as ngayid, "+
								"\n	stuff      "+
								"\n	(     "+
								"\n		(    "+
								"\n			select distinct  top 100 percent ' , ' + N''+cast(aa.tanso as varchar(20)) "+
								"\n			from khachhang_tuyenbh aa inner join tuyenbanhang bb on bb.pk_seq=aa.tbh_fk  "+ 
								"\n			inner join daidienkinhdoanh cc on cc.pk_seq=bb.ddkd_fk "+
								"\n			where aa.khachhang_fk=kh.pk_seq and bb.npp_fk=tbh.npp_fk and bb.ddkd_fk=tbh.ddkd_fk "+
								"\n			order by ' , ' + N''+cast(aa.tanso as varchar(20)) "+
								"\n			for xml path('')       "+
								"\n		), 1, 2, ''   "+
								"\n	) + ' '  as tanso, "+
								"\n	stuff   "+     
								"\n	(     "+
								"\n		(    "+
								"\n			select distinct top 100 percent ' , ' + aa.diengiai "+ 
								"\n			from HINHTHUCKINHDOANH aa inner join khachhang_htkd  bb on aa.pk_seq=bb.htkd_fk "+	
								"\n			where  bb.khachhang_fk=kh.pk_Seq "+
								"\n			order by ' , ' + aa.diengiai  "+
								"\n			for xml path('')       "+
								"\n		 ), 1, 2, ''    "+
								"\n	) + ' '  as HTKD,kh.dientichcuahang, " +
								"\n stuff   "+     
								"\n	(     "+
								"\n		(    "+
								"\n			select distinct top 100 percent ' , ' + aa.diengiai "+ 
								"\n			from nhomkhachhang aa inner join NHOMKHACHHANG_KHACHHANG  bb on aa.pk_seq=bb.nkh_fk "+	
								"\n			where  bb.kh_fk=kh.pk_Seq "+
								"\n			order by ' , ' + aa.diengiai  "+
								"\n			for xml path('')       "+
								"\n		 ), 1, 2, ''    "+
								"\n	) + ' '  as nhomkh, kh.songayno,kh.sotienno"+
								"\n	from tuyenbanhang tbh "+ 
								"\n	inner join khachhang_tuyenbh khtbh on tbh.pk_seq=khtbh.tbh_fk "+  
								"\n	inner join khachhang kh on kh.pk_seq=khtbh.khachhang_fk  and kh.npp_fk=tbh.npp_fk "+
								"\n	left join hangcuahang hch on hch.pk_seq=kh.hch_fk "+  
								"\n	left join loaicuahang lch on lch.pk_seq=kh.lch_fk "+  
								"\n	left join vitricuahang vt on vt.pk_seq=kh.vtch_fk  "+
								"\n 	left join daidienkinhdoanh ddkd on ddkd.pk_seq in (select ddkd_fk from tuyenbanhang where pk_seq = khtbh.tbh_fk) " +
								"\n where tbh.npp_fk= "+NppId+" and tbh.ddkd_fk=" + rs.getString("ddkdid") +""+
								" order by ngayid ";
				System.out.println("Query Export MCP: "+sql);

				ResultSet rs1=db.get(sql);
				Label label ;

				Number number;
				int j=5;
				//set style to cell data
				WritableCellFormat cellFormat2 = new WritableCellFormat();

				cellFormat2.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
				cellFormat2.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
				cellFormat2.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
				cellFormat2.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
				int stt = 0;
				/*if(rs1!=null)*/
				while (rs1.next()){
					
					stt++;
					count = 0;
					label = new Label(count++, j, ""+stt,cellFormat2); 
					sheet.addCell(label); 
					
					label = new Label(count++, j, rs1.getString("CodeRoute"),cellFormat2); 
					sheet.addCell(label); 
					
					label = new Label(count++, j, rs1.getString("RouteName"),cellFormat2); 
					sheet.addCell(label); 
					
					label = new Label(count++, j, rs1.getString("codenvbh"),cellFormat2); 
					sheet.addCell(label); 
					
					label = new Label(count++, j, rs1.getString("tennvbh"),cellFormat2); 
					sheet.addCell(label); 
					
					label = new Label(count++, j, rs1.getString("smartid"),cellFormat2); 
					sheet.addCell(label); 
					
					label = new Label(count++, j, rs1.getString("mathamchieu"),cellFormat2); 
					sheet.addCell(label); 

					label = new Label(count++, j,rs1.getString("tencuahieu"),cellFormat2); 
					sheet.addCell(label); 

					label = new Label(count++, j,rs1.getString("chucuahieu"),cellFormat2); 
					sheet.addCell(label); 
					
					label = new Label(count++, j,rs1.getString("dienthoai"),cellFormat2); 
					sheet.addCell(label);

					label = new Label(count++, j,rs1.getString("diachi"),cellFormat2); 
					sheet.addCell(label);

					label = new Label(count++, j,rs1.getString("phuong"),cellFormat2); 
					sheet.addCell(label); 

					label = new Label(count++, j,rs1.getString("quanhuyen_fk"),cellFormat2); 
					sheet.addCell(label); 

					label = new Label(count++, j,rs1.getString("tinhthanh_fk"),cellFormat2); 
					sheet.addCell(label);

					label = new Label(count++, j,rs1.getString("vitri"),cellFormat2); 
					sheet.addCell(label);

					label = new Label(count++, j,rs1.getString("loaicuahang"),cellFormat2); 
					sheet.addCell(label);

					label = new Label(count++, j,rs1.getString("hangcuahang"),cellFormat2); 
					sheet.addCell(label);

					label = new Label(count++, j,rs1.getString("ngayid"),cellFormat2); 
					sheet.addCell(label);

					label = new Label(count++, j,rs1.getString("tanso"),cellFormat2); 
					sheet.addCell(label);
					
					label = new Label(count++, j,rs1.getString("trangthai"),cellFormat2); 
					sheet.addCell(label);

					label = new Label(count++, j,rs1.getString("TOADO"),cellFormat2); 
					sheet.addCell(label);

					label = new Label(count++, j,rs1.getString("HTKD"),cellFormat2); 
					sheet.addCell(label);

					label = new Label(count++, j,rs1.getString("songayno"),cellFormat2); 
					sheet.addCell(label);

					label = new Label(count++, j,rs1.getString("sotienno"),cellFormat2); 
					sheet.addCell(label);

					label = new Label(count++, j,rs1.getString("dientichcuahang"),cellFormat2); 
					sheet.addCell(label);

					label = new Label(count++, j,rs1.getString("nhomkh"),cellFormat2); 
					sheet.addCell(label);
					j++;
				}
				k++;
			}
			w.write();
			w.close();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Error Cac Ban : "+e.toString());
		} 
		finally{
			if (out1 != null)
				out1.close();
		}
	}

	private void XuatFileExcelSRDS(HttpServletResponse response,String NppId) throws IOException 
	{
		OutputStream out1 = null;
		try {

			dbutils db=new dbutils();
			String npp =  "select  dbo.ftbodau2(ten) ten from nhaphanphoi where pk_seq = "+NppId+" ";
			String ten = "";
			ResultSet tennppRs = db.get(npp);
			if(tennppRs != null)
				if(tennppRs.next())
				{
					ten = tennppRs.getString("ten");
					tennppRs.close();
				}
			response.setContentType("application/vnd.ms-excel");
			response.setHeader
			("Content-Disposition", "attachment; filename=MCP_"+ten+".xls");
			WritableWorkbook w = 
					jxl.Workbook.createWorkbook(response.getOutputStream());


			String sql = "\n select npp.ten as tennpp ,npp.ma,kv.ten as tenkv,ddkd.pk_seq as ddkdid,ddkd.ten  as ddkdten "+
					"\n from nhaphanphoi npp  "+
					"\n inner join khuvuc kv  on kv.pk_Seq=npp.khuvuc_fk "+ 
					"\n inner join daidienkinhdoanh  ddkd on ddkd.npp_fk=npp.pk_seq   "+
					"\n where npp.pk_seq="+NppId ;	

			System.out.println("sql upload : "+sql);

			ResultSet rs=db.get(sql);
			int k=0;
			while (rs.next()){
				WritableSheet sheet = w.createSheet(rs.getString("ddkdid"), k);

				sheet.addCell(new Label(0, 1, "NPP : "));sheet.addCell(new Label(1, 1, ""+rs.getString("tennpp") ));

				sheet.addCell(new Label(0, 2, "KHU VỰC : "));sheet.addCell(new Label(1, 2, ""+rs.getString("tenkv") ));

				sheet.addCell(new Label(0, 3, "NVBH : "));sheet.addCell(new Label(1, 3, ""+rs.getString("ddkdten") ));

				WritableFont cellFont = new WritableFont(WritableFont.TIMES, 12);
				cellFont.setColour(Colour.BLACK);

				WritableCellFormat cellFormat = new WritableCellFormat(cellFont);

				cellFormat.setBackground(jxl.format.Colour.GRAY_25);
				cellFormat.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
				cellFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
				cellFormat.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
				cellFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);

				//cellFormat.setFont()
				sheet.addCell(new Label(0, 4, "SỐ TT",cellFormat));
				sheet.addCell(new Label(1, 4, "MÃ KH",cellFormat));
				sheet.addCell(new Label(2, 4 , "TÊN CỬA HIỆU",cellFormat));
				sheet.addCell(new Label(3, 4, "CHỦ CỬA HIỆU",cellFormat));
				sheet.addCell(new Label(4, 4, "ĐỊA CHỈ",cellFormat));
				sheet.addCell(new Label(5, 4, "PHƯỜNG XÃ",cellFormat));
				sheet.addCell(new Label(6, 4, "QUẬN/HUYỆN",cellFormat));
				sheet.addCell(new Label(7, 4, "TỈNH/THÀNH PHỐ",cellFormat));
				sheet.addCell(new Label(8, 4, "SỐ ĐIỆN THOẠI",cellFormat));
				sheet.addCell(new Label(9, 4, "VỊ TRÍ CỬA HÀNG",cellFormat));
				sheet.addCell(new Label(10, 4, "LOẠI CỬA HÀNG",cellFormat));
				sheet.addCell(new Label(11, 4, "HẠNG CỬA HÀNG",cellFormat));
				sheet.addCell(new Label(12, 4, "TUYẾN THỨ",cellFormat));
				sheet.addCell(new Label(13, 4, "TẦN SUẤT VIẾNG THĂM",cellFormat));
				sheet.addCell(new Label(14, 4, "TRẠNG THÁI",cellFormat));
				sheet.addCell(new Label(15, 4, "TỌA ĐỘ",cellFormat));
				sheet.addCell(new Label(16, 4, "HÌNH THỨC KINH DOANH",cellFormat));
				sheet.addCell(new Label(17, 4, "SỐ NGÀY NỢ",cellFormat));
				sheet.addCell(new Label(18, 4, "SỐ TIỀN NỢ",cellFormat));
				sheet.addCell(new Label(19, 4, "DIỆN TÍCH CỬA HÀNG",cellFormat));
				sheet.addCell(new Label(20, 4, "NHÓM KHÁCH HÀNG",cellFormat));
				sheet.addCell(new Label(21, 4, "DOANH SỐ KH",cellFormat));

				/*	sql="select vt.vitri ,hch.hang as hangcuahang,lch.loai as loaicuahang,kh.phuong,  "+ 
						" kh.nguoidaidien,kh.chucuahieu,kh.tinhthanh_fk,kh.quanhuyen_fk,  "+ 
						" kh.ten as tencuahieu,kh.smartid,kh.diachi,kh.dienthoai,tbh.ngayid,khtbh.tanso,khtbh.sott,kh.trangthai, " +
						" CASE WHEN kh.LAT IS NOT NULL AND kh.LONG IS NOT NULL THEN '1' ELSE '0' END AS TOADO ,htkd.DienGiai as HTKD " +
						" from tuyenbanhang tbh "+ 
						" inner join khachhang_tuyenbh khtbh on tbh.pk_seq=khtbh.tbh_fk "+ 
						" inner join khachhang kh on kh.pk_seq=khtbh.khachhang_fk "+ 
						" left join hangcuahang hch on hch.pk_seq=kh.hch_fk "+ 
						" left join loaicuahang lch on lch.pk_seq=kh.lch_fk "+ 
						" left join vitricuahang vt on vt.pk_seq=kh.vtch_fk " +
						"  left join HINHTHUCKINHDOANH htkd on khachhang_htkd bb on bb.pk_seq=htkd_fk	and bb.khachhang_fk=kh.pk_Seq "+ 
						" where tbh.npp_fk= "+NppId +" and tbh.ddkd_fk=" + rs.getString("ddkdid") +
						" order by ddkd_fk,ngayid,khtbh.sott ";*/


				sql=
						" select distinct vt.vitri ,hch.hang as hangcuahang,lch.loai as loaicuahang,kh.phuongxa_FK as phuong,  "+  
								"\n				 kh.nguoidaidien,kh.chucuahieu,kh.tinhthanh_fk,kh.quanhuyen_fk,   "+
								"\n				 kh.ten as tencuahieu,kh.smartid,kh.diachi,kh.dienthoai,kh.trangthai, "+ 
								"\n				 case when kh.lat is not null and kh.long is not null then '1' else '0' end as toado , "+
								"\n				stuff      "+
								"\n				(     "+
								"\n					(    "+	
								"\n						select distinct top 100 percent ' , ' + N''+cast(bb.ngayid as varchar(2)) "+
								"\n						from khachhang_tuyenbh aa inner join tuyenbanhang bb on bb.pk_seq=aa.tbh_fk "+ 
								"\n						where   aa.khachhang_fk=kh.pk_seq and bb.npp_fk=tbh.npp_fk and bb.ddkd_fk=tbh.ddkd_fk "+
								"\n						order by ' , ' + N''+cast(bb.ngayid as varchar(2)) "+
								"\n						for xml path('')       "+
								"\n					 ), 1, 2, ''    "+
								"\n				) + ' '  as ngayid, "+
								"\n				stuff      "+
								"\n				(     "+
								"\n					(    "+
								"\n							 "+
								"\n						select distinct  top 100 percent ' , ' + N''+cast(aa.tanso as varchar(20)) "+
								"\n						from khachhang_tuyenbh aa inner join tuyenbanhang bb on bb.pk_seq=aa.tbh_fk  "+ 
								"\n						  inner join daidienkinhdoanh cc on cc.pk_seq=bb.ddkd_fk "+
								"\n						where   aa.khachhang_fk=kh.pk_seq and bb.npp_fk=tbh.npp_fk and bb.ddkd_fk=tbh.ddkd_fk "+
								"\n						order by ' , ' + N''+cast(aa.tanso as varchar(20)) "+
								"\n						for xml path('')       "+
								"\n					 ), 1, 2, ''   "+
								"\n				) + ' '  as tanso "+
								"\n	,stuff   "+     
								"\n	(     "+
								"\n		(    "+
								"\n			select distinct top 100 percent ' , ' + aa.diengiai "+ 
								"\n			from HINHTHUCKINHDOANH aa inner join khachhang_htkd  bb on aa.pk_seq=bb.htkd_fk "+	
								"\n			where  bb.khachhang_fk=kh.pk_Seq "+
								"\n			order by ' , ' + aa.diengiai  "+
								"\n			for xml path('')       "+
								"\n		 ), 1, 2, ''    "+
								"\n	) + ' '  as HTKD,kh.dientichcuahang, stuff   "+     
								"\n	(     "+
								"\n		(    "+
								"\n			select distinct top 100 percent ' , ' + aa.diengiai "+ 
								"\n			from nhomkhachhang aa inner join NHOMKHACHHANG_KHACHHANG  bb on aa.pk_seq=bb.nkh_fk "+	
								"\n			where  bb.kh_fk=kh.pk_Seq "+
								"\n			order by ' , ' + aa.diengiai  "+
								"\n			for xml path('')       "+
								"\n		 ), 1, 2, ''    "+
								"\n	) + ' '  as nhomkh , kh.songayno,kh.sotienno, round(dh.doanhso,0) as dskh"+
								"\n			 from tuyenbanhang tbh "+ 
								"\n				 inner join khachhang_tuyenbh khtbh on tbh.pk_seq=khtbh.tbh_fk "+  
								"\n				 inner join khachhang kh on kh.pk_seq=khtbh.khachhang_fk  and kh.npp_fk=tbh.npp_fk "+
								"\n				 left join hangcuahang hch on hch.pk_seq=kh.hch_fk "+  
								"\n				 left join loaicuahang lch on lch.pk_seq=kh.lch_fk "+  
								"\n				 left join vitricuahang vt on vt.pk_seq=kh.vtch_fk  "+
								"\n				 left join ( "+
								"\n							select khachhang_fk, round(sum(dh_sp.soluong*dh_sp.giamua),0) as doanhso from donhang dh "+
								"\n							inner join donhang_sanpham dh_sp on dh_sp.donhang_fk = dh.pk_seq "+
								"\n							where dh.trangthai = 1 "+
								"\n							and dh.ngaynhap >= CONVERT(date,DATEADD(month, -2, dbo.GetLocalDate(DEFAULT))) and dh.ngaynhap <= CONVERT(date, dbo.GetLocalDate(DEFAULT))  and trangthai = 1 "+
								"\n							group by khachhang_fk "+
								"\n				 ) dh on dh.khachhang_fk = kh.pk_seq "+
								"\n where tbh.npp_fk= "+NppId +" and tbh.ddkd_fk=" + rs.getString("ddkdid") +""+
								"\n order by ngayid ";
				System.out.println(sql);

				ResultSet rs1=db.get(sql);
				Label label ;

				Number number;
				int j=5;
				//set style to cell data
				WritableCellFormat cellFormat2 = new WritableCellFormat();

				cellFormat2.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
				cellFormat2.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
				cellFormat2.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
				cellFormat2.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
				int stt = 0;
				/*if(rs1!=null)*/
				while (rs1.next()){
					stt++;
					label = new Label(0, j, ""+stt,cellFormat2); 

					sheet.addCell(label); 

					label = new Label(1, j, rs1.getString("smartid"),cellFormat2); 
					sheet.addCell(label); 

					label = new Label(2, j,rs1.getString("tencuahieu"),cellFormat2); 
					sheet.addCell(label); 

					label = new Label(3, j,rs1.getString("chucuahieu"),cellFormat2); 
					sheet.addCell(label); 


					label = new Label(4, j,rs1.getString("diachi"),cellFormat2); 
					sheet.addCell(label);


					label = new Label(5, j,rs1.getString("phuong"),cellFormat2); 
					sheet.addCell(label); 

					label = new Label(6, j,rs1.getString("quanhuyen_fk"),cellFormat2); 
					sheet.addCell(label); 

					label = new Label(7, j,rs1.getString("tinhthanh_fk"),cellFormat2); 
					sheet.addCell(label);

					label = new Label(8, j,rs1.getString("dienthoai"),cellFormat2); 
					sheet.addCell(label);

					label = new Label(9, j,rs1.getString("vitri"),cellFormat2); 
					sheet.addCell(label);

					label = new Label(10, j,rs1.getString("loaicuahang"),cellFormat2); 
					sheet.addCell(label);

					label = new Label(11, j,rs1.getString("hangcuahang"),cellFormat2); 
					sheet.addCell(label);

					label = new Label(12, j,rs1.getString("ngayid"),cellFormat2); 
					sheet.addCell(label);

					label = new Label(13, j,rs1.getString("tanso"),cellFormat2); 
					sheet.addCell(label);
					label = new Label(14, j,rs1.getString("trangthai"),cellFormat2); 
					sheet.addCell(label);

					label = new Label(15, j,rs1.getString("TOADO"),cellFormat2); 
					sheet.addCell(label);

					label = new Label(16, j,rs1.getString("HTKD"),cellFormat2); 
					sheet.addCell(label);

					label = new Label(17, j,rs1.getString("songayno"),cellFormat2); 
					sheet.addCell(label);

					label = new Label(18, j,rs1.getString("sotienno"),cellFormat2); 
					sheet.addCell(label);

					label = new Label(19, j,rs1.getString("dientichcuahang"),cellFormat2); 
					sheet.addCell(label);

					label = new Label(20, j,rs1.getString("nhomkh"),cellFormat2); 
					sheet.addCell(label);

					
					number = new Number(21, j, rs1.getDouble("dskh"), cellFormat2);
					sheet.addCell(number);
					

					j++;
				}


				k++;
			}
			w.write();
			w.close();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Error Cac Ban : "+e.toString());
		} 
		finally{
			if (out1 != null)
				out1.close();
		}
	}

	private Hashtable<String, String> gethtpQuanHuyen(dbutils db) {

		Hashtable<String,String> htbtuyen= new Hashtable<String, String>();
		String sql="select pk_seq from quanhuyen";

		ResultSet rs=db.get(sql);
		try{
			while (rs.next()){
				htbtuyen.put(rs.getString("pk_seq").trim(), rs.getString("pk_seq").trim());

			}
			rs.close();
		}catch(Exception er){
			System.out.println("Loi lay tinhthanh : "+er.toString());
		}
		return htbtuyen;
	}
	private Hashtable<String, String> gethtpTinhThanh(dbutils db) {

		Hashtable<String,String> htbtuyen= new Hashtable<String, String>();
		String sql="select pk_seq from tinhthanh";

		ResultSet rs=db.get(sql);
		try{
			while (rs.next()){
				htbtuyen.put(rs.getString("pk_seq").trim(), rs.getString("pk_seq").trim());

			}
			rs.close();
		}catch(Exception er){
			System.out.println("Loi lay tinhthanh : "+er.toString());
		}
		return htbtuyen;
	}
	private Hashtable<String, String> gethtpVitricuahang(dbutils db) {

		Hashtable<String,String> htbtuyen= new Hashtable<String, String>();
		String sql="select vitri,pk_seq from vitricuahang";

		ResultSet rs=db.get(sql);
		try{
			while (rs.next()){
				htbtuyen.put(rs.getString("vitri").trim(), rs.getString("pk_seq").trim());

			}
			rs.close();
		}catch(Exception er){
			System.out.println("Loi lay vtch : "+er.toString());
		}
		return htbtuyen;
	}
	private Hashtable<String, String> gethtpLoaicuahang(dbutils db) {

		Hashtable<String,String> htbtuyen= new Hashtable<String, String>();
		String sql="select loai,pk_seq from loaicuahang";

		ResultSet rs=db.get(sql);
		try{
			while (rs.next()){
				htbtuyen.put(rs.getString("loai").trim(), rs.getString("pk_seq").trim());

			}
			rs.close();
		}catch(Exception er){
			System.out.println("Loi lay loai cuahang : "+er.toString());
		}
		return htbtuyen;
	}
	private Hashtable<String, String> gethtpHangcuahang(dbutils db) {

		Hashtable<String,String> htbtuyen= new Hashtable<String, String>();
		String sql="select hang,pk_seq from hangcuahang";

		ResultSet rs=db.get(sql);
		try{
			while (rs.next()){
				htbtuyen.put(rs.getString("hang").trim(), rs.getString("pk_seq").trim());
				}
			rs.close();
		}catch(Exception er){
			System.out.println("Loi lay hangcuahang : "+er.toString());
		}
		return htbtuyen;
	}

	private Hashtable<String, String> getNhomKh(dbutils db) {

		Hashtable<String,String> htbnhomkh= new Hashtable<String, String>();
		String sql="select diengiai,pk_seq from hangcuahang";

		ResultSet rs=db.get(sql);
		try{
			while (rs.next()){
				htbnhomkh.put(rs.getString("diengiai").trim(), rs.getString("pk_seq").trim());

			}
			rs.close();
		}catch(Exception er){
			System.out.println("Loi lay nhomkh : "+er.toString());
		}
		return htbnhomkh;
	}
	private String getDateTime() 
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();

		return dateFormat.format(date);	
	}
	private Hashtable<String,String>  Htb_TuyenBH(String nppId,String userid, String nvbhid, dbutils db) {

		String sql=" insert into tuyenbanhang  (diengiai,ngaylamviec,ngaytao,ngaysua,nguoitao,nguoisua,ddkd_fk,npp_fk,ngayid) "+
				" select N'Thứ Hai','Thu hai','"+this.getDateTime()+"','"+this.getDateTime()+"',"+userid+","+userid+",pk_seq,"+nppId+",2   "+
				" from  daidienkinhdoanh   "+
				" where pk_seq not in (select ddkd_fk from tuyenbanhang where ngayid=2 and ddkd_fk="+nvbhid+" and npp_fk="+nppId+") "+
				" and pk_seq= "+nvbhid+
				" union  "+
				" select N'Thứ Ba','Thu ba','"+this.getDateTime()+"','"+this.getDateTime()+"',"+userid+","+userid+",pk_seq,"+nppId+",3   "+
				" from  daidienkinhdoanh   "+
				" where pk_seq not in (select ddkd_fk from tuyenbanhang where ngayid=3 and ddkd_fk="+nvbhid+" and npp_fk="+nppId+") "+
				" and pk_seq="+nvbhid+
				" union  "+
				" select N'Thứ Tư','Thu tu','"+this.getDateTime()+"','"+this.getDateTime()+"',"+userid+","+userid+",pk_seq,"+nppId+",4   "+
				" from  daidienkinhdoanh   "+
				" where pk_seq not in (select ddkd_fk from tuyenbanhang where ngayid=4 and ddkd_fk="+nvbhid+" and npp_fk="+nppId+") "+
				" and pk_seq="+nvbhid+
				" union  "+
				" select N'Thứ Năm','Thu nam','"+this.getDateTime()+"','"+this.getDateTime()+"',"+userid+","+userid+",pk_seq,"+nppId+",5   "+
				" from  daidienkinhdoanh   "+
				" where pk_seq not in (select ddkd_fk from tuyenbanhang where ngayid=5 and ddkd_fk="+nvbhid+" and npp_fk="+nppId+") "+
				" and pk_seq="+nvbhid+
				" union  "+
				" select N'Thứ Sáu','Thu sau','"+this.getDateTime()+"','"+this.getDateTime()+"',"+userid+","+userid+",pk_seq,"+nppId+",6  "+ 
				" from  daidienkinhdoanh   "+
				" where pk_seq not in (select ddkd_fk from tuyenbanhang where ngayid=6 and ddkd_fk="+nvbhid+" and npp_fk="+nppId+") "+
				" and pk_seq="+nvbhid+
				" union  "+
				" select N'Thứ Bảy','Thu bay','"+this.getDateTime()+"','"+this.getDateTime()+"',"+userid+","+userid+",pk_seq,"+nppId+",7 "+  
				" from  daidienkinhdoanh   "+
				" where pk_seq not in (select ddkd_fk from tuyenbanhang where ngayid=7 and ddkd_fk="+nvbhid+" and npp_fk="+nppId+") "+
				" and pk_seq=" +nvbhid;
		db.update(sql);
		System.out.println("Them Tuyen ban hang : "+sql);
		// xoa het tuyen cu cua nhan vien ban hàng này đi
		sql="delete khachhang_tuyenbh where tbh_fk in (select pk_seq from tuyenbanhang where ddkd_fk="+nvbhid +") ";
		System.out.println("Xoa TBH : "+sql);
		if(! db.update(sql)){
			System.out.println("xoa Tuyen ban hang : "+sql);
		}

		Hashtable<String,String> htbtuyen= new Hashtable<String, String>();
		sql="select pk_seq,ngayid,npp_fk,ddkd_fk from tuyenbanhang where ddkd_fk="+nvbhid +" and npp_fk="+nppId;


		ResultSet rs=db.get(sql);
		try{
			while (rs.next()){
				htbtuyen.put(rs.getString("npp_fk").trim()+"_"+ rs.getString("ddkd_fk").trim()+"_"+ rs.getString("ngayid").trim() , rs.getString("pk_seq").trim());

			}
		}catch(Exception er){
			System.out.println("Loi lay tuyen : "+er.toString());
		}
		return htbtuyen;

	}

	String getValue(Sheet sheet,int col,int row,boolean replaceDauPhay)
	{
		try{
			if(replaceDauPhay)
				return sheet.getCell(col,row).getContents().trim().replaceAll(",", "").trim();
			else 
				return sheet.getCell(col,row).getContents().trim().trim();
		}catch (Exception e) {
			return "";
		}
	}
	public static boolean isNumericOnlyNumber(String str)
	{
		return str.matches("\\d+");
	}  
	String getValue(Sheet sheet,int col,int row)
	{
		return sheet.getCell(col,row).getContents().trim().replaceAll(",", "");
	}
	
	private String getDateTime_MaTmp()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
		Date date = new Date();
		return dateFormat.format(date);
	}
}
