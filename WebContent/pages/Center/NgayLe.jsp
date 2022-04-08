<%@page import="geso.dms.center.beans.ngayle.INgayLeList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    

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
	}else{
		int[] quyen = new  int[6];
		quyen = util.Getquyen("NgayLeSvl","",userId);
		
		System.out.println(quyen[0]);
		System.out.println(quyen[1]);
		System.out.println(quyen[2]);
		System.out.println(quyen[3]);	
		System.out.println(quyen[4]);
		
%>

<% INgayLeList obj = (INgayLeList)session.getAttribute("obj"); %>
<% ResultSet ncclist = (ResultSet)obj.getNccList(); %>
<% String nnId = (String)session.getAttribute("nnId"); %> 
<% if(nnId == null) {
	nnId = "vi"; 

	}	
String url = util.getChuyenNguUrl("NgayLeSvl","",nnId);	
	%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
<LINK rel="stylesheet" href="../css/trung.css" type="text/css">

<script type="text/javascript" language="JavaScript" src="../scripts/simplecalendar.js"></script>
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
	<style type="text/css">
	
	</style>
	
	<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
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
        }); 		
		
</script>
<SCRIPT language="javascript" type="text/javascript">

function clearform()
{
    document.nccForm.nguoisua.value = "";
    document.nccForm.nguoitao.value = "";
    document.nccForm.ngaysua.value = "";
    document.nccForm.ngaytao.value = "";
    document.nccForm.ngayle.value = "";
    document.nccForm.diengiai.value = "";
    submitform();
}

function submitform()
{
	document.forms['nccForm'].action.value= 'search';
	document.forms['nccForm'].submit();
}

function newform()
{
	document.forms['nccForm'].action.value= 'new';
	document.forms['nccForm'].submit();
}
function thongbao()
{var tt = document.forms['nccForm'].msg.value;
	if(tt.length>0)
    alert(document.forms['nccForm'].msg.value);
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

<form name="nccForm" method="post" action="../../NgayLeSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%=userId%>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="msg" value='<%=obj.getMsg() %>'>
<input type="hidden" name="ngonnguId" value='<%=nnId%>'>

<script language="javascript" type="text/javascript">
    thongbao();
</script> 
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#ffffff">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" >
					   <table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							   <TD align="left" colspan="2" class="plainlabel">
							   		<%= " " + url %>
							   </TD>
							   <TD colspan="2" align="right" class="plainlabel"><%=ChuyenNgu.get("Chào mừng",nnId) %>  <%=userTen %> &nbsp;</td> 
						    </tr>
						</table>
					</TD>
				</TR>
				<TR>
					<TD>
					<TABLE border="0" width="100%" >
							<TR>
								<TD width="100%" align="left"><FIELDSET>
									<LEGEND class="legendtitle"><%= ChuyenNgu.get("Tiêu chí tìm kiếm",nnId) %></LEGEND>

									<TABLE class="tblight" width="100%" cellpadding="3" cellspacing = "0">
										
										
										
										<TR>							
											<TD class="plainlabel" ><%=ChuyenNgu.get("Ngày Tạo",nnId) %></TD>
											<TD class="plainlabel" colspan="3">
												<TABLE cellpadding="0" cellspacing="0">
													<TR><TD>
										<input type="text" class="days" name="ngaytao" value='<%=obj.getNgaytao() %>'  size="20" onchange=submitform(); >
												</TD>
												
		   										</TR>
												</TABLE>
																						
		  									</TD>
										</TR>
										<TR>
                                          <TD class="plainlabel" ><%=ChuyenNgu.get("Ngày Sửa",nnId) %> </TD>
										  <TD class="plainlabel" colspan="3">
										  		<TABLE cellpadding="0" cellspacing="0">
										  			<TR><TD>
										 <input class="days" type="text" name="ngaysua" value='<%=obj.getNgaysua() %>' size="20" onchange=submitform(); >
										  		</TD>

											  </TR>
											 </TABLE>
									  	</TR>
									  	<TR>
                                          <TD class="plainlabel" ><%=ChuyenNgu.get("Người tạo",nnId) %> </TD>
										  <TD class="plainlabel" colspan="3">
										  		<TABLE cellpadding="0" cellspacing="0">
										  			<TR><TD>
										 <input  type="text" name="nguoitao" value='<%=obj.getNguoitao() %>' size="20" onchange=submitform(); >
										  		</TD>

											  </TR>
											 </TABLE>
									  	</TR>
									  	<TR>
                                          <TD class="plainlabel" ><%=ChuyenNgu.get("Người Sửa",nnId) %> </TD>
										  <TD class="plainlabel" colspan="3">
										  		<TABLE cellpadding="0" cellspacing="0">
										  			<TR><TD>
										 <input  type="text" name="nguoisua" value='<%=obj.getNguoisua() %>' size="20" onchange=submitform(); >
										  		</TD>

											  </TR>
											 </TABLE>
									  	</TR>
									  	<TR>
                                          <TD class="plainlabel" ><%=ChuyenNgu.get("Ngày Lễ",nnId) %> </TD>
										  <TD class="plainlabel" colspan="3">
										  		<TABLE cellpadding="0" cellspacing="0">
										  			<TR><TD>
										 <input class="days" type="text" name="ngayle" value='<%=obj.getNgayle() %>' size="20" onchange=submitform(); >
										  		</TD>

											  </TR>
											 </TABLE>
									  	</TR>
									  	<TR>
                                          <TD class="plainlabel" ><%=ChuyenNgu.get("Diễn Giải",nnId) %> </TD>
										  <TD class="plainlabel" colspan="3">
										  		<TABLE cellpadding="0" cellspacing="0">
										  			<TR><TD>
										 <input type="text" name="diengiai" value='<%=obj.getDiengiai() %>' size="20" onchange=submitform(); >
										  		</TD>

											  </TR>
											 </TABLE>
									  	</TR>
									
									
										<TR>
                                             <TD class="plainlabel" >
                                              <a class="button2" href="javascript:clearform()">
												<img style="top: -4px;" src="../images/button.png" alt=""><%=ChuyenNgu.get("Nhập lại",nnId) %></a>&nbsp;&nbsp;&nbsp;&nbsp;	
                                              
                                            </TD>
                                            <TD class="plainlabel" colspan="5"></TD>
										</TR>
										
									</TABLE>
									</FIELDSET>
								</TD>	
							</TR>
						</TABLE>
					</TD>
				</TR>	
				
			    <TR>
					<TD align="left" >
						<FIELDSET>
							<LEGEND class="legendtitle">&nbsp;<%=ChuyenNgu.get("Nhà cung cấp",nnId) %> &nbsp;&nbsp;&nbsp;
							<%if(quyen[0]!=0){ %>
							<a class="button3" href="javascript:newform()">
    						<img style="top: -4px;" src="../images/New.png" alt=""><%=ChuyenNgu.get("Tạo mới",nnId) %> </a>   
    						<%} %>                         
						</LEGEND>
				
							<TABLE class="" width="100%" border="0" cellpadding="0" cellspacing="0">
								<TR>
									<TD width="98%">
										<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
											<TR class="tbheader">
											<TH width="9%"><%=ChuyenNgu.get("Ngày lễ",nnId) %></TH>
											<TH width="9%"><%=ChuyenNgu.get("Diễn giải",nnId) %></TH>
											<TH width="10%"><%=ChuyenNgu.get("Ngày tạo",nnId) %></TH>
											<TH width="8%"><%=ChuyenNgu.get("Ngày sửa",nnId) %></TH>
											<TH width="9%"><%=ChuyenNgu.get("Người tạo",nnId) %></TH>
											<TH width="9%"><%=ChuyenNgu.get("Người sửa",nnId) %></TH>
											<TH width="9%"><%=ChuyenNgu.get("Tác vụ",nnId) %></TH>
										
										</TR>
								<% 
									int m = 0;
									String lightrow = "tblightrow";
									String darkrow = "tbdarkrow";
								if(ncclist != null){
									while (ncclist.next()){
										if (m % 2 != 0) {%>						
											<TR class= <%=lightrow%> >
										<%} else {%>
											<TR class= <%= darkrow%> >
										<%}%>
												
											
												
											
												<%-- <TD align="center"><%=sotaikhoan%></TD> --%>
												<TD align="center"><%=ncclist.getString("ngayle")%></TD>
												<TD align="center"><%=ncclist.getString("diengiai")%></TD>
												<TD align="center"><%=ncclist.getString("ngaytao")%></TD>
												<TD align="center"><%=ncclist.getString("ngaysua")%></TD>
												<TD align="center"><%=ncclist.getString("nguoitao")%></TD>
												<TD align="center"><%=ncclist.getString("nguoisua")%></TD>
												<TD align="center">
												<TABLE border = 0 cellpadding="0" cellspacing="0">
													<TR><TD>
														<%if(quyen[2]!=0){ %>
														<A href = "../../RouterSvl?task=<%= util.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "NgayLeUpdateSvl?userId="+userId+"&update="+ncclist.getString("pk_seq")+"")%>"><img src="../images/Edit20.png" alt="Cap nhat" title="Cập nhật" width="20" height="20" longdesc="Cap nhat" border = 0></A>
													<%} %>
													</TD>
													<TD>&nbsp;</TD>
													<TD>
														<%if(quyen[1]!=0){ %>
														<A href = "../../RouterSvl?task=<%= util.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "NgayLeSvl?userId="+userId+"&delete="+ncclist.getString("pk_seq")+"")%>"><img src="../images/Delete20.png" alt="Xoa" title="Xóa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn có muốn xóa nhà cung cấp này ?')) return false; "></A>
														<%} %>
														</TD>
													</TR></TABLE>
												</TD>
											</TR>
								<%m++; }}%>
							
								<TR>
									<TD align="center" colspan="11" class="tbfooter">&nbsp;</TD>
								</TR>
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
if (ncclist != null){ ncclist.close(); ncclist = null ; }
	obj.DBClose();
	obj=null;
	session.setAttribute("obj", null);
}
%>