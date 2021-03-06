<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.distributor.beans.phieuxuatkho.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>
<% IPhieuxuatkho pxkBean = (IPhieuxuatkho)session.getAttribute("pxkBean"); %>
<% ResultSet nvbh = (ResultSet)pxkBean.getNvBanhang(); %>
<% ResultSet nvgn = (ResultSet)pxkBean.getNhanvienGN(); %>
<% ResultSet donhangList = (ResultSet)pxkBean.getDonhangList(); %>

<% Hashtable<Integer, String> donhangIds = (Hashtable<Integer, String>)pxkBean.getDonhangIds(); %>

<% String userId = (String) session.getAttribute("userId");  %>
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
		function CheckAll()
		{
			var selectAll = document.getElementById("selectAll");
			var chon = document.getElementsByName("donhangList");
			if(selectAll.checked)
			{
				for(i = 0; i < chon.length; i++)
					chon.item(i).checked = true;
			}
			else
			{
				for(i = 0; i < chon.length; i++)
					chon.item(i).checked = false;
			}
		}
		
		function UnCheckedAll()
		{
			var selectAll = document.getElementById("selectAll");
			selectAll.checked = false;
		}
		
		function submitform()
		{   
		    document.forms['pxkForm'].action.value='submitForm';
		    document.forms['pxkForm'].submit();
		}
		
		function saveform()
		{	
			var nvgn = document.getElementById("nvgnList");
			if(nvgn.value == "")
			{
				alert('B???n ph???i ch???n nh??n vi??n giao nh???n...');
				return;
			}
			if(checkDonhangList() == false)
			{
				alert('Xin l???i , kh??ng c?? ????n h??ng...');
				return;
			}
			var ngaykhoaso = document.getElementById("ngaykhoaso").value;
			 var tungay = document.getElementById("ngaylap").value;
			// alert(ngaykhoaso +' '+ tungay);
			 if(ngaykhoaso.length >0 && tungay.length >0 ){
			 var ngay1 =Date.parse(ngaykhoaso);
			 var ngay2 = Date.parse(tungay);
			 	if(ngay1 >=  ngay2)
				{
					alert('L???i...B???n ???? kh??a s??? ?????n ng??y '+ngaykhoaso  +' r???i...');
					return;
				}
			 }
			document.forms['pxkForm'].action.value='save';
		    document.forms['pxkForm'].submit();
		}
		
		function checkDonhangList()
		{
			if(document.getElementsByName("donhangList") == null)
				return false;
			var dhList = document.getElementsByName("donhangList");
			for(k=0; k < dhList.length; k++)
			{
				if(dhList.item(k).checked)
					return true;
			}
			return false;
		}
	</script>
    

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

<form name="pxkForm" method="post" action="../../PhieuxuatkhoUpdateSvl"> 
 <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<INPUT name="userId" type="hidden" value='<%=userId %>' size="30">
<INPUT name="nppId" type="hidden" value='<%=pxkBean.getNppId() %>' size="30">
<input type="hidden" name="ngaykhoaso" id = "ngaykhoaso" value='<%=pxkBean.getngaykhoaso() %>'>
<input type="hidden" name="action" value='1'>
<div id="main" style="width:99%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:70%; padding:5px; float:left" class="tbnavigation">
        	X??? l?? ????n h??ng > Phi???u xu???t kho > t???o m???i
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Ch??o m???ng b???n   <%= pxkBean.getNppTen() %> 
        </div>
    </div>
  
  	<div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="tbdarkrow">
    	<A href= "javascript:history.back()" >
        	<img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1px" longdesc="Quay ve" style="border-style:outset"></A>
        <A href="javascript:saveform()" >
        	<IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border ="1px" style="border-style:outset"></A>
    </div>
    
  	<div align="left" style="width:100%; float:none; clear:left">
  		<fieldset>
    		<legend class="legendtitle">B??o l???i nh???p li???u</legend>
    		<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold" style="width:100%" rows="1" readonly="readonly"><%= pxkBean.getMessage() %></textarea>
		         <% pxkBean.setMessage(""); %>
    	</fieldset>
  	</div>
  	
    <div align="left" style="width:100%; float:none; clear:left">
    <fieldset>
    	<legend class="legendtitle"> Phi???u xu???t kho </legend>
        	
        	<div style="float:none; width:100%" align="left">
            	<table width="100%" cellspacing="0" cellpadding="6px">
                    <tr>
                    	<td class="plainlabel" width="20%" valign="middle">Ng??y l???p phi???u</td>
                    	<td class="plainlabel">
                            <input type="text" size="11" class="w8em format-y-m-d divider-dash highlight-days-12" 
                                    id="ngaylap" name="ngaylap" value="<%= pxkBean.getNgaylap() %>" maxlength="10" readonly />
                    	</td> 
                    	<td class="plainlabel">&nbsp;</td>                   
                    </tr>       
                    <tr>
           				<td class="plainlabel">Nh??n vi??n giao nh???n <FONT class="erroralert"> *</FONT></td> 
                        <td class="plainlabel">
                            <SELECT name="nvgnTen" id="nvgnList" onchange="submitform()">
					 			 <option value="">&nbsp;</option>
								  <% if(nvgn != null){
									  try{ while(nvgn.next()){ 
						    			if(nvgn.getString("nvgnId").equals(pxkBean.getNvgnId())){ %>
						      				<option value='<%=nvgn.getString("nvgnId")%>' selected><%=nvgn.getString("nvgnTen") %></option>
						      			<%}else{ %>
						     				<option value='<%=nvgn.getString("nvgnId")%>'><%=nvgn.getString("nvgnTen") %></option>
						     	<%}}}catch(java.sql.SQLException e){} }%>	 
                              </SELECT>
                        </td>
                        <td class="plainlabel">&nbsp;</td>
                    </tr>
                    <tr>
           				<td class="plainlabel">Nh??n vi??n b??n h??ng<FONT class="erroralert"> *</FONT></td> 
                        <td class="plainlabel">
                            <select name="nvbhTen" id="nvbhTen" onchange="submitform()">
                            	<option value="">&nbsp;</option>
                                <% if(nvbh != null){
									  try{ while(nvbh.next()){ 
						    			if(nvbh.getString("nvbhId").equals(pxkBean.getNvbhId())){ %>
						      				<option value='<%=nvbh.getString("nvbhId")%>' selected><%=nvbh.getString("nvbhTen") %></option>
						      			<%}else{ %>
						     				<option value='<%=nvbh.getString("nvbhId")%>'><%=nvbh.getString("nvbhTen") %></option>
						     	<%}}}catch(java.sql.SQLException e){} }%>
                            </select>
                        </td>
                        <td class="plainlabel">&nbsp;</td>
                    </tr>
                 </table>
                 <hr>
                 <% if(donhangList != null){ %>
                 <table width="100%" cellpadding="4px" cellspacing="1px">
                 	<tr>
                    	<th class="tbheader" align="center">M?? ????n h??ng</th>
                        <th class="tbheader" align="center">Ng??y nh???p</th>
                        <th class="tbheader" align="center">M?? kh??ch h??ng</th>
                        <th class="tbheader" align="left">Ten khach hang</th>
                        <th class="tbheader" valign="middle" width="10%" align="center">Chon 
                        	<input type="checkbox" name="selectAll" id="selectAll" onChange="CheckAll()"></th>
                    </tr>
                    
                    <% try{ while(donhangList.next()){ %>
		    			<tr class="tbdarkrow">
	                    	<td align="center"><%= donhangList.getString("dhId") %></td>
	                        <td align="center"><%= donhangList.getDate("ngaynhap").toString() %></td>
	                        <td align="center"><%= donhangList.getString("khId") %></td>
	                        <td><%= donhangList.getString("khTen") %></td>
	                        <% if(donhangIds.contains(donhangList.getString("dhId"))){ %>
	                        	<td align="center"><input type="checkbox" name="donhangList" value="<%= donhangList.getString("dhId") %>" onChange="UnCheckedAll()" checked></td>
	                        <%}else{ %>
	                        	<td align="center"><input type="checkbox" name="donhangList" value="<%= donhangList.getString("dhId") %>" onChange="UnCheckedAll()"></td>
	                        <%} %>
                    	</tr>
	     			<% }}catch(java.sql.SQLException e){}%>
	     			
                    <tr class="tbfooter"><td colspan="5">&nbsp;</td></tr>
                 </table>
                 <%} %>
            </div>
            <div align="left" style="width:100%; float:none; clear:none; display:none" id="sanphamList">              
        </div>      
    </fieldset>	
    </div>
</div>
</form>
</BODY>
</html>