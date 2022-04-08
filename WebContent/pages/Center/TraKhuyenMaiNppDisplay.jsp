<%@page import="geso.dms.center.beans.trakhuyenmainpp.ITrakhuyenmainpp"%>
<%@page import="geso.dms.center.beans.trakhuyenmainpp.ITrakhuyenmainppList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.dms.center.beans.trakhuyenmai.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<% ITrakhuyenmainpp obj = (ITrakhuyenmainpp)session.getAttribute("obj"); %>
<% ResultSet ctkmIds = obj.getctkmIds(); %>
<% ResultSet nppIds = obj.getnppIds(); %>
<% ResultSet sanpham = obj.getSanpham(); %>
<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Insert title here</title>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">
    <LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
    <LINK rel="stylesheet" href="../css/main.css" type="text/css">
    <LINK rel="stylesheet" href="../css/datepicker.css" type="text/css">
    
    <script language="javascript" src="../scripts/datepicker.js"></script>
   	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css"/>
  	<script type="text/javascript" src="../scripts/Timepicker/jquery.min.js"></script>
  	<script type="text/javascript" src="../scripts/Timepicker/jquery-ui.min.js"></script>
   
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
		$(document).ready(function(){
            $(".button3").hover(function(){
                $(".button3 img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        }); 
    </script>
    <SCRIPT language="javascript" type="text/javascript">
	 function confirmLogout()
	 {
	    if(confirm("Ban co muon dang xuat?"))
	    {
			top.location.href = "home.jsp";
	    }
	    return
	 }
	 function submitform()
	 {   
	    document.forms["trakmnppForm"].submit();
	 }
	
	
	 function tong()
	 {   var tong = 0;
		 var chon = document.getElementsByName("chon");
		 var dasudung =document.getElementsByName("dasudung");
		 var tongtien = document.getElementById("tongtien");
		 var i=0;
		 for(i=0; i<dasudung.length; i++)
		 {//alert("thong bao");
			 	if(document.trakmnppForm.chon.item(i).checked ==true){
			 	 tong  = tong + parseInt(dasudung.item(i).value);
		      }
		 }
		 tongtien.value = tong;
		 setTimeout(tong,20);
	 }
	 tong();
	 function saveform()
	 {
		 var diengiai = document.getElementById("diengiai");
		 if(diengiai.value.length >0)
		 {
		    document.forms["trakmnppForm"].action.value = "save";
		    document.forms["trakmnppForm"].submit();
		 }
		 else
			 alert("Ban phai nhap dien giai");
	 }
	</SCRIPT>

<!-- BEGIN RENDER AUTO -->

<% if (!geso.dms.center.util.GlobalValue.dev_mode) { %>
	<script type="text/javascript" src="../scripts/disableF12.js"></script>
<% } %>

<!-- END RENDER AUTO -->
</head>
<body>
<!-- BEGIN RENDER AUTO -->
<%if(!geso.dms.center.util.GlobalValue.dev_mode){ %>
	<noscript> 
	 	<meta http-equiv="refresh" content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")  %>">
		<!-- //window.location = "https://www.google.com"; -->
	</noscript>
<%} %>
<!-- END RENDER AUTO -->

<form name="trakmnppForm" method="post" action="../../TrakhuyenmainppUpdateSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="action" value="1" >
<input type="hidden" name="id" value="<%= obj.getId() %>" >
<div id="main" style="width:99%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:70%; padding:5px; float:left" class="tbnavigation">
        Quản lý khuyến mãi> Trả khuyến mãi> hien thi
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        Chào mừng bạn <%= userTen %> &nbsp;
        </div>
    </div>
  	<div id="cotent" style="width:98%; float:none">
    	<div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
                 <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
                    <TR class = "tbdarkrow">
                        <TD width="30" align="left"><A href= "javascript:history.back()" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
                        <TD width="2" align="left" ></TD>
                        
                        <TD align="left" >&nbsp;</TD>
                    </TR>
                </TABLE>
            </TD>
            <fieldset>

    		<legend class="legendtitle"> Báo lỗi nhập liệu</legend>
    		<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold" readonly="readonly" rows="1"><%= obj.getMsg() %></textarea>
		         
    	</fieldset>
        <fieldset>
            <legend class="legendtitle"> Tiêu chí tìm kiếm </legend>
            
                <TABLE width="100%" cellpadding="6px" cellspacing="0px">
                    <TR>
                        <TD class="plainlabel" valign="middle" width="15%">Diễn giải </TD>
                        <TD class="plainlabel" valign="middle">
                            <input type="text" name="diengiai" id ="diengiai" value="<%= obj.getDiengiai() %>">
                        </TD>                        
                    </TR>      
                     	<TR>
                   	          <TD class="plainlabel">nha phan phoi </TD>
								      <TD class="plainlabel">
								      	<SELECT name="nppid" onchange="submitform();">								      
								  	 		<option value =""></option>
								  	 <% try{ while(nppIds.next()){ 
								    	if(nppIds.getString("pk_seq").equals(obj.getnppId())){ %>
								      		<option value='<%=nppIds.getString("pk_seq") %>' selected><%=nppIds.getString("ten") %></option>
								      	<%}else{ %>
								     		<option value='<%=nppIds.getString("pk_seq") %>'><%=nppIds.getString("ten") %></option>
								     	<%}}}catch(java.sql.SQLException e){} %>	
								     	
									</SELECT></TD>
								</TR>  
								 <TR>
                        <TD class="plainlabel" valign="middle" width="15%">Tong tien tra </TD>
                        <TD class="plainlabel" valign="middle">
                            <input type="text" id ="tongtien" name="tongtien" value="<%= obj.getTongtien() %>" readonly>
                        </TD>                        
                    </TR>  
	            </TABLE>                      
        </fieldset>                      
    	</div>
        <div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
        	<fieldset>
            	<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
								<TR class="tbheader">
				                    <TH align="center">Ct khuuyen mai</TH>
				                    <TH align="center">Ngan sach</TH>
				                    <TH align="center">Da su dung</TH>
				                    <TH align="center">Chon</TH>
				                 </TR>
							  <%
							   int m =0;
							   String lightrow = "tblightrow";
								String darkrow = "tbdarkrow";
							
							   if(sanpham != null) 
							   while(sanpham.next()) {
								 if(m % 2 == 0) { %>
							     <TR class ="tblightrow">
							      <% }else { %>
							      <TR class ="tbdarkrow">
							      <%} %>
							   <TD align="left"> <%= sanpham.getString("diengiai") %> </TD>
							   <TD align="left"><input type="text" name ="ngansach" value =' <%= sanpham.getString("ngansach") %>'> </TD>
							    <TD align="left"><input type="text" name ="dasudung" value ='<%= sanpham.getString("dasudung") %>'>  </TD>
							    <% if(sanpham.getString("chon").equals("1")) {%>
							    <TD align="left"> <input type ="checkbox" name ="chon" value ='<%= sanpham.getString("pk_seq") %>' checked> </TD>
							    <%} else { %>
							      <TD align="left"> <input type ="checkbox" name ="chon" value ='<%= sanpham.getString("pk_seq") %>'> </TD>
							    <%} %>
							   </TR>
							  <%m++;} %>
								<TD align="center" colspan="10"> |< < 1 to 20 of 100 > >|</TD>
							
						</TABLE>
						
            </fieldset>
        </div>
    </div>  
       <SCRIPT language="javascript" type="text/javascript">
        tong();
 //       setTimeout(tong,20);
      </SCRIPT>
</div>
</form>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</html>