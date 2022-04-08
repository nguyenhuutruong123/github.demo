<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.beans.mucchietkhau.*" %>
<%@ page  import = "geso.dms.center.beans.mucchietkhau.imp.*" %>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.NumberFormat"%>
<%@ page  import = "geso.dms.center.util.*" %>
<%@ page  import = "geso.dms.center.util.ChuyenNgu" %>
<%	
	NumberFormat formatter = new DecimalFormat("#,###,###"); 
	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{int[] quyen = new  int[6];
	quyen = util.Getquyen("ChietkhauSvl","",userId);
	
	System.out.println(quyen[0]);
	System.out.println(quyen[1]);
	System.out.println(quyen[2]);
	System.out.println(quyen[3]);	
	System.out.println(quyen[4]); %>

<% IChietkhauList obj = (IChietkhauList)session.getAttribute("obj"); %>
<% List<IChietkhau> ckList = (List<IChietkhau>)obj.getChietkhauList(); %>

<% String nnId = (String)session.getAttribute("nnId"); %> 
<% if(nnId == null) {
 nnId = "vi"; 
 }
String url = util.getChuyenNguUrl("ChietkhauSvl","",nnId);	
 %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<TITLE>Acecook - Project</TITLE>
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<SCRIPT language="javascript" type="text/javascript">

 function clearform()
 {
     document.ckForm.sotien.value = "";
     document.ckForm.chietkhau.value = "";
     submitform();
 }

 function submitform()
 {
 	document.forms['ckForm'].action.value= 'search';
 	document.forms['ckForm'].submit();
 }

 function newform()
 {
 	document.forms['ckForm'].action.value= 'new';
 	document.forms['ckForm'].submit();
 }
 function thongbao()
 {var tt = document.forms['ckForm'].msg.value;
 	if(tt.length>0)
     alert(document.forms['ckForm'].msg.value);
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

<form name="ckForm" method="post" action="../../ChietkhauSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%=userId%>'>
<input type="hidden" name="action" value='1'>

<input type="hidden" name="msg" value='<%=obj.getMsg() %>'>

<script language="javascript" type="text/javascript">
    thongbao();
</script> 
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top'>
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" >
					   <table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							   <TD align="left" colspan="2" class="tbnavigation"><%=" "+url %></TD>
							   <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%= userTen %>&nbsp;  </TD> 
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
									<LEGEND class="legendtitle">Tiêu chí tìm kiếm  </LEGEND>

									<TABLE class="tblight" width="100%" cellpadding="6" cellspacing = "0">
										<TR>
										  <TD class="plainlabel" ><%=ChuyenNgu.get("Số tiền",nnId) %> </TD>
										  <TD class="plainlabel" ><INPUT name="sotien" size="20" type="text" value="<%= obj.getSotien() %>" onChange="submitform();"></TD>
									  </TR>
										<TR>
											<TD width="20%" class="plainlabel" ><%=ChuyenNgu.get("Mức chiết khấu",nnId) %> </TD>
										    <TD width="80%" class="plainlabel" ><INPUT name="chietkhau" size="20" type="text" value="<%= obj.getChietkhau() %>"  onChange="submitform();"> %</TD>
										</TR>
										<TR>
                                             <TD class="plainlabel" colspan=2> 
                                             <a class="button2" href="javascript:clearform()">
													<img style="top: -4px;" src="../images/button.png" alt=""><%=ChuyenNgu.get("Nhập lại",nnId) %></a>&nbsp;&nbsp;&nbsp;&nbsp;	
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
							<LEGEND class="legendtitle">&nbsp;Chiết khấu &nbsp;&nbsp;
							<%if(quyen[0]!=0){ %>
							<a class="button3" href="javascript:newform()">
    							<img style="top: -4px;" src="../images/New.png" alt=""><%=ChuyenNgu.get("Tạo mới",nnId) %> </a>                            
							<%} %>
						</LEGEND>
				
							<TABLE class="" width="100%" border="0" cellpadding="0" cellspacing="0">
								<TR>
									<TD width="98%">
										<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
											<TR class="tbheader">
												<TH width="8%"><%=ChuyenNgu.get("Chiết khấu",nnId) %></TH>
												<TH width="12%"><%=ChuyenNgu.get("Diễn giải",nnId) %></TH>
											    <TH width="10%"><%=ChuyenNgu.get("Số tiền",nnId) %></TH>
											    <TH width="10%"><%=ChuyenNgu.get("Mức chiết khấu",nnId) %></TH>
											    <TH width="9%"><%=ChuyenNgu.get("Ngày tạo",nnId) %></TH>
											  <TH width="13%"><%=ChuyenNgu.get("Người tạo",nnId) %> </TH>										
											  <TH width="9%"><%=ChuyenNgu.get("Ngày sửa",nnId) %></TH>
											  <TH width="14%"><%=ChuyenNgu.get("Người sửa",nnId) %></TH>
											  <TH width="10%"><%=ChuyenNgu.get("Tác vụ",nnId) %></TH>
										  </TR>
								<% 
									IChietkhau ck = null;
									int size = ckList.size();
									int m = 0;
									String lightrow = "tblightrow";
									String darkrow = "tbdarkrow";
									while (m < size){
										ck = ckList.get(m);
										if (m % 2 != 0) {%>						
											<TR class= <%=lightrow%> >
										<%} else {%>
											<TR class= <%= darkrow%> >
										<%}%>
												<%
											  String gia="";
											  if(ck.getSotien().trim().length()!=0)
											  {
												 gia= formatter.format(Double.parseDouble(ck.getSotien()));
											  }
											  else
											  {
												  gia=ck.getSotien();
											  }
											  %>
												<TD align="center"><%= ck.getId() %></TD> 
												<TD align="left"><%= ck.getDiengiai() %></TD>                                   
												<TD align="right"><%= gia %> VNĐ</TD>
											 	<TD align="right"><%= ck.getChietkhau() %> %</TD>
												<TD align="center"><%=ck.getNgaytao()%></TD>
												<TD align="center"><%=ck.getNguoitao()%></TD>
												<TD align="center"><%=ck.getNgaysua()%></TD>
												<TD align="center"><%=ck.getNguoisua()%></TD>
												<TD align="center">
												<%if(quyen[2]!=0){ %>
												  <A href = "../../ChietkhauUpdateSvl?userId=<%=userId%>&update=<%= ck.getId()%>">
	                                             				<img src="../images/Edit20.png" alt="Cập nhật" title="Cập nhật" width="20" height="20" longdesc="Cập nhật" border = 0></A>
													 &nbsp;<%} %>
													 <%if(quyen[1]!=0){ %>
												  <A href = "../../ChietkhauSvl?userId=<%=userId%>&delete=<%=ck.getId()%>">
	                                              			<img src="../images/Delete20.png" alt="Xóa" title="Xóa" width="20" height="20" longdesc="Xóa" border=0 onclick="if(!confirm('Bạn có muốn xóa mức chiết khấu này ?')) return false;"></A></TD>
													<%} %>
												</TD>
											</TR>
										<% m++; }%>
								
									<TR>	
									<TD height="" colspan="11" align="center" class="tbfooter">
									&nbsp;</TD>
								</TR>
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
</form>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</HTML>

<% 
   if(ckList!= null){ ckList.clear(); ckList = null; }
	obj.DBClose(); obj = null;
	session.setAttribute("obj","");

	} %>