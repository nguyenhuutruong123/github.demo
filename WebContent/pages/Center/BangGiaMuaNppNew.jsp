<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.beans.banggiamuanpp.IBanggiamuanpp" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.NumberFormat"%>
<%	

	NumberFormat formatter = new DecimalFormat("#,###,###"); 
	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>

<% IBanggiamuanpp bgmuanppBean = (IBanggiamuanpp)session.getAttribute("bgmuanppBean"); %>
<% ResultSet dvkd = (ResultSet)bgmuanppBean.getDvkdIds(); %>
<% ResultSet kenh = (ResultSet)bgmuanppBean.getKenhIds(); %>
<% String[] spIds = bgmuanppBean.getSpIds() ; %>
<% String[] masp = bgmuanppBean.getMasp() ; %>
<% String[] tensp = bgmuanppBean.getTensp() ; %>
<% String[] giamua = bgmuanppBean.getGiamuanpp() ; %>
<% String[] dv = bgmuanppBean.getDonvi() ; %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">

<SCRIPT language="javascript" type="text/javascript">
function submitform()
{   
   document.forms["bgmuanppForm"].submit();
}

 function saveform()
{
	document.forms['bgmuanppForm'].action.value='save';
    document.forms["bgmuanppForm"].submit();
}
 function DinhDangTien(num) 
 {
    num = num.toString().replace(/\$|\,/g,'');
    if(isNaN(num))
    num = "0";
    sign = (num == (num = Math.abs(num)));
    num = Math.floor(num*100+0.50000000001);
    num = Math.floor(num/100).toString();
    for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
    num = num.substring(0,num.length-(4*i+3))+','+
    num.substring(num.length-(4*i+3));
    return (((sign)?'':'-') + num);
}
 function Dinhdang(element)
{
	element.value=DinhDangTien(element.value);
	if(element.value== '' ||element.value=='0' )
	{
		element.value= '';
	}
}

 function checkedAll() {
	var spIds = new Array(<%= bgmuanppBean.getMaspstr() %>);	
	for (var i =0; i < spIds.length; i++) 
 	{
	 	var cb = "chbox" + spIds[i];
	 	var objCheckBoxes = document.forms["bgmuanppForm"].elements[cb];
		if (document.forms["bgmuanppForm"].elements["chon"].checked == false){
			objCheckBoxes.checked = false;
		}else{
			objCheckBoxes.checked = true;
		}
 	}
}
 </SCRIPT>


<!-- BEGIN RENDER AUTO -->

<% if (!geso.dms.center.util.GlobalValue.dev_mode) { %>
	<script type="text/javascript" src="../scripts/disableF12.js"></script>
<% } %>

<!-- END RENDER AUTO -->
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<!-- BEGIN RENDER AUTO -->
<%if(!geso.dms.center.util.GlobalValue.dev_mode){ %>
	<noscript> 
	 	<meta http-equiv="refresh" content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")  %>">
		<!-- //window.location = "https://www.google.com"; -->
	</noscript>
<%} %>
<!-- END RENDER AUTO -->

<form name="bgmuanppForm" method="post" action="../../BanggiamuanppUpdateSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%=bgmuanppBean.getUserId() %>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="id" value=''>

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"	>
	
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
					   <table width="100%" border="0" cellpadding="0" cellspacing="0">
						   <tr height="22">
 							   <TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền &gt; Sản phẩm &gt; Bảng giá bán cho NPP &gt; Tạo mới</TD>
 							   <TD colspan="2" align="right" class="tbnavigation">Chào mừng Bạn <%= bgmuanppBean.getUserTen()  %>&nbsp;  </TD>
					     </tr>
						</table>
					 </TD>
				  </TR>	
		  	</TABLE>


			<TABLE width="100%" cellpadding="0" cellspacing="1">
			<TR ><TD >
				<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
					<TR class = "tbdarkrow">
						<TD width="30" align="left"><A href="javascript:history.back()" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
					    <TD width="2" align="left" ></TD>
					    <TD width="30" align="left" ><A href="javascript: saveform()" ><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A></TD>
				    	<TD align="left" >&nbsp;</TD>
					</TR>
				</TABLE>
			</TD></TR>
			</TABLE>

		<TABLE width="100%"  border = "0" cellspacing="0" style="padding:2px" cellpadding="2px" >
			  	<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>
				
	    				<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold"  style="width: 100% ; color:#F00 ; font-weight:bold" readonly="readonly" rows="1" ><%=bgmuanppBean.getMessage()%></textarea>
						<% bgmuanppBean.setMessage(""); %>
						</FIELDSET>
				   </TD>
				</tr>			

		 	<TR>
				<TD >
			        <FIELDSET>
			        <LEGEND class="legendtitle">&nbsp;Bảng giá bán cho NPP </LEGEND>
					<TABLE width="100%"cellpadding="0" cellspacing="1">
						<TR>
							<TD>				
								<TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
									<TR>
										<TD width="100%" align="center">
											<TABLE class="tblight" width="100%" cellpadding="6" cellspacing="0">
												<TR>
													<TD width="15%" class="plainlabel">Tên bảng giá <FONT class="erroralert"></FONT></TD>
													<TD  class="plainlabel"><INPUT name="bgTen" value="<%= bgmuanppBean.getTen() %>"
										type="text"  style="width:300px" ></TD>
												</TR>
												<TR>
								    				<TD class="plainlabel">Đơn vị kinh doanh</TD>
								      				<TD class="plainlabel">
								      				<SELECT name="dvkdId" onChange = "submitform();" style="width:300px">								      
								  	 					<option value =""></option>
								  	 					<% try{ while(dvkd.next()){ 
								  	 							if(dvkd.getString("dvkdId").equals(bgmuanppBean.getDvkdId())){ %>
								      								<option value='<%=dvkd.getString("dvkdId")%>' selected><%=dvkd.getString("dvkd") %></option>
								      						   <%}else{ %>
								     								<option value='<%=dvkd.getString("dvkdId")%>' ><%=dvkd.getString("dvkd")  %></option>
								     							<%}}}catch(java.sql.SQLException e){} %>	
								     	
													</SELECT></TD>
												</TR>
												<TR>
								  					<TD class="plainlabel">Kênh bán hàng </TD>
								  					<TD class="plainlabel">
								      					<SELECT name="kenhId" onChange = "submitform();" style="width:300px">								      
								  	 						<option value =""></option>
								  	 					<% try{ while(kenh.next()){ 
								    							if((kenh.getString("kenhId").trim()).equals(bgmuanppBean.getKenhId().trim())){ %>
								      								<option value='<%=kenh.getString("kenhId")%>' selected><%=kenh.getString("kenh") %></option>
								      						  <%}else{ %>
								     								<option value='<%=kenh.getString("kenhId")%>'><%=kenh.getString("kenh") %></option>
								     						<%}}}catch(java.sql.SQLException e){} %>	
								     	
                                  						</SELECT></TD>
									  			</TR>
												<TR>							
							    					<TD  class="plainlabel" colspan=2 align=left>  									
							    						<% if (bgmuanppBean.getTrangthai().equals("1")){ %>
															<input name="trangthai" type="checkbox" value = "1" checked >
														<%}else{ %>
															<input name="trangthai" type="checkbox" value = "0"  >
														<%} %>
														Hoạt động </TD>
												</TR>	

											</TABLE>								
										</TD>
									</TR>
								</TABLE>
							</TD>
						</TR>
				</TABLE>
				</FIELDSET>
				<TABLE class="tabledetail" cellpadding="2" cellspacing="1" width="100%">
					<TR>
						<TD >
												<TABLE width="100%" border="0" cellspacing="1" cellpadding="0">
								<TR class="tbheader">
									<TH width="20%">Mã sản phẩm </TH>
									<TH width="50%">Tên sản phẩm</TH>								
									<TH width="9%">Giá bán cho NPP </TH>
									<TH width="9%">Đơn vị</TH>
									<TH width="20%">Chọn bán 
									<input type="checkbox" name="chon" onclick="checkedAll();">
									</TH>
								</TR>
								<%
								int j = 0;
								String lightrow = "tblightrow";
								String darkrow = "tbdarkrow";
								for(int i = 0; i < spIds.length; i++){
										if (j % 2 != 0) {%>						
											<TR class= <%=lightrow%> >
									    <%} else {%>
											<TR class= <%= darkrow%> >
										<%}%>
											<TD align="center"><div align="left"><%= masp[i] %> </div></TD>
											<TD align="center"><div align="left"><%= tensp[i] %></div></TD>		
											<%	
											String gia="";
											  if(giamua[i].trim().length()!=0)
											  {
												 gia= formatter.format(Double.parseDouble(giamua[i]));
											  }
											  else
											  {
												  gia=giamua[i];
											  }
											  %>		
											<TD align="center"><input type='text' name='<%= "gia" +  spIds[i] %>' size=16 value="<%=gia %>" onkeyup="Dinhdang(this)" style="text-align: right"/></TD>
											<TD align="center"><%= dv[i] %></TD>
											<TD align="center">											
												<input type="checkbox" name=<%= "chbox"+ spIds[i] %>  value='<%= spIds[i] %>' >											
											</TD>
												
							     		<%j++;
										 
								}%>

							</TABLE>

						</TD>
					</TR>
				</TABLE>
			</TD>
			</TR>
		</TABLE>
	</TR>
</TABLE>
</FORM>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</HTML>
<% if (dvkd != null) dvkd.close(); %>
<% if (kenh != null) kenh.close(); %>
<%bgmuanppBean.closeDB(); %>
<%}%>