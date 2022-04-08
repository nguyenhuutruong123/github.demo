package geso.dms.distributor.servlets.dondathang;

import geso.dms.distributor.beans.dondathang.IErpDondathangNpp;
import geso.dms.distributor.beans.dondathang.imp.ErpDondathangNpp;
import geso.dms.center.beans.dondathang.imp.XLkhuyenmaiTT;
import geso.dms.distributor.beans.ctkhuyenmai.ICtkhuyenmai;
import geso.dms.distributor.db.sql.dbutils;
import geso.dms.distributor.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/KhuyenmaiNppSvl ")
public class KhuyenmaiNppSvl  extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public KhuyenmaiNppSvl ()
	{
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		this.doPost(request, response);
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

			XLkhuyenmaiTT xlkm;
			IErpDondathangNpp dhBean;
			dbutils db;

			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();

			Utility util = new Utility();
			userId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("userId")));

			String khachhang = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("khachhang")));

			String dhId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("dhId")));

			String nppId = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("nppId")));
			if(nppId == null)
				nppId = "";

			String ngaygiaodich = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ngaygiaodich")));
			dhBean = new ErpDondathangNpp(dhId);
			dhBean.setUserId(userId);

			/*String hinhthuc = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("hinhthuc"));
		if(hinhthuc == null)
			hinhthuc = "0";
		System.out.println("4000..Hinh thuc khuyen mai: " + hinhthuc);*/

			String loaidonhang = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("loaidonhang"));
			if(loaidonhang == null)
				loaidonhang = "0";

			String tonggiatridh = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("tonggiatri")));
			String[] schemeList = request.getParameterValues("schemeList");
			String[] schemeOR = request.getParameterValues("schemeOR");
			String[] soxuatkm = request.getParameterValues("soxuatKM");
			String[] schemePhanBo = request.getParameterValues("schemePhanBo");


			String[] schemeDiengiai = request.getParameterValues("schemeDiengiai");

			String[] masp = request.getParameterValues("spMa");
			String[] soluong = request.getParameterValues("spSoluong");
			String[] dongia = request.getParameterValues("spDongia");
			String[] quycach = request.getParameterValues("spQuycach");

			String action = geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("action"));
			if (action == null){
				action = "";
			}

			System.out.println("11.So CTKM: " + schemeList.length);

			String error = "";
			if(action.equals("save"))
			{
				db = new dbutils();
				String kho_fk = "";
				String query = "";
				try 
				{
					db.getConnection().setAutoCommit(false);

					int i = 0;

					if(schemeList.length > 0)
					{
						while(i < schemeList.length)
						{
							String[] trakm = request.getParameterValues(schemeList[i] + ".trakmId");
							String[] trakmType = request.getParameterValues(schemeList[i] + ".trakmType");
							String[] trakmHinhThuc = request.getParameterValues(schemeList[i] + ".trakmHinhThuc");
							String[] trakmPrimary = request.getParameterValues(schemeList[i] + ".trakmPrimary");

							System.out.println("Scheme OR cua CTKM: " + schemeList[i] + ", la: " + schemeOR[i]);
							if(Boolean.parseBoolean(schemeOR[i]))
							{
								String trakmSelected = request.getParameter(schemeList[i] + ".chon");
								System.out.println("1145. Ban chon TKM: " + trakmSelected);

								if(trakmSelected == null)
									trakmSelected = trakm[0];

								int index_OR = -1;
								for(int kk = 0; kk < trakm.length; kk++)
								{
									if(trakm[kk].trim().equals(trakmSelected))
									{
										index_OR = kk;
										break;
									}
								}

								String trakmTypeSelected = trakmType[index_OR];
								String trakmHinhthucSelected = trakmHinhThuc[index_OR];
								String trakmPrimarySelected = trakmPrimary[index_OR];

								//thay tra khuyen mai OR duoc chon trong List
								trakm = new String[]{trakmSelected};
								trakmType = new String[]{trakmTypeSelected};
								trakmHinhThuc = new String[]{trakmHinhthucSelected};
								trakmPrimary = new String[]{trakmPrimarySelected};

							}

							query = "select kho_fk from ctkhuyenmai where pk_seq = '" + schemeList[i] + "'";
							ResultSet KhoRS = db.get(query);
							if(KhoRS != null)
							{
								KhoRS.next();
								kho_fk = KhoRS.getString("kho_fk");
								KhoRS.close();
							}

							for(int count = 0; count < trakm.length; count++)
							{
								//System.out.println("i la: " + i + " --Tra Km size:  " + trakm.length + " -- Count la: " + count);

								String ttTrakm = request.getParameter(schemeList[i] + "." + trakm[count] + ".tonggiatriTKM");
								String spId = request.getParameter(schemeList[i] + "." + trakm[count] + ".spSelected");
								if(spId == null)
									spId = "";

								//System.out.println("San pham Id la: " + schemeList[i] + "." + trakm[count] + ".spSelected" + "  --- " + spId);
								//System.out.println("Tra khuyen mai Type: " + trakmType[count] + ".Hinh thuc" + "  --- " + trakmHinhThuc[count]);

								if( !(trakmType[count].trim().equals("3") && trakmHinhThuc[count].trim().equals("1") ) ) //tra khuyen mai voi san pham tuy chon
								{
									String flag = "";

									System.out.println("[Theo Tien]");

									if(schemePhanBo[i].equals("0"))
									{
										flag = CheckNghanSach(schemeList[i], nppId, ttTrakm, "0", db);
									}

									if(flag.length() > 0) //ngan sach khong du
									{
										db.getConnection().rollback();

										xlkm = new XLkhuyenmaiTT(userId, ngaygiaodich, khachhang, loaidonhang);

										Hashtable<String,Integer> sp_sl = new Hashtable<String,Integer>();
										Hashtable<String,Float> sp_dg = new Hashtable<String,Float>();
										Hashtable<String,Float> sp_qc = new Hashtable<String,Float>();

										int m = 0; 
										while(m < masp.length)
										{
											sp_sl.put(masp[m], Integer.parseInt(soluong[m].replaceAll(",", "")));
											sp_dg.put(masp[m], Float.parseFloat(dongia[m].replaceAll(",", "")));
											sp_qc.put(masp[m], Float.parseFloat(quycach[m].replaceAll(",", "")));

											m++;
										}

										String[] scheme = request.getParameterValues("Scheme");
										if(scheme != null)
										{
											List<ICtkhuyenmai> listCTKM = xlkm.SortList(xlkm.getCtkmList(), scheme);
											xlkm.setCtkmList(listCTKM); //Sort lai thu tu
										}

										String showAll = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ShowAll")));
										if(showAll == null)
											xlkm.setDieuchinh(true);
										else
											xlkm.setDieuchinh(false);

										xlkm.setMasp(masp);
										xlkm.setSoluong(soluong);
										xlkm.setDongia(dongia);
										xlkm.setQuycach(quycach);
										xlkm.setIdDonhang(dhId);
										xlkm.setTonggiatriDh(Float.parseFloat(tonggiatridh));
										xlkm.setNgaygiaodich(ngaygiaodich);
										xlkm.setMsg("Ngan sach khong du, vui long dieu chinh ngan sach chuong trinh: " + (schemeDiengiai[i] == null ? schemeList[i] : schemeDiengiai[i]) + " - Con toi da " + flag);

										xlkm.setHashA(sp_sl);
										xlkm.setHashB(sp_dg);
										xlkm.setHash_QuyCach(sp_qc);

										xlkm.ApKhuyenMai();
										session.setAttribute("xlkm", xlkm);

										String nextJSP = "/AHF/pages/Distributor/KhuyenMaiNpp.jsp";
										response.sendRedirect(nextJSP);

										return;
									}

									//Thoa ngan sach
									if(trakmPrimary[count].equals("0"))
									{
										if(spId.length() > 0 ) //masp1-soluong1;masp2-soluong2...
										{
											if(spId.indexOf(";") > 0) //nhieu san pham
											{
												String[] spIds = spId.split(";");

												int toalTRA = 0;
												for(int j = 0; j < spIds.length; j++)
												{
													String[] spIdss = spIds[j].split("-");

													String msg = checkTonKhoKhuyenMai(kho_fk, nppId, spIdss, khachhang, db);

													if(msg.equals(""))
													{
														long tongtri = 0;
														//query = "select dongia from trakhuyenmai_sanpham a inner join sanpham b on a.sanpham_fk = b.pk_seq where a.trakhuyenmai_fk = '" + trakm[i].trim() + "' and b.ma = '" + spIdss[0].trim() + "'";
														/*query = "select c.GIAMUANPP as dongia from NHAPHANPHOI a inner join BANGGIAMUANPP_NPP b on a.PK_SEQ = b.NPP_FK inner join BGMUANPP_SANPHAM c on b.BANGGIAMUANPP_FK = c.BGMUANPP_FK  " +
				    										" inner join  BANGGIAMUANPP bgmnpp on bgmnpp.pk_seq=c.BGMUANPP_FK  " +
				    								"where a.PK_SEQ = '" + nppId + "' and  c.trangthai ='1'  and  bgmnpp.kenh_fk=(select kbh_fk from donhang where pk_seq='"+dhId+"')  and c.SANPHAM_FK = (select PK_SEQ from SANPHAM where ma = '" + spIdss[0].trim() + "')";*/


														query = "select GIAMUANPP as dongia  " +
																"from BGMUANPP_SANPHAM bg inner join SANPHAM sp on bg.SANPHAM_FK = sp.PK_SEQ  " +
																"where BGMUANPP_FK in (  select top(1) a.PK_SEQ " +
																"from BANGGIAMUANPP a inner join BANGGIAMUANPP_NPP b on a.PK_SEQ = b.BANGGIAMUANPP_FK  " +
																"where a.TUNGAY <= ( select ngaydonhang from ERP_DONDATHANG where pk_seq='" + dhId + "' ) and b.NPP_FK = '" + nppId + "' and a.KENH_FK = ( select kbh_fk from ERP_DONDATHANG where pk_seq='" + dhId + "' ) and a.DVKD_FK = sp.DVKD_FK  " +
																"order by a.TUNGAY desc  ) and SANPHAM_FK = ( select pk_seq from SANPHAM where MA = '" + spIdss[0].trim() + "' )";

														ResultSet rsKm = db.get(query);
														if(rsKm != null)
														{
															if(rsKm.next())
																tongtri = Math.round(rsKm.getFloat("dongia"));
															rsKm.close();
														}
														long gtriKm = tongtri * Integer.parseInt(spIdss[1].trim());

														query = "Insert into ERP_DONDATHANG_CTKM_TRAKM(DONDATHANGID, ctkmId, trakmId, soxuat,sanpham_fk, spMa, soluong, tonggiatri)" +
																" values('" + dhId + "','" + schemeList[i] + "','" + trakm[count] + "','" + Math.round(Double.parseDouble(soxuatkm[i])) + "',(select pk_seq from sanpham where ma = '" + spIdss[0].trim() + "'),'" + spIdss[0].trim() + "','" + spIdss[1].trim().replaceAll(",", "") + "','" + Long.toString(gtriKm) + "')";
														System.out.println("1.Cau lenh chen du lieu: " + query);
														if(!db.update(query))
														{
															db.getConnection().rollback(); 
															error = "Loi khi tao moi 'ERP_DONDATHANG_CTKM_TRAKM': " + query;
															return;
														}

														//int slg = Integer.parseInt(spIdss[1].trim());
														/*long slg = Math.round(Double.parseDouble(spIdss[1].trim().replaceAll(",", "")));
					    							query = "Update nhapp_kho set available = available - '" + slg + "', booked = booked + '" + slg + "' where kho_fk = '" + kho_fk + "' and npp_fk = '" + nppId + "' and sanpham_fk = (select pk_seq from sanpham where ma = '" + spIdss[0].trim() + "') and kbh_fk = (select kbh_fk from khachhang where pk_seq = '" + khachhang + "')";
					    							System.out.println("2.Cau lenh cap nhat nhapp_kho: " + query);
					    							if(!db.update(query))
					    			    			{
					    								db.getConnection().rollback(); 
					    								error = "Loi khi tao moi 'ERP_DONDATHANG_CTKM_TRAKM': " + query;
					    								return;
					    			    			}

					    							String sql = "update Phanbokhuyenmai set DASUDUNG = DASUDUNG + '" + ttTrakm + "' where ctkm_fk = '" + schemeList[i] + "' and npp_fk = '" + nppId + "'";
									    			System.out.println("7.Cau lenh cap nhat Phanbokhuyenmai: " + sql);
									    			if(!db.update(sql))
									    			{
									    				db.getConnection().rollback();
														error += "Loi khi tao moi 'Phanbokhuyenmai'";
														return;
									    			}*/

														toalTRA += Integer.parseInt(spIdss[1].trim().replaceAll(",", ""));
													}
													else
													{
														db.getConnection().rollback();
														System.out.println("11.RockBack tai day roi....");
														error += msg + " nên bạn không được hưởng ctkm '" + (schemeDiengiai[i] == null ? schemeList[i] : schemeDiengiai[i]) + "'. Vui lòng điều chỉnh lại.\n";
													}
												}

												if(toalTRA > 0)
												{
													//NEU PHAN BO THEO SOLUONG THI PHAI CHECK TONG LONG TRA SO VOI TONG LUONG PHAN BO
													flag = "";
													if(schemePhanBo[i].equals("1"))
													{
														System.out.println("[323 Check Ngan Sach ]");
														flag = CheckNghanSach(schemeList[i], nppId, Integer.toString(toalTRA), "1", db);

														if(flag.trim().length() > 0)
														{
															db.getConnection().rollback();

															dhBean.init();
															dhBean.setAplaikhuyenmai(false);
															dhBean.setMsg(flag);

															//dhBean.setAplaikhuyenmai(false);
															session.setAttribute("lsxBean", dhBean);

															String nextJSP = "/AHF/pages/Distributor/ErpDonDatHangNppUpdate.jsp";
															response.sendRedirect(nextJSP);
															return;
														}
													}
												}

											}
											else  //mot san pham
											{
												String[] spIdss = spId.split("-");


												//NEU PHAN BO THEO SOLUONG THI PHAI CHECK TONG LONG TRA SO VOI TONG LUONG PHAN BO
												flag = "";
												System.out.println ("[SpId]"+spIdss[1].trim().replace(",", ""));
												if(schemePhanBo[i].equals("1"))
												{
													flag = CheckNghanSach(schemeList[i], nppId, spIdss[1].trim().replace(",", ""), "1", db);

													if(flag.trim().length() > 0)
													{
														db.getConnection().rollback();

														dhBean.init();
														dhBean.setAplaikhuyenmai(false);
														dhBean.setMsg(flag);

														//dhBean.setAplaikhuyenmai(false);
														session.setAttribute("lsxBean", dhBean);

														String nextJSP = "/AHF/pages/Distributor/ErpDonDatHangNppUpdate.jsp";
														response.sendRedirect(nextJSP);
														return;
													}
												}
												//kiem tra trong kho khuyen mai con du san pham hay khong, khong du thi thoat
												String msg = checkTonKhoKhuyenMai(kho_fk, nppId, spIdss, khachhang, db);
												if(msg.equals(""))
												{
													long tongtri = 0;
													//query = "select dongia from trakhuyenmai_sanpham a inner join sanpham b on a.sanpham_fk = b.pk_seq where a.trakhuyenmai_fk = '" + trakm[i].trim() + "' and b.ma = '" + spIdss[0].trim() + "'";
													/*query = "select c.GIAMUANPP as dongia from NHAPHANPHOI a inner join BANGGIAMUANPP_NPP b on a.PK_SEQ = b.NPP_FK inner join BGMUANPP_SANPHAM c on b.BANGGIAMUANPP_FK = c.BGMUANPP_FK " +
					    						" inner join  BANGGIAMUANPP bgmnpp on bgmnpp.pk_seq=c.BGMUANPP_FK  " +		
					    						"where a.PK_SEQ = '" + nppId + "' and  c.trangthai ='1' and bgmnpp.kenh_fk =(select kbh_fk from donhang where pk_seq='"+dhId+"') and c.SANPHAM_FK = (select PK_SEQ from SANPHAM where ma = '" + spIdss[0].trim() + "')";*/

													query = "select GIAMUANPP  as dongia  " +
															"from BGMUANPP_SANPHAM bg inner join SANPHAM sp on bg.SANPHAM_FK = sp.PK_SEQ  " +
															"where BGMUANPP_FK in (  select top(1) a.PK_SEQ " +
															"from BANGGIAMUANPP a inner join BANGGIAMUANPP_NPP b on a.PK_SEQ = b.BANGGIAMUANPP_FK  " +
															"where a.TUNGAY <= ( select ngaydonhang from ERP_DONDATHANG where pk_seq='" + dhId + "' ) and b.NPP_FK = '" + nppId + "' and a.KENH_FK = ( select kbh_fk from ERP_DONDATHANG where pk_seq='" + dhId + "' ) and a.DVKD_FK = bg.DVKD_FK  " +
															"order by a.TUNGAY desc  ) and SANPHAM_FK = ( select pk_seq from SANPHAM where MA = '" + spIdss[0].trim() + "' )";


													ResultSet rsKm = db.get(query);
													if(rsKm != null)
													{
														if(rsKm.next())
															tongtri = Math.round(rsKm.getFloat("dongia"));
														rsKm.close();
													}
													long gtriKm = tongtri * Integer.parseInt(spIdss[1].trim());

													query = "Insert into ERP_DONDATHANG_CTKM_TRAKM(DONDATHANGID, ctkmId, trakmId, soxuat,sanpham_fk, spMa, soluong, tonggiatri) " +
															"values('" + dhId + "','" + schemeList[i] + "','" + trakm[count] + "','" + Math.round(Double.parseDouble(soxuatkm[i])) + "',(select pk_seq from sanpham where ma = '" + spIdss[0].trim() + "'),'" + spIdss[0].trim() + "','" + spIdss[1].trim().replaceAll(",", "") + "','" + Long.toString(gtriKm) + "')";	
													System.out.println("3.Cau lenh insert du lieu: " + query);
													if(!db.update(query))
													{
														db.getConnection().rollback(); 
														error += "Loi khi tao moi 'ERP_DONDATHANG_CTKM_TRAKM'";
														return;
													}

													//int slg = Integer.parseInt(spIdss[1].trim());
													/*long slg = Math.round(Double.parseDouble(spIdss[1].trim().replaceAll(",", "")));
						    					query = "Update nhapp_kho set available = available - '" + slg + "', booked = booked + '" + slg + "' where kho_fk = '" + kho_fk + "' and npp_fk = '" + nppId + "' and sanpham_fk = (select pk_seq from sanpham where ma = '" + spIdss[0].trim() + "') and kbh_fk = (select kbh_fk from khachhang where pk_seq = '" + khachhang + "')";
						    					System.out.println("4.Cau lenh cap nhat nhapp_kho: " + query);
						    					if(!db.update(query))
								    			{
						    						db.getConnection().rollback();
													error += "Loi khi tao moi 'ERP_DONDATHANG_CTKM_TRAKM'";
													return;
								    			}

						    					String sql = "update Phanbokhuyenmai set DASUDUNG = DASUDUNG + '" + ttTrakm + "' where ctkm_fk = '" + schemeList[i] + "' and npp_fk = '" + nppId + "'";
								    			System.out.println("7.Cau lenh cap nhat Phanbokhuyenmai: " + sql);
								    			if(!db.update(sql))
								    			{
								    				db.getConnection().rollback();
													error += "Loi khi tao moi 'Phanbokhuyenmai'";
													return;
								    			}*/
												}
												else
												{
													db.getConnection().rollback();
													System.out.println("10.RockBack tai day roi....");
													error += msg + " nên bạn không được hưởng  ctkm '" + (schemeDiengiai[i] == null ? schemeList[i] : schemeDiengiai[i]) + "'.Vui lòng điều chỉnh lại.\n";
												}
											}
										}
										else
										{
											query = "Insert into ERP_DONDATHANG_CTKM_TRAKM(DONDATHANGID, ctkmId, trakmId, soxuat, tonggiatri) values('" + dhId + "','" + schemeList[i] + "','" + trakm[count] + "','" + Math.round(Double.parseDouble(soxuatkm[i])) + "','" + ttTrakm.replaceAll(",", "") + "')";
											System.out.println("5.Cau lenh tao moi ERP_DONDATHANG_CTKM_TRAKM: " + query);
											if(!db.update(query))
											{
												db.getConnection().rollback(); 
												error += "Loi khi tao moi 'ERP_DONDATHANG_CTKM_TRAKM': " + query;
												return;
											}

											/*String sql = "update Phanbokhuyenmai set DASUDUNG = DASUDUNG + '" + ttTrakm + "' where ctkm_fk = '" + schemeList[i] + "' and npp_fk = '" + nppId + "'";
						    			System.out.println("7.Cau lenh cap nhat Phanbokhuyenmai: " + sql);
						    			if(!db.update(sql))
						    			{
						    				db.getConnection().rollback();
											error += "Loi khi tao moi 'Phanbokhuyenmai'";
											return;
						    			}*/
										}										
									}
									else
									{
										///Chon hinh thuc la tra tien
										System.out.println("123.Hinh thuc la tra tien.....");
										query = "Insert into ERP_DONDATHANG_CTKM_TRAKM(DONDATHANGID, ctkmId, trakmId, soxuat, tonggiatri) values('" + dhId + "','" + schemeList[i] + "','" + trakm[count] + "','" + Math.round(Double.parseDouble(soxuatkm[i])) + "','" + ttTrakm.replaceAll(",", "") + "')";
										if(!db.update(query))
										{
											db.getConnection().rollback();
											error = query;
											return;
										}

										/*String sql = "update Phanbokhuyenmai set DASUDUNG = DASUDUNG + '" + ttTrakm + "' where ctkm_fk = '" + schemeList[i] + "' and npp_fk = '" + nppId + "'";
					    			System.out.println("7.Cau lenh cap nhat Phanbokhuyenmai: " + sql);
					    			if(!db.update(sql))
					    			{
					    				db.getConnection().rollback();
										error += "Loi khi tao moi 'Phanbokhuyenmai'";
										return;
					    			}*/
									}

								}
								else
								{
									System.out.println("10.Toi da vao trong nay....");
									if(trakmPrimary[count].equals("0"))
									{
										System.out.println("11.Vao trong luu san pham i: =" + i + ", count: " + count);

										String msg = CreateKhuyenmai(schemeList[i], schemePhanBo[i], trakm[count], dhId, nppId, Math.round(Double.parseDouble(soxuatkm[i])), khachhang, db);
										if(msg.length() > 0)
										{
											db.getConnection().rollback();
											/*error += "9.Lỗi khi tạo mới CTKM: " + msg;
										System.out.println(error);
										return;
											 */

											xlkm = new XLkhuyenmaiTT(userId, ngaygiaodich, khachhang, loaidonhang);

											Hashtable<String,Integer> sp_sl = new Hashtable<String,Integer>();
											Hashtable<String,Float> sp_dg = new Hashtable<String,Float>();
											Hashtable<String,Float> sp_qc = new Hashtable<String,Float>();

											int m = 0; 
											while(m < masp.length)
											{
												sp_sl.put(masp[m], Integer.parseInt(soluong[m].replaceAll(",", "")));
												sp_dg.put(masp[m], Float.parseFloat(dongia[m].replaceAll(",", "")));
												sp_qc.put(masp[m], Float.parseFloat(quycach[m].replaceAll(",", "")));

												m++;
											}

											String[] scheme = request.getParameterValues("Scheme");
											if(scheme != null)
											{
												List<ICtkhuyenmai> listCTKM = xlkm.SortList(xlkm.getCtkmList(), scheme);
												xlkm.setCtkmList(listCTKM); //Sort lai thu tu
											}

											String showAll = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ShowAll")));
											if(showAll == null)
												xlkm.setDieuchinh(true);
											else
												xlkm.setDieuchinh(false);

											xlkm.setMasp(masp);
											xlkm.setSoluong(soluong);
											xlkm.setDongia(dongia);
											xlkm.setQuycach(quycach);
											xlkm.setIdDonhang(dhId);
											xlkm.setTonggiatriDh(Float.parseFloat(tonggiatridh));
											xlkm.setNgaygiaodich(ngaygiaodich);
											xlkm.setMsg("Ngan sach khong du, vui long dieu chinh ngan sach chuong trinh: " + (schemeDiengiai[i] == null ? schemeList[i] : schemeDiengiai[i]) + " - Con toi da " );

											xlkm.setHashA(sp_sl);
											xlkm.setHashB(sp_dg);
											xlkm.setHash_QuyCach(sp_qc);

											xlkm.ApKhuyenMai();
											session.setAttribute("xlkm", xlkm);

											String nextJSP = "/AHF/pages/Distributor/KhuyenMaiNpp.jsp";
											response.sendRedirect(nextJSP);
											return;
										}
									}
									else
									{
										System.out.println("12.Vao trong luu tien trong san pham");

										/*********************************************************************************/
										String msg = CheckNghanSach(schemeList[i], nppId, ttTrakm, "0", db);
										if(msg.trim().length() > 0)
										{
											db.getConnection().rollback();
											error = msg;
											return;
										}
										/*********************************************************************************/

										query = "Insert into ERP_DONDATHANG_CTKM_TRAKM(DONDATHANGID, ctkmId, trakmId, soxuat, tonggiatri) values('" + dhId + "','" + schemeList[i] + "','" + trakm[count] + "','" + Math.round(Double.parseDouble(soxuatkm[i].replaceAll(",", ""))) + "','" + ttTrakm.replaceAll(",", "") + "')";
										System.out.println("13.Chen khuyen mai: " + query);
										if(!db.update(query))
										{
											db.getConnection().rollback();
											error = query;
											return;
										}

										/*String sql = "update Phanbokhuyenmai set DASUDUNG = DASUDUNG + '" + ttTrakm.replaceAll(",", "") + "' where ctkm_fk = '" + schemeList[count] + "' and npp_fk = '" + nppId + "'";
					    			if(!db.update(sql))
					    			{
					    				db.getConnection().rollback();
										error += "12.Lỗi khi tạo mới CTKM: " + sql;
										System.out.println(error);
										return;
					    			}*/
									}
								}
							}
							i++;
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
						System.out.println("8.Exception tai day: " + e1.getMessage());
						db.getConnection().rollback();
					}
					catch(Exception err){}

					error += "Loi khi tao moi chuong trinh khuyen mai: " + e1.getMessage();
				}
				db.shutDown();

				dhBean.init();
				dhBean.setAplaikhuyenmai(false);
				if(!error.equals(""))
					dhBean.setMsg(error);

				//dhBean.setAplaikhuyenmai(false);
				session.setAttribute("lsxBean", dhBean);

				String nextJSP = "/AHF/pages/Distributor/ErpDonDatHangNppUpdate.jsp";
				response.sendRedirect(nextJSP);	
			}
			else //submit
			{
				xlkm = new XLkhuyenmaiTT(userId, ngaygiaodich, nppId, dhId);

				Hashtable<String,Integer> sp_sl = new Hashtable<String,Integer>();
				Hashtable<String,Float> sp_dg = new Hashtable<String,Float>();
				Hashtable<String,Float> sp_qc = new Hashtable<String,Float>();

				int m = 0; 
				while(m < masp.length)
				{
					sp_sl.put(masp[m], Integer.parseInt(soluong[m].replaceAll(",", "")));
					sp_dg.put(masp[m], Float.parseFloat(dongia[m].replaceAll(",", "")));
					sp_qc.put(masp[m], Float.parseFloat(quycach[m].replaceAll(",", "")));

					m++;
				}

				String[] scheme = request.getParameterValues("Scheme");
				if(scheme != null)
				{
					List<ICtkhuyenmai> listCTKM = xlkm.SortList(xlkm.getCtkmList(), scheme);
					xlkm.setCtkmList(listCTKM); //Sort lai thu tu
				}


				String showAll = util.antiSQLInspection(geso.dms.center.util.Utility.antiSQLInspection(request.getParameter("ShowAll")));
				if(showAll == null)
					xlkm.setDieuchinh(true);
				else
					xlkm.setDieuchinh(false);

				xlkm.setMasp(masp);
				xlkm.setSoluong(soluong);
				xlkm.setDongia(dongia);
				xlkm.setQuycach(quycach);
				xlkm.setIdDonhang(dhId);
				xlkm.setTonggiatriDh(Float.parseFloat(tonggiatridh));
				xlkm.setNgaygiaodich(ngaygiaodich);

				xlkm.setHashA(sp_sl);
				xlkm.setHashB(sp_dg);
				xlkm.setHash_QuyCach(sp_qc);

				xlkm.ApKhuyenMai();
				xlkm.checkConfirm();
				xlkm.SetDungSanPham();
				session.setAttribute("xlkm", xlkm);
				String nextJSP = "/AHF/pages/Distributor/KhuyenMaiNpp.jsp";
				response.sendRedirect(nextJSP);
			}
		} 
	}

	private String checkTonKhoKhuyenMai(String kho_fk, String nppId, String[] spIdss, String khachhang, dbutils db)
	{
		return "";
	}

	private String checkTonkho(String nppId, String ctkmId, String khId, String spId, String spMa, int slg, dbutils db) 
	{
		return "";
	}

	private String CreateKhuyenmai(String ctkmId, String phanbotheoDH, String trakmId, String id, String nppId, long soxuat, String khId, dbutils db)
	{
		String str = "";

		try 
		{ 	
			String sql = "select a.sanpham_fk as spId, a.soluong, b.ma as spMa from trakhuyenmai_sanpham a inner join sanpham b on a.sanpham_fk = b.pk_seq where a.trakhuyenmai_fk = '" + trakmId + "'";
			System.out.println("10.Query truy van san pham khuyen mai: " + sql + "\n");
			ResultSet rsSQl = db.get(sql);
			long tongtien = 0;

			while(rsSQl.next())
			{
				int slg = Integer.parseInt(rsSQl.getString("soluong")) * (int)soxuat;
				String query =  "select GIAMUANPP  as dongia  " +
						"from BGMUANPP_SANPHAM bg inner join SANPHAM sp on bg.SANPHAM_FK = sp.PK_SEQ  " +
						"where BGMUANPP_FK in (  select top(1) a.PK_SEQ " +
						"from BANGGIAMUANPP a inner join BANGGIAMUANPP_NPP b on a.PK_SEQ = b.BANGGIAMUANPP_FK  " +
						"where a.TUNGAY <= ( select ngaydonhang from ERP_DONDATHANG where pk_seq='" + id + "' ) and b.NPP_FK = '" + nppId + "' and a.KENH_FK = ( select kbh_fk from ERP_DONDATHANG where pk_seq='" + id + "' ) and a.DVKD_FK = ( select dvkd_fk from ERP_DONDATHANG where pk_seq='" + id + "' )  " +
						"order by a.TUNGAY desc  ) and SANPHAM_FK = '" + rsSQl.getString("spId") + "' ";

				System.out.println("11.Lay don gia: " + query);

				ResultSet rsDg = db.get(query);
				{
					if(rsDg.next())  
					{
						if(rsDg.getString("dongia") != null)
							tongtien = Math.round(slg * rsDg.getFloat("dongia"));
						rsDg.close();
					}
				}

				String error = checkTonkho(nppId, ctkmId, khId, rsSQl.getString("spId"), rsSQl.getString("spMa"), slg, db);
				if(error.length() > 0)
				{
					db.getConnection().rollback();
					return error;
				}

				//luu tong gia tri o moi dong sanpham
				query = "Insert into ERP_DONDATHANG_CTKM_TRAKM(DONDATHANGID, ctkmId, trakmId, soxuat, tonggiatri,sanpham_fk, spMa, soluong)" +
						" values('" + id + "','" + ctkmId + "','" + trakmId + "','" + soxuat + "', '" + Long.toString(tongtien) + "', '" + rsSQl.getString("spId").trim() + "', '" + rsSQl.getString("spMa").trim() + "', '" + Integer.toString(slg) + "')";
				System.out.println("12.Chen khuyen mai: " + query);
				if(!db.update(query))
				{
					db.getConnection().rollback();
					str = query;
					return str;
				}

			}
			rsSQl.close();
			/*********************************************************************************/
			String msg = CheckNghanSach(ctkmId, nppId, "0", "1", db);
			if(msg.trim().length() > 0)
			{
				db.getConnection().rollback();
				return msg;
			}
			/*********************************************************************************/
		}
		catch (Exception e1) 
		{
			e1.printStackTrace();
			System.out.println("3.Loi KM: " + e1.toString());
			try 
			{
				db.getConnection().rollback();
			} 
			catch (SQLException e) {}

			return "Loi khi tao ctkm: " + ctkmId + ", " + e1.toString();
		}
		return str;
	}


	private String CheckNghanSach(String ctkmId, String nppId, String ngansach, String loai, dbutils db)
	{
		String sql = 
				" select a.CTKM_FK, b.scheme, b.PHANBOTHEODONHANG, a.NGANSACH, "+  	 
						"	case when b.PHANBOTHEODONHANG=1 then "+
						"	 ISNULL( 	( select SUM(SOLUONG)  	  "+
						"	  from ERP_DONDATHANG_CTKM_TRAKM  	  "+
						"	 where CTKMID = a.CTKM_FK AND SPMA IS NOT NULL and DONDATHANGID in  "+
						"	 ( select PK_SEQ from ERP_DONDATHANG where NPP_FK = a.NPP_FK and TRANGTHAI != 3 )),0) "+ 
						"	 else  "+
						"		  ISNULL( 	( select SUM(tonggiatri)  "+  
						"		  from ERP_DONDATHANG_CTKM_TRAKM   "+
						"		  where CTKMID = a.CTKM_FK and DONDATHANGID in ( select PK_SEQ from ERP_DONDATHANG where NPP_FK = a.NPP_FK and TRANGTHAI != 3 )),0)  "+
						"	END as DASUDUNG  "+
						"	 from phanbokhuyenmai a inner join CTKHUYENMAI b on a.CTKM_FK = b.PK_SEQ   "+
						"where npp_fk = '" + nppId + "' and ctkm_fk = '" + ctkmId + "'  ";

		System.out.println("1.Cau lenh check ngan sach: " + sql + " --- Ngan sach de check: " + ngansach);
		ResultSet rs = db.get(sql);
		String scheme = "";

		try 
		{
			double NganSach=0;
			double DaSuDung =0;
			if(rs.next())
			{
				scheme = rs.getString("scheme");
				NganSach=rs.getDouble("NganSach");
				DaSuDung=rs.getDouble("DaSuDung");
				rs.close();	
			}
			System.out.println("---NGAN SACH ( SOXUAT / TONG GIA TRI ): " + NganSach + " -- Da Su Dung : " + DaSuDung);
			if(DaSuDung > NganSach)
			{
				return "1.Chương trình khuyến mại: " + scheme + ", đã hết ngân sách phân bổ -Ngân sách("+NganSach+") - Sử dụng ("+DaSuDung+")";
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
