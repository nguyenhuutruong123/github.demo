<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.dms.center.beans.duyettradonhang.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>
<% IDuyettradonhangList obj = (IDuyettradonhangList)session.getAttribute("obj"); %>
<% ResultSet npp = (ResultSet)obj.getNppRs(); %>
<% ResultSet dtdhList = (ResultSet)obj.getdhtvList(); %>
<% obj.setNextSplittings(); %>
<% String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen"); String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{
		int[] quyen = new  int[5];
		quyen = util.Getquyen("28",userId);		%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">

<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">


<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js"
	type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function() {		
			$( ".days" ).datepicker({			    
					changeMonth: true,
					changeYear: true				
			});            
        }); 		
		
</script>

<script type="text/javascript" language="JavaScript" src="../scripts/simplecalendar.js"></script>
<SCRIPT language="javascript" type="text/javascript">
 function confirmLogout()
 {
    if(confirm("Ban co muon dang xuat?"))
    {
		top.location.href = "home.jsp";
    }
    return
  }
 function clearform()
 {
	 document.forms["dtdh"].tungay.value = "";
	 document.forms["dtdh"].denngay.value = "";
	 document.forms["dtdh"].nppTen.value = "";
	 document.forms["dtdh"].trangthai.value = "";
	 //document.forms["dtdh"].action1.value="guilai";
	 document.forms["dtdh"].submit();
 }
 function newform()
 {
	 document.forms["dtdh"].action.value="taomoi";
	 document.forms["dtdh"].submit();
 }
 function submitform()
 {   
    document.forms["dtdh"].submit();
 }
 function thongbao(){
		var tt = document.forms['dtdh'].msg.value;
		if(tt.length>0)
	    alert(document.forms['dtdh'].msg.value);
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

<form name="dtdh" method="post" action="../../DuyettradonhangSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="action" value="1" >
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="nppId" value="<%= obj.getNppId() %>" >
<input type="hidden" name="msg" value='<%=obj.getMsg() %>'>
<script language="javascript" type="text/javascript">
    thongbao();
</script>
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0">
				<TR>
					<TD align="left" class="tbnavigation">
					   <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							   <TD align="left" colspan="2" class="tbnavigation"> &nbsp;Qu???n l?? t???n kho > Duy???t h??ng tr??? v??? NPP > Duy???t tr??? ????n h??ng
							   <TD colspan="2" align="right" class="tbnavigation">Ch??o m???ng  <%= userTen %>&nbsp;</TD></tr>
						</TABLE>
					</TD>
		  		</TR>
				<TR>
				<TD >
				<FIELDSET><LEGEND class="legendtitle">&nbsp;Ti??u ch?? t??m ki???m</LEGEND>
							<TABLE width="100%" cellpadding="6" cellspacing="0">
								
								<TR>
									<TD width="21%" class="plainlabel">Nh?? ph??n ph???i </TD>
									<TD class="plainlabel">
									<SELECT name="nppTen" onChange = "submitform();">
										  <option value=""> </option>
										  <% try{ while(npp.next()){ 
								    			if(npp.getString("nppId").equals(obj.getNppId())){ %>
								      				<option value='<%=npp.getString("nppId")%>' selected><%= npp.getString("nppTen") %></option>
								      			<%}else{ %>
								     				<option value='<%=npp.getString("nppId")%>'><%= npp.getString("nppTen") %></option>
								     			<%}} npp.close(); }catch(java.sql.SQLException e){} %>	 
                                    </SELECT></TD>
							    </TR>
								
								<TR>
									<TD class="plainlabel">Tr???ng th??i </TD>
									<TD class="plainlabel">
										<select name="trangthai" onChange="submitform();">
												<option value="" ></option>
											    <% if (obj.getTrangthai().equals("1")){%>
											  	<option value="1" selected>??ang ch??? duy???t</option>
											  	<option value="2">???? h???y</option>
											  	<option value="3">???? duy???t</option>
											<%}else if(obj.getTrangthai().equals("3")) { %>
											  	<option value="1" >??ang ch??? duy???t</option>
											  	<option value="2">???? h???y</option>
											  	<option value="3" selected>???? duy???t</option>											
											<%} else if(obj.getTrangthai().equals("2")){ %>
												<option value="1" >??ang ch??? duy???t</option>
												<option value="2" selected>???? h???y</option>
											  	<option value="3">???? duy???t</option>
											<% }else { %>
												<option value="1" >??ang ch??? duy???t</option>
												<option value="2">???? h???y</option>
											  	<option value="3">???? duy???t</option>
											<%} %>
									          </select>
									</TD>
							    </TR>
								
								<TR>
									<TD class="plainlabel" >T??? ng??y</TD>
									<TD class="plainlabel">
										<TABLE cellpadding="0" cellspacing="0">
											<TR><TD>
											    <input class="days" type="text" name="tungay" value="<%=obj.getTungay() %>" size="11" onChange = "submitform();">
											</TD></TR>
										</TABLE>	
								</TR>
								<TR>
								  <TD class="plainlabel" >?????n ng??y</TD>
							      <TD width="100%" class="plainlabel">
								  		<TABLE cellpadding="0" cellspacing="0">
										  	<TR><TD>
											  <input class="days" type="text" name="denngay" value="<%=obj.getDenngay() %>" size="11" onChange = "submitform();">
										  	</TD></TR>
										</TABLE>
							  	</TR>
								<TR>
									<TD class="plainlabel" colspan="3">
									<a class="button2" style=" display:none; ">
										<img style="top: -4px;" src="../images/Search30.png" alt="">T??m ki???m </a>&nbsp;&nbsp;&nbsp;&nbsp;	
									<a class="button2" href="javascript:clearform()">
										<img style="top: -4px;" src="../images/button.png" alt="">Nh???p l???i</a>&nbsp;&nbsp;&nbsp;&nbsp;	
								</TR>
							</TABLE>
				  </FIELDSET>
							</TD>	
				</TR>

				<TR>
					<TD width="100%">
					<FIELDSET>
					<LEGEND class="legendtitle">&nbsp;Duy???t tr??? ????n h??ng &nbsp;&nbsp;&nbsp;
					<%if(quyen[0]!=0){ %>
					<a class="button3" onclick="newform()">
    					<img style="top: -4px;" src="../images/New.png" >T???o m???i </a> 
    					<%} %>
					</LEGEND>
					<TABLE class="" width="100%">
						<TR>
							<TD width="98%">
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="3">
								<TR class="tbheader">
									<TH width="10%" align="center">M?? ????n h??ng</TH>
									<TH width="20%" align="center">Nh?? ph??n ph???i</TH>
									<TH width="10%" align="center">M?? kh??ch h??ng</TH>
									<TH width="10%" align="center">T??n kh??ch h??ng</TH>
									<TH width="10%" align="center">Ng??y nh???p</TH>
									<TH width="10%" align="center">Tr???ng th??i</TH>
									<TH width="10%" align="center">Ng??y duy???t</TH>
									<TH width="10%" align="center">Ng?????i duy???t</TH>
									<TH width="10%" align="center">T??c v???</TH>
								</TR>
								
								<% 
									int m = 0;
									String lightrow = "tblightrow";
									String darkrow = "tbdarkrow";
									if(dtdhList != null){
									try{
									while (dtdhList.next()){
										if (m % 2 != 0) {%>						
											<TR class= <%=lightrow%> >
										<%} else {%>
											<TR class= <%= darkrow%> >
										<%}%>
												
										<TD align="center"><%= dtdhList.getString("dhtvId") %></TD> 
										<TD align="left"><%= dtdhList.getString("nppTen") %></TD>                                  
										<TD align="center"><%= dtdhList.getString("khId") %></TD>
										<TD align="left"><%= dtdhList.getString("khTen") %></TD>
										<TD align="center"><%= dtdhList.getString("ngaynhap") %></TD>
	
										<%
										String trangthai = dtdhList.getString("trangthai");
										if (trangthai.equals("1")){ %>
											<TD align="left"><i>??ang ch??? duy???t</i></TD>
										<%}else if(trangthai.equals("2")){%>
										<TD align="left"><b>????  h???y</b></TD>
										<%}else { %> 
											<TD align="left"><i>???? duy???t</i></TD>
										<%}%>
										
										<TD align="center"><%= dtdhList.getString("ngayduyet") %></TD>
										<TD align="left"><%= dtdhList.getString("nguoiduyet")%></TD>
										<TD align="center">

										<% if(dtdhList.getString("trangthai").equals("1")){ %>
												<%if(quyen[4]!=0){ %>
											<A href = "../../DuyettradonhangUpdateSvl?userId=<%=userId%>&duyet=<%= dtdhList.getString("dhtvId") %>&nppId=<%= dtdhList.getString("nppId") %>"><img src="../images/Chot.png" alt="Duyet" title="Ch???t" width="20" height="20" longdesc="Duyet" border = 0></A>
											<%}if(quyen[1]!=0){ %>
											<A href = "../../DuyettradonhangSvl?userId=<%=userId%>&huy=<%= dtdhList.getString("dhtvId") %>&nppId=<%= dtdhList.getString("nppId") %>"><img src="../images/Delete.png" alt="Huy" title="H???y" width="20" height="20" longdesc="Huy" border = 0 onclick="if(!confirm('B???n c?? mu???n h???y ????n tr??? h??ng(<%=dtdhList.getString("dhtvId") %>) n??y ?')) return false;"></A>
											<%} %>
										<%}else{ %>
											<%if(quyen[3]!=0){ %>
											<A href = "../../DuyettradonhangUpdateSvl?userId=<%=userId%>&display=<%= dtdhList.getString("dhtvId") %>&nppId=<%= dtdhList.getString("nppId") %>"><img src="../images/Display20.png" alt="Hien thi" title="Hi???n th???" width="20" height="20" longdesc="Hien thi" border = 0></A>
											<%} %>
										<%} %>
								
										</TD>
									</TR>
								<% m++;} dtdhList.close(); } catch(java.sql.SQLException e){} } %>
								
								<tr class="tbfooter" > 
						 <TD align="center" valign="middle" colspan="13" class="tbfooter">
						 	<%if(obj.getNxtApprSplitting() >1) {%>
								<img alt="Trang Dau" src="../images/first.gif" style="cursor: pointer;" onclick="View('erpDmhForm', 1, 'view')"> &nbsp;
							<%}else {%>
								<img alt="Trang Dau" src="../images/first.gif" > &nbsp;
								<%} %>
							<% if(obj.getNxtApprSplitting() > 1){ %>
								<img alt="Trang Truoc" src="../images/prev.gif" style="cursor: pointer;" onclick="Prev('erpDmhForm', 'prev')"> &nbsp;
							<%}else{ %>
								<img alt="Trang Truoc" src="../images/prev_d.gif" > &nbsp;
							<%} %>
							
							<%
								int[] listPage = obj.getNextSplittings();
								for(int i = 0; i < listPage.length; i++){
							%>
							
							<% 							
						
							if(listPage[i] == obj.getNxtApprSplitting()){ %>
							
								<a  style="color:white;"><%= listPage[i] %>/ <%=obj.getTheLastSplitting() %></a>
							<%}else{ %>
								<a href="javascript:View('erpDmhForm', <%= listPage[i] %>, 'view')"><%= listPage[i] %></a>
							<%} %>
								<input type="hidden" name="list" value="<%= listPage[i] %>" />  &nbsp;
							<%} %>
							
							<% if(obj.getNxtApprSplitting() < obj.getTheLastSplitting()){ %>
								&nbsp; <img alt="Trang Tiep" src="../images/next.gif" style="cursor: pointer;" onclick="Next('erpDmhForm', 'next')"> &nbsp;
							<%}else{ %>
								&nbsp; <img alt="Trang Tiep" src="../images/next_d.gif" > &nbsp;
							<%} %>
							<%if(obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {%>
						   		<img alt="Trang Cuoi" src="../images/last.gif" > &nbsp;
					   		<%}else{ %>
					   			<img alt="Trang Cuoi" src="../images/last.gif" style="cursor: pointer;" onclick="View('erpDmhForm', -1, 'view')"> &nbsp;
					   		<%} %>
						</TD>
					 </tr>  
							</TABLE>
							</TD>
						</TR>
					</TABLE>
					</FIELDSET>
					</TD>
				</TR>
			</TBODY>
		</TABLE>
		<!--end body Dossier--></TD>
	</TR>
</TABLE>
</form>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
<%obj.DBclose(); %>
</HTML>
<%}%>