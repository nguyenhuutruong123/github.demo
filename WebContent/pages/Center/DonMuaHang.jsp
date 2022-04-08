<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.beans.donmuahang.IDonmuahangList" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	System.out.println(userId);
	System.out.println(userTen);
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ int[] quyen = new  int[5];
		quyen = util.Getquyen("36",userId);
		
		System.out.println(quyen[0]);
		System.out.println(quyen[1]);
		System.out.println(quyen[2]);
		System.out.println(quyen[3]);	
		System.out.println(quyen[4]);%>

<% IDonmuahangList obj = (IDonmuahangList)session.getAttribute("obj"); %>
<% ResultSet kvlist = (ResultSet)obj.getKvList(); %>
<% ResultSet dhlist = (ResultSet)obj.getDhList(); %>
<% obj.setNextSplittings(); %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<script type="text/javascript" language="JavaScript" src="../scripts/Numberformat.js"></script>
<script type="text/javascript" src="../scripts/phanTrang.js"></script>

<link rel="stylesheet" type="text/css" href="../css/autocomplete.css" />
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
    <script type="text/javascript" src="../scripts/cool_DHTML_tootip.js"></script>
<script type="text/javascript" src="../scripts/jquery-1.4.2.min.js"></script>
<script src="../scripts/jquery.autocomplete.js"></script>

<script type="text/javascript" src="../scripts/jquery.js"></script>


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
  <style type="text/css">
		#dhtmltooltip
		{
			position: absolute;
			left: -300px;
			width: 150px;
			border: 1px solid black;
			padding: 2px;
			background-color: lightyellow;
			visibility: hidden;
			z-index: 100;
			/*Remove below line to remove shadow. Below line should always appear last within this CSS*/
			filter: progid:DXImageTransform.Microsoft.Shadow(color=gray,direction=135);
			font-size: 13;
		}	
		#dhtmlpointer
		{
			position:absolute;
			left: -300px;
			z-index: 101;
			visibility: hidden;
		}
		th {
		cursor: pointer;
		}	
  	</style>

<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
<script type="text/javascript" language="JavaScript" src="../scripts/simplecalendar.js"></script>

<SCRIPT language="javascript" type="text/javascript">

function submitForm(arg){
	
	document.forms["ddhForm"].action.value = arg;
	document.forms["ddhForm"].submit();
	
}
function submitform()
{   
   document.forms["ddhForm"].submit();
}

function clearform()
{
    document.forms["ddhForm"].sku.value = "";
    document.forms["ddhForm"].kvId.value = "";
    document.forms["ddhForm"].tungay.value = "";
    document.forms["ddhForm"].denngay.value = "";
    document.forms["ddhForm"].trangthai.value = "";
    document.forms["ddhForm"].so.value = "";
    document.forms["ddhForm"].nppTen.value = "";
    document.forms["ddhForm"].submit();
}

function  newform(){
	document.forms["ddhForm"].action.value ="new";
	  document.forms["ddhForm"].submit();
}
function processing(id,chuoi){
	 document.getElementById(id).innerHTML = "<a href='#'><img src='../images/waiting.gif' width = '20' height = '20' title='cho thuc hien..' border='0' longdesc='cho thuc hien..' style='border-style:outset'> Proc...</a>"; 		  
	 document.getElementById(id).href=chuoi;
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

	<form id="ddhForm" name="ddhForm" method="post"
		action="../../DonmuahangSvl"> 
 <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
		<input type="hidden" name="userId" value='<%=userId%>'> 
		<input type="hidden" name="userTen" value='<%=userTen%>'> 
		<input
			type="hidden" name="action" value="1"> 
		<input type="hidden" name="crrApprSplitting" value="<%= obj.getCrrApprSplitting() %>">
		 <input type="hidden" name="nxtApprSplitting" value="<%= obj.getNxtApprSplitting() %>">



		<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
			height="100%" bgcolor="#FFFFFF">
			<TR>
				<TD colspan="4" align='left' valign='top'>
					<!--begin body Dossier--> <!--begin common dossier info---> <!--End common dossier info--->


					<TABLE width="100%" cellspacing="0" cellpadding="0">
						<TR>
							<TD>
								<TABLE width="100%" cellspacing="1" cellpadding="0">
									<TR>
										<TD align="left" class="tbnavigation">
											<table width="100%" border="0" cellpadding="0"
												cellspacing="0">
												<tr height="22">
													<TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý bán hàng
														&gt; Duyệt đơn hàng</TD>

													<TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=obj.getTen() %> &nbsp;</TD>
												</tr>
											</table></TD>
									</TR>
								</TABLE>

								<TABLE border="0" width="100%" cellspacing=0 cellpadding=0>

									<TR>
										<TD width="100%" align="left">
											<FIELDSET>
												<LEGEND class="legendtitle">&nbsp;Tiêu chí hiển thị
													&nbsp;</LEGEND>

												<TABLE width="100%" cellpadding="4" cellspacing="0">
													<TR>
														<TD width="19%" class="plainlabel">Chứa SKU</TD>
														<TD width="81%" class="plainlabel"><input type="text"
															id="sku" name="sku" value="<%= obj.getSKU() %>" size=40 />

														</TD>
													</TR>
													<TR>
														<TD class="plainlabel">Từ ngày</TD>
														<TD class="plainlabel">
															<TABLE cellpadding="0" cellspacing="0">
																<TR>
																	<TD><input class="days" type="text" name="tungay"
																		value="<%=obj.getTungay() %>" size="20"></TD>

																</TR>
															</TABLE></TD>
													</TR>
													<TR>
														<TD class="plainlabel">Đến ngày</TD>
														<TD class="plainlabel">
															<TABLE cellpadding="0" cellspacing="0">
																<TR>
																	<TD><input class="days" type="text" name="denngay"
																		value="<%=obj.getDenngay() %>" size="20"></TD>

																</TR>
															</TABLE></TD>

													</TR>
													<TR>
														<TD class="plainlabel">Trạng thái</TD>
														<TD class="plainlabel"><select id="trangthai" name="trangthai">
																<%if (obj.getTrangthai().equals("0")){ %>
																<option value=""></option>
																<option value="0" selected>Nhà PP Chưa Chốt</option>
																<option value="1" >Nhà PP đã xác nhận/Chờ duyệt</option>
																<option value="2">Trung tâm đã duyệt</option>
																<option value="3">Đã xuất HDTC</option>
																<option value="4">Đang giao hàng</option>
																<option value="5">Hoàn tất</option>
																<option value="6">Đã hủy</option>
																<%}else 							
								  						if (obj.getTrangthai().equals("1")){ %>
								  								<option value=""></option>
																<option value="0" >Nhà PP Chưa Chốt</option>
																<option value="1" selected >Nhà PP đã xác nhận/Chờ duyệt</option>
																<option value="2">Trung tâm đã duyệt</option>
																<option value="3">Đã xuất HDTC</option>
																<option value="4">Đang giao hàng</option>
																<option value="5">Hoàn tất</option>
																<option value="6">Đã hủy</option>
																<%}else
														if (obj.getTrangthai().equals("2")){ %>
																<option value=""></option>
																<option value="0" >Nhà PP Chưa Chốt</option>
																<option value="1"  >Nhà PP đã xác nhận/Chờ duyệt</option>
																<option value="2" selected>Trung tâm đã duyệt</option>
																<option value="3">Đã xuất HDTC</option>
																<option value="4">Đang giao hàng</option>
																<option value="5">Hoàn tất</option>
																<option value="6">Đã hủy</option>
													<%} else											   	 
														if (obj.getTrangthai().equals("3")){ %>
																<option value=""></option>
																<option value="0" >Nhà PP Chưa Chốt</option>
																<option value="1"  >Nhà PP đã xác nhận/Chờ duyệt</option>
																<option value="2" >Trung tâm đã duyệt</option>
																<option value="3" selected>Đã xuất HDTC</option>
																<option value="4">Đang giao hàng</option>
																<option value="5">Hoàn tất</option>
																<option value="6">Đã hủy</option>

																<%	} else  if(obj.getTrangthai().equals("4")) { %>
																	<option value=""></option>
																<option value="0" >Nhà PP Chưa Chốt</option>
																<option value="1"  >Nhà PP đã xác nhận/Chờ duyệt</option>
																<option value="2" >Trung tâm đã duyệt</option>
																<option value="3" >Đã xuất HDTC</option>
																<option value="4" selected>Đang giao hàng</option>
																<option value="5">Hoàn tất</option>
																<option value="6">Đã hủy</option>
																	
																 <%} else if(obj.getTrangthai().equals("5")){ %> 
																 	<option value=""></option>
																<option value="0" >Nhà PP Chưa Chốt</option>
																<option value="1"  >Nhà PP đã xác nhận/Chờ duyệt</option>
																<option value="2" >Trung tâm đã duyệt</option>
																<option value="3" >Đã xuất HDTC</option>
																<option value="4" >Đang giao hàng</option>
																<option value="5" selected>Hoàn tất</option>
																<option value="6">Đã hủy</option>
																 <%}	else if(obj.getTrangthai().equals("6")){ %> 
																 	<option value=""></option>
																<option value="0" >Nhà PP Chưa Chốt</option>
																<option value="1"  >Nhà PP đã xác nhận/Chờ duyệt</option>
																<option value="2" >Trung tâm đã duyệt</option>
																<option value="3" >Đã xuất HDTC</option>
																<option value="4" >Đang giao hàng</option>
																<option value="5">Hoàn tất</option>
																<option value="6" selected>Đã hủy</option>
																  <%} else { %>
																		<option value="" selected></option>
																<option value="0" >Nhà PP Chưa Chốt</option>
																<option value="1"  >Nhà PP đã xác nhận/Chờ duyệt</option>
																<option value="2" >Trung tâm đã duyệt</option>
																<option value="3" >Đã xuất HDTC</option>
																<option value="4" >Đang giao hàng</option>
																<option value="5">Hoàn tất</option>
																<option value="6" >Đã hủy</option>
																<%  }   %>
														</select>
														</TD>
													</TR>
													<TR>
														<TD class="plainlabel">Khu vực</TD>
														<TD width="81%" class="plainlabel"><SELECT
															name="kvId" >
																<option value=""></option>
																<%  try{
								  		while(kvlist.next()){								  			
								  			if (obj.getKvId().equals(kvlist.getString("kvId"))){ %>
																<option value="<%= kvlist.getString("kvId")%>" selected><%= kvlist.getString("kv")%></option>
																<%}else{ %>
																<option value="<%= kvlist.getString("kvId")%>"><%= kvlist.getString("kv")%></option>
																<%		}
								  		}	
								  	}catch (java.sql.SQLException e){} %>
														</SELECT>
														</TD>
													</TR>
														<TR>
														<TD width="19%" class="plainlabel">Số SO</TD>
														<TD width="81%" class="plainlabel"><input type="text"
															id="so" name="so" value="<%=obj.getSO()%>" size=40  />

														</TD>
													</TR>
													
													<TR>													
														<TD width="19%" class="plainlabel">Tên NPP</TD>
														<TD width="81%" class="plainlabel"><input type="text"
														id="nppTen" name="nppTen" value="<%=obj.getnppTen()%>" size=40 />

														</TD>
													</TR>
													<TR>

														<TD class="plainlabel" align="left" colspan=2><a
															class="button1" href="javascript:submitForm('Hien thi')">
																<img style="top: -4px;" src="../images/Search30.png"
																alt="">Tìm kiếm </a>
															&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a class="button2"
															href="javascript:clearform()"> <img
																style="top: -4px;" src="../images/button.png" alt="">Nhập
																lại</a>&nbsp;&nbsp;&nbsp;&nbsp; <!--  <INPUT name="reinitialiser" type="button" value="Nhap  lai gia tri" onclick="clearform()" >-->

														</TD>

													</TR>
												</TABLE>
											</FIELDSET></TD>
									</TR>

								</TABLE></TD>
						</TR>
					</TABLE>
					<TABLE width="100%" cellspacing=0 cellpadding=0>
						<TR>
							<TD>
								<FIELDSET>
									<LEGEND class="legendtitle">
										&nbsp;&nbsp;&nbsp;Đơn đặt hàng&nbsp;&nbsp;&nbsp;&nbsp;<!--  <a
											class="button3" href="javascript:newform()"> <img
											style="top: -4px;" src="../images/New.png" alt="">Tạo
											mới </a> -->
									</LEGEND>

									<TABLE width="100%">
										<TR>
											<TD>
												<TABLE width="100%" border="0" cellspacing="1"
													cellpadding="4">
													<TR class="tbheader">
														<TH>Ngày đặt hàng</TH>
														<TH>Số PO</TH>
														<TH>Số SO</TH>
														<TH>Nhà phân phối</TH>
														<TH>Đ.v. kinh doanh</TH>
														<TH>T.tiền Đ.hàng</TH>
														<TH>Trạng thái</TH>
														<TH>Ngày giao hàng</TH>
														<TH>T.tiền h.đơn</TH>
														<TH align="center">&nbsp;Tác vụ</TH>
													</TR>

													<% 
                            NumberFormat formatter = new DecimalFormat("#,###,###");
                            int m = 0;
                            String lightrow = "tblightrow";
                            String darkrow = "tbdarkrow";
							if(dhlist != null){
								try{								
                                    while (dhlist.next()){
                                        	String msg="Số Đơn hàng: "+dhlist.getString("chungtu")  +"  </br> Ngày giờ đặt : " +dhlist.getString("ngaygiodat")  + " </br>  . Ngày duyệt :" + dhlist.getString("NGAYDUYET") +"</br> Ngày ra hóa đơn :   "  + dhlist.getString("ngaygiohd")+"</br> Ngày giao hàng  :   "  + dhlist.getString("ngayhd")+"</br> Ngày hàng về :   "  + dhlist.getString("ngayhangve")+"</br> Ngày NPP nhận hàng :   "  + dhlist.getString("ngaynhanhang");	
                                        	String chuoi="";
                                        	String ngaydat=dhlist.getString("ngaydat");
											  if( dhlist.getString("loaidonhang").equals("1")){
												  
												  chuoi="color: red;";
												  ngaydat=dhlist.getString("ngaydat")+"("+dhlist.getString("nam")+"-"+dhlist.getString("thang")+")";
											  }
                                        			if (m % 2 != 0) {  %>
													<TR style="<%=chuoi %>" class=<%=lightrow%> onMouseover="ddrivetip('<%=msg %>', 300)"; onMouseout="hideddrivetip()">
														<%} else {%>
													 
													  
														<TR  style="<%=chuoi %>"  class=<%= darkrow%> onMouseover="ddrivetip('<%=msg %>', 300)"; onMouseout="hideddrivetip()">
														<%}%>
														<TD align="left"><div align="left" onMouseover="ddrivetip('<%=msg %>', 300)"; onMouseout="hideddrivetip()"><%= ngaydat %></div>
														</TD>
														<TD align="left"  ><div align="left"> <%= dhlist.getString("soid")%></div>
														</TD>
														
														<TD>
														<%
                                                  		 if (dhlist.getString("trangthai").equals("7")){ %>
															<span style="color: red;">
																<%=  dhlist.getString("chungtu") %>
															</span>
														<%} else{ %>
															<span >
																<%=  dhlist.getString("chungtu") %>
															</span>
														<%} %>
														</TD>
														<TD><div align="center"><%= dhlist.getString("nppTen") %></div>
														</TD>
														<TD><div align="center"><%= dhlist.getString("donvikinhdoanh") %></div>
														</TD>
														<TD><% String thd="0";
														   if(dhlist.getString("sotienavat").length()!=0)
															{
																thd=dhlist.getString("sotienavat");

															}
														
														%><div align="right"><%=formatter.format(Double.parseDouble(thd)) %></div>
														</TD>

														<%
                                                  		 if (dhlist.getString("trangthai").trim().equals("1")){ %>
														<TD>
														 <div align="center">NPP chốt/ Chờ TT xử lý</div>
														</TD>
														<%}else  if (dhlist.getString("trangthai").trim().equals("2")) {%>
														<TD><div align="center">TT Đã xác nhận</div></TD>
														<% } else if  (dhlist.getString("trangthai").trim().equals("3")){ %> 
															<TD><div align="center">Ðã xuất HDTC</div></TD>	
														<% } else if  (dhlist.getString("trangthai").trim().equals("4")) {
															if(dhlist.getString("isnhanhang").trim().equals("1")){
																%>
																<TD><div align="center">Đang giao hàng/Chưa đủ hàng với HD</div></TD>													
																<%
															}else{
																%>
																<TD><div align="center">Đang giao hàng/NPP chưa nhận hàng</div></TD>													
																<%
															}
															} else if( dhlist.getString("trangthai").trim().equals("5"))  {
															%>
															<TD><div align="center">Hoàn tất</div></TD>	
															<%
															}else if( dhlist.getString("trangthai").trim().equals("6"))  {
																%>
																<TD><div align="center">Đã hủy</div></TD>	
																<%
																}else if( dhlist.getString("trangthai").trim().equals("0"))  {
																	%>
																	<TD><div align="center">NPP chưa Chốt</div></TD>	
																	<%
																	}
														
															%>
														

														<% if (dhlist.getString("ngayhd").equals("0")){ %>
														<TD align="center">&nbsp;</TD>
														<%}else{ %>
														<TD align="center"><%=dhlist.getString("ngayhd") %></TD>
														<%} %>

														<% if (dhlist.getString("tienhd").equals("0")){ %>
														<TD align="center">&nbsp;</TD>
														<%}else
														{ %>
														<% String thd2="0";
														   if(dhlist.getString("tienhd").length()!=0)
															{
																thd2 =dhlist.getString("tienhd");

															}
														
														%>
														<TD align="right"><%=formatter.format(Double.parseDouble(thd2)) %></TD>
														<!-- Tien sua -->

														<%} %>

														<TD align="center">
															<TABLE border=0 cellpadding="1" cellspacing="0">
																<TR>
																	<% 
                                	           	  				int t	= Integer.valueOf(dhlist.getString("trangthai")).intValue();%>
																	<TD width=25>
																	<%if(quyen[3]!=0){ %>
																	<A
																	   href="../../ERP_DonDatHangUpdateSvl?userId=<%=userId%>&display=<%=dhlist.getString("chungtu") %>"><img
																	   src="../images/Display20.png" alt="Hiển thị"
																	   width="20" height="20" title="Hiển thị" border="0"   >
																	</A>
																	<%} %>
																	</TD>
																	
																	<% if (t == 1){ %>
																	<TD width=25>
																	<%if(quyen[4]!=0){ %>
																	
																	<% if( dhlist.getString("DONDATHANG_FROM_FK") == null ) { %>
																		<A id='chotphieu<%=dhlist.getString("chungtu")%>'
																		   href=""><img
																		   src="../images/unChot.png" alt="Bỏ chốt đơn hàng"
																		   width="20" height="20" title="Bỏ chốt đơn hàng" 
																		   border="0" onclick="if(!confirm('Bạn có muốn bỏ chốt đơn hàng này?')) {return false ;}else{ processing('<%="chotphieu"+dhlist.getString("chungtu")%>' , '../../DonmuahangSvl?userId=<%=userId%>&unchot=<%=dhlist.getString("chungtu") %>');}"  >
																		</A>
																	<% } %>
																	
																	<%} %>
																	</TD>
																	<% }
                         	                   	 					%>
																
																</TR>


															</TABLE></TD>
													</TR>
													<% m++; } 

                                    
                                        }catch(java.sql.SQLException e){
                                        	System.out.println("Error : "+ e.toString());
                                        }
                               		}%>

													<tr class="tbfooter">
														<TD align="center" valign="middle" colspan="13"
															class="tbfooter">
															<%if(obj.getNxtApprSplitting() >1) {%> <img alt="Trang Dau"
															src="../images/first.gif" style="cursor: pointer;"
															onclick="View('ddhForm', 1, 'view')"> &nbsp; <%}else {%>
															<img alt="Trang Dau" src="../images/first.gif">
															&nbsp; <%} %> <% if(obj.getNxtApprSplitting() > 1){ %> <img
															alt="Trang Truoc" src="../images/prev.gif"
															style="cursor: pointer;"
															onclick="View('ddhForm', eval(ddhForm.nxtApprSplitting.value) -1, 'view')">
															&nbsp; <%}else{ %> <img alt="Trang Truoc"
															src="../images/prev_d.gif"> &nbsp; <%} %> <%
													int[] listPage = obj.getNextSplittings();
													for(int i = 0; i < listPage.length; i++){
												%> <% 
												
												System.out.println("Current page:" + obj.getNxtApprSplitting());
												System.out.println("List page:" + listPage[i]);
												
												if(listPage[i] == obj.getNxtApprSplitting()){ %> <a
															style="color: white;"><%= listPage[i] %>/ <%=obj.getTheLastSplitting() %></a>
															<%}else{ %> <a
															href="javascript:View('ddhForm', <%= listPage[i] %>, 'view')"><%= listPage[i] %></a>
															<%} %> <input type="hidden" name="list"
															value="<%= listPage[i] %>" /> &nbsp; <%} %> <% if(obj.getNxtApprSplitting() < obj.getTheLastSplitting()){ %>
															&nbsp; <img alt="Trang Tiep" src="../images/next.gif"
															style="cursor: pointer;"
															onclick="View('ddhForm', eval(ddhForm.nxtApprSplitting.value) +1, 'view')">
															&nbsp; <%}else{ %> &nbsp; <img alt="Trang Tiep"
															src="../images/next_d.gif"> &nbsp; <%} %> <%if(obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {%>
															<img alt="Trang Cuoi" src="../images/last.gif">
															&nbsp; <%}else{ %> <img alt="Trang Cuoi"
															src="../images/last.gif" style="cursor: pointer;"
															onclick="View('ddhForm', -1, 'view')"> &nbsp; <%} %>
														</TD>
													</tr>


												</TABLE></TD>
										</TR>
									</TABLE>
								</FIELDSET></TD>
						</TR>
					</TABLE></TD>
			</TR>
		</TABLE>
	</form>
	<p id="result"></p>

</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</HTML>

<% if (!( dhlist == null)) dhlist.close(); %>
<% if (!( kvlist == null)) kvlist.close(); %>
<% obj.DBclose(); %>
<%}%>