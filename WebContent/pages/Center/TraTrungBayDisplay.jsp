<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.dms.center.beans.tratrungbay.*" %>
<%@ page  import = "geso.dms.center.beans.tratrungbay.imp.*" %>
<%@ page  import = "geso.dms.center.beans.nhomsptrungbay.ISanpham" %>
<%@ page  import = "geso.dms.center.beans.nhomsptrungbay.imp.Sanpham" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "java.util.List" %>
<% ITratrungbay tratbBean = (ITratrungbay)session.getAttribute("tratbBean"); %>
<% ResultSet nhomsp = tratbBean.getNhomspRs(); %>
<% Hashtable<String, Integer> sp_nhomSpIds = tratbBean.getSp_nhomspIds(); %>
<% List<ISanpham> spList = tratbBean.getSpList(); %>
<% List<ISanpham> spSudungList = tratbBean.getSpSudungList(); %>

<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<TITLE>Best - Project</TITLE>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">

	<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
	<LINK rel="stylesheet" href="../css/main.css" type="text/css">
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
	<script type="text/javascript" src="../scripts/tratrungbay_sanpham.js"></script>
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

<form name="trakmForm" method="post" action="../../TratrungbayUpdateSvl">  <% geso.dms.center.util.Csrf csdr=new geso.dms.center.util.Csrf(request,response,true,false,true);%> <input type="hidden" name="<%=csdr.get_tokenName() %>" value='<%=csdr.get_tokenValue() %>'>
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="id" value='<%= tratbBean.getId() %>'>
<div id="main" style="width:99%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:70%; padding:5px; float:left" class="tbnavigation">
        	Qu???n l?? tr??ng b??y > Tr??? tr??ng b??y > Hi???n th???
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Ch??o m???ng b???n &nbsp;&nbsp; <%= userTen %> &nbsp;&nbsp;
        </div>
    </div>
  
  	<div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="tbdarkrow">
    	<A href= "javascript:history.back()" >
        	<img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1px" longdesc="Quay ve" style="border-style:outset"></A>
     </div>
  	
  	<div align="left" style="width:100%; float:none; clear:left">
  		<fieldset>
    		<legend class="legendtitle"> B??o l???i nh???p li???u</legend>
    		<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold" rows="1" style="width: 100%" readonly="readonly" readonly="readonly"><%= tratbBean.getMessage() %></textarea>
		         <% tratbBean.setMessage(""); %>
    	</fieldset>
  	</div>
  	
    <div align="left" style="width:100%; float:none; clear:left">
    <fieldset>
    	<legend class="legendtitle"> Tr??? tr??ng b??y </legend>
        		<div style="float:none; width:100%" align="left">
                <TABLE width="100%" cellpadding="6" cellspacing="0">								
                    <TR>
                        <TD width="15%" class="plainlabel" valign="top">Di???n gi???i </TD>
                        <TD class="plainlabel" valign="top"><textarea name="diengiai" style="width:500px" rows="1"><%= tratbBean.getDiengiai() %></textarea></TD>
                    </TR>               
                    <TR>
                        <TD class="plainlabel">Lo???i</TD>
                        <TD class="plainlabel">
                            <select name="type" id="type" onChange="submitform()">
                                	<% if(tratbBean.getType().equals("1")){ %>		                            	                            	
		                                <option value="1" selected>Tr??? ti???n</option>
		                                <option value="2">Tr??? s???n ph???m</option>  
		                                <option value=""> </option>	
	                                <%} else {if(tratbBean.getType().equals("2")){ %>
	                                	<option value="2" selected>Tr??? s???n ph???m</option>  
	                                	<option value="1">Tr??? ti???n</option>
		                                <option value=""> </option>
	                               <% } else{ %>
	                               		<option value="" selected> </option>
	                                	<option value="1">Tr??? ti???n </option>
	                                	<option value="2">Tr??? s???n ph???m </option>    
	                               <% }} %>                        
                            </select>
                         </TD> 
                    </TR>
                    <% if(tratbBean.getType().equals("2")){ %>
	                    <TR>
	                        <TD class="plainlabel">H??nh th???c</TD>
	                        <TD class="plainlabel">
	                            <select name="hinhthuc" id="hinhthuc" onChange="submitform();" >
		                        <% if(tratbBean.getHinhthuc().equals("1")){ %>                           
		                            <option value="1" selected>B???t bu???c nh???p s??? l?????ng t???ng s???n ph???m</option>
		                            <option value="2">B???t k??? trong</option>
		                            <option value=""> </option>
		                        <%}else{if(tratbBean.getHinhthuc().equals("2")){ %>                      	
		                            <option value="2" selected>B???t k??? trong</option>
		                            <option value="1">B???t bu???c nh???p s??? l?????ng t???ng s???n ph???m</option>
		                            <option value=""> </option>
		                        <%}else{ %>
		                        	<option value="" selected> </option>                       	
		                            <option value="1">B???t bu???c nh???p s??? l?????ng t???ng s???n ph???m</option>      
		                            <option value="2">B???t k??? trong</option>                    
		                        <% }} %>
	                            </select>
	                        </TD> 
	                    </TR> 
                    <%} %>
                    <% if(tratbBean.getType().equals("1")){ %>
	                    <TR>
	                        <TD class="plainlabel">T???ng ti???n </TD>
	                        <TD class="plainlabel"><input type="text" name="tongtien" id="tongtien" value="<%= tratbBean.getTongtien() %>" style="text-align:right" size="15" onkeypress="return keypress(event);"> VND</TD> 
	                    </TR>
                    <%}else{ if(tratbBean.getType().equals("2")){ %>
	                   	<TR>
	                        <TD class="plainlabel">T???ng l?????ng </TD>
	                        <TD class="plainlabel">
	                        <% if(tratbBean.getHinhthuc().equals("1")){ %>
	                        	<input type="text" name="tongluong" id="tongluong" value="<%= tratbBean.getTongluong() %>" style="text-align:right" size="15" readonly>
	                        <%}else{ %>
	                        	<input type="text" name="tongluong" id="tongluong" value="<%= tratbBean.getTongluong() %>" style="text-align:right" size="15" onkeypress="return keypress(event);">
	                        <%} %>
	                        </TD> 
	                    </TR> 
                   	<%}} %>
                    
                    <% if(tratbBean.getType().equals("2")){ %>
                    <TR>
	                    <TD class="plainlabel">Nh??m s???n ph???m</TD>
	                    <TD class="plainlabel">
	                        <select name="nhomsp" id="nhomsp" onChange = "submitform();">
	                            <option value="" selected></option>
	                            <% if(nhomsp != null){
									  try{ while(nhomsp.next()){ 
						    			if(nhomsp.getString("nspId").equals(tratbBean.getNhomspId())){ %>
						      				<option value='<%= nhomsp.getString("nspId")%>' selected><%= nhomsp.getString("nspTen") %></option>
						      			<%}else{ %>
						     				<option value='<%= nhomsp.getString("nspId")%>'><%= nhomsp.getString("nspTen") %></option>
						     	<%}} nhomsp.close(); }catch(java.sql.SQLException e){} }%>
	                        </select>
	                     </TD> 
                	</TR>
                	<%} %>  		  				
                </TABLE>
                </div>
                <hr>
                <% if(tratbBean.getType().equals("2")){ %>
                	<div align="left" style="width:100%; float:none; clear:none" >
                <%}else{ %>
                	<div align="left" style="width:100%; float:none; clear:none; display:none" >
                <%} %>
               <TABLE class="tabledetail" id="content" width="100%" border="0" cellpadding="2" cellspacing="1">
                    <TR class="tbheader">
                        <TH align="center" width="15%">M?? s???n ph???m </TH>
                        <TH align="left" width="60%">T??n s???n  ph???m</TH>
                        <TH align="center">????n gi??</TH>
                        <% if(tratbBean.getHinhthuc().equals("1")){ %>
                       		 <TH align="center" width="10%">S??? l?????ng</TH>
                       	<%}else{ %>
                       		<TH align="center"  style="display: none">S??? l?????ng</TH>
                       	<%} %>
                    </TR>  
                    <% if(tratbBean.getNhomspId().length() > 0)
	                {
	                	for( int i =0; i < spList.size(); i++)
	                	{
	                		ISanpham sp = spList.get(i);
	                	%>
	                		<TR class='tbdarkrow'>	                    	
		                    	<td align="center"><input type="text" size="16" value="<%= sp.getMasanpham() %>" name="masp" readonly></td>
		                    	<td align="left"><input type="text" size="70" value="<%= sp.getTensanpham() %>" name = "tensp" readonly></td>
		                    	<td align="center"><input type="text" size="15" value="<%= sp.getDongia() %>" name = "dongia" readonly></td>
		                    	<% if(sp_nhomSpIds.containsKey(sp.getMasanpham().trim())){
		                    			if(tratbBean.getHinhthuc().equals("1")){ %>
		                    			<td align="center"><input type="text" value="<%= sp_nhomSpIds.get(sp.getMasanpham()) %>" size="10" name = "soluong" style="text-align:right" onkeypress="return keypress(event);"></td>
		                    		<%}else{ %>
		                    			<td align="center" style="display: none"><input type="text" value="<%= sp_nhomSpIds.get(sp.getMasanpham()) %>" size="10" name = "soluong" style="text-align:right" onkeypress="return keypress(event);"></td>
		                    	<%}}else{ if(tratbBean.getHinhthuc().equals("1")){ %>
		                    			<td align="center"><input type="text" size="10" name = "soluong" style="text-align:right" onkeypress="return keypress(event);"></td>
		                    		<%}else{ %>
		                    			<td align="center" style="display: none"><input type="text" size="10" name = "soluong" style="text-align:right" onkeypress="return keypress(event);"></td>
		                    		<%}} %>
			                </TR>
	                	<%}}
	                else { if(tratbBean.getType().length() > 0){
	               			for(int i = 0; i < spSudungList.size(); i++)
	               			{
	               				Sanpham sp = (Sanpham)spSudungList.get(i);  %>
	              				<TR class='tbdarkrow'>
			                    	<td align="center"><input type="text" size="16" value="<%= sp.getMasanpham() %>" name = "masp" onkeyup="ajax_showOptions(this,'abc',event)" size="18" AUTOCOMPLETE="off"></td>
			                   		<td align="left"><input type="text" size="70" value="<%= sp.getTensanpham() %>" name= "tensp" readonly></td>
			                   		<td align="center"><input type="text" size="15" value="<%= sp.getDongia() %>" name= "dongia" readonly></td>
			                   		<% if(tratbBean.getHinhthuc().equals("1")){ %>
			                    		<td align="center"><input type="text" name="soluong" size="10" value="<%= sp.getSoluong() %>"  style="text-align:right" onkeypress="return keypress(event);"></td>
			                    	<%}else{ %>
			                    		<td align="center" style="display: none"><input type="text" name="soluong" size="10" value="<%= sp.getSoluong() %>"  style="text-align:right" onkeypress="return keypress(event);"></td>
			                    	<%} %>
		                    	</TR>
	               		<% }%>
	               		                		
	               		<% for(int i = 0; i < 40 - spSudungList.size(); i++){ %>
		                <TR class='tbdarkrow'>
	                    	<td align="center"><input type="text" size="16" value="" name = "masp" onkeyup="ajax_showOptions(this,'abc',event)" size="18" AUTOCOMPLETE="off"></td>
	                   		<td align="left"><input type="text" size="70" value="" name= "tensp" readonly></td>
	                   		<td align="center"><input type="text" size="15" value="" name= "dongia" readonly></td>
	                   		<% if(tratbBean.getHinhthuc().equals("1")){ %>
	                    		<td align="center"><input type="text" name="soluong" size="10" style="text-align:right" onkeypress="return keypress(event);"></td>
	                    	<%} else{ %>
	                    		<td align="center" style="display: none"><input type="text" name="soluong" size="10" style="text-align:right" onkeypress="return keypress(event);"></td>
	                    	<%} %>
		                </TR>
                	<%}}} %>                   
                    <tr class="tbfooter">
                        <td colspan="4">&nbsp;</td>
                    </tr>             
                </TABLE>
				<br><a class="button" href="javascript:clearform()">
					<img style="top: -4px;" src="../images/button.png" alt="">Nh???p l???i s???n ph???m </a>                         
            </div>     
     </fieldset>	
    </div>
</div>
</form>
</body>  <script type='text/javascript' src='../scripts/loadingv2.js'></script>
</html>