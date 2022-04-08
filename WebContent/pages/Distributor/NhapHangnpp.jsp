<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.distributor.beans.Nhaphangnpp.INhaphangnppList" %>
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
	}else{
		/* int[] quyen = new  int[6];
		quyen = util.Getquyen("NhaphangnppSvl","",userId); */
	%>

<% INhaphangnppList obj = (INhaphangnppList)session.getAttribute("obj"); %>
<% ResultSet Nhaphangnpplist = (ResultSet)obj.getNhaphangList(); %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

	<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
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
<script type="text/javascript" src="../scripts/jquery.js"></script> 
<link rel="stylesheet" href="../css/jqueryautocomplete.css" type="text/css" />
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">

<script type="text/javascript" src="../scripts/jqueryautocomplete.js"></script>

<SCRIPT language="javascript" type="text/javascript">


function clearform()
{
    document.nhForm.sku.value = "";
    document.nhForm.tungay.value = "";
    document.nhForm.denngay.value = "";
    document.nhForm.trangthai.value="";
    document.nhForm.sodh.value="";
    document.nhForm.soct.value="";
    document.forms['nhForm'].submit();
}

function submitform()
{   
   document.forms["nhForm"].submit();
}

function xuatExcel()
{
	document.forms['nhForm'].action.value= 'toExcel';
	document.forms['nhForm'].submit();
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

<form name="nhForm" method="post" action="../../NhaphangnppSvl"> 
 <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%=userId%>'>
<input type="hidden" name="action" value='1'>
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
    height="100%"  bgcolor="#FFFFFF">
    <TR>
        <TD colspan="4" align='left' valign='top'> 
        
        <TABLE width="100%" cellspacing="0" cellpadding="0" >
        	<TR>
            	<TD>
                	<TABLE width="100%" cellspacing="1" cellpadding="0" >
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
                    
                     <TABLE border="0" width="100%" cellspacing = 0 cellpadding = 0 style="background-color: #C5E8CD;">

                        <TR>
                            <TD width="100%" align="left">
                                <FIELDSET>
                                <LEGEND class="legendtitle">&nbsp;Tiêu chí hiển thị &nbsp;</LEGEND>

                                <TABLE  width="100%" cellpadding="4" cellspacing="0" >
                                    <TR style="display: none;" >
                                        <TD width="19%" class="plainlabel">Chứa SKU </TD>
                                      	<TD width="81%" class="plainlabel">
                                        	<input type="text" name="sku" value="<%= obj.getSKU() %>" size="40" onChange="submitform();">
                                      		
                                      	</TD>
                                    </TR>
									<TR>
										<TD class="plainlabel" width="120px" >Từ ngày </TD>
									  	<TD class="plainlabel" >
											<input class="days" type="text" name="tungay" value="<%=obj.getTungay() %>" size="20" onchange="submitform();">				
										</TD>
									</TR>
									<TR>
	                                    <TD class="plainlabel" >Đến ngày </TD>
									  	<TD class="plainlabel" >
											<input class="days" type="text" name="denngay" value="<%=obj.getDenngay() %>" size="20" onchange="submitform();">
										</TD>
	
									</TR>
									<TR>
										<TD class="plainlabel">Trạng thái</TD>
										<TD class="plainlabel">
											<select name="trangthai" onChange = "submitform();">
									  		
											<%if (obj.getTrangthai().equals("0")){ %>								  		
										    	<option value="0" selected>Chưa nhận hàng </option>
										    	<option value="1">Đã nhận hàng</option>
										    	<option value=""></option>
											<%  } else if (obj.getTrangthai().equals("1")){ %>								  		
										    	<option value="0" >Chưa nhận hàng </option>
										    	<option value="1" selected>Đã nhận hàng</option>
										    	<option value=""></option>
											<% } else { %>
												<option value="0" >Chưa nhận hàng </option>
										    	<option value="1">Đã nhận hàng</option>
										    	<option value="" selected ></option>
											<% } %>
											
									    	</select>
									    </TD>
									</TR>
									
									<TR>
										<TD class="plainlabel" width="120px" >Số chứng từ </TD>
									  	<TD class="plainlabel" >
											<input  type="text" name="soct" value="<%=obj.getSoct() %>" size="20" onchange="submitform();">				
										</TD>
									</TR>
									
									<TR>
										<TD class="plainlabel" width="120px" >Số đơn hàng </TD>
									  	<TD class="plainlabel" >
											<input  type="text" name="sodh" value="<%=obj.getSodh() %>" size="20" onchange="submitform();">				
										</TD>
									</TR>
									
									
                                <TR>
                                	<TD class="plainlabel" >
                                		 
                                		 <a class="button2" href="javascript:clearform()" width="60px;">
												<img style="top: -4px;" src="../images/button.png" alt="" >Nhập lại</a>	 </TD>


										<td>	<a class="button2" href="javascript:xuatExcel()"> <img style="top: -4px;" src="../images/button.png" alt="">Xuất Excel </a>
									

                                    	</TD>
                                    <TD class="plainlabel" colspan=4>&nbsp;</TD>
                                 </TR>
                               </TABLE>
                               </br>
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
                                            <TH >Ngày chứng từ</TH>
                                            <TH >Ngày nhận</TH>
                                            <TH >Số nhận hàng</TH>
                                            <TH> Số chứng từ</TH>
                                            <TH >ĐVKD </TH>
                                            <TH >Kênh bán hàng </TH>
                                            <TH >Tổng tiền</TH>
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
							if(Nhaphangnpplist != null){
								try{								
                                    while (Nhaphangnpplist.next()){
                 
                                       	if (m % 2 != 0) {%>                     
                                        	<TR class= <%=lightrow%> >
                                        <%} else {%>
                                           	<TR class= <%= darkrow%> >
                                        	<%}%>
                                                <TD align="left"><div align="left"><%= Nhaphangnpplist.getString("ngaychungtu") %></div></TD>
                                                <TD><div align="center"><%= Nhaphangnpplist.getString("ngaynhan") %></div></TD>                                   
                                                <TD><div align="center"><%= Nhaphangnpplist.getString("chungtu") %></div></TD>
                                                <TD style="text-align: center;" >
                                                	<% if (Nhaphangnpplist.getString("trangthai").equals("1")){ %>
                                                		<%= Nhaphangnpplist.getString("dathang_fk")==null?"NA":Nhaphangnpplist.getString("dathang_fk")%>
                                                	<% }  %>
                                                </TD>
                                                <TD><div align="center"><%= Nhaphangnpplist.getString("donvikinhdoanh") %></div></TD>
                                                <TD><div align="center"><%= Nhaphangnpplist.getString("kbh") %></div></TD>
                                                <TD><div align="center"><%= formatter.format(Float.parseFloat(Nhaphangnpplist.getString("sotienavat"))) %></div></TD>
                                                
                                                <% if (Nhaphangnpplist.getString("trangthai").equals("0")){ %>
                                                   		<TD><div align="center">Chưa nhận hàng</div></TD>
                                                <%}else { 
                                                   if (Nhaphangnpplist.getString("trangthai").equals("1")) {%>
                                                   		<TD><div align="center">Đã nhận hàng</div></TD>
                                                  <%}else if (Nhaphangnpplist.getString("trangthai").equals("2")) {%>
                                                   		<TD><div align="center">Hóa đơn hủy</div></TD>
                                                    <%  }}%>
                                                <TD align="center"><%= Nhaphangnpplist.getString("nguoitao") %></TD>
                                                <TD align="center"><%= Nhaphangnpplist.getString("nguoisua")%></TD>
                                                <TD align="center">
                            	                    <TABLE border = 0 cellpadding="0" cellspacing="0">
                                	                    <TR><TD>
                                	                    	<% if (Nhaphangnpplist.getString("trangthai").equals("0") ) {%>
                                	                    	
                                	                    		<%-- <%if(quyen[Utility.SUA]!=0){ %> --%>
	                                    	                    	<A href = "../../NhaphangnppUpdateSvl?userId=<%=userId%>&update=<%=Nhaphangnpplist.getString("chungtu") %>"><img src="../images/Chot.png" alt="Nhận hàng" width="20" height="20" longdesc="Nhận hàng" border = 0 "></A>
															<%-- 	<%} %> --%>
																
															<%}else
															{ %>
															
																<%-- <%if(quyen[Utility.XEM]!=0){ %> --%>
                                	                    			<A href = "../../NhaphangnppUpdateSvl?userId=<%=userId%>&display=<%=Nhaphangnpplist.getString("chungtu") %>"><img src="../images/Display20.png" alt="Hiển thị" width="20" height="20" longdesc="Hiển thị" border = 0 "></A>
																<%-- <%} %> --%>
															<%} %>                                    	                   
                                        	            </TD>
                                                   
                                            		 </TABLE>
                                                </TD>
                                            </TR>
                                        <% m++; } 
								}catch(Exception e){e.printStackTrace();}
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
</form>
</BODY>
</HTML>
<% if(!(Nhaphangnpplist == null)) Nhaphangnpplist.close(); %>
<% if(obj != null){
	obj.DBclose();
	obj = null;
	session.setAttribute("obj",null);
} %>
<% }%>