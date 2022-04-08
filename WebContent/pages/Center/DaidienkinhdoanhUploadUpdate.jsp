<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.beans.daidienkinhdoanh.*" %>
<%@ page  import = "geso.dms.center.beans.daidienkinhdoanh.imp.*" %>
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
	}else{ int[] quyen = new  int[6];		
%>

<% 	
Daidienkinhdoanh ddkdBean = (Daidienkinhdoanh)session.getAttribute("ddkdBean"); 
%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="java.sql.SQLException"%>
<HTML>
<HEAD>
<TITLE>TNI</TITLE>

<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<script type="text/javascript"	src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js"></script>
<script type="text/javascript">
	$(document).ready(function() {		
		$( ".days" ).datepicker({			    
			changeMonth: true,
			changeYear: true				
		});            
    });	
	
</script>

<script type="text/javascript"	src="../scripts/jquery.min.1.7.js"></script>

<% if (!geso.dms.center.util.GlobalValue.dev_mode) { %>
	<script type="text/javascript" src="../scripts/disableF12.js"></script>
<% } %>

<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs.css">
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs-panes.css">
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<script>

$(document).ready(function() {

    //When page loads...
    $(".tab_content").hide(); //Hide all content
    var index = $("ul.tabs li.current").show().index(); 
    $(".tab_content").eq(index).show();

    //On Click Event
    $("ul.tabs li").click(function() {
  
        $("ul.tabs li").removeClass("current"); //Remove any "active" class
        $(this).addClass("current"); //Add "active" class to selected tab
        $(".tab_content").hide(); //Hide all tab content  
        var activeTab = $(this).find("a").attr("href"); //Find the href attribute value to identify the active tab + content  
        $(activeTab).show(); //Fade in the active ID content
        return false;
    });

});
</script>

<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
     $(document).ready(function() { 
      $("select:not(.notuseselect2)").select2({ width: 'resolve' });     
     });
    </script>
<SCRIPT language="JavaScript" type="text/javascript">
function submitform()
{
	 document.nspForm.setAttribute('enctype', "multipart/form-data", 0);	
		document.nspForm.action.value='save';
	    document.forms["nspForm"].submit();
}

function LuuFile(abc)
{	
	 console.log(abc);
	 var xyz = document.getElementById("valuedownload");
	 xyz.value = abc;
	 console.log(xyz.value);
	 document.nspForm.setAttribute('enctype', "multipart/form-data", 0);	
	 document.nspForm.action.value="download";
	 document.nspForm.submit();
}

function filterDvkd()
{
    document.nspForm.action.value='filter';
    document.nspForm.nhId.value='0';
    document.nspForm.clId.value='0';
    document.forms["nspForm"].submit();       
}

function filterNh()
{
    document.nspForm.action.value='filter';
    document.nspForm.clId.value='0';
    document.forms["nspForm"].submit();
}

function filterCl()
{
    document.nspForm.action.value='filter';
    document.forms["nspForm"].submit();       
}

function filter()
{
    document.nspForm.action.value='filter';
    document.forms["nspForm"].submit();       
}

function checkedAll() {
	var chk = document.getElementsByName("sanpham");
	for(i=0; i<chk.length; i++){
	 	if(chk[i].checked==false){
			chk[i].checked = true;
		}else{
			chk[i].checked = false;
		}
	 }
}

function checkedAllNPP() {
	var chk = document.getElementsByName("npp");
	for(i=0; i<chk.length; i++){
	 	if(chk[i].checked==false){
			chk[i].checked = true;
		}else{
			chk[i].checked = false;
		}
	 }
}

</SCRIPT>
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

<form name ="nspForm" method="post" action="../../DaidienkinhdoanhUploadSvl" >
<% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> 
<input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%=userId%>'>
<input type="hidden" name="nspId" value="">
<input type="hidden" name="action" value="0">
<input type="hidden" name="id" value="<%=ddkdBean.getId()%>">
<input type="hidden" name="valuedownload" id="valuedownload" value="abcdef">
<input type="hidden" name="view" id="valuedownload" value="<%=ddkdBean.getView()%>">

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							 <TD align="left" colspan="2" class="tbnavigation">
							 		&nbsp;Dữ liệu nền &gt; Kinh doanh &gt; Nhân viên bán hàng > Upload ảnh </TD> 
							 <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %>&nbsp;  </TD></tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
			<TR ><TD >
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR class = "tbdarkrow">
							<TD width="30" align="left"><A href="../../catalogSvl?userId=<%=userId %>" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
						    <TD width="2" align="left" ></TD>
						    <TD width="30" align="left" ><A href="javascript: submitform()" ><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A></TD>
							<TD >&nbsp; </TD>						
						</TR>
					</TABLE>
			</TD></TR>
		</TABLE>
			<TABLE width="100%" border="0" cellpadding="0"  cellspacing="1" >
			  	<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Báo lỗi nhập liệu</LEGEND>
				
	    				<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold"  
	    					style="width: 100% ; color:#F00 ; font-weight:bold" readonly="readonly" rows="1">
	    					<%=ddkdBean.getMessage() %>
	    				</textarea>
						
						</FIELDSET>
				   </TD>
				</tr>			

				<TR>
				  <TD height="100%" width="100%">
						<FIELDSET >
						<LEGEND class="legendtitle" style="color:black">Thông tin Nhân viên bán hàng </LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
							
							<TR>
						  	  	<TD class="plainlabel">Mã NVBH <FONT class="erroralert"> *</FONT> </TD>
						  	  	<TD class="plainlabel" >
						  	  	<INPUT type="text" name="Ma" style="width:200px" value='<%=ddkdBean.getSmartId()%>'>
						  	  	</TD>
						  	</TR>
						  	<TR>  	
						  	  <TD class="plainlabel">Tên NVBH <FONT class="erroralert"> *</FONT> </TD>
						  	  	<TD class="plainlabel" >
						  	  	<INPUT type="text" name="Ten" style="width:200px" value='<%=ddkdBean.getTen()%>'>
						  	  	</TD>
						  	</TR>															  	  		
						  	
						  <%ArrayList<String> imgList = ddkdBean.getImgList();
						  	  	for (int i =0; i<imgList.size();i++) {%>
						  	 <TR>
						  	  	<TD class="plainlabel">Tên File</TD>
						  	  	<TD class="plainlabel" colspan="1">
						  	   	<input type="hidden" id="valueten" name="tenfile" value="<%= imgList.get(i) %>">
						  	 	<div id="tenfilea">
						  	 		<p> <%= imgList.get(i) %>
						  	 			<img src="../images/Save20.png" onclick="LuuFile('<%=imgList.get(i)%>')" 
						  	 				style="cursor: pointer;" alt="Lưu File" width="20" height="20" longdesc="Lưu File" border = 0>
						  	 		</p>
						  	 	</div>						  	  		
						  	  	</TD>
						  	  </TR>
						  <%} %>
						  
						  <TR>
						  		<TD class="plainlabel">File</TD>
						  	  	<TD class="plainlabel" >
						  	  	<INPUT type="file" name="filename" style="width:200px" value='' >
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
</BODY>
</HTML>
<%
if (ddkdBean.getDb() != null) {
	try {
		ddkdBean.getDb().shutDown();
	}
	catch (Exception e) {
		
	}
}
%>
<%}%>