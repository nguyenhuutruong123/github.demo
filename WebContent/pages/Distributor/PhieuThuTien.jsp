<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.distributor.beans.phieuthutien.*" %>
<%@ page  import = "geso.dms.center.util.*" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>

<% IPhieuthutienList obj = (IPhieuthutienList)session.getAttribute("obj"); %>
<% ResultSet nvgn = obj.getNvgn(); %>
<% ResultSet ptt = obj.getPttList(); %>

<% String query = (String) session.getAttribute("query");  
	  
	int[] quyen = new  int[6];
	quyen = util.Getquyen("PhieuthutienSvl","", userId);
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
		document.forms["pttForm"].action.value = "Tao moi";
	    document.forms["pttForm"].submit();
	 }
	 function nhaplai()
	 {   
		document.forms["pttForm"].action.value = "nhaplai";
	    document.forms["pttForm"].submit();
	 }
	 function clearform()
	 {   
		document.forms["pttForm"].nvgnId.value = "";
	    document.forms["pttForm"].tungay.value = "";
	    document.forms["pttForm"].denngay.value = "";
	    document.forms["pttForm"].sotien.value = "";
	 }
	 
	 function thongbao()
		{
			if(document.getElementById("msg").value != '')
				alert(document.getElementById("msg").value);
		}
	</SCRIPT>

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

<form name="pttForm" method="post" action="../../PhieuthutienSvl"> 
 <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="nppId" value="<%=obj.getNppId()%>" >
<input type="hidden" name="action" value="1" >
<input type="hidden" id="msg" value="<%=obj.getMsg() %>" >
<div id="main" align="left" style="width:99%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:70%; padding:5px; float:left" class="tbnavigation">
        	Quản lý công nợ > Phiếu thu tiền
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        Chào mừng bạn  <%= obj.getNppTen() %> &nbsp;
        </div>
    </div>
  	<div id="cotent" style="width:100%; float:none">
    	<div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
        <fieldset>
            <legend class="legendtitle"> Tiêu chí tìm kiếm  </legend>
                <TABLE width="100%" cellpadding="5px" cellspacing="0px">								                 
                    <TR>
                        <TD class="plainlabel" valign="middle" width="15%">Nhân viên giao nhận  </TD>
                        <TD class="plainlabel" valign="middle">
                        	<select name="nvgnId" id="nvgnId">
							  <option value=""> </option>
							  <% try{
								  if(nvgn != null){
								  	while(nvgn.next()){ 
								    	if(nvgn.getString("nvgnId").equals(obj.getNvgnId())){ %>
								      		<option value='<%=nvgn.getString("nvgnId")%>' selected><%=nvgn.getString("nvgnTen") %></option>
								      	<%}else{ %>
								     		<option value='<%=nvgn.getString("nvgnId")%>'><%=nvgn.getString("nvgnTen") %></option>
								     			
								     	<%}
								    	}
								  }
								 }catch(java.sql.SQLException e){} %>	 
                        	
                        	</select>
                        
                        </TD>                       
                    </TR>            
                    <TR>
                        <TD class="plainlabel" >Từ ngày </TD>
                        <TD class="plainlabel">
                            <input type="text" size="10" class="w8em format-y-m-d divider-dash highlight-days-12" 
                                   id="tungay" name="tungay" value="<%= obj.getTungay() %>" maxlength="10" />
                        </TD>
                    </TR>
                     <TR>
                        <TD class="plainlabel" >Đến ngày </TD>
                        <TD class="plainlabel">
                            <input type="text" size="10" class="w8em format-y-m-d divider-dash highlight-days-12" 
                                   id="denngay" name="denngay" value="<%= obj.getDenngay() %>" maxlength="10" />
                        </TD>
                    </TR>
                    <TR>
                        <TD class="plainlabel" >Số tiền </TD>
                        <TD class="plainlabel"> <input type="text" name="sotien" value="<%= obj.getSotien() %>" /> </TD>                       
                    </TR>   
                    <tr >
                        <td  class="plainlabel" colspan="2">
                            <a class="button" href="javascript:submitform()">
                                <img style="top: -4px;" src="../images/button.png" alt="">Tìm kiếm  </a>&nbsp;&nbsp;&nbsp;&nbsp;
                          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  <a class="button2" href="javascript:nhaplai()">
                                <img style="top: -4px;" src="../images/button.png" alt="">Nhập lại  </a>
                        </td>
                    </tr>        					
                </TABLE>                      
        </fieldset>                      
    	</div>
        <div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
        	<fieldset>
            	<legend><span class="legendtitle"> Phiếu thu </span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                	<% if(quyen[0]!= 0){ %>
                	<a class="button3" href="javascript:newform()">
                           <img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>
                    <%} %>
                </legend>
            	<TABLE width="100%" border="0" cellspacing="1px" cellpadding="4px">
								<TR class="tbheader">
                                	<TH align="center">Số chứng từ </TH>
									<TH align="center">Ngày thu tiền </TH>
									<TH align="center">Diễn giải </TH>											
									<TH align="center" > Nhân viên giao nhận </TH>
									<TH align="center">Số tiền </TH>
									<TH align="center">Trạng thái</TH>
									<TH align="center">Người nhập </TH>								
									<TH align="center">Ngày nhập </TH>
									<TH align="center"> Tác vụ</TH>
								</TR>								
								<%      
								try{
									 int m = 0;
									 if(ptt != null){
                                     	while(ptt.next()){
											if(m%2==0){%>
                                 
                                     			<TR class="tbdarkrow" >   
                                     		<%}else{ %>
                                     			<TR class="tblightrow" >
                                     		<%} %>                            
                                         
                                         	<TD align="center"><%= ptt.getString("pttId") %></TD>                                   
                                         	<TD align="center"><%= ptt.getString("ngaythu") %></TD>
                                         	<TD align="left"><%= ptt.getString("diengiai") %></TD>
                                         	<TD align="left"><%=ptt.getString("nvgnTen") %></TD>
                                         	<TD align="right"><%=ptt.getString("sotien") %></TD>
                                         	
                                         	<%
                                         		String trangthai = "";
                                         		if(ptt.getString("trangthai").equals("0"))
                                         			trangthai = "Chưa chốt";
                                         		else
                                         		{
                                         			if(ptt.getString("trangthai").equals("2"))
                                         				trangthai = "Đã hủy";
                                         			else
                                         			{
                                         				if(ptt.getString("trangthai").equals("1"))
                                         					trangthai = "Đã chốt";
                                         			}
                                         		}%>
                                         
											 <TD align="center"><%= trangthai %></TD>
	                                         <TD align="left"><%=ptt.getString("nguoisua")%></TD>
    	                                     <TD align="center"><%=ptt.getString("ngaysua") %></TD>
        	                                 <TD align="center"> 
            	                             <% if( quyen[3]!= 0 && (ptt.getString("trangthai").equals("1") || ptt.getString("trangthai").equals("2")) ){ %> 
                	                         	<A href = "../../PhieuthutienUpdateSvl?userId=<%=userId%>&display=<%=ptt.getString("pttId")%>"><img src="../images/Display20.png" alt="Hien thi" title="Hiển thị" width="20" height="20" longdesc="Hien thi" border ="0"></A>                                                                                                                       
                    	                     <%}else{ %>
                    	                     	<% if(quyen[2]!= 0){ %>
                        	                 	<A href = "../../PhieuthutienUpdateSvl?userId=<%=userId%>&update=<%=ptt.getString("pttId")%>"><img src="../images/Edit20.png" alt="Cap nhat" title="Cập nhật" width="20" height="20" longdesc="Cap nhat" border ="0"></A>
                            	                <%} %>
                            	                
                            	                <% if(quyen[4]!= 0){ %>
                            	                <A href = "../../PhieuthutienSvl?userId=<%=userId%>&chotphieu=<%=ptt.getString("pttId")%>"><img src="../images/Chot.png" alt="Chot phieu" title="Chốt phiếu" width="20" height="20" longdesc="Chot phieu" border="0" ></A>
                                	            <%} %>
                                	            
                                	            <% if(quyen[1]!= 0){ %>
                                	            <A href = "../../PhieuthutienSvl?userId=<%=userId%>&delete=<%=ptt.getString("pttId")%>"><img src="../images/Delete20.png" alt="Huy phieu" title="Hủy phiếu" width="20" height="20" longdesc="Huy Phieu" border="0" ></A>
                                    	     	<%} %>
                                    	     	
                                    	     <%} %>
                                        	 </TD>                                      
                                     	</TR>
                                		<% m++; }
									 }
									}catch(java.sql.SQLException e){}
                                %>
								<TR class="tbfooter">
									<TD align="center" colspan="9"> |< < 1 to 20 of 100 > >| </TD>
								</TR>
							</TABLE>
            </fieldset>
        </div>
    </div>  
</div>

</form>
</BODY>
</html>
<<script type="text/javascript">
<!--
thongbao();
//-->
</script>
<% 	
	if(nvgn!=null){
		nvgn.close(); nvgn = null;
	}
   if(obj != null){
	obj.DBclose();
	obj = null;
   }
   session.setAttribute("obj",null);
   
%>
<%}%>