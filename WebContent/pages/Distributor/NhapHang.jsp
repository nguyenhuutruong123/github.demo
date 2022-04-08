<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.distributor.beans.nhaphang.INhaphangList" %>
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
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>

<% INhaphangList obj = (INhaphangList)session.getAttribute("obj"); %>
<% ResultSet nhaphanglist = (ResultSet)obj.getNhaphangList(); %>
<% obj.setNextSplittings(); %>
<% String nnId = (String)session.getAttribute("nnId"); %> 
<% if(nnId == null) {
	nnId = "vi"; 
	}	
String url = util.getChuyenNguUrl("NhaphangSvl","",nnId);	
int[] quyen = new  int[6];
quyen = util.Getquyen("NhaphangSvl","",userId);
	%>

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

function thongbao()
{
	if(document.getElementById("msg").value != '')
		alert(document.getElementById("msg").value);
}

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
   document.forms["nhForm"].submit();
}

function newform()
{
	document.forms['nhForm'].action.value= 'new';
	document.forms['nhForm'].submit();
}

</SCRIPT>

<!-- BEGIN RENDER AUTO -->

<% if (!geso.dms.center.util.GlobalValue.dev_mode) { %>
	<script type="text/javascript" src="../scripts/disableF12.js"></script>
<% } %>

<!-- END RENDER AUTO -->

<style>
	
	.td-red
	{
		color: #DC143C; 
		font-weight: bold;
	}
	
	.danger
	{
		background-color: #f5c6cb;
		border-color: #ed969e;
	}

</style>

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

<form name="nhForm" method="post" action="../../NhaphangSvl"> 
 <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%=userId%>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="msg" id="msg" value='<%=obj.getMsg()%>'>


<script type="text/javascript">
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
                                    	<TD align="left" colspan="2" class="tbnavigation">&nbsp;<%= " " + url %></TD>
                                   
                                        <TD colspan="2" align="right" class="tbnavigation"><%=ChuyenNgu.get("Chào mừng bạn",nnId) %>  <%= obj.getNppTen() %>  &nbsp;</TD>
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
                                        <TD width="19%" class="plainlabel"><%=ChuyenNgu.get("Chứa SKU",nnId) %> </TD>
                                      	<TD width="81%" class="plainlabel">
                                        	<input type="text" name="sku" value="<%= obj.getSKU() %>" size="40" onChange="submitform();">
                                      		
                                      	</TD>
                                    </TR>
								<TR>
											<TD class="plainlabel" ><%=ChuyenNgu.get("Từ ngày",nnId) %> </TD>
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
                                    <TD class="plainlabel" ><%=ChuyenNgu.get("Đến ngày",nnId) %> </TD>
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
									<TD class="plainlabel"><%=ChuyenNgu.get("Trạng thái",nnId) %></TD>
									<TD class="plainlabel"><select name="trangthai" onChange = "submitform();">
								  		<option value=""></option>
									<%if (obj.getTrangthai().equals("0")){ %>								  		
								    	<option value="0" selected><%=ChuyenNgu.get("Chưa nhận hàng",nnId) %> </option>
								    	<option value="1"><%=ChuyenNgu.get("Đã nhận hàng",nnId) %></option>
								    	<option value="2" ><%=ChuyenNgu.get("Đã hủy",nnId) %></option>
									<%}else{ 							
								  		if (obj.getTrangthai().equals("1")){ %>								  		
								    	<option value="0" ><%=ChuyenNgu.get("Chưa nhận hàng",nnId) %> </option>
								    	<option value="1" selected><%=ChuyenNgu.get("Đã nhận hàng",nnId) %></option>
								    	<option value="2" ><%=ChuyenNgu.get("Đã hủy",nnId) %></option>
									<%  }else if (obj.getTrangthai().equals("2")){ %>								  		
							    	<option value="0" ><%=ChuyenNgu.get("Chưa nhận hàng",nnId) %> </option>
							    	<option value="1" ><%=ChuyenNgu.get("Đã nhận hàng",nnId) %></option>
							    	<option value="2" selected><%=ChuyenNgu.get("Đã hủy",nnId) %></option>
								<%  }
								  		else{%>								  		
								    	<option value="0" ><%=ChuyenNgu.get("Chưa nhận hàng",nnId) %> </option>
								    	<option value="1" ><%=ChuyenNgu.get("Đã nhận hàng",nnId) %></option>
								    	<option value="2" ><%=ChuyenNgu.get("Đã hủy",nnId) %></option>
									
									<%}}%>
								    	</select></TD>
								</TR>

                                <TR>
                                	<TD class="plainlabel" align="left">
                                		 
                                		 <a class="button2" href="javascript:clearform()">
	<img style="top: -4px;" src="../images/button.png" alt=""><%=ChuyenNgu.get("Nhập lại",nnId) %></a>&nbsp;&nbsp;&nbsp;&nbsp;	 
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
                                <LEGEND class="legendtitle">&nbsp;<%=ChuyenNgu.get("Nhận hàng",nnId) %>
                            <%  if(quyen[Utility.THEM]!=0) {%>     
                                	<a class="button3"  onclick="newform()">
	    							<img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a> 
	    							    <%}%>    
	    							
                                </LEGEND> 
                                
    
                      	        <TABLE width="100%">
                                <TR>
                                    <TD>
                                        <TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
                                        <TR class="tbheader">
                                        <th width="6%" align="center"><%=ChuyenNgu.get("STT",nnId) %></th>
                                            <TH ><%=ChuyenNgu.get("Ngày chứng từ",nnId) %> </TH>
                                            <TH ><%=ChuyenNgu.get("Ngày nhận",nnId) %></TH>
                                            <TH ><%=ChuyenNgu.get("Số HD",nnId) %> </TH>
                                            <TH ><%=ChuyenNgu.get("Số SO",nnId) %> </TH>
                                            <TH ><%=ChuyenNgu.get("Số chứng từ",nnId) %> </TH>
                                            <TH> <%=ChuyenNgu.get("Số đơn hàng",nnId) %></TH>
                                            <TH ><%=ChuyenNgu.get("Đơn vị kinh doanh",nnId) %> </TH>
                                            <TH ><%=ChuyenNgu.get("Kênh bán hàng",nnId) %> </TH>
                                            <!-- <TH >Tổng số tiền có VAT (VND) </TH> -->
											<TH ><%=ChuyenNgu.get("Trạng thái",nnId) %> </TH>
                                            <TH ><%=ChuyenNgu.get("Người tạo",nnId) %> </TH>
                                            <TH ><%=ChuyenNgu.get("Người sửa",nnId) %> </TH>
                                            <TH align="center">&nbsp;<%=ChuyenNgu.get("Tác vụ",nnId) %> </TH>
                                        </TR>
         
                               <% 
                            NumberFormat formatter = new DecimalFormat("#,###,###");
                            int m = 0;
                            String lightrow = "tblightrow";
                            String darkrow = "tbdarkrow";
                            if(nhaphanglist != null){
								try{								
                                    while (nhaphanglist.next())
                                    {
                                       	if (m % 2 != 0) {%>                     
                                        	<TR class= "<%= lightrow %>">
                                        <%} else {%>
                                           	<TR class= "<%= darkrow %>">
                                        	<%}%>
                                        		<TD align="center"><%= nhaphanglist.getString("_no") %></TD>
                                                <TD align="left"><div align="left"><%= nhaphanglist.getString("ngaychungtu") %></div></TD>
                                                 <TD><div align="center"><%= nhaphanglist.getString("ngaynhan") %></div></TD>
                                                 <TD><div align="center"><%=nhaphanglist.getString("trangthai").equals("1") ?  nhaphanglist.getString("HDTaiChinh"):"0" %></div></TD>
                                                  <TD><div align="center"><%= nhaphanglist.getString("SO_number") %></div></TD>  
                                               
                                               <% if (nhaphanglist.getString("trangthai").equals("0")){ %>                                 
                                               	<TD class="td-red"><div align="center"><%= nhaphanglist.getString("chungtu") %></div></TD>
                                               <%} else { %>
                                               	<TD><div align="center"><%= nhaphanglist.getString("chungtu") %></div></TD>
                                               <%} %>
                                               
                                                <TD><div align="center"><%= nhaphanglist.getString("dathang_fk")==null?"":nhaphanglist.getString("dathang_fk") %></div></TD>
                                                <TD><div align="center"><%= nhaphanglist.getString("donvikinhdoanh") %></div></TD>
                                                <TD><div align="center"><%= nhaphanglist.getString("kbh") %></div></TD>
                                                <%-- <TD><div align="center"><%= formatter.format(Float.parseFloat(nhaphanglist.getString("sotienavat")==null?"0":nhaphanglist.getString("sotienavat"))) %></div></TD> --%>
                                                <% if (nhaphanglist.getString("trangthai").equals("0")){ %>
                                                   		<TD><div align="center"><%=ChuyenNgu.get("Chưa nhận hàng",nnId) %></div></TD>
                                                <%}else if(nhaphanglist.getString("trangthai").equals("2")) { %>
                                                   		<TD><div align="center"><%=ChuyenNgu.get("Đã hủy",nnId) %></div></TD>
                                                 <% }
                                                 else  if (nhaphanglist.getString("trangthai").equals("1")) {%>
                                                   		<TD><div align="center"><%=ChuyenNgu.get("Đã nhận hàng",nnId) %></div></TD>
                                                  <% }  %>
                                                 
                                                <TD align="center"><%= nhaphanglist.getString("nguoitao") %></TD>
                                                <TD align="center"><%= nhaphanglist.getString("nguoisua")%></TD>
                                                <TD align="center">
                            	                    <TABLE border = 0 cellpadding="0" cellspacing="0">
                                	                    <TR><TD>
                                	                    	<% if (nhaphanglist.getString("trangthai").equals("0")) {%>
	                                    	                    <%-- <A href = "../../NhaphangUpdateSvl?userId=<%=userId%>&update=<%=nhaphanglist.getString("chungtu") %>"><img src="../images/Chot.png" alt="Nhận hàng" title="Nhận hàng" width="20" height="20" longdesc="Nhận hàng" border = 0 "></A> --%>
	                                    	                    <A href = "../../RouterSvl?task=<%= Utility.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "NhaphangUpdateSvl?userId=" + userId+ "&update="+ nhaphanglist.getString("chungtu")) %>"><img src="../images/Chot.png" alt="Nhận hàng" title="Nhận hàng" width="20" height="20" longdesc="Nhận hàng" style="border = 0;"></A>
	                                    	                    
	                                    	                    <%if(nhaphanglist.getString("loai").equals("1")) { %>
	                                    	                    	<A href = "../../RouterSvl?task=<%= Utility.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "NhaphangSvl?userId=" + userId+ "&delete="+ nhaphanglist.getString("chungtu")) %>"><img src="../images/Delete30.png" alt="XoaNhanHang" title="Xóa Nhận Hàng" width="20" height="20" longdesc="Xóa Nhận Hàng" style="border = 0;"></A>
	                                    	                    <%} %>
															<%}else{ %>
                                	                    		<%-- <A href = "../../NhaphangDisplaySvl?userId=<%=userId%>&display=<%=nhaphanglist.getString("chungtu") %>"><img src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" width="20" height="20" longdesc="Hiển thị" border = 0 "></A> --%>
                                	                    		<A href = "../../RouterSvl?task=<%= Utility.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "NhaphangDisplaySvl?userId=" + userId+ "&display="+ nhaphanglist.getString("chungtu")) %>"><img src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" width="20" height="20" longdesc="Hiển thị" border = 0 "></A>
															   <A href = "../../RouterSvl?task=<%= Utility.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "NhapHangPdf?userId=" + userId+ "&pdf="+ nhaphanglist.getString("chungtu")) %>"><img src="../images/Printer30.png" alt="IN" title="IN" width="20" height="20" longdesc="Hiển thị" border = 0 "></A>
															
															<%}
															%>                                    	                   
                                        	            </TD>
                                            		 </TABLE>
                                                </TD>
                                            </TR>
                                        <% m++; } 
                                        }catch(java.sql.SQLException e){}
                               		}%>
                               			<tr class="tbfooter" > 
						 <TD align="center" valign="middle" colspan="13" class="tbfooter">
						 	<% obj.setNextSplittings(); %>
						 <script type="text/javascript" src="../scripts/phanTrang.js"></script>
						<input type="hidden" name="crrApprSplitting" value="<%= obj.getCrrApprSplitting() %>" >
						<input type="hidden" name="nxtApprSplitting" value="<%= obj.getNxtApprSplitting() %>" >

					 	<%if(obj.getNxtApprSplitting() >1) {%>
							<img alt="Trang Dau" src="../images/first.gif" style="cursor: pointer;" onclick="View(document.forms[0].name, 1, 'view')"> &nbsp;
						<%}else {%>
							<img alt="Trang Dau" src="../images/first.gif" > &nbsp;
							<%} %>
						<% if(obj.getNxtApprSplitting() > 1){ %>
							<img alt="Trang Truoc" src="../images/prev.gif" style="cursor: pointer;" onclick="View(document.forms[0].name, eval(document.forms[0].nxtApprSplitting.value) -1, 'view')"> &nbsp;
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
							<a href="javascript:View(document.forms[0].name, <%= listPage[i] %>, 'view')"><%= listPage[i] %></a>
						<%} %>
							<input type="hidden" name="list" value="<%= listPage[i] %>" />  &nbsp;
						<%} %>
						
						<% if(obj.getNxtApprSplitting() < obj.getTheLastSplitting()){ %>
							&nbsp; <img alt="Trang Tiep" src="../images/next.gif" style="cursor: pointer;" onclick="View(document.forms[0].name, eval(document.forms[0].nxtApprSplitting.value) +1, 'view')"> &nbsp;
						<%}else{ %>
							&nbsp; <img alt="Trang Tiep" src="../images/next_d.gif" > &nbsp;
						<%} %>
						<%if(obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {%>
					   		<img alt="Trang Cuoi" src="../images/last.gif" > &nbsp;
				   		<%}else{ %>
				   			<img alt="Trang Cuoi" src="../images/last.gif" style="cursor: pointer;" onclick="View(document.forms[0].name, -1, 'view')"> &nbsp;
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
<% if(!(nhaphanglist == null)) nhaphanglist.close(); %>
<% if(obj != null){
	obj.DBclose();
	obj = null;
	session.setAttribute("obj",null);
} %>
<% }%>