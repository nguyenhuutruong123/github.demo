<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.distributor.beans.dangkytrungbay.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>

<%@ page  import = "geso.dms.center.util.*" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>
<% IDangkytrungbay dktbBean = (IDangkytrungbay)session.getAttribute("dktbBean"); %>
<% ResultSet nvbh = (ResultSet)dktbBean.getNvBanhang(); %>
<% ResultSet cttrungbay = (ResultSet)dktbBean.getCttrungbay(); %>
<% ResultSet khList = (ResultSet)dktbBean.getKhList(); %>
<% Hashtable<Integer, String> nvbhIds = (Hashtable<Integer, String>)dktbBean.getNvbhIds(); %>
<% Hashtable<String, Integer> khachhang_dangky = (Hashtable<String, Integer>)dktbBean.getKh_Dangky(); %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<TITLE>Best - Project</TITLE>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">
    <LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
    <LINK rel="stylesheet" href="../css/main.css" type="text/css">
    <LINK rel="stylesheet" href="../css/datepicker.css" type="text/css">
    
    <script language="javascript" src="../scripts/datepicker.js"></script>
   	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css"/>
  	<script type="text/javascript" src="../scripts/Timepicker/jquery.min.js"></script>
  	<script type="text/javascript" src="../scripts/Timepicker/jquery-ui.min.js"></script>
   
  	<script type="text/javascript" src="..scripts/jquery-1.js"></script>
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
		$(document).ready(function(){
            $(".button2").hover(function(){
                $(".button2 img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        }); 
    </script>
    <script type="text/javascript">
	    function saveform()
		{  
			var cttb = document.getElementById("cttbTen");
			if(cttb.value == "")
			{
				alert('Vui lòng chọn chương trình trưng bày...');
				return;
			}
			
			if(checkDangky() == false)
			{
				alert('Không có khách hàng nào được chọn để đăng ký tham gia chương trình...');
				return;
			}
			
			var sosuatphanbo = document.getElementById("sosuatphanbo").value;
			var sosuatdaphanbo = document.getElementById("sosuatdaphanbo").value;
			if(parseFloat(sosuatdaphanbo) > parseFloat(sosuatphanbo))
			{
				alert('Tổng đăng ký phải nhỏ hơn ngân sách');
				return;
			}
			
			document.getElementById("saveid").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Proc...</a>";	
			document.forms['dktbForm'].action.value='save';
			document.forms['dktbForm'].submit();
		}
	    
	    function checkDangky()
	    {
	    	
	    	var dangky = document.getElementsByName("dangky");
	    	for(i = 0; i < dangky.length; i++)
			{
				if(dangky.item(i).value != null)
					return true;
			}
	    	return false;
	    }
	    
	    function Check(str)
		{
	    	kiemtrahetphanbo();
	    	var dangkythem = document.forms['dktbForm'].dangkythem.value;
	    	
			/* var id = document.getElementById(str);
			var hidden = document.getElementById(str + '.hidden').value;
			if( parseInt(id.value) > parseInt(hidden))
			{
				alert('Số xuất đăng ký phải nhỏ hơn số xuất bán đã mua..');
				id.value = "";
				return;
			} */
			congdon();
		}
	    
		function submitform()
		{
			document.forms['dktbForm'].action.value = 'submit';
		    document.forms['dktbForm'].submit();
		}
		
		function submitform2()
		{
			var cttb = document.getElementById("cttbTen");
			if(cttb.value == "")
			{
				alert('vui lòng chọn chương trình trưng bày...');
				return;
			}
			document.forms['dktbForm'].action.value='submit';
		    document.forms['dktbForm'].submit();
		}
			
		function kiemtrahetphanbo()
		{
			var sosuatphanbo = document.getElementById("sosuatphanbo").value;
			var sosuatdaphanbo = document.getElementById("sosuatdaphanbo").value;
			var tong =0;
			var conlai = sosuatphanbo;// - sosuatdaphanbo;
	    	var dangky = document.getElementsByName("dangky");
	    	for(i = 0; i < dangky.length; i++)
			{
			  	tong = tong +   parseInt(dangky.item(i).value) ;
			    if(tong > conlai)
		    	{
			    	alert('Bạn chỉ còn ' + conlai +' xuất phân bổ, Vui lòng điều chỉnh lại');
			    	//dangky.item(i).value = 0;
			    	return;
		    	}
			}
			
		}
		
		function congdon()
	    {  	
			var sosuatphanbo = document.getElementById("sosuatdaphanbo");
	    	
			var hidden = document.getElementsByName("dangky");
			var tong = 0;
			
			for(i = 0; i< hidden.length; i++)
			{   
				if(hidden.item(i).value.length > 0)
				  tong = tong + parseInt(hidden.item(i).value);
			}
			
			sosuatphanbo.value = tong;
	    }
		
		function check_all(value)
		{
			var checkone=document.getElementsByName("nvbhIds");
			for(var i=0; i<checkone.length; i++ )
			{
				checkone.item(i).checked=value;
			}
		}
		
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
	</script>

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

<form name="dktbForm" method="post" action="../../DangkytrungbayUpdateSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<INPUT name="userId" type="hidden" value='<%=userId %>' size="30">
<input type="hidden" name="nppId" value='<%= dktbBean.getNppId() %>'>
<input type="hidden" name="dangkythem" value='<%= dktbBean.getdangkythem() %>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="id" value='<%= dktbBean.getId() %>'>

<div id="main" style="width:99%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:50%; padding:5px; float:left" class="tbnavigation">
        	Quản lý trưng bày > Đăng ký trưng bày > Cập nhật
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng bạn  <%= dktbBean.getNppTen() %> &nbsp;
        </div>
    </div>
  
  	<div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="tbdarkrow">
    	<A href= "../../DangkytrungbaySvl?userId=<%= userId %>" >
        	<img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1px" longdesc="Quay ve" style="border-style:outset"></A>
        <A id="saveid" href="javascript:saveform()" >
        	<IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border ="1px" style="border-style:outset"></A>
    </div>
  	<div align="left" style="width:100%; float: none"> 
    	<fieldset>
        	<legend class="legendtitle">Báo lỗi nhập liệu </legend>
            <textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold" cols="71" rows="1"  style="width: 100% " readonly="readonly" ><%= dktbBean.getMessage() %></textarea>
            <% dktbBean.setMessage(""); %>
        </fieldset>
    </div>
    <div align="left" style="width:100%; float:none">
    <fieldset>
    	<legend class="legendtitle"> Đăng ký trưng bày </legend>
        <div style=" width:100%; float:non; clear:left; font-size:0.7em">
        <TABLE width="100%" cellpadding="5px" cellspacing="0px">
              <TR>
                <TD class="plainlabel"  width="23%">Chương trình </TD>
                <TD class="plainlabel" colspan="2">
                    <select name="cttbTen" id="cttbTen" onchange="submitform()">
                        <option value="">&nbsp;</option>
                            <% if(cttrungbay != null){
					  		try{ while(cttrungbay.next()){ 
		    					if(cttrungbay.getString("cttbId").equals(dktbBean.getCttbId())){ %>
		      					<option value='<%=cttrungbay.getString("cttbId")%>' selected><%= cttrungbay.getString("scheme") %></option>
		      				<%}else{ %>
		     					<option value='<%=cttrungbay.getString("cttbId")%>'><%=cttrungbay.getString("scheme") %></option>
		     			<%}} cttrungbay.close(); }catch(java.sql.SQLException e){} }%>
                     </select>
                </TD>
            </TR>	
            <TR>
                <TD class="plainlabel">Diễn giải </TD>
                <TD class="plainlabel" colspan="2">
                 <%=dktbBean.getdiengiai() %>
                     </TD>
            </TR> 
             <TR>
                <TD class="plainlabel">Thời gian tính doanh số : Từ ngày</TD>
                <TD class="plainlabel" colspan="2">
                    <input type="text" size="15" name="batdauds" value="<%=dktbBean.getTgbdTinhds() %>" disabled="disabled" />
                     &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; Đến ngày  &nbsp;&nbsp;&nbsp;&nbsp;
                    <input type="text" size="15" name="ketthucds" value="<%=dktbBean.getTgktTinhds() %>" disabled="disabled" />
                </TD>
            </TR> 
            <TR>
                
            </TR> 							
            <TR>
                <TD class="plainlabel">Thời gian trưng bày &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:Từ ngày </TD>
                <TD class="plainlabel" colspan="2">
                    <input type="text" size="15" name="batdau" value="<%= dktbBean.getThoigianbd() %>" disabled="disabled" />
                   &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; Đến ngày &nbsp;&nbsp;&nbsp;&nbsp; 
                    <input type="text" size="15" name="ketthuc" value="<%= dktbBean.getThoigiankt() %>" disabled="disabled" />
                </TD>
            </TR> 

            <TR>
                <TD class="plainlabel">Thời gian đăng ký &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:Từ ngày </TD>
                <TD class="plainlabel" colspan="2">
                    <input type="text" size="15" name="batdau" value="<%= dktbBean.getThoigianbddk() %>" disabled="disabled" />
                    &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Đến ngày&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
                    <input type="text" size="15" name="ketthuc" value="<%= dktbBean.getThoigianktdk()  %>" disabled="disabled" />
                </TD>
            </TR> 

            <TR>
                
            </TR> 
           
            <TR>
                <TD class="plainlabel">Số lần thanh toán</TD>
                <TD class="plainlabel" colspan="2">
                    <input type="text" size="15" name="solan" value="<%= dktbBean.getSolantt() %>" disabled="disabled" />
                </TD>                 
            </TR>            
            <TR>
                <TD class="plainlabel">Số suất phân bổ</TD>
                <TD class="plainlabel" colspan="2">
                    <input type="text" size="15" name="sosuatphanbo" id ="sosuatphanbo" value="<%= dktbBean.getSosuatphanbo() %>" disabled="disabled" />
                </TD>                 
            </TR>   	
            <TR>
                <TD class="plainlabel">Số suất đã đăng ký</TD>
                <TD class="plainlabel" colspan="2">
                       <input type="text" size="15" name="sosuatdaphanbo" id ="sosuatdaphanbo" value="<%= dktbBean.getSosuatdaphanbo() %>" disabled="disabled" />
                  </TD>                 
            </TR>  				
        </TABLE>
        </div>
        <hr>
        <div style="width:100%; float:none; clear:left" align="left">
            <table style="width:100%; cellpadding="4px" cellspacing="1px">
                <tr class="tbheader">
                    <th align="center">Mã NVBH</th>
                    <th align="left">Họ tên</th>
                    <th align="left">Địa chỉ</th>
                    <th align="center">Điện thoại</th>
                    <th align="center">Chọn <input type="checkbox" name="checkall"  onchange="check_all(this.checked);"> </th>
                </tr>
                <% 
                	
                	if(nvbh != null){
                		int i = 0;
				  		try{ while(nvbh.next()){ 
				  		 	if (i % 2 == 0){ %>
				  		 <tr class="tblightrow">
				  		 <%}else{ %>
	    				 <tr class="tbdarkrow">
	    				 <%} %>
		                    <td align="center"><%= nvbh.getString("nvbhId") %></td>
		                    <td align="left"><%= nvbh.getString("nvbhTen") %></td>
		                    <td align="left"><%= nvbh.getString("diachi") %></td>
		                    <td align="center"><%= nvbh.getString("dienthoai") %></td>
		                    <% if(nvbhIds.contains(nvbh.getString("nvbhId"))){ %>
			                    <td align="center">
			                    	<input type="checkbox" name="nvbhIds" value="<%= nvbh.getString("nvbhId") %>" checked></td>
		                   <%}else{ %>
		                   		<td align="center">
			                    	<input type="checkbox" name="nvbhIds" value="<%= nvbh.getString("nvbhId") %>"></td>
		                   <%} %>
		                </tr>	 
	     		<% i++;} nvbh.close(); }catch(java.sql.SQLException e){} }%>      
                <tr class="plainlabel"><td colspan="5">&nbsp;</td></tr>
            </table> 
            <br>
               <a  class="button" href="javascript:submitform2()">
                   <img style="top: -4px;" src="../images/button.png" alt="">Hiển thị khách hàng</a>     
        </div> 
        <hr>
        <div style="width:100%; float:none; clear:left" align="left">
             <table class="tabledetail" style="width:100%;" cellpadding="1px" cellspacing="1px">
             	<tr class="tbheader">
                	<th width="10%" align="center">Mã KH</th>
                    <th width="20%" align="left">Họ tên</th>
                    <th width="25%" align="left">Địa chỉ</th>
                    <th width="10%" align="center">Điện thoại</th>
                    <th width="10%" align="center">Đạt</th>
                    <th width="10%" align="center">Số suất ĐK</th>
                  </tr>
                <% int m=0; %>
                <% if(khList != null)
                {
				  	try
				  		{ 
				  		while(khList.next())
				  		{ 
				  		%>
	    					 <tr class="tbdarkrow">
			                	<td align="center">
			                		<%= khList.getString("smartId") %>
			                	<input type="hidden" name="khIds" value="<%= khList.getString("khId") %>" >
			                	</td>
			                    <td align="left"><%= khList.getString("khTen") %></td>
			                    <td align="left"><%= khList.getString("diachi") %></td>
			                    <td align="center"><%= khList.getString("dienthoai") %></td>
			                    <td align="center">
			                    	<input type="text" name="soxuat" value="<%= khList.getString("dat") %>" style="text-align:right" size="6" readonly></td>
			                    <td align="center">
			                    <% if(khList.getString("dat").length() > 0) { if(khList.getInt("dat") <= 0 ) { %>
			                    	 <% if( khachhang_dangky.contains(khList.getString("khId") ) ) {  %>
			                    	<input type="text" name="dangky" id="<%= "dangky" + Integer.toString(m) %>" value="<%= khachhang_dangky.get(khList.getString("khId")) %>" style="text-align:right" size="6" 
			                    			onkeypress="return keypress(event);" onkeyup="Check('<%= "dangky" + Integer.toString(m) %>')" >
				                    <% } else { %>
				                    	<input type="text" name="dangky" id="<%= "dangky" + Integer.toString(m) %>" value="<%= khList.getString("dangky") %>" style="text-align:right" size="6" 
				                    			onkeypress="return keypress(event);" onkeyup="Check('<%= "dangky" + Integer.toString(m) %>')" >
				                    <% } %>
			                    <% }  else { %>
			                    		<input type="text" name="dangky" id="<%= "dangky" + Integer.toString(m) %>" value="<%= khList.getString("dangky") %>" style="text-align:right" size="6"  >
			                    <% } } else { %> 
			                    		<% if( khachhang_dangky.contains(khList.getString("khId") ) ) {  %>
			                    	<input type="text" name="dangky" id="<%= "dangky" + Integer.toString(m) %>" value="<%= khachhang_dangky.get(khList.getString("khId")) %>" style="text-align:right" size="6" 
			                    			onkeypress="return keypress(event);" onkeyup="Check('<%= "dangky" + Integer.toString(m) %>')" >
				                    <% } else { %>
				                    	<input type="text" name="dangky" id="<%= "dangky" + Integer.toString(m) %>" value="<%= khList.getString("dangky") %>" style="text-align:right" size="6" 
				                    			onkeypress="return keypress(event);" onkeyup="Check('<%= "dangky" + Integer.toString(m) %>')" >
				                    <% } %>
			                    <%} %>
			                   
			                   	</td>
			                   	
			                </tr>
	     			<% } khList.close(); } catch(Exception e){} }%>               
                <tr class="tbfooter"><td colspan="6">&nbsp;</td></tr>
             </table>
        </div>  
    </fieldset>
    </div>   
</div>
</form>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</html>
<% 	
	try
	{
		if(nvbh!=null)
		nvbh.close();
		if(cttrungbay!=null)
		cttrungbay.close();
		if(khList!=null)
		khList.close();
		if(dktbBean!=null){
		dktbBean.DBclose();
		dktbBean = null;
		}
		if(nvbhIds!=null){
			nvbhIds.clear();
		}
		session.setAttribute("dktbBean",null);
	}
	catch (SQLException e) {}

%>
<%}%>


    