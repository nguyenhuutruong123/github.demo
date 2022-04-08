<%@page import="java.util.Hashtable"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.sql.SQLException"%>
<%@ page import = "java.util.Iterator" %>
<%@ page import = "java.util.List" %>
<%@ page import = "java.sql.ResultSet" %>
<%@ page import = "geso.dms.center.beans.themnppctkm.*" %>
<%@ page import = "geso.dms.center.util.*" %>

<%
	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if (!util.check(userId, userTen, sum))
	{
		response.sendRedirect("/Vifon/index.jsp");
	} else
	{
%>
<%
		IThemNppCtkm obj = (IThemNppCtkm) session.getAttribute("fileBean");

		String[] ctkmId = obj.getCtkmId();
		String[] scheme = obj.getScheme();
		String[] diengiai = obj.getDiengiai();
		String[] ngansach = obj.getNganSach();
		String[] loaingansach = obj.getLoaingansach();
		
		ResultSet nppRs = obj.getNppRs();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<TITLE>Vifon - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />

<style type="text/css">
#mainContainer {
	width: 660px;
	margin: 0 auto;
	text-align: left;
	height: 100%;
	border-left: 3px double #000;
	border-right: 3px double #000;
}

#formContent {
	padding: 5px;
}
/* END CSS ONLY NEEDED IN DEMO */

/* Big box with list of options */
#ajax_listOfOptions {
	position: absolute; /* Never change this one */
	width: auto; /* Width of box */
	height: 200px; /* Height of box */
	overflow: auto; /* Scrolling features */
	border: 1px solid #317082; /* Dark green border */
	background-color: #C5E8CD; /* White background color */
	color: black;
	text-align: left;
	font-size: 1.0em;
	z-index: 100;
}

#ajax_listOfOptions div {
	/* General rule for both .optionDiv and .optionDivSelected */
	margin: 1px;
	padding: 1px;
	cursor: pointer;
	font-size: 1.0em;
}

#ajax_listOfOptions .optionDiv { /* Div for each item in list */
	
}

#ajax_listOfOptions .optionDivSelected { /* Selected item in the list */
	background-color: #317082; /*mau khi di chuyen */
	color: #FFF;
}

#ajax_listOfOptions_iframe {
	background-color: #F00;
	position: absolute;
	z-index: 5;
}

form {
	display: inline;
}

#dhtmltooltip {
	position: absolute;
	left: -300px;
	width: 150px;
	border: 1px solid black;
	padding: 2px;
	background-color: lightyellow;
	visibility: hidden;
	z-index: 100;
	/*Remove below line to remove shadow. Below line should always appear last within this CSS*/
	filter: progid:DXImageTransform.Microsoft.Shadow(color=gray, direction=135
		);
}

#dhtmlpointer {
	position: absolute;
	left: -300px;
	z-index: 101;
	visibility: hidden;
}
}
</style>

<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<script type="text/javascript" language="JavaScript" src="../scripts/jquery.tools.min.js"></script>

<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function() {		
		$( ".days" ).datepicker({			    
				changeMonth: true,
				changeYear: true				
		});            
	});	
	
	function replaces()
	{
		var scheme = document.getElementsByName("scheme");
		var diengiai = document.getElementsByName("diengiai");  
		var loaingansach = document.getElementsByName("loaingansach");
		var ctkmId = document.getElementsByName("ctkmId");
		var i;
		for(i = 0; i < scheme.length; i++)
		{
			if(scheme.item(i).value != "")
			{
				var sp = scheme.item(i).value;
				var pos = parseInt(sp.indexOf(" - "));

				if(pos > 0)
				{
					scheme.item(i).value = sp.substring(0, pos);
					sp = sp.substr(parseInt(sp.indexOf(" - ")) + 3);
					
					diengiai.item(i).value = sp.substring(0, parseInt(sp.indexOf(" [")));
					sp = sp.substr(parseInt(sp.indexOf(" [")) + 2);
					
					loaingansach.item(i).value = sp.substring(0, parseInt(sp.indexOf("] [")));
					sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
					
					
					ctkmId.item(i).value = sp.substring(0, parseInt(sp.indexOf("]"))); 
				}
			}
			else
			{
				scheme.item(i).value = "";
				diengiai.item(i).value = "";
				loaingansach.item(i).value = "";	
				ctkmId.item(i).value = "";	
			}
		}	 
		setTimeout(replaces, 300);
	}
	
	 function save()
	 {             
		document.forms['khtt'].action.value='save';
	    document.forms["khtt"].submit();
	 }
	
	
</script>

<script type="text/javascript" src="../scripts/ajax.js"></script>
<script type="text/javascript" src="../scripts/AjaxThemNppCtkm.js"></script>

<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
$(document).ready(function()
{
	$("#nppId").select2();
});
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

<% Csrf csdr=new Csrf(request,response,true,false,true);%>
<input type="hidden" name="userId" value="<%=userId%>" >
<input type="hidden" name="action" value="0">
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align="left" valign="top" bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
							<TR height="22">
								<TD align="left" colspan="2" class="tbnavigation">&nbsp; Quản lý Khuyến mãi > Khai báo > Thêm Npp CTKM >Hiển thị</TD>
								<TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen%></TD>
							</TR>
						</TABLE>
					</TD>
				</TR>
			</TABLE>
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR >
					<TD >
						<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
							<TR class = "tbdarkrow">
								<TD width="30" align="left">
									<A href="../../ThemNppCtkmSvl?userId=<%=userId%>" >
										<img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset">
									</A>
								</TD>
								<TD width="2" align="left" ></TD>
								<TD width="30" align="left" >
									<div id="btnSave">
										
									</div>
								</TD>
								<TD >&nbsp; </TD>
							</TR>
						</TABLE>
					</TD>
				</TR>
			</TABLE>
			<TABLE width="100%" border="0" cellpadding="0"  cellspacing="1" >
				<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
							<LEGEND class="legendtitle">Thông báo </LEGEND>
							<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold"
								style="width: 100% ; color:#F00 ; font-weight:bold" readonly="readonly" rows="1"><%=obj.getMsg()%>
							</textarea>
						</FIELDSET>
					</TD>
				</tr>
				<TR>
					<TD height="100%" width="100%">
						<FIELDSET >
							<LEGEND class="legendtitle" style="color:black">Thông tin Thêm Npp CTKM</LEGEND>
							<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">

								<TR>
									<TD class="plainlabel"  width="120px">Ghi chú</TD>
									<TD class="plainlabel"  width="240px">
										<input type="text" name="ghichu" value="<%=obj.getGhichu()%>"  >
									</TD>
									<TD class="plainlabel" width="100px"></TD>
									<TD class="plainlabel"></TD>
								</TR>
								
								
								
									<TR>													
												<td width="9%" class="plainlabel">Nhà phân phối</td>
									<td width="33%" class="plainlabel" colspan="3">
									<SELECT name="nppId" id="nppId" style="width:450px"  >
									<option value=""> </option>
									<% if(nppRs != null){
										  try
										  { 
											  String optionGroup = "";
											  String optionName = "";
											  int i = 0;
											  
											  while(nppRs.next())
											  { 
												 if(i == 0)
												 {
													 optionGroup = nppRs.getString("kvTen");
													 optionName = nppRs.getString("kvTen");
													 
													 %>
													 
													 <optgroup label="<%= optionName %>" >
												 <% }
												 
												 optionGroup = nppRs.getString("kvTen");
												 if(optionGroup.trim().equals(optionName.trim()))
												 { %>
													 <% if(nppRs.getString("nppId").equals(obj.getNppId())) {%>
													 	<option value="<%= nppRs.getString("nppId") %>" selected="selected" ><%= nppRs.getString("nppTen") %></option>
													 <%} else { %>
													 	<option value="<%= nppRs.getString("nppId") %>"><%= nppRs.getString("nppTen") %></option>
													 <%} %>
												 <% }
												 else
												 {
													 %>
													</optgroup>
													<% optionName = optionGroup; %>
													<optgroup label="<%= optionName %>" >
													<% if(nppRs.getString("nppId").equals(obj.getNppId())) {%>
													 	<option value="<%= nppRs.getString("nppId") %>" selected="selected" ><%= nppRs.getString("nppTen") %></option>
													 <%} else { %>
													 	<option value="<%= nppRs.getString("nppId") %>"><%= nppRs.getString("nppTen") %></option>
													 <%} %>
												 <% }
												 i++;
								     	 	  } 
											  %>
											  	</optgroup>
											  <%nppRs.close(); 
								     	 }catch(java.sql.SQLException e){}}%>	  
                                	</SELECT>
								</td>
													</TR>
								

							</TABLE>
							
							
					<table cellpadding="0px" cellspacing="1px" width="100%">
					<tr class="tbheader">
						<th align="center" width="15%" >Scheme</th>
						<th align="center" width="45%" >Diễn giải</th>
						<th align="center" width="10%" > Ngân sách  </th>
					</tr>

					<%
						int count = 0;
							if ( ctkmId != null)
							{
								for (int i = 0; i < ctkmId.length; i++)
								{
					%>
								<tr>
									<td>
										<input type="text" name="scheme" value="<%=scheme[i]%>" style="width: 100%"  onkeyup="ajax_showOptions(this,'nhapkho',event)" AUTOCOMPLETE="off"  > 
										 <input type="hidden" name="ctkmId" value="<%=ctkmId[i]%>" >
										    <input type="hidden" name="loaingansach" value="<%=loaingansach[i]%>" >
									</td>
									<td> <input type="text" name="diengiai" value="<%=diengiai[i]%>" style="width: 100%"  > </td>									
									<td><input type="text" name="ngansach" value="<%=ngansach[i]%>" style="width: 100%"  > </td>
								</tr>	
								
						<%
																count++;
																		}
																	}
															%>
					
					<%
											for (int i = count; i < 50; i++)
												{
										%>
						
						<tr>
									<td>
										<input type="text" name="scheme" value="" style="width: 100%"  onkeyup="ajax_showOptions(this,'nhapkho',event)" AUTOCOMPLETE="off"  > 
										 <input type="hidden" name="ctkmId" value="" >
										 <input type="hidden" name="loaingansach" value="" >
									</td>
									<td> <input type="text" name="diengiai" value="" style="width: 100%"  > </td>
									<td><input type="text" name="ngansach" value="" style="width: 100%"  > </td>
								</tr>	

						
					<%
													}
												%>	
				</table>							
						</FIELDSET>
					</TD>
				</TR>
			</TABLE>
		</TD>
	</TR>
</TABLE>

<script type="text/javascript">
	replaces();
</script>


</form>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</HTML>
<%
	}
%>
