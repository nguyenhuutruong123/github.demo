<%@page import="java.util.Hashtable"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="geso.dms.center.beans.tieuchithuong.imp.*" %>
<%@page import="geso.dms.center.beans.tieuchithuong.*" %>
<%@ page  import = "geso.dms.center.beans.dieukienkhuyenmai.ISanpham" %>
<%@ page  import = "geso.dms.center.beans.dieukienkhuyenmai.imp.Sanpham" %>
<%@page import="java.util.Calendar" %>
<%@page import="java.util.Date" %>
<%@page import="java.util.List" %>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%@page import="java.sql.SQLException" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>

<%
 	ITieuchithuongTD obj =(ITieuchithuongTD)session.getAttribute("tctskuBean");
	ResultSet sanphamRs = obj.getSanphamRs();
	ResultSet vungRs = obj.getVungRs();
	ResultSet kvRs = obj.getKhuvucRs();
	ResultSet nppRs = obj.getNppRs();
	
	Hashtable<String, String> dieukien = obj.getDieukien();
	Hashtable<String, String> quydoi = obj.getQuydoi();
	
	String[] maspTra = (String[])obj.getMaspTra();
	String[] tenspTra = (String[])obj.getTenspTra();
	String[] soluongspTra = (String[])obj.getSoluongspTra();
	String[] idspTra = (String[])obj.getIdspTra();
	String[] dongiaspTra = (String[])obj.getDongiaspTra();
	
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
	<TITLE>dms - Project</TITLE>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">
	
	<LINK rel="stylesheet" href="../css/main.css" type="text/css">
	<script type="text/javascript"	src="../scripts/jquery.min.1.7.js"></script>
	<script type="text/javascript" language="JavaScript" src="../scripts/jquery.tools.min.js"></script>
	
	<script type="text/javascript" src="../scripts/ajax.js"></script>
	<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs.css">
	<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs-panes.css">
	
	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
	<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
	<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
	<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>

	<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
	<style type="text/css">
		#mainContainer{
			width:600px;
			margin:0 auto;
			text-align:left;
			height:100%;
			border-left:3px double #000;
			border-right:3px double #000;
		}
		#formContent{
			padding:5px;
		}
		/* END CSS ONLY NEEDED IN DEMO */
			
		/* Big box with list of options */
		#ajax_listOfOptions{
			position:absolute;	/* Never change this one */
			width:auto;	/* Width of box */
			height:200px;	/* Height of box */
			overflow:auto;	/* Scrolling features */
			border:1px solid #317082;	/* Dark green border */
			background-color:#C5E8CD;	/* White background color */
	    	color: black;
			text-align:left;
			font-size:1.0em;
			z-index:100;
		}
		#ajax_listOfOptions div{	/* General rule for both .optionDiv and .optionDivSelected */
			margin:1px;		
			padding:1px;
			cursor:pointer;
			font-size:1.0em;
		}
		#ajax_listOfOptions .optionDiv{	/* Div for each item in list */
			
		}
		#ajax_listOfOptions .optionDivSelected{ /* Selected item in the list */
			background-color:#317082; /*mau khi di chuyen */
			color:#FFF;
		}
		#ajax_listOfOptions_iframe{
			background-color:#F00;
			position:absolute;
			z-index:5;
		}
		form{
			display:inline;
		}
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
		}	
		#dhtmlpointer
		{
			position:absolute;
			left: -300px;
			z-index: 101;
			visibility: hidden;
		}	
	</style>
	<script type="text/javascript" src="../scripts/ajax.js"></script>
	<script type="text/javascript" src="../scripts/dkkhuyenmai_sanpham.js"></script>

	<script type="text/javascript">
		$(document).ready(function() {
			$(".days").datepicker({
				changeMonth : true,
				changeYear : true
			});
			
			//$("ul.tabs").tabs("div.panes > div");
		});
	</script> 
	
	<script>
	$(function() {
	 	$("ul.tabs").tabs("div.panes > div");
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
		  var thang = document.getElementById("thang").value;
		  var nam = document.getElementById("nam").value;
	
		  if( thang == '' )
		  {
			  alert("Bạn phải nhập từ ngày");
			  return;
		  }
		  	
		  if( nam == '' )
		  {
			  alert("Bạn phải nhập đến ngày");
			  return;
		  }
		  
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
	
	/* function ajaxOption(id, str)
	{
		String vungId = document.getElementById("vungId").value;
		String kvId = document.getElementById("khuvucId").value;
		
		var xmlhttp;
		if (str == "")
		{
		   document.getElementById(id).innerHTML = "";
		   return;
		}
		if (window.XMLHttpRequest)
		{// code for IE7+, Firefox, Chrome, Opera, Safari
		   xmlhttp = new XMLHttpRequest();
		}
		else
		{// code for IE6, IE5
		   xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		xmlhttp.onreadystatechange=function()
		{
		   if (xmlhttp.readyState == 4 && xmlhttp.status == 200)
		   {
		      document.getElementById(id).innerHTML = xmlhttp.responseText;
		   }
		}
		xmlhttp.open("POST","../../TieuchithuongTDSvl?vungId=" + vungId + "&khuvucId=" + kvId + "&type=Ajax",true);
		xmlhttp.send();
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

<% Csrf csdr=new Csrf(request,response,true,false,true);%>
<input type="hidden" name="userId" value='<%= userId %>' >
<input type="hidden" name="action" value="0">
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý khuyến mại > Khuyến mại tích điểm > Tạo mới</TD> 
							 <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%= userTen %></TD></tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
			<TR ><TD >
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR class = "tbdarkrow">
							<TD width="30" align="left"><A href="../../TieuchithuongTDSvl?userId=<%=userId%>" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
						    <TD width="2" align="left" ></TD>
						    <TD width="30" align="left" >
						    <div id="btnSave">
						    <A href="javascript: save()" ><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A>
						    </div>
						    </TD>
							<TD >&nbsp; </TD>						
						</TR>
					</TABLE>
			</TD></TR>
		</TABLE>
			<TABLE width="100%" border="0" cellpadding="0"  cellspacing="1" >
			  	<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>
				
	    				<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold"  style="width: 100% ; color:#F00 ; font-weight:bold" readonly="readonly" rows="1"><%= obj.getMsg() %></textarea>
						</FIELDSET>
				   </TD>
				</tr>			

				<TR>
				  <TD height="100%" width="100%">
						<FIELDSET >
						<LEGEND class="legendtitle" style="color:black">Khuyến mại tích điểm </LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
							<TR>
								<TD width="120px" class="plainlabel" >Từ ngày<FONT class="erroralert"> *</FONT></TD>
								<TD width="250px" class="plainlabel" >
									<input type="text" name="thang" id="thang" class="days" value="<%= obj.getThang() %>" readonly="readonly" >
								</TD>
						  	  	<TD width="120px" class="plainlabel" >Đến ngày<FONT class="erroralert"> *</FONT></TD>
								<TD class="plainlabel" >
									<input type="text" name="nam" id="nam" class="days" value="<%= obj.getNam() %>" readonly="readonly" >
								</TD>
						  </TR>
						  <TR>
						  	  	<TD class="plainlabel">Scheme <FONT class="erroralert"> *</FONT></TD>
						  	  	<TD class="plainlabel" >
						  	  		<input type="text" name="scheme" id="scheme" value="<%= obj.getScheme() %>"> 
						  	  	</TD>
						  	  	<TD class="plainlabel">Loại tích điểm </TD>
						  	  	<TD class="plainlabel" >
						  	  		<select name="httt" onchange="submitform();" >
						  	  			<% if(obj.getHTTT().equals("0")) { %>
						  	  				<option value="0" selected="selected" >Theo sản phẩm</option>
						  	  			<% } else { %>
						  	  				<option value="0"  >Theo sản phẩm</option>
						  	  			<% } %>
						  	  			<% if(obj.getHTTT().equals("1")) { %>
						  	  				<option value="1" selected="selected" >Theo nhóm sản phẩm</option>
						  	  			<% } else { %>
						  	  				<option value="1"  >Theo nhóm sản phẩm</option>
						  	  			<% } %>
						  	  		</select>
						  	  	</TD>
						  </TR>
						  <TR>
						  	  	<TD class="plainlabel">Diễn giải</TD>
						  	  	<TD class="plainlabel" >
						  	  		<input type="text" name="diengiai" id="diengiai" value="<%= obj.getDiengiai() %>"> 
						  	  	</TD>
						  	  	<TD class="plainlabel" > </TD>
								<TD class="plainlabel"  >
									<input type="text" name="ptTRACK" id="ptTRACK" value="<%= obj.getPT_TRACK() %>" style="display: none;"  >
									
									&nbsp;&nbsp;&nbsp;&nbsp;
						  	  		
						  	  		<%-- <% if(obj.getDoanhsotheoThung().equals("1")) { %>
						  	  			<input type="checkbox" name="doanhsothung" checked="checked" value="1" > <i>Doanh số tính theo thùng</i>
						  	  		<% } else { %>
						  	  			<input type="checkbox" name="doanhsothung" value="1" > <i>Doanh số tính theo thùng</i>
						  	  		<% } %> --%>
									
								</TD>
						  </TR>
						</TABLE>
						
						
						<ul class="tabs">
							<li><a href="#">Công thức tích điểm</a></li>
							<li><a href="#">Áp dụng cho</a></li>
							<li><a href="#">Trả tích điểm</a></li>
						</ul>
						
						<div class="panes">
							
							<div>
								
								<TABLE class="tabledetail" width="100%" border="0" cellspacing="1px" cellpadding="0px">
				                    
				                    <TR class="tbheader">
				                        <td align="center" width="15%">Mã nhóm / sản phẩm</td>
				                    	<td align="center" width="35%">Tên nhóm / sản phẩm</td>
				                    	
				                    	<td align="center" width="10%" >Điều kiện</td>
				                    	<td align="center" width="10%" >Đơn vị</td>
				                    	
				                    	<td align="center" width="10%" >Quy đổi</td>
				                    	<td align="center" width="10%" >Đơn vị</td>
				                    </TR>
				                    
				                    <%
				                    	if(sanphamRs != null)
				                    	{
				                    		while(sanphamRs.next())
				                    		{
				                    			String _dieukien = "";
				                    			String _dvDieukien = "";
				                    			if( dieukien.get(sanphamRs.getString("pk_seq")) != null )
				                    			{
				                    				_dieukien = dieukien.get(sanphamRs.getString("pk_seq")).split("__")[0];
				                    				_dvDieukien = dieukien.get(sanphamRs.getString("pk_seq")).split("__")[1];
				                    			}
				                    			
				                    			String _quydoi = "";
				                    			String _dvQuydoi = "";
				                    			if( quydoi.get(sanphamRs.getString("pk_seq")) != null )
				                    			{
				                    				_quydoi = quydoi.get(sanphamRs.getString("pk_seq")).split("__")[0];
				                    				_dvQuydoi = quydoi.get(sanphamRs.getString("pk_seq")).split("__")[1];
				                    			}
				                    			
				                    			%>
				                    			
				                    			<tr>
				                    				
				                    				<td>
				                    					<input type="hidden" name="spIds" value="<%= sanphamRs.getString("pk_seq") %>" style="width: 100%;" readonly="readonly" >
				                    					<input type="text" value="<%= sanphamRs.getString("ma") %>" style="width: 100%;" readonly="readonly" >
				                    				</td>
				                    				<td><input type="text" value="<%= sanphamRs.getString("ten") %>" style="width: 100%;" readonly="readonly" ></td>
				                    				
				                    				<td>
														<input type="text" name="dieukien" value="<%= _dieukien %>" style="text-align: right;"  onkeypress="return keypress(event);" >
													</td>
													<td>
														<select name="donviDIEUKIEN" style="width: 100%;" >
															<% if(_dvDieukien.equals("0")) { %>
																<option value="0" selected="selected" > Số lượng </option>	
															<% } else { %>
																<option value="0" > Số lượng </option>	
															<% } %>
															<% if(_dvDieukien.equals("1")) { %>
																<option value="1" selected="selected" > Số tiền </option>
															<% } else { %>
																<option value="1" > Số tiền </option>
															<% } %>						
														</select>
													</td>
													
				                    				<td>
														<input type="text" name="quydoi" value="<%= _quydoi %>" style="text-align: right;"  onkeypress="return keypress(event);" >
													</td>
													<td>
														<select name="donviQUYDOI" style="width: 100%;" >
															<% if(_dvQuydoi.equals("0")) { %>
																<option value="0" selected="selected" > Điểm </option>	
															<% } else { %>
																<option value="0" > Điểm </option>	
															<% } %>
															<% if(_dvQuydoi.equals("1")) { %>
																<option value="1" selected="selected" > VNĐ </option>
															<% } else { %>
																<option value="1" > VNĐ </option>
															<% } %>								
														</select>
													</td>
				                    				
				                    			</tr>
				                    			
				                    		<% }
				                    		sanphamRs.close();
				                    	}
				                    %>
									
				                    <TR>
				                        <TD align="center" colspan="15" class="tbfooter">&nbsp;</TD>
				                    </TR>
								</TABLE>
								
							</div>
									
							<div>
								
								<TABLE class="tabledetail" width="100%" border="0" cellspacing="1px" cellpadding="0px">
				                    
				                    <tr>
				                		<td style="font-size: 12px; padding: 8px; " colspan="3" >Vùng &nbsp;&nbsp;   
				                		
				                			<select name="vungId" style="width: 200px;" onchange="submitform();"  >
												<option value="" ></option>
												<%
													if(vungRs != null)
													{
														while(vungRs.next())
														{
															if(obj.getVungIds().indexOf(vungRs.getString("pk_seq")) >= 0 )
															{
																%>
																<option value="<%= vungRs.getString("pk_seq") %>" selected="selected"  ><%= vungRs.getString("ten") %></option>
															<% } else { %>
																<option value="<%= vungRs.getString("pk_seq") %>"  ><%= vungRs.getString("ten") %></option>
															<%}
														}
														vungRs.close();
													}
												%>							
											</select>
				                		&nbsp;&nbsp; 
				                	Khu vực
				                		&nbsp;&nbsp; 
				                			<select name="khuvucId" style="width: 200px;" onchange="submitform();" >
												<option value="" ></option>
												<%
													if(kvRs != null)
													{
														while(kvRs.next())
														{
															if(obj.getKhuvucIds().indexOf(kvRs.getString("pk_seq")) >= 0 )
															{
																%>
																<option value="<%= kvRs.getString("pk_seq") %>" selected="selected"  ><%= kvRs.getString("ten") %></option>
															<% } else { %>
																<option value="<%= kvRs.getString("pk_seq") %>"  ><%= kvRs.getString("ten") %></option>
															<%}
														}
														kvRs.close();
													}
												%>							
											</select>
				                		</td>	
				                    </tr>
				                    
				                    <TR class="tbheader">
				                        <td align="center" width="20%">Mã nhà phân phối</td>
				                    	<td align="center" width="70%">Tên nhà phân phối</td>
				                    	<td align="center" width="10%">Chọn <input type="checkbox" name="checkAll2" id="checkAll2" onchange="SelectALL2();" > </td>
				                    </TR>
				                    
				                    <%
				                    	if(nppRs != null)
				                    	{
				                    		while(nppRs.next())
				                    		{
				                    			%>
				                    			
				                    			<tr>
				                    				
				                    				<td><input type="text" value="<%= nppRs.getString("ma") %>" style="width: 100%;" readonly="readonly" ></td>
				                    				<td><input type="text" value="<%= nppRs.getString("ten") %>" style="width: 100%;" readonly="readonly" ></td>
				                    				<td align="center">
				                    					<% if(obj.getNppIds().contains(nppRs.getString("pk_seq"))) { %>
				                    						<input type="checkbox" name="nppIds" value="<%= nppRs.getString("pk_seq") %>" checked="checked" >
				                    					<% } else { %>
				                    						<input type="checkbox" name="nppIds" value="<%= nppRs.getString("pk_seq") %>"  >
				                    					<% } %>
				                    				</td>
				                    				
				                    			</tr>
				                    			
				                    		<% }
				                    		nppRs.close();
				                    	}
				                    %>
									
				                    <TR>
				                        <TD align="center" colspan="15" class="tbfooter">&nbsp;</TD>
				                    </TR>
								</TABLE>
								
							</div>
							
							<div>
								
								<TABLE class="tabledetail" width="100%" border="0" cellspacing="1px" cellpadding="0px">
				                    <tr>
				                		<td style="font-size: 12px; padding: 8px; " colspan="4" >Hình thức trả  &nbsp;&nbsp; 
				                		
				                			<select name="hinhthuctra" id="hinhthuctra" style="width: 200px;" >
					                			<% if(obj.getHinhthuctra().equals("0")) { %>
													<option value="0" selected="selected" >Bất kỳ trong</option>
												<% } else { %>
													<option value="0" >Bất kỳ trong</option>
												<% } %>
												
												<% if(obj.getHinhthuctra().equals("1")) { %>
													<option value="1" selected="selected" >Nhập số lượng sản phẩm</option>	
												<% } else { %>
													<option value="1" >Nhập số lượng sản phẩm</option>	
												<% } %>						
											</select>
				                		</td>	
				                    </tr>
				                    
				                    <TR class="tbheader">
				                        <td align="center" width="20%">Mã sản phẩm</td>
				                    	<td align="center" width="60%">Tên sản phẩm</td>
				                    	<td align="center" width="10%">Số lượng</td>
				                    	<td align="center" width="10%">Đơn giá</td>
				                    </TR>
				                    
				                    <% 
				                    int count = 0;
				                    if(maspTra != null)
				                    {
				                    for(int i = 0; i < maspTra.length; i++) { %>
				                    	<tr>
				                    		<td>
				                    			<input type="text" name="maspTra" style="width: 100%" value="<%= maspTra[i] %>"  onkeyup="ajax_showOptions(this,'abc',event)"  AUTOCOMPLETE="off" >
				                    		</td>
				                    		<td>
				                    			<input type="text" name="tenspTra" style="width: 100%" readonly="readonly" value="<%= tenspTra[i] %>" >
				                    		</td>
				                    		<td>
				                    			<input type="text" name="soluongspTra" style="width: 100%; text-align: right;" value="<%= soluongspTra[i] %>" >
				                    		</td>
				                    		<td>
				                    			<input type="text" name="dongiaspTra" style="width: 100%; text-align: right;" value="<%= dongiaspTra[i] %>" >
				                    		</td>
				                    	</tr>
				                    <% count++; } } %>
				                    
				                    <%
				                    	while(count < 20)
				                    	{
				                    		%>
				                    		
				                    		<tr>
					                    		<td>
					                    			<input type="text" name="maspTra" style="width: 100%" value=""  onkeyup="ajax_showOptions(this,'abc',event)"  AUTOCOMPLETE="off" >
					                    		</td>
					                    		<td>
					                    			<input type="text" name="tenspTra" style="width: 100%" readonly="readonly" value="" >
					                    		</td>
					                    		<td>
					                    			<input type="text" name="soluongspTra" style="width: 100%; text-align: right;" value="" >
					                    		</td>
					                    		<td>
					                    			<input type="text" name="dongiaspTra" style="width: 100%; text-align: right;" value="" >
					                    		</td>
					                    	</tr>
				                    		
				                    <% count++; } %>
									
				                    <TR>
				                        <TD align="center" colspan="15" class="tbfooter">&nbsp;</TD>
				                    </TR>
								</TABLE>
								
							</div>
							
							
						</div>
									
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
<%}%>
