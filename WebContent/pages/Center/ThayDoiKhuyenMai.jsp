<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.beans.thaydoikhuyenmai.IThayDoiKhuyenMaiList" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%@ page  import = "geso.dms.center.util.ChuyenNgu" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(util==null||!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>
<% IThayDoiKhuyenMaiList obj = (IThayDoiKhuyenMaiList)session.getAttribute("obj"); %>
<% ResultSet nhaphanglist = (ResultSet)obj.getNhaphangList(); 
obj.setNextSplittings();

int[] quyen = new  int[6];
quyen = util.Getquyen("ThayDoiKhuyenMaiSvl","",userId);

%>
<% String nnId = (String)session.getAttribute("nnId"); %>
<% if(nnId == null) {
 nnId = "vi";  
 }
 String url = util.getChuyenNguUrl("ThayDoiKhuyenMaiSvl","",nnId);
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
<link rel="stylesheet" href="../css/jqueryautocomplete.css" type="text/css" />
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<script type="text/javascript" src="../scripts/phanTrang.js"></script>
<script type="text/javascript" src="../scripts/jqueryautocomplete.js"></script>
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
$(document).ready(function()
{
	$("#nppId").select2();
});
</script>

<SCRIPT language="javascript" type="text/javascript">

function clearform()
{
	document.forms["nhForm"].tungay.value = "";
	document.forms["nhForm"].denngay.value = "";
    document.forms["nhForm"].trangthai.selectedIndex = 0;
    document.forms["nhForm"].submit();
    
}

function submitform()
{   
   document.forms["nhForm"].submit();
}
function seach()
{   
   document.forms["nhForm"].submit();
}
function xuatExcel()
{
	document.forms['nhForm'].action.value= 'toExcel';
	document.forms['nhForm'].submit();
	document.forms['nhForm'].action.value= '';
}

function  newform()
{
	document.forms["nhForm"].action.value ="new";
	document.forms["nhForm"].submit();
}
function processing(id,chuoi)
{
	 document.getElementById(id).innerHTML = "<a href='#'><img src='../images/waiting.gif' width = '20' height = '20' title='cho thuc hien..' border='0' longdesc='cho thuc hien..' style='border-style:outset'> Proc...</a>"; 		  
	 document.getElementById(id).href=chuoi;
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

<form name="nhForm" method="post" action="../../ThayDoiKhuyenMaiSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%=userId%>'> 
<input type="hidden" name="userTen" value='<%=userTen%>'> 
<input type="hidden" name="action" value="1"> 
<input type="hidden" name="crrApprSplitting" value="<%= obj.getCrrApprSplitting() %>">
<input type="hidden" name="nxtApprSplitting"value="<%= obj.getNxtApprSplitting() %>">


<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%"  bgcolor="#FFFFFF">
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
                                        <TD colspan="2" align="right" class="tbnavigation"><%=ChuyenNgu.get("Chào mừng",nnId) %> <%=userTen %>  &nbsp;</TD>
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
									<TD class="plainlabel" ><%=ChuyenNgu.get("Từ ngày",nnId) %> </TD>
						  			<TD class="plainlabel" >
										<TABLE cellpadding="0" cellspacing="0">
										<TR>
											<TD>
												<input class="days" type="text" name="tungay" value="<%=obj.getTungay() %>" size="20">
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
													<input class="days" type="text" name="denngay" value="<%=obj.getDenngay() %>" size="20">
												</TD>

                	                     	</TR>
										</TABLE>
									</TD>
								</TR>
								
								<TR>
									<TD class="plainlabel"><%=ChuyenNgu.get("Trạng thái",nnId) %></TD>
									<TD class="plainlabel"><select name="trangthai">
								  		<option value=""></option>
									<%if (obj.getTrangthai().equals("0")){ %>								  		
								    	<option value="0" selected><%=ChuyenNgu.get("Chưa duyệt",nnId) %> </option>
								    	<option value="1"><%=ChuyenNgu.get("Đã duyệt",nnId) %></option>
								    	<option value="2" ><%=ChuyenNgu.get("Đã hủy",nnId) %></option>
									<%}else{ 							
								  		if (obj.getTrangthai().equals("1")){ %>								  		
								    	<option value="0" selected><%=ChuyenNgu.get("Chưa duyệt",nnId) %> </option>
								    	<option value="1" selected><%=ChuyenNgu.get("Đã duyệt",nnId) %></option>
								    	<option value="2" >Đã hủy</option>
									<%  }else if (obj.getTrangthai().equals("2")){ %>								  		
								    	<option value="0" selected><%=ChuyenNgu.get("Chưa duyệt",nnId) %> </option>
								    	<option value="1"><%=ChuyenNgu.get("Đã duyệt",nnId) %></option>
								    	<option value="2" selected ><%=ChuyenNgu.get("Đã hủy",nnId) %></option>
									<% }else{  %>
										<option value="0" ><%=ChuyenNgu.get("Chưa duyệt",nnId) %> </option>
								    	<option value="1"><%=ChuyenNgu.get("Đã duyệt",nnId) %></option>
								    	<option value="2" ><%=ChuyenNgu.get("Đã hủy",nnId) %></option>
									<% }}%>
								    	</select></TD>
								</TR>

                                <TR>
                                	<TD align="left" colspan="4">
                                	
                                	 
									
                                		 <a class="button2" href="javascript:clearform()">
										<img style="top: -4px;" src="../images/button.png" alt=""><%=ChuyenNgu.get("Nhập lại",nnId) %></a>
										
										<a class="button2" href="javascript:submitform()">
										<img style="top: -4px;" src="../images/button.png" alt="">Tìm kiếm</a>
		<a class="button2" href="javascript:xuatExcel()"> <img style="top: -4px;" src="../images/button.png" alt=""><%=ChuyenNgu.get("Xuất Excel",nnId) %> </a>&nbsp;&nbsp;&nbsp;&nbsp;											
											
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
                              
                      <TABLE width="100%" cellspacing = 0 cellpadding=0>
                        <TR>
                            <TD >
                                <FIELDSET>
                                <LEGEND class="legendtitle">&nbsp;<%=ChuyenNgu.get("Thay đổi chương trình khuyến mãi",nnId) %></LEGEND> 
                                	<%if(quyen[0]!=0){ %>
                                	<a class="button3" href="javascript:newform()"> 
                                	<img style="top: -4px;" src="../images/New.png" alt=""><%=ChuyenNgu.get("Tạo mới",nnId) %> 
                                	</a>
                                	<%} %>
    
                      	        <TABLE width="100%">
                                <TR>
                                    <TD>
                                        <TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
                                        <TR class="tbheader">
                                            <TH ><%=ChuyenNgu.get("ID",nnId) %> </TH>                                            
                                            <TH ><%=ChuyenNgu.get("Mã trả khuyến mãi",nnId) %> </TH>
                                            <TH><%=ChuyenNgu.get("Mã điều kiện khuyến mãi",nnId) %> </TH>
                                            <TH ><%=ChuyenNgu.get("Người tạo",nnId) %> </TH>
                                            <TH ><%=ChuyenNgu.get("Ngày tạo",nnId) %> </TH>
                                            <TH ><%=ChuyenNgu.get("Người sửa",nnId) %> </TH>
                                            <TH ><%=ChuyenNgu.get("Ngày sửa",nnId) %> </TH>
                                            <TH ><%=ChuyenNgu.get("Người duyệt",nnId) %> </TH>
                                            <TH ><%=ChuyenNgu.get("Ngày duyệt",nnId) %>Ngày duyệt </TH>
											<TH ><%=ChuyenNgu.get("Trạng thái",nnId) %> </TH>
                                            <TH align="center">&nbsp;T<%=ChuyenNgu.get("Tác vụ",nnId) %> </TH>
                                        </TR>
         
                               <% 
                            int m = 0;
                            String lightrow = "tblightrow";
                            String darkrow = "tbdarkrow";
							if(nhaphanglist != null){
								try{								
                                    while (nhaphanglist.next())
                                    {
                                    	String trangthai="";
                	                    if(nhaphanglist.getString("trangthai").equals("0"))
                	                    		trangthai="Chưa duyệt";
                	                    else if(nhaphanglist.getString("trangthai").equals("1"))
                	                    		{
                	                    			trangthai="Đã duyệt";
                	                    		}	
                                    	
                                       	if (m % 2 != 0) {%>                     
                                        	<TR class= <%=lightrow%> >
                                        <%} else {%>
                                           	<TR class= <%= darkrow%> >
                                        	<%}%>
                                                <TD align="left"><div align="left"><%= nhaphanglist.getString("thaydoiId") %></div></TD>
                                                <TD align="center"><%= nhaphanglist.getString("TraKm") %></TD>
                                                <TD align="center"><%= nhaphanglist.getString("dkKm") %></TD>
                                                <TD><div align="center"><%= nhaphanglist.getString("NguoiTao") %></div></TD>
                                                <TD><div align="center"><%= nhaphanglist.getString("NgayTao") %></div></TD>
                                                <TD><div align="center"><%= nhaphanglist.getString("NguoiSua") %></div></TD>
                                                <TD><div align="center"><%= nhaphanglist.getString("NgaySua") %></div></TD>
                                                <TD align="center"><%= nhaphanglist.getString("NguoiDuyet") %></TD>
                                                <TD align="center"><%= nhaphanglist.getString("NgayDuyet") %></TD>
                                                 <TD align="center"> <%= trangthai %></TD>
                                                <TD align="center">
                            	                    <TABLE border = 0 cellpadding="0" cellspacing="0">
                                	                    <TR>
                                	                    <%
                                	                    
                                	                    
                                	                    if(nhaphanglist.getString("trangthai").equals("0")){ %>
                                	                    
                                	                     <%if(quyen[3]!=0){ %>
	                                	                    <TD>	                                    	                    
	                                	                    	<A href = "../../RouterSvl?task=<%= util.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "ThayDoiKhuyenMaiUpdateSvl?userId="+userId+"&display="+nhaphanglist.getString("thaydoiId") +"")%>"><img src="../images/Display20.png" alt="Hiển thị" width="20" height="20" longdesc="Hiển thị" border = 0 "></A>															                                    	                   
	                                        	            </TD>
	                                        	            <%} %>
	                                        	            
                                	                    <%if(quyen[2]!=0){ %>
                                	                    	<TD>	                                    	                    
	                                	                    	<A href = "../../RouterSvl?task=<%= util.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "ThayDoiKhuyenMaiUpdateSvl?userId="+userId+"&edit="+nhaphanglist.getString("thaydoiId") +"")%>"><img src="../images/Edit.png" alt="Cập nhật" width="20" height="20" longdesc="Cập nhật" border = 0 "></A>															                                    	                   
	                                        	            </TD>
	                                        	          <%} %>
	                                        	          
	                                        	          <%if(quyen[4]!=0){ %>
                                	                    	<TD>	                                    	                    
																	<A id='chotphieu<%=nhaphanglist.getString("thaydoiId")%>' href=""><img src="../images/Chot.png" alt="Chốt" width="20" height="20" title="Chốt" border="0" onclick="if(!confirm('Bạn có muốn thêm sp vào ctkm này hay không?')) {return false ;}else{ processing('<%="chotphieu"+nhaphanglist.getString("thaydoiId")%>' , '../../RouterSvl?task=<%= util.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "ThayDoiKhuyenMaiSvl?userId="+userId+"&chot="+nhaphanglist.getString("thaydoiId") +"")%>');}"  ></A>															                                    	                   
	                                        	            </TD>
																  <%} %>			
	                                        	          
	                                        	          <%if(quyen[1]!=0){ %>
                                	                    	<TD>	                                    	                    
																	<A id='deletephieu<%=nhaphanglist.getString("thaydoiId")%>' href=""><img src="../images/Delete.png" alt="Xóa" width="20" height="20" title="Xóa" border="0" onclick="if(!confirm('Bạn có muốn xóa thêm sp vào ctkm này hay không?')) {return false ;}else{ processing('<%="deletephieu"+nhaphanglist.getString("thaydoiId")%>' , '../../RouterSvl?task=<%= util.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "ThayDoiKhuyenMaiSvl?userId="+userId+"&delete="+nhaphanglist.getString("thaydoiId") +"")%>');}"  ></A>															                                    	                   
	                                        	            </TD>
	                                        	          <%} %>
	                                        	            
	                                        	           
                                        	            <%}else { %>
                                        	            
                                        	            <%if(quyen[3]!=0){ %>
	                                	                    <TD>	                                    	                    
	                                	                    	<A href = "../../RouterSvl?task=<%= util.dongMa(getServletContext().getInitParameter("RedirectNoScript") + "ThayDoiKhuyenMaiUpdateSvl?userId="+userId+"&display="+nhaphanglist.getString("thaydoiId") +"")%>"><img src="../images/Display20.png" alt="Hiển thị" width="20" height="20" longdesc="Hiển thị" border = 0 "></A>															                                    	                   
	                                        	            </TD>
	                                        	            <%} %>
                                        	            
                                        	            <%} %>
														</TR>
                                            		 </TABLE>
                                                </TD>
                                            </TR>
                                        <% m++; } 
								}catch(java.sql.SQLException e){e.printStackTrace();}
                               		}%>
                               			<tr class="tbfooter" > 
                               			
                               			<TD align="center" valign="middle" colspan="13" class="tbfooter">
											 	<%if(obj.getNxtApprSplitting() >1) {%>
													<img alt="Trang Dau" src="../images/first.gif" style="cursor: pointer;" onclick="View('nhForm', 1, 'view')"> &nbsp;
												<%}else {%>
													<img alt="Trang Dau" src="../images/first.gif" > &nbsp;
													<%} %>
												<% if(obj.getNxtApprSplitting() > 1){ %>
													<img alt="Trang Truoc" src="../images/prev.gif" style="cursor: pointer;" onclick="View('nhForm', eval(nhForm.nxtApprSplitting.value) -1, 'view')"> &nbsp;
												<%}else{ %>
													<img alt="Trang Truoc" src="../images/prev_d.gif" > &nbsp;
												<%} %>
												
												<%
													int[] listPage = obj.getNextSplittings();
													for(int i = 0; i < listPage.length; i++){
												%>
												
												<% 
												
												
												if(listPage[i] == obj.getNxtApprSplitting()){ %>
												
													<a  style="color:white;"><%= listPage[i] %>/ <%=obj.getTheLastSplitting() %></a>
												<%}else{ %>
													<a href="javascript:View('nhForm', <%= listPage[i] %>, 'view')"><%= listPage[i] %></a>
												<%} %>
													<input type="hidden" name="list" value="<%= listPage[i] %>" />  &nbsp;
												<%} %>
												
												<% if(obj.getNxtApprSplitting() < obj.getTheLastSplitting()){ %>
													&nbsp; <img alt="Trang Tiep" src="../images/next.gif" style="cursor: pointer;" onclick="View('nhForm', eval(nhForm.nxtApprSplitting.value) +1, 'view')"> &nbsp;
												<%}else{ %>
													&nbsp; <img alt="Trang Tiep" src="../images/next_d.gif" > &nbsp;
												<%} %>
												<%if(obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {%>
											   		<img alt="Trang Cuoi" src="../images/last.gif" > &nbsp;
										   		<%}else{ %>
										   			<img alt="Trang Cuoi" src="../images/last.gif" style="cursor: pointer;" onclick="View('nhForm', -1, 'view')"> &nbsp;
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
</form>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</HTML>
<% if(!(nhaphanglist == null)){ nhaphanglist.close(); nhaphanglist = null;} %>
<% if(obj != null){
	obj.DBclose();
	obj = null;
	session.setAttribute("obj",null);
} %>
<% }%>