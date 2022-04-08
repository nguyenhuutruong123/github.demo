<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="geso.dms.center.beans.duyettrakhuyenmai.imp.*" %>
<%@page import="geso.dms.center.beans.duyettrakhuyenmai.*" %>
<%@page import="java.util.Calendar" %>
<%@page import="java.util.Date" %>
<%@page import="java.util.List" %>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%@page import="java.sql.SQLException" %>
<%@ page  import = "java.text.DateFormat" %>
<%@ page  import = "java.text.SimpleDateFormat" %>
<%@ page import = "java.text.DecimalFormat" %>
<%@ page import = "java.text.NumberFormat" %>

<%@ page import = "com.google.gson.JsonArray" %>
<%@ page import = "com.google.gson.JsonObject" %>
<%@ page import = "com.google.gson.JsonParser" %>



<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/SOHACO/index.jsp");
	}else{ %>

<%
 	IDuyettrakhuyenmai obj =(IDuyettrakhuyenmai)session.getAttribute("tctskuBean");
	
	ResultSet ctkmRs = obj.getCtkmRs();
	//ResultSet khRs = obj.getKhachhangRs();
	//String[] nppId = obj.getNppId();
	String[] nppTen = obj.getNppTen();
	String[] khId = obj.getKhId();
	String[] khMa = obj.getKhMa();
	String[] khTen = obj.getKhTen();
	String[] doanhso = obj.getDoanhso();
	String[] mucdk = obj.getMucDk();
	String[] mucthuong = obj.getMucthuong();
	String[] mucduyet = obj.getSoxuat();
	String[] tongtien = obj.getTongtien();
	String[] sanpham = obj.getSanpham();
	String[] ddkdTen = obj.getddkdTen();
	String[] tinhthanhTen = obj.gettinhthanhTen();
	
	NumberFormat format = new DecimalFormat("#,###,###");
	
%>

<% String nnId = (String)session.getAttribute("nnId"); %>
<% if(nnId == null) {
 nnId = "vi";  
 }
 String url = util.getChuyenNguUrl("DuyettrakhuyenmaiSvl","" ,nnId);
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


<SCRIPT language="JavaScript" type="text/javascript">
	function submitform()
	{
	    document.forms["tctsku"].submit();
	}
	function newform()
	{
		document.forms["tctsku"].action.value = "newform";
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
	  document.forms["tctsku"].action.value = "save";
	  document.forms["tctsku"].submit(); 
  	}
	function excel()
	{
		document.forms["tctsku"].action.value = "excel";
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
	function upload()
	{
		document.forms['tctsku'].action.value='upload';
		document.forms["tctsku"].setAttribute('enctype', "multipart/form-data", 0);
	    document.forms["tctsku"].submit();
	  
	}

</SCRIPT>

<script>
$( document ).ready( function()
		{
		    var targets = $( '[rel~=tooltip]' ),
		        target  = false,
		        tooltip = false,
		        title   = false;
		  
		    targets.bind( 'mouseenter', function()
		    {
		        target  = $( this );
		        tip     = target.attr( 'title' );
		        tooltip = $( '<div id="tooltip"></div>' );
		  
		        if( !tip || tip == '' )
		            return false;
		  
		        target.removeAttr( 'title' );
		        tooltip.css( 'opacity', 0 )
		               .html( tip )
		               .appendTo( 'body' );
		  
		        var init_tooltip = function()
		        {
		            if( $( window ).width() < tooltip.outerWidth() * 1.5 )
		                tooltip.css( 'max-width', $( window ).width() / 2 );
		            else
		                tooltip.css( 'max-width', 340 );
		  
		            var pos_left = target.offset().left + ( target.outerWidth() / 2 ) - ( tooltip.outerWidth() / 2 ),
		                pos_top  = target.offset().top - tooltip.outerHeight() - 20;
		  
		            if( pos_left < 0 )
		            {
		                pos_left = target.offset().left + target.outerWidth() / 2 - 20;
		                tooltip.addClass( 'left' );
		            }
		            else
		                tooltip.removeClass( 'left' );
		  
		            if( pos_left + tooltip.outerWidth() > $( window ).width() )
		            {
		                pos_left = target.offset().left - tooltip.outerWidth() + target.outerWidth() / 2 + 20;
		                tooltip.addClass( 'right' );
		            }
		            else
		                tooltip.removeClass( 'right' );
		  
		            if( pos_top < 0 )
		            {
		                var pos_top  = target.offset().top + target.outerHeight();
		                tooltip.addClass( 'top' );
		            }
		            else
		                tooltip.removeClass( 'top' );
		  
		            tooltip.css( { left: pos_left, top: pos_top } )
		                   .animate( { top: '+=10', opacity: 1 }, 50 );
		        };
		  
		        init_tooltip();
		        $( window ).resize( init_tooltip );
		  
		        var remove_tooltip = function()
		        {
		            tooltip.animate( { top: '-=10', opacity: 0 }, 50, function()
		            {
		                $( this ).remove();
		            });
		  
		            target.attr( 'title', tip );
		        };
		  
		        target.bind( 'mouseleave', remove_tooltip );
		        tooltip.bind( 'click', remove_tooltip );
		    });
		});
</script>


<style type="text/css">

#tooltip
 		{
 			font-family: Ubuntu, sans-serif;
 			font-size: 1.3em;
 			text-align: left;
 			text-shadow: 0 1px rgba( 0, 0, 0, .5 );
 			line-height: 1.5;
 			color: #fff;
 			background: #333;
 			background: -webkit-gradient( linear, left top, left bottom, from( rgba( 0, 0, 0, .6 ) ), to( rgba( 0, 0, 0, .8 ) ) );
 			background: -webkit-linear-gradient( top, rgba( 0, 0, 0, .6 ), rgba( 0, 0, 0, .8 ) );
 			background: -moz-linear-gradient( top, rgba( 0, 0, 0, .6 ), rgba( 0, 0, 0, .8 ) );
 			background: -ms-radial-gradient( top, rgba( 0, 0, 0, .6 ), rgba( 0, 0, 0, .8 ) );
 			background: -o-linear-gradient( top, rgba( 0, 0, 0, .6 ), rgba( 0, 0, 0, .8 ) );
 			background: linear-gradient( top, rgba( 0, 0, 0, .6 ), rgba( 0, 0, 0, .8 ) );
 			-webkit-border-radius: 5px;
 			-moz-border-radius: 5px;
 			border-radius: 5px;
 			border-top: 1px solid #fff;
 			-webkit-box-shadow: 0 3px 5px rgba( 0, 0, 0, .3 );
 			-moz-box-shadow: 0 3px 5px rgba( 0, 0, 0, .3 );
 			box-shadow: 0 3px 5px rgba( 0, 0, 0, .3 );
 			position: absolute;
 			z-index: 100;
 			padding: 15px;
 		}

 			#tooltip:after
 			{
 		        width: 0;
 		        height: 0;
 		        border-left: 10px solid transparent;
 		        border-right: 10px solid transparent;
 		        border-top-color: #333;
 				border-top: 10px solid rgba( 0, 0, 0, .7 );
 				content: '';
 				position: absolute;
 				left: 50%;
 				bottom: -10px;
 				margin-left: -10px;
 			}

 				#tooltip.top:after
 				{
 			        border-top-color: transparent;
 			        border-bottom-color: #333;
 					border-bottom: 10px solid rgba( 0, 0, 0, .6 );
 					top: -20px;
 					bottom: auto;
 				}

 				#tooltip.left:after
 				{
 					left: 20px;
 					margin: 0;
 				}

 				#tooltip.right:after
 				{
 					right: 10px;
 					left: auto;
 					margin: 0;
 				}	

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
	/* text-shadow: 1px 1px 0px #fff; */
	background:#eaebec;
	margin:5px;
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
/*table.chitieutable th:first-child{
	text-align: left;
	padding-left:20px;
}*/
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
</style>

</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name ="tctsku" method="post" action="../../DuyettrakhuyenmaiUpdateSvl" >
<input type="hidden" name="userId" value='<%= userId %>' >
<input type="hidden" name="id" value="<%=obj.getId()%>">
<input type="hidden" name="action" value="0">
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý khuyến mại > Tích lũy > Duyệt trả tích lũy > Hiển thị</TD> 
							 <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%= userTen %></TD></tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
			<TR ><TD >
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR class = "tbdarkrow">
							<TD width="30" align="left"><A href="../../DuyettrakhuyenmaiSvl?userId=<%=userId%>" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
						    <TD width="2" align="left" ></TD>
						    <TD width="30" align="left" >
						   
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
						<LEGEND class="legendtitle" style="color:black">Duyệt trả khuyến mại tích lũy </LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
							<TR>
											<TD width="120px" class="plainlabel"><%=ChuyenNgu.get("Ngày duyệt",nnId) %> <FONT
												class="erroralert"> *</FONT></TD>
											<TD class="plainlabel"><input
												name="ngayduyet" class="days"
												value="<%=obj.getNgayduyet()%>" readonly="readonly">
											</TD>
											<TD class="plainlabel"><%=ChuyenNgu.get("Diễn giải",nnId) %></TD>
											<TD class="plainlabel" ><input type="text"
												name="diengiai" id="diengiai" style="width: 400px;"
												value="<%=obj.getDiengiai()%>"></TD>
										</TR>
										
										<TR>
											<TD width="120px" class="plainlabel"><%=ChuyenNgu.get("Từ ngày tính Doanh số",nnId) %> <FONT
												class="erroralert"> *</FONT></TD>
											<TD class="plainlabel"><input
												name="tungay_ds" class="days"
												value="<%=obj.getTungay_ds()%>" readonly="readonly">
											</TD>
											
											<TD width="120px" class="plainlabel"><%=ChuyenNgu.get("Đến ngày tính Doanh số",nnId) %> <FONT
												class="erroralert"> *</FONT></TD>
											<TD class="plainlabel"><input
												name="denngay_ds" class="days"
												value="<%=obj.getDenngay_ds()%>" readonly="readonly">
											</TD>
											
										</TR>
						  
						  <tr>
						  	<TD class="plainlabel">Scheme <FONT class="erroralert"> *</FONT></TD>
						  	  	<TD class="plainlabel" colspan="5">
						  	  		<select name="ctkmId" style= "width: 400px;">
						  	  			<option value=""></option>
						  	  			<% if(ctkmRs != null) { 
						  	  				while(ctkmRs.next())
						  	  				{
						  	  					if(ctkmRs.getString("ctkmId").equals(obj.getCtkmId()))
						  	  					{
						  	  						%>
						  	  						<option value="<%= ctkmRs.getString("ctkmId") %>" selected="selected" ><%= ctkmRs.getString("Scheme") %></option>
						  	  					<% } else { %>
						  	  						<option value="<%= ctkmRs.getString("ctkmId") %>"  ><%= ctkmRs.getString("Scheme") %></option>
						  	  					<% }
						  	  				}
						  	  				ctkmRs.close();
						  	  			} %>
						  	  		</select>
						  	  	</TD>
						  </tr>
						  
						  
						  
						  
						  
						 
							  
						  <TR>
						  	  	<td class="plainlabel" colspan="6">
	    						<a class="button3"  onclick="excel()">
	    							<img style="top: -4px; width: 30px" src="../images/excel.gif" alt="">Xuất Excel</a>
	    						</td>
						  </TR>
						  
						</TABLE>
					
						
						
						<TABLE class="chitieutable">
											<TR class="tbheader">
												<TH align="center" width="20%"><%=ChuyenNgu.get("Mã",nnId) %></TH>
												<TH align="center" width="50%"><%=ChuyenNgu.get("Diễn giải",nnId) %></TH>
												<TH align="center" width="30%"><%=ChuyenNgu.get("Ngày đăng ký",nnId) %></TH>
												<TH align="center" width="10%"><%=ChuyenNgu.get("Chọn",nnId) %></TH>
											</TR>
	
											<%
												if (obj.getDangkyTichluyRs() != null) {
														
														while ( obj.getDangkyTichluyRs().next()  ) {
															int flag = obj.getDangkyTichluyRs().getInt("flagg");
															String cl = "";
															if(flag > 0) cl = "color: red;";
															
											%>
	
											<TR >
												
											
												
												<TD  >
													<input type="text" value="<%=obj.getDangkyTichluyRs().getString("pk_seq") %>" style="width: 100%; text-align: center;<%=cl %>" readonly="readonly">
												</TD>
												<TD >
													<input type="text" value="<%=obj.getDangkyTichluyRs().getString("DienGiai") %>" style="width: 100%; text-align: center;<%=cl %>" readonly="readonly">
												</TD>
												<TD >
													<input type="text" value="<%=obj.getDangkyTichluyRs().getString("NgayDangKy") %>" style="width: 100%; text-align: center;<%=cl %>" readonly="readonly">
												</TD>											
												<TD >
													<input   value="<%=obj.getDangkyTichluyRs().getString("pk_seq") %>"  type="checkbox" name="dangkyIds"  <%= obj.getDangkyIds().contains(obj.getDangkyTichluyRs().getString("pk_seq") ) ? "checked" :""   %>     >
												</TD>
	
											</TR>
	
											<%
												}
													}
											%>
																	
									</TABLE>
					
						<TABLE class = "chitieutable" style="display: none">
		                   
							<TR class="tbheader">
								<TH align="center" width="5%" >STT</TH>
								<TH align="center" width="10%" >Mã KH</TH>
		                        <TH align="center" width="20%" >Khách hàng</TH>
		                        <TH align="center" width="15%" >Nhà phân phối</TH>
		                        <TH align="center" width="10%" >Doanh số</TH>
		                        <TH align="center" width="7%" >SL mua</TH>
		                        <TH align="center" width="7%" >Mức đk</TH>
		                        <TH align="center" width="7%" >Mức đạt</TH>
		                        <TH align="center" width="7%" >Mức duyệt</TH>
		                        <TH align="center" width="10%" >Tổng thưởng/Tổng lượng</TH>
		                      
		                    </TR>
									                    
		                    <%
		                    	if(obj.getKhDuyetRs() != null)
		                    	{
		                    		String npp_Ten = "";
		                    		int i = 0;
		                    		while(obj.getKhDuyetRs().next())
		                    		{
		                    			
		                    			
		                    			
		                    			%>
		                    			
		                    			<TR>
		                    				<td class="plainlabel" style="text-align: center;"><%=i+1 %></td>
		                    				<TD>
		                    					<input type="text" name="khMa" style="width:100%; border:none; background: transparent;" value="<%=obj.getKhDuyetRs().getString("MAFAST") %>" style="width: 100%" readonly="readonly" >
		                    				</TD>
		                    				<TD>
		                    					<input type="hidden" name="khId" style="width:100%; border:none; background: transparent;" 
		                    							value="<%=  obj.getKhDuyetRs().getString("KHACHHANG_FK")  %>" style="width: 100%" >
		                    					<input type="text" name="khTen" title="<%= obj.getKhDuyetRs().getString("KHTEN") %>" rel="tooltip"  style="width:100%; border:none; background: transparent;" 
		                    							value="<%= obj.getKhDuyetRs().getString("KHTEN") %>" style="width: 100%" readonly="readonly" >
		                    				</TD>
		                    				<TD>
		                    					
	                    						<input title="<%= obj.getKhDuyetRs().getString("NPP") %>" rel="tooltip" 	type="text" name="nppTen" style="width:100%; border:none; background: transparent;" 
	                    								value="<%= obj.getKhDuyetRs().getString("NPP") %>" style="width: 100%" readonly="readonly"  >

		                    				</TD>
		                    				<TD>
		                    					<input type="text" name="doanhso" style="width:100%; border:none; background: transparent;" 
		                    							value="<%= format.format(obj.getKhDuyetRs().getDouble("DOANHSO")) %>" style="width: 100%; text-align: right;" readonly="readonly" >
		                    				</TD>
		                    			
		                    				<TD>
		                    					<input type="text" name="soluongmua" style="width:100%; border:none; background: transparent;" 
		                    						value="<%= format.format(obj.getKhDuyetRs().getDouble("SoLuongMua"))  %>" style="width: 100%; text-align: right;" readonly="readonly" >
		                    				</TD>
		                    				<TD>
		                    					<input type="hidden" name="mucdk"  value="<%= mucdk[i] %>">
		                    					<input type="text" name="mucthuong" style="width:100%; border:none; background: transparent;" value="<%= format.format(obj.getKhDuyetRs().getDouble("MUCDANGKY"))  %>" style="width: 100%; text-align: center;" readonly="readonly" >
		                    				 
		                    				</TD>
		                    				<TD>
		                    					<input type="text" name="mucdat" style="width:100%; border:none; background: transparent;" value="<%= format.format(obj.getKhDuyetRs().getDouble("MUCDAT")) %>" style="width: 100%; text-align: center;" readonly="readonly" >
		                    				</TD>
		                    				<TD>
		                    					<input type="text" name="mucduyet" style="width:100%; border:none; background: transparent;" value="<%= format.format(obj.getKhDuyetRs().getDouble("MUCDUYET")) %>" style="width: 100%; text-align: center;" readonly="readonly" >
		                    				</TD>
		                    				<TD>
		                    					<input type="text" name="tongthuong" style="width:100%; border:none; background: transparent;" value="<%= format.format(obj.getKhDuyetRs().getDouble("TONGTIEN")) %>" style="width: 100%; text-align: right;" readonly="readonly" >
		                    				</TD>
		                    				
		                    			</TR>
		                    			
		                    		<%  
		                    			if(obj.getKhDuyetRs().getString("jsonSP")  != null )
		                    			{
		                    				System.out.println("   [=]" +obj.getKhDuyetRs().getString("jsonSP"));
		                    				System.out.println("Json string: "+obj.getKhDuyetRs().getString("jsonSP"));
		                    				JsonArray jsonArray = (JsonArray) new JsonParser().parse(obj.getKhDuyetRs().getString("jsonSP"));
		                    				for(int xx=0; xx < jsonArray.size(); xx++ )
		                    				{
		                    					JsonObject rec = (JsonObject) jsonArray.get(xx);
		                    				
		                    					String spMa= rec.get("spMa").getAsString();
		                    					String spTen= rec.get("spTen").getAsString();
		                    					Double soluong = rec.get("SoLuong").getAsDouble();
		                    				%>
			                    				<TR>
			                    					<td   colspan="3" > Sản phẩm </td>
			                    					<td   colspan="1" ><%=spMa %> </td>
			                    					<td 	colspan="5" ><%=spTen %></td>
			                    					<td   colspan="5" ><%= soluong > 0 ? format.format(soluong) : ""  %></td>
			                    				</TR>
			                    				
		                    				<%
		                    				}
		                    			}
		                    		
		                    		
		                    		i++;
		                    		%>
		                    			<TR >
		                    				<td style="background: buttonhighlight ; height:10px "  colspan="10" ></td>
		                    			</TR>
		                    		
		                    		<%
		                    		
		                    		}
		                    	}
		                    %>
		                    
		                   
						</TABLE>
									
						</FIELDSET>						
					</TD>
				</TR>
			</TABLE>
		</TD>
	</TR>
	</TABLE>
</form>

</body>  <!-- <script type='text/javascript' src='../scripts/loadingv2.js'></script> -->
</HTML>
<%
if(ctkmRs != null) ctkmRs.close();
obj.DbClose();
} %>
