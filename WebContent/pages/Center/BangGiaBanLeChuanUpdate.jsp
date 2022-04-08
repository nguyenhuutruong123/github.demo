<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.beans.banggiablc.IBanggiablc" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>

<% IBanggiablc bgblcBean = (IBanggiablc)session.getAttribute("bgblcBean"); %>
<% ResultSet dvkd = (ResultSet)bgblcBean.getDvkdIds();
	ResultSet kbhRs = (ResultSet)bgblcBean.getKbhRs();
%>
<% ResultSet sanpham = (ResultSet)bgblcBean.getSanPhamList();
	NumberFormat formatter = new DecimalFormat("#,###,####");
	NumberFormat formatter2 = new DecimalFormat("#,###,###.####");
	
	ResultSet loaikhRS = (ResultSet)bgblcBean.getLoaiKhRs();	

	String[] spIdArr = bgblcBean.getSpIdArr();

	String[] spMaArr =  bgblcBean.getSpMaArr() ;

	String[] spTenArr =  bgblcBean.getSpTenArr();
	
	String[] dongiaArr =  bgblcBean.getDongiaArr();
	
	String[]  donviArr= bgblcBean.getDonviArr();
	
	String[]  checkluonhien= bgblcBean.getCheckluonhien();
	String[]  checkban= bgblcBean.getCheckban();

	
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">

<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<script type="text/javascript"	src="../scripts/jquery.min.1.7.js"></script>
<script type="text/javascript" language="JavaScript" src="../scripts/jquery.tools.min.js"></script>

<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function() {		
		$( ".days" ).datepicker({			    
				changeMonth: true,
				changeYear: true				
		});            
	});	
</script>


<SCRIPT language="javascript" type="text/javascript">
<%if( !bgblcBean.getDisplay().equals("1") &&  !bgblcBean.getTrangthai().equals("1")){ %>


function RemoveDisable()
{
	 document.getElementById("dvkdId").removeAttribute('disabled');

}

function submitform()
{   
	RemoveDisable();	
	document.forms["bgblcForm"].submit();
}
			
function saveform()
{
	RemoveDisable();
	var bgTen = document.getElementById('bgTen');
	if(bgTen.value===""){
		alert("Bạn chưa nhập tên bảng giá");
		return;
	}
	document.forms['bgblcForm'].action.value='save';
	document.forms["bgblcForm"].submit();
}

function changeTick(cb,i){
	console.log('gggxx');
	var hid = document.getElementById("checkluonhien"+i);
	if(cb.checked){
		hid.value = "1";
	}
	else{
		hid.value = "0";
	}
}

 <%}%>
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
		if(element.value== '' ||element.value=='0' )
		{
			element.value= '';
		}
	}
 function changeTick1(cb,i){
		/* console.log('gggxx'); */
		var hid = document.getElementById("checkban"+i);
		if(cb.checked){
			hid.value = "1";
		}
		else{
			hid.value = "0";
		}
	}
 function checkedAll1(chk) {
		if(chk !=null && chk.length>0){
			for(i=0; i<chk.length; i++){
			 	if(document.bgblcForm.chonban.checked==true){
					chk[i].checked = true;
					changeTick1(chk[i],i);
				}else{
					
					chk[i].checked = false;
					changeTick1(chk[i],i);
				}
			 }
		}
}
 function inExcel()
 {
 	document.forms['bgblcForm'].action.value='excel';
     document.forms["bgblcForm"].submit();
 }
 function checkedAll(chk) {
		if(chk !=null && chk.length>0){
			for(i=0; i<chk.length; i++){
			 	if(document.bgblcForm.chon.checked==true){
					chk[i].checked = true;
					changeTick(chk[i],i);
				}else{
					
					chk[i].checked = false;
					changeTick(chk[i],i);
				}
			 }
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

<form name="bgblcForm" method="post" action="../../BanggiabanlechuanUpdateSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%=bgblcBean.getUserId() %>'>
<input type="hidden" name="id" value="<%=bgblcBean.getId() %>">
<input type="hidden" name="action" value='1'>
 
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
		
			<TABLE width="100%" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
					   <table width="100%" border="0" cellpadding="0" cellspacing="0">
						   <tr height="22">
 							   <TD align="left" colspan="2" class="tbnavigation">Dữ liệu nền &gt;Sản phẩm &gt; Bảng giá bán lẻ &gt; <%if(bgblcBean.getTrangthai().equals("1")){ %> Hiển thị <%} else { %> Cập nhật <%} %> </TD>
 							   <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %>&nbsp;</td>
					     </tr>
						</table>
					 </TD>
				  </TR>	
		  	</TABLE>
			<TABLE width="100%" cellpadding="0" cellspacing="1">
			<TR ><TD >
				<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
					<TR class = "tbdarkrow">
						<TD width="30" align="left"><A href="../../BanggiabanlechuanSvl?userId=<%=userId %>" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
					    <TD width="2" align="left" ></TD>
					    <TD width="30" align="left"  >
					    <%if( !bgblcBean.getDisplay().equals("1") &&  !bgblcBean.getTrangthai().equals("1")){ %>
					    <A href="javascript: saveform()" ><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A>
					   <%} %>
					    </TD>
					    <TD width="30" height="30" align="left" ><A href="javascript: inExcel()" ><IMG src="../images/excel.gif" title="In file Excel" alt="In file Excel" border = "1"  style="border-style:outset" width="30" height="30"></A></TD>
				    	<TD align="left" >&nbsp;</TD>
					</TR>
				</TABLE>
			</TD></TR>
			</TABLE>

		<TABLE width="100%"  border = "0" cellspacing="0" cellpadding="0">
			  	<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Bão lỗi nhập liệu </LEGEND>
				
	    				<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold" rows="1" style="width: 100%" readonly="readonly" ><%=bgblcBean.getMessage()%></textarea>
						<% bgblcBean.setMessage(""); %>
						</FIELDSET>
				   </TD>
				</tr>			
		 	
			<TR>
		 	
			<TR>
				<TD >
        			
				<FIELDSET>
				<LEGEND class="legendtitle">&nbsp;Bảng giá bán &nbsp;</LEGEND>
				<TABLE width="100%" cellpadding="0" cellspacing="1">
					<TR><TD>					
						<TABLE  width="100%" cellpadding="4" cellspacing="0">
							<TR>
								<TD  width="15%" class="plainlabel">Tên bảng giá <FONT class="erroralert">*</FONT></TD>
								<TD class="plainlabel">
								  <input id="bgTen" name="bgTen" type="text" value="<%= bgblcBean.getTen() %>" style="width:300px">
							  	</TD>
							  	
							  		<TD class="plainlabel" rowspan="4" colspan="1">Loại khách hàng </TD>
		                        	<TD class="plainlabel" width="40%" rowspan="4" colspan="1">
		                        	<a href="javascript:;" onclick="selectAllLKH();">Chọn tất cả</a> | <a href="javascript:;" onclick="deSelectAllLKH();">Bỏ chọn tất cả</a>
										<br />
		                            <select name="loaikhId" id="loaikhId" multiple="multiple"  >
							        
				                        <% if(loaikhRS != null) {
				                         while(loaikhRS.next()) 
				                         {
				                           if(bgblcBean.getLoaiKhIds().indexOf(loaikhRS.getString("loaiId")) >= 0 ){ %>
				                             <option value="<%= loaikhRS.getString("loaiId") %>" selected style="padding: 2px" ><%= loaikhRS.getString("loaiTen") %></option>
				                         <%}else { %>
				                         	<option value="<%= loaikhRS.getString("loaiId") %>" style="padding: 2px"><%= loaikhRS.getString("loaiTen") %></option>
				                         <%} }}%>        	
				                    </select>
				                 </TD>
							</TR>
								
							<TR>
								<TD class="plainlabel">Đơn vị kinh doanh <FONT class="erroralert">*</FONT></TD>
								    <TD class="plainlabel">
								    <SELECT   name="" id="dvkdId"  onChange = "submitform();" style="width:300px">
								  		<option value =""></option>
								  		
								  	 <% try{ while(dvkd.next()){ 
								    	if(dvkd.getString("dvkdId").equals(bgblcBean.getDvkdId())){ %>
								      		<option value='<%=dvkd.getString("dvkdId") %>' selected><%=dvkd.getString("dvkd") %></option>
								      	<%}else{ %>
								     		<option value='<%=dvkd.getString("dvkdId") %>'><%=dvkd.getString("dvkd") %></option>
								     	<%}}}catch(java.sql.SQLException e){} %>	
                                  </select>
                                   <input name="dvkdId" type="hidden"  value="<%=bgblcBean.getDvkdId() %>">
                                  </TD>
							</TR>
							
							<TR>
								<TD class="plainlabel">Kênh bán hàng <FONT class="erroralert">*</FONT></TD>
								    <TD class="plainlabel">
								    <SELECT   name="kbhId"  id="kbhId" onChange = "submitform();" style="width:300px">
								  		<option value =""></option>
								  		
								  	 <% try{ while(kbhRs.next()){ 
								    	if(kbhRs.getString("pk_seq").equals(bgblcBean.getKbhId())){ %>
								      		<option value='<%=kbhRs.getString("pk_seq") %>' selected><%=kbhRs.getString("ten") %></option>
								      	<%}else{ %>
								     		<option value='<%=kbhRs.getString("pk_seq") %>'><%=kbhRs.getString("ten") %></option>
								     	<%}}}catch(java.sql.SQLException e){} %>	
                                  </select>
                                  <%--  <input name="kbhId" type="hidden"  value="<%=bgblcBean.getKbhId() %>"> --%>
                                  </TD>
                                  
							</TR>
							
								<TR>
								<TD  width="15%" class="plainlabel">Từ ngày <FONT class="erroralert">*</FONT></TD>
								<TD class="plainlabel">
								  <input name="tungay" type="text"  class="days"  value="<%=bgblcBean.getTungay() %>" style="width:300px">
							  	</TD>
							</TR>
							
						
							
						</TABLE>
					</TD></TR>
				</TABLE>
				<TABLE class="tabledetail" cellpadding="0" cellspacing="0" width="100%">
						<TR id="spdvkd" bgcolor="#FFFFFF">
							<TD width="100%">
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="0">
								<TR class="tbheader">
									<TH width="25%">Mã sản phẩm </TH>
									<TH width="45%">Tên sản phẩm</TH>
									<TH width="10%">Đơn vị tính</TH>
									<TH width="10%">Giá bán lẻ chuẩn</TH>
									<TH width="10%">Tiền tệ</TH>
									<TH width="5%">Luôn hiện
										<input type="checkbox" name="chon" onClick="checkedAll(document.bgblcForm.checkluonhien4)">
									</TH>
									<TH width="5%">SP chọn bán
										<input type="checkbox" name="chonban" onClick="checkedAll1(document.bgblcForm.checkban4)">
									</TH>
								</TR>
								
								<%
								int j = 0;
								String lightrow = "tblightrow";
								String darkrow = "tbdarkrow";

								for(int i =0 ; i < spIdArr.length; i ++)
								{ 
									if (j % 2 != 0) 
									{%>						
										<TR class= <%=lightrow%> >
								    <%} else 
								    {%>
										<TR class= <%= darkrow%> >
									<%}%>
											<TD align="center">
												<input type='text' name='spMaArr'  value="<%= spMaArr[i]%>" style="text-align: left"/>
											</TD>
											<TD align="center">
												<input type='text' name='spTenArr'  value="<%= spTenArr[i]%>" style="text-align: left"/>
											</TD>
											
											<TD align="center">
												<input type='text' name='donviArr'  value="<%= donviArr[i]%>" style="text-align: center"/>
											</TD>
											<TD align="center">
												<input type='text' name='dongiaArr'  value="<%= formatter2.format(Utility.parseDouble(dongiaArr[i].replace(",",""))) %>" style="text-align: right"/>
												<input type='hidden' name='spIdArr' value="<%= spIdArr[i] %>" />
												<input type='hidden' name='quycach' value="0" style="text-align: right"/>
											</TD>
											<TD  align="center">
												VNĐ
											</TD>
											<TD align="center">
											  	<% if ( checkluonhien[i].equals("1")  ) {%>
														<input type="checkbox" name="checkluonhien4" value='1' onchange="javascript:changeTick(this,<%=i %>)" checked="checked" >
														<input type="hidden" name="checkluonhien" id="checkluonhien<%=i %>" value='1'   >
												<%}else{ %>
														<input type="checkbox" name="checkluonhien4" value='1' onchange="javascript:changeTick(this,<%=i %>)">
														<input type="hidden" name="checkluonhien" id="checkluonhien<%=i %>" value='0'   >
														 
												<%} %> 
												
											</TD>
											<TD align="center">
											  	<% if ( checkban[i].equals("1")  ) {%>
														<input type="checkbox" name="checkban4" value='1' onchange="javascript:changeTick1(this,<%=i %>)" checked="checked" >
														<input type="hidden" name="checkban" id="checkban<%=i %>" value='1'   >
												<%}else{ %>
														<input type="checkbox" name="checkban4" value='1' onchange="javascript:changeTick1(this,<%=i %>)">
														<input type="hidden" name="checkban" id="checkban<%=i %>" value='0'   >
														 
												<%} %> 
												
											</TD>
						     		<% j++;
								}
								%>
							</TABLE>

							</TD>
						</TR>
					</TABLE>
					</FIELDSET>
				</td>
			</TR>
		</TABLE>
	
	</TD>
	</TR>
</Table>
</form>
</BODY>
</html>

<% if(dvkd != null) dvkd.close(); %>
<% if (sanpham != null) sanpham.close(); 
	bgblcBean.DbClose();
%>
<%}%>