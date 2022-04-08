<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.beans.chungloai.IChungloai" %>
<%@ page  import = "java.sql.ResultSet"%>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%@ page  import = "geso.dms.center.util.ChuyenNgu" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>

<% IChungloai cloaiBean = (IChungloai)session.getAttribute("cloaiBean"); 
   ResultSet nhlist = (ResultSet)session.getAttribute("nhlist"); 
   Hashtable nhanhangSelected = (Hashtable)cloaiBean.getNhanhangSelected(); %>
<% String nnId = (String)session.getAttribute("nnId"); %> 
<% if(nnId == null) {
	nnId = "vi";
	} 
	String url = util.getChuyenNguUrl("ChungloaiSvl","",nnId);
	%>
	
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print"
	href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<SCRIPT language="javascript" type="text/javascript">

</SCRIPT>
<SCRIPT language="JavaScript" type="text/javascript">
function submitform()
{
    document.forms["chungloaiForm"].submit();
}

function checkedAll(chk) {
	for(i=0; i<chk.length; i++){
	 	if(document.chungloaiForm.chon.checked==true){
			chk[i].checked = true;
		}else{
			chk[i].checked = false;
		}
	 }
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

<form name="chungloaiForm" method="post" action="../../ChungloaiNewSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<INPUT name="userId" type="hidden" value='<%=userId %>' size="30">

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#ffffff">
			<TABLE width="100%" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">

					   	<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							   <TD align="left" colspan="2" class="tbnavigation">
							   		<%=url %> &gt; Tạo mới</TD>
							   <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %>&nbsp;</td> 
					      </tr>
   
						</TABLE>
					</TD>
				</TR>
			</TABLE>	
			<TABLE width="100%" border="0" cellpadding="3" cellspacing="0">
				<TR ><TD >
						<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
								<TR class = "tbdarkrow">
									<TD width="30" align="left"><A href="javascript:history.back()" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
								    <TD width="2" align="left" ></TD>
								    <TD width="30" align="left" ><A href="javascript: submitform()" ><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A></TD>
									<TD>&nbsp; </TD>						
								</TR>
						</TABLE>
				</TD></TR>
			</TABLE>
				
			<TABLE width = 100% cellpadding = "3" cellspacing = "0" border = "0">
				  	<tr>
							<TD align="left" colspan="4" class="legendtitle">
								<FIELDSET>
								<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>
				
			    				<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold" style="width: 100%" readonly="readonly" rows="1" ><%=cloaiBean.getMessage()%></textarea>
								<%cloaiBean.setMessage(""); %>
								</FIELDSET>
						   </TD>
					</tr>			
				  	
				  	<tr>
						   <TD align="left" colspan="4" class="legendtitle">
								<FIELDSET>
								<LEGEND class="legendtitle">Thông tin chủng loại
								</LEGEND>
								<TABLE class="tblight" width="100%" cellspacing="0" cellpadding="6">
									<TR>
									  <TD width="15%" class="plainlabel required" ><%=ChuyenNgu.get("Chủng loại",nnId) %><FONT class="erroralert"></FONT></TD>
									  <TD  class="plainlabel" ><INPUT name="chungloai" value ='<%=cloaiBean.getChungloai()%>' size="40"
										type="text"></TD>
								  </TR>
									<TR>
									  <TD colspan="2" class="plainlabel" ><label>
									    <input name="trangthai" type="checkbox" value="1" checked>
								      <%=ChuyenNgu.get("Hoạt động",nnId) %></label></TD>
								    </TR>
								</TABLE>
								</FIELDSET>
							    <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
									<TR>
										<TD colspan = "6" width="100%" " >
											<FIELDSET>
											<LEGEND class="legendtitle">Chọn nhãn hàng
											</LEGEND>
                                            <TABLE width="100%" align="left" >
												<TR>
													<TD width="100%">
														<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
															<TR class="tbheader">
																<TH width="20%"><%=ChuyenNgu.get("Nhãn hàng",nnId) %></TH>
																<TH width="10%"><%=ChuyenNgu.get("Chọn tất cả",nnId) %>
																<input type="checkbox" name="chon" onClick="checkedAll(document.chungloaiForm.nhId)">
																</TH>
															</TR>
											<%
												int m = 0;
												String lightrow = "tblightrow";
												String darkrow = "tbdarkrow";

												while (nhlist.next()){							
													if (m % 2 != 0) {%>						
														<TR class= <%=lightrow%> >
													<%} else {%>
														<TR class= <%= darkrow%> >
													<%}%>
															<TD align="left" class="textfont"><%=nhlist.getString("ten") %></TD>
  												    <% if (nhanhangSelected.contains(nhlist.getString("pk_seq"))){ %>
									    					<TD align="center" ><input name="nhId" type="checkbox" value="<%=nhlist.getString("pk_seq")%>" checked></TD>
													<%}else{ %>
															<TD align="center"><input name="nhId" type="checkbox" value="<%=nhlist.getString("pk_seq")%>"></TD>
													<%}%>

												</TR>
													<%
														m++;
													} %>
												
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
</TD>
</TR>
 
</TABLE>
</form>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</HTML>
<% if(nhlist != null) 
	nhlist.close(); %>
<%
	cloaiBean.DBClose();
	}%>