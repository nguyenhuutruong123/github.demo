<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.beans.donmuahang.IDonmuahang" %>
<%@ page  import = "geso.dms.center.beans.donmuahang.imp.Donmuahang" %>
<%@ page  import = "java.util.Hashtable"%>
<%@ page  import = "java.util.List"%>
<%@ page import = "java.sql.ResultSet" %>
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

<% IDonmuahang dmhBean = (IDonmuahang)session.getAttribute("dmhBean"); %>
<% ResultSet ncc = (ResultSet) dmhBean.getNcc(); %>
<% ResultSet dvkd = (ResultSet)dmhBean.getDvkdIds(); %>
<% ResultSet kbh = (ResultSet)dmhBean.getKbhIds(); %>
<% String[] masp = dmhBean.getMasp() ; %>
<% String[] tensp = dmhBean.getTensp(); %>
<% String[] sl = dmhBean.getSl(); %>
<% String[] tienbVAT = dmhBean.getTienbVAT(); %>
<% String[] dg = dmhBean.getDongia() ; %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="java.util.Hashtable;"%>
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
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
<SCRIPT  type="text/javascript" language="JavaScript" src="../scripts/jquery-1.3.2.min.js"></SCRIPT> 
<SCRIPT  type="text/javascript" language="JavaScript" src="../scripts/simplecalendar.js"></SCRIPT>
<SCRIPT  type="text/javascript" language="JavaScript" src="../scripts/Numberformat.js"></SCRIPT>
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">

<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
<SCRIPT type="text/javascript">
function setTTienbVAT(){
	 	
	var i = <%= dmhBean.getSize()%>;
	var masp = new Array(<%= dmhBean.getMaspstr() %>);	
	var tongtienbvat = 0;
	
	for(x=0; x<i; x++){
		var slId = "sl" + masp[x];
	
		var sl= document.getElementById(slId).value;
		sl.replace(/^\s+|\s+$/, '');
		if(sl.length==0) 
			sl="0";
		
		var dgId = "dg"  + masp[x];
		
		var dg= document.getElementById(dgId).value;
	
		var tien = parseFloat(sl)*parseFloat(dg);
		
		var tienId = "t"  + masp[x];
				
		if(!(isNaN(tien))){
			document.getElementById(tienId).setAttribute("value", formatCurrency(tien));
			tongtienbvat = parseFloat(tongtienbvat) + parseFloat(tien);
		}
	}
	document.getElementById("tongtienbvat").setAttribute("value", formatCurrency(tongtienbvat)); 
	var vat = Math.round(tongtienbvat*0.1);
	document.getElementById("vat").setAttribute("value", formatCurrency(vat));
	var tongtienavat = Math.round(tongtienbvat*1.1);
	document.getElementById("tongtienavat").setAttribute("value", formatCurrency(tongtienavat));
	document.getElementById('lblDocSo').innerHTML = DocTienBangChu(tongtienavat) + " Đồng Việt Nam";
		
}
      
function init()
{
	var tongtienavat= document.getElementById("tongtienavat").value;
	document.getElementById('lblDocSo').innerHTML = DocTienBangChu(tongtienavat) + " Đồng Việt Nam";
}

function submitenter(myfield,e)
{
var keycode;
if (window.event) keycode = window.event.keyCode;
else if (e) keycode = e.which;
else return true;

if (keycode == 13)
   {
   return false;
   }
else
   return true;
}

function chotform()
{  document.forms['ddhForm'].action.value='chot';
   document.forms["ddhForm"].submit();
}

 function saveform()
{
	document.forms['ddhForm'].action.value='save';
    document.forms["ddhForm"].submit();
}

function printform()
{   
	document.forms['ddhForm'].action.value='print';
	document.forms["ddhForm"].submit();
}

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

<form name="ddhForm" method="post" action="../../DonmuahangDisplaySvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<INPUT name="userId" type="hidden" value='<%=userId %>' size="30">
<input type="hidden" name="action" value='1'>
<input type="hidden" name="id" value='<%=dmhBean.getId() %>'>

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
    height="100%"  bgcolor="#FFFFFF">
    <TR>
        <TD colspan="4" align='left' valign='top'> <!--begin body Dossier-->

            <TABLE width="100%">
                <TR>
                    <TD align="left" class="tbnavigation">
                       <table width="100%" border="0" cellpadding="0" cellspacing="0">
                           <tr height="22">
                               <TD align="left" colspan="2" class="tbnavigation">Quan ly kho &gt; &nbsp;Xu ly don hang &gt; Hien thi </TD>
      
                               <TD colspan="2" align="right" class="tbnavigation">Chao mung Nha Phan Phoi <%=dmhBean.getNppTen() %> &nbsp;</TD>

                            </tr>
                        </table>
                     </TD>
                  </TR> 
            </TABLE>

            <TABLE width="100%" border="0" cellpadding="3" cellspacing="0">
                <TR ><TD >
                    <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
                        <TR class = "tbdarkrow">

                            <TD width="30" align="left"><A href="javascript:history.back()" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
                            <TD width="2" align="left" >&nbsp;</TD>
                            <TD width="30" align="left" ><A href="javascript: printform()" ><img src="../images/Printer30.png" alt="In" title="In chung tu" width="30" height="30" longdesc="In chung tu" border=1 style="border-style:outset"></A></TD>
                            <TD  align="left">&nbsp;</TD>
                        </TR>
                    </TABLE>
                </TD></TR>

            </TABLE>

            <TABLE width = 100% cellpadding="0" cellspacing="0">
			  	<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Bao loi nhap lieu</LEGEND>
				
	    				<textarea name="err" cols="96" rows="1" ><%=dmhBean.getMessage()%></textarea>
						<% dmhBean.setMessage(""); %>
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

                                    <LEGEND class="legendtitle">&nbsp;Don dat hang &nbsp;</LEGEND>
                                                <TABLE cellpadding = 4 cellspacing = 0 width = "100%" border = 0>
                                                    <TR>
                                                        <TD width="30%" class="plainlabel">Nha phan phoi </TD>
                                                        <TD width="70%" class="plainlabel"><%= dmhBean.getNppTen() %>                                                   
                                                    </TR>

                            
                                                    <TR >
                                                        <TD class="plainlabel">Ngay dat hang</TD>
                                                        <TD class="plainlabel">
        
                                                            <table border=0 cellpadding = 0 cellspacing = 0>
                                                                <tr>
                                                                    <td class="plainlabel"><input type="text" name="ngaydh" size="11" value="<%= dmhBean.getNgaydh() %>" readonly="readonly">
                                                                    <td>&nbsp;</td>
                                                                </tr>

                                                            </table>                                                
                                                        </TD>
                                                    </TR>
                                                    <TR class="tblightrow">
                                                        <TD  class="plainlabel">Nha Cung Cap</TD>
                                                        <TD  class="plainlabel"> 
                                                          <select name="nccId" readonly="readonly">
                                                          	
                                                           <% try{ %>
                                                       			    	
                                                         	<%   if(ncc != null){
                                                         			while(ncc.next()){      
                                                            	      if (ncc.getString("nccId").equals(dmhBean.getNccId())){ %>   
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
														<TD class="plainlabel">Don vi kinh doanh </TD>
								    					<TD class="plainlabel">
								    						<SELECT  name="dvkdId" style="width:80" readonly="readonly">
								  		
														  	 <% try{ while(dvkd.next()){ 
															    	if(dvkd.getString("dvkdId").equals(dmhBean.getDvkdId())){ %>
								      									<option value='<%=dvkd.getString("dvkdId") %>' selected><%=dvkd.getString("dvkd") %></option>
								      							  <%}else{ %>
								     									<option value='<%=dvkd.getString("dvkdId") %>'><%=dvkd.getString("dvkd") %></option>
								     							  <%}}}catch(java.sql.SQLException e){} %>	
                                  							</select>
                                  						</TD>
													</TR>
													<TR>
														<TD class="plainlabel">Kenh ban hang </TD>
								    					<TD class="plainlabel">
								    						<SELECT  name="kbhId" style="width:80" readonly="readonly">
								  		
														  	 <% try{ while(kbh.next()){ 
															    	if(kbh.getString("kbhId").equals(dmhBean.getKbhId())){ %>
								      									<option value='<%=kbh.getString("kbhId") %>' selected><%=kbh.getString("kbh") %></option>
								      							  <%}else{ %>
								     									<option value='<%=dvkd.getString("kbhId") %>'><%=kbh.getString("kbh") %></option>
								     							  <%}}}catch(java.sql.SQLException e){} %>	
                                  							</select>
                                  						</TD>
													</TR>
													
        									<%NumberFormat formatter = new DecimalFormat("#,###,###"); %>
                                                    <TR class="tblightrow">
                                                      <TD  class="plainlabel">Tong so tien (chua VAT)</TD>
                                                      <TD  class="plainlabel"><input type="text" name="tongtienbvat" id="tongtienbvat" readonly="readonly" value="<%= formatter.format(Long.parseLong(dmhBean.getTongtienbVAT())) %>" style="text-align: right"  >
                                              VND </TD>
                                                    </TR>

                                                    <TR class="tblightrow">

                                                      <TD  class="plainlabel">VAT (10%) </TD>
                                                      <TD  class="plainlabel"><input type="text" name="vat" id="vat"  value="<%= formatter.format(Long.parseLong(dmhBean.getVat())) %>" readonly="readonly" style="text-align: right">
                                              VND </TD>
                                                    </TR>
                                                    
                                                    <TR class="tblightrow">
                                                      <TD  class="plainlabel">Tong so tien (co VAT) </TD>
                                                      <TD  class="plainlabel"><input type="text" name="tongtienavat" id="tongtienavat" readonly="readonly" value="<%= formatter.format(Long.parseLong(dmhBean.getTongtienaVAT())) %>"  style="text-align: right" >
                                              VND </TD>

                                                   </TR>

                                                    <TR class="tblightrow">
                                                      <TD  class="plainlabel">Bang chu </TD>
                                                      <TD  class="plainlabel"> <span id="lblDocSo" style="font-weight:bold;font-style:italic;"></span> </TD>

                                                   </TR>
        
                                                </TABLE>
                                            
                                                <TABLE  width = 100% cellpadding="4"  border="0" cellspacing="1" >
                                                    <TR class="tbheader" >
                                                        <TH width="20%">Ma san pham  </TH>
                                                        <TH width="40%">Ten san pham</TH>
                                                        <TH width="10%">So luong </TH>
                                                        <TH width="9%">Don vi </TH>
                                                        <TH width="15%">Don gia</TH>
                                                        <TH width="15%">T. tien (w/o VAT) </TH>
                                                    </TR>

															                                                    
                                    		<% 

               								String lightrow = "tblightrow";
                                            String darkrow = "tbdarkrow";
                                            int size = new Integer(dmhBean.getSize()).intValue();
                                            int m= 0;
                                    		for(int i=0;i< size ;i++){ 			
                                    			if(m%2 == 0){					%>
													<TR class= <%=lightrow%> >
											<% 	}else{%>	         
													<TR class= <%=darkrow%> >
											<%	} %>
         
                                                       <TD><%= masp[i] %></TD>
                                                       <TD><%= tensp[i] %></TD>
                                                       <TD align = "center"><%= sl[i] %></TD>
                                                       <TD align = "center">Thung</TD>
                       								   <TD align = "right"><%= formatter.format(Long.parseLong(dg[i])) %></TD>
                                                       <TD align = "right"><%= formatter.format(Long.parseLong(tienbVAT[i])) %></TD>																							
                                                                                                              
                                                  </TR>
                                            <% m++;} %>
                                                    
                                                   
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
<% if(!(ncc == null)) ncc.close(); %>
<% if(!(dvkd == null)) dvkd.close(); %>
<% if(!(kbh == null)) kbh.close(); %>
<% dmhBean.DBclose(); %>
<%}%>