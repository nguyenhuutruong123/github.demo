<%@page import="geso.dms.center.beans.phieuthanhtoantt.IPhieuThanhToanTT"%>
<%@page import="java.sql.ResultSet"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.dms.distributor.beans.phieuthanhtoan.*" %>
<% IPhieuThanhToanTT obj = (IPhieuThanhToanTT)session.getAttribute("obj"); %>
<% List<IPhieuThanhToanTT> listphieu=obj.getListThanhToan(); %>
	
<%
    String userId = (String) session.getAttribute("userId"); 
	String userTen=(String)session.getAttribute("userTen");
%>

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
	    if(confirm("Bạn có muốn đăng xuất?"))
	    {
			top.location.href = "home.jsp";
	    }
	    return
	  }
	 function submitform()
	 {   
		 document.forms["pttForm"].submit();
	 }
	 function newform()
	 {   
		document.forms["pttForm"].action.value = "new";
	    document.forms["pttForm"].submit();
	 }
	 function clearform()
	 {   
	    document.forms["pttForm"].diengiai.value = "";
	    document.forms["pttForm"].tungay.value = "";
	    document.forms["pttForm"].denngay.value = "";
	    document.forms["pttForm"].sotien.value = "";
	    submitform();
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
				if (keypressed == 8 || keypressed == 127 || keypressed == 37 || keypressed == 39)
				{//Phím Delete và Phím Back
					return;
				}
				return false;
			}
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

<form name="pttForm" method="post" action="../../PhieuThanhToanTTSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="userTen" value="<%=userTen%>" >
<input type="hidden" name="action" value="1" >

<div id="main" align="left" style="width:99%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:60%; padding:5px; float:left" class="tbnavigation">
        	Quản lý công nợ > Phiếu thanh toán
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng bạn  <%= userTen %> &nbsp;
        </div>
    </div>
  	<div id="cotent" style="width:100%; float:none">
    	<div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
        <fieldset>
            <legend class="legendtitle"> Tiêu chí tìm kiếm </legend>
                <TABLE width="100%" cellpadding="5px" cellspacing="0px">								                 
                    <TR>
                        <TD class="plainlabel" valign="middle" width="15%">Diễn giải </TD>
                        <TD class="plainlabel"  colspan="2"><input type="text" name="diengiai" value="<%= obj.getDiengiai() %>" onchange="submitform();"></TD>                       
                    </TR>            
                    <TR>
                        <TD class="plainlabel" >Từ ngày </TD>
                        <TD class="plainlabel" colspan="2">
                            <input type="text" size="10" class="w8em format-y-m-d divider-dash highlight-days-12" 
                                   id="tungay" name="tungay" value="<%= obj.getTungay() %>" maxlength="10" onchange="submitform();" />
                        </TD>
                    </TR>
                     <TR>
                        <TD class="plainlabel" >Đến ngày </TD>
                        <TD class="plainlabel" colspan="2">
                            <input type="text" size="10" class="w8em format-y-m-d divider-dash highlight-days-12" 
                                   id="denngay" name="denngay" value="<%= obj.getDenngay() %>" maxlength="10" onchange="submitform();"/>
                        </TD>
                    </TR>
                    <TR>
                        <TD class="plainlabel" >Số tiền </TD>
                        <TD class="plainlabel" colspan="2"> <input type="text" name="sotien" value="<%=obj.getSotien()%>" onchange="submitform();" /> </TD>                       
                    </TR>   
                    <tr >
                        <td class="plainlabel">
                                <a class="button2" href="javascript:clearform()">
                                <img style="top: -4px;" src="../images/button.png" alt="">Nhập lại </a>
                        </td>
                        <TD class="plainlabel" colspan="2">
                        </TD>
                    </tr>        					
                </TABLE>                      
        </fieldset>                      
    	</div>
        <div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
        	<fieldset>
            	<legend><span class="legendtitle"> Phiếu thanh toán </span>&nbsp;&nbsp;&nbsp;
                	<a class="button3" href="javascript:newform()">
                           <img style="top: -4px;" src="../images/New.png" alt="">Tạo mới  </a>
                </legend>
            	<TABLE width="100%" border="0" cellspacing="1px" cellpadding="4px">
					<TR class="tbheader">
                             	<TH align="center">Số phiếu</TH>
						<TH align="center">Diễn giải</TH>									
						<TH align="left" > Ngày thanh toán</TH>
						<TH align="center">Số tiền </TH>
						<TH align="center">Ngày nhập </TH>
						<TH align="left">Người nhập </TH>
						<TH align="left">Ngày sửa </TH>
						<TH align="left">Người sửa </TH>
						<TH align="center">Trạng thái </TH>
						<TH align="center"> Tác vụ</TH>
					</TR>								
					<%      
                       
                        int m = 0;
						if(listphieu != null)
						{
						
                        while (m<listphieu.size()){

                        	IPhieuThanhToanTT phieu=listphieu.get(m);
                      if(m%2==0){
                        	%> 
                        	 <TR class="tbdarkrow" >       
                        	 <%}else{ %>
                        	 <TR class="tblightrow" >
                        	 <%} %>                        
                             <TD align="center"><%= phieu.getId()%></TD>                                   
                             <TD align="center"><%=phieu.getDiengiai() %></TD>
                             <TD align="left"><%= phieu.getNgaythanhtoan() %></TD>
                             <TD align="right"><%=Math.round(phieu.getSotien()) %></TD>
                             <TD align="left"><%= phieu.getNgayTao()%></TD>                            
                             <TD align="left"><%=phieu.getNguoiTao() %></TD>
                             <TD align="left"><%= phieu.getNgaySua() %></TD>                            
                             <TD align="left"><%= phieu.getNguoiSua() %></TD>
                             
                             <% if(phieu.getTrangThai().equals("0")){ %>
                             	<TD align="center">Đã nhập</TD>
                             <%}else  if(phieu.getTrangThai().equals("1")){%>
                             	<TD align="center">Đã chốt</TD>
                             <%} else { %>
                             	<TD align="center">Đã hủy</TD>
                             <%} %>
                              <TD align="center"> 
                             <% if(phieu.getTrangThai().equals("0")){ %>
                             	<A href = "../../PhieuThanhToanTTSvl?userId=<%=userId%>&chotphieu=<%=phieu.getId()%>"><img src="../images/Chot.png" alt="Chot phieu" title="Chốt phiếu" width="20" height="20" longdesc="Chot phieu" border ="0"></A>  
                             	<A href = "../../PhieuThanhToanTTSvl?userId=<%=userId%>&delete=<%=phieu.getId()%>"><img src="../images/Delete20.png" alt="Huy phieu" title="Hủy phiếu" width="20" height="20" longdesc="Huy Phieu" border="0" onclick="if(!confirm('Bạn chắc chắn muốn hủy phiếu thanh toán này ?')) return false;" ></A>
                             	<A href = "../../PhieuThanhToanTTNewSvl?userId=<%=userId%>&update=<%=phieu.getId()%>"><IMG src="../images/Edit20.png" alt="Cap nhat" title="Chỉnh sửa" border="0"></A>
                             <%}else{ %>
                             	<A href = "../../PhieuThanhToanTTNewSvl?userId=<%=userId%>&display=<%=phieu.getId()%>"><img src="../images/Display20.png" alt="Hien thi" title="Hiển thị" width="20" height="20" longdesc="Hien thi" border ="0"></A>  
                             <%} %>
                             </TD>                                      
                         </TR>
                     <% m++; } }%>
					<TR class="tbfooter" >
						<TD align="center" colspan="13"> |< < 1 to 20 of 100 > >| </TD>
						
					</TR>
				</TABLE>
            </fieldset>
        </div>
    </div>  
</div>
</form>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</html>
    

