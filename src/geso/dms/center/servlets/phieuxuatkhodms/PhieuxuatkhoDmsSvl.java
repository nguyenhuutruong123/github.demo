package geso.dms.center.servlets.phieuxuatkhodms;

import geso.dms.center.beans.phieuxuatkhodms.IPhieuxuatkhoDms;
import geso.dms.center.beans.phieuxuatkhodms.IPhieuxuatkhoDmsList;
import geso.dms.center.beans.phieuxuatkhodms.imp.PhieuxuatkhoDms;
import geso.dms.center.beans.phieuxuatkhodms.imp.PhieuxuatkhoDmsList;
import geso.dms.center.db.sql.db_Sync;
import geso.dms.center.db.sql.dbutils;

import geso.dms.center.util.Utility;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import oracle.sql.LxMetaData;

import com.google.gson.Gson;

public class PhieuxuatkhoDmsSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

	public PhieuxuatkhoDmsSvl() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IPhieuxuatkhoDmsList obj;
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");        

		HttpSession session = request.getSession();	    
		Utility util = new Utility();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);


		if (userId.length()==0)
			userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));

		String action = util.getAction(querystring);

		String lsxId = util.getId(querystring);
		obj = new PhieuxuatkhoDmsList();			    	

		String loaidonhang = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("loaidonhang"));
		if(loaidonhang == null)
			loaidonhang = "0";
		obj.setLoaidonhang(loaidonhang);
		System.out.println("---LOAI DON HANG: " + loaidonhang);

		if (action.equals("delete") )
		{	
			String msg = this.DeleteChuyenKho(lsxId);
			obj.setMsg(msg);
		}
		else
		{
			if(action.equals("chot"))
			{
				String msg = this.Chot(lsxId,userId);
				System.out.println("in ra câu"+msg);
				obj.setMsg(msg);
				
			}
			
			//GIU NGUYEN TIM KIEM		   		   
			String tungaytk = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay")));
			System.out.println("tungay : "+tungaytk);
			if(tungaytk == null)
				tungaytk = "";	    	   	    
			obj.setTungay(tungaytk);

			String denngaytk = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay")));

			if(denngaytk == null)
				denngaytk = "";	    	   	    
			obj.setDenngay(denngaytk);

			String vungtk = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId")));
			if(vungtk == null)
				vungtk = "";	    	   	    
			obj.setVungId(vungtk);

			String khuvuctk = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId")));
			if(khuvuctk == null)
				khuvuctk = "";	    	   	    
			obj.setKhuvucId(khuvuctk);

			String npptk = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));
			System.out.println("nppId : "+npptk);
			if(npptk == null)
				npptk = "";	    	   	    
			obj.setNppTen(npptk);

			String kbhtk = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhId")));
			if(kbhtk == null)
				kbhtk = "";	    	   	    
			obj.setKbhId(kbhtk);		    		   

			String trangthaitk = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai")));		    
			if(trangthaitk == null)
				trangthaitk = "";	    	   	    
			obj.setTrangthai(trangthaitk);		    		  

			//--------------------------------------------------------
		}
		String type = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("type")==null?"":request.getParameter("type")));
		System.out.println("type : "+type);
		if(type.equals("GetDonGia"))
		{
			NumberFormat formatter = new DecimalFormat("#,###,###.####");
			NumberFormat formatter1 = new DecimalFormat("#,###,###");
			Gson gson = new Gson();

			String spMa = "";
			String dvdlId ="";
			String nppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));

			String kbhId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kbhId")));  			

			String dvkdId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dvkdId")));

			String query = (String)request.getQueryString();
			spMa = new String(query.substring(query.indexOf("&spMa=") + 6, query.indexOf("&dvdlId=")).getBytes("UTF-8"), "UTF-8");
			spMa = URLDecoder.decode(spMa, "UTF-8").replace("+", " ");

			dvdlId = new String(query.substring(query.indexOf("&dvdlId=") + 8, query.indexOf("&nppId=")).getBytes("UTF-8"), "UTF-8");
			dvdlId = URLDecoder.decode(dvdlId, "UTF-8").replace("+", " ");

			dbutils db = new dbutils();

			query = 
					" select a.DVDL_FK as dvCHUAN, ( select PK_SEQ from DONVIDOLUONG where DONVI = N'" + dvdlId + "' ) as dvNEW, " + 
					"	case when a.DVDL_FK =( select PK_SEQ from DONVIDOLUONG where DONVI = N'" + dvdlId + "' ) then 1  "+
					"	else (select soluong1/soluong2 from QUYCACH where SANPHAM_FK=a.PK_SEQ and DVDL1_FK=a.DVDL_FK and DVDL2_FK=  "+
					"		( select PK_SEQ from DONVIDOLUONG where DONVI = N'" + dvdlId + "' ) ) end as TyLe,  "+
					"	(select soluong1/soluong2 from QUYCACH where SANPHAM_FK=a.PK_SEQ and DVDL1_FK=a.DVDL_FK and DVDL2_FK=  "+
					"	( select PK_SEQ from DONVIDOLUONG where DONVI =  N'" + dvdlId + "' ) ) as QuyCach_THG ,a.TRONGLUONG,a.THETICH, " +  
					" isnull( ( select GIAMUANPP * ( 1 - isnull( ( select chietkhau from BANGGIAMUANPP_NPP where banggiamuaNPP_FK = bg_sp.bgmuaNPP_FK and NPP_FK = '"+nppId+"' ), 0) / 100 )  				" +
					"			from BGMUANPP_SANPHAM bg_sp 			    where SANPHAM_FK = a.pk_seq  " +
					"							and BGMUANPP_FK in ( select top(1) PK_SEQ from BANGGIAMUANPP bg inner join BANGGIAMUANPP_NPP bg_npp on bg.PK_SEQ = bg_npp.BANGGIAMUANPP_FK where bg.TRANGTHAI = '1'" +
					" 							and bg_npp.NPP_FK = '"+nppId+"' and bg.DVKD_FK = '"+dvkdId+"' and bg.KENH_FK = '"+kbhId+"' order by bg.TUNGAY desc ) ), 0) as GiaGoc, "+
					"	isnull( ( select GIAMUANPP * ( 1 - (isnull( ( select chietkhau from BANGGIAMUANPP_NPP "+
					"	where banggiamuaNPP_FK = bg_sp.bgmuaNPP_FK and NPP_FK = '"+nppId+"' ), 0) + isnull(bg_sp.ptChietKhau, 0)) / 100 )  "+ 							
					"	from BGMUANPP_SANPHAM bg_sp where SANPHAM_FK = a.pk_seq  "+							
					"	and BGMUANPP_FK in ( select top(1) PK_SEQ from BANGGIAMUANPP bg inner join BANGGIAMUANPP_NPP bg_npp on bg.PK_SEQ = bg_npp.BANGGIAMUANPP_FK "+ 
					"	where bg.TRANGTHAI = '1' and bg_npp.NPP_FK = '"+nppId+"' and bg.DVKD_FK = '"+dvkdId+"' and bg.KENH_FK = '"+kbhId+"' order by bg.TUNGAY desc ) ), 0) as GiaMua "+
					" from SANPHAM a where a.MA = '" + spMa + "'  ";

			System.out.println("[Sql]"+query);

			ResultSet rs = db.get(query);
			double TheTich =0;		
			double TrongLuong =0;
			double DonGia =0;		
			double GiaGoc = 0;
			double QuyCach=0;
			double TyLe = 0;


			if(rs != null)
			{
				try 
				{
					if(rs.next())
					{
						TheTich=rs.getDouble("thetich");
						TrongLuong= rs.getDouble("trongluong");
						DonGia =rs.getDouble("giamua");
						GiaGoc =rs.getDouble("giagoc");
						QuyCach = rs.getDouble("QuyCach_THG");
						TyLe = rs.getDouble("TyLe");

						SanPham sp = new SanPham();
						sp.setDongia( formatter1.format((DonGia*TyLe)/1.1));
						sp.setGiagoc( formatter.format(GiaGoc*TyLe));
						sp.setTrongluong(formatter.format(TrongLuong*TyLe));
						sp.setThetich(formatter.format(TheTich*TyLe));
						sp.setQuycach(formatter.format(QuyCach) );
						response.setContentType("application/json");
						response.setCharacterEncoding("UTF-8");
						response.getWriter().write(gson.toJson(sp));

					}
					rs.close();
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
				finally
				{
					if(db!=null)db.shutDown();
				}
			}
			return;
		}
		obj.setUserId(userId);
		String search = "";
		if(action.equals("chot") || action.equals("delete"))
		{
			search = getSearchQuery(request, obj);				 
		}

		obj.init(search);		   		   
		session.setAttribute("obj", obj);

		String nextJSP = "/AHF/pages/Center/PhieuxuatkhoDms.jsp";

		response.sendRedirect(nextJSP);
	}
	
	private String Chot(String lsxId,String userId) 
	{
		dbutils db = new dbutils();

		String msg = "";
		String query="";
		ResultSet rs;
		try
		{
			db.getConnection().setAutoCommit(false);
			query = "update phieuxuatkhodms set trangthai = 0 where trangthai = 0 and pk_seq = "+lsxId;
			System.out.println("[phieuxuatkhodms] "+query);
			if(db.updateReturnInt(query) <= 0)
			{
				db.getConnection().rollback();
				return "Vui lòng kiểm tra lại, trạng thái không hợp lệ";
				
			}
			//Những HD có thể tạo nhận hàng cho npp.
			query=
					"select a.PK_SEQ, a.trangthai, a.ngaychungtu, a.soso, a.sopo, a.sochungtu, a.npp_fk, a.dvkd_fk, a.kbh_fk, "+
					"	'100368' as nguoitao, '100368' as nguoisua  "+
					"from phieuxuatkhodms a where a.trangthai = 0 and a.pk_seq = "+lsxId;
			System.out.println("[pxk] "+query);
			rs = db.get(query);

				db.getConnection().setAutoCommit(false);
				while(rs.next())
				{
					int flag = 0;
					String ngaytra = rs.getString("ngaychungtu");
					String nguoitao = rs.getString("nguoitao");
					String nguoisua = rs.getString("nguoisua");
					String po = rs.getString("sopo");
					int Trangthai = 0;
					String npp_fk = rs.getString("npp_fk");
					String ncc_fk = "100022";
					String so = rs.getString("soso");
					String sochungtu = rs.getString("sochungtu");
					String dvkd_fk = rs.getString("dvkd_fk");
					String kbh_fk = rs.getString("kbh_fk");
					
					if((po != null && po.trim().length() > 0))
					{
						query = "select  (select count(*) as sl from nhaphang where chungtu = '"+sochungtu+"'  and trangthai <> 2) as sl, "+
							  "(select pk_seq from nhaphang where chungtu = '"+sochungtu+"' and trangthai <> 2 ) as pk_seq,(select chungtu from nhaphang where chungtu = '"+sochungtu+"' and trangthai <> 2 ) as so";
						System.out.println("[check sochungtu] "+query);
						ResultSet rscheck=db.get(query);
						
						rscheck.next();
						int check=rscheck.getInt("sl");
						String socheck=rscheck.getString("so");
						String nhaphang_fk=rscheck.getString("pk_seq");
						if(check==0)
						{
							query = 
								"insert into nhaphang(ngaynhan, ngaychungtu, nguoitao, nguoisua, trangthai, npp_fk, ncc_fk, chungtu, hdtaichinh, SO_NUMBER, "+
								"dondathang_fk, ngaytao, ngaysua, created_date, dvkd_fk, kbh_fk, isdms) "+
								"values ('"+ngaytra+"', '"+ngaytra+"', '"+nguoitao+"', '"+nguoisua+"', "+Trangthai+", '"+npp_fk+"', '"+ncc_fk+"', '"+sochungtu+"', "+
								"'"+sochungtu+"','"+so+"', "+po+", '"+getDateTime()+"', '"+getDateTime()+"', dbo.GetLocalDate(DEFAULT), '"+dvkd_fk+"', '"+kbh_fk+"', 1)";
						}
						else
						{
							flag=1;
							
							/*query = 
								"update nhaphang set trangthai = "+Trangthai+", ngaynhan ='"+ngaytra+"', ngaychungtu = '"+ngaytra+"', ngaysua = '"+getDateTime()+"', created_date = dbo.GetLocalDate(DEFAULT), "+
								"dvkd_fk = '"+dvkd_fk+"', kbh_fk = '"+kbh_fk+"', npp_fk = '"+npp_fk+"', chungtu='"+sochungtu+"', hdtaichinh='"+sochungtu+"' where chungtu = '"+sochungtu+"' and isdms = 1";
				*/		}
						rscheck.close();
						System.out.println("[nhaphang] "+query);
						if(db.updateReturnInt(query)<=0)
						{
							flag=1;
						}
						if(check==0)
						{
							query="select scope_identity() as dhid";
							ResultSet rs11=db.get(query);
							rs11.next();
							nhaphang_fk=rs11.getString("dhid");
							rs11.close();
						}
						else
						{
							query="delete from nhaphang_sp where nhaphang_fk="+nhaphang_fk;
							if(db.updateReturnInt(query)<=0)
							{
								flag=1;
							}
						}
						
						if(flag==0)
						{
							query = "select a.*, b.ma as masanpham, c.donvi from PHIEUXUATKHODMS_SANPHAM a inner join sanpham b on a.sanpham_fk = b.pk_seq "+
									"inner join donvidoluong c on a.dvdl_fk = c.pk_seq where phieuxuatkho_fk = "+lsxId;
							ResultSet rs1 = db.get(query);
							while(rs1.next())
							{
								String soluong = rs1.getString("soluong");
								double soluongnhan = 0;
								String masanpham = rs1.getString("masanpham");
								String sanpham_fk = rs1.getString("sanpham_fk");
								String dvdl_fk = rs1.getString("dvdl_fk");
								String donvi = rs1.getString("donvi");
								double dongia = rs1.getDouble("dongia");
								double vat = rs1.getDouble("vat");
								double thanhtien = 0;
								String scheme = rs1.getString("scheme")==null?"":rs1.getString("scheme");
								String kho_fk = "100000";
								thanhtien = Double.parseDouble(soluong) * dongia;
								
								query = "select isnull(d.donvi, '') dvdl1, isnull(soluong1,0) soluong1, isnull(c.donvi, '') dvdl2, isnull(soluong2, 0) soluong2 "+
								      "from quycach a inner join sanpham b on a.sanpham_fk = b.pk_seq "+
									  "inner join donvidoluong c on a.dvdl2_fk = c.pk_seq "+
									  "inner join donvidoluong d on a.dvdl1_fk = d.pk_seq "+
									  "where b.PK_SEQ = '"+sanpham_fk+"' and c.pk_seq = '"+dvdl_fk+"'";
								System.out.println("[quycach] "+query);
								ResultSet rs2 = db.get(query);
								if(rs2 != null)
								{
									if(rs2.next())
									{
										String dvdl2 = rs2.getString("dvdl2"); 
										if(!dvdl2.equals("") && dvdl2.trim().equals(donvi.trim()))
										{
											soluongnhan = Double.parseDouble(soluong)*rs2.getDouble("soluong1")/rs2.getDouble("soluong2");
										}
										else if(!dvdl2.equals("") && !dvdl2.equals(donvi))
										{
											flag=1;
										}
										else
											soluongnhan = Double.parseDouble(soluong);
									}
									else
										soluongnhan = Double.parseDouble(soluong);
								}
								System.out.println("[FLAG] "+flag);
								if(scheme.trim().equals("KM"))
								{
									kho_fk="100001";
								}
								System.out.println("[FLAG] "+flag);
								query = 
									"insert into NHAPHANG_SP(nhaphang_fk, sanpham_fk, soluong, soluongnhan, donvi, gianet, tienbvat, vat, tienavat, kbh_fk, kho_fk, scheme) "+
									"values ("+nhaphang_fk+", '"+masanpham+"', '"+soluong+"', '"+soluongnhan+"', '"+donvi+"', '"+dongia+"', '"+thanhtien+"', '"+vat+"', '"+thanhtien+"', "+
									"'"+kbh_fk+"','"+kho_fk+"',N'"+scheme+"') ";
								System.out.println("[NHAPHANG_SP] "+query);
								if(db.updateReturnInt(query)<=0 )
								{
									flag=1;
								}
							}
						}
						
						query = "update phieuxuatkhodms set nhaphang_fk = "+nhaphang_fk+", trangthai = 1 where trangthai = 0 and pk_seq = "+lsxId;
						System.out.println("[phieuxuatkhodms] "+query);
						if(db.updateReturnInt(query) <= 0)
						{
							flag = 1;
						}
						
						if(flag==1)
						{
							db.getConnection().rollback();
							return "Vui lòng kiểm tra lại, trạng thái không hợp lệ";
							
						}
					}
				}
				db.getConnection().commit();
				db.getConnection().setAutoCommit(true);
			
		} catch (Exception e) 
		{
			e.printStackTrace();
			return "Exception "+e.getMessage();
		}
		finally
		{
			db.shutDown();
		}
		return msg;
	}

	private static String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);	
	}
	
	private String DeleteChuyenKho(String lsxId)
	{
		dbutils db = new dbutils();
		String msg = "";
		try
		{
			db.getConnection().setAutoCommit(false);

			String query = "update phieuxuatkhodms set trangthai = '2' where pk_seq = '" + lsxId + "' and trangthai = 0";
			if(db.updateReturnInt(query) <= 0)
			{
				msg = "1.Khong the xoa, trạng thái không hợp lệ " ;
				db.getConnection().rollback();
				return msg;
			}

			db.getConnection().commit();
			db.shutDown();
		}
		catch (Exception e) 
		{
			db.update("roolback");
			db.shutDown();
			return "Exception: " + e.getMessage();
		}

		return "";
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
		if (action == null)
		{
			action = "";
		}

		IPhieuxuatkhoDmsList obj = new PhieuxuatkhoDmsList();

		Utility util = new Utility();

		HttpSession session = request.getSession();
		String userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId"))); 
		obj.setUserId(userId);

		//GIU NGUYEN TIM KIEM		   		   
		String tungaytk = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay")));
		System.out.println("tungay : "+tungaytk);
		if(tungaytk == null)
			tungaytk = "";	    	   	    
		obj.setTungay(tungaytk);

		String denngaytk = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay")));

		if(denngaytk == null)
			denngaytk = "";	    	   	    
		obj.setDenngay(denngaytk);

		String vungtk = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId")));
		if(vungtk == null)
			vungtk = "";	    	   	    
		obj.setVungId(vungtk);

		String khuvuctk = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId")));
		if(khuvuctk == null)
			khuvuctk = "";	    	   	    
		obj.setKhuvucId(khuvuctk);

		String npptk = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));
		System.out.println("nppId : "+npptk);
		if(npptk == null)
			npptk = "";	    	   	    
		obj.setNppTen(npptk);

		String kbhtk = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhId")));
		if(kbhtk == null)
			kbhtk = "";	    	   	    
		obj.setKbhId(kbhtk);		    		   

		String trangthaitk = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai")));		    
		if(trangthaitk == null)
			trangthaitk = "";	    	   	    
		obj.setTrangthai(trangthaitk);		    		  

		session.setAttribute("lsxBeanList", obj);
		//--------------------------------------------------------

		if(action.equals("Tao moi"))
		{
			IPhieuxuatkhoDms lsxBean = new PhieuxuatkhoDms();

			lsxBean.setUserId(userId);

			lsxBean.createRs();
			session.setAttribute("dvkdId", lsxBean.getDvkdId());
			session.setAttribute("kbhId", lsxBean.getKbhId());
			session.setAttribute("nppId", lsxBean.getNppId());

			session.setAttribute("lsxBean", lsxBean);

			String nextJSP = "/AHF/pages/Center/PhieuxuatkhoDmsNew.jsp";

			response.sendRedirect(nextJSP);
		}
		else
		{
			if(action.equals("view") || action.equals("next") || action.equals("prev"))
			{
				obj.setUserId(userId);
				String search = getSearchQuery(request, obj);
				obj.setNxtApprSplitting(Integer.parseInt(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nxtApprSplitting"))));

				obj.init(search);
				obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
				session.setAttribute("obj", obj);

				String nextJSP = "/AHF/pages/Center/PhieuxuatkhoDms.jsp";

				response.sendRedirect(nextJSP);
			} else 
			{
				String search = getSearchQuery(request, obj);
				obj.setUserId(userId);
				obj.init(search);										   

				String nextJSP = "/AHF/pages/Center/PhieuxuatkhoDms.jsp";
				
				session.setAttribute("obj", obj);  	
				session.setAttribute("userId", userId);
				response.sendRedirect(nextJSP);
			}
		}
	}

	private String getSearchQuery(HttpServletRequest request, IPhieuxuatkhoDmsList obj)
	{

		Utility util = new Utility();

		String query =  
				"select a.PK_SEQ, a.trangthai, a.ngaychungtu, c.ten as nppTEN, a.NGAYSUA, a.NGAYTAO, NV.TEN as nguoitao, NV2.TEN as nguoisua "+	
				"from phieuxuatkhodms a inner join NHAPHANPHOI c on a.NPP_FK = c.pk_seq  " +
				"inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ   " +
				"inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ where a.pk_seq > 0 "+
				"and a.kbh_fk in "+util.quyen_kenh(obj.getUserId());//AND A.NPP_FK IN  "+util.quyen_npp(obj.getUserId()) +"  ";

		String tungay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tungay"));
		if(tungay == null)
			tungay = "";
		obj.setTungay(tungay);

		String denngay = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("denngay"));
		if(denngay == null)
			denngay = "";
		obj.setDenngay(denngay);

		String trangthai = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("trangthai"));
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);

		String nppId = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId"));
		if(nppId == null)
			nppId = "";
		obj.setNppTen(nppId);

		String kbhId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("kenhId")));
		obj.setKbhId(kbhId);
		String vungId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("vungId")));
		obj.setVungId(vungId);
		String kvId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khuvucId")));
		obj.setKhuvucId(kvId);

		if(tungay.length() > 0)
			query += " and a.ngaychungtu >= '" + tungay + "'";

		if(denngay.length() > 0)
			query += " and a.ngaychungtu <= '" + denngay + "'";

		if(trangthai.length() > 0)
			query += " and a.TrangThai = '" + trangthai + "'";

		if(nppId.length() > 0){
			query += " and a.NPP_FK= '" + nppId + "'";
		}

		System.out.print(query);
		return query;
	}

	class SanPham
	{
		String dongia;
		String giagoc;
		String quycach ;
		String trongluong;
		String thetich;

		public String getTrongluong()
		{
			return trongluong;
		}
		public void setTrongluong(String trongluong)
		{
			this.trongluong = trongluong;
		}
		public String getThetich()
		{
			return thetich;
		}
		public void setThetich(String thetich)
		{
			this.thetich = thetich;
		}
		public String getQuycach()
		{
			return quycach;
		}
		public void setQuycach(String quycach)
		{
			this.quycach = quycach;
		}
		public String getDongia() 
		{
			return dongia;
		}
		public void setDongia(String dongia) 
		{
			this.dongia = dongia;
		}
		public String getGiagoc() 
		{
			return giagoc;
		}
		public void setGiagoc(String giagoc) 
		{
			this.giagoc = giagoc;
		}

	}

}
