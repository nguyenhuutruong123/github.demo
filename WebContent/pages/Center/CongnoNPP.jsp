<%@page import="java.sql.ResultSet"%>
<%@page import="geso.dms.center.beans.congnonpp.ICongnonppList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "geso.dms.center.util.*" %><%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>

<% ICongnonppList obj = (ICongnonppList)session.getAttribute("obj"); %>
<% ResultSet ctList = obj.getCnList(); 

	int[] quyen = new  int[6];
	quyen = util.Getquyen("CongnoNPPSvl","", userId);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>DuongBienHoa - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">

<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>

<script type="text/javascript">
$( function() {
	//Created By: Brij Mohan
	//Website: http://techbrij.com
	function groupTable($rows, startIndex, total)
	{
		if (total === 0)
		{
			return;
		}
		var i , currentIndex = startIndex, count=1, lst=[];
		var tds = $rows.find('td:eq('+ currentIndex +')');
		var ctrl = $(tds[0]);
		lst.push($rows[0]);
		for (i=1;i<=tds.length;i++){
		if (ctrl.text() ==  $(tds[i]).text()){
		count++;
		$(tds[i]).addClass('deleted');
		lst.push($rows[i]);
		}
		else{
			if (count>1){
			ctrl.attr('rowspan',count);
			groupTable($(lst),startIndex+1,total-1);
			}
			count=1;
			lst = [];
			ctrl=$(tds[i]);
			lst.push($rows[i]);
		}
		}
	}
	var rowCount = $('#sku tr').length;

	groupTable($('#sku tr:has(td)'),0,rowCount);
	$('#sku .deleted').remove();
	});
    </script>

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


<script type="text/javascript" language="JavaScript" src="../scripts/simplecalendar.js"></script>
<SCRIPT language="JavaScript" type="text/javascript">
function submitform()
{
	 document.nkhForm.action.value = "new";
   	 document.forms["nkhForm"].submit();
}

function searchform()
{
	 document.nkhForm.action.value = "search";
   	 document.forms["nkhForm"].submit();
}

function excel()
{
	 document.nkhForm.action.value = "excel";
   	 document.forms["nkhForm"].submit();
}

function clearform()
{
	document.forms['nkhForm'].tungay.value= '';
	document.forms['nkhForm'].denngay.value= '';	
	document.forms['nkhForm'].trangthai.value= '';
  	document.forms["nkhForm"].submit();
}

function thongbao()
{var tt = document.forms['nkhForm'].msg.value;
	if(tt.length>0)
    alert(document.forms['nkhForm'].msg.value);
	}
</SCRIPT>

<link href="../css/select2.css" rel="stylesheet" />
 <script src="../scripts/select2.js"></script>
 <script>
    $(document).ready(function() { 
     $("select:not(.notuseselect2)").select2({ width: 'resolve' });     
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

<form name="nkhForm" method="post" action="../../CongnoNPPSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%=userId%>'>
<input type="hidden" name="action" value="1">
<input type="hidden" name="msg" value='<%=obj.getmsg() %>'>
<script language="javascript" type="text/javascript">
    thongbao();
</script> 

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
				   		<table width="100%" border="0" cellpadding="0" cellspacing="0">
					  		<tr height="22">
						  		<TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý bán hàng &gt; Công nợ nhà phân phối</TD> 
						  		<TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %>&nbsp;  </TD>
						  	</tr>
						</table></TD>
			  	</TR>
			</TABLE>
			
			<TABLE width="100%" border="0" cellpadding="0">
				<TR>
					<TD>
						<FIELDSET><LEGEND class="legendtitle">&nbsp;Tiêu chí tìm kiếm&nbsp;</LEGEND>
							<TABLE  width="100%" cellspacing="0" cellpadding="6">
								
								<TR>
									<TD width="10%" class="plainlabel">Từ ngày </TD>									
									<TD width="81%" class="plainlabel">
									 <input type="text"  class="days" size="11" onchange = "searchform();"
                   				     id="tungay" name="tungay" value="<%= obj.getTungay() %>" maxlength="10" readonly />
									</TD>																	   				
								</TR>
																								
								<TR>
									<TD width="10%" class="plainlabel">Đến ngày </TD>									
									<TD width="81%" class="plainlabel">
									 <input type="text"  class="days" size="11" onchange = "searchform();"
                   				     id="denngay" name="denngay" value="<%= obj.getDenngay() %>" maxlength="10" readonly />
									</TD>																	   				
								</TR>
								
								<TR>
									<TD class="plainlabel">Trạng thái </TD>
									<TD class="plainlabel">
										<select name="trangthai" onchange = "searchform();">
											<% if (obj.getTrangthai().equals("1")){%>
											  	<option value="1" selected>Đã chốt</option>
											  	<option value="0">Chưa chốt</option>											  	
											  	<option value=""></option>
											  
											<%}else if(obj.getTrangthai().equals("0")) {%>
											 	<option value="0" selected>Chưa chốt</option>
											  	<option value="1" >Đã chốt</option>											 	
											  	<option value="" ></option>											  																						  	  	
											<%}else{%>
												<option value="" selected> </option>
												<option value="0" >Chưa chốt</option>
											  	<option value="1" >Đã chốt</option>																						  	
											<%} %>
								         </select>
									</TD>
							    </TR>									
								</TABLE>
								
								<TABLE  width="100%" cellspacing="0" cellpadding="6">																							 																						
									<TR>
										<!-- <TD class="plainlabel" width="12%"> -->
										<TD class="plainlabel" width="1%">
										<a class="button2" href="javascript:clearform()">
										<img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;	
			                            </TD>	
			                            
			                            <TD class="plainlabel" width="16%">	
			                            <%if(quyen[0]!= 0){ %>						
										<a class="button2"	href="../../UploadCongnoNPPSvl?userId=<%=userId %>"> 
										<img style="top: -4px;" src="../images/button.png" alt="">Tạo mới</a>
										<%} %>
			                            </TD>           
			                            
			                            <!-- <TD class="plainlabel">							
										<a class="button2"	href="javascript:excel()"> 
										<img style="top: -4px;" src="../images/button.png" alt="">Danh sách chỉ tiêu</a>
			                            </TD> -->	                                             																	
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
					<LEGEND class="legendtitle">&nbsp;Công nợ nhà phân phối &nbsp;&nbsp;&nbsp;</LEGEND>
					<TABLE class="" width="100%">
						<TR>
							<TD width="98%">
							<TABLE ID="sku" width="100%" border="0" cellspacing="1" cellpadding="4">
								<TR class="tbheader">
									<TH width="9%">Diễn giải </TH>
									<TH width="9%">Thời gian </TH>
									<TH width="9%">Đơn vị kinh doanh </TH>
									<TH width="9%">Kênh bán hàng </TH>
									<TH width="9%">Người tạo </TH>
									<TH width="9%">Ngày tạo </TH>
									<TH width="9%">Người sửa </TH>
									<TH width="9%">Ngày sửa </TH>
									<TH width="15%">Trạng thái </TH>																
									<TH width="9%">Tác vụ</TH>
								</TR>
						<% 
							
							int m = 0;
							String lightrow = "tblightrow";
							String darkrow = "tbdarkrow";
							while (ctList.next()){
								if (m % 2 != 0) {%>						
									<TR class= <%=lightrow%> >
								<%} else {%>
									<TR class= <%= darkrow%> >
								<%} %>										
									<TD>
									<input type="hidden" value="<%=ctList.getString("pk_seq")%>">
									<%=ctList.getString("diengiai") %></TD>						
									<TD><%=ctList.getString("thoigian") %></TD>
									<TD><%=ctList.getString("DVKDTEN") %></TD>
									<TD><%=ctList.getString("KBHTEN") %></TD>
									<TD align="center"><%=ctList.getString("nguoitao") %></TD>																	
									<TD align="center"><%=ctList.getString("ngaytao") %></TD>
									<TD align="center"><%=ctList.getString("nguoisua") %></TD>																	
									<TD align="center"><%=ctList.getString("ngaysua") %></TD>
									<TD align="center">
									<%
									String trangthai = ctList.getString("trangthai");
									if (trangthai.equals("0")){ %>
										<span> Chưa chốt</span>
									<%}else if(trangthai.equals("1")){ %>
										<span><b> Đã chốt</b></span>									
									<% } %>
									</TD>									
									<TD align="center">
									<TABLE border = 0 cellpadding="0" cellspacing="0" >
									<TR>
									<TD>
										<%if (trangthai.equals("0")){ %>
											 <%if(quyen[2]!= 0){ %>		                     	
						                     	<A href = "../../CongnoNPPUpdateSvl?userId=<%=userId%>&edit=<%=ctList.getString("pk_seq")%>"> <IMG src="../images/Edit.png" alt="Sửa" title="Sửa" border=0 width=20  height=20></A>&nbsp;	                     		                     	                     	
						                     <%} %>
						                     <%if(quyen[1]!= 0){ %>	
						                     	<A href = "../../CongnoNPPSvl?userId=<%=userId%>&delete=<%=ctList.getString("pk_seq")%>"> <img src="../images/Delete20.png" alt="Xóa" title="Xóa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn có muốn xóa mục này không ?')) return false;"></A>                      	                       
						                     <%} %>
						                     <%if(quyen[4]!= 0){ %>	
						                     	<A href = "../../CongnoNPPSvl?userId=<%=userId%>&finish=<%=ctList.getString("pk_seq")%>"> <img src="../images/Chot.png" alt="Chốt" title="Chốt" width="20" height="20" longdesc="Chot" border=0 onclick="if(!confirm('Bạn có muốn chốt mục này không ?')) return false;"></A>
						                   	<%} %>
					                    <%}else { %>
					                    	<%if(quyen[3]!= 0){ %>		                    	                     	
					                     	<A href="../../CongnoNPPUpdateSvl?userId=<%=userId%>&display=<%=ctList.getString("pk_seq")%>"> <IMG src="../images/Display20.png" alt="Hiển thị" title="HienThi" border=0 width=20  height=20></A>&nbsp;	                     	
					                     <%}} %>	
																		
									</TD>
									</TR>
									</TABLE>
							<%m++; }%>
							
								<TR>
									<TD align="center" colspan="12" class="tbfooter">&nbsp;</TD>
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
try{
	if(ctList!=null){ ctList.close(); ctList = null; }
	if(obj!=null)
	{
		obj.DBclose();
		obj=null;
	}
	session.setAttribute("obj",null);
}catch(Exception er){	}
}%>