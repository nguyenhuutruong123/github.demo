<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.beans.khaibaockdathang.*" %>
<%@ page  import = "geso.dms.center.beans.khaibaockdathang.imp.*" %>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.NumberFormat"%>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%	
	NumberFormat formatter = new DecimalFormat("#,###,###"); 
	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{int[] quyen = new  int[6];
	quyen = util.Getquyen("KhaibaoCKdathangSvl","",userId);
	
	System.out.println(quyen[0]);
	System.out.println(quyen[1]);
	System.out.println(quyen[2]);
	System.out.println(quyen[3]);	
	System.out.println(quyen[4]); %>

<% IKhaibaoCKdathanglist obj = (IKhaibaoCKdathanglist)session.getAttribute("obj"); %>
<% List<IKhaibaoCKdathang> ckdhList = (List<IKhaibaoCKdathang>)obj.getKhaibaoCKdathangList(); %>
<% ResultSet nppRs = obj.getNPPList(); %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<TITLE>Acecook - Project</TITLE>
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<script type="text/javascript"	src="../scripts/jquery.min.1.7.js"></script>
<script type="text/javascript" language="JavaScript" src="../scripts/jquery.tools.min.js"></script>
	
<script type="text/javascript" src="../scripts/ajax.js"></script>
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs.css">
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs-panes.css">
	
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
<script type="text/javascript">
		$(document).ready(function() {
			$(".days").datepicker({
				changeMonth : true,
				changeYear : true
			});
			
		});
	</script> 
<SCRIPT language="javascript" type="text/javascript">

 function clearform()
 {
     document.ckdhForm.scheme.value = "";
     document.ckdhForm.loaick.value = "";
     document.ckdhForm.tungay.value = "";
     document.ckdhForm.denngay.value = "";
     document.ckdhForm.nppId.value = "";
     document.ckdhForm.trangthai.value = "";
     submitform();
 }

 function submitform()
 {
 	document.forms['ckdhForm'].action.value= 'search';
 	document.forms['ckdhForm'].submit();
 }

 function newform()
 {
 	document.forms['ckdhForm'].action.value= 'new';
 	document.forms['ckdhForm'].submit();
 }
 function thongbao()
 {var tt = document.forms['ckdhForm'].msg.value;
 	if(tt.length>0)
     alert(document.forms['ckdhForm'].msg.value);
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

<form name="ckdhForm" method="post" action="../../KhaibaoCKdathangSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
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
							   <TD align="left" colspan="2" class="tbnavigation">
							   		&nbsp;D??? li???u n???n > Kinh doanh > Khai b??o chi???t kh???u ?????t h??ng</TD>
							   <TD colspan="2" align="right" class="tbnavigation">Ch??o m???ng b???n <%= userTen %>&nbsp;  </TD> 
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
									<LEGEND class="legendtitle">Ti??u ch?? t??m ki???m  </LEGEND>

									<TABLE class="tblight" width="100%" cellpadding="6" cellspacing = "0">
										<TR>
										  <TD width="15%" class="plainlabel" >Scheme </TD>
										  <TD width="35%"class="plainlabel" ><INPUT name="scheme" size="20" type="text" value="<%= obj.getScheme() %>" onChange="submitform();"></TD>
										  <TD width="15%" class="plainlabel" >Lo???i chi???t kh???u </TD>
											<TD class="plainlabel"><select name="loaick" onChange="submitform();">
												  <option value=""> </option>
												  <% if (obj.getLoaiCK().equals("1")){ %>										  
												  		<option value="1" selected>Theo ????n h??ng</option>
												  		<option value="0">Theo s???n ph???m</option>
												  <%}else{
												  		if (obj.getLoaiCK().equals("0")){ %>										  
												  			<option value="1" >Theo ????n h??ng</option>
												  			<option value="0" selected>Theo s???n ph???m</option>
												  		<%}else{%>	
												  			<option value="1" >Theo ????n h??ng</option>
												  			<option value="0" >Theo s???n ph???m</option>
												  		<%}%>
												   <%}%>
												  </select>
											</TD>
									  </TR>
										<TR>
											<TD width="15%" class="plainlabel" >T??? ng??y</TD>
											<TD  class="plainlabel" >
												<input type="text" name="tungay" id="tungay" class="days" value="<%= obj.getTungay() %>" readonly="readonly" onChange="submitform();">
											</TD>
									  	  	<TD width="15%" class="plainlabel" >?????n ng??y</TD>
											<TD class="plainlabel" >
												<input type="text" name="denngay" id="denngay" class="days" value="<%= obj.getDenngay() %>" readonly="readonly" onChange="submitform();">
											</TD>
						  				</TR>
										<tr>
											<TD width="15%" class="plainlabel">Nh?? ph??n ph???i ??p d???ng </TD>
									  	  	<TD width="35%" class="plainlabel">
									  	  		<select name="nppId" onchange="submitform();">
									  	  			<option value=""> </option>
									  	  			<%if(nppRs != null)  
   													{  
   														while(nppRs.next())  
   														{  
   															if(nppRs.getString("pk_seq").equals(obj.getNppId()))  
   															{  
   																System.out.println("NPP ID la: " + obj.getNppId() + "\n");%>  
																<option value="<%= nppRs.getString("pk_seq") %>" selected="selected"  ><%= nppRs.getString("ten") %></option>
															<% } else { %>
																<option value="<%= nppRs.getString("pk_seq") %>"  ><%= nppRs.getString("ten") %></option>
															<%}
   														}  
   														nppRs.close();  
   													}%>				 
									  	  		</select>
									  	  	</TD>
									  	  	<TD width="15%" class="plainlabel" >Tr???ng th??i</TD>
											<TD class="plainlabel" >
												<select name="trangthai" onChange="submitform();">
												  <option value=""> </option>
												  <% if (obj.getTrangthai().equals("1")){ %>										  
												  		<option value="1" selected>???? ch???t</option>
												  		<option value="0">Ch??a ch???t</option>
												  <%}else{
												  		if (obj.getTrangthai().equals("0")){ %>										  
												  			<option value="1" >???? ch???t</option>
												  			<option value="0" selected>Ch??a ch???t</option>
												  		<%}else{%>	
												  			<option value="1" >???? ch???t</option>
												  			<option value="0" >Ch??a ch???t</option>
												  		<%}%>
												   <%}%>
												  </select>
											</TD>
										</tr>
										<TR>
                                             <TD class="plainlabel" colspan=4> 
                                             <a class="button2" href="javascript:clearform()">
													<img style="top: -4px;" src="../images/button.png" alt="">Nh???p l???i</a>&nbsp;&nbsp;&nbsp;&nbsp;	
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
							<LEGEND class="legendtitle">&nbsp;Chi???t kh???u &nbsp;&nbsp;
							<%if(quyen[0]!=0){ %>
							<a class="button3" href="javascript:newform()">
    							<img style="top: -4px;" src="../images/New.png" alt="">T???o m???i </a>                            
							<%} %>
						</LEGEND>
				
							<TABLE class="" width="100%" border="0" cellpadding="0" cellspacing="0">
								<TR>
									<TD width="98%">
										<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
											<TR class="tbheader">
												<TH width="8%">Scheme</TH>
												<TH width="12%">Di???n gi???i</TH>
												<TH width="8%">Lo???i chi???t kh???u</TH>
												<TH width="8%">Tr???ng th??i</TH>
											    <TH width="9%">Ng??y t???o</TH>
											  <TH width="10%">Ng?????i t???o </TH>										
											  <TH width="9%">Ng??y s???a</TH>
											  <TH width="10%">Ng?????i s???a</TH>
											  <TH width="9%">T??? ng??y</TH>
											  <TH width="9%">?????n ng??y</TH>
											  <TH width="10%" align="center">&nbsp;T??c v???</TH>
										  </TR>
								<% 
									IKhaibaoCKdathang ck = null;
									int size = ckdhList.size();
									int m = 0;
									String lightrow = "tblightrow";
									String darkrow = "tbdarkrow";
									while (m < size){
										ck = ckdhList.get(m);
										if (m % 2 != 0) {%>						
											<TR class= <%=lightrow%> >
										<%} else {%>
											<TR class= <%= darkrow%> >
										<%}%>
												
												<TD align="center"><%= ck.getScheme() %></TD> 
												<TD align="left"><%= ck.getDiengiai() %></TD> 
												<% if (ck.getLoaiCK().equals("1")){ %>
													<TD align="center">????n h??ng </TD>
									 			<%}else{ %>
													<TD align="center">S???n ph???m </TD>
									 			<%} %>
									 			<% if (ck.getTrangthai().equals("0")){ %>
													<TD align="center">Ch??a ch???t </TD>
									 			<%}else{ %>
													<TD align="center">???? ch???t </TD>
									 			<%} %>
												<TD align="center"><%=ck.getNgaytao()%></TD>
												<TD align="center"><%=ck.getNguoitao()%></TD>
												<TD align="center"><%=ck.getNgaysua()%></TD>
												<TD align="center"><%=ck.getNguoisua()%></TD>
												<TD align="center"><%=ck.getTungay()%></TD>
												<TD align="center"><%=ck.getDenngay()%></TD>
												<TD align="center">
												<%if (ck.getTrangthai().equals("0")){ %>
												<%if(quyen[2]!=0){ %>
												  <A href = "../../KhaibaoCKdathangUpdateSvl?userId=<%=userId%>&update=<%= ck.getId()%>">
	                                             				<img src="../images/Edit20.png" alt="C???p nh???t" title="C???p nh???t" width="20" height="20" longdesc="C???p nh???t" border = 0></A>
													 &nbsp;<%} %>
													 <%if(quyen[4]!=0){ %>
												  <A href = "../../KhaibaoCKdathangSvl?userId=<%=userId%>&chot=<%= ck.getId()%>">
	                                             				<img src="../images/Chot.png" alt="Ch???t" title="Ch???t" width="20" height="20" longdesc="Ch???t" border = 0></A>
													 &nbsp;<%} %>
													 <%if(quyen[1]!=0){ %>
												  <A href = "../../KhaibaoCKdathangSvl?userId=<%=userId%>&delete=<%=ck.getId()%>">
	                                              			<img src="../images/Delete20.png" alt="X??a" title="X??a" width="20" height="20" longdesc="X??a" border=0 onclick="if(!confirm('B???n c?? mu???n x??a m???c chi???t kh???u n??y ?')) return false;"></A>
													<%}} else{ %>
													<%if(quyen[3]!=0){ %>
												  <A href = "../../KhaibaoCKdathangUpdateSvl?userId=<%=userId%>&display=<%=ck.getId()%>">
	                                              			<img src="../images/Display20.png" alt="Xem" title="Xem" width="20" height="20" longdesc="Xem" border=0 ></A>
													<%}}%>
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
	if(ckdhList != null) { ckdhList.clear(); ckdhList = null ; }
	if(nppRs != null) { nppRs.close(); nppRs = null ; };
	
	if(obj!= null) {
		obj.DBClose(); obj = null ;
		session.setAttribute("obj",null); 
	}

	
	} %>