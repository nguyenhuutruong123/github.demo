<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.beans.khachhang.IKhachhang" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%@ page  import = "geso.dms.center.util.ChuyenNgu" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>
<% IKhachhang khBean = (IKhachhang)session.getAttribute("khBean"); %>
<% ResultSet tp = (ResultSet)khBean.getTp() ;  %>
<% ResultSet qh = (ResultSet)khBean.getQh() ;  %>
<% ResultSet hch = (ResultSet)khBean.getHangcuahang(); %>
<% ResultSet kbh = (ResultSet)khBean.getKenhbanhang();  %>
<% ResultSet bgst = (ResultSet)khBean.getBgsieuthi();  %>
<% ResultSet vtch = (ResultSet)khBean.getVtcuahang();  %>
<% ResultSet lch = (ResultSet)khBean.getLoaicuahang(); %>
<% ResultSet nch = (ResultSet)khBean.getNhomcuahang();  %>
<% ResultSet mck = (ResultSet)khBean.getMucchietkhau();  %>
<% ResultSet ghcn = (ResultSet)khBean.getGhcongno();  %>
<% ResultSet nvgn = (ResultSet)khBean.getNvgnRs();  %>
<% ResultSet npp =  (ResultSet) khBean.getNhaphanphoi(); %>
<% ResultSet kvrs =  (ResultSet) khBean.getKhuvucList(); %>
<% ResultSet asm = (ResultSet)khBean.getASMRs();  %>
<% ResultSet khors =  (ResultSet) khBean.getKhoRs(); %>
<% ResultSet Phuongxars =  (ResultSet) khBean.getPhuongxaRS(); %>
<% ResultSet nkh_khList = (ResultSet)khBean.getNkh_khList();  %>
<% Hashtable<Integer, String> nkh_khIds = (Hashtable<Integer, String>)khBean.getNkh_KhIds(); %>

<% String nnId = (String)session.getAttribute("nnId"); %> 
<% if(nnId == null) {
 nnId = "vi"; 
 }
String url = util.getChuyenNguUrl("KhachhangTTSvl","&isMT=0",nnId);	
 %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">

<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>	
<link href="../css/chosen.css" rel="stylesheet" type="text/css" />
<script src="../scripts/chosen.jquery.js" type="text/javascript"></script>
<SCRIPT language="javascript" type="text/javascript">

function submitform()
 {   
    document.forms["khForm"].submit();
 }

 function saveform()
 { 
	 document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Processing...</a>";

 	 document.forms['khForm'].action.value='save';
     document.forms['khForm'].submit();
 }
 function showElement(id)
 {
 	var element = document.getElementById(id);
 	element.style.display = "";
 }

 function hideElement(id)
 {
 	var element = document.getElementById(id);
 	element.style.display = "none";
 }
 function DinhDangTien(num) 
 {
    num = num.toString().replace(/\$|\,/g,'');
    if(isNaN(num))
    num = "0";
    sign = (num == (num = Math.abs(num)));
    num = Math.floor(num*100+0.50000000001);
    num = Math.floor(num/100).toString();
    for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
    num = num.substring(0,num.length-(4*i+3))+','+
    num.substring(num.length-(4*i+3));
    return (((sign)?'':'-') + num);
 }
 function Dinhdang(element)
 {
	element.value=DinhDangTien(element.value);
	if(element.value== '' )
	{
		element.value= '';
	}
 }
 function keypress(e) //Hàm dùng để ngăn người dùng nhập các ký tự khác ký tự số vào TextBox
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
 </SCRIPT>
 <link href="../css/select2.css" rel="stylesheet" />
 	<script src="../scripts/select2.js"></script>
 	<script>
     	$(document).ready(function() { 
     		$("select:not(.notuseselect2)").select2({ width: 'resolve' });     
     	});
     </script>	
<link href="../css/chosen.css" rel="stylesheet" type="text/css" />
<script src="../scripts/chosen.jquery.js" type="text/javascript"></script>

<!-- BEGIN RENDER AUTO -->

<% if (!geso.dms.center.util.GlobalValue.dev_mode) { %>
	<script type="text/javascript" src="../scripts/disableF12.js"></script>
<% } %>

<!-- END RENDER AUTO -->
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<!-- BEGIN RENDER AUTO -->
<%if(!geso.dms.center.util.GlobalValue.dev_mode){ %>
	<noscript> 
	 	<meta http-equiv="refresh" content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")  %>">
		<!-- //window.location = "https://www.google.com"; -->
	</noscript>
<%} %>
<!-- END RENDER AUTO -->

<form name="khForm" method="post" action="../../KhachhangUpdateTTSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%=userId %>'>
<%-- <input type="hidden" name="nppId" value='<%= khBean.getNppId() %>'> --%>
<input type="hidden" name="action" value='1'>
<INPUT name="ismt" type="hidden" value='<%= khBean.getIsMT() %>' size="30">
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="2">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							 <TD align="left" colspan="2" class="tbnavigation"><%=" "+url %> &gt; Tạo mới</TD>
	   						 <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%= userTen %>&nbsp;</TD> </tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>
			<TABLE width="100%" cellpadding="0" cellspacing="2">
			<TR ><TD >
				<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
					<TR class = "tbdarkrow">
						<TD width="30" align="left"><A href="javascript:history.back()" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" width="30" height="30" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
					    <TD width="2" align="left" ></TD>
					    <TD width="30" align="left" >
					    <div id="btnSave">
					    <A href="javascript:saveform()" ><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A>
					    </div>
					    </TD>
				    	<TD align="left" >&nbsp;</TD>
					</TR>
				</TABLE>
			</TD></TR>
			</TABLE>
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
			  	<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>		
	    				<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold"  style="width:100%" rows="1"><%= khBean.getMessage() %></textarea>
							<%khBean.setMessage(""); %>
						</FIELDSET>
				   </TD>
				</tr>			

				<TR>
				  <TD height="100%" width="100%">
						<FIELDSET><LEGEND class="legendtitle" style="color:black">Thông tin khách hàng</LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
						<% if(!khBean.getIsMT().equals("1")) {%>
							<TR >
							<TD width="18%" class="plainlabel"><%=ChuyenNgu.get("Nhà phân phối",nnId) %><FONT class="erroralert"> *</TD>
									<TD colspan="6"  width="33%" class="plainlabel">
									<SELECT name="nppId" id="nppId" onChange = "submitform()">
									<option value=""> </option>
									<% if(npp != null){
										  try
										  { 
											  System.out.println("1.Id npp : " + khBean.getNppId());
											  String optionGroup = "";
											  String optionName = "";
											  int i = 0;
											  
											  while(npp.next())
											  { 
												 if(i == 0)
												 {
													 optionGroup = npp.getString("kvTen");
													 optionName = npp.getString("kvTen");
													 
													 %>
													 
													 <optgroup label="<%= optionName %>" >
												 <% }
												 
												 optionGroup = npp.getString("kvTen");
												 if(optionGroup.trim().equals(optionName.trim()))
												 { %>
													 <% if(npp.getString("nppId").equals(khBean.getNppId())) {%>
													 	<option value="<%= npp.getString("nppId") %>" selected="selected" ><%= npp.getString("nppTen") %></option>
													 <%} else { %>
													 	<option value="<%= npp.getString("nppId") %>"><%= npp.getString("nppTen") %></option>
													 <%} %>
												 <% }
												 else
												 {
													 %>
													</optgroup>
													<% optionName = optionGroup; %>
													<optgroup label="<%= optionName %>" >
													<% if(npp.getString("nppId").equals(khBean.getNppId())) {%>
													 	<option value="<%= npp.getString("nppId") %>" selected="selected" ><%= npp.getString("nppTen") %></option>
													 <%} else { %>
													 	<option value="<%= npp.getString("nppId") %>"><%= npp.getString("nppTen") %></option>
													 <%} %>
												 <% }
												 i++;
								     	 	  } 
											  %>
											  	</optgroup>
											  <%npp.close(); 
								     	 }catch(java.sql.SQLException e){}}%>	  
                                	</SELECT>
								</TD>
																
							</TR>
							<%} %>
							<TR>
								<TD width="15%" class="plainlabel" ><%=ChuyenNgu.get("Tên khách hàng",nnId) %> <FONT class="erroralert"> *</FONT></TD>
								<TD colspan = "2" class="plainlabel">
									<INPUT type="text" name="khTen" id="khTen" value="<%= khBean.getTen() %>" size="40">
								</TD>
								<% if(!khBean.getIsMT().equals("1")) {%>
								<TD width="15%" class="plainlabel" ><%=ChuyenNgu.get("Chủ cửa hiệu",nnId) %> </TD>
								<TD colspan = "2" class="plainlabel">
									<INPUT type="text" name="chucuahieu" id="chucuahieu" value="<%= khBean.getChucuahieu() %>" size="40">
								</TD>
							    <%} %>
							    <TD width="16%" class="plainlabel" ><%=ChuyenNgu.get("Hoạt động",nnId) %> &nbsp;&nbsp;&nbsp;&nbsp; 
						      		<%  if (khBean.getTrangthai().equals("1")){%>
										<input name="trangthai" type="checkbox" value ="1" checked="checked">
									<%} else {%>
										<input name="trangthai" type="checkbox" value ="0">
									<%} %>
						      	</TD>
						      	<%if(khBean.getIsMT().equals("1") ){%>
								<TD width="15%" class="plainlabel" > </TD>
								<TD colspan = "2" class="plainlabel">
								
								</TD>
							    <%} %>
						      
							</TR>
							<TR>
							  	<TD class="plainlabel"><%=ChuyenNgu.get("Địa chỉ",nnId) %> <FONT class="erroralert"> *</FONT></TD>
						  	  	<TD colspan = "6" class="plainlabel"><INPUT type="text" name="diachi" id="diachi" value="<%= khBean.getDiachi() %>" size="40"></TD>
						  </TR>
						  
						  <TR>
							   	 <TD class="plainlabel"><%=ChuyenNgu.get("Tỉnh /Thành phố",nnId) %><FONT class="erroralert"> *</FONT></TD>
								 <TD colspan="2" class="plainlabel"><SELECT name="tpId" id="TP" onChange="submitform();">
								    	<option value=""></option>
								      <% try{while(tp.next()){ 
								    	if(tp.getString("tpId").equals(khBean.getTpId())){ %>
								      		<option value='<%=tp.getString("tpId")%>' selected><%=tp.getString("tpTen") %></option>
								      	<%}else{ %>
								     		<option value='<%=tp.getString("tpId")%>'><%=tp.getString("tpTen") %></option>
								     	<%}}}catch(java.sql.SQLException e){} %>	  
                        				</SELECT>	</TD>

							   	 <TD class="plainlabel"><%=ChuyenNgu.get("Quận/Huyện",nnId) %> <FONT class="erroralert"> *</FONT></TD>
								 <TD colspan="3" class="plainlabel"><SELECT name="qhId" id="QH"  onChange="submitform();">
								    <option value=""></option>
								      <%if(qh != null){ 
								      		try{while(qh.next()){ 
								    			if(qh.getString("qhId").equals(khBean.getQhId())){ %>
								      				<option value='<%=qh.getString("qhId")%>' selected><%=qh.getString("qhTen") %></option>
								      		 <%}else{ %>
								     				<option value='<%=qh.getString("qhId")%>'><%=qh.getString("qhTen") %></option>
								     		<%}}}catch(java.sql.SQLException e){} 
								     		
								      }	%>
								     		  
                        				</SELECT>	</TD>
                        			
						  </TR>
						  
							<TR>
							
							   	 <TD class="plainlabel"><%=ChuyenNgu.get("Phường xã ",nnId) %><FONT class="erroralert"> *</FONT></TD>
								 <TD colspan="2" class="plainlabel"><SELECT name="phuongxaId" >
								    <option value=""></option>
								      <%if(Phuongxars != null){ 
								      		try{while(Phuongxars.next()){ 
								    			if(Phuongxars.getString("pk_seq").equals(khBean.getPhuongxa())){ %>
								      				<option value='<%=Phuongxars.getString("pk_seq")%>' selected><%=Phuongxars.getString("ten") %></option>
								      		 <%}else{ %>
								     				<option value='<%=Phuongxars.getString("pk_seq")%>'><%=Phuongxars.getString("ten") %></option>
								     		<%}}}catch(java.sql.SQLException e){
								     			e.printStackTrace();
								     		} 
								     		
								      }	%>
								     		  
                        				</SELECT>	</TD>
                        				<TD class="plainlabel"><%=ChuyenNgu.get("Điện thoại",nnId) %></TD>
								 <TD colspan="3" class="plainlabel"><INPUT type="text" name="dienthoai" value="<%= khBean.getSodienthoai() %>" size="15"></TD>
							<%-- 	<TD width="15%" class="plainlabel">Mức tín dụng</TD>
						      <TD class="plainlabel" colspan="2"><SELECT name="ghcnTen" id="ghcnTen" >
                                	<OPTION value="" selected></OPTION>
                                	<% try{while(ghcn.next()){ 
								    	if(ghcn.getString("ghcnId").equals(khBean.getGhcnId())){ %>
								      		<option value='<%= ghcn.getString("ghcnId") %>' selected><%= ghcn.getString("ghcnTen")%></option>
								      	<%}else{ %>
								     		<option value='<%= ghcn.getString("ghcnId") %>'><%= ghcn.getString("ghcnTen")%></option>
								     	<%}}}catch(java.sql.SQLException e){} 
								     %>
                              </SELECT></TD> --%>
									      
						  </TR>
						  	<% if(!khBean.getIsMT().equals("1")) {%>
							<TR>
						
							  	 <TD class="plainlabel"><%=ChuyenNgu.get("Số ngày nợ",nnId) %></TD>
								 <TD colspan="2" class="plainlabel"><INPUT type="text" name="songayno" value="<%= khBean.getSongayno() %>" size="15"></TD>
								  <TD class="plainlabel"><%=ChuyenNgu.get("Số tiền nợ",nnId) %></TD>
								 <TD colspan="3" class="plainlabel"><INPUT type="text" name="sotienno" value="<%= khBean.getSotienno() %>" size="15"></TD>
							</TR>
							<%} %>
							<TR>
							 <TD class="plainlabel"><%=ChuyenNgu.get("Mức chiết khấu",nnId) %></TD>
							  <TD class="plainlabel" colspan="2" ><INPUT type="text" name="mckTen" value="<%= khBean.getMckId() %>" size="15"></TD>
							  	 <TD class="plainlabel"><%=ChuyenNgu.get("Mã số thuế",nnId) %></TD>
								 <TD colspan="3" class="plainlabel"><INPUT type="text" name="masothue" value="<%= khBean.getMasothue()==null?"":khBean.getMasothue() %>" size="15"></TD>
							</TR>
							
							<TR>
							   	 <TD class="plainlabel"><%=ChuyenNgu.get("Kênh bán hàng",nnId) %><FONT class="erroralert">*</FONT></TD>
								 <TD colspan="2" class="plainlabel"><SELECT name="kbhTen" id="kbhTen" onChange="tongleBangGia(this);">
								    <OPTION value="" selected></OPTION>
									<% try{while(kbh.next()){ 
								    	if(kbh.getString("kbhId").equals(khBean.getKbhId())){ %>
								      		<option value='<%= kbh.getString("kbhId") %>' selected><%=kbh.getString("kbhTen") %></option>
								      	<%}else{ %>
								     		<option value='<%= kbh.getString("kbhId") %>'><%= kbh.getString("kbhTen") %></option>
								     	<%}}}catch(java.sql.SQLException e){} 
								     %>
									</SELECT></TD>
										<% if(!khBean.getIsMT().equals("1")) {%>
									<TD class="plainlabel"><%=ChuyenNgu.get("Diện tích cửa hàng",nnId) %></TD>
								 <TD colspan="3" class="plainlabel"><INPUT type="text" name="dtch" value="<%= khBean.getDientichch() %>" size="15"></TD>
								 <%} %>
								 	<% if(khBean.getIsMT().equals("1")) {%>
									<TD class="plainlabel"><%=ChuyenNgu.get("Email",nnId) %></TD>
								 <TD colspan="3" class="plainlabel"><INPUT type="text" name="email" value="<%= khBean.getEmail() %>" size="40"></TD>
								 <%} %>
							</TR>
							<% if(!khBean.getIsMT().equals("1")) {%>
							<TR>
							   	 <TD class="plainlabel"><%=ChuyenNgu.get("Nhân viên giao nhận",nnId) %><FONT class="erroralert">*</FONT></TD>
								 <TD colspan="6" class="plainlabel">
									 <SELECT name="nvgnTen" id="nvgnTen">
										<% try{while(nvgn.next()){ 
									    	if(nvgn.getString("nvgnId").equals(khBean.getNvgnId())){ %>
									      		<option value='<%= nvgn.getString("nvgnId") %>' selected><%=nvgn.getString("nvgnTen") %></option>
									      	<%}else{ %>
									     		<option value='<%= nvgn.getString("nvgnId") %>'><%= nvgn.getString("nvgnTen") %></option>
									     	<%}} nvgn.close(); }catch(java.sql.SQLException e){} 
									     %>
									</SELECT>
								</TD>
							</TR>
							<%} %>
								<%if(khBean.getIsMT().equals("1"))  {%>  
							<TR>
							   	 <TD class="plainlabel"><%=ChuyenNgu.get("SR trực thuộc",nnId) %><FONT class="erroralert">*</FONT></TD>
								 <TD colspan="6" class="plainlabel">
									 <SELECT name="asm" id="asm">
									  <option value = ""> </option>
										<% try{
											if(asm != null)
											{
											while(asm.next()){
												
									    	if(asm.getString("pk_seq").equals(khBean.getASMId())){ %>
									      		<option value='<%= asm.getString("pk_seq") %>' selected><%=asm.getString("ten") %></option>
									      	<%}else{ %>
									     		<option value='<%= asm.getString("pk_seq") %>'><%= asm.getString("ten") %></option>
									     	<%}} asm.close();
											}
									     	}catch(java.sql.SQLException e){} 
									     %>
									</SELECT>
								</TD>
							</TR>				
								<%}%>  
						</TABLE>
						</FIELDSET>
							<% if(!khBean.getIsMT().equals("1")) {%>
						<TABLE width = 100% border="0" cellpadding="0" cellspacing ="0">						
							<TR>
								<TD height="100%">
									<FIELDSET><LEGEND class="legendtitle" style="color:black">Phân loại</LEGEND>
									<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
										<TR>
											<TD width="15%" class="plainlabel"><%=ChuyenNgu.get("Loại khách hàng",nnId) %></TD>
											<TD colspan = "6" class="plainlabel"><SELECT name="lchTen" id="lchTen">
											  <option value="" selected> </option>
											  <% try{while(lch.next()){ 
										    		if(lch.getString("lchId").equals(khBean.getLchId())){ %>
										      			<option value='<%= lch.getString("lchId") %>' selected><%= lch.getString("lchTen") %></option>
										      		<%}else{ %>
										     			<option value='<%= lch.getString("lchId") %>'><%= lch.getString("lchTen") %></option>
										     	<%}}}catch(java.sql.SQLException e){} 
										     %>
											  </SELECT></TD>
										</TR>
										<TR>
										  <TD class="plainlabel" valign="top"><%=ChuyenNgu.get("Hạng khách hàng",nnId) %> </TD>
										  <TD colspan = "6" class="plainlabel" valign="top"><SELECT name="hchTen" >
										    <option value=""> </option>					
										     <% try{ while(hch.next()){ 
								    			if(hch.getString("hchId").equals(khBean.getHchId())){ %>
								      				<option value='<%=hch.getString("hchId")%>' selected><%=hch.getString("hchTen") %></option>
								      			<%}else{ %>
								     				<option value='<%=hch.getString("hchId")%>'><%=hch.getString("hchTen") %></option>
								     			<%}}}catch(java.sql.SQLException e){} %>
                                          	</SELECT></TD>
                                          </TR>
                                          <TR>	
                                           <TD class="plainlabel" valign="top"><%=ChuyenNgu.get("Vị trí",nnId) %> </TD>
										  <TD colspan="6" class="plainlabel" valign="top"><SELECT name="vtchTen" >
										    <option value=""> </option>
										    <% try{ 
										    	while(vtch.next()){ 
								    				if(vtch.getString("vtchId").equals(khBean.getVtchId())){ %>
								      					<option value='<%=vtch.getString("vtchId")%>' selected><%=vtch.getString("vtchTen") %></option>
								      			<%  }else{ 
								      					System.out.println(vtch.getString("vtchId")+";"+vtch.getString("vtchTen"));
								      			%>
								     					<option value='<%=vtch.getString("vtchId")%>'><%=vtch.getString("vtchTen") %></option>
								     				
								     			<%}
								    		    }
										      }catch(java.sql.SQLException e){} %>
                                          	</SELECT></TD>
                                          </tr>											  	
									</TABLE>
										<% }%>
									</FIELDSET>								
								</TD>
							</TR>
						</TABLE>
							<% if(!khBean.getIsMT().equals("1")) {%>
					    <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
							<TR>
								<TD colspan = "6" width="100%">
									<FIELDSET><LEGEND class="legendtitle">Phân nhóm 
									</LEGEND>
								<TABLE width="100%" border="0" cellspacing="1" cellpadding="6">
								<TR class="tbheader">
									<TH width="20%"><%=ChuyenNgu.get("Nhóm khách hàng",nnId) %> </TH>
									<TH width="10%"><%=ChuyenNgu.get("Chọn",nnId) %></TH>
								</TR>

								<%
								int i = 0;
								String lightrow = "tblightrow";
								String darkrow = "tbdarkrow";
								if(nkh_khList != null){
								try{while(nkh_khList.next()){ 
									if (i % 2 != 0) {%>						
										<TR class= <%=lightrow%> >
									<%} else {%>
										<TR class= <%= darkrow%> >
								<%}%>
									<TD align="center"><div align="left"><%= nkh_khList.getString("nkhTen") %> </div></TD>
									<% if (nkh_khIds.contains(nkh_khList.getString("nkhId"))){ %>
										   <TD align="center"><input name="nkh_khList" type="checkbox" value ="<%= nkh_khList.getString("nkhId") %>" checked></TD>
									<%}else{%>
										   <TD align="center"><input name="nkh_khList" type="checkbox" value ="<%= nkh_khList.getString("nkhId") %>" ></TD>
									<%}%>
									</TR>
							     	<%i++;}}catch(java.sql.SQLException e){}
								}
							  %>		
							   <tr class="tbfooter" > <td colspan="4" >&nbsp;</td> </tr>  											
							</TABLE>
								<%}%>
									</FIELDSET>								
								</TD>
							</TR>
				    	</TABLE>
					</TR>
			  	</TABLE>
			
	</TD>
	</TR>
</TABLE>
</form>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</HTML>

<% 	

if(khBean != null){
	khBean.DBclose();
	khBean = null;
}

	try{
	if(bgst != null)
		bgst.close();
	if(ghcn != null)
		ghcn.close();
	if(hch != null)
		hch.close();
	if(kbh != null)
		kbh.close();
	if(lch != null)
		lch.close();
	if(mck != null)
		mck.close();
	if(nkh_khList!= null)
		nkh_khList.close();
	if(tp != null)
		tp.close();
	if(qh != null)
		qh.close();
	if(vtch != null)
		vtch.close();
	if(nch!=null){
		nch.close();
	}
	if(nvgn!=null){
		nvgn.close();
	}
	if(nkh_khList!=null){
		nkh_khList.close();
	}
	if(nkh_khIds!=null){
		nkh_khIds.clear();
	}
	
	session.setAttribute("khBean",null);
	}
	catch (SQLException e) {}
	
%>
<%}%>
