<%@page import="java.sql.ResultSet"%>
<%@page import="geso.dms.center.beans.upload.IUpload"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.sql.Date"%>
<%@page import="geso.dms.center.util.Utility" %>
<%@ page  import = "geso.dms.center.util.ChuyenNgu" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen"); 	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>
<%
	IUpload obj = (IUpload) session.getAttribute("obj");
	//Utility util = new Utility();
	ResultSet npp=(ResultSet)obj.getRsNpp();
	ResultSet vungRs=(ResultSet)obj.getVungRs();
	ResultSet khuvuc=(ResultSet)obj.getKhuvucRs();
	ResultSet rsTonkho=(ResultSet)obj.getNppCoTonKhoRs();

	int[] quyen = new  int[6];
	quyen = util.Getquyen("ImportTonKhoSvl","&task="+obj.getTask()+"",userId);
	
%>
<% String nnId = (String)session.getAttribute("nnId"); %> 
<% if(nnId == null) {
 nnId = "vi"; 
 }
String url = util.getChuyenNguUrl("ImportTonKhoSvl","&task=2",nnId);	
 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<TITLE>DDT - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">


<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />

<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$(".days").datepicker({
			changeMonth : true,
			changeYear : true
		});
		$(".button").hover(function() {
			$(".button img").animate({
				top : "-10px"
			}, 200).animate({
				top : "-4px"
			}, 200) // first jump
			.animate({
				top : "-7px"
			}, 100).animate({
				top : "-4px"
			}, 100) // second jump
			.animate({
				top : "-6px"
			}, 100).animate({
				top : "-4px"
			}, 100); // the last jump
		});
	});
	$(document).ready(function() {
		$(".button1").hover(function() {
			$(".button1 img").animate({
				top : "-10px"
			}, 200).animate({
				top : "-4px"
			}, 200) // first jump
			.animate({
				top : "-7px"
			}, 100).animate({
				top : "-4px"
			}, 100) // second jump
			.animate({
				top : "-6px"
			}, 100).animate({
				top : "-4px"
			}, 100); // the last jump
		});
	});
</script>
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
$(document).ready(function()
{
	$("#nppMa").select2();
	$("#vungId").select2();
	$("#khuvucId").select2();
});
</script>



<script type="text/javascript" src="../scripts/report-js/action-report.js"></script>
<script language="javascript" type="text/javascript">
function upload()
{
 	document.forms['frm'].action.value="ImportTonKhoSvl";
	document.getElementById("btUpload").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Processing...</a>";
	document.forms['frm'].setAttribute('enctype', "multipart/form-data", 0);
    document.forms['frm'].submit();
}
function uploadSalesIn()
{
	document.forms['frm'].action.value="uploadSalesIn";
	document.getElementById("btUpload").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Processing...</a>";
	document.forms['frm'].setAttribute('enctype', "multipart/form-data", 0);
    document.forms['frm'].submit();
    
 	
}



function thongbao(){
	var tt = document.forms['frm'].msg.value;
	if(tt.length>0)
    alert(document.forms['frm'].msg.value);
}


function excel()
{
 	document.forms['frm'].action.value="excel";
    document.forms['frm'].submit();
}

function newform()
{   
	document.forms["frm"].action.value = "Tao moi";
	document.forms["frm"].submit();
}


</script>

<!-- BEGIN RENDER AUTO -->

<% if (!geso.dms.center.util.GlobalValue.dev_mode) { %>
	<script type="text/javascript" src="../scripts/disableF12.js"></script>
<% } %>

<!-- END RENDER AUTO -->
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<!-- BEGIN RENDER AUTO -->
<%if(!geso.dms.center.util.GlobalValue.dev_mode){ %>
	<noscript> 
	 	<meta http-equiv="refresh" content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")  %>">
		<!-- //window.location = "https://www.google.com"; -->
	</noscript>
<%} %>
<!-- END RENDER AUTO -->

	<form name="frm" method="post" action="../../ImportTonKhoSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
		<input type="hidden" name="userId" value='<%= userId %>'>
		<input type="hidden" name="msg" value='<%=obj.getMsg()%>'>
		<input type="hidden" name="task" value='<%=obj.getTask()%>'>
		<script language="javascript" type="text/javascript">
    		thongbao();
		</script>
		<input type="hidden" name="action" value='1'> <input type="hidden" name="userId" value='<%=obj.getUserId()%>'>
		<div id="main" style="width: 99%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 70%; padding: 5px; float: left" class="tbnavigation"><%=" "+url %></div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					<%=ChuyenNgu.get("Chào mừng bạn",nnId) %>
					<%=userTen%></div>
			</div>
			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"><%=ChuyenNgu.get("Báo lỗi nhập liệu",nnId) %> </legend>
					<textarea id="errors" name="errors" rows="1" style="width: 100%">
						<%=obj.getMsg()%></textarea>
				</fieldset>
			</div>
			<div align="left" style="width: 100%; float: none; clear: left; font-size: 0.7em">
				<fieldset>
					<legend class="legendtitle"><%=ChuyenNgu.get("Upload Tồn Kho",nnId) %></legend>
					<div style="width: 100%; float: none" align="left">
						<div style="width: 100%; float: none" align="left" class="plainlabel">
							<TABLE width="70%" cellpadding="6" cellspacing="0">
								<TR>
								<TD class="plainlabel"><%=ChuyenNgu.get("Miền",nnId) %></TD>
									<TD class="plainlabel">
										<select name="vungId" id="vungId" onchange="seach();" style="width:250px">
											<option value="" selected>All</option>
											<%
												if (vungRs != null)
													while (vungRs.next()) {
														if (vungRs.getString("vungId").equals(obj.getVungId()  )) {
											%>
											   <option value="<%=vungRs.getString("vungId")%>" selected><%=vungRs.getString("vungten")%></option>
											   <%
											   	} else {
											   %>
											   <option value="<%=vungRs.getString("vungId")%>"><%=vungRs.getString("vungten")%></option>
											<%
												}
													}
											%>
										</select>
										</TD>
									<TD class="plainlabel"><%=ChuyenNgu.get("Nhà phân phối",nnId) %></TD>
									<TD class="plainlabel"><select name="nppMa" id="nppMa" style="width:200px;" >
											<option value="" selected>All</option>
											<%
												if (npp != null)
													while (npp.next()) {
														if (npp.getString("nppMa").equals(obj.getNppId()  )) {
											%>
											   <option value="<%=npp.getString("nppMa")%>" selected><%=npp.getString("nppMa") +"--"+npp.getString("nppTen")%></option>
											   <%
											   	} else {
											   %>
											   <option value="<%=npp.getString("nppMa")%>"><%=npp.getString("nppMa") +"--"+npp.getString("nppTen")%></option>
											<%
												}
													}
											%>
									</select></TD>
									</TR>
								<TR>									
									<TD class="plainlabel"><%=ChuyenNgu.get("Khu Vực",nnId) %></TD>
									<TD class="plainlabel">
									<select name="khuvucId" id="khuvucId" onchange="seach();" style="width:250px">
											<option value="" selected>All</option>
											<%
												if (khuvuc != null)
													while (khuvuc.next()) {
														if (khuvuc.getString("kvId").equals(obj.getKhuvucId()   )) {
											%>
											   <option value="<%=khuvuc.getString("kvId")%>" selected><%=khuvuc.getString("kvTen")%></option>
											   <%
											   	} else {
											   %>
											   <option value="<%=khuvuc.getString("kvId")%>"><%=khuvuc.getString("kvTen")%></option>
											<%
												}
													}
											%>
									</select>
									</TD>
									</TR>
									<TR class="plainlabel">
											  <TD class="plainlabel"><%=ChuyenNgu.get("Ngày giao dịch",nnId) %> </TD>
											  <TD class="plainlabel">                               
                                                 <input type="text" size="10" class="days" id="ngaykhoaso" name="ngaykhoaso" value="<%=obj.getNgaykhoaso()%>" maxlength="10"   /> 
											    </TD>
											    
											     <TD class="plainlabel"><%=ChuyenNgu.get("Ghi chú",nnId) %>   </TD>
											  <TD class="plainlabel">                               
                                                 <input type="text"  name="ghichu" value="<%=obj.getGhichu()%>"    /> 
											    </TD>
											    
											    </TR>
								<TR>
									<TD class="plainlabel"><%=ChuyenNgu.get("Chọn tập tin",nnId) %></TD>
									<TD class="plainlabel"><INPUT type="file" name="filename" size="40" value=''></TD>
								</TR>
								<%if(quyen[0]!=0){ %>
								<TR>
									<td colspan="1">
										<label id="btUpload">
											<a class="button" href="javascript:upload();"> <img style="top: -4px;" src="../images/button.png" alt=""> Upload </a>
										</label>
									</td>
									<td colspan="1">
										<label id="btUpload">
											<a class="button" href="javascript:uploadSalesIn();"> <img style="top: -4px;" src="../images/button.png" alt=""> Upload SalesIn </a>
										</label>
									</td>

								</TR>
								<%} %>
							</TABLE>
						</div>
					</div>
				</fieldset>
			</div>
		</div>
        
        <div style="width:100%; float:none" align="left">
        	<fieldset>
	        	<legend><span class="legendtitle"><%=ChuyenNgu.get("Tồn kho store",nnId) %> </span>&nbsp;&nbsp;&nbsp;
	    		
	        		<a class="button3" href="javascript:newform()" style="display:none">
	                <img style="top: -4px;" src="../images/New.png" alt=""><%=ChuyenNgu.get("Tạo mới",nnId) %></a>
	            
	        	</legend>
	            <TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
	                <TR class="tbheader">
	                	<TH align="center" width="20%" ><%=ChuyenNgu.get("Mã nhà phân phối",nnId) %></TH>
	                    <TH align="center" width="60%"><%=ChuyenNgu.get("Nhà phân phồi",nnId) %></TH>
	                     <TH align="center" width="10%"><%=ChuyenNgu.get("Đã có tồn đầu",nnId) %></TH>
	                    <TH align="center" width="10%"><%=ChuyenNgu.get("Tác vụ",nnId) %></TH>
	                </TR>
	                <%
	                String tt="";
	                                    if (rsTonkho != null) 
	                                    {									
	                                        int m = 0;
	                                        while (rsTonkho.next()) 
	                                        {
	                                            if ((m % 2) == 0) {
	                                %>
	                <TR class='tbdarkrow'>
	                    <%
	                                        } else {
	                                    %>
	                
	                <TR class='tblightrow'>
	                    <%
	                                        }
	                                    %>
	                    <TD align="center"><%=rsTonkho.getString("nppMa")%></TD>
	                    <TD align="center"><%=rsTonkho.getString("nppTen")%></TD>
	                     <TD align="center"><%=rsTonkho.getInt("coks") > 0 ?  "x" : "" %></TD>
	                    <TD align="center">
	                        <A href="../../ImportTonKhoSvl?userId=<%=userId%>&excel=<%=rsTonkho.getString("nppId")%>"> <IMG src="../images/excel.gif" alt="Excel" title="Excel" border=0 width=20  height=20></A>&nbsp;
	                        <A href="../../ImportTonKhoSvl?userId=<%=userId%>&delete=<%=rsTonkho.getString("nppId")%>"> <IMG src="../images/Delete20.png" alt="Chốt" title="Chốt" border=0 width=20  height=20 onclick="if(!confirm('Bạn có muốn xóa tồn kho này?')) return false;"></A>&nbsp;
	                    </TD>
	                </tr>
	                <%   } }%>
	            </TABLE>
			</fieldset>
       </div>
	</form>

</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</html>

<% 
	if(npp != null){ npp.close(); npp = null ;} 
	if(vungRs != null){ vungRs.close(); vungRs = null ;} 
	if(khuvuc != null){ khuvuc.close(); khuvuc = null ;} 
	if(rsTonkho != null){ rsTonkho.close(); rsTonkho = null ;} 

	if(obj != null) obj.closeDB(); obj = null;
	session.setAttribute("obj",null);
	
}
%>