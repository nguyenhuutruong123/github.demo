<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.beans.donvidoluong.IDonvidoluong" %>
<%@ page  import = "geso.dms.center.beans.donvidoluong.IDonvidoluongList" %>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.ArrayList" %>
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
		quyen = util.Getquyen("DonvidoluongSvl", "",userId);
		
		System.out.println(quyen[0]);
		System.out.println(quyen[1]);
		System.out.println(quyen[2]);
		System.out.println(quyen[3]);	
		System.out.println(quyen[4]);
		
	%>

<% IDonvidoluongList obj = (IDonvidoluongList)session.getAttribute("obj"); %>
<% ResultSet donvilist = (ResultSet)obj.getDonvilist(); %>
<% String nnId = (String)session.getAttribute("nnId"); %> 
<% if(nnId == null) {
	nnId = "vi";
	} 
	String url = util.getChuyenNguUrl("DonvidoluongSvl","",nnId);
	%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print"
	href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">

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

<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<SCRIPT language="JavaScript" type="text/javascript">
function clearform()
{
	document.dvdlForm.dvdl.value = "";
    document.dvdlForm.diengiai.value = "";
    document.dvdlForm.tungay.value = "";
	document.dvdlForm.denngay.value = "";       
    document.dvdlForm.trangthai.selectedIndex = 0;
    submitform();
}

function submitform()
{
	document.forms['dvdlForm'].action.value= 'search';
	document.forms['dvdlForm'].submit();
}

function newform()
{
	document.forms['dvdlForm'].action.value= 'new';
	document.forms['dvdlForm'].submit();
}
function thongbao(){
	var tt = document.forms['dvdlForm'].msg.value;
	if(tt.length>0)
    	alert(document.forms['dvdlForm'].msg.value);
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

<form name="dvdlForm" method="post" action="../../DonvidoluongSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%=userId%>'>
<input type="hidden" name="action" value="1">
<input type="hidden" name="msg" value='<%=obj.getMsg() %>'>

<script language="javascript" type="text/javascript">
thongbao();
</script> 
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#ffffff">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" >
					   <table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							   <TD align="left" colspan="2" class="tbnavigation">
							   		<%=url %></TD>
							   <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %>&nbsp;  </TD> 
						    </tr>
						</table>
					</TD>
				</TR>
			</TABLE>
			<TABLE border="0" width="100%" cellpadding="0"  cellspacing="1">
							<TR>
								<TD width="100%" align="left"><FIELDSET>
									<LEGEND class="legendtitle">Tiêu chí tìm kiếm  </LEGEND>

									<TABLE class="tblight" width="100%" cellpadding="6" cellspacing = "0">
										<TR>
											<TD width="19%" class="plainlabel" ><%=ChuyenNgu.get("Đơn vị đo lường",nnId) %></TD>
										  <TD width="81%" class="plainlabel" ><INPUT name="dvdl" type="text" size="40" value="<%= obj.getDvdl() %>" onChange="submitform();"></TD>
										</TR>

										<TR>
											<TD width="19%" class="plainlabel" ><%=ChuyenNgu.get("Diễn giải",nnId) %></TD>
										  <TD width="81%" class="plainlabel" ><INPUT name="diengiai" type="text" size="40" value="<%= obj.getDiengiai()%>" onChange="submitform();"></TD>
										</TR>
										
										<TR>
											<TD width="19%" class="plainlabel" ><%=ChuyenNgu.get("Trạng thái",nnId) %></TD>
										    <TD width="81%" class="plainlabel" ><select name="trangthai" onChange="submitform();">
											<% if (obj.getTrangthai().equals("1")){%>
											  <option value="2" selected> </option>
											  <option value="1" selected><%=ChuyenNgu.get("Hoạt động",nnId) %></option>
											  <option value="0"><%=ChuyenNgu.get("Ngưng hoạt động",nnId) %></option>
											<%}else if(obj.getTrangthai().equals("0")) {%>
											  <option value="2" > </option>
											  <option value="0" selected><%=ChuyenNgu.get("Ngưng hoạt động",nnId) %></option>
											  <option value="1" ><%=ChuyenNgu.get("Hoạt động",nnId) %></option>
											<%}else{%>											
											  <option value="2" selected> </option>
											  <option value="1" ><%=ChuyenNgu.get("Hoạt động",nnId) %></option>
											  <option value="0" ><%=ChuyenNgu.get("Ngưng hoạt động",nnId) %></option>
											<%}%>
										      </select>
									      </TD>
										</TR>
										<TR>
											<TD class="plainlabel" ><%=ChuyenNgu.get("Từ ngày",nnId) %></TD>
											<TD class="plainlabel" colspan="3">
												<TABLE cellpadding="0" cellspacing="0">
													<TR><TD>
														<input class="days" type="text" name="tungay" value='<%=obj.getTungay() %>'  size="20" onchange="submitform();">
													</TD>
													
													</TR>
												</TABLE>																					
		  									</TD>
										</TR>
										<TR>
                                          <TD class="plainlabel" ><%=ChuyenNgu.get("Đến ngày",nnId) %></TD>
										  <TD class="plainlabel" colspan="3">
										  		<TABLE cellpadding="0" cellspacing="0"><TR><TD>
										 <input class="days" type="text" name="denngay" value='<%=obj.getDenngay() %>' size="20" onchange="submitform();">
										  		</TD>
										

											  </TR>
											 </TABLE>
									  </TR>
										<TR>
										    <TD class="plainlabel" colspan=4>
										    <a class="button2" href="javascript:clearform()">
	<img style="top: -4px;" src="../images/button.png" alt=""><%=ChuyenNgu.get("Nhập lại",nnId) %></a>&nbsp;&nbsp;&nbsp;&nbsp;
				</TD>
										</TR>
									</TABLE>
									</FIELDSET>
								</TD>	
							</TR>
				</TABLE>
				<TABLE width = 100% cellpadding="0" cellspacing="1">
			    <TR>
					<TD align="left" >
						<FIELDSET>
							<LEGEND class="legendtitle">&nbsp;Đơn vị đo lường sản phẩm &nbsp;&nbsp;
							<%if(quyen[0]!=0){ %>
							<a name="new" class="button3" href="javascript:newform()">
    	<img style="top: -4px;" src="../images/New.png" alt=""><%=ChuyenNgu.get("Tạo mới",nnId) %></a>  
							<%} %>
							
						</LEGEND>
				
							<TABLE class="" width="100%" border="0" cellpadding="0" cellspacing="0">
								<TR>
									<TD width="98%">
										<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
											<TR class="tbheader">
											    <TH width="14%"><%=ChuyenNgu.get("Đơn vị đo lường",nnId) %></TH>
											    <TH width="10%"><%=ChuyenNgu.get("Diễn giải",nnId) %></TH>
											    <TH width="16%"><%=ChuyenNgu.get("Tình trạng",nnId) %></TH>
												<TH width="9%"><%=ChuyenNgu.get("Ngày tạo",nnId) %></TH>
												<TH width="16%"><%=ChuyenNgu.get("Người tạo",nnId) %></TH>
												<TH width="9%"><%=ChuyenNgu.get("Ngày sửa",nnId) %></TH>
												<TH width="16%"><%=ChuyenNgu.get("Người sửa",nnId) %></TH>
												<TH width="10%"><%=ChuyenNgu.get("Tác vụ",nnId) %></TH>
											</TR>
										<% 
											int m = 0;
											String lightrow = "tblightrow";
											String darkrow = "tbdarkrow";
											while (donvilist.next()){
												if (m % 2 != 0) {%>						
													<TR class= <%=lightrow%> >
												<%} else {%>
													<TR class= <%= darkrow%> >
												<%}%>
		
												<TD><%= donvilist.getString("donvi") %></TD>
												<TD align="center"><%= donvilist.getString("diengiai") %></TD>
												<%if (donvilist.getString("trangthai").equals("1")){ %>
													<TD align="center"><%=ChuyenNgu.get("Hoạt động",nnId) %></TD>
												<%}else{ %>
													<TD align="center"><%=ChuyenNgu.get("Ngưng hoạt động",nnId) %></TD>
												<%} %>
												<TD align="center"><%= donvilist.getString("ngaytao") %></TD>
												<TD align="center"><%= donvilist.getString("nguoitao") %></TD>
												<TD align="center"><%= donvilist.getString("ngaysua") %></TD>
												<TD align="center"><%= donvilist.getString("nguoisua") %></TD>
												<TD align="center">
												<TABLE border = 0 cellpadding="0" cellspacing="0">
													<TR>
													<TD>
													<%if(quyen[3]!=0){ %>
                                                        <A href = "../../DonvidoluongUpdateSvl?userId=<%=userId%>&display=<%=donvilist.getString("pk_seq")%>"><img src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" width="20" height="20" longdesc="Hiển thị" border = 0 "></A>
                                                    <%} %>
                                                    </TD>
                                                    <TD>&nbsp;</TD>
													<TD>
													<%if(quyen[2]!=0){ %>
														<A href = "../../DonvidoluongUpdateSvl?userId=<%=userId%>&update=<%=donvilist.getString("pk_seq")%>"><img src="../images/Edit20.png" alt="Cap nhat" title="Cập nhật" width="20" height="20" longdesc="Cap nhat" border = 0></A>
													<%} %>
													</TD>
													<TD>&nbsp;</TD>
													<TD>
													<%if(quyen[1]!=0){ %>
														<A href = "../../DonvidoluongSvl?userId=<%=userId%>&delete=<%=donvilist.getString("pk_seq")%>"><img src="../images/Delete20.png" alt="Xoa" title="Xóa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn có muốn xóa đơn vị đo lường này ?')) return false;"></A>
													<%} %>
													</TD></TR>												
												</TABLE>												
												</TD>
											</TR>
										<%m++; }%>
									<TR>
									<TD align="center" colspan="12" class="tbfooter">&nbsp;</TD>
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

<%	if(donvilist != null){ donvilist.close(); donvilist = null; }
	obj.DBClose(); obj = null;
	session.setAttribute("obj", null);

	}%>