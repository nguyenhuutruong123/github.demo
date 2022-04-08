<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.distributor.beans.tradonhang.ITraDonHangList" %>
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

<% ITraDonHangList obj = (ITraDonHangList)session.getAttribute("obj"); %>
<% ResultSet dhlist = (ResultSet)obj.getDhList();%>
<% obj.setNextSplittings(); 

	int[] quyen = new  int[6];
	quyen = util.Getquyen("TraDonHangSvl","", userId);
%>
 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>DDT - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">
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
<script type="text/javascript" src="../scripts/phanTrang.js"></script>

<SCRIPT language="javascript" type="text/javascript">
function submitform()
{   
   document.forms["ddhForm"].submit();
}

function submitForm(arg){
	
	document.forms["ddhForm"].action.value = arg;
	document.forms["ddhForm"].submit();
	
}

function newform()
{
	document.forms['ddhForm'].action.value= 'new';
	document.forms['ddhForm'].submit();
}


function clearform()
{
	document.forms["ddhForm"].sku.value="";
	document.forms["ddhForm"].tungay.value="";
	document.forms["ddhForm"].denngay.value="";
	document.forms["ddhForm"].trangthai.value="";
	document.forms["ddhForm"].submit();
	}


function thongbao()
{var tt = document.forms['ddhForm'].msg.value;
	if(tt.length>0)
    alert(document.forms['ddhForm'].msg.value);
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

<form id="ddhForm" name="ddhForm" method="post" action="../../TraDonHangSvl"> 
 <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%=userId%>'>

<input type="hidden" name="action" value="1" >
<input type="hidden" name="crrApprSplitting" value="<%= obj.getCrrApprSplitting() %>" >
<input type="hidden" name="nxtApprSplitting" value="<%= obj.getNxtApprSplitting() %>" >
<input type="hidden" name="msg" value='<%=obj.getMsg() %>'>
<script language="javascript" type="text/javascript">
    thongbao();
    <%obj.setMsg("");%>
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
                                    	<TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý bán hàng &gt; Trả đơn hàng </TD> 
                                   
                                        <TD colspan="2" align="right" class="tbnavigation">Chào mừng Cửa hàng/ KH VP <%= obj.getNppTen() %> &nbsp;</TD>
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
	<TD class="plainlabel" >Từ ngày</TD>
	<td class="plainlabel">
		<input type="text"  class="days" size="11"
				id="tungay" name="tungay" value="<%= obj.getTungay() %>" maxlength="10" readonly />
	</td>
</TR>
<TR>
  <TD class="plainlabel" >Ðến ngày</TD>
  <td class="plainlabel">
		<input type="text"  class="days" size="11"
				id="denngay" name="denngay" value="<%= obj.getDenngay() %>" maxlength="10" readonly />
	</td>
</TR>
								<TR>
														<TD class="plainlabel">Trạng thái</TD>
														<TD class="plainlabel"><select id="trangthai" name="trangthai" onChange="submitform();">
														<option value=""></option>
																<%if (obj.getTrangthai().equals("1")){ %>
																<option value="0" >Chưa Chốt</option>
																<option value="1" selected>Đã chốt</option>
																<option value="2">Đã hủy</option>
																<%}else 							
								  						if (obj.getTrangthai().equals("1")){ %>
								  								<option value="0" >Chưa Chốt</option>
																<option value="1" selected >Đã chốt</option>
																<option value="2">Đã hủy</option>
																<%}else
														if (obj.getTrangthai().equals("2")){ %>
																<option value="0" >Chưa Chốt</option>
																<option value="1"  >Đã chốt</option>
																<option value="2" selected>Đã hủy</option>
													<%}   else { %>
																<option value="0" >Chưa Chốt</option>
																<option value="1"  >Đã chốt</option>
																<option value="2" >Đã hủy</option>
																<%  }   %>
														</select>
														</TD>
													</TR>
                                    		<TR>
                                        			                                       				
                                        		<TD class="plainlabel" align="left" colspan=2>
                                        				<a class="button1" href="javascript:submitForm('Hien thi')">
    													<img style="top: -4px;" src="../images/Search30.png" alt="">Tìm kiếm </a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;                                                       				
                                        					<a class="button2" href="javascript:clearform()">
															<img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;	
                                        				<!--  <INPUT name="reinitialiser" type="button" value="Nhap  lai gia tri" onclick="clearform()" >-->

                                        				</TD>
                                            		
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
                                <LEGEND class="legendtitle">&nbsp;Trả đơn hàng&nbsp;&nbsp;
                                    <%if(quyen[0]!= 0){ %>
                                	<a class="button3"  onclick="newform()">
    								<img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>                            
                                	<%} %>
                                </LEGEND> 
                                
    
                      	        <TABLE width="100%">
                                <TR>
                                    <TD>
                                        <TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
                                        <TR class="tbheader">
                                            <TH >Ngày trả</TH>
                                            <TH >Số chứng từ </TH>
                                            <TH >Mã đơn hàng </TH>
                                            <TH >Khách hàng </TH>
                                            <TH >Người tạo </TH>
											<TH >Ngày tạo </TH>
                                            <TH >Người sửa </TH>
                                            <TH >Ngày sửa </TH>
                                            <TH >Trạng thái </TH>
                                            <TH align="center">&nbsp;Tác vụ </TH>
                                        </TR>
         
                               <% 
                            NumberFormat formatter = new DecimalFormat("#,###,###");
                            int m = 0;
                            String lightrow = "tblightrow";
                            String darkrow = "tbdarkrow";
                            String style= "";
							if(dhlist != null){%>
								
								<% try{								
                                    while (dhlist.next()){
                                    	 String trangthai=dhlist.getString("trangthai");
                                    	 if(trangthai.equals("1"))
                                    	 {
                                    		 trangthai="Chưa chốt ";
                                    		 style= "";
                                    	 }
                                    	 else if(trangthai.equals("2"))
                                    	 {
                                    			 trangthai="Đã hủy";
                                    			 style= "";
                                    	 }
                                    	 else if(trangthai.equals("3"))
                                    	 {
                                			 trangthai="Đã chốt ";
                                			 style= "";
                                    	 }
                                    	
                                       	if (m % 2 != 0) {%>                     
                                        	<TR class= <%=lightrow%>  <%=style %> >
                                        <%} else {%>
                                           	<TR class= <%= darkrow%> <%=style %>  >
                                        	<%}%>
                                        	<TD align="center"><%= dhlist.getString("NgayNhap") %></TD>
                                        	<TD align="center"><%= dhlist.getString("nhaphangId") %></TD>
                                        	<TD align="center"><%= dhlist.getString("donhang_fk") %></TD>
                                        	<TD align="center"><%= dhlist.getString("khTen") %> - <%= dhlist.getString("dienthoai") %></TD>
                                        	<TD align="center"><%= dhlist.getString("nguoitao") %></TD>
                                        	<TD align="center"><%= dhlist.getString("ngaytao") %></TD>
                                        	<TD align="center"><%= dhlist.getString("nguoisua") %></TD>
                                        	<TD align="center"><%= dhlist.getString("ngaysua") %></TD>
                                        	<TD align="center"><%=trangthai%></TD>
                                                <TD align="center">
                            	                    <TABLE border = 0 cellpadding="0" cellspacing="0">
                                	                    <TR><TD>
                                	                    	<% if (dhlist.getString("trangthai").equals("1")) {%>
	                                    	                    
	                                    	                    <%if(quyen[2]!= 0){ %>
	                                    	                    <A href = "../../TraDonHangUpdateSvl?userId=<%=userId%>&update=<%=dhlist.getString("nhaphangId") %>"><img src="../images/Edit20.png" alt="Cập nhật" title="Cập nhật" width="20" height="20" longdesc="Cập nhật" border = 0 "></A>
	                                    	                    <%} %>
	                                    	                    
	                                    	                    <%if(quyen[4]!= 0){ %>
	                                    	                    <A href = "../../TraDonHangSvl?userId=<%=userId%>&chot=<%=dhlist.getString("nhaphangId") %>"><img  onclick="if(!confirm('Bạn muốn chốt đơn hàng trả về này?')) return false;"  src="../images/Chot.png" alt=C title="Chot" width="20" height="20" longdesc="Chot" border = 0 "></A>
    	                                	                   	<%} %>
    	                                	                   	
    	                                	                   	<%if(quyen[1]!= 0){ %>
    	                                	                   	<A href = "../../TraDonHangSvl?userId=<%=userId%>&delete=<%=dhlist.getString("nhaphangId") %>"><img  onclick="if(!confirm('Bạn muốn xóa đơn hàng trả về này?')) return false;"  src="../images/Delete20.png" alt="Xóa" title="Xóa" width="20" height="20" longdesc="Xóa" border = 0 "></A> 
																<%} %>
															
															<%}else { %>
																<%if(quyen[3]!= 0){ %>
                                	                    		<A href = "../../TraDonHangUpdateSvl?userId=<%=userId%>&display=<%=dhlist.getString("nhaphangId") %>"><img src="../images/Display20.png" alt="Hiển thị" width="20" height="20" longdesc="Hiển thị" title="Hiển thị" border = 0 "></A>
																<%} %>
																
															<%} %>                                    	                   
                                        	            </TD>
                                                   
                                            		 </TABLE>
                                                </TD>
                                            </TR>
                                        <% m++; 
                                        } 
                                        }catch(java.sql.SQLException e){e.printStackTrace();}
                               		}%>


 <tr class="tbfooter" > 
											 <TD align="center" valign="middle" colspan="13" class="tbfooter">
											 	<%if(obj.getNxtApprSplitting() >1) {%>
													<img alt="Trang Dau" src="../images/first.gif" style="cursor: pointer;" onclick="View('ddhForm', 1, 'view')"> &nbsp;
												<%}else {%>
													<img alt="Trang Dau" src="../images/first.gif" > &nbsp;
													<%} %>
												<% if(obj.getNxtApprSplitting() > 1){ %>
													<img alt="Trang Truoc" src="../images/prev.gif" style="cursor: pointer;" onclick="View('ddhForm', eval(ddhForm.nxtApprSplitting.value) -1, 'view')"> &nbsp;
												<%}else{ %>
													<img alt="Trang Truoc" src="../images/prev_d.gif" > &nbsp;
												<%} %>
												
												<%
													int[] listPage = obj.getNextSplittings();
													for(int i = 0; i < listPage.length; i++){
												%>
												
												<% 
												
												System.out.println("Current page:" + obj.getNxtApprSplitting());
												System.out.println("List page:" + listPage[i]);
												
												if(listPage[i] == obj.getNxtApprSplitting()){ %>
												
													<a  style="color:white;"><%= listPage[i] %>/ <%=obj.getTheLastSplitting() %></a>
												<%}else{ %>
													<a href="javascript:View('ddhForm', <%= listPage[i] %>, 'view')"><%= listPage[i] %></a>
												<%} %>
													<input type="hidden" name="list" value="<%= listPage[i] %>" />  &nbsp;
												<%} %>
												
												<% if(obj.getNxtApprSplitting() < obj.getTheLastSplitting()){ %>
													&nbsp; <img alt="Trang Tiep" src="../images/next.gif" style="cursor: pointer;" onclick="View('ddhForm', eval(ddhForm.nxtApprSplitting.value) +1, 'view')"> &nbsp;
												<%}else{ %>
													&nbsp; <img alt="Trang Tiep" src="../images/next_d.gif" > &nbsp;
												<%} %>
												<%if(obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {%>
											   		<img alt="Trang Cuoi" src="../images/last.gif" > &nbsp;
										   		<%}else{ %>
										   			<img alt="Trang Cuoi" src="../images/last.gif" style="cursor: pointer;" onclick="View('ddhForm', -1, 'view')"> &nbsp;
										   		<%} %>
											</TD>
										 </tr>  


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

<% if(!(dhlist == null)){ dhlist.close(); dhlist = null ;}%>
<%if(obj != null){
	obj.DBclose();
	obj = null;
} session.setAttribute("obj",""); %>
<%}%>
