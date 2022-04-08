<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.distributor.beans.denghitratb.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "geso.dms.center.util.*" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>
<% IDenghitratb dnttbBean = (IDenghitratb)session.getAttribute("dnttbBean"); %>
<% ResultSet nvbh = (ResultSet)dnttbBean.getNvBanhang(); %>
<% ResultSet cttrungbay = (ResultSet)dnttbBean.getCttrungbay(); %>
<% ResultSet khSelectedList = (ResultSet)dnttbBean.getKhSelectedList(); %>
<% ResultSet khList = (ResultSet)dnttbBean.getKhList(); %>

<% Hashtable<Integer, String> nvbhIds = (Hashtable<Integer, String>)dnttbBean.getNvbhIds(); %>


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
    </script>
    <script type="text/javascript">
	    function saveform()
		{  
			var cttb = document.getElementById("cttbTen");
			if(cttb.value == "")
			{
				alert('Vui lòng chọn chương trình trưng bày...');
				return;
			}
			if(checkDeNghi() == false)
			{
				alert('KHông có khách hàng nào thỏa điều kiện..');
				return;
			}
		    document.forms['dnttbForm'].action.value='save';
		    document.forms['dnttbForm'].submit();
		}

	    function checkDeNghi()
	    {
	    	var denghi = document.getElementsByName("denghi");
	    	for(i = 0; i < denghi.length; i++)
			{
				if(denghi.item(i).value != null)
					return true;
			}
	    	return false;
	    }
	    
	    function Check(str)
		{
			var id = document.getElementById(str);
			var hidden = document.getElementById(str + '.hidden').value;
			if( parseInt(id.value) > parseInt(hidden))
			{
				alert('số xuất đề nghị phải nhỏ hơn số xuất bán đã đăng ký..');
				id.value = "";
				return;
			}
		}
	    
		function submitform()
		{
			document.forms['dnttbForm'].action.value='submit';
		    document.forms['dnttbForm'].submit();
		}
		function submitform2()
		{
			var cttb = document.getElementById("cttbTen");
			if(cttb.value == "")
			{
				alert('Vui lòng chọn chương trình trưng bày...');
				return;
			}
			document.forms['dnttbForm'].action.value='submit';
		    document.forms['dnttbForm'].submit();
		}
	</script>

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

<form name="dnttbForm" method="post" action="../../DenghitratbUpdateSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<INPUT name="userId" type="hidden" value='<%=userId %>' size="30">
<input type="hidden" name="nppId" value='<%= dnttbBean.getNppId() %>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="id" value='<%= dnttbBean.getId() %>'>
<div id="main" style="width:99%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:70%; padding:5px; float:left" class="tbnavigation">
        		Quản lý trưng bày > Đề nghị trưng bày >cập nhật
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng bạn  <%= dnttbBean.getNppTen() %> &nbsp;
        </div>
    </div>
  
  	<div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="tbdarkrow">
    	<A href= "javascript:history.back()" >
        	<img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1px" longdesc="Quay ve" style="border-style:outset"></A>
        <A href="javascript:saveform()" >
        	<IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border ="1px" style="border-style:outset"></A>
    </div>
  	<div align="left" style="width:100%; float: none"> 
    	<fieldset>
        	<legend class="legendtitle">Báo lỗi nhập liệu </legend>
            <textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold" cols="71" rows="1" readonly="readonly"><%= dnttbBean.getMessage() %></textarea>
            <% dnttbBean.setMessage(""); %>
        </fieldset>
    </div>
    <div align="left" style="width:100%; float:none">
    <fieldset>
    	<legend class="legendtitle"> Đề nghị trả trưng bày </legend>
        <div style=" width:100%; float:non; clear:left; font-size:0.7em">
        <TABLE width="100%" cellpadding="5px" cellspacing="0px">
             <TR>
                <TD class="plainlabel"  width="30%">Chương trình</TD>
                <TD class="plainlabel" colspan="2">
                    <select name="cttbTen" id="cttbTen" disabled = "disabled">
                        <option value="">&nbsp;</option>
                            <% if(cttrungbay != null){
					  		try{ while(cttrungbay.next()){ 
		    					if(cttrungbay.getString("cttbId").equals(dnttbBean.getCttbId())){ %>
		      					<option value='<%=cttrungbay.getString("cttbId")%>' selected><%=cttrungbay.getString("scheme")+" - " + cttrungbay.getString("cttbTen") %></option>
		      				<%}else{ %>
		     					<option value='<%=cttrungbay.getString("cttbId")%>'><%=cttrungbay.getString("scheme")+" - " + cttrungbay.getString("cttbTen") %></option>
		     			<%}} cttrungbay.close(); }catch(java.sql.SQLException e){} }%>
                     </select>
                </TD>
            </TR>	
               <TR>
                <TD class="plainlabel">Thời gian tính doanh số : Từ ngày</TD>
                <TD class="plainlabel" colspan="2">
                    <input type="text" size="15" name="batdauds" value="<%=dnttbBean.getTgbdTinhds() %>" disabled="disabled" />
                     &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; Đến ngày  &nbsp;&nbsp;&nbsp;&nbsp;
                    <input type="text" size="15" name="ketthucds" value="<%=dnttbBean.getTgktTinhds() %>" disabled="disabled" />
                </TD>
            </TR> 
            <TR>
                
            </TR> 							
            <TR>
                <TD class="plainlabel">Thời gian trưng bày &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:Từ ngày </TD>
                <TD class="plainlabel" colspan="2">
                    <input type="text" size="15" name="batdau" value="<%= dnttbBean.getThoigianbd() %>" disabled="disabled" />
                   &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; Đến ngày &nbsp;&nbsp;&nbsp;&nbsp; 
                    <input type="text" size="15" name="ketthuc" value="<%= dnttbBean.getThoigiankt() %>" disabled="disabled" />
                </TD>
            </TR> 
            <% if(dnttbBean.getType()==1){%>
            	
            <TR>
                <TD class="plainlabel">Thời gian đăng ký &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:Từ ngày </TD>
                <TD class="plainlabel" colspan="2">
                    <input type="text" size="15" name="batdau" value="<%=dnttbBean.getTgktTinhds() %>" disabled="disabled" />
                    &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;Đến ngày&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                    <input type="text" size="15" name="ketthuc" value="<%= dnttbBean.getThoigianbd() %>" disabled="disabled" />
                </TD>
            </TR> 
           <% } else {%>
            <TR>
                <TD class="plainlabel">Thời gian đăng ký &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:Từ ngày </TD>
                <TD class="plainlabel" colspan="2">
                    <input type="text" size="15" name="batdau" value="<%=dnttbBean.getTgktTinhds() %>" disabled="disabled" />
                    &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;Đến ngày&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                    <input type="text" size="15" name="ketthuc" value="<%=dnttbBean.getThoigiankt()  %>" disabled="disabled" />
                </TD>
            </TR> 
           <%} %>
            <TR>
                
            </TR> 
            <TR>
                <TD class="plainlabel">Số lần thanh toán </TD>
                <TD class="plainlabel" colspan="2">
                    <input type="text" size="15" name="solan" value="<%= dnttbBean.getSolantt() %>" disabled="disabled" />
                </TD>                 
            </TR>     
            <TR>
                <TD class="plainlabel">Số suất phân bổ</TD>
                <TD class="plainlabel" colspan="2">
                    <input type="text" size="15" name="sosuatphanbo" id ="sosuatphanbo" value="<%= dnttbBean.getSosuatphanbo() %>" disabled="disabled" />
                </TD>                 
            </TR>   	
            <TR>
                <TD class="plainlabel">Số suất đã mua</TD>
                <TD class="plainlabel" colspan="2">
                       <input type="text" size="15" name="sosuatdaphanbo" id ="sosuatdaphanbo" value="<%= dnttbBean.getSosuatdaphanbo() %>" disabled="disabled" />
                  </TD>                 
            </TR>        					
        </TABLE>

        </div>
        <hr>
        <div style="width:100%; float:none; clear:left" align="left">
            <table style="width:100%; cellpadding="4px" cellspacing="1px">
                <tr class="plainlabel">
                    <th align="center">Mã NVBH</th>
                    <th align="left">Họ tên</th>
                    <th align="left">Địa chỉ</th>
                    <th align="center">Điện thoại</th>
                    <th align="center">Chọn</th>
                </tr>
                <% if(nvbh != null){
				  		try{ while(nvbh.next()){ %>
	    				 <tr class="tbdarkrow">
		                    <td align="center"><%= nvbh.getString("nvbhId") %></td>
		                    <td align="left"><%= nvbh.getString("nvbhTen") %></td>
		                    <td align="left"><%= nvbh.getString("diachi") %></td>
		                    <td align="center"><%= nvbh.getString("dienthoai") %></td>
		                    <% if(nvbhIds.contains(nvbh.getString("nvbhId"))){ %>
			                    <td align="center">
			                    	<input type="checkbox" name="nvbhIds" value="<%= nvbh.getString("nvbhId") %>" checked></td>
		                   <%}else{ %>
		                   		<td align="center">
			                    	<input type="checkbox" name="nvbhIds" value="<%= nvbh.getString("nvbhId") %>"></td>
		                   <%} %>
		                </tr>	 
	     		<%} nvbh.close(); }catch(java.sql.SQLException e){} }%>      
                <tr class="plainlabel"><td colspan="5"></td></tr>
            </table> 
            <br>
               <a class="button" href="javascript:submitform2()">
                   <img style="top: -4px;" src="../images/button.png" alt="">Hien thi khach hang </a>     
        </div> 
        <hr>
        <div style="width:100%; float:none; clear:left" align="left">
             <table style="width:100%" cellpadding="4px" cellspacing="1px">
             	<tr class="plainlabel">
                	<th align="center">Ma KH</th>
                    <th align="left">Họ tên</th>
                    <th align="left">Địa chỉ</th>
                    <th align="center">Điện thoại</th>
                    <th align="center">Đăng ký</th>
                    <th align="center">Đề nghị</th>
                </tr>
                <% int m=0; %>
                <% if(khSelectedList != null){
				  	try{ while(khSelectedList.next()){ m++; %>
    					 <tr class="tbdarkrow">
		                	<td align="center">
		                		<%= khSelectedList.getString("khId") %>
		                		<input type="hidden" name="khIds" value="<%= khSelectedList.getString("khId") %>" >
		                		<input type="hidden" name="ddkdIds" value="<%= khSelectedList.getString("ddkdId") %>" >
		                	</td>
		                    <td align="left"><%= khSelectedList.getString("khTen") %></td>
		                    <td align="left"><%= khSelectedList.getString("diachi") %></td>
		                    <td align="center"><%= khSelectedList.getString("dienthoai") %></td>
		                    <td align="center">
		                    	<input type="text" name="dangky" id="<%= "dangky" + Integer.toString(m) %>" value="<%= khSelectedList.getString("dangky") %>" style="text-align:right" size="6" readonly></td>
		                    <td align="center">
		                    	<input type="text" name="denghi" id="<%= "denghi" + Integer.toString(m) %>" value="<%= khSelectedList.getString("denghi") %>" style="text-align:right" size="6" onkeyup="Check('<%= "denghi" + Integer.toString(m) %>')">
		                    	<input type="hidden" id="<%= "denghi" + Integer.toString(m) + ".hidden" %>" value="<%= khSelectedList.getString("dangky") %>" >
		                   	</td>
		                </tr>
	     			<%} khSelectedList.close(); }catch(java.sql.SQLException e){} }%>  
	     		<% if(khList != null){
				  	try{ while(khList.next()){ m++; %>
    					 <tr class="tbdarkrow">
		                	<td align="center">
		                		<%= khList.getString("khId") %>
		                		<input type="hidden" name="khIds" value="<%= khList.getString("khId") %>" >
		                		<input type="hidden" name="ddkdIds" value="<%= khList.getString("ddkdId") %>" >
		                	</td>
		                    <td align="left"><%= khList.getString("khTen") %></td>
		                    <td align="left"><%= khList.getString("diachi") %></td>
		                    <td align="center"><%= khList.getString("dienthoai") %></td>
		                    <td align="center">
		                    	<input type="text" name="dangky" id="<%= "dangky" + Integer.toString(m) %>" value="<%= khList.getString("dangky") %>" style="text-align:right" size="6" readonly></td>
		                    <td align="center">
		                    	<input type="text" name="denghi" id="<%= "denghi" + Integer.toString(m) %>" value="<%= khList.getString("denghi") %>" style="text-align:right" size="6" onkeyup="Check('<%= "denghi" + Integer.toString(m) %>')">
		                    	<input type="hidden" id="<%= "denghi" + Integer.toString(m) + ".hidden" %>" value="<%= khList.getString("dangky") %>" >
		                   	</td>
		                </tr>
	     			<%} khList.close(); }catch(java.sql.SQLException e){} }%>              
                <tr class="plainlabel"><td colspan="6">&nbsp;</td></tr>
             </table>
        </div>  
    </fieldset>
    </div>   
</div>
</form>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</html>
    

<% 	


	try{
		if(nvbh != null)
			nvbh.close();
		if(cttrungbay != null)
			cttrungbay.close();
		if(khList != null)
			khList.close();
		if(dnttbBean != null){
			dnttbBean.DBclose();
			dnttbBean = null;
		}
		if(nvbhIds!=null){
			nvbhIds.clear();
		}
	session.setAttribute("dnttbBean",null);
	}
	catch (SQLException e) {}

%>
<%}%>