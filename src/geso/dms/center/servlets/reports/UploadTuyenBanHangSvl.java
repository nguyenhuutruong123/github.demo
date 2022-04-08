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
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
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
public class UploadTuyenBanHangSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;



	public UploadTuyenBanHangSvl() 
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
		obj.initUploadMCP();

		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);
		String nextJSP = "/AHF/pages/Center/UploadTuyenBanHang.jsp";
		response.sendRedirect(nextJSP);

	}
	void XuatExcel (String action , IStockintransit obj , String userId, HttpServletResponse response) throws Exception
	{
		if(action != null)
		{
			if(action.equals("exportmcp")){			
				obj.setuserId(userId);
				XuatFileExcelSR(obj,response,obj.getnppId());
				return;
			}
			if(action.equals("exportmcpds")){			
				obj.setuserId(userId);
				XuatFileExcelSRDS(response,obj.getnppId());
			}
			return;
		}

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

		obj.setvungId(Utility.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId"))==null? "":Utility.antiSQLInspection(request.getParameter("vungId"))));	   	 
		obj.setkhuvucId(Utility.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId"))==null? "":Utility.antiSQLInspection(request.getParameter("khuvucId"))));	 
		obj.setnppId(Utility.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId"))==null? "":Utility.antiSQLInspection(request.getParameter("nppId"))));


		if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0)) 
		{	
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=THONGTINUPLOAD.xls");
			WritableWorkbook w = Workbook.createWorkbook(response.getOutputStream());
			MultipartRequest multi = new MultipartRequest(request, "C:\\java-tomcat\\data\\", 20000000, "UTF-8"); 
			//getServletContext().getRealPath("\\pages\\Templates")+"\\BCDonHangBanTrongKyNPP.xlsm"

			//MultipartRequest multi = new MultipartRequest(request, getServletContext().getRealPath("\\upload\\"), 20000000, "UTF-8"); 
			userId = Utility.antiSQLInspection(multi.getParameter("userId"));
			obj.setuserId(userId);
			obj.setvungId(Utility.antiSQLInspection(multi.getParameter("vungId"))==null? "":Utility.antiSQLInspection(multi.getParameter("vungId")));	   	 
			obj.setkhuvucId(Utility.antiSQLInspection(multi.getParameter("khuvucId"))==null? "":Utility.antiSQLInspection(multi.getParameter("khuvucId")));	 
			obj.setnppId(Utility.antiSQLInspection(multi.getParameter("nppId"))==null? "":Utility.antiSQLInspection(multi.getParameter("nppId")));



			if(action != null)
			{
				try {
					XuatExcel ( action ,  obj ,  userId,  response) ;

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					action = null;
				}


			}
			else
			{

				dbutils db=new dbutils();
				Enumeration<?> files = multi.getFileNames(); 
				String filenameu  ="";
				while (files.hasMoreElements())
				{
					String name = (String)files.nextElement();
					filenameu = multi.getFilesystemName(name); 
					System.out.println("name: "+name);;
				}

				String filename = "C:\\java-tomcat\\data\\"+ filenameu;
				//String filename = getServletContext().getRealPath("\\upload\\")+ "\\"+ filenameu;
				if (filename.length() > 0)
				{
					//doc file excel
					File file = new File(filename);
					System.out.println("filename  "+file);
					Workbook workbook;
					WorkbookSettings ws = new WorkbookSettings();
					ws.setEncoding("ISO8859_1");


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
						workbook = Workbook.getWorkbook( file, ws );
						Sheet[] sheet1 = workbook.getSheets();

						Hashtable<String, String> htp_kbh= gethtpKenhBanHang(db); 
						Hashtable<String, String> htp_hch=gethtpHangcuahang(db);
						Hashtable<String, String> htp_lch=gethtpLoaicuahang(db);
						Hashtable<String, String> htp_vtch=gethtpVitricuahang(db);
						Hashtable<String, String> htp_Tinhthanh=gethtpTinhThanh(db);
						Hashtable<String, String> htp_quanhuyen=gethtpQuanHuyen(db);
						Hashtable<String, String> htp_nhomhk=getNhomKh(db);
						Hashtable<String, String[]> htp_DDKD=getHTP_DDKD_Thuoc_NPP(db,obj.getnppId());

						Hashtable<String, String> htp_tanso=gethtpTanso(db);
						
						
						boolean error=false;
						boolean flag = true;
						// Kiểm tra các mã nhân viên này có đúng mã và phải của nhà
						// phân phối đã chọn ko?
						int sokh = 0;
						db.update("SET TRANSACTION ISOLATION LEVEL SNAPSHOT;");
						db.update("SET LOCK_TIMEOUT 60000;");

						/*					for (int t = 0; t < sheet1.length; t++)

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

					}*/

						if(obj.getnppId().trim().length() <=0)
						{
							error = true;
							obj.setMsg(" vui lòng chọn NPP");
						}

						if(!error)
						{

							int t = 0;
							{


								//TAO RA 1 SHEET LUU THONG TIN UPLOAD KHONG THANH CONG
								WritableSheet sheetwrite = w.createSheet(sheet1[t].getName(), t);
								//sheetwrite.addCell(new Label(0, 0, "THÔNG TIN CẬP NHẤT TUYẾN CỦA NHÂN VIÊN BÁN HÀNG CÓ MÃ: "+ sheet1[t].getName()));
								sheetwrite.addCell(new Label(0, 0, "THÔNG TIN CẬP NHẤT TUYẾN CỦA NHÂN VIÊN BÁN HÀNG CÓ MÃ: " ));
								Sheet sheet=sheet1[t];

								//String ddkdid=sheet.getName().trim();
								// Xóa tuyến cũ, tạo các tuyến chưa có cho nhân viên bán hàng !
								//Hashtable<String,String> htbtuyen= Htb_TuyenBH(obj.getnppId(),userId,ddkdid,db);

								Cell[] cells ;


								for(int i= 5; i < sheet.getRows();i++)
								{
									int indexRow=i;
									cells = sheet.getRow(i);
									System.out.println("");
									if (cells.length>0)
									{
										int rowstart = 0;
										if(cells[0] !=null && cells[0].getContents().toString().length() >0 )
										{	
											String errror = "";
											db.getConnection().setAutoCommit(false);

											sott = getValue(sheet, rowstart++,i);
											coderoute = getValue(sheet, rowstart++,i);
											routename = getValue(sheet, rowstart++,i);

											String codenvbh = getValue(sheet, rowstart++,i);// check NVBH
											String[] ddkdArr = htp_DDKD.get(codenvbh);
											
											String ddkdId = "";
											String kenhDDKD = "";
											if (ddkdArr == null || ddkdArr.length !=2 || ddkdArr[0] == null || ddkdArr[0].length() <=0 ) // || ddkdArr[1] == null || ddkdArr[1].length() <=0 
											{
												sheetwrite.addCell(new Label(0, i, "Mã NVBH ("+codenvbh+") không tồn tại:" ));
												db.getConnection().rollback();
												db.getConnection().setAutoCommit(true);
												continue;
											}
											else
											{
												ddkdId = ddkdArr[0];
												kenhDDKD = ddkdArr[1];
											}
											
											

											//Không đọc
											String tennvbh = getValue(sheet, rowstart++,i);

											boolean isTaomoi = false;
											String _makh = getValue(sheet, rowstart++,i);
											if (_makh != null && _makh.trim().toUpperCase().equals("NA") || (_makh == null || _makh.length() <= 0)) {
												_makh = "NA_Tmp" + getDateTime_MaTmp() +"_" +i;
												isTaomoi = true;
											}
											makh = _makh;

											String mathamchieu = getValue(sheet, rowstart++,i);
											if (mathamchieu != null) {
												mathamchieu = mathamchieu.trim();
											}

											tencuahieu = getValue(sheet, rowstart++,i).trim().replace("'", "''");
											chucuahieu = getValue(sheet, rowstart++,i).trim().replace("'", "''");
											sodienthoai = getValue(sheet, rowstart++,i).trim().replace("'", "''");
											diachi = getValue(sheet, rowstart++,i).trim().replace("'", "''");

											phuong = getValue(sheet, rowstart++,i).trim().replace("'", "''");
											//Không đọc
											String pxten = getValue(sheet, rowstart++,i).trim().replace("'", "''");

											//quanhuyen=htp_quanhuyen.get(quanhuyen).trim().replace("'", "''");
											//tinhthanh = htp_Tinhthanh.get(tinhthanh).trim().replace("'", "''");

											quanhuyen = getValue(sheet, rowstart++,i).trim().replace("'", "''");
											//Không đọc
											String qhten = getValue(sheet, rowstart++,i).trim().replace("'", "''");

											tinhthanh = getValue(sheet, rowstart++,i).trim().replace("'", "''");
											String ttten = getValue(sheet, rowstart++,i).trim().replace("'", "''");

											
											
											
											String kbhTen =  getValue(sheet, rowstart++,i).trim().replace("'", "''");
											
											if(!htp_kbh.containsKey(kbhTen))
											{
												sheetwrite.addCell(new Label(0, i, "Kênh ("+kbhTen+") không tồn tại:" ));
												db.getConnection().rollback();
												db.getConnection().setAutoCommit(true);
												continue;
											}
											String kbh_fk = htp_kbh.get(kbhTen);
											/*
											if(!kenhDDKD.equals(kbh_fk))
											{
												sheetwrite.addCell(new Label(0, i, "Kênh khách hàng khác kênh của NVBH " ));
												db.getConnection().rollback();
												db.getConnection().setAutoCommit(true);
												continue;
											}*/
											
											
											
											vitricuahang = getValue(sheet, rowstart++,i).trim().replace("'", "''");
											loaicuahang	= getValue(sheet, rowstart++,i).trim().replace("'", "''");

											String _hangcuahang = getValue(sheet, rowstart++,i);
											hangcuahang = _hangcuahang == null?"": _hangcuahang.trim().replace("'", "''");
											tuyenbanhang = getValue(sheet, rowstart++,i,false).trim().replace("'", "''");
											
											tanso = getValue(sheet, rowstart++,i).trim().replace("'", "");
											if(tanso.trim().length() <=  0)
											{
												errror=errror+  " . Không được để trống tần số viếng thăm "+ (indexRow+1);
											}
											else
											{
												tanso = htp_tanso.get(tanso);
												if(tanso == null)
												{
													errror=errror+  " . Tần số ở dòng "+ (indexRow+1) + " không hợp lệ";
												}
											}
											
											
											
											
											
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

											String latStr = getValue(sheet, rowstart++,i,false).trim().replace("'", "''");
											String lonStr = getValue(sheet, rowstart++,i,false).trim().replace("'", "''");
											double lat = 0;
											double lon = 0;


											
											lch_fk = htp_lch.get(loaicuahang);
											hch_fk = htp_hch.get(hangcuahang);
											vtch_fk = htp_vtch.get(vitricuahang);
											nhomkh_fk = htp_nhomhk.get(nhomkhachhhang);
											
											int kt = 0;	
											String query = "";
											String mavung = "", ma = "";
											ResultSet rs1 = null;

											int stt_max = -1;
											if (makh.contains("NA_Tmp")) {
												stt_max = Khachhang.MaKhachHang(db);
												query = "select dbo.PlusZero("+stt_max+",7) ma";
												temprs = db.get(query);
												temprs.next();
												String makh_tutang = temprs.getString("ma");
												temprs.close();
												ma = makh_tutang;

											}
											else {
												ma = makh;
											}

											if(phuong.length() >50 )
											{
												errror=errror+  " . Độ dài ký tự phường không hợp lệ "+ (indexRow+1);
											}

											if(nhomkhachhhang.length() >50 )
											{
												errror=errror+  " . Độ dài ký tự nhóm khách hàng không hợp lệ "+ (indexRow+1);
											}

											if(latStr.trim().length() > 0 && lonStr.trim().length() > 0 )
											{
												try
												{
													lat = Double.parseDouble(latStr.replace(",",""));
													lon = Double.parseDouble(lonStr.replace(",",""));
												}
												catch (Exception e) {
													errror=errror+  " . Tọa độ không hợp lệ: dòng "+ (indexRow+1);
												}
											}

											if(!errror.equals(""))
											{
												sheetwrite.addCell(new Label(0, indexRow, errror));
												db.getConnection().rollback();
												db.getConnection().setAutoCommit(true);
												continue;
											}

											int soDong = -1;
											if(tinhthanh == null) tinhthanh = "";
											if(quanhuyen == null) quanhuyen = "";
											String	sql="";
											
											boolean isChuyenNPP  = false;
											String nppId_Old = obj.getnppId();
											if ((makh.trim().length() > 0) && 
								                      (mathamchieu.length() > 0) && (!mathamchieu.equals("NA")))
								                    {
								                      sql = " select smartId from khachhang where mathamchieu = '" + mathamchieu + "' and pk_seq not in (select pk_seq from khachhang where smartid = '" + makh + "') ";
								                       temprs = db.get(sql);
								                      if (temprs.next())
								                      {
								                        String smartidtrungmf = temprs.getString("smartId");
								                        System.out.println("Da co kh trung mathamchieu" + sql);
								                        sheetwrite.addCell(new Label(0, indexRow, "Khách hàng trùng mã tham chiếu với khác hàng mã: " + smartidtrungmf + " "));
								                        db.getConnection().rollback();
								                        db.getConnection().setAutoCommit(true);
								                        continue;
								                      }
								                      temprs.close();
								                    }
											String mathamchieuIns ="null";
											if (mathamchieu.length() > 0 && !mathamchieu.equals("NA")) 
											{
												mathamchieuIns = "N'"+mathamchieu+"'";
											}
											if(makh.length() <= 0 || makh.contains("NA_Tmp"))
											{
												String latInsert ="null";
												String lonInsert ="null";
												if(lat > 0 && lon > 0)
												{
													latInsert = "'" + lat + "'";
													lonInsert = "'" + lon + "'";
												}
												
												System.out.println("quanhuyen = "+ quanhuyen);
												System.out.println("tinhthanh = "+ tinhthanh);

												sql = " select smartId from khachhang where    diachi =N'"+diachi+"' and dienthoai = N'"+sodienthoai+"' and quanhuyen_fk = "+quanhuyen;
												System.out.println("sql count = "+ sql);
												temprs = db.get(sql);																						
												if(temprs.next())
												{
													String makhTrung = temprs.getString("smartId");

													System.out.println("Da co kh" + sql);
													sheetwrite.addCell(new Label(0, indexRow, "Kh  mã ("+makhTrung+") có sẵn  đang trùng các thông tin :  ("+sodienthoai+"),("+diachi+"), ("+qhten+") "  ));
													db.getConnection().rollback();
													db.getConnection().setAutoCommit(true);
													continue;
												}


												sql = 	"\n insert into khachhang (daduyet,coderoute,routename,mathamchieu,ten,trangthai,ngaytao,ngaysua,nguoitao,nguoisua,kbh_fk,"+ 
														"\n hch_fk,lch_fk,npp_fk,vtch_fk,dienthoai,diachi,tinhthanh_fk, "+
														"\n quanhuyen_fk,smartid,phuongxa_fk,chucuahieu,songayno,sotienno,dientichcuahang,import) " +
														"\n select 1,N'"+coderoute+"',N'"+routename+"',"+mathamchieuIns+"," +
														"\n N'"+tencuahieu+"','"+trangthai+"','"+this.getDateTime()+"','"+this.getDateTime()+"'," +
														"\n "+userId+","+userId +","+kbh_fk+","+hch_fk+","+lch_fk+","+obj.getnppId()+","+vtch_fk+"," +
														"\n '"+sodienthoai+"',N'"+diachi+"',"+(tinhthanh.length() <= 0?null:tinhthanh)+", " +
														"\n "+(quanhuyen.length() <= 0?null:quanhuyen)+",'"+ma+"',"+(phuong.length() <= 0?null:phuong)+"," +
														"\n N'"+chucuahieu+"','"+songayno+"','"+sotienno+"','"+dientichcuahang+"','1'  " +
														"\n where not exists (select 1 from khachhang where npp_fk="+obj.getnppId().trim() +" " +
														"\n and smartid='"+ma.trim()+"') ";


												//System.out.println("Query Insert: "+query);
												soDong=db.updateReturnInt(sql);
												if(soDong<0)
												{
													System.out.println("Tạo khách hàng không thành công:" + sql);
													sheetwrite.addCell(new Label(0, indexRow, "Tạo mới khách hàng không thành công, vui lòng kiểm tra lại các thông tin: " + sql));
													db.getConnection().rollback();
													db.getConnection().setAutoCommit(true);
													continue;
												}

												query = "select scope_identity() abc";
												temprs = db.get(query);
												String khId = "";
												temprs.next();
												khId = temprs.getString("abc");
												temprs.close();

												if (khId != null && khId.length() > 0 && stt_max != -1  ) {
													String kq = Khachhang.Log_MaKhachHang(db,khId,stt_max);
													if(kq.trim().length() > 0)
													{	
														sheetwrite.addCell(new Label(0, indexRow, "Không thể tạo Log_MaKhachHang" + kq));
														db.getConnection().rollback();
														db.getConnection().setAutoCommit(true);
														continue;
													}
												}
												else
												{
													sheetwrite.addCell(new Label(0, indexRow, "Không thể tạo Log_MaKhachHang 2" ));
													db.getConnection().rollback();
													db.getConnection().setAutoCommit(true);
													continue;
												}
											}else
											{
												
												sql = " select pk_seq,NPP_FK from  khachhang where smartid ='"+makh.trim()+"' ";
												temprs = db.get(sql);	
												if(!temprs.next())
												{
													sheetwrite.addCell(new Label(0, indexRow, "Mã khách hàng ("+makh+") không tồn tại! "));
													db.getConnection().rollback();
													db.getConnection().setAutoCommit(true);
													continue;
												}
												String khId = temprs.getString("pk_seq");
												nppId_Old = 	temprs.getString("NPP_FK");
												isChuyenNPP =  !obj.getnppId().equals(nppId_Old);
												
												
												// Trường hợp điền mã kh, cập nhật khách hàng theo mã đã điền trong File Excel
												sql =  "\n update khachhang set mathamchieu = "+mathamchieuIns+", NPP_FK = "+obj.getnppId()+"  ,kbh_fk = " + kbh_fk  + " , coderoute=N'"+coderoute+"',routename=N'"+routename+"',trangthai='"+trangthai+"', ten=N'"+tencuahieu+"',hch_fk="+hch_fk+",lch_fk="+lch_fk+",vtch_fk="+vtch_fk+", " +
														"\n dienthoai=N'"+sodienthoai+"',diachi=N'"+diachi+"',tinhthanh_fk= " +(tinhthanh.length() <= 0?null:tinhthanh)+ "," +
														"\n quanhuyen_fk="+(quanhuyen.length() <= 0?null:quanhuyen)+" ,phuongxa_fk ="+(phuong.length() <= 0?null:phuong)+",chucuahieu=N'"+chucuahieu+"', " +
														"\n songayno ='"+songayno+"' , sotienno = '"+sotienno+"', dientichcuahang = '"+dientichcuahang+"',nguoisua = "+userId+", ngaysua = '"+this.getDateTime()+"' " +
												( lat > 0 && lon > 0 ? " , lat = '"+lat+"' ,long = '"+lon+"'  ":""  )+
												"\n where pk_seq =  "+ khId;
//												System.out.println("Query Update: " + sql);
												if(db.updateReturnInt(sql) <= 0 )
												{
													sheetwrite.addCell(new Label(0, indexRow, "Cập nhật khách hàng không thành công, vui lòng kiểm tra mã khách hàng hoặc các thông tin liên quan :" + sql));
													db.getConnection().rollback();
													db.getConnection().setAutoCommit(true);
													continue;
												}
												if(isChuyenNPP)
												{
													sql =   " insert KhachHang_NPP_Log (khachhang_fk,NPP_FK,nguoisua) " +
															" select pk_seq,npp_fk,nguoisua from khachhang where pk_seq =   " + khId;
													db.update(query);
												}
											}

											sql = "\n update khachhang  set TimKiem = dbo.ftBoDau(ten)+'-'+SMARTID where npp_fk="+obj.getnppId().trim() +" and smartid='"+ma.trim()+"'";
											if(!db.update(sql))
											{
												sheetwrite.addCell(new Label(0, indexRow, "Cập nhật khách hàng không thành công:" + sql));
												System.out.println("4");
												db.getConnection().rollback();
												db.getConnection().setAutoCommit(true);
												continue;
											}


											sql=" delete from NHOMKHACHHANG_KHACHHANG where kh_FK = (select pk_seq from KhachHang where smartid ='"+ma+"' and npp_fk='"+obj.getnppId()+"') ";
											if(!db.update(sql))
											{

												sheetwrite.addCell(new Label(0, indexRow , "Xóa NHOMKHACHHANG_KHACHHANG không thành công  :" + sql));
												System.out.println("5");
												db.getConnection().rollback();
												db.getConnection().setAutoCommit(true);
												continue;
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

												if(db.updateReturnInt(sql) <= 0)
												{
													sheetwrite.addCell(new Label(0, indexRow , "Tạo nhóm khách hàng không thành công, nhóm khách hàng không tồn tại: " + sql));
													System.out.println("6");
													db.getConnection().rollback();
													db.getConnection().setAutoCommit(true);
													continue;
												}
											}

											sql=" delete from khachhang_htkd where khachhang_Fk =(select pk_seq from KhachHang where smartId='"+ma+"' and npp_fk='"+obj.getnppId()+"') ";
											if(!db.update(sql))
											{
												sheetwrite.addCell(new Label(0, indexRow , "Xóa khách hàng - HTKD không thành công  :" + sql));
												db.getConnection().rollback();
												db.getConnection().setAutoCommit(true);
												continue;

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

												if(db.updateReturnInt(sql) <= 0)
												{

													sheetwrite.addCell(new Label(0, indexRow , "Tạo khách hàng - hình thức kinh doanh không thành công, sai hình thức kinh doanh:" + sql));
													System.out.println("8");
													db.getConnection().rollback();
													db.getConnection().setAutoCommit(true);
													continue;
												}
											}

											sql=" delete from NVGN_KH where khachhang_Fk =(select top 1 pk_seq from KhachHang where smartid ='"+ma+"') ";

											if(!db.update(sql))
											{
												System.out.println("Tạo khách hàng không thành công :" + sql);
												sheetwrite.addCell(new Label(0, indexRow , "Xóa NVGN không thành công  :" + sql));
												System.out.println("9");
												db.getConnection().rollback();
												db.getConnection().setAutoCommit(true);
												continue;
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

											if(db.updateReturnInt(sql) <= 0)
											{
												System.out.println("Tạo khách hàng không thành công :" + sql);
												sheetwrite.addCell(new Label(0, indexRow , "Tạo khách hàng - NVGN không thành công:" + sql));
												System.out.println("10");
												db.getConnection().rollback();
												db.getConnection().setAutoCommit(true);
												continue;
											}

										

											sql = "\n delete a from khachhang_tuyenbh a inner join khachhang b on b.pk_seq=a.khachhang_fk " +
											"\n where b.smartid = '" + ma.trim() + "'  ";
											if (!db.update(sql))
											{
												System.out.println("Khong thanh cong do :" + sql);
												sheetwrite.addCell(new Label(1, indexRow, "Xóa tuyến không thành công  :" + sql));
												System.out.println("12");
												db.getConnection().rollback();
												db.getConnection().setAutoCommit(true);
												continue;
											}	

											if(tanso.length() <=  0)
											{
												System.out.println("Khong thanh cong do :" + sql);
												sheetwrite.addCell(new Label(1, indexRow, "Tần số sai, không được để trống:" + sql));
												System.out.println("13");
												db.getConnection().rollback();
												db.getConnection().setAutoCommit(true);
												continue;
											}

											
											sql = "\n insert into khachhang_tuyenbh (khachhang_fk,tbh_fk,tanso,sott) " + 
											"\n	 select a.pk_seq,b.pk_seq,'"+tanso+"','"+sott+"' "+
											"\n	 from "+
											"\n	 ( "+
											"\n		select pk_seq from KhachHang where smartid ='"+ma.trim()+"' and npp_fk='"+obj.getnppId()+"'  "+
											"\n	 )a , "+
											"\n	 ( "+
											"\n		select pk_seq  "+
											"\n		from tuyenbanhang "+
											"\n		where npp_Fk='"+obj.getnppId()+"' and ddkd_fk='"+ddkdId+"' and NgayId in ( select dbo.trim( data) from dbo.Split(N'"+tuyenbanhang+"',';') )  "+
											"\n	 )b ";

											int songdongMCP = db.updateReturnInt(sql);

											if (songdongMCP <= 0)
											{
												System.out.println("Lỗi: " + sql);
												sheetwrite.addCell(new Label(1, indexRow, "Tuyến bán hàng không tồn tại hoặc không được để trống tuyến:" + sql));
												System.out.println("15");
												db.getConnection().rollback();
												db.getConnection().setAutoCommit(true);
												continue;
											}	

											sql =" update khachhang set DDKDTao_fk = "+ddkdId+" where smartid ='"+ma.trim()+"' and npp_fk='"+obj.getnppId()+"' and DDKDTao_fk is null ";
											if(!db.update(sql))
											{
												System.out.println("Lỗi: " + sql);
												sheetwrite.addCell(new Label(1, indexRow, "Tuyến bán hàng không tồn tại hoặc không được để trống tuyến:" + sql));
												System.out.println("15");
												db.getConnection().rollback();
												db.getConnection().setAutoCommit(true);
											}
											
											if(songdongMCP > 1)// nhiu hon 1 tuyen
											{
												int soF =  4 * songdongMCP;
												String ts = "F" +soF;
												query = " update khachhang_tuyenbh set tanso = '"+ts+"'  " +
												" where khachhang_fk in (select pk_seq from KhachHang where smartid ='"+ma+"' and npp_fk='"+obj.getnppId()+"' )" +
												" 		and tbh_fk in (select pk_seq from tuyenbanhang where npp_Fk='"+obj.getnppId()+"' and ddkd_fk='"+ddkdId+"' )  ";
												db.update(query);
											}

											query = "\n update kh set kh.route_fk = ddkd.route_fk " +
											"\n from khachhang_tuyenbh a " +
											"\n inner join khachhang kh on kh.pk_seq = a.khachhang_fk " +
											"\n inner join tuyenbanhang b on a.tbh_fk = b.pk_seq " +
											"\n inner join daidienkinhdoanh ddkd on ddkd.pk_seq = b.ddkd_fk " +
											"\n where ddkd.pk_seq = "+ddkdId;
											db.update(query);

											sheetwrite.addCell(new Label(0, indexRow, "Upload thành công  khách hàng!" ));
											db.getConnection().commit();
											db.getConnection().setAutoCommit(true);

											sokh++;
										}
									}
									indexRow++;
								}
							}

							System.out.println("flag: "+flag+" - "+sokh);
							if( sokh > 0)
							{
								obj.setMsg("Upload thành công "+sokh+" khách hàng!");
								String sql =" insert uploadmcp_log select '"+getDateTime1()+"','"+userId+"','"+obj.getnppId()+"' ";
								if(!db.update(sql))
								{
									System.out.println("Lỗi: " + sql);
									System.out.println("15");
									db.getConnection().rollback();
									db.getConnection().setAutoCommit(true);
								}
								w.write();
								w.close();
							}
							else
							{
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
						obj.setMsg("Upload không thành công! Vui lòng xem file !");
						w.write();
						try {
							w.close();
						} catch (WriteException e) {
							e.printStackTrace();
						}
					}

					Cookie fileDwnld = new Cookie("fileDownload", "true");
					fileDwnld.setPath("/");
					response.addCookie(fileDwnld);
					
					//return;	
					
				}
			}
		}

		if(action != null && !action.equals("seach"))
		{
			try {
				XuatExcel ( action ,  obj ,  userId,  response) ;
				return;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("obj.getuser"+obj.getuserId());

		obj.initUploadMCP();

		//Cookie fileDwnld = new Cookie("fileDownload", "true");
		//fileDwnld.setPath("/");
		//response.addCookie(fileDwnld);
		System.out.println("userId"+userId);

		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);
		String nextJSP = "/AHF/pages/Center/UploadTuyenBanHang.jsp";
		response.sendRedirect(nextJSP);
	}

	private void XuatFileExcelSR(IStockintransit obj, HttpServletResponse response,String NppId) throws IOException 
	{
		OutputStream out1 = null;
		try {

			dbutils db=new dbutils();
			String npp =  "select dbo.ftbodau2(ten) ten from nhaphanphoi where pk_seq = "+NppId+" ";
			String ten = "";
			ResultSet tennppRs = db.get(npp);
			if(tennppRs != null) {
				if(tennppRs.next())
				{
					ten = tennppRs.getString("ten");
					tennppRs.close();
				}
			}
			 npp =  "select top(1) a.ngaygio,b.ten   from uploadmcp_log a left join nhanvien b on a.ngaysua=b.pk_seq where a.npp_fk = "+NppId+" order by a.ngaygio desc";
			String nguoisua = "";
			String ngaysua = "";
			System.out.println("npp haha : "+npp);

			 tennppRs = db.get(npp);
			if(tennppRs != null) {
				if(tennppRs.next())
				{
					nguoisua = tennppRs.getString("ten");
					ngaysua = tennppRs.getString("ngaygio");

					tennppRs.close();
				}
			}
			response.setContentType("application/vnd.ms-excel");
			response.setHeader
			("Content-Disposition", "attachment; filename=MCP_"+ten+".xls");
			WritableWorkbook w = jxl.Workbook.createWorkbook(response.getOutputStream());

			String sql  = " select  gsbh_fk, asm_fk from NHANVIEN where pk_seq =  " + obj.getuserId();
			ResultSet rs=db.get(sql);
			String gsbh_fk = "";
			String asm_fk = "";
			if(rs.next())
			{
				gsbh_fk = rs.getString("gsbh_fk") == null ? "": rs.getString("gsbh_fk");
				asm_fk = rs.getString("asm_fk") == null ? "": rs.getString("asm_fk");
			}
			
			sql = "\n select npp.ten as tennpp ,npp.ma,kv.ten as tenkv " + // ,ddkd.pk_seq as ddkdid,ddkd.ten  as ddkdten 
			"\n from nhaphanphoi npp  "+
			"\n left join khuvuc kv  on kv.pk_Seq=npp.khuvuc_fk "+ 
			"\n where npp.pk_seq="+NppId ;	
			
			System.out.println("sql upload : "+sql);

			rs=db.get(sql);
			int k=0;
			if (rs.next()){
				WritableSheet sheet = w.createSheet("MCP", k); // rs.getString("ddkdid")
				sheet.addCell(new Label(0, 0, "Ngày sửa gần nhất : "));sheet.addCell(new Label(1, 0, ""+ngaysua));

				sheet.addCell(new Label(0, 1, "Người sửa gần nhất : "));sheet.addCell(new Label(1, 1, ""+nguoisua ));
				
				sheet.addCell(new Label(0, 2, "NPP : "));sheet.addCell(new Label(1, 2, ""+rs.getString("tennpp") ));

				sheet.addCell(new Label(0, 3, "KHU VỰC : "));sheet.addCell(new Label(1, 3, ""+rs.getString("tenkv") ));

				//sheet.addCell(new Label(0, 3, "NVBH : "));sheet.addCell(new Label(1, 3, ""+rs.getString("ddkdten") ));

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
				sheet.addCell(new Label(count++, 4, "MÃ PHƯỜNG/XÃ",cellFormat));
				sheet.addCell(new Label(count++, 4, "TÊN PHƯỜNG/XÃ",cellFormat));
				sheet.addCell(new Label(count++, 4, "MÃ QUẬN/HUYỆN",cellFormat));
				sheet.addCell(new Label(count++, 4, "TÊN QUẬN/HUYỆN",cellFormat));
				sheet.addCell(new Label(count++, 4, "MÃ TỈNH/THÀNH PHỐ",cellFormat));
				sheet.addCell(new Label(count++, 4, "TÊN TỈNH/THÀNH PHỐ",cellFormat));
				sheet.addCell(new Label(count++, 4, "KÊNH BÁN HÀNG",cellFormat));
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
				sheet.addCell(new Label(count++, 4, "VĨ ĐỘ",cellFormat));
				sheet.addCell(new Label(count++, 4, "KINH ĐỘ",cellFormat));

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

				sql = "\n select distinct isnull(px.ten,'')pxten,isnull(qh.ten,'')qhten,isnull(tt.ten,'')ttten, " +
				"\n ddkd.SMARTID codenvbh,ddkd.ten tennvbh,kh.coderoute,kh.routename,isnull(kh.mathamchieu,'')mathamchieu, " +
				"\n vt.vitri ,hch.hang as hangcuahang,lch.loai as loaicuahang,kh.phuongxa_FK as phuong,  "+  
				"\n	kh.nguoidaidien,kh.chucuahieu,kh.tinhthanh_fk,kh.quanhuyen_fk,   "+
				"\n	kh.ten as tencuahieu,kh.smartid,kh.diachi,kh.dienthoai,kh.trangthai, "+ 
				"\n	case when kh.lat is not null and kh.long is not null then '1' else '0' end as toado , "+
				"\n	stuff      "+
				"\n	(     "+
				"\n		(    "+	
				"\n			select distinct top 100 percent ' ; ' + N''+cast(bb.ngayid as varchar(2)) "+
				"\n			from khachhang_tuyenbh aa inner join tuyenbanhang bb on bb.pk_seq=aa.tbh_fk "+ 
				"\n			where   aa.khachhang_fk=kh.pk_seq and bb.npp_fk=tbh.npp_fk and bb.ddkd_fk=tbh.ddkd_fk "+
				"\n			order by ' ; ' + N''+cast(bb.ngayid as varchar(2)) "+
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
				"\n	) + ' '  as nhomkh, kh.songayno,kh.sotienno , kh.lat , kh.long, isnull(kbh.ten,'') kenh "+
				"\n	from tuyenbanhang tbh "+ 
				"\n	inner join khachhang_tuyenbh khtbh on tbh.pk_seq=khtbh.tbh_fk "+  
				"\n	inner join khachhang kh on kh.pk_seq=khtbh.khachhang_fk  and kh.npp_fk=tbh.npp_fk " +
				"\n left join kenhbanhang kbh on kbh.pk_seq = kh.kbh_fk "+
				"\n	left join hangcuahang hch on hch.pk_seq=kh.hch_fk "+  
				"\n	left join loaicuahang lch on lch.pk_seq=kh.lch_fk "+  
				"\n	left join vitricuahang vt on vt.pk_seq=kh.vtch_fk  "+
				"\n left join daidienkinhdoanh ddkd on ddkd.pk_seq in (select ddkd_fk from tuyenbanhang where pk_seq = khtbh.tbh_fk) " +
				"\n left join phuongxa px on px.pk_seq = kh.phuongxa_fk " +
				"\n left join quanhuyen qh on qh.pk_seq = kh.quanhuyen_fk " +
				"\n left join tinhthanh tt on tt.pk_seq = kh.tinhthanh_fk " +
				"\n where tbh.npp_fk= "+NppId+" and tbh.ddkd_fk= xxx.pk_seq ";
				
				
				if(asm_fk.trim().length() > 0)
				{
					
					sql +=	"\n and exists ( select 1 from GIAMSATBANHANG gs 	" +
							"\n					inner join ddkd_gsbh x on x.gsbh_fk = gs.pk_seq" +
							"\n					inner join asm on asm.pk_seq =  " + asm_fk +
							"\n 					where x.ddkd_fk = ddkd.pk_seq  and gs.kbh_fk = asm.kbh_fk ) "	;
				}
				
				if( gsbh_fk.trim().length() > 0 )
				{
					sql +=" and ddkd.pk_seq in ( select ddkd_fk from ddkd_gsbh x where x.gsbh_fk in  ("+gsbh_fk+")  ) ";
				}
				
				String sqlTong = "\n select data.*  " +
				"\n from daidienkinhdoanh xxx	" +
				"\n cross apply " +
				"\n (" +
				sql +
				"\n ) data" +
				"\n where 1=1	" +
				"\n order by xxx.pk_seq,ngayid ";

				System.out.println("Query Export MCP: "+sqlTong);

				ResultSet rs1=db.get(sqlTong);
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

					label = new Label(count++, j,rs1.getString("pxten"),cellFormat2); 
					sheet.addCell(label); 

					label = new Label(count++, j,rs1.getString("quanhuyen_fk"),cellFormat2); 
					sheet.addCell(label); 

					label = new Label(count++, j,rs1.getString("qhten"),cellFormat2); 
					sheet.addCell(label); 

					label = new Label(count++, j,rs1.getString("tinhthanh_fk"),cellFormat2); 
					sheet.addCell(label);

					label = new Label(count++, j,rs1.getString("ttten"),cellFormat2); 
					sheet.addCell(label);
					
					label = new Label(count++, j,rs1.getString("kenh"),cellFormat2); 
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

					label = new Label(count++, j,rs1.getString("lat"),cellFormat2); 
					sheet.addCell(label);

					label = new Label(count++, j,rs1.getString("long"),cellFormat2); 
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
	
	private Hashtable<String, String> gethtpKenhBanHang(dbutils db) {

		Hashtable<String,String> htbtuyen= new Hashtable<String, String>();
		String sql=" select ten,PK_SEQ FROM KENHBANHANG ";

		ResultSet rs=db.get(sql);
		try{
			while (rs.next()){
				htbtuyen.put(rs.getString("ten").trim(), rs.getString("PK_SEQ").trim());

			}
			rs.close();
		}catch(Exception er){
			System.out.println("Loi lay gethtpKenhBanHang : "+er.toString());
		}
		return htbtuyen;
	}
	


	private Hashtable<String, String[]> getHTP_DDKD_Thuoc_NPP(dbutils db,String nppId) {

		Hashtable<String,String[]> htb= new Hashtable<String,String[]>();
		String sql="select smartId,pk_seq,kbh_fk from daidienkinhdoanh where trangthai = 1 ";
		if(nppId.trim().length() > 0)
		{
			sql +=  " and npp_Fk =" + nppId;
		}

		ResultSet rs=db.get(sql);
		try{
			while (rs.next()){
				String id  = rs.getString("pk_seq") == null ? "" : rs.getString("pk_seq");
				String kbh = rs.getString("kbh_fk") == null ? "" : rs.getString("kbh_fk");
				htb.put(rs.getString("smartId").trim(), new String[] { id, kbh } );

			}
			rs.close();
		}catch(Exception er){
			System.out.println("Loi lay DDKD thuoc NPP : "+er.toString());
		}
		return htb;
	}
	
	
	private Hashtable<String, String> gethtpTanso(dbutils db) {

		Hashtable<String,String> htbtuyen= new Hashtable<String, String>();
		String sql="select DIENGIAI from tanso";

		ResultSet rs=db.get(sql);
		try{
			while (rs.next()){
				htbtuyen.put(rs.getString("DIENGIAI").trim(), rs.getString("DIENGIAI").trim());

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
	private String getDateTime1() 
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd:HH:mm:ss");
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
		return sheet.getCell(col,row).getContents().trim();
	}



	private String getDateTime_MaTmp()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
		Date date = new Date();
		return dateFormat.format(date);
	}
	public static void main(String[] args) {

		String query = "\n select distinct isnull(px.ten,'')pxten,isnull(qh.ten,'')qhten,isnull(tt.ten,'')ttten, " +
		"\n ddkd.SMARTID codenvbh,ddkd.ten tennvbh,kh.coderoute,kh.routename,isnull(kh.mathamchieu,'')mathamchieu, " +
		"\n vt.vitri ,hch.hang as hangcuahang,lch.loai as loaicuahang,kh.phuongxa_FK as phuong,  "+  
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
		"\n	) + ' '  as nhomkh, kh.songayno,kh.sotienno , kh.lat , kh.long "+
		"\n	from tuyenbanhang tbh "+ 
		"\n	inner join khachhang_tuyenbh khtbh on tbh.pk_seq=khtbh.tbh_fk "+  
		"\n	inner join khachhang kh on kh.pk_seq=khtbh.khachhang_fk  and kh.npp_fk=tbh.npp_fk "+
		"\n	left join hangcuahang hch on hch.pk_seq=kh.hch_fk "+  
		"\n	left join loaicuahang lch on lch.pk_seq=kh.lch_fk "+  
		"\n	left join vitricuahang vt on vt.pk_seq=kh.vtch_fk  "+
		"\n left join daidienkinhdoanh ddkd on ddkd.pk_seq in (select ddkd_fk from tuyenbanhang where pk_seq = khtbh.tbh_fk) " +
		"\n left join phuongxa px on px.pk_seq = kh.phuongxa_fk " +
		"\n left join quanhuyen qh on qh.pk_seq = kh.quanhuyen_fk " +
		"\n left join tinhthanh tt on tt.pk_seq = kh.tinhthanh_fk " +
		"\n where  tbh.ddkd_fk= xxx.pk_seq ";
		String sqlTong = "\n select data.*  " +
		"\n from daidienkinhdoanh xxx	" +
		"\n cross apply " +
		"\n (" +
		query +
		"\n ) data" +
		"\n where xxx.trangthai = 1	" +
		"\n order by xxx.pk_seq,ngayid ";


		System.out.println("query = " + sqlTong);

	}

}
