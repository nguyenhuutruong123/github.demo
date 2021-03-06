<%@page import="org.apache.poi.hssf.record.formula.functions.Value"%>
<%@page import="geso.dms.center.beans.chuyennhanvienbanhang.IChuyennhanvienbanhang"%>
<%@page import="java.util.Calendar"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "geso.dms.center.util.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page import="java.sql.SQLException"%>
<%@ page  import = "geso.dms.center.util.ChuyenNgu" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>

<%  IChuyennhanvienbanhang obj = (IChuyennhanvienbanhang)session.getAttribute("obj"); 
	
	ResultSet rsnppmoi=obj.getRsNppMoi();
	
	ResultSet rsnppcu=obj.getRsNppCu();
	
	ResultSet rsddkd=obj.getRsDdkd();
	
	String chuoinvbhchon=obj.getIdNvbhChon();

	int[] quyen = new  int[6];
	quyen = util.Getquyen("chuyennhanvienbanhangSvl","", userId);
%>
<% String nnId = (String)session.getAttribute("nnId"); %> 
<% if(nnId == null) {
 nnId = "vi"; 
 }
String url = util.getChuyenNguUrl("chuyennhanvienbanhangSvl","",nnId);	
 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js"
	type="text/javascript"></script>
<script type="text/javascript">
	
	$(document).ready(function() {
		$(".days").datepicker({
			changeMonth : true,
			changeYear : true
		});
		$(".button").hover(function() {
			$(".button img").animate({
				top : "-10px"
			}, 200).animate({
				top : "-4px"
			}, 200) // first jump
			.animate({
				top : "-7px"
			}, 100).animate({
				top : "-4px"
			}, 100) // second jump
			.animate({
				top : "-6px"
			}, 100).animate({
				top : "-4px"
			}, 100); // the last jump
		});
	});
	$(document).ready(function() {
		$(".button1").hover(function() {
			$(".button1 img").animate({
				top : "-10px"
			}, 200).animate({
				top : "-4px"
			}, 200) // first jump
			.animate({
				top : "-7px"
			}, 100).animate({
				top : "-4px"
			}, 100) // second jump
			.animate({
				top : "-6px"
			}, 100).animate({
				top : "-4px"
			}, 100); // the last jump
		});
	});
</script>

	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
	
	<style type="text/css">
#nppcu {
	width: 47%;
	height: 400px;
	float: left;

	 border : solid #808080 1px;
	padding: 10px;
	margin: 3px;
}

#nppmoi {
	width: 46%;
	height: 400px;
	float: left;
	top:0px;
	border: solid #808080 1px;
	margin: 3px;
	padding: 10px;
	 
}

#content {
	
	width: 100%	;
	height: 500px;
	overflow: auto;
	font-family: Arial,Helvetica,sans-serif;
	font-size: 9pt;
	
}
#nhappcu {
	width: 300px;
	height: 300px;
}



</style>
    <script type="text/javascript">
        $(document).ready(function(){
            $(".button").hover(function(){
                $(".button img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        }); 
</script>
<link href="../css/select2.css" rel="stylesheet" />
	<script src="../scripts/select2.js"></script>
	<script>
        $(document).ready(function() { $("#nhappcu").select2(); });
        $(document).ready(function() { $("#nhappmoi").select2(); });
    </script>
<SCRIPT language="javascript" type="text/javascript">
	

	
	function saveform()
	{	   
		if(document.forms['kstdForm'].nhappcu.value==""){
			alert("Vui l??ng ch???n nh?? ph??n ph???i c??");
			return;
		}
			
		if(document.forms['kstdForm'].nhappmoi.value==""){
			alert("Vui l??ng ch???n nh?? ph??n ph???i m???i");
			return;
		}
			
		 document.forms['kstdForm'].action.value='save';
	     document.forms['kstdForm'].submit();
	}
	
	function submitform(){
		 document.forms['kstdForm'].action.value='loadnhanvienbanhang';
	     document.forms['kstdForm'].submit();
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

<% Csrf csdr=new Csrf(request,response,true,false,true);%>
<input type="hidden" name="action" value='1'>
<input name="userId" type="hidden" value='<%=userId %>' size="30">
<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:70%; padding:5px; float:left" class="tbnavigation"><%=" "+url %>
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	<%=ChuyenNgu.get("Ch??o m???ng b???n",nnId) %> <%= userTen %> &nbsp;&nbsp;
        </div>
    </div>    
    <div align="left" style="width:100%; float:none; clear:left">
  		<fieldset>
    		<legend class="legendtitle"><%=ChuyenNgu.get("B??o l???i nh???p li???u",nnId) %> </legend>
    		<textarea name="dataerror" style="width: 100%" readonly="readonly" rows="1" readonly="readonly"><%= obj.getMsg() %></textarea>
		         <% obj.setMsg(""); %>
    	</fieldset>
  	</div>
  		<div id="content" >
	  	     <div id="nppcu"  align="left"  >		
	  	  		<%=ChuyenNgu.get("Ch???n nh?? ph??n ph???i c?? :",nnId) %> 
	  	  		<select style="width: 300px" name="nhappcu"  onchange="submitform()" id="nhappcu">
	  	  			<option value=""> </option> 
	  	  		 	<%
	  	  		 	try{
	  	  		 	if(rsnppcu!=null){
	  	  		 	
	  	  		 		while (rsnppcu.next()){
	  	  		 		if(rsnppcu.getString("pk_seq").equals(obj.getIdNppCu())){
	  	  		 		%>
	  	  		 		<option value="<%=rsnppcu.getString("pk_seq") %>" selected="selected"> <%=  rsnppcu.getString("ma")+"-"+ rsnppcu.getString("ten") %></option> 
	  	  		 		<%	
	  	  		 		}else{
	  	  		 		%>
	  	  		 		<option value="<%=rsnppcu.getString("pk_seq") %>" > <%=  rsnppcu.getString("ma")+"-"+ rsnppcu.getString("ten") %></option>
	  	  		 		<%	
	  	  		 		}
	  	  		 		%>
	  	  		 		
	  	  		 		
	  	  		 	<% }}}catch(Exception err){} %>
	  	  		</select>
	  	  		
	  	  		<div> 
	  	  			<table style="width: 100%; margin:5px;border-color:#808080;border:1px">
	  	  			<tr class="tableheader">
	  	  			<th><%=ChuyenNgu.get("M??",nnId) %></th>
	  	  			<th><%=ChuyenNgu.get("T??n",nnId) %></th>
	  	  			<th><%=ChuyenNgu.get("Ch???n",nnId) %></th>
	  	  			</tr>
	  	  			<%if(rsddkd!=null) { 
	  	  			while(rsddkd.next()){
	  	  				%>
	  	  			<tr>
	  	  			<td> <%=rsddkd.getString("pk_seq") %> </td>
	  	  			<td> <%=rsddkd.getString("ten") %> </td>
	  	  			
	  	  			<td>  
	  	  			<%if(chuoinvbhchon.indexOf(rsddkd.getString("pk_seq"))>=0) {%>
	  	  			<input checked="checked" name="nvbhidchon" id="check<%=rsddkd.getString("ten")%>" value="<%=rsddkd.getString("pk_seq")%>" type="checkbox" > 
	  	  			<%}else{ %>
	  	  			<input name="nvbhidchon" id="check<%=rsddkd.getString("ten")%>" type="checkbox" value="<%=rsddkd.getString("pk_seq")%>" >
	  	  			<%} %>
	  	  			 </td>
	  	  			</tr> 
	  	  			<%
	  	  			}
	  	  			} %>
	  	  			</table>
	  	  		</div>
	  		</div  >
	  		   <div id="nppmoi"   align="left"  >		
	  	   		<%=ChuyenNgu.get("Ch???n nh?? ph??n ph???i m???i :",nnId) %> 
	  	  		<select style="width: 300px" name="nhappmoi"  id="nhappmoi">
	  	  			<option value=""> </option> 
	  	  		 	<%
	  	  		 	try{
	  	  		 		if(rsnppmoi!=null){
	  	  		 	
	  	  		 		while (rsnppmoi.next()){
	  	  		 		if(rsnppmoi.getString("pk_seq").equals(obj.getIdNppMoi())){
	  	  		 		%>
	  	  		 		<option value="<%=rsnppmoi.getString("pk_seq") %>" selected="selected"> <%=  rsnppmoi.getString("ma")+"-"+ rsnppmoi.getString("ten") %></option> 
	  	  		 		<%	
	  	  		 		}else{
	  	  		 		%>
	  	  		 		<option value="<%=rsnppmoi.getString("pk_seq") %>" > <%=  rsnppmoi.getString("ma")+"-"+ rsnppmoi.getString("ten") %></option>
	  	  		 		<%	
	  	  		 		}
	  	  		 		%>
	  	  		 		
	  	  		 		
	  	  		 	<% }}}catch(Exception err){} %>
	  	  		</select>
	  	  		<br>
	  	  		
	  	  		<br>
	  	  <%-- 		Ch??? Copy  sang nh?? ph??n ph???i m???i :
	  	  		<%if(obj.getIscopy().equals("1")) { %>
	  	  		<input checked="checked" type="checkbox" name="iscopy" value="1"  >
	  	  		<%}else{ %>
	  	  		<input type="checkbox" name="iscopy" value="1"  >
	  	  		<%} %> --%>
	  	  		
	  	  		<br>
	  	  		<br>
	  	  		 <a  class="button"	href="javascript:saveform();"> <img style="top: -4px; "
											src="../images/button.png" alt=""><%=ChuyenNgu.get("Th???c hi???n",nnId) %>  </a> 	
	  	  			
	  			</div >
  		</div>
  	
</div>
</form>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</html>

<% 	
	try
    {
		if(rsnppmoi != null){ rsnppmoi.close(); rsnppmoi = null ;} 
		if(rsnppcu != null){ rsnppcu.close(); rsnppcu = null ;} 
		if(rsddkd != null){ rsddkd.close(); rsddkd = null ;} 
		
		if(obj != null)
		{
			obj.DbClose();
			obj = null;
		}
		session.setAttribute("obj", null);
	}
	catch (SQLException e) {e.printStackTrace();} %>
<%}%>	
