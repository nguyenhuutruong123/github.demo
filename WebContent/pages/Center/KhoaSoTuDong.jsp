<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.beans.khoasongay.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page import="geso.dms.center.util.*"%>
<%@ page  import = "geso.dms.center.util.ChuyenNgu" %>

<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  %>
<%	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ 
		int[] quyen = new  int[6];
		quyen = util.Getquyen("KhoasotudongSvl", "",userId);
	

%>
<% IKhoasotudong kstdBean = (IKhoasotudong)session.getAttribute("obj"); %>
<% ResultSet vung = (ResultSet)kstdBean.getVungRs(); %>
<% ResultSet khuvuc = (ResultSet)kstdBean.getKhuvucRs(); %>
<% ResultSet nhapp = (ResultSet)kstdBean.getNppRs(); %>

<% Hashtable<Integer, String> vungIds = (Hashtable<Integer, String>)kstdBean.getVungIds(); %>
<% Hashtable<Integer, String> kvIds = (Hashtable<Integer, String>)kstdBean.getKvIds(); %>
<% Hashtable<Integer, String> nppIds = (Hashtable<Integer, String>)kstdBean.getNppIds(); %>

<% String nnId = (String)session.getAttribute("nnId"); %> 
<% if(nnId == null) {
	nnId = "vi"; 
	}	
String url = util.getChuyenNguUrl("KhoasotudongSvl","",nnId);	
	%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<script type="text/javascript" src="../scripts/jquery.min.js"></script>
<script type="text/javascript" src="../scripts/jquery-1.js"></script>
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
</script>


<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>

<link href="../css/select2.css" rel="stylesheet" />
	<script src="../scripts/select2.js"></script>
	<script>
    	$(document).ready(function() { 
    		$("select:not(.notuseselect2)").select2({ width: 'resolve' });     
    	});
    </script>	
<SCRIPT language="javascript" type="text/javascript">
	
	function submitform()
	{
		
		document.forms['kstdForm'].kvIds.value= "";
		document.forms['kstdForm'].action.value='submitForm';
	   	document.forms['kstdForm'].submit();
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
			if (keypressed == 8 || keypressed == 127 || keypressed == 37 || keypressed == 39 || keypressed == 0 || keypressed == 46)
			{//Phím Delete và Phím Back
				return;
			}
			return false;
		}
	}
	function submitform1()
	{
		
		
		document.forms['kstdForm'].action.value='submitForm';
	   	document.forms['kstdForm'].submit();
	}
	
	function saveform()
	{	   
		
		 document.forms['kstdForm'].action.value='save';
	     document.forms['kstdForm'].submit();
	}
	
	function kiemtra()
	{	   
		
		var npp = document.getElementsByName("ngaylui");
		for(i = 0; i < npp.length; i++)
		{
			if(parseFloat(npp.item(i).value) > 31 || parseFloat(npp.item(i).value) < 0)
			{
				 alert('Lỗi ! Ngày lùi chỉ được phép từ 1-31 ngày');
				break;
			}
		}
	}
	
	function DoiNgay()
	{	   
		
		var npp = document.getElementsByName("ngaylui");
		var ngayluidh = document.getElementById("ngayluidh"); 
		if(parseFloat(parseFloat(ngayluidh.value)) > 0)
		{
			for(i = 0; i < npp.length; i++)
			{
				npp.item(i).value = 0;
				$('#ngaylui'+i).attr('readonly', 'readonly');
				
			}
		}else
		{
			for(i = 0; i < npp.length; i++)
			{
				npp.item(i).value = 0;
				$('#ngaylui'+i).removeAttr('readonly');
				
			}
		}
		 
	}
	
	function CheckNpp()
	{
		var npp = document.getElementsByName("nppIds");
		for(i = 0; i < npp.length; i++)
		{
			if(npp.item(i).checked)
				 return true;
		}
		return false;
		
	}
	function CheckAllNpp(values){
		var npp = document.getElementsByName("nppIds");
		for(i = 0; i < npp.length; i++)
		{
			npp.item(i).checked =values;
				
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

<% Csrf csdr=new Csrf(request,response,true,false,true);%>
<input type="hidden" name="action" value='1'>
<input name="userId" type="hidden" value='<%=userId %>' size="30">
<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:70%; padding:5px; float:left" class="tbnavigation">
        	<%=url %>
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng bạn <%= userTen %> &nbsp;&nbsp;
        </div>
    </div>
  
  	<div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="tbdarkrow">
    	<A href= "../../KhoasotudongSvl?userId=<%=userId %>">		 		
	<img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" width="30" height="30" border="1" longdesc="Quay ve" style="border-style:outset"></A>
        <A href="javascript:saveform()">
        	<IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A>
    </div>
    
    <div align="left" style="width:100%; float:none; clear:left">
  		<fieldset>
    		<legend class="legendtitle"> Báo lỗi nhập liệu</legend>
    		<textarea name="dataerror" style="width: 100%" readonly="readonly" rows="1" readonly="readonly"><%= kstdBean.getMsg() %></textarea>
		         <% kstdBean.setMsg(""); %>
    	</fieldset>
  	</div>
  	
  	<TABLE width="100%" border="0" cellpadding="0" >
			  <tr>
				
				   
				
							<TABLE  width="100%" cellspacing="0" cellpadding = "6" >
								
								
								<TR>
								  <TD width="10%" class="plainlabel"  align="left" ><%=ChuyenNgu.get("Ngày khóa sổ hàng tháng",nnId) %> </TD>
								  <TD width="20%" class="plainlabel"  align="left"><input class="days" type="text" name="beforeday" value="<%=kstdBean.getBeforeDay() %>" size="20"> </TD>
								
									
								
								  <TD width="10%" class="plainlabel"  align="left"><%=ChuyenNgu.get("Ngày lùi đơn hàng",nnId) %> </TD>
								  <TD width="20%" class="plainlabel" ><input class="days" type="text" name="ngayluidh" id = "ngayluidh" value="<%=kstdBean.getNgayluidh() %>" size="20" onChange="DoiNgay();"> </TD>
								</TR>
									
								<TR>
									
								  	  <TD width="10%" class="plainlabel"  align="left"><%=ChuyenNgu.get("Vùng",nnId) %></TD>
								  	  	<TD  class="plainlabel" width="20%"  align="left">
								  		<SELECT name="vungIds" onChange="submitform();">
								    	<option value=""></option> 
									      <% try{ while(vung.next()){ 
									    	if(vungIds.contains(vung.getString("pk_seq"))){ %>
									      		<option value='<%=vung.getString("pk_seq") %>' selected='selected'><%=vung.getString("ten")  %></option>
									      	<%}else{ %>
									     		<option value='<%=vung.getString("pk_seq") %>' ><%=vung.getString("ten")  %></option>
									     	<%}}}catch(java.sql.SQLException e){} %>
									  	</SELECT>
									</TD>
								
								   <TD width="10%" class="plainlabel"  align="left" ><%=ChuyenNgu.get("Khu vực",nnId) %></TD>
								  	<TD  class="plainlabel"  width="20%" ><SELECT name="kvIds" onChange="submitform1();" >
								    	<option value=""></option> 
									      <% try{ while(khuvuc.next()){ 
									    	if(kvIds.contains(khuvuc.getString("pk_seq"))){ %>
									      		<option value='<%= khuvuc.getString("pk_seq") %>' selected='selected'><%=khuvuc.getString("ten") %></option>
									      	<%}else{ %>
									     		<option value='<%= khuvuc.getString("pk_seq") %>' ><%=khuvuc.getString("ten") %></option>
									     	<%}}}catch(java.sql.SQLException e){} %>
									  	</SELECT>
									</TD>	 
								</TR>
								
							</TABLE>
					
					 
					 
					
				</TR>
	</TABLE>
  	
  	 

             <table width="100%" border="0" cellspacing="1" cellpadding="3px">
                    <tr class="tbheader">
	                	<th width="50px" align="center"><%=ChuyenNgu.get("Mã npp",nnId) %> </th>
	                    <th width="100px" align="left"><%=ChuyenNgu.get("Tên npp",nnId) %>  </th>
	                    <th width="200px" align="left"><%=ChuyenNgu.get("Địa chỉ",nnId) %> </th>
	                       <th width="100px" align="left"><%=ChuyenNgu.get("Ngày lùi đơn hàng",nnId) %></th>
	                    <th width="20px" align="center"><%=ChuyenNgu.get("Chọn",nnId) %> <input type ="checkbox" id="checkall" onchange="CheckAllNpp(this.checked)"> </th>
                	</tr>
                    <% 
                    if(nhapp != null){
                	int n = 0;
					try{ while(nhapp.next())
					{ %>
					
					<% if (n % 2 == 0){ %>
					<TR class= "tblightrow" >
					<%}else{ %>
					<TR class= "tbdarkrow" >
					<%} %>
						<TD align="center"><%= nhapp.getString("manpp") %>  
						<input type="hidden" name="danhsachnpp" value="<%=nhapp.getString("pk_seq")%>"></TD>
						<TD align="left"><%= nhapp.getString("ten") %></TD>
						<TD align="left"><%= nhapp.getString("diachi") %></TD>	
					 <TD align="left"> 
					 <input type="text" name="ngaylui" id = "ngaylui<%=n %>" value="<%=nhapp.getString("ngaylui")%>" maxlength="2"  onkeypress="return keypress(event);" onchange = "kiemtra();">
					 </TD>										
						<% if( nppIds.contains(nhapp.getString("pk_seq"))){ %>
							   <TD align="center"><input name="nppIds" type="checkbox"  value ="<%= nhapp.getString("pk_seq") %>" checked></TD>
						<%}else{%>
							   <TD align="center"><input name="nppIds" type="checkbox" value ="<%= nhapp.getString("pk_seq") %>" ></TD>
						<%}%> 
                    </TR> <%
                    	n++;
                    
					} %> 
				     	<tr class="tbheader"><td colspan="4">&nbsp;</td></tr>
				   <% nhapp.close(); }catch(java.sql.SQLException e){} 
                } %>	                 
             </table>    
         </div>      
    
</form>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</html>


<%
	if(vung!=null){ vung.close(); vung = null ; }
	if(khuvuc!=null){ khuvuc.close(); khuvuc = null ; }
	if(nhapp!=null){ nhapp.close(); nhapp = null ; }
	if(vungIds!=null){ vungIds.clear(); vungIds = null ; }
	if(kvIds!=null){ kvIds.clear(); kvIds = null ; }
	if(nppIds!=null){ nppIds.clear(); nppIds = null ; }
	
	if(kstdBean!=null)
	{
		kstdBean.DBclose();
		kstdBean = null;
	}
		session.setAttribute("obj", null);
	}
%>
