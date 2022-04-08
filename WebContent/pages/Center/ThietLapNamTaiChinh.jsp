<%@page import="geso.dms.center.beans.thietlapnamtaichinh.IThietLapNamTaiChinh"%>
<%@page import="java.util.Calendar"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.beans.thietlapnamtaichinh.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "geso.dms.center.util.*" %>
<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  %>
<%	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ 
		int[] quyen = new  int[6];
		quyen = util.Getquyen("thietlapnamtaichinhSvl", "",userId);

%>
<% IThietLapNamTaiChinh tlNamTC = (IThietLapNamTaiChinh)session.getAttribute("obj"); 
	
	ResultSet rslist = tlNamTC.getRsListKs();
	ResultSet kylist = tlNamTC.getKyList();
	ResultSet quylist = tlNamTC.getQuyList();
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
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs.css">
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs-panes.css">
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
<SCRIPT language="javascript" type="text/javascript">
	


	function saveform()
	{	   
		var tt = document.kstdForm.diengiai.value;
		var ktn = document.kstdForm.ngaytl.value;
		 if( tt.length <= 0)
			 document.kstdForm.dataerror.value =  "Không để trống ô diễn giải";
		 else
			 if (ktn.length <= 0)
				 document.kstdForm.dataerror.value =  "Không để trống ngày thiết lập";
		 else
			 {
				 document.forms['kstdForm'].action.value='save';
		     	document.forms['kstdForm'].submit();
			 }
		
	}
	function adddayinyear(){
		 document.forms['kstdForm'].action.value='themngaycn';
	     document.forms['kstdForm'].submit();
	}


	</script>
	


<!-- BEGIN RENDER AUTO -->

<% if (!geso.dms.center.util.GlobalValue.getDevmode()) { %>
	<script type="text/javascript" src="../scripts/disableF12.js"></script>
<% } %>

<!-- END RENDER AUTO -->
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<!-- BEGIN RENDER AUTO -->
<%if(!geso.dms.center.util.GlobalValue.getDevmode()){ %>
	<noscript> 
	 	<meta http-equiv="refresh" content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")  %>">
		<!-- //window.location = "https://www.google.com"; -->
	</noscript>
<%} %>
<!-- END RENDER AUTO -->

<% Csrf csdr=new Csrf(request,response,true,false,true);%>
<input type="hidden" name="action" value='1'>
<input type="hidden" id="activeTab" name="activeTab" value='<%=tlNamTC.getActiveTab()%>'>
<input name="userId" type="hidden" value='<%=userId %>' size="30">
<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:70%; padding:5px; float:left" class="tbnavigation">
        	&nbsp;Dữ liệu nền &gt; Cơ bản &gt; Thiết lập năm tài chính
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng bạn <%= userTen %> &nbsp;&nbsp;
        </div>
    </div>    
    <div align="left" style="width:100%; float:none; clear:left">
  		<fieldset>
    		<legend class="legendtitle"> Báo lỗi nhập liệu</legend>
    		<textarea name="dataerror" style="width: 100%;color:#F00" readonly="readonly"  rows="1" readonly="readonly"><%= tlNamTC.getMsg() %></textarea>
		         <% tlNamTC.setMsg(""); %>
    	</fieldset>
  	</div>
  	 <div align="left" style="width:100%; float:none; clear:left">
  		<TABLE width="100%" cellspacing="0" cellpadding="0">
								<TR>
								<TD width="98%">
									<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
										<TR class="tbheader">
											<TH width="15%">Ngày thiết lập </TH>
											<TH>Diễn giải </TH>
											
											<TH width="8%">Tác vụ</TH>
										</TR>
									
								<% 
									int m = 0;
									String lightrow = "tblightrow";
									String darkrow = "tbdarkrow";
									if(rslist != null)
									while (rslist.next()){
										if (m % 2 != 0) {%>						
											<TR class= <%=lightrow%> >
										<%} else {%>
											<TR class= <%= darkrow%> >
										<%}%>

											
											<TD align="center"><%=rslist.getString("NgayTL") %></TD>
											<TD align="center"><%=rslist.getString("DienGiai") %></TD>
<%-- 											<%if(rslist.getString("NgayTL").trim().equals("Sunday")) {%> --%>
<%-- 											<TD align="center"  style="color:red"><%=rslist.getString("DienGiai") %></TD> --%>
<%-- 											<%}else{ %> --%>
<%-- 											<TD align="center"><%=rslist.getString("NgayTL") %></TD> --%>
<%-- 											<%} %> --%>
											<TD align="center">
												
												<%if(quyen[1]!=0){ %>
														<A href = "../../thietlapnamtaichinhSvl?userId=<%=userId%>&delete=<%=rslist.getString("NgayTL")%>"><img src="../images/Delete20.png" alt="Xoa" title="Xóa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn có muốn xóa ngày này không ?')) return false;"></A>
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

  	  <div  align="left" style="width:100%; float:none; clear:left" class= "tblightrow">
  	   <fieldset>
    		<legend class="legendtitle" > </legend>
    		Diễn giải &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    			<input name="diengiai" type="text" value=<%=tlNamTC.GetDienGiai()%> >
    		 
    		 	</fieldset>		
  	     <fieldset >
    		<legend class="legendtitle"> </legend>
    		 Ngày thiết lập  &nbsp;&nbsp;&nbsp;&nbsp;
    			<input name="ngaytl" type="text" class="days"  readonly="readonly" value=<%=tlNamTC.GetNgayThietLap() %>>
    			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    			 
    			 <%if(quyen[0]!=0){ %>
    			 <a class="button"	href="javascript:saveform();"> <img style="top: -4px;" src="../images/button.png" alt=""> Thêm mới </a> 
    			 <%} %>	
    			 
    		 	</fieldset>
    		 	
    		 	
  	</div >
  	
  				<ul class="tabs">
						<li <%=tlNamTC.getActiveTab().equals("0") ? "class='current'" : "" %>><a href="#tabTieuchi">Kỳ </a></li>
						<li <%=tlNamTC.getActiveTab().equals("1") ? "class='current'" : "" %>><a href="#tabNpp">Quý </a></li>

						</ul>
				<div class="panes">
							
							<div id="tabTieuchi" class="tab_content">
								
								<TABLE class="tabledetail" width="100%" border="0" cellspacing="1px" cellpadding="4">
				                    
				                    <TR class="tbheader">
				                        <TH align="center" width="10%"  >Tên kỳ</TH>
				                        <TH align="center" width="10%"  >Tháng </TH>
				                        <th align="center" width="10%"  >Năm </TH>
				                        <TH align="center" width="20%" >Từ ngày </TH>
				                        <TH align="center" width="20%" >Đến ngày</TH>
				                    </TR>

				                    
				                    <%
				                    int m1 = 0;
									String lightrow1 = "tblightrow";
									String darkrow1 = "tbdarkrow";
  				                    	if(kylist != null) 
  				                    	while(kylist.next())
  				                    	{
  				                    		if (m1 % 2 != 0) {%>						
											<TR class= <%=lightrow1%> >
										<%} else {%>
											<TR class= <%= darkrow1%> >
										<%}%>
												
													<td align="center">
														<%= kylist.getString("tenky") %>
													</td>
													
													<td align="center">
														<%= kylist.getString("thang") %>
													</td>
													
													<td align="center">
														<%= kylist.getString("nam") %>
													</td>
													<td align="center">
														<%= kylist.getString("tungay") %>
													</td>
														<td align="center">
														<%= kylist.getString("denngay") %>
													</td>
													
												</tr>
												                 			
				                    		<% 
				                    		m1++;
  				                    	} 
				                        %> 
  				       
								</TABLE>
								
							</div>
							<div id="tabNpp" class="tab_content">
								
								<TABLE class="tabledetail" width="100%" border="0" cellspacing="1px" cellpadding="4">
				                    
				                    <TR class="tbheader">
				                       <TH align="center" width="10%"  >Tên quý</TH>
				                        <TH align="center" width="10%"  >Tháng </TH>
				                        <th align="center" width="20%"  >Năm </TH>
				                          <TH align="center" width="30%" >Từ ngày </TH>
				                        <TH align="center" width="30%" >Đến ngày</TH>
				                    </TR>

				                    
				                    <%
				                     m1 = 0;
									 lightrow1 = "tblightrow";
									 darkrow1 = "tbdarkrow";
  				                    	if(quylist != null) 
  				                    	while(quylist.next())
  				                    	{
  				                    		if (m1 % 2 != 0) {%>						
											<TR class= <%=lightrow1%> >
										<%} else {%>
											<TR class= <%= darkrow1%> >
										<%}%>
											<td align="center">
													<%= quylist.getString("ten") %>
											</td>
													<td align="center">
														<%= quylist.getString("quy") %>
													</td>
													<td align="center">
														<%= quylist.getString("nam") %>
													</td >
													<td align="center">
														<%= quylist.getString("tungay") %>
													</td>
													<td align="center">
														<%= quylist.getString("denngay") %>
													</td>
													
												</tr>
												                 			
				                    		<% 
				                    		m1++;
  				                    	} 
				                        %> 
  				       
								</TABLE>
								
							</div>
						
						</div>
	
</div>
</form>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</html>


<%
  if(rslist != null) { rslist.close(); rslist = null; }
  if(kylist != null) { kylist.close(); kylist = null; }
  if(quylist != null) { quylist.close(); quylist = null; }
  
  if(tlNamTC != null)
  {
	  tlNamTC.DBclose();
	  tlNamTC = null;
	  session.setAttribute("obj", null); 
  }
  
} %>
