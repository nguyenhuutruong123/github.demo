<%@page import="java.sql.SQLException"%>
<%@page import="geso.dms.center.beans.dangkykhuyenmaitichluy.IDangkykhuyenmaitichluyTTList"%>
<%@page import="geso.dms.center.beans.dangkykhuyenmaitichluy.IDangkykhuyenmaitichluyTT"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%@ page  import = "geso.dms.center.util.ChuyenNgu" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/SOHACO/index.jsp");
	}else{ 
		int[] quyen = new  int[6];
		quyen = util.Getquyen("DangkykhuyenmaitichluyTTSvl","",userId);
	%>

<% IDangkykhuyenmaitichluyTTList obj = (IDangkykhuyenmaitichluyTTList)session.getAttribute("obj"); %>
<%ResultSet dstichluy = (ResultSet)obj.getDsTichluy(); %>
<% String nnId = (String)session.getAttribute("nnId"); %>
<% if(nnId == null) {
 nnId = "vi";  
 }
 String url = util.getChuyenNguUrl("DangkykhuyenmaitichluyTTSvl","" ,nnId);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>SOHACO - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
	
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
	
<script type="text/javascript" src="../scripts/jquery.js"></script> 
<link rel="stylesheet" href="../css/jqueryautocomplete.css" type="text/css" />
<script type="text/javascript" src="../scripts/jqueryautocomplete.js"></script>





<SCRIPT language="javascript" type="text/javascript">
function clearform()
{      document.forms['dthForm'].tungay.value= '';
       document.forms['dthForm'].denngay.value= '';
       document.forms['dthForm'].diengiai.value= '';
       document.forms['dthForm'].trangthai.value= '';
       submitform();
}

function submitform()
{   
   document.forms['dthForm'].action.value= 'search';
   document.forms["dthForm"].submit();
}

function newform()
{
	document.forms['dthForm'].action.value= 'new';
	document.forms['dthForm'].submit();
}
function thongbao() {
	var tt = document.forms['dthForm'].msg.value;
	if (tt.length > 0)
		alert(document.forms['dthForm'].msg.value);

	document.forms['dthForm'].msg.value = '';
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

<form id="dthForm" name="dthForm" method="post" action="../../DangkykhuyenmaitichluyTTSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%=userId%>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="msg" value='<%=obj.getMsg()== null ? "":obj.getMsg() %>'> 
<script language="javascript" type="text/javascript">
			 thongbao();
</script>

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
                                    	<TD align="left" colspan="2" class="tbnavigation"><%=url %></TD> 
                                   
                                        <TD colspan="2" align="right" class="tbnavigation"><%=ChuyenNgu.get("Chào mừng",nnId) %>  <%=userTen %> &nbsp;</TD>
                                    </tr>
                                  </table>
                              </TD>
                         </TR> 
                     </TABLE>
                    
                     <TABLE border="0" width="100%" cellspacing = 0 cellpadding = 0>

                        <TR>
                            <TD width="100%" align="left">
                                <FIELDSET>
                                <LEGEND class="legendtitle">&nbsp;<%=ChuyenNgu.get("Tiêu chí hiển thị",nnId) %> &nbsp;</LEGEND>

                                <TABLE  width="100%" cellpadding="4" cellspacing="0">
                                <TR>
                                	<TD width="19%"  class="plainlabel" ><%=ChuyenNgu.get("Diễn giải",nnId) %></TD>
								  	<TD class="plainlabel" ><input type="text" name= "diengiai" value ="<%= obj.getDiengiai() %>" onchange="submitform();">
								</TD>
								</TR>
								
									<TR>
										<TD class="plainlabel" ><%=ChuyenNgu.get("Từ ngày",nnId) %></TD>
										<td class="plainlabel">
											<input type="text"  class="days" size="11"
													id="tungay" name="tungay" value="<%= obj.getTungay() %>" maxlength="10" readonly onchange="submitform();" />
										</td>
									</TR>
									<TR>
									  <TD class="plainlabel" ><%=ChuyenNgu.get("Ðến ngày",nnId) %></TD>
									  <td class="plainlabel">
											<input type="text"  class="days" size="11"
													id="denngay" name="denngay" value="<%= obj.getDenngay() %>" maxlength="10" readonly onchange="submitform();" />
										</td>
									</TR>
								
								 <TR class="plainlabel">
	                              <TD class="plainlabel"><%=ChuyenNgu.get("Trạng thái",nnId) %></TD>
	                              <TD class="plainlabel">
	                              	<select name = "trangthai" onchange="submitform();">
	                              	<%if(obj.getTrangthai().equals("1")) {%>
	                              		<option value="" ></option>
	                              		<option value="1" selected="selected"><%=ChuyenNgu.get("Đã chốt",nnId) %></option>
	                              		<option value="2" ><%=ChuyenNgu.get("Đã xóa",nnId) %></option>
	                              		<option value="0" ><%=ChuyenNgu.get("Đang xử lý",nnId) %></option>
	                              	<%} else if(obj.getTrangthai().equals("2")) {%>
	                              		<option value="" ></option>
	                              		<option value="1" ><%=ChuyenNgu.get("Đã chốt",nnId) %></option>
	                              		<option value="2" selected="selected"><%=ChuyenNgu.get("Đã xóa",nnId) %></option>
	                              		<option value="0" ><%=ChuyenNgu.get("Đang xử lý",nnId) %></option>
	                              	<%} else if(obj.getTrangthai().equals("0")) {%>
	                              		<option value="" ></option>
	                              		<option value="11" ><%=ChuyenNgu.get("Đã chốt",nnId) %></option>
	                              		<option value="2" ><%=ChuyenNgu.get("Đã xóa",nnId) %></option>
	                              		<option value="0" selected="selected"><%=ChuyenNgu.get("Đang xử lý",nnId) %></option>
	                              	<%}else {%>
	                              		<option value="" selected="selected"></option>
	                              		<option value="1" ><%=ChuyenNgu.get("Đã chốt",nnId) %></option>
	                              		<option value="2" ><%=ChuyenNgu.get("Đã xóa",nnId) %></option>
	                              		<option value="0" ><%=ChuyenNgu.get("Đang xử lý",nnId) %></option>
	                              	<%} %>
	                              	</select>
							
	                              </TD>
                              </TR>
                              
                                <TR>
                                	<TD class="plainlabel" align="left">
                                		<a class="button2" href="javascript:clearform()">
	<img style="top: -4px;" src="../images/button.png" alt=""><%=ChuyenNgu.get("Nhập lại",nnId) %></a>&nbsp;&nbsp;&nbsp;&nbsp;	
                                	<!-- 
                                		<INPUT name="reinitialiser" type="button" value="Nhap lai" onClick="clearform();"> -->
                                		</TD>
                                	<TD class="plainlabel" colspan=4>&nbsp;</TD>
                                </TR>
                                			
									
								</TABLE>
                              </FIELDSET>
                          	</TD>			
                          </TR>
                      </TABLE>
                      <TABLE width="100%" cellspacing = 0 cellpadding=0>
                        <TR>
                            <TD >
                                <FIELDSET>
                                <LEGEND class="legendtitle">&nbsp;<%=ChuyenNgu.get("Khuyến mãi tích lũy",nnId) %> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                
                                <%if(quyen[Utility.THEM]!=0){ %>
                                 	<a class="button3"  onclick="newform()">
    								<img style="top: -4px;" src="../images/New.png" alt=""><%=ChuyenNgu.get("Tạo mới",nnId) %> </a>
    							<%} %>                       
                                <!-- 
								<INPUT name="new" type="button" value="Tao moi" onClick="newform();"> -->
								 </LEGEND>                                
                      	        <TABLE width="100%">
                                <TR>
                                    <TD>
                                        <TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
                                        <TR class="tbheader">
                                            <TH ><%=ChuyenNgu.get("Mã chương trình khuyến mãi",nnId) %> </TH>
                                            <TH ><%=ChuyenNgu.get("Diễn giải",nnId) %> </TH>
                                            <TH ><%=ChuyenNgu.get("Ngày đăng ký",nnId) %></TH>
                                            <TH ><%=ChuyenNgu.get("Ngày kết thúc",nnId) %></TH>
                                             <TH ><%=ChuyenNgu.get("Trạng thái",nnId) %></TH>
                                             <TH align="center">&nbsp;<%=ChuyenNgu.get("Tác vụ",nnId) %> </TH>
                                        </TR>
                              <%
								int m = 0;
								String lightrow = "tblightrow";
								String darkrow = "tbdarkrow";
								if(dstichluy!=null)
								while (dstichluy.next()){							

									if (m % 2 != 0) {%>						
										<TR class= <%=lightrow%> >
									<%} else {%>
										<TR class= <%= darkrow%> >
									<%}%>
									
                                     <TD align="left" class="textfont"><%=dstichluy.getString("scheme")%> </TD>
                                     <TD align="left" class="textfont"><%=dstichluy.getString("diengiai")%> </TD>
									 <TD align="left" class="textfont"><%=dstichluy.getString("tungay")%> </TD>
									 <TD align="left" class="textfont"><%=dstichluy.getString("denngay")%> </TD>
									 <td>
									 	<%if(dstichluy.getString("trangthai").equals("1")){ %>
									 		<%=ChuyenNgu.get("Đã chốt",nnId) %>
									 	<%}else if(dstichluy.getString("trangthai").equals("2")) { %>
									 		<%=ChuyenNgu.get("Đã xóa",nnId) %>
									 	<%}else{ %>
									 		<%=ChuyenNgu.get("Đang xử lý",nnId) %>
									 	<%}%>
									 </td>
								     <TD align="center">
								     <%if(dstichluy.getString("trangthai").equals("0")){ %>
								     
								     		<%if(quyen[Utility.SUA]!=0){ %>
								     		<A href = "../../DangkykhuyenmaitichluyTTUpdateSvl?userId=<%=userId%>&update=<%=dstichluy.getString("pk_seq")%>"><img src="../images/Edit20.png" alt="Cap nhat" width="20" height="20" longdesc="Cap nhat" border = 0></A>
									   		<%} %>
									   		
									   		<%if(quyen[Utility.CHOT]!=0){ %>
									   		<A href = "../../DangkykhuyenmaitichluyTTUpdateSvl?userId=<%=userId%>&chot=<%=dstichluy.getString("pk_seq")%>"><img src="../images/Chot.png" alt="Chot" width="20" height="20" longdesc="Chot" border = 0 onclick="if(!confirm('Bạn có muốn duyệt đăng ký tích lũy này?')) return false;"></A>
									     	<%} %>
									     	
									     	<%if(quyen[Utility.XOA]!=0){ %>
									     	<A href = "../../DangkykhuyenmaitichluyTTUpdateSvl?userId=<%=userId%>&delete=<%=dstichluy.getString("pk_seq")%>"><img src="../images/Delete.png" alt="Xoa" width="20" height="20" longdesc="Xoa" border = 0 onclick="if(!confirm('Bạn có muốn xóa chỉ tiêu này?')) return false;"></A> 
									   		<%} %>
									   
									   <%} else { %>
									   		<%if(quyen[Utility.CHOT]!=0 && dstichluy.getString("trangthai").equals("1")){ %>
									   		<A href = "../../DangkykhuyenmaitichluyTTUpdateSvl?userId=<%=userId%>&unchot=<%=dstichluy.getString("pk_seq")%>"><img src="../images/unChot.png" alt="UnChot" width="20" height="20" longdesc="Chot" border = 0 onclick="if(!confirm('Bạn có muốn bỏ duyệt đăng ký tích lũy này?')) return false;"></A>
									     	<%} %>
									   		<%if(quyen[Utility.XEM]!=0){ %>
									    	<A href = "../../DangkykhuyenmaitichluyTTUpdateSvl?userId=<%=userId%>&dislay=<%=dstichluy.getString("pk_seq")%>"><img src="../images/Display20.png" alt="Hien thi" width="20" height="20" longdesc="Hien thi" border = 0 "></A>
											<A href="../../DangkykhuyenmaitichluyExcelSvl?userId=<%=userId%>&excel=<%=dstichluy.getString("PK_SEQ") %>">
																<img src="../images/excel.gif" alt="In ds khách hàng chi tiết Pdf" width="20"
																height="20" longdesc="In file Pdf" border=0>
																</A>
											
											<%} %>
											
									  <%} %>
									 </TD>
								 </TR>
								<%
									m++;
								} %>
                           
                                	<TR>
                                    	<TD align="center" colspan="12" class="tbfooter">&nbsp;</TD>
                                	</TR>
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


</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</HTML>
<% 	
if(obj != null){
	obj.DBclose();
	obj = null;
}
session.setAttribute("obj",null);
	try{
	
		if(dstichluy != null)
			dstichluy.close();
	
	}
	catch (SQLException e) {}

%>
<%}%>