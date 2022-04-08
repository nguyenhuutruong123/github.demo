<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.beans.thongtinhoadon.IThongtinhoadon" %>
<%@ page import = "java.sql.ResultSet" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>

<% IThongtinhoadon dctkBean = (IThongtinhoadon)session.getAttribute("dctkBean"); %>
<% ResultSet dvkd = (ResultSet) dctkBean.getDvkd(); %>
<% ResultSet kbh = (ResultSet) dctkBean.getKbh(); %>
<% ResultSet kho = (ResultSet) dctkBean.getKho(); %>
<% String[] spId = (String[])dctkBean.getSpId(); %>
<% String[] masp = (String[])dctkBean.getMasp(); %>
<% String[] tensp = (String[])dctkBean.getTensp() ; %>
<% String[] tkht = (String[])dctkBean.getTkht(); %>
<% String[] dv = (String[])dctkBean.getDonvi(); %>
<% String[] dc = (String[])dctkBean.getDc(); %>
<% String[] giamua = (String[])dctkBean.getGiamua(); %>
<% String[] ttien = (String[])dctkBean.getTtien(); %>
<% String[] tkm = (String[])dctkBean.getTkm(); %>
<% ResultSet tthd = dctkBean.getNcc(); %>
<% NumberFormat formatter = new DecimalFormat("#,###,###");%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">
<SCRIPT src="../js/md5.js" type="text/javascript" language="javascript">
</SCRIPT>
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">

<SCRIPT src="../js/scripts.js" type="text/javascript" language="javascript"> </SCRIPT>
<SCRIPT src="../js/commun.js" type="text/javascript" language="javascript"> </SCRIPT>

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
        }); 		
		
</script>

<script type="text/javascript" language="JavaScript" src="../scripts/Numberformat.js"></script>
<SCRIPT type="text/javascript">
function setTTien(){
	
	
	 	
	var i = <%= dctkBean.getSize()%>;
	var masp = new Array(<%= dctkBean.getMaspstr() %>);	
	var tongtien = 0;
	
	for(x=0; x<i; x++){
		var dcId = "dc" + masp[x];
	
		var dc= document.getElementById(dcId).value;
		dc.replace(/^\s+|\s+$/, '');
		if(dc.length==0) 
			dc="0";

		if(!(isNumeric(dc))){
			alert("Số không hợp lệ");
			document.getElementById(dcId).focus();
			x=i;
		}else{
			var gmId = "gia"  + masp[x];
		
			var gia= document.getElementById(gmId).value;
	
			var tien = parseFloat(dc)*parseFloat(gia.replace(/\,/g,''));
		
			var tienId = "ttien"  + masp[x];	
		
			if(!(isNaN(tien))){
				document.getElementById(tienId).setAttribute("value", formatCurrency(tien));
				tongtien = parseFloat(tongtien) + parseFloat(tien);
			}

			var tkhtId = "tkht" + masp[x];
			var tkht = document.getElementById(tkhtId).value;
			var tkmId = "tkm" + masp[x];
			var tkm = parseFloat(tkht) + parseFloat(dc);
			document.getElementById(tkmId).setAttribute("value", tkm);
		}
	}
	document.getElementById("ttien").setAttribute("value", formatCurrency(tongtien)); 
	document.getElementById('lblDocSo').innerHTML = DocTienBangChu(tongtien) + " Đồng Việt Nam";
		
}

function isNumeric(n)
{
    var n2 = n;
    n = parseFloat(n);
    return (n!='NaN' && n2==n);
}
           
function submitform()
{      
   document.forms["dctkForm"].submit();
}

function submitenter(myfield,e)
{
var keycode;
if (window.event) keycode = window.event.keyCode;
else if (e) keycode = e.which;
else return true;

if (keycode == 13)
   {
   return false;
   }
else
   return true;
}

 function duyetform()
 {
	 
		 if(!confirm('Bạn có muốn chốt điều chỉnh tồn kho này không?')) {
		  return ;
		 }else{
			 document.getElementById("duyetid").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Proc...</a>";	
		 	document.forms['dctkForm'].action.value='approve';
		    document.forms["dctkForm"].submit();
		 }
 }

 function printform()
{   
	 
		document.forms['dctkForm'].action.value='excel';
        document.forms["dctkForm"].submit();
}
 
 function init()
 {	
 	var tongtienavat= document.getElementById("ttien").value;
 	tongtienavat = tongtienavat.replace(',', '');
 	tongtienavat = tongtienavat.replace(',', '');
 	tongtienavat = tongtienavat.replace(',', '');
 	tongtienavat = tongtienavat.replace(',', '');
 	tongtienavat = tongtienavat.replace(',', '');
 	document.getElementById('lblDocSo').innerHTML = DocTienBangChu(tongtienavat) + " Đồng Việt Nam";
 	
 }


</SCRIPT>


<!-- BEGIN RENDER AUTO -->

<% if (!geso.dms.center.util.GlobalValue.dev_mode) { %>
	<script type="text/javascript" src="../scripts/disableF12.js"></script>
<% } %>

<!-- END RENDER AUTO -->
</HEAD>
<body onLoad = 'init();'>
<!-- BEGIN RENDER AUTO -->
<%if(!geso.dms.center.util.GlobalValue.dev_mode){ %>
	<noscript> 
	 	<meta http-equiv="refresh" content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")  %>">
		<!-- //window.location = "https://www.google.com"; -->
	</noscript>
<%} %>
<!-- END RENDER AUTO -->

<form name="dctkForm" method="post" action="../../ThongtinhoadonSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<INPUT name="userId" type="hidden" value='<%=userId %>' size="30">
<INPUT name="id" type="hidden" value='<%=dctkBean.getId() %>' size="30">
<INPUT name="action" type="hidden" value='1' size="30">

<table width="100%" border="0" cellspacing="0" cellpadding="0"  >
  <tr>
    <td colspan = 4 valign="top">
                <TABLE border =0 width = 100% >
					
                <TBODY>
                	
                    <TR>
                        <TD align="left" >
                            <TABLE width="100%" cellpadding="0" cellspacing="0">
                                <TR>
                                    <TD align="left" class="tbnavigation">
                                       <table width="100%" border="0" cellpadding="0" cellspacing="0">
                                           <tr height="22">
                                               <TD align="left" colspan="1" class="tbnavigation">Quản lý bán hàng &nbsp;&gt; &nbsp;Chức năng &nbsp;&gt; Thông tin hóa đơn &nbsp; &gt; &nbsp; Hiển thị </TD>
                                               <TD colspan="1" align="right" class="tbnavigation">Chào mừng Bạn  <%= dctkBean.getUserTen() %> &nbsp;</TD>

                                            </tr>
                                        </table>
                                     </TD>
                                </TR>   
                            </TABLE>
                        <TD></TR>
                        <TR><TD>
                            <TABLE width="100%" border="0" cellpadding="1" cellspacing="0">
                                <TR ><TD >
                                    <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
                                        <TR class = "tbdarkrow">
                                            <TD width="30" align="left"><A href="javascript:history.back()" ><img src="../images/Back30.png" alt="Quay về"  title="Quay về" border="1" longdesc="Quay về" style="border-style:outset"></A></TD>
                                            <TD width="2" align="left" >&nbsp;</TD>
<!--                             				<TD width="30" align="left" ><A href="javascript: printform()" ><img src="../images/Printer30.png" alt="In" title="In chung tu" width="30" height="30" longdesc="In chung tu" border=1 style="border-style:outset"></A></TD> -->
<!--                                             <TD width="2">&nbsp;</TD> -->
                                         
                            				<TD align="left">&nbsp;</TD>
                                        </TR>
                                    </TABLE>
                                </TD></TR>

                            </TABLE>
                        </TD>           
                    </TR>
                    <TR>
                        <TD>
                            <TABLE border="0" width="100%" cellpadding="0" cellspacing="0" >
                               
                                </TR>

                                <TR>
                                    <TD  align="left">                       
                                        <FIELDSET>
                                        <LEGEND class="legendtitle">&nbsp;Thông tin hóa đơn </LEGEND>
                                        <TABLE cellpadding = "6" cellspacing = "0" width = "100%" border = 0>
                                        <TR> 
                                        <TH style="width:20%"></TH>
                						<TH style="width:100%"></TH>
                						</TR>
                                            <TR >
                                                <TD class="plainlabel">Mã nhà phân phối</TD>
                                                <TD class="plainlabel"><%= dctkBean.getNppTen() %></TD>                                                                   
                                            </TR>

                                           <%--  <TR >
                                                <TD class="plainlabel">Ngày điều chỉnh </TD>
                                                <TD class="plainlabel">
                                                    <table border=0 cellpadding="0" cellspacing="0">
                                                        <tr>
                                                            <td class="plainlabel" >
                                                            <input class="days" type="text" name="ngaydc" style="width:200px" value="<%= dctkBean.getNgaydc() %>" readonly="readonly"> </td>
                                                         </tr>

                                                    </table>                                                
                                                </TD>
                                            </TR>
											<TR>
							    				<TD class="plainlabel">Đơn vị kinh doanh</TD>
							      				<TD class="plainlabel">
								    						<SELECT   name="dvkdId"  disabled="disabled">								      
							  	 					<option value ="" ></option>
							  	 					<% try{ 
							  	 							while(dvkd.next()){ 
							  	 								if(dvkd.getString("dvkdId").equals(dctkBean.getDvkdId())){ %>
							      									<option value='<%=dvkd.getString("dvkdId")%>' selected><%=dvkd.getString("dvkd") %></option>
							      						   	  <%}else{ %>
							     									<option value='<%=dvkd.getString("dvkdId")%>' ><%=dvkd.getString("dvkd")  %></option>
							     							  <%}
							  	 							}
							  	 						}catch(java.sql.SQLException e){} %>	
								     	
													</SELECT></TD>
											</TR>
											<TR>
							  					<TD class="plainlabel">Kênh bán hàng </TD>
							  					<TD class="plainlabel">
							      					<SELECT name="kbhId" disabled="disabled" >								      
							  	 						<option value ="" ></option>
							  	 					<% try{ while(kbh.next()){ 
							    							if(kbh.getString("kbhId").equals(dctkBean.getKbhId())){ %>
							      								<option value='<%= kbh.getString("kbhId")%>' selected><%=kbh.getString("kbh") %></option>
							      						  <%}else{ %>
							     								<option value='<%= kbh.getString("kbhId")%>'><%= kbh.getString("kbh") %></option>
							     						<%}}}catch(java.sql.SQLException e){} %>	
								     	
                               						</SELECT>
                               					</TD>
								  			</TR>

                                			<TR>
                                 				 <TD class="plainlabel">Kho</TD>
								  				 <TD class="plainlabel"><SELECT name="khoId"  disabled="disabled">
								  					<option value="" > </option>
												<%  try{
								  						while(kho.next()){								  			
								  							if (dctkBean.getKhoId().equals(kho.getString("khoId"))){ %>								  			
								  								<option value="<%= kho.getString("khoId")%>" selected><%= kho.getString("ten")%></option>
								  		  				  <%}else{ %>		
								  		  						<option value="<%= kho.getString("khoId")%>" ><%= kho.getString("ten")%></option>
								  						  <%}
								  					    }
								  						}catch (java.sql.SQLException e){} %>
                                  						</SELECT></TD>

  			                              	</TR>
												<TR class="tblightrow">
                                     			<TD  class="plainlabel">Lý do điều chỉnh </TD>
                                     			<TD  class="plainlabel"><input name="lydodc" id="lydodc" type="text" value="<%= dctkBean.getLydodc()%>" style="background-color: #FFFFFF" readonly="readonly">
                                              		</TD>
                                			</TR>
                                			<TR class="tblightrow">
                                     			<TD  class="plainlabel">Giá trị điều chỉnh </TD>
                                     			<TD  class="plainlabel"><input name="ttien" id="ttien" type="text" value="<%= formatter.format(Long.parseLong(dctkBean.getGtdc())) %>" style="background-color: #FFFFFF" readonly="readonly">
                                              		VND </TD>
                                			</TR>
                                			<TR class="tblightrow">
                                     			<TD  class="plainlabel">Bằng chữ </TD>
                                     			<TD  class="plainlabel"> <span id="lblDocSo" style="font-weight:bold;font-style:italic;"></span> </TD>
                                			</TR> --%>
                                          
                               			</TABLE>
                           			</FIELDSET>

                                  </TD>
                               </TR>    
                               <TR>
                                    <TD>
                                        <TABLE  width = 100%  >
                                            <TR class="tbheader" >
                                             <th align="center" width="3%" >STT</th>
                                                <TH width="10%">Ngày hóa đơn </TH>
                                                <TH >Số hóa đơn </TH>
                                                <TH width="8%">Kênh bán hàng </TH>
                                                <TH width="8%">SO </TH>
                                                <TH width="10%">Mã sản phẩm</TH>                                                
                                                <TH width="8%" >Số lượng</TH>
                                                <TH width="8%" >Đơn vị tính </TH>
                                                <TH width="8%" >Giá Net</TH>
                                                <TH width="8%" >Tiền trước thuế</TH>
                                                 <TH width="8%" >Thuế</TH>
                                                  <TH width="8%" >Giá Gross</TH>
                                                  <TH width="8%" >Tiền sau thuế</TH>
                                                  
                                                    <TH width="8%" >Scheme</TH>
                                            </TR>
                                     <%                                      
                                         
                                    int m = 0;
                                    String lightrow = "tblightrow";
                                    String darkrow = "tbdarkrow";
                                    while (tthd.next()){
                                        if (m % 2 != 0) {%>                     
                                            <TR class= <%=lightrow%> >
                                        <%} else {%>
                                            <TR class= <%= darkrow%> >
                                        <%}%>
                                           <TD align="left"><div align="left"><%= m+1 %></div></TD>   
                                                <TD align="left"><div align="left"><%= tthd.getString("NGAY_HD") %></div></TD>                                   
                                                <TD><div align="center"><%= tthd.getString("SO_HD") %></div></TD>
                                                <TD align="center"><%= tthd.getString("KENH") %></TD>
      											 <TD align="center"><%= tthd.getString("SOSO") %></TD>	
                                                <TD align="center"><%= tthd.getString("Masp") %></TD>
                                               <TD align="center"><%= tthd.getString("soluong") %></TD>
                                                  <TD align="center"><%= tthd.getString("DVT") %></TD>
                                                 <TD align="center"><%= formatter.format(tthd.getDouble("gianet")) %></TD>
                                                   <TD align="center"><%= formatter.format(tthd.getDouble("tientruocthue")) %></TD>
                                                   <TD align="center"><%=formatter.format(tthd.getDouble("thue")) %></TD>
                                                  <TD align="center"><%= formatter.format(tthd.getDouble("giagross")) %></TD>
                                                 
                                                <TD align="center"><%= formatter.format(tthd.getDouble("TIENSAUTHUE")) %></TD>
                                                 <TD align="center"><%= tthd.getString("SHEME") %></TD>
                                                   
                                                
                                            </TR>
                                        <% m++; } %>
                                    <tr class="tbfooter">
                                   <td colspan="14"> 
                                   &nbsp;&nbsp;&nbsp;
                                   </td>
                                   </tr>
                                      </TABLE>
                                    </TD>
                              </TR>
               
                                
                          </TABLE>
                        </TD>
                    </TR>   
                       			
                </TBODY>
            </TABLE>
    </td>

  </tr>
</table>
</form>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>

</html>
<%}%>