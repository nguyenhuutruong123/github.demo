<%@page import="geso.dms.center.servlets.reports.NPPPerformance"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.beans.dieuchinhtonkho.IDieuchinhtonkhoList" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DecimalFormat"%>
<%@ page  import = "geso.dms.center.util.*" %>
<%@ page  import = "geso.dms.center.util.ChuyenNgu" %>
<%	
	NumberFormat formatter = new DecimalFormat("#,###,###"); 
	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		/* response.sendRedirect("/AHF/index.jsp"); */
		response.sendRedirect("/AHF/error_page.jsp");
	}else{int[] quyen = new  int[6];
	quyen = util.Getquyen("DuyetdctkSvl","",userId);
	
	System.out.println(quyen[0]);
	System.out.println(quyen[1]);
	System.out.println(quyen[2]);
	System.out.println(quyen[3]);	
	System.out.println(quyen[4]); %>

<% IDieuchinhtonkhoList obj = (IDieuchinhtonkhoList)session.getAttribute("obj"); %>
<% ResultSet dctklist = (ResultSet)obj.getDctkList(); %>
<% ResultSet dvkd = (ResultSet)obj.getDvkd(); %>
<% ResultSet kbh = (ResultSet)obj.getKbh(); %>
<% ResultSet kho = (ResultSet)obj.getKho(); %>
<% ResultSet nhaPhanPhoiRs = (ResultSet) obj.getNhaPhanPhoiRs(); %>
<% String nnId = (String)session.getAttribute("nnId"); %> 
<% if(nnId == null) {
	nnId = "vi"; 
	
	}	
String url = util.getChuyenNguUrl("DuyetdctkSvl","",nnId);	
	%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">


<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">


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
        }); 		
		
</script>

<script type="text/javascript" src="../scripts/jquery.js"></script> 
<link rel="stylesheet" href="../css/jqueryautocomplete.css" type="text/css" />
<script type="text/javascript" src="../scripts/jqueryautocomplete.js"></script>



	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<SCRIPT language="JavaScript" type="text/javascript">
function clearform()
{
    document.dctkForm.dvkdId.selectedIndex = 0;
    document.dctkForm.kbhId.selectedIndex = 0;
    document.dctkForm.khoId.selectedIndex = 0;
    document.dctkForm.nppId.selectedIndex = 0;    
    
    document.dctkForm.tungay.value = "";
    document.dctkForm.denngay.value = "";
    document.dctkForm.trangthai.value = "";
    document.dctkForm.sochungtu.value = "";
    submitform();
}
function thongbao()
{   var tt = document.forms['dctkForm'].msg.value;
	if(tt.length>0)
    	alert(document.forms['dctkForm'].msg.value);
	
	document.forms['dctkForm'].msg.value= '';
}
function submitform()
{   
   document.forms['dctkForm'].action.value= 'search';
   document.forms["dctkForm"].submit();
}
function Xoa(id){
	
	document.forms['dctkForm'].IdXoa.value = id;
	document.forms['dctkForm'].action.value= 'Xoa';
	document.forms['dctkForm'].submit();
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

<form name="dctkForm" method="post" action="../../DuyetdctkSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%=userId%>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="IdXoa" id="IdXoa"  value=''>
<input type="hidden" name="msg" value='<%= obj.getMsg()==null?"":obj.getMsg() %>'>
<script language="javascript" type="text/javascript">
    thongbao();
</script>
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
    height="100%">
    <TR>
        <TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">

        <TABLE width="100%" bgcolor="#FFFFFF" cellspacing="1" cellpadding="0">
            <TR>
                <TD align="left" class="tbnavigation">
                       <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
                           <TR height="22">
                               <TD align="left"  class="tbnavigation">&nbsp;<%= " " + url %> </TD>
                               <TD align="right" class="tbnavigation"><%=ChuyenNgu.get("Ch??o m???ng b???n",nnId) %>  <%= userTen %> </TD>

                             </TR>
                      </TABLE>
                  </TD>
          </TR> 
        </TABLE>
        <TABLE width="100%" bgcolor="#FFFFFF" cellspacing="0" cellpadding="0">      
            <TR>
                <TD>
                    <TABLE border="0" width="100%" cellspacing="0" cellpadding="0">
                        <TR>

                            <TD width="100%" align="left">
                            <FIELDSET>
                            <LEGEND class="legendtitle">&nbsp;<%=ChuyenNgu.get("Ti??u ch?? hi???n th???",nnId) %> &nbsp;</LEGEND>

                            <TABLE width="100%" cellpadding="6" cellspacing="0">
								<TR>
								  <TD width="20%" class="plainlabel"><%=ChuyenNgu.get("????n v??? kinh doanh",nnId) %></TD>
								  <TD width="30%" class="plainlabel"><SELECT name="dvkdId" onChange="submitform();">
								  <option value="" > </option>
								<%  try{
								  		while(dvkd.next()){								  			
								  			if (obj.getDvkdId().equals(dvkd.getString("dvkdId"))){ %>								  			
								  				<option value="<%= dvkd.getString("dvkdId")%>" selected><%= dvkd.getString("dvkd")%></option>
								  		  <%}else{ %>		
								  		  		<option value="<%= dvkd.getString("dvkdId")%>" ><%= dvkd.getString("dvkd")%></option>
								  	<%		}
								  		}
								  	}catch (java.sql.SQLException e){} %>
                                  </SELECT></TD>
                                  
                                  <TD class = "plainlabel" width = "100px"> <%=ChuyenNgu.get("Nh?? ph??n ph???i",nnId) %> </TD>
                                  <TD class = "plainlabel">
                                  	<select name = "nppId" id = "nppId" class = "select2" style = "width: 200px" onchange = "submitform();">
                                  		<option value = ""></option>
                                  		<%
                                  		if (nhaPhanPhoiRs != null)
                                  		{
                                  			try
                                  			{
                                  				while( nhaPhanPhoiRs.next())
                                  				{
                                  					//System.out.println("=Vuan===pk_seq=nhaPP=resulfSet=" + nhaPhanPhoiRs.getString("pk_seq"));
                                  					//System.out.println("=Vuan===pk_seq=obj==" + obj.getNppId());
                                  					if( nhaPhanPhoiRs.getString("pk_seq").equals(obj.getNppId() )){ %>                                  					
                                  						<option value = "<%= nhaPhanPhoiRs.getString("pk_seq") %>" selected = "selected" >
                                  						<%= nhaPhanPhoiRs.getString("ten") %></option>                                  						                                  					
                                  				  <%} else {%> 
                                  						<option value = "<%= nhaPhanPhoiRs.getString("pk_seq") %>" >
                                  					<%= nhaPhanPhoiRs.getString("ten") %></option>
                                  						<% } %>
                                  			<%  } %>
                                  		<% } catch( Exception ex) { ex.printStackTrace(); } %>
                                  	<%  } %>
                                  	</select>
                                  </TD>
                                  
<!--                                   <TD class="plainlabel" width="100px">Nh?? ph??n ph???i</TD> -->
<!--                         <TD class="plainlabel"> -->
<!--                             <select name = "nppId" id="nppId" class="select2" style="width: 200px" onchange="submitform();" > -->
<!-- 	                    		<option value=""> </option> -->
<%-- 	                        	<% --%>
<!-- // 	                        		if(nppRs != null) -->
<!-- // 	                        		{ -->
<!-- // 	                        			try -->
<!-- // 	                        			{ -->
<!-- // 	                        			while(nppRs.next()) -->
<!-- // 	                        			{   -->
<%-- 	                        			if( nppRs.getString("pk_seq").equals(obj.getNppTen())){ %> --%>
<%-- 	                        				<option value="<%= nppRs.getString("pk_seq") %>" selected="selected" ><%= nppRs.getString("ten") %></option> --%>
<%-- 	                        			<%}else { %> --%>
<%-- 	                        				<option value="<%= nppRs.getString("pk_seq") %>" ><%= nppRs.getString("ten") %></option> --%>
<%-- 	                        		 <% } } nppRs.close();} catch(Exception ex){} --%>
<!-- // 	                        		} -->
<%-- 	                        	%> --%>
<!-- 	                    	</select> -->
<!--                         </TD> -->

                       
							  	</TR>
								<TR>
								  <TD class="plainlabel"><%=ChuyenNgu.get("K??nh b??n h??ng",nnId) %></TD>
								  <TD class="plainlabel"><SELECT name="kbhId" onChange="submitform();">
								  <option value="" > </option>
								<%  try{
								  		while(kbh.next()){								  			
								  			if (obj.getKbhId().equals(kbh.getString("kbhId"))){ %>								  			
								  				<option value="<%= kbh.getString("kbhId")%>" selected><%= kbh.getString("kbh")%></option>
								  		  <%}else{ %>		
								  		  		<option value="<%= kbh.getString("kbhId")%>" ><%= kbh.getString("kbh")%></option>
								  	<%		}
								  		}
								  	}catch (java.sql.SQLException e){} %>
                                  </SELECT></TD>
                                  
                                   <TD class="plainlabel"> <%=ChuyenNgu.get("Kho",nnId) %> </TD>
								  <TD class="plainlabel"><SELECT name="khoId" onChange="submitform();">
								  <option value="" > </option>
								<%  try{
								  		while(kho.next()){								  			
								  			if (obj.getKhoId().equals(kho.getString("khoId"))){ %>								  			
								  				<option value="<%= kho.getString("khoId")%>" selected><%= kho.getString("ten")+"-" +kho.getString("diengiai")%></option>
								  		  <%}else{ %>		
								  		  		<option value="<%= kho.getString("khoId")%>" ><%= kho.getString("ten")+"-" +kho.getString("diengiai")%></option>
								  	<%		}
								  		}
								  	}catch (java.sql.SQLException e){} %>
                                  </SELECT></TD>
                                  
							  	</TR>                                
                                
                                <TR>
                                <TD class="plainlabel"><%=ChuyenNgu.get("T??? ng??y",nnId) %> </TD>
                                <TD class="plainlabel">
                                  <table border=0 cellpadding = 0>
                                <TD class="plainlabel">
                                  <input class="days" type="text" name="tungay" size="15" value="<%= obj.getTungay()%>" onchange="submitform();">
                                 </TD>          
          						</table></TD>
          						
          						<TD class="plainlabel"><%=ChuyenNgu.get("?????n ng??y",nnId) %> </TD>
                                  <TD class="plainlabel"> <table border=0 cellpadding = 0>
                                    <TD class="plainlabel">
                                    	<input class="days" type="text" name="denngay" size="15" value="<%= obj.getDenngay()%>" onchange="submitform();">
                                    </TD>
									</table></TD> 
          						
                                </TR>                                
          						                            
                                <TR>
                                	<TD class="plainlabel"><%=ChuyenNgu.get("Tr???ng th??i",nnId) %></TD>
                                	<TD class="plainlabel" >
                                	<select name = "trangthai" id = "trangthai" onchange="submitform();">
                                	<%if(obj.getTrangthai().equals("0")) {%>
                                		<option value= ""></option>
                                		<option value= "0" selected="selected"><%=ChuyenNgu.get("Ch??a duy???t",nnId) %></option>
                                		<option value= "1"><%=ChuyenNgu.get("???? duy???t",nnId) %></option>
                                		<option value= "2"><%=ChuyenNgu.get("???? h???y",nnId) %></option>
                                	<%}else if(obj.getTrangthai().equals("1")){ %>
                                		<option value= ""></option>
                                		<option value= "0"><%=ChuyenNgu.get("Ch??a duy???t",nnId) %></option>
                                		<option value= "1" selected="selected"><%=ChuyenNgu.get("???? duy???t",nnId) %></option>
                                		<option value= "2"><%=ChuyenNgu.get("???? h???y",nnId) %></option>
                                	<%}else if(obj.getTrangthai().equals("2")){ %>
                                		<option value= ""></option>
                                		<option value= "0"><%=ChuyenNgu.get("Ch??a duy???t",nnId) %></option>
                                		<option value= "1"><%=ChuyenNgu.get("???? duy???t",nnId) %></option>
                                		<option value= "2" selected="selected"><%=ChuyenNgu.get("???? h???y",nnId) %></option>
                                	<%}else { %>
                                		<option value= "" selected="selected"></option>
                                		<option value= "0"><%=ChuyenNgu.get("Ch??a duy???t",nnId) %></option>
                                		<option value= "1"><%=ChuyenNgu.get("???? duy???t",nnId) %></option>
                                		<option value= "2"><%=ChuyenNgu.get("???? h???y",nnId) %></option>
                                	<%} %>
                                	</select>
                                	</TD>
                                	<TD class="plainlabel">S??? ch???ng t???</TD>                              	
                                	 <TD class="plainlabel"> 
                                  <input type="text" name="sochungtu" id="sochungtu" size="15" value="<%= obj.getSochungtu()%>" onchange="submitform();">
                                 </TD> 
                                </TR>
                                
                                <TR>
                                    <TD class="plainlabel" colspan = "3">
                                    	<a class="button2" href="javascript:clearform()">
										<img style="top: -4px;" src="../images/button.png" alt=""><%=ChuyenNgu.get("Nh???p l???i",nnId) %></a>&nbsp;&nbsp;&nbsp;&nbsp;	
                                      </TD>
                                    <TD class="plainlabel">&nbsp;</TD>
                                </TR>
                            </TABLE>

                            </FIELDSET>
                            </TD>
                        </TR>
                    </TABLE>
                    </TD>
                </TR>
            </TABLE>

        <TABLE width="100%" bgcolor="#FFFFFF" cellspacing="0" cellpadding="0">                  
            <TR>
                <TD width="100%">
                    <FIELDSET>
                    <LEGEND class="legendtitle">&nbsp;<%=ChuyenNgu.get("??i???u ch???nh t???n kho",nnId) %></LEGEND>
    
                    <TABLE class="contenu_fieldset" width="100%" cellpadding="0" cellspacing="0">
                        <TR>
                            <TD>
                                <TABLE width="100%" border="0" cellspacing="1" cellpadding="4">

                                    <TR class="tbheader">
                                        <TH ><%=ChuyenNgu.get("Ng??y ??i???u ch???nh",nnId) %> </TH>
                                        <TH ><%=ChuyenNgu.get("S??? ch???ng t???",nnId) %> </TH>
                                        <TH ><%=ChuyenNgu.get("T???ng s??? ti???n",nnId) %> </TH>
                                        <TH ><%=ChuyenNgu.get("????n v??? kinh doanh",nnId) %> </TH>
                                        <TH ><%=ChuyenNgu.get("K??nh b??n h??ng",nnId) %> </TH>
                                        <TH ><%=ChuyenNgu.get("Kho",nnId) %> </TH>
                                        <TH ><%=ChuyenNgu.get("Tr???ng th??i",nnId) %> </TH>
                                        <TH ><%=ChuyenNgu.get("Ng?????i t???o",nnId) %> </TH>
                                        <TH ><%=ChuyenNgu.get("Ng?????i s???a",nnId) %> </TH>
                                        <TH ><%=ChuyenNgu.get("Ng?????i duy???t",nnId) %> </TH>

                                        <TH " align="center">&nbsp;<%=ChuyenNgu.get("T??c v???",nnId) %></TH>
                                    </TR>
                                     <%                                      
                                         
                                    int m = 0;
                                    String lightrow = "tblightrow";
                                    String darkrow = "tbdarkrow";
                                    while (dctklist.next()){
                                        if (m % 2 != 0) {%>                     
                                            <TR class= <%=lightrow%> >
                                        <%} else {%>
                                            <TR class= <%= darkrow%> >
                                        <%}%>
                                                <TD align="left"><div align="left"><%= dctklist.getString("ngaydc") %></div></TD>                                   
                                                <TD><div align="center"><%= dctklist.getString("chungtu") %></div></TD>
                                                 <%
												  String gia="";
												  if(dctklist.getString("tongtien").trim().length()!=0)
												  {
													 gia= formatter.format(Double.parseDouble(dctklist.getString("tongtien")));
												  }
												  else
												  {
													  gia = dctklist.getString("tongtien");
												  }
											  %>
                                                <TD><div align="center"><%= gia %></div></TD>
                                                <TD align="center"><%= dctklist.getString("dvkd") %></TD>
                                                <TD align="center"><%= dctklist.getString("kbh") %></TD>
                                                <TD align="center"><%= dctklist.getString("ten") %></TD>
                                             <% if (dctklist.getString("trangthai").equals("0")){ %>
                                                <TD align="center"><%=ChuyenNgu.get("Ch??? duy???t",nnId) %></TD>
                                             <%}else if(dctklist.getString("trangthai").equals("1")){ %>
                                             	<TD align="center"><%=ChuyenNgu.get("???? duy???t",nnId) %></TD>
                                             <%}else{ %>
                                             	<TD align="center"><%=ChuyenNgu.get("???? h???y",nnId) %></TD>
                                             <%} %>
                                                <TD align="center"><%= dctklist.getString("nguoitao") %></TD>
                                                <TD align="center"><%= dctklist.getString("nguoisua") %></TD>
                                                <% if (dctklist.getString("nguoiduyet").equals("0")) {%>
                                                <TD align="center">&nbsp;</TD>
                                                <%}else{ %>
                                                <TD align="center"><%= dctklist.getString("nguoiduyet") %></TD>
                                                <%} %>
                                                
                                                <TD align="center">
                                                <TABLE border = 0 cellpadding="0" cellspacing="0">
                                             <% if (dctklist.getString("trangthai").equals("0")){ %>                                                
                                                    <TR>
                                                    <TD>
                                                    <%if(quyen[4]!=0){ %>
                                                        <A href = "../../RouterSvl?task=<%= util.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "DctkDisplaySvl?userId="+userId+"&display="+ dctklist.getString("chungtu")+"") %>"><img src="../images/Chot.png" alt="Hi???n th???" title="Ch???t" width="20" height="20" longdesc="Hi???n th???" border = 0 ></A>
                                                   <%} %>
                                                    </TD>                                                  
                                                    <TD>
                                                    <%if(quyen[1]!=0){ %>
                                                   	 <A href="javascript:Xoa('<%=dctklist.getString("chungtu") %>');">
                                                    <img src="../images/Delete20.png" alt="Xoa" title="X??a" width="20" height="20" longdesc="Xoa" border=0 
                                                        		onclick="if(!confirm('B???n c?? mu???n x??a phi???u ??i???u ch???nh t???n kho n??y n??y?')) return false;"></A>
                                                  
                                                   <%} %>
                                                    </TD>
                                                    </TR>
                                            <%}else{ %>
                                                    <TR><TD>
                                                    <%if(quyen[3]!=0){ %>
                                                        <A href = "../../RouterSvl?task=<%= util.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "DctkDisplaySvl?userId="+userId+"&display="+ dctklist.getString("chungtu")+"") %>"><img src="../images/Display20.png" alt="Hi???n th???" title="Hi???n th???" width="20" height="20" longdesc="Hi???n th???" border = 0 "></A>
                                                    <%} %>
                                                    </TD>
                                                    <TD width=20>
                                                        &nbsp;
                                                    </TD>
                                                    
                                                    </TR>
                                            <%} %>                                             
                                               </TABLE>
                                                </TD>
                                            </TR>
                                        <% m++; } %>
                                   <tr class="tbfooter">
                                   <td colspan="12"> 
                                   &nbsp;&nbsp;&nbsp;
                                   </td>
                                   </tr>
                                </TABLE>
                            </TD>
                        </TR>
                    </TABLE>

                    </FIELDSET>
                </TD>
            </TR>

        </TABLE>
        <!--end body Dossier--></TD>
    </TR>
</TABLE>
</form>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</HTML>

<%
	if(dctklist != null) { dctklist.close(); dctklist = null; }
	if(dvkd != null) { dvkd.close(); dvkd = null; }
	if(kbh != null) { kbh.close(); kbh = null; }
	if(kho != null) { kho.close(); kho = null; }
	
	if(obj != null){
		obj = null;
	}
	session.setAttribute("obj",null); 
	
	
	}%>