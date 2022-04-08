<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="geso.dms.distributor.beans.chuyenkhonew.IChuyenKho"%>
<%@ page import="geso.dms.distributor.beans.chuyenkhonew.ISanPham"%>
<%@ page import="geso.dms.distributor.beans.chuyenkhonew.imp.ChuyenKho"%>
<%@ page import="java.util.Hashtable"%>
<%@ page import="java.util.List"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ page import="java.text.NumberFormat"%>
<%@page import="java.util.Enumeration"%>
<%@ page import="geso.dms.center.util.*"%>
<%
	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if (!util.check(userId, userTen, sum))
	{
		response.sendRedirect("/AHF/index.jsp");
	} else
	{
%>

<%
	IChuyenKho ddhBean = (IChuyenKho) session.getAttribute("ckBean");
%>
<%
	ResultSet ncc = (ResultSet) ddhBean.getNccRs();
%>
<%
String nnId = (String)session.getAttribute("nnId"); %> 
<% if(nnId == null) {
	nnId = "vi"; 
	
	}
	ResultSet dvkd = (ResultSet) ddhBean.getDvkdRs();
	ResultSet kbh = (ResultSet) ddhBean.getKbhRs();
	ResultSet kho = (ResultSet) ddhBean.getKhoRs();
	List<ISanPham> spList = (List<ISanPham>) ddhBean.getSpList();
	Hashtable<String, String> dvdlList = ddhBean.getDvdlList();
	
	
	String url ="";
	if(ddhBean!= null && ddhBean.getView().trim().length() >0)
		url =  util.getChuyenNguUrl("ChuyenKhoSvl","&view="+ddhBean.getView(),nnId);	
	else
		url =  util.getChuyenNguUrl("ChuyenKhoSvl","",nnId);	
	
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<HTML>
<HEAD>
<TITLE>AHF - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
<LINK rel="stylesheet" type="text/css" media="print"
	href="../css/impression.css">
<script type="text/javascript" src="../scripts/DocTienTiengViet.js"></script>
<style type="text/css">
	#mainContainer{
		width:660px;
		margin:0 auto;
		text-align:left;
		height:100%;
		border-left:3px double #000;
		border-right:3px double #000;
	}
	#formContent{
		padding:5px;
	}
	/* END CSS ONLY NEEDED IN DEMO */
		
	/* Big box with list of options */
	#ajax_listOfOptions{
		position:absolute;	/* Never change this one */
		width:auto;	/* Width of box */
		height:200px;	/* Height of box */
		overflow:auto;	/* Scrolling features */
		border:1px solid #317082;	/* Dark green border */
		background-color:#C5E8CD;	/* White background color */
    	color: black;
		text-align:left;
		font-size:1.0em;
		z-index:100;
	}
	#ajax_listOfOptions div{	/* General rule for both .optionDiv and .optionDivSelected */
		margin:1px;		
		padding:1px;
		cursor:pointer;
		font-size:1.0em;
	}
	#ajax_listOfOptions .optionDiv{	/* Div for each item in list */
		
	}
	#ajax_listOfOptions .optionDivSelected{ /* Selected item in the list */
		background-color:#317082; /*mau khi di chuyen */
		color:#FFF;
	}
	#ajax_listOfOptions_iframe{
		background-color:#F00;
		position:absolute;
		z-index:5;
	}
	form{
		display:inline;
	}
	#dhtmltooltip
	{
		position: absolute;
		left: -300px;
		width: 150px;
		border: 1px solid black;
		padding: 2px;
		background-color: lightyellow;
		visibility: hidden;
		z-index: 100;
		/*Remove below line to remove shadow. Below line should always appear last within this CSS*/
		filter: progid:DXImageTransform.Microsoft.Shadow(color=gray,direction=135);
	}	
	#dhtmlpointer
	{
		position:absolute;
		left: -300px;
		z-index: 101;
		visibility: hidden;
	}	
	
}
</style>




<script type="text/javascript" src="../scripts/ajax.js"></script>
<script type="text/javascript" src="../scripts/AjaxChuyenKho.js"></script>
<script type="text/javascript" src="../scripts/formattien.js"></script>
<script type="text/javascript" language="JavaScript" src="../scripts/Numberformat.js"></script>
<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js"type="text/javascript"></script>
<script>
	$(document).ready(function() 
	{
		$(".days").datepicker(
		{
			changeMonth : true,
			changeYear : true
		});
	});
</script>
<style type="text/css">
#tab tr td input {
	cursor: default;
	background-repeat: no-repeat;
	background: none;
}

#tab tr:HOVER {
	cursor: pointer;
	background: #E2F0D9;
}
</style>
<script>
	function replaces()
	{	
		var spId = document.getElementsByName("spId");
		var masp = document.getElementsByName("spMa");
		var tensp = document.getElementsByName("spTen");
		var donvitinh = document.getElementsByName("dvdlId");
		var dongia = document.getElementsByName("dongia");
		var soluong = document.getElementsByName("soluong");
		var thanhtien = document.getElementsByName("thanhtien");
		var tonkho = document.getElementsByName("tonkho");
		var quycach = document.getElementsByName("quycach");			
		var i;
		for(i=0; i < masp.length; i++)
		{
			if(masp.item(i).value != "")
			{
				var sp = masp.item(i).value;
				var pos = parseInt(sp.indexOf(" - "));
				if(pos > 0)
				{
					masp.item(i).value = sp.substring(0, pos);
					sp = sp.substr(parseInt(sp.indexOf(" - ")) + 3);
					tensp.item(i).value = sp.substring(0, parseInt(sp.indexOf("[")));
					sp = sp.substr(parseInt(sp.indexOf(" [")) + 2);
					donvitinh.item(i).value = sp.substring(0, parseInt(sp.indexOf("] [")));
					sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
					var valuegia= sp.substring(0, parseInt(sp.indexOf("] [")));
					dongia.item(i).value =formattien(valuegia);
					sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
				    spId.item(i).value= sp.substring(0, parseInt(sp.indexOf("]")));
				    
				    sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
				    tonkho.item(i).value= sp.substring(0, parseInt(sp.indexOf("]")));
				    
				    sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
				    quycach.item(i).value= sp.substring(0, parseInt(sp.indexOf("]")));
				}
				var so_luong=soluong.item(i).value.replace(",","");
				if(parseInt(so_luong) > 0)
				{				
					var don_gia = dongia.item(i).value;
					while(don_gia.match(","))
					{
						don_gia=don_gia.replace(",","");
						
					}
					var tt = parseFloat(so_luong) * parseFloat(don_gia);
					thanhtien.item(i).value = formattien(tt);
				}
				else			
				{
					thanhtien.item(i).value = "";
				}
			}
			else
			{
				tensp.item(i).value = "";
				donvitinh.item(i).value = "";
				dongia.item(i).value = "";
				soluong.item(i).value = "";
				thanhtien.item(i).value = "";
			}
			TinhTien();
		}	
		setTimeout(replaces, 20);
	}

	function TinhTien()
	{
	
		var thuevat=document.getElementById("thuevat").value;
		var thanhtien = document.getElementsByName("thanhtien");
		var tongtien = 0;
		var tongtienbvat = 0;
		for(var i=0; i < thanhtien.length; i++)
		{
			if(thanhtien.item(i).value != "")
			{
				var thanh_tien = thanhtien.item(i).value.replace(",", "");
				while(thanh_tien.match(","))
				{
					thanh_tien=thanh_tien.replace(",","");
				}
				tongtien = parseFloat(tongtien) +  parseFloat(thanh_tien);
			}
		}
		document.getElementById("tienBVAT").value = formatCurrency(tongtien);
		var vat = Math.round((tongtien*thuevat)/100);
		document.getElementById("vat").value = formatCurrency(vat);
		var tongtienavat =Math.round(tongtien)+ Math.round((tongtien*thuevat)/100);
		document.getElementById("tienAVAT").value = formatCurrency(tongtienavat);	
		document.getElementById('lblDocSo').innerHTML = DocTienBangChu(tongtienavat) + " Đồng Việt Nam";		
	}
	function LuuFile(name)
	{
		 document.forms["ddhForm"].action.value = "download";
		 document.forms["ddhForm"].tenfile.value = name;
		 document.forms["ddhForm"].submit();
	}
	function QuyRaLe(pos)
	{
		var spMa = document.getElementsByName("spMa");
		var dvdlId = document.getElementsByName("dvdlId");
		var soluongChuan = document.getElementsByName("soluongchuan");
		var soluong = document.getElementsByName("soluong");
		
		if( soluongChuan.item(pos).value != '' && dvdlId.item(pos).value != '' )
		{
			var xmlhttp;
			if (window.XMLHttpRequest)
			{
			   xmlhttp = new XMLHttpRequest();
			}
			else
			{
			   xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
			}
			
			xmlhttp.onreadystatechange=function()
			{
			   if (xmlhttp.readyState == 4 && xmlhttp.status == 200)
			   {
				   soluong.item(pos).value = xmlhttp.responseText;
			   }
			}
			xmlhttp.open("POST","../../AjaxDonNhapHang?type=QuyDoi&spMa=" + spMa.item(pos).value + "&dvdlId=" + dvdlId.item(pos).value + "&soluongchuan=" + soluongChuan.item(pos).value,true);
			xmlhttp.send();
		}
		else
		{
			soluong.item(pos).value = "0";
		}
	}	
	
	function saveform()
	{
		var masp = document.getElementsByName("spMa");
		var tensp = document.getElementsByName("spTen");
		var soluong = document.getElementsByName("soluong");
// 		var giatri= document.forms['ddhForm'].ngaynhap.value ;
// 		if(giatri=="")
// 		{
// 			document.forms['ddhForm'].err.value ="Vui lòng chọn ngày nhập hàng !";
// 			return;
// 		}
		document.forms['ddhForm'].action.value='save';
	    document.forms["ddhForm"].submit();
	}
	
	function submitForm()
	{
	    document.forms["ddhForm"].submit();
	}
	
	
	function congDonSPCungMa()  //Khong cong don, chi cho phep SP trong don hang co 1 don vi theo tung dong
	 {
		var masp = document.getElementsByName("spMa");
		var soluong = document.getElementsByName("soluong");
		var ii;
		for(ii = 0; ii < (masp.length - 1) ; ii++)
		{
			for(var j = ii + 1; j < masp.length; j++)
			{
				if(masp.item(ii).value != "" && masp.item(ii).value == masp.item(j).value)
				{		
					return false;
				}
			}
		}
	 	return true;
	 }
	 
	 
	 function DinhDangTien(num) 
		{
		    num = num.toString().replace(/\$|\,/g,'');
		    if(isNaN(num))
		    num = "0";
		    sign = (num == (num = Math.abs(num)));
		    num = Math.floor(num*100+0.50000000001);
		    num = Math.floor(num/100).toString();
		    for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
		    num = num.substring(0,num.length-(4*i+3))+','+
		    num.substring(num.length-(4*i+3));
		    return (((sign)?'':'-') + num);
		}
		
		function Dinhdang(element)
		{
			element.value = DinhDangTien(element.value);
			if(element.value== '' )
			{
				element.value= '';
			}
		}	
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

	<form name="ddhForm" method="post" action="../../ChuyenKhoUpdateSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
		<INPUT name="userId" type="hidden" value='<%=userId%>'> 
		<input type="hidden" name="action" value='1'>
		<input type="hidden" name="nppId" value='<%= ddhBean.getNppId()%>'>
		<input type="hidden" name="id" value='<%= ddhBean.getId()%>'>
			<input
			type="hidden" name="action" value='1'> <input type="hidden"
			name="thuevat" id="thuevat" value='<%=ddhBean.getThuevat()%>'>
		<input type="hidden" name="removename" value='0' />
	<input type = "hidden" name = "tenfile" value = "">
		<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
			height="100%" bgcolor="#FFFFFF">
			<TR>
				<TD colspan="4" align='left' valign='top'>

					<TABLE width="100%">
						<TR>
							<TD align="left" class="tbnavigation">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr height="22">
										<TD align="left" colspan="2" class="tbnavigation"><%=url %> &gt; Hiển thị </TD>										
										<TD colspan="2" align="right" class="tbnavigation">Chào mừng Bạn <%= userTen %>&nbsp;
										</TD>
									</tr>
								</table>
							</TD>
						</TR>
					</TABLE>

					<TABLE width="100%" border="0" cellpadding="3" cellspacing="0">
						<TR>
							<TD>
								<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
									<TR class="tbdarkrow">

										<TD width="30" align="left">
											<A href="javascript:history.back()"> <img src="../images/Back30.png" alt="Quay ve" title="Quay ve" border="1" longdesc="Quay ve" style="border-style: outset"></A>
										</TD>
										<TD width="2" align="left">&nbsp;</TD>
<!-- 										<TD width="30" align="left"> -->
<%-- 										<% if (  (Integer.parseInt(ddhBean.getTrangThai()) <=1)  && !ddhBean.getView().equals("TT") ){ %> --%>
<!-- 											<A href="javascript: saveform()"><img src="../images/Save30.png" alt="Luu lai" title="Luu lai"border="1" longdesc="Luu lai" style="border-style: outset"></A> -->
<%-- 										<%} %> --%>
										</TD>
										<TD width="2" align="left">&nbsp;</TD>
										<TD width="30" align="left">
										<% if (  (Integer.parseInt(ddhBean.getTrangThai()) == 0 )  ){ %>
<%-- 											<A href = "../../ChuyenKhoSvl?userId=<%=userId%>&GoiLenTrungTam=<%= ddhBean.getDhId() %>"><img  onclick="if(!confirm('Bạn muốn gởi đơn chuyển kho này lên TT không ?')) return false;"  src="../images/Chot.png" alt=C title="Gởi"longdesc=" Gởi " border = 0 ></A> --%>
										<% } else if (Integer.parseInt(ddhBean.getTrangThai()) == 1 && ddhBean.getView().equals("TT")) { %>
											<A href = "../../ChuyenKhoSvl?userId=<%=userId%>&Chot=<%= ddhBean.getDhId() %>"><img onclick="if(!confirm('Bạn muốn chốt đơn chuyển kho này không ?')) return false;"  src="../images/Chot.png" alt=C title="Chốt" width = "40" height = "40" longdesc=" Chốt " border = 0 ></A>												
										<% } %>
										<TD align="left">&nbsp;</TD>
									</TR>
								</TABLE>
							</TD>
						</TR>

					</TABLE>

					<TABLE width=100% cellpadding="0" cellspacing="0">
						<tr>
							<TD align="left" colspan="4" class="legendtitle">
								<FIELDSET>
									<LEGEND class="legendtitle">Thông báo </LEGEND>
									<textarea name="err" cols="96" rows="1" disabled><%=ddhBean.getMsg()%></textarea>
									<%
										ddhBean.setMsg("");
									%>
								</FIELDSET>
							</TD>
						</tr>

						<TR>
						<TR>
							<TD>
								<TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
									<TR>
										<TD align="left">
											<FIELDSET>
												<%
													NumberFormat formatter = new DecimalFormat("#,###,###");
												%>
											  <LEGEND class="legendtitle">&nbsp;Chuyển kho
													&nbsp;</LEGEND>
												<TABLE cellpadding=4 cellspacing=0 width="100%" border=0>
													<TR>
														<TD width="15%" class="plainlabel"> Nhà phân phối </TD>
														<TD width="30%" class="plainlabel"><%= ddhBean.getNppCk() %>
														<TD class="plainlabel">Ngày điều chỉnh</TD>
														<td class="plainlabel">
															<input class="days" type="text" name="ngayDc" size="11" value="<%= ddhBean.getNgayDc() %>">
														</td>
													</TR>

													<TR>
														<TD class="plainlabel">Nhà cung cấp</TD>
														<TD width="30%" class="plainlabel"><select
															name="nccId">
																<%
																	try
																		{
																			if (ncc != null)
																			{
																				while (ncc.next())
																				{
																					if (ncc.getString("nccId").equals(ddhBean.getNccId()))
																					{
																%>
																<option value='<%=ncc.getString("nccId")%>' selected><%=ncc.getString("nccTen")%></option>

																<%
																	} else
																					{
																%>
																<option value='<%=ncc.getString("nccId")%>'><%=ncc.getString("nccTen")%></option>
																<%
																	}
																				}
																			}
																		} catch (java.sql.SQLException e)
																		{
																			e.printStackTrace();
																		}
																%>
														</select></TD>
														<TD class="plainlabel">Tổng số tiền (chưa VAT)</TD>
														<TD class="plainlabel">
															<input type="text" name="tienBVAT" id="tienBVAT" readonly value="<%=ddhBean.getTienBVAT()%>" style="text-align: right">VND
														</TD>
														
													</TR>

													<TR>
														<TD width="15%" class="plainlabel">Đơn vị kinh doanh
														</TD>
														<TD class="plainlabel">
														<SELECT name="dvkdId" style="width: 80" onChange="submitForm();">
																<%
																	try
																		{
																			while (dvkd.next())
																			{
																				if (dvkd.getString("dvkdId").equals(ddhBean.getDvkdId()))
																				{
																%>
																<option value='<%=dvkd.getString("dvkdId")%>' selected><%=dvkd.getString("dvkd")%></option>
																<%
																	} else
																				{
																%>
																<option value='<%=dvkd.getString("dvkdId")%>'><%=dvkd.getString("dvkd")%></option>
																<%
																	}
																			}
																		} catch (java.sql.SQLException e)
																		{
																		}
																%>
														</select></TD>
														
														<TD class="plainlabel">VAT (<%=ddhBean.getThuevat()%>%)</TD>
													  <TD colspan="3" class="plainlabel">
													  	<input type="text"	name="vat" id="vat" value="<%=ddhBean.getVat()%>" readonly style="text-align: right"> VND
													    <input type="hidden" name="thuesuat" id="thuesuat" value="0.1" readonly style="text-align: right">
													   </TD>
														
													</TR>
																				
													<TR class="tblightrow">
													<TD class="plainlabel">Kênh bán hàng </TD>
								  				 		<TD class="plainlabel"><SELECT name="kbhId" onchange="submitForm();" >
													<%  try
													{
									  						while(kbh.next())
									  						{								  			
									  							if (ddhBean.getKbhId().equals(kbh.getString("kbhId")))
									  							{ %>								  			
									  								<option value="<%= kbh.getString("kbhId")%>" selected><%= kbh.getString("kbh") %></option>
									  		  				  <%}else{ %>		
									  		  						<option value="<%= kbh.getString("kbhId")%>" ><%= kbh.getString("kbh") %></option>
									  						  <%}
									  					    }
								  					}catch (java.sql.SQLException e){e.printStackTrace();} %>
                                  						</SELECT></TD>  
													 <TD class="plainlabel">Tổng số tiền (có VAT)</TD>
														<TD colspan="3" class="plainlabel">
															<input type="text" name="tienAVAT" id="tienAVAT" readonly value="<%=ddhBean.getTienAVAT()%>"style="text-align: right"> VND
														</TD>
                                  						
												  </TR>
												  <TR class="tblightrow">
												   <TD class="plainlabel">Kho chuyển</TD>
								  				 		<TD class="plainlabel"><SELECT name="khochuyenId" onChange="submitForm();" >
								  						<option value="" > </option>
													<%  try
													{
														kho.beforeFirst();
									  						while(kho.next())
									  						{								  			
									  							if (ddhBean.getKhochuyenId().equals(kho.getString("khoId")))
									  							{ %>								  			
									  								<option value="<%= kho.getString("khoId")%>" selected><%= kho.getString("ten")+"-"+ kho.getString("diengiai")%></option>
									  		  				  <%}else{ %>		
									  		  						<option value="<%= kho.getString("khoId")%>" ><%= kho.getString("ten")+"-"+kho.getString("diengiai")%></option>
									  						  <%}
									  					    }
								  					}catch (java.sql.SQLException e){e.printStackTrace();} %>
                                  						</SELECT></TD>  
												  <TD class="plainlabel"></TD>
												  <TD class="plainlabel"></TD>
												  </TR>
												  
												  <TR class="tblightrow">
												   <TD class="plainlabel">Kho nhận</TD>
								  				 		<TD class="plainlabel"><SELECT name="khonhanId" >
								  						<option value="" > </option>
													<%  try
													{
														kho.beforeFirst();
									  						while(kho.next())
									  						{								  			
									  							if (ddhBean.getKhonhanId().equals(kho.getString("khoId")))
									  							{ %>								  			
									  								<option value="<%= kho.getString("khoId")%>" selected><%= kho.getString("ten")+"-"+ kho.getString("diengiai")%></option>
									  		  				  <%}else{ %>		
									  		  						<option value="<%= kho.getString("khoId")%>" ><%= kho.getString("ten")+"-"+kho.getString("diengiai")%></option>
									  						  <%}
									  					    }
								  					}catch (java.sql.SQLException e){e.printStackTrace();} %>
                                  						</SELECT></TD>  
												  <TD class="plainlabel"></TD>
												  <TD class="plainlabel"></TD>
												  </TR>
												  
													<TR class="tblightrow">
														<TD class="plainlabel">Ghi chú đơn hàng</TD>
														<TD colspan="6" class="plainlabel">
															<textarea name="ghichu" id="ghichu" style="width: 100%" rows="2"><%=ddhBean.getGhichu()%></textarea>
														</TD>
													</TR>
													<TR class="tblightrow">
														<TD class="plainlabel">Bằng chữ</TD>
														<TD colspan="3" class="plainlabel">
															<span id="lblDocSo" style="font-weight: bold; font-style: italic;"></span>
														</TD>
													</TR>
													
														 <TR>
										  	  	<TD class="plainlabel">File đính kèm
										  	  		<div><div id="input"  style="float: left;"><INPUT type="file" name="fileup" value=""></div>
										  	  		&nbsp;&nbsp;
										  	  		<img src="../images/xpcollapse1.gif" onclick="javascript:upload2()" style="cursor: pointer;" alt="Thêm file" width="20" height="20" longdesc="Thêm file" border = 0>
										  	  		</div>
										  	  		<% if(ddhBean.getFile() != null)
										  	  		if(ddhBean.getFile().length > 0 ) {	
										  	  			for(int i=0;i <ddhBean.getFile().length; i++)
										  	  			if(ddhBean.getFile()[i].length() > 0)
										  	  			{%>
										  	  			<input type="hidden" name = "filedk" value = "<%=ddhBean.getFile()[i]%>">
										  	  			<div id="tenfilea"><p><%= ddhBean.getFile()[i] %>
										  	  				<img src="../images/Save20.png" onclick="LuuFile('<%= ddhBean.getFile()[i]%>')" style="cursor: pointer;" alt="Lưu File" width="20" height="20" longdesc="Lưu File" border = 0></p></div>
										  	  			<%}
									  	  			}%>
										  	  	</TD>
										  	  	<TD class="plainlabel"></TD>
										  	  		<TD class="plainlabel"></TD>
										  	  		 		<TD class="plainlabel"></TD>
										  	  	
				  		</TR>
												</TABLE>
												</FIELDSET>




 									<TABLE class="tabledetail" width = 100% cellpadding="0"  border="0" cellspacing="1" >
                                                    <TR class="tbheader" >
                                                      	<TH width="10%">Mã sản phẩm </TH>
														<TH width="27%">Tên sản phẩm</TH>
														<TH width="10%">Tồn kho(THG)</TH>
														<TH width="7%">Số lượng</TH>
														<TH width="10%">ĐVT</TH>
														<TH width="9%">Đơn giá(lẻ)</TH>
														<TH width="10%">Thành tiền</TH>													
                                                    </TR>
							<% 
								int m = 0;
								double tongluong = 0;
								double thanhtien = 0;
								if(spList != null){
								ISanPham sanpham = null;
								
								int size = spList.size();
								while (m < size){
								sanpham = spList.get(m); 
							%>
								<TR class= 'tblightrow' >
										<TD align="left" >
											<input name="quycach" type="hidden"  value="<%=sanpham.getQuycach() %>" readonly >
											<input name="spId" type="hidden" value="<%=sanpham.getId() %>" readonly>
											<input name="spMa" type="text" value="<%=sanpham.getMa() %>" onkeyup="ajax_showOptions(this,'abc',event)" style="width:100%" readonly AUTOCOMPLETE="off">
										</TD>
										<TD align="left" >
									        <input name="spTen" type="text" value="<%=sanpham.getTen()  %>" readonly style="width:100%" >
										</TD>
										<TD align="left" >
									        <input name="tonkho" type="text" value="<%=sanpham.getTonkho()  %>" readonly style="width:100%" >
										</TD>
										<TD align = "center" >
									    	<input name="soluongchuan" type="text" onchange="QuyRaLe(<%= m %>);" readonly  value="<%= sanpham.getSoluongchuan() %>"  style="width:100%;text-align:right; color: black;" onkeypress="return keypress(event);" >
									    </TD>									    								
							        	<TD align ="center" style="display:none;" >
								        	<input name="soluong"  type="text" value="<%= formatter.format(Double.parseDouble(sanpham.getSoluong().replace(",", ""))) %>" readonly style="width:100%;text-align:right"  readonly="readonly" >
								        	<% tongluong += (Double.parseDouble(sanpham.getSoluong().replace(",", ""))); %>
								        </TD>
										<TD align = "center" >
											<select name="dvdlId" onchange="QuyRaLe(<%= m %>);" style="width: 100%;" >
												<option value="" > </option>
										    	<%
																	if (dvdlList != null)
																			{
																				Enumeration<String> keys = dvdlList.keys();
																				while (keys.hasMoreElements())
																				{
																					String key = keys.nextElement();
																					if (key.toString().equals(sanpham.getDvdlId()))
																					{
																%>
																<option selected="selected" value="<%=key%>"><%=dvdlList.get(key)%></option>
																<%
																	} else
																					{
																%>
																<option value="<%=key%>"><%=dvdlList.get(key)%></option>
																<%
																	}

																				}
																			}
																%>
									    	</select>
									    </TD>	
										
										
									    
									    <TD align = "center" >
									    	<input type="text" name="dongia" value="<%=formatter.format(Double.parseDouble (sanpham.getDongia().replace(",", "")) )%>" readonly style="width:100% ;text-align:right">
									    </TD>
									    <TD align = "center" >
									    	<input name="thanhtien" type="text" value="<%=formatter.format(Double.parseDouble( sanpham.getThanhtien().replace(",", "")) )%>" readonly  style="width:100% ;text-align:right">
									    	<% thanhtien += Double.parseDouble( sanpham.getThanhtien().replace(",", "")); %>
									    </TD>
									</TR>
									<% m++; }}%>
																
<%-- 								<% --%>
<!-- // 									int i = m; -->
<!-- // 									while(i <= (40 + m) ) {  -->
<%-- 								%> --%>
<!-- 								<TR class= 'tblightrow'> -->
<!-- 									<TD align="center" > -->
<!-- 										<input name="quycach" type="hidden"  > -->
<!-- 										<input name="spId" type="hidden"  > -->
<!-- 										<input name="spMa" type="text" value="" onkeyup="ajax_showOptions(this,'abc',event)" style="width:100%" AUTOCOMPLETE="off"> -->
<!-- 									</TD> -->
<!-- 									<TD align="left" > -->
<!-- 										<input name="spTen" type="text" value="" style="width:100%"> -->
<!-- 									</TD> -->
<!-- 									<TD align="left" > -->
<!-- 										<input name="tonkho" type="text" value="" style="width:100%"> -->
<!-- 									</TD> -->
<!-- 									 <TD align = "center" > -->
<%-- 								    	<input name="soluongchuan" type="text" onkeyup="QuyRaLe(<%= i %>);" onkeypress="return keypress(event);" value=""  style="width:100%;text-align:right; color: black;"> --%>
<!-- 								    </TD>	 -->
<!-- 								    <TD align = "center" style="display:none;"> -->
<!-- 							        	<input name="soluong"  type="text" value="" style="width:100%; text-align:right"  readonly="readonly" > -->
<!-- 							        </TD>	 -->
									
<!-- 									<TD align = "center" > -->
<%-- 										<select name="dvdlId" onchange="QuyRaLe(<%= i %>);" style="width: 100%;" > --%>
<!-- 											<option value="" > </option> -->
<%-- 									    		<% --%>
<!-- // 																	if (dvdlList != null) -->
<!-- // 																			{ -->
<!-- // 																				Enumeration<String> keys = dvdlList.keys(); -->
<!-- // 																				while (keys.hasMoreElements()) -->
<!-- // 																				{ -->
<!-- // 																					String key = keys.nextElement(); -->
																					
<%-- 																%> --%>
<%-- 																<option value="<%=key%>"><%=dvdlList.get(key)%></option> --%>
<%-- 																<% --%>
<!-- // 																	} -->

<!-- // 																				} -->
																			
<%-- 																%> --%>
<!-- 								    	</select> -->
<!-- 								    </TD>	 -->
									
									
								    					        
<!-- 								    <TD align = "center" > -->
<!-- 								    	<input type="text" name="dongia" readonly style="width:100% ;text-align:right"> -->
<!-- 								    </TD> -->
<!-- 								    <TD align = "center" > -->
<!-- 								    	<input name="thanhtien" type="text" readonly  style="width:100% ;text-align:right"> -->
<!-- 								    </TD> -->
<!-- 								</TR> -->
<%-- 								<% i++;} %>	 --%>
							
										<TR class="tbheader" >
           	 		<td  align="middle" colspan="3"  style="color:red;font-weight: bold;font-size:1.25em"> Tổng cộng </td>
           	 		<td  align="right"    style="font-weight: bold;font-size:1.25em"><%=formatter.format(tongluong) %></td>           	 		           	 		           	 		
           	 		<td  align="right"  colspan="3" style="font-weight: bold;font-size:1.25em;" ><%=formatter.format(thanhtien) %></td>
           	 	</TR>		
												
								</TABLE>
							</TD>
						</TR>
					</TABLE>
				</td>
			</tr>
		</table>
		
<script type="text/javascript">
	replaces();
</script>
	</form>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</html>
<%
	if (!(ncc == null))
			ncc.close();
%>
<%
	if (!(dvkd == null))
			dvkd.close();
%>
<%
	if (ddhBean != null)
		{
			ddhBean.DBclose();
			ddhBean = null;
		}
		spList.clear();
%>
<%
	}
%>