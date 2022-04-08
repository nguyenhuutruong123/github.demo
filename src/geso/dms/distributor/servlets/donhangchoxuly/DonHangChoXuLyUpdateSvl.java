package geso.dms.distributor.servlets.donhangchoxuly;

import geso.dms.distributor.beans.ctkhuyenmai.ICtkhuyenmai;
import geso.dms.distributor.beans.ctkhuyenmai.imp.XLkhuyenmai;
import geso.dms.distributor.beans.ctkhuyenmai.imp.XLkhuyenmaiDonhangDXK;
import geso.dms.distributor.beans.donhangchoxuly.IDonHangChoXuLy;
import geso.dms.distributor.beans.donhangchoxuly.IDonHangChoXuLyList;
import geso.dms.distributor.beans.donhangchoxuly.ISanpham;
import geso.dms.distributor.beans.donhangchoxuly.imp.DonHangChoXuLy;
import geso.dms.distributor.beans.donhangchoxuly.imp.DonHangChoXuLyList;
import geso.dms.distributor.beans.donhangchoxuly.imp.Sanpham;
import geso.dms.distributor.beans.donhangchoxuly.imp.XLTrungbay;
import geso.dms.distributor.beans.trakhuyenmai.ITrakhuyenmai;
import geso.dms.distributor.db.sql.dbutils;
import geso.dms.distributor.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/DonHangChoXuLyUpdateSvl")
public class DonHangChoXuLyUpdateSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	PrintWriter out;
   
    public DonHangChoXuLyUpdateSvl() {
        super();
       
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
		}
		else{
			session.setMaxInactiveInterval(30000);

			this.out = response.getWriter();
			Utility util = new Utility();

			String querystring = request.getQueryString();
			userId = util.getUserId(querystring);

			out.println(userId);

			if (userId.length()==0)
				userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));

			String id = util.getId(querystring);  	
			IDonHangChoXuLy dhBean = new DonHangChoXuLy(id);
			dhBean.setUserId(userId); //phai co UserId truoc khi Init
			dhBean.init();

			dhBean.setKhId(dhBean.getKhId());
			session.setAttribute("bgstId", dhBean.getBgstId());
			session.setAttribute("khoId", dhBean.getKhoId());
			String nextJSP;

			if(request.getQueryString().indexOf("display") >= 0 ) 
			{
				//dhBean.CreateSpDisplay();
				nextJSP = "/AHF/pages/Distributor/DonHangChoXuLyDisplay.jsp";
			}
			else
			{
				nextJSP = "/AHF/pages/Distributor/DonHangChoXuLyUpdate.jsp";
			}

			session.setAttribute("dhBean", dhBean);
			session.setAttribute("khId", dhBean.getKhId());
			session.setAttribute("ddkdId", dhBean.getDdkdId());
			session.setAttribute("ngaydonhang", dhBean.getNgaygiaodich() );
			//bo sung them de do phai truy xuat csdl khi nhan sanpham
			session.setAttribute("nppId", dhBean.getNppId());

			response.sendRedirect(nextJSP);
		}
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
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");

			session.setMaxInactiveInterval(30000);

			this.out = response.getWriter();
			dbutils db = new dbutils();
			IDonHangChoXuLy dhBean;
			Utility util = new Utility();
			String id = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));
			if(id == null)
			{  	
				dhBean = new DonHangChoXuLy("");
			}
			else
			{
				dhBean = new DonHangChoXuLy(id);
			}

			userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
			dhBean.setUserId(userId);

			String nppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));
			if (nppId == null)
				nppId = "";
			dhBean.setNppId(nppId);

			String gsbhId =  util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("gsbhId")));
			if(gsbhId == null)
				gsbhId ="0";
			dhBean.setGsbhId(gsbhId);

			String ngaygd = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay")));
			if (ngaygd == null || ngaygd == "")
				ngaygd = this.getDateTime();				
			dhBean.setNgaygiaodich(ngaygd);

			session.setAttribute("ngaydonhang", ngaygd );

			String trangthai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai")));
			if (trangthai == null)
				trangthai = "";				
			dhBean.setTrangthai(trangthai);

			String diachigiaohang = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diachigiao")));
			if (diachigiaohang == null)
				diachigiaohang = "";				
			dhBean.setDiachigiaohang(diachigiaohang);

			String ddkdId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ddkdTen")));
			if (ddkdId == null)
				ddkdId = "";    	
			dhBean.setDdkdId(ddkdId);
			session.setAttribute("ddkdId", ddkdId );

			String khoId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khoTen")));
			if (khoId == null)
				khoId = "";    	
			dhBean.setKhoId(khoId);

			String khId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khId")));
			if (khId == null)
				khId = "";    	
			//System.out.println("Khach hang :"+khId);

			dhBean.setKhId(khId);
			session.setAttribute("khId",khId);
			//System.out.println("DonHangChoXuLyUpdateSvl user :" + userId + " ,khachhangId :"+ khId +"  ,sessionId: " + session.getId() );

			String smartId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("smartId")));
			if (smartId == null)
				smartId = "";    	
			dhBean.setSmartId(smartId);

			String khTen = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khTen")));
			if (khTen == null)
				khTen = "";    	
			dhBean.setKhTen(khTen);
			/*	System.out.println("11111111111111111.KHACH HANG TEN: " + khTen);*/

			String muctindung = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("muctindung")));
			if (muctindung ==null)
				muctindung = "0";    	
			dhBean.setMuctindung(muctindung);    	

			String chietkhau = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ChietKhau")));
			if (chietkhau == null)
				chietkhau = ""; 
			else
				chietkhau = chietkhau.replaceAll(",", "");
			dhBean.setChietkhau(chietkhau);

			String tongtientruocVAT = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("SoTienChuaVAT")));
			if(tongtientruocVAT == null)
				tongtientruocVAT = "0";
			else
				tongtientruocVAT = tongtientruocVAT.replaceAll(",", "");
			dhBean.setTongtientruocVAT(tongtientruocVAT);   //tien chi gom soluong * dongia --> de ap khuyen mai

			String tongtiensauVAT = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("SoTienCoVAT")));
			if(tongtiensauVAT == null)
				tongtiensauVAT = "0";
			else
				tongtiensauVAT = tongtiensauVAT.replaceAll(",", "");
			dhBean.setTongtiensauVAT(tongtiensauVAT);   

			String tongtienDonhang = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("SoTienSauCKKM")));
			if(tongtienDonhang == null)
				tongtienDonhang = tongtiensauVAT;
			else
				tongtienDonhang = tongtienDonhang.replaceAll(",", "");
			dhBean.setTongtiensauCKKM(Float.parseFloat(tongtienDonhang));
			/*System.out.println("Tong gia tri don hang ben SVL: " + tongtienDonhang + "\n");*/

			String TienChietKhau = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tck")));
			if(TienChietKhau == null)
				TienChietKhau = "0";
			else
				TienChietKhau = TienChietKhau.replaceAll(",", "");
			dhBean.setTongChietKhau(TienChietKhau);  

			String VAT = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("VAT")));
			if (VAT == null)
				VAT = "0";  //TTC, don gia da co VAT
			else
				VAT = VAT.replaceAll(",", "");
			dhBean.setVAT(VAT);

			String bgstId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("bgstId")));
			if (bgstId == null)
				bgstId = "0";   //neu khach hang khong co bang gia sieu thi 
			/*System.out.println("bang gia sieu thi:" + bgstId );*/
			dhBean.setBgstId(bgstId);
			session.setAttribute("bgstId", bgstId);

			String ngayks = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngaykhoaso")));
			if(ngayks == null)
				ngayks = getDateTime();
			dhBean.setNgayKs(ngayks);

			String ngaysua = getDateTime();
			dhBean.setNgaysua(ngaysua);

			String[] masp = request.getParameterValues("masp");
			String[] tensp = request.getParameterValues("tensp");
			String[] soluong = request.getParameterValues("soluong");
			String[] donvitinh = request.getParameterValues("donvitinh");
			String[] dongia = request.getParameterValues("dongia");
			String[] spchietkhau = request.getParameterValues("spchietkhau");
			String[] spchietkhauDLN = request.getParameterValues("spchietkhauDLN");
			String[] spchietkhauTT = request.getParameterValues("spchietkhauTT");
			String[] spchietkhauDH = request.getParameterValues("spchietkhauDH");
			String[] tonkho = request.getParameterValues("tonkho");

			String[] spGiagoc = request.getParameterValues("spGiagoc");
			String[] spBgId = request.getParameterValues("spBgId");

			long tonggiatriKm = Math.round(Float.parseFloat(tongtientruocVAT));

			List<ISanpham>	spList = new ArrayList<ISanpham>();
			if(masp != null)
			{		
				ISanpham sanpham = null;
				String[] param = new String[11];
				int m = 0;
				while(m < masp.length)
				{
					if(masp[m] != "")
					{
						if(soluong[m].length() >  0) //chi them nhung san pham co so luong > 0
						{
							param[0] = "idSP";
							param[1] = masp[m];
							param[2] = tensp[m];
							param[3] = soluong[m].replaceAll(",", "");
							param[4] = donvitinh[m];
							param[5] = dongia[m].replaceAll(",", "");
							param[6] = ""; //khuyen mai
							param[7] = spchietkhau[m].replaceAll(",", "");
							param[8] = spchietkhauDLN[m].replaceAll(",", "");
							param[9] = spchietkhauTT[m].replaceAll(",", "");
							param[10] = spchietkhauDH[m].replaceAll(",", "");

							sanpham = new Sanpham(param);
							sanpham.setTonhientai(tonkho[m].replaceAll(",", ""));
							sanpham.setGiagoc(spGiagoc[m].replaceAll(",", ""));
							sanpham.setBgId(spBgId[m]);
							spList.add(sanpham);
						}
					}
					m++;
				}	
			}

			if(!checkNgayKhoaSo(ngayks, ngaygd))
			{
				if(id == null)
				{
					dhBean.createRS();
					dhBean.setNgaygiaodich(ngaygd);
					dhBean.setSpList(spList);
					dhBean.setMessage("Bạn phải nhập ngày đơn hàng lớn hơn ngày khóa sổ gần nhất...");
					session.setAttribute("dhBean", dhBean);
					String nextJSP = "/AHF/pages/Distributor/DonHangChoXuLyNew.jsp";
					response.sendRedirect(nextJSP);
					return;
				}else{
					dhBean.setMessage("Bạn phải nhập ngày đơn hàng lớn hơn ngày khóa sổ gần nhất...");
					dhBean.init();
					dhBean.setNgaygiaodich(ngaygd);
					dhBean.setSpList(spList);
					dhBean.setKhId(khId);
					dhBean.setDdkdId(ddkdId);
					session.setAttribute("dhBean", dhBean);
					String nextJSP = "/AHF/pages/Distributor/DonHangChoXuLyUpdate.jsp";
					response.sendRedirect(nextJSP);
					return;
				}

			}

			//Kiem tra nhung san pham khi luu, chot don hang (check ton kho)
			Hashtable<String, Integer> spThieuList = new Hashtable<String, Integer>();
			if(masp != null)
			{
				int m = 0;			
				while(m < masp.length)
				{				
					if(masp[m].length() > 0)
					{
						String sl = "0";
						String sanpham_fk = "";

						String query = "select sanpham_fk, available from nhapp_kho where npp_fk='" + nppId +"' and kho_fk = '" + khoId + "' ";
						query = query + " and sanpham_fk in (select pk_seq from sanpham where ma='" + masp[m].trim() + "') " +
								"and kbh_fk  in (select kbh_fk from khachhang where pk_seq='" + dhBean.getKhId() + "') ";
						//System.out.println("Get San Pham thieu : "+query);
						ResultSet slspAvailable = db.get(query);
						if(slspAvailable != null)
						{
							try
							{
								if(slspAvailable.next())
								{
									sl = slspAvailable.getString("available");
									sanpham_fk = slspAvailable.getString("sanpham_fk");

									slspAvailable.close();

									//lay nhung sp da co trong don hang
									if(id != null)
									{
										String slg = "0";
										query = "select ISNULL(soluong, 0) as soluong from DONHANG_SANPHAM where DONHANG_FK = '" + id + "' and SANPHAM_FK in (select pk_seq from sanpham where ma='" + masp[m].trim() + "')";
										ResultSet SlgRs = db.get(query);
										if(SlgRs != null)
										{
											SlgRs.next();
											if(SlgRs.getString("soluong") != null)
												slg = SlgRs.getString("soluong");
											SlgRs.close();
										}

										sl = Integer.toString( ( Math.round( Float.parseFloat(sl) + Float.parseFloat(slg) ) ) ) ;
										//System.out.print("\n So luong la: "+ sl + "\n");
									}	
								}
								else
								{
									soluong[m] = "0";
								}
							} 
							catch (Exception e) {}

							//System.out.println("So luong avail: " + sl + " -- So luong ban nhap: " + soluong[m] + "\n");

							if(soluong[m].replaceAll(",", "") == "")
								soluong[m] = "0";
							if(Float.parseFloat(soluong[m].replaceAll(",", "")) > Float.parseFloat(sl))
							{
								spThieuList.put(masp[m], Integer.parseInt(sl.replaceAll(",", "")));

								//Chen vao bang thieu hang
								try{
									query = "insert thieuhang_npp(sp_fk, kh_fk, npp_fk, ngayghinhan, soluonghientai, soluongdat) " +
											"values('" + sanpham_fk + "', '" + khId + "', '" + nppId + "', '" + getDateTimeNow() + "', '" + sl + "', '" + soluong[m].replaceAll(",", "") + "')";

									/*System.out.println("____Insert thieu hang: " + query);*/
									db.update(query);
								}catch(Exception e){dhBean.setMessage("Loi dong lenh sau :"+query);}
							}
						}
					}														
					m++;
				}
			}
			dhBean.setSpThieuList(spThieuList);

			String hinhthuc = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("hinhthuc"));
			if(hinhthuc == null)
				hinhthuc = "0";

			String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
			System.out.println("__Action la: " + action);

			if(action.equals("submitKh"))
			{

				String sql = "";
				if(khTen.trim().length() < 0)
				{
					sql = "select top(1) substring(a.smartid, charindex('_', a.smartid)+1, len(a.smartid)) as smartId, a.pk_seq as khId, a.ten as khTen, f.CHIETKHAU as ckId, isnull(a.bgst_fk, '0') as bgstId, c.ddkd_fk, e.gsbh_fk, a.diachi ";
					sql += "from khachhang a inner join khachhang_tuyenbh b on a.pk_seq = b.khachhang_fk inner join tuyenbanhang c on b.tbh_fk = c.pk_seq inner join ddkd_gsbh e on c.ddkd_fk = e.ddkd_fk  left join MUCCHIETKHAU f on a.CHIETKHAU_FK = f.PK_SEQ ";
					sql += "where a.trangthai = '1' and a.npp_fk = '" + nppId + "' and ( a.smartId = '" + smartId + "'  or a.ten like N'%" + smartId + "%') and e.gsbh_fk in (select gsbh_fk from NHAPP_GIAMSATBH where npp_fk ='" + nppId + "' and ngaybatdau <='"+ngaygd+"' and ngayketthuc > ='"+ngaygd+"' ) order by a.smartId asc";
				}
				else
				{
					sql = "select top(1) substring(a.smartid, charindex('_', a.smartid)+1, len(a.smartid)) as smartId, a.pk_seq as khId, a.ten as khTen, f.CHIETKHAU as ckId, isnull(a.bgst_fk, '0') as bgstId, c.ddkd_fk, e.gsbh_fk, a.diachi ";
					sql += "from khachhang a inner join khachhang_tuyenbh b on a.pk_seq = b.khachhang_fk inner join tuyenbanhang c on b.tbh_fk = c.pk_seq inner join ddkd_gsbh e on c.ddkd_fk = e.ddkd_fk  left join MUCCHIETKHAU f on a.CHIETKHAU_FK = f.PK_SEQ ";
					sql += "where a.trangthai = '1' and a.npp_fk = '" + nppId + "' and ( a.ten like N'%" + khTen.split(" - ")[0] + "%' and a.smartId = '" + smartId + "' ) and e.gsbh_fk in (select gsbh_fk from NHAPP_GIAMSATBH where npp_fk ='" + nppId + "' and   ngaybatdau <='"+ngaygd+"' and ngayketthuc > ='"+ngaygd+"')";
				}
				System.out.println("____Lay khach hang: " + sql);

				ResultSet rs = db.get(sql);
				if(rs != null)
				{
					try 
					{
						if(rs.next())
						{	
							dhBean.setSmartId(rs.getString("smartId"));	

							dhBean.setKhId(rs.getString("khId"));
							session.setAttribute("khId", rs.getString("khId"));

							dhBean.setKhTen(rs.getString("khTen") + " - " + rs.getString("diachi"));

							if(rs.getString("ckId") != null)
								dhBean.setChietkhau(rs.getString("ckId"));

							dhBean.setDdkdId(rs.getString("ddkd_fk"));
							session.setAttribute("ddkdId", rs.getString("ddkd_fk"));

							dhBean.setGsbhId(rs.getString("gsbh_fk"));

							String cmd = "select distinct banggiasieuthi_fk from BGST_KHACHHANG where khachhang_fk='" + rs.getString("khId") + "'";
							System.out.println("Bang gia ST: " + cmd + "\n");
							ResultSet bgst = db.get(cmd);
							String bgstIdss = "";
							if(bgst != null)
							{
								while(bgst.next())
								{
									bgstIdss += bgst.getString("banggiasieuthi_fk") + ",";
								}
								bgst.close();
								if(bgstIdss.length() > 0)
									bgstIdss = bgstIdss.substring(0, bgstIdss.length() - 1);
							}

							//dhBean.setBgstId(rs.getString("bgstId"));
							System.out.println("bang gia sieu thi Ids la: " + rs.getString("bgstId") + "\n");
							session.setAttribute("bgstId", bgstIdss);

							if(id == null)
							{
								dhBean.createRS();
								dhBean.setSpList(spList);
								session.setAttribute("dhBean", dhBean);
								String nextJSP = "/AHF/pages/Distributor/DonHangChoXuLyNew.jsp";
								response.sendRedirect(nextJSP);
							}
							else
							{
								dhBean.init();
								dhBean.setSpList(spList);
								dhBean.setSmartId(rs.getString("smartId"));	
								dhBean.setKhId(rs.getString("khId"));
								dhBean.setKhTen(rs.getString("khTen") + " - " + rs.getString("diachi"));
								if(rs.getString("ckId") != null)
									dhBean.setChietkhau(rs.getString("ckId"));

								dhBean.setDdkdId(rs.getString("ddkd_fk"));
								dhBean.setGsbhId(rs.getString("gsbh_fk"));

								session.setAttribute("dhBean", dhBean);
								String nextJSP = "/AHF/pages/Distributor/DonHangChoXuLyUpdate.jsp";
								response.sendRedirect(nextJSP);
							}
						}
						else
						{
							if( id == null)
							{
								dhBean.createRS();
								dhBean.setSpList(spList);
								dhBean.setMessage("Mã khách hàng không đúng, hoặc khách hàng chưa được phân tuyến vui lòng kiểm tra lại...");
								session.setAttribute("dhBean", dhBean);
								String nextJSP = "/AHF/pages/Distributor/DonHangChoXuLyNew.jsp";
								response.sendRedirect(nextJSP);
							}
							else
							{
								dhBean.init();
								dhBean.setSpList(spList);

								dhBean.setMessage("Mã khách hàng không đúng, hoặc khách hàng chưa được phân tuyến vui lòng kiểm tra lại...");
								session.setAttribute("dhBean", dhBean);
								String nextJSP = "/AHF/pages/Distributor/DonHangChoXuLyUpdate.jsp";
								response.sendRedirect(nextJSP);
							}
						}
						if(rs!=null){
							rs.close();
						}
					} 
					catch (Exception e) {};				
				}
			}
			else
			{
				if(action.equals("save"))
				{
					if(id == null)
					{
						boolean tao = dhBean.CreateDh(spList);
						String dhId = dhBean.getId();
						if (!tao)
						{							
							dhBean.createRS();
							dhBean.setSpList(spList);
							session.setAttribute("dhBean", dhBean);
							String nextJSP = "/AHF/pages/Distributor/DonHangChoXuLyNew.jsp";
							response.sendRedirect(nextJSP);
						}
						else
						{
							/*
						IDonHangChoXuLyList obj = new DonhangList();
						obj.setUserId(userId);
						obj.init("");
						session.setAttribute("obj", obj);
						session.setAttribute("ddkdId", "");

						String nextJSP = "/AHF/pages/Distributor/DonHang.jsp";
						response.sendRedirect(nextJSP);	
							 */
							dhBean = (IDonHangChoXuLy) new DonHangChoXuLy("");
							dhBean.setUserId(userId);
							dhBean.createRS();
							dhBean.setNgaygiaodich(ngaygd);
							dhBean.setMessage("Số đơn hàng bạn vừa lưu " + dhId);

							// Save Data into session
							session.setAttribute("dhBean", dhBean);//truyen vao session mot doi tuong donhang co gia tri rong khi khoi tao de ben form don hang nhan dc
							session.setAttribute("khId", "");
							session.setAttribute("ddkdId", "");
							session.setAttribute("nppId", dhBean.getNppId());

							String nextJSP = "/AHF/pages/Distributor/DonHangChoXuLyNew.jsp";
							response.sendRedirect(nextJSP);
						}
					}
					else
					{
						//Kiem tra xem neu don hang do da co khuyen mai, ma thay doi san pham thi phai ap lai khuyen mai
						boolean flag = false;
						boolean cokm = Boolean.parseBoolean(util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("cokm"))));

						if(cokm == true)
						{
							flag = CheckDonHangDKM(spList, id, db);
							if(flag == false)
							{
								dhBean.init();
								dhBean.setSpList(spList);
								dhBean.setKhId(khId);
								dhBean.setDdkdId(ddkdId);
								dhBean.setMessage("Đơn hàng này đã hưởng khuyến mại, khi thay đổi sản phẩm bạn phải áp lại khuyến mại");
								session.setAttribute("dhBean", dhBean);
								String nextJSP = "/AHF/pages/Distributor/DonHangChoXuLyUpdate.jsp";
								response.sendRedirect(nextJSP);
								return;
							}
						}

						boolean temp = false;
						if(trangthai.equals("3")) //da xuat kho
							temp = dhBean.UpdateDhXuatKho(spList);
						else
							temp = dhBean.UpdateDh(spList);

						if (temp == false)
						{
							dhBean.init();
							dhBean.setSpList(spList);
							dhBean.setKhId(khId);
							dhBean.setDdkdId(ddkdId);
							session.setAttribute("dhBean", dhBean);
							String nextJSP = "/AHF/pages/Distributor/DonHangChoXuLyUpdate.jsp";
							response.sendRedirect(nextJSP);
							return;
						}
						else
						{
							/*
						IDonHangChoXuLyList obj = new DonhangList();
						obj.setUserId(userId);
						obj.init("");
						session.setAttribute("obj", obj);
						session.setAttribute("ddkdId", "");

						String nextJSP = "/AHF/pages/Distributor/DonHang.jsp";
						response.sendRedirect(nextJSP);	
						return;
							 */
							dhBean = (IDonHangChoXuLy) new DonHangChoXuLy("");
							dhBean.setUserId(userId);
							dhBean.createRS();
							dhBean.setNgaygiaodich(ngaygd);

							// Save Data into session
							session.setAttribute("dhBean", dhBean);//truyen vao session mot doi tuong donhang co gia tri rong khi khoi tao de ben form don hang nhan dc
							session.setAttribute("khId", "");
							session.setAttribute("ddkdId", "");
							session.setAttribute("nppId", dhBean.getNppId());
							dhBean.setMessage("Số đơn hàng bạn vừa lưu " + id);

							String nextJSP = "/AHF/pages/Distributor/DonHangChoXuLyNew.jsp";
							response.sendRedirect(nextJSP);
						}					
					}
				}
				else
				{
					if(action.equals("chotdonhang"))
					{
						//save donhangDXK truoc
						if (!(dhBean.UpdateDhXuatKho(spList)))
						{
							dhBean.init();
							dhBean.setKhId(khId);
							dhBean.setDdkdId(ddkdId);
							session.setAttribute("dhBean", dhBean);
							String nextJSP = "/AHF/pages/Distributor/DonHangChoXuLyUpdate.jsp";
							response.sendRedirect(nextJSP);
							return;
						}
						else
						{
							if (!(dhBean.ChotDh(spList)))
							{
								dhBean.init();
								dhBean.setSpList(spList);
								dhBean.setDdkdId(ddkdId);
								dhBean.setKhId(khId);
								session.setAttribute("dhBean", dhBean);
								String nextJSP = "/AHF/pages/Distributor/DonHangChoXuLyUpdate.jsp";
								response.sendRedirect(nextJSP);
								return;
							}
							else
							{
								IDonHangChoXuLyList obj = new DonHangChoXuLyList();
								obj.setUserId(userId);
								obj.init("");
								session.setAttribute("obj", obj);
								session.setAttribute("ddkdId", "");

								String nextJSP = "/AHF/pages/Distributor/DonHang.jsp";
								response.sendRedirect(nextJSP);	
								return;
							}
						}
					}
					else
					{
						if(action.equals("apkhuyenmai"))
						{
							//Save donhang truoc
							if(id == null)
							{   
								boolean tao = dhBean.CreateDh(spList);
								if (!tao)
								{
									dhBean.createRS();
									dhBean.setSpList(spList);	
									session.setAttribute("dhBean", dhBean);
									String nextJSP = "/AHF/pages/Distributor/DonHangChoXuLyNew.jsp";
									response.sendRedirect(nextJSP);
									return;
								}
								else
								{
									id = dhBean.getId();		
								}						
							}
							else
							{
								boolean temp = false;
								if(trangthai.equals("3")) //da xuat kho
									temp = dhBean.UpdateDhXuatKho(spList);
								else
									temp = dhBean.UpdateDh(spList);

								if (!temp)
								{
									dhBean.init();
									dhBean.setKhId(khId);
									dhBean.setDdkdId(ddkdId);
									session.setAttribute("dhBean", dhBean);
									String nextJSP = "/AHF/pages/Distributor/DonHangChoXuLyUpdate.jsp";
									response.sendRedirect(nextJSP);
									return;
								}
							}

							Hashtable<String, Integer> sanpham_soluong = new Hashtable<String, Integer>();
							Hashtable<String, Float> sanpham_dongia = new Hashtable<String, Float>();

							if(masp != null)
							{	
								int m = 0;
								while(m < masp.length)
								{
									if(masp[m] != "")
									{
										System.out.println("111.Ma sp: " + masp[m] + " --- So Luong: " + soluong[m]);
										sanpham_soluong.put(masp[m], Integer.parseInt(soluong[m].replaceAll(",", "")));
										sanpham_dongia.put(masp[m], Float.parseFloat(dongia[m].replaceAll(",", "")));
									}
									m++;
								}	
							}
							if(!trangthai.equals("3"))
							{
								//truoc khi ap lai khuyen mai
								try 
								{
									db.getConnection().setAutoCommit(false);
									String msg = capNhatKM(id, nppId, khId, trangthai,db);

									System.out.println("cau Lenh Cap Nhat KM Tra Ve: "+msg);
									if(msg.length()>0)
									{
										//db.getConnection().rollback();

										dhBean.init();
										dhBean.setKhId(khId);
										dhBean.setDdkdId(ddkdId);
										dhBean.setMessage("3.Khong The Cap Nhat Khuyen Mai Cua Don Hang: " + msg);
										session.setAttribute("dhBean", dhBean);
										String nextJSP = "/AHF/pages/Distributor/DonHangChoXuLyUpdate.jsp";
										response.sendRedirect(nextJSP);
										return;
									}
									else
									{
										db.getConnection().commit();
										db.getConnection().setAutoCommit(true);
									}
								} 
								catch (Exception e) 
								{
									try 
									{
										db.getConnection().rollback();
									}
									catch (Exception e1) {}
								}


								/**********************************************************/
								//Neu co trung bay thi khong duoc huong khuyen mai

								Hashtable<String, Integer> cotrungbay = dhBean.ApTrungBay(dhBean.getId(), khId, nppId, ngaygd);
								//String thongbao = dhBean.getCotrungbay();

								//XLTrungbay  xltb = new XLTrungbay(dhBean.getId(), khId, nppId, ngaygd, userId);
								System.out.println("Size : "+cotrungbay.size());
								if( cotrungbay.size() >= 2 )
								{
									XLTrungbay xltb = new XLTrungbay(dhBean.getId(), khId, nppId, ngaygd, khoId);
									xltb.setTrungbay(cotrungbay);
									session.setAttribute("xltb", xltb);
									String nextJSP = "/AHF/pages/Distributor/TrungBay.jsp";
									response.sendRedirect(nextJSP);
									return;
								}
								else if(cotrungbay.size() == 1)
								{								
									Enumeration<String> keys = cotrungbay.keys();
									String key = "";
									while(keys.hasMoreElements())
									{
										key = keys.nextElement();

										System.out.println("Key: " + key + " -- Value: " + cotrungbay.get(key));
									}
									dhBean.LuuTrungBay(key, cotrungbay.get(key));
									dhBean.setMessage("Đơn hàng: " + dhBean.getId() + " thỏa chương trình trưng bày: " + key + "( đạt " + cotrungbay.get(key) + " xuất)");								
									dhBean.init();
									dhBean.setKhId(khId);
									dhBean.setDdkdId(ddkdId);															

									dhBean.setAplaitrugnbay(false);

									session.setAttribute("dhBean", dhBean);
									String nextJSP = "/AHF/pages/Distributor/DonHangChoXuLyUpdate.jsp";
									response.sendRedirect(nextJSP);														
									return;
								}	

								/************************************************************/


								XLkhuyenmai xlkm = new XLkhuyenmai(userId, ngaygd, khId); //ngay giao dich trong donhang
								xlkm.setKhachhang(khId);

								xlkm.setMasp(masp);
								xlkm.setSoluong(soluong);
								xlkm.setDongia(dongia);
								xlkm.setIdDonhang(id);
								xlkm.setTonggiatriDh((float)tonggiatriKm);
								xlkm.setNgaygiaodich(ngaygd);

								xlkm.setHashA(sanpham_soluong);
								xlkm.setHashB(sanpham_dongia);

								xlkm.setDieuchinh(false); //Lay truong hop ngau nhien

								xlkm.ApKhuyenMai();

								System.out.println("[TonggiatriDh] " + tonggiatriKm);

								List<ICtkhuyenmai> ctkmResual = xlkm.getCtkmResual();
								//xlkm.checkConfirm();
								System.out.println("[So xuat khuyen mai duoc huong: ]" + ctkmResual.size() + "\n");

								if(xlkm.checkConfirm()) //bi dung --> sang trang lua chon
								{
									System.out.println("Bi dung san pham roi...\n");
									session.setAttribute("xlkm", xlkm);
									String nextJSP = "/AHF/pages/Distributor/KhuyenMai.jsp";
									response.sendRedirect(nextJSP);
									return;
								}

								String msg = ""; //nhung ctkm khong thoa
								for(int i = 0; i < ctkmResual.size(); i++)
								{
									ICtkhuyenmai ctkhuyenmai = ctkmResual.get(i);

									System.out.println("ConFi laf: "+ctkhuyenmai.getConfirm());
									if(ctkhuyenmai.getConfirm() == false) //khong dung chung san pham
									{	
										msg += CreateKhuyenmai(ctkhuyenmai, id, nppId, tonggiatriKm, khId, sanpham_soluong, sanpham_dongia, db,ngaygd);

										//remove khoi danh sach
										ctkmResual.remove(i);	
										i = i -1;
									}
								}

								if(msg.length() > 0)
								{
									dhBean.init();
									dhBean.setSpList(spList);
									dhBean.setKhId(khId);
									dhBean.setDdkdId(ddkdId);

									dhBean.setAplaikhuyenmai(false);
									xlkm.DBclose();
									dhBean.setMessage("khong the them moi 'donhang_ctkm_trakm' " + msg);
									session.setAttribute("dhBean", dhBean);
									String nextJSP = "/AHF/pages/Distributor/DonHangChoXuLyUpdate.jsp";
									response.sendRedirect(nextJSP);
									return;
								}

								String nextJSP = "";

								if(ctkmResual.size() > 0)
								{
									session.setAttribute("xlkm", xlkm);
									nextJSP = "/AHF/pages/Distributor/KhuyenMai.jsp";
									response.sendRedirect(nextJSP);
								}
								else
								{	
									xlkm.DBclose();
									dhBean.init();
									dhBean.setAplaikhuyenmai(false);
									dhBean.setKhId(khId);
									dhBean.setDdkdId(ddkdId);
									session.setAttribute("dhBean", dhBean);
									nextJSP = "/AHF/pages/Distributor/DonHangChoXuLyUpdate.jsp";
									response.sendRedirect(nextJSP);
									return;
								}

							}
							else //ap lai khuyen mai cho don hang da xuat kho
							{

								/**********************************************************/
								//Bat buoc phai Ap lai trung bay neu co
								Hashtable<String, Integer> cotrungbay = dhBean.ApTrungBay(dhBean.getId(), khId, nppId, ngaygd);
								//String thongbao = dhBean.getCotrungbay();

								System.out.println("Size : "+cotrungbay.size());
								if( cotrungbay.size() >= 2 )
								{
									XLTrungbay xltb = new XLTrungbay(dhBean.getId(), khId, nppId, ngaygd, khoId);
									xltb.setTrungbay(cotrungbay);
									session.setAttribute("xltb", xltb);
									String nextJSP = "/AHF/pages/Distributor/TrungBay.jsp";
									response.sendRedirect(nextJSP);
									return;
								}
								else if(cotrungbay.size() == 1)
								{								
									Enumeration<String> keys = cotrungbay.keys();
									String key = "";
									while(keys.hasMoreElements())
									{
										key = keys.nextElement();

										System.out.println("Key: " + key + " -- Value: " + cotrungbay.get(key));
									}
									try{
										if(!dhBean.LuuTrungBay(key, cotrungbay.get(key)))
										{
											dhBean.setMessage("Không thể áp trưng bày !");									
											db.getConnection().rollback();
											return;
										}
									}catch(Exception e)
									{System.out.println("Error : "+e.toString());}

									dhBean.setMessage("Đơn hàng: " + dhBean.getId() + " thỏa chương trình trưng bày: " + key + "( đạt " + cotrungbay.get(key) + " xuất)");								
									dhBean.init();
									dhBean.setKhId(khId);
									dhBean.setDdkdId(ddkdId);


									dhBean.setAplaitrugnbay(false);

									session.setAttribute("dhBean", dhBean);
									String nextJSP = "/AHF/pages/Distributor/DonHangChoXuLyUpdate.jsp";
									response.sendRedirect(nextJSP);														
									return;
								}	

								/************************************************************/




								String[] ctkm = request.getParameterValues("trakmSpScheme"); //chi duoc nhung CTKM cu
								String[] maspTrakm = request.getParameterValues("maspTrakm"); //Khong duoc thay doi san pham trakhuyenmai nhan duoc

								String msg = "";
								if(maspTrakm != null)
								{
									XLkhuyenmaiDonhangDXK xlkm = new XLkhuyenmaiDonhangDXK(nppId, id, ngaygd, ctkm, khId); //ngay giao dich trong donhang

									xlkm.setTonggiatriDh((float)tonggiatriKm);
									xlkm.setNgaygiaodich(ngaygd);

									xlkm.setHashA(sanpham_soluong);
									xlkm.setHashB(sanpham_dongia);
									xlkm.setDieuchinh(true);

									xlkm.ApKhuyenMai();

									xlkm.checkConfirm();
									List<ICtkhuyenmai> ctkmResual = xlkm.getCtkmResual();

									boolean config = false;
									for(int i = 0; i < ctkmResual.size(); i++)
									{
										if(ctkmResual.get(i).getConfirm() == true)
										{
											config = true;
											break;
										}
									}

									if(!config)
									{
										msg = CreateKhuyenmaiDhDxk(ctkmResual, id, nppId, tonggiatriKm, khId, db);
									}
									else
									{
										//neu la tra khuyen mai chon san pham (ma so sp > 2), van phai cho nguoi dung lua chon san pham....
										////voi dieu kien la chon nho hon so da xuat kho, kho tu dong tinh

										try
										{
											db.getConnection().setAutoCommit(false);
											String msg1=capNhatKM(id, nppId, khId, trangthai,db);

											if(msg1.length()>0)
											{
												System.out.println(msg1);
												db.getConnection().rollback();
												return;
											}
											else
											{
												db.getConnection().commit();
												db.getConnection().setAutoCommit(true);

												xlkm.setNppTen(dhBean.getNppTen());
												session.setAttribute("xlkm", xlkm);
												String nextJSP = "/AHF/pages/Distributor/KhuyenMaiDxk.jsp";
												response.sendRedirect(nextJSP);
												return;
											}
										}
										catch (Exception e) 
										{
											try
											{
												db.getConnection().rollback();
											} catch (Exception e1) {}
										}
									}
									xlkm.DBclose();
								}

								dhBean.init();
								dhBean.setMessage(msg);
								dhBean.setKhId(khId);
								dhBean.setDdkdId(ddkdId);
								dhBean.createPxkId();

								try
								{
									db.getConnection().setAutoCommit(false);

									String error = dhBean.createPth(dhBean.getPxkId(), db);
									if(error.trim().length() <= 0)
										db.getConnection().commit();
									else
										db.getConnection().rollback();

									dhBean.setMessage(error);
								}
								catch (Exception e) { }

								dhBean.setAplaikhuyenmai(false);

								System.out.println("Ap lai khuyen mai la: " + dhBean.isAplaikhuyenmai());

								session.setAttribute("dhBean", dhBean);
								response.sendRedirect("/AHF/pages/Distributor/DonHangChoXuLyUpdate.jsp");
							}
						}
						else  //submit
						{
							dhBean.setUserId(userId);
							dhBean.createRS();

							String nextJSP = "/AHF/pages/Distributor/DonHangChoXuLyNew.jsp";
							if(id != null)
							{
								dhBean.CreateSpList();
								List<ISanpham> listSp = dhBean.getSpList();

								//cap nhat gia cac san pham
								for(int i = 0; i < spList.size(); i++)
								{
									ISanpham sp = spList.get(i);
									for(int j = 0; j < listSp.size(); j++)
									{
										ISanpham sp2 = listSp.get(j);
										if(sp.getMasanpham().trim().equals(sp2.getMasanpham().trim()))
											spList.get(j).setDongia(sp2.getDongia());
									}
								}
								nextJSP = "/AHF/pages/Distributor/DonHangChoXuLyUpdate.jsp";						
							}									
							dhBean.setSpList(spList);
							session.setAttribute("ddkdId", ddkdId );
							session.setAttribute("dhBean", dhBean);
							response.sendRedirect(nextJSP);				
						}
					}
				}
				db.shutDown();
			}
		}
	}

	private boolean CheckDonHangDKM(List<ISanpham> spList, String id, dbutils db) 
	{
		List<ISanpham> list = new ArrayList<ISanpham>();
		String query = "select a.sanpham_fk as spId, a.soluong, b.ma as spMa from donhang_sanpham a inner join sanpham b on a.sanpham_fk = b.pk_seq where donhang_fk = '" + id + "'";
		ResultSet rs = db.get(query);

		if(rs != null)
		{
			try 
			{
				ISanpham sp = null;
				while(rs.next())
				{
					sp = new Sanpham("", rs.getString("spMa"), "", rs.getString("soluong"), "", "", "", "");
					list.add(sp);
				}
				rs.close();
			} 
			catch (Exception e) {
				return false;
			}
		}

		for(int i = 0; i < list.size(); i++)
		{
			ISanpham spA = list.get(i);
			for(int j = 0; j < spList.size(); j++)
			{
				ISanpham spB = spList.get(j);
				if(spA.getMasanpham().trim().equals(spB.getMasanpham().trim()))
				{
					int slg = Integer.parseInt(spA.getSoluong()) - Integer.parseInt(spB.getSoluong());
					System.out.println("San pham A: " + spA.getMasanpham() + " - So luong: " + spA.getSoluong() + " -- San pham B: " + spB.getMasanpham() + " -- So luong: " + spB.getSoluong() + "\n");
					list.get(i).setSoluong(Integer.toString(slg));
				}
			}
		}

		for(int i = 0; i < list.size(); i++)
		{
			ISanpham spC = list.get(i);
			//System.out.println("San pham " + spC.getMasanpham() + " tai List la: " + spC.getSoluong() + "\n");
			if(!spC.getSoluong().equals("0"))
			{
				//System.out.println("San pham " + spC.getMasanpham() + " tai List la: " + spC.getSoluong() + "\n");
				return false;
			}
		}

		return true;
	}

	private String CreateKhuyenmai(ICtkhuyenmai ctkm, String id, String nppId, long tongGtridh, String khId, Hashtable<String, Integer> sp_sl, Hashtable<String, Float> sp_dg, dbutils db,String tungay)
	{
		String str = "";
		try 
		{ 
			db.update("SET TRANSACTION ISOLATION LEVEL SNAPSHOT;");
			db.update("SET LOCK_TIMEOUT 60000;");

			db.getConnection().setAutoCommit(false);

			List<ITrakhuyenmai> trakmList = ctkm.getTrakhuyenmai();
			for(int count = 0; count < trakmList.size(); count++)
			{
				//ITrakhuyenmai trakm = ctkm.getTrakhuyenmai().get(0);			
				ITrakhuyenmai trakm = ctkm.getTrakhuyenmai().get(count);

				long tongtienTraKM = 0;
				if(trakm.getType() == 1)
					tongtienTraKM = Math.round(ctkm.getSoxuatKM() * trakm.getTongtien());
				else
				{
					if(trakm.getType() == 2) //tra chiet khau
					{
						System.out.println("___Tong tien tra km: " + ctkm.getTongTienTheoDKKM() + " -- Chiet khau: " + trakm.getChietkhau());
						//Tinh tong gia tri tra khuyen mai theo dieu kien (chu khong phai tren tong gia tri don hang)
						long tonggiatriTrakm = ctkm.getTongTienTheoDKKM();
						tongtienTraKM = Math.round(tonggiatriTrakm * (trakm.getChietkhau() / 100));
						//tongtienTraKM = Math.round(tongGtridh * (trakm.getChietkhau() / 100));
					}
					else
					{
						if(trakm.getHinhthuc() == 1)
						{
							tongtienTraKM = Math.round(ctkm.getSoxuatKM() * trakm.getTongGtriKm());
							System.out.println("Tong tien trakm co dinh: " + tongtienTraKM + "\n");
						}
					}
				}	

				/*********************************************************************************/
				if(ctkm.getPhanbotheoDH().equals("0"))
				{
					String msg = CheckNghanSach(ctkm.getId(), nppId, Long.toString(tongtienTraKM), "0", db);
					if(msg.trim().length() > 0)
					{
						db.getConnection().rollback();
						return msg;
					}
				}
				/*********************************************************************************/




				if(trakm.getType() == 3) //san pham co so luong co dinh
				{
					/*System.out.println("san pham co so luong co dinh ");*/
					if(trakm.getHinhthuc() == 1)
					{
						//String sql = "select a.sanpham_fk as spId, a.soluong, b.ma as spMa, a.dongia from trakhuyenmai_sanpham a inner join sanpham b on a.sanpham_fk = b.pk_seq where a.trakhuyenmai_fk = '" + trakm.getId() + "'";
						String sql = "select f.pk_seq as spId, a.soluong, e.GIAMUANPP as dongia, f.ma as spMa  " +
								"from Trakhuyenmai_sanpham a inner join SANPHAM f on a.SANPHAM_FK = f.PK_SEQ " +
								"	inner join BGMUANPP_SANPHAM e on a.sanpham_fk = e.SANPHAM_FK " +
								"where a.TRAKHUYENMAI_FK = '" + trakm.getId() + "' " +
								"and e.BGMUANPP_FK in ( select top(1) a.PK_SEQ " +
								"from BANGGIAMUANPP a inner join BANGGIAMUANPP_NPP b on a.PK_SEQ = b.BANGGIAMUANPP_FK  " +
								"where a.TUNGAY <= '" + tungay + "' and b.NPP_FK = '" + nppId + "' and a.KENH_FK = ( select KBH_FK from KHACHHANG where PK_SEQ = '" + khId + "' ) and a.DVKD_FK = '100068' " +
								"order by a.TUNGAY desc  ) -- and e.GIAMUANPP > 0  and e.trangthai = '1'  ";

						System.out.println("[BGMUANPP_SANPHAM]"+sql);

						ResultSet rsSQl = db.get(sql);
						/*if(rsSQl != null)*/
						{
							while(rsSQl.next())
							{
								int slg = Integer.parseInt(rsSQl.getString("soluong")) * (int)ctkm.getSoxuatKM();
								long tonggtri = Math.round(slg * rsSQl.getFloat("dongia"));


								/*********************************************************************************/
								if(ctkm.getPhanbotheoDH().equals("1"))
								{
									String msg = CheckNghanSach(ctkm.getId(), nppId, Long.toString(slg), "1", db);
									if(msg.trim().length() > 0)
									{
										db.getConnection().rollback();
										return msg;
									}
								}
								/*********************************************************************************/


								String error = checkTonkho(nppId, ctkm.getId(), khId, rsSQl.getString("spId"), rsSQl.getString("spMa"), slg, db);
								if(error.length() > 0)
								{
									return error;
								}

								//luu tong gia tri o moi dong sanpham
								//String query = "Insert into donhang_ctkm_trakm(donhangId, ctkmId, trakmId, soxuat, tonggiatri, spMa, soluong) values('" + id + "','" + ctkm.getId() + "','" + trakm.getId() + "','" + ctkm.getSoxuatKM() + "','" + Long.toString(tongtienTraKM) + "', '" + rsSQl.getString("spMa").trim() + "', '" + Integer.toString(slg) + "')";
								String query = "Insert into donhang_ctkm_trakm(donhangId, ctkmId, trakmId, soxuat, tonggiatri, spMa, soluong) values('" + id + "','" + ctkm.getId() + "','" + trakm.getId() + "','" + ctkm.getSoxuatKM() + "','" + Long.toString(tonggtri) + "', '" + rsSQl.getString("spMa").trim() + "', '" + Integer.toString(slg) + "')";
								System.out.println("1.Chen khuyen mai co dinh: " + query);

								if(!db.update(query))
								{
									db.getConnection().rollback();
									str = query;
									return str;
								}

								//cap nhat kho
								query = "Update nhapp_kho set available = available - '" + Integer.toString(slg) + "', booked = booked + '" + Integer.toString(slg) + "' where kho_fk = (select kho_fk from ctkhuyenmai where pk_seq = '" + ctkm.getId() + "') and npp_fk = '" + nppId + "' and sanpham_fk = '" + rsSQl.getString("spId") + "' and kbh_fk = (select kbh_fk from khachhang where pk_seq = '" + khId+ "')";   							
								System.out.println("2.Cap nhat kho: " + query);
								if(!db.update(query))
								{
									db.getConnection().rollback();
									str = query;
									return str;
								}

								query = "update Phanbokhuyenmai set DASUDUNG = DASUDUNG + " + tongtienTraKM + " where ctkm_fk = '" + ctkm.getId() + "' and npp_fk = '" + nppId + "'";
								System.out.println("4.Cap nhat phanbo Phanbokhuyenmai: " + query); 
								if(!db.update(query))
								{
									db.getConnection().rollback(); 
									str = query;
									return str;
								}
							}
						}
						rsSQl.close();
					}
					else //tinh so luong san pham nhapp da chon, phai check ton kho tung buoc
					{
						if(trakm.getHinhthuc() == 2)
						{
							String query = "select a.sanpham_fk as spId, c.MA as spMa, isnull(bgmnpp.dongia, '0') as dongia, isnull(b.TONGLUONG, 0) as tongluong " +
									"from TRAKM_NHAPP a inner join TRAKHUYENMAI b on a.trakm_fk = b.PK_SEQ " +
									" inner join SANPHAM c on a.sanpham_fk = c.PK_SEQ " +
									" left join (  select sanpham_fk, GIAMUANPP as dongia  " +
									"				from BGMUANPP_SANPHAM   " +
									"				where BGMUANPP_FK in (  select top(1) a.PK_SEQ " +
									"										from BANGGIAMUANPP a inner join BANGGIAMUANPP_NPP b on a.PK_SEQ = b.BANGGIAMUANPP_FK   " +
									"										where a.TUNGAY <= '" + tungay + "' and b.NPP_FK = '" + nppId + "' and a.KENH_FK = ( select kbh_fk from khachhang where pk_seq='" + khId + "' ) and a.DVKD_FK = '100068' " +
									"										order by a.TUNGAY desc  )   " +
									") bgmnpp on bgmnpp.sanpham_fk=a.sanpham_fk" + 
									" where a.ctkm_fk = '" + ctkm.getId() + "' and a.npp_fk = '" + nppId + "' and a.trakm_fk = '" + trakm.getId() + "' " +
									"order by a.thutuuutien asc";

							System.out.println("5.Query tinh gia km npp chon truoc: " + query);

							ResultSet spkm = db.get(query);

							String sp = "";
							String ma = "";
							String dg = "";
							String tg = "";
							while(spkm.next())
							{
								sp += spkm.getString("spId") + ",";
								dg += spkm.getString("dongia") + ",";
								tg += spkm.getString("tongluong") + ",";
								ma += spkm.getString("spMa") + ",";
							}

							String[] spId = sp.split(",");
							String[] dongia = dg.split(",");
							String[] tongluong = tg.split(",");
							String[] spMa = ma.split(",");

							//CheckTonKho nhung tra khuyen mai da duoc npp chon truoc
							String[] arr = checkTonKhoTraKm(nppId, ctkm, khId, spId, dongia, tongluong, spMa, db);
							if(arr == null)  //nhung san pham da chon truoc cua tra khuyen mai da het hang trong kho
							{
								db.getConnection().rollback();
								str = "Số lượng những sản phẩm bạn chọn trước trong thiết lập sản phẩm trả khuyến mãi không đủ trong kho";
								System.out.println("Error: " + str + "\n");
								return str;
							}
							else
							{

								/*********************************************************************************/
								if(ctkm.getPhanbotheoDH().equals("1"))
								{
									String msg = CheckNghanSach(ctkm.getId(), nppId, arr[1].replaceAll(",", ""), "1", db);
									if(msg.trim().length() > 0)
									{
										db.getConnection().rollback();
										return msg;
									}
								}
								/*********************************************************************************/


								//luu tong gia tri o moi dong sanpham
								query = "Insert into donhang_ctkm_trakm(donhangId, ctkmId, trakmId, soxuat, tonggiatri, spMa, soluong) values('" + id + "','" + ctkm.getId() + "','" + trakm.getId() + "','" + ctkm.getSoxuatKM() + "','" + arr[2] + "', '" + arr[3] + "', '" + arr[1] + "')";
								System.out.println("6.Chen khuyen mai Npp chon truoc: " + query);

								if(!db.update(query))
								{
									db.getConnection().rollback();
									str = query;
									return str;
								}

								//cap nhat kho
								query = "Update nhapp_kho set available = available - '" + arr[1].replaceAll(",", "") + "', booked = booked + '" + arr[1].replaceAll(",", "") + "' where kho_fk = (select kho_fk from ctkhuyenmai where pk_seq = '" + ctkm.getId() + "') and npp_fk = '" + nppId + "' and sanpham_fk = '" + arr[0] + "' and kbh_fk = (select kbh_fk from khachhang where pk_seq = '" + khId+ "')";   							
								System.out.println("7.Cap nhat npp_kho: " + query);
								if(!db.update(query))
								{
									db.getConnection().rollback();
									str = query;
									return str;
								}

								query = "update Phanbokhuyenmai set DASUDUNG = DASUDUNG + " + arr[2].replaceAll(",", "") + " where ctkm_fk = '" + ctkm.getId() + "' and npp_fk = '" + nppId + "'";
								System.out.println("9.Cap nhat ngan sach Phanbokhuyenmai: " + query);
								if(!db.update(query))
								{
									db.getConnection().rollback();
									str = query;
									return str;
								}
							}
						}
					}
				}
				else
				{
					if(trakm.getType() != 3)//tra tien, tra chiet khau
					{
						String query = "Insert into donhang_ctkm_trakm(donhangId, ctkmId, trakmId, soxuat, tonggiatri) values('" + id + "','" + ctkm.getId() + "','" + trakm.getId() + "','" + ctkm.getSoxuatKM() + "'," + tongtienTraKM + ")";
						System.out.println("10.Chen khuyen mai tien / ck: " + query);
						if(!db.update(query))
						{
							db.getConnection().rollback();
							str = query;
							return str;
						}

						query = "update Phanbokhuyenmai set DASUDUNG = DASUDUNG + " +tongtienTraKM + " where ctkm_fk ='" + ctkm.getId() + "' and npp_fk='" + nppId + "'";
						System.out.println("12.Cap nhat ngan sach Phanbokhuyenmai: " + query);
						if(!db.update(query))
						{
							db.getConnection().rollback();
							str = query;
							return str;
						}
					}
				}
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e1)
		{
			e1.printStackTrace();
			try 
			{
				db.getConnection().rollback();
			} 
			catch (SQLException e) {}
			return "Loi khi tao moi ctkm " + ctkm + ", " + e1.toString(); 
		}
		finally
		{
			db.shutDown();
		}
		return str;
	}

	private String[] checkTonKhoTraKm(String nppId, ICtkhuyenmai ctkm, String khId, String[] spId, String[] dongia, String[] tongluong, String[] spma, dbutils db) 
	{
		String[] kq = new String[4];

		String msg = "";
		try
		{
			for(int i = 0; i < spId.length; i++)
			{
				int slg = Integer.parseInt(tongluong[i]) * ctkm.getSoxuatKM();
				msg = checkTonkho(nppId, ctkm.getId(), khId, spId[i], "", slg, db);
				if(msg == "")  //thoa ton kho
				{
					kq[0] = spId[i];
					kq[1] = Integer.toString(slg);
					kq[2] = Long.toString(Math.round(slg * Float.parseFloat(dongia[i])));
					//System.out.println("Don gia: " + spId[i] + "- dongia: " + dongia[i] + " - Tong gia tri o buoc nay: " + kq[2] + "\n");
					kq[3] = spma[i];

					return kq;
				}
			}
		}
		catch (Exception e) {
			return null;
		}
		return null;
	}

	private String CreateKhuyenmaiDhDxk(List<ICtkhuyenmai> ctkmList, String id, String nppId, long tongGtridh, String khId, dbutils db)
	{
		String str = "";
		try 
		{ 

			db.update("SET TRANSACTION ISOLATION LEVEL SNAPSHOT;");
			db.update("SET LOCK_TIMEOUT 60000;");

			db.getConnection().setAutoCommit(false);

			//cap nhat lai so luong san pham khuyen mai cua tung chuong trinh theo so xuat tuong ung
			String listCtkm = "";
			for(int i = 0; i < ctkmList.size(); i++)
			{
				ICtkhuyenmai ctkm = ctkmList.get(i);
				ITrakhuyenmai trakm = ctkm.getTrakhuyenmai().get(0);

				listCtkm += "'" + ctkm.getId() + "',";
				float tongtienTraKM = 0f;
				if(trakm.getType() != 3) 
				{
					if(trakm.getType() == 1) //tra tien
						tongtienTraKM = ctkm.getSoxuatKM() * trakm.getTongtien();
					else //tra chiet khau
						tongtienTraKM = tongGtridh * (trakm.getChietkhau() / 100);

					ResultSet rsTt = db.get("select tonggiatri from donhang_ctkm_trakm where donhangId='" + id + "' and ctkmId='" + ctkm.getId() + "'");
					float tt = 0.0f;
					if(rsTt.next())
					{
						tt = rsTt.getFloat("tonggiatri");
						rsTt.close();
					}

					String query = "update donhang_ctkm_trakm set soxuat = '" + Integer.toString(ctkm.getSoxuatKM()) + "', tonggiatri = " + tongtienTraKM + " where donhangId = '" + id + "' and ctkmId = '" + ctkm.getId() + "'";
					if(!db.update(query))
					{
						str = query;
						db.getConnection().rollback();
						return query;
					}

					String st ="update Phanbokhuyenmai set DASUDUNG = DASUDUNG - " +( tt - tongtienTraKM) + " where ctkm_fk='" + ctkm.getId() + "' and npp_fk='" + nppId + "'";			
					if(!db.update(st)){	
						db.getConnection().rollback(); 
						return st;
					}

				}
				else
				{
					String query = "select soxuat, tonggiatri, spMa, soluong from donhang_ctkm_trakm where donhangId = '" + id + "' and ctkmid = '" + ctkm.getId() + "' and spMa is not null";
					System.out.println("Query lay gia tri km la: " + query + "\n");
					ResultSet rsSp = db.get(query);
					float tonggiatri = 0f;
					float tonggiatriNew = 0f;
					while(rsSp.next())
					{
						int soxuat = rsSp.getInt("soxuat");
						String spMa = rsSp.getString("spMa");
						int soluong = rsSp.getInt("soluong");
						tonggiatri = rsSp.getFloat("tonggiatri");
						System.out.println("Tong gia tri buoc nay la: " + tonggiatri + "\n");

						int sx = ctkm.getSoxuatKM();
						//if(sx != soxuat)
						//{
						if(sx > soxuat)
							sx = soxuat;

						tonggiatriNew += (tonggiatri * sx) / soxuat;
						int soluongNew = (soluong * sx) / soxuat;
						query = "update donhang_ctkm_trakm set soxuat = '" + Integer.toString(sx) + "', tonggiatri = " + Float.toString(tonggiatriNew) + ", soluong = '" + Integer.toString(soluongNew) + "' where donhangId = '" + id + "' and ctkmid = '" + ctkm.getId() + "' and spMa = '" + spMa.trim() + "'";
						System.out.println("Query update la: " + query + "\n");

						if(!db.update(query))
						{
							str = query;
							db.getConnection().rollback();
							return str;
						}

						String st ="update Phanbokhuyenmai set DASUDUNG = DASUDUNG - " + (tonggiatri - tonggiatriNew) + " where ctkm_fk='" + ctkm.getId() + "' and npp_fk='" + nppId + "'";
						if(!db.update(st)){	
							db.getConnection().rollback(); 
							return st;
						}
					}
					rsSp.close();
				}
			}
			if(listCtkm.length() > 0)
			{
				listCtkm = listCtkm.substring(0, listCtkm.length() - 1);
				ResultSet rsTt = db.get("select ctkmId, tonggiatri from donhang_ctkm_trakm where donhangId = '" + id + "' and ctkmId not in (" + listCtkm + ")");
				while(rsTt.next())
				{
					String st ="update Phanbokhuyenmai set DASUDUNG = DASUDUNG - " + rsTt.getDouble("tonggiatri") + " where ctkm_fk='" + rsTt.getString("ctkmId") + "' and npp_fk='" + nppId + "'";
					if(!db.update(st)){	
						db.getConnection().rollback(); 
						return st;
					}

				}
				rsTt.close();

				//Xoa nhung ctkm khong con duoc huong
				String st="delete from donhang_ctkm_trakm where donhangId = '" + id + "' and ctkmId not in (" + listCtkm + ")";
				if(!db.update(st)){	
					db.getConnection().rollback(); 
					return st;
				}
				//System.out.println("Cau lenh xoa nhung ctkm khong thoa la: delete from donhang_ctkm_trakm where donhangId = '" + id + "' and ctkmId not in (" + listCtkm + ")");
			}
			else //khong con ctkm nao thoa
			{
				String msg=capNhatKM(id, nppId, khId, "3", db);

				if(msg.length()>0)//3
				{

					db.getConnection().rollback();
					return "Khong The Cap Nhat Khuyen Mai Cua Don Hang: " + id +"Error:" + msg;
				}
			}

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e1) {

			geso.dms.center.util.Utility.rollback_throw_exception(db);
			return "Có lỗi trong quá trình cập nhật lại khuyến mại của đơn hàng, vui lòng thử lại sau..." + e1.getMessage();

		}
		return str;
	}

	private String checkTonkho(String nppId, String ctkmId, String khId, String spId, String spMa, int slg, dbutils db) 
	{
		//kiem tra trong kho khuyen mai con du san pham hay khong, khong du thi thoat
		String query = "select available from nhapp_kho where kho_fk = (select kho_fk from ctkhuyenmai where pk_seq = '" + ctkmId + "') and npp_fk = '" + nppId + "' and sanpham_fk = '" + spId + "' and kbh_fk = (select kbh_fk from khachhang where pk_seq = '" + khId + "')";

		ResultSet rsCheck = db.get(query);
		/*if(rsCheck != null)*/
		{
			try 
			{
				boolean flag = false;

				while(rsCheck.next())
				{
					if(rsCheck.getString("available") != null)
					{
						flag = true;
						String avai = rsCheck.getString("available");
						if(Integer.parseInt(avai) < slg)
						{
							return "Sản phẩm khuyến mại " + spMa + " - Còn tối đa " + avai;
						}
					}
				}
				rsCheck.close();
				if(flag == false) //khong co dong du lieu nao, Resualset van khac null
				{
					//System.out.println("San pham khuyen mai " + spMa + " --- Con toi da 0 \n");
					return "San pham khuyen mai " + spMa + " - Con toi da 0";
				}
			}
			catch (Exception e) {e.printStackTrace(); return "error"; }
		}

		return "";
	}

	private String  capNhatKM(String id, String nppId, String khId, String trangthai,dbutils db)
	{

		try 
		{

			String query = "select ctkmId, trakmId, sum(tonggiatri) as tonggiatri from donhang_ctkm_trakm where donhangid='" + id + "' group by ctkmId, trakmId";
			System.out.println("Cau lenh lay du lieu cap nhat: " + query + "\n");
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				while(rs.next())
				{
					String ctkmId = rs.getString("ctkmId");
					String tonggiatri = rs.getString("tonggiatri");
					String trakmId = rs.getString("trakmId");
					String st ="update Phanbokhuyenmai set DASUDUNG = DASUDUNG - " + tonggiatri + " where ctkm_fk='" + ctkmId + "' and npp_fk='" + nppId + "'";
					System.out.println(st);

					if(!	db.update(st))
					{
						db.getConnection().rollback();
						return "Error :"+ st;
					}



					//cap nhat lai sanpham trong kho
					if(!trangthai.equals("3")) //neu don hang da xuat kho, khong duoc cap nhat truc tiep kho, chi khi chot phieu thu hoi
					{
						query = "select spMa, soluong from donhang_ctkm_trakm where donhangId = '" + id + "' and ctkmId = '" + ctkmId + "' and trakmId = '" + trakmId + "' and soluong is not null";
						System.out.println(query);
						ResultSet spRs = db.get(query);
						if(spRs != null)
						{
							while(spRs.next())
							{
								String maSp = spRs.getString("spMa");
								String sl = spRs.getString("soluong");						
								query = "Update nhapp_kho set available = available + '" + sl + "', booked = booked - '" + sl + "' where kho_fk = (select kho_fk from ctkhuyenmai where pk_seq = '" + ctkmId + "') and npp_fk = '" + nppId + "' and sanpham_fk in (select pk_seq from sanpham where ma = '" + maSp + "') and kbh_fk in (select kbh_fk from khachhang where pk_seq = '" + khId + "')";
								if(!db.update(query))
								{
									db.getConnection().rollback();
									return "Error :"+ query;
								}

								String sql = "select booked, available from nhapp_kho where kho_fk = (select kho_fk from ctkhuyenmai where pk_seq = '" + ctkmId + "') and npp_fk = '" + nppId + "' and sanpham_fk in (select pk_seq from sanpham where ma = '" + maSp + "') and kbh_fk in (select kbh_fk from khachhang where pk_seq = '" + khId + "')";;
								ResultSet checkBook = db.get(sql);
								System.out.println(sql);
								if(checkBook != null)
								{
									while(checkBook.next())
									{
										String booked = checkBook.getString("booked");
										String available = checkBook.getString("available");

										String error = "Chuc nang DonHangChoXuLyUpdateSVL / capnhatKhuyenMai, giam Booked, tai thoi diem: " + getDateTimeNow();

										String str = "insert ErrorMsg(nppId, pxkId, dhId, spId, listId, msg, booked, avai, soluongdieuchinh) " +
												"values('" + nppId + "', '', '" + id + "', '" + maSp + "', '', '" + error + "', '" + booked + "', '" + available + "', '" + sl + "')";
										System.out.println(str);
										db.update(str);
									}
									checkBook.close();
								}
							}
							spRs.close();
						}
					}
				}
				rs.close();
			}

			//delete neu ton tai, cap nhat lai kho voi so luong tang
			query = "delete from donhang_ctkm_trakm where donhangid = '" + id + "'";

			System.out.println(query);
			if(!db.update(query))
			{
				db.getConnection().rollback();

				System.out.println("Error :"+ query);
				return "Error :"+ query;
			}



			System.out.println("Oke Da xONg");
			return "";
		} 
		catch (Exception e1) {
			try 
			{
				db.getConnection().rollback();
			} catch (Exception e) {
				System.out.println("Error :"+e.toString());
			}
			System.out.println("Error :"+e1.toString());
			return "Error :"+ e1.toString();
		}
	}

	boolean kiemtraDKKM(String donhangId, String ctkm,dbutils db)
	{
		String sql ="select a.sanpham_fk,a.soluong,a.batbuoc from dieukienkm_sanpham a,ctkm_dkkm b where b.dkkhuyenmai_fk = a.dieukienkhuyenmai_fk and a.batbuoc = 1 and b.ctkhuyenmai_fk ='" + ctkm + "'";
		//System.out.println(sql);
		ResultSet rs = db.get(sql);
		try 
		{
			while(rs.next())
			{		
				sql ="select count(*) as num from donhang_sanpham where sanpham_fk ='" + rs.getString("sanpham_fk") + "' and soluong >='" + rs.getString("soluong") + "' and donhang_fk='" + donhangId + "'";
				//System.out.println(sql);
				ResultSet tb = db.get(sql);
				tb.next();
				if(tb.getString("num").equals("0"))
					return false;
			}
			rs.close();
		} 
		catch (Exception e) {
			return false;
		}
		return true;
	}

	private String getDateTime() 
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);	
	}

	private String getDateTimeNow()
	{
		String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		String name = sdf.format(cal.getTime());
		return name;
	}

	private boolean checkNgayKhoaSo(String ngayksgn, String ngaygd) 
	{
		if(ngayksgn.equals(""))
			ngayksgn = this.getDateTime();

		String[] ngayks = ngayksgn.split("-");
		String[] ngaydh = ngaygd.split("-");

		Calendar c1 = Calendar.getInstance(); //new GregorianCalendar();
		Calendar c2 = Calendar.getInstance(); //new GregorianCalendar();

		c1.set(Integer.parseInt(ngayks[0]), Integer.parseInt(ngayks[1]) - 1, Integer.parseInt(ngayks[2]));
		c2.set(Integer.parseInt(ngaydh[0]), Integer.parseInt(ngaydh[1]) - 1, Integer.parseInt(ngaydh[2]));

		long songay = (c2.getTime().getTime() - c1.getTime().getTime()) / (24 * 3600 * 1000);

		if(songay >= 1)
			return true;
		return false;
	}

	private String CheckNghanSach(String ctkmId, String nppId, String ngansach, String loai, dbutils db)
	{
		String sql = 
				"select a.ctkm_fk, b.scheme, b.phanbotheodonhang, a.ngansach,  "+
						"	case  "+
						"	when b.phanbotheodonhang=0 then "+
						"			isnull( ( select sum(tonggiatri) "+
						"			  from donhang_ctkm_trakm "+
						"			  where ctkmid = a.ctkm_fk  and donhangid in ( select pk_seq from donhang where npp_fk = a.				npp_fk and trangthai != 2 ) and donhangid not in ( select donhang_fk from donhangtrave where donhang_fk is not				null and npp_fk = '"+nppId+"' and trangthai in ( 1, 3 ) ) ), 0 ) "+
						"else "+
						"	isnull( ( select sum(soluong) "+
						"	from donhang_ctkm_trakm "+
						"	where ctkmid = a.ctkm_fk and spma is not null and donhangid in "+ 
						"		( select pk_seq from donhang where npp_fk = a.npp_fk and	trangthai != 2 ) and donhangid not in ( select donhang_fk	from donhangtrave where donhang_fk is not null and npp_fk = '"+nppId+"' and trangthai in ( 1, 3 ) ) ), 0 ) end as dasudung "+ 
						"	from phanbokhuyenmai a inner join ctkhuyenmai b on a.ctkm_fk = b.pk_seq "+
						"where npp_fk = '" + nppId + "' and ctkm_fk = '" + ctkmId + "'  ";

		System.out.println("1.Cau lenh check ngan sach: " + sql + " --- Ngan sach de check: " + ngansach);
		ResultSet rs = db.get(sql);
		String scheme = "";
		String pbTHEODONHANG = "";
		try 
		{
			float conlai = 0.0f;
			if(rs.next())
			{
				scheme = rs.getString("scheme");
				conlai = Float.parseFloat(rs.getString("ngansach")) - Float.parseFloat(rs.getString("DASUDUNG"));
				pbTHEODONHANG = rs.getString("PHANBOTHEODONHANG");
				rs.close();	
			}

			if(pbTHEODONHANG.equals("0"))
			{
				if( conlai < Float.parseFloat(ngansach) )
				{
					return "1.Chương trình khuyến mại: " + scheme + ", đã hết ngân sách phân bổ";
				}
			}
			else
			{
				if( conlai <= 0 )
				{
					return "1.Chương trình khuyến mại: " + scheme + ", đã hết ngân sách phân bổ";
				}
			}
		} 
		catch (Exception e) 
		{ 
			e.printStackTrace();
			System.out.println("__EXCEPTION CHECK NGAN SACH: " + e.getMessage());
			return "2.Chương trình khuyến mại: " + scheme + ", đã hết ngân sách phân bổ";
		}
		return "";
	}

}
