<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.beans.dieuchuyenkhuyenmai.IDieuchuyenkhuyenmai" %>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>
<%@page import="java.sql.SQLException"%>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>

<% IDieuchuyenkhuyenmai dckmBean = (IDieuchuyenkhuyenmai)session.getAttribute("dckm"); %>
<% String schemeId = (String)session.getAttribute("schemeId"); %>
<% ResultSet scheme = (ResultSet)dckmBean.getSchemeRS() ; %>
<% ResultSet khuvuc = (ResultSet)dckmBean.getKv() ; %>
<% ResultSet vung = (ResultSet)dckmBean.getVung() ;  %>
<% ResultSet npp = (ResultSet)dckmBean.getNpp(); %>
 <% NumberFormat formatter = new DecimalFormat("#,###,###"); %>
 
 <% Hashtable<String, String> htDieuchuyen = (Hashtable<String, String>)dckmBean.getDieuchuyen(); %>
 
 <%
	int[] quyen = new  int[6];
	quyen = util.Getquyen("DieuchuyenkhuyenmaiSvl","", userId);
 %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<HTML>
<HEAD>
<TITLE>DuongBienHoa - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">

<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<script type="text/javascript"	src="../scripts/jquery.min.1.7.js"></script>
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
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
            $(".button").hover(function(){
                $(".button img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        }); 
		$(document).ready(function(){
            $(".button1").hover(function(){
                $(".button1 img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        });
		
    </script>
<SCRIPT language="JavaScript" type="text/javascript">
function submitform()
{
	document.forms['dckmForm'].submit();
}

function saveform()
{	
	document.dckmForm.action.value= "save";
	document.forms['dckmForm'].submit();
}

function dieuchuyen()
{

	document.forms['dckmForm'].action.value="adjust";
	
	document.forms['dckmForm'].submit();
}



function keypress(e) //Hàm dùng d? ngan ngu?i dùng nh?p các ký t? khác ký t? s? vào TextBox
{    
	var keypressed = null;
	if (window.event)
		keypressed = window.event.keyCode; //IE
	else
		keypressed = e.which; //NON-IE, Standard
	
	if (keypressed < 48 || keypressed > 57)
	{ 
		if (keypressed == 8 || keypressed == 127 || keypressed == 37 || keypressed == 39)
		{//Phím Delete và Phím Back
			return;
		}
		return false;
	}
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
	element.value = DinhDangTien(element.value);
	if(element.value== '' )
	{
		element.value= '';
	}
}	
function DinhDangDonGia(num) 
{
	num = num.toString().replace(/\$|\,/g,'');
	
	//num = (Math.round( num * 1000 ) / 1000).toString();
	
	var sole = '';
	if(num.indexOf(".") >= 0)
	{
		sole = num.substring(num.indexOf('.'));
	}
	
	if(isNaN(num))
		num = "0";
	sign = (num == (num = Math.abs(num)));
	num = Math.floor(num*100);
	num = Math.floor(num/100).toString();
	for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
		num = num.substring(0,num.length-(4*i+3)) + ',' + num.substring(num.length-(4*i+3));

	var kq;
	if(sole.length >= 0)
	{
		kq = (((sign)?'':'-') + num) + sole;
	}
	else
		kq = (((sign)?'':'-') + num);
	
	//alert(kq);
	return kq;
}


function replaces()
{
	var nppId = document.getElementsByName("nppId");
	var conlai = document.getElementsByName("conlai");
	var dieuchuyen = document.getElementsByName("dieuchuyen");
	var ngansach = document.getElementsByName("ngansach");
	var ngansachmoi = document.getElementsByName("ngansachmoi");
	var i;
	for(i = 0; i < nppId.length; i++)
	{
		var _dieuchuyen = dieuchuyen.item(i).value.replace(/,/g,"");
		if(_dieuchuyen == '')
			_dieuchuyen = '0';
		
		var _conlai = ngansach.item(i).value.replace(/,/g,"");
		if(_conlai == '')
			_conlai = '0';
		
		var _ngansachmoi = parseFloat(_conlai)+parseFloat(_dieuchuyen);
		
		ngansachmoi.item(i).value = DinhDangTien( parseFloat(_ngansachmoi));
	}	
	
	
	setTimeout(replaces, 300);
}

</SCRIPT>
<link href="../css/select2.css" rel="stylesheet" />
	<script src="../scripts/select2.js"></script>
	<script>
    	$(document).ready(function() { 
    		$("select").select2(); 
    	});
     
 	</script>

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

<% Csrf csdr=new Csrf(request,response,true,false,true);%>
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
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý khuyến mãi &gt; Điều chuyển ngân sách </TD> 
							 <TD colspan="2" align="right" class="tbnavigation">Chào mừng Bạn <%=userTen %>&nbsp;  </TD></tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
			<TR ><TD >
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR class = "tbdarkrow">
							<TD width="30" align="left"><A href="javascript:saveform();"  ><img src="../images/Save30.png" alt="Quay về"  title="Lưu lại" border="1" longdesc="Lưu lại" style="border-style:outset"></A></TD>

							<TD >&nbsp; </TD>						
						</TR>
					</TABLE>
			</TD></TR>
		</TABLE>
			<TABLE width="100%" border="0" cellpadding="0"  cellspacing="1" >
			  	<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Thông báo </LEGEND>
				
	    				<textarea name="dataerror" style="width: 100%" readonly="readonly"  rows="1"><%= dckmBean.getMessage() %></textarea>

						</FIELDSET>
				   </TD>
				</tr>			

				<TR>
				  <TD height="100%" width="100%">
						<FIELDSET>
						<LEGEND class="legendtitle" style="color:black">Điều chuyển ngân sách khuyến mãi</LEGEND>
						<TABLE border="0" width="100%" cellpadding="4" cellspacing="0">
						 <TR>
								<TD class="plainlabel">Từ ngày </TD>
								<TD class="plainlabel" >	
									<input type="text" name="tungay" id="tungay" class="days" value='<%= dckmBean.getTungay() %>' onchange="submitform();" />
								</TD>

								<TD class="plainlabel">Đến ngày </TD>
								<TD class="plainlabel" >
									<input type="text" name="denngay" id="denngay" class="days" value='<%= dckmBean.getDenngay() %>' onchange="submitform();" />
								</TD>
						 </TR>
						 <TR>
									<TD class="plainlabel">Vùng/Miền</TD>
									<TD class="plainlabel">
										<select name="vungId" id="vungId" onchange="submitform();" style ="width:200px">
											<option value="" selected>All</option>
											<%if (vung != null)
													while (vung.next()) {
														if (vung.getString("pk_seq").equals(dckmBean.getVungId())) {%>
													<option value="<%=vung.getString("pk_seq")%>" selected><%=vung.getString("diengiai")%></option>
												<%} else {%>
													<option value="<%=vung.getString("pk_seq")%>"><%=vung.getString("diengiai")%></option>
											<%}}%>
										</select>
									</TD>
									<TD class="plainlabel">Khu vực</TD>
									<TD class="plainlabel">
										<select name="khuvucId" id="khuvucId" onchange="submitform();" style ="width:200px">
											<option value="" selected>All</option>
											<%if (khuvuc != null)
													while (khuvuc.next()) {
														if (khuvuc.getString("pk_seq").equals(dckmBean.getKvId())) {%>
															<option value="<%=khuvuc.getString("pk_seq")%>" selected><%=khuvuc.getString("diengiai")%></option>
													<%} else {%>
														<option value="<%=khuvuc.getString("pk_seq")%>"><%=khuvuc.getString("diengiai")%></option>
													<%}}%>
										</select>
									</TD>
						</TR>
						  <TR>
							  	<TD width="20%" class="plainlabel">Chương trình</TD>
						  	  	<TD class="plainlabel" colspan="3">
						  	  		<input type="hidden" name="schemeoldid" id="schemeoldid" class="days" value='<%= dckmBean.getSchemeId() %>' />
									<SELECT name="schemeId" onchange="submitform();" style="width: 600px">
								    <option value=""></option>
								      <% try{while(scheme.next()){ 
								    	if(scheme.getString("pk_seq").equals(dckmBean.getSchemeId())){ %>
								      		<option value='<%=scheme.getString("pk_seq")%>' selected><%=scheme.getString("scheme") %></option>
								      	<%}else{ %>
								     		<option value='<%=scheme.getString("pk_seq")%>'><%=scheme.getString("scheme") %></option>
								     	<%}}}catch(java.sql.SQLException e){} %>	  
                        				</SELECT>						  	  	
						  	  	
						  	  	</TD>
						  </TR>

						<%--   <TR>						    
							    <TD class="plainlabel"><B></B>Khu vực </TD>
							    <TD class="plainlabel" >
							    <SELECT name="kvId" >
								    <option value=""></option>
								      <% try{while(kv.next()){ 
								    	if(kv.getString("pk_seq").equals(dckmBean.getKvId())){ %>
								      		<option value='<%=kv.getString("pk_seq")%>' selected><%=kv.getString("diengiai") %></option>
								      	<%}else{ %>
								     		<option value='<%=kv.getString("pk_seq")%>'><%=kv.getString("diengiai") %></option>
								     	<%}}}catch(java.sql.SQLException e){} %>	  
                        				</SELECT>			
                        		</TD>
                        		                        				
					      </TR> --%>
							<TR>							  	
						  	  	<TD class="plainlabel" colspan="5">
						  	  	<a class="button2"  href="javascript:submitform()">
	                       <img style="top: -4px;" src="../images/button.png" alt="">Cập nhật</a>&nbsp;&nbsp;&nbsp;&nbsp;	
						  	  	
						  	 
						  	  	</TD>
						  </TR>
								</TABLE>
						
				</FIELDSET>
				</TD>
				</TR>
			<TR>
							<TD height="100%" width="100%">
								<FIELDSET>
					<TABLE width="100%" border="0" cellpadding="0"  cellspacing="1" >
							<tr class="tbheader">
						  <TH width="10%" >Mã NPP </TH>
						  <TH width="35%" >Tên NPP</TH>						
						  <TH width="10%" >Ngân sách</TH>
						  <TH width="10%" >Đã sử dụng</TH>
						  <TH width="10%">Còn lại</TH>
						  <TH width="10%" >Ngân sách mới</TH>
						  <TH width="10%" >Điều chuyển (+-)</TH>
						  </tr>

						  <%
						  long dadung =0;
							long conlai =0;
						  try{
							    String lightrow = "tblightrow";
								String darkrow = "tbdarkrow";
								int m = 0;
								long rest = 0;
								if(npp != null)
								{
									String nppIdPrev="";
									String kvIdPrev ="";
									while(npp.next())
									{
										
										if(!nppIdPrev.trim().equals(npp.getString("kvTen").trim()))
		 						       	{ 
												nppIdPrev= npp.getString("kvTen");
												%>
											  <tr style="color:black ;font-weight: bold;font-size:12" >
											
											 <TD colspan="<%=4%>"  >
												 <%=" "+npp.getString("kvTen")%>   
											 </TD>  
											 </tr>
										<%} %>
										<%
										String dieuchuyen = htDieuchuyen.get(npp.getString("nppId")) != null ? htDieuchuyen.get(npp.getString("nppId")).split("__")[4] : "";
									if (m % 2 != 0) {%>						
										<TR class= <%=lightrow%> >
									<%} else {%>
										<TR class= <%= darkrow%> >
									<%}%>
											<TD align="left">
												<input type ="text" name ="nppMa" value="<%= npp.getString("nppMa")%>"   style="width: 100%; text-align: right;" readonly="readonly" >
												<input type ="hidden" name ="nppId" value="<%= npp.getString("nppId")%>"   style="width: 100%; text-align: right;" readonly="readonly" >
												<input type ="hidden" name ="ctkmId" value="<%= npp.getString("ctkmId")%>"   style="width: 100%; text-align: right;" readonly="readonly" >
											</TD>                                   
											<TD align="left">
												<input type ="text" name ="nppten" value="<%=npp.getString("nppten") %>"   style="width: 100%; text-align: right;" readonly="readonly" >											
											</TD>  											                               
							  				<TD align="left">							  					
							  					<input type ="text" name ="ngansach" value="<%=formatter.format(npp.getDouble("ngansach")) %>"   style="width: 100%; text-align: right;" readonly="readonly" >
							  				</TD>
							  				<TD align="left">
							  					<input type ="text" name ="dasudung" value="<%=formatter.format(npp.getDouble("dasudung")) %>"   style="width: 100%; text-align: right;" readonly="readonly" >
							  				</TD>
							  				<TD align="left">
							  					<input type ="text" name ="conlai" value="<%=formatter.format( npp.getDouble("ngansach")  - npp.getDouble("dasudung") ) %>"   style="width: 100%; text-align: right;" readonly="readonly" >
							  				</TD>
							  				<TD align="left">
							  					<input type ="text" name ="ngansachmoi" value=""   style="width: 100%; text-align: right;" readonly="readonly" >
							  				</TD>
							  				<TD align="left">
							  					<input type ="text" name ="dieuchuyen" value="<%=dieuchuyen %>"   style="width: 100%; text-align: right;"  >
							  				</TD>
							  			 </TR>
						  		<% m++ ;} 
						  		
						  		}%>		
						  		
						  <%}catch(java.sql.SQLException e){}%>
						<tr class="tbfooter">
						<td colspan="13">
						&nbsp;
				</td>
				</tr>
				</TABLE>
				</FIELDSET>
				</TD>
				</TR>
				</TABLE>
				</TD>
				</TR>
				</TABLE>
				<script type="text/javascript">
	replaces();
</script>
				
</form>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
			
<%
	if(scheme != null){ scheme.close(); scheme = null; }
	if(khuvuc != null){ khuvuc.close(); khuvuc = null; }
	if(vung != null){ vung.close(); vung = null; }
	if(npp != null){ npp.close(); npp = null; }
	if(htDieuchuyen != null){ htDieuchuyen.clear(); htDieuchuyen = null; }

	dckmBean.DBClose(); htDieuchuyen = null;
	session.setAttribute("dckm", null);
%>
</HTML>
<%}%>