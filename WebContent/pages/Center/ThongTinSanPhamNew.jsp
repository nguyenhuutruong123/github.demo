<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.beans.thongtinsanpham.IThongtinsanpham" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.NumberFormat"%>
<%@ page  import = "geso.dms.center.util.ChuyenNgu" %>

<%	NumberFormat formatter = new DecimalFormat("#,###,###"); 
	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>

<% IThongtinsanpham spBean = (IThongtinsanpham)session.getAttribute("spBean"); %>
<% 	ResultSet dvdl = (ResultSet)spBean.getDvdl(); 
	ResultSet dvkd = (ResultSet)spBean.getDvkd(); 
	ResultSet ngh = (ResultSet)spBean.getNganhHang();
	ResultSet nh = (ResultSet)spBean.getNh();
	ResultSet cl = (ResultSet)spBean.getCl();
	ResultSet nsp = (ResultSet)spBean.getNsp();
	ResultSet splist = (ResultSet)spBean.getSanphamRs();
	ResultSet loaisp = (ResultSet)spBean.getLoaiSpRs();
	
	String dvdlId = (String) spBean.getDvdlId();
	String dvkdId = (String) spBean.getDvkdId();
	String nganhhangId = (String) spBean.getNganhHangId();
	String nhId = (String)spBean.getNhId();
	String clId = (String)spBean.getClId();
	Hashtable nspIds = spBean.getNspIds();
	Hashtable<Integer, String> spIds = spBean.getSpIds();	
%>

<% String nnId = (String)session.getAttribute("nnId"); %> 
<% if(nnId == null) {
	nnId = "vi"; 
	}	
String url = util.getChuyenNguUrl("ThongtinsanphamSvl","",nnId);	
	%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Bibica - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">

<LINK rel="stylesheet" href="../css/main.css" type="text/css">

<SCRIPT language="javascript" type="text/javascript">
 function submitform()
{	
	 document.spForm.action.value='abc';
    document.forms["spForm"].submit();
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
		if(element.value== '' ||element.value=='0' )
		{
			element.value= '';
		}
	} 

 function saveform()
{
	 var type = document.getElementById("type").value;
	 var masp = document.getElementById("masp").value;
	 var tensp = document.getElementById("tensp").value;
	 var tenspvt = document.getElementById("tenviettat").value;
	 var dvdlId = document.getElementById("dvdlId").value;
	 var dvkdId = document.getElementById("dvkdId").value;
	 var nganhhangId = document.getElementById("nganhhangId").value;
	 var nhId = document.getElementById("nhId").value;
	 var clId = document.getElementById("clId").value;
	 
	 if(masp==""){
		 alert("Vui lòng điền mã sản phẩm");
		 return;
	 }
	 else if(tensp==""){
		 alert("Vui lòng điền tên sản phẩm");
		 return;
	 }
	 else if(tenspvt==""){
		 alert("Vui lòng điền tên viết tắt sản phẩm");
		 return;
	 }
	 else if(dvdlId==""){
		 alert("Vui lòng chọn đơn vị đo lường");
		 return;
	 }
	 else if(dvkdId==""){
		 alert("Vui lòng chọn đơn vị kinh doanh");
		 return;
	 }
	 else if(nganhhangId==""){
		 alert("Vui lòng chọn ngành hàng");
		 return;
	 }
	 else if(nhId==""){
		 alert("Vui lòng chọn nhãn hàng");
		 return;
	 }
	 else if(clId==""){
		 alert("Vui lòng chọn chủng loại");
		 return;
	 }
	 var kl = document.spForm.kl.value;
	 if(isNaN(kl) && kl.length > 0)
		 alert("khoi luong phai nhap so");
	/*  if(type == 1)
	 {
		if(checkBundle() == false)
		{
			alert('Bạn phải chọn sản phẩm cho Bundle');
			return;
		}
	 } */
	 
	 document.spForm.action.value='save';
     document.forms["spForm"].submit();
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
 		if (keypressed == 8 || keypressed == 127 || keypressed == 37 || keypressed == 39 || keypressed == 0 || keypressed == 46)
 		{//Phím Delete và Phím Back
 			return;
 		}
 		return false;
 	}
 }
 function checkBundle()
 {
	 var spIds = document.getElementsByName("spIds");
	 if(spIds != null)
	 {
		 for(i = 0; i < spIds.length; i++)
			 if(spIds.item(i).checked)
				 return true;
	 }
	 return false;
 }
 
 function chonSp(mm)
 {
	 var spIds = document.getElementById("spIds" + mm);
	 var spSoluong = document.getElementById("spSoLuong" + mm);
	 if(spIds != null)
	 {
		 if(spIds.checked)
		 {
			 if(spSoluong.value == '')
			 {
				 spIds.checked = false;
				 alert('Bạn phải nhập số lượng cho sản phẩm này!');
				 return;
			 }
			 else
			 {
				 spIds.value = spIds.value + '-' + spSoluong.value;
				 console.log('spIds.value : '+ spIds.value);
			 }
		 }
	 }
	
	 return false;
 }
 
 function updateUoM()
 {
	 dvdlId = document.getElementById("dvdlId").value;
	 
	 for(var i=0;i<= document.getElementById("0").length-1;i=i+1)
	 {
	 	var dvdl1=document.getElementById("0").options[i].value;
	 		 		
	 	if(dvdl1==dvdlId)
	 	{
	 		document.getElementById("0").selectedIndex=i;
	 		break;
	 	}
	 } 
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

<form name="spForm" method="post" action="../../ThongtinsanphamUpdateSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%=spBean.getUserId() %>'>
<input type="hidden" name="action" value="1">

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							 <TD align="left" colspan="2" class="tbnavigation"><%=url %> &gt; Tạo mới
							 <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %>&nbsp;  </TD></tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
			<TR ><TD >
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR class = "tbdarkrow">
							<TD width="30" align="left"><A href="javascript:history.back()" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
						    <TD width="2" align="left" ></TD>
						    <TD width="30" align="left" ><A href="javascript: saveform()" ><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A></TD>
							<TD >&nbsp; </TD>						
						</TR>
					</TABLE>
			</TD></TR>
			</TABLE>

			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1" >
			  	<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>
				
	    				<textarea name="dataerror"  style="width: 100%" readonly="readonly" rows="1" ><%=spBean.getMessage()%></textarea>
						<% spBean.setMessage(""); %>
						</FIELDSET>
				   </TD>
				</tr>			

				<TR>
					<TD>

						<TABLE width="100%" border="0" cellpadding="0" cellspacing="1" >
							<TR>
								<TD>
									<FIELDSET>
									<LEGEND class="legendtitle" style="color:black">Thông tin sản phẩm </LEGEND>
	
									<TABLE border="0" width="100%" cellpadding="4" cellspacing="0">
										<TR>
											<TD width="25%" class="plainlabel required" ><%=ChuyenNgu.get("Mã sản phẩm",nnId) %>  <FONT class="erroralert"> </FONT></TD>
											<TD width="20%" class="plainlabel"><INPUT type="text" name="masp" id="masp" style="width:300px" value = '<%=spBean.getMasp() %>'></TD>
										    <TD class="plainlabel"><%=ChuyenNgu.get("Hoạt động",nnId) %>  &nbsp;
  												<% if (spBean.getTrangthai().equals("1")){ %>
													<input name="trangthai" type="checkbox" value = "1" checked >
												<%}else{ %>
													<input name="trangthai" type="checkbox" value = "0"  >
												<%} %>
											</TD>
										</TR>	
										<%-- <TR>
											<TD class="plainlabel">Mã sản phẩm Đại Đồng Tiến<FONT class="erroralert"></FONT></TD>
										  	<TD colspan = "3" class="plainlabel"><input name="maddt" type="text" style="width:300px" value = '<%=spBean.getMaddt() %>'></TD>
						  				</TR> --%>
										<TR>
											<TD class="plainlabel required"><%=ChuyenNgu.get("Tên sản phẩm",nnId) %>  <FONT class="erroralert"></FONT></TD>
										  	<TD colspan = "3" class="plainlabel"><input name="tensp" id="tensp" type="text" style="width:500px" value = '<%=spBean.getTen() %>'></TD>
						  				</TR>
										<TR>
											<TD class="plainlabel required"><%=ChuyenNgu.get("Tên sản phẩm viết tắt",nnId) %>  <FONT class="erroralert"></FONT></TD>
										  	<TD colspan = "3" class="plainlabel"><input name="tenviettat" id="tenviettat" type="text" style="width:500px" value = '<%=spBean.getTenVietTat() %>'></TD>
						  				</TR>
						  				<%-- <TR>
											<TD class="plainlabel">Mã tắt <FONT class="erroralert"></FONT></TD>
										  	<TD colspan = "3" class="plainlabel"><input name="matat" type="text" style="width:300px" value ='<%=spBean.getMaTat()%>'></TD>
						  				</TR> --%>
										<TR>
							  				<TD class="plainlabel required"><%=ChuyenNgu.get("Đơn vị đo lường",nnId) %>  <FONT class="erroralert"> </FONT></TD>
						  	  				<TD colspan = "3" class="plainlabel">
												<select name="dvdlId" id="dvdlId" onChange="updateUoM();">
												<option value="" > </option>
												<%
													if(dvdl != null){
													try{
														while(dvdl.next()){
								  							if (dvdlId.equals(dvdl.getString("dvdlId"))){ %>											
								  								<option value='<%= dvdl.getString("dvdlId")%>' selected><%= dvdl.getString("dvdlTen")%></option>
								  		  					<%}else{ %>		
								  		  						<option value='<%= dvdl.getString("dvdlId")%>' ><%= dvdl.getString("dvdlTen")%></option>
								  							<%		}
								  						}
														dvdl.close();
								  					}catch (java.sql.SQLException e){}} %>
															
										  	  	</select>											
										  	 </TD>
										</TR>
										
										<tr>
											<TD class="plainlabel"><%=ChuyenNgu.get("Khối lượng",nnId) %>  <FONT class="erroralert"> </FONT></TD>
											<TD colspan="2" class="plainlabel">
												<INPUT type="text" name="kl" style="width:150px; text-align: right;" value = '<%= spBean.getKL() %>' onkeypress="return keypress(event);">
											&nbsp;
												<b><i>kg</i></b>
											</TD>
											
										</tr>
										
										<%-- <tr>
											<TD class="plainlabel">Thể tích <FONT class="erroralert"> </FONT></TD>
											<TD colspan="2" class="plainlabel">
												<INPUT type="text" name="tt" style="width:150px; text-align: right;" value = '<%=spBean.getTT() %>' onkeypress="return keypress(event);">
											&nbsp;
												<b><i>m<sup>3</sup></i></b>
											</TD>
										</tr> --%>
										
										<TR>
										  <TD class="plainlabel required"><%=ChuyenNgu.get("Đơn vị kinh doanh",nnId) %> <FONT class="erroralert"></FONT></TD>
										  <TD colspan="3" class="plainlabel">
										  	<select name="dvkdId" id="dvkdId" onChange="submitform();">
												<option value="" ></option>
												<% 
													if(dvkd != null){
													try{
														while(dvkd.next()){
								  							if (dvkdId.equals(dvkd.getString("dvkdId"))){%>								  								
								  								<option value='<%= dvkd.getString("dvkdId")%>' selected ><%=dvkd.getString("dvkdTen") %></option>
												<%			}
								  							else{ %>
								  								<option value='<%= dvkd.getString("dvkdId")%>'  ><%=dvkd.getString("dvkdTen") %></option>
								  				<%			}
								  						}
														dvkd.close();
								  					}catch (java.sql.SQLException e){}} %>

										    </select>
										  </TD>
									  </TR>
									  
									  <TR>
										  <TD class="plainlabel required"><%=ChuyenNgu.get("Ngành hàng",nnId) %> <FONT class="erroralert"></FONT></TD>
										  <TD colspan="3" class="plainlabel">
										  	<select name="nganhhangId" id="nganhhangId" onChange="submitform();">
												<option value="" ></option>
												<%
												
												if(ngh!=null)
												{
													
													try{
														while(ngh.next()){
															if(nganhhangId != null)
															{
																
																
																if(nganhhangId.equals(ngh.getString("PK_SEQ")))
																{
																	%>
																	
																	<option value='<%= ngh.getString("PK_SEQ")%>'  selected><%=ngh.getString("TEN") %></option>
																	<%	
																}
																else
																{
																	%>
																	<option value='<%= ngh.getString("PK_SEQ")%>'  ><%=ngh.getString("TEN") %></option>
																	<%	
																}
															}
															
															
								  						}
														ngh.close();
								  					}catch (java.sql.SQLException e){}
												}
												 %>
										    </select>
										  </TD>
									  </TR>
									  
									  
										<TR>
											  <TD class="plainlabel required"><%=ChuyenNgu.get("Nhãn hàng",nnId) %>  <FONT class="erroralert"></FONT></TD>
											  <TD colspan="3" class="plainlabel">
											  		<% if (dvkdId.length()>0){ %>
											  			
											  			<select name="nhId" id="nhId" onChange="submitform();">
											  		<%}else{ %>
											  			<select name="nhId" id="nhId" disabled="disabled">
											  		<%} %>
											  		
															<option value="" ></option>
														<% 
															if(nh != null){
															try{
																while(nh.next()){
									  							if (nhId.equals(nh.getString("nhId"))){%>								  								
							  										<option value='<%= nh.getString("nhId")%>' selected ><%=nh.getString("nhTen") %></option>
														<%		}
							  									else{ %>
							  										<option value='<%= nh.getString("nhId")%>'  ><%=nh.getString("nhTen") %></option>
							  							<%		}
															
								  								}
																nh.close();
								  							}catch (java.sql.SQLException e){}} %>
											  			
												</select>
										  </TR>
										  <TR>
											  <TD class="plainlabel required"><%=ChuyenNgu.get("Chủng loại",nnId) %> <FONT class="erroralert"> </FONT></TD>
											  <TD colspan="3" class="plainlabel">
											  		
											  		<% if ((nhId.length() >0)&(dvkdId.length()>0)){ %>
											  			<select name="clId" id="clId">
											  		<%}else{ %>
											  			<select name="clId" id="clId" disabled="disabled">
											  		<%} %>

															<option value="" ></option>
														<% 
															if(cl != null){
															try{
																while(cl.next()){
									  							if (clId.equals(cl.getString("clId"))){%>								  								
							  										<option value='<%= cl.getString("clId")%>' selected ><%=cl.getString("clTen") %></option>
														<%		}
							  									else{ %>
							  										<option value='<%= cl.getString("clId")%>'  ><%=cl.getString("clTen") %></option>
							  							<%		}															
								  								}
																cl.close();
								  							}catch (java.sql.SQLException e){}} %>
											  			

												    </select>											  </TD>
										  </TR>
										  
										   <TR> 
										  <TD class="plainlabel" colspan="3">Sản phẩm trọng tâm
										  <% if (spBean.getIsTrongtam().equals("1")){ %>
													<input name="isTrongtam" id="isTrongtam" type="checkbox" value = "1" checked >
												<%}else{ %>
													<input name="isTrongtam" id="isTrongtam" type="checkbox" value = "1"  >
												<%} %>
												</TD>
										  </TR>
										 
										  <TR style="display: none">
											  <TD colspan="3" class="plainlabel">
											  <% if(spBean.getType().equals("1")) {%>
											  		<input name="type" type="checkbox" id="type" value="1" checked="checked" onChange="submitform();"> is Bundle		
											  <%}else{ %>
											  		<input name="type" type="checkbox" id="type" value="0" onChange="submitform();"> is Bundle	
											  <%} %>				  				
											  </TD>
										  </TR>
									</TABLE>
									</FIELDSET>
									
									<% if(splist != null){ int mm = 0; %>
									  		<fieldset><legend class="legendtitle"> Chọn sản phẩm thuộc Bundle </legend>
									  		<table style="width: 100%" cellpadding="4" cellspacing="1">
									  		<tr class="tbheader">
									  			<th align="center"><%=ChuyenNgu.get("Mã sản phẩm",nnId) %> </th>
									  			<th align="center"><%=ChuyenNgu.get("Tên sản phẩm",nnId) %> </th>
									  			<th align="center" style="width:80px"><%=ChuyenNgu.get("Số lượng",nnId) %></th>
									  			<th align="center"><%=ChuyenNgu.get("Chọn",nnId) %> </th>
									  		</tr>
									  			<% while(splist.next()){ 
									  				
									  				
									  				String soluong = splist.getString("soluong");
									  				if(soluong.equals("0"))
									  					soluong = "";
									  				
									  				String values = splist.getString("pk_seq");
									  				if(soluong != "")
									  					values += "-" + soluong;
									  				
									  				if(mm % 2 == 0){	%>
									  					<tr class="tblightrow">
									  				<%}else{ %>
									  					<tr class="tbdarkrow">
									  				<%} %>
									  					<td><%= splist.getString("spMa") %></td>
									  					<td><%= splist.getString("spTen") %></td>
									  					<td><input type="text" value="<%=soluong %>" id="spSoLuong<%= Integer.toString(mm) %>" style="width:100%; text-align: right;"></td>
									  					<td align="center">
									  					<% if(soluong != ""){//spIds.contains(splist.getString("pk_seq")) %>
									  						<input type="checkbox" name="spIds" id="spIds<%= Integer.toString(mm) %>" value='<%= values %>' checked="checked" onchange="chonSp(<%= mm %>)" >
									  					<% }else{ %>
									  						<input type="checkbox" name="spIds" id="spIds<%= Integer.toString(mm) %>" value='<%= values %>' onchange="chonSp(<%= mm %>)">
									  					<% } %>
									  					</td>
									  				</tr>
									  			<% mm ++;} %>
									  		</table></fieldset>
									  <%} %>	 
									  
									<TABLE width="100%" border="0" cellpadding="0" cellspacing="0" >
										<TR>
										  	<TD >
												<FIELDSET>
												<LEGEND class="legendtitle" style="color:black"> Chọn nhóm sản phẩm</LEGEND>
												<TABLE border="0" width="100%" cellpadding="4" cellspacing="1">
													<TR class="tbheader">
														<TH width="30%" ><%=ChuyenNgu.get("Nhóm sản phẩm",nnId) %>  </TH>
														<TH width="60%" ><%=ChuyenNgu.get("Diễn giải",nnId) %>  </TH>
														<TH width="10%" ><%=ChuyenNgu.get("Chọn",nnId) %> </TH>
													</TR>
													<% try{
														String lightrow = "tblightrow";
														String darkrow = "tbdarkrow";
														int m = 0;
														if (!(nsp == null)){
															while (nsp.next()){ 
																if (m % 2 != 0) {%>						
																	<TR class= <%=lightrow%> >
															   <%} else {%>
																	<TR class= <%= darkrow%> >
								 						  	   <%}%>							
															
																<TD><%=nsp.getString("nspTen") %> </TD>
																<TD><div align="left"><%=nsp.getString("diengiai") %> </div></TD>
																<TD> <div align="center">
																<% if (nspIds.contains(nsp.getString("nspId"))){ %>
															  			<input type="checkbox" name="nspIds" value='<%=nsp.getString("nspId") %>' checked>
															  	<%}else{ %>
															  			<input type="checkbox" name="nspIds" value='<%=nsp.getString("nspId") %>' >
															  	<%} %>

																</div></TD>
															</TR>																
													<%		m++;
															}
															nsp.close();
														}
														}catch(java.sql.SQLException e){}%>
													
												</TABLE>						
												</FIELDSET>							
											</TD>
										</TR>
									</TABLE>
									
									<TABLE width = "100%" cellpadding="0" cellspacing="0">
										<TR>
											<TD>
												<FIELDSET>
												<LEGEND class="legendtitle">&nbsp;Thiết lập quy cách</LEGEND>
												<TABLE  border="1" cellpadding="0"  cellspacing="1" width="100%">
															<TR class="tbheader" >
																<TH width="21%" ><%=ChuyenNgu.get("Số lượng",nnId) %>  </TH>
																<TH width="21%" ><%=ChuyenNgu.get("Đơn vị đo lường",nnId) %>  </TH>
																<TH width="16%" ><%=ChuyenNgu.get("Quy đổi",nnId) %>  </TH>
																<TH width="21%" ><%=ChuyenNgu.get("Số lượng",nnId) %>  </TH>
																<TH width="21%" ><%=ChuyenNgu.get("Đơn vị đo lường",nnId) %>  </TH>
															</TR>
												<%			String[] sl1 = spBean.getSl1();
															String[] sl2 = spBean.getSl2();
															String[] dvdl1 = spBean.getDvdl1();	
															String[] dvdl2 = spBean.getDvdl2();
															/* if(!(dvdl1[0] == null) && !(dvdl2[0] == null))
															{
																if (!dvdl1[0].equals(dvdlId)){ 
																	dvdl1[0] = dvdlId;
																	sl1[0] = (sl1[0] == null ? "" : sl1[0]);
																}
															
																if (!dvdl2[0].equals("100018")){ 
																	dvdl2[0] = "100018";
																	sl2[0] = "";
																} 
															}
															else
															{																
												 				dvdl1[0] = dvdlId;
												 				sl1[0] = (sl1[0] == null ? "" : sl1[0]);
																dvdl2[0] = "100018";
																sl2[0] = "";										
															} */
															
														for(int i = 0; i < 5; i++){	%>
															<TR class= 'tblightrow'>
											  					<TD align="center" >
											  					<%	if (!(dvdl1[i] == null)){
											  							if (dvdl1[i].trim().length()>0){   
											  										%>
																			<input name="sl1" type="text" style="width:100%" value="<%=sl1[i] %>" >
																<%		}
											  							else{  %>
																			<input name="sl1" type="text" style="width:100%" value="">
																<%		}	
																	}else{%>											    					
																		<input name="sl1" type="text" style="width:100%" value="">
																<%	} %>										
																</TD>
															  	<TD align="center" >
																	<% if (i == 0){ %>
																		<select name="dvdl1" id = "<%= i %>" style="width:100%;height:22" onChange="updateUoM();">
																	<%}else{ %>
																		<select name="dvdl1" id = "<%= i %>" style="width:100%;height:22" >
																	<%} %>
																	<option value=""> </option>																	 	
																	<%	dvdl = spBean.createDvdlRS();												
																		try{
																			while(dvdl.next()){
																				if(!(dvdl1[i] == null)){
																					if (dvdl1[i].equals(dvdl.getString("dvdlId"))){%>																																																																					
																						<option value="<%= dvdl.getString("dvdlId") %>" selected><%= dvdl.getString("dvdlTen")%> </option>
																		<%			}else{ %>
																						<option value="<%= dvdl.getString("dvdlId") %>" ><%= dvdl.getString("dvdlTen")%> </option>
																		<%			}
																				}else{ %>
																					<option value="<%= dvdl.getString("dvdlId") %>" ><%= dvdl.getString("dvdlTen")%> </option>
																		<%		}
																			}
																			dvdl.close();
																		}catch(java.sql.SQLException e){}%>
																			
																	
																    </select>
															  	</TD>
															  	<TD align="center" > = </TD>
											  					<TD align="center" >
											  					<%	if (!(dvdl2[i] == null)){
											  							if (dvdl2[i].trim().length()>0){%>   
											  									
																			<input name="sl2" type="text" style="width:100%" value=<%=sl2[i] %> >
																<%		}
											  							else{  %>
																			<input name="sl2" type="text" style="width:100%" value="">
																<%		}	
																	}else{%>											    					
																		<input name="sl2" type="text" style="width:100%" value="">
																<%	} %>										
																</TD>
															  	<TD align="center" >
																	<select name="dvdl2" style="width:100%;height:22" >
																<%-- 	<% if (i == 0){ %>
																		<option value="100018">Thùng </option>
																	<%}else{ %> --%>
																	<option value=""> </option>
																	<% 	
																		dvdl = spBean.createDvdlRS();
																	try{
																		while(dvdl.next()){
																			if(!(dvdl2[i] == null)){
																				if (dvdl2[i].equals(dvdl.getString("dvdlId"))){%>																																																																					
																					<option value="<%= dvdl.getString("dvdlId") %>" selected><%= dvdl.getString("dvdlTen")%> </option>
																	<%			}else{ %>
																					<option value="<%= dvdl.getString("dvdlId") %>" ><%= dvdl.getString("dvdlTen")%> </option>
																	<%			}
																			}else{ %>
																				<option value="<%= dvdl.getString("dvdlId") %>" ><%= dvdl.getString("dvdlTen")%> </option>
																	<%		}
																		}
																			dvdl.close();
																		}catch(java.sql.SQLException e){}%>
																	<%-- <%} %>		 --%>
																    </select>											  
															  	</TD>
														  	</TR>
														<% }%>
												</TABLE>
												</FIELDSET>
											</TD>
										</TR>
									</TABLE>
								</TD>
							</TR>
						</TABLE>
						
					</TD>
	      		</TR>
		  	</TABLE>
		</TD>
	
</TABLE>
</form>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</HTML>

<% 	if(dvdl != null) dvdl.close(); 
	if(dvkd != null) dvkd.close(); 
	if(nh != null) nh.close();
	if(cl != null) cl.close();
	if(nsp != null) nsp.close();
	 spBean.DBClose();
}%>