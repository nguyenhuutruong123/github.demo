<%@page import="geso.dms.center.beans.donmuahang.ISanPhamTraKM"%>
<%@page import="java.util.List"%>
<%@page import="geso.dms.center.beans.donmuahang.ITaodonhangKm"%>
 
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DecimalFormat"%>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/Bibica/index.jsp");
	}else{ %>

<% 
	ITaodonhangKm dhkm=(ITaodonhangKm)session.getAttribute("dhkm");
	String thang = dhkm.getThang();
   String nam = dhkm.getNam();
   String msg = dhkm.getMsg();
   ResultSet rsctkm=dhkm.getRsCTKM();
   ResultSet rssp=dhkm.getRsSp();
   
   List<ISanPhamTraKM> listsp1=dhkm.getListSanPham();
   String ctkmchon=dhkm.getCTKMChon();
   
   NumberFormat formatter = new DecimalFormat("#,###,###.##");
   
%>



<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="java.sql.SQLException"%>
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
        $(document).ready(function() { $("#ctkmid123").select2(); });
        $(document).ready(function() { 
        	
        	for(var i=0;i<= <%=listsp1.size()%> ;i++)
        	$("#selectspid"+i).select2(); 
        });
    </script>
<META http-equiv="Content-Style-Type" content="text/css">

<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<SCRIPT language="JavaScript" type="text/javascript">
	function thuchien()
	{
		    if(!confirm("B???n c?? ch???c mu???n th???c hi???n kh??ng ?"))
		    {
			  return;	
		    }
			document.forms["mkbctBean"].action.value="thuchien";   	
	   	    document.forms['mkbctBean'].submit();
	}	
	function submit1()
	{
			document.forms["mkbctBean"].action.value="chance";   	
    		document.forms['mkbctBean'].submit();
	}
	function keypress(e) //H??m d??ng d? ngan ngu?i d??ng nh?p c??c k?? t? kh??c k?? t? s? v??o TextBox
	{    
		var keypressed = null;
		if (window.event)
			keypressed = window.event.keyCode; //IE
		else
			keypressed = e.which; //NON-IE, Standard
		
		if (keypressed < 48 || keypressed > 57)
		{ 
			if (keypressed == 8 ||  keypressed == 127 || keypressed == 37 || keypressed == 39 || keypressed == 0 || keypressed == 46)
			{//Ph??m Delete v?? Ph??m Back
				return;
			}
			return false;
		}
	}
	

</SCRIPT>

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

<% Csrf csdr=new Csrf(request,response,true,false,true);%>
<input type = "hidden" name="action" value = "1">
<TABLE width="100%" border="0" cellspacing="0" cellpadding="1" height="100%">
	<TR>
		<TD align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD>				
					   	<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
			  				<TR height="22">
				   				<TD align="left" class="tbnavigation">
				   					&nbsp;Qu???n l?? b??n h??ng &gt; T???o ????n h??ng khuy???n m??i</TD>
				   				<TD  align="right" class="tbnavigation">Ch??o m???ng b???n <%= userTen %>&nbsp;  </TD> 
		     				</TR>
   
						</TABLE>
					</TD>								
				</TR>
			</TABLE>
			<TABLE width="100%" border="0" cellpadding="1"  cellspacing="1" >
			  <tr>
				<TD align="left" colspan="4" class="legendtitle">
				<FIELDSET>
				<LEGEND class="legendtitle">Th??ng b??o</LEGEND>
			
   				<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold" style="width: 100%" readonly="readonly" rows="1" ><%= msg %></textarea>
				</FIELDSET>
				</TD>
			 </tr> 
			</TABLE>
			<TABLE width="100%" border="0" cellpadding="0"  cellspacing="1" >
				<TR>
				  <TD height="100%" width="100%">
						<FIELDSET >
						<LEGEND class="legendtitle" style="color:black"> T???o ????n h??ng khuy???n m??i th??ng </LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
						  <TR>
							  	<TD width="20%" class="plainlabel">Ch???n th??ng </TD>
						  	  	<TD width="80%" class="plainlabel">
									<select onchange="submit1();" name="thang"  style="width :50px" ">
									<option value=0> </option>  
									<%
  										int k=1;
  									for(k=1;k<=12;k++){
  										String chuoi=""+k;
  									  if(thang.equals(chuoi)){
  									%>
									<option value=<%=k%> selected="selected" > <%=k%></option> 
									<%
 										}else{
 									%>
									<option  value=<%=k%> ><%=k%></option> 
									<%
 										}
 									  }
 									%>
									</select>
						  	  	</TD>
						  </TR>

						  <TR>
							  	<TD class="plainlabel">Ch???n n??m </TD>
						  	  	<TD class="plainlabel">
									<select onchange="submit1();" name="nam" style="width :50px" ">
									<option value=0> </option>  
									<%
									  
  										int n;
  										for(n=2008;n<2020;n++){
  										if(nam.equals(""+n)){
  									%>
									<option value=<%=n%> selected="selected" > <%=n%></option> 
									<%
 										}else{
 									%>
									<option value=<%=n%> ><%=n%></option> 
									<%
 										}
 									 }
 									%>
 									</select> 
						  	  	</TD>
						  </TR>
 							 
					 <TR>
							  	<TD class="plainlabel">Ch???n ch????ng tr??nh KM </TD>
						  	  	<TD class="plainlabel">
									<select onchange="submit1();" name="schemechon" id="ctkmid123" style="width :450px" ">
									<option value=0> </option>  
									<%
									  
  										if(rsctkm!=null)
  										while (rsctkm.next()){
  										if(rsctkm.getString("pk_seq").equals(dhkm.getCTKMChon())){
		  									%>
											<option value="<%=rsctkm.getString("pk_seq") %>" selected="selected" > <%=rsctkm.getString("scheme")+ "--"+ rsctkm.getString("diengiai")%></option> 
											<%
		 										}else{
		 									%>
											<option value="<%=rsctkm.getString("pk_seq") %>" > <%=rsctkm.getString("scheme")+ "--"+ rsctkm.getString("diengiai")%></option>
											<%
 										}
 									 }
 									%>
 									</select> 
						  	  	</TD>
						  </TR>
 						<TR>							  	
 						   <TD colspan="2"><a class="button2" href="javascript:thuchien();">
							<img style="top: -4px;" src="../images/button.png" alt="">Duy???t s???n ph???m tr??? khuy???n m??i</a>&nbsp;&nbsp;&nbsp;&nbsp;
 						  </TD>
						 </TR>
 						<tr>
 						 
 						  <td colspan="2">
 						   <TABLE  width="100%" >
 						      <tr  class="tbheader">
 						        	 <th  width="20%" style="display:none">
	 						   			 Nh?? ph??n ph???i
	 						     	 </th>
	 						    	  <th  width="20%" style="display:none">
	 						     	  Khuy???n m??i
	 						     	 </th>
	 						      
		 						      <th  width="40">
		 						   		S???n ph???m
		 						      </th>
	 						              <th  width="10%">
		 						   		S??? l?????ng
		 						      </th>
		 						      <th  width="40%">
		 						      S???n ph???m thay th???
		 						      </th>
 						  		      <th  width="10%">
	 						     	 	S??? l?????ng thay th???
	 						    	  </th>
 						      </tr> 
 						     
 						      <%
 						     int i=0;
 						     String nppIdPrev="";
 						      while (i<listsp1.size()) 
 						      { 						    	  
 						    	  ISanPhamTraKM sp=listsp1.get(i);
 						       	if(!nppIdPrev.trim().equals(sp.getNPPId().trim()))
 						       	{ 
										nppIdPrev= sp.getNPPId();
										%>
									  <tr style="color:black ;font-weight: bold;font-size:12" >
									
									 <TD colspan="<%=4%>"  >
										 <%=" "+sp.getNPPTen()%>   
									 </TD>  
									 </tr>
								<%} %>
 						    	<tr class= 'tblightrow'>
 						    	<td style="display:none">
 						    	  <input type="hidden" name="nppid" value="<%=sp.getNPPId()%>" >
 						    	  	  <input type="hidden" name="kbhid" value="<%=sp.getKBHId()%>" >
 						    	  	  <input type="hidden" name="nppten" value="<%=sp.getNPPTen()%>" >
 						    	</td>
 						    	<td style="display:none"> 
 						    	    <input   style="width:100%"  type="text" name="scheme" value="<%=sp.getScheme()%>" >
 						    	    <input type="hidden" name="ctkmid" value="<%=sp.getCtkm()%>" >
 						    	    </td>
 						    	    <td>
 						    	    	<input type="hidden" name="spid" value="<%=sp.getSpId()%>" >
 						    	    	<input  style="width:100%"   type="text"  name="spma" value="<%=sp.getspma()+"-"+sp.getspten() %>" >
 						    	    	<input type="hidden" name="spten" value="<%=sp.getspten()%>" >
 						    	    	
 						    	    </td>
 						    	   <td>
 						    	  <input  style="width:100%;text-align:right;"  type="text" name="soluong" readonly="readonly" value="<%=formatter.format( sp.getsoluong() ) %>" style="width:100%" >
 						    	 
 						    	</td>
 						    		<td>
 						    		<select name="idsptt"  id="selectspid<%=i%>"  style="width:100%"  >
 						    		<option value=""> </option>
 						    			<% try{
 						    				rssp.beforeFirst();
 						    				while (rssp.next()){
 						    					if(rssp.getString("pk_seq").equals(sp.getSpIdTT())){
 						    					
 						    					%>
 						    						<option value="<%=rssp.getString("pk_seq") %>" selected="selected"> 
 						    						 <%=rssp.getString("ma")+"-"+rssp.getString("ten") %> </option>			
 						    						<%
 						    					}else{
 						    						%>
 						    						<option value="<%=rssp.getString("pk_seq") %>" > 
 						    						 <%=rssp.getString("ma")+"-"+rssp.getString("ten") %> </option>			
 						    						<%
 						    					}
 						    				}
 						    				%>
 						    			<% }catch(Exception er){} %>
 						    			</select>
 						    		</td>
 						    		     <td> 
 						    	    	<input type="text"  style="width:100%;text-align:right;" name="soluongtt" onkeypress="return keypress(event);" value="<%= formatter.format(sp.getsoluongtt() )%>"  >
 						    	    </td>
 						    	</tr>
 						      <%  i++; 
 						      }   %>
 						      
 						   </table>
 						  </td>
 						
 						</tr>		 
 		 
						</TABLE>
						</FIELDSET>
					</TD>
				</TR>
		</TABLE>
	</TD>
</TR>
</TABLE>
</form>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</HTML>
<%
}%>