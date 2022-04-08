<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="geso.dms.center.beans.khaibaokiemkho.*" %>
<%@ page import="geso.dms.center.beans.khaibaokiemkho.imp.*" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.List" %>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%@page import="java.sql.SQLException" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/Phanam/index.jsp");
	}else{ %>

<%

 	IKhaiBaoKiemKho obj =(IKhaiBaoKiemKho)session.getAttribute("csxBean");
	ResultSet khRs = (ResultSet)obj.getKhList(); 
	ResultSet spRs = obj.getSpRs();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">

<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<script type="text/javascript"	src="../scripts/jquery.min.1.7.js"></script>
<script type="text/javascript" language="JavaScript" src="../scripts/jquery.tools.min.js"></script>

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

<script type="text/javascript" src="../scripts/ajax.js"></script>
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs.css">
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs-panes.css">

<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<script type="text/javascript" src="../scripts/ajax.js"></script>
<script type="text/javascript" src="../scripts/KhaiBaoSpKiemKho.js"></script>
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

<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>

<script>

$(function() {
 
 	$("ul.tabs").tabs("div.panes > div");
});
</script>

<SCRIPT language="JavaScript" type="text/javascript">
	function submitform()
	{
	    document.forms["khtt"].submit();
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
	function replaces()
	{
		var masp = document.getElementsByName("spMa");
		var tensp = document.getElementsByName("spTen");
		var donvi = document.getElementsByName("donvi");
		var spId = document.getElementsByName("spId");
		var i;
		
		for(i=0; i < masp.length; i++)
		{
			if(masp.item(i).value != "")
			{
				var sp = masp.item(i).value;
				var pos = parseInt(sp.indexOf(" - "));
				if(pos > 0)
				{
					spId.item(i).value = sp.substring(0, pos);
					
					console.log(sp);
					sp = sp.substr(parseInt(sp.indexOf(" - ")) + 3);
					pos = parseInt(sp.indexOf(" - "));
					
					masp.item(i).value = sp.substring(0, pos);
					sp = sp.substr(parseInt(sp.indexOf(" - ")) + 3);
					
					pos = parseInt(sp.indexOf(" - "));
					tensp.item(i).value = sp.substr(0,pos);
					
					sp = sp.substr(parseInt(sp.indexOf(" - ")) + 3);
					donvi.item(i).value = sp.substr(0,pos);
				}
			}
			else
			{
				tensp.item(i).value = "";
				donvi.item(i).value = "";
				spId.item(i).value = "";
			}			
		}
		setTimeout(replaces, 20);
	}	
	function save()
	{
		var masp = document.getElementsByName("spMa");
		for(i=0; i < masp.length; i++)
		{
			for(j=0; j < masp.length; j++)
			{
				if(masp.item(i).value != "")
				{
					if(masp.item(i).value == masp.item(j).value && i != j )
					{
						alert("Trùng mã sản phẩm, vui lòng kiểm tra lại");
						return;
					}
				}
			}
			
		}
	  document.forms["khtt"].action.value = "save";
	  document.forms["khtt"].submit(); 
    }
	function checkedAll()
	{
		 var chkAll = document.getElementById("chkAll");
		 var spIds = document.getElementsByName("khIds");
		 
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
	function submitFrom()
	{
	  document.forms["khtt"].action.value = "submit";
	  document.forms["khtt"].submit(); 
    }
	
	function changeKhachHang()
	{
		document.forms["khtt"].action.value = "changeKhachHang";
		document.forms["khtt"].submit(); 
	}
	
	function checkedAll()
	{
		 var chkAll = document.getElementById("chekAllSp");
		 var spIds = document.getElementsByName("chonban");
		 
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
	
	function checkedAll_KhachHang()
	{
		 var chkAll = document.getElementById("checkAllKh");
		 var spIds = document.getElementsByName("nppId");
		 
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
<input type="hidden" name="id" value="<%= obj.getId()%>">
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý khảo sát > Chức năng > Khai báo kiểm kho > Cập nhật</TD> 
							 <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%= userTen %></TD></tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
			<TR ><TD >
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR class = "tbdarkrow">
							<TD width="30" align="left"><A href="../../KhaiBaoKiemKhoSvl?userId=<%= userId%>" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
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
						<LEGEND class="legendtitle" style="color:black">Thông tin khai báo</LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
                          <TR>
                          		<TD class="plainlabel" valign="middle" width="120px">Diễn giải</TD>   
		                        <TD class="plainlabel" valign="middle" colspan="3" ><input type="text" name="diengiai" value="<%= obj.getDiengiai()%>"  > </TD>          
		                 		       
		                  </TR> 
		                  
		                   <TR>
                          		<TD class="plainlabel" valign="middle" width="120px" >Từ ngày </TD>   
		                        <TD class="plainlabel" valign="middle" width="250px" ><input type="text" class="days" name="tungay" value="<%= obj.getTuNgay() %>"  >  </TD>          
		                 		<TD class="plainlabel" valign="middle" width="130px">Đến ngày</TD>   
		                        <TD class="plainlabel" valign="middle"  ><input type="text" class="days" name="denngay" value="<%= obj.getDenNgay() %>"  >  </TD>
		                  </TR> 
		                  
		                  
		                  <TR>
		                  		<td colspan="4">
		                  		
		                  			<ul class="tabs">
		                  				<li><a href="#">Sản phẩm khách hàng</a></li>
			                  			<li><a href="#">Đối thủ</a></li>
			                  			<li><a href="#"> Upload Khách hàng</a></li>
			                  		</ul>
		                  			
		                  			<div class="panes">
										<div>
				                  			<TABLE width="100%" border="0" cellspacing="1" cellpadding="0">
												<TR class="tbheader">
													<TH width="20%">Mã sản phẩm </TH>
													<TH width="50%">Tên sản phẩm</TH>								
													<TH width="10%">Đơn vị</TH>
												
												</TR>
												<%if(spRs != null){ %>
												<%while(spRs.next()){%>
													
													<TR>
														<TD>
															<input type="hidden" style="width: 100%" name="spId" value="<%= spRs.getString("spId") %>" >
															<input type="text" style="width: 100%" name="spMa" value="<%= spRs.getString("spMa") %>" readonly="readonly" >
														</TD>
														<TD>
															<input type="text" style="width: 100%" name="spTen" value="<%= spRs.getString("spTen") %>" readonly="readonly" >
														</TD>
														<TD>
															<input type="text" style="width: 100%; text-align: center;" name="donvi" value="<%= spRs.getString("donvi") %>" readonly="readonly" >
														</TD>
					
													</TR>
													
												<% } }%>
												
												<%int k = 0; %>
											
												<%while( k < 20){%>
													
													<TR>
														<TD>
															<input type="hidden" style="width: 100%" name="spId" value="" >
															<input type="text" style="width: 100%" name="spMa" value=""  onkeyup="ajax_showOptions(this,'nhapkho',event)">
														</TD>
														<TD>
															<input type="text" style="width: 100%" name="spTen" value="" readonly="readonly" >
														</TD>
														<TD>
															<input type="text" style="width: 100%; text-align: center;" name="donvi" value="" readonly="readonly" >
														</TD>
														
													</TR>
													
												<%
													k++;
												} %>
											</TABLE>
										</div>
										<div>
											<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
												<TR class="tbheader">
													<TH width="15%">Sản phẩm</TH>
													<TH width="25%">Đơn vị tính</TH>								
												</TR>
												<%int n = 0; %>
												<% for(n = 0; n < obj.getSpDoithu().length; n++)
												{						
												if(n % 2 == 0) { %>			
														<TR class="tbdarkrow">
												<% } else { %>
														<TR class="tblightrow">
												<% } %>
													
														<TD><input type = "text" name = "spdt" value = "<%= obj.getSpDoithu()[n] %>" style="width: 100%"/></TD>
														<TD><input type = "text" name = "dvdt" value = "<%= obj.getDvtDoithu()[n] %>" style="width: 100%"/></TD>
														
														
													</TR>	
														
												<%} %>
												
												<% for(int i= n; i< 40; i++)
												{											
												if(i % 2 == 0) { %>			
														<TR class="tbdarkrow">
												<% } else { %>
														<TR class="tblightrow">
												<% } %>
													
														<TD><input type = "text" name = "spdt" style="width: 100%"/></TD>
														<TD><input type = "text" name = "dvdt" style="width: 100%"/></TD>
														
														
													</TR>	
														
												<%} %>
												
												
											</TABLE>
											
										</div>
										<div>
										<table width="100%" border="0" cellspacing="1" cellpadding="4">
											 <tr class="plainlabel">
										  		<TD class="plainlabel" >Chọn file upload</TD>
										  	  	<td colspan="1"><INPUT type="file" name="filename" size="40" value=''> </td> 
										  	</tr>
										  	<tr class="plainlabel">
										  		<td colspan="2">&nbsp;<a class="button2" href="javascript:upload()">
													<img style="top: -4px;" src="../images/button.png" alt="">Upload</a>							
										  		</td>
										  	</tr>    				
     											   </TABLE>
											<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
												<Tr class="tbheader">
        		<td width="15%" >Mã KH</td>
        		<td width="40%" align="center">Tên khách hàng</td>
        		<td width="45%" >Địa chỉ </td>
        		<td align="center" >Chọn <input type="checkbox" name="chkAll" id="chkAll" onchange="checkedAll();" ></td>
        	</Tr>
        	<% if(khRs != null)
        		while(khRs.next())
        		{
        			%>
        			
        			<TR>
        				<td>
        					<input type="text" value="<%= khRs.getString("Mafast") %>" style="width: 100%" readonly="readonly" > 
        				</td>
        				<td>
        					<input type="text" value="<%= khRs.getString("TEN") %>" style="width: 100%" readonly="readonly" >  
        				</td>
        				<td>
        					<input type="text" value="<%= khRs.getString("Diachi") %>" style="width: 100%" readonly="readonly" >  
        				</td>
        				<%-- <td>
        					<input type="text" value="<%= khRs.getString("DIACHI") %>" style="width: 100%" readonly="readonly" > 
        				</td> --%>
        			<%--	<td>
        					<input type="text" value="<%= khRs.getString("nppTen") %>" style="width: 100%" readonly="readonly" > 
        				</td>
        				<%-- <td align="center">
        					<input type="text" name = "suatmua_<%=khRs.getString("PK_SEQ")%>" value="<%= suatmua.get(khRs.getString("PK_SEQ")) != null? suatmua.get(khRs.getString("PK_SEQ")): ""%>" style="width: 100%; text-align: center;" > 
        				</td> --%>
        				<td align="center" >
        					<% if(obj.getKhId().contains(khRs.getString("PK_SEQ"))) { %> 
        						<input type="checkbox" name="khIds" value="<%= khRs.getString("PK_SEQ")%>"  checked="checked" >
        					<% } else { %> 
        						<input type="checkbox" name="khIds" value="<%= khRs.getString("PK_SEQ")%>"  >
        					<%  } %>
        					<input type="hidden" name = "khIdSelected" value = "<%= khRs.getString("PK_SEQ")%>">
        				</td>
        			</TR>
						<%} %>			
												
											</TABLE>
											
										</div>
									</div>
		                  		</td>
		                  </TR> 
		                  
						</TABLE>
							
						</FIELDSET>						
					</TD>
				</TR>
			</TABLE>
		</TD>
	</TR>
	</TABLE>
	<script type="text/javascript">
replaces();
</script>
</form>

</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</HTML>

<%
	if(spRs != null)
		spRs.close();
	
	obj.DbClose();
%>

<%}%>
