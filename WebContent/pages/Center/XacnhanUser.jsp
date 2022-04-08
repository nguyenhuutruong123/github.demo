<%@page import="geso.dms.center.beans.xacnhanuser.IXacnhanuser"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="geso.dms.center.beans.upload.IUpload"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.sql.Date"%>
<%@page import="geso.dms.center.util.Utility" %>
<%-- <%@ page  import = "geso.sohaco.center.util.ChuyenNgu" %> --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen"); 	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/demoFMCG/index.jsp");
	}else{ %>
<%
	IXacnhanuser obj = (IXacnhanuser) session.getAttribute("obj");
	//Utility util = new Utility();
	ResultSet npp=(ResultSet)obj.getRsNpp();
	ResultSet rsds=(ResultSet)obj.getRsForeCast();
	 

	int[] quyen = new  int[6];
	quyen = util.Getquyen("ImportTonKhoSvl","&task="+obj.getTask()+"",userId);
	
%>
<% String nnId = (String)session.getAttribute("nnId"); %> 
<% if(nnId == null) {
 nnId = "vi"; 
 }
/* String url = util.getChuyenNguUrl("ImportTonKhoSvl","&task=2",nnId); */	
 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<TITLE>DDT - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">


<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />

<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
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
		$(".button").hover(function() {
			$(".button img").animate({
				top : "-10px"
			}, 200).animate({
				top : "-4px"
			}, 200) // first jump
			.animate({
				top : "-7px"
			}, 100).animate({
				top : "-4px"
			}, 100) // second jump
			.animate({
				top : "-6px"
			}, 100).animate({
				top : "-4px"
			}, 100); // the last jump
		});
	});
	$(document).ready(function() {
		$(".button1").hover(function() {
			$(".button1 img").animate({
				top : "-10px"
			}, 200).animate({
				top : "-4px"
			}, 200) // first jump
			.animate({
				top : "-7px"
			}, 100).animate({
				top : "-4px"
			}, 100) // second jump
			.animate({
				top : "-6px"
			}, 100).animate({
				top : "-4px"
			}, 100); // the last jump
		});
	});
</script>
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
$(document).ready(function()
{
	$("#nppMa").select2();
	$("#vungId").select2();
	$("#khuvucId").select2();
});
</script>



<script type="text/javascript" src="../scripts/report-js/action-report.js"></script>
<script language="javascript" type="text/javascript">
function upload()
{
 	document.forms['frm'].action.value="xacnhan";
	document.getElementById("btUpload").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Processing...</a>";
 
    document.forms['frm'].submit();
}
function uploadSalesIn()
{
	document.forms['frm'].action.value="uploadSalesIn";
	document.getElementById("btUpload").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Processing...</a>";
	document.forms['frm'].setAttribute('enctype', "multipart/form-data", 0);
    document.forms['frm'].submit();
    
 	
}



function thongbao(){
	var tt = document.forms['frm'].msg.value;
	if(tt.length>0)
    alert(document.forms['frm'].msg.value);
}


function excel()
{
 	document.forms['frm'].action.value="excel";
    document.forms['frm'].submit();
}

function newform()
{   
	document.forms["frm"].action.value = "Tao moi";
	document.forms["frm"].submit();
}

function search()
{   
	document.forms["frm"].action.value = "search";
	document.forms["frm"].submit();
}

</script>

<!-- BEGIN RENDER AUTO -->

<%-- <% if (!geso.sohaco.center.util.GlobalValue.dev_mode) { %>
	<script type="text/javascript" src="../scripts/disableF12.js"></script>
<% } %> --%>

<!-- END RENDER AUTO -->
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<!-- BEGIN RENDER AUTO -->
<%-- <%if(!geso.dms.center.util.GlobalValue.dev_mode){ %>
	<noscript> 
	 	<meta http-equiv="refresh" content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")  %>">
		<!-- //window.location = "https://www.google.com"; -->
	</noscript>
<%} %> --%>
<!-- END RENDER AUTO -->

	<form name="frm" method="post" action="../../XacnhanUserSvl">  
	<%-- <% geso.sohaco.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'> --%>
		<input type="hidden" name="userId" value='<%= userId %>'>
		<input type="hidden" name="msg" value='<%=obj.getMsg()%>'>
		<script language="javascript" type="text/javascript">thongbao();</script>
		<input type="hidden" name="action" value='1'> 
		<input type="hidden" name="userId" value='<%=obj.getUserId()%>'>
		<div id="main" style="width: 99%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 70%; padding: 5px; float: left"
					class="tbnavigation">Quản trị hệ thống &#62; Xác nhận user sử dụng phần mềm</div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					Chào mừng bạn
					<%=userTen%></div>
			</div>
			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle">Báo lỗi nhập liệu </legend>
					<textarea id="errors" name="errors" rows="1" style="width: 100%">
						<%=obj.getMsg()%></textarea>
				</fieldset>
			</div>
			<div align="left" style="width: 100%; float: none; clear: left; font-size: 0.7em">
				<fieldset>
					<legend class="legendtitle">Xác nhận</legend>
					<div style="width: 100%; float: none" align="left">
						<div style="width: 100%; float: none" align="left" class="plainlabel">
							<TABLE width="70%" cellpadding="6" cellspacing="0">
							<TR>
								 
									<td colspan="1">
										<!-- <label id="btUpload">
											<a class="button" href="javascript:upload();"> <img style="top: -4px;" src="../images/button.png" alt=""> Xác nhận </a>
										</label> -->
									</td>
								 
								</TR>
							</TABLE>
						</div>
					</div>
				</fieldset>
			</div>
		</div>
          <div style="width:50%; float:none" align="left">
              	<fieldset>
	        	<legend><span class="legendtitle">Danh sách tổng user </span>&nbsp;&nbsp;&nbsp;
	    	 
	        	</legend>
	            <TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
	                <TR class="tbheader">
	                	<TH align="center">Loại đối tượng</TH>
	                    <TH align="center">Số lượng hoạt động</TH>
	                  <TH align="center">Số lượng có phát sinh doanh số</TH>
	                </TR>
	                <%
	                String tt="";
	                                    if (rsds != null) 
	                                    {									
	                                        int m = 0;
	                                        while (rsds.next()) 
	                                        {
	                                            if ((m % 2) == 0) {
	                                %>
	                <TR class='tbdarkrow'>
	                    <%
	                                        } else {
	                                    %>
	                
	                <TR class='tblightrow'>
	                    <%
	                                        }
	                                    %>
	                    <TD align="center"><%=rsds.getString("type")%></TD>
	                    <TD align="center"><%=rsds.getString("count")%></TD>
	                  <TD align="center"><%=rsds.getString("COUNT_DS")%></TD>
	                   
	                </tr>
	                <%   } }%>
	            </TABLE>
			</fieldset>
    
          </div>
        <div style="width:100%; float:none" align="left">
        	<fieldset>
	        	<legend><span class="legendtitle">Danh sách chi tiết </span>&nbsp;&nbsp;&nbsp;
	    	 
	        	</legend>
	            <TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
	                <TR class="tbheader">
	                	<TH align="center">Loại đối tượng</TH>
	                    <TH align="center">Mã đối tượng</TH>
	                    <TH align="center">Tên đối tượng</TH>
	                    <TH align="center">Hoạt động</TH>
	                    <TH align="center">Có DS 1 tháng gần nhất</TH>	
	                </TR>
	                <%
	                  tt="";
	                                    if (npp != null) 
	                                    {									
	                                        int m = 0;
	                                        while (npp.next()) 
	                                        {
	                                            if ((m % 2) == 0) {
	                                %>
	                <TR class='tbdarkrow'>
	                    <%
	                                        } else {
	                                    %>
	                
	                <TR class='tblightrow'>
	                    <%
	                                        }
	                                    %>
	                    <TD align="center"><%=npp.getString("type")%></TD>
	                    <TD align="center"><%=npp.getString("ma")%></TD>
	                     <TD align="center"><%=npp.getString("ten")%></TD>
	                       <TD align="center"><%=npp.getString("trangthai")%></TD>
	                       <TD align="center"><%=npp.getString("IS_CODS")%></TD>
	                </tr>
	                <%   } }%>
	            </TABLE>
			</fieldset>
       </div>
	</form>

</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</html>

<% 
	if(npp != null){ npp.close(); npp = null ;} 
 

	if(obj != null) obj.closeDB(); obj = null;
	session.setAttribute("obj",null);
	
}
%>