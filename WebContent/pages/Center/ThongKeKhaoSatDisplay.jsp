<%@page import="java.util.Hashtable"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.List" %>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@page import="java.sql.SQLException" %>
<%@ page import = "geso.dms.center.beans.khaosat.*" %>
<%@ page import = "geso.dms.center.util.*" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/SalesUpERP/index.jsp");
	}else{ %>

<%
 	IThongkekhaosat obj =(IThongkekhaosat)session.getAttribute("csxBean");
	Hashtable<String, String> cauhoi_noidung = (Hashtable<String, String>)obj.getNoidungcauhoi();
	Hashtable<String, String> cauhoi_traloi = (Hashtable<String, String>)obj.getNoidungcautraloi();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">

<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<script type="text/javascript"	src="../scripts/jquery.min.1.7.js"></script>
<script type="text/javascript" language="JavaScript" src="../scripts/jquery.tools.min.js"></script>

<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />

<script type="text/javascript" src="../scripts/ajax.js"></script>
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs.css">
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs-panes.css">


<SCRIPT language="JavaScript" type="text/javascript">
	
	
	function keypress(e) //Hàm dùng d? ngan ngu?i dùng nh?p các ký t? khác ký t? s? vào TextBox
	{    
		var keypressed = null;
		if (window.event)
			keypressed = window.event.keyCode; //IE
		else
			keypressed = e.which; //NON-IE, Standard
		
		if (keypressed < 48 || keypressed > 57)
		{ 
			if (keypressed == 8 || keypressed == 127 || keypressed == 37 || keypressed == 39 || keypressed == 0 || keypressed == 46)
			{//Phím Delete và Phím Back
				return;
			}
			return false;
		}
	}
	
	
	
	function ChangeLoaiCauHoi(cau)
	{
		var loaicauhoi = document.getElementById(cau + "_loaicauhoi").value;
		if(loaicauhoi == "1")  //Mot lua chon
		{
			document.getElementById(cau + "_motluachon").style.display = "";
			document.getElementById(cau + "_nhieuluachon").style.display = "none";
		}
		else
		{
			if(loaicauhoi == "2")
			{
				document.getElementById(cau + "_motluachon").style.display = "none";
				document.getElementById(cau + "_nhieuluachon").style.display = "";
			}
			else
			{
				document.getElementById(cau + "_motluachon").style.display = "none";
				document.getElementById(cau + "_nhieuluachon").style.display = "none";
			}
		}
	}
	
</SCRIPT>

<script>
//perform JavaScript after the document is scriptable.
$(function() {
 // setup ul.tabs to work as tabs for each div directly under div.panes
 	$("ul.tabs").tabs("div.panes > div");
});
</script>


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
<input type="hidden" name="userId" value='<%= userId %>' >
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
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý khảo sát > Chức năng > Thống kê khảo sát > Xem kết quả</TD> 
							 <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%= userTen %></TD></tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
			<TR ><TD >
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR class = "tbdarkrow">
							<TD width="30" align="left"><A href="../../ThongkekhaosatSvl?userId=<%= userId%>&khaosatId=<%= obj.getId()  %>" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
						    <TD width="2" align="left" ></TD>
						    <TD width="30" align="left" >
						    </TD>
							<TD >&nbsp; </TD>						
						</TR>
					</TABLE>
			</TD></TR>
		</TABLE>
			<TABLE width="100%" border="0" cellpadding="0"  cellspacing="1" >
			  	<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Thông báo </LEGEND>
				
	    				<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold"  style="width: 100% ; color:#F00 ; font-weight:bold" readonly="readonly" rows="1"><%= obj.getMsg() %></textarea>
						</FIELDSET>
				   </TD>
				</tr>			

				<TR>
				  <TD height="100%" width="100%">
						<FIELDSET >
						<LEGEND class="legendtitle" style="color:black">Thông tin khảo sát</LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
                          <TR>
                          		<TD class="plainlabel" valign="middle" width="120px" >Tiêu đề </TD>   
		                        <TD class="plainlabel" valign="middle" width="220px" >
		                        	<input type="text" name="tieude" value="<%= obj.getTieude() %>" readonly="readonly" > 
		                        </TD>          
                          		<TD class="plainlabel" valign="middle" width="120px" >Diễn giải </TD>   
		                        <TD class="plainlabel" valign="middle" colspan="3" >
		                        	<input type="text" name="diengiai" value="<%= obj.getDiengiai() %>" readonly="readonly" > 
		                        </TD>          
		                  </TR> 
		                  <TR>
                          		<TD class="plainlabel" valign="middle" >Bộ phận </TD>   
		                        <TD class="plainlabel" valign="middle" >
		                            <select name="bophan" disabled="disabled" >
		                            	<option value="" ></option>
		                            	<% if(obj.getBophan().equals("R&D")){ %>
		                            		<option value="R&D" selected="selected" >R&D</option>
		                            	<% } else { %> 
		                            		<option value="R&D" >R&D</option>
		                            	<%  } %>
		                            	<% if(obj.getBophan().equals("CRM")){ %>
		                            		<option value="CRM" selected="selected" >CRM</option>
		                            	<% } else { %> 
		                            		<option value="CRM" >CRM</option>
		                            	<%  } %>
		                            	<% if(obj.getBophan().equals("Mar res")){ %>
		                            		<option value="Mar res" selected="selected" >Mar res</option>
		                            	<% } else { %> 
		                            		<option value="Mar res" >Mar res</option>
		                            	<%  } %>
		                            </select>
		                        </TD>                
		                  
                          		<TD class="plainlabel" valign="middle" >Số câu hỏi </TD>   
		                        <TD class="plainlabel" valign="middle" colspan="3" >
		                        	<input type="text" name="socauhoi" value="<%= obj.getSocauhoi() %>" style="text-align: right;"  onkeypress="return keypress(this);" readonly="readonly" > 
		                        </TD>          
		                  </TR> 
		                  
		                  <TR>
                          		<TD class="plainlabel" valign="middle" >Tên người trả lời </TD>   
		                        <TD class="plainlabel" valign="middle"  >
		                        	<input type="text"  value="<%= obj.getTennguoiks()%>" readonly="readonly" > 
		                        </TD>          
                          		<TD class="plainlabel" valign="middle"  >Điện thoại </TD>   
		                        <TD class="plainlabel" valign="middle" width="220px" >
		                        	<input type="text"  value="<%= obj.getSodienthoai() %>" readonly="readonly" > 
		                        </TD>          
		                        
                          		<TD class="plainlabel" valign="middle" width="120px" >Địa chỉ </TD>   
		                        <TD class="plainlabel" valign="middle"  >
		                        	<input type="text"  value="<%= obj.getDiachi() %>" readonly="readonly" > 
		                        </TD>          
                          		        
		                  </TR> 
		                  
		                  <TR>
                          		<TD class="plainlabel" valign="middle" >Đối tượng </TD>   
		                        <TD class="plainlabel" valign="middle"  >
		                        	<input type="text"  value="<%= obj.getDoituong() %>" readonly="readonly" > 
		                        </TD>          
                          		<TD class="plainlabel" valign="middle"  >Mục tiêu </TD>   
		                        <TD class="plainlabel" valign="middle"  >
		                        	<input type="text"  value="<%= obj.getMuctieu() %>" readonly="readonly" > 
		                        </TD>          
		                  
                          		<TD class="plainlabel" valign="middle" >Thu nhập </TD>   
		                        <TD class="plainlabel" valign="middle"  >
		                        	<input type="text"  value="<%= obj.getThunhap() %>" readonly="readonly" > 
		                        </TD>                
		                  </TR> 
		                  
		                  <TR>
		                  		<TD class="plainlabel" valign="middle"  >Giới tính </TD>   
		                        <TD class="plainlabel" valign="middle"  >
		                        	<input type="text"  value="<%= obj.getGioitinh() %>" readonly="readonly" > 
		                        </TD>   
		                        
		                        <TD class="plainlabel" valign="middle"  >Tuổi </TD>   
		                        <TD class="plainlabel" valign="middle" colspan="3" >
		                        	<input type="text"  value="<%= obj.getTuoi() %>" readonly="readonly" > 
		                        </TD>         
		                  </TR> 
		                  
		                  <% if(obj.getSocauhoi().trim().length() > 0) { %>
		                  
		                  <TR>
		                  	<TD colspan="6" >
		                  		<ul class="tabs">
		                  			<% for(int i = 0; i < Integer.parseInt(obj.getSocauhoi()); i++) { %>
		                  				<li><a href="#">Câu <%= i + 1 %></a></li>
		                  			<% } %>
		                  		</ul>
		                  		
		                  		<div class="panes">
		                  		
		                  			<% for(int i = 0; i < Integer.parseInt(obj.getSocauhoi()); i++) { 
		                  				
		                  					String noidung = cauhoi_noidung.get("cau" + ( i + 1 ) );
		                  					String traloi = cauhoi_traloi.get("cau" + ( i + 1 ) );
		                  					
		                  					System.out.println("Noi dung lay duoc: " + noidung);
		                  					System.out.println("Noi dung tra loi: " + traloi);
		                  					
		                  					String loaisauhoi = "";
		                  					String cauhoi = "";
		                  					String huongdantraloi = "";
		                  					String dapan = "";
		                  					
		                  					if(noidung != null)
		                  					{
		                  						if(noidung.contains(",,"))
		                  						{
		                  							String[] ndArr = noidung.split(",,");
		                  							loaisauhoi = ndArr[0].trim();
		                  							cauhoi = ndArr[1].trim();
		                  							huongdantraloi = ndArr[2].trim();
		                  							dapan = ndArr[3].trim();
		                  						}
		                  					}
		                  					
		                  					String[] dapanArr = null;
		                  					if(dapan.trim().length() > 0)
		                  						dapanArr = dapan.split("__");
		                  					
		                  					String[] traloiArr = null;
		                  					if( traloi != null && traloi.trim().length() > 0 && !loaisauhoi.equals("0") )
		                  					{
		                  						traloiArr = traloi.split("__");
		                  						//System.out.println("-----Tra loi: " + traloiArr[4]);
		                  					}
		                  				
		                  				%>
		                  			
			                  			<div style="font-size: 12px;" >
			                  				<table style="width: 100%" cellspacing="10" >
			                  					<tr>
			                  						<td width="120px" ><span style="font-size: 12px;" >Loại câu hỏi</span></td>
			                  						<td>
			                  							<select id="cau<%= i + 1 %>_loaicauhoi" name="cau<%= i + 1 %>_loaicauhoi" disabled="disabled"  >
			                  								
			                  								<option value="0" selected="selected" >Văn bản</option>
			                  								
			                  								<% if(loaisauhoi.equals("1")) { %> 
			                  									<option value="1" selected="selected" >Một lựa chọn</option>
			                  								<% } else { %> 
			                  									<option value="1">Một lựa chọn</option>
			                  								<% } %>
			                  								
			                  								<% if(loaisauhoi.equals("2")) { %> 
			                  									<option value="2" selected="selected" >Nhiều lựa chọn</option>
			                  								<% } else { %> 
			                  									<option value="2">Nhiều lựa chọn</option>
			                  								<% } %>
			                  								
			                  							</select>
			                  						</td>
			                  					</tr>
			                  					<tr>
			                  						<td ><span style="font-size: 12px;" >Câu hỏi</span></td>
			                  						<td>
			                  							<input type="text" style="width: 600px" name="cau<%= i + 1 %>_cauhoi"  value="<%= cauhoi %>" readonly="readonly" >
			                  						</td>
			                  					</tr>
			                  					<tr>
			                  						<td ><span style="font-size: 12px;" >Hướng dẫn trả lời</span></td>
			                  						<td>
			                  							<input type="text" style="width: 600px" name="cau<%= i + 1 %>_huongdantraloi"  value="<%= huongdantraloi %>" readonly="readonly"  >
			                  						</td>
			                  					</tr>
			                  					<tr>
			                  						<td valign="top" ><span style="font-size: 12px;" >Đáp án</span></td>
			                  						<td valign="top" style="font-size: 11px;" >
			                  							
			                  							<input type="text" style="width: 600px; <%= loaisauhoi.equals("0") ? "" : "display: none;" %> "  value="<%= traloi %>" readonly="readonly" >
			                  							
			                  							<table style="width: 100%; <%= loaisauhoi.equals("1") ? "" : "display: none;" %> " id="cau<%= i + 1 %>_motluachon" >
			                  								<tr>
			                  									<td width="100px" ><span style="font-size: 12px;" >
			                  										<input type="radio"  <%= loaisauhoi.equals("0") ? "" :  (traloiArr[0].equals("1") ? "checked='checked'" :"" ) %>  >&nbsp;Lựa chọn 1</span>
			                  									</td>
			                  									<td><input type="text" style="width: 485px" name="cau<%= i + 1 %>_motluachon"  value="<%= ( dapanArr == null ?  "" : dapanArr[0].trim() ) %>" readonly="readonly" ></td>
			                  								</tr>
			                  								<tr>
			                  									<td width="100px" ><span style="font-size: 12px;" >
			                  										<input type="radio"  <%= loaisauhoi.equals("0") ? "" :  (traloiArr[1].equals("1") ? "checked='checked'" :"" ) %> >&nbsp;Lựa chọn 2</span>
			                  									</td>
			                  									<td><input type="text" style="width: 485px" name="cau<%= i + 1 %>_motluachon" value="<%= ( dapanArr == null ?  "" : dapanArr[1].trim() ) %>"  readonly="readonly" ></td>
			                  								</tr>
			                  								<tr>
			                  									<td width="100px" ><span style="font-size: 12px;" >
			                  										<input type="radio"  <%= loaisauhoi.equals("0") ? "" :  (traloiArr[2].equals("1") ? "checked='checked'" :"" ) %> >&nbsp;Lựa chọn 3</span>
			                  									</td>
			                  									<td><input type="text" style="width: 485px" name="cau<%= i + 1 %>_motluachon" value="<%= ( dapanArr == null ?  "" : dapanArr[2].trim() ) %>" readonly="readonly"  ></td>
			                  								</tr>
			                  								<tr>
			                  									<td width="100px" ><span style="font-size: 12px;" >
			                  										<input type="radio"  <%= loaisauhoi.equals("0") ? "" :  (traloiArr[3].equals("1") ? "checked='checked'" : "" ) %> >&nbsp;Lựa chọn 4</span>
			                  									</td>
			                  									<td><input type="text" style="width: 485px" name="cau<%= i + 1 %>_motluachon" value="<%= ( dapanArr == null ?  "" : dapanArr[3].trim() ) %>" readonly="readonly" ></td>
			                  								</tr>
			                  								<tr>
			                  									<td width="100px" ><span style="font-size: 12px;" >
			                  										<input type="radio"  <%= loaisauhoi.equals("0") ? "" :  (traloiArr[4].equals("1") ? "checked='checked'" :"" ) %> >&nbsp;Lựa chọn 5</span>
			                  									</td>
			                  									<td><input type="text" style="width: 485px" name="cau<%= i + 1 %>_motluachon" value="<%= ( dapanArr == null ?  "" : dapanArr[4].trim() ) %>" readonly="readonly" ></td>
			                  								</tr>
			                  							</table>
			                  							
			                  							<table style="width: 100%; <%= loaisauhoi.equals("2") ? "" : "display: none;" %>" id="cau<%= i + 1 %>_nhieuluachon" >
			                  								<tr>
			                  									<td width="100px" ><span style="font-size: 12px;" >
			                  										<input type="checkbox" <%= loaisauhoi.equals("0") ? "" :  (traloiArr[0].equals("1") ? "checked='checked'" :"" ) %> >&nbsp;Lựa chọn 1</span>
			                  									</td>
			                  									<td><input type="text" style="width: 485px" name="cau<%= i + 1 %>_nhieuluachon"  value="<%= ( dapanArr == null ?  "" : dapanArr[0].trim() ) %>" readonly="readonly" ></td>
			                  								</tr>
			                  								<tr>
			                  									<td width="100px" ><span style="font-size: 12px;" >
			                  										<input type="checkbox" <%= loaisauhoi.equals("0") ? "" :  (traloiArr[1].equals("1") ? "checked='checked'" :"" ) %> >&nbsp;Lựa chọn 2</span>
			                  									</td>
			                  									<td><input type="text" style="width: 485px" name="cau<%= i + 1 %>_nhieuluachon" value="<%= ( dapanArr == null ?  "" : dapanArr[1].trim() ) %>" readonly="readonly" ></td>
			                  								</tr>
			                  								<tr>
			                  									<td width="100px" ><span style="font-size: 12px;" >
			                  										<input type="checkbox" <%= loaisauhoi.equals("0") ? "" :  (traloiArr[2].equals("1") ? "checked='checked'" :"" ) %> >&nbsp;Lựa chọn 3</span>
			                  									</td>
			                  									<td><input type="text" style="width: 485px" name="cau<%= i + 1 %>_nhieuluachon" value="<%= ( dapanArr == null ?  "" : dapanArr[2].trim() ) %>" readonly="readonly"  ></td>
			                  								</tr>
			                  								<tr>
			                  									<td width="100px" ><span style="font-size: 12px;" >
			                  										<input type="checkbox" <%= loaisauhoi.equals("0") ? "" :  (traloiArr[3].equals("1") ? "checked='checked'" :"" ) %> >&nbsp;Lựa chọn 4</span>
			                  									</td>
			                  									<td><input type="text" style="width: 485px" name="cau<%= i + 1 %>_nhieuluachon" value="<%= ( dapanArr == null ?  "" : dapanArr[3].trim() ) %>" readonly="readonly"  ></td>
			                  								</tr>
			                  								<tr>
			                  									<td width="100px" ><span style="font-size: 12px;" >
			                  										<input type="checkbox" <%= loaisauhoi.equals("0") ? "" :  (traloiArr[4].equals("1") ? "checked='checked'" :"" ) %> >&nbsp;Lựa chọn 5</span>
			                  									</td>
			                  									<td><input type="text" style="width: 485px" name="cau<%= i + 1 %>_nhieuluachon" value="<%= ( dapanArr == null ?  "" : dapanArr[4].trim() ) %>" readonly="readonly" ></td>
			                  								</tr>
			                  							</table>
			                  						
			                  						</td>
			                  					</tr>
			                  				</table>
			                  			</div>
		                  			
		                  			<% } %>
		                  			
		                  		</div>
		                  		
		                  	</TD>
		                  </TR>
		                  
		                  <% } %>
		                  
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
<%}%>
