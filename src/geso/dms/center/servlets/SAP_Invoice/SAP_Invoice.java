package geso.dms.center.servlets.SAP_Invoice;

import geso.dms.center.beans.report.IBcHoaDon;
import geso.dms.center.beans.report.imp.BcHoaDon;
import geso.dms.center.db.sql.dbutils;
import geso.dms.center.util.Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;


import com.oreilly.servlet.MultipartRequest;


@WebServlet("/SAP_Invoice")
public class SAP_Invoice extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String UPLOAD_DIRECTORY = "c:\\java-tomcat\\upload\\excel\\";
	public SAP_Invoice() 
	{
		super();
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		IBcHoaDon obj;
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		HttpSession session = request.getSession();

		Utility util = new Utility();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);

		if (userId.length() == 0)
			userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));

		String loaihoadon = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("loaihoadon"));
		if (loaihoadon == null)
			loaihoadon = "0";

		obj = new BcHoaDon();
		obj.setUserId(userId);
		obj.setView("TT");
		String nextJSP = "";

		String query=
				" select distinct [Charnnel] ,[InvoiceId],[Invoice_RevertFor],[LegalNumber],[CustomerId],[CustomerName],[InVoiceDate],[InvoiceType]  "+
						"   ,[InVoice_BVAT] ,[Invoice_VAT],[Status],[Created_Date] "+
						"  ,[SO_Number],[PO_Number],Readed,NhapHang_FK,ReadedTime "+ 
						"			from DMSsap.dbo.SAP_Invoice_Header " +
						"    where 1=1 ";

		obj.init(query,"InVoiceDate desc,CustomerName");

		nextJSP = "/AHF/pages/Center/SAP_Invoice.jsp";
		session.setAttribute("obj", obj);
		response.sendRedirect(nextJSP);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String contentType = request.getContentType();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		Utility util = new Utility();
		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		HttpSession session = request.getSession();

		OutputStream out = response.getOutputStream();

		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		if (action == null)
			action = "";

		IBcHoaDon obj = new BcHoaDon();
		obj.setUserId(userId);


		String query=
				" select distinct [Charnnel] ,[InvoiceId],[Invoice_RevertFor],[LegalNumber],[CustomerId],[CustomerName],[InVoiceDate],[InvoiceType]  "+
						"   ,[InVoice_BVAT] ,[Invoice_VAT],[Status],[Created_Date] ,Readed,NhapHang_FK,ReadedTime "+
						"  ,[SO_Number],[PO_Number] "+ 
						"			from DMSsap.dbo.SAP_Invoice_Header " +
						"  where 1=1 ";



		obj.setView("TT");

		String tungay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Sdays") == null ? "" : request.getParameter("Sdays"));
		obj.setTuNgay(tungay);

		String denngay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Edays") == null ? "" : request.getParameter("Edays"));
		obj.setDenNgay(denngay);

		String vungId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId") == null ? "" : request.getParameter("vungId"));
		obj.setVungId(vungId);

		String kbhId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kbhId") == null ? "" : request.getParameter("kbhId"));
		obj.setKbhId(kbhId);

		String ttId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ttId") == null ? "" : request.getParameter("ttId"));
		obj.setTtId(ttId);

		String nhomId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nhomId") == null ? "" : request.getParameter("nhomId"));
		obj.setNhomId(nhomId);

		String khId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khId") == null ? "" : request.getParameter("khId"));
		obj.setKhId(khId);

		String ddkdId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ddkdId") == null ? "" : request.getParameter("ddkdId"));
		obj.setDdkdId(ddkdId);

		String spId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("spId") == null ? "" : request.getParameter("spId"));
		obj.setSpId(spId);

		String nppId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId") == null ? "" : request.getParameter("nppId"));
		obj.setNppId(nppId);


		String soId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("soId") == null ? "" : request.getParameter("soId"));
		obj.setSoId(soId);


		String hoadon = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("hoadon") == null ? "" : request.getParameter("hoadon"));
		obj.setSohoadon(hoadon);


		String InvoiceId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("InvoiceId") == null ? "" : request.getParameter("InvoiceId"));
		obj.setInvoiceId(InvoiceId);


		String Invoice_RevertFor = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Invoice_RevertFor") == null ? "" : request.getParameter("Invoice_RevertFor"));
		obj.setInvoice_RevertFor(Invoice_RevertFor);


		String LegalNumber = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("LegalNumber") == null ? "" : request.getParameter("LegalNumber"));
		obj.setLegalNumber(LegalNumber);


		String CustomerId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("CustomerId") == null ? "" : request.getParameter("CustomerId"));
		obj.setCustomerId(CustomerId);


		String CustomerName = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("CustomerName") == null ? "" : request.getParameter("CustomerName"));
		obj.setCustomerName(CustomerName);



		String InVoiceDate = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("InVoiceDate") == null ? "" : request.getParameter("InVoiceDate"));
		obj.setInVoiceDate(InVoiceDate);



		String InvoiceType = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("InvoiceType") == null ? "" : request.getParameter("InvoiceType"));
		obj.setInvoiceType(InvoiceType);


		String SO_Number = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("SO_Number") == null ? "" : request.getParameter("SO_Number"));
		obj.setSO_Number(SO_Number);

		String PO_Number = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("PO_Number") == null ? "" : request.getParameter("PO_Number"));
		obj.setPO_Number(PO_Number);



		if(InvoiceId.length()>0)
		{
			query+=" and InvoiceId  like '%"+InvoiceId+"%' "		;
		}

		if(Invoice_RevertFor.length()>0)
		{
			query+=" and Invoice_RevertFor  like '%"+Invoice_RevertFor+"%' "		;
		}


		if(LegalNumber.length()>0)
		{
			query+=" and LegalNumber  like '%"+LegalNumber+"%' "		;
		}

		if(CustomerId.length()>0)
		{
			query+=" and CustomerId  like '%"+CustomerId+"%' "		;
		}




		if(CustomerName.length()>0)
		{
			query+=" and CustomerName  like '%"+CustomerName+"%' "		;
		}



		if(Invoice_RevertFor.length()>0)
		{
			query+=" and Invoice_RevertFor  like '%"+Invoice_RevertFor+"%' "		;
		}



		if(SO_Number.length()>0)
		{
			query+=" and SO_Number  like '%"+SO_Number+"%' "		;
		}



		if(PO_Number.length()>0)
		{
			query+=" and PO_Number  like '%"+PO_Number+"%' "		;
		}


		if(tungay.length()>0)
		{
			query+=" and 	CONVERT(char(10),cast(InVoiceDate as datetime),20) >='"+tungay+"'   "		;
		}

		if(denngay.length()>0)
		{
			query+=" and 	CONVERT(char(10),cast(InvoiceDate as datetime),20) <='"+tungay+"'   "		;
		}

		obj.set_Action(action);

		System.out.println("___ATION " + action);
		if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0))
		{
			String msg= readExcel(request);
			obj.setMsg(msg);

			obj.init(query,"InvoiceDate desc,CustomerName " );
			session.setAttribute("obj", obj);
			String nextJSP = "";
			nextJSP = "/AHF/pages/Center/SAP_Invoice.jsp";
			response.sendRedirect(nextJSP);

		}else
		{
			
			if(action.equals("save"))
			{
				obj.setMsg(obj.Save());
				obj.init(query,"InvoiceDate desc,CustomerName " );
				session.setAttribute("obj", obj);
				String nextJSP = "";
				nextJSP = "/AHF/pages/Center/SAP_Invoice.jsp";
				response.sendRedirect(nextJSP);
			}
			else if (action.equals("view") || action.equals("next") || action.equals("prev"))
			{
				obj.setNxtApprSplitting(Integer.parseInt(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nxtApprSplitting"))));
				obj.setUserId(userId);
				obj.init(query,"InVoiceDate desc,CustomerName");
				obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
				session.setAttribute("obj", obj);

				String nextJSP = "/AHF/pages/Center/SAP_Invoice.jsp";
				response.sendRedirect(nextJSP);
			} 

			else
			{
				obj.init(query,"InvoiceDate desc,CustomerName " );
				session.setAttribute("obj", obj);
				String nextJSP = "";
				nextJSP = "/AHF/pages/Center/SAP_Invoice.jsp";
				response.sendRedirect(nextJSP);
			}
			
		}
		
	}
	private String readExcel(HttpServletRequest request)
	//private static String readExcel()
	{
		String msg="";
		dbutils db = new dbutils();

		String columName= "BillToNumber,SoHDGGTGT,DeliveryDate,Material,Price,BillingQty,NetValue,TaxAmount,Total";
		try
		{
			MultipartRequest multi;
			multi = new MultipartRequest(request, "C:\\java-tomcat\\data\\", 20000000, "UTF-8");
			Enumeration files = multi.getFileNames();
			String filename = "";
			while (files.hasMoreElements())
			{
				String name = (String) files.nextElement();
				filename = multi.getFilesystemName(name);

			}
			filename = "C:\\java-tomcat\\data\\" + ""+filename+"";

			if (filename.length() > 0)
			{
				File file = new File(filename);
				Workbook workbook;
				workbook = Workbook.getWorkbook(file);
				Sheet sheet = workbook.getSheet(0);
				int indexRow = 1;
				int sodong = sheet.getRows();
				int nCol=	sheet.getColumns();
				String query=" ";
				String sql_TABLE="";
				for (int row=indexRow; row < sodong ; row++)
				{
					query ="select ";
					for (int col=0; col < nCol ; col++)
					{
						String colName=getValue(sheet,col,0).trim();
						String value=getValue(sheet,col,row).trim();
						if(colName.trim().length()>0&&value.trim().length()>0 && columName.indexOf(colName)>=0)
							query+=" N'"+value+"' as "+colName+" , ";
					}
					if(query.trim().length()>7)
					{
						query =query.substring(0,query.length()-3) +"\n";
						sql_TABLE += query +" union all \n";	
					}
				}
				//System.out.println("sql_TABLE []"+sql_TABLE.substring(0,sql_TABLE.length()-13));
				sql_TABLE=sql_TABLE.substring(0,sql_TABLE.length()-13);

				query="select distinct Material from ("+sql_TABLE+") as data where Material not in (select Ma From SanPham ) and len(dbo.trim(Material))>0 ";
				ResultSet rs=db.get(query);
				while(rs.next())
				{
					msg+=" MÃ SẢN PHẨM "+rs.getString("Material")+" chưa được khai báo trên hệ thống ,vui lòng kiểm tra lại ! \n "+query;
				}
				rs.close();


				query="select distinct BillToNumber from ("+sql_TABLE+") as data where BillToNumber not in (select Ma From NhaPhanPhoi ) and len(dbo.trim(BillToNumber))>0 ";
				rs=db.get(query);
				while(rs.next())
				{
					msg+=" MÃ NHÀ PHÂN PHỐI "+rs.getString("BillToNumber")+" chưa được khai báo trên hệ thống ,vui lòng kiểm tra lại ! \n "+query;
				}
				rs.close();

				db.getConnection().setAutoCommit(false);
				query=
						"	drop table NhapHang_Import "+
								"	create table NhapHang_Import "+
								"	      ( "+
								"	      BillToNumber nvarchar(100), "+
								"	      SoldTo nvarchar(100),BillToName nvarchar(200), "+
								"	      DeliveryDate  nvarchar(200), "+
								"	      SoHDGGTGT nvarchar(200),PostingDate nvarchar(200),Material nvarchar(200), "+
								"	      MaterialNumber nvarchar(200), "+
								"	      BillingQty float,PriceVND float,NetValue float,TaxVND float,TotalVND float, "+
								"	Price float,TaxAmount float,Total float,  "+
								"	Created_DATE datetime default dbo.GetLocalDate(DEFAULT))  ";
				if(!db.update(query))
				{
					msg="Lỗi phát sinh khi Import Tồn Kho "+query;
					db.getConnection().rollback();
				}
				query=
						"	insert into NhapHang_Import(BillToNumber,SoHDGGTGT,DeliveryDate,Material,Price,BillingQty,NetValue,TaxAmount,Total)  "+
								"	select BillToNumber,SoHDGGTGT, (select DateTime from [ConvertToDate](DeliveryDate) ) as DeliveryDate ,Material,Price,BillingQty,NetValue,TaxAmount,Total  "+
								"	from  ("+sql_TABLE+") as data  ";
				if(!db.update(query))
				{
					msg="Lỗi phát sinh khi Import Tồn Kho "+query;
					db.getConnection().rollback();
				}


				query=
						"insert into nhaphang(NGAYNHAN,SOTIENBVAT,NGUOITAO,NGUOISUA,TRANGTHAI,NPP_FK,NCC_FK,VAT,SOTIENAVAT,DVKD_FK,KBH_FK,DATHANG_FK,CHUNGTU,NGAYCHUNGTU,KHO_FK,  "+
								"		      HDTAICHINH,LOAIHOADON,NGAYTAO,NGAYSUA)  "+
								"		select distinct DeliveryDate,0 as SOTIENBVAT,100368 as ntao,100368 as nsua,0,npp.PK_SEQ,100022,0 as vat,0 as avat,100068,100025,  "+
								"		            NULL,data.SoHDGGTGT,DeliveryDate,  "+
								"		      100000,data.SoHDGGTGT,0,DeliveryDate,DeliveryDate  "+
								"		from  NhapHang_Import as data inner join NHAPHANPHOI npp on npp.MA=data.billToNumber  "+
								"		where not exists (select 1 from nhaphang where HDTAICHINH=data.SoHDGGTGT)  ";

				if(!db.update(query))
				{
					msg="Lỗi phát sinh khi Import Tồn Kho "+query;
					db.getConnection().rollback();
				}

				query=
						"	insert into nhaphang_sp(NHAPHANG_FK,SANPHAM_FK,soluong,DONVI,GIANET,TIENBVAT,VAT,TIENAVAT,GIAGROSS,SCHEME) "+
								" select * from" +
								" ( " +
								"	select (select pk_Seq from NhapHang where HDTAICHINH=data.SoHDGGTGT) as NHAPHANG_FK , "+
								"	    data.Material, sum(data.BillingQty) as BillingQty ,dv.DONVI,  data.Price, sum(data.NetValue) as NetValue ,sum(data.TaxAmount) as TaxAmount ,sum(data.Total) as Total,sum(data.NetValue) as GIAGROSS ,'' as  SCHEME "+
								"	from  NhapHang_Import as data inner join sanpham sp on sp.MA=data.Material "+
								"	      inner join DONVIDOLUONG dv on dv.PK_SEQ=sp.DVDL_FK "+
								"	group by SoHDGGTGT,data.Material,data.Price,dv.DONVI ) as Nhap " +
								" where "+
								"	not exists "+
								"	(  "+
								"	select 1 from nhaphang_sp where NHAPHANG_FK =Nhap.NHAPHANG_FK "+
								"	      and SANPHAM_FK=Nhap.Material and soluong=Nhap.BillingQty and Nhap.Price=GIANET "+
								"	)  ";
				if(!db.update(query))
				{
					msg="Lỗi phát sinh khi Import Tồn Kho "+query;
					db.getConnection().rollback();
				}

				query=
						" update nh set VAT=sp.VAT,SOTIENAVAT=sp.AVAT,SOTIENBVAT=sp.BVAT "+
								"  from "+ 
								"  ( "+
								"  select SUM(b.Tienavat) as AVAT,SUM(b.VAT) as VAT,SUM(b.TIENBVAT) as BVAT,b.NHAPHANG_FK "+
								"  from nhaphang a inner join nhaphang_sp b on b.NHAPHANG_FK=a.PK_SEQ "+
								" group by b.NHAPHANG_FK  "+
								" )as sp inner join nhaphang nh on nh.PK_SEQ=sp.NHAPHANG_FK ";
				if(!db.update(query))
				{
					msg="Lỗi phát sinh khi Import Tồn Kho "+query;
					db.getConnection().rollback();
				}

				if(msg.length()>0)
				{
					return msg;
				}
				db.getConnection().commit();
				db.getConnection().setAutoCommit(true);

			}
		} catch (Exception e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return "Exception nhận hàng "+e1.getMessage();
		}
		finally
		{
			db.shutDown();
		}

		return "Đọc thành công nhập hàng";
	}

	private static String getValue(Sheet sheet, int column, int row)
	{
		Cell cell;
		cell = sheet.getCell(column, row);
		try{
			return cell.getContents();
		}catch(Exception er){
			return	"0";
		}
	}

}
