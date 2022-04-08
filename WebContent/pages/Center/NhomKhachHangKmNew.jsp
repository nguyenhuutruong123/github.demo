<%@page import="geso.dms.center.beans.nhomkhachhangkm.INhomkhachhangkm"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.beans.nhanhang.INhanhang" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>

<% 

INhomkhachhangkm obj = (INhomkhachhangkm)session.getAttribute("nkhKmBean");
ResultSet kenhRs=(ResultSet) obj.getKenhRs();
ResultSet vungRs=(ResultSet) obj.getVungRs();
ResultSet khuvucRs=(ResultSet) obj.getKhuvucRs();
ResultSet nppRs=(ResultSet) obj.getNppRs();
ResultSet ddkdRs=(ResultSet) obj.getDdkdRs();
ResultSet khRs=(ResultSet) obj.getKhRs();

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">
    <LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
    <LINK rel="stylesheet" href="../css/main.css" type="text/css">
    
  	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="../scripts/Timepicker/jquery.min.js"></script>
  	<script type="text/javascript" src="../scripts/Timepicker/jquery-ui.min.js"></script>
  	
  	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
	<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
	<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(document).ready(function() {		
				$( ".days" ).datepicker({			    
						changeMonth: true,
						changeYear: true				
				});            
	        }); 		
			
    </script>
<script>
	  $(document).ready(function() {
			$("#accordion").accordion({autoHeight: false}); //autoHeight content set false
			$( "#accordion" ).accordion( "option", "icons", { 'header': 'ui-icon-plus', 'headerSelected': 'ui-icon-minus' } );
			$( "#accordion" ).accordion({ active: <%= obj.getActive() %> });
	  });
  </script>
<SCRIPT language="JavaScript" type="text/javascript">
function submitform()
{
    document.forms["KHForm"].submit();
}

function submitform()
{  
	var active = $( "#accordion" ).accordion( "option", "active" );
	document.forms["KHForm"].active.value = active;
    document.forms["KHForm"].submit();
}
function search()
{
	var active = $( "#accordion" ).accordion( "option", "active" );
	document.forms["KHForm"].active.value = active;
	document.forms["KHForm"].action.value = "seach";
	document.forms["KHForm"].load.value = "1";
	document.forms["KHForm"].submit();
}
function save()
{
	document.forms["KHForm"].action.value = "save";
	document.forms["KHForm"].submit();
}

function upload()
{
	if(document.forms["KHForm"].filename.value==""){
		   
		   document.forms["KHForm"].dataerror.value="Chưa tìm file upload lên. Vui lòng chọn file cần upload.";
	   }else{
		 document.forms["KHForm"].setAttribute('enctype', "multipart/form-data", 0);
		 document.forms["KHForm"].submit();	
	   }
}


function searchDdkd()
{
	document.forms["KHForm"].action.value = "searchDdkd";
	document.forms["KHForm"].submit();
}
function searchKh()
{
	document.forms["KHForm"].action.value = "searchKh";
	document.forms["KHForm"].submit();
}

function sellectAll(cbId1,cbId2 )
{
	 var chkAll_Lct = document.getElementById(cbId1);
	 var loaiCtIds = document.getElementsByName(cbId2);
	 

	 
	 if(chkAll_Lct.checked)
	 {
		 for(var i = 0; i < loaiCtIds.length; i++)
		 {
			 loaiCtIds.item(i).checked = true;
		 }
	 }
	 else
	 {
		 for(var i = 0; i < loaiCtIds.length; i++)
		 {
			 loaiCtIds.item(i).checked = false;
		 }
	 }
}

</SCRIPT>



<style type="text/css">
#tab tr td input{
cursor:default;
background-repeat: no-repeat;
background: none;
}
#tab tr:HOVER{
cursor:pointer;
background: #E2F0D9;
}
</style>

</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="KHForm" method="post" action="../../NhomkhachhangkmUpdateSvl" >
<INPUT name="userId" type="hidden" value='<%=userId %>' size="30">
<input type="hidden" name="load" value='0'>
<input type="hidden" name="action" value='0'>
<input type="hidden" name="active" value='<%= obj.getActive() %>'>
<input type="hidden" name="load" value='0'>
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
		
		<TABLE width="100%" cellpadding="0" cellspacing="1">
			
				<TR>
					<TD align="left">
					  <table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
						   <TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý khuyến mãi > Khai báo > Nhóm khách hàng khuyến mại > Tạo mới</TD>
   
						   <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %>&nbsp;  </TD>
						  </tr>
 					  </table>
					</TD>
				</TR>
		</TABLE>		
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR ><TD >
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR ><TD >
						<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
								<TR class = "tbdarkrow">
									<TD width="30" align="left"><A href="../../nhomkhachhangkmSvl?userId=<%=userId %>" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
								    <TD width="2" align="left" ></TD>
								    <TD width="30" align="left" ><A href="javascript: save()" ><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A></TD>
									<TD >&nbsp; </TD>						
								</TR>
						</TABLE>
				</TD></TR>
			</TABLE>
				</TD></TR>
		</TABLE>
					
		<TABLE width = 100% cellpadding = "4" cellspacing = "0" border = "0">
		  	<tr>
				<TD align="left" colspan="4" class="legendtitle">
					<FIELDSET>
					<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>
				
	    				<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold" style="width: 100%" readonly="readonly" rows="1"><%=obj.getMsg()%></textarea>
											</FIELDSET>
				   </TD>
			</tr>			
			<TR>
				<TD width="100%" align="left" >
					<FIELDSET>
						<LEGEND class="legendtitle">&nbsp;Nhóm Khách Hàng</LEGEND>

						<TABLE  width="100%" cellpadding="6" cellspacing="0">
							<TR>
								<TD width="20%" class="plainlabel">Tên nhóm <FONT class="erroralert">*</FONT></TD>
								<TD width="40%" class="plainlabel"><INPUT name="ten" type="text" style="width:400px" value='<%=obj.getTen() %>'></TD>
							</TR>
							<TR>
								<TD width="20%" class="plainlabel">Diễn giải <FONT class="erroralert">*</FONT></TD>
								<TD width="80%" class="plainlabel"><INPUT name="diengiai" style="width:400px" type="text" size="40" value='<%=obj.getDiengiai() %>'></TD>
							</TR>
							
							
							<TR>
								 <td  class="plainlabel">
							  	  	<INPUT type="file" name="filename" size="40" value=''> 
							  	  </td>
							  	  <td class="plainlabel">
							  	  	<a class="button2" href="javascript:upload()"><img style="top: -4px;" src="../images/button.png" alt="">Upload</a>
							  	  </td> 
						  	 </TR>
							
							<TR class="plainlabel">
								<% if(obj.getTrangthai().trim().equals("1")) {%>
								<TD  >
								  Hoạt động 
								 </TD>
								  <TD >
									<input name="trangthai" type="checkbox" value="1" checked >
								  </TD>
								  <%} else { %>
								 <TD >
								   Hoạt động </TD>
								  <TD>
									<input name="trangthai" type="checkbox" value="1">
								  </TD>
								 <%} %>
							</TR>

						</TABLE>   
                    </FIELDSET>
					
                <div id="accordion" style="width:100%; height:auto; float:none; font-size:1.0em" align="left">
            	<h1 style="font-size:0.8em"><a href="#">Chi nhánh / NPP</a></h1>
            	<div style="height:auto">
                     <TABLE width="100%"  id="tab" border="0" cellspacing="1px" cellpadding="3px">
                <TR class="plainlabel" valign="bottom">
                <th colspan="3">
                   <fieldset style="width: 30%; float: left;">
                    <legend> Kênh bán hàng </legend>
                            <select name="kenhId" id="kenhId" multiple="multiple" onchange="AjaxNpp();"  >
					        	<option value=""></option>
		                        <% if(kenhRs != null) {
		                         while(kenhRs.next()) 
		                         {
		                           if(obj.getKenhId().indexOf(kenhRs.getString("pk_seq")) >= 0 ){ %>
		                             <option value="<%= kenhRs.getString("pk_seq") %>" selected style="padding: 2px" ><%= kenhRs.getString("ten") %></option>
		                         <%}else { %>
		                         	<option value="<%= kenhRs.getString("pk_seq") %>" style="padding: 2px"><%= kenhRs.getString("ten") %></option>
		                         <%} }}%>        	
		                  </select>
		             </fieldset>
		             
			       <fieldset style="width: 30%; float: left;">
			       <legend>Vùng &nbsp;</legend> 
			       <select name="vungId" id="vungId"  multiple="multiple" onchange="AjaxNpp()">
			       <option value="">Chọn hết</option>
                        <% if(vungRs != null) {
                         while(vungRs.next()) 
                         {
                           if(obj.getVungId().indexOf(vungRs.getString("pk_seq")) >= 0 ){ %>
                             <option value="<%= vungRs.getString("pk_seq") %>" selected style="padding: 2px" ><%= vungRs.getString("ten") %></option>
                         <%}else { %>
                         	<option value="<%=vungRs.getString("pk_seq") %>" style="padding: 2px"><%= vungRs.getString("ten") %></option>
                         <%} }}%>        	
                    </select>
                    </fieldset>
                    
                    <fieldset style="width: 30%; float: left;"> 
					<legend>Khu Vực&nbsp;</legend>
					<select name="khuvucId" multiple="multiple" id="khuvucId"  onchange="AjaxNpp()">
					<option value="">Chọn hết</option>
			            <% if(khuvucRs != null) {
			            	while(khuvucRs.next())
	                          {
	                            if(obj.getKhuvucId().indexOf(khuvucRs.getString("pk_seq")) >= 0 )
	                            { %>
	                              <option value="<%=khuvucRs.getString("pk_seq") %>" selected style="padding: 2px"><%=khuvucRs.getString("ten") %></option> 
	                          <%}else { %>
	                          	<option value="<%=khuvucRs.getString("pk_seq") %>" style="padding: 2px"><%=khuvucRs.getString("ten") %></option>
	                          <%}}}%>
                    </select>
                    </fieldset>
			   </th>
				</TR>
                <tr class="plainlabel" style="padding: 5px">
                	<th colspan="3">
                		<a class="button" href="javascript:search();">
        					<img style="top: -4px;" src="../images/button.png" alt=""> Hiển thị Npp theo điều kiện</a>
                	</th>
                </tr>
					</TABLE>
					
					
					<TABLE  id="tab"  width="100%" border="0" cellspacing="1px" cellpadding="3px">
                    <TR class="tbheader">
                        <TH align="left" width="10%">Mã</TH>
                        <TH align="left" width="50%"> Tên </TH>
                        <TH align="center" width="10%"> Chọn tất cả <input type ="checkbox" id ="cbNpp" onClick="sellectAll('cbNpp','nppId')"></TH>
                    </TR>
					<%
					int k=0;
                    if(nppRs != null)
                    {
                    	while(nppRs.next())
                    	{
                    		if(k % 2 == 0)
                    		{
                    			%>
                    			<TR class='tbdarkrow'>
	                    	<%}else{ %> 
	                    		 <TR class='tblightrow'>
	                    	<% } %>
	                    	<TD align="center"><%= nppRs.getString("ma") %></TD>
		                    <TD align="left"><%=nppRs.getString("ten") %></TD>
		                    <% if(obj.getNppId().indexOf(nppRs.getString("pk_seq")) >=0) {%>
		                    	<TD align="center"><input type ="checkbox" name ="nppId" value ="<%=nppRs.getString("pk_seq")%>" checked="checked" style="text-align:left;" ></TD>
		                    <%} else {%>
		                      	<TD align="center"><input type ="checkbox" name ="nppId" value ="<%=nppRs.getString("pk_seq")%>" style="text-align:left;"></TD>
		                  	<%} k++; } nppRs.close(); }
					%>
                    <TR>
                        <TD align="center" colspan="10" class="plainlabel">
                        	<a class="button" href="javascript:search();">
        						<img style="top: -4px;" src="../images/button.png" alt=""> Hiển thị ĐDKD theo điều kiện
        					</a>
                        </TD>
                    </TR>
                    </TABLE>
					</div>
                    
  				<h1 style="font-size:0.8em"><a href="#">DDKD</a></h1>
            		<div style="height:auto">
                    <TABLE width="100%"  id="tab" border="0" cellspacing="1px" cellpadding="3px">
                    <TR class="tbheader">
                        <TH align="center" width="15%">Mã ĐDKD</TH>
                        <TH align="left" width="40%"> Tên DDKD </TH>
                        <TH align="left" width="35%"> Chi nhánh / NPP </TH>
                        <TH align="center" width="10%"> Chọn tất cả <input type ="checkbox" id ="cbDdkd" onClick="sellectAll('cbDdkd','ddkdId')" ></TH>
                    </TR>
					<%
					 k=0;
                    if(ddkdRs != null)
                    {
                    	while(ddkdRs.next())
                    	{
                    		if(k % 2 == 0)
                    		{
                    			%>
                    			<TR class='tbdarkrow'>
	                    	<%}else{ %> 
	                    		 <TR class='tblightrow'>
	                    	<% } %>
	                    	<TD align="left"><%= ddkdRs.getString("ma") %></TD>
		                    <TD align="left"><%=ddkdRs.getString("ten") %></TD>
		                    <TD align="left"><%=ddkdRs.getString("nppTen") %></TD>
		                    <% if(obj.getDdkdId().indexOf(ddkdRs.getString("pk_seq")) >=0) {%>
		                    	<TD align="center"><input type ="checkbox" name ="ddkdId" value ="<%=ddkdRs.getString("pk_seq")%>" checked="checked" style="text-align:left;" ></TD>
		                    <%} else {%>
		                      	<TD align="center"><input type ="checkbox" name ="ddkdId" value ="<%=ddkdRs.getString("pk_seq")%>" style="text-align:left;"></TD>
		                  	<%} k++; } ddkdRs.close(); }
					%>
                    <TR>
                        <TD align="center" colspan="10" class="plainlabel">
                        	<a class="button" href="javascript:search();"> Hiển thị KH theo điều kiện
        						<img style="top: -4px;" src="../images/button.png" alt="">
        					</a>
                        </TD>
                    </TR>
                    </TABLE>
                    </div>
                    
                      <h1 style="font-size:0.8em"><a href="#">Khách hàng</a></h1>
         			   <div style="height:auto">
                    
                    <TABLE width="100%"  id="tab" border="0" cellspacing="1px" cellpadding="3px">
                    <TR class="tbheader">
                        <TH align="center" width="15%">Mã Khách hàng</TH>
                        <TH align="left" width="40%">Khách hàng</TH>
                        <TH align="left" width="35%"> Chi nhánh / NPP </TH>
                        <TH align="center" width="10%"> Chọn tất cả <input type ="checkbox" id ="cbKh" onClick="sellectAll('cbKh','khId')" ></TH>
                    </TR>
					<%
					 k=0;
                    if(khRs != null)
                    {
                    	while(khRs.next())
                    	{
                    		if(k % 2 == 0)
                    		{
                    			%>
                    			<TR class='tbdarkrow'>
	                    	<%}else{ %> 
	                    		 <TR class='tblightrow'>
	                    	<% } %>
	                    	<TD align="left"><%= khRs.getString("khMa") %></TD>
		                    <TD align="left"><%=khRs.getString("ten") %></TD>
		                    <TD align="left"><%=khRs.getString("nppTen") %></TD>
		                    <% if(obj.getKhId().indexOf(khRs.getString("pk_seq")) >=0) {%>
		                    	<TD align="center"><input type ="checkbox" name ="khId" value ="<%=khRs.getString("pk_seq")%>" checked="checked" style="text-align:left;" ></TD>
		                    <%} else {%>
		                      	<TD align="center"><input type ="checkbox" name ="khId" value ="<%=khRs.getString("pk_seq")%>" style="text-align:left;"></TD>
		                  	<%} k++; } khRs.close(); }
					%>
                    </TABLE>
                    </div>
                    </div>
              </TABLE>
              
              </TD>
              
              </TR>
              
              </TABLE>
              
              </form>
              </BODY>
              </HTML>
<%}%>