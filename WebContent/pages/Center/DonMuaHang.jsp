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
													<TD align="left" colspan="2" class="tbnavigation">&nbsp;Qu???n l?? b??n h??ng
														&gt; Duy???t ????n h??ng</TD>

													<TD colspan="2" align="right" class="tbnavigation">Ch??o m???ng b???n <%=obj.getTen() %> &nbsp;</TD>
												</tr>
											</table></TD>
									</TR>
								</TABLE>

								<TABLE border="0" width="100%" cellspacing=0 cellpadding=0>

									<TR>
										<TD width="100%" align="left">
											<FIELDSET>
												<LEGEND class="legendtitle">&nbsp;Ti??u ch?? hi???n th???
													&nbsp;</LEGEND>

												<TABLE width="100%" cellpadding="4" cellspacing="0">
													<TR>
														<TD width="19%" class="plainlabel">Ch???a SKU</TD>
														<TD width="81%" class="plainlabel"><input type="text"
															id="sku" name="sku" value="<%= obj.getSKU() %>" size=40 />

														</TD>
													</TR>
													<TR>
														<TD class="plainlabel">T??? ng??y</TD>
														<TD class="plainlabel">
															<TABLE cellpadding="0" cellspacing="0">
																<TR>
																	<TD><input class="days" type="text" name="tungay"
																		value="<%=obj.getTungay() %>" size="20"></TD>

																</TR>
															</TABLE></TD>
													</TR>
													<TR>
														<TD class="plainlabel">?????n ng??y</TD>
														<TD class="plainlabel">
															<TABLE cellpadding="0" cellspacing="0">
																<TR>
																	<TD><input class="days" type="text" name="denngay"
																		value="<%=obj.getDenngay() %>" size="20"></TD>

																</TR>
															</TABLE></TD>

													</TR>
													<TR>
														<TD class="plainlabel">Tr???ng th??i</TD>
														<TD class="plainlabel"><select id="trangthai" name="trangthai">
																<%if (obj.getTrangthai().equals("0")){ %>
																<option value=""></option>
																<option value="0" selected>Nh?? PP Ch??a Ch???t</option>
																<option value="1" >Nh?? PP ???? x??c nh???n/Ch??? duy???t</option>
																<option value="2">Trung t??m ???? duy???t</option>
																<option value="3">???? xu???t HDTC</option>
																<option value="4">??ang giao h??ng</option>
																<option value="5">Ho??n t???t</option>
																<option value="6">???? h???y</option>
																<%}else 							
								  						if (obj.getTrangthai().equals("1")){ %>
								  								<option value=""></option>
																<option value="0" >Nh?? PP Ch??a Ch???t</option>
																<option value="1" selected >Nh?? PP ???? x??c nh???n/Ch??? duy???t</option>
																<option value="2">Trung t??m ???? duy???t</option>
																<option value="3">???? xu???t HDTC</option>
																<option value="4">??ang giao h??ng</option>
																<option value="5">Ho??n t???t</option>
																<option value="6">???? h???y</option>
																<%}else
														if (obj.getTrangthai().equals("2")){ %>
																<option value=""></option>
																<option value="0" >Nh?? PP Ch??a Ch???t</option>
																<option value="1"  >Nh?? PP ???? x??c nh???n/Ch??? duy???t</option>
																<option value="2" selected>Trung t??m ???? duy???t</option>
																<option value="3">???? xu???t HDTC</option>
																<option value="4">??ang giao h??ng</option>
																<option value="5">Ho??n t???t</option>
																<option value="6">???? h???y</option>
													<%} else											   	 
														if (obj.getTrangthai().equals("3")){ %>
																<option value=""></option>
																<option value="0" >Nh?? PP Ch??a Ch???t</option>
																<option value="1"  >Nh?? PP ???? x??c nh???n/Ch??? duy???t</option>
																<option value="2" >Trung t??m ???? duy???t</option>
																<option value="3" selected>???? xu???t HDTC</option>
																<option value="4">??ang giao h??ng</option>
																<option value="5">Ho??n t???t</option>
																<option value="6">???? h???y</option>

																<%	} else  if(obj.getTrangthai().equals("4")) { %>
																	<option value=""></option>
																<option value="0" >Nh?? PP Ch??a Ch???t</option>
																<option value="1"  >Nh?? PP ???? x??c nh???n/Ch??? duy???t</option>
																<option value="2" >Trung t??m ???? duy???t</option>
																<option value="3" >???? xu???t HDTC</option>
																<option value="4" selected>??ang giao h??ng</option>
																<option value="5">Ho??n t???t</option>
																<option value="6">???? h???y</option>
																	
																 <%} else if(obj.getTrangthai().equals("5")){ %> 
																 	<option value=""></option>
																<option value="0" >Nh?? PP Ch??a Ch???t</option>
																<option value="1"  >Nh?? PP ???? x??c nh???n/Ch??? duy???t</option>
																<option value="2" >Trung t??m ???? duy???t</option>
																<option value="3" >???? xu???t HDTC</option>
																<option value="4" >??ang giao h??ng</option>
																<option value="5" selected>Ho??n t???t</option>
																<option value="6">???? h???y</option>
																 <%}	else if(obj.getTrangthai().equals("6")){ %> 
																 	<option value=""></option>
																<option value="0" >Nh?? PP Ch??a Ch???t</option>
																<option value="1"  >Nh?? PP ???? x??c nh???n/Ch??? duy???t</option>
																<option value="2" >Trung t??m ???? duy???t</option>
																<option value="3" >???? xu???t HDTC</option>
																<option value="4" >??ang giao h??ng</option>
																<option value="5">Ho??n t???t</option>
																<option value="6" selected>???? h???y</option>
																  <%} else { %>
																		<option value="" selected></option>
																<option value="0" >Nh?? PP Ch??a Ch???t</option>
																<option value="1"  >Nh?? PP ???? x??c nh???n/Ch??? duy???t</option>
																<option value="2" >Trung t??m ???? duy???t</option>
																<option value="3" >???? xu???t HDTC</option>
																<option value="4" >??ang giao h??ng</option>
																<option value="5">Ho??n t???t</option>
																<option value="6" >???? h???y</option>
																<%  }   %>
														</select>
														</TD>
													</TR>
													<TR>
														<TD class="plainlabel">Khu v???c</TD>
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
														<TD width="19%" class="plainlabel">S??? SO</TD>
														<TD width="81%" class="plainlabel"><input type="text"
															id="so" name="so" value="<%=obj.getSO()%>" size=40  />

														</TD>
													</TR>
													
													<TR>													
														<TD width="19%" class="plainlabel">T??n NPP</TD>
														<TD width="81%" class="plainlabel"><input type="text"
														id="nppTen" name="nppTen" value="<%=obj.getnppTen()%>" size=40 />

														</TD>
													</TR>
													<TR>

														<TD class="plainlabel" align="left" colspan=2><a
															class="button1" href="javascript:submitForm('Hien thi')">
																<img style="top: -4px;" src="../images/Search30.png"
																alt="">T??m ki???m </a>
															&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a class="button2"
															href="javascript:clearform()"> <img
																style="top: -4px;" src="../images/button.png" alt="">Nh???p
																l???i</a>&nbsp;&nbsp;&nbsp;&nbsp; <!--  <INPUT name="reinitialiser" type="button" value="Nhap  lai gia tri" onclick="clearform()" >-->

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
										&nbsp;&nbsp;&nbsp;????n ?????t h??ng&nbsp;&nbsp;&nbsp;&nbsp;<!--  <a
											class="button3" href="javascript:newform()"> <img
											style="top: -4px;" src="../images/New.png" alt="">T???o
											m???i </a> -->
									</LEGEND>

									<TABLE width="100%">
										<TR>
											<TD>
												<TABLE width="100%" border="0" cellspacing="1"
													cellpadding="4">
													<TR class="tbheader">
														<TH>Ng??y ?????t h??ng</TH>
														<TH>S??? PO</TH>
														<TH>S??? SO</TH>
														<TH>Nh?? ph??n ph???i</TH>
														<TH>??.v. kinh doanh</TH>
														<TH>T.ti???n ??.h??ng</TH>
														<TH>Tr???ng th??i</TH>
														<TH>Ng??y giao h??ng</TH>
														<TH>T.ti???n h.????n</TH>
														<TH align="center">&nbsp;T??c v???</TH>
													</TR>

													<% 
                            NumberFormat formatter = new DecimalFormat("#,###,###");
                            int m = 0;
                            String lightrow = "tblightrow";
                            String darkrow = "tbdarkrow";
							if(dhlist != null){
								try{								
                                    while (dhlist.next()){
                                        	String msg="S??? ????n h??ng: "+dhlist.getString("chungtu")  +"  </br> Ng??y gi??? ?????t : " +dhlist.getString("ngaygiodat")  + " </br>  . Ng??y duy???t :" + dhlist.getString("NGAYDUYET") +"</br> Ng??y ra h??a ????n :   "  + dhlist.getString("ngaygiohd")+"</br> Ng??y giao h??ng  :   "  + dhlist.getString("ngayhd")+"</br> Ng??y h??ng v??? :   "  + dhlist.getString("ngayhangve")+"</br> Ng??y NPP nh???n h??ng :   "  + dhlist.getString("ngaynhanhang");	
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
														 <div align="center">NPP ch???t/ Ch??? TT x??? l??</div>
														</TD>
														<%}else  if (dhlist.getString("trangthai").trim().equals("2")) {%>
														<TD><div align="center">TT ???? x??c nh???n</div></TD>
														<% } else if  (dhlist.getString("trangthai").trim().equals("3")){ %> 
															<TD><div align="center">???? xu???t HDTC</div></TD>	
														<% } else if  (dhlist.getString("trangthai").trim().equals("4")) {
															if(dhlist.getString("isnhanhang").trim().equals("1")){
																%>
																<TD><div align="center">??ang giao h??ng/Ch??a ????? h??ng v???i HD</div></TD>													
																<%
															}else{
																%>
																<TD><div align="center">??ang giao h??ng/NPP ch??a nh???n h??ng</div></TD>													
																<%
															}
															} else if( dhlist.getString("trangthai").trim().equals("5"))  {
															%>
															<TD><div align="center">Ho??n t???t</div></TD>	
															<%
															}else if( dhlist.getString("trangthai").trim().equals("6"))  {
																%>
																<TD><div align="center">???? h???y</div></TD>	
																<%
																}else if( dhlist.getString("trangthai").trim().equals("0"))  {
																	%>
																	<TD><div align="center">NPP ch??a Ch???t</div></TD>	
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
																	   src="../images/Display20.png" alt="Hi???n th???"
																	   width="20" height="20" title="Hi???n th???" border="0"   >
																	</A>
																	<%} %>
																	</TD>
																	
																	<% if (t == 1){ %>
																	<TD width=25>
																	<%if(quyen[4]!=0){ %>
																	
																	<% if( dhlist.getString("DONDATHANG_FROM_FK") == null ) { %>
																		<A id='chotphieu<%=dhlist.getString("chungtu")%>'
																		   href=""><img
																		   src="../images/unChot.png" alt="B??? ch???t ????n h??ng"
																		   width="20" height="20" title="B??? ch???t ????n h??ng" 
																		   border="0" onclick="if(!confirm('B???n c?? mu???n b??? ch???t ????n h??ng n??y?')) {return false ;}else{ processing('<%="chotphieu"+dhlist.getString("chungtu")%>' , '../../DonmuahangSvl?userId=<%=userId%>&unchot=<%=dhlist.getString("chungtu") %>');}"  >
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