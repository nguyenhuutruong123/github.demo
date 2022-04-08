/**
 * Author : KHOAND 
 * class name : MuaHangNhaPPKhacSvl
 * Date : 2011-10-20
 */

package geso.dms.distributor.servlets.donhangnpp;
import geso.dms.distributor.beans.donhangnhapp.IDonhangnpp;
import geso.dms.distributor.beans.donhangnhapp.imp.DonHangNPP;
import geso.dms.distributor.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DonhangnppSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;


	public DonhangnppSvl() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/AHF/index.jsp");
		}else
		{
			IDonhangnpp obj;
			PrintWriter out; 
			Utility util = new Utility();
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			out  = response.getWriter();
			String querystring = request.getQueryString();
			userId = util.getUserId(querystring);


			if (userId.length()==0)
				userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));

			String action = util.getAction(querystring);
			session.setAttribute("userId",userId );

			String dhId = util.getId(querystring);
			String nextJSP="";
			if(action.equals("display") )
			{
				obj=new DonHangNPP(dhId);
				obj.setUserId(userId);
				obj.createRs();
				session.setAttribute("obj", obj);
				nextJSP = "/AHF/pages/Distributor/DonHangNhaPPDisplay.jsp";
				response.sendRedirect(nextJSP);
			}
			else if(action.equals("update"))
			{
				obj=new DonHangNPP(dhId);
				obj.setUserId(userId);
				obj.setNppId_Ban(util.getIdNhapp(userId));
				obj.setTenNPPBan(util.getTenNhaPP());
				obj.createRs();
				session.setAttribute("obj", obj);			
				nextJSP = "/AHF/pages/Distributor/DonHangNPPUpdate.jsp";
				response.sendRedirect(nextJSP);
			}	
			else if(action.equals("delete"))
			{ 
				obj=new DonHangNPP(dhId);
				obj.setId(dhId);
				obj.setUserId(userId);
				obj.setNguoisua(userId);
				obj.setNgaysua(getDateTime());
				obj.DeleteDonHangNPP() ;
				obj.setUserId(userId);
				obj.setNppId_Ban(util.getIdNhapp(userId));
				obj.setTenNPPBan(util.getTenNhaPP());
				obj.SetDonHangNPP("");
				session.setAttribute("obj", obj);			
				nextJSP = "/AHF/pages/Distributor/DonHangNhaPP.jsp";
				response.sendRedirect(nextJSP);
			}else if(action.equals("chot"))
			{
				obj=new DonHangNPP(dhId);
				obj.setId(dhId);
				obj.setUserId(userId);
				obj.setNguoisua(userId);
				obj.setNgaysua(getDateTime());
				String msg=obj.ChotDh();
				
				System.out.println("[KetQuaLa]"+  msg);
				
				obj.setUserId(userId);
				obj.setNppId_Ban(util.getIdNhapp(userId));
				obj.setTenNPPBan(util.getTenNhaPP());
				obj.SetDonHangNPP("");
				session.setAttribute("obj", obj);			
				nextJSP = "/AHF/pages/Distributor/DonHangNhaPP.jsp";
				response.sendRedirect(nextJSP);
			}
			else
			{
				obj=new DonHangNPP();
				obj.setUserId(userId);
				obj.setNppId_Ban(util.getIdNhapp(userId));
				obj.setTenNPPBan(util.getTenNhaPP());
				obj.createRs();
				obj.SetDonHangNPP("");
				session.setAttribute("obj", obj);			
				nextJSP = "/AHF/pages/Distributor/DonHangNhaPP.jsp";
				response.sendRedirect(nextJSP);
			}
		}
	}

	private String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.dms.center.util.Utility cutil = (geso.dms.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/AHF/index.jsp");
		}else{
			IDonhangnpp objj=new DonHangNPP();
			PrintWriter out; 

			Utility util = new Utility();
			userId=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
			String action=geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action1"));

			userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
			//tu ten dang nhap,lay ra nha phan phoi
			String trangthai=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai")));
			objj.setUserId(userId);
			String manhpp_ban=util.getIdNhapp(userId);
			objj.setNppId_Ban(manhpp_ban);

			String nextjsp;

			if(action.equals("new"))//TRUONG HOP THEM MOI
			{
				nextjsp="/AHF/pages/Distributor/DonHangNhaPPNew.jsp";
				objj.createRs();
				session.setAttribute("obj", objj);
				session.setAttribute("userId", userId);
				response.sendRedirect(nextjsp);
			}
			else //Trong truong hop tim kiem 
			{
				String trangthai1 = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai")));
				String npptimkiem = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nhappmuatk")));
				String tungay=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay")));
				String denngay=util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay")));
				String sql1= "SELECT  a.NppDaChot,   A.PK_SEQ, M.TEN, T.TEN AS NGUOITAO, S.TEN AS NGUOISUA, A.NGAYNHAP, A.TRANGTHAI, A.NGAYTAO, A.NGAYSUA, A.VAT, A.TONGGIATRI,"+ 
						"A.DATHANHTOAN, A.NPP_FK_MUA, A.NPP_FK_BAN, A.KHO_FK, A.KBH_FK "+
						"FROM         dbo.DONHANG_NPP AS A INNER JOIN "+
						"dbo.NHAPHANPHOI AS M ON A.NPP_FK_MUA = M.PK_SEQ INNER JOIN "+
						"dbo.NHANVIEN AS T ON T.PK_SEQ = A.NGUOITAO INNER JOIN " +
						"dbo.NHANVIEN AS S ON S.PK_SEQ = A.NGUOISUA "+
						"WHERE     (A.NPP_FK_BAN = "+ manhpp_ban +")" ;
				if(trangthai!="")
				{
					sql1=sql1+ " and a.trangthai = "+ trangthai1;
				}

				session.setAttribute("tungay", "");
				session.setAttribute("denngay","");//Truyen qua mac dinh 2 gia tri trong
				if(tungay!="") {
					sql1=sql1+ " and A.NGAYNHAP >= '"+tungay+"'"; 
					session.setAttribute("tungay", tungay);//truyen qua tu ngay co gia tri
				}
				if(denngay!="")
				{
					sql1=sql1+ " and A.NGAYNHAP <= '"+denngay+"'"; 
					session.setAttribute("denngay", denngay);//truyen qua den ngay co gia tri
				}
				if(npptimkiem!="" && npptimkiem!=null)
				{
					sql1=sql1+ " and A.NPP_FK_MUA=" + npptimkiem;
				}
				objj.SetDonHangNPP(sql1);
				objj.setTrangthai(trangthai1);
				objj.createRs();
				session.setAttribute("obj", objj);
				String nextJSP = "/AHF/pages/Distributor/DonHangNhaPP.jsp";
				session.setAttribute("idnpp", npptimkiem);
				session.setAttribute("userId", userId);
				response.sendRedirect(nextJSP);
			}
		}
	}
}
