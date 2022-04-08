<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.distributor.beans.quanlykhuyenmai.IChuongtrinhkhuyenmai" %>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/IMV/index.jsp");
	}else{ %>

<% IChuongtrinhkhuyenmai ctkmBean = (IChuongtrinhkhuyenmai)session.getAttribute("ctkm"); %>
<% ResultSet scheme = (ResultSet)ctkmBean.getSchemeRS() ;%>
 <% Hashtable<String, String> budget = (Hashtable<String, String>)ctkmBean.getBudget() ; %>
<% Hashtable<String, String> usedPro = (Hashtable<String, String>)ctkmBean.getusedPro(); %>

<% ctkmBean.setNextSplittings();

	int[] quyen = new  int[6];
	quyen = util.Getquyen("ChuongtrinhkhuyenmaiSvl","", userId);
%>

<% String nnId = (String)session.getAttribute("nnId"); %> 
<% if(nnId == null) {
 nnId = "vi"; 
 
 } 
String url = util.getChuyenNguUrl("ChuongtrinhkhuyenmaiSvl","",nnId); 
 %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="java.sql.SQLException"%>
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">

<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">

<script type="text/javascript" src="../scripts/phanTrang.js"></script>


<!-- Table Sorter -->
<script type="text/javascript" src="../scripts/jquery-latest.js"></script> 
<script type="text/javascript" src="../scripts/jquery.tablesorter.js"></script>	
	
<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js"
	type="text/javascript"></script>
<script type="text/javascript">
	
	$(document).ready(function() {
		$(".days").datepicker({
			changeMonth : true,
			changeYear : true
		});
	});

		</script>

<SCRIPT language="JavaScript" type="text/javascript">
	function submitform() {
		document.forms['ctkhForm'].submit();
	}
	 function xuatexcel()
	 {
			document.forms["ctkhForm"].action.value = "excel";
	    	document.forms["ctkhForm"].submit();
	 }
</SCRIPT>
<style type="text/css">
th {
		cursor: pointer;
		}	
</style>

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

<% Csrf csdr=new Csrf(request,response,true,false,true);%>
<input type="hidden" name="action" value="1" >
<input type="hidden" name="crrApprSplitting" value="<%= ctkmBean.getCrrApprSplitting() %>" >
<input type="hidden" name="nxtApprSplitting" value="<%= ctkmBean.getNxtApprSplitting() %>" >

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;<%= " " + url %> </TD> 
							 <TD colspan="2" align="right" class="tbnavigation"><%=ChuyenNgu.get("Chào mừng",nnId)%>  <%=ctkmBean.getNppTen() %>&nbsp;  </TD></tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
			<TR ><TD >
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR class = "tbdarkrow">
							<!-- <TD width="30" align="left"><A href=""  ><img src="../images/excel.gif" alt="Luu lai"  height = '30' width = '30' title="Luu lai" border="1" longdesc="Luu lai" style="border-style:outset"></A></TD> -->

							<TD >&nbsp; </TD>						
						</TR>
					</TABLE>
			</TD></TR>
		</TABLE>
    <div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
    <fieldset>
    	<legend class="legendtitle"><%=ChuyenNgu.get("Tiếu chí tìm kiếm",nnId)%></legend>
        <div style="width:100%; float:none" align="left">
                <TABLE width="100%" cellpadding="6" cellspacing="0">								
                    <TR>
                        <TD width="10%" class="plainlabel"><%=ChuyenNgu.get("Mã CTKM",nnId)%> </TD>
                        <TD class="plainlabel" colspan='2'><input type="text" value="<%= ctkmBean.getDiengiai() %>" name="diengiai" size="40"></TD>
                    </TR>               
<TR>
	<TD class="plainlabel" ><%=ChuyenNgu.get("Từ ngày",nnId)%></TD>
	<td class="plainlabel">
		<input type="text"  class="days" size="11"
				id="tungay" name="tungay" value="<%= ctkmBean.getTungay() %>" maxlength="10" readonly />
	</td>
</TR>
<TR>
  <TD class="plainlabel" ><%=ChuyenNgu.get("Đến ngày",nnId)%></TD>
  <td class="plainlabel">
		<input type="text"  class="days" size="11"
				id="denngay" name="denngay" value="<%= ctkmBean.getDenngay() %>" maxlength="10" readonly />
	</td>
</TR>

                    <TR>
                        <TD class="plainlabel" ><%=ChuyenNgu.get("Trạng thái",nnId)%></TD>
						<TD colspan="2" class="plainlabel"> 
				 			<SELECT name="trangthai" id="trangthai" class="legendtitle" onChange = submitform();>
					 			 <option value=""></option>
								  <% 											 
											if(ctkmBean.getTrangthai().equals("1")){ %>
												<option value='1' selected><%=ChuyenNgu.get("Còn hiệu lực",nnId)%></option>
										  <%}else{ %>
												<option value='1'><%=ChuyenNgu.get("Còn hiệu lực",nnId)%></option>
										  <%} %>
										  
								  <%		if(ctkmBean.getTrangthai().equals("2")){ %>
												<option value='2' selected><%=ChuyenNgu.get("Hết hiệu lực",nnId)%></option>
										  <%}else{ %>
												<option value='2'><%=ChuyenNgu.get("Hết hiệu lực",nnId)%></option>
										  <%} %>	 
										  	 
                            </SELECT>
                        </TD>
                    </TR>

                    <tr style="background-color:#C5E8CD">
                        <td colspan="0">
                          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  <a class="button" href="javascript:submitform()">
                                <img style="top: -4px;" src="../images/Search30.png" alt=""><%=ChuyenNgu.get("Tìm kiếm",nnId)%> </a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <a class="button2" href="javascript:clearform()">
                                <img style="top: -4px;" src="../images/button.png" alt=""><%=ChuyenNgu.get("Nhập lại",nnId)%></a>
                        </td>
                        
                        <td colspan="1">
						  		&nbsp;&nbsp;&nbsp;&nbsp; <a class="button2" href="javascript:xuatexcel()">
								<img style="top: -4px;" src="../images/button.png" alt=""><%=ChuyenNgu.get("Xuất excel CTKM",nnId)%></a>							
						  	</td>
                    </tr>      				
                </TABLE>       
         </div>
      </fieldset>  
    </div>
		
			<TABLE width="100%" border="0" cellpadding="0"  cellspacing="1" >

				<TR>
				  <TD height="100%" width="100%">
						<FIELDSET >
						<LEGEND class="legendtitle" style="color:black"><%=ChuyenNgu.get("Chương trình khuyến mãi",nnId)%></LEGEND>
						<TABLE id="table" class="tabledetail sortable" border="0" width="100%" cellpadding="6" cellspacing="1">
							<thead><tr class="tbheader">
						  <TH  width="18%" ><%=ChuyenNgu.get("Scheme",nnId)%> </TH>
						  <TH width="30%"><%=ChuyenNgu.get("Diễn giải",nnId)%></TH>
						  <!-- <TH width="10%" >Loại</TH> -->
						  <TH width="10%" ><%=ChuyenNgu.get("Từ ngày",nnId)%> </TH>
						  <TH width="10%" ><%=ChuyenNgu.get("Đến ngày",nnId)%> </TH>
						  <TH width="10%" ><%=ChuyenNgu.get("Ngân sách",nnId)%> </TH>
						  <TH width="10%" ><%=ChuyenNgu.get("Đã sử dụng",nnId)%></TH>
						  <TH width="10%"><%=ChuyenNgu.get("Còn lại",nnId)%></TH>
						  </tr></thead>
						  <tbody>
						  <%NumberFormat formatter = new DecimalFormat("#,###,###"); %>
						  <% try{
							    String lightrow = "tblightrow";
								String darkrow = "tbdarkrow";
								int m = 0;
								if(scheme != null){
									while(scheme.next()){ 
									if (m % 2 != 0) {%>						
										<TR class= <%=lightrow%> >
									<%} else {%>
										<TR class= <%= darkrow%> >
									<%}%>
											<TD align="left"><div align="center"><%= scheme.getString("scheme")%></div></TD>                                   
											<TD align="left"><div align="left"><%=scheme.getString("diengiai") %></div></TD>
											<%-- <%if(scheme.getString("loaict").equals("1")){ %>  
											<TD align="center"><div align="center">Normal</div></TD>
											<%}else{ %>
											<TD align="center"><div align="center">On Top</div></TD>
											<%} %> --%>
											<TD align="left"><div align="center"><%=scheme.getString("tungay") %></div></TD>
											<TD align="left"><div align="center"><%=scheme.getString("denngay") %></div></TD>
										 	<td >  <%=formatter.format(scheme.getDouble("ngansach")) %></td>
										 	<td >  <%=formatter.format(scheme.getDouble("sudung")) %></td>
										 	<td >  <%=formatter.format(scheme.getDouble("ngansach")- scheme.getDouble("sudung"))%></td>
							  			</TR>
						  		<% m++ ;} 
						  		
						  		}%>		
						  		
						  <%}catch(java.sql.SQLException e){}%>

</tbody>
<tfoot>
<tr class="tbfooter" > 
											 <TD align="center" valign="middle" colspan="13" class="tbfooter">
											 	<%if(ctkmBean.getNxtApprSplitting() >1) {%>
													<img alt="Trang Dau" src="../images/first.gif" style="cursor: pointer;" onclick="View('ctkhForm', 1, 'view')"> &nbsp;
												<%}else {%>
													<img alt="Trang Dau" src="../images/first.gif" > &nbsp;
													<%} %>
												<% if(ctkmBean.getNxtApprSplitting() > 1){ %>
													<img alt="Trang Truoc" src="../images/prev.gif" style="cursor: pointer;" onclick="View('ctkhForm', eval(ctkhForm.nxtApprSplitting.value) -1, 'view')"> &nbsp;
												<%}else{ %>
													<img alt="Trang Truoc" src="../images/prev_d.gif" > &nbsp;
												<%} %>
												
												<%
													int[] listPage = ctkmBean.getNextSplittings();
													for(int i = 0; i < listPage.length; i++){
												%>
												
												<% 
												
												System.out.println("Current page:" + ctkmBean.getNxtApprSplitting());
												System.out.println("List page:" + listPage[i]);
												
												if(listPage[i] == ctkmBean.getNxtApprSplitting()){ %>
												
													<a  style="color:white;"><%= listPage[i] %>/ <%=ctkmBean.getTheLastSplitting() %></a>
												<%}else{ %>
													<a href="javascript:View('ctkhForm', <%= listPage[i] %>, 'view')"><%= listPage[i] %></a>
												<%} %>
													<input type="hidden" name="list" value="<%= listPage[i] %>" />  &nbsp;
												<%} %>
												
												<% if(ctkmBean.getNxtApprSplitting() < ctkmBean.getTheLastSplitting()){ %>
													&nbsp; <img alt="Trang Tiep" src="../images/next.gif" style="cursor: pointer;" onclick="View('ctkhForm', eval(ctkhForm.nxtApprSplitting.value) +1, 'view')"> &nbsp;
												<%}else{ %>
													&nbsp; <img alt="Trang Tiep" src="../images/next_d.gif" > &nbsp;
												<%} %>
												<%if(ctkmBean.getNxtApprSplitting() == ctkmBean.getTheLastSplitting()) {%>
											   		<img alt="Trang Cuoi" src="../images/last.gif" > &nbsp;
										   		<%}else{ %>
										   			<img alt="Trang Cuoi" src="../images/last.gif" style="cursor: pointer;" onclick="View('ctkhForm', -1, 'view')"> &nbsp;
										   		<%} %>
											</TD>
										 </tr> </tfoot>


						</TABLE>
						</FIELDSET>
					</TD>
				
				
				</TR>
				
			</TABLE>
		</TD>
		</TR>
		</TABLE>
		</form>
		
	<script type="text/javascript" src="../scripts/script-table-sorter.js"></script>
	<script type="text/javascript">
		var sorter = new TINY.table.sorter("sorter");
		sorter.head = "head";
		sorter.asc = "asc";
		//sorter.desc = "desc";
		sorter.even = "tblightrow";
		sorter.odd = "tbdarkrow";
		sorter.evensel = "evenselected";
		sorter.oddsel = "oddselected";
		sorter.paginate = true;
		sorter.currentid = "currentpage";
		sorter.limitid = "pagelimit";
		sorter.init("table",11);
	</script> 
<% 
/*
	
	*/
	%>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</HTML>

<% 	

	
	try{
		if(scheme != null){ scheme.close(); scheme = null ;}
		if(budget!=null) { budget.clear(); budget = null ; }
		if(usedPro!=null) { usedPro.clear(); usedPro = null ; }
		
		if(ctkmBean!=null){
			ctkmBean.DBclose();
			ctkmBean=null;
		}
		session.setAttribute("ctkm",null);
				
	}
	catch (SQLException e) {}

%>
<%}%>


