<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.distributor.beans.chuyenkhonv.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>

<%@ page  import = "geso.dms.center.util.*" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/CaiLan/index.jsp");
	}else{ %>

<% ITrahang pxkBean = (ITrahang)session.getAttribute("pxkBean"); %>
<% ResultSet nvbh = (ResultSet)pxkBean.getNvBanhang(); %>
<% ResultSet kho = (ResultSet)pxkBean.getKhoRs(); %>
<% ResultSet spRs = (ResultSet)pxkBean.getSpRs(); %>
<% NumberFormat formatter = new DecimalFormat("#,###,###"); %>
<% Hashtable<String, Integer> sp_sl = (Hashtable<String, Integer>)pxkBean.getSp_Soluong(); %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<TITLE>SalesUp - Project</TITLE>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">

	<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
    <LINK rel="stylesheet" href="../css/main.css" type="text/css">
    
   	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css"/>
   	<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
   
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
   	
    <script language="javascript" type="text/javascript">
	    function keypress(e) //Hàm dùng d? ngan ngu?i dùng nh?p các ký t? khác ký t? s? vào TextBox
		{    
			var keypressed = null;
			if (window.event)
				keypressed = window.event.keyCode; //IE
			else
				keypressed = e.which; //NON-IE, Standard
			
			if (keypressed < 48 || keypressed > 57)
			{ 
				if (keypressed == 8 || keypressed == 127 || keypressed == 37 || keypressed == 39 || keypressed == 0 || keypressed == 46)
				{//Phím Delete và Phím Back
					return;
				}
				return false;
			}
		}
		
		function submitform()
		{   
			
		    document.forms['pxkForm'].action.value='submitForm';
		    document.forms['pxkForm'].submit();
		}
		
		function saveform()
		{	var soluong = document.getElementsByName("soluong");
			var soluongkho = document.getElementsByName("availablenvbh");
			for(var i = 0; i < soluong.length; i++ )
			{
				if(soluong.item(i).value != '')
				{
					if(parseFloat(soluong.item(i).value.replace(/,/g,"")) > parseFloat(soluongkho.item(i).value.replace(/,/g,"")))
					{
						alert("Số lượng chuyển không được phép lớn hơn tồn kho !");
						return;
					}
				}
				
			}
			var r = confirm("Bạn chắc chắn muốn chốt trả hàng này?");
			if (r == false) {
			    return;
			}
			
			document.forms['pxkForm'].action.value='save';
		    document.forms['pxkForm'].submit();
		}
		
		function DinhDangTien(num) 
		 {
		    num = num.toString().replace(/\$|\,/g,'');
		    if(isNaN(num))
		    num = "0";
		    sign = (num == (num = Math.abs(num)));
		    num = Math.floor(num*100+0.50000000001);
		    num = Math.floor(num/100).toString();
		    for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
		    num = num.substring(0,num.length-(4*i+3))+','+
		    num.substring(num.length-(4*i+3));
		    return (((sign)?'':'-') + num);
		}
		 
		function Dinhdang(element)
		{
			element.value = DinhDangTien(element.value);
			if(element.value== '' )
			{
				element.value= '';
			}
			
			//UPDATE
			Update_SoLuong();
			
		}	
		
		function Update_SoLuong()
		{
			var spIds = document.getElementsByName("spIds");
			var soluong = document.getElementsByName("soluong");
			
			for(var i = 0; i < spIds.length; i++ )
			{
				var _soluong = document.getElementsByName(spIds.item(i).value + "_spSOLUONG");
				//alert('SP ID: ' + spIds[i] + ' --- So dong: ' +  _soluong.length);
				
				var _tongluong = 0;
				for(var j = 0; j < _soluong.length; j++ )
				{
					_tongluong = parseFloat(_tongluong) + parseFloat(_soluong.item(j).value.replace(/,/g,""));
				}
				
				if(parseFloat(_tongluong) > 0 )
					soluong.item(i).value = DinhDangTien(_tongluong);
				else
					soluong.item(i).value = '';
			}
		}
		
	</script>
    

<!-- BEGIN RENDER AUTO -->

<% if (!geso.dms.center.util.GlobalValue.dev_mode) { %>
	<script type="text/javascript" src="../scripts/disableF12.js"></script>
<% } %>

<!-- END RENDER AUTO -->
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<!-- BEGIN RENDER AUTO -->
<%if(!geso.dms.center.util.GlobalValue.dev_mode){ %>
	<noscript> 
	 	<meta http-equiv="refresh" content="0;url=<%=getServletContext().getInitParameter("RedirectNoScript")  %>">
		<!-- //window.location = "https://www.google.com"; -->
	</noscript>
<%} %>
<!-- END RENDER AUTO -->

<form name="pxkForm" method="post" action="../../TrahangUpdateSvl"> 
 <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<INPUT name="userId" type="hidden" value='<%= userId %>' size="30">
<INPUT name="nppId" type="hidden" value='<%= pxkBean.getNppId() %>' size="30">
<input type="hidden" name="action" value='1'>
<div id="main" style="width:99%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:50%; padding:5px; float:left" class="tbnavigation">
        	Quản lý tồn kho > Trả hàng > Tạo mới
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng nhà phân phối  <%= pxkBean.getNppTen() %> 
        </div>
    </div>
  
  	<div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="tbdarkrow">
    	<A href= "../../TrahangSvl?userId=<%=userId %>" >
        	<img src="../images/Back30.png" alt="Quay về"  title="Quay về" border="1px" longdesc="Quay ve" style="border-style:outset"></A>
        <A href="javascript:saveform()" >
        	<IMG src="../images/Save30.png" title="Lưu lại" alt="Lưu lại" border ="1px" style="border-style:outset"></A>
    </div>
    
  	<div align="left" style="width:100%; float:none; clear:left">
  		<fieldset>
    		<legend class="legendtitle">Báo lỗi nhập liệu</legend>
    		<textarea name="dataerror" style="width:100%" rows="1" readonly="readonly"><%= pxkBean.getMessage() %></textarea>
		         <% pxkBean.setMessage(""); %>
    	</fieldset>
  	</div>
  	
    <div align="left" style="width:100%; float:none; clear:left">
    <fieldset>
    	<legend class="legendtitle">Trả hàng </legend>
        	
        	<div style="float:none; width:100%" align="left">
            	<table width="100%" cellspacing="0" cellpadding="6px">
                    <tr>
                    	<td class="plainlabel" width="20%" valign="middle">Ngày trả</td>
                    	<td class="plainlabel">
                            <input type="text" size="11"  class="days"
                                    id="ngaychuyen" name="ngaychuyen" value="<%= pxkBean.getNgaychuyen() %>" maxlength="10" readonly />
                    	</td> 
                    	<td class="plainlabel">&nbsp;</td>                   
                    </tr>       
                    <tr>
           				<td class="plainlabel">Nhân viên bán hàng<FONT class="erroralert"> *</FONT></td> 
                        <td class="plainlabel">
                            <select name="nvbhId" id="nvbhId" onchange="submitform()">
                            	<option value="">&nbsp;</option>
                                <% if(nvbh != null){
									  try{ while(nvbh.next()){ 
						    			if(nvbh.getString("nvbhId").equals(pxkBean.getNvbhId())){ %>
						      				<option value='<%=nvbh.getString("nvbhId")%>' selected><%=nvbh.getString("nvbhTen") %></option>
						      			<%}else{ %>
						     				<option value='<%=nvbh.getString("nvbhId")%>'><%=nvbh.getString("nvbhTen") %></option>
						     	<%}} nvbh.close(); }catch(Exception e){ System.out.println("Exception: " + e.getMessage()); } }%>
                            </select>	
                        </td>
                        <td class="plainlabel">&nbsp;</td>
                    </tr>
                    <tr>
           				<td class="plainlabel">Kho nhận<FONT class="erroralert"> *</FONT></td> 
                        <td class="plainlabel">
                            <select name="khoId" id="khoId" onchange="submitform()">
                            	<option value="">&nbsp;</option>
                                <% if(kho != null){
									  try{ while(kho.next()){ 
						    			if(kho.getString("pk_seq").equals(pxkBean.getKhoId())){ %>
						      				<option value='<%=kho.getString("pk_seq")%>' selected><%=kho.getString("khoTen") %></option>
						      			<%}else{ %>
						     				<option value='<%=kho.getString("pk_seq")%>'><%=kho.getString("khoTen") %></option>
						     	<%}} kho.close(); }catch(java.sql.SQLException e){} }%>
                            </select>	
                        </td>
                        <td class="plainlabel">&nbsp;</td>
                    </tr>
                 </table>
                 <hr>
                 <% if(spRs != null){ %>
                 <table width="100%" cellpadding="0px" cellspacing="1px">
                 	<tr>
                    	<th class="tbheader" align="center" width="10%">Mã sản phẩm</th>
                        <th class="tbheader" align="center" width="35%">Tên sản phẩm</th>
                        <th class="tbheader" align="center" width="10%">Đơn vị tính</th>
                        <th class="tbheader" align="center" width="15%">Tồn tại kho NPP</th>
                        <th class="tbheader" align="center" width="15%">Tồn tại kho NVBH</th>
                        <th class="tbheader" align="center" width="10%">Số lượng trả</th>
                        
                    </tr>
                    
                    <% try { 
                    	
                    	int count = 0;
                    	while(spRs.next()){
                    	
                    	%>
		    			<tr class="tbdarkrow">
	                    	<td>
	                    		<input type="hidden" name="spIds" value="<%= spRs.getString("spId") %>" >
	                    		<input type="text" value="<%= spRs.getString("spMa") %>" style="width: 100%" disabled="disabled">	
	                    	</td>
	                        <td>
	                        	<input type="hidden" name="spTen" value="<%= spRs.getString("spTen") %>" style="width: 100%" >	
	                    		<input type="text"  value="<%= spRs.getString("spTen") %>" style="width: 100%" disabled="disabled">	
	                    	</td>
	                        <td>
	                    		<input type="text" value="<%= spRs.getString("donvi") %>" style="width: 100%" disabled="disabled">	
	                    	</td>
	                        <td>
	                    		<input type="text" name="available" value="<%= formatter.format(spRs.getDouble("available")) %>" style="width: 100%; text-align: right;" disabled="disabled">	
	                    	</td>
	                    	<td>
	                    		<input type="text" name="availablenvbh" value="<%= formatter.format(spRs.getDouble("avaiNVBH")) %>" style="width: 100%; text-align: right;" disabled="disabled">	
	                    	</td>
	                    	<td>
								<% if(sp_sl.get( spRs.getString("spId")) != null ) { %>
			                    <input type="text" style="width: 100%;text-align: right;" name="soluong" value="<%= formatter.format((double) sp_sl.get( spRs.getString("spId")  ) ) %>"  >
			                        				<% } else { %>
								<input type="text" name="soluong" value="" style="width: 100%; text-align: right;" onkeypress="return keypress(event);">
								<%} %>
								</td>
                    	</tr>
	     			<% count++; } } catch(Exception e){}%>
	     			
                    <tr class="tbfooter"><td colspan="8">&nbsp;</td></tr>
                 </table>
                 <%} %>
            </div>
            <div align="left" style="width:100%; float:none; clear:none; display:none" id="sanphamList">              
        </div>      
    </fieldset>	
    </div>
</div>
</form>
</BODY>
<script type="text/javascript">
	Update_SoLuong();
</script>

</html>
<% 	

	try{
	if(nvbh != null)
		nvbh.close();
	if(pxkBean != null){
		pxkBean.DBclose();
	pxkBean= null;}
	
	}
	catch (SQLException e) {}

%>
<%}%>