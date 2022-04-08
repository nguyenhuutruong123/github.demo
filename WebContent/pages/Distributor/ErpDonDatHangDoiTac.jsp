<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.dms.distributor.beans.dondathang.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>
<% IErpDondathangDoitacList obj = (IErpDondathangDoitacList)session.getAttribute("obj"); %>
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
		int[] quyen = new  int[6];
		quyen = util.Getquyen("ErpDondathangDoitacSvl","&loaidonhang=0",userId);
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
	    document.forms["ckParkForm"].nppId.value = "";
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

<form name="ckParkForm" method="post" action="../../ErpDondathangDoitacSvl"> 
 <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="action" value="1" >
<input type="hidden" name="msg" value='<%= obj.getMsg() %>'>
<input type="hidden" name="loaidonhang" value='<%= obj.getLoaidonhang() %>'>
<input type="hidden" name = "nppId" value="<%= obj.getNppId() %>" > 
<input type="hidden" name="currentPage" value="<%= obj.getCurrentPage() %>" >
<script language="javascript" type="text/javascript">
    thongbao();
</script> 

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:55%; padding:5px; float:left" class="tbnavigation">
        	&nbsp;Quản lý bán hàng > Bán hàng cho ETC / Đối tác > Bán hàng cho đối tác
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng bạn <%= obj.getNppTen() %> &nbsp;
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
                     
                        <TD class="plainlabel" valign="middle">Trạng thái </TD>
                        <TD class="plainlabel" valign="middle"  >
                           <select name="trangthai" onchange="submitform();"  >
								<% if (obj.getTrangthai().equals("0")){%>
								  	<option value="0" selected>Chưa chốt</option>
								  	<option value="1">Chờ duyệt</option>
								  	<option value="2" >Đã duyệt</option>
								  	<option value="3" >Đã hủy</option>
								  	<option value="4" >Hoàn tất</option>
								  	<option value="" ></option>
								<%}else if(obj.getTrangthai().equals("1")) {%>
								 	<option value="0" >Chưa chốt</option>
								  	<option value="1" selected>Chờ duyệt</option>
								  	<option value="2" >Đã duyệt</option>
								  	<option value="3" >Đã hủy</option>
								  	<option value="4" >Hoàn tất</option>
								  	<option value="" ></option>
								<%}else if(obj.getTrangthai().equals("2")) {%>
								 	<option value="0" >Chưa chốt</option>
								  	<option value="1">Chờ duyệt</option>
								  	<option value="2" selected>Đã duyệt</option>
								  	<option value="3" >Đã hủy</option>
								  	<option value="4" >Hoàn tất</option>
								  	<option value="" ></option>
								<%}else if(obj.getTrangthai().equals("3")) {%>
								 	<option value="0" >Chưa chốt</option>
								  	<option value="1">Chờ duyệt</option>
								  	<option value="2" >Đã duyệt</option>
								  	<option value="3" selected >Đã hủy</option>
								  	<option value="4" >Hoàn tất</option>
								  	<option value="" ></option>
								<%}else if(obj.getTrangthai().equals("4")) {%>
								 	<option value="0" >Chưa chốt</option>
								  	<option value="1">Chờ duyệt</option>
								  	<option value="2" >Đã duyệt</option>
								  	<option value="3"  >Đã hủy</option>
								  	<option value="4" selected >Hoàn tất</option>
								  	<option value="" ></option>
								<%} else  {%>
								 	<option value="0" >Chưa chốt</option>
								  	<option value="1">Chờ duyệt</option>
								  	<option value="2" >Đã duyệt</option>
								  	<option value="3" >Đã hủy</option>
								  	<option value="4" >Hoàn tất</option>
								  	<option value="" selected ></option>
							<%} %>
                           </select>
                        </TD>  
                        
                        <TD class="plainlabel" >Số đơn hàng</TD>
                        <TD class="plainlabel">
                            <input type="text" name="sodonhang" value="<%= obj.getSodonhang() %>"  onchange="submitform();" />
                        </TD>                      
                    </TR>  
                    <TR>
						<TD class="plainlabel" width="100px">Đối tác</TD>
						<TD class="plainlabel"  ><select name="khId" class="select2"
							style="width: 200px" onchange="submitform();">
								<option value=""></option>
								<%
                       		if(nppRs != null)
                       		{
                       			try
                       			{
                       			while(nppRs.next())
                       			{  
                       			if( nppRs.getString("pk_seq").equals(obj.getKhTen())){ %>
								<option value="<%= nppRs.getString("pk_seq") %>"
									selected="selected"><%= nppRs.getString("ten") %></option>
								<%}else { %>
								<option value="<%= nppRs.getString("pk_seq") %>"><%= nppRs.getString("ten") %></option>
								<% } } nppRs.close();} catch(Exception ex){}
                       		}
                       	%>
						</select></TD>
						 <TD class="plainlabel" >Mã đặt hàng</TD>
                        <TD class="plainlabel">
                            <input type="text" name="madhdt" value="<%= obj.getMaddh() %>"  onchange="submitform();" />
                        </TD>  
					</TR>  
					
					<TR>
                    	<TD class="plainlabel"  > Loại đơn hàng </td>
                    	<TD width="300px" class="plainlabel" valign="top"  >
                    	<% 	String[] tthai = new  String[] {"Đơn hàng bán","Đơn hàng KM có HĐTC ","All"  } ;
							String[] idTrangThai = new  String[] {"0","1",""} ;%> 
						<SELECT name="iskm" >
		      		 <% for( int i=0;i<tthai.length;i++) { 
		    			if(idTrangThai[i].equals(obj.getIsKm()) ){ %>
		      				<option value='<%=idTrangThai[i]%>' selected><%=tthai[i] %></option>
		      		 	<%}else{ %>
		     				<option value='<%=idTrangThai[i]%>'><%=tthai[i] %></option>
		     			<%} 
		      		 }
		      		 	%>
					       	</SELECT>
                    	
                    </TD>
                    
                    <TD class="plainlabel" colspan="3" ></TD>
                         
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
            	<legend><span class="legendtitle"> Bán hàng cho đối tác </span>&nbsp;&nbsp;
				<%if(quyen[Utility.THEM]!=0){ %>
                	<a class="button3" href="javascript:newform()">
                        <img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>
                <%} %>   
                </legend>
            	<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
					<TR class="tbheader">
	                    <TH align="center" width="8%" >Mã số</TH>
	                    <TH align="center" width="8%" >Ngày đặt</TH>
	                     <TH align="center" width="8%" >Mã đơn hàng</TH>
	                    <TH align="center" width="18%" >Đối tác</TH>
	                    <TH align="center" width="8%" >Kênh bán hàng</TH>
	                    <TH align="center" width="10%" >Kho đặt</TH>
	                    <TH align="center" width="10%" >Trạng thái</TH>
	                    <!-- <TH align="center" width="8%" >Ngày tạo</TH> -->
	                    <!-- <TH align="center" width="10%" >Người tạo</TH> -->
	                    <TH align="center" width="8%" >Ngày sửa</TH>
	                    <TH align="center" width="10%" >Người sửa</TH>
	                    <TH align="center" style="width: 6%" >Ngày giờ chốt</TH>
	                    <TH align="center" width="10%" >Tác vụ</TH>
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
		                    <TD align="center"><%= nhapkhoRs.getString("NGAYDONHANG") %></TD>
		                     <TD align="center"><%= nhapkhoRs.getString("maddh") %><%=nhapkhoRs.getString("isdhkhac").equals("1")?" (KM)":" (Bán)" %>   </TD>
		                    <TD ><%= nhapkhoRs.getString("nppTEN") %></TD> 
		                    <TD  align="center" ><%= nhapkhoRs.getString("KenhBanHang") %></TD> 
		                    <TD ><%= nhapkhoRs.getString("khoTEN") %></TD>  
		                    	 <TD align="center">
		                    	<%
		                    		String trangthai = "";
		                    		String tt = nhapkhoRs.getString("trangthai");
		                    		String NPP_DACHOT = nhapkhoRs.getString("NPP_DACHOT");
		                    		
		                    		if(tt.equals("0")  ) //NPP TAO
		                    			trangthai = "Chưa chốt";
		                    		else
		                    		{
		                    			if(tt.equals("1") && NPP_DACHOT.equals("1") )
		                    			{
		                    				trangthai = "Chờ duyệt";
		                    			}
		                    			else if(tt.equals("2"))
		                    			{
			                    			trangthai = "Đã duyệt";
		                    			}
		                    			else if(tt.equals("3"))
		                    			{
		                    				trangthai = "Đã hủy";
	                    				}
		                    			else if(tt.equals("4"))
		                    			{
		                    				trangthai = "Hoàn tất";
	                    				}
		                    		}
		                    	%>
		                    	<%= trangthai %>
		                    </TD>   									                                    
					     	<%-- <TD align="center"><%= nhapkhoRs.getString("NGAYTAO") %></TD>	 --%>
		                    <%-- <TD align="center"><%= nhapkhoRs.getString("NGUOITAO") %></TD> --%>
         					<TD align="center"><%= nhapkhoRs.getString("NGAYSUA") %></TD>
							<TD align="center"><%= nhapkhoRs.getString("NGUOISUA") %></TD>
							<TD align="center"><%= nhapkhoRs.getString("ngaygiochot") %></TD>
									
		                    <TD align="center"> 
		                    <% if(tt.equals("0")  ){ %>
		                    <%if(quyen[Utility.SUA]!=0){ %>
                                <A href = "../../ErpDondathangDoitacUpdateSvl?userId=<%=userId%>&update=<%=nhapkhoRs.getString("PK_SEQ") %>"><IMG src="../images/Edit20.png" alt="Cập nhật" title="Cập nhật" border=0></A>&nbsp;
                              <%} %>
                               <%if(quyen[Utility.CHOT]!=0){ %>
                                <A href = "../../ErpDondathangDoitacSvl?userId=<%=userId%>&chot=<%= nhapkhoRs.getString("PK_SEQ") %>&loaidonhang=<%= obj.getLoaidonhang() %>"><img src="../images/Chot.png" alt="Chốt" title="Chốt" width="20" height="20" border=0 onclick="if(!confirm('Bạn có muốn chốt đơn hàng này?')) return false;"></A>&nbsp;
                              	  <%} %>
                              	 <%if(quyen[Utility.XOA]!=0){ %>
                              	<A href = "../../ErpDondathangDoitacSvl?userId=<%=userId%>&delete=<%= nhapkhoRs.getString("PK_SEQ") %>&loaidonhang=<%= obj.getLoaidonhang() %>"><img src="../images/Delete20.png" alt="Xóa" title="Xóa" width="20" height="20" border=0 onclick="if(!confirm('Bạn có muốn xóa đơn hàng này?')) return false;"></A>									
		                   		 <%} %>
		                    <%} else{ %>
		                    	<% if(tt.equals("1")) { %>
		                    	<%if(quyen[Utility.HUYCHOT]!=0){ %>
		                    		<A href = "../../ErpDondathangDoitacSvl?userId=<%=userId%>&unchot=<%= nhapkhoRs.getString("PK_SEQ") %>&loaidonhang=<%= obj.getLoaidonhang() %>"><img src="../images/unChot.png" alt="Mở Chốt" title="Mở Chốt" width="20" height="20" border=0 onclick="if(!confirm('Bạn có muốn mở chốt đơn hàng này?')) return false;"></A>&nbsp;
		                    	<% } %>
		                    <% } %>
		                    <%if(quyen[Utility.XEM]!=0){ %>
		                    	<A href = "../../ErpDondathangDoitacUpdateSvl?userId=<%=userId%>&display=<%= nhapkhoRs.getString("PK_SEQ") %>"><IMG src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" border=0></A>
		                    <% } %>
		                    <% } %>
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