<%@page import="geso.dms.center.beans.trakhuyenmainpp.ITrakhuyenmainppList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.dms.center.beans.trakhuyenmai.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<% ITrakhuyenmainppList obj = (ITrakhuyenmainppList)session.getAttribute("obj"); %>

<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  %>
<% ResultSet dsduyet = (ResultSet)obj.getDanhsachduyet(); %>
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
	 {   document.forms["trakmnppForm"].action.value = "thuchien";
	    document.forms["trakmnppForm"].submit();
	 }
	 function newform()
	 {   
		document.forms["trakmnppForm"].action.value = "Tao moi";
	    document.forms["trakmnppForm"].submit();
	 }
	 function clearform()
	 {   
	    document.forms["trakmnppForm"].diengiai.value = "";
	    document.forms["trakmnppForm"].tungay.value = "";
	    document.forms["trakmnppForm"].denngay.value = "";
	    document.forms["trakmnppForm"].submit();
	 }
	</SCRIPT>

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

<form name="trakmnppForm" method="post" action="../../TrakhuyenmainppSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="action" value="1" >


<div id="main" style="width:99%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:70%; padding:5px; float:left" class="tbnavigation">
        Quản lý khuyến mãi> Trả khuyến mãi
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        Chào mừng bạn <%= userTen %> &nbsp;
        </div>
    </div>
  	<div id="cotent" style="width:100%; float:none">
    	<div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
        <fieldset>
            <legend class="legendtitle"> Tiêu chí tìm kiếm </legend>
                <TABLE width="100%" cellpadding="6px" cellspacing="0px">								                 
                    <TR>
                        <TD class="plainlabel" valign="middle" width="15%">Diễn giải </TD>
                        <TD class="plainlabel" valign="middle">
                            <input type="text" name="diengiai" value="<%= obj.getDiengiai() %>">
                        </TD>                        
                    </TR>            
                    <TR>
                        <TD class="plainlabel" >Từ ngày </TD>
                        <TD class="plainlabel">
                            <input type="text" size="10" class="w8em format-d-m-y divider-dash highlight-days-12" 
                                   id="tungay" name="tungay" value="<%= obj.getTungay() %>" maxlength="10" />
                        </TD>
                    </TR>
                     <TR>
                        <TD class="plainlabel" >Đến ngày</TD>
                        <TD class="plainlabel">
                            <input type="text" size="10" class="w8em format-d-m-y divider-dash highlight-days-12" 
                                   id="denngay" name="denngay" value="<%= obj.getDenngay() %>" maxlength="10" />
                        </TD>
                    </TR>
                    <tr >
                        <td   class="plainlabel" colspan="2">
                            <a class="button" href="javascript:submitform()">
                                <img style="top: -4px;" src="../images/button.png" alt="">Tìm kiếm  </a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <a class="button2" href="javascript:clearform()">
                                <img style="top: -4px;" src="../images/button.png" alt="">Nhập lại  </a>&nbsp;&nbsp;&nbsp;&nbsp;
                        </td>
                    </tr>        					
                </TABLE>                      
        </fieldset>                      
    	</div>
        <div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
        	<fieldset>
            	<legend><span class="legendtitle"> Trả khuyến mãi</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                	<a class="button3" href="javascript:newform()">
                           <img style="top: -4px;" src="../images/New.png" alt="">Tạo mới   </a>
                </legend>
            	<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
								<TR class="tbheader">
				                    <TH align="center">Mã trả KM</TH>
				                    <TH align="center">Diễn giải</TH>
				                     <TH align="center">Ngày tạo</TH>
				                    <TH align="left"> Người tạo </TH>
				                    <TH align="center"> Ngày sửa </TH>
				                    <TH align="left"> Người sửa </TH>
				                    <TH align="center" >Tác vụ</TH>
				                </TR>
				              <%int m =0; 
				              while(dsduyet.next()){
				            	if(m%2==0){  
				            	  %>  
				              
							<TR class='tbdarkrow'>
							<%}else{ %>
							<TR class='tblightrow'>
							<%} %>
				                    <TD align="center"><%=dsduyet.getString("pk_seq") %></TD>
				                    <TD align="left"><%= dsduyet.getString("diengiai") %></TD>
				                    <TD align="right"><%=dsduyet.getString("ngaytao") %></TD>										                                    
				                    <TD align="right"><%= dsduyet.getString("nguoitao") %></TD>
				                    <TD align="right"><%=dsduyet.getString("ngaysua") %></TD>
				                    <TD align="left"><%= dsduyet.getString("nguoisua") %></TD>
				                       <TD align="center">
				                        <TABLE cellpadding="1" cellspacing="0">
				                          <%if(dsduyet.getString("trangthai").equals("0")){ %>
				                            <TR><TD>
				                                <A href = "../../TrakhuyenmainppUpdateSvl?userId=<%=userId%>&update=<%= dsduyet.getString("pk_seq") %>"><IMG src="../images/Edit20.png" alt="Cap nhat" title="Cập nhật" border="0"></A>
				                            </TD><TD>
				                                <A href = "../../TrakhuyenmainppUpdateSvl?userId=<%=userId%>&delete=<%= dsduyet.getString("pk_seq") %>"><img src="../images/Delete20.png" alt="Xoa" title="Xóa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn có muốn xóa trả khuyến mãi  <% dsduyet.getString("pk_seq") ; %>?')) return false;"></A>
				                            </TD>
				                            <TD> <A href = "../../TrakhuyenmainppUpdateSvl?userId=<%=userId%>&chotphieu=<%= dsduyet.getString("pk_seq")%>"><img src="../images/Chot.png" alt="Chot phieu" title="Chốt phiếu" width="20" height="20" longdesc="Chot phieu" border ="0"></A> </TD>
		                                       </TR>
				                            <%}else { %>
				                             </TR>
				                              <TD>  <A href = "../../TrakhuyenmainppUpdateSvl?userId=<%=userId%>&hienthi=<%= dsduyet.getString("pk_seq") %>"><img src="../images/Display20.png" alt="Hien thi" title="Hiển thị" width="20" height="20" longdesc="Hien thi" border ="0"></A>
				                            </TD>
				                            <%} %>
				                        </TABLE>									
				                    </TD>
				                </TR>
		                     <% m++;
				                }%>														
                                
							<TR class="tbfooter">
								<TD align="center" colspan="10"> |< < 1 to 20 of 100 > >|</TD>
							</TR>
						</TABLE>
            </fieldset>
        </div>
    </div>  
</div>
<%
	try{
		if(obj!=null){
			obj.DBclose();
			obj=null;
		}
		if(dsduyet!=null){
			dsduyet.close();
		}
		session.setAttribute("obj",null);
	}catch(Exception er){
		
	}
%>
</form>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</html>