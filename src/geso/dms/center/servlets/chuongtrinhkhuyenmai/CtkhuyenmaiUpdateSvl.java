package geso.dms.center.servlets.chuongtrinhkhuyenmai;

import geso.dms.center.beans.ctkhuyenmai.*;
import geso.dms.center.beans.ctkhuyenmai.imp.*;
import geso.dms.center.beans.dieukienkhuyenmai.ISanpham;
import geso.dms.center.beans.dieukienkhuyenmai.imp.Sanpham;
import geso.dms.center.util.Utility;
import geso.dms.distributor.db.sql.dbutils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

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

import com.oreilly.servlet.MultipartRequest;

public class CtkhuyenmaiUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	PrintWriter out; 

    public CtkhuyenmaiUpdateSvl() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		ICtkhuyenmai ctkmBean;
		
		this.out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    out.println(userId);
	    
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
	    	    
	    String id = util.getId(querystring);
	    
	    //System.out.println("id :" + id);

	    ctkmBean = new Ctkhuyenmai(id);
	    ctkmBean.setId(id);
	    ctkmBean.setUserId(userId);
	    
        ctkmBean.init();
        session.setAttribute("ctkmBean", ctkmBean);
        session.setAttribute("type", ctkmBean.getType());
    	
        String nextJSP = "/AHF/pages/Center/ChuongTrinhKhuyenMaiUpdate.jsp";
        
        //copy chuogn trinh khuyen mai
		String copy = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("copy"));
		if(copy != null)
		{			
			nextJSP = "/AHF/pages/Center/ChuongTrinhKhuyenMaiNew.jsp";
			ctkmBean.setScheme("");
			/*ctkmBean.setTungay("");
			ctkmBean.setDenngay("");
			ctkmBean.setNpp(null);*/
		}
		String display = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("display"));
		if(display != null)
		{			
			nextJSP = "/AHF/pages/Center/ChuongTrinhKhuyenMaiDisplay.jsp";
		}
		
        response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		ICtkhuyenmai ctkmBean;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		String contentType = request.getContentType();
		this.out = response.getWriter();
		Utility util = new Utility();
		
	    String id = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));
	    
	    if(id == null){  	
	    	ctkmBean = new Ctkhuyenmai("");
	    }else{
	    	ctkmBean = new Ctkhuyenmai(id);
	    }
	    
	    String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
 		System.out.println("action:"+ action);
	    	    
		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
		ctkmBean.setUserId(userId);	       
		
		if(action.equals("ngunghoatdong")) {
			
 			if(ctkmBean.updateNgayNgungHoatDong()) {
 				ctkmBean.init();
 				
 		        session.setAttribute("ctkmBean", ctkmBean);
 		        session.setAttribute("type", ctkmBean.getType());
 		        
 		        String nextJSP = "/AHF/pages/Center/ChuongTrinhKhuyenMaiUpdate.jsp";
 		        response.sendRedirect(nextJSP);
 		        return;
 			}
 			
 		}
    			
		String scheme = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("scheme")));
		if (scheme == null)
			scheme = "";
		ctkmBean.setScheme(scheme);
		
		String diengiai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diengiai")));
		if (diengiai == null)
			diengiai = "";
		ctkmBean.setDiengiai(diengiai);
		
		String tungay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay")));
		if (tungay == null)
			tungay = "";
		ctkmBean.setTungay(tungay);
		
		String denngay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay")));
		if (denngay == null)
			denngay = "";
		ctkmBean.setDenngay(denngay);
		
		String type = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("loaiCt")));
		if (type == null)
			type = "";
		ctkmBean.setType(type);
		session.setAttribute("type", type);
		
		String loains = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("loains")));
		if (loains == null)
			loains = "1";
		ctkmBean.setLoaiNganSach(loains);
		
		String[] loaikhId = request.getParameterValues("loaikhId");
		String str4 = "";
		if(loaikhId != null)
		{
			for(int i = 0; i < loaikhId.length; i++)
				str4 += loaikhId[i] + ",";
			if(str4.length() > 0)
				str4 = str4.substring(0, str4.length() - 1);
		}
		System.out.println("---LOAI KHACH HANG: " + str4);
		ctkmBean.setLoaikhIds(str4);
		String ngansach = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngansach")));
		if (ngansach == null)
			ngansach = "";
		ctkmBean.setNgansach(ngansach);
				
		String dasudung =  util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dasudung")));
		if (dasudung == null)
			dasudung = "";
		ctkmBean.setDasudung(dasudung);
		
		String active =  util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("active")));
		if (active == null)
			active = "";
		ctkmBean.setActive(active);
		
		String khoId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kho")));
		if(khoId == null)
			khoId = "";
		ctkmBean.setkhoId(khoId);
		
		String nhom_kh_loai_tru= util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nhom_kh_loai_tru")));
		if(nhom_kh_loai_tru ==null)
			nhom_kh_loai_tru ="";
		ctkmBean.setNhom_kh_loai_tru(nhom_kh_loai_tru);
		
		String nhomkhnpp= util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nhomkhnpp")));
		if(nhomkhnpp ==null)
			nhomkhnpp ="";
		ctkmBean.setNhomkhnppId(nhomkhnpp);
		
		String[] kbhId = request.getParameterValues("kbhIds");
		String str3 = "";
		if(kbhId != null)
		{
			for(int i = 0; i < kbhId.length; i++)
				str3 += kbhId[i] + ",";
			if(str3.length() > 0)
				str3 = str3.substring(0, str3.length() - 1);
		}
		ctkmBean.setKbhIds(str3);
		String ApDUNGCHODHLE = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ApDUNGCHODHLE")));
		System.out.println("___Ap dung cho don hang le: " + ApDUNGCHODHLE);
		
		if(ApDUNGCHODHLE == null)
			ApDUNGCHODHLE = "0";
		ctkmBean.setApdungchoDHLe(ApDUNGCHODHLE);
		
		String PHANBOTHEODH = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("PHANBOTHEODH")));
		if(PHANBOTHEODH == null)
			PHANBOTHEODH = "0";
		ctkmBean.setPPhanbotheoDH(PHANBOTHEODH);
		
		
		String[] vungId = request.getParameterValues("vung");
		String str = "";
		if(vungId != null)
		{
			for(int i = 0; i < vungId.length; i++)
				str += vungId[i] + ",";
			if(str.length() > 0)
				str = str.substring(0, str.length() - 1);
		}
		ctkmBean.setVungId(str);
	
		String[] khuvucId = request.getParameterValues("khuvuc");
		String str2 = "";
		if(khuvucId != null)
		{
			for(int i = 0; i < khuvucId.length; i++)
				str2 += khuvucId[i] + ",";
			if(str2.length() > 0)
				str2 = str2.substring(0, str2.length() - 1);
		}
		ctkmBean.setKhuvucId(str2);
		
		String kbhId1 = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kbhId"));
		ctkmBean.setKbhId(kbhId1);
		
		/*String kenhbanhangId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhbanhangId")));
		if(kenhbanhangId == null)
			kenhbanhangId = "";
		ctkmBean.setKbhId(kenhbanhangId);*/
		
		String ngayds = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngayds")));
		if(ngayds == null)
			ngayds = "";
		ctkmBean.setngayds(ngayds);
		
		String ngayktds = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngayktds")));
		if(ngayktds == null)
			ngayktds = "";
		ctkmBean.setngayktds(ngayktds);
		
		String phantramtoida = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("phantramtoida")));
		if(phantramtoida == null)
			phantramtoida = "100";
		ctkmBean.setPTToida(phantramtoida);
		
		String loaikhuyenmai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("loaikhuyenmai")));
		if(loaikhuyenmai == null)
			loaikhuyenmai = "0";
		ctkmBean.setLoaikhuyenmai(loaikhuyenmai);
		
		String tilevoiprimary = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tilevoiprimary")));
		if(tilevoiprimary == null)
			tilevoiprimary = "0";
		ctkmBean.setTylevoiPrimary(tilevoiprimary);
		
		String trakmIds = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trakmIds")));
		if (trakmIds == null)
			trakmIds = "";
		ctkmBean.setTrakmId(trakmIds);
		
		String nppTuchay = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppTuchay")));
		if (nppTuchay == null)
			nppTuchay = "0";
		ctkmBean.setNppTuchay(nppTuchay);
		
		String ngansachkehoach = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngansachkehoach")));
		if (ngansachkehoach == null)
			ngansachkehoach = "0";
		ctkmBean.setNgansachkehoach(ngansachkehoach);
		
		
		String io = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("io")));
		if (io == null)
			io = "";
		ctkmBean.setIo(io);
		
		String schemeErp = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("schemeErp")));
		if (schemeErp == null)
			schemeErp = "";
		ctkmBean.setSchemeErp(schemeErp);
		
		
		
		String load = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("load")));
		ctkmBean.setload(load);
		String ngaysua = getDateTime();
    	ctkmBean.setNgaysua(ngaysua);
    	
    	String mucphanbo = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("mucphanbo")));
		if(mucphanbo == null)
			mucphanbo = "0";
		ctkmBean.setMucphanbo(mucphanbo);
		
		String apdungchoId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("apdungchoId")));
		if(apdungchoId == null)
			apdungchoId = "0";
		ctkmBean.setApdungcho(apdungchoId);
		
		String tungay_dathang = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay_dathang")==null?"":request.getParameter("tungay_dathang")));
		ctkmBean.setTuNgay_DatHang(tungay_dathang);
		
		String denngay_dathang = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay_dathang")==null?"":request.getParameter("denngay_dathang")));
		ctkmBean.setDenNgay_DatHang(denngay_dathang);
		
		
    	String[] dkkmId = request.getParameterValues("dkkmId");
		String[] dkkmDiengiai = request.getParameterValues("dkkmDiengiai");
		String[] dkkmTongluong = request.getParameterValues("dkkmTongluong");
		String[] dkkmTongtien = request.getParameterValues("dkkmTongtien");
		String[] dkkmPheptoan = request.getParameterValues("dkkmPheptoan");
		String[] dkkmThutu = request.getParameterValues("dkkmThutu");
		
		List<IDieukienkm> dkkmlist = new ArrayList<IDieukienkm>();	
		if(!type.equals("4"))
		{
			if(dkkmId != null)
			for(int i = 0; i < dkkmId.length; i++)
			{
				if(dkkmPheptoan[i] == "")
					dkkmPheptoan[i] = "2";  //or

				if(dkkmId[i].trim().length() > 0)
				{				
					Dieukienkm dkkm = new Dieukienkm(dkkmId[i], dkkmDiengiai[i], dkkmTongluong[i], dkkmTongtien[i], dkkmPheptoan[i], dkkmThutu[i]);
					dkkmlist.add(dkkm);
				}
				else
				{
					String diengiaiD = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dieukienkhuyenmai" + Integer.toString(i) + ".diengiai"));
					if(diengiaiD == null)
						diengiaiD = "";

					String loaidieukienD = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dieukienkhuyenmai" + Integer.toString(i) + ".loaidieukien"));
					if(loaidieukienD == null)
						loaidieukienD = "";

					String hinhthucD = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dieukienkhuyenmai" + Integer.toString(i) + ".hinhthuc"));
					if(hinhthucD == null)
						hinhthucD = "";

					String sotongD = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dieukienkhuyenmai" + Integer.toString(i) + ".sotong"));
					if(sotongD == null)
						sotongD = "";

					String nhomsanphamD = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dieukienkhuyenmai" + Integer.toString(i) + ".nhomsanpham"));
					if(nhomsanphamD == null)
						nhomsanphamD = "";

					if(diengiaiD.trim().length() > 0)
					{
						Dieukienkm dkkm = new Dieukienkm(dkkmId[i], dkkmDiengiai[i], dkkmTongluong[i], dkkmTongtien[i], dkkmPheptoan[i], dkkmThutu[i]);

						IDieukienDetail dkDetail = new DieukienDetail(diengiaiD, loaidieukienD, hinhthucD, sotongD, nhomsanphamD);

						String[] masanpham = request.getParameterValues("dieukienkhuyenmai" + Integer.toString(i) + ".masanpham");
						String[] tensanpham = request.getParameterValues("dieukienkhuyenmai" + Integer.toString(i) + ".tensanpham");
						String[] soluong = request.getParameterValues("dieukienkhuyenmai" + Integer.toString(i) + ".soluong");

						List<ISanpham> spList = new ArrayList<ISanpham>();
						if(masanpham != null)
						{
							ISanpham spDetai = null;
							for(int j = 0; j < masanpham.length; j++)
							{
								if(masanpham[j].length() > 0)
								{
									spDetai = new Sanpham();
									spDetai.setMasanpham(masanpham[j]);
									spDetai.setTensanpham(tensanpham[j]);
									spDetai.setSoluong(soluong[j]);

									spList.add(spDetai);

									dkDetail.setSpList(spList);
								}
							}
						}

						System.out.println("1.So san pham tao moi: " + dkDetail.getSpList().size());
						dkkm.setDieukineDetail(dkDetail);

						dkkmlist.add(dkkm);
					}
				}
			}

			System.out.println("2.So dieu kien khuyen mai: " + dkkmlist.size());
			ctkmBean.setDkkmList(dkkmlist);
		}
		if(!type.equals("4"))
		{
			//Tra khuyen mai
			String[] trakmId = request.getParameterValues("trakmId");
			String[] trakmDiengiai = request.getParameterValues("trakmDiengiai");
			String[] trakmTongluong = request.getParameterValues("trakmTongluong");
			String[] trakmTongtien = request.getParameterValues("trakmTongtien");
			String[] trakmChietkhau = request.getParameterValues("trakmChietkhau");
			String[] trakmPheptoan = request.getParameterValues("trakmPheptoan");
			String[] trakmThutu = request.getParameterValues("trakmThutu");

			List<ITrakm> trakmlist = new ArrayList<ITrakm>();	
			if(trakmId != null)
			for(int i = 0; i < trakmId.length; i++)
			{
				if(trakmPheptoan[i] == "")
					trakmPheptoan[i] = "1";  //and

				if(trakmId[i].trim().length() > 0)
				{				
					Trakm tkm = new Trakm(trakmId[i], trakmDiengiai[i], trakmTongluong[i], trakmTongtien[i], trakmChietkhau[i], trakmPheptoan[i], trakmThutu[i]);
					trakmlist.add(tkm);
				}
				else
				{
					String diengiaiD = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trakhuyenmai" + Integer.toString(i) + ".diengiai"));
					if(diengiaiD == null)
						diengiaiD = "";

					String loaidieukienD = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trakhuyenmai" + Integer.toString(i) + ".loaidieukien"));
					if(loaidieukienD == null)
						loaidieukienD = "";

					String hinhthucD = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trakhuyenmai" + Integer.toString(i) + ".hinhthuc"));
					if(hinhthucD == null)
						hinhthucD = "";

					String sotongD = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trakhuyenmai" + Integer.toString(i) + ".sotong"));
					if(sotongD == null)
						sotongD = "";

					String nhomsanphamD = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trakhuyenmai" + Integer.toString(i) + ".nhomsanpham"));
					if(nhomsanphamD == null)
						nhomsanphamD = "";

					if(diengiaiD.trim().length() > 0)
					{
						Trakm tkm = new Trakm(trakmId[i], trakmDiengiai[i], trakmTongluong[i], trakmTongtien[i], trakmChietkhau[i], trakmPheptoan[i], trakmThutu[i]);
						System.out.println("Dien giai TKM: " + trakmDiengiai[i] + "\n");

						ITrakmDetail traDetail = new TrakmDetail(diengiaiD, loaidieukienD, hinhthucD, sotongD, nhomsanphamD);

						String[] masanpham = request.getParameterValues("trakhuyenmai" + Integer.toString(i) + ".masanpham");
						String[] tensanpham = request.getParameterValues("trakhuyenmai" + Integer.toString(i) + ".tensanpham");
						String[] soluong = request.getParameterValues("trakhuyenmai" + Integer.toString(i) + ".soluong");

						List<ISanpham> spList = new ArrayList<ISanpham>();
						if(masanpham != null)
						{
							ISanpham spDetai = null;
							for(int j = 0; j < masanpham.length; j++)
							{
								if(masanpham[j].length() > 0)
								{
									spDetai = new Sanpham();
									spDetai.setMasanpham(masanpham[j]);
									spDetai.setTensanpham(tensanpham[j]);
									spDetai.setSoluong(soluong[j]);

									spList.add(spDetai);

									traDetail.setSpList(spList);
								}
							}
						}

						System.out.println("22.So san pham tra khuyen mai tao moi: " + traDetail.getSpList().size());
						tkm.setTraDetail(traDetail);

						trakmlist.add(tkm);
					}
				}
			}

			System.out.println("2.So tra khuyen mai: " + trakmlist.size());
			ctkmBean.setTrakmList(trakmlist);
		}
		
		String[] npp = request.getParameterValues("npp");
		ctkmBean.setNpp(npp);

 		if(action.equals("save"))
 		{    
    		if (id == null || id.trim().length()==0)
    		{
    			if (!ctkmBean.CreateCtkm()){
    		    	ctkmBean.setUserId(userId);
    		    	ctkmBean.createRS();
    		    	session.setAttribute("userId", userId);
    				session.setAttribute("ctkmBean", ctkmBean);
    				System.out.println(ctkmBean.getMessage());
    				
    				String nextJSP = "/AHF/pages/Center/ChuongTrinhKhuyenMaiNew.jsp";
    				response.sendRedirect(nextJSP);
    			}
    			else
    			{
    				ICtkhuyenmaiList obj = new CtkhuyenmaiList();
    				obj.setUserId(userId);
    				obj.init("");
    				session.setAttribute("obj", obj);
    					
    				response.sendRedirect("/AHF/pages/Center/ChuongTrinhKhuyenMai.jsp");	    	
    			 }	
    		}
    		else
    		{
    			if (!(ctkmBean.UpdateCtkm()))
    			{			
    		    	ctkmBean.setUserId(userId);
    		    	ctkmBean.createRS();
    		    	
    		    	session.setAttribute("userId", userId);
    				session.setAttribute("ctkmBean", ctkmBean);
    				
    				String nextJSP = "/AHF/pages/Center/ChuongTrinhKhuyenMaiUpdate.jsp";
    				response.sendRedirect(nextJSP);
    			}
			else
			{
				ICtkhuyenmaiList obj = new CtkhuyenmaiList();
				obj.setUserId(userId);
				obj.init("");
			
				session.setAttribute("obj", obj);
				session.setAttribute("userId", userId);
	    		
				response.sendRedirect("/AHF/pages/Center/ChuongTrinhKhuyenMai.jsp");
			}
    		}
	    }
		else
		{
			ctkmBean.createRS();		
			session.setAttribute("userId", userId);
			session.setAttribute("ctkmBean", ctkmBean);
			
			String nextJSP;
			if (id == null){
				nextJSP = "/AHF/pages/Center/ChuongTrinhKhuyenMaiNew.jsp";
			}
			else{
				nextJSP = "/AHF/pages/Center/ChuongTrinhKhuyenMaiUpdate.jsp";   						
			}
			response.sendRedirect(nextJSP);
		}
	}
	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
}
