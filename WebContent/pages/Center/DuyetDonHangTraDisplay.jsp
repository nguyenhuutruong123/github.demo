<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.dms.center.beans.duyettradonhang.*" %>
<%@ page  import = "geso.dms.distributor.beans.donhangtrave.ISanpham" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>
<%@ page  import = "java.text.DateFormat" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "java.text.SimpleDateFormat" %>
<% 	NumberFormat formatter = new DecimalFormat("#,###,###"); 
	IDuyettradonhang dtdhBean = (IDuyettradonhang)session.getAttribute("dtdhBean"); %>
<% List<ISanpham> splist = (List<ISanpham>)dtdhBean.getSpList(); %>
<% ResultSet npp = (ResultSet)dtdhBean.getNppRs(); %>

<% String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen"); 	%>

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

<script type="text/javascript" src="../scripts/jquery.min.js"></script>
<script type="text/javascript" src="../scripts/speechbubbles.js"></script>
<script type="text/javascript">
	jQuery(function($){ 
		 $('.addspeech').speechbubble();})
</script>
<script type="text/javascript" language="JavaScript" src="../scripts/simplecalendar.js"></script>
<script type="text/javascript" src="../scripts/ajax.js"></script>
<script type="text/javascript" src="../scripts/ajax-dynamic-list.js"></script>
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
				document.forms['dtdhForm'].action.value='submit';
			    document.forms["dtdhForm"].submit();
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
					var x =soluong.item(i).value;
					while(x.match(","))
					{
						x=x.replace(",","");
					}
					while(don_gia.match(","))
					{
						don_gia=don_gia.replace(",","");
					}
					var tt = parseFloat(x) * parseFloat(don_gia);
					thanhtien.item(i).value = DinhDangTien(Math.round(tt));
					TinhTien();
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
	
				TinhTien();
			}		
		}	

	setTimeout(replaces, 20);
	}
	
	<% if(splist != null){ if(splist.size() > 0){ %>
		replaces();
	<%}} %>
	
	function TinhTien()
	{
		var thanhtien = document.getElementsByName("thanhtien");
		//var chietkhau = document.getElementsByName("ChietKhau");
		var tongtien = 0;
		for(var i=0; i < thanhtien.length; i++)
		{
			if(thanhtien.item(i).value != "")
			{
				var x =thanhtien.item(i).value;
				while(x.match(","))
				{
					x=x.replace(",","");
				}
				//var thanh_tien = thanhtien.item(i).value.replace(".", "");
				
				var thanh_tien = x;
				tongtien = parseFloat(tongtien) +  parseFloat(thanh_tien);
			}
		}
		var tienchuaVAT = tongtien;
		document.getElementById("SoTienChuaVAT").value = DinhDangTien(Math.round(tienchuaVAT));

		
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

<form name="dtdhForm" method="post" action="../../DuyettradonhangUpdateSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%=userId %>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="id" value='<%= dtdhBean.getId() %>'>
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFEE">
				<TABLE border =0 width = "100%" cellpadding="2" cellspacing="0">
				<TBODY>
					<TR height="22">
						<TD align="left" >
							<TABLE width="100%" cellpadding="0" cellspacing="0">
								<TR>
									<TD align="left">
									   <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
										   <TR height="22">
			 								   <TD align="left"  class="tbnavigation"> &nbsp;Quản lý tồn kho > Duyệt trả hàng về NPP > Duyệt trả đơn hàng > Hiển thị </TD>								   
			 								   <TD align="right" class="tbnavigation">Chào mừng  <%= userTen %>&nbsp;</TD>
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
										</TR>
									</TABLE>
								</TD></TR>
							</TABLE>												
							<TABLE border="0" width="100%" cellpadding = "0" cellspacing = "0">
								<tr>
								<TD align="left" colspan="4" class="legendtitle">
									<FIELDSET>
									<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>			
				    				<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold"  style="width:100%" readonly="readonly" rows="1"><%= dtdhBean.getMessage() %></textarea>
									</FIELDSET>
							   </TD>
								</tr>
								<TR>
									<TD  align="left">						
										<FIELDSET>
										<LEGEND class="legendtitle">&nbsp;Đơn hàng trả về </LEGEND>
										<TABLE cellpadding = "6" cellspacing = "0" width = "100%" border = 0>
											<TR >
											  <TD class="plainlabel">Ngày trả hàng </TD>
											  <TD colspan="3" class="plainlabel">
											  <table border=0 cellpadding="0" cellspacing="0">
                                                <TR><TD>
											    <input type="text" name="tungay" id="tungay" size="20" value="<%= dtdhBean.getNgaygiaodich() %>" readonly="readonly">
											</TD><TD>
												<a href="javascript: void(0);" onMouseOver="if (timeoutId) clearTimeout(timeoutId);window.status='Show Calendar';return true;" onMouseOut="if (timeoutDelay) calendarTimeout();window.status='';" onClick="g_Calendar.show(event,'dtdhForm.tungay',false, 'yyyy-mm-dd'); return false;">
		  											&nbsp;<img src="../images/Calendar20.png" name="imgCalendar" border="0" alt=""></a>
											</TD></TR>
                                          </table></TR>
                                          <TR >
												<TD width="22%" class="plainlabel">Nhà phân phối</TD>
												<TD colspan="3" class="plainlabel">
													<%= dtdhBean.getNppTen() %>
													<input type="hidden" name="nppTen" value="<%= dtdhBean.getNppId() %>" > 
												</TD>
										  </TR>	                           							
											<TR class="tblightrow">
												<TD  class="plainlabel">Mã đơn hàng </TD>
												<TD colspan="3" class="plainlabel"> 
													<input type="text" id="madonhang" name="madonhang" value="<%= dtdhBean.getDhId() %>" readonly="readonly" > 
									 			</TD>
											</TR>
											
											<% if(splist != null ){ if(splist.size() > 0 ){ %>
											<TR class="tblightrow">
											    <TD  class="plainlabel">Tổng số tiền  </TD>	  
											    <%
														  String gia="";
														  if( dtdhBean.getTongtientruocVAT().trim().length()!=0)
														  {
															 gia= formatter.format(Double.parseDouble( dtdhBean.getTongtientruocVAT()));
														  }
														  else
														  {
															  gia= dtdhBean.getTongtientruocVAT();
														  }
														  
														  %>   
										        <TD colspan="3"  class="plainlabel"><input name="SoTienChuaVAT" id="SoTienChuaVAT" type="text" value="<%=gia%>" style="text-align: right;" readonly > VND </TD>
									       	</TR>
											
										    
											<%}} %>
																										  
										</TABLE>
										
									</FIELDSET>
								  </TD>
							   </TR>
							<% if(splist != null){ if(splist.size() > 0) {%>
							   <TR>
							   		<TD>
										<TABLE width = "100%"  border="0" cellpadding="0" cellspacing="1">
										<tbody id="san_pham">
												<TR class="tbheader" >
													<TH width="15%" height="20">Mã sản phẩm </TH>
													<TH width="28%">Tên sản phẩm </TH>
													<TH width="5%">Số lượng </TH>
													<TH width="7%">DVT</TH>
													<TH width="10%">Đơn giá </TH>
													<TH width="10%">Thành tiền </TH>
													<TH width="10%">CTKM </TH>
												</TR>
								<% 
							ISanpham sanpham = null;
							int size = splist.size();
							int m = 0;
							while (m < size){
								sanpham = splist.get(m);
								 %>										
								    <TR class= 'tblightrow' >		
									<TD align="left" >
										<input name="masp" type="text"  value="<%=sanpham.getMasanpham()%>" style="width:100% ;text-align: left;" readonly="readonly">
									</TD>
									<TD align="left" >
										<input name="tensp" type="text" readonly value="<%=sanpham.getTensanpham()%>" style="width:100% ;text-align: left;"></TD>

						        	<TD align = "center" >
						        	<%
										 String  gia="";
										  if(sanpham.getSoluong().trim().length()!=0)
										  {
											 gia= formatter.format(Double.parseDouble(sanpham.getSoluong() ));
										  }
										  else
										  {
											  gia= sanpham.getSoluong() ;
										  }
										  
										  %>
							        <input name="soluong" type="text" value="<%=gia %>" style="width:100%; text-align:left;" readonly="readonly">
							        </TD>

								    <TD align = "center" ><input name="donvitinh" type="text" value="<%= sanpham.getDonvitinh() %>" readonly style="width:100% ;text-align: left;"></TD>
								    <TD align = "center" >
								    	<%
										   gia="";
										  if(sanpham.getDongia().trim().length()!=0)
										  {
											 gia= formatter.format(Double.parseDouble(sanpham.getDongia() ));
										  }
										  else
										  {
											  gia= sanpham.getDongia() ;
										  }	  
										 %>
								    	<input type="text" name="dongia" value="<%= gia %>" readonly style="width:100% ;text-align: left;">
								    </TD>
								    <TD align = "center" ><input name="thanhtien" type="text" value="" readonly style="width:100% ;text-align: left;"></TD>
								    <TD align = "center" ><input name="ctkm" type="text" value="<%= sanpham.getCTKM() %>" readonly style="width:100% ;text-align: left;"></TD>
								</TR>
								<% m++; }}%>
																																																																																																							
								</tbody>
								</TABLE>
								</TD>
							  </TR>
							  <%} %>
							 		   
						  </TABLE>
						</TD>
					</TR>	
					
				</TBODY>
			</TABLE>
	</td>
  </tr>

</TABLE>
</form>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</HTML>
