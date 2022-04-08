<%@page import="java.sql.ResultSet"%>
<%@page import="geso.dms.center.beans.Router.imp.Router"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.util.*" %>
<%@ page  import = "geso.dms.center.beans.lotrinh.ILoTrinh;" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	String loi=(String)session.getAttribute("loi");
	String tungay=(String)session.getAttribute("tungay");
	String denngay=(String)session.getAttribute("denngay");
	Utility util = (Utility) session.getAttribute("util");
	ILoTrinh obj = (ILoTrinh)session.getAttribute("obj");
	ResultSet khuvuc = (ResultSet)obj.getkhuvuc();
	ResultSet vung = (ResultSet)obj.getvung();
	ResultSet npp = (ResultSet)obj.getnpp();
	ResultSet kbh = (ResultSet)obj.getKenh();
	ResultSet ddkd = (ResultSet)obj.getddkd();
	ResultSet tuyen = (ResultSet)obj.getTuyen();
	ResultSet danhsach = (ResultSet)obj.getdanhsach();
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>
   
<% 

	int[] quyen = new  int[6];
	quyen = util.Getquyen("LoTrinhBanHangReport","&view="+obj.getView()+"", userId);

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<HTML>
<HEAD>
<TITLE>SGP - Project</TITLE>
	<META http-equiv="Content-Style-Type" content="text/css">
<script type="text/javascript" src="../scripts/jquery-1.js"></script>
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<script type="text/javascript" src="../scripts/phanTrang.js"></script>
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">

<script type="text/javascript" src="../scripts/jquery-1.js"></script>

<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />

<script type="text/javascript"	src="../scripts/jquery.min.1.7.js"></script>
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

<SCRIPT language="javascript" type="text/javascript">

function khuvucChanging() {
	document.forms['rpForm'].action.value="khuvucChanged";
}

function submitform()
{
	document.forms['rpForm'].action.value="thuchien";
	document.forms['rpForm'].submit();
}
function exportToExcel()
{
	if(document.forms["rpForm"].ac)
	
	//alter('Mời bạn chọn nhà phân phối!');
	if(document.forms['rpForm'].nppId.value==""){
		document.forms['rpForm'].nppId.focus();
		alert('Vui lòng chọn nhà phân phối!');
		
		return;
	}
	
	document.forms['rpForm'].action.value="export";
	document.forms['rpForm'].submit();
}
function submitCBO(){
	document.forms['rpForm'].action.value="";
	document.forms['rpForm'].submit();
}
function exportToMCP()
{
	document.forms['rpForm'].action.value="exportmcp";
	document.forms['rpForm'].submit();
}

function exportToMCPIP()
{
	document.forms['rpForm'].action.value="chitietip";
	document.forms['rpForm'].submit();
}

function exportToCC()
{
	document.forms['rpForm'].action.value="bcchamcong";
	document.forms['rpForm'].submit();
}

function exportToDetail()
{
	document.forms['rpForm'].action.value="chitiet";
	document.forms['rpForm'].submit();
}
</SCRIPT>
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
$(document).ready(function()
{
	$("#nppId").select2();
	$("#ddkdId").select2();

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

<form name="rpForm" method="post" action="../../LoTrinhBanHangReport">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value= <%= userId %> >
<%if(obj.getView().equals("NPP")) {%>
<input type="hidden" name="nppId" value= <%= obj.getnppId() %> >
<%} %>
<input type="hidden" name="view" value= <%= obj.getView() %> >
<input type="hidden" name="action" value='1'>

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" cellpadding="0" cellspacing="1">		
				<TR>
					<TD width="100%" align="left">
					  <table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
						   <TD align="left" colspan="2" class="tbnavigation">&nbsp;Báo cáo quản tri &gt; Báo cáo khác &gt; Lộ trình bán hàng</TD>
   
						   <TD colspan="2" align="right" class="tbnavigation">Chào mừng Bạn <%=userTen %>&nbsp;</TD>
						  </tr>
 					  </table>					</TD>
				</TR>
				<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>
	    				<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold"  style="width: 100% ; color:#F00 ; font-weight:bold" readonly="readonly" rows="1"><%=loi%></textarea>
						</FIELDSET>
				   </TD>
				</tr>	
				<TR>
					<TD>
					<TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
						<TR>
							<TD width="100%" align="center" >
							<FIELDSET>
							  <LEGEND class="legendtitle">Tiêu chí xuất báo cáo</LEGEND>
							<TABLE  width="100%" cellpadding="6" cellspacing="0">
								<TR>
								    <TD class="plainlabel" >Từ ngày <FONT class="erroralert"> *</FONT></TD>
								    <TD class="plainlabel" colspan="3">
								        <TABLE cellpadding="0" cellspacing="0">
								            <TR><TD>
								                <input AUTOCOMPLETE="off" class="days" type="text" name="tungay" id="tungay" value='<%=obj.getTungay() %>'  size="20">
								            </TD>
								            </TR>
								        </TABLE>																					
								    </TD>
								</TR>
								<TR>
								  <TD class="plainlabel" >Đến ngày <FONT class="erroralert"> *</FONT></TD>
								  <TD class="plainlabel" colspan="3">
								  	<TABLE cellpadding="0" cellspacing="0">
								            <TR><TD>
								                <input  AUTOCOMPLETE="off" class="days" type="text" name="denngay" id="denngay" value='<%=obj.getDenngay() %>' size="20">
								            </TD>
								            </TR>
								    </TABLE>
								
								
								</TR>

								<%if(!obj.getView().equals("NPP")) {%>
								
								<tr>
								
								<TD width="19%" class="plainlabel" >Vùng </TD>
								<TD class="plainlabel" >
								<TABLE cellpadding="0" cellspacing="0">
								<TR><TD>
								<select name="vungId" onchange="submitCBO();" >
                                 <option value ="" > </option>  
                               <%
                               
                               while(vung.next())
                               {
                               if(vung.getString("pk_seq").equals(obj.getvungId())) {
                            	%><option value ="<%=vung.getString("pk_seq") %>" selected> <%=vung.getString("ten") %></option>  
                            	  <%} else { %>
                            	  <option value ="<%=vung.getString("pk_seq") %>"> <%=vung.getString("ten") %></option>
                              <%}} %>                             </select>		   										</TD>
                                    		</TR>
								</TABLE>
								</TD>
								
								</tr>
								
								
								
								<tr>
								
								<TD width="19%" class="plainlabel" >Khu Vực </TD>
								<TD class="plainlabel" >
								<TABLE cellpadding="0" cellspacing="0">
								<TR><TD>
								<select name="khuvucId" onchange="submitCBO();" >
                                 <option value ="" > </option>  
                               <%
                               
                               while(khuvuc.next())
                               {
                               if(khuvuc.getString("pk_seq").equals(obj.getkhuvucId())) {
                            	%><option value ="<%=khuvuc.getString("pk_seq") %>" selected> <%=khuvuc.getString("ten") %></option>  
                            	  <%} else { %>
                            	  <option value ="<%=khuvuc.getString("pk_seq") %>"> <%=khuvuc.getString("ten") %></option>
                              <%}} %>                             </select>		   										</TD>
                                    		</TR>
								</TABLE>
								</TD>
								
								</tr>
								
								<tr>
								
								<TD width="19%" class="plainlabel" >Kênh bán hàng </TD>
								<TD class="plainlabel" >
								<TABLE cellpadding="0" cellspacing="0">
								<TR><TD>
								<select name="kenhId" onchange="submitCBO();" >
                                 <option value ="" > </option>  
                               <%
                               
                               while(kbh.next())
                               {
                               if(kbh.getString("pk_seq").equals(obj.getkenhId())) {
                            	%><option value ="<%=kbh.getString("pk_seq") %>" selected> <%=kbh.getString("ten") %></option>  
                            	  <%} else { %>
                            	  <option value ="<%=kbh.getString("pk_seq") %>"> <%=kbh.getString("ten") %></option>
                              <%}} %>                             </select>		   										</TD>
                                    		</TR>
								</TABLE>
								</TD>
								
								</tr>
							
								<tr>
									<td width="19%" class="plainlabel" >Trạng thái NPP</td>
									<td class="plainlabel" >
										<table cellpadding="0" cellspacing="0">
											<tr>
												<td>
													<select name="status" onchange="submitCBO();">
														<option value=""></option>
														<%if(obj.getStatus().equals("1")) {%>
															<option selected="selected" value="1">Hoạt Động</option>
															<option value="0">Không Hoạt Động</option>
														<%}else {%>
															<option value="1">Hoạt Động</option>
															<option selected="selected" value="0">Không Hoạt Động</option>
														<%} %>
													</select>
												</td>
											</tr>
										</table>
									
									</td>
								</tr>
									<%} %>
								<%if(!obj.getView().equals("NPP")) {%>
								<tr>
								  	<TD width="19%" class="plainlabel" >Nhà phân phối</TD>
								  	<TD class="plainlabel" >
										<TABLE cellpadding="0" cellspacing="0">
											<TR><TD>
								<select id = "nppId" name="nppId" onchange="submitCBO();" style = "width: 250px" >
                                 <option value =""> </option>  
                               <%
                               if(npp != null)
                               while(npp.next())
                               {
                               if(npp.getString("pk_seq").equals(obj.getnppId())) {
                            	%><option value ="<%=npp.getString("pk_seq") %>" selected> <%=npp.getString("ten") %></option>  
                            	  <%} else { %>
                            	  <option value ="<%=npp.getString("pk_seq") %>"> <%=npp.getString("ten") %></option>
                              <%}} %>                             </select>		   										</TD>
                                    		</TR>
								</TABLE>									
								</TD>
								
								</tr>
								<%} %>
								
							  <TR>
										<TD width="19%" class="plainlabel" >Nhân viên bán hàng</TD>
								  	<TD class="plainlabel" >
										<TABLE cellpadding="0" cellspacing="0">
											<TR><TD>
								<select name="ddkdId" id = "ddkdId"  style = "width: 250px">
                                 <option value =""> </option>  
                               <%
                               while(ddkd.next())
                               {
                               if(ddkd.getString("pk_seq").equals(obj.getddkdId())) {
                            	%><option value ="<%=ddkd.getString("pk_seq") %>" selected> <%=ddkd.getString("ten") %></option>  
                            	  <%} else { %>
                            	  <option value ="<%=ddkd.getString("pk_seq") %>"> <%=ddkd.getString("ten") %></option>
                              <%}} %>                             </select>		   										</TD>
                                    		</TR>
								</TABLE>									
								</TD>							
								</TR>
							    <TR>
							    <TR>
										<TD width="19%" class="plainlabel" >Tuyến</TD>
								  	<TD class="plainlabel" >
										<TABLE cellpadding="0" cellspacing="0">
								<TR><TD>
								<select name="tuyenId"  >
                                 <option value ="0"> </option>  
                               <%
                               while(tuyen.next())
                               {
                               if(tuyen.getString("ngaylamviec").equals(obj.gettuyenId())) {
                            	%><option value ="<%=tuyen.getString("ngayid") %>" selected> <%=tuyen.getString("ngaylamviec") %></option>  
                            	  <%} else { %>
                            	  <option value ="<%=tuyen.getString("ngayid") %>"> <%=tuyen.getString("ngaylamviec") %></option>
                              <%}} %>             
                               </select>		   										
                               </TD>
                               </TR>
                               </TABLE>
                               </TD>
                               </TR>
                               <TR>
									<td class="plainlabel"  colspan = "2">
										<a class="button3" href="javascript:exportToMCP()">
											<img style="top: -4px;" src="../images/button.png" alt=""> Lộ trình bán hàng </a> 
										&nbsp;&nbsp;&nbsp;&nbsp;
										<a class="button3" href="javascript:exportToDetail()">
											<img style="top: -4px;" src="../images/button.png" alt=""> Lộ trình chi tiết </a>
											&nbsp;&nbsp;&nbsp;&nbsp;
										<a class="button3" href="javascript:exportToMCPIP()">
											<img style="top: -4px;" src="../images/button.png" alt=""> Lộ trình bán hàng IP </a>
												&nbsp;&nbsp;&nbsp;&nbsp;
										<a class="button3" href="javascript:exportToCC()">
											<img style="top: -4px;" src="../images/button.png" alt=""> BC chấm công </a>
										
									</td>
									
								 
									
										
								
								</TR>
                               </TABLE>
                               <!-- <a class="button3" href="javascript:exportToExcel()">Xuất ra excel </a> -->
                               </FIELDSET>
                               
                               </TD>
                               </TR>
                               
    							  
                               </TABLE>
                                
								                        

			
					</TD>
				</TR>
				
			</TABLE>
		</TD>
	</TR>
</TABLE>
</form>
</body> 
</HTML>
<%
	try
	{
		if(!(danhsach == null)){ danhsach.close(); danhsach = null; }
		if(ddkd != null){ ddkd.close(); danhsach = null; }
		if(npp != null){ npp.close(); danhsach = null; }
		if(tuyen != null){ tuyen.close(); danhsach = null; }
		if(khuvuc!=null){ khuvuc.close(); danhsach = null; }
		
		if(obj != null){
			obj.DBclose();
			obj = null;
		}
		session.setAttribute("obj", null);
		
	}catch(java.sql.SQLException e){}
	}
%>