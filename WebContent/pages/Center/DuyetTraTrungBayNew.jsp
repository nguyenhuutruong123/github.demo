<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.beans.duyettratrungbay.IDuyettratrungbay" %>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "java.text.NumberFormat" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "geso.dms.center.util.ChuyenNgu" %>
<%@ page import = "com.google.gson.JsonArray" %>
<%@ page import = "com.google.gson.JsonObject" %>
<%@ page import = "com.google.gson.JsonParser" %>


<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	//if(!util.check(userId, userTen, sum)){
		//response.sendRedirect("/SOHACO/index.jsp");
	//}else{

 IDuyettratrungbay dttbBean = (IDuyettratrungbay)session.getAttribute("dttbBean"); %>
<% ResultSet scheme = (ResultSet)dttbBean.getSchemeRS() ; %>
<% ResultSet khRs = (ResultSet)dttbBean.getKhRs() ; %>
<% Hashtable<String, Integer> tratb = (Hashtable<String, Integer>)dttbBean.getTraTb(); %>
<% NumberFormat formatter = new DecimalFormat("#,###,###.##"); %>
<% String nnId = (String)session.getAttribute("nnId");

	boolean edit = dttbBean.getId().trim().length() > 0;

%> 
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
<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<SCRIPT language="JavaScript" type="text/javascript">
function submitform()
{
	document.forms['dttbForm'].setAttribute('enctype', '', 0);
    document.forms['dttbForm'].submit();
}

function save2()
{
	
	document.dttbForm.action.value='save';
	document.forms['dttbForm'].setAttribute('enctype', '', 0);
    document.forms['dttbForm'].submit();
}

function aptrungbay()
{
	document.dttbForm.action.value='aptrungbay';
	document.forms['dttbForm'].setAttribute('enctype', '', 0);
    document.forms['dttbForm'].submit();
}

function excel()
{

	/* var l = new Loading();
	l.render();
	l.show();
	l.hide_with_download('DuyettratrungbayNewSvl'); */
	document.dttbForm.action.value='excel';
	document.forms['dttbForm'].setAttribute('enctype', '', 0);
    document.forms['dttbForm'].submit();
}


function chot()
{
	document.dttbForm.action.value='chot';
	document.forms['dttbForm'].setAttribute('enctype', '', 0);
    document.forms['dttbForm'].submit();
}

function upload()
{
	document.forms['dttbForm'].setAttribute('enctype', "multipart/form-data", 0);
    document.forms['dttbForm'].submit();
}

function duyetAnh(khId, loai, khMa, divId,dvtrangthaiAnhId)
{	
	
	
	var duyetId = '<%=dttbBean.getId()  %>';
	var e =  document.getElementById(divId);
	
	 
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
	         
	         
	         if(xmlhttp.responseText !='')
	         {
	        	 alert(xmlhttp.responseText);
	        	 
			 }
	         else
	         { 
	        	 if(loai == 1)
	        	 	document.getElementById(dvtrangthaiAnhId).innerHTML = "<%=ChuyenNgu.get("Đã duyệt",nnId) %>";	
	        	 else if(loai == 2)
	        		 document.getElementById(dvtrangthaiAnhId).innerHTML = "<%=ChuyenNgu.get("Không duyệt",nnId) %>";
	        	 e.style.display = "none";
	        	 alert('Thành công!');
	     		
	         }
	      } 
	   }
	   xmlhttp.open("POST","../../DuyetAnhTrungBayAjaxSvl?khId="+khId+"&duyetId=" + duyetId+"&loai=" + loai,true);
	   xmlhttp.send();

	
	
	
}

</SCRIPT>


<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />

<!-- Khai bao su dung colorbox ajax -->
<link media="screen" rel="stylesheet" href="../css/colorbox.css">
<script src="../scripts/colorBox/jquery.colorbox.js"></script>


</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name ="dttbForm" ENCTYPE="multipart/form-data" method="post" action="../../DuyettratrungbayNewSvl" >
<input type="hidden" name="action" value="0">
<input type="hidden" name="id" value="<%=dttbBean.getId()%>">

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;<%= " " + url + " > " + (dttbBean.getId().trim().length() > 0 ? ChuyenNgu.get("Cập nhật",nnId) :ChuyenNgu.get("Tạo mới",nnId) )   %> </TD> 
							 <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %>&nbsp;  </TD></tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
			<TR ><TD >
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR class = "tbdarkrow">
							<TD width="2">&nbsp; </TD> 
	 						<TD><A href="../../DuyettratrungbaySvl?userId=<%=userId %>"  >
		 						<img src="../images/Back.png" alt="Back"  title="Back" border="1" longdesc="Back" style="border-style:outset"></A>
		 					<%  if(dttbBean.getDaduyet().equals("0") ) { %>
									<A href="javascript:save2();"  >
										<img src="../images/Save30.png" alt="Luu lai"  title="Luu lai" border="1" longdesc="Luu lai" style="border-style:outset">
									</A>
								<% } %>
							</TD>
					
						</TR>
					</TABLE>
			</TD></TR>
		</TABLE>
			<TABLE width="100%" border="0" cellpadding="0"  cellspacing="1" >
			  	<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>
				
	    				<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold"  style="width:100%" rows="1"><%= dttbBean.getMessage() %></textarea>

						</FIELDSET>
				   </TD>
				</tr>			

				<TR>
				  <TD height="100%" width="100%">
						<FIELDSET >
						<LEGEND class="legendtitle" style="color:black">Duyệt trả trưng bày</LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
						  <TR>
							  	<TD width="15%" class="plainlabel"><%=ChuyenNgu.get("Chương trình",nnId) %></TD>
						  	  	<TD class="plainlabel">
						  	  		<%if(edit) { %>
														<input type="hidden" name="cttbId" value="<%=dttbBean.getSchemeId()%>">
													
													<%} %>
						  	  	
									<SELECT  <%=!edit ? "name=\"cttbId\""  : ""   %> <%=edit ? "disabled"  : ""   %>      >
								    	<option value=""></option>
								      <% try{while(scheme.next()){ 
								    	if(scheme.getString("pk_seq").equals(dttbBean.getSchemeId())){ %>
								      		<option value='<%=scheme.getString("pk_seq")%>' selected><%=scheme.getString("scheme") + "_" + scheme.getString("diengiai") %></option>
								      	<%}else{ %>
								     		<option value='<%=scheme.getString("pk_seq")%>'><%=scheme.getString("scheme") + "_" + scheme.getString("diengiai") %></option>
								     	<%}}}catch(java.sql.SQLException e){} %>	  
                       				</SELECT>						  	  	
						  	  	
						  	  	</TD>
						  </TR>
						  
						  <TR>
							  	<TD class="plainlabel"><%=ChuyenNgu.get("Diễn giải",nnId) %></TD>
						  	  	<TD class="plainlabel">
						  	  		<input type="text" name = "diengiai" value = "<%=dttbBean.getDiengiai()%>">
						  	  	</TD>
						</TR>
						
<%if(dttbBean.getId().trim().length() > 0) { %>

						   <%  if(dttbBean.getDaduyet().equals("0") ) { %>
								  <TR>	
		 						  <TD class="plainlabel" colspan="2"><a class="button2" href="javascript:aptrungbay();">
									<img style="top: -4px;" src="../images/button.png" alt=""><%=ChuyenNgu.get("Áp trưng bày",nnId) %></a>&nbsp;&nbsp;&nbsp;&nbsp;
		 						  </TD>
								 </TR>
						  <%} %>

							 <TR>	
		 						  <TD class="plainlabel" colspan="2"><a class="button2" href="javascript:excel();">
									<img style="top: -4px;" src="../images/button.png" alt=""><%=ChuyenNgu.get("Excel",nnId) %></a>&nbsp;&nbsp;&nbsp;&nbsp;
		 						  </TD>
								 </TR>
						  <TR>
							  	<TD class="plainlabel"><%=ChuyenNgu.get("Chọn tập tin",nnId) %></TD>
						  	  	<TD class="plainlabel"><INPUT type="file" name="filename" size="40" value=''>
						  	  	&nbsp;&nbsp;&nbsp;&nbsp;
						  	  	
						  	  	<a class="button2" href="javascript:upload();">
							<img style="top: -4px;" src="../images/button.png" alt=""><%=ChuyenNgu.get("Upload",nnId) %></a>
						  	  	
						  	  	</TD>
						  </TR>
						  
						 
						  
						  <%-- <TR>
						  							  	
 						   <TD><a class="button2" href="javascript:upload();">
							<img style="top: -4px;" src="../images/button.png" alt=""><%=ChuyenNgu.get("Upload",nnId) %></a>&nbsp;&nbsp;&nbsp;&nbsp;
 						  </TD>
						  </TR> --%>
						  
 						  
<%} %>
						</TABLE>
						</FIELDSET>
					</TD>
				</TR>
				
				<%if(dttbBean.getId().trim().length() > 0) { %>
				<TR>
				  <TD height="100%" width="100%">
						<FIELDSET >
						<LEGEND class="legendtitle" style="color:black">Khách hàng </LEGEND>
						<TABLE border="0" width="100%" cellpadding="5" cellspacing="1">
							<tr class="tbheader">
								<TH width="5%" ><%=ChuyenNgu.get("STT",nnId) %></TH>
								<TH width="5%" ><%=ChuyenNgu.get("Mã NVBH",nnId) %> </TH>
								<TH width="10%" ><%=ChuyenNgu.get("TÊN NVBH",nnId) %> </TH>
							  	<TH width="5%" ><%=ChuyenNgu.get("Mã KH",nnId) %> </TH>
							  	<TH width="15%" ><%=ChuyenNgu.get("Tên khách hàng",nnId) %></TH>
							  	<TH width="15%" ><%=ChuyenNgu.get("Nhà phân phối",nnId) %></TH>
							  	<TH width="10%" ><%=ChuyenNgu.get("Duyệt đăng ký",nnId) %></TH>
							  	<TH width="10%" ><%=ChuyenNgu.get("Doanh số",nnId) %></TH>
							  	<TH width="7%" ><%=ChuyenNgu.get("Đạt",nnId) %></TH>
							  	<TH width="7%" ><%=ChuyenNgu.get("Duyệt trả",nnId) %></TH>
							  	<TH width="7%" ><%=ChuyenNgu.get("Trạng thái ảnh",nnId) %></TH>
							  	<TH width="7%" ><%=ChuyenNgu.get("Hình ảnh",nnId) %></TH>
							  	
						  </tr>
						  <% try{
							    String lightrow = "tblightrow";
								String darkrow = "tbdarkrow";
								int m = 0;
								String[] arrTrangthaiAnh = { ChuyenNgu.get("Chưa duyệt",nnId),ChuyenNgu.get("Đã duyệt",nnId),ChuyenNgu.get("Không duyệt",nnId) };
								
								if(khRs != null){
									while(khRs.next()){ 
									
										String nut = "#nutmopopup" + Integer.toString(m + 1);
										String nut2 = "#nutmopopup" + Integer.toString(m + 2);
										String popup = "#noidungpopup"	+ Integer.toString(m + 1);
										
										String popup2 = "#noidungpopup"+ Integer.toString(m + 2);
										
										String nut_id = "nutmopopup" + Integer.toString(m + 1);
										String nut_id2 = "nutmopopup" + Integer.toString(m + 2);
										String popup_id = "noidungpopup"+ Integer.toString(m + 1);
										String popup_id2 = "noidungpopup"+ Integer.toString(m + 2);

										
										int duyetAnh = khRs.getInt("duyetAnh");
										System.out.println("dasdasd"+duyetAnh);
										String trangthaiAnh = "";
										if(duyetAnh >=0 && duyetAnh <= arrTrangthaiAnh.length)
											trangthaiAnh = arrTrangthaiAnh[duyetAnh];
										
										String anh_dang_ky = khRs.getString("AnhDK") != null ? khRs.getString("AnhDK") : "";
										String thoidiemAnhDK = khRs.getString("thoidiemAnhDK");
										
										if (m % 2 != 0) {%>						
										<TR class= <%=lightrow%> >
									<%} else {%>
										<TR class= <%= darkrow%> >
									<%}%>
											<TD align="center"><%=m+1%></TD>      
											
											
											<TD align="left"><%=khRs.getString("DDKDMA") %></TD>
											<TD align="left"><%=khRs.getString("DDKDTEN") %></TD>
											<TD align="center">
											<input type="hidden" name = "mafast" value="<%=khRs.getString("MAFAST")%>">
												<%= khRs.getString("MAFAST")%></TD>                                   
											<TD align="left"><%=khRs.getString("khTen") %></TD>  
											<TD align="left"><%=khRs.getString("NPP") %></TD>  		
											
											<TD align="center"><%=khRs.getString("SUATDUYETDK") %></TD>			
											<TD align="center"><%= formatter.format(khRs.getDouble("doanhso")) %></TD>
											<TD align="center"><%=khRs.getString("XuatDeNghi") %></TD>
																	                               
							  				<TD align="center">
							  					<input type="hidden" name = "tt_<%=khRs.getString("MAFAST")%>" value="<%=khRs.getString("SOXUAT")%>">
							  					<%=formatter.format(khRs.getDouble("SOXUAT")) %>
							  				</TD>
							  			
							  				<TD align="center"><div id="divTrangThaiAnh_<%=khRs.getString("khId") %>" ><%=trangthaiAnh %></div> </TD>
							  				<TD align="center">
							  					<%if(anh_dang_ky  != null) { %>
													<TABLE border = 0 cellpadding="0" cellspacing="0">
														<TR>
														<TD>																
																<!-- Noi nay chua anh va nut DONG -->
																<a id="<%=nut_id%>"   href="#">
																	<img src="../images/Display20.png" width="20" height="20" alt="Xem" ; >
																	
																	<script>
																		$(document).ready(function()
																		{
																			$("<%=nut%>").colorbox({width:"100%", inline:true, href:"<%=popup%>", top: "50"});        	
																		});
																		
																		
																		$(document).ready(function(){
																		
																			$("<%=nut%>").colorbox({rel:'group4', slideshow:false});
																		});
																		
																		
																		
																		
																	 </script>
																</a>
																
																<!-- Khung Colorbox tam thoi an di -->
																<div style='display:none'>
																	<div id="<%=popup_id%>" style='padding:0px 5px; background:#fff;'>
																		<table   cellpadding="4px" cellspacing="2px" width="100%"  align="center" >
																			
																			<%
																			JsonArray jsonArray = null;
																			if( khRs.getString("hinhanh")  != null )
																				jsonArray = (JsonArray) new JsonParser().parse(khRs.getString("hinhanh"));
																		 
																			int conspanTD  = jsonArray != null && jsonArray.size() > 0 ? jsonArray.size() + 1 : 1;
																			%>
																			
																			<tr >
																				<TD align="center" colspan="<%=conspanTD  %>"> Mã: <%=khRs.getString("MAFAST")%> - Tên: <%=khRs.getString("khTen")%></TD>
																	
																			</tr>
																			<%if(duyetAnh == 0){ %>
																			<tr >
																				<TD align="center" colspan=<%=conspanTD%> >
																					<div align="center" id ="divDuyet_<%=khRs.getString("khId") %>" >
																						<A href = "javascript:duyetAnh('<%=khRs.getString("khId") %>', 1, '<%=khRs.getString("MAFAST")%>','divDuyet_<%=khRs.getString("khId") %>','divTrangThaiAnh_<%=khRs.getString("khId") %>')"><img src="../images/Chot.png" alt="Duyệt" title="Duyệt" width="20" height="20" longdesc="Duyệt" border=0 onclick="if(!confirm('Bạn muốn duyệt ảnh KH này?')) return false;"></A>
																						<A href = "javascript:duyetAnh('<%=khRs.getString("khId") %>', 2, '<%=khRs.getString("MAFAST")%>','divDuyet_<%=khRs.getString("khId") %>','divTrangThaiAnh_<%=khRs.getString("khId") %>')"><img src="../images/Delete20.png" alt="Xóa" title="Không duyệt" width="20" height="20" longdesc="Xóa" border=0 onclick="if(!confirm('Bạn Có Muốn không Duyệt ảnh KH Này?')) return false;"></A>									
																					</div>
																				</TD> 
																			</tr>
																			<%} %>
																			
																			
																			<tr >
																			
																					<td >
																					<img style="top: -4px; max-width: 300px; max-height: 300px;" src="<%= getServletContext().getInitParameter("urlAnhPDA")  +"cttrungbay/"+ anh_dang_ky %>" >																																							
																					</td>
																					<%
																					if(jsonArray != null)
																					for(int xxx = 0 ; xxx < jsonArray.size(); xxx++)
																					{ 
																						JsonObject rec = (JsonObject) jsonArray.get(xxx);
												                    					String imagePath= rec.get("imagePath").getAsString();
																						%>
																						<td >
																						<img style="top: -4px; max-width: 300px; max-height: 300px;" src="<%= getServletContext().getInitParameter("urlAnhPDA")  +"cttrungbay/"+ imagePath %>" >																																							
																						</td>
																					<%} %>
																			<tr >
																			<tr>
																				
																					<td >
																						<a target='_blank'  class="button" href="<%= getServletContext().getInitParameter("urlAnhPDA")  +"cttrungbay/"+ anh_dang_ky %>"> 
																					Ảnh đăng ký: <%=thoidiemAnhDK %>	
																						</a>																																						
																					</td>
																					<%
																					if(jsonArray != null)
																					for(int xxx = 0 ; xxx < jsonArray.size(); xxx++)
																					{ 
																						JsonObject rec = (JsonObject) jsonArray.get(xxx);
												                    					String imagePath= rec.get("imagePath").getAsString();
												                    					String thoidiem= rec.get("thoidiem").getAsString();
																						%>
																						<td >																																																										
																							<a target='_blank'  class="button" href="<%= getServletContext().getInitParameter("urlAnhPDA")  +"cttrungbay/"+ imagePath %>"> 
																								Ảnh trưng bày: <%=thoidiem %>	
																							</a>
																						
																						</td>
																					<%} %>
																			</tr>
																			
																			
																		</table>
																	</div>
																</div>
																
														</TD>
														</TR>
							  							</TABLE>
							  							<%} %>				  			
							  						</TD>
										</TR>
						  		<% m++ ;} 
						  		
						  		}%>		
						  		
						  <%}catch(java.sql.SQLException e){}%>
							<tr class="tbfooter"><td colspan="12" > &nbsp; </td></tr>
						</TABLE>
						</FIELDSET>
					</TD>
				
				
				</TR>
				<%} %>
			</TABLE>
		</TD>
		</TR>
		</TABLE>
		</form>
		</body>   
		<!-- <script type='text/javascript' src='../scripts/loadingv2.js'></script> --> 
		</HTML>
