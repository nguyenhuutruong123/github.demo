package geso.dms.center.servlets.tieuchithuong;

import geso.dms.center.beans.chitieunhanvien.IChiTieuNhanvien;
import geso.dms.center.beans.chitieunhanvien.imp.ChiTieuNhanvien;
import geso.dms.center.beans.tieuchithuong.ITichLuyItem;
import geso.dms.center.beans.tieuchithuong.ITieuchithuongTL;
import geso.dms.center.beans.tieuchithuong.ITieuchithuongTLList;
import geso.dms.center.beans.tieuchithuong.imp.TichLuyItem;
import geso.dms.center.beans.tieuchithuong.imp.TieuchithuongTL;
import geso.dms.center.beans.tieuchithuong.imp.TieuchithuongTLList;
import geso.dms.center.servlets.report.ReportAPI;
import geso.dms.center.util.Utility;
import geso.dms.distributor.db.sql.dbutils;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Types;
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

import com.aspose.cells.BorderLineType;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Worksheet;
import com.oreilly.servlet.MultipartRequest;

public class TieuchithuongTLUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out; 

	public TieuchithuongTLUpdateSvl() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		ITieuchithuongTL tctskuBean;

		this.out = response.getWriter();
		Utility util = new Utility();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);

		out.println(userId);

		if (userId.length() == 0)
			userId = util.antiSQLInspection(request.getParameter("userId"));

		String id = util.getId(querystring);

		tctskuBean = new TieuchithuongTL(id);
		tctskuBean.setId(id);
		tctskuBean.setUserId(userId);

		session.setAttribute("tctskuBean", tctskuBean);

		String nextJSP = "";
		if(querystring.indexOf("display") >= 0)
		{
			tctskuBean.init();
			nextJSP = "/AHF/pages/Center/TieuChiThuongTLDisplay.jsp";
		}
		else if(querystring.indexOf("copy") >= 0)
		{
			tctskuBean.init();
			tctskuBean.setId("");
			tctskuBean.setScheme("");
			tctskuBean.setDiengiai("");
			nextJSP = "/AHF/pages/Center/TieuChiThuongTLNew.jsp";
		}
		else
		{
			tctskuBean.init();
			nextJSP = "/AHF/pages/Center/TieuChiThuongTLUpdate.jsp";
		}

		response.sendRedirect(nextJSP);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{

		ITieuchithuongTL tctskuBean;
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		
		String contentType = request.getContentType();
		
		if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0))
		{
			 Upload(request, response);
			 return;
		}
		
		Utility util = new Utility();

		String id = util.antiSQLInspection(request.getParameter("id"));


		String action =   util.antiSQLInspection(request.getParameter("action")); 

		if(id == null){  	
			tctskuBean = new TieuchithuongTL("");
		}else{
			tctskuBean = new TieuchithuongTL(id);
		}

		String userId = util.antiSQLInspection(request.getParameter("userId"));
		if(userId == null)
			userId = "";
		System.out.println("userid "+userId);
		tctskuBean.setUserId(userId);

		String phanloai = request.getParameter("phanloai");
		if(phanloai == null)
			phanloai = "0";
		tctskuBean.setPhanloai(phanloai);

		String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
		if (diengiai == null)
			diengiai = "";
		tctskuBean.setDiengiai(diengiai);

		String scheme = util.antiSQLInspection(request.getParameter("scheme"));
		if (scheme == null)
			scheme = "";
		tctskuBean.setScheme(scheme);

		String thang = util.antiSQLInspection(request.getParameter("thang"));
		if (thang == null)
			thang = "";
		tctskuBean.setThang(thang);
		String ghichu = util.antiSQLInspection(request.getParameter("ghichu"));
		if (ghichu == null)
			ghichu= "";
		tctskuBean.setGhichu(ghichu);

		String nam = util.antiSQLInspection(request.getParameter("nam"));
		if (nam == null)
			nam = "";
		tctskuBean.setNam(nam);


		String phaidangky = util.antiSQLInspection(request.getParameter("phaidangky"));
		if (phaidangky == null)
			phaidangky = "0";
		tctskuBean.setPhandangky(phaidangky);

		String tinhtheosl = util.antiSQLInspection(request.getParameter("tinhtheosl"));
		if (tinhtheosl == null)
			tinhtheosl = "0";
		tctskuBean.setTinhtheoSl(tinhtheosl);

		String ngaytb_tungay = util.antiSQLInspection(request.getParameter("ngaytb_tungay"));
		if (ngaytb_tungay == null)
			ngaytb_tungay = "";
		tctskuBean.setNgayTB_Tungay(ngaytb_tungay);

		String ngaytb_denngay = util.antiSQLInspection(request.getParameter("ngaytb_denngay"));
		if (ngaytb_denngay == null)
			ngaytb_denngay = "";
		tctskuBean.setNgayTB_Denngay(ngaytb_denngay);

		try
		{
			


			String mucNpp = util.antiSQLInspection(request.getParameter("mucNPP"));
			if (mucNpp == null)
				mucNpp = "";
			tctskuBean.setMucNPP(mucNpp);
			System.out.println("mucNpp "+mucNpp);
			String mucnpp1 = util.antiSQLInspection(request.getParameter("mucnpp1"));

			if(mucnpp1 == null )
				mucnpp1 = "";
			System.out.println("mucnpp1 "+mucnpp1);
			String[] nppIds1 = request.getParameterValues("nppIds1");


			tctskuBean.getSpMuaList().clear();
			String[] maspMua = request.getParameterValues("maspTra");
			if(maspMua != null)
				for(int i = 0; i < maspMua.length ;i ++)
					if(maspMua[i] != null && maspMua[i].trim().length() > 0)
						tctskuBean.getSpMuaList().add(maspMua[i]);
			tctskuBean.setSanphamMuaRs();

			
			//Muc 1 thang
			String[] diengiaiMuc = request.getParameterValues("diengiaiMuc");

		
			String[] tumuc = request.getParameterValues("tumuc");


			String[] denmuc = request.getParameterValues("denmuc");


			String[] thuongSR = request.getParameterValues("chietkhau");


			String[] hinhthuc  = request.getParameterValues("hinhthuc");

			String[] thuongTDSR = request.getParameterValues("donvi");


			tctskuBean.getTichluyItemList().clear();
			if(diengiaiMuc != null)
			{
				for(int i = 0; i < diengiaiMuc.length ;i ++)
				{
					ITichLuyItem item = new TichLuyItem();
					item.setMuc(i);
					item.setTumuc(  Utility.parseDouble(tumuc[i].replace(",",""))   );
					item.setDenmuc(  Utility.parseDouble(denmuc[i].replace(",",""))   );
					item.setTratichluy( Utility.parseDouble(thuongSR[i].replace(",",""))   );
					item.setLoaitra(Utility.parseInt(thuongTDSR[i].replace(",",""))  );
					item.setHinhthuc(Utility.parseInt(hinhthuc[i].replace(",",""))  );
					
					
					
					if(item.getLoaitra() == 2)
					{
						if(item.getHinhthuc() == 1)// trả  bắt buộc thì tong luog set lai 0
							item.setTratichluy(0.0);
						
						item.getSpList().clear();
						String[] spMas  = request.getParameterValues("sanphamTRA"+i+".masanpham");
						String[] spSlss  = request.getParameterValues("sanphamTRA"+i+".soluong");
						if(spMas != null)
							for(int  x= 0  ; x <  spMas.length; x ++ )
							{
								if(spMas[x] != null && spMas[x].trim().length() > 0)
									item.getSpList().add(  new Object[] { spMas[x], Utility.parseDouble(spSlss[x].replace(",","")) }     );
							}
						item.setSanPhamRs( tctskuBean.getDb());


					}
					tctskuBean.getTichluyItemList().add(item);
				}
			}

			if(action.equals("newline"))
			{
				ITichLuyItem item = new TichLuyItem();
				item.setMuc( tctskuBean.getTichluyItemList().size()   );
				tctskuBean.getTichluyItemList().add(item);
			}
			if(action.equals("remove"))
			{
				int itemRemove = Utility.parseInt(request.getParameter("itemRemove") );
				if(itemRemove >= 0 && itemRemove < tctskuBean.getTichluyItemList().size() )
					tctskuBean.getTichluyItemList().remove(itemRemove);

				for(int i = 0; i < tctskuBean.getTichluyItemList().size(); i ++ )
					tctskuBean.getTichluyItemList().get(i).setMuc(i);

			}






			String doanhsothung = request.getParameter("doanhsothung");
			if(doanhsothung == null)
				doanhsothung = "0";
			tctskuBean.setDoanhsotheoThung(doanhsothung);

			String httt = request.getParameter("httt");
			if(httt == null)
				httt = "0";
			tctskuBean.setHTTT(httt);

			String ptTRACK = request.getParameter("ptTRACK");
			if(ptTRACK == null)
				ptTRACK = "0";
			tctskuBean.setPT_TRACK(ptTRACK);


		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}


		String tungay = request.getParameter("tungay");
		if(tungay == null)
			tungay = "";
		tctskuBean.setNgayDS_Tungay(tungay);

		String denngay = request.getParameter("denngay");
		if(denngay == null)
			denngay = "";
		tctskuBean.setNgayDS_Denngay(denngay);

		String khoId = request.getParameter("khoId");
		if(khoId == null)
			khoId = "";
		tctskuBean.setKhoIds(khoId);

		String activeTab = request.getParameter("activeTab");
		if(activeTab == null)
			activeTab = "";
		System.out.println("Tab "+activeTab);
		tctskuBean.setActiveTab(activeTab);


		String nspId = request.getParameter("nspId");
		if(nspId == null)
			nspId = "";
		tctskuBean.setNhomsanphamIds(nspId);

		if (action.equals("excel"))
		{
			
			try
			{
				response.setContentType("application/xls");
				response.setHeader("Content-Disposition", "attachment; filename=Tichluy_Template.xls");
				OutputStream out = response.getOutputStream();
				ExportToExcel(out,tctskuBean);
				tctskuBean.getDb().shutDown();	
			} 
			catch (Exception ex)
			{
				ex.printStackTrace();					
			}
		}
		if(action.equals("save"))
		{    
			if (id == null )
			{
				if (!tctskuBean.createTctSKU())
				{
					tctskuBean.setUserId(userId);

					tctskuBean.createRs();
					session.setAttribute("userId", userId);
					session.setAttribute("tctskuBean", tctskuBean);

					String nextJSP = "/AHF/pages/Center/TieuChiThuongTLNew.jsp";
					response.sendRedirect(nextJSP);
				}
				else
				{
					ITieuchithuongTLList obj = new TieuchithuongTLList();
					obj.setUserId(userId);

					obj.init("");
					session.setAttribute("obj", obj);

					String nextJSP = "/AHF/pages/Center/TieuChiThuongTL.jsp";
					response.sendRedirect(nextJSP);
				}	
			}
			else
			{
				if (!(tctskuBean.updateTctSKU()))
				{			
					tctskuBean.setUserId(userId);
					System.out.println("voday1");
					tctskuBean.createRs();
					session.setAttribute("userId", userId);
					session.setAttribute("tctskuBean", tctskuBean);

					String nextJSP = "/AHF/pages/Center/TieuChiThuongTLUpdate.jsp";
					response.sendRedirect(nextJSP);
				}
				else
				{
					ITieuchithuongTLList obj = new TieuchithuongTLList();
					obj.setUserId(userId);
					System.out.println("voday2");
					obj.init("");
					session.setAttribute("obj", obj);

					String nextJSP = "/AHF/pages/Center/TieuChiThuongTL.jsp";
					response.sendRedirect(nextJSP);
				}
			}
		}
		else
		{		
			tctskuBean.createRs();
			if(action.equals("loadSP_NHOM"))
			{
				tctskuBean.getSpMuaList().clear();
				tctskuBean.loadSP_NHOM();
			}

			session.setAttribute("userId", userId);
			session.setAttribute("tctskuBean", tctskuBean);

			String nextJSP;
			if (id == null){
				nextJSP = "/AHF/pages/Center/TieuChiThuongTLNew.jsp";
				System.out.println("bbbbbbbbbbbb");
			}
			else{
				System.out.println("aaaaaaaaaaaa");
				nextJSP = "/AHF/pages/Center/TieuChiThuongTLUpdate.jsp";   						
			}
			response.sendRedirect(nextJSP);

		}

	}
	
	public void Redirect(ITieuchithuongTL  tctskuBean ,String nextJSP, HttpServletResponse response,  HttpSession session) throws IOException
	{

		session.setAttribute("tctskuBean", tctskuBean);
		response.sendRedirect(nextJSP);
	}

	public void Upload(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		int indexRow = 8;
		int socotfixcung = 2;
		 HttpSession session =  request.getSession();
		
		MultipartRequest multi = new MultipartRequest(request, "C:\\java-tomcat\\data\\", 20000000, "UTF-8");
		ITieuchithuongTL tctskuBean = new TieuchithuongTL();

		String id = multi.getParameter("id");
		if(id == null ) id ="";
		tctskuBean.setId(id);
		tctskuBean.setUserId( (String)session.getAttribute("userId")  );
		tctskuBean.init();
		tctskuBean.getDataUpload().clear();
		int count =tctskuBean.getTichluyItemList().size();
		
		Enumeration files = multi.getFileNames();
		String filenameu = "";
		while (files.hasMoreElements())
		{
			String name = (String) files.nextElement();
			filenameu = multi.getFilesystemName(name);
			System.out.println("name  " + name);
			;
		}
		String filename = "C:\\java-tomcat\\data\\" + filenameu;
		String values = "";
		
		if (filename.length() > 0)
		{
			// doc file excel
			File file = new File(filename);
			System.out.println("filename  " + file);
			Workbook workbook;
			try
			{
				workbook = Workbook.getWorkbook(file);
				Sheet sheet = workbook.getSheet(0);
				Cell[] cells = sheet.getRow(indexRow);
				
				for (int i = indexRow ; i < sheet.getRows(); i++)
				{
					System.out.println("Vao Day: " + i);
					cells = sheet.getRow(i);
					if(cells.length > 0)
					{

						String npp = getStringValue(cells,0);
						if(npp.trim().length() >0)
							for(int z= 0; z < count; z ++)
							{
								int  muc = z;
								double sosuat = getDoubleValue(cells,z + socotfixcung);
								if(values.trim().length() > 0)
									values += "\n  union all select ? as nppId, ? as muc, ? as [sosuat] ";
								else
									values = "\n  select ? as nppId, ? as muc, ? as [sosuat] ";
								tctskuBean.getDataUpload().add(npp);
								tctskuBean.getDataUpload().add(muc + "");
								tctskuBean.getDataUpload().add(sosuat + "");
							}
					}

				}
				if(values.trim().length() <= 0)
				{
					tctskuBean.setMsg("Không lấy được dữ liệu upload");
					Redirect(tctskuBean ,"/AHF/pages/Center/TieuChiThuongTLUpdate.jsp",  response, session);
					return;
				}
				
				tctskuBean.uploadPhanbo(values);
				
				System.out.println("this.msg = "+ tctskuBean.getMsg() );
				Redirect(tctskuBean ,"/AHF/pages/Center/TieuChiThuongTLUpdate.jsp",  response, session);
				
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				tctskuBean.setMsg( "Phát sinh lỗi khi Upload ");
				Redirect(tctskuBean ,"/AHF/pages/Center/TieuChiThuongTLUpdate.jsp",  response, session);
			}
		}
		
	}

	public String getDateTime() 
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);	
	}
	private void ExportToExcel(OutputStream out,ITieuchithuongTL obj )throws Exception
	{
		try
		{ 			
			com.aspose.cells.Workbook workbook = new com.aspose.cells.Workbook();
			workbook.setFileFormatType(FileFormatType.EXCEL2003);

			String query = setQuery(obj);
			TaoBaoCao(workbook, obj, query);
			workbook.save(out);			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw new Exception(ex.getMessage());
		}

	}
	
	public String setQuery (ITieuchithuongTL obj )
	{
		
		int count = obj.getTichluyItemList().size();		
	
		String data=  "";
		String diengiai = "";
		
		for(int i = 0; i < count ; i ++ )	
		{
			if(data.trim().length() <=0)
			{
				data = "["+i+"]";
				diengiai = "["+i+"] as ["+ (i+1) +"] ";
			}
			else
			{
				data += ",["+i+"]";
				diengiai += ",["+i+"] as ["+ (i+1) +"] ";
			}
		}
	
		String query =	 	 "\n select pk_seq [MÃ HỆ THỐNG],manpp [ MÃ NPP],ten [TÊN NPP] , "+diengiai+" " + 
							 "\n from NHAPHANPHOI npp " + 
							 "\n left join " + 
							 "\n ( " + 
							 "\n 	select * from " + 
							 "\n 	( " + 
							 "\n 		select npp_fk ,muc,soluong " + 
							 "\n 		from tieuchithuongtl_npp " + 
							 "\n 		where thuongtl_fk =  " + obj.getId() + 
							 "\n 	)	 dt PIVOT ( sum(soluong) FOR muc IN ("+data+")) AS pvt " + 
							 "\n ) pb on npp.pk_seq = pb.npp_fk ";
						
		System.out.println("query BC="+ query);
		return query;
	}
	
	private void TaoBaoCao(com.aspose.cells.Workbook workbook,ITieuchithuongTL obj,String query )throws Exception
	{
		try
		{
			com.aspose.cells.Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			com.aspose.cells.Cells cells = worksheet.getCells();

			com.aspose.cells.Cell cell = cells.getCell("B3");
			cells.setRowHeight(0, 50.0f);
			 cell = cells.getCell("A1");
			 ReportAPI.getCellStyle(cell, Color.RED, true, 16,"FORM UPLOAD TÍCH LŨY");

			cell = cells.getCell("A2");
			ReportAPI.getCellStyle(cell, Color.BLUE, true, 10, "Ngày tạo : "+ getDateTime());		
		
			ResultSet rs=  obj.getDb().get(query);

			ResultSetMetaData rsmd = rs.getMetaData();
			int socottrongSql = rsmd.getColumnCount();

			int countRow = 7;
			int column = 0;
			for( int i =1 ; i <=socottrongSql ; i ++  )
			{

				cell = cells.getCell(countRow, column + i-1 );cell.setValue(rsmd.getColumnName(i));
				ReportAPI.setCellBackground(cell, new Color(219,229,241), BorderLineType.THIN, true, 0);	

			}
			countRow ++;
			while(rs.next())
			{
				for(int i =1;i <=socottrongSql ; i ++)
				{
					Color c = Color.WHITE;
					cell = cells.getCell(countRow,column + i-1 );
					if(rsmd.getColumnType(i) == Types.DOUBLE || rsmd.getColumnType(i) == Types.INTEGER || rsmd.getColumnType(i) == Types.DECIMAL )
					{
						cell.setValue(rs.getDouble(i));
						ReportAPI.setCellBackground(cell, c, BorderLineType.THIN, true, 41);
					}
					else
					{
						cell.setValue(rs.getString(i));
						ReportAPI.setCellBackground(cell, c, BorderLineType.THIN, true, 0);
					}
				}
				++countRow;
			}
			
			if(rs!=null)rs.close();
			


		}catch(Exception ex){

			ex.printStackTrace();
			throw new Exception("Lỗi không lấy tích lũy !");
		}
	}
	
	public String getStringValue(Cell[] cells,int vitri)
	{
		try
		{
			return cells[vitri].getContents().toString().replace("\t", "").replace(",", "").replace(" ", "").trim();
		}
		catch (Exception e) {
			return "";
		}
	}

	public double getDoubleValue(Cell[] cells,int vitri)
	{
		try
		{
			return Double.parseDouble(cells[vitri].getContents().toString().replace("\t", "").replace(",", "").replace(" ", "").trim());
		}
		catch (Exception e) {
			return 0.0;
		}
	}
}
