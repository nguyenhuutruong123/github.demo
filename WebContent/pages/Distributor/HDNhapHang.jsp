<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.distributor.beans.hdnhaphang.IHDnhaphangList" %>
<%@ page  import = "java.sql.ResultSet" %>
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

<% IHDnhaphangList obj = (IHDnhaphangList)session.getAttribute("obj"); %>
<% ResultSet hdnhaphanglist = (ResultSet)obj.getNhaphangList(); %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

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

<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
<script type="text/javascript" src="../scripts/jquery.js"></script> 
<link rel="stylesheet" href="../css/jqueryautocomplete.css" type="text/css" />
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">

<script type="text/javascript" src="../scripts/jqueryautocomplete.js"></script>
<script type="text/javascript" language="JavaScript" src="../scripts/simplecalendar.js"></script>

<SCRIPT language="javascript" type="text/javascript">


function clearform()
{
    document.nhForm.sku.value = "";
    document.nhForm.tungay.value = "";
    document.nhForm.denngay.value = "";
    document.nhForm.trangthai.selectedIndex = 0;
    document.forms['nhForm'].submit();
}

function submitform()
{   	
   document.nhForm.action.value = 'search';   
   document.forms["nhForm"].submit();
}

</SCRIPT>

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

<form name="nhForm" method="post" action="../../HDnhaphangSvl"> 
 <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%=userId%>'>
<input type="hidden" name="action" value='1'>
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
    height="100%"  bgcolor="#FFFFFF">
    <TR>
        <TD colspan="4" align='left' valign='top'> 
        
        <TABLE width="100%" cellspacing="0" cellpadding="0">
        	<TR>
            	<TD>
                	<TABLE width="100%" cellspacing="1" cellpadding="0">
                    	<TR>
                        	<TD align="left" class="tbnavigation">
                            	<table width="100%" border="0" cellpadding="0" cellspacing="0">
                                	<tr height="22">
                                    	<TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý tồn kho &gt; &nbsp;Mua hàng từ Nhà cung cấp &gt; &nbsp;Nhận hàng</TD>
                                   
                                        <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn  <%= obj.getNppTen() %>  &nbsp;</TD>
                                    </tr>
                                  </table>
                              </TD>
                         </TR> 
                     </TABLE>
                    
                     <TABLE border="0" width="100%" cellspacing = 0 cellpadding = 0>

                        <TR>
                            <TD width="100%" align="left">
                                <FIELDSET>
                                <LEGEND class="legendtitle">&nbsp;Tiêu chí hiển thị &nbsp;</LEGEND>

                                <TABLE  width="100%" cellpadding="4" cellspacing="0">
                                    <TR>
                                        <TD width="19%" class="plainlabel">Chứa SKU </TD>
                                      	<TD width="81%" class="plainlabel">
                                        	<input type="text" name="sku" value="<%= obj.getSKU() %>" size="40" onChange="submitform();">
                                      		
                                      	</TD>
                                    </TR>
								<TR>
											<TD class="plainlabel" >Từ ngày </TD>
								  	<TD class="plainlabel" >
										<TABLE cellpadding="0" cellspacing="0">
										<TR><TD>
											<input class="days" type="text" name="tungay" value="<%=obj.getTungay() %>" size="20" onchange="submitform();">
										</TD>

                                    	</TR>
										</TABLE>
									</TD>
										</TR>
								<TR>
                                    <TD class="plainlabel" >Đến ngày </TD>
								  	<TD class="plainlabel" >
							  			<TABLE cellpadding="0" cellspacing="0">
							  				<TR>
							  					<TD>
													<input class="days" type="text" name="denngay" value="<%=obj.getDenngay() %>" size="20" onchange="submitform();">
												</TD>

                	                     	</TR>
										</TABLE>
									</TD>

								</TR>
								<TR>
									<TD class="plainlabel">Trạng thái</TD>
									<TD class="plainlabel"><select name="trangthai" onChange = "submitform();">
								  		<option value=""></option>
									<%if (obj.getTrangthai().equals("0")){ %>								  		
								    	<option value="0" selected>Chưa nhận hàng </option>
								    	<option value="1">Đã nhận hàng</option>
								    	<option value="2">Đã hủy</option>
									<%}else{ 							
								  		if (obj.getTrangthai().equals("1")){ %>								  		
								    	<option value="0" >Chưa nhận hàng </option>
								    	<option value="1" selected>Đã nhận hàng</option>
								    	<option value="2">Đã hủy</option>
								    	
								    	<%}else if (obj.getTrangthai().equals("2")){ %>								  		
								    	<option value="0" >Chưa nhận hàng </option>
								    	<option value="1" >Đã nhận hàng</option>
								    	<option value="2" selected>Đã hủy</option>
									<%  }else{%>								  		
								    	<option value="0" >Chưa nhận hàng </option>
								    	<option value="1" >Đã nhận hàng</option>
								    	<option value="2">Đã hủy</option>
									
									<%}}%>
								    	</select></TD>
								</TR>

                                <TR>
                                	<TD class="plainlabel" align="left">
                                		 
                                		 <a class="button2" href="javascript:clearform()">
	<img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;	 
	     		 <!-- 
                                    	<INPUT name="reinitialiser" type="button" value="Nhap lai" onClick="clearform();">
                                    	 -->
                                    	</TD>
                                    <TD class="plainlabel" colspan=4>&nbsp;</TD>
                                 </TR>
                               </TABLE>
                               </FIELDSET>
                              </TD>
							</TR>
									
						</TABLE>
                              
                          	</TD>			
                          </TR>
                      </TABLE>
                      <TABLE width="100%" cellspacing = 0 cellpadding=0>
                        <TR>
                            <TD >
                                <FIELDSET>
                                <LEGEND class="legendtitle">&nbsp;Nhận hàng</LEGEND> 
                                
    
                      	        <TABLE width="100%">
                                <TR>
                                    <TD>
                                        <TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
                                        <TR class="tbheader">
                                            <TH >Ngày chứng từ </TH>
                                             <TH >Ngày nhận</TH>
                                            <TH >Số chứng từ </TH>
                                            <TH> Số đơn hàng</TH>
                                            <TH >Đơn vị kinh doanh </TH>
                                            <TH >Kênh bán hàng </TH>
                                            <TH >Tổng số tiền có VAT (VND) </TH>
											<TH >Trạng thái </TH>
                                            <TH >Người tạo </TH>
                                            <TH >Người sửa </TH>
                                            <TH align="center">&nbsp;Tác vụ </TH>
                                        </TR>
         
                               <% 
                            NumberFormat formatter = new DecimalFormat("#,###,###");
                            int m = 0;
                            String lightrow = "tblightrow";
                            String darkrow = "tbdarkrow";
							if(hdnhaphanglist != null){
								try{								
                                    while (hdnhaphanglist.next()){
                                        	
                                       	if (m % 2 != 0) {%>                     
                                        	<TR class= <%=lightrow%> >
                                        <%} else {%>
                                           	<TR class= <%= darkrow%> >
                                        	<%}%>
                                                <TD align="left"><div align="left"><%= hdnhaphanglist.getString("ngaychungtu") %></div></TD>
                                                 <TD><div align="center"><%= hdnhaphanglist.getString("ngaynhan") %></div></TD>                                   
                                                <TD><div align="center"><%= hdnhaphanglist.getString("chungtu") %></div></TD>
                                                <TD><div align="center"><%= hdnhaphanglist.getString("dathang_fk") %></div></TD>
                                                <TD><div align="center"><%= hdnhaphanglist.getString("donvikinhdoanh") %></div></TD>
                                                <TD><div align="center"><%= hdnhaphanglist.getString("kbh") %></div></TD>
                                                <TD><div align="center"><%= formatter.format(Float.parseFloat(hdnhaphanglist.getString("sotienavat"))) %></div></TD>
                                                <% if (hdnhaphanglist.getString("trangthai").equals("0")){ %>
                                                   		<TD><div align="center">Chưa nhận hàng</div></TD>
                                                <%}else { 
                                                   if (hdnhaphanglist.getString("trangthai").equals("1")) {%>
                                                   		<TD><div align="center">Đã nhận hàng</div></TD>
                                                  <%}
                                                   else if (hdnhaphanglist.getString("trangthai").equals("2")) {%>
                                              		<TD><div align="center">Đã hủy</div></TD>   
                                                <%}}%>
                                                 
                                                <TD align="center"><%= hdnhaphanglist.getString("nguoitao") %></TD>
                                                <TD align="center"><%= hdnhaphanglist.getString("nguoisua")%></TD>
                                                <TD align="center">
                            	                    <TABLE border = 0 cellpadding="0" cellspacing="0">
                                	                    <TR><TD>                                	                    	
                                	                    		<A href = "../../HDnhaphangDisplaySvl?userId=<%=userId%>&display=<%=hdnhaphanglist.getString("chungtu") %>"><img src="../images/Display20.png" alt="Hiển thị" width="20" height="20" longdesc="Hiển thị" border = 0 "></A>														                                   	                 
                                        	            </TD>
                                                   
                                            		 </TABLE>
                                                </TD>
                                            </TR>
                                        <% m++; } 
                                        }catch(java.sql.SQLException e){}
                               		}%>
                               			<tr class="tbfooter" > <td colspan="12" >&nbsp;</td> </tr> 
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
      </TD>
     </TR>
    </TABLE>
</form>


</BODY>
</HTML>
<% if(!(hdnhaphanglist == null)) hdnhaphanglist.close(); %>
<% if(obj != null){
	obj.DBclose();
	obj = null;
	session.setAttribute("obj",null);
} %>
<% }%>