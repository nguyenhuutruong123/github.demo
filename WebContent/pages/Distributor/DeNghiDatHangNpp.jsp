<%@page import="geso.dms.distributor.beans.denghidathangnpp.IDenghidathangnppList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.distributor.beans.denghidathang.IDenghidathangList" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>

<% IDenghidathangnppList obj = (IDenghidathangnppList)session.getAttribute("obj"); %>
<% ResultSet dndhlist = (ResultSet)obj.getDndhList(); %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Acecook - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<script type="text/javascript" language="JavaScript" src="../scripts/Numberformat.js"></script>

<link rel="stylesheet" type="text/css" href="../css/autocomplete.css" />

<script src="../scripts/jquery.autocomplete.js"></script>

<script type="text/javascript" src="../scripts/jquery.js"></script>
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<script type="text/javascript" language="JavaScript" src="../scripts/simplecalendar.js"></script>
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

<SCRIPT language="javascript" type="text/javascript">

function submitform()
{ 
   document.forms["dndhForm"].action.value="tim kiem";
   document.forms["dndhForm"].submit();
}

function clearform()
{
   // document.forms["dndhForm"].sku.value = "";
    document.forms["dndhForm"].tungay.value = "";
    document.forms["dndhForm"].denngay.value = "";
    document.forms["dndhForm"].trangthai.value = "";
   // document.forms["dndhForm"].action.value="clear";
   //document.forms["dndhForm"].submit();
    submitform();
}
function newform()
{
	 document.forms["dndhForm"].action.value="Tao moi";
	 document.forms["dndhForm"].submit();
	}
 

</SCRIPT>
<SCRIPT language="javascript" type="text/javascript">
</SCRIPT>

<!-- BEGIN RENDER AUTO -->

<% if (!geso.dms.center.util.GlobalValue.getDevmode()) { %>
	<script type="text/javascript" src="../scripts/disableF12.js"></script>
<% } %>

<!-- END RENDER AUTO -->
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0" >
<!-- BEGIN RENDER AUTO -->
<%if(!geso.dms.center.util.GlobalValue.getDevmode()){ %>
	<noscript> 
	 	<meta http-equiv="refresh" content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")  %>">
		<!-- //window.location = "https://www.google.com"; -->
	</noscript>
<%} %>
<!-- END RENDER AUTO -->

<form id="dndhForm" name="dndhForm" method="post" action="../../DenghidathangnppSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value="<%=userId%>">
<input type="hidden" name="action" value="1">

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
    height="100%"  bgcolor="#FFFFFF">
    <TR>
        <TD colspan="4" align="left" valign="top"> 
        
        <TABLE width="100%" cellspacing="0" cellpadding="0">
        	<TR>
            	<TD>
                	<TABLE width="100%" cellspacing="1" cellpadding="0">
                    	<TR>
                        	<TD align="left" >
                            	<table width="100%" border="0" cellpadding="0" cellspacing="0">
                                	<tr height="22">
                                    	<TD align="left" colspan="2" class="tbnavigation">&nbsp;Qu???n l?? kho &gt; &nbsp;Qu???n l?? mua h??ng &gt; &nbsp; ????? ngh???
                                   
                                        <TD colspan="2" align="right" class="tbnavigation">Ch??o m???ng b???n  <%= obj.getNppTen() %> &nbsp;</TD>
                                    </tr>
                                  </table>
                              </TD>
                         </TR> 
                     </TABLE>
                    
                     <TABLE border="0" width="100%" cellspacing = 0 cellpadding = 0>

                        <TR>
                            <TD width="100%" align="left">
                                <FIELDSET>
                                <LEGEND class="legendtitle">&nbsp;Ti??u ch?? hi???n th??? &nbsp;</LEGEND>

                                <TABLE  width="100%" cellpadding="4" cellspacing="0">
                                   
<TR>
	<TD class="plainlabel" >T??? ng??y</TD>
	<td class="plainlabel">
		<input type="text"  class="days" size="11"
				id="tungay" name="tungay" value="<%= obj.getTungay() %>" maxlength="10" readonly />
	</td>
</TR>
<TR>
  <TD class="plainlabel" >?????n ng??y</TD>
  <td class="plainlabel">
		<input type="text"  class="days" size="11"
				id="denngay" name="denngay" value="<%= obj.getDenngay() %>" maxlength="10" readonly />
	</td>
</TR>
								<TR>
									<TD class="plainlabel">Tr???ng th??i</TD>
									<TD class="plainlabel"><select name="trangthai" >
								  	<% if (obj.getTrangthai().equals("0")){ %>
								    	<option value=""></option>
								    	<option value="0" selected>Ch??a ?????t h??ng</option>
								    	<option value="1">Da dat hang</option>
								    	
									<%}else{ 							
								  		if (obj.getTrangthai().equals("1")){ %>
								    	<option value=""></option>
								    	<option value="1" selected>???? ?????t h??ng</option>
								    	<option value="0" >Ch??a ?????t h??ng</option>
								    	
								    	
									<%}else{%>
								    	<option value=""></option>
								    	<option value="1" >Ch??a ?????t h??ng</option>
								    	<option value="2" >???? ?????t h??ng</option>
									<%}} %>									
									
								    	</select></TD>
								</TR>

                               	<TR>
                                      <TD class="plainlabel" align="left" colspan="2">
                                               <a class="button1" href="javascript:submitform();">
	<img style="top: -4px;" src="../images/button.png" alt="">Hi???n th???</a>&nbsp;&nbsp;&nbsp;&nbsp;
	                                  <a class="button2" href="javascript:clearform();">
	<img style="top: -4px;" src="../images/button.png" alt="">Nh???p l???i</a>&nbsp;&nbsp;&nbsp;&nbsp;
                               
                                        </TD>
                                      
                                </TR>
                                			
									
								</TABLE>
                              </FIELDSET>
                          	</TD>			
                          </TR>
                      </TABLE>
                      <TABLE width="100%" cellspacing = 0 cellpadding=0>
                        <TR>
                            <TD >
                                <FIELDSET>
                                <LEGEND class="legendtitle">&nbsp;????? ngh??? ?????t h??ng&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                	<a class="button3" href="javascript:newform()">
    	<img style="top: -4px;" src="../images/New.png" alt="">T???o m???i </a>                            
                                
                               <!--  <INPUT name="action" type="submit" value="Tao moi" newform() >  -->
                                </LEGEND>
    
                      	        <TABLE width="100%">
                                <TR>
                                    <TD>
                                        <TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
                                        <TR class="tbheader">
                                            <TH >Ng??y ?????t h??ng </TH>
                                            <TH >S??? ch???ng t??? </TH>
                                            <TH>S??? ????n ?????t h??ng</TH>
                                            <TH >????n v??? kinh doanh </TH>
                                            <TH >T???ng ti???n (c?? VAT-VND) </TH>
											<TH >Tr???ng th??i </TH>
                                            <TH >???? ?????t h??ng </TH>
                                            <TH >C??n l???i </TH>
                                            <TH align="center">&nbsp;T??c v??? </TH>
                                        </TR>
         
                               <% 
                            NumberFormat formatter = new DecimalFormat("#,###,###");
                            int m = 0;
                            String lightrow = "tblightrow";
                            String darkrow = "tbdarkrow";
							if(dndhlist != null){
								try{								
                                    while (dndhlist.next()){
                                        	
                                       	if (m % 2 != 0) {%>                     
                                        	<TR class= <%=lightrow%> >
                                        <%} else {%>
                                           	<TR class= <%= darkrow%> >
                                        <%}%>
                                                <TD align="left"><div align="left"><%= dndhlist.getString("ngaydat") %></div></TD>                                   
                                                <TD><div align="center"><%= dndhlist.getString("chungtu") %></div></TD>
                                                <%if(dndhlist.getString("dondathang")==null){ %>
                                                 <TD><div align="center"></div></TD>
                                                 <%}else{ %>
                                                   <TD><div align="center"><%= dndhlist.getString("dondathang") %></div></TD>
                                                 <%} %>
                                                <TD><div align="center"><%= dndhlist.getString("donvikinhdoanh") %></div></TD>
                                                <TD><div align="center"><%= dndhlist.getString("sotienavat") %></div></TD>
                                                <% if (dndhlist.getString("trangthai").equals("0")){ %>
                                                   		<TD><div align="center">Ch??a ?????t h??ng</div></TD>
                                               <% }else if (dndhlist.getString("trangthai").equals("3")){ %>
                                              			 <TD><div align="center">???? ch???p nh???n</div></TD>
                                                <%}else if (dndhlist.getString("trangthai").equals("1")) {%>
                                                   		<TD><div align="center">???? ?????t h??ng</div></TD>
                                                 <%}else {%>
                                                 <TD><div align="center"></div></TD>
                                                 <%} %>
                                        	        <TD align="center"><%= dndhlist.getString("dadathang") %></TD>
                                        	         <%if(Integer.parseInt(dndhlist.getString("conlai")) > 0) { %>
                                        	           <TD align="center"><%= dndhlist.getString("conlai") %></TD>
                                        	         <%} else { %>
                                        	           <TD align="center">0</TD>
                                        	    	<%} %>
                                        	    	<TD align="center">
                            	                    <TABLE border = 0 cellpadding="0" cellspacing="0">
                                	                    <TR>
                                	                    <TD>
                               	                    		<A href = "../../DenghidathangnppUpdateSvl?userId=<%=userId%>&update=<%=dndhlist.getString("chungtu") %>"><img src="../images/Display20.png" alt="Hien thi" title="Hi???n th???" width="20" height="20" longdesc="Hien thi" border = 0 "></A>
                                        	            </TD>
                                        	            <%// if((Float.parseFloat(dndhlist.getString("sotienavat"))-Float.parseFloat(dndhlist.getString("dadathang"))) > 0) {%>
                                        	            <TD>&nbsp;&nbsp;</TD>
														<TD>
				         	                    			<A href = "../../DathangnppSvl?userId=<%=userId%>&dathang=<%=dndhlist.getString("chungtu") %>"><img src="../images/convert.gif" alt="Dat hang" title="?????t h??ng" width="20" height="20" longdesc="Dat hang" border = 0 "></A>
                                        	            </TD>
                                        	            <TD>&nbsp;&nbsp;</TD>
                                        	            <% if (dndhlist.getString("trangthai").equals("0")){ %>
														<TD>
				         	                    			<A href = "../../DenghidathangnppSvl?userId=<%=userId%>&delete=<%=dndhlist.getString("chungtu") %>"><img src="../images/Delete20.png" alt="X??a" title="X??a" onclick="if(!confirm('B???n ch???c ch???n mu???n x??a ????? ngh??? ?????t h??ng n??y kh??ng?')){ return false;}" width="20" height="20" longdesc="X??a" border = 0 "></A>
                                        	            </TD>      
                                        	            <%} %>                                  	            
                                        	                                                    	            
                								   <%//} %>                        	            
                                        	                                 	            
                                        	            </TR>
														                                                   
                                            		 </TABLE>
                                                </TD>
                                            </TR>
                                        <% m++; } 
                                        }catch(java.sql.SQLException e){}
                               		}%>
                                	<TR>
                                    	<TD align="center" colspan="12" class="tbfooter">&nbsp;</TD>
                                	</TR>
                            	</TABLE>                              
                          </TD>
                        </TR>
                      </TABLE>
                       </FIELDSET>
                </TD>
              </TR>
            </TABLE>
          </TD>
         </TR>
        </TABLE>
       </TD>
      </TR>
 </TABLE>
</form>

</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</HTML>

<% if(!(dndhlist == null)) dndhlist.close();%>
<% if(obj != null){
	obj.DBclose();
	obj = null;
}
;%>
<%}%>