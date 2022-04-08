<%@page import="java.sql.ResultSet"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.distributor.beans.catalog.*" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/DEMO2/index.jsp");
	}else{ %>

<% 	IcatalogList obj = (IcatalogList)session.getAttribute("obj"); %>
<% 	ResultSet nsplist = (ResultSet)obj.getCatalogList();
	
	int[] quyen = new  int[6];
	quyen = util.Getquyen("catalogSvl","",userId);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">

<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">


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

<SCRIPT language="JavaScript" type="text/javascript">
function submitform()
{
	 document.nspForm.action.value = "new";
   	 document.forms["nspForm"].submit();
}


function searchform()
{
	 document.nspForm.action.value = "search";
   	 document.forms["nspForm"].submit();
}

function clearform()
{
	document.nspForm.diengiai.value = "";
	document.nspForm.thanhvien.selectedIndex = 2;
    document.nspForm.trangthai.selectedIndex = 0;
    document.nspForm.lnhom.value = "";
	document.nspForm.tungay.value = "";
	document.nspForm.denngay.value = "";
 	document.forms["nspForm"].submit();
}

function xuatexcell()
{
 	document.forms['nspForm'].action.value= 'excel';
 	document.forms['nspForm'].submit();
 	document.forms['nspForm'].action.value= '';
}
function upload(){
	
		if(document.forms["nspForm"].filename.value==""){
			   
			  alert("Chưa tìm file upload lên. Vui lòng chọn file cần upload.");
		   }else{
			 document.forms["nspForm"].setAttribute('enctype', "multipart/form-data", 0);
			 document.forms["nspForm"].submit();	
		   }
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

<form name="nspForm" method="post" action="../../catalogSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%=userId%>'>
<input type="hidden" name="action" value="1">

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF"><!--begin body Dossier-->
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
				   		<table width="100%" border="0" cellpadding="0" cellspacing="0">
					  		<tr height="22">
						  		<TD align="left" colspan="2" class="tbnavigation">
						  			&nbsp;Dữ liệu nền &gt; Sản phẩm  &gt; Catalog</TD> 
						  		<TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %>&nbsp;  </TD>
						  	</tr>
						</table></TD>
			  	</TR>
			</TABLE>
			
			
			<TABLE width="100%" border = "0" cellpading = "0" cellspacing = "0">
				<TR>
					<TD width="100%">
					<FIELDSET>
					<LEGEND class="legendtitle">&nbsp;Catalog &nbsp;&nbsp;&nbsp;
					<%if(quyen[Utility.THEM]!=0) {%>
						<a class="button3" href="javascript:submitform()" >
    					<img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>   
    				<%} %>   
   <!-- 				&nbsp;&nbsp;&nbsp;
    				<a class="button3" href="javascript:xuatexcell()" >
    					<img style="top: -4px;" src="../images/New.png" alt="">Xuất excell </a>   
    			      
    			     &nbsp;&nbsp;&nbsp;
    				<a class="button3" href="javascript:upload()" >
    					<img style="top: -4px;" src="../images/New.png" alt="">Upload </a>   
    			     &nbsp;&nbsp;&nbsp;
    					<input type="file" name="filename" value=""/>   -->
    			   
    			                      		
					</LEGEND>
					<TABLE class="" width="100%">
						<TR>
							<TD width="98%">
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
								<TR class="tbheader">
									<TH width="8%">Mã Catalog </TH>
									<TH width="9%">Tên Catalog </TH>
									<TH width="9%">Nhãn hàng </TH>
									<TH width="9%">Chủng loại </TH>
									<TH width="8%">Ngày tạo </TH>
									<TH width="8%">Người tạo </TH>
									<TH width="9%">Ngày sửa</TH>
									<TH width="12%">Người sửa</TH> 
									<TH width="9%">Tác vụ</TH>
								</TR>
						<% 
							int m = 0;
							
							String lightrow = "tblightrow";
							String darkrow = "tbdarkrow";
							if(nsplist !=null)
							{
								while (nsplist.next()){
									if (m % 2 != 0) {%>						
										<TR class= <%=lightrow%> >
									<%} else {%>
										<TR class= <%= darkrow%> >
									<%} %>						
										<TD align="center"><%=nsplist.getString("MA")%></TD>				


									<TD><%=nsplist.getString("ten") %></TD>
									<TD><%=nsplist.getString("nhanhang_fk") %></TD>
									<TD><%=nsplist.getString("chungloai_fk") %></TD>
									
									<TD align="center"><%=nsplist.getString("ngaytao") %></TD>
									<TD align="center"><%=nsplist.getString("nguoitao") %></TD>
									<TD align="center"><%=nsplist.getString("ngaysua") %></TD>
									<TD align="center"><%=nsplist.getString("nguoisua") %> </TD>
									<TD align="center">
										<TABLE>
											<TR><TD>
											<%if(quyen[Utility.SUA]!=0 ) {%>
												<A href = "../../catalogUpdateSvl?userId=<%=userId%>&update=<%=nsplist.getString("pk_seq")%>"><img src="../images/Edit20.png" alt="Cap nhat" title="Cập nhật" width="20" height="20" longdesc="Cap nhat" border = 0></A>
											<%} %>
											
											</TD>
											<TD>&nbsp;</TD>
											<TD>
											<%if(quyen[Utility.XOA]!=0) {%>
												<A href = "../../catalogSvl?userId=<%=userId%>&delete=<%=nsplist.getString("pk_seq")%>"><img src="../images/Delete20.png" alt="Xoa" title="Xóa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn muốn xóa Catalog này?')) return false;"></A>
												<%} %> 
												</TD>
											</TR>												
										</TABLE>									
									</TD>
								</TR>
								<%m++; } }%>
							
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
</BODY>
</HTML>
<%}%>