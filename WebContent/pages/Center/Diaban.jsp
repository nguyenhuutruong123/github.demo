<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "geso.dms.center.beans.diaban.IDiaban" %>
<%@ page  import = "geso.dms.center.beans.diaban.IDiabanList" %>
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

<% IDiabanList obj = (IDiabanList)session.getAttribute("obj"); %>
<% List<IDiaban> kvlist = (List<IDiaban>)obj.getDiabanList(); %>
<% ResultSet khuvucRs = (ResultSet)obj.getKhuvucRs();
	int[] quyen = new  int[6];
	quyen = util.Getquyen("DiabanSvl","",userId);
	
%>
<% String nnId = (String)session.getAttribute("nnId"); %> 
<% if(nnId == null) {
	 nnId = "vi"; 
 	} 
String url = util.getChuyenNguUrl("DiabanSvl","",nnId); 
 %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>OPV - Project</TITLE>

<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">

<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<SCRIPT language="javascript" type="text/javascript">
function clearform()
{
	
	document.kvForm.khuvuc.selectedIndex = 0;
    //document.kvForm.DienGiai.value = "";      
    document.kvForm.TrangThai.selectedIndex = 2;
    submitform();
}

function submitform()
{
	//alert("hello");
	document.forms['kvForm'].action.value= 'search';
	document.forms['kvForm'].submit();
	
}

function newform()
{
	document.forms['kvForm'].action.value= 'new';
	document.forms['kvForm'].submit();
}
function thongbao()
{
	var tt = document.forms['kvForm'].msg.value;
	if(tt.length>0)
    alert(document.forms['kvForm'].msg.value);
}

function xuatExcel()
{
	document.forms['kvForm'].action.value= 'excel';
 	document.forms['kvForm'].submit();
 	document.forms['kvForm'].action.value= '';
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

<form name="kvForm" method="post" action="../../DiabanSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="msg" value='<%=obj.getMsg() %>'>
<input type="hidden" name="ngonnguId" value='<%=nnId%>'>

<script language="javascript" type="text/javascript">
    thongbao();
</script> 
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">	
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
		<TABLE width="100%" cellpadding="0" cellspacing="1">
			
				<TR>
					<TD align="left">
					  <table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
						   <TD align="left" colspan="2" class="tbnavigation"><%=url %></TD>
   
						   <TD colspan="2" align="right" class="tbnavigation"><%=ChuyenNgu.get("Chào mừng",nnId)%> <%=userTen %>&nbsp;  </TD>
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
							
							  <LEGEND class="legendtitle"><%=ChuyenNgu.get("Tiêu chí tìm kiếm",nnId)%> &nbsp;</LEGEND>
						    
							<TABLE  width="100%" cellpadding="6" cellspacing="0">
								<TR>
								    <TD width="20%" class="plainlabel"><%=ChuyenNgu.get("Khu vực",nnId)%></TD>
								      <TD width="80%" class="plainlabel"><SELECT name="khuvuc" onChange = "submitform();">
								      	<option value='' ></option>
								        <% try{ while(khuvucRs.next()){ 
								    	if(khuvucRs.getString("pk_seq").equals(obj.getKhuvucId())){ %>
								      		<option value='<%= khuvucRs.getString("pk_seq") %>' selected> <%= khuvucRs.getString("Ten") %></option>
								      	<%}else{ %>
								     		<option value='<%= khuvucRs.getString("pk_seq") %>'> <%= khuvucRs.getString("Ten") %></option>
								     	<%}}}catch(java.sql.SQLException e){} %>
								      </SELECT></TD>
								</TR>
								  <TR>
								    <TD class="plainlabel"><%=ChuyenNgu.get("Trạng thái",nnId)%> </TD>
								    <TD class="plainlabel"><SELECT name="TrangThai" onChange = "submitform();" >
                                      <% if (obj.getTrangthai().equals("1")){%>
											  <option value="1" selected><%=ChuyenNgu.get("Hoạt động",nnId)%> </option>
											  <option value="0"><%=ChuyenNgu.get("Ngưng hoạt động",nnId)%></option>
											  <option value="2"> </option>
											  
											<%}else if(obj.getTrangthai().equals("0")) {%>
											  <option value="0" selected><%=ChuyenNgu.get("Ngưng hoạt động",nnId)%></option>
											  <option value="1" ><%=ChuyenNgu.get("Hoạt động",nnId)%></option>
											  <option value="2" > </option>
											  
											<%}else{%>																						  
											  <option value="1" ><%=ChuyenNgu.get("Hoạt động",nnId)%></option>
											  <option value="0" ><%=ChuyenNgu.get("Ngưng hoạt động",nnId)%></option>
											  <option value="2" selected> </option>
											<%}%>
											
                                    </SELECT></TD>
						      </TR>
							    <TR>
									<TD colspan="2" class="plainlabel">
									<a class="button2" href="javascript:clearform()"> <img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;
									<!-- <a class="button2" href="javascript:xuatExcel()"> <img style="top: -4px;" src="../images/button.png" alt="">Xuất Excel </a> -->
									
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
			<TABLE width="100%" cellpadding="0" cellspacing="0">			
				<TR>
					<TD width="100%">
						<FIELDSET>
							<LEGEND class="legendtitle">&nbsp;<%=ChuyenNgu.get("Khu vực",nnId)%> &nbsp;&nbsp;
							<%if(quyen[Utility.THEM]!=0) {%>
									<a class="button3" href="javascript:newform()">
    	<img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>                            
							<%} %>
						</LEGEND>
	
						    <TABLE class="" width="100%">
						<TR>
							<TD width="98%">
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
								<TR class="tbheader">
									<TH width="4%"><%=ChuyenNgu.get("STT",nnId)%></TH>
									<TH width="13%"><%=ChuyenNgu.get("Địa bàn",nnId)%></TH>
									<TH width="13%"><%=ChuyenNgu.get("Diễn giải",nnId)%> </TH>
									<TH width="13%"><%=ChuyenNgu.get("Khu vực",nnId)%> </TH>
									<TH width="8%"><%=ChuyenNgu.get("Trạng thái",nnId)%> </TH>
									<TH width="6%"><%=ChuyenNgu.get("Ngày tạo",nnId)%> </TH>
									<TH width="10%"><%=ChuyenNgu.get("Người tạo",nnId)%> </TH>
									<TH width="6%"><%=ChuyenNgu.get("Ngày sửa",nnId)%></TH>
									<TH width="10%"><%=ChuyenNgu.get("Người sửa",nnId)%> </TH>
									<TH width="6%" align="center">&nbsp;<%=ChuyenNgu.get("Tác vụ",nnId)%></TH>
								</TR>
							
								<% 
									IDiaban kv = null;
									int size = kvlist.size();
									int m = 0;
									String lightrow = "tblightrow";
									String darkrow = "tbdarkrow";
									while (m < size){
										kv = kvlist.get(m);
										if (m % 2 != 0) {%>						
											<TR class= <%=lightrow%> >
										<%} else {%>
											<TR class= <%= darkrow%> >
										<%}%>
												<TD align="center"><%=m+1%></TD>
												<TD align="left"><div align="left"><%= kv.getTen() %></div></TD>
											    <TD align="left"><div align="left"><%= kv.getDiengiai() %></div></TD>
												<TD><div align="center"><%= kv.getKhuvucTen() %></div></TD>                               
												<% if (kv.getTrangthai().equals("Hoat dong")){ %>
													<TD align="center">Hoạt động </TD>
												<%}else{%>
													<TD align="center">Ngưng hoạt động</TD>
												<%}%>
												<TD align="center"><%= kv.getNgaytao() %></TD>
										 		<TD align="center"><%= kv.getNguoitao() %></TD>
												<TD align="center"><%= kv.getNgaysua() %></TD>
												<TD align="center"><%= kv.getNguoisua()%></TD>
												<TD align="center">
												<TABLE border = 0 cellpadding="0" cellspacing="0">
													<TR><TD>
													<%if(quyen[Utility.SUA]!=0) {%>
														<A href = "../../DiabanUpdateSvl?userId=<%=userId%>&update=<%=kv.getId()%>"><img src="../images/Edit20.png" alt="Cap nhat" title="Cập nhật" width="20" height="20" longdesc="Cap nhat" border = 0></A>
													<%} %>
													</TD>
													<TD>&nbsp;</TD>
													<TD>
													<%if(quyen[Utility.XOA]!=0) {%>
														<A href = "../../DiabanSvl?userId=<%=userId%>&delete=<%=kv.getId()%>"><img src="../images/Delete20.png" alt="Xoa" title="Xóa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn có muốn xóa Địa bàn này ?')) return false;"></A></TD>
													<%} %>
													</TR></TABLE>
												</TD>
											</TR>
								<%m++; }%>
								<TR>	
									<TD height="" colspan="11" align="center" class="tbfooter">
									&nbsp;</TD>
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

</BODY>
</HTML>
<%
	obj.closeDB();
	for(int i = 0; i < kvlist.size(); i++){
		kv = kvlist.get(i);
		kv.closeDB();
	}
}%>