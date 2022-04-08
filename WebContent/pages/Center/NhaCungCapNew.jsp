<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.beans.nhacungcap.INhacungcap" %>
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
		quyen = util.Getquyen("NhacungcapSvl","",userId);
		
	%>

<% INhacungcap nccBean = (INhacungcap)session.getAttribute("nccBean"); %>
<% String nnId = (String)session.getAttribute("nnId"); %> 
<% if(nnId == null) {
	nnId = "vi"; 
	}	
String url = util.getChuyenNguUrl("NhacungcapSvl","",nnId);
 
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

<SCRIPT language="javascript" type="text/javascript">
</SCRIPT>
<SCRIPT language="JavaScript" type="text/javascript">
<!--HPB_SCRIPT_CODE_40
function submitform()
{
	var sotaikhoan=document.getElementById("sotaikhoan");
	var dienthoai=document.getElementById("dienthoai");
	var fax=document.getElementById("fax");
	if(sotaikhoan.value=="")
		{
			alert("Bạn chưa nhập số tài khoản của Nhà Cung Cấp");
			return ;
		}
	if(dienthoai.value=="")
		{
			alert("Bạn chưa nhập số điện thoại của Nhà Cung Cấp");
			return ;
		}
	if(fax.value=="")
	{
		alert("Bạn chưa nhập số fax của Nhà Cung Cấp");
		return ;
	}
    document.forms["nccForm"].submit();
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

<form name="nccForm" method="post" action="../../NhacungcapNewSvl" charset="UTF-8">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<INPUT name="userId" type="hidden" value='<%=userId %>' size="30">
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#ffffff">
			<TABLE width="100%" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation" >

					   	<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							   <TD align="left" colspan="2" class="tbnavigation">
							   		<%=url %> &gt; Tạo mới </TD>
							   <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %> &nbsp; </td> 
						    </tr>
   
						</TABLE>
					</TD>
				</TR>
			</TABLE>	
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR><TD >
						<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
								<TR class = "tbdarkrow" >
									<TD width="30" align="left"><A href="javascript:history.back()"  ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
								    <TD width="2" align="left" ></TD>
								    <TD width="30" align="left" ><A href="javascript: submitform()" ><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A></TD>
									<TD >&nbsp; </TD>						
								</TR>
						</TABLE>
				</TD></TR>
			</TABLE>

				
			<TABLE width = 100% cellpadding = "3" cellspacing = "0" border = "0">
				  	<tr>
							<TD align="left" colspan="4" class="legendtitle">
								<FIELDSET>
								<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>
				
			    				<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold" style="width: 100%" readonly="readonly" rows="1" ><%=nccBean.getMessage()%></textarea>
								<%nccBean.setMessage(""); %>
								</FIELDSET>
						   </TD>
					</tr>
					<tr>
						   <TD align="left" colspan="4" class="legendtitle">
								<FIELDSET>
								<LEGEND class="legendtitle">Thông tin nhà cung cấp 
								</LEGEND>
								<TABLE  width="100%" cellspacing="0" cellpadding="6">
									<TR>
									  <TD class="plainlabel required" ><%=ChuyenNgu.get("Tên nhà cung cấp",nnId) %><FONT class="erroralert"></FONT></TD>
									  <TD class="plainlabel" ><INPUT name="TenNCC_long" style="width:300px" value ='<%=nccBean.getTen()%>'    type="text"></TD>
								  </TR>
									<TR>
									  <TD class="plainlabel required" ><%=ChuyenNgu.get("Tên viết tắt",nnId) %> <FONT class="erroralert"></FONT></TD>
									  <TD class="plainlabel" ><input type="text" style="width:300px"  name="TenNCC_short" value ='<%=nccBean.getTenviettat() %>' type="text"></TD>
								  </TR>
									<TR>
									  <TD class="plainlabel required" ><%=ChuyenNgu.get("Địa chỉ",nnId) %><FONT class="erroralert"></FONT></TD>
									  <TD class="plainlabel" ><INPUT name="DiaChi" value ='<%=nccBean.getDiachi() %>'  style="width:300px"   type="text"></TD>
								  </TR>
									<TR>
									  <TD class="plainlabel required" ><%=ChuyenNgu.get("Mã số thuế",nnId) %><FONT class="erroralert"></FONT></TD>
									  <TD class="plainlabel" ><INPUT name="MaSoThue" value ='<%=nccBean.getMasothue() %>' style="width:300px"    type="text"></TD>
								  </TR>
								  <TR>
									  <TD class="plainlabel required" ><%=ChuyenNgu.get("Số tài khoản",nnId) %><FONT class="erroralert"></FONT></TD>
									  <TD class="plainlabel " ><INPUT id="sotaikhoan" name="sotaikhoan" maxlength="100" value ='<%=nccBean.getSotaikhoan() %>' style="width:300px"   type="text"></TD>
								  </TR>
								  <TR>
									  <TD class="plainlabel required" ><%=ChuyenNgu.get("Điện thoại",nnId) %><FONT class="erroralert"></FONT></TD>
									  <TD class="plainlabel" ><INPUT id="dienthoai" name="dienthoai" maxlength="100" value ='<%=nccBean.getSotaikhoan() %>' style="width:300px"   type="text"></TD>
								  </TR>
								  <TR>
									  <TD class="plainlabel required" ><%=ChuyenNgu.get("Fax",nnId) %><FONT class="erroralert"></FONT></TD>
									  <TD class="plainlabel" ><INPUT id="fax" name="fax" maxlength="100" value ='<%=nccBean.getFax() %>' style="width:300px"   type="text"></TD>
								  </TR>
								   <TR>
									  <TD class="plainlabel" ><%=ChuyenNgu.get("Người liên hệ",nnId) %><FONT class="erroralert"></FONT></TD>
									  <TD class="plainlabel" ><INPUT name="nguoidaidien" maxlength="100" value ='<%=nccBean.getNguoidaidien()%>' style="width:300px"   type="text"></TD>
								  </TR>
									<TR>
										<TD width="24%" class="plainlabel" ><label>
										  <input name="trangthai" type="checkbox" value="1" checked >
									    Hoạt động </label></TD>
										<TD width="70%" class="plainlabel" >&nbsp;</TD>
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
nccBean.DBClose();
nccBean = null;
session.setAttribute("nccBean", null);
}
%>