<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.beans.denghitratrungbay.IDeNghiTraTrungBay" %>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/IMV/index.jsp");
	}else{  
		Utility Util = new Utility();
		int[] quyen = new  int[5];
		quyen = Util.Getquyen("DuyettratrungbaySvl","",userId);
		
		%>

<% IDeNghiTraTrungBay dttbBean = (IDeNghiTraTrungBay)session.getAttribute("lsxBean"); %>
<% String schemeId = (String)session.getAttribute("schemeId"); %>
<% ResultSet scheme = (ResultSet)dttbBean.getCttbRs() ; %>
<% ResultSet npp = (ResultSet)dttbBean.getNppRs(); %>
<% ResultSet kh = (ResultSet)dttbBean.getKhRs() ; %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="java.sql.SQLException"%>
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">

<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<style type="text/css">

#tabc tbody tr input:HOVER{
	background: #C5E8CD;
}
#tabc tbody tr:hover{
	background: #C5E8CD;
}
</style>
<SCRIPT language="JavaScript" type="text/javascript">
function submitform()
{
	document.forms['dttbForm'].setAttribute('enctype', '', 0);
    document.forms['dttbForm'].submit();
}

function save()
{
	document.dttbForm.action.value='save';
	document.forms['dttbForm'].setAttribute('enctype', '', 0);
    document.forms['dttbForm'].submit();
}

function chot()
{
	document.dttbForm.action.value='chot';
	document.forms['dttbForm'].setAttribute('enctype', '', 0);
    document.forms['dttbForm'].submit();
}

function upload()
{
	document.forms['dttbForm'].setAttribute('enctype', "multipart/form-data", 0);
    document.forms['dttbForm'].submit();
}
function DinhDangTien(num) 
{
   num = num.toString().replace(/\$|\,/g,'');
   if(isNaN(num))
   num = "0";
   sign = (num == (num = Math.abs(num)));
   num = Math.floor(num*100+0.50000000001);
   num = Math.floor(num/100).toString();
   for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
   num = num.substring(0,num.length-(4*i+3))+','+
   num.substring(num.length-(4*i+3));
   return (((sign)?'':'-') + num);
}
function Dinhdang(element)
{

	element.value=DinhDangTien(element.value);
	if(element.value== '' )
	{
		element.value= '';
	}
	
}
</SCRIPT>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name ="dttbForm" ENCTYPE="multipart/form-data" method="post" action="../../DuyettratrungbaySvl" >
<input type="hidden" name="action" value="0">

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý trưng bày &gt; Duyệt trả trưng bày </TD> 
							 <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %>&nbsp;  </TD></tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
			<TR ><TD >
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR class = "tbdarkrow">
							<TD >&nbsp; </TD>						
													
						</TR>
					</TABLE>
			</TD></TR>
		</TABLE>
			<TABLE width="100%" border="0" cellpadding="0"  cellspacing="1" >
			  	<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>
				
	    				<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold"  style="width:100%" rows="1"><%= dttbBean.getMsg() %></textarea>

						</FIELDSET>
				   </TD>
				</tr>			

				<TR>
				  <TD height="100%" width="100%">
						<FIELDSET >
						<LEGEND class="legendtitle" style="color:black">Duyệt trả trưng bày</LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
						  <TR>
							  	<TD width="15%" class="plainlabel">Chương trình</TD>
						  	  	<TD class="plainlabel">
								      <% while(scheme.next()){ 
								    	 %>
								      		<%=scheme.getString("scheme") + "_" + scheme.getString("diengiai") %>
								      	<%} %>
						  	  	
						  	  	</TD>
						  </TR>
						  <TR>
						  	<TD width="15%" class="plainlabel">Nhà phân phối</TD>
						  	  	<TD class="plainlabel">
								      <% while(npp.next()){ 
								    	 %>
								      		<%=npp.getString("ma") + "_" + npp.getString("ten") %>
								      	<%} %>
						  	  	
						  	  	</TD>
						  </TR>
						</TABLE>
						</FIELDSET>
					</TD>
				</TR>
						 <TABLE id="tabc" border="0" cellpadding="1" cellspacing="1" width="100%">   
						<TR class="tbheader">
						  <TH width="15%" >Mã KH </TH>
						  <TH width="45%" >Tên khách hàng</TH>
						  <TH width="15%" >Đăng ký </TH>
						  <TH width="15%" >Đề nghị</TH>
						  <TH width="10%" >Duyệt</TH>
						 </TR>
						  <%NumberFormat formatter = new DecimalFormat("#,###,###"); %>
						  <% try{
							    String lightrow = "tblightrow";
								String darkrow = "tbdarkrow";
								int m = 0;
								if(kh != null){
									while(kh.next()){ 
									if (m % 2 != 0) {%>						
										<TR  class= <%=lightrow%> >
									<%} else {%>
										<TR   class= <%= darkrow%> >
									<%}%>
											<TD align="left"><div align="center"><%= kh.getString("KHACHHANG_FK")%></div></TD>                                   
											<TD align="left"><div align="left"><%=kh.getString("TEN") %> -- <%=kh.getString("DIACHI") %> </div></TD>    
											                               
							  				<TD align="left"><div align="left"><%=kh.getString("xuatdangky") %></div></TD>							  			
							  				<TD align="left"><div align="left"><%=kh.getString("xuatdenghi") %></div></TD>
							  				<%
							  				String xuatdengh=kh.getString("xuatdenghi");
							  				String xuatduyet=kh.getString("xuatduyet");
							  				if(xuatduyet.trim().length()!=0)
							  					xuatduyet=formatter.format(Double.parseDouble(xuatduyet));
							  				if(xuatduyet.equals("0"))
							  				{
							  					xuatduyet = formatter.format(Double.parseDouble(xuatdengh.trim()));
							  				}
							  				%>
							  				
							  				<TD align="left"> <input type="text" value=<%=kh.getString("xuatduyet")  %> >  </TD>
							  				
										</TR>
						  		<% m++ ;} 
						  		
						  		}%>		
						  		
						  <%}catch(java.sql.SQLException e){}%>
							<tr class="tbfooter"><td colspan="12" > &nbsp; </td></tr>
							</TABLE>
							</TABLE>
							</TD>
							</TR>
							</TABLE>
							</form>
							</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
							</HTML>							
<%}%>