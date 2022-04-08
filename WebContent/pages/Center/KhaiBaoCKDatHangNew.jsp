<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="geso.dms.center.beans.khaibaockdathang.imp.*" %>
<%@page import="geso.dms.center.beans.khaibaockdathang.*" %>
<%@ page  import = "geso.dms.center.beans.thongtinsanpham.IThongtinsanpham" %>
<%@ page  import = "geso.dms.center.beans.thongtinsanpham.imp.ThongtinsanphamList" %>
<%@page import="java.util.Calendar" %>
<%@page import="java.util.Date" %>
<%@page import="java.util.List" %>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%@page import="java.sql.SQLException" %>
<%@ page  import = "java.util.Hashtable" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>

<%
 	IKhaibaoCKdathang obj =(KhaibaoCKdathang)session.getAttribute("ckdhBean");
	ResultSet spRs = obj.getSpRs();
	ResultSet nppRs = obj.getNppRs();
	ResultSet kvRs = obj.getKvRs();
	ResultSet vungRs = obj.getVungRs();
	ResultSet nganhhangRs = obj.getNganhhangRs();
	ResultSet nhanhangRs = obj.getNhanhangRs();
	ResultSet nhomspRs = obj.getNhomspRs();
	
	String[] chietkhauSp = (String[])obj.getChietkhauSP();
	String[] tumuc = (String[])obj.getTumuc();
	String[] denmuc = (String[])obj.getDenmuc();
	String[] chietkhauDh = (String[])obj.getChietkhauDh();
	String[] diengiaiMuc = (String[])obj.getGhichu();
%>

<% Hashtable<String, String> hbchitieu = (Hashtable<String, String>)obj.getChitieu(); %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
	<TITLE>DDT - Project</TITLE>
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

	<script>
	$(document).ready(function() {

	    //When page loads...
	    $(".tab_content").hide(); //Hide all content
	    var index = $("ul.tabs li.current").show().index(); 
	    $(".tab_content").eq(index).show();
	    //On Click Event
	    $("ul.tabs li").click(function() {
	  
	        $("ul.tabs li").removeClass("current"); //Remove any "active" class
	        $(this).addClass("current"); //Add "active" class to selected tab
	        $(".tab_content").hide(); //Hide all tab content  
	        var activeTab = $(this).find("a").attr("href"); //Find the href attribute value to identify the active tab + content  
	        $(activeTab).show(); //Fade in the active ID content
	        return false;
	    });

	});
	</script>

	<SCRIPT language="JavaScript" type="text/javascript">
				
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
		  var tungay = document.getElementById("tungay").value;
		  var denngay = document.getElementById("denngay").value;
	
		  if( tungay == '' )
		  {
			  alert("Bạn phải nhập từ ngày");
			  return;
		  }
		  	
		  if( denngay == '' )
		  {
			  alert("Bạn phải nhập đến ngày");
			  return;
		  }
		  var active =$(".tabs li.current").index();
		  document.forms["ckdhForm"].activeTab.value =active;
		  document.forms["ckdhForm"].action.value = "save";
		  document.forms["ckdhForm"].submit(); 
	  }
	
		function submitform()
		{
			var active =$(".tabs li.current").index();
			document.forms["ckdhForm"].activeTab.value =active;
			document.forms["ckdhForm"].action.value = "submit";
			document.forms["ckdhForm"].submit(); 
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

<% Csrf csdr=new Csrf(request,response,true,false,true);%>
<input type="hidden" name="userId" value='<%= userId %>' >
<input type="hidden" id="activeTab" name="activeTab" value='<%=obj.getActiveTab()%>'>
<input type="hidden" name="action" value="0">
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền > Khai báo CK đặt hàng > Tạo mới</TD> 
							 <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%= userTen %></TD></tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
			<TR ><TD >
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR class = "tbdarkrow">
							<TD width="30" align="left"><A href="../../KhaibaoCKdathangSvl?userId=<%=userId%>" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
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
				
	    				<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold"  style="width: 100% ; color:#F00 ; font-weight:bold" readonly="readonly" rows="1"><%= obj.getMessage() %></textarea>
						</FIELDSET>
				   </TD>
				</tr>			

				<TR>
				  <TD height="100%" width="100%">
						<FIELDSET >
						<LEGEND class="legendtitle" style="color:black">Thông tin khai báo </LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
							<TR>
								<TD width="120px" class="plainlabel" >Từ ngày<FONT class="erroralert"> *</FONT></TD>
								<TD width="250px" class="plainlabel" >
									<input type="text" name="tungay" id="tungay" class="days" value="<%= obj.getTungay() %>" readonly="readonly" >
								</TD>
						  	  	<TD width="120px" class="plainlabel" >Đến ngày<FONT class="erroralert"> *</FONT></TD>
								<TD class="plainlabel" >
									<input type="text" name="denngay" id="denngay" class="days" value="<%= obj.getDenngay() %>" readonly="readonly" >
								</TD>
						  </TR>
						  <TR>
						  	  	<TD class="plainlabel">Scheme <FONT class="erroralert"> *</FONT></TD>
						  	  	<TD class="plainlabel" >
						  	  		<input type="text" name="scheme" id="scheme" value="<%= obj.getScheme() %>"> 
						  	  	</TD>
						  	  	<TD class="plainlabel">Loại chiết khấu </TD>
						  	  	<TD class="plainlabel" >
						  	  		<select name="loaick" onchange="submitform();">
						  	  			<% if(obj.getLoaiCK().equals("0")) { %>
						  	  				<option value="0" selected="selected" >Theo sản phẩm</option>
						  	  			<% } else { %>
						  	  				<option value="0"  >Theo sản phẩm</option>
						  	  			<% } %>
						  	  			<% if(obj.getLoaiCK().equals("1")) { %>
						  	  				<option value="1" selected="selected" >Theo đơn hàng</option>
						  	  			<% } else { %>
						  	  				<option value="1"  >Theo đơn hàng</option>
						  	  			<% } %>
						  	  		</select>
						  	  	</TD>
						  </TR>
						  <TR>
						  	  	<TD class="plainlabel">Diễn giải</TD>
						  	  	<TD class="plainlabel" colspan="3" >
						  	  		<input type="text" name="diengiai" id="diengiai" value="<%= obj.getDiengiai() %>"> 
						  	  	</TD>
						  	  	
						  </TR>
						</TABLE>
						
						
						<ul class="tabs">
						<% if(obj.getLoaiCK().equals("1")) { %>
							<li <%=obj.getActiveTab().equals("0") ? "class='current'" : "" %>><a href="#tabTieuchi">Chiết khấu theo đơn hàng</a></li>
							<li <%=obj.getActiveTab().equals("1") ? "class='current'" : "" %>><a href="#tabNpp">Nhà phân phối áp dụng</a></li>
						<% }else{ %>	
							<li <%=obj.getActiveTab().equals("0") ? "class='current'" : "" %>><a href="#tabTieuchi">Mức chiết khấu</a></li>
							<li <%=obj.getActiveTab().equals("1") ? "class='current'" : "" %>><a href="#tabSp">Sản phẩm</a></li>
							<li <%=obj.getActiveTab().equals("2") ? "class='current'" : "" %>><a href="#tabNpp">Nhà phân phối áp dụng</a></li>
						<% } %>
						</ul>
						
						<div class="panes">
							
							<div id="tabTieuchi" class="tab_content">
								
								<TABLE class="tabledetail" width="100%" border="0" cellspacing="1px" cellpadding="0px">
				                    
				                    <TR class="tbheader">
				                        <TH align="center" width="30%" rowspan="2" >Ghi chú</TH>
				                        <TH align="center" width="40%" colspan="2" >Doanh số phát sinh</TH>
				                        <td align="center" width="20%" rowspan="2" >Chiết khấu</td>
				                    </TR>
				                    
				                    <TR class="tbheader">
				                        <TH align="center" width="20%" >Từ mức</TH>
				                        <TH align="center" width="20%" >Đến mức</TH>
				                    </TR>
				                    
				                    <%
  				                    	int count = 0; 
  				                    	if( diengiaiMuc != null )  
  				                    	{ 
  				                    		for(int i = 0; i < diengiaiMuc.length; i++)
  				                    		{ 
 												%>    
												
												<tr>
													<td>
														<input type="text" name="diengiaiMuc" value="<%= diengiaiMuc[i] %>"   >
													</td>
													<td>
														<input type="text" name="tumuc" value="<%= tumuc[i] %>" style="text-align: right;" onkeyup="FormatNumber(this);" onkeypress="return keypress(event);" >
													</td>
													<td>
														<input type="text" name="denmuc" value="<%= denmuc[i] %>" style="text-align: right;" onkeyup="FormatNumber(this);" onkeypress="return keypress(event);" >
													</td>
													<td>
														<input type="text" name="chietkhau" value="<%= chietkhauDh[i] %>" style="text-align: right;"  onkeypress="return keypress(event);" >
													</td>
													
												</tr>
												                 			
				                    		<% count++; }
				                    		
  				                    	} 
				                    	
  				                    	for(int i = count; i < 10; i++)
  			                    		{ 
 			                    			%> 
			                    			
			                    			<tr>
												<td>
													<input type="text" name="diengiaiMuc" value=""   >
												</td>
												<td>
													<input type="text" name="tumuc" value="" style="text-align: right;" onkeyup="FormatNumber(this);" onkeypress="return keypress(event);" >
												</td>
												<td>
													<input type="text" name="denmuc" value="" style="text-align: right;" onkeyup="FormatNumber(this);" onkeypress="return keypress(event);" >
												</td>
												<td>
													<input type="text" name="chietkhau" value="" style="text-align: right;"  onkeypress="return keypress(event);" >
												</td>
												
											</tr>
			                    			
			                    		<%  } %> 
									
									<tr>
										<td>
											<input type="text" name="diengiaiMucVuot" value="Mức vượt quy định"   >
										</td>
										<td>
											<input type="text" value=">" style="text-align: center;" readonly="readonly" >
										</td>
										<td>
											<input type="text" name="mucVuot" value="<%= obj.getMucvuot() %>" style="text-align: right;" onkeyup="FormatNumber(this);" onkeypress="return keypress(event);" >
										</td>
										<td>
											<input type="text" name="chietkhauMucVuot" value="<%= obj.getCKMucvuot() %>" style="text-align: right;"  onkeypress="return keypress(event);" >
										</td>
										
									</tr>
									
				                    <TR>
				                        <TD align="center" colspan="15" class="tbfooter">&nbsp;</TD>
				                    </TR>
								</TABLE>
								
							</div>
							<% if(obj.getLoaiCK().equals("0")) { %>
							<div id="tabSp" class="tab_content">
								
								<TABLE class="tabledetail" width="100%" border="0" cellspacing="1px" cellpadding="0px">
				                    <tr>
				                		<td style="font-size: 12px; padding: 8px; " colspan="3" >Ngành hàng &nbsp;&nbsp;   
				                		
				                			<select name="nganhhangId" style="width: 200px;" onchange="submitform();"  >
												<option value="" ></option>
												<%
  													if(nganhhangRs != null) 
  													{ 
  														while(nganhhangRs.next()) 
  														{
  															if(obj.getNganhhangIds().indexOf(nganhhangRs.getString("pk_seq")) >= 0 ) 
  															{ 
 																%> 
																<option value="<%= nganhhangRs.getString("pk_seq") %>" selected="selected"  ><%= nganhhangRs.getString("ten") %></option>
															<% } else { %>
																<option value="<%= nganhhangRs.getString("pk_seq") %>"  ><%= nganhhangRs.getString("ten") %></option>
															<%}
  														}
  														nganhhangRs.close();
  													} 
 												%>							 
											</select>
				                		&nbsp;&nbsp; 
				                	Nhãn hàng
				                		&nbsp;&nbsp; 
				                			<select name="nhanhangId" style="width: 200px;" onchange="submitform();" >
												<option value="" ></option>
												<%
 													if(nhanhangRs != null) 
 													{ 
 														while(nhanhangRs.next()) 
 														{ 
 															if(obj.getNhanhangIds().indexOf(nhanhangRs.getString("pk_seq")) >= 0 ) 
 															{ 
 																%> 
																<option value="<%= nhanhangRs.getString("pk_seq") %>" selected="selected"  ><%= nhanhangRs.getString("ten") %></option>
															<% } else { %>
																<option value="<%= nhanhangRs.getString("pk_seq") %>"  ><%= nhanhangRs.getString("ten") %></option>
															<%}
 														} 
 														nhanhangRs.close(); 
 													} 
 												%>							 
											</select>
											&nbsp;&nbsp; 
				                	Nhóm sản phẩm
				                		&nbsp;&nbsp; 
				                			<select name="nhomspId" style="width: 200px;" onchange="submitform();" >
												<option value="" ></option>
												<%
 													if(nhomspRs != null) 
 													{ 
 														while(nhomspRs.next()) 
 														{ 
 															if(obj.getNhomspIds().indexOf(nhomspRs.getString("pk_seq")) >= 0 ) 
 															{ 
 																%> 
																<option value="<%= nhomspRs.getString("pk_seq") %>" selected="selected"  ><%= nhomspRs.getString("ten") %></option>
															<% } else { %>
																<option value="<%= nhomspRs.getString("pk_seq") %>"  ><%= nhomspRs.getString("ten") %></option>
															<%}
 														} 
 														nhomspRs.close(); 
 													} 
 												%>							 
											</select>
				                		</td>	
				                    </tr> 
				                    <TR class="tbheader">
				                        <th align="center" width="20%">Mã sản phẩm</th>
				                    	<th align="center" width="70%">Tên sản phẩm</th>
<!-- 				                    	<th align="center" width="10%">Chiết khấu </th> -->
				                    	<td align="center" width="10%">Chọn <input type="checkbox" name="checkAll" id="checkAll" onchange="SelectALL();" > </td>
				                    </TR>
				                    
				                    <%
				                    	if(spRs != null)
				                    	{
				                    		while(spRs.next())
				                    		{
				                    			%>
				                    			
				                    			<tr>
				                    				
				                    				<td><input type="text" value="<%= spRs.getString("ma") %>" style="width: 100%;" readonly="readonly" ></td>
				                    				<td><input type="text" value="<%= spRs.getString("ten") %>" style="width: 100%;" readonly="readonly" ></td>
<!-- 				                    				<td align="center"> -->
<%-- 				                    				<%	String chitieu = hbchitieu.get(spRs.getString("pk_seq")) != null ? hbchitieu.get(spRs.getString("pk_seq")) : ""; %> --%>
<%-- 				                    					<input type="text" name="spChietkhau" value="<%=chitieu %>" > --%>
<%-- 				                    					<input type="hidden" name="spId" value="<%=spRs.getString("pk_seq") %>" > --%>
<!-- 				                    				</td> -->
				                    				<td align="center">
				                    					<% if(obj.getSpIds().contains(spRs.getString("pk_seq"))) { %>
				                    						<input type="checkbox" name="spIds" value="<%= spRs.getString("pk_seq") %>" checked="checked" >
				                    					<% } else { %>
				                    						<input type="checkbox" name="spIds" value="<%= spRs.getString("pk_seq") %>"  >
				                    					<% } %>
				                    				</td>
				                    			</tr>
				                    			
				                    		<% }
				                    		spRs.close();
				                    	} %>
									
				                    <TR>
				                        <TD align="center" colspan="15" class="tbfooter">&nbsp;</TD>
				                    </TR>
								</TABLE>
								
							</div>
							<% } %>		
							<div id="tabNpp" class="tab_content">
								
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
 															if(obj.getKvIds().indexOf(kvRs.getString("pk_seq")) >= 0 ) 
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
				                        <th align="center" width="20%">Mã nhà phân phối</th>
				                    	<th align="center" width="70%">Tên nhà phân phối</th>
				                    	<th align="center" width="10%">Chọn <input type="checkbox" name="checkAll2" id="checkAll2" onchange="SelectALL2();" > </th>
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
