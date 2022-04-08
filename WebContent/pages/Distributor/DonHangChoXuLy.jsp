<%@page import="CI.IS"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@page import="geso.dms.distributor.util.Utility"%>
<%@ page  import = "geso.dms.distributor.beans.donhangchoxuly.*" %>
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

<% IDonHangChoXuLyList obj = (IDonHangChoXuLyList)s.getAttribute("obj"); %>
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
 {
	 var tt = document.forms["dhForm"].msg.value;
 	 if(tt.length>0)
     	alert(document.forms["dhForm"].msg.value);
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

<form name="dhForm" method="post" action="../../DonHangChoXuLySvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="nppId" value="<%=obj.getNppId()%>" >
<input type="hidden" name="action" value="1">
<input type="hidden" name="msg" id="msg" value='<%= obj.getMsg() %>'>


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
							   <TD align="left" colspan="2" class="tbnavigation"> &nbsp;Quản lý bán hàng > Đơn hàng chờ xử lý
							   <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn  <%= obj.getNppTen() %>&nbsp;</tr>
						</TABLE>
					</TD>
		  </TR>
			<TR>
				<TD >
				<FIELDSET><LEGEND class="legendtitle">&nbsp;Tiêu chí tìm kiếm</LEGEND>
							<TABLE width="100%" cellpadding="6" cellspacing="0">
								
								<TR>
                                     <TD width="21%" class="plainlabel">ID</TD>
									<TD class="plainlabel">
										<input type="text" name="id"  value="<%= obj.getId() %>">
									</TD>
                                    
                                    <TD class="plainlabel" >Số đơn hàng</TD>
									<TD class="plainlabel">
										<input type="text" name="sodonhang" value="<%= obj.getSohoadon() %>">
									</TD>
							    </TR>
								
								<TR>
									<TD class="plainlabel">Trạng thái </TD>
									<TD class="plainlabel">
									<% 
									String[]	tt = new String[]
														{"All" ,"Chờ xử lý", "Đã chuyển thành đơn hàng", "Đã hủy","Hoàn tất" };
											String[]			idTrangThai = new String[]
														{ "","0", "1", "2","3" };
												%>
										<select name="trangthai">
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
										<input type="text" name="khachhang" size="11" value="<%= obj.getKhachhang() %>">
									</TD>
							    </TR>	
								<TR>
									<TD class="plainlabel" >Từ ngày</TD>
									<td class="plainlabel">
			                            <input type="text"  class="days" size="11"
			                                    id="tungay" name="tungay" value="<%= obj.getTungay() %>" maxlength="10" readonly />
			                    	</td>
			                    	<TD class="plainlabel" >Từ mức</TD>
									<TD class="plainlabel">
										<input type="text" name="tumuc" size="11" value="<%= obj.getTumuc() %>">
									</TD>
								</TR>
								<TR>
								  <TD class="plainlabel" >Đến ngày</TD>
							      <td class="plainlabel">
			                            <input type="text"  class="days" size="11"
			                                    id="denngay" name="denngay" value="<%= obj.getDenngay() %>" maxlength="10" readonly />
			                    	</td>
			                    	<TD class="plainlabel" >Đến mức</TD>
									<TD class="plainlabel">
										<input type="text" name="denmuc" size="11" value="<%= obj.getDenmuc() %>">
									</TD>
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
									<th width="6%" align="center">STT</th>
									<th width="6%" align="center">ID</th>
									<th width="6%" align="center">Mã ĐH</th>
									<th width="15%" align="center">Khách hàng</th>
									<th width="9%" align="center"> Trạng thái</th>
									<th width="10%" align="center">Ngày đơn hàng</th>
									<th width="10%" align="center">Tổng tiền</th>
									<th width="8%" align="center">Ngày tạo</th>
									<th width="15%" align="center">Người tạo</th>
									<!-- <th width="8%" align="center">Ngày sửa</th>
									<th width="15%" align="center">Người sửa </th> -->
									<th width="10%" align="center">Tác vụ</th>
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
												<TD align="center"><%= dhlist.getString("donhang_fk") %></TD>      
												<TD align="left"><%= dhlist.getString("khTen") %></TD>
												<TD align="left">
												<%
												String trangthai = dhlist.getString("trangthai");
												int ischuyen = dhlist.getInt("ischuyen");
												if (trangthai.equals("0")){ %>
													<span> Chưa chốt</span>
												<%}else if(trangthai.equals("1")){ %>
													<span><b> Đã chốt</b></span>
												<%}else if(trangthai.equals("2")){ %>
													<span><u> Đã hủy</u></span>
												<%}else if(trangthai.equals("3")){ %>
													
													<span><i style="color:red"> Đã xuất kho</i></span>
												<%} else if(trangthai.equals("7"))  { %>
													<span><u style="color:blue" > Đơn hàng chờ xử lý</u></span>
												<% } else if(trangthai.equals("9"))  { %>
													<span><u style="color:green;" > Đơn hàng PDA</u></span>
												<% } %>
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
													<%if(trangthai.equals("0") || trangthai.equals("6") || (trangthai.equals("1") && ischuyen > 0)){ %>
													<A href = "../../DonHangChoXuLySvl?userId=<%=userId%>&convert=<%= dhlist.getString("dhId") %>&nppId=<%= obj.getNppId() %>"><IMG src="../images/Next.png" alt="Chuyển thành đơn đặt hàng" title="Chuyển thành đơn hàng" width="20px" border=0 onclick="if(!confirm('Bạn có muốn chuyển sang Chuyển thành đơn hàng?')) return false;" ></A>&nbsp;
													
													<%if(!trangthai.equals("1")){ %>
													<A id='<%="xoadhid" + dhlist.getString("dhId") %>'
															   href=""><img 
															   src="../images/Delete20.png" alt="Xóa đơn hàng" width="20" height="20" longdesc="Xóa đơn hàng"
															   border=0	onclick="if(!confirm('Bạn có muốn xóa đơn hàng này?')) {return false ;}else{ processing('<%="xoadhid" + dhlist.getString("dhId") %>' , '../../DonHangChoXuLySvl?userId=<%=userId%>&delete=<%= dhlist.getString("dhId") %>&nppId=<%= obj.getNppId() %>');}" >
													</A>
													<%} %>
													<A href = "../../DonHangChoXuLyUpdateSvl?userId=<%=userId%>&display=<%= dhlist.getString("dhId") %>"><img src="../images/Display20.png" alt="Hien thi" title="Hiển thị" width="20" height="20" longdesc="Hien thi" border = 0></A>
															
														 
													<%}else { if( trangthai.equals("3")) {%>
														<A href = "../../DonHangChoXuLySvl?userId=<%=userId%>&update=<%= dhlist.getString("dhId") %>"><img src="../images/Edit20.png" alt="Cap nhat" title="Cập nhật" width="20" height="20" longdesc="Cap nhat" border = 0></A> &nbsp;
														<A href = "../../DonHangChoXuLySvl?userId=<%=userId%>&display=<%= dhlist.getString("dhId") %>"><img src="../images/Display20.png" alt="Hien thi" title="Hiển thị" width="20" height="20" longdesc="Hien thi" border = 0></A>
														<A id='<%="xoadhid" + dhlist.getString("dhId") %>'
														   href=""><img 
														   src="../images/Delete20.png" alt="Xóa đơn hàng" width="20" height="20" longdesc="Xóa đơn hàng"
														   border=0	onclick="if(!confirm('Bạn có muốn xóa đơn hàng này?')) {return false ;}else{ processing('<%="xoadhid" + dhlist.getString("dhId") %>' , '../../DonHangChoXuLySvl?userId=<%=userId%>&delete=<%= dhlist.getString("dhId") %>&nppId=<%= obj.getNppId() %>');}" >
														</A>
 
													<% } else  if( trangthai.equals("7")) { %>
														
															<A href = "../../DonHangChoXuLySvl?userId=<%=userId%>&convert=<%= dhlist.getString("dhId") %>&nppId=<%= obj.getNppId() %>"><IMG src="../images/Next.png" alt="Chuyển thành đơn đặt hàng" title="Chuyển thành đơn hàng" width="20px" border=0 onclick="if(!confirm('Bạn có muốn chuyển sang Chuyển thành đơn hàng?')) return false;" ></A>&nbsp;
															<A id='<%="xoadhid" + dhlist.getString("dhId") %>'
															   href=""><img 
															   src="../images/Delete20.png" alt="Xóa đơn hàng" width="20" height="20" longdesc="Xóa đơn hàng"
															   border=0	onclick="if(!confirm('Bạn có muốn xóa đơn hàng này?')) {return false ;}else{ processing('<%="xoadhid" + dhlist.getString("dhId") %>' , '../../DonHangChoXuLySvl?userId=<%=userId%>&delete=<%= dhlist.getString("dhId") %>&nppId=<%= obj.getNppId() %>');}" >
															</A>
															<A href = "../../DonHangChoXuLyUpdateSvl?userId=<%=userId%>&display=<%= dhlist.getString("dhId") %>"><img src="../images/Display20.png" alt="Hien thi" title="Hiển thị" width="20" height="20" longdesc="Hien thi" border = 0></A>
															
														
													<%} else  if( trangthai.equals("9")) { %>
													<A href = "../../DonHangChoXuLySvl?userId=<%=userId%>&update=<%= dhlist.getString("dhId") %>"><img src="../images/Edit20.png" alt="Cap nhat" title="Cập nhật" width="20" height="20" longdesc="Cap nhat" border = 0></A> &nbsp;
														<A id='<%="xoadhid" + dhlist.getString("dhId") %>'
														   href=""><img 
														   src="../images/Delete20.png" alt="Xóa đơn hàng" width="20" height="20" longdesc="Xóa đơn hàng"
														   border=0	onclick="if(!confirm('Bạn có muốn xóa đơn hàng này?')) {return false ;}else{ processing('<%="xoadhid" + dhlist.getString("dhId") %>' , '../../DonHangChoXuLySvl?userId=<%=userId%>&delete=<%= dhlist.getString("dhId") %>&nppId=<%= obj.getNppId() %>');}" >
														</A>
														<A href = "../../DonHangChoXuLySvl?userId=<%=userId%>&display=<%= dhlist.getString("dhId") %>"><img src="../images/Display20.png" alt="Hien thi" title="Hiển thị" width="20" height="20" longdesc="Hien thi" border = 0></A>
													<%} else{  %> 
													
													
													<A href = "../../DonHangChoXuLyUpdateSvl?userId=<%=userId%>&display=<%= dhlist.getString("dhId") %>"><img src="../images/Display20.png" alt="Hien thi" title="Hiển thị" width="20" height="20" longdesc="Hien thi" border = 0></A>
													
													<% }}%>
													</TD></TR>
												</TABLE>
												</TD>
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