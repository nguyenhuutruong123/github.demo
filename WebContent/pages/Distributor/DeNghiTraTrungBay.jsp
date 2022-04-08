<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.dms.distributor.beans.denghitratb.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>
<% IDenghitratbList obj = (IDenghitratbList)session.getAttribute("obj"); %>
<% ResultSet rsDenghi=(ResultSet)obj.getRsDenghi(); %>
<% obj.setNextSplittings(); 
	  
	int[] quyen = new  int[6];
	quyen = util.Getquyen("DenghitratbSvl","", userId);
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
	    document.forms["dnttbForm"].submit();
	 }
	 function newform()
	 {   
		document.forms["dnttbForm"].action.value = "Tao moi";
	    document.forms["dnttbForm"].submit();
	 }
	 function clearform()
	 {   
	    document.forms["dnttbForm"].sophieu.value = "";
	    document.forms["dnttbForm"].tungay.value = "";
	    document.forms["dnttbForm"].denngay.value = "";
	    document.forms["dnttbForm"].submit();
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

<form name="dnttbForm" method="post" action="../../DenghitratbSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="nppId" value="<%=obj.getNppId()%>" >
<input type="hidden" name="action" value="1" >

<div id="main" style="width:99%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:70%; padding:5px; float:left" class="tbnavigation">
        	Quản lý trưng bày > Đề nghị trưng bày
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng bạn  <%= obj.getNppTen() %> &nbsp;
        </div>
    </div>
  	<div id="cotent" style="width:100%; float:none">
    	<div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
        <fieldset>
            <legend class="legendtitle">Tiêu chí tìm kiếm </legend>
                <TABLE width="100%" cellpadding="6px" cellspacing="0px">								                 
                    <TR>
                        <TD class="plainlabel" valign="middle" width="15%">Số phiếu </TD>
                        <TD class="plainlabel" valign="middle">
                            <input type="text" name="sophieu" value="<%= obj.getScheme() %>">
                        </TD>                        
                    </TR>            
                    <TR>
                        <TD class="plainlabel" >Từ ngày </TD>
                        <TD class="plainlabel">
                            <input type="text" size="10" class="days" 
                                   id="tungay" name="tungay" value="<%= obj.getTungay() %>" maxlength="10" />
                        </TD>
                    </TR>
                     <TR>
                        <TD class="plainlabel" >Đến ngày</TD>
                        <TD class="plainlabel">
                            <input type="text" size="10" class="days" 
                                   id="denngay" name="denngay" value="<%= obj.getDenngay() %>" maxlength="10" />
                        </TD>
                    </TR>
                    <tr >
                        <td   class="plainlabel" colspan="2">
                            <a class="button" href="javascript:submitform()">
                                <img style="top: -4px;" src="../images/button.png" alt="">Tìm kiếm</a>&nbsp;&nbsp;&nbsp;&nbsp;
                            <a class="button2" href="javascript:clearform()">
                                <img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;
                        </td>
                    </tr>        					
                </TABLE>                      
        </fieldset>                      
    	</div>
        <div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
        	<fieldset>
            	<legend><span class="legendtitle"> Đề nghị trả trưng bày </span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                	<%if(quyen[0]!=0){ %>
                	<a class="button3" href="javascript:newform()">
                           <img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>
                    <%} %>
                </legend>
            	<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
								<TR class="tbheader">
                                	<TH align="center">Số phiếu</TH>
									<TH align="center">Ngày đề nghị</TH>
                                    <TH align="center">CT Trưng bày</TH>	
                                    <TH align="center">Trạng thái</TH>									
									<TH align="center">Người tạo</TH>
									<TH align="center">Ngày sửa</TH>
									<TH align="center">Người sửa</TH>
                                    <TH align="center">Tác vụ</TH>
								</TR>
					<%		
					 
                    int m = 0;
                    
                    	if(rsDenghi != null)
                    		
                    	while (rsDenghi.next()){
                        	if(m%2==0){
                %> 
                  		<TR class='tbdarkrow'>
                  		<% }else{%>
                	  
                	  	<TR class='tblightrow'>
                	  	<%
                  		   }
                        	%>
		                             <TD align="center"><%= rsDenghi.getString("dnttbId") %></TD>                                   
		                             <TD align="center"><%= rsDenghi.getString("ngaydenghi") %></TD>
		                             <TD align="center"><%= rsDenghi.getString("cttbTen") %></TD>
		                             <% if(rsDenghi.getString("trangthai").equals("0")){ %>
		                             	<TD align="center">Chưa duyệt </TD>
		                             <%}else {%>
		                             	<TD align="center">Đã duyệt</TD>
		                             <%}%>                      
		                             <TD align="center"><%= rsDenghi.getString("nguoitao") %></TD>
		                               <TD align="center"><%= rsDenghi.getString("ngaysua") %></TD>
		                               <TD align="center"><%= rsDenghi.getString("nguoisua") %></TD>
		                             <TD align="center"> 
		                             <% if(rsDenghi.getString("trangthai").equals("0")){ %>
		                             	
		                             	<%if(quyen[2]!=0){ %>
		                             	<A href = "../../DenghitratbUpdateSvl?userId=<%=userId%>&update=<%=rsDenghi.getString("dnttbId")%>"><img src="../images/Edit20.png" alt="Cap nhat" title="Cập nhật" width="20" height="20" longdesc="Cap nhat" border ="0"></A>  
		                             	<%} %>
		                             	
		                             	<%if(quyen[1]!=0){ %>
		                             	<A href = "../../DenghitratbSvl?userId=<%=userId%>&delete=<%=rsDenghi.getString("dnttbId")%>"><img src="../images/Delete20.png" alt="Huy phieu" title="Hủy phiếu" width="20" height="20" longdesc="Huy Phieu" border="0" onclick="if(!confirm('Bạn chắc chắn muốn hủy phiếu đăng ký này ?')) return false;" ></A>
		                            	<%} %>
		                             	
		                             <%}else{ %>
		                             	<%if(quyen[3]!=0){ %>
		                             	<A href = "../../DenghitratbUpdateSvl?userId=<%=userId%>&display=<%=rsDenghi.getString("dnttbId")%>"><img src="../images/Display20.png" alt="Hien thi" title="Hiển thị" width="20" height="20" longdesc="Hien thi" border ="0"></A>  
		                             	<%} %>
		                             	
		                             <%} %>
		                             </TD>                               
		                         </TR>
		                     <% m++; }%>														
                                
						 <tfoot>
				 <tr class="tbfooter" > 
											 <TD align="center" valign="middle" colspan="13" class="tbfooter">
											 	<%if(obj.getNxtApprSplitting() >1) {%>
													<img alt="Trang Dau" src="../images/first.gif" style="cursor: pointer;" onclick="View('ctkmForm', 1, 'view')"> &nbsp;
												<%}else {%>
													<img alt="Trang Dau" src="../images/first.gif" > &nbsp;
													<%} %>
												<% if(obj.getNxtApprSplitting() > 1){ %>
													<img alt="Trang Truoc" src="../images/prev.gif" style="cursor: pointer;" onclick="View('ctkmForm', eval(ctkmForm.nxtApprSplitting.value) -1, 'view')"> &nbsp;
												<%}else{ %>
													<img alt="Trang Truoc" src="../images/prev_d.gif" > &nbsp;
												<%} %>
												
												<%
													int[] listPage = obj.getNextSplittings();
													for(int i = 0; i < listPage.length; i++){
												%>
												
												<% 
												
												System.out.println("Current page:" + obj.getNxtApprSplitting());
												System.out.println("List page:" + listPage[i]);
												
												if(listPage[i] == obj.getNxtApprSplitting()){ %>
												
													<a  style="color:white;"><%= listPage[i] %>/ <%=obj.getTheLastSplitting() %></a>
												<%}else{ %>
													<a href="javascript:View('ctkmForm', <%= listPage[i] %>, 'view')"><%= listPage[i] %></a>
												<%} %>
													<input type="hidden" name="list" value="<%= listPage[i] %>" />  &nbsp;
												<%} %>
												
												<% if(obj.getNxtApprSplitting() < obj.getTheLastSplitting()){ %>
													&nbsp; <img alt="Trang Tiep" src="../images/next.gif" style="cursor: pointer;" onclick="View('ctkmForm', eval(ctkmForm.nxtApprSplitting.value) +1, 'view')"> &nbsp;
												<%}else{ %>
													&nbsp; <img alt="Trang Tiep" src="../images/next_d.gif" > &nbsp;
												<%} %>
												<%if(obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {%>
											   		<img alt="Trang Cuoi" src="../images/last.gif" > &nbsp;
										   		<%}else{ %>
										   			<img alt="Trang Cuoi" src="../images/last.gif" style="cursor: pointer;" onclick="View('ctkmForm', -1, 'view')"> &nbsp;
										   		<%} %>
											</TD>
										 </tr> </tfoot> 
						</TABLE>
            </fieldset>
        </div>
    </div>  
</div>
</form>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</html>

<% 	
	if(rsDenghi != null){ rsDenghi.close(); rsDenghi = null;}

	if(obj != null){
		obj.DBclose();
		obj = null;
	}
	session.setAttribute("obj",null);

%>
<%}%>
    