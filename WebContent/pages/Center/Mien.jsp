<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.beans.mien.IMien" %>
<%@ page  import = "geso.dms.center.beans.mien.IMienList" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%@ page  import = "geso.dms.center.util.ChuyenNgu" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{int[] quyen = new  int[6];
	quyen = util.Getquyen("MienSvl", "",userId);
	
	System.out.println(quyen[0]);
	System.out.println(quyen[1]);
	System.out.println(quyen[2]);
	System.out.println(quyen[3]);	
	System.out.println(quyen[4]); %>


<% IMienList obj = (IMienList)session.getAttribute("obj"); %>
<% List<IMien> vmlist = (List<IMien>)obj.getVmList(); %>
<% String nnId = (String)session.getAttribute("nnId"); %> 
<% if(nnId == null) {
	nnId = "vi"; 
	
	}	
String url = util.getChuyenNguUrl("MienSvl","",nnId);	
	%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<SCRIPT language="javascript" type="text/javascript">
function clearform()
{
    document.vmForm.VungMien.value = "";
    document.vmForm.DienGiai.value = "";      
    document.vmForm.TrangThai.selectedIndex = 2;
    submitform();
}

function submitform()
{
	document.forms['vmForm'].action.value= 'search';
	document.forms['vmForm'].submit();
}

function newform()
{
	document.forms['vmForm'].action.value= 'new';
	document.forms['vmForm'].submit();
}
function thongbao()
{var tt = document.forms['vmForm'].msg.value;
	if(tt.length>0)
    alert(document.forms['vmForm'].msg.value);
	}
	

</SCRIPT>

</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="vmForm" method="post" action="../../MienSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%=userId%>'>
<input type="hidden" name="action" value='1'>

<input type="hidden" name="msg" value='<%=obj.getMsg() %>'>

<script language="javascript" type="text/javascript">
    thongbao();
</script> 

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#ffffff">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" >
					   <table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							   <TD align="left" colspan="2" class="tbnavigation">
							   		<%= " " + url %></TD>
							   <TD colspan="2" align="right" class="tbnavigation">Ch??o m???ng b???n <%=userTen %>&nbsp;  </TD> 
						    </tr>
   						</table>
					</TD>
				</TR>
			</TABLE>
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD>
						<TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
							<TR>
								<TD width="100%" align="left"><FIELDSET>
									<LEGEND class="legendtitle">Ti??u ch?? t??m ki???m </LEGEND>

									<TABLE class="tblight" width="100%" cellpadding="6" cellspacing = "0">
										<TR>
										  <TD class="plainlabel" ><%=ChuyenNgu.get("Mi???n",nnId) %> </TD>
										  <TD class="plainlabel" ><INPUT name="VungMien" size="20" type="text" 
                                          					value="<%= obj.getMien()%>" onChange="submitform();"></TD>
									  </TR>
										<TR>
											<TD width="20%" class="plainlabel" ><%=ChuyenNgu.get("Di???n gi???i",nnId) %> </TD>
										    <TD width="80%" class="plainlabel" ><INPUT name="DienGiai" size="40" type="text" 
                                            				value="<%= obj.getDiengiai()%>" onChange="submitform();"></TD>
										</TR>
										<TR>
											<TD class="plainlabel" ><%=ChuyenNgu.get("Tr???ng th??i",nnId) %></TD>
											<TD class="plainlabel" >
											  <select name="TrangThai" onChange="submitform();">
											    <% if (obj.getTrangthai().equals("1")){%>
											  	<option value="1" selected><%=ChuyenNgu.get("Ho???t ?????ng",nnId) %></option>
											  	<option value="0"><%=ChuyenNgu.get("Ng??ng ho???t ?????ng",nnId) %></option>
											  	<option value="2"> </option>
											  
											<%}else if(obj.getTrangthai().equals("0")) {%>
											 	 <option value="0" selected><%=ChuyenNgu.get("Ng??ng ho???t ?????ng",nnId) %></option>
											  	<option value="1" ><%=ChuyenNgu.get("Ho???t ?????ng",nnId) %></option>
											 	 <option value="2" > </option>
											  
											<%}else{%>																					 	 
											  	<option value="1" ><%=ChuyenNgu.get("Ho???t ?????ng",nnId) %></option>
											  	<option value="0" ><%=ChuyenNgu.get("Ng??ng ho???t ?????ng",nnId) %></option>
											  	<option value="2" selected> </option>
											<%}%>
									          </select></TD>
										</TR>
										<TR>
										    <TD class="plainlabel" colspan=4>
										    	<a class="button2" href="javascript:clearform()">
	<img style="top: -4px;" src="../images/button.png" alt=""><%=ChuyenNgu.get("Nh???p l???i",nnId) %> </a>&nbsp;&nbsp;&nbsp;&nbsp;	
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
			<TABLE width="100%" cellpadding="0" cellspacing="1">
			    <TR>
					<TD align="left" >
						 <FIELDSET>
							<LEGEND class="legendtitle">&nbsp;Mi???n &nbsp;&nbsp;&nbsp;
							<%if(quyen[0]!=0){ %>
								<a class="button3" href="javascript:newform()">
    	<img style="top: -4px;" src="../images/New.png" alt=""><%=ChuyenNgu.get("T???o m???i",nnId) %> </a>                            
							<%} %>
						</LEGEND>
				
							<TABLE class="" width="100%" border="0" cellpadding="0" cellspacing="0">
								<TR>
									<TD width="98%">
										<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
											<TR class="tbheader">
												<TH width="12%"><%=ChuyenNgu.get("M??",nnId) %> </TH>
												<TH width="12%"><%=ChuyenNgu.get("Mi???n",nnId) %> </TH>
											    <TH width="22%"><%=ChuyenNgu.get("Di???n gi???i",nnId) %> </TH>
											    <TH width="11%"><%=ChuyenNgu.get("Tr???ng th??i",nnId) %> </TH>
											    <TH width="9%"><%=ChuyenNgu.get("Ng??y t???o",nnId) %></TH>
											  <TH width="13%"><%=ChuyenNgu.get("Ng?????i t???o",nnId) %> </TH>										
											  <TH width="9%"><%=ChuyenNgu.get("Ng??y s???a",nnId) %> </TH>
											  <TH width="14%"><%=ChuyenNgu.get("Ng?????i s???a",nnId) %></TH>
											  <TH width="10%"><%=ChuyenNgu.get("T??c v???",nnId) %></TH>
										  </TR>
								<% 
									IMien vm = null;
									int size = vmlist.size();
									int m = 0;
									String lightrow = "tblightrow";
									String darkrow = "tbdarkrow";
									while (m < size){
										vm = vmlist.get(m);
										if (m % 2 != 0) {%>						
											<TR class= <%=lightrow%> >
										<%} else {%>
											<TR class= <%= darkrow%> >
										<%}%>
										<TD align="left"><div align="left"><%= vm.getMa() %></div></TD> 
												<TD align="left"><div align="left"><%= vm.getMien() %></div></TD>                                   
												<TD><div align="left"><%= vm.getDiengiai() %></div></TD>
											  <% if (vm.getTrangthai().equals("1")){ %>
													<TD align="left"><%=ChuyenNgu.get("Ho???t ?????ng",nnId) %></TD>
												<%}else{%>
													<TD align="left"><%=ChuyenNgu.get("Ng??ng ho???t ?????ng",nnId) %></TD>
												<%}%>
												<TD align="center"><%=vm.getNgaytao()%></TD>
												<TD align="center"><%=vm.getNguoitao()%></TD>
												<TD align="center"><%=vm.getNgaysua()%></TD>
												<TD align="center"><%=vm.getNguoisua()%></TD>
												<TD align="center">
												<TABLE border = 0 cellpadding="0" cellspacing="0">
													<TR><TD>
													<%if(quyen[2]!=0){ %>
													  <A href = "../../MienUpdateSvl?userId=<%=userId%>&update=<%= vm.getId()%>">
                                               <img src="../images/Edit20.png" alt="Cap nhat" title="C???p nh???t" width="20" height="20" longdesc="Cap nhat" border = 0></A>
													<%} %>
													</TD>
													<TD>&nbsp;</TD>
													<TD>
													<%if(quyen[1]!=0){ %>
													  <A href = "../../MienSvl?userId=<%=userId%>&delete=<%=vm.getId()%>">
                                                <img src="../images/Delete20.png" alt="Xoa" title="X??a" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('B???n c?? mu???n x??a  mi???n ?')) return false;"></A>
                                                	<%} %>
                                                </TD>
													</TR></TABLE>
												</TD>
											</TR>
								<%m++; }%>
								
									<TR>	
									<TD height="" colspan="11" align="center" class="tbfooter">
									&nbsp;</TD>
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
	if(vmlist != null ){ vmlist.clear(); vmlist = null ; }
	obj.closeDB(); obj = null;
	session.setAttribute("obj", null); 

}%>