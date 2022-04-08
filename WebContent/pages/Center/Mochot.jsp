<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.dms.center.beans.mochot.IMochot" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>
<%@ page  import = "java.text.DateFormat" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "java.text.SimpleDateFormat" %>
<% IMochot dhtvBean = (IMochot)session.getAttribute("mksBean"); %>
<% ResultSet ddkd = (ResultSet)dhtvBean.getDdkdList(); %>
<% ResultSet gsbh = (ResultSet)dhtvBean.getGsbhList(); %>
<% ResultSet npp = (ResultSet)dhtvBean.getNppList(); %>
<% ResultSet madh = (ResultSet)dhtvBean.getDonhangList(); %>
<% String userId = (String) session.getAttribute("userId");  %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
<style type="text/css">
	#mainContainer{
		width:660px;
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
<link rel="stylesheet" type="text/css" href="../css/speechbubbles.css" />

<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
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


<script type="text/javascript" src="../scripts/speechbubbles.js"></script>
<script type="text/javascript">
	jQuery(function($){ 
		 $('.addspeech').speechbubble();})
</script>
<script type="text/javascript" src="../scripts/ajax.js"></script>
<script type="text/javascript" src="../scripts/ajax-dynamic-list-sptrave.js"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<script type="text/javascript" src="../scripts/cool_DHTML_tootip.js"></script>
<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>

<script language="javascript" type="text/javascript" >
function replaces()
{
	var khTen = document.getElementsByName("khTen");
	var smartId = document.getElementsByName("smartId");
	
	for(i = 0; i < smartId.length; i++)
	{
		var tem = smartId.item(0).value;
		if(parseInt(tem.indexOf("-->[")) > 0)
		{
			var tmp = tem.substring(0, parseInt(tem.indexOf("-->[")));
			document.getElementById("khId").value = tmp.substring(parseInt(tem.indexOf("-")+1, tmp.length));
			smartId.item(0).value = tmp.substring(0, parseInt(tem.indexOf("-")));
			tem = tem.substr(parseInt(tem.indexOf("-->[")) + 4);
			khTen.item(0).value = tem.substring(0, parseInt(tem.indexOf("][")));
			
			if(khTen != "")
			{
				document.forms['dhtvForm'].action.value='submit';
			    document.forms["dhtvForm"].submit();
			}
			
			break;
		}
		if(tem == "")
		{
			khTen.item(0).value = "";
		}		
	}

	setTimeout(replaces, 100);
	}
	

	
	
	
	function keypress(e)
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

	
	
	function saveform()
	 {	 
		 congDonSPCungMa();
		 var masp = document.getElementsByName("masp");
		 var tensp = document.getElementsByName("tensp");
		 var soluong = document.getElementsByName("soluong");
		 var ddkdId = document.getElementById("ddkdTen");
		 var khId = document.getElementById("khTen");
		 var khoId = document.getElementById("khoTen");
		 var gsbhId = document.getElementById("gsbhTen");
		 
	/* 	 if(gsbhId.value == "")
		 {
			alert("Vui lòng kiểm tra giám sát bán hàng...");
			return;
		 }
		 
		 if(ddkdId.value == "")
		 {
			alert("vui lòng chọn Nhân viên bán hàng...");
			return;
		 }
		 if(khId.value == "")
		 {
			alert("Vui lòng chọn khách hàng...");
			return;
		 }
		 if(khoId.value == "")
		 {
			alert("vui lòng chọn kho nhập hàng...");
			return;
		 } */
			
		 for(var k = 0; k < masp.length; k++)
		 {
			if(masp.item(k).value != "")
			{
				if(soluong.item(k).value == "" || soluong.item(k).value  == "0" || tensp.item(k).value == "")			
				{
					alert("Kiểm tra lại tên và số lượng sản phẩm, Phải nhập tên và số lượng cho sản phẩm được chọn");
					return;
				}
			}
		 }
		 
		 document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Processing...</a>&nbsp;&nbsp;&nbsp;&nbsp;";


	 	 document.forms['dhtvForm'].action.value='save';
	     document.forms['dhtvForm'].submit();
	 }
	
	 function submitform()
	 { 
		
		document.forms['dhtvForm'].action.value='submit';
	    document.forms["dhtvForm"].submit();
	 }
	 function Mochot()
	 {
	 	document.forms['dhtvForm'].action.value= 'mochot';
	 	 var r = confirm("Bạn chắc chắn muốn mở chốt đơn hàng ! Lưu ý các đơn hàng mở chốt có ngày đơn hàng lớn hơn ngày khóa sổ ");
	 	 if (r == false) {		 
	 	     return;
	 	 }
	 	document.forms['dhtvForm'].submit();
	 	
	 }

	 function Huydon()
	 {
	 	document.forms['dhtvForm'].action.value= 'huydon';
	 	 var r = confirm("Bạn chắc chắn muốn hủy đơn hàng này??? ! Lưu ý các đơn hàng hủy có ngày đơn hàng lớn hơn ngày khóa sổ ");
	 	 if (r == false) {		 
	 	     return;
	 	 }
	 	document.forms['dhtvForm'].submit();
	 	
	 }

	
	
</script>
<link href="../css/select2.css" rel="stylesheet" />
	<script src="../scripts/select2.js"></script>
	<script>
    	$(document).ready(function() { 
    		$("select").select2(); 
    	});
     
 	</script>	

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

<form name="dhtvForm" method="post" action="../../MochotdhSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%=userId %>'>
<%-- <input type="hidden" name="nppId" value='<%= dhtvBean.getNppId() %>'> --%>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="chot_dh" value='false'>
<input type="hidden" id = "khId" name="khId" value='<%= dhtvBean.getKhId() %>'> 
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFEE"><!--begin body Dossier-->
				<TABLE border =0 width = "100%" cellpadding="2" cellspacing="0">
				<TBODY>
					<TR height="22">
						<TD align="left" >
							<TABLE width="100%" cellpadding="0" cellspacing="0">
								<TR>
									<TD align="left">
									   <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
										   <TR height="22">
			 								   <TD align="left"  class="tbnavigation">&nbsp;Quản lý bán hàng > Đổi trạng thái đơn hàng </TD>								   
			 								   
					    				 </TR>
									  </TABLE>
								  </TD>
							  </TR>	
						  	</TABLE>
							<TABLE width="100%" border="0" cellpadding="1" cellspacing="0">
							<tr>
								<TD align="left" colspan="4" class="legendtitle">
									<FIELDSET>
									<LEGEND class="legendtitle">Thông báo </LEGEND>
							
				    				<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold"  style="width :100%" rows="1"><%= dhtvBean.getMsg() %></textarea>
			
									</FIELDSET>
							   </TD>
							</tr>	
								<TR ><TD >
									<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
										<TR class = "tbdarkrow">
											<TD width="30" align="left"><A href = "javascript:history.back()" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
										    <TD width="2" align="left" >&nbsp;</TD>
										    <TD width="30" align="left" >
										    <div id="btnSave">
										    	<A href="javascript:saveform()" ><img src="../images/Save30.png" alt="Luu lai"  title="Luu lai" border="1" longdesc="Luu lai" style="border-style:outset"></A>
										    </div>	
										    	</TD>	
										    <TD align="left" >&nbsp;</TD>				    		
										</TR>
									</TABLE>
								</TD></TR>
							</TABLE>												
							<TABLE border="0" width="100%" cellpadding = "0" cellspacing = "0">
								<tr>
								
								</tr>
								<TR>
									<TD  align="left">						
										<FIELDSET>
										<LEGEND class="legendtitle">&nbsp;Đơn hàng mở trạng thái </LEGEND>
										<TABLE cellpadding = "6" cellspacing = "0" width = "100%" border = 0>
											
                                         <TR>
										<TD class="plainlabel">Từ ngày </TD>
											<TD class="plainlabel">	<input type="text" name="Sdays" id="Sdays" class="days" value='<%= dhtvBean.gettungay() %>' />
											</TD>
											<TD class="plainlabel">Đến ngày </TD>
											<TD class="plainlabel">
												<input type="text" name="Edays" id="Edays" class="days" value='<%= dhtvBean.getdenngay() %>'/>
											</TD>
										</TR>
                               		            <TR >
												<TD width="22%" class="plainlabel">Nhà phân phối  </TD>
												<TD colspan="3" class="plainlabel"> 
												<SELECT name="nppId" id="nppId" style ="width: 200px"  onChange = "submitform();" >
										 			 <option value=""></option>
													  <% if(npp != null){
														  try{ while(npp.next()){ 													 
											    			if(npp.getString("nppId").equals(dhtvBean.getNppId())){ %>
											      				<option value='<%=npp.getString("nppId")%>' selected><%= npp.getString("nppTen") %></option>
											      			<%}else{ %>
											     				<option value='<%=npp.getString("nppId")%>'><%= npp.getString("nppTen") %></option>
											     			<%}} npp.close(); }catch(java.sql.SQLException e){}} %>	 
                                    			</SELECT></TD>
										  </TR>	
                                          <TR class="tblightrow">
												<TD  class="plainlabel">Giám sát bán hàng </TD>
												<TD colspan="3" class="plainlabel"> 
									 			<SELECT name="gsbhTen" id="gsbhTen" style ="width: 200px"  onChange = "submitform();">
										 			 <option value=""></option>
													  <% if(gsbh != null){
														  try{ while(gsbh.next()){ 													 
											    			if(gsbh.getString("pk_seq").equals(dhtvBean.getGsbhId())){ %>
											      				<option value='<%=gsbh.getString("pk_seq")%>' selected><%= gsbh.getString("ten") %></option>
											      			<%}else{ %>
											     				<option value='<%=gsbh.getString("pk_seq")%>'><%= gsbh.getString("ten") %></option>
											     			<%}} gsbh.close(); }catch(java.sql.SQLException e){}} %>	 
                                    			</SELECT></TD>
											</TR>						
											<TR class="tblightrow">
												<TD  class="plainlabel">Nhân viên bán hàng </TD>
												<TD colspan="3" class="plainlabel"> 
									 			<SELECT name="ddkdTen" id="ddkdTen" style ="width: 200px" onChange = "submitform();">
										 			 <option value=""> </option>
													  <% if(ddkd != null){
														  try{ while(ddkd.next()){ 													 
											    			if(ddkd.getString("ddkdId").equals(dhtvBean.getDdkdId())){ %>
											      				<option value='<%=ddkd.getString("ddkdId")%>' selected><%=ddkd.getString("ddkdTen") %></option>
											      			<%}else{ %>
											     				<option value='<%=ddkd.getString("ddkdId")%>'><%=ddkd.getString("ddkdTen") %></option>
											     			<%}} ddkd.close(); }catch(java.sql.SQLException e){}} %>	 
                                    			</SELECT></TD>
											</TR>
											<%-- <TR >
												<TD class="plainlabel">Mã khách hàng</TD>
												<TD class="plainlabel" width="250px">
                                                 	<input type="text" id="smartId" name="smartId" value="<%= dhtvBean.getSmartId() %>" onkeypress="keypress2(event);" />
                                                <TD class="plainlabel" width="190px">Tên khách hàng - Địa chỉ</TD>                                                   
                                                <TD class="plainlabel" colspan="5">
                                                	<input type="text" id="khTen" name="khTen" style="width: 100%" value="<%= dhtvBean.getKhTen() %>" readonly/>  
                                                </TD>                                                           
                                            </TR>                 --%>                                  
                                              
											<!-- </TR> -->
											
											
											 <TR >
												<TD width="22%" class="plainlabel">Số đơn hàng  </TD>
												<TD colspan="3" class="plainlabel"> 
												<SELECT name="dhId" id="dhId" style ="width: 200px"  onChange = "submitform();">
										 			 <option value=""></option>
													  <% if(madh != null){
														  try{ while(madh.next()){ 													 
											    			if(madh.getString("dhId").equals(dhtvBean.getDonhangId())){ %>
											      				<option value='<%=madh.getString("dhId")%>' selected><%= madh.getString("dhId") %></option>
											      			<%}else{ %>
											     				<option value='<%=madh.getString("dhId")%>'><%= madh.getString("dhId") %></option>
											     			<%}} npp.close(); }catch(java.sql.SQLException e){}} %>	 
                                    			</SELECT></TD>
										  </TR>	
											
										  	
  						<TR>
					      		<TD class="legendtitle" >
					      		<a class="button3" href="javascript:Mochot()">
					      		 	<img style="top: -4px;" src="../images/New.png" alt="">Mở chốt đơn
					      		 </a>   
					      		</TD>
					      		
					      		<TD class="legendtitle">
					      		<a class="button3" href="javascript:Huydon()">
					      		 	<img style="top: -4px;" src="../images/New.png" alt="">Hủy đơn đã chốt
					      		 </a>     
					      		</TD>
					      </TR>
										 
										    </TABLE></TD>																	  
										</TABLE>
										
									</FIELDSET>
								  </TD>
							   </TR>	
							 
							 		   
						  </TABLE>
						</TD>
					</TR>	
					
				</TBODY>
			</TABLE>
	</td>
  </tr>

</TABLE>
<script type="text/javascript">
replaces();
//Call dropdowncontent.init("anchorID", "positionString", glideduration, "revealBehavior") at the end of the page:
dropdowncontent.init("searchlink", "right-bottom", 500, "click");
</script>
</form>
</BODY>
<script>
	jQuery(function()
	{		
		$("#smartId").autocomplete("KhachHangList.jsp");	
		//$("#smartId").autocomplete("list.jsp");
	});	
</script>
</HTML>
<%
try{
	if(ddkd != null)
		ddkd.close();
	if(gsbh != null)
		gsbh.close();
	
	session.setAttribute("dhtvBean",null);
	
	}
	catch (Exception e) {}
%>