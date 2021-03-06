<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.beans.nhomsptrungbay.*" %>
<%@ page  import = "geso.dms.center.beans.nhomsptrungbay.imp.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%@ page  import = "geso.dms.center.util.ChuyenNgu" %>
<% INhomsptrungbay nsptbBean = (INhomsptrungbay)session.getAttribute("nsptbBean"); %>
<% ResultSet nhomsp = nsptbBean.getNhomspRs(); %>
<% Hashtable<String, Integer> sp_nhomSpIds = nsptbBean.getSp_nhomspIds(); %>
<% List<ISanpham> spList = nsptbBean.getSpList(); %>
<% List<ISanpham> spSudungList = nsptbBean.getSpSudungList(); %>

<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  %>
<% String nnId = (String)session.getAttribute("nnId"); %> 
<% Utility util = (Utility) session.getAttribute("util"); %>
<% if(nnId == null) {
	nnId = "vi"; 
	
	}	
String url = util.getChuyenNguUrl("NhomsptrungbaySvl","",nnId);	
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
	<script type="text/javascript" src="../scripts/nhomsptrungbay_sanpham.js"></script>
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
	   document.forms["nsptbForm"].submit();
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
				if(tongluong == "")
				{
					alert('B???n ph???i nh???p t???ng l?????ng...');
					return;
				}
			}
			if(tongtn.checked)
			{
				var tongtien = document.getElementById("tongtien").value;			
				if(tongtien == "")
				{
					alert('B???n ph???i nh???p t???ng ti???n...');
					return;
				}
			}
		}
		
		if(checkSanphamNhap() == false)
		{
			alert('Kh??ng c?? s???n ph???m n??o ???????c nh???p...');
			return;
		}
		
		if(checkTrungmasp() == false)
		{
			alert('Ki???m tra l???i c??c s???n ph???m tr??? tr??ng b??y tr??ng m??...');
			return;
		}
		
		if(type == 1) //nhap soluong tung san pham
		{
			if(checkSanpham() == false)
			{
				alert('Ki???m tra l???i s??? l?????ng s???n ph???m...');
				return;
			}
		}
		
		document.forms["nsptbForm"].action.value = "save";
		document.forms["nsptbForm"].submit();		
	}
	
	function checkSanpham()
	{	
		var masp = document.getElementsByName("masp");
		var soluong = document.getElementsByName("soluong");
		for(k=0; k < masp.length; k++)
		{
			if(masp.item(k).value != "")
			{
				if(soluong.item(k).value == "")
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

<form name="nsptbForm" method="post" action="../../NhomsptrungbayUpdateSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="action" value='1'>
<div id="main" style="width:99%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:70%; padding:5px; float:left" class="tbnavigation">
        	<%= " " + url %> > T???o m???i
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Ch??o m???ng b???n <%= userTen %> &nbsp;
        </div>
    </div>
  
  	<div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="tbdarkrow">
    	<A href= "javascript:history.back()" >
        	<img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1px" longdesc="Quay ve" style="border-style:outset"></A>
        <A href="javascript:saveform()" >
        	<IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border ="1px" style="border-style:outset"></A>
    </div>
  	
  	<div align="left" style="width:100%; float:none; clear:left">
  		<fieldset>
    		<legend class="legendtitle"> B??o l???i nh???p li???u</legend>
    		<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold" readonly="readonly" rows="1"><%= nsptbBean.getMessage() %></textarea>
		         <% nsptbBean.setMessage(""); %>
    	</fieldset>
  	</div>
  	
    <div align="left" style="width:100%; float:none; clear:left">
    <fieldset>
    	<legend class="legendtitle"> ??i???u ki???n tr??ng b??y </legend>
        	<div style="float:none; width:100%" align="left">
            <TABLE width="100%" cellpadding="6" cellspacing="0">							
                <TR>
                    <TD width="15%" class="plainlabel" valign="top"><%=ChuyenNgu.get("Di???n gi???i",nnId) %></TD>
                    <TD class="plainlabel" valign="top"><textarea name="diengiai" style="width:500px; " rows="1" "><%= nsptbBean.getDiengiai() %></textarea></TD>
                </TR> 
                
                <TR>
                    <TD class="plainlabel"><%=ChuyenNgu.get("Lo???i ??i???u ki???n",nnId) %></TD>
                    <TD class="plainlabel">
                        <select name="type" id="type" onChange="submitform();">
                        <% if(nsptbBean.getType().equals("1")){ %>                           
                            <option value="1" selected><%=ChuyenNgu.get("S??? l?????ng t???ng s???n ph???m b???t bu???c",nnId) %></option>
                            <option value="2"><%=ChuyenNgu.get("B???t k??? trong",nnId) %></option>
                            <option value=""> </option>
                        <%}else{if(nsptbBean.getType().equals("2")){ %>                      	
                            <option value="2" selected><%=ChuyenNgu.get("B???t k??? trong",nnId) %></option>
                            <option value="1"><%=ChuyenNgu.get("S??? l?????ng t???ng s???n ph???m b???t bu???c",nnId) %></option>
                            <option value=""> </option>
                        <%}else{ %>
                        	<option value="" selected> </option>                       	
                            <option value="1"><%=ChuyenNgu.get("S??? l?????ng t???ng s???n ph???m b???t bu???c",nnId) %></option>      
                            <option value="2"><%=ChuyenNgu.get("B???t k??? trong",nnId) %></option>                    
                        <% }} %>
                        </select>
                     </TD> 
                </TR>  	
                <TR>
                    <TD width="15%" class="plainlabel" valign="top"><%=ChuyenNgu.get("H??nh th???c",nnId) %></TD>
                    <TD class="plainlabel" valign="middle">  
                    <% if(nsptbBean.getHinhthuc().equals("2")){ %>              	
                        <input type="radio" name="option" value="1" id="tonglg" onChange="submitform();" ><%=ChuyenNgu.get("Nh???p t???ng l?????ng",nnId) %> &nbsp;
                        <input type="radio" name="option" value="2" id="tongtn" onChange="submitform();" checked><%=ChuyenNgu.get("Nh???p t???ng ti???n",nnId) %> &nbsp; 
                    <%} else{ %>
                    	<input type="radio" name="option" value="1" id="tonglg" onChange="submitform();" checked><%=ChuyenNgu.get("Nh???p t???ng l?????ng",nnId) %> &nbsp;
                        <input type="radio" name="option" value="2" id="tongtn" onChange="submitform();" ><%=ChuyenNgu.get("Nh???p t???ng ti???n",nnId) %> &nbsp; 
                    <%} %>                    
                    </TD>
                </TR>
                <% if(nsptbBean.getHinhthuc().equals("2")){ %>              
	               <TR>
	                    <TD class="plainlabel"><%=ChuyenNgu.get("T???ng ti???n",nnId) %></TD>
	                    <% if(nsptbBean.getType().equals("1")){ %>
	                    	<TD class="plainlabel"><input type="text" name="tongtien" id="tongtien" value="<%= nsptbBean.getTongtien() %>" style="text-align:right" size="15" readonly> VND</TD> 
	                    <%}else{ %>
	                    	<TD class="plainlabel"><input type="text" name="tongtien" id="tongtien" value="<%= nsptbBean.getTongtien() %>" style="text-align:right" size="15" onkeypress="return keypress(event);"> VND</TD> 
	                    <%} %>
	                </TR>
                <%}else{ %>
	                <TR>
	                    <TD class="plainlabel"><%=ChuyenNgu.get("T???ng l?????ng",nnId) %></TD>
	                    <% if(nsptbBean.getType().equals("1")){ %>
	                    	<TD class="plainlabel"><input type="text" name="tongluong" id="tongluong" value="<%= nsptbBean.getTongluong() %>" style="text-align:right" size="15" readonly></TD> 
	                    <%}else{ %>
	                    	<TD class="plainlabel"><input type="text" name="tongluong" id="tongluong" value="<%= nsptbBean.getTongluong() %>" style="text-align:right" size="15" onkeypress="return keypress(event);"></TD> 
	                    <%} %>
	                </TR> 
                <%} %>  
                <TR>
                    <TD class="plainlabel"><%=ChuyenNgu.get("Nh??m s???n ph???m",nnId) %></TD>
                    <TD class="plainlabel">
                        <select name="nhomsp" id="nhomsp" onChange = "submitform();">
                            <option value="" selected></option>
                            <% if(nhomsp != null){
								  try{ while(nhomsp.next()){ 
					    			if(nhomsp.getString("nspId").equals(nsptbBean.getNhomspId())){ %>
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
           <TABLE width="100%" border="0" cellpadding="1" cellspacing="1" >
                <TR class="tbheader">                   
                
               	<TH align="center" width="15%"><%=ChuyenNgu.get("M?? s???n ph???m",nnId) %></TH>
               	<TH align="left" width="73%"><%=ChuyenNgu.get("T??n s???n ph???m",nnId) %></TH>
              	<TH align="center" width="10%"><%=ChuyenNgu.get("S??? l?????ng",nnId) %></TH>
                 
                </TR>
                <% if(nsptbBean.getNhomspId().length() > 0)
                {
                	for( int i =0; i < spList.size(); i++)
                	{
                		ISanpham sp = spList.get(i);
                	%>
                		<TR class='tbdarkrow'>	                    	
	                    	<td align="center"><input type="text"  value="<%= sp.getMasanpham() %>" name="masp" style="width:100%" readonly></td>
	                    	<td align="left"><input type="text"  value="<%= sp.getTensanpham() %>" name = "tensp" style="width:100%" readonly></td>
	                    	<% if(sp_nhomSpIds.containsKey(sp.getMasanpham().trim())){
	                    		if(nsptbBean.getType().equals("1")){ %>
	                    			<td align="center"><input type="text" value="<%= sp_nhomSpIds.get(sp.getMasanpham()) %>"  name = "soluong" style="text-align:right;width:100%" onkeypress="return keypress(event);"></td>
	                    		<%}else{ %>
	                    			<td align="center" ><input type="text" value="<%= sp_nhomSpIds.get(sp.getMasanpham()) %>"  name = "soluong" style="text-align:right;width:100%" onkeypress="return keypress(event);"></td>
	                    	<%}}else{ if(nsptbBean.getType().equals("1")){ %>
	                    			<td align="center"><input type="text" name = "soluong" style="text-align:right;width:100%" onkeypress="return keypress(event);"></td>
	                    		<%}else{ %>
	                    			<td align="center" ><input type="text" name = "soluong" style="text-align:right;width:100%" onkeypress="return keypress(event);"></td>
	                    		<%}} %>
		                </TR>
                	<%}}
                else { if(nsptbBean.getType().length() > 0){
               			for(int i = 0; i < spSudungList.size(); i++)
               			{
               				Sanpham sp = (Sanpham)spSudungList.get(i);  %>
              				<TR class='tbdarkrow'>
		                    	<td align="center"><input type="text" value="<%= sp.getMasanpham() %>" name = "masp" onkeyup="ajax_showOptions(this,'abc',event)" style="width:100%" AUTOCOMPLETE="off"></td>
		                   		<td align="left"><input type="text"  value="<%= sp.getTensanpham() %>" name= "tensp" style="width:100%" readonly></td>
		                    	<td align="center"><input type="text" name="soluong"  value="<%= sp.getSoluong() %>"  style="text-align:right ;width:100%" onkeypress="return keypress(event);"></td>
	                    	</TR>
               		<% }%>
               		                		
               		<% for(int i = 0; i < 40 - spSudungList.size(); i++){ %>
	                <TR class='tbdarkrow'>
                    	<td align="center"><input type="text"  value="" name = "masp" style="width:100%" onkeyup="ajax_showOptions(this,'abc',event)"  AUTOCOMPLETE="off"></td>
                   		<td align="left"><input type="text"  value="" name= "tensp" style="width:100%" readonly></td>
                    	<td align="center"><input type="text" name="soluong"  style="text-align:right;width:100%" onkeypress="return keypress(event);"></td>      	
	                </TR>
                	<%}}} %>
                <tr class="tbfooter">
                    <td colspan="4">&nbsp;</td>
                </tr>            
            </TABLE> 
            <br>
               <a class="button" href="javascript:clearform()">
                   <img style="top: -4px;" src="../images/button.png" alt=""><%=ChuyenNgu.get("Nhap lai san pham",nnId) %></a>             
        </div>      
     </fieldset>	
    </div>
</div>
</form>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</html>