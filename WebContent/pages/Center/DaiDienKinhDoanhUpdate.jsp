<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.beans.daidienkinhdoanh.IDaidienkinhdoanh" %>
<%@ page  import = "java.util.Hashtable"%>
<%@ page  import = "java.sql.ResultSet "%>
<%@ page  import = "geso.dms.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if (!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>


<% IDaidienkinhdoanh ddkdBean = (IDaidienkinhdoanh)session.getAttribute("ddkdBean"); %>
<% ResultSet npp = (ResultSet)ddkdBean.getNppList(); %>
<% //ResultSet ttpp = (ResultSet) ddkdBean.getTrungtamphanphoiList(); %>
<% String nppId = (String)ddkdBean.getNppId(); %>
<% ResultSet gsbhList = (ResultSet)ddkdBean.getGsbhList(); %>
<% Hashtable gsbhIds = (Hashtable)ddkdBean.getGsbhIds(); %>
<% ResultSet rsddkd = (ResultSet)ddkdBean.GetRsDDKDTienNhiem(); %>
<% String nnId = (String)session.getAttribute("nnId"); %> 
<% 
if (nnId == null) {
	nnId = "vi"; 
} 
String view = ddkdBean.getView();
String url = util.getChuyenNguUrl("DaidienkinhdoanhSvl","",nnId); 
ResultSet route = ddkdBean.getRouteRs();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">

<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<script type="text/javascript" src="../scripts/maskedinput.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function() {
		 $("#ngaysinh").mask("9999-99-99");
			$( ".days" ).datepicker({			    
					changeMonth: true,
					changeYear: true				
			});            
        }); 		
		
</script>

<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
     $(document).ready(function() { 
      $("select:not(.notuseselect2)").select2({ width: 'resolve' });     
     });
</script>

<SCRIPT language="javascript" type="text/javascript">
 function confirmLogout(){
    if (confirm("Ban co muon dang xuat?"))
    {
		top.location.href = "home.jsp";
    }
    return
  }
 function submitform()
 {   
    document.forms["ddkdForm"].submit();
 }

 function saveform()
 {
	 var ten = document.getElementById('ddkdTen');
	 var diachi = document.getElementById('DiaChi');
	 var dienthoai = document.getElementById('DienThoai');
	 var gsbh_npp = document.getElementsByName("gsbhList");
	 var matkhau = document.getElementById("matkhau");
	 
	 var ngaysinh = document.getElementById('ngaysinh');
	 var cmnd = document.getElementById('cmnd');
	 var ngayvaolam = document.getElementById('ngaybatdau');
	 var nppId = document.getElementById('nppId');
	 var route_fk = document.getElementById('route_fk');
	 
	 var ngayketthuc = document.getElementsByName("ngayketthuc");
	 var gioitinh = document.getElementsByName("chutaikhoan");
	 var gioitinh = document.getElementsByName("chutaikhoan");
	 
	 if (nppId.value == '') {
		 alert('Vui l??ng ch???n chi nh??nh/nh?? ph??n ph???i!');
		 return;
	 }
	 
	 if (ngaysinh.value == "")
	 {
		 alert('Vui l??ng nh???p ng??y sinh Nh??n vi??n b??n h??ng');
		 return;
	 }
	 
	 if (cmnd.value == "")
	 {
		 alert('Vui l??ng nh???p CMND Nh??n vi??n b??n h??ng');
		 return;
	 }
	 
	 if (ngayvaolam.value == "")
	 {
		 alert('Vui l??ng nh???p ng??y v??o l??m Nh??n vi??n b??n h??ng');
		 return;
	 }
	 
	 if (ngayketthuc.value == "")
	 {
		 alert('Vui l??ng nh???p ng??y k???t th??c Nh??n vi??n b??n h??ng');
		 return;
	 }
	 
	 if (gioitinh.value == "")
	 {
		 alert('Vui l??ng nh???p gi???i t??nh Nh??n vi??n b??n h??ng');
		 return;
	 }
	 
	 if (ten.value == "")
	 {
		 alert('Vui l??ng nh???p t??n Nh??n vi??n b??n h??ng');
		 return;
	 }
	 if (diachi.value == "")
	 {
		 alert('Vui l??ng nh???p ?????a ch???');
		 return;
	 }	
	 if (dienthoai.value == "")
	 {
		 alert('Vui l??ng nh???p s??? ??i???n tho???i');
		 return;
	 }	 
	
		 
	 var flag = '';
	 for(var i in gsbh_npp)
	 {
		 if (gsbh_npp[i].checked)
			 flag = flag + gsbh_npp[i].value;
	 }
	 if (flag == '')
	 {
		 alert('Vui l??ng ch???n gi??m s??t b??n h??ng v?? nh?? ph??n ph???i');
		 return;
	 }
	 
 	 document.forms['ddkdForm'].action.value='save';
     document.forms['ddkdForm'].submit();
 }
 function upload(){// nhan nut cap nhat

	   if (document.forms["ddkdForm"].filename.value==""){
		   
		   document.forms["ddkdForm"].dataerror.value="Ch??a t??m file upload l??n. Vui l??ng ch???n file c???n upload.";
	   }else{
		 document.forms["ddkdForm"].setAttribute('enctype', "multipart/form-data", 0);
		 document.forms["ddkdForm"].submit();	
	   }

}
 
 function upload1(){// nhan nut cap nhat
	
	   if (document.forms["ddkdForm"].filenamecap.value==""){
		   
		   document.forms["ddkdForm"].dataerror.value="Ch??a t??m file upload l??n. Vui l??ng ch???n file c???n upload.";
	   }else{
		  document.forms["ddkdForm"].action.value="filecap";
		 document.forms["ddkdForm"].setAttribute('enctype', "multipart/form-data", 0);
		 document.forms["ddkdForm"].submit();	
	   }

}
 
 function upload2(){// nhan nut cap nhat

	   if (document.forms["ddkdForm"].filenamethe.value==""){
		   
		   document.forms["ddkdForm"].dataerror.value="Ch??a t??m file upload l??n. Vui l??ng ch???n file c???n upload.";
	   }else{
		   document.forms["ddkdForm"].action.value="filethe";
		 document.forms["ddkdForm"].setAttribute('enctype', "multipart/form-data", 0);
		 document.forms["ddkdForm"].submit();	
	   }

}
 
 function upload3(){// nhan nut cap nhat

	   if (document.forms["ddkdForm"].filenamemat.value==""){
		   
		   document.forms["ddkdForm"].dataerror.value="Ch??a t??m file upload l??n. Vui l??ng ch???n file c???n upload.";
	   }else{
		   document.forms["ddkdForm"].action.value="filemat";
		 document.forms["ddkdForm"].setAttribute('enctype', "multipart/form-data", 0);
		 document.forms["ddkdForm"].submit();	
	   }

}
 
 function Xoafile1(name)
 {
 	
 	if (confirm("B???n mu???n x??a file ????nh k??m"))
 	{
 		/* xmlhttp.open("GET","../../ThongbaoUpdateSvl?task=" + "xoafilenew" + "&id=" + document.getElementById('valueten').value,true);
 		xmlhttp.send(); */
 		document.forms["ddkdForm"].removename.value = name;
 		document.forms["ddkdForm"].action.value = "removecap";
	    document.forms['ddkdForm'].submit();
 	}
 	else
 		return false;
 }
 
 function Xoafile2(name)
 {
 	
 	if (confirm("B???n mu???n x??a file ????nh k??m"))
 	{
 		/* xmlhttp.open("GET","../../ThongbaoUpdateSvl?task=" + "xoafilenew" + "&id=" + document.getElementById('valueten').value,true);
 		xmlhttp.send(); */
 		document.forms["ddkdForm"].removename.value = name;
 		document.forms["ddkdForm"].action.value = "removethe";
	    document.forms['ddkdForm'].submit();
 	}
 	else
 		return false;
 }
 
 function Xoafile3(name)
 {
 	
 	if (confirm("B???n mu???n x??a file ????nh k??m"))
 	{
 		/* xmlhttp.open("GET","../../ThongbaoUpdateSvl?task=" + "xoafilenew" + "&id=" + document.getElementById('valueten').value,true);
 		xmlhttp.send(); */
 		document.forms["ddkdForm"].removename.value = name;
 		document.forms["ddkdForm"].action.value = "removemat";
	    document.forms['ddkdForm'].submit();
 	}
 	else
 		return false;
 }
 
 function Download1(name)
 {
 	
 	if (confirm("B???n mu???n download file ????nh k??m"))
 	{
 		/* xmlhttp.open("GET","../../ThongbaoUpdateSvl?task=" + "xoafilenew" + "&id=" + document.getElementById('valueten').value,true);
 		xmlhttp.send(); */
 		document.forms["ddkdForm"].download.value = name;
 		document.forms["ddkdForm"].action.value = "downcap";
	    document.forms['ddkdForm'].submit();
 	}
 	else
 		return false;
 }
 function Download2(name)
 {
 	
 	if (confirm("B???n mu???n download file ????nh k??m"))
 	{
 		/* xmlhttp.open("GET","../../ThongbaoUpdateSvl?task=" + "xoafilenew" + "&id=" + document.getElementById('valueten').value,true);
 		xmlhttp.send(); */
 		document.forms["ddkdForm"].download.value = name;
 		document.forms["ddkdForm"].action.value = "downthe";
	    document.forms['ddkdForm'].submit();
 	}
 	else
 		return false;
 }
 function Download3(name)
 {
 	
 	if (confirm("B???n mu???n download file ????nh k??m"))
 	{
 		/* xmlhttp.open("GET","../../ThongbaoUpdateSvl?task=" + "xoafilenew" + "&id=" + document.getElementById('valueten').value,true);
 		xmlhttp.send(); */
 		document.forms["ddkdForm"].download.value = name;
 		document.forms["ddkdForm"].action.value = "downmat";
	    document.forms['ddkdForm'].submit();
 	}
 	else
 		return false;
 }
 
</SCRIPT>
<script src="../Uploadify/swfobject.js" type="text/javascript"></script>
<script src="../Uploadify/jquery.uploadify.v2.1.4.min.js" type="text/javascript"></script>
<link href="../Uploadify/uploadify.css" rel="stylesheet" type="text/css" />

 <script type="text/javascript">
     $(function () {
         $('#file_upload').uploadify({
             'buttonImage': '../Uploadify/images.jpg',
             'uploader': '../Uploadify/uploadify.swf',
             'script': '../../DaidienkinhdoanhUpdateSvl?flag=saveFile',
             'cancelImg': '../Uploadify/cancel.png',
             'buttonText': ' Upload File Hinh ',
             'folder': 'Files',
             'fileDataName': 'Filedata',
             'fileDesc': 'Images Files',
             'fileExt': '*.jpg; *.png; *.gif',
             'multi': false,
             'auto': true,
             'removeCompleted': false,
             'onComplete': function (event, queueID, fileObj, response, data) {
              document.getElementById("hinhdaidien").innerHTML = "<img src='../Templates/images/" + response + "' style='max-width:50px; max-height:50px' />";
              document.getElementById("fileName").value = response;
              document.getElementById("fileNamecap").value = response;
              document.getElementById("fileNamethe").value = response;
              document.getElementById("fileNamemat").value = response;
              
             }
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
<!-- BEGIN RENDER AUTO -->
<%if (!geso.dms.center.util.GlobalValue.getDevmode()){ %>
	<noscript> 
	 	<meta http-equiv="refresh" content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")  %>">
		<!-- //window.location = "https://www.google.com"; -->
	</noscript>
<%} %>
<!-- END RENDER AUTO -->

<form name="ddkdForm" method="post" action="../../DaidienkinhdoanhUpdateSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<INPUT name="userId" type="hidden" value='<%= userId %>' size="30">
<input type="hidden" name="removename" value="">
<input type="hidden" name="download" value="">
<input type="hidden" name="id" value='<%= ddkdBean.getId() %>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="ngonnguId" value='<%=nnId%>'>
<input type="hidden" name="view" value='<%=view%>'>


<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"  height="100%">
    <TR>
        <TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
            <TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
                <TR>
                    <TD align="left" class="tbnavigation">
                        <table width="100%" border="0" cellpadding="0" cellspacing="0">
                          <tr height="22">
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;<%=url %> > <%=ChuyenNgu.get("C???p nh???t",nnId)%></TD> 
                             <TD colspan="2" align="right" class="tbnavigation"><%=ChuyenNgu.get("Ch??o m???ng ",nnId)%> <%=userTen %>&nbsp;  </TD></tr>

                        </table>
                    </TD>
                </TR>
            </TABLE>
            <TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
                    <TR class = "tbdarkrow">
                        <TD width="30" align="center"><A href="javascript:history.back()" ><img src="../images/Back30.png" alt="Back"  title="Back" width="30" height="30" border="1" longdesc="Back" style="border-style:outset"></A></TD>
                        <TD width="2" align="left" ></TD>
                        <TD width="30" align="left" ><A href="javascript:saveform()" ><IMG src="../images/Save30.png" title="Save" alt="Save" border = "1"  style="border-style:outset"></A></TD>

                        <TD align="left" >&nbsp;</TD>
                    </TR>
            </TABLE>
            <TABLE width="100%" border="0" cellpadding="0"  cellspacing="0">
                                <TR>
                                    <TD align="left" colspan="4" class="legendtitle">
                                        <FIELDSET>
                                        <LEGEND class="legendtitle"><%=ChuyenNgu.get("B??o l???i nh???p li???u",nnId)%> </LEGEND>
                                        
                                        <textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold" style="width: 100%" readonly="readonly" rows="1" ><%= ddkdBean.getMessage() %></textarea>
                                        <%ddkdBean.setMessage(""); %>
                                        </FIELDSET>
                                   </TD>
                                </TR>
                
              	<TR>
							<TD height="100%" width="100%">
								<FIELDSET>
									<LEGEND class="legendtitle" style="color: black"><%=ChuyenNgu.get("Th??ng tin",nnId)%></LEGEND>
									<TABLE border="0" width="100%" cellpadding="4" cellspacing="0">

										<TR>
											<TD width="130px" class="plainlabel" valign="top"><%=ChuyenNgu.get("H??nh",nnId)%></TD>
											<TD width="300px" class="plainlabel" valign="top">
												<div id="hinhdaidien">
													<img src="../Templates/images/<%= ddkdBean.getHinhanh()%>"
														style="max-width: 300px; max-height: 300px" />
												</div> <input type="hidden" id="fileName" name="fileName"
												value="<%= ddkdBean.getHinhanh() %>">
											</TD>
											<TD width="120px" class="plainlabel" valign="top"></TD>
											<TD class="plainlabel" valign="top" colspan="5"></TD>
										</TR>
										<TR class="plainlabel">
											<TD width="130px" class="plainlabel" valign="top"><%=ChuyenNgu.get("Ch???n File",nnId)%></TD>
											<TD width="300px" class="plainlabel" valign="top"><INPUT
												type="file" name="filename" size="40" value=''></td>

											<TD width="120px" class="plainlabel" valign="top">
												&nbsp;&nbsp;&nbsp;&nbsp; <a class="button2"
												href="javascript:upload()"> <img style="top: -4px;"
													src="../images/button.png" alt=""> <%=ChuyenNgu.get("C???p nh???t",nnId)%>
											</a>

											</TD>
											<TD class="plainlabel" colspan="5"></TD>
										</TR>


										<TR>
											<TD width="130px" class="plainlabel" valign="top"><%=ChuyenNgu.get("T??n",nnId)%> <FONT class="erroralert">*</FONT>
											</TD>
											<TD width="300px" class="plainlabel" valign="top"><INPUT
												name="ddkdTen" maxlength="50" id="ddkdTen" type="text"
												value="<%= ddkdBean.getTen() %>"></TD>
											<TD width="120px" class="plainlabel" valign="top"><%=ChuyenNgu.get("Gi???i t??nh",nnId)%> <FONT
												class="erroralert">*</FONT></TD>
											<TD class="plainlabel" colspan="5"><INPUT type="text"
												name="chutaikhoan" value="<%= ddkdBean.getChuTaiKhoan()  %>"></TD>

										</TR>
										<TR>
											<TD width="130px" class="plainlabel" valign="top"><%=ChuyenNgu.get("M??",nnId)%> <FONT class="erroralert">*</FONT>
											</TD>
											<TD width="300px" class="plainlabel" valign="top">
												<INPUT name="ddkdMa" maxlength="50" id="ddkdMa" type="text"
													value="<%= ddkdBean.getSmartId() %>" >
											</TD>
											<TD width="120px" class="plainlabel" valign="top"><%=ChuyenNgu.get("S??? t??i kho???n",nnId)%></TD>
											<TD class="plainlabel" colspan="5"><INPUT type="text"
												name="sotaikhoan" value="<%= ddkdBean.getSoTaiKhoan()  %>"></TD>
										</TR>
										<TR>
											<TD class="plainlabel"><%=ChuyenNgu.get("Ng??y sinh",nnId)%> <FONT
												class="erroralert">*</FONT></TD>
											<TD class="plainlabel"><input name="ngaysinh"
												id="ngaysinh" type="text"
												value="<%= ddkdBean.getNgaysinh()%>"></TD>
											<TD class="plainlabel"><%=ChuyenNgu.get("Ng??n h??ng",nnId)%></TD>
											<TD class="plainlabel" colspan="5"><INPUT type="text"
												name="nganhang" value="<%= ddkdBean.getNganHang() %>"></TD>
										</TR>
										<TR>
											<TD class="plainlabel"><%=ChuyenNgu.get("S??? CMND ",nnId)%><FONT
												class="erroralert">*</FONT></TD>
											<TD class="plainlabel"><input name="cmnd" maxlength="50"
										 		id="cmnd" type="text" value="<%= ddkdBean.getCmnd()%>"></TD>
											<TD class="plainlabel"><%=ChuyenNgu.get("M???t kh???u",nnId)%> <FONT
												class="erroralert">*</FONT></TD>
											<TD class="plainlabel" colspan="5"><INPUT type="text" id="matkhau"
												name="matkhau" value="<%= ddkdBean.getMatkhau()%>"></TD>
										</TR>
										<TR>
											<TD class="plainlabel"><%=ChuyenNgu.get("?????a ch???",nnId)%> <FONT class="erroralert">*</FONT></TD>
											<TD class="plainlabel"><INPUT name="DiaChi" id="DiaChi"
												type="text" value="<%= ddkdBean.getDiachi() %>"
												maxlength="300" ></TD>
											<TD class="plainlabel"><div align="right"><%=ChuyenNgu.get("Ho???t ?????ng",nnId)%></div></TD>
													<TD class="plainlabel" colspan="5">	
														<%
						      		if (ddkdBean.getTrangthai() ==null ){
						      			ddkdBean.setTrangthai("0");
						      		}
						      		if (ddkdBean.getTrangthai().equals("1")){%>
										<input name="TrangThai" id="TrangThai" type="checkbox" value ="1" checked>
									<%} else {%>
										<input name="TrangThai" id="TrangThai"  type="checkbox" value ="0">
									<%} %>
										</TD>
										</TR>
										<TR>
										<TD class="plainlabel"><%=ChuyenNgu.get("IMEI",nnId)%><FONT class="erroralert">
											</FONT></TD>
											<TD class="plainlabel" >
											<input name="imei" id="imei" type="text" value="<%= ddkdBean.getImei()%>" ></TD>
										
											<TD class="plainlabel"><%=ChuyenNgu.get("Qu?? qu??n",nnId)%><FONT class="erroralert">
											</FONT></TD>
											<TD class="plainlabel"  >
											<input name="quequan" id="quequan" type="text" value="<%= ddkdBean.getQuequan()%>" ></TD>
											
											<TD class="plainlabel"></TD>
											<TD class="plainlabel" colspan="7"></TD>
										</TR>
										<TR>
											<TD class="plainlabel"><%=ChuyenNgu.get("??i???n tho???i",nnId)%><FONT
												class="erroralert">*</FONT></TD>
											<TD class="plainlabel"><input name="DienThoai"
												maxlength="50" id="DienThoai" type="text"
												value="<%= ddkdBean.getSodt()%>"></TD>

											<TD class="plainlabel"><%=ChuyenNgu.get("Email",nnId)%></TD>
											<TD class="plainlabel" colspan="5"><input name="email" id="email"
												type="text" value="<%= ddkdBean.getEmail()%>"></TD>
										</TR>

										<TR>
											<TD class="plainlabel"><%=ChuyenNgu.get("Ng??y v??o l??m",nnId)%> <FONT
												class="erroralert">*</FONT></TD>
											<TD class="plainlabel"><input
												readonly="readonly" class="days" name="ngaybatdau"
												id="ngaybatdau" type="text"
												value="<%= ddkdBean.getNgaybatdau()%>"></TD>
											<TD class="plainlabel">Ng??y k???t th??c <FONT
												class="erroralert">*</FONT></TD>
											<TD  class="plainlabel" colspan="5"><input
												readonly="readonly" class="days" name="ngayketthuc"
												id="ngayketthuc" type="text"
												value="<%= ddkdBean.getNgayketthuc()%>"></TD>
										</TR>
										<TR>
											<TD class="plainlabel" ><%=ChuyenNgu.get("Nh?? ph??n ph???i",nnId)%>
												<FONT class="erroralert">*</FONT>
											</TD>
											<TD  class="plainlabel" ><SELECT name="nppId"
												id="nppId" onChange="submitform()">
													<option value=""></option>
													<% if (npp != null){
										  try
										  { 
											  String optionGroup = "";
											  String optionName = "";
											  int i = 0;
											  
											  while(npp.next())
											  { 
												 if (i == 0)
												 {
													 optionGroup = npp.getString("kvTen");
													 optionName = npp.getString("kvTen");
													 
													 %>

													<optgroup label="<%= optionName %>">
														<% }
												 
												 optionGroup = npp.getString("kvTen");
												 if (optionGroup.trim().equals(optionName.trim()))
												 { %>
														<% if (npp.getString("nppId").equals(nppId)) {%>
														<option value="<%= npp.getString("nppId") %>"
															selected="selected"><%= npp.getString("nppTen") %></option>
														<%} else { %>
														<option value="<%= npp.getString("nppId") %>"><%= npp.getString("nppTen") %></option>
														<%} %>
														<% }
												 else
												 {
													 %>
													</optgroup>
													<% optionName = optionGroup; %>
													<optgroup label="<%= optionName %>">
														<% if (npp.getString("nppId").equals(nppId)) {%>
														<option value="<%= npp.getString("nppId") %>"
															selected="selected"><%= npp.getString("nppTen") %></option>
														<%} else { %>
														<option value="<%= npp.getString("nppId") %>"><%= npp.getString("nppTen") %></option>
														<%} %>
														<% }
												 i++;
								     	 	  } 
											  %>
													</optgroup>
													<%npp.close(); 
								     	 }catch(java.sql.SQLException e){} } %>
											</SELECT></TD>
											
											<TD class="plainlabel" ><%=ChuyenNgu.get("K??nh b??n h??ng",nnId)%></TD>
											<TD class="plainlabel" colspan="5" >
												<input name="kbhId" type ="hidden" value="<%= ddkdBean.getKenhbanhang() %>" />
												<select name="kbhId" id="kbhId" disabled="disabled">
					                                 <option value =""> </option>  
					                             <%
					                             ResultSet kbhRs= ddkdBean.getKbhRs();
					                             if (kbhRs!=null){
					                            	 try{
						                            	 while (kbhRs.next()){                      				                       				
						                       				 if (ddkdBean.getKenhbanhang() != null && kbhRs.getString("pk_seq").equals(ddkdBean.getKenhbanhang())){ %>
						                       				<option value ="<%= kbhRs.getString("pk_seq") %>" selected="selected"> <%=kbhRs.getString("ten") %></option>
						                       				<%}else{ %>
						                       				<option value ="<%=kbhRs.getString("pk_seq") %>"> <%=kbhRs.getString("ten") %></option>
						                       				<%}     		
						                            	 }
					                            	 }
					                            	 catch (Exception e) {
					                            		 e.printStackTrace();
					                            	 }
					                             }
					                             %>
					                             </select>
											</TD>
											
										</TR>
										
										<TR>
											<TD width="130px" class="plainlabel" valign="top"><%=ChuyenNgu.get("M?? NV ERP",nnId)%> <FONT class="erroralert">*</FONT>
											</TD>
											<TD width="300px" colspan = "5" class="plainlabel" valign="top">
												<INPUT name="ddkdMaERP" maxlength="50" id="ddkdMaERP" type="text"
													value="<%= ddkdBean.getMaERP() %>" >
											</TD>
										</TR>
										
										
										
									
									</TABLE>
								</FIELDSET>
								
								
								<TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
									<TR>
										<TD colspan="4">
											<FIELDSET>
												<LEGEND class="legendtitle" style="color: black"><%=ChuyenNgu.get("Ch???n gi??m s??t",nnId)%></LEGEND>

												<TABLE border="0" width="100%" cellpadding="3"
													cellspacing="1">
													<TR class="tbheader">
														<TH width="13%"><%=ChuyenNgu.get("Gi??m s??t",nnId)%></TH>
														<TH width="13%"><%=ChuyenNgu.get("K??nh",nnId)%></TH>
														<TH width="13%"><%=ChuyenNgu.get("Ch???n",nnId)%></TH>
													</TR>

													<%
								int i = 0;
								String lightrow = "tblightrow";
								String darkrow = "tbdarkrow";
								try{
									if (gsbhList!=null)
										while(gsbhList.next()){ 
											if (i % 2 != 0) {%>
													<TR class=<%=lightrow%>>
														<%} else {%>
													
													<TR class=<%= darkrow%>>
														<%}%>
														<TD align="center"><div align="left"><%= gsbhList.getString("gsbhTen") %>
															</div></TD>
														<TD align="center"><div align="left"><%= gsbhList.getString("kbhTen") %>
															</div></TD>
														<% if (gsbhIds.contains(gsbhList.getString("id"))){ %>
														<TD align="center"><input name="gsbhList"
															id="gsbhList" type="checkbox"
															value="<%= gsbhList.getString("id") %>" checked></TD>
														<%}else{ %>
														<TD align="center"><input name="gsbhList"
															id="gsbhList" type="checkbox"
															value="<%= gsbhList.getString("id") %>"></TD>
														<%} %>
													</TR>

													<%i++;
								     	}
									else
									{
										System.out.println("GSBH la null");
									}
								}catch(java.sql.SQLException e){}
							  %>
												</TABLE>
											</FIELDSET>
										</TD>
									</TR>
								</TABLE>
								<TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
									<TR>
										<TD colspan="4">
											<FIELDSET>
												<LEGEND class="legendtitle" style="color: black"><%=ChuyenNgu.get("L???ch s???",nnId)%></LEGEND>

												<TABLE border="0" width="100%" cellpadding="3"
													cellspacing="1">
													<TR class="tbheader">
														<TH width="13%"><%=ChuyenNgu.get("M?? NPP",nnId)%></TH>
														<TH width="13%"><%=ChuyenNgu.get("T??n NPP",nnId)%></TH>
														<TH width="13%" style="display:none"><%=ChuyenNgu.get("Route",nnId)%></TH>
														<TH width="13%"><%=ChuyenNgu.get("M?? GSBH",nnId)%></TH>
														<TH width="13%"><%=ChuyenNgu.get("T??n GSBH",nnId)%></TH>
														<TH width="13%"><%=ChuyenNgu.get("Tr???ng th??i",nnId)%></TH>
														<TH width="13%"><%=ChuyenNgu.get("Th???i ??i???m",nnId)%></TH>
													</TR>

													<%
								i = 0;
								
								try{
									if (ddkdBean.getLogRs()!=null)
										while(ddkdBean.getLogRs().next()){ 
											
											String trangthai = "";
											if ( ddkdBean.getLogRs().getInt("trangthai") == 1 )
												trangthai  = ChuyenNgu.get("Ho???t ?????ng",nnId);
											else if ( ddkdBean.getLogRs().getInt("trangthai") == 0 )
												trangthai  = ChuyenNgu.get("Ng??ng ho???t ?????ng",nnId);
											if (i % 2 != 0) {%>
													<TR class=<%=lightrow%>>
														<%} else {%>
													
													<TR class=<%= darkrow%>>
														<%}%>
														<TD align="center">
															<div align="left"><%= ddkdBean.getLogRs().getString("MANPP") %></div>
														</TD>
														<TD align="center">
															<div align="left"><%= ddkdBean.getLogRs().getString("NPP") %></div>
														</TD>
														<TD align="center" style="display:none">
															<div align="left"><%= ddkdBean.getLogRs().getString("routeName") %></div>
														</TD>
														<TD align="center">
															<div align="left"><%= ddkdBean.getLogRs().getString("magsbh") %></div>
														</TD>
														<TD align="center">
															<div align="left"><%= ddkdBean.getLogRs().getString("gsbh") %></div>
														</TD>
														<TD align="center">
															<div align="left"><%= trangthai %></div>
														</TD>
														<TD align="center">
															<div align="left"><%= ddkdBean.getLogRs().getString("ThoiDiem") %></div>
														</TD>
													</TR>

													<%i++;
								     	}
									else
									{
										System.out.println("GSBH la null");
									}
								}catch(java.sql.SQLException e){}
							  %>
												</TABLE>
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
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</HTML>
<%
try{
	if (npp!=null){
		npp.close();
	}
	
	if (gsbhList!=null){
		gsbhList.close();
	}
	if (gsbhIds!=null){
		gsbhIds.clear();
	}
	ddkdBean.DBClose();
}catch(Exception er){
	
}

}%>