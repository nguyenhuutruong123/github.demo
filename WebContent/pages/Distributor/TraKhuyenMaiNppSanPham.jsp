<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.sql.SQLException"%>
<%@ page  import = "geso.dms.distributor.beans.quanlykhuyenmai.*" %>
<%@ page  import = "geso.dms.distributor.beans.donhang.ISanpham" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "geso.dms.center.util.*" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>

<% ITrakhuyenmaiNpp tkmBean = (ITrakhuyenmaiNpp)session.getAttribute("tkmBean"); %>
<% List<ISanpham> spList = (List<ISanpham>)tkmBean.getSpList(); %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<TITLE>Best - Project</TITLE>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">

	<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
    <LINK rel="stylesheet" href="../css/main.css" type="text/css">
    <LINK rel="stylesheet" href="../css/datepicker.css" type="text/css">
    
    <script language="javascript" src="../scripts/datepicker.js"></script>
   	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css"/>
  	<script type="text/javascript" src="../scripts/Timepicker/jquery.min.js"></script>
  	<script type="text/javascript" src="../scripts/Timepicker/jquery-ui.min.js"></script>
   
    <script language="javascript" type="text/javascript">
		function saveform()
		{	
			document.forms['tkmForm'].action.value='save';
		    document.forms['tkmForm'].submit();
		}
		function removeform()
		{	
			document.forms['tkmForm'].action.value='remove';
		    document.forms['tkmForm'].submit();
		}
		function keypress(e) //Hàm dùng d? ngan ngu?i dùng nh?p các ký t? khác ký t? s? vào TextBox
		{    
			var keypressed = null;
			if (window.event)
				keypressed = window.event.keyCode; //IE
			else
				keypressed = e.which; //NON-IE, Standard
			
			if (keypressed < 48 || keypressed > 57)
			{ 
				if (keypressed == 8 || keypressed == 127 || keypressed == 37 || keypressed == 39 || keypressed == 0 || keypressed == 46)
				{//Phím Delete và Phím Back
					return;
				}
				return false;
			}
		}
	</script>
    

<!-- BEGIN RENDER AUTO -->

<% if (!geso.dms.center.util.GlobalValue.getDevmode()) { %>
	<script type="text/javascript" src="../scripts/disableF12.js"></script>
<% } %>

<!-- END RENDER AUTO -->
</head>
<body>
<!-- BEGIN RENDER AUTO -->
<%if(!geso.dms.center.util.GlobalValue.getDevmode()){ %>
	<noscript> 
	 	<meta http-equiv="refresh" content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")  %>">
		<!-- //window.location = "https://www.google.com"; -->
	</noscript>
<%} %>
<!-- END RENDER AUTO -->

<form name="tkmForm" method="post" action="../../TrakhuyenmaiNppUpdateSvl"> 
 <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<INPUT name="userId" type="hidden" value='<%= userId %>' size="30">
<INPUT name="nppId" type="hidden" value='<%= tkmBean.getNppId() %>' size="30">
<input type="hidden" name="action" value='1'>
<input type="hidden" name="ctkmId" value='<%= tkmBean.getCtkmId() %>'>
<input type="hidden" name="tkmId" value='<%= tkmBean.getTkmId() %>'>
<div id="main" style="width:99%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:55%; padding:5px; float:left" class="tbnavigation">
        	Quản lý khuyến mại > Sản phẩm trả khuyến mại > Cập nhật
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng bạn   <%= tkmBean.getNppTen() %> 
        </div>
    </div>
  
  	<div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="tbdarkrow">
    	<A href= "javascript:history.back()" >
        	<img src="../images/Back30.png" alt="Quay về"  title="Quay về" border="1px" longdesc="Quay ve" style="border-style:outset"></A>
        <A href="javascript:removeform()" >
        	<IMG src="../images/Delete30.png" title="Go bo thiet lap" alt="Go bo thiet lap" border ="1px" style="border-style:outset"></A>
        <A href="javascript:saveform()" >
        	<IMG src="../images/Save30.png" title="Lưu lại" alt="Lưu lại" border ="1px" style="border-style:outset"></A>
    </div>
    
  	<div align="left" style="width:100%; float:none; clear:left">
  		<fieldset>
    		<legend class="legendtitle">Báo lỗi nhập liệu</legend>
    		<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold" style="width:100%" rows="1" readonly="readonly"><%= tkmBean.getMsg() %></textarea>
		         <% tkmBean.setMsg(""); %>
    	</fieldset>
  	</div>
  	
    <div align="left" style="width:100%; float:none; clear:left">
    <fieldset>
    	<legend class="legendtitle"> Trả khuyến mại </legend>
        	
        	<div style="float:none; width:100%" align="left">
            	<table width="100%" cellspacing="0" cellpadding="6px">
                    <tr>
                    	<td class="plainlabel" width="20%" valign="middle">Chương trình khuyến mại</td>
                    	<td class="plainlabel">
                            <i><%= tkmBean.getScheme() + " - " + tkmBean.getCtkmDiengiai() %></i>
                    	</td> 
                    	<td class="plainlabel">&nbsp;</td>                   
                    </tr>       
                    <tr>
                    	<td class="plainlabel" width="30%" valign="middle">Từ ngày</td>
                    	<td class="plainlabel">
                            <i><%= tkmBean.getTungay() %></i>
                    	</td> 
                    	<td class="plainlabel">&nbsp;</td>                   
                    </tr>     
                    <tr>
                    	<td class="plainlabel" width="30%" valign="middle">Đến ngày</td>
                    	<td class="plainlabel">
                            <i><%= tkmBean.getDenngay() %></i>
                    	</td> 
                    	<td class="plainlabel">&nbsp;</td>                   
                    </tr> 
                    <tr>
                    	<td class="plainlabel" width="30%" valign="middle">Trả khuyến mại</td>
                    	<td class="plainlabel">
                            <i><%= tkmBean.getTkmId() + " - " + tkmBean.getDiengiai() %></i>
                    	</td> 
                    	<td class="plainlabel">&nbsp;</td>                   
                    </tr>    
                    <tr>
                    	<td class="plainlabel" width="30%" valign="middle">Tình trạng</td>
                    	<td class="plainlabel">
                    		<% if(tkmBean.getTrangthai().length() > 0) {%>
                            	<i style="color: red;">Đã thiết lập sản phẩm ưu tiên khi áp khuyến mại</i>
                            <%}else{ %>
                            	<i style="color: red;">Chọn sản phẩm khi áp khuyến mại</i>
                            <%} %>
                    	</td> 
                    	<td class="plainlabel">&nbsp;</td>   
                    </tr>         
                 </table>
                 <hr>
                 <% if(spList.size() > 0){ %>
                 <table width="100%" cellpadding="4px" cellspacing="1px">
                 	<tr>
                    	<th class="tbheader" align="left">Mã sản phẩm</th>
                        <th class="tbheader" align="left">Tên sản phẩm</th>
                        <th class="tbheader" align="center">Đơn giá</th>
                        <th class="tbheader" align="center">Tồn hiện tại</th>
                        <th class="tbheader" align="center" width="15%">Thứ tự ưu tiên</th>
                    </tr>
                    
                    <% for(int i = 0; i < spList.size(); i++) { ISanpham sp = spList.get(i); %>
		    			<tr class="tbdarkrow">
	                    	<td align="left">
	                    		<input type="text" name="spMa" style="width: 100%; text-align: left;" value=" <%= sp.getMasanpham() %>" readonly="readonly">
	                    		<input type="hidden" name="spId" style="width: 100%; text-align: left;" value=" <%= sp.getId() %>" readonly="readonly">
	                    	</td>
	                        <td align="left">
	                        	<input type="text" name="spTen" style="width: 100%; text-align: left;" value=" <%= sp.getTensanpham() %>" readonly="readonly">
	                        </td>
	                        <td align="right">
	                        	<input type="text" name="dongia" style="width: 100%; text-align: right;" value=" <%= sp.getDongia() %>" readonly="readonly">
	                        </td>
	                        <td align="right">
	                        	<input type="text" name="soluong" style="width: 100%; text-align: right;" value=" <%= sp.getSoluong() %>" readonly="readonly">
	                        </td>
	                        <td align="right">
	                        	<input type="text" name="thutu" style="width: 100%; text-align: right;" value=" <%= sp.getDonvitinh() %>" onkeypress="return keypress(event);">
	                        </td>
                    	</tr>
	     			<% }%>
	     			
                    <tr class="tbfooter"><td colspan="5">&nbsp;</td></tr>
                 </table>
                 <%} %>
            </div>   
    </fieldset>	
    </div>
</div>
</form>
</body>
</html>
<%
tkmBean.DbClose();
}%>