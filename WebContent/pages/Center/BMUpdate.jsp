<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.beans.bm.IBm"  %>
<%@ page  import = "geso.dms.center.beans.bm.imp.*" %>
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


<% IBm bmBean = (IBm)session.getAttribute("bmBean"); %>
<% //ResultSet ttpp = (ResultSet) bmBean.getTrungtamphanphoiList(); %>
<% ResultSet kbh = bmBean.getKbh(); %>
<% ResultSet dvkd = bmBean.getDvkd(); %>
<% ResultSet vung = bmBean.getVung(); %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="../scripts/jquery-1.js"></script>
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />

<script type="text/javascript"	src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js"
	type="text/javascript"></script>
<script type="text/javascript">
		$(document).ready(function() {		
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

<script type="text/javascript"
	src="../scripts/report-js/action-report.js"></script>

<LINK rel="stylesheet" href="../css/main.css" type="text/css">

<SCRIPT language="javascript" type="text/javascript">
 function confirmLogout(){
    if(confirm("B???n c?? mu???n ????ng xu???t?"))
    {
		top.location.href = "home.jsp";
    }
    return
  }
 function submitform()
 {   
    document.forms["bmForm"].submit();
 }

 function saveform()
 {
	 var ten = document.getElementById('bmTen');
	 var diachi = document.getElementById('DiaChi');
	 var email = document.getElementById('Email');
	 var dienthoai = document.getElementById('DienThoai');
	 var kbhId = document.getElementById('kbhId');
	 var dvkdId = document.getElementById('dvkdId');	
	 var bm_vung = document.getElementsByName("vungId");
	 var ma = document.getElementById('smartid');

	 //var ttpp = document.getElementById('TTPP');	
	 
/* 	 if(ttpp.value == "")
	 {
		 alert('Vui l??ng ch???n Trung t??m ph??n ph???i');
		 return;
	 } */

	 
	 if(kbhId.value == ""){
	 		alert('Vui l??ng nh???p k??nh b??n h??ng');
			 return;
	 	}
		
		 if(ten.value == "")
		 {
			 alert('Vui l??ng nh???p t??n c???a Tr?????ng chi nh??nh');
			 return;
		 }

		
		 if(dvkdId.value == "")
		 {
			 alert('Vui l??ng ch???n ????n v??? kinh doanh');
			 return;
		 }	 

		 if(diachi.value == "")
		 {
			 alert('Vui l??ng nh???p ?????a ch???');
			 return;
		 }	
		 if(ma.value == "")
		 {
			 alert('Vui l??ng nh???p m?? c???a Tr?????ng chi nh??nh');
			 return;
		 }

		 if(email.value == "")
		 {
			 alert('Vui l??ng nh???p ?????a ch??? Email');
			 return;
		 }	 
		 
		 if(dienthoai.value == "")
		 {
			 alert('Vui l??ng nh???p s??? ??i???n tho???i');
			 return;
		 }	 
			 

	 var flag = '';
	 for(var i in bm_vung)
	 {
		 if(bm_vung[i].checked)
			 flag = flag + bm_vung[i].value;
	 }
	 if(dvkdId.value != 100069)
	 if(flag == '')
	 {
		 alert('Vui l??ng ch???n Chi nh??nh');
		 return;
	 }
	 
	 document.forms['bmForm'].action.value='save';
     document.forms['bmForm'].submit();
 }
</SCRIPT>

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

<form name="bmForm" method="post" action="../../BmUpdateSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<INPUT name="userId" type="hidden" value='<%= userId %>' size="30">
<INPUT name="Id" type="hidden" value='<%=bmBean.getId()%>' size="30">
<input type="hidden" name="action" value='1'>

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"  height="100%">
    <TR>
        <TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
            <TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
                <TR>
                    <TD align="left" class="tbnavigation">
                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                          <tr height="22">
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;Qu???n l?? nh??n s??? > Khai b??o > Gi??m ?????c mi???n > C???p nh???t</TD> 
                             <TD colspan="2" align="right" class="tbnavigation">Ch??o m???ng b???n <%=userTen %>&nbsp;  </TD></tr>

                        </table>
                    </TD>
                </TR>
            </TABLE>
            <TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
                    <TR class = "tbdarkrow">
                        <TD width="30" align="center"><A href="javascript:history.back()" ><img src="../images/Back30.png" alt="Quay Ve"  title="Quay Ve" width="30" height="30" border="1" longdesc="Quay ve????" style="border-style:outset"></A></TD>
                        <TD width="2" align="left" ></TD>
                        <TD width="30" align="left" ><A href="javascript:saveform()" ><IMG src="../images/Save30.png" title="Luu Lai" alt="Luu Lai" border = "1"  style="border-style:outset"></A></TD>

                        <TD align="left" >&nbsp;</TD>
                    </TR>
            </TABLE>
            <TABLE width="100%" border="0" cellpadding="0"  cellspacing="0">
                                <TR>
                                    <TD align="left" colspan="4" class="legendtitle">
                                        <FIELDSET>
                                        <LEGEND class="legendtitle">Th??ng b??o</LEGEND>
                                        
                                        <textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold"  style="width: 100% ; color:#F00 ; font-weight:bold" readonly="readonly" rows="1" ><%= bmBean.getMsg() %></textarea>
                                        <%bmBean.setMsg(""); %>
                                        </FIELDSET>
                                   </TD>
                                </TR>
                
                <TR>
                  <TD height="100%" width="100%">
                        <FIELDSET>
                        <LEGEND class="legendtitle" style="color:black">Th??ng tin Tr?????ng chi nh??nh </LEGEND>

                        <TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
                            <TR>
                                <TD width="15%" class="plainlabel" >T??n gi??m ?????c mi???n <FONT class="erroralert"> *</FONT></TD>
                                <TD width="48%" class="plainlabel"><INPUT name="bmTen" id="bmTen" type="text" value="<%= bmBean.getTen() %>" style="width:400Px"></TD>
                                
                                <TD width="15%" class="plainlabel"><div align="left">Ho???t ?????ng<FONT class="erroralert"> *</FONT></div></TD>
                              	<TD width="5%"  class="plainlabel">                         
                                    <% if(bmBean.getTrangthai().equals("1")){ %>
                                    <input name="TrangThai" type="checkbox" value ="" checked >
                                    <%}else{ %>                                                         
                                    <input name="TrangThai" type="checkbox" value ="">
                                    <%} %>    
                              	</TD>

                            </TR>
                            <TR>
							    <TD class="plainlabel">K??nh b??n h??ng<FONT class="erroralert"> *</FONT></TD>
							    <TD  class="plainlabel">
								    <SELECT name="kbhId" id = "kbhId" >
									   <option value=""></option> 
									    <% try{
									    	if(kbh != null)
									    	while(kbh.next()){ 
										   	if(kbh.getString("kbhId").equals(bmBean.getKbhId())){ %>
									      		<option value='<%=kbh.getString("kbhId") %>' selected='selected'><%=kbh.getString("kbh") %></option>
									      	<%}else{ %>
									     		<option value='<%=kbh.getString("kbhId") %>' ><%=kbh.getString("kbh") %></option>
									     	<%}}}catch(java.sql.SQLException e){} %>
									     	
									 </SELECT>
								</TD>
                                
							    <TD class="plainlabel">????n v??? kinh doanh<FONT class="erroralert"> *</FONT></TD>
							    <TD  class="plainlabel">
								    <SELECT name="dvkdId" id = "dvkdId" >
									   <option value=""></option> 
									    <% try{ while(dvkd.next()){ 
										   	if(dvkd.getString("dvkdId").equals(bmBean.getDvkdId())){ %>
									      		<option value='<%=dvkd.getString("dvkdId") %>' selected='selected'><%=dvkd.getString("dvkd") %></option>
									      	<%}else{ %>
									     		<option value='<%=dvkd.getString("dvkdId") %>' ><%=dvkd.getString("dvkd") %></option>
									     	<%}}}catch(java.sql.SQLException e){} %>
									     	
									 </SELECT>
								</TD>

                          	</TR>
                            <TR>
                                <TD width="15%" class="plainlabel" > ?????a ch??? <FONT class="erroralert"> *</FONT></TD>
                                <TD class="plainlabel">
                                	<INPUT name="DiaChi" readonly="readonly" id="DiaChi" type="text" value="<%= bmBean.getDiachi() %>" style="width:400Px">
                                </TD>
								 
								 <TD class="plainlabel required">M??
								 <TD class="plainlabel">
                                	<INPUT  name="smartid" id="smartid" type="text" value="<%= bmBean.getSmartid() %>" style="width:200Px">
                                </TD>
								 <!-- <TD class="plainlabel"></TD> -->
								 
								 <%-- <TD width="15%" class="plainlabel" > Trung t??m ph??n ph???i <FONT class="erroralert"> *</FONT></TD>
                                <TD class="plainlabel">
                                	<select name="TTPP" id="TTPP" > 
													<option value=""></option>
													<%
													if(ttpp!=null)
													{
														while(ttpp.next())
														{
															if(bmBean.getTtppId()!=null)
															{
																if(ttpp.getString("PK_SEQ").equals(bmBean.getTtppId()))
																{
																	%>
																	<option value='<%=ttpp.getString("PK_SEQ")%>' selected><%=ttpp.getString("TEN")%></option>
																	<%
																}
																else
																{
																	%>
																	<option value='<%=ttpp.getString("PK_SEQ")%>' ><%=ttpp.getString("TEN")%></option>
																	<%
																}
															}
															else
															{
															%>
															<option value='<%=ttpp.getString("PK_SEQ")%>' ><%=ttpp.getString("TEN")%></option>
														<%	}
														}
													}
														
													%>
												</select>
									
                                	</TD> --%>
                          	</TR>
                            <TR>
                                <TD width="15%" class="plainlabel" > Email <FONT class="erroralert"> *</FONT></TD>
                                <TD class="plainlabel">
                                	<INPUT name="Email" readonly="readonly" id="Email" type="text" value="<%= bmBean.getEmail() %>" style="width:400Px">
                                </TD>

                              	<TD class="plainlabel">??i???n tho???i <FONT class="erroralert"> *</FONT></TD>
                              	<TD class="plainlabel"><input  readonly="readonly" name="DienThoai" id="DienThoai" type="text" value="<%= bmBean.getDienthoai() %>"></TD>
                          	</TR>
                          	
                    </FIELDSET>
                    <TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
                          <TR>
                            <TD colspan="4">
                        <FIELDSET>
                        <LEGEND class="legendtitle" style="color:black">Ch???n chi nh??nh<FONT class="erroralert"> *</FONT></LEGEND>

                        <TABLE border="0" width="100%" cellpadding="3" cellspacing="1">
                            <TR class="tbheader">
                                <TH width="40%">V??ng </TH>    
                                <TH width="20%">Ng??y b???t ?????u </TH>
                                <TH width="20%">Ng??y k???t th??c </TH>
                                <TH width="20%">Ch???n </TH>
                            </TR>
                     		
                     		<%
								int i = 0;
								String lightrow = "tblightrow";
								String darkrow = "tbdarkrow";
								try{
									Hashtable<String, String> vungIds = bmBean.getHTVungId();
									if(vung!=null)
									while(vung.next()){ 
										if (i % 2 != 0) {%>						
										<TR class= <%=lightrow%> >
										<%} else {%>
										<TR class= <%= darkrow%> >
										<%}%>
											<TD align="center"><div align="left"> <%= vung.getString("VUNG") %> </div></TD>
											<% if (vungIds.containsKey(vung.getString("VUNGID"))){ %>
											<TD align="center"><input class="days" name="ngaybatdau<%=vung.getString("VUNGID")%>" value='<%=vung.getString("ngaybatdau")%>' readonly="readonly">  </TD>
											<TD align="center"><input class="days"  name="ngayketthuc<%=vung.getString("VUNGID")%>" value='<%=vung.getString("ngayketthuc")%>' readonly="readonly"> </TD>
											
												<TD align="center"><input name="vungId" id="vungId" type="checkbox" value ="<%= vung.getString("VUNGID") %>" checked></TD>
											<%}else{ %>
												<TD align="center"><input class="days" name="ngaybatdau<%=vung.getString("VUNGID")%>" value='<%=vung.getString("ngaybatdau")%>' readonly="readonly">  </TD>
											<TD align="center"><input class="days"  name="ngayketthuc<%=vung.getString("VUNGID")%>" value='<%=vung.getString("ngayketthuc")%>' readonly="readonly"> </TD>
											
												<TD align="center"><input name="vungId" id="vungId" type="checkbox" value ="<%= vung.getString("VUNGID") %>" ></TD>
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
	if(vung != null) vung.close();

	bmBean.DBClose() ;%>	
<%}%>