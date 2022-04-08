<%@page import="java.sql.ResultSet"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dtl.center.beans.chamcong.IChamcong" %>
<%@ page  import = "geso.dtl.center.beans.chamcong.IChamcongList" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "geso.dtl.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/DTL/index.jsp");
	}else{ %>

<% IChamcongList obj = (IChamcongList)session.getAttribute("obj"); %>
<% //List<INhomthuong> nkmlist = (List<INhomthuong>)obj.getNkmList(); %>
<% ResultSet Dsnkm = (ResultSet)obj.getDsnkm(); 
	/* int[] quyen = new  int[5];
	quyen = util.Getquyen("62",userId); */
	
	int[] quyen = new  int[6];
	quyen = util.Getquyen("NhomthuongSvl","",userId);
	
	
	System.out.println(quyen[0]);
	System.out.println(quyen[1]);
	System.out.println(quyen[2]);
	System.out.println(quyen[3]);
	System.out.println(quyen[4]);%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>OPV - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">

<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
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

<script type="text/javascript" language="JavaScript" src="../scripts/simplecalendar.js"></script>
<SCRIPT language="JavaScript" type="text/javascript">
function submitform()
{
	 document.nkmForm.action.value = "new";
   	 document.forms["nkmForm"].submit();
}

function refresh()
{
	 document.nkmForm.action.value = "refresh";
   	 document.forms["nkmForm"].submit();
}

function searchform()
{
	 document.nkmForm.action.value = "search";
   	 document.forms["nkmForm"].submit();
}

function clearform()
{
	 	document.forms["nkmForm"].submit();
}

function xuatExcel()
{
	document.forms['nkmForm'].action.value= 'excel';
	document.forms['nkmForm'].submit();
	document.forms['nkmForm'].action.value= '';
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

<form name="nkmForm" method="post" action="../../ChamcongSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%=userId%>'>
<input type="hidden" name="action" value="1">

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
				   		<table width="100%" border="0" cellpadding="0" cellspacing="0">
					  		<tr height="22">
						  		<TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý chỉ tiêu &gt; Khai báo &gt; Chấm Công</TD> 
						  		<TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %>&nbsp;  </TD>
						  	</tr>
						</table></TD>
			  	</TR>
			</TABLE>
			
			<TABLE width="100%" border="0" cellpadding="0">
				<TR>
					<TD >
						<FIELDSET><LEGEND class="legendtitle">&nbsp;Tiêu chí tìm kiếm&nbsp;</LEGEND>
							<TABLE  width="100%" cellspacing="0" cellpadding="6">
								
								<TR>
									<TD width="19%" class="plainlabel">Diễn giải </TD>
								    <TD width="81%" class="plainlabel">
										<TABLE cellpadding="0" cellspacing="0">
											<TR><TD>
												<INPUT name="diengiai" type="text" size="40" value='<%=obj.getDiengiai()%>' onChange ="searchform();">
											</TD></TR>
										</TABLE>								
								</TR>
								<TR>
									<TD class="plainlabel">Trạng thái </TD>
								  	<TD  class="plainlabel"><SELECT name = "trangthai"  onChange = "searchform();">
								  	
								  	<% if (obj.getTrangthai().equals("0")){ %>
								      	<option value=""> </option>
								    	<option value="1">Đã chốt</option>
								    	<option value="0" selected>Chưa chốt</option>
									<%}else if (obj.getTrangthai().equals("1")){%>		
									  				
								  		<option value="1" selected>Đã chốt</option>
								    	<option value="0" >Chưa chốt</option>
									<%} else {%>
									   <option value="" selected> </option>
								    	<option value="1">Đã chốt</option>
								    	<option value="0" >Chưa chốt</option>
                                        <%} %>
										</SELECT></TD>
									
								</TR>
								
										<TR>
											<TD class="plainlabel" >Từ ngày </TD>
											<TD class="plainlabel" colspan="3">
												<TABLE cellpadding="0" cellspacing="0">
													<TR><TD>
														<input  class="days" type="text" name="tungay" value='<%=obj.getTungay() %>'  size="20" onchange = "searchform();">
													</TD>
													
													</TR>
												</TABLE>																					
		  									</TD>
										</TR>
										<TR>
                                          <TD class="plainlabel" >Đến ngày </TD>
										  <TD class="plainlabel" colspan="3">
										  		<TABLE cellpadding="0" cellspacing="0"><TR><TD>
										 <input  class="days" type="text" name="denngay" value='<%=obj.getDenngay() %>' size="20" onchange = "searchform();">
										  		</TD>
										

											  </TR>
											 </TABLE>
									  </TR>
								<TR>
									<TD class="plainlabel">
									
                                 <a class="button2" href="javascript:refresh()">
									<img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;	
									<a class="button2" href="javascript:xuatExcel()"> <img style="top: -4px;" src="../images/button.png" alt="">Xuất Excel </a>&nbsp;&nbsp;&nbsp;&nbsp;
                                    </TD>								
									<TD class="plainlabel">&nbsp;</TD>										
								</TR>
								
							</TABLE>
					  </FIELDSET>
					</TD>	
				</TR>
			</TABLE>
			
			<TABLE width="100%" border = "0" cellpading = "0" cellspacing = "0">
				<TR>
					<TD width="100%">
					<FIELDSET>
					<LEGEND class="legendtitle">&nbsp;Nhóm SKU &nbsp;&nbsp;&nbsp;
					<%if(quyen[Utility.THEM]!=0) {%>
					<a class="button3" href="javascript:submitform()">
    	<img style="top: -4px;" src="../images/New.png" >Tạo mới </a>                            
					<%} %>
					</LEGEND>
					<TABLE class="" width="100%">
						<TR>
							<TD width="98%">
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
								<TR class="tbheader">
									<TH width="5%">Mã nhóm</TH>
									<TH width="16%">Tên nhóm</TH>
									<TH width="9%">Diễn giải </TH>
									<TH width="8%">Từ ngày </TH>
									<TH width="8%">Đến ngày </TH>
									<TH width="8%">Trạng thái </TH>
									<TH width="8%">Ngày tạo </TH>
									<TH width="8%">Người tạo </TH>
									<TH width="8%">Ngày sửa</TH>
									<TH width="8%">Người sửa</TH>
									<TH width="20%">Tác vụ</TH>
								</TR>
						<% 
							IChamcong nkm = null;
						//	int size = nkmlist.size();
							int m = 0;
							String star = "";
							String lightrow = "tblightrow";
							String darkrow = "tbdarkrow";
							if(Dsnkm != null)
							while (Dsnkm.next()){				
								String type = Dsnkm.getString("type");
								if(type == null) type = "";
								else if(type.equals("4")) type = "SKU In";
								else if(type.equals("6")) type = "SKU Out";
								
								if (m % 2 != 0) {%>						
									<TR class= <%=lightrow%> >
								<%} else {%>
									<TR class= <%= darkrow%> >
								<%} %>										
															
									<TD align="left"><%=Dsnkm.getString("pk_seq") %></TD>
									<TD align="left"><%=Dsnkm.getString("ten") %></TD>
							
									<TD><%=Dsnkm.getString("diengiai") %></TD>
									<TD align="center"><%=Dsnkm.getString("tungay") %></TD>
									<TD align="center"><%=Dsnkm.getString("denngay") %></TD>

									<% if(Dsnkm.getString("trangthai").equals("1")) {%>
										<TD align="center">Đã chốt</TD>
									<%}else {%>
										<TD align="center">Chưa chốt</TD>
									<%} %>
									<TD align="center"><%=Dsnkm.getString("ngaytao") %></TD>
									<TD align="center"><%=Dsnkm.getString("nguoitao") %></TD>
									<TD align="center"><%=Dsnkm.getString("ngaysua") %></TD>
									<TD align="center"><%=Dsnkm.getString("nguoisua") %> </TD>
									<TD align="center">
									<% if(Dsnkm.getString("trangthai").equals("0")) { %>
											<%if(quyen[Utility.SUA]!=0) {%>	
										<A href = "../../NhomthuongUpdateSvl?userId=<%=userId%>&update=<%=Dsnkm.getString("pk_seq")%>">
											<img src="../images/Edit20.png" alt="Cap nhat" width="20" height="20" longdesc="Cap nhat" border = 0></A>&nbsp;
											<%} %>		
											<%if(quyen[Utility.CHOT]!=0){ %>					
										<A href = "../../NhomthuongSvl?userId=<%=userId%>&chot=<%= Dsnkm.getString("pk_seq") %>">
											<img src="../images/Chot.png" alt="Chot" width="20" height="20" longdesc="Chot" border = 0></A>
											<%} %>
									<%} else { %> 
											<%if(quyen[Utility.XEM]!=0){ %>
										<A href = "../../NhomthuongUpdateSvl?userId=<%=userId%>&display=<%=Dsnkm.getString("pk_seq")%>">
												<img src="../images/Display20.png" alt="Hien thi" width="20" height="20" longdesc="Hien thi" border = 0></A>&nbsp;
												<%} %>	
									<% } %>
									<%if(quyen[Utility.THEM]!=0) {%>	
										<A href = "../../NhomthuongUpdateSvl?userId=<%=userId%>&copy=<%=Dsnkm.getString("pk_seq")%>">
											<img src="../images/copy20.png" alt="Cap nhat" width="20" height="20" longdesc="Cap nhat" border = 0></A>&nbsp;
											<%} %>	
									<%if(quyen[Utility.XOA]!=0) {%>	
										<A href = "../../NhomthuongUpdateSvl?userId=<%=userId%>&delete=<%=Dsnkm.getString("pk_seq")%>">
											<img src="../images/Delete20.png" alt="Xoa" width="20" height="20" longdesc="Cap nhat" border = 0></A>&nbsp;
											<%} %>
									</TD>
								</TR>
							<%m++; }%>
							<TR class="tbfooter">
								<TD align="center" colspan="12"> |< < 1 to 20 of 100 > >|</TD>
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
if(Dsnkm!=null){
	Dsnkm.close();
	
}

}%>