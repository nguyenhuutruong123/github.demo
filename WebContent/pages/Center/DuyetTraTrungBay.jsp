<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.beans.duyettratrungbay.IDuyettratrungbayList" %>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "java.text.NumberFormat" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "geso.dms.center.util.ChuyenNgu" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	//if(!util.check(userId, userTen, sum)){
		//response.sendRedirect("/SOHACO/index.jsp");
	//}else{ 
	%>

<% IDuyettratrungbayList dttbBean = (IDuyettratrungbayList)session.getAttribute("dttbBean"); %>
<% ResultSet duyetRs = (ResultSet)dttbBean.getDuyettraRS() ; %>
<% ResultSet cttbRs = (ResultSet)dttbBean.getCttbRs() ; %>
<% ResultSet vung = (ResultSet)dttbBean.getVung() ; %>
<% ResultSet kv = (ResultSet)dttbBean.getKvRs() ; %>
<% ResultSet npp = (ResultSet)dttbBean.getNppRs(); %>

<% Utility Util = new Utility(); %>
<% int[] quyen = new  int[6];
	quyen = Util.Getquyen("DuyettratrungbaySvl","",userId); %>
<% String nnId = (String)session.getAttribute("nnId"); %> 
<% if(nnId == null) {
	nnId = "vi"; 
	
	}	
String url = util.getChuyenNguUrl("DuyettratrungbaySvl","",nnId);	
	%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="java.sql.SQLException"%>
<HTML>
<HEAD>
<TITLE>SOHACO - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">

<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<SCRIPT language="JavaScript" type="text/javascript">
function submitform()
{

    document.forms['dttbForm'].submit();
}


function clearform()
{      document.forms['dttbForm'].schemeId.value= '';
       document.forms['dttbForm'].trangthai.value= '';
       submitform();
}


function newform()
{
	document.dttbForm.action.value='new';
    document.forms['dttbForm'].submit();
}

</SCRIPT>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name ="dttbForm" method="post" action="../../DuyettratrungbaySvl" >
<input type="hidden" name="action" value="0">

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;<%= " " + url %> </TD> 
							 <TD colspan="2" align="right" class="tbnavigation">Ch??o m???ng b???n <%=userTen %>&nbsp;  </TD></tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>
			<TABLE width="100%" border="0" cellpadding="0"  cellspacing="1" >
			  	<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Th??ng b??o</LEGEND>
				
	    				<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold"  style="width:100%" rows="1"><%= dttbBean.getMessage() %></textarea>

						</FIELDSET>
				   </TD>
				</tr>			

				<TR>
				  <TD height="100%" width="100%">
						<FIELDSET >
						<LEGEND class="legendtitle" style="color:black">??i???u ki???n l???c</LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
						  <TR>
							  	<TD width="15%" class="plainlabel"><%=ChuyenNgu.get("Ch????ng tr??nh",nnId) %></TD>
						  	  	<TD class="plainlabel">
									<SELECT name="schemeId" onChange = "submitform();">
								    <option value=""></option>
								      <% try{while(cttbRs.next()){ 
								    	if(cttbRs.getString("pk_seq").equals(dttbBean.getCttbId())){ %>
								      		<option value='<%=cttbRs.getString("pk_seq")%>' selected><%=cttbRs.getString("scheme") + "_" + cttbRs.getString("diengiai") %></option>
								      	<%}else{ %>
								     		<option value='<%=cttbRs.getString("pk_seq")%>'><%=cttbRs.getString("scheme") + "_" + cttbRs.getString("diengiai") %></option>
								     	<%}}}catch(java.sql.SQLException e){} %>	  
                        				</SELECT>						  	  	
						  	  	
						  	  	</TD>
						  </TR>

						  <%-- <TR>
						    	<TD class="plainlabel">V??ng</TD>
							    <TD class="plainlabel"><SELECT name="vungId" onChange = "submitform();">
								    <option value="0"></option>
								      <% try{while(vung.next()){ 
								    	if(vung.getString("pk_seq").equals(dttbBean.getVungId())){ %>
								      		<option value='<%=vung.getString("pk_seq")%>' selected><%=vung.getString("diengiai") %></option>
								      	<%}else{ %>
								     		<option value='<%=vung.getString("pk_seq")%>'><%=vung.getString("diengiai") %></option>
								     	<%}}}catch(java.sql.SQLException e){} %>	  
                        				</SELECT>			
                        		</TD>
						</TR>
						<TR>  
							    <TD class="plainlabel">Khu V???c </TD>
							    <TD class="plainlabel"><SELECT name="kvId" onChange = "submitform();">
								    <option value="0"></option>
								      <% try{while(kv.next()){ 
								    	if(kv.getString("pk_seq").equals(dttbBean.getKvId())){ %>
								      		<option value='<%=kv.getString("pk_seq")%>' selected><%=kv.getString("diengiai") %></option>
								      	<%}else{ %>
								     		<option value='<%=kv.getString("pk_seq")%>'><%=kv.getString("diengiai") %></option>
								     	<%}}}catch(java.sql.SQLException e){} %>	  
                        				</SELECT>			
                        		</TD>		
					      </TR>
					      
					      <TR>
						    	<TD class="plainlabel"><B></b>Chi nh??nh / Nh?? ph??n ph???i</TD>
							    <TD class="plainlabel"><SELECT name="nppId" onChange = "submitform();">
								    <option value="0"></option>
								      <% try{while(npp.next()){ 
								    	if(npp.getString("pk_seq").equals(dttbBean.getNppId())){ %>
								      		<option value='<%=npp.getString("pk_seq")%>' selected><%=npp.getString("ten") %></option>
								      	<%}else{ %>
								     		<option value='<%=npp.getString("pk_seq")%>'><%=npp.getString("ten") %></option>
								     	<%}}}catch(java.sql.SQLException e){} %>	  
                        				</SELECT>			
                        		</TD>
							    
					      </TR> --%>

						  <TR>
							  	<TD width="130px" class="plainlabel"><%=ChuyenNgu.get("Tr???ng th??i",nnId) %> </TD>
									<TD class="plainlabel">
										<select name="trangthai" onChange="submitform();">
											<% if (dttbBean.getTrangthai().equals("1")){%>
												<option value=""></option>
											  	<option value="0"><%=ChuyenNgu.get("Ch??a duy???t",nnId) %></option>
											  	<option value="1" selected><%=ChuyenNgu.get("???? duy???t",nnId) %></option>

											<%}else if(dttbBean.getTrangthai().equals("0")) {%>
											  	<option value="" ></option>
											 	<option value="0" selected><%=ChuyenNgu.get("Ch??a duy???t",nnId) %></option>
											  	<option value="1" ><%=ChuyenNgu.get("???? duy???t",nnId) %></option>
											  	
											<%}else{%>
												<option value="" selected> </option>
												<option value="0" ><%=ChuyenNgu.get("Ch??a duy???t",nnId) %></option>
											  	<option value="1" ><%=ChuyenNgu.get("???? duy???t",nnId) %></option>			
											<%} %>
									          </select>
									</TD>
						  	  	
						  </TR>
						  </TABLE>
						  
						  <TABLE  width="100%" cellpadding="4" cellspacing="0">
						  <TR>
                                	<TD class="plainlabel" align="left">
                                	
                                	 	<a class="button" href="javascript:submitform()">
                                		<img style="top: -4px;" src="../images/Search30.png" alt=""><%=ChuyenNgu.get("T??m ki???m",nnId) %></a>&nbsp;&nbsp;&nbsp;&nbsp;
                                	
                                		<a class="button2" href="javascript:clearform()">
										<img style="top: -4px;" src="../images/button.png" alt=""><%=ChuyenNgu.get("Nh???p l???i",nnId) %></a>&nbsp;&nbsp;&nbsp;&nbsp;	
                                	<!-- 
                                		<INPUT name="reinitialiser" type="button" value="Nhap lai" onClick="clearform();"> -->
                                		</TD>
                                	<TD class="plainlabel" colspan=4>&nbsp;</TD>
                                </TR>

 						  <TR>							  	
 						   <TD class="plainlabel" colspan="2">
 						   <%if(quyen[Utility.THEM] == 1){ %>
 						   <a class="button2" href="javascript:newform();"> 						   
								<img style="top: -4px;" src="../images/New.png" alt=""><%=ChuyenNgu.get("T???o m???i",nnId) %></a>&nbsp;&nbsp;&nbsp;&nbsp;
							<%} %>
 						  </TD>
						 </TR>

						</TABLE>
						</FIELDSET>
					</TD>
				</TR>
				<TR>
				  <TD height="100%" width="100%">
						<FIELDSET>
						<LEGEND class="legendtitle" style="color:black">Duy???t tr???</LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="1">
							<tr class="tbheader">
								<TH width="10%" ><%=ChuyenNgu.get("Di???n gi???i",nnId) %></TH>
							  	<TH width="15%" ><%=ChuyenNgu.get("Ch????ng tr??nh tr??ng b??y",nnId) %></TH>
								<TH width="10%" ><%=ChuyenNgu.get("Tr???ng th??i",nnId) %></TH>
								<TH width="10%" ><%=ChuyenNgu.get("Ng??y ????? ngh???",nnId) %></TH>
								<TH width="10%" ><%=ChuyenNgu.get("Ng??y s???a",nnId) %></TH>
								<TH width="10%" ><%=ChuyenNgu.get("Ng?????i t???o",nnId) %></TH>
								<TH width="10%" ><%=ChuyenNgu.get("Ng?????i s???a",nnId) %></TH>
							  	<TH width="10%" ><%=ChuyenNgu.get("T??c v???",nnId) %></TH>
						  </tr>
						  <% try{
							    String lightrow = "tblightrow";
								String darkrow = "tbdarkrow";
								int m = 0;
								if(duyetRs != null){
									while(duyetRs.next()){
										String tt = duyetRs.getString("TRANGTHAI");
										String trangthai = "Ch??a duy???t";
										if(tt.equals("1"))
											trangthai = "???? duy???t";
										if(tt.equals("2"))
											trangthai = "???? h???y";
										
									if (m % 2 != 0) {%>						
										<TR class= <%=lightrow%> >
									<%} else {%>
										<TR class= <%= darkrow%> >
									<%}%>
										<TD align="left"><div align="left"><%= duyetRs.getString("DIENGIAI")%></div></TD>
										<TD align="left"><div align="left"><%= duyetRs.getString("SCHEME")%></div></TD>
										<TD align="left"><div align="left"><%= trangthai%></div></TD>
										<TD align="left"><div align="left"><%= duyetRs.getString("NGAYDENGHI")%></div></TD>                                   
										<TD align="left"><div align="left"><%= duyetRs.getString("NGAYSUA")%></div></TD> 
										<TD align="left"><div align="left"><%= duyetRs.getString("NGUOITAO")%></div></TD> 
										<TD align="left"><div align="left"><%= duyetRs.getString("NGUOISUA")%></div></TD> 
										<TD align="left"><div align="center">
										<%if(tt.equals("0")){ %>
											<%if(quyen[Utility.SUA]!=0 && tt.equals("0")){ %>                  
						                      	<A href = "../../DuyettratrungbayUpdateSvl?userId=<%=userId%>&update=<%= duyetRs.getString("PK_SEQ")%>"><IMG src="../images/Edit20.png" alt="C???p nh???t" title="C???p nh???t" border="0"></A>
					                      	<%} %>
				                      		<%if(quyen[Utility.CHOT]!=0 && tt.equals("0")){ %>    
							                  	<A href = "../../DuyettratrungbaySvl?userId=<%=userId%>&chot=<%= duyetRs.getString("PK_SEQ") %>"><img src="../images/Chot.png" alt="Duy???t" title="Duy???t" width="20" height="20" longdesc="Duy???t" border=0 onclick="if(!confirm('B???n mu???n duy???t ch????ng tr??nh n??y?')) return false;"></A>       
											<%} %>
											<%if(quyen[Utility.XOA]!=0 && tt.equals("0")){ %>    
						                  		<A href = "../../DuyettratrungbaySvl?userId=<%=userId%>&delete=<%= duyetRs.getString("PK_SEQ") %>"><img src="../images/Delete20.png" alt="X??a" title="X??a" width="20" height="20" longdesc="X??a" border=0 onclick="if(!confirm('B???n C?? Mu???n X??a Duy???t Tr??? N??y?')) return false;"></A>       
					                 		<%} %>
				                      	<%} else{%>
				                      		<%if(quyen[Utility.XEM]!=0){ %>                  
						                      <A href = "../../DuyettratrungbayUpdateSvl?userId=<%=userId%>&display=<%= duyetRs.getString("PK_SEQ")%>"><IMG src="../images/Display20.png" alt="Xem" title="Xem" border="0"></A>
				                      		<%} %>    
				                      	<%} %>
										</div></TD> 
									</TR>
						  		<% m++ ;} 
						  		
						  		}%>		
						  		
						  <%}catch(java.sql.SQLException e){}%>
							<tr class="tbfooter"><td colspan="12" > &nbsp; </td></tr>
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