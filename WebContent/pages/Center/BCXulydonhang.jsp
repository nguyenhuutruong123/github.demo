<%@page import="java.sql.ResultSet"%>
<%@ page  import = "geso.dms.center.beans.donmuahang.IDonmuahangList" %>
<%@page import="java.util.Calendar"%>
<%@page import="java.sql.Date"%>
<%@ page  import = "geso.dms.center.util.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	System.out.println(userId);
	System.out.println(userTen);
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ 
	
%>

<% IDonmuahangList obj = (IDonmuahangList)session.getAttribute("obj"); %>
<% ResultSet vung = (ResultSet)obj.getVungList(); %>
<% ResultSet khuvuc = (ResultSet)obj.getKvList(); %>
<% ResultSet dhlist = (ResultSet)obj.getDhList(); %>
<% obj.setNextSplittings(); 

	int[] quyen = new  int[6];
	quyen = util.Getquyen("DonmuahangSvl","&view="+obj.getView()+"",userId);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<TITLE>SalesUp - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">

<script type="text/javascript" src="../scripts/jquery-1.js"></script>

<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />

<script type="text/javascript"	src="../scripts/jquery.min.1.7.js"></script>
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
<script type="text/javascript">
	function submitform() 
	{
		if (document.getElementById("Sdays").value == "") 
		{
			alert("Vui lòng chọn ngày bắt đầu");
			return ;
		}
		if (document.getElementById("Edays").value == "")
		{
			alert("Vui lòng chọn ngày kết thúc");
			return ;
		}		
	
		document.forms['frm'].action.value= 'BCxldh';
		document.forms["frm"].submit();
	}
	
	function changeform()
	{
		document.forms['frm']['action'].value="changeBCXLDH";
		document.forms['frm'].submit();
	}
	
</script>

<link href="../css/select2.css" rel="stylesheet" />
	<script src="../scripts/select2.js"></script>
	<script>
    	$(document).ready(function() { 
    		$("select:not(.notuseselect2)").select2(); 
    	});
    </script>	
 	

<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<!-- BEGIN RENDER AUTO -->
<%if(!geso.dms.center.util.GlobalValue.getDevmode()){ %>
	<noscript> 
	 	<meta http-equiv="refresh" content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")  %>">
		<!-- //window.location = "https://www.google.com"; -->
	</noscript>
<%} %>
<!-- END RENDER AUTO -->

	<form name="frm" method="post" action="../../DonmuahangSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
	<input type="hidden" name="action" value='1'>	
	<input type="hidden" name="userId" value='<%=userId%>'>
		<div id="main" style="width: 99%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 70%; padding: 5px; float: left"
					class="tbnavigation">Báo cáo quản trị &#62; Báo cáo khác &#62; Báo cáo xử lý đơn hàng</div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					Chào mừng bạn <%=userTen %></div>
			</div>

			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>

					<legend class="legendtitle"> Báo lỗi nhập liệu</legend>
					<textarea id="errors"  name="errors" rows="1" style="width: 100% ; color:#F00 ; font-weight:bold"><%= obj.getMsg() %></textarea>
					
				</fieldset>
			</div>
			<div align="left"
				style="width: 100%; float: none; clear: left; font-size: 0.7em">
				<fieldset>
					<legend class="legendtitle">Báo cáo xử lý đơn hàng </legend>
					<div style="width: 100%; float: none" align="left" class="plainlabel">
							<TABLE width="70%" cellpadding="6" cellspacing="0">
								<TR>
										<TD class="plainlabel">Từ ngày </TD>
											<TD class="plainlabel">	<input type="text" name="Sdays" id="Sdays" class="days"  />
											</TD>
											<TD class="plainlabel">Đến ngày </TD>
											<TD class="plainlabel">
												<input type="text" name="Edays" id="Edays" class="days" />
											</TD>
								</TR>
								
								<TR>
									<TD class="plainlabel">Vùng/Miền</TD>
									<TD class="plainlabel" >
										<select name="vungId" id="vungId"  style ="width:200px" onchange="changeform();">
											<option value="" selected>All</option>
											<%if (vung != null)
													while (vung.next()) {
														if (vung.getString("pk_seq").equals(obj.getVungId())) {%>
													<option value="<%=vung.getString("pk_seq")%>" selected><%=vung.getString("ten")%></option>
												<%} else {%>
													<option value="<%=vung.getString("pk_seq")%>"><%=vung.getString("ten")%></option>
											<%}}%>
										</select>
									</TD>
									
									<TD class="plainlabel">Khu vực</TD>
									<TD class="plainlabel">
										<select name="khuvucId"  style ="width:200px" onchange="changeform();">
			                                 <option value ="" > </option>  
			                               <%			                               
			                               while(khuvuc.next())
			                               {
			                               if(khuvuc.getString("kvid").equals(obj.getKvId())) {
			                            	%><option value ="<%=khuvuc.getString("kvid") %>" selected> <%=khuvuc.getString("kv") %></option>  
			                            	  <%} else { %>
			                            	  <option value ="<%=khuvuc.getString("kvid") %>"> <%=khuvuc.getString("kv") %></option>
			                              <%}} %>                             
                              			</select>		  
									</TD>
								</TR>
								
								<TR>
									<td colspan="4"><a class="button" href="javascript:submitform();"> 
										<img style="top: -4px;" src="../images/button.png" alt=""> Tạo báo cáo
									</a></td>
								</TR>
							</TABLE>
						</div>
						
				</fieldset>
			</div>
		</div>
		<br />
	</form>
	<script type="text/javascript">
		$("select:not(.notuseselect2)").css({
			"width": "250px", 
			//"height": "200px"
		});
	</script>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</html>

<%
	if(vung != null){ vung.close(); vung = null ;} 
	if(khuvuc != null){ khuvuc.close(); vung = null ;} 
	if(dhlist != null){ dhlist.close(); dhlist = null ;} 
	
	if(obj != null)
	{
		obj.DBclose();
		obj = null;
	}
	session.setAttribute("obj", null);
	
	
}%>