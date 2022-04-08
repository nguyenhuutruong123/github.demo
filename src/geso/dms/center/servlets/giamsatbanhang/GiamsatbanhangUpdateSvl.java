package geso.dms.center.servlets.giamsatbanhang;

import geso.dms.center.beans.giamsatbanhang.imp.*;
import geso.dms.center.beans.giamsatbanhang.*;
import geso.dms.center.util.Utility;

import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

public class GiamsatbanhangUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	private PrintWriter out;

	public GiamsatbanhangUpdateSvl()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		System.out.println("XXXXXXXXXXXXXX");
		this.out = response.getWriter();
		Utility util = new Utility();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);

		out.println(userId);

		if (userId.length() == 0)
			userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));

		String id = util.getId(querystring);

		IGiamsatbanhang gsbhBean = new Giamsatbanhang(id);
		gsbhBean.setUserId(userId);

		session.setAttribute("gsbhBean", gsbhBean);
		String nextJSP = "/AHF/pages/Center/GiamSatBanHangUpdate.jsp";
		if (querystring.indexOf("display") > 0)
			nextJSP = "/AHF/pages/Center/GiamSatBanHangDisplay.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IGiamsatbanhang gsbhBean;
		this.out = response.getWriter();
		String contentType = request.getContentType();
		String fileName = "";
		String id = "";
		String userId = "";
		String MaGSBH = "";
		String TenGSBH = "";
		String trangthai = "";
		String DiaChi = "";

		String DienThoai = "";
		String Email = "";
		String NCC = "";
		String TTPP = "";
		String dvkdId = "";
		String KenhBH = "";
		String vungId = "";
		String kvId = "";
		String[] kvIds = new String[]
		{ "" };

		String cmnd = "";
		String ngaysinh = "";
		String quequan = "";
		String thuviec = "";
		String nganhang = "";

		String NganHangId = "";
		String ChiNhanhId = "";
		String sotaikhoan = "";
		String chutaikhoan = "";
		String ngaybatdau = "";
		String ngayketthuc = "";
		String action = "";
		String filename = "";
		String gsbhTnId = "";
		String nppId ="";
		
		String tendangnhap ="";
		String matkhau ="";
		String diaban_fk ="";
		String asmId = "";
		Utility util = new Utility();
		if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0))
		{
			String filePath = getServletContext().getInitParameter("path") + "\\images\\";
			MultipartRequest multi = new MultipartRequest(request, filePath, 20000000, "UTF-8");

			Enumeration files = multi.getFileNames();
			String filenameu = "";
			while (files.hasMoreElements())
			{
				String name = (String) files.nextElement();
				filenameu = multi.getFilesystemName(name);
				System.out.println("name  " + filenameu);
				;
			}

			id = util.antiSQLInspection(multi.getParameter("id"));
			userId = util.antiSQLInspection(multi.getParameter("userId"));

			TenGSBH = util.antiSQLInspection(multi.getParameter("TenGSBH"));
			trangthai = util.antiSQLInspection(multi.getParameter("trangthai"));
			
			DiaChi = util.antiSQLInspection(multi.getParameter("DiaChi"));

			DienThoai = util.antiSQLInspection(multi.getParameter("DienThoai"));
			Email = util.antiSQLInspection(multi.getParameter("Email"));
			NCC = util.antiSQLInspection(multi.getParameter("NCC"));
			//TTPP =  util.antiSQLInspection(multi.getParameter("TTPP"));
			dvkdId = util.antiSQLInspection(multi.getParameter("dvkdId"));
			KenhBH = util.antiSQLInspection(multi.getParameter("KenhBH"));
			

			vungId =  util.Doisangchuoi(multi.getParameterValues("vung"));
			kvId = util.Doisangchuoi(multi.getParameterValues("khuvuc"));
			
			nppId =util.Doisangchuoi(multi.getParameterValues("nppId"));
			
			kvIds = multi.getParameterValues("kvIds");

			cmnd = util.antiSQLInspection(multi.getParameter("cmnd"));
			ngaysinh = util.antiSQLInspection(multi.getParameter("ngaysinh"));
			quequan = util.antiSQLInspection(multi.getParameter("quequan"));
			thuviec = util.antiSQLInspection(multi.getParameter("thuviec"));
			nganhang = util.antiSQLInspection(multi.getParameter("nganhang"));
			
			ngaybatdau = util.antiSQLInspection(multi.getParameter("ngaybatdau"));
			ngayketthuc = util.antiSQLInspection(multi.getParameter("ngayketthuc"));
			
			NganHangId = util.antiSQLInspection(multi.getParameter("NganHangId"));
			ChiNhanhId = util.antiSQLInspection(multi.getParameter("ChiNhanhId"));
			sotaikhoan = util.antiSQLInspection(multi.getParameter("sotaikhoan"));
			chutaikhoan = util.antiSQLInspection(multi.getParameter("chutaikhoan"));
			gsbhTnId = util.antiSQLInspection(multi.getParameter("gsbhTnId"));
			
			
			tendangnhap = util.antiSQLInspection(multi.getParameter("tendangnhap"));
			if(tendangnhap == null) tendangnhap = "";
			matkhau = util.antiSQLInspection(multi.getParameter("matkhau"));
			if(matkhau == null) matkhau = "";
			diaban_fk = util.antiSQLInspection(multi.getParameter("diaban_fk"));
			asmId = util.antiSQLInspection(multi.getParameter("asmId"));
			action = multi.getParameter("action");

			filename = filenameu;

		} else
		{
			id = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("id")));
			userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
			MaGSBH = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("MaGSBH"));
			if(MaGSBH == null)
				MaGSBH = "";
			TenGSBH = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("TenGSBH")));
			trangthai = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai"));
			DiaChi = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("DiaChi")));

			DienThoai = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("DienThoai")));
			Email = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("Email")));
			NCC = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("NCC")));
			//TTPP =  util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("TTPP")));
			dvkdId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdId")));
			KenhBH = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("KenhBH")));
			
			vungId =  util.Doisangchuoi(request.getParameterValues("vung"));
			kvId = util.Doisangchuoi(request.getParameterValues("khuvuc"));
			
			nppId =util.Doisangchuoi(request.getParameterValues("nppId"));
			
			cmnd = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("cmnd")));
			ngaysinh = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngaysinh")));
			quequan = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("quequan")));
			thuviec = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("thuviec")));
			nganhang = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nganhang")));
			ngaybatdau = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngaybatdau")));
			ngayketthuc = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngayketthuc")));
			
			
			NganHangId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("NganHangId")));
			ChiNhanhId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ChiNhanhId")));
			sotaikhoan = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("sotaikhoan")));
			chutaikhoan = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("chutaikhoan")));
			
			
			gsbhTnId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("gsbhTnId")));
			
			tendangnhap = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tendangnhap")));
			if(tendangnhap == null) tendangnhap = "";
			matkhau = (geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("matkhau")));
			if(matkhau == null) matkhau = "";
			
			diaban_fk = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("diaban_fk")));
			asmId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("asmId")));
			
			System.out.println("[gsbhTnId]"+gsbhTnId);
			
			action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));

			filename = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("fileName"));

		}

		System.out.println("GiamsatbanhangUpdateSvl: filename = " + filename);

		if (id == null)
		{
			gsbhBean = new Giamsatbanhang("");
		} else
		{
			gsbhBean = new Giamsatbanhang(id);
		}

		if(asmId == null) asmId = "";
		gsbhBean.setAsmId(asmId);
		
		gsbhBean.setHinhanh(filename);

		gsbhBean.setUserId(userId);
		session.setAttribute("userId", userId);

		if (TenGSBH == null)
			TenGSBH = "";
		gsbhBean.setTen(TenGSBH);

		System.out.println("GSBH Ten = " + TenGSBH);
		System.out.println("trangthai1 = "+ trangthai);
		
		if (trangthai == null)
			trangthai = "0";
		else
			trangthai = "1";
		gsbhBean.setTrangthai(trangthai);

		System.out.println("trangthai2 = "+ trangthai);
		
		if (DiaChi == null)
			DiaChi = "";
		gsbhBean.setDiachi(DiaChi);

		if (DienThoai == null)
			DienThoai = "";
		gsbhBean.setSodienthoai(DienThoai);

		if (Email == null)
			Email = "";
		gsbhBean.setEmail(Email);

		if (NCC == null)
			NCC = "";
		gsbhBean.setNccId(NCC);

		if (TTPP == null)
			TTPP = "";
		gsbhBean.setTtppId(TTPP);
		
		if (dvkdId == null)
			dvkdId = "";
		gsbhBean.setDvkdId(dvkdId);

		if (KenhBH == null)
			KenhBH = "";
		gsbhBean.setKbhId(KenhBH);

		if (vungId == null)
			vungId = "";
		gsbhBean.setVungId(vungId);
		
		if (ngaybatdau == null)
			ngaybatdau = "";
		System.out.println("nha phan phoiid: " + nppId);
		System.out.println("ngay bat dau: " + ngaybatdau);
		System.out.println("ngay ket thuc: " + ngayketthuc);
		gsbhBean.setNgaybatdau(ngaybatdau);
		
		if (ngayketthuc == null)
			ngayketthuc = "";
		gsbhBean.setNgayketthuc(ngayketthuc);

		if (kvId == null)
			kvId = "";
		gsbhBean.setKvId(kvId);

		// lay khuvuc ids

		if (cmnd == null)
			cmnd = "";
		gsbhBean.setCmnd(cmnd);

		if (ngaysinh == null)
			ngaysinh = "";
		gsbhBean.setNgaysinh(ngaysinh);

		if (quequan == null)
			quequan = "";
		gsbhBean.setQuequan(quequan);

		if (thuviec == null)
			thuviec = "0";
		else
			thuviec = "1";

		gsbhBean.setThuviec(thuviec);

		if (nganhang == null)
			nganhang = "";
		gsbhBean.setNganHang(nganhang);

		if (NganHangId == null || NganHangId.trim().length() == 0)
			NganHangId = "";
		gsbhBean.setNganHangId(NganHangId);

		if (ChiNhanhId == null || ChiNhanhId.trim().length() == 0)
			ChiNhanhId = "";
		gsbhBean.setChiNhanhId(ChiNhanhId);

		if (sotaikhoan == null)
			sotaikhoan = "";
		gsbhBean.setSoTaiKhoan(sotaikhoan);

		if (chutaikhoan == null)
			chutaikhoan = "";
		gsbhBean.setChuTaiKhoan(chutaikhoan);

		System.out.println("[GSBHTN]" + gsbhTnId);
		if (gsbhTnId == null)
			gsbhTnId = "";
		gsbhBean.setGsbhTnId(gsbhTnId);
		
		if (diaban_fk == null)
			diaban_fk = "";
		gsbhBean.setDiaban_fk(diaban_fk);
		
		gsbhBean.setNppIds(nppId);
		System.out.println("[nppId]"+nppId);

		String ngaysua = getDateTime();
		gsbhBean.setNgaysua(ngaysua);

		String nguoisua = userId;
		gsbhBean.setNguoisua(nguoisua);
		
		
		gsbhBean.setTenDangNhap(tendangnhap);
		gsbhBean.setMatKhau(matkhau);
		System.out.print("aaaaaaaaaaaaaaaaaaaaaa"+matkhau);
		
		System.out.println("[TenDangNhap]"+tendangnhap+"[MatKhau]"+matkhau);
		
		boolean error = false;
		
		if (TenGSBH.trim().length() == 0)
		{
			gsbhBean.setMessage("Vui lòng nhập tên giám sát bán hàng");
			error = true;
		}
		
		if(MaGSBH.trim().length() == 0) {
			gsbhBean.setMessage("Vui lòng nhập mã của Giám sát bán hàng");
			error = true;
		}

		if (NCC.trim().length() == 0)
		{
			gsbhBean.setMessage("Vui lòng chọn nhà cung cấp");
			error = true;
		}
		
		
		
		gsbhBean.setSmartId(MaGSBH);
/*		if (TTPP.trim().length() == 0)
		{
			gsbhBean.setMessage("Vui lòng chọn trung tâm phân phối");
			error = true;
		}*/

		if (dvkdId.trim().length() == 0)
		{
			gsbhBean.setMessage("Vui lòng chọn đơn vị kinh doanh");
			error = true;
		}
		if(!dvkdId.equals("100069"))
		if (kvId.trim().length() == 0)
		{
			gsbhBean.setMessage("Vui lòng chọn khu vực quản lý");
			error = true;
		}
		
		/*if(!dvkdId.equals("100069"))
		if(tendangnhap.trim().length() == 0)
		{
			gsbhBean.setMessage("Vui lòng nhập tên đăng nhập");
			error = true;
		}
		*/
		
		if(DienThoai.trim().length() == 0)
		{
			gsbhBean.setMessage("Vui lòng nhập điện thoại");
			error = true;
		}
		
		if(KenhBH.trim().length() == 0){
			gsbhBean.setMessage("Vui lòng nhập kênh bán hàng");
			error = true;
		}
		
		if(DiaChi.trim().length() == 0){
			gsbhBean.setMessage("Vui lòng nhập địa chỉ");
			error = true;
		}
		
		String nextJSP = "";
		String[] nppIds = request.getParameterValues("nppId");
		String[] nppMas = request.getParameterValues("nppMa");
		String[] nppTens = request.getParameterValues("nppTen");
		String[] ngaybatdau1 = new String[nppIds.length];
		String[] ngayketthuc1 = new String[nppIds.length];
		if(nppId != null)
		{
			int n=0;
			int i= 0;
			int size=nppIds.length;
			while(n < size)
			{
				
				String ngaybatdaucu=request.getParameter("ngaybatdau"+i)==null?"":request.getParameter("ngaybatdau"+i).trim();
				String ngayketthuccu=request.getParameter("ngayketthuc"+i)==null?"":request.getParameter("ngayketthuc"+i).trim();
				ngaybatdau1[n]=ngaybatdaucu;
				ngayketthuc1[n]=ngayketthuccu;
				n++;
				i++;
			}
		}
		String queryngaycu="";
		String nppidcu="";
		int check=0;
		for(int i=0;i<nppIds.length;i++){
			if(ngaybatdau1[i].trim().length()>0 && ngayketthuc1[i].trim().length()>0){
				queryngaycu+="\n union ";
				nppidcu+=nppIds[i]  +",";
				
				String tenvung="\n select top(1)  v.ten FROM NHAPHANPHOI A   "+
		                      " \n left join nhapp_giamsatbh b on a.pk_seq = b.npp_fk    "+
		                      " \n left join khuvuc kv on kv.pk_Seq=A.khuvuc_Fk    "		+
		                      " \nleft join vung v on v.pk_Seq=kv.vung_fk  WHERE 1=1   "+
		                      "\n and a.pk_seq='"+nppIds[i]+"'"; 
				String tenkhuvuc="\n select top(1)  kv.ten FROM NHAPHANPHOI A   "+
	                      "\n  left join nhapp_giamsatbh b on a.pk_seq = b.npp_fk    "+
	                      "\n  left join khuvuc kv on kv.pk_Seq=A.khuvuc_Fk    "		+
	                      "\n left join vung v on v.pk_Seq=kv.vung_fk  WHERE 1=1   "+
	                      "\n and a.pk_seq='"+nppIds[i]+"'"; 
			queryngaycu+= "\n select ("+tenvung+") as vTen,("+tenkhuvuc+") as kvten,N'"+nppIds[i]+"',N'"+nppMas[i]+"',N'"+nppTens[i]+"','"+ngaybatdau1[i]+"','"+ngayketthuc1[i]+"'";
			}
		}
		if(nppidcu.length()>0){
			gsbhBean.setNppIdcu(nppidcu.substring(0,nppidcu.length()-1));
		}
		System.out.print("queryngaycu"+queryngaycu);
		gsbhBean.setQuerycu(queryngaycu);
		IGiamsatbanhangList obj = new GiamsatbanhangList();
		if (!error)
		{
			if (action.equals("save"))
			{
				if (id == null || id.trim().length()==0)
				{
					if (!(gsbhBean.CreateGsbh( request)))
					{
						gsbhBean.setUserId(userId);
						session.setAttribute("obj", obj);
						session.setAttribute("gsbhBean", gsbhBean);
						nextJSP = "/AHF/pages/Center/GiamSatBanHangNew.jsp";
					} else
					{
						obj.setUserId(userId);
						obj.init("");
						obj.setCrrSplitting(obj.getTheLastSplitting() <= 15 ? obj.getTheLastSplitting() : 15);
						session.setAttribute("userId", userId);
						session.setAttribute("obj", obj);
						nextJSP = "/AHF/pages/Center/GiamSatBanHang.jsp";
					}

				} else
				{
					if (!(gsbhBean.UpdateGsbh(request)))
					{
						session.setAttribute("gsbhBean", gsbhBean);
						session.setAttribute("obj", obj);
						nextJSP = "/AHF/pages/Center/GiamSatBanHangUpdate.jsp";

					} else
					{
						obj.setUserId(userId);
						obj.init("");
						obj.setCrrSplitting(obj.getTheLastSplitting() <= 15 ? obj.getTheLastSplitting() : 15);
						session.setAttribute("obj", obj);
						nextJSP = "/AHF/pages/Center/GiamSatBanHang.jsp";

					}
				}
			} else
			{
				/*if(id.length() > 0) gsbhBean.init();
				else */
					gsbhBean.createRS();
				gsbhBean.setUserId(userId);
				session.setAttribute("gsbhBean", gsbhBean);
				if (id == null) {
					nextJSP = "/AHF/pages/Center/GiamSatBanHangNew.jsp";
				} else {
					nextJSP = "/AHF/pages/Center/GiamSatBanHangUpdate.jsp";
				}

			}
		} else
		{
			if( id != null && id.length() > 0) gsbhBean.init();
			else gsbhBean.createRS();
			gsbhBean.setUserId(userId);
			session.setAttribute("gsbhBean", gsbhBean);

			if (id == null) {
				nextJSP = "/AHF/pages/Center/GiamSatBanHangNew.jsp";
			} else {
				nextJSP = "/AHF/pages/Center/GiamSatBanHangUpdate.jsp";
			}
		}
		response.sendRedirect(nextJSP);
	}

	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	private String getDateTime2()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS");
		Date date = new Date();
		return dateFormat.format(date);
	}

	private String SaveFile(HttpServletRequest request)
	{
		try
		{
			HttpSession session = request.getSession();
			Utility util = new Utility();
			String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));
			session.setAttribute("userId", userId);
			String filePath = getServletContext().getInitParameter("path") + "\\images\\";

			/*
			 * String fileA = (String)geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("file_upload"));
			 * if(fileA == null) fileA = "NA"; System.out.println("Ten FILE: " +
			 * fileA);
			 */
			String contentType = request.getContentType();

			String fileName = getDateTime2().replaceAll(":", "-").replaceAll(" ", "_") + ".jpg";

			DataInputStream in = new DataInputStream(request.getInputStream());

			int formDataLength = request.getContentLength();
			byte dataBytes[] = new byte[formDataLength];
			int byteRead = 0;
			int totalBytesRead = 0;

			System.out.println("Do dai lay duoc: " + formDataLength);

			while (totalBytesRead < formDataLength)
			{
				byteRead = in.read(dataBytes, totalBytesRead, formDataLength);
				totalBytesRead += byteRead;
			}

			String file = new String(dataBytes);

			System.out.println("1.Chay toi day ");

			// file = contents[0];

			// for(int p = 0; p < contents.length; p ++) {
			// System.out.println("--------asdasdasdasdasdasd ::::: " +
			// contents[0]);
			// }
			System.out.println("[File]" + file);

			String saveFile = file.substring(file.indexOf("filename=\"") + 10);
			System.out.println("1.Chay toi day ");
			saveFile = saveFile.substring(0, saveFile.indexOf("\n"));
			saveFile = saveFile.substring(saveFile.lastIndexOf("\\") + 1, saveFile.indexOf("\""));
			System.out.println("2.Chay toi day ");
			int lastIndex = contentType.lastIndexOf("=");
			String boundary = contentType.substring(lastIndex + 1, contentType.length());
			int pos;
			// extracting the index of file
			pos = file.indexOf("filename=\"");
			pos = file.indexOf("\n", pos) + 1;
			pos = file.indexOf("\n", pos) + 1;
			pos = file.indexOf("\n", pos) + 1;
			System.out.println("3.Chay toi day ");
			int boundaryLocation = file.indexOf(boundary, pos) - 4;
			int startPos = ((file.substring(0, pos)).getBytes()).length;
			int endPos = ((file.substring(0, boundaryLocation)).getBytes()).length;
			System.out.println("4.Chay toi day ");

			if (file.length() > 0)
			{
				FileOutputStream fileOut = new FileOutputStream(filePath + fileName);
				fileOut.write(dataBytes, startPos, (endPos - startPos));
				fileOut.flush();
				fileOut.close();

				System.out.println("5.Chay toi day");

				return fileName;
			} else
			{
				return "";
			}
		} catch (Exception e)
		{

			System.out.println("__Exception: " + e.getMessage());
			e.printStackTrace();
			return e.getMessage();
		}

	}

}
