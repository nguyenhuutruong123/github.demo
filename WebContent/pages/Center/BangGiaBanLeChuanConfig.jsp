<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.beans.banggiablc.IBanggiablc" %>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>

<% IBanggiablc bgblcBean = (IBanggiablc)session.getAttribute("bgblcBean"); %>
<% ResultSet kv = (ResultSet)bgblcBean.getKhuvucList();  %>
<% ResultSet nppList = (ResultSet)bgblcBean.getNppList(); %>
<% ResultSet dvkd = (ResultSet)bgblcBean.getDvkdIds(); %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="java.sql.SQLException"%>
<HTML>
<HEAD>
<TITLE>Acecook\\ - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">

<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<style type="text/css" >
/* .tblightrowa {
	background-color: #FFFFFF;
	font-family: Arial, Helvetica, sans-serif;
	font-size: 10pt;
	color: #000000;
	line-height: 12px;
}
.tblighta {
	background-color: #EEFFFF;
	cellpadding: 2;
	font-family: Arial, Helvetica, sans-serif;
	font-size: 10pt;
}
 */
</style>
<SCRIPT language="javascript" type="text/javascript">
function submitform()
{   
	document.forms['bgmuanppForm'].action.value='lockhuvuc';
    document.forms["bgmuanppForm"].submit();
}

 function saveform()
{
	document.forms['bgmuanppForm'].action.value='saveconfig';
    document.forms["bgmuanppForm"].submit();
}

</SCRIPT>
 <script type="text/javascript">
 
 function checkedAllgiam() {
		var nppIds = new Array(parseInt(document.getElementById("table").rows.length));	
		for (var i =0; i < nppIds.length; i++) 
	 	{
		 	var cb = "nppgiam" + i;
		 	var objCheckBoxes = document.getElementById(cb);
		 	if(!objCheckBoxes.checked)
		 		{
		 			objCheckBoxes.checked=true;
		 		}
		 	else
		 		{
		 		objCheckBoxes.checked=false;
		 		}

	 	}
 }
 function checkedAlltang() {
		var nppIds = new Array(parseInt(document.getElementById("table").rows.length));	
		for (var i =0; i < nppIds.length; i++) 
	 	{
		 	var cb = "npptang" + i;
		 	var objCheckBoxes = document.getElementById(cb);
		 	if(!objCheckBoxes.checked)
		 		{
		 		objCheckBoxes.checked=true;
		 		}
		 	else
	 		{
	 			objCheckBoxes.checked=false;
	 		}
	 		
	 	}
}
 </script>


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

<form name="bgmuanppForm" method="post" action="../../BanggiabanlechuanUpdateSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%=bgblcBean.getUserId() %>'>
<input type="hidden" name="action" value='assign'>
<input type="hidden" name="id" value='<%= bgblcBean.getId() %>'>
 <input type="hidden" id="soluong"  name="soluong" value="<%=bgblcBean.getSoluong()%>"  readonly="readonly">
 <div style="display: none;">
<SELECT   name="dvkdId"  style="width:300px" onChange = "submitform();">
<option value =""></option>

<%
	 	//System.out.println(bgblcBean.getDvkdId());
try{ 
	while(dvkd.next()){ 
		 //System.out.println(dvkd.getString("dvkdId").trim());
  	if(dvkd.getString("dvkdId").trim().equals(bgblcBean.getDvkdId().trim())){ %>
<option value='<%=dvkd.getString("dvkdId") %>' selected><%=dvkd.getString("dvkd") %></option>
<%}else{ %>
<option value='<%=dvkd.getString("dvkdId") %>'><%=dvkd.getString("dvkd") %></option>
<%}}}catch(java.sql.SQLException e){} %>	
        </select>
        </div>
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF"><!--begin body Dossier-->
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;Thiết lập dữ liệu > Dữ liệu nền sản phẩm > Bảng giá bán lẻ chuẩn > Áp cho nhà phân phối</TD> 
							 <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %>&nbsp;  </TD></tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
			<TR ><TD >
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR class = "tbdarkrow">
							<TD width="30" align="left"><A href="javascript:history.back()" ><img src="../images/Back30.png" alt="Quay về"  title="Quay về" border="1" longdesc="Quay về" style="border-style:outset"></A></TD>
						    <TD width="2" align="left" ></TD>
						    <TD width="30" align="left" ><A href="javascript: saveform()" ><IMG src="../images/Save30.png" title="Lưu lại" alt="Lưu lại" border = "1"  style="border-style:outset"></A></TD>
							<TD >&nbsp; </TD>						
						</TR>
					</TABLE>
			</TD></TR>
		</TABLE>
		<TABLE width="100%" border="0" cellpadding="0"  cellspacing="1" >
		  	<tr>
				<TD align="left" colspan="4" class="legendtitle">
					<FIELDSET>
					<LEGEND class="legendtitle">Thông báo </LEGEND>
				
	    			<textarea name="dataerror"  style="width:100%" rows="1"><%=bgblcBean.getMessage()%></textarea>
					<% bgblcBean.setMessage(""); %>
					</FIELDSET>
			   </TD>
			</tr>			

		 	<TR>
				<TD >
			        <FIELDSET>
			        <LEGEND class="legendtitle">&nbsp;Thông tin bảng giá bán cho NPP </LEGEND>
					<TABLE width="100%"cellpadding="0" cellspacing="1">
						<TR>
							<TD>				
								<TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
									<TR>
										<TD width="100%" align="center">
											<TABLE class="tblight" width="100%" cellpadding="6" cellspacing="0">
												<TR>
													<TD width="15%" class="plainlabel">Tên bảng giá <FONT class="erroralert">*</FONT></TD>
													<TD  class="plainlabel"><INPUT name="bgTen" value="<%= bgblcBean.getTen() %>"  type="text" style="width:300px" readonly="readonly"/></TD>
										
												</TR>
												<TR>
							   	 					<TD class="plainlabel">Khu vực <FONT class="erroralert"> *</FONT></TD>
								 					<TD colspan="4" class="plainlabel">
								 						<SELECT name="kvId" id="KhuVuc" onChange = "submitform();" style="width:300px">
								    						<option value=""></option>
								      					<% try{while(kv.next()){ 
								    					if(kv.getString("kvId").equals(bgblcBean.getKhuvuc())){ %>
								      						<option value='<%=kv.getString("kvId")%>' selected><%=kv.getString("kvTen") %></option>
												      	<%}else{ %>
												     		<option value='<%=kv.getString("kvId")%>'><%=kv.getString("kvTen") %></option>
								    				 	<%}}}catch(java.sql.SQLException e){} %>	  
                        								</SELECT>	
                        							</TD>
                        			
												</TR>

											</TABLE>								
										</TD>
									</TR>
								</TABLE>
							</TD>
						</TR>
				</TABLE>
				</FIELDSET>
						<TABLE id="table" width="100%" border="0" cellspacing="1" cellpadding="4">							
								<TR class="tbheader">
									<TH width="25%">Mã nhà phân phối</TH>
									<TH width="45%">Tên nhà phân phối </TH>
									<TH width="15%">Giảm 						
										<input type="checkbox" name="chon" onClick="checkedAllgiam()">																	
									</TH>
									<TH width="15%">Tăng 						
										<input type="checkbox" name="chon" onClick="checkedAlltang()">																	
									</TH>
								</TR>

								<% ResultSet rs = null;
								   
								   String lightrow = "tblightrowa";
								   String darkrow = "tbdarkrowa";
								   int m = 0;
						   	       rs = nppList;
								   
								   if (!(rs == null))
								   {			

								   		while (rs.next())
								   		{								   			
											if (true) {%>						
												<TR style="cellpadding: 2;font-family: Arial, Helvetica, sans-serif;font-size: 10pt;" onMouseOver="this.bgColor='C5E8CD';" onMouseOut="this.bgColor='#FFFFFF';" >
										<%  }  {%>
												<TR style="cellpadding: 2;font-family: Arial, Helvetica, sans-serif;font-size: 10pt;cursor: pointer;" onMouseOver="this.bgColor='#C5E8CD';" onMouseOut="this.bgColor='#FFFFFF';">
										<%  } %>	
											<% String ma= rs.getString("pk_seq");%>
												<TD align="left" class="textfont"><%= ma %></TD>
												<TD align="center"><div align="left"><%= rs.getString("ten")%></div></TD>
												<TD align="center">
												<%
													String[] a= new String[3];
													
													String giam=rs.getString("DIEUCHINHGIAM");
													String tang=rs.getString("DIEUCHINHTANG");
													if(bgblcBean.getNpp()!=null)
													{
														for(int k=0;k<bgblcBean.getNpp().length;k++)
														{
															if(bgblcBean.getNpp()[k] !=null)
															{
																a = bgblcBean.getNpp()[k].split("-");
																if(ma.equals(a[0]))
																{
	
																	tang=a[1];
																	giam=a[2];
																	break;
																}
															}
														}
													}
													if(giam.equals("1")){
													%>
													<input type="hidden"  name="manpp<%=m %>" value=<%=ma%>  readonly="readonly"><input id="nppgiam<%=m%>" name="nppgiam<%=m%>"  type="checkbox" checked="checked"/>		
													<%
													}
													else
													{
													%>
													<input type="hidden"  name="manpp<%=m %>" value=<%=ma%>  readonly="readonly"><input id="nppgiam<%=m%>" name="nppgiam<%=m%>"  type="checkbox" />								
													<%} %>
												</TD>
												<TD align="center">
												<%
													if(tang.equals("1")){
													%>
													<input type="hidden"  name="manpp<%=m %>" value=<%=ma%>  readonly="readonly"><input id="npptang<%=m%>" name="npptang<%=m%>"  type="checkbox" checked="checked"/>			
													<%
													}
													else
													{
													%>
													<input type="hidden"  name="manpp<%=m %>" value=<%=ma%>  readonly="readonly"><input id="npptang<%=m%>" name="npptang<%=m%>"  type="checkbox"/>									
													<%} %>	
																				
												</TD>
												</TR>
												
												
									<% 	m++;
												}
										}	
									   
									%>	
								  
						</TABLE>
													
						</FIELDSET>						
					</TD>
				</TR>
			</TABLE>
		</TD>
	</TR>
	</TABLE>
</form>
<%

%>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</HTML>
<%}%>