<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="geso.dms.center.beans.phanbokhuyenmai.IPhanbokhuyenmai"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="geso.dms.center.util.*"%>
<%@ page import="java.util.Hashtable"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ page import="java.text.NumberFormat"%>
<%@ page  import = "geso.dms.center.util.ChuyenNgu" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>
<% IPhanbokhuyenmai pbkmBean = (IPhanbokhuyenmai)session.getAttribute("pbkm"); %>
<% String schemeId = (String)session.getAttribute("schemeId"); %>
<% ResultSet scheme = (ResultSet)pbkmBean.getSchemeRS() ; %>
<% ResultSet schemeKogh = (ResultSet)pbkmBean.getSchemeKoGioiHanRs(); %>
<%
Hashtable<String, String> usedPro = (Hashtable<String, String>)pbkmBean.getusedPro(); 
ResultSet vungRs=pbkmBean.getVung();
ResultSet khuvucRs=pbkmBean.getKv();
ResultSet nppRs = pbkmBean.getNppRs();
ResultSet npp=pbkmBean.getPhanboRs();

NumberFormat formatter = new DecimalFormat("#,###,###");

int[] quyen = new  int[6];
quyen = util.Getquyen("PhanbokhuyenmaiSvl","", userId);

%>
<% String nnId = (String)session.getAttribute("nnId"); %>
<% if(nnId == null) {
 nnId = "vi";  
 }
 String url = util.getChuyenNguUrl("PhanbokhuyenmaiSvl","",nnId);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="java.sql.SQLException"%>
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<script type="text/javascript" language="JavaScript"
	src="../scripts/jquery.tools.min.js"></script>

<script type="text/javascript" src="../scripts/ajax.js"></script>
<LINK rel="stylesheet" type="text/css" media="screen"
	href="../css/tabs.css">
<LINK rel="stylesheet" type="text/css" media="screen"
	href="../css/tabs-panes.css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">

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

<SCRIPT language="JavaScript" type="text/javascript">

	function submitform()
	{
		document.forms[''].setAttribute('enctype', '', 0);
		var active =$(".tabs li.current").index();
		document.forms["pbkhForm"].activeTab.value =active;
	    document.forms['pbkhForm'].submit();
	}
	function dowload()
	{
		document.forms['pbkhForm'].action.value='xuatexcel';
		var active =$(".tabs li.current").index();
		document.forms["pbkhForm"].activeTab.value =active;
    document.forms['pbkhForm'].submit();
	}
	
	function search()
	{
		 document.forms['pbkhForm'].action.value='search';
			var active =$(".tabs li.current").index();
			document.forms["pbkhForm"].activeTab.value =active;
	    document.forms['pbkhForm'].submit();
	}
	function phanboCTKM_Ko_Gh()
	{
		 document.forms['pbkhForm'].action.value='phanbo';
			var active =$(".tabs li.current").index();
			document.forms["pbkhForm"].activeTab.value =active;
	    document.forms['pbkhForm'].submit();
	}
	
	function upload()
	{
		document.forms['pbkhForm'].setAttribute('enctype', "multipart/form-data", 0);
		var active =$(".tabs li.current").index();
		document.forms["pbkhForm"].activeTab.value =active;
	    document.forms['pbkhForm'].submit();
	}
	
	function sellectAll(cbId1,cbId2 )
	{
		 var chkAll_Lct = document.getElementById(cbId1);
		 var loaiCtIds = document.getElementsByName(cbId2);
		 

		 
		 if(chkAll_Lct.checked )
		 {
			 for(var i = 0; i < loaiCtIds.length; i++)
			 {
				/*  if(!loaiCtIds.item(i).disabled)
				{ */
					 loaiCtIds.item(i).checked = true;
				/*  }*/
			 }
		 }
		 else
		 {
			 for(var i = 0; i < loaiCtIds.length; i++)
			 {
				 loaiCtIds.item(i).checked = false;
			 }
		 }
	}
</SCRIPT>

<script>
	$(function() {
	 	$("ul.tabs").tabs("div.panes > div");
	});
	</script>


<script>
	$(document).ready(function() {

	    //When page loads...
	    $(".tab_content").hide(); //Hide all content
	    var index = $("ul.tabs li.current").show().index(); 
	    $(".tab_content").eq(index).show();
	    //On Click Event
	    $("ul.tabs li").click(function() {
	  
	        $("ul.tabs li").removeClass("current"); //Remove any "active" class
	        $(this).addClass("current"); //Add "active" class to selected tab
	        $(".tab_content").hide(); //Hide all tab content  
	        var activeTab = $(this).find("a").attr("href"); //Find the href attribute value to identify the active tab + content  
	        $(activeTab).show(); //Fade in the active ID content
	        return false;
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
<%if(!geso.dms.center.util.GlobalValue.getDevmode()){ %>
	<noscript> 
	 	<meta http-equiv="refresh" content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")  %>">
		<!-- //window.location = "https://www.google.com"; -->
	</noscript>
<%} %>
<!-- END RENDER AUTO -->

	<form name="pbkhForm" method="post" action="../../PhanbokhuyenmaiSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
		<input type="hidden" name="action" value="0"> <input
			type="hidden" name="flag" value="<%= pbkmBean.getFlag() %>">
			<input type="hidden" id="activeTab" name="activeTab" value='<%=pbkmBean.getActiveTab()%>'>
		<input type="hidden" name="userId" value="<%=userId %>">


		<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
			height="100%">
			<TR>
				<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
						<TR>
							<TD align="left" class="tbnavigation">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr height="22">
										<TD align="left" colspan="2" class="tbnavigation"><%=url %></TD>
										<TD colspan="2" align="right" class="tbnavigation"><%=ChuyenNgu.get("Chào mừng",nnId) %> <%= userTen %>&nbsp;
										</TD>
									</tr>
								</table>
							</TD>
						</TR>
					</TABLE>
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
						<TR>
							<TD>
								<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
									<TR class="tbdarkrow">
										<TD>&nbsp;</TD>
									</TR>
								</TABLE>
							</TD>
						</TR>
					</TABLE>
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
						<tr>
							<TD align="left" colspan="4" class="legendtitle">
								<FIELDSET>
									<LEGEND class="legendtitle"><%=ChuyenNgu.get("Báo lỗi nhập liệu",nnId) %> </LEGEND>

									<textarea name="dataerror"
										style="width: 100%; color: #F00; font-weight: bold"
										style="width:100%" rows="1" readonly="readonly"><%= pbkmBean.getMessage() %></textarea>

								</FIELDSET>
							</TD>
						</tr>

						<TR>
							<TD height="100%" width="100%">
								<FIELDSET>
									<LEGEND class="legendtitle" style="color: black"><%=ChuyenNgu.get("Phân bổ khuyến mãi",nnId) %> </LEGEND>
									<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
										<TR>
											<TD class="plainlabel" width="10%"><%=ChuyenNgu.get("Từ ngày",nnId) %></TD>
											<TD colspan="3" class="plainlabel">
												<table border=0 cellpadding="0" cellspacing="0">
													<TR>
														<TD><input class="days" type="text" name="tungay"
															id="tungay" style="width: 100%"
															value="<%= pbkmBean.getTungay() %>"></TD>
													</TR>
												</table>
										</TR>
										<TR>
											<TD class="plainlabel"><%=ChuyenNgu.get("Đến ngày",nnId) %></TD>
											<TD colspan="3" class="plainlabel">
												<table border=0 cellpadding="0" cellspacing="0">
													<TR>
														<TD><input class="days" type="text" name="denngay"
															id="denngay" style="width: 100%"
															value="<%= pbkmBean.getDenngay() %>"></TD>
													</TR>
												</table>
										</TR>
										<TR>
										<TR>
											<TD class="plainlabel"><%=ChuyenNgu.get("Vùng",nnId) %></TD>
											<TD class="plainlabel"><select name="vungId"
												onchange="search();" id="vungId" style="width: 350px;">
													<option value="" selected><%=ChuyenNgu.get("All",nnId) %></option>
													<%if (vungRs != null)
											while (vungRs.next()) {
												if (vungRs.getString("vungId").equals(pbkmBean.getVungId())) {%>
													<option value="<%=vungRs.getString("vungId")%>" selected><%=vungRs.getString("vungTen")%></option>
													<%} else {%>
													<option value="<%=vungRs.getString("vungId")%>"><%=vungRs.getString("vungTen")%></option>
													<%}}%>
											</select></TD>
										</TR>
										<TR>
											<TD class="plainlabel"><%=ChuyenNgu.get("Khu vực",nnId) %></TD>
											<TD class="plainlabel"><select name="khuvucId"
												onchange="search();" id="khuvucId" style="width: 350px;">
													<option value="" selected>All</option>
													<%if (khuvucRs != null)
											while (khuvucRs.next()) {
												if (khuvucRs.getString("khuvucId").equals(pbkmBean.getKvId()  )) {%>
													<option value="<%=khuvucRs.getString("khuvucId")%>"
														selected><%=khuvucRs.getString("khuvucTen")%></option>
													<%} else {%>
													<option value="<%=khuvucRs.getString("khuvucId")%>"><%=khuvucRs.getString("khuvucTen")%></option>
													<%}}%>
											</select></TD>
										</TR>
										<TR>
											<TD class="plainlabel"><%=ChuyenNgu.get("Nhà phân phối",nnId) %></TD>
											<TD class="plainlabel">
												<select name="nppIdSearch" onchange="search();" id="nppIdSearch" style="width: 350px;">
													<option value="" selected><%=ChuyenNgu.get("All",nnId) %></option>
													<%if (nppRs != null)
											while (nppRs.next()) {
												//System.out.println("npp "+ pbkmBean.getNppIdSearch());
												if (nppRs.getString("PK_SEQ").equals(pbkmBean.getNppIdSearch())) {%>
													<option value="<%=nppRs.getString("PK_SEQ")%>" selected><%=nppRs.getString("TEN")%></option>
													<%} else {%>
													<option value="<%=nppRs.getString("PK_SEQ")%>"><%=nppRs.getString("TEN")%></option>
													<%}}%>
											</select></TD>
										</TR>
										<TR>
											<TD class="plainlabel"><%=ChuyenNgu.get("Ngân sách",nnId) %></TD>
											<TD colspan="2" class="plainlabel"><SELECT
												name="loaingansach" id="loaingansach" class="legendtitle">
													<option value=""></option>
													<% 											 
										if(pbkmBean.getLoaingansach().equals("1")){ %>
													<option value='1' selected><%=ChuyenNgu.get("Giới hạn",nnId) %></option>
													<%}else{ %>
													<option value='1'><%=ChuyenNgu.get("Giới hạn",nnId) %></option>
													<%} %>
													<%		if(pbkmBean.getLoaingansach().equals("0")){ %>
													<option value='0' selected><%=ChuyenNgu.get("Không giới hạn",nnId) %></option>
													<%}else{ %>
													<option value='0'><%=ChuyenNgu.get("Không giới hạn",nnId) %></option>
													<%} %>
											</SELECT></TD>
										</TR>

										<TR>
											<TD class="plainlabel"><%=ChuyenNgu.get("Tình trạng",nnId) %></TD>
											<TD colspan="2" class="plainlabel"><SELECT name="phanbo"
												id="phanbo" class="legendtitle">
													<option value=""></option>
													<% 											 
										if(pbkmBean.getPhanbo().equals("1")){ %>
													<option value='1' selected><%=ChuyenNgu.get("Đã phân bổ",nnId) %></option>
													<%}else{ %>
													<option value='1'><%=ChuyenNgu.get("Đã phân bổ",nnId) %></option>
													<%} %>
													<%		if(pbkmBean.getPhanbo().equals("0")){ %>
													<option value='0' selected><%=ChuyenNgu.get("Chưa phân bổ",nnId) %></option>
													<%}else{ %>
													<option value='0'><%=ChuyenNgu.get("Chưa phân bổ",nnId) %></option>
													<%} %>
											</SELECT></TD>
										</TR>


										<TR>
											<TD class="plainlabel"><%=ChuyenNgu.get("Chọn tập tin",nnId) %></TD>
											<TD class="plainlabel"><INPUT type="file"
												name="filename" size="40" value=''>&nbsp;&nbsp;&nbsp;&nbsp;
												<a class="button2" href="javascript:upload();"><img
													style="top: -4px;" src="../images/button.png" alt=""><%=ChuyenNgu.get("Upload",nnId) %></a>
											</TD>
										</TR>
										<TR>
											<td class="plainlabel" colspan="2"><a class="button2"
												href="javascript:search();"> <img style="top: -4px;"
													src="../images/button.png" alt=""><%=ChuyenNgu.get("1.Lọc chương trình khuyến mãi",nnId) %>
											</a>&nbsp;&nbsp;&nbsp;&nbsp;<a class="button2"
												href="javascript:search();"><img style="top: -4px;"
													src="../images/button.png" alt=""><%=ChuyenNgu.get("2.Thêm nhà phân phối",nnId) %></a>&nbsp;&nbsp;&nbsp;&nbsp;
													<%if(quyen[2]!=0 && quyen[0]!= 0){ %>
													 <a class="button2"
												href="javascript:phanboCTKM_Ko_Gh();"><img
													style="top: -4px;" src="../images/button.png" alt=""><%=ChuyenNgu.get("3.Lưu phân bổ chương trình khuyến mãi",nnId) %></a>&nbsp;&nbsp;&nbsp;&nbsp; 
												<%} %>
											
											 <a class="button2"
												href="javascript:dowload();"><img
													style="top: -4px;" src="../images/button.png" alt=""><%=ChuyenNgu.get("4.Download phân bổ chương trình khuyến mãi",nnId) %></a>&nbsp;&nbsp;&nbsp;&nbsp; 	
													
											</TD>
										</TR>
										<TR>
											<td class="plainlabel" colspan="2">&nbsp;</td>
										</TR>
									</TABLE>
								</FIELDSET>
							</TD>
						</TR>
					</TABLE>

					<ul class="tabs">
						<li <%=pbkmBean.getActiveTab().equals("0") ? "class='current'" : ""%>><a href="#tab1"><%=ChuyenNgu.get("Chương trình khuyến mãi",nnId) %></a></li>
						<li <%=pbkmBean.getActiveTab().equals("1") ? "class='current'" : "" %>><a href="#tab2"><%=ChuyenNgu.get("Nhà phân phối",nnId) %> </a></li>
					</ul>

					<div class="panes">
						<div id="tab1" class="tab_content">

							<TABLE class="tabledetail" width="100%" border="0"
								cellspacing="1px" cellpadding="5px">
								<TR class="tbheader">
									<TH align="center" width="10%"><%=ChuyenNgu.get("Scheme",nnId) %></TH>
									<TH align="left" width="30%"><%=ChuyenNgu.get("Diễn giải",nnId) %></TH>
									<TH align="left" width="10%"><%=ChuyenNgu.get("Từ ngày",nnId) %></TH>
									<TH align="left" width="10%"><%=ChuyenNgu.get("Đến ngày",nnId) %></TH>
									<TH align="center" width="15%"><%=ChuyenNgu.get("Ngân sách",nnId) %></TH>
									<TH align="center" width="10%"><%=ChuyenNgu.get("Chọn tất cả",nnId) %> <input
										type="checkbox" id="cbCtkmId" name="all"
										onclick="sellectAll('cbCtkmId','ctkmId')"></TH>
								</TR>
								<%      int k=0;
                    if(scheme != null)
                    {
                    	System.out.println(":::: SCHEME KHAC NULL..........");
                    	while(scheme.next())
                    	{
                    		String color="style=\"line-height:30px\"";
                    		if(scheme.getString("DaPhanBo").equals("1"))
                    			color="style=\"line-height:30px;color:red\"";
                    		
                    			if(k % 2 == 0){
                        			%>
								<TR class='tbdarkrow' <%=color %>>
									<%}else{ %>
								
								<TR class='tblightrow' <%=color %>>
									<% } %>
									<TD align="left"><%=scheme.getString("SCHEME")%></TD>
									<TD align="left"><%=scheme.getString("DIENGIAI")%></TD>
									<TD align="left"><%=scheme.getString("TUNGAY")%></TD>
									<TD align="left"><%=scheme.getString("DENNGAY")%></TD>
									<TD align="left">
										<%if(scheme.getString("loaingansach").equals("0")){ %> Không
										giới hạn <%}else  {%> Giới hạn <%} %>
									</TD>
									<TD align="center">
										<%if(  pbkmBean.getSchemeId().indexOf(scheme.getString("pk_seq") )>=0  ){ %>
										<input type="checkbox" name="ctkmId"
										value="<%=scheme.getString("pk_seq")%>" checked="checked">
										<%}else { %> <input type="checkbox" name="ctkmId"
										value="<%=scheme.getString("pk_seq")%>"> <% 	} %>
									</TD>
								</TR>
								<%	k++;
                    	} }%>

							</TABLE>

						</div>


						<div id="tab2" class="tab_content">
							<TABLE border="0" width="100%" cellpadding="6" cellspacing="1">
								<TR>
									<TH width="20%" class="plainlabel"><%=ChuyenNgu.get("Scheme",nnId) %></TH>
									<TH width="10%" class="plainlabel"><%=ChuyenNgu.get("Mã nhà phân phối",nnId) %></TH>
									<TH width="20%" class="plainlabel"><%=ChuyenNgu.get("Tên nhà phân phối",nnId) %></TH>
									<TH width="20%" class="plainlabel"><%=ChuyenNgu.get("Khách hàng",nnId) %></TH>
									<TH width="10%" class="plainlabel"><%=ChuyenNgu.get("Ngân sách",nnId) %></TH>
									<TH width="10%" class="plainlabel"><%=ChuyenNgu.get("Đã sử dụng",nnId) %></TH>
									<TH width="10%" class="plainlabel"><%=ChuyenNgu.get("Còn lại",nnId) %></TH>

									<% try{
							    String lightrow = "tblightrow";
								String darkrow = "tbdarkrow";
								int m = 0;
								if(npp != null){
									while(npp.next()){ 
									if (m % 2 != 0) {%>
								
								<TR class=<%=lightrow%>>
									<%} else {%>
								
								<TR class=<%= darkrow%>>
									<%}%>
									<TD align="left"><input type="text" readonly="readonly"
										style="width: 100%"
										value="<%= npp.getString("SCHEME")%>__<%= npp.getString("DIENGIAI")%>">
										<input type="hidden" name="ctkmId.pb" readonly="readonly"
										style="width: 100%" value="<%= npp.getString("ctkm_fk")%>">
									</TD>
									<TD align="left"><input type="text" readonly="readonly"
										style="width: 100%" value="<%= npp.getString("NPPma")%>">
										<input type="hidden" name="nppId" readonly="readonly"
										style="width: 100%" value="<%= npp.getString("nppId")%>">
										<input type="hidden" name="loaingansach.pb"
										readonly="readonly" style="width: 100%"
										value="<%= npp.getString("loaingansach")%>"></TD>
									<TD align="left"><input type="text" readonly="readonly"
										style="width: 100%" value="<%=npp.getString("NPPten") %>">
									</TD>
									<TD align="left"><input type="text" readonly="readonly"
										style="width: 100%" value="<%=npp.getString("KHten") %>">
									</TD>
									<TD align="left"><input type="text" name="ngansach"
										<%=(npp.getInt("loaingansach")==0?"readonly=\"readonly\"":"") %>
										style="width: 100%"
										value="<%= npp.getDouble("ngansach")==99999999999.0 ? "Không giới hạn" : formatter.format(npp.getDouble("ngansach") ) %>">
									</TD>
									<TD align="left"><input type="text" readonly="readonly"
										style="width: 100%"
										value="<%= formatter.format(npp.getDouble("dasudung") )%>">
									</TD>
									<TD align="left"><input type="text" readonly="readonly"
										style="width: 100%"
										value="<%=formatter.format(npp.getDouble("ngansach")-npp.getDouble("dasudung")) %>">
									</TD>
								</TR>

								<% m++ ;} 
						  		
						  		}%>

								<%}catch(Exception e){e.printStackTrace();}%>
							</TABLE>
						</div>
					</div>
				</TD>
			</TR>
		</TABLE>
	</form>
</body> 
</HTML>

<%
	if(scheme!= null){ scheme.close(); scheme = null; }
	if(schemeKogh!= null){ schemeKogh.close(); schemeKogh = null; }
	if(usedPro!= null){ usedPro.clear(); usedPro = null; }
	if(vungRs!= null){ vungRs.close(); vungRs = null; }
	if(khuvucRs!= null){ khuvucRs.close(); khuvucRs = null; }
	if(nppRs!= null){ nppRs.close(); nppRs = null; }
	if(npp!= null){ npp.close(); npp = null; }

	pbkmBean.DBClose(); pbkmBean=null;	
	session.setAttribute("pbkm",null); 
}%>
