<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.dms.distributor.beans.dondathang.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%@ page  import = "geso.dms.center.util.ChuyenNgu" %>
<% IErpDondathangNppList obj = (IErpDondathangNppList)session.getAttribute("obj"); %>
<% ResultSet nhapkhoRs =  obj.getDondathangRs(); %>
<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  %>
<% obj.setNextSplittings();
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{

		int[] quyen = new  int[6];
		quyen = util.Getquyen("ErpDondathangNppSvl","", userId);
%>
<% String nnId = (String)session.getAttribute("nnId"); %> 
<% if(nnId == null) {
	nnId = "vi"; 
	
	}	
String url = util.getChuyenNguUrl("ErpDondathangNppSvl","",nnId);	
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
	    //document.forms["ckParkForm"].nppId.value = "";
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

<form name="ckParkForm" method="post" action="../../ErpDondathangNppSvl"> 
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
    	<div style="width:70%; padding:5px; float:left" class="tbnavigation">
        	&nbsp;<%= " " + url %>
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	<%=ChuyenNgu.get("Ch??o m???ng b???n",nnId) %>  <%= obj.getNppTen() %> &nbsp;
        </div>
    </div>
  	<div id="cotent" style="width:100%; float:none">
    	<div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
        <fieldset style="margin-top:5px" >
            <legend class="legendtitle"> <%=ChuyenNgu.get("Ti??u ch?? t??m ki???m",nnId) %></legend>
                <TABLE width="100%" cellpadding="6px" cellspacing="0px" style="margin-top: 5px " >
                	 <TR>
                        <TD class="plainlabel" width="100px"><%=ChuyenNgu.get("T??? ng??y",nnId) %></TD>
                        <TD class="plainlabel" width="250px" >
                            <input type="text" class="days" name="tungay" value="<%= obj.getTungay() %>" maxlength="10" onchange="submitform();" />
                        </TD>
                    
                        <TD class="plainlabel" width="100px"><%=ChuyenNgu.get("?????n ng??y",nnId) %></TD>
                        <TD class="plainlabel">
                            <input type="text" class="days" name="denngay" value="<%= obj.getDenngay() %>" maxlength="10" onchange="submitform();" />
                        </TD>
                    </TR>
                    
                     <TR>
                     
                        <TD class="plainlabel" valign="middle"><%=ChuyenNgu.get("Tr???ng th??i",nnId) %> </TD>
                        <TD class="plainlabel" valign="middle" colspan="3" >
                           <select name="trangthai" onchange="submitform();"  >
								<% if (obj.getTrangthai().equals("0")){%>
								  	<option value="0" selected><%=ChuyenNgu.get("Ch??a ch???t",nnId) %></option>
								  	<option value="1"><%=ChuyenNgu.get("Ch??? duy???t",nnId) %></option>
								  	<option value="2" ><%=ChuyenNgu.get("???? duy???t",nnId) %></option>
								  	<option value="3" ><%=ChuyenNgu.get("???? h???y",nnId) %></option>
								  	<option value="4" ><%=ChuyenNgu.get("Ho??n t???t",nnId) %></option>
								  	<option value="" ></option>
								<%}else if(obj.getTrangthai().equals("1")) {%>
								 	<option value="0" ><%=ChuyenNgu.get("Ch??a ch???t",nnId) %></option>
								  	<option value="1" selected><%=ChuyenNgu.get("Ch??? duy???t",nnId) %></option>
								  	<option value="2" ><%=ChuyenNgu.get("???? duy???t",nnId) %></option>
								  	<option value="3" ><%=ChuyenNgu.get("???? h???y",nnId) %></option>
								  	<option value="4" ><%=ChuyenNgu.get("Ho??n t???t",nnId) %></option>
								  	<option value="" ></option>
								<%}else if(obj.getTrangthai().equals("2")) {%>
								 	<option value="0" ><%=ChuyenNgu.get("Ch??a ch???t",nnId) %></option>
								  	<option value="1"><%=ChuyenNgu.get("Ch??? duy???t",nnId) %></option>
								  	<option value="2" selected><%=ChuyenNgu.get("???? duy???t",nnId) %></option>
								  	<option value="3" ><%=ChuyenNgu.get("???? h???y",nnId) %></option>
								  	<option value="4" ><%=ChuyenNgu.get("Ho??n t???t",nnId) %></option>
								  	<option value="" ></option>
								<%}else if(obj.getTrangthai().equals("3")) {%>
								 	<option value="0" ><%=ChuyenNgu.get("Ch??a ch???t",nnId) %></option>
								  	<option value="1"><%=ChuyenNgu.get("Ch??? duy???t",nnId) %></option>
								  	<option value="2" ><%=ChuyenNgu.get("???? duy???t",nnId) %></option>
								  	<option value="3" selected ><%=ChuyenNgu.get("???? h???y",nnId) %></option>
								  	<option value="4" ><%=ChuyenNgu.get("Ho??n t???t",nnId) %></option>
								  	<option value="" ></option>
								<%}else if(obj.getTrangthai().equals("4")) {%>
								 	<option value="0" ><%=ChuyenNgu.get("Ch??a ch???t",nnId) %></option>
								  	<option value="1"><%=ChuyenNgu.get("Ch??? duy???t",nnId) %></option>
								  	<option value="2" ><%=ChuyenNgu.get("???? duy???t",nnId) %></option>
								  	<option value="3"  ><%=ChuyenNgu.get("???? h???y",nnId) %></option>
								  	<option value="4" selected ><%=ChuyenNgu.get("Ho??n t???t",nnId) %></option>
								  	<option value="" ></option>
								<%} else  {%>
								 	<option value="0" ><%=ChuyenNgu.get("Ch??a ch???t",nnId) %></option>
								  	<option value="1"><%=ChuyenNgu.get("Ch??? duy???t",nnId) %></option>
								  	<option value="2" ><%=ChuyenNgu.get("???? duy???t",nnId) %></option>
								  	<option value="3" ><%=ChuyenNgu.get("???? h???y",nnId) %></option>
								  	<option value="4" ><%=ChuyenNgu.get("Ho??n t???t",nnId) %></option>
								  	<option value="" selected ></option>
							<%} %>
                           </select>
                        </TD>  
                        
                                              
                    </TR>    
                    <tr>
                        <td colspan="4" class="plainlabel">
                            <a class="button" href="javascript:submitform()">
                                <img style="top: -4px;" src="../images/Search30.png" alt=""><%=ChuyenNgu.get("T??m ki???m",nnId) %> </a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <a class="button2" href="javascript:clearform()">
                                <img style="top: -4px;" src="../images/button.png" alt=""><%=ChuyenNgu.get("Nh???p l???i",nnId) %></a>&nbsp;&nbsp;&nbsp;&nbsp;
                        </td>
                    </tr>        					
                </TABLE>                      
        </fieldset>                      
    	</div>
        <div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
        	<fieldset>
            	<legend><span class="legendtitle"> <%=ChuyenNgu.get("????n ?????t h??ng",nnId) %> </span>&nbsp;&nbsp;

                	<a class="button3" href="javascript:newform()">
                           <img style="top: -4px;" src="../images/New.png" alt=""><%=ChuyenNgu.get("T???o m???i",nnId) %> </a>
                </legend>
            	<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
					<TR class="tbheader">
	                    <TH align="center" style="width: 8%" ><%=ChuyenNgu.get("M?? s???",nnId) %></TH>
	                    <TH align="center" style="width: 8%" ><%=ChuyenNgu.get("Ng??y ?????t",nnId) %></TH>
	                    <TH align="center" style="width: 18%" ><%=ChuyenNgu.get("Nh?? ph??n ph???i",nnId) %></TH>
	                    <TH align="center" style="width: 10%" ><%=ChuyenNgu.get("Kho ?????t",nnId) %></TH>
	                    <TH align="center" style="width: 10%" ><%=ChuyenNgu.get("Tr???ng th??i",nnId) %></TH>
	                    <TH align="center" style="width: 8%" ><%=ChuyenNgu.get("Ng??y t???o",nnId) %></TH>
	                    <TH align="center" style="width: 10%" ><%=ChuyenNgu.get("Ng?????i t???o",nnId) %></TH>
	                    <TH align="center" style="width: 8%" ><%=ChuyenNgu.get("Ng??y s???a",nnId) %></TH>
	                    <TH align="center" style="width: 10%" ><%=ChuyenNgu.get("Ng?????i s???a",nnId) %></TH>
	                    <TH align="center" style="width: 10%" ><%=ChuyenNgu.get("T??c v???",nnId) %></TH>
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
		                    <TD ><%= nhapkhoRs.getString("nppTEN") %></TD>  
		                    <TD ><%= nhapkhoRs.getString("khoTEN") %></TD>  
		                    	 <TD align="center">
		                    	<%
		                    		String trangthai = "";
		                    		String tt = nhapkhoRs.getString("trangthai");
		                    		String NPP_DACHOT = nhapkhoRs.getString("NPP_DACHOT");
		                    		String TT_DuyetLai = nhapkhoRs.getString("TT_DuyetLai")==null?"":nhapkhoRs.getString("TT_DuyetLai");
		                    		
		                    		if(tt.equals("0") && NPP_DACHOT.equals("0") ) //NPP TAO
		                    			{
		                    				trangthai = "Ch??a ch???t";
		                    				if(TT_DuyetLai.equals("1"))
		                    					trangthai += " (TT ch???nh s???a ??H) ";
		                    			}
		                    		else
		                    		{
		                    			if(tt.equals("1")||(tt.equals("0") && NPP_DACHOT.equals("1")))
		                    			{
		                    				trangthai = "Ch??? duy???t";
		                    			}
		                    			else if(tt.equals("2"))
		                    			{
			                    			trangthai = "???? duy???t";
		              						if(nhapkhoRs.getInt("DaXuatKho") >0 )
		              							trangthai="???? xu???t kho ";
		                    			}
		                    			else if(tt.equals("3"))
		                    			{
		                    				trangthai = "???? h???y";
	                    				}
		                    			else if(tt.equals("4"))
		                    			{
		                    				trangthai = "Ho??n t???t";
	                    				}
		                    		}
		                    	%>
		                    	<%= trangthai %>
		                    </TD>   									                                    
					     	<TD align="center"><%= nhapkhoRs.getString("NGAYTAO") %></TD>	
		                    <TD align="center"><%= nhapkhoRs.getString("NGUOITAO") %></TD>
         					<TD align="center"><%= nhapkhoRs.getString("NGAYSUA") %></TD>
							<TD align="center"><%= nhapkhoRs.getString("NGUOISUA") %></TD>
									
		                    <TD align="center"> 
		                    <% if(tt.equals("0") && NPP_DACHOT.equals("0") ){ %>
		                    <%-- <A href="../../ErpDondathangExcelSvl?userId=<%=userId%>&excelDetails=<%=nhapkhoRs.getString("PK_SEQ") %>"> <img src="../images/excel.gif" alt="In ????n h??ng chi ti???t Pdf" width="20" height="20" longdesc="In file Pdf" border=0> </A> --%>                              										
                                <A href="../../RouterSvl?task=<%= Utility.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "ErpDondathangExcelSvl?userId=" + userId+ "&excelDetails="+ nhapkhoRs.getString("PK_SEQ")) %>"> <img src="../images/excel.gif" alt="In ????n h??ng chi ti???t Pdf" width="20" height="20" longdesc="In file Pdf" border=0> </A>
                                <%-- <A href = "../../ErpDondathangNppUpdateSvl?userId=<%=userId%>&update=<%=nhapkhoRs.getString("PK_SEQ") %>"><IMG src="../images/Edit20.png" alt="C???p nh???t" title="C???p nh???t" border=0></A>&nbsp; --%>
                                <A href = "../../RouterSvl?task=<%= Utility.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "ErpDondathangNppUpdateSvl?userId=" + userId+ "&update="+ nhapkhoRs.getString("PK_SEQ")) %>"><IMG src="../images/Edit20.png" alt="C???p nh???t" title="C???p nh???t" border=0></A>&nbsp;
                                <%-- <A href = "../../ErpDondathangNppSvl?userId=<%=userId%>&chot=<%= nhapkhoRs.getString("PK_SEQ") %>&loaidonhang=<%= obj.getLoaidonhang() %>"><img src="../images/Chot.png" alt="Ch???t" title="Ch???t" width="20" height="20" border=0 onclick="if(!confirm('B???n c?? mu???n ch???t ????n ?????t h??ng n??y?')) return false;"></A>&nbsp; --%>
                              	<A href = "../../RouterSvl?task=<%= Utility.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "ErpDondathangNppSvl?userId=" + userId+ "&chot="+ nhapkhoRs.getString("PK_SEQ")+"&loaidonhang="+obj.getLoaidonhang()) %>"><img src="../images/Chot.png" alt="Ch???t" title="Ch???t" width="20" height="20" border=0 onclick="if(!confirm('B???n c?? mu???n ch???t ????n ?????t h??ng n??y?')) return false;"></A>&nbsp;
                              	<%-- <A href = "../../ErpDondathangNppSvl?userId=<%=userId%>&delete=<%= nhapkhoRs.getString("PK_SEQ") %>&loaidonhang=<%= obj.getLoaidonhang() %>"><img src="../images/Delete20.png" alt="X??a" title="X??a" width="20" height="20" border=0 onclick="if(!confirm('B???n c?? mu???n x??a ????n ?????t h??ng n??y?')) return false;"></A> --%>
		                    	<A href = "../../RouterSvl?task=<%= Utility.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "ErpDondathangNppSvl?userId=" + userId+ "&delete="+ nhapkhoRs.getString("PK_SEQ")+"&loaidonhang="+obj.getLoaidonhang()) %>"><img src="../images/Delete20.png" alt="X??a" title="X??a" width="20" height="20" border=0 onclick="if(!confirm('B???n c?? mu???n x??a ????n ?????t h??ng n??y?')) return false;"></A>
		                    <%} else{ %>
		                    	<%-- <A href = "../../ErpDondathangNppUpdateSvl?userId=<%=userId%>&display=<%= nhapkhoRs.getString("PK_SEQ") %>"><IMG src="../images/Display20.png" alt="Hi???n th???" title="Hi???n th???" border=0></A> --%>
		                    	<A href = "../../RouterSvl?task=<%= Utility.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "ErpDondathangNppUpdateSvl?userId=" + userId+ "&display="+ nhapkhoRs.getString("PK_SEQ")) %>"><IMG src="../images/Display20.png" alt="Hi???n th???" title="Hi???n th???" border=0></A>
		                    	
		                    	<%-- <A href="../../ErpDondathangExcelSvl?userId=<%=userId%>&excelDetails=<%=nhapkhoRs.getString("PK_SEQ") %>"> <img src="../images/excel.gif" alt="In ????n h??ng chi ti???t Pdf" width="20" height="20" longdesc="In file Pdf" border=0> </A> --%>
		                    	<A href="../../RouterSvl?task=<%= Utility.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "ErpDondathangExcelSvl?userId=" + userId+ "&excelDetails="+ nhapkhoRs.getString("PK_SEQ")) %>"> <img src="../images/excel.gif" alt="In ????n h??ng chi ti???t Pdf" width="20" height="20" longdesc="In file Pdf" border=0> </A>
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
</html>

<%
	if(nhapkhoRs!= null) { nhapkhoRs.close(); nhapkhoRs = null ;}
	obj.DBclose(); obj = null;
	session.setAttribute("obj",null);

	}%>