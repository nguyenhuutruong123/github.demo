<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.dms.distributor.beans.hoadontaichinhNPP.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.text.DecimalFormat"%>

<%
NumberFormat formatter = new DecimalFormat("#,###,###");
IErpHoadontaichinhNPPList obj = (IErpHoadontaichinhNPPList)session.getAttribute("obj"); %>
<% ResultSet nhapkhoRs =  obj.getDondathangRs(); %>
<% ResultSet khRs = obj.getKhRs(); %>
<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  
   NumberFormat formater = new DecimalFormat("##,###,###");%>
<% obj.setNextSplittings();
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/Diageo/index.jsp");
	}else{
	/* 	int[] quyen = new  int[6];
		quyen = util.Getquyen("ErpHoadontaichinhNPPSvl","",userId);
 */
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
	    document.forms["ckParkForm"].khTen.value = "";
	    document.forms["ckParkForm"].sohoadon.value = "";
	    document.forms["ckParkForm"].sodonhang.value = "";
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

<form name="ckParkForm" method="post" action="../../ErpHoadontaichinhNPPSvl"> 
 <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="action" value="1" >
<input type="hidden" name="msg" value='<%= obj.getMsg() %>'>
<input type="hidden" name="nppId" value='<%= obj.getnppId() %>'>
<input type="hidden" name="currentPage" value="<%= obj.getCurrentPage() %>" >
<script language="javascript" type="text/javascript">
    thongbao();
</script> 

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:55%; padding:5px; float:left" class="tbnavigation">
        	&nbsp;Qu???n l?? b??n h??ng > B??n h??ng cho NPP/ ?????i l?? > Xu???t H??TC 
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Ch??o m???ng B???n <%= userTen %> &nbsp;
        </div>
    </div>
  	<div id="cotent" style="width:100%; float:none">
    	<div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
        <fieldset style="margin-top:5px" >
            <legend class="legendtitle"> Ti??u ch?? t??m ki???m</legend>
                <TABLE width="100%" cellpadding="6px" cellspacing="0px" style="margin-top: 5px " >
                	 <TR>
                        <TD class="plainlabel" width="100px">T??? ng??y</TD>
                        <TD class="plainlabel" width="250px" >
                            <input type="text" class="days" name="tungay" value="<%= obj.getTungay() %>" maxlength="10" onchange="submitform();" />
                        </TD>
                    
                        <TD class="plainlabel" width="100px">?????n ng??y</TD>
                        <TD class="plainlabel" colspan="4">
                            <input type="text" class="days" name="denngay" value="<%= obj.getDenngay() %>" maxlength="10" onchange="submitform();" />
                        </TD>
                    </TR>
                    
                     <TR>
                     
                     	<TD class="plainlabel" width="100px">Kh??ch h??ng</TD>
                        <TD class="plainlabel">
                            <select name = "khTen" class="select2" style="width: 200px" onchange="submitform();" >
	                    		<option value=""> </option>
	                        	<%
	                        		if(khRs != null)
	                        		{
	                        			try
	                        			{
	                        			while(khRs.next())
	                        			{  
	                        			if( khRs.getString("pk_seq").equals(obj.getKhTen())){ %>
	                        				<option value="<%= khRs.getString("pk_seq") %>" selected="selected" ><%= khRs.getString("ten") %></option>
	                        			<%}else { %>
	                        				<option value="<%= khRs.getString("pk_seq") %>" ><%= khRs.getString("ten") %></option>
	                        		 <% } } khRs.close();} catch(Exception ex){}
	                        		}
	                        	%>
	                    	</select>
                        </TD>
                     	
                        <TD class="plainlabel" valign="middle">Tr???ng th??i </TD>
                        <TD class="plainlabel" valign="middle" colspan="4">
                           <select name="trangthai" onchange="submitform();"  >
								<% if (obj.getTrangthai().equals("1")){%>
									<option value="" ></option>
								  	<option value="1" selected>Ch??? x??c nh???n</option>
								  	<option value="2">???? x??c nh???n</option>
								  	<option value="3" >???? x??a</option>
								  	<option value="4" >???? in H??</option>
								  	<option value="5" >???? h???y</option>
								<%}else if(obj.getTrangthai().equals("2")) {%>
								 	<option value="" ></option>
								 	<option value="1" >Ch??? x??c nh???n</option>
								  	<option value="2" selected>???? x??c nh???n</option>
								  	<option value="3" >???? x??a</option>
								  	<option value="4" >???? in H??</option>
								  	<option value="5" >???? h???y</option>
								<%}else if(obj.getTrangthai().equals("3")) {%>
								 	<option value="" ></option>
								 	<option value="1" >Ch??? x??c nh???n</option>
								  	<option value="2" >???? x??c nh???n</option>
								  	<option value="3" selected>???? x??a</option> 
								  	<option value="4" >???? in H??</option> 
								  	<option value="5" >???? h???y</option>	
								<%}else if(obj.getTrangthai().equals("4")) {%>
									<option value="" ></option>
								 	<option value="1" >Ch??? x??c nh???n</option>
								  	<option value="2" >???? x??c nh???n</option>
								  	<option value="3" >???? x??a </option> 
								  	<option value="4" selected>???? in H??</option> 
								  	<option value="5" >???? h???y</option>
								<% }else if(obj.getTrangthai().equals("5")) {%>
									<option value="" ></option>
								 	<option value="1" >Ch??? x??c nh???n</option>
								  	<option value="2" >???? x??c nh???n</option>
								  	<option value="3" >???? x??a </option> 
								  	<option value="4" >???? in H??</option> 
								  	<option value="5" selected>???? h???y</option>
							  	<% }else {%>
									<option value="" selected></option>
								 	<option value="1" >Ch??? x??c nh???n</option>
								  	<option value="2">???? x??c nh???n</option>
								  	<option value="3"  >???? x??a </option>
								  	<option value="4" >???? in H??</option>
								  	<option value="5" >???? h???y</option>
							<%} %>
                           </select>
                        </TD>  
                        
                                          
                    </TR>
                 <TR>
							<TD  class="plainlabel">S??? h??a ????n</TD>
							<TD  class="plainlabel" ><INPUT name="sohoadon" type="text" size="30" value = '<%= obj.getSohoadon()%>' onChange = "submitform();"></TD>
							<TD  class="plainlabel">S??? ????n h??ng</TD>
							<TD  class="plainlabel"  colspan="4"><INPUT name="sodonhang" type="text" size="30" value = '<%= obj.getSodonhang()%>' onChange = "submitform();"></TD>
									
					</TR>
					
					<%if(obj.getIsSearch()){ %>
								<TR><TD class="plainlabel" colspan="6"></TD>
								<TR>
									<TD class="plainlabel" >Doanh s???</TD>
									<td class="plainlabel"><input type="text" name="ds" size="6" value="<%= formatter.format(obj.getTongTruoc()) %>"></td>
									<TD class="plainlabel" >Thu???</TD>
									<td class="plainlabel"><input type="text" name="ck" size="6" value="<%= formatter.format(obj.getTongCK()) %>"></td>
									<TD class="plainlabel" >Doanh thu</TD>
									<td class="plainlabel"><input type="text" name="dt" size="6" value="<%= formatter.format(obj.getTongSau()) %>"></td>
							
								</TR>
								<%} %>       
								
                    <tr>
                        <td colspan="6" class="plainlabel">
                            <a class="button" href="javascript:submitform()">
                                <img style="top: -4px;" src="../images/Search30.png" alt="">T??m ki???m </a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <a class="button2" href="javascript:clearform()">
                                <img style="top: -4px;" src="../images/button.png" alt="">Nh???p l???i</a>&nbsp;&nbsp;&nbsp;&nbsp;
                        </td>
                    </tr> 
                    
                    					
                </TABLE>                      
        </fieldset>                      
    	</div>
        <div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
        	<fieldset>
            	<legend><span class="legendtitle"> Xu???t h??a ????n t??i ch??nh</span>&nbsp;&nbsp;
            	
            	<%--   <%if(quyen[Utility.THEM]!=0){ %> --%>
                	<a class="button3" href="javascript:newform()">
                    <img style="top: -4px;" src="../images/New.png" alt="">T???o m???i </a> 
                <%--  <%} %>    --%>
                 
                </legend>
            	<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
					<TR class="tbheader">
	                    <TH align="center" style="width: 8%" >M?? s???</TH>
	                    <TH align="center" style="width: 8%" >Ng??y xu???t HD</TH>
	                    <TH align="center" style="width: 8%" >S??? h??a ????n</TH>
	                     <TH align="center" style="width: 5%" >K??nh b??n h??ng</TH>
	                    <TH align="center" style="width: 13%" >Kh??ch h??ng ETC / ?????i t??c</TH>
	                    
	                    <TH align="center" style="width: 8%" >Tr???ng th??i</TH>
	                    <TH align="center" style="width: 8%" >T???ng ti???n</TH>
	                    <TH align="center" style="width: 8%" >Ng??y t???o</TH>
	                    <TH align="center" style="width: 8%" >Ng?????i t???o</TH>
	                    <TH align="center" style="width: 8%" >Ng??y s???a</TH>
	                    <TH align="center" style="width: 8%" >Ng?????i s???a</TH>
	                    <TH align="center" style="width: 10%" >T??c v???</TH>
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
		                    <TD align="center"><%= nhapkhoRs.getString("NGAYXUATHD") %></TD>
		                    <TD align="center"><%= nhapkhoRs.getString("SOHOADON") %></TD>
		                     <TD align="center"><%= nhapkhoRs.getString("kenhbanhang") %></TD>
		                    <TD ><%= nhapkhoRs.getString("khTEN") %></TD>  
		                    	 <TD align="center">
		                    	<%
		                    		String trangthai = "";
		                    		String tt = nhapkhoRs.getString("trangthai");
		                    		if(tt.equals("1")){ //NPP TAO
		                    			trangthai = "Ch??? x??c nh???n";
		                    		}else
		                    		{
		                    			if(tt.equals("2")) {
			                    			trangthai = "???? x??c nh???n";
		                    			}else{
		                    				if(tt.equals("4")) 
		                    					trangthai = "???? in H??";
		                    				else
		                    				{
		                    					if(tt.equals("3")) 
			                    					trangthai = "???? x??a";
		                    					else 
		                    						trangthai = "???? h???y";
		                    				}
		                    			}
		                    			
		                    		}
		                    	%>
		                    	<%= trangthai %>
		                    </TD>   
		                    <TD align="center"><%= formater.format(nhapkhoRs.getDouble("TONGTIEN")) %></TD>									                                    
					     	<TD align="center"><%= nhapkhoRs.getString("NGAYTAO") %></TD>	
		                    <TD align="center"><%= nhapkhoRs.getString("NGUOITAO") %></TD>
         					<TD align="center"><%= nhapkhoRs.getString("NGAYSUA") %></TD>
							<TD align="center"><%= nhapkhoRs.getString("NGUOISUA") %></TD>
									
		                    <TD align="center"> 
		                    <%
		                     if(tt.equals("1")){ %>
								<%-- <%if(quyen[Utility.SUA]!=0){ %> --%>
									  <A href = "../../ErpHoadontaichinhNPPUpdateSvl?userId=<%=userId%>&update=<%=nhapkhoRs.getString("PK_SEQ") %>"><IMG src="../images/Edit20.png" alt="C???p nh???t" title="C???p nh???t" border=0></A>&nbsp;
                            <%--     <%} %>
                                 --%>
                              <%--   <%if(quyen[Utility.CHOT]!=0){ %> --%>
                                	<A href = "../../ErpHoadontaichinhNPPSvl?userId=<%=userId%>&chot=<%= nhapkhoRs.getString("PK_SEQ") %>"><img src="../images/Chot.png" alt="Duy???t xu???t kho" title="Duy???t xu???t kho" width="20" height="20" border=0 ></A>&nbsp;
                              	<%-- <%} %> --%>
                              	
                              	<%-- <%if(quyen[Utility.XOA]!=0){ %> --%>
                              		<A href = "../../ErpHoadontaichinhNPPSvl?userId=<%=userId%>&delete=<%= nhapkhoRs.getString("PK_SEQ") %>"><img src="../images/Delete20.png" alt="X??a h??a ????n" title="X??a h??a ????n" width="20" height="20" border=0 onclick="if(!confirm('B???n c?? mu???n x??a h??a ????n n??y?')) return false;"></A>									
		                  		<%-- <%} %>	 --%>
		                  			                  		
		                    <%} else{ %>
		                    	<%-- 
		                    	<%if(quyen[Utility.XEM]!=0){ %> --%>
		                    		<A href = "../../ErpHoadontaichinhNPPUpdateSvl?userId=<%=userId%>&display=<%= nhapkhoRs.getString("PK_SEQ") %>"><IMG src="../images/Display20.png" alt="Hi???n th???" title="Hi???n th???" border=0></A>
		                   		<%-- <%} %> --%>
		                   		
		                   	<%-- 	<%  if( !tt.equals("3") && !tt.equals("5") ) { %> --%>	
		                   			<A href = "../../ErpHoadontaichinhNPPPdfSvl?userId=<%=userId%>&pdf=<%= nhapkhoRs.getString("PK_SEQ") %>&nppId=<%= obj.getnppId()%>"><IMG src="../images/Printer30.png" alt="In" title="In" width="20" height="20" border=0></A>
		                    	<%-- <% } %> --%>
		                    	

                              	<%  if( !tt.equals("3") && !tt.equals("5") ) { %>
		                    		
		                    	<%-- 	<%if(quyen[Utility.XOA]!=0){ %> --%>
	                              	<A href = "../../ErpHoadontaichinhNPPSvl?userId=<%=userId%>&delete=<%= nhapkhoRs.getString("PK_SEQ") %>"><img src="../images/Delete_Icon.png" alt="X??a h??a ????n" title="X??a h??a ????n" width="20" height="20" border=0 onclick="if(!confirm('B???n c?? x??a h??a ????n n??y?')) return false;"></A>									
			                   		<%-- <%} %> --%>
		                    		
		                    	<%-- 	<%if(quyen[Utility.XOA]!=0){ %> --%>
		                    		<A href = "../../ErpHoadontaichinhNPPSvl?userId=<%=userId%>&huy=<%= nhapkhoRs.getString("PK_SEQ") %>"><img src="../images/Delete20.png" alt="H???y h??a ????n" title="H???y h??a ????n" width="20" height="20" border=0 onclick="if(!confirm('B???n c?? mu???n h???y h??a ????n n??y?')) return false;"></A>	
		                    	<%-- 	<%} %> --%>
		                    		
		                    	<% } %>	
                              		
		                    	
		                    <% 
		                       }%>
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