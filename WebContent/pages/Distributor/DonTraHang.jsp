<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.NumberFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@page import="java.sql.SQLException"%>
<%@ page  import = "geso.dms.distributor.beans.dontrahang.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>
<% IDontrahangList obj = (IDontrahangList)session.getAttribute("obj"); %>
<% ResultSet nhapkhoRs =  obj.getNhapkhoRs(); %>
<%	ResultSet nppRs = obj.getKhRs(); %>
<%	ResultSet nguoitaoRs = obj.getNguoitaoRs(); %>
<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  %>
<% obj.setNextSplittings();
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{
		
		String view = obj.getView();
		String url = "";
		int[] quyen = new  int[6];
		NumberFormat formater = new DecimalFormat("##,###,###");

		if(view.trim().length() > 0)
		{
		 	quyen = util.Getquyen("DontrahangSvl","&view="+view,userId); 
		 	url = util.getUrl("DontrahangSvl","&view="+view);
		}
		else
		{
			quyen = util.Getquyen("DontrahangSvl","",userId); 
			url = util.getUrl("DontrahangSvl","");
		}
		 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Diageo - Project</title>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">
    <LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
    <LINK rel="stylesheet" href="../css/main.css" type="text/css">
    <LINK rel="stylesheet" href="../css/datepicker.css" type="text/css">
    <script language="javascript" src="../scripts/datepicker.js"></script>
   	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css"/>
  	<script type="text/javascript" src="../scripts/Timepicker/jquery.min.js"></script>
  	<script type="text/javascript" src="../scripts/Timepicker/jquery-ui.min.js"></script>
  	<script type="text/javascript" src="../scripts/phanTrang.js"></script>
	<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
	<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
	<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
	<script type="text/javascript" src="../scripts/phanTrang.js"></script>
	
<script type="text/javascript" src="../scripts/dropdowncontent2.js"></script>
	<script src="../scripts/ui/jquery.ui.datepicker.js"
		type="text/javascript"></script>
		<style type="text/css">

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
	<script type="text/javascript">
		$(document).ready(function() {		
				$( ".days" ).datepicker({			    
						changeMonth: true,
						changeYear: true				
				});            
	        }); 		
			
	</script>
   
  	<script type="text/javascript" src="..scripts/jquery-1.js"></script>
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
    <script type="text/javascript">
        $(document).ready(function(){
            $(".button").hover(function(){
                $(".button img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        }); 
		$(document).ready(function(){
            $(".button2").hover(function(){
                $(".button2 img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        }); 
		$(document).ready(function(){
            $(".button3").hover(function(){
                $(".button3 img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        }); 
    </script>
    <SCRIPT language="javascript" type="text/javascript">
	 function submitform()
	 {   
	    document.forms["ckParkForm"].submit();
	 }
	 function newform()
	 {   
		document.forms["ckParkForm"].action.value = "Tao moi";
	    document.forms["ckParkForm"].submit();
	 }
	 function clearform()
	 {   
	    document.forms["ckParkForm"].tungay.value = "";
	    document.forms["ckParkForm"].denngay.value = "";
	    document.forms["ckParkForm"].trangthai.value = "";
	    document.forms["ckParkForm"].sochungtu.value = "";
	    document.forms["ckParkForm"].khId.value = "";
	    document.forms["ckParkForm"].submit();
	 }
	 function thongbao()
	 {
		 var tt = document.forms["ckParkForm"].msg.value;
	 	 if(tt.length>0)
	     	alert(document.forms["ckParkForm"].msg.value);
	 }
	</SCRIPT>
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
    $(document).ready(function() { 
     $(".select2").select2();
 });
</script>

<!-- BEGIN RENDER AUTO -->

<% if (!geso.dms.center.util.GlobalValue.getDevmode()) { %>
	<script type="text/javascript" src="../scripts/disableF12.js"></script>
<% } %>

<!-- END RENDER AUTO -->
</head>
<body>
<!-- BEGIN RENDER AUTO -->
<%if(!geso.dms.center.util.GlobalValue.getDevmode()){ %>
	<noscript> 
	 	<meta http-equiv="refresh" content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")  %>">
		<!-- //window.location = "https://www.google.com"; -->
	</noscript>
<%} %>
<!-- END RENDER AUTO -->

<form name="ckParkForm" method="post" action="../../DontrahangSvl"> 
 <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="action" value="1" >
<input type="hidden" name="msg" value='<%= obj.getMsg() %>'>
<input type="hidden" name="nppId" value='<%= obj.getNppId() %>'>
<input type="hidden" name="view" value='<%= obj.getView() %>'>
<script language="javascript" type="text/javascript">
    thongbao();
</script> 

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:55%; padding:5px; float:left" class="tbnavigation">
        	&nbsp;<%=url %>
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng Bạn <%= userTen %> &nbsp;
        </div>
    </div>
  	<div id="cotent" style="width:100%; float:none">
    	<div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
        <fieldset style="margin-top:5px" >
            <legend class="legendtitle"> Tiêu chí tìm kiếm</legend>
                <TABLE width="100%" cellpadding="6px" cellspacing="0px" style="margin-top: 5px " >
                	 <TR>
                        <TD class="plainlabel" width="15%">Từ ngày</TD>
                        <TD class="plainlabel">
                            <input type="text" name="tungay" value="<%= obj.getTungayTao() %>" class="days" maxlength="10" onchange="submitform();" />
                        </TD>
                        <TD class="plainlabel" width="15%">Đến ngày</TD>
                        <TD class="plainlabel">
                            <input type="text" class="days" 
                                   name="denngay" value="<%= obj.getDenngayTao() %>" class="days" maxlength="10" onchange="submitform();"  />
                        </TD>
                    </TR>	
                    
                    
                     <TR>
                     <TD class="plainlabel" width="15%">Số chứng từ</TD>
                        <TD class="plainlabel">
                            <input type="text" name="sochungtu" value="<%= obj.getSochungtu()%>"  onchange="submitform();" />
                        </TD>
                        <TD class="plainlabel" valign="middle">Trạng thái </TD>
                        <TD class="plainlabel" valign="middle">
                           <select name="trangthai" onchange="submitform();" >
                           		<option value=""> </option>
								<% if (obj.getTrangthai().equals("1")){%>
								  	<option value="1" selected>Chờ duyệt</option>
								  	<option value="0">Chưa chốt</option>
								  	<option value="2" >Hoàn tất</option>
								  	<option value="3" >Đã hủy</option>
								  
								<%}else if(obj.getTrangthai().equals("0")) {%>
								 	<option value="0" selected>Chưa chốt</option>
								 	<option value="1" >Chờ duyệt</option>
								  	<option value="2" >Hoàn tất</option>
								  	<option value="3" >Đã hủy</option>
								<%}else if(obj.getTrangthai().equals("2")) {%>
								 	<option value="2" selected>Hoàn tất</option>
								  	<option value="0">Chưa chốt</option>
								  	<option value="1" >Chờ duyệt</option>
								  	<option value="3" >Đã hủy</option>
								<%} else  {%>
								 	<option value="0">Chưa chốt</option>
								  	<option value="1" >Chờ duyệt</option>
								  	<option value="2" >Hoàn tất</option>
								  	<option value="3" >Đã hủy</option>
								<%} %>
                           </select>
                        </TD>
                                                
                    </TR> 
                    <TR>
	                    <TD class="plainlabel" >Đối tượng nhận</TD>
	                    <TD class="plainlabel" >
	                    	<select name = "khId" style="width: 200px" class="select2"  onchange="submitform();"  >
	                    		<option value=""> </option>
	                        	<%
	                        		if(nppRs != null)
	                        		{
	                        			try
	                        			{
	                        			while(nppRs.next())
	                        			{  
	                        			if( nppRs.getString("pk_seq").equals(obj.getKhId())){ %>
	                        				<option value="<%= nppRs.getString("pk_seq") %>" selected="selected" ><%= nppRs.getString("ten") %></option>
	                        			<%}else { %>
	                        				<option value="<%= nppRs.getString("pk_seq") %>" ><%= nppRs.getString("ten") %></option>
	                        		 <% } } nppRs.close();} catch(SQLException ex){}
	                        		}%>
	                        	
	                    	</select>
	                    </TD>  
	                    <TD class="plainlabel" >Người tạo</TD>
	                    <TD class="plainlabel" >
	                    	<select name = "nguoitaoId" style="width: 200px" class="select2"  onchange="submitform();"  >
	                    		<option value=""> </option>
	                        	 <%
	                        		if(nguoitaoRs != null)
	                        		{
	                        			try
	                        			{
	                        			while(nguoitaoRs.next())
	                        			{  
	                        			if( nguoitaoRs.getString("pk_seq").equals(obj.getNguoitaoId())){ %>
	                        				<option value="<%= nguoitaoRs.getString("pk_seq") %>" selected="selected" ><%= nguoitaoRs.getString("ten") %></option>
	                        			<%}else { %>
	                        				<option value="<%= nguoitaoRs.getString("pk_seq") %>" ><%= nguoitaoRs.getString("ten") %></option>
	                        		 <% } } nguoitaoRs.close();} catch(SQLException ex){}
	                        		}%> 
	                        	
	                    	</select>
	                    </TD>  
                    </TR>   
                    <tr>
                        <td colspan="4" class="plainlabel">
                            <a class="button" href="javascript:submitform()">
                                <img style="top: -4px;" src="../images/Search30.png" alt="">Tìm kiếm </a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <a class="button2" href="javascript:clearform()">
                                <img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;
                        </td>
                    </tr>        					
                </TABLE>                      
        </fieldset>                      
    	</div>
        <div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
        	<fieldset>
            	<legend><span class="legendtitle"> Trả hàng về NCC </span>&nbsp;&nbsp;
                	<%if(!view.equals("TT")&& quyen[0]!=0){ %>
                	<a class="button3" href="javascript:newform()">
                    <img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>
                    <%} %>
                </legend>
            	<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
					<TR class="tbheader">
	                    <TH style="width: 7%" align="center">Mã số</TH>
	                    <TH style="width: 7%" align="center">Ngày trả</TH>
	                    <TH style="width: 15%" align="center">Đối tượng</TH>
	                    <TH style="width: 10%" align="center">Tổng tiền sau VAT</TH>
	                    <TH style="width: 10%" align="center">Ghi chú</TH>
	                    <TH style="width: 10%" align="center">Trạng thái</TH>
	                    <TH style="width: 10%" align="center">Ngày tạo</TH>
	                    <TH style="width: 10%" align="center"> Người tạo </TH>
	                    <TH style="width: 10%" align="center"> Ngày sửa </TH>
	                    <TH style="width: 15%" align="center"> Người sửa </TH>
	                    <TH style="width: 10%" align="center" >Tác vụ</TH>
	                </TR>
					<%
                 		if(nhapkhoRs != null)
                 		{
                 			int m = 0;
                 			while(nhapkhoRs.next())
                 			{  		
                 				if((m % 2 ) == 0) {%>
		                         	<TR class='tbdarkrow'>
		                        <%}else{ %>
		                          	<TR class='tblightrow'>
		                        <%} %>
		                    <TD align="center"><%= nhapkhoRs.getString("PK_SEQ") %></TD>
		                    <TD align="center"><%= nhapkhoRs.getString("ngaytra") %></TD>
		                    <TD align="left"><%= nhapkhoRs.getString("tructhuoc") %></TD> 
		                     <TD align="center"><%= formater.format(nhapkhoRs.getDouble("tienavat")) %></TD> 
		                      <TD align="left"><%= nhapkhoRs.getString("ghichu") %></TD>  
		                    	 <TD align="center">
		                    	<%
		                    		String trangthai = "";
		                    		String tt = nhapkhoRs.getString("trangthai");
		                    		if(tt.equals("0"))
		                    			trangthai = "Chưa chốt";
		                    		else
		                    		{
		                    			if(tt.equals("1"))
		                    				trangthai = "Chờ duyệt";
		                    			else if(tt.equals("2"))
		                    			{
			                    			trangthai = "Hoàn tất";
		                    			}
		                    			else if(tt.equals("3"))
		                    			{
		                    				trangthai = "Đã hủy";
	                    				}
		                    		}
		                    	%>
		                    	<%= trangthai %>
		                    </TD>   									                                    
					     	<TD align="center"><%= nhapkhoRs.getString("created_date") %></TD>	
		                    <TD align="left"><%= nhapkhoRs.getString("NGUOITAO") %></TD>
         					<TD align="center"><%= nhapkhoRs.getString("modified_Date") %></TD>
							<TD align="left"><%= nhapkhoRs.getString("NGUOISUA") %></TD>
									
		                    <TD align="center"> 
		                    <% if( tt.equals("0") &&  !view.equals("TT")  ){ %>
		                    
		                    	<%if(quyen[Utility.SUA]!=0  ){ %>
                                <A href = "../../DontrahangUpdateSvl?userId=<%=userId%>&update=<%=nhapkhoRs.getString("PK_SEQ") %>&view=<%=view %>"><IMG src="../images/Edit20.png" alt="Cập nhật" title="Cập nhật" border=0></A>&nbsp;
                            	<%} %>
                                
                            	<%if(quyen[Utility.CHOT]!=0  ){ %>
                                <A href = "../../DontrahangSvl?userId=<%=userId%>&chot=<%= nhapkhoRs.getString("PK_SEQ") %>&view=<%=view %>"><img src="../images/Chot.png" alt="Chốt" title="Chốt" width="20" height="20" border=0 onclick="if(!confirm('Bạn có muốn chốt Trả hàng về NCC này?')) return false;"></A>&nbsp;
                            	
                            	
                            	<%} %>
                                
                            	<%if(quyen[1]!=0){ %>
                              	<A href = "../../DontrahangSvl?userId=<%=userId%>&delete=<%= nhapkhoRs.getString("PK_SEQ") %>&view=<%=view %>"><img src="../images/Delete20.png" alt="Xóa" title="Xóa" width="20" height="20" border=0 onclick="if(!confirm('Bạn có muốn xóa Trả hàng về NCC này?')) return false;"></A>
                                <%} %>
                              										
		                    <%} if (tt.equals("1") &&  view.equals("TT")) {%>
		                    
		                    	<%if(quyen[Utility.CHOT]!=0){ %>
                                <A href = "../../DontrahangSvl?userId=<%=userId%>&duyet=<%= nhapkhoRs.getString("PK_SEQ") %>&view=<%=view %>"><img src="../images/Chot.png" alt="Chốt" title="Duyệt" width="20" height="20" border=0 onclick="if(!confirm('Bạn có muốn duyệt Trả hàng về NCC này?')) return false;"></A>&nbsp;
                            	<%} %>
                            	
                            	<%if(quyen[1]!=0){ %>
                              	<A href = "../../DontrahangSvl?userId=<%=userId%>&HOdelete=<%= nhapkhoRs.getString("PK_SEQ") %>&view=<%=view %>"><img src="../images/Delete20.png" alt="Xóa" title="Xóa" width="20" height="20" border=0 onclick="if(!confirm('Bạn có muốn xóa Trả hàng về NCC này?')) return false;"></A>
                                <%} %>
		                    
		                    <% } %>
		                    
		                    <%if(quyen[3]!=0){ %>
		                    	<A href = "../../DontrahangUpdateSvl?userId=<%=userId%>&display=<%= nhapkhoRs.getString("PK_SEQ") %>&view=<%=view %>"><IMG src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" border=0></A>
		                   		<%} %>
		                   		<%if(quyen[3]!=0){ %>
                                <A href = "../../DonHangTraPdf?userId=<%=userId%>&pdf=<%= nhapkhoRs.getString("PK_SEQ") %>&view=<%=view %>"><img src="../images/Printer30.png" alt="PDF" title="PDF" width="20" height="20" border=0 ></A>&nbsp;
		                   		<%} %>
		                   		
		                    </TD>
		                </TR>
                     <% m++; } nhapkhoRs.close(); } %>
					<tr class="tbfooter" > 
						 <TD align="center" valign="middle" colspan="13" class="tbfooter">
						 	 <% obj.setNextSplittings(); %>
												 <script type="text/javascript" src="../scripts/phanTrang.js"></script>
												<input type="hidden" name="crrApprSplitting" value="<%= obj.getCrrApprSplitting() %>" >
												<input type="hidden" name="nxtApprSplitting" value="<%= obj.getNxtApprSplitting() %>" >

											 	<%if(obj.getNxtApprSplitting() >1) {%>
													<img alt="Trang Dau" src="../images/first.gif" style="cursor: pointer;" onclick="View(document.forms[0].name, 1, 'view')"> &nbsp;
												<%}else {%>
													<img alt="Trang Dau" src="../images/first.gif" > &nbsp;
													<%} %>
												<% if(obj.getNxtApprSplitting() > 1){ %>
													<img alt="Trang Truoc" src="../images/prev.gif" style="cursor: pointer;" onclick="View(document.forms[0].name, eval(document.forms[0].nxtApprSplitting.value) -1, 'view')"> &nbsp;
												<%}else{ %>
													<img alt="Trang Truoc" src="../images/prev_d.gif" > &nbsp;
												<%} %>
												
												<%
													int[] listPage = obj.getNextSplittings();
													for(int i = 0; i < listPage.length; i++){
												%>
												
												<% 
												
												System.out.println("Current page:" + obj.getNxtApprSplitting());
												System.out.println("List page:" + listPage[i]);
												
												if(listPage[i] == obj.getNxtApprSplitting()){ %>
												
													<a  style="color:white;"><%= listPage[i] %>/ <%=obj.getTheLastSplitting() %></a>
												<%}else{ %>
													<a href="javascript:View(document.forms[0].name, <%= listPage[i] %>, 'view')"><%= listPage[i] %></a>
												<%} %>
													<input type="hidden" name="list" value="<%= listPage[i] %>" />  &nbsp;
												<%} %>
												
												<% if(obj.getNxtApprSplitting() < obj.getTheLastSplitting()){ %>
													&nbsp; <img alt="Trang Tiep" src="../images/next.gif" style="cursor: pointer;" onclick="View(document.forms[0].name, eval(document.forms[0].nxtApprSplitting.value) +1, 'view')"> &nbsp;
												<%}else{ %>
													&nbsp; <img alt="Trang Tiep" src="../images/next_d.gif" > &nbsp;
												<%} %>
												<%if(obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {%>
											   		<img alt="Trang Cuoi" src="../images/last.gif" > &nbsp;
										   		<%}else{ %>
										   			<img alt="Trang Cuoi" src="../images/last.gif" style="cursor: pointer;" onclick="View(document.forms[0].name, -1, 'view')"> &nbsp;
										   		<%} %>
						</TD>
					 </tr>
					 
				</TABLE>
            </fieldset>
        </div>
    </div>  
</div>
</form>
</body>
</html>

<%
	if(nhapkhoRs != null){ nhapkhoRs.close(); nhapkhoRs = null ;} 
	if(nppRs != null){ nppRs.close(); nppRs = null ;} 
	
	if(obj != null)
	{
		obj.DBclose();
		obj = null;
	}
	session.setAttribute("obj", null);
}%>