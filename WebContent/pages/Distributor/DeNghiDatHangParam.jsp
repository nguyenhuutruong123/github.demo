<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.distributor.beans.denghidathang.IDenghidathang" %>
<%@ page import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>

<% IDenghidathang dndhBean = (IDenghidathang)session.getAttribute("dndhBean"); %>
<% ResultSet ncc = (ResultSet) dndhBean.getNcc(); %>
<% String nccId = (String) dndhBean.getNccId(); %>

<% ResultSet dvkd = (ResultSet)dndhBean.getDvkdIds(); %>
<% String dvkdId = (String)dndhBean.getDvkdId(); %>

<% ResultSet kbh = (ResultSet)dndhBean.getKbhIds(); %>
<% String kbhId = (String)dndhBean.getKbhId(); %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<HTML>
<HEAD>
<TITLE>Acecook - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">
<SCRIPT src="../js/md5.js" type="text/javascript" language="javascript">
</SCRIPT>
<SCRIPT src="../js/scripts.js" type="text/javascript"
    language="javascript">
</SCRIPT>
<SCRIPT src="../js/commun.js" type="text/javascript"
    language="javascript">
</SCRIPT>

<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">

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

<script type="text/javascript" language="JavaScript" src="../scripts/jquery.js"></script>
<script type="text/javascript" language="JavaScript" src="../scripts/simplecalendar.js"></script>
<script type="text/javascript" language="JavaScript" src="../scripts/Numberformat.js"></script>


<SCRIPT type="text/javascript">
function submitform()
{   
   
   document.forms["dndhForm"].submit();
}


</SCRIPT>


<!-- BEGIN RENDER AUTO -->

<% if (!geso.dms.center.util.GlobalValue.dev_mode) { %>
	<script type="text/javascript" src="../scripts/disableF12.js"></script>
<% } %>

<!-- END RENDER AUTO -->
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0" >
<!-- BEGIN RENDER AUTO -->
<%if(!geso.dms.center.util.GlobalValue.dev_mode){ %>
	<noscript> 
	 	<meta http-equiv="refresh" content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")  %>">
		<!-- //window.location = "https://www.google.com"; -->
	</noscript>
<%} %>
<!-- END RENDER AUTO -->

<form name="dndhForm" method="post" action="../../DenghidathangDisplaySvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<INPUT name="userId" type="hidden" value='<%=userId %>' size="30">
<INPUT name="nppId" type="hidden" value='<%=dndhBean.getNppId() %>' size="30">
<input type="hidden" name="action" value='next'>

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
    height="100%"  bgcolor="#FFFFFF">
    <TR>
        <TD align='left' valign='top'> <!--begin body Dossier-->

            <TABLE width="100%" cellspacing="0" cellpadding="0">
                <TR>
                    <TD align="left" >
	                	<TABLE width="100%" cellspacing="1" cellpadding="0">
    	                	<TR>
        	                	<TD align="left">
            	                	<table width="100%" border="0" cellpadding="0" cellspacing="0">
                	                	<tr height="22">
                    	                	<TD align="left" colspan="2" class="tbnavigation">&nbsp;Qu???n l?? t???n kho &gt; Mua h??ng t??? nh?? cung c???p &gt; ????? ngh??? ?????t h??ng &gt; T???o m???i
                                   
                        	                <TD colspan="2" align="right" class="tbnavigation">Ch??o m???ng b???n  <%= dndhBean.getNppTen() %> &nbsp;</TD>
                            	        </tr>
                                	  </table>
                              	</TD>
                         	</TR> 
                     	</TABLE>
                     	
            <TABLE width="100%" border="0" cellpadding="3" cellspacing="0">
                <TR ><TD >
                    <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
                        <TR class = "tbdarkrow">
                            <TD width="30" align="left"><A href="javascript:history.back()"  ><img src="../images/Back30.png" alt="Quay v???"  title="Quay v???" border="1" longdesc="Quay v???" style="border-style:outset"></A></TD>
                            <TD width="2" align="left" >&nbsp;</TD>
                        </TR>
                    </TABLE>
                </TD></TR>

            </TABLE>

            <TABLE width = 100% cellpadding="0" cellspacing="0">
			  	<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Th??ng b??o </LEGEND>
				
	    				<textarea name="err" cols="84" rows="1" disabled ><%=dndhBean.getMessage()%></textarea>
						<% dndhBean.setMessage(""); %>
						</FIELDSET>
				   </TD>
				</tr>			
		 	
			<TR>
                
                <TR>
                    <TD >
                        <TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
                            <TR>
                                <TD  align="left">
                                    <FIELDSET>

                                    <LEGEND class="legendtitle">&nbsp;Th??ng tin ????? ngh??? ?????t h??ng&nbsp;</LEGEND>
                                                <TABLE cellpadding = 4 cellspacing = 0 width = "100%" border = 0>
                                                    <TR>
                                                        <TD width="20%" class="plainlabel">Nh?? ph??n ph???i </TD>
                                                        <TD width="70%" class="plainlabel"><%= dndhBean.getNppTen() %></TD>                                                   
                                                    </TR>

                            
                                                    <TR >
                                                        <TD class="plainlabel">Ng??y ????? ngh??? </TD>
                                                        <TD class="plainlabel">
        
                                                            <table border=0 cellpadding = 0 cellspacing = 0>
                                                                <tr>
                                                                    <td class="plainlabel"><input class="days" type="text" name="ngaydn" size="11" value="<%=dndhBean.getNgaydn() %>" ></td>
                                                                                                                                       
                                                                </tr>
                                                            </table>                                                
                                                        </TD>
                                                    </TR>
                                                    <TR class="tblightrow">
                                                        <TD  class="plainlabel">Nh?? cung c???p </TD>
                                                        <TD  class="plainlabel"> 
                                                          <select name="nccId" onChange="submitform();">
                                                          	
                                                           <% try{ %>
                                                       			    	
                                                         	<%   if(ncc != null){
                                                         			while(ncc.next()){      
                                                            	      if (ncc.getString("nccId").equals(nccId)){ %>   
                                                                	       <option value='<%=ncc.getString("nccId")%>' selected><%=ncc.getString("nccTen")%></option>
                                                                	       
                                                                   <% }else{ %>
                                                                			<option value='<%=ncc.getString("nccId")%>'><%=ncc.getString("nccTen")%></option>   		   
                                                                 	<%} 
                                                                	} 
                                                                  }%>
                                                            <% }catch(java.sql.SQLException e){   %>
                                                                    <%  }  %>
                                                          </select>                                             
                                                        </TD>

                                                    </TR>
													<TR>
														<TD class="plainlabel">????n v??? kinh doanh </TD>
								    					<TD class="plainlabel">
								    						<SELECT  name="dvkdId" style="width:80" onChange="submitform();">
								  								<OPTION value""></OPTION>
														  	 <% if(!(dvkd ==null)){
														  	 		try{ while(dvkd.next()){ 
															    		if(dvkd.getString("dvkdId").equals(dvkdId)){ %>
								      										<option value='<%=dvkd.getString("dvkdId") %>' selected><%=dvkd.getString("dvkd") %></option>
								      							  	  <%}else{ %>
								     										<option value='<%=dvkd.getString("dvkdId") %>'><%=dvkd.getString("dvkd") %></option>
								     							  <%}}}catch(java.sql.SQLException e){} 
								     							 }%>	
                                  							</select>
                                  						</TD>
													</TR>
													<TR>
														<TD class="plainlabel">K??nh b??n h??ng </TD>
								    					<TD class="plainlabel">
								    						<SELECT  name="kbhId" style="width:80">
								  								<OPTION value""></OPTION>
														  	 <% try{ while(kbh.next()){ 
															    	if(kbh.getString("kbhId").equals(kbhId)){ %>
								      									<option value='<%=kbh.getString("kbhId") %>' selected><%=kbh.getString("kbh") %></option>
								      							  <%}else{ %>
								     									<option value='<%=kbh.getString("kbhId") %>'><%=kbh.getString("kbh") %></option>
								     							  <%}}}catch(java.sql.SQLException e){} %>	
                                  							</select>
                                  						</TD>
													</TR>
                                                    <TR >
                                                        <TD class="plainlabel">T???n su???t ?????t h??ng</TD>
                                                        <TD class="plainlabel">
        
                                                            <table border=0 cellpadding = 0 cellspacing = 0>
                                                                <tr>
                                                                    <td class="plainlabel"><input type="text" name="tanso" size="5" value="" ></td>
                                                                    <td class="plainlabel">&nbsp;l???n / tu???n</td>
                                                                </tr>
                                                            </table>                                                
                                                        </TD>
                                                    </TR>

													<TR>
														<TD class="plainlabel" colspan=2><INPUT type="submit" name="next" value="Ti???p t???c"></TD>
													</TR>
                                                   
                                                </TABLE>
                                               </FIELDSET>
                                            </TD>
                                    </TR>

                                   
                                </TABLE>
                                
                        </TD>
                    </TR>   
            </TABLE>
    </td>

  </tr>
</table>

</form>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</html>

<% if(!(ncc == null))  ncc.close(); %>
<% if(!(dvkd == null)) dvkd.close(); %>
<% if(!(kbh == null)) kbh.close(); %>
<%if(dndhBean != null){
	dndhBean.DBclose();
	dndhBean = null;
} %>
<%}%>