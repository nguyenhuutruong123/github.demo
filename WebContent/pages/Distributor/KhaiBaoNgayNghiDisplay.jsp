<%@page import="java.util.Calendar"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.distributor.beans.kehoachnhanvien.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "java.util.Date" %>
<%@ page  import = "java.text.DateFormat" %>
<%@ page  import = "java.text.SimpleDateFormat" %>
<%@ page  import = "geso.dms.center.util.*" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	} else { %>
<% IKhaibaongaynghi kstdBean = (IKhaibaongaynghi)session.getAttribute("obj"); 
	
	String[] ngayList = kstdBean.GetNgayNghi();
	String[] ghichu = kstdBean.GetGhichu();
	ResultSet ddkd = (ResultSet)kstdBean.getDDKDRs();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<TITLE>OPV - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
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
		$(".button").hover(function() {
			$(".button img").animate({
				top : "-10px"
			}, 200).animate({
				top : "-4px"
			}, 200) // first jump
			.animate({
				top : "-7px"
			}, 100).animate({
				top : "-4px"
			}, 100) // second jump
			.animate({
				top : "-6px"
			}, 100).animate({
				top : "-4px"
			}, 100); // the last jump
		});
	});
	$(document).ready(function() {
		$(".button1").hover(function() {
			$(".button1 img").animate({
				top : "-10px"
			}, 200).animate({
				top : "-4px"
			}, 200) // first jump
			.animate({
				top : "-7px"
			}, 100).animate({
				top : "-4px"
			}, 100) // second jump
			.animate({
				top : "-6px"
			}, 100).animate({
				top : "-4px"
			}, 100); // the last jump
		});
	});
</script>

	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
    <script type="text/javascript">
        $(document).ready(function(){
            $(".button").hover(function(){
                $(".button img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        }); 
</script>
<SCRIPT language="javascript" type="text/javascript">
	

	
	function saveform()
	{	   
		
		 document.forms['kstdForm'].action.value='save';
	     document.forms['kstdForm'].submit();
	}
	function adddayinyear(){
		 document.forms['kstdForm'].action.value='themngaycn';
	     document.forms['kstdForm'].submit();
	}

	
	
</SCRIPT>

<!-- BEGIN RENDER AUTO -->

<% if (!geso.dms.center.util.GlobalValue.getDevmode()) { %>
	<script type="text/javascript" src="../scripts/disableF12.js"></script>
<% } %>

<!-- END RENDER AUTO -->
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<!-- BEGIN RENDER AUTO -->
<%if(!geso.dms.center.util.GlobalValue.getDevmode()){ %>
	<noscript> 
	 	<meta http-equiv="refresh" content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")  %>">
		<!-- //window.location = "https://www.google.com"; -->
	</noscript>
<%} %>
<!-- END RENDER AUTO -->

<% Csrf csdr=new Csrf(request,response,true,false,true);%>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="id" value='<%= kstdBean.getId()%>'>
<input name="view" type="hidden" value='<%=kstdBean.getView() %>'>
<input name="userId" type="hidden" value='<%=userId %>' size="30">
<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:70%; padding:5px; float:left" class="tbnavigation">
        	&nbsp;Quản lý chỉ tiêu &gt; Khai báo &gt; Khai báo ngày nghỉ > Hiển thị
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng bạn <%= userTen %> &nbsp;&nbsp;
        </div>
    </div>
	<div align="left" style="width:100%; float:none; clear:left">
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
			<TR class = "tbdarkrow">
				<TD width="30" align="left"><A href = "../../KhaibaoNgaynghiSvl?userId=<%=userId + (kstdBean.getView().equals("TT")?"&view=TT":"")%>" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A>
			    	
			    </TD>
			</TR>
		</TABLE>
	</div>
    <div align="left" style="width:100%; float:none; clear:left">
  		<fieldset>
    		<legend class="legendtitle"> Báo lỗi nhập liệu</legend>
    		<textarea name="dataerror" style="width: 100%" readonly="readonly" rows="1" readonly="readonly"><%= kstdBean.getMsg() %></textarea>
		         <% kstdBean.setMsg(""); %>
    	</fieldset>
  	</div>
  	 <div align="left" style="width:100%; float:none; clear:left">
  		<TABLE width="100%" cellspacing="0" cellpadding="0">
			<TR>
				<td>
					<TABLE class="tblight" width="100%" cellspacing="0" cellpadding="6">
						<TR>
				  			<TD class="plainlabel" width="30px">Nhân viên bán hàng</TD>
					  		<TD class="plainlabel">
								<%while(ddkd.next())
			                          	{
			                          		if(ddkd.getString("pk_seq").equals(kstdBean.getDdkdId())) {%>
			                          		<input type="text" value="<%= ddkd.getString("ten")%>">
			                          		<input type="hidden" name="ddkdId" value="<%= ddkd.getString("pk_seq")%>"> 
			                       	  <%}
                          		} %>                             
							</TD>		
						</TR>
						<TR>
						  <TD width="24%" class="plainlabel" >Tháng <FONT class="erroralert">*</FONT></TD>
						  <TD width="70%" class="plainlabel" >
						  	<select name="thang" style="width: 200px;">
						  	<%  String[] thang = {"01","02","03","04","05","06","07","08","09","10","11","12"};
						  		for(int i = 0; i < thang.length; i++) {
						  			if (kstdBean.getThang().equals(thang[i])){%>
							  			<option value ="<%=thang[i] %>" selected><%=thang[i] %></option>
							<%} 	else {%>
										<option value ="<%=thang[i] %>"><%=thang[i] %></option>
							<%		} 
								}%>
							</select>
						  </TD>
					  </TR>
						<TR>
						  <TD class="plainlabel" >Năm <FONT class="erroralert">*</FONT></TD>
						  <TD width="70%" class="plainlabel" >
						  	<select name="nam" style="width: 200px;">
						  	<%  
							  	DateFormat dateFormat = new SimpleDateFormat("yyyy");
						        Date date = new Date();
						        int year = Integer.parseInt(dateFormat.format(date));
						  		for(int i = year; i < year + 2; i++) {
						  			if (kstdBean.getNam().equals(String.valueOf(i))) { %>
							  			<option value ="<%=i %>" selected><%=i %></option>
							<%} 	else { %>
										<option value ="<%=i %>"><%=i %></option>
							<%		}
								}%>
							</select>
						  </TD>
					  	</TR>
					</TABLE>
				</td>
								
			</TR>
  			<tr><td colspan="4" height="10"></td></tr>
			<TR>
					<TD width="98%" colspan="2">
						<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
							<TR class="tbheader">
								<TH width="15%">Ngày nghỉ</TH>
								<TH width="85%">Ghi chú</TH>
							</TR>
						
					<% 
						int m = 0;
						String lightrow = "tblightrow";
						String darkrow = "tbdarkrow";
						for (int i = 0; i < ngayList.length; i ++){
							if (m % 2 != 0) {%>						
								<TR class= <%=lightrow%> >
							<%} else {%>
								<TR class= <%= darkrow%> >
							<%}%>
								<td><input name="ngay" type="text" class="days"  readonly="readonly" value="<%=ngayList[i]%>"></td>
								<td><input name="ghichu" type="text" value="<%=ghichu[i]%>" style="width: 100%"></td>
							</TR>
						<%
						m ++;
						}%>
						
							<TR>
								<TD align="center" colspan="11" class="tbfooter">&nbsp;	</TD>
							</TR>
						</TABLE>
					</TD>
				</TR>
			</TABLE>
	</div>
	</div> 
</form>
</body>
</html>
<% 	
if(kstdBean != null){
	kstdBean.DBclose();
	kstdBean = null;
}
	if(ddkd != null)
		ddkd.close();
%>
<%} %>