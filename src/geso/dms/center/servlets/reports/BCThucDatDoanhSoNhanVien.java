package geso.dms.center.servlets.reports;

import geso.dms.center.beans.stockintransit.IStockintransit;
import geso.dms.center.beans.stockintransit.imp.Stockintransit;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.servlets.report.ReportAPI;
import geso.dms.center.util.Utility;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.BorderLineType;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;
import com.aspose.cells.Picture;

public class BCThucDatDoanhSoNhanVien extends HttpServlet
{
	private static final long serialVersionUID = 1L;


	private String setQueryNSM(IStockintransit obj)
	{
		
			
		String query ="select * from [dbo].[ufn_ThucDatDoanhSoNSM]('"+obj.getdenngay()+"')";	
		
		System.out.println("____setQuery NSM: " + query);
		return query;
	}

	private String setQueryVung(IStockintransit obj)
	{
		
		String query ="select * from [dbo].[ufn_ThucDatDoanhSoVung]('"+obj.getdenngay()+"')";
		System.out.println("____setQuery Vung: " + query);
		return query;
	}

	private String setQueryRSM(IStockintransit obj)
	{
	
		String query ="select * from [dbo].[ufn_ThucDatDoanhSoRSM]('"+obj.getdenngay()+"')";
		System.out.println("____setQuery RSM: " + query);
		return query;
	}

	private String setQueryGSBH(IStockintransit obj) 
	{
		String query ="select * from [dbo].[ufn_ThucDatDoanhSoGSBH]('"+obj.getdenngay()+"') ";
		System.out.println("____setQuery GSBH: " + query);
		return query;
	}

	private String setQueryTDV(IStockintransit obj) 
	{

		String query ="select * from [dbo].[ufn_ThucDatDoanhSoTDV]('"+obj.getdenngay()+"')";
		System.out.println("____setQuery TDV: " + query);
		return query;
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


		String view=geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view"));
		if(view == null)
			view = "";

		obj.setLoaiMenu(view);

		obj.setuserId(userId);
		obj.init();	

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);

		String nextJSP = "/AHF/pages/Center/BCThucDatDoanhSoNhanVien.jsp";
		response.sendRedirect(nextJSP);
	}


	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();
		Utility  util = new Utility();

		String userId = (String) session.getAttribute("userId");
		if (userId == null)
			userId = "";
		obj.setuserId(userId);

		String userTen = (String) session.getAttribute("userTen");
		obj.setuserTen(userTen);


		String view=geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("view"));
		if(view == null)
			view = "";

		obj.setLoaiMenu(view);



		String vungId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId")));
		if (vungId == null)
			vungId = "";
		obj.setvungId(vungId);

		String tinhthanhId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tinhthanhId")));
		if (tinhthanhId == null)
			tinhthanhId = "";
		obj.setTinhthanhid(tinhthanhId);

		String nppId="";
		nppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));
		if (nppId == null)
			nppId = "";
		obj.setnppId(nppId);

		//System.out.println("nppId ="  + obj.getnppId());

		String ddkdId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ddkdId")));
		if (ddkdId == null)
			ddkdId = "";
		obj.setDdkd(ddkdId);

		String khId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khId")));
		if (khId == null)
			khId = "";
		obj.setkhId(khId);

		String tungay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay")));
		if (tungay == null)
			tungay = "";
		obj.settungay(tungay);

		String denngay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay")));
		if (denngay == null)
			denngay = "";
		obj.setdenngay(denngay);



		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		if (action.equals("tao")) 
		{
			try 
			{
				request.setCharacterEncoding("utf-8");

				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=BCThucDatDoanhSoNhanVien.xlsm");

				OutputStream out = response.getOutputStream();



				CreatePivotTable(out, obj);


			} 
			catch (Exception ex) 
			{
				request.getSession().setAttribute("errors", ex.getMessage());
			}
		}
		else
		{
			obj.init();

			session.setAttribute("obj", obj);
			session.setAttribute("userId", userId);
			String nextJSP = "/AHF/pages/Center/BCThucDatDoanhSoNhanVien.jsp";
			response.sendRedirect(nextJSP);

		}

	}

	private void CreatePivotTable(OutputStream out,IStockintransit obj) throws Exception {
		try 
		{
			dbutils db = new dbutils();
			
			String query ="select pk_seq, thang,nam from chitieunhanvien where trangthai != 2 and month('"+obj.getdenngay()+"') =thang and  Year('"+obj.getdenngay()+"') = nam ";
			ResultSet rsInfo = db.get(query);
			if(rsInfo.next())
			{
				obj.setToMonth(rsInfo.getString("thang"));
				obj.setToYear(rsInfo.getString("nam"));
				obj.setSpId(rsInfo.getString("pk_seq"));
				String dauthang = ( rsInfo.getString("nam") +"-" + (rsInfo.getInt("thang") <=9? "0"+rsInfo.getInt("thang"):rsInfo.getInt("thang")+"" ) +"-01"  );
				obj.settungay(dauthang);
			}
			
			Workbook workbook = new Workbook();

			
			
			List<nhanvien> rsmL = TaoDanhSach(db, obj,setQueryRSM(obj));
			
			List<nhanvien> gsbhL = TaoDanhSach(db, obj,setQueryGSBH(obj));
			
	
			List<nhanvien> nsmL = TaoDanhSach(db, obj,setQueryNSM(obj));
	
			List<nhanvien> vL =TaoDanhSach(db, obj,setQueryVung(obj));
			
			List<nhanvien> tdvL = TaoDanhSach(db, obj,setQueryTDV(obj));

			
			// ve total kenh 25
			System.out.println("nsmL= "+nsmL.size());
			if(nsmL.size() >0)
			{
				workbook.getWorksheets().addSheet();
				Color cl = Color.YELLOW;
				CreateSR(cl,"NSM",0,out,workbook,obj,db,nsmL.get(0),rsmL,null );
			}
		
			// ve cac vung
			for( int i = 0; i < rsmL.size(); i++)
			{
				workbook.getWorksheets().addSheet();
				List<nhanvien> gsbhL_tructhuoc = getNhanVienTrucThuoc(rsmL.get(i), gsbhL);
				List<nhanvien> vL_tructhuoc = getNhanVienTrucThuoc(rsmL.get(i), vL);
				System.out.println("tdvL_tructhuoc= "+gsbhL_tructhuoc.size());
				System.out.println("vL_tructhuoc= "+vL_tructhuoc.size());
				Color cl = new Color(74-i,69-i,42-i);
				CreateSR(cl,"RSM",1+i,out,workbook,obj,db,rsmL.get(i),gsbhL_tructhuoc,vL_tructhuoc );
			}
			// ve cac khu vuc
			for( int i = 0; i < gsbhL.size(); i++)
			{
				workbook.getWorksheets().addSheet();
				List<nhanvien> tdvL_tructhuoc = getNhanVienTrucThuoc(gsbhL.get(i), tdvL);
				
				
				System.out.println("tdvL_tructhuoc= "+tdvL_tructhuoc.size());
				Color cl = new Color(74+i,69+i,42+i);
				CreateSR(cl,"ASM",1+i+rsmL.size(),out,workbook,obj,db,gsbhL.get(i),tdvL_tructhuoc,null );
			}

			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);


			workbook.save(out);

		} 
		catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("Error Message: " + ex.getMessage());
		}
	}

	private void CreateSR(Color colorTab ,String loai,int tab,OutputStream out,Workbook workbook,IStockintransit obj,dbutils db , nhanvien gsbh,List<nhanvien> tdvL_tructhuoc ,List<nhanvien> vL)
	{	
		
		Worksheets worksheets = workbook.getWorksheets();

		Worksheet worksheet = worksheets.getSheet(tab);
		worksheet.setTabColor(colorTab);
		System.out.println("loai = " + loai);
		worksheet.setName(gsbh.Phutrach); //gsbh.Ten + " - " +
		Cells cells = worksheet.getCells();	 

		// vẽ giám sát bán hàng
		int dongbatdau = 3;

		Cell cell = null;
		cells.setRowHeight(0, 20.0f);	 
		dongbatdau = FillHeader( dongbatdau, cells, cell, gsbh);
		dongbatdau = FillData(dongbatdau, cells, cell, gsbh);
		
		// vẽ TDV
		String vungOld = "";
		for (int i = 0 ; i < tdvL_tructhuoc.size() ; i ++ )
		{
			nhanvien tdv = tdvL_tructhuoc.get(i);

			if(!vungOld.equals(tdv.DiaLy)&& loai.equals("RSM")&&vL != null)
			{
				nhanvien v = new nhanvien();
				for(int z=0;z < vL.size(); z ++)
					if(vL.get(z).DiaLy.equals(tdv.DiaLy))
					{
						v = vL.get(z);
						break;
					}		

				dongbatdau = FillHeader(dongbatdau, cells, cell, v);				
				dongbatdau = FillData(dongbatdau, cells, cell, v);

			}
			vungOld = tdv.DiaLy;
			
			dongbatdau = FillHeader(dongbatdau, cells, cell, tdv);
			dongbatdau = FillData(dongbatdau, cells, cell, tdv);
			
		}

	}

	

	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy : hh-mm-ss");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public class nhanvien 
	{
		public String DiaLy ="";
		public String Tructhuoc ="";
		public String Id ="";
		public String Ten="";
		public String Phutrach="";
		public String mafast ="";
		
		public double tdOrderToday = 0;
		public double tdOrder = 0;
		public double ctOrder = 0;
		public double OrderVsTarget = 0;
		
		public double tdPharmacyPurchasedToday = 0;
		public double tdPharmacyPurchased = 0;
		public double ctPharmacyPurchased = 0;
		public double PharmacyPurchasedVsTarget = 0;
		
		public double tdNewPharmacyToday = 0;
		public double tdNewPharmacy = 0;
		public double ctNewPharmacy = 0;
		public double NewPharmacyVsTarget = 0;
		

		public List<nhanvienchitiet> nvct = new ArrayList<nhanvienchitiet>();
		
		public nhanvien copy()
		{
			nhanvien nv = new nhanvien();
			nv.DiaLy = DiaLy;
			nv.Tructhuoc = Tructhuoc;
			nv.Id = Id;
			nv.Ten = Ten;
			nv.Phutrach = Phutrach;
			nv.mafast = mafast;
			
			nv.tdOrderToday = tdOrderToday;
			nv.tdOrder = tdOrder;
			nv.ctOrder = ctOrder;
			nv.OrderVsTarget = OrderVsTarget;
			
			nv.tdPharmacyPurchasedToday = tdPharmacyPurchasedToday;
			nv.tdPharmacyPurchased = tdPharmacyPurchased;
			nv.ctPharmacyPurchased = ctPharmacyPurchased;
			nv.PharmacyPurchasedVsTarget = PharmacyPurchasedVsTarget;
			
			nv.tdNewPharmacyToday = tdNewPharmacyToday;
			nv.tdNewPharmacy = tdNewPharmacy;
			nv.ctNewPharmacy = ctNewPharmacy;
			nv.NewPharmacyVsTarget = NewPharmacyVsTarget;
			
			
			for(int i = 0; i <nvct.size(); i++ )
			{
				nv.nvct.add(nvct.get(i).copy());
			}
			return nv;
		}
	}
	public class nhanvienchitiet
	{
		public nhanvienchitiet()
		{}
		public nhanvienchitiet( String[] param)
		{
			int count = 0;

			this.nsp_fk= param[count++];
			this.nspten= param[count++];
			this.kv = param[count++];
			this.slToday = Double.parseDouble(param[count++]);
			this.slMTD= Double.parseDouble(param[count++]);
			this.slMonthTarget= Double.parseDouble(param[count++]);
			this.slMonthVsTarget= Double.parseDouble(param[count++]);

			this.dsToday= Double.parseDouble(param[count++]);
			this.dsMTD= Double.parseDouble(param[count++]);
			this.dsMonthTarget= Double.parseDouble(param[count++]);
			this.dsMonthVsTarget= Double.parseDouble(param[count++]);

			this.slActual= Double.parseDouble(param[count++]);
			this.slYearTarget= Double.parseDouble(param[count++]);
			this.slYearVsTarget= Double.parseDouble(param[count++]);

			this.dsActual= Double.parseDouble(param[count++]);
			this.dsYearTarget= Double.parseDouble(param[count++]);
			this.dsYearVsTarget= Double.parseDouble(param[count++]);

			this.Volume= Double.parseDouble(param[count++]);
			this.Value= Double.parseDouble(param[count++]);
			this.Grow= Double.parseDouble(param[count++]);

		}

		public String nsp_fk="";
		public String nspten="";
		public String kv ="";

		public double slToday=0;
		public double slMTD=0;
		public double slMonthTarget=0;
		public double slMonthVsTarget=0;

		public double dsToday=0;
		public double dsMTD=0;
		public double dsMonthTarget=0;
		public double dsMonthVsTarget=0;

		public double slActual=0;
		public double slYearTarget=0;
		public double slYearVsTarget=0;

		public double dsActual=0;
		public double dsYearTarget=0;
		public double dsYearVsTarget=0;

		public double Volume=0;
		public double Value=0;
		public double Grow=0;
		
		public nhanvienchitiet copy()
		{
			nhanvienchitiet nvct = new nhanvienchitiet();
			nvct.nsp_fk=this.nsp_fk;
			nvct.nspten=this.nspten;
			nvct.kv =this.kv;

			nvct.slToday= this.slToday;
			nvct.slMTD=this.slMTD;
			nvct.slMonthTarget=this.slMonthTarget;
			nvct.slMonthVsTarget=this.slMonthVsTarget;

			nvct.dsToday=this.dsToday;
			nvct.dsMTD=this.dsMTD;
			nvct.dsMonthTarget=this.dsMonthTarget;
			nvct.dsMonthVsTarget=this.dsMonthVsTarget;

			nvct.slActual=this.slActual;
			nvct.slYearTarget=slYearTarget;
			nvct.slYearVsTarget=slYearVsTarget;

			nvct.dsActual=dsActual;
			nvct.dsYearTarget=dsYearTarget;
			nvct.dsYearVsTarget=dsYearVsTarget;

			nvct.Volume=Volume;
			nvct.Value=Value;
			nvct.Grow=Grow;
			return nvct;
		}

	}

	public List<nhanvien> TaoDanhSach(dbutils db,IStockintransit obj,String query)
	{
		try
		{
			ResultSet rsGSBH = db.get(query);
			ResultSetMetaData rsmd = rsGSBH.getMetaData();
			int socottrongSql = rsmd.getColumnCount();
			List<nhanvien> gsbhL = new ArrayList<nhanvien>();
			List<nhanvienchitiet> gsbhCtL = new ArrayList<nhanvienchitiet>();
			nhanvien gs = null;
			while (rsGSBH.next())
			{
				if(rsGSBH.getInt("rowNhanVien") == 1)
				{
					if(gs != null) 
					{
						gs.nvct = gsbhCtL;
						gsbhL.add(gs);

					}
					gs =new nhanvien();
					gs.Tructhuoc = rsGSBH.getString("tructhuocId");
					gs.Id =rsGSBH.getString("manv");
					gs.Ten =rsGSBH.getString("tennv");
					gs.Phutrach = rsGSBH.getString("phutrach");
					gs.mafast = rsGSBH.getString("mafast");
					gs.DiaLy = rsGSBH.getString("DiaLy");
					
					gs.tdOrderToday = rsGSBH.getDouble("tdOrderToday"); 
					gs.tdOrder =  rsGSBH.getDouble("tdOrder");
					gs.ctOrder =  rsGSBH.getDouble("ctOrder");
					gs.OrderVsTarget =  rsGSBH.getDouble("OrderVsTarget");
					
					gs.tdPharmacyPurchasedToday =rsGSBH.getDouble("tdPharmacyPurchasedToday") ;
					gs.tdPharmacyPurchased =rsGSBH.getDouble("tdPharmacyPurchased") ;
					gs.ctPharmacyPurchased =rsGSBH.getDouble("ctPharmacyPurchased") ;
					gs.PharmacyPurchasedVsTarget =rsGSBH.getDouble("PharmacyPurchasedVsTarget") ;
					
					gs.tdNewPharmacyToday =rsGSBH.getDouble("tdNewPharmacyToday") ;
					gs.tdNewPharmacy =rsGSBH.getDouble("tdNewPharmacy") ;
					gs.ctNewPharmacy =rsGSBH.getDouble("ctNewPharmacy") ;
					gs.NewPharmacyVsTarget =rsGSBH.getDouble("NewPharmacyVsTarget") ;
					
					gsbhCtL = new ArrayList<nhanvienchitiet>();

				}
				String[] param= new String [22];
				//System.out.println("socottrongSq=" + socottrongSql);
				for (int z = 19; z <= socottrongSql; z ++)
				{
					param[z-19] = rsGSBH.getString(z);
				}
				nhanvienchitiet gsct = new nhanvienchitiet(param);
				gsbhCtL.add(gsct);

			}
			if(gs != null) 
			{
				gs.nvct = gsbhCtL;
				gsbhL.add(gs);

			}
			return gsbhL;

		}catch(Exception e)
		{
			e.printStackTrace();
			return new ArrayList<nhanvien>();
		}

	}


	public List<nhanvien> getNhanVienTrucThuoc (nhanvien nv,List<nhanvien> nvL )
	{
		List<nhanvien> nvRs = new ArrayList<nhanvien>();
		for( int i = 0; i <nvL.size() ; i ++)
		{
			if(nvL.get(i).Tructhuoc.equals(nv.Id))
				nvRs.add(nvL.get(i));
		}
		return nvRs;
	}
	
	
	public List<nhanvien> getNhanVienCungVung (nhanvien nv,List<nhanvien> nvL )
	{
		List<nhanvien> nvRs = new ArrayList<nhanvien>();
		for( int i = 0; i <nvL.size() ; i ++)
		{
			if(nvL.get(i).DiaLy.equals(nv.DiaLy))
				nvRs.add(nvL.get(i));
		}
		return nvRs;
	}
	
	
	public List<nhanvien> getVungList (List<nhanvien> gsbhL)
	{
		List<nhanvien> vRs = new ArrayList<nhanvien>();
		
		while (gsbhL.size() > 0)
		{
			nhanvien gs = gsbhL.get(0);
			List<nhanvien> gsCungVung =getNhanVienCungVung(gs,gsbhL);
			for( int i = 0; i < gsbhL.size(); i ++)
			{
				if(gs.DiaLy.equals(gsbhL.get(i).DiaLy))
					gsbhL.remove(i--);
			}
			vRs.add(TaoVung(gsCungVung) );			
		}
		
		return vRs;
	}
	
	public nhanvien TaoVung(List<nhanvien> gsbhL)
	{
		nhanvien v = gsbhL.get(0).copy();	
		v.Ten = "TOTAL " + gsbhL.get(0).DiaLy;	
		return v;
	}
	
	public static String GetExcelColumnName(int columnNumber)
	{
		int dividend = columnNumber;
		String columnName = "";
		int modulo;

		while (dividend > 0)
		{
			modulo = (dividend - 1) % 26;
			columnName = (char)(65 + modulo) + columnName;
			dividend = (int)((dividend - modulo) / 26);
		} 

		return columnName;
	}
	
	public static int FillData(int dongbatdau,Cells cells,Cell cell,nhanvien gsbh)
	{
		for (int i = 0; i < gsbh.nvct.size(); i ++ )
		{


			int cot = 1;
			cell = cells.getCell(dongbatdau + i,cot++);cell.setValue(gsbh.nvct.get(i).nsp_fk);
			ReportAPI.setCellBackground(cell,Color.WHITE, BorderLineType.THIN, true,0,TextAlignmentType.CENTER);
			cell = cells.getCell(dongbatdau + i,cot++);cell.setValue(gsbh.nvct.get(i).nspten);
			ReportAPI.setCellBackground(cell,Color.WHITE, BorderLineType.THIN, true,0,TextAlignmentType.CENTER);
			cell = cells.getCell(dongbatdau + i,cot++);cell.setValue(gsbh.nvct.get(i).kv);
			ReportAPI.setCellBackground(cell,Color.WHITE, BorderLineType.THIN, true,0,TextAlignmentType.CENTER);
			
			cell = cells.getCell(dongbatdau + i,cot++);cell.setValue(gsbh.nvct.get(i).slToday);
			ReportAPI.setCellBackground(cell,Color.WHITE, BorderLineType.THIN, true,41,TextAlignmentType.CENTER);
			cell = cells.getCell(dongbatdau + i,cot++);cell.setValue(gsbh.nvct.get(i).slMTD);
			ReportAPI.setCellBackground(cell,Color.WHITE, BorderLineType.THIN, true,41,TextAlignmentType.CENTER);
			cell = cells.getCell(dongbatdau + i,cot++);cell.setValue(gsbh.nvct.get(i).slMonthTarget);
			ReportAPI.setCellBackground(cell,Color.WHITE, BorderLineType.THIN, true,41,TextAlignmentType.CENTER);
			cell = cells.getCell(dongbatdau + i,cot++); cell.setValue(gsbh.nvct.get(i).slMonthVsTarget);
			ReportAPI.setCellBackground(cell,Color.WHITE, BorderLineType.THIN, true,10,TextAlignmentType.CENTER);
			
			cell = cells.getCell(dongbatdau + i,cot++);cell.setValue(gsbh.nvct.get(i).dsToday);
			ReportAPI.setCellBackground(cell,Color.WHITE, BorderLineType.THIN, true,41,TextAlignmentType.CENTER);
			cell = cells.getCell(dongbatdau + i,cot++);cell.setValue(gsbh.nvct.get(i).dsMTD);
			ReportAPI.setCellBackground(cell,Color.WHITE, BorderLineType.THIN, true,41,TextAlignmentType.CENTER);
			cell = cells.getCell(dongbatdau + i,cot++);cell.setValue(gsbh.nvct.get(i).dsMonthTarget);
			ReportAPI.setCellBackground(cell,Color.WHITE, BorderLineType.THIN, true,41,TextAlignmentType.CENTER);
			cell = cells.getCell(dongbatdau + i,cot++); cell.setValue(gsbh.nvct.get(i).dsMonthVsTarget);
			ReportAPI.setCellBackground(cell,Color.WHITE, BorderLineType.THIN, true,10,TextAlignmentType.CENTER);
			
			cell = cells.getCell(dongbatdau + i,cot++);cell.setValue(gsbh.nvct.get(i).slActual);
			ReportAPI.setCellBackground(cell,Color.WHITE, BorderLineType.THIN, true,41,TextAlignmentType.CENTER);
			cell = cells.getCell(dongbatdau + i,cot++);cell.setValue(gsbh.nvct.get(i).slYearTarget);
			ReportAPI.setCellBackground(cell,Color.WHITE, BorderLineType.THIN, true,41,TextAlignmentType.CENTER);
			cell = cells.getCell(dongbatdau + i,cot++);cell.setValue(gsbh.nvct.get(i).slYearVsTarget);
			ReportAPI.setCellBackground(cell,Color.WHITE, BorderLineType.THIN, true,10,TextAlignmentType.CENTER);
			
			cell = cells.getCell(dongbatdau + i,cot++);cell.setValue(gsbh.nvct.get(i).dsActual);
			ReportAPI.setCellBackground(cell,Color.WHITE, BorderLineType.THIN, true,41,TextAlignmentType.CENTER);
			cell = cells.getCell(dongbatdau + i,cot++);cell.setValue(gsbh.nvct.get(i).dsYearTarget);
			ReportAPI.setCellBackground(cell,Color.WHITE, BorderLineType.THIN, true,41,TextAlignmentType.CENTER);
			cell = cells.getCell(dongbatdau + i,cot++);cell.setValue(gsbh.nvct.get(i).dsYearVsTarget);
			ReportAPI.setCellBackground(cell,Color.WHITE, BorderLineType.THIN, true,10,TextAlignmentType.CENTER);
			
			cell = cells.getCell(dongbatdau + i,cot++);cell.setValue(gsbh.nvct.get(i).Volume);
			ReportAPI.setCellBackground(cell,Color.WHITE, BorderLineType.THIN, true,41,TextAlignmentType.CENTER);
			cell = cells.getCell(dongbatdau + i,cot++);cell.setValue(gsbh.nvct.get(i).Value);
			ReportAPI.setCellBackground(cell,Color.WHITE, BorderLineType.THIN, true,41,TextAlignmentType.CENTER);
			
			cell = cells.getCell(dongbatdau + i,cot++);
			cell.setFormula("=IFERROR(P"+ (dongbatdau + i + 1) +"/T"+(dongbatdau + i + 1)+"-1,0)");
			//cell.setValue(gsbh.nvct.get(i).Grow);		
			
			ReportAPI.setCellBackground(cell,Color.WHITE, BorderLineType.THIN, true,10,TextAlignmentType.CENTER);
		}
		for (int cotTmp = 4;cotTmp <21;cotTmp++ )
		{
			if(cotTmp == 20)
			{
				cell = cells.getCell(dongbatdau + gsbh.nvct.size(),cotTmp);
				cell.setFormula("=IFERROR(P"+ (dongbatdau + gsbh.nvct.size() + 1) +"/T"+(dongbatdau + gsbh.nvct.size() + 1)+"-1,0)");
				ReportAPI.setCellBackground(cell,new Color(191,191,191), BorderLineType.THIN, true, 10);
			}
			else
			if( (cotTmp >=4 && cotTmp <=7)|| (cotTmp >=12 && cotTmp <=14)|| (cotTmp==18) )// số luong thì ko tính sum
			{
				cell = cells.getCell(dongbatdau + gsbh.nvct.size(),cotTmp);
				ReportAPI.setCellBackground(cell,new Color(191,191,191), BorderLineType.THIN, true, 0);
			}
			else
			if(cotTmp == 7 ||  cotTmp == 11||  cotTmp == 14||  cotTmp == 17)
			{
				cell = cells.getCell(dongbatdau + gsbh.nvct.size(),cotTmp);
				cell = cells.getCell(dongbatdau + gsbh.nvct.size(),cotTmp);
				String formula = "="+GetExcelColumnName(cotTmp-1)+(dongbatdau +gsbh.nvct.size() +1 ) +"/"+GetExcelColumnName(cotTmp)+(dongbatdau +gsbh.nvct.size() +1);
				cell.setFormula(formula);
				ReportAPI.setCellBackground(cell,new Color(191,191,191), BorderLineType.THIN, true, 10);
			}
			else
			{
				cell = cells.getCell(dongbatdau + gsbh.nvct.size(),cotTmp);
				String formula = "=SUM("+GetExcelColumnName(cotTmp+1)+(dongbatdau +1 ) +":"+GetExcelColumnName(cotTmp+1)+(dongbatdau +gsbh.nvct.size())+")";
				cell.setFormula(formula);
				ReportAPI.setCellBackground(cell,new Color(191,191,191), BorderLineType.THIN, true, 41);
			}
		}
		dongbatdau  +=gsbh.nvct.size() +1;
		
		int cot = 2;
		cell = cells.getCell(dongbatdau,cot ++);cell.setValue(" # of order ");
		ReportAPI.setCellBackground(cell,new Color(197,190,151), BorderLineType.THIN, true,0,TextAlignmentType.CENTER);	
		cell = cells.getCell(dongbatdau,cot ++);cell.setValue(gsbh.Phutrach);
		ReportAPI.setCellBackground(cell,new Color(197,190,151), BorderLineType.THIN, true,0,TextAlignmentType.CENTER);
		cell = cells.getCell(dongbatdau,cot ++);cell.setValue(gsbh.tdOrderToday);
		ReportAPI.setCellBackground(cell,new Color(197,190,151), BorderLineType.THIN, true,41,TextAlignmentType.CENTER);
		cell = cells.getCell(dongbatdau,cot ++);cell.setValue(gsbh.tdOrder);
		ReportAPI.setCellBackground(cell,new Color(197,190,151), BorderLineType.THIN, true,41,TextAlignmentType.CENTER);
		cell = cells.getCell(dongbatdau,cot ++);cell.setValue(gsbh.ctOrder);
		ReportAPI.setCellBackground(cell,new Color(197,190,151), BorderLineType.THIN, true,41,TextAlignmentType.CENTER);
		cell = cells.getCell(dongbatdau,cot ++);cell.setValue(gsbh.OrderVsTarget);
		ReportAPI.setCellBackground(cell,new Color(197,190,151), BorderLineType.THIN, true,10,TextAlignmentType.CENTER);
		dongbatdau ++;
		cot = 2;
		cell = cells.getCell(dongbatdau,cot ++);cell.setValue(" # of Pharmacy purchased ");
		ReportAPI.setCellBackground(cell,new Color(197,190,151), BorderLineType.THIN, true,0,TextAlignmentType.CENTER);	
		cell = cells.getCell(dongbatdau,cot ++);cell.setValue(gsbh.Phutrach);
		ReportAPI.setCellBackground(cell,new Color(197,190,151), BorderLineType.THIN, true,0,TextAlignmentType.CENTER);
		cell = cells.getCell(dongbatdau,cot ++);cell.setValue(gsbh.tdPharmacyPurchasedToday);
		ReportAPI.setCellBackground(cell,new Color(197,190,151), BorderLineType.THIN, true,41,TextAlignmentType.CENTER);
		cell = cells.getCell(dongbatdau,cot ++);cell.setValue(gsbh.tdPharmacyPurchased);
		ReportAPI.setCellBackground(cell,new Color(197,190,151), BorderLineType.THIN, true,41,TextAlignmentType.CENTER);
		cell = cells.getCell(dongbatdau,cot ++);cell.setValue(gsbh.ctPharmacyPurchased);
		ReportAPI.setCellBackground(cell,new Color(197,190,151), BorderLineType.THIN, true,41,TextAlignmentType.CENTER);
		cell = cells.getCell(dongbatdau,cot ++);cell.setValue(gsbh.PharmacyPurchasedVsTarget);
		ReportAPI.setCellBackground(cell,new Color(197,190,151), BorderLineType.THIN, true,10,TextAlignmentType.CENTER);
		dongbatdau ++;
		cot = 2;
		cell = cells.getCell(dongbatdau,cot ++);cell.setValue(" # of new pharmacy ");
		ReportAPI.setCellBackground(cell,new Color(197,190,151), BorderLineType.THIN, true,0,TextAlignmentType.CENTER);	
		cell = cells.getCell(dongbatdau,cot ++);cell.setValue(gsbh.Phutrach);
		ReportAPI.setCellBackground(cell,new Color(197,190,151), BorderLineType.THIN, true,0,TextAlignmentType.CENTER);
		cell = cells.getCell(dongbatdau,cot ++);cell.setValue(gsbh.tdNewPharmacyToday);
		ReportAPI.setCellBackground(cell,new Color(197,190,151), BorderLineType.THIN, true,41,TextAlignmentType.CENTER);
		cell = cells.getCell(dongbatdau,cot ++);cell.setValue(gsbh.tdNewPharmacy);
		ReportAPI.setCellBackground(cell,new Color(197,190,151), BorderLineType.THIN, true,41,TextAlignmentType.CENTER);
		cell = cells.getCell(dongbatdau,cot ++);cell.setValue(gsbh.ctNewPharmacy);
		ReportAPI.setCellBackground(cell,new Color(197,190,151), BorderLineType.THIN, true,41,TextAlignmentType.CENTER);
		cell = cells.getCell(dongbatdau,cot ++);cell.setValue(gsbh.NewPharmacyVsTarget);
		ReportAPI.setCellBackground(cell,new Color(197,190,151), BorderLineType.THIN, true,10,TextAlignmentType.CENTER);

		dongbatdau +=2;
		return dongbatdau;
	}
	
	public static int FillHeader(int dongbatdau,Cells cells,Cell cell,nhanvien gsbh)
	{
		int cotbatdau = 0;   
		
		cell = cells.getCell(dongbatdau,0);			
		ReportAPI.setCellBackground(cell,  new Color(255,204,0), BorderLineType.NONE, true, 0,TextAlignmentType.CENTER);
		ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, gsbh.Ten);

		cell = cells.getCell(dongbatdau,1);			
		ReportAPI.setCellBackground(cell,  Color.WHITE, BorderLineType.NONE, true, 0,TextAlignmentType.CENTER);
		ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, gsbh.Phutrach);
		
		dongbatdau ++;

		cells.merge(dongbatdau, cotbatdau, dongbatdau +1 ,cotbatdau );cell = cells.getCell(dongbatdau,cotbatdau);cell.setValue("KPIs");		
		ReportAPI.setCellBackground(cell,  new Color(204,255,255), BorderLineType.THIN, true, 0,TextAlignmentType.CENTER);
		ReportAPI.setBorder_Style_MergerCell(cells,dongbatdau,dongbatdau +1,cotbatdau,cotbatdau,BorderLineType.THIN,Color.BLACK,cell.getStyle());


		cotbatdau ++;
		cells.merge(dongbatdau, cotbatdau, dongbatdau +1,cotbatdau );cell = cells.getCell(dongbatdau,cotbatdau);cell.setValue("ID");
		ReportAPI.setCellBackground(cell, new Color(204,255,255), BorderLineType.THIN, true, 0,TextAlignmentType.CENTER);
		ReportAPI.setBorder_Style_MergerCell(cells,dongbatdau,dongbatdau +1,cotbatdau,cotbatdau,BorderLineType.THIN,Color.BLACK,cell.getStyle());
		
		cotbatdau ++;
		cells.merge(dongbatdau, cotbatdau, dongbatdau +1 ,cotbatdau );cell = cells.getCell(dongbatdau,cotbatdau);cell.setValue("Product");
		ReportAPI.setCellBackground(cell,new Color(204,255,255), BorderLineType.THIN, true, 0,TextAlignmentType.CENTER);
		ReportAPI.setBorder_Style_MergerCell(cells,dongbatdau,dongbatdau +1,cotbatdau,cotbatdau,BorderLineType.THIN,Color.BLACK,cell.getStyle());
		
		cotbatdau ++;
		cells.merge(dongbatdau , cotbatdau,dongbatdau +1,cotbatdau );cell = cells.getCell(dongbatdau,cotbatdau);cell.setValue("Area");
		ReportAPI.setCellBackground(cell, new Color(204,255,255), BorderLineType.THIN, true, 0,TextAlignmentType.CENTER);
		ReportAPI.setBorder_Style_MergerCell(cells,dongbatdau,dongbatdau +1,cotbatdau,cotbatdau,BorderLineType.THIN,Color.BLACK,cell.getStyle());
		
		cotbatdau ++;

		cells.merge(dongbatdau , cotbatdau, dongbatdau ,cotbatdau+3 );cell = cells.getCell(dongbatdau ,cotbatdau);cell.setValue("This Month(Volume)");		
		ReportAPI.setCellBackground(cell, new Color(238,236,225), BorderLineType.THIN, true, 0,TextAlignmentType.CENTER);
		ReportAPI.setBorder_Style_MergerCell(cells,dongbatdau,dongbatdau,cotbatdau,cotbatdau +3,BorderLineType.THIN,Color.BLACK,cell.getStyle());
		
		cell = cells.getCell(dongbatdau + 1,cotbatdau);cell.setValue("Today");
		ReportAPI.setCellBackground(cell, new Color(238,236,225), BorderLineType.THIN, true, 0,TextAlignmentType.CENTER);
		cell = cells.getCell(dongbatdau + 1,cotbatdau +1);cell.setValue("MTD");
		ReportAPI.setCellBackground(cell, new Color(238,236,225), BorderLineType.THIN, true, 0,TextAlignmentType.CENTER);
		cell = cells.getCell(dongbatdau + 1,cotbatdau +2);cell.setValue("Target");
		ReportAPI.setCellBackground(cell, new Color(238,236,225), BorderLineType.THIN, true, 0,TextAlignmentType.CENTER);
		cell = cells.getCell(dongbatdau + 1,cotbatdau +3);cell.setValue("Vs Target");
		ReportAPI.setCellBackground(cell, new Color(238,236,225), BorderLineType.THIN, true, 0,TextAlignmentType.CENTER);

		cotbatdau +=4;

		cells.merge(dongbatdau , cotbatdau, dongbatdau ,cotbatdau+3 );cell = cells.getCell(dongbatdau ,cotbatdau);cell.setValue("This Month(Value)");
		ReportAPI.setCellBackground(cell, new Color(238,236,225), BorderLineType.THIN, true, 0,TextAlignmentType.CENTER);
		ReportAPI.setBorder_Style_MergerCell(cells,dongbatdau,dongbatdau ,cotbatdau,cotbatdau+3,BorderLineType.THIN,Color.BLACK,cell.getStyle());
		
		cell = cells.getCell(dongbatdau + 1,cotbatdau);cell.setValue("Today");
		ReportAPI.setCellBackground(cell, new Color(238,236,225), BorderLineType.THIN, true, 0,TextAlignmentType.CENTER);
		cell = cells.getCell(dongbatdau + 1,cotbatdau +1);cell.setValue("MTD");
		ReportAPI.setCellBackground(cell, new Color(238,236,225), BorderLineType.THIN, true, 0,TextAlignmentType.CENTER);
		cell = cells.getCell(dongbatdau + 1,cotbatdau +2);cell.setValue("Target");
		ReportAPI.setCellBackground(cell, new Color(238,236,225), BorderLineType.THIN, true, 0,TextAlignmentType.CENTER);
		cell = cells.getCell(dongbatdau + 1,cotbatdau +3);cell.setValue("Vs Target");
		ReportAPI.setCellBackground(cell, new Color(238,236,225), BorderLineType.THIN, true, 0,TextAlignmentType.CENTER);

		cotbatdau +=4;

		cells.merge(dongbatdau , cotbatdau, dongbatdau ,cotbatdau+2 );cell = cells.getCell(dongbatdau ,cotbatdau);cell.setValue("YTD 2015(Volume)");
		ReportAPI.setCellBackground(cell, new Color(215,228,188), BorderLineType.THIN, true, 0,TextAlignmentType.CENTER);
		ReportAPI.setBorder_Style_MergerCell(cells,dongbatdau,dongbatdau ,cotbatdau,cotbatdau+2,BorderLineType.THIN,Color.BLACK,cell.getStyle());
		
		cell = cells.getCell(dongbatdau + 1,cotbatdau);cell.setValue("Actual");
		ReportAPI.setCellBackground(cell, new Color(215,228,188), BorderLineType.THIN, true, 0,TextAlignmentType.CENTER);
		cell = cells.getCell(dongbatdau + 1,cotbatdau +1);cell.setValue("Target");
		ReportAPI.setCellBackground(cell, new Color(215,228,188), BorderLineType.THIN, true, 0,TextAlignmentType.CENTER);
		cell = cells.getCell(dongbatdau + 1,cotbatdau +2);cell.setValue("Vs Target");
		ReportAPI.setCellBackground(cell, new Color(215,228,188), BorderLineType.THIN, true, 0,TextAlignmentType.CENTER);

		cotbatdau +=3;

		cells.merge(dongbatdau , cotbatdau,dongbatdau ,cotbatdau+2 );cell = cells.getCell(dongbatdau ,cotbatdau);cell.setValue("YTD 2015(Value)");
		ReportAPI.setCellBackground(cell, new Color(215,228,188), BorderLineType.THIN, true, 0,TextAlignmentType.CENTER);
		ReportAPI.setBorder_Style_MergerCell(cells,dongbatdau,dongbatdau ,cotbatdau,cotbatdau+2,BorderLineType.THIN,Color.BLACK,cell.getStyle());
		
		cell = cells.getCell(dongbatdau + 1,cotbatdau);cell.setValue("Actual");
		ReportAPI.setCellBackground(cell, new Color(215,228,188), BorderLineType.THIN, true, 0,TextAlignmentType.CENTER);
		cell = cells.getCell(dongbatdau + 1,cotbatdau +1);cell.setValue("Target");
		ReportAPI.setCellBackground(cell, new Color(215,228,188), BorderLineType.THIN, true, 0,TextAlignmentType.CENTER);
		cell = cells.getCell(dongbatdau + 1,cotbatdau +2);cell.setValue("Vs Target");
		ReportAPI.setCellBackground(cell, new Color(215,228,188), BorderLineType.THIN, true, 0,TextAlignmentType.CENTER);

		cotbatdau +=3;

		cells.merge(dongbatdau , cotbatdau,dongbatdau ,cotbatdau+2 );cell = cells.getCell(dongbatdau ,cotbatdau);cell.setValue("YTD 2014");
		ReportAPI.setCellBackground(cell,new Color(215,228,188), BorderLineType.THIN, true, 0,TextAlignmentType.CENTER);
		ReportAPI.setBorder_Style_MergerCell(cells,dongbatdau,dongbatdau ,cotbatdau,cotbatdau+2,BorderLineType.THIN,Color.BLACK,cell.getStyle());
		
		cell = cells.getCell(dongbatdau + 1,cotbatdau);cell.setValue("Volume");
		ReportAPI.setCellBackground(cell,new Color(215,228,188), BorderLineType.THIN, true, 0,TextAlignmentType.CENTER);
		cell = cells.getCell(dongbatdau + 1,cotbatdau +1);cell.setValue("Value");
		ReportAPI.setCellBackground(cell, new Color(215,228,188), BorderLineType.THIN, true, 0,TextAlignmentType.CENTER);
		cell = cells.getCell(dongbatdau + 1,cotbatdau +2);cell.setValue("%Grow");
		ReportAPI.setCellBackground(cell,new Color(215,228,188), BorderLineType.THIN, true, 0,TextAlignmentType.CENTER);

		dongbatdau += 2;
		return dongbatdau;
	}
	
}
