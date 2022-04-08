<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.beans.theodoithieuhang.ITheodoithieuhang" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{int[] quyen = new  int[5];
	quyen = util.Getquyen("37",userId);
	
	System.out.println(quyen[0]);
	System.out.println(quyen[1]);
	System.out.println(quyen[2]);
	System.out.println(quyen[3]);	
	System.out.println(quyen[4]); %>

<% ITheodoithieuhang obj = (ITheodoithieuhang)session.getAttribute("obj"); %>
<% ResultSet thlist = (ResultSet)obj.getThlist(); %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">
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

<script type="text/javascript" language="JavaScript" src="../scripts/simplecalendar.js"></script>
<SCRIPT language="javascript" type="text/javascript">


function clearform()
{
    document.thForm.sku.value = "";
    document.thForm.tungay.value = "";
	document.thForm.denngay.value = "";       
    submitform();
}
function exportToExel(){
	document.forms['thForm'].action.value= 'excel';
	document.forms['thForm'].submit();
}

function submitform()
{
	document.forms['thForm'].action.value= 'search';
	document.forms['thForm'].submit();
}

function thongbao()
{
	var tt = document.forms['thForm'].msg.value;
	if(tt.length>0)
    alert(document.forms['thForm'].msg.value);
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

<form name="thForm" method="post" action="../../TheodoithieuhangSvl" charset="utf-8">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<INPUT name="userId" type="hidden" value='<%=userId %>' size="30">
<input type="hidden" name="action" value='1' >
<input type="hidden" name="msg" value='<%=obj.getMsg() %>'>

<script language="javascript" type="text/javascript">
    thongbao();
</script> 

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0">
	
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
		<TABLE width="100%" cellpadding="0" cellspacing="1">
			
				<TR>
					<TD align="left">
					  <table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
						   <TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý bán hàng &gt; Theo dõi thiếu hàng </TD>
   
						   <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %>&nbsp;  </TD>
						  </tr>
 					  </table>
					</TD>
				</TR>
				<TR>
					<TD>
					<TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
						<TR>
							<TD width="100%" align="center" >
							<FIELDSET>
							<LEGEND class="legendtitle">&nbsp;Tiêu chí tìm kiếm &nbsp;</LEGEND>

							<TABLE  width="100%" cellpadding="4" cellspacing="0">
								<TR>
									<TD width="19%" class="plainlabel" >SKU</TD>
								    <TD width="81%" class="plainlabel" ><INPUT name="sku" size="40" type="text" value='<%= obj.getSKU() %>' ></TD>
								</TR>
								
								<TR>
											<TD class="plainlabel" >Từ ngày </TD>
								  	<TD class="plainlabel" >
										<TABLE cellpadding="0" cellspacing="0">
										<TR><TD>
											<input class="days" type="text" name="tungay" value="<%=obj.getTungay() %>" size="20" >
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
													<input class="days" type="text" name="denngay" value="<%=obj.getDenngay() %>" size="20" >
												</TD>
												
                	                     	</TR>
										</TABLE>
									</TD>

								</TR>
								<tR>
									<TD class="plainlabel" colspan="2"></TD>
								</tR>
								<TR>
									<TD class="plainlabel" colspan="2" >
										<a class="button1" href="javascript:submitform()">
  													<img style="top: -4px;" src="../images/Search30.png" alt="">Tìm kiếm </a>
  													 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<a class="button2" href="javascript:clearform()">
												<img style="top: -4px;" src="../images/button.png" 
												alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;	
									<a class="button2" 
										href="javascript:exportToExel()">
											<img style="top: -4px;" 
											src="../images/button.png" alt="">Xuất Excel </a>
											&nbsp;&nbsp;&nbsp;&nbsp;
                                  </TD>								
																		
								</TR>
							</TABLE>

							</FIELDSET>
							</TD>
						</TR>
					</TABLE>
					</TD>
				</TR>

				<TR>
					<TD >
						<FIELDSET>
							<LEGEND class="legendtitle">&nbsp;Theo dõi thiếu hàng &nbsp; &nbsp; &nbsp;
							
						</LEGEND>
						<TABLE width="100%" cellpadding="0" cellspacing="0">
							<TR><TD>
								<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
								<TR class="tbheader">
									<TH width="10%">Mã sản phẩm </TH>
									<TH width="15%">Tên sản phẩm</TH>
									<TH width="5%">Kho</TH>
									<TH width="10%">Thiếu </TH>
									<TH width="12%">Dự tính bổ sung</TH>
									<TH width="12%">Ngày dự tính </TH>
									<TH width="12%">Thực tế bổ sung</TH>
									<TH width="12%">Ngày thực tế </TH>
									<TH width="12%">Trạng thái </TH>
									<TH width="10%" align="center">&nbsp;Tác vụ</TH>
								</TR>
								<%	 
									
									int m = 0;
									String lightrow = "tblightrow";
									String darkrow = "tbdarkrow";
									if(thlist != null){
										while (thlist.next()){
											
											if (m % 2 != 0) {%>
																												
											<TR class= <%=lightrow%> >
										<%} else {%>
											<TR class= <%= darkrow%> >
										<%}%>
											<TD align="center"><div align="left"><%=thlist.getString("masp")%></div></TD>
											<TD align="center"><div align="left"><%=thlist.getString("tensp") %></div></TD>
											<TD align="center"><%=thlist.getString("kho") %></TD>
											<TD align="center"><%=thlist.getString("soluongthieu") %></TD>
									
									<TD align="center"><%=thlist.getString("soluongdt") %> </TD>
									
									<%if(thlist.getString("ngaydt").length()==0){%>
										<TD align="center">&nbsp;</TD>
									<%}else{ %>									
										<TD align="center"><%=thlist.getString("ngaydt").toString() %></TD>
									<%} %>
																		
									<%if(thlist.getString("soluongtt")== null){%>
										<TD align="center">0</TD>
									<%}else{ %>
										<TD align="center"><%=thlist.getString("soluongtt") %></TD>
									<%} %>
									
									<%if(thlist.getString("ngaytt")== null){%>
										<TD align="center">&nbsp;</TD>
									<%}else{ %>
										<TD align="center"><%=thlist.getString("ngaytt").toString() %></TD>
									<%} %>
									
									<% if(thlist.getString("trangthai").equals("0")){%>
											<TD align="center">Chưa nhập kho</TD>	
									<% 	}else{
											if(thlist.getString("trangthai").equals("1")){ %>
												<TD align="center">Nhập kho chưa đủ</TD>	
									<%		}else{ %>
												<TD align="center">Đã nhập kho đủ</TD>
									<%		}
										
										}%>
											
									
									
									<TD align="center">
										<TABLE border = 0 cellpadding="0" cellspacing="0">
											<TR><TD>
											<% if(!thlist.getString("trangthai").equals("2")){%>	
											<%if(quyen[2]!=0){ %>											
												<A href = "../../TheodoithieuhangSvl?userId=<%=userId%>&id=<%=thlist.getString("id")%>" ><img src="../images/Edit20.png" alt="Chinh sua" title="Chỉnh sửa" width="20" height="20" longdesc="Chinh sua" border = 0></A>
											<%} %>
											<%}else{%>
												&nbsp 
											<%} %>
											</TD>
											<TD>&nbsp;</TD>
											</TR>
										</TABLE>												
									 </TD>
									</TR>
									<% 	m++; }}%>
					
								<TR>
									<TD align="center" colspan="11" class="tbfooter">&nbsp;	</TD>
								</TR>
							</TABLE>
							</TD>
						</TR>
					</TABLE>

					</FIELDSET>
					</TD>
				</TR>

		</TABLE>

	</TR>
</TABLE>
</form>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</HTML>
<%
 	}%>