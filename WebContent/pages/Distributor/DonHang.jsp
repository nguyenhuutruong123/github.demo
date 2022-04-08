<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@page import="geso.dms.distributor.util.Utility"%>
<%@ page  import = "geso.dms.distributor.beans.donhang.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>
<%@ page  import = "geso.dms.center.util.*" %>

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

<% IDonhangList obj = (IDonhangList)s.getAttribute("obj"); %>
<% ResultSet dhlist = (ResultSet)obj.getDonhangRs(); %>
<% ResultSet ddkd = (ResultSet)obj.getDaidienkd(); %>
<% String userId = (String) s.getAttribute("userId");  %>
<% Utility Util = new Utility();

 int active=(Integer)session.getAttribute("active");
 int[] quyen = new int[6];
 quyen = Utility.Getquyen("DonhangSvl","",userId);

%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>AHF - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">

<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<link type="text/css" rel="stylesheet" href="../css/table.css">

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
	 document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='Waiting...' border='1' longdesc='Waiting...' style='border-style:outset'> Processing...</a>";
	 
 	document.forms['dhForm'].action.value= 'XK';
 	document.forms['dhForm'].dhId.value = Id;
 	document.forms['dhForm'].submit();
 }
 function clearform()
 {   
	document.forms['dhForm'].tungay.value= '';
	document.forms['dhForm'].denngay.value= '';
	document.forms['dhForm'].trangthai.value ='';
	document.forms['dhForm'].trangthaiin.value ='';
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
 function dongbo()
 {
 	document.forms['dhForm'].action.value= 'dongbo';
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

<style>
	.badge {

    display: inline-block;
    min-width: 10px;
    padding: 3px 7px;
    font-size: 12px;
    font-weight: 700;
    line-height: 1;
    color: #fff;
    text-align: center;
    white-space: nowrap;
    vertical-align: middle;
    background-color: #777;
    border-radius: 10px;
}
</style>

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

<form name="dhForm" method="post" action="../../DonhangSvl">  
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
<TABLE width="100%" border="0" cellpadding="0">
<TR>
<TD align="left" class="tbnavigation">
	<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr height="22">
	<TD align="left" colspan="2" class="tbnavigation"> &nbsp;Quản lý bán hàng > Bán hàng cho khách hàng
	<TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn  <%= obj.getNppTen() %>&nbsp;
	</tr>
	</TABLE>
</TD>
</TR>

<TR>	
<TD>
				<FIELDSET style="padding: 5px 10px; border: 1px solid rgb(128, 203, 155); background-color:#C5E8CD">
				<p id="button" style="font-size:12px; cursor:pointer; font-family:arial; font-weight:bold; text-decoration: underline;">Tiêu chí tìm kiếm</p>
				
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
									<TD class="plainlabel">Trạng thái </TD>
									<TD class="plainlabel">
									<% 
									String[] tt = new String[] {"All" ,"Chưa chốt", "Đã chốt", "Đã hủy","Đã xuất kho","Đã trả" };
											String[] idTrangThai = new String[] { "","0", "1", "2","3","4" }; %>
										<select name="trangthai" onChange="submitform();">
											 <% for( int i=0;i<tt.length;i++) { 
							    			if(idTrangThai[i].equals(obj.getTrangthai()  )   ){ %>
							      				<option value='<%=idTrangThai[i]%>' selected><%=tt[i] %></option>
							      		 	<%}else{ %>
							     				<option value='<%=idTrangThai[i]%>'><%=tt[i] %></option>
							     			<%} 
							      		 }%>
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
											<TD width="19%" class="plainlabel">Trạng thái IN </TD>
											<TD width="81%" colspan="3" class="plainlabel">
												<%  String[] ttIn = new String[] {"All" ,"Chưa in", "Đã in"};
													String[] idTrangThaiIn = new String[] { "","0", "1" }; %>
												<select name="trangthaiin" onChange="submitform();">
													 <% for( int i=0;i<ttIn.length;i++) { 
									    			if(idTrangThaiIn[i].equals(obj.getTrangthaiin())){ %>
									      				<option value='<%=idTrangThaiIn[i]%>' selected><%=ttIn[i] %></option>
									      		 	<%}else{ %>
									     				<option value='<%=idTrangThaiIn[i]%>'><%=ttIn[i] %></option>
									     			<%} 
									      		 }%>
											</SELECT>
											</TD>
								</TR>
								 <%if(obj.getNppId().equals("1000580")) {%>	
							  	<TR>
											<TD width="19%" class="plainlabel" style="color:red;">Số đơn hàng chưa đồng bộ </TD>
											<TD width="81%" colspan="3" class="plainlabel" style="color:red;"><%=obj.getSodonhangchuadongbo()%></TD>
												
											
								</TR>
								 <%}%>	
								<TR>
									<TD class="plainlabel" colspan="4">
                                   <a class="button2" href="javascript:submitform()" >
    	                           <img style="top: -4px;" src="../images/Search30.png" alt="">Tìm kiếm  
    	                           </a> &nbsp;&nbsp;&nbsp;                         
									<a class="button2" href="javascript:clearform()">
	                               <img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;
	                               <%if(obj.getNppId().equals("1000580") && !obj.getSodonhangchuadongbo().equals("0")) {%>	
	                               <a class="button2" href="javascript:dongbo()">
	                               <img style="top: -4px;" src="../images/button.png" alt="">Đồng bộ</a>&nbsp;&nbsp;&nbsp;&nbsp;
	                               <%}%>	
                                   </TD>
								</TR>
					
					<% if(Util.daKhoaSoNgay30(obj.getNppId()) == false && quyen[Utility.THEM] != 0){
						if(active!=0){%>
						<TR>
						<TD class="plainlabel" colspan="4">
						<a class="button2"  onclick="newform()">
	    					<img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>
	    				</TD></TR>                            
					<%} } %>
					</TABLE>
					
					<table class="chitieutable">
					<TR>
						<TH>STT</TH>
						<TH>Mã TT</TH>
						<TH>Mã ĐH</TH>
						<TH>Mã KH</th>
						<TH>Ngày giao hàng</th>
						<TH>Khách hàng</th>
						<TH> Trạng thái</th>
						<!-- <TH>Trạng thái In</TH> -->
						<TH>Ngày ĐH</th>
						<TH>Ngày chốt</th>
						<TH>Tổng tiền</th>
						<TH>Ngày tạo</th>
						<TH>Người tạo</th>
						<TH>Tác vụ</th>
						<%if(obj.getqtbh().equals("1")) {%>
					   <TH class="nosort">Chốt </BR> tất cả<br /> 
           				<input type="checkbox" id="chkALL" onchange="checkALL();" >  
           				<A href = "javascript:DuyetALL();"><IMG src="../images/Chot.png" title="Duyệt tất cả đơn hàng" border=0></A>
       				   </TH>
       				   <%} %>
					</TR>
								<% 
								if(dhlist != null)
								{
									int m = 0;
									while (dhlist.next())
									{
										String dhk =  "";
										if(dhlist.getString("donhangkhac").equals("1"))
											dhk =   "(DHK)";
										else
											if(dhlist.getString("donhangkhac").equals("2"))
												dhk =   "(Combo)";
										
								%>
										<TR>
										  <% String _text = ""; 
								           if (dhlist.getString("isPDA") != null && dhlist.getString("isPDA").equals("1")) { _text = "PDA"+dhk; }
								           else { _text = "Web"+dhk; }
								          %>
										
												<TD align="center"><%= dhlist.getString("_no") %></TD>
												<TD align="center"><%= dhlist.getString("madonhang") %></TD>
												<TD align="center"><%= dhlist.getString("dhid") %> <span class="badge"><%= _text %></span> </TD>
												<TD align="left"><%= dhlist.getString("smartid") %></TD>        
												<TD align="left"><%= dhlist.getString("ngaygiaohang") %></TD>                                   
												<TD align="left"><%= dhlist.getString("khTen") %></TD>
												<TD align="center">
												<%
												String trangthai = dhlist.getString("trangthai");
												if (trangthai.equals("0")){ %>
													<span> Chưa chốt</span>
												<%}else if(trangthai.equals("1")){ %>
													
													
													<%
													String ttChot = dhlist.getInt("datrahang") > 0 ? "Đã trả" : "Đã chốt";
													 %>
													
													<span><b> <%=ttChot %></b></span>
												
												
												<%}else if(trangthai.equals("2")){ %>
													<span><u> Đã hủy</u></span>
												<%}else if(trangthai.equals("3")){ %>
													
													<span><i style="color:red"> Đã xuất kho</i></span>
												<%} else if(trangthai.equals("7"))  { %>
													<span><u style="color:blue" >Chờ xử lý</u></span>
												<% } else if(trangthai.equals("9"))  { %>
													<span><u style="color:green;" > PDA</u></span>
												<% } %>
												
												<%
												String trangthaiin = dhlist.getString("trangthaiin");
												if (trangthaiin == null)
												{ %>
													<span> Chưa in</span>
												<% }
												else if(trangthaiin.equals("1")) 
												{%>
												<span><b> Đã in</b></span><%
												}else { %>
												<span><b> Chưa in</b></span> 
												<%} %>
												</TD>
												
												<%-- <TD align="left">
														<%
														String trangthaiin = dhlist.getString("trangthaiin");
														if (trangthaiin == null)
														{ %>
															<span> Chưa in</span>
														<% }
														else if(trangthaiin.equals("1")) 
														{%>
														<span><b> Đã in </b></span><%
														}else { %>
														<span><b> Chưa in</b></span> 
														<%} %>
												</TD>  --%>
											    
												<TD align="center"><%= dhlist.getString("ngaynhap") %></TD>
												<TD align="center"><%= dhlist.getString("ngaychot") %></TD>
												<TD align="center"><%= formatter.format(Math.round(Double.parseDouble(dhlist.getString("tonggiatri")))) %></TD>
												<TD align="center"><%= dhlist.getString("ngaytao") %></TD>
												<% if(dhlist.getString("ispda")!= null && dhlist.getString("ispda").equals("1")){%>
												
												<TD align="left"><%= dhlist.getString("ddkdTao")==null?"":dhlist.getString("ddkdTao") %></TD>
												<% }else{%>
												<TD align="left"><%= dhlist.getString("nguoitao") %></TD>
												<%} %>
												<TD>
													<%if(trangthai.equals("0") || trangthai.equals("6")){ %>
													<div id="abc123" style="display: inline-block">
														<A href = "../../RouterSvl?task=<%= Util.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "DonhangUpdateSvl?userId="+userId+"&update="+ dhlist.getString("dhId") +"&tn="+ obj.getTungay() +"&dn="+ obj.getDenngay() +"&tumuc="+ obj.getTumuc() +"&denmuc="+ obj.getDenmuc() +"&nvbhid="+ obj.getDdkdId() +"&sodh="+ obj.getSohoadon() +"&tthai="+ obj.getTrangthai() +"&makh="+ obj.getKhachhang() +"&tthaiin="+ obj.getTrangthaiin() +"")%>"><img src="../images/Edit20.png" alt="Update" title="Update" width="20" height="20" longdesc="Cap nhat" border = 0></A>
														<A href = "../../RouterSvl?task=<%= Util.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "DonhangUpdateSvl?userId="+userId+"&display="+ dhlist.getString("dhId") +"")%>"><img src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" width="20" height="20" longdesc="Hiển thị" border = 0></A>
														<A id='<%="xoadhid" + dhlist.getString("dhId") %>'
														   href=""><img 
														   src="../images/Delete20.png" alt="Xóa đơn hàng" width="20" height="20" longdesc="Xóa đơn hàng" title="Xóa đơn hàng" border=0	
														   onclick="if(!confirm('Bạn có muốn xóa đơn hàng này?')) 
														   {return false ;}
														   else
														   { processing('<%="xoadhid" + dhlist.getString("dhId") %>' , '../../DonhangSvl?userId=<%=userId%>&delete=<%= dhlist.getString("dhId") %>');}" >
														</A>
														</div> 
														<div id="btnSave" style="display: inline-block">
														
														<A href="javascript:duyetform(<%= dhlist.getString("dhId") %>);" >
															<img  src="../images/Chot.png" alt="Chốt đơn hàng" width="20" height="20"  border='0' title="Chốt đơn hàng"	onclick="if(!confirm('Bạn có muốn chốt đơn hàng này không ?')) {return false ;}" >
														</A>
														</div>
														<%--  <%} %> --%>
													<%}else { if( trangthai.equals("3")) {%>
														<A href = "../../RouterSvl?task=<%= Util.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "DonhangUpdateSvl?userId="+userId+"&update="+ dhlist.getString("dhId") +"&tn="+ obj.getTungay() +"&dn="+ obj.getDenngay() +"&tumuc="+ obj.getTumuc() +"&denmuc="+ obj.getDenmuc() +"&nvbhid="+ obj.getDdkdId() +"&sodh="+ obj.getSohoadon() +"&tthai="+ obj.getTrangthai() +"&makh="+ obj.getKhachhang() +"&tthaiin="+ obj.getTrangthaiin() +"")%>"><img src="../images/Edit20.png" alt="Cap nhat" title="Cập nhật" width="20" height="20" longdesc="Cap nhat" border = 0></A>
														<A href = "../../RouterSvl?task=<%= Util.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "DonhangUpdateSvl?userId="+userId+"&display="+ dhlist.getString("dhId") +"")%>"><img src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" width="20" height="20" longdesc="Hiển thị" border = 0></A>
														<A id='<%="xoadhid" + dhlist.getString("dhId") %>'
														   href=""><img 
														   src="../images/Delete20.png" alt="Xóa đơn hàng" width="20" height="20" longdesc="Xóa đơn hàng"
														   border=0	onclick="if(!confirm('Bạn có muốn xóa đơn hàng này? Lưu ý đơn hàng đã xuất kho,sau khi xóa phải chốt phiếu thu hồi!')) {return false ;}else{ processing('<%="xoadhid" + dhlist.getString("dhId") %>' , '../../DonhangSvl?userId=<%=userId%>&delete=<%= dhlist.getString("dhId") %>&nppId=<%= obj.getNppId() %>');}" >
														</A>
														<%if(dhlist.getString("qtbh").equals("1")) {%>
														 <div id="btnSave">
														<A href="javascript:duyetform(<%= dhlist.getString("dhId") %>);" >
																		<img  src="../images/Chot.png" alt="Chốt đơn hàng" width="20" height="20"  border='0' title="Chốt đơn hàng"	onclick="if(!confirm('Bạn có muốn chốt đơn hàng này không ?')) {return false ;}" >
																	</A></div>
 														<%} %>
													<% } else  if( trangthai.equals("7")) { %>
														
															<A href = "../../RouterSvl?task=<%= Util.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "DonhangUpdateSvl?userId="+userId+"&convert="+ dhlist.getString("dhId") +"")%>"><IMG src="../images/Next.png" alt="Chuyển thành đơn đặt hàng" title="Chuyển thành đơn hàng" width="20px" border=0 onclick="if(!confirm('Bạn có muốn chuyển sang Chuyển thành đơn hàng?')) return false;" ></A>
															<A id='<%="xoadhid" + dhlist.getString("dhId") %>'
															   href=""><img 
															   src="../images/Delete20.png" alt="Xóa đơn hàng" width="20" height="20" longdesc="Xóa đơn hàng"
															   border=0	onclick="if(!confirm('Bạn có muốn xóa đơn hàng này?')) {return false ;}else{ processing('<%="xoadhid" + dhlist.getString("dhId") %>' , '../../DonhangSvl?userId=<%=userId%>&delete=<%= dhlist.getString("dhId") %>&nppId=<%= obj.getNppId() %>');}" >
															</A>
															<A href = "../../RouterSvl?task=<%= Util.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "DonhangUpdateSvl?userId="+userId+"&display="+ dhlist.getString("dhId") +"")%>"><img src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" width="20" height="20" longdesc="Hiển thị" border = 0></A>
															
														
													<%} else  if( trangthai.equals("9")) { %>
														<A href = "../../RouterSvl?task=<%= Util.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "DonhangUpdateSvl?userId="+userId+"&update="+ dhlist.getString("dhId") +"")%>"><img src="../images/Edit20.png" alt="Cap nhat" title="Cập nhật" width="20" height="20" longdesc="Cap nhat" border = 0></A> 
														<A id='<%="xoadhid" + dhlist.getString("dhId") %>'
														   href=""><img 
														   src="../images/Delete20.png" alt="Xóa đơn hàng" width="20" height="20" longdesc="Xóa đơn hàng"
														   border=0	onclick="if(!confirm('Bạn có muốn xóa đơn hàng này?')) {return false ;}else{ processing('<%="xoadhid" + dhlist.getString("dhId") %>' , '../../DonhangSvl?userId=<%=userId%>&delete=<%= dhlist.getString("dhId") %>&nppId=<%= obj.getNppId() %>');}" >
														</A>
														<A href = ""../../RouterSvl?task=<%= Util.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "DonhangUpdateSvl?userId="+userId+"&display="+ dhlist.getString("dhId") +"")%>""><img src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" width="20" height="20" longdesc="Hiển thị" border = 0></A>
														<%if(dhlist.getString("qtbh").equals("1")) {%>
														 <div id="btnSave">
															<A href="javascript:duyetform(<%= dhlist.getString("dhId") %>);" >
																		<img  src="../images/Chot.png" alt="Chốt đơn hàng" width="20" height="20"  border='0' title="Chốt đơn hàng"	onclick="if(!confirm('Bạn có muốn chốt đơn hàng này không ?')) {return false ;}" >
															</A>
														</div>
														
														<%} %>
													<%} else{  %> 
													<A href = "../../RouterSvl?task=<%= Util.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "DonhangUpdateSvl?userId="+userId+"&display="+ dhlist.getString("dhId") +"")%>"><img src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" width="20" height="20" longdesc="Hiển thị" border = 0></A>
													
													<% }}%>
													<!-- </TD>
														
													</TR>
												</TABLE> -->
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
										            </TR>
									<%m++; } dhlist.close(); }%>
										
										 <tr class="tbfooter" > 
											 <TD align="center" valign="middle" colspan="14" class="tbfooter">
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
												
													<a  style="color:red;"><%= listPage[i] %>/ <%=obj.getTheLastSplitting() %></a>
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
							</FIELDSET>
					</TD>
				</TR>
		</TABLE>
</form>
</body>  
<script type='text/javascript' src='../scripts/loadingv2.js'></script>
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
		
	}catch(Exception e){
		e.printStackTrace();
	}
%>