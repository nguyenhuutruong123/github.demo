<%@page import="com.cete.dynamicpdf.text.bd"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.beans.bandott.IBandott" %>
<%@ page  import = "geso.dms.center.beans.bandott.imp.Bandott" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%@ page  import = "geso.dms.center.util.ChuyenNgu" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>

<% IBandott khBean = (IBandott)session.getAttribute("obj"); %>
<% ResultSet vungRs = khBean.getVungRs(); %>
<% ResultSet kvRs = khBean.getKvRs(); %>
<% ResultSet nppRs = khBean.getNppRs(); %>
<% ResultSet DvkdRs = khBean.getDvkdRs();%>
<% ResultSet ddkd = khBean.getDdkdRs(); %>
<% ResultSet ma_tenkhRs = khBean.getMa_tenkhRs(); %>
<% ResultSet tbh = khBean.getTbhRs(); %>
<% ResultSet khlist = khBean.getKhChuaViengThamRs(); 

	String isTT = khBean.getIsTT();
	int[] quyen = new  int[6];
	String param = "";
	if (isTT != null && isTT.equals("1")) {
		param = "&view="+khBean.getView()+"&isTT="+isTT;
	}
	else { //chưa dùng trường hợp này, tính sau
		param = "&view="+khBean.getView();
	}
	quyen = util.Getquyen("BandoTTSvl",param, userId);
	
%>

<% String nnId = (String)session.getAttribute("nnId"); %> 
<% 
if(nnId == null) {
	nnId = "vi"; 
}
String url = util.getChuyenNguUrl("BandoTTSvl","&view=khachhangToado",nnId);	
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<TITLE>SGP - Project</TITLE>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
    
    <script type="text/javascript">

	function xtdnhieukh()
  	{
  		document.forms['bdForm'].action.value= 'xtdnhieukh';
  		document.forms["bdForm"].submit();
  	}
    function anhdaidien()
    {
    	document.forms['bdForm'].action.value= 'xoaanhnhieu';
  		document.forms["bdForm"].submit();
    }
    </script>
    
    
    
    <LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
	<LINK rel="stylesheet" href="../css/main.css" type="text/css">

   	<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
	<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
	<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
  	
  	<script type="text/javascript">
 	  	function xoatoado(khId) {
			if(confirm("Bạn có chắc chắn muốn xóa tọa độ khách hàng này không ?") && typeof(khId) !== "undefined") {
	  			$("#khId").val(khId);
	  			document.forms["bdForm"].submit();
	  		}
		} 
		function xoaanh(khId) {
			if(confirm("Bạn có chắc chắn muốn ảnh ảnh đại diện  ?") && typeof(khId) !== "undefined") {
	  			$("#khId").val(khId);
	  			document.forms['bdForm'].action.value= 'xoaanh';
	  			document.forms["bdForm"].submit();
	  		}
		}
	  	function submitform()
		{
		    document.forms["bdForm"].submit();
		}
  	</script>
  	<link href="../css/select2.css" rel="stylesheet" />
	<script src="../scripts/select2.js"></script>
	<script>
    	$(document).ready(function() { 
    		$("select:not(.notuseselect2)").select2({ width: 'resolve' });     
    	});
    </script>	

<!-- BEGIN RENDER AUTO -->

<% if (!geso.dms.center.util.GlobalValue.getDevmode()) { %>
	<script type="text/javascript" src="../scripts/disableF12.js"></script>
<% } %>

<!-- END RENDER AUTO -->
</head>


  <script type="text/javascript">

    function SelectALL()
	{
		var checkAll = document.getElementById("chonall");
		var spIds = document.getElementsByName("chon");
		
		if(checkAll.checked == true)
		{
			for(i = 0; i < spIds.length; i++)
				spIds.item(i).checked = true;
		}
		else
		{
			for(i = 0; i < spIds.length; i++)
				spIds.item(i).checked = false;
		}
		
	}
    
    </script>

<body>
<!-- BEGIN RENDER AUTO -->
<%if(!geso.dms.center.util.GlobalValue.getDevmode()){ %>
	<noscript> 
	 	<meta http-equiv="refresh" content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")  %>">
		<!-- //window.location = "https://www.google.com"; -->
	</noscript>
<%} %>
<!-- END RENDER AUTO -->

	<form name="bdForm" method="post" action="../../BandoTTSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
		<input type="hidden" name="userId" value='<%= userId %>'>
		<input type="hidden" name="view" value='khachhangToado'>
		<input type="hidden" name="action" id="action" value='1'>
		<input type="hidden" name="trungtam" id="trungtam" value=''>
		<input type="hidden" name="khId" id="khId" value="">
		<input type="hidden" name="isTT" id="isTT" value="<%=khBean.getIsTT()%>">
		
		<div id="main" style="width:99.5%; padding-left:2px">
		
			<div align="left" id="header" style="width:100%; float:none">
		    	<div style="width:40%; padding:5px; float:left" class="tbnavigation">
		        	<%=" "+url %>
		        </div>
		        <div align="right" style="padding:5px" class="tbnavigation">
		        	Chào mừng bạn <%= userTen %> &nbsp;&nbsp;
		        </div>
		    </div>
		    
		    <div style="width:100%; float:none" align="left">
		    <fieldset>
		    	<legend class="legendtitle">Thông tin khách hàng</legend>
		        	<TABLE width="100%" cellpadding="4" cellspacing="0">     
		        	
		            <TR>
			         <TD class="plainlabel" ><%=ChuyenNgu.get("Đơn vị kinh doanh",nnId) %> </TD>
			                <TD class="plainlabel" colspan="1" >
			                    <select name="dvkdId"  onChange = "submitform();" id="dvkdId"  style="width:200px" class="select2" > 
			                            <option value="" selected></option>
			                            <% if(DvkdRs != null){
											  try{ while(DvkdRs.next()){ 
								    			if(DvkdRs.getString("pk_seq").equals(khBean.getDvkdId())){ %>
								      				<option value='<%= DvkdRs.getString("pk_seq")%>' selected><%= DvkdRs.getString("donvikinhdoanh") %></option>
								      			<%}else{ %>
								     				<option value='<%= DvkdRs.getString("pk_seq")%>'><%= DvkdRs.getString("donvikinhdoanh") %></option>
								     	<%}} DvkdRs.close(); }catch(java.sql.SQLException e){} }%>
			                        </select>    
			                </TD>
			                
			                <TD width="17%" class="plainlabel"><%=ChuyenNgu.get("Mã/tên khách hàng",nnId)%></TD>
								    <TD width="29%" class="plainlabel">
											<INPUT name="ma_tenkh" type="text" value="<%= khBean.getMa_tenkh() %>" size="40" onChange = "submitform();">
								  </TD>
			                
			        
			               
			                
     					   </TR> 
			            <TR>
			                <TD class="plainlabel" width="150px" ><%=ChuyenNgu.get("Vùng",nnId) %> </TD>
			                <TD class="plainlabel" width="240px">
			                    <select name="vung" id="vung" onChange = "submitform();">
			                            <option value="" selected></option>
			                            <% if(vungRs != null){
											  try{ while(vungRs.next()){ 
								    			if(vungRs.getString("pk_seq").equals(khBean.getVungId())){ %>
								      				<option value='<%= vungRs.getString("pk_seq")%>' selected><%= vungRs.getString("ten") %></option>
								      			<%}else{ %>
								     				<option value='<%= vungRs.getString("pk_seq")%>'><%= vungRs.getString("ten") %></option>
								     	<%}} vungRs.close(); }catch(java.sql.SQLException e){} }%>
			                        </select>    
			                </TD>
			                <TD class="plainlabel" width="150px"><%=ChuyenNgu.get("Khu vực",nnId) %> </TD>
			                <TD class="plainlabel">
			                    <select name="khuvuc" id="khuvuc" onChange = "submitform();">
			                            <option value="" selected></option>
			                            <% if(kvRs != null){
											  try{ while(kvRs.next()){ 
								    			if(kvRs.getString("pk_seq").equals(khBean.getkvId())){ %>
								      				<option value='<%= kvRs.getString("pk_seq")%>' selected><%= kvRs.getString("ten") %></option>
								      			<%}else{ %>
								     				<option value='<%= kvRs.getString("pk_seq")%>'><%= kvRs.getString("ten") %></option>
								     	<%}} kvRs.close(); }catch(java.sql.SQLException e){} }%>
			                        </select>    
			                </TD>
			            </TR>
			            <TR>
			                <TD class="plainlabel" ><%=ChuyenNgu.get("Nhà phân phối",nnId) %> </TD>
			                <TD class="plainlabel">
			                    <select name="npp" id="npp" onChange = "submitform();">
			                            <option value="" selected></option>
			                            <% if(nppRs != null){
											  try{ while(nppRs.next()){ 
								    			if(nppRs.getString("pk_seq").equals(khBean.getNppId())){ %>
								      				<option value='<%= nppRs.getString("pk_seq")%>' selected><%= nppRs.getString("ten") %></option>
								      			<%}else{ %>
								     				<option value='<%= nppRs.getString("pk_seq")%>'><%= nppRs.getString("ten") %></option>
								     	<%}} nppRs.close(); }catch(java.sql.SQLException e){} }%>
			                        </select>
			                </TD>
			                <TD class="plainlabel" ><%=ChuyenNgu.get("Nhân viên bán hàng",nnId) %> </TD>
			                <TD class="plainlabel">
			                    <select name="ddkd" id="ddkd" onChange = "submitform();">
			                            <option value="" selected></option>
			                            <% if(ddkd != null){
											  try{ while(ddkd.next()){ 
								    			if(ddkd.getString("ddkdId").equals(khBean.getDdkdId())){ %>
								      				<option value='<%= ddkd.getString("ddkdId")%>' selected><%= ddkd.getString("ddkdTen") %></option>
								      			<%}else{ %>
								     				<option value='<%= ddkd.getString("ddkdId")%>'><%= ddkd.getString("ddkdTen") %></option>
								     	<%}} ddkd.close(); }catch(java.sql.SQLException e){} }%>
			                        </select>    
			                </TD>
		            	</TR>
		            	<TR>
		               
		                <TD class="plainlabel" ><%=ChuyenNgu.get("Tuyến bán hàng",nnId) %> </TD>
		                <TD class="plainlabel" colspan="2">
		                    <select name="tbh" id="tbh" onChange = "submitform();">
		                          <option value="" selected></option>
		                          <% if(tbh != null){
								  try{ while(tbh.next()){ 
					    			if(tbh.getString("tbhId").equals(khBean.getTbhId())){ %>
					      				<option value='<%= tbh.getString("tbhId")%>' selected><%= tbh.getString("tbhTen") %></option>
					      			<%}else{ %>
					     				<option value='<%= tbh.getString("tbhId")%>'><%= tbh.getString("tbhTen") %></option>
					     	<%}} tbh.close(); }catch(java.sql.SQLException e){} }%>
		                      </select>    
		               </TD>
		               
		                <TD class="plainlabel" > <a style="display:none" class="button2" href="javascript:xtdnhieukh()"> 
								<img style="top: -4px;" src="../images/button.png" alt=""><%=ChuyenNgu.get("Xóa toạ độ",nnId) %>  </a>&nbsp;&nbsp;&nbsp;&nbsp;
								 <a class="button2" href="javascript:anhdaidien()"> 
								<img style="top: -4px;" src="../images/button.png" alt=""><%=ChuyenNgu.get("Xóa ảnh đại diện",nnId) %>  </a>&nbsp;&nbsp;&nbsp;&nbsp;
                       </TD> 
		               
		               
		            </TR>
		        </TABLE>
		     	<hr>
		    
				<TABLE width="100%" border="0" cellspacing="0" cellpadding="2">
					<TR>
						<TD width="100%">
							<TABLE class="" width="100%">
								<TR>
									<TD width="98%">
										<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
											<TR class="tbheader">
												<TH width="10%" align="center"><%=ChuyenNgu.get("Mã khách hàng",nnId) %></TH>
												<TH width="30%" align="center"><%=ChuyenNgu.get("Tên khách hàng",nnId) %></TH>
												<TH width="20%" align="center"><%=ChuyenNgu.get("Địa chỉ",nnId) %></TH>
												<TH width="10%" align="center"><%=ChuyenNgu.get("Điện thoại",nnId) %></TH>
												<TH width="10%" align="center"><%=ChuyenNgu.get("Kinh độ",nnId) %></TH>
												<TH width="10%" align="center"><%=ChuyenNgu.get("Vĩ độ",nnId) %></TH>
												<TH width="5%" align="center"><%=ChuyenNgu.get("Xóa toạ độ",nnId) %></TH>
												<TH width="5%" align="center"><%=ChuyenNgu.get("Xóa ảnh",nnId) %></TH>
												<TH width="4%">
													<input name="chonall" id="chonall" value="" type="checkbox" onchange="SelectALL();"  />
												</TH>
											</TR>
										
							<%  														
		                        int m = 0;
		                        String lightrow = "tblightrow";
		                        String darkrow = "tbdarkrow";
								if(khlist!=null)
								{ 
									try{								
		                               while (khlist.next()){
		                                   	
		                                  	if (m % 2 != 0) {%>                     
		                                   	<TR class= <%=lightrow%> >
		                                   <%} else {%>
		                                      	<TR class= <%= darkrow%> >
		                                   	<%}%>
												<TD align="left"><div align="center"><%=khlist.getString("khId") %></div></TD>                                   
												<TD align="center"><%= khlist.getString("khTen")%></TD>
													<TD align="center"><%= khlist.getString("diachi")%></TD>
												<TD align="center"><%= khlist.getString("dienthoai")%></TD>
												<TD align="center"><%=khlist.getString("lat")%></TD>
												<TD align="center"><%=khlist.getString("lon")%></TD>
												<TD align="center" >
													<TABLE border = 0 cellpadding="0" cellspacing="0">
														<TR >
															<TD>
																<%
																String isGSBH = khBean.getIsGSBH();
																System.out.println("quyen[1]: "+quyen[1]+"--isGSBH: "+isGSBH);
																if(quyen[1]!= 0 || (isGSBH != null && isGSBH.equals("1"))) {%>
																<A href = "javascript:xoatoado(<%=khlist.getString("pk_seq") %>);">
																<img src="../images/Delete20.png" alt="Xoá toạ độ (Delete coordinates)" title="Xoá toạ độ (Delete coordinates)" width="20" height="20" longdesc="Xoá toạ độ (Delete coordinates)" border = 0></A>
																<%} %>
															</TD>												
														</TR>
													</TABLE>
												</TD>
												<TD align="center">
													<TABLE border = 0 cellpadding="0" cellspacing="0">
														<TR>
															<TD>
																
																<%if(quyen[1]!= 0 ) {%>
																<A href = "javascript:xoaanh(<%=khlist.getString("pk_seq") %>);"><img src="../images/Delete_Icon.png" alt="Xoa toa do" title="Xóa ảnh" width="20" height="20" longdesc="Xoa toa do" border = 0></A>
																<%} %>
															</TD>												
														</TR>
													</TABLE>
												</TD>
												<TD align="center"> <input type="checkbox" name="chon" value="<%=khlist.getString("pk_seq") %>" /> </TD>
		                                   		
												
											</TR>
									<%m++; }}catch(java.sql.SQLException e){}
								}%>
									
											 							
										</TABLE>
			
								</TABLE>
							</TD>
						</TR>
					</TABLE>
		    
		    	</fieldset>
		    </div>
		 </div>
	 </form>
	 <script>
		var msg = "<%= khBean.getMsg() %>".trim();
		if(msg.length > 0) {
			alert(msg);
		}
		
	</script>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>

</html>

<% 
    if(vungRs != null) { vungRs.close(); vungRs = null; } 
	if(kvRs != null) { kvRs.close(); kvRs = null; } 
	if(nppRs != null) { nppRs.close(); nppRs = null; } 
	if(ddkd != null) { ddkd.close(); ddkd = null; } 
	if(tbh != null) { tbh.close(); tbh = null; } 
	if(khlist != null) { khlist.close(); khlist = null; } 

	khBean.DBclose(); khBean = null;
	session.setAttribute("obj",null);
   
}%>