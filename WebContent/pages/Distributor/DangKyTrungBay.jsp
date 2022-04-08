<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.dms.distributor.beans.dangkytrungbay.*" %>
<%@ page  import = "java.sql.ResultSet" %>

<%@ page  import = "geso.dms.center.util.*" %>
<%@ page  import = "geso.dms.center.util.ChuyenNgu" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/IMV/index.jsp");
	}else{ %>

<% IDangkytrungbayList obj = (IDangkytrungbayList)session.getAttribute("obj"); %>
<% ResultSet dktbList = (ResultSet)obj.getDktbList();
	  
	int[] quyen = new  int[6];
	quyen = util.Getquyen("DangkytrungbaySvl","", userId);%>
<% String nnId = (String)session.getAttribute("nnId"); %> 
<% if(nnId == null) {
	nnId = "vi"; 
	
	}	
String url = util.getChuyenNguUrl("DangkytrungbaySvl","",nnId);	
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
	    if(confirm("Bạn có muốn đăng xuất?"))
	    {
			top.location.href = "home.jsp";
	    }
	    return
	 }
	 function submitform()
	 {   
	    document.forms["dktbForm"].submit();
	 }
	 function newform()
	 {   
		document.forms["dktbForm"].action.value = "Tao moi";
	    document.forms["dktbForm"].submit();
	 }
	 function clearform()
	 {   
	    document.forms["dktbForm"].sophieu.value = "";
	    document.forms["dktbForm"].tungay.value = "";
	    document.forms["dktbForm"].denngay.value = "";
	    document.forms["dktbForm"].submit();
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

<form name="dktbForm" method="post" action="../../DangkytrungbaySvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="nppId" value="<%=obj.getNppId()%>" >
<input type="hidden" name="action" value="1" >

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:50%; padding:5px; float:left" class="tbnavigation">
        	<%= " " + url %>
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	<%=ChuyenNgu.get("Chào mừng bạn",nnId) %>  <%= obj.getNppTen() %> &nbsp;
        </div>
    </div>
  	<div id="cotent" style="width:100%; float:none">
    	<div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
        <fieldset>
            <legend class="legendtitle"> <%=ChuyenNgu.get("Tiêu chí tìm kiếm",nnId) %> </legend>
                <TABLE width="100%" cellpadding="6px" cellspacing="0px">								                 
                    <TR>
                        <TD class="plainlabel" valign="middle" width="15%"><%=ChuyenNgu.get("Số phiếu",nnId) %></TD>
                        <TD class="plainlabel" valign="middle">
                            <input type="text" name="sophieu" value="<%= obj.getScheme() %>">
                        </TD>                        
                    </TR>            
                    <TR>
                        <TD class="plainlabel" ><%=ChuyenNgu.get("Từ ngày",nnId) %>: </TD>
                        <TD class="plainlabel">
                            <input type="text" size="10" class="days" 
                                   id="tungay" name="tungay" value="<%= obj.getTungay() %>" maxlength="10" />
                        </TD>
                    </TR>
                     <TR>
                        <TD class="plainlabel" ><%=ChuyenNgu.get("Đến ngày",nnId) %>: </TD>
                        <TD class="plainlabel">
                            <input type="text" size="10" class="days" 
                                   id="denngay" name="denngay" value="<%= obj.getDenngay() %>" maxlength="10" />
                        </TD>
                    </TR>
                    <tr >
                        <td class="plainlabel" colspan="2">
                            <a class="button" href="javascript:submitform()">
                                <img style="top: -4px;" src="../images/button.png" alt=""><%=ChuyenNgu.get("Tìm kiếm",nnId) %></a>&nbsp;&nbsp;&nbsp;&nbsp;
                            <a class="button2" href="javascript:clearform()">
                                <img style="top: -4px;" src="../images/button.png" alt=""><%=ChuyenNgu.get("Nhập lại",nnId) %></a>&nbsp;&nbsp;&nbsp;&nbsp;
                        </td>
                    </tr>        					
                </TABLE>                      
        </fieldset>                      
    	</div>
        <div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
        	<fieldset>
            	<legend><span class="legendtitle"> <%=ChuyenNgu.get("Đăng ký trưng bày",nnId) %></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            	    <%if(quyen[0]!= 0){ %>
                	<a class="button3" href="javascript:newform()">
                           <img style="top: -4px;" src="../images/New.png" alt=""><%=ChuyenNgu.get("Tạo mới",nnId) %> </a>
                    <%} %>
                </legend>
            	<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
								<TR class="tbheader">
                                	<TH align="center" width="8%"><%=ChuyenNgu.get("ID Đăng Ký",nnId) %></TH>
									<TH align="center" width="10%"><%=ChuyenNgu.get("ID Chương trình",nnId) %></TH>
                                    <TH align="center" width="28%"><%=ChuyenNgu.get("Tên Chương Trình",nnId) %></TH>
                                    <TH align="center" width="8%"><%=ChuyenNgu.get("Ngày đăng ký",nnId) %></TH>			
                                    <TH align="center" width="10%"><%=ChuyenNgu.get("Trạng thái",nnId) %></TH>							
									<TH align="center" width="15%"><%=ChuyenNgu.get("Người tạo",nnId) %> </TH>
									<TH align="center" width="8%"><%=ChuyenNgu.get("Ngày sửa",nnId) %></TH>
									<TH align="center" width="15%"><%=ChuyenNgu.get("Người sửa",nnId) %></TH>
                                    <TH align="center" width="20%"><%=ChuyenNgu.get("Tác vụ",nnId) %></TH>
								</TR>
							<%      
		                       
		                   
		                        int m = 0;
		                        String lightrow = "tblightrow";
								String darkrow = "tbdarkrow";
								if(dktbList!=null)
		                        while (dktbList.next()){
		                            
		                    if (m % 2 != 0) {%>						
										<TR  class= <%=lightrow%> >
									<%} else {%>
										<TR   class= <%= darkrow%> >
									<%}%>		                                                       
		                             <TD align="center"><%= dktbList.getString("dktbId")%></TD>   
		                             <TD align="center"><%= dktbList.getString("cttbId")%></TD>                                  
		                             <TD align="center"><%= dktbList.getString("cttbTen") %></TD>
		                             <TD align="left"><%=dktbList.getString("ngaydangky") %></TD>
		                           <% if(dktbList.getString("trangthai").equals("0")){ %>
		                             	<TD align="center"><%=ChuyenNgu.get("Chưa chốt",nnId) %></TD>
		                             <%}else { if(dktbList.getString("trangthai").equals("1")){ %>
		                             	<TD align="center"><%=ChuyenNgu.get("Đã chốt",nnId) %></TD>
		                             <%}else{ %>
		                             	<TD align="center"><%=ChuyenNgu.get("Đã xoá",nnId) %></TD>
		                             <%}} %>  
		                             <TD align="left"><%=dktbList.getString("nguoitao") %></TD>
		                             <TD align="center"><%=dktbList.getString("ngaysua") %></TD>
		                             <TD align="left"><%=dktbList.getString("nguoisua") %></TD>
		                             <TD align="center"> 
		                             <% if(dktbList.getString("trangthai").equals("0")){ %>
		                             	
		                             	<%if(quyen[2]!= 0){ %>
		                             	<A href = "../../DangkytrungbayUpdateSvl?userId=<%=userId%>&update=<%=dktbList.getString("dktbId")%>"><img src="../images/Edit20.png" alt="Cap nhat" title="Cập nhật" width="20" height="20" longdesc="Cap nhat" border ="0"></A>  
		                             	<%} %>
		                             	
		                             	<%if(quyen[4]!= 0){ %>
		                             	<A href = "../../DangkytrungbayUpdateSvl?userId=<%=userId%>&chot=<%=dktbList.getString("dktbId")%>"><img src="../images/Chot.png" alt="Chot phieu" title="Chốt phiếu" width="20" height="20" longdesc="Chot phieu" border ="0" onclick="if(!confirm('Bạn chắc chắn muốn chốt phiếu đăng ký này ?')) return false;" ></A> 
		                             	<%} %>
		                             	
		                             	 <%if(quyen[1]!= 0){ %>
		                             	<A href = "../../DangkytrungbayUpdateSvl?userId=<%=userId%>&delete=<%=dktbList.getString("dktbId")%>"><img src="../images/Delete20.png" alt="Huy phieu" title="Hủy phiếu" width="20" height="20" longdesc="Huy Phieu" border="0" onclick="if(!confirm('Bạn chắc chắn muốn hủy phiếu đăng ký này ?')) return false;" ></A>
		                             	<%} %>
		                             	
		                             <%}else{ %>
		                             	 <%if(quyen[3]!= 0){ %>
		                             	<A href = "../../DangkytrungbayUpdateSvl?userId=<%=userId%>&display=<%=dktbList.getString("dktbId")%>"><img src="../images/Display20.png" alt="Hien thi" title="Hiển thị" width="20" height="20" longdesc="Hien thi" border ="0"></A>  
			                             <%} %>
			                             	
		                             <%} %>
		                             </TD>                               
		                         </TR>
		                     <% m++; }%>														
                                
							<TR class="tbfooter">
								<TD align="center" colspan="9"> |<   <   1 to 20 of 100   >   >| </TD>
							</TR>
						</TABLE>
            </fieldset>
        </div>
    </div>  
</div>
</form>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</html>
<% 	

	
	if(obj != null){
		obj.DBclose();
		obj = null;
	}
	session.setAttribute("obj",null);
%>
<%}%>
    