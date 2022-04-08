<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@page import="geso.dms.distributor.util.Utility"%>
<%@ page  import = "geso.dms.distributor.beans.donhangsam.*" %>
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

<% IDonhangsamList obj = (IDonhangsamList)s.getAttribute("obj"); %>
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
	 document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Processing...</a>";
	 
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
		 alert('Vui lòng chọn đơn hàng');
		 return;
	 }		
	 
	 
	 var r = confirm("Bạn chắc chắn muốn in tất cả đơn hàng? ");
	 if (r == false) {		 
	     return;
	 }
	 else
		{
		 	
		 
		 	document.forms["dhForm"].action.value = "InALL";
		 	document.forms["dhForm"].submit(); 
		
		}
	 
	 
 }
 function DuyetALL()
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
		 alert('Vui lòng chọn đơn hàng');
		 return;
	 }		
	 
	 
	 var r = confirm("Bạn chắc chắn muốn chốt tất cả đơn hàng? ");
	 if (r == false) {		 
	     return;
	 }
	 else
		{
			 document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Processing...</a>";
		 
		 	document.forms["dhForm"].action.value = "duyetALL";
		
		 	document.forms["dhForm"].submit(); 
		
		}
	 
	 
 }
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

<form name="dhForm" method="post" action="../../DonhangSamplingSvl"> 
 <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="nppId" value="<%=obj.getNppId()%>" >
<input type="hidden" name="dhId" id="dhId"  >
<input type="hidden" name="qtbh" id="qtbh" value='<%= obj.getqtbh() %>'  >
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
							   <TD align="left" colspan="2" class="tbnavigation"> &nbsp;Quản lý bán hàng > Bán hàng cho khách hàng
							   <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn  <%= obj.getNppTen() %>&nbsp;</tr>
						</TABLE>
					</TD>
		  </TR>
			<TR>
				<TD >
				<FIELDSET><LEGEND class="legendtitle">&nbsp;Tiêu chí tìm kiếm</LEGEND>
							<TABLE width="100%" cellpadding="6" cellspacing="0">
								
								<TR>
									<TD width="21%" class="plainlabel">Nhân viên bán hàng </TD>
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
                                    <TD class="plainlabel" >Số đơn hàng</TD>
									<TD class="plainlabel">
										<input type="text" name="sodonhang" size="11" value="<%= obj.getSohoadon() %>" onChange="submitform();">
									</TD>
							    </TR>
								
								<TR>
									<TD class="plainlabel colspan="1"">Trạng thái </TD>
									<TD class="plainlabel" >
									<% 
									String[]	tt = new String[]
														{"All" ,"Chưa chốt", "Đã chốt", "Đã hủy","Đã xuất kho","PDA" };
											String[]			idTrangThai = new String[]
														{ "","0", "1", "2","3","9" };
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
									<TD class="plainlabel" >Mã / tên khách hàng</TD>
									<TD class="plainlabel">
										<input type="text" name="khachhang" size="11" value="<%= obj.getKhachhang() %>" onChange="submitform();">
									</TD>
							    </TR>	
							    
								<TR>
									<TD class="plainlabel" >Từ ngày</TD>
									<td class="plainlabel">
			                            <input type="text"  class="days" size="11"
			                                    id="tungay" name="tungay" value="<%= obj.getTungay() %>" maxlength="10" readonly onChange="submitform();"/>
			                    	</td>
			                    	<TD class="plainlabel" >Từ mức</TD>
									<TD class="plainlabel">
										<input type="text" name="tumuc" size="11" value="<%= obj.getTumuc() %>" onChange="submitform();">
									</TD>
								</TR>
								<TR>
								  <TD class="plainlabel" >Đến ngày</TD>
							      <td class="plainlabel">
			                            <input type="text"  class="days" size="11"
			                                    id="denngay" name="denngay" value="<%= obj.getDenngay() %>" maxlength="10" readonly onChange="submitform();"/>
			                    	</td>
			                    	<TD class="plainlabel" >Đến mức</TD>
									<TD class="plainlabel">
										<input type="text" name="denmuc" size="11" value="<%= obj.getDenmuc() %>" onChange="submitform();">
									</TD>
							  	</TR>
							  	<TR>
								  <TD class="plainlabel" >Tình trạng đơn hàng</TD>
									<TD class="plainlabel" colspan="3">
									<% 
									String[]	ttt = new String[]
														{"All" ,"Chưa xử lý", "Đã xử lý" };
											String[]			idttdh = new String[]
														{ "","0", "1" };
												%>
										<select name="ttdh" onChange="submitform();">
												<% for( int i=0;i<ttt.length;i++) { 
								    			if(idttdh[i].equals( obj.getTtdh() )   )
								    			{ %>
								      				<option value='<%=idttdh[i]%>' selected><%=ttt[i] %></option>
								      		 	<%}else{ %>
								     				<option value='<%=idttdh[i]%>'><%=ttt[i] %></option>
								     			<%} 
								      		 	}
								       			%> 
											
									          </select>
									</TD>
								</TR>
								<TR>
											<TD width="19%" class="plainlabel">Trạng thái IN </TD>
											<TD width="81%" colspan="3" class="plainlabel"><SELECT
												name="trangthaiin" >
													<% if (obj.getTrangthaiin().equals("1")){%>
													<option value="1" selected>Đã tạo</option>
													<option value="">Chưa tạo</option>
													<%}else if(obj.getTrangthaiin().equals("0")) {%>
													<option value="" selected>Chưa tạo</option>
													<option value="1">Đã tạo</option>
													<%}else{%>
													<option value="" selected>Chưa tạo</option>
													<option value="1">Đã tạo</option>
													<%}%>
											</SELECT></TD>
								</TR>
							  	
								<TR>
									<TD class="plainlabel" colspan="4">
                                   <a class="button2" href="javascript:submitform()" >
    	                           <img style="top: -4px;" src="../images/Search30.png" alt="">Tìm kiếm  </a> &nbsp;&nbsp;&nbsp;                         
									<a class="button2" href="javascript:clearform()">
	                               <img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;	
									
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
					<LEGEND class="legendtitle">&nbsp;Đơn hàng bán &nbsp;&nbsp;&nbsp; 
					<% if(Util.daKhoaSoNgay30(obj.getNppId()) == false ){
						if(active!=0){
						System.out.println("Ket qua check la false;"); %>
						<a class="button3"  onclick="newform()">
	    					<img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>                            
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
									<th width="6%" align="center">Mã DH</th>
									<th width="13%" align="center">Khách hàng</th>
									<th width="9%" align="center"> Trạng thái</th>
									<TH align="center">Trạng thái In</TH>
									<th width="9%" align="center"> Tình trạng ĐH</th>
									<th width="8%" align="center">Ngày đơn hàng</th>
									<th width="8%" align="center">Tổng tiền</th>
									<th width="8%" align="center">Ngày tạo</th>
									<th width="8%" align="center">Người tạo</th>
									<!-- <th width="8%" align="center">Ngày sửa</th>
									<th width="15%" align="center">Người sửa </th> -->
									<th width="13%" align="center">Tác vụ</th>
									<%if(obj.getqtbh().equals("1")) {%>
								   <TH align="center" style="width: 5%" class="nosort" >Chốt </BR> tất cả<br /> 
	                    				<input type="checkbox" id="chkALL" onchange="checkALL();" >  
	                    				<A href = "javascript:DuyetALL();"><IMG src="../images/Chot.png" title="Duyệt tất cả đơn hàng" border=0></A>
	               				   </TH>
	               				   <%} %>
	               				    <TH align="center" style="width: 5%" class="nosort" >In </BR> tất cả<br /> 
	                    				<input type="checkbox" id="InALL" onchange="chekALL();" >  
	                    				<A href = "javascript:InALL();"><IMG width="30px" height="30px" src="../images/Printer30.png" title="In tất cả đơn hàng" border=0></A>
	                    				
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
													<span> Chưa chốt</span>
												<%}else if(trangthai.equals("1")){ %>
													<span><b> Đã chốt</b></span>
												<%}else if(trangthai.equals("2")){ %>
													<span><u> Đã hủy</u></span>
												<%}else if(trangthai.equals("3")){ %>
													
													<span><i style="color:red"> Đã xuất kho</i></span>
												<%} else if(trangthai.equals("7"))  { %>
													<span><u style="color:blue" >Chờ xử lý</u></span>
												<% } else if(trangthai.equals("9"))  { %>
													<span><u style="color:green;" > PDA</u></span>
												<% } %>
												</TD>
												<TD align="left">
														<%
														String trangthaiin = dhlist.getString("trangthaiin");
														if (trangthaiin == null)
														{ %>
															<span> Chưa tạo</span>
														<% }
														else if(trangthaiin.equals("1")) 
														{%>
														<span><b> Đã tạo </b></span><%
														}else { %>
														<span><b> Chưa tạo</b></span> 
														<%} %>
												</TD> 
												<TD align="left">
												<%
												String tinhtrangdh = dhlist.getString("FlagModified");
												if (tinhtrangdh == null)
												{ %>
													<span> null</span>
												<% }
												else if(tinhtrangdh.equals("0")) 
												{%>
													<span><b> Chưa xử lý</b></span><%
												}else if(tinhtrangdh.equals("1")){ %>
													<span><b> Đã xử lý</b></span> 
												<%} %>
												</TD>      
												<TD align="center"><%= dhlist.getString("ngaynhap") %></TD>
												<TD align="center"><%= formatter.format(Math.round(Double.parseDouble(dhlist.getString("tonggiatri")))) %></TD>
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
													<%if(trangthai.equals("0") || trangthai.equals("6")){ %>
														<A href = "../../DonhangSamUpdateSvl?userId=<%=userId%>&update=<%= dhlist.getString("dhId") %>"><img src="../images/Edit20.png" alt="Cap nhat" title="Cập nhật" width="20" height="20" longdesc="Cap nhat" border = 0></A> &nbsp;
														<A href = "../../DonhangSamUpdateSvl?userId=<%=userId%>&display=<%= dhlist.getString("dhId") %>"><img src="../images/Display20.png" alt="Hien thi" title="Hiển thị" width="20" height="20" longdesc="Hien thi" border = 0></A>
														<%-- <A href = "../../DonhangSvl?userId=<%=userId%>&delete=<%= dhlist.getString("dhId") %>"><img src="../images/Delete20.png" alt="Xoa" title="Xóa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Ban Muon Xoa Don Hang Nay?')) return false;"></A> --%>
														<A id='<%="xoadhid" + dhlist.getString("dhId") %>'
														   href=""><img 
														   src="../images/Delete20.png" alt="Xóa đơn hàng" width="20" height="20" longdesc="Xóa đơn hàng"
														   border=0	onclick="if(!confirm('Bạn có muốn xóa đơn hàng này?')) {return false ;}else{ processing('<%="xoadhid" + dhlist.getString("dhId") %>' , '../../DonhangSamplingSvl?userId=<%=userId%>&delete=<%= dhlist.getString("dhId") %>');}" >
														</A>
														<%if(dhlist.getString("qtbh").equals("1")) {%>
														 <div id="btnSave">
														<A href="javascript:duyetform(<%= dhlist.getString("dhId") %>);" >
																		<img  src="../images/Chot.png" alt="Chốt đơn hàng" width="20" height="20"  border='0' title="Chốt đơn hàng"	onclick="if(!confirm('Bạn có muốn chốt đơn hàng này không ?')) {return false ;}" >
																	</A></div>
												
														
														 <%} %>
													<%}else { if( trangthai.equals("3")) {%>
														<A href = "../../DonhangSamUpdateSvl?userId=<%=userId%>&update=<%= dhlist.getString("dhId") %>"><img src="../images/Edit20.png" alt="Cap nhat" title="Cập nhật" width="20" height="20" longdesc="Cap nhat" border = 0></A> &nbsp;
														<A href = "../../DonhangSamUpdateSvl?userId=<%=userId%>&display=<%= dhlist.getString("dhId") %>"><img src="../images/Display20.png" alt="Hien thi" title="Hiển thị" width="20" height="20" longdesc="Hien thi" border = 0></A>
														<A id='<%="xoadhid" + dhlist.getString("dhId") %>'
														   href=""><img 
														   src="../images/Delete20.png" alt="Xóa đơn hàng" width="20" height="20" longdesc="Xóa đơn hàng"
														   border=0	onclick="if(!confirm('Bạn có muốn xóa đơn hàng này? Lưu ý đơn hàng đã xuất kho,sau khi xóa phải chốt phiếu thu hồi!')) {return false ;}else{ processing('<%="xoadhid" + dhlist.getString("dhId") %>' , '../../DonhangSamplingSvl?userId=<%=userId%>&delete=<%= dhlist.getString("dhId") %>&nppId=<%= obj.getNppId() %>');}" >
														</A>
														<%if(dhlist.getString("qtbh").equals("1")) {%>
														 <div id="btnSave">
														<A href="javascript:duyetform(<%= dhlist.getString("dhId") %>);" >
																		<img  src="../images/Chot.png" alt="Chốt đơn hàng" width="20" height="20"  border='0' title="Chốt đơn hàng"	onclick="if(!confirm('Bạn có muốn chốt đơn hàng này không ?')) {return false ;}" >
																	</A></div>
 														<%} %>
													<% } else  if( trangthai.equals("7")) { %>
														
															<A href = "../../DonhangSamplingSvl?userId=<%=userId%>&convert=<%= dhlist.getString("dhId") %>"><IMG src="../images/Next.png" alt="Chuyển thành đơn đặt hàng" title="Chuyển thành đơn hàng" width="20px" border=0 onclick="if(!confirm('Bạn có muốn chuyển sang Chuyển thành đơn hàng?')) return false;" ></A>&nbsp;
															<A id='<%="xoadhid" + dhlist.getString("dhId") %>'
															   href=""><img 
															   src="../images/Delete20.png" alt="Xóa đơn hàng" width="20" height="20" longdesc="Xóa đơn hàng"
															   border=0	onclick="if(!confirm('Bạn có muốn xóa đơn hàng này?')) {return false ;}else{ processing('<%="xoadhid" + dhlist.getString("dhId") %>' , '../../DonhangSamplingSvl?userId=<%=userId%>&delete=<%= dhlist.getString("dhId") %>&nppId=<%= obj.getNppId() %>');}" >
															</A>
															<A href = "../../DonhangSamUpdateSvl?userId=<%=userId%>&display=<%= dhlist.getString("dhId") %>"><img src="../images/Display20.png" alt="Hien thi" title="Hiển thị" width="20" height="20" longdesc="Hien thi" border = 0></A>
															
														
													<%} else  if( trangthai.equals("9")) { %>
													<A href = "../../DonhangSamUpdateSvl?userId=<%=userId%>&update=<%= dhlist.getString("dhId") %>"><img src="../images/Edit20.png" alt="Cap nhat" title="Cập nhật" width="20" height="20" longdesc="Cap nhat" border = 0></A> &nbsp;
														<A id='<%="xoadhid" + dhlist.getString("dhId") %>'
														   href=""><img 
														   src="../images/Delete20.png" alt="Xóa đơn hàng" width="20" height="20" longdesc="Xóa đơn hàng"
														   border=0	onclick="if(!confirm('Bạn có muốn xóa đơn hàng này?')) {return false ;}else{ processing('<%="xoadhid" + dhlist.getString("dhId") %>' , '../../DonhangSvl?userId=<%=userId%>&delete=<%= dhlist.getString("dhId") %>&nppId=<%= obj.getNppId() %>');}" >
														</A>
														<A href = "../../DonhangSamUpdateSvl?userId=<%=userId%>&display=<%= dhlist.getString("dhId") %>"><img src="../images/Display20.png" alt="Hien thi" title="Hiển thị" width="20" height="20" longdesc="Hien thi" border = 0></A>
														<%if(dhlist.getString("qtbh").equals("1")) {%>
														 <div id="btnSave">
														<A href="javascript:duyetform(<%= dhlist.getString("dhId") %>);" >
																		<img  src="../images/Chot.png" alt="Chốt đơn hàng" width="20" height="20"  border='0' title="Chốt đơn hàng"	onclick="if(!confirm('Bạn có muốn chốt đơn hàng này không ?')) {return false ;}" >
																	</A></div>&nbsp;
																
								
										                    </Td>
														<%-- <A id='<%="dhid" + dhlist.getString("dhId") %>'
														
														   href=""><img 
														   src="../images/Chot.png" alt="Chốt đơn hàng" width="20" height="20" longdesc="Chốt đơn hàng" title="Chốt đơn hàng"
														   border=0	onclick="if(!confirm('Bạn có muốn Chốt đơn hàng cho đơn hàng này?')) {return false ;}else{ processing('<%="dhid" + dhlist.getString("dhId") %>' , '../../DonhangSvl?userId=<%=userId%>&XK=<%= dhlist.getString("dhId") %>&nppId=<%= obj.getNppId() %>');}" >
														</A> --%>
														<%} %>
													<%} else{  %> 
													
													
													<A href = "../../DonhangSamUpdateSvl?userId=<%=userId%>&display=<%= dhlist.getString("dhId") %>"><img src="../images/Display20.png" alt="Hien thi" title="Hiển thị" width="20" height="20" longdesc="Hien thi" border = 0></A>
													
													<% }}%>
													</TD>
														
													</TR>
												</TABLE>
												</TD>
												<%if(obj.getqtbh().equals("1")) {%>
												<% if(trangthai.equals("0") || trangthai.equals("6") || trangthai.equals("9") || trangthai.equals("3")){ %>
															 <Td align="center" >
										                    
										                    		<input type="checkbox" name="donhangIds" value="<%= dhlist.getString("dhId") %>" >
										                    	
								
										                    </Td>
										                    <% }else {%>
										                    
										                    	 <Td align="center" >  </Td>
										                    <%} %>
										                    	<%}%>
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