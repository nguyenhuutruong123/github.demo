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
<%@ page  import = "geso.dms.center.util.ChuyenNgu" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>

<%
 	IKhaosat obj =(IKhaosat)session.getAttribute("csxBean");
	Hashtable<String, String> cauhoi_noidung = (Hashtable<String, String>)obj.getNoidungcauhoi();
	ResultSet nvList = obj.getDdkdRs();
	ResultSet kvList = obj.getKhuvucRs();
	ResultSet vList = obj.getVungRs();
%>
<% String nnId = (String)session.getAttribute("nnId"); %> 
<% if(nnId == null) {
	nnId = "vi"; 
	
	}	
String url = util.getChuyenNguUrl("KhaosatSvl","",nnId);	
	%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
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
 <link type="text/css" rel="stylesheet" href="../css/mybutton.css">
 <script type="text/javascript" src="../scripts/jquery.min.js"></script>
<!--   <script type="text/javascript" src="..scripts/jquery-1.js"></script> -->


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
	    document.forms["khtt"].submit();
	}
	
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
	
	function save()
	{
		
		<%if(obj.getLoaict().equals("1")){ %>
		var tungay = document.getElementById("tungay").value;
		var denngay = document.getElementById("denngay").value;
		if(tungay == '' || denngay == '')
		{
			alert('Vui lòng chọn thời gian hiệu lực');
			return;
		}
		<%}%>	
	 document.forms["khtt"].action.value = "save";  
	  /* document.forms['khtt'].action.value = 'save';   */
	  document.forms["khtt"].submit(); 
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
	
	function sellectAll(id1, id2)
	 {
		 var chkAll = document.getElementById(id1);
		 var spIds = document.getElementsByName(id2);
		 
		 if(chkAll.checked)
		 {
			 for(i = 0; i < spIds.length; i++)
			 {
				 spIds.item(i).checked = true;
			 }
		 }
		 else
		 {
			 for(i = 0; i < spIds.length; i++)
			 {
				 spIds.item(i).checked = false;
			 }
		 }
	 }
	
	function LocNhanVien()
	{
		document.forms["khtt"].action.value = "submit";
		document.forms["khtt"].submit(); 
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

<form name="khtt" method="post" action="../../KhaosatUpdateSvl">

<% Csrf csdr=new Csrf(request,response,true,false,true);%>
<input type="hidden" name="userId" value='<%= userId %>' >
<input type="hidden" name="action" id="action" value="0">
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;<%= " " + url %> > Tạo mới</TD> 
							 <TD colspan="2" align="right" class="tbnavigation"><%=ChuyenNgu.get("Chào mừng bạn",nnId) %> <%= userTen %></TD></tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
			<TR ><TD >
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR class = "tbdarkrow">
							<TD width="30" align="left"><A href="../../KhaosatSvl?userId=<%= userId%>" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
						    <TD width="2" align="left" ></TD>
						    <TD width="30" align="left" >
						    <div id="btnSave">
						    <A href="javascript:save();" ><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A>
						    </div>
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
						<LEGEND class="legendtitle"><%=ChuyenNgu.get("Thông báo",nnId) %> </LEGEND>
				
	    				<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold"  style="width: 100% ; color:#F00 ; font-weight:bold" readonly="readonly" rows="1"><%= obj.getMsg() %></textarea>
						</FIELDSET>
				   </TD>
				</tr>			

				<TR>
				  <TD height="100%" width="100%">
						<FIELDSET >
						<LEGEND class="legendtitle" style="color:black"><%=ChuyenNgu.get("Thông tin khảo sát",nnId) %></LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
                          <TR>
                          		<TD class="plainlabel" valign="middle" width="120px" ><%=ChuyenNgu.get("Tiêu đề",nnId) %> </TD>   
		                        <TD class="plainlabel" valign="middle"  >
		                        	<input type="text" name="tieude" value="<%= obj.getTieude() %>"  > 
		                        </TD>          
		                  </TR> 
                         
		                  
		                 
		                 <TR>
		                  		<TD class="plainlabel" valign="middle" width="120px" ><%=ChuyenNgu.get("Diễn giải",nnId) %> </TD>   
		                        <TD class="plainlabel" valign="middle"  >
		                        	<input type="text" name="diengiai" value="<%= obj.getDiengiai() %>"  > 
		                        </TD>   
		                  </TR>
		                  
		                  <TR>
                          		<TD class="plainlabel" valign="middle" ><%=ChuyenNgu.get("Đối tượng khảo sát",nnId) %> </TD>   
		                        <TD class="plainlabel" valign="middle" >
		                            <select name="doituong"  onChange="submitform();">
		                            	
		                            	<% if(obj.getDoituong().equals("KH")){ %>
		                            		<option value="KH" selected="selected" ><%=ChuyenNgu.get("Khách hàng",nnId) %></option>
		                            		<option value="TDV"  ><%=ChuyenNgu.get("Nhân viên bán hàng",nnId) %></option>
		                            	<% } else { %> 
		                            		<option value="KH" ><%=ChuyenNgu.get("Khách hàng",nnId) %></option>
		                            		<option value="TDV" selected="selected" ><%=ChuyenNgu.get("Nhân viên bán hàng",nnId) %></option>
		                            	<%  } %>
		                            </select>
		                        </TD>                
		                  </TR> 
		                  
		                 <% if(obj.getDoituong().equals("TDV")){ %>
                          <TR>
                          		<TD class="plainlabel" valign="middle" colspan="2"  >
                          		<% if(obj.getLoaict().equals("1")){ %>
									  	 					<INPUT TYPE="radio" NAME="loaict" value="0" onChange="submitform();"><%=ChuyenNgu.get("Câu hỏi Bình thường",nnId) %>
									  	 					<INPUT TYPE="radio" NAME="loaict" value="1" checked onChange="submitform();"><%=ChuyenNgu.get("Câu hỏi đầu ngày",nnId) %>
									  	 					
										  	 			<%}else{%>
									  	 					<INPUT TYPE="radio" NAME="loaict" value="0" checked onChange="submitform();"><%=ChuyenNgu.get("Câu hỏi Bình thường",nnId) %>
									  	 					<INPUT TYPE="radio" NAME="loaict" value="1" onChange="submitform();"><%=ChuyenNgu.get("Câu hỏi đầu ngày",nnId) %>	 			
									  	 		  <%}%>
                          		  </TD>         
		                  </TR> 
		                  <%}else {%>
		                  <TR>
                          		<TD class="plainlabel" valign="middle" colspan="2"  >
                          		<INPUT TYPE="radio" NAME="loaict" value="0" checked><%=ChuyenNgu.get("Câu hỏi Bình thường",nnId) %>
                          		  </TD>         
		                  </TR> 
		                  <%} %>
		                  
		                  <% if(obj.getDoituong().equals("TDV")){ %>
		                  	<TR>
									<TD class="plainlabel" ><%=ChuyenNgu.get("Từ ngày",nnId) %></TD>
									<td class="plainlabel">
			                            <input type="text"  class="days" size="11"
			                                    id="tungay" name="tungay" value="<%= obj.getTungay() %>" maxlength="10" readonly />
			                    	</td>
			                  </TR>
			                  <TR>
			                    	  <TD class="plainlabel" ><%=ChuyenNgu.get("Đến ngày",nnId) %></TD>
								      <td class="plainlabel">
				                            <input type="text"  class="days" size="11"
				                                    id="denngay" name="denngay" value="<%= obj.getDenngay() %>" maxlength="10" readonly />
				                    </td>
			                    	
								</TR>
		                   <%}%> 
		                  
		                  
		                  
		                  
		                  
		                  <TR>
                          		<TD class="plainlabel" valign="middle" ><%=ChuyenNgu.get("Bộ phận",nnId) %> </TD>   
		                        <TD class="plainlabel" valign="middle" >
		                            <select name="bophan" >
		                            	<option value="" ></option>
		                            	<% if(obj.getBophan().equals("R&amp;D")){ %> 
		                            		<option value="R&D" selected="selected" ><%=ChuyenNgu.get("R&D",nnId) %></option>
		                            	<% } else { %> 
		                            		<option value="R&D" ><%=ChuyenNgu.get("R&D",nnId) %></option>
		                            	<%  } %>
		                            	<% if(obj.getBophan().equals("CRM")){ %>
		                            		<option value="CRM" selected="selected" ><%=ChuyenNgu.get("CRM",nnId) %></option>
		                            	<% } else { %> 
		                            		<option value="CRM" ><%=ChuyenNgu.get("CRM",nnId) %></option>
		                            	<%  } %>
		                            	<% if(obj.getBophan().equals("Mar res")){ %>
		                            		<option value="Mar res" selected="selected" ><%=ChuyenNgu.get("Mar res",nnId) %></option>
		                            	<% } else { %> 
		                            		<option value="Mar res" ><%=ChuyenNgu.get("Mar res",nnId) %></option>
		                            	<%  } %>
		                            </select>
		                        </TD>                
		                  </TR> 
		                  <TR>
                          		<TD class="plainlabel" valign="middle" ><%=ChuyenNgu.get("Số câu hỏi",nnId) %> </TD>   
		                        <TD class="plainlabel" valign="middle"  >
		                        	<input type="text" name="socauhoi" value="<%= obj.getSocauhoi() %>" style="text-align: right;" onchange="submitform();" onkeypress="return keypress(this);" > 
		                        </TD>          
		                  </TR> 
		                  
		                  
		                  
		                  <TR>
		                  	<TD colspan="2" >
		                  		<ul class="tabs">
		                  			<%-- <li><a href="#">Nhân viên áp dụng</a></li>--%>
		                  		
		                  		<% if(obj.getSocauhoi().trim().length() > 0) { %>	
		                  			<% for(int i = 0; i < Integer.parseInt(obj.getSocauhoi()); i++) { %>
		                  				<li><a href="#">Câu <%= i + 1 %></a></li>
		                  			<% } %>
		                  		<% } %>
		                  			
		                  		</ul>
		                  		
		                  		<div class="panes">
		                  		
		                  		<%-- <div style="font-size: 1.0em; font-weight: 500" >
		                  				<TABLE width="100%" cellspacing="1" cellpadding="4">
		                  					<TR>
		                  						<td >Miền </td>
		                  						<td colspan="4" >
		                  							<select name="vungId" onchange="LocNhanVien();">
														 <option value = "" > </option>
							                             <%
							                             if (vList != null)
							                             {
							                            	 while (vList.next())
							                            	 { %>
							                       				<% if(vList.getString("pk_seq").equals(obj.getVungId())){ %>
							                       					<option value ="<%= vList.getString("pk_seq") %>" selected="selected"> <%= vList.getString("ten") %></option>
							                       				<%}else{ %>
							                       					<option value ="<%= vList.getString("pk_seq") %>"> <%= vList.getString("ten") %></option>
							                       				<%} %>
							                       			<% }
							                             }
							                             %>
						                             </select>  
						                             &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  
		                  						Vùng 
		                  							 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
		                  							<select name="khuvucId" onchange="LocNhanVien();">
														 <option value =""> </option>
							                             <%
							                             if (kvList!=null)
							                             {
							                            	 while (kvList.next())
							                            	 { %>
							                       				<% if(kvList.getString("pk_seq").equals(obj.getKhuvucId())){ %>
							                       					<option value ="<%=kvList.getString("pk_seq") %>" selected="selected"> <%=kvList.getString("ten") %></option>
							                       				<%}else{ %>
							                       					<option value ="<%=kvList.getString("pk_seq") %>"> <%=kvList.getString("ten") %></option>
							                       				<%} %>
							                       			<% }
							                             }
						                             	 %>
						                             </select>      
		                  						</td>
		                  					</TR>
		                  					
		                  					<TR>
		                  						<TH width="10%" align="center" class="plainlabel" >Mã nhân viên</TH>
		                  						<TH width="30%" align="center" class="plainlabel" >Tên nhân viên</TH>
		                  						<TH width="40%" align="center" class="plainlabel" >Địa chỉ</TH>
		                  						<TH width="10%" align="center" class="plainlabel" >Điện thoại</TH>
		                  						<TH width="10%" align="center" class="plainlabel" >Chọn hết <input type="checkbox" id='chkAll' onchange="sellectAll('chkAll', 'nvIds');"   > </TH>
		                  					</TR>
		                  					
		                  					<% if(nvList != null) 
		                  					{ 
		                  						int m = 0;
		                  						while(nvList.next()) 
		                  						{ 
		                  							%>
		                  							
		                  							<TR class="<%= m % 2 == 0 ? "tblightrow" : "tbdarkrow"  %>" >
		                  								<TD style="font-size: 1.0em" ><%= nvList.getString("MANHANVIEN") %></TD>
		                  								<TD><%= nvList.getString("ten") %></TD>
		                  								<TD><%= nvList.getString("diachi") %></TD>
		                  								<TD><%= nvList.getString("dienthoai") %></TD>
		                  								<TD align="center" >
		                  									<% if( obj.getDdkdId().indexOf(nvList.getString("pk_seq")) >= 0 ) { %>
		                  										<input type="checkbox" name="nvIds" value="<%= nvList.getString("pk_seq") %>" checked="checked"  >
		                  									<% } else { %> 
		                  										<input type="checkbox" name="nvIds" value="<%= nvList.getString("pk_seq") %>"   >
		                  									<% } %>
		                  								</TD>
		                  							</TR>
		                  							
		                  						<% m++; } 
		                  						nvList.close(); 
		                  					} %>
		                  					
		                  				</TABLE>
		                  			</div> --%>
		                  		
		                  		<% if(obj.getSocauhoi().trim().length() > 0) { %>	
		                  			<% for(int i = 0; i < Integer.parseInt(obj.getSocauhoi()); i++) { 
		                  				
		                  					String noidung = cauhoi_noidung.get("cau" + ( i + 1 ) );
		                  					System.out.println("Noi dung lay duoc: " + noidung);
		                  					
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
		                  					
		                  					if(dapanArr != null)
		                  					{
		                  						for(int j = 0; j < dapanArr.length; j++)
		                  						{
		                  							if(dapanArr[j].equals("NA"))
		                  								dapanArr[j] = "";
		                  						}
		                  					}
		                  				
		                  				%>
		                  			
			                  			<div style="font-size: 12px;" >
			                  				<table style="width: 100%" cellspacing="10" >
			                  					<tr>
			                  						<td width="120px" ><span style="font-size: 12px;" ><%=ChuyenNgu.get("Loại câu hỏi",nnId) %></span></td>
			                  						<td>
			                  							<select id="cau<%= i + 1 %>_loaicauhoi" name="cau<%= i + 1 %>_loaicauhoi"  onchange="ChangeLoaiCauHoi('cau<%= i + 1 %>');" >
			                  								
			                  								<option value="0" selected="selected" ><%=ChuyenNgu.get("Văn bản",nnId) %></option>
			                  								
			                  								<% if(loaisauhoi.equals("1")) { %> 
			                  									<option value="1" selected="selected" ><%=ChuyenNgu.get("Một lựa chọn",nnId) %></option>
			                  								<% } else { %> 
			                  									<option value="1"><%=ChuyenNgu.get("Một lựa chọn",nnId) %></option>
			                  								<% } %>
			                  								
			                  								<% if(loaisauhoi.equals("2")) { %> 
			                  									<option value="2" selected="selected" ><%=ChuyenNgu.get("Nhiều lựa chọn",nnId) %></option>
			                  								<% } else { %> 
			                  									<option value="2"><%=ChuyenNgu.get("Nhiều lựa chọn",nnId) %></option>
			                  								<% } %>
			                  								
			                  							</select>
			                  						</td>
			                  					</tr>
			                  					<tr>
			                  						<td ><span style="font-size: 12px;" ><%=ChuyenNgu.get("Câu hỏi",nnId) %></span></td>
			                  						<td>
			                  							<input type="text" style="width: 600px" name="cau<%= i + 1 %>_cauhoi"  value="<%= cauhoi %>"  >
			                  						</td>
			                  					</tr>
			                  					<tr>
			                  						<td ><span style="font-size: 12px;" ><%=ChuyenNgu.get("Hướng dẫn trả lời",nnId) %></span></td>
			                  						<td>
			                  							<input type="text" style="width: 600px" name="cau<%= i + 1 %>_huongdantraloi"  value="<%= huongdantraloi %>" >
			                  						</td>
			                  					</tr>
			                  					<tr>
			                  						<td valign="top" ><span style="font-size: 12px;" ><%=ChuyenNgu.get("Đáp án",nnId) %></span></td>
			                  						<td valign="top" style="font-size: 11px;" >
			                  							
			                  							<table style="width: 100%; <%= loaisauhoi.equals("1") ? "" : "display: none;" %> " id="cau<%= i + 1 %>_motluachon" >
			                  								<tr>
			                  									<td width="100px" ><span style="font-size: 12px;" >
			                  										<input type="radio" disabled="disabled" >&nbsp;<%=ChuyenNgu.get("Lựa chọn",nnId) %> 1</span>
			                  									</td>
			                  									<td><input type="text" style="width: 485px" name="cau<%= i + 1 %>_motluachon"  value="<%= ( dapanArr == null ?  "" : dapanArr[0].trim() ) %>"  ></td>
			                  								</tr>
			                  								<tr>
			                  									<td width="100px" ><span style="font-size: 12px;" >
			                  										<input type="radio" disabled="disabled" >&nbsp;<%=ChuyenNgu.get("Lựa chọn",nnId) %> 2</span>
			                  									</td>
			                  									<td><input type="text" style="width: 485px" name="cau<%= i + 1 %>_motluachon" value="<%= ( dapanArr == null ?  "" : dapanArr[1].trim() ) %>" ></td>
			                  								</tr>
			                  								<tr>
			                  									<td width="100px" ><span style="font-size: 12px;" >
			                  										<input type="radio" disabled="disabled" >&nbsp;<%=ChuyenNgu.get("Lựa chọn",nnId) %> 3</span>
			                  									</td>
			                  									<td><input type="text" style="width: 485px" name="cau<%= i + 1 %>_motluachon" value="<%= ( dapanArr == null ?  "" : dapanArr[2].trim() ) %>"  ></td>
			                  								</tr>
			                  								<tr>
			                  									<td width="100px" ><span style="font-size: 12px;" >
			                  										<input type="radio" disabled="disabled" >&nbsp;<%=ChuyenNgu.get("Lựa chọn",nnId) %> 4</span>
			                  									</td>
			                  									<td><input type="text" style="width: 485px" name="cau<%= i + 1 %>_motluachon" value="<%= ( dapanArr == null ?  "" : dapanArr[3].trim() ) %>"  ></td>
			                  								</tr>
			                  								<tr>
			                  									<td width="100px" ><span style="font-size: 12px;" >
			                  										<input type="radio" disabled="disabled" >&nbsp;Lựa chọn<%=ChuyenNgu.get("Tên",nnId) %> 5</span>
			                  									</td>
			                  									<td><input type="text" style="width: 485px" name="cau<%= i + 1 %>_motluachon" value="<%= ( dapanArr == null ?  "" : dapanArr[4].trim() ) %>"  ></td>
			                  								</tr>
			                  							</table>
			                  							
			                  							<table style="width: 100%; <%= loaisauhoi.equals("2") ? "" : "display: none;" %>" id="cau<%= i + 1 %>_nhieuluachon" >
			                  								<tr>
			                  									<td width="100px" ><span style="font-size: 12px;" >
			                  										<input type="checkbox" disabled="disabled" >&nbsp;<%=ChuyenNgu.get("Lựa chọn",nnId) %> 1</span>
			                  									</td>
			                  									<td><input type="text" style="width: 485px" name="cau<%= i + 1 %>_nhieuluachon"  value="<%= ( dapanArr == null ?  "" : dapanArr[0].trim() ) %>"  ></td>
			                  								</tr>
			                  								<tr>
			                  									<td width="100px" ><span style="font-size: 12px;" >
			                  										<input type="checkbox" disabled="disabled" >&nbsp;<%=ChuyenNgu.get("Lựa chọn",nnId) %> 2</span>
			                  									</td>
			                  									<td><input type="text" style="width: 485px" name="cau<%= i + 1 %>_nhieuluachon" value="<%= ( dapanArr == null ?  "" : dapanArr[1].trim() ) %>" ></td>
			                  								</tr>
			                  								<tr>
			                  									<td width="100px" ><span style="font-size: 12px;" >
			                  										<input type="checkbox" disabled="disabled" >&nbsp;<%=ChuyenNgu.get("Lựa chọn",nnId) %> 3</span>
			                  									</td>
			                  									<td><input type="text" style="width: 485px" name="cau<%= i + 1 %>_nhieuluachon" value="<%= ( dapanArr == null ?  "" : dapanArr[2].trim() ) %>"  ></td>
			                  								</tr>
			                  								<tr>
			                  									<td width="100px" ><span style="font-size: 12px;" >
			                  										<input type="checkbox" disabled="disabled" >&nbsp;<%=ChuyenNgu.get("Lựa chọn",nnId) %> 4</span>
			                  									</td>
			                  									<td><input type="text" style="width: 485px" name="cau<%= i + 1 %>_nhieuluachon" value="<%= ( dapanArr == null ?  "" : dapanArr[3].trim() ) %>"  ></td>
			                  								</tr>
			                  								<tr>
			                  									<td width="100px" ><span style="font-size: 12px;" >
			                  										<input type="checkbox" disabled="disabled" >&nbsp;<%=ChuyenNgu.get("Lựa chọn",nnId) %> 5</span>
			                  									</td>
			                  									<td><input type="text" style="width: 485px" name="cau<%= i + 1 %>_nhieuluachon" value="<%= ( dapanArr == null ?  "" : dapanArr[4].trim() ) %>"  ></td>
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
