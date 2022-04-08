<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="geso.dms.center.beans.chitieu.imp.*"%>
<%@page import="geso.dms.center.beans.chitieu.*"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.List"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="geso.dms.center.util.*"%>
<%@ page import="java.util.Hashtable"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DecimalFormat"%>

<%
	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if (!util.check(userId, userTen, sum))
	{
		response.sendRedirect("/IMV/index.jsp");
	} else
	{
%>

<%
	IChiTieuNhanVien obj = (IChiTieuNhanVien) session.getAttribute("tctskuBean");
		ResultSet sanphamRs = obj.getSanphamRs();
		ResultSet vungRs = obj.getVungRs();
		ResultSet kvRs = obj.getKhuvucRs();
		ResultSet nppRs = obj.getNppRs();
		ResultSet tdvRs = obj.getTdvRs();
		ResultSet SsRs = obj.getSsRs();
		ResultSet AsmRs = obj.getAsmRs();
		ResultSet RsmRs = obj.getRsmRs();
		ResultSet KyRs = obj.getKyRs();
		ResultSet quyRs = obj.getQuyRs();

		ResultSet nhomRs = obj.getNhomRs();
		
		String[] nhomsp = obj.getNhomTen();
		String[] nhomspid = obj.getNhomId();

		String[] tumuc = (String[]) obj.getTumuc();
		String[] denmuc = (String[]) obj.getDenmuc();
		String[] thuong = (String[]) obj.getThuong();
		String[] donvi_tinh_ds = (String[]) obj.getDonvi_tinh_ds();
		String[] donvi_tinh_thuong = (String[]) obj.getDonvi_tinh_thuong();
		NumberFormat formatter = new DecimalFormat("#,###,###.##");
%>
<%
	Hashtable<String, String> npp_chitieu = (Hashtable<String, String>) obj.getNpp_chitieu();
%>
<%
	Hashtable<String, String> npp_donvi_chitieu = (Hashtable<String, String>) obj.getNpp_donvi_chitieu();
%>

<%
	Hashtable<String, String> tdv_chitieu = (Hashtable<String, String>) obj.getTdv_chitieu();
%>
<%
	Hashtable<String, String> asm_chitieu = (Hashtable<String, String>) obj.getAsm_chitieu();
%>
<%
	Hashtable<String, String> rsm_chitieu = (Hashtable<String, String>) obj.getRsm_chitieu();
%>
<%
	Hashtable<String, String> ss_chitieu = (Hashtable<String, String>) obj.getSs_chitieu();
%>


<%
	Hashtable<String, String> tdv_donvi_chitieu = (Hashtable<String, String>) obj.getTdv_donvi_chitieu();

	String url="Quản lý chỉ tiêu > Chỉ tiêu nhân viên > Chỉ tiêu cho Bán Ra > Tạo mới";
	if(obj.getLoaichitieu().equals("1")){
		 url="Quản lý chỉ tiêu > Chỉ tiêu nhân viên > Chỉ tiêu cho Mua Vào > Tạo mới";
	}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>DDT - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<link href="../scripts/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="../scripts/bootstrap/css/bootstrap-theme.min.css"
	rel="stylesheet">
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

<!-- <link href="../css/chosen.css" rel="stylesheet" type="text/css" />
<script src="../scripts/chosen.jquery.js" type="text/javascript"></script> -->


<script type="text/javascript">
		$(document).ready(function() {
			$(".days").datepicker({
				changeMonth : true,
				changeYear : true
			});
		});
	</script>


<script>
	$(function() {
	 	$("ul.tabs").tabs("div.panes > div");
	});
	</script>

<script>
	jQuery(document).ready(function()
			{
				$("select:not(.notuseselect2)").chosen();     
				
			}); 
	</script>


<SCRIPT language="JavaScript" type="text/javascript">
		function replaces()
		{
			var masp = document.getElementsByName("maspTra");
			var tensp = document.getElementsByName("tenspTra");
			var type = document.getElementById("hinhthuctra");
	
			var i;
			for(i=0; i < masp.length; i++)
			{
				if(masp.item(i).value != "")
				{
					var sp = masp.item(i).value;
					var pos = parseInt(sp.indexOf(" - "));
					if(pos > 0)
					{
						masp.item(i).value = sp.substring(0, pos);
						tensp.item(i).value = sp.substr(parseInt(sp.indexOf(" - ")) + 3);					
					}
				}
				else
				{
					tensp.item(i).value = "";
					if(type.value == "1")
					{
						var soluong = document.getElementsByName("soluong");
						soluong.item(i).value = "";
					}
				}			
			}
			setTimeout(replaces, 300);
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
		
		function save()
		{
		  document.forms["tctsku"].action.value = "save";
		  document.forms["tctsku"].submit(); 
	  	}
	
		function submitform()
		{
			document.forms["tctsku"].action.value = "submit";
			document.forms["tctsku"].submit(); 
		}
		
	function FormatNumber(e)
	{
		e.value = DinhDangTien(e.value.replace(/,/g,""));
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
	
	function SelectALL()
	{
		var checkAll = document.getElementById("checkAll");
		var spIds = document.getElementsByName("spIds");
		
		if(checkAll.checked == true)
		{
			for(i = 0; i < spIds.length; i++)
				spIds.item(i).checked = true;
		}
		else
		{
			for(i = 0; i < spIds.length; i++)
				spIds.item(i).checked = false;
		}
		
	}
	
	function SelectALL2()
	{
		var checkAll = document.getElementById("checkAll2");
		var spIds = document.getElementsByName("nppIds");
		
		if(checkAll.checked == true)
		{
			for(i = 0; i < spIds.length; i++)
				spIds.item(i).checked = true;
		}
		else
		{
			for(i = 0; i < spIds.length; i++)
				spIds.item(i).checked = false;
		}
		
	}
	function SelectALL3()
	{
		var checkAll = document.getElementById("checkAll3");
		var spIds = document.getElementsByName("tdvIds");
		
		if(checkAll.checked == true)
		{
			for(i = 0; i < spIds.length; i++)
				spIds.item(i).checked = true;
		}
		else
		{
			for(i = 0; i < spIds.length; i++)
				spIds.item(i).checked = false;
		}
		
	}
	
	function upload()
	{
		   if(document.forms["tctsku"].filename.value=="")
		   {   
			   document.forms["tctsku"].dataerror.value="Chưa tìm file upload lên. Vui lòng chọn file cần upload.";
		   }else
		   {
			 document.forms["tctsku"].setAttribute('enctype', "multipart/form-data", 0);
			 document.forms["tctsku"].action.value = "save";
			 document.forms["tctsku"].submit();	
		   }
	}
	
	function xuatExcel()
	{
		document.forms['tctsku'].action.value= 'excel';
		document.forms['tctsku'].submit();
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

	<form name="tctsku" method="post"
		action="../../ChiTieuNhanVienUpdateSvl"> 
 <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
		<input type="hidden" name="userId" value='<%=userId%>'> <input
			type="hidden" name="action" value="0"> <input type="hidden"
			name="loaichitieu" value='<%=obj.getLoaichitieu()%>'> <input
			type="hidden" name="view" value='<%=obj.getView()%>'> <input
			type="hidden" name="tructhuocId" value='<%=obj.getTructhuocId()%>'>
		<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
			height="100%">
			<TR>
				<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
						<TR>
							<TD align="left" class="tbnavigation">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr height="22">
										<TD align="left" colspan="2" class="tbnavigation">&nbsp; <%= url %></TD>
										<TD colspan="2" align="right" class="tbnavigation">Chào
											mừng bạn <%=userTen%></TD>
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
										<TD width="30" align="left"><A
											href="../../ChiTieuSvl?userId=<%=userId%>"><img
												src="../images/Back30.png" alt="Quay ve" title="Quay ve"
												border="1" longdesc="Quay ve" style="border-style: outset"></A></TD>
										<TD width="2" align="left"></TD>
										<TD width="30" align="left">
											<div id="btnSave">
												<A href="javascript: save()"><IMG
													src="../images/Save30.png" title="Luu lai" alt="Luu lai"
													border="1" style="border-style: outset"></A>
											</div>
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
									<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>

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
									<LEGEND class="legendtitle" style="color: black">Chỉ
										tiêu </LEGEND>
									<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">

												<TR>
								<TD width="130px" class="plainlabel" valign="top">Tháng <FONT class="erroralert"> </FONT></TD>
								<TD width="300px" class="plainlabel" valign="top">
									<select name="thang" style="width :50px">
									<option value=0> </option>  
									<%
  										int k=1;
  									for(k=1;k<=12;k++){
  									  if(obj.getThang().equals(k+"")){
  									%>
									<option value=<%=k%> selected="selected" > <%=k%></option> 
									<%
 										}else{
 									%>
									<option value=<%=k%> ><%=k%></option> 
									<%
 										}
 									  }
 									%>
									</select>
									
								</TD>
								<TD width="120px" class="plainlabel" valign="top"> Năm </TD>
								<TD class="plainlabel" valign="top">
									<select name="nam"  style="width :80px">
									<option value=0> </option>  
									<%
									  Calendar c2 = Calendar.getInstance();
  										int t=c2.get(Calendar.YEAR) +6;
  										int n;
  										for(n=2015;n<t;n++){
  										if(obj.getNam().equals(n+"")){
  									%>
									<option value=<%=n%> selected="selected" > <%=n%></option> 
									<%
 										}else{
 									%>
									<option value=<%=n%> ><%=n%></option> 
									<%
 										}
 									 }
 									%>
									</select>
								</TD>
							</TR>

										<TR>
											<TD class="plainlabel">Scheme <FONT class="erroralert">
													*</FONT></TD>
											<TD class="plainlabel" colspan="3"><input type="text"
												name="scheme" id="scheme" value="<%=obj.getScheme()%>">
											</TD>
										</TR>
										<TR>
											<TD class="plainlabel">Diễn giải</TD>
											<TD class="plainlabel" colspan="3"><input type="text"
												name="diengiai" id="diengiai"
												value="<%=obj.getDiengiai()%>">

												&nbsp;&nbsp;&nbsp;&nbsp;</TD>
										</TR>

									<%-- 	<TR>
											<TD class="plainlabel" colspan="6"><a href="#"
												class="btn btn-lg btn-primary" data-toggle="modal"
												data-target="#divNhanHang">Nhóm chỉ tiêu</a>
												<div class="modal fade" id="divNhanHang" tabindex="-1"
													role="dialog" aria-labelledby="myModalLabel"
													aria-hidden="true">
													<div class="modal-dialog modal-lg">
														<div class="modal-content">
															<div class="modal-header">
																<button type="button" class="close" data-dismiss="modal"
																	aria-hidden="true">&times;</button>
																<h4 class="modal-title" id="myModalLabel">Nhóm chỉ
																	tiêu</h4>
															</div>
															<div class="modal-body">
																<table width="95%" align="center">
																	<TR class="tbheader">
																		<th width="250px"
																			style="font-size: 12px; text-align: center;">Tên</th>
																		<th width="80px"
																			style="font-size: 12px; text-align: center;">Chọn
																		</th>
																	</tr>
																	<%
																		int sodong = 0;
																			while (nhomRs != null && nhomRs.next())
																			{
																				/* String ck = nhPtCk.get(nhRs.getString("nhId")) != null ? nhPtCk.get(nhRs.getString("nhId")) : ""; */
																	%>
																	<TR>
																		<TD><input type="hidden" style="width: 100%"
																			name="nhId" value="<%=nhomRs.getString("pk_seq")%>">
																			<input type="text" style="width: 100%" name="nhTen"
																			value="<%=nhomRs.getString("ten")%>"
																			readonly="readonly"></TD>
																		<TD><input type="checkbox" style="width: 100%"
																			name="nhPtCk"></TD>
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
												</div></TD>
										</TR> --%>

										<tr class="plainlabel">
											<TD class="plainlabel">File Upload</td>
											<TD class="plainlabel"><INPUT type="file"
												name="filename" size="40" value=''></TD>
											<TD class="plainlabel" >&nbsp;&nbsp;&nbsp;&nbsp;
												<a class="button2" href="javascript:upload()"> <img
													style="top: -4px;" src="../images/button.png" alt="">
													Cập nhật
											</a>
											</td>
											<TD class="plainlabel" >&nbsp;&nbsp;&nbsp;&nbsp;
												<a class="button2" href="javascript:xuatExcel()"> <img
													style="top: -4px;" src="../images/button.png" alt="">
													Xuất Excel
											</a>
											</td>
										</tr>

									</TABLE>
										
								</FIELDSET>
							</TD>
						</TR>
					</TABLE>
				</TD>
			</TR>
		</TABLE>
	</form>
	<script type="text/javascript">
	replaces();
</script>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</HTML>
<%
	}
%>
