<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.beans.daidienkinhdoanh.IDaidienkinhdoanh" %>
<%@ page  import = "geso.dms.center.beans.daidienkinhdoanh.IDaidienkinhdoanhList" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%@ page  import = "geso.dms.center.util.ChuyenNgu" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ int[] quyen = new  int[6];		
%>

<% IDaidienkinhdoanhList obj =(IDaidienkinhdoanhList)session.getAttribute("obj"); %>
<% ResultSet ddkdList = (ResultSet)obj.getDdkdList(); %>
<% ResultSet gsbh =  (ResultSet) obj.getGsbanhang(); %>
<% ResultSet npp =  (ResultSet) obj.getNhaphanphoi(); %>
<% ResultSet kbh =  (ResultSet) obj.getKenhbanhang(); %>
<% obj.setNextSplittings(); %>

<% String nnId = (String)session.getAttribute("nnId"); %> 
<% if(nnId == null) {
	nnId = "vi"; 
 } 
String url = util.getChuyenNguUrl("DaidienkinhdoanhSvl","",nnId); 
String view = obj.getView();
if (view != null && view.equals("TT")) {
	quyen = util.Getquyen("DaidienkinhdoanhSvl","&view=TT",userId);
}
else {
	quyen = util.Getquyen("DaidienkinhdoanhSvl","",userId);
}

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/s">



<script type="text/javascript" src="../scripts/jquery-1.js"></script>
<script type="text/javascript"	src="../scripts/jquery.min.1.7.js"></script>

<% if (!geso.dms.center.util.GlobalValue.dev_mode) { %>
	<script type="text/javascript" src="../scripts/disableF12.js"></script>
<% } %>

<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<script type="text/javascript" src="../scripts/phanTrang.js"></script>
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">


<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />

<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js"
	type="text/javascript"></script>
<SCRIPT language="javascript" type="text/javascript">

function clearform()
{
	document.ddkdForm.ddkdTen.value = "";
	document.ddkdForm.ddkdma.value = "";
    document.ddkdForm.DienThoai.value = "";      
    document.ddkdForm.KenhBanHang.selectedIndex = 0;
    document.ddkdForm.GSBanHang.selectedIndex = 0;
    document.ddkdForm.NhaPhanPhoi.selectedIndex = 0;
    document.ddkdForm.loaigiamsat.selectedIndex = 0;
    document.ddkdForm.TrangThai.selectedIndex = 2;
    submitform();    
}

function submitform()
{
	document.forms['ddkdForm'].action.value= 'search';
	document.forms['ddkdForm'].submit();
}
function xuatexcel()
{
	document.forms['ddkdForm'].action.value= 'excel';
	document.forms['ddkdForm'].submit();
}
function newform()
{
	document.forms['ddkdForm'].action.value= 'new';
	document.forms['ddkdForm'].submit();
}
function thongbao(){
	var tt = document.forms['ddkdForm'].msg.value;
	if(tt.length>0)
    alert(document.forms['ddkdForm'].msg.value);
}

function Xoa(id){
	
	document.forms['ddkdForm'].IdXoa.value = id;
	document.forms['ddkdForm'].action.value= 'Xoa';
	document.forms['ddkdForm'].submit();
}

</SCRIPT>
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
$(document).ready(function()
{
	$("#NhaPhanPhoi").select2();
	$("#GSBanHang").select2();
	$("#khuvucId").select2();
	$("#nhanhangId").select2();
	$("#chungloaiId").select2();
	$("#programs").select2();
	$("#dvkdId").select2();
	$("#gsbhId").select2();
	$("#kenhId").select2();
	$("#dvdlId").select2();
});
</script>



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


<form name="ddkdForm" method="post" action="../../DaidienkinhdoanhSvl">  
<% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> 
<input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="IdXoa" value=''>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="crrApprSplitting" value="<%= obj.getCrrApprSplitting() %>" >
<input type="hidden" name="nxtApprSplitting" value="<%= obj.getNxtApprSplitting() %>" >
<input type="hidden" name="currentPage" value="<%= obj.getCurrentPage() %>" >
<input type="hidden" name="ngonnguId" value='<%=nnId%>'>
<input type="hidden" name="msg" value='<%=obj.getMsg() %>'>
<input type="hidden" name="view" value='<%=view%>'>

<script language="javascript" type="text/javascript">
    thongbao();
</script> 

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF"><!--begin body Dossier-->
		<!--begin common dossier info---> <!--End common dossier info--->
		<TABLE width="100%" cellpadding="0" cellspacing="1">
			
				<TR>
				  <TD align="left"><table width="100%" border="0" cellpadding="0" cellspacing="0">

						  <tr height="20">
						   <TD align="left" colspan="2" class="tbnavigation">&nbsp;<%= " " + url %> </TD>
   
						   <TD colspan="2" align="right" class="tbnavigation"><%=ChuyenNgu.get("Chào mừng",nnId)%> <%=userTen %>&nbsp;  </TD>
						  </tr>
 					  </table>
					</TD>
				</TR>

		</TABLE>
		<TABLE width="100%" cellpadding="0" cellspacing="0">		
				<TR>
					<TD>
					<TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
						<TR>
							<TD width="100%" align="center" >
							<FIELDSET>
							  <LEGEND class="legendtitle"><%=ChuyenNgu.get("Tiêu chí tìm kiếm",nnId)%> &nbsp;</LEGEND>
							<TABLE  width="100%" cellpadding="6" cellspacing="0">
								    <tr>  <TD width="24%" class="plainlabel"><%=ChuyenNgu.get("Tên",nnId)%> </TD>
								        <TD colspan="3" class="plainlabel"><input type="text" name="ddkdTen" value="<%= obj.getTen() %>" onChange="submitform();"></TD>
								         <TD class="plainlabel"><%=ChuyenNgu.get("Giám sát bán hàng",nnId)%> </TD>
								    <TD width="20%" class="plainlabel">
								    <SELECT name="GSBanHang" id = "GSBanHang" onChange = "submitform();" style = "width:250px">
									    <option value=""></option> 
									      <% try{ while(gsbh.next()){ 
									    	if(gsbh.getString("gsbhId").equals(obj.getGsbhId())){ %>
									      		<option value='<%=gsbh.getString("gsbhId") %>' selected='selected'><%=gsbh.getString("gsbhTen") %></option>
									      	<%}else{ %>
									     		<option value='<%=gsbh.getString("gsbhId") %>' ><%=gsbh.getString("gsbhTen") %></option>
									     	<%}}}catch(java.sql.SQLException e){} %>
									     	
									    </SELECT> </TD>
								  </TR>
								    <tr>  <TD width="24%" class="plainlabel"><%=ChuyenNgu.get("Mã",nnId)%> </TD>
								        <TD colspan="3" class="plainlabel"><input type="text" name="ddkdma" value="<%= obj.getSmartId() %>"  onChange="submitform();" ></TD>
								        <TD class="plainlabel"><%=ChuyenNgu.get("Loại",nnId)%></TD>
								    <TD colspan="3" class="plainlabel"><SELECT name="loaigiamsat" onChange = "submitform();" >
                                      <% 
                                      if (obj.getThuviec().equals("0")){ %>
								    	<option > </option>
								    	<option value="1"><%=ChuyenNgu.get("Thử việc",nnId)%></option>
								    	<option value="0" selected><%=ChuyenNgu.get("Nhân viên",nnId)%></option>
									<%}else{ 							
								  		if (obj.getThuviec().equals("1")){ %>
								    	<option > </option>
								    	<option value="1" selected><%=ChuyenNgu.get("Thử việc",nnId)%></option>
								    	<option value="0" ><%=ChuyenNgu.get("Nhân viên",nnId)%></option>
									<%}else{ %>
								    	<option  selected> </option>
								    	<option value="1" ><%=ChuyenNgu.get("Thử việc",nnId)%></option>
								    	<option value="0" ><%=ChuyenNgu.get("Nhân viên",nnId)%></option>
									<%}} %>
                                    </SELECT></TD>
								  </TR>
								  <TR>

								    <TD class="plainlabel"><%=ChuyenNgu.get("Số điện thoại",nnId)%> </TD>
								    <TD colspan="3" class="plainlabel"><input type="text" name="DienThoai" value="<%= obj.getSodienthoai() %>" onChange="submitform();"></TD>
								     <TD class="plainlabel"><%=ChuyenNgu.get("Trạng thái",nnId)%> </TD>
								    <TD colspan="3" class="plainlabel"><SELECT name="TrangThai" onChange = "submitform();">
                                      
                                      <% if (obj.getTrangthai().equals("1")){%>
											  <option value="1" selected><%=ChuyenNgu.get("Hoạt động",nnId)%></option>
											  <option value="0"><%=ChuyenNgu.get("Ngưng hoạt động",nnId)%></option>
											  <option value="2"></option>
											  
										<%}else if(obj.getTrangthai().equals("0")) {%>
											  <option value="0" selected><%=ChuyenNgu.get("Ngưng hoạt động",nnId)%></option>
											  <option value="1" ><%=ChuyenNgu.get("Hoạt động",nnId)%></option>
											  <option value="2" ></option>
											  
										<%}else{%>																						  
											  <option value="1" ><%=ChuyenNgu.get("Hoạt động",nnId)%></option>
											  <option value="0" ><%=ChuyenNgu.get("Ngưng hoạt động",nnId)%></option>
											  <option value="2" selected></option>
										<%}%>
                                    </SELECT></TD>
								      
						      </TR>
								  <TR>
								    <TD class="plainlabel"><%=ChuyenNgu.get("Kênh bán hàng",nnId)%> </TD>
								    <TD colspan="3" class="plainlabel">
								    	<SELECT name="KenhBanHang" onChange = "submitform();">
									    <option value=""></option> 
									      <% try{ while(kbh.next()){ 
									    	if(kbh.getString("kbhId").equals(obj.getKbhId())){ %>
									      		<option value='<%=kbh.getString("kbhId") %>' selected='selected'><%=kbh.getString("kbhTen") %></option>
									      	<%}else{ %>
									     		<option value='<%=kbh.getString("kbhId") %>' ><%=kbh.getString("kbhTen") %></option>
									     	<%}}}catch(java.sql.SQLException e){} %>
									     	
									    </SELECT></TD>
									    
						            <TD width="18%" class="plainlabel"><%=ChuyenNgu.get("Nhà phân phối",nnId)%></TD>
									<TD width="33%" class="plainlabel">
									<SELECT name="NhaPhanPhoi" id="NhaPhanPhoi" onChange = "submitform()" style = "width:350px" >
									<option value=""> </option>
									<% if(npp != null){
										  try
										  { 
											  String optionGroup = "";
											  String optionName = "";
											  int i = 0;
											  
											  while(npp.next())
											  { 
												 if(i == 0)
												 {
													 optionGroup = npp.getString("kvTen");
													 optionName = npp.getString("kvTen");
													 
													 %>
													 
													 <optgroup label="<%= optionName %>" >
												 <% }
												 
												 optionGroup = npp.getString("kvTen");
												 if(optionGroup.trim().equals(optionName.trim()))
												 { %>
													 <% if(obj.getNppId() != null && npp.getString("nppId").equals(obj.getNppId())) {%>
													 	<option value="<%= npp.getString("nppId") %>" selected="selected" ><%= npp.getString("nppTen") %></option>
													 <%} else { %>
													 	<option value="<%= npp.getString("nppId") %>"><%= npp.getString("nppTen") %></option>
													 <%} %>
												 <% }
												 else
												 {
													 %>
													</optgroup>
													<% optionName = optionGroup; %>
													<optgroup label="<%= optionName %>" >
													<% if(obj.getNppId() != null && npp.getString("nppId").equals(obj.getNppId())) {%>
													 	<option value="<%= npp.getString("nppId") %>" selected="selected" ><%= npp.getString("nppTen") %></option>
													 <%} else { %>
													 	<option value="<%= npp.getString("nppId") %>"><%= npp.getString("nppTen") %></option>
													 <%} %>
												 <% }
												 i++;
								     	 	  } 
											  %>
											  	</optgroup>
											  <%npp.close(); 
								     	 }catch(java.sql.SQLException e){}}%>	  
                                	</SELECT>
								</TD>
									     
				              </TR>
								  <TR>
								   

						            
							  </TR>
							  <TR>
								  
						      </TR>
								  <TR>
								  
						      </TR>
							    <TR>
									<TD colspan="6" class="plainlabel">
									<a class="button2" href="javascript:clearform()">
											<img style="top: -4px;" src="../images/button.png" alt=""><%=ChuyenNgu.get("Nhập lại",nnId)%></a>&nbsp;&nbsp;&nbsp;&nbsp;
									<a class="button2" href="javascript:xuatexcel()">
											<img style="top: -4px;" src="../images/button.png" alt=""><%=ChuyenNgu.get("Xuất Excel",nnId)%> </a>&nbsp;&nbsp;&nbsp;&nbsp;
                                    </TD>
								</TR>

							</TABLE>

							</FIELDSET>
							</TD>
						</TR>
					</TABLE>
					</TD>
				</TR>

				<TR>

					<TD width="100%">
						<FIELDSET>
							<LEGEND class="legendtitle">&nbsp;<%=ChuyenNgu.get("Nhân viên bán hàng",nnId)%> &nbsp;&nbsp;&nbsp;
							<%if(quyen[0]!=0){ %>
							<a class="button3" href="javascript:newform()">
    								<img style="top: -4px;" src="../images/New.png" alt=""><%=ChuyenNgu.get("Tạo mới",nnId)%>  </a>    
							<%} %>
							
						</LEGEND>
	
						    <TABLE class="" width="100%">
						<TR>
							<TD width="98%">
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">

								<TR class="tbheader">
									<TH width="4%"><%=ChuyenNgu.get("Route",nnId)%></TH>
									<TH width="10%"><%=ChuyenNgu.get("Tên đăng nhập",nnId)%></TH>
									<TH width="13%"><%=ChuyenNgu.get("Tên",nnId)%></TH>
									<TH width="8%"><%=ChuyenNgu.get("Số điện thoại",nnId)%></TH>
									<TH width="18%"><%=ChuyenNgu.get("Nhà phân phối",nnId)%> </TH>
									<TH width="10%"><%=ChuyenNgu.get("Giám sát bán hàng",nnId)%></TH>
<%-- 									<TH width="7%"><%=ChuyenNgu.get("Loại",nnId)%></TH> --%>
									<TH width="7%"><%=ChuyenNgu.get("Trạng thái",nnId)%></TH>
									<TH width="8%"><%=ChuyenNgu.get("Ngày tạo",nnId)%></TH>
									<TH width="8%"><%=ChuyenNgu.get("Người tạo",nnId)%></TH>
									<TH width="8%"><%=ChuyenNgu.get("Ngày sửa ",nnId)%></TH>
									<TH width="8%"><%=ChuyenNgu.get("Người sửa ",nnId)%></TH>
									<TH width="17%" align="center">&nbsp;<%=ChuyenNgu.get("Tác vụ",nnId)%></TH>
								</TR>

                                <%      
									int m = 0;
                                    String lightrow = "tblightrow";
                                    String darkrow = "tbdarkrow";
                                    
                                    if(ddkdList!=null)
                                    while (ddkdList.next()){
                                       
                                        if (m % 2 != 0) {%>                     
                                            <TR class= <%=lightrow%> >
                                        <%} else { %>
                                            <TR class= <%= darkrow%> >
                                        <%}%>
                                        		<TD align="center"><%= ddkdList.getString("route") %></TD>
                                        		<TD align="center"><%= ddkdList.getString("smartid") %></TD>
                                                <TD align="left"><div align="left"><%= ddkdList.getString("ten") %></div></TD>
                                                <%String dienthoai= ddkdList.getString("dienthoai");
                                                if(dienthoai==null||dienthoai.trim().length()==0||dienthoai.equals("null"))
                                                	dienthoai="NA";
                                                %>                                   
                                                <TD><div align="center"><%= dienthoai%></div></TD>
                                                <TD align="left"><%= ddkdList.getString("nppTen") %></TD>
                                                <%String giamsat= ddkdList.getString("gsbhTen");
                                                if(giamsat==null||giamsat.trim().length()==0)
                                                	giamsat="NA";
                                                %>
                                                <TD align="left"><%= giamsat %></TD>
                                                
<%--                                                 <% if(ddkdList.getString("tinhtrang").equals("0")){ %>                             
												<TD align="center"><%=ChuyenNgu.get("Nhân viên",nnId)%> </TD>
												<%} else {%>
												<TD align="center"><%=ChuyenNgu.get("Thử việc",nnId)%></TD>
												<%} %>   --%>
												
                                                <% if (ddkdList.getString("trangthai").equals("1")){ %>
                                                    <TD align="left"><%=ChuyenNgu.get("Hoạt động",nnId)%></TD>
                                                <%}else{%>
                                                    <TD align="left"><%=ChuyenNgu.get("Ngưng hoạt động",nnId)%></TD>
                                                <%}%>
                                                <TD align="center"><%= ChuyenNgu.get(ddkdList.getString("ngaytao"),nnId) %></TD>
                                                <TD align="center"><%= ChuyenNgu.get(ddkdList.getString("nguoitao"),nnId) %></TD>
                                                <TD align="center"><%= ChuyenNgu.get(ddkdList.getString("ngaysua"),nnId)%></TD>
                                                <TD align="center"><%= ChuyenNgu.get(ddkdList.getString("nguoisua"),nnId)%></TD>
                                                <TD align="center">
                                                <TABLE border = 0 cellpadding="0" cellspacing="0">
                                                    <TR>
                                                    <TD>
                                                    <%if(quyen[2]!=0 && 1==2){ %>
                                                    	<A href = "../../RouterSvl?task=<%= Utility.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "DaidienkinhdoanhUploadSvl?userId=" + userId+ "&update="+ ddkdList.getString("id")+"&view="+ view) %>"><img src="../images/plus32.png" alt="Thêm ảnh" title="Thêm ảnh" width="20" height="20" longdesc="Thêm ảnh" border = 0></A>
                                                    <%} %>
                                                    </TD>
                                                    <TD>&nbsp;</TD>
                                                    <TD>
                                                    <%if(quyen[2]!=0){ %>
                                                    	<A href = "../../RouterSvl?task=<%= Utility.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "DaidienkinhdoanhUpdateSvl?userId=" + userId+ "&update="+ ddkdList.getString("id")+"&view="+ view) %>"><img src="../images/Edit20.png" alt="Cap nhat" title="Cập nhật" width="20" height="20" longdesc="Cap nhat" border = 0></A>
                                                    <%} %>
                                                    </TD>
                                                    <TD>&nbsp;</TD>
                                                    <TD>
                                                    <%if(quyen[Utility.XOA]!=0){ %>
                                                        	<A href="javascript:Xoa('<%=ddkdList.getString("id") %>');">
                                                        		<img src="../images/Delete20.png" alt="Xoa" title="Xóa" width="20" height="20" longdesc="Xoa" border=0 
                                                        		onclick="if(!confirm('Bạn có muốn xóa Nhân viên bán hàng này?')) return false;"></A>
                                                    <%} %>
                                                    </TD>
                                                    <TD>&nbsp;</TD>
                                                    <TD>	
														<%if(quyen[3]!=0){ %>
														<A href = "../../DaidienkinhdoanhUpdateSvl?userId=<%=userId%>&display=<%=ddkdList.getString("id") %>"><img src="../images/Display20.png" alt="Hien thi" title="Hiển thị" width="20" height="20" longdesc="Hien thi" border = 0></A>&nbsp;
														<%} %>
													</TD>
                                                    
                                                    </TR></TABLE>
                                                </TD>
                                            </TR>
                                <%m++; }%>
                                
                                
                                <tr class="tbfooter" > 
											 <TD align="center" valign="middle" colspan="13" class="tbfooter">
											 	<%if(obj.getNxtApprSplitting() >1) {%>
													<img alt="<%=ChuyenNgu.get("Trang đầu",nnId)%>" src="../images/first.gif" style="cursor: pointer;" onclick="View('ddkdForm', 1, 'view')"> &nbsp;
												<%}else {%>
													<img alt="<%=ChuyenNgu.get("Trang đầu",nnId)%>" src="../images/first.gif" > &nbsp;
													<%} %>
												<% if(obj.getNxtApprSplitting() > 1){ %>
													<img alt="<%=ChuyenNgu.get("Trang trước",nnId)%>" src="../images/prev.gif" style="cursor: pointer;" onclick="View('ddkdForm', eval(ddkdForm.nxtApprSplitting.value) -1, 'view')"> &nbsp;
												<%}else{ %>
													<img alt="<%=ChuyenNgu.get("Trang trước",nnId)%>" src="../images/prev_d.gif" > &nbsp;
												<%} %>
												
												<%
													int[] listPage = obj.getNextSplittings();
													for(int i = 0; i < listPage.length; i++){
												%>
												
												<% 
												
												System.out.println("Current page:" + obj.getNxtApprSplitting());
												System.out.println("List page:" + listPage[i]);
												
												if(listPage[i] == obj.getNxtApprSplitting()){ %>
												
													<a  style="color:white;"><%= listPage[i] %>/ <%=obj.getTheLastSplitting() %></a>
												<%}else{ %>
													<a href="javascript:View('ddkdForm', <%= listPage[i] %>, 'view')"><%= listPage[i] %></a>
												<%} %>
													<input type="hidden" name="list" value="<%= listPage[i] %>" />  &nbsp;
												<%} %>
												
												<% if(obj.getNxtApprSplitting() < obj.getTheLastSplitting()){ %>
													&nbsp; <img alt="<%=ChuyenNgu.get("Trang tiếp",nnId)%>" src="../images/next.gif" style="cursor: pointer;" onclick="View('ddkdForm', eval(ddkdForm.nxtApprSplitting.value) +1, 'view')"> &nbsp;
												<%}else{ %>
													&nbsp; <img alt="<%=ChuyenNgu.get("Trang tiếp",nnId)%>" src="../images/next_d.gif" > &nbsp;
												<%} %>
												<%if(obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {%>
											   		<img alt="<%=ChuyenNgu.get("Trang cuối",nnId)%>" src="../images/last.gif" > &nbsp;
										   		<%}else{ %>
										   			<img alt="<%=ChuyenNgu.get("Trang cuối",nnId)%>" src="../images/last.gif" style="cursor: pointer;" onclick="View('ddkdForm', -1, 'view')"> &nbsp;
										   		<%} %>
											</TD>
										 </tr>  
									
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
	if(ddkdList!=null){ ddkdList.close(); ddkdList = null; }
	if(gsbh!=null){ gsbh.close(); gsbh = null;  }	
	if(npp!=null){ npp.close(); npp = null;  }
	if(kbh!=null){ kbh.close(); kbh = null;  }
	
	obj.DbClose(); obj = null;
	session.setAttribute("obj",null);
	
}catch(Exception er){
	
}

}%>

