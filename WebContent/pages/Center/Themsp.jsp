<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.List" %>
<%@ page  import = "geso.dms.center.beans.themsanphambanggia.IThemSp" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/Traphaco/index.jsp");
	}else{
		
		IThemSp obj =(IThemSp)session.getAttribute("obj");
		ResultSet rsbanggiamua=obj.getRsbanggiamua();
		ResultSet rsbanggiaban=obj.getRsbanggiaban();
		ResultSet rssp=obj.getRssanpham();
		%>

<% int[] quyen = new  int[6];
	quyen = util.Getquyen("ThemSpSvl","",userId);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<SCRIPT language="javascript" type="text/javascript">
function clearform()
{
    document.bgblcForm.bgTen.value = "";
    document.bgblcForm.dvkdId.selectedIndex = 0;
    document.bgblcForm.trangthai.selectedIndex = 0;
    submitform();   
}

function submitform()
{
	document.bgblcForm.action.value= 'search';
	document.forms['bgblcForm'].submit();
}
function saveform()
{
	document.bgblcForm.action.value= 'save';
	document.forms['bgblcForm'].submit();
}


</SCRIPT>

<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
<link href="../css/chosen.css" rel="stylesheet" type="text/css" />
<script src="../scripts/chosen.jquery.js" type="text/javascript"></script>

<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
$(document).ready(function()
{
	$(document).ready(function()
	{
		$(".select2").select2();
	});
});
</script>
<script src="../scripts/chosen.jquery.js" type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$(".days").datepicker({
			changeMonth : true,
			changeYear : true
		});
	});
	jQuery(document).ready(function()
	{
		$("select:not(.notuseselect2)").chosen();     
		
	});
</script>

  

</HEAD>
<body leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="bgblcForm" method="post" action="../../ThemSpSvl">
<input type="hidden" name="userId" value='<%=obj.getUserid() %>'>
<input type="hidden" name="action" value='1'>

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">

		<TABLE width="100%" cellpadding="0" cellspacing="1">
			<TR>
				<TD align="left" class="tbnavigation">
				  <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
					<TR height="22">
					   <TD align="left" colspan="2" class="tbnavigation">
					   		&nbsp;Dữ liệu nền &gt;Sản phẩm &gt; Thêm SP vào bảng giá</TD>
					   <TD colspan="2" align="right" class="tbnavigation">Chào mừng Bạn <%=userTen %>&nbsp;  </TD>
					</TR>
				  </TABLE>
				</TD>
			</TR>
			<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>
	    				<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold" id="errors" style="width: 100%" readonly="readonly" rows="1"><%= obj.getMsg() %></textarea>
						</FIELDSET>
				   </TD>
				</tr>	
		</TABLE>
		<TABLE width="100%" cellpadding="0" cellspacing="1">				
				<TR>
					<TD>
					<TABLE border="0" width="100%"  cellpadding="0" cellspacing="0">
						<TR>
							<TD width="100%" align="left"><FIELDSET>
							<LEGEND class="legendtitle">Tiêu chí  &nbsp;</LEGEND>

							<TABLE  width="100%" cellpadding="5" cellspacing="0">
								<TR>
								<TD class="plainlabel">Chọn loại bảng giá</TD>
								  <TD class="plainlabel"><SELECT  name="loai"  onChange = "submitform();">								  		
								  	 <% 
								    	if(obj.getLoai().equals("2")){ %>
								      		<option value='1' >Bảng giá bán lẻ chuẩn</option>
								      		<option value='2' selected >Bảng giá mua nhà phân phối</option>
								      	<%}else{ %>
								     			<option value='1' selected >Bảng giá bán lẻ chuẩn</option>
								      		<option value='2' >Bảng giá mua nhà phân phối</option>
								     	<%} %>	
								     	
									</SELECT>
								</TD>
								
								<TD class="plainlabel">Tên bảng giá</TD>
								
								<%if(obj.getLoai().equals("2")) {%>
								 <TD class="plainlabel"><SELECT  name="banggiamuaid"  style="width: 300px" >								  		
								  	 <% if(rsbanggiamua!=null)
								    	while(rsbanggiamua.next())
								    	{
								    	%>
								    		<option value='<%=rsbanggiamua.getString("pk_seq")%>' <%if(obj.getIdbanggiamua().equals(rsbanggiamua.getString("pk_seq"))){ %> selected <%} %> ><%=rsbanggiamua.getString("ten")%></option>
								    	<%} %>	
								     	
									</SELECT>
								</TD>
								<%} %>
								
								<%if(obj.getLoai().equals("1")) {%>
								 <TD class="plainlabel"><SELECT  name="banggiabanid"   style="width: 300px">								  		
								  	 <% 
								  	if(rsbanggiaban!=null)
								    	while(rsbanggiaban.next())
								    	{
								    	%>
								    		<option value='<%=rsbanggiaban.getString("pk_seq")%>' <%if(obj.getIdbanggiaban().equals(rsbanggiaban.getString("pk_seq"))){ %> selected <%} %> ><%=rsbanggiaban.getString("ten")%></option>
								    	<%} %>	
								     	
									</SELECT>
								</TD>
								<%} %>
								
									
									
								
								  
								</TR>
								
								
								
								<tr>
										<TD class="plainlabel">Sản phẩm</TD>
										 <TD class="plainlabel"><SELECT  name="spid"   style="width: 300px" >	
										 <option value="" selected="selected"></option>							  		
										  	 <% 
										  	if(rssp!=null)
										    	while(rssp.next())
										    	{
										    	%>
										    		<option value='<%=rssp.getString("pk_seq")%>' <%if(obj.getSpid().equals(rssp.getString("pk_seq"))){ %> selected <%} %> ><%=rssp.getString("ten")%></option>
										    	<%} %>	
										     	
											</SELECT>
										</TD>
										<TD class="plainlabel"></TD>
										<TD class="plainlabel"> </TD>
									
								</tr>
								
								<tr>
										<TD class="plainlabel">Giá sản phẩm</TD>
										<TD class="plainlabel"><input name="giasanpham" value="<%=obj.getGia() %>" /> </TD>
										<TD class="plainlabel">Chiết khấu</TD>
										<TD class="plainlabel"><input name="chietkhau" value="<%=obj.getChietkhau() %>"/> </TD>
								</tr>
								
								
							</TABLE>

							</FIELDSET>
							</TD>
						</TR>
						
							<TR>
					<TD width="100%">
					<FIELDSET>
				
					<%if(quyen[Utility.THEM]!=0) {%>
						<a class="button3" href="javascript:saveform()">
					    	<img style="top: -4px;" src="../images/New.png" alt="" onClick="newform();">Thêm giá </a>
										<%} %>
					
						</FIELDSET>
					</TD>
						
					</TABLE>
					</TD>
				</TR>

				
			
		</TABLE>
		</TD>
	</TR>

</TABLE>
</form>
</body>
</html>
<%
obj.Dbclose();
	}
%>