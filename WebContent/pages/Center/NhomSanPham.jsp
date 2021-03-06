<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.beans.nhomsanpham.INhomsanpham" %>
<%@ page  import = "geso.dms.center.beans.nhomsanpham.INhomsanphamList" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%@ page  import = "geso.dms.center.util.ChuyenNgu" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ 
		int[] quyen = new  int[6];
		
		
	%>

<% INhomsanphamList obj = (INhomsanphamList)session.getAttribute("obj"); %>
<% List<INhomsanpham> nsplist = (List<INhomsanpham>)obj.getNspList(); %>
<% String nnId = (String)session.getAttribute("nnId");

String view = obj.getView().trim().length() > 0 ? "&view="+obj.getView() : "";
quyen = util.Getquyen("NhomsanphamSvl",view,userId);


%> 
<% if(nnId == null) {
	nnId = "vi";
	} 
	String url = util.getChuyenNguUrl("NhomsanphamSvl","",nnId);
	%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">

<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">


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


<SCRIPT language="JavaScript" type="text/javascript">
function submitform()
{
	 document.nspForm.action.value = "new";
   	 document.forms["nspForm"].submit();
}

function searchform()
{
	 document.nspForm.action.value = "search";
   	 document.forms["nspForm"].submit();
}

function clearform()
{
	document.nspForm.diengiai.value = "";
	document.nspForm.thanhvien.selectedIndex = 2;
    document.nspForm.trangthai.selectedIndex = 0;
    document.nspForm.lnhom.value = "";
	document.nspForm.tungay.value = "";
	document.nspForm.denngay.value = "";
 	document.forms["nspForm"].submit();
}

</SCRIPT>

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

<form name="nspForm" method="post" action="../../NhomsanphamSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%=userId%>'>
<input type="hidden" name="view" value='<%=obj.getView()%>'>
<input type="hidden" name="action" value="1">

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF"><!--begin body Dossier-->
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
				   		<table width="100%" border="0" cellpadding="0" cellspacing="0">
					  		<tr height="22">
						  		<TD align="left" colspan="2" class="tbnavigation"><%=url %></TD> 
						  		<TD colspan="2" align="right" class="tbnavigation">Ch??o m???ng b???n <%=userTen %>&nbsp;  </TD>
						  	</tr>
						</table></TD>
			  	</TR>
			</TABLE>
			
			<TABLE width="100%" border="0" cellpadding="0">
				<TR>
					<TD >
						<FIELDSET><LEGEND class="legendtitle">&nbsp;Ti??u ch?? t??m ki???m &nbsp;</LEGEND>
							<TABLE  width="100%" cellspacing="0" cellpadding="6">
								
								<TR>
									<TD width="19%" class="plainlabel"><%=ChuyenNgu.get("Di???n gi???i",nnId) %></TD>
								    <TD width="81%" class="plainlabel">
										<TABLE cellpadding="0" cellspacing="0">
											<TR><TD>
												<INPUT name="diengiai" type="text" size="40" value='<%=obj.getDiengiai()%>' onChange ="searchform();">
											</TD></TR>
										</TABLE>								
								</TR>
								<TR>
								  <TD class="plainlabel"><%=ChuyenNgu.get("Lo???i th??nh vi??n",nnId) %></TD>
								  <TD  class="plainlabel"><select name="thanhvien" onChange = "searchform();">
								    <% if (obj.getLoaithanhvien().equals("1")){ %>								    
								    	<option value="1" selected><%=ChuyenNgu.get("Nh??m s???n ph???m",nnId) %></option>
								    	<option value="2"><%=ChuyenNgu.get("S???n ph???m",nnId) %></option>
								    	<option value=""> </option>
								    <%}else{ 
								    	 if (obj.getLoaithanhvien().equals("2")){ %>								    
								    		<option value="1"><%=ChuyenNgu.get("Nh??m s???n ph???m",nnId) %></option>
								    		<option value="2" selected><%=ChuyenNgu.get("S???n ph???m",nnId) %></option>
								    		<option value=""> </option>
								    	<%}else{ %>							    							    
								    		<option value="1" ><%=ChuyenNgu.get("Nh??m s???n ph???m",nnId) %></option>
								    		<option value="2"><%=ChuyenNgu.get("S???n ph???m",nnId) %></option>
								    		<option value="" selected> </option>
								    <%} }%>
								    
								    </select>
								  </TD>								  							 							  							 
							  </TR>
							  
							  <TR>
							  		<TD class="plainlabel"><%=ChuyenNgu.get("Lo???i nh??m",nnId) %></TD>
								  <TD  class="plainlabel"><select name="lnhom" onChange = "searchform();">
								    <% if (obj.getLoainhom().equals("0")){ %>	
								    	<option value=""> </option>							    
								    	<option value="0" selected><%=ChuyenNgu.get("Nh??m b??nh th?????ng",nnId) %></option>
								    	<option value="3"><%=ChuyenNgu.get("Nh??m ch??? ti??u",nnId) %></option>								    	
								    <%}else{ 
								    	 if (obj.getLoainhom().equals("3")){ %>	
								    	 	<option value=""> </option>							    								    		
								    		<option value="0"><%=ChuyenNgu.get("Nh??m b??nh th?????ng",nnId) %></option>
								    		<option value="3" selected><%=ChuyenNgu.get("Nh??m ch??? ti??u",nnId) %></option>								    		
								    	<%}else{ %>	
								    		<option value="" selected> </option>						    							    
								    		<option value="0"><%=ChuyenNgu.get("Nh??m b??nh th?????ng",nnId) %></option>
								    		<option value="3"><%=ChuyenNgu.get("Nh??m ch??? ti??u",nnId) %></option>								    		
								    <%} }%>
								    
								    </select>
								  </TD>				
							  </TR>
							  
								<TR>
									<TD class="plainlabel"><%=ChuyenNgu.get("Tr???ng th??i",nnId) %></TD>
								  	<TD  class="plainlabel"><SELECT name = "trangthai"  onChange = "searchform();">
								  	<% if (obj.getTrangthai().equals("0")){ %>
								    	<option value="2"> </option>
								    	<option value="1"><%=ChuyenNgu.get("Ho???t ?????ng",nnId) %></option>
								    	<option value="0" selected><%=ChuyenNgu.get("Ng??ng ho???t ?????ng",nnId) %></option>
									<%}else{ 							
								  		if (obj.getTrangthai().equals("1")){ %>
								    	<option value="2"> </option>
								    	<option value="1" selected><%=ChuyenNgu.get("Ho???t ?????ng",nnId) %></option>
								    	<option value="0" ><%=ChuyenNgu.get("Ng??ng ho???t ?????ng",nnId) %></option>
									<%}else{ %>
								    	<option value="2" selected> </option>
								    	<option value="1" ><%=ChuyenNgu.get("Ho???t ?????ng",nnId) %></option>
								    	<option value="0" ><%=ChuyenNgu.get("Ng??ng ho???t ?????ng",nnId) %></option>
									<%}} %>

										</SELECT></TD>
									
								</TR>
								
										<TR>
											<TD class="plainlabel" ><%=ChuyenNgu.get("T??? ng??y",nnId) %></TD>
											<TD class="plainlabel" colspan="3">
												<TABLE cellpadding="0" cellspacing="0">
													<TR><TD>
														<input class="days" type="text" name="tungay" value='<%=obj.getTungay() %>'  size="20" onchange = "searchform();">
													</TD>
													
													</TR>
												</TABLE>																					
		  									</TD>
										</TR>
										<TR>
                                          <TD class="plainlabel" ><%=ChuyenNgu.get("?????n ng??y",nnId) %></TD>
										  <TD class="plainlabel" colspan="3">
										  		<TABLE cellpadding="0" cellspacing="0"><TR><TD>
										 <input class="days" type="text" name="denngay" value='<%=obj.getDenngay() %>' size="20" onchange = "searchform();">
										  		</TD>
										

											  </TR>
											 </TABLE>
									  </TR>
								<TR>
									<TD class="plainlabel">
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
			
			<TABLE width="100%" border = "0" cellpading = "0" cellspacing = "0">
				<TR>
					<TD width="100%">
					<FIELDSET>
					<LEGEND class="legendtitle">&nbsp;Nh??m s???n ph???m &nbsp;&nbsp;&nbsp;
					<%if(quyen[0]!=0 && !obj.getView().equals("NPP") ){ %>
						<a class="button3" href="javascript:submitform()">
    					<img style="top: -4px;" src="../images/New.png" alt=""><%=ChuyenNgu.get("T???o m???i",nnId) %></a>      
    				<%} %>                      		
					</LEGEND>
					<TABLE class="" width="100%">
						<TR>
							<TD width="98%">
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
								<TR class="tbheader">
									<TH width="10%"><%=ChuyenNgu.get("M?? nh??m",nnId) %></TH>
									<TH width="10%"><%=ChuyenNgu.get("M?? ?????ng b???",nnId) %></TH>
									<TH width="15%"><%=ChuyenNgu.get("T??n nh??m",nnId) %></TH>
									<TH width="19%" style="display: none"><%=ChuyenNgu.get("Di???n gi???i",nnId) %></TH>
									<TH width="12%"><%=ChuyenNgu.get("Lo???i th??nh vi??n",nnId) %></TH>
									<TH width="9%"><%=ChuyenNgu.get("Tr???ng th??i",nnId) %></TH>
									<TH width="8%"><%=ChuyenNgu.get("Ng??y t???o",nnId) %></TH>
									<TH width="13%"><%=ChuyenNgu.get("Ng?????i t???o",nnId) %></TH>
									<TH width="9%"><%=ChuyenNgu.get("Ng??y s???a",nnId) %></TH>
									<TH width="12%"><%=ChuyenNgu.get("Ng?????i s???a",nnId) %></TH> 
									<TH width="9%"><%=ChuyenNgu.get("T??c v???",nnId) %></TH>
								</TR>
						<% 
							INhomsanpham nsp = null;
							int size = nsplist.size();
							int m = 0;
							Hashtable parent = new Hashtable();
							parent.put("0", "* ");
							String star = "";
							String lightrow = "tblightrow";
							String darkrow = "tbdarkrow";
							while (m < size){
								nsp = nsplist.get(m);
								if (m % 2 != 0) {%>						
									<TR class= <%=lightrow%> >
								<%} else {%>
									<TR class= <%= darkrow%> >
								<%} %>										
							
								<TD align="center"><%= nsp.getId() %></TD>
									<TD align="center"><%= nsp.getMa() %></TD>
														
							<%	if(!obj.getSearch()){
							
									if (parent.containsKey(nsp.getParent())){									
										star = (String)parent.get(nsp.getParent()); 
									}else{																		
										star = star + "* ";
										parent.put(nsp.getParent(), star);
									}		%>

									<TD align="left"><%= star %><%=nsp.getTen() %></TD>
							
							<%	}else{ %>		
							
									<TD align="left"><%=nsp.getTen() %></TD>
							
							<%	} %>
									<TD style="display: none" ><%=nsp.getDiengiai() %></TD>
									<% if(nsp.getThanhvien().equals("2")) {%>
										<TD align="center"><div align="left"><%=ChuyenNgu.get("S???n ph???m",nnId) %></div></TD>
									<%}else {%>
										<TD align="center"><div align="left"><%=ChuyenNgu.get("Nh??m s???n ph???m",nnId) %></div></TD>
									<%} %>
									
									<% if(nsp.getTrangthai().equals("1")) {%>
										<TD align="center"><%=ChuyenNgu.get("Ho???t ?????ng",nnId) %></TD>
									<%}else {%>
										<TD align="center"><%=ChuyenNgu.get("Ng??ng ho???t ?????ng",nnId) %></TD>
									<%} %>
									<TD align="center"><%= nsp.getNgaytao() %></TD>
									<TD align="center"><%= nsp.getNguoitao() %></TD>
									<TD align="center"><%= nsp.getNgaysua() %></TD>
									<TD align="center"><%= nsp.getNguoisua() %> </TD>
									<TD align="center">
										<TABLE>
											<TR><TD>
											<%if(quyen[Utility.SUA]!=0){
												
												%>
												<A href = "../../NhomsanphamUpdateSvl?userId=<%=userId%>&update=<%=nsp.getId()%>&view=<%=obj.getView()%>"><img src="../images/Edit20.png" alt="Cap nhat" title="C???p nh???t" width="20" height="20" longdesc="Cap nhat" border ="0"></A>
											<%} %>
											</TD>
											<TD>&nbsp;</TD>
											<TD>
											<%
											System.out.println("In quyen "+quyen[1]);
											if( !obj.getView().equals("NPP") && quyen[Utility.XOA]!=0)
											{ 
											%>
												<A href = "../../NhomsanphamSvl?userId=<%=userId%>&delete=<%= nsp.getId()%>&view=<%=obj.getView()%>"><img src="../images/Delete20.png" alt="Xoa" title="X??a" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('B???n mu???n x??a nh??m s???n ph???m n??y?')) return false;"></A>
											<%} %>	
												</TD>
											</TR>												
										</TABLE>									
									</TD>
								</TR>
							<%m++; }%>
							
								<TR>
									<TD align="center" colspan="13" class="tbfooter">&nbsp;</TD>
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
</form>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</HTML>

<%
	if(nsplist != null) { nsplist.clear(); nsplist = null ;}
	obj = null;
	session.setAttribute("obj", null);

}%>