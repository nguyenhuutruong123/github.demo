package geso.dms.center.servlets.phanbotrungbay;

import geso.dms.center.beans.phanbotrungbay.IPhanbotrungbay;
import geso.dms.center.beans.phanbotrungbay.imp.Phanbotrungbay;

import java.io.*;
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

import com.oreilly.servlet.MultipartRequest;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import geso.dms.center.db.sql.*;
import geso.dms.center.util.Utility;

import java.sql.ResultSet;

public class PhanbotrungbaySvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private static final String UPLOAD_DIRECTORY = "c:\\upload\\excel\\";

	public PhanbotrungbaySvl()
	{
		super();
	}

	private String schemeId;
	private Utility util = new Utility();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IPhanbotrungbay pbtbBean = new Phanbotrungbay();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);

		pbtbBean.init();
		session.setAttribute("pbtb", pbtbBean);
		session.setAttribute("schemeId", "0");
		session.setAttribute("userId", userId);
		String nextJSP = "/AHF/pages/Center/PhanBoTrungBay.jsp";
		response.sendRedirect(nextJSP);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		PrintWriter out = response.getWriter();
		String contentType = request.getContentType();
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		IPhanbotrungbay pbtbBean = new Phanbotrungbay();
		String action=geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		if(action==null)action="";
		if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0))
		{
			//MultipartRequest multi = new MultipartRequest(request, "C:\\java-tomcat\\data\\", 20000000, "UTF-8");
			MultipartRequest multi = new MultipartRequest(request, getServletContext().getRealPath("\\upload\\"), 20000000, "UTF-8"); 
			//String filename = SaveExcel(request);
			Enumeration files = multi.getFileNames(); 
		  	String filenameu  ="";
		  	while (files.hasMoreElements())
            {
                 String name = (String)files.nextElement();
                 filenameu = multi.getFilesystemName(name); 
                 System.out.println("name :   "+name);;
            }
		  	boolean err = false;
		  //String filename = "C:\\java-tomcat\\data\\" + filenameu;
			String filename=    getServletContext().getRealPath("\\upload\\")+ "\\"+ filenameu;
			if(filenameu == null)
				pbtbBean.setMessage("Không có file nào được upload");
			String schemeId = multi.getParameter("schemeId");
			this.schemeId = schemeId;
					
			pbtbBean.setSchemeId(schemeId);
			if (filename.length() > 0)
			{


				//doc file excel
				File file = new File(filename);
				System.out.println("filename  "+file);
				Workbook workbook;
				int indexRow=4;
		
				try 
				{						
					System.out.println(file);
					workbook = Workbook.getWorkbook(file);
					
					Sheet[] sheet1 = workbook.getSheets();
					Sheet sheet=sheet1[0];					 
					Cell[] cells ;
					String npp_fks = "";
					Hashtable<String, Integer> npp_st= new Hashtable<String, Integer>();
					Hashtable<String, Integer> npp_ngansach= new Hashtable<String, Integer>();
					int sonpp = 0;
					String npptrung = "";
					//System.out.println("getRows = " +sheet.getRows());
					for(int i= indexRow; i < sheet.getRows();i++)
					{	
						cells = sheet.getRow(i);
						//System.out.println("cell length: " +cells.length);
						if (cells.length >= 3){
							String stt = cells[0].getContents().toString();
							String npp_FK = cells[1].getContents().toString();
							String nsach = cells[2].getContents().toString();
							npp_fks += "'" + npp_FK.trim() + "',";
							if(nsach.trim().length() == 0)
								nsach = "0";
							if(npp_ngansach.containsKey(npp_FK.trim())){
								npptrung += npp_FK + ",\n";
							}
							else
								sonpp ++;
							
							npp_st.put(npp_FK.trim(), i - 3);
							npp_ngansach.put(npp_FK.trim(), Integer.parseInt(nsach));
							
							//System.out.println("__ " + KHACHHANG_FK + ":" + muc_dk);
						}							
					}
					if(sonpp == 0)
						pbtbBean.setMessage("Không có nhà phân phối nào được chọn!");
					
					if(npp_fks.length() > 0)
						npp_fks = npp_fks.substring(0, npp_fks.length() - 1);

					pbtbBean.getId_npp(npp_fks, npp_ngansach, npp_st);
					if(npptrung.length() > 0)
						pbtbBean.setMessage(pbtbBean.getMessage() + "\nSố nhà phân phối trùng:\n" + npptrung);
					//obj.setAction("upload");
					
				}catch(Exception er){
					er.printStackTrace();
					System.out.println("Exception. "+er.getMessage());
					pbtbBean.setMessage("Lỗi trong quá trình upload: " + er.getMessage());
				}
				String schemeId1 = (String) session.getAttribute("schemeId");
				pbtbBean.setSchemeId(schemeId1);
				pbtbBean.init();
				pbtbBean.setusedPro(this.getUsedPromotion(schemeId1));
				session.setAttribute("pbtb", pbtbBean);
				session.setAttribute("schemeId", schemeId1);
		    	response.sendRedirect("/AHF/pages/Center/PhanBoTrungBay.jsp");
				
			}
			else
			{
				pbtbBean.init();
				session.setAttribute("pbtb", pbtbBean);
				session.setAttribute("schemeId", "0");
				session.setAttribute("userId", userId);
				String nextJSP = "/AHF/pages/Center/PhanBoTrungBay.jsp";
				response.sendRedirect(nextJSP);
			}
		}
		
		else if(action.equals("save"))
		{/*

			String schemeId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("schemeId"));
			pbtbBean.setSchemeId(schemeId);
			String vungId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId"));
			pbtbBean.setVungId(vungId);

			String kvId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kvId"));
			pbtbBean.setKvId(kvId);
						
			pbtbBean.save(request);
			
			pbtbBean.init();
			pbtbBean.setusedPro(this.getUsedPromotion(schemeId));
			session.setAttribute("pbtb", pbtbBean);
			session.setAttribute("schemeId", schemeId);
			session.setAttribute("userId", userId);
			String nextJSP = "/AHF/pages/Center/PhanBoTrungBay.jsp";
			response.sendRedirect(nextJSP);
		*/
		}
		else 
		{
			String schemeId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("schemeId"));
			pbtbBean.setSchemeId(schemeId);
			String vungId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId"));
			pbtbBean.setVungId(vungId);

			String kvId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kvId"));
			pbtbBean.setKvId(kvId);

			pbtbBean.init();
			pbtbBean.setusedPro(this.getUsedPromotion(schemeId));
			session.setAttribute("pbtb", pbtbBean);
			session.setAttribute("schemeId", schemeId);
			session.setAttribute("userId", userId);
			String nextJSP = "/AHF/pages/Center/PhanBoTrungBay.jsp";
			response.sendRedirect(nextJSP);
		}
	}

	private String SaveExcel(HttpServletRequest request) throws ServletException, IOException
	{
		String contentType = request.getContentType();
		DataInputStream in = new DataInputStream(request.getInputStream());
		// we are taking the length of Content type data
		int formDataLength = request.getContentLength();
		byte dataBytes[] = new byte[formDataLength];
		int byteRead = 0;
		int totalBytesRead = 0;
		// this loop converting the uploaded file into byte code
		while (totalBytesRead < formDataLength)
		{
			byteRead = in.read(dataBytes, totalBytesRead, formDataLength);
			totalBytesRead += byteRead;
		}

		String file = new String(dataBytes);

		// for saving the file name
		String Id = file.substring(file.indexOf("schemeId"));
		Id = Id.substring(Id.indexOf("\n"));
		Id = Id.substring(0, 10);
		this.schemeId = Id.trim();

		String saveFile = file.substring(file.indexOf("filename=\"") + 10);
		saveFile = saveFile.substring(0, saveFile.indexOf("\n"));
		saveFile = saveFile.substring(saveFile.lastIndexOf("\\") + 1, saveFile.indexOf("\""));
		int lastIndex = contentType.lastIndexOf("=");
		String boundary = contentType.substring(lastIndex + 1, contentType.length());
		int pos;
		pos = file.indexOf("filename=\"");
		pos = file.indexOf("\n", pos) + 1;
		pos = file.indexOf("\n", pos) + 1;
		pos = file.indexOf("\n", pos) + 1;
		int boundaryLocation = file.indexOf(boundary, pos) - 4;
		int startPos = ((file.substring(0, pos)).getBytes()).length;
		int endPos = ((file.substring(0, boundaryLocation)).getBytes()).length;

		if (saveFile.length() > 0)
		{
			// creating a new file with the same name and writing the content in
			// new file
			FileOutputStream fileOut = new FileOutputStream(UPLOAD_DIRECTORY + "-" + saveFile);
			fileOut.write(dataBytes, startPos, (endPos - startPos));
			fileOut.flush();
			fileOut.close();
			return (UPLOAD_DIRECTORY + "-" + saveFile);
		} else
		{
			return "";
		}
	}

	private String readExcel(String filename) throws ServletException, IOException
	{
		File inputWorkbook = new File(filename);
		Workbook w;
		Hashtable ht = this.getNPP();
		Hashtable usedPro = this.getUsedPromotion(this.schemeId);
		dbutils db = new dbutils();
		try
		{
			w = Workbook.getWorkbook(inputWorkbook);
			// Get the first sheet
			Sheet sheet = w.getSheet(0);

			for (int i = 3; i < sheet.getRows(); i++)
			{
				Cell cell0 = sheet.getCell(0, i);
				Cell cell1 = sheet.getCell(1, i);
				Cell cell2 = sheet.getCell(2, i);
				Cell cell3 = sheet.getCell(3, i);
				if ((cell0.getContents().length() == 0 || cell0.getContents().contains("AREA")) & !cell0.getContents().contains("Total") & !cell1.getContents().contains("Ma NPP")
						& !cell0.getContents().contains("Grand"))
				{
					String nppId = (String) ht.get(cell1.getContents().trim());
					
					if (nppId == null)
					{
						System.out.println(cell1.getContents()+"[nppId]" + nppId);
						return "Mã nhà phân phối " + cell1.getContents() +  " không có trong hệ thống hoặc đã ngưng hoạt động !";
					}
					String sql = "";
					if (nppId != null)
					{
						if (usedPro.containsKey(nppId))
						{
							if (Float.valueOf(cell3.getContents()).floatValue() > Float.valueOf((String) usedPro.get(nppId)).floatValue())
							{
								sql = "INSERT INTO PHANBOTRUNGBAY(CTTB_FK,NPP_FK,NGANSACH,dasudung) values('" + this.schemeId + "','" + nppId + "','" + cell3.getContents().replace(",", "") + "', 0)";
								if (!db.update(sql))
								{
									sql = "update PHANBOTRUNGBAY set ngansach='" + cell3.getContents().replace(",", "") + "' where CTTB_FK='" + this.schemeId + "' and npp_fk='" + (String) ht.get(cell1.getContents())+ "'";
									db.update(sql);
								}
							} else
							{
								return nppId;
							}
						} else
						{
							sql = "insert into PHANBOTRUNGBAY(CTTB_FK,NPP_FK,NGANSACH,dasudung) values('" + this.schemeId + "','" + nppId + "','" + cell3.getContents().replace(",", "") + "', 0)";
							if (!db.update(sql))
							{
								sql = "update PHANBOTRUNGBAY set ngansach='" + cell3.getContents().replace(",", "") + "' where CTTB_FK='" + this.schemeId + "' and npp_fk='" + (String) ht.get(cell1.getContents())+ "'";
								db.update(sql);
							}
						}
					}
				}
			}
		} catch (BiffException e)
		{
			e.printStackTrace();
		}
		return "0";
	}

	private Hashtable<String, String> getNPP()
	{
		Hashtable<String, String> ht = new Hashtable<String, String>();
		dbutils db = new dbutils();
		if (db.getConnection() != null)
		{
			ResultSet rs = db.get("select pk_seq, ma from nhaphanphoi where trangthai='1'");
			if (rs != null)
			{
				try
				{
					while (rs.next())
					{
						if (rs.getString("ma") != null)
						{
							ht.put(rs.getString("ma"), rs.getString("pk_seq"));
						}
					}
				} catch (Exception e)
				{
				}
			} else
			{
				System.out.print("Error here!");
			}
			db.shutDown();
		} else
		{
			System.out.print("Error here!");
		}
		return ht;
	}

	private Hashtable<String, String> getUsedPromotion(String schemeId)
	{
		Hashtable<String, String> ht = new Hashtable<String, String>();
		dbutils db = new dbutils();
		if (db.getConnection() != null)
		{
			String sql=
					" SELECT DN.NPP_FK AS NPPID, SUM(XUATDENGHI) AS AMOUNT "+  
					" FROM DENGHITRATRUNGBAY DN  "+
					"	INNER JOIN CTTRUNGBAY TB ON TB.PK_SEQ=DN.CTTRUNGBAY_FK  "+
					"	INNER JOIN DENGHITRATB_KHACHHANG TRA ON DN.PK_SEQ=TRA.DENGHITRATB_FK "+
					" WHERE DN.TRANGTHAI = 1  AND TB.PK_SEQ='"+schemeId+"' "+ 
					"  GROUP BY NPP_FK ";
			ResultSet rs = db.get(sql);
			if (rs != null)
			{
				try
				{
					while (rs.next())
					{
						if (rs.getString("nppId") != null)
						{
							ht.put(rs.getString("nppId"), rs.getString("amount"));
						}
					}

				} catch (Exception e)
				{
				}
			} else
			{
				System.out.print("Error in getUsedPromotion/rs==null");
			}
			db.shutDown();
		} else
		{
			System.out.print("Error in getUsedPromotion/rs==null");
		}

		return ht;

	}

	public String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
}