<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="geso.dms.center.beans.tinhthunhap.imp.*" %>
<%@ page import="geso.dms.center.beans.tinhthunhap.*" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.List" %>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%@page import="java.sql.SQLException" %>
<%@ page  import = "java.text.NumberFormat" %>
<%@ page  import = "java.text.DecimalFormat" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>

<%
 	ITinhthunhap obj =(ITinhthunhap)session.getAttribute("ttnBean");
	ResultSet dvkdRs = obj.getDvkdRs();
	ResultSet kbhRs = obj.getKbhRs();
	ResultSet kvRs = obj.getKhuvucRs();
	List<ITinhthunhapDetail> tcDetailList = obj.getTieuchiDetail();
	NumberFormat formatter = new DecimalFormat("#,###,##0.##");
	NumberFormat formatter2 = new DecimalFormat("#,###,##0.##");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>

<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
	<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
	<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
	<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(document).ready(function() {		
				$( ".days" ).datepicker({			    
						changeMonth: true,
						changeYear: true				
				});            
	        }); 		
			
	</script>
	
	<script type="text/javascript" src="../scripts/cool_DHTML_tootip.js"></script>
	
	<script type="text/javascript" src="..scripts/jquery-1.js"></script>
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
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
			z-index:100200;
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
		
		#dhtmltooltip
		{
			position: absolute;
			left: -300px;
			width: 400px;
			border: 1px solid black;
			padding: 5px;
			background-color: lightyellow;
			visibility: hidden;
			z-index: 100;
			font-size: 1.2em;
			cursor: pointer;
			color: red;
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
		
		form{
			display:inline;
		}	
	</style>
	
	<script type="text/javascript" src="../scripts/ajax.js"></script>
	<script type="text/javascript" src="../scripts/nhomthuongList.js"></script>
	
	<link media="screen" rel="stylesheet" href="../css/colorbox.css">
	    <script src="../scripts/colorBox/jquery.colorbox.js"></script>
	<script>
	function AjaxNpp(pos,element)
	{
		//Lay tat ca nppIdSelecd nhung nhapp da tick chon 
		var all_NppId_Checked="";
		//Lay nhung npp da tick chon chi tren dong nay thoi
		var nppIdChecked="";
		for(var  i= 0; i < 15; i++) { 
			var nppId= document.getElementsByName('NppIdCheck'+i+'');
			for(var ii=0;ii<nppId.length;ii++)
				{
					if(nppId.item(ii).checked==true&&i!=pos)
					{
						all_NppId_Checked=all_NppId_Checked+nppId.item(ii).value+",";
					}		
					if(nppId.item(ii).checked==true&&i==pos)
					{
						nppIdChecked=nppIdChecked+nppId.item(ii).value+",";
					}
				}
		}
		if(all_NppId_Checked.length>0)
		{
			all_NppId_Checked=all_NppId_Checked.substring(0,all_NppId_Checked.length-1);
		}
		if(nppIdChecked.length>0)
		{
			nppIdChecked=nppIdChecked.substring(0,nppIdChecked.length-1);
		}
	//	alert('nppTick tren dong '+nppIdChecked+'tat ca npp duoc tick'+all_NppId_Checked)
		var kvIdChecked= document.getElementsByName('khuvuc'+pos+'');
		var kvIds="";
		for(var ii=0;ii<kvIdChecked.length;ii++)
			{
				if(kvIdChecked.item(ii).checked==true)
				{
					kvIds=kvIds+kvIdChecked.item(ii).value+",";
				}		
			}
		if(kvIdChecked.length>0)
		{
			kvIds=kvIds.substring(0,kvIds.length-1);
		}
		 $.get("../../DonHangAjaxSvl?action=ajaxNpp&kvId="+kvIds+"&nppSelected="+all_NppId_Checked+"", function(list,status) {
				var table = $('#NppTable'+pos+'');
				if(kvIds=="")
				{	
					table.html(
						'<table width="600px" align="center" id="NppTable'+pos+'">'+
		                	'<tr>'+
	                		'<th width="100px" style="font-size: 12px">Mã NPP</th>'+
	                		'<th width="450px" style="font-size: 12px">Tên NPP</th>'+
	                		'<th width="50px" style="font-size: 12px" align="center">'+
	                		'Chọn <input type="checkbox" id="chkAll'+pos+'" onchange="sellectAll('+pos+')" >'+
	                		'</th>'+
	            		'</tr>'+
	            		'</table>'
            					);
				}
				else
				{
					table.html('<table width="600px" align="center" id="NppTable'+pos+'">'+
		                	'<tr>'+
		                		'<th width="100px" style="font-size: 12px">Mã NPP</th>'+
		                		'<th width="450px" style="font-size: 12px">Tên NPP</th>'+
		                		'<th width="50px" style="font-size: 12px" align="center">'+
		                		'Chọn <input type="checkbox" id="chkAll'+pos+'" onchange="sellectAll('+pos+')" >'+
		                		'</th>'+
		            		'</tr>'+
		            		'</table>'
    					);
					$.each(list, function(index, data) {
						var checked='';
						if(nppIdChecked.indexOf(data.id)>=0)
							 checked='checked="checked"';
						$('<tr>').appendTo(table)
							.append($('<td><input type=text  name="nppId'+pos+'"  value='+data.id +' style="width: 100%;"  readonly="readonly" ></td>' ))
							.append($("<td><input type='text' name='nppTen"+pos+"' value= '"+data.ten+"' style='width: 100%;'  readonly='readonly'> </td>" ))
							.append($('<td><input type=checkbox value='+data.id+' name="NppIdCheck'+pos+'" '+checked+' onchange="ajaxNv('+pos+')" ></td>' ));
					});
			}});
		 ajaxNv(pos);
		 }
	
	function ajaxNv(pos)
	{
		var nppId= document.getElementsByName('NppIdCheck'+pos+'');
		var kvIdChecked= document.getElementsByName('khuvuc'+pos+'');
		var luongcb=document.getElementsByName('luongcb').item(pos).value;
		var kvIds="";
		var chucvu=document.getElementsByName('chucvu').item(pos).value;
		for(var ii=0;ii<kvIdChecked.length;ii++)
			{
				if(kvIdChecked.item(ii).checked==true)
				{
					kvIds=kvIds+kvIdChecked.item(ii).value+",";
				}		
			}
		if(kvIdChecked.length>0)
		{
			kvIds=kvIds.substring(0,kvIds.length-1);
		}
		
		var nppIdChecked="";
		for(var ii=0;ii<nppId.length;ii++)
			{
				if(nppId.item(ii).checked==true)
				{
					nppIdChecked=nppIdChecked+nppId.item(ii).value+",";
				}
			}
		
		if(nppIdChecked.length>0)
		{
			nppIdChecked=nppIdChecked.substring(0,nppIdChecked.length-1);
		}
		//alert(chucvu);
		$.get("../../DonHangAjaxSvl?action=ajaxNv&nppSelected="+nppIdChecked+"&chucvu="+chucvu+"&kvId="+kvIds+"", function(list,status) {
			var table = $('#nvTable'+pos+'');
			if(nppIdChecked==""&&chucvu!="SS")
			{	
				table.html('<table width="700px" align="center" cellpadding="0" cellspacing="1" >'+
					              '<tr >'+
					           	'<th width="300px" style="font-size: 12px">Nhà phân phối</th>'+
					               '<th width="100px" style="font-size: 12px">Mã NV</th>'+
					               '<th width="200px" style="font-size: 12px">Tên nhân viên</th>'+
					               '<th width="100px" style="font-size: 12px">Lương cơ bản</th>'+
					          	'</tr>'+
					      	'</table>'
        					);
			}
			else
			{
				table.html('<table width="700px" align="center" cellpadding="0" cellspacing="1" >'+
			              '<tr >'+
				           	'<th width="300px" style="font-size: 12px">Nhà phân phối</th>'+
				               '<th width="100px" style="font-size: 12px">Mã NV</th>'+
				               '<th width="200px" style="font-size: 12px">Tên nhân viên</th>'+
				               '<th width="100px" style="font-size: 12px">Lương cơ bản</th>'+
				          	'</tr>'+
				      	'</table>'
					);
				$.each(list, function(index, data) {
					var checked='';
					if(data.luongcb=="")
						data.luongcb=luongcb;
					if(nppIdChecked.indexOf(data.id)>=0)
						 checked='checked="checked"';
					$('<tr>').appendTo(table)
						.append($('<td><input type=text  name="npp'+pos+'Id"  value="'+data.nppTen +'" style="width: 100%;"  readonly="readonly" ></td>' ))
						.append($("<td><input type='text' name='nv"+pos+"Id' value= '"+data.id+"' style='width: 100%;'  readonly='readonly'> </td>" ))
						.append($("<td><input type='text' name='nv"+pos+"Ten' value= '"+data.ten+"' style='width: 100%;'  readonly='readonly'> </td>" ))
					.append($("<td><input type='text' name='nv"+pos+"Luongcb' value= '"+data.luongcb+"' style='width: 100%;' onkeyup='Dinhdang(this)'  > </td>" ))
				});
		}});
	}
	
	</script>
    <script>
        $(document).ready(function()
        {
        	<% for(int k = 0; k < 15; k++) { %>
        	
	        	$(".tieuchidanhgia<%= k %>").colorbox({width:"60%", inline:true, href:"#tieuchidanhgia<%= k %>"});
	         
	            $("#click").click(function(){ 
	                $('#click').css({"background-color":"#f00", "color":"#fff", "cursor":"inherit"}).text("DuongBienHoa - Project.");
	                return false;
	            });
	            
	            $(".apchokhuvuc<%= k %>").colorbox({width:"50%", inline:true, href:"#apchokhuvuc<%= k %>"});
	         
	            $("#click").click(function(){ 
	                $('#click').css({"background-color":"#f00", "color":"#fff", "cursor":"inherit"}).text("DuongBienHoa - Project.");
	                return false;
	            });
	            
	            $(".apchoNhanVien<%= k %>").colorbox({width:"90%", inline:true, href:"#apchoNhanVien<%= k %>"});
	         
	            $("#click").click(function(){ 
	                $('#click').css({"background-color":"#f00", "color":"#fff", "cursor":"inherit"}).text("DuongBienHoa - Project.");
	                return false;
	            });
	            
	            $(".apChoNpp<%= k %>").colorbox({width:"90%", inline:true, href:"#apChoNpp<%= k %>"});
	         
	            $("#click").click(function(){ 
	                $('#click').css({"background-color":"#f00", "color":"#fff", "cursor":"inherit"}).text("DuongBienHoa - Project.");
	                return false;
	            });
	            
	            $(".thuongvuotmuc<%= k %>").colorbox({width:"70%", inline:true, href:"#thuongvuotmuc<%= k %>"});
	            $("#click").click(function(){ 
	                $('#click').css({"background-color":"#f00", "color":"#fff", "cursor":"inherit"}).text("DuongBienHoa - Project.");
	                return false;
	            });
           
            <% } %>
        });
    </script>

<SCRIPT language="JavaScript" type="text/javascript">
	function submitform()
	{
		document.forms["tctsku"].action.value = "submit";
	    document.forms["tctsku"].submit();
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
	  if( thang == '' )
	  {
		  alert("Vui lòng chọn tháng");
		  return;
	  }
	  
	  var nam = document.getElementById("nam").value;
	  if( nam == '' )
	  {
		  alert("Vui lòng chọn năm");
		  return;
	  }
	  
	  document.forms["tctsku"].action.value = "save";
	  document.forms["tctsku"].submit(); 
  }
	
	function Dinhdang(element)
	{
		element.value = DinhDangTien(element.value);
	}
	
	function DinhdangLuong(element, stt)
	{
		element.value = DinhDangTien(element.value);
		var nvLuongcb = document.getElementsByName("nv" + stt + "Luongcb");
		if(nvLuongcb != null)
		{
			for(var i = 0; i < nvLuongcb.length; i++)
			{
				nvLuongcb.item(i).value = element.value;
			}
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
	
	  function sellectAll(stt)
	    {
	    	//alert(stt);
			 var chkAll = document.getElementById("chkAll" + stt);
			 var spIds = document.getElementsByName("NppIdCheck" + stt);
			
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
			 ajaxNv(stt);
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

<% Csrf csdr=new Csrf(request,response,true,false,true);%>
<input type="hidden" name="userId" value='<%= userId %>' >
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
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý nhân sự > Khai báo > Tính thu nhập > Cập nhật</TD> 
							 <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%= userTen %></TD></tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
			<TR ><TD >
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR class = "tbdarkrow">
							<TD width="30" align="left"><A href="javascript:history.back()" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
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
						<LEGEND class="legendtitle">Thông báo </LEGEND>
				
	    				<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold"  style="width: 100% ; color:#F00 ; font-weight:bold" readonly="readonly" rows="1"><%= obj.getMsg() %></textarea>
						</FIELDSET>
				   </TD>
				</tr>			

				<TR>
				  <TD height="100%" width="100%">
						<FIELDSET >
						<LEGEND class="legendtitle" style="color:black">Thông tin tiêu chí đánh giá </LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
						  <TR valign="top">
						  	  	<TD width="1%"  class="plainlabel">Diễn giải</TD>
						  	  	<TD class="plainlabel" colspan="3">
						  	  		<input type="text" name="diengiai" id="diengiai" value="<%= obj.getDiengiai() %>" >
						  	  	</TD>
						  </TR>
						  <TR>
								<TD class="plainlabel" >Tháng <FONT class="erroralert"> *</FONT></TD>
								<TD class="plainlabel">
									<select name="thang" id = "thang" style="width: 50px">
									<option value= ""> </option>  
									<%
									int k=1;
									for(k=1; k <= 12; k++ ){
										
									  if(obj.getThang().equals(Integer.toString(k)) ) {
									%>
										<option value=<%= k %> selected="selected" > <%= k %></option> 
									<%  }else{  %>
										<option value=<%= k %> > <%= k %></option> 
									<% } }%>
									</select>
								</TD>
							</TR>
							<TR>
							  	<TD class="plainlabel">Năm <FONT class="erroralert"> *</FONT></TD>
						  	  	<TD class="plainlabel">
									<select name="nam" id = "nam" style="width :50px">
									<option value= ""> </option>  
									<%
									Calendar cal=Calendar.getInstance();
									int year_=cal.get(Calendar.YEAR);
									for(int n=2008; n<year_+3; n++) {
									  if(obj.getNam().equals( Integer.toString(n)) ){									  
									%>
										<option value=<%=n %> selected="selected" > <%=n %></option> 
									<%
									  }else{
									 %>
										<option value=<%= n %> ><%=n %></option> 
									<% } }
									%>
									</select>
						  	  	</TD>
						  </TR>
						</TABLE>
					<hr />
						
					<TABLE class="tabledetail" width="100%" border="0" cellspacing="1px" cellpadding="0px">
                    <TR class="tbheader">
                        <TH align="center" width="5%">ĐVKD</TH>
                        <TH align="center" width="5%">Kênh</TH>
                        <TH align="center" width="10%">Khu vực</TH>
                        <TH align="center" width="5%">Chức vụ</TH>
                        <TH align="center" width="10%">Nhà phân phối</TH>
                        <TH align="center" width="10%">Lương CB</TH>
                        <TH align="center" width="10%">% Lương TG</TH>
                        <TH align="center" width="10%">% Lương HQ</TH>
                        <TH align="center" width="2%" style="display: none">Bảo hiểm từ</TH>
                        <TH align="center" width="10%">Mức bảo hiểm</TH>
                        <TH align="center" width="8%">Bảo hiểm</TH>
                        <TH align="center" width="8%">Công đoàn</TH>
                        <TH align="center" width="5%">TĐNC</TH>
                        <TH align="center" width="5%" style="display: none">TVM</TH>
                        <TH align="center" width="5%">Tiêu chí</TH>
                    </TR>
					
					<% 
						int count = 0;
                        
                        String[] maDetai = null;
                        String[] noidung = null;
                        String[] trongso = null;
                        String[] mucbaohiem = null;
                        String[] thuongSRvuotmuc = null;

                        for(int i = 0; i < tcDetailList.size(); i++)
                        {
                        	ITinhthunhapDetail detail = tcDetailList.get(i);
                            maDetai = tcDetailList.get(i).getMaDetail();
                            noidung = tcDetailList.get(i).getNoidung();
                            trongso = tcDetailList.get(i).getTrongso();
                            mucbaohiem = tcDetailList.get(i).getMucbaohiem();
                            thuongSRvuotmuc = tcDetailList.get(i).getThuongSRvuotmuc();
                            List<IThuongvuotmuc> tvmList = detail.getThuongvmList();
                            
                        	%>
                        	
                        	<tr>
                        		<td>
                        			<select name="dvkdIds" style="width: 100%" >
                                   		<% if(dvkdRs != null)
                                   		{ 
                                   			dvkdRs.beforeFirst();
                                   			while(dvkdRs.next()) 
                                   			{  %>
                                   				<option value="<%= dvkdRs.getString("pk_seq") %>"><%= dvkdRs.getString("donvi") %></option>
                                   		 <%} }  %>
                                   	</select>
                        			<input type="hidden" style="width: 100%" name = 'trongsoIds' value="" >
                        		</td>
                        		<td>
                        			<select name="kbhIds" style="width: 100%" >
                                   		<option value=""> </option>
                                   		<% if(kbhRs != null)
                                   		{ 
                                   			kbhRs.beforeFirst();
                                   			while(kbhRs.next()) 
                                   			{ 
                                   		if(detail.getKbhId().equals(kbhRs.getString("pk_seq"))){ %>
                                   				<option value="<%= kbhRs.getString("pk_seq") %>" selected="selected"><%= kbhRs.getString("diengiai") %></option>
                                   		 <% } else { %> 
                                   		 		<option value="<%= kbhRs.getString("pk_seq") %>"><%= kbhRs.getString("diengiai") %></option>
                                   		 <%  } } }  %>
                                   	</select>
                        		</td>
                        		<td align="center">
                        			<a class="apchokhuvuc<%= i %>" href="#">
	           	 							&nbsp; <img alt="Áp cho khu vực" src="../images/vitriluu.png"></a>
	           	 				
		           	 				<div style='display:none'>
				                        <div id='apchokhuvuc<%= i %>' style='padding:0px 5px; background:#fff;'>
				                        	<h4 align="left">Áp dụng cho khu vực</h4>
											
											<table width="450px" align="center">
						                        <tr>
						                            <th width="100px" style="font-size: 12px">Mã KV</th>
						                            <th width="250px" style="font-size: 12px">Khu vực</th>
						                            <th width="100px" style="font-size: 12px" align="center">Chọn</th>
						                        </tr>
				                            	<% if(kvRs != null)
			                                   		{ 
			                                   			kvRs.beforeFirst();
			                                   			while(kvRs.next()) 
			                                   			{  
			                                   				if( detail.getKvId().indexOf(kvRs.getString("pk_seq")) >= 0 ){ %>
			                                   					<Tr>
			                                   						<td><input type="text" style="width: 100%" value="<%= kvRs.getString("pk_seq") %>" readonly="readonly" ></td>
			                                   						<td><input type="text" style="width: 100%" value="<%= kvRs.getString("ten") %>" readonly="readonly" ></td>
			                                   						<td align="center">
			                                   							<input    name="khuvuc<%= i %>" type="checkbox" style="width: 100%" value="<%= kvRs.getString("pk_seq") %>"  checked="checked"  onchange="AjaxNpp(<%=i %>,this)">
			                                   						</td>
			                                   					</Tr>
			                                   		 		<% } else { %> 
			                                   		 			<Tr>
			                                   						<td><input type="text" style="width: 100%" value="<%= kvRs.getString("pk_seq") %>" readonly="readonly" ></td>
			                                   						<td><input type="text" style="width: 100%" value="<%= kvRs.getString("ten") %>" readonly="readonly" ></td>
			                                   						<td align="center">
			                                   							<input   name="khuvuc<%= i %>" type="checkbox" style="width: 100%" value="<%= kvRs.getString("pk_seq") %>"  onchange="AjaxNpp(<%=i %>,this)">
			                                   						</td>
			                                   					</Tr>
			                                   				 <%  } } }  %>
						                       <tr>
						                       		<td colspan="3">&nbsp;</td>
						                       </tr>
						                    </table>
											
										</div>
					                </div>
                        			
                        		</td>
                        		<td>
                        			<select name="chucvu" style="width: 100%"  onchange="ajaxNv(<%=i %>);"  >
                        				<% if(detail.getChucvu().equals("SS")){ %>
                        					<option value=""></option>
	                        				<option value="SR">SR</option>
	                        				<option value="SS" selected="selected">SS</option>
                        				<%} else if(detail.getChucvu().equals("SR")){ %> 
                        					<option value=""></option>
	                        				<option value="SR" selected="selected">SR</option>
	                        				<option value="SS" >SS</option>
                        				<% }else {%>
                        					<option value=""></option>
	                        				<option value="SR">SR</option>
	                        				<option value="SS" >SS</option>
                        				<%} %>
                        			</select>
                        		</td>
                        		
                       			<td align="center">
                       			<a class="apChoNpp<%= i %>" href="#">
           	 							&nbsp; <img alt="Áp cho nhà phân phối" src="../images/vitriluu.png"></a>
           	 				
	           	 				<div style='display:none'>
			                        <div id='apChoNpp<%= i %>' style='padding:0px 5px; background:#fff;'>
			                        	<h4 align="left">Áp dụng cho nhà phân phối</h4>
										
										<table width="600px" align="center" id="NppTable<%=i%>">
					                        <tr>
					                            <th width="100px" style="font-size: 12px">Mã NPP</th>
					                            <th width="450px" style="font-size: 12px">Tên NPP</th>
					                            <th width="50px" style="font-size: 12px" align="center">
					                            Chọn <input type="checkbox" id="chkAll<%= i %>" onchange="sellectAll(<%= i %>)" >
					                            </th>
					                            
					                        </tr>
			                            	 <% 
						                        List<INhaPhanPhoi> nppList = detail.getNhaPhanPhoiList();
						                        if(nppList.size() > 0 ) 
						                        { 
						                        	for(int ii = 0; ii < nppList.size(); ii++ ) 
						                        	{ 
						                        		INhaPhanPhoi npp = nppList.get(ii);
						                        		%> 
						                        	<Tr>
						                        		<td><input  value="<%= npp.getId() %>" style="width: 100%;"  readonly="readonly"  name="nppId<%=i%>"> </td>
						                        		<td><input value="<%= npp.getTen() %>" style="width: 100%; text-align: center;"  readonly="readonly" name="nppTen<%=i%>"> </td>
						                        		<td align="center">
						                        			<% if(detail.GetNppId().indexOf(npp.getId()) >= 0 ) { %>
						                        			<input  name="NppIdCheck<%=i %>" value="<%= npp.getId() %>" type="checkbox"  checked="checked"  onchange="ajaxNv(<%=i %>" >
						                        		<% } else { %> 
						                        			<input name="NppIdCheck<%=i %>"   value="<%= npp.getId() %>" type="checkbox"  onchange="ajaxNv(<%=i %>">
						                        	
						                        <% } }} %>

					                       
					                    </table>
										
									</div>
				                </div>
                       			
                       		</td>
                        		
                        		
                        		<td>
                        			<input type="text" style="width: 70%; text-align: right;" name = 'luongcb' value="<%= detail.getLuongCB().length() > 0 ? formatter.format(Double.parseDouble(detail.getLuongCB())) : "" %>" onkeyup="DinhdangLuong(this, <%= i %> )" >
                        			<a class="apchoNhanVien<%= i %>" href="#">
	           	 							&nbsp; <img alt="Áp cho khu vực" src="../images/vitriluu.png"></a>
		           	 				<div style='display:none'>
				                        <div id='apchoNhanVien<%= i %>' style='padding:0px 5px; background:#fff;'>
				                        	<h4 align="left">Áp dụng cho nhân viên</h4>
											<div style="max-height: 400px">
												<table width="700px" align="center" cellpadding="0" cellspacing="1" id="nvTable<%=i%>">
							                        <tr >
							                        	<th width="300px" style="font-size: 12px">Nhà phân phối</th>
							                            <th width="100px" style="font-size: 12px">Mã NV</th>
							                            <th width="200px" style="font-size: 12px">Tên nhân viên</th>
							                            <th width="100px" style="font-size: 12px">Lương cơ bản</th>
							                        </tr>
							                        
							                        <% 
							                        List<INhanvien> nvList = detail.getNhanvienList(); 
							                        if(nvList.size() > 0 ) 
							                        { 
							                        	for(int ii = 0; ii < nvList.size(); ii++ ) 
							                        	{ 
							                        		INhanvien nv = nvList.get(ii);
							                        		%> 
							                        	
							                        	<Tr>
							                        		<td><input value="<%= nv.getNppTen() %>" style="width: 100%;" name="npp<%= i %>Id" readonly="readonly" > </td>
							                        		<td><input value="<%= nv.getId() %>" style="width: 100%; text-align: center;" name="nv<%= i %>Id" readonly="readonly" > </td>
							                        		<td><input value="<%= nv.getTen() %>" style="width: 100%" name="nv<%= i %>Ten"  readonly="readonly" > </td>
							                        		<td><input value="<%= nv.getLuongCB().trim().length()>0?formatter.format(Double.parseDouble( nv.getLuongCB())):"" %>" style="width: 100%; text-align: right;" name="nv<%= i %>Luongcb" onkeyup="Dinhdang(this)"  onchange="Dinhdang(this)" > </td>
							                        	</Tr>
							                        	
							                        <% } } %>
							                        
							                       <tr>
							                       		<td colspan="3">&nbsp;</td>
							                       </tr>
							                    </table>
						                    </div>
											
										</div>
					                </div>
                        			
                        		</td>
                        		<td>
                        			<input type="hidden" name="khuvucTen" value="<%= detail.getKvTenSelected() %>" > 
                        			<input type="text" style="width: 100%; text-align: right;" name = 'ptluongtg' value="<%= detail.getPhantramluongTG().length() > 0 ? formatter.format(Double.parseDouble(detail.getPhantramluongTG())) : "" %>" onkeyup="Dinhdang(this)" >
                        		</td>
                        		<td><input type="text" style="width: 100%; text-align: right;" name = 'ptluonghq' value="<%= detail.getPhantramluongHQ().length() > 0 ? formatter.format(Double.parseDouble(detail.getPhantramluongHQ())) : "" %>" onkeyup="Dinhdang(this)" ></td>
                        		<td style="display:none;"><input type="text" style="width: 100%; text-align: right;" name = 'baohiemtu' value="<%= detail.getBaohiemtu() %>" onkeyup="Dinhdang(this)" ></td>
                        		<td><input type="text" style="width: 100%; text-align: right;" name = 'baohiemden' value="<%= detail.getBaohiemDen().length() > 0 ? formatter.format(Double.parseDouble(detail.getBaohiemDen())) : "" %>" onkeyup="Dinhdang(this)" : "" ></td>
                        		<td><input type="text" style="width: 100%; text-align: right;" name = 'baohiem' value="<%= detail.getBaohiem().length() > 0 ? formatter2.format(Double.parseDouble(detail.getBaohiem())) : "" %>" ></td>
                        		<td><input type="text" style="width: 100%; text-align: right;" name = 'congdoan' value="<%= detail.getCongdoan().length() > 0 ? formatter2.format(Double.parseDouble(detail.getCongdoan())) : "" %>"  ></td>
                        		<td><input type="text" style="width: 100%; text-align: right;" name = 'tdnc' value="<%= detail.getThucdatngaycong().length() > 0 ? formatter.format(Double.parseDouble(detail.getThucdatngaycong())) : "" %>" onkeyup="Dinhdang(this)" ></td>
                        		
                        		<td align="center" style="display: none">
                        			
                        			<a class="thuongvuotmuc<%= i %>" href="#">
	           	 							&nbsp; <img alt="Khai báo tiêu chí" src="../images/vitriluu.png"></a>
	           	 				
		           	 				<div style='display:none'>
				                        <div id='thuongvuotmuc<%= i %>' style='padding:0px 5px; background:#fff;'>
				                        	<h4 align="left">Khai báo thưởng vượt mức</h4>
											
											<table width="650px" align="center">
						                        <tr>
						                            <th width="250px" style="font-size: 12px">Nhóm thưởng </th>
						                            <th width="100px" align="center" style="font-size: 12px">Từ mức</th>
						                            <th width="100px" align="center" style="font-size: 12px">Đến mức</th>
						                            <th width="100px" align="center" style="font-size: 12px; display: none">T. vượt mức</th>
						                        </tr>
				                            	<%
				                            	int sodong = 0;
				                            	for(int pos = 0; pos < tvmList.size(); pos++)
				                            	{
				                            		IThuongvuotmuc tvm = tvmList.get(pos);
				                            	%>
					                            	<tr>
							                            <td><input type="text" value="<%= tvm.getNhomthuong() %>" name="tvmNhomthuong<%= i %>" style="width: 100%"
							                            			 onkeyup="ajax_showOptions(this,'sanpham',event)"	 ></td>
							                            <td><input type="text" style="text-align: right; width: 100%" value="<%= tvm.getTumuc().trim().length() > 0 ? formatter.format(Double.parseDouble(tvm.getTumuc().trim())) : "" %>" name="tvmTumuc<%= i %>"></td>
							                            <td><input type="text" style="text-align: right; width: 100%" value="<%= tvm.getDenmuc().trim().length() > 0 ? formatter.format(Double.parseDouble(tvm.getDenmuc().trim())) : "" %>" name="tvmDenmuc<%= i %>"></td>
							                            <td ><input type="text" style="text-align: right; width: 100%" value="<%= tvm.getThuong().trim().length() > 0 ? formatter.format(Double.parseDouble(tvm.getThuong().trim())) : "" %>" name="tvmThuong<%= i %>"></td>
							                        </tr>
						                       <% sodong ++;  } %>
						                       
						                       <% for(int pos = sodong; pos <= 10; pos ++ ) { %>
						                       
						                       		<tr>
							                            <td><input type="text" value="" name="tvmNhomthuong<%= i %>" style="width: 100%"
							                            			 onkeyup="ajax_showOptions(this,'sanpham',event)"	 ></td>
							                            <td><input type="text" style="text-align: right; width: 100%" value="" name="tvmTumuc<%= i %>"></td>
							                            <td><input type="text" style="text-align: right; width: 100%" value="" name="tvmDenmuc<%= i %>"></td>
							                            <td ><input type="text" style="text-align: right; width: 100%" value="" name="tvmThuong<%= i %>"></td>
							                        </tr>
						                       		
						                       <% } %>
						                       
						                       <tr>
						                       		<td colspan="5">&nbsp;</td>
						                       </tr>
						                    </table>
											
										</div>
					                </div>
                        			     
                        		</td>
                        		
                        		<td align="center">
                        			
                        			<a class="tieuchidanhgia<%= i %>" href="#">
	           	 							&nbsp; <img alt="Khai báo tiêu chí" src="../images/vitriluu.png"></a>
	           	 				
		           	 				<div style='display:none'>
				                        <div id='tieuchidanhgia<%= i %>' style='padding:0px 5px; background:#fff;'>
				                        	<h4 align="left">Khai báo tiêu chí tính lương</h4>
											
											<table width="650px" align="center">
						                        <tr>
						                            <th width="100px" style="font-size: 12px">Mã </th>
						                            <th width="250px" style="font-size: 12px">Nội dung</th>
						                            <th width="100px" align="center" style="font-size: 12px">Trọng số</th>
						                            <th width="100px" align="center" style="font-size: 12px">Mức bảo hiểm</th>
						                            <th width="100px" align="center" style="font-size: 12px; display: none">T. vượt mức</th>
						                        </tr>
				                            	<% for(int pos = 0; pos < 4; pos++) { %>
					                            	<tr>
							                            <td><input type="text" value="<%= maDetai[pos] %>" name="maDetail<%= i %>" style="width: 100%" readonly="readonly"></td>
							                            <td><input type="text" value="<%= noidung[pos] %>" name="noidung<%= i %>" style="width: 100%"
							                            			 onkeyup="ajax_showOptions(this,'sanpham',event)"	 ></td>
							                            <td><input type="text" style="text-align: right; width: 100%" value="<%=trongso[pos]!=null&&trongso[pos].trim().length() > 0? formatter.format(Double.parseDouble(trongso[pos])):"" %>" name="trongso<%= i %>"></td>
							                            <td><input type="text" style="text-align: right; width: 100%" value="<%= mucbaohiem[pos]!=null&&mucbaohiem[pos].trim().length() > 0 ? formatter.format(Double.parseDouble(mucbaohiem[pos])) : "" %>" name="mucbaohiem<%= i %>"></td>
							                            <td style="display: none"><input type="text" style="text-align: right; width: 100%" value="<%= thuongSRvuotmuc[pos]!=null&&thuongSRvuotmuc[pos].trim().length() > 0 ? formatter.format(Double.parseDouble(thuongSRvuotmuc[pos])) : "" %>" name="thuongvuotmuc<%= i %>"></td>
							                        </tr>
						                       <% } %>
						                       <tr>
						                       		<td colspan="5">&nbsp;</td>
						                       </tr>
						                    </table>
											
										</div>
					                </div>
                        			     
                        		</td>
                        	</tr>
                        	
                        <% count++; }
                    %>
   
   					<% 
	                    for(int i = count; i < 15; i++) 
	                    { 
	                    	ITinhthunhapDetail ttnDetai = new TinhthunhapDetail();
	                    	
	                    	 maDetai = ttnDetai.getMaDetail();
	                         noidung = ttnDetai.getNoidung();
	                         trongso = ttnDetai.getTrongso();
	                         mucbaohiem = ttnDetai.getMucbaohiem();
	                         thuongSRvuotmuc = ttnDetai.getThuongSRvuotmuc();
	                    	
	                    	%>
	                    	
	                    	<tr>
                        		<td>
                        			<select name="dvkdIds" style="width: 100%" >
                                   		<% if(dvkdRs != null)
                                   		{ 
                                   			dvkdRs.beforeFirst();
                                   			while(dvkdRs.next()) 
                                   			{  %>
                                   				<option value="<%= dvkdRs.getString("pk_seq") %>"><%= dvkdRs.getString("donvi") %></option>
                                   		 <%} }  %>
                                   	</select>
                        			<input type="hidden" style="width: 100%" name = 'trongsoIds' value="" >
                        		</td>
                        		<td>
                        			<select name="kbhIds" style="width: 100%" >
                                   		<option value=""> </option>
                                   		<% if(kbhRs != null)
                                   		{ 
                                   			kbhRs.beforeFirst();
                                   			while(kbhRs.next()) 
                                   			{  %>
                                   				<option value="<%= kbhRs.getString("pk_seq") %>"><%= kbhRs.getString("diengiai") %></option>
                                   		 <%} }  %>
                                   	</select>
                        		</td>
                        		<td align="center">
                        			<a class="apchokhuvuc<%= i %>" href="#">
	           	 							&nbsp; <img alt="Áp cho khu vực" src="../images/vitriluu.png"></a>
	           	 				
		           	 				<div style='display:none'>
				                        <div id='apchokhuvuc<%= i %>' style='padding:0px 5px; background:#fff;'>
				                        	<h4 align="left">Áp dụng cho khu vực</h4>
											
											<table width="450px" align="center">
						                        <tr>
						                            <th width="100px" style="font-size: 12px">Mã KV</th>
						                            <th width="250px" style="font-size: 12px">Khu vực</th>
						                            <th width="100px" style="font-size: 12px" align="center">Chọn</th>
						                        </tr>
				                            	<% if(kvRs != null)
			                                   		{ 
			                                   			kvRs.beforeFirst();
			                                   			while(kvRs.next()) 
			                                   			{  
		                                   				 %> 
		                                   		 			<Tr>
		                                   						<td><input type="text" style="width: 100%" value="<%= kvRs.getString("pk_seq") %>" readonly="readonly" ></td>
		                                   						<td><input type="text" style="width: 100%" value="<%= kvRs.getString("ten") %>" readonly="readonly" ></td>
		                                   						<td align="center">
		                                   							<input  name="khuvuc<%= i %>" type="checkbox" style="width: 100%" value="<%= kvRs.getString("pk_seq") %>" onchange="AjaxNpp(<%=i %>,this)">
		                                   						</td>
		                                   					</Tr>
		                                   				 <%  } }  %>
						                       <tr>
						                       		<td colspan="3">&nbsp;</td>
						                       </tr>
						                    </table>
											
										</div>
					                </div>
                        		</td>
                        		<td>
                        			<select name="chucvu" style="width: 100%"  onchange="ajaxNv(<%=i %>);"  >
                        				<option value=""></option>
                        				<option value="SR">SR</option>
                        				<option value="SS">SS</option>
                        			</select>
                        		</td>
                        		<td align="center">
                       			<a class="apChoNpp<%= i %>" href="#">
           	 							&nbsp; <img alt="Áp cho nhà phân phối" src="../images/vitriluu.png"></a>
           	 				
	           	 				<div style='display:none'>
			                        <div id='apChoNpp<%= i %>' style='padding:0px 5px; background:#fff;'>
			                        	<h4 align="left">Áp dụng cho nhà phân phối</h4>
										
										<table width="800px" align="center" id="NppTable<%=i%>" >
					                        <tr>
					                            <th width="100px" style="font-size: 12px">Mã NPP</th>
					                            <th width="500px" style="font-size: 12px">Tên NPP</th>
					                            <th width="50px" style="font-size: 12px" align="center">
					                             Chọn <input type="checkbox" id="chkAll<%= i %>" onchange="sellectAll(<%= i %>)" >
					                             </th>
					                        </tr>
					                       <tr>
					                       		<td colspan="3">&nbsp;</td>
					                       </tr>
					                    </table>
										
									</div>
				                </div>
                       			
                       		</td>
                        		<td>
                        			<input type="text" style="width: 70%; text-align: right;" name = 'luongcb' value="" onkeyup="DinhdangLuong(this, <%= i %> )" >
                        			
                        			<a class="apchoNhanVien<%= i %>" href="#">
	           	 							&nbsp; <img alt="Áp cho khu vực" src="../images/vitriluu.png"></a>
	           	 				
		           	 				<div style='display:none'>
				                        <div id='apchoNhanVien<%= i %>' style='padding:0px 5px; background:#fff;'>
				                        	<h4 align="left">Áp dụng cho nhân viên</h4>
											
											<div style="max-height: 400px">
												<table width="700px" align="center" cellpadding="0" cellspacing="1" id="nvTable<%=i%>">
							                        <tr>
							                        	<th width="300px" style="font-size: 12px">Nhà phân phối</th>
							                            <th width="100px" style="font-size: 12px">Mã NV</th>
							                            <th width="200px" style="font-size: 12px">Tên nhân viên</th>
							                            <th width="100px" style="font-size: 12px">Lương cơ bản</th>
							                        </tr>
							                       <tr>
							                       		<td colspan="3">&nbsp;</td>
							                       </tr>
							                    </table>
						                    </div>
											
										</div>
					                </div>
                        			
                        		</td>
                        		<td>
                        			<input type="hidden" name="khuvucTen" value="" > 
                        			<input type="text" style="width: 100%; text-align: right;" name = 'ptluongtg' value="45" onkeyup="Dinhdang(this)" >
                        		</td>
                        		<td><input type="text" style="width: 100%; text-align: right;" name = 'ptluonghq' value="55" onkeyup="Dinhdang(this)" ></td>
                        		<td style="display:none; "><input type="text" style="width: 100%; text-align: right;" name = 'baohiemtu' value="0" onkeyup="Dinhdang(this)" ></td>
                        		<td><input type="text" style="width: 100%; text-align: right;" name = 'baohiemden' value="0" onkeyup="Dinhdang(this)" ></td>
                        		<td><input type="text" style="width: 100%; text-align: right;" name = 'baohiem' value="0" ></td>
                        		<td><input type="text" style="width: 100%; text-align: right;" name = 'congdoan' value="0" ></td>
                        		<td><input type="text" style="width: 100%; text-align: right;" name = 'tdnc' value="20" onkeyup="Dinhdang(this)" ></td>
                        		
                        		<td align="center" style="display: none">
                        			
                        			<a class="thuongvuotmuc<%= i %>" href="#">
	           	 							&nbsp; <img alt="Khai báo tiêu chí" src="../images/vitriluu.png"></a>
	           	 				
		           	 				<div style='display:none'>
				                        <div id='thuongvuotmuc<%= i %>' style='padding:0px 5px; background:#fff;'>
				                        	<h4 align="left">Khai báo thưởng vượt mức</h4>
											
											<table width="650px" align="center">
						                        <tr>
						                            <th width="250px" style="font-size: 12px">Nhóm thưởng </th>
						                            <th width="100px" align="center" style="font-size: 12px">Từ mức</th>
						                            <th width="100px" align="center" style="font-size: 12px">Đến mức</th>
						                            <th width="100px" align="center" style="font-size: 12px">T. vượt mức</th>
						                        </tr>
				                            	
						                       <% for(int pos = 0; pos <= 15; pos ++ ) { %>
						                       
						                       		<tr>
							                            <td><input type="text" value="" name="tvmNhomthuong<%= i %>" style="width: 100%"
							                            			 onkeyup="ajax_showOptions(this,'sanpham',event)"	 ></td>
							                            <td><input type="text" style="text-align: right; width: 100%" value="" name="tvmTumuc<%= i %>"></td>
							                            <td><input type="text" style="text-align: right; width: 100%" value="" name="tvmDenmuc<%= i %>"></td>
							                            <td><input type="text" style="text-align: right; width: 100%" value="" name="tvmThuong<%= i %>"></td>
							                        </tr>
						                       		
						                       <% } %>
						                       
						                       <tr>
						                       		<td colspan="5">&nbsp;</td>
						                       </tr>
						                    </table>
											
										</div>
					                </div>
                        			     
                        		</td>
                        		
                        		<td align="center">
                        				
                        		<a class="tieuchidanhgia<%= i %>" href="#">
	           	 							&nbsp; <img alt="Khai báo tiêu chí" src="../images/vitriluu.png"></a>
	           	 				
	           	 				<div style='display:none'>
			                        <div id='tieuchidanhgia<%= i  %>' style='padding:0px 5px; background:#fff;'>
			                        	<h4 align="left">Khai báo tiêu chí tính lương</h4>
										
										<table width="650px" align="center">
					                        <tr>
					                            <th width="100px" style="font-size: 12px">Mã </th>
					                            <th width="250px" style="font-size: 12px">Nội dung</th>
					                            <th width="100px" align="center" style="font-size: 12px">Trọng số</th>
					                            <th width="100px" align="center" style="font-size: 12px">Mức bảo hiểm</th>
					                            <th width="100px" align="center" style="font-size: 12px; display: none">T. vượt mức</th>
					                        </tr>
			                            	<% for(int pos = 0; pos < 4; pos++) { %>
				                            	<tr>
						                            <td><input type="text" value="<%= maDetai[pos] %>" name="maDetail<%= i %>" style="width: 100%" readonly="readonly"></td>
						                            <td><input type="text" value="<%= noidung[pos] %>" name="noidung<%= i %>" style="width: 100%"
						                            			 onkeyup="ajax_showOptions(this,'sanpham',event)"	 ></td>
						                            <td><input type="text" style="text-align: right; width: 100%" value="<%= trongso[pos] %>" name="trongso<%= i %>"></td>
						                             <td><input type="text" style="text-align: right; width: 100%" value="<%= mucbaohiem[pos] %>" name="mucbaohiem<%= i %>"></td>
						                            <td style="display: none"><input type="text" style="text-align: right; width: 100%" value="<%= thuongSRvuotmuc[pos] %>" name="thuongvuotmuc<%= i %>"></td>
						                        </tr>
					                       <% } %>
					                       <tr>
					                       		<td colspan="5">&nbsp;</td>
					                       </tr>
					                    </table>
  
									</div>
				                </div>
		                         
						            
						                
                        		</td>
                        	</tr>
	                    	
	                    <% }  %>
   
                       <TR>
                        <TD align="center" colspan="14" class="tbfooter">&nbsp;</TD>
                    </TR>
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
<%}%>
