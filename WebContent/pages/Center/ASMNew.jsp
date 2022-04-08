<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.beans.asm.IAsm"  %>
<%@ page  import = "geso.dms.center.beans.asm.imp.*" %>
<%@ page  import = "java.util.Hashtable"%>
<%@ page  import = "java.sql.ResultSet "%>
<%@ page  import = "geso.dms.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>


<% IAsm asmBean = (IAsm)session.getAttribute("asmBean"); %>
<% //ResultSet ttpp = (ResultSet) asmBean.getTrungtamphanphoiList(); %>
<% ResultSet kbh = asmBean.getKbh(); %>
<% ResultSet dvkd = asmBean.getDvkd(); %>
<% ResultSet kv = asmBean.getKv(); %>

<% String nnId = (String)session.getAttribute("nnId"); %> 
<% if(nnId == null) {
	nnId = "vi"; 
	
	}	
String url = util.getChuyenNguUrl("ASMSvl","",nnId);	
	%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">

<LINK rel="stylesheet" href="../css/main.css" type="text/css">

<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<script type="text/javascript" src="../scripts/jquery.min.js"></script>
<script type="text/javascript" src="../scripts/maskedinput.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js"
	type="text/javascript"></script>
<script type="text/javascript">
		$(document).ready(function() {	
			$("#ngaysinh").mask("9999-99-99");
			$( ".days" ).datepicker({			    
					changeMonth: true,
					changeYear: true				
			});
            $(".button").hover(function(){
                $(".button img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        }); 
		$(document).ready(function(){
            $(".button1").hover(function(){
                $(".button1 img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        });
		
    </script>
<SCRIPT language="javascript" type="text/javascript">
 function confirmLogout(){
    if(confirm("Bạn có muốn đăng xuất?"))
    {
		top.location.href = "home.jsp";
    }
    return
  }
 function submitform()
 {   
    
    document.forms['asmForm'].submit();
 }

 function saveform()
 {
	 var ma = document.getElementById('asmMa');
	 var ten = document.getElementById('asmTen');
	 var diachi = document.getElementById('DiaChi');
	 var email = document.getElementById('Email');
	 var dienthoai = document.getElementById('DienThoai');
	 var kbhId = document.getElementById('kbhId');
	 var dvkdId = document.getElementById('dvkdId');	
	 var asm_kv = document.getElementsByName("kvId");
	 //var ttpp = document.getElementById("TTPP");
	 
/* 	 if(ttpp.value == "")
	 {
		 alert('Vui lòng chọn Trung tâm phân phối');
		 return;
	 } */
	 if(kbhId.value == ""){
	 	alert('Vui lòng chọn Kênh bán hàng');
	 	return;
	 }
	 if(ma.value == "")
	 {
		 alert('Vui lòng nhập mã của ASM');
		 return;
	 }
	 
	 if(ten.value == "")
	 {
		 alert('Vui lòng nhập tên của ASM');
		 return;
	 }

	
	 
	 if(dvkdId.value == "")
	 {
		 alert('Vui lòng chọn Đơn vị kinh doanh');
		 return;
	 }	 

	 if(diachi.value == "")
	 {
		 alert('Vui lòng nhập địa chỉ');
		 return;
	 }	

	 if(email.value == "")
	 {
		 alert('Vui lòng nhập địa chỉ Email');
		 return;
	 }	 
	 
	 if(dienthoai.value == "")
	 {
		 alert('Vui lòng nhập số điện thoại');
		 return;
	 }	 
		 

	/*  var flag = '';
	 for(var i in asm_kv)
	 {
		 if(asm_kv[i].checked)
			 flag = flag + asm_kv[i].value;
	 }
	 
	 if(dvkdId.value != 100069)
	 if(flag == '')
	 {
		 alert('Vui lòng chọn Khu vực');
		 return;
	 } */
	 
	 document.forms['asmForm'].action.value='save';
     document.forms['asmForm'].submit();
 }
</SCRIPT>

<!-- BEGIN RENDER AUTO -->

<% if (!geso.dms.center.util.GlobalValue.getDevmode()) { %>
	<script type="text/javascript" src="../scripts/disableF12.js"></script>
<% } %>

<!-- END RENDER AUTO -->
</HEAD>

<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<!-- BEGIN RENDER AUTO -->
<%if(!geso.dms.center.util.GlobalValue.getDevmode()){ %>
	<noscript> 
	 	<meta http-equiv="refresh" content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")  %>">
		<!-- //window.location = "https://www.google.com"; -->
	</noscript>
<%} %>
<!-- END RENDER AUTO -->

<form name="asmForm" method="post" action="../../ASMUpdateSvl"> 

 <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> 
 <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>

<INPUT name="userId" type="hidden" value='<%= userId %>' size="30">
<input type="hidden" name="action" value='1'>

	<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"  height="100%">
    <TR>
        <TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
            <TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
                <TR>
                    <TD align="left" class="tbnavigation">
                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                          <tr height="22">
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;<%= " " + url %> &gt; Tạo mới</TD> 
                             <TD colspan="2" align="right" class="tbnavigation"><%=ChuyenNgu.get("Chào mừng",nnId) %> <%=userTen %>&nbsp;  </TD></tr>

                        </table>
                    </TD>
                </TR>
            </TABLE>
            <TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
                    <TR class = "tbdarkrow">
                        <TD width="30" align="center"><A href="javascript:history.back()" ><img src="../images/Back30.png" alt="Quay Ve"  title="Quay Ve" width="30" height="30" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
                        <TD width="2" align="left" ></TD>
                        <TD width="30" align="left" ><A href="javascript:saveform()" ><IMG src="../images/Save30.png" title="Luu Lai" alt="Luu Lai" border = "1"  style="border-style:outset"></A></TD>

                        <TD align="left" >&nbsp;</TD>
                    </TR>
            </TABLE>
            <TABLE width="100%" border="0" cellpadding="0"  cellspacing="0">
                                <TR>
                                    <TD align="left" colspan="4" class="legendtitle">
                                        <FIELDSET>
                                        <LEGEND class="legendtitle"><%=ChuyenNgu.get("Thông báo",nnId) %></LEGEND>
                                        
                                        <textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold"  style="width: 100% ; color:#F00 ; font-weight:bold" readonly="readonly" rows="1" ><%= asmBean.getMsg() %></textarea>
                                        <%asmBean.setMsg(""); %>
                                        </FIELDSET>
                                   </TD>
                                </TR>
                
                <TR>
                  <TD height="100%" width="100%">
                        <FIELDSET>
                        <LEGEND class="legendtitle" style="color:black"><%=ChuyenNgu.get("Thông tin ASM",nnId) %> </LEGEND>

                        <TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
                            <TR>
                                <TD width="15%" class="plainlabel" ><%=ChuyenNgu.get("Tên ASM",nnId) %> <FONT class="erroralert"> *</FONT></TD>
                                <TD width="48%" class="plainlabel"><INPUT name="asmTen" id="asmTen" type="text" value="<%= asmBean.getTen() %>" style="width:400Px"></TD>
                                <TD width="15%" class="plainlabel"><div align="left"><%=ChuyenNgu.get("Hoạt động",nnId) %><FONT class="erroralert"> *</FONT></div></TD>
                              	<TD width="5%"  class="plainlabel">                         
                                    <input name="TrangThai" type="checkbox" value ="1" checked readonly>                                                                     
                              	</TD>

                            </TR>
                          <TR>
                              <TD class="plainlabel"><%=ChuyenNgu.get("Mã ASM",nnId) %><FONT class="erroralert"> </FONT></TD>
                              <TD colspan="4" class="plainlabel"><input  name="asmMa" id="asmMa" type="text" value="<%= asmBean.getMa()%>"></TD>
                          </TR>
                            <TR>
                              <TD class="plainlabel"><%=ChuyenNgu.get("Ngày sinh",nnId) %><FONT class="erroralert"> </FONT></TD>
                              <TD colspan="4" class="plainlabel"><input  name="ngaysinh" id="ngaysinh" type="text" value="<%= asmBean.getNgaysinh()%>"></TD>
                          </TR>
                          <TR>
                              <TD class="plainlabel"><%=ChuyenNgu.get("Số CMND",nnId) %><FONT class="erroralert"></FONT></TD>
                              <TD colspan="4" class="plainlabel"><input name="cmnd" maxlength="50"  id="cmnd" type="text" value="<%= asmBean.getCmnd()%>"></TD>
                          </TR>
                          <TR>
                              <TD class="plainlabel"><%=ChuyenNgu.get("Quê quán",nnId) %><FONT class="erroralert"> </FONT></TD>
                              <TD colspan="4" class="plainlabel"><input name="quequan" id="quequan" type="text" value="<%= asmBean.getQuequan()%>" maxlength="300"  style="width:400Px" ></TD>
                          </TR>
                            <TR>
							    <TD class="plainlabel"><%=ChuyenNgu.get("Kênh bán hàng",nnId) %><FONT class="erroralert"> *</FONT></TD>
							    <TD  class="plainlabel">
								    <SELECT name="kbhId" id = "kbhId" onchange="submitform();" >
									   <option value=""></option> 
									    <% try{ while(kbh.next()){ 
										   	if(kbh.getString("kbhId").equals(asmBean.getKbhId())){ %>
									      		<option value='<%=kbh.getString("kbhId") %>' selected='selected'><%=kbh.getString("kbh") %></option>
									      	<%}else{ %>
									     		<option value='<%=kbh.getString("kbhId") %>' ><%=kbh.getString("kbh") %></option>
									     	<%}}}catch(java.sql.SQLException e){} %>
									     	
									 </SELECT>
								</TD>
                                
                                <TD class="plainlabel"><%=ChuyenNgu.get("RSM", nnId)%>
												<FONT class="erroralert">*</FONT></TD>

											<TD class="plainlabel">
												<select name="bmId">
													<option value=""></option>
													<%
														try {
																ResultSet bmRs = asmBean.getBmRs();
																if(bmRs != null)
																while (bmRs.next()) {
																	if (bmRs.getString("pk_seq").equals(asmBean.getBmId())) {
													%>
													<option value='<%=bmRs.getString("pk_seq")%>' selected><%=bmRs.getString("ten")%></option>
													<%
														} else {
													%>
													<option value='<%=bmRs.getString("pk_seq")%>'><%=bmRs.getString("ten")%></option>
													<%
														}
																}
															} catch (java.sql.SQLException e) {
																e.printStackTrace();
															}
													%>
													</select>
											</TD>
							    

                          	</TR>
                            <TR>
                                <TD width="15%" class="plainlabel" > <%=ChuyenNgu.get("Địa chỉ",nnId) %> <FONT class="erroralert"> *</FONT></TD>
                                <TD  class="plainlabel">
                                	<INPUT name="DiaChi" id="DiaChi" type="text" value="<%= asmBean.getDiachi() %>" style="width:400Px">
                                </TD>
                                
                                <TD class="plainlabel"><%=ChuyenNgu.get("Đơn vị kinh doanh",nnId) %><FONT class="erroralert"> *</FONT></TD>
							    <TD  class="plainlabel">
								    <SELECT name="dvkdId" id = "dvkdId" >
									   <option value=""></option> 
									    <% try{ while(dvkd.next()){ 
										   	if(dvkd.getString("dvkdId").equals(asmBean.getDvkdId())){ %>
									      		<option value='<%=dvkd.getString("dvkdId") %>' selected='selected'><%=dvkd.getString("dvkd") %></option>
									      	<%}else{ %>
									     		<option value='<%=dvkd.getString("dvkdId") %>' ><%=dvkd.getString("dvkd") %></option>
									     	<%}}}catch(java.sql.SQLException e){} %>
									     	
									 </SELECT>
								</TD>
                                
								
                          	</TR>
                            <TR>
                                <TD width="15%" class="plainlabel" ><%=ChuyenNgu.get("Email",nnId) %> <FONT class="erroralert"> *</FONT></TD>
                                <TD class="plainlabel">
                                	<INPUT name="Email" id="Email" type="text" value="<%= asmBean.getEmail() %>" style="width:400Px">
                                </TD>

                              	<TD class="plainlabel"><%=ChuyenNgu.get("Điện thoại",nnId) %> <FONT class="erroralert"> *</FONT></TD>
                              	<TD class="plainlabel"><input name="DienThoai" id="DienThoai" type="text" value="<%= asmBean.getDienthoai() %>"></TD>
                          	</TR>
                          	<TR>
							    <TD width="13%" class="plainlabel"><%=ChuyenNgu.get("Nhân viên thử việc",nnId) %></TD>
						      <TD colspan="4" width="13%" class="plainlabel">	
						      <%
						      String check=asmBean.getThuviec();
						      if(check==null)
						    	  check="0";
						      if(check.trim().equals("1")){%>					    
										<input name="thuviec" type="checkbox"  checked>
										<%}else{ %>
										<input name="thuviec" type="checkbox"  >
										<%} %>
							</TD>
							</TR> 
                   
                          <TR>
                            <TD colspan="4">
<%--                         <FIELDSET>
                        <LEGEND class="legendtitle" style="color:black"><%=ChuyenNgu.get("Chọn khu vực",nnId) %><FONT class="erroralert"> *</FONT></LEGEND>
 --%>
                        <TABLE border="0" width="100%" cellpadding="3" cellspacing="1">
                            <TR class="tbheader">
                                <TH width="80%"><%=ChuyenNgu.get("Khu vực",nnId) %> </TH>    
                                  <TH width="20%"><%=ChuyenNgu.get("Ngày bắt đầu",nnId) %> </TH>
                                <TH width="20%"><%=ChuyenNgu.get("Ngày kết thúc",nnId) %> </TH>                       
                                <TH width="20%"><%=ChuyenNgu.get("Chọn",nnId) %> </TH>
                            </TR>
                     		
                     		<%
								int i = 0;
								String lightrow = "tblightrow";
								String darkrow = "tbdarkrow";
								try{
									Hashtable<String, String> kvIds = asmBean.getHTKvId();
									if(kv!=null)
									while(kv.next()){ 
										if (i % 2 != 0) {%>						
										<TR class= <%=lightrow%> >
										<%} else {%>
										<TR class= <%= darkrow%> >
										<%}%>
											<TD align="center"><div align="left"><%= kv.getString("KV") %> </div></TD>
											<% if (kvIds.containsKey(kv.getString("KVID"))){ %>
												<TD align="center"><input class="days" name="ngaybatdau<%=kv.getString("KVID")%>" value='<%=kv.getString("ngaybatdau")%>'>  </TD>
											<TD align="center"><input class="days"  name="ngayketthuc<%=kv.getString("KVID")%>" value='<%=kv.getString("ngayketthuc")%>'> </TD>
											
												<TD align="center"><input name="kvId" id="kvId" type="checkbox" value ="<%= kv.getString("KVID") %>" checked></TD>
											<%}else{ %>
												<TD align="center"><input class="days" name="ngaybatdau<%=kv.getString("KVID")%>" value='<%=kv.getString("ngaybatdau")%>'>  </TD>
											<TD align="center"><input class="days"  name="ngayketthuc<%=kv.getString("KVID")%>" value='<%=kv.getString("ngayketthuc")%>'> </TD>
											
												<TD align="center"><input name="kvId" id="kvId" type="checkbox" value ="<%= kv.getString("KVID") %>" ></TD>
											<%} %>
										</TR>
							     
							     <%i++;}}catch(java.sql.SQLException e){}
							  %>
                     		
                        </TABLE>                        
                        </FIELDSET>

                            </TD></TR>
                        </TABLE>
                        

                        
                    </TD>
                  </TR>
            </TABLE>
	    </TD>
    </TR>
</TABLE>
</form>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</HTML>

<% 
	if(kbh != null) kbh.close();
	if(dvkd != null) dvkd.close();
	if(kv != null) kv.close();

	asmBean.DBClose() ;%>	
<%}%>