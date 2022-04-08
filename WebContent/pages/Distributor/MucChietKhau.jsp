<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.dms.distributor.beans.mucchietkhau.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>

<% IMucchietkhauList obj = (IMucchietkhauList)session.getAttribute("obj"); %>
<% List<IMucchietkhau> mcklist = (List<IMucchietkhau>)obj.getMckList(); 

	int[] quyen = new  int[6];
	quyen = util.Getquyen("MucchietkhauSvl","", userId);
%>

<% String nnId = (String)session.getAttribute("nnId"); %> 
<% if(nnId == null) {
 nnId = "vi"; 
 
 } 
String url = util.getChuyenNguUrl("MucchietkhauSvl","",nnId); 
 %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
<SCRIPT language="javascript" type="text/javascript">
function clearform()
{
	document.mckForm.chietkhau.value = "";
	document.mckForm.khTen.value = "";  
	submitform();    
}

function submitform()
{
	document.forms['mckForm'].action.value= 'search';
	document.forms['mckForm'].submit();
}

function newform()
{
	document.forms['mckForm'].action.value= 'new';
	document.forms['mckForm'].submit();
}
function thongbao()
{var tt = document.forms['mckForm'].msg.value;
	if(tt.length>0)
    alert(document.forms['mckForm'].msg.value);
	}

</SCRIPT>
<script type="text/javascript" src="../scripts/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>


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

<form name="mckForm" method="post" action="../../MucchietkhauSvl"> 
 <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="nppId" value="<%=obj.getNppId()%>" >
<input type="hidden" name="action" value="1" >

<input type="hidden" name="msg" value='<%=obj.getMsg() %>'>

<script language="javascript" type="text/javascript">
    thongbao();
</script> 
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="2">
			<TR>
				<TD align="left" class="tbnavigation">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr height="22">
   							<TD align="left" colspan="2" class="tbnavigation">&nbsp;<%= " " + url %></TD>
   							<TD colspan="2" align="right" class="tbnavigation"><%=ChuyenNgu.get("Chào mừng",nnId)%><%= obj.getNppTen() %>  &nbsp;</TD>
						</tr>
					</table>
				</TD>
		  	</TR>
		</TABLE>
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="2">
			<TR>
				<TD >
					<FIELDSET><LEGEND class="legendtitle">&nbsp;<%=ChuyenNgu.get("Tiêu chí tìm kiếm",nnId)%> &nbsp;</LEGEND>
					<TABLE  width="100%" cellspacing="0" cellpadding="3">
						<TR>
							<TD width="19%" class="plainlabel"><%=ChuyenNgu.get("Diễn giải",nnId)%> </TD>
							<TD width="81%" valign="middle" class="plainlabel"><INPUT name="chietkhau" size="10" type="text" value="<%= obj.getMucchietkhau() %>"  onChange = "submitform();">&nbsp; %</TD>
						</TR>
						<TR>
							<TD class="plainlabel"><%=ChuyenNgu.get("Khách hàng",nnId)%> </TD>
							<TD class="plainlabel" valign="middle">
								<TABLE cellpadding="0" cellspacing="0">
									<TR><TD>
										<input name="khTen" type="text" size="35" value="<%= obj.getKhachhang() %>" onChange = "submitform();">
									</TD>
									<TD>&nbsp;</TD>
									</TR>
								</TABLE>
							</TD>
						</TR>
						<TR>
						    <TD class="plainlabel" colspan="2">
						    
						    	<a class="button2" href="javascript:clearform()">
	<img style="top: -4px;" src="../images/button.png" alt=""><%=ChuyenNgu.get("Nhập lại",nnId)%></a>&nbsp;&nbsp;&nbsp;&nbsp;	
		
                            </TD>
						</TR>
					</TABLE>

				  </FIELDSET>
				</TD>	
			</TR>
		</TABLE>
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
			<TR>
					<TD width="100%">
					<FIELDSET>
					<LEGEND class="legendtitle"><%=ChuyenNgu.get("Mức chiết khấu",nnId)%> &nbsp;&nbsp; 
					<%if(quyen[0]!= 0){ %>
						<a class="button3" href="javascript:newform()">
	    	<img style="top: -4px;" src="../images/New.png" alt=""><%=ChuyenNgu.get("Tạo mới",nnId)%> </a>
	    			<%} %>                            
 
					</LEGEND>
					<TABLE class="" width="100%" cellpadding="0" cellspacing="0">
						<TR>
							<TD>
								<TABLE width="100%" border="0" cellspacing="1" cellpadding="2">
									<TR class="tbheader">
										<TH width="35%"><%=ChuyenNgu.get("Mức chiết khấu",nnId)%> </TH>
										<TH width="10%"><%=ChuyenNgu.get("% Chiết khấu",nnId)%></TH>
										<TH width="10%"><%=ChuyenNgu.get("Ngày tạo",nnId)%></TH>
										<TH width="12%"><%=ChuyenNgu.get("Người tạo",nnId)%> </TH>
										<TH width="10%"><%=ChuyenNgu.get("Ngày sửa",nnId)%> </TH>
										<TH width="12%"><%=ChuyenNgu.get("Người sửa",nnId)%> </TH>
										<TH width="10%"><%=ChuyenNgu.get("Tác vụ",nnId)%></TH>
									</TR>

									<% 
									IMucchietkhau mck = null;
									int size = mcklist.size();
									int m = 0;
									String lightrow = "tblightrow";
									String darkrow = "tbdarkrow";
									while (m < size){
										mck = mcklist.get(m);
										if (m % 2 != 0) {%>						
											<TR class= <%=lightrow%> >
										<%} else {%>
											<TR class= <%= darkrow%> >
										<%}%>
												<TD align="left"><%= mck.getDiengiai() %></TD>                                   
												<TD align="center"><%= mck.getChietkhau()%></TD>                                 
												<TD align="center"><%= mck.getNgaytao()%></TD>
												<TD align="center"><%= mck.getNguoitao()%></TD>
												<TD align="center"><%= mck.getNgaysua()%></TD>
												<TD align="center"><%= mck.getNguoisua()%></TD>
												<TD align="center">
												<TABLE border = 0 cellpadding="0" cellspacing="0">
												<TR><TD>
												<%if(quyen[2]!= 0){ %>
													<A href = "../../MucchietkhauUpdateSvl?userId=<%=userId%>&update=<%= mck.getId()%>"><img src="../images/Edit20.png" alt="Cap nhat" title="Cập nhật" width="20" height="20" longdesc="Cap nhat" border = 0></A>
												<%} %>
												</TD>
												<TD>&nbsp;</TD>
												<TD>
												<%if(quyen[1]!= 0){ %>
													<A href = "../../MucchietkhauSvl?userId=<%=userId%>&delete=<%=mck.getId()%>"><img src="../images/Delete20.png" alt="Xoa" title="Xóa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn chắc chắn muốn xóa mức chiết khấu này ?')) return false;"></A></TD>
												<%} %>
												</TR></TABLE>
												</TD>
											</TR>
								<%m++; }%>
								
							       <tr class="tbfooter" > <td colspan="8" >&nbsp;</td> </tr>  
						</TABLE>
					</TD>
				</TR>
			</TABLE>
			</FIELDSET>
		</TD>
	</TR>
</TABLE>
		<!--end body Dossier--></TD>
</TR>
</TABLE>
</form>
</BODY>
<script>
	jQuery(function()
	{		
		$("#khTen").autocomplete("KhachHangSearch_MCK_GHCN.jsp");		
	});	
</script>
</html>

<%
	try{
		if(mcklist!=null){ mcklist.clear(); mcklist =null; }
		
		if(obj != null){
			obj.DBclose();
			obj = null;
		}	
		session.setAttribute("obj",null);
		
	}catch(Exception er){}
	}
%>