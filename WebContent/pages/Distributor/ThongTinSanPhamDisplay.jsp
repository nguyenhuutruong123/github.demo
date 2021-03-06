<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.distributor.beans.thongtinsanpham.IThongtinsanpham" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DecimalFormat"%>
<%@ page  import = "geso.dms.center.util.*" %>
<%	
	NumberFormat formatter = new DecimalFormat("#,###,###"); 
	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>

<% IThongtinsanpham spBean = (IThongtinsanpham)session.getAttribute("spBean"); %>
<% 	
	ResultSet dvdl = (ResultSet)spBean.getDvdl(); 
	ResultSet dvkd = (ResultSet)spBean.getDvkd(); 
	ResultSet nh = (ResultSet)spBean.getNh();
	ResultSet cl = (ResultSet)spBean.getCl();

	ResultSet nspSelected = (ResultSet)spBean.getNspSelected();	
	ResultSet splistSelected = (ResultSet)spBean.getSanphamSelectedRs();
	
	String dvdlId = (String) spBean.getDvdlId();
	String dvkdId = (String) spBean.getDvkdId();
	String nhId = (String)spBean.getNhId();
	String clId = (String)spBean.getClId();
	Hashtable<Integer, String> nspIds = spBean.getNspIds();
	Hashtable<Integer, String> spIds = spBean.getSpIds();
	
	  %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
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

<form name="spForm" method="post" action="../../ThongtinsanphamUpdateSvl"> 
 <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
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
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;Thi???t l???p d??? li???u n???n &gt; D??? li???u n???n s???n ph???m &gt; Th??ng tin s???n ph???m > Hi???n th??? </TD>
							 <TD colspan="2" align="right" class="tbnavigation">
							 	Ch??o m???ng b???n   <%= spBean.getNppTen() %> &nbsp;
							 </TD></tr>
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
									<LEGEND class="legendtitle" style="color:black">Th??ng tin s???n ph???m </LEGEND>
	
									<TABLE border="0" width="100%" cellpadding="4" cellspacing="0">
										<TR>
											<TD width="25%" class="plainlabel" >M?? s???n ph???m <FONT class="erroralert"> *</FONT></TD>
											<TD width="55%" class="plainlabel"><INPUT type="text" name="masp" size="13" value = '<%=spBean.getMasp() %>'></TD>
										    <TD class="plainlabel">Tr???ng th??i </TD>
										    <TD class="plainlabel">Ho???t ?????ng &nbsp;
  												<% if (spBean.getTrangthai().equals("1")){ %>
													<input name="trangthai" type="checkbox" value = "1" checked >
												<%}else{ %>
													<input name="trangthai" type="checkbox" value = "0"  >
												<%} %>
											</TD>
										</TR>
										<!-- <TR>
											<TD class="plainlabel">M?? s???n ph???m ?????i ?????ng Ti???n<FONT class="erroralert">*</FONT></TD>
										  	<TD colspan = "3" class="plainlabel"><input name="maddt" type="text" size="40" value = '<%=spBean.getMaddt() %>'></TD>
						  				</TR>	
						  				 -->
										<TR>
											<TD class="plainlabel">T??n s???n ph???m<FONT class="erroralert">*</FONT></TD>
										  	<TD colspan = "3" class="plainlabel"><input name="tensp" type="text" size="40" value = '<%=spBean.getTen() %>'></TD>
						  				</TR>
										<TR>
							  				<TD class="plainlabel">????n v??? ??o l?????ng <FONT class="erroralert"> *</FONT></TD>
						  	  				<TD colspan = "3" class="plainlabel">
												<select name="dvdlId" id="dvdlId" onChange="updateUoM();">
												<option value="" > </option>
												<%
													if(dvdl != null){
													try{
														while(dvdl.next()){
								  							if (dvdlId.equals(dvdl.getString("dvdlId"))){ %>											
								  								<option value='<%= dvdl.getString("dvdlId")%>' selected><%= dvdl.getString("dvdlTen")%></option>
								  		  					<%}else{ %>		
								  		  						<option value='<%= dvdl.getString("dvdlId")%>' ><%= dvdl.getString("dvdlTen")%></option>
								  							<%		}
								  						}
														dvdl.close();
								  					}catch (java.sql.SQLException e){}} %>
															
										  	  	</select>											
										  	 </TD>
										</TR>
										<TR>
										  <TD class="plainlabel">????n v??? kinh doanh<FONT class="erroralert">*</FONT></TD>
										  <TD colspan="3" class="plainlabel">
										  	<select name="dvkdId" onChange="submitform();">
												<option value="" ></option>
												<% 
													if(dvkd != null){
													try{
														while(dvkd.next()){
								  							if (dvkdId.equals(dvkd.getString("dvkdId"))){%>								  								
								  								<option value='<%= dvkd.getString("dvkdId")%>' selected ><%=dvkd.getString("dvkdTen") %></option>
												<%			}
								  							else{ %>
								  								<option value='<%= dvkd.getString("dvkdId")%>'  ><%=dvkd.getString("dvkdTen") %></option>
								  				<%			}
								  						}
														dvkd.close();
								  					}catch (java.sql.SQLException e){}} %>

										    </select>
										  </TD>
									  	</TR>
										<TR>
											  <TD class="plainlabel">Nh??n h??ng <FONT class="erroralert">*</FONT></TD>
											  <TD colspan="3" class="plainlabel">
											  		<% if (dvkdId.length()>0){ %>
											  			<select name="nhId" onChange="submitform();">
											  		<%}else{ %>
											  			<select name="nhId" disabled="disabled">
											  		<%} %>
											  		
															<option value="" ></option>
														<% 
															if(nh != null){
															try{
																while(nh.next()){
									  							if (nhId.equals(nh.getString("nhId"))){%>								  								
							  										<option value='<%= nh.getString("nhId")%>' selected ><%=nh.getString("nhTen") %></option>
														<%		}
							  									else{ %>
							  										<option value='<%= nh.getString("nhId")%>'  ><%=nh.getString("nhTen") %></option>
							  							<%		}
															
								  								}
																nh.close();
								  							}catch (java.sql.SQLException e){}} %>
											  			
												</select>
										  </TR>
										  <TR>
											  <TD class="plainlabel">Ch???ng lo???i<FONT class="erroralert"> *</FONT></TD>
											  <TD colspan="3" class="plainlabel">
											  		
											  		<% if ((nhId.length() >0)&(dvkdId.length()>0)){ %>
											  			<select name="clId" >
											  		<%}else{ %>
											  			<select name="clId" disabled="disabled">
											  		<%} %>

															<option value="" ></option>
														<% 
															if(cl != null){
															try{
																while(cl.next()){
									  							if (clId.equals(cl.getString("clId"))){%>								  								
							  										<option value='<%= cl.getString("clId")%>' selected ><%=cl.getString("clTen") %></option>
														<%		}
							  									else{ %>
							  										<option value='<%= cl.getString("clId")%>'  ><%=cl.getString("clTen") %></option>
							  							<%		}															
								  								}
																cl.close();
								  							}catch (java.sql.SQLException e){}} %>
											  			

												    </select></TD>
										  </TR>
										  <TR>
											  <TD class="plainlabel">Gi?? b??n l??? chu???n <FONT class="erroralert"> *</FONT></TD>
											  <TD colspan="3" class="plainlabel" align="left">
											  <%
											  String gia="";
											  if(spBean.getGiablc().trim().length()!=0)
											  {
												 gia= formatter.format(Double.parseDouble(spBean.getGiablc()));
											  }
											  else
											  {
												  gia=spBean.getGiablc();
											  }
											  %>
											  		<input name="giablc" type="text" size="10" value="<%=gia %>" onkeyup="Dinhdang(this)"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<%--   <% if(spBean.getType().equals("1")) {%>
											  		<input name="type" type="checkbox" id="type" value="1" checked="checked" onChange="submitform();"> is Bundle		
											  <%}else{ %>
											  		<input name="type" type="checkbox" id="type" value="0" onChange="submitform();"> is Bundle	
											  <%} %>	 --%>			  				
											  </TD>
										  </TR>
									</TABLE>
									
									<%try{ if(splistSelected != null){ int mm = 0; %>
									  		<fieldset><legend class="legendtitle"> S???n ph???m thu???c Bundle </legend>
									  		<table style="width: 100%" cellpadding="4" cellspacing="1">
									  		<tr class="tbheader">
									  			<th align="center">M?? s???n ph???m</th>
									  			<th align="center">T??n s???n ph???m</th>
									  			<th align="center">Ch???n</th>
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
												<LEGEND class="legendtitle" style="color:black">Nh??m s???n ph???m</LEGEND>
												<TABLE border="0" width="100%" cellpadding="4" cellspacing="1">
													<TR class="tbheader">
														<TH width="30%" >Nh??m s???n ph???m </TH>
														<TH width="60%" >Di???n gi???i </TH>
														<TH width="10%" >Ch???n</TH>
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
												<LEGEND class="legendtitle">&nbsp;Thi???t l???p quy c??ch</LEGEND>
												<TABLE  border="0" cellpadding="0" style="border:1px" cellspacing="1" width="100%">
															<TR class="tbheader" >
																<TH width="21%" >S??? l?????ng </TH>
																<TH width="21%" >????n v??? ??o l?????ng </TH>
																<TH width="16%" >Quy ?????i </TH>
																<TH width="21%" >S??? l?????ng </TH>
																<TH width="21%" >????n v??? ??o l?????ng </TH>
															</TR>
												<%			String[] sl1 = spBean.getSl1();
															String[] sl2 = spBean.getSl2();
															String[] dvdl1 = spBean.getDvdl1();	
															String[] dvdl2 = spBean.getDvdl2();
															if(!(dvdl1[0] == null) & !(dvdl2[0] == null)){
																if (!dvdl1[0].equals(dvdlId)){ 
																	dvdl1[0] = dvdlId;
																	sl1[0] = (sl1[0] == null ? "" : sl1[0]);
																}
															
																if (!dvdl2[0].equals("100018")){ 
																	dvdl2[0] = "100018";
																	sl2[0] = "";
																} 
															}else{																
												 				dvdl1[0] = dvdlId;
												 				sl1[0] = (sl1[0] == null ? "" : sl1[0]);
																dvdl2[0] = "100018";
																sl2[0] = "";										
																
															}
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
																		<option value="100018">Thung </option>
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
																		}catch(java.sql.SQLException e){}%>
																	<%}} %>																			
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
</BODY>

</HTML>

<% 

	try{
		
		if(cl != null)
			cl.close();
		if(dvdl != null)
			dvdl.close();
		if(dvkd != null)
			dvkd.close();
		if(nh != null)
			nh.close();
		if(nspSelected != null)
			nspSelected.close();
		if(splistSelected != null)
			splistSelected.close();
		nspIds.clear();
		spIds.clear();
		if(spBean != null){
			spBean.DBClose();
			spBean = null;}		
	
	}
	catch (Exception e) {}
	
%>
<%}%>


