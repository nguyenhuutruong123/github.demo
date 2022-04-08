<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.dms.distributor.beans.khoasongay.IKhoasongay" %>
<%@ page  import = "geso.dms.distributor.beans.khoasongay.imp.Khoasongay" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>
<%@page import="geso.dms.distributor.util.Utility"%>

<%@ page  import = "geso.dms.center.util.*" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	geso.dms.center.util.Utility util = (geso.dms.center.util.Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>

<% IKhoasongay obj = (IKhoasongay)session.getAttribute("obj"); %>
<% String msg = obj.getMessege(); %>
<% Utility Ulti = new Utility();%>
<% String ngaykhoaso = Ulti.ngaykhoaso(obj.getNppId());%>
<% ResultSet dhChuaChotList = (ResultSet)obj.getDhChuaChotList(); %>
<% ResultSet dhDaXuatKhoList = (ResultSet)obj.getDhDaXuatKhoList(); %>
<% ResultSet dhDaChotList = (ResultSet)obj.getDhDaChotList();
	
	int[] quyen = new  int[6];
	quyen = util.Getquyen("KhoasongaySvl","", userId);
	String url = util.getUrl("KhoasongaySvl","");
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<TITLE>Best - Project</TITLE>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">

	<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
    <LINK rel="stylesheet" href="../css/main.css" type="text/css">
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
    
    <script type="text/javascript" src="..scripts/jquery-1.js"></script>
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
		$(document).ready(function(){
            $(".button2").hover(function(){
                $(".button2 img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        }); 
    </script>
    
    <script language="javascript" type="text/javascript">
		function submitform()
		{   
			document.forms['ksnForm'].action.value='submit';
		    document.forms['ksnForm'].submit();
		}
		function backform()
		{
			window.history.back();
		}
		function saveform()
		{	
			var dhChuachot = document.getElementsByName('dhChuachot');
			var dhDaxuatkho = document.getElementsByName('dhDaxuatkho');
			
			if(dhChuachot.length > 0 )
			{
				var r = confirm("Có đon hàng chưa chốt, ngày đon hàng sẽ được tăng thêm 1 ngày, bạn vẫn muốn tiếp tuc?");
				if(r == false)
					return;
			}
			if(dhDaxuatkho.length > 0 )
			{
				var rr = confirm("Có đơn hàng đã xuất kho nhưng chưa chốt, đơn hàng sẽ tự động chuyển thành đã chốt, bạn vẫn muốn tiếp tục?");
				if(rr == false)
					return;
			} 
			if(document.getElementById("isPxkCc").value == "true")
			{
				var rrr = confirm("Có phiếu xuất kho chưa chốt, phiếu xuất kho sẽ tự động chuyển sang ngày tiếp theo, bạn vẫn muốn tiếp tục?");
				if(rrr == false)
					return;
			} 
			if(document.getElementById("isPthCc").value == "true")
			{
				var rrrr = confirm("Có phiếu thu hồi chưa chốt, phiếu thu hồi sẽ tự động chốt, bạn vẫn muốn tiếp tục?");
				if(rrrr == false)
					return;
			} 
			var r = confirm("Bạn chắc chắn muốn khóa sổ, đơn hàng chưa chốt sẽ bị xóa khuyến mãi và đẩy ngày đơn hàng sang đầu tháng tới ! ");
			 if (r == false) {		 
			     return;
			 }
		
			document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Processing...</a>";

			document.forms['ksnForm'].action.value='save';
		    document.forms['ksnForm'].submit();
		}
		
		function reload()
		{						
			parent.frames[0].document.khoaso();
		}
	</script>

<!-- BEGIN RENDER AUTO -->

<% if (!geso.dms.center.util.GlobalValue.dev_mode) { %>
	<script type="text/javascript" src="../scripts/disableF12.js"></script>
<% } %>

<!-- END RENDER AUTO -->
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" onload="reload()" rightmargin="0">
<!-- BEGIN RENDER AUTO -->
<%if(!geso.dms.center.util.GlobalValue.dev_mode){ %>
	<noscript> 
	 	<meta http-equiv="refresh" content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")  %>">
		<!-- //window.location = "https://www.google.com"; -->
	</noscript>
<%} %>
<!-- END RENDER AUTO -->

<form name="ksnForm" method="post" action="../../KhoasongaySvl"> 
 <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="nppId" value='<%= obj.getNppId() %>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="isPxkCc" id="isPxkCc" value='<%= obj.isPxkChuaChot() %>'>
<input type="hidden" name="isPthCc" id="isPthCc" value='<%= obj.isPthChuaChot() %>'>
<input name="userId" type="hidden" value='<%=userId %>' size="30">
<input type="hidden" name="ngaykhoaso" id = "ngaykhoaso" value='<%= ngaykhoaso %>'>
<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:70%; padding:5px; float:left" class="tbnavigation">
        	 <%=url %>
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng bạn  <%= obj.getNppTen() %> &nbsp; 
        </div>
    </div>
  
  	<div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="tbdarkrow">
    	<A href="javascript:backform()">
        	<img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1px" longdesc="Quay ve" style="border-style:outset"></A>
        <label id="btnSave">
        <A href="javascript:saveform()" >
        	<IMG src="../images/Save30.png" title="Khoa so ngay" alt="Khoa so ngay" border ="1px" style="border-style:outset"></A>
        	</label>
    </div>
  	<div align="left" style="width:100%; float:none; clear:left">
  		<fieldset>
    		<legend class="legendtitle">Thông báo </legend>
    		<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold" style="width:100%" rows="1" readonly="readonly"><%= obj.getMessege() %></textarea>
		         <% obj.setMessege(""); %>
    	</fieldset>
  	</div>
    <div align="left" style="width:100%; float:none; clear:left">
    <fieldset>
    	<legend class="legendtitle">Khóa sổ tháng </legend>    	
        	<div style="float:none; width:100%" align="left">
        		<TABLE width="100%" cellpadding="5px" cellspacing="0px">
        			<TR>
		                <TD class="plainlabel" width="20%">Tháng khóa sổ gần nhất</TD>
		                <TD class="plainlabel" colspan="1">
		                    <i><%= obj.getNgayKhoaSoGanNhat() %></i>
		                    <input type="hidden" name="ngayksgn" value="<%= obj.getNgayKhoaSoGanNhat() %>" >
		                </TD>
		            </TR>
		            <TR id="TheoThang">
									<TD class="plainlabel">Tháng khóa sổ</TD>
									<TD class="plainlabel">
									 <select disabled name="tuthang" class="notuseselect2"  style="width :100px" onchange="loctien()">
									<option value=0> </option>  
									<%
  										int k=1;
  									for(k=1;k<=12;k++){
  										//String chuoi=k<10?"0"+k:""+k;
  									  if(obj.getFromMonth().equals( (k + "") )){
  									%>
									<option value=<%=k%> selected="selected" > <%=k%></option> 
									<%
 										}else{
 									%>
									<option value=<%=k%> ><%=k%></option> 
									<%
 										}
 									  }
 									%>
									</select>
									<select disabled name="tunam" class="notuseselect2"  style="width :100px" onchange="loctien()">
									<option value=0> </option>  
									<%
									  
  										int n;
  										for(n=2019;n<2030;n++){
  										if(obj.getFromYear().equals(""+n)){
  									%>
									<option value=<%=n%> selected="selected" > <%=n%></option> 
									<%
 										}else{
 									%>
									<option value=<%=n%> ><%=n%></option> 
									<%
 										}
 									 }
 									%>
									</TD>
									  </TR>        
		             <%-- <TR>
		                <TD class="plainlabel"  width="20%">Ngày khóa sổ</TD>
		                <TD class="plainlabel" colspan="2">
		                    <input type="text" size="11"
			                        id="ngayks" name="ngayks" value="<%= obj.getNgaykhoaso() %>" maxlength="10" readonly />
		                </TD>
		            </TR>         	 --%>
	            </TABLE>
	            <hr />
              <%--    <table width="100%" cellpadding="5px" cellspacing="1px">
                 	<tr class="tbheader">
                    	<th align="center">Mã đơn hàng</th>
                        <th align="center">Trạng thái</th>
                        <th align="center">Mã khách hàng</th>
                        <th align="center">Tên khách hàng</th>
                        <th align="center"> Tổng giá trị </th>
                    </tr>
                    <% if(dhChuaChotList != null){
					try{ while(dhChuaChotList.next())
					{ %>
					<TR class= "tbdarkrow" style="background-color:#F9C">
						<TD align="center">
							<%= dhChuaChotList.getString("dhId") %>
							<input type="hidden" name = "dhChuachot" value="<%= dhChuaChotList.getString("dhId") %>" />
						</TD>
						<TD align="left">Chưa chốt</TD>
						<TD align="center"><%= dhChuaChotList.getString("smartid") %></TD>	
						<TD align="left"><%= dhChuaChotList.getString("khTen") %></TD>									
						<TD align="right"><%= dhChuaChotList.getInt("tonggiatri") %> VNĐ </TD>
                    </TR> 
                    <%}dhChuaChotList.close(); }catch(java.sql.SQLException e){} }%>
                    
                    <% if(dhDaXuatKhoList != null){
					try{ while(dhDaXuatKhoList.next())
					{ %>
					<TR class= "tbdarkrow" style="background-color:#9F6">
						<TD align="center">
							<%= dhDaXuatKhoList.getString("dhId") %>
							<input type="hidden" name = "dhDaxuatkho" value="<%= dhDaXuatKhoList.getString("dhId") %>" />
						</TD>
						<TD align="left">Đã xuất kho</TD>
						<TD align="center"><%= dhDaXuatKhoList.getString("smartid") %></TD>	
						<TD align="left"><%= dhDaXuatKhoList.getString("khTen") %></TD>									
						<TD align="right"><%= dhDaXuatKhoList.getInt("tonggiatri") %> VNĐ </TD>
                    </TR> 
                    <%}dhDaXuatKhoList.close(); }catch(java.sql.SQLException e){} }%>
                    
                    <% if(dhDaChotList != null){
					try{ while(dhDaChotList.next())
					{ %>
					<TR class= "tbdarkrow" >
						<TD align="center"><%= dhDaChotList.getString("dhId") %></TD>
						<TD align="left">Đã chốt</TD>
						<TD align="center"><%= dhDaChotList.getString("smartid") %></TD>	
						<TD align="left"><%= dhDaChotList.getString("khTen") %></TD>									
						<TD align="right"><%= dhDaChotList.getInt("tonggiatri") %> VNĐ</TD>
                    </TR> 
                    <%}dhDaChotList.close(); }catch(java.sql.SQLException e){} }%>
                    <tr class="tbfooter"><td colspan="5">&nbsp;</td></tr>
                 </table> --%>
            </div>         
    </fieldset>	
    </div>
</div>
</form>
</BODY>
</html>


<% 	
	try{
		if(dhChuaChotList != null){ dhChuaChotList.close(); dhChuaChotList = null ; }
		if(dhDaChotList != null){ dhDaChotList.close(); dhDaChotList = null ; } 
		if(dhDaXuatKhoList != null){ dhDaXuatKhoList.close(); dhDaXuatKhoList = null ; }
				
		if(obj != null){
			obj.DBclose();
			obj = null;
			util=null;
		}
		session.setAttribute("obj",null);
	}
	catch (SQLException e) {}
%>
<%}%>