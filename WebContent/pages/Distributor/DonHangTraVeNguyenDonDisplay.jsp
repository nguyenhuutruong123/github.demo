<%@page import="geso.dms.center.util.Utility"%>
<%@page import="geso.dms.distributor.db.sql.dbutils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.dms.distributor.beans.donhangtrave.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>
<%@ page  import = "java.text.DateFormat" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "java.text.SimpleDateFormat" %>
<% IDonhangtrave dhtvBean = (IDonhangtrave)session.getAttribute("dhtvBean"); %>
<% List<ISanpham> splist = (List<ISanpham>)dhtvBean.getSpList(); %>
<% ResultSet ddkd = (ResultSet)dhtvBean.getDdkdRs(); %>
<% ResultSet khachhangRs = (ResultSet)dhtvBean.getKhachhangRs(); %>
<% ResultSet donhangRs = (ResultSet)dhtvBean.getDonhangRs(); %>
<% String userId = (String) session.getAttribute("userId");  %>
<% Hashtable<String, Integer> spThieuList = dhtvBean.getSpThieuList(); 
NumberFormat formatter = new DecimalFormat("###,###,###,###");

Utility util = (Utility) session.getAttribute("util");
String nnId = "";

if (nnId == null) {
	nnId = "vi";
}

String url = util.getChuyenNguUrl("DonhangtraveNguyenDonSvl", "", nnId);

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
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
</style>
<link rel="stylesheet" type="text/css" href="../css/speechbubbles.css" />

<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
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
            $(".button").hover(function(){
                $(".button img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        }); 
		$(document).ready(function(){
            $(".button1").hover(function(){
                $(".button1 img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        });
		
    </script>


<script type="text/javascript" src="../scripts/speechbubbles.js"></script>
<script type="text/javascript">
	jQuery(function($){ 
		 $('.addspeech').speechbubble();})
</script>
<script type="text/javascript" src="../scripts/ajax.js"></script>
<script type="text/javascript" src="../scripts/ajax-dynamic-list-sptrave.js"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<script type="text/javascript" src="../scripts/cool_DHTML_tootip.js"></script>
<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>

<script language="javascript" type="text/javascript" >
function replaces()
{
	var khTen = document.getElementsByName("khTen");
	var smartId = document.getElementsByName("smartId");
	
	for(i = 0; i < smartId.length; i++)
	{
		var tem = smartId.item(0).value;
		if(parseInt(tem.indexOf("-->[")) > 0)
		{
			var tmp = tem.substring(0, parseInt(tem.indexOf("-->[")));
			document.getElementById("khId").value = tmp.substring(parseInt(tem.indexOf("-")+1, tmp.length));
			smartId.item(0).value = tmp.substring(0, parseInt(tem.indexOf("-")));
			tem = tem.substr(parseInt(tem.indexOf("-->[")) + 4);
			khTen.item(0).value = tem.substring(0, parseInt(tem.indexOf("][")));
			
			if(khTen != "")
			{
				document.forms['dhtvForm'].action.value='submit';
			    document.forms["dhtvForm"].submit();
			}
			
			break;
		}
		if(tem == "")
		{
			khTen.item(0).value = "";
		}		
	}
	
		var masp = document.getElementsByName("masp");
		var tensp = document.getElementsByName("tensp");
		var donvitinh = document.getElementsByName("donvitinh");
		var dongia = document.getElementsByName("dongia");
		var soluong = document.getElementsByName("soluong");
		var thanhtien = document.getElementsByName("thanhtien");
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
					tensp.item(i).value = sp.substring(0, parseInt(sp.indexOf(" [")));
					sp = sp.substr(parseInt(sp.indexOf(" [")) + 2);
					donvitinh.item(i).value = sp.substring(0, parseInt(sp.indexOf("] [")));
					sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
					dongia.item(i).value = sp.substring(0, parseInt(sp.indexOf("]")));
				}
				if(parseInt(soluong.item(i).value) > 0)
				{				
					var don_gia = dongia.item(i).value;
					var tt = parseFloat(soluong.item(i).value) * parseFloat(don_gia);
					thanhtien.item(i).value = tt;
					TinhTien();
				}
				else			
				{
					thanhtien.item(i).value = "";
				}
				/*
				if(checkMasp(masp.item(i).value) == true)
				{
					masp.item(i).parentNode.parentNode.bgColor = "#9FC";
				}
				*/
			}
			else
			{
				tensp.item(i).value = "";
				donvitinh.item(i).value = "";
				dongia.item(i).value = "";
				soluong.item(i).value = "";
				thanhtien.item(i).value = "";
	
				TinhTien();
			}		
		}	

	setTimeout(replaces, 100);
	}
	
	function TinhTien()
	{
		var thanhtien = document.getElementsByName("thanhtien");
		//var chietkhau = document.getElementsByName("ChietKhau");
		var tongtien = 0;
		for(var i=0; i < thanhtien.length; i++)
		{
			if(thanhtien.item(i).value != "")
			{
				//var thanh_tien = thanhtien.item(i).value.replace(".", "");
				var thanh_tien = thanhtien.item(i).value;
				tongtien = parseFloat(tongtien) +  parseFloat(thanh_tien);
			}
		}
		var tienchuaVAT = tongtien;
		document.getElementById("SoTienChuaVAT").value = tienchuaVAT;

		var vat = document.getElementById("VAT").value;
		if(vat == "")
			vat = "10";

		var tongtiencoVAT = (parseFloat(vat) * tienchuaVAT) / 100 + tienchuaVAT;
		document.getElementById("SoTienCoVAT").value = Math.round(tongtiencoVAT);
	}
	
	function checkMasp(masanpham)
	{
		var masp = document.getElementsByName("masp");
		for(i = 0; i < masp.length ; i++)
		{
			if(masp.item(i).value == masanpham)
				return true;
		}
		return false;
	}
	
	function keypress(e)
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

	function TongchietkhauKM()
	{
		var ckTrakm = document.getElementsByName("ckTrakm");
		var sum = 0;
		if(ckTrakm.length > 0)
		{
			for(h =0; h < ckTrakm.length; h++)
			{
				if(ckTrakm.item(h).value != "")
					sum = parseFloat(sum) + parseFloat(ckTrakm.item(h).value);
			}
		}
		return sum;
	}
	
	 function saveform()
	 {	 

	 }
	
	 function submitform()
	 { 

	 }
	 
	 function submitform2()
	 { 

	 }
	 
	 function congDonSPCungMa()
	 {
		var masp = document.getElementsByName("masp");
		var soluong = document.getElementsByName("soluong");
		var ii;
		for(ii = 0; ii < (masp.length - 1) ; ii++)
		{
			for( j = ii + 1; j < masp.length; j++)
			{
				if(masp.item(ii).value != "" && masp.item(ii).value == masp.item(j).value)
				{
					//alert(masp.item(ii).value + "-" + masp.item(j).value);				
					if(soluong.item(j).value == "")
						soluong.item(j).value = "0";
					
					soluong.item(ii).value = parseInt(soluong.item(ii).value) + parseInt(soluong.item(j).value);
					masp.item(j).value = "";
				}
			}
		}
	 }
	function addRow(name)
		{
			tableName = document.getElementById(name);
			
			var tr = document.createElement("TR");
			var maspAdd = document.createElement("TD");
			var tenspAdd = document.createElement("TD");
			var soluongAdd = document.createElement("TD");
			var dvtinhAdd = document.createElement("TD");
			var dongiaAdd = document.createElement("TD");
			var chietkhauAdd = document.createElement("TD");
			var thanhtienAdd = document.createElement("TD");
			var ctkmAdd = document.createElement("TD");
			
			var masp = document.createElement("input");
			masp.setAttribute("type", "textbox");
			masp.setAttribute("onkeyup", "ajax_showOptions(this,'abc',event)");
			masp.setAttribute("style","width:100%;border:1px;	border-style:solid;border-color: #808080;");
			masp.name = 'masp';
			maspAdd.appendChild(masp);
			
			var tensp = document.createElement("input");
			tensp.setAttribute("type", "textbox");
			tensp.setAttribute("readonly", "readonly");
			tensp.setAttribute("style"," width:100%;border:1px;	border-style:solid;border-color: #808080;");
			
			tensp.name = 'tensp';
			tenspAdd.appendChild(tensp);
			
			var soluong = document.createElement("input");
			soluong.setAttribute("type", "textbox");
			soluong.setAttribute("onkeypress","return keypress(event)");
			soluong.value = "";
			soluong.setAttribute("style","width:100%;border:1px; border-style:solid;border-color: #808080;");

			soluong.name = "soluong";
			soluongAdd.appendChild(soluong);
			
			var dvt = document.createElement("input");
			dvt.setAttribute("type", "textbox");
			dvt.setAttribute("readonly", "readonly");
			dvt.setAttribute("style","width:100%;border:1px; border-style:solid;border-color: #808080;");
			dvt.value = "";
			
			dvt.name = 'donvitinh';
			dvtinhAdd.appendChild(dvt);
			
			var dongia = document.createElement("input");
			dongia.setAttribute("type", "textbox");
			dongia.setAttribute("readonly", "readonly");
			dongia.setAttribute("style","width:100%;border:1px;	border-style:solid;border-color: #808080;");
			dongia.value = "";
			dongia.name = 'dongia';
			dongiaAdd.appendChild(dongia);
			
			var thanhtien = document.createElement("input");
			thanhtien.setAttribute("type", "textbox");
			thanhtien.setAttribute("readonly", "readonly");
			thanhtien.setAttribute("style","width:100%;border:1px;	border-style:solid;border-color: #808080;");
			thanhtien.value = "";
			thanhtien.name = "thanhtien";
			thanhtienAdd.appendChild(thanhtien);
		
			tr.appendChild(maspAdd);
			tr.appendChild(tenspAdd);
			tr.appendChild(soluongAdd);
			tr.appendChild(dvtinhAdd);
			tr.appendChild(dongiaAdd);
			tr.appendChild(thanhtienAdd);
			
			tableName.appendChild(tr);
		}
		function ThemSanPham()
		{
			 var sl = window.prompt("Nhấp số lượng sản phẩm muốn thêm", '');
			 if(isNaN(sl) == false && sl < 30)
			 {
				 for(var i=0; i < sl ; i++)
					 addRow("san_pham");
			 }
			 else
			 {
				 alert('Số lượng bạn nhập không hợp lệ. Mọi lần bạn chỉ được thêm tối đa 30 sản phẩm');
				 return;
			 }
		 }
</script>
<link href="../css/select2.css" rel="stylesheet" />
	<script src="../scripts/select2.js"></script>
	<script>
    	$(document).ready(function() { 
    		$("select").select2(); 
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

<form name="dhtvForm" method="post" action="../../DonhangtraveNguyenDonUpdateSvl"> 
 <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> 
<input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%=userId %>'>
<%-- <input type="hidden" name="nppId" value='<%= dhtvBean.getNppId() %>'> --%>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="chot_dh" value='false'>
<input type="hidden" name="id" value='<%=dhtvBean.getId()%>'>
<input type="hidden" name="view" value='<%=dhtvBean.getView()%>'>
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFEE"><!--begin body Dossier-->
				<TABLE border =0 width = "100%" cellpadding="2" cellspacing="0">
				<TBODY>
					<TR height="22">
						<TD align="left" >
							<TABLE width="100%" cellpadding="0" cellspacing="0">
								<TR>
									<TD align="left">
									   <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
										   <TR height="22">
			 								   <TD align="left" class="tbnavigation">&nbsp;<%= url %> > Hiển thị </TD>								   
			 								   <TD align="right" class="tbnavigation">Chào mừng bạn <%= dhtvBean.getNppTen() %> &nbsp; </TD>
					    				 </TR>
									  </TABLE>
								  </TD>
							  </TR>	
						  	</TABLE>
							<TABLE width="100%" border="0" cellpadding="1" cellspacing="0">
								<TR ><TD >
									<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
										<TR class = "tbdarkrow">
											<TD width="30" align="left"><A href = "javascript:history.back()" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
										    <TD width="2" align="left" >&nbsp;</TD>
										    <TD align="left" >&nbsp;</TD>				    		
										</TR>
									</TABLE>
								</TD></TR>
							</TABLE>												
							<TABLE border="0" width="100%" cellpadding = "0" cellspacing = "0">
								<tr>
								<TD align="left" colspan="4" class="legendtitle">
									<FIELDSET>
									<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>			
				    				<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold"  style="width:100%" readonly="readonly" rows="1"><%= dhtvBean.getMessage() %></textarea>
									</FIELDSET>
							   </TD>
								</tr>
								<TR>
									<TD  align="left">						
										<FIELDSET>
										<LEGEND class="legendtitle">&nbsp; Điều kiện lọc </LEGEND>
										<TABLE cellpadding = "6" cellspacing = "0" width = "100%" border = 0>
											
											<TR>
											  <TD class="plainlabel" style="width:15%;">Từ ngày</TD>
											  <TD colspan="1" class="plainlabel">
											    <input class="days" type="text" name="tungay" size="20" value="<%= dhtvBean.getTungay() %>" ></TD>
												<TD class="plainlabel" style="width:15%;">Đến ngày </TD>
											  	<TD colspan="1" class="plainlabel">
											    	<input class="days" type="text" name="denngay" size="20" value="<%= dhtvBean.getDenngay() %>" >
												</TD>												
											</TR>
																			
                                         
											<TR class="tblightrow">
												<TD  class="plainlabel">Nhân viên bán hàng </TD>
												<TD colspan="1" class="plainlabel"> 
									 				<SELECT name="ddkdId" id="ddkdId" style ="width: 200px" onChange = "submitform2();">
										 			 <option value=""> </option>
													  <% if(ddkd != null){
														  try{ while(ddkd.next()){ 													 
											    			if(ddkd.getString("pk_seq").equals(dhtvBean.getDdkdId())){ %>
											      				<option value='<%=ddkd.getString("pk_seq")%>' selected><%=ddkd.getString("mafast")+"--"+ddkd.getString("ten") %></option>
											      			<%}else{ %>
											     				<option value='<%=ddkd.getString("pk_seq")%>'><%=ddkd.getString("mafast")+"--"+ddkd.getString("ten") %></option>
											     			<%}} ddkd.close(); }catch(java.sql.SQLException e){e.printStackTrace();}} %>	 
                                    				</SELECT>
                                    			</TD>
                                    			
                                    			<TD  class="plainlabel">Mã khách hàng </TD>
												<TD colspan="1" class="plainlabel"> 
									 				<SELECT name="khId" id="khId" style ="width: 200px" onChange = "submitform();">
										 			 <option value=""> </option>
													  <% if(khachhangRs != null){
														  try{ while(khachhangRs.next()){ 													 
											    			if(khachhangRs.getString("pk_seq").equals(dhtvBean.getKhId())){ %>
											      				<option value='<%=khachhangRs.getString("pk_seq")%>' selected><%=khachhangRs.getString("mafast")+"--"+khachhangRs.getString("ten") %></option>
											      			<%}else{ %>
											     				<option value='<%=khachhangRs.getString("pk_seq")%>'><%=khachhangRs.getString("mafast")+"--"+khachhangRs.getString("ten") %></option>
											     			<%}} khachhangRs.close(); }catch(java.sql.SQLException e){e.printStackTrace();}} %>	 
                                    				</SELECT>
                                    			</TD>
											</TR>
											
											<TR class="tblightrow">
												<TD class="plainlabel">Số đơn hàng </TD>
												<TD colspan="1" class="plainlabel"> 
									 				<SELECT name="dhId" id="dhId" style ="width: 200px" onChange = "submitform();">
										 			 <option value=""> </option>
													  <% if(donhangRs != null){
														  try{ while(donhangRs.next()){ 													 
											    			if(donhangRs.getString("pk_seq").equals(dhtvBean.getDonhangId())){ %>
											      				<option value='<%=donhangRs.getString("pk_seq")%>' selected><%=donhangRs.getString("pk_seq")%></option>
											      			<%}else{ %>
											     				<option value='<%=donhangRs.getString("pk_seq")%>'><%=donhangRs.getString("pk_seq") %></option>
											     			<%}} donhangRs.close(); }catch(java.sql.SQLException e){e.printStackTrace();}} %>	 
                                    				</SELECT>
                                    			</TD>
                                    			<TD class="plainlabel">Tổng giá trị đơn hàng</TD>
											  	<TD colspan="1"  class="plainlabel">
											  		<input name="SoTienCoVAT" id="SoTienCoVAT" type="text" readonly 
											  			value="<%= dhtvBean.toCurrencyFormat(dhtvBean.getTongtiensauVAT())%>" style="text-align: right;"> VND 
									          	</TD>
											</TR>       
										 </TABLE></FIELDSET></TD> </TR>															  
									
									<TR> 
									<TD align="left">						
										<FIELDSET>
										<LEGEND class="legendtitle">&nbsp; Thông tin phiếu trả hàng nguyên đơn</LEGEND>
										<TABLE cellpadding = "6" cellspacing = "0" width = "100%" border = 0>
										  	
										  	<TR>
											  <TD class="plainlabel" style="width:15%;">Ngày trả hàng </TD>
											  <TD colspan="1" class="plainlabel">
											    <input class="days" type="text" name="ngaygiaodich" size="20" value="<%= dhtvBean.getNgaygiaodich() %>" >
											</TD>
											</TR>
										  	
										  	<TR class="tblightrow">
											  <TD class="plainlabel">Lý do trả hàng</TD>
											  <TD colspan="1"  class="plainlabel">
											  	<input name="lydo" id="lydo" type="text" style="width:400px;" value="<%= dhtvBean.getLydo()%>" style="text-align: left;">
									          </TD>
										  	</TR>

										    </TABLE>
										   </FIELDSET> 
										</TD> 
									</TR>	
								  </TD>
							   </TR>	
							   <TR>
							   		<TD>
										<TABLE width = "100%"  border="0" cellpadding="0" cellspacing="1">
										<tbody id="san_pham">
												<TR class="tbheader" >
													<TH width="15%" height="20">Scheme </TH>
													<TH width="15%" height="20">Mã sản phẩm </TH>
													<TH width="28%">Tên sản phẩm </TH>
													<TH width="5%">Số lượng </TH>
													<TH width="7%">ĐVT</TH>
													<TH width="10%">Đơn giá </TH>
													<TH width="10%">Thành tiền </TH>
												</TR>
								<% 
							if(splist != null){
							ISanpham sanpham = null;
							int size = splist.size();
							int m = 0;
							while (m < size){
								sanpham = splist.get(m); 
								 %>										
								    <TR class= 'tblightrow' >			
								    
								    <TD align="left" >
										<input name=scheme type="text" readonly value="<%=sanpham.getCTKM()%>" readonly style="width:100% ;text-align: left;">
									</TD>
													
									<TD align="left" >
										<input name="masp" type="text" readonly value="<%=sanpham.getMasanpham()%>" readonly  style="width:100% ;text-align: left;">
									</TD>
									
									<TD align="left" >
										<input name="tensp" type="text" readonly value="<%=sanpham.getTensanpham()%>" style="width:100% ;text-align: left;">
									</TD>

							        <TD align = "center" >
								        <input name="soluong" type="text" value="<%= sanpham.getSoluong() %>" onkeypress="return keypress(event);" readonly style="width:100%; text-align:left;">
								    </TD>
								    
								    <TD align = "center" >
								    	<input name="donvitinh" type="text" value="<%= sanpham.getDonvitinh() %>" readonly style="width:100% ;text-align: left;">
								    </TD>
								    
								    <TD align = "center" >
								    	<input type="text" name="dongia" value="<%= sanpham.getDongia() %>" readonly style="width:100% ;text-align: left;">
								    </TD>
								    
								    <TD align = "center" >
								    	<input name="thanhtien" type="text" value="<%=formatter.format(Double.parseDouble(sanpham.getTongtien())) %>" readonly style="width:100% ;text-align: left;">
								    </TD>
								</TR>
								<% m++; }}%>
									
								<%
									int i=0;
									while(i < 20){
								%>
								<TR class= 'tblightrow'>
								
									<TD align="left" >
										<input name="scheme" readonly type="text" value="" style="width:100% ;text-align: left;" AUTOCOMPLETE="off">
									</TD>
								
									<TD align="left" >
										<input name="masp" readonly type="text" value="" style="width:100% ;text-align: left;" AUTOCOMPLETE="off">
									</TD>
									
									<TD align="left" >
										<input name="tensp" type="text" readonly value="" style="width:100% ;text-align: left;"></TD>
								    <TD align = "center" >
							        <input name="soluong" type="text" value="" readonly  onkeypress="return keypress(event);" style="width:100% ;text-align: left;"></TD>
								    <TD align = "center" ><input name="donvitinh" type="text" value="" readonly style="width:100% ;text-align: left;"></TD>
								    <TD align = "center" ><input name="dongia" type="text" value="" readonly style="width:100% ;text-align: left;"></TD>
								    <TD align = "center" ><input name="thanhtien" type="text" value="" readonly style="width:100% ;text-align: left;"></TD>
								</TR>
								<% i++;} %>		
																																																																																																															
								</tbody>
								</TABLE>
								<br>
								&nbsp;&nbsp;&nbsp;&nbsp; <a class="button2" href="javascript:ThemSanPham()">
									<img style="top: -4px;" src="../images/add.png" alt="">Thêm sản phẩm</a>&nbsp;&nbsp;&nbsp;&nbsp;	

								</TD>
							  </TR>	
							 		   
						  </TABLE>
						</TD>
					</TR>	
					
				</TBODY>
			</TABLE>
	</td>
  </tr>

</TABLE>
<script type="text/javascript">
//replaces();
//Call dropdowncontent.init("anchorID", "positionString", glideduration, "revealBehavior") at the end of the page:
dropdowncontent.init("searchlink", "right-bottom", 500, "click");
</script>
</form>
</BODY>
<script>
	jQuery(function()
	{		
		$("#smartId").autocomplete("KhachHangList.jsp");	
		//$("#smartId").autocomplete("list.jsp");
	});	
</script>
</HTML>
<%
try{
	if(ddkd != null)
		ddkd.close();
	if(dhtvBean != null){
		dhtvBean.DBclose();
		dhtvBean = null;
	}
	if(splist!=null){
		splist.clear();
	}
	if(spThieuList!=null){
		spThieuList.clear();
	}
	
	dbutils db = dhtvBean.getDb();
	db.shutDown();

	session.setAttribute("dhtvBean",null);
	
	}
	catch (Exception e) {}
%>