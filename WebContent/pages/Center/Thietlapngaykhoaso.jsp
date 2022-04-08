<%@page import="java.util.Calendar"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.beans.khoasongay.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page import="geso.dms.center.util.*"%>

<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  %>
<%	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ 
		int[] quyen = new  int[6];
		quyen = util.Getquyen("CaiDatNgayKhongKsSvl", "",userId);
	

%>
<% IKhoasotudong kstdBean = (IKhoasotudong)session.getAttribute("obj"); 
	ResultSet rslist=kstdBean.getRsListKs();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js"
	type="text/javascript"></script>
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

	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
    <script type="text/javascript">
        $(document).ready(function(){
            $(".button").hover(function(){
                $(".button img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        }); 
</script>
<SCRIPT language="javascript" type="text/javascript">
	

	
	function saveform()
	{	   
		
		 document.forms['kstdForm'].action.value='save';
	     document.forms['kstdForm'].submit();
	}
	function adddayinyear(){
		 document.forms['kstdForm'].action.value='themngaycn';
	     document.forms['kstdForm'].submit();
	}

	
	
</SCRIPT>

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

<% Csrf csdr=new Csrf(request,response,true,false,true);%>
<input type="hidden" name="action" value='1'>
<input name="userId" type="hidden" value='<%=userId %>' size="30">
<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:70%; padding:5px; float:left" class="tbnavigation">
        	&nbsp;Dữ liệu nền &gt; Cơ bản &gt; Thiết lập ngày không khóa sổ
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng bạn <%= userTen %> &nbsp;&nbsp;
        </div>
    </div>    
    <div align="left" style="width:100%; float:none; clear:left">
  		<fieldset>
    		<legend class="legendtitle"> Báo lỗi nhập liệu</legend>
    		<textarea name="dataerror" style="width: 100%" readonly="readonly" rows="1" readonly="readonly"><%= kstdBean.getMsg() %></textarea>
		         <% kstdBean.setMsg(""); %>
    	</fieldset>
  	</div>
  	 <div align="left" style="width:100%; float:none; clear:left">
  		<TABLE width="100%" cellspacing="0" cellpadding="0">
								<TR>
								<TD width="98%">
									<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
										<TR class="tbheader">
											<TH width="15%">Ngày không khóa sổ tự động </TH>
											<TH>Ngày trong tuần </TH>
											
											<TH width="8%">Tác vụ</TH>
										</TR>
									
								<% 
									int m = 0;
									String lightrow = "tblightrow";
									String darkrow = "tbdarkrow";
									while (rslist.next()){
										if (m % 2 != 0) {%>						
											<TR class= <%=lightrow%> >
										<%} else {%>
											<TR class= <%= darkrow%> >
										<%}%>

											
											<TD align="center"><%=rslist.getString("ngaykhongks") %></TD>
											<%if(rslist.getString("thu").trim().equals("Sunday")) {%>
											<TD align="center"  style="color:red"><%=rslist.getString("thu") %></TD>
											<%}else{ %>
											<TD align="center"><%=rslist.getString("thu") %></TD>
											<%} %>
											<TD align="center">
												
										<%if(quyen[1]!=0) {%>
												<A href = "../../CaiDatNgayKhongKsSvl?userId=<%=userId%>&delete=<%=rslist.getString("pk_seq")%>"><img src="../images/Delete20.png" alt="Xoa" title="Xóa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn có muốn xóa ngày này không ?')) return false;"></A>
										<%} %>				
																						
											</TD>
										</TR>
									<% 	m++;
									}%>
										<TR>
											<TD align="center" colspan="11" class="tbfooter">&nbsp;	</TD>
										</TR>
									</TABLE>
				</TABLE>
  	</div>
  	  <div  align="left" style="width:100%; float:none; clear:left" class= "tblightrow">		
  	     <fieldset>
    		<legend class="legendtitle"> </legend>
    		Ngày không khóa sổ &nbsp;&nbsp;&nbsp;&nbsp;
    			<input name="ngayks" type="text" class="days"  readonly="readonly" value=<%=kstdBean.GetNgayKhoaSo() %>>
    			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    			 <a class="button"	href="javascript:saveform();"> <img style="top: -4px;"
											src="../images/button.png" alt=""> Thêm mới </a> 	
    		 	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    								 <select name="nam"  style="width :50px" ">
									<option value=0> </option>  
									<%
									  Calendar c2 = Calendar.getInstance();
  										int t=c2.get(Calendar.YEAR) +6;
  										int n;
  										for(n=c2.get(Calendar.YEAR);n<t;n++){
  										
  									%>
									
										<option value=<%=n%> ><%=n%></option> 
									<%
 									 }
 									%>
									</select>
									
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										 <a class="button"	href="javascript:adddayinyear();"> <img style="top: -4px;"
											src="../images/button.png" alt=""> Thêm ngày chủ nhật trong năm </a>
    		 
    		 	</fieldset>
    		 	
    		 	
  	</div >
  	
  	
</div>
</form>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</html>

<%
   if(rslist != null){ rslist.close(); rslist = null; }
   if(kstdBean != null)
   {
	   kstdBean.DBclose(); kstdBean = null; 
	   session.setAttribute("obj", null);
   }

} %>
