<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@page import="geso.dms.distributor.util.Utility"%>
<%@ page  import = "geso.dms.center.beans.donhangip.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>


<% HttpSession s = request.getSession();
   if (s.isNew()){
	   s.invalidate();
	   System.out.println("New session");
   }else{
	   System.out.println("Old session");
   }
%>
<% 
	NumberFormat formatter = new DecimalFormat("#,###,###");
   NumberFormat formatter2 = new DecimalFormat("#,###,##0.000"); 
	   	  
%>

<% IDonhangListIP obj = (IDonhangListIP)s.getAttribute("obj"); %>
<% ResultSet dhlist = (ResultSet)obj.getDonhangRs(); %>
<% ResultSet ddkd = (ResultSet)obj.getDaidienkd(); %>
<% String userId = (String) s.getAttribute("userId");  %>
<% Utility Util = new Utility();

 int active=(Integer)session.getAttribute("active");
 

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">

<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
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

<SCRIPT language="javascript" type="text/javascript">

 function submitform()
 {
 	document.forms['dhForm'].action.value= 'search';
 	document.forms['dhForm'].submit();
 }
 function newform()
 {
 	document.forms['dhForm'].action.value= 'new';
 	document.forms['dhForm'].submit();
 }
 function duyetform(Id)
 {
 	document.forms['dhForm'].action.value= 'XK';
 	 document.forms['dhForm'].dhId.value = Id;
 	document.forms['dhForm'].submit();
 }
 function clearform()
 {   
	document.forms['dhForm'].tungay.value= '';
	document.forms['dhForm'].denngay.value= '';
	document.forms['dhForm'].trangthai.value ='';
	document.forms['dhForm'].khachhang.value ='';
	document.forms['dhForm'].sodonhang.value ='';
	document.forms['dhForm'].tumuc.value ='';
	document.forms['dhForm'].denmuc.value ='';
	document.forms['dhForm'].ddkdTen.selectedIndex = 0; 
	submitform();
 }
 
 function Next()
 {
 	document.forms['dhForm'].action.value= 'next';
 	document.forms['dhForm'].submit();
 }

 function Prev()
 {
 	document.forms['dhForm'].action.value= 'prev';
 	document.forms['dhForm'].submit();
 }

 function XemTrang(page)
 {
 	document.forms['dhForm'].action.value= 'view';
 	document.forms['dhForm'].currentPage.value = page;
 	document.forms['dhForm'].submit();
 }
 function processing(id,chuoi){
	 document.getElementById(id).innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Proc...</a>";
	 document.getElementById(id).href = chuoi;
	  
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
 function thongbao()
 {   var tt = document.forms['dhForm'].msg.value;
 	if(tt.length>0)
     	alert(document.forms['dhForm'].msg.value);
 	
 	document.forms['dhForm'].msg.value= '';
 }
 function InALL()
 {
	 var donhangIds = document.getElementsByName("IndonhangIds");
	 var _dhIds = '';
	 var kt = 0;
	 
	 
		 for(var i=0; i < donhangIds.length; i++){
			 if(donhangIds[i].checked == true)
			{
				 kt = 1;
			}
		 }
	 if(kt == 0 )
	 {
		 alert('Vui l??ng ch???n ????n h??ng');
		 return;
	 }		
	 
	 
	 var r = confirm("B???n ch???c ch???n mu???n in t???t c??? ????n h??ng? ");
	 if (r == false) {		 
	     return;
	 }
	 else
		{
		 	document.forms["dhForm"].action.value = "InALL";
		 	document.forms["dhForm"].submit(); 
		
		}
	 
	 
 }
 function ChotALL()
 {
	 var donhangIds = document.getElementsByName("donhangIds");
	 var _dhIds = '';
	 var kt = 0;
 	var donhangIds = document.getElementsByName("donhangIds");
	 
	 
		 for(var i=0; i < donhangIds.length; i++){
			 if(donhangIds[i].checked == true)
			{
				 kt = 1;
			}
		 }
	 if(kt == 0 )
	 {
		 alert('Vui l??ng ch???n ????n h??ng');
		 return;
	 }		
	 
	 
	 var r = confirm("B???n ch???c ch???n mu???n duy???t t???t c??? ????n h??ng? ");
	 if (r == false) {		 
	     return;
	 }
	 else
		{
		 	document.forms["dhForm"].action.value = "chotALL";
		 	document.forms["dhForm"].submit(); 
		
		}
	 
	 
 }
 /* function DuyetALL()
 {
	 var donhangIds = document.getElementsByName("duyetdonhangIds");
	 var _dhIds = '';
	 var kt = 0;
 	var donhangIds = document.getElementsByName("duyetdonhangIds");
	 
	 
		 for(var i=0; i < donhangIds.length; i++){
			 if(donhangIds[i].checked == true)
			{
				 kt = 1;
			}
		 }
	 if(kt == 0 )
	 {
		 alert('Vui l??ng ch???n ????n h??ng');
		 return;
	 }		
	 
	 
	 var r = confirm("B???n ch???c ch???n mu???n duy???t t???t c??? ????n h??ng? ");
	 if (r == false) {		 
	     return;
	 }
	 else
		{
		 	document.forms["dhForm"].action.value = "duyetALL";
		 	document.forms["dhForm"].submit(); 
		
		}
	 
	 
 } */
 function checkALL()
 {
	 var chkALL = document.getElementById("chkALL");
	 var donhangIds = document.getElementsByName("donhangIds");
	 
	 if(chkALL.checked == true )
	 {
		 for(var i=0; i < donhangIds.length; i++){
			 donhangIds[i].checked = true;
		 }
	 }
	 else
	 {
		 for(var i=0; i < donhangIds.length; i++){
			 donhangIds[i].checked = false;
		 }
	 }
 }
 function checkALLDuyet()
 {
	 var chkALL = document.getElementById("duyetchkALL");
	 var donhangIds = document.getElementsByName("duyetdonhangIds");
	 
	 if(chkALL.checked == true )
	 {
		 for(var i=0; i < donhangIds.length; i++){
			 donhangIds[i].checked = true;
		 }
	 }
	 else
	 {
		 for(var i=0; i < donhangIds.length; i++){
			 donhangIds[i].checked = false;
		 }
	 }
 }
 function chekALL()
 {
	 var chkALL = document.getElementById("InALL");
	 var donhangIds = document.getElementsByName("IndonhangIds");
	 
	 if(chkALL.checked == true )
	 {
		 for(var i=0; i < donhangIds.length; i++){
			 donhangIds[i].checked = true;
		 }
	 }
	 else
	 {
		 for(var i=0; i < donhangIds.length; i++){
			 donhangIds[i].checked = false;
		 }
	 }
 }
</SCRIPT>

<link href="../css/select2.css" rel="stylesheet" />
	<script src="../scripts/select2.js"></script>
	<script>
    	$(document).ready(function() { 
    		$("select:not(.notuseselect2)").select2({ width: 'resolve' });     
    	});
    </script>	

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

<form name="dhForm" method="post" action="../../DonhangIPSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="nppId" value="<%=obj.getNppId()%>" >
<input type="hidden" name="dhId" id="dhId"  >
<input type="hidden" name="action" value="1">
<input type="hidden" name="msg" value='<%= obj.getMsg() %>'>
<input type="hidden" name="currentPage" value="<%= obj.getCurrentPage() %>" >
<script language="javascript" type="text/javascript">
    thongbao();
</script>
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF"><!--begin body Dossier-->
			<TABLE width="100%" border="0" cellpadding="0">
				<TR>
					<TD align="left" class="tbnavigation">
					   <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							   <TD align="left" colspan="2" class="tbnavigation"> &nbsp;Qu???n l?? b??n h??ng > B??n h??ng cho kh??ch h??ng
							   <TD colspan="2" align="right" class="tbnavigation">Ch??o m???ng b???n  <%= obj.getNppTen() %>&nbsp;</tr>
						</TABLE>
					</TD>
		  </TR>
			<TR>
				<TD >
				<FIELDSET><LEGEND class="legendtitle">&nbsp;Ti??u ch?? t??m ki???m</LEGEND>
							<TABLE width="100%" cellpadding="6" cellspacing="0">
								
								<TR>
									<TD width="21%" class="plainlabel">Nh??n vi??n b??n h??ng </TD>
									<TD class="plainlabel">
									<SELECT name="ddkdTen" onChange="submitform();">
										  <option value=""> </option>
										  <% if(ddkd != null){
										  	try{ while(ddkd.next()){ 
								    			if(ddkd.getString("ddkdId").equals(obj.getDdkdId())){ %>
								      				<option value='<%=ddkd.getString("ddkdId")%>' selected><%=ddkd.getString("ddkdTen") %></option>
								      			<%}else{ %>
								     				<option value='<%=ddkd.getString("ddkdId")%>'><%=ddkd.getString("ddkdTen") %></option>
								     	<%}} ddkd.close(); }catch(java.sql.SQLException e){} }%>	 
                                    </SELECT></TD>
                                    <TD class="plainlabel" >S??? ????n h??ng</TD>
									<TD class="plainlabel">
										<input type="text" name="sodonhang" size="11" value="<%= obj.getSohoadon() %>" onChange="submitform();">
									</TD>
							    </TR>
								
								<TR>
									<TD class="plainlabel">Tr???ng th??i </TD>
									<TD class="plainlabel">
									<% 
									String[]	tt = new String[]
														{"All" ,"Ch??a duy???t", "???? duy???t", "???? h???y","PDA" };
											String[]			idTrangThai = new String[]
														{ "","0", "1", "2","9" };
												%>
										<select name="trangthai" onChange="submitform();">
												 <% for( int i=0;i<tt.length;i++) { 
								    			if(idTrangThai[i].equals(obj.getTrangthai()  )   ){ %>
								      				<option value='<%=idTrangThai[i]%>' selected><%=tt[i] %></option>
								      		 	<%}else{ %>
								     				<option value='<%=idTrangThai[i]%>'><%=tt[i] %></option>
								     			<%} 
								      		 }
								       	%>
											
									          </select>
									</TD>
									<TD class="plainlabel" >M?? / t??n kh??ch h??ng</TD>
									<TD class="plainlabel">
										<input type="text" name="khachhang" size="11" value="<%= obj.getKhachhang() %>" onChange="submitform();">
									</TD>
							    </TR>	
							    
								<TR>
									<TD class="plainlabel" >T??? ng??y</TD>
									<td class="plainlabel">
			                            <input type="text"  class="days" size="11"
			                                    id="tungay" name="tungay" value="<%= obj.getTungay() %>" maxlength="10" readonly onChange="submitform();"/>
			                    	</td>
			                    	<TD class="plainlabel" >T??? m???c</TD>
									<TD class="plainlabel">
										<input type="text" name="tumuc" size="11" value="<%= obj.getTumuc() %>" onChange="submitform();">
									</TD>
								</TR>
								<TR>
								  <TD class="plainlabel" >?????n ng??y</TD>
							      <td class="plainlabel">
			                            <input type="text"  class="days" size="11"
			                                    id="denngay" name="denngay" value="<%= obj.getDenngay() %>" maxlength="10" readonly onChange="submitform();"/>
			                    	</td>
			                    	<TD class="plainlabel" >?????n m???c</TD>
									<TD class="plainlabel">
										<input type="text" name="denmuc" size="11" value="<%= obj.getDenmuc() %>" onChange="submitform();">
									</TD>
							  	</TR>
							  	
								<TR>
									<TD class="plainlabel" colspan="4">
                                   <a class="button2" href="javascript:submitform()" >
    	                           <img style="top: -4px;" src="../images/Search30.png" alt="">T??m ki???m  </a> &nbsp;&nbsp;&nbsp;                         
									<a class="button2" href="javascript:clearform()">
	                               <img style="top: -4px;" src="../images/button.png" alt="">Nh???p l???i</a>&nbsp;&nbsp;&nbsp;&nbsp;	
									
									  <!--  <INPUT name="action" type="submit" value="Tim kiem">&nbsp;
                                       <INPUT name="reinitialiser" type="button" value="Nhap lai" onClick="clearform();">
                                       -->
                                       </TD>
								</TR>
							</TABLE>
				  </FIELDSET>
			   </TD>	
				</TR>
				<TR>
					<TD width="100%">
					<FIELDSET>
					<LEGEND class="legendtitle">&nbsp;????n h??ng b??n &nbsp;&nbsp;&nbsp; 
					<% if(Util.daKhoaSoNgay30(obj.getNppId()) == false ){
						if(active!=0){
						System.out.println("Ket qua check la false;"); %>
						<a class="button3"  onclick="newform()">
	    					<img style="top: -4px;" src="../images/New.png" alt="">T???o m???i </a>                            
					<%} 
					}
					%>
					<!--<INPUT name="action" type="submit" value="Tao moi"> -->	
					</LEGEND>
					<TABLE class="" width="100%">
						<TR>
							<TD width="98%">
							<TABLE width="100%" border="0" cellspacing="1px" cellpadding="4px">
								<TR class="tbheader">
									<th width="3%" align="center">STT</th>
									<th width="6%" align="center">M?? DH</th>
									<th width="15%" align="center">Kh??ch h??ng</th>
									<th width="9%" align="center"> Tr???ng th??i</th>
									<th width="10%" align="center">Ng??y ????n h??ng</th>
									<!-- <th width="10%" align="center">T???ng ti???n</th> -->
									<th width="8%" align="center">Ng??y t???o</th>
									<th width="10%" align="center">Ng?????i t???o</th>
									<!-- <th width="8%" align="center">Ng??y s???a</th>
									<th width="15%" align="center">Ng?????i s???a </th> -->
									<th width="13%" align="center">T??c v???</th>
								<!-- 	<TH align="center" style="width: 5%" class="nosort" >Duy???t </BR> t???t c???<br /> 
	                    				<input type="checkbox" id="duyetchkALL" onchange="checkALLDuyet();" >  
	                    				<A href = "javascript:DuyetALL();"><IMG src="../images/duyet.png" width = "30px" height = "30px" title="Duy???t t???t c??? ????n h??ng" border=0></A>
	               				   </TH> -->
									 <TH align="center" style="width: 5%" class="nosort" >Duy???t </BR> t???t c???<br /> 
	                    				<input type="checkbox" id="chkALL" onchange="checkALL();" >  
	                    				<A href = "javascript:ChotALL();"><IMG src="../images/Chot.png" title="Ch???t t???t c??? ????n h??ng" border=0></A>
	               				   </TH>
								   
	               				    <TH align="center" style="width: 5%" class="nosort" >In </BR> t???t c???<br /> 
	                    				<input type="checkbox" id="InALL" onchange="chekALL();" >  
	                    				<A href = "javascript:InALL();"><IMG width="30px" height="30px" src="../images/Printer30.png" title="In t???t c??? ????n h??ng" border=0></A>
	                    				
	               				   </TH>
								</TR>
								
								<% 
								if(dhlist != null)
								{
									int m = 0;
									String lightrow = "tblightrow";
									String darkrow = "tbdarkrow";
									while (dhlist.next()){
										if (m % 2 != 0) {%>						
											<TR class= <%=lightrow%> >
										<%} else {%>
											<TR class= <%= darkrow%> >
										<%}%>
												<TD align="center"><%= dhlist.getString("_no") %></TD>
												<TD align="center"><%= dhlist.getString("dhId") %></TD>                                   
												<TD align="left"><%= dhlist.getString("khTen") %></TD>
												<TD align="left">
												<%
												String trangthai = dhlist.getString("trangthai");
												if (trangthai.equals("0")){ %>
													<span> Ch??a duy???t</span>
												<%}else if(trangthai.equals("1")){ %>
													<span><b> ???? duy???t</b></span>
												<%}else if(trangthai.equals("2")){ %>
													<span><u> ???? h???y</u></span>
												<%} else if(trangthai.equals("9"))  { %>
													<span><u style="color:green;" > PDA</u></span>
												<% } %>
												</TD>
												<TD align="center"><%= dhlist.getString("ngaynhap") %></TD>
												<%-- <TD align="center"><%= formatter.format(Math.round(Double.parseDouble(dhlist.getString("tonggiatri")))) %></TD> --%>
												<TD align="center"><%= dhlist.getString("ngaytao") %></TD>
												<% if(dhlist.getString("ispda")!= null && dhlist.getString("ispda").equals("1")){%>
												
												<TD align="left"><%= dhlist.getString("ddkdTao")==null?"":dhlist.getString("ddkdTao") %></TD>
												<% }else{%>
												<TD align="left"><%= dhlist.getString("nguoitao") %></TD>
												<%} %>
												<%-- <TD align="center"><%= dhlist.getString("ngaysua") %></TD>
												<TD align="left"><%= dhlist.getString("nguoisua") %></TD> --%>
												<TD align="center">
												<TABLE border = 0 cellpadding="0" cellspacing="0" >
													<TR><TD>
													<%if(trangthai.equals("0")){ %>
														<A href = "../../DonhangIPUpdateSvl?userId=<%=userId%>&update=<%= dhlist.getString("dhId") %>"><img src="../images/Edit20.png" alt="Cap nhat" title="C???p nh???t" width="20" height="20" longdesc="Cap nhat" border = 0></A> &nbsp;
														
														
														<A id='<%="xoadhid" + dhlist.getString("dhId") %>'
														   href=""><img 
														   src="../images/Delete20.png" alt="X??a ????n h??ng" width="20" height="20" longdesc="X??a ????n h??ng"
														   border=0	onclick="if(!confirm('B???n c?? mu???n x??a ????n h??ng n??y?')) {return false ;}else{ processing('<%="xoadhid" + dhlist.getString("dhId") %>' , '../../DonhangIPSvl?userId=<%=userId%>&delete=<%= dhlist.getString("dhId") %>');}" >
														</A>
														<A href = "../../DonhangIPUpdateSvl?userId=<%=userId%>&display=<%= dhlist.getString("dhId") %>"><img src="../images/Display20.png" alt="Hien thi" title="Hi???n th???" width="20" height="20" longdesc="Hien thi" border = 0></A>
														
													<%}else { if( trangthai.equals("1")) {%>
															<A href = "../../DonhangIPUpdateSvl?userId=<%=userId%>&display=<%= dhlist.getString("dhId") %>"><img src="../images/Display20.png" alt="Hien thi" title="Hi???n th???" width="20" height="20" longdesc="Hien thi" border = 0></A>
														
													<% }  else  if( trangthai.equals("9")) { %>
													<A href = "../../DonhangIPUpdateSvl?userId=<%=userId%>&update=<%= dhlist.getString("dhId") %>"><img src="../images/Edit20.png" alt="Cap nhat" title="C???p nh???t" width="20" height="20" longdesc="Cap nhat" border = 0></A> &nbsp;
														<A id='<%="xoadhid" + dhlist.getString("dhId") %>'
														   href=""><img 
														   src="../images/Delete20.png" alt="X??a ????n h??ng" width="20" height="20" longdesc="X??a ????n h??ng"
														   border=0	onclick="if(!confirm('B???n c?? mu???n x??a ????n h??ng n??y?')) {return false ;}else{ processing('<%="xoadhid" + dhlist.getString("dhId") %>' , '../../DonhangIPSvl?userId=<%=userId%>&delete=<%= dhlist.getString("dhId") %>&nppId=<%= obj.getNppId() %>');}" >
														</A>
														<A href = "../../DonhangIPUpdateSvl?userId=<%=userId%>&display=<%= dhlist.getString("dhId") %>"><img src="../images/Display20.png" alt="Hien thi" title="Hi???n th???" width="20" height="20" longdesc="Hien thi" border = 0></A>
														
													<%} else{  %> 
													
													
													<A href = "../../DonhangIPUpdateSvl?userId=<%=userId%>&display=<%= dhlist.getString("dhId") %>"><img src="../images/Display20.png" alt="Hien thi" title="Hi???n th???" width="20" height="20" longdesc="Hien thi" border = 0></A>
													
													<% }}%>
													</TD>
														
													</TR>
												</TABLE>
												</TD>
												
										                    	<% if( trangthai.equals("0") || trangthai.equals("9")){%>
										                    
										                    	<Td align="center" >
										                    
										                    		<input type="checkbox" name="donhangIds" value="<%= dhlist.getString("dhId") %>" >
										                    	
								
										                    </Td>
										                    <%} else {%>
										                    	<Td align="center" >
										                    
										                    	
										                    	
								
										                    </Td>
										                    <%} %>
										                    
												<%-- <% if( trangthai.equals("3")){ %>
															 <Td align="center" >
										                    
										                    		<input type="checkbox" name="donhangIds" value="<%= dhlist.getString("dhId") %>" >
										                    	
								
										                    </Td>
										                    <% }else {%>
										                    	<Td align="center" >
										                    
										                    	
										                    	
								
										                    </Td>
										                    <%} %> --%>
										                    	
										          			 <Td align="center" >
										                    
										                    		<input type="checkbox" name="IndonhangIds" value="<%= dhlist.getString("dhId") %>" >
										                    	
								
										                    </Td>          
										            
											</TR>
									<%m++; } dhlist.close(); }%>	
										 <tr class="tbfooter" > 
											 <TD align="center" valign="middle" colspan="13" class="tbfooter">
											 <% obj.setNextSplittings(); %>
												 <script type="text/javascript" src="../scripts/phanTrang.js"></script>
												<input type="hidden" name="crrApprSplitting" value="<%= obj.getCrrApprSplitting() %>" >
												<input type="hidden" name="nxtApprSplitting" value="<%= obj.getNxtApprSplitting() %>" >

											 	<%if(obj.getNxtApprSplitting() >1) {%>
													<img alt="Trang Dau" src="../images/first.gif" style="cursor: pointer;" onclick="View(document.forms[0].name, 1, 'view')"> &nbsp;
												<%}else {%>
													<img alt="Trang Dau" src="../images/first.gif" > &nbsp;
													<%} %>
												<% if(obj.getNxtApprSplitting() > 1){ %>
													<img alt="Trang Truoc" src="../images/prev.gif" style="cursor: pointer;" onclick="View(document.forms[0].name, eval(document.forms[0].nxtApprSplitting.value) -1, 'view')"> &nbsp;
												<%}else{ %>
													<img alt="Trang Truoc" src="../images/prev_d.gif" > &nbsp;
												<%} %>
												
												<%
													int[] listPage = obj.getNextSplittings();
													for(int i = 0; i < listPage.length; i++){
												%>
												<% 
												if(listPage[i] == obj.getNxtApprSplitting()){ %>
												
													<a  style="color:white;"><%= listPage[i] %>/ <%=obj.getTheLastSplitting() %></a>
												<%}else{ %>
													<a href="javascript:View(document.forms[0].name, <%= listPage[i] %>, 'view')"><%= listPage[i] %></a>
												<%} %>
													<input type="hidden" name="list" value="<%= listPage[i] %>" />  &nbsp;
												<%} %>
												
												<% if(obj.getNxtApprSplitting() < obj.getTheLastSplitting()){ %>
													&nbsp; <img alt="Trang Tiep" src="../images/next.gif" style="cursor: pointer;" onclick="View(document.forms[0].name, eval(document.forms[0].nxtApprSplitting.value) +1, 'view')"> &nbsp;
												<%}else{ %>
													&nbsp; <img alt="Trang Tiep" src="../images/next_d.gif" > &nbsp;
												<%} %>
												<%if(obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {%>
											   		<img alt="Trang Cuoi" src="../images/last.gif" > &nbsp;
										   		<%}else{ %>
										   			<img alt="Trang Cuoi" src="../images/last.gif" style="cursor: pointer;" onclick="View(document.forms[0].name, -1, 'view')"> &nbsp;
										   		<%} %>
											</TD>
										 </tr>
  			
							</TABLE>
							</TD>
						</TR>
					</TABLE>
					</FIELDSET>
					</TD>
				</TR>
			</TBODY>
		</TABLE>
		<!--end body Dossier--></TD>
	</TR>
</TABLE>
</form>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</HTML>
<%
	try
	{
		if(!(ddkd == null))
			ddkd.close();
		if(obj != null){
			obj.DBclose();
			obj = null;
		}
		if(dhlist!=null){
			dhlist.close();
		}
		Util=null;
	
		s.setAttribute("obj",null);
		
	}catch(Exception e){}
%>