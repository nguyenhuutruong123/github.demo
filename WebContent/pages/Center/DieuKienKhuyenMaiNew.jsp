<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.beans.dieukienkhuyenmai.*" %>
<%@ page  import = "geso.dms.center.beans.dieukienkhuyenmai.imp.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%@ page  import = "geso.dms.center.util.ChuyenNgu" %>
<% IDieukienkhuyenmai dkkmBean = (IDieukienkhuyenmai)session.getAttribute("dkkmBean"); %>
<% ResultSet nhomsp = dkkmBean.getNhomspRs(); %>
<% Hashtable<String, Integer> sp_nhomSpIds = dkkmBean.getSp_nhomspIds(); %>
<% List<ISanpham> spList = dkkmBean.getSpList(); %>
<% List<ISanpham> spSudungList = dkkmBean.getSpSudungList(); 
String sum = (String) session.getAttribute("sum");%>

<% 
String userId = (String) session.getAttribute("userId");  
String userTen = (String) session.getAttribute("userTen");  
Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/AHF/index.jsp");
	}else{ %>
<% String nnId = (String)session.getAttribute("nnId"); %>
<% 
if(nnId == null) {
 	nnId = "vi";  
}
String url = util.getChuyenNguUrl("DieukienkhuyenmaiSvl","",nnId);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<TITLE>Best - Project</TITLE>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">

	<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
	<LINK rel="stylesheet" href="../css/main.css" type="text/css">
   	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css"/>
  	<script type="text/javascript" src="../scripts/jquery.min.js"></script>
    <script type="text/javascript" src="..scripts/jquery-1.js"></script>
    <link type="text/css" rel="stylesheet" href="../css/mybutton.css">
    
    <LINK rel="stylesheet" type="text/css" href="../css/style.css" />
	<style type="text/css">
		#mainContainer{
			width:600px;
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
		#dhtmltooltip
		{
			position: absolute;
			left: -300px;
			width: 150px;
			border: 1px solid black;
			padding: 2px;
			background-color: lightyellow;
			visibility: hidden;
			z-index: 100;
			/*Remove below line to remove shadow. Below line should always appear last within this CSS*/
			filter: progid:DXImageTransform.Microsoft.Shadow(color=gray,direction=135);
		}	
		#dhtmlpointer
		{
			position:absolute;
			left: -300px;
			z-index: 101;
			visibility: hidden;
		}	
	</style>
	<script type="text/javascript" src="../scripts/ajax.js"></script>
	<script type="text/javascript" src="../scripts/dkkhuyenmai_sanpham.js"></script>
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
    
	<script language="javascript" type="text/javascript">
	function replaces()
	{
		var masp = document.getElementsByName("masp");
		var tensp = document.getElementsByName("tensp");
		var type = document.getElementById("type");

		var i;
		for(i=0; i < masp.length; i++)
		{
			if(masp.item(i).value != "")
			{
				var sp = masp.item(i).value;
				var pos = parseInt(sp.indexOf(" - "));
				if(pos > 0)
				{
					masp.item(i).value = sp.substring(0, pos);
					tensp.item(i).value = sp.substr(parseInt(sp.indexOf(" - ")) + 3);					
				}
			}
			else
			{
				tensp.item(i).value = "";
				if(type.value == "1")
				{
					var soluong = document.getElementsByName("soluong");
					soluong.item(i).value = "";
				}
			}			
		}
		setTimeout(replaces, 20);
	}	
	replaces();
	
	function keypress(e)
	{    
		var keypressed = null;
		if (window.event)
			keypressed = window.event.keyCode; //IE
		else
			keypressed = e.which; //NON-IE, Standard
		
		if (keypressed < 48 || keypressed > 57)
		{ 
			if (keypressed == 8 || keypressed == 127 || keypressed == 37 || keypressed == 39)
			{
				return;
			}
			return false;
		}
	}
	
	function submitform()
	{   
	   document.forms["dkkmForm"].submit();
	}
	
	function saveform()
	{
		var type = document.getElementById("type").value;
		var tonglg = document.getElementById("tonglg");
		var tongtn = document.getElementById("tongtn");
		if(type == 2)
		{
			if(tonglg.checked)
			{
				var tongluong = document.getElementById("tongluong").value;			
				if(tongluong == "" )
				{
					alert('Bạn phải nhập tổng lượng của điều kiện khuyễn mãi');
					return;
				}
				var tongluong = document.getElementById("tongluong").value;			
				if(tongluong == "0" )
				{
					alert('Bạn phải nhập tổng lượng của điều kiện khuyễn mãi lớn hơn 0');
					return;
				}
			}
			if(tongtn.checked)
			{
				var tongtien = document.getElementById("tongtien").value;			
				if(tongtien == "")
				{
					alert('Phải nhập tổng tiền của điều kiện khuyễn mãi');
					return;
				}
			}
		}
		
		if(checkSanphamNhap() == false && type != 3)
		{
			alert('Không có sản phẩm nào được nhập cho điều kiện khuyễn mãi');
			return;
		}
		
		if(checkTrungmasp() == false)
		{
			alert('Kiểm tra lại các sản phẩm trả trưng bày mã Kiem tra lai cac san pham tra trung bay trung ma...');
			return;
		}
		
		if(type == 1) //nhap soluong tung san pham
		{
			if(checkSanpham() == false)
			{
				alert('Kiểm tra lại số lượng sản phẩm');
				return;
			}
		}
		
		document.forms["dkkmForm"].action.value = "save";
		document.forms["dkkmForm"].submit();		
	}
	
	function checkSanpham()
	{	
		var masp = document.getElementsByName("masp");
		var soluong = document.getElementsByName("soluong");
		for(k=0; k < masp.length; k++)
		{
			if(masp.item(k).value != "")
			{
				if(soluong.item(k).value == "" || soluong.item(k).value == "0")
					return false;
			}	
		}
		return true;
	}
	
	function checkTrungmasp()
	{
		var masp = document.getElementsByName("masp");
		for(l = 0; l < (masp.length - 1) ; l++)
		{
			for(m = (parseInt(l) + 1); m < masp.length; m++)
			{
				if(masp.item(l).value != "" && masp.item(m).value != "")
				{
					if(masp.item(l).value == masp.item(m).value)
						return false;
				}
			}
		}
		return true;
	}
	
	function checkSanphamNhap()
	{
		var masp = document.getElementsByName("masp");
		var soluong = document.getElementsByName("soluong");
		for(h=0; h < masp.length; h++)
		{
			if(masp.item(h).value != "")
			{
				return true;
			}	
		}
		return false;
	}
	
	function clearform()
	{
		var masp = document.getElementsByName("masp");
		for(h = 0; h < masp.length; h++)
		{
			masp.item(h).value = "";
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

<form name="dkkmForm" method="post" action="../../DkkhuyenmaiUpdateSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="action" value='1'>
<div id="main" style="width:99%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:60%; padding:5px; float:left" class="tbnavigation">
        	<%=url %>
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	<%=ChuyenNgu.get("Chào mừng",nnId) %> <%= userTen %> &nbsp;
        </div>
    </div>
  
  	<div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="tbdarkrow">
    	<A href= "javascript:history.back()" >
        	<img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1px" longdesc="Quay ve" style="border-style:outset"></A>
        <A href="javascript:saveform()" >
        	<IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border ="1px" style="border-style:outset"></A>
    </div>
  	
  	<div align="left" style="width:100%%; float:none; clear:left">
  		<fieldset>
    		<legend class="legendtitle"> <%=ChuyenNgu.get("Báo lỗi nhập liệu",nnId) %></legend>
    		<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold"  rows="1" readonly="readonly" style ="width:100%%"><%= dkkmBean.getMessage() %></textarea>
		         <% dkkmBean.setMessage(""); %>
    	</fieldset>
  	</div>
  	
    <div align="left" style="width:100%; float:none; clear:left">
    <fieldset>
    	<legend class="legendtitle"> <%=ChuyenNgu.get("Điều kiện khuyến mãi",nnId) %></legend>
        	<div style="float:none; width:100%" align="left">
            <TABLE width="100%" cellpadding="6" cellspacing="0">							
                <TR>
                    <TD width="15%" class="plainlabel" valign="top"><%=ChuyenNgu.get("Diễn giải",nnId) %> </TD>
                    <TD class="plainlabel" valign="top"><textarea name="diengiai" style="width:500px" rows="1"><%= dkkmBean.getDiengiai() %></textarea></TD>
                </TR> 
                
                <TR>
                    <TD class="plainlabel"><%=ChuyenNgu.get("Loại điều kiện",nnId) %></TD>
                    <TD class="plainlabel">
                        <select name="type" id="type" onChange="submitform();">
                        <% if(dkkmBean.getType().equals("1")){ %>                           
                            <option value="1" selected><%=ChuyenNgu.get("Bắt buộc nhập số lượng từng sản phẩm",nnId) %></option>
                            <option value="2"><%=ChuyenNgu.get("Bất kỳ trong",nnId) %></option>
                            <option value=""> </option>
                        <%}else if(dkkmBean.getType().equals("2")){ %>                      	
                            <option value="2" selected><%=ChuyenNgu.get("Bất kỳ trong",nnId) %></option>
                            <option value="1"><%=ChuyenNgu.get("Bắt buộc nhập số lượng từng sản phẩm",nnId) %></option>
                            <option value=""> </option>
                        <%}else{ %>
                        	<option value="" selected> </option>                       	
                            <option value="1"><%=ChuyenNgu.get("Bắt buộc nhập số lượng từng sản phẩm",nnId) %></option>      
                            <option value="2"><%=ChuyenNgu.get("Bất kỳ trong",nnId) %></option>          
                        <% } %>
                        </select>
                     </TD> 
                </TR>
          
                
          <%if(!dkkmBean.getType().equals("3")){ %>   
                <%if(!dkkmBean.getType().equals("1")){ %>  
                <TR>
                    <TD width="15%" class="plainlabel" valign="top"><%=ChuyenNgu.get("Hình thức",nnId) %></TD>
                    <TD class="plainlabel" valign="middle">  
                    <% if(dkkmBean.getHinhthuc().equals("2")){ %>              	
                        <input type="radio" name="option" value="1" id="tonglg" onChange="submitform();" ><%=ChuyenNgu.get("Nhập tổng lượng",nnId) %> &nbsp;
                        <input type="radio" name="option" value="2" id="tongtn" onChange="submitform();" checked><%=ChuyenNgu.get("Nhập tổng tiền",nnId) %> &nbsp; 
                    <%} else{ %>
                    	<input type="radio" name="option" value="1" id="tonglg" onChange="submitform();" checked><%=ChuyenNgu.get("Nhập tổng lượng",nnId) %> &nbsp;
                        <input type="radio" name="option" value="2" id="tongtn" onChange="submitform();" ><%=ChuyenNgu.get("Nhập tổng tiền",nnId) %> &nbsp; 
                    <%} %>                    
                    </TD>
                </TR>
                
                <% if(dkkmBean.getHinhthuc().equals("2")){ %>              
	               <TR>
	                    <TD class="plainlabel"><%=ChuyenNgu.get("Tổng tiền",nnId) %> </TD>
	                    <% if(dkkmBean.getType().equals("1")){ %>
	                    	<TD class="plainlabel"><input type="text" name="tongtien" id="tongtien" value="<%= dkkmBean.getTongtien() %>" style="text-align:right" size="15" readonly> VND</TD> 
	                    <%}else{ %>
	                    	<TD class="plainlabel"><input type="text" name="tongtien" id="tongtien" value="<%= dkkmBean.getTongtien() %>" style="text-align:right" size="15" onkeypress="return keypress(event);"> VND</TD> 
	                    <%} %>
	                </TR>
                <%}else{ %>
	                <TR>
	                    <TD class="plainlabel"><%=ChuyenNgu.get("Tổng lượng",nnId) %> </TD>
	                    <% if(dkkmBean.getType().equals("1")){ %>
	                    	<TD class="plainlabel"><input type="number" name="tongluong" id="tongluong" value="<%= dkkmBean.getTongluong() %>" style="text-align:right" size="15" readonly></TD> 
	                    <%}else{ %>
	                    	<TD class="plainlabel"><input type="number" name="tongluong" id="tongluong" value="<%= dkkmBean.getTongluong() %>" style="text-align:right" size="15" onkeypress="return keypress(event);"></TD> 
	                    <%} %>
	                </TR> 
                <%} %> 
                
                <%} %> 
                <TR>
                    <TD class="plainlabel"><%=ChuyenNgu.get("Nhóm sản phẩm",nnId) %></TD>
                    <TD class="plainlabel">
                        <select name="nhomsp" id="nhomsp" onChange = "submitform();">
                            <option value="" selected></option>
                            <% if(nhomsp != null){
								  try{ while(nhomsp.next()){ 
					    			if(nhomsp.getString("nspId").equals(dkkmBean.getNhomspId())){ %>
					      				<option value='<%= nhomsp.getString("nspId")%>' selected><%= nhomsp.getString("nspTen") %></option>
					      			<%}else{ %>
					     				<option value='<%= nhomsp.getString("nspId")%>'><%= nhomsp.getString("nspTen") %></option>
					     	<%}} nhomsp.close(); }catch(java.sql.SQLException e){} }%>
                        </select>
                     </TD> 
                </TR>  						
            </TABLE>
            <hr> 
            </div>
           
           	<div align="left" style="width:100%; float:none; clear:none;">
           <TABLE class="tabledetail" width="100%" border="0" cellpadding="1" cellspacing="1" >
                <TR class="tbheader">                   
                 <% if(dkkmBean.getType().equals("1")){ %>
                	<TH align="center" width="15%"><%=ChuyenNgu.get("Mã sản phẩm ",nnId) %></TH>
                	<TH align="left" width="73%"><%=ChuyenNgu.get("Tên sản phẩm",nnId) %></TH>
               	 	<TH align="center" width="10%"><%=ChuyenNgu.get("Số lượng",nnId) %></TH>
                 <%}else{ %>
                 	<TH align="center" width="15%"><%=ChuyenNgu.get("Mã sản phẩm",nnId) %></TH>
                	<TH align="left" width="73%"><%=ChuyenNgu.get("Tên sản phẩm",nnId) %></TH>
               	 	<TH align="center" width="10%"><%=ChuyenNgu.get("Số lượng",nnId) %></TH>
               
                 <%} %>
                </TR>
                <% if(dkkmBean.getNhomspId().length() > 0)
                {
                	for( int i =0; i < spList.size(); i++)
                	{
                		ISanpham sp = spList.get(i);
                	%>
                		<TR class='tbdarkrow'>
		                    	<td align="center"><input type="text"  value="<%= sp.getMasanpham() %>" name = "masp"  onkeyup="ajax_showOptions(this,'abc',event)"  AUTOCOMPLETE="off"></td>
		                   		<td align="left"><input type="text"  value="<%= sp.getTensanpham() %>" name = "tensp" readonly ></td>
		                   		<% if(dkkmBean.getType().equals("1")){ %>
		                    		<td align="center"><input type="number" name="soluong"  value="<%= sp.getSoluong() %>"  style="text-align:right;" onkeypress="return keypress(event);"></td>
		                    	<%}else{ %>
		                    		<td align="center""><input type="number" name="soluong"  value="<%= sp.getSoluong() %>"  style="text-align:right;" onkeypress="return keypress(event);"></td>
		                    	<%} %>
	                    	</TR>
                	<%}}
                else { if(dkkmBean.getType().length() > 0){
               			for(int i = 0; i < spSudungList.size(); i++)
               			{
               				Sanpham sp = (Sanpham)spSudungList.get(i);  %>
               				<TR class='tbdarkrow'>
	                    	<td align="center"><input type="text"  value="<%= sp.getMasanpham() %>" name = "masp" onkeyup="ajax_showOptions(this,'abc',event)"  AUTOCOMPLETE="off"></td>
	                   		<td align="left"><input type="text"  value="<%= sp.getTensanpham() %>" name= "tensp" readonly></td>
	                   		<% if(dkkmBean.getType().equals("1")){ %>
	                    		<td align="center"><input type="number" name="soluong"  style="text-align:right;" onkeypress="return keypress(event);"></td>
	                    	<%} else{ %>
	                    		<td align="center""><input type="number" name="soluong"  style="text-align:right;" onkeypress="return keypress(event);"></td>
	                    	<%} %>
	                </TR>
              				
               		<% }%>
               		                		
               		<% for(int i = 0; i < 40 - spSudungList.size(); i++){ %>
	                <TR class='tbdarkrow'>
	                    	<td align="center"><input type="text" value="" name = "masp" onkeyup="ajax_showOptions(this,'abc',event)"  AUTOCOMPLETE="off"></td>
	                   		<td align="left"><input type="text"  value="" name= "tensp" readonly></td>
	                   		<% if(dkkmBean.getType().equals("1")){ %>
	                    		<td align="center"><input type="number" name="soluong" value =""  style="text-align:right;width:100%" onkeypress="return keypress(event);"></td>
	                    	<%} else{ %>
	                    		<td align="center"><input type="number" name="soluong"  style="text-align:right;width:100%" onkeypress="return keypress(event);"></td>
	                    	<%} %>
	                </TR>
                	<%}}} %>
                <tr class="tbfooter">
                    <td colspan="4">&nbsp;</td>
                </tr>            
            </TABLE> 
            <br>
               <a class="button" href="javascript:clearform()">
                   <img style="top: -4px;" src="../images/button.png" alt=""><%=ChuyenNgu.get("Nhập lại sản phẩm",nnId) %> </a>     
           <%} else {%>
            <TR>
	                <TD class="plainlabel"><%=ChuyenNgu.get("Tổng tiền",nnId) %> </TD>
	                    <% if(dkkmBean.getType().equals("1")){ %>
	                    	<TD class="plainlabel"><input type="number" name="tongtien" id="tongtien" value="<%= dkkmBean.getTongtien() %>"   readonly> VND</TD> 
	                    <%}else{ %>
	                    	<TD class="plainlabel"><input type="number" name="tongtien" id="tongtien" value="<%= dkkmBean.getTongtien() %>"   onkeypress="return keypress(event);"> VND</TD> 
	                    <%} %>
	                </TR> 
           <%} } %>    
              
        </div>      
     </fieldset>	
    </div>
</div>
</form>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</html>