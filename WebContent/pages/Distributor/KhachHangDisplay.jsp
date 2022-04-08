<%@page import="java.util.List"%>
<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.distributor.beans.khachhang.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>

<%@ page  import = "geso.dms.center.util.*" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>

<% IKhachhang khBean = (IKhachhang)session.getAttribute("khBean"); %>
<% ResultSet tp = (ResultSet)khBean.getTp() ;  %>
<% ResultSet qh = (ResultSet)khBean.getQh() ;  %>
<% ResultSet hch = (ResultSet)khBean.getHangcuahang(); %>
<% ResultSet kbh = (ResultSet)khBean.getKenhbanhang();  %>
<% ResultSet bgst = (ResultSet)khBean.getBgsieuthi();  %>
<% ResultSet vtch = (ResultSet)khBean.getVtcuahang();  %>
<% ResultSet lch = (ResultSet)khBean.getLoaicuahang(); %>
<% ResultSet nch = (ResultSet)khBean.getNhomcuahang();  %>
<% ResultSet mck = (ResultSet)khBean.getMucchietkhau();  %>
<% ResultSet ghcn = (ResultSet)khBean.getGhcongno();  %>
<% ResultSet nvgn = (ResultSet)khBean.getNvgnRs();  %>
<% ResultSet tansoRs = (ResultSet)khBean.getTansoRs();  %>

<% ResultSet rsbanggiasieuthi = (ResultSet)khBean.getBangGiaST();  %>
<% ResultSet nkh_khList = (ResultSet)khBean.getNkh_khList();  %>
<% ResultSet nkh_khSelected = (ResultSet)khBean.getNkh_KhSelected();  %>
<% ResultSet htkdRs = (ResultSet)khBean.getHtkdRs();  
String view = khBean.getView();
%>

<%  
	List<IErpKhachHang_SPCK> spCKlist = (List<IErpKhachHang_SPCK>)khBean.getListSanPhamCK(); 
	List<IErpKhachHang_ChungLoaiSP> chungloaiSP = (List<IErpKhachHang_ChungLoaiSP>)khBean.getListChungLoaiCK();%>
<% Hashtable<Integer, String> nkh_khIds = (Hashtable<Integer, String>)khBean.getNkh_KhIds(); %>

<% ResultSet pxRs = (ResultSet)khBean.getPhuongxaRs();  %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">

<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<script type="text/javascript" src="../scripts/ajax.js"></script>
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs.css">
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs-panes.css">

<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">

<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<link media="screen" rel="stylesheet" href="../css/colorbox.css">
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

<SCRIPT language="javascript" type="text/javascript">
function replaces()
{
	//SP CHIET KHAU
	var idspCK = document.getElementsByName("idspCK");
	var maspCK = document.getElementsByName("maspCK");
	var tenspCK = document.getElementsByName("tenspCK");
	var dvtCK = document.getElementsByName("donvitinhCK");

	for(i = 0; i < maspCK.length; i++)
	{
		if(maspCK.item(i).value != "")
		{
			var sp = maspCK.item(i).value;
			
			var pos = parseInt(sp.indexOf(" - "));
	
			if(pos > 0)
			{
				
				maspCK.item(i).value = sp.substring(0, pos);
				sp = sp.substr(parseInt(sp.indexOf(" - ")) + 3);
		
				pos = parseInt(sp.indexOf(" - "));
				
				tenspCK.item(i).value = sp.substring(0, pos);
				
				sp = sp.substr(parseInt(sp.indexOf(" - ")) + 3);
		
				dvtCK.item(i).value = sp.substring(0, parseInt(sp.indexOf("] [")));
				sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
		
				idspCK.item(i).value = sp.substring(0, parseInt(sp.indexOf("]")));
				
			}
		}
		else
		{
			maspCK.item(i).value = "";
			dvtCK.item(i).value = "";
			idspCK.item(i).value = "";
			tenspCK.item(i).value = "";
		}
	}
	setTimeout(replaces, 500);
}
  
 
 function showElement(id)
 {
 	var element = document.getElementById(id);
 	element.style.display = "";
 }

 function hideElement(id)
 {
 	var element = document.getElementById(id);
 	element.style.display = "none";
 }

 function tongleBangGia(sel)
 {
	 if ( sel.options[sel.selectedIndex].value == "100002" ) //kenh hien dai 
	 {
	  	showElement("banggia");
	 } 
	 else 
	 {
	  	hideElement("banggia");
	 }
 }
 function ChonCK() {
		document.forms['khForm'].action.value = 'ChonCK';
		 
	}
 function submitForm2()
 {	
		var pxTen = document.getElementById('pxTen').value;
		var tpId = document.getElementById('tpId').value; 
		var qhId = document.getElementById('qhId').value;
		var id = document.getElementById('pxId').value;
		
		if(pxTen == '' || tpId == ''|| qhId == '')
		{
			alert('Vui lòng nhập đầy đủ thông tin !');
			return;
		}
		
		var xmlhttp;
		if (window.XMLHttpRequest)
		{
		  xmlhttp=new XMLHttpRequest();
		}
		else
		{
		  xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		
		xmlhttp.onreadystatechange=function()
		{
		   if (xmlhttp.readyState == 4 && xmlhttp.status == 200)
		   {
			  if(xmlhttp.responseText.length > 10)
			  { 
				  alert('Không thể tạo mới phường xã.'); 
			  }
			  else
			  { 
				  alert('Tạo phường xã mới thành công.'); submitform(); 
			  } 
		   }
		}

		xmlhttp.open("GET","../../AjaxPhuongXa?action=save&tpId=" + tpId + "&qhId=" + qhId + "&pxTen=" + pxTen + "&id=" + id, true); 	
		xmlhttp.send();
 }
 
</SCRIPT>
<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>
<script type="text/javascript" src="../scripts/spFilterList.js"></script>
<script type="text/javascript" src="../scripts/ajax_KH_SP_CK.js"></script>
<script type="text/javascript" src="../scripts/ajax.js"></script>
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<link media="screen" rel="stylesheet" href="../css/colorbox.css">
<script src="../scripts/colorBox/jquery.colorbox.js"></script>
<script>
    $(document).ready(function()
    {
    	$(".taomoitc").colorbox({width:"45%", inline:true, href:"#taomoitc"});
        //Example of preserving a JavaScript event for inline calls.
        $("#click").click(function(){ 
            $('#click').css({"background-color":"#f00", "color":"#fff", "cursor":"inherit"}).text("Bibica - Project.");
            return false;
        });
    });
</script>
	<script>
$(document).ready(function()
{
	$("#htkdId").select2();
	
});
jQuery(document).ready(function()
		 {
		 	$("select:not(.notuseselect2)").chosen();     
		 	
		 }); 
</SCRIPT>
<link href="../css/chosen.css" rel="stylesheet" type="text/css" />
<script src="../scripts/chosen.jquery.js" type="text/javascript"></script>

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

<form name="khForm" method="post" action="../../KhachhangUpdateSvl"> 
 <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%=userId %>'>
<input type="hidden" name="nppId" value='<%= khBean.getNppTen() %>'>
<input type="hidden" name="view" value='<%= view %>'>
<input type="hidden" name="action" value='1'>
<INPUT name="id" type="hidden" value='<%= khBean.getId() %>' size="30">
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="2">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;Thiết lập dữ liệu nền > Dữ liệu nền kinh doanh > Khách hàng > Hiển thị
	   						 <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn  <%= userTen %> &nbsp; </tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>
			<TABLE width="100%" cellpadding="0" cellspacing="2">
			<TR ><TD >
				<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
					<TR class = "tbdarkrow">
						<TD width="30" align="left"><A href="javascript:history.back()"  ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" width="30" height="30" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
					    <TD width="2" align="left" ></TD>
					    <!-- <TD width="30" align="left" >
					    <div id="btnSave">
					    <A href="javascript:saveform()" ><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A>
					    </div>
					    </TD> -->
				    	<TD align="left" >&nbsp;</TD>
					</TR>
				</TABLE>
			</TD></TR>
			</TABLE>
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
			  	<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>		
	    				<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold"  style="width:100% ; font-weight:bold ; color:#F00" rows="1" ><%= khBean.getMessage() %></textarea>
							<%khBean.setMessage(""); %>
						</FIELDSET>
				   </TD>
				</tr>			

				<TR>
				  <TD height="100%" width="100%">
						<FIELDSET><LEGEND class="legendtitle" style="color:black">Thông tin khách hàng</LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
						
						 <TR>
								<TD class="plainlabel" width="130px"  > Tên khách hàng<FONT class="erroralert"> *</FONT></TD>
								<TD class="plainlabel" width="220px" >
									<INPUT type="text" name="khTen" id="khTen" value="<%= khBean.getTen() %>" size="40"></TD>
							    <TD class="plainlabel" width="140px" >Tên người đại diện <FONT class="erroralert"> *</FONT></TD>
						  	  	<TD class="plainlabel"  ><INPUT type="text" name="nguoidaidien" id="diachi" value="<%= khBean.getNguoidaidien() %>" size="40"></TD>
						  	  	<TD width="15%" class="plainlabel" > Mã KH</TD>			
									<TD colspan = "1" class="plainlabel">
									<INPUT readonly = "readonly" type="text" name="maddt" id="maddt" value="<%= khBean.getsmartid() %>" size="40">
								</TD>		
						</TR>
						
						<TR> 
						<TD class="plainlabel">Mã KH tham chiếu<FONT class="erroralert"> *</FONT></TD>
						<TD class="plainlabel" colspan="5"><input type="text" name="mathamchieu" value="<%=khBean.getMathamchieu()%>"></TD>
						</TR>
						
						<TR>
							   	 <TD class="plainlabel">Tỉnh /Thành phố<FONT class="erroralert"> *</FONT></TD>
								 <TD class="plainlabel" width="220px" >
								 <SELECT name="tpId" id="tpId" onChange="submitform();" class="select2" style="width: 200px;" >
								    	<option value=""></option>
								      <% try{while(tp.next()){ 
								    	if(tp.getString("tpId").equals(khBean.getTpId())){ %>
								      		<option value='<%=tp.getString("tpId")%>' selected><%=tp.getString("tpTen") %></option>
								      	<%}else{ %>
								     		<option value='<%=tp.getString("tpId")%>'><%=tp.getString("tpTen") %></option>
								     	<%}}}catch(java.sql.SQLException e){} %>	  
                        				</SELECT>	</TD>

							   	 <TD class="plainlabel">Quận/Huyện <FONT class="erroralert"> *</FONT></TD>
								 <TD class="plainlabel" >
								 	<SELECT name="qhId" id="qhId" class="select2" style="width: 200px;" onChange="submitform();" >
								    <option value=""></option>
								      <%if(qh != null){ 
								      		try{while(qh.next()){ 
								    			if(qh.getString("qhId").equals(khBean.getQhId())){ %>
								      				<option value='<%=qh.getString("qhId")%>' selected><%=qh.getString("qhTen") %></option>
								      		 <%}else{ %>
								     				<option value='<%=qh.getString("qhId")%>'><%=qh.getString("qhTen") %></option>
								     		<%}}}catch(java.sql.SQLException e){} 
								     		
								      }	%>
								     		  
                        				</SELECT>	
                        		</TD>
                        		
                        		<TD class="plainlabel">Phường xã</TD>
                        		<%--<TD class="plainlabel">
                        		 <input type="text" name="phuongxa" id="phuongxa"  value="<%=khBean.getPhuongxa()%>" ></TD>  --%>
						  	  	<TD class="plainlabel"  >
						  	  		<%-- <INPUT type="text" name="phuong" id="diachi" value="<%= khBean.getPhuong() %>" size="40"> --%>
						  	  		<SELECT name="pxId" id="pxId" class="select2" style="width: 200px;">
								    <option value=""></option>
								      <%if(pxRs != null){ 
								      		try{
								      			while(pxRs.next()){ 
								    			if(pxRs.getString("pk_seq").equals(khBean.getPxId() )){ %>
								      				<option value='<%=pxRs.getString("pk_seq")%>' selected><%=pxRs.getString("ten") %></option>
								      		 <%}else{ %>
								     				<option value='<%=pxRs.getString("pk_seq")%>'><%=pxRs.getString("ten") %></option>
								     		<%}}}catch(java.sql.SQLException e){} 
								     		
								      }	%>
                        				</SELECT>
                        			<%-- 	<% if(pxRs != null){
													try
													{	pxRs.beforeFirst();
														while(pxRs.next())
														{
															if(khBean.getPhuongxa().equals(pxRs.getString("pk_seq")))
												%>
						  	  					<A href = "../../AjaxPhuongXa?userId=<%=userId%>&action=delete&pxId<%= pxRs.getString("pk_seq") %>;">
                                  				<img src="../images/Delete20.png" alt="Xoa" width="20" height="20" style="margin-bottom:-5; margin-left:7px;" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn có muốn xóa tiêu chí thưởng này?')) return false;"></A>
						  	  					
						  	  					<%		} %>
											
													<%} catch(java.sql.SQLException e){}
													}%> --%>
                        				
                        				<%-- <a class="taomoitc" href="#">
		                        			<img src="../images/New.png" width="20" height="20" style="margin-bottom:-5; margin-left:7px;" title="Tạo mới phường xã"></a>
							                <div style='display:none'>
						                        <div id="taomoitc" style='padding:0px 5px; background:#fff;'>
						                        	<h4 align="left" style="text-decoration: underline;">Tạo mới phường xã</h4>
													<table cellpadding="4px" cellspacing="0px" width="100%" align="center">
													
						                            	<tr>
						                                	<TD class="plainlabel" width="40%" valign="top" align="left">Diễn giải</td>
						                                    <td class="plainlabel" valign="top" align="left">
							                                    <input type="text" name="pxTen" id="pxTen" value="<%=khBean.getPhuong() %>" />
						                                    </td>
						                                </tr>
						                                 <tr>
						                                	<td class="plainlabel" valign="top" align="left" colspan="2">
						        								<a class="btn btn-default" href="javascript:submitForm2();">Lưu lại</a>
						        								 
						        								 <!-- <button type="submit" class="btn btn-xl" href="javascript:submitForm2();">Send Message</button> -->
						                                	</td>
						                                </tr>
						                            </table>
												</div>
							                </div> --%>
						  	  	
						  	  	</TD>
						  </TR>
						   <TR>
						   <TD class="plainlabel" width="140px" > Địa chỉ <FONT class="erroralert"> *</FONT></TD>
						  	  	<TD colspan = "1" class="plainlabel"><INPUT type="text" name="diachi" id="diachi" value="<%= khBean.getDiachi() %>" size="40"></TD>
						  	  	 <TD class="plainlabel">Số ngày nợ</TD>
								 <TD colspan="1" class="plainlabel"><INPUT type="text" name="songayno" value="<%= khBean.getSongayno() %>" size="15"></TD>
								  <TD class="plainlabel">Số tiền nợ</TD>
								 <TD colspan="1" class="plainlabel"><INPUT type="text" name="sotienno" value="<%= khBean.getSotienno() %>" size="15"></TD>
							</TR>
						  <TR>
								<%-- <TD class="plainlabel">Mức tín dụng</TD>
						      <TD class="plainlabel" >
						      <SELECT name="ghcnTen" id="ghcnTen" >
                                	<OPTION value="" selected></OPTION>
                                	<% try{while(ghcn.next()){ 
								    	if(ghcn.getString("ghcnId").equals(khBean.getGhcnId())){ %>
								      		<option value='<%= ghcn.getString("ghcnId") %>' selected><%= ghcn.getString("ghcnTen")%></option>
								      	<%}else{ %>
								     		<option value='<%= ghcn.getString("ghcnId") %>'><%= ghcn.getString("ghcnTen")%></option>
								     	<%}}}catch(java.sql.SQLException e){} 
								     %>
                              </SELECT></TD> --%>
							  <TD class="plainlabel" style="display:none">Mức chiết khấu</TD>
							  <TD class="plainlabel" style="display:none"><INPUT type="text" name="mckTen" value="<%= khBean.getMckId() %>" size="15"></TD>		  
                               	 <TD class="plainlabel">Điện thoại</TD>
								 <TD class="plainlabel" colspan = "1"><INPUT type="text" name="dienthoai" value="<%= khBean.getSodienthoai() %>" size="15"></TD>
								  <TD class="plainlabel">Diện tích cửa hàng</TD>
								 <TD colspan="3" class="plainlabel"><INPUT type="text" name="dtch" value="<%= khBean.getDientichch() %>" size="15"></TD>    
						  </TR>
						  
							<TR>
							   	 <TD class="plainlabel">Kênh bán hàng<FONT class="erroralert">*</FONT></TD>
								 <TD  class="plainlabel"><SELECT name="kbhTen" id="kbhTen" onChange="tongleBangGia(this);">
								    <OPTION value="" selected></OPTION>
									<% try{while(kbh.next()){ 
								    	if(kbh.getString("kbhId").equals(khBean.getKbhId())){ %>
								      		<option value='<%= kbh.getString("kbhId") %>' selected><%=kbh.getString("kbhTen") %></option>
								      	<%}else{ %>
								     		<option value='<%= kbh.getString("kbhId") %>'><%= kbh.getString("kbhTen") %></option>
								     	<%}}}catch(java.sql.SQLException e){} 
								     %>
									</SELECT></TD>
									
									<TD class="plainlabel">Nhân viên giao nhận<FONT class="erroralert">*</FONT></TD>
								 <TD class="plainlabel">
									 <SELECT name="nvgnTen" id="nvgnTen">
									 <option value = ""> </option>
										<% try{while(nvgn.next()){ 
									    	if(nvgn.getString("nvgnId").equals(khBean.getNvgnId())){ %>
									      		<option value='<%= nvgn.getString("nvgnId") %>' selected><%=nvgn.getString("nvgnTen") %></option>
									      	<%}else{ %>
									     		<option value='<%= nvgn.getString("nvgnId") %>'><%= nvgn.getString("nvgnTen") %></option>
									     	<%}} nvgn.close(); }catch(java.sql.SQLException e){} 
									     %>
									</SELECT>
								</TD>
								
								 <TD class="plainlabel">Mã số thuế</TD>
								 <TD  class="plainlabel"><INPUT type="text" name="masothue" value="<%= khBean.getMasothue() %>" size="15"></TD>
								 
							</TR>
							
							<TR>
							 	<TD class="plainlabel">Code Route</TD>
								<TD class="plainlabel"><INPUT type="text" name="coderoute" id="coderoute" value="<%= khBean.getCoderoute() %>"></TD>
								<TD class="plainlabel">Route Name</TD>
								<TD class="plainlabel" colspan="3"><INPUT type="text" name="routename" id="routename" value="<%= khBean.getRoutename()%>"></TD>
							</TR>
							
						<TR>
						<TD class="plainlabel">Sử dụng chiết khấu trung tâm </TD>
							    <TD class="plainlabel" colspan="5" >
						      		<%  if (khBean.getSudungckTT().equals("1")){%>
										<input name="sudungck" type="checkbox" value ="1" checked="checked">
									<%} else {%>
										<input name="sudungck" type="checkbox" value ="0">
									<%} %>
						      	</TD>
						      	</TR>
						      	
						   <% if(khBean.getView() != null && khBean.getView().equals("TT")) {%>
						   <TR>
								<TD class="plainlabel">Hoạt động </TD>
							    <TD class="plainlabel" colspan="5" >
						      		<%  if (khBean.getTrangthai().equals("1")){%>
										<input name="trangthai" type="checkbox" value ="1" checked="checked">
									<%} else {%>
										<input name="trangthai" type="checkbox" value ="0">
									<%} %>
						      	</TD>
						    </TR>
						    <% } %>

						</TABLE>
						</FIELDSET>
						<TABLE width = 100% border="0" cellpadding="0" cellspacing ="0">						
							<TR>
								<TD height="100%">
									<FIELDSET><LEGEND class="legendtitle" style="color:black">Phân loại</LEGEND>
									<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
										<TR>
											<TD width="15%" class="plainlabel">Loại khách hàng</TD>
											<TD  class="plainlabel"><SELECT name="lchTen" id="lchTen">
											  <option value="" selected> </option>
											  <% try{while(lch.next()){ 
										    		if(lch.getString("lchId").equals(khBean.getLchId())){ %>
										      			<option value='<%= lch.getString("lchId") %>' selected><%= lch.getString("lchTen") %></option>
										      		<%}else{ %>
										     			<option value='<%= lch.getString("lchId") %>'><%= lch.getString("lchTen") %></option>
										     	<%}}}catch(java.sql.SQLException e){} 
										     %>
											  </SELECT></TD>
											  <TD class="plainlabel" valign="top">Vị trí </TD>
										  <TD class="plainlabel" valign="top"><SELECT name="vtchTen" >
										    <option value=""> </option>
										    <% try{ 
										    	while(vtch.next()){ 
								    				if(vtch.getString("vtchId").equals(khBean.getVtchId())){ %>
								      					<option value='<%=vtch.getString("vtchId")%>' selected><%=vtch.getString("vtchTen") %></option>
								      			<%  }else{ 
								      			%>
								     					<option value='<%=vtch.getString("vtchId")%>'><%=vtch.getString("vtchTen") %></option>
								     				
								     			<%}
								    		    }
										      }catch(java.sql.SQLException e){} %>
                                          	</SELECT></TD>
										</TR>
										<TR>
										  <TD class="plainlabel" valign="top">Hạng khách hàng </TD>
										  <TD class="plainlabel" colspan = "3" valign="top"><SELECT name="hchTen" >
										    <option value=""> </option>					
										     <% try{ while(hch.next()){ 
								    			if(hch.getString("hchId").equals(khBean.getHchId())){ %>
								      				<option value='<%=hch.getString("hchId")%>' selected><%=hch.getString("hchTen") %></option>
								      			<%}else{ %>
								     				<option value='<%=hch.getString("hchId")%>'><%=hch.getString("hchTen") %></option>
								     			<%}}}catch(java.sql.SQLException e){} %>
                                          	</SELECT></TD>
                                          <%-- 	<TD class="plainlabel" valign="top">Hình thức kinh doanh <FONT class="erroralert"> *</FONT></TD>
										  <TD class="plainlabel" valign="top">
										    <SELECT name="htkdId" id="htkdId"   onChange = "ajaxOption('tbNpp')"  style="width:150px" multiple>
						  	  		<OPTION value="0" ></OPTION>	
						  	  		<% if(htkdRs!= null){						  	  			
						   					while (htkdRs.next()){
						  	  					if (khBean.getHtkdId() .indexOf(htkdRs.getString("htkdId"))  >=0){%>
						  	  						<OPTION value=<%= htkdRs.getString("htkdId")%> selected><%= htkdRs.getString("htkdTen")%></OPTION>
						  	  					<%}else{ %>
						  	  						<OPTION value=<%= htkdRs.getString("htkdId")%> ><%= htkdRs.getString("htkdTen") %></OPTION>
						  	  	<%				  }
						  	  				
						  	  				}
						  	  			
						  	  		}%>						  	  			
						  	  	</SELECT></TD> --%>
									  </TR>
									</TABLE>
									</FIELDSET>								
								</TD>
							</TR>
						</TABLE>
					    <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
							<TR>
								<TD colspan = "6" width="100%">
									<FIELDSET><LEGEND class="legendtitle">Phân nhóm 
									</LEGEND>
								<TABLE width="100%" border="0" cellspacing="1" cellpadding="6">
								<TR class="tbheader">
									<TH width="20%">Nhóm khách hàng </TH>
									<TH width="10%">Chọn</TH>
								</TR>

								<%
								int i = 0;
								String lightrow = "tblightrow";
								String darkrow = "tbdarkrow";
								if(nkh_khList != null){
								try{while(nkh_khList.next()){ 
									if (i % 2 != 0) {%>						
										<TR class= <%=lightrow%> >
									<%} else {%>
										<TR class= <%= darkrow%> >
								<%}%>
									<TD align="center"><div align="left"><%= nkh_khList.getString("nkhTen") %> </div></TD>
									<% if (nkh_khIds.contains(nkh_khList.getString("nkhId"))){ %>
										   <TD align="center"><input name="nkh_khList" type="checkbox" value ="<%= nkh_khList.getString("nkhId") %>" checked></TD>
									<%}else{%>
										   <TD align="center"><input name="nkh_khList" type="checkbox" value ="<%= nkh_khList.getString("nkhId") %>" ></TD>
									<%}%>
									</TR>
							     	<%i++;}}catch(java.sql.SQLException e){}
								}
							  %>		
							   <tr class="tbfooter" > <td colspan="4" >&nbsp;</td> </tr>  											
							</TABLE>
							
									</FIELDSET>								
								</TD>
							</TR>
				    	</TABLE>
				    	<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
							<TR>
								<TD colspan = "6" width="100%">
									<FIELDSET><LEGEND class="legendtitle">Tuyến bán hàng 
									</LEGEND>
								<TABLE width="100%" border="0" cellspacing="1" cellpadding="6">
								<TR class="tbheader">
									<TH width="20%">Tuyến bán hàng </TH>
									<TH width="10%">Tần số</TH>
								</TR>

								<%
								 i = 0;
								 lightrow = "tblightrow";
								 darkrow = "tbdarkrow";
								if(tansoRs != null){
								try{while(tansoRs.next()){ 
									if (i % 2 != 0) {%>						
										<TR class= <%=lightrow%> >
									<%} else {%>
										<TR class= <%= darkrow%> >
								<%}%>
									<TD align="center"><div align="center"><%= tansoRs.getString("diengiai") %> </div></TD>
									<TD align="center"><div align="center"><%= tansoRs.getString("tanso") %> </div></TD>
									
									</TR>
							     	<%i++;}}catch(java.sql.SQLException e){}
								}
							  %>		
							   <tr class="tbfooter" > <td colspan="4" >&nbsp;</td> </tr>  											
							</TABLE>
							
									</FIELDSET>								
								</TD>
							</TR>
				    	</TABLE>
				    	<%-- <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
							<TR>
								<TD colspan = "6" width="100%">
									<FIELDSET><LEGEND class="legendtitle">Khai báo chiết khấu
									</LEGEND>
								<TABLE width="100%" border="0" cellspacing="1" cellpadding="6">
								<TR class="tbheader" >
		     	   							
		     	   						<TD class="plainlabel" id="menuchon"  align = "left" >
		     	   							
		     	   		 				<%if(khBean.getChonChietKhau().equals("1")) { %>
		     	   							<INPUT type="radio" name="chonchietkhau" value="1" onclick="ChonCK();" checked>Chiết khấu theo sản phẩm &nbsp;
		     	   						<%} else { %>
		     	   							<INPUT type="radio" name="chonchietkhau" value="1" onclick="ChonCK();">Chiết khấu theo sản phẩm &nbsp;
		     	   						<%} %>
		     	   			
		     	   							</TD>
		     	   							<TD class="plainlabel" id="menuchon" colspan = "3"  align = "left">
		     	   						<%if(khBean.getChonChietKhau().equals("2")) { %>
		     	   							<INPUT type="radio" name="chonchietkhau" value ="2" onclick="ChonCK();" checked>Chiết khấu theo chủng loại &nbsp;
		     	   						<%} else { %>
		     	   							<INPUT type="radio" name="chonchietkhau" value ="2" onclick="ChonCK();">Chiết khấu theo chủng loại &nbsp;
		     	   						<%} %>
		     	   			
		     	   							
		     	   							</TD>

										<!-- CHIẾT KHẤU SẢN PHẨM -->
										<% if(khBean.getChonChietKhau().equals("1")) {%>
				  							<TABLE width="100%" border="0" cellspacing="1px" cellpadding="3px" >
			       						<%} else { %>
			          						<TABLE width="100%" border="0" cellspacing="1px" cellpadding="3px" style="display: none">
			       						<%} %>
											<TR>
												<TD>
													<TABLE class="tabledetail" width = "100%" border="0" cellpadding="0" cellspacing="1">
													<tbody id="san_pham">
														<TR class="plainlabel">
													
															<TH width="13%">Mã sản phẩm </TH>
															<TH width="20%">Tên sản phẩm</TH>
															<TH width="7%">Đơn vị tính</TH>
															<TH width="10%">Phần trăm chiết khấu</TH>
														</TR>
							 			<% 
											int m = 0;
							 				
											if(spCKlist != null){
												IErpKhachHang_SPCK sanphamCK;
												int size = spCKlist.size();
								
												while (m < size){
													sanphamCK = spCKlist.get(m); 
												%> 
														<TR class= 'tblightrow' >
															<TD align="left" >
																<input type="hidden" name="stt" value = " <%= m%> " >
																<input value = "<%=sanphamCK.getIdSanPham() %> " name= "idspCK" type='hidden' >
																<input name = "maspCK" type="text" value="<%= sanphamCK.getMaSanPham()%>" autocomplete='off'  onkeyup="ajax_showOptions(this,'abc',event)" style="width:100%">
															</TD>
															<TD align="left" >
																<input name="tenspCK" type="text" readonly value="<%=sanphamCK.getTenSanPham()%>" style="width:100%" >
															</TD>
									
															<TD align = "center" >
								    							<input name="donvitinhCK" type="text" value="<%= sanphamCK.getDonViTinh() %>" readonly style="width:100%; text-align:center;padding-right: 5">
								    						</TD>								    
								    						<TD align = "center" >
								    							<input name="ptspCK" type="text" value="<%= sanphamCK.getPTChietKhau() %>"  style="width:100% ;text-align:right;padding-right: 5">
								    						</TD>
														</TR>
								 				<% m++; 
								 				}
											}
											int soSp=m;
											int max  = m +5;
											while(soSp < max){ 
										%> 
														<TR class= 'tblightrow'>
															<TD align="center" >
																<input type="hidden" name="stt" value = " <%= soSp%> " >
																<input values="" name="idspCK" type='hidden'>
																<input name = "maspCK" type="text" value="" autocomplete='off' onkeyup="ajax_showOptions(this,'abc',event)" style="width:100%" >
															</TD>
															<TD align="left" >
																<input name ="tenspCK" type="text" readonly value="" style="width:100%">
															</TD>
															<TD align = "center" >
								    							<input name ="donvitinhCK" type="text" value="" readonly style="width:100%; text-align: center;">
								    						</TD>
								    						<TD align = "center" >
								    							<input name="ptspCK" type="text" value="" style="width:100%;text-align:right">
								    						</TD>
														</TR>
										<% 
												soSp++;
												m++;
											}
									%>
									
														</tbody>
								
													</TABLE>

											</TD>
							
										</TR>
						
									</TABLE>
									                    									
		</TABLE>
							<!-- CHIẾT KHẤU SẢN PHẨM -->
							<% if(khBean.getChonChietKhau().equals("2")) {%>
				  					<TABLE width="100%" border="0" cellspacing="1px" cellpadding="3px" >
			       			<%} else { %>
			          				<TABLE width="100%" border="0" cellspacing="1px" cellpadding="3px" style="display: none">
			       			<%} %>
									<TR>
										<TD>

											<TABLE class="tabledetail" width = "100%" border="0" cellpadding="0" cellspacing="1">
											<tbody id="chungloai">
												<TR class="tbheader" >
													<TH width="13%">Mã chủng loại </TH>
													<TH width="13%">Tên chủng loại </TH>
													<TH width="10%">Phần trăm chiết khấu</TH>
												</TR>
							  <% 
							  		m = 0;
									if(chungloaiSP != null){
										IErpKhachHang_ChungLoaiSP clSPDetail;
										int size = chungloaiSP.size();
								
										while (m < size){
											clSPDetail = chungloaiSP.get(m); 
								%> 
												<TR class= 'tblightrow' >
													<TD align="left" >
														<input type="hidden" name="stt" value = " <%= m%> " >
														<input value=" <%=clSPDetail.getIdChungLoai() %> " name= "idChungLoaiSP" type='hidden' >
														<input name= "maChungLoaiSP" type="text" value="<%=clSPDetail.getMaChungLoai() %>" readonly style="width:100%">
													</TD>
													<TD align = "center" >
								    					<input name= "tenChungLoaiSP" type="text" value="<%= clSPDetail.getTenChungLoai() %>"  readonly style="width:100%">
								    				</TD>							    
								    				<TD align = "center" >
								    					<input name= "ptCKChungLoai" type="text" value="<%= clSPDetail.getPTChietKhau()  %>"  style="width:100% ;text-align:right;padding-right: 5">
								    				</TD>
												</TR>
								 <%  		m++; 
								 		}
									}%>
											</tbody>
										</TABLE>
									
									</TD>
								</TR>
							</TABLE>
				</table>
										
													
							</TABLE>
							
									</FIELDSET>								
								</TD>
							</TR>
				    	</TABLE> --%>
					</TR>
					
			  	</TABLE>
			
		<!--end body Dossier-->		
	</TD>
	</TR>
</TABLE>
</form>
<script type="text/javascript">
	
	replaces();
</script>
</BODY>
</HTML>
<% 	
khBean.DBclose();
khBean = null;
	try{
	
		if(bgst != null)
			bgst.close();
		if(ghcn != null)
			ghcn.close();
		if(hch != null)
			hch.close();
		if(kbh != null)
			kbh.close();
		if(lch != null)
			lch.close();
		if(mck != null)
			mck.close();
		if(nkh_khList!= null)
			nkh_khList.close();
		if(tp != null)
			tp.close();
		if(qh != null)
			qh.close();
		if(vtch != null)
			vtch.close();
		if(nch!=null){
			nch.close();
		}
		if(nvgn!=null){
			nvgn.close();
		}
		if(nkh_khList!=null){
			nkh_khList.close();
		}
		if(nkh_khIds!=null){
			nkh_khIds.clear();
		}
		session.setAttribute("khBean",null);
		if(rsbanggiasieuthi!=null){
			rsbanggiasieuthi.close();
		}
	
	}
	catch (SQLException e) {
		e.printStackTrace();
	}

%>
<%}%>
