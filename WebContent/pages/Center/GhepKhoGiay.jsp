<%@ page import="java.util.List"%>
<%@page import="java.util.Hashtable"%>
<%@ page import="geso.dms.center.beans.ghepkhogiay.*"%>
<%@page import="geso.dms.center.util.Utility"%>
<%@page import="java.sql.ResultSet"%>
<%@ page  import = "java.text.DateFormat" %>
<%@ page  import = "java.text.SimpleDateFormat" %>
<%@page import="java.util.Calendar"%>
<%@page import="java.sql.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	
	<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	int[] quyen = new  int[6];
	for (int i = 0; i < 6; i++)
	{
		quyen[i] = 1;
	}
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>
<%
	KeHoachList obj = (KeHoachList) session.getAttribute("obj");
	ResultSet keHoachRs = obj.getKeHoachRs();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print"
	href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">

<script type="text/javascript" src="../scripts/jquery-1.js"></script>
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />

<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
$(document).ready(function()
{
	$("#khId").select2();
	$("#nccId").select2();
	$("#dvkdId").select2();
});
</script>
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js"	type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function() {		
			$( ".days" ).datepicker({			    
					changeMonth: true,
					changeYear: true				
			});
            $(".button").hover(function(){
                $(".button img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        }); 
		$(document).ready(function(){
            $(".button1").hover(function(){
                $(".button1 img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        });
		
    </script>

<script type="text/javascript"
	src="../scripts/report-js/action-report.js"></script>
<script language="javascript" type="text/javascript">	
function clearform()
{
	document.forms['keHoachForm'].trangThai.value = '';
	document.forms['keHoachForm'].Sdays.value = '';
	document.forms['keHoachForm'].Edays.value = '';
	document.forms['keHoachForm'].toHopId.value = '';
	document.forms['keHoachForm'].kichBanId.value = '';
	document.forms['keHoachForm'].donHangId.value = '';
	document.forms['keHoachForm'].action.value= 'search';
	document.forms["keHoachForm"].submit();	
}

function search()
{
	document.forms['keHoachForm'].action.value= 'search';
	document.forms["keHoachForm"].submit();
}

function validate() {
	if (document.forms["keHoachForm"]["Sdays"].value.length == 0) {
		setErrors("Vui l??ng ch???n ng??y b???t ?????u");
		return false;
	}
	if (document.forms["keHoachForm"]["Edays"].value.length == 0) {
		setErrors("Vui l??ng ch???n ng??y k???t th??c");
		return false;
	}
	
	if (document.forms["keHoachForm"]["nhanVienGiaoNhan"].value.length == 0) {
		setErrors("Vui l??ng ch???n nh??n vi??n giao nh???n");
		return false;
	}
	
	if (document.forms["keHoachForm"]["kenh"].value.length == 0) {
		setErrors("Vui l??ng ch???n k??nh b??n h??ng");
		return false;
	}
	
	if (document.forms["keHoachForm"]["donViKinhDoanh"].value.length == 0) {
		setErrors("Vui l??ng ch???n ????n v??? kinh doanh");
		return false;
	}
	
	if (document.forms["frm"]["nhaPhanPhoi"].value.length == 0) {
		setErrors("Vui l??ng ch???n nh?? ph??n ph???i");
		return false;
	}
}

	function submitform() {
		document.forms['keHoachForm'].action.value= 'tao';
		document.forms["keHoachForm"].submit();
	}
	
	function setErrors(errorMsg) {
		var errors = document.getElementById("errors");
		errors.value = errorMsg;
	}
	
	function newform()
	{
		document.keHoachForm.action.value= 'new';
		document.forms['keHoachForm'].submit();
	}
</script>

<!-- BEGIN RENDER AUTO -->

<% if (!geso.dms.center.util.GlobalValue.dev_mode) { %>
	<script type="text/javascript" src="../scripts/disableF12.js"></script>
<% } %>

<!-- END RENDER AUTO -->
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<!-- BEGIN RENDER AUTO -->
<%if(!geso.dms.center.util.GlobalValue.dev_mode){ %>
	<noscript> 
	 	<meta http-equiv="refresh" content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")  %>">
		<!-- //window.location = "https://www.google.com"; -->
	</noscript>
<%} %>
<!-- END RENDER AUTO -->

<form name="keHoachForm" method="post" action="../../GhepKhoGiaySvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="action" id="action" value="1" >

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
    <TR>
        <TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF"><!--begin body Dossier-->
            <TABLE width="100%" border="0" cellpadding="0" cellspacing="2">
                <TR>
                    <TD align="left" class="tbnavigation">
                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                            <tr height="22">
                                <TD align="left" colspan="2" class="tbnavigation">&nbsp;Qu???n l?? b??n h??ng&nbsp; &gt; &nbsp;L???p k??? ho???ch s???n xu???t</TD>
                                <TD colspan="2" align="right" class="tbnavigation">Ch??o m???ng b???n <%= userTen %> &nbsp;</TD></tr>
                        </table></TD>
                </TR>
            </TABLE>
            
            <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
                <TR>

                    <TD >
                        <FIELDSET><LEGEND class="legendtitle">&nbsp;Ti??u ch?? t??m ki???m &nbsp;</LEGEND>
                            <TABLE class="tblight" width="100%" cellspacing="0" cellpadding="6">
                            	
<!--                             	<TR> -->
<!--                                     <TD class="plainlabel">Nh??n vi??n giao nh???n </TD> -->
<!--                                     <TD  class="plainlabel"> -->
<!--                                     <SELECT name ="nhanVienGiaoNhan" onChange = "submitform();"> -->
<!--                                     	<option value="" selected>All</option> -->
<%--                                         <% --%>
<!--                                         if (keHoachRs != null) -->
<!--                                         { -->
<!--                                         	while (keHoachRs.next()) -->
<!--                                         	{ -->
<!--                                         		if (keHoachRs.getString("PK_SEQ").equals(obj.getNhanVienGiaoNhan())){%> -->
<%--                                          <option selected value="<%=keHoachRs.getString("PK_SEQ") %>" selected><%= keHoachRs.getString("TEN") %></option> --%>
<%--                                         		<%}else{%> --%>
<%--                                          <option value="<%=keHoachRs.getString("PK_SEQ") %>"><%= keHoachRs.getString("TEN") %></option> --%>
<%--                                         		<%} --%>
<!--                                         	} -->
<!--                                         }%> -->
<!--                                         </SELECT></TD> -->
<!--                                 </TR> -->
                                
                                <TR>
                                    <TD class="plainlabel">Tr???ng th??i </TD>
                                    <TD  class="plainlabel">
                                    <SELECT  name="trangThai" onChange="search();">
		                                <option value="">All</option>
		                              	<%
			                              	String[] trangThaiIdArr = new String[] { "0", "1", "2"};
		                              		String[] trangThaiNameArr = new String[] { "Ch??a ch???t", "???? ch???t (???? t???o l???nh s???n xu???t)", "???? x??a"};
		                              		String loaiHangHoa = obj.getTrangThai();
		                              		int size = trangThaiIdArr.length;
		                              		for (int j = 0; j < size; j++) {
		                              			if (loaiHangHoa.trim().equals(trangThaiIdArr[j])) {
		                              	%>
				                		<option selected="selected" value="<%=trangThaiIdArr[j]%>"> <%=trangThaiNameArr[j]%> </option>
						                <%
							                	} else {
						                %>
							                <option value="<%=trangThaiIdArr[j]%>"> <%=trangThaiNameArr[j]%> </option>
						                <%
							                	}
					                		}
						                %>
                           			</SELECT>
                                        </TD>
                                </TR>
                                <TR>
									<TD class="plainlabel">T??? ng??y</TD>
									<TD class="plainlabel"><input type="text" name="Sdays"
										class="days" value='<%=obj.getTuNgay()%>'  onChange="search();"/></TD>
								</TR>
								<TR>
									<TD class="plainlabel">?????n ng??y</TD>
									<td class="plainlabel"><input type="text" name="Edays" class="days"
										value='<%=obj.getDenNgay()%>'  onChange="search();"/></td>
								</TR>
								<TR>
									<TD class="plainlabel">M?? t??? h???p</TD>
									<td class="plainlabel"><input type="text" name="toHopId" value='<%=obj.getToHopId()%>' onChange = "search();"/></td>
								</TR>
								<TR>
									<TD class="plainlabel">M?? k???ch b???n</TD>
									<td class="plainlabel"><input type="text" name="kichBanId" value='<%=obj.getKichBanId()%>' onChange = "search();"/></td>
								</TR>
								<TR>
									<TD class="plainlabel">M?? ????n h??ng</TD>
									<td class="plainlabel"><input type="text" name="donHangId" value='<%=obj.getDonHangId()%>' onChange = "search();"/></td>
								</TR>
                                <TR>
									<TD class="plainlabel" colspan="3">
									<a class="button2" href="javascript:clearform()">
										<img style="top: -4px;" src="../images/button.png" alt="">Nh???p l???i</a>&nbsp;&nbsp;&nbsp;&nbsp;	
                                    </TD>
							   </TR>
                            </TABLE>
                      </FIELDSET>
                    </TD>   
                </TR>
            </TABLE>
            
            <TABLE width="100%" border="0" cellpadding="0" cellspacing ="0">
                <TR>
                    <TD width="100%">

                    <FIELDSET>
                    <LEGEND class="legendtitle">&nbsp;Giao h??ng &nbsp;&nbsp;&nbsp;
<%--                     <%if(quyen[0]!=0) {%> --%>
                    	<a class="button3" href="javascript:newform()">
    						<img style="top: -4px;" src="../images/New.png" alt="">T???o m???i </a>  
<%--   					<%}%>                           --%>
                    </LEGEND>
                    <TABLE class="" width="100%" cellpadding="0" cellspacing="0">
                        <TR>
                            <TD>
                            <TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
                                <TR class="tbheader">
                          			<TH align="center" width="5%">STT </TH>
                                    <TH align="center" width="10%">M?? </TH>
                                    <TH align="center" width="15%">Tr???ng th??i</TH>
                                    <TH align="center" width="10%">Ng??y t???o</TH>
                                    <TH align="center" width="10%">Ng?????i t???o </TH>
                                    <TH align="center" width="10%">Ng??y s???a</TH>
                                    <TH align="center" width="10%">Ng?????i s???a </TH>
                                    <TH align="center" width="30%">T??c v???</TH>
                                </TR>
                            
                                <%     
                                	if (keHoachRs != null)
                                	{
	                               		String chuoitrs = "";
	                                    int m = 0;
	                                    String lightrow = "tblightrow";
	                                    String darkrow = "tbdarkrow";
	                                    while (keHoachRs.next()){
	                                    	System.out.println("sssssssssssssss");
	                                    	if (m % 2 != 0) {
	                                    %>
								<TR class=<%=lightrow%>>
	                                    <%	
	                                    	}else{
                                    	%>
                                <TR class='<%= darkrow%>'>
                                    	<%	
	                                    	}
	                                    %>
	                            <TD align="center"><%= m + 1%></TD>
								<TD align="center"><%=keHoachRs.getString("PK_SEQ")%></TD>
								<%String trangThai = ""; 
								if (keHoachRs.getString("TRANGTHAI").trim().equals("0"))
									trangThai = "Ch??a ch???t";
								else if (keHoachRs.getString("TRANGTHAI").trim().equals("1"))
									trangThai = "???? ch???t";
								else if (keHoachRs.getString("TRANGTHAI").trim().equals("2"))
									trangThai = "???? x??a";
								%>
								<TD align="center"><%=trangThai%></TD>
								<TD align="center"><%=keHoachRs.getString("NGAYTAO")%></TD>
								<TD align="center"><%=keHoachRs.getString("NGUOITAO")%></TD>
								<TD align="center"><%=keHoachRs.getString("NGAYSUA")%></TD>
								<TD align="center"><%=keHoachRs.getString("NGUOISUA")%></TD>
								<TD align="center">
                                    <TABLE border = 0 cellpadding="0" cellspacing="0">
										<TR>
										<%if(quyen[3] != 0 && !keHoachRs.getString("TRANGTHAI").trim().equals("0")) {%>
										<TD>
											<A href = "../../GhepKhoGiayNewSvl?userId=<%=userId%>&view=<%=keHoachRs.getString("pk_seq")%>"><img src="../images/Display20.png" alt="Xoa" title="View" width="20" height="20" longdesc="View" border=0></A>
										</TD>
										<%} %>
										<%if ( keHoachRs.getString("TRANGTHAI").trim().equals("0"))
										{%>
										<TD>
										<%if(quyen[2]!=0) {%>
											<A href = "../../GhepKhoGiayNewSvl?userId=<%=userId%>&update=<%=keHoachRs.getString("pk_seq")%>"><img src="../images/Edit20.png" alt="Cap nhat" title="C???p nh???t" width="20" height="20" longdesc="Cap nhat" border = 0></A>
										<%} %>
										</TD>
										<%} %>
										<TD>&nbsp;</TD>
										
										<TD>
										<%if((quyen[1] != 0) && keHoachRs.getString("TRANGTHAI").trim().equals("0")) {%>
											<A href = "../../GhepKhoGiayNewSvl?userId=<%=userId%>&delete=<%=keHoachRs.getString("pk_seq")%>"><img src="../images/Delete20.png" alt="Xoa" title="X??a" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('B???n th???c s??? mu???n x??a?')) return false;"></A>
										<%} %>
										</TD>
										<%if ( keHoachRs.getString("TRANGTHAI").trim().equals("0"))
										{%>
										<TD>&nbsp;</TD>
										<TD>
										<%
										if(quyen[4] != 0) {%>
											<A href = "../../GhepKhoGiayNewSvl?userId=<%=userId%>&chot=<%=keHoachRs.getString("pk_seq")%>">
												<img src="../images/Chot.png" alt="Ch???t" title="Ch???t" width="20" height="20" longdesc="Chot" border=0 onclick="if(!confirm('B???n c?? mu???n ch???t k??? ho???ch s???n xu???t n??y?')) return false;">
											</A>
										<%} %>
										</TD>
										 <%}%>
										</TR>												
									</TABLE>
                               	    </TD>	
	                            </TR>
	                                    <%
	                                    m++;
	                                    }
                                    }%>
                                	<!--                                     Start Ph??n trang -->
                      	<tr class="tbfooter" > 
                      	<% if (obj != null){%>
						 <TD align="center" valign="middle" colspan="13" class="tbfooter">
						 	 <% obj.setNextSplittings(); %>
												 <script type="text/javascript" src="../scripts/phanTrang.js"></script>
												<input type="hidden" name="crrApprSplitting" value="<%= obj.getCrrApprSplitting() %>" >
												<input type="hidden" name="nxtApprSplitting" value="<%= obj.getNxtApprSplitting() %>" >

											 	<%if(obj.getNxtApprSplitting() >1) {%>
													<img alt="Trang Dau" src="../images/first.gif" style="cursor: pointer;" onclick="View(document.forms[0].name, 1, 'view')"> &nbsp;
												<%}else {%>
													<img alt="Trang Dau" src="../images/first.gif" > &nbsp;
													<%} %>
												<% if(obj.getNxtApprSplitting() > 1){ %>
													<img alt="Trang Truoc" src="../images/prev.gif" style="cursor: pointer;" onclick="View(document.forms[0].name, eval(document.forms[0].nxtApprSplitting.value) -1, 'view')"> &nbsp;
												<%}else{ %>
													<img alt="Trang Truoc" src="../images/prev_d.gif" > &nbsp;
												<%} %>
												
												<%
													int[] listPage = obj.getNextSplittings();
													if (listPage != null)
													for(int i = 0; i < listPage.length; i++){
												%>
												
												<% 
												
												System.out.println("Current page:" + obj.getNxtApprSplitting());
												System.out.println("List page:" + listPage[i]);
												
												if(listPage[i] == obj.getNxtApprSplitting()){ %>
												
													<a  style="color:white;"><%= listPage[i] %>/ <%=obj.getTheLastSplitting() %></a>
												<%}else{ %>
													<a href="javascript:View(document.forms[0].name, <%= listPage[i] %>, 'view')"><%= listPage[i] %></a>
												<%} %>
													<input type="hidden" name="list" value="<%= listPage[i] %>" />  &nbsp;
												<%} %>
												
												<% if(obj.getNxtApprSplitting() < obj.getTheLastSplitting()){ %>
													&nbsp; <img alt="Trang Tiep" src="../images/next.gif" style="cursor: pointer;" onclick="View(document.forms[0].name, eval(document.forms[0].nxtApprSplitting.value) +1, 'view')"> &nbsp;
												<%}else{ %>
													&nbsp; <img alt="Trang Tiep" src="../images/next_d.gif" > &nbsp;
												<%} %>
												<%if(obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {%>
											   		<img alt="Trang Cuoi" src="../images/last.gif" > &nbsp;
										   		<%}else{ %>
										   			<img alt="Trang Cuoi" src="../images/last.gif" style="cursor: pointer;" onclick="View(document.forms[0].name, -1, 'view')"> &nbsp;
										   		<%} %>
						</TD>
						<%} %>
					 </tr>
<!--                                 	End Ph??n trang  -->
                            </TABLE>
                            </TD>
                        </TR>
                    </TABLE>
                    </FIELDSET>
                    </TD>
                </TR>
        </TABLE>
        <%
        if (obj != null)
        	obj.DBclose();
        %>
		<%} %>
    </TR>
</TABLE>
</form>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</html>