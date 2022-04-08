<%@page import="java.sql.SQLException"%>
<%@page import="geso.dms.distributor.beans.dangkykhuyenmaitichluy.IDangkykhuyenmaitichluy"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.distributor.beans.dangkytrungbay.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "geso.dms.center.util.*" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	} else { %>
<% IDangkykhuyenmaitichluy obj = (IDangkykhuyenmaitichluy)session.getAttribute("obj"); %>
<% ResultSet ctkmIds = (ResultSet)obj.getCtkmRs(); %>
<% ResultSet khRs = (ResultSet)obj.getKhList(); %>
<% ResultSet NppRs = (ResultSet)obj.getNppRs(); %>
<% ResultSet nvbhRs = (ResultSet)obj.getNvBanhang(); %>
<% ResultSet vungRs = (ResultSet)obj.getVungRs(); %>
<% ResultSet khuvucRs = (ResultSet)obj.getKhuvucRs(); %>
<% Hashtable<String, Integer> kh_muc = obj.getMucDangky(); %>
<% Hashtable<String, Integer> kh_stt = obj.getSTT(); %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<TITLE>SOHACO - Project</TITLE>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">
    <LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
    <LINK rel="stylesheet" href="../css/main.css" type="text/css">
    
   	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css"/>
  	<script type="text/javascript" src="../scripts/Timepicker/jquery.min.js"></script>
  	<script type="text/javascript" src="../scripts/Timepicker/jquery-ui.min.js"></script>
  	
  	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
	<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
	<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
	<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
	<script type="text/javascript" src="..scripts/jquery-1.js"></script>
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
    <LINK rel="stylesheet" type="text/css" href="../css/style.css" />
    <link media="screen" rel="stylesheet" href="../css/colorbox.css">
    <script src="../scripts/colorBox/jquery.colorbox.js"></script>
   	<script>
	  $(document).ready(function() {
			$("#accordion").accordion({autoHeight: false}); //autoHeight content set false
			$( "#accordion" ).accordion( "option", "icons", { 'header': 'ui-icon-plus', 'headerSelected': 'ui-icon-minus' } );
			$( "#accordion" ).accordion({ active: 1 });
			
			var ac = document.getElementById("action").value;
			if(ac == "upload"){
				document.forms['dktbForm'].submit();
			}
	  });
  	</script>
    <script type="text/javascript">
	    function saveform()
		{  
		    document.forms['dktbForm'].action.value='save';
		    document.forms['dktbForm'].submit();
		}
	    
		function submitform()
		{
			document.forms['dktbForm'].action.value='submit';
		    document.forms['dktbForm'].submit();
		}
		
		function submitform2()
		{
			var cttb = document.getElementById("ctkmId");
			if(cttb.value == "")
			{
				alert('vui lòng chọn chương trình khuyến mãi..');
				return;
			}
			document.forms['dktbForm'].action.value='submit';
		    document.forms['dktbForm'].submit();
		}
		
		function CheckALL()
		{ 
			var khachhang = document.getElementsByName("khIds");
			var khSelected = document.getElementsByName("khIdSelected");
			for(i=0; i < khSelected.length; i++)
			{
				for(j=0; j<khachhang.length; j++)
				{
					if(khSelected[i].value == khachhang[j].value){
			 			if(document.dktbForm.chkAll.checked ==true)
			 			{
			 	  			khachhang[j].checked = true;
						}
			 			else
			 			{
							khachhang[j].checked =false;
						}
					}
			 	}																				
			}
		 }
		function CheckALLNpp()
		{
			var npp = document.getElementsByName("nppIds");
			for(i=0; i<npp.length; i++)
			{
			 	if(document.dktbForm.chkAllNpp.checked ==true)
			 	{
			 		npp[i].checked = true;
				}
			 	else
			 	{
			 		npp[i].checked =false;
				}
			 }
		}
		function nppCheck(chk, id)
		{
			if(chk.checked == true)
				document.getElementById("npp_"+ id).value = id;
			else
				document.getElementById("npp_"+ id).value = "";
		}
		
		function upload()
		{
			document.forms['dktbForm'].action.value='upload';
			document.forms["dktbForm"].setAttribute('enctype', "multipart/form-data", 0);
		    document.forms["dktbForm"].submit();
		  
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

<form name="dktbForm" method="post" action="../../DangkykhuyenmaitichluyUpdateSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<INPUT name="userId" type="hidden" value='<%=userId %>' size="30">
<input type="hidden" name="id" value='<%= obj.getId() %>'>
<input type="hidden" name="action" id = "action" value="<%=obj.getAction()%>">

   <div id="main" style="width:99%; padding-left:2px">
	 <div align="left" id="header" style="width:100%; float:none">
    	  <div style="width:40%; padding:5px; float:left" class="tbnavigation">
    	  <%if(obj.getId().length() ==0) {%>
        	Quản lý khuyến mãi > Tích lũy > Đăng ký khuyến mãi tích lũy > Tạo mới
        	<%} else { %>
        	Quản lý khuyến mãi > Tích lũy > Đăng ký khuyến mãi tích lũy > Cập nhật
        	<%} %>
          </div>
          <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng  <%= userTen %> &nbsp;
         </div>
    </div>
  
  	<div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="tbdarkrow">
    	<A href= "../../DangkykhuyenmaitichluySvl?userId=<%= userId %>" >
        	<img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1px" longdesc="Quay ve" style="border-style:outset"></A>
        	
        	<A href="javascript:saveform()" >
        	<IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border ="1px" style="border-style:outset"></A>
        	
    </div>
  	<div align="left" style="width:100%; float: none"> 
    	<fieldset>
        	<legend class="legendtitle">Báo lỗi nhập liệu </legend>
            <textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold" cols="71" rows="1"  style="width: 100% " readonly="readonly" ><%= obj.getMessage() %></textarea>
           
        </fieldset>
    </div>
    <div align="left" style="width:100%; float:none">
    <fieldset>
    	<legend class="legendtitle"> Đăng ký khuyến mãi tích lũy </legend>
        <div style=" width:100%; float:non; clear:left; font-size:0.7em">
        <TABLE width="100%" cellpadding="5px" cellspacing="0px">
             <TR>
                <TD class="plainlabel"  width="130px">Chương trình </TD>
                <TD class="plainlabel" >
                	<%if(obj.getId().length() == 0){ %>
                    <select name="ctkmId" id="ctkmId"  onchange="submitform()">
                       <option value=''></option>
                            <% if(ctkmIds != null){
					  		try{ while(ctkmIds.next()){ 
		    					if(ctkmIds.getString("pk_seq").equals(obj.getctkmId())){ %>
		      					<option value='<%=ctkmIds.getString("pk_seq")%>' selected><%=ctkmIds.getString("scheme") %></option>
		      				<%}else{ %>
		      				<option value='<%=ctkmIds.getString("pk_seq")%>'><%=ctkmIds.getString("scheme") %></option>
		      				<%}} ctkmIds.close(); }catch(java.sql.SQLException e){} }%>
                     </select>
                     <%} else{
                     	try{ 
                     		while(ctkmIds.next()){ 
		    					if(ctkmIds.getString("pk_seq").equals(obj.getctkmId())){ %>
		      					<input type ="hidden" name = "ctkmId" value='<%=ctkmIds.getString("pk_seq")%>'>
		      					<input type = "text" value ="<%=ctkmIds.getString("scheme") %>">
		      					<%} %>
		      				<%}
                   		} 
                     	catch(Exception ex){}
                   	}%>
                </TD>
            </TR>	
             							
            <TR>
                <TD class="plainlabel">Từ ngày </TD>
                <TD class="plainlabel" >
                    <input type="text" value="<%= obj.getTungay() %>" readonly="readonly" />
                   &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; Đến ngày &nbsp;&nbsp;&nbsp;&nbsp; 
                    <input type="text" value="<%= obj.getDenngay() %>" readonly="readonly" />
                </TD>
            </TR> 
            
            <TR >
            	<TD class="plainlabel">Nhân viên bán hàng </TD>
            	<TD class="plainlabel">
            		<select name="nvbhId" onchange="submitform()" >
            			<option value="" ></option>
            			<%
            				if(nvbhRs != null)
            				{
            					while(nvbhRs.next())
            					{
            						if(nvbhRs.getString("pk_seq").equals(obj.getNvbhIds()))
            						{ %>
            							<option value="<%= nvbhRs.getString("pk_seq") %>" selected="selected" ><%= nvbhRs.getString("TEN") %></option>
            						<% } else { %>
            							<option value="<%= nvbhRs.getString("pk_seq") %>" ><%= nvbhRs.getString("TEN") %></option>
            						<% } 	
            					}
            					nvbhRs.close();
            				}
            			%>
            		</select>
            	</TD>
            </TR>
            <tr class="plainlabel">
		  		<TD class="plainlabel" >Chọn file upload</TD>
		  	  	<td colspan="1"><INPUT type="file" name="filename" size="40" value=''> </td> 
		  	</tr>
		  	<tr class="plainlabel">
		  		<td colspan="2">&nbsp;<a class="button2" href="javascript:upload()">
					<img style="top: -4px;" src="../images/button.png" alt="">Upload</a>							
		  		</td>
		  	</tr>    				
        </TABLE>
        
        <div id="accordion" style="width:100%; height:auto; float:none; font-size:1.0em" align="left">
                     
       		<%-- <h1 style="font-size:1.8em"><a href="#">Lọc Nhà phân phối</a></h1>
			<div style="height:auto">
		        <TABLE width="100%" border="0" cellspacing="1px" cellpadding="3px">
			        <TR class="plainlabel" valign="bottom">
			        	<th >
		           
		  					<fieldset style="width: 30%; float: left;">
		  						<legend>Vùng &nbsp;</legend> 
		  						<select name="vung" id="vungId"  multiple="multiple" onchange="submitform()">
		  							<option value="">Chọn hết</option>
		                			<% if(vungRs != null) {
					                 while(vungRs.next()) 
					                 {
					                   if(obj.getVungIds().indexOf(vungRs.getString("pk_seq")) >= 0 ){ %>
					                     <option value="<%= vungRs.getString("pk_seq") %>" selected style="padding: 2px" ><%= vungRs.getString("ten") %></option>
					                 <%}else { %>
					                 	<option value="<%=vungRs.getString("pk_seq") %>" style="padding: 2px"><%= vungRs.getString("ten") %></option>
					                 <%} }
					                vungRs.close(); 
		                			}%>        	
					            </select>
		           			</fieldset>
		            
		            		<fieldset style="width: 30%; float: left;"> 
								<legend>Khu Vực&nbsp;</legend>
								<select name="khuvuc" multiple="multiple" id="khuvucId"  onchange="submitform()">
									<option value="">Chọn hết</option>
					          		<% if(khuvucRs != null) {
							       	while(khuvucRs.next())
					                   {
					                     if(obj.getKhuvucIds().indexOf(khuvucRs.getString("pk_seq")) >= 0 )
					                     { %>
					                       <option value="<%=khuvucRs.getString("pk_seq") %>" selected style="padding: 2px"><%=khuvucRs.getString("ten") %></option> 
					                   <%}else { %>
					                   	<option value="<%=khuvucRs.getString("pk_seq") %>" style="padding: 2px"><%=khuvucRs.getString("ten") %></option>
					                   <%}}
							       	khuvucRs.close();
							       	}%>
					            </select>
			            	</fieldset>
		               
				  		</th>
					</TR>
		        </TABLE>
	       
        		<hr />
        
		        <div style="overflow: scroll; height: 300px">
		        	<table class="tabledetail" style="width: 100%" cellpadding="0" cellspacing="1">
			        	<Tr class="tbheader">
			        		<td width="10%" style="text-align: center;">Id</td>
			        		<td width="20%" style="text-align: center;">Mã Chi nhánh - Nhà phân phối</td>
			        		<td width="35%" >Tên</td>	        		
			        		<td align="center" >Chọn<!--  <input type="checkbox" name="chkAllNpp" id="chkAllNpp" onchange="CheckALLNpp();" > --> </td>
			        	</Tr>
			        	<% if(NppRs != null)
			        	{ 
			        		while(NppRs.next())
			        		{%>
			        			<%if(obj.getVungIds().length() == 0 || (obj.getVungIds().contains(NppRs.getString("VUNG"))&& obj.getKhuvucIds().length() == 0) || (obj.getKhuvucIds().length() > 0 && obj.getKhuvucIds().contains(NppRs.getString("KHUVUC")))) {%>
			        			<TR>
			        				<td  class='tblightrow'>
			        					<input type="text" value="<%= NppRs.getString("PK_SEQ") %>" style="width: 100%" readonly="readonly" > 
			        				</td>
			        				<td  class='tblightrow'>
			        					<input type="text" value="<%= NppRs.getString("MaFAST") %>" style="width: 100%" readonly="readonly" >  
			        				</td>
			        				<td  class='tblightrow'>
			        					<input type="text" value="<%= NppRs.getString("TEN") %>" style="width: 100%" readonly="readonly" > 
			        				</td>
			        				<td align="center"  class='tblightrow'>
			        					<% if(obj.getNppIds().contains(NppRs.getString("PK_SEQ"))) { %> 
			        						<input type="checkbox" name="nppIds" value="<%= NppRs.getString("PK_SEQ")  %>"  checked="checked" onchange="nppCheck(this, <%=NppRs.getString("PK_SEQ")%>);">
			        						<input type="hidden" name = "nppIdSelecteds" id = "npp_<%=NppRs.getString("PK_SEQ")%>" value="<%= NppRs.getString("PK_SEQ")  %>">
			        					<% } else { %> 
			        						<input type="checkbox" name="nppIds" value="<%=NppRs.getString("PK_SEQ")%>"  onchange="nppCheck(this, <%=NppRs.getString("PK_SEQ")%>);">
			        						<input type="hidden" name = "nppIdSelecteds" id = "npp_<%=NppRs.getString("PK_SEQ")%>" value="">
			        					<%  } %>
			        				</td>
			        			</TR>
			        			<%}else{ %>
			        			<tr style="display: none;">
			        				<td>
			        					<% if(obj.getNppIds().contains(NppRs.getString("PK_SEQ"))) { %> 
			        						<input type="checkbox" name="nppIds" value="<%= NppRs.getString("PK_SEQ")  %>"  checked="checked" >
			        					<% } else { %> 
			        						<input type="checkbox" name="nppIds" value="<%= NppRs.getString("PK_SEQ")  %>"  >
			        					<%  } %>
			        				</td>
			        			</tr>
			        			<%} %>
			        		<%  }
			        		NppRs.close();
			        	} %>
			        </table>
		        </div>&nbsp; 
        	 </div> --%>
	        <h1 style="font-size:1.8em"><a href="#">Khách hàng</a></h1> 
            <div style="height:auto">
        <!-- <a class="button" href="javascript:submitform();">
					<img style="top: -4px;" src="../images/button.png" alt=""> Hiển thị Khách hàng</a> -->
        
        <hr/>
        <table style="width: 100%" cellpadding="0" cellspacing="1">
        	<Tr class="tbheader">
        		<td width="5%" >STT</td>
        		<td width="7%" >Mã KH</td>
        		<td width="27%" >Tên khách hàng</td>
        		<td width="30%">Chi nhánh/ Nhà PP</td>
        		<td width="20%" align="center">Mức đăng ký</td>
        		<td align="center" >Chọn <input type="checkbox" name="chkAll" id="chkAll" onchange="CheckALL();" ></td>
        	</Tr>
        	<% if(khRs != null)
        	{ 
        		int stt = 1;
        		while(khRs.next())
        		{%>
        			
        			<TR class="tblightrow">
        				<td>
        					<input type="text" name = "stt_<%=khRs.getString("PK_SEQ")%>" value="<%= stt%>" style="width: 100%; text-align: center;" readonly="readonly">  
        				</td>
        				<td>
        					<input type="text" value="<%= khRs.getString("SMARTID") %>" style="width: 100%" readonly="readonly" > 
        				</td>
        				<td>
        					<input type="text" value="<%= khRs.getString("TEN") %>" style="width: 100%" readonly="readonly" >  
        				</td>

        				<td>
        					<input type="text" value="<%= khRs.getString("nppTen") %>" style="width: 100%" readonly="readonly" > 
        				</td>
        				<td align="center">
        					<input type="text" name = "mdk_<%=khRs.getString("PK_SEQ")%>" value="<%= kh_muc.get(khRs.getString("PK_SEQ")) != null? kh_muc.get(khRs.getString("PK_SEQ")): ""%>" style="width: 100%; text-align: center;" >  
        				</td>
        				<td align="center" >
        					<input type="checkbox" name="khIds" value="<%= khRs.getString("PK_SEQ")%>"  checked="checked" >
        				</td>
        			</TR>
        		<%
        			stt ++;
        		}
        		khRs.close();
        	} %>
        </table>
        </div>
        </div>
        </div>
	</fieldset>
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
	try{
		if(ctkmIds != null)
			ctkmIds.close();
	}
	catch (SQLException e) {}

%>
<%}%>

    