<%@page import="geso.dms.center.beans.stockintransit.IStockintransit"%>
<%@page import="java.sql.ResultSet"%>

<%@page import="java.util.Calendar"%>
<%@page import="java.sql.Date"%>
<%@ page  import = "geso.dms.center.util.*" %>
<%@ page  import = "geso.dms.center.util.ChuyenNgu" %>


<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");
	IStockintransit obj = (IStockintransit)session.getAttribute("obj");
	ResultSet npp = obj.getnpp();
	String nppId = obj.getnppId();
	ResultSet vung = obj.getvung();
	ResultSet cttb = obj.getcttbRs();
	ResultSet khuvuc = obj.getkhuvuc();
	ResultSet ddkd = obj.getRsddkd();
	Utility util = (Utility) session.getAttribute("util");
	ResultSet anhtrungbayRs = obj.getAnhtrungbayRs();


%>
<% String nnId = (String)session.getAttribute("nnId"); %> 
<% if(nnId == null) {
 nnId = "vi"; 
 }
String url = util.getChuyenNguUrl("BCAnhTrungBay","",nnId);	
 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<TITLE>SalesUp - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">

<script type="text/javascript" src="../scripts/jquery-1.js"></script>

<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />

<script type="text/javascript"	src="../scripts/jquery.min.1.7.js"></script>
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
            $(".button").hover(function(){
                $(".button img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        }); 
		$(document).ready(function(){
            $(".button1").hover(function(){
                $(".button1 img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        });
		
</script>

<!-- Khai bao su dung colorbox ajax -->
<link media="screen" rel="stylesheet" href="../css/colorbox.css">
<script src="../scripts/colorBox/jquery.colorbox.js"></script>

    
    
<script type="text/javascript">


	function submitform() 
	{
		
		document.forms['frm'].action.value= 'submit';
		document.forms["frm"].submit();
	}
	
	function thongbao()
	{
		if(document.getElementById("msg").value != '')
			alert(document.getElementById("msg").value);
	}
	
	function search()
	{
		if (document.getElementById("Sdays").value == "") 
		{
			alert("Vui lòng chọn ngày bắt đầu");
			return ;
		}
		if (document.getElementById("Edays").value == "")
		{
			alert("Vui lòng chọn ngày kết thúc");
			return ;
		}		
	
		document.forms['frm'].action.value= 'search';
		document.forms["frm"].submit();
	}
	
	function excel()
	{
		if (document.getElementById("Sdays").value == "") 
		{
			alert("Vui lòng chọn ngày bắt đầu");
			return ;
		}
		if (document.getElementById("Edays").value == "")
		{
			alert("Vui lòng chọn ngày kết thúc");
			return ;
		}		
	
		document.forms['frm'].action.value= 'excel';
		document.forms["frm"].submit();
	}
	
</script>
<link href="../css/select2.css" rel="stylesheet" />
	<script src="../scripts/select2.js"></script>
	<script>
    	$(document).ready(function() { 
    		$("select:not(.notuseselect2)").select2({ width: 'resolve' });     
    	});
    </script>	
    
<style>
.Text{
        font-family: Verdana, Arial, Sans-serif, 'Times New Roman';
        font-size: 8pt;
        font-weight: normal;
        font-style: normal;
        color: #333333;
        text-decoration: none;
}
.toolTip {
        font-family: Verdana, Arial, Sans-serif, 'Times New Roman';
        font-size: 8pt;
        filter:alpha(opacity=80);
        -moz-opacity: 0.8;
        opacity: 0.8;
        /* comment the above 3 line if you don't want transparency*/
}

</style>
<script language="javascript" defer="false">
//browser detection
    var agt=navigator.userAgent.toLowerCase();
    var is_major = parseInt(navigator.appVersion);
    var is_minor = parseFloat(navigator.appVersion);

    var is_nav  = ((agt.indexOf('mozilla')!=-1) && (agt.indexOf('spoofer')==-1)
                && (agt.indexOf('compatible') == -1) && (agt.indexOf('opera')==-1)
                && (agt.indexOf('webtv')==-1) && (agt.indexOf('hotjava')==-1));
    var is_nav4 = (is_nav && (is_major == 4));
    var is_nav6 = (is_nav && (is_major == 5));
    var is_nav6up = (is_nav && (is_major >= 5));
    var is_ie     = ((agt.indexOf("msie") != -1) && (agt.indexOf("opera") == -1));
</script>
<script language="JavaScript">
//tooltip Position
var offsetX = -165;
var offsetY = -165;
var opacity = 200;
var toolTipSTYLE;

function initToolTips(){
  if(document.getElementById){
          toolTipSTYLE = document.getElementById("toolTipLayer").style;
  }
  if(is_ie || is_nav6up)
  {
    toolTipSTYLE.visibility = "visible";
    toolTipSTYLE.display = "none";
    document.onmousemove = moveToMousePos;
  }
}
function moveToMousePos(e)
{
  if(!is_ie){
    x = e.pageX;
    y = e.pageY;
  }else{
    x = event.x + document.body.scrollLeft;
    y = event.y + document.body.scrollTop;
  }

  toolTipSTYLE.left = x + offsetX+'px';
  toolTipSTYLE.top = y + offsetY+'px';
  return true;
}


function toolTip(msg, fg, bg)
{
  if(toolTip.arguments.length < 1) // if no arguments are passed then hide the tootip
  {
    if(is_nav4)
        toolTipSTYLE.visibility = "hidden";
    else
        toolTipSTYLE.display = "none";
  }
  else // show
  {
    if(!fg) fg = "#777777";
    if(!bg) bg = "#ffffe5";
    var content = '<table border="0" cellspacing="0" cellpadding="0" class="toolTip"><tr><td bgcolor="' + fg + '">' +
                                  '<table border="0" cellspacing="1" cellpadding="0"<tr><td bgcolor="' + bg + '">'+
                                  '<font face="sans-serif" color="' + fg + '" size="-2">' + msg +
                                  '</font></td></tr></table>'+
                                  '</td></tr></table>';
   if(is_nav4)
    {
      toolTipSTYLE.document.write(content);
      toolTipSTYLE.document.close();
      toolTipSTYLE.visibility = "visible";
    }

    else if(is_ie || is_nav6up)
    {
      document.getElementById("toolTipLayer").innerHTML = content;
      toolTipSTYLE.display='block'
    }
  }
}


	function show(pos,data)
	{
		if(pos<3)
		{
			offsetX = -165;
			offsetY = 5;	
		}
		else
		{
			offsetX = -165;
			offsetY = -165;
		}
	    s = '<table width="100%" cellspacing="2" cellpadding="0" border="0">';
	    s += '<tr><td><img src="'+data+'" height="500" width="400" border="0" /> </td></tr>'; 
	  //  s += '<tr><td colspan="2" class="Text"><hr/>this is a test for simple tooltip. <br/>You can add text and images to the tooltip</td></tr>';
	    s += '</table>';
	    
	    toolTip(s);
	 	
	}

</script>      
    

   

<BODY onload="initToolTips();" leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<!-- BEGIN RENDER AUTO -->
<%if(!geso.dms.center.util.GlobalValue.getDevmode()){ %>
	<noscript> 
	 	<meta http-equiv="refresh" content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")  %>">
		<!-- //window.location = "https://www.google.com"; -->
	</noscript>
<%} %>
<!-- END RENDER AUTO -->

<div id="toolTipLayer" style="position:absolute; visibility: hidden;left:0;right:0"></div>
	<form name="frm" method="post" action="../../BCAnhTrungBay">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
	<input type="hidden" name="action" value='1'>
	<input type="hidden" name="view" value='TT'>
	<input type="hidden" name="userId" value='<%=userId%>'>
	
<input type="hidden" name="msg" id="msg" value='<%= obj.getMsg() %>'>
<script type="text/javascript">
	thongbao();
</script>
	
		<div id="main" style="width: 99%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 70%; padding: 5px; float: left"
					class="tbnavigation"><%=" "+url %></div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					<%=ChuyenNgu.get("Chào mừng bạn",nnId) %> <%= userTen %></div>
			</div>

			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>

					<legend class="legendtitle"><%=ChuyenNgu.get(" Báo lỗi nhập liệu",nnId) %></legend>
					<textarea id="errors" value="<%= session.getAttribute("errors") %>" name="errors" rows="1" style="width: 100% ; color:#F00 ; font-weight:bold"></textarea>
				</fieldset>
			</div>
			
			<div align="left"
				style="width: 100%; float: none; clear: left; font-size: 0.7em">
				<fieldset>
					<legend class="legendtitle"><%=ChuyenNgu.get("Báo cáo ảnh cửa hàng",nnId) %></legend>
					<div style="width: 100%; float: none" align="left" class="plainlabel">
							<TABLE width="70%" cellpadding="6" cellspacing="0">
								<TR>
										<TD class="plainlabel"><%=ChuyenNgu.get("Từ ngày",nnId) %> </TD>
										<TD class="plainlabel">	<input type="text" name="Sdays" id="Sdays" class="days" value='<%= obj.gettungay() %>'  />
											</TD>
										<TD class="plainlabel"><%=ChuyenNgu.get("Đến ngày",nnId) %> </TD>
										<TD class="plainlabel">
												<input type="text" name="Edays" id="Edays" class="days" value='<%= obj.getdenngay() %>'/>
											</TD>
									</TR>
									<TR>
									<TD class="plainlabel"><%=ChuyenNgu.get("Vùng",nnId) %></TD>
									<TD class="plainlabel">
										<select name="vungId" id="vungId" onchange="submitform();" style="width: 300px">
											<option value="" selected>All</option>
											<%if (vung != null)
													while (vung.next()) {
														if (vung.getString("pk_seq").equals(obj.getvungId())) {%>
													<option value="<%=vung.getString("pk_seq")%>" selected><%=vung.getString("ten")%></option>
												<%} else {%>
													<option value="<%=vung.getString("pk_seq")%>"><%=vung.getString("ten")%></option>
											<%}}%>
										</select>
									</TD>
									
									<TD class="plainlabel"><%=ChuyenNgu.get("Khu Vực",nnId) %></TD>
									<TD class="plainlabel">
										<select name="khuvucId" id="khuvucId" onchange="submitform();" style="width: 300px">
											<option value="" selected>All</option>
											<%if (khuvuc != null)
													while (khuvuc.next()) {
														if (khuvuc.getString("pk_seq").equals(obj.getkhuvucId())) {%>
															<option value="<%=khuvuc.getString("pk_seq")%>" selected><%=khuvuc.getString("ten")%></option>
													<%} else {%>
														<option value="<%=khuvuc.getString("pk_seq")%>"><%=khuvuc.getString("ten")%></option>
													<%}}%>
										</select>
									</TD>
								</TR>
								<TR>
									<TD class="plainlabel"><%=ChuyenNgu.get("TTPP/NPP",nnId) %></TD>
									<TD class="plainlabel" > 
										<select name="nppId" onchange="submitform();" id="nppId" style="width: 300px">
											<option value="" selected>All</option>
											<%if (npp != null)
													while (npp.next()) {
														if (npp.getString("pk_seq").equals(obj.getnppId())) {%>
															<option value="<%=npp.getString("pk_seq")%>" selected><%=npp.getString("ten")%></option>
													<%} else {%>
														<option value="<%=npp.getString("pk_seq")%>"><%=npp.getString("ten")%></option>
													<%}}%>
										</select>
									</TD>
									<TD class="plainlabel"><%=ChuyenNgu.get("Nhân viên bán hàng",nnId) %></TD>
									<TD class="plainlabel">
										<select name="ddkdId" id="ddkdId"	onchange="submitform();" style="width: 300px">
											<option value="" selected>All</option>
											<%if (ddkd != null)
													while (ddkd.next()) {
														if (ddkd.getString("pk_seq").equals(obj.getDdkd())) {%>
														<option value="<%=ddkd.getString("pk_seq")%>" selected><%=ddkd.getString("ten")%></option>
													<%} else {%>
														<option value="<%=ddkd.getString("pk_seq")%>"><%=ddkd.getString("ten")%></option>
													<%}}%>
										</select>
									</TD>
									
									
									
									
								</TR>
								<TR>
								<TD class="plainlabel"><%=ChuyenNgu.get("Chọn Chương Trình",nnId) %></TD>
									<TD class="plainlabel">
										<select name="cttbId" id="cttbId" onchange="submitform();" style="width: 300px">
											<option value="" selected>All</option>
											<%if (cttb != null)
													while (cttb.next()) {
														if (cttb.getString("pk_seq").equals(obj.getcttbId())) {%>
													<option value="<%=cttb.getString("pk_seq")%>" selected><%=cttb.getString("scheme")%></option>
												<%} else {%>
													<option value="<%=cttb.getString("pk_seq")%>"><%=cttb.getString("scheme")%></option>
											<%}}%>
										</select>
									</TD>
								</TR>
								
							
								<TR>
									<td colspan="4">
										<a class="button" href="javascript:search();"> 
											<img style="top: -4px;" src="../images/button.png" alt=""><%=ChuyenNgu.get("Tạo báo cáo",nnId) %> 
										</a>
										&nbsp;&nbsp;&nbsp;&nbsp;
										<a class="button" href="javascript:excel();"> 
											<img style="top: -4px; height: 20px" src="../images/excel.gif" alt=""><%=ChuyenNgu.get("Xuất Excel",nnId) %> </a>
									</td>
								</TR>
							</TABLE>
						</div>
						
					<TABLE width="100%" border="0" cellspacing="0" cellpadding="2">
					<TR>
						<TD width="100%">
							<TABLE class="" width="100%">
								<TR>
									<TD width="98%">
										<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
											<TR class="tbheader">
												<TH width="4%"><%=ChuyenNgu.get("STT",nnId) %></TH>
												
												<TH width="15%" align="center"><%=ChuyenNgu.get("NPP (KH)",nnId) %></TH>
												<TH width="15%" align="center"><%=ChuyenNgu.get("Nhân viên bán hàng",nnId) %></TH>
												<TH width="5%" align="center"><%=ChuyenNgu.get("Mã KH",nnId) %></TH>
												<TH width="15%" align="center"><%=ChuyenNgu.get("Khách hàng",nnId) %></TH>
											<!-- 	<TH width="15%" align="center">Mua hàng</TH> -->
												<TH width="5%" align="center"><%=ChuyenNgu.get("Ảnh",nnId) %></TH>

												
												
											</TR>
										
							<%  														
		                        int m = 0;
		                        String lightrow = "tblightrow";
		                        String darkrow = "tbdarkrow";
								if(anhtrungbayRs !=null)
								{%>
								<% try{								
		                               while (anhtrungbayRs.next()){
		                            		String nut = "#nutmopopup" + Integer.toString(m+1);
		                            		String popup = "#noidungpopup" + Integer.toString(m+1);
		                            		String nut_id = "nutmopopup" + Integer.toString(m+1);
		                            		String popup_id = "noidungpopup" + Integer.toString(m+1);
		                            		
		                                  	if (m % 2 != 0) {%>                     
		                                   	<TR class= <%=lightrow%> >
		                                   <%} else {%>
		                                      	<TR class= <%= darkrow%> >
		                                   	<%}%>
		                                   		
		                                   		<TD align="center"><%=m+1 %></TD>
												<TD align="center"><%= anhtrungbayRs.getString("NPP")%></TD>                                   
											
												<TD align="center"><%= anhtrungbayRs.getString("TDV")%></TD>
												<TD align="center"><%= anhtrungbayRs.getString("MÃ KH")%></TD>
												<TD align="center"><%= anhtrungbayRs.getString("KH")%></TD>
											<%-- 	<TD align="center"><%= anhtrungbayRs.getString("ismuahang")%></TD> --%>
												<TD align="center">
													<TABLE border = 0 cellpadding="0" cellspacing="0">
														<TR>
														<TD>																
																<!-- Noi nay chua anh va nut DONG -->
																<a id="<%=nut_id%>"   href="#">
																	<img src="../images/Display20.png" width="20" height="20" alt="Xem" ; >
																	
																	<script>
																		$(document).ready(function()
																		{
																			$("<%=nut%>").colorbox({width:"100%", inline:true, href:"<%=popup%>", top: "50"});        	
																		});
																		
																		$(document).ready(function(){
																		$("<%=nut%>").colorbox({
																				inline: true,
																				//href:"#ef",
																				close:"Ðóng",
																			opacity:0.95,
																				onClosed:function(){
																					$.colorbox.close();
																				}
																		});
																	});
																	 </script>
																</a>
																<!-- Khung Colorbox tam thoi an di -->
																<div style='display:none'>
																	<div id="<%=popup_id%>" style='padding:0px 5px; background:#fff;'>
																		<table   cellpadding="4px" cellspacing="2px" width="100%" align="center" >
																			<tr >
																				
																				<td>
																				<img style="top: -4px; max-width: 400px; max-height: 400px;" src="<%= getServletContext().getInitParameter("urlAnhPDA")  +"anhhangngay/"+ anhtrungbayRs.getString("ANH2") %>" >																																							
																				</td>																				
																			</tr>
																			<tr >
																				
																				<td>
																					<a target='_blank'  class="button" href="<%= getServletContext().getInitParameter("urlAnhPDA")  +"anhhangngay/"+ anhtrungbayRs.getString("ANH2") %>"> 
																					<%=ChuyenNgu.get("Xem",nnId) %> <%= anhtrungbayRs.getString("NGAY1") %>
																					</a>																																					
																				</td>

																				
																			</tr>
																		</table>
																	</div>
																</div>
																
														</TD>
														</TR>
													</TABLE>
												</TD>
												
											</TR>
									<%m++; }}catch(java.sql.SQLException e){}
								}%>	
																				
										</TABLE>
										
			
								</TABLE>
							</TD>
						</TR>
					</TABLE>	
						
				</fieldset>
			</div>
		</div>
		<br />
	</form>
</body>  
</html>