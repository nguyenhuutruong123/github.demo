<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.beans.nhomkhuyenmai.INhomkhuyenmai" %>
<%@ page  import = "geso.dms.center.beans.nhomkhuyenmai.imp.Nhomkhuyenmai" %>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>

<% INhomkhuyenmai nkmBean = (INhomkhuyenmai)session.getAttribute("nkmBean"); 
	ResultSet spList = (ResultSet)nkmBean.getSpList(); 
	ResultSet spSelected = (ResultSet)nkmBean.getSpSelected();
	ResultSet dvkdList = (ResultSet)nkmBean.getDvkdList();
	ResultSet nhList = (ResultSet)nkmBean.getNhList();
	ResultSet clList = (ResultSet)nkmBean.getCLList();
	String dvkdId = (String) nkmBean.getDvkdId();
	String nhId = (String)nkmBean.getNhId();
	String clId = (String)nkmBean.getClId();
	ResultSet kenh = (ResultSet)nkmBean.getKenh();
	ResultSet nganhhang = (ResultSet)nkmBean.getNganhhang();

%>

<% String nnId = (String)session.getAttribute("nnId"); %> 
<% if(nnId == null) {
 nnId = "vi";  
 }
 String url = util.getChuyenNguUrl("NhomkhuyenmaiSvl","",nnId);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="java.sql.SQLException"%>
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<LINK rel="stylesheet" href="../css/main.css" type="text/css">

<SCRIPT language="JavaScript" type="text/javascript">

function xuatexel()
{
	document.nkmForm.action.value='xuatexel';
   // document.forms["nkmForm"].submit();
}

function filterDvkd()
{
    document.nkmForm.action.value='filter';
    document.nkmForm.nhId.value='0';
    document.nkmForm.clId.value='0';
  //  document.forms["nkmForm"].submit();       
}

function filterNh()
{
    document.nkmForm.action.value='filter';
    document.nkmForm.clId.value='0';
 //   document.forms["nkmForm"].submit();   
    
}

function filterCl()
{
    document.nkmForm.action.value='filter';
  //  document.forms["nkmForm"].submit();       
}

function checkedAll(chk) {
	for(i=0; i<chk.length; i++){
	 	if(document.nkmForm.chon.checked==true){
			chk[i].checked = true;
		}else{
			chk[i].checked = false;
		}
	 }
}

</SCRIPT>
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
	$(document).ready(function() {
		$("select:not(.notuseselect2)").select2({
			width : 'resolve'
		});
	});
</script>

<!-- BEGIN RENDER AUTO -->

<% if (!geso.dms.center.util.GlobalValue.getDevmode()) { %>
	<script type="text/javascript" src="../scripts/disableF12.js"></script>
<% } %>

<!-- END RENDER AUTO -->
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name ="nkmForm" method="post" action="../../NhomkhuyenmaiUpdateSvl" >
<!-- BEGIN RENDER AUTO -->
<%if(!geso.dms.center.util.GlobalValue.getDevmode()){ %>
	<noscript> 
	 	<meta http-equiv="refresh" content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")  %>">
		<!-- //window.location = "https://www.google.com"; -->
	</noscript>
<%} %>
<!-- END RENDER AUTO -->

	<form name="nkmForm" method="post"
		action="../../NhomkhuyenmaiUpdateSvl"> 
 <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
		<input type="hidden" name="userId" value='<%=userId%>'> <input
			type="hidden" name="nkmId" value='<%= nkmBean.getId()  %>'> <input
			type="hidden" name="action" value="0">

		<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
			height="100%">
			<TR>
				<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
						<TR>
							<TD align="left" class="tbnavigation">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr height="22">
										<TD align="left" colspan="2" class="tbnavigation">&nbsp;Qu???n
											l?? khuy???n m??i &gt; Nh??m s???n ph???m khuy???n m??i &gt; Hi???n th???</TD>
										<TD colspan="2" align="right" class="tbnavigation">Ch??o
											m???ng b???n <%=userTen %>&nbsp;</TD>
									</tr>
								</table></TD>
						</TR>
					</TABLE>
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
						<TR>
							<TD>
								<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
									<TR class="tbdarkrow">
										<TD width="30" align="left"><A
											href="javascript:history.back()"><img
												src="../images/Back30.png" alt="Quay ve" title="Quay ve"
												border="1" longdesc="Quay ve" style="border-style: outset">
										</A>
										
										<TD>&nbsp;</TD>

									</TR>
								</TABLE></TD>
						</TR>
					</TABLE>
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
						<tr>
							<TD align="left" colspan="4" class="legendtitle">
								<FIELDSET>
									<LEGEND class="legendtitle">B??o l???i nh???p li???u</LEGEND>

									<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold" style="width: 100%"
										readonly="readonly" rows="1"><%= nkmBean.getMessage() %></textarea>
									<% nkmBean.setMessage(""); %>
								</FIELDSET></TD>
						</tr>

						<TR>
							<TD height="100%" width="100%">
								<FIELDSET>
									<LEGEND class="legendtitle" style="color: black">Th??ng
										tin nh??m khuy???n m??i </LEGEND>
									<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
										<TR>
											<TD width="29%" class="plainlabel">T??n nh??m khuy???n m??i
												<FONT class="erroralert"> *</FONT>
											</TD>
											<TD class="plainlabel"><INPUT type="text" name="ten"
												size="20" value='<%= nkmBean.getTen()%>'>
											</TD>
										</TR>
										<TR>
											<TD class="plainlabel">Di???n gi???i</TD>
											<TD class="plainlabel"><INPUT type="text"
												name="diengiai" size="40"
												value='<%= nkmBean.getDiengiai() %>'>
											</TD>
										</TR>

										<TR>
											<TD class="plainlabel">????n v??? kinh doanh</TD>
											<TD class="plainlabel"><SELECT name="dvkdId"
												onchange="filterDvkd();">
													<OPTION value="0"></OPTION>
													<% if(dvkdList!= null){						  	  			
						   					while (dvkdList.next()){
						  	  					if (dvkdList.getString("pk_seq").equals(dvkdId)){%>
													<OPTION value=<%= dvkdList.getString("pk_seq")%> selected><%= dvkdList.getString("diengiai")%></OPTION>
													<%}else{ %>
													<OPTION value=<%= dvkdList.getString("pk_seq")%>><%= dvkdList.getString("diengiai") %></OPTION>
													<%				  }
						  	  				
						  	  				}
						  	  			
						  	  		}%>
											</SELECT></TD>
										</TR>
										<TR>
											<TD class="plainlabel">K??nh b??n h??ng</TD>
											<TD class="plainlabel"><SELECT name="kenhId"
												onchange="filterDvkd();">
													<OPTION value=""></OPTION>
													<% if(kenh!= null){						  	  			
						   					while (kenh.next()){
						  	  					if (kenh.getString("pk_seq").equals(nkmBean.getKenhId())){%>
													<OPTION value=<%= kenh.getString("pk_seq")%> selected><%= kenh.getString("diengiai")%></OPTION>
													<%}else{ %>
													<OPTION value=<%= kenh.getString("pk_seq")%>><%= kenh.getString("diengiai") %></OPTION>
													<%	 }
						  	  				
						  	  				}
						  	  			
						  	  		}%>
											</SELECT></TD>
										</TR>
										<TR>
											<TD class="plainlabel">Nh??n h??ng</TD>
											<TD class="plainlabel"><SELECT name="nhId"
												onchange="filterNh();">
													<OPTION value="0"></OPTION>
													<% if(nhList!= null){						  	  		
							   				while (nhList.next()){
						  	  					if (nhList.getString("pk_seq").equals(nhId)){%>
													<OPTION value='<%= nhList.getString("pk_seq")%>' selected><%= nhList.getString("ten") %></OPTION>
													<%}else{ %>
													<OPTION value='<%= nhList.getString("pk_seq")%>'><%= nhList.getString("ten") %></OPTION>
													<%	 }
						  	  				}}%>
									</SELECT>
								</TD>
							</TR>
										
							<TR>
						  	  	<TD class="plainlabel"><%=ChuyenNgu.get("Ng??nh h??ng", nnId)%></TD>
						  	  	<TD class="plainlabel"><SELECT name="nganhhang_fk" id="nganhhang_fk" onchange="filterNh();">
						  	  		<OPTION value="" ></OPTION>	
						  	  		<% if(nganhhang!= null){						  	  		
							   				while (nganhhang.next()){
						  	  					if (nkmBean.getNganhhang_fk() != null && 
						  	  							nganhhang.getString("pk_seq").equals(nkmBean.getNganhhang_fk())){%>
						  	  						<OPTION value='<%= nganhhang.getString("pk_seq")%>' selected ><%= nganhhang.getString("ten") %></OPTION>
						  	  					<%}else{ %>
						  	  						<OPTION value='<%= nganhhang.getString("pk_seq")%>'  ><%= nganhhang.getString("ten") %></OPTION>
						  	  		<%			 }
						  	  				}
						  	  		}%>
						  	  	</SELECT>
						  	  	</TD>
						  	</TR>
										
										<TR>
											<TD class="plainlabel">Ch???ng lo???i</TD>
											<TD class="plainlabel"><SELECT name="clId"
												onchange="filterCl();" multiple="multiple">
													<OPTION value="0"></OPTION>
													<% if(clList!= null){					  	  			
							   				while (clList.next()){
							   					if (!clId.equals("0") && clId.indexOf(clList.getString("pk_seq")) >= 0){%>
													<OPTION value="<%= clList.getString("pk_seq")%>" selected><%= clList.getString("ten") %></OPTION>
													<%}else{ %>
													<OPTION value="<%= clList.getString("pk_seq")%>"><%= clList.getString("ten") %></OPTION>
													<%				  }
						  	  			
						  	  				}
						  	  			
						  	  		}%>
											</SELECT></TD>
										</TR>
										<TR>


											<TD colspan="2" class="plainlabel"><label> <% if(nkmBean.getTrangthai().equals("1")){ %>
													<input name="trangthai" type="checkbox" value="1" checked>
													<%}else{ %> <input name="trangthai" type="checkbox" value="0">
													<%} %> Ho???t ?????ng</label>
											</TD>
										</TR>

									</TABLE>
									<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
										<TR class="tbheader">
											<TH width="28%">M?? s???n ph???m</TH>
											<TH width="60%">T??n s???n ph???m</TH>
											<TH width="12%">Ch???n <input type="checkbox" name="chon"
												onClick="checkedAll(document.nkmForm.sanpham)"></TH>
										</TR>

										<% ResultSet rs = null;
								   
								   String lightrow = "tblightrow";
								   String darkrow = "tbdarkrow";
								   int m = 0;
						   	       rs = spSelected;
									   
									   if (!(rs == null)){			
										    
									   		while (rs.next()){								   			
												if (m % 2 != 0) {%>
										<TR class=<%=lightrow%>>
											<%  } else {%>
										
										<TR class=<%= darkrow%>>
											<%  } %>
											<TD align="left" class="textfont"><%= rs.getString("ma") %></TD>
											<TD align="center"><div align="left"><%= rs.getString("ten")%></div>
											</TD>
											<TD align="center"><input type="checkbox" name="sanpham"
												value='<%= rs.getString("pk_seq") %>' checked></TD>

										</TR>

										<% 			m++;}
										}	
			
							   	       rs = spList;
									   
									   if (!(rs == null)){			
										    
									   		while (rs.next()){								   			
												if (m % 2 != 0) {%>
										<TR class=<%=lightrow%>>
											<%  } else {%>
										
										<TR class=<%= darkrow%>>
											<%  } %>
											<TD align="left" class="textfont"><%= rs.getString("ma") %></TD>
											<TD align="center"><div align="left"><%= rs.getString("ten")%></div>
											</TD>
											<TD align="center"><input type="checkbox" name="sanpham"
												value='<%= rs.getString("pk_seq") %>'></TD>

										</TR>

										<% 			m++;}
										}	
									   
									%>

									</TABLE>
								</FIELDSET></TD>
						</TR>
					</TABLE></TD>
			</TR>
		</TABLE>
	</form>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</HTML>
<%  
	if(spList != null) spList.close(); 
	if(spSelected != null) spSelected.close();
	if(dvkdList != null) dvkdList.close();
	if(nhList != null) nhList.close();
	if(clList != null) clList.close() ; %>
<%}%>