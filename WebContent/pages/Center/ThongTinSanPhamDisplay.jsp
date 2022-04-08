<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.beans.thongtinsanpham.IThongtinsanpham" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>

<% IThongtinsanpham spBean = (IThongtinsanpham)session.getAttribute("spBean"); %>
<% 	ResultSet dvdl = (ResultSet)spBean.getDvdl(); 
	ResultSet dvkd = (ResultSet)spBean.getDvkd(); 
	ResultSet ngh = (ResultSet)spBean.getNganhHang();
	ResultSet nh = (ResultSet)spBean.getNh();
	ResultSet cl = (ResultSet)spBean.getCl();

	ResultSet nspSelected = (ResultSet)spBean.getNspSelected();	
	ResultSet splistSelected = (ResultSet)spBean.getSanphamSelectedRs();
	
	String dvdlId = (String) spBean.getDvdlId();
	String dvkdId = (String) spBean.getDvkdId();
	String nganhhangId = (String) spBean.getNganhHangId();
	String nhId = (String)spBean.getNhId();
	String clId = (String)spBean.getClId();
	Hashtable<Integer, String> nspIds = spBean.getNspIds();
	Hashtable<Integer, String> spIds = spBean.getSpIds();
	
	  %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Bibica - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">

<LINK rel="stylesheet" href="../css/main.css" type="text/css">


<!-- BEGIN RENDER AUTO -->

<% if (!geso.dms.center.util.GlobalValue.getDevmode()) { %>
	<script type="text/javascript" src="../scripts/disableF12.js"></script>
<% } %>

<!-- END RENDER AUTO -->
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<!-- BEGIN RENDER AUTO -->
<%if(!geso.dms.center.util.GlobalValue.getDevmode()){ %>
	<noscript> 
	 	<meta http-equiv="refresh" content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")  %>">
		<!-- //window.location = "https://www.google.com"; -->
	</noscript>
<%} %>
<!-- END RENDER AUTO -->

<form name="spForm" method="post" action="../../ThongtinsanphamUpdateSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%=spBean.getUserId() %>'>
<input type="hidden" name="action" value='1'>

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền &gt; Sản phẩm &gt; Thông tin sản phẩm &gt; Hiển thị </TD>
							 <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %>&nbsp;  </TD></tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
			<TR ><TD >
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR class = "tbdarkrow">
							<TD width="30" align="left"><A href="javascript:history.back()" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
						    <TD width="2" align="left" ></TD>						
						</TR>
					</TABLE>
			</TD></TR>
			</TABLE>

			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1" >
				<TR>
					<TD>
						<TABLE width="100%" border="0" cellpadding="0" cellspacing="1" >
							<TR>
								<TD>
									<FIELDSET>
									<LEGEND class="legendtitle" style="color:black">Thông tin sản phẩm </LEGEND>
	
									<TABLE border="0" width="100%" cellpadding="4" cellspacing="0">
										<TR>
											<TD width="25%" class="plainlabel" >Mã sản phẩm <FONT class="erroralert"> *</FONT></TD>
											<TD width="35%" class="plainlabel"><INPUT type="text" name="masp" size="13" value = '<%=spBean.getMasp() %>'></TD>
										
										    <TD class="plainlabel">Hoạt động &nbsp;
  												<% if (spBean.getTrangthai().equals("1")){ %>
													<input name="trangthai" type="checkbox" value = "1" checked >
												<%}else{ %>
												<!-- 	<input name="trangthai" type="checkbox" value = "0"  > -->
												<%} %>
											</TD>
										</TR>	
										<%-- <TR>
											<TD class="plainlabel">Mã sản phẩm Đại Đồng Tiến<FONT class="erroralert">*</FONT></TD>
										  	<TD colspan = "3" class="plainlabel"><input name="maddt" type="text" size="40" value = '<%=spBean.getMaddt() %>'></TD>
						  				</TR> --%>
										<TR>
											<TD class="plainlabel">Tên sản phẩm<FONT class="erroralert">*</FONT></TD>
										  	<TD colspan = "3" class="plainlabel"><input name="tensp" style="width:500px" type="text" size="40" value = '<%=spBean.getTen() %>'></TD>
						  				</TR>
										<TR>
											<TD class="plainlabel">Tên sản phẩm viết tắt<FONT class="erroralert">*</FONT></TD>
										  	<TD colspan = "3" class="plainlabel"><input name="tenviettat" style="width:500px" type="text" size="40" value = '<%=spBean.getTenVietTat() %>'></TD>
						  				</TR>
						  				<%-- <TR>
											<TD class="plainlabel">Mã tắt <FONT class="erroralert">*</FONT></TD>
										  	<TD colspan = "3" class="plainlabel"><input name="matat" type="text" size="40" value ='<%=spBean.getMaTat()%>'></TD>
						  				</TR> --%>
										<TR>
							  					<TD class="plainlabel">Đơn vị đo lường <FONT class="erroralert"> *</FONT></TD>
						  	  				<TD colspan = "3" class="plainlabel">
												<select name="dvdlId" id="dvdlId" onChange="f();">
												<option value="" > </option>
												<% try{
														while(dvdl.next()){
								  							if (dvdlId.equals(dvdl.getString("dvdlId"))){ %>											
								  								<option value='<%= dvdl.getString("dvdlId")%>' selected><%= dvdl.getString("dvdlTen")%></option>
								  		  					<%}else{ %>		
								  		  						<%-- <option value='<%= dvdl.getString("dvdlId")%>' ><%= dvdl.getString("dvdlTen")%></option> --%>
								  							<%		}
								  						}
														dvdl.close();
								  					}catch (java.sql.SQLException e){
								  						e.printStackTrace();
								  					} %>
															
										  	  	</select>											
														
										  	  	</select>											
										  	 </TD>
										</TR>
										<TR>
										  <TD class="plainlabel">Đơn vị kinh doanh <FONT class="erroralert">*</FONT></TD>
										  <TD colspan="3" class="plainlabel">
										  	<select name="dvkdId" onChange="submitform()">
												<option value="" ></option>
												<% try{
														while(dvkd.next()){
								  							if (dvkdId.equals(dvkd.getString("dvkdId"))){%>								  								
								  								<option value='<%= dvkd.getString("dvkdId")%>' selected ><%=dvkd.getString("dvkdTen") %></option>
												<%			}
								  							else{ %>
								  							<%-- 	<option value='<%= dvkd.getString("dvkdId")%>'  ><%=dvkd.getString("dvkdTen") %></option> --%>
								  				<%			}
								  						}
														dvkd.close();
								  					}catch (java.sql.SQLException e){} %>


										    </select>
										  </TD>
									  </TR>
									  
									   <TR>
										  <TD class="plainlabel">Ngành hàng<FONT class="erroralert">*</FONT></TD>
										  <TD colspan="3" class="plainlabel">
										  	<select name="nganhhangId" onChange="submitform();">
												<option value="" ></option>
												<%
												if(ngh!=null)
												{
													try{
														while(ngh.next()){
															if(nganhhangId != null)
															{
																if(nganhhangId.equals(ngh.getString("PK_SEQ")))
																{
																	%>
																	<option value='<%= ngh.getString("PK_SEQ")%>'  selected><%=ngh.getString("TEN") %></option>
																	<%	
																}															
															}
															else
															{
																%>
																<option value='<%= ngh.getString("PK_SEQ")%>'  ><%=ngh.getString("TEN") %></option>
																<%	
															}
															
								  						}
														ngh.close();
								  					}catch (java.sql.SQLException e){}
												}
												 %>
										    </select>
										  </TD>
									  </TR>
									  
										<TR>
											  <TD class="plainlabel">Nhãn hàng <FONT class="erroralert">*</FONT></TD>
											  <TD colspan="3" class="plainlabel">
											  		<% if (dvkdId.length()>0){ %>
											  			<SELECT name="nhId" values ="" onChange="submitform()" >
											  		<%}else{ %>
											  			<SELECT name="nhId" values ="" >
											  		<%} %>
											  		
															<option value="" ></option>
														<% try{
																while(nh.next()){
									  							if (nhId.equals(nh.getString("nhId"))){%>								  								
							  										<option value='<%= nh.getString("nhId")%>' selected ><%=nh.getString("nhTen") %></option>
														<%		}
							  									else{ %>
							  										<%-- <option value='<%= nh.getString("nhId")%>'  ><%=nh.getString("nhTen") %></option> --%>
							  							<%		}
															
								  								}
																nh.close();
								  							}catch (java.sql.SQLException e){} %>
											  			
													</SELECT>													
												</TD>
										  </TR>
										  <TR>
											  <TD class="plainlabel">Chủng loại <FONT class="erroralert"> *</FONT></TD>
											  <TD colspan="3" class="plainlabel">
											  		
											  		<% if ((nhId.length() >0)&(dvkdId.length()>0)){ %>
											  			<select name="clId" onChange="submitform()" >
											  		<%}else{ %>
											  			<select name="clId" >
											  		<%} %>

															<option value="" ></option>
														<% try{
																while(cl.next()){
									  							if (clId.equals(cl.getString("clId"))){%>								  								
							  										<option value='<%= cl.getString("clId")%>' selected ><%=cl.getString("clTen") %></option>
														<%		}
							  									else{ %>
							  										<option value='<%= cl.getString("clId")%>'  ><%=cl.getString("clTen") %></option>
							  							<%		}															
								  								}
																cl.close();
								  							}catch (java.sql.SQLException e){} %>
											  			

												    </select>									
												</TD>
										  </TR>
										  
										  <TR> 
										  <TD class="plainlabel" colspan="3">Sản phẩm trọng tâm
										  <% if (spBean.getIsTrongtam().equals("1")){ %>
													<input name="isTrongtam" type="checkbox" value = "1" checked >
												<%}else{ %>
													<input name="isTrongtam" type="checkbox" value = "1"  >
												<%} %>
												</TD>
										  </TR>
										  
<!-- 										  Start thông tin thêm sản phẩm IP -->
										  <%if (spBean.getIsIP() == true){ %>
										  <TR>
											<TD class="plainlabel">Nhãn hàng sản phẩm<FONT class="erroralert"></FONT></TD>
										  	<TD colspan = "3" class="plainlabel"><input name="nhanHangSp" type="text" size="40" value = '<%=spBean.getNhanHang() %>'></TD>
						  				  </TR>
						  				  <TR>
											<TD class="plainlabel">Định lượng<FONT class="erroralert"></FONT></TD>
										  	<TD colspan = "3" class="plainlabel"><input name="dinhLuong" type="text" size="40" value = '<%=spBean.getDinhLuong()%>'></TD>
						  				  </TR>
						  				  <TR>
											<TD class="plainlabel">Khổ giấy<FONT class="erroralert"></FONT></TD>
										  	<TD colspan = "3" class="plainlabel"><input name="khoGiay" type="text" size="40" value = '<%=spBean.getKhoGiay()%>'></TD>
						  				  </TR>
						  				  <TR>
											<TD class="plainlabel">Máy sản xuất<FONT class="erroralert"></FONT></TD>
										  	<TD colspan = "3" class="plainlabel"><input name="maySanXuat" type="text" size="40" value = '<%=spBean.getMaySanXuat() %>'></TD>
						  				  </TR>
						  				  <%} %>
<!-- 						  				  End thông tin thêm sản phẩm IP -->
										    <TR style="display: none">
											  <TD colspan="3" class="plainlabel" align="left">
											  <% if(spBean.getType().equals("1")) {%>
											  		<input name="type" type="checkbox" id="type" value="1" checked="checked" onChange="submitform();"> is Bundle		
											  <%}else{ %>
											  		<input name="type" type="checkbox" id="type" value="0" onChange="submitform();"> is Bundle	
											  <%} %>				  				
											  </TD>
										  </TR>
									</TABLE>
									
									<%try{ if(splistSelected != null){ int mm = 0; %>
									  		<fieldset><legend class="legendtitle"> Sản phẩm thuộc Bundle </legend>
									  		<table style="width: 100%" cellpadding="4" cellspacing="1">
									  		<tr class="tbheader">
									  			<th align="center">Mã sản phẩm</th>
									  			<th align="center">Tên sản phẩm</th>
									  			<th align="center">Chọn</th>
									  		</tr>
									  			<% while(splistSelected.next()){ 
									  				if(mm % 2 == 0){	%>
									  					<tr class="tblightrow">
									  				<%}else{ %>
									  					<tr class="tbdarkrow">
									  				<%} %>
									  					<td><%= splistSelected.getString("spMa") %></td>
									  					<td><%= splistSelected.getString("spTen") %></td>
									  					<td align="center">
									  					<% if(spIds.contains(splistSelected.getString("pk_seq"))){ %>
									  						<input type="checkbox" name="spIds" value='<%= splistSelected.getString("pk_seq") %>' checked="checked">
									  					<%}else{ %>
									  						<input type="checkbox" name="spIds" value='<%= splistSelected.getString("pk_seq") %>'>
									  					<%} %>
									  					</td>
									  				</tr>
									  			<% mm ++;}%>
									  		</table></fieldset>
									  <%}} catch(java.sql.SQLException e){} %>	  
									
									</FIELDSET>									
									<TABLE width="100%" border="0" cellpadding="0" cellspacing="0" >
										<TR>
										  	<TD >
												<FIELDSET>
												<LEGEND class="legendtitle" style="color:black">Nhóm sản phẩm</LEGEND>
												<TABLE border="0" width="100%" cellpadding="4" cellspacing="1">
													<TR class="tbheader">
														<TH width="30%" >Nhóm sản phẩm </TH>
														<TH width="60%" >Diễn giải </TH>
														<TH width="10%" >Chọn</TH>
													</TR>
													<% try{
														String lightrow = "tblightrow";
														String darkrow = "tbdarkrow";
														int m = 0;
														if (nspSelected != null)
														{
															while (nspSelected.next()){ 
																if (m % 2 != 0) {%>						
																	<TR class= <%=lightrow%> >
															   <%} else {%>
																	<TR class= <%= darkrow%> >
								 						  	   <%}%>							
															
																<TD><%=nspSelected.getString("nspTen") %> </TD>
																<TD><div align="left"><%=nspSelected.getString("diengiai") %> </div></TD>
																<TD> <div align="center">
																<% if (nspIds.contains(nspSelected.getString("nspId"))){ %>
															  			<input type="checkbox" name="nspIds" value='<%=nspSelected.getString("nspId") %>' checked>
															  	<%}else{ %>
															  			<input type="checkbox" name="nspIds" value='<%=nspSelected.getString("nspId") %>' >
															  	<%} %>
																</div></TD>
															</TR>																
															<% m++;}nspSelected.close();}} catch(java.sql.SQLException e){}%>						
												</TABLE>						
												</FIELDSET>							
											</TD>
										</TR>
									</TABLE>
									<TABLE width = "100%" cellpadding="0" cellspacing="0">
										<TR>
											<TD>
												<FIELDSET>
												<LEGEND class="legendtitle">&nbsp;Thiết lập quy cách</LEGEND>
												<TABLE  border="1" cellpadding="0"  cellspacing="1" width="100%">
															<TR class="tbheader" >
																<TH width="21%" >Số lượng </TH>
																<TH width="21%" >Đơn vị đo lường </TH>
																<TH width="16%" >Quy đổi </TH>
																<TH width="21%" >Số lượng </TH>
																<TH width="21%" >Đơn vị đo lường </TH>
															</TR>
												<%			String[] sl1 = spBean.getSl1();
															String[] sl2 = spBean.getSl2();
															String[] dvdl1 = spBean.getDvdl1();	
															String[] dvdl2 = spBean.getDvdl2();
														/* 	if(!(dvdl1[0] == null) & !(dvdl2[0] == null)){
																if (!dvdl1[0].equals(dvdlId)){ 
																	dvdl1[0] = dvdlId;
																	sl1[0] = "";
																}
															
																if (!dvdl2[0].equals("100018")){ 
																	dvdl2[0] = "100018";
																	sl2[0] = "";
																} 
															}else{																
												 				dvdl1[0] = dvdlId;
																sl1[0] = "";
																dvdl2[0] = "100018";
																sl2[0] = "";										
																
															} */
														int count = -1;
														for(int i = 0; i < 5; i++){	%>
															<TR class= 'tblightrow'>
											  					<TD align="center" >
											  					<%	if (!(sl1[i] == null)){
											  							if (sl1[i].trim().length()>0){   
											  								count++;		%>
																			<input name="sl1" type="text" style="width:100%" value=<%=sl1[i] %> >
																<%		}
											  							else{  %>
																			<input name="sl1" type="text" style="width:100%" value="">
																<%		}	
																	}else{%>											    					
																		<input name="sl1" type="text" style="width:100%" value="">
																<%	} %>										
																</TD>
															  	<TD align="center" >
																	<% if (i == 0){ %>
																		<select name="dvdl1" id = "<%= i %>" style="width:100%;height:22" onChange="updateUoM();">
																	<%}else{ %>
																		<select name="dvdl1" id = "<%= i %>" style="width:100%;height:22" >
																	<%} %>

																	<option value=""> </option>																	 	
																	<%	dvdl = spBean.createDvdlRS();												
																		try{
																			while(dvdl.next()){
																				if(!(dvdl1[i] == null)){
																					if (dvdl1[i].equals(dvdl.getString("dvdlId")) & (count == i)){%>																																																																					
																						<option value=<%= dvdl.getString("dvdlId") %> selected><%= dvdl.getString("dvdlTen")%> </option>
																		<%			}else{ %>
																						<option value=<%= dvdl.getString("dvdlId") %> ><%= dvdl.getString("dvdlTen")%> </option>
																		<%			}
																				}else{ %>
																					<option value=<%= dvdl.getString("dvdlId") %> ><%= dvdl.getString("dvdlTen")%> </option>
																		<%		}
																			}
																			dvdl.close();
																		}catch(java.sql.SQLException e){}%>
																			
																	
																    </select>
															  	</TD>
															  	<TD align="center" > = </TD>
											  					<TD align="center" >
											  					<%	if (!(sl2[i] == null)){
											  							if (sl2[i].trim().length()>0){%>   
											  									
																			<input name="sl2" type="text" style="width:100%" value=<%=sl2[i] %> >
																<%		}
											  							else{  %>
																			<input name="sl2" type="text" style="width:100%" value="">
																<%		}	
																	}else{%>											    					
																		<input name="sl2" type="text" style="width:100%" value="">
																<%	} %>										
																</TD>
															  	<TD align="center" >
																	<select name="dvdl2" style="width:100%;height:22" >
																	<% if (i == 0){ %>
																		<option value="100018">Thùng </option>
																	<%}else{ %>

																	<option value=""> </option>
																	<% 	
																		dvdl = spBean.createDvdlRS();
																	try{
																		while(dvdl.next()){
																			if(!(dvdl2[i] == null)){
																				if (dvdl2[i].equals(dvdl.getString("dvdlId"))){%>																																																																					
																					<option value=<%= dvdl.getString("dvdlId") %> selected><%= dvdl.getString("dvdlTen")%> </option>
																	<%			}else{ %>
																					<option value=<%= dvdl.getString("dvdlId") %> ><%= dvdl.getString("dvdlTen")%> </option>
																	<%			}
																			}else{ %>
																				<option value=<%= dvdl.getString("dvdlId") %> ><%= dvdl.getString("dvdlTen")%> </option>
																	<%		}
																		}
																			dvdl.close();
																		}catch(java.sql.SQLException e){
																			e.printStackTrace();
																		}%>
																	<%}}} %>																			
																    </select>											  
															  	</TD>
														  	</TR>
												</TABLE>
												</FIELDSET>
											</TD>
										</TR>
									</TABLE>
								</TD>
							</TR>
						</TABLE>
					</TD>
	      		</TR>
		  	</TABLE>
		</TD>
	
</TABLE>
</form>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>

</HTML>


