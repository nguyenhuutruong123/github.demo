<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.beans.bm.IBm" %>
<%@ page  import = "geso.dms.center.beans.bm.IBmList" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%@ page  import = "geso.dms.center.util.ChuyenNgu" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ int[] quyen = new  int[6];
		quyen = util.Getquyen("BmSvl","",userId);
		
		System.out.println(quyen[0]);
		System.out.println(quyen[1]);
		System.out.println(quyen[2]);
		System.out.println(quyen[3]);	
		System.out.println(quyen[4]);%>


<% IBmList obj =(IBmList)session.getAttribute("obj"); %>
<% ResultSet kbh =  (ResultSet) obj.getKbh(); %>
<% ResultSet dvkd = (ResultSet) obj.getDvkd();  %>
<% ResultSet vung = (ResultSet) obj.getVung(); %>
<% ResultSet bmList = (ResultSet) obj.getBmlist();%>

<% String nnId = (String)session.getAttribute("nnId"); %> 
<% if(nnId == null) {
	nnId = "vi"; 
	
	}	
String url = util.getChuyenNguUrl("BmSvl","",nnId);	
	%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">
<script type="text/javascript"	src="../scripts/jquery.min.1.7.js"></script>
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<script type="text/javascript" src="../scripts/phanTrang.js"></script>
<SCRIPT language="javascript" type="text/javascript">

function clearform()
{
	document.bmForm.bmTen.value = "";
    document.bmForm.DienThoai.value = "";  
    document.bmForm.kbhId.selectedIndex = 0;
    document.bmForm.dvkdId.selectedIndex = 0;
    document.bmForm.vungId.selectedIndex = 0;
    document.bmForm.TrangThai.selectedIndex = 2;
    submitform();    
}

function submitform()
{
	document.forms['bmForm'].action.value= 'search';
	document.forms['bmForm'].submit();
}
function xuatexcel()
{
	document.forms['bmForm'].action.value= 'excel';
	document.forms['bmForm'].submit();
}
function newform()
{
	document.forms['bmForm'].action.value= 'new';
	document.forms['bmForm'].submit();
}
function thongbao(){
	var tt = document.forms['bmForm'].msg.value;
	if(tt.length>0)
    alert(document.forms['bmForm'].msg.value);
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

<form name="bmForm" method="post" action="../../BmSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="action" value='1'>


<input type="hidden" name="msg" value='<%=obj.getMsg() %>'>

<script language="javascript" type="text/javascript">
    thongbao();
</script> 

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
		<TABLE width="100%" cellpadding="0" cellspacing="1">
			
				<TR>
				  <TD align="left"><table width="100%" border="0" cellpadding="0" cellspacing="0">

						  <tr height="20">
						   <TD align="left" colspan="2" class="tbnavigation">&nbsp;<%= " " + url %> </TD>
   
						   <TD colspan="2" align="right" class="tbnavigation"><%=ChuyenNgu.get("Ch??o m???ng",nnId) %> <%=userTen %>&nbsp;  </TD>
						  </tr>
 					  </table>
					</TD>
				</TR>

		</TABLE>
		<TABLE width="100%" cellpadding="0" cellspacing="0">		
				<TR>
					<TD>
					<TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
						<TR>
							<TD width="100%" align="center" >
							<FIELDSET>
							  <LEGEND class="legendtitle"><%=ChuyenNgu.get("Ti??u ch?? t??m ki???m",nnId) %> &nbsp;</LEGEND>
							<TABLE  width="100%" cellpadding="6" cellspacing="0">
							      <TR>  
							      		<TD width="24%" class="plainlabel"><%=ChuyenNgu.get("T??n gi??m ?????c mi???n (RSM)",nnId) %> </TD>
								        <TD class="plainlabel"><input type="text" name="bmTen" id="bmTen" value="<%= obj.getTen() %>" onChange="submitform();"></TD>
								    	<TD class="plainlabel"><%=ChuyenNgu.get("S??? ??i???n tho???i (??T)",nnId) %> </TD>
								    	<TD class="plainlabel"><input type="text" name="DienThoai" id="DienThoai" value="<%= obj.getDienthoai() %>" onChange="submitform();"></TD>
							      </TR>

								  <TR>
								    <TD class="plainlabel"><%=ChuyenNgu.get("K??nh b??n h??ng (KBH)",nnId) %> </TD>
								    <TD  class="plainlabel">
								    	<SELECT name="kbhId" onChange = "submitform();">
									    <option value=""></option> 
									      <% try{ while(kbh.next()){ 
									    	if(kbh.getString("kbhId").equals(obj.getKbhId())){ %>
									      		<option value='<%=kbh.getString("kbhId") %>' selected='selected'><%=kbh.getString("kbh") %></option>
									      	<%}else{ %>
									     		<option value='<%=kbh.getString("kbhId") %>' ><%=kbh.getString("kbh") %></option>
									     	<%}}}catch(java.sql.SQLException e){} %>
									     	
									    </SELECT>
									</TD>
								    <TD class="plainlabel"><%=ChuyenNgu.get("????n v??? Kinh doanh(??VKD)",nnId) %> </TD>
								    <TD width="20%" class="plainlabel">
								    <SELECT name="dvkdId" onChange = "submitform();">
									    <option value=""></option> 
									      <% try{ while(dvkd.next()){ 
									    	if(dvkd.getString("dvkdId").equals(obj.getDvkdId())){ %>
									      		<option value='<%=dvkd.getString("dvkdId") %>' selected='selected'><%=dvkd.getString("dvkd") %></option>
									      	<%}else{ %>
									     		<option value='<%=dvkd.getString("dvkdId") %>' ><%=dvkd.getString("dvkd") %></option>
									     	<%}}}catch(java.sql.SQLException e){} %>
									     	
									    </SELECT> 
									</TD>
								<TR>
						            <TD width="18%" class="plainlabel">V??ng</TD>
									<TD width="33%" class="plainlabel">
									<SELECT name="vungId"  onChange = "submitform()">
									<option value=""> </option>
									      <% try{ while(vung.next()){ 
									    	if(vung.getString("vungId").equals(obj.getVungId())){ %>
									      		<option value='<%=vung.getString("vungId") %>' selected='selected'><%=vung.getString("vung") %></option>
									      	<%}else{ %>
									     		<option value='<%=vung.getString("vungId") %>' ><%=vung.getString("vung") %></option>
									     	<%}}}catch(java.sql.SQLException e){} %>
                                	</SELECT>
									</TD>
						            
								    <TD class="plainlabel"><%=ChuyenNgu.get("Tr???ng th??i",nnId) %> </TD>
								    <TD  class="plainlabel"><SELECT name="TrangThai" onChange = "submitform();">
                                      
                                      <% if (obj.getTrangthai().equals("1")){%>
											  <option value="1" selected><%=ChuyenNgu.get("Ho???t ?????ng",nnId) %></option>
											  <option value="0"><%=ChuyenNgu.get("Ng??ng ho???t ?????ng",nnId) %></option>
											  <option value="2"></option>
											  
										<%}else if(obj.getTrangthai().equals("0")) {%>
											  <option value="0" selected><%=ChuyenNgu.get("Ng??ng ho???t ?????ng",nnId) %></option>
											  <option value="1" ><%=ChuyenNgu.get("Ho???t ?????ng",nnId) %></option>
											  <option value="2" ></option>
											  
										<%}else{%>																						  
											  <option value="1" ><%=ChuyenNgu.get("Ho???t ?????ng",nnId) %></option>
											  <option value="0" ><%=ChuyenNgu.get("Ng??ng ho???t ?????ng",nnId) %></option>
											  <option value="2" selected></option>
										<%}%>
                                    </SELECT></TD>
						      </TR>
						      
						      
							    <TR>
									<TD colspan="4" class="plainlabel">
									<a class="button2" href="javascript:clearform()">
											<img style="top: -4px;" src="../images/button.png" alt=""><%=ChuyenNgu.get("Nh???p l???i",nnId) %></a>&nbsp;&nbsp;&nbsp;&nbsp;
                                    </TD>
								</TR>

							</TABLE>

							</FIELDSET>
							</TD>
						</TR>
					</TABLE>
					</TD>
				</TR>

				<TR>

					<TD width="100%">
						<FIELDSET>
							<LEGEND class="legendtitle">&nbsp; <%=ChuyenNgu.get("Tr?????ng chi nh??nh",nnId) %> &nbsp;&nbsp;&nbsp;
							<%if(quyen[0]!=0){ %>
							<a class="button3" href="javascript:newform()">
    								<img style="top: -4px;" src="../images/New.png" alt=""><%=ChuyenNgu.get("T???o m???i",nnId) %> </a>    
							<%} %>
							
						</LEGEND>
	
						    <TABLE class="" width="100%">
						<TR>
							<TD width="98%">
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">

								<TR class="tbheader">
									<TH width="15%"><%=ChuyenNgu.get("M?? RSM",nnId) %> </TH>
									<TH width="15%"><%=ChuyenNgu.get("T??n RSM",nnId) %> </TH>
									<TH width="10%"><%=ChuyenNgu.get("S??? ??T",nnId) %> </TH>
									<TH width="10%"><%=ChuyenNgu.get("K??nh BH",nnId) %> </TH>
									<TH width="10%"><%=ChuyenNgu.get("??VKD",nnId) %></TH>
									<TH width="10%"><%=ChuyenNgu.get("V??ng",nnId) %></TH>
									<TH width="9%"><%=ChuyenNgu.get("Tr???ng th??i",nnId) %> </TH>
									<TH width="6%"><%=ChuyenNgu.get("Ng??y t???o",nnId) %></TH>
									<TH width="9%"><%=ChuyenNgu.get("Ng?????i t???o",nnId) %></TH>
									<TH width="6%"><%=ChuyenNgu.get("Ng??y s???a",nnId) %> </TH>
									<TH width="9%"><%=ChuyenNgu.get("Ng?????i s???a",nnId) %></TH>
									<TH width="8%" align="center">&nbsp; <%=ChuyenNgu.get("T??c v???",nnId) %></TH>
								</TR>

                                <%      
                                   
                                   
                                    int m = 0;
                                    String lightrow = "tblightrow";
                                    String darkrow = "tbdarkrow";
                                    
                                    if(bmList!=null)
                                    	try{
                                    		while (bmList.next()){
                                       
                                        	if (m % 2 != 0) {%>                     
                                            	<TR class= <%=lightrow%> >
                                        	<%} else { %>
                                            	<TR class= <%= darkrow%> >
                                        	<%}%>
                                        	 <TD align="left"><div align="left"><%= bmList.getString("smartid") %></div></TD>  
                                            	    <TD align="left"><div align="left"><%= bmList.getString("bmten") %></div></TD>                                   
                                                	<TD><div align="center"><%= bmList.getString("dienthoai")  %></div></TD>
                                                	<TD align="center"><%= bmList.getString("kbh") %></TD>
                                                	<TD align="center"><%= bmList.getString("dvkd") %></TD>
                                                	<TD align="center"><%= bmList.getString("vung") %></TD>
                                                	<% if (bmList.getString("trangthai").equals("1")){ %>
                                                    	<TD align="center"><%=ChuyenNgu.get("Ho???t ?????ng",nnId) %></TD>
                                                	<%}else{%>
                                                    	<TD align="center"><%=ChuyenNgu.get("Ng??ng ho???t ?????ng",nnId) %></TD>
                                                	<%}%>
                                                	<TD align="center"><%= bmList.getString("ngaytao") %></TD>
                                                	<TD align="center"><%= bmList.getString("nguoitao") %></TD>
                                                	<TD align="center"><%=bmList.getString("ngaysua")%></TD>
                                                	<TD align="center"><%= bmList.getString("nguoisua")%></TD>
                                                	<TD align="center">
                                                		<TABLE border = 0 cellpadding="0" cellspacing="0">
                                                    	<TR><TD>
                                                    	<%if(quyen[2]!=0){ %>
                                                    	<%-- 	<A href = "../../BmUpdateSvl?userId=<%=userId%>&update=<%= bmList.getString("bmid") %>">  --%>
                                                    		
                                                    		<A href = "../../RouterSvl?task=<%= Utility.dongMa(getServletContext().getInitParameter("RedirectNoScript") + 
                                                    				"BmUpdateSvl?userId=" + userId+ "&update="+ bmList.getString("bmid")) %>"><img src="../images/Edit20.png" alt="Cap nhat" title="C???p nh???t" width="20" height="20" longdesc="Cap nhat" border = 0></A>
                                                    	<%} %>
                                                    	</TD>
                                                    	<TD>&nbsp;</TD>
                                                    	<TD>
                                                    	<%if(quyen[1]!=0){ %>
                                                        	<%-- <A href = "../../BmSvl?userId=<%=userId%>&delete=<%= bmList.getString("bmid") %>;<%= bmList.getString("vungId")%>">  --%>
                                                        	
                                                        	
                                                        		<A href = "../../RouterSvl?task=<%= Utility.dongMa(getServletContext().getInitParameter("RedirectNoScript") + 
                                                    				"BmSvl?userId=" + userId+ "&delete="+ bmList.getString("bmid")) %>"> 
                                                    				<img src="../images/Delete20.png" alt="Xoa" title="X??a" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('B???n c?? mu???n x??a Tr?????ng chi nh??nh n??y?')) return false;"></A>
                                                    	<%} %>
                                                    	</TD>
                                                    	<TD>&nbsp;</TD>
                                                    	<TD>	
														<%if(quyen[3]!=0){ %>
														<%-- <A href = "../../BmUpdateSvl?userId=<%=userId%>&display=<%=bmList.getString("bmid") %>"> --%>
														<A href = "../../RouterSvl?task=<%= Utility.dongMa(getServletContext().getInitParameter("RedirectNoScript") + 
                                                    				"BmUpdateSvl?userId=" + userId+ "&display="+ bmList.getString("bmid")) %>"><img src="../images/Display20.png" alt="Hien thi" title="Hi???n th???" width="20" height="20" longdesc="Hien thi" border = 0></A>&nbsp;
														<%} %>
													</TD>
                                                    	
                                                    	</TR></TABLE>
                                                	</TD>
                                            	</TR>
                                		<%m++; }}catch(java.sql.SQLException e){}
                                		
                                		%>
                                
                                									
							</TABLE>
							</TD>
						</TR>
					</TABLE>

					</FIELDSET>
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
	if(kbh != null){ kbh.close(); kbh = null; }
	if(dvkd != null){ dvkd.close(); dvkd = null; }
	if(vung != null){ vung.close(); vung = null; }
	if(bmList != null){ bmList.close(); bmList = null; }
	
	obj.DBClose(); obj=null;
	session.setAttribute("obj","");
	
	
	}%>