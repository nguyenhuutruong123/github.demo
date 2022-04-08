<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="geso.dms.center.beans.banggiabanlechuan.imp.*"%>
<%@ page import="geso.dms.center.beans.banggiabanlechuan.*"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="geso.dms.center.util.*"%>
<%@page import="java.sql.SQLException"%>
<%@ page import="java.util.Hashtable"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.text.DecimalFormat"%>
<%
	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if (!util.check(userId, userTen, sum))
	{
		response.sendRedirect("/AHF/index.jsp");
	} else
	{
%>
<%
	IBangGiaBanLeChuan obj = (IBangGiaBanLeChuan) session.getAttribute("csxBean");
		ResultSet dvkdList = obj.getDvkdRs();
		ResultSet kbhList = obj.getKbhRs();
		ResultSet nppRs = obj.getNppRs();
		ResultSet vungRs = (ResultSet) obj.getVungRs();
		ResultSet kvRs = (ResultSet) obj.getKvRs();
		ResultSet spRs = (ResultSet) obj.getSpRs();
		ResultSet clRs = (ResultSet) obj.getClRs();
		ResultSet loaikhRS = (ResultSet)obj.getLoaiKhRs();	
		ResultSet nhRs = (ResultSet) obj.getNhRs();
		NumberFormat formater = new DecimalFormat("##,###,###");
%>
<%
	Hashtable<String, String> spChonban = (Hashtable<String, String>) obj.getSpChonban();
%>
<%
	Hashtable<String, String> spGiaban = (Hashtable<String, String>) obj.getSpGiaban();
Hashtable<String, String> spGiahuongdan = (Hashtable<String, String>) obj.getSpGiahuongdan();
%>
<%
	Hashtable<String, String> spPtChietkhau = (Hashtable<String, String>) obj.getSpPtChietkhau();
%>
<%
	Hashtable<String, String> clPtCk = (Hashtable<String, String>) obj.getClPtCk();
%>
<%
	Hashtable<String, String> nhPtCk = (Hashtable<String, String>) obj.getNhPtCk();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>DDT - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<link href="../scripts/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="../scripts/bootstrap/css/bootstrap-theme.min.css" rel="stylesheet">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<script type="text/javascript" src="../scripts/jquery-2.1.4.min.js"></script>
<script src="../scripts/bootstrap/js/bootstrap.min.js"></script>
 <link rel="stylesheet" href="../scripts/jquery-ui.css">
  <script src="../scripts/jquery-ui.js"></script>
<script type="text/javascript">
$(document).ready(function() {		
		$( ".days" ).datepicker({			    
				changeMonth: true,
				changeYear: true	,
				dateFormat:'yy-mm-dd',
				separator: '-'
		});            
	}); 
</script>

<link href="../css/chosen.css" rel="stylesheet" type="text/css" />
<script src="../scripts/chosen.jquery.js" type="text/javascript"></script>

<SCRIPT language="JavaScript" type="text/javascript">

jQuery(document).ready(function()
		{
			$("select:not(.notuseselect2)").chosen();     
			
		}); 


	
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
			element.value=DinhDangDonGia(element.value);
			if(element.value== '' ||element.value=='0' )
			{
				element.value= '';
			}
		}
	 function DinhDangDonGia(num) 
	 {
	  num = num.toString().replace(/\$|\,/g,'');
	   
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
	function FormatNumber(e)
	{
		e.value = DinhDangTien(e.value);
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
	
	
	
	function checkedAll()
	{
		 var chkAll = document.getElementById("chekAllSp");
		 var spIds = document.getElementsByName("chonban");
		 
		 if(chkAll.checked)
		 {
			 for(var i = 0; i < spIds.length; i++)
			 {
				 spIds.item(i).checked = true;
			 }
		 }
		 else
		 {
			 for(var i = 0; i < spIds.length; i++)
			 {
				 spIds.item(i).checked = false;
			 }
		 }
	}
	
	function checkedAll_KhachHang()
	{
		 var chkAll = document.getElementById("checkAllKh");
		 var spIds = document.getElementsByName("nppId");
		 
		 if(chkAll.checked)
		 {
			 for(var i = 0; i < spIds.length; i++)
			 {
				 spIds.item(i).checked = true;
			 }
		 }
		 else
		 {
			 for(i = 0; i < spIds.length; i++)
			 {
				 spIds.item(i).checked = false;
			 }
		 }
	}
	
	function checkedAll_HopDong(hdId)
	{
		 var chkAll = document.getElementById(hdId + ".checkAll");
		 var spIds = document.getElementsByName(hdId + ".chonban");
		 
		 if(chkAll.checked)
		 {
			 for(i = 0; i < spIds.length; i++)
			 {
				 spIds.item(i).checked = true;
			 }
		 }
		 else
		 {
			 for(i = 0; i < spIds.length; i++)
			 {
				 spIds.item(i).checked = false;
			 }
		 }
	}
	
	 function  updateCkNhanHang(nhId)
	 {
		 var nhIdValue = document.getElementById("nhId_"+nhId).value;
		 var spIds = document.getElementsByName("spId");
		 var spChungLoaiId = document.getElementsByName("spChungLoaiId");
		 var spNhanHangId = document.getElementsByName("spNhanHangId");
		 var   spPtCk  = document.getElementsByName("ptChietkhau");
		var ptCk = document.getElementById("nhPtCk_"+nhIdValue).value;
		
		var cl_nhId = document.getElementsByName("cl_nhId");
		var clPtCk = document.getElementsByName("clPtCk");
		
	 	for(var i = 0; i < spIds.length; i++)
		{
	 		if(spNhanHangId.item(i).value==nhIdValue)
	 		{
	 			spPtCk.item(i).value=ptCk;
	 		}
		} 
	 	
	 	for(var i = 0; i < cl_nhId.length; i++)
		{
	 		if(cl_nhId.item(i).value==nhIdValue)
	 		{
	 			clPtCk.item(i).value=ptCk;
	 		}
		} 
	 	
	 	
	 	
	 	
	 }
	 
	 function  updateCkChungLoai(nhId)
	 {
		 var nhIdValue = document.getElementById("clId_"+nhId).value;
		 var spIds = document.getElementsByName("spId");
		 var spChungLoaiId = document.getElementsByName("spChungLoaiId");
		 var spNhanHangId = document.getElementsByName("spNhanHangId");
		 var   spPtCk  = document.getElementsByName("ptChietkhau");
		var ptCk = document.getElementById("clPtCk_"+nhIdValue).value;
		 console.log('[nhId]'+nhIdValue +'[ptCk]'+ptCk);
	 	for(var i = 0; i < spIds.length; i++)
		{
	 		if(spChungLoaiId.item(i).value==nhIdValue)
	 		{
	 			spPtCk.item(i).value=ptCk;
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

	<form name="khtt" method="post" action="../../BanggiabanlechuanUpdateSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
		<input type="hidden" name="userId" value='<%=userId%>'> 
		 <input type="hidden" name="action" value="0">
		 <input type="hidden" name="id" value="<%=obj.getId()%>">
		<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
			height="100%">
			<TR>
				<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
						<TR>
							<TD align="left" class="tbnavigation">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr height="22">
										<TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền > Sản phẩm > Bảng giá bán lẻ chuẩn > Hiển thị </TD>
										<TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen%></TD>
									</tr>
								</table>
							</TD>
						</TR>
					</TABLE>
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
						<TR>
							<TD>
								<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
									<TR class="tbdarkrow">
										<TD width="30" align="left">
												<A href="../../BanggiabanlechuanSvl?userId=<%=userId%>"><img src="../images/Back30.png" alt="Quay ve" title="Quay ve" border="1" longdesc="Quay ve" style="border-style: outset"></A>
										</TD>
										<TD width="2" align="left"></TD>
										<TD width="30" align="left">
											
										</TD>
										<TD>&nbsp;</TD>
									</TR>
								</TABLE>
							</TD>
						</TR>
					</TABLE>
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
						<tr>
							<TD align="left" colspan="4" class="legendtitle">
								<FIELDSET>
									<LEGEND class="legendtitle">Thông báo </LEGEND>

									<textarea name="dataerror"
										style="width: 100%; color: #F00; font-weight: bold"
										style="width: 100% ; color:#F00 ; font-weight:bold"
										readonly="readonly" rows="1"><%=obj.getMsg()%></textarea>
								</FIELDSET>
							</TD>
						</tr>

						<TR>
							<TD height="100%" width="100%">
								<FIELDSET>
									<LEGEND class="legendtitle" style="color: black">Thông
										tin bảng giá </LEGEND>
									<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
										<TR>
											<TD class="plainlabel" width="130px">Tên bảng giá</TD>
											<TD class="plainlabel" width="220px"><input type="text" readonly="readonly"
												name="ten" value="<%=obj.getTen()%>"></TD>
												
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
												
											<TD class="plainlabel" width="140px">Đơn vị kinh doanh</TD>
											<TD class="plainlabel" valign="middle"><select
												name="dvkdId" id="dvkdId" onchange="submitFrom();">
													<option value=""></option>
													<%
														if (dvkdList != null)
															{
																while (dvkdList.next())
																{
																	if (dvkdList.getString("dvkdId").equals(obj.getDvkdId()))
																	{
													%>
													<option value="<%=dvkdList.getString("dvkdId")%>"
														selected="selected"><%=dvkdList.getString("dvkd")%></option>
													<%
														} else
																	{
													%>
													<option value="<%=dvkdList.getString("dvkdId")%>"><%=dvkdList.getString("dvkd")%></option>
													<%
														}
																}
																dvkdList.close();
															}
													%>
											</select></TD>
											<TD class="plainlabel" width="140px"></TD>
											<TD colspan="2" class="plainlabel"></TD>
										</TR>

										<TR>
											<TD class="plainlabel" valign="middle" width="120px">Từ ngày</TD>
											<TD class="plainlabel" valign="middle">
											 <input size="10"  onchange="submitFrom()" name="tungay" readonly="readonly" type="text" value="<%=obj.getTuNgay()%>" readonly class="days">
											 
											<TD class="plainlabel" valign="middle">Kênh bán hàng</TD>
											<TD class="plainlabel" valign="middle"><select
												name="kbhId" id="kbhId" onchange="submitFrom();">
													<!-- <option value=""></option> -->
													<%
														if (kbhList != null)
															{
																while (kbhList.next())
																{
																	if (kbhList.getString("kenhId").equals(obj.getKbhId()))
																	{
													%>
													<option value="<%=kbhList.getString("kenhId")%>"
														selected="selected"><%=kbhList.getString("kenh")%></option>
													<%
														} else
																	{
													%>
													<option value="<%=kbhList.getString("kenhId")%>"><%=kbhList.getString("kenh")%></option>
													<%
														}
																}
																kbhList.close();
															}
													%>
											</select></TD>
											<TD class="plainlabel" width="140px"></TD>
											<TD colspan="2" class="plainlabel"></TD>
										</TR>

										<TR style="display: none;">
											<TD class="plainlabel" valign="middle" width="120px">Chiết
												khấu</TD>
											<TD class="plainlabel" valign="middle"><input
												type="text" name="chietkhau"
												value="<%=obj.getChietKhau()%>" maxlength="10"
												onchange="thaydoichietkhau(this);"></TD>
											<TD class="plainlabel" width="140px"></TD>
											<TD colspan="2" class="plainlabel"></TD>
										</TR>

										  <%if(!obj.getDvkdId().equals("100069")) {%>
										<TR>

											<TD class="plainlabel">Vùng</TD>
											<TD class="plainlabel"><SELECT name="vungId" id="vungId" style="width: 150px" multiple onchange="submitFrom();">
													<OPTION value="0"></OPTION>
													<%
														if (vungRs != null)
															{
																while (vungRs.next())
																{

																	if (obj.getVungId().indexOf(vungRs.getString("pk_seq")) >= 0)
																	{
													%>
													<OPTION value="<%=vungRs.getString("pk_seq")%>" selected><%=vungRs.getString("ten")%></OPTION>
													<%
														} else
																	{
													%>
													<OPTION value="<%=vungRs.getString("pk_seq")%>"><%=vungRs.getString("ten")%></OPTION>
													<%
														}

																}

															}
													%>
											</SELECT></TD>


											<TD class="plainlabel">Khu vực</TD>
											<TD class="plainlabel"><SELECT name="kvId" id="kvId" style="width: 150px" multiple>
													<OPTION value="0"></OPTION>
													<%
														if (kvRs != null)
															{
																while (kvRs.next())
																{
																	if (obj.getKvId().indexOf(kvRs.getString("pk_seq")) >= 0)
																	{
													%>
													<OPTION value=<%=kvRs.getString("pk_seq")%> selected><%=kvRs.getString("diengiai")%></OPTION>
													<%
														} else
																	{
													%>
													<OPTION value=<%=kvRs.getString("pk_seq")%>><%=kvRs.getString("diengiai")%></OPTION>
													<%
														}

																}

															}
													%>
											</SELECT></TD>


											<TD class="plainlabel" width="140px"></TD>
											<TD colspan="2" class="plainlabel"></TD>

										</TR>
									<%} %>


										<TR>
											<TD class="plainlabel" valign="middle">Trạng thái</TD>
											<TD class="plainlabel" valign="middle">
												<%
													if (obj.getTrangthai().equals("1"))
														{
												%> <input
												type="checkbox" name="trangthai" value="1" checked="checked"><i>
													Hoạt động</i> <%
 	} else
 		{
 %> <input type="checkbox"
												name="trangthai" value="1"><i> Hoạt động</i> <%
 	}
 %>
											</TD>
											<TD class="plainlabel" width="140px">											
											<a href="#" class="btn btn-lg btn-primary"    data-toggle="modal" data-target="#divNhanHang">Nhãn hàng</a>
												<div class="modal fade" id="divNhanHang"   tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
													<div class="modal-dialog modal-lg">
														<div class="modal-content">
															<div class="modal-header">
																<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
																<h4 class="modal-title" id="myModalLabel">Nhãn hàng</h4>
															</div>
															<div class="modal-body">
																<table width="95%" align="center">
														<TR class="tbheader">
															<th width="250px" style="font-size: 12px; text-align: center;">Tên</th>
															<th width="80px" style="font-size: 12px; text-align: center;">Chiết khấu</th>
														</tr>
														<%
															int sodong = 0;
																while (nhRs != null && nhRs.next())
																{
																	String ck = nhPtCk.get(nhRs.getString("nhId")) != null ? nhPtCk.get(nhRs.getString("nhId")) : "";
														%>
														<TR>
															<TD>
																<input type="hidden" style="width: 100%" name="nhId"  id="nhId_<%=nhRs.getString("nhId")%>"   value="<%=nhRs.getString("nhId")%>"> 
																<input type="text" style="width: 100%" name="nhTen"  id="nhTen_<%=nhRs.getString("nhId")%>"  value="<%=nhRs.getString("nhTen")%>" readonly="readonly">
															</TD>
															<TD> <input type="text" style="width: 100%" name="nhPtCk"  id="nhPtCk_<%=nhRs.getString("nhId")%>"    value="<%=ck%>"  onchange="updateCkNhanHang(<%=nhRs.getString("nhId")%>)" ></TD>
														</TR>
														<%
															}
														%>
													</table>
															</div>
															<div class="modal-footer">
																<button type="button" class="btn btn-default"
																	data-dismiss="modal">Close</button>
															</div>
														</div>
													</div>
												</div>
											
											
											
												<%-- <a href="" id="popupCHIETKHAU" rel="subcontentCK"> &nbsp; <img alt="Khai báo chiết khấu" src="../images/vitriluu.png" title="Khai báo chiết khấu"></a>
												<DIV id="subcontentCK"
													style="position: absolute; visibility: hidden; border: 9px solid #80CB9B; background-color: white; width: 500px; max-height: 300px; overflow: auto; padding: 4px;">
													<table width="95%" align="center">
														<TR class="tbheader">
															<th width="250px" style="font-size: 12px; text-align: center;">Tên</th>
															<th width="80px" style="font-size: 12px; text-align: center;">Chiết khấu</th>
														</tr>
														<%
															int sodong = 0;
																while (nhRs != null && nhRs.next())
																{
																	String ck = nhPtCk.get(nhRs.getString("nhId")) != null ? nhPtCk.get(nhRs.getString("nhId")) : "";
														%>
														<TR>
															<TD>
																<input type="hidden" style="width: 100%" name="nhId" value="<%=nhRs.getString("nhId")%>"> 
																<input type="text" style="width: 100%" name="nhTen"value="<%=nhRs.getString("nhTen")%>" readonly="readonly">
															</TD>
															<TD> <input type="text" style="width: 100%" name="nhPtCk" value="<%=ck%>" readonly="readonly"></TD>
														</TR>
														<%
															}
														%>
													</table>
													<div align="right">
														<label style="color: red"></label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a href="javascript:dropdowncontent.hidediv('subcontentCK')"><b> Đóng lại </b></a>
													</div>
												</DIV> <script type="text/javascript"> dropdowncontent.init('popupCHIETKHAU', "right-top", 300, "click"); </script>
 --%>
											</TD>

											<TD class="plainlabel" width="140px"> 
											
<a href="#" class="btn btn-lg btn-primary" data-toggle="modal" data-target="#divChungLoai">Chủng loại </a>
												<div class="modal fade" id="divChungLoai" tabindex="-1"
													role="dialog" aria-labelledby="divChungLoai" aria-hidden="true">
													<div class="modal-dialog modal-lg">
														<div class="modal-content">
															<div class="modal-header">
																<button type="button" class="close" data-dismiss="modal"
																	aria-hidden="true">&times;</button>
																<h4 class="modal-title" id="myModalLabel">Chủng loại</h4>
															</div>
															<div class="modal-body">															
																<table width="95%" align="center">
																	<TR class="tbheader">
																		<th width="250px" style="font-size: 12px; text-align: center;">Nhãn hàng</th>
																		<th width="250px" style="font-size: 12px; text-align: center;">Tên chủng lọai</th>
																		<th width="80px" style="font-size: 12px; text-align: center;">Chiết khấu</th>
																	</tr>
																	<%
																		while (clRs != null && clRs.next())
																			{
																				String ck = clPtCk.get(clRs.getString("clId")) != null ? clPtCk.get(clRs.getString("clId")) : "";
																	%>
																	
														<TR>
															<TD> 
																<input type="text" style="width: 100%" name="cl_nhTen"  id=""    value="<%=clRs.getString("nhTen")%>"   >
																<input type="hidden" style="width: 100%" name="cl_nhId"  id="cl_nhId_<%=clRs.getString("nhId")%>"   value="<%=clRs.getString("nhId")%>">
															</TD>
															<TD>
																<input type="hidden" style="width: 100%" name="clId"  id="clId_<%=clRs.getString("clId")%>"   value="<%=clRs.getString("clId")%>"> 
																<input type="text" style="width: 100%" name="clTen"  id="clTen_<%=clRs.getString("clId")%>"  value="<%=clRs.getString("clTen")%>" readonly="readonly">
															</TD>
															<TD> <input type="text" style="width: 100%" name="clPtCk"  id="clPtCk_<%=clRs.getString("clId")%>"    value="<%=ck%>"  onchange="updateCkChungLoai(<%=clRs.getString("clId")%>)" ></TD>
														</TR>
																	
																	
																	<%
																		}
																	%>
																</table> 															
															</div>
															<div class="modal-footer">
																<button type="button" class="btn btn-default"
																	data-dismiss="modal">Close</button>
															</div>
														</div>
													</div>
												</div>
												
												 <%-- <a href="" id="popupCHUNGLOAI" rel="subcontentCHUNGLOAI"> &nbsp; <img alt="Khai báo chiết khấu" src="../images/vitriluu.png" title="Khai báo chiết khấu"></a>
												<DIV id="subcontentCHUNGLOAI" style="position: absolute; visibility: hidden; border: 9px solid #80CB9B; background-color: white; width: 500px; max-height: 300px; overflow: auto; padding: 4px;">
													<table width="95%" align="center">
														<TR class="tbheader">
															<th width="250px" style="font-size: 12px; text-align: center;">Tên</th>
															<th width="80px" style="font-size: 12px; text-align: center;">Chiết khấu</th>
														</tr>
														<%
														while (clRs != null && clRs.next())
																{
															String ck = clPtCk.get(clRs.getString("clId")) != null ? clPtCk.get(clRs.getString("clId")) : "";
														%>
														<TR>
															<TD>
																<input type="hidden" style="width: 100%" name="clId" value="<%=clRs.getString("clId")%>">
																<input type="text" style="width: 100%" name="clTen" value="<%=clRs.getString("clTen")%>" readonly="readonly"></TD>
															<TD><input type="text" style="width: 100%" name="clPtCk" value="<%=ck%>" readonly="readonly"></TD>
														</TR>
														<%
															}
														%>
													</table>

													<div align="right">
														<label style="color: red"></label>
														&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a
															href="javascript:dropdowncontent.hidediv('subcontentCHUNGLOAI')"><b>
																Đóng lại </b></a>
													</div>
												</DIV> <script type="text/javascript">
				            	dropdowncontent.init('popupCHUNGLOAI', "right-top", 300, "click");
				            </script> --%>

											</TD>



 <TD colspan = "2" class="plainlabel"></TD>
										</TR>
<TR>		
	<td colspan="6">
					<div class="tabbable"> <!-- Only required for left/right tabs -->
					  <ul class="nav nav-tabs">
					    <%if(!obj.getDvkdId().equals("100069")) {%>
					    <li class="active"><a href="#tab1" data-toggle="tab">Nhà phân phối</a></li>
					    <%}else { %>
					      <li class="active"><a href="#tab1" data-toggle="tab">Khách hàng IP</a></li>
					    
					    <%} %>
					    <li><a href="#tab2" data-toggle="tab">Chọn sản phẩm</a></li>
					  </ul>
					  <div class="tab-content">
					    <div class="tab-pane active" id="tab1">
					     
					     
					     <TABLE width="100%" border="0" cellspacing="1"
															cellpadding="4">
															<TR class="tbheader">
																 <%if(!obj.getDvkdId().equals("100069")) {%>
																<TH width="15%">Mã Nhà phân phối</TH>
																 <%}else { %>
																 	<TH width="15%">Khách hàng IP</TH>
																 <%} %>
																<TH width="25%">Họ tên</TH>
																<TH width="10%">Điện thoại</TH>
																<TH width="30%">Địa chỉ</TH>
																<TH style="display:none" width="10%">Chiết khấu</TH>
																<TH width="10%">Chọn <input type="checkbox" id="checkAllKh" onclick="checkedAll_KhachHang();">
																</TH>
															</TR>
															<%
																if (nppRs != null)
																	{
																		int rowCount = 0;
																		while (nppRs.next())
																		{
															%>

															<%
																if (rowCount % 2 == 0)
																			{
															%>
															<TR class="tbdarkrow">
																<%
																	} else
																				{
																%>
															
															<TR class="tblightrow">
																<%
																	}
																%>

																<TD><%=nppRs.getString("ma")%></TD>
																<TD><%=nppRs.getString("ten")%></TD>
																<TD><%=nppRs.getString("dienthoai")%></TD>
																<TD><%=nppRs.getString("diachi")%></TD>

																<TD style="display:none">
																	<input type="hidden" name="nppIdck" value="<%=nppRs.getString("pk_seq")%>"> 
																	<input style="text-align: right;" type="text" name="chietkhauNPP" class="chietkhauNPP" value="<%=nppRs.getDouble("chietkhaunpp")%>">
																</TD>

																<%
																	if (obj.getNppId().indexOf(nppRs.getString("pk_seq")) >= 0)
																				{
																%>
																<TD align="center">
																	<input type="checkbox" name="nppId" value="<%=nppRs.getString("pk_seq")%>" checked="checked" >
																</TD>
																<%
																	} else
																				{
																%>
																<TD align="center">
																	<input type="checkbox" name="nppId" value="<%=nppRs.getString("pk_seq")%>" >
																</TD>
																<%
																	}
																%>

															</TR>

															<%
																rowCount++;
																		}
																	}
															%>
														</TABLE>
					     
					     
					     
					    </div>
					    <div class="tab-pane" id="tab2">
					      
					      <TABLE width="100%" border="0" cellspacing="1"
															cellpadding="0">
															<TR class="tbheader">
																<TH width="10%">Nhãn hàng</TH>
																<TH width="10%">Chủng loại</TH>
																<TH width="10%">Mã sản phẩm</TH>
																<TH width="20%">Tên sản phẩm</TH>
																<TH width="10%">Đơn vị</TH>
																<TH width="10%">Giá bán</TH>
																<TH width="10%">Giá hướng dẫn</TH>
																<TH width="10%">Chiết khấu (%)</TH>
																<TH width="10%">Chọn bán <input type="checkbox" id="chekAllSp" onclick="checkedAll();">
																</TH>
															</TR>


															<%
																int rowCount = 0;
																while (spRs != null && spRs.next())
																	{
																	
																		String giaban = spGiaban.get(spRs.getString("spId")) != null ? spGiaban.get(spRs.getString("spId")) : "";
																		String giahuongdan = spGiahuongdan.get(spRs.getString("spId")) != null ? spGiahuongdan.get(spRs.getString("spId")) : "";
																		String ptChietkhau = spPtChietkhau.get(spRs.getString("spId")) != null ? spPtChietkhau.get(spRs.getString("spId")) : "";
															%>
															<%
																if (rowCount % 2 == 0)
																			{
															%>
															<TR class="tbdarkrow">
																<%
																	} else
																				{
																%>
															
															<TR class="tblightrow">
																<%
																	}
																%>
																<TD> 
																	<%=spRs.getString("nhTen")%>
																	<%-- <input type="text" style="width: 100%" name=nhTen value="<%=spRs.getString("nhTen")%>" readonly="readonly"> --%>
																</TD>
																<TD> 
																<%=spRs.getString("clTen")%>
																	<input type="hidden" style="width: 100%" name="clTen" value="<%=spRs.getString("clTen")%>" readonly="readonly">
																</TD>
																<TD>
																	<%=spRs.getString("spMa")%>	
																	<input type="hidden" style="width: 100%" name="spId" value="<%=spRs.getString("spId")%>">
																	<input type="hidden" style="width: 100%" name="spNhanHangId" value="<%=spRs.getString("NHANHANG_FK")%>">
																	<input type="hidden" style="width: 100%" name="spChungLoaiId" value="<%=spRs.getString("CHUNGLOAI_FK")%>">
																	<%-- <input type="text" style="width: 100%" name="spMa" value="<%=spRs.getString("spMa")%>" readonly="readonly"> --%>
																</TD>
																<TD> 
																	<%=spRs.getString("spTen")%>
																	<%-- <input type="text" style="width: 100%" name="spTen" value="<%=spRs.getString("spTen")%>" readonly="readonly"> --%>
																</TD>
																<TD>
																		<%=spRs.getString("donvi")%>	
																	<%-- <input type="text" style="width: 100%; text-align: center;" name="donvi" value="<%=spRs.getString("donvi")%>" readonly="readonly"> --%>
																</TD>
																<TD>
																	<input type="text" readonly="readonly" style="width: 100%; text-align: right;" onkeyup="Dinhdang(this)" name="giaban" value="<%=giaban%>"> 
																</TD>
																<TD>
																	<input type="text" style="width: 100%; text-align: right;" onkeyup="Dinhdang(this)" name="giahuongdan" value="<%=giahuongdan%>"> 
																</TD>
																<TD>
																	<input type="text" style="width: 100%; text-align: right;"   onkeyup="Dinhdang(this)" name="ptChietkhau" value="<%=ptChietkhau%>">
																</TD>
																<TD align="center">
																	<%
																		if (spChonban.get(spRs.getString("spId")) != null)
																				{
																	%> <input type="checkbox" name="chonban" value="<%=spRs.getString("spId")%>" checked="checked">
																	<%
																		} else
																				{
																	%> <input type="checkbox" name="chonban" value="<%=spRs.getString("spId")%>"> 
																	<%
 																		}
 																	%>
																</TD>
															</TR>
															<%
															rowCount++; }
															%>
														</TABLE>
					      
					      
					    </div>
					  </div>
					</div>
</td>
</TR>						

										<%-- <TR>
											<td colspan="6">

												<ul class="tabs">

													<li><a href="#">Nhà phân phối</a></li>

													<li><a href="#">Chọn sản phẩm</a></li>

												</ul>

												<div class="panes">
													<div>
														<a class="button2" href="javascript:changeKhachHang();">
															<img style="top: -4px;" src="../images/button.png" alt="">Lọc
															sản phẩm
														</a>&nbsp;&nbsp;&nbsp;&nbsp; <br /> <br />

														<TABLE width="100%" border="0" cellspacing="1"
															cellpadding="4">
															<TR class="tbheader">
																<TH width="15%">Mã Nhà phân phối</TH>
																<TH width="25%">Họ tên</TH>
																<TH width="10%">Điện thoại</TH>
																<TH width="30%">Địa chỉ</TH>
																<TH width="10%">Chiết khấu</TH>
																<TH width="10%">Chọn <input type="checkbox" id="checkAllKh" onclick="checkedAll_KhachHang();">
																</TH>
															</TR>
															<%
																if (nppRs != null)
																	{
																		int rowCount = 0;
																		while (nppRs.next())
																		{
															%>

															<%
																if (rowCount % 2 == 0)
																			{
															%>
															<TR class="tbdarkrow">
																<%
																	} else
																				{
																%>
															
															<TR class="tblightrow">
																<%
																	}
																%>

																<TD><%=nppRs.getString("ma")%></TD>
																<TD><%=nppRs.getString("ten")%></TD>
																<TD><%=nppRs.getString("dienthoai")%></TD>
																<TD><%=nppRs.getString("diachi")%></TD>

																<TD>
																	<input type="hidden" name="nppIdck" value="<%=nppRs.getString("pk_seq")%>"> 
																	<input style="text-align: right;" type="text" name="chietkhauNPP" class="chietkhauNPP" value="<%=nppRs.getDouble("chietkhaunpp")%>">
																</TD>

																<%
																	if (obj.getNppId().indexOf(nppRs.getString("pk_seq")) >= 0)
																				{
																%>
																<TD align="center">
																	<input type="checkbox" name="nppId" value="<%=nppRs.getString("pk_seq")%>" checked="checked" >
																</TD>
																<%
																	} else
																				{
																%>
																<TD align="center">
																	<input type="checkbox" name="nppId" value="<%=nppRs.getString("pk_seq")%>" >
																</TD>
																<%
																	}
																%>

															</TR>

															<%
																rowCount++;
																		}
																	}
															%>
														</TABLE>
													</div>
													
													<div>
														<TABLE width="100%" border="0" cellspacing="1"
															cellpadding="0">
															<TR class="tbheader">
																<TH width="10%">Nhãn hàng</TH>
																<TH width="10%">Chủng loại</TH>
																<TH width="10%">Mã sản phẩm</TH>
																<TH width="20%">Tên sản phẩm</TH>
																<TH width="10%">Đơn vị</TH>
																<TH width="10%">Giá bán</TH>
																<TH width="10%">Chiết khấu (%)</TH>
																<TH width="10%">Chọn bán <input type="checkbox" id="chekAllSp" onclick="checkedAll();">
																</TH>
															</TR>


															<%
																while (spRs != null && spRs.next())
																	{
																		String giaban = spGiaban.get(spRs.getString("spId")) != null ? spGiaban.get(spRs.getString("spId")) : "";
																		String ptChietkhau = spPtChietkhau.get(spRs.getString("spId")) != null ? spPtChietkhau.get(spRs.getString("spId")) : "";
															%>
															<TR>
																<TD> 
																	<input type="text" style="width: 100%" name=nhTen value="<%=spRs.getString("nhTen")%>" readonly="readonly">
																</TD>
																<TD> 
																	<input type="text" style="width: 100%" name="clTen" value="<%=spRs.getString("clTen")%>" readonly="readonly">
																</TD>
																<TD>
																	<input type="hidden" style="width: 100%" name="spId" value="<%=spRs.getString("spId")%>">
																	<input type="text" style="width: 100%" name="spMa" value="<%=spRs.getString("spMa")%>" readonly="readonly">
																</TD>
																<TD> 
																	<input type="text" style="width: 100%" name="spTen" value="<%=spRs.getString("spTen")%>" readonly="readonly">
																</TD>
																<TD>
																	<input type="text" style="width: 100%; text-align: center;" name="donvi" value="<%=spRs.getString("donvi")%>" readonly="readonly">
																</TD>
																<TD>
																	<input type="text" style="width: 100%; text-align: right;" onkeyup="Dinhdang(this)" name="giaban" value="<%=giaban%>"> 
																</TD>
																<TD>
																	<input type="text" style="width: 100%; text-align: right;"  onkeyup="Dinhdang(this)" name="ptChietkhau" value="<%=ptChietkhau%>">
																</TD>
																<TD align="center">
																	<%
																		if (spChonban.get(spRs.getString("spId")) != null)
																				{
																	%> <input type="checkbox" name="chonban" value="<%=spRs.getString("spId")%>" checked="checked">
																	<%
																		} else
																				{
																	%> <input type="checkbox" name="chonban" value="<%=spRs.getString("spId")%>"> 
																	<%
 																		}
 																	%>
																</TD>
															</TR>
															<%
																}
															%>
														</TABLE>
													</div>
												</div>
											</td>
										</TR> --%>

									</TABLE>
								</FIELDSET>
							</TD>
						</TR>
					</TABLE>
				</TD>
			</TR>
		</TABLE>
	</form>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</HTML>
<%
	if (nppRs != null)
			nppRs.close();

		obj.DbClose();
%>

<%
	}
%>
