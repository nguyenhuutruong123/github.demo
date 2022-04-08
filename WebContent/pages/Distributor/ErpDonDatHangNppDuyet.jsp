<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.dms.distributor.beans.dondathang.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>
<% IErpDuyetddhNppList obj = (IErpDuyetddhNppList)session.getAttribute("obj"); %>
<% ResultSet nhapkhoRs =  obj.getDondathangRs(); %>
<% ResultSet nppRs = obj.getKhRs(); %>
<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  %>
<% obj.setNextSplittings();
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{
	/* 	int[] quyen = new  int[6];
		quyen = util.Getquyen("ErpDuyetddhNppSvl","",userId); */
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>dms - Project</title>
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
	    document.forms["ckParkForm"].khId.value = "";
	    document.forms["ckParkForm"].sodh.value = "";
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
		$(document).ready(function()
		{
			$(".select2").select2();
		});
	</script>
	
	<script type="text/javascript" src="../scripts/cool_DHTML_tootip.js"></script>
	<style type="text/css">
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
		th {
		cursor: pointer;
		}	
  	</style>
	

<!-- BEGIN RENDER AUTO -->

<% if (!geso.dms.center.util.GlobalValue.dev_mode) { %>
	<script type="text/javascript" src="../scripts/disableF12.js"></script>
<% } %>

<!-- END RENDER AUTO -->
</head>
<body>
<!-- BEGIN RENDER AUTO -->
<%if(!geso.dms.center.util.GlobalValue.dev_mode){ %>
	<noscript> 
	 	<meta http-equiv="refresh" content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")  %>">
		<!-- //window.location = "https://www.google.com"; -->
	</noscript>
<%} %>
<!-- END RENDER AUTO -->

<form name="ckParkForm" method="post" action="../../ErpDuyetddhNppSvl"> 
 <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="action" value="1" >
<input type="hidden" name="msg" value='<%= obj.getMsg() %>'>
<input type="hidden" name="nppId" value='<%= obj.getNppId() %>'>
<input type="hidden" name="loaidonhang" value='<%= obj.getLoaidonhang() %>'>
<input type="hidden" name="currentPage" value="<%= obj.getCurrentPage() %>" >
<script language="javascript" type="text/javascript">
    thongbao();
</script> 

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:55%; padding:5px; float:left" class="tbnavigation">
        	&nbsp;Quản lý bán hàng > Bán hàng cho NPP / Đại lý > Duyệt đơn hàng
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
                        <TD class="plainlabel" width="100px">Từ ngày</TD>
                        <TD class="plainlabel" width="250px" >
                            <input type="text" class="days" name="tungay" value="<%= obj.getTungay() %>" maxlength="10" onchange="submitform();" />
                        </TD>
                    
                        <TD class="plainlabel" width="100px">Đến ngày</TD>
                        <TD class="plainlabel">
                            <input type="text" class="days" name="denngay" value="<%= obj.getDenngay() %>" maxlength="10" onchange="submitform();" />
                        </TD>
                    </TR>
                    
                     <TR>
                     
                     	<TD class="plainlabel" width="100px">ETC / Đối tác</TD>
                        <TD class="plainlabel">
                            <select name = "khId" class="select2" style="width: 200px" onchange="submitform();" >
	                    		<option value="">All</option>
	                        	<%
	                        		if(nppRs != null)
	                        		{
	                        			try
	                        			{
	                        			while(nppRs.next())
	                        			{  
	                        			if( nppRs.getString("pk_seq").equals(obj.getKhTen())){ %>
	                        				<option value="<%= nppRs.getString("pk_seq") %>" selected="selected" ><%= nppRs.getString("ten") %></option>
	                        			<%}else { %>
	                        				<option value="<%= nppRs.getString("pk_seq") %>" ><%= nppRs.getString("ten") %></option>
	                        		 <% } } nppRs.close();} catch(Exception ex){}
	                        		}
	                        	%>
	                    	</select>
                        </TD>
                     	
                        <TD class="plainlabel" valign="middle">Loại đơn hàng </TD>
                        <TD class="plainlabel" valign="middle">
                           <select name="trangthai" onchange="submitform();"  >
								<% if (obj.getTrangthai().equals("0")){%>
								  	<option value="0" selected>Đơn hàng ETC</option>
								  	<option value="1">Đơn hàng đối tác</option>
								  	<option value="2" >Đơn hàng khuyến mại</option>
								  	<option value="" ></option>
								<%}else if(obj.getTrangthai().equals("1")) {%>
								 	<option value="0" >Đơn hàng ETC</option>
								  	<option value="1" selected>Đơn hàng đối tác</option>
								  	<option value="2" >Đơn hàng khuyến mại</option>
								  	<option value="" ></option>
								<%}else if(obj.getTrangthai().equals("2")) {%>
								 	<option value="0" >Đơn hàng ETC</option>
								  	<option value="1">Đơn hàng đối tác</option>
								  	<option value="2" selected >Đơn hàng khuyến mại</option>
								  	<option value="" ></option>
								<%} else  {%>
								 	<option value="0" >Đơn hàng ETC</option>
								  	<option value="1">Đơn hàng đối tác</option>
								  	<option value="2">Đơn hàng khuyến mại</option>
								  	<option value="" selected ></option>
							<%} %>
                           </select>
                        </TD>  
                     
                    </TR>  
                    <TR>
						<TD  class="plainlabel">Số đơn hàng</TD>
						<TD  class="plainlabel" colspan="3"><INPUT name="sodh" type="text" size="30" value = '<%= obj.getSodh()%>' onChange = "submitform();"></TD>
								
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
            	<legend><span class="legendtitle"> Duyệt đơn hàng </span>&nbsp;&nbsp;
            	<%-- <%if(quyen[Utility.THEM]!=0){ %>
                	<a class="button3" href="javascript:newform()">
                           <img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>
                               <%} %> --%>
                </legend>
            	<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
					<TR class="tbheader">
	                    <TH align="center" style="width: 7%" >Mã đơn hàng</TH>
	                    <TH align="center" style="width: 7%" >Ngày đặt</TH>
	                    <TH align="center" style="width: 20%" >ETC / Đối tác</TH>
	                    <TH align="center" style="width: 10%" >Loại đơn hàng</TH>
	                    <TH align="center" style="width: 10%" >Trạng thái</TH>
	                    <TH align="center" style="width: 7%" >Ngày tạo</TH>
	                    <TH align="center" style="width: 11%" >Người tạo</TH>
	                    <TH align="center" style="width: 7%" >Ngày sửa</TH>
	                    <TH align="center" style="width: 11%" >Người sửa</TH>
	                    <TH align="center" style="width: 10%" >Tác vụ</TH>
	                </TR>
					<%
                 		if(nhapkhoRs != null)
                 		{
                 			int m = 0;
                 			while(nhapkhoRs.next())
                 			{  	
                 				String chuoiFORMAT = "";
                 				String trangthai = "Chờ duyệt";
                 				if(nhapkhoRs.getDouble("SOTIENTHU") > 0 )
                 				{
                 					
                 					trangthai = "Chờ duyệt <br /> (Chờ TT thêm) ";
                 					
                 			
                 				}
                 				if(nhapkhoRs.getString("trangthai").equals("4"))
                 					trangthai = "Đã duyệt <br /> ";
                 				if((m % 2 ) == 0) { 
                 					
                 					if(nhapkhoRs.getString("NOTE").trim().length() > 0)
                 						chuoiFORMAT = " style='color: red;' onMouseover=\"ddrivetip('" + nhapkhoRs.getString("NOTE") + "', 250)\"; onMouseout='hideddrivetip()' ";
                 				%>
		                         	<TR class='tbdarkrow' <%= chuoiFORMAT %> >
		                        <%}else{ %>
		                          	<TR class='tblightrow' <%= chuoiFORMAT %> >
		                        <%} %>
		                    <TD align="center"><%= nhapkhoRs.getString("PK_SEQ") %></TD>
		                    <TD align="center"><%= nhapkhoRs.getString("NGAYDONHANG") %></TD>
		                    <TD ><%= nhapkhoRs.getString("nppTEN") %></TD>  
		                    <TD align="center">
		                    	<%
		                    		String loaidonhang = "";
		                    		String ldh = nhapkhoRs.getString("loaidonhang");
		                    		String hopdong_fk = "";
		                    		if(nhapkhoRs.getString("hopdong_fk") != null )
		                    			hopdong_fk = nhapkhoRs.getString("hopdong_fk");
		                    		
		                    		if(hopdong_fk.trim().length() > 0 ) //NPP TAO
		                    			loaidonhang = "Đơn hàng ETC";
		                    		else 
	                    			{
		                    			loaidonhang = "Đơn hàng đối tác";
		                    			if(ldh.equals("4"))
		                    				loaidonhang = " Đơn hàng khác";
	                    			}
		                    	%>
		                    	<%= loaidonhang %>
		                    </TD>   	
		                    <TD align="center">
		                    	<%= trangthai %>
		                    </TD>   									                                    
					     	<TD align="center"><%= nhapkhoRs.getString("NGAYTAO") %></TD>	
		                    <TD align="center"><%= nhapkhoRs.getString("NGUOITAO") %></TD>
         					<TD align="center"><%= nhapkhoRs.getString("NGAYSUA") %></TD>
							<TD align="center"><%= nhapkhoRs.getString("NGUOISUA") %></TD>
									
		                    <TD align="center">   
	                   			<% 
	                   				if(hopdong_fk.trim().length() > 0) {  %>
	                   					<% if(nhapkhoRs.getString("trangthai").equals("4")){ %>
                             		<A href = "../../ErpDonhangNppETCUpdateSvl?userId=<%=userId%>&duyet=<%= nhapkhoRs.getString("PK_SEQ") %>"><IMG src="../images/Display20.png" alt="Xem đơn đặt hàng" title="Xem đơn đặt hàng" border=0></A>
	                    			<%} else { %>
                             		<A href = "../../ErpDonhangNppETCUpdateSvl?userId=<%=userId%>&duyet=<%= nhapkhoRs.getString("PK_SEQ") %>"><IMG src="../images/Chot.png" alt="Duyệt đơn đặt hàng" title="Duyệt đơn đặt hàng" border=0></A>
	                    			
	                    		<% }  } else {
	                    			
	                    				if(!ldh.equals("4")) { %>
	                    					<% if(nhapkhoRs.getString("trangthai").equals("4")){ %>
	                    					<A href = "../../ErpDondathangDoitacUpdateSvl?userId=<%=userId%>&duyet=<%= nhapkhoRs.getString("PK_SEQ") %>"><IMG src="../images/Display20.png" alt="Xem đơn đặt hàng" title="Xem đơn đặt hàng" border=0></A>
	                    					<%} else {%>
	                    					<A href = "../../ErpDondathangDoitacUpdateSvl?userId=<%=userId%>&duyet=<%= nhapkhoRs.getString("PK_SEQ") %>"><IMG src="../images/Chot.png" alt="Duyệt đơn đặt hàng" title="Duyệt đơn đặt hàng" border=0></A>
                    				<%}} else { %>
                    						<% if(nhapkhoRs.getString("trangthai").equals("4")) {%>
	                    					<A href = "../../ErpDonDatHangKhacUpdateSvl?userId=<%=userId%>&duyet=<%= nhapkhoRs.getString("PK_SEQ") %>"><IMG src="../images/Display20.png" alt="Xem đơn đặt hàng" title="Xem đơn đặt hàng" border=0></A>
	                    					<% } else {%>
	                    					<A href = "../../ErpDonDatHangKhacUpdateSvl?userId=<%=userId%>&duyet=<%= nhapkhoRs.getString("PK_SEQ") %>"><IMG src="../images/Chot.png" alt="Duyệt đơn đặt hàng" title="Duyệt đơn đặt hàng" border=0></A>
	                    			<%} }%>                   			
	                    		<% } 
	                   			 %>
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
</html><%}%>