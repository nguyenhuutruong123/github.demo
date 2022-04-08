<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.distributor.beans.dondathang.IDondathangList" %>
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

<% IDondathangList obj = (IDondathangList)session.getAttribute("obj"); %>
<% ResultSet dhlist = (ResultSet)obj.getDhList();%>
<% obj.setNextSplittings(); %>
 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Acecook - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">
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
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<script type="text/javascript" src="../scripts/phanTrang.js"></script>

<script type="text/javascript" src="../scripts/cool_DHTML_tootip.js"></script>
<script type="text/javascript" src="../scripts/jquery-1.4.2.min.js"></script>
<style type="text/css">
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
			font-size: 13;
		}	
		#dhtmlpointer
		{
			position:absolute;
			left: -300px;
			z-index: 101;
			visibility: hidden;
		}
		th {
		cursor: pointer;
		}	
  	</style>

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

</SCRIPT>

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

<form id="ddhForm" name="ddhForm" method="post" action="../../DondathangSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%=userId%>'>

<input type="hidden" name="action" value="1" >
<input type="hidden" name="crrApprSplitting" value="<%= obj.getCrrApprSplitting() %>" >
<input type="hidden" name="nxtApprSplitting" value="<%= obj.getNxtApprSplitting() %>" >


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
                                    	<TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý tồn kho &gt; Mua hàng từ nhà cung cấp &gt; Đặt hàng </TD> 
                                   
                                        <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn  <%= obj.getNppTen() %> &nbsp;</TD>
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
                                        	<input type="text" id="sku" name="sku" value="<%= obj.getSKU() %>" size=40/>
                                      		<!-- <img src="../images/Search20.png" width="20" height="20" > -->
                                      	</TD>
                                    </TR>
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
																<%if (obj.getTrangthai().equals("1")){ %>
																<option value=""></option>
																<option value="0" selected>Nhà PP Chưa Chốt</option>
																<option value="1" >Nhà PP đã xác nhận/Chờ duyệt</option>
																<option value="2">Trung tâm đã duyệt</option>
																<option value="3">Đã xuất HDTC</option>
																<option value="4">Đang giao hàng</option>
																<option value="5">Hoàn tất</option>
																<option value="6">Đã hủy</option>
																<%}else 							
								  						if (obj.getTrangthai().equals("1")){ %>
								  								<option value=""></option>
																<option value="0" >Nhà PP Chưa Chốt</option>
																<option value="1" selected >Nhà PP đã xác nhận/Chờ duyệt</option>
																<option value="2">Trung tâm đã duyệt</option>
																<option value="3">Đã xuất HDTC</option>
																<option value="4">Đang giao hàng</option>
																<option value="5">Hoàn tất</option>
																<option value="6">Đã hủy</option>
																<%}else
														if (obj.getTrangthai().equals("2")){ %>
																<option value=""></option>
																<option value="0" >Nhà PP Chưa Chốt</option>
																<option value="1"  >Nhà PP đã xác nhận/Chờ duyệt</option>
																<option value="2" selected>Trung tâm đã duyệt</option>
																<option value="3">Đã xuất HDTC</option>
																<option value="4">Đang giao hàng</option>
																<option value="5">Hoàn tất</option>
																<option value="6">Đã hủy</option>
													<%} else											   	 
														if (obj.getTrangthai().equals("3")){ %>
																<option value=""></option>
																<option value="0" >Nhà PP Chưa Chốt</option>
																<option value="1"  >Nhà PP đã xác nhận/Chờ duyệt</option>
																<option value="2" >Trung tâm đã duyệt</option>
																<option value="3" selected>Đã xuất HDTC</option>
																<option value="4">Đang giao hàng</option>
																<option value="5">Hoàn tất</option>
																<option value="6">Đã hủy</option>

																<%	} else  if(obj.getTrangthai().equals("4")) { %>
																	<option value=""></option>
																<option value="0" >Nhà PP Chưa Chốt</option>
																<option value="1"  >Nhà PP đã xác nhận/Chờ duyệt</option>
																<option value="2" >Trung tâm đã duyệt</option>
																<option value="3" >Đã xuất HDTC</option>
																<option value="4" selected>Đang giao hàng</option>
																<option value="5">Hoàn tất</option>
																<option value="6">Đã hủy</option>
																	
																 <%} else if(obj.getTrangthai().equals("5")){ %> 
																 	<option value=""></option>
																<option value="0" >Nhà PP Chưa Chốt</option>
																<option value="1"  >Nhà PP đã xác nhận/Chờ duyệt</option>
																<option value="2" >Trung tâm đã duyệt</option>
																<option value="3" >Đã xuất HDTC</option>
																<option value="4" >Đang giao hàng</option>
																<option value="5" selected>Hoàn tất</option>
																<option value="6">Đã hủy</option>
																 <%}	else if(obj.getTrangthai().equals("6")){ %> 
																 	<option value=""></option>
																<option value="0" >Nhà PP Chưa Chốt</option>
																<option value="1"  >Nhà PP đã xác nhận/Chờ duyệt</option>
																<option value="2" >Trung tâm đã duyệt</option>
																<option value="3" >Đã xuất HDTC</option>
																<option value="4" >Đang giao hàng</option>
																<option value="5">Hoàn tất</option>
																<option value="6" selected>Đã hủy</option>
																  <%} else { %>
																		<option value="" selected></option>
																<option value="0" >Nhà PP Chưa Chốt</option>
																<option value="1"  >Nhà PP đã xác nhận/Chờ duyệt</option>
																<option value="2" >Trung tâm đã duyệt</option>
																<option value="3" >Đã xuất HDTC</option>
																<option value="4" >Đang giao hàng</option>
																<option value="5">Hoàn tất</option>
																<option value="6" >Đã hủy</option>
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
                                <LEGEND class="legendtitle">&nbsp;Đặt hàng&nbsp;&nbsp;
                                	<a class="button3"  onclick="newform()">
    								<img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>                            
					
									<!--<INPUT name="action" type="submit" value="Tao moi"> -->	
                                
                                </LEGEND> 
                                
    
                      	        <TABLE width="100%">
                                <TR>
                                    <TD>
                                        <TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
                                        <TR class="tbheader">
                                            <TH >Ngày đặt hàng </TH>
                                            <TH >Số chứng từ </TH>
                                            <TH >Đơn vị kinh doanh </TH>
                              
											<TH >Trạng thái </TH>
                                            <TH >Người tạo </TH>
                                            <TH >Người sửa </TH>
                                           <!--  <TH >Số lượng </TH>
                                            <TH >Số lượng duyệt </TH> -->
                                            <TH align="center">&nbsp;Tác vụ </TH>
                                        </TR>
         
                               <% 
                            NumberFormat formatter = new DecimalFormat("#,###,###");
                            int m = 0;
                            String lightrow = "tblightrow";
                            String darkrow = "tbdarkrow";
							if(dhlist != null){%>
								
								<% try{								
                                    while (dhlist.next()){
                                        	
                                       	if (m % 2 != 0) {%>                     
                                        	<TR class= <%=lightrow%> <%= dhlist.getString("dondathang_from_fk") != null ? " onMouseover=\"ddrivetip('" + dhlist.getString("ghichu") + "', 300)\"; onMouseout=\"hideddrivetip()\" " : ""  %>  >
                                        <%} else {%>
                                           	<TR class= <%= darkrow%> <%= dhlist.getString("dondathang_from_fk") != null ? " onMouseover=\"ddrivetip('" + dhlist.getString("ghichu") + "', 300)\"; onMouseout=\"hideddrivetip()\" " : ""  %>  >
                                        	<%}%>
                                        	<% 
                                        	String sl = dhlist.getString("soluong");
                                        	String thd = dhlist.getString("soluongduyet");
                                        	//formatter.format(Integer.parseInt(thd));
                                        	System.out.println("DDH_FROM_FK : "+dhlist.getString("dondathang_from_fk")+"SL :"+formatter.format(Double.parseDouble(sl))+" & SLD :"+formatter.format(Double.parseDouble(thd)));
                                        	if( !(formatter.format(Double.parseDouble(sl)).equals(formatter.format(Double.parseDouble(thd)))) ) {%>
                                                <TD align="left"><div align="left" style="color:#F00"><%= dhlist.getString("ngaydat") %></div></TD>                                   
                                                <TD><div align="center" style="color:#F00"><%= dhlist.getString("chungtu") %></div></TD>
                                                <TD><div align="center" style="color:#F00"><%= dhlist.getString("donvikinhdoanh") %></div></TD>
                                               
                                                <% if (dhlist.getString("trangthai").equals("0")){ %>
                                                   		<TD><div align="center" style="color:#F00">Còn chỉnh sửa</div></TD>
                                                <%}else 
                                                   if (dhlist.getString("trangthai").equals("1")){ %>
                                                   		<TD><div align="center" style="color:#F00">Chờ xử lý</div></TD>
                                                <%}else  
                                                   if (dhlist.getString("trangthai").equals("2")) {%>
                                                   		<TD><div align="center" style="color:#F00">Đã xác nhận</div></TD>
                                                  <%}else if (dhlist.getString("trangthai").equals("3")) {%>
                                                   		<TD><div align="center" style="color:#F00">Đã xuất HDTC</div></TD>
                                                   <%}else if (dhlist.getString("trangthai").equals("4")) {%>
                                                   		<TD><div align="center" style="color:#F00">Đã giao hàng</div></TD>
                                             		  <%}else if (dhlist.getString("trangthai").equals("5")) {%>
                                             		<TD><div align="center" style="color:#F00">Hòan tất</div></TD>
                                                  <%}else { %>
                                                   		<TD><div align="center" style="color:#F00">Đã hủy</div></TD>
                                                  <%}%>
                                                <TD align="center" style="color:#F00"><%= dhlist.getString("nguoitao") %></TD>
                                                <TD align="center" style="color:#F00"><%= dhlist.getString("nguoisua")%></TD>
                                                <%-- <TD align="center" style="color:#F00"><%= dhlist.getString("soluong") %></TD>
                                                <TD align="center" style="color:#F00"><%= formatter.format(Double.parseDouble(thd)) %></TD> --%>
                                                <%}else{ %>
                                                <TD align="left"><div align="left"><%= dhlist.getString("ngaydat") %></div></TD>                                   
                                                <TD><div align="center"><%= dhlist.getString("chungtu") %></div></TD>
                                                <TD><div align="center"><%= dhlist.getString("donvikinhdoanh") %></div></TD>
                                               
                                             <% if (dhlist.getString("trangthai").equals("0")){ %>
                                                   		<TD><div align="center" style="color:#F00">Còn chỉnh sửa</div></TD>
                                                <%}else 
                                                   if (dhlist.getString("trangthai").equals("1")){ %>
                                                   		<TD><div align="center" style="color:#F00">Chờ xử lý</div></TD>
                                                <%}else  
                                                   if (dhlist.getString("trangthai").equals("2")) {%>
                                                   		<TD><div align="center" style="color:#F00">Đã xác nhận</div></TD>
                                                  <%}else if (dhlist.getString("trangthai").equals("3")) {%>
                                                   		<TD><div align="center" style="color:#F00">Đã xuất HDTC</div></TD>
                                                   <%}else if (dhlist.getString("trangthai").equals("4")) {%>
                                                   		<TD><div align="center" style="color:#F00">Đã giao hàng</div></TD>
                                             		  <%}else if (dhlist.getString("trangthai").equals("5")) {%>
                                             		<TD><div align="center" style="color:#F00">Hòan tất</div></TD>
                                                  <%}else { %>
                                                   		<TD><div align="center" style="color:#F00">Đã hủy</div></TD>
                                                  <%}%>
                                                <TD align="center"><%= dhlist.getString("nguoitao") %></TD>
                                                <TD align="center"><%= dhlist.getString("nguoisua")%></TD>
                                                <%-- <TD align="center"><%= dhlist.getString("soluong") %></TD>
                                                <TD align="center"><%= formatter.format(Double.parseDouble(thd))%></TD> --%>
                                                <%} %>
                                                <TD align="center">
                            	                    <TABLE border = 0 cellpadding="0" cellspacing="0">
                                	                    <TR><TD>
                                	                    	<% if (dhlist.getString("trangthai").equals("0")) {%>
	                                    	                    <A href = "../../DondathangUpdateSvl?userId=<%=userId%>&update=<%=dhlist.getString("chungtu") %>"><img src="../images/Edit20.png" alt="Cập nhật" title="Cập nhật" width="20" height="20" longdesc="Cập nhật" border = 0 "></A>
    	                                	                   	<A href = "../../DondathangSvl?userId=<%=userId%>&delete=<%=dhlist.getString("chungtu") %>"><img  onclick="if(!confirm('Bạn muốn xóa đặt hàng này?')) return false;"  src="../images/Delete20.png" alt="Xóa" title="Xóa" width="20" height="20" longdesc="Xóa" border = 0 "></A> 

															<%}else { %>
                                	                    		<A href = "../../DondathangDisplaySvl?userId=<%=userId%>&display=<%=dhlist.getString("chungtu") %>"><img src="../images/Display20.png" alt="Hiển thị" width="20" height="20" longdesc="Hiển thị" title="Hiển thị" border = 0 "></A>
															<%} %>                                    	                   
                                        	            </TD>
                                                   
                                            		 </TABLE>
                                                </TD>
                                            </TR>
                                        <% m++; 
                                        } 
                                        }catch(java.sql.SQLException e){}
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


</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</HTML>

<% if(!(dhlist == null)) dhlist.close(); %>
<%if(obj != null){
	obj.DBclose();
	obj = null;
}%>
<%}%>