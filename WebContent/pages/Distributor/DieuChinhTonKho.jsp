<%@page import="com.cete.dynamicpdf.kb"%>
<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.distributor.beans.dieuchinhtonkho.IDieuchinhtonkhoList" %>
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

<% IDieuchinhtonkhoList obj = (IDieuchinhtonkhoList)session.getAttribute("obj"); %>
<% ResultSet dctklist = (ResultSet)obj.getDctkList(); %>
<% ResultSet dvkd = (ResultSet)obj.getDvkd(); %>
<% ResultSet kbh = (ResultSet)obj.getKbh(); %>
<% ResultSet kho = (ResultSet)obj.getKho(); %>
<% String nppId = (String) obj.getNppId();  

	int[] quyen = new  int[6];
	quyen = util.Getquyen("DieuchinhtonkhoSvl","", userId);
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
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<script type="text/javascript" src="../scripts/jquery.js"></script> 
<link rel="stylesheet" href="../css/jqueryautocomplete.css" type="text/css" />
<script type="text/javascript" src="../scripts/jqueryautocomplete.js"></script>
<script type="text/javascript" language="JavaScript" src="../scripts/simplecalendar.js"></script>

<SCRIPT language="JavaScript" type="text/javascript">
function clearform()
{
  
	 document.forms['dctkForm'].action.value= 'clear';
	 document.forms["dctkForm"].submit();
    
}

function submitform()
{   
   document.forms['dctkForm'].action.value= 'search';
   document.forms["dctkForm"].submit();
}

function newform()
{
	document.forms['dctkForm'].action.value= 'new';
	document.forms['dctkForm'].submit();
}
function thongbao()
{   var tt = document.forms['dctkForm'].msg.value;
	if(tt.length>0)
    	alert(document.forms['dctkForm'].msg.value);
	
	document.forms['dctkForm'].msg.value= '';
}

</SCRIPT>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="dctkForm" method="post" action="../../DieuchinhtonkhoSvl">
<input type="hidden" name="userId" value='<%=userId%>'>
<input type="hidden" name="nppId" value='<%=nppId%>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="msg" value='<%= obj.getMsg()==null?"":obj.getMsg() %>'>
<script language="javascript" type="text/javascript">
    thongbao();
</script>
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
    height="100%">
    <TR>
        <TD colspan="4" align='left' valign='top'>

        <TABLE width="100%"  cellspacing="1" cellpadding="0">
            <TR>
                <TD align="left" class="tbnavigation">
                       <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
                           <TR height="22">
                               <TD align="left"  class="tbnavigation">&nbsp;Quản lý tồn kho > Mua hàng từ nhà cung cấp > Điều chỉnh tồn kho </TD>
                               <TD align="right" class="tbnavigation">Chào mừng bạn  <%= obj.getNppTen() %> </TD>

                             </TR>
                      </TABLE>
                  </TD>
          </TR> 
        </TABLE>
        <TABLE width="100%"  cellspacing="0" cellpadding="0">      
            <TR>
                <TD>
                    <TABLE border="0" width="100%" cellspacing="0" cellpadding="0">
                        <TR>

                            <TD width="100%" align="left">
                            <FIELDSET>
                            <LEGEND class="legendtitle">&nbsp;Tiêu chí hiển thị &nbsp;</LEGEND>

                            <TABLE width="100%" cellpadding="6" cellspacing="0">
								<TR>
								  <TD width="19%" class="plainlabel">Đơn vị kinh doanh</TD>
								  <TD width="81%" class="plainlabel"><SELECT name="dvkdId" onChange="submitform();">
								  <option value="" > </option>
								<%  try{
								  		while(dvkd.next()){								  			
								  			if (obj.getDvkdId().equals(dvkd.getString("dvkdId"))){ %>								  			
								  				<option value="<%= dvkd.getString("dvkdId")%>" selected><%= dvkd.getString("dvkd")%></option>
								  		  <%}else{ %>		
								  		  		<option value="<%= dvkd.getString("dvkdId")%>" ><%= dvkd.getString("dvkd")%></option>
								  	<%		}
								  		}
								  	}catch (java.sql.SQLException e){} %>
                                  </SELECT></TD>
							  	</TR>
								<TR>
								  <TD class="plainlabel">Kênh bán hàng</TD>
								  <TD class="plainlabel"><SELECT name="kbhId" onChange="submitform();">
								  <option value="" > </option>
								<%  try{
								  		while(kbh.next()){								  			
								  			if (obj.getKbhId().equals(kbh.getString("kbhId"))){ %>								  			
								  				<option value="<%= kbh.getString("kbhId")%>" selected><%= kbh.getString("kbh")%></option>
								  		  <%}else{ %>		
								  		  		<option value="<%= kbh.getString("kbhId")%>" ><%= kbh.getString("kbh")%></option>
								  	<%		}
								  		}
								  	}catch (java.sql.SQLException e){} %>
                                  </SELECT></TD>
							  	</TR>
                                
                                <TR>
                                  <TD class="plainlabel">Kho</TD>
								  <TD class="plainlabel"><SELECT name="khoId" onChange="submitform();">
								  <option value="" > </option>
								<%  try{
								  		while(kho.next()){								  			
								  			if (obj.getKhoId().equals(kho.getString("khoId"))){ %>								  			
								  				<option value="<%= kho.getString("khoId")%>" selected><%= kho.getString("ten")+"-"+kho.getString("diengiai")%></option>
								  		  <%}else{ %>		
								  		  		<option value="<%= kho.getString("khoId")%>" ><%= kho.getString("ten")+"-"+kho.getString("diengiai")%></option>
								  	<%		}
								  		}
								  	}catch (java.sql.SQLException e){} %>
                                  </SELECT></TD>

                                </TR>
								<TR>
									<TD class="plainlabel" >Từ ngày</TD>
									<td class="plainlabel">
										<input type="text"  class="days" size="11"
												id="tungay" name="tungay" value="<%= obj.getTungay() %>" maxlength="10" readonly onchange="submitform();" />
									</td>
								</TR>
								<TR>
								  <TD class="plainlabel" >Ðến ngày</TD>
								  <td class="plainlabel">
										<input type="text"  class="days" size="11"
												id="denngay" name="denngay" value="<%= obj.getDenngay() %>" maxlength="10" readonly onchange="submitform();"  />
									</td>
								</TR>
								
								 <TR>
                                	<TD class="plainlabel">Trạng thái</TD>
                                	<TD class="plainlabel">
                                	<select name = "trangthai" id = "trangthai" onchange="submitform();">
                                	<%if(obj.getTrangthai().equals("0")) {%>
                                		<option value= ""></option>
                                		<option value= "0" selected="selected">Chưa duyệt</option>
                                		<option value= "1">Đã duyệt</option>
                                		<option value= "2">Đã hủy</option>
                                	<%}else if(obj.getTrangthai().equals("1")){ %>
                                		<option value= ""></option>
                                		<option value= "0">Chưa duyệt</option>
                                		<option value= "1" selected="selected">Đã duyệt</option>
                                		<option value= "2">Đã hủy</option>
                                	<%}else if(obj.getTrangthai().equals("2")){ %>
                                		<option value= ""></option>
                                		<option value= "0">Chưa duyệt</option>
                                		<option value= "1">Đã hủy</option>
                                		<option value= "2" selected="selected">Đã xóa</option>
                                	<%}else { %>
                                		<option value= "" selected="selected"></option>
                                		<option value= "0">Chưa duyệt</option>
                                		<option value= "1">Đã duyệt</option>
                                		<option value= "2">Đã hủy</option>
                                	<%} %>
                                	</select>
                                	</TD>
                                </TR>
                         <TR>
                                    <TD class="plainlabel">
                                    <a class="button2" href="javascript:clearform()">
									<img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;	
                                    <!-- 
                                      <INPUT name="reinitialiser" type="button" value="Nhap lai" onClick="clearform();">  --></TD>
                                    <TD class="plainlabel">&nbsp;</TD>
                                </TR>
                            </TABLE>

                            </FIELDSET>
                            </TD>
                        </TR>
                    </TABLE>
                    </TD>
                </TR>
            </TABLE>

        <TABLE width="100%" cellspacing="0" cellpadding="0">                  
            <TR>
                <TD width="100%">
                    <FIELDSET>
                    <LEGEND class="legendtitle">&nbsp;Điều chỉnh tồn kho &nbsp;&nbsp;&nbsp;
                    <%if(quyen[0]!=0){ %>
                    <a class="button3" href="javascript:newform()">
    				<img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>                            
                    <%} %>
                   <!-- <INPUT name="new" type="button" value="Tao moi" onClick="newform();">  --> </LEGEND>
    
                    <TABLE class="contenu_fieldset" width="100%" cellpadding="0" cellspacing="0">
                        <TR>
                            <TD>
                                <TABLE width="100%" border="0" cellspacing="1" cellpadding="4">

                                    <TR class="tbheader">
                                        <TH >Ngày điều chỉnh </TH>
                                        <TH >Số chứng từ </TH>
                                       
                                        <TH >Đơn vị kinh doanh </TH>
                                        <TH >Kênh bán hàng </TH>
                                        <TH >Kho </TH>
                                        <TH >Trạng thái </TH>
                                        <TH >Người tạo </TH>
                                        <TH >Người sửa </TH>
                                        <TH >Người duyệt </TH>

                                        <TH " align="center">&nbsp;Tác vụ</TH>
                                    </TR>
                                     <%                                      
                                    NumberFormat formatter = new DecimalFormat("#,###,###");                                     
                                    int m = 0;
                                    String lightrow = "tblightrow";
                                    String darkrow = "tbdarkrow";
                                    while (dctklist.next()){
                                    	//System.out.println("tong tien:"+dctklist.getString("tongtien"));
                                        if (m % 2 != 0) {%>                     
                                            <TR class= <%=lightrow%> >
                                        <%} else {%>
                                            <TR class= <%= darkrow%> >
                                        <%}%>
                                                <TD align="left"><div align="left"><%= dctklist.getString("ngaydc") %></div></TD>                                   
                                                <TD><div align="center"><%= dctklist.getString("chungtu") %></div></TD>
                                               
                                                <TD align="center"><%= dctklist.getString("dvkd") %></TD>
                                                <TD align="center"><%= dctklist.getString("kbh") %></TD>
                                                <TD align="center"><%= dctklist.getString("ten") %></TD>
                                             <% if (dctklist.getString("trangthai").equals("0")){ %>
                                                <TD align="center">Chờ duyệt</TD>
                                             <%}else if (dctklist.getString("trangthai").equals("1")){ %>
                                             	<TD align="center">Đã duyệt</TD>
                                            <%}else{ %>
                                             	<TD align="center">Đã hủy</TD>
                                             <%} %>
                                                <TD align="center"><%= dctklist.getString("nguoitao") %></TD>
                                                <TD align="center"><%= dctklist.getString("nguoisua") %></TD>
                                                <% if (dctklist.getString("nguoiduyet").equals("0")) {%>
                                                <TD align="center">&nbsp;</TD>
                                                <%}else{ %>
                                                <TD align="center"><%= dctklist.getString("nguoiduyet") %></TD>
                                                <%} %>
                                                <TD align="center">
                                                <TABLE border = 0 cellpadding="0" cellspacing="0">
                                                
                                             <% if (dctklist.getString("trangthai").equals("0") && dctklist.getInt("NppDaChot")==0 ){ %>                                                
                                                    <TR>
	                                                    <TD>
	                                                    	<%if(quyen[2]!=0){ %>
	                                                        <A href = "../../DieuchinhtonkhoUpdateSvl?userId=<%=userId%>&update=<%= dctklist.getString("chungtu")%>"><img src="../images/Edit20.png" alt="Cập nhật" title="Cập nhật" width="20" height="20" longdesc="Cập nhật" border = 0 "></A>
	                                                    	<%} %>
	                                                    </TD>
	                                                   <TD>
	                                                   		<%if(quyen[1]!=0){ %>
	                                                        <A href = "../../DieuchinhtonkhoSvl?userId=<%=userId%>&delete=<%= dctklist.getString("chungtu")%>"><img src="../images/Delete20.png" alt="Xóa" title="Xóa" width="20" height="20" longdesc="Xóa" border = 0 "></A>
	                                                   		<%} %>
	                                                   </TD>
		                                            <TD> 
		                                            	<%if(quyen[4]!=0){ %>
		                                            	<A href = "../../DieuchinhtonkhoSvl?userId=<%=userId%>&chot=<%= dctklist.getString("chungtu") %>"><img src="../images/Chot.png" alt="Chốt" title="Chốt" width="20" height="20" border=0 onclick="if(!confirm('Bạn có muốn chốt điều chỉnh tồn kho này?')) return false;"></A>
		                                            	<%} %>
		                                            </TD>
	                                           </TR>
                                            <%}else  { %>
                                                    <TR>
	                                                    <TD>
	                                                    	<%if(quyen[3]!=0){ %>
	                                                        <A href = "../../DieuchinhtonkhoDisplaySvl?userId=<%=userId%>&display=<%= dctklist.getString("chungtu")%>"><img src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" width="20" height="20" longdesc="Hiển thị" border = 0 "></A>
	                                                    	<%} %>
	                                                    </TD>
                                                    </TR>
                                            <%} %>                                             
                                               </TABLE>
                                                </TD>
                                            </TR>
                                        <% m++; } %>
                                   <tr class="tbfooter"><td colspan="12">&nbsp; </td></tr>
                                </TABLE>
                            </TD>
                        </TR>
                    </TABLE>

                    </FIELDSET>
                </TD>
            </TR>

        </TABLE>
        <!--end body Dossier--></TD>
    </TR>
</TABLE>
</form>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</HTML>

<% 	

	try{
	if(dctklist != null){ dctklist.close(); dctklist = null;}
	if(dvkd != null){ dvkd.close(); dvkd = null; }
	if(kbh != null){ kbh.close(); kbh = null; }
	if(kho != null){ kho.close(); kho = null; }
	
	if(obj != null){
		obj.DBclose();
		obj = null;
	}
	session.setAttribute("obj",null);
	
	}
	catch (SQLException e) {}

%>
<%}%>
