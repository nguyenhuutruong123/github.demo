package geso.dms.center.servlets.reports;

import geso.dms.center.beans.duyettrakhuyenmai.IDuyettrakhuyenmai;
import geso.dms.center.beans.stockintransit.IStockintransit;
import geso.dms.center.beans.stockintransit.imp.Stockintransit;
import geso.dms.center.beans.tieuchithuong.ITieuchithuongTLList;
import geso.dms.center.beans.tieuchithuong.imp.TieuchithuongTLList;
import geso.dms.center.servlets.report.ReportAPI;
import geso.dms.center.util.Utility;
import geso.dms.distributor.db.sql.dbutils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Z.DB;

import com.aspose.cells.BorderLineType;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Font;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;
import com.cete.dynamicpdf.pageelements.IListProperties;

public class TieuchithuongTLReportSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
    
    public TieuchithuongTLReportSvl() {
        super();
    }
    
    public String getQuery (IStockintransit obj ) throws SQLException
    {
    	String query = "\n select tl.SCHEME [MÃ CTTL] , tl.DIENGIAI [CTTL], d.DIENGIAI [DUYỆT TRẢ], V.TEN [VÙNG], KV.TEN [KHU VỰC],NPP.TEN [NPP ] , KH.SMARTID [MÃ KH], KH.TEN [KHÁCH HÀNG] " +
    		"\n , DH.PK_SEQ [ĐƠN HÀNG TRẢ] ,DH.NGAYNHAP [NGÀY TRẢ] " +
    		"\n		,  case when  duyet.donvi = 2 " +
			"\n 	then" +
			
			"\n 		STUFF(( " +
			"\n 			SELECT '; ' + spx.MA + '-' + spx.TEN + ': ' + cast( xxx.soluong as varchar)  + ' ' + dv.DONVI  + char(10)  " +
			"\n 			from  DUYETTRAKHUYENMAI_KHACHHANG_SanPham xxx " +
			"\n 			inner join SANPHAM spx on spx.pk_seq = xxx.sanpham_fk " +
			"\n 			inner join DONVIDOLUONG dv on dv.PK_SEQ = spx.DVDL_FK " +
			"\n 			where xxx.khId = duyet.khId and xxx.duyetkm_fk = duyet.duyetkm_fk " +
			"\n 	 	FOR XML PATH(''), TYPE).value('.', 'NVARCHAR(MAX)'), 1, 2, '')  " +											
			"\n		else   format(duyet.thuong,'N','en-US')  end [Thưởng] " +
    		"\n from DUYETTRAKHUYENMAI_KHACHHANG duyet " +
    		"\n inner join khachhang kh on kh.PK_SEQ = duyet.khId " +
    		"\n outer apply " +
    		"\n ( " +
    		"\n 	select top 1 ddkd.* " +
    		"\n 	from daidienkinhdoanh ddkd " +
    		"\n 	inner join tuyenbanhang t on t.ddkd_fk = ddkd.pk_seq and t.npp_fk = ddkd.npp_fk" +
    		"\n 	inner join khachhang_tuyenbh tx on tx.tbh_fk = t.pk_seq " +
    		"\n 	where tx.khachhang_fk = kh.pk_seq and kh.npp_fk = t.npp_fk " +
    		"\n )ddkd " +
    		"\n inner join DUYETTRAKHUYENMAI d on d.PK_SEQ = duyet.duyetkm_fk " +
    		"\n inner join TIEUCHITHUONGTL tl on tl.PK_SEQ = d.CTKM_FK " +
    		"\n outer apply " +
    		"\n ( " +
    		"\n 	select top 1 dh.pk_seq, dh.ngaynhap " +
    		"\n 	from DONHANG dh " +
    		"\n 	inner join donhang_ctkm_trakm dhkm on dh.PK_SEQ = dhkm.DONHANGID " +
    		"\n 	where not exists ( select 1 from DONHANGTRAVE x where x.trangthai = 3 and x.donhang_fk = dh.pk_seq) " +
    		"\n 		and dhkm.duyetkm_fk = duyet.duyetkm_fk and dh.KHACHHANG_FK = duyet.khId	and dh.TRANGTHAI !=2 " +
    		"\n )dh " +
    		"\n left join NHAPHANPHOI npp on npp.pk_seq = kh.npp_fk " +
    		"\n left join khuvuc kv on kv.pk_seq = npp.khuvuc_fk " +
    		"\n left join vung v on v.pk_seq = kv.VUNG_FK " +
    		"\n where duyet.mucduyet >=0 and duyet.trangthai = 1 AND D.TRANGTHAI = 1 ";
    	
    	if(obj.gettungay()!= null && obj.gettungay().trim().length() > 0)
			query +=" and tl.ngayds_tungay >='"+obj.gettungay()+"'";
			
		if(obj.getdenngay() != null && obj.getdenngay() .trim().length() > 0)
			query +=" and tl.ngayds_tungay <='"+obj.getdenngay() +"'";		
    	
    	if(obj.getcttbId().length() > 0)
    	{
    		query +=" and tl.pk_seq in  (  "+ obj.getcttbId() + ") ";
    		
    	}
    	if(obj.getnppId().length() > 0)
    	{
    		query +=" and kh.npp_fk =  "+ obj.getnppId();
    		
    	}
    	if(obj.getkhuvucId().length() > 0)
    	{
    		query +=" and npp.khuvuc_fk =  "+ obj.getkhuvucId();
    	}
    	if(obj.getvungId().length() > 0)
    	{
    		query +=" and npp.khuvuc_fk in (select pk_seq from khuvuc where vung_fk = "+obj.getvungId()+" ) " ; 
    	}
    	if(obj.getPhanloai().equals("2"))
    	{
    		query +=" and ddkd.pk_seq in ("+Utility.get_DDKD_from_User (obj.getuserId(),obj.getLoaiNv()) +") ";
    	}

    	query += "\n and kh.kbh_fk in ("+Utility.PhanQuyenKBH(obj.getuserId())+") ";
    	
    	System.out.println("query = " + query);
    	return query;
    	
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    HttpSession session = request.getSession();	
	    
	    Utility util = new Utility();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    IStockintransit obj = new Stockintransit();
	    obj.setuserId(userId);
	    
	    obj.init();
		session.setAttribute("obj", obj);
	    
	    String nextJSP = "/AHF/pages/Center/TieuChiThuongTLReport.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    HttpSession session = request.getSession();	
	    
	    Utility util = new Utility();
	    
	    String userId = util.antiSQLInspection(request.getParameter("userId"));     
	    IStockintransit obj = new Stockintransit();
	    obj.setuserId(userId);
	    obj.init_user();
	    
	    String userName = (String) session.getAttribute("userTen");  
	    if(userName == null)
	    	userName = "";
	    
	  
	    
	    String tungay = request.getParameter("Sdays");
	    if(tungay == null)
	    	tungay = "";
	    obj.settungay(tungay);
	    
	    String denngay = request.getParameter("Edays");
	    if(denngay == null)
	    	denngay = "";
	    obj.setdenngay(denngay);
	    
	  
	    
	    String vungId = request.getParameter("vungId");
	    if(vungId == null)
	    	vungId = "";
	    obj.setvungId(vungId);
	    
	    String khuvucId = request.getParameter("khuvucId");
	    if(khuvucId == null)
	    	khuvucId = "";
	    obj.setkhuvucId(khuvucId);
	    
	    String nppId = request.getParameter("nppId");
	    if(nppId == null)
	    	nppId = "";
	    obj.setnppId(nppId);
	    
	    String [] schemeId = request.getParameterValues("schemeId");
	    obj.setMultiCttbId(schemeId);
	    
		String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	    
	    if(action.equals("taobc"))
	    {
    		
	    	try 
			{
				request.setCharacterEncoding("utf-8");
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=BCTichLuy.xls");
				OutputStream out1 = response.getOutputStream();
				String query = getQuery(obj);
				ExportToExcel( out1, obj, query );
				obj.getDb().shutDown();
			} 
			catch (Exception ex) 
			{
				ex.printStackTrace();
				System.out.println("Error Here : "+ex.toString());
				request.getSession().setAttribute("errors", ex.getMessage());
			}
	    	return;
	    }
	    
	   
		

    	session.setAttribute("obj", obj);  	
		session.setAttribute("userId", userId);
	
		response.sendRedirect("/AHF/pages/Center/TieuChiThuongTLReport.jsp");
	   
	}

	private void ExportToExcel(OutputStream out,IStockintransit obj,String query )throws Exception
	{
		try{ 					
			
			FileInputStream fstream = null;
			com.aspose.cells.Workbook workbook = new com.aspose.cells.Workbook();
			workbook.setFileFormatType(FileFormatType.EXCEL2003);
			TaoBaoCao(workbook, obj, query);			
			workbook.save(out);					

		}catch(Exception ex){
			ex.printStackTrace();
			throw new Exception(ex.getMessage());
		}
	}
	
	private void TaoBaoCao(com.aspose.cells.Workbook workbook,IStockintransit obj,String query)throws Exception
	{
		
		try{		

			com.aspose.cells.Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			com.aspose.cells.Cells cells = worksheet.getCells();
			com.aspose.cells.Cell cell = cells.getCell("A1");;	
		   
			cells.setRowHeight(0, 20.0f);
			ReportAPI.getCellStyle(cell, Color.RED, true, 16, "Báo Cáo tích lũy ");
			cell = cells.getCell("A2");
			ReportAPI.getCellStyle(cell, Color.NAVY, true, 10, "Ngày tạo : " + Utility.getNgayHienTai() );
	
			
			ResultSet rs = obj.getDb().get(query);
			ResultSetMetaData rsmd = rs.getMetaData();
			int socottrongSql = rsmd.getColumnCount();
			
			int location  = 0;
			int row = 10;
			for( int i =1 ; i <=socottrongSql ; i ++  )
			{
				cell = cells.getCell(row, location + i-1 );
				cell.setValue(rsmd.getColumnName(i).replace("_isNum","").replace("_isNum2","")  );
				ReportAPI.setCellBackground(cell, new Color(219,229,241), BorderLineType.THIN, true, 0);
			}
			
			
			row ++;
			while(rs.next())
			{
				for(int i =1;i <=socottrongSql ; i ++)
				{					
					cell = cells.getCell(row,location + i-1 );
					
					if(rsmd.getColumnName(i).contains("_isNum") )
					{
						int format = 37;
							if(rsmd.getColumnName(i).contains("_isNum2"))	
								format = 10;
						cell.setValue(rs.getDouble(i));
						ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, format);
					}
					else
					{
						cell.setValue(rs.getString(i));
						ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					}
				}
				
				++row;
			}
			
			
			
			if(rs!=null)rs.close();		
			
		}catch(Exception ex){

			ex.printStackTrace();
			throw new Exception("Lỗi ! Không có dữ liệu để xuất file !");
		}	
	}

	
}
