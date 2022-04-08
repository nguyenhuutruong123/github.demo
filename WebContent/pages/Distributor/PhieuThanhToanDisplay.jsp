<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.distributor.beans.phieuthanhtoan.*" %>
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

<% IPhieuthanhtoan obj = (IPhieuthanhtoan)session.getAttribute("obj"); %>
<% ResultSet ddkd = (ResultSet)obj.getDdkd();%>
<% ResultSet dshd = (ResultSet)obj.getDshd();%>
<% ResultSet tuyens = (ResultSet)obj.getTuyens();%>


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
   
   	<style type="text/css">
	#mainContainer{
		width:660px;
		margin:0 auto;
		text-align:left;
		height:100%;
		border-left:3px double #000;
		border-right:3px double #000;
	}
	#formContent{
		padding:5px;
	}
	/* END CSS ONLY NEEDED IN DEMO */
		
	/* Big box with list of options */
	#ajax_listOfOptions{
		position:absolute;	/* Never change this one */
		width:auto;	/* Width of box */
		height:200px;	/* Height of box */
		overflow:auto;	/* Scrolling features */
		border:1px solid #317082;	/* Dark green border */
		background-color:#C5E8CD;	/* White background color */
    	color: black;
		text-align:left;
		font-size:1.0em;
		z-index:100;
	}
	#ajax_listOfOptions div{	/* General rule for both .optionDiv and .optionDivSelected */
		margin:1px;		
		padding:1px;
		cursor:pointer;
		font-size:1.0em;
	}
	#ajax_listOfOptions .optionDiv{	/* Div for each item in list */
		
	}
	#ajax_listOfOptions .optionDivSelected{ /* Selected item in the list */
		background-color:#317082; /*mau khi di chuyen */
		color:#FFF;
	}
	#ajax_listOfOptions_iframe{
		background-color:#F00;
		position:absolute;
		z-index:5;
	}
	form{
		display:inline;
	}
	</style>
	<script type="text/javascript" src="../scripts/ajax.js"></script>
	<script type="text/javascript" src="../scripts/ajax-dynamic-list-kh.js"></script>
   	  	
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
    <script language="javascript" type="text/javascript">
    
    
  /*  function Tinhsodu()
	{
		var sodu = document.getElementById("sodudau");
		var pttIds = document.getElementsByName("pttIds");
		var sum = 0;
		for(i = 0; i < pttIds.length; i++)
		{
			if(pttIds.item(i).checked)
			{
				var pos = pttIds.item(i).value.indexOf('-');
				var sotien = (pttIds.item(i).value).substring(parseInt(pos) + 1, pttIds.item(i).value.length);
				sum = parseInt(sum) + parseInt(sotien);
			}
		}
		sodu.value = sum;
	}

	function Tinhtien(str)
	{
		var sodu = document.getElementById("sodudau");
		var tongthanhtoan = document.getElementById("thanhtoan");
		var tongconlai = document.getElementById("conlai");
		
		var checkId = document.getElementById(str);
		var thanhtoan = document.getElementById(str + '.thanhtoan');
		var conlai = document.getElementById(str + '.conlai');
		
		var sd = 0;
		if(parseInt(sodu.value) > 0)
			sd = parseInt(sodu.value);
		if(parseInt(sd) <= 0)
		{
			alert('Vui lòng nhập số dư đầu...');
			checkId.checked = false;
			return;
		}
		var sum = 0;
		if( parseInt(tongthanhtoan.value) > 0)
			sum = parseInt(tongthanhtoan.value);
		//var pos = checkId.value.indexOf('-');
		//var sotien = (checkId.value).substring(parseInt(pos) + 1, checkId.value.length);
		var sotien = document.getElementById(str + '.tienconno').value;
		if(checkId.checked)	
		{
			sum = parseInt(sum) + parseInt(sotien);
			thanhtoan.value = parseInt(sotien);
		}
		else
		{
			sum = parseInt(sum) - parseInt(sotien);
			thanhtoan.value = "";
		}
		if(parseInt(sum) > parseInt(sodu.value))
		{
			alert('Không đủ số dư để tiếp tục thanh toán....');
			checkId.checked = false;
			thanhtoan.value =  "";
			return;
		}
		
		tongthanhtoan.value = sum;
		tongconlai.value = parseInt(sodu.value) - parseInt(sum);
		//conlai.value = '0';
		var tienconno = document.getElementById(str + '.tienconno');
		var tientt = 0;
		if(parseFloat(thanhtoan.value) > 0)
			tientt = parseFloat(thanhtoan.value);
		conlai.value = parseFloat(tienconno.value) - parseFloat(tientt);
	}
	
	function Tinhtien2(str)
	{
		var sodu = document.getElementById("sodudau");			
		var tongthanhtoan = document.getElementById("thanhtoan");
		var tongconlai = document.getElementById("conlai");
		
		var thanhtoan = document.getElementById(str);
		
		var str = str.substr(0, str.indexOf('.'));
		var checkId = document.getElementById(str);
		var tienconno = document.getElementById(str + '.tienconno');
		var conlai = document.getElementById(str + '.conlai');

		var sum = 0;
		var oldSum = 0;
		if(parseFloat(thanhtoan.value) > 0)
		{
			if(thanhtoan.value.length == 1)
				oldSum = thanhtoan.value;
			else
			{
				if(thanhtoan.value.length > 1)
					oldSum = thanhtoan.value.substring(0, thanhtoan.value.length - 1);
			}
			sum = thanhtoan.value;
			//alert(oldSum + ' --- ' + sum);
		}
			
		if( parseFloat(sum) > parseFloat(tienconno.value))
		{
			alert('Số tiền thanh toán,Vượt qua số tiền nợ,Vui lòng nhập số nhỏ hơn...');
			thanhtoan.value = "";
			conlai.value = parseFloat(tienconno.value);
			Tinhtonggiatri();
			return;
		}
		if(parseFloat(tongthanhtoan.value) - parseFloat(oldSum) + parseFloat(sum) >  parseFloat(sodu.value))
		{
			alert('Không đủ số dư để tiếp tục thanh toán....');
			thanhtoan.value = "";
			conlai.value = parseFloat(tienconno.value);
			Tinhtonggiatri();
			return;
		}
		var tientt = 0;
		if(parseFloat(thanhtoan.value) > 0)
			tientt = parseFloat(thanhtoan.value);
		conlai.value = parseFloat(tienconno.value) - parseFloat(tientt);
		Tinhtonggiatri();
	}
	
	function Tinhtonggiatri()
	{
		var sodu = document.getElementById("sodudau");			
		var tongthanhtoan = document.getElementById("thanhtoan");						
		var tongconlai = document.getElementById("conlai");
						
		var tienthanhtoan = document.getElementsByName("tienthanhtoan");
		var sum = 0;
		for(i=0; i < tienthanhtoan.length; i++)
		{
			if(parseFloat(tienthanhtoan.item(i).value) > 0)
				sum = parseFloat(sum) + parseFloat(tienthanhtoan.item(i).value);
		}
		tongthanhtoan.value = sum;
		tongconlai.value = parseFloat(sodu.value) - parseFloat(sum);
	}
	
    function saveform()
	{  
    	var kh = document.getElementById("khTen");
		var sotien = document.getElementById("sotien");
		if(kh.value == "")
		{
			alert('Vui lòng nhập thông tin khách hàng...');
			retrun;
		}
		if(kh.value.indexOf(" -- ") <= 0)
		{
			alert('Khách hàng không hợp lệ,Bạn phải chọn khách hàng trong danh sách khách hàng...');
			retrun;
		}
		if(sotien == "")
		{
			alert('Vui lòng nhập số tiền...');
			retrun;
		}
		if(checkKhachhangTT() == false)
		{
			alert('Lỗi...Không có khách hàng nào được chọn thanh toán..');
			retrun;
		}
	
	    document.forms['pttForm'].action.value='save';
	    document.forms['pttForm'].submit();
	}
    
	function submitform()
	{	
		document.forms['pttForm'].action.value='submit';
	    document.forms['pttForm'].submit();
	}
	
	function checkKhachhangTT()
	{
		var khIds = document.getElementsByName("pttIds");
		for(i = 0; i < khIds.length; i++)
		{
			if(khIds.item(i).checked == true)
				return true;
		}
		return false;
	}
	function checkDonhangTT()
	{
		var dhIds = document.getElementsByName("dhIds");
		for(i = 0; i < dhIds.length; i++)
		{
			if(dhIds.item(i).checked == true)
				return true;
		}
		return false;
	}
	*/
	
	function keypress(e) //Hàm dùng d? ngan ngu?i dùng nh?p các ký t? khác ký t? s? vào TextBox
	{    
		var keypressed = null;
		if (window.event)
			keypressed = window.event.keyCode; //IE
		else
			keypressed = e.which; //NON-IE, Standard
		
		if (keypressed < 48 || keypressed > 57)
		{ 
			if (keypressed == 8 || keypressed == 127 || keypressed == 37 || keypressed == 39)
			{//Phím Delete và Phím Back
				return;
			}
			return false;
		}
	}
	
	
	function checkDonhangTT()
	{
		var trahet = document.getElementsByName("trahet1");
		var conlai = document.getElementsByName("conlai1");
		var thanhtoan = document.getElementsByName("thanhtoan1");
		var tonggiatri = document.getElementsByName("tonggiatri1");
		  var datra = document.getElementsByName("dathanhtoan1");
		 
		for(i = 0; i < conlai.length; i++)
		{
			if(trahet.item(i).checked == true)
			{
				 
				thanhtoan.item(i).value = parseFloat(tonggiatri.item(i).value) - parseFloat(datra.item(i).value);
				conlai.item(i).value = 0;
				trahet.item(i).checked == false;
			//	trahet[i].checked == false;
					
			}
			
				
		}
		tinh();
		//return false;
	}
	function tinh()
	{
	  var trahet = document.getElementsByName("trahet1");
	  var tonggiatri = document.getElementsByName("tonggiatri1");
	  var datra = document.getElementsByName("dathanhtoan1");
	  var thanhtoan = document.getElementsByName("thanhtoan1");
	  var conlai = document.getElementsByName("conlai1");
	  var tongthanhtoan = document.getElementById("thanhtoan");
	
	  var sotien = document.getElementById("sotien").value;
	  var tongconlai = document.getElementById("conlai");
	//  alert(sotien);   
	  var  tong =0;
	  var con =0;
	 
	  for(i = 0; i < tonggiatri.length; i++)
	  {
	     // con = parseFloat(tonggiatri.item(i+1).value) -  parseFloat(datra.item(i+1).value) - parseFloat(thanhtoan.item(i+1).value);
	      //alert(con);
	      var h = parseFloat(tonggiatri.item(i).value)-parseFloat(thanhtoan.item(i).value)-parseFloat(datra.item(i).value) ;
		   conlai.item(i).value = h;// - parseFloat(datra.item(i+1).value);
		   con = con + parseFloat(conlai.item(i).value);
		   tong  = tong + parseFloat(thanhtoan.item(i).value);
		   if(h < 0)
			{
			 alert('Ban da nhap qua so tien roi');   
			 thanhtoan.item(i).value = 0;
			 conlai.item(i).value =parseFloat(tonggiatri.item(i).value) - parseFloat(datra.item(i).value) ;
			 trahet.item(i).checked == false;
			 return false;
			}
		   if(tong > parseFloat(sotien))
			   {
			    alert('Ban da nhap qua tong so tien:'+ sotien );   
				 thanhtoan.item(i).value = 0;
				 conlai.item(i).value =parseFloat(tonggiatri.item(i).value) - parseFloat(datra.item(i).value) ;
				 trahet.item(i).checked == false;
				 return false;
			   }
		  }
	  tongthanhtoan.value = tong;
	  tongconlai.value = con;
	  return true;
	}
	function kiemtra()
	{  var tong = 0;
	   var thanhtoan = document.getElementsByName("thanhtoan1");
	   var sotien = document.getElementById("sotien").value;
	   for(i = 0; i < thanhtoan.length; i++)
	   { tong = tong + parseFloat(thanhtoan.item(i).value);
	   }
		if(sotien != tong)
			{
			alert('ban chua thanh toan het tong so tien cho cac don hang');
			return false;
			}
		return true;
	}
	function saveform()
	{   if(kiemtra())
		{
		if (tinh())
		{
			var ngaythanhtoan = document.getElementById("ngaythanhtoan").value;
			var diengiai = document.getElementById("diengiai").value;
			var sotien = document.getElementById("sotien").value;
			
			var h = parseFloat(sotien);
			if(h<= 0 | ngaythanhtoan.length <=0 || diengiai.length <=0 )
			 { 
				 alert('ban phai nhap du thong tin');
				 return;
			 }
			document.forms['pttForm'].action.value= 'save';
			document.forms['pttForm'].submit();
		}
	 else
		alert('ban phai thanh toan cho cac don hang');
		}
	}
	function thuchien()
	{
		document.forms['pttForm'].submit();
		
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

<form name="pttForm" method="post" action="../../PhieuthanhtoanUpdateSvl"> 
 <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<INPUT name="userId" type="hidden" value='<%=userId %>' size="30">
<input type="hidden" name="nppId" value='<%=obj.getNppId() %>'>
<input type="hidden" name="id" value='<%=obj.getId() %>'>
<input type="hidden" name="action" value='1'>
<div id="main" style="width:99%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:70%; padding:5px; float:left" class="tbnavigation">
        	Quản lý công nợ > Phiếu thanh toán> hiển thị
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng bạn   <%=obj.getNppTen() %> &nbsp;
        </div>
    </div>
  
  	<div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="tbdarkrow">
    	<A href= "javascript:history.back()" >
        	<img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1px" longdesc="Quay ve" style="border-style:outset"></A>
       </div>
  	<div align="left" style="width:70%; float: none"> 
    	<fieldset>
        	<legend class="legendtitle">Báo lỗi nhập liệu </legend>
            <textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold" cols="90" rows="1" readonly="readonly"><%=obj.getMsg() %></textarea>
           
        </fieldset>
    </div>
    <div align="left" style="width:70%; float:none">
    <fieldset>
    	<legend class="legendtitle">Phiếu thu tiền</legend>
        <div style=" width:100%; float:non; clear:left; font-size:0.7em">
        <TABLE width="100%" cellpadding="5px" cellspacing="0px">
             <TR>
                <TD class="plainlabel"  width="20%">Ngày thanh toán</TD>
                <TD class="plainlabel" colspan="2">
                    <input type="text" size="10" class="w8em format-d-m-y divider-dash highlight-days-12" 
                           id="ngaythanhtoan" name="ngaythanhtoan" id = "ngaythanhtoan" value="<%=obj.getNgaythanhtoan() %>" maxlength="10" />
                </TD>
            </TR>		
             			
            <TR>
                <TD class="plainlabel">Nhân viên giao nhận</TD>
                <TD class="plainlabel" colspan="2">
                    <select name="ddkdId" id="ddkdId" onchange="submitform()">
                        <option value="">&nbsp;</option>
                            <% if(ddkd != null){
					  		try{ while(ddkd.next()){ 
		    					if(ddkd.getString("pk_seq").equals(obj.getddkdId())){ %>
		      					<option value='<%=ddkd.getString("pk_seq")%>' selected><%=ddkd.getString("ten") %></option>
		      				<%}else{ %>
		     					<option value='<%=ddkd.getString("pk_seq")%>'><%=ddkd.getString("ten") %></option>
		     			<%}} ddkd.close(); }catch(java.sql.SQLException e){} }%>
                     </select>
                </TD>
            </TR> 
            <TR>
                <TD class="plainlabel">Diễn giải </TD>
                <TD class="plainlabel" colspan="2"><input type="text" name="diengiai" id ="diengiai" size="50" value ='<%= obj.getDiengiai() %>'> </TD>
            </TR> 
       
            <TR>
                        <TD class="plainlabel" >Hình thức </TD>
                        <TD class="plainlabel" colspan="2">
                        <%if(obj.getHinhthuc().equals("0")) {%>
                            <input type="radio" name="hinhthuc" value="0" checked>Tiền mặt &nbsp;
                            <input type="radio" name="hinhthuc" value="1" >Chuyển kho &nbsp;
                        <%} else { %>
                            <input type="radio" name="hinhthuc" value="0">Tiền mặt &nbsp;
                            <input type="radio" name="hinhthuc" value="1" checked >Chuyển kho &nbsp;
                        <% }%>
                        </TD>
             </TR>
              <TR>
                        <TD class="plainlabel" >Số tiền </TD>
                        <TD class="plainlabel" colspan="2"><input type="text" name="sotien" size="10" id="sotien" value="<%= obj.getSotien() %>" readonly  style="text-align:right"/> VND</TD>                        
               </TR>     
            
            <TR>
                <TD class="plainlabel" valign="middle">Thanh toán </TD>
                <TD class="plainlabel" valign="middle">
                    <input type="text" name="thanhtoan" id="thanhtoan" value="" readonly style="text-align:right"/> VND
                </TD>  
                <TD class="plainlabel" valign="middle">Còn lại 
                	<input type="text" name="conlai"  id="conlai" value="" readonly style="text-align:right"/> VND
                </TD>                 
            </TR>
                       
                <tr >
                        <td  class="plainlabel" colspan="2">
                            <a class="button" href="javascript:thuchien();">
                                <img style="top: -4px;" src="../images/button.png" alt="">Thực hiện </a>&nbsp;&nbsp;&nbsp;&nbsp;
                         </td>
                         <TD class="plainlabel" colspan="2">
                         
                         </TD>
                    </tr>    					
        </TABLE>
       
        <hr>
            <div style="width:100%; float:none" align="left">
            <table style="width:100%;" cellpadding="3px" cellspacing="1px">
                <tr class="plainlabel">
                    <th align="center">Mã KH</th>
                    <th align="center">Tên</th>
                    <th align="center">Đơn hàng</th>
                    <th align="center">Ngày ĐH</th>
                    <th align="center">Tiền ĐH</th>
                    <th align="center">da thanh toan</th>
                    <th align="center">Thanh toán</th>
                    <th align="center">Còn lại</th>
                </tr>
                <% int m = 0;
                  while(dshd.next()) {
                    %>
                    <% if(!dshd.getString("thanhtoan").equals("0")) {%>
					
                	 <% if (m % 2 != 0) {%>						
					<TR class= "tblightrow">
					  <%} else {%>
				   <TR class= "tbdarkrow">
					<%}%>
					<TD align="center"><%=dshd.getString("khId") %></TD>
					<TD align="center"><%=dshd.getString("ten") %></TD>
					<TD align="center"><input type ="text" name ="dhid" readonly  size="10" value ='<%=dshd.getString("dhId")%>' readonly ></TD>
					<TD align="center"><%=dshd.getString("ngaynhap") %></TD>
					<TD align="center"><input type ="text" size="10" name ="tonggiatri1" readonly value ='<%=dshd.getString("tonggiatri") %>'></TD>
					<TD align="center"><input type ="text" size="10" name ="dathanhtoan1" readonly value ='<%=dshd.getString("dathanhtoan") %>'></TD>
				    <TD align="center"><input type ="text" size="10" name ="thanhtoan1" readonly  value="<%=dshd.getString("thanhtoan") %>"></TD>
					 <TD align="center"><input type ="text" size="10" name ="conlai1" readonly value ='<%=dshd.getString("conlai") %>'></TD>
                    </TR>
                  <% m++;}}
                   dshd.close();%>
                 <tr class="plainlabel">
                	<td colspan="9">&nbsp;</td>
                </tr>
                  <script language="javascript" type="text/javascript">
                  tinh();
              	</script>
            </table>
        </div>            
    </fieldset>
    </div>  
</div>
</form>
</BODY>
</html>

<% 	
	
	try{
		
		if(ddkd != null)
			ddkd.close();
		if(dshd != null)
			dshd.close();
	
	
	}
	catch (SQLException e) {}

%>
<%}%>