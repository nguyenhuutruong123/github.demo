<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.beans.mucchietkhau.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.NumberFormat"%>
<%	
	NumberFormat formatter = new DecimalFormat("#,###,###"); 
	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>

<% IChietkhau ckBean = (IChietkhau)session.getAttribute("ckBean"); 
   ResultSet npp = ckBean.getNppRs(); 
  %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<HEAD>
<TITLE>Acecook - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print"
	href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">

<SCRIPT language="javascript" type="text/javascript">
 function confirmLogout(){
    if(confirm("Ban co muon dang xuat?"))
    {
		top.location.href = "home.jsp";
    }
    return
  }
 function submitform()
 {
     document.forms['ckForm'].submit();
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
 function saveform()
 {
	 var sotien = document.getElementById('sotien');
	 var chietkhau = document.getElementById('chietkhau');
	 if(sotien.value == "")
	 {
		 alert('Vui Long Nhap So Tien');
		 return;
	 }
	 if(chietkhau.value == "")
	 {
		 alert('Vui Long Nhap Chiet Khau');
		 return;
	 }	
	 if(checkNpp() == false)
	 {
		 alert('Khong co nha phan phoi nao duoc chon');
		 return;
	 }
 	 document.forms['ckForm'].action.value= 'save';
     document.forms['ckForm'].submit();
 }
 function checkNpp()
 {
	 var npp = document.getElementsByName("nppTen");
	 for(i = 0; i < npp.length; i++)
	 {	 
		 if(npp.item(i).checked)
			 return true;
	 }
	 return false;
			 
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

<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<!-- BEGIN RENDER AUTO -->
<%if(!geso.dms.center.util.GlobalValue.getDevmode()){ %>
	<noscript> 
	 	<meta http-equiv="refresh" content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")  %>">
		<!-- //window.location = "https://www.google.com"; -->
	</noscript>
<%} %>
<!-- END RENDER AUTO -->

<form name='ckForm' method="post" action="../../ChietkhauUpdateSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="id" value='<%= ckBean.getId() %>'>
<INPUT name="userId" type="hidden" value='<%= userId %>' size="30">
<div id="main" style="width:99%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:60%; padding:5px; float:left" class="tbnavigation">
        	Dữ liệu nền > Kinh doanh > Mức chiết khấu > Cập nhật
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng bạn <%= userTen %> &nbsp;
        </div>
    </div>
  
  	<div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="tbdarkrow">
    	<A href="javascript:history.back()" ><img src="../images/Back30.png" alt="Quay về"  title="Quay về" border="1" longdesc="Quay về" style="border-style:outset"></A>
        <A href="javascript: saveform()" ><IMG src="../images/Save30.png" title="Lưu lại" alt="Lưu lại" border = "1"  style="border-style:outset"></A>
    </div>
  	
  	<div align="left" style="width:100%%; float:none; clear:left">
  		<fieldset>
    		<legend class="legendtitle"> Thông báo</legend>
    		<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold"  rows="1" readonly="readonly" style ="width:100%%"><%= ckBean.getMessage() %></textarea>
		         <% ckBean.setMessage(""); %>
    	</fieldset>
  	</div>
  	
    <div align="left" style="width:100%; float:none; clear:left">
    <fieldset>
    	<legend class="legendtitle">Thông tin chiết khấu </legend>
        	<div style="float:none; width:100%" align="left">
            <TABLE class="tblight" width="100%" cellspacing="0" cellpadding="6">
				<TR>
				  <TD width="15%" class="plainlabel" >Diễn giải</TD>
				  <TD  class="plainlabel" ><INPUT name="diengiai" 
					type="text" value='<%= ckBean.getDiengiai() %>' size="20"></TD>
			  	</TR>
				<TR>
				  <TD width="15%" class="plainlabel" >Số tiền <FONT class="erroralert"></FONT></TD>
				  <% 
					  String gia="";
					  if(ckBean.getSotien().trim().length()!=0)
					  {
						 gia= formatter.format(Double.parseDouble(ckBean.getSotien()));
					  }
					  else
					  {
						  gia=ckBean.getSotien();
					  }
				  %>
				  <TD  class="plainlabel" ><INPUT name="sotien" id="sotien" onkeyup="Dinhdang(this)"
					type="text" value='<%= gia %>' size="20"> VNĐ</TD>
			  	</TR>
				<TR>
				  <TD class="plainlabel" >Mức chiết khấu <FONT class="erroralert"></FONT></TD>
				  <TD class="plainlabel" >
				  	<INPUT name="chietkhau" id="chietkhau" type="text" value='<%= ckBean.getChietkhau() %>' size="20" onkeypress="return keypress(event);"> %</TD>
			  	</TR>
			</TABLE>
            <hr> 
            </div>
           
           	<div align="left" style="width:100%; float:none; clear:none;">
           <TABLE class="tabledetail" width="100%" border="0" cellpadding="1" cellspacing="1" >
                <TR class="tbheader">                   
                 	<TH align="center" width="25%"> Mã NPP </TH>
                	<TH align="left" width="65%"> Tên NPP </TH>
               	 	<TH align="center" width="10%"> Chọn </TH>
                </TR>
                <% if(npp != null)
                {
				  try
				  { 
					  String optionGroup = "";
					  String optionName = "";
					  int i = 0;
					  
					  while(npp.next())
					  { 
						if(i ==  0)
						{ 
							optionGroup = npp.getString("kvTen");
							optionName = npp.getString("kvTen");
					 %>
							<tr class='tbdarkrow'><td colspan="3"><b><%= optionName %></b></td></tr>
						<%}
						 optionGroup = npp.getString("kvTen");
						 if(optionGroup.trim().equals(optionName.trim()))
						 { %>
							 <% if(ckBean.getNppIds().indexOf(npp.getString("nppId")) >= 0 ) {%>
							 	<tr class='tbdarkrow'>
							 		<td><%= npp.getString("nppId") %></td>
							 		<td><%= npp.getString("nppTen") %></td>
							 		<td align="center"><input type="checkbox" name="nppTen" value="<%= npp.getString("nppId") %>" checked="checked"></td>
							 	</tr>
							 <%} else { %>
							 	<tr class='tbdarkrow'>
							 		<td><%= npp.getString("nppId") %></td>
							 		<td><%= npp.getString("nppTen") %></td>
							 		<td align="center"><input type="checkbox" name="nppTen" value="<%= npp.getString("nppId") %>"></td>
							 	</tr>
							 <%} %>
						 <% }
						 else
						 {
							 %>
							<% optionName = optionGroup; %>
							<tr class='tbdarkrow'><td colspan="3"><b><%= optionName %></b></td></tr>
						 <% }
						 i++;
		     	 	  } 
					  %>
					  <%npp.close(); 
		     	 }
				catch(java.sql.SQLException e){} } %>	  
                
                <tr class="tbfooter">
                    <td colspan="4">&nbsp;</td>
                </tr>            
            </TABLE> 
        </div>      
     </fieldset>	
    </div>
</div>
</form>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</html>
<%
	ckBean.DBClose();
	}%>