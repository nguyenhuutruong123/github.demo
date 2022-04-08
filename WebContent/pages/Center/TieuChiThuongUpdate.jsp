<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.beans.tieuchithuong.ITieuchithuong" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/IMV/index.jsp");
	}else{ %>

<% 	ITieuchithuong tctBean = (ITieuchithuong)session.getAttribute("tctBean"); 
	
	String tctId = tctBean.getTctId();	
	ResultSet tct = tctBean.getTct();
	ResultSet tc = tctBean.getTieuchiRS();
	ResultSet nhomtcRs = tctBean.getNhomtcRS();
	ResultSet rsdvkd=tctBean.GetRsDVKD();
	ResultSet rsnhomsp = tctBean.GetRsNhomSp();
	ResultSet rskenh=tctBean.GetRsKenh();
	ResultSet tctctYeuCauRs = tctBean.getTctctYeuCauRs();
	ResultSet spDieuKienRs = tctBean.getSpDieuKienRs();
%>
<%NumberFormat formatter = new DecimalFormat("#,###,###"); %>
<%NumberFormat formatter2 = new DecimalFormat("#,###,###.##"); %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>HTP - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">

<LINK rel="stylesheet" href="../css/main.css" type="text/css">


<SCRIPT language="javascript" type="text/javascript">
 function submitForm()
{	
	
	
   
    document.tctForm.action.value='capnhat';
    document.forms["tctForm"].submit();
    	
}
 
 function submitForm2()
 {	
		var diengiaitc = document.getElementById('diengiaitc').value;
		var nhomtc = document.getElementById('nhomtc').value; 
		var id = document.getElementById('id').value;
		
		if(diengiaitc == '' || nhomtc == '')
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
			  { alert('Không thể tạo mới tiêu chí.'); }
			  else
			  { alert('Tạo tiêu chí mới thành công.'); submitForm(); } 
		   }
		}
		xmlhttp.open("GET","../../AjaxTaoTCMoi?diengiaitc=" + diengiaitc + "&nhomtc=" + nhomtc + "&id=" + id, true); 	
		xmlhttp.send();
 }

 function saveform()
{
	 	var tc = document.getElementById('tc').value;
	    var id = document.getElementById('id').value;
	    if(id != '' && id != '0' && tc =='' )
	    {
	    	alert('Vui lòng chọn loại tiêu chí để cập nhật');
	    	return;
	   	}
	 document.tctForm.action.value='luulai';
     document.forms["tctForm"].submit();
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


</SCRIPT>

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

<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />

<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
<script type="text/javascript">
	
	$(document).ready(function() {
		$(".days").datepicker({
			changeMonth : true,
			changeYear : true
		});		
	});	
</script>

<link rel="stylesheet" href="../css/radiobutton.css">

<link media="screen" rel="stylesheet" href="../css/colorbox.css">
<script src="../scripts/colorBox/jquery.colorbox.js"></script>
<script>
    $(document).ready(function()
    {
    	$(".taomoitc").colorbox({width:"45%", inline:true, href:"#taomoitc"});
        //Example of preserving a JavaScript event for inline calls.
        $("#click").click(function(){ 
            $('#click').css({"background-color":"#f00", "color":"#fff", "cursor":"inherit"}).text("dms - Project.");
            return false;
        });
    });
</script>

<!-- <link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
$(document).ready(function() { 
    $("select:not(.notuseselect2)").select2({ width: 'resolve' });     
});
</script> -->


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

<form name="tctForm" method="post" action="../../TieuChiThuongUpdateSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%=tctBean.getUserId() %>'>
<input type="hidden" name="loaiTC" value='<%=tctBean.GetLoaiTieuChi() %>'>
<input type="hidden" name="action" value="1">
 <input type="hidden" name="loaithuong" value="<%=tctBean.getLoaithuong()%>">
<input type="hidden" name="id" id="id" value="<%= tctBean.getTctId() %>">

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý chỉ tiêu &gt; Khai báo &gt; Công thức thưởng &gt; Cập nhật
							 <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %>&nbsp;  </TD></tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
			<TR ><TD >
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR class = "tbdarkrow">
							<TD width="30" align="left"><A href="../../TieuChiThuongSvl?userId=<%=userId %>" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>						
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
						<LEGEND class="legendtitle">Thông báo </LEGEND>
				
	    				<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold; border: 0px;" readonly="readonly" rows="1" ><%=tctBean.getMsg() %></textarea>
						<% tctBean.setMsg(""); %>
						</FIELDSET>
				   </TD>
				</tr>			

				<TR>
					<TD>

						<TABLE width="100%" border="0" cellpadding="0" cellspacing="1" >
							<TR>
								<TD>
									<FIELDSET>
									<LEGEND class="legendtitle" style="color:black">Tiêu chí thưởng 
									<% if(tctBean.GetLoaiTieuChi().equals("1")){ %> (TDV) <%} 
									else if (tctBean.GetLoaiTieuChi().equals("2")){ %> (Quản lý cấp 1) <%} 
									else if (tctBean.GetLoaiTieuChi().equals("3")){ %> (Quản lý cấp 2) <%} 
									else if (tctBean.GetLoaiTieuChi().equals("4")){%> (Quản lý khu vực) <%} 
									else if (tctBean.GetLoaiTieuChi().equals("5")){%> (Nhà phân phối) <%} %>
									</LEGEND>
	
									<TABLE class="first" border="0" width="100%" cellpadding="3" cellspacing="0">
					
										<TR>
							  				<TD class="plainlabel">Diễn giải </TD>
						  	  				<TD class="plainlabel" colspan= '3'>
						  	  					<input name = "diengiai" value = "<%= tctBean.getDiengiai() %>" size = 40 ></input>
										  	 </TD>
										  	 
					
										</TR>
										<tr style="display: none">
										
							  				<TD class="plainlabel">Kênh bán hàng </TD>
						  	  				<TD class="plainlabel">
						  	  					<SELECT name= "kbhId" >
						  	  						<%if(rskenh!=null){ 
							  	  						while (rskenh.next()){
							  	  							if(rskenh.getString("pk_seq").equals(tctBean.getKbh())){
							  	  								%>
							  	  							<OPTION value ="<%=rskenh.getString("pk_Seq") %> " ><%=rskenh.getString("ten")%> </OPTION>
							  	  								<% 
							  	  							}else{
							  	  							%>
						  	  									<OPTION value ="<%=rskenh.getString("pk_Seq") %>" ><%=rskenh.getString("ten")%> </OPTION>
						  	  								<% 
							  	  							}
							  	  						}
						  	  						}%>			

						  	  					</SELECT>

						  	  				</TD>
						  	  				
							  				<TD class="plainlabel">Đơn vị kinh doanh </TD>
						  	  				<TD class="plainlabel">
						  	  					<SELECT name= "dvkdId" >
						  	  						<%if(rsdvkd!=null){ 
							  	  						while (rsdvkd.next()){
							  	  							if(rsdvkd.getString("pk_seq").equals(tctBean.getDvkd())){
							  	  								%>
							  	  							<OPTION value ="<%=rsdvkd.getString("pk_Seq") %> " ><%=rsdvkd.getString("ten")%> </OPTION>
							  	  								<% 
							  	  							}else{
							  	  							%>
						  	  									<OPTION value ="<%=rsdvkd.getString("pk_Seq") %>" ><%=rsdvkd.getString("ten")%> </OPTION>
						  	  								<% 
							  	  							}
							  	  						}
						  	  						}%>							

						  	  					</SELECT>
						  	  				</TD>
										</TR>
									
										<TR>
							  				<TD class="plainlabel">Tháng</TD>
						  	  				<TD class="plainlabel">
						  	  				
						  	  				<select name="thang" >
											<option value=""> </option>  
											<%
		  										int k=0;
											String[] thang = { "Tháng","1","2","3","4","5","6","7","8","9","10","11","12" };
		  									for(k=0;k<=12;k++){
		  										//String chuoi=k<10?"0"+k:""+k;
		  										String chuoi=""+k;
		  									  if(tctBean.getThang().trim().equals(chuoi)){
		  									%>
											<option value=<%=k%> selected="selected" > <%=thang[k]%></option> 
											<%
		 										}else{
		 									%>
											<option value=<%=k%> ><%=thang[k]%></option> 
											<%
		 										}
		 									  }
		 									%>
											</select>
							  	  			</TD>											

							  				<TD class="plainlabel">Năm</TD>
						  	  				<TD class="plainlabel">
						  	  				
						  	  				<select name="nam" >
											<option value=""> </option>  
											<%
											  
		  										int n;
		  										for(n=2015;n<2030;n++){
		  										if(tctBean.getNam().equals(""+n)){
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
							  				<TD class="plainlabel">Tiêu chí </TD>
						  	  				<TD class="plainlabel" colspan= '3'>
						  	  					<SELECT name="tc" id="tc"  onChange = submitForm() >
						  	  						<OPTION value = "">&nbsp;</OPTION>
						  	  					<%try{
						  	  						while(tc.next()){
						  	  						if(tctBean.getTieuchiFK().equals(tc.getString("tc"))){%>
						  	  							<OPTION value = "<%=tc.getString("tc")%>" selected><%=tc.getString("diengiai") %></OPTION>
						  	  						<%}else{  %>	
						  	  							<OPTION value = "<%=tc.getString("tc")%>" ><%=tc.getString("diengiai") %></OPTION>						  	  								
						  	  					<% }}}catch(java.sql.SQLException e){}%>
						  	  						
						  	  					</SELECT>
						  	  					
						  	  					 <%-- <%  if(tct != null && !tctBean.getTieuchi().trim().equals("1")){ --%>
						  	  					 <% if(tct != null){
													try
													{	tct.beforeFirst();
														while(tct.next())
														{ 
												%>

						  	  					<A href = "../../TieuChiThuongUpdateSvl?userId=<%=userId%>&xoatieuchi=<%= tct.getString("pk_seq") %>;<%= tctBean.GetLoaiTieuChi()%>;<%= tctBean.getDiengiai()%>;<%= tctBean.getKbh().trim()%>;<%= tctBean.getDvkd().trim()%>;<%= tctBean.getThang()%>;<%= tctBean.getNam()%>;<%= tctBean.getTongThuong()%>;<%= tctBean.getTileDStoithieu()%>;<%= tctBean.getTctId()%>">
                                  				<%if(1==1){ %>
                                  				<img src="../images/Delete20.png" alt="Xoa" width="20" height="20" style="margin-bottom:-5; margin-left:7px;" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn có muốn xóa tiêu chí thưởng này?')) return false;"></A> 
						  	  					<%	}	   	} %>
											
													<%} catch(java.sql.SQLException e){}
													}%>
													
													
											<a <% if((tctBean.getTctId().trim().length() <=0 || tctBean.getTctId().equals("0") )  ) { %> style="display: none"  <%} %>  class="taomoitc" href="#">
		                        						<%if(1==1){ %>
		                        			<img src="../images/New.png" width="20" height="20" style="margin-bottom:-5; margin-left:7px;" title="Tạo mới tiêu chí"></a> <%} %>
							                <div style='display:none'>
						                        <div id="taomoitc" style='padding:0px 5px; background:#fff;'>
						                        	<h4 align="left" style="text-decoration: underline;">Tạo mới tiêu chí</h4>
													<table cellpadding="4px" cellspacing="0px" width="100%" align="center">
													
						                            	<tr>
						                                	<TD class="plainlabel" width="40%" valign="top" align="left">Diễn giải</td>
						                                    <td class="plainlabel" valign="top" align="left">
							                                    <input type="text" name="diengiaitc" id="diengiaitc" value="<%= tctBean.getTCDiengiai() %>" />
						                                    </td>
						                                </tr>
						                                
						                                <tr>
						                                	<td class="plainlabel" valign="top" align="left">Nhóm tiêu chí</td>
						                                    <td class="plainlabel" valign="top" align="left">		                                    	
						                                    	<select name="nhomtc" id="nhomtc">
						                                    		<option value=""> </option>
						                                    		<% if(nhomtcRs != null)
						                                    		{
						                                    			while(nhomtcRs.next()){ if(tctBean.getTCNhomId().equals(nhomtcRs.getString("pk_seq"))){ %>
						                                    				<option selected="selected" value="<%= nhomtcRs.getString("pk_seq") %>"><%= nhomtcRs.getString("diengiai") %></option>
						                                    		<% } else { %> 
						                                    				<option value="<%= nhomtcRs.getString("pk_seq") %>"><%= nhomtcRs.getString("diengiai") %></option>
						                                    		 <%} } } %>
						                                    	</select>
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
							                </div>
													
										  	 </TD>
										  	
										</TR>
										
										 <%-- <TR>
										  	 <%  if(tct != null){
													try
													{	tct.beforeFirst();
														while(tct.next())
														{
												%>
						  	  					
						  	  					 <td>
						  	  					<A href = "../../TieuChiThuongUpdateSvl?userId=<%=userId%>&deleteDetail=<%= tct.getString("pk_seq") %>">
                                  				<img src="../images/Delete20.png" alt="Xoa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn có muốn xóa tiêu chí thưởng này?')) return false;"></A>
						  	  					
						  	  					<%		} %>
												</td>
													<%} catch(java.sql.SQLException e){}
													}%>
										  	
										  	 </TR> --%>
										
										<TR style="display: none">
											<TD class="plainlabel">Tổng thưởng </TD>
						  	  				<TD class="plainlabel">
						  	  					<input name = "tongthuong" onblur="if(this.value=='')this.value='0'"   value = "<%= formatter2.format(Double.parseDouble(tctBean.getTongThuong())) %>" size = 30 ></input>
										  	 </TD>
										  	 
							  				<TD class="plainlabel">Tỉ lệ DS tối thiểu </TD>
						  	  				<TD class="plainlabel" colspan= '3'>
						  	  				<input name = "tldstoithieu" onblur="if(this.value=='')this.value='0'"   value = "<%= formatter2.format(Double.parseDouble(tctBean.getTileDStoithieu())) %>" size = 30 ></input>
										  	</TD>
											
										</TR>
										
										<% 
										tct.beforeFirst();
										while(tct.next())
										{ 

										%>
												<TR>
											  	<TD class="plainlabel">Từ ngày</TD>
												<TD class="plainlabel">
													<input type="text" name="tungay" class="days" value='<%=tct.getString("tungay")%>' />
												</TD>
												<TD class="plainlabel">Đến ngày</TD>
												<td class="plainlabel">
													<input type="text" name="denngay" class="days" value='<%=tct.getString("denngay")%>' />
												</td>
												</TR>
										 <%
												
										} %>
										
							
										
										
											
									</TABLE>
									</FIELDSET>
																	
									<TABLE width = "100%" cellpadding="0" cellspacing="0">
										<TR>
											<TD>
												<FIELDSET>
												<LEGEND class="legendtitle">&nbsp;Cập nhật
												<% if(tctBean.GetLoaiTieuChi().equals("1")){ %> (TDV) <%} 
												else if (tctBean.GetLoaiTieuChi().equals("2")){ %> (Quản lý cấp 1) <%} 
												else if (tctBean.GetLoaiTieuChi().equals("3")){ %> (Quản lý cấp 2) <%} 
												else if (tctBean.GetLoaiTieuChi().equals("4")){%> (Quản lý khu vực) <%} 
												else if (tctBean.GetLoaiTieuChi().equals("5")){%> (Nhà phân phối) <%} %>
												</LEGEND>
												
												<TABLE class = "chitieutable">
												<% 
													tct.beforeFirst();
													while(tct.next())
													{ %>
											
																<TR>
																	
																	<TD width="16%" class="plainlabel">Loại
														      		<%
														      		if(tctBean.getLoai().equals("1")){%>
																		<input name="loai" type="checkbox" onChange = submitForm() value ="1" checked>
																	<%} else {%>
																		<input name="loai" type="checkbox" onChange = submitForm() value ="0">
																	<%} %>
														      		</TD>
																	  <TD style="font-weight: bold;">DS tối thiểu ĐH </TD>
																	 <TD>
																		<input onblur="if(this.value=='')this.value='0'" style="width:100%; border:none; background: transparent;" name = "dstoithieudh" value = "<%= formatter2.format(Double.parseDouble(tct.getString("dstoithieudh"))) %>" size = 30 ></input>
																	 </TD>
																	<%--			
													  				<TD style="font-weight: bold;">Tối thiểu </TD>
												  	  				<TD>
												  	  					<input onblur="if(this.value=='')this.value='0'"   style="width:100%; border:none; background: transparent;" name = "min" value = "<%= formatter2.format(Double.parseDouble(tct.getString("toithieu"))) %>" size = 30 ></input>
																  	 </TD>
											  	 
																  	 <TD style="font-weight: bold;">Tối đa </TD>
												  	  				 <TD>
												  	  					<input onblur="if(this.value=='')this.value='0'"   style="width:100%; border:none; background: transparent;" name = "max" value = "<%= formatter2.format(Double.parseDouble(tct.getString("toida"))) %>" size = 30 ></input>
																  	 </TD>
																  	  --%>
																  	 <TD style="font-weight: bold;"><%if(tctBean.getTieuchi().equals("11") ) { %> CTKM bão hòa áp dụng  <%}else{ %>  Nhóm sản phẩm<%} %>  </TD>
												  	  				 <TD>
												  	  					<SELECT style="width:400px;" name= "nhomsp"  >
												  	  					<option value="" ></option>
												  	  						<%if(rsnhomsp!=null){ 
													  	  						while (rsnhomsp.next()){
													  	  							if(rsnhomsp.getString("pk_seq").trim().equals(tct.getString("nhomsp_fk"))){
													  	  								%>
													  	  							<OPTION selected="selected" value ="<%=rsnhomsp.getString("pk_Seq") %> " ><%=rsnhomsp.getString("ten")%> </OPTION>
													  	  								<% 
													  	  							}else{
													  	  							%>
												  	  									<OPTION value ="<%=rsnhomsp.getString("pk_Seq") %>" ><%=rsnhomsp.getString("ten")%> </OPTION>
												  	  								<% 
													  	  							}
													  	  						}
												  	  						}%>							
							
												  	  					</SELECT>
												  	  				</TD>

																	<TD class="plainlabel" colspan="3">
																	<div style="width: 300px;" class="register-switch">										
																	<% 
																	System.out.println("Loai ds : "+tct.getString("loaiDS"));
																	if(tct.getString("loaiDS").equals("0")){ %>
																	
																		<input type="radio" name="loaisales" value="0" id="salesin" checked="checked" class="register-switch-input"/>
																	    <label style="width:50%;" for="salesin" class="register-switch-label">Thưởng theo (VNĐ)</label>
																	    
																	    <input type="radio" name="loaisales" value="1" id="salesout" class="register-switch-input"/>
																	    <label style="width:50%;" for="salesout" class="register-switch-label">Thưởng theo (%)</label>

																	<%}else if(tct.getString("loaiDS").equals("1")){ %>
																		<input type="radio" name="loaisales" value="0" id="salesin" class="register-switch-input"/>
																	    <label style="width:50%;" for="salesin" class="register-switch-label">Thưởng theo (VNĐ)</label>
																	    
																	    <input type="radio" name="loaisales" value="1" id="salesout" checked="checked" class="register-switch-input"/>
																	    <label style="width:50%;" for="salesout" class="register-switch-label">Thưởng theo (%)</label>
																	<%} %>
																	 </div>	
																	 </TD>
 																</TR>
	 															  
															 
														 
															
													
												<% } %> 
												</TABLE>
												
												<TABLE class = "chitieutable" style= "width : 95%; ">
												<% 													
															
															try{tct.beforeFirst();while(tct.next()){ 
																%>
												
																	<TR <% if(tctBean.getLoai().equals("0")) { %> style='display: none'  <%} %>   >
														  				<TD style="font-weight: bold;">Tối thiểu(%) </TD>
													  	  				<TD>
													  	  					
													  	  					<input onblur="if(this.value=='')this.value='0'" style="width:100%; border:none; background: transparent;" name = "min" value = "<%= formatter2.format(Double.parseDouble(tct.getString("toithieu"))) %>" size = 30 ></input>
																	  	</TD>
												  	 
																	  	<TD style="font-weight: bold;">Tối đa(%) </TD>
													  	  				<TD>
													  	  					<input onblur="if(this.value=='')this.value='0'"   style="width:100%; border:none; background: transparent;" name = "max" value = "<%= formatter2.format(Double.parseDouble(tct.getString("toida"))) %>" size = 30 ></input>
																	  	</TD>
																	  	
																	  	<TD style="font-weight: bold;">Thưởng </TD>
													  	  				<TD>
													  	  					<input onblur="if(this.value=='')this.value='0'"  style="width:100%; border:none; background: transparent;" name = "thuongnsp" value = "<%= formatter2.format(Double.parseDouble(tct.getString("thuong"))) %>" size = 30 ></input>
																	  	</TD>
																	</TR>
																	
																	<TR  ><%--  <% if(tctBean.getLoai().equals("1")) { %> style='display: none'  <%} %> --%>
																	<TH width="21%" align="center" >Thứ tự </TH>
																	<TH width="21%" align="center">Từ </TH>															
																	<TH width="16%" align="center"> &nbsp; </TH>
																	<TH width="21%" align="center">Đến </TH>
																	<TH width="21%" align="center">Thưởng tối thiểu  </TH>
																	</TR>
															<%	
															String[] strNoidung = tct.getString("noidung").split(";");
															for(int i = 0; i < strNoidung.length; i++)
															{ 
																String classtr = "";
																if(i % 2 != 0)
																{
																	classtr = "even";
																}
															%>
																
																
																<TR <% if(tctBean.getLoai().equals("1")) { %>   <%} %> class= <%=classtr%>>
												  					<TD align="center" >
												  						<input name="tcct" readonly="readonly" type="hidden" value="<%=strNoidung[i].split(",")[0] %>" >
																		<input name="stt" readonly="readonly" type="text" style="width:100%; border:none; background: transparent;" value="<%=strNoidung[i].split(",")[1] %>" >
																	</TD>
																  	
												  					<TD align="center" >
																		<input name="tu" type="text"  
																		onblur="if(this.value=='')this.value='0'" 
																		style="width:100%; border:none; background: transparent;" value=<%=formatter2.format(Double.parseDouble(strNoidung[i].split(",")[2])) %> >
																									
																	</TD>
																	
																	<TD align="center" > &lt; </TD>
																	
												  					<TD align="center" >
																		<input name="den" type="text" 
																		onblur="if(this.value=='')this.value='0'" 
																		style="width:100%; border:none; background: transparent;" value=<%=formatter2.format(Double.parseDouble(strNoidung[i].split(",")[3])) %> >
																									
																	</TD>
																	
												  					<TD align="center" >
																		<input name="thuong" type="text" 
																		onblur="if(this.value=='')this.value='0'"
																		style="width:100%; border:none; background: transparent;" value="<%=formatter2.format(Double.parseDouble(strNoidung[i].split(",")[4])) %>" >
																									
																	</TD>
																</TR>
														<%	} %>
																	

														<%} // while
														}catch(java.sql.SQLException e){}
												 %>
												</TABLE>
												<%if(tctBean.getTieuchi().equals("10")){ %>	
												<TABLE class = "chitieutable" style= "width : 50%; ">
													<TR class="tbheader">
														<TH align="center" width="15%">Diễn giải</TH>
														<TH align="center" width="15%">Giá trị</TH>
														
													</TR>
													
													<%while(tctctYeuCauRs.next()){ %>
													
													<TR >
														<input type="hidden" name="tctctYeuCauId" value='<%= tctctYeuCauRs.getString("pk_seq") %>'>
														<TD align="center" width="15%"><%= tctctYeuCauRs.getString("diengiai") %></TD>
														<TD align="center" width="15%">
														<input name = "giatri" value = "<%= tctctYeuCauRs.getString("giatri") %>" size = 40 ></input>
														</TD>
														
													</TR>
													
													<%} %>
													
												</TABLE>
												<%} %>
												
												<%if(tctBean.getTieuchi().equals("8")){ %>	
												<TABLE class = "chitieutable" style= "width : 50%; ">
													<TR class="tbheader">
														<TH align="center" width="15%">Sản phẩm</TH>
														<TH align="center" width="15%">Số Lượng</TH>
														
													</TR>
													
													<%while(spDieuKienRs.next()){ %>
													
													<TR >
														<input type="hidden" name="spDieuKienId" value='<%= spDieuKienRs.getString("pk_seq") %>'>
														<TD align="center" ><%= spDieuKienRs.getString("ten") %></TD>
														<TD align="center" >
														<input name = "soluongSpDieuKien" value = "<%= spDieuKienRs.getString("SoLuong") %>" size = 40 ></input>
														</TD>
														
													</TR>
													
													<%} %>
													
												</TABLE>
												<%} %>
												
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
</BODY>
</HTML>

<% 
	if(tct != null) tct.close();
	if(tc != null) tc.close();
	tctBean.closeDB();
}%>