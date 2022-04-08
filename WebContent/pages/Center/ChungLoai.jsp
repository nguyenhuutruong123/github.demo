<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.beans.chungloai.IChungloaiList" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%@ page  import = "geso.dms.center.util.ChuyenNgu" %>

<%	String userId 	= 	(String) 	session.getAttribute("userId");  
	String userTen 	= 	(String) 	session.getAttribute("userTen");  	
	String sum 		= 	(String) 	session.getAttribute("sum");
	Utility util 	= 	(Utility) 	session.getAttribute("util");
	
	if (!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	} else { 
		
		int[] quyen = new  int[6];
		quyen = util.Getquyen("ChungloaiSvl","",userId);
		
		System.out.println(quyen[0]);
		System.out.println(quyen[1]);
		System.out.println(quyen[2]);
		System.out.println(quyen[3]);	
		System.out.println(quyen[4]);
	%>

<% IChungloaiList 	obj 	= 	(IChungloaiList)session.getAttribute("obj"); %>
<% ResultSet 		cllist 	= 	(ResultSet)obj.getClList() ; %>
<% ResultSet 		nhList 	= 	(ResultSet)obj.getNhList(); %>
<% String nnId = (String)session.getAttribute("nnId"); %> 
<% if(nnId == null) {
	nnId = "vi";
	} 
	String url = util.getChuyenNguUrl("ChungloaiSvl","",nnId);
	%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<META http-equiv="Content-Style-Type" content="text/css">
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

<SCRIPT language="javascript" type="text/javascript">

function clearform()
{
    document.clForm.chungloai.value = "";
    document.clForm.nhId.selectedIndex = 0;
    document.clForm.tungay.value = "";
	document.clForm.denngay.value = "";       
    document.clForm.trangthai.selectedIndex = 0;
    submitform();
}

function submitform()
{
	document.forms['clForm'].action.value= 'search';
	document.forms['clForm'].submit();
}

function newform()
{
	document.forms['clForm'].action.value= 'new';
	document.forms['clForm'].submit();
}
function thongbao()
{var tt = document.forms['clForm'].msg.value;
	if(tt.length>0)
    alert(document.forms['clForm'].msg.value);
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

<form name="clForm" method="post" action="../../ChungloaiSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%=userId%>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="msg" value='<%=obj.getMsg() %>'>

<script language="javascript" type="text/javascript">
    thongbao();
</script> 

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF"><!--begin body Dossier-->
		<!--begin common dossier info---> <!--End common dossier info--->
			<TABLE width="100%" cellpadding="0" cellspacing="1">		
				<TR>
					<TD align="left">
					  <table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
						   <TD align="left" colspan="2" class="tbnavigation"><%=url %></TD>
   
						   <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %>&nbsp;</TD>
						  </tr>
 					  </table>
					</TD>
				</TR>
				<TR>
					<TD>
					<TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
						<TR>
							<TD width="100%" align="center" >
							<FIELDSET>
							  <LEGEND class="legendtitle">Tiêu chí tìm kiếm &nbsp;</LEGEND>
							<TABLE  width="100%" cellpadding="6" cellspacing="0">
								<tr>
								  	<TD class="plainlabel"><%=ChuyenNgu.get("Chủng loại",nnId) %></TD>
								    <TD class="plainlabel"><input type="text" name="chungloai" value="<%=obj.getCloai() %>" onChange="submitform();">
								    
								    
								    
								    </TD>
						      	</TR>
						      	<TR>
									<TD width="19%" class="plainlabel"><%=ChuyenNgu.get("Nhãn hàng",nnId) %></TD>
  								  	<TD class="plainlabel" >
										<TABLE cellpadding="0" cellspacing="0" border="0">
											<TR>
										  		<TD><SELECT  name="nhId" onChange="submitform();">
										  			<option value=""  ></option>
										  			<%try{ %>
											  			<%while(nhList.next()){ %>	
												  			<%if(nhList.getString("pk_seq").equals(obj.getNhId())){ %>
												  				<option value="<%=nhList.getString("pk_seq")%>" selected="selected"><%=nhList.getString("ten")%></option>
															<%}else {%>
																<option value="<%=nhList.getString("pk_seq")%>" ><%=nhList.getString("ten")%></option>	
																<%}} %>
													<%}catch(SQLException ex){} %>										                                           
                                              			</SELECT>
                                         			</TD>
											</TR>
										</TABLE>									
									</TD>

								</TR>
								<TR>
								  	<TD class="plainlabel" ><%=ChuyenNgu.get("Từ ngày",nnId) %></TD>
								  	<TD class="plainlabel" >
										<TABLE cellpadding="0" cellspacing="0">
											<TR><TD>
												<input class="days" type="text" name="tungay" size="20" value = "<%=obj.getTungay()%>" onchange="submitform();">
												</TD>
												
                                    		</TR>
										</TABLE>
									</TD>
								<TR>
								<TR>
									<TD class="plainlabel" ><%=ChuyenNgu.get("Đến ngày",nnId) %></TD>
								  	<TD class="plainlabel" >
							  			<TABLE cellpadding="0" cellspacing="0">
							  				<TR>
							  					<TD>
													<input class="days" type="text" name="denngay" size="20" value = "<%=obj.getDenngay()%>" onchange="submitform();">
												</TD>
												
                                     		</TR>
										</TABLE>
									</TD>
								</TR>
								<TR>
								    <TD class="plainlabel"><%=ChuyenNgu.get("Trạng thái",nnId) %></TD>
									<TD class="plainlabel"><select name="trangthai" onChange="submitform();">
											<option value="2"> </option>
										<% if (obj.getTrangthai().equals("1")){ %>
										  	<option value="1" selected ><%=ChuyenNgu.get("Hoạt động",nnId) %></option>
										  	<option value="0"><%=ChuyenNgu.get("Ngưng hoạt động",nnId) %></option>
										 <%}else{ %>
											<% if (obj.getTrangthai().equals("0")){ %>
										  		<option value="1"  ><%=ChuyenNgu.get("Hoạt động",nnId) %></option>
										  		<option value="0" selected><%=ChuyenNgu.get("Ngưng hoạt động",nnId) %></option>
										 	<%}else{ %>
												<option value="1"  ><%=ChuyenNgu.get("Hoạt động",nnId) %></option>
										  		<option value="0"  ><%=ChuyenNgu.get("Ngưng hoạt động",nnId) %></option>
										 	<%}}%>										 
										    </select>
						      	</TR>
							    <TR>
									<TD colspan="2" class="plainlabel">
									<a class="button2" href="javascript:clearform()">
	<img style="top: -4px;" src="../images/button.png" alt=""><%=ChuyenNgu.get("Nhập lại",nnId) %></a>&nbsp;&nbsp;&nbsp;&nbsp;
									
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
						<LEGEND class="legendtitle">&nbsp; Chủng loại &nbsp; &nbsp;&nbsp; 
						<%if(quyen[0]!=0){ %>
						<a class="button3" href="javascript:newform()">
    	<img style="top: -4px;" src="../images/New.png" alt=""><%=ChuyenNgu.get("Tạo mới",nnId) %></a>     
    					<%} %>                       
																		</LEGEND>
	
		   				<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
			  				<TR>
			  	   				<TD align="left" colspan="4" class="legendtitle">
									<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
										<TR class="tbheader">
										<TH width="13%"><%=ChuyenNgu.get("Mã",nnId) %></TH>
											<TH width="13%"><%=ChuyenNgu.get("Chủng loại",nnId) %></TH>
											<TH width="15%"><%=ChuyenNgu.get("Nhãn hàng",nnId) %></TH>
											<TH width="14%"><%=ChuyenNgu.get("Trạng thái",nnId) %></TH>
											<TH width="9%"><%=ChuyenNgu.get("Ngày tạo",nnId) %></TH>
											<TH width="15%"><%=ChuyenNgu.get("Người tạo",nnId) %></TH>
											<TH width="9%"><%=ChuyenNgu.get("Ngày sửa",nnId) %></TH>
											<TH width="14%"><%=ChuyenNgu.get("Người sửa",nnId) %></TH>
											<TH width="11%" align="center">&nbsp;<%=ChuyenNgu.get("Tác vụ",nnId) %></TH>
										</TR>
									
								<% 
								int m = 0;
								String lightrow = "tblightrow";
								String darkrow = "tbdarkrow";
								while (cllist.next()){
									System.out.println("cllist");
									if (m % 2 != 0) {%>						
										<TR class= <%=lightrow%> >
									<%} else {%>
										<TR class= <%= darkrow%> >
									<%}%>
											<TD align="left"><%=cllist.getString("ma") %></TD>   
											<TD align="left"><%=cllist.getString("chungloai") %></TD>                                   
											<TD><%=cllist.getString("nhanhang") %></TD>

									<% if (cllist.getString("trangthai").equals("1")){ %>
											<TD align="left"><%=ChuyenNgu.get("Hoạt động",nnId) %></TD>
									<%}else{%>
											<TD align="left"><%=ChuyenNgu.get("Ngưng hoạt động",nnId) %></TD>
									<%}%>

											<TD align="center"><%=cllist.getString("ngaytao") %></TD>
											<TD align="center"><%=cllist.getString("nguoitao")%></TD>
											<TD align="center"><%=cllist.getString("ngaysua") %></TD>
											<TD align="center"><%=cllist.getString("nguoisua") %> </TD>
											<TD align="center">
												<TABLE border = 0 cellpadding="0" cellspacing="0">
													<TR>
													<TD>
													<%if(quyen[3]!=0){ %>
                                                        <A href = "../../ChungloaiUpdateSvl?userId=<%=userId%>&display=<%=cllist.getString("clId")%>"><img src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" width="20" height="20" longdesc="Hiển thị" border = 0 "></A>
                                                    <%} %>
                                                    </TD>
                                                    <TD>&nbsp;</TD>
													<TD>
													<%if(quyen[2]!=0){ %>
															<A href = "../../ChungloaiUpdateSvl?userId=<%=userId%>&chId=<%=cllist.getString("clId")%>"><img src="../images/Edit20.png" alt="Chinh sua" title="Chỉnh sửa" width="20" height="20" longdesc="Chinh sua" border = 0></A>
														<%} %>
														</TD>
														<TD>&nbsp;&nbsp;</TD>
														<TD>
														<%if(quyen[1]!=0){ %>
															<A href = "../../ChungloaiSvl?userId=<%=userId%>&delete=<%=cllist.getString("clId")%>;<%=cllist.getString("nhId") %>"><img src="../images/Delete20.png" alt="Xoa" title="Xóa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn có muốn xóa chủng loại này không ?')) return false;"></A>
														<%} %>	
															</TD>
													</TR>
												</TABLE>											
											</TD>
										</TR>
									<% 	m++;
									}%>
							
										<TR>
											<TD height="26" colspan="11" align="center" class="tbfooter">&nbsp;	</TD>
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

<% if(nhList != null) nhList.close(); nhList = null; %>
<% if(cllist != null) cllist.close(); cllist = null; %>
<%
if(obj != null){
   obj.DBClose();
   obj = null;  
   session.setAttribute("obj", null);
}
	}%>