<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.sql.SQLException"%>
<%@ page import = "java.util.Iterator" %>
<%@ page import = "java.util.List" %>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "java.sql.ResultSet" %>
<%@ page import = "geso.dms.distributor.beans.kehoachnhanvien.*" %>
<%@ page import = "geso.dms.center.util.*" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	geso.dms.center.util.Utility util = (geso.dms.center.util.Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>
<%	
	IKhaibaongaynghiList obj =(IKhaibaongaynghiList)session.getAttribute("obj");
	ResultSet dsach = obj.getNgaynghiRs();
	int[] quyen = new  int[6];
	if(!obj.getView().equals("TT"))
		quyen = util.Getquyen("KhaibaoNgaynghiSvl","",userId);
	else
		quyen = util.Getquyen("KhaibaoNgaynghiSvl","&view=TT",userId);
	System.out.println(quyen[Utility.THEM]);
	System.out.println(quyen[Utility.XOA]);
	System.out.println(quyen[Utility.SUA]);
	System.out.println(quyen[Utility.CHOT]);
	ResultSet AsmRs = obj.getAsmRs();
	ResultSet DdkdRs = obj.getDDkdRs();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<TITLE>Best - Project</TITLE>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">
	<LINK rel="stylesheet" href="../css/main.css" type="text/css">
	<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
	<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
	<script type="text/javascript" language="JavaScript" src="../scripts/Numberformat.js"></script>
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
	<script type="text/javascript" src="../scripts/phanTrang.js"></script>
	<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
	<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
	<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
	<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(document).ready(function() {		
				$( ".days" ).datepicker({			    
						changeMonth: true,
						changeYear: true				
				});            
	        }); 		
			
	</script>
<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script type="text/javascript">
	$(document).ready(function() {		
			$(".select2").select2();
        }); 		
		
</script>
	
	<SCRIPT language="javascript" type="text/javascript">
	function clearform()
	{ 
	    //document.forms['khtt'].manguongoc.value= "";
	    document.forms['khtt'].asmid.value= "";
	    document.forms['khtt'].thang.value = "";
		document.forms['khtt'].submit();
	}

	function submitform()
	{
		document.forms['khtt'].action.value= 'search';
		document.forms['khtt'].submit();
	}

	function newform()
	{
		document.forms['khtt'].action.value= 'new';
		document.forms['khtt'].submit();
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

<form name="khtt" method="post" action="../../KhaibaoNgaynghiSvl"> 
 <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="action" value="1" >
<input type="hidden" name="view" value="<%=obj.getView() %>" >
<input type="hidden"name="crrApprSplitting" value="<%= obj.getCrrApprSplitting() %>">
		<input type="hidden" name="nxtApprSplitting" value="<%= obj.getNxtApprSplitting() %>">
		<% obj.setNextSplittings(); %>
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%"> 
    <TR>
        <TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
        <TABLE width="100%" cellpadding="0" cellspacing="2">
            <TR>
                <TD align="left" class="tbnavigation">
                    <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
                        <TR height="22">
                            <TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý chỉ tiêu > Khai báo > Khai báo ngày nghỉ </TD>  
                            <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%= userTen %></TD>
                        </tr>
                        <tr>
                        	<td colspan="4"><fieldset>
								<legend class="legendtitle">Thông báo</legend>
								<textarea id="errors" value="<%= obj.getMsg() %>" 
								name="errors" rows="1" style="width: 100% ; color:#F00 ; font-weight:bold"></textarea>
							</fieldset></td>
                        </tr>
                    </TABLE>
                </TD>
            </TR>
        </TABLE>
        <TABLE width="100%" cellpadding="0" cellspacing="1">
            <TR>
                <TD>
                   <TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
                        <TR>
                            <TD width="100%" align="center" >
                            	 <%if(obj.getView().equals("TT")) { %>
                            <FIELDSET>
                              <LEGEND class="legendtitle">&nbsp;Tiêu chí tìm kiếm &nbsp;</LEGEND>
    					
                            <TABLE  width="100%" cellpadding="6" cellspacing="0">
                       
                             <TR>
                             	<TD width="20%" class="plainlabel" >Tháng </TD>
								<TD class="plainlabel">
									<select name="thang" onChange="submitform();">
									<option value="" selected></option>
									<%
									int k=1;
										for(k=1; k <= 12; k++ ){
											
										  if(obj.getThang().equals(Integer.toString(k) ) ) {
										%>
											<option value=<%= k %> selected="selected" > <%= k %></option> 
										<%  }else{  %>
											<option value=<%= k %> > <%= k %></option> 
										<% } }%> 
										
									 </select>
								</TD>
                             </TR >
                              <TR>
                             	<TD width="20%" class="plainlabel" >ASM </TD>
								<TD class="plainlabel">
									<select name="asmid" onChange="submitform();" class ="select2" style="width:250px">
									<option value="" selected ></option>
									<% if(AsmRs != null)
										while(AsmRs.next()) {
									%>
										<% if(obj.getAsmId().equals(AsmRs.getString("pk_seq"))){ %>
											<option value="<%=AsmRs.getString("pk_seq")%>" selected> <%=AsmRs.getString("mafast") +" - "+AsmRs.getString("Ten")%></option>
										 <% } else{ %>
										 	<option value="<%=AsmRs.getString("pk_seq")%>" > <%=AsmRs.getString("mafast") +" - "+AsmRs.getString("Ten")%></option>
										 <%} %>
										<% }  %>
										
									 </select>
								</TD>
                             </TR >
                              <TR>
                             	<TD width="20%" class="plainlabel" >Nhân viên bán hàng </TD>
								<TD class="plainlabel">
									<select name="ddkdid" onChange="submitform();" class ="select2" style="width:250px">
									<option value="" selected ></option>
									<% if(DdkdRs != null)
										while(DdkdRs.next()) {
									%>
										<% if(obj.getDdkdId().equals(DdkdRs.getString("pk_seq"))){ %>
											<option value="<%=DdkdRs.getString("pk_seq")%>" selected> <%=DdkdRs.getString("mafast") +" - "+DdkdRs.getString("Ten")%></option>
										 <% } else{ %>
										 	<option value="<%=DdkdRs.getString("pk_seq")%>" > <%=DdkdRs.getString("mafast") +" - "+DdkdRs.getString("Ten")%></option>
										 <%} %>
										<% }  %>
										
									 </select>
								</TD>
                             </TR >
                            <%-- <TR>
                             	<TD width="20%" class="plainlabel" >Trạng thái </TD>
								<TD class="plainlabel">
									<select name="trangthai" onChange="submitform();">
										<% if(obj.getTrangThai().equals("0")){ 
											<option value="0" selected>Chưa chốt</option>
											<option value="1">Đã chốt</option>
											<option value="2">Chưa duyệt</option>
											<option value=""> </option>
										<%} else { if( obj.getTrangThai().equals("1") ) { %>										
											<option value="0" >Chưa chốt</option>
											<option value="1" selected>Đã chốt</option>	
											<option value="2">Đã hủy</option>									
											<option value=""> </option>
										<% } else { if( obj.getTrangthai().equals("2") ) { %>
											<option value="2" selected="selected" >Đã hủy</option>	
											<option value="0" >Chưa chốt</option>
											<option value="1">Đã chốt</option>										
											<option value=""> </option>
										 <% } else { %> 
										 	<option value="" ></option>	
											<option value="0" >Chưa chốt</option>
											<option value="1">Đã chốt</option>										
											<option value="2">Đã hủy</option>
										 <% } }  }  %>
										
									 </select>
								</TD>
                             </TR >--%>
                              
                             <tr class="plainlabel"> 
                             	<td colspan="2" > 
                           			<a class="button2" href="javascript:clearform()">
										<img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;	
                             	</td> 
                             </tr>
                           
                            </TABLE>
							  <% } %>
                            </FIELDSET>
                            </TD>

                        </TR>
                    </TABLE> 
                    </TD>
                </TR>

                <TR>
                    <TD width="100%">
                        <FIELDSET>
                            <LEGEND class="legendtitle">&nbsp;Khai báo ngày nghỉ&nbsp;&nbsp;	
                            	<%if(quyen[Utility.THEM] != 0){ %>
                            	<a class="button3" href="javascript:newform()">
                           		<img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>      
                            	<%} %>  
                            </LEGEND>
    
                            <TABLE class="" width="100%">
                        <TR>
                            <TD width="98%">
                            <TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
                                <TR class="tbheader">
                                    <TH width="20%" align="center">Nhân viên bán hàng</TH>
                                    <TH width="5%" align="center">Tháng</TH>
                                    <TH width="5%" align="center">Năm</TH>
                                    <TH width="10%" align="center">Trạng thái</TH>
                                    <TH width="10%" align="center">Ngày tạo</TH>
                                    <TH width="10%" align="center">Người tạo</TH>
                                    <TH width="10%" align="center">Ngày sửa</TH>
                                    <TH width="10%" align="center">Người sửa</TH>
                                    <TH width="10%" align="center">Tác vụ</TH>
                                </TR>
                                <% 
                                   
                                    int m = 0;
                                    String lightrow = "tblightrow";
                                    String darkrow = "tbdarkrow";
                                    if(dsach != null)
                                    while (dsach.next()){
                                       
                                    	String tt = dsach.getString("trangthai").trim();
//                                     	if(obj.getView().equals("TT") && !tt.equals("0") )
//                                     	{
                                        if (m % 2 != 0) { %>                     
                                            <TR class= <%=lightrow%> >
                                        <%} else {%>
                                            <TR class= <%= darkrow%> >
                                        <%}%>
                                                <TD align="center"><%= dsach.getString("TEN") %></TD>
                                                <TD align="center"><%= dsach.getString("THANG") %></TD>  
                                                <TD align="center"><%= dsach.getString("NAM") %></TD>  
                                                <% if( tt.equals("0") ) { %>
                                                	<TD align="center">Chưa chốt</TD>
                                                <% } else if(tt.equals("1")) { %>       
                                                	<TD align="center">Đã chốt</TD>
                                                <%} else if(tt.equals("2")) { %>       
                                            	<TD align="center">Đã duyệt</TD>
                                            	<%} else{ %>
                                                	<TD align="center">Đã hủy</TD>
                                                <% } %> 
                                                	
                                                <TD align="center"><%= dsach.getString("NGAYTAO")%> </TD>
                                                <TD align="left"><%= dsach.getString("NGUOITAO")%></TD>
                                                <TD align="center"><%= dsach.getString("NGAYSUA")%> </TD>
                                                <TD align="left"><%= dsach.getString("NGUOISUA")%> </TD>
                                                <TD align="center"> 
                                                <% if(!obj.getView().equals("TT")){ %>
                                                <% if(tt.equals("0")|| tt.equals("1")) { %>
                                                	<%if(quyen[Utility.SUA] != 0 && tt.equals("0")){ %>
							                   			<A href = "../../KhaibaoNgaynghiUpdateSvl?userId=<%=userId%>&update=<%= dsach.getString("pk_seq") %>&view=<%= obj.getView() %>"><img src="../images/Edit20.png" alt="Cap nhat" width="20" height="20" longdesc="Cap nhat" border = 0></A> &nbsp;
							                   		<%}
							                   		if(quyen[Utility.XOA] != 0 && tt.equals("0")){ %>
														<A href = "../../KhaibaoNgaynghiSvl?userId=<%=userId%>&delete=<%= dsach.getString("pk_seq") %>&view=<%= obj.getView() %>"><img src="../images/Delete20.png" alt="Xoa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn muốn xóa nghỉ phép này?')) return false;"></A>
													<%}
							                   		if(quyen[Utility.CHOT] != 0 && tt.equals("0")){ %>
														<%if(tt.equals("0")){ %>
															<A href = "../../KhaibaoNgaynghiSvl?userId=<%=userId%>&chot=<%= dsach.getString("pk_seq") %>&view=<%= obj.getView() %>"><img src="../images/Chot.png" alt="Chot" width="20" height="20" longdesc="Chot" border=0 onclick="if(!confirm('Bạn muốn chốt nghỉ phép này?')) return false;"></A>
														<%}%>
															
														<%
							                   		}
							                   		
												}
												if(quyen[Utility.XEM] != 0 && (tt.equals("1") ||tt.equals("2"))  ){ %>
													<A href = "../../KhaibaoNgaynghiUpdateSvl?userId=<%=userId%>&display=<%=dsach.getString("pk_seq")%>&view=<%= obj.getView() %>"><IMG src="../images/Display20.png" alt="Hien thi" title="Hien thi" border=0></A>&nbsp;
													
												<% }  %>
												<%
													if(quyen[Utility.CHOT]!=0) {
														System.out.println("trang thai "+tt);
													if(!obj.getView().equals("TT") &&  tt.equals("1")){
														
														%>
													<A href = "../../KhaibaoNgaynghiSvl?userId=<%=userId%>&unchot=<%=dsach.getString("pk_seq")%>"><img src="../images/unChot.png" alt="Hien thi" width="20" height="20" title="Mở chốt" border = 0></A>
													<%} }%>
									<%} else
									{
										
										
										if(quyen[Utility.CHOT] != 0 ){ %>
										<%if(tt.equals("1") || tt.equals("0")){ %>
											<A href = "../../KhaibaoNgaynghiSvl?userId=<%=userId%>&duyet=<%= dsach.getString("pk_seq") %>&view=<%= obj.getView() %>"><img src="../images/Chot.png" alt="Chot" width="20" height="20" longdesc="Chot" border=0 onclick="if(!confirm('Bạn muốn duyệt nghỉ phép này?')) return false;"></A>
										<%} }
										if(quyen[Utility.XEM] != 0 && (tt.equals("1") ||tt.equals("2"))  ){ %>
										<A href = "../../KhaibaoNgaynghiUpdateSvl?userId=<%=userId%>&display=<%=dsach.getString("pk_seq")%>&view=<%= obj.getView() %>"><IMG src="../images/Display20.png" alt="Hien thi" title="Hien thi" border=0></A>&nbsp;
										<A href = "../../KhaibaoNgaynghiSvl?userId=<%=userId%>&delete=<%= dsach.getString("pk_seq") %>&view=<%= obj.getView() %>"><img src="../images/Delete20.png" alt="Xoa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn muốn xóa nghỉ phép này?')) return false;"></A>
									<%} %>
								
												
									
									<% } %>
							                    </TD>
                                            </TR>
                                          <% m++;  }%>  
                                      <tr class="tbfooter">
														<TD align="center" valign="middle" colspan="13"
															class="tbfooter">
															<%if(obj.getNxtApprSplitting() >1) {%> <img alt="Trang Dau"
															src="../images/first.gif" style="cursor: pointer;"
															onclick="View('khtt', 1, 'view')"> &nbsp; <%}else {%>
															<img alt="Trang Dau" src="../images/first.gif">
															&nbsp; <%} %> <% if(obj.getNxtApprSplitting() > 1){ %> <img
															alt="Trang Truoc" src="../images/prev.gif"
															style="cursor: pointer;"
															onclick="Prev('khtt', 'prev')"> &nbsp; <%}else{ %>
															<img alt="Trang Truoc" src="../images/prev_d.gif">
															&nbsp; <%} %> <%
													int[] listPage = obj.getNextSplittings();
													for(int i = 0; i < listPage.length; i++){
												%> <% 							
											
												if(listPage[i] == obj.getNxtApprSplitting()){ %> <a
															style="color: white;"><%= listPage[i] %>/ <%=obj.getTheLastSplitting() %></a>
															<%}else{ %> <a
															href="javascript:View('khtt', <%= listPage[i] %>, 'view')"><%= listPage[i] %></a>
															<%} %> <input type="hidden" name="list"
															value="<%= listPage[i] %>" /> &nbsp; <%} %> <% if(obj.getNxtApprSplitting() < obj.getTheLastSplitting()){ %>
															&nbsp; <img alt="Trang Tiep" src="../images/next.gif"
															style="cursor: pointer;"
															onclick="Next('khtt', 'next')"> &nbsp; <%}else{ %>
															&nbsp; <img alt="Trang Tiep" src="../images/next_d.gif">
															&nbsp; <%} %> <%if(obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {%>
															<img alt="Trang Cuoi" src="../images/last.gif">
															&nbsp; <%}else{ %> <img alt="Trang Cuoi"
															src="../images/last.gif" style="cursor: pointer;"
															onclick="View('khtt', -1, 'view')"> &nbsp; <%} %>
														</TD>
													</tr>
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
</BODY>
</html>
<% 	
	try
    {
		if(dsach != null)
			dsach.close();
		
		if(obj != null)
		{
			obj.DBclose();
			obj = null;
		}
		session.setAttribute("obj", null);
	}
	catch (SQLException e) {} %>
<%}%>