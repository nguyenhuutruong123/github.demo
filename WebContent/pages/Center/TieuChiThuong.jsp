<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.beans.tieuchithuong.ITieuchithuongList" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%@page import="java.util.Calendar"%>
<%@ page  import = "geso.dms.center.util.ChuyenNgu" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/HTP/index.jsp");
	}else{ %>

<% 	ITieuchithuongList obj = (ITieuchithuongList)session.getAttribute("obj"); 

	ResultSet dvkd = obj.getdvkd();
	String dvkdId = obj.getdvkdId();	

	ResultSet kbh = obj.getKbh();
	String kbhId = obj.getkbhId();	
	
	ResultSet tct = obj.getTct();
	int[] quyen = new  int[5];
	quyen = util.Getquyen("TieuChiThuongSvl","",userId);
	
	System.out.println(quyen[0]);
	System.out.println(quyen[1]);
	System.out.println(quyen[2]);
	System.out.println(quyen[3]);
	System.out.println(quyen[4]);
%>
<% String nnId = (String)session.getAttribute("nnId"); %> 
<% if(nnId == null) {
 nnId = "vi"; 
 }
String url = util.getChuyenNguUrl("TieuChiThuongSvl","",nnId);	
 %>
 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>HTP - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">

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

function thongbao()
{   var tt = document.forms['tctForm'].msg.value;
	if(tt.length>0)
    	alert(document.forms['tctForm'].msg.value);
	
	document.forms['tctForm'].msg.value= '';
}

function submitForm()
{	
	
	document.tctForm.action.value='timkiem';
    document.forms["tctForm"].submit();
}
 
function saveForm()
{	
	
	document.tctForm.action.value='taomoi';
    document.forms["tctForm"].submit();
}

/*  function checklimit(m)
 {
	 var boo = false;
 	 if(document.getElementById("ngaysua"+m).value != "")
 		{		
 			var now = new Date();
 			var month = now.getMonth() + 1;
 			var date = now.getDate();
 			if(month < 10)
 			{month = "0"+month;}	
 			if(date < 10)
 			{date = "0"+date;}
 			var ngaysua = Date.parse(document.getElementById("ngaysua"+m).value);
 			var ngayhientai = Date.parse(now.getFullYear()+"-"+month+"-"+date);	
 			if(ngayhientai - ngaysua > 0)
 			{
 				
 				alert('Tiêu chí này đã hết hạn mở chốt. Vui lòng liên hệ quản trị !');
 				return;
 			}
 			boo = true;
 			
 	}
 	// document.forms["ddkdForm"].submit();
 } */


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

<form name="tctForm" method="post" action="../../TieuChiThuongSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%= obj.getUserId() %>'>
<input type="hidden" name="action" value="1">
<input type="hidden" name="msg" value='<%= obj.getMsg() %>'>
<script language="javascript" type="text/javascript"> thongbao(); </script>
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							 <TD align="left" colspan="2" class="tbnavigation">
							 	<%=" "+url %>
							</TD>
							 <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %>&nbsp;  </TD></tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>

			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1" >
				<TR>
					<TD>

						<TABLE width="100%" border="0" cellpadding="0" cellspacing="1" >
							<TR>
								<TD>
									<FIELDSET>
									<LEGEND class="legendtitle" style="color:black">Tiêu chí tìm kiếm </LEGEND>
	
									<TABLE border="0" width="100%" cellpadding="4" cellspacing="0">
										<TR>
										  	 <TD class="plainlabel" colspan=2>
												<%if(obj.getLoai().equals("1")){ %>									  	 
									  	 			<INPUT TYPE="radio" NAME="loai" value="1" checked onChange="submitForm();" ><%=ChuyenNgu.get("NVBH",nnId) %>
									  	 			<INPUT TYPE="radio" NAME="loai" value="2" onChange="submitForm();"><%=ChuyenNgu.get("GSBH",nnId) %>
									  	 			<INPUT TYPE="radio" NAME="loai" value="3" onChange="submitForm();"><%=ChuyenNgu.get("ASM",nnId) %>
									  	 			<INPUT TYPE="radio" NAME="loai" value="4" onChange="submitForm();"><%=ChuyenNgu.get("RSM",nnId) %>
									  	 			<INPUT TYPE="radio" NAME="loai" value="5" onChange="submitForm();"><%=ChuyenNgu.get("Nhà phân phối",nnId) %>
									  	 			
									  	 		<%}else{
									  	 				if(obj.getLoai().equals("2")){ %>
									  	 					<INPUT TYPE="radio" NAME="loai" value="1" onChange="submitForm();"><%=ChuyenNgu.get("NVBH",nnId) %>
									  	 					<INPUT TYPE="radio" NAME="loai" value="2" checked onChange="submitForm();"><%=ChuyenNgu.get("GSBH",nnId) %>									  	 		
									  	 					<INPUT TYPE="radio" NAME="loai" value="3" onChange="submitForm();"><%=ChuyenNgu.get("ASM",nnId) %>
									  	 					<INPUT TYPE="radio" NAME="loai" value="4" onChange="submitForm();"><%=ChuyenNgu.get("RSM",nnId) %>
									  	 					<INPUT TYPE="radio" NAME="loai" value="5" onChange="submitForm();"><%=ChuyenNgu.get("Nhà phân phối",nnId) %>
									  	 			  <%}else{ 
									  	 				 if(obj.getLoai().equals("3")){ %>
									  	 					<INPUT TYPE="radio" NAME="loai" value="1" onChange="submitForm();"><%=ChuyenNgu.get("NVBH",nnId) %>
									  	 					<INPUT TYPE="radio" NAME="loai" value="2" onChange="submitForm();"><%=ChuyenNgu.get("GSBH",nnId) %>
									  	 					<INPUT TYPE="radio" NAME="loai" value="3" checked onChange="submitForm();"><%=ChuyenNgu.get("ASM",nnId) %>
									  	 					<INPUT TYPE="radio" NAME="loai" value="4" onChange="submitForm();"><%=ChuyenNgu.get("RSM",nnId) %>
									  	 					<INPUT TYPE="radio" NAME="loai" value="5" onChange="submitForm();"><%=ChuyenNgu.get("Nhà phân phối",nnId) %>
									  	 				<%}else{ 
										  	 				 if(obj.getLoai().equals("4")){ %>
										  	 					<INPUT TYPE="radio" NAME="loai" value="1" onChange="submitForm();"><%=ChuyenNgu.get("NVBH",nnId) %>
										  	 					<INPUT TYPE="radio" NAME="loai" value="2" onChange="submitForm();"><%=ChuyenNgu.get("GSBH",nnId) %>
										  	 					<INPUT TYPE="radio" NAME="loai" value="3"  onChange="submitForm();"><%=ChuyenNgu.get("ASM",nnId) %>
										  	 					<INPUT TYPE="radio" NAME="loai" value="4" checked onChange="submitForm();"><%=ChuyenNgu.get("RSM",nnId) %>
										  	 					<INPUT TYPE="radio" NAME="loai" value="5" onChange="submitForm();"><%=ChuyenNgu.get("Nhà phân phối",nnId) %>
										  	 				<%}else{ %>
									  	 					<INPUT TYPE="radio" NAME="loai" value="1" onChange="submitForm();"><%=ChuyenNgu.get("NVBH",nnId) %>
									  	 					<INPUT TYPE="radio" NAME="loai" value="2" onChange="submitForm();"><%=ChuyenNgu.get("GSBH",nnId) %>
									  	 					<INPUT TYPE="radio" NAME="loai" value="3" onChange="submitForm();"><%=ChuyenNgu.get("ASM",nnId) %>
									  	 					<INPUT TYPE="radio" NAME="loai" value="4"  onChange="submitForm();"><%=ChuyenNgu.get("RSM",nnId) %>
									  	 					<INPUT TYPE="radio" NAME="loai" value="5" checked onChange="submitForm();"><%=ChuyenNgu.get("Nhà phân phối",nnId) %>
										  	 	<%}}}}%>
										  	 </TD>
										</TR>
										<%-- <TR>
							  				<TD class="plainlabel" width = "20%" >Kênh bán hàng </TD>
						  	  				<TD class="plainlabel"  >
												<select name="kbhId" id="kbhId" onChange="submitForm();">
												<option value="" > </option>
												<%
													if(kbh != null){
													try{
														while(kbh.next()){
								  							if (kbhId.equals(kbh.getString("kbhId"))){ %>											
								  								<option value='<%= kbh.getString("kbhId")%>' selected><%= kbh.getString("kbh")%></option>
								  		  					<%}else{ %>		
								  		  						<option value='<%= kbh.getString("kbhId")%>' ><%= kbh.getString("kbh")%></option>
								  							<%		}
								  						}
														kbh.close();
								  					}catch (java.sql.SQLException e){}} %>
															
										  	  	</select>											
										  	 </TD>
										</TR> --%>

										<TR>
							  				<TD class="plainlabel"  ><%=ChuyenNgu.get("Đơn vị kinh doanh",nnId) %></TD>
						  	  				<TD class="plainlabel"  >
												<select name="dvkdId" id="dvkdId" onChange="submitForm();">
												<option value="" > </option>
												<%
													if(dvkd != null){
													try{
														while(dvkd.next()){
								  							if (dvkdId.equals(dvkd.getString("dvkdId"))){ %>											
								  								<option value='<%= dvkd.getString("dvkdId")%>' selected><%= dvkd.getString("dvkd")%></option>
								  		  					<%}else{ %>		
								  		  						<option value='<%= dvkd.getString("dvkdId")%>' ><%= dvkd.getString("dvkd")%></option>
								  							<%		}
								  						}
														dvkd.close();
								  					}catch (java.sql.SQLException e){}} %>
															
										  	  	</select>											
										  	 </TD>
										</TR>

								<TR>	
									<TD class="plainlabel"><%=ChuyenNgu.get("Chọn tháng",nnId) %></TD>
									<TD class="plainlabel" >										
										<SELECT name = "month" onChange="submitForm();">
											<option value = "" selected>&nbsp</option>
										<%if(obj.getMonth().equals("1")){ %>
											<option value = "1" selected><%=ChuyenNgu.get("T1",nnId) %></option>
										<%}else{ %>
											<option value = "1"><%=ChuyenNgu.get("T1",nnId) %></option>
										<%} %>

										<%if(obj.getMonth().equals("2")){ %>
											<option value = "2" selected><%=ChuyenNgu.get("T2",nnId) %></option>
										<%}else{ %>
											<option value = "2"><%=ChuyenNgu.get("T2",nnId) %></option>
										<%} %>

										<%if(obj.getMonth().equals("3")){ %>
											<option value = "3" selected><%=ChuyenNgu.get("T3",nnId) %></option>
										<%}else{ %>
											<option value = "3"><%=ChuyenNgu.get("T3",nnId) %></option>
										<%} %>

										<%if(obj.getMonth().equals("4")){ %>
											<option value = "4" selected><%=ChuyenNgu.get("T4",nnId) %></option>
										<%}else{ %>
											<option value = "4"><%=ChuyenNgu.get("T4",nnId) %></option>
										<%} %>

										<%if(obj.getMonth().equals("5")){ %>
											<option value = "5" selected><%=ChuyenNgu.get("T5",nnId) %></option>
										<%}else{ %>
											<option value = "5"><%=ChuyenNgu.get("T5",nnId) %></option>
										<%} %>

										<%if(obj.getMonth().equals("6")){ %>
											<option value = "6" selected><%=ChuyenNgu.get("T6",nnId) %></option>
										<%}else{ %>
											<option value = "6"><%=ChuyenNgu.get("T6",nnId) %></option>
										<%} %>

										<%if(obj.getMonth().equals("7")){ %>
											<option value = "7" selected><%=ChuyenNgu.get("T7",nnId) %></option>
										<%}else{ %>
											<option value = "7"><%=ChuyenNgu.get("T7",nnId) %></option>
										<%} %>

										<%if(obj.getMonth().equals("8")){ %>
											<option value = "8" selected><%=ChuyenNgu.get("T8",nnId) %></option>
										<%}else{ %>
											<option value = "8"><%=ChuyenNgu.get("T8",nnId) %></option>
										<%} %>
										
										<%if(obj.getMonth().equals("9")){ %>
											<option value = "9" selected><%=ChuyenNgu.get("T9",nnId) %></option>
										<%}else{ %>
											<option value = "9"><%=ChuyenNgu.get("T9",nnId) %></option>
										<%} %>
										
										<%if(obj.getMonth().equals("10")){ %>
											<option value = "10" selected><%=ChuyenNgu.get("T10",nnId) %></option>
										<%}else{ %>
											<option value = "10"><%=ChuyenNgu.get("T10",nnId) %></option>
										<%} %>
										
										<%if(obj.getMonth().equals("11")){ %>
											<option value = "11" selected><%=ChuyenNgu.get("T11",nnId) %></option>
										<%}else{ %>
											<option value = "11"><%=ChuyenNgu.get("T11",nnId) %></option>
										<%} %>
										
										<%if(obj.getMonth().equals("12")){ %>
											<option value = "12" selected><%=ChuyenNgu.get("T12",nnId) %></option>
										<%}else{ %>
											<option value = "12"><%=ChuyenNgu.get("T12",nnId) %></option>
										<%} %>
										</SELECT>									
									</TD>
								</TR>
								<TR>
									<TD class="plainlabel"><%=ChuyenNgu.get("Chọn năm",nnId) %></TD>
									
									<TD class="plainlabel">
										<select name="year" style="width :100px" onChange="submitForm();">
										<option value=""></option>  
										<%
										Calendar cal=Calendar.getInstance();
										int year_=cal.get(Calendar.YEAR);
										for(int n=year_ - 2; n<year_+3; n++) {
										  if(obj.getYear().equals( Integer.toString(n)) ){									  
										%>
											<option value=<%=n %> selected="selected" > <%=n %></option> 
										<%
										  }else{
										 %>
											<option value=<%=n %> ><%=n %></option> 
										<% } }
										%>
										</select>
									</TD>	
							
								</TR>
								<%-- <TR>
									<TD class="plainlabel" >Từ ngày</TD>
									<td class="plainlabel">
			                            <input type="text"  class="days" size="11"
			                                    id="tungay" name="tungay" value="<%= obj.getTungay() %>" maxlength="10" readonly onChange="submitform();"/>
			                    	</td>
								</TR>
								<TR>
			                    	 <TD class="plainlabel" >Đến ngày</TD>
							      <td class="plainlabel">
			                            <input type="text"  class="days" size="11"
			                                    id="denngay" name="denngay" value="<%= obj.getDenngay() %>" maxlength="10" readonly onChange="submitform();"/>
			                    	</td>
								</TR> --%>
								
								<tr class="plainlabel"> 
									<td colspan="2" > 
                             			<a class="button3" href="javascript:submitForm()">
                           				<img style="top: -4px;" src="../images/Search30.png" alt=""><%=ChuyenNgu.get("Tìm kiếm",nnId) %> </a>   &nbsp;&nbsp;&nbsp;
	                           		</td>
	                           	</tr>

								</TABLE>
								</FIELDSET>
																	
									<TABLE width = "100%" cellpadding="0" cellspacing="0">
										<TR>
											<TD>
											<% String tieude = "";
													if(obj.getLoai().equals("1"))
														tieude =  ChuyenNgu.get("Tiêu chi tính thưởng cho TDV",nnId);
													else if(obj.getLoai().equals("2"))
														tieude =  ChuyenNgu.get("Tiêu chi tính thưởng cho GSBH",nnId);
													else if(obj.getLoai().equals("3"))
														tieude =  ChuyenNgu.get("Tiêu chi tính thưởng cho ASM",nnId);
													else if(obj.getLoai().equals("4"))
														tieude =  ChuyenNgu.get("Tiêu chi tính thưởng cho RMS",nnId);
													else if(obj.getLoai().equals("5"))
														tieude =  ChuyenNgu.get("Tiêu chi tính thưởng cho NPP",nnId);
														
										%>
												<FIELDSET>
												
												<LEGEND class="legendtitle">&nbsp;<%=tieude %>&nbsp;&nbsp;&nbsp;
												<%if(quyen[Utility.THEM]!=0) {%>
													<a class="button3" href="javascript:saveForm()">
								    				<img style="top: -4px;" src="../images/New.png" ><%=ChuyenNgu.get("Tạo mới",nnId) %> </a>                            
												<%} %>
												</LEGEND>
												
												
												
												<TABLE  border="0" cellpadding="4"  cellspacing="1" width="100%">
															<TR class="tbheader" >
																<TH width="18%" ><%=ChuyenNgu.get("Diễn giải",nnId) %> </TH>
<!-- 																<TH width="10%" >Kênh bán hàng</TH>															 -->
																<TH width="12%" ><%=ChuyenNgu.get("Đơn vị kinh doanh",nnId) %> </TH>
																<TH width="5%" ><%=ChuyenNgu.get("Tháng",nnId) %> </TH>
																<TH width="5%" ><%=ChuyenNgu.get("Năm",nnId) %></TH>
																<TH width="14%" ><%=ChuyenNgu.get("Người tạo",nnId) %></TH>
																<TH width="7%" ><%=ChuyenNgu.get("Ngày tạo",nnId) %></TH>
																<TH width="14%" ><%=ChuyenNgu.get("Người sửa",nnId) %></TH>
																<TH width="7%" ><%=ChuyenNgu.get("Ngày sửa",nnId) %></TH>
																<TH width="8%" ><%=ChuyenNgu.get("Tác vụ",nnId) %></TH>
															</TR>
												<%	
												if(tct != null){
													try{
														int m = 0;
														while(tct.next()){
																					
															if(m%2 == 0){						%>
															<TR class= 'tblightrow'>
															<%}else{ %>
															<TR class= 'tbdarkrow'>
															<%} %>
											  					<TD align="left" ><%= tct.getString("diengiai") %></TD>								

<%-- 											  					<TD align="center" ><%= tct.getString("kbh") %></TD> --%>
																
																<TD align="center" > <%= tct.getString("dvkd") %></TD>
																
											  					<TD align="center" ><%= tct.getString("thang") %> </TD>
														
																
											  					<TD align="center" ><%= tct.getString("nam") %> </TD>
																
																<TD align="center"><%=tct.getString("nguoitao")%></TD>	
																
																<TD align="center"><%= tct.getString("ngaytao").substring(0,10) %></TD>
															
																<TD align="center"><%=tct.getString("nguoisua")%></TD>
																
																<TD align="center"><%=tct.getString("ngaysua").substring(0,10)%>
																<input type="hidden" id = "ngaysua<%=m %>" value = "<%=tct.getString("ngaysua").substring(0,10)%>">
																</TD>
																
																
																<TD align="center">
																	<TABLE border = 0 cellpadding="0" cellspacing="0">
																		<TR>
																		<%if(!tct.getString("trangthai").equals("1")){ %>
																			<TD>
																				<%if(quyen[2]!=0) {%>
													  							<A href = "../../RouterSvl?task=<%= util.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "TieuChiThuongUpdateSvl?userId="+userId+"&capnhat="+ tct.getString("pk_seq")+"") %>">
                                               									<img src="../images/Edit20.png" alt="Cap nhat" width="20" height="20" longdesc="Cap nhat" title="Cập nhật" border = 0></A>
																				<%} %>
																			</TD>
																			<TD>&nbsp;</TD>
																			<TD>
																				<%if(quyen[1]!=0) {%>
														  						<A href = "../../RouterSvl?task=<%= util.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "TieuChiThuongSvl?userId="+userId+"&xoa="+ tct.getString("pk_seq")+";"+obj.getLoai()+"") %>">
    	                                            							<img src="../images/Delete20.png" alt="Xoa" width="20" height="20" longdesc="Xoa" title="Xóa" border=0 onclick="if(!confirm('Bạn có muốn xóa tiêu chí tính thưởng này?')) return false;"></A>
        	                                        							<%} %>
        	                                        						</TD>
            	                                    						<TD>&nbsp;</TD>
            	                                    					
																			<TD>
																			<%if(quyen[4]!=0){ %>
													  						<A href = "../../RouterSvl?task=<%= util.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "TieuChiThuongSvl?userId="+userId+"&chot="+ tct.getString("pk_seq")+";"+obj.getLoai()+"") %>">
                                                							<img src="../images/Chot.png" alt="Chot" width="20" height="20" longdesc="Chot" border=0 title="Chốt" onclick="if(!confirm('Bạn có muốn chốt tiêu chí tính thưởng này?')) return false;"></A>
                                                							<%} %>
                                                							</TD>
																	 	<%}else{ %>
																			<TD>
																			<%if(quyen[3]!=0){ %>
													  							<A href = "../../RouterSvl?task=<%= util.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "TieuChiThuongUpdateSvl?userId="+userId+"&hienthi="+ tct.getString("pk_seq")+"")%>">
                                               									<img src="../images/Display20.png" alt="Hien thi" width="20" height="20" longdesc="Hien thi" title="Hiển thị" border = 0></A>
																			<%} %>
																			</TD>
																			
																		<%-- 	<TD>
													  							<A href = "../../TieuChiThuongSvl?userId=<%=userId%>&mochot=<%= tct.getString("pk_seq")%>">
                                               									<img src="../images/unChot.png" alt="Mở chốt" width="20" height="20" longdesc="Mo chot" border = 0></A>
																			</TD>
            	                                    						 --%>
																			<TD>
																				<%if(quyen[0]!=0){ %>
													  							<A href = "../../RouterSvl?task=<%= util.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "TieuChiThuongUpdateSvl?userId="+userId+"&copy="+ tct.getString("pk_seq")+"")%>">
                                               									<img src="../images/Copy.png" alt="Sao chep" width="20" height="20" longdesc="Sao chep" title="Sao chép" border = 0></A>
																				<%} %>
																			</TD>
																			
																			<TD>
																				<%if(quyen[0]!=0){ %>
																				
																				<A href = "../../RouterSvl?task=<%= util.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "TieuChiThuongSvl?userId="+userId+"&mochot="+ tct.getString("pk_seq")+";"+obj.getLoai()+"") %>">
                                                								<img src="../images/unChot.png" alt="Mo chot" width="20" height="20" longdesc="Mo chot" border=0 title="Mở chốt" onclick="if(!confirm('Bạn có muốn mở chốt tiêu chí tính thưởng này?')) return false;"></A>
																				<%} %>
																			</TD>
            	                                    					
																		<%} %>																	
																		</TR>
																		
																	</TABLE>
																</TD>
																
															</TR>
												<%	m++;															
														}
														}catch(java.sql.SQLException e){}
														  	
												 }%>
												 <tr class="tbfooter" > 
													 <TD align="center" valign="middle" colspan="10" class="tbfooter">&nbsp;</TD>
												</tr>
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

<% 	if(dvkd != null) dvkd.close(); 
	if(tct != null) tct.close();
	obj.closeDB();
}%>