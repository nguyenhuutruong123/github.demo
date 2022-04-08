
package geso.dms.center.servlets.chuongtrinhkhuyenmai;

import geso.dms.center.servlets.sendmail.SendMail;
import geso.dms.center.beans.ctkhuyenmai.*;
import geso.dms.center.beans.ctkhuyenmai.imp.*;
import geso.dms.center.beans.thongtinsanpham.imp.ThongtinsanphamList;
import geso.dms.center.servlets.report.ReportAPI;
import geso.dms.center.util.OracleConnUtils;
import geso.dms.center.util.Utility;
import geso.dms.distributor.db.sql.dbutils;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import com.aspose.cells.BorderLineType;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;
import com.oreilly.servlet.MultipartRequest;

public class CtkhuyenmaiSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

	PrintWriter out;

	public CtkhuyenmaiSvl() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		this.out  = response.getWriter();

		HttpSession session = request.getSession();	    
		ICtkhuyenmaiList obj = new CtkhuyenmaiList();

		Utility util = new Utility();
		out = response.getWriter();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		out.println(userId);

		if (userId.length()==0)
			userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));

		String action = util.getAction(querystring);
		out.println(action);

		String ctkmId = util.getId(querystring);

		if (action.equals("delete"))
		{
			String msg = Delete(ctkmId);
			if(msg.length() > 0)
				obj.setMessage(msg);
		}

		if (action.equals("duyet"))
		{
			String noidung = "duyệt";
			String msg = Duyet(ctkmId, noidung);
			if(msg.length() > 0)
				obj.setMessage(msg);
		}

		if (action.equals("boDuyet"))
		{
			String msg = BoDuyet(ctkmId);
			if(msg.length() > 0)
				obj.setMessage(msg);
		}
		
		if (action.equals("mail"))
		{
			String noidung = "mở chốt";
			String msg = Mail(ctkmId, noidung);
			if(msg.length() > 0)
				obj.setMessage(msg);
		}

		obj.setRequestObj(request);
		obj.setUserId(userId);
		obj.init("");
		session.setAttribute("obj", obj);
		session.setAttribute("dkkmDien_giai", "");
		session.setAttribute("dkkmTungay", "");
		session.setAttribute("dkkmDenngay", "");

		String nextJSP = "/AHF/pages/Center/ChuongTrinhKhuyenMai.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		//this.out = response.getWriter();
		String contentType = request.getContentType();
		ICtkhuyenmaiList obj = new CtkhuyenmaiList();	    
		HttpSession session = request.getSession();
		String userId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"));
		String userTen = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userTen"));

		String diengiai = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diengiai"));
		String tungay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay"));
		String denngay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay"));
		String trangthai = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai"));
		String vung =geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vung"));
		String khuvuc =geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvuc"));
		String npp = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("npp"));
		
		obj.setDiengiai(diengiai);
		obj.setTungay(tungay);
		obj.setDenngay(denngay);
		obj.setTrangthai(trangthai);
		obj.setvung(vung);
		obj.setkhuvuc(khuvuc);
		obj.setnpp(npp);

		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		if (action == null){
			action = "";
		}
		Utility util = new Utility();
		if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0)) 
		{	
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=THONGTINUPLOAD.xls");
			WritableWorkbook w = jxl.Workbook.createWorkbook(response.getOutputStream());


			dbutils db=new dbutils();
			MultipartRequest multi = new MultipartRequest(request, "C:\\java-tomcat\\data\\", 20000000, "UTF-8");
			//MultipartRequest multi = new MultipartRequest(request, getServletContext().getRealPath("\\upload\\"), 20000000, "UTF-8"); 
			userId = util.antiSQLInspection(multi.getParameter("userId"));
			obj.setUserId(userId);
			tungay = multi.getParameter("tungay");
			if(tungay == null)
				tungay = "";
			obj.setTungay(tungay);

			npp = multi.getParameter("npp");
			if(npp == null)
				npp = "";
			obj.setnpp(npp);
			
			denngay = multi.getParameter("denngay");
			if(denngay == null)
				denngay = "";
			obj.setDenngay(denngay);

			vung = multi.getParameter("vung");
			if(vung == null)
				vung = "";
			obj.setvung(vung);
			
			khuvuc = multi.getParameter("khuvuc");
			if(khuvuc == null)
				khuvuc = "";
			obj.setkhuvuc(khuvuc);
			
			trangthai = multi.getParameter("trangthai");
			if(trangthai == null)
				trangthai = "";
			obj.setTrangthai(trangthai);
			diengiai = multi.getParameter("diengiai");
			if(diengiai == null)
				diengiai = "";
			obj.setDiengiai(diengiai);

			Enumeration files = multi.getFileNames(); 
			String filenameu  ="";
			while (files.hasMoreElements())
			{
				String name = (String)files.nextElement();
				filenameu = multi.getFilesystemName(name); 
				System.out.println("name :   "+name);;
			}

			String filename = "C:\\java-tomcat\\data\\" + filenameu;
			//String filename=    getServletContext().getRealPath("\\upload\\")+ "\\"+ filenameu;
			if (filename.length() > 0)
			{
				//doc file excel
				File file = new File(filename);
				System.out.println("filename  "+file);
				Workbook workbook;

				int indexRow=9;

				try 
				{
					String sott="";
					String mactkm="";
					String mactkmErp ="";
					String diengiaikm ="";
					String loaict ="";
					String kenh="";
					String tungaykm="";
					String denngaykm="";
					String kho ="";
					String loaikh="";
					String loaingansach ="";
					String maspmua ="";
					String soluongmua ="";
					String masptra ="";
					String soluongtra ="";
					String manppapdung ="";
					String apdungcho = "";
					String npptuchay = "";
					String chietkhau = "";
					// 0 sellin && sellout, 1 sell in, 2 sell out
					workbook = Workbook.getWorkbook(file);
					Sheet[] sheet1 = workbook.getSheets();
					int soct = 0;
					boolean error=false;
					boolean flag = true;
					// phân phối đã chọn ko?
					db.getConnection().setAutoCommit(false);
					if(!error)
					{
						for(int t=0;t<sheet1.length;t++)
						{
							//TAO RA 1 SHEET LUU THONG TIN UPLOAD KHONG THANH CONG
							WritableSheet sheetwrite = w.createSheet(sheet1[t].getName(), t);
							sheetwrite.addCell(new Label(0, 0, "THÔNG TIN UPLOAD KHUYẾN MÃI KHÔNG THÀNH CÔNG:"+ sheet1[t].getName()));
							Sheet sheet=sheet1[t];
							Cell[] cells ;
							indexRow=3;

							for(int i= indexRow; i < sheet.getRows();i++)
							{
								cells = sheet.getRow(i);
								if (cells.length>0)
								{ 
									if(cells[1] !=null && cells[1].getContents().toString().length() >0 )
									{
										soct++;
										sott = getValue(sheet, 0,i);
										mactkm = getValue(sheet, 1,i,false);
										mactkmErp = getValue(sheet, 2,i,false);
										diengiaikm = getValue(sheet, 3,i);
										loaict	=  getValue(sheet, 4,i);
										kenh	=	 getValue(sheet, 5,i);
										
										tungay	=	 getValue(sheet, 6,i);

										denngay	=	 getValue(sheet, 7,i);

										kho =	 getValue(sheet, 8,i);
										loaikh	=	 getValue(sheet, 9,i);
										apdungcho = getValue(sheet, 10, i) == null?"0":getValue(sheet, 10, i);
										loaingansach	=	 getValue(sheet, 11,i);
										maspmua =  getValue(sheet, 12,i);
										soluongmua = getValue(sheet, 13,i);;
										masptra = getValue(sheet, 14,i);
										soluongtra = getValue(sheet, 15,i);

										manppapdung =getValue(sheet, 16,i,false);
										npptuchay =getValue(sheet, 17,i,false);
										chietkhau = getValue(sheet, 18, i);
										String errror="";
										if(mactkm.trim().equals(""))
										{
											errror= "Mã CTKM  trong hàng "+ (indexRow+1) +" không hợp lệ " ;
										}
										System.out.println("apdungcho: "+apdungcho);
										if(!apdungcho.trim().equals("2"))
											if(mactkmErp.trim().length() <= 0 )
											{
												errror=  "Mã CTKM_ERP  trong hàng "+ (indexRow+1)+" không hợp lệ " ;
											}
										String diengiaikmcodh = "";
										if(diengiaikm.trim().length() <= 0 )
										{
											///errror=errror+  "Diễn giải  trong hàng "+ (indexRow+1)+" không hợp lệ " ;
											diengiaikmcodh = diengiaikm;
											String sqldg = "select N'Mua "+soluongmua+" '+b.DIENGIAI+' '+a.TEN as ten from SANPHAM a inner join DONVIDOLUONG b on a.DVDL_FK = b.PK_SEQ where MA = '"+maspmua+"' ";
											ResultSet rs = db.get(sqldg);
											if(rs.next())
											{
												diengiaikm = rs.getString("ten")+" - ";

											}
											rs.close();
											if(chietkhau.trim().length() <= 0 )
											{
												sqldg = "select N'Tặng "+soluongtra+" '+b.DIENGIAI+' '+a.TEN as ten from SANPHAM a inner join DONVIDOLUONG b on a.DVDL_FK = b.PK_SEQ where MA = '"+masptra+"' ";
												rs = db.get(sqldg);
												if(rs.next())
												{
													diengiaikm += rs.getString("ten");
	
												}
											}else
												diengiaikm += " Tặng "+chietkhau+"% chiết khấu";
											rs.close();

										}

										if(loaict.trim().length() <= 0 )
										{
											errror=errror+  "Loại chương trình KM trong hàng "+ (indexRow+1)+" không hợp lệ " ;
										}

										if(kenh.trim().length() <= 0 )
										{
											errror=errror+  "Kênh chương trình KM trong hàng "+ (indexRow+1)+" không hợp lệ " ;
										}


										if(tungay.trim().length() <= 0 || tungay.trim().length() < 10)
										{
											errror=errror+  "Từ ngày chương trình KM trong hàng "+ (indexRow+1)+" không hợp lệ hoặc không phải định dạng Text (yyyy-mm-dd) " ;
										}

										if(denngay.trim().length() <= 0 || denngay.trim().length() < 10)
										{
											errror=errror+  "Đến ngày chương trình KM trong hàng "+ (indexRow+1)+" không hợp lệ hoặc không phải định dạng Text (yyyy-mm-dd) " ;
										}

										if(kho.trim().length() <= 0 )
										{
											errror=errror+  "Kho chương trình KM trong hàng "+ (indexRow+1)+" không hợp lệ " ;
										}

										/*if(loaikh.trim().length() <= 0 )
										{
											errror=errror+  "Loại khách hàng chương trình KM trong hàng "+ (indexRow+1)+" không hợp lệ " ;
										}*/

										if(loaingansach.trim().length() <= 0 )
										{
											errror=errror+  "Loại ngân sách chương trình KM trong hàng "+ (indexRow+1)+" không hợp lệ " ;
										}

										if(maspmua.trim().length() <= 0 )
										{
											errror=errror+  "Mã sản phẩm mua chương trình KM trong hàng "+ (indexRow+1)+" không hợp lệ " ;
										}

										if(soluongmua.trim().length() <= 0 )
										{
											errror=errror+  "Số lượng mua chương trình KM trong hàng "+ (indexRow+1)+" không hợp lệ " ;
										}

										if(maspmua.trim().length() <= 0 )
										{
											errror=errror+  "Mã sản phẩm trả chương trình KM trong hàng "+ (indexRow+1)+" không hợp lệ " ;
										}

										if(soluongmua.trim().length() <= 0 )
										{
											errror=errror+  "Số lượng trả chương trình KM trong hàng "+ (indexRow+1)+" không hợp lệ " ;
										}

										if(manppapdung.trim().length() <= 0 )
										{
											errror=errror+  "Mã nhà phân phối chương trình KM trong hàng "+ (indexRow+1)+" không hợp lệ " ;
										}



										if(!errror.equals(""))
										{
											sheetwrite.addCell(new Label(0, indexRow, errror));
											System.out.println("1.Loi: "+errror);
											flag = false;
										}
										else
										{

											if(flag)
											{
												int soDong=0;
												String nguoitao = userId;
												String ngaytao = getDateTime();
												String	sql="select count(pk_seq) as num from ctkhuyenmai where scheme = '"+mactkm+"' ";
												ResultSet rscheck = db.get(sql);
												rscheck.next();
												soDong = rscheck.getInt("num");
												rscheck.close();
												if(soDong == 0)
												{
													if(!kiemtra_scheme(db,kho,mactkm))
													{		
														errror = "Scheme khuyến mại: "+mactkm+" đã tồn tại !";
														sheetwrite.addCell(new Label(0, indexRow, errror));
														System.out.println("2.Loi");
														flag = false;
													}
													if(!apdungcho.trim().equals("2"))
														if(!kiemtra_schemErp(db,kho,mactkmErp))
														{		
															errror = "SchemeERP: "+mactkmErp+" và Kho: "+kho+" đã tồn tại, hoặc có CTKM đã sử dụng scheme này ! ";
															sheetwrite.addCell(new Label(1, indexRow, errror));
															flag = false;
															System.out.println("3.Loi");
														}
													if(npptuchay.length() > 0 && npptuchay.equals("1"))
													{
														npptuchay = "1";
													}
													else
														npptuchay = "0";
													
													sql = "Insert into Ctkhuyenmai(scheme, diengiai, tungay, denngay, loaict, ngansach, ngaytao, nguoitao, ngaysua, nguoisua,kho_fk,nhomkhnpp_fk, loaingansach, tilevoiprimary, NPPTUCHAY,ApDUNGCHODHLE,PHANBOTHEODONHANG, LEVEL_PHANBO, IO,tungay_dathang,denngay_dathang, ngansachkehoach, apdungcho,SCHEMEERP,isduyet) " +
															"values((select dbo.ftBoDau2(N'" + mactkm + "')), N'" + diengiaikm + "', '" + tungay + "' , '" + denngay + "' , '" + loaict + "','0', " +
															"'" + ngaytao + "', '" +nguoitao + "', '" + ngaytao + "', '" +nguoitao + "','"+ kho +"'," + null + ", '" + loaingansach + "', '', '"+npptuchay+"' ,'','', '', N'','','', '', '"+apdungcho+"',N'" +mactkmErp + "','1')";
													
													
													
													if(flag)
														soDong=db.updateReturnInt(sql);
													//System.out.println("[soDong]"+soDong +"[khachhang.Create]" + sql);
													if(soDong<0)
													{
														System.out.println("Tạo CTKM không thành công :" + sql);
														sheetwrite.addCell(new Label(0, indexRow , "Tạo CTKM không thành công  :" + sql));

														flag = false;
														System.out.println("4.Loi");

													}
													String ctkmCurrent = "";
													String query = "select IDENT_CURRENT('Ctkhuyenmai') as ctkmId";
													System.out.println(query);
													ResultSet rsCtkm = db.get(query);						
													rsCtkm.next();
													ctkmCurrent = rsCtkm.getString("ctkmId");

													rsCtkm.close();
													if(loaikh != null)
														if(loaikh.trim().length() > 0)
														{
															query = "Insert CTKHUYENMAI_LOAIKH(ctkm_fk, LOAIKH_Fk)  " +
																	"select '" + ctkmCurrent + "', pk_seq from LoaiCuaHang where pk_seq >= 0  ";
															if(loaikh.trim().length() > 0)
																query += " and loai in ( select dbo.trim( data) from dbo.Split(N'"+loaikh+"',',') ) ";
															if(flag)
															if(!db.update(query))
															{

																sheetwrite.addCell(new Label(1, indexRow , "Tạo CTKM không thành công  :" + query));
																System.out.println("1.Loi: "+query);
																flag = false;
															}
														}
													
													
													if(kenh != null)
														if(kenh.trim().length() > 0)
														{
															query = "Insert CTKHUYENMAI_KBH(ctkm_fk, KBH_Fk)  " +
																	"select '" + ctkmCurrent + "', pk_seq from Kenhbanhang where pk_seq >= 0  ";
																query += " and pk_seq in ( select dbo.trim( data) from dbo.Split(N'"+kenh+"',';') ) ";
															if(flag)
															if(!db.update(query))
															{

																sheetwrite.addCell(new Label(1, indexRow , "Tạo CTKM không thành công  :" + query));
																System.out.println("1.Loi: "+query);
																flag = false;
															}
														}

													// tạo điều kiện khuyến mãi loại bất trong
													query = "Insert into Dieukienkhuyenmai(diengiai, tongluong, tongtien, loai, ngaytao, nguoitao, ngaysua, nguoisua) values(";
													query = query + "N'ĐK CTKM MUA " +soluongmua  + " sản phẩm mã "+maspmua+" TẶNG "+soluongtra+" sản phẩm mã "+masptra+"',null, " + null + ", '1', '" + ngaytao + "', '" + nguoitao + "', '" + ngaytao + "', '" + nguoitao + "')";
													System.out.println("in _____________"+query);
													if(flag)
													if(!db.update(query))
													{
														System.out.println("2.Loi: "+query);
														sheetwrite.addCell(new Label(2, indexRow , "Tạo Dieukienkhuyenmai không thành công  :" + query));
														flag = false;

													}
													String dkkkmCurrent = "";
													query = "select IDENT_CURRENT('Dieukienkhuyenmai') as dkkmId";

													ResultSet rsDkkm = db.get(query);						
													rsDkkm.next();
													dkkkmCurrent = rsDkkm.getString("dkkmId");
													rsDkkm.close();
													query = "Insert into dieukienkm_sanpham(dieukienkhuyenmai_fk, sanpham_fk, soluong,batbuoc) "
															+ " values('" + dkkkmCurrent + "',( select top(1) pk_seq from sanpham where ma ='" + maspmua + "'), "+soluongmua+",1)";
													if(flag)
													if(!db.update(query))
													{
														System.out.println("3.Loi: "+query);
														System.out.println("Tạo mới dieukienkm_sanpham không thành công :" + query);
														sheetwrite.addCell(new Label(3, indexRow , "Tạo mới dieukienkm_sanpham không thành công  :" + query));

														flag = false;

													}
													// tạo mới ctkm_dkkm mặc định là and,và thứ tự đk là 0
													query = "Insert into ctkm_dkkm(ctkhuyenmai_fk, dkkhuyenmai_fk, pheptoan, thutudieukien)"
															+ " values('" + ctkmCurrent + "', '" + dkkkmCurrent + "', '1', 0)";
													if(flag)
													if(!db.update(query))
													{
														System.out.println("4.Loi: "+query);	
														sheetwrite.addCell(new Label(0, indexRow , "Tạo mới dieukienkm_sanpham không thành công  :" + query));
														flag = false; 
													}
													
													
													String trakmCurrent = "";
													System.out.println("______---ciêt khấu _______"+chietkhau);
													if(chietkhau.trim().length() <= 0 )
													{
														System.out
														.println("vào đây kkkkkkkk 00-0-0-0-0 _________");
														// Tạo mới trả khuyến mãi, loại trả sản phẩm,hình thức bất kỳ trong
														query = "Insert into Trakhuyenmai(diengiai, tongluong, tongtien, chietkhau, loai, hinhthuc, ngaytao, nguoitao, ngaysua, nguoisua) values(";
														query = query + "N'Scheme "+mactkm+".Tặng " + soluongtra + " sản phẩm mã "+masptra+" ',null ,null,null, '3', '1', '" + ngaytao + "', '" + nguoitao + "', '" + ngaytao + "', '" + nguoitao + "')";
														if(flag)
														if(!db.update(query))
														{
															System.out.println("5.Loi: "+query);
															sheetwrite.addCell(new Label(4, indexRow , "Tạo mới Trakhuyenmai không thành công  :" + query));
															flag =  false; 
														}
														
														query = "select IDENT_CURRENT('Trakhuyenmai') as trakmId";
	
														ResultSet rsTrakm = db.get(query);						
														rsTrakm.next();
														trakmCurrent = rsTrakm.getString("trakmId");
														rsTrakm.close();
														query = "Insert into trakhuyenmai_sanpham(trakhuyenmai_fk, sanpham_fk, soluong, dongia)"
																+ "  select '" + trakmCurrent + "',pk_seq, dbo.trim( data), null "
																+ " from sanpham,dbo.Split(N'"+soluongtra+"',',')  where ma in ( select dbo.trim( data) from dbo.Split(N'"+masptra+"',','))";
														if(flag)
														if (!db.update(query))
														{
															System.out.println("6.Loi: "+query);
															sheetwrite.addCell(new Label(5, indexRow, "Tạo mới trả khuyến mãi sản phẩm ko thành công:" + query));
	
															flag = false;
	
														}
													}else{
														// Tạo mới trả khuyến mãi, loại trả chiết khấu
														System.out.println("vào đây _________");
														query = "Insert into Trakhuyenmai(diengiai, tongluong, tongtien, chietkhau, loai, hinhthuc, ngaytao, nguoitao, ngaysua, nguoisua) values(";
														query = query + "N'Scheme "+mactkm+".Chả chiết khấu "+chietkhau+"% ',null ,null,"+chietkhau+", '2', '1', '" + ngaytao + "', '" + nguoitao + "', '" + ngaytao + "', '" + nguoitao + "')";
														if(flag)
														if(!db.update(query))
														{
															System.out.println("5.55.Loi: "+query);
															sheetwrite.addCell(new Label(4, indexRow , "Tạo mới Trakhuyenmai không thành công  :" + query));
															flag =  false; 
														}
														query = "select IDENT_CURRENT('Trakhuyenmai') as trakmId";
														
														ResultSet rsTrakm = db.get(query);						
														rsTrakm.next();
														trakmCurrent = rsTrakm.getString("trakmId");
														rsTrakm.close();
													}
													
													query = "Insert into ctkm_trakm(ctkhuyenmai_fk, trakhuyenmai_fk, pheptoan, thutu)"
															+ "  values('" + ctkmCurrent + "', '" + trakmCurrent + "', '1', '0')";
													if(flag)
													if(!db.update(query))
													{
														System.out.println("7.Loi: "+query);
														sheetwrite.addCell(new Label(6, indexRow, "Tạo mới ctkm_trakm ko thành công:" + query));
														flag = false; 
													}
													if(manppapdung.equals("ALL"))	
														query ="insert into ctkm_npp(CTKM_FK,NPP_FK,CHON,NGUOITAO,NGUOISUA,NGAYTAO,NGAYSUA)  "
															+ "select '" + ctkmCurrent +"',pk_seq,'1',"+nguoitao+",'"+nguoitao+"','"+ngaytao+"','"+ngaytao+"'"
															+ "from  nhaphanphoi where  trangthai = 1 ";
												
													else
														query ="insert into ctkm_npp(CTKM_FK,NPP_FK,CHON,NGUOITAO,NGUOISUA,NGAYTAO,NGAYSUA)  "
																+ "select '" + ctkmCurrent +"',pk_seq,'1',"+nguoitao+",'"+nguoitao+"','"+ngaytao+"','"+ngaytao+"'"
																+ "from  nhaphanphoi where manpp in ( select dbo.trim( data) from dbo.Split(N'"+manppapdung+"',';'))";
													System.out
															.println("them nppad: "+query);
													if(flag)
													if(!db.update(query))
													{
														System.out.println("8.Loi: "+query);
														sheetwrite.addCell(new Label(7, indexRow, "Tạo mới chương trình khuyến mãi NPP ko thành công:" + query));

														flag = false;
													}
												}

												else
												{
													 nguoitao = userId;
													 ngaytao = getDateTime();
													if(!apdungcho.trim().equals("2"))
														if(!kiemtra_schemErp(db,kho,mactkmErp))
														{		
															errror = "SchemeERP: "+mactkmErp+" và Kho: "+kho+" đã tồn tại, hoặc có CTKM đã sử dụng scheme này ! ";
															sheetwrite.addCell(new Label(0, indexRow, errror));
															flag = false;
															System.out.println("3.Loi");
														}
													boolean flagup = false;
													String CtkmID = "";
													String query = "select pk_seq as ID from ctkhuyenmai where scheme = N'"+mactkm+"'";

													ResultSet rsctkm = db.get(query);						
													rsctkm.next();
													CtkmID = rsctkm.getString("ID");
													rsctkm.close();
														sql="select count(*) as num from donhang_ctkm_trakm where  ctkmid='" + CtkmID+ "' and DONHANGID in (select PK_SEQ from DONHANG where TRANGTHAI!=2) ";
													System.out.println(sql);
													ResultSet rs = db.get(sql);
													try 
													{
														rs.next();
														if(rs.getInt("num") > 0)
														{
															flagup = true;
															rs.close();
														}
														rs.close();
													}
													catch (Exception e) {e.printStackTrace();}
													
													sql="select count(*) as num from ERP_DONDATHANG_CTKM_TRAKM where  ctkmid='" + CtkmID+ "' and DONDATHANGID in (select PK_SEQ from ERP_DONDATHANG where TRANGTHAI!=3) ";
													System.out.println(sql);
											    	 rs = db.get(sql);
													try 
													{
														rs.next();
														if(rs.getInt("num") > 0)
														{
															flag = true;
															rs.close();
														}
														rs.close();
													}
													catch (Exception e) {e.printStackTrace();}
													
													boolean ktpb = true;
													sql="select COUNT(*) as ispb  "+
															"		from PHANBOKHUYENMAI pb where pb.CTKM_FK= '"+CtkmID+"' "+
															"						and NGANSACH!=0 ";
													System.out.println(sql);
													rs = db.get(sql);
													try 
													{
														rs.next();
														if(rs.getInt("ispb") > 0)
														{
															ktpb = false;
															rs.close();
														}
														rs.close();
													}
													catch (Exception e) {e.printStackTrace();}
													//khuyen mai tich luy

													boolean flagerp = false;
													/*try 
													{
														OracleConnUtils or = new OracleConnUtils();
														sql="select count(a.SHEME) as num1, count(b.SHEME) as num2 from apps.tbl_hoadon a,apps.tbl_phieuxuatkho b where ( a.SHEME= '"+mactkmErp+"' or b.SHEME= '"+mactkmErp+"' )";
														rs = or.get(sql);
														rs.next();
														if(rs.getInt("num1") > 0 && rs.getInt("num2") > 0)
														{
															flagerp = true;
															rs.close();
														}
														rs.close();
														or.shutDown();
													}
													catch (Exception e) 
													{
														e.printStackTrace();

													}*/
													// 1. CTKM khi chưa đồng bộ hóa đơn/ PXK nào có scheme ERP thì dc sửa mã này
												/*	if(!flagerp)
													{
														query = "Update Ctkhuyenmai set SCHEMEERP = N'" + mactkmErp + "' " +
																"where pk_seq = '" + CtkmID + "'";
														System.out.println("Truong Hop SCHEMEERP :"+query);
														if(flag)
														if(!db.update(query))
														{


															flag =  false; 
															sheetwrite.addCell(new Label(0, indexRow, "Cập nhật SCHEMEERP không thành công:" + query));
															System.out.println("1.Loi Update Ctkhuyenmai: "+query);
														}
													}*/
													if(!flagup )
													{

														if(npptuchay.length() > 0 && npptuchay.equals("1"))
														{
															npptuchay = "1";
														}
														else
															npptuchay = "0";
														query = "Update Ctkhuyenmai set  diengiai = N'" +diengiaikm + "', tungay = '" + tungay + "', " +
																"denngay = '" + denngay + "', loaict = '" + loaict + "', ngaysua = '" + ngaytao + "', nguoisua = '" + nguoitao + "'," +
																"kho_fk ='"+ kho +"', apdungcho = '" + apdungcho + "',NPPTUCHAY =  '"+npptuchay+"' " +
																"where pk_seq = '" + CtkmID + "'";
														System.out.println("Truong Hop 1 :"+query);
														if(flag)
														if(!db.update(query))
														{
															flag =  false; 
															sheetwrite.addCell(new Label(1, indexRow, "Cập nhật Ctkhuyenmai không thành công:" + query));
															System.out.println("2.Loi Update Ctkhuyenmai: "+query);
														}
														if(ktpb == false)
														{
															query = "select count(*) as so from Ctkhuyenmai where loaingansach <> '"+loaingansach+"' and pk_seq = '" + CtkmID + "' ";
															ResultSet rsckpb = db.get(query);
															if(rsckpb != null)
															{
																rsckpb.next();
																if(rsckpb.getInt("so") > 0)
																{
																	query = "delete from phanbokhuyenmai where ctkm_FK = '" +CtkmID + "'";
																	if(!db.update(query))
																	{
																		flag =  false; 
																		sheetwrite.addCell(new Label(1, indexRow, "Xóa phân bổ không thành công:" + query));
																		System.out.println("2.Loi Update Ctkhuyenmai: "+query);
																	}
																	
																	query = "Update Ctkhuyenmai set LOAINGANSACH ='"+loaingansach+"' " +
																			"where pk_seq = '" + CtkmID + "'";
																	System.out.println("Truong Hop 1 :"+query);
																	if(flag)
																	if(!db.update(query))
																	{
																		flag =  false; 
																		sheetwrite.addCell(new Label(1, indexRow, "Cập nhật Loại ngân sách không thành công:" + query));
																		System.out.println("2.Loi Update Ctkhuyenmai: "+query);
																	}
																	
																}
																rsckpb.close();
															}
															
														}
														else
														{
															query = "Update Ctkhuyenmai set LOAINGANSACH ='"+loaingansach+"' " +
																	"where pk_seq = '" + CtkmID + "'";
															System.out.println("Truong Hop 1 :"+query);
															if(flag)
															if(!db.update(query))
															{
																flag =  false; 
																sheetwrite.addCell(new Label(1, indexRow, "Cập nhật Loại ngân sách không thành công:" + query));
																System.out.println("2.Loi Update Ctkhuyenmai: "+query);
															}
														}
															
														String dkkkmCurrent = "";
														query = "select DKKHUYENMAI_FK as dkkmId from ctkm_dkkm where CTKHUYENMAI_FK = '"+CtkmID+"'";

														ResultSet rsDkkm = db.get(query);						
														rsDkkm.next();
														dkkkmCurrent = rsDkkm.getString("dkkmId");
														rsDkkm.close();
														if(checkExitsDKKM(db, dkkkmCurrent, CtkmID))
														{
															flag =  false; 
															sheetwrite.addCell(new Label(3, indexRow, "Điều kiện khuyến mãi đã được sử dụng ở ctkm khác,không được phép sửa !:" + query));
															System.out.println("4.Loi Update trakhuyenmai_sanpham: "+query);
														}
														else
														{
															query = "delete from dieukienkm_sanpham where DIEUKIENKHUYENMAI_FK = '" +dkkkmCurrent + "'";
															db.update(query);
															query = "Insert into dieukienkm_sanpham(dieukienkhuyenmai_fk, sanpham_fk, soluong,batbuoc) "
																	+ " values('" + dkkkmCurrent + "',( select top(1) pk_seq from sanpham where ma ='" + maspmua + "'), "+soluongmua+",1)";
															if(flag)
																if(!db.update(query))
																{
																	flag =  false; 
																	sheetwrite.addCell(new Label(2, indexRow, "Cập nhật dieukienkm_sanpham không thành công:" + query));
																	System.out.println("3.Loi Update dieukienkm_sanpham: "+query);
																}
															query = "update dieukienkhuyenmai set diengiai = N'ĐK CTKM MUA " +soluongmua  + " sản phẩm mã "+maspmua+" TẶNG "+soluongtra+" sản phẩm mã "+masptra+"',tongluong = null, tongtien = null, loai = 1,ngaysua = '" + ngaytao + "',nguoisua= '" + nguoitao + "'  where pk_seq = "+dkkkmCurrent;
															if(flag) 
																if(!db.update(query))
																{
																	flag =  false; 
																	sheetwrite.addCell(new Label(2, indexRow, "Cập nhật dieukienkhuyenmai không thành công:" + query));
																	System.out.println("3.Loi Update dieukienkm_sanpham: "+query);
																}
														}
														String trakmid = "";
														query = "select TRAKHUYENMAI_FK from CTKM_TRAKM where CTKHUYENMAI_FK = '"+CtkmID+"'";
														ResultSet rstrakm = db.get(query);						
														rstrakm.next();
														trakmid = rstrakm.getString("TRAKHUYENMAI_FK");
														rstrakm.close();
														if(checkExits(db,trakmid))
														{
															flag =  false; 
															sheetwrite.addCell(new Label(3, indexRow, "Trả khuyến mãi đã được sử dụng,không được phép sửa !:" + query));
															System.out.println("4.Loi Update trakhuyenmai_sanpham: "+query);
														}else
														{
															

															query = "delete from trakhuyenmai_sanpham where TRAKHUYENMAI_FK = '" + trakmid+ "'";
															db.update(query);


															query = "Insert into trakhuyenmai_sanpham(trakhuyenmai_fk, sanpham_fk, soluong, dongia)"
																	+ "  select '" + trakmid + "',pk_seq, dbo.trim( data), null "
																	+ " from sanpham,dbo.Split(N'"+soluongtra+"',',')  where ma in ( select dbo.trim( data) from dbo.Split(N'"+masptra+"',','))";
															if(flag)
																if(!db.update(query))
																{
																	flag =  false; 
																	sheetwrite.addCell(new Label(3, indexRow, "Cập nhật trakhuyenmai_sanpham không thành công:" + query));
																	System.out.println("4.Loi Update dieukienkm_sanpham: "+query);
																}
															if(chietkhau.trim().length() <= 0 )
															{
																query = "update TRAKHUYENMAI set diengiai = N'Scheme "+mactkm+".Tặng " + soluongtra + " sản phẩm mã "+masptra+" ', tongluong = null, tongtien = null, chietkhau = null, loai = 3, hinhthuc = 1,ngaysua = '" + ngaytao + "',nguoisua= '" + nguoitao + "' where pk_seq = "+trakmid;
																if(flag)
																	if(!db.update(query))
																	{
																		flag =  false; 
																		sheetwrite.addCell(new Label(2, indexRow, "Cập nhật TRAKHUYENMAI không thành công:" + query));
																		System.out.println("3.Loi Update dieukienkm_sanpham: "+query);
																	}
															}else{
																query = "update TRAKHUYENMAI set diengiai = N'Scheme "+mactkm+".Tặng " + chietkhau + " % chiết khấu ', tongluong = null, tongtien = null, chietkhau = "+chietkhau+", loai = 2, hinhthuc = 1,ngaysua = '" + ngaytao + "',nguoisua= '" + nguoitao + "' where pk_seq = "+trakmid;
																if(flag)
																	if(!db.update(query))
																	{
																		flag =  false; 
																		sheetwrite.addCell(new Label(2, indexRow, "Cập nhật TRAKHUYENMAI không thành công:" + query));
																		System.out.println("3.Loi Update dieukienkm_sanpham: "+query);
																	}
															}
														}
														query = "delete CTKHUYENMAI_LOAIKH where ctkm_fk = '" + CtkmID + "'";
														db.update(query);
														if(loaikh != null)
															if(loaikh.trim().length() > 0)
															{
																query = "Insert CTKHUYENMAI_LOAIKH(ctkm_fk, LOAIKH_Fk)  " +
																		"select '" + CtkmID + "', pk_seq from LoaiCuaHang where pk_seq >= 0  ";
																if(loaikh.trim().length() > 0)
																	query += " and loai in ( select dbo.trim( data) from dbo.Split(N'"+loaikh+"',',') ) ";

																if(!db.update(query))
																{

																	sheetwrite.addCell(new Label(4, indexRow , "Tạo CTKM không thành công  :" + query));
																	System.out.println("5.Loi CTKHUYENMAI_LOAIKH: "+query);
																	flag = false;
																}
															}
														query = "delete CTKHUYENMAI_KBH where ctkm_fk = '" + CtkmID + "'";
														db.update(query);
														if(kenh != null)
															if(kenh.trim().length() > 0)
															{
																query = "Insert CTKHUYENMAI_KBH(ctkm_fk, KBH_Fk)  " +
																		"select '" + CtkmID + "', pk_seq from Kenhbanhang where pk_seq >= 0  ";
																	query += " and pk_seq in ( select dbo.trim( data) from dbo.Split(N'"+kenh+"',';') ) ";
																if(flag)
																if(!db.update(query))
																{

																	sheetwrite.addCell(new Label(1, indexRow , "Tạo CTKM không thành công  :" + query));
																	System.out.println("1.Loi: "+query);
																	flag = false;
																}
															}

														query = "delete ctkm_npp where ctkm_fk = '" + CtkmID+ "'";
														if(flag)
														db.update(query);
														
														if(manppapdung.equals("ALL"))	
															query ="insert into ctkm_npp(CTKM_FK,NPP_FK,CHON,NGUOITAO,NGUOISUA,NGAYTAO,NGAYSUA)  "
																+ "select '" + CtkmID +"',pk_seq,'1',"+nguoitao+",'"+nguoitao+"','"+ngaytao+"','"+ngaytao+"'"
																+ "from  nhaphanphoi where  trangthai = 1 ";
														else
														query ="insert into ctkm_npp(CTKM_FK,NPP_FK,CHON,NGUOITAO,NGUOISUA,NGAYTAO,NGAYSUA)  "
																+ "select '" + CtkmID +"',pk_seq,'1',"+nguoitao+",'"+nguoitao+"','"+ngaytao+"','"+ngaytao+"'"
																+ "from  nhaphanphoi where manpp in ( select dbo.trim( data) from dbo.Split(N'"+manppapdung+"',';'))";
														if(flag)
														if(!db.update(query))
														{

															sheetwrite.addCell(new Label(9, indexRow , "Update ctkm_npp không thành công  :" + query));
															System.out.println("10.Loi Update Ctkm_npp: "+query);
															flag = false;

														}
													}
													else
													{
														
															//kiem tra denngay cua ctkhuyenmai co lon hon ngay lon nhat cua don hang khong?
															query="select max(ngaynhap) as  maxngaynhap from donhang  a inner join donhang_ctkm_trakm b on "+
																	" a.pk_seq=b.donhangid where    ctkmid="+CtkmID +
																	" having max(ngaynhap) <='"+denngay+"'"; 
															System.out.println("Check Ngay 1 :"+query);

															rs = db.get(query);
															try 
															{

																if(rs.next())
																{

														
																		String maxngaynhap = rs.getString("maxngaynhap");
																		query = "Update Ctkhuyenmai  set denngay = '" + denngay + "' , ngaysua = '" + ngaytao + "', nguoisua = '" +nguoitao + "' where pk_seq = '" + CtkmID + "' and '"+maxngaynhap+"' <= '"+denngay+"' ";
																		System.out.println("Update Truong Hop 2"+query);
																		if(!db.update(query))
																		{
																			sheetwrite.addCell(new Label(5, indexRow , "Update Ctkhuyenmai không thành công  :" + query));
																			System.out.println("6.Loi Update Ctkhuyenmai: "+query);
																			flag = false;
																		}
																		if(flag)
																		if(diengiaikmcodh.length() > 0)
																		{
																			query="Update Ctkhuyenmai set diengiai = N'" + diengiaikmcodh + "' where pk_seq = '" +CtkmID + "'";
																			if(!db.update(query))
																			{
																				sheetwrite.addCell(new Label(6, indexRow , "Update Ctkhuyenmai không thành công  :" + query));
																				System.out.println("7.Loi Update Ctkhuyenmai: "+query);
																				flag = false;
																			}
																		}
																	rs.close();
																}else{
																		//this.msg = "II. Khong the cap nhat CT Khuyen Mai, Da Co Nha Phan Phoi Ap Chuong Trinh Khuyen Mai Toi Ngay Nay";
																		//return false;
																		if(diengiaikmcodh.length() > 0)
																		{
																			query="Update Ctkhuyenmai set diengiai = N'" + diengiaikmcodh + "' where pk_seq = '" +CtkmID + "'";
																			if(!db.update(query))
																			{
																				sheetwrite.addCell(new Label(6, indexRow , "Update Ctkhuyenmai không thành công  :" + query));
																				System.out.println("7.Loi Update Ctkhuyenmai: "+query);
																				flag = false;
																			}
																		}
																}
																
															}
																catch (Exception e) {
																	sheetwrite.addCell(new Label(7, indexRow , "Update Ctkhuyenmai không thành công  :" + query));
																	System.out.println("8.Loi Update Ctkhuyenmai: "+query);
																	flag = false;
																}
														}
														if(manppapdung.equals("ALL"))	
															query ="insert into ctkm_npp(CTKM_FK,NPP_FK,CHON,NGUOITAO,NGUOISUA,NGAYTAO,NGAYSUA)  "
																+ "select '" + CtkmID +"',pk_seq,'1',"+nguoitao+",'"+nguoitao+"','"+ngaytao+"','"+ngaytao+"'"
																+ "from  nhaphanphoi where  trangthai = 1 ";
														else
														query ="insert into ctkm_npp(CTKM_FK,NPP_FK,CHON,NGUOITAO,NGUOISUA,NGAYTAO,NGAYSUA)  "
																+ "select '" + CtkmID +"',pk_seq,'1',"+nguoitao+",'"+nguoitao+"','"+ngaytao+"','"+ngaytao+"'"
																+ "from  nhaphanphoi where manpp in ( select dbo.trim( data) from dbo.Split(N'"+manppapdung+"',';'))"
																+ " and not exists (select 1 from ctkm_npp where npp_fk = pk_seq and CTKM_FK = '" + CtkmID +"')";
														System.out
																.println("Insert npp: "+query);
														if(flag)
														if(!db.update(query))
														{

															sheetwrite.addCell(new Label(9, indexRow , "Update ctkm_npp không thành công  :" + query));
															System.out.println("10.Loi Update Ctkm_npp: "+query);
															flag = false;

														}
													



												}




											}
										}

									}
								}
								indexRow++;

							}
						}
						System.out.println("indexRow: "+indexRow+" Soct: "+soct);
						if(flag && soct > 0)
						{

							obj.setMessage("Upload thành công "+soct+" khuyến mãi !");
							db.getConnection().commit();
							db.getConnection().setAutoCommit(true);
							obj.init("");
							session.setAttribute("obj", obj);

							db.shutDown();
							String nextJSP = "/AHF/pages/Center/ChuongTrinhKhuyenMai.jsp";
							response.sendRedirect(nextJSP);

						}
						else
						{
							db.getConnection().rollback();
							obj.setMessage("Upload không thành công ! Vui lòng xem file !");

							obj.init("");
							session.setAttribute("obj", obj);
							w.write();
							db.shutDown();

							w.close();

						}

					}
				}catch(Exception er)
				{
					er.printStackTrace();
					try {
						db.getConnection().rollback();
						w.write();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					obj.setMessage("Upload không thành công ! Vui lòng xem file !");

					obj.init("");
					session.setAttribute("obj", obj);
					
					db.shutDown();

					try {
						w.close();
					} catch (WriteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					String nextJSP = "/AHF/pages/Center/ChuongTrinhKhuyenMai.jsp";
					response.sendRedirect(nextJSP);
				}
			}
		}
		else
		{
			out.println(action); 
			obj.setRequestObj(request);
			if (action.equals("Tao moi"))
			{
				ICtkhuyenmai ctkmBean = (ICtkhuyenmai) new Ctkhuyenmai("");
				ctkmBean.setUserId(userId);
				ctkmBean.createRS();
				ctkmBean.CreateVung();
				session.setAttribute("ctkmBean", ctkmBean);
				session.setAttribute("dkkmDien_giai", "");
				session.setAttribute("dkkmTungay", "");
				session.setAttribute("dkkmDenngay", "");
				String nextJSP = "/AHF/pages/Center/ChuongTrinhKhuyenMaiNew.jsp";
				response.sendRedirect(nextJSP);
			}
			else if(action.equals("view") || action.equals("next") || action.equals("prev")){
				obj.setUserId(userId);
				//String search = getSearchQuery(request, obj);
				obj.setNxtApprSplitting(Integer.parseInt(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nxtApprSplitting"))));
				obj.init("");
				obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
				session.setAttribute("obj", obj);
				response.sendRedirect("/AHF/pages/Center/ChuongTrinhKhuyenMai.jsp");
			}else  if (action.equals("excel"))
		    {
				
		    	
		    	try
			    {
		    		response.setContentType("application/vnd.ms-excel");
					response.setHeader("Content-Disposition", "attachment; filename=THONGTINCTKM.xls");
				
					com.aspose.cells.Workbook workbook = new com.aspose.cells.Workbook();
					TaoBaoCao(request,workbook, userTen,obj);
					OutputStream out = response.getOutputStream();
					workbook.save(out);
				 
			    }
			    catch (Exception ex)
			    {
			       ex.printStackTrace();
			    }
		    	
				obj.setUserId(userId);
			  		
		    }
			else{

				//a

				//obj = new CtkhuyenmaiList();
				obj.setRequestObj(request);    	

				//------------------------
				obj.setUserId(userId);
				String search = getSearchQuery(request, obj);
				System.out.println("ct khuyen mai tt: " + search);

				//obj = new DaidienkinhdoanhList(search);

				obj.init(search);


				session.setAttribute("obj", obj);  	
				session.setAttribute("userId", userId);

				response.sendRedirect("/AHF/pages/Center/ChuongTrinhKhuyenMai.jsp");    	
			}
		}
	}
	private void TaoBaoCao(HttpServletRequest request,com.aspose.cells.Workbook workbook,String nguoitao ,ICtkhuyenmaiList obj)throws Exception
	{
		try{
			
			workbook.setFileFormatType(FileFormatType.EXCEL2003);
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			worksheet.setName("ThongTinCTKM");
			
			com.aspose.cells.Cells cells = worksheet.getCells();
			cells.setRowHeight(0, 50.0f);
			com.aspose.cells.Cell cell = cells.getCell("A1");
			ReportAPI.getCellStyle(cell, Color.RED, true, 16,"Chương Trình Khuyến Mãi");
			Utility util = new Utility();
			

				String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
				obj.setUserId(userId);
			System.out.println("_+_+____________" + obj.getUserId());
			cell = cells.getCell("A3");
			ReportAPI.getCellStyle(cell, Color.BLUE, true, 10, "Ngày tạo : "+ getDateTime());
			
			cell = cells.getCell("A2");
			cells.setColumnWidth(0, 20.0f);

			cells.setColumnWidth(4, 20.0f);
			cells.setColumnWidth(9, 15.0f);
			cells.setColumnWidth(8, 15.0f);
			cells.setColumnWidth(7, 15.0f);
			cells.setColumnWidth(6, 15.0f);
			cell = cells.getCell("A4");
			ReportAPI.getCellStyle(cell, Color.BLUE, true, 10,"Người tạo : " + nguoitao);
			ResultSet rs = null;
			dbutils db = new dbutils();
			String query =    " select '' as STT,a.NGAYTAO as [Ngày tạo] ,isnull(a.SCHEMEERP,'') SCHEMEERP,a.SCHEME,a.DIENGIAI as [Diễn giải],a.TUNGAY [Từ ngày],a.DENNGAY [Đến ngày],"
					 + "\n a.KBH_FK [Kênh],kho.DIENGIAI [Kho],sp.MA as [Mã SP DKKM],"
					 + "\n sp.ten as [Tên Sản Phẩm DKKM],c.SOLUONG as [Số Lượng DKKM],dv.DIENGIAI as [Đơn vị DKKM],"
					 + "\n sp1.MA as [Mã SP Trả],sp1.ten as [Tên SP Trả],f.SOLUONG as [Số Lượng Trả] ,dv1.DIENGIAI as [Đơn vị trả KM] , case when a.apdungcho = 0 then N'Sell in & Sell out' when a.apdungcho = 1 then N'Sell in' else N'Sell out' end as [Áp dụng cho], "
					 + "\n [dbo].GetCTKHUYENMAI_LOAIKH(a.PK_SEQ) as [Loại KH],[dbo].[GetCTKM_NPP](a.PK_SEQ,"+obj.getUserId()+") as NhaPhanPhoi"
					 + "\n from CTKHUYENMAI a left join CTKM_DKKM b"
					 + "\n on a.PK_SEQ=b.CTKHUYENMAI_FK left join DIEUKIENKM_SANPHAM c "
					 + "\n on c.DIEUKIENKHUYENMAI_FK=b.DKKHUYENMAI_FK "
					 + "\n left join CTKM_TRAKM d on d.CTKHUYENMAI_FK=a.PK_SEQ"
					 + "\n left join TRAKHUYENMAI_SANPHAM f on f.TRAKHUYENMAI_FK=d.TRAKHUYENMAI_FK"
					 + "\n left join sanpham sp on sp.PK_SEQ=c.SANPHAM_FK"
					 + "\n left join sanpham sp1 on sp1.PK_SEQ=f.SANPHAM_FK"
					 + "\n left join DONVIDOLUONG dv on dv.PK_SEQ=sp.DVDL_FK"
					 + "\n left join DONVIDOLUONG dv1 on dv1.PK_SEQ=sp1.DVDL_FK"
					 + "\n left join KHO kho on kho.PK_SEQ = a.KHO_FK ";
					 if(obj.getUserId().equals("100368"))
					 {
						 query +=  " where 1=1 ";
					 }
					 else{	 
						query += " where exists (select 1 from ctkm_npp z " +
					 		" inner join nhaphanphoi fx on fx.pk_seq = z.npp_fk " +
					 		" inner join phamvihoatdong sx on sx.npp_fk = fx.pk_seq " +
					 							 		
					 		" where sx.nhanvien_fk = '"+obj.getUserId()+"' and z.ctkm_fk = a.pk_seq ) ";}
			if(obj.getvung().length() > 0)
				query += " and  exists (select 1 from ctkm_npp z inner join nhaphanphoi x on z.npp_fk = x.pk_seq inner join khuvuc kv on x.khuvuc_fk = kv.pk_seq where vung_fk = '"+obj.getvung()+"' and z.ctkm_fk = a.pk_seq )" ;
			
			if(obj.getkhuvuc().length() > 0)
				query += " and  exists (select 1 from ctkm_npp z inner join nhaphanphoi x on z.npp_fk = x.pk_seq  where x.khuvuc_fk = '"+obj.getkhuvuc()+"' and z.ctkm_fk = a.pk_seq )" ;
			
			if(obj.getnpp().length() > 0)
				query += " and  exists (select 1 from ctkm_npp z where z.npp_fk = '"+obj.getnpp()+"' and z.ctkm_fk = a.pk_seq )" ;
			
			if(obj.getTungay().length() > 0)
			{
				query = query + " and a.tungay >= '" +obj.getTungay()+ " '";			
			}
			
			if(obj.getDenngay().length() > 0)
			{
				query = query + " and a.denngay <= '" +obj.getDenngay()+ " '";			
			}
			
			if(obj.getTrangthai().equals("1")){
				query = query + " and tungay <= (SELECT CONVERT(VARCHAR(10),DATEADD(day,0,dbo.GetLocalDate(DEFAULT)),120))" +
						" and denngay >=( SELECT CONVERT(VARCHAR(10),DATEADD(day,0,dbo.GetLocalDate(DEFAULT)),120))";							 

			}

			if(obj.getTrangthai().equals("2")){
				query = query + " and (tungay > (SELECT CONVERT(VARCHAR(10),DATEADD(day,0,dbo.GetLocalDate(DEFAULT)),120))" +
						" or denngay < ( SELECT CONVERT(VARCHAR(10),DATEADD(day,0,dbo.GetLocalDate(DEFAULT)),120)))";

			}

			System.out.println("Get excel CTKM: "+query);
			 rs = db.get(query);
			
			ResultSetMetaData rsmd = rs.getMetaData();
			int socottrongSql = rsmd.getColumnCount();
			
			int countRow = 10;

			for( int i =1 ; i <=socottrongSql ; i ++  )
			{
				cell = cells.getCell(countRow,i-1 );cell.setValue(rsmd.getColumnName(i));
				cells.setColumnWidth(i, 20.0f);
				ReportAPI.setCellBackground(cell, new Color(70,130,180), BorderLineType.THIN, true, 0);	
			 
			}
			countRow ++;
			
			int stt = 0;
			while(rs.next())
			{
				boolean kt = false;
				boolean ck = true;
				stt++;
				String value = "";
				for(int i =1;i <=socottrongSql ; i ++)
				{
					cell = cells.getCell(countRow,i-1 );
					if(rsmd.getColumnName(i).equals("STT"))
					{					
						cell.setValue(stt);
						ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
						//System.out.println("STT: "+stt);

					}else
					if(rsmd.getColumnType(i) == Types.DOUBLE || rsmd.getColumnType(i) == Types.INTEGER || rsmd.getColumnType(i) == Types.DECIMAL)
					{
						cell.setValue(rs.getDouble(i));
						
						ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
					}
					else
					{
						cell.setValue(rs.getString(i));
						ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					}
				}
				++countRow;
			}

			if(rs!=null)rs.close();
			if(db!=null){
				db.shutDown();
			}

	
		}catch(Exception ex){
			
			System.out.println("Errrorr : "+ex.toString());
			ex.printStackTrace();
			throw new Exception("Lỗi không tạo được báo cáo !");
		}
	}
	private String getSearchQuery(HttpServletRequest request, ICtkhuyenmaiList obj)
	{	
		Utility util = new Utility();
		dbutils db = new dbutils();

		String diengiai = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diengiai"));
		if ( diengiai == null)
			diengiai = "";
		obj.setDiengiai(diengiai);

		String tungay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay"));
		if (tungay == null)
			tungay = "";    	
		obj.setTungay(tungay);

		String denngay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay"));
		if (denngay == null)
			denngay = "";    	
		obj.setDenngay(denngay);

		String npp = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("npp"));
		if (npp == null)
			npp = "";    	
		obj.setnpp(npp);
		
		String vung = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vung"));
		if (vung == null)
			vung = "";    	
		obj.setvung(vung);
		
		String khuvuc = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvuc"));
		if (khuvuc == null)
			khuvuc = "";    	
		obj.setkhuvuc(khuvuc);

		String trangthai = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai"));
		if (trangthai == null)
			trangthai = "1";    	
		obj.setTrangthai(trangthai);

		String query = "select distinct a.PK_SEQ as ctkmId, a.scheme, isnull(a.DIENGIAI, '') as diengiai, a.TUNGAY, a.DENNGAY"
				+"\n , isnull(a.LOAICT, '1') as type, isnull(a.NGANSACH, '') as NGANSACH, a.DASUDUNG"
				+"\n , isnull(a.NGAYTAO, '') as NGAYTAO, isnull(a.NGAYSUA, '') as NGAYSUA, b.TEN as nguoitao, c.TEN as nguoisua " +
				"\n , [dbo].[getvungctkm](a.PK_SEQ) as Vten "+
				
				"\n  , (  "+
				"\n 		select COUNT(*)  "+
				"\n 		from PHANBOKHUYENMAI pb where pb.CTKM_FK=a.PK_SEQ "+
				"\n 						and NGANSACH!=0 ) as isPB, "+
				"\n    isnull(a.isduyet,0) isDuyet "+
				"\n 	, (select COUNT(*) From DONHANG_CTKM_TRAKM  km where km.CTKMID=a.PK_SEQ  )  "+
				"\n 	+ "+
				"\n 	(select COUNT(*) from ERP_DONDATHANG_CTKM_TRAKM  where CTKMID=a.PK_SEQ )as soDH, "+
				"\n  DATEDIFF(day, a.tungay, dbo.GetLocalDate(DEFAULT)) kcngay,isnull(ISDONGBO,'0') as ISDONGBO "+
				"\n  from CTKHUYENMAI a inner join NHANVIEN b on a.NGUOITAO = b.PK_SEQ inner join NHANVIEN c on a.NGUOISUA = c.PK_SEQ "+
				"\n  where 1=1  and a.PK_SEQ in (select ctkm_fk from CTKM_NPP ctn inner join phamvihoatdong pv on pv.npp_fk = ctn.npp_fk 	       "+ 
					"\n inner join nhaphanphoi npp on npp.PK_SEQ = ctn.NPP_FK	  "+
					"\n and pv.Npp_fk = npp.PK_SEQ and pv.Nhanvien_fk = '"+obj.getUserId()+"')"; 
		
		
		
		if (diengiai.length()>0){
			query = query + " and upper(a.diengiai) like upper('%" + diengiai + "%') or upper(a.SCHEME) like upper('%" + diengiai + "%')";			
		}
		if (tungay.length()>0){
			query = query + " and a.tungay >= '" + convertDate(tungay) + " '";			
		}

		if (denngay.length()>0){
			query = query + " and a.denngay <= '" + convertDate(denngay) + " '";		
		}
 
		if(vung.length() > 0)
			query += " and  exists (select 1 from ctkm_npp z inner join nhaphanphoi x on z.npp_fk = x.pk_seq inner join khuvuc kv on x.khuvuc_fk = kv.pk_seq where vung_fk = '"+vung+"' and z.ctkm_fk = a.pk_seq )" ;
		
		if(khuvuc.length() > 0)
			query += " and  exists (select 1 from ctkm_npp z inner join nhaphanphoi x on z.npp_fk = x.pk_seq  where x.khuvuc_fk = '"+khuvuc+"' and z.ctkm_fk = a.pk_seq )" ;
		
		if(npp.length() > 0)
			query += " and  exists (select 1 from ctkm_npp z where z.npp_fk = '"+npp+"' and z.ctkm_fk = a.pk_seq )" ;
		
		if(trangthai.equals("1")){
			query = query + " and tungay <= (SELECT CONVERT(VARCHAR(10),DATEADD(day,0,dbo.GetLocalDate(DEFAULT)),120))" +
					" and denngay >=( SELECT CONVERT(VARCHAR(10),DATEADD(day,0,dbo.GetLocalDate(DEFAULT)),120))";							 

		}

		if(trangthai.equals("2")){
			query = query + " and (tungay > (SELECT CONVERT(VARCHAR(10),DATEADD(day,0,dbo.GetLocalDate(DEFAULT)),120))" +
					" or denngay < ( SELECT CONVERT(VARCHAR(10),DATEADD(day,0,dbo.GetLocalDate(DEFAULT)),120)))";

		}


		//query = query + " order by a.NGAYTAO DESC, a.pk_seq DESC";

		System.out.println("query search: "+query);

		return query;
	}

	private String convertDate(String date) 
	{
		//chuyen dinh dang dd-MM-yyyy sang dinh dang yyyy-MM-dd
		if(!date.contains("-"))
			return "";
		String[] arr = date.split("-");
		if(arr[0].length() < arr[2].length())
			return arr[2] + "-" + arr[1] + "-" + arr[0];
		return date;
	}

	private String Delete(String ctkmId) 
	{
		dbutils db = new dbutils();


		String query=
				"select ( select count(*) as num from donhang_ctkm_trakm where ctkmId ='" + ctkmId + "' )" +
						"+(select COUNT(*) as num from ERP_DONDATHANG_CTKM_TRAKM  where CTKMID='" + ctkmId + "' ) as Num  ";
		ResultSet rs = db.get(query);
		try 
		{
			System.out.println("[ERP_DONDATHANG_CTKM_TRAKM]"+query);
			rs.next();
			if(!rs.getString("num").equals("0"))
			{
				rs.close();
				return "Chuong trinh khuyen mai nay da duoc su dung";
			}
			rs.close();

			db.getConnection().setAutoCommit(false);

			//Xoa Cac Bang Con Truoc
			if(!db.update("delete from ctkm_dkkm where ctkhuyenmai_fk='" + ctkmId + "'"))	
			{
				db.getConnection().rollback();
				return "Chương trình khuyến mãi này không xóa được";
			}
			if(!db.update("delete from ctkm_trakm where ctkhuyenmai_fk='" + ctkmId + "'"))
			{
				db.getConnection().rollback();
				return "Chương trình khuyến mãi này không xóa được";
			}
			if(!db.update("delete from ctkm_npp where ctkm_fk='" + ctkmId + "'"))
			{
				db.getConnection().rollback();
				return "Chương trình khuyến mãi này không xóa được";
			}
			if(!db.update("delete from PHANBOKHUYENMAI where ctkm_fk='" + ctkmId + "'"))
			{
				db.getConnection().rollback();
				return "Chương trình khuyến mãi này không xóa được";
			}
			//Xoa Bang Cha
			if(!db.update("delete from ctkhuyenmai where pk_seq = '" + ctkmId + "'"))
			{
				db.getConnection().rollback();
				return "Chương trình khuyến mãi này không xóa được";
			}

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);


			return "";

		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			return "Khong the xoa chuong trinh khuyen mai nay";
		}
		finally
		{
			db.shutDown();
		}


	}

	private String Duyet(String ctkmId, String noidung) 
	{
		dbutils db = new dbutils();
		try 
		{
			//db.update("update ctkhuyenmai set isduyet = 1 where pk_seq = '" + ctkmId + "'");
			db.update("update ctkhuyenmai set isduyet = '2' where pk_seq = '" + ctkmId + "'"); // sửa lại user dưới quyền isduyet = '2', user admin isduyet = '1' 2019-04-03
			db.shutDown();
			
			String msg = Mail(ctkmId, noidung);
			if(msg.length() > 0)
			{	return msg; }
			return "";
		} 
		catch (Exception e) 
		{
			db.shutDown();
			return "Khong the duyet chuong trinh khuyen mai nay";
		}
	}

	private String BoDuyet(String ctkmId) 
	{
		dbutils db = new dbutils();
		try 
		{
			db.update("update ctkhuyenmai set isduyet = 0 where pk_seq = '" + ctkmId + "'");
			db.update("commit");

			return "";

		} 
		catch (Exception e) 
		{
			return "Khong the bo duyet chuong trinh khuyen mai nay";
		}

	}
	
	private String Mail(String ctkmId, String noidung) 
	{
		dbutils db = new dbutils();
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			String sql = " SELECT ( SCHEME+' --- '+DIENGIAI ) AS DIENGIAI FROM CTKHUYENMAI WHERE PK_SEQ = '"+ ctkmId +"' ";
			ResultSet rs = db.get(sql);
			rs.next();
			String diengiai = rs.getString("DIENGIAI");
			rs.close();
			
			sql = "update ctkhuyenmai set ismail = '1' where pk_seq = '" + ctkmId + "' AND ismail = '0' ";
			if(db.updateReturnInt(sql) != 1)
			{
				db.getConnection().rollback();
				return "[1] Không thể yêu cầu gửi mail.";
			}
			else
			{
				if(MailAction(db, diengiai, noidung).length() == 0)
				{
					db.getConnection().commit();
					return "Yêu cầu gửi mail thành công.";
				}
				else
				{
					db.getConnection().rollback();
					return "[2] Không thể yêu cầu gửi mail.";
				}
			}
		} 
		catch (Exception e) 
		{
			try { db.getConnection().rollback(); } catch (SQLException e1) { e1.printStackTrace(); }
			return "Không thể yêu cầu gửi mail.";
		}
		finally{ try { db.getConnection().setAutoCommit(true); } catch (SQLException e) { e.printStackTrace(); } db.shutDown(); }
	}
	
	private String MailAction( dbutils db, String diengiai, String noidung) 
	{
		try {
			 String sql1 = "select email from TB_EMAIL WHERE TRANGTHAI = '1' ";
		 ResultSet rsdb1 = db.get(sql1);
		 String list_email="";
		while(rsdb1.next())
		{
			String email=rsdb1.getString("email");
			list_email+=","+email;
		}
		
		if(list_email.trim().length() > 0)
		{
			list_email = list_email.substring(1, list_email.length());
		}
		
		//SEND MAIL CANH BAO
		SendMail mail = new SendMail();
		String tb = "Đây là email tự động hệ thống DMS yêu cầu hỗ trợ "+ noidung +" chương trình khuyến mãi " + diengiai;
		return mail.postMailHTML(list_email,"", "Email hỗ trợ "+ noidung +" CTKM", tb,"","");
		//System.out.println(ReportAPI.NOW("yyyy-MM-dd hh:mm:ss")+" - Done.");
		} catch (Exception e) { e.printStackTrace(); return "Có lỗi trong việc gửi mail.";}
		
	}
	

	/*public static void main(String[] arg) throws InterruptedException
	{
		dbutils db=new dbutils();
		//MailAttachment fixed = new MailAttachment();							
		System.out.println("[MailAuto.....]");
		//FileInputStream fstream;
		try {
			 String sql1 = "select email from TB_EMAIL WHERE TRANGTHAI = '1' ";
		 ResultSet rsdb1=db.get(sql1);
		 String list_email="";
		while(rsdb1.next())
		{
			String email=rsdb1.getString("email");
			list_email+=","+email;
		}
		
		if(list_email.trim().length() > 0)
		{
			list_email = list_email.substring(1, list_email.length());
		}
		 
		//SEND MAIL CANH BAO
		SendMail mail = new SendMail();
		String tb = "Đây là email tự động hệ thống DMS yêu cầu hỗ trợ mở chương trình khuyến mãi " + ;
		//mail.postMailHTML(list_email,"", "Báo cáo Daily Report Tracking "+fromdate+" - "+todate, tb,"BCTuDong.xlsm","BCTuDong.xlsm");
		mail.postMailHTML(list_email,"", "Báo cáo Daily Report Tracking ", tb,"","");
		
		System.out.println(ReportAPI.NOW("yyyy-MM-dd hh:mm:ss")+" - Done.");
		} catch (Exception e) { e.printStackTrace(); }
	}*/
	
	String getValue(Sheet sheet,int col,int row,boolean replaceDauPhay)
	{
		try{
			if(replaceDauPhay)
				return sheet.getCell(col,row).getContents().trim().replaceAll(",", "").trim();
			else 
				return sheet.getCell(col,row).getContents().trim().trim();
		}catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	boolean kiemtra_scheme(dbutils db,String khoId,String scheme )
	{
		String sql = "select count(*) as num  from ctkhuyenmai where scheme ='" + scheme + "'";
		ResultSet rs = db.get(sql);
		try 
		{
			rs.next();
			if(rs.getString("num").equals("0")){
				rs.close();
				return true;
			}

		} 
		catch (SQLException e) {}
		return false;

	}
	private boolean checkExitsDKKM(dbutils db,String dkkmid,String ctkmid) 
	{
		ResultSet rs = db.get("select count(*) as sodong from ctkm_dkkm where dkkhuyenmai_fk = '" + dkkmid + "' and CTKHUYENMAI_FK <> '"+ctkmid+"' ");
		int sodong = 0;
		if(rs != null)
		{
			try 
			{
				if(rs.next())
				{
					sodong = rs.getInt("sodong");
					rs.close();
				}
			} catch(Exception e) { sodong = 0; }
		}
		if(sodong > 0)
			return true;
		return false;
	}
	private boolean checkExits(dbutils db,String id) 
	{
		ResultSet rs = db.get("select count(*) as sodong from ctkm_trakm inner join DONHANG_CTKM_TRAKM on ctkm_trakm.CTKHUYENMAI_FK=DONHANG_CTKM_TRAKM.CTKMID WHERE TRAKHUYENMAI_FK = '" + id + "'");
		int sodong = 0;
		if(rs != null)
		{
			try 
			{
				if(rs.next())
				{
					sodong = rs.getInt("sodong");
					rs.close();
				}
				rs.close();
			} catch(Exception e) { sodong = 0; }
			
		}
		
		rs = db.get("select count(*) as sodong from ctkm_trakm  inner join ERP_DONDATHANG_CTKM_TRAKM on ctkm_trakm.CTKHUYENMAI_FK=ERP_DONDATHANG_CTKM_TRAKM.CTKMID WHERE TRAKHUYENMAI_FK = '" + id + "'");
		if(rs != null)
		{
			try 
			{
				if(rs.next())
				{
					sodong = rs.getInt("sodong");
					rs.close();
				}
				rs.close();
			} catch(Exception e) { sodong = 0; }
		}
		
		if(sodong > 0)
			return true;
		return false;
	}
	boolean kiemtra_schemErp(dbutils db,String khoId,String schemeErp)
	{

		String sql = "select count(*) as num ,KHO_FK   from ctkhuyenmai where SCHEMEERP = N'" + schemeErp + "' group by KHO_FK";
		ResultSet rs = db.get(sql);

		try 
		{
			if(rs != null)
			{
				if(rs.next())
				{		

					if(rs.getString("num").equals("0")){
						rs.close();
						return true;
					}
					if(rs.getString("KHO_FK").equals(khoId))
					{
						rs.close();
						return true;
					}
				}
				else
					return true;
			}
			else
				return true;
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return false;

	}
	String getValue(Sheet sheet,int col,int row)
	{
		return sheet.getCell(col,row).getContents().trim().replaceAll(",", "");
	}
	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);	
	}
}
//gjjg
