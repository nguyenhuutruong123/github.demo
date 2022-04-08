<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%@page import="javax.xml.crypto.Data"%>
<%@page import="geso.dms.center.beans.thuongdauthung.imp.Thuongdauthung"%>
<%@page import="geso.dms.center.beans.thuongdauthung.IThuongdauthung"%>
<%@ page  import = "geso.dms.center.beans.thuongdauthung.ISanpham" %>
<%@ page  import = "geso.dms.center.beans.thuongdauthung.imp.Sanpham" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%@ page  import = "java.text.NumberFormat" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "geso.dms.center.util.ChuyenNgu" %>

<%NumberFormat formatter = new DecimalFormat("#,###,##0.###");%>
<%NumberFormat formatter2 = new DecimalFormat("#,###,###.##"); %>
<%
	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	IThuongdauthung objbean=(Thuongdauthung)session.getAttribute("obj");
 	
 	ResultSet nspRS = objbean.getNspRs();
 	ResultSet nspttRS = objbean.getNspttRs();
 	ResultSet nkhRS = objbean.getNkhRs();
 	List<ISanpham> spList =objbean.getSpList();
 	Utility util = (Utility) session.getAttribute("util");
%>

<% String nnId = (String)session.getAttribute("nnId"); %> 
<% if(nnId == null) {
 nnId = "vi"; 
 }
String url = util.getChuyenNguUrl("ThuongdauthungSvl","",nnId);	
 %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="java.sql.SQLException"%>
<HTML>
<HEAD>
<TITLE>OPV - Project</TITLE>

<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">
	<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
	<LINK rel="stylesheet" href="../css/main.css" type="text/css">
	<script type="text/javascript"	src="../scripts/jquery.min.1.7.js"></script>
	<script type="text/javascript" language="JavaScript" src="../scripts/jquery.tools.min.js"></script>
	
	<script type="text/javascript" src="../scripts/ajax.js"></script>
	<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs.css">
	<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs-panes.css">
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
	
	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
	<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
	<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
	<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
    
  	<script type="text/javascript" src="../scripts/Timepicker/jquery.min.js"></script>
  	<script type="text/javascript" src="../scripts/Timepicker/jquery-ui.min.js"></script> 
	
	
	<!-- <script type="text/javascript" src="..scripts/jquery-1.js"></script> -->
		<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
    <LINK rel="stylesheet" type="text/css" href="../css/style.css" />

<script type="text/javascript" src="../scripts/ajax.js"></script>
<script type="text/javascript" src="../scripts/AjaxLaySPThuongdauthu.js"></script>
<script>
	$(function() {
	 	$("ul.tabs").tabs("div.panes > div");
	});
	</script>


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
			z-index:100000000000;
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
			z-index:5000000000000;
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
 <link media="screen" rel="stylesheet" href="../css/colorbox.css">
    <script src="../scripts/colorBox/jquery.colorbox.js"></script>
<style type="text/css">
#mainContainer {
	width: 660px;
	margin: 0 auto;
	text-align: left;
	height: 100%;
	border-left: 3px double #000;
	border-right: 3px double #000;
}

#formContent {
	padding: 5px;
}
/* END CSS ONLY NEEDED IN DEMO */

/* Big box with list of options */
#ajax_listOfOptions {
	position: absolute; /* Never change this one */
	width: auto; /* Width of box */
	height: 200px; /* Height of box */
	overflow: auto; /* Scrolling features */
	border: 1px solid #317082; /* Dark green border */
	background-color: #C5E8CD; /* White background color */
	color: black;
	text-align: left;
	font-size: 1.0em;
	z-index: 100;
}

#ajax_listOfOptions div {
	/* General rule for both .optionDiv and .optionDivSelected */
	margin: 1px;
	padding: 1px;
	cursor: pointer;
	font-size: 1.0em;
}

#ajax_listOfOptions .optionDiv { /* Div for each item in list */
	
}

#ajax_listOfOptions .optionDivSelected { /* Selected item in the list */
	background-color: #317082; /*mau khi di chuyen */
	color: #FFF;
}

#ajax_listOfOptions_iframe {
	background-color: #F00;
	position: absolute;
	z-index: 5;
}

form {
	display: inline;
}

#dhtmltooltip {
	position: absolute;
	left: -300px;
	width: 150px;
	border: 1px solid black;
	padding: 2px;
	background-color: lightyellow;
	visibility: hidden;
	z-index: 100;
	/*Remove below line to remove shadow. Below line should always appear last within this CSS*/
	filter: progid:DXImageTransform.Microsoft.Shadow(color=gray, direction=135
		);
}

#dhtmlpointer {
	position: absolute;
	left: -300px;
	z-index: 101;
	visibility: hidden;
}
}
</style>



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
  <script>
        $(document).ready(function()
        {
            <% for(int i = 0; i < 5; i++)
        	{ %>
        	
        		$(".mucasm").colorbox({width:"500px", inline:true, href:"#mucasm",top:"20px"});
                //Example of preserving a JavaScript event for inline calls.
                $("#click").click(function(){ 
                    $('#click').css({"background-color":"#f00", "color":"#fff", "cursor":"inherit"}).text("DuongBienHoa - Project.");
                    return false;
                });
                
                
                $(".mucsr").colorbox({width:"500px", inline:true, href:"#mucsr",top:"20px"});
                //Example of preserving a JavaScript event for inline calls.
                $("#click").click(function(){ 
                    $('#click').css({"background-color":"#f00", "color":"#fff", "cursor":"inherit"}).text("DuongBienHoa - Project.");
                    return false;
                });
                
                
                $(".mucss").colorbox({width:"500px", inline:true, href:"#mucss",top:"20px"});
                //Example of preserving a JavaScript event for inline calls.
                $("#click").click(function(){ 
                    $('#click').css({"background-color":"#f00", "color":"#fff", "cursor":"inherit"}).text("DuongBienHoa - Project.");
                    return false;
                });
               
        	<% } %>
        });
    </script>
<style type="text/css">

/* .chitieutable caption, tbody, tfoot, thead, tr, th, td {
	margin: 0;
	padding: 0;
	border: none;
	outline: 0;
	font-size: 100%;
	vertical-align: baseline;
	background: transparent;
}  */

table.chitieutable {border-spacing: 0; } /* IMPORTANT, I REMOVED border-collapse: collapse; FROM THIS LINE IN ORDER TO MAKE THE OUTER BORDER RADIUS WORK */

/*------------------------------------------------------------------ */

.first {
	padding : 5px;
}

.first td {
	padding : 5px;
}

/*
Table Style - This is what you want
------------------------------------------------------------------ */
table.chitieutable a:link {
	color: #666;
	font-weight: bold;
	text-decoration:none;
}
table.chitieutable a:visited {
	color: #999999;
	font-weight:bold;
	text-decoration:none;
}
table.chitieutable a:active,
table.chitieutable a:hover {
	color: #bd5a35;
	text-decoration:underline;
}
table.chitieutable {
	font-family:Arial, Helvetica, sans-serif;
	/* color:#666; */
	font-size:12px;
	text-shadow: 1px 1px 0px #fff;
	background:#eaebec;
	margin:20px;
	border:#ccc 1px solid;

	-moz-border-radius:3px;
	-webkit-border-radius:3px;
	border-radius:3px;

	-moz-box-shadow: 0 1px 2px #d1d1d1;
	-webkit-box-shadow: 0 1px 2px #d1d1d1;
	box-shadow: 0 1px 2px #d1d1d1;
}
table.chitieutable th {
	padding:21px 25px 22px 25px;
	border-top:1px solid #fafafa;
	border-bottom:1px solid #e0e0e0;

	/* background: #ededed; */
	/* background: #E1EEFF; */ 
	background:#C5E8CD;
	
	/* background: -webkit-gradient(linear, left top, left bottom, from(#ededed), to(#ebebeb));
	background: -moz-linear-gradient(top,  #ededed,  #ebebeb); */
}
table.chitieutable th:first-child{
	text-align: left;
	padding-left:20px;
}
table.chitieutable tr:first-child th:first-child{
	-moz-border-radius-topleft:3px;
	-webkit-border-top-left-radius:3px;
	border-top-left-radius:3px;
}
table.chitieutable tr:first-child th:last-child{
	-moz-border-radius-topright:3px;
	-webkit-border-top-right-radius:3px;
	border-top-right-radius:3px;
}
table.chitieutable tr{
	text-align: center;
	padding-left:20px;
}
table.chitieutable tr td:first-child{
	text-align: left;
	padding-left:20px;
	border-left: 0;
}
table.chitieutable tr td {
	padding:10px;
	border-top: 1px solid #ffffff;
	border-bottom:1px solid #e0e0e0;
	border-left: 1px solid #e0e0e0;
	
	background: #fafafa;
	background: -webkit-gradient(linear, left top, left bottom, from(#fbfbfb), to(#fafafa));
	background: -moz-linear-gradient(top,  #fbfbfb,  #fafafa);
}
table.chitieutable tr.even td{
	background: #f6f6f6;
	background: -webkit-gradient(linear, left top, left bottom, from(#f8f8f8), to(#f6f6f6));
	background: -moz-linear-gradient(top,  #f8f8f8,  #f6f6f6);
}
table.chitieutable tr:last-child td{
	border-bottom:0;
}
table.chitieutable tr:last-child td:first-child{
	-moz-border-radius-bottomleft:3px;
	-webkit-border-bottom-left-radius:3px;
	border-bottom-left-radius:3px;
}
table.chitieutable tr:last-child td:last-child{
	-moz-border-radius-bottomright:3px;
	-webkit-border-bottom-right-radius:3px;
	border-bottom-right-radius:3px;
}
table.chitieutable tr:hover td{
	background: #f2f2f2;
	background: -webkit-gradient(linear, left top, left bottom, from(#f2f2f2), to(#f0f0f0));
	background: -moz-linear-gradient(top,  #f2f2f2,  #f0f0f0);	
}


.btn {
  display: inline-block;
  padding: 6px 12px;
  margin-bottom: 0;
  font-size: 14px;
  font-weight: normal;
  line-height: 1.42857143;
  text-align: center;
  white-space: nowrap;
  vertical-align: middle;
  -ms-touch-action: manipulation;
      touch-action: manipulation;
  cursor: pointer;
  -webkit-user-select: none;
     -moz-user-select: none;
      -ms-user-select: none;
          user-select: none;
  background-image: none;
  border: 1px solid transparent;
  border-radius: 4px;
}
.btn:focus,
.btn:active:focus,
.btn.active:focus,
.btn.focus,
.btn:active.focus,
.btn.active.focus {
  outline: thin dotted;
  outline: 5px auto -webkit-focus-ring-color;
  outline-offset: -2px;
}
.btn:hover,
.btn:focus,
.btn.focus {
  color: #333;
  text-decoration: none;
}
.btn:active,
.btn.active {
  background-image: none;
  outline: 0;
  -webkit-box-shadow: inset 0 3px 5px rgba(0, 0, 0, .125);
          box-shadow: inset 0 3px 5px rgba(0, 0, 0, .125);
}
.btn.disabled,
.btn[disabled],
fieldset[disabled] .btn {
  pointer-events: none;
  cursor: not-allowed;
  filter: alpha(opacity=65);
  -webkit-box-shadow: none;
          box-shadow: none;
  opacity: .65;
}
.btn-default {
  color: #333;
  background-color: #fff;
  border-color: #ccc;
}
.btn-default:hover,
.btn-default:focus,
.btn-default.focus,
.btn-default:active,
.btn-default.active,
.open > .dropdown-toggle.btn-default {
  color: #333;
  background-color: #e6e6e6;
  border-color: #adadad;
}
.btn-default:active,
.btn-default.active,
.open > .dropdown-toggle.btn-default {
  background-image: none;
}
.btn-default.disabled,
.btn-default[disabled],
fieldset[disabled] .btn-default,
.btn-default.disabled:hover,
.btn-default[disabled]:hover,
fieldset[disabled] .btn-default:hover,
.btn-default.disabled:focus,
.btn-default[disabled]:focus,
fieldset[disabled] .btn-default:focus,
.btn-default.disabled.focus,
.btn-default[disabled].focus,
fieldset[disabled] .btn-default.focus,
.btn-default.disabled:active,
.btn-default[disabled]:active,
fieldset[disabled] .btn-default:active,
.btn-default.disabled.active,
.btn-default[disabled].active,
fieldset[disabled] .btn-default.active {
  background-color: #fff;
  border-color: #ccc;
}
.btn-default .badge {
  color: #fff;
  background-color: #333;
}
</style>
<SCRIPT language="JavaScript" type="text/javascript"> 
function submitform()
{
	<%if(!objbean.getDisplay().equals("1")){%>
    	document.forms["ChiTieuTTForm"].submit();
    <%}%>
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
	element.value = DinhDangTien(element.value);
}


function save(){
 
 
		 document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Processing...</a>";
		 
		 document.forms["ChiTieuTTForm"].action.value = "save";
		 document.forms["ChiTieuTTForm"].submit();


}

</SCRIPT>

<script language="javascript" type="text/javascript">

function replaces()
{	
	var spId = document.getElementsByName("spId");
	var masp = document.getElementsByName("spMa");
	var tensp = document.getElementsByName("spTen");
	
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
				sp = sp.substr(parseInt(sp.indexOf(" - ")) + 3);
				tensp.item(i).value = sp.substring(0, parseInt(sp.indexOf("[")));
							
				
				sp = sp.substr(parseInt(sp.indexOf(" [")) + 2); //console.log(sp);
			    spId.item(i).value= sp.substring(0, parseInt(sp.indexOf("]")));
			    
			    
			}			
		}
		else
		{
			tensp.item(i).value = "";
			spId.item(i).value = "";	
		}
	}	
	setTimeout(replaces, 20);
}

function addRow(pos)
{
	var table = $('#tbSanPham');
	table.append(
            '<tr>'+
			'<td>'+
			'<input type="hidden" name="spId"  value=""/>'+ 		                            			
			'<input type="text" name="spMa" style="width: 100%"  value=""   onkeyup="ajax_showOptions(this,\'donmuahang\',event)" AUTOCOMPLETE="off"  />   </td>'+
			'<td><input type="text" name="spTen"  style="width: 100%" value=""/>   </td>'+
		'</TR>');
}



function ThemSanPham(pos)
{
	 var sl = window.prompt("Nhấp số lượng sản phẩm muốn thêm", '');
	 if(isNaN(sl) == false && sl < 100)
	 {
		 for(var i=0; i < sl ; i++)
			 addRow(pos);
	 }
	 else
	 {
		 alert('Số lượng bạn nhập không hợp lệ. Mọi lần bạn chỉ được thêm tối đa 30 sản phẩm');
		 return;
	 }
 } 
 
function xuatExcel()
{
	document.forms['ChiTieuTTForm'].action.value= 'excel';
	document.forms['ChiTieuTTForm'].submit();
	
}
 
</SCRIPT>
<script type="text/javascript" src="../scripts/ajax.js"></script>

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
<input type="hidden"  name="userId" value='<%=userId%>'>
<input type="hidden" name="userTen" value='<%=userTen%>'>
<input type="hidden" name="nkmId" value="">
<input type="hidden" name="action" value="0">
<input type="hidden" name="id" value='<%=objbean.getID()%>'>
<input type="hidden" name="tenform" value="0">
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							 <TD align="left" colspan="2" class="tbnavigation">
							 <%
							 if(!objbean.getDisplay().equals("1")){
								 if(objbean.getID()>0){%>
									 <%=" "+url %> &gt; Cập nhật
								 <%}
								 else{%>
									 <%=" "+url %> &gt; Tạo mới
								 <%}
							 }
							 else{%>
								 <%=" "+url %> &gt; Hiển thị
							 <%}
							 %>
							 </TD> 
							 <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen%>&nbsp;  </TD></tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
			<TR ><TD >
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR class = "tbdarkrow">
							<TD width="30" align="left"><A href="../../ThuongdauthungSvl?userId=<%=userId%>" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
						    <TD width="2" align="left" ></TD>
						    <%if(!objbean.getDisplay().equals("1")){ %>
							    <TD width="30" align="left" >						    
								<div id="btnSave">
							    <A href="javascript: save()" ><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A>
							    </div>
								</TD>
							<%} %>
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
	    				<textarea name="dataerror"  style="width: 100%" readonly="readonly" rows="1"><%=objbean.getMessage()%></textarea>
						</FIELDSET>
				   </TD>
				</tr>			

				<TR>
				  <TD height="100%" width="100%">
						<FIELDSET >
						<LEGEND class="legendtitle" style="color:black">Thông tin thưởng đầu thùng</LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
							
							
							
							
									
							<TR>
	                        <TD class="plainlabel" ><%=ChuyenNgu.get("Từ ngày",nnId) %> </TD>
			                        <TD class="plainlabel" >
			                            <input readonly type="text" size="10" class="days" 
			                                   id="tungay" name="tungay" value="<%= objbean.getTungay() %>" maxlength="10" />
			                        </TD>
	                       	 <TD class="plainlabel" ><%=ChuyenNgu.get("Đến ngày",nnId) %></TD>
		                        <TD class="plainlabel"  >
		                             <input  readonly type="text" size="10" class="days" 
		                                    id="denngay" name="denngay" value="<%= objbean.getDenngay() %>" maxlength="10" />
		                        </TD>
		                        <TD class="plainlabel">  </TD>
                                <TD colspan = "2" class="plainlabel"></TD>
                   			 </TR>
							
				  		  	<TR>
	                        	
	                        	<TD class="plainlabel"><%=ChuyenNgu.get("Loại Chương trình",nnId) %> </TD>
									<TD class="plainlabel" colspan="5">
										<select name="loaict"  >
											<%if(objbean.getLoaict().equals("1")){%>	
														  	 
							  	 			<option   value="0" ><%=ChuyenNgu.get("Thùng",nnId) %> 	 				
							  	 			<option   value="1" selected><%=ChuyenNgu.get("Lẻ",nnId) %>
							  	 			<option   value="2" ><%=ChuyenNgu.get("Số Khách hàng",nnId) %>						  	 											  	 				
									  	 	<%}else if(objbean.getLoaict().equals("2")) {%>
									  	 	<option   value="0" > <%=ChuyenNgu.get("Thùng",nnId) %> 				
							  	 			<option   value="1" ><%=ChuyenNgu.get("Lẻ",nnId) %>
							  	 			<option   value="2" selected><%=ChuyenNgu.get("Số Khách hàng",nnId) %>								  	 		 		
											<%}else {%>
									  	 		<option   value="0" ><%=ChuyenNgu.get("Thùng",nnId) %> 	 				
							  	 			<option   value="1" ><%=ChuyenNgu.get("Lẻ",nnId) %>
							  	 			<option   value="2" ><%=ChuyenNgu.get("Số Khách hàng",nnId) %>
								  	 		<%}%>					
									    </select>
									</TD>
	                        	
                   			 </TR>
				  		  
						  	<TR>
						  	  <TD class="plainlabel"><%=ChuyenNgu.get("Diễn giải",nnId) %></TD>
						  	  <TD class="plainlabel"  >
						  	  		<input  type="text"  name="diengiai" value="<%=objbean.getDienGiai()%>" > 
						  	  	</TD>
						  	  	
						  	  	<TD class="plainlabel" style="font-weight: bold;"><%=ChuyenNgu.get("Nhóm sản phẩm",nnId) %></TD>
					  	  				<TD class="plainlabel">
					  	  					<SELECT  name= "nsp_fk" onchange="submitform();">
					  	  					<option value="" ></option>
					  	  						<%
					  	  						if(nspRS!=null){ 
													
						  	  						while (nspRS.next())
						  	  						{
						  	  							if(nspRS.getString("pk_seq").trim().equals(objbean.getNsp_fk().trim()))
						  	  							{
						  	  							System.out.println("so sanh MATH= " + objbean.getNsp_fk() +" vs " + nspRS.getString("pk_seq"));
						  	  								%>
						  	  							<OPTION selected="selected" value ="<%=nspRS.getString("pk_Seq") %> " ><%=nspRS.getString("ten")%> </OPTION>
						  	  								<% 
						  	  							}else{
						  	  							%>
					  	  									<OPTION value ="<%=nspRS.getString("pk_Seq") %>" ><%=nspRS.getString("ten")%> </OPTION>
					  	  								<% 
						  	  							}
						  	  						}
					  	  						}
					  	  						%>							
					  	  					</SELECT>
										</TD>
										<TD class="plainlabel" width="140px" >  </TD>
                                	<TD colspan = "2" class="plainlabel"></TD>
						  	</TR>		
						  		<TR>
						  	 
						  	  	<TD class="plainlabel" style="font-weight: bold;"><%=ChuyenNgu.get("Nhóm khách hàng",nnId) %></TD>
					  	  				<TD class="plainlabel">
					  	  					<SELECT  name= "nkh_fk" onchange="submitform();">
					  	  					<option value="" ></option>
					  	  						<%
					  	  						if(nkhRS!=null){ 
						  	  						while (nkhRS.next())
						  	  						{
						  	  							if(nkhRS.getString("pk_seq").trim().equals(objbean.getNkh_fk().trim()))
						  	  							{
						  	  							System.out.println("so sanh MATH= " + objbean.getNkh_fk() +" vs " + nkhRS.getString("pk_seq"));
						  	  								%>
						  	  							<OPTION selected="selected" value ="<%=nkhRS.getString("pk_Seq") %> " ><%=nkhRS.getString("ten")%> </OPTION>
						  	  								<% 
						  	  							}else{
						  	  							%>
					  	  									<OPTION value ="<%=nkhRS.getString("pk_Seq") %>" ><%=nkhRS.getString("ten")%> </OPTION>
					  	  								<% 
						  	  							}
						  	  						}
					  	  						}
					  	  						%>							
					  	  					</SELECT>
										</TD>
										<TD class="plainlabel" width="140px" >  </TD>
                                	<TD colspan = "3" class="plainlabel"></TD>
						  	</TR>					  	
							<TR>
								<TD   class="plainlabel"><%=ChuyenNgu.get("Thưởng Quản lý khu vực",nnId) %></TD>
								<%-- <TD  class="plainlabel" >
									<input value="<%= formatter.format(objbean.getThuongASM())%>" style="text-align: right;" name="thuongASM"/>
								</TD> --%>
								
							
									<td   class="plainlabel" colspan="5">
														<a class="mucasm" href="#">
						                        			<img style="top: -4px;" src="../images/vitriluu.png" title="Mức thưởng"></a>
										                  <div style='display:none'>
									                        <div id='mucasm' style='padding:0px 5px; background:#fff;height:100%'>
									                        	<h4 align="left">Mức thưởng quản lý khu vực</h4>
																<table cellpadding="4px" cellspacing="2px" width="100%" align="center">
									                                <tr>
									                                	<td colspan="2">
									                                		<table align="left" cellpadding="0" cellspacing="1">
										                                		<tr class="tbheader">
										                                			<td width="150px" align="center" ><span style="font-size: 0.9em">Diễn giải</span></td>
										                                			<td width="100px" align="center" ><span style="font-size: 0.9em">Số khách hàng</span></td>       			
										                                			<td width="100px" align="center" ><span style="font-size: 0.9em">Thưởng</span></td>
										                                		</tr>
										                                		<%
										                                			int count2 = 0;
										                                			if(objbean.getinfoDETAILASM().trim().length() > 0)
										                                			{ 
										                                				String[] info = objbean.getinfoDETAILASM().split("__");
										                                				for(int k = 0; k < info.length; k++ )
										                                				{
										                                					//System.out.println("INFO ::: " + info[k]);
										                                				%>
										                                					
										                                					<tr>
													                                			<td>
													                                				<input type="text" value="Mức <%= count2 + 1 %>" name="muc.tiendoasm" style="width: 100%;" readonly="readonly"  >
													                                			</td>
													                                			<td>
													                                				<input type="text" value="<%= info[k].split("_")[0] %>" name="muc.tiendosokhasm" style="width: 100%;text-align: right;" onkeypress="return keypress(event);" >
													                                			</td>
													                                			<td>
													                                				<input type="text" value="<%= info[k].split("_")[1] %>" name="muc.tiendothuongasm" style="width: 100%;text-align: right;" onkeypress="return keypress(event);" >
													                                			</td>
													                                			
													                                		</tr>
										                                					
										                                				<% 	count2++; } } %>
										                                				
										                                		<% for(int j = count2; j < 5; j++ ) { %>
										                                		<tr>
										                                			<td>
										                                				<input type="text" value="Mức <%= j + 1 %>" name="muc.tiendoasm" style="width: 100%;" readonly="readonly"  >
										                                			</td>
										                                			<td>
										                                				<input type="text" value="" name="muc.tiendosokhasm" style="width: 100%; text-align: right;" onkeypress="return keypress(event);" >
										                                			</td>
										                                			<td>
										                                				<input type="text" value="" name="muc.tiendothuongasm" style="width: 100%; text-align: right;" onkeypress="return keypress(event);">
										                                			</td>
										                                		</tr>
										                                		<% } %>
										                                	</table>
										                                	
									                                	</td>
									                                </tr>
									                                <tr>
									                                	<td valign="top" align="left" colspan="2">
									        								<a class="button" href="javascript:DongLai();">
									        								<img style="top: -4px;" src="../images/button.png" alt=""> Đóng lại  </a>
									                                	</td>
									                                </tr>
									                            </table>
															</div>
										                </div>
													</td>
								
							</TR>
								
							<TR>
								<TD   class="plainlabel"><%=ChuyenNgu.get("Thưởng SR",nnId) %></TD>
								<%-- <TD  class="plainlabel">
									<input value="<%= formatter.format(objbean.getThuongSR())%>" style="text-align: right;" name="thuongSR" />
								</TD> --%>
								
								<td   class="plainlabel" colspan="5">
														<a class="mucsr" href="#">
						                        			<img style="top: -4px;" src="../images/vitriluu.png" title="Mức thưởng SR"></a>
										                  <div style='display:none'>
									                        <div id='mucsr' style='padding:0px 5px; background:#fff;'>
									                        	<h4 align="left">Mức thưởng</h4>
																<table cellpadding="4px" cellspacing="2px" width="100%" align="center">
									                                <tr>
									                                	<td colspan="2">
									                                		<table align="left" cellpadding="0" cellspacing="1">
										                                		<tr class="tbheader">
										                                			<td width="150px" align="center" ><span style="font-size: 0.9em">Diễn giải</span></td>
										                                			<td width="100px" align="center" ><span style="font-size: 0.9em">Số khách hàng</span></td>       			
										                                			<td width="100px" align="center" ><span style="font-size: 0.9em">Thưởng</span></td>
										                                		</tr>
										                                		<%
										                                			count2 = 0;
										                                			if(objbean.getinfoDETAILSR().trim().length() > 0)
										                                			{ 
										                                				String[] info = objbean.getinfoDETAILSR().split("__");
										                                				for(int k = 0; k < info.length; k++ )
										                                				{
										                                					//System.out.println("INFO ::: " + info[k]);
										                                				%>
										                                					
										                                					<tr>
													                                			<td>
													                                				<input type="text" value="Mức <%= count2 + 1 %>" name="muc.tiendosr" style="width: 100%;" readonly="readonly"  >
													                                			</td>
													                                			<td>
													                                				<input type="text" value="<%= info[k].split("_")[0] %>" name="muc.tiendosokhsr" style="width: 100%;text-align: right;" onkeypress="return keypress(event);" >
													                                			</td>
													                                			<td>
													                                				<input type="text" value="<%= info[k].split("_")[1] %>" name="muc.tiendothuongsr" style="width: 100%;text-align: right;" onkeypress="return keypress(event);" >
													                                			</td>
													                                			
													                                		</tr>
										                                					
										                                				<% 	count2++; } } %>
										                                				
										                                		<% for(int j = count2; j < 5; j++ ) { %>
										                                		<tr>
										                                			<td>
										                                				<input type="text" value="Mức <%= j + 1 %>" name="muc.tiendosr" style="width: 100%;" readonly="readonly"  >
										                                			</td>
										                                			<td>
										                                				<input type="text" value="" name="muc.tiendosokhsr" style="width: 100%; text-align: right;" onkeypress="return keypress(event);" >
										                                			</td>
										                                			<td>
										                                				<input type="text" value="" name="muc.tiendothuongsr" style="width: 100%; text-align: right;" onkeypress="return keypress(event);">
										                                			</td>
										                                		</tr>
										                                		<% } %>
										                                	</table>
										                                	
									                                	</td>
									                                </tr>
									                                <tr>
									                                	<td valign="top" align="left" colspan="2">
									        								<a class="button" href="javascript:DongLai();">
									        								<img style="top: -4px;" src="../images/button.png" alt=""> Đóng lại  </a>
									                                	</td>
									                                </tr>
									                            </table>
															</div>
										                </div>
													</td>
								</TR>
								
							<TR>
								<TD   class="plainlabel"><%=ChuyenNgu.get("Thưởng SS",nnId) %></TD>
								<%-- <TD  class="plainlabel" >
									<input value="<%= formatter.format(objbean.getThuongSS())%>" style="text-align: right;" name="thuongSS" />
								</TD> --%>
								<td   class="plainlabel" colspan="5">
														<a class="mucss" href="#">
						                        			<img style="top: -4px;" src="../images/vitriluu.png" title="Mức thưởng"></a>
										                  <div style='display:none'>
									                        <div id='mucss' style='padding:0px 5px; background:#fff;'>
									                        	<h4 align="left">Mức thưởng SS</h4>
																<table cellpadding="4px" cellspacing="2px" width="100%" align="center">
									                                <tr>
									                                	<td colspan="2">
									                                		<table align="left" cellpadding="0" cellspacing="1">
										                                		<tr class="tbheader">
										                                			<td width="150px" align="center" ><span style="font-size: 0.9em">Diễn giải</span></td>
										                                			<td width="100px" align="center" ><span style="font-size: 0.9em">Số khách hàng</span></td>       			
										                                			<td width="100px" align="center" ><span style="font-size: 0.9em">Thưởng</span></td>
										                                		</tr>
										                                		<%
										                                			count2 = 0;
										                                			if(objbean.getinfoDETAILSS().trim().length() > 0)
										                                			{ 
										                                				String[] info = objbean.getinfoDETAILSS().split("__");
										                                				for(int k = 0; k < info.length; k++ )
										                                				{
										                                					//System.out.println("INFO ::: " + info[k]);
										                                				%>
										                                					
										                                					<tr>
													                                			<td>
													                                				<input type="text" value="Mức <%= count2 + 1 %>" name="muc.tiendoss" style="width: 100%;" readonly="readonly"  >
													                                			</td>
													                                			<td>
													                                				<input type="text" value="<%= info[k].split("_")[0] %>" name="muc.tiendosokhss" style="width: 100%;text-align: right;" onkeypress="return keypress(event);" >
													                                			</td>
													                                			<td>
													                                				<input type="text" value="<%= info[k].split("_")[1] %>" name="muc.tiendothuongss" style="width: 100%;text-align: right;" onkeypress="return keypress(event);" >
													                                			</td>
													                                			
													                                		</tr>
										                                					
										                                				<% 	count2++; } } %>
										                                				
										                                		<% for(int j = count2; j < 5; j++ ) { %>
										                                		<tr>
										                                			<td>
										                                				<input type="text" value="Mức <%= j + 1 %>" name="muc.tiendoss" style="width: 100%;" readonly="readonly"  >
										                                			</td>
										                                			<td>
										                                				<input type="text" value="" name="muc.tiendosokhss" style="width: 100%; text-align: right;" onkeypress="return keypress(event);" >
										                                			</td>
										                                			<td>
										                                				<input type="text" value="" name="muc.tiendothuongss" style="width: 100%; text-align: right;" onkeypress="return keypress(event);">
										                                			</td>
										                                		</tr>
										                                		<% } %>
										                                	</table>
										                                	
									                                	</td>
									                                </tr>
									                                <tr>
									                                	<td valign="top" align="left" colspan="2">
									        								<a class="button" href="javascript:DongLai();">
									        								<img style="top: -4px;" src="../images/button.png" alt=""> Đóng lại  </a>
									                                	</td>
									                                </tr>
									                            </table>
															</div>
										                </div>
													</td>
							</TR>	
							
							<TR>
						<TD class="plainlabel" colspan="6">
								<!-- <a class="button2" href="javascript:search();"> 
									<img style="top: -4px;" src="../images/button.png" alt=""> Tìm kiếm</a>&nbsp;&nbsp;&nbsp;&nbsp; -->
							<a class="button2" href="javascript:xuatExcel();"> 
								<img style="top: -4px;" src="../images/button.png" alt=""><%=ChuyenNgu.get("Xuất dữ liệu",nnId)%> </a>&nbsp;&nbsp;&nbsp;&nbsp;
					</TR> 
													  	
						</TABLE>
						<TABLE class = "chitieutable">
												
										<TR>
											
											
											  <TD style="font-weight: bold;"><%=ChuyenNgu.get("DS tối thiểu ĐH",nnId)%> </TD>
											 <TD>
												<input onblur="if(this.value=='')this.value='0'" style="width:100%; border:none; background: transparent;" name = "dstoithieu" value = "<%= formatter2.format(objbean.getdstoithieu()) %>" size = 30 ></input>
											 </TD>
											
										  	<%--  <TD style="font-weight: bold;">  Nhóm sản phẩm</TD>
						  	  				 <TD>
						  	  					<SELECT style="width:400px;" name= "nsptt_fk"  >
						  	  					<option value="" ></option>
						  	  						<%if(nspttRS!=null){ 
							  	  						while (nspttRS.next()){
							  	  							if(nspttRS.getString("pk_seq").trim().equals(objbean.getNsptt_fk().trim())){
							  	  								%>
							  	  							<OPTION selected="selected" value ="<%=nspttRS.getString("pk_Seq") %> " ><%=nspttRS.getString("ten")%> </OPTION>
							  	  								<% 
							  	  							}else{
							  	  							%>
						  	  									<OPTION value ="<%=nspttRS.getString("pk_Seq") %>" ><%=nspttRS.getString("ten")%> </OPTION>
						  	  								<% 
							  	  							}
							  	  						}
						  	  						}%>							
	
						  	  					</SELECT>
						  	  				</TD> --%>

											</TR>
											  
									 
								 
									
							
						
						</TABLE>
						<TABLE width="100%" border="0" cellspacing="1" cellpadding="4"> 
						<tbody  id="tbSanPham">							
								<TR class="tbheader">
									<TH width="28%"><%=ChuyenNgu.get("Mã sản phẩm",nnId)%> </TH>
									<TH width="60%"><%=ChuyenNgu.get("Tên sản phẩm",nnId)%> </TH>
								</TR>
						<%
                            	int sodong=spList.size();
                            	int n=0;
                           			while(n<sodong)
                           			{
                           				ISanpham sp =spList.get(n);
                           				n++;
                           		%>
                           		<tr>
                           			<td>
                           			<input type="hidden" name="spId"  value="<%=sp.getId()%>"    />
                           			<input type="text" name="spMa"  style="width: 100%"  value="<%=sp.getMa()%>"  onkeyup="ajax_showOptions(this,'donmuahang',event)" AUTOCOMPLETE="off" />   </td>
									<td><input type="text" name="spTen"  style="width: 100%" value="<%=sp.getTen()%>"/>   </td>
                           		</tr>
                            		<% }
                            			
                            		while(sodong < 40)
                            		{
                            		%>
                            		<tr>
                            			<td>
										<input type="hidden" name="spId"  value=""/> 		                            			
                            			<input type="text" name="spMa" style="width: 100%"  value=""   onkeyup="ajax_showOptions(this,'donmuahang',event)" AUTOCOMPLETE="off"  />   </td>
										<td><input type="text" name="spTen"  style="width: 100%" value=""/>   </td>
                            		</tr>
                            		<% sodong++; }
                            	%>	
                            	</tbody>
                            	<TR>
                            		<TD>
                            			<a class="button2" href="javascript:ThemSanPham()"> <img style="top: -4px;" src="../images/add.png" alt="">Thêm sản phẩm</a>&nbsp;&nbsp;&nbsp;&nbsp;
                            		</TD>
                            	</TR>							   
						</TABLE>
						
                             							
						</FIELDSET>						
					</TD>
				</TR>
			</TABLE>
		</TD>
	</TR>
	</TABLE>
<!-- </form> -->
	<script type="text/javascript">
		replaces();
		</script>
</body>  
<!-- <script type='text/javascript' src='../scripts/loadingv2.js'></script> -->
</HTML>