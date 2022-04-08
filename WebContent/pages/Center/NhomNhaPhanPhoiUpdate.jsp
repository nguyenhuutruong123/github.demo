<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.beans.nhomnhaphanphoi.imp.NhomNhaPhanPhoi" %>
<%@ page  import = "geso.dms.center.beans.nhomnhaphanphoi.INhomNhaPhanPhoi" %>
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
		response.sendRedirect("/Vifon/index.jsp");
	}else{ %>

<% INhomNhaPhanPhoi nnppBean = (INhomNhaPhanPhoi)session.getAttribute("nnppBean"); %>
<% 	
			
ResultSet vungRs=(ResultSet) nnppBean.getVungRs();
ResultSet kvRs=(ResultSet) nnppBean.getKvRs();
ResultSet kenhRs=(ResultSet) nnppBean.getKenhRs();
ResultSet nhomRs=(ResultSet) nnppBean.getNhomRs();
ResultSet nppRs=(ResultSet) nnppBean.getNppRs();
ResultSet tinhRs=(ResultSet) nnppBean.getTinhRs();
ResultSet quanhuyeRs=(ResultSet) nnppBean.getQuanhuyenRs();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<TITLE>Vifon - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print"
	href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">

<script type="text/javascript" src="../scripts/jquery-1.js"></script>
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />

<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js"type="text/javascript"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<style type="text/css" >
    .black_overlay{
        display: none;
        position: absolute;
        top: 0%;
        left: 0%;
        width: 100%;
        height: 900%;
        background-color: black;
        z-index:1001;
        -moz-opacity: 0.8;
        opacity:.80;
        filter: alpha(opacity=80);
    }
    .white_content {
        display: none;
        position: absolute;
        top: 25%;
        left: 25%;
        width: 50%;
        height: 50%;
        padding: 16px;
        /*border: 10px solid #CCC;
        background-color: white;*/
        z-index:1002;
        overflow: auto;
    }
 
 </style>
<script>
$(document).ready(function()
{
	$("#kenhId").select2();
	$("#vungId").select2();
	$("#kvId").select2();	
	$("#tinhId").select2();
	$("#quanhuyenId").select2();
});
</script>



<SCRIPT language="JavaScript" type="text/javascript">
function submitform()
{
    document.forms["nspForm"].submit();
}

function save()
{
	document.nspForm.action.value="save";
    document.forms["nspForm"].submit();       
}


function checkedAll(chk) {
	for(i=0; i<chk.length; i++){
	 	if(document.nspForm.chon.checked==true){
			chk[i].checked = true;
		}else{
			chk[i].checked = false;
		}
	 }
}
function ShowPopup() {
    document.getElementById('light').style.display = 'block';
    document.getElementById('fade').style.display = 'block'
}
function HidePopup() {
    document.getElementById('light').style.display = 'none';
    document.getElementById('fade').style.display = 'none'
}

function ajaxOption(id)
{
	var str = '';
	var kenhIds = document.getElementById("kenhId");
	for(var i = 0; i < kenhIds.options.length ; i++)
	{
		if(kenhIds.options[i].selected)
			str += kenhIds.options[i].value + ',';
	}
	
	var str2 = '';
	var vungIds =document.getElementById("vungId");
	for(var j = 0; j < vungIds.options.length ; j++)
	{
		if(vungIds.options[j].selected)
			str2 += vungIds.options[j].value + ',';
	}
	
	var str3='';
	var kvIds = document.getElementById("kvId");
	for(var j = 0; j < kvIds.options.length ; j++)
	{
		if(kvIds.options[j].selected)
			str3 += kvIds.options[j].value + ',';
	}
	
	var str4='';
	var tinhIds =document.getElementById("tinhId");
	for(var j = 0; j < tinhIds.options.length ; j++)
	{
		if(tinhIds.options[j].selected)
			str4 += tinhIds.options[j].value + ',';
	}
	
	var str5='';
	/* var qhIds =  document.getElementById("quanhuyenId");
	for(var j = 0; j < qhIds.options.length ; j++)
	{
		if(qhIds.options[j].selected)
			str5 += qhIds.options[j].value + ',';
	} */
	var xmlhttp;
	
	if (window.XMLHttpRequest)
	{// code for IE7+, Firefox, Chrome, Opera, Safari
	   xmlhttp = new XMLHttpRequest();
	}
	else
	{// code for IE6, IE5
	   xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlhttp.onreadystatechange=function()
	{
		
		
	   if (xmlhttp.readyState == 4 && xmlhttp.status == 200)
	   {
		   HidePopup();
	      document.getElementById(id).innerHTML = xmlhttp.responseText;
	   }
	}
	ShowPopup();
	xmlhttp.open("GET","../../AjaxNhomNpp?id=" + id + "&kenhId=" + str + "&vungId=" + str2 + "&kvId=" + str3 + "&tinhId=" + str4 + "&qhId=" + str5 + "&nhomId=<%=nnppBean.getId()%>",true);
	xmlhttp.send();
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

<% Csrf csdr=new Csrf(request,response,true,false,true);%>
<input type="hidden" name="userId" value='<%=userId%>'>
<input type="hidden" name="action" value="0">
<input type="hidden" name="id" value="<%=nnppBean.getId()%>">
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							<TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền &gt; Kinh doanh  &gt; Nhóm nhà phân phối > Cập nhật</TD> 
							 <TD colspan="2" align="right" class="tbnavigation">Chào mừng Bạn <%=userTen %>&nbsp;  </TD></tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
			<TR ><TD >
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR class = "tbdarkrow">
							<TD width="30" align="left"><A href="../../NhomNhaPhanPhoiSvl?userId=<%=userId %>" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
						    <TD width="2" align="left" ></TD>
						    <TD width="30" align="left" ><A href="javascript: save();" ><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A></TD>
							<TD >&nbsp; </TD>						
						</TR>
					</TABLE>
			</TD></TR>
		</TABLE>
			<TABLE width="100%" border="0" cellpadding="0"  cellspacing="1" >
			  	<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Báo lỗi nhập liệu</LEGEND>
				
	    				<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold"  style="width: 100% ; color:#F00 ; font-weight:bold" readonly="readonly" rows="1"><%=nnppBean.getMsg()%></textarea>
						<% nnppBean.getMsg(); %>
						</FIELDSET>
				   </TD>
				</tr>			

				<TR>
				  <TD height="100%" width="100%">
						<FIELDSET >
						<LEGEND class="legendtitle" style="color:black">Thông tin nhóm sản phẩm </LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
							<TR>
								<TD width="12%" class="plainlabel" >Mã nhóm<FONT class="erroralert"> </FONT></TD>
								<TD class="plainlabel">
                                <INPUT type="text" name="ma" style="width:300px" value='<%= nnppBean.getMa() %>'></TD>
							</TR>
							<TR>
							  	<TD class="plainlabel">Tên nhóm</TD>
						  	  	<TD class="plainlabel"><INPUT type="text" name="ten" style="width:300px" value='<%= nnppBean.getTen() %>'>
                                </TD>
						  </TR>
							<TR  style="display:none;">
							  <TD class="plainlabel">Loại nhóm</TD>
							  <TD width="88%" class="plainlabel">
                              <SELECT name="loainhom" onChange="filter();">
                              <% if (nnppBean.getLoainhom().equals("1")){ %>                                
                                	<OPTION value="1" selected >Nhóm nhà phân phối</OPTION>
                                	<OPTION value="2" >Nhà phân phối </OPTION>
                              <%}else{ %>
                                	<OPTION value="1" >Nhóm nhà phân phối</OPTION>
                                	<OPTION value="2" selected>Nhà phân phối</OPTION>
							  <%} %>                              
                              
                              </SELECT></TD>
						  </TR>
						  
						  <% if (nnppBean.getLoainhom().equals("2")){%>
						  <TR>
						  	  	<TD class="plainlabel">Kênh bán hàng</TD>
						  	  	<TD class="plainlabel">
						  	  		<SELECT name="kenhId"  id="kenhId"  onChange = "ajaxOption('tbNpp')"  style="width:150px" multiple>
						  	  		<OPTION value="0" ></OPTION>	
						  	  		<% if(kenhRs!= null){						  	  			
						   					while (kenhRs.next()){
						  	  					if (kenhRs.getString("pk_seq").equals(nnppBean.getKenhId()  )){%>
						  	  						<OPTION value=<%= kenhRs.getString("pk_seq")%> selected><%= kenhRs.getString("diengiai")%></OPTION>
						  	  					<%}else{ %>
						  	  						<OPTION value=<%= kenhRs.getString("pk_seq")%> ><%= kenhRs.getString("diengiai") %></OPTION>
						  	  	<%				  }
						  	  				
						  	  				}
						  	  			
						  	  		}%>						  	  			
						  	  	</SELECT>
						  	  	</TD>
						  		<TR>
						  
						  					  	  		
						  		<TR>
						  	  	<TD class="plainlabel">Khu vực</TD>
						  	  	<TD class="plainlabel">
						  	  		<SELECT name="kvId" id="kvId"   onChange = "ajaxOption('tbNpp')"  style="width:150px" multiple>
						  	  		<OPTION value="0" ></OPTION>	
						  	  		<% if(kvRs!= null){						  	  			
						   					while (kvRs.next()){
						  	  					if (kvRs.getString("pk_seq").equals(nnppBean.getKvId())){%>
						  	  						<OPTION value=<%= kvRs.getString("pk_seq")%> selected><%= kvRs.getString("diengiai")%></OPTION>
						  	  					<%}else{ %>
						  	  						<OPTION value=<%= kvRs.getString("pk_seq")%> ><%= kvRs.getString("diengiai") %></OPTION>
						  	  	<%				  }
						  	  				
						  	  				}
						  	  			
						  	  		}%>						  	  			
						  	  	</SELECT>
						  	  	</TD>
						  		<TR>
						  								  		
						  		<TR>
						  	  	<TD class="plainlabel">Vùng</TD>
						  	  	<TD class="plainlabel">
						  	  	<SELECT name="vungId" id="vungId"  id="vungId" onChange = "ajaxOption('tbNpp')" style="width:150px" multiple>	
						  	  		<OPTION value="0" ></OPTION>
						  	  		<% if(vungRs!= null){					  	  			
							   				while (vungRs.next()){
							   					
						  	  					if (vungRs.getString("pk_seq").equals(nnppBean.getVungId())){%>
						  	  						<OPTION value="<%= vungRs.getString("pk_seq")%>" selected><%= vungRs.getString("ten") %></OPTION>
						  	  					<%}else{ %>
						  	  						<OPTION value="<%= vungRs.getString("pk_seq")%>" ><%= vungRs.getString("ten") %></OPTION>
						  	  	<%				  }
						  	  			
						  	  				}
						  	  			
						  	  		}%>
						  	  	</SELECT>
						  	  	</TD>						  	 
						  		<TR>					
						  		
						  		<TR>
						  	  	<TD class="plainlabel">Tình thành</TD>
						  	  	<TD class="plainlabel">
						  	  	<SELECT name="tinhId"  id="tinhId" onChange = "ajaxOption('tbNpp')" style="width:150px" multiple>	
						  	  		<OPTION value="0" ></OPTION>
						  	  		<% if(tinhRs!= null){					  	  			
							   				while (tinhRs.next()){
							   					
						  	  					if (tinhRs.getString("pk_seq").equals(nnppBean.getTinhId()  )  ){%>
						  	  						<OPTION value="<%= tinhRs.getString("pk_seq")%>" selected><%= tinhRs.getString("ten") %></OPTION>
						  	  					<%}else{ %>
						  	  						<OPTION value="<%= tinhRs.getString("pk_seq")%>" ><%= tinhRs.getString("ten") %></OPTION>
						  	  	<%				  }
						  	  			
						  	  				}
						  	  			
						  	  		}%>
						  	  	</SELECT>
						  	  	</TD>						  	 
						  		<TR>
						  			  		
<%-- 						  		<TR>
						  	  	<TD class="plainlabel">Quận huyện</TD>
						  	  	<TD class="plainlabel">
						  	  		<SELECT name="quanhuyenId"  id="quanhuyenId" onChange = "ajaxOption('tbNpp')" style="width:150px" multiple>	
						  	  		<OPTION value="0" ></OPTION>
						  	  		<% if(quanhuyeRs!= null){					  	  			
							   				while (quanhuyeRs.next()){
							   					
						  	  					if (quanhuyeRs.getString("pk_seq").equals(nnppBean.getQuanhuyenId()  )){%>
						  	  						<OPTION value="<%= quanhuyeRs.getString("pk_seq")%>" selected><%= quanhuyeRs.getString("ten") %></OPTION>
						  	  					<%}else{ %>
						  	  						<OPTION value="<%= quanhuyeRs.getString("pk_seq")%>" ><%= quanhuyeRs.getString("ten") %></OPTION>
						  	  	<%				  }
						  	  			
						  	  				}
						  	  			
						  	  		}%>
						  	  	</SELECT>
						  	  	</TD>						  	 
						  		<TR> --%>
						  			  		
						  			  		
						  	<%	}%>
							
						  </TR>
						  
						</TABLE>
						<div id="tbNpp">
						<TABLE width="100%" border="0" cellspacing="1" cellpadding="4" >							
								<TR class="tbheader">
								<% if (nnppBean.getLoainhom().equals("1")){ %>
									<TH width="28%">Mã nhóm</TH>
									<TH width="60%">Tên nhóm</TH>
								<%}else{ %>
									<TH width="10%">Kênh bán hàng</TH>
									<TH width="10%">Vùng</TH>
									<TH width="10%">Khu vực</TH>
									<TH width="10%">Tỉnh thành</TH>
									<!-- <TH width="10%">Quận huyện</TH> -->
									<TH width="10%">Mã nhà phân phối</TH>
									<TH width="20%">Nhà phân phối</TH>
									<TH width="30%">Địa chỉ</TH>
								<%}%>								
									<TH width="15%">Chọn
								<%	if(nnppBean.getLoainhom().equals("1")){%>			
										<input type="checkbox" name="chon" onClick="checkedAll(document.nspForm.nhomId)">
								<%}else{ %>
										<input type="checkbox" name="chon" onClick="checkedAll(document.nspForm.nppId)">								
								<%} %>
									
									</TH>
								</TR>

								<% ResultSet rs = null;
								   
								   String lightrow = "tblightrow";
								   String darkrow = "tbdarkrow";
								   int m = 0;
							   if (nnppBean.getLoainhom().equals("1")){

								   rs = nppRs;
								   
								   if (!(rs == null)){			
									    
								   		while (rs.next()){								   			
											if (m % 2 != 0) {%>						
												<TR class= <%=lightrow%> >
										<%  } else {%>
												<TR class= <%= darkrow%> >
										<%  } %>	
																												
												
												<TD align="left" class="textfont"><%= rs.getString("ma") %></TD>
												<TD align="center"><div align="left"><%= rs.getString("ten") %></div></TD>
												<TD align="center">
												<% if (rs.getString("NhomNPP_FK").indexOf(nnppBean.getNhomId())>=0  ) {%>
														<input type="checkbox" name="nhomId" value='<%= rs.getString("pk_seq") %>' checked>
												<%}else{ %>
														<input type="checkbox" name="nhomId" value='<%= rs.getString("pk_seq") %>'>
												<%} %>
												</TD>
											
												</TR>
												
							<% 			m++;}
									}	
									
								}else{
							   	       rs = nppRs;
									   if (!(rs == null)){			
										    
									   		while (rs.next()){								   			
												if (m % 2 != 0) {%>						
													<TR class= <%=lightrow%> >
											<%  } else {%>
													<TR class= <%= darkrow%> >
											<%  } %>	
												<TD align="left" class="textfont"><%= rs.getString("kenh") %></TD>
												<TD align="left" class="textfont"><%= rs.getString("vung") %></TD>		
												<TD align="left" class="textfont"><%= rs.getString("khuvuc") %></TD>
												<TD align="left" class="textfont"><%= rs.getString("tinh") %></TD>
												<%-- <TD align="left" class="textfont"><%= rs.getString("quanhuyen") %></TD> --%>
												<TD align="left" class="textfont"><%= rs.getString("ma") %></TD>
												<TD align="center"><div align="left"><%= rs.getString("ten") %></div></TD>
												<TD align="center"><div align="left"><%= rs.getString("diachi") %></div></TD>
													<TD align="center">
													<% if (nnppBean.getNppId().indexOf( rs.getString("pk_seq") )>=0  ) {%>
														<input type="checkbox" name="nppId" value='<%= rs.getString("pk_seq") %>' checked>
													<%}else{ %>
														<input type="checkbox" name="nppId" value='<%= rs.getString("pk_seq") %>'>
													<%} %>								
													</TD>

													</TR>
													
								<% 			m++;}
										}}	
							   	      %>
						</TABLE>			
						</div>				
						</FIELDSET>						
					</TD>
				</TR>
			</TABLE>
		</TD>
	</TR>
	</TABLE>
</form>
<div id="light" class="white_content">
    <img alt="" src="../images/loading02.gif" width="150px" height="150px" >
</div>
<div id="fade" class="black_overlay"></div>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</HTML>
<% 
	if(vungRs != null) vungRs.close() ; %>
<%}%>