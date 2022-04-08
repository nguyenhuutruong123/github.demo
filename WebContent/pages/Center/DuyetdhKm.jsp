
<%@page import="java.sql.ResultSet"%>
<%@page import="geso.dms.center.beans.duyetdhkm.IDuyetDhKmList"%>
 
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 
<%@ page  import = "java.util.List" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{int[] quyen = new  int[5];
	quyen = util.Getquyen("121",userId);
	
	
	
 %>


<% IDuyetDhKmList obj = (IDuyetDhKmList)session.getAttribute("obj");  
	ResultSet rslist=obj.getrslist();
	
	ResultSet npp=(ResultSet)obj.getNppRs();
	
	ResultSet ctkmRs=(ResultSet)obj.getCtkmRs();
%>
<% obj.setNextSplittings(); %>
 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">

<script type="text/javascript" src="../scripts/jquery.min.js"></script>


<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />


<SCRIPT language="javascript" type="text/javascript">
function clearform()
{
    document.vmForm.trangthai.value = "";
    document.vmForm.nppId.value = "";      
    document.vmForm.ctkmId.value = "";
    document.vmForm.thang.value = "";
    document.vmForm.nam.value = "";
    submitform();
}

function submitform()
{
	document.forms['vmForm'].action.value= 'search';
	document.forms['vmForm'].submit();
}

function newform()
{
	document.forms['vmForm'].action.value= 'new';
	document.forms['vmForm'].submit();
}
function thongbao()
{var tt = document.forms['vmForm'].msg.value;
	if(tt.length>0)
    alert(document.forms['vmForm'].msg.value);
	}
	

</SCRIPT>


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

<form name="vmForm" method="post" action="../../DuyetdhkmSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%=userId%>'>
<input type="hidden" name="action" value='1'>

<input type="hidden" name="msg" value='<%=obj.getMsg() %>'>
		<input type="hidden" name="crrApprSplitting" value="<%= obj.getCrrApprSplitting() %>">
		 <input type="hidden" name="nxtApprSplitting" value="<%= obj.getNxtApprSplitting() %>">



<script language="javascript" type="text/javascript">
    thongbao();
</script> 

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#ffffff">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" >
					   <table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							   <TD align="left" colspan="2" class="tbnavigation">
							   		 Quản lý bán hàng > Duyệt đơn khuyến mãi   </TD>
							   <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %>&nbsp;  </TD> 
						    </tr>
   						</table>
					</TD>
				</TR>
			</TABLE>
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD>
						<TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
							<TR>
								<TD width="100%" align="left"><FIELDSET>
									<LEGEND class="legendtitle">Tiêu chí tìm kiếm </LEGEND>

									<TABLE class="tblight" width="100%" cellpadding="6" cellspacing = "0">
										<TR>
										  <TD class="plainlabel" style="width:10%" > Tháng </TD>
										  <TD class="plainlabel" style="width:20%" >
										  	<INPUT name="thang" size="20" type="text" value="<%= obj.getThang() %>" onChange="submitform();">
										  	</TD>
										  	
										  	<TD class="plainlabel" style="width:10%" > Năm </TD>
										  <TD class="plainlabel"  style="width:60%">
										  	<INPUT name="nam" size="20" type="text" value="<%= obj.getNam() %>" onChange="submitform();">
										  	</TD>
										  	
									 	 </TR>
									 	 
									 	<TR>
											<TD class="plainlabel">CTKM  </TD>
											<td  class="plainlabel" colspan="4" >
											<SELECT
												name="ctkmId" onChange="submitform();">
													<option value=""></option>
													<%  try{
														if(ctkmRs!=null)
											  		while(ctkmRs.next()){								  			
											  			if (obj.getCtkmId().equals(ctkmRs.getString("pk_Seq"))){ %>
																			<option value="<%= ctkmRs.getString("pk_Seq")%>" selected><%= ctkmRs.getString("diengiai")%></option>
																			<%}else{ %>
																			<option value="<%= ctkmRs.getString("pk_Seq")%>"><%= ctkmRs.getString("diengiai")%></option>
																			<%		}
											  		}	
											  	}catch (java.sql.SQLException e){} %>
											</SELECT>
											</TD>
										</TR>
									 	 
									 	 
									 	<TR>													
												<td width="9%" class="plainlabel">Nhà phân phối</td>
									<td width="33%" class="plainlabel" colspan="4" >
									<SELECT name="nppId" id="nppId" style="width:350px"   onChange="submitform();" >
									<option value="">All </option>
									<% if(npp != null){
										  try
										  { 
											  String optionGroup = "";
											  String optionName = "";
											  int i = 0;
											  
											  while(npp.next())
											  { 
												 if(i == 0)
												 {
													 optionGroup = npp.getString("kvTen");
													 optionName = npp.getString("kvTen");
													 
													 %>
													 
													 <optgroup label="<%= optionName %>" >
												 <% }
												 
												 optionGroup = npp.getString("kvTen");
												 if(optionGroup.trim().equals(optionName.trim()))
												 { %>
													 <% if(npp.getString("nppId").equals(obj.getNppId())) {%>
													 	<option value="<%= npp.getString("nppId") %>" selected="selected" ><%= npp.getString("nppTen") %></option>
													 <%} else { %>
													 	<option value="<%= npp.getString("nppId") %>"><%= npp.getString("nppTen") %></option>
													 <%} %>
												 <% }
												 else
												 {
													 %>
													</optgroup>
													<% optionName = optionGroup; %>
													<optgroup label="<%= optionName %>" >
													<% if(npp.getString("nppId").equals(obj.getNppId())) {%>
													 	<option value="<%= npp.getString("nppId") %>" selected="selected" ><%= npp.getString("nppTen") %></option>
													 <%} else { %>
													 	<option value="<%= npp.getString("nppId") %>"><%= npp.getString("nppTen") %></option>
													 <%} %>
												 <% }
												 i++;
								     	 	  } 
											  %>
											  	</optgroup>
											  <%npp.close(); 
								     	 }catch(java.sql.SQLException e){}}%>	  
                                	</SELECT>
								</td>
													</TR>
									 	 
										 
										 <TR>
											<TD class="plainlabel" >Trạng thái</TD>
											<td  class="plainlabel" colspan="4" >
											  <select name="trangthai" onChange="submitform();">
											    <% if (obj.getTrangthai().equals("1")){%>
											  	<option value="1" selected>Đã duyệt</option>
											  	<option value="2">Đã bỏ duyệt</option>
											  	<option value=""> </option>
											  
											<%}else if(obj.getTrangthai().equals("2")) {%>
											 	 <option value="2" selected> Đã bỏ duyệt  </option>
											  	<option value="1" >Đã duyệt</option>
											 	 <option value="" > </option>
											  
											<%}else{%>																					 	 
											  <option value="2" selected> Đã bỏ duyệt  </option>
											  	<option value="1" >Đã duyệt</option>
											  	<option value="" selected> </option>
											<%}%>
									          </select></TD>
										</TR> 
										<TR>
										    <TD class="plainlabel" colspan=4>
										    	<a class="button2" href="javascript:clearform()">
	<img style="top: -4px;" src="../images/button.png" alt="">Nhập lại </a>&nbsp;&nbsp;&nbsp;&nbsp;	
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
			<TABLE width="100%" cellpadding="0" cellspacing="1">
			    <TR>
					<TD align="left" >
						 <FIELDSET>
							<LEGEND class="legendtitle">&nbsp; Duyệt đơn hàng KM &nbsp;&nbsp;&nbsp;
							<%if(quyen[0]!=0){ %>
								<a class="button3" href="javascript:newform()">
    	<img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>                            
							<%} %>
						</LEGEND>
				
							<TABLE class="" width="100%" border="0" cellpadding="0" cellspacing="0">
								<TR>
									<TD width="98%">
										<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
											<TR class="tbheader">
											  <TH width="10%">ID </TH>
											    <TH width="10%">Tháng </TH>
											    <TH width="10%">Năm </TH>
											    <TH width="15%">Trạng thái </TH>
											    <TH width="10%">Ngày tạo</TH>
											  <TH width="10%">Người tạo </TH>										
											  <TH width="10%">Ngày taọ </TH>
											  <TH width="10%">Người sửa</TH>
											  <TH width="15%">Tác vụ</TH>
										  </TR>
								<% 
							 
								 
									int m = 0;
									String lightrow = "tblightrow";
									String darkrow = "tbdarkrow";
									if(rslist!=null)
									while (rslist.next()){
										 
										if (m % 2 != 0) {%>						
											<TR class= <%=lightrow%> >
										<%} else {%>
											<TR class= <%= darkrow%> >
										<%}%>
									<TD><div align="left"><%= rslist.getString("pk_seq") %></div></TD>
									<TD><div align="left"><%= rslist.getString("Thang") %></div></TD>
									<TD><div align="left"><%= rslist.getString("Nam") %></div></TD>
									
										 
											  <% if (rslist.getString("trangthai") .equals("1")){ %>
													<TD align="left">Đã duyệt </TD>
												<%}else{%>
													<TD align="left">Đã bỏ duyệt</TD>
												<%}%>
												<TD align="center"><%=rslist.getString("ngaytao") %></TD>
												<TD align="center"><%=rslist.getString("nguoitao") %></TD>
												<TD align="center"><%=rslist.getString("ngaysua")%></TD>
													<TD align="center"><%=rslist.getString("nguoisua")%></TD>
												<TD align="center">
												<TABLE border = 0 cellpadding="0" cellspacing="0">
													<TR><TD>
													  <% if (rslist.getString("trangthai") .equals("1")){ %>
													<%if(quyen[2]!=0){ %>
													  <A href = "../../DuyetdhkmUpdateSvl?userId=<%=userId%>&view=<%= rslist.getString("pk_seq")%>">
                                               <img src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" width="20" height="20" longdesc="Hiển thị" border = 0></A>
													<%} %>
													<%if(quyen[2]!=0){ %>
													  <A href = "../../DuyetdhkmSvl?userId=<%=userId%>&unchot=<%= rslist.getString("pk_seq")%>" onclick="if(!confirm('Bạn có muốn bỏ chốt đơn hàng này?')) {return false ;}">
                                              			 <img src="../images/unChot.png" alt="Bỏ duyệt" title="Bỏ duyệt" width="20" height="20" longdesc="Bỏ duyệt" border = 0></A>
													<%}
													  }else{
														  %> 
														   <A href = "../../DuyetdhkmUpdateSvl?userId=<%=userId%>&view=<%= rslist.getString("pk_seq")%>">
                                               <img src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" width="20" height="20" longdesc="Hiển thị" border = 0></A>
														  <%
													  }
													%>
													</TD>
													 
													</TR></TABLE>
												</TD>
											</TR>
								<%m++; }%>
								<tr class="tbfooter">
														<TD align="center" valign="middle" colspan="13"
															class="tbfooter">
															<%if(obj.getNxtApprSplitting() >1) {%> <img alt="Trang Dau"
															src="../images/first.gif" style="cursor: pointer;"
															onclick="View('ddhForm', 1, 'view')"> &nbsp; <%}else {%>
															<img alt="Trang Dau" src="../images/first.gif">
															&nbsp; <%} %> <% if(obj.getNxtApprSplitting() > 1){ %> <img
															alt="Trang Truoc" src="../images/prev.gif"
															style="cursor: pointer;"
															onclick="View('ddhForm', eval(ddhForm.nxtApprSplitting.value) -1, 'view')">
															&nbsp; <%}else{ %> <img alt="Trang Truoc"
															src="../images/prev_d.gif"> &nbsp; <%} %> <%
													int[] listPage = obj.getNextSplittings();
													for(int i = 0; i < listPage.length; i++){
												%> <% 
												
												System.out.println("Current page:" + obj.getNxtApprSplitting());
												System.out.println("List page:" + listPage[i]);
												
												if(listPage[i] == obj.getNxtApprSplitting()){ %> <a
															style="color: white;"><%= listPage[i] %>/ <%=obj.getTheLastSplitting() %></a>
															<%}else{ %> <a
															href="javascript:View('ddhForm', <%= listPage[i] %>, 'view')"><%= listPage[i] %></a>
															<%} %> <input type="hidden" name="list"
															value="<%= listPage[i] %>" /> &nbsp; <%} %> <% if(obj.getNxtApprSplitting() < obj.getTheLastSplitting()){ %>
															&nbsp; <img alt="Trang Tiep" src="../images/next.gif"
															style="cursor: pointer;"
															onclick="View('ddhForm', eval(ddhForm.nxtApprSplitting.value) +1, 'view')">
															&nbsp; <%}else{ %> &nbsp; <img alt="Trang Tiep"
															src="../images/next_d.gif"> &nbsp; <%} %> <%if(obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {%>
															<img alt="Trang Cuoi" src="../images/last.gif">
															&nbsp; <%}else{ %> <img alt="Trang Cuoi"
															src="../images/last.gif" style="cursor: pointer;"
															onclick="View('ddhForm', -1, 'view')"> &nbsp; <%} %>
														</TD>
													</tr>

							</TABLE>
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

<script type="text/javascript">
	replaces();
	jQuery(function()
	{		
		$("#nppId").autocomplete("Erp_NhaPhanPhoiList.jsp");
	});	
</script>

</form>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</HTML>
<%}%>