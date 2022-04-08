<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.beans.report.IBcHoaDon" %>
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

<% IBcHoaDon obj = (IBcHoaDon)session.getAttribute("obj"); %>
<% ResultSet hdRs = obj.getHoadonRs(); %>
<% NumberFormat formatter = new DecimalFormat("#,###,###.#######");%>
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

</SCRIPT>


<!-- BEGIN RENDER AUTO -->

<% if (!geso.dms.center.util.GlobalValue.getDevmode()) { %>
	<script type="text/javascript" src="../scripts/disableF12.js"></script>
<% } %>

<!-- END RENDER AUTO -->
</HEAD>
<body onLoad = 'init();'>
<!-- BEGIN RENDER AUTO -->
<%if(!geso.dms.center.util.GlobalValue.getDevmode()){ %>
	<noscript> 
	 	<meta http-equiv="refresh" content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")  %>">
		<!-- //window.location = "https://www.google.com"; -->
	</noscript>
<%} %>
<!-- END RENDER AUTO -->

<form name="dctkForm" method="post" action="../../TBL_PXK_Display">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<INPUT name="userId" type="hidden" value='<%=userId %>' size="30">
<INPUT name="id" type="hidden" value='<%=obj.getSohoadon() %>' size="30">
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
                                               <TD align="left" colspan="1" class="tbnavigation">Báo cáo quản trị > Phiếu xuất kho > Hiển thị </TD>
                                               <TD colspan="1" align="right" class="tbnavigation">Chào mừng Bạn  <%= userTen %> &nbsp;</TD>

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
                                <TR>
                                    <TD  align="left">                       
                                        <FIELDSET>
                                        <LEGEND class="legendtitle">&nbsp;Thông tin phiếu xuất kho </LEGEND>
                                        <TABLE cellpadding = "6" cellspacing = "0" width = "100%" border = 0>
                                        	<TR> 
                                        		<TH style="width:20%"></TH>
                								<TH style="width:100%"></TH>
                							</TR>
                               			</TABLE>
                           			</FIELDSET>
                                  </TD>
                               </TR>    
                               <TR>
                                    <TD>
                                        <TABLE  width = 100%  >
                                            <TR class="tbheader" >
                                             	<th align="center" width="3%" >STT</th>
                                                <TH >Ngày chứng từ </TH>
		                                        <TH >Số chứng từ </TH>
		                                        <TH >Mã NPP </TH>
		                                        <TH >Tên NPP </TH>
		                                        <TH >Kênh bán hàng </TH>
		                                        <TH >Số SO </TH>
		                                        <TH >DVKD </TH>
		                                        <TH >Mã SP </TH>
		                                        <TH >DVT </TH>
		                                        <TH >Số lượng </TH>
		                                        <TH >Đơn giá </TH>
		                                        <TH >Thành tiền </TH>
		                                        <TH >Scheme </TH>
		                                        <TH >Trạng thái </TH>
                                            </TR>
                                     <%                                      
                                         
                                     int m = 0, n = 1;
                                     String lightrow = "tblightrow";
                                     String darkrow = "tbdarkrow";
                                     while (hdRs.next()){
                                     	//System.out.println("MASP "+hdRs.getString("MASP")+"DVT "+hdRs.getString("DVT")+"KENH "+hdRs.getString("KENH")+"SOLUONG "+hdRs.getString("SOLUONG"));
                                         if (m % 2 != 0) {%>                     
                                             <TR class= <%=lightrow%> >
                                         <%} else {%>
                                             <TR class= <%= darkrow%> >
                                         <%}%>
                                         		<TD align="center"><%= n %></TD>
                                                <TD align="left"><%= hdRs.getString("NGAYCHUNGTU") %></TD>                                   
                                                <TD align="center"><%= hdRs.getString("SOCHUNGTU") %></TD>
                                                <TD align="center"><%= hdRs.getString("MANHAPP") %></TD>
                                                <TD align="center"><%= hdRs.getString("TENNPP") %></TD>
                                                <TD align="center"><%= hdRs.getString("KENH") %></TD>
                                                <TD align="center"><%= hdRs.getString("SOSO") %></TD>
                                                <TD align="center"><%= hdRs.getString("DVKINHDOANH") %></TD>
                                                <TD align="center"><%= hdRs.getString("MASP") %></TD>
                                                <TD align="center"><%= hdRs.getString("DVT") %></TD>
                                                <TD align="center"><%= hdRs.getString("SOLUONG") %></TD>
                                                <TD align="center"><%= formatter.format(hdRs.getDouble("DONGIA")) %></TD>
                                                <TD align="center"><%= formatter.format(hdRs.getDouble("TONGCONG")) %></TD>
                                                <TD align="center"><%= hdRs.getString("SHEME")==null?"":hdRs.getString("SHEME") %></TD> 
                                                <TD align="center"><%= hdRs.getString("TRANGTHAI") %></TD>
                                             </TR>
                                         <% m++; n++; } %>
                                    <tr class="tbfooter">
                                   <td colspan="13"> 
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
<%

if(obj!=null){
	obj.closeDB();
	obj = null;
}
session.setAttribute("obj", null);
}
%>